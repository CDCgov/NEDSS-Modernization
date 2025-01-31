package gov.cdc.nedss.systemservice.sqlscript;

import gov.cdc.nedss.util.NEDSSConstants;

public class DefinedFieldSQLQuery {

  public static final String INSERT_METADATA =
      "INSERT INTO State_defined_field_metadata " + "(ldf_uid, " + //1
      "active_ind, " + //2
      "admin_comment,  " + //3
      "business_object_nm, " + //4
      "category_type,    " + //5
      "cdc_national_id, " + //6
      "class_cd, " + //7
      "code_set_nm,  " + //8
      "condition_cd,  " + //9
      "condition_desc_txt,   " + //10
      "data_type,  " + //11
      "display_order_nbr, " + //12
      "field_size,   " + //13
      "label_txt,    " + //14
      "validation_txt, " + //15
      "validation_jscript_txt, " + //16
      "ldf_page_id, " + //17
      "required_ind, " + //18
      "add_time , record_status_time, record_status_cd"
      + ",deployment_cd,state_cd, "
      +
      //below 6 new attributes added to MetaDataTable to support CDFs and CustomSubForms (01/12/04)
      " CUSTOM_SUBFORM_METADATA_UID, HTML_TAG, IMPORT_VERSION_NBR, NND_IND, LDF_OID, VERSION_CTRL_NBR) "
      + " VALUES"
      + "(?,"
      +
      //1
      "?," + //2
      " ?," + //3
      " ?, " + //4
      " ?, " + //5
      " ?," + //6
      " ?, " + //7
      " ?, " + //8
      " ?," + //9
      " ?, " + //10
      "  ?, " + //11
      " ?," + //12
      " ?, " + //13
      " ?, " + //14
      " ?, " + //15
      " ?, " + //16
      " ?, " + //17
      " ?, " + //18
      " ?, ? , ?" //19,20,21,22
      + ",?,?" //21,22 Added new fields 11.25.03
      + ",?,?,?,?,?,?" + ")";

  public static final String UPDATE_METADATA =
      "UPDATE State_defined_field_metadata SET " + "active_ind = ?, " +
      //1
      "admin_comment = ?, " + //2
      "business_object_nm = ?, " + //3
      "category_type = ?,    " + //4
      "cdc_national_id =  ?, " + //5
      "class_cd = ?,  " + //6
      "code_set_nm = ?,   " + //7
      "condition_cd = ?,   " + //8
      "condition_desc_txt = ?,   " + //9
      "data_type = ?,   " + //10
      "display_order_nbr = ?,  " + //11
      "field_size = ?,    " + //12
      "label_txt = ?,    " + //13
      "validation_txt = ?, " + //14
      "validation_jscript_txt = ?, " + //15
      "ldf_page_id = ?, " + //16
      "required_ind = ? ," + //17
      " record_status_time  = ?, " + //18
      " record_status_cd = ? " + //19
      " ,deployment_cd = ? " + //20 - Added new fields 11.25.03
      " ,state_cd = ?, " + //21 - Added new fields 11.25.03
      //below 6 new attributes added to MetaDataTable to support CDFs and CustomSubForms (01/12/04)
      " CUSTOM_SUBFORM_METADATA_UID = ?,"
      + " HTML_TAG = ?,"
      + " IMPORT_VERSION_NBR = ?, "
      + " NND_IND =?, "
      + " LDF_OID = ?, "
      + " VERSION_CTRL_NBR =? "
      + " WHERE ldf_uid = ? and VERSION_CTRL_NBR = ? ";
  // added VERSION_CTRL_NBR in where clasuse to check data concurrency (xz, 1/16/04)


  public static final String DELETE_METADATA =
      "update State_defined_field_metadata "
      + " set active_ind = 'N', record_status_cd = 'LDF_UPDATE' WHERE (ldf_uid = ?)";

  public static final String DELETE_BY_IMPORT_VERSION_NBR =
     "delete from State_defined_field_metadata WHERE (import_version_nbr = ?)";

 //select queries

