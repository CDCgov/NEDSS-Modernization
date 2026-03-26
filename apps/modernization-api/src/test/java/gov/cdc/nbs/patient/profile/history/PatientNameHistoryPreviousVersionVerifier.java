package gov.cdc.nbs.patient.profile.history;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class PatientNameHistoryPreviousVersionVerifier {
  private static final String QUERY =
      """
      select 1 from Person_name [patient]
          join Person_name_hist [history] on
                  [history].[person_uid] = [patient].[person_uid]
      where [patient].person_uid = ?
      and [patient].person_name_seq = 1
      """;
  private static final int RESULT_COLUMN = 1;
  private static final int PATIENT_PARAMETER = 1;

  private final JdbcTemplate template;

  PatientNameHistoryPreviousVersionVerifier(final JdbcTemplate template) {
    this.template = template;
  }

  boolean verify(final long patient) {
    return !this.template
        .query(
            QUERY,
            statement -> statement.setLong(PATIENT_PARAMETER, patient),
            (resultSet, row) -> resultSet.getLong(RESULT_COLUMN))
        .isEmpty();
  }
}
