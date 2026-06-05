package gov.cdc.nbs.report;

import gov.cdc.nbs.datasource.utils.DataSourceNameUtils;
import gov.cdc.nbs.report.models.*;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

/**
 * The ReportSpecBuilder is responsible for constructing a ReportSpec object based on the provided
 * ReportExecutionRequest and ReportConfiguration parameters, to then be supplied to the Report
 * Execution service for subsequent report generation. It assembles report metadata, includes
 * togglable report parameters and (arguably most importantly) compiles a SQL statement to be
 * invoked during report execution, accounting for all supplied parameters as well as relevant
 * permissions for the individual invoking said report.
 */
public class ReportSpecBuilder {
  @Getter private final ReportExecutionRequest reportExecRequest;
  @Getter private final ReportConfiguration reportConfig;
  private final DataSourceNameUtils dataSourceNameUtils;
  private final WhereClauseService whereClauseService;

  public ReportSpecBuilder(
      final ReportExecutionRequest request,
      ReportConfiguration reportConfig,
      DataSourceNameUtils dataSourceNameUtils,
      WhereClauseService whereClauseService) {
    this.reportExecRequest = request;
    this.reportConfig = reportConfig;
    this.dataSourceNameUtils = dataSourceNameUtils;
    this.whereClauseService = whereClauseService;
  }

  private String buildSelectClause(List<ReportColumn> columns) {
    if (columns == null) {
      return "SELECT *";
    }

    return "SELECT "
        + columns.stream()
            .map(column -> "[" + column.name() + "] AS [" + column.title() + "]")
            .collect(Collectors.joining(", "));
  }

  private ReportColumn findMatchingColumn(Long columnUid) {
    return reportConfig.columns().stream()
        .filter(column -> column.id().equals(columnUid))
        .findFirst()
        .orElseThrow(
            () ->
                new IllegalArgumentException("No report column found for columnUid " + columnUid));
  }

  @SuppressWarnings("java:S1168")
  public List<ReportColumn> fetchColumns() {
    if (reportExecRequest.columnUids() == null || reportExecRequest.columnUids().isEmpty()) {
      return null;
    }

    List<ReportColumn> reportColumns =
        reportExecRequest.columnUids().stream().map(this::findMatchingColumn).toList();

    if (reportColumns.size() != reportExecRequest.columnUids().size()) {
      throw new IllegalArgumentException("One or more of the reportColumns provided is invalid");
    }

    return reportColumns;
  }

  public ReportSpec build() {
    Library reportLibrary = reportConfig.library();

    boolean isExport = reportExecRequest.isExport();
    boolean isBuiltin = reportLibrary.isBuiltin();
    String reportTitle = reportConfig.title();
    String libraryName = reportConfig.library().name();
    String dataSourceName =
        dataSourceNameUtils.buildDataSourceName(reportConfig.dataSource().name());
    List<ReportColumn> columns = fetchColumns();
    List<List<String>> columnMap = null;
    if (columns != null) {
      columnMap = columns.stream().map(c -> List.of(c.name(), c.title())).toList();
    }

    String selectClause = buildSelectClause(columns);
    String fromClause = String.format("FROM %s", dataSourceName);
    String whereClause =
        whereClauseService.buildWhereClause(reportConfig, reportExecRequest, dataSourceNameUtils);
    String orderByClause = "";

    Integer daysValue = extractDaysValue();

    String subsetQuery =
        String.join(" ", selectClause, fromClause, whereClause, orderByClause).trim();

    return new ReportSpec(
        isExport,
        isBuiltin,
        reportTitle,
        libraryName,
        dataSourceName,
        subsetQuery,
        columnMap,
        daysValue);
  }

  private Integer extractDaysValue() {
    // Guard clause: Return early if either the execution request or the report configuration
    // contain no basic filters.
    if (reportExecRequest.basicFilters() == null || reportConfig.basicFilters() == null) {
      return null;
    }

    // Find the basic filter configuration for "BAS_DAYS" if it exists.
    BasicFilterConfiguration basDaysConfig =
        reportConfig.basicFilters().stream()
            .filter(
                filterConfig -> ReportConstants.BAS_DAYS.equals(filterConfig.filterType().type()))
            .findFirst()
            .orElse(null);

    // If no "BAS_DAYS" filter is configured for this report, there's nothing to extract.
    if (basDaysConfig == null) {
      return null;
    }

    // Inspect the runtime execution request to find the user-submitted filter matching
    // the targeted "BAS_DAYS" UIDs
    String rawDaysValue =
        reportExecRequest.basicFilters().stream()
            .filter(request -> basDaysConfig.reportFilterUid().equals(request.reportFilterUid()))
            .flatMap(request -> request.values().stream())
            .findFirst()
            .orElse(null);

    if (rawDaysValue == null || rawDaysValue.isBlank()) {
      return null;
    }

    // Parse the text value into an Int. Malformed entries will bubble up as an argument validation
    // failure.
    try {
      return Integer.valueOf(rawDaysValue);
    } catch (NumberFormatException e) {
      // Safely extract the description, fallback to a default if it happens to be missing/null
      String description =
          (basDaysConfig.filterType().descTxt() != null)
              ? basDaysConfig.filterType().descTxt()
              : "Days Filter";

      throw new IllegalArgumentException(
          String.format(
              "The '%s' filter value must be a valid integer: %s", description, rawDaysValue));
    }
  }
}
