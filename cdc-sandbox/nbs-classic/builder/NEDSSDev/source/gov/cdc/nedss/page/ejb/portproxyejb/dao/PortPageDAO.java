package gov.cdc.nedss.page.ejb.portproxyejb.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.page.ejb.portproxyejb.dt.AnswerMappingDT;
import gov.cdc.nedss.page.ejb.portproxyejb.dt.ManagePageDT;
import gov.cdc.nedss.page.ejb.portproxyejb.dt.MappedFromToQuestionFieldsDT;
import gov.cdc.nedss.pagemanagement.wa.dao.PageManagementDAOImpl;
import gov.cdc.nedss.pagemanagement.wa.dt.ConditionDT;
import gov.cdc.nedss.pagemanagement.wa.dt.PageCondMappingDT;
import gov.cdc.nedss.pagemanagement.wa.dt.WaTemplateDT;
import gov.cdc.nedss.pagemanagement.wa.dt.WaUiMetadataDT;
import gov.cdc.nedss.systemservice.ejb.pamconversionejb.dt.NBSConversionConditionDT;
import gov.cdc.nedss.systemservice.ejb.pamconversionejb.dt.NBSConversionMappingDT;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.portpage.util.PortPageUtil;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

/**
 * @author PatelDh
 *
 */
public class PortPageDAO extends DAOBase{
	static final LogUtils logger = new LogUtils(PortPageDAO.class.getName());
	private static PropertyUtil propertyUtil = PropertyUtil.getInstance();
	
	public static final String SELECT_FROM_FIELD_LIST = "SELECT question_identifier \"fromQuestionId\", question_nm \"fromLabel\", frompage.code_set_group_id \"codeSetGroupId\", codeset.code_set_nm \"fromCodeSetNm\",data_location \"fromDbLocation\", data_type \"fromDataType\", wa_ui_metadata_uid \"waUiMetadataUid\", record_status_cd \"recordStatusCd\", 'N' \"autoMapped\", question_group_seq_nbr \"questionGroupSeqNbr\", unit_type_cd \"unitTypeCd\", unit_value  \"unitValue\", frompage.nbs_ui_component_uid \"fromNbsUiComponentUid\"  "+
									"FROM WA_UI_metadata frompage left outer join  NBS_SRTE..Codeset codeset on frompage.code_set_group_id = codeset.code_set_group_id "+
									" WHERE (frompage.wa_template_uid = ?) AND (question_identifier NOT IN (SELECT question_identifier FROM WA_UI_metadata WHERE (wa_template_uid = ?))) AND (question_identifier NOT IN ('NBS290', 'NBS243')) AND (data_location like 'NBS_CASE_ANSWER.%' OR (data_location LIKE '%_UID' and part_type_cd IS NOT NULL)) and order_nbr <> 0 "+
									"UNION "+
									"select  frompage.question_identifier \"fromQuestionId\", frompage.question_nm \"fromLabel\", frompage.code_set_group_id \"codeSetGroupId\", codeset.code_set_nm \"fromCodeSetNm\",frompage.data_location \"fromDbLocation\", frompage.data_type \"fromDataType\", frompage.wa_ui_metadata_uid \"waUiMetadataUid\", frompage.record_status_cd \"recordStatusCd\", 'N' \"autoMapped\", frompage.question_group_seq_nbr \"questionGroupSeqNbr\", frompage.unit_type_cd \"unitTypeCd\", frompage.unit_value  \"unitValue\", frompage.nbs_ui_component_uid \"fromNbsUiComponentUid\" "+
									"FROM WA_UI_metadata frompage left outer join  NBS_SRTE..Codeset codeset on frompage.code_set_group_id = codeset.code_set_group_id "+
									"inner join WA_UI_metadata topage on frompage.wa_template_uid= ? and topage.wa_template_uid= ?  "+
									"and frompage.question_identifier=topage.question_identifier and "+
									"(frompage.data_type<>topage.data_type or frompage.nbs_ui_component_uid<>topage.nbs_ui_component_uid or frompage.code_set_group_id<>topage.code_set_group_id or (frompage.question_group_seq_nbr is null AND topage.question_group_seq_nbr is not null) or (frompage.question_group_seq_nbr is not null AND topage.question_group_seq_nbr is null)) "+
									"AND (frompage.data_location like 'NBS_CASE_ANSWER.%' OR (frompage.data_location LIKE '%_UID' and frompage.part_type_cd IS NOT NULL)) and frompage.order_nbr <> 0 AND (frompage.question_identifier NOT IN ('NBS290', 'NBS243'))"+
									" UNION "+
									"SELECT question_identifier \"fromQuestionId\", question_nm \"fromLabel\", frompage.code_set_group_id \"codeSetGroupId\", codeset.code_set_nm \"fromCodeSetNm\",data_location \"fromDbLocation\", data_type \"fromDataType\", wa_ui_metadata_uid \"waUiMetadataUid\", record_status_cd \"recordStatusCd\", 'Y' \"autoMapped\", question_group_seq_nbr \"questionGroupSeqNbr\", unit_type_cd \"unitTypeCd\", unit_value  \"unitValue\", frompage.nbs_ui_component_uid \"fromNbsUiComponentUid\" "+
									"FROM WA_UI_metadata frompage left outer join  NBS_SRTE..Codeset codeset on frompage.code_set_group_id = codeset.code_set_group_id "+ 
									"WHERE wa_template_uid = ? and data_location like 'NBS_CASE_ANSWER.%' and (question_identifier NOT IN ('NBS290', 'NBS243')) ";
	
	public static final String SELECT_TO_QUESTION_LIST = "SELECT question_identifier \"fromQuestionId\", question_nm \"fromLabel\", topage.code_set_group_id \"codeSetGroupId\", codeset.code_set_nm \"fromCodeSetNm\",data_location \"fromDbLocation\", data_type \"fromDataType\", wa_ui_metadata_uid \"waUiMetadataUid\", record_status_cd \"recordStatusCd\", question_group_seq_nbr \"questionGroupSeqNbr\", nbs_ui_component_uid \"toNbsUiComponentUid\", unit_type_cd \"unitTypeCd\", unit_value  \"unitValue\", other_value_ind_cd  \"otherInd\" "+
									"FROM WA_UI_metadata topage left outer join  NBS_SRTE..Codeset codeset on topage.code_set_group_id = codeset.code_set_group_id "+ 
									"WHERE (wa_template_uid = ?) AND (question_identifier NOT IN  (SELECT question_identifier FROM WA_UI_metadata WHERE (wa_template_uid = ?))) AND (data_location like 'NBS_CASE_ANSWER.%' OR data_location like '%_UID') AND (question_identifier NOT IN ('NBS290', 'NBS243')) "+
									"UNION "+
									"SELECT question_identifier \"fromQuestionId\", question_nm \"fromLabel\", topage.code_set_group_id \"codeSetGroupId\", codeset.code_set_nm \"fromCodeSetNm\",data_location \"fromDbLocation\", data_type \"fromDataType\", wa_ui_metadata_uid \"waUiMetadataUid\", record_status_cd \"recordStatusCd\", question_group_seq_nbr \"questionGroupSeqNbr\", nbs_ui_component_uid \"toNbsUiComponentUid\", unit_type_cd \"unitTypeCd\", unit_value  \"unitValue\", other_value_ind_cd  \"otherInd\" "+
									"FROM WA_UI_metadata topage left outer join  NBS_SRTE..Codeset codeset on topage.code_set_group_id = codeset.code_set_group_id "+
									"WHERE wa_template_uid = ? and data_location like 'NBS_CASE_ANSWER.%' AND (question_identifier NOT IN ('NBS290', 'NBS243')) ";
	
	public static final String SELECT_ANSWERS_FOR_QUESTION = "select question_identifier \"questionIdentifier\", question_nm \"questionLabel\", uiMetadata.code_set_group_id \"codeSetGroupId\",cvg.code_set_nm \"codeSetNm\",cvg.code \"code\", cvg.code_short_desc_txt \"codeDescTxt\", 'N' \"autoMapped\" from WA_UI_metadata uiMetadata, NBS_SRTE..Codeset codeset, NBS_SRTE..Code_value_general cvg "+
									"where uiMetadata.code_set_group_id=codeset.code_set_group_id and codeset.code_set_nm=cvg.code_set_nm and uiMetadata.wa_template_uid=? and uiMetadata.code_set_group_id is not null and "+
									"question_identifier in (REPLACE_WITH_QUESTIONS) "+
									" UNION "+
									"select question_identifier \"questionIdentifier\", question_nm \"questionLabel\", uiMetadata.code_set_group_id \"codeSetGroupId\",cvg.code_set_nm \"codeSetNm\",cvg.code \"code\", cvg.code_short_desc_txt \"codeDescTxt\", 'Y' \"autoMapped\" from WA_UI_metadata uiMetadata, NBS_SRTE..Codeset codeset, NBS_SRTE..Code_value_general cvg "+
									"where uiMetadata.code_set_group_id=codeset.code_set_group_id and codeset.code_set_nm=cvg.code_set_nm and uiMetadata.wa_template_uid=? and uiMetadata.code_set_group_id is not null and "+
									"data_location like 'NBS_CASE_ANSWER.%' and question_identifier not in (REPLACE_WITH_NOT_IN_QUESTIONS) order by 1";
	
	
	private static final String INSERT_NBS_CONVERSION_MAPPING = "INSERT INTO NBS_conversion_mapping "+
									"(nbs_conversion_mapping_uid,from_code, from_code_set_nm, from_data_type,from_question_id,condition_cd_group_id,to_code,to_code_set_nm,to_data_type,to_question_id,translation_required_ind,from_db_location,to_db_location,from_label,to_label,block_id_nbr, unit_type_cd, unit_value, answer_group_seq_nbr, conversion_type) "+
									"VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
	private static final String INSERT_NBS_CONVERSION_CONDITION = "INSERT INTO NBS_conversion_condition (nbs_conversion_condition_uid ,condition_cd ,condition_cd_group_id,NBS_Conversion_Page_Mgmt_uid,status_cd,add_time,last_chg_time) "+
									"VALUES(?,?,?,?,?,?,?)";
	
	private static final String INSERT_WA_CONVERSION_MAPPING = "INSERT INTO WA_Conversion_Mapping "+
									"(NBS_Conversion_Page_Mgmt_uid, from_question_id, from_answer,to_question_id,to_answer,to_code_set_group_id, to_data_type, to_nbs_ui_component_uid, block_id_nbr, mapping_status,question_mapped,answer_mapped,answer_group_seq_nbr, conversion_type) "+
									"VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
	/*private static final String SELECT_WA_CONVERSION_MAPPING = "select WA_Conversion_Mapping_uid \"waPortMappingUid\", from_question_id \"fromQuestionId\", from_label \"fromLabel\", from_answer \"fromCode\", from_code_set_nm \"fromCodeSetNm\", from_data_type \"fromDataType\", from_db_location \"fromDbLocation\", to_question_id \"toQuestionId\", to_label \"toLabel\", to_answer \"toCode\", to_code_set_nm \"toCodeSetNm\", to_data_type \"toDataType\", to_db_location \"toDbLocation\", condition_cd_group_id \"conditionCdGroupId\", translation_required_ind \"translationRequiredInd\", block_id_nbr \"blockIdNbr\", mapping_status \"statusCode\", question_mapped \"questionMappedCode\", answer_mapped \"answerMapped\" from WA_Conversion_Mapping "+
									"where condition_cd_group_id=?";*/
	
	private static final String SELECT_NBS_CASE_ANSWER_FOR_QUESTION = "SELECT DISTINCT NBS_case_answer.answer_txt FROM NBS_question INNER JOIN NBS_ui_metadata ON NBS_question.nbs_question_uid = NBS_ui_metadata.nbs_question_uid INNER JOIN "+
									"NBS_case_answer ON NBS_question.nbs_question_uid = NBS_case_answer.nbs_question_uid INNER JOIN "+ 
									"WA_template ON NBS_ui_metadata.investigation_form_cd = WA_template.form_cd INNER JOIN "+
									"Page_cond_mapping ON WA_template.wa_template_uid = Page_cond_mapping.wa_template_uid INNER JOIN "+
									"Public_health_case ON Page_cond_mapping.condition_cd = Public_health_case.cd AND "+ 
									"NBS_case_answer.act_uid = Public_health_case.public_health_case_uid "+
									"WHERE (NBS_ui_metadata.group_nm = 'GROUP_INV' OR NBS_ui_metadata.ldf_page_id IS NOT NULL) AND (WA_template.wa_template_uid = ?) AND (NBS_question.question_identifier = ?) "+
									"GROUP BY NBS_case_answer.answer_txt, NBS_question.question_identifier, NBS_ui_metadata.investigation_form_cd, WA_template.wa_template_uid, NBS_ui_metadata.nbs_question_uid, NBS_ui_metadata.group_nm";
			
			
	private static final String SELECT_NBS_CONVERSION_PAGE_MANAGEMENT = "SELECT nbsConversionPageMgmt.NBS_Conversion_Page_Mgmt_uid \"nbsConversionPageMgmtUid\", nbsConversionPageMgmt.map_name \"mapName\", "+
									"toPage.bus_obj_type \"eventTypeCd\", nbsConversionPageMgmt.from_page_form_cd \"fromPageFormCd\", "+
									"nbsConversionPageMgmt.to_page_form_cd \"toPageFormCd\", NBS_conversion_condition.condition_cd \"conditionCd\", "+
									"fromPage.wa_template_uid \"fromPageWaTemplateUid\", toPage.wa_template_uid \"toPageWaTemplateUid\", fromPage.template_nm \"fromPageName\", "+
									"toPage.template_nm \"toPageName\", NBS_conversion_condition.status_cd \"mappingStatusCd\", NBS_conversion_condition.last_chg_time \"lastChgTime\" "+
									"FROM NBS_Conversion_Page_Mgmt nbsConversionPageMgmt LEFT OUTER JOIN "+
									"WA_template fromPage ON nbsConversionPageMgmt.from_page_form_cd = fromPage.form_cd AND fromPage.template_type IN ('Draft', 'Published','LEGACY') "+
									"INNER JOIN WA_template toPage ON nbsConversionPageMgmt.to_page_form_cd = toPage.form_cd LEFT OUTER JOIN "+
									"NBS_conversion_condition ON nbsConversionPageMgmt.NBS_Conversion_Page_Mgmt_uid = NBS_conversion_condition.NBS_Conversion_Page_Mgmt_uid "+
									"WHERE (toPage.template_type IN ('Draft', 'Published')) "+
									"ORDER BY nbsConversionPageMgmt.add_time";
	
	private static final String INSERT_NBS_CONVERSION_PAGE_MANAGEMENT ="INSERT INTO NBS_Conversion_Page_Mgmt (map_name, from_page_form_cd, to_page_form_cd, mapping_status_cd, add_time, add_user_id, last_chg_time, last_chg_user_id, xml_payload) "+
									" VALUES(?,?,?,?,?,?,?,?,?)";
				
	public static final String SELECT_WA_CONVERSION_MAPPING_VIEW ="SELECT WA_Conversion_Mapping.WA_Conversion_Mapping_uid \"waPortMappingUid\", WA_Conversion_Mapping.NBS_Conversion_Page_Mgmt_uid \"nbsConversionPageMgmtUid\", WA_Conversion_Mapping.from_question_id \"fromQuestionId\", WA_UI_metadata.question_nm \"fromLabel\", "+ 
            "WA_UI_metadata.code_set_group_id  fromCodeSetGroupId, WA_UI_metadata.data_type \"fromDataType\", WA_UI_metadata.data_location \"fromDbLocation\", WA_UI_metadata.wa_template_uid, WA_Conversion_Mapping.from_answer \"fromCode\", WA_UI_metadata.nbs_ui_component_uid \"fromNbsUiComponentUid\",   "+ 
            "WA_Conversion_Mapping.to_question_id \"toQuestionId\",WA_UI_metadata_1.data_location \"toDbLocation\", WA_UI_metadata_1.data_type \"toDataType\", WA_UI_metadata_1.code_set_group_id AS toCodeSetGroupId, WA_UI_metadata_1.question_nm \"toLabel\",   "+ 
            "WA_UI_metadata_1.wa_template_uid , WA_Conversion_Mapping.to_answer \"toCode\", WA_Conversion_Mapping.block_id_nbr \"blockIdNbr\", WA_Conversion_Mapping.mapping_status \"statusCode\",  "+ 
            "WA_Conversion_Mapping.question_mapped \"questionMappedCode\", WA_Conversion_Mapping.answer_mapped \"answerMappedCode\", WA_Conversion_Mapping.answer_group_seq_nbr \"answerGroupSeqNbr\",  cgm.code_set_nm \"fromCodeSetNm\", cgm1.code_set_nm \"toCodeSetNm\", to_nbs_ui_component_uid \"toNbsUiComponentUid\", to_code_set_group_id \"toCodeSetGroupId\", WA_UI_metadata.question_group_seq_nbr \"questionGroupSeqNbr\", WA_UI_metadata_1.question_group_seq_nbr \"toQuestionGroupSeqNbr\", WA_UI_metadata.unit_type_cd \"unitTypeCd\", WA_UI_metadata.unit_value  \"unitValue\", WA_UI_metadata_1.other_value_ind_cd \"otherInd\", WA_Conversion_Mapping.conversion_type \"conversionType\"  "+ 
            "FROM WA_Conversion_Mapping LEFT OUTER JOIN "+ 
            "WA_UI_metadata  WA_UI_metadata_1 ON WA_Conversion_Mapping.to_question_id = WA_UI_metadata_1.question_identifier and WA_UI_metadata_1.wa_template_uid =  ? LEFT OUTER JOIN "+ 
            "WA_UI_metadata ON WA_Conversion_Mapping.from_question_id = WA_UI_metadata.question_identifier LEFT OUTER JOIN "+ 
            "NBS_SRTE..Codeset_Group_Metadata  cgm1 ON cgm1.code_set_group_id = WA_UI_metadata_1.code_set_group_id LEFT OUTER JOIN "+ 
            "NBS_SRTE..Codeset_Group_Metadata  cgm ON cgm.code_set_group_id = WA_UI_metadata.code_set_group_id "+ 
            "WHERE (WA_UI_metadata.wa_template_uid = ? or WA_Conversion_Mapping.from_question_id ='' or WA_Conversion_Mapping.from_question_id is null) AND (WA_UI_metadata_1.wa_template_uid = ? or WA_Conversion_Mapping.to_question_id ='' or WA_Conversion_Mapping.to_question_id is null   or WA_Conversion_Mapping.to_question_id is not null ) AND (WA_Conversion_Mapping.NBS_Conversion_Page_Mgmt_uid = ?) order by WA_Conversion_Mapping.from_question_id ";
		
