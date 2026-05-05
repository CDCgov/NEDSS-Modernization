package gov.cdc.nbs.report;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import gov.cdc.nbs.report.models.BasicFilterConfiguration;
import gov.cdc.nbs.report.models.BasicFilterRequest;
import gov.cdc.nbs.report.models.FilterType;
import gov.cdc.nbs.report.models.Library;
import gov.cdc.nbs.report.models.ReportColumn;
import gov.cdc.nbs.report.models.ReportConfiguration;
import gov.cdc.nbs.report.models.ReportDataSource;
import gov.cdc.nbs.report.models.ReportExecutionRequest;
import gov.cdc.nbs.report.utils.FieldFormatter;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class WhereClauseServiceTest {

  @Mock private FieldFormatter fieldFormatter;

  private WhereClauseService mockWhereClauseService;

  @BeforeEach
  void setUp() {
    mockWhereClauseService = new WhereClauseService(fieldFormatter);
    // Default behavior for formatter: return value as is
    Mockito.lenient()
        .when(fieldFormatter.formatField(anyString(), anyString()))
        .thenAnswer(ff -> ff.getArgument(1));
  }

  private ReportConfiguration createReportConfig(
      List<BasicFilterConfiguration> basicFilterConfigurations, List<ReportColumn> columns) {
    return new ReportConfiguration(
        Mockito.mock(ReportDataSource.class),
        Mockito.mock(Library.class),
        "Test Report",
        basicFilterConfigurations,
        null,
        columns);
  }

  private BasicFilterConfiguration createBasicFilterConfiguration(
      List<String> filterDefaultValues,
      Long reportFilterUid,
      Long reportColumnUid,
      Boolean defaultIncludeNulls,
      String typeString) {

    FilterType mockType = Mockito.mock(FilterType.class);
    // Ensure the mock returns the string we need for the IF/ELSE logic
    Mockito.lenient().when(mockType.type()).thenReturn(typeString);

    return new BasicFilterConfiguration(
        reportFilterUid,
        reportColumnUid,
        filterDefaultValues,
        null,
        null,
        null,
        mockType,
        defaultIncludeNulls);
  }

  private ReportColumn mockReportColumn(Long id, String columnSourceTypeCode, String columnName) {
    ReportColumn reportColumn = Mockito.mock(ReportColumn.class);

    Mockito.lenient().when(reportColumn.id()).thenReturn(id);
    Mockito.lenient().when(reportColumn.columnSourceTypeCode()).thenReturn(columnSourceTypeCode);
    Mockito.lenient().when(reportColumn.columnName()).thenReturn(columnName);

    return reportColumn;
  }

  @Test
  void should_handle_single_value_equality() {

    Long filterUid = 100L;
    Long columnUid = 2L;

    String filterDefaultValue = "condition1";

    List<BasicFilterConfiguration> basicFilterConfigs =
        List.of(
            createBasicFilterConfiguration(
                List.of(filterDefaultValue), filterUid, columnUid, null, "BAS_TXT"));

    ReportColumn reportColumn = mockReportColumn(columnUid, "STRING", "ColumnName");

    ReportConfiguration reportConfig =
        createReportConfig(basicFilterConfigs, List.of(reportColumn));

    List<BasicFilterRequest> basicFilterRequest =
        List.of(new BasicFilterRequest(filterUid, List.of(filterDefaultValue), false));

    String whereFragment =
        mockWhereClauseService.buildBasicWhereFragment(reportConfig, basicFilterRequest);

    assertThat(whereFragment).isEqualTo("([ColumnName] IN (condition1))");
  }

  @Test
  void should_combine_multiple_filters_with_and() {
    Long filter1 = 101L;
    Long col1 = 1L;
    Long filter2 = 102L;
    Long col2 = 2L;

    ReportConfiguration reportConfig =
        createReportConfig(
            List.of(
                createBasicFilterConfiguration(List.of(), filter1, col1, false, "BAS_TXT"),
                createBasicFilterConfiguration(List.of(), filter2, col2, false, "BAS_TXT")),
            List.of(
                mockReportColumn(col1, "STRING", "ColumnName1"),
                mockReportColumn(col2, "STRING", "ColumnName2")));

    List<BasicFilterRequest> request =
        List.of(
            new BasicFilterRequest(filter1, List.of("A"), false),
            new BasicFilterRequest(filter2, List.of("B"), false));

    String whereFragment = mockWhereClauseService.buildBasicWhereFragment(reportConfig, request);

    assertThat(whereFragment).isEqualTo("([ColumnName1] IN (A)) AND ([ColumnName2] IN (B))");
  }

  @Test
  void should_handle_allow_nulls_operator() {
    Long filterUid = 100L;
    Long columnUid = 2L;

    String filterDefaultValue = "condition1";

    List<BasicFilterConfiguration> basicFilterConfigs =
        List.of(
            createBasicFilterConfiguration(
                List.of(filterDefaultValue), filterUid, columnUid, true, "BAS_TXT"));

    ReportColumn reportColumn = mockReportColumn(columnUid, "STRING", "ColumnName");

    ReportConfiguration reportConfig =
        createReportConfig(basicFilterConfigs, List.of(reportColumn));

    List<BasicFilterRequest> basicFilterRequest =
        List.of(new BasicFilterRequest(filterUid, List.of(filterDefaultValue), true));

    String whereFragment =
        mockWhereClauseService.buildBasicWhereFragment(reportConfig, basicFilterRequest);

    assertThat(whereFragment).isEqualTo("([ColumnName] IN (condition1) OR [ColumnName] IS NULL)");
  }

  @Test
  void should_handle_only_nulls_requested() {
    Long filterUid = 100L;
    Long columnUid = 2L;

    List<BasicFilterConfiguration> basicFilterConfigs =
        List.of(createBasicFilterConfiguration(List.of(), filterUid, columnUid, true, "BAS_TXT"));
    ReportColumn reportColumn = mockReportColumn(columnUid, "STRING", "ColumnName");
    ReportConfiguration reportConfig =
        createReportConfig(basicFilterConfigs, List.of(reportColumn));

    // Request with empty values but includeNulls = true
    List<BasicFilterRequest> request = List.of(new BasicFilterRequest(filterUid, List.of(), true));

    String whereFragment = mockWhereClauseService.buildBasicWhereFragment(reportConfig, request);

    assertThat(whereFragment).isEqualTo("([ColumnName] IS NULL)");
  }

  //  TIME RANGE TESTS

  @Test
  void should_handle_time_range_logic() {
    Long filterUid = 200L;
    Long columnUid = 5L;

    BasicFilterConfiguration config =
        createBasicFilterConfiguration(List.of(), filterUid, columnUid, false, "BAS_TIM_RANGE");

    ReportColumn reportColumn = mockReportColumn(columnUid, "DATE", "date_column");
    ReportConfiguration reportConfig = createReportConfig(List.of(config), List.of(reportColumn));

    when(fieldFormatter.convertToSQLFromDateRange(any()))
        .thenReturn(List.of("'2023-01-01'", "'2023-01-31'"));

    List<BasicFilterRequest> request =
        List.of(new BasicFilterRequest(filterUid, List.of("01/2023", "01/2023"), false));

    String whereFragment = mockWhereClauseService.buildBasicWhereFragment(reportConfig, request);

    assertThat(whereFragment).isEqualTo("([date_column] BETWEEN '2023-01-01' AND '2023-01-31')");
  }

  @Test
  void should_handle_time_range_with_nulls() {
    Long filterUid = 200L;
    Long columnUid = 5L;

    BasicFilterConfiguration config =
        createBasicFilterConfiguration(List.of(), filterUid, columnUid, true, "BAS_TIM_RANGE");

    ReportColumn reportColumn = mockReportColumn(columnUid, "DATE", "date_column");
    ReportConfiguration reportConfig = createReportConfig(List.of(config), List.of(reportColumn));

    when(fieldFormatter.convertToSQLFromDateRange(any()))
        .thenReturn(List.of("'2023-01-01'", "'2023-01-31'"));

    List<BasicFilterRequest> request =
        List.of(new BasicFilterRequest(filterUid, List.of("01/2023", "01/2023"), true));

    String whereFragment = mockWhereClauseService.buildBasicWhereFragment(reportConfig, request);

    assertThat(whereFragment)
        .isEqualTo(
            "(([date_column] BETWEEN '2023-01-01' AND '2023-01-31') OR ([date_column] IS NULL))");
  }

  @Test
  void should_return_empty_string_when_no_filters_match_type() {
    Long filterUid = 100L;
    Long columnUid = 2L;

    // Type is "UNKNOWN_TYPE", which is not in BAS_TYPES or TIME_RANGE_TYPES
    BasicFilterConfiguration config =
        createBasicFilterConfiguration(List.of(), filterUid, columnUid, false, "UNKNOWN_TYPE");

    ReportColumn reportColumn = mockReportColumn(columnUid, "STRING", "ColumnName");
    ReportConfiguration reportConfig = createReportConfig(List.of(config), List.of(reportColumn));

    List<BasicFilterRequest> request =
        List.of(new BasicFilterRequest(filterUid, List.of("val"), false));

    String whereFragment = mockWhereClauseService.buildBasicWhereFragment(reportConfig, request);

    assertThat(whereFragment).isEmpty();
  }

  @Test
  void should_ignore_null_values_in_request_list() {
    Long filterUid = 100L;
    Long columnUid = 2L;

    BasicFilterConfiguration config =
        createBasicFilterConfiguration(List.of(), filterUid, columnUid, false, "BAS_CON_LIST");

    ReportColumn reportColumn = mockReportColumn(columnUid, "STRING", "ColumnName");
    ReportConfiguration reportConfig = createReportConfig(List.of(config), List.of(reportColumn));

    // Request with a null item in the list
    List<BasicFilterRequest> request =
        List.of(new BasicFilterRequest(filterUid, java.util.Arrays.asList("A", null), false));

    String whereFragment = mockWhereClauseService.buildBasicWhereFragment(reportConfig, request);

    assertThat(whereFragment).contains("ColumnName");
  }

  @Test
  void should_escape_malicious_strings_to_prevent_in_clause_breakout() {
    Long filterUid = 100L;
    Long columnUid = 2L;

    // Setup Config
    BasicFilterConfiguration config =
        createBasicFilterConfiguration(List.of(), filterUid, columnUid, false, "BAS_CON_LIST");

    ReportColumn reportColumn = mockReportColumn(columnUid, "STRING", "ColumnName");
    ReportConfiguration reportConfig = createReportConfig(List.of(config), List.of(reportColumn));

    // Malicious Input: Attempt to close the IN clause and drop the Reports table
    // The -- at the end comments out the rest of your generated query (the closing parentheses)
    String maliciousInput = "'); DROP TABLE Reports; --";

    // We mock the FieldFormatter's job: it must escape the single quote
    // Result: ''); DROP TABLE Reports; --'
    String escapedInput = "''); DROP TABLE Reports; --'";

    // We expect the formatter to turn the ' into ''
    when(fieldFormatter.formatField(anyString(), eq(maliciousInput))).thenReturn(escapedInput);

    List<BasicFilterRequest> request =
        List.of(new BasicFilterRequest(filterUid, List.of("good_text", maliciousInput), false));

    String whereFragment = mockWhereClauseService.buildBasicWhereFragment(reportConfig, request);

    // The output should treat the entire malicious string as a literal value
    assertThat(whereFragment)
        .isEqualTo("([ColumnName] IN (good_text, ''); DROP TABLE Reports; --'))");
  }

  @Test
  void should_throw_exception_when_column_metadata_is_missing() {

    Long filterUid = 100L;
    Long columnUid = 2L;

    List<BasicFilterConfiguration> basicFilterConfigs =
        List.of(createBasicFilterConfiguration(List.of(), filterUid, columnUid, true, "BAS_TXT"));
    // Column list is empty or doesn't contain 2L
    ReportConfiguration reportConfig = createReportConfig(basicFilterConfigs, List.of());

    List<BasicFilterRequest> basicFilterRequest =
        List.of(new BasicFilterRequest(filterUid, List.of(), true));

    assertThatThrownBy(
            () -> mockWhereClauseService.buildBasicWhereFragment(reportConfig, basicFilterRequest))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("No report column found for columnUid: 2");
  }

  @Test
  void should_throw_exception_when_filter_config_is_missing() {
    ReportConfiguration reportConfig = createReportConfig(List.of(), List.of());
    List<BasicFilterRequest> request = List.of(new BasicFilterRequest(999L, List.of("val"), false));

    assertThatThrownBy(() -> mockWhereClauseService.buildBasicWhereFragment(reportConfig, request))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("No basic filter configuration found for UID: 999");
  }

  @Test
  void should_throw_exception_when_filter_type_definition_is_missing() {
    Long filterUid = 100L;
    Long columnUid = 2L;

    // Create a config where filterType is null
    BasicFilterConfiguration config =
        new BasicFilterConfiguration(
            filterUid, columnUid, List.of(), null, null, null, null, // FilterType is null
            false);

    ReportColumn reportColumn = mockReportColumn(columnUid, "STRING", "ColumnName");
    ReportConfiguration reportConfig = createReportConfig(List.of(config), List.of(reportColumn));

    List<BasicFilterRequest> request =
        List.of(new BasicFilterRequest(filterUid, List.of("val"), false));

    assertThatThrownBy(() -> mockWhereClauseService.buildBasicWhereFragment(reportConfig, request))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Filter type is missing for Report Filter ID: 100");
  }

  @Test
  void should_return_full_where_clause_with_prefix() {
    Long filterUid = 100L;
    Long columnUid = 2L;

    Long filterUid2 = 10L;
    Long columnUid2 = 3L;

    BasicFilterConfiguration config =
        createBasicFilterConfiguration(List.of(), filterUid, columnUid, false, "BAS_TXT");

    BasicFilterConfiguration config2 =
        createBasicFilterConfiguration(List.of(), filterUid2, columnUid2, true, "BAS_TIM_RANGE");

    ReportColumn reportColumn = mockReportColumn(columnUid, "STRING", "ColumnName");
    ReportColumn reportColumn2 = mockReportColumn(columnUid2, "DATE", "TimeRangeColumn");

    ReportConfiguration reportConfig =
        createReportConfig(List.of(config, config2), List.of(reportColumn, reportColumn2));

    when(fieldFormatter.convertToSQLFromDateRange(any()))
        .thenReturn(List.of("'2023-01-01'", "'2024-01-01'"));

    // Wrap in the execution request
    ReportExecutionRequest executionRequest = Mockito.mock(ReportExecutionRequest.class);
    when(executionRequest.basicFilters())
        .thenReturn(
            List.of(
                new BasicFilterRequest(filterUid, List.of("A"), false),
                new BasicFilterRequest(filterUid2, List.of("2023-01-01", "2024-01-01"), true)));

    String result = mockWhereClauseService.buildWhereClause(reportConfig, executionRequest);

    assertThat(result)
        .isEqualTo(
            "WHERE ([ColumnName] IN (A)) AND (([TimeRangeColumn] BETWEEN '2023-01-01' AND '2024-01-01') OR ([TimeRangeColumn] IS NULL))");
  }
}
