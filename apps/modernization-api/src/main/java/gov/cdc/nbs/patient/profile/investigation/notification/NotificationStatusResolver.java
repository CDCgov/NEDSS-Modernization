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
          TOP 1 t.processingStatus
      FROM
          Act_relationship ar
          JOIN Notification n ON n.notification_uid = ar.source_act_uid
          JOIN NBS_MSGOUTE.dbo.TransportQ_out t ON t.messageId = n.local_id
      WHERE
          ar.target_act_uid = :investigationId
          AND ar.type_cd = 'Notification'
      ORDER BY
          t.messageCreationTime DESC;
                  """;

  public NotificationStatus resolve(String investigationId) {
    SqlParameterSource params = new MapSqlParameterSource()
        .addValue("investigationId", investigationId);
    try {
      return new NotificationStatus(template.queryForObject(QUERY, params, String.class));
    } catch (EmptyResultDataAccessException e) {
      return new NotificationStatus(null);
    }
  }

}
