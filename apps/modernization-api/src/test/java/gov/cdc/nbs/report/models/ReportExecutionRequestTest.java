package gov.cdc.nbs.report.models;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class ReportExecutionRequestTest {

  @Test
  void should_throw_exception_with_time_range_missing_start_date() {
    String end = "1999-12-31";

    assertThatThrownBy(() -> new ReportExecutionRequest.TimeRange(null, end))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("time_range must contain 'start' and 'end' keys");
  }

  @Test
  void should_throw_exception_with_time_range_missing_end_date() {
    String start = "1999-01-01";

    assertThatThrownBy(() -> new ReportExecutionRequest.TimeRange(start, null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("time_range must contain 'start' and 'end' keys");
  }

  @Test
  void should_throw_exception_with_time_range_start_using_non_iso_format() {
    String invalidStart = "01-Jan-1999";
    String end = "1999-12-31";

    assertThatThrownBy(() -> new ReportExecutionRequest.TimeRange(invalidStart, end))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Start and end dates must be in the format YYYY-MM-DD");
  }

  @Test
  void should_throw_exception_with_time_range_end_using_non_iso_format() {
    String start = "1999-01-01";
    String invalidEnd = "31-Dec-1999";

    assertThatThrownBy(() -> new ReportExecutionRequest.TimeRange(start, invalidEnd))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Start and end dates must be in the format YYYY-MM-DD");
  }
}
