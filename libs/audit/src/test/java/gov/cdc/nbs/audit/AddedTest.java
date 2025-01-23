package gov.cdc.nbs.audit;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class AddedTest {

  @Test
  void should_use_default_reason_when_not_specified() {

    Added actual = new Added(311L,
        LocalDateTime.parse("2021-01-17T01:45:30")
    );

    assertThat(actual.reason()).isEqualTo("Add");

  }
}
