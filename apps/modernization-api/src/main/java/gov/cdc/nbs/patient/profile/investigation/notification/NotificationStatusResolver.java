package gov.cdc.nbs.patient.profile.investigation.notification;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

@Component
class NotificationStatusResolver {

  private final NamedParameterJdbcTemplate template;
  private final NotificationStatusMapper mapper = new NotificationStatusMapper();

  NotificationStatusResolver(NamedParameterJdbcTemplate template) {
    this.template = template;
  }

  private static final String QUERY =
      """
      SELECT
          top 1 t.record_status_cd status,
          t.notification_local_id localId
      FROM
          Act_relationship ar
          JOIN Public_health_case phc on ar.target_act_uid = phc.public_health_case_uid
          JOIN CN_transportq_out t on t.notification_uid = ar.source_act_uid
      WHERE
          target_act_uid = :investigationId
          AND type_cd = 'Notification';
      """;

  public NotificationStatus resolve(String investigationId) {
    SqlParameterSource params =
        new MapSqlParameterSource().addValue("investigationId", investigationId);
    try {
      var statusList = template.query(QUERY, params, mapper);
      if (statusList.isEmpty()) {
        return new NotificationStatus(null, null);
      }
      return statusList.get(0);
    } catch (EmptyResultDataAccessException e) {
      return new NotificationStatus(null, null);
    }
  }

  private class NotificationStatusMapper implements RowMapper<NotificationStatus> {

    @Override
    public NotificationStatus mapRow(ResultSet rs, int rowNum) throws SQLException {
      return new NotificationStatus(rs.getString("status"), rs.getString("localId"));
    }
  }
}