  public static final String SELECT_ALL_METADATA =
      "select ldf_uid \"ldfUid\", "
      + " active_ind \"activeInd\",  "
      + "admin_comment   \"adminComment\", "
      + "add_time \"AddTime\", "
      + "business_object_nm     \"businessObjNm\",  "
      + "category_type   \"categoryType\",   "
      + "cdc_national_id  \"cdcNationalId\",  "
      + "class_cd    \"classCd\",    "
      + "code_set_nm   \"codeSetNm\",   "
      + "condition_cd   \"conditionCd\",  "
      + "condition_desc_txt     \"conditionDescTxt\",   "
      + "data_type   \"dataType\",  "
      + "display_order_nbr   \"displayOrderNbr\",   "
      + "field_size   \"fieldSize\", "
      + "label_txt \"labelTxt\", "
      + "validation_txt  \"validationTxt\", "
      + "validation_jscript_txt   \"validationJscriptTxt\", "
      + "required_ind \"requiredInd\", "
      + "ldf_page_id \"ldfPageId\", "
      + "record_status_cd \"recordStatusCd\", "
      + "record_status_time \"recordStatusTime\" "
      + ",deployment_cd \"deploymentCd\" " //Added new fields 11.25.03
      + ",state_cd \"stateCd\", " //Added new fields 11.25.03
      //below 6 new attributes added to MetaDataTable to support CDFs and CustomSubForms (01/12/04)
      + " CUSTOM_SUBFORM_METADATA_UID \"customSubformMetadataUid\","
      + " HTML_TAG \"htmlTag\", "
      + " IMPORT_VERSION_NBR \"importVersionNbr\", "
      + " NND_IND \"nndInd\", "
      + " LDF_OID \"ldfOid\", "
      + " VERSION_CTRL_NBR \"versionCtrlNbr\" "
      + " from state_defined_field_metadata";

  
  public static final String SELECT_ALL_LDF_METADATA_NBS_QUESTION_SQL = 
	  "SELECT DISTINCT m.nbs_ui_metadata_uid \"ldfUid\", "  
	  + "m.record_status_cd AS \"activeInd\", "
	  + "m.admin_comment AS \"adminComment\", "
	  + "m.add_time AS \"addTime\", " 
	  + "m.investigation_form_cd AS \"businessObjNm\", " 
	  + "NULL AS \"categoryType\", "
	  + "NULL AS \"cdcNationalId\", "
	  + "'STATE' AS \"classCd\", "
	  + "cs.code_set_nm AS \"codeSetNm\", "
	  + "cc.condition_cd AS \"conditionCd\", " 
	  + "cc.condition_desc_txt AS \"conditionDescTxt\", "
	  + "q.data_type AS \"dataType\", " 
	  + "m.ldf_position AS \"displayOrderNbr\", "
	  + "m.max_length AS \"fieldSize\", "
	  + "m.question_label AS \"labelTxt\", " 
	  + "NULL AS \"validationTxt\", "
	  + "NULL AS \"validationJscriptTxt\", "
	  + "m.required_ind AS \"requiredInd\", " 
	  + "m.order_nbr AS \"ldfPageId\", "
	  + "m.ldf_status_cd AS \"recordStatusCd\", "
	  + "m.ldf_status_time AS \"recordStatusTime\", "
	  + "NULL AS \"deploymentCd\", " 
	  + "NULL AS \"stateCd\", "
	  + "NULL AS \"customSubformMetadataUid\", "
	  + "NULL AS \"htmlTag\", "
	  + "NULL AS \"importVersionNbr\", " 
	  + "NULL AS \"nndInd\", "
	  + "NULL AS \"ldfOid\", "
	  + "m.version_ctrl_nbr AS \"versionCtrlNbr\" " 
	  + "FROM NBS_UI_Metadata AS m INNER JOIN "
	  + "NBS_Question AS q ON m.nbs_question_uid = q.nbs_question_uid "
	  + "LEFT OUTER JOIN " 
	  + NEDSSConstants.SYSTEM_REFERENCE_TABLE 
	  + "..Codeset AS cs ON q.code_set_group_id = cs.code_set_group_id "
	  + "INNER JOIN "
	  + NEDSSConstants.SYSTEM_REFERENCE_TABLE
	  + "..Condition_code AS cc ON m.investigation_form_cd = cc.investigation_form_cd "
	  + "WHERE (m.ldf_status_cd IS NOT NULL)";	  
	  
   
  public static final String SELECT_METADATA_BY_PAGEID_CDF =
      "select ldf_uid \"ldfUid\", "
      + " active_ind \"activeInd\",  "
      + "admin_comment   \"adminComment\", "
      + "business_object_nm     \"businessObjNm\",  "
      + "category_type   \"categoryType\",   "
      + "cdc_national_id  \"cdcNationalId\",  "
      + "class_cd    \"classCd\",    "
      + "code_set_nm   \"codeSetNm\",   "
      + "condition_cd   \"conditionCd\",  "
      + "condition_desc_txt     \"conditionDescTxt\",   "
      + "data_type   \"dataType\",  "
      + "display_order_nbr   \"displayOrderNbr\",   "
      + "field_size   \"fieldSize\", "
      + "label_txt \"labelTxt\",  "
      + "validation_txt   \"validationTxt\", "
      + "validation_jscript_txt \"validationJscriptTxt\", "
      + "required_ind \"requiredInd\", "
      + "ldf_page_id \"ldfPageId\" "
      + ",deployment_cd \"deploymentCd\" " //Added new fields 11.25.03
      + ",state_cd \"stateCd\", " //Added new fields 11.25.03
      //below 6 new attributes added to MetaDataTable to support CDFs and CustomSubForms (01/12/04)
      + " CUSTOM_SUBFORM_METADATA_UID \"customSubformMetadataUid\","
      + " HTML_TAG \"htmlTag\", "
      + " IMPORT_VERSION_NBR \"importVersionNbr\", "
      + " NND_IND \"nndInd\", "
      + " LDF_OID \"ldfOid\", "
      + " VERSION_CTRL_NBR \"versionCtrlNbr\" "
	  + " from state_defined_field_metadata sdmetadata, "
	  + " (select ldf_oid \"tmpoid\", max( IMPORT_VERSION_NBR) \"tmpversion\" "
	  + " from state_defined_field_metadata "
	  +	" where ldf_page_id = ? "
	  + " and ldf_oid is not null and CUSTOM_SUBFORM_METADATA_UID IS NULL "
	  + " group by ldf_oid)oid_version "
	  + " where sdmetadata.ldf_oid=oid_version.\"tmpoid\" "
	  + "and sdmetadata.import_version_nbr=oid_version.\"tmpversion\" "
	  + "and sdmetadata.active_ind = 'Y' "
      + "  order by display_order_nbr";