	private static final String SELECT_WA_CONVERSION_MAPPING_FOR_REVIEW ="";
	
	private static final String FIND_WA_TEMPLATE_BY_TEMPLATE_NM = "SELECT wa_template_uid \"waTemplateUid\", "
			+ "template_type \"templateType\", "
			+ "template_nm \"templateNm\", "
			+ "xml_payload \"xmlPayload\", "
			+ "publish_version_nbr \"publishVersionNbr\", "
			+ "form_cd \"formCd\", "
			+ "condition_cd \"conditionCd\", "
			+ "bus_obj_type \"busObjType\", "
			+ "datamart_nm \"dataMartNm\", "
			+ "last_chg_user_id \"lastChgUserId\", "
			+ "last_chg_time \"lastChgTime\", "
			+ "record_status_cd \"recStatusCd\", "
			+ "record_status_time \"recStatusTime\","
			+ "desc_txt \"descTxt\","
			+ "publish_ind_cd \"publishIndCd\","
			+ "version_note \"versionNote\","
			+ "publish_version_nbr \"version\" "
			+ "FROM WA_template "
			+ "WHERE template_nm = ? and bus_obj_type='INV' and template_type<>'TEMPLATE'" ;
	
	private static final String SELECT_CORE_QUESTION_FOR_PAGE = "SELECT question_identifier \"fromQuestionId\", question_nm \"fromLabel\", page.code_set_group_id \"codeSetGroupId\", codeset.code_set_nm \"fromCodeSetNm\",data_location \"fromDbLocation\", data_type \"fromDataType\", wa_ui_metadata_uid \"waUiMetadataUid\", question_group_seq_nbr \"questionGroupSeqNbr\", nbs_ui_component_uid \"fromNbsUiComponentUid\" "+ 
			" FROM WA_UI_metadata page left outer join  NBS_SRTE..Codeset codeset on page.code_set_group_id = codeset.code_set_group_id "+
			" WHERE wa_template_uid = ? and data_location IS NOT NULL and data_location <> 'NBS_CASE_ANSWER.ANSWER_TXT' AND order_nbr <> 0";
	
	
	private static final String NOTIFICATION_COUNT_BY_CONDITION_AND_STATUS = "select COUNT(*) from Notification where case_condition_cd = ? AND record_status_cd = ?";
	
	private static  String SELECT_NBS_CONVERSION_MAPPING = "SELECT nbs_conversion_mapping_uid  \"nbsConversionMappingUid\",from_code   \"fromCode\",from_code_set_nm  \"fromCodeSetNm\"," +
			"from_data_type  \"fromDataType\",from_question_id  \"fromQuestionId\",condition_cd_group_id   \"conditionCdGroupId\"," +
			"to_code  \"toCode\",to_code_set_nm  \"toCodeSetNm\",to_data_type  \"toDataType\",to_question_id  \"toQuestionId\",translation_required_ind  " +
			" \"translationRequiredInd\",from_db_location  \"fromDbLocation\",to_db_location  \"toDbLocation\",from_label  \"fromLabel\", " +
			" to_label \"toLabel\", legacy_block_ind \"legacyBlockInd\", block_id_nbr \"blockIdNbr\", other_ind \"otherInd\", unit_ind \"unitInd\", " +
			" unit_type_cd \"unitTypeCd\", unit_value \"unitValue\", " + 
			" trigger_question_id \"triggerQuestionId\", trigger_question_value \"triggerQuestionValue\", from_other_question_id \"fromOtherQuestionId\", " +
			" conversion_type \"conversionType\", answer_group_seq_nbr \"answerGroupSeqNbr\" " +
			" FROM  NBS_conversion_mapping  where condition_cd_group_id = ? " +
			" order by  to_question_id, to_db_location";
	
	private static String SELECT_PUBLIC_HEALTH_CASE_UID_BY_CONDITION = "select public_health_case_uid \"longKey\", local_id \"value\" from public_health_case where cd = ? AND case_type_cd = 'I' order by add_time";

	private static String SELECT_NBS_QUESTION_FOR_QID_QUID = "SELECT distinct NBS_ui_metadata.nbs_question_uid , ISNULL(NBS_ui_metadata.question_identifier, NBS_question.question_identifier) FROM NBS_ui_metadata INNER JOIN NBS_question ON NBS_ui_metadata.nbs_question_uid = NBS_question.nbs_question_uid WHERE NBS_ui_metadata.nbs_question_uid IN (REPLACE_WITH_NBS_QUESTION_UIDS)";
	
	private static String SELECT_NBS_QUESTION_UID_FOR_QUESTION_ID = "select NBS_ui_metadata.question_identifier, NBS_ui_metadata.nbs_question_uid from NBS_ui_metadata "+ 
			"inner join WA_template on form_cd = investigation_form_cd  where NBS_ui_metadata.question_identifier in (REPLACE_WITH_QUESTION_IDS)  and wa_template_uid = ? ";
	
			//"select question_identifier, nbs_question_uid from NBS_ui_metadata where question_identifier in (REPLACE_WITH_QUESTION_IDS)";
	
	private static String SELECT_CONVERTED_CASES_FROM_NBS_CONVERSION_MASTER= "SELECT NBS_conversion_master.act_uid FROM NBS_conversion_master INNER JOIN NBS_conversion_condition "+ 
			"ON NBS_conversion_master.nbs_conversion_condition_uid = NBS_conversion_condition.nbs_conversion_condition_uid "+
			"where condition_cd_group_id=? and NBS_conversion_master.status_cd=? and process_type_ind='Production' and NBS_conversion_master.act_uid is not null";
	
	//Gets the fromDbLocation from IMRDBMapping table's IMRDBMapping.DB_table+'.'+IMRDBMapping.DB_field
	private static final String SELECT_WA_CONVERSION_LEGACY_MAPPING_VIEW = "SELECT WA_Conversion_Mapping.WA_Conversion_Mapping_uid \"waPortMappingUid\", WA_Conversion_Mapping.NBS_Conversion_Page_Mgmt_uid \"nbsConversionPageMgmtUid\", WA_Conversion_Mapping.from_question_id \"fromQuestionId\", WA_UI_metadata_1.question_nm \"fromLabel\",  "+
			"Codeset.code_set_group_id  fromCodeSetGroupId, WA_UI_metadata_1.data_type \"fromDataType\", IMRDBMapping.DB_table+'.'+IMRDBMapping.DB_field \"fromDbLocation\", WA_UI_metadata_1.wa_template_uid, WA_Conversion_Mapping.from_answer \"fromCode\", WA_UI_metadata_1.nbs_ui_component_uid \"fromNbsUiComponentUid\",    "+
			"WA_Conversion_Mapping.to_question_id \"toQuestionId\",WA_UI_metadata_1.data_location \"toDbLocation\", WA_UI_metadata_1.data_type \"toDataType\", WA_UI_metadata_1.code_set_group_id AS toCodeSetGroupId, WA_UI_metadata_1.question_nm \"toLabel\",    "+
			"WA_UI_metadata_1.wa_template_uid , WA_Conversion_Mapping.to_answer \"toCode\", WA_Conversion_Mapping.block_id_nbr \"blockIdNbr\", WA_Conversion_Mapping.mapping_status \"statusCode\",   "+
			"WA_Conversion_Mapping.question_mapped \"questionMappedCode\", WA_Conversion_Mapping.answer_mapped \"answerMappedCode\", WA_Conversion_Mapping.answer_group_seq_nbr \"answerGroupSeqNbr\",  TotalIDM.SRT_reference \"fromCodeSetNm\", cgm1.code_set_nm \"toCodeSetNm\", to_nbs_ui_component_uid \"toNbsUiComponentUid\", to_code_set_group_id \"toCodeSetGroupId\", WA_UI_metadata_1.question_group_seq_nbr \"questionGroupSeqNbr\", WA_UI_metadata_1.unit_type_cd \"unitTypeCd\", WA_UI_metadata_1.unit_value  \"unitValue\", WA_UI_metadata_1.other_value_ind_cd \"otherInd\" "+  
			"FROM WA_Conversion_Mapping LEFT OUTER JOIN "+  
			"WA_UI_metadata  WA_UI_metadata_1 ON WA_Conversion_Mapping.to_question_id = WA_UI_metadata_1.question_identifier LEFT OUTER JOIN "+  
			"NBS_SRTE..Codeset_Group_Metadata  cgm1 ON cgm1.code_set_group_id = WA_UI_metadata_1.code_set_group_id "+
			"LEFT OUTER JOIN NBS_SRTE..IMRDBMapping IMRDBMapping ON WA_UI_metadata_1.question_identifier = IMRDBMapping.unique_cd and CONDITION_CD = 'VACCINATION' "+
			"LEFT OUTER JOIN NBS_SRTE..TotalIDM TotalIDM  ON TotalIDM.unique_cd = IMRDBMapping.unique_cd "+
			"LEFT OUTER JOIN NBS_SRTE..Codeset Codeset ON Codeset.code_set_nm = TotalIDM.SRT_reference "+
			"WHERE (WA_UI_metadata_1.wa_template_uid = ? or WA_Conversion_Mapping.to_question_id ='' or WA_Conversion_Mapping.to_question_id is null) AND (WA_Conversion_Mapping.NBS_Conversion_Page_Mgmt_uid = ?) order by WA_Conversion_Mapping.from_question_id ";
			
	private static final String SELECT_TO_QUESTION_LIST_FOR_LEGACY = "SELECT question_identifier \"fromQuestionId\", question_nm \"fromLabel\", topage.code_set_group_id \"codeSetGroupId\", codeset.code_set_nm \"fromCodeSetNm\", "+
			"data_location \"fromDbLocation\", data_type \"fromDataType\", wa_ui_metadata_uid \"waUiMetadataUid\", record_status_cd \"recordStatusCd\",  "+
			"question_group_seq_nbr \"questionGroupSeqNbr\", nbs_ui_component_uid \"toNbsUiComponentUid\", other_value_ind_cd  \"otherInd\"   "+
			"FROM WA_UI_metadata topage left outer join  NBS_SRTE..Codeset codeset on topage.code_set_group_id = codeset.code_set_group_id   "+
			"WHERE group_nm <> 'GROUP_DEM' and order_nbr > 0 and wa_template_uid = ? ";
	
