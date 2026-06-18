package gov.cdc.nbs.report;

import static gov.cdc.nbs.report.ReportConstants.BAS_CODES_NO_COLUMN;
import static gov.cdc.nbs.report.ReportConstants.BAS_TIME_RANGE_TYPES;
import static gov.cdc.nbs.report.ReportConstants.BAS_TYPES;
import static gov.cdc.nbs.report.ReportConstants.COMPARISON_OPERATORS;
import static gov.cdc.nbs.report.ReportConstants.Operator;
import static gov.cdc.nbs.report.ReportConstants.RDB_LAB_RESULT_VAL_COLS;
import static gov.cdc.nbs.report.ReportConstants.SQL_AND;
import static gov.cdc.nbs.report.ReportConstants.SQL_WHERE;

import gov.cdc.nbs.authorization.permission.Permission;
import gov.cdc.nbs.authorization.permission.scope.PermissionScope;
import gov.cdc.nbs.authorization.permission.scope.PermissionScopeResolver;
import gov.cdc.nbs.config.security.SecurityUtil;
import gov.cdc.nbs.datasource.utils.DataSourceNameUtils;
import gov.cdc.nbs.report.models.AdvancedFilterRequest;
import gov.cdc.nbs.report.models.AdvancedQuery;
import gov.cdc.nbs.report.models.BasicFilterConfiguration;
import gov.cdc.nbs.report.models.BasicFilterRequest;
import gov.cdc.nbs.report.models.FilterType;
import gov.cdc.nbs.report.models.ReportColumn;
import gov.cdc.nbs.report.models.ReportConfiguration;
import gov.cdc.nbs.report.models.ReportExecutionRequest;
import gov.cdc.nbs.report.utils.FieldFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/** Service to generate the SQL WHERE clause from a report configuration. */
@Service
@RequiredArgsConstructor
public class WhereClauseService {

  private final FieldFormatter fieldFormatter;
  private final PermissionScopeResolver scopeResolver;

  private static final String LAB_RESULT_QUERY_VAL =
      SQL_WHERE + "root_ordered_test_pntr IN (SELECT root_ordered_test_pntr FROM %s %s)";

  Map<Operator, BiFunction<AdvancedQuery.Rule, ReportColumn, String>> advQueryOperations =
      Map.ofEntries(
          Map.entry(
              Operator.EQ,
              (rule, column) ->
                  isCodedType(column)
                      ? buildAdvFilterCriteria(rule, column)
                      : buildAdvComparisonCriteria(rule, column)),
          Map.entry(
              Operator.NE,
              (rule, column) ->
                  isCodedType(column)
                      ? buildAdvFilterCriteria(rule, column)
                      : buildAdvComparisonCriteria(rule, column)),
          Map.entry(Operator.IN, this::buildAdvNullCriteria),
          Map.entry(Operator.NN, this::buildAdvNullCriteria),
          Map.entry(Operator.SW, this::buildAdvLikeCriteria),
          Map.entry(Operator.CO, this::buildAdvLikeCriteria),
          Map.entry(Operator.BW, this::buildAdvBetweenCriteria),
          Map.entry(Operator.LT, this::buildAdvComparisonCriteria),
          Map.entry(Operator.GT, this::buildAdvComparisonCriteria),
          Map.entry(Operator.LE, this::buildAdvComparisonCriteria),
          Map.entry(Operator.GE, this::buildAdvComparisonCriteria));

