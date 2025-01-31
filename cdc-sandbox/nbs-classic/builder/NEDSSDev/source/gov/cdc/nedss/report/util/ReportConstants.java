package gov.cdc.nedss.report.util;

import java.io.*;
import java.util.*;

/**
 * Title:WumConstants class
 * This class consolidates the SQL statements used in the Report.
 * Copyright:    Copyright (c) 2001
 * Company:CSC
 * @author Pradeep Kumar Sharma
 * @version 1.0
 * Date Feb 01:2002
 */
public interface ReportConstants
{

    /**
     * SQL staements used by the ReportController EJB
     */
    public static final String SELECT_SHARED_REPORT_LIST =
        "SELECT report_uid \"reportUid\", data_source_uid \"dataSourceUid\", add_reason_cd \"addReasonCd\", " + "add_time \"addTime\", add_user_uid \"addUserUid\", desc_txt \"descTxt\", effective_from_time \"effectiveFromTime\", effective_to_time \"effectiveToTime\", " + "filter_mode \"filterMode\", is_modifiable_ind \"isModifiableInd\", location \"location\", owner_uid \"ownerUid\", org_access_permis \"orgAccessPermis\", " + "prog_area_access_permis \"progAreaAccessPermis\", report_title \"reportTitle\", report_type_code \"reportTypeCode\", shared \"shared\", status_cd \"statusCd\", " + "status_time \"statusTime\", category \"category\" FROM report WHERE shared = ?";
    public static final String SELECT_DATASOURCE_LIST =
        "SELECT data_source_uid \"dataSourceUid\", data_source_name \"dataSourceName\", data_source_title \"dataSourceTitle\", " + "data_source_type_code \"dataSourceTypeCode\", desc_txt \"descTxt\", condition_security \"conditionSecurity\", effective_from_time \"effectiveFromTime\", " + "effective_to_time \"effectiveToTime\", jurisdiction_security \"jurisdictionSecurity\", org_access_permis \"orgAccessPermis\", prog_area_access_permis \"progAreaAccessPermis\", " + "status_cd \"statusCd\", status_time \"statusTime\" FROM data_source";
    public static final String SELECT_TEMPLATE_REPORT_LIST =
        "SELECT report_uid \"reportUid\", data_source_uid \"dataSourceUid\", " + "add_reason_cd \"addReasonCd\", add_time \"addTime\", add_user_uid \"addUserUid\", desc_txt \"descTxt\", " + "effective_from_time \"effectiveFromTime\", effective_to_time \"effectiveToTime\", filter_mode \"filterMode\", " + "is_modifiable_ind \"isModifiableInd\", location \"location\", owner_uid \"ownerUid\", org_access_permis \"orgAccessPermis\", " + "prog_area_access_permis \"progAreaAccessPermis\", report_title \"reportTitle\", report_type_code \"reportTypeCode\", " + "shared \"shared\", status_cd \"statusCd\", status_time \"statusTime\", category \"category\" FROM report WHERE shared = ?";
    public static final String SELECT_DISPLAYABLE_COLUMN_LIST =
        "SELECT column_uid \"columnUid\", column_name \"columnName\", " + "column_title \"columnTitle\", column_type_code \"columnTypeCode\", data_source_uid \"dataSourceUid\", desc_txt \"descTxt\", " + "displayable \"displayable\", effective_from_time \"effectiveFromTime\", effective_to_time \"effectiveToTime\", filterable \"filterable\", " + "status_cd \"statusCd\", status_time \"statusTime\", column_max_len \"columnMaxLen\" FROM data_source_column WHERE data_source_uid = ? and displayable = ?";
    public static final String SELECT_FILTERCOLUMN_LIST =    	
    	"SELECT dsc.column_uid \"columnUid\", " +
    	"dsc.column_name \"columnName\",  " +
    	"dsc.column_title \"columnTitle\",  " +
    	"dsc.column_type_code \"columnTypeCode\",  " +
    	"dsc.data_source_uid \"dataSourceUid\",  " +
    	"dsc.desc_txt \"descTxt\",  " +
    	"dsc.displayable \"displayable\",  " +
    	"dsc.effective_from_time \"effectiveFromTime\",  " +
    	"dsc.effective_to_time \"effectiveToTime\",  " +
    	"dsc.filterable \"filterable\",  " +
    	"dsc.status_cd \"statusCd\",  " +
    	"dsc.status_time \"statusTime\",  " +
    	"dsc.column_max_len \"columnMaxLen\", " +
    	"dsce.code_desc_cd \"codeDescCd\", " +
        "dsce.codeset_nm \"codesetNm\" " +
    	"FROM data_source_column dsc " +
    	"LEFT OUTER JOIN " +
        "Data_Source_Codeset dsce ON dsc.column_uid = dsce.column_uid " +
    	"WHERE dsc.data_source_uid = ? and dsc.filterable = ? ";
    
