package gov.cdc.nbs.report;

import static gov.cdc.nbs.report.ReportConstants.Operator;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.authorization.permission.Permission;
import gov.cdc.nbs.authorization.permission.scope.PermissionScope;
import gov.cdc.nbs.authorization.permission.scope.PermissionScopeResolver;
import gov.cdc.nbs.datasource.utils.DataSourceNameUtils;
import gov.cdc.nbs.report.ReportConstants.ReportGroup;
import gov.cdc.nbs.report.models.AdvancedFilterConfiguration;
import gov.cdc.nbs.report.models.AdvancedFilterRequest;
import gov.cdc.nbs.report.models.AdvancedQuery;
import gov.cdc.nbs.report.models.BasicFilterConfiguration;
import gov.cdc.nbs.report.models.BasicFilterRequest;
import gov.cdc.nbs.report.models.FilterType;
import gov.cdc.nbs.report.models.Library;
import gov.cdc.nbs.report.models.ReportColumn;
import gov.cdc.nbs.report.models.ReportConfiguration;
import gov.cdc.nbs.report.models.ReportDataSource;
import gov.cdc.nbs.report.models.ReportExecutionRequest;
import gov.cdc.nbs.report.utils.FieldFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

@ExtendWith(MockitoExtension.class)
class WhereClauseServiceTest {

  private WhereClauseService whereClauseService;

  @Mock private PermissionScopeResolver scopeResolver;

  private final FieldFormatter fieldFormatter = new FieldFormatter();

  @BeforeEach
  void setUp() {
    whereClauseService = new WhereClauseService(fieldFormatter, scopeResolver);
  }

  @AfterEach
  void tearDown() {
    SecurityContextHolder.clearContext();
  }

  private ReportConfiguration createReportConfig(
      List<BasicFilterConfiguration> basicFilterConfigurations,
      AdvancedFilterConfiguration advFilterConfiguration,
      List<ReportColumn> columns,
      ReportConstants.ReportGroup group) {
    return new ReportConfiguration(
        Mockito.mock(ReportDataSource.class),
        Mockito.mock(Library.class),
        "Test Report",
        null,
        0L,
        group,
        "1002",
        basicFilterConfigurations,
        advFilterConfiguration,
        columns,
        null,
        null);
  }

  private ReportConfiguration createReportConfig(
      List<BasicFilterConfiguration> basicFilterConfigurations, List<ReportColumn> columns) {
    return createReportConfig(basicFilterConfigurations, null, columns, ReportGroup.PUBLIC);
  }

  private ReportConfiguration createReportConfig(
      List<BasicFilterConfiguration> basicFilterConfigurations,
      List<ReportColumn> columns,
      ReportGroup group) {
    return createReportConfig(basicFilterConfigurations, null, columns, group);
  }

  private ReportConfiguration createReportConfig(
      AdvancedFilterConfiguration advancedFilterConfiguration, List<ReportColumn> columns) {
    return createReportConfig(List.of(), advancedFilterConfiguration, columns, ReportGroup.PUBLIC);
  }

  private BasicFilterConfiguration createBasicFilterConfiguration(
      List<String> filterDefaultValues,
      Long reportFilterUid,
      Long reportColumnUid,
      Boolean defaultIncludeNulls,
      FilterType fitlerType) {

    return new BasicFilterConfiguration(
        reportFilterUid,
        reportColumnUid,
        filterDefaultValues,
        defaultIncludeNulls,
        null,
        null,
        fitlerType);
  }

  private FilterType createFilterType(String type, String code) {
    return new FilterType(1L, "", "", code, "", type, "");
  }

  private AdvancedFilterConfiguration createAdvancedFilterConfiguration(
      Long id, AdvancedQuery.RuleGroup value) {
    return new AdvancedFilterConfiguration(id, value);
  }

  private AdvancedFilterRequest createAdvancedFilterRequest(
      Long id, AdvancedQuery.RuleGroup ruleGroup) {
    return new AdvancedFilterRequest(id, ruleGroup);
  }

  private AdvancedQuery.RuleGroup createRuleGroup(
      String id, String combinator, List<AdvancedQuery> rules) {
    return new AdvancedQuery.RuleGroup(id, combinator, rules);
  }

  private AdvancedQuery.Rule createRule(String id, Long columnId, Operator operator, String value) {
    return new AdvancedQuery.Rule(id, columnId, operator.name(), value);
  }

  private ReportColumn mockReportColumn(Long id, String columnSourceTypeCode, String columnName) {
    return mockReportColumn(id, columnSourceTypeCode, columnName, null);
  }

  private ReportColumn mockReportColumn(
      Long id, String columnSourceTypeCode, String columnName, String codesetName) {
    ReportColumn reportColumn = Mockito.mock(ReportColumn.class);

    Mockito.lenient().when(reportColumn.id()).thenReturn(id);
    Mockito.lenient().when(reportColumn.sourceTypeCode()).thenReturn(columnSourceTypeCode);
    Mockito.lenient().when(reportColumn.name()).thenReturn(columnName);
    Mockito.lenient().when(reportColumn.codesetNm()).thenReturn(codesetName);

    return reportColumn;
  }

  private void mockAuthenticatedUser(Long externalOrgUid) {

    NbsUserDetails mockUserDetails = Mockito.mock(NbsUserDetails.class);
    Mockito.lenient().when(mockUserDetails.getExternalOrgUid()).thenReturn(externalOrgUid);

    // Mock the Authentication and return our user as the Principal
    Authentication mockAuthentication = Mockito.mock(Authentication.class);
    Mockito.lenient().when(mockAuthentication.getPrincipal()).thenReturn(mockUserDetails);

    // Mock the Security Context container and have it return the auth token
    SecurityContext mockSecurityContext = Mockito.mock(SecurityContext.class);
    Mockito.lenient().when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);