  /**
   * Generates a complete SQL WHERE clause for a report execution. Process both the basic and
   * advanced where clauses as well as if filtering by certain RDB.LAB_TEST_REPORT table columns
   *
   * @param reportConfig The metadata configuration for the report being executed.
   * @param executionRequest The specific filter values and columns requested by the user.
   * @param dataSourceNameUtils Utility to standardize datasource naming
   * @return A string starting with "WHERE " followed by the filter criteria, or an empty string if
   *     no filters are applied.
   */
  public String buildWhereClause(
      ReportConfiguration reportConfig,
      ReportExecutionRequest executionRequest,
      DataSourceNameUtils dataSourceNameUtils) {

    List<String> activeClauses = new ArrayList<>();

    String basicWhereFragment =
        buildBasicWhereFragment(reportConfig, executionRequest.basicFilters());
    if (!basicWhereFragment.isBlank()) {
      activeClauses.add(basicWhereFragment);
    }

    String advWhereFragment =
        buildAdvancedQueryResult(reportConfig, executionRequest.advancedFilter());
    if (advWhereFragment != null && !advWhereFragment.isBlank()) {
      activeClauses.add(advWhereFragment);
    }

    boolean hasLabResultVal = hasLabResultVal(reportConfig, executionRequest.advancedFilter());
    if (hasLabResultVal) {
      String rdbDataSource = dataSourceNameUtils.buildDataSourceName("nbs_rdb.lab_test_report");
      String labResultQueryValFragment =
          LAB_RESULT_QUERY_VAL.formatted(
              rdbDataSource, SQL_WHERE + String.join(SQL_AND, activeClauses));

      // Add permission fragment to outer where
      String permissionFragment = buildPermissionFragment(reportConfig);
      if (!permissionFragment.isBlank()) {
        return labResultQueryValFragment + SQL_AND + buildPermissionFragment(reportConfig);
      }

      return labResultQueryValFragment;
    }

    String permissionFragment = buildPermissionFragment(reportConfig);
    if (!permissionFragment.isBlank()) {
      activeClauses.add(permissionFragment);
    }

    if (activeClauses.isEmpty()) {
      return "";
    }

    return SQL_WHERE + String.join(SQL_AND, activeClauses);
  }

  public String buildPermissionFragment(ReportConfiguration reportConfig) {
    List<String> permissionClauses = new ArrayList<>();

    String jpCriteria =
        getJurisProgramRestrictionCriteria(
            reportConfig.dataSource().hasJurisdictionSecurity(), reportConfig.group());
    if (!jpCriteria.isBlank()) {
      permissionClauses.add(jpCriteria);
    }

    String reportingFacilityCriteria =
        getReportingFacilityRestrictionCriteria(reportConfig.dataSource().hasFacilitySecurity());
    if (!reportingFacilityCriteria.isBlank()) {
      permissionClauses.add(reportingFacilityCriteria);
    }

    if (permissionClauses.isEmpty()) {
      return "";
    }

    return "(" + String.join(SQL_AND, permissionClauses) + ")";
  }

  /**
   * Constructs the SQL criteria block to restrict data visibility by program area and jurisdiction.
   *
   * <p>If jurisdiction security is not set for the data source, this method returns an empty string
   * indicating no juris or prog area restrictions. If security is set but the user possesses no
   * valid scopes, an exception is thrown to prevent silent data exposure.
   *
   * @param hasJurisdictionSecurity Indicates if jurisdiction and program area isolation is set.
   * @param group The report group used to derive the specific visibility permission.
   * @return A parenthesized SQL predicate clause (e.g., {@code "(program_jurisdiction_oid IN (1,
   *     2))"}). Returns an empty string {@code ""} if jurisdiction/program area security is not
   *     set.
   * @throws IllegalArgumentException If jurisdiction/progam area security is set but the user's
   *     resolved {@link PermissionScope} contains no assigned identifiers.
   */
  private String getJurisProgramRestrictionCriteria(
      boolean hasJurisdictionSecurity, ReportConstants.ReportGroup group) {
    final String NO_JURIS_RESTRICTION = "";
    if (!hasJurisdictionSecurity) {
      return NO_JURIS_RESTRICTION;
    }

    PermissionScope scope = this.scopeResolver.resolve(mapSharedToPermission(group));
    if (scope.any().isEmpty()) {
      throw new IllegalArgumentException(
          "No Jurisdiction or Program Area permissions found for user: %s for group: %s"
              .formatted(SecurityUtil.getUserDetails().getUsername(), group));
    }

    String ids = scope.any().stream().map(String::valueOf).collect(Collectors.joining(", "));

    return "(program_jurisdiction_oid IN (" + ids + "))";
  }