    public static final String SELECT_GET_REPORT_LIST =
        "SELECT report_uid \"reportUid\", data_source_uid \"dataSourceUid\", add_reason_cd \"addReasonCd\", " + "report.add_time \"addTime\", add_user_uid \"addUserUid\", desc_txt \"descTxt\", effective_from_time \"effectiveFromTime\", effective_to_time \"effectiveToTime\", " + "filter_mode \"filterMode\", is_modifiable_ind \"isModifiableInd\", location \"location\", owner_uid \"ownerUid\", org_access_permis \"orgAccessPermis\", " + "prog_area_access_permis \"progAreaAccessPermis\", report_title \"reportTitle\", report_type_code \"reportTypeCode\", shared \"shared\", report.status_cd \"statusCd\", " + "status_time \"statusTime\", category \"category\",report.section_cd \"sectionCd\", section_desc_txt \"sectionDescTxt\" FROM report, report_section WHERE report.section_cd=report_section.section_cd and (shared = 'T' or shared = 'S' or shared = 'R' or owner_uid = ?)";
    public static final String SELECT_MYREPORT_LIST =
        "SELECT report_uid \"reportUid\", data_source_uid \"dataSourceUid\", add_reason_cd \"addReasonCd\", " + "add_time \"addTime\", add_user_uid \"addUserUid\", desc_txt \"descTxt\", effective_from_time \"effectiveFromTime\", effective_to_time \"effectiveToTime\", " + "filter_mode \"filterMode\", is_modifiable_ind \"isModifiableInd\", location \"location\", owner_uid \"ownerUid\", org_access_permis \"orgAccessPermis\", " + "prog_area_access_permis \"progAreaAccessPermis\", report_title \"reportTitle\", report_type_code \"reportTypeCode\", shared \"shared\", status_cd \"statusCd\", " + "status_time \"statusTime\", category \"category\" FROM report WHERE owner_uid = ?";
    /**
     * SQL statements for DataSourceDAOImpl
     */
    public static final String SELECT_DATASOURCE_UID = "SELECT data_source_uid FROM data_source WHERE data_source_uid = ?";
    public static final String INSERT_DATASOURCE = "INSERT INTO data_source (data_source_uid, data_source_name, data_source_title, data_source_type_code, desc_txt, condition_security, effective_from_time, effective_to_time, jurisdiction_security, org_access_permis, prog_area_access_permis, status_cd, status_time) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String UPDATE_DATASOURCE = "UPDATE data_source SET data_source_name = ?, data_source_title = ?, data_source_type_code = ?, desc_txt = ?, condition_security = ?, effective_from_time = ?, effective_to_time = ?, jurisdiction_security = ?, org_access_permis = ?, prog_area_access_permis = ?, status_cd = ?, status_time = ? WHERE data_source_uid = ?";
    public static final String SELECT_DATASOURCE = "SELECT data_source_uid \"dataSourceUid\", data_source_name \"dataSourceName\", data_source_title \"dataSourceTitle\", data_source_type_code \"dataSourceTypeCode\", desc_txt \"descTxt\", condition_security \"conditionSecurity\", effective_from_time \"effectiveFromTime\", effective_to_time \"effectiveToTime\", jurisdiction_security \"jurisdictionSecurity\", org_access_permis \"orgAccessPermis\", prog_area_access_permis \"progAreaAccessPermis\", status_cd \"statusCd\", status_time \"statusTime\", Reporting_facility_security \"reportingFacilitySecurity\" FROM data_source WHERE data_source_uid = ?";
    public static final String DELETE_DATASOURCE = "DELETE FROM data_source WHERE data_source_uid = ?";
    /**
     * SQL statements used by DataSourceColumnDAOImpl
     */
    public static final String SELECT_DATASOURCECOLUMN_UID = "SELECT column_uid FROM data_source_column WHERE column_uid = ?";
    public static final String INSERT_DATASOURCECOLUMN = "INSERT INTO data_source_column (column_uid, column_name, column_title, column_type_code, data_source_uid, desc_txt, displayable, effective_from_time, effective_to_time, filterable, status_cd, status_time, column_max_len) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String UPDATE_DATASOURCECOLUMN = "UPDATE data_source_column SET column_name = ?, column_title = ?, column_type_code = ?, data_source_uid = ?, desc_txt = ?, displayable = ?, effective_from_time = ?, effective_to_time = ?, filterable = ?, status_cd = ?, status_time = ?, column_max_len = ? WHERE column_uid = ?";
    public static final String SELECT_DATASOURCECOLUMN = 
		"SELECT dsc.column_uid \"columnUid\", " +
		"dsc.column_name \"columnName\",  " +
		"dsc.column_title \"columnTitle\",  " +
		"dsc.column_type_code \"columnTypeCode\",  " +
		"dsc.data_source_uid \"dataSourceUid\",  " +
		"dsc.desc_txt \"descTxt\",  " +
		"dsc.displayable \"displayable\",  " +
		"dsc.effective_from_time \"effectiveFromTime\",  " +
		"dsc.effective_to_time \"effectiveToTime\",  " +
		"dsc.filterable \"filterable\",  " +
		"dsc.status_cd \"statusCd\",  " +
		"dsc.status_time \"statusTime\",  " +
		"dsc.column_max_len \"columnMaxLen\", " +
		"dsce.code_desc_cd \"codeDescCd\", " +
	    "dsce.codeset_nm \"codesetNm\" " +
		"FROM data_source_column dsc " +
		"LEFT OUTER JOIN " +
	    "Data_Source_Codeset dsce ON dsc.column_uid = dsce.column_uid " +
		"WHERE dsc.data_source_uid = ?";   
    
