package gov.cdc.nbs.patient.profile.history;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class PatientRaceHistoryPreviousVersionVerifier {
  private static final String QUERY =
      """
      select 1 from Person_race [race]
          join Person_race_hist [history] on
                  [history].[person_uid] = [race].[person_uid]
      where [race].person_uid = ?
      """;
  private static final int RESULT_COLUMN = 1;
  private static final int PATIENT_PARAMETER = 1;

  private final JdbcTemplate template;

  public PatientRaceHistoryPreviousVersionVerifier(JdbcTemplate template) {
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
