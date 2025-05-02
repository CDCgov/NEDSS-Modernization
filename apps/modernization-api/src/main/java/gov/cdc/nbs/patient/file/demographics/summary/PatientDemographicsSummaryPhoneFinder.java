package gov.cdc.nbs.patient.file.demographics.summary;

import gov.cdc.nbs.demographics.phone.DisplayablePhone;
import gov.cdc.nbs.demographics.phone.DisplayablePhoneRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Optional;

@Component
class PatientDemographicsSummaryPhoneFinder {

  private static final String QUERY = """
      select
          coalesce(
              [type].code_short_desc_txt,
              [locators].cd,
              ''
          )                               as [type],
          coalesce(
              [use].code_short_desc_txt,
              [locators].[use_cd],
              ''
          )                               as [use],
          [phone_number].phone_nbr_txt    as [phone_number]
      from Entity_locator_participation [locators]
      
          join Tele_locator [phone_number] on
                  [phone_number].[tele_locator_uid] = [locators].[locator_uid]
              and [phone_number].record_status_cd   = [locators].[record_status_cd]
              and [phone_number].phone_nbr_txt is not null
      
          left join  NBS_SRTE..Code_value_general [type] on
      
                  [type].code_set_nm = 'EL_TYPE_TELE_PAT'
              and [type].code = [locators].cd
      
          left join NBS_SRTE..Code_value_general [use] on
                  [use].code_set_nm = 'EL_USE_TELE_PAT'
              and [use].code = [locators].[use_cd]
      
      where [locators].entity_uid = ?
          and [locators].[class_cd] = 'TELE'
          and [locators].record_status_cd = 'ACTIVE'
          and [locators].as_of_date = (
                          select
                              max(eff_as_of.as_of_date)
                          from Entity_locator_participation [eff_as_of]
      
                          where   [eff_as_of].[entity_uid]         = [locators].entity_uid
                              and [eff_as_of].[class_cd]           = [locators].[class_cd]
                              and [eff_as_of].[record_status_cd]   = [locators].[record_status_cd]
                              and [eff_as_of].[as_of_date]         <= ?
                      )
              and [locators].locator_uid = (
                  select
                      max(eff_seq.locator_uid)
                  from Entity_locator_participation [eff_seq]\s
                                  where   [eff_seq].[entity_uid] = [locators].entity_uid
                              and [eff_seq].[class_cd]           = [locators].[class_cd]
                              and [eff_seq].[record_status_cd]   = [locators].[record_status_cd]
                              and [eff_seq].[as_of_date]         = [locators].as_of_date
          )
      """;

  private static final int PATIENT_PARAMETER = 1;
  private static final int AS_OF_PARAMETER = 2;

  private final JdbcTemplate template;
  private final RowMapper<DisplayablePhone> mapper;

  PatientDemographicsSummaryPhoneFinder(final JdbcTemplate template) {
    this.template = template;
    this.mapper = new DisplayablePhoneRowMapper();
  }

  Optional<DisplayablePhone> find(final long patient, final LocalDate asOf) {
    return this.template.query(
            QUERY, statement -> {
              statement.setLong(PATIENT_PARAMETER, patient);
              statement.setTimestamp(AS_OF_PARAMETER, Timestamp.valueOf(asOf.atStartOfDay()));
            },
            this.mapper
        ).stream()
        .findFirst();
  }

}
