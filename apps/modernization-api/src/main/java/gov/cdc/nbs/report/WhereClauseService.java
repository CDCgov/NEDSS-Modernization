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

    StringJoiner valueJoiner = new StringJoiner(", ");
    boolean allowNulls = false;

    for (FilterDefaultValue fdv : values) {
      // "none" type values indicate that NULL records should be included
      boolean isNoneType = "none".equalsIgnoreCase(fdv.valueType());

      // Check for explicit ALLOW_NULLS operator
      if ("ALLOW_NULLS".equals(fdv.operator()) || isNoneType) {
        allowNulls = true;
      }
      // Format and add valid values to the set (skipping "none" which is handled by IS NULL logic)
      if (!isNoneType && fdv.valueTxt() != null) {
        valueJoiner.add(fieldFormatter.formatField(column.columnSourceTypeCode(), fdv.valueTxt()));
      }
    }

    StringBuilder segment = new StringBuilder("(");
    boolean hasValues = valueJoiner.length() > 0;

    if (hasValues) {
      // determine if there is only one value or a list of values
      if (isSingleValue(filterConfig)) {
        segment.append(column.columnName()).append(" = ").append(valueJoiner);
      } else {
        segment.append(column.columnName()).append(" IN (").append(valueJoiner).append(")");
      }
    }

    if (allowNulls) {
      // If we have values and nulls, we need the "OR"
      if (hasValues) {
        segment.append(" OR ");
      }
      segment.append(column.columnName()).append(" IS NULL");
    }
    return segment.append(")").toString();
  }

  private boolean isSingleValue(FilterConfiguration filter) {
    return Integer.valueOf(1).equals(filter.maxValueCnt())
        && Integer.valueOf(1).equals(filter.minValueCnt());
  }
}
