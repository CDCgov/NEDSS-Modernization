package gov.cdc.nbs.report;

import gov.cdc.nbs.report.models.FilterConfiguration;
import gov.cdc.nbs.report.models.FilterDefaultValue;
import gov.cdc.nbs.report.models.ReportColumn;
import gov.cdc.nbs.report.models.ReportConfiguration;
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
   * Constructs WHERE clause based on the basic filters for a report. Each basic filter in the
   * configuration is processed and joined with AND logic.
   *
   * @param reportConfig is the full configuration for the report. Most relevant here are the
   *     columns and active filters.
   * @return A formatted SQL string starting with WHERE. An empty string is returned if required
   *     parts are empty
   */
  public String buildBasicWhereClause(ReportConfiguration reportConfig) {
    if (reportConfig.filters() == null || reportConfig.filters().isEmpty()) return "";

    // StringJoiner provides the "WHERE " prefix and " AND " delimiters between filter statements
    StringJoiner finalWhere = new StringJoiner(" AND ", "WHERE ", "");

    for (FilterConfiguration filter : reportConfig.filters()) {
      findColumn(reportConfig, filter)
          .ifPresent(
              column -> {
                String segment = buildFilterFragment(filter, column);
                if (!segment.isEmpty()) finalWhere.add(segment);
              });
    }

    // Only return the WHERE clause if it contains anything beyond the initial "WHERE " prefix
    return finalWhere.length() > 6 ? finalWhere.toString() : "";
  }

  /**
   * Finds the column definition targeted by the filterConfig configuration
   *
   * @param reportConfig holds the list of report columns
   * @param filterConfig provides the column UID value
   * @return a definition if found
   */
  private Optional<ReportColumn> findColumn(
      ReportConfiguration reportConfig, FilterConfiguration filterConfig) {
    return reportConfig.reportColumns().stream()
        .filter(rc -> Objects.equals(rc.id(), filterConfig.reportColumnUid()))
        .findFirst();
  }

  /**
   * Builds the SQL fragment (e.g., "(COL_NAME IN ('A', 'B') OR COL_NAME IS NULL)")
   *
   * @param filterConfig provides the filter values to the column with and the operators
   * @param column provides the column name and type
   * @return an SQL fragment or empty string
   */
  private String buildFilterFragment(FilterConfiguration filterConfig, ReportColumn column) {
    List<FilterDefaultValue> values = filterConfig.filterDefaultValues();
    if (values == null || values.isEmpty()) return "";

    boolean allowNulls =
        values.stream()
            .anyMatch(
                v -> "none".equalsIgnoreCase(v.valueType()) || "ALLOW_NULLS".equals(v.operator()));

    List<String> formattedValues =
        values.stream()
            .filter(v -> !"none".equalsIgnoreCase(v.valueType()) && v.valueTxt() != null)
            .map(v -> fieldFormatter.formatField(column.columnSourceTypeCode(), v.valueTxt()))
            .toList();

    StringBuilder segment = new StringBuilder("(");
    String colName = "[" + column.columnName() + "]"; // Wrap in brackets for SQL Server safety
    boolean hasValues = !formattedValues.isEmpty();

    if (hasValues) {
      if (isSingleValue(filterConfig)) {
        segment.append(colName).append(" = ").append(formattedValues.getFirst());
      } else {
        segment
            .append(colName)
            .append(" IN (")
            .append(String.join(", ", formattedValues))
            .append(")");
      }
    }

    if (allowNulls) {
      if (hasValues) segment.append(" OR ");
      segment.append(colName).append(" IS NULL");
    }

    // Safety: If no values and no nulls were processed, return empty to avoid "()"
    if (!hasValues && !allowNulls) return "";

    return segment.append(")").toString();
  }

  private boolean isSingleValue(FilterConfiguration filter) {
    return Integer.valueOf(1).equals(filter.maxValueCnt())
        && Integer.valueOf(1).equals(filter.minValueCnt());
  }
}
