package gov.cdc.nedss.systemservice.sqlscript;

import gov.cdc.nedss.util.NEDSSConstants;

/**
 * This class consolidates the SQL statements used in the CachedDropDown values for SRT.
 */
public class SRTSQLQuery {

   public static final String TREATMENTPREDEFINEDQUERYSQL =
       "Select  code_system_cd \"codeSystemCd\", code_system_desc_txt \"cdSystemDescTxt\", dose_qty \"doseQty\"," + " dose_qty_unit_cd \"doseQtyUnitCd\"," +
       " route_cd \"routeCd\"," + " interval_cd \"intervalCd\"," +
       " duration_amt \"durationAmt\"," +
       " duration_unit_cd \"durationUnitCd\" from " +
       NEDSSConstants.SYSTEM_REFERENCE_TABLE +
       "..TREATMENT_CODE where TREATMENT_CD = ? ";

   //  public static final String CODE_QUERY_SQL = "Select code, code_short_desc_txt from " + NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..code_Value_General where upper(code_set_nm) = ? order by code_short_desc_txt";
   //  public static final String CODE_QUERY_ORACLE = "Select code, code_short_desc_txt from " + NEDSSConstants.SYSTEM_REFERENCE_TABLE + ".code_Value_General where upper(code_set_nm) = ? order by code_short_desc_txt";
   /*public static final String PHCQUERYSQL = "Select code \"key\", " +
                                            " code_desc_txt \"value\"  from " +
                                            NEDSSConstants.
                                            SYSTEM_REFERENCE_TABLE +
       "..code_Value_General where upper(code_set_nm) = ? ";
   public static final String PHCQUERYORACLE = "Select code \"key\", " +
                                               " code_desc_txt \"value\" from " +
                                               NEDSSConstants.
                                               SYSTEM_REFERENCE_TABLE +
       ".code_Value_General where upper(code_set_nm) = ? ";*/
   public static final String CODEQUERYSQL = "Select code \"key\", " +
       " code_short_desc_txt \"value\", concept_code \"altValue\" , status_cd \"statusCd\",  effective_to_time \"effectiveToTime\" from " +
	   
                                             NEDSSConstants.SYSTEM_REFERENCE_TABLE +
       "..code_Value_General where upper(code_set_nm) = ? order by concept_order_nbr, code_short_desc_txt ";

    public static final String CODEQUERYSQL_FOR_DESCRIPTION_TXT = "Select code \"key\", " +
       " code_desc_txt \"value\" from " +
                                             NEDSSConstants.SYSTEM_REFERENCE_TABLE +
       "..code_Value_General where upper(code_set_nm) = ? ";

   public static final String CITYSQL = "select code \"key\", " +
                                        " code_desc_txt \"value\"  from " +
                                        NEDSSConstants.SYSTEM_REFERENCE_TABLE +
                                        "..city_code order by code_desc_txt";
    //public static final String STATESQL = "select state_nm AS code, state_nm As code_desc_txt from nbs_srt..state_code " ;
   //added another state sql STATESQL1 with state_cd as code and code_desc_txt as description :)
   public static final String STATESQL = "select state_cd \"key\", " +
                                         " code_desc_txt \"value\", status_cd \"statusCd\",  effective_to_time \"effectiveToTime\" from " +
                                         NEDSSConstants.SYSTEM_REFERENCE_TABLE +
                                         "..state_code order by code_desc_txt ";
   public static final String STATEABSQL = "select state_cd \"key\", " +
                                           " state_nm \"value\", status_cd \"statusCd\",  effective_to_time \"effectiveToTime\" from  " +
                                           NEDSSConstants.SYSTEM_REFERENCE_TABLE +
                                           "..state_code ";
    public static final String COUNTYSQL = "SELECT code \"key\", " +
                                          " code_desc_txt \"value\" FROM " +
                                          NEDSSConstants.SYSTEM_REFERENCE_TABLE +
                                          "..State_county_code_value where INDENT_LEVEL_NBR='2' ";
    public static final String COUNTY_DESC_SQL = "SELECT code \"key\", " +
													   " code_desc_txt \"value\" FROM " +
													   NEDSSConstants.SYSTEM_REFERENCE_TABLE +
													   "..State_county_code_value where code= ";
 
