package gov.cdc.nbs.report;

public final class ReportConstants {
  public static final String ADV_FILTER_TYPE = "ADV_WCB";

  /** Indicates the type of filtering used for the report. */
  public enum FilterMode {
    ADVANCED('A'),
    BASIC('B');

    private final Character text;

    FilterMode(final Character text) {
      this.text = text;
    }

    @Override
    public String toString() {
      return text.toString();
    }
  }

  public enum ReportGroup {
    PRIVATE("Private"),
    REPORTING_FACILITY("Reporting Facility"), // with a space
    PUBLIC("Public"),
    TEMPLATE("Template");

    private final String text;

    ReportGroup(final String text) {
      this.text = text;
    }

    @Override
    public String toString() {
      return text;
    }
  }

  private ReportConstants() {}
}
