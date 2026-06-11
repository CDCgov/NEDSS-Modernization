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
    return super.getMessage() + " [Query String: '" + generateQueryString() + "']";
  }

  public String generateQueryString() {
    return "WHERE "
        + String.join(
            " ",
            filterValues.stream()
                .sorted(Comparator.comparing(FilterValue::getSequenceNumber))
                .map(
                    f -> {
                      String part;

                      if (List.of("(", ")", "and", "or").contains(f.getOperator())) {
                        part = f.getOperator();
                      } else {
                        part = "COL " + f.getOperator();
                      }

                      if (f.getValueTxt() != null) {
                        part += " " + f.getValueTxt();
                      }

                      return part;
                    })
                .toList());
  }
}
