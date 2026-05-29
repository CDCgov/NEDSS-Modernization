package gov.cdc.nbs.report;

import java.util.Map;
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

  // operator options for the advanced filter
  public static final String EQ = "EQ"; // equals
  public static final String NE = "NE"; // not equals
  public static final String IN = "IN"; // is null
  public static final String NN = "NN"; // not null
  public static final String SW = "SW"; // starts with
  public static final String CO = "CO"; // contains
  public static final String BW = "BW"; // between
  public static final String LT = "LT"; // less than
  public static final String GT = "GT"; // greater than
  public static final String LE = "LE"; // less than or equal to
  public static final String GE = "GE"; // greater than or equal to

  public static final Map<String, String> COMPARISON_OPERATORS =
      Map.ofEntries(
          Map.entry(LT, "<"),
          Map.entry(GT, ">"),
          Map.entry(LE, "<="),
          Map.entry(GE, ">="),
          Map.entry(NE, "<>"),
          Map.entry(EQ, "="));

  public static final Set<String> RDB_LAB_RESULT_VAL_COLS =
      Set.of(
          "numeric_result_val",
          "ref_range_frm",
          "ref_range_to",
          "coded_result_val",
          "coded_result_val_desc",
          "text_result_val",
          "RESULT_UNITS");

  private ReportConstants() {}
}
