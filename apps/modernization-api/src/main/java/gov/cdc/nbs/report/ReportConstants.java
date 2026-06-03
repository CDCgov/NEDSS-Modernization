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

  public static final String REPORTINGOPERATION = "REPORTING";

  public static final String VIEWREPORTTEMPLATE = "VIEWREPORTTEMPLATE";
  public static final String VIEWREPORTPRIVATE = "VIEWREPORTPRIVATE";
  public static final String VIEWREPORTPUBLIC = "VIEWREPORTPUBLIC";
  public static final String VIEWREPORTREPORTINGFACILITY = "VIEWREPORTREPORTINGFACILITY";

  public static final char PRIVATE_REPORT_GROUP_CHAR = 'P';
  public static final char REPORTING_FACILITY_REPORT_GROUP_CHAR = 'R';
  public static final char PUBLIC_REPORT_GROUP_CHAR = 'S';
  public static final char TEMPLATE_REPORT_GROUP_CHAR = 'T';

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

  private ReportConstants() {}
}
