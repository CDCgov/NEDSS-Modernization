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

  private ReportConstants() {}
}