    public static final String COUNTYSHORTDESCSQL = "SELECT code \"key\", " +
                                           " code_short_desc_txt \"value\" FROM " +
                                           NEDSSConstants.SYSTEM_REFERENCE_TABLE +
                                           "..State_county_code_value where INDENT_LEVEL_NBR='2'";
    public static final String COUNTRYSQL = "select code \"key\", " +
                                           " code_desc_txt \"value\" from " +
                                           NEDSSConstants.
                                           SYSTEM_REFERENCE_TABLE +
                                           "..country_code order by code_desc_txt";
  public static final String COUNTRYSHORTDESCSQL = "select code \"key\", " +
                                                   " code_short_desc_txt \"value\" from " +
                                                   NEDSSConstants.SYSTEM_REFERENCE_TABLE +
                                                   "..country_code ";
   //public static final String RACESQL = "select code, code_short_desc_txt from " + NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..code_value_general ";
   public static final String RACESQL = "select  code \"key\", " +
                                        " code_short_desc_txt \"value\"  from " +
                                        NEDSSConstants.SYSTEM_REFERENCE_TABLE +
                                        "..race_code ";
    public static final String GETADDRTYPESQL = "select  code \"key\", " +
       " code_short_desc_txt \"value\" from " +
                                               NEDSSConstants.SYSTEM_REFERENCE_TABLE +
                                               "..code_value_general " +
       "where (code_set_nm = 'EL_TYPE') and (code not in ('BDL', 'BR'))  ";
    public static final String GETADDRUSESQL = "select  code \"key\", " +
       " code_short_desc_txt \"value\" from " +
                                              NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..code_value_general where (code_set_nm = 'EL_USE') and (code NOT IN ('BDL', 'BIR', 'DTH')) ";
    public static final String REGIONSQL = "SELECT code \"key\", " +
       " code_short_desc_txt \"value\" FROM " +
                                          NEDSSConstants.SYSTEM_REFERENCE_TABLE +
       "..State_county_code_value WHERE   (code_set_nm = 'FIPS_9REGIONS')";
   public static final String REGIONORACLE = "SELECT code  \"key\", " +
       " code_short_desc_txt \"value\" FROM " +
                                             NEDSSConstants.SYSTEM_REFERENCE_TABLE +
       ".State_county_code_value WHERE   (code_set_nm = 'FIPS_9REGIONS')";
   public static final String PROGRAMAREASQL =
	   "select condition_cd \"key\", " +
       " prog_area_cd \"value\"  FROM " +
       NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..condition_code " +
       " WHERE condition_cd = ?";   
    public static final String PROGRAM_AREA_LIST_SQL =
       "select DISTINCT  prog_area_cd \"key\", " +
       " prog_area_desc_txt \"value\" FROM " +
       NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..Program_area_code";
   public static final String CONDTION_CODE_SQL =
       "SELECT condition_cd \"key\", " + " condition_short_nm \"value\" FROM " +
       NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..Condition_Code ";

    public static final String CONDTIONANDPROGRAMAREA_CODE_SQL =
        "SELECT condition_cd \"key\", " + " prog_area_cd \"value\" FROM " +
        NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..Condition_Code ";
  
    public static final String CONDTIONANDINVFORM_CODE_SQL =
        "SELECT condition_cd \"key\", " + " investigation_form_cd \"value\" FROM " +
        NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..Condition_Code ";
     
    public static final String CONDTIONFAMILY_CODE_SQL =
        "SELECT condition_cd \"key\", " + " condition_short_nm \"value\" FROM " +
        NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..Condition_Code " +
        "where family_cd in (select family_cd from " + NEDSSConstants.SYSTEM_REFERENCE_TABLE + 
        "..Condition_Code " + "where condition_cd='?') " +
        "and condition_cd in (select condition_cd from wa_template where template_type='Published')" +
        " order by value";
     
