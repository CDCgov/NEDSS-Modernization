package gov.cdc.nbs.patient.profile.history;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class PatientIdentificationHistoryPreviousVersionVerifier {
  private static final String QUERY =
      """
      select 1 from Entity_id [entity]
          join Entity_id_hist [history] on
                  [history].[entity_uid] = [entity].[entity_uid]
      where [entity].entity_uid = ?
      and [entity].entity_id_seq = 1
      """;
  private static final int RESULT_COLUMN = 1;
  private static final int ENTITY_PARAMETER = 1;

  private final JdbcTemplate template;

  public PatientIdentificationHistoryPreviousVersionVerifier(JdbcTemplate template) {
    this.template = template;
  }

  boolean verify(final long entity) {
    return !this.template
        .query(
            QUERY,
            statement -> statement.setLong(ENTITY_PARAMETER, entity),
            (resultSet, row) -> resultSet.getLong(RESULT_COLUMN))
        .isEmpty();
  }
}
