package gov.cdc.nbs.patient.file.history;

import gov.cdc.nbs.testing.support.Available;
import io.cucumber.spring.ScenarioScope;
import jakarta.annotation.PreDestroy;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Component
@ScenarioScope
class PatientMergeMother {

  private static final String INSERT = """
      INSERT INTO person_merge (
          superced_person_uid,
          superceded_version_ctrl_nbr,
          superceded_parent_uid,
          surviving_person_uid,
          surviving_version_ctrl_nbr,
          surviving_parent_uid,
          record_status_cd,
          record_status_time,
          merge_user_id,
          merge_time
      )
      VALUES (
          :supersededId,
          :supersededVersion,
          :supersededParent,
          :survivingId,
          :survivingVersion,
          :survivingParent,
          :status,
          :statusTime,
          :userId,
          :mergedAt
      )
      """;

  private static final String DELETE = """
      DELETE FROM person_merge
      WHERE surviving_person_uid IN (:survivors)
      """;

  private final JdbcClient client;
  private final Available<Long> available;

  PatientMergeMother(final JdbcClient client) {
    this.client = client;
    this.available = new Available<>();
  }

  void createMerge(
      long survivingId,
      int survivingVersion,
      Long survivingParent,
      long supersededId,
      int supersededVersion,
      Long supersededParent,
      long userId,
      LocalDateTime mergedAt
  ) {
    client.sql(INSERT)
        .param("supersededId", supersededId)
        .param("supersededVersion", supersededVersion)
        .param("supersededParent", supersededParent)
        .param("survivingId", survivingId)
        .param("survivingVersion", survivingVersion)
        .param("survivingParent", survivingParent)
        .param("status", "ACTIVE")
        .param("statusTime", Timestamp.valueOf(mergedAt))
        .param("userId", userId)
        .param("mergedAt", Timestamp.valueOf(mergedAt))
        .update();

    available.available(survivingId);
  }

  void remove(long survivingId) {
    client.sql(DELETE)
        .param("survivors", List.of(survivingId))
        .update();
  }

  @PreDestroy
  void reset() {
    var survivors = available.all().toList();
    if (!survivors.isEmpty()) {
      client.sql(DELETE)
          .param("survivors", survivors)
          .update();
      available.reset();
    }
  }
}
