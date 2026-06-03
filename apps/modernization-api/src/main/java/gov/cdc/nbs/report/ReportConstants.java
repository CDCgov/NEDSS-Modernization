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

  // operator options for the advanced filter
  public enum Operator {
    EQ, // equals
    NE, // not equals
    IN, // is null
    NN, // not null
    SW, // starts with
    CO, // contains
    BW, // between
    LT, // less than
    GT, // greater than
    LE, // less than or equal to
    GE // greater than or equal to
  }

  public static final Map<Operator, String> COMPARISON_OPERATORS =
      Map.ofEntries(
          Map.entry(Operator.LT, "<"),
          Map.entry(Operator.GT, ">"),
          Map.entry(Operator.LE, "<="),
          Map.entry(Operator.GE, ">="),
          Map.entry(Operator.NE, "<>"),
          Map.entry(Operator.EQ, "="));

  public static final Set<String> RDB_LAB_RESULT_VAL_COLS =
      Set.of(
          "NUMERIC_RESULT_VAL",
          "REF_RANGE_FRM",
          "REF_RANGE_TO",
          "CODED_RESULT_VAL",
          "CODED_RESULT_VAL_DESC",
          "TEXT_RESULT_VAL",
          "RESULT_UNITS");

  private ReportConstants() {}
}
