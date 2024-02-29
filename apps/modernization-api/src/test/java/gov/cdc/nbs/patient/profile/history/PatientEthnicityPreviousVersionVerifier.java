package gov.cdc.nbs.patient.profile.history;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class PatientEthnicityPreviousVersionVerifier {
    private static final String QUERY = """
      select 1 from Person_ethnic_group [person_ethnic]
          join Person_ethnic_group_hist [person_ethnic_history] on
                  [person_ethnic_history].[person_uid] = [person_ethnic].[person_uid]
      where [person_ethnic].person_uid = ?
      """;
    private static final int RESULT_COLUMN = 1;
    private static final int PATIENT_PARAMETER = 1;
    private final JdbcTemplate template;

    public PatientEthnicityPreviousVersionVerifier(JdbcTemplate template) {
        this.template = template;
    }

    public boolean verify(long personUid) {
        return !this.template.query(
                QUERY,
                statement -> statement.setLong(PATIENT_PARAMETER, personUid),
                (resultSet, row) -> resultSet.getLong(RESULT_COLUMN)
        ).isEmpty();
    }
}
