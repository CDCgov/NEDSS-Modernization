package gov.cdc.nbs.authentication.entity;

import static org.assertj.core.api.Assertions.assertThat;

import gov.cdc.nbs.authentication.enums.AuthRecordStatus;
import java.time.Instant;
import org.junit.jupiter.api.Test;

class AuthAuditTest {

  @Test
  void should_be_created_with_same_add_and_change_user() {

    AuthAudit actual = new AuthAudit(1139L, Instant.parse("2023-07-14T01:02:03Z"));

    assertThat(actual)
        .returns(1139L, AuthAudit::addedBy)
        .returns(Instant.parse("2023-07-14T01:02:03Z"), AuthAudit::addedOn)
        .returns(1139L, AuthAudit::changedBy)
        .returns(Instant.parse("2023-07-14T01:02:03Z"), AuthAudit::changedOn);
  }

  @Test
  void should_default_to_active() {

    AuthAudit actual = new AuthAudit(1139L, Instant.parse("2023-07-14T01:02:03Z"));

    assertThat(actual)
        .returns(AuthRecordStatus.ACTIVE, AuthAudit::recordStatus)
        .returns(Instant.parse("2023-07-14T01:02:03Z"), AuthAudit::recordStatusChangedOn);
  }

  @Test
  void should_inactivate() {

    AuthAudit initial = new AuthAudit(1139L, Instant.parse("2023-07-14T01:02:03Z"));

    AuthAudit actual = initial.inactivate(Instant.parse("2024-03-19T05:11:00Z"));

    assertThat(actual)
        .returns(AuthRecordStatus.INACTIVE, AuthAudit::recordStatus)
        .returns(Instant.parse("2024-03-19T05:11:00Z"), AuthAudit::recordStatusChangedOn);
  }

  @Test
  void should_copy_the_given_audit() {

    AuthAudit initial = new AuthAudit(1139L, Instant.parse("2023-07-14T01:02:03Z"));

    AuthAudit actual = new AuthAudit(initial);

    assertThat(actual)
        .returns(1139L, AuthAudit::addedBy)
        .returns(Instant.parse("2023-07-14T01:02:03Z"), AuthAudit::addedOn)
        .returns(1139L, AuthAudit::changedBy)
        .returns(Instant.parse("2023-07-14T01:02:03Z"), AuthAudit::changedOn)
        .returns(AuthRecordStatus.ACTIVE, AuthAudit::recordStatus)
        .returns(Instant.parse("2023-07-14T01:02:03Z"), AuthAudit::recordStatusChangedOn);
  }
}
