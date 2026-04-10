package gov.cdc.nbs.report;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import gov.cdc.nbs.report.models.FilterConfiguration;
import gov.cdc.nbs.report.models.Library;
import gov.cdc.nbs.report.models.ReportColumn;
import gov.cdc.nbs.report.models.ReportConfiguration;
import gov.cdc.nbs.report.models.ReportDataSource;
import gov.cdc.nbs.report.models.ReportExecutionRequest;
import gov.cdc.nbs.report.models.ReportSpec;
import gov.cdc.nbs.report.utils.DataSourceNameUtils;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReportSpecBuilderTest {

  private DataSourceNameUtils mockDataSourceNameUtils() {
    DataSourceNameUtils dataSourceNameUtils = Mockito.mock(DataSourceNameUtils.class);
    when(dataSourceNameUtils.buildDataSourceName("nbs_ods.NBS_configuration"))
        .thenReturn("[NBS_ODSE].[dbo].[NBS_configuration]");
    return dataSourceNameUtils;
  }

  private FilterConfiguration mockFilterConfiguration(Long columnId) {
    FilterConfiguration filterConfig = Mockito.mock(FilterConfiguration.class);

    Mockito.lenient().when(filterConfig.reportColumnUid()).thenReturn(columnId);

    return filterConfig;
  }

  private ReportColumn mockReportColumn(Long columnId, String columnName, String columnTitle) {
    ReportColumn reportColumn = Mockito.mock(ReportColumn.class);

    Mockito.lenient().when(reportColumn.id()).thenReturn(columnId);
    Mockito.lenient().when(reportColumn.columnName()).thenReturn(columnName);
    Mockito.lenient().when(reportColumn.columnTitle()).thenReturn(columnTitle);

    return reportColumn;
  }

  private ReportConfiguration mockReportConfiguration(
      List<FilterConfiguration> filters, List<ReportColumn> columns) {
    ReportConfiguration reportConfiguration = Mockito.mock(ReportConfiguration.class);

    DataSourceNameConfiguration dataSourceNameConfiguration =
        Mockito.mock(DataSourceNameConfiguration.class);
    Mockito.lenient().when(dataSourceNameConfiguration.getMappings()).thenReturn(new HashMap<>());

    Library library = Mockito.mock(Library.class);
    Mockito.lenient().when(reportConfiguration.reportLibrary()).thenReturn(library);
    Mockito.lenient().when(library.libraryName()).thenReturn("nbs_custom");

    ReportDataSource dataSource = Mockito.mock(ReportDataSource.class);
    Mockito.lenient().when(reportConfiguration.dataSource()).thenReturn(dataSource);
    Mockito.lenient().when(dataSource.name()).thenReturn("nbs_ods.NBS_configuration");

    Mockito.lenient().when(reportConfiguration.filters()).thenReturn(filters);
    Mockito.lenient().when(reportConfiguration.reportColumns()).thenReturn(columns);

    return reportConfiguration;
  }

  private ReportExecutionRequest mockReportExecutionRequest(List<Long> columnUids) {
    ReportExecutionRequest request = Mockito.mock(ReportExecutionRequest.class);

    Mockito.lenient().when(request.columnUids()).thenReturn(columnUids);
    Mockito.lenient().when(request.reportTitle()).thenReturn("Test Title");

    return request;
  }

  @Test
  void build_should_set_all_fields_correctly() {
    Long columnUid1 = 1L;
    Long columnUid2 = 2L;

    FilterConfiguration filterConfig1 = mockFilterConfiguration(columnUid1);
    FilterConfiguration filterConfig2 = mockFilterConfiguration(columnUid2);

    ReportColumn reportColumn1 = mockReportColumn(columnUid1, "column1", "Column 1");
    ReportColumn reportColumn2 = mockReportColumn(columnUid2, "column2", "Column 2");

    ReportConfiguration reportConfig =
        mockReportConfiguration(
            List.of(filterConfig1, filterConfig2), List.of(reportColumn1, reportColumn2));

    List<Long> columnUids = List.of(columnUid1, columnUid2);
    ReportExecutionRequest request = mockReportExecutionRequest(columnUids);

    DataSourceNameUtils dataSourceNameUtils = mockDataSourceNameUtils();

    ReportSpec reportSpec =
        new ReportSpecBuilder(request, reportConfig, dataSourceNameUtils).build();

    assertThat(reportSpec.version()).isEqualTo(reportConfig.reportLibrary().version());
    assertThat(reportSpec.isBuiltin()).isEqualTo(reportConfig.reportLibrary().isBuiltin());
    assertThat(reportSpec.isExport()).isEqualTo(request.isExport());
    assertThat(reportSpec.reportTitle()).isEqualTo(request.reportTitle());
    assertThat(reportSpec.libraryName()).isEqualTo(reportConfig.reportLibrary().libraryName());
    assertThat(reportSpec.dataSourceName()).isEqualTo("[NBS_ODSE].[dbo].[NBS_configuration]");
    assertThat(reportSpec.subsetQuery())
        .isEqualTo(
            "SELECT [column1] AS [Column 1], [column2] AS [Column 2] FROM [NBS_ODSE].[dbo].[NBS_configuration]");
  }

  @Test
  void setColumns_should_throw_illegal_argument_when_columns_not_found() {
    Long knownColumnUid = 1L;
    Long unknownColumnUid = 2L;

    FilterConfiguration filterConfig1 = mockFilterConfiguration(knownColumnUid);
    ReportColumn reportColumn1 = mockReportColumn(knownColumnUid, "column1", "Column 1");

    ReportConfiguration reportConfig =
        mockReportConfiguration(List.of(filterConfig1), List.of(reportColumn1));

    List<Long> columnUids = List.of(knownColumnUid, unknownColumnUid);
    ReportExecutionRequest request = mockReportExecutionRequest(columnUids);

    DataSourceNameUtils dataSourceNameUtils = mockDataSourceNameUtils();

    ReportSpecBuilder reportSpecBuilder =
        new ReportSpecBuilder(request, reportConfig, dataSourceNameUtils);

    assertThatThrownBy(reportSpecBuilder::build)
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("No report column found for columnUid 2");
  }

  @Test
  void build_should_generate_correct_select_clause_for_multiple_columns() {
    Long columnUid1 = 1L;
    Long columnUid2 = 2L;
    Long columnUid3 = 3L;

    FilterConfiguration filterConfig1 = mockFilterConfiguration(columnUid1);
    FilterConfiguration filterConfig2 = mockFilterConfiguration(columnUid2);
    FilterConfiguration filterConfig3 = mockFilterConfiguration(columnUid3);

    ReportColumn reportColumn1 = mockReportColumn(columnUid1, "col1", "Col 1");
    ReportColumn reportColumn2 = mockReportColumn(columnUid2, "col2", "Col 2");
    ReportColumn reportColumn3 = mockReportColumn(columnUid3, "col3", "Col 3");

    ReportConfiguration reportConfig =
        mockReportConfiguration(
            List.of(filterConfig1, filterConfig2, filterConfig3),
            List.of(reportColumn1, reportColumn2, reportColumn3));

    List<Long> columnUids = List.of(columnUid1, columnUid2, columnUid3);
    ReportExecutionRequest request = mockReportExecutionRequest(columnUids);

    DataSourceNameUtils dataSourceNameUtils = mockDataSourceNameUtils();

    ReportSpec reportSpec =
        new ReportSpecBuilder(request, reportConfig, dataSourceNameUtils).build();

    assertThat(reportSpec.subsetQuery())
        .isEqualTo(
            "SELECT [col1] AS [Col 1], [col2] AS [Col 2], [col3] AS [Col 3] FROM [NBS_ODSE].[dbo].[NBS_configuration]");
  }

  @Test
  void build_should_generate_correct_select_clause_for_no_columns() {
    FilterConfiguration filterConfig1 = mockFilterConfiguration(1L);
    FilterConfiguration filterConfig2 = mockFilterConfiguration(2L);

    ReportColumn reportColumn1 = mockReportColumn(1L, "col1", "Col 1");
    ReportColumn reportColumn2 = mockReportColumn(2L, "col2", "Col 2");

    ReportConfiguration reportConfig =
        mockReportConfiguration(
            List.of(filterConfig1, filterConfig2), List.of(reportColumn1, reportColumn2));

    ReportExecutionRequest request = mockReportExecutionRequest(null);

    DataSourceNameUtils dataSourceNameUtils = mockDataSourceNameUtils();

    ReportSpec reportSpec =
        new ReportSpecBuilder(request, reportConfig, dataSourceNameUtils).build();

    assertThat(reportSpec.subsetQuery())
        .isEqualTo("SELECT * FROM [NBS_ODSE].[dbo].[NBS_configuration]");
  }

  @ParameterizedTest
  @MethodSource("fetchSingleColumnTestParams")
  void build_should_generate_correct_select_clause_for_column_names(
      String columnName, String columnTitle) {
    Long columnUid1 = 1L;

    FilterConfiguration filterConfig1 = mockFilterConfiguration(columnUid1);
    ReportColumn reportColumn1 = mockReportColumn(columnUid1, columnName, columnTitle);

    ReportConfiguration reportConfig =
        mockReportConfiguration(List.of(filterConfig1), List.of(reportColumn1));

    List<Long> columnUids = List.of(columnUid1);
    ReportExecutionRequest request = mockReportExecutionRequest(columnUids);

    DataSourceNameUtils dataSourceNameUtils = mockDataSourceNameUtils();

    ReportSpec reportSpec =
        new ReportSpecBuilder(request, reportConfig, dataSourceNameUtils).build();

    assertThat(reportSpec.subsetQuery())
        .isEqualTo(
            "SELECT ["
                + columnName
                + "] AS ["
                + columnTitle
                + "] FROM [NBS_ODSE].[dbo].[NBS_configuration]");
  }

  private static Stream<Arguments> fetchSingleColumnTestParams() {
    return Stream.of(
        Arguments.of("column1", "Column 1"), //  Standard single column
        Arguments.of("first column", "Column 1"), //  Column name with spaces
        Arguments.of("user", "User Column") //  Column name with keywords
        );
  }
}
