package gov.cdc.nbs.audit;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class RecordStatusTest {

  @Test
  void should_default_to_ACTIVE() {

    RecordStatus actual = new RecordStatus(Instant.parse("2021-01-17T01:45:30Z"));

    assertThat(actual.status()).isEqualTo("ACTIVE");
    assertThat(actual.appliedOn()).isEqualTo("2021-01-17T01:45:30Z");

  }
}
