package gov.cdc.nbs.report;

import java.util.Set;

public final class ReportConstants {
  public static final String ADV_FILTER_TYPE = "ADV_WCB";
  public static final String BAS_DAYS = "BAS_DAYS";

  public static final Set<String> BAS_TIME_RANGE_TYPES =
      Set.of("BAS_TIM_RANGE", "BAS_TIM_RANGE_CUSTOM", "BAS_TIM_RANGE_LIST", "BAS_MM_YYYY_RANGE");

  public static final Set<String> BAS_TYPES =
      Set.of("BAS_CON_LIST", "BAS_JUR_LIST", "BAS_CVG_LIST", "BAS_TXT", "BAS_STD_HIV_WRKR");

  public static final String SQL_AND = " AND ";
  public static final String SQL_WHERE = "WHERE ";

  public enum SortDirection {
    ASC,
    DESC;
  }

  public enum SelectType {
    SINGLE,
    MULTI
  }

  public enum ReportGroup {
    PRIVATE,
    REPORTING_FACILITY,
    PUBLIC,
    TEMPLATE;
  }

  public static Character reportGroupToDbChar(ReportConstants.ReportGroup group) {
    return switch (group) {
      case PRIVATE -> 'P';
      case REPORTING_FACILITY -> 'R';
      case PUBLIC -> 'S';
      case TEMPLATE -> 'T';
    };
  }

  public static ReportConstants.ReportGroup dbCharToReportGroup(Character shared) {
    return switch (shared) {
      case 'P' -> ReportGroup.PRIVATE;
      case 'R' -> ReportGroup.REPORTING_FACILITY;
      case 'S' -> ReportGroup.PUBLIC;
      case 'T' -> ReportGroup.TEMPLATE;
      default ->
          throw new IllegalArgumentException(
              "Invalid `Report.shared` group value: %s".formatted(shared));
    };
  }

  private ReportConstants() {}
}
