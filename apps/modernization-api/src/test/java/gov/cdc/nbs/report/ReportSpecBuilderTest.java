package gov.cdc.nbs.report;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import gov.cdc.nbs.report.models.FilterConfiguration;
import gov.cdc.nbs.report.models.ReportConfiguration;
import gov.cdc.nbs.report.models.ReportExecutionRequest;
import gov.cdc.nbs.report.models.ReportSpec;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReportSpecBuilderTest {

  private FilterConfiguration mockFilterConfiguration(
      Long columnId, String columnName, String columnTitle) {
    FilterConfiguration filterConfig = Mockito.mock(FilterConfiguration.class);

    gov.cdc.nbs.report.models.DataSourceColumn dataSourceColumn =
        Mockito.mock(gov.cdc.nbs.report.models.DataSourceColumn.class);
    Mockito.lenient().when(dataSourceColumn.id()).thenReturn(columnId);
    Mockito.lenient().when(dataSourceColumn.columnName()).thenReturn(columnName);
    Mockito.lenient().when(dataSourceColumn.columnTitle()).thenReturn(columnTitle);

    Mockito.lenient().when(filterConfig.dataSourceColumn()).thenReturn(dataSourceColumn);

    return filterConfig;
  }

  private ReportConfiguration mockReportConfiguration(List<FilterConfiguration> filters) {
    ReportConfiguration reportConfiguration = Mockito.mock(ReportConfiguration.class);

    Mockito.lenient().when(reportConfiguration.filters()).thenReturn(filters);

    return reportConfiguration;
  }

  private ReportExecutionRequest mockReportExecutionRequest(List<Long> columnUids) {
    ReportExecutionRequest request = Mockito.mock(ReportExecutionRequest.class);

    Mockito.lenient().when(request.columnUids()).thenReturn(columnUids);

    return request;
  }

  @Test
  void should_build_hardcoded_report_spec() {
    FilterConfiguration filterConfig1 = mockFilterConfiguration(1L, "column1", "Column 1");
    FilterConfiguration filterConfig2 = mockFilterConfiguration(2L, "column2", "Column 2");
    ReportConfiguration reportConfig =
        mockReportConfiguration(List.of(filterConfig1, filterConfig2));

    List<Long> columnUids = List.of(1L, 2L);
    ReportExecutionRequest request = mockReportExecutionRequest(columnUids);

    ReportSpec reportSpec = new ReportSpecBuilder(request, reportConfig).build();

    assertThat(reportSpec.version()).isEqualTo(1);
    assertThat(reportSpec.isBuiltin()).isTrue();
    assertThat(reportSpec.isExport()).isTrue();
    assertThat(reportSpec.reportTitle()).isEqualTo("Test Report");
    assertThat(reportSpec.libraryName()).isEqualTo("nbs_custom");
    assertThat(reportSpec.dataSourceName()).isEqualTo("nbs_rdb.investigation");
    assertThat(reportSpec.subsetQuery())
        .isEqualTo(
            "SELECT [column1] AS \"Column 1\", [column2] AS \"Column 2\" FROM [NBS_ODSE].[dbo].[NBS_configuration]");
  }

  @Test
  void setColumns_should_throw_illegal_argument_when_columns_not_found() {
    FilterConfiguration filterConfig1 = mockFilterConfiguration(1L, "column1", "Column 1");
    ReportConfiguration reportConfig = mockReportConfiguration(List.of(filterConfig1));

    List<Long> columnUids = List.of(1L, 2L);
    ReportExecutionRequest request = mockReportExecutionRequest(columnUids);

    assertThatThrownBy(() -> new ReportSpecBuilder(request, reportConfig).build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("No filter column found for columnUid 2");
  }

  @Test
  void build_should_generate_correct_select_clause_for_single_column() {
    FilterConfiguration filterConfig1 = mockFilterConfiguration(1L, "column1", "Column 1");
    ReportConfiguration reportConfig = mockReportConfiguration(List.of(filterConfig1));

    List<Long> columnUids = List.of(1L);
    ReportExecutionRequest request = mockReportExecutionRequest(columnUids);

    ReportSpec reportSpec = new ReportSpecBuilder(request, reportConfig).build();

    assertThat(reportSpec.subsetQuery())
        .isEqualTo("SELECT [column1] AS \"Column 1\" FROM [NBS_ODSE].[dbo].[NBS_configuration]");
  }

  @Test
  void build_should_generate_correct_select_clause_for_multiple_columns() {
    FilterConfiguration filterConfig1 = mockFilterConfiguration(1L, "col1", "Col 1");
    FilterConfiguration filterConfig2 = mockFilterConfiguration(2L, "col2", "Col 2");
    FilterConfiguration filterConfig3 = mockFilterConfiguration(3L, "col3", "Col 3");
    ReportConfiguration reportConfig =
        mockReportConfiguration(List.of(filterConfig1, filterConfig2, filterConfig3));

    List<Long> columnUids = List.of(1L, 2L, 3L);
    ReportExecutionRequest request = mockReportExecutionRequest(columnUids);

    ReportSpec reportSpec = new ReportSpecBuilder(request, reportConfig).build();

    assertThat(reportSpec.subsetQuery())
        .isEqualTo(
            "SELECT [col1] AS \"Col 1\", [col2] AS \"Col 2\", [col3] AS \"Col 3\" FROM [NBS_ODSE].[dbo].[NBS_configuration]");
  }

  @Test
  void build_should_generate_correct_select_clause_for_no_columns() {
    FilterConfiguration filterConfig1 = mockFilterConfiguration(1L, "col1", "Col 1");
    FilterConfiguration filterConfig2 = mockFilterConfiguration(2L, "col2", "Col 2");
    ReportConfiguration reportConfig =
        mockReportConfiguration(List.of(filterConfig1, filterConfig2));

    ReportExecutionRequest request = mockReportExecutionRequest(null);

    ReportSpec reportSpec = new ReportSpecBuilder(request, reportConfig).build();

    assertThat(reportSpec.subsetQuery())
        .isEqualTo("SELECT * FROM [NBS_ODSE].[dbo].[NBS_configuration]");
  }

  @Test
  void build_should_generate_correct_select_clause_for_column_names_with_spaces() {
    FilterConfiguration filterConfig1 = mockFilterConfiguration(1L, "first column", "Column 1");
    ReportConfiguration reportConfig = mockReportConfiguration(List.of(filterConfig1));

    List<Long> columnUids = List.of(1L);
    ReportExecutionRequest request = mockReportExecutionRequest(columnUids);

    ReportSpec reportSpec = new ReportSpecBuilder(request, reportConfig).build();

    assertThat(reportSpec.subsetQuery())
        .isEqualTo(
            "SELECT [first column] AS \"Column 1\" FROM [NBS_ODSE].[dbo].[NBS_configuration]");
  }

  @Test
  void build_should_generate_correct_select_clause_for_column_names_with_keywords() {
    FilterConfiguration filterConfig1 = mockFilterConfiguration(1L, "user", "User Column");
    ReportConfiguration reportConfig = mockReportConfiguration(List.of(filterConfig1));

    List<Long> columnUids = List.of(1L);
    ReportExecutionRequest request = mockReportExecutionRequest(columnUids);

    ReportSpec reportSpec = new ReportSpecBuilder(request, reportConfig).build();

    assertThat(reportSpec.subsetQuery())
        .isEqualTo("SELECT [user] AS \"User Column\" FROM [NBS_ODSE].[dbo].[NBS_configuration]");
  }
}
