package gov.cdc.nbs.report;

import static gov.cdc.nbs.report.ReportConstants.BAS_TIME_RANGE_TYPES;
import static gov.cdc.nbs.report.ReportConstants.BAS_TYPES;
import static gov.cdc.nbs.report.ReportConstants.COMPARISON_OPERATORS;
import static gov.cdc.nbs.report.ReportConstants.Operator;
import static gov.cdc.nbs.report.ReportConstants.RDB_LAB_RESULT_VAL_COLS;
import static gov.cdc.nbs.report.ReportConstants.SQL_AND;
import static gov.cdc.nbs.report.ReportConstants.SQL_WHERE;

import gov.cdc.nbs.datasource.utils.DataSourceNameUtils;
import gov.cdc.nbs.report.models.AdvancedFilterRequest;
import gov.cdc.nbs.report.models.AdvancedQuery;
import gov.cdc.nbs.report.models.AdvancedQueryResult;
import gov.cdc.nbs.report.models.BasicFilterConfiguration;
import gov.cdc.nbs.report.models.BasicFilterRequest;
import gov.cdc.nbs.report.models.FilterType;
import gov.cdc.nbs.report.models.ReportColumn;
import gov.cdc.nbs.report.models.ReportConfiguration;
import gov.cdc.nbs.report.models.ReportExecutionRequest;
import gov.cdc.nbs.report.utils.FieldFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.function.BiFunction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/** Service to generate the SQL WHERE clause from a report configuration. */
@Service
@RequiredArgsConstructor
public class WhereClauseService {

  private final FieldFormatter fieldFormatter;