  /**
   * Constructs the SQL criteria block to restrict data visibility by reporting facility.
   *
   * @param hasReportingFacilitySecurity Indicates if facility-level isolation has been set.
   * @return A parenthesized SQL predicate clause (e.g., {@code "(REPORTING_FACILITY_UID = 123)"}).
   *     Returns an empty string {@code ""} when no facility restrictions apply, indicating the
   *     query should run for all facilities.
   */
  private String getReportingFacilityRestrictionCriteria(boolean hasReportingFacilitySecurity) {
    final String NO_FACILITY_RESTRICTION = "";

    if (!hasReportingFacilitySecurity) {
      return NO_FACILITY_RESTRICTION;
    }

    Long externalOrgId = SecurityUtil.getUserDetails().getExternalOrgUid();
    if (externalOrgId == null) {
      return NO_FACILITY_RESTRICTION;
    }

    return "(REPORTING_FACILITY_UID = " + externalOrgId + ")";
  }

  /** Pure mapper utility isolating the business rules matching flags to permissions. */
  private Permission mapSharedToPermission(ReportConstants.ReportGroup group) {
    return switch (group) {
      case TEMPLATE ->
          new Permission(
              ReportConstants.Permissions.VIEWREPORTTEMPLATE,
              ReportConstants.Permissions.REPORTINGOBJECT);
      case PRIVATE ->
          new Permission(
              ReportConstants.Permissions.VIEWREPORTPRIVATE,
              ReportConstants.Permissions.REPORTINGOBJECT);
      case PUBLIC ->
          new Permission(
              ReportConstants.Permissions.VIEWREPORTPUBLIC,
              ReportConstants.Permissions.REPORTINGOBJECT);
      case REPORTING_FACILITY ->
          new Permission(
              ReportConstants.Permissions.VIEWREPORTREPORTINGFACILITY,
              ReportConstants.Permissions.REPORTINGOBJECT);
    };
  }

  /**
   * Processes a list of basic filter requests into a joined SQL fragment.
   *
   * @param reportConfig used to map filter UIDs to database columns.
   * @param basicFilterRequests The list of filter UIDs and values provided in the execution
   *     request.
   * @return A joined SQL string (e.g., "(col IN (...)) AND (col BETWEEN ...)") without the "WHERE"
   *     prefix.
   */
  public String buildBasicWhereFragment(
      ReportConfiguration reportConfig, List<BasicFilterRequest> basicFilterRequests) {
    if (basicFilterRequests == null || basicFilterRequests.isEmpty()) return "";

    StringJoiner basicCriteria = new StringJoiner(SQL_AND);

    for (BasicFilterRequest filterRequest : basicFilterRequests) {
      // Find the Filter Configuration
      BasicFilterConfiguration config =
          findBasicFilterConfiguration(reportConfig, filterRequest.reportFilterUid())
              .orElseThrow(
                  () ->
                      new IllegalArgumentException(
                          "No basic filter configuration found for UID: "
                              + filterRequest.reportFilterUid()));
      // BAS_CODES_NO_COLUMN are non-column filters which are intercepted and processed
      // independently during execution spec builds.
      if (config.filterType() != null && BAS_CODES_NO_COLUMN.contains(config.filterType().code())) {
        continue;
      }

      // Find the associated Column
      ReportColumn column =
          findColumn(reportConfig, config.reportColumnUid())
              .orElseThrow(
                  () ->
                      new IllegalArgumentException(
                          "No report column found for columnUid: " + config.reportColumnUid()));

      // Determine the formatting strategy
      String type =
          Optional.ofNullable(config.filterType())
              .map(FilterType::type)
              .orElseThrow(
                  () ->
                      new IllegalArgumentException(
                          "Filter type is missing for Report Filter ID: "
                              + config.reportFilterUid()));

      if (BAS_TYPES.contains(type)) {
        basicCriteria.add(buildBasicFilterCriteria(filterRequest, column));
      } else if (BAS_TIME_RANGE_TYPES.contains(type)) {
        basicCriteria.add(buildBasicBetweenCriteria(filterRequest, column));
      }
    }

    return basicCriteria.toString();
  }

  /**
   * Processes an advanced filter request into a joined SQL fragment.
   *
   * @param config used to map columnIds to database columns.
   * @param advancedFilterRequest The advanced filter request provided in the execution request.
   * @return A joined SQL string without the "WHERE" prefix
   */
  public String buildAdvancedQueryResult(
      ReportConfiguration config, AdvancedFilterRequest advancedFilterRequest) {
    if (advancedFilterRequest == null) return "";
    return buildAdvancedQuery(config, advancedFilterRequest.value());
  }

