package gov.cdc.nbs.patient.search.email;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
class PatientSearchResultEmailFinder {

  private static final String QUERY = """
      select distinct
      [locator].as_of_date            as [as_of],
      [email_address].email_address   as [email],
      [locator].locator_uid as [locator_uid]
      from Entity_locator_participation [locator]
      
      join Tele_locator [email_address] on
      [email_address].[tele_locator_uid] = [locator].[locator_uid]
      and [email_address].record_status_cd = 'ACTIVE'
      and [email_address].email_address is not null
      
      
      where   [locator].entity_uid = ?
      and [locator].[class_cd] = 'TELE'
      and [locator].record_status_cd = 'ACTIVE'
      order by
      [locator].as_of_date desc, [locator].locator_uid desc""";

  private static final int PATIENT_PARAMETER = 1;
  private static final int EMAIL_COLUMN = 2;

  private final JdbcTemplate template;

  PatientSearchResultEmailFinder(final JdbcTemplate template) {
    this.template = template;
  }

  Collection<String> find(final long patient) {
    return this.template.query(QUERY, statement -> statement.setLong(PATIENT_PARAMETER, patient),
        (rs, row) -> rs.getString(EMAIL_COLUMN));
  }
}