    public static final String BM_OTHER_BAC_SP_AND_BM_SPEC_ISOL_SQL =
        "SELECT code_short_desc_txt \"key\", " + " code_Set_nm \"value\" FROM " +
        NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..code_value_general where code_Set_nm in('BM_OTHER_BAC_SP','BM_SPEC_ISOL')";
     public static final String BM_OTHER_BAC_SP_AND_BM_SPEC_ISOL_CODE_DESC_TEXT_KEY_SQL =
        "SELECT code_desc_txt \"key\", " + " code \"value\" FROM " +
        NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..code_value_general where code_Set_nm in('BM_OTHER_BAC_SP','BM_SPEC_ISOL')";

 
    public static final String VAC_NM_CODE_SQL =
        "SELECT code_short_desc_txt \"key\", " + " code \"value\" FROM " +
        NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..code_value_general where code_Set_nm = 'VAC_NM' order by code_short_desc_txt";

  
    public static final String STATE_DEFINED_SRT_CODESET_SQL =
        "SELECT code_set_nm \"key\", " + " code_set_short_desc_txt \"value\" , status_cd \"statusCd\",  effective_to_time \"effectiveToTime\" from  " +
        NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..Codeset_Group_Metadata where ldf_picklist_ind_cd='Y' order by code_set_short_desc_txt";

      public static final String SAIC_DEFINED_SRT_CODESET_SQL =
                "SELECT code_set_nm \"key\", " + " code_set_nm \"value\" FROM " +
                NEDSSConstants.SYSTEM_REFERENCE_TABLE+"..CODE_VALUE_CLINICAL ";

     public static final String SAIC_DEFINED_SRT_CODES_SQL =
                "SELECT code \"key\", " + " CODE_SHORT_DESC_TXT \"value\" FROM " +
                NEDSSConstants.SYSTEM_REFERENCE_TABLE+"..CODE_VALUE_CLINICAL where code_set_nm=? " +
                " order by ORDER_NUMBER";
  public static final String RESULTDTESTDESCSQL =
     "SELECT loinc_cd \"key\", " + " component_name \"value\" FROM " +
     NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..LOINC_CODE ";

  public static final String RESULTDTESTDESCLABSQL =
   "SELECT lab_test_cd \"key\", " + " lab_test_desc_txt \"value\" FROM " +
  NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..LAB_TEST ";

  public static final String CODE_DESC_TXT_SQL =
       "SELECT DISTINCT code_desc_txt \"key\" " +
//     ", 1 \"value\"  FROM "
       " FROM " + NEDSSConstants.SYSTEM_REFERENCE_TABLE +
       "..Code_value_general WHERE code = ?";
    public static final String LANGUAGESQL = "SELECT code \"key\", " +
       " code_short_desc_txt \"value\"  FROM " +
                                            NEDSSConstants.SYSTEM_REFERENCE_TABLE +
                                            "..language_code";
    public static final String INDUSTRYCODEESQL = "SELECT code \"key\", " +
       " code_short_desc_txt \"value\" FROM " +
       NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..Industry_code";
   public static final String NAICSINDUSTRYCODEESQL = "SELECT code \"key\", " +
       " code_short_desc_txt \"value\" FROM " +
       NEDSSConstants.SYSTEM_REFERENCE_TABLE +
             "..NAICS_Industry_code where indent_level_nbr=1";
    public static final String CASEWORKERSSQL =
	       "SELECT distinct root_extension_txt \"key\", " + " first_nm+' '+last_nm \"value\" FROM " +
	    		   "person_name pn , entity_id ei, auth_user au, auth_user_role aur " +
	    		   "where pn.person_uid = ei.entity_uid " +
	    		   "and ei.type_cd = 'QEC' "+
	    		   "and au.provider_uid = pn.person_uid "+
	    		   "and au.auth_user_uid = aur.auth_user_uid and root_extension_txt is not null and root_extension_txt !='' and aur.prog_area_cd in";
   //public static final String PROGRAMAREACONDITIONSSQL = "SELECT condition_cd, condition_short_nm, state_prog_area_code, state_prog_area_cd_desc FROM "+ NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..Condition_code WHERE status_cd = 'A' AND state_prog_area_code IN ";
   public static final String PROGRAMAREACONDITIONSSQL =
       "SELECT c.condition_cd \"conditionCd\", " + " c.condition_short_nm \"conditionShortNm\", c.prog_area_cd \"stateProgAreaCode\", " + "p.prog_area_desc_txt \"stateProgAreaCdDesc\", c. investigation_form_cd \"investigationFormCd\" FROM " +
       NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..Condition_code c INNER JOIN " +
       NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..Program_area_code p ON c.prog_area_cd = p.prog_area_cd " +
       " and c.indent_level_nbr = ? and c.prog_area_cd IN ";
   public static final String PROGRAMAREACONDITIONSSQLWOINDENT =
       "SELECT c.condition_cd \"conditionCd\", " + " c.condition_short_nm \"conditionShortNm\", c.prog_area_cd \"stateProgAreaCode\", " + "p.prog_area_desc_txt \"stateProgAreaCdDesc\", c. investigation_form_cd \"investigationFormCd\" FROM " +
       NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..Condition_code c INNER JOIN " +
       NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..Program_area_code p ON c.prog_area_cd = p.prog_area_cd " +
       " and c.condition_cd = ?";
   public static final String ACTIVE_PROGRAMAREA_CONDITIONS_SQL =
       "SELECT c.condition_cd \"conditionCd\", " + " c.condition_short_nm \"conditionShortNm\", c.prog_area_cd \"stateProgAreaCode\", " + "p.prog_area_desc_txt \"stateProgAreaCdDesc\", c. investigation_form_cd \"investigationFormCd\" FROM " +
       NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..Condition_code c INNER JOIN " +
       NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..Program_area_code p ON c.prog_area_cd = p.prog_area_cd and c.status_cd = 'A' " +
       " and c.investigation_form_cd is not NULL and c.indent_level_nbr = ? and c.prog_area_cd IN ";

