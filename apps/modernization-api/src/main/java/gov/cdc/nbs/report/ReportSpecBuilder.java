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
    return reportConfig.reportColumns().stream()
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
    Library reportLibrary = reportConfig.reportLibrary();

    boolean isExport = reportExecRequest.isExport();
    boolean isBuiltin = reportLibrary.isBuiltin();
    String reportTitle = reportConfig.reportTitle();
    String libraryName = reportConfig.reportLibrary().libraryName();
    String dataSourceName =
        dataSourceNameUtils.buildDataSourceName(reportConfig.dataSource().name());
    List<ReportColumn> columns = fetchColumns();

    String selectClause = buildSelectClause(columns);
    String fromClause = String.format("FROM %s", dataSourceName);
    String whereClause = whereClauseService.buildWhereClause(reportConfig, reportExecRequest);
    String orderByClause = "";

    Integer daysValue = extractDaysValue();

    String subsetQuery =
        String.join(" ", selectClause, fromClause, whereClause, orderByClause).trim();

    return new ReportSpec(
        isExport, isBuiltin, reportTitle, libraryName, dataSourceName, subsetQuery, daysValue);
  }

  private Integer extractDaysValue() {
    if (reportExecRequest.basicFilters() == null || reportConfig.basicFilters() == null) {
      return null;
    }

    // Find the UIDs of any filters with type = "BAS_DAYS"
    List<Long> basDaysFilterUids =
        reportConfig.basicFilters().stream()
            .filter(
                filterConfig ->
                    filterConfig.filterType() != null
                        && ReportConstants.BAS_DAYS.equals(filterConfig.filterType().type()))
            .map(BasicFilterConfiguration::reportFilterUid)
            .toList();

    // Find the matching request filter and value
    String rawDaysValue =
        reportExecRequest.basicFilters().stream()
            .filter(request -> basDaysFilterUids.contains(request.reportFilterUid()))
            .flatMap(request -> request.values().stream())
            .findFirst()
            .orElse(null);

    if (rawDaysValue == null || rawDaysValue.isBlank()) {
      return null;
    }

    try {
      return Integer.valueOf(rawDaysValue);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException(
          "The requested filter value must be a valid integer: " + rawDaysValue);
    }
  }
}
