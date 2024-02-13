package gov.cdc.nbs.patient.profile.history;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
class PatientHistoryPreviousVersionVerifier {

  private static final String QUERY = """
      select 1 from Person [patient]
          join Person_hist [history] on
                  [history].[person_uid] = [patient].[person_uid]
              and [history].version_ctrl_nbr = [patient].[version_ctrl_nbr] - 1

      where [patient].cd = 'PAT'
      and [patient].person_uid = ?
      """;
  private static final int RESULT_COLUMN = 1;
  private static final int PATIENT_PARAMETER = 1;

  private final JdbcTemplate template;

  PatientHistoryPreviousVersionVerifier(final JdbcTemplate template) {
    this.template = template;
  }

  boolean verify(final long patient) {
    return !this.template.query(
        QUERY,
        statement -> statement.setLong(PATIENT_PARAMETER, patient),
        (resultSet, row) -> resultSet.getLong(RESULT_COLUMN)
    ).isEmpty();
  }
}