     // public static final String STATEORACLESQL = "select state_nm AS code, state_nm As code_desc_txt from " + NEDSSConstants.SYSTEM_REFERENCE_TABLE + ".state_code " ;
   //public static final String PROGRAMAREAORACLESQL = "select DISTINCT  a.SUPER_CODE, b.code_desc_txt FROM " + NEDSSConstants.SYSTEM_REFERENCE_TABLE + ".Code_value_general a, " + NEDSSConstants.SYSTEM_REFERENCE_TABLE + ".code_value_general b WHERE b.code_set_nm = a.super_code_set_nm and b.code = a.super_code and  a.code = ?";
   public static final String COUNTRY_CODE_SQL = "SELECT code \"key\", " +
       "code_short_desc_txt \"value\" " + "FROM " +
                                                 NEDSSConstants.SYSTEM_REFERENCE_TABLE +
                                                 "..Country_code order by CODE_SHORT_DESC_TXT";
    //-----------for build d security
   public static final String PROGRAM_AREA_CODED_VALUES_SQL =
       "SELECT prog_area_cd \"key\" , " + "prog_area_desc_txt \"value\" FROM " +
       NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..Program_area_code";

    public static final String CODED_RESULT_VALUES_SQL =
    "SELECT lab_result_cd \"key\" , " + "lab_result_desc_txt \"value\" FROM " +
    NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..lab_result where ORGANISM_NAME_IND = 'N' AND LABORATORY_ID = 'DEFAULT'";

  
  public static final String CODED_RESULT_VALUES_SUSC_SQL =
		    "SELECT code \"key\" , " + "code_desc_txt \"value\" FROM " +
		    NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..v_lab_result where ORGANISM_NAME_IND = 'N' AND LABORATORY_ID = 'DEFAULT'";

		  public static final String RESULT_METHOD_VALUES_SUSC_SQL =
				    "SELECT code \"key\" , " + "code_short_desc_txt \"value\" FROM " +
				    NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..V_OBSERVATION_METHOD";
		  public static final String RESULT_METHOD_VALUES_SUSC_ORACLE =
				    "SELECT code \"key\" , " + "code_short_desc_txt \"value\" FROM " +
				    NEDSSConstants.SYSTEM_REFERENCE_TABLE + ".V_OBSERVATION_METHOD";

		  
		  