  public static final String SELECT_METADATA_BY_PAGEID =
     "select ldf_uid \"ldfUid\", "
     + " active_ind \"activeInd\",  "
     + "admin_comment   \"adminComment\", "
     + "business_object_nm     \"businessObjNm\",  "
     + "category_type   \"categoryType\",   "
     + "cdc_national_id  \"cdcNationalId\",  "
     + "class_cd    \"classCd\",    "
     + "code_set_nm   \"codeSetNm\",   "
     + "condition_cd   \"conditionCd\",  "
     + "condition_desc_txt     \"conditionDescTxt\",   "
     + "data_type   \"dataType\",  "
     + "display_order_nbr   \"displayOrderNbr\",   "
     + "field_size   \"fieldSize\", "
     + "label_txt \"labelTxt\",  "
     + "validation_txt   \"validationTxt\", "
     + "validation_jscript_txt \"validationJscriptTxt\", "
     + "required_ind \"requiredInd\", "
     + "ldf_page_id \"ldfPageId\" "
     + ",deployment_cd \"deploymentCd\" " //Added new fields 11.25.03
     + ",state_cd \"stateCd\", " //Added new fields 11.25.03
     //below 6 new attributes added to MetaDataTable to support CDFs and CustomSubForms (01/12/04)
     + " CUSTOM_SUBFORM_METADATA_UID \"customSubformMetadataUid\","
     + " HTML_TAG \"htmlTag\", "
     + " IMPORT_VERSION_NBR \"importVersionNbr\", "
     + " NND_IND \"nndInd\", "
     + " LDF_OID \"ldfOid\", "
     + " VERSION_CTRL_NBR \"versionCtrlNbr\" "
     + "from state_defined_field_metadata "
     + "WHERE(ldf_page_id = ? and active_ind = 'Y' and CUSTOM_SUBFORM_METADATA_UID IS NULL "
     + " and ldf_oid is null) "
     + "order by display_order_nbr";


/* This query is commented out since we need to show a combined count of below 3 queries !!
  public static final String SELECT_METADATA_COUNT_BY_PAGEID =
      " "
      + "select count(*) from state_defined_field_metadata "
      + "where ldf_page_id = ? and active_ind = 'Y'";
*/
  public static final String SELECT_LDF_DMDF_METADATA_COUNT_BY_PAGEID =

