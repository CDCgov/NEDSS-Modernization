package gov.cdc.nbs.event.search;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class RelevanceResolverTest {

  @Test
  void should_resolve_from_score() {
    double relevance = RelevanceResolver.resolve(63.1d);

    assertThat(relevance).isEqualTo(63.1d);
  }

  @Test
  void should_resolve_zero_when_null() {
    double relevance = RelevanceResolver.resolve(null);

    assertThat(relevance).isZero();
  }

  @Test
  void should_resolve_negative_one_when_NaN() {
    double relevance = RelevanceResolver.resolve(Double.NaN);

    assertThat(relevance).isNegative();
  }
}
