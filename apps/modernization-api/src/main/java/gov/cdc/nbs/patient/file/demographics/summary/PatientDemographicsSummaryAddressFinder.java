package gov.cdc.nbs.patient.file.demographics.summary;

import gov.cdc.nbs.demographics.address.DisplayableAddress;
import gov.cdc.nbs.demographics.address.DisplayableAddressRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Optional;

@Component
class PatientDemographicsSummaryAddressFinder {

  private static final String QUERY = """
      select
          coalesce(
              [use].code_short_desc_txt,
              [locators].[use_cd]
          )                               as [use],
          [address].street_addr1          as [address_1],
          [address].street_addr2          as [address_2],
          [address].city_desc_txt         as [city],
          [state].[state_nm]              as [state],
          [address].zip_cd                as [zip]
      from Entity_locator_participation [locators]
      
              join Postal_locator [address] on
                      [address].[postal_locator_uid] = [locators].[locator_uid]
                  and [address].record_status_cd = [locators].record_status_cd
      
      
              left join NBS_SRTE..Code_value_general [use] on
                      [use].code_set_nm = 'EL_USE_PST_PAT'
                  and [use].code = [locators].[use_cd]
      
              left join NBS_SRTE..State_code [state] on
                      [state].state_cd = [address].state_cd
      
      
      where   [locators].entity_uid = ?
          and [locators].[class_cd] = 'PST'
          and [locators].[use_cd] not in ('BIR', 'DTH')
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
                              where   [eff_seq].[entity_uid]         = [locators].entity_uid
                          and [eff_seq].[class_cd]           = [locators].[class_cd]
                          and [eff_seq].[record_status_cd]   = [locators].[record_status_cd]
                          and [eff_seq].[as_of_date]         = [locators].as_of_date
          )
      """;

  private static final int PATIENT_PARAMETER = 1;
  private static final int AS_OF_PARAMETER = 2;

  private final JdbcTemplate template;
  private final RowMapper<DisplayableAddress> mapper;

  PatientDemographicsSummaryAddressFinder(final JdbcTemplate template) {
    this.template = template;
    this.mapper = new DisplayableAddressRowMapper();
  }

  Optional<DisplayableAddress> find(final long patient, final LocalDate asOf) {
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
