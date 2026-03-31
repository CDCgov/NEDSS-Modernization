package gov.cdc.nbs.report.mappers;

import gov.cdc.nbs.entity.odse.FilterValue;
import gov.cdc.nbs.report.models.FilterValueOption;

public class FilterValueOptionMapper {
  private FilterValueOptionMapper() {}

  public static FilterValueOption fromFilterValue(FilterValue filterValue) {
    return new FilterValueOption(
        filterValue.getId(),
        filterValue.getSequenceNumber(),
        filterValue.getValueType(),
        filterValue.getColumnUid(),
        filterValue.getOperator(),
        filterValue.getValueTxt());
  }
}
