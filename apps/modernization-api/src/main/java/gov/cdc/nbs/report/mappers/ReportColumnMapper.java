package gov.cdc.nbs.report.mappers;

import gov.cdc.nbs.entity.odse.DataSourceColumn;
import gov.cdc.nbs.report.models.ReportColumn;

public class ReportColumnMapper {
  private ReportColumnMapper() {}

  public static ReportColumn fromDataSourceColumn(DataSourceColumn dataSourceColumn) {

    return new ReportColumn(
        dataSourceColumn.getId(),
        dataSourceColumn.getColumnMaxLength(),
        dataSourceColumn.getColumnName(),
        dataSourceColumn.getColumnTitle(),
        dataSourceColumn.getColumnSourceTypeCode(),
        dataSourceColumn.getDescTxt(),
        dataSourceColumn.getDisplayable(),
        dataSourceColumn.getFilterable(),
        dataSourceColumn.getStatusCd(),
        dataSourceColumn.getStatusTime());
  }
}
