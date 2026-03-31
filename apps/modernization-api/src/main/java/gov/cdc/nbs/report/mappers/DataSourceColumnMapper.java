package gov.cdc.nbs.report.mappers;

import gov.cdc.nbs.entity.odse.DataSourceColumn;
import org.springframework.stereotype.Component;

@Component
public class DataSourceColumnMapper {
  public static gov.cdc.nbs.report.models.DataSourceColumn fromDb(DataSourceColumn dbColumn) {
    return new gov.cdc.nbs.report.models.DataSourceColumn(
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
