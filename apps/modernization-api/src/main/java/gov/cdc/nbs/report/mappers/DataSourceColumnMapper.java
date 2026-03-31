package gov.cdc.nbs.report.mappers;

import gov.cdc.nbs.entity.odse.DataSourceColumn;
import gov.cdc.nbs.report.models.FilterColumn;

public class DataSourceColumnMapper {
  private DataSourceColumnMapper() {}

  public static FilterColumn fromDb(DataSourceColumn dbColumn) {

    return new FilterColumn(
        dbColumn.getId(),
        dbColumn.getColumnMaxLength(),
        dbColumn.getColumnName(),
        dbColumn.getColumnTitle(),
        dbColumn.getColumnSourceTypeCode(),
        dbColumn.getDescTxt(),
        dbColumn.getDisplayable(),
        dbColumn.getFilterable(),
        dbColumn.getStatusCd(),
        dbColumn.getStatusTime());
  }
}
