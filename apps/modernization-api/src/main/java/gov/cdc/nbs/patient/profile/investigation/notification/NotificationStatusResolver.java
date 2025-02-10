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
          top 1 t.record_status_cd
      FROM
          Act_relationship ar
          JOIN Public_health_case phc on ar.target_act_uid = phc.public_health_case_uid
          JOIN CN_transportq_out t on t.notification_uid = ar.source_act_uid
      WHERE
          target_act_uid = :investigationId
          AND type_cd = 'Notification';
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
