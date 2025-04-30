package gov.cdc.nbs.patient.file.demographics.summary;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Optional;

@Component
class PatientDemographicsSummaryEmailFinder {

  private static final String QUERY = """
      select
          [email_address].email_address    as [email_address]
      from Entity_locator_participation [locators]
      
          join Tele_locator [email_address] on
                  [email_address].[tele_locator_uid] = [locators].[locator_uid]
              and [email_address].record_status_cd   = [locators].[record_status_cd]
              and [email_address].email_address is not null
      
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
  private static final int EMAIL_COLUMN = 1;

  private final JdbcTemplate template;

  PatientDemographicsSummaryEmailFinder(final JdbcTemplate template) {
    this.template = template;

  }

  Optional<String> find(final long patient, final LocalDate asOf) {
    return this.template.query(
            QUERY, statement -> {
              statement.setLong(PATIENT_PARAMETER, patient);
              statement.setTimestamp(AS_OF_PARAMETER, Timestamp.valueOf(asOf.atStartOfDay()));
            },
            (rs, row) -> rs.getString(EMAIL_COLUMN)
        ).stream()
        .findFirst();
  }

}
