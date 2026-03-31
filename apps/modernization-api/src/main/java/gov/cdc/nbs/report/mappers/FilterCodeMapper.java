package gov.cdc.nbs.report.mappers;

import gov.cdc.nbs.entity.odse.FilterCode;
import org.springframework.stereotype.Component;

@Component
public class FilterCodeMapper {
  public static gov.cdc.nbs.report.models.FilterCode fromDb(FilterCode filterCode) {
    return new gov.cdc.nbs.report.models.FilterCode(
        filterCode.getId(),
        filterCode.getCodeTable(),
        filterCode.getDescTxt(),
        filterCode.getCode(),
        filterCode.getFilterCodeSetName(),
        filterCode.getFilterType(),
        filterCode.getFilterName());
  }
}