      "select count(*) from state_defined_field_metadata " +
      "where ldf_page_id = ? and active_ind = 'Y' " +
      "and ldf_oid is null and custom_subform_metadata_uid is null ";

  public static final String SELECT_CDF_METADATA_COUNT_BY_PAGEID =

      "select count(distinct ldf_oid) "
	  + " from state_defined_field_metadata sdmetadata, "
	  + " (select ldf_oid \"tmpoid\", max( IMPORT_VERSION_NBR) \"tmpversion\" "
	  + " from state_defined_field_metadata "
	  +	" where ldf_page_id = ? "
	  + " and ldf_oid is not null and CUSTOM_SUBFORM_METADATA_UID IS NULL "
	  + " group by ldf_oid)oid_version "
	  + " where sdmetadata.ldf_oid=oid_version.\"tmpoid\" "
	  + "and sdmetadata.import_version_nbr=oid_version.\"tmpversion\" "
	  + "and sdmetadata.active_ind = 'Y' ";

  public static final String SELECT_SUBFORM_CDF_METADATA_COUNT_BY_PAGEID =

      "select count(distinct ldf_oid) from state_defined_field_metadata " +
      "where ldf_page_id = ? and active_ind = 'Y' and " +
      "custom_subform_metadata_uid in " +
      "(select custom_subform_metadata_uid from custom_subform_metadata sfmetadata, " +
      "(select subform_oid \"tmpoid\", max( IMPORT_VERSION_NBR) " +
      "\"tmpversion\" from custom_subform_metadata where page_set_id = ? " +
      "group by subform_oid)oid_version " +
      "where sfmetadata.subform_oid=oid_version.\"tmpoid\" " +
      "and sfmetadata.status_cd = 'A' " +
      "and sfmetadata.import_version_nbr=oid_version.\"tmpversion\")";


  public static final String SELECT_LDF_PAGE_SET_SQL =
      " "
      + "select ldf_page_id \"ldfPageId\" , "
      + "business_object_nm  \"businessObjectNm\" ,"
      + "ui_display \"uiDisplay\" ,"
      + "condition_cd \"conditionCd\" , "
      + " ui_display \"uiDisplay\" , "
      + "indent_level_nbr \"indentLevelNbr\" , "
      + "parent_is_cd \"parentIsCd\" , "
      + "code_set_nm \"codeSetNm\" , "
      + "seq_num \"seqNum\" , "
      + "code_version \"codeVersion\" , "
      + "nbs_uid \"nbsUid\" , "
      + "effective_from_time \"effectiveFromTime\" , "
      + "effective_to_time \"effectiveToTime\" , "
      + "status_cd \"statusCd\"  "
      + "from "
      + NEDSSConstants.SYSTEM_REFERENCE_TABLE
      + "..ldf_page_set "
      + "where ldf_page_id = ? ";

  public static final String SELECT_METADATA_BY_CLASS_CD_AND_ACTIVE_IND =
      "select ldf_uid \"ldfUid\", "
      + " active_ind \"activeInd\",  "
      + "admin_comment   \"adminComment\", "
      + "business_object_nm     \"businessObjNm\",  "
      + "category_type   \"categoryType\",   "
      + "cdc_national_id  \"cdcNationalId\",  "
      + "class_cd    \"classCd\",    "
      + "code_set_nm   \"codeSetNm\",   "
      + "condition_cd   \"conditionCd\",  "
      + "condition_desc_txt     \"conditionDescTxt\",   "
      + "data_type   \"dataType\",  "
      + "display_order_nbr   \"displayOrderNbr\",   "
      + "field_size   \"fieldSize\", "
      + "label_txt \"labelTxt\",  "
      + "validation_txt   \"validationTxt\", "
      + "validation_jscript_txt \"validationJscriptTxt\", "
      + "ldf_page_id \"ldfPageId\", "
      + "required_ind \"requiredInd\" "
      + ",deployment_cd \"deploymentCd\" " //Added new fields 11.25.03
      + ",state_cd \"stateCd\", " //Added new fields 11.25.03
      //below 6 new attributes added to MetaDataTable to support CDFs and CustomSubForms (01/12/04)
      + " CUSTOM_SUBFORM_METADATA_UID \"customSubformMetadataUid\","
      + " HTML_TAG \"htmlTag\", "
      + " IMPORT_VERSION_NBR \"importVersionNbr\", "
      + " NND_IND \"nndInd\", "
      + " LDF_OID \"ldfOid\", "
      + " VERSION_CTRL_NBR \"versionCtrlNbr\" "
      + "from state_defined_field_metadata "
      + "WHERE class_cd = ? "
      + " and active_ind = ? "
      + " order by display_order_nbr asc";

