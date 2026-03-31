package gov.cdc.nbs.report.mappers;

import gov.cdc.nbs.entity.odse.DataSourceColumn;
import gov.cdc.nbs.report.models.FilterColumn;

public class FilterColumnMapper {
  private FilterColumnMapper() {}

  public static FilterColumn fromDataSourceColumn(DataSourceColumn dataSourceColumn) {

    return new FilterColumn(
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
