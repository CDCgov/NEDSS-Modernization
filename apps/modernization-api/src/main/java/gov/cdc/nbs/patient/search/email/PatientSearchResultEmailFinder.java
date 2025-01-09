package gov.cdc.nbs.patient.search.email;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
class PatientSearchResultEmailFinder {

  private static final String QUERY = """
      select distinct
          IsNull([email_address].email_address, '') as [email]
      from Entity_locator_participation [locator]

          join Tele_locator [email_address] on
                  [email_address].[tele_locator_uid] = [locator].[locator_uid]
              and [email_address].record_status_cd = [locator].[record_status_cd]


      where   [locator].entity_uid = ?
          and [locator].[class_cd] = 'TELE'
          and [locator].cd = 'NET'
          and [locator].record_status_cd = 'ACTIVE'
      """;
  private static final int PATIENT_PARAMETER = 1;
  private static final int EMAIL_COLUMN = 1;

  private final JdbcTemplate template;

  PatientSearchResultEmailFinder(final JdbcTemplate template) {
    this.template = template;
  }

  Collection<String> find(final long patient) {
    return this.template.query(
        QUERY,
        statement -> statement.setLong(PATIENT_PARAMETER, patient),
        (rs, row) -> rs.getString(EMAIL_COLUMN));
  }
}
