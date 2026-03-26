package gov.cdc.nbs.patient.file.demographics.summary;

import java.time.LocalDate;
import java.util.Optional;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

@Component
class PatientDemographicsSummaryEmailFinder {

  private static final String QUERY =
      """
      with emails(patient, locator_uid, as_of_date, [type], [use], [email]) as (
          select
              [locators].entity_uid,
              [locators].locator_uid,
              [locators].as_of_date,
              [locators].cd,
              [locators].use_cd,
              [email_address].email_address
          from Entity_locator_participation [locators]

          join Tele_locator [email_address] on
                  [email_address].[tele_locator_uid] = [locators].[locator_uid]
              and [email_address].record_status_cd   = [locators].[record_status_cd]
              and [email_address].email_address is not null

          where   [locators].entity_uid = ?
              and [locators].[class_cd] = 'TELE'
              and [locators].record_status_cd = 'ACTIVE'
      )
      select
          [emails].email  as [email_address]
      from emails

      where [emails].as_of_date = (
              select
                  max(eff_as_of.as_of_date)
              from emails [eff_as_of]
              where   [eff_as_of].patient     = [emails].patient
                  and [eff_as_of].[as_of_date] <= ?
          )
      and [emails].locator_uid = (
          select
              max(eff_seq.locator_uid)
          from emails [eff_seq]
          where   [eff_seq].patient     = [emails].patient
              and [eff_seq].[as_of_date]  = [emails].as_of_date
      )
      """;

  private final JdbcClient client;

  PatientDemographicsSummaryEmailFinder(final JdbcClient client) {
    this.client = client;
  }

  Optional<String> find(final long patient, final LocalDate asOf) {
    return this.client
        .sql(QUERY)
        .param(patient)
        .param(asOf.atTime(23, 59, 59))
        .query(String.class)
        .optional();
  }
}
