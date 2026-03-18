package gov.cdc.nbs.report;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
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
    assertThat(reportSpec.isBuiltin()).isEqualTo(true);
    assertThat(reportSpec.isExport()).isEqualTo(true);
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
    assertThat(reportSpec.isBuiltin()).isEqualTo(true);
    assertThat(reportSpec.isExport()).isEqualTo(true);
    assertThat(reportSpec.reportTitle()).isEqualTo("Test Report");
    assertThat(reportSpec.libraryName()).isEqualTo("nbs_custom");
    assertThat(reportSpec.dataSourceName()).isEqualTo("nbs_rdb.investigation");
    assertThat(reportSpec.subsetQuery())
        .isEqualTo("SELECT * FROM [NBS_ODSE].[dbo].[NBS_configuration]");
    assertThat(reportSpec.timeRange().get("start")).isEqualTo(LocalDate.parse("1999-01-01"));
    assertThat(reportSpec.timeRange().get("end")).isEqualTo(LocalDate.parse("1999-01-10"));
    assertThat(reportSpec.timeRange().size()).isEqualTo(2);
  }

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
