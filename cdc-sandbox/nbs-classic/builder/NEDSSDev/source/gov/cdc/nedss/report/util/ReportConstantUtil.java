package gov.cdc.nedss.report.util;

import gov.cdc.nedss.report.ejb.reportcontrollerejb.bean.ReportControllerHome;
import gov.cdc.nedss.util.*;

import java.io.*;
import java.util.*;

/**
 * ReportConstantUtil stores constants related to ReportWebProcessor
 * and ReportSQLBuilder
 *
 * @version     1.82 18 Mar 1999
 * @author  Narendra Mallela
 */
public class ReportConstantUtil
{

    static final LogUtils logger = new LogUtils((ReportConstantUtil.class).getName());  //Used for logging
    public static final boolean DEBUG_MODE = false;  // Used for debugging
    //FILTER CODE CONSTANTS
    public static final String DISEASE_CODE = "C_D01";
    public static final String STATE_CODE = "J_S01";
    public static final String COUNTY_CODE = "J_C01";
    public static final String REGION_CODE = "J_R01";
    public static final String WHERE_CLAUSE_CODE = "A_W01";
    public static final String TIME_RANGE_CODE = "T_T01";
    public static final String TIME_PERIOD_CODE = "T_T02";
    public static final String MONTH_YEAR_RANGE_CODE = "M_Y01";
    public static final String REPORT_SECTION = "R_S01";
    
    //FILTER CODE CONSTANTS WITH NULLS
    public static final String DISEASE_CODE_N = "C_D01_N";
    public static final String STATE_CODE_N = "J_S01_N";
    public static final String COUNTY_CODE_N = "J_C01_N";
    public static final String REGION_CODE_N = "J_R01_N";
    public static final String TIME_RANGE_CODE_N = "T_T01_N";
    public static final String TIME_PERIOD_CODE_N = "T_T02_N";  
    public static final String MONTH_YEAR_RANGE_CODE_N = "M_Y01_N";
    public static final String CVG_CUSTOM_N = "CVG_CUSTOM_N";
    public static final String TEXT_FILTER = "TXT_01";
    public static final String DAYS_FILTER = "D_01";
    public static final String STD_HIV_WRKR = "STD_HIV_WRKR";
    
    //Data_source_column column_type_code Constants
    public static final String COL_TYPE_STRING = "STRING";
    public static final String COL_TYPE_DATE = "DATE";
    public static final String COL_TYPE_DATETIME = "DATETIME";
    public static final String COL_TYPE_INTEGER = "INTEGER";
    public static final String COL_TYPE_NUMBER = "NUMBER";
    //Report Report_type_code Constants
    public static final String SAS_CUSTOM = "SAS_CUSTOM";
    public static final String SAS_GRAPH = "SAS_GRAPH";
    public static final String SAS_JAVA = "SAS_JAVA";
    public static final String SAS_ODS_HTML = "SAS_ODS_HTML";
    //Filter_Code  Filter_Type Constants
    public static final String BAS_TXT = "BAS_TXT";
    public static final String BAS_DAYS = "BAS_DAYS";
    public static final String BAS_CVG_LIST = "BAS_CVG_LIST";
    public static final String BAS_STD_HIV_WRKR = "BAS_STD_HIV_WRKR";
    public static final String BAS_CON_LIST = "BAS_CON_LIST";
    public static final String BAS_JUR_LIST = "BAS_JUR_LIST";
    public static final String BAS_TIM_LIST = "BAS_TIM_LIST";
    public static final String BAS_TIM_RANGE = "BAS_TIM_RANGE";
    public static final String BAS_TIM_RANGE_CUSTOM = "BAS_TIM_RANGE_CUSTOM";
    public static final String BAS_TIM_RANGE_LIST = "BAS_TIM_RANGE_LIST";
    public static final String BAS_MM_YYYY_RANGE = "BAS_MM_YYYY_RANGE";
    public static final String ADV_WCB = "ADV_WCB";
    //Filter_Value Value_Type Constants
    public static final String VAL_TYPE_OPERATOR = "OPERATOR";
    public static final String VAL_TYPE_CLAUSE = "CLAUSE";
    public static final String VAL_TYPE_CODE = "CODE";
    public static final String BEGIN_RANGE = "BEGIN_RANGE";
    public static final String END_RANGE = "END_RANGE";
    //SESSION ATTRIBUTES
    public static final String SECURITY_OBJECT = "NBSSecurityObject";
    public static final String RESULT = "result";
    public static final String OLD_RESULT = "oldReport";
    public static final String PRIVATE_REPORT_LIST = "PRIVATE_REPORTS";
    public static final String PUBLIC_REPORT_LIST = "PUBLIC_REPORTS";
    public static final String TEMPLATE_REPORT_LIST = "REPORT_TEMPLATES";
    public static final String REPORTING_FACILITY_REPORT_LIST = "REPORTING_FACILITY_REPORTS";
    public static final String DISEASE_LIST = "Diseases";
    public static final String CUSTOM_LIST = "Custom";
    public static final String STATE_LIST = "States";
    public static final String COUNTY_LIST = "Counties";
    public static final String REGION_LIST = "Regions";
    public static final String TIME_PERIOD_RANGE = "Time Range";
    public static final String TIME_PERIOD_LIST = "Time Period";
    public static final String FILTERABLE_COLUMNS = "FILTERABLE_COLUMNS";
    public static final String CRITERIA_LIST = "CRITERIA_LIST";
    public static final String AVAILABLE_COLUMNS = "AVAILABLE_COLUMNS";
    public static final String AVAILABLE_COLUMN_LIST = "AVAILABLE_COLUMN_LIST";
    public static final String SELECTED_COLUMNS = "SELECTED_COLUMNS";
    public static final String FILTERS = "filters";
    public static final String CURRENT_STATE = "CURRENT_STATE";
    //public static final String REPORTUID           =   "reportUID";
    //public static final String DATASOURCEUID       =   "dataSourceUID";
    //REQUEST ATTRIBUTES
    public static final String OBJECT_TYPE = "ObjectType";
    public static final String OPERATION_TYPE = "OperationType";
    public static final String REPORT_UID = "ReportUID";
    public static final String DATASOURCE_UID = "DataSourceUID";
    public static final String DISEASE = "C_D01";
    public static final String STATE = "J_S01";
    public static final String COUNTY = "J_C01";
    public static final String REGION = "J_R01";
    public static final String CVG_CUSTOM = "CVG_CUSTOM";
    public static final String CVG_CUSTOM_1 = "CVG_CUSTOM_N01";
    public static final String CVG_CUSTOM_2 = "CVG_CUSTOM_N02";
    public static final String TIME_RANGE_FROM = "T_T01a";
    public static final String TIME_RANGE_TO = "T_T01b";
    public static final String MONTH_YEAR_RANGE_FROM = "M_Y01a";
    public static final String MONTH_YEAR_RANGE_TO = "M_Y01b";
    public static final String TIME_PERIOD_FROM = "T_T02a";
    public static final String TIME_PERIOD_TO = "T_T02b";
    public static final String CRITERIA = "CriteriaList";
    public static final String COLUMNS = "Columns";
    public static final String MODE = "mode";
    public static final String REPORT_NAME = "name";
    public static final String REPORT_DESCRIPTION = "description";
    //RETURN PAGE CONSTANTS
    public static final String PROXY_PAGE = "/nbs/report/proxy";
    public static final String REPORT_LIST_PAGE = "/nbs/ManageReports.do";
    public static final String BASIC_PAGE = "/nbs/report/basic";
    public static final String ADVANCE_PAGE = "/nbs/report/advanced";
    public static final String COLUMN_PAGE = "/nbs/report/column";
    //RUNPAGE modified from RUN_PAGE as to avoid conflict from RUN_PAGE of NEDSSContantUtil
    public static final String RUNPAGE = "/nbs/report/run";
    //RUNPAGE modified from SAVE_PAGE as to avoid conflict from SAVE_PAGE of NEDSSContantUtil
    public static final String SAVEPAGE = "/nbs/report/save";
    public static final String ERROR_PAGE = "/nbs/error";
    public static final String LOGIN_PAGE = "/nbs/nfc";
    public static final String SAS_ERROR_PAGE = "/nbs/report/error";