   public static final String PROGRAM_AREA_IDS_SQL =
       "SELECT prog_area_cd \"key\" , " + "nbs_uid \"intValue\" FROM " +
       NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..Program_area_code";
   public static final String JURISDICTION_CODED_VALUES_SQL =
       "SELECT code \"key\" , " + " code_desc_txt \"value\" , export_ind \"altValue\" FROM " +
       NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..Jurisdiction_code order by code_desc_txt";
   //"where state_domain_cd = \'47\'";
   public static final String JURISDICTION_CODED_VALUES_FOR_DOC_SQL =
       "SELECT code \"key\" , " + " code_desc_txt \"value\" , export_ind \"altValue\" FROM " +
       NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..Jurisdiction_code where ( export_ind IS NULL OR export_ind <> 'T') order by code_desc_txt";
    //"where state_domain_cd = \'47\'";
   public static final String JURISDICTION_NUMERIC_IDS_SQL =
       "SELECT code \"key\" , " + "nbs_uid  \"intValue\" FROM " +
       NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..Jurisdiction_code ";
 
   public static final String LAB_TEST_SQL = "SELECT nbs_test_code \"key\" , " +
       " nbs_test_desc_txt \"value\" FROM " +
                                             NEDSSConstants.SYSTEM_REFERENCE_TABLE +
                                             ".." +
                                             NEDSSConstants.LAB_TEST_TABLE;
     public static final String SUSP_TEST_SQL =
       "SELECT nbs_susp_test_cd \"key\", " +
       " nbs_susp_test_desc_txt \"value\"  FROM " +
       NEDSSConstants.SYSTEM_REFERENCE_TABLE + ".." +
       NEDSSConstants.SUSP_TEST_TABLE;
     public static final String JURISDICTION_SQL = "SELECT code  \"key\" ," +
       " code_short_desc_txt \"value\"  FROM " +
       NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..Jurisdiction_code ";
   public static final String BMDCODEQUERYSQL = "Select code \"key\"," +
       " code_short_desc_txt \"value\" from " +
       NEDSSConstants.SYSTEM_REFERENCE_TABLE +
       "..code_Value_General where upper(code_set_nm) = ? ";
    public static final String REPORTSUMMARYQUERYSQL = "Select code \"key\"," +
       " code_desc_txt  \"value\"  from " +
       NEDSSConstants.SYSTEM_REFERENCE_TABLE +
                 "..code_Value_General where code_set_nm = 'SUMMARY_RPT_SRC' " +
                 " AND super_code_set_nm = 'COUNTY_CCD' AND super_code  = ? ";
    public static final String RSUMMARYCONDITIONQUERYSQL =
       "Select condition_cd \"key\"," + " prog_area_cd \"value\" from " +
       NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..condition_code "; //where upper(reportable_summary_ind) = 'Y' ";
   public static final String RSUMMARYDATACODEQUERYSQL =
       "Select condition_cd \"key\"," + " condition_short_nm \"value\" from " +
       NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..condition_code "; //where upper(reportable_summary_ind) = 'Y' ";
    public static final String SECURITY_LOG_INSERT = "INSERT INTO Security_log (security_log_uid, event_type_cd, event_time, session_id, user_ip_addr, nedss_entry_id, first_nm, last_nm) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
   /* Add for LDFs */
   public static final String GETLDFDROPDOWN_SQL = "SELECT CODE \"key\"," +
       "CODE_SHORT_DESC_TXT \"value\" from " + NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..code_value_general " +
       "where CODE_SET_NM=?";

   public static final String GETLDFSRTDROPDOWN_SQL = "SELECT code_set_nm \"key\"," +
       "code_set_short_desc_txt \"value\" from " + NEDSSConstants.SYSTEM_REFERENCE_TABLE +"..Codeset_Group_Metadata  where ldf_picklist_ind_cd='Y' order by code_set_short_desc_txt" ;
   
    public static final String GETLDFPAGES = "SELECT page_uid \"pageUid\"," +
       " page_id \"pageId\", page_name \"pageName\", page_url \"pageUrl\", " +
       " business_object \"businessObject\", sort_order \"sortOrder\" " +
       " from state_defined_field_url_map order by sort_Order";

