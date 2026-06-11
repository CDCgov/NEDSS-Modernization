package gov.cdc.nbs.report;

import gov.cdc.nbs.entity.odse.FilterValue;
import java.util.Comparator;
import java.util.List;
import lombok.Getter;

public class AdvancedQueryException extends Exception {
  @Getter private List<FilterValue> filterValues;

  public AdvancedQueryException(String message) {
    super(message);
  }

  public AdvancedQueryException(String message, List<FilterValue> filterValues) {
    super(message);
    this.filterValues = filterValues;
  }

  @Override
  public String getMessage() {
    // Appends custom record information to the standard message
    return super.getMessage() + " (Query String: '" + generateQueryString() + "')";
  }

  public String generateQueryString() {
    return "WHERE "
        + String.join(
            " ",
            filterValues.stream()
                .sorted(Comparator.comparing(FilterValue::getSequenceNumber))
                .map(f -> (f.getOperator() + " " + f.getValueTxt()).strip())
                .toList());
  }
}