  /**
   * Performs a preorder traversal of the advanced filter request's Rule values to build a joined
   * SQL fragment.
   *
   * @return A joined SQL string without the "WHERE" prefix
   */
  private String buildAdvancedQuery(ReportConfiguration config, AdvancedQuery query) {
    if (query.getClass().equals(AdvancedQuery.Rule.class)) {
      AdvancedQuery.Rule rule = (AdvancedQuery.Rule) query;
      return buildFormattedAdvancedCriteria(config, rule);
    }

    if (query.getClass().equals(AdvancedQuery.RuleGroup.class)) {
      AdvancedQuery.RuleGroup ruleGroup = (AdvancedQuery.RuleGroup) query;
      if (ruleGroup.rules().isEmpty()) return "";

      String combinator = String.format(" %s ", ruleGroup.combinator().toString().toUpperCase());
      StringJoiner joiner = new StringJoiner(combinator, "(", ")");

      for (AdvancedQuery rule : ruleGroup.rules()) {
        String innerRuleSql = buildAdvancedQuery(config, rule);
        if (!innerRuleSql.isEmpty()) {
          joiner.add(innerRuleSql);
        }
      }

      return joiner.toString();
    }

    throw new IllegalArgumentException("Invalid advanced filter");
  }

  private String buildFormattedAdvancedCriteria(
      ReportConfiguration config, AdvancedQuery.Rule rule) {
    Operator operator = getOperator(rule);
    ReportColumn column =
        findColumn(config, rule.columnId()).orElseThrow(IllegalArgumentException::new);
    return Optional.ofNullable(advQueryOperations.get(operator))
        .map(fn -> fn.apply(rule, column))
        .orElseThrow(() -> new IllegalArgumentException("Unsupported operator: " + operator));
  }

  /**
   * Builds a standard multi-value SQL criteria using the IN operator. Possible result formats:
   *
   * <ul>
   *   <li>([COLUMN_NAME] IN ('Val1', 'Val2'))
   *   <li>([COLUMN_NAME] NOT IN ('Val1', 'Val2') OR [COLUMN_NAME] IS NULL)
   *   <li>([COLUMN_NAME] IN ('Val1', 'Val2') OR [COLUMN_NAME] IS NULL)
   * </ul>
   *
   * @param values The user-provided values.
   * @param column The metadata for the column being filtered.
   * @param includeNulls adds " OR [COLUMN_NAME] IS NULL"
   * @param negateCriteria adds "NOT" operator
   * @return A parenthesized SQL fragment.
   */
  private String buildFilterCriteria(
      List<String> values, ReportColumn column, boolean includeNulls, boolean negateCriteria) {
    if (values.isEmpty() && !includeNulls) return "";

    // Delegate type-specific escaping and quoting to the FieldFormatter
    List<String> formattedValues =
        values.stream()
            .filter(Objects::nonNull)
            .map(v -> fieldFormatter.formatField(column.sourceTypeCode(), v))
            .toList();

    // Throw if no values were produced but values were expected
    if (!values.isEmpty() && formattedValues.isEmpty()) {
      throw new IllegalArgumentException(
          "No valid formatted values produced for column: " + column.name());
    }

    // Throw if the count doesn't match (indicates nulls were filtered or mapping failed)
    if (formattedValues.size() != values.size()) {
      throw new IllegalStateException(
          String.format(
              "Value mismatch for column [%s]: Expected %d values but only %d were successfully formatted",
              column.name(), values.size(), formattedValues.size()));
    }

    StringBuilder criteria = new StringBuilder("(");
    String colName = fieldFormatter.convertToSQLColName(column.name(), column.sourceTypeCode());
    boolean hasValues = !formattedValues.isEmpty();

    // Append the IN clause if actual data values were provided
    if (hasValues) {
      criteria
          .append(colName)
          .append(negateCriteria ? " NOT" : "")
          .append(" IN (")
          .append(String.join(", ", formattedValues))
          .append(")");
    }

    if (includeNulls) {
      // If we already have values, use OR to join them with the NULL check
      if (hasValues) {
        criteria.append(" OR ");
      }
      criteria.append(buildNullCriteria(column, false));
    }

    return criteria.append(")").toString();
  }

