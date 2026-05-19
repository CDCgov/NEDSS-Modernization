package gov.cdc.nbs.report.mappers;

import gov.cdc.nbs.entity.odse.DataSourceCodeset;
import gov.cdc.nbs.entity.odse.DataSourceColumn;
import gov.cdc.nbs.report.models.ReportColumn;

public class ReportColumnMapper {
  private ReportColumnMapper() {}

  public static ReportColumn fromDataSourceColumn(DataSourceColumn dataSourceColumn) {
    String codeDescCd = null;
    String codesetNm = null;
    DataSourceCodeset codeset = dataSourceColumn.getCodeset();
    if (codeset != null) {
      codeDescCd = codeset.getCodeDescCd();
      codesetNm = codeset.getCodesetNm();
    }

    Boolean isDisplayable = Character.valueOf('Y').equals(dataSourceColumn.getDisplayable());
    Boolean isFilterable = Character.valueOf('Y').equals(dataSourceColumn.getFilterable());

    return new ReportColumn(
        dataSourceColumn.getId(),
        dataSourceColumn.getColumnMaxLength(),
        dataSourceColumn.getColumnName(),
        dataSourceColumn.getColumnTitle(),
        dataSourceColumn.getColumnSourceTypeCode(),
        dataSourceColumn.getDescTxt(),
        isDisplayable,
        isFilterable,
        codeDescCd,
        codesetNm,
        dataSourceColumn.getStatusCd(),
        dataSourceColumn.getStatusTime());
  }
}
