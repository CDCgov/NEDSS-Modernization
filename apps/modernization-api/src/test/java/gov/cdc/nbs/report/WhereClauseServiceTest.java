package gov.cdc.nbs.report;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.authorization.permission.Permission;
import gov.cdc.nbs.authorization.permission.scope.PermissionScope;
import gov.cdc.nbs.authorization.permission.scope.PermissionScopeResolver;
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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
      List<ReportColumn> columns,
      Character shared) {
    return new ReportConfiguration(
        Mockito.mock(ReportDataSource.class),
        Mockito.mock(Library.class),
        "Test Report",
        shared,
        basicFilterConfigurations,
        null,
        columns,
        null,
        null);
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
        defaultIncludeNulls,
        null,
        null,
        null,
        mockType);
  }

  private ReportColumn mockReportColumn(Long id, String columnSourceTypeCode, String columnName) {
    ReportColumn reportColumn = Mockito.mock(ReportColumn.class);

    Mockito.lenient().when(reportColumn.id()).thenReturn(id);
    Mockito.lenient().when(reportColumn.sourceTypeCode()).thenReturn(columnSourceTypeCode);
    Mockito.lenient().when(reportColumn.name()).thenReturn(columnName);

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

    List<BasicFilterConfiguration> basicFilterConfigs =
        List.of(
            createBasicFilterConfiguration(
                filterDefaultValue, filterUid, columnUid, null, "BAS_TXT"));

    ReportColumn reportColumn = mockReportColumn(columnUid, "STRING", "ColumnName");

    ReportConfiguration reportConfig =
        createReportConfig(basicFilterConfigs, List.of(reportColumn), null);

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

    ReportConfiguration reportConfig =
        createReportConfig(
            List.of(
                createBasicFilterConfiguration(List.of(), filter1, col1, false, "BAS_TXT"),
                createBasicFilterConfiguration(List.of(), filter2, col2, false, "BAS_TXT")),
            List.of(
                mockReportColumn(col1, "STRING", "ColumnName1"),
                mockReportColumn(col2, "STRING", "ColumnName2")),
            null);

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

    ReportConfiguration reportConfig =
        createReportConfig(
            List.of(
                createBasicFilterConfiguration(List.of(), filterUid, columnUid, true, "BAS_TXT")),
            List.of(mockReportColumn(columnUid, "STRING", "ColumnName")),
            null);

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

    ReportConfiguration reportConfig =
        createReportConfig(
            List.of(
                createBasicFilterConfiguration(List.of(), filterUid, columnUid, true, "BAS_TXT"),
                createBasicFilterConfiguration(
                    List.of(), filterUid2, columnUid2, false, "BAS_TXT")),
            List.of(
                mockReportColumn(columnUid, "STRING", "ColumnName"),
                mockReportColumn(columnUid2, "STRING", "ColumnName2")),
            null);

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

    List<BasicFilterConfiguration> basicFilterConfigs =
        List.of(createBasicFilterConfiguration(List.of(), filterUid, columnUid, true, "BAS_TXT"));
    ReportColumn reportColumn = mockReportColumn(columnUid, "STRING", "ColumnName");
    ReportConfiguration reportConfig =
        createReportConfig(basicFilterConfigs, List.of(reportColumn), null);

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

    BasicFilterConfiguration config =
        createBasicFilterConfiguration(List.of(), filterUid, columnUid, false, "BAS_TIM_RANGE");

    ReportColumn reportColumn = mockReportColumn(columnUid, "DATE", "date_column");
    ReportConfiguration reportConfig =
        createReportConfig(List.of(config), List.of(reportColumn), null);

    List<BasicFilterRequest> request =
        List.of(new BasicFilterRequest(filterUid, List.of("01/2023", "01/2023"), false));

    String whereFragment = whereClauseService.buildBasicWhereFragment(reportConfig, request);

    assertThat(whereFragment).isEqualTo("([date_column] BETWEEN '2023-01-01' AND '2023-01-31')");
  }

  @Test
  void should_handle_time_range_with_nulls() {
    Long filterUid = 200L;
    Long columnUid = 5L;

    BasicFilterConfiguration config =
        createBasicFilterConfiguration(List.of(), filterUid, columnUid, true, "BAS_TIM_RANGE");

    ReportColumn reportColumn = mockReportColumn(columnUid, "DATE", "date_column");
    ReportConfiguration reportConfig =
        createReportConfig(List.of(config), List.of(reportColumn), null);

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

    // Type is "UNKNOWN_TYPE", which is not in BAS_TYPES or TIME_RANGE_TYPES
    BasicFilterConfiguration config =
        createBasicFilterConfiguration(List.of(), filterUid, columnUid, false, "UNKNOWN_TYPE");

    ReportColumn reportColumn = mockReportColumn(columnUid, "STRING", "ColumnName");
    ReportConfiguration reportConfig =
        createReportConfig(List.of(config), List.of(reportColumn), null);

    List<BasicFilterRequest> request =
        List.of(new BasicFilterRequest(filterUid, List.of("val"), false));

    String whereFragment = whereClauseService.buildBasicWhereFragment(reportConfig, request);

    assertThat(whereFragment).isEmpty();
  }

  @Test
  void should_escape_malicious_strings_to_prevent_in_clause_breakout() {
    Long filterUid = 100L;
    Long columnUid = 2L;

    // Setup Config
    BasicFilterConfiguration config =
        createBasicFilterConfiguration(List.of(), filterUid, columnUid, false, "BAS_CON_LIST");

    ReportColumn reportColumn = mockReportColumn(columnUid, "STRING", "ColumnName");
    ReportConfiguration reportConfig =
        createReportConfig(List.of(config), List.of(reportColumn), null);

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

    List<BasicFilterConfiguration> basicFilterConfigs =
        List.of(createBasicFilterConfiguration(List.of(), filterUid, columnUid, true, "BAS_TXT"));
    // Column list is empty or doesn't contain 2L
    ReportConfiguration reportConfig = createReportConfig(basicFilterConfigs, List.of(), null);

    List<BasicFilterRequest> basicFilterRequest =
        List.of(new BasicFilterRequest(filterUid, List.of(), true));

    assertThatThrownBy(
            () -> whereClauseService.buildBasicWhereFragment(reportConfig, basicFilterRequest))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("No report column found for columnUid: 2");
  }

  @Test
  void should_throw_exception_when_filter_config_is_missing() {
    ReportConfiguration reportConfig = createReportConfig(List.of(), List.of(), null);
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
        new BasicFilterConfiguration(
            filterUid, columnUid, List.of(), false, null, null, null, null);

    ReportColumn reportColumn = mockReportColumn(columnUid, "STRING", "ColumnName");
    ReportConfiguration reportConfig =
        createReportConfig(List.of(config), List.of(reportColumn), null);

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

    Long filterUid2 = 10L;
    Long columnUid2 = 3L;

    BasicFilterConfiguration config =
        createBasicFilterConfiguration(List.of(), filterUid, columnUid, false, "BAS_TXT");

    BasicFilterConfiguration config2 =
        createBasicFilterConfiguration(List.of(), filterUid2, columnUid2, true, "BAS_TIM_RANGE");

    ReportColumn reportColumn = mockReportColumn(columnUid, "STRING", "ColumnName");
    ReportColumn reportColumn2 = mockReportColumn(columnUid2, "DATE", "TimeRangeColumn");

    // Pass 'S' (Public) as the shared status parameter
    ReportConfiguration reportConfig =
        createReportConfig(List.of(config, config2), List.of(reportColumn, reportColumn2), 'S');

    // 2. Enable both jurisdiction and facility security toggles on the data source mock
    when(reportConfig.dataSource().jurisdictionSecurity()).thenReturn('Y');
    when(reportConfig.dataSource().facilitySecurity()).thenReturn('Y');

    // 3. Stub the authenticated user context for facility tracking (REPORTING_FACILITY_UID = 54321)
    mockAuthenticatedUser(54321L);

    // 4. Stub the permission scope resolver for jurisdiction tracking (program_jurisdiction_oid IN
    // (101))
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

    String result = whereClauseService.buildWhereClause(reportConfig, executionRequest);

    // 5. Verify that basic filters and parenthesized permission fragments are joined cleanly by an
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
    BasicFilterConfiguration config =
        createBasicFilterConfiguration(List.of(), filterUid, 2L, false, "BAS_TXT");
    ReportColumn reportColumn = mockReportColumn(2L, "STRING", "ColumnName");
    ReportConfiguration reportConfig =
        createReportConfig(List.of(config), List.of(reportColumn), null);

    // Request contains a null which is stripped by the stream, triggering the validation check
    List<BasicFilterRequest> request =
        List.of(new BasicFilterRequest(filterUid, java.util.Arrays.asList("Active", null), false));

    assertThatThrownBy(() -> whereClauseService.buildBasicWhereFragment(reportConfig, request))
        .isInstanceOf(IllegalStateException.class)
        .hasMessageContaining("Expected 2 values but only 1 were successfully formatted");
  }

  @Test
  void should_throw_exception_when_input_values_result_in_zero_formatted_values() {
    Long filterUid = 100L;
    Long columnUid = 2L;

    // Setup config and column
    BasicFilterConfiguration config =
        createBasicFilterConfiguration(List.of(), filterUid, columnUid, false, "BAS_TXT");
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
  void should_return_empty_fragments_when_security_indicators_are_not_active() {
    ReportConfiguration reportConfig = createReportConfig(List.of(), List.of(), null);
    // Set security parameters to 'N' or null
    when(reportConfig.dataSource().jurisdictionSecurity()).thenReturn('N');
    when(reportConfig.dataSource().facilitySecurity()).thenReturn(null);

    String result = whereClauseService.buildPermissionFragment(reportConfig, 'N');

    // The output wrapper is structural: "()" inside a Joiner, meaning empty elements yield "()"
    assertThat(result).isEmpty();
  }

  @Test
  void should_append_facility_uid_when_facility_security_is_active() {
    ReportConfiguration reportConfig = createReportConfig(List.of(), List.of(), null);
    when(reportConfig.dataSource().jurisdictionSecurity()).thenReturn('N');
    when(reportConfig.dataSource().facilitySecurity()).thenReturn('Y');

    mockAuthenticatedUser(54321L);

    String result = whereClauseService.buildPermissionFragment(reportConfig, 'Y');

    assertThat(result).isEqualTo("((REPORTING_FACILITY_UID = 54321))");
  }

  @Test
  void should_append_jurisdiction_oids_when_active() {
    // Pass 'S' (Shared/Public) directly into the configuration metadata
    ReportConfiguration reportConfig = createReportConfig(List.of(), List.of(), 'S');

    when(reportConfig.dataSource().jurisdictionSecurity()).thenReturn('Y');
    when(reportConfig.dataSource().facilitySecurity()).thenReturn('N');

    // Stub the permission scope resolver data
    PermissionScope mockScope = Mockito.mock(PermissionScope.class);
    when(mockScope.any()).thenReturn(List.of(101L, 102L));

    Permission expectedPermission = new Permission("REPORTING", "VIEWREPORTPUBLIC");
    when(scopeResolver.resolve(expectedPermission)).thenReturn(mockScope);

    // Directly call the fragment builder
    String result = whereClauseService.buildPermissionFragment(reportConfig, reportConfig.shared());

    assertThat(result).isEqualTo("((program_jurisdiction_oid IN (101, 102)))");
  }

  @Test
  void should_combine_both_jurisdiction_and_facility_security_with_and() {
    ReportConfiguration reportConfig = createReportConfig(List.of(), List.of(), 'S');
    when(reportConfig.dataSource().jurisdictionSecurity()).thenReturn('Y');
    when(reportConfig.dataSource().facilitySecurity()).thenReturn('Y');

    mockAuthenticatedUser(54321L);

    PermissionScope mockScope = Mockito.mock(PermissionScope.class);
    when(mockScope.any()).thenReturn(List.of(50L));
    when(scopeResolver.resolve(new Permission("REPORTING", "VIEWREPORTPUBLIC")))
        .thenReturn(mockScope);

    String result = whereClauseService.buildPermissionFragment(reportConfig, reportConfig.shared());

    assertThat(result)
        .isEqualTo("((program_jurisdiction_oid IN (50)) AND (REPORTING_FACILITY_UID = 54321))");
  }

  @Test
  void should_ignore_jurisdiction_security_if_shared_character_is_unrecognized() {
    // Pass an invalid character like 'X'
    ReportConfiguration reportConfig = createReportConfig(List.of(), List.of(), 'X');
    when(reportConfig.dataSource().jurisdictionSecurity()).thenReturn('Y');
    when(reportConfig.dataSource().facilitySecurity()).thenReturn('N');

    String result = whereClauseService.buildPermissionFragment(reportConfig, reportConfig.shared());

    assertThat(result).isEmpty();
  }

  @Test
  void should_return_empty_jurisdiction_criteria_when_user_has_no_assigned_ids() {
    ReportConfiguration reportConfig = createReportConfig(List.of(), List.of(), 'S');
    when(reportConfig.dataSource().jurisdictionSecurity()).thenReturn('Y');
    when(reportConfig.dataSource().facilitySecurity()).thenReturn('N');

    PermissionScope emptyScope = Mockito.mock(PermissionScope.class);
    when(emptyScope.any()).thenReturn(List.of()); // Empty list
    when(scopeResolver.resolve(new Permission("REPORTING", "VIEWREPORTPUBLIC")))
        .thenReturn(emptyScope);

    String result = whereClauseService.buildPermissionFragment(reportConfig, reportConfig.shared());

    assertThat(result).isEmpty();
  }

  @Test
  void should_omit_facility_criteria_if_external_org_uid_is_null() {
    ReportConfiguration reportConfig = createReportConfig(List.of(), List.of(), 'S');
    when(reportConfig.dataSource().jurisdictionSecurity()).thenReturn('N');
    when(reportConfig.dataSource().facilitySecurity()).thenReturn('Y');

    mockAuthenticatedUser(null);

    String result = whereClauseService.buildPermissionFragment(reportConfig, reportConfig.shared());

    assertThat(result).isEmpty();
  }

  @Test
  void should_combine_basic_filters_and_permission_clauses_with_and() {
    Long filterUid = 100L;
    Long columnUid = 2L;

    // 1. Setup basic text filter configuration
    BasicFilterConfiguration config =
        createBasicFilterConfiguration(List.of(), filterUid, columnUid, false, "BAS_TXT");
    ReportColumn reportColumn = mockReportColumn(columnUid, "STRING", "StateCode");

    // 2. Enable facility security with a 'S' (Public) shared context
    ReportConfiguration reportConfig =
        createReportConfig(List.of(config), List.of(reportColumn), 'S');
    when(reportConfig.dataSource().jurisdictionSecurity()).thenReturn('N');
    when(reportConfig.dataSource().facilitySecurity()).thenReturn('Y');

    // Set up authenticated user profile
    mockAuthenticatedUser(8888L);

    // 3. Assemble execution payload matching the configured filter UID
    ReportExecutionRequest executionRequest = Mockito.mock(ReportExecutionRequest.class);
    when(executionRequest.basicFilters())
        .thenReturn(List.of(new BasicFilterRequest(filterUid, List.of("GA"), false)));

    // 4. Run the full orchestrator
    String result = whereClauseService.buildWhereClause(reportConfig, executionRequest);

    // 5. Verify both strings merged cleanly with zero dangling operators
    assertThat(result)
        .isEqualTo("WHERE ([StateCode] IN ('GA')) AND ((REPORTING_FACILITY_UID = 8888))");
  }

  @Test
  void should_apply_where_prefix_to_permissions_only_when_basic_filters_are_empty() {
    // 1. Setup config with zero basic filters, but with active jurisdiction visibility controls
    ReportConfiguration reportConfig = createReportConfig(List.of(), List.of(), 'S');
    when(reportConfig.dataSource().jurisdictionSecurity()).thenReturn('Y');
    when(reportConfig.dataSource().facilitySecurity()).thenReturn('N');

    // Stub the permission context return mapping
    PermissionScope mockScope = Mockito.mock(PermissionScope.class);
    when(mockScope.any()).thenReturn(List.of(77L));
    when(scopeResolver.resolve(new Permission("REPORTING", "VIEWREPORTPUBLIC")))
        .thenReturn(mockScope);

    // 2. Setup user context requesting a report with no filters
    ReportExecutionRequest executionRequest = Mockito.mock(ReportExecutionRequest.class);
    when(executionRequest.basicFilters()).thenReturn(List.of()); // Wide open request

    // 3. Invoke full compiler
    String result = whereClauseService.buildWhereClause(reportConfig, executionRequest);

    // 4. Verify formatting treats permissions as the primary clause component
    assertThat(result).isEqualTo("WHERE ((program_jurisdiction_oid IN (77)))");
  }
}
