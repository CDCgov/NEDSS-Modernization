package gov.cdc.nbs.report.mappers;

import gov.cdc.nbs.audit.Status;
import gov.cdc.nbs.entity.odse.FilterCode;
import gov.cdc.nbs.report.models.FilterType;
import java.time.LocalDateTime;

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

  public static FilterCode toFilterCode(FilterType filterType) {
    LocalDateTime now = LocalDateTime.now();

    return FilterCode.builder()
        .id(filterType.id())
        .codeTable(filterType.codeTable())
        .descTxt(filterType.descTxt())
        .code(filterType.code())
        .filterCodeSetName(filterType.codeSetName())
        .filterType(filterType.type())
        .filterName(filterType.name())
        .status(new Status('A', now))
        .build();
  }
}