    // Bind the context to the execution thread
    SecurityContextHolder.setContext(mockSecurityContext);
  }

  @Test
  void should_handle_single_value_equality() {

    Long filterUid = 100L;
    Long columnUid = 2L;
    List<String> filterDefaultValue = List.of("condition1", "condition2");

    FilterType filterType = createFilterType("BAS_TXT", "");

    List<BasicFilterConfiguration> basicFilterConfigs =
        List.of(
            createBasicFilterConfiguration(
                filterDefaultValue, filterUid, columnUid, null, filterType));

    ReportColumn reportColumn = mockReportColumn(columnUid, "STRING", "ColumnName");

    ReportConfiguration reportConfig =
        createReportConfig(basicFilterConfigs, List.of(reportColumn));

    List<BasicFilterRequest> basicFilterRequest =
        List.of(new BasicFilterRequest(filterUid, filterDefaultValue, false));

    String whereFragment =
        whereClauseService.buildBasicWhereFragment(reportConfig, basicFilterRequest);

    assertThat(whereFragment).isEqualTo("([ColumnName] IN ('condition1', 'condition2'))");
  }

  @Test
  void should_combine_multiple_filters_with_and() {
    Long filter1 = 101L;
    Long col1 = 1L;
    Long filter2 = 102L;
    Long col2 = 2L;
    FilterType filterType = createFilterType("BAS_TXT", "");

    ReportConfiguration reportConfig =
        createReportConfig(
            List.of(
                createBasicFilterConfiguration(List.of(), filter1, col1, false, filterType),
                createBasicFilterConfiguration(List.of(), filter2, col2, false, filterType)),
            List.of(
                mockReportColumn(col1, "STRING", "ColumnName1"),
                mockReportColumn(col2, "STRING", "ColumnName2")));

    List<BasicFilterRequest> request =
        List.of(
            new BasicFilterRequest(filter1, List.of("A"), false),
            new BasicFilterRequest(filter2, List.of("B"), false));

    String whereFragment = whereClauseService.buildBasicWhereFragment(reportConfig, request);

    assertThat(whereFragment).isEqualTo("([ColumnName1] IN ('A')) AND ([ColumnName2] IN ('B'))");
  }

  @Test
  void should_handle_allow_nulls_operator() {
    Long filterUid = 100L;
    Long columnUid = 1L;
    FilterType filterType = createFilterType("BAS_TXT", "");

    ReportConfiguration reportConfig =
        createReportConfig(
            List.of(
                createBasicFilterConfiguration(List.of(), filterUid, columnUid, true, filterType)),
            List.of(mockReportColumn(columnUid, "STRING", "ColumnName")));

    List<BasicFilterRequest> basicFilterRequest =
        List.of(new BasicFilterRequest(filterUid, List.of("condition1"), true));

    String whereFragment =
        whereClauseService.buildBasicWhereFragment(reportConfig, basicFilterRequest);

    assertThat(whereFragment).isEqualTo("([ColumnName] IN ('condition1') OR [ColumnName] IS NULL)");
  }

  @Test
  void should_handle_allow_nulls_operator_with_multiple_fields() {
    Long filterUid = 100L;
    Long columnUid = 1L;
    Long filterUid2 = 101L;
    Long columnUid2 = 2L;
    FilterType filterType = createFilterType("BAS_TXT", "");

    ReportConfiguration reportConfig =
        createReportConfig(
            List.of(
                createBasicFilterConfiguration(List.of(), filterUid, columnUid, true, filterType),
                createBasicFilterConfiguration(
                    List.of(), filterUid2, columnUid2, false, filterType)),
            List.of(
                mockReportColumn(columnUid, "STRING", "ColumnName"),
                mockReportColumn(columnUid2, "STRING", "ColumnName2")));

    List<BasicFilterRequest> basicFilterRequest =
        List.of(
            new BasicFilterRequest(filterUid, List.of("condition1"), true),
            new BasicFilterRequest(filterUid2, List.of("condition2"), false));

    String whereFragment =
        whereClauseService.buildBasicWhereFragment(reportConfig, basicFilterRequest);

    assertThat(whereFragment)
        .isEqualTo(
            "([ColumnName] IN ('condition1') OR [ColumnName] IS NULL) AND ([ColumnName2] IN ('condition2'))");
  }

  @Test
  void should_handle_only_nulls_requested() {
    Long filterUid = 100L;
    Long columnUid = 2L;
    FilterType filterType = createFilterType("BAS_TXT", "");

    List<BasicFilterConfiguration> basicFilterConfigs =
        List.of(createBasicFilterConfiguration(List.of(), filterUid, columnUid, true, filterType));
    ReportColumn reportColumn = mockReportColumn(columnUid, "STRING", "ColumnName");
    ReportConfiguration reportConfig =
        createReportConfig(basicFilterConfigs, List.of(reportColumn));

    // Request with empty values but includeNulls = true
    List<BasicFilterRequest> request = List.of(new BasicFilterRequest(filterUid, List.of(), true));

    String whereFragment = whereClauseService.buildBasicWhereFragment(reportConfig, request);

    assertThat(whereFragment).isEqualTo("([ColumnName] IS NULL)");
  }

  //  TIME RANGE TESTS

  @Test
  void should_handle_time_range_logic() {
    Long filterUid = 200L;
    Long columnUid = 5L;
    FilterType filterType = createFilterType("BAS_TIM_RANGE", "");

    BasicFilterConfiguration config =
        createBasicFilterConfiguration(List.of(), filterUid, columnUid, false, filterType);

    ReportColumn reportColumn = mockReportColumn(columnUid, "DATE", "date_column");
    ReportConfiguration reportConfig = createReportConfig(List.of(config), List.of(reportColumn));

    List<BasicFilterRequest> request =
        List.of(new BasicFilterRequest(filterUid, List.of("01/2023", "01/2023"), false));

    String whereFragment = whereClauseService.buildBasicWhereFragment(reportConfig, request);

    assertThat(whereFragment).isEqualTo("([date_column] BETWEEN '2023-01-01' AND '2023-01-31')");
  }

  @Test
  void should_handle_time_range_with_nulls() {
    Long filterUid = 200L;
    Long columnUid = 5L;
    FilterType filterType = createFilterType("BAS_TIM_RANGE", "");

    BasicFilterConfiguration config =
        createBasicFilterConfiguration(List.of(), filterUid, columnUid, true, filterType);

    ReportColumn reportColumn = mockReportColumn(columnUid, "DATE", "date_column");
    ReportConfiguration reportConfig = createReportConfig(List.of(config), List.of(reportColumn));

    List<BasicFilterRequest> request =
        List.of(new BasicFilterRequest(filterUid, List.of("01/2023", "01/2023"), true));

    String whereFragment = whereClauseService.buildBasicWhereFragment(reportConfig, request);

    assertThat(whereFragment)
        .isEqualTo(
            "(([date_column] BETWEEN '2023-01-01' AND '2023-01-31') OR ([date_column] IS NULL))");
  }

  @Test
  void should_return_empty_string_when_no_filters_match_type() {
    Long filterUid = 100L;
    Long columnUid = 2L;
    FilterType filterType = createFilterType("UNKNOWN_TYPE", "");

    // Type is "UNKNOWN_TYPE", which is not in BAS_TYPES or TIME_RANGE_TYPES
    BasicFilterConfiguration config =
        createBasicFilterConfiguration(List.of(), filterUid, columnUid, false, filterType);

    ReportColumn reportColumn = mockReportColumn(columnUid, "STRING", "ColumnName");
    ReportConfiguration reportConfig = createReportConfig(List.of(config), List.of(reportColumn));

    List<BasicFilterRequest> request =
        List.of(new BasicFilterRequest(filterUid, List.of("val"), false));

    String whereFragment = whereClauseService.buildBasicWhereFragment(reportConfig, request);

    assertThat(whereFragment).isEmpty();
  }

  private static Stream<String> noColumnFilterTypes() {
    return ReportConstants.BAS_CODES_NO_COLUMN.stream();
  }

  // Test will loop through all values in BAS_CODES_NO_COLUMN and run the test for each
  @ParameterizedTest(
      name =
          "Should skip processing and return empty string when filter type is in BAS_CODES_NO_COLUMN ")
  @MethodSource("noColumnFilterTypes")
  void should_return_empty_string_when_filter_type_has_no_associated_column(
      String filterCodeNoColumn) {
    Long filterUid = 100L;
    Long columnUid = 2L;
    FilterType filterType = createFilterType("", filterCodeNoColumn);

    // Create configuration using the parameterized filter type string
    BasicFilterConfiguration config =
        createBasicFilterConfiguration(List.of(), filterUid, columnUid, false, filterType);

    ReportColumn reportColumn = mockReportColumn(columnUid, "STRING", "ColumnName");

    ReportConfiguration reportConfig = createReportConfig(List.of(config), List.of(reportColumn));

    List<BasicFilterRequest> request =
        List.of(new BasicFilterRequest(filterUid, List.of("val"), false));

    String whereFragment = whereClauseService.buildBasicWhereFragment(reportConfig, request);

    assertThat(whereFragment).isEmpty();
  }

  // Test will loop through all values in BAS_CODES_NO_COLUMN and run the test for each
  @ParameterizedTest(
      name =
          "Should skip processing and return empty string when filter type is in BAS_CODES_NO_COLUMN ")
  @MethodSource("noColumnFilterTypes")
  void should_eliminate_BAS_CODES_NO_COLUMN_filters_but_leave_other_filters(
      String filterCodeNoColumn) {
    Long filterUid = 100L;
    Long columnUid = 1L;
    Long filterUid2 = 101L;
    Long columnUid2 = 2L;
    FilterType filterType1 = createFilterType("BAS_TXT", "");
    FilterType filterType2 = createFilterType("", filterCodeNoColumn);

    ReportConfiguration reportConfig =
        createReportConfig(
            List.of(
                createBasicFilterConfiguration(List.of(), filterUid, columnUid, true, filterType1),
                createBasicFilterConfiguration(
                    List.of(), filterUid2, columnUid2, false, filterType2)),
            List.of(
                mockReportColumn(columnUid, "STRING", "ColumnName"),
                mockReportColumn(columnUid2, "STRING", "ColumnName2")));

    List<BasicFilterRequest> basicFilterRequest =
        List.of(
            new BasicFilterRequest(filterUid, List.of("condition1"), false),
            new BasicFilterRequest(filterUid2, List.of("condition2"), false));

    String whereFragment =
        whereClauseService.buildBasicWhereFragment(reportConfig, basicFilterRequest);

    assertThat(whereFragment).isEqualTo("([ColumnName] IN ('condition1'))");
  }

  @Test
  void should_escape_malicious_strings_to_prevent_in_clause_breakout() {
    Long filterUid = 100L;
    Long columnUid = 2L;
    FilterType filterType = createFilterType("BAS_CON_LIST", "");

    // Setup Config
    BasicFilterConfiguration config =
        createBasicFilterConfiguration(List.of(), filterUid, columnUid, false, filterType);

    ReportColumn reportColumn = mockReportColumn(columnUid, "STRING", "ColumnName");
    ReportConfiguration reportConfig = createReportConfig(List.of(config), List.of(reportColumn));

    // Malicious Input: Attempt to close the IN clause and drop the Reports table
    // The -- at the end comments out the rest of your generated query (the closing parentheses)
    String maliciousInput = "'); DROP TABLE Reports; --";

    List<BasicFilterRequest> request =
        List.of(new BasicFilterRequest(filterUid, List.of("good_text", maliciousInput), false));

    String whereFragment = whereClauseService.buildBasicWhereFragment(reportConfig, request);

    // The output should treat the entire malicious string as a literal value
    assertThat(whereFragment)
        .isEqualTo("([ColumnName] IN ('good_text', '''); DROP TABLE Reports; --'))");
  }

  @Test
  void should_throw_exception_when_column_metadata_is_missing() {

    Long filterUid = 100L;
    Long columnUid = 2L;
    FilterType filterType = createFilterType("BAS_TXT", "");

    List<BasicFilterConfiguration> basicFilterConfigs =
        List.of(createBasicFilterConfiguration(List.of(), filterUid, columnUid, true, filterType));
    // Column list is empty or doesn't contain 2L
    ReportConfiguration reportConfig = createReportConfig(basicFilterConfigs, List.of());

    List<BasicFilterRequest> basicFilterRequest =
        List.of(new BasicFilterRequest(filterUid, List.of(), true));

    assertThatThrownBy(
            () -> whereClauseService.buildBasicWhereFragment(reportConfig, basicFilterRequest))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("No report column found for columnUid: 2");
  }

  @Test
  void should_throw_exception_when_filter_config_is_missing() {
    ReportConfiguration reportConfig = createReportConfig(List.of(), List.of());
    List<BasicFilterRequest> request = List.of(new BasicFilterRequest(999L, List.of("val"), false));

    assertThatThrownBy(() -> whereClauseService.buildBasicWhereFragment(reportConfig, request))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("No basic filter configuration found for UID: 999");
  }

  @Test
  void should_throw_exception_when_filter_type_definition_is_missing() {
    Long filterUid = 100L;
    Long columnUid = 2L;

    // Create a config where filterType is null
    BasicFilterConfiguration config =
        new BasicFilterConfiguration(filterUid, columnUid, List.of(), false, null, null, null);

    ReportColumn reportColumn = mockReportColumn(columnUid, "STRING", "ColumnName");
    ReportConfiguration reportConfig = createReportConfig(List.of(config), List.of(reportColumn));

    List<BasicFilterRequest> request =
        List.of(new BasicFilterRequest(filterUid, List.of("val"), false));

    assertThatThrownBy(() -> whereClauseService.buildBasicWhereFragment(reportConfig, request))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Filter type is missing for Report Filter ID: 100");
  }

  @Test
  void should_return_full_where_clause_with_prefix() {
    Long filterUid = 100L;
    Long columnUid = 2L;
    FilterType filterType = createFilterType("BAS_TXT", "");

    Long filterUid2 = 10L;
    Long columnUid2 = 3L;
    FilterType filterType2 = createFilterType("BAS_TIM_RANGE", "");

    BasicFilterConfiguration config =
        createBasicFilterConfiguration(List.of(), filterUid, columnUid, false, filterType);

    BasicFilterConfiguration config2 =
        createBasicFilterConfiguration(List.of(), filterUid2, columnUid2, true, filterType2);

    ReportColumn reportColumn = mockReportColumn(columnUid, "STRING", "ColumnName");
    ReportColumn reportColumn2 = mockReportColumn(columnUid2, "DATE", "TimeRangeColumn");

    // Pass 'S' (Public) as the shared status parameter
    ReportConfiguration reportConfig =
        createReportConfig(
            List.of(config, config2), List.of(reportColumn, reportColumn2), ReportGroup.PUBLIC);

    // Enable both jurisdiction and facility security toggles on the data source mock
    when(reportConfig.dataSource().hasJurisdictionSecurity()).thenReturn(Boolean.TRUE);
    when(reportConfig.dataSource().hasFacilitySecurity()).thenReturn(Boolean.TRUE);

    // Stub the authenticated user context for facility tracking
    mockAuthenticatedUser(54321L);

    // Stub the permission scope resolver for jurisdiction tracking (program_jurisdiction_oid IN
    PermissionScope mockScope = Mockito.mock(PermissionScope.class);
    when(mockScope.any()).thenReturn(List.of(101L));
    when(scopeResolver.resolve(new Permission("REPORTING", "VIEWREPORTPUBLIC")))
        .thenReturn(mockScope);

    // Wrap in the execution request
    ReportExecutionRequest executionRequest = Mockito.mock(ReportExecutionRequest.class);
    when(executionRequest.basicFilters())
        .thenReturn(
            List.of(
                new BasicFilterRequest(filterUid, List.of("A"), false),
                new BasicFilterRequest(filterUid2, List.of("01/01/2023", "01/01/2024"), true)));

    DataSourceNameUtils mockDataSourceNameUtils = Mockito.mock(DataSourceNameUtils.class);

    String result =
        whereClauseService.buildWhereClause(
            reportConfig, executionRequest, mockDataSourceNameUtils);

    // Verify that basic filters and parenthesized permission fragments are joined cleanly by an
    // AND
    assertThat(result)
        .isEqualTo(
            "WHERE ([ColumnName] IN ('A')) AND "
                + "(([TimeRangeColumn] BETWEEN '2023-01-01' AND '2024-01-01') OR ([TimeRangeColumn] IS NULL)) AND "
                + "((program_jurisdiction_oid IN (101)) AND (REPORTING_FACILITY_UID = 54321))");
  }

  @Test
  void should_throw_exception_when_values_mismatch_due_to_nulls() {
    Long filterUid = 100L;
    FilterType filterType = createFilterType("BAS_TXT", "");

    BasicFilterConfiguration config =
        createBasicFilterConfiguration(List.of(), filterUid, 2L, false, filterType);
    ReportColumn reportColumn = mockReportColumn(2L, "STRING", "ColumnName");
    ReportConfiguration reportConfig =
        createReportConfig(List.of(config), List.of(reportColumn), null);

    // Request contains a null which is stripped by the stream, triggering the validation check
    List<BasicFilterRequest> request =
        List.of(new BasicFilterRequest(filterUid, Arrays.asList("Active", null), false));

    assertThatThrownBy(() -> whereClauseService.buildBasicWhereFragment(reportConfig, request))
        .isInstanceOf(IllegalStateException.class)
        .hasMessageContaining("Expected 2 values but only 1 were successfully formatted");
  }

  @Test
  void should_throw_exception_when_input_values_result_in_zero_formatted_values() {
    Long filterUid = 100L;
    Long columnUid = 2L;
    FilterType filterType = createFilterType("BAS_TXT", "");

    // Setup config and column
    BasicFilterConfiguration config =
        createBasicFilterConfiguration(List.of(), filterUid, columnUid, false, filterType);
    ReportColumn reportColumn = mockReportColumn(columnUid, "STRING", "ColumnName");
    ReportConfiguration reportConfig =
        createReportConfig(List.of(config), List.of(reportColumn), null);

    // Request with ONLY null values.
    // The .filter(Objects::nonNull) will strip these, leaving formattedValues empty.
    List<BasicFilterRequest> request =
        List.of(new BasicFilterRequest(filterUid, java.util.Arrays.asList(null, null), false));

    assertThatThrownBy(() -> whereClauseService.buildBasicWhereFragment(reportConfig, request))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("No valid formatted values produced for column: ColumnName");
  }

  @Test
  void should_create_where_clause_with_all_filters_and_nested_rules() {
    Long filterUid = 100L;
    Long columnUid = 2L;
    FilterType filterType = createFilterType("BAS_TXT", "");

    Long filterUid2 = 10L;
    Long columnUid2 = 3L;
    FilterType filterType2 = createFilterType("BAS_TIM_RANGE", "");

    BasicFilterConfiguration config =
        createBasicFilterConfiguration(List.of(), filterUid, columnUid, false, filterType);

    BasicFilterConfiguration config2 =
        createBasicFilterConfiguration(List.of(), filterUid2, columnUid2, true, filterType2);

    ReportColumn reportColumn = mockReportColumn(columnUid, "STRING", "ColumnName");
    ReportColumn reportColumn2 = mockReportColumn(columnUid2, "DATE", "TimeRangeColumn");

    AdvancedQuery.Rule labResultRule =
        createRule(UUID.randomUUID().toString(), 15L, Operator.EQ, "1");
    AdvancedQuery.Rule labResultRule2 =
        createRule(UUID.randomUUID().toString(), 16L, Operator.NE, "1");

    ReportColumn labResultReportColumn = mockReportColumn(15L, "INTEGER", "numeric_result_val");
    ReportColumn labResultReportColumn2 = mockReportColumn(16L, "STRING", "RESULT_UNITS", "");

    List<AdvancedQuery.Rule> rules = create_adv_query_rules();
    List<ReportColumn> reportCols = new java.util.ArrayList<>(create_adv_query_report_cols());

    AdvancedQuery.RuleGroup ruleGroup1 =
        createRuleGroup(
            UUID.randomUUID().toString(),
            "and",
            List.of(rules.get(18), rules.get(22), rules.get(0), rules.get(4)));
    AdvancedQuery.RuleGroup ruleGroup3 =
        createRuleGroup(
            UUID.randomUUID().toString(),
            "or",
            List.of(rules.get(1), ruleGroup1, rules.get(15), labResultRule));
    AdvancedQuery.RuleGroup ruleGroup4 =
        createRuleGroup(
            UUID.randomUUID().toString(),
            "and",
            List.of(rules.get(19), ruleGroup3, rules.get(23), labResultRule2));
    AdvancedQuery.RuleGroup ruleGroup2 =
        createRuleGroup(
            UUID.randomUUID().toString(),
            "or",
            List.of(
                rules.get(9),
                ruleGroup4,
                rules.get(13),
                rules.get(11),
                rules.get(2),
                rules.get(8)));

    AdvancedFilterConfiguration advFilterConfig = createAdvancedFilterConfiguration(1L, ruleGroup2);

    reportCols.addAll(
        List.of(reportColumn, reportColumn2, labResultReportColumn, labResultReportColumn2));
    ReportConfiguration reportConfig =
        createReportConfig(
            List.of(config, config2), advFilterConfig, reportCols, ReportGroup.PUBLIC);
    ReportExecutionRequest executionRequest = Mockito.mock(ReportExecutionRequest.class);

    when(reportConfig.dataSource().hasJurisdictionSecurity()).thenReturn(Boolean.TRUE);
    when(reportConfig.dataSource().hasFacilitySecurity()).thenReturn(Boolean.TRUE);

    mockAuthenticatedUser(54321L);

    PermissionScope mockScope = Mockito.mock(PermissionScope.class);
    when(mockScope.any()).thenReturn(List.of(50L));
    when(scopeResolver.resolve(new Permission("REPORTING", "VIEWREPORTPUBLIC")))
        .thenReturn(mockScope);

    when(executionRequest.basicFilters())
        .thenReturn(
            List.of(
                new BasicFilterRequest(filterUid, List.of("1"), false),
                new BasicFilterRequest(filterUid2, List.of("01/01/2023", "01/01/2024"), true)));
    when(executionRequest.advancedFilter()).thenReturn(new AdvancedFilterRequest(3L, ruleGroup2));

    DataSourceNameUtils mockDataSourceNameUtils = Mockito.mock(DataSourceNameUtils.class);
    when(mockDataSourceNameUtils.buildDataSourceName("nbs_rdb.lab_test_report"))
        .thenReturn("[RDB].[dbo].[lab_test_report]");

    assertThat(
            whereClauseService.buildWhereClause(
                reportConfig, executionRequest, mockDataSourceNameUtils))
        .isEqualTo(
            "WHERE root_ordered_test_pntr IN (SELECT root_ordered_test_pntr FROM [RDB].[dbo].[lab_test_report] WHERE ([COLUMN_INTEGER] IN (1)) AND (([TimeRangeColumn] BETWEEN '2023-01-01' AND '2024-01-01') OR ([TimeRangeColumn] IS NULL)) AND ((CAST([COLUMN_DATETIME] AS DATE) IN ('2026-05-28')) OR (([COLUMN_STRING] LIKE CONCAT('%', 'foo', '%')) AND (([COLUMN_INTEGER] NOT IN (1) OR [COLUMN_INTEGER] IS NULL) OR (([COLUMN_STRING] LIKE CONCAT('foo', '%')) AND ([COLUMN_STRING] IN ('2019 Novel Coronavirus', 'AIDS', 'Acanthamoeba Disease (Excluding Keratitis)')) AND ([COLUMN_INTEGER] IN (1)) AND ([COLUMN_INTEGER] >= 1)) OR (CAST([COLUMN_DATETIME] AS DATE) > '2026-05-28') OR ([numeric_result_val] = 1)) AND ([COLUMN_STRING] NOT IN ('2019 Novel Coronavirus', 'AIDS', 'Acanthamoeba Disease (Excluding Keratitis)') OR [COLUMN_STRING] IS NULL) AND ([RESULT_UNITS] <> '1' OR ([RESULT_UNITS] IS NULL))) OR (CAST([COLUMN_DATETIME] AS DATE) BETWEEN '2026-05-25' AND '2026-05-28') OR (CAST([COLUMN_DATETIME] AS DATE) IS NOT NULL) OR ([COLUMN_INTEGER] > 1) OR ([COLUMN_INTEGER] BETWEEN 1 AND 2))) AND ((program_jurisdiction_oid IN (50)) AND (REPORTING_FACILITY_UID = 54321))");
  }

  @ParameterizedTest
  @MethodSource("fetchAdvancedQueryRuleParams")
  void build_advanced_query_result_should_return_query_with_value(
      List<Integer> ruleIndices, String expectedSQL) {
    List<AdvancedQuery.Rule> rules = create_adv_query_rules();
    List<ReportColumn> reportCols = create_adv_query_report_cols();
    List<AdvancedQuery> selectedRules = List.of();

    if (ruleIndices != null && !ruleIndices.isEmpty()) {
      selectedRules = ruleIndices.stream().map(index -> (AdvancedQuery) rules.get(index)).toList();
    }

    AdvancedQuery.RuleGroup ruleGroup =
        createRuleGroup(UUID.randomUUID().toString(), "and", selectedRules);
    AdvancedFilterRequest advancedFilterRequest = createAdvancedFilterRequest(1L, ruleGroup);

    AdvancedFilterConfiguration filterConfiguration =
        createAdvancedFilterConfiguration(1L, ruleGroup);
    ReportConfiguration reportConfig = createReportConfig(filterConfiguration, reportCols);

    String advFilterResult =
        whereClauseService.buildAdvancedQueryResult(reportConfig, advancedFilterRequest);
    assertThat(advFilterResult).isEqualTo(expectedSQL);
  }

  private static Stream<Arguments> fetchAdvancedQueryRuleParams() {
    return Stream.of(
        Arguments.of(
            List.of(1, 2, 3, 4, 5, 6, 7, 8),
            "(([COLUMN_INTEGER] NOT IN (1) OR [COLUMN_INTEGER] IS NULL) AND ([COLUMN_INTEGER] > 1) AND ([COLUMN_INTEGER] < 1) AND ([COLUMN_INTEGER] >= 1) AND ([COLUMN_INTEGER] <= 1) AND ([COLUMN_INTEGER] IS NOT NULL) AND ([COLUMN_INTEGER] IS NULL) AND ([COLUMN_INTEGER] BETWEEN 1 AND 2))"),
        Arguments.of(
            List.of(9, 10, 11, 12, 13, 14, 15, 16, 17),
            "((CAST([COLUMN_DATETIME] AS DATE) IN ('2026-05-28')) AND (CAST([COLUMN_DATETIME] AS DATE) NOT IN ('2026-05-28') OR CAST([COLUMN_DATETIME] AS DATE) IS NULL) AND (CAST([COLUMN_DATETIME] AS DATE) IS NOT NULL) AND (CAST([COLUMN_DATETIME] AS DATE) IS NULL) AND (CAST([COLUMN_DATETIME] AS DATE) BETWEEN '2026-05-25' AND '2026-05-28') AND (CAST([COLUMN_DATETIME] AS DATE) < '2026-05-28') AND (CAST([COLUMN_DATETIME] AS DATE) > '2026-05-28') AND (CAST([COLUMN_DATETIME] AS DATE) <= '2026-05-28') AND (CAST([COLUMN_DATETIME] AS DATE) >= '2026-05-28'))"),
        Arguments.of(
            List.of(18, 19, 20, 21),
            "(([COLUMN_STRING] LIKE CONCAT('foo', '%')) AND ([COLUMN_STRING] LIKE CONCAT('%', 'foo', '%')) AND ([COLUMN_STRING] IN ('foo')) AND ([COLUMN_STRING] NOT IN ('foo') OR [COLUMN_STRING] IS NULL))"),
        Arguments.of(List.of(), ""),
        Arguments.of(null, ""));
  }

  private List<AdvancedQuery.Rule> create_adv_query_rules() {
    String intNumVal = "1";
    String dateVal = "05/28/2026";
    List<Map<String, Object>> rules =
        Arrays.asList(
            Map.of("columnId", 2L, "operator", Operator.EQ, "value", intNumVal),
            Map.of("columnId", 2L, "operator", Operator.NE, "value", intNumVal),
            Map.of("columnId", 2L, "operator", Operator.GT, "value", intNumVal),
            Map.of("columnId", 2L, "operator", Operator.LT, "value", intNumVal),
            Map.of("columnId", 2L, "operator", Operator.GE, "value", intNumVal),
            Map.of("columnId", 2L, "operator", Operator.LE, "value", intNumVal),
            Map.of("columnId", 2L, "operator", Operator.NN, "value", ""),
            Map.of("columnId", 2L, "operator", Operator.IN, "value", ""),
            Map.of("columnId", 2L, "operator", Operator.BW, "value", "1,2"),
            Map.of("columnId", 4L, "operator", Operator.EQ, "value", dateVal),
            Map.of("columnId", 4L, "operator", Operator.NE, "value", dateVal),
            Map.of("columnId", 4L, "operator", Operator.NN, "value", ""),
            Map.of("columnId", 4L, "operator", Operator.IN, "value", ""),
            Map.of("columnId", 4L, "operator", Operator.BW, "value", "05/25/2026,05/28/2026"),
            Map.of("columnId", 4L, "operator", Operator.LT, "value", dateVal),
            Map.of("columnId", 4L, "operator", Operator.GT, "value", dateVal),
            Map.of("columnId", 4L, "operator", Operator.LE, "value", dateVal),
            Map.of("columnId", 4L, "operator", Operator.GE, "value", dateVal),
            Map.of("columnId", 5L, "operator", Operator.SW, "value", "foo"),
            Map.of("columnId", 5L, "operator", Operator.CO, "value", "foo"),
            Map.of("columnId", 5L, "operator", Operator.EQ, "value", "foo"),
            Map.of("columnId", 5L, "operator", Operator.NE, "value", "foo"),
            Map.of(
                "columnId",
                6L,
                "operator",
                Operator.EQ,
                "value",
                "2019 Novel Coronavirus|AIDS|Acanthamoeba Disease (Excluding Keratitis)"),
            Map.of(
                "columnId",
                6L,
                "operator",
                Operator.NE,
                "value",
                "2019 Novel Coronavirus|AIDS|Acanthamoeba Disease (Excluding Keratitis)"));

    return rules.stream()
        .map(
            rule -> {
              Long columnUid = (Long) rule.get("columnId");
              return createRule(
                  UUID.randomUUID().toString(),
                  columnUid,
                  (Operator) rule.get("operator"),
                  (String) rule.get("value"));
            })
        .toList();
  }

  private List<ReportColumn> create_adv_query_report_cols() {
    List<Long> columnIds = List.of(2L, 4L, 5L, 6L);
    return columnIds.stream()
        .map(
            id -> {
              String typeCode = getColumnSourceTypeCode(id);
              return mockReportColumn(
                  id, typeCode, String.format("COLUMN_%s", typeCode), "CONDITION_CODE");
            })
        .toList();
  }

  private String getColumnSourceTypeCode(long columnId) {
    return switch ((int) columnId) {
      case 2 -> "INTEGER";
      case 4 -> "DATETIME";
      case 5, 6 -> "STRING";
      default -> "";
    };
  }

  @Test
  void should_append_facility_uid_when_facility_security_is_active() {
    ReportConfiguration reportConfig = createReportConfig(List.of(), List.of(), null);
    when(reportConfig.dataSource().hasJurisdictionSecurity()).thenReturn(Boolean.FALSE);
    when(reportConfig.dataSource().hasFacilitySecurity()).thenReturn(Boolean.TRUE);

    mockAuthenticatedUser(54321L);

    String result = whereClauseService.buildPermissionFragment(reportConfig);

    assertThat(result).isEqualTo("((REPORTING_FACILITY_UID = 54321))");
  }

  @Test
  void should_append_jurisdiction_oids_when_active() {
    ReportConfiguration reportConfig = createReportConfig(List.of(), List.of(), ReportGroup.PUBLIC);

    when(reportConfig.dataSource().hasJurisdictionSecurity()).thenReturn(Boolean.TRUE);
    when(reportConfig.dataSource().hasFacilitySecurity()).thenReturn(Boolean.FALSE);

    // Stub the permission scope resolver data
    PermissionScope mockScope = Mockito.mock(PermissionScope.class);
    when(mockScope.any()).thenReturn(List.of(101L, 102L));

    Permission expectedPermission = new Permission("REPORTING", "VIEWREPORTPUBLIC");
    when(scopeResolver.resolve(expectedPermission)).thenReturn(mockScope);

    String result = whereClauseService.buildPermissionFragment(reportConfig);

    assertThat(result).isEqualTo("((program_jurisdiction_oid IN (101, 102)))");
  }

  @Test
  void should_combine_both_jurisdiction_and_facility_security_with_and() {
    ReportConfiguration reportConfig = createReportConfig(List.of(), List.of(), ReportGroup.PUBLIC);

    when(reportConfig.dataSource().hasJurisdictionSecurity()).thenReturn(Boolean.TRUE);
    when(reportConfig.dataSource().hasFacilitySecurity()).thenReturn(Boolean.TRUE);

    mockAuthenticatedUser(54321L);

    PermissionScope mockScope = Mockito.mock(PermissionScope.class);
    when(mockScope.any()).thenReturn(List.of(50L));
    when(scopeResolver.resolve(new Permission("REPORTING", "VIEWREPORTPUBLIC")))
        .thenReturn(mockScope);

    String result = whereClauseService.buildPermissionFragment(reportConfig);

    assertThat(result)
        .isEqualTo("((program_jurisdiction_oid IN (50)) AND (REPORTING_FACILITY_UID = 54321))");
  }

  @Test
  void should_ignore_jurisdiction_security_if_report_group_is_unrecognized() {
    // Pass in null for group
    ReportConfiguration reportConfig = createReportConfig(List.of(), List.of(), null);

    when(reportConfig.dataSource().hasJurisdictionSecurity()).thenReturn(Boolean.FALSE);
    when(reportConfig.dataSource().hasFacilitySecurity()).thenReturn(Boolean.TRUE);

    mockAuthenticatedUser(null);

    String result = whereClauseService.buildPermissionFragment(reportConfig);

    assertThat(result).isEmpty();
  }

  @Test
  void should_throw_illegal_arg_exception_when_user_has_no_assigned_ids() {
    ReportConfiguration reportConfig = createReportConfig(List.of(), List.of(), ReportGroup.PUBLIC);

    when(reportConfig.dataSource().hasJurisdictionSecurity()).thenReturn(Boolean.TRUE);

    PermissionScope emptyScope = Mockito.mock(PermissionScope.class);
    when(emptyScope.any()).thenReturn(List.of()); // Empty list
    when(scopeResolver.resolve(new Permission("REPORTING", "VIEWREPORTPUBLIC")))
        .thenReturn(emptyScope);

    mockAuthenticatedUser(null);

    assertThatThrownBy(() -> whereClauseService.buildPermissionFragment(reportConfig))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("No Jurisdiction or Program Area permissions found for user: null");
  }

  @Test
  void should_omit_facility_criteria_if_external_org_uid_is_null() {
    ReportConfiguration reportConfig = createReportConfig(List.of(), List.of(), ReportGroup.PUBLIC);

    when(reportConfig.dataSource().hasJurisdictionSecurity()).thenReturn(Boolean.FALSE);
    when(reportConfig.dataSource().hasFacilitySecurity()).thenReturn(Boolean.TRUE);

    mockAuthenticatedUser(null);

    String result = whereClauseService.buildPermissionFragment(reportConfig);

    assertThat(result).isEmpty();
  }

  @Test
  void should_combine_basic_filters_and_permission_clauses_with_and() {
    Long filterUid = 100L;
    Long columnUid = 2L;
    FilterType filterType = createFilterType("BAS_TXT", "");

    // Setup basic text filter configuration
    BasicFilterConfiguration config =
        createBasicFilterConfiguration(List.of(), filterUid, columnUid, false, filterType);
    ReportColumn reportColumn = mockReportColumn(columnUid, "STRING", "StateCode");

    // Enable facility security with a 'S' (Public)
    ReportConfiguration reportConfig =
        createReportConfig(List.of(config), List.of(reportColumn), ReportGroup.PUBLIC);

    when(reportConfig.dataSource().hasJurisdictionSecurity()).thenReturn(Boolean.FALSE);
    when(reportConfig.dataSource().hasFacilitySecurity()).thenReturn(Boolean.TRUE);

    // Set up authenticated user profile
    mockAuthenticatedUser(8888L);

    // Assemble execution payload matching the configured filter UID
    ReportExecutionRequest executionRequest = Mockito.mock(ReportExecutionRequest.class);
    when(executionRequest.basicFilters())
        .thenReturn(List.of(new BasicFilterRequest(filterUid, List.of("GA"), false)));

    DataSourceNameUtils mockDataSourceNameUtils = Mockito.mock(DataSourceNameUtils.class);

    // Run the full orchestrator
    String result =
        whereClauseService.buildWhereClause(
            reportConfig, executionRequest, mockDataSourceNameUtils);

    // Verify both strings merged cleanly with zero dangling operators
    assertThat(result)
        .isEqualTo("WHERE ([StateCode] IN ('GA')) AND ((REPORTING_FACILITY_UID = 8888))");
  }

  @Test
  void should_apply_where_prefix_to_permissions_only_when_basic_filters_are_empty() {
    // Setup config with zero basic filters, but with active jurisdiction visibility controls
    ReportConfiguration reportConfig = createReportConfig(List.of(), List.of(), ReportGroup.PUBLIC);

    when(reportConfig.dataSource().hasJurisdictionSecurity()).thenReturn(Boolean.TRUE);
    when(reportConfig.dataSource().hasFacilitySecurity()).thenReturn(Boolean.FALSE);

    // Stub the permission context return mapping
    PermissionScope mockScope = Mockito.mock(PermissionScope.class);
    when(mockScope.any()).thenReturn(List.of(77L));
    when(scopeResolver.resolve(new Permission("REPORTING", "VIEWREPORTPUBLIC")))
        .thenReturn(mockScope);

    // Setup user context requesting a report with no filters
    ReportExecutionRequest executionRequest = Mockito.mock(ReportExecutionRequest.class);
    when(executionRequest.basicFilters()).thenReturn(List.of()); // Wide open request

    DataSourceNameUtils mockDataSourceNameUtils = Mockito.mock(DataSourceNameUtils.class);

    String result =
        whereClauseService.buildWhereClause(
            reportConfig, executionRequest, mockDataSourceNameUtils);

    // Verify formatting treats permissions as the primary clause component
    assertThat(result).isEqualTo("WHERE ((program_jurisdiction_oid IN (77)))");
  }
}