    public static final String DELETE_DATASOURCECOLUMN = "DELETE FROM data_source_column WHERE column_uid = ?";
    /**
     * SQL statements used by DisplayColumnsDAOImpl
     */
    public static final String DC_SELECT_DISPLAYCOLUMN_UID = "SELECT display_column_uid FROM display_column WHERE display_column_uid = ?";
    public static final String DC_INSERT_DISPLAYCOLUMN = "INSERT INTO display_column (display_column_uid, column_uid, data_source_uid, report_uid, sequence_nbr, status_cd) VALUES (?, ?, ?, ?, ?, ?)";
    public static final String DC_UPDATE_DISPLAYCOLUMN = "UPDATE display_column SET column_uid = ?, data_source_uid = ?, report_uid = ?, sequence_nbr = ?, status_cd = ? WHERE display_column_uid = ?";
    public static final String DC_SELECT_DISPLAYCOLUMN = "SELECT display_column_uid \"displayColumnUid\", column_uid \"columnUid\", data_source_uid \"dataSourceUid\", report_uid \"reportUid\", sequence_nbr \"sequenceNbr\", status_cd \"statusCd\" FROM display_column WHERE report_uid  = ? ORDER BY sequence_nbr";
    public static final String DC_DELETE_DISPLAYCOLUMN = "DELETE FROM display_column WHERE display_column_uid = ?";
    public static final String DC_SELECT_DATASOURCECOLUMN_UID = "SELECT column_uid FROM data_source_column WHERE column_uid = ?";
    public static final String DC_SELECT_DATASOURCECOLUMN = "SELECT column_uid \"columnUid\", column_name \"columnName\", column_title \"columnTitle\", column_type_code \"columnTypeCode\", data_source_uid \"dataSourceUid\", desc_txt \"descTxt\", displayable \"displayable\", effective_from_time \"effectiveFromTime\", effective_to_time \"effectiveToTime\", filterable \"filterable\", status_cd \"statusCd\", status_time \"statusTime\", column_max_len \"columnMaxLen\" FROM data_source_column WHERE column_uid = ?";
    
