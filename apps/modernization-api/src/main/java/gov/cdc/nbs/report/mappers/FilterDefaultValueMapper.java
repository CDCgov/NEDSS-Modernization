package gov.cdc.nbs.report.mappers;

import gov.cdc.nbs.entity.odse.FilterValue;
import gov.cdc.nbs.report.models.FilterDefaultValue;

public class FilterDefaultValueMapper {
  private FilterDefaultValueMapper() {}

  public static FilterDefaultValue fromFilterValue(FilterValue filterValue) {
    return new FilterDefaultValue(
        filterValue.getId(),
        filterValue.getSequenceNumber(),
        filterValue.getValueType(),
        filterValue.getColumnUid(),
        filterValue.getOperator(),
        filterValue.getValueTxt());
  }
}
