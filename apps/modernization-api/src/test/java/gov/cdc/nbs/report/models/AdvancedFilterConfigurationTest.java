package gov.cdc.nbs.report.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class AdvancedFilterConfigurationTest {
  @Test
  void should_throw_if_default_value_and_query_are_both_omitted() {
    assertThrows(
        IllegalArgumentException.class, () -> new AdvancedFilterConfiguration(1L, null, null));
    assertThrows(
        IllegalArgumentException.class, () -> new AdvancedFilterConfiguration(2L, null, ""));
  }
}
