package gov.cdc.nbs.report;

public final class ReportConstants {
  public static final String ADV_FILTER_TYPE = "ADV_WCB";

  public enum SelectType {
    SINGLE,
    MULTI
  }

  public enum ReportGroup {
    PRIVATE("Private"),
    REPORTING_FACILITY("Reporting Facility"),
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
