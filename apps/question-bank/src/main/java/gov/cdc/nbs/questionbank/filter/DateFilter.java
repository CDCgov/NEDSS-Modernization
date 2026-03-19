package gov.cdc.nbs.questionbank.filter;

public record DateFilter(String property, Operator operator) implements Filter {

  public enum Operator {
    TODAY("today"),
    LAST_7_DAYS("last 7 days"),
    LAST_14_DAYS("last 14 days"),
    LAST_30_DAYS("last 30 days"),
    MORE_THAN_30_DAYS("more than 30 days");
    private final String display;

    Operator(final String display) {
      this.display = display;
    }

    public String display() {
      return display;
    }
  }
}
