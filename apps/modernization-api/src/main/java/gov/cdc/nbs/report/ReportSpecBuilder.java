package gov.cdc.nbs.report;

import gov.cdc.nbs.report.models.ReportColumn;
import gov.cdc.nbs.report.models.ReportConfiguration;
import gov.cdc.nbs.report.models.ReportExecutionRequest;
import gov.cdc.nbs.report.models.ReportSpec;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
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

  public ReportSpecBuilder(final ReportExecutionRequest request, ReportConfiguration reportConfig) {
    this.reportExecRequest = request;
    this.reportConfig = reportConfig;
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
    int version = 1;
    boolean isExport = true;
    boolean isBuiltin = true;
    String reportTitle = "Test Report";
    String libraryName = "nbs_custom";
    String dataSourceName = "nbs_rdb.investigation";
    Map<String, LocalDate> timeRange = null;
    List<ReportColumn> columns = fetchColumns();

    String selectClause = buildSelectClause(columns);
    String fromClause = "FROM [NBS_ODSE].[dbo].[NBS_configuration]";
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