  private static final String LAB_RESULT_QUERY_VAL =
      "root_ordered_test_pntr IN (SELECT root_ordered_test_pntr FROM %s";

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
   * advanced where clauses
   *
   * @param reportConfig The metadata configuration for the report being executed.
   * @param executionRequest The specific filter values and columns requested by the user.
   * @param dataSourceNameUtils
   * @return A string starting with "WHERE " followed by the filter criteria, or an empty string if
   *     no filters are applied.
   */
  public String buildWhereClause(
      ReportConfiguration reportConfig,
      ReportExecutionRequest executionRequest,
      DataSourceNameUtils dataSourceNameUtils) {
    String rdbDataSource = dataSourceNameUtils.buildDataSourceName("nbs_rdb.lab_test_report");
    String labResultQuery = String.format(LAB_RESULT_QUERY_VAL, rdbDataSource);

    String basicWhereFragment =
        buildBasicWhereFragment(reportConfig, executionRequest.basicFilters());

    AdvancedQueryResult advancedQueryResult =
        buildAdvancedQueryResult(reportConfig, executionRequest.advancedFilter());
    String advWhereFragment = advancedQueryResult.query();
    boolean hasLabResultVal = advancedQueryResult.hasLabResultVal();

    // StringJoiner provides the "WHERE " prefix and " AND " delimiters between filter statements
    StringJoiner finalWhere = new StringJoiner(SQL_AND, SQL_WHERE, "");

    if (hasLabResultVal) {
      basicWhereFragment = String.format("%s WHERE %s", labResultQuery, basicWhereFragment);
    }
    finalWhere.add(basicWhereFragment);
    if (!advWhereFragment.isEmpty()) {
      finalWhere.add(
          String.format("%s%s", advancedQueryResult.query(), hasLabResultVal ? ")" : ""));
    }

    // Only return the WHERE clause if it contains anything beyond the initial "WHERE " prefix
    return finalWhere.length() > SQL_WHERE.length() ? finalWhere.toString() : "";
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
   * @return AdvancedQueryResult that contains
   *     <ul>
   *       <li>query - a joined SQL string without the "WHERE" prefix
   *       <li>hasLabResultVal - a boolean to determine whether a special clause needs to be added
   *           to handle special columns from the RDB.LAB_RESULT_VAL table
   *     </ul>
   */
  public AdvancedQueryResult buildAdvancedQueryResult(
      ReportConfiguration config, AdvancedFilterRequest advancedFilterRequest) {
    if (advancedFilterRequest == null) return new AdvancedQueryResult("", false);
    return buildAdvancedQuery(config, advancedFilterRequest.value());
  }

  /**
   * Performs a preorder traversal of the advanced filter request's Rule values to build a joined
   * SQL fragment.
   *
   * @return AdvancedQueryResult that contains
   *     <ul>
   *       <li>query - a joined SQL string without the "WHERE" prefix
   *       <li>hasLabResultVal - a boolean to determine whether a special clause needs to be added
   *           to handle special columns from the RDB.LAB_RESULT_VAL table
   *     </ul>
   */
  private AdvancedQueryResult buildAdvancedQuery(ReportConfiguration config, AdvancedQuery query) {
    boolean containsLabResultValCol = false;
    if (query.getClass().equals(AdvancedQuery.Rule.class)) {
      AdvancedQuery.Rule rule = (AdvancedQuery.Rule) query;
      AdvancedQueryResult advQueryRes = buildFormattedAdvancedCriteria(config, rule);
      if (advQueryRes.hasLabResultVal()) {
        containsLabResultValCol = true;
      }
      return new AdvancedQueryResult(advQueryRes.query(), containsLabResultValCol);
    }

    if (query.getClass().equals(AdvancedQuery.RuleGroup.class)) {
      AdvancedQuery.RuleGroup ruleGroup = (AdvancedQuery.RuleGroup) query;
      if (ruleGroup.rules().isEmpty()) return new AdvancedQueryResult("", false);

      String combinator = String.format(" %s ", ruleGroup.combinator().toUpperCase());
      StringJoiner joiner = new StringJoiner(combinator, "(", ")");

      for (AdvancedQuery rule : ruleGroup.rules()) {
        AdvancedQueryResult innerAdvQueryRes = buildAdvancedQuery(config, rule);
        String innerRuleSql = innerAdvQueryRes.query();
        if (!innerRuleSql.isEmpty()) {
          joiner.add(innerRuleSql);
        }
        if (innerAdvQueryRes.hasLabResultVal()) {
          containsLabResultValCol = true;
        }
      }

      return new AdvancedQueryResult(joiner.toString(), containsLabResultValCol);
    }

    throw new IllegalArgumentException("Invalid advanced filter");
  }

  private AdvancedQueryResult buildFormattedAdvancedCriteria(
      ReportConfiguration config, AdvancedQuery.Rule rule) {
    boolean hasLabResultValCol;

    Operator operator = Operator.valueOf(rule.operator().toUpperCase());
    ReportColumn column =
        findColumn(config, rule.columnId()).orElseThrow(IllegalArgumentException::new);
    hasLabResultValCol = RDB_LAB_RESULT_VAL_COLS.contains(column.name().toUpperCase());
    String query =
        Optional.ofNullable(advQueryOperations.get(operator))
            .map(fn -> fn.apply(rule, column))
            .orElseThrow(() -> new IllegalArgumentException("Unsupported operator: " + operator));
    return new AdvancedQueryResult(query, hasLabResultValCol);
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
    String colName = "[" + column.name() + "]"; // Brackets protect against SQL reserved words
    boolean hasValues = !formattedValues.isEmpty();

    // Append the IN clause if actual data values were provided
    if (hasValues) {
      criteria
          .append(colName)
          .append(String.format(" %sIN (", negateCriteria ? "NOT " : ""))
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
    boolean isNEOperator = Operator.valueOf(rule.operator()).equals(Operator.NE);
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

    if (colType.equals("DATE")) {
      formattedValues = fieldFormatter.convertToSQLFromDateRange(values);
    } else {
      formattedValues =
          values.stream()
              .filter(Objects::nonNull)
              .map(v -> fieldFormatter.formatField(column.sourceTypeCode(), v))
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
    boolean negateCriteria = Operator.valueOf(rule.operator()).equals(Operator.NN);
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
    boolean isContains = Operator.valueOf(rule.operator()).equals(Operator.CO);

    return criteria
        .append(fieldFormatter.convertToSQLColName(column.name(), column.sourceTypeCode()))
        .append(" LIKE CONCAT(")
        .append(String.format("'%s', ", isContains ? "%" : ""))
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
    Operator operator = Operator.valueOf(rule.operator().toUpperCase());
    String sqlOperator =
        Optional.ofNullable(COMPARISON_OPERATORS.get(operator))
            .orElseThrow(
                () -> new IllegalArgumentException("Unsupported comparison operator: " + operator));
    String colName = fieldFormatter.convertToSQLColName(column.name(), column.sourceTypeCode());
    String value = fieldFormatter.formatField(column.sourceTypeCode(), rule.value());

    String nullCriteria = "";
    if (Operator.valueOf(rule.operator()).equals(Operator.NE)) {
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
}
