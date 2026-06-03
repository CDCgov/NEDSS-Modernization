package gov.cdc.nbs.report;

import gov.cdc.nbs.entity.odse.FilterValue;
import lombok.Getter;

class AdvancedQueryException extends Exception {
  @Getter private FilterValue filterValue;

  public AdvancedQueryException(String message) {
    super(message);
  }

  public AdvancedQueryException(String message, FilterValue filterValue) {
    super(message);
    this.filterValue = filterValue;
  }
}
