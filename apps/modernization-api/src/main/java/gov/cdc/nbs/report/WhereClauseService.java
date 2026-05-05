package gov.cdc.nbs.report;

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
import java.util.Set;
import java.util.StringJoiner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/** Service to generate the SQL WHERE clause from a report configuration. */
@Service
@RequiredArgsConstructor
public class WhereClauseService {

  private final FieldFormatter fieldFormatter;

  private static final Set<String> BAS_TIME_RANGE_TYPES =
      Set.of("BAS_TIM_RANGE", "BAS_TIM_RANGE_CUSTOM", "BAS_TIM_RANGE_LIST", "BAS_MM_YYYY_RANGE");

  private static final Set<String> BAS_TYPES =
      Set.of("BAS_CON_LIST", "BAS_JUR_LIST", "BAS_CVG_LIST", "BAS_TXT", "BAS_STD_HIV_WRKR");

  /**
   * @param reportConfig
   * @param executionRequest
   * @return
   */
  public String buildWhereClause(
      ReportConfiguration reportConfig, ReportExecutionRequest executionRequest) {

    // StringJoiner provides the "WHERE " prefix and " AND " delimiters between filter statements
    StringJoiner finalWhere = new StringJoiner(" AND ", "WHERE ", "");

    String basicWherefragment =
        buildBasicWhereFragment(reportConfig, executionRequest.basicFilters());

    finalWhere.add(basicWherefragment);

    // Only return the WHERE clause if it contains anything beyond the initial "WHERE " prefix
    return finalWhere.length() > 6 ? finalWhere.toString() : "";
  }

  /**
   * Constructs WHERE clause based on the basic filters for a report. Each basic filter in the
   * configuration is processed and joined with AND logic.
   *
   * @param reportConfig is the full configuration for the report. Most relevant here are the
   *     columns and active filters.
   * @return A formatted SQL string starting with WHERE. An empty string is returned if required
   *     parts are empty
   */
  public String buildBasicWhereFragment(
      ReportConfiguration reportConfig, List<BasicFilterRequest> basicFilterRequests) {
    if (basicFilterRequests == null || basicFilterRequests.isEmpty()) return "";

    StringJoiner basicCriteria = new StringJoiner(" AND ");

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
   * Builds the SQL criateria for date range(e.g., "(COL_NAME BETWEEN DATE1 AND DATE2)")
   *
   * @param basicFilterRequest provides the filter values to the column with and the operators
   * @param column provides the column name and type
   * @return an SQL fragment or empty string
   */
  private String buildBasicFilterCriteria(
      BasicFilterRequest basicFilterRequest, ReportColumn column) {

    List<String> values =
        basicFilterRequest.values() == null ? List.of() : basicFilterRequest.values();
    if (values.isEmpty() && !basicFilterRequest.includeNulls()) return "";

    List<String> formattedValues =
        values.stream()
            .map(v -> fieldFormatter.formatField(column.columnSourceTypeCode(), v))
            .toList();

    StringBuilder criteria = new StringBuilder("(");
    String colName = "[" + column.columnName() + "]"; // Brackets protect against SQL reserved words
    boolean hasValues = !formattedValues.isEmpty();

    // Append the IN clause if actual data values were provided
    if (hasValues) {
      criteria
          .append(colName)
          .append(" IN (")
          .append(String.join(", ", formattedValues))
          .append(")");
    }

    if (basicFilterRequest.includeNulls()) {
      // If we already have values, use OR to join them with the NULL check
      if (hasValues) {
        criteria.append(" OR ");
      }
      criteria.append(colName).append(" IS NULL");
    }

    return criteria.append(")").toString();
  }

  private String buildBasicTimeRangeCriteria(
      BasicFilterRequest basicFilterRequest, ReportColumn column) {
    List<String> values = basicFilterRequest.values();
    boolean includeNulls = basicFilterRequest.includeNulls();

    if ((values == null || values.size() != 2) && !includeNulls) return "";

    // Standardize the values list to avoid NullPointer exception on stream
    List<String> rawValues = (values == null) ? List.of() : values;

    // Transform raw client values into type-formatted strings
    List<String> formattedValues = fieldFormatter.convertToSQLFromDateRange(rawValues);

    StringBuilder criteria = new StringBuilder("(");

    String colName = "[" + column.columnName() + "]";

    criteria
        .append(colName)
        .append(" BETWEEN ")
        .append(formattedValues.get(0))
        .append(" AND ")
        .append(formattedValues.get(1));

    if (basicFilterRequest.includeNulls()) {
      criteria.insert(0, "(").append(") OR (").append(colName).append(" IS NULL").append(")");
    }

    return criteria.append(")").toString();
  }

  /**
   * Finds the column definition targeted by the filterConfig configuration
   *
   * @param reportConfig holds the list of report columns
   * @param reportColumnUid provides the column UID value
   * @return a definition if found
   */
  private Optional<ReportColumn> findColumn(
      ReportConfiguration reportConfig, Long reportColumnUid) {
    return reportConfig.reportColumns().stream()
        .filter(rc -> Objects.equals(rc.id(), reportColumnUid))
        .findFirst();
  }

  private Optional<BasicFilterConfiguration> findBasicFilterConfiguration(
      ReportConfiguration reportConfig, Long reportFilterUid) {
    return reportConfig.basicFilters().stream()
        .filter(bf -> Objects.equals(bf.reportFilterUid(), reportFilterUid))
        .findFirst();
  }
}