  public static final String SELECT_METADATA_BY_CLASS_CD =
      "select ldf_uid \"ldfUid\", "
      + " active_ind \"activeInd\",  "
      + "admin_comment   \"adminComment\", "
      + "add_time \"addTime\", "
      + "business_object_nm     \"businessObjNm\",  "
      + "category_type   \"categoryType\",   "
      + "cdc_national_id  \"cdcNationalId\",  "
      + "class_cd    \"classCd\",    "
      + "code_set_nm   \"codeSetNm\",   "
      + "condition_cd   \"conditionCd\",  "
      + "condition_desc_txt     \"conditionDescTxt\",   "
      + "data_type   \"dataType\",  "
      + "display_order_nbr   \"displayOrderNbr\",   "
      + "field_size   \"fieldSize\", "
      + "label_txt \"labelTxt\", "
      + "validation_txt  \"validationTxt\", "
      + "validation_jscript_txt   \"validationJscriptTxt\", "
      + "required_ind \"requiredInd\", "
      + "ldf_page_id \"ldfPageId\", "
      + "record_status_cd \"recordStatusCd\", "
      + "record_status_time \"recordStatusTime\" "
      + ",deployment_cd \"deploymentCd\" " //Added new fields 11.25.03
      + ",state_cd \"stateCd\", " //Added new fields 11.25.03
      //below 6 new attributes added to MetaDataTable to support CDFs and CustomSubForms (01/12/04)
      + " CUSTOM_SUBFORM_METADATA_UID \"customSubformMetadataUid\","
      + " HTML_TAG \"htmlTag\", "
      + " IMPORT_VERSION_NBR \"importVersionNbr\", "
      + " NND_IND \"nndInd\", "
      + " LDF_OID \"ldfOid\", "
      + " VERSION_CTRL_NBR \"versionCtrlNbr\" "
      + "from state_defined_field_metadata "
      + "WHERE class_cd = ? "
      + " order by display_order_nbr asc";

  private static final String SELECT_LDF_DFDT_METADATA_SUBSQL =
      "select ldf_uid \"ldfUid\", "
          + " active_ind \"activeInd\", "
          + "admin_comment   \"adminComment\", "
          + "business_object_nm     \"businessObjNm\", "
          + "category_type   \"categoryType\", "
          + "cdc_national_id  \"cdcNationalId\", "
          + "class_cd    \"classCd\", "
          + "code_set_nm   \"codeSetNm\", "
          + "condition_cd   \"conditionCd\", "
          + "condition_desc_txt     \"conditionDescTxt\", "
          + "data_type   \"dataType\", "
          + "display_order_nbr   \"displayOrderNbr\", "
          + "field_size   \"fieldSize\", "
          + "label_txt \"labelTxt\", "
          + "validation_txt  \"validationTxt\", "
          + "validation_jscript_txt   \"validationJscriptTxt\", "
          + "required_ind \"requiredInd\", "
          + "ldf_page_id \"ldfPageId\", "
          + "deployment_cd \"deploymentCd\", "
          + "state_cd \"stateCd\", "
          + "custom_subform_metadata_uid \"customSubformMetadataUid\", "
          + "html_tag \"htmlTag\", "
          + "import_version_nbr \"importVersionNbr\", "
          + "nnd_ind \"nndInd\", "
          + "ldf_oid \"ldfOid\", "
          + "version_ctrl_nbr \"versionCtrlNbr\" "
          + "from state_defined_field_metadata ";


  public static final String SELECT_LDF_AND_DMDF_METADATA_BY_BO = SELECT_LDF_DFDT_METADATA_SUBSQL

      + "where business_object_nm = ? "
      + "and active_ind = 'Y' "
      + "and condition_cd is null "
      + "and ldf_oid is null and custom_subform_metadata_uid is null "
      + "order by display_order_nbr";

  public static final String SELECT_LDF_AND_DMDF_METADATA_BY_BO_AND_CONDITION = SELECT_LDF_DFDT_METADATA_SUBSQL

      + "where business_object_nm = ? "
      + "and condition_cd = ? "
      + "and active_ind = 'Y' "
      + "and ldf_oid is null and custom_subform_metadata_uid is null "
      + "order by display_order_nbr";

