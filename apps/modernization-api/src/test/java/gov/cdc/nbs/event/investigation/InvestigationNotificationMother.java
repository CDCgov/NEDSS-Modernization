package gov.cdc.nbs.event.investigation;

import gov.cdc.nbs.testing.identity.SequentialIdentityGenerator;
import gov.cdc.nbs.testing.support.Active;
import gov.cdc.nbs.testing.support.Available;
import gov.cdc.nbs.time.FlexibleInstantConverter;
import io.cucumber.spring.ScenarioScope;
import jakarta.annotation.PostConstruct;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.time.Instant;
import java.util.List;
import java.util.Map;

@Component
@ScenarioScope
class InvestigationNotificationMother {
  private static final String CREATE = """
      insert into Act (
        act_uid,
        class_cd,
        mood_cd
      ) values (
        :identifier,
        'NOTF',
        'EVN'
      );

      insert into Act_relationship(
        source_act_uid,
        source_class_cd,
        target_act_uid,
        target_class_cd,
        type_cd,
        record_status_cd
      ) values (
        :identifier,
        'NOTF',
        :investigation,
        'CASE',
        'Notification',
        'ACTIVE'
      );

      insert into Notification(
        notification_uid,
        local_id,
        record_status_cd,
        add_time,
        shared_ind,
        version_ctrl_nbr
      ) values (
        :identifier,
        :local,
        :status,
        :on,
        'F',
        1
      );
      """;

  private static final String DELETE = """
      delete from CN_transportq_out where notification_uid in (:identifiers);
      delete from Notification where notification_uid in (:identifiers);
      delete from Act_relationship where source_act_uid in (:identifiers);
      delete from Act where act_uid in (:identifiers);
      """;

  private static final String CREATE_TRANSPORT_NOTIFICATION = """
      insert into CN_transportq_out (
      		notification_uid,
          notification_local_id,
      		record_status_cd
      	) values (
          :notificationId,
          :notificationLocalId,
          :processingStatus);
        """;

  private final SequentialIdentityGenerator idGenerator;
  private final NamedParameterJdbcTemplate template;
  private final Active<NotificationIdentifier> active;
  private final Available<NotificationIdentifier> available;

  InvestigationNotificationMother(
      final SequentialIdentityGenerator idGenerator,
      final NamedParameterJdbcTemplate template,
      final Active<NotificationIdentifier> active,
      final Available<NotificationIdentifier> available) {
    this.idGenerator = idGenerator;
    this.template = template;
    this.active = active;
    this.available = available;
  }

  @PostConstruct
  void reset() {

    List<Long> created = this.available.all()
        .map(NotificationIdentifier::identifier)
        .toList();

    if (!created.isEmpty()) {

      SqlParameterSource params = new MapSqlParameterSource()
          .addValue("identifiers", created);

      template.execute(
          DELETE,
          params,
          PreparedStatement::executeUpdate);
      this.active.reset();
    }
  }

  void create(
      final InvestigationIdentifier investigation,
      final String status,
      final Instant on) {
    long identifier = idGenerator.next();
    String local = idGenerator.nextLocal("NOTF");

    SqlParameterSource parameters = new MapSqlParameterSource(
        Map.of(
            "identifier", identifier,
            "investigation", investigation.identifier(),
            "local", local,
            "status", status,
            "on", FlexibleInstantConverter.toString(on)));

    template.execute(
        CREATE,
        parameters,
        PreparedStatement::executeUpdate);

    NotificationIdentifier created = new NotificationIdentifier(identifier, local);
    this.available.available(created);
    this.active.active(created);
  }

  void createTransportStatus(final String status) {
    SqlParameterSource parameters = new MapSqlParameterSource()
        .addValue("notificationId", active.active().identifier())
        .addValue("notificationLocalId", active.active().local())
        .addValue("processingStatus", status);
    template.execute(
        CREATE_TRANSPORT_NOTIFICATION,
        parameters,
        PreparedStatement::executeUpdate);
  }
}