    /**
     * SQL statements used by DisplayColumnsDAOImpl
     */
    public static final String INSERT_SORTCOLUMN_ORDER = "INSERT INTO report_sort_column (report_sort_column_uid, report_sort_order_code,report_sort_sequence_num,data_source_uid, report_uid,column_uid, status_cd, status_time) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String UPDATE_SORTCOLUMN_ORDER = "UPDATE report_sort_column SET report_sort_order_code = ?, report_sort_sequence_num = ?, data_source_uid = ?, report_uid = ?, column_uid = ?, status_cd = ?, status_time  = ? WHERE report_sort_column_uid = ?";
    public static final String SELECT_SORTCOLUMN_ORDER = "SELECT report_sort_column_uid \"reportSortColumnUid\", report_sort_order_code \"reportSortOrderCode\", report_sort_sequence_num \"reportSortSequenceNum\", data_source_uid \"dataSourceUid\", report_uid \"reportUid\", column_uid \"columnUid\", status_cd \"statusCd\", status_time \"statusTime\" FROM report_sort_column WHERE report_uid  = ?";
    public static final String DELETE_SORTCOLUMN_ORDER = "DELETE FROM report_sort_column WHERE report_sort_column_uid = ?";
    
    
    /**
     * SQL Staements used by the ReportDAOImpl
     */
    public static final String SELECT_REPORT_UID = "SELECT report_uid FROM report WHERE report_uid = ?";
    public static final String INSERT_REPORT = "INSERT INTO report (report_uid, data_source_uid, add_reason_cd, add_time, add_user_uid, desc_txt, effective_from_time, effective_to_time, filter_mode, is_modifiable_ind, location, owner_uid, org_access_permis, prog_area_access_permis, report_title, report_type_code, shared, status_cd, status_time, category, section_cd) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
    public static final String UPDATE_REPORT = "UPDATE report SET add_reason_cd = ?, add_time = ?, add_user_uid = ?, desc_txt = ?, effective_from_time = ?, effective_to_time = ?, filter_mode = ?, is_modifiable_ind = ?, location = ?, owner_uid = ?, org_access_permis = ?, prog_area_access_permis = ?, report_title = ?, report_type_code = ?, shared = ?, status_cd = ?, status_time = ?, category = ?, section_cd = ? WHERE report_uid = ? AND data_source_uid = ?";
    public static final String SELECT_REPORT = "SELECT report_uid \"reportUid\", data_source_uid \"dataSourceUid\", add_reason_cd \"addReasonCd\", add_time \"addTime\", add_user_uid \"addUserUid\", desc_txt \"descTxt\", effective_from_time \"effectiveFromTime\", effective_to_time \"effectiveToTime\", filter_mode \"filterMode\", is_modifiable_ind \"isModifiableInd\", location \"location\", owner_uid \"ownerUid\", org_access_permis \"orgAccessPermis\", prog_area_access_permis \"progAreaAccessPermis\", report_title \"reportTitle\", report_type_code \"reportTypeCode\", shared \"shared\", status_cd \"statusCd\", status_time \"statusTime\", category \"category\", section_cd \"sectionCd\" FROM report WHERE report_uid = ?";
    public static final String DELETE_REPORT = "DELETE FROM report WHERE report_uid = ?";
    /**
     * SQL Staements used by the ReportFilterDAOImpl
     */
    public static final String SELECT_REPORTFILTER_UID = "SELECT report_filter_uid FROM report_filter WHERE report_filter_uid = ?";
    public static final String INSERT_REPORTFILTER = "INSERT INTO report_filter (report_filter_uid, report_uid, data_source_uid, filter_uid, status_cd, max_value_cnt, min_value_cnt, column_uid) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String UPDATE_REPORTFILTER = "UPDATE report_filter SET report_uid = ?, data_source_uid = ?, filter_uid = ?, status_cd = ?, max_value_cnt = ?, min_value_cnt = ?, column_uid = ? WHERE report_filter_uid = ?";
    
