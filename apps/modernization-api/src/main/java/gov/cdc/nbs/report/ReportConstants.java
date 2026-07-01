package gov.cdc.nbs.report;

import java.util.Map;
import java.util.Set;

public final class ReportConstants {
  public enum QueryCombinators {
    OR,
    AND
  }

  public enum AdvancedFilterValueType {
    OPERATOR,
    CLAUSE
  }

  public static final String BASIC_FILTER_VALUE_TYPE = "code";

  public static final String BASIC_FILTER_ALLOW_NULLS_OP = "ALLOW_NULLS";

  public static final String BAS_DAYS = "BAS_DAYS";

  public static final Set<String> BAS_TIME_RANGE_TYPES =
      Set.of("BAS_TIM_RANGE", "BAS_TIM_RANGE_CUSTOM", "BAS_TIM_RANGE_LIST", "BAS_MM_YYYY_RANGE");

  public static final Set<String> BAS_TYPES =
      Set.of("BAS_CON_LIST", "BAS_JUR_LIST", "BAS_CVG_LIST", "BAS_TXT", "BAS_STD_HIV_WRKR");

  public static final Set<String> BAS_CODES_NO_COLUMN = Set.of("D_01", "J_R01", "J_R01_N");

  public static final String SQL_AND = " AND ";
  public static final String SQL_WHERE = "WHERE ";

  public static final class Permissions {
    private Permissions() {}

    public static final String REPORTINGOBJECT = "REPORTING";

    public static final String VIEWREPORTTEMPLATE = "VIEWREPORTTEMPLATE";
    public static final String VIEWREPORTPRIVATE = "VIEWREPORTPRIVATE";
    public static final String VIEWREPORTPUBLIC = "VIEWREPORTPUBLIC";
    public static final String VIEWREPORTREPORTINGFACILITY = "VIEWREPORTREPORTINGFACILITY";

    public static final String CREATEREPORTPUBLIC = "CREATEREPORTPUBLIC";
    public static final String CREATEREPORTPRIVATE = "CREATEREPORTPRIVATE";
    public static final String CREATEREPORTREPORTINGFACILITY = "CREATEREPORTREPORTINGFACILITY";
    
    public static final String EDITREPORTPUBLIC = "EDITREPORTPUBLIC";
    public static final String EDITREPORTPRIVATE = "EDITREPORTPRIVATE";
    public static final String EDITREPORTREPORTINGFACILITY = "EDITREPORTREPORTINGFACILITY";
  }

  public enum SortDirection {
    ASC,
    DESC
  }

  public enum SelectType {
    SINGLE,
    MULTI
  }

  public enum ReportGroup {
    PRIVATE,
    REPORTING_FACILITY,
    PUBLIC,
    TEMPLATE
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