    /**
     * @param fromPageTemplateUid
     * @param toPageTemplateUid
     * @return
     * @throws NEDSSDAOSysException
     * @throws NEDSSSystemException
     */
    public ArrayList<Object> getFieldsFromPageQuestions(Long fromPageTemplateUid, Long toPageTemplateUid) throws NEDSSDAOSysException, NEDSSSystemException {
		ArrayList<Object>  fromFieldList = new ArrayList<Object> ();
		//fromFieldList.add(conditionCd);
		
		logger.debug("fromPageTemplateUid: "+fromPageTemplateUid+", toPageTemplateUid: "+toPageTemplateUid);
		fromFieldList.add(fromPageTemplateUid);
		fromFieldList.add(toPageTemplateUid);
		fromFieldList.add(fromPageTemplateUid);
		fromFieldList.add(toPageTemplateUid);
		fromFieldList.add(fromPageTemplateUid);
		MappedFromToQuestionFieldsDT portPageFromFieldsDT = new MappedFromToQuestionFieldsDT();
		try{
			String selectQuery=null;
			selectQuery=SELECT_FROM_FIELD_LIST;
			logger.info("Query : "+selectQuery);
			
			fromFieldList = (ArrayList<Object>) preparedStmtMethod(portPageFromFieldsDT, fromFieldList, selectQuery, NEDSSConstants.SELECT);
			Map<String, MappedFromToQuestionFieldsDT> map = new HashMap<>();
			
			//Removing duplicate questions from list
			for (int i=0;i<fromFieldList.size();i++){
				MappedFromToQuestionFieldsDT dt = (MappedFromToQuestionFieldsDT)fromFieldList.get(i);
				if(!map.containsKey(dt.getFromQuestionId()) || "N".equals(dt.getAutoMapped())){ // Query has UNION, which gets duplicate records, if resultset has not mapped and automapped questions. Add not mapped one.
					map.put(dt.getFromQuestionId(), dt);
				}
			}
			fromFieldList.clear();
			fromFieldList.addAll(map.values());
			
		}catch(NEDSSDAOSysException daoSysEx){
			logger.fatal("NEDSSDAOSysException getFieldsFromPageQuestions fromPageTemplateUid: "+fromPageTemplateUid+", toPageTemplateUid: "+toPageTemplateUid+", Exception: "+daoSysEx.getMessage(), daoSysEx);
			throw new NEDSSDAOSysException(daoSysEx.toString());
		}catch(NEDSSSystemException sysEx){
			logger.fatal("NEDSSSystemException getFieldsFromPageQuestions fromPageTemplateUid: "+fromPageTemplateUid+", toPageTemplateUid: "+toPageTemplateUid+", Exception: "+sysEx.getMessage(), sysEx);
			throw new NEDSSSystemException(sysEx.toString());
		}catch(Exception ex){
			logger.fatal("Exception getFieldsFromPageQuestions fromPageTemplateUid: "+fromPageTemplateUid+", toPageTemplateUid: "+toPageTemplateUid+", Exception: "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return fromFieldList;
	}
	
	/**
	 * @param fromPageTemplateUid
	 * @param toPageTemplateUid
	 * @return
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	public ArrayList<Object> getToPageQuestionsFields(Long fromPageTemplateUid, Long toPageTemplateUid, String mappingType) throws NEDSSDAOSysException, NEDSSSystemException {
		ArrayList<Object>  toFieldList = new ArrayList<Object> ();
		logger.debug("fromPageTemplateUid: "+fromPageTemplateUid+", toPageTemplateUid: "+toPageTemplateUid);
		
		
		MappedFromToQuestionFieldsDT portPageFromFieldsDT = new MappedFromToQuestionFieldsDT();
		try{
			String selectQuery=null;
			if(PortPageUtil.MAPPING_PAGEBUILDER.equals(mappingType)){
				toFieldList.add(toPageTemplateUid);
				toFieldList.add(fromPageTemplateUid);
				toFieldList.add(toPageTemplateUid);
				selectQuery = SELECT_TO_QUESTION_LIST;
			}else{
				toFieldList.add(toPageTemplateUid);
				selectQuery = SELECT_TO_QUESTION_LIST_FOR_LEGACY;
			}
			
			logger.info("selectQuery : "+selectQuery);
			toFieldList = (ArrayList<Object>) preparedStmtMethod(portPageFromFieldsDT, toFieldList, selectQuery, NEDSSConstants.SELECT);
			
			addDummyQuestionInList(toFieldList);
			
			/*// Structure Numeric Mapping, add dummy question with _UNIT in ToQuesiton Field List.
			
			ArrayList<Object>  unitQuestionList = new ArrayList<Object> ();
			
			CachedDropDownValues cdv = new CachedDropDownValues();
			for(int i=0;i<toFieldList.size();i++){
				MappedFromToQuestionFieldsDT toFieldDT = (MappedFromToQuestionFieldsDT) toFieldList.get(i);
				if(toFieldDT.getUnitTypeCd()!=null && PortPageUtil.DATA_TYPE_CODED.equals(toFieldDT.getUnitTypeCd())){
					MappedFromToQuestionFieldsDT dt = (MappedFromToQuestionFieldsDT) toFieldDT.deepCopy();
					dt.setFromQuestionId(dt.getFromQuestionId()+PortPageUtil.STRUCTURE_NUMERIC_DUMMY_QUE_SUFFIX);
					dt.setFromDataType(PortPageUtil.DATA_TYPE_CODED);
					if(dt.getUnitValue()!=null){
						dt.setCodeSetGroupId(Long.parseLong(dt.getUnitValue()));
						dt.setFromCodeSetNm(cdv.getTheCodeSetNm(Long.parseLong(dt.getUnitValue())));
						dt.setFromLabel(dt.getFromLabel()+PortPageUtil.STRUCTURE_NUMERIC_DUMMY_QUE_LABEL_SUFFIX);
					}
					unitQuestionList.add(dt);
				}
			}
			
			toFieldList.addAll(unitQuestionList);*/
		}catch(NEDSSDAOSysException daoSysEx){
			logger.fatal("NEDSSDAOSysException getToPageQuestionsFields fromPageTemplateUid: "+fromPageTemplateUid+", toPageTemplateUid: "+toPageTemplateUid+", Exception: "+daoSysEx.getMessage(), daoSysEx);
			throw new NEDSSDAOSysException(daoSysEx.toString());
		}catch(NEDSSSystemException sysEx){
			logger.fatal("NEDSSSystemException getToPageQuestionsFields fromPageTemplateUid: "+fromPageTemplateUid+", toPageTemplateUid: "+toPageTemplateUid+", Exception: "+sysEx.getMessage(), sysEx);
			throw new NEDSSSystemException(sysEx.toString());
		}catch(Exception ex){
			logger.fatal("Exception getToPageQuestionsFields fromPageTemplateUid: "+fromPageTemplateUid+", toPageTemplateUid: "+toPageTemplateUid+", Exception: "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return toFieldList;
	}
	
	/**
	 * @param questionIDList
	 * @param notMappedQuestionList
	 * @param waTemplateUid
	 * @return
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	public ArrayList<Object> getAnswersToMap(ArrayList<Object> questionIDList, ArrayList<Object> notMappedQuestionList, Long waTemplateUid) throws NEDSSDAOSysException, NEDSSSystemException {
		ArrayList<Object>  answerMappingList = new ArrayList<Object> ();
		logger.debug("waTemplateUid: "+waTemplateUid);
		try{
			StringBuffer questionInStringBuffer = new StringBuffer("");
			String questionsIn="";
			for(int i=0;i<questionIDList.size();i++){
				questionInStringBuffer=questionInStringBuffer.append("'"+questionIDList.get(i)+"',");
			}
			if(questionInStringBuffer.length()>0)
				questionsIn = questionInStringBuffer.substring(0,questionInStringBuffer.length()-1);
			else
				questionsIn="' '";//if non of the question exist then pass '' in 'question_identifier in ('')'
			
			String questionsNotIn="";
			StringBuffer questionNotInStringBuffer = new StringBuffer("");
			if(notMappedQuestionList!=null){
				for(int i=0;i<notMappedQuestionList.size();i++){
					questionNotInStringBuffer=questionNotInStringBuffer.append("'"+notMappedQuestionList.get(i)+"',");
				}
			}
			
			if(questionNotInStringBuffer.length()>0){
				questionsNotIn = questionNotInStringBuffer.substring(0,questionNotInStringBuffer.length()-1)+","+questionsIn;
			}else if (questionsIn.length()>0){
				questionsNotIn = questionsIn;
			}else{
				questionsNotIn="' '";
			}
			
			answerMappingList.add(waTemplateUid);
			answerMappingList.add(waTemplateUid);
			
			AnswerMappingDT answerMappingDT = new AnswerMappingDT();
			
			String selectQuery=SELECT_ANSWERS_FOR_QUESTION.replace("REPLACE_WITH_QUESTIONS", questionsIn).replace("REPLACE_WITH_NOT_IN_QUESTIONS", questionsNotIn);
			
			logger.info("selectQuery: "+selectQuery);
			
			answerMappingList = (ArrayList<Object>) preparedStmtMethod(answerMappingDT, answerMappingList, selectQuery, NEDSSConstants.SELECT);
		}catch(NEDSSDAOSysException daoSysEx){
			logger.fatal("NEDSSDAOSysException getAnswersToMap waTemplateUid: "+waTemplateUid+", "+daoSysEx.getMessage(), daoSysEx);
			throw new NEDSSDAOSysException(daoSysEx.toString());
		}catch(NEDSSSystemException sysEx){
			logger.fatal("NEDSSSystemException getAnswersToMap waTemplateUid: "+waTemplateUid+", "+sysEx.getMessage(), sysEx);
			throw new NEDSSSystemException(sysEx.toString());
		}catch(Exception ex){
			logger.fatal("Exception getAnswersToMap waTemplateUid: "+waTemplateUid+", "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return answerMappingList;
	}
	
	
	// Inserts mapped question into WA_Conversion_Mapping table.
		/**
		 * @param queAnsList
		 * @param waPortMappingUid
		 * @return
		 * @throws NEDSSDAOSysException
		 * @throws NEDSSSystemException
		 */
		public boolean insertQuestionAnswers(ArrayList<Object> queAnsList, Long waPortMappingUid) throws NEDSSDAOSysException, NEDSSSystemException {
			Connection dbConnection = null;
			PreparedStatement preparedStmt = null;
			//ResultSet resultSet = null;
			//int conditionCDGroupId=0;
			logger.debug("waPortMappingUid: "+waPortMappingUid);
			
			Connection dbConnectionToDelete = null;
			PreparedStatement preparedStmtToDelete = null;
			logger.debug("waPortMappingUid: "+waPortMappingUid);

			try{
				String deletePageMapping = "DELETE FROM WA_Conversion_Mapping WHERE NBS_Conversion_Page_Mgmt_uid = ?";
				dbConnectionToDelete = getConnection();
				preparedStmtToDelete = dbConnectionToDelete.prepareStatement(deletePageMapping);
				preparedStmtToDelete.setLong(1, waPortMappingUid);
				preparedStmtToDelete.executeUpdate();
				
			}catch(NEDSSDAOSysException daoSysEx){
				logger.fatal("NEDSSDAOSysException insertQuestionAnswers waPortMappingUid: "+waPortMappingUid+", "+daoSysEx.getMessage(), daoSysEx);
				throw new NEDSSDAOSysException(daoSysEx.toString());
			}catch(NEDSSSystemException sysEx){
				logger.fatal("NEDSSSystemException insertQuestionAnswers waPortMappingUid: "+waPortMappingUid+", "+sysEx.getMessage(), sysEx);
				throw new NEDSSSystemException(sysEx.toString());
			}catch(Exception ex){
				logger.fatal("Exception insertQuestionAnswers waPortMappingUid: "+waPortMappingUid+", "+ex.getMessage(), ex);
				throw new NEDSSSystemException(ex.toString());
			}finally {
				closeStatement(preparedStmtToDelete);
				releaseConnection(dbConnectionToDelete);

			}
			
			try{
				dbConnection = getConnection();
				preparedStmt = dbConnection.prepareStatement(INSERT_WA_CONVERSION_MAPPING);
				for(int i=0;i<queAnsList.size();i++){
					MappedFromToQuestionFieldsDT dt = (MappedFromToQuestionFieldsDT) queAnsList.get(i);
					preparedStmt.setLong(1, waPortMappingUid);
					preparedStmt.setString(2, dt.getFromQuestionId());
					preparedStmt.setString(3, dt.getFromCode()); // from answer
					preparedStmt.setString(4, dt.getToQuestionId());
					preparedStmt.setString(5, dt.getToCode());
					if(dt.getToCodeSetGroupId()!=null)
						preparedStmt.setLong(6, dt.getToCodeSetGroupId());
					else 
						preparedStmt.setNull(6, Types.BIGINT);
					preparedStmt.setString(7, dt.getToDataType());
					
					if(dt.getToNbsUiComponentUid()!=null)
						preparedStmt.setLong(8, dt.getToNbsUiComponentUid());
					else
						preparedStmt.setNull(8, Types.BIGINT);
					
					if(dt.getBlockIdNbr()!=null)
						preparedStmt.setInt(9, dt.getBlockIdNbr().intValue());
					else
						preparedStmt.setNull(9, Types.INTEGER);
					preparedStmt.setString(10, dt.getStatusCode());
					preparedStmt.setString(11, dt.getQuestionMappedCode());
					preparedStmt.setString(12, dt.getAnswerMappedCode());
					if(dt.getAnswerGroupSeqNbr()!=null)
						preparedStmt.setInt(13, dt.getAnswerGroupSeqNbr().intValue());
					else
						preparedStmt.setNull(13, Types.INTEGER);
					preparedStmt.setString(14, dt.getConversionType());
					
					preparedStmt.addBatch();
				}
				int[] insertResult = preparedStmt.executeBatch();
				logger.debug("total insertResult ------------"+insertResult.length);
				return true;
			}catch(NEDSSDAOSysException daoSysEx){
				logger.fatal("NEDSSDAOSysException insertQuestionAnswers waPortMappingUid: "+waPortMappingUid+", "+daoSysEx.getMessage(), daoSysEx);
				throw new NEDSSDAOSysException(daoSysEx.toString());
			}catch(NEDSSSystemException sysEx){
				logger.fatal("NEDSSSystemException insertQuestionAnswers waPortMappingUid: "+waPortMappingUid+", "+sysEx.getMessage(), sysEx);
				throw new NEDSSSystemException(sysEx.toString());
			}catch(Exception ex){
				logger.fatal("Exception insertQuestionAnswers waPortMappingUid: "+waPortMappingUid+", "+ex.getMessage(), ex);
				throw new NEDSSSystemException(ex.toString());
			}finally {
				closeStatement(preparedStmt);
				releaseConnection(dbConnection);

			}
		}
		
		//Retrieve Question Answers from WA_Conversion_Mapping table, Mapped, mapping needed and auto mapped questions
		/**
		 * @param conditionCd
		 * @return
		 * @throws NEDSSDAOSysException
		 * @throws NEDSSSystemException
		 */
		public ArrayList<Object> retrieveQuestionAnswers(String conditionCd) throws NEDSSDAOSysException, NEDSSSystemException {
			ArrayList<Object>  questionAnswerList = new ArrayList<Object> ();
			logger.debug("conditionCd: "+conditionCd);
			/*final String selectConditionCdGroupID = "select condition_cd_group_id from NBS_conversion_condition where condition_cd=? order by nbs_conversion_condition_uid desc";
			Connection dbConnection = null;
			PreparedStatement preparedStmt = null;
			ResultSet resultSet = null;
			
			int conditionCDGroupId=0;
			try{
				dbConnection = getConnection();
				preparedStmt = dbConnection.prepareStatement(selectConditionCdGroupID);
				preparedStmt.setString(1, conditionCd);
				resultSet = preparedStmt.executeQuery();
				if(resultSet.next()){
					conditionCDGroupId = resultSet.getInt(1);
				}
			}catch(NEDSSDAOSysException daoSysEx){
				logger.fatal("NEDSSDAOSysException getFieldsFromPageQuestions "+daoSysEx.getMessage(), daoSysEx);
				throw new NEDSSDAOSysException(daoSysEx.toString());
			}catch(NEDSSSystemException sysEx){
				logger.fatal("NEDSSSystemException getFieldsFromPageQuestions "+sysEx.getMessage(), sysEx);
				throw new NEDSSSystemException(sysEx.toString());
			}catch(Exception ex){
				logger.fatal("Exception getFieldsFromPageQuestions "+ex.getMessage(), ex);
				throw new NEDSSSystemException(ex.toString());
			}finally {
				closeResultSet(resultSet);
				closeStatement(preparedStmt);
				releaseConnection(dbConnection);
			}
			
			try{
				questionAnswerList.add(conditionCDGroupId);
				MappedFromToQuestionFieldsDT questionDT = new MappedFromToQuestionFieldsDT();
				questionAnswerList = (ArrayList<Object>) preparedStmtMethod(questionDT, questionAnswerList, SELECT_WA_CONVERSION_MAPPING, NEDSSConstants.SELECT);
				
				for(int i=0;i<questionAnswerList.size();i++){
					MappedFromToQuestionFieldsDT mappingDT = (MappedFromToQuestionFieldsDT) questionAnswerList.get(i);
					mappingDT.setStatusDesc(CachedDropDowns.getCodeDescTxtForCd(mappingDT.getStatusCode(), PortPageUtil.NBS_QA_MAPPING_STATUS_CODE_SET));
					
					mappingDT.setQuestionMappedDesc(CachedDropDowns.getCodeDescTxtForCd(mappingDT.getQuestionMappedCode(), PortPageUtil.CODE_SET_YN));
				}
			}catch(NEDSSDAOSysException daoSysEx){
				logger.fatal("NEDSSDAOSysException getFieldsFromPageQuestions "+daoSysEx.getMessage(), daoSysEx);
				throw new NEDSSDAOSysException(daoSysEx.toString());
			}catch(NEDSSSystemException sysEx){
				logger.fatal("NEDSSSystemException getFieldsFromPageQuestions "+sysEx.getMessage(), sysEx);
				throw new NEDSSSystemException(sysEx.toString());
			}catch(Exception ex){
				logger.fatal("Exception getFieldsFromPageQuestions "+ex.getMessage(), ex);
				throw new NEDSSSystemException(ex.toString());
			}*/
			return questionAnswerList;
		}
		
	
	
	
	// find list of answers for questions by page, used for Numeric to Coded / Text to Coded mapping
	/**
	 * @param questionIdentifier
	 * @param fromPageWaTemplateUid
	 * @return
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	public ArrayList<Object> findAnswersForQuestionsByPage(String questionIdentifier, Long fromPageWaTemplateUid) throws NEDSSDAOSysException, NEDSSSystemException {
		ArrayList<Object>  answersByPage = new ArrayList<Object> ();
		logger.debug("questionIdentifier: "+questionIdentifier+", fromPageWaTemplateUid: "+fromPageWaTemplateUid);
		
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		
		try{
			logger.info("Query: "+SELECT_NBS_CASE_ANSWER_FOR_QUESTION);
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(SELECT_NBS_CASE_ANSWER_FOR_QUESTION);
			preparedStmt.setLong(1, fromPageWaTemplateUid);
			preparedStmt.setString(2, questionIdentifier);			
			resultSet = preparedStmt.executeQuery();
			while(resultSet.next()){
				answersByPage.add(resultSet.getString(1));
			}
		}catch(NEDSSDAOSysException daoSysEx){
			logger.fatal("NEDSSDAOSysException findAnswersForQuestionsByPage questionIdentifier: "+questionIdentifier+", fromPageWaTemplateUid: "+fromPageWaTemplateUid+" Exception : "+daoSysEx.getMessage(), daoSysEx);
			throw new NEDSSDAOSysException(daoSysEx.toString());
		}catch(NEDSSSystemException sysEx){
			logger.fatal("NEDSSSystemException findAnswersForQuestionsByPage questionIdentifier: "+questionIdentifier+", fromPageWaTemplateUid: "+fromPageWaTemplateUid+" Exception : "+sysEx.getMessage(), sysEx);
			throw new NEDSSSystemException(sysEx.toString());
		}catch(Exception ex){
			logger.fatal("Exception findAnswersForQuestionsByPage questionIdentifier: "+questionIdentifier+", fromPageWaTemplateUid: "+fromPageWaTemplateUid+" Exception : "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
		
		return answersByPage;
	}
	
	
	/**
	 * @return
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	public ArrayList<Object> getPortPageList() throws NEDSSDAOSysException, NEDSSSystemException {
		ArrayList<Object>  portPageList = new ArrayList<Object> ();
		
		ManagePageDT managePageDT = new ManagePageDT();
		try{
			logger.info("Query: "+SELECT_NBS_CONVERSION_PAGE_MANAGEMENT);
			portPageList = (ArrayList<Object>) preparedStmtMethod(managePageDT, portPageList, SELECT_NBS_CONVERSION_PAGE_MANAGEMENT, NEDSSConstants.SELECT);
			for(int i=0;i<portPageList.size();i++){
				ManagePageDT pageDT = (ManagePageDT) portPageList.get(i);
				pageDT.setConditionDescText(CachedDropDowns.getConditionDesc(pageDT.getConditionCd()));
				pageDT.setMappingStatusDescText(CachedDropDowns.getCodeDescTxtForCd(pageDT.getMappingStatusCd(), PortPageUtil.NBS_PAGE_MAPPING_STATUS_CODE_SET));
				pageDT.setEventTypeDescTxt(CachedDropDowns.getCodeDescTxtForCd(pageDT.getEventTypeCd(), PortPageUtil.BUS_OBJ_TYPE_CODE_SET));
				pageDT.setConditionDescWithLink(pageDT.getConditionDescText());
			}
			logger.debug("portPageList.size(): "+portPageList.size());
		}catch(NEDSSDAOSysException daoSysEx){
			logger.fatal("NEDSSDAOSysException getPortPageList "+daoSysEx.getMessage(), daoSysEx);
			throw new NEDSSDAOSysException(daoSysEx.toString());
		}catch(NEDSSSystemException sysEx){
			logger.fatal("NEDSSSystemException getPortPageList "+sysEx.getMessage(), sysEx);
			throw new NEDSSSystemException(sysEx.toString());
		}catch(Exception ex){
			logger.fatal("Exception getPortPageList "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		
		return portPageList;
	}
	
	
	
	/**
	 * @param mapName
	 * @param fromPageTemplateName
	 * @param toPageTemplateName
	 * @param mappingStatus
	 * @param addUserId
	 * @param lastChgUserId
	 * @return
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	public Long createPortPageMapping(String mapName,String fromPageTemplateName,String toPageTemplateName,String mappingStatus,String xmlPayload,Long addUserId,Long lastChgUserId) throws NEDSSDAOSysException, NEDSSSystemException {
		Long nbsConversionPageMgmtUid = null;
		Connection dbConnection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		//String queryBasedOnDBType = null;
		logger.debug("mapName: "+mapName+", fromPageTemplateName: "+fromPageTemplateName+", toPageTemplateName: "+toPageTemplateName+", mappingStatus: "+mappingStatus);
		try{
			
			ArrayList<Object> paramList = new ArrayList<Object>();
			
			paramList.add(mapName);
			paramList.add(fromPageTemplateName);
			paramList.add(toPageTemplateName);
			paramList.add(mappingStatus);
			paramList.add(PortPageUtil.getCurrentTimestamp());
			paramList.add(addUserId);
			paramList.add(PortPageUtil.getCurrentTimestamp());
			paramList.add(lastChgUserId);
			paramList.add(xmlPayload);
			preparedStmtMethod(null,paramList,INSERT_NBS_CONVERSION_PAGE_MANAGEMENT,NEDSSConstants.UPDATE);
			
			
			dbConnection = getConnection();
			statement = dbConnection.createStatement();
			statement.execute("select max(NBS_Conversion_Page_Mgmt_uid) from NBS_Conversion_Page_Mgmt");
			resultSet = statement.getResultSet();
			while (resultSet.next()) {
				nbsConversionPageMgmtUid = new Long(resultSet.getLong(1));
			}
			
			if(nbsConversionPageMgmtUid == null){
				logger.fatal("Exception in createPortPageMapping: "+"nbsConversionPageMgmtUid = "+nbsConversionPageMgmtUid);
				throw new NEDSSSystemException("Exception in createPortPageMapping nbsConversionPageMgmtUid is null");
			}
		}catch(NEDSSDAOSysException daoSysEx){
			logger.fatal("NEDSSDAOSysException cratePortPageMapping, mapName: "+mapName+", fromPageTemplateName: "+fromPageTemplateName+", toPageTemplateName: "+toPageTemplateName+", mappingStatus: "+mappingStatus+", Exception: "+daoSysEx.getMessage(), daoSysEx);
			throw new NEDSSDAOSysException(daoSysEx.toString());
		}catch(NEDSSSystemException sysEx){
			logger.fatal("NEDSSSystemException cratePortPageMapping , mapName: "+mapName+", fromPageTemplateName: "+fromPageTemplateName+", toPageTemplateName: "+toPageTemplateName+", mappingStatus: "+mappingStatus+", Exception: "+sysEx.getMessage(), sysEx);
			throw new NEDSSSystemException(sysEx.toString());
		}catch(Exception ex){
			logger.fatal("Exception cratePortPageMapping , mapName: "+mapName+", fromPageTemplateName: "+fromPageTemplateName+", toPageTemplateName: "+toPageTemplateName+", mappingStatus: "+mappingStatus+", Exception: "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}finally {
			closeResultSet(resultSet);
			closeStatement(statement);
			releaseConnection(dbConnection);
		}
		
		return nbsConversionPageMgmtUid;
	
	}
	
	
	
	/**
	 * @param fromWaTemplateUid
	 * @param toWaTemplateUid
	 * @param waPortPageUid
	 * @return
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	public ArrayList<Object> viewPortPageMapping(Long fromWaTemplateUid, Long toWaTemplateUid, Long waPortPageUid, String mappingType) throws NEDSSDAOSysException, NEDSSSystemException {
		ArrayList<Object>  mappedQuestionAnswers = new ArrayList<Object> ();
		logger.debug("fromWaTemplateUid: "+fromWaTemplateUid+", toWaTemplateUid: "+toWaTemplateUid+", waPortPageUid"+waPortPageUid);
		MappedFromToQuestionFieldsDT portPageFieldsDT = new MappedFromToQuestionFieldsDT();
		try{
			if(PortPageUtil.MAPPING_PAGEBUILDER.equals(mappingType)){
				mappedQuestionAnswers.add(toWaTemplateUid);
				mappedQuestionAnswers.add(fromWaTemplateUid);
			}
			//ND-31789
			mappedQuestionAnswers.add(toWaTemplateUid);
			mappedQuestionAnswers.add(waPortPageUid);
			
			String selectQuery=null;
		
			if(PortPageUtil.MAPPING_PAGEBUILDER.equals(mappingType)){
				
					selectQuery = SELECT_WA_CONVERSION_MAPPING_VIEW;
				
			}else{
				
					selectQuery = SELECT_WA_CONVERSION_LEGACY_MAPPING_VIEW;
				
			}
			
			logger.info("selectQuery:"+selectQuery);
			
			mappedQuestionAnswers = (ArrayList<Object>) preparedStmtMethod(portPageFieldsDT, mappedQuestionAnswers, selectQuery, NEDSSConstants.SELECT);
			
			for(int i=0;i<mappedQuestionAnswers.size();i++){
				MappedFromToQuestionFieldsDT mappingDT = (MappedFromToQuestionFieldsDT) mappedQuestionAnswers.get(i);
				mappingDT.setStatusDesc(CachedDropDowns.getCodeDescTxtForCd(mappingDT.getStatusCode(), PortPageUtil.NBS_QA_MAPPING_STATUS_CODE_SET));
				mappingDT.setQuestionMappedDesc(CachedDropDowns.getCodeDescTxtForCd(mappingDT.getQuestionMappedCode(), PortPageUtil.CODE_SET_YN));
				mappingDT.setAnswerMappedDesc(CachedDropDowns.getCodeDescTxtForCd(mappingDT.getAnswerMappedCode(), PortPageUtil.CODE_SET_YN));
				if(mappingDT.getFromCode()!=null ){
					if(PortPageUtil.DATA_TYPE_CODED.equals(mappingDT.getFromDataType()))
						mappingDT.setFromCodeDesc(CachedDropDowns.getCodeDescTxtForCd(mappingDT.getFromCode(), mappingDT.getFromCodeSetNm()));
				}else{
					mappingDT.setFromCode("");
				}
				
				if(mappingDT.getToCode()!=null){
					if(PortPageUtil.DATA_TYPE_CODED.equals(mappingDT.getToDataType()))
						mappingDT.setToCodeDesc(CachedDropDowns.getCodeDescTxtForCd(mappingDT.getToCode(), mappingDT.getToCodeSetNm()));
				}else{
					mappingDT.setToCode("");
				}
			}
			
		}catch(NEDSSDAOSysException daoSysEx){
			logger.fatal("NEDSSDAOSysException viewPortPageMapping fromWaTemplateUid: "+fromWaTemplateUid+", toWaTemplateUid: "+toWaTemplateUid+", waPortPageUid"+waPortPageUid+" Exception: "+daoSysEx.getMessage(), daoSysEx);
			throw new NEDSSDAOSysException(daoSysEx.toString());
		}catch(NEDSSSystemException sysEx){
			logger.fatal("NEDSSSystemException viewPortPageMapping fromWaTemplateUid: "+fromWaTemplateUid+", toWaTemplateUid: "+toWaTemplateUid+", waPortPageUid"+waPortPageUid+" Exception: "+sysEx.getMessage(), sysEx);
			throw new NEDSSSystemException(sysEx.toString());
		}catch(Exception ex){
			logger.fatal("Exception viewPortPageMapping fromWaTemplateUid: "+fromWaTemplateUid+", toWaTemplateUid: "+toWaTemplateUid+", waPortPageUid"+waPortPageUid+" Exception: "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return mappedQuestionAnswers;
	}
	
	/**
	 * @param waPortPageUid
	 * @return
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	public ArrayList<Object> viewPortPageSummary(Long waPortPageUid) throws NEDSSDAOSysException, NEDSSSystemException {
		ArrayList<Object>  mappingsList = new ArrayList<Object> ();
		logger.debug("waPortPageUid: "+waPortPageUid);
		MappedFromToQuestionFieldsDT portPageFieldsDT = new MappedFromToQuestionFieldsDT();
		
		logger.info("Query: "+SELECT_WA_CONVERSION_MAPPING_FOR_REVIEW);
		try{
			mappingsList = (ArrayList<Object>) preparedStmtMethod(portPageFieldsDT, mappingsList, SELECT_WA_CONVERSION_MAPPING_FOR_REVIEW, NEDSSConstants.SELECT);
		
			
		}catch(NEDSSDAOSysException daoSysEx){
			logger.fatal("NEDSSDAOSysException viewPortPageSummary waPortPageUid: "+waPortPageUid+", Exception: "+daoSysEx.getMessage(), daoSysEx);
			throw new NEDSSDAOSysException(daoSysEx.toString());
		}catch(NEDSSSystemException sysEx){
			logger.fatal("NEDSSSystemException viewPortPageSummary waPortPageUid: "+waPortPageUid+", Exception: "+sysEx.getMessage(), sysEx);
			throw new NEDSSSystemException(sysEx.toString());
		}catch(Exception ex){
			logger.fatal("Exception viewPortPageSummary waPortPageUid: "+waPortPageUid+", Exception: "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return mappingsList;
	}
	
	
	/**
	 * @param pageName
	 * @return
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	public ArrayList<Object> findWaTemplateByPageName(String pageName) throws NEDSSDAOSysException, NEDSSSystemException {
		ArrayList<Object>  pageList = new ArrayList<Object> ();
		logger.debug("pageName: "+pageName);
		logger.info("Query: "+FIND_WA_TEMPLATE_BY_TEMPLATE_NM);
		
		WaTemplateDT waTemplateDT = new WaTemplateDT();
		try{
			pageList.add(pageName);
			pageList = (ArrayList<Object>) preparedStmtMethod(waTemplateDT, pageList, FIND_WA_TEMPLATE_BY_TEMPLATE_NM, NEDSSConstants.SELECT);
			
		}catch(NEDSSDAOSysException daoSysEx){
			logger.fatal("NEDSSDAOSysException findWaTemplateByPageName pageName: "+pageName+", Exception: "+daoSysEx.getMessage(), daoSysEx);
			throw new NEDSSDAOSysException(daoSysEx.toString());
		}catch(NEDSSSystemException sysEx){
			logger.fatal("NEDSSSystemException findWaTemplateByPageName pageName: "+pageName+", Exception: "+sysEx.getMessage(), sysEx);
			throw new NEDSSSystemException(sysEx.toString());
		}catch(Exception ex){
			logger.fatal("Exception findWaTemplateByPageName pageName: "+pageName+", Exception: "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return pageList;
	}
	
	
	/**
	 * @param waTemplateUid
	 * @return
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	public ArrayList<Object> findCoreQuestionsByPage(Long waTemplateUid) throws NEDSSDAOSysException, NEDSSSystemException {
		ArrayList<Object>  coreQuestionsList = new ArrayList<Object> ();
		logger.debug("waTemplateUid: "+waTemplateUid);
		MappedFromToQuestionFieldsDT coreQuestionDT = new MappedFromToQuestionFieldsDT();
		try{
			coreQuestionsList.add(waTemplateUid);
			String selectQuery=null;
			selectQuery = SELECT_CORE_QUESTION_FOR_PAGE;                                         //for sql
			logger.info("selectQuery: "+selectQuery);
			coreQuestionsList = (ArrayList<Object>) preparedStmtMethod(coreQuestionDT, coreQuestionsList, selectQuery, NEDSSConstants.SELECT);
		}catch(NEDSSDAOSysException daoSysEx){
			logger.fatal("NEDSSDAOSysException findCoreQuestionsByPage waTemplateUid: "+waTemplateUid+", Exception: "+daoSysEx.getMessage(), daoSysEx);
			throw new NEDSSDAOSysException(daoSysEx.toString());
		}catch(NEDSSSystemException sysEx){
			logger.fatal("NEDSSSystemException findCoreQuestionsByPage waTemplateUid: "+waTemplateUid+", Exception: "+sysEx.getMessage(), sysEx);
			throw new NEDSSSystemException(sysEx.toString());
		}catch(Exception ex){
			logger.fatal("Exception findCoreQuestionsByPage waTemplateUid: "+waTemplateUid+", Exception: "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return coreQuestionsList;
	}
	
	
	/**
	 * @param conditionCd
	 * @param statusCd
	 * @return
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	public Integer getNotificationCountByConditionAndStatus(String conditionCd, String statusCd) throws NEDSSDAOSysException, NEDSSSystemException {
		ArrayList<Object>  queryParams = new ArrayList<Object> ();
		logger.debug("conditionCd: "+conditionCd+", statusCd: "+statusCd);
		MappedFromToQuestionFieldsDT coreQuestionDT = new MappedFromToQuestionFieldsDT();
		try{
			logger.info("Query: "+NOTIFICATION_COUNT_BY_CONDITION_AND_STATUS);
			queryParams.add(conditionCd);
			queryParams.add(statusCd);
			
			Integer notificationByStatus = (Integer) preparedStmtMethod(coreQuestionDT, queryParams, NOTIFICATION_COUNT_BY_CONDITION_AND_STATUS, NEDSSConstants.SELECT_COUNT);
			return notificationByStatus;
		}catch(NEDSSDAOSysException daoSysEx){
			logger.fatal("NEDSSDAOSysException getNotificationCountByConditionAndStatus conditionCd: "+conditionCd+", statusCd: "+statusCd+", Exception: "+daoSysEx.getMessage(), daoSysEx);
			throw new NEDSSDAOSysException(daoSysEx.toString());
		}catch(NEDSSSystemException sysEx){
			logger.fatal("NEDSSSystemException getNotificationCountByConditionAndStatus conditionCd: "+conditionCd+", statusCd: "+statusCd+", Exception: "+sysEx.getMessage(), sysEx);
			throw new NEDSSSystemException(sysEx.toString());
		}catch(Exception ex){
			logger.fatal("Exception getNotificationCountByConditionAndStatus conditionCd: "+conditionCd+", statusCd: "+statusCd+", Exception: "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}
	
	
	/**
	 * @param mappedQueAnsList
	 * @param conditionCdGroupId
	 * @return
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	public boolean insertMappingForConversion(ArrayList<Object> mappedQueAnsList, Long conditionCdGroupId) throws NEDSSDAOSysException, NEDSSSystemException {
			Connection dbConnection = null;
			PreparedStatement preparedStmt = null;
			ResultSet resultSet = null;
			long nbsConversionMappingUid=100000;
			
			logger.debug("conditionCdGroupId: "+conditionCdGroupId);
			try{
				final String selectConversionMapping = "select max(nbs_conversion_mapping_uid) from NBS_conversion_mapping";
				dbConnection = getConnection();
				preparedStmt = dbConnection.prepareStatement(selectConversionMapping);
				resultSet = preparedStmt.executeQuery();
				if(resultSet.next()){
					long maxNbsConversionMappingUid = resultSet.getLong(1);
					if(maxNbsConversionMappingUid<100000){
						nbsConversionMappingUid=100000;
					}
					else{
						nbsConversionMappingUid=maxNbsConversionMappingUid+1;
					}
						
				}
			}catch(NEDSSDAOSysException daoSysEx){
				logger.fatal("NEDSSDAOSysException insertMappingForConversion conditionCdGroupId: "+conditionCdGroupId+", "+daoSysEx.getMessage(), daoSysEx);
				throw new NEDSSDAOSysException(daoSysEx.toString());
			}catch(NEDSSSystemException sysEx){
				logger.fatal("NEDSSSystemException insertMappingForConversion conditionCdGroupId: "+conditionCdGroupId+", "+sysEx.getMessage(), sysEx);
				throw new NEDSSSystemException(sysEx.toString());
			}catch(Exception ex){
				logger.fatal("Exception insertMappingForConversion conditionCdGroupId: "+conditionCdGroupId+", "+ex.getMessage(), ex);
				throw new NEDSSSystemException(ex.toString());
			}finally {
				closeResultSet(resultSet);
				closeStatement(preparedStmt);
				releaseConnection(dbConnection);
			}
			
			try{
				dbConnection = getConnection();
				preparedStmt = dbConnection.prepareStatement(INSERT_NBS_CONVERSION_MAPPING);
				for(int i=0;i<mappedQueAnsList.size();i++){
					MappedFromToQuestionFieldsDT dt = (MappedFromToQuestionFieldsDT) mappedQueAnsList.get(i);
					if(dt.isFieldMappingRequired() && !PortPageUtil.STATUS_AUTO_MAPPED.equals(dt.getStatusCode())){
						preparedStmt.setLong(1, nbsConversionMappingUid);
						preparedStmt.setString(2, dt.getFromCode());
						preparedStmt.setString(3, dt.getFromCodeSetNm());
						preparedStmt.setString(4, dt.getFromDataType());
						preparedStmt.setString(5, dt.getFromQuestionId());
						preparedStmt.setLong(6, conditionCdGroupId);
						preparedStmt.setString(7, dt.getToCode());
						preparedStmt.setString(8, dt.getToCodeSetNm());
						preparedStmt.setString(9, dt.getToDataType());
						preparedStmt.setString(10, dt.getToQuestionId());
						preparedStmt.setString(11, "N");
						preparedStmt.setString(12, dt.getFromDbLocation());
						preparedStmt.setString(13, dt.getToDbLocation());
						preparedStmt.setString(14, dt.getFromLabel());
						preparedStmt.setString(15, dt.getToLabel());
						if(dt.getBlockIdNbr()!=null)
							preparedStmt.setInt(16, dt.getBlockIdNbr().intValue());
						else
							preparedStmt.setNull(16, Types.INTEGER);
						preparedStmt.setString(17, dt.getUnitTypeCd());
						preparedStmt.setString(18, dt.getUnitValue());
						if(dt.getAnswerGroupSeqNbr()!=null)
							preparedStmt.setInt(19, dt.getAnswerGroupSeqNbr());
						else
							preparedStmt.setNull(19, Types.INTEGER);
						preparedStmt.setString(20, dt.getConversionType());
						
						preparedStmt.addBatch();
						nbsConversionMappingUid++;
					}
				}
				int[] insertResult = preparedStmt.executeBatch();
				logger.debug("total insertResult ------------"+insertResult.length);
				return true;
		}catch(NEDSSDAOSysException daoSysEx){
			logger.fatal("NEDSSDAOSysException insertMappingForConversion conditionCdGroupId: "+conditionCdGroupId+", "+daoSysEx.getMessage(), daoSysEx);
			throw new NEDSSDAOSysException(daoSysEx.toString());
		}catch(NEDSSSystemException sysEx){
			logger.fatal("NEDSSSystemException insertMappingForConversion  conditionCdGroupId: "+conditionCdGroupId+", "+sysEx.getMessage(), sysEx);
			throw new NEDSSSystemException(sysEx.toString());
		}catch(Exception ex){
			logger.fatal("Exception insertMappingForConversion conditionCdGroupId: "+conditionCdGroupId+", "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
	}
	
	
	/**
	 * @param conditionCd
	 * @param nbsConversionPageMgmtUid
	 * @return
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	public Long createNBSConversionCondition(String conditionCd, Long nbsConversionPageMgmtUid) throws NEDSSDAOSysException, NEDSSSystemException {
		logger.debug("conditionCd: "+conditionCd+", nbsConversionPageMgmtUid: "+nbsConversionPageMgmtUid);
		final String selectConversionCondition = "select max(condition_cd_group_id) \"condition_cd_group_id\", max(nbs_conversion_condition_uid) \"nbs_conversion_condition_uid\" from NBS_conversion_condition";
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		long nbsConversionConditionUid = 100000;
		
		long conditionCDGroupId = 100000;
		try{
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(selectConversionCondition);
			resultSet = preparedStmt.executeQuery();
			if(resultSet.next()){
				long maxConditionCDGroupId = resultSet.getLong(1);// max of condition_cd_group_id
				long maxNbsConversionConditionUid = resultSet.getLong(2);//max of nbs_conversion_condition_uid
				if(maxNbsConversionConditionUid<100000){
					nbsConversionConditionUid=100000;
				}else{
					nbsConversionConditionUid=maxNbsConversionConditionUid+1;
				}
				if(maxConditionCDGroupId<100000){
					conditionCDGroupId=100000;
				}else{
					conditionCDGroupId=maxConditionCDGroupId+1;
				}
				     
			}
		}catch(NEDSSDAOSysException daoSysEx){
			logger.fatal("NEDSSDAOSysException createNBSConversionCondition, select NBS_conversion_condition - conditionCd: "+conditionCd+", nbsConversionPageMgmtUid: "+nbsConversionPageMgmtUid+", Exception: "+daoSysEx.getMessage(), daoSysEx);
			throw new NEDSSDAOSysException(daoSysEx.toString());
		}catch(NEDSSSystemException sysEx){
			logger.fatal("NEDSSSystemException createNBSConversionCondition, select NBS_conversion_condition -  conditionCd: "+conditionCd+", nbsConversionPageMgmtUid: "+nbsConversionPageMgmtUid+", Exception: "+sysEx.getMessage(), sysEx);
			throw new NEDSSSystemException(sysEx.toString());
		}catch(Exception ex){
			logger.fatal("Exception createNBSConversionCondition, select NBS_conversion_condition - conditionCd: "+conditionCd+", nbsConversionPageMgmtUid: "+nbsConversionPageMgmtUid+", Exception: "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
				
		try{
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(INSERT_NBS_CONVERSION_CONDITION);
			preparedStmt.setLong(1, nbsConversionConditionUid);
			preparedStmt.setString(2, conditionCd);
			preparedStmt.setLong(3, conditionCDGroupId);
			preparedStmt.setLong(4, nbsConversionPageMgmtUid);
			preparedStmt.setString(5, null); // Setting status null initially, once 1st case is ported set status to PORTING_IN_PROGRESS 
			preparedStmt.setTimestamp(6, PortPageUtil.getCurrentTimestamp());
			preparedStmt.setTimestamp(7, PortPageUtil.getCurrentTimestamp());
			preparedStmt.executeUpdate();
			
			return conditionCDGroupId;
		}catch(NEDSSDAOSysException daoSysEx){
			logger.fatal("NEDSSDAOSysException createNBSConversionCondition, Insert NBS_conversion_condition - conditionCd: "+conditionCd+", nbsConversionPageMgmtUid: "+nbsConversionPageMgmtUid+", Exception: "+daoSysEx.getMessage(), daoSysEx);
			throw new NEDSSDAOSysException(daoSysEx.toString());
		}catch(NEDSSSystemException sysEx){
			logger.fatal("NEDSSSystemException createNBSConversionCondition, Insert NBS_conversion_condition - conditionCd: "+conditionCd+", nbsConversionPageMgmtUid: "+nbsConversionPageMgmtUid+", Exception: "+sysEx.getMessage(), sysEx);
			throw new NEDSSSystemException(sysEx.toString());
		}catch(Exception ex){
			logger.fatal("Exception createNBSConversionCondition, Insert NBS_conversion_condition - conditionCd: "+conditionCd+", nbsConversionPageMgmtUid: "+nbsConversionPageMgmtUid+", Exception: "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}finally {
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
	}
	
	
	/**
	 * @param conditionCd
	 * @param nbsConversionPageMgmtUid
	 * @return
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	public Long findConditionCdGroupIdByCondition(String conditionCd, Long nbsConversionPageMgmtUid) throws NEDSSDAOSysException, NEDSSSystemException {
		logger.debug("conditionCd: "+conditionCd+", nbsConversionPageMgmtUid: "+nbsConversionPageMgmtUid);
		final String selectConditionCdGroupId = "select condition_cd_group_id from NBS_conversion_condition where condition_cd=? and NBS_Conversion_Page_Mgmt_uid=?";
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		
		Long conditionCDGroupId=null;
		try{
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(selectConditionCdGroupId);
			preparedStmt.setString(1, conditionCd);
			preparedStmt.setLong(2, nbsConversionPageMgmtUid);
			resultSet = preparedStmt.executeQuery();
			if(resultSet.next()){
				conditionCDGroupId = resultSet.getLong(1);
			}
			logger.debug("conditionCDGroupId: "+conditionCDGroupId);
			
			return conditionCDGroupId;
		}catch(NEDSSDAOSysException daoSysEx){
			logger.fatal("NEDSSDAOSysException findConditionCdGroupIdByCondition, select NBS_conversion_condition - conditionCd: "+conditionCd+", nbsConversionPageMgmtUid: "+nbsConversionPageMgmtUid+", Exception: "+daoSysEx.getMessage(), daoSysEx);
			throw new NEDSSDAOSysException(daoSysEx.toString());
		}catch(NEDSSSystemException sysEx){
			logger.fatal("NEDSSSystemException findConditionCdGroupIdByCondition, select NBS_conversion_condition -  conditionCd: "+conditionCd+", nbsConversionPageMgmtUid: "+nbsConversionPageMgmtUid+", Exception: "+sysEx.getMessage(), sysEx);
			throw new NEDSSSystemException(sysEx.toString());
		}catch(Exception ex){
			logger.fatal("Exception findConditionCdGroupIdByCondition, select NBS_conversion_condition - conditionCd: "+conditionCd+", nbsConversionPageMgmtUid: "+nbsConversionPageMgmtUid+", Exception: "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
	}
	
	
	/**
	 * @param conditionCdGroupId
	 * @return
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	public boolean removeMappingsCreatedForConversion(Long conditionCdGroupId) throws NEDSSDAOSysException, NEDSSSystemException {
		logger.debug("conditionCdGroupId: "+conditionCdGroupId);
		final String selectConditionCdGroupId = "DELETE FROM NBS_conversion_mapping where condition_cd_group_id=?";
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		boolean isMappingRemoved=false;
		try{
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(selectConditionCdGroupId);
			preparedStmt.setLong(1, conditionCdGroupId);
			preparedStmt.executeUpdate();
			
			return isMappingRemoved;
		}catch(NEDSSDAOSysException daoSysEx){
			logger.fatal("NEDSSDAOSysException removeMappingsCreatedForConversion - conditionCdGroupId: "+conditionCdGroupId+", Exception: "+daoSysEx.getMessage(), daoSysEx);
			throw new NEDSSDAOSysException(daoSysEx.toString());
		}catch(NEDSSSystemException sysEx){
			logger.fatal("NEDSSSystemException removeMappingsCreatedForConversion -  conditionCdGroupId: "+conditionCdGroupId+", Exception: "+sysEx.getMessage(), sysEx);
			throw new NEDSSSystemException(sysEx.toString());
		}catch(Exception ex){
			logger.fatal("Exception removeMappingsCreatedForConversion - conditionCdGroupId: "+conditionCdGroupId+", Exception: "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}finally {
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
	}
	
	/**
	 * @param conditionCdGroupId
	 * @return
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	public ArrayList<Object> getNBSConversionMappings(Long conditionCdGroupId) throws NEDSSDAOSysException, NEDSSSystemException {
		NBSConversionMappingDT  nbsConversionMapperrDT  = new NBSConversionMappingDT();
		ArrayList<Object> nbsConversionMappingList  = new ArrayList<Object> ();
		logger.debug("conditionCdGroupId: "+conditionCdGroupId);
		try{
			nbsConversionMappingList.add(conditionCdGroupId);
			nbsConversionMappingList  = (ArrayList<Object> )preparedStmtMethod(nbsConversionMapperrDT, nbsConversionMappingList, SELECT_NBS_CONVERSION_MAPPING, NEDSSConstants.SELECT);
		}catch(NEDSSDAOSysException daoSysEx){
			logger.fatal("NEDSSDAOSysException getNBSConversionMappings conditionCdGroupId: "+conditionCdGroupId+", Exception: "+daoSysEx.getMessage(), daoSysEx);
			throw new NEDSSDAOSysException(daoSysEx.toString());
		}catch(NEDSSSystemException sysEx){
			logger.fatal("NEDSSSystemException getNBSConversionMappings conditionCdGroupId: "+conditionCdGroupId+", Exception: "+sysEx.getMessage(), sysEx);
			throw new NEDSSSystemException(sysEx.toString());
		}catch(Exception ex){
			logger.fatal("Exception getNBSConversionMappings conditionCdGroupId: "+conditionCdGroupId+", Exception: "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return nbsConversionMappingList;
	}
	
	public Map<Long, String> getPublicHealthCaseUidsByCondition(String conditionCd) throws NEDSSDAOSysException, NEDSSSystemException {
		logger.debug("conditionCd: "+conditionCd);
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		Map<Long, String>  phcUidAndLocalIdMap = new HashMap<>();
		logger.debug("conditionCd: "+conditionCd);
		try{
			logger.info("Query: "+SELECT_PUBLIC_HEALTH_CASE_UID_BY_CONDITION);
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(SELECT_PUBLIC_HEALTH_CASE_UID_BY_CONDITION);
			preparedStmt.setString(1, conditionCd);
			resultSet = preparedStmt.executeQuery();
			while(resultSet.next()){
				phcUidAndLocalIdMap.put(resultSet.getLong(1), resultSet.getString(2));
			}
			
		}catch(NEDSSDAOSysException daoSysEx){
			logger.fatal("NEDSSDAOSysException getPublicHealthCaseUidsByCondition conditionCd: "+conditionCd+", Exception: "+daoSysEx.getMessage(), daoSysEx);
			throw new NEDSSDAOSysException(daoSysEx.toString());
		}catch(NEDSSSystemException sysEx){
			logger.fatal("NEDSSSystemException getPublicHealthCaseUidsByCondition conditionCd: "+conditionCd+", Exception: "+sysEx.getMessage(), sysEx);
			throw new NEDSSSystemException(sysEx.toString());
		}catch(Exception ex){
			logger.fatal("Exception getPublicHealthCaseUidsByCondition conditionCd: "+conditionCd+", Exception: "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
		return phcUidAndLocalIdMap;
	}
	
	/**
	 * @param nbsQuestionUidList
	 * @return
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	public Map<Long, String> getQuestionIdentifiersForNbsQuestionUid(ArrayList<Object> nbsQuestionUidList) throws NEDSSDAOSysException, NEDSSSystemException {
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		Map<Long, String>  questionIdentifierAndNbsQuestionUidMap = new HashMap<>();
		try{
			StringBuffer questionInStringBuffer = new StringBuffer("");
			String questionsIn="''";
			for(int i=0;i<nbsQuestionUidList.size();i++){
				questionInStringBuffer=questionInStringBuffer.append(nbsQuestionUidList.get(i)+",");
			}
			if(questionInStringBuffer.length()>0)
				questionsIn = questionInStringBuffer.substring(0,questionInStringBuffer.length()-1);
			
			String query=SELECT_NBS_QUESTION_FOR_QID_QUID.replace("REPLACE_WITH_NBS_QUESTION_UIDS", questionsIn);
			
			
			
			logger.info("selectQuery: "+query);
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(query);
			
			resultSet = preparedStmt.executeQuery();
			while(resultSet.next()){
				questionIdentifierAndNbsQuestionUidMap.put(resultSet.getLong(1), resultSet.getString(2));
			}
				
		}catch(NEDSSDAOSysException daoSysEx){
			logger.fatal("NEDSSDAOSysException getQuestionIdentifiersForNbsQuestionUid : "+daoSysEx.getMessage(), daoSysEx);
			throw new NEDSSDAOSysException(daoSysEx.toString());
		}catch(NEDSSSystemException sysEx){
			logger.fatal("NEDSSSystemException getQuestionIdentifiersForNbsQuestionUid : "+sysEx.getMessage(), sysEx);
			throw new NEDSSSystemException(sysEx.toString());
		}catch(Exception ex){
			logger.fatal("Exception getQuestionIdentifiersForNbsQuestionUid : "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
		return questionIdentifierAndNbsQuestionUidMap;
	}
	
	
	/**
	 * @param questionIdentifierList
	 * @return map of questionIdentifiers and nbsQustionUid i.e. ("INV123", 1234)
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	public Map<String, Long> getNbsQuestionUidsForQuestionIdentifiers(ArrayList<Object> questionIdentifierList, Long waTemplateUid) throws NEDSSDAOSysException, NEDSSSystemException {
		logger.debug("waTemplateUid: "+waTemplateUid);
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		Map<String, Long>  questionIdentifierAndNbsQuestionUidMap = new HashMap<String, Long>();
		try{
			StringBuffer questionInStringBuffer = new StringBuffer("");
			String questionsIn="''";
			for(int i=0;i<questionIdentifierList.size();i++){
				questionInStringBuffer=questionInStringBuffer.append("'"+questionIdentifierList.get(i)+"',");
			}
			if(questionInStringBuffer.length()>0)
				questionsIn = questionInStringBuffer.substring(0,questionInStringBuffer.length()-1);
			
			String selectQuery=SELECT_NBS_QUESTION_UID_FOR_QUESTION_ID.replace("REPLACE_WITH_QUESTION_IDS", questionsIn);

			logger.debug("getNbsQuestionUidsForQuestionIdentifiers selectQuery: "+selectQuery);
			
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(selectQuery);
			preparedStmt.setLong(1, waTemplateUid);
			
			resultSet = preparedStmt.executeQuery();
			while(resultSet.next()){
				questionIdentifierAndNbsQuestionUidMap.put(resultSet.getString(1), resultSet.getLong(2));
			}
				
		}catch(NEDSSDAOSysException daoSysEx){
			logger.fatal("NEDSSDAOSysException getNbsQuestionUidsForQuestionIdentifiers : waTemplateUid: "+waTemplateUid+", "+daoSysEx.getMessage(), daoSysEx);
			throw new NEDSSDAOSysException(daoSysEx.toString());
		}catch(NEDSSSystemException sysEx){
			logger.fatal("NEDSSSystemException getNbsQuestionUidsForQuestionIdentifiers : waTemplateUid: "+waTemplateUid+", "+sysEx.getMessage(), sysEx);
			throw new NEDSSSystemException(sysEx.toString());
		}catch(Exception ex){
			logger.fatal("Exception getNbsQuestionUidsForQuestionIdentifiers : waTemplateUid: "+waTemplateUid+", "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}		
		return questionIdentifierAndNbsQuestionUidMap;
	}
	
	
	/**
	 * @param conditionCd
	 * @param oldWaTemplateUid
	 * @param newWaTemplateUid
	 * @param lastChangeUserId
	 */
	public void updatePageConditionMappingByCondtionCdAndTempalteUid(String conditionCd, Long oldWaTemplateUid, Long newWaTemplateUid, Long lastChangeUserId) throws NEDSSDAOSysException, NEDSSSystemException {
		try{
			logger.debug("conditionCd: "+conditionCd+", oldWaTemplateUid: "+oldWaTemplateUid+", newWaTemplateUid: "+newWaTemplateUid+", lastChangeUserId:"+lastChangeUserId);
			String query = "UPDATE PAGE_COND_MAPPING "+
						   "SET wa_template_uid=? ,last_chg_time=?,last_chg_user_id=? "+ 
						   "WHERE condition_cd = ? and wa_template_uid=?";
			
			//Delete record from page cond mapping if multiple entries exist for same condition.
			PageManagementDAOImpl pageManagementDAOImpl = new PageManagementDAOImpl();
			ArrayList<Object> pageCondMappingColl  =  (ArrayList<Object>)pageManagementDAOImpl.getPageCondMappingDTCollByCond(conditionCd, "INV");
    		if(pageCondMappingColl != null && pageCondMappingColl.size()>=2){
    			PageCondMappingDT pageCondDT = (PageCondMappingDT)pageCondMappingColl.get(0);
	    		if(pageCondDT.getWaTemplateUid().compareTo(newWaTemplateUid)!=0){
	    			pageManagementDAOImpl.deletePageConditionMapping(conditionCd, pageCondDT.getWaTemplateUid());
	    		}
    		}
    		
			ArrayList<Object> paramList = new ArrayList<Object>();
			paramList.add(newWaTemplateUid);
			paramList.add(PortPageUtil.getCurrentTimestamp());
			paramList.add(lastChangeUserId);
			paramList.add(conditionCd);
			paramList.add(oldWaTemplateUid);
			
			preparedStmtMethod(null, paramList, query, NEDSSConstants.UPDATE);

			
    		
		}catch(NEDSSDAOSysException daoSysEx){
			logger.fatal("NEDSSDAOSysException updatePageConditionMappingByCondtionCdAndTempalteUid, conditionCd: "+conditionCd+", oldWaTemplateUid: "+oldWaTemplateUid+", newWaTemplateUid: "+newWaTemplateUid+", lastChangeUserId:"+lastChangeUserId+", Exception: "+daoSysEx.getMessage(), daoSysEx);
			throw new NEDSSDAOSysException(daoSysEx.toString());
		}catch(NEDSSSystemException sysEx){
			logger.fatal("NEDSSSystemException updatePageConditionMappingByCondtionCdAndTempalteUid, conditionCd: "+conditionCd+", oldWaTemplateUid: "+oldWaTemplateUid+", newWaTemplateUid: "+newWaTemplateUid+", lastChangeUserId:"+lastChangeUserId+", Exception: "+sysEx.getMessage(), sysEx);
			throw new NEDSSSystemException(sysEx.toString());
		}catch(Exception ex){
			logger.fatal("Exception updatePageConditionMappingByCondtionCdAndTempalteUid, conditionCd: "+conditionCd+", oldWaTemplateUid: "+oldWaTemplateUid+", newWaTemplateUid: "+newWaTemplateUid+", lastChangeUserId:"+lastChangeUserId+", Exception: "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}
	
	
	/**
	 * @param nbsConversionPageMgmtUid
	 * @param mappingStatusCd
	 * @param lastChangeUserId
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	public void updateNbsConversionPageMgmt(Long nbsConversionPageMgmtUid, String mappingStatusCd, Long lastChangeUserId) throws NEDSSDAOSysException, NEDSSSystemException {
		try{
			logger.debug("nbsConversionPageMgmtUid: "+nbsConversionPageMgmtUid+", mappingStatusCd: "+mappingStatusCd+", lastChgUserId: "+lastChangeUserId);
			String query = "UPDATE nbs_conversion_page_mgmt "+
							"SET mapping_status_cd=?, last_chg_time=?, last_chg_user_id=? "+
							"WHERE NBS_Conversion_Page_Mgmt_uid = ?";
			
			ArrayList<Object> paramList = new ArrayList<Object>();
			paramList.add(mappingStatusCd);
			paramList.add(PortPageUtil.getCurrentTimestamp());
			paramList.add(lastChangeUserId);
			paramList.add(nbsConversionPageMgmtUid);
			
			preparedStmtMethod(null, paramList, query, NEDSSConstants.UPDATE);
			
		}catch(NEDSSDAOSysException daoSysEx){
			logger.fatal("NEDSSDAOSysException updateNbsConversionPageMgmt nbsConversionPageMgmtUid: "+nbsConversionPageMgmtUid+ ", Exception: "+daoSysEx.getMessage(), daoSysEx);
			throw new NEDSSDAOSysException(daoSysEx.toString());
		}catch(NEDSSSystemException sysEx){
			logger.fatal("NEDSSSystemException updateNbsConversionPageMgmt nbsConversionPageMgmtUid: "+nbsConversionPageMgmtUid+ ", Exception: "+sysEx.getMessage(), sysEx);
			throw new NEDSSSystemException(sysEx.toString());
		}catch(Exception ex){
			logger.fatal("Exception updateNbsConversionPageMgmt nbsConversionPageMgmtUid: "+nbsConversionPageMgmtUid+ ", Exception: "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}
	
	
	/**
	 * @param nbsConversionPageMgmtUid
	 * @param statusCd
	 * @param conditionCdGroupId
	 * @param conditionCd
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	public void updateNbsConversionConditionAfterPorting(Long nbsConversionPageMgmtUid, String statusCd, Long conditionCdGroupId, String conditionCd) throws NEDSSDAOSysException, NEDSSSystemException {
		try{
			logger.debug("nbsConversionPageMgmtUid: "+nbsConversionPageMgmtUid+", statusCd: "+statusCd+", conditionCdGroupId: "+conditionCdGroupId+", conditionCd: "+conditionCd);
			String updateQuery = "update NBS_conversion_condition "+
							"set status_cd=?, last_chg_time=? "+
							"where NBS_Conversion_Page_Mgmt_uid=? and condition_cd=?";
			
			ArrayList<Object> paramList = new ArrayList<Object>();
			paramList.add(statusCd);
			paramList.add(PortPageUtil.getCurrentTimestamp());
			paramList.add(nbsConversionPageMgmtUid);
			paramList.add(conditionCd);
			
			preparedStmtMethod(null, paramList, updateQuery, NEDSSConstants.UPDATE);
			
		}catch(NEDSSDAOSysException daoSysEx){
			logger.fatal("NEDSSDAOSysException updateNbsConversionConditionAfterPorting nbsConversionPageMgmtUid: "+nbsConversionPageMgmtUid+", conditionCdGroupId: "+conditionCdGroupId+", conditionCd: "+conditionCd+ ", Exception: "+daoSysEx.getMessage(), daoSysEx);
			throw new NEDSSDAOSysException(daoSysEx.toString());
		}catch(NEDSSSystemException sysEx){
			logger.fatal("NEDSSSystemException updateNbsConversionConditionAfterPorting nbsConversionPageMgmtUid: "+nbsConversionPageMgmtUid+", conditionCdGroupId: "+conditionCdGroupId+", conditionCd: "+conditionCd+ ", Exception: "+sysEx.getMessage(), sysEx);
			throw new NEDSSSystemException(sysEx.toString());
		}catch(Exception ex){
			logger.fatal("Exception updateNbsConversionConditionAfterPorting nbsConversionPageMgmtUid: "+nbsConversionPageMgmtUid+", conditionCdGroupId: "+conditionCdGroupId+", conditionCd: "+conditionCd+ ", Exception: "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}
	
	
	/**
	 * @param conditionCodeGroupId
	 * @param status
	 * @return
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	public ArrayList<Object> getConvertedCasesFromNbsConversionMaster(Long conditionCodeGroupId, String status) throws NEDSSDAOSysException, NEDSSSystemException {
		ArrayList<Object> publicHealthCaseUidList  = new ArrayList<Object> ();
		logger.debug("conditionCodeGroupId: "+conditionCodeGroupId+", status: "+status);
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		logger.info("Query: "+SELECT_CONVERTED_CASES_FROM_NBS_CONVERSION_MASTER);
		try{
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(SELECT_CONVERTED_CASES_FROM_NBS_CONVERSION_MASTER);
			preparedStmt.setLong(1, conditionCodeGroupId);
			preparedStmt.setString(2, status);
			
			resultSet = preparedStmt.executeQuery();
			while(resultSet.next()){
				publicHealthCaseUidList.add(resultSet.getLong(1));
			}
			
		}catch(NEDSSDAOSysException daoSysEx){
			logger.fatal("NEDSSDAOSysException getConvertedCasesFromNbsConversionMaster conditionCodeGroupId: "+conditionCodeGroupId+", Exception: "+daoSysEx.getMessage(), daoSysEx);
			throw new NEDSSDAOSysException(daoSysEx.toString());
		}catch(NEDSSSystemException sysEx){
			logger.fatal("NEDSSSystemException getConvertedCasesFromNbsConversionMaster conditionCodeGroupId: "+conditionCodeGroupId+", Exception: "+sysEx.getMessage(), sysEx);
			throw new NEDSSSystemException(sysEx.toString());
		}catch(Exception ex){
			logger.fatal("Exception getConvertedCasesFromNbsConversionMaster conditionCodeGroupId: "+conditionCodeGroupId+", Exception: "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
		return publicHealthCaseUidList;
	}
	
	
	public Map<String, MappedFromToQuestionFieldsDT> getQuestionsToBeMappedByWaTemplateUid(Long waTemplateUid, Boolean addSNDummyQue){
		try{
			logger.debug("waTemplateUid: "+waTemplateUid);
			Map<String, MappedFromToQuestionFieldsDT>  questionMap = new HashMap<String, MappedFromToQuestionFieldsDT>();
			ArrayList<Object>  questionList = new ArrayList<Object> ();
			
			// Preventing places questions 'NBS290', 'NBS243' from mapping 
			//Setting nbs_ui_component_uid for toNbsUiComponentUid and fromNbsUiCOmponenetUid so that it can be used for both pages.
			String selectQuery ="SELECT question_identifier \"fromQuestionId\", question_nm \"fromLabel\", page.code_set_group_id \"codeSetGroupId\", codeset.code_set_nm \"fromCodeSetNm\",data_location \"fromDbLocation\", data_type \"fromDataType\", wa_ui_metadata_uid \"waUiMetadataUid\", record_status_cd \"recordStatusCd\", question_group_seq_nbr \"questionGroupSeqNbr\", nbs_ui_component_uid \"fromNbsUiComponentUid\", unit_type_cd \"unitTypeCd\", unit_value  \"unitValue\", other_value_ind_cd \"otherInd\", nbs_ui_component_uid \"toNbsUiComponentUid\" "+ 
					"FROM WA_UI_metadata page left outer join  NBS_SRTE..Codeset codeset on page.code_set_group_id = codeset.code_set_group_id "+  
					"WHERE wa_template_uid = ? and (page.data_location like 'NBS_CASE_ANSWER.%' OR (page.data_location LIKE '%_UID' and page.part_type_cd IS NOT NULL)) and question_identifier NOT IN ('NBS290', 'NBS243')";
			
			String queryBasedOnDBType = selectQuery;
			
			
			logger.info("Query: "+queryBasedOnDBType);
			
			questionList.add(waTemplateUid);
			MappedFromToQuestionFieldsDT questionDT = new MappedFromToQuestionFieldsDT();
			
			questionList = (ArrayList<Object>) preparedStmtMethod(questionDT, questionList, queryBasedOnDBType, NEDSSConstants.SELECT);
			
			//For To side of question list add dummy structure numeric questions. Don't add for from side of page.
			if(Boolean.TRUE.equals(addSNDummyQue))
				addDummyQuestionInList(questionList);
			
			for(int i=0;i<questionList.size();i++){
				questionDT = (MappedFromToQuestionFieldsDT) questionList.get(i);
				questionMap.put(questionDT.getFromQuestionId(), questionDT);
			}
			
			return questionMap;
		}catch(NEDSSDAOSysException daoSysEx){
			logger.fatal("NEDSSDAOSysException getQuestionsToBeMappedByWaTemplateUid waTemplateUid: "+waTemplateUid+", Exception: "+daoSysEx.getMessage(), daoSysEx);
			throw new NEDSSDAOSysException(daoSysEx.toString());
		}catch(NEDSSSystemException sysEx){
			logger.fatal("NEDSSSystemException getQuestionsToBeMappedByWaTemplateUid waTemplateUid: "+waTemplateUid+", Exception: "+sysEx.getMessage(), sysEx);
			throw new NEDSSSystemException(sysEx.toString());
		}catch(Exception ex){
			logger.fatal("Exception getQuestionsToBeMappedByWaTemplateUid waTemplateUid: "+waTemplateUid+", Exception: "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}
	
	
	public Map<String, WaUiMetadataDT> getWaUiMetadataByTemplateUidAndQuestionIdentifier(ArrayList<Object> questionIdentiferList, Long waTemplateUid){
		logger.debug("waTemplateUid: "+waTemplateUid);
		ArrayList<Object> waUiMetadataList  = new ArrayList<Object> ();
		Map<String, WaUiMetadataDT>  questionMap = new HashMap<String, WaUiMetadataDT>();
		try{
			String query = "SELECT question_identifier \"questionIdentifier\",unit_type_cd \"unitTypeCd\", unit_value  \"unitValue\" "+
					"FROM WA_UI_metadata waUiMetadata "+
					"WHERE (wa_template_uid = ?) AND (question_identifier IN (REPLACE_WITH_QUESTION_ID))";
			
			StringBuffer questionInStringBuffer = new StringBuffer("");
			String questionsIn="''";
			for(int i=0;i<questionIdentiferList.size();i++){
				questionInStringBuffer=questionInStringBuffer.append("'"+questionIdentiferList.get(i)+"',");
			}
			if(questionInStringBuffer.length()>0)
				questionsIn = questionInStringBuffer.substring(0,questionInStringBuffer.length()-1);
			
			String selectQuery=query.replace("REPLACE_WITH_QUESTION_ID", questionsIn);
			
			String queryBasedOnDBType = selectQuery;
			
			logger.info("Query: "+queryBasedOnDBType);
			
			waUiMetadataList.add(waTemplateUid);
			WaUiMetadataDT waUiMetadataDT = new WaUiMetadataDT();
			
			waUiMetadataList = (ArrayList<Object>) preparedStmtMethod(waUiMetadataDT, waUiMetadataList, queryBasedOnDBType, NEDSSConstants.SELECT);
			
			for(int i=0;i<waUiMetadataList.size();i++){
				waUiMetadataDT = (WaUiMetadataDT) waUiMetadataList.get(i);
				questionMap.put(waUiMetadataDT.getQuestionIdentifier(), waUiMetadataDT);
			}
			
			return questionMap;
		}catch(NEDSSDAOSysException daoSysEx){
			logger.fatal("NEDSSDAOSysException getWaUiMetadataByTemplateUidAndQuestionIdentifier waTemplateUid: "+waTemplateUid+", Exception: "+daoSysEx.getMessage(), daoSysEx);
			throw new NEDSSDAOSysException(daoSysEx.toString());
		}catch(NEDSSSystemException sysEx){
			logger.fatal("NEDSSSystemException getWaUiMetadataByTemplateUidAndQuestionIdentifier waTemplateUid: "+waTemplateUid+", Exception: "+sysEx.getMessage(), sysEx);
			throw new NEDSSSystemException(sysEx.toString());
		}catch(Exception ex){
			logger.fatal("Exception getWaUiMetadataByTemplateUidAndQuestionIdentifier waTemplateUid: "+waTemplateUid+", Exception: "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}
	
	
	public Map<Integer, ArrayList<Object>> getBatchGroupQuestionsByPage(Long waTemplateUid) throws NEDSSDAOSysException, NEDSSSystemException {
		logger.debug("waTemplateUid: "+waTemplateUid);
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		Map<Integer, ArrayList<Object>>  batchGroupMap = new HashMap<Integer, ArrayList<Object>>();
		logger.debug("waTemplateUid: "+waTemplateUid);

		
		try{

			String selectQuery= "select question_group_seq_nbr, question_identifier from WA_UI_metadata "+ 
							"where wa_template_uid=? and question_group_seq_nbr is not null and data_location is not null "+
							"order by question_group_seq_nbr ";

			
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(selectQuery);
			preparedStmt.setLong(1, waTemplateUid);
			
			resultSet = preparedStmt.executeQuery();
			
			while(resultSet.next()){
				int currentGroupSeqNbr = resultSet.getInt(1);
				String questionIdentifier = resultSet.getString(2);
				if (batchGroupMap.get(currentGroupSeqNbr) == null) {
					batchGroupMap.put(currentGroupSeqNbr, new ArrayList<Object>());
				}
				batchGroupMap.get(currentGroupSeqNbr).add(questionIdentifier);
			}
			
		}catch(NEDSSDAOSysException daoSysEx){
			logger.fatal("NEDSSDAOSysException getBatchGroupQuestionsByPage : waTemplateUid: "+waTemplateUid+", Exception: "+daoSysEx.getMessage(), daoSysEx);
			throw new NEDSSDAOSysException(daoSysEx.toString());
		}catch(NEDSSSystemException sysEx){
			logger.fatal("NEDSSSystemException getBatchGroupQuestionsByPage : waTemplateUid: "+waTemplateUid+", Exception: "+sysEx.getMessage(), sysEx);
			throw new NEDSSSystemException(sysEx.toString());
		}catch(Exception ex){
			logger.fatal("Exception getBatchGroupQuestionsByPage : waTemplateUid: "+waTemplateUid+", Exception: "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}		
		return batchGroupMap;
	}
	
	public ArrayList<Object> getConditionByTemplateFormCd(String fromPageFormCd){
		logger.debug("fromPageFormCd:"+fromPageFormCd);
		ArrayList<Object> conditionList  = new ArrayList<Object> ();
		try{
			String selectQuery = "SELECT NBS_SRTE..Condition_code.condition_cd \"conditionCd\", NBS_SRTE..Condition_code.condition_desc_txt \"conditionDescTxt\", NBS_SRTE..Condition_code.port_req_ind_cd \"PortReqIndCd\" "+
					" FROM Page_cond_mapping INNER JOIN "+
					"NBS_SRTE..Condition_code ON Page_cond_mapping.condition_cd = NBS_SRTE..Condition_code.condition_cd where    "+
					"Page_cond_mapping.wa_template_uid =(select wa_template_uid from wa_template where form_cd=? and publish_ind_cd='T') "+ 
					"order by NBS_SRTE..Condition_code.condition_desc_txt";
			
			String queryBasedOnDBType=selectQuery;
			
			logger.info("Query :"+queryBasedOnDBType);
			
			ConditionDT conditionDT = new ConditionDT();
			conditionList.add(fromPageFormCd);
			conditionList  = (ArrayList<Object> )preparedStmtMethod(conditionDT, conditionList, queryBasedOnDBType, NEDSSConstants.SELECT);
			
			return conditionList;
		}catch(NEDSSDAOSysException daoSysEx){
			logger.fatal("NEDSSDAOSysException getConditionByTemplateFormCd : fromPageFormCd: "+fromPageFormCd+", Exception: "+daoSysEx.getMessage(), daoSysEx);
			throw new NEDSSDAOSysException(daoSysEx.toString());
		}catch(NEDSSSystemException sysEx){
			logger.fatal("NEDSSSystemException getConditionByTemplateFormCd : fromPageFormCd: "+fromPageFormCd+", Exception: "+sysEx.getMessage(), sysEx);
			throw new NEDSSSystemException(sysEx.toString());
		}catch(Exception ex){
			logger.fatal("Exception getConditionByTemplateFormCd : fromPageFormCd: "+fromPageFormCd+", Exception: "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}
	
	
	/**
	 * @param eventType
	 * @return count
	 */
	public Integer getEventCountByEventType(String eventType,String conditionCd){
		logger.debug("busObjType / eventType : "+eventType+", conditionCd: "+conditionCd);
		Integer eventCount = null;
		ArrayList<Object> queryParams  = new ArrayList<Object> ();
		try{
			String selectQuery = "";
			if(NEDSSConstants.VACCINATION_BUSINESS_OBJECT_TYPE.equals(eventType)){
				selectQuery = "SELECT count(*) FROM INTERVENTION";
			}else if(NEDSSConstants.INVESTIGATION_BUSINESS_OBJECT_TYPE.equals(eventType)){
				selectQuery = "select COUNT(*) from public_health_case where cd = ? AND case_type_cd = 'I'";
				queryParams.add(conditionCd);
			}
			//else if(){
				//Extend based on different event type.
			//}
			
			eventCount  =  (Integer)preparedStmtMethod(null, queryParams, selectQuery, NEDSSConstants.SELECT_COUNT);
			
			return eventCount;
		}catch(NEDSSDAOSysException daoSysEx){
			logger.fatal("NEDSSDAOSysException getEventCountByEventType : eventType: "+eventType+", Exception: "+daoSysEx.getMessage(), daoSysEx);
			throw new NEDSSDAOSysException(daoSysEx.toString());
		}catch(NEDSSSystemException sysEx){
			logger.fatal("NEDSSSystemException getEventCountByEventType : eventType: "+eventType+", Exception: "+sysEx.getMessage(), sysEx);
			throw new NEDSSSystemException(sysEx.toString());
		}catch(Exception ex){
			logger.fatal("Exception getEventCountByEventType : eventType: "+eventType+", Exception: "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}
	
	
	/**
	 * @return map of intervention_uid and local_id
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	public Map<Long, String> getInterventionUids() throws NEDSSDAOSysException, NEDSSSystemException {
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		Map<Long, String>  interventionUidAndLocalIdMap = new HashMap<>();
		String SELECT_INTERVENTION_UID = "SELECT intervention_uid, local_id from INTERVENTION  order by intervention_uid desc";
		try{
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(SELECT_INTERVENTION_UID);
			resultSet = preparedStmt.executeQuery();
			while(resultSet.next()){
				interventionUidAndLocalIdMap.put(resultSet.getLong(1), resultSet.getString(2));
			}
		}catch(NEDSSDAOSysException daoSysEx){
			logger.fatal("NEDSSDAOSysException getInterventionUids Exception: "+daoSysEx.getMessage(), daoSysEx);
			throw new NEDSSDAOSysException(daoSysEx.toString());
		}catch(NEDSSSystemException sysEx){
			logger.fatal("NEDSSSystemException getInterventionUids Exception: "+sysEx.getMessage(), sysEx);
			throw new NEDSSSystemException(sysEx.toString());
		}catch(Exception ex){
			logger.fatal("Exception getInterventionUids Exception: "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
		return interventionUidAndLocalIdMap;
	}
	
	
	
	/**
	 * @param nbsConversionPageMgmtUid
	 * @return ManagePageDT, holds the values from NBS_conversion_page_mgmt table
	 */
	public ManagePageDT getNBSConversionPageMgmtByUid(Long nbsConversionPageMgmtUid){
		logger.debug("nbsConversionPageMgmtUid : "+nbsConversionPageMgmtUid);
		ManagePageDT managePageDT = new ManagePageDT();
		ArrayList<Object> queryParams  = new ArrayList<Object> ();
		try{
			String selectQuery = "SELECT nbsConversionPageMgmt.NBS_Conversion_Page_Mgmt_uid \"nbsConversionPageMgmtUid\", nbsConversionPageMgmt.map_name \"mapName\", "+
				"toPage.bus_obj_type \"eventTypeCd\", nbsConversionPageMgmt.from_page_form_cd \"fromPageFormCd\", "+
				"nbsConversionPageMgmt.to_page_form_cd \"toPageFormCd\", fromPage.wa_template_uid \"fromPageWaTemplateUid\","+ 
				"toPage.wa_template_uid \"toPageWaTemplateUid\", fromPage.template_nm \"fromPageName\", "+
				"toPage.template_nm \"toPageName\", nbsConversionPageMgmt.mapping_status_cd \"mappingStatusCd\" "+
				"FROM NBS_Conversion_Page_Mgmt nbsConversionPageMgmt LEFT OUTER JOIN "+
				"WA_template fromPage ON nbsConversionPageMgmt.from_page_form_cd = fromPage.form_cd AND fromPage.template_type IN ('Draft', 'Published') "+ 
				"INNER JOIN WA_template toPage ON nbsConversionPageMgmt.to_page_form_cd = toPage.form_cd "+
				"WHERE (toPage.template_type IN ('Draft', 'Published')) and nbsConversionPageMgmt.NBS_Conversion_Page_Mgmt_uid=?";
			
			queryParams.add(nbsConversionPageMgmtUid);
			
			logger.info("Query: "+selectQuery);
			
			ArrayList<Object> list =  (ArrayList<Object> )preparedStmtMethod(managePageDT, queryParams, selectQuery, NEDSSConstants.SELECT);
			Iterator it = list.iterator();
			{
				while(it.hasNext()){
					managePageDT =(ManagePageDT)it.next();
					return managePageDT;
				}
			}
			
		}catch(NEDSSDAOSysException daoSysEx){
			logger.fatal("NEDSSDAOSysException getNBSConversionPageMgmtByUid : nbsConversionPageMgmtUid : "+nbsConversionPageMgmtUid+", Exception: "+daoSysEx.getMessage(), daoSysEx);
			throw new NEDSSDAOSysException(daoSysEx.toString());
		}catch(NEDSSSystemException sysEx){
			logger.fatal("NEDSSSystemException getNBSConversionPageMgmtByUid : nbsConversionPageMgmtUid : "+nbsConversionPageMgmtUid+", Exception: "+sysEx.getMessage(), sysEx);
			throw new NEDSSSystemException(sysEx.toString());
		}catch(Exception ex){
			logger.fatal("Exception getNBSConversionPageMgmtByUid : nbsConversionPageMgmtUid : "+nbsConversionPageMgmtUid+", Exception: "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		
		return managePageDT;
	}
	
	
	/**
	 * @param toPageFormCd
	 * @param fromPageFormCd
	 * @param conditionCd
	 */
	public void updateToPageFormCdInNbsConversionPageMgmt(String toPageFormCd, String fromPageFormCd, String conditionCd){
		logger.debug("toPageFormCd: "+toPageFormCd+", fromPageFormCd: "+fromPageFormCd+", conditionCd: "+conditionCd);
		try{
			String updateQuery = "update nbs_conversion_page_mgmt "+
					"set to_page_form_cd = ? "+
					"where NBS_Conversion_Page_Mgmt.NBS_Conversion_Page_Mgmt_uid in "+
					"(SELECT NBS_Conversion_Page_Mgmt.NBS_Conversion_Page_Mgmt_uid "+
					"FROM     NBS_Conversion_Page_Mgmt LEFT OUTER JOIN "+
					"NBS_conversion_condition ON NBS_Conversion_Page_Mgmt.NBS_Conversion_Page_Mgmt_uid = NBS_conversion_condition.NBS_Conversion_Page_Mgmt_uid "+
					"and NBS_conversion_condition.condition_cd=? WHERE NBS_Conversion_Page_Mgmt.from_page_form_cd=?)";
	
			ArrayList<Object> paramList = new ArrayList<Object>();
			paramList.add(toPageFormCd);
			paramList.add(conditionCd);
			paramList.add(fromPageFormCd);
	
			preparedStmtMethod(null, paramList, updateQuery, NEDSSConstants.UPDATE);
		}catch(NEDSSDAOSysException daoSysEx){
			logger.fatal("NEDSSDAOSysException updateToPageFormCdInNbsConversionPageMgmt : fromPageFormCd : "+fromPageFormCd+", conditionCd: "+conditionCd+", Exception: "+daoSysEx.getMessage(), daoSysEx);
			throw new NEDSSDAOSysException(daoSysEx.toString());
		}catch(NEDSSSystemException sysEx){
			logger.fatal("NEDSSSystemException updateToPageFormCdInNbsConversionPageMgmt : fromPageFormCd : "+fromPageFormCd+", conditionCd: "+conditionCd+", Exception: "+sysEx.getMessage(), sysEx);
			throw new NEDSSSystemException(sysEx.toString());
		}catch(Exception ex){
			logger.fatal("Exception updateToPageFormCdInNbsConversionPageMgmt : fromPageFormCd : "+fromPageFormCd+", conditionCd: "+conditionCd+", Exception: "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}
	
	
	/**
	 * @param fromPageFormCd
	 * @param conditionCd
	 */
	public boolean isLegacyEventPortedToNewPage(String fromPageFormCd, String conditionCd){
		
		logger.debug("FromPageFormCd: "+fromPageFormCd+", ConditionCd: "+conditionCd);
		
		try{
			NBSConversionConditionDT nbsConversionConditionDT = new NBSConversionConditionDT();
			ArrayList<Object> queryParams  = new ArrayList<Object> ();
			
			String selectQuery = "SELECT NBS_conversion_condition.status_cd \"statusCd\", NBS_conversion_condition.NBS_Conversion_Page_Mgmt_uid \"nbsConversionPageMgmtUid\", NBS_conversion_condition.nbs_conversion_condition_uid \"nbsConversionConditionUid\" "+
					" FROM NBS_Conversion_Page_Mgmt "+
					" LEFT OUTER JOIN NBS_conversion_condition ON "+ 
					" NBS_Conversion_Page_Mgmt.NBS_Conversion_Page_Mgmt_uid = NBS_conversion_condition.NBS_Conversion_Page_Mgmt_uid "+
					" and NBS_conversion_condition.condition_cd=? "+ 
					" WHERE   NBS_Conversion_Page_Mgmt.from_page_form_cd=? ";
			queryParams.add(conditionCd);
			queryParams.add(fromPageFormCd);
			
			ArrayList<Object> list =  (ArrayList<Object> )preparedStmtMethod(nbsConversionConditionDT, queryParams, selectQuery, NEDSSConstants.SELECT);
			Iterator it = list.iterator();
			{
				while(it.hasNext()){
					nbsConversionConditionDT =(NBSConversionConditionDT)it.next();
					if(nbsConversionConditionDT!=null && PortPageUtil.NBS_PAGE_MAPPING_STATUS_COMPLETE.equals(nbsConversionConditionDT.getStatusCd())){
						logger.debug("Conversion complete for fromPageFromCd : "+fromPageFormCd+", conditionCd: "+conditionCd);
						return true;
					}
				}
			}
			
			return false;
		}catch(NEDSSDAOSysException daoSysEx){
			logger.fatal("NEDSSDAOSysException isLegacyEventPortedToNewPage : fromPageFromCd : "+fromPageFormCd+", conditionCd: "+conditionCd+", Exception: "+daoSysEx.getMessage(), daoSysEx);
			throw new NEDSSDAOSysException(daoSysEx.toString());
		}catch(NEDSSSystemException sysEx){
			logger.fatal("NEDSSSystemException isLegacyEventPortedToNewPage : fromPageFromCd : "+fromPageFormCd+", conditionCd: "+conditionCd+", Exception: "+sysEx.getMessage(), sysEx);
			throw new NEDSSSystemException(sysEx.toString());
		}catch(Exception ex){
			logger.fatal("Exception isLegacyEventPortedToNewPage : fromPageFromCd : "+fromPageFormCd+", conditionCd: "+conditionCd+", Exception: "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}
	
	
	/**
	 * Add dummy questions for 'Structured Numeric' and 'Coded with Other' mappings
	 * 
	 * @param toFieldList
	 */
	private static void addDummyQuestionInList(ArrayList<Object>  toFieldList){
		try{
			// Structure Numeric Mapping, add dummy question with _UNIT in ToQuesiton Field List.
			
			ArrayList<Object>  unitQuestionList = new ArrayList<Object> ();
			
			CachedDropDownValues cdv = new CachedDropDownValues();
			for(int i=0;i<toFieldList.size();i++){
				MappedFromToQuestionFieldsDT toFieldDT = (MappedFromToQuestionFieldsDT) toFieldList.get(i);
				if(toFieldDT.getUnitTypeCd()!=null && PortPageUtil.DATA_TYPE_CODED.equals(toFieldDT.getUnitTypeCd())){	//Structured Numeric 
					MappedFromToQuestionFieldsDT dt = (MappedFromToQuestionFieldsDT) toFieldDT.deepCopy();
					dt.setFromQuestionId(dt.getFromQuestionId()+PortPageUtil.STRUCTURE_NUMERIC_DUMMY_QUE_SUFFIX);
					dt.setFromDataType(PortPageUtil.DATA_TYPE_CODED);
					if(dt.getUnitValue()!=null){
						dt.setCodeSetGroupId(Long.parseLong(dt.getUnitValue()));
						dt.setFromCodeSetNm(cdv.getTheCodeSetNm(Long.parseLong(dt.getUnitValue())));
						dt.setFromLabel(dt.getFromLabel()+PortPageUtil.STRUCTURE_NUMERIC_DUMMY_QUE_LABEL_SUFFIX);
					}
					unitQuestionList.add(dt);
					logger.info("Adding dummy question (Structure Numeric mapping) for question identifier: "+dt.getFromQuestionId());
				}else if(PortPageUtil.DATA_TYPE_CODED.equals(toFieldDT.getFromDataType()) && PortPageUtil.TRUE_FLAG_STR.equals(toFieldDT.getOtherInd())){ // Coded with Other
					MappedFromToQuestionFieldsDT dt = (MappedFromToQuestionFieldsDT) toFieldDT.deepCopy();
					dt.setFromQuestionId(dt.getFromQuestionId()+PortPageUtil.CODED_WITH_OTHER_DUMMY_QUE_SUFFIX);
					dt.setFromDataType(PortPageUtil.DATA_TYPE_TEXT);
					dt.setFromLabel(dt.getFromLabel()+PortPageUtil.CODED_WITH_OTHER_DUMMY_QUE_LABEL_SUFFIX);
					unitQuestionList.add(dt);
					logger.info("Adding dummy question (Coded with Other mapping) for question identifier: "+dt.getFromQuestionId());
				}
			}
			
			toFieldList.addAll(unitQuestionList);
		}catch(Exception ex){
			logger.fatal("Exception addStructureNumericQuestionInList : "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}
	
	
	/**
	 * @param fromPageInvFormCd
	 * @param fromPageTemplateUid
	 * @return
	 */
	public boolean insertLDFFromNbsUiMetadataToWaUiMetadata(String fromPageInvFormCd, Long fromPageTemplateUid){
		logger.debug("Inserting LDF from fromPageInvFormCd: "+fromPageInvFormCd+",to  fromPageTemplateUid: "+fromPageTemplateUid);
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		boolean isSuccessfulInsert = false;
		try{
			
			String query = "INSERT INTO WA_UI_metadata (wa_template_uid,question_identifier,nbs_ui_component_uid, parent_uid,question_nm, question_label, question_tool_tip,enable_ind,default_value,display_ind,order_nbr,required_ind,add_time, add_user_id, last_chg_time, last_chg_user_id, record_status_cd, record_status_time, max_length, admin_comment, version_ctrl_nbr, field_size, future_date_ind_cd, local_id, code_set_group_id, data_cd, data_location, data_type, data_use_cd, legacy_data_location, part_type_cd, question_group_seq_nbr, question_oid, question_oid_system_txt, question_unit_identifier, repeats_ind_cd, unit_parent_identifier, group_nm, sub_group_nm, desc_txt, mask,min_value,max_value,standard_nnd_ind_cd,unit_value,other_value_ind_cd,batch_table_appear_ind_cd,batch_table_header,batch_table_column_width,coinfection_ind_cd) "+
						   "SELECT ?, NBS_question.question_identifier, NBS_ui_metadata.nbs_ui_component_uid, NBS_ui_metadata.parent_uid, left(NBS_ui_metadata.question_label,50) as question_nm, NBS_ui_metadata.question_label, NBS_ui_metadata.question_tool_tip, NBS_ui_metadata.enable_ind, NBS_ui_metadata.default_value, NBS_ui_metadata.display_ind, NBS_ui_metadata.order_nbr, NBS_ui_metadata.required_ind, NBS_ui_metadata.add_time, NBS_ui_metadata.add_user_id, NBS_ui_metadata.last_chg_time, NBS_ui_metadata.last_chg_user_id, NBS_ui_metadata.record_status_cd, NBS_ui_metadata.record_status_time, NBS_ui_metadata.max_length, NBS_ui_metadata.admin_comment, NBS_ui_metadata.version_ctrl_nbr, NBS_ui_metadata.field_size, NBS_ui_metadata.future_date_ind_cd, NBS_ui_metadata.local_id, NBS_question.code_set_group_id, NBS_ui_metadata.data_cd, 'NBS_CASE_ANSWER.ANSWER_TXT', UPPER(NBS_question.data_type), NBS_ui_metadata.data_use_cd, NBS_ui_metadata.legacy_data_location, NBS_ui_metadata.part_type_cd, NBS_ui_metadata.question_group_seq_nbr, NBS_ui_metadata.question_oid, NBS_ui_metadata.question_oid_system_txt, NBS_ui_metadata.question_unit_identifier, NBS_ui_metadata.repeats_ind_cd, NBS_ui_metadata.unit_parent_identifier, NBS_ui_metadata.group_nm, NBS_ui_metadata.sub_group_nm, NBS_ui_metadata.desc_txt, NBS_ui_metadata.mask,NBS_ui_metadata.min_value,NBS_ui_metadata.max_value,NBS_ui_metadata.standard_nnd_ind_cd,NBS_ui_metadata.unit_value,NBS_ui_metadata.other_value_ind_cd,NBS_ui_metadata.batch_table_appear_ind_cd,NBS_ui_metadata.batch_table_header,NBS_ui_metadata.batch_table_column_width,NBS_ui_metadata.coinfection_ind_cd "+
						   "FROM     NBS_ui_metadata INNER JOIN NBS_question ON NBS_ui_metadata.nbs_question_uid = NBS_question.nbs_question_uid "+
						   "WHERE  NBS_ui_metadata.investigation_form_cd = ? AND NBS_ui_metadata.ldf_page_id IS NOT NULL AND NBS_ui_metadata.nbs_ui_component_uid in (1007,1008,1009,1013) "+
						   "AND NBS_question.question_identifier NOT IN (SELECT question_identifier FROM WA_UI_metadata WHERE wa_template_uid =?) ";	
			
			String queryBasedOnDBType=queryBasedOnDBType=query;
			
			logger.info("queryBasedOnDBType :"+queryBasedOnDBType);
			
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(queryBasedOnDBType);
			preparedStmt.setLong(1, fromPageTemplateUid);
			preparedStmt.setString(2, fromPageInvFormCd);
			preparedStmt.setLong(3, fromPageTemplateUid);
			
			preparedStmt.executeUpdate();
			isSuccessfulInsert = true;
		}catch(NEDSSDAOSysException daoSysEx){
			logger.fatal("NEDSSDAOSysException insertLDFFromNbsUiMetadataToWaUiMetadata: "+daoSysEx.getMessage(), daoSysEx);
			throw new NEDSSDAOSysException(daoSysEx.getMessage(), daoSysEx);
		}catch(NEDSSSystemException sysEx){
			logger.fatal("NEDSSSystemException insertLDFFromNbsUiMetadataToWaUiMetadata: "+sysEx.getMessage(), sysEx);
			throw new NEDSSSystemException(sysEx.getMessage(), sysEx);
		}catch(Exception ex){
			logger.fatal("Exception insertLDFFromNbsUiMetadataToWaUiMetadata: "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.getMessage(), ex);
		}finally {
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
		return isSuccessfulInsert;
	}
	
	
	public ArrayList<Object> getNewlyAddedLDFsForHybridPage(String fromPageInvFormCd, Long fromPageTemplateUid) throws NEDSSDAOSysException, NEDSSSystemException {
		ArrayList<Object>  newlyAddedLDFList = new ArrayList<Object> ();
		ArrayList<Object>  paramList = new ArrayList<Object> ();
		
		logger.debug("fromPageInvFormCd: "+fromPageInvFormCd+",to  fromPageTemplateUid: "+fromPageTemplateUid);
		paramList.add(fromPageInvFormCd);
		paramList.add(fromPageTemplateUid);
		
		MappedFromToQuestionFieldsDT mappedFromToQueFieldsdT = new MappedFromToQuestionFieldsDT();
		try{
			String query = "SELECT  NBS_question.question_identifier \"fromQuestionId\", left(NBS_ui_metadata.question_label,50) \"fromLabel\", NBS_question.code_set_group_id \"codeSetGroupId\", codeset.code_set_nm \"fromCodeSetNm\", NBS_question.data_location \"fromDbLocation\", UPPER(NBS_question.data_type) \"fromDataType\", NBS_ui_metadata.record_status_cd \"recordStatusCd\",  'N' \"autoMapped\", NBS_ui_metadata.question_group_seq_nbr \"questionGroupSeqNbr\", NBS_ui_metadata.unit_type_cd \"unitTypeCd\", NBS_ui_metadata.unit_value \"unitValue\" "+ 
						   "FROM     NBS_ui_metadata INNER JOIN NBS_question ON NBS_ui_metadata.nbs_question_uid = NBS_question.nbs_question_uid "+
						   "left outer join  NBS_SRTE..Codeset codeset on NBS_question.code_set_group_id = codeset.code_set_group_id "+
						   "WHERE  NBS_ui_metadata.investigation_form_cd = ? AND NBS_ui_metadata.ldf_page_id IS NOT NULL AND NBS_ui_metadata.nbs_ui_component_uid in (1007,1008,1009,1013) "+
						   "AND NBS_question.question_identifier NOT IN (SELECT question_identifier FROM WA_UI_metadata WHERE wa_template_uid = ?)";
			
			String selectQuery=query;
			
			
			logger.info("Query : "+selectQuery);
			
			newlyAddedLDFList = (ArrayList<Object>) preparedStmtMethod(mappedFromToQueFieldsdT, paramList, selectQuery, NEDSSConstants.SELECT);
			
		}catch(NEDSSDAOSysException daoSysEx){
			logger.fatal("NEDSSDAOSysException getFieldsFromPageQuestions fromPageInvFormCd: "+fromPageInvFormCd+",to  fromPageTemplateUid: "+fromPageTemplateUid+", Exception: "+daoSysEx.getMessage(), daoSysEx);
			throw new NEDSSDAOSysException(daoSysEx.getMessage(), daoSysEx);
		}catch(NEDSSSystemException sysEx){
			logger.fatal("NEDSSSystemException getFieldsFromPageQuestions fromPageInvFormCd: "+fromPageInvFormCd+",to  fromPageTemplateUid: "+fromPageTemplateUid+", Exception: "+sysEx.getMessage(), sysEx);
			throw new NEDSSSystemException(sysEx.getMessage(), sysEx);
		}catch(Exception ex){
			logger.fatal("Exception getFieldsFromPageQuestions fromPageInvFormCd: "+fromPageInvFormCd+",to  fromPageTemplateUid: "+fromPageTemplateUid+", Exception: "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.getMessage(), ex);
		}
		return newlyAddedLDFList;
	}
	
	
	
	/**
	 * Currently applicable to Varicella two discrete question to one discrete conversion
	 * @param templateNm
	 * @return 
	 */
	public boolean correctDataBeforeConversion(String conditionCd, String mappingType) throws NEDSSDAOSysException{
		logger.debug("conditionCd: "+conditionCd);
		Connection conn = null;
		CallableStatement cs = null;
		boolean success = false;
		try{
			conn = getConnection();
			cs = conn.prepareCall("{call CorrectDataBeforeConversion(?)}");
	        cs.setString(1, conditionCd);
	        cs.execute();
	        success = true;
		}catch (Exception ex) {
			success = false;
			logger.fatal("Error while correctDataBeforeConversion for conditionCd: "+conditionCd+", Exception: "+ex.getMessage(),ex);
			throw new NEDSSDAOSysException(ex.getMessage(), ex);
		} finally {
			closeCallableStatement(cs);
			releaseConnection(conn);
		}
		
		
		//Correcting SRTE codes.
		try{
			String storedProc = "nbs_srte.dbo.CorrectCodesBeforeConversion(?)";

			String storedProcToExecute = storedProc;
					
			conn = getConnection(NEDSSConstants.SRT);
			cs = conn.prepareCall("{call "+storedProcToExecute+"}");
	        cs.setString(1, conditionCd);
	        cs.execute();
	        success = true;
		}catch (Exception ex) {
			success = false;
			logger.fatal("Error while correctDataBeforeConversion (SRTE codes) for conditionCd: "+conditionCd+", Exception: "+ex.getMessage(),ex);
			throw new NEDSSDAOSysException(ex.getMessage(), ex);
		} finally {
			closeCallableStatement(cs);
			releaseConnection(conn);
		}
		
		//For Varicella create vaccination record
		
		if("10030".equals(conditionCd) && PortPageUtil.MAPPING_HYBRID.equals(mappingType)){
			try{
				conn = getConnection();
				cs = conn.prepareCall("{call CreateVaricellaVaccEvent()}");
		        cs.execute();
		        success = true;
			}catch (Exception ex) {
				success = false;
				logger.fatal("Error while Creating vaccination for varicella: "+conditionCd+", Exception: "+ex.getMessage(),ex);
				throw new NEDSSDAOSysException(ex.getMessage(), ex);
			} finally {
				closeCallableStatement(cs);
				releaseConnection(conn);
			}
		}
		
		return success;
	}
	
	
	/**
	 * @param conditionCd
	 * @param conditionCdGroupId
	 * @param nbsConversionPageMgmtUid
	 * @return
	 */
	public NBSConversionConditionDT getNBSConversionConditionByParameters(String conditionCd, Long conditionCdGroupId, Long nbsConversionPageMgmtUid){
		logger.debug("conditionCd:"+conditionCd+", conditionCdGroupId: "+conditionCdGroupId+", nbsConversionPageMgmtUid: "+nbsConversionPageMgmtUid);
		ArrayList<Object> nbsConversionConditionList  = new ArrayList<Object> ();
		NBSConversionConditionDT nbsConversionConditionDT = new NBSConversionConditionDT();
		try{
			String selectQuery = "SELECT nbs_conversion_condition_uid \"nbsConversionConditionUid\",condition_cd \"conditionCd\", condition_cd_group_id  \"conditionCdGroupId\", NBS_Conversion_Page_Mgmt_uid \"nbsConversionPageMgmtUid\", status_cd \"statusCd\", add_time \"addTime\",  last_chg_time \"lastChangeTime\" FROM NBS_conversion_condition WHERE condition_cd=? AND condition_cd_group_id=? AND NBS_Conversion_Page_Mgmt_uid=?";
						
			
			nbsConversionConditionList.add(conditionCd);
			nbsConversionConditionList.add(conditionCdGroupId);
			nbsConversionConditionList.add(nbsConversionPageMgmtUid);
			nbsConversionConditionList  = (ArrayList<Object> )preparedStmtMethod(nbsConversionConditionDT, nbsConversionConditionList, selectQuery, NEDSSConstants.SELECT);
			
			Iterator it = nbsConversionConditionList.iterator();
			{
				while(it.hasNext()){
					nbsConversionConditionDT =(NBSConversionConditionDT)it.next();
					return nbsConversionConditionDT;
				}
			}
			
		}catch(NEDSSDAOSysException daoSysEx){
			logger.fatal("NEDSSDAOSysException getNBSConversionConditionByParameters : conditionCd:"+conditionCd+", conditionCdGroupId: "+conditionCdGroupId+", nbsConversionPageMgmtUid: "+nbsConversionPageMgmtUid+", Exception: "+daoSysEx.getMessage(), daoSysEx);
			throw new NEDSSDAOSysException(daoSysEx.getMessage(), daoSysEx);
		}catch(NEDSSSystemException sysEx){
			logger.fatal("NEDSSSystemException getNBSConversionConditionByParameters : conditionCd:"+conditionCd+", conditionCdGroupId: "+conditionCdGroupId+", nbsConversionPageMgmtUid: "+nbsConversionPageMgmtUid+", Exception: "+sysEx.getMessage(), sysEx);
			throw new NEDSSSystemException(sysEx.getMessage(), sysEx);
		}catch(Exception ex){
			logger.fatal("Exception getNBSConversionConditionByParameters : conditionCd:"+conditionCd+", conditionCdGroupId: "+conditionCdGroupId+", nbsConversionPageMgmtUid: "+nbsConversionPageMgmtUid+", Exception: "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.getMessage(), ex);
		}
		return nbsConversionConditionDT;
	}
	
	
	public boolean postConversionCleanUp(String conditionCd) throws NEDSSDAOSysException{
		logger.debug("conditionCd: "+conditionCd);
		Connection conn = null;
		CallableStatement cs = null;
		boolean success = false;
		try{
			conn = getConnection();
			cs = conn.prepareCall("{call PostConversionCleanUp(?)}");
	        cs.setString(1, conditionCd);
	        cs.execute();
	        success = true;
		}catch (Exception ex) {
			logger.fatal("Error while postConversionCleanUp for conditionCd: "+conditionCd+", Exception: "+ex.getMessage(),ex);
			throw new NEDSSDAOSysException(ex.getMessage(), ex);
		} finally {
			closeCallableStatement(cs);
			releaseConnection(conn);
		}
		
		return success;
	}
}