    public static final String SELECT_REPORTFILTER = 
    	"SELECT rf.report_filter_uid \"reportFilterUid\", " +
    	"rf.report_uid \"reportUid\", " +
    	"rf.data_source_uid \"dataSourceUid\", " +
    	"rf.filter_uid \"filterUid\", " +
    	"rf.status_cd \"statusCd\", " +
    	"rf.max_value_cnt \"maxValueCnt\", " +
    	"rf.min_value_cnt \"minValueCnt\", " +
    	"rf.column_uid \"columnUid\", " +
    	"rfv.report_filter_ind \"reportFilterInd\" " +	
    	"FROM report_filter rf " +
    	"LEFT OUTER JOIN " +
    	"report_filter_validation rfv ON rf.report_filter_uid = rfv.report_filter_uid " +
    	"WHERE report_uid = ?";
    
    public static final String INSERT_REPORTFILTER_VALIDATION = "INSERT INTO report_filter_validation(report_filter_validation_uid, report_filter_uid, report_filter_ind) VALUES(?,?,?)";
    public static final String DELETE_REPORTFILTER_VALIDATION = "DELETE FROM report_filter_validation WHERE report_filter_uid = ?";
    
    public static final String DELETE_REPORTFILTER = "DELETE FROM report_filter WHERE report_filter_uid = ?";
    public static final String SELECT_FILTERVALUE_UID = "SELECT value_uid FROM filter_value WHERE report_filter_uid = ?";
    public static final String INSERT_FILTERVALUE = "INSERT INTO filter_value (value_uid, report_filter_uid, sequence_nbr, value_type, column_uid, operator, value_txt) VALUES (?, ?, ?, ?, ?, ?, ?)";
    public static final String UPDATE_FILTERVALUE = "UPDATE filter_value SET report_filter_uid = ?, sequence_nbr = ?, value_type = ?, column_uid = ?, operator = ?, value_txt = ? WHERE value_uid = ?";
    public static final String SELECT_FILTERVALUE = "SELECT value_uid \"valueUid\", report_filter_uid \"reportFilterUid\", sequence_nbr  \"sequenceNbr\", value_type \"valueType\", column_uid \"columnUid\", operator \"filterValueOperator\", value_txt \"valueTxt\" FROM filter_value WHERE report_filter_uid = ? ORDER BY value_uid";
    public static final String DELETE_FILTERVALUE = "DELETE FROM filter_value WHERE value_uid = ?";
    public static final String SELECT_RF_FILTERCODE_UID = "SELECT filter_uid FROM filter_code WHERE filter_uid = ?";
    public static final String RF_SELECT_FILTERCODE = "SELECT filter_uid \"filterUid\", code_table \"codeTable\", desc_txt \"descTxt\", effective_from_time \"effectiveFromTime\", effective_to_time \"effectiveToTime\", filter_code \"filterCode\", filter_code_set_nm \"filterCodeSetNm\", filter_type \"filterType\", filter_name \"filterName\", status_cd \"statusCd\", status_time \"statusTime\" FROM filter_code WHERE filter_uid = ?";

    /**
     * Where Clause used by the ReportSQLBuilder
     */
    public static final String WHERE_LAB_RESULT_VAL = "where root_ordered_test_pntr in (select root_ordered_test_pntr from nbs_rdb.lab_test_report ";
}
