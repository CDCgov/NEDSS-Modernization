package gov.cdc.nbs.report;

import static gov.cdc.nbs.report.ReportConstants.BAS_TIME_RANGE_TYPES;
import static gov.cdc.nbs.report.ReportConstants.BAS_TYPES;
import static gov.cdc.nbs.report.ReportConstants.SQL_AND;
import static gov.cdc.nbs.report.ReportConstants.SQL_WHERE;

import gov.cdc.nbs.report.models.BasicFilterConfiguration;
import gov.cdc.nbs.report.models.BasicFilterRequest;
import gov.cdc.nbs.report.models.FilterType;
import gov.cdc.nbs.report.models.ReportColumn;
import gov.cdc.nbs.report.models.ReportConfiguration;
import gov.cdc.nbs.report.models.ReportExecutionRequest;
import gov.cdc.nbs.report.utils.FieldFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.StringJoiner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/** Service to generate the SQL WHERE clause from a report configuration. */
@Service
@RequiredArgsConstructor
public class WhereClauseService {

  private final FieldFormatter fieldFormatter;

  /**
   * Generates a complete SQL WHERE clause for a report execution.
   *
   * @param reportConfig The metadata configuration for the report being executed.
   * @param executionRequest The specific filter values and columns requested by the user.
   * @return A string starting with "WHERE " followed by the filter criteria, or an empty string if
   *     no filters are applied.
   */
  public String buildWhereClause(
      ReportConfiguration reportConfig, ReportExecutionRequest executionRequest) {

    // StringJoiner provides the "WHERE " prefix and " AND " delimiters between filter statements
    StringJoiner finalWhere = new StringJoiner(SQL_AND, SQL_WHERE, "");

    String basicWhereFragment =
        buildBasicWhereFragment(reportConfig, executionRequest.basicFilters());

    finalWhere.add(basicWhereFragment);

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
        basicCriteria.add(buildBasicTimeRangeCriteria(filterRequest, column));
      }
    }

    return basicCriteria.toString();
  }

  /**
   * Builds a standard multi-value SQL criteria using an IN clause. Result format: ([COLUMN_NAME] IN
   * ('Val1', 'Val2') OR [COLUMN_NAME] IS NULL)
   *
   * @param basicFilterRequest The user-provided values.
   * @param column The metadata for the column being filtered.
   * @return A parenthesized SQL fragment.
   */
  private String buildBasicFilterCriteria(
      BasicFilterRequest basicFilterRequest, ReportColumn column) {

    boolean includeNulls = basicFilterRequest.includeNulls();

    List<String> values = basicFilterRequest.values();
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
          .append(" IN (")
          .append(String.join(", ", formattedValues))
          .append(")");
    }

    if (includeNulls) {
      // If we already have values, use OR to join them with the NULL check
      if (hasValues) {
        criteria.append(" OR ");
      }
      criteria.append(colName).append(" IS NULL");
    }

    return criteria.append(")").toString();
  }

  /**
   * Builds an SQL date/time range criteria using a BETWEEN clause. Result format: (([COLUMN_NAME]
   * BETWEEN 'Start' AND 'End') OR ([COLUMN_NAME] IS NULL))
   *
   * @param basicFilterRequest The user-provided date range values.
   * @param column The metadata for the column being filtered.
   * @return A parenthesized SQL fragment.
   */
  private String buildBasicTimeRangeCriteria(
      BasicFilterRequest basicFilterRequest, ReportColumn column) {
    List<String> values = basicFilterRequest.values();
    boolean includeNulls = basicFilterRequest.includeNulls();

    if (values.size() != 2 && !includeNulls) return "";

    // Transform raw client values into type-formatted strings
    List<String> formattedValues = fieldFormatter.convertToSQLFromDateRange(values);

    StringBuilder criteria = new StringBuilder("(");

    String colName = "[" + column.name() + "]";

    criteria
        .append(colName)
        .append(" BETWEEN ")
        .append(formattedValues.get(0))
        .append(SQL_AND)
        .append(formattedValues.get(1));

    if (includeNulls) {
      criteria.insert(0, "(").append(") OR (").append(colName).append(" IS NULL").append(")");
    }

    return criteria.append(")").toString();
  }

  /** Retrieves the column metadata associated with a specific column UID. */
  private Optional<ReportColumn> findColumn(
      ReportConfiguration reportConfig, Long reportColumnUid) {
    return reportConfig.reportColumns().stream()
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
}
