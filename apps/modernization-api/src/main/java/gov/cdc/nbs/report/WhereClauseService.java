package gov.cdc.nbs.report;

import gov.cdc.nbs.report.models.BasicFilterConfiguration;
import gov.cdc.nbs.report.models.BasicFilterRequest;
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

  public String buildWhereClause(
      ReportConfiguration reportConfig, ReportExecutionRequest executionRequest) {
    return buildBasicWhereClause(reportConfig, executionRequest.basicFilters());
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
  public String buildBasicWhereClause(
      ReportConfiguration reportConfig, List<BasicFilterRequest> basicFilterRequests) {
    if (basicFilterRequests == null || basicFilterRequests.isEmpty()) return "";

    // StringJoiner provides the "WHERE " prefix and " AND " delimiters between filter statements
    StringJoiner finalWhere = new StringJoiner(" AND ", "WHERE ", "");

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

      // Build the SQL segment
      String segment = buildBasicFilterFragment(filterRequest, column);
      if (!segment.isEmpty()) {
        finalWhere.add(segment);
      }
    }

    // Only return the WHERE clause if it contains anything beyond the initial "WHERE " prefix
    return finalWhere.length() > 6 ? finalWhere.toString() : "";
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

  /**
   * Builds the SQL fragment (e.g., "(COL_NAME IN ('A', 'B') OR COL_NAME IS NULL)")
   *
   * @param basicFilterRequest provides the filter values to the column with and the operators
   * @param column provides the column name and type
   * @return an SQL fragment or empty string
   */
  private String buildBasicFilterFragment(
      BasicFilterRequest basicFilterRequest, ReportColumn column) {

    List<String> values = basicFilterRequest.values();
    boolean includeNulls = basicFilterRequest.includeNulls();

    // Guard clause: if there are no values, and we aren't including nulls, there is no filter to
    // apply
    if ((values == null || values.isEmpty()) && !includeNulls) return "";

    // Standardize the values list to avoid NullPointer exception on stream
    List<String> rawValues = (values == null) ? List.of() : values;

    // Transform raw client values into type-formatted strings
    List<String> formattedValues =
        rawValues.stream()
            .map(v -> fieldFormatter.formatField(column.columnSourceTypeCode(), v))
            .toList();

    StringBuilder segment = new StringBuilder("(");
    String colName = "[" + column.columnName() + "]"; // Brackets protect against SQL reserved words
    boolean hasValues = !formattedValues.isEmpty();

    // Append the IN clause if actual data values were provided
    if (hasValues) {
      segment
          .append(colName)
          .append(" IN (")
          .append(String.join(", ", formattedValues))
          .append(")");
    }

    if (includeNulls) {
      // If we already have values, use OR to join them with the NULL check
      if (hasValues) {
        segment.append(" OR ");
      }
      segment.append(colName).append(" IS NULL");
    }

    return segment.append(")").toString();
  }
}
