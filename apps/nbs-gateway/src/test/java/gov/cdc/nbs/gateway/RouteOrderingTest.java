package gov.cdc.nbs.gateway;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class RouteOrderingTest {

  @Test
  void should_return_ordering_to_apply_after_the_order() {
    RouteOrdering ordering = RouteOrdering.NBS_6;

    assertThat(ordering.after()).isGreaterThan(ordering.order());
  }

  @Test
  void should_return_ordering_to_apply_before_the_order() {
    RouteOrdering ordering = RouteOrdering.NBS_6;

    assertThat(ordering.before()).isLessThan(ordering.order());
  }
}
