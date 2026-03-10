package gov.cdc.nbs.patient.profile.history;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class PatientEntityLocatorHistoryPreviousVersionVerifier {
  private static final String QUERY =
      """
      select 1 from Entity_locator_participation [entity]
          join Entity_loc_participation_hist [history] on
                  [history].[entity_uid] = [entity].[entity_uid]
      where [entity].entity_uid = ?
      """;
  private static final int RESULT_COLUMN = 1;
  private static final int ENTITY_PARAMETER = 1;
  private final JdbcTemplate template;

  public PatientEntityLocatorHistoryPreviousVersionVerifier(JdbcTemplate template) {
    this.template = template;
  }

  boolean verify(final long entityUid) {
    return !this.template
        .query(
            QUERY,
            statement -> statement.setLong(ENTITY_PARAMETER, entityUid),
            (resultSet, row) -> resultSet.getLong(RESULT_COLUMN))
        .isEmpty();
  }
}