   public static final String GETLDFPAGEIDS_SQL =
	  "SELECT ldf_page_id \"ldfPageId\"," +
	  " business_object_nm \"businessObjNm\", condition_cd \"conditionCd\", " +
	  " ui_display \"uiDisplay\", indent_level_nbr \"indentLevelNbr\"," +
	  " parent_is_cd \"parentIsCd\", nbs_uid \"nbsUid\", status_cd \"statusCd\","+
	  " code_short_desc_txt  \"codeShortDescTxt\", display_row \"displayRow\"," +
	  " display_column \"displayColumn\" "+
	  " from "+  NEDSSConstants.SYSTEM_REFERENCE_TABLE +"..LDF_PAGE_SET where condition_cd is null or condition_cd not in(SELECT condition_cd FROM "  +
	  NEDSSConstants.SYSTEM_REFERENCE_TABLE +"..Condition_Code " +
	  " WHERE (condition_cd IN  (select distinct condition_cd from page_cond_mapping) or investigation_form_cd is null ) and port_req_ind_cd = 'F' " +
	  ") order by display_column,display_row";


  public static final String GETLDFPAGEIDSHOME_SQL =
	  "SELECT ldf_page_id \"ldfPageId\"," +
	  " business_object_nm \"businessObjNm\", condition_cd \"conditionCd\", " +
	  " ui_display \"uiDisplay\", indent_level_nbr \"indentLevelNbr\"," +
	  " parent_is_cd \"parentIsCd\", nbs_uid \"nbsUid\", status_cd \"statusCd\","+
	  " code_short_desc_txt  \"codeShortDescTxt\", display_row \"displayRow\"," +
	  " display_column \"displayColumn\" "+
	  " from "+  NEDSSConstants.SYSTEM_REFERENCE_TABLE +"..LDF_PAGE_SET where status_cd='A' and condition_cd is null or condition_cd not in(SELECT condition_cd FROM "  +
	  NEDSSConstants.SYSTEM_REFERENCE_TABLE +"..Condition_Code " +
	  " WHERE port_req_ind_cd = 'F' and condition_cd IN  (select distinct condition_cd from page_cond_mapping) or investigation_form_cd is null " +
	  ") order by display_column,display_row";

  
   public static final String OCCUPATIONCODEESQL = "SELECT code \"key\", " +
       " code_short_desc_txt \"value\" FROM " +
       NEDSSConstants.SYSTEM_REFERENCE_TABLE +
          "..OCCUPATION_CODE where indent_level_nbr=1";
 /* Race code and category. Done for dat migration */

   public static final String RACECODEANDCATEGORYSQL = "select code \"key\",  "+
       " parent_is_cd \"value\" "+
       " from "+
       NEDSSConstants.SYSTEM_REFERENCE_TABLE +
       "..race_code where status_cd= 'A' and  indent_level_nbr in  (1 ,2) order by parent_is_cd ";

/* Ethnicity code and group. Done for dat migration */
 
   public static final String ETHNICITYCODEANDGROUOPSQL = "select code \"value\" , " +
      " code_short_desc_txt \"key\" ," +
      " (select  code from " + NEDSSConstants.SYSTEM_REFERENCE_TABLE + ".code_value_general  " +
      " where code_set_nm = 'P_ETHN_GRP' and code_short_desc_txt like 'Hispanic%') \"altValue\" "+
      " from "+
      NEDSSConstants.SYSTEM_REFERENCE_TABLE +
      "..code_value_general where  code_set_nm in ('P_ETHN' , 'P_ETHN_GRP') ";

 
    public static final String REPORTSOURCESINCOUNTYSQl = "select code \"key\" , " +
     " code_short_desc_txt \"value\" " +
     " from " + NEDSSConstants.SYSTEM_REFERENCE_TABLE + ".code_value_general " +
     " where code_set_nm = 'SUMMARY_RPT_SRC' and super_code_set_nm = 'COUNTY_CCD' and " +
     " super_code=?";

    public static final String LAB_TEST_DESC_SQL =
    	   "SELECT lab_test_desc_txt FROM NBS_SRTE..LAB_TEST ";
    
    public static final String REPORT_FILTER_OPERATORS = 
    	"SELECT     f.filter_operator_code, f.filter_operator_desc " +
    	"FROM         Filter_Operator f, Data_Source_Operator d " +
    	"where f.filter_operator_uid = d.filter_operator_uid " +
    	"and D.column_type_code = ?";