    public static final String PRIVATE_REPORT = "P";
    public static final String TEMPLATE_REPORT = "T";
    public static final String PUBLIC_REPORT = "S";
    public static final String REPORTING_FACILITY_REPORT = "R";
    
    public static final String SORT_BY = "SORT_BY";
    public static final String SORT_ORDER = "SORT_ORDER";
    public static final String SORT_COLUMN = "SORT_COLUMN";
    
    public static final String INCLUDE_NULL_DISEASE = "INCLUDE_NULL_DISEASE";
    public static final String INCLUDE_NULL_STATE = "INCLUDE_NULL_STATE";
    public static final String INCLUDE_NULL_COUNTY = "INCLUDE_NULL_COUNTY";
    public static final String INCLUDE_NULL_REGION = "INCLUDE_NULL_REGION";
    public static final String INCLUDE_NULL_TIMERANGE = "INCLUDE_NULL_TIMERANGE";
    public static final String INCLUDE_NULL_TIMEPERIOD = "INCLUDE_NULL_TIMEPERIOD";
    public static final String INCLUDE_NULL_MONTHYEAR_RANGE = "INCLUDE_NULL_MONTHYEAR_RANGE";
    public static final String INCLUDE_NULL_CVG = "INCLUDE_NULL_CVG";
    public static final String ALLOW_NULLS = "ALLOW_NULLS";
    public static final String SELECT_ALL_DISEASES = "SELECT_ALL_DISEASES";
    public static final String SELECT_ALL_COUNTIES = "SELECT_ALL_COUNTIES";
    public static final String COUNTIES_SELECTED = "COUNTIES_SELECTED";
    public static final String SELECT_ALL_CVG = "SELECT_ALL_CVG";
    public static final String SELECT_ALL_WRKR = "SELECT_ALL_WRKR";
    
    public static final String COLUMNTYPE_STRING = "STRING";
    public static final String COLUMNTYPE_INTEGER = "INTEGER";
    public static final String COLUMNTYPE_DATETIME = "DATETIME";
    public static final String ALL_FILTER_OPERATORS = "ALL_FILTER_OPERATORS";
    
    public static final String CODED_VALUES = "CODED_VALUES";
    public static final String BASIC_FILTERS_ENTERED = "BASIC_FILTERS_ENTERED";
    public static final String ADVANCE_FILTERS_ENTERED = "ADVANCE_FILTERS_ENTERED";
    public static final String BASIC_REQUIRED_ENTERED = "BASIC_REQUIRED_ENTERED";
    public static final String ANY_BASIC_REQUIRED = "ANY_BASIC_REQUIRED";
    public static final String IS_BASICDATA_VALID = "IS_BASICDATA_VALID";
}
