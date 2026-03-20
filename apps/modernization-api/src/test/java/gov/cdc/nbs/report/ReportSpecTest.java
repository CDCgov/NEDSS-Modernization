package gov.cdc.nbs.report;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import gov.cdc.nbs.report.models.ReportSpec;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class ReportSpecTest {
  @Test
  void should_create_report_spec_with_no_time_range() {
    ReportSpec reportSpec =
        new ReportSpec(
            1,
            true,
            true,
            "Test Report",
            "nbs_custom",
            "nbs_rdb.investigation",
            "SELECT * FROM [NBS_ODSE].[dbo].[NBS_configuration]",
            null);

    assertThat(reportSpec.version()).isEqualTo(1);
    assertThat(reportSpec.isBuiltin()).isTrue();
    assertThat(reportSpec.isExport()).isTrue();
    assertThat(reportSpec.reportTitle()).isEqualTo("Test Report");
    assertThat(reportSpec.libraryName()).isEqualTo("nbs_custom");
    assertThat(reportSpec.dataSourceName()).isEqualTo("nbs_rdb.investigation");
    assertThat(reportSpec.subsetQuery())
        .isEqualTo("SELECT * FROM [NBS_ODSE].[dbo].[NBS_configuration]");
  }

  @Test
  void should_create_report_spec_with_time_range() {
    Map<String, LocalDate> timeRange = new HashMap<>();
    timeRange.put("start", LocalDate.parse("1999-01-01"));
    timeRange.put("end", LocalDate.parse("1999-01-10"));

    ReportSpec reportSpec =
        new ReportSpec(
            1,
            true,
            true,
            "Test Report",
            "nbs_custom",
            "nbs_rdb.investigation",
            "SELECT * FROM [NBS_ODSE].[dbo].[NBS_configuration]",
            timeRange);

    assertThat(reportSpec.version()).isEqualTo(1);
    assertThat(reportSpec.isBuiltin()).isTrue();
    assertThat(reportSpec.isExport()).isTrue();
    assertThat(reportSpec.reportTitle()).isEqualTo("Test Report");
    assertThat(reportSpec.libraryName()).isEqualTo("nbs_custom");
    assertThat(reportSpec.dataSourceName()).isEqualTo("nbs_rdb.investigation");
    assertThat(reportSpec.subsetQuery())
        .isEqualTo("SELECT * FROM [NBS_ODSE].[dbo].[NBS_configuration]");
    assertThat(reportSpec.timeRange()).containsEntry("start", LocalDate.parse("1999-01-01"));
    assertThat(reportSpec.timeRange()).containsEntry("end", LocalDate.parse("1999-01-10"));
    assertThat(reportSpec.timeRange()).hasSize(2);
  }

  @ParameterizedTest
  @MethodSource("invalidTimeRangeProvider")
  void should_throw_exception_with_invalid_time_range(Map<String, LocalDate> timeRange) {
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
                    timeRange))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("time_range must contain 'start' and 'end' keys");
  }

  private static Stream<Map<String, LocalDate>> invalidTimeRangeProvider() {
    Map<String, LocalDate> timeRange1 = new HashMap<>();
    timeRange1.put("effective_time", LocalDate.parse("1999-01-10"));

    Map<String, LocalDate> timeRange2 = new HashMap<>();
    timeRange2.put("start", LocalDate.parse("1999-01-01"));
    timeRange2.put("effective_time", LocalDate.parse("1999-01-10"));

    Map<String, LocalDate> timeRange3 = new HashMap<>();
    timeRange3.put("end", LocalDate.parse("1999-01-01"));
    timeRange3.put("effective_time", LocalDate.parse("1999-01-10"));

    return Stream.of(timeRange1, timeRange2, timeRange3);
  }
}
