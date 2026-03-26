package gov.cdc.nbs.patient.profile.history;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class PatientPhoneLocatorHistoryPreviousVersionVerifier {
  private static final String QUERY =
      """
      select 1 from Tele_locator [tele_locator]
          join Tele_locator_hist [tele_locator_history] on
                  [tele_locator_history].[tele_locator_uid] = [tele_locator].[tele_locator_uid]
      where [tele_locator].tele_locator_uid = ?
      """;
  private static final int RESULT_COLUMN = 1;
  private static final int LOCATOR_PARAMETER = 1;
  private final JdbcTemplate template;

  public PatientPhoneLocatorHistoryPreviousVersionVerifier(JdbcTemplate template) {
    this.template = template;
  }

  public boolean verify(long locatorUid) {
    return !this.template
        .query(
            QUERY,
            statement -> statement.setLong(LOCATOR_PARAMETER, locatorUid),
            (resultSet, row) -> resultSet.getLong(RESULT_COLUMN))
        .isEmpty();
  }
}
