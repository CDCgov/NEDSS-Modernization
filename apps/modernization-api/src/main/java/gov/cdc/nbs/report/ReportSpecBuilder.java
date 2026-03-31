package gov.cdc.nbs.report;

import gov.cdc.nbs.report.models.FilterColumn;
import gov.cdc.nbs.report.models.ReportConfiguration;
import gov.cdc.nbs.report.models.ReportExecutionRequest;
import gov.cdc.nbs.report.models.ReportSpec;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;

public class ReportSpecBuilder {
  @Getter private final ReportExecutionRequest reportExecRequest;
  @Getter private final ReportConfiguration reportConfig;

  public ReportSpecBuilder(final ReportExecutionRequest request, ReportConfiguration reportConfig) {
    this.reportExecRequest = request;
    this.reportConfig = reportConfig;
  }

  private String buildSelectClause(List<FilterColumn> columns) {
    if (columns == null) {
      return "SELECT *";
    }

    return "SELECT "
        + columns.stream()
            .map(column -> "[" + column.columnName() + "] AS \"" + column.columnTitle() + "\"")
            .collect(Collectors.joining(", "));
  }

  public List<FilterColumn> fetchColumns() {
    if (reportExecRequest.columnUids() == null || reportExecRequest.columnUids().isEmpty()) {
      return null;
    }

    List<FilterColumn> filterColumns =
        reportExecRequest.columnUids().stream()
            .map(
                columnUid ->
                    reportConfig.filters().stream()
                        .flatMap(f -> Stream.of(f.filterColumn()))
                        .filter(column -> column.id().equals(columnUid))
                        .findFirst()
                        .orElseThrow(
                            () ->
                                new IllegalArgumentException(
                                    "No filter column found for columnUid " + columnUid)))
            .toList();

    if (filterColumns.size() != reportExecRequest.columnUids().size()) {
      throw new IllegalArgumentException("One or more of the columns provided is invalid");
    }

    return filterColumns;
  }

  public ReportSpec build() {
    int version = 1;
    boolean isExport = true;
    boolean isBuiltin = true;
    String reportTitle = "Test Report";
    String libraryName = "nbs_custom";
    String dataSourceName = "nbs_rdb.investigation";
    Map<String, LocalDate> timeRange = null;
    List<FilterColumn> columns = fetchColumns();

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
