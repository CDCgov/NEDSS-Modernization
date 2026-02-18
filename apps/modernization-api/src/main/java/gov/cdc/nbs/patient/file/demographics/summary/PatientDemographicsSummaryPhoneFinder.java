package gov.cdc.nbs.patient.file.demographics.summary;

import gov.cdc.nbs.demographics.phone.DisplayablePhone;
import gov.cdc.nbs.demographics.phone.DisplayablePhoneRowMapper;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

@Component
class PatientDemographicsSummaryPhoneFinder {

  private static final String QUERY =
      """
      with phones(patient, locator_uid, as_of_date, [type], [use], [phone]) as (
          select
              [locators].entity_uid,
              [locators].locator_uid,
              [locators].as_of_date,
              [locators].cd,
              [locators].use_cd,
              [phone_number].phone_nbr_txt
          from Entity_locator_participation [locators]

          join Tele_locator [phone_number] on
                  [phone_number].[tele_locator_uid] = [locators].[locator_uid]
              and [phone_number].record_status_cd   = [locators].[record_status_cd]
              and [phone_number].phone_nbr_txt is not null

          where [locators].entity_uid = ?
          and [locators].[class_cd] = 'TELE'
          and [locators].record_status_cd = 'ACTIVE'
      )
      select
          coalesce(
              [type].code_short_desc_txt,
              [phones].[type],
              ''
          )                               as [type],
          case [phones].[use]
              when 'WP' then 'Work'
              when 'SB' then 'Work'
              when 'H' then 'Home'
              when 'MC' then 'Cell'
              else\s
                  coalesce(
                      [use].code_short_desc_txt,
                      [phones].[use],
                      ''
                  )
          end as [use],
          [phones].phone                   as [phone_number]
      from phones [phones]

          left join  NBS_SRTE..Code_value_general [type] on

                  [type].code_set_nm = 'EL_TYPE_TELE_PAT'
              and [type].code = [phones].[type]

          left join NBS_SRTE..Code_value_general [use] on
                  [use].code_set_nm = 'EL_USE_TELE_PAT'
              and [use].code = [phones].[use]

      where [phones].as_of_date = (
              select
                  max(eff_as_of.as_of_date)
              from phones [eff_as_of]
              where   [eff_as_of].patient     = [phones].patient
                  and [eff_as_of].[as_of_date] <= ?
          )
          and [phones].locator_uid = (
              select
                  max(eff_seq.locator_uid)
              from phones [eff_seq]
              where   [eff_seq].patient     = [phones].patient
                  and [eff_seq].[as_of_date]  = [phones].as_of_date
      )
      """;

  private final JdbcClient client;
  private final RowMapper<DisplayablePhone> mapper;

  PatientDemographicsSummaryPhoneFinder(final JdbcClient client) {
    this.client = client;
    this.mapper = new DisplayablePhoneRowMapper();
  }

  Optional<DisplayablePhone> find(final long patient, final LocalDate asOf) {
    return this.client
        .sql(QUERY)
        .param(patient)
        .param(asOf.atTime(23, 59, 59))
        .query(this.mapper)
        .optional();
  }
}
