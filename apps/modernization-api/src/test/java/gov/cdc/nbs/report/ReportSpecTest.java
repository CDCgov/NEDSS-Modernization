package gov.cdc.nbs.report;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ReportSpecTest {
  @Test
  void should_throw_exception_with_invalid_time_range() {
    Map<String, LocalDate> invalidTimeRange = new HashMap<>();
    invalidTimeRange.put("effective_time", LocalDate.parse("1999-01-01"));

    assertThatThrownBy(
            () ->
                new ReportSpec(
                    1,
                    true,
                    true,
                    "Test Report",
                    "nbs_custom",
                    "nbs_rdb.investigation",
                    "SELECT * FROM [NBS_ODSE].[dbo].[NBS_configuration]",
                    invalidTimeRange))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("time_range must contain 'start' and 'end' keys");
  }
}
