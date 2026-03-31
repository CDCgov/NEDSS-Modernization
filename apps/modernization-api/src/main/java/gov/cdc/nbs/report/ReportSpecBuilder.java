package gov.cdc.nbs.report;

import gov.cdc.nbs.entity.odse.DataSourceColumn;
import gov.cdc.nbs.report.models.ReportSpec;
import gov.cdc.nbs.repository.DataSourceColumnRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Getter;

public class ReportSpecBuilder {

  private final DataSourceColumnRepository dataSourceColumnRepository;

  //  TODO: Remove defaults once support has been established for these fields
  @Getter private int version = 1;
  @Getter private Boolean isExport = true;
  @Getter private Boolean isBuiltin = true;
  @Getter private String reportTitle = "Test Report";
  @Getter private String libraryName = "nbs_custom";
  @Getter private String dataSourceName = "nbs_rdb.investigation";
  @Getter private Map<String, LocalDate> timeRange;
  @Getter private List<DataSourceColumn> columns = null;

  @SuppressWarnings("FieldCanBeLocal")
  private String selectClause;

  @SuppressWarnings("FieldCanBeLocal")
  private String fromClause;

  @SuppressWarnings("FieldCanBeLocal")
  private String whereClause;

  @SuppressWarnings("FieldCanBeLocal")
  private String orderByClause;

  private String buildSelectClause() {
    if (columns == null || columns.isEmpty()) {
      return "SELECT *";
    }

    return "SELECT "
        + columns.stream()
            .map(
                column -> "[" + column.getColumnName() + "] AS \"" + column.getColumnTitle() + "\"")
            .collect(Collectors.joining(", "));
  }

  public ReportSpecBuilder(final DataSourceColumnRepository dataSourceColumnRepository) {
    this.dataSourceColumnRepository = dataSourceColumnRepository;
  }

  public ReportSpecBuilder setVersion(int version) {
    this.version = version;
    return this;
  }

  public ReportSpecBuilder setIsExport(Boolean isExport) {
    this.isExport = isExport;
    return this;
  }

  public ReportSpecBuilder setIsBuiltin(Boolean isBuiltin) {
    this.isBuiltin = isBuiltin;
    return this;
  }

  public ReportSpecBuilder setReportTitle(String reportTitle) {
    this.reportTitle = reportTitle;
    return this;
  }

  public ReportSpecBuilder setLibraryName(String libraryName) {
    this.libraryName = libraryName;
    return this;
  }

  public ReportSpecBuilder setDataSourceName(String dataSourceName) {
    this.dataSourceName = dataSourceName;
    return this;
  }

  public ReportSpecBuilder setTimeRange(Map<String, LocalDate> timeRange) {
    this.timeRange = timeRange;
    return this;
  }

  public ReportSpecBuilder setColumns(List<Long> columnUids) {
    List<DataSourceColumn> dataSourceColumns = dataSourceColumnRepository.findAllById(columnUids);
    if (dataSourceColumns.size() != columnUids.size()) {
      throw new IllegalArgumentException("One or more of the columns provided is invalid");
    }

    columns = dataSourceColumns;
    return this;
  }

  public ReportSpec build() {
    selectClause = buildSelectClause();
    fromClause = "FROM [NBS_ODSE].[dbo].[NBS_configuration]";
    whereClause = "";
    orderByClause = "";

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
