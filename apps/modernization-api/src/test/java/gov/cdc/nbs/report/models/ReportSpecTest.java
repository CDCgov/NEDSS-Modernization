package gov.cdc.nbs.report.models;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
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
    String start = "1999-01-01";
    String end = "1999-01-10";

    ReportSpec.TimeRange timeRange =
        new ReportSpec.TimeRange(LocalDate.parse(start), LocalDate.parse(end));

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
    assertThat(reportSpec.timeRange().start().equals(LocalDate.parse(start)));
    assertThat(reportSpec.timeRange().end().equals(LocalDate.parse(end)));
  }

  @ParameterizedTest
  @MethodSource("invalidTimeRangeProvider")
  void should_throw_exception_with_invalid_time_range(ReportSpec.TimeRange timeRange) {
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
        .isInstanceOf(IllegalArgumentException.class);
  }

  private static Stream<ReportSpec.TimeRange> invalidTimeRangeProvider() {
    ReportSpec.TimeRange timeRange1 = new ReportSpec.TimeRange(LocalDate.parse("1999-01-01"), null);
    ReportSpec.TimeRange timeRange2 = new ReportSpec.TimeRange(null, LocalDate.parse("1999-01-10"));
    ReportSpec.TimeRange timeRange3 =
        new ReportSpec.TimeRange(LocalDate.parse("1999-01-10"), LocalDate.parse("1999-01-01"));

    return Stream.of(timeRange1, timeRange2, timeRange3);
  }
}
