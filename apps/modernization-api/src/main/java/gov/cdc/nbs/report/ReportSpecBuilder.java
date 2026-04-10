package gov.cdc.nbs.report;

import gov.cdc.nbs.report.models.*;
import gov.cdc.nbs.report.utils.DataSourceNameUtils;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

  public ReportSpecBuilder(
      final ReportExecutionRequest request,
      ReportConfiguration reportConfig,
      DataSourceNameUtils dataSourceNameUtils) {
    this.reportExecRequest = request;
    this.reportConfig = reportConfig;
    this.dataSourceNameUtils = dataSourceNameUtils;
  }

  private String buildSelectClause(List<ReportColumn> columns) {
    if (columns == null) {
      return "SELECT *";
    }

    return "SELECT "
        + columns.stream()
            .map(column -> "[" + column.columnName() + "] AS [" + column.columnTitle() + "]")
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

    int version = reportLibrary.version();
    boolean isBuiltin = reportLibrary.isBuiltin();
    String libraryName = reportLibrary.libraryName();

    boolean isExport = reportExecRequest.isExport();

    String reportTitle =
        reportExecRequest.reportTitle().isEmpty() ? libraryName : reportExecRequest.reportTitle();
    String dataSourceName =
        dataSourceNameUtils.buildDataSourceName(reportConfig.dataSource().name());

    ReportSpec.TimeRange timeRange = null;

    if (reportExecRequest.timeRange() != null) {
      LocalDate start =
          LocalDate.parse(reportExecRequest.timeRange().start(), DateTimeFormatter.ISO_LOCAL_DATE);
      LocalDate end =
          LocalDate.parse(reportExecRequest.timeRange().end(), DateTimeFormatter.ISO_LOCAL_DATE);

      timeRange = new ReportSpec.TimeRange(start, end);
    }

    List<ReportColumn> columns = fetchColumns();

    String selectClause = buildSelectClause(columns);
    String fromClause = String.format("FROM %s", dataSourceName);
    String whereClause = "";
    String orderByClause = "";

    String subsetQuery =
        String.join(" ", selectClause, fromClause, whereClause, orderByClause).trim();

    return new ReportSpec(
        version,
        isExport,
        isBuiltin,
        reportTitle,
        libraryName,
        dataSourceName,
        subsetQuery,
        timeRange);
  }
}
