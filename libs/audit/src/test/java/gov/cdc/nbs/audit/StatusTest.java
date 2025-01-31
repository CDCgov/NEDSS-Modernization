package gov.cdc.nbs.audit;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class StatusTest {

  @Test
  void should_default_to_A() {
    Status actual = new Status(LocalDateTime.parse("2021-01-17T01:45:30"));

    assertThat(actual.status()).isEqualTo('A');
    assertThat(actual.appliedOn()).isEqualTo("2021-01-17T01:45:30");
  }
}