  private String buildBasicFilterCriteria(
      BasicFilterRequest basicFilterRequest, ReportColumn column) {
    return buildFilterCriteria(
        basicFilterRequest.values(), column, basicFilterRequest.includeNulls(), false);
  }

  private String buildAdvFilterCriteria(AdvancedQuery.Rule rule, ReportColumn column) {
    List<String> values = Arrays.asList(rule.value().split("\\|"));
    boolean isNEOperator = getOperator(rule).equals(Operator.NE);
    return buildFilterCriteria(values, column, isNEOperator, isNEOperator);
  }

  /**
   * Builds a SQL criteria using the BETWEEN operator. Possible result formats:
   *
   * <ul>
   *   <li>(([COLUMN_NAME] BETWEEN 'Start' * AND 'End') OR ([COLUMN_NAME] IS NULL))
   *   <li>([COLUMN_NAME] NOT IN ('Val1', 'Val2') OR [COLUMN_NAME] IS NULL)
   *   <li>([COLUMN_NAME] IN ('Val1', 'Val2') OR [COLUMN_NAME] IS NULL)
   * </ul>
   *
   * @param values The user-provided values.
   * @param column The metadata for the column being filtered.
   * @param includeNulls adds " OR ([COLUMN_NAME] IS NULL)"
   * @return A parenthesized SQL fragment.
   */
  private String buildBetweenCriteria(
      List<String> values, ReportColumn column, boolean includeNulls) {
    if (values.size() != 2 && !includeNulls) return "";

    String colType = column.sourceTypeCode();
    List<String> formattedValues;

    if (colType.equals("DATE") || colType.equals("DATETIME")) {
      formattedValues = fieldFormatter.convertToSQLFromDateRange(values);
    } else {
      formattedValues =
          values.stream()
              .filter(Objects::nonNull)
              .map(v -> fieldFormatter.formatField(colType, v))
              .toList();
    }

    StringBuilder criteria = new StringBuilder("(");

    String colName = fieldFormatter.convertToSQLColName(column.name(), colType);

    criteria
        .append(colName)
        .append(" BETWEEN ")
        .append(formattedValues.get(0))
        .append(SQL_AND)
        .append(formattedValues.get(1));

    if (includeNulls) {
      criteria.insert(0, "(").append(") OR (").append(buildNullCriteria(column, false)).append(")");
    }

    return criteria.append(")").toString();
  }

  private String buildBasicBetweenCriteria(
      BasicFilterRequest basicFilterRequest, ReportColumn column) {
    List<String> values = basicFilterRequest.values();
    boolean includeNulls = basicFilterRequest.includeNulls();

    return buildBetweenCriteria(values, column, includeNulls);
  }

  private String buildAdvBetweenCriteria(AdvancedQuery.Rule rule, ReportColumn column) {
    List<String> values = Arrays.asList(rule.value().split(","));
    return buildBetweenCriteria(values, column, false);
  }

  /**
   * Builds a SQL criteria using the NULL operator. Possible result formats:
   *
   * <ul>
   *   <li>[COLUMN_NAME] IS NULL
   *   <li>[COLUMN_NAME] IS NOT NULL
   * </ul>
   *
   * @param column The metadata for the column being filtered.
   * @param negateCriteria adds "NOT" operator
   * @return a SQL fragment.
   */
  private String buildNullCriteria(ReportColumn column, boolean negateCriteria) {
    return String.format(
        "%s IS %sNULL",
        fieldFormatter.convertToSQLColName(column.name(), column.sourceTypeCode()),
        negateCriteria ? "NOT " : "");
  }

  private String buildAdvNullCriteria(AdvancedQuery.Rule rule, ReportColumn column) {
    boolean negateCriteria = getOperator(rule).equals(Operator.NN);
    return "(" + buildNullCriteria(column, negateCriteria) + ")";
  }

