package gov.cdc.nbs.report.models;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

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

  @Test
  void should_throw_exception_with_time_range_missing_start_date() {
    assertThatThrownBy(() -> new ReportSpec.TimeRange(null, LocalDate.parse("1999-12-31")))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Start and end values are required");
  }

  @Test
  void should_throw_exception_with_time_range_missing_end_date() {
    assertThatThrownBy(() -> new ReportSpec.TimeRange(LocalDate.parse("1999-01-01"), null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Start and end values are required");
  }

  @Test
  void should_throw_exception_with_time_range_end_before_start() {
    assertThatThrownBy(
            () ->
                new ReportSpec.TimeRange(
                    LocalDate.parse("1999-12-31"), LocalDate.parse("1999-01-01")))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Start date cannot be after end date");
  }
}
