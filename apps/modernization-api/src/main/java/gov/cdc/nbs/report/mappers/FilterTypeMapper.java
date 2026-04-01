package gov.cdc.nbs.report.mappers;

import gov.cdc.nbs.entity.odse.FilterCode;
import gov.cdc.nbs.report.models.FilterType;

public class FilterTypeMapper {
  private FilterTypeMapper() {}

  public static FilterType fromFilterCode(FilterCode filterCode) {
    return new FilterType(
        filterCode.getId(),
        filterCode.getCodeTable(),
        filterCode.getDescTxt(),
        filterCode.getCode(),
        filterCode.getFilterCodeSetName(),
        filterCode.getFilterType(),
        filterCode.getFilterName());
  }
}