    public static final String CODEQUERY_SQL_ORDERED = "Select code \"key\", code_short_desc_txt \"value\" from " +
    	NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..code_Value_General where upper(code_set_nm) = ? order by NBS_UID ";

   
     public static final String SELECT_SNOMED_PROGRAM_AREA_EXCLUSION_SQL = "SELECT snomed_cd \"snomedCd\", pa_derivation_exclude_cd \"paDerivationExcludeCd\" FROM  nbs_srte..Snomed_code ";
    public static final String SELECT_LOINC_PROGRAM_AREA_EXCLUSION_SQL = "SELECT loinc_cd \"loincCd\", pa_derivation_exclude_cd \"paDerivationExcludeCd\" FROM  NBS_SRTE..LOINC_code ";
    public static final String SELECT_LAB_TEST_PROGRAM_AREA_EXCLUSION_SQL = "SELECT lab_test_cd \"labTestCd\", laboratory_id \"laboratoryId\", pa_derivation_exclude_cd \"paDerivationExcludeCd\" FROM  NBS_SRTE..Lab_test ";
    public static final String SELECT_LAB_RESULT_PROGRAM_AREA_EXCLUSION_SQL = "SELECT lab_result_cd \"labResultCd\", laboratory_id \"laboratoryId\", pa_derivation_exclude_cd \"paDerivationExcludeCd\" FROM  NBS_SRTE..Lab_result ";
	public static final String GET_EXPORT_LIST = "Select export_Receiving_Facility_uid \"longKey\",RECEIVING_SYSTEM_SHORT_NM \"value\" from Export_Receiving_Facility where type_cd='"+NEDSSConstants.PHC_236+"'";
	public static final String CODE_DESC_SHORT_TXT_SQL ="SELECT DISTINCT code_short_desc_txt \"key\" FROM " + NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..Code_value_general WHERE code = ? and upper(code_set_nm) = ? ";
	public static final String CONTACTTRACINGENABLEINDSQL = "select condition_cd \"key\", " +
     " contact_tracing_enable_ind \"value\" from " +
     NEDSSConstants.
     SYSTEM_REFERENCE_TABLE +
     "..condition_code order by condition_cd";

	public static final String GETTemplate = "SELECT CAST(wa_template_uid as varchar) \"key\"," +
			 "template_nm \"value\" from wa_template where record_status_cd = 'ACTIVE' and template_type = 'Template'" ;

	public static final String PARTICIPATIONTYPESSQL =
			"SELECT act_class_cd \"actClassCd\", " + "subject_class_cd \"subjectClassCd\", " + " type_cd \"typeCd\", type_desc_txt \"typeDescTxt\", " + "question_identifier \"questionIdentifier\" FROM nbs_srte..Participation_type " +
					" where question_identifier is not null and record_status_cd = 'ACTIVE' order by type_cd ";
	
	public static final String COINFECTION_CONDITION_SQL = "SELECT condition_cd , coinfection_grp_cd from nbs_srte..condition_code where coinfection_grp_cd is not null" ;
	public static final String PLACE_LIST = "SELECT root_extension_txt , nm from place inner join entity_id on place.place_uid = entity_id.entity_uid and entity_id.type_cd='QEC' and root_extension_txt is not null and root_extension_txt!=''";

	public static final String PHIN_TO_NBS_CODE_SQL = "select distinct cvg.code_set_nm +'^'+ cvg.concept_code PHINConcepts, cvg.code  from wa_ui_metadata wum " 
					+ " inner join nbs_srte..codeset cs on wum.code_set_group_id = cs.code_set_group_id and upper(cs.class_cd)='CODE_VALUE_GENERAL' "
					+ " inner join nbs_srte..Code_value_general cvg on "
					+" cs.code_set_nm = cvg.code_set_nm" ;
	
	
	public static final String NULL_FLAVOR_CODED_VALUES = "Select code \"key\", code_short_desc_txt \"value\" from " +
			NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..code_Value_General where upper(code_set_nm) = ? and code_system_cd='2.16.840.1.113883.5.1008' ";

	public static final String AOE_LOINC_CODES = "select distinct loinc_cd \"key\", loinc_cd \"value\" from NBS_SRTE..LOINC_CODE where time_aspect = 'Pt' and system_cd='^Patient'";
}


