package gov.cdc.nbs.report.mappers;

import gov.cdc.nbs.entity.odse.FilterCode;
import gov.cdc.nbs.report.models.FilterOption;

public class FilterOptionMapper {
  private FilterOptionMapper() {}

  public static FilterOption fromFilterCode(FilterCode filterCode) {
    return new FilterOption(
        filterCode.getId(),
        filterCode.getCodeTable(),
        filterCode.getDescTxt(),
        filterCode.getCode(),
        filterCode.getFilterCodeSetName(),
        filterCode.getFilterType(),
        filterCode.getFilterName());
  }
}