  public static final String SELECT_LDF_AND_DMDF_METADATA_BY_BO_AND_CONDITION_INCLUSIVE = SELECT_LDF_DFDT_METADATA_SUBSQL

      + "where business_object_nm = ? "
      + " and ( Condition_cd = ? OR condition_cd is null )"
      + "and active_ind = 'Y' "
      + "and ldf_oid is null and custom_subform_metadata_uid is null "
      + "order by display_order_nbr";



  private static final String SELECT_CDF_METADATA_SUBSQL =
      "select sd1.ldf_uid \"ldfUid\", "
           + "sd1.active_ind \"activeInd\", "
           + "sd1.admin_comment   \"adminComment\", "
           + "sd1.business_object_nm     \"businessObjNm\", "
           + "sd1.category_type   \"categoryType\", "
           + "sd1.cdc_national_id  \"cdcNationalId\", "
           + "sd1.class_cd    \"classCd\", "
           + "sd1.code_set_nm   \"codeSetNm\", "
           + "sd1.condition_cd   \"conditionCd\", "
           + "sd1.condition_desc_txt     \"conditionDescTxt\", "
           + "sd1.data_type   \"dataType\", "
           + "sd1.display_order_nbr   \"displayOrderNbr\", "
           + "sd1.field_size   \"fieldSize\", "
           + "sd1.label_txt \"labelTxt\", "
           + "sd1.validation_txt  \"validationTxt\", "
           + "sd1.validation_jscript_txt   \"validationJscriptTxt\", "
           + "sd1.required_ind \"requiredInd\", "
           + "sd1.ldf_page_id \"ldfPageId\", "
           + "sd1.deployment_cd \"deploymentCd\", "
           + "sd1.state_cd \"stateCd\", "
           + "sd1.custom_subform_metadata_uid \"customSubformMetadataUid\", "
           + "sd1.html_tag \"htmlTag\", "
           + "sd1.import_version_nbr \"importVersionNbr\", "
           + "sd1.nnd_ind \"nndInd\", "
           + "sd1.ldf_oid \"ldfOid\", "
           + "sd1.version_ctrl_nbr \"versionCtrlNbr\" "
           + "from state_defined_field_metadata sd1, "
           + "(select  sd2.ldf_oid , max( sd2.IMPORT_VERSION_NBR) IMPORT_VERSION_NBR "
           + "from state_defined_field_metadata sd2 "
           + "where sd2.business_object_nm= ? "
           + "group by  sd2.ldf_oid) sd3 ";


  public static final String SELECT_CDF_METADATA_BY_BO = SELECT_CDF_METADATA_SUBSQL

      + "where sd1.business_object_nm = ? "
      + "and condition_cd is null "
      + "and active_ind = 'Y' "
      + "and  (sd1.ldf_oid = sd3.ldf_oid "
      + "and sd1.import_version_nbr  = sd3.import_version_nbr ) "
      + "and custom_subform_metadata_uid is null";

  public static final String SELECT_CDF_METADATA_BY_BO_AND_CONDITION = SELECT_CDF_METADATA_SUBSQL

      + "where sd1.business_object_nm = ? "
      + "and condition_cd = ? "
      + "and active_ind = 'Y' "
      + "and  (sd1.ldf_oid = sd3.ldf_oid "
      + "and sd1.import_version_nbr  = sd3.import_version_nbr ) "
      + "and custom_subform_metadata_uid is null";

  public static final String SELECT_CDF_METADATA_BY_BO_AND_CONDITION_INCLUSIVE = SELECT_CDF_METADATA_SUBSQL

      + "where sd1.business_object_nm = ? "
      + " and ( Condition_cd = ? OR condition_cd is null )"
      + "and active_ind = 'Y' "
      + "and  (sd1.ldf_oid = sd3.ldf_oid "
      + "and sd1.import_version_nbr  = sd3.import_version_nbr ) "
      + "and custom_subform_metadata_uid is null";

  public static final String SELECT_METADATA_BY_SUBFORM_UID = SELECT_ALL_METADATA + " where custom_subform_metadata_uid = ?";

  public static final String SELECT_METADATA_BY_LDF_OID = SELECT_ALL_METADATA + " where LDF_OID = ? and import_Version_Nbr = ? ";

  public static final String SELECT_METADATA_BY_LDF_UID = SELECT_ALL_METADATA + " where ldf_uid = ? ";
}