package gov.cdc.nbs.report;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import gov.cdc.nbs.authorization.permission.scope.PermissionScopeResolver;
import gov.cdc.nbs.datasource.utils.DataSourceNameConfiguration;
import gov.cdc.nbs.datasource.utils.DataSourceNameUtils;
import gov.cdc.nbs.report.models.BasicFilterConfiguration;
import gov.cdc.nbs.report.models.BasicFilterRequest;
import gov.cdc.nbs.report.models.FilterType;
import gov.cdc.nbs.report.models.Library;
import gov.cdc.nbs.report.models.ReportColumn;
import gov.cdc.nbs.report.models.ReportConfiguration;
import gov.cdc.nbs.report.models.ReportDataSource;
import gov.cdc.nbs.report.models.ReportExecutionRequest;
import gov.cdc.nbs.report.models.ReportSpec;
import gov.cdc.nbs.report.utils.FieldFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReportSpecBuilderTest {

  private WhereClauseService whereClauseService;

  @Mock private PermissionScopeResolver scopeResolver;

  private final FieldFormatter fieldFormatter = new FieldFormatter();

  @BeforeEach
  void setUp() {
    whereClauseService = new WhereClauseService(fieldFormatter, scopeResolver);
  }

  private DataSourceNameUtils mockDataSourceNameUtils() {
    DataSourceNameUtils dataSourceNameUtils = Mockito.mock(DataSourceNameUtils.class);
    when(dataSourceNameUtils.buildDataSourceName("nbs_ods.NBS_configuration"))
        .thenReturn("[NBS_ODSE].[dbo].[NBS_configuration]");
    return dataSourceNameUtils;
  }

  private BasicFilterConfiguration mockBasicFilterConfiguration(
      List<String> filterDefaultValues, Long reportFilterUid, Long reportColumnUid) {

    // Default to "BAS_TXT" for backwards compatibility
    return mockBasicFilterConfiguration(
        filterDefaultValues, reportFilterUid, reportColumnUid, "BAS_TXT");
  }

  // For use with "BAS_DAYS" tests
  private BasicFilterConfiguration mockBasicFilterConfiguration(
      List<String> filterDefaultValues,
      Long reportFilterUid,
      Long reportColumnUid,
      String filterTypeCode) {

    FilterType filterType = Mockito.mock(FilterType.class);
    Mockito.lenient().when(filterType.type()).thenReturn(filterTypeCode);

    return new BasicFilterConfiguration(
        reportFilterUid, reportColumnUid, filterDefaultValues, null, null, null, filterType);
  }

  private ReportColumn mockReportColumn(Long columnId, String columnName, String columnTitle) {
    ReportColumn reportColumn = Mockito.mock(ReportColumn.class);

    Mockito.lenient().when(reportColumn.id()).thenReturn(columnId);
    Mockito.lenient().when(reportColumn.name()).thenReturn(columnName);
    Mockito.lenient().when(reportColumn.title()).thenReturn(columnTitle);
    Mockito.lenient().when(reportColumn.sourceTypeCode()).thenReturn("STRING");

    return reportColumn;
  }

  private ReportConfiguration mockReportConfiguration(
      List<BasicFilterConfiguration> filters, List<ReportColumn> columns, String title) {
    ReportConfiguration reportConfiguration = Mockito.mock(ReportConfiguration.class);

    DataSourceNameConfiguration dataSourceNameConfiguration =
        Mockito.mock(DataSourceNameConfiguration.class);
    Mockito.lenient().when(dataSourceNameConfiguration.getMappings()).thenReturn(new HashMap<>());

    Library library = Mockito.mock(Library.class);
    Mockito.lenient().when(reportConfiguration.library()).thenReturn(library);
    Mockito.lenient().when(library.name()).thenReturn("nbs_custom");

    ReportDataSource dataSource = Mockito.mock(ReportDataSource.class);
    Mockito.lenient().when(reportConfiguration.dataSource()).thenReturn(dataSource);
    Mockito.lenient().when(dataSource.name()).thenReturn("nbs_ods.NBS_configuration");

    Mockito.lenient().when(reportConfiguration.basicFilters()).thenReturn(filters);
    Mockito.lenient().when(reportConfiguration.columns()).thenReturn(columns);
    Mockito.lenient().when(reportConfiguration.title()).thenReturn(title);

    return reportConfiguration;
  }

  private ReportExecutionRequest mockReportExecutionRequest(List<Long> columnUids) {
    ReportExecutionRequest request = Mockito.mock(ReportExecutionRequest.class);

    Mockito.lenient().when(request.columnUids()).thenReturn(columnUids);

    return request;
  }

  @Test
  void build_should_set_all_fields_correctly() {
    List<String> filterDefaultValues = List.of("condition1");

    Long columnUid1 = 1L;
    Long columnUid2 = 2L;

    BasicFilterConfiguration filterConfig1 =
        mockBasicFilterConfiguration(filterDefaultValues, 1L, columnUid1);
    BasicFilterConfiguration filterConfig2 =
        mockBasicFilterConfiguration(filterDefaultValues, 1L, columnUid1);

    ReportColumn reportColumn1 = mockReportColumn(columnUid1, "column1", "Column 1");
    ReportColumn reportColumn2 = mockReportColumn(columnUid2, "column2", "Column 2");

    ReportConfiguration reportConfig =
        mockReportConfiguration(
            List.of(filterConfig1, filterConfig2),
            List.of(reportColumn1, reportColumn2),
            "Test Title");

    List<Long> columnUids = List.of(columnUid1, columnUid2);

    ReportExecutionRequest request = mockReportExecutionRequest(columnUids);

    DataSourceNameUtils dataSourceNameUtils = mockDataSourceNameUtils();

    ReportSpec reportSpec =
        new ReportSpecBuilder(request, reportConfig, dataSourceNameUtils, whereClauseService)
            .build();

    assertThat(reportSpec.isBuiltin()).isEqualTo(reportConfig.library().isBuiltin());
    assertThat(reportSpec.isExport()).isEqualTo(request.isExport());
    assertThat(reportSpec.reportTitle()).isEqualTo(reportConfig.title());
    assertThat(reportSpec.libraryName()).isEqualTo(reportConfig.library().name());
    assertThat(reportSpec.dataSourceName()).isEqualTo("[NBS_ODSE].[dbo].[NBS_configuration]");

    assertThat(reportSpec.subsetQuery())
        .isEqualTo(
            "SELECT [column1] AS [Column 1], [column2] AS [Column 2] FROM [NBS_ODSE].[dbo].[NBS_configuration]");
  }

  @Test
  void setColumns_should_throw_illegal_argument_when_columns_not_found() {

    List<String> filterDefaultValue = List.of("condition1");

    Long knownColumnUid = 1L;
    Long unknownColumnUid = 2L;

    BasicFilterConfiguration filterConfig1 =
        mockBasicFilterConfiguration(filterDefaultValue, 1L, knownColumnUid);
    ReportColumn reportColumn1 = mockReportColumn(knownColumnUid, "column1", "Column 1");

    ReportConfiguration reportConfig =
        mockReportConfiguration(List.of(filterConfig1), List.of(reportColumn1), "Test Title");

    List<Long> columnUids = List.of(knownColumnUid, unknownColumnUid);
    ReportExecutionRequest request = mockReportExecutionRequest(columnUids);

    DataSourceNameUtils dataSourceNameUtils = mockDataSourceNameUtils();

    ReportSpecBuilder reportSpecBuilder =
        new ReportSpecBuilder(request, reportConfig, dataSourceNameUtils, whereClauseService);

    assertThatThrownBy(reportSpecBuilder::build)
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("No report column found for columnUid 2");
  }

  @Test
  void build_should_generate_correct_select_clause_for_multiple_columns() {

    List<String> filterDefaultValue = List.of("condition1");

    Long columnUid1 = 1L;
    Long columnUid2 = 2L;
    Long columnUid3 = 3L;

    BasicFilterConfiguration filterConfig1 =
        mockBasicFilterConfiguration(filterDefaultValue, 1L, columnUid1);
    BasicFilterConfiguration filterConfig2 =
        mockBasicFilterConfiguration(filterDefaultValue, 1L, columnUid1);
    BasicFilterConfiguration filterConfig3 =
        mockBasicFilterConfiguration(filterDefaultValue, 1L, columnUid1);

    ReportColumn reportColumn1 = mockReportColumn(columnUid1, "col1", "Col 1");
    ReportColumn reportColumn2 = mockReportColumn(columnUid2, "col2", "Col 2");
    ReportColumn reportColumn3 = mockReportColumn(columnUid3, "col3", "Col 3");

    ReportConfiguration reportConfig =
        mockReportConfiguration(
            List.of(filterConfig1, filterConfig2, filterConfig3),
            List.of(reportColumn1, reportColumn2, reportColumn3),
            "Test Title");

    List<Long> columnUids = List.of(columnUid1, columnUid2, columnUid3);
    ReportExecutionRequest request = mockReportExecutionRequest(columnUids);

    DataSourceNameUtils dataSourceNameUtils = mockDataSourceNameUtils();

    ReportSpec reportSpec =
        new ReportSpecBuilder(request, reportConfig, dataSourceNameUtils, whereClauseService)
            .build();

    assertThat(reportSpec.subsetQuery())
        .isEqualTo(
            "SELECT [col1] AS [Col 1], [col2] AS [Col 2], [col3] AS [Col 3] FROM [NBS_ODSE].[dbo].[NBS_configuration]");
  }

  @Test
  void build_should_generate_correct_select_clause_for_no_columns() {

    List<String> filterDefaultValue = List.of("condition1");

    BasicFilterConfiguration filterConfig1 =
        mockBasicFilterConfiguration(filterDefaultValue, 1L, 1L);
    BasicFilterConfiguration filterConfig2 =
        mockBasicFilterConfiguration(filterDefaultValue, 1L, 2L);

    ReportColumn reportColumn1 = mockReportColumn(1L, "col1", "Col 1");
    ReportColumn reportColumn2 = mockReportColumn(2L, "col2", "Col 2");

    ReportConfiguration reportConfig =
        mockReportConfiguration(
            List.of(filterConfig1, filterConfig2),
            List.of(reportColumn1, reportColumn2),
            "Test Title");

    ReportExecutionRequest request = mockReportExecutionRequest(null);

    DataSourceNameUtils dataSourceNameUtils = mockDataSourceNameUtils();

    ReportSpec reportSpec =
        new ReportSpecBuilder(request, reportConfig, dataSourceNameUtils, whereClauseService)
            .build();

    assertThat(reportSpec.subsetQuery())
        .isEqualTo("SELECT * FROM [NBS_ODSE].[dbo].[NBS_configuration]");
  }

  @ParameterizedTest
  @MethodSource("fetchSingleColumnTestParams")
  void build_should_generate_correct_select_clause_for_column_names(
      String columnName, String columnTitle) {

    List<String> filterDefaultValue = List.of("condition1");
    Long columnUid1 = 1L;

    BasicFilterConfiguration filterConfig1 =
        mockBasicFilterConfiguration(filterDefaultValue, 1L, columnUid1);
    ReportColumn reportColumn1 = mockReportColumn(columnUid1, columnName, columnTitle);

    ReportConfiguration reportConfig =
        mockReportConfiguration(List.of(filterConfig1), List.of(reportColumn1), "Test Title");

    List<Long> columnUids = List.of(columnUid1);
    ReportExecutionRequest request = mockReportExecutionRequest(columnUids);

    DataSourceNameUtils dataSourceNameUtils = mockDataSourceNameUtils();

    ReportSpec reportSpec =
        new ReportSpecBuilder(request, reportConfig, dataSourceNameUtils, whereClauseService)
            .build();

    assertThat(reportSpec.subsetQuery())
        .isEqualTo(
            "SELECT ["
                + columnName
                + "] AS ["
                + columnTitle
                + "] FROM [NBS_ODSE].[dbo].[NBS_configuration]");
  }

  @Test
  void build_should_include_where_clause_when_present() {
    Long filterUid = 100L;
    Long columnUid = 1L;
    List<String> filterDefaultValue = List.of("Value");

    BasicFilterConfiguration basicFilterConfiguration =
        mockBasicFilterConfiguration(filterDefaultValue, filterUid, columnUid);

    ReportColumn reportColumn1 = mockReportColumn(columnUid, "col1", "Col 1");

    ReportConfiguration reportConfig =
        mockReportConfiguration(
            List.of(basicFilterConfiguration), List.of(reportColumn1), "Test Title");

    List<BasicFilterRequest> filterRequests =
        List.of(new BasicFilterRequest(filterUid, filterDefaultValue, false));

    ReportExecutionRequest request = mockReportExecutionRequest(List.of(columnUid));
    when(request.basicFilters()).thenReturn(filterRequests);

    DataSourceNameUtils dataSourceNameUtils = mockDataSourceNameUtils();

    ReportSpec reportSpec =
        new ReportSpecBuilder(request, reportConfig, dataSourceNameUtils, whereClauseService)
            .build();

    // ASSERT: Verify the WHERE clause is appended correctly with a space
    assertThat(reportSpec.subsetQuery())
        .isEqualTo(
            "SELECT [col1] AS [Col 1] FROM [NBS_ODSE].[dbo].[NBS_configuration] WHERE ([col1] IN ('Value'))");
  }

  @Test
  void build_should_include_days_value_when_filter_present() {
    Long filterUid = 100L;
    Long columnUid = 1L;
    List<String> requestValue = List.of("11");

    BasicFilterConfiguration basicFilterConfiguration =
        mockBasicFilterConfiguration(List.of(), filterUid, columnUid, "BAS_DAYS");

    ReportColumn reportColumn1 = mockReportColumn(columnUid, "col1", "Col 1");
    ReportConfiguration reportConfig =
        mockReportConfiguration(
            List.of(basicFilterConfiguration), List.of(reportColumn1), "Test Title");

    List<BasicFilterRequest> filterRequests =
        List.of(new BasicFilterRequest(filterUid, requestValue, false));

    ReportExecutionRequest request = mockReportExecutionRequest(List.of(columnUid));
    when(request.basicFilters()).thenReturn(filterRequests);

    DataSourceNameUtils dataSourceNameUtils = mockDataSourceNameUtils();

    ReportSpec reportSpec =
        new ReportSpecBuilder(request, reportConfig, dataSourceNameUtils, whereClauseService)
            .build();

    assertThat(reportSpec.daysValue()).isEqualTo(Integer.valueOf(requestValue.getFirst()));
  }

  @Test
  void build_should_throw_exception_when_days_value_text() {
    Long filterUid = 100L;
    Long columnUid = 1L;
    List<String> requestValue = List.of("not_number");

    BasicFilterConfiguration basicFilterConfiguration =
        mockBasicFilterConfiguration(List.of(), filterUid, columnUid, "BAS_DAYS");

    ReportColumn reportColumn1 = mockReportColumn(columnUid, "col1", "Col 1");
    ReportConfiguration reportConfig =
        mockReportConfiguration(
            List.of(basicFilterConfiguration), List.of(reportColumn1), "Test Title");

    List<BasicFilterRequest> filterRequests =
        List.of(new BasicFilterRequest(filterUid, requestValue, false));

    ReportExecutionRequest request = mockReportExecutionRequest(List.of(columnUid));
    when(request.basicFilters()).thenReturn(filterRequests);

    DataSourceNameUtils dataSourceNameUtils = mockDataSourceNameUtils();

    ReportSpecBuilder reportSpec =
        new ReportSpecBuilder(request, reportConfig, dataSourceNameUtils, whereClauseService);

    assertThatThrownBy(reportSpec::build)
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("The 'Days Filter' filter value must be a valid integer: not_number");
  }

  @Test
  void build_should_return_null_days_value_when_bas_days_filter_not_present() {
    Long filterUid = 100L;
    Long columnUid = 1L;
    List<String> requestValue = List.of("11");

    // Configured as text filter, NOT BAS_DAYS
    BasicFilterConfiguration basicFilterConfiguration =
        mockBasicFilterConfiguration(List.of(), filterUid, columnUid, "BAS_TXT");

    ReportColumn reportColumn1 = mockReportColumn(columnUid, "col1", "Col 1");
    ReportConfiguration reportConfig =
        mockReportConfiguration(
            List.of(basicFilterConfiguration), List.of(reportColumn1), "Test Title");

    List<BasicFilterRequest> filterRequests =
        List.of(new BasicFilterRequest(filterUid, requestValue, false));

    ReportExecutionRequest request = mockReportExecutionRequest(List.of(columnUid));
    when(request.basicFilters()).thenReturn(filterRequests);

    DataSourceNameUtils dataSourceNameUtils = mockDataSourceNameUtils();

    ReportSpec reportSpec =
        new ReportSpecBuilder(request, reportConfig, dataSourceNameUtils, whereClauseService)
            .build();

    // Verifies that a non-BAS_DAYS request doesn't accidentally map
    assertThat(reportSpec.daysValue()).isNull();
  }

  @Test
  void build_should_return_null_days_value_when_basic_filters_are_null() {
    Long columnUid = 1L;

    BasicFilterConfiguration basicFilterConfiguration =
        mockBasicFilterConfiguration(List.of(), 100L, columnUid, "BAS_DAYS");

    ReportColumn reportColumn1 = mockReportColumn(columnUid, "col1", "Col 1");
    ReportConfiguration reportConfig =
        mockReportConfiguration(
            List.of(basicFilterConfiguration), List.of(reportColumn1), "Test Title");

    ReportExecutionRequest request = mockReportExecutionRequest(List.of(columnUid));
    when(request.basicFilters()).thenReturn(null); // Explicitly testing null guard clause

    DataSourceNameUtils dataSourceNameUtils = mockDataSourceNameUtils();

    ReportSpec reportSpec =
        new ReportSpecBuilder(request, reportConfig, dataSourceNameUtils, whereClauseService)
            .build();

    assertThat(reportSpec.daysValue()).isNull();
  }

  @Test
  void build_should_return_null_days_value_when_provided_value_is_blank() {
    Long filterUid = 100L;
    Long columnUid = 1L;

    BasicFilterConfiguration basicFilterConfiguration =
        mockBasicFilterConfiguration(List.of(), filterUid, columnUid, "BAS_DAYS");

    ReportColumn reportColumn1 = mockReportColumn(columnUid, "col1", "Col 1");
    ReportConfiguration reportConfig =
        mockReportConfiguration(
            List.of(basicFilterConfiguration), List.of(reportColumn1), "Test Title");

    // User sent a filter request but left the actual text blank
    List<BasicFilterRequest> filterRequests =
        List.of(new BasicFilterRequest(filterUid, List.of("   "), false));

    ReportExecutionRequest request = mockReportExecutionRequest(List.of(columnUid));
    when(request.basicFilters()).thenReturn(filterRequests);

    DataSourceNameUtils dataSourceNameUtils = mockDataSourceNameUtils();

    ReportSpec reportSpec =
        new ReportSpecBuilder(request, reportConfig, dataSourceNameUtils, whereClauseService)
            .build();

    assertThat(reportSpec.daysValue()).isNull();
  }

  private static Stream<Arguments> fetchSingleColumnTestParams() {
    return Stream.of(
        Arguments.of("column1", "Column 1"), //  Standard single column
        Arguments.of("first column", "Column 1"), //  Column name with spaces
        Arguments.of("user", "User Column") //  Column name with keywords
        );
  }
}
