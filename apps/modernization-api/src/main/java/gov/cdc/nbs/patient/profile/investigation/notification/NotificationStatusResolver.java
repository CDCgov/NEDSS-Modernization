package gov.cdc.nbs.patient.profile.investigation.notification;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

@Component
class NotificationStatusResolver {

  private final NamedParameterJdbcTemplate template;

  NotificationStatusResolver(NamedParameterJdbcTemplate template) {
    this.template = template;
  }

  private static final String QUERY = """
      SELECT
          processingStatus
      FROM
          NBS_MSGOUTE.dbo.TransportQ_out
      WHERE
          messageId = :notificationId
            """;

  public NotificationStatus resolve(String notificationId) {
    SqlParameterSource params = new MapSqlParameterSource()
        .addValue("notificationId", notificationId);
    try {
      return new NotificationStatus(template.queryForObject(QUERY, params, String.class));
    } catch (EmptyResultDataAccessException e) {
      return new NotificationStatus(null);
    }
  }

}
