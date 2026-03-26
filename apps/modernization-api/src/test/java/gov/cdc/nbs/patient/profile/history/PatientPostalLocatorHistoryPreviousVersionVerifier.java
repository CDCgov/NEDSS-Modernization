package gov.cdc.nbs.patient.profile.history;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class PatientPostalLocatorHistoryPreviousVersionVerifier {
  private static final String QUERY =
      """
      select 1 from Postal_locator [postal_locator]
          join Postal_locator_hist [postal_locator_history] on
                  [postal_locator_history].[postal_locator_uid] = [postal_locator].[postal_locator_uid]
      where [postal_locator].postal_locator_uid = ?
      """;
  private static final int RESULT_COLUMN = 1;
  private static final int LOCATOR_PARAMETER = 1;
  private final JdbcTemplate template;

  public PatientPostalLocatorHistoryPreviousVersionVerifier(JdbcTemplate template) {
    this.template = template;
  }

  boolean verify(final long locatorUid) {
    return !this.template
        .query(
            QUERY,
            statement -> statement.setLong(LOCATOR_PARAMETER, locatorUid),
            (resultSet, row) -> resultSet.getLong(RESULT_COLUMN))
        .isEmpty();
  }
}
