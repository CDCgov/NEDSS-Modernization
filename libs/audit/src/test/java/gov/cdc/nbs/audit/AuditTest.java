package gov.cdc.nbs.audit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class AuditTest {

  @Test
  void should_initialize_added() {

    Audit actual = new Audit(311L, LocalDateTime.parse("2021-01-17T01:45:30"), "reason-value");

    assertThat(actual.added())
        .describedAs("added is initialized on creation")
        .satisfies(
            added ->
                assertAll(
                    () -> assertThat(added.addedBy()).isEqualTo(311L),
                    () -> assertThat(added.addedOn()).isEqualTo("2021-01-17T01:45:30"),
                    () -> assertThat(added.reason()).isEqualTo("reason-value")));
  }

  @Test
  void should_initialize_changed() {

    Audit actual = new Audit(311L, LocalDateTime.parse("2021-01-17T01:45:30"), "reason-value");

    assertThat(actual.changed())
        .describedAs("changed is initialized on creation")
        .satisfies(
            changed ->
                assertAll(
                    () -> assertThat(changed.changedBy()).isEqualTo(311L),
                    () -> assertThat(changed.changedOn()).isEqualTo("2021-01-17T01:45:30"),
                    () -> assertThat(changed.reason()).isNull()));
  }

  @Test
  void should_audit_change() {

    Audit actual = new Audit(311L, LocalDateTime.parse("2021-01-17T01:45:30"), "reason-value");

    actual.changed(701L, LocalDateTime.parse("2022-03-19T00:00:00"), "changed");

    assertThat(actual.added())
        .describedAs("added is not affected by change")
        .satisfies(
            added ->
                assertAll(
                    () -> assertThat(added.addedBy()).isEqualTo(311L),
                    () -> assertThat(added.addedOn()).isEqualTo("2021-01-17T01:45:30"),
                    () -> assertThat(added.reason()).isEqualTo("reason-value")));

    assertThat(actual.changed())
        .describedAs("the change is audited")
        .satisfies(
            changed ->
                assertAll(
                    () -> assertThat(changed.changedBy()).isEqualTo(701L),
                    () -> assertThat(changed.changedOn()).isEqualTo("2022-03-19T00:00:00"),
                    () -> assertThat(changed.reason()).isEqualTo("changed")));
  }
}
