package gov.cdc.nbs.report.models;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.Test;

class ReportSpecTest {

  @Test
  void should_create_report_spec() {
    Map<String, String> sortBy = Map.of("column_name", "columnName", "direction", "ASC");
    Integer daysValue = 11;
    String libraryParams = "{\"reportDays\": \"30\"}";

    ReportSpec reportSpec =
        new ReportSpec(
            true,
            true,
            "Test Report",
            "nbs_custom",
            "nbs_rdb.investigation",
            "SELECT * FROM [NBS_ODSE].[dbo].[NBS_configuration]",
            null,
            sortBy,
            daysValue,
            libraryParams);

    assertThat(reportSpec.isBuiltin()).isTrue();
    assertThat(reportSpec.isExport()).isTrue();
    assertThat(reportSpec.reportTitle()).isEqualTo("Test Report");
    assertThat(reportSpec.libraryName()).isEqualTo("nbs_custom");
    assertThat(reportSpec.dataSourceName()).isEqualTo("nbs_rdb.investigation");
    assertThat(reportSpec.subsetQuery())
        .isEqualTo("SELECT * FROM [NBS_ODSE].[dbo].[NBS_configuration]");
    assertThat(reportSpec.sortBy())
        .isNotNull()
        .containsEntry("column_name", "columnName")
        .containsEntry("direction", "ASC");
    assertThat(reportSpec.daysValue()).isEqualTo(daysValue);
    assertThat(reportSpec.libraryParams()).isEqualTo(libraryParams);
  }
}
