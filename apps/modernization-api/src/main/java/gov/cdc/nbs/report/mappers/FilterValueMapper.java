package gov.cdc.nbs.report.mappers;

import gov.cdc.nbs.entity.odse.FilterValue;
import org.springframework.stereotype.Component;

@Component
public class FilterValueMapper {
  public static gov.cdc.nbs.report.models.FilterValue fromDb(FilterValue dbFilterValue) {
    return new gov.cdc.nbs.report.models.FilterValue(
        dbFilterValue.getId(),
        dbFilterValue.getSequenceNumber(),
        dbFilterValue.getValueType(),
        dbFilterValue.getColumnUid(),
        dbFilterValue.getOperator(),
        dbFilterValue.getValueTxt());
  }
}