  /**
   * Builds a SQL criteria using the LIKE operator. Possible result formats:
   *
   * <ul>
   *   <li>[COLUMN_NAME] IS LIKE 'val%'
   *   <li>[COLUMN_NAME] IS LIKE '%val%'
   * </ul>
   *
   * @param rule the advanced query rule
   * @param column The metadata for the column being filtered.
   * @return a SQL fragment.
   */
  private String buildAdvLikeCriteria(AdvancedQuery.Rule rule, ReportColumn column) {
    StringBuilder criteria = new StringBuilder("(");
    boolean isContains = getOperator(rule).equals(Operator.CO);

    return criteria
        .append(fieldFormatter.convertToSQLColName(column.name(), column.sourceTypeCode()))
        .append(" LIKE CONCAT(")
        .append(isContains ? "'%', " : "")
        .append(fieldFormatter.formatField(column.sourceTypeCode(), rule.value()))
        .append(", '%')")
        .append(")")
        .toString();
  }

  /**
   * Builds a SQL criteria using comparison operators. Possible result formats:
   *
   * <ul>
   *   <li>[COLUMN_NAME] <> 'val' OR [COLUMN_NAME] IS NULL
   *   <li>[COLUMN_NAME] != 'val'
   *   <li>[COLUMN_NAME] > val
   *   <li>[COLUMN_NAME] <= val
   * </ul>
   *
   * @param rule the advanced query rule
   * @param column The metadata for the column being filtered.
   * @return a SQL fragment.
   */
  private String buildAdvComparisonCriteria(AdvancedQuery.Rule rule, ReportColumn column) {
    Operator operator = getOperator(rule);
    String sqlOperator =
        Optional.ofNullable(COMPARISON_OPERATORS.get(operator))
            .orElseThrow(
                () -> new IllegalArgumentException("Unsupported comparison operator: " + operator));
    String colName = fieldFormatter.convertToSQLColName(column.name(), column.sourceTypeCode());
    String value = fieldFormatter.formatField(column.sourceTypeCode(), rule.value());

    String nullCriteria = "";
    if (getOperator(rule).equals(Operator.NE)) {
      nullCriteria = String.format(" OR %s", buildAdvNullCriteria(rule, column));
    }
    return String.format("(%s %s %s%s)", colName, sqlOperator, value, nullCriteria);
  }

  /** Retrieves the column metadata associated with a specific column UID. */
  private Optional<ReportColumn> findColumn(
      ReportConfiguration reportConfig, Long reportColumnUid) {
    return reportConfig.columns().stream()
        .filter(rc -> Objects.equals(rc.id(), reportColumnUid))
        .findFirst();
  }

  /** Retrieves the filter configuration associated with a specific report filter UID. */
  private Optional<BasicFilterConfiguration> findBasicFilterConfiguration(
      ReportConfiguration reportConfig, Long reportFilterUid) {
    return reportConfig.basicFilters().stream()
        .filter(bf -> Objects.equals(bf.reportFilterUid(), reportFilterUid))
        .findFirst();
  }

  private boolean isCodedType(ReportColumn column) {
    return column.codesetNm() != null && !column.codesetNm().isEmpty();
  }

  /** Checks if the AdvancedFilterRequest contains a column in RDB_LAB_RESULT_VAL_COLS */
  private boolean hasLabResultVal(ReportConfiguration config, AdvancedFilterRequest filterRequest) {
    if (filterRequest == null) return false;

    List<Long> columnIds = extractColumnIds(filterRequest.value());
    return columnIds.stream()
        .distinct()
        .map(id -> findColumn(config, id))
        .flatMap(Optional::stream)
        .map(ReportColumn::name)
        .anyMatch(RDB_LAB_RESULT_VAL_COLS::contains);
  }

  private List<Long> extractColumnIds(AdvancedQuery query) {
    List<Long> columnIds = new ArrayList<>();
    if (query.getClass().equals(AdvancedQuery.Rule.class)) {
      AdvancedQuery.Rule rule = (AdvancedQuery.Rule) query;
      columnIds.add(rule.columnId());
    } else if (query.getClass().equals(AdvancedQuery.RuleGroup.class)) {
      AdvancedQuery.RuleGroup ruleGroup = (AdvancedQuery.RuleGroup) query;
      for (AdvancedQuery subRule : ruleGroup.rules()) {
        columnIds.addAll(extractColumnIds(subRule));
      }
    }
    return columnIds;
  }

  private Operator getOperator(AdvancedQuery.Rule rule) {
    return Operator.valueOf(rule.operator().toUpperCase());
  }
}
