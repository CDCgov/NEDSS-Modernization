package gov.cdc.nbs.report;

public final class ReportConstants {
  public static final String ADV_FILTER_TYPE = "ADV_WCB";

  /**
   * Represents the types of filters available for reports.
   *
   * <ul>
   *   <li>SS: Single Select
   *   <li>MS: Multi Select
   * </ul>
   */
  public enum FilterType {
    SS,
    MS
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
