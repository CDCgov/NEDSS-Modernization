package gov.cdc.nedss.pagemanagement.wa.dao;

import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.localfields.dt.NbsPageDT;
import gov.cdc.nedss.localfields.dt.NbsQuestionDT;
import gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.helper.PageManagementHelper;
import gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.vo.PageElementVO;
import gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.vo.PageManagementProxyVO;
import gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.vo.WaTemplateVO;
import gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.wa.dao.WaRuleMetadataDaoImpl;
import gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.wa.dt.WaRuleMetadataDT;
import gov.cdc.nedss.pagemanagement.wa.dt.NbsBusObjMetadataDT;
import gov.cdc.nedss.pagemanagement.wa.dt.PageCondMappingDT;
import gov.cdc.nedss.pagemanagement.wa.dt.PageMetadataDT;
import gov.cdc.nedss.pagemanagement.wa.dt.WaAggregatePageElementDT;
import gov.cdc.nedss.pagemanagement.wa.dt.WaNndMetadataDT;
import gov.cdc.nedss.pagemanagement.wa.dt.WaQuestionDT;
import gov.cdc.nedss.pagemanagement.wa.dt.WaRdbMetadataDT;
import gov.cdc.nedss.pagemanagement.wa.dt.WaTemplateDT;
import gov.cdc.nedss.pagemanagement.wa.dt.WaUiMetadataDT;
import gov.cdc.nedss.systemservice.exception.ResultSetUtilsException;
import gov.cdc.nedss.systemservice.uidgenerator.UidClassCodes;
import gov.cdc.nedss.systemservice.uidgenerator.UidGeneratorHelper;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.util.ResultSetUtils;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

import java.io.ByteArrayInputStream;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class PageManagementDAOImpl extends DAOBase {

	private static PropertyUtil propertyUtil = PropertyUtil.getInstance();

	private static final LogUtils logger = new LogUtils(
			PageManagementDAOImpl.class.getName());
	private long waNndMetadataUid = -1;
	private long waQuestionUid = -1;
	private long waRdbMetadataUid = -1;
	private long waTemplateUid = -1;
	private long waUiMetatdataUid = -1;

	private static final String BY_TEMPLATE_UID = "BY_TEMPLATE_UID";
	private static final String BY_TABLE_UID = "BY_TABLE_UID";

	private static final String FIND_WA_NND_METADATA = "SELECT num.wa_nnd_metadata_uid \"waNndMetadataUid\", "
			+ "num.wa_question_uid \"waQuestionUid\", "
			+ "num.wa_template_uid \"waTemplateUid\", "
			+ "num.question_identifier_nnd \"questionIdentifierNnd\", "
			+ "num.question_label_nnd \"questionLabelNnd\", "
			+ "num.question_required_nnd \"questionRequiredNnd\", "
			+ "num.question_data_type_nnd \"questionDataTypeNnd\", "
			+ "num.HL7_segment_field \"hl7SegmentField\", "
			+ "num.order_group_id \"orderGroupId\", "
			+ "num.translation_table_nm \"translationTableNm\", "
			+ "num.question_identifier \"questionIdentifier\", "
			+ "num.xml_path \"xmlPath\", "
			+ "num.xml_tag \"xmlTag\", "
			+ "num.add_time \"addTime\", "
			+ "num.add_user_id \"addUserId\", "
			+ "num.last_chg_time \"lastChgTime\", "
			+ "num.last_chg_user_id \"lastChgUserId\", "
			+ "num.record_status_cd \"recStatusCd\", "
			+ "num.record_status_time \"recStatusTime\", "
			+ "num.xml_data_type \"xmlDataType\", "
			+ "num.part_type_cd \"partTypeCd\", "
			+ "num.repeat_group_seq_nbr \"repeatGroupSeqNbr\", "
			+ "num.question_order_nnd \"questionOrderNnd\" "
			+ "FROM WA_NND_metadata num "
			+ "WHERE num.wa_nnd_metadata_uid = ? ";

	private static final String FIND_WA_QUESTION = "SELECT ques.wa_question_uid \"waQuestionUid\", "
			+ "ques.code_set_group_id \"codeSetGroupId\", "
			+ "ques.data_cd \"dataCd\", "
			+ "ques.data_location \"dataLocation\", "
			+ "ques.question_identifier \"questionIdentifier\", "
			+ "ques.question_oid \"questionOid\", "
			+ "ques.question_oid_system_txt \"questionOidSystemTxt\", "
			+ "ques.question_unit_identifier \"questionUnitIdentifier\", "
			+ "ques.data_type \"dataType\", "
			+ "ques.data_use_cd \"dataUseCd\", "
			+ "ques.question_label \"questionLabel\", "
			+ "ques.question_tool_tip \"questionToolTip\", "
			+ "ques.user_defined_column_nm \"userDefinedColumnNm\", "
			+ "ques.part_type_cd \"partTypeCd\", "
			+ "ques.add_time \"addTime\", "
			+ "ques.add_user_id \"addUserId\", "
			+ "ques.last_chg_time \"lastChgTime\", "
			+ "ques.last_chg_user_id \"lastChgUserId\", "
			+ "ques.default_value \"defaultValue\", "
			+ "ques.part_type_cd \"partTypeCd\", "
			+ "ques.version_ctrl_nbr \"versionCtrlNbr\", "
			+ "ques.unit_parent_identifier  \"unitParentIndentifier\", "
			+ "ques.question_group_seq_nbr \"questionGroupSeqNbr\", "
			+ "ques.future_date_ind_cd \"futureDateIndCd\", "
			+ "ques.legacy_data_location \"legacyDataLocation\", "
			+ "ques.repeats_ind_cd \"repeatsIndCd\", "
			+ "ques.local_id \"localId\", "
			+ "ques.question_nm \"questionNm\", "
			+ "ques.group_nm \"groupNm\", "
			+ "ques.sub_group_nm \"subGroupNm\", "
			+ "ques.desc_txt \"description\", "
			+ "ques.mask \"mask\", "
			+ "ques.field_size \"fieldLength\", "
			+ "ques.nbs_ui_component_uid \"nbsUiComponentUid\", "
			+ "ques.rpt_admin_column_nm \"reportAdminColumnNm\", "
			+ "ques.question_required_nnd \"questionReqNnd\", "
			+ "ques.question_identifier_nnd \"questionIdentifierNnd\", "
			+ "ques.question_label_nnd  \"questionLabelNnd\", "
			+ "ques.nnd_msg_ind \"nndMsgInd\", "
			+ "ques.question_data_type_nnd\"questionDataTypeNnd\", "
			+ "ques.hl7_segment_field \"hl7Segment\", "
			+ "ques.order_group_id \"orderGrpId\", "
			+ "ques.record_status_cd \"recordStatusCd\", "
			+ "ques.record_status_time \"recordStatusTime\", "
			+ "ques.question_type \"questionType\","
			+ "ques.entry_method \"entryMethod\","
			+ "ques.rdb_table_nm \"rdbTableNm\", "
			+ "ques.rdb_column_nm \"rdbcolumnNm\", "
			+ "ques.min_value \"minValue\", "
			+ "ques.max_value \"maxValue\", "
			+ "ques.admin_comment \"adminComment\", "
			+ "ques.standard_question_ind_cd \"standardQuestionIndCd\", "
			+ "ques.unit_type_cd \"unitTypeCd\", "
			+ "ques.unit_value \"unitValue\", "
			+ "ques.other_value_ind_cd \"otherValIndCd\", "
			+ "ques.coinfection_ind_cd \"coInfQuestion\" "
			+ "FROM WA_Question ques ";

	private static final String WITH_QUESTION_UID = "WHERE ques.wa_question_uid = ? ";

	private static final String WITH_QUESTION_IDENTIFIER = "WHERE ques.question_identifier = ? ";
	private static final String WITH_QUESTION_NAME = "WHERE ques.question_nm = ? ";
	private static final String WITH_DM_COLUMN = "WHERE ques.rdb_column_nm = ? ";
	private static final String WITH_USER_DEFINED_COLUMN_NM= "WHERE ques.user_defined_column_nm = ? ";

	private static final String SELECT_WA_QUESTION_COLLECTION = "SELECT ques.wa_question_uid \"waQuestionUid\", "
			+ "ques.code_set_group_id \"codeSetGroupId\", "
			+ "ques.question_identifier \"questionIdentifier\", "
			+ "ques.question_label \"questionLabel\", "
			+ "ques.data_type \"dataType\", "
			+ "ques.data_use_cd \"dataUseCd\", "
			+ "ques.group_nm \"groupNm\", "
			+ "ques.sub_group_nm \"subGroupNm\", "
			+ "ques.coinfection_ind_cd \"coInfQuestion\" "
			+ "FROM WA_Question ques "
			+ "WHERE ques.record_status_cd = " + NEDSSConstants.RECORD_STATUS_Active;

	private static final String FIND_WA_RDB_METADATA = "SELECT rdb.wa_rdb_metadata_uid \"waRdbMetadataUid\", "
			+ "rdb.wa_question_uid \"waQuestionUid\", "
			+ "rdb.wa_template_uid \"waTemplateUid\", "
			+ "rdb.rdb_table_nm \"rdbTableNm\", "
			+ "rdb.user_defined_column_nm \"userDefinedColumnNm\", "
			+ "rdb.rpt_admin_column_nm \"rptAdminColumnNm\", "
			+ "rdb.add_time \"addTime\", "
			+ "rdb.add_user_id \"addUserId\", "
			+ "rdb.last_chg_time \"lastChgTime\", "
			+ "rdb.last_chg_user_id \"lastChgUserId\", "
			+ "rdb.record_status_cd \"recStatusCd\", "
			+ "rdb.record_status_time \"recStatusTime\", "
			+ "rdb.version_ctrl_nbr \"versionCtrlNbr\", "
			+ "rdb.block_pivot_nbr \"dataMartRepeatNbr\", "
			+ "rdb.question_identifier \"question_identifier\" "
			+ "FROM WA_RDB_metadata rdb "
			+ "WHERE rdb.wa_rdb_metadata_uid = ? ";

	private static final String FIND_WA_TEMPLATE = "SELECT tem.wa_template_uid \"waTemplateUid\", "
			+ "tem.template_type \"templateType\", "
			+ "tem.xml_payload \"xmlPayload\", "
			+ "tem.publish_version_nbr \"publishVersionNbr\", "
			+ "tem.form_cd \"formCd\", "
			+ "tem.condition_cd \"conditionCd\", "
			+ "tem.bus_obj_type \"busObjType\", "
			+ "tem.datamart_nm \"dataMartNm\", "
			+ "tem.last_chg_user_id \"lastChgUserId\", "
			+ "tem.last_chg_time \"lastChgTime\", "
			+ "tem.record_status_cd \"recStatusCd\", "
			+ "tem.record_status_time \"recStatusTime\","
			+ "tem.desc_txt \"descTxt\","
			+ "tem.publish_ind_cd \"publishIndCd\","
			+ "tem.template_nm \"templateNm\","
			+ "tem.nnd_entity_identifier \"messageId\","
			+ "tem.version_note \"versionNote\","
			+ "tem.publish_version_nbr \"publishVersionNbr\" "
			+ "FROM WA_template tem "
			+ "WHERE tem.wa_template_uid = ? ";

	private static final String FIND_WA_RDB_METADATA_BY_TEMPLATE = "SELECT rdb.wa_rdb_metadata_uid \"waRdbMetadataUid\","+
			"rdb.wa_template_uid \"waTemplateUid\","+
			"rdb.user_defined_column_nm \"userDefinedColumnNm\","+
			"rdb.record_status_cd \"recStatusCd\","+
			"rdb.record_status_time \"recordStatusTime\","+
			"rdb.add_user_id \"addUserId\","+
			"rdb.last_chg_time \"lastChgTime\","+
			"rdb.last_chg_user_id \"lastChgUserId\","+
			"rdb.local_id \"localId\","+
			"rdb.wa_ui_metadata_uid \"waUiMetadataUid\","+
			"rdb.rdb_table_nm \"rdbTableNm\","+
			"rdb.rpt_admin_column_nm \"rptAdminColumnNm\","+
			"rdb.rdb_column_nm \"rdbcolumNm\","+
			"rdb.question_identifier \"questionIdentifier\","+
			"rdb.block_pivot_nbr \"dataMartRepeatNbr\","+
			"ui.part_type_cd \"partTypeCd\","+
			"ui.block_nm \"blockName\","+
			"ui.data_type \"dataType\","+
			"wa_question.other_value_ind_cd \"otherValueIndCd\","+
			"wa_question.unit_type_cd \"unitTypeCd\""+
			"from wa_rdb_metadata rdb inner join wa_ui_metadata ui on rdb.wa_ui_metadata_UID = ui.wa_ui_metadata_uid "+
			"left join wa_question on rdb.question_identifier = wa_question.question_identifier where ui.wa_template_uid = ?"+
			" and rdb.user_defined_column_nm IS NOT null AND rdb.rdb_table_nm NOT IN ('NOTIFICATION_EVENT','NOTIFICATION','CONDITION','F_PAGE_CASE','CONFIRMATION_METHOD_GROUP','CONFIRMATION_METHOD')"+
			" and (ui.part_type_cd is NULL or (ui.part_type_cd IS NOT NULL AND (rdb.rdb_column_nm LIKE '%_UID%' OR rdb.rdb_column_nm LIKE '%PATIENT%')))";	

	
	private static final String FIND_PAGE_METADATA_BY_TEMPLATE_SQL =
			"SELECT "+
"NBS_ODSE.dbo.WA_template.template_nm AS \"page_nm\", "+
"NBS_ODSE.dbo.WA_UI_metadata.order_nbr,"+
"NBS_ODSE.dbo.WA_UI_metadata.question_identifier,"+
"NBS_ODSE.dbo.WA_UI_metadata.question_nm,"+
"NBS_ODSE.dbo.WA_UI_metadata.question_label,"+
"NBS_ODSE.dbo.WA_UI_metadata.question_type,"+
"NBS_ODSE.dbo.WA_UI_metadata.desc_txt,"+
"NBS_ODSE.dbo.WA_UI_metadata.question_tool_tip,"+
"NBS_ODSE.dbo.WA_UI_metadata.data_type,"+
"NBS_ODSE.dbo.NBS_ui_component.type_cd_desc AS \"ui_display_type\","+
"NBS_SRTE.dbo.Codeset.code_set_nm,"+
"NBS_SRTE.dbo.Codeset.value_set_code,"+
"NBS_SRTE.dbo.Codeset.value_set_nm,"+
"NBS_ODSE.dbo.WA_UI_metadata.enable_ind,"+
"NBS_ODSE.dbo.WA_UI_metadata.display_ind,"+
"NBS_ODSE.dbo.WA_UI_metadata.required_ind,"+
"NBS_ODSE.dbo.WA_UI_metadata.publish_ind_cd,"+
"NBS_ODSE.dbo.WA_UI_metadata.repeats_ind_cd,"+
"NBS_ODSE.dbo.WA_UI_metadata.field_size,"+
"NBS_ODSE.dbo.WA_UI_metadata.max_length,"+
"NBS_ODSE.dbo.WA_UI_metadata.mask,"+
"NBS_ODSE.dbo.WA_UI_metadata.min_value,"+
"NBS_ODSE.dbo.WA_UI_metadata.max_value,"+
"NBS_ODSE.dbo.WA_UI_metadata.default_value,"+
"NBS_ODSE.dbo.WA_UI_metadata.other_value_ind_cd,"+
"NBS_ODSE.dbo.WA_UI_metadata.future_date_ind_cd,"+
"NBS_ODSE.dbo.WA_UI_metadata.unit_type_cd,"+
"NBS_SRTE.dbo.Codeset.code_set_nm,"+
"NBS_ODSE.dbo.WA_UI_metadata.unit_value,"+
"NBS_ODSE.dbo.WA_UI_metadata.question_unit_identifier,"+
"NBS_ODSE.dbo.WA_UI_metadata.unit_parent_identifier,"+
"NBS_ODSE.dbo.WA_UI_metadata.data_location,"+
"NBS_ODSE.dbo.WA_UI_metadata.part_type_cd,"+
"NBS_SRTE.dbo.Participation_type.type_desc_txt AS \"participation_type\","+
"NBS_ODSE.dbo.WA_UI_metadata.data_cd,"+
"NBS_ODSE.dbo.WA_UI_metadata.data_use_cd,"+
"NBS_ODSE.dbo.WA_UI_metadata.standard_question_ind_cd,"+
"NBS_ODSE.dbo.WA_UI_metadata.standard_nnd_ind_cd,"+
"NBS_ODSE.dbo.WA_UI_metadata.coinfection_ind_cd,"+
"NBS_ODSE.dbo.WA_NND_metadata.repeat_group_seq_nbr,"+
"NBS_ODSE.dbo.WA_UI_metadata.question_group_seq_nbr,"+
"NBS_ODSE.dbo.WA_UI_metadata.batch_table_appear_ind_cd,"+
"NBS_ODSE.dbo.WA_UI_metadata.batch_table_column_width,"+
"NBS_ODSE.dbo.WA_UI_metadata.batch_table_header,"+
"NBS_ODSE.dbo.WA_UI_metadata.block_nm,"+
"NBS_ODSE.dbo.WA_RDB_metadata.block_pivot_nbr,"+
"NBS_ODSE.dbo.WA_NND_metadata.question_identifier_nnd,"+
"NBS_ODSE.dbo.WA_NND_metadata.question_label_nnd,"+
"NBS_ODSE.dbo.WA_NND_metadata.question_required_nnd,"+
"NBS_ODSE.dbo.WA_NND_metadata.question_data_type_nnd,"+
"NBS_ODSE.dbo.WA_NND_metadata.hl7_segment_field,"+
"NBS_ODSE.dbo.WA_NND_metadata.order_group_id,"+
"NBS_ODSE.dbo.WA_NND_metadata.question_map,"+
"NBS_ODSE.dbo.WA_NND_metadata.indicator_cd,"+
"NBS_ODSE.dbo.WA_UI_metadata.group_nm,"+
"NBS_ODSE.dbo.WA_UI_metadata.sub_group_nm,"+
"NBS_ODSE.dbo.WA_RDB_metadata.rdb_table_nm,"+
"NBS_ODSE.dbo.WA_RDB_metadata.rdb_column_nm,"+
"NBS_ODSE.dbo.WA_RDB_metadata.user_defined_column_nm AS \"data_mart_column_nm\","+
"NBS_ODSE.dbo.WA_RDB_metadata.rpt_admin_column_nm,"+
"NBS_ODSE.dbo.WA_UI_metadata.admin_comment,"+


"NBS_ODSE.dbo.WA_UI_metadata.nbs_ui_component_uid,"+
"NBS_ODSE.dbo.WA_UI_metadata.code_set_group_id,"+
"NBS_ODSE.dbo.WA_UI_metadata.version_ctrl_nbr,"+
"NBS_ODSE.dbo.WA_UI_metadata.legacy_data_location,"+
"NBS_ODSE.dbo.WA_UI_metadata.entry_method,"+
"NBS_ODSE.dbo.WA_UI_metadata.question_oid,"+
"NBS_ODSE.dbo.WA_UI_metadata.question_oid_system_txt"+

		

" FROM "+/*NBS_SRTE.dbo.Code_value_general WITH (nolock) INNER JOIN "+*/
"NBS_ODSE.dbo.WA_UI_metadata WITH (nolock) "+/* ON Code_value_general.code_desc_txt = NBS_ODSE.dbo.WA_UI_metadata.question_oid */" LEFT OUTER JOIN "+
"NBS_ODSE.dbo.WA_template WITH (nolock) ON NBS_ODSE.dbo.WA_UI_metadata.wa_template_uid = NBS_ODSE.dbo.WA_template.wa_template_uid LEFT OUTER JOIN "+
"NBS_ODSE.dbo.WA_RDB_metadata WITH (nolock) ON NBS_ODSE.dbo.WA_UI_metadata.wa_ui_metadata_uid = NBS_ODSE.dbo.WA_RDB_metadata.wa_ui_metadata_uid LEFT OUTER JOIN "+
"NBS_ODSE.dbo.WA_NND_metadata WITH (nolock) ON NBS_ODSE.dbo.WA_UI_metadata.wa_ui_metadata_uid = NBS_ODSE.dbo.WA_NND_metadata.wa_ui_metadata_uid LEFT OUTER JOIN "+
"NBS_SRTE.dbo.Participation_type WITH (nolock) ON NBS_ODSE.dbo.WA_UI_metadata.part_type_cd = Participation_type.type_cd LEFT OUTER JOIN "+
"NBS_ODSE.dbo.NBS_ui_component WITH (nolock) ON  "+
"NBS_ODSE.dbo.WA_UI_metadata.nbs_ui_component_uid = NBS_ODSE.dbo.NBS_ui_component.nbs_ui_component_uid LEFT OUTER JOIN "+
"NBS_SRTE.dbo.Codeset WITH (nolock) ON NBS_ODSE.dbo.WA_UI_metadata.code_set_group_id = Codeset.code_set_group_id LEFT OUTER JOIN "+
"NBS_SRTE.dbo.Code_value_general WITH (nolock) ON NBS_SRTE.dbo.Code_value_general.code_set_nm = Codeset.code_set_nm "+

"WHERE  (NBS_ODSE.dbo.WA_ui_metadata.wa_template_uid = ?) "+
/*"AND WA_UI_metadata.question_identifier NOT LIKE '%_UI_%' AND WA_UI_metadata.question_identifier NOT LIKE 'MSG%' "+*/
 		
"GROUP BY NBS_ODSE.dbo.WA_template.template_nm , NBS_ODSE.dbo.WA_UI_metadata.order_nbr, NBS_ODSE.dbo.WA_UI_metadata.question_identifier,"+
"NBS_ODSE.dbo.WA_UI_metadata.question_nm, NBS_ODSE.dbo.WA_UI_metadata.desc_txt, NBS_ODSE.dbo.WA_UI_metadata.question_oid,"+
"NBS_ODSE.dbo.WA_UI_metadata.question_label,"+
"NBS_ODSE.dbo.WA_UI_metadata.question_tool_tip, NBS_ODSE.dbo.WA_UI_metadata.data_location, NBS_ODSE.dbo.WA_UI_metadata.data_type, NBS_SRTE.dbo.Codeset.code_set_nm,"+
"NBS_ODSE.dbo.NBS_ui_component.type_cd_desc , NBS_ODSE.dbo.WA_UI_metadata.group_nm, NBS_SRTE.dbo.Participation_type.type_desc_txt ,"+
"NBS_ODSE.dbo.WA_UI_metadata.data_cd, NBS_ODSE.dbo.WA_UI_metadata.data_use_cd, NBS_ODSE.dbo.WA_UI_metadata.sub_group_nm,"+
"NBS_ODSE.dbo.WA_UI_metadata.display_ind, NBS_ODSE.dbo.WA_UI_metadata.enable_ind, NBS_ODSE.dbo.WA_UI_metadata.required_ind,"+
"NBS_ODSE.dbo.WA_UI_metadata.other_value_ind_cd, NBS_ODSE.dbo.WA_UI_metadata.future_date_ind_cd, NBS_ODSE.dbo.WA_UI_metadata.publish_ind_cd,"+
"NBS_ODSE.dbo.WA_UI_metadata.default_value, NBS_ODSE.dbo.WA_UI_metadata.max_length, NBS_ODSE.dbo.WA_UI_metadata.field_size,"+
"NBS_ODSE.dbo.WA_UI_metadata.mask, NBS_ODSE.dbo.WA_UI_metadata.min_value, NBS_ODSE.dbo.WA_UI_metadata.max_value,"+
"NBS_ODSE.dbo.WA_UI_metadata.unit_type_cd, NBS_ODSE.dbo.WA_UI_metadata.unit_value, NBS_ODSE.dbo.WA_UI_metadata.question_unit_identifier,"+
"NBS_ODSE.dbo.WA_UI_metadata.unit_parent_identifier, NBS_ODSE.dbo.WA_UI_metadata.block_nm, NBS_ODSE.dbo.WA_RDB_metadata.block_pivot_nbr,"+
"NBS_ODSE.dbo.WA_RDB_metadata.rdb_table_nm, NBS_ODSE.dbo.WA_RDB_metadata.rdb_column_nm,"+
"NBS_ODSE.dbo.WA_RDB_metadata.user_defined_column_nm , NBS_ODSE.dbo.WA_RDB_metadata.rpt_admin_column_nm,"+
"NBS_ODSE.dbo.WA_NND_metadata.question_identifier_nnd, NBS_ODSE.dbo.WA_NND_metadata.question_label_nnd,"+
"NBS_ODSE.dbo.WA_NND_metadata.question_required_nnd, NBS_ODSE.dbo.WA_NND_metadata.question_data_type_nnd,"+
"NBS_ODSE.dbo.WA_NND_metadata.hl7_segment_field, NBS_ODSE.dbo.WA_NND_metadata.order_group_id, NBS_ODSE.dbo.WA_UI_metadata.admin_comment, "+

"NBS_SRTE.dbo.Codeset.value_set_code,"+
"NBS_SRTE.dbo.Codeset.value_set_nm,"+
"NBS_ODSE.dbo.WA_UI_metadata.repeats_ind_cd,"+
"NBS_ODSE.dbo.WA_UI_metadata.part_type_cd,"+
"WA_UI_metadata.standard_question_ind_cd,"+
"NBS_ODSE.dbo.WA_UI_metadata.standard_nnd_ind_cd,"+
"NBS_ODSE.dbo.WA_UI_metadata.coinfection_ind_cd,"+
"NBS_ODSE.dbo.WA_NND_metadata.repeat_group_seq_nbr,"+
"NBS_ODSE.dbo.WA_UI_metadata.question_group_seq_nbr,"+
"NBS_ODSE.dbo.WA_UI_metadata.batch_table_appear_ind_cd,"+
"NBS_ODSE.dbo.WA_UI_metadata.batch_table_column_width,"+
"NBS_ODSE.dbo.WA_UI_metadata.batch_table_header,"+
"NBS_ODSE.dbo.WA_NND_metadata.question_map,"+

"NBS_ODSE.dbo.WA_UI_metadata.desc_txt,"+
"NBS_ODSE.dbo.WA_UI_metadata.question_type,"+
"NBS_ODSE.dbo.WA_NND_metadata.indicator_cd,"+
"NBS_ODSE.dbo.WA_UI_metadata.nbs_ui_component_uid,"+
"NBS_ODSE.dbo.WA_UI_metadata.code_set_group_id,"+
"NBS_ODSE.dbo.WA_UI_metadata.version_ctrl_nbr,"+
"NBS_ODSE.dbo.WA_UI_metadata.legacy_data_location,"+
"NBS_ODSE.dbo.WA_UI_metadata.entry_method,"+
"NBS_ODSE.dbo.WA_UI_metadata.question_oid,"+
"NBS_ODSE.dbo.WA_UI_metadata.question_oid_system_txt"+
/*
"NBS_SRTE.dbo.Code_value_general.code_short_desc_txt,"+
"NBS_SRTE.dbo.Code_value_general.status_cd,"+
"NBS_SRTE.dbo.Code_value_general.concept_code,"+
"NBS_SRTE.dbo.Code_value_general.concept_nm,"+
"NBS_SRTE.dbo.Code_value_general.concept_preferred_nm,"+
"NBS_SRTE.dbo.Code_value_general.code_system_cd,"+
"NBS_SRTE.dbo.Code_value_general.code_system_desc_txt,"+
"NBS_SRTE.dbo.Code_value_general.concept_type_cd,"+
"NBS_SRTE.dbo.Code_value_general.effective_from_time,"+
"NBS_SRTE.dbo.Code_value_general.effective_to_time,"+
"NBS_SRTE.dbo.Code_value_general.add_time,"+
"NBS_SRTE.dbo.code_value_general.code,"+
"NBS_SRTE.dbo.Code_value_general.code_desc_txt"+*/

" ORDER BY participation_type, NBS_ODSE.dbo.WA_UI_metadata.order_nbr";
	
	
	private static final String FIND_PAGE_VOCABULARY_BY_TEMPLATE_SQL = 
	
	"SELECT distinct COALESCE(NBS_SRTE.dbo.Codeset.code_set_nm, codeset2.code_set_nm) code_set_nm, "+
	"COALESCE(NBS_SRTE.dbo.Codeset.value_set_code,codeset2.value_set_code) value_set_code, "+
	"COALESCE(NBS_SRTE.dbo.Codeset.value_set_nm,codeset2.value_set_nm) value_set_nm, "+
	"COALESCE(NBS_SRTE.dbo.Code_value_general.code,codevaluegeneral2.code) code, "+
	"COALESCE(NBS_SRTE.dbo.Code_value_general.code_desc_txt,codevaluegeneral2.code_desc_txt) code_desc_txt, "+
	"COALESCE(NBS_SRTE.dbo.Code_value_general.code_short_desc_txt,codevaluegeneral2.code_short_desc_txt) code_short_desc_txt, "+
	"COALESCE(NBS_SRTE.dbo.Code_value_general.status_cd,codevaluegeneral2.status_cd) status_cd, "+
	"COALESCE(NBS_SRTE.dbo.Code_value_general.concept_code,codevaluegeneral2.concept_code) concept_code, "+
	"COALESCE(NBS_SRTE.dbo.Code_value_general.concept_nm,codevaluegeneral2.concept_nm) concept_nm, "+
	"COALESCE(NBS_SRTE.dbo.Code_value_general.concept_preferred_nm,codevaluegeneral2.concept_preferred_nm) concept_preferred_nm, "+
	"COALESCE(NBS_ODSE.dbo.WA_UI_metadata.code_set_group_id, codeset2.code_set_group_id) code_set_group_id, "+
	"COALESCE(NBS_SRTE.dbo.Code_value_general.code_system_cd,codevaluegeneral2.code_system_cd) code_system_cd, "+
	"COALESCE(NBS_SRTE.dbo.Code_value_general.code_system_desc_txt,codevaluegeneral2.code_system_desc_txt) code_system_desc_txt, "+
	"COALESCE(NBS_SRTE.dbo.Code_value_general.concept_type_cd,codevaluegeneral2.concept_type_cd) concept_type_cd, "+
	"COALESCE(NBS_SRTE.dbo.Code_value_general.effective_from_time,codevaluegeneral2.effective_from_time) effective_from_time, "+
	"COALESCE(NBS_SRTE.dbo.Code_value_general.effective_to_time,codevaluegeneral2.effective_to_time) effective_to_time, "+
	"NBS_ODSE.dbo.WA_UI_metadata.unit_type_cd,NBS_ODSE.dbo.WA_UI_metadata.unit_type_cd, "+
	"COALESCE(NBS_SRTE.dbo.Code_value_general.add_time,codevaluegeneral2.add_time) add_time "+
	"FROM  NBS_ODSE.dbo.WA_UI_metadata WITH (nolock)  LEFT OUTER JOIN NBS_ODSE.dbo.WA_template WITH (nolock) ON "+
	"NBS_ODSE.dbo.WA_UI_metadata.wa_template_uid = NBS_ODSE.dbo.WA_template.wa_template_uid "+
	"LEFT OUTER JOIN NBS_SRTE.dbo.Codeset WITH (nolock) ON  NBS_ODSE.dbo.WA_UI_metadata.code_set_group_id = Codeset.code_set_group_id  "+
	"LEFT OUTER JOIN NBS_SRTE.dbo.Codeset codeset2 WITH (nolock) ON  NBS_ODSE.dbo.WA_UI_metadata.unit_value = CAST(Codeset2.code_set_group_id AS VARCHAR(10)) AND unit_type_cd = 'CODED' "+
	//ND-29760
	"AND unit_value NOT IN (SELECT CAST(code_set_group_id AS VARCHAR(10)) FROM WA_UI_metadata WHERE wa_template_uid = ? AND code_set_group_id IS NOT NULL)"+
	
	"LEFT OUTER JOIN NBS_SRTE.dbo.code_value_general codevaluegeneral2 WITH (nolock) ON codevaluegeneral2.code_set_nm =  "+
	"codeset2.code_set_nm "+
	"LEFT OUTER JOIN  NBS_SRTE.dbo.code_value_general WITH (nolock) ON NBS_SRTE.dbo.code_value_general.code_set_nm =  "+
	"NBS_SRTE.dbo.Codeset.code_set_nm WHERE  (NBS_ODSE.dbo.WA_UI_metadata.wa_template_uid = ?) "+
	"AND ((NBS_SRTE.dbo.Codeset.code_set_nm is not NULL AND NBS_SRTE.dbo.Codeset.code_set_nm <> 'OUTBREAK_NM') OR (NBS_ODSE.dbo.WA_UI_METADATA.unit_type_cd='CODED' AND codeset2.code_set_nm IS NOT NULL)) GROUP BY NBS_SRTE.dbo.Codeset.code_set_nm, NBS_SRTE.dbo.Codeset.value_set_code, NBS_SRTE.dbo.Codeset.value_set_nm, NBS_SRTE.dbo.Code_value_general.code, NBS_SRTE.dbo.Code_value_general.code_desc_txt, NBS_SRTE.dbo.Code_value_general.code_short_desc_txt, NBS_SRTE.dbo.Code_value_general.status_cd, NBS_SRTE.dbo.Code_value_general.concept_code, NBS_SRTE.dbo.Code_value_general.concept_nm, NBS_SRTE.dbo.Code_value_general.concept_preferred_nm, NBS_ODSE.dbo.WA_UI_metadata.code_set_group_id, NBS_SRTE.dbo.Code_value_general.code_system_cd, NBS_SRTE.dbo.Code_value_general.code_system_desc_txt, NBS_SRTE.dbo.Code_value_general.concept_type_cd, NBS_SRTE.dbo.Code_value_general.effective_from_time, NBS_SRTE.dbo.Code_value_general.effective_to_time, NBS_SRTE.dbo.Code_value_general.add_time, NBS_ODSE.dbo.WA_UI_metadata.order_nbr, NBS_ODSE.dbo.WA_UI_metadata.unit_type_cd, NBS_ODSE.dbo.WA_UI_metadata.unit_value, "+
	"codeset2.code_set_nm, "+
	"codeset2.value_set_code, "+
	"codeset2.value_set_nm, "+
	"codevaluegeneral2.code, "+
	"codevaluegeneral2.code_desc_txt, "+
	"codevaluegeneral2.code_short_desc_txt, "+
	"codevaluegeneral2.status_cd, "+
	"codevaluegeneral2.concept_code, "+
	"codevaluegeneral2.concept_nm, "+
	"codevaluegeneral2.concept_preferred_nm, "+
	"wa_ui_metadata.code_set_group_id, "+
	"codevaluegeneral2.code_system_cd, "+
	"codevaluegeneral2.code_system_desc_txt, "+
	"codevaluegeneral2.concept_type_cd, "+
	"codevaluegeneral2.effective_from_time, "+
	"codevaluegeneral2.effective_to_time, "+
	"codevaluegeneral2.add_time, "+
	"codeset2.code_set_group_id";
	
	

	private static final String FIND_PAGE_QUESTION_VOCABULARY_BY_TEMPLATE_SQL =
			"SELECT distinct "+
					"NBS_ODSE.dbo.WA_template.template_nm AS \"page_nm\", "+
					"NBS_ODSE.dbo.WA_UI_metadata.order_nbr,"+
					"NBS_ODSE.dbo.WA_UI_metadata.question_identifier, "+
					"NBS_ODSE.dbo.WA_UI_metadata.question_label, "+
					"NBS_ODSE.dbo.WA_UI_metadata.data_type, "+
					"NBS_ODSE.dbo.WA_UI_metadata.enable_ind, "+
					"NBS_ODSE.dbo.WA_UI_metadata.display_ind, "+
					"NBS_ODSE.dbo.WA_UI_metadata.other_value_ind_cd, "+	
					"NBS_SRTE.dbo.Codeset.code_set_nm,"+
					"NBS_SRTE.dbo.Codeset.value_set_code,"+
					"NBS_SRTE.dbo.Codeset.value_set_nm,"+
					"NBS_SRTE.dbo.Code_value_general.code,"+
					"NBS_SRTE.dbo.Code_value_general.code_desc_txt,"+
					"NBS_SRTE.dbo.Code_value_general.code_short_desc_txt,"+
					"NBS_SRTE.dbo.Code_value_general.status_cd,"+
					"NBS_SRTE.dbo.Code_value_general.effective_from_time, "+
					"NBS_SRTE.dbo.Code_value_general.effective_to_time, "+
					"NBS_SRTE.dbo.Code_value_general.concept_code,"+
					"NBS_SRTE.dbo.Code_value_general.concept_nm,"+
					"NBS_SRTE.dbo.Code_value_general.concept_preferred_nm,"+
					"NBS_SRTE.dbo.Code_value_general.code_system_cd,"+
					"NBS_SRTE.dbo.Code_value_general.code_system_desc_txt,"+
					"NBS_SRTE.dbo.Code_value_general.concept_type_cd,"+
					"NBS_SRTE.dbo.Code_value_general.add_time"+

" FROM  NBS_ODSE.dbo.WA_UI_metadata WITH (nolock)  LEFT OUTER JOIN NBS_ODSE.dbo.WA_template WITH (nolock) ON "+
"NBS_ODSE.dbo.WA_UI_metadata.wa_template_uid = NBS_ODSE.dbo.WA_template.wa_template_uid "+
/*"LEFT OUTER JOIN NBS_ODSE.dbo.WA_RDB_metadata WITH (nolock) "+
"ON NBS_ODSE.dbo.WA_UI_metadata.wa_ui_metadata_uid = NBS_ODSE.dbo.WA_RDB_metadata.wa_ui_metadata_uid "+*/
"LEFT OUTER JOIN NBS_SRTE.dbo.Codeset WITH (nolock) ON  "+
"NBS_ODSE.dbo.WA_UI_metadata.code_set_group_id = Codeset.code_set_group_id "+
"LEFT OUTER JOIN  NBS_SRTE.dbo.code_value_general WITH (nolock) ON NBS_SRTE.dbo.code_value_general.code_set_nm = NBS_SRTE.dbo.Codeset.code_set_nm "+
"WHERE  (NBS_ODSE.dbo.WA_UI_metadata.wa_template_uid = ?) AND NBS_SRTE.dbo.Codeset.code_set_nm is not NULL AND NBS_SRTE.dbo.Codeset.code_set_nm <> 'OUTBREAK_NM' GROUP BY "+


"NBS_SRTE.dbo.Codeset.code_set_nm, "+
"NBS_SRTE.dbo.Codeset.value_set_code, "+
"NBS_SRTE.dbo.Codeset.value_set_nm, "+
"NBS_SRTE.dbo.Code_value_general.code, "+
"NBS_SRTE.dbo.Code_value_general.code_desc_txt, "+
"NBS_SRTE.dbo.Code_value_general.code_short_desc_txt, "+
"NBS_SRTE.dbo.Code_value_general.status_cd, "+
"NBS_SRTE.dbo.Code_value_general.concept_code, "+
"NBS_SRTE.dbo.Code_value_general.concept_nm, "+
"NBS_SRTE.dbo.Code_value_general.concept_preferred_nm, "+
"NBS_ODSE.dbo.WA_UI_metadata.code_set_group_id, "+
"NBS_SRTE.dbo.Code_value_general.code_system_cd, "+
"NBS_SRTE.dbo.Code_value_general.code_system_desc_txt, "+
"NBS_SRTE.dbo.Code_value_general.concept_type_cd, "+
"NBS_SRTE.dbo.Code_value_general.effective_from_time, "+
"NBS_SRTE.dbo.Code_value_general.effective_to_time, "+
"NBS_SRTE.dbo.Code_value_general.add_time, "+
"NBS_ODSE.dbo.WA_UI_metadata.order_nbr, "+
"NBS_ODSE.dbo.WA_template.template_nm, "+
"NBS_ODSE.dbo.WA_UI_metadata.question_identifier, "+
"NBS_ODSE.dbo.WA_UI_metadata.question_label, "+
"NBS_ODSE.dbo.WA_UI_metadata.data_type, "+
"NBS_ODSE.dbo.WA_UI_metadata.enable_ind, "+
"NBS_ODSE.dbo.WA_UI_metadata.display_ind, "+
"NBS_ODSE.dbo.WA_UI_metadata.other_value_ind_cd ";
	
	
private static final String FIND_ALL_PUBLISHED_PAGES_SQL = "SELECT wa_template_uid \"waTemplateUid\", template_nm \"templateNm\" FROM NBS_ODSE.DBO.wa_template where template_type = 'Published' order by 1 desc";
private static final String FIND_PAGES_BY_TEMPLATE_NM_SQL = "SELECT wa_template_uid \"waTemplateUid\", template_nm \"templateNm\" FROM NBS_ODSE.DBO.wa_template where template_type = 'Published' ";


	
	private static final String FIND_WA_TEMPLATE_BY_TEMPLATE_NM = "SELECT tem.wa_template_uid \"waTemplateUid\", "
		+ "tem.template_type \"templateType\", "
		+ "tem.template_nm \"templateNm\", "
		+ "tem.xml_payload \"xmlPayload\", "
		+ "tem.publish_version_nbr \"publishVersionNbr\", "
		+ "tem.form_cd \"formCd\", "
		+ "tem.condition_cd \"conditionCd\", "
		+ "tem.bus_obj_type \"busObjType\", "
		+ "tem.datamart_nm \"dataMartNm\", "
		+ "tem.last_chg_user_id \"lastChgUserId\", "
		+ "tem.last_chg_time \"lastChgTime\", "
		+ "tem.record_status_cd \"recStatusCd\", "
		+ "tem.record_status_time \"recStatusTime\","
		+ "tem.desc_txt \"descTxt\","
		+ "tem.publish_ind_cd \"publishIndCd\","
		+ "tem.version_note \"versionNote\","
		+ "tem.publish_version_nbr \"version\" "
		+ "FROM WA_template tem "
		+ "WHERE tem.template_nm = ? " ;
	
	private static final String FIND_WA_TEMPLATE_BY_DATAMART_NM = "SELECT tem.wa_template_uid \"waTemplateUid\", "
			+ "tem.template_type \"templateType\", "
			+ "tem.template_nm \"templateNm\", "
			+ "tem.xml_payload \"xmlPayload\", "
			+ "tem.publish_version_nbr \"publishVersionNbr\", "
			+ "tem.form_cd \"formCd\", "
			+ "tem.condition_cd \"conditionCd\", "
			+ "tem.bus_obj_type \"busObjType\", "
			+ "tem.datamart_nm \"dataMartNm\", "
			+ "tem.last_chg_user_id \"lastChgUserId\", "
			+ "tem.last_chg_time \"lastChgTime\", "
			+ "tem.record_status_cd \"recStatusCd\", "
			+ "tem.record_status_time \"recStatusTime\","
			+ "tem.desc_txt \"descTxt\","
			+ "tem.publish_ind_cd \"publishIndCd\","
			+ "tem.version_note \"versionNote\","
			+ "tem.publish_version_nbr \"version\" "
			+ "FROM WA_template tem "
			+ "WHERE tem.datamart_nm = ? " ;
	
	private static final String FIND_WA_TEMPLATE_BY_CONDITION = "SELECT tem.wa_template_uid \"waTemplateUid\", "
		+ "tem.template_type \"templateType\", "
		+ "tem.template_nm \"templateNm\", "
		+ "tem.xml_payload \"xmlPayload\", "
		+ "tem.publish_version_nbr \"publishVersionNbr\", "
		+ "tem.form_cd \"formCd\", "
		+ "tem.condition_cd \"conditionCd\", "
		+ "tem.bus_obj_type \"busObjType\", "
		+ "tem.datamart_nm \"dataMartNm\", "
		+ "tem.last_chg_user_id \"lastChgUserId\", "
		+ "tem.last_chg_time \"lastChgTime\", "
		+ "tem.record_status_cd \"recStatusCd\", "
		+ "tem.record_status_time \"recStatusTime\","
		+ "tem.desc_txt \"descTxt\","
		+ "tem.publish_ind_cd \"publishIndCd\","
		+ "tem.version_note \"versionNote\","
		+ "tem.publish_version_nbr \"version\" "
		+ "FROM WA_template tem , Page_Cond_Mapping pcm "
		+ "WHERE tem.wa_template_uid = pcm.wa_template_uid " 
		+ " and pcm.condition_cd = ?";
	
	private static final String  templateWhereClause = 	 " and tem.template_type = ? "; 
	private static final String  nonTemplateWhereClause = 	 " and tem.template_type != '"+NEDSSConstants.TEMPLATE+"' ";
	private static final String  PublishedWhereClause = " and tem.template_type in('Published With Draft','Published')";
	private static final String  templateBusObjWhereClause = 	 " and tem.bus_obj_type = ? "; 

	private static final String FIND_WA_TEMPLATE_UID_BY_CONDITION = "SELECT wa_template_uid FROM WA_template WHERE condition_cd = ? ";
	
	private static final String FIND_WA_TEMPLATE_UID_BY_PAGENM = "SELECT wa_template_uid FROM WA_template WHERE template_nm = ? ";
	
	private static final String INACTIVATE_LDF_PAGESET_ID_1 = "update nbs_srte..LDF_page_set set status_cd='I' where business_object_nm=?";
	
	private static final String INACTIVATE_LDF_PAGESET_ID_2 = " and condition_cd=?";
	
	private static final String FIND_WA_UI_METADATA = "SELECT uim.wa_ui_metadata_uid \"waUiMetadataUid\", "
			+ "uim.wa_template_uid \"waTemplateUid\", "
			+ "uim.nbs_ui_component_uid \"nbsUiComponentUid\", "
			+ "uim.wa_question_uid \"waQuestionUid\", "
			+ "uim.parent_uid \"parentUid\", "
			+ "uim.question_label \"questionLabel\", "
			+ "uim.question_tool_tip \"questionToolTip\", "
			+ "uim.enable_ind \"enableInd\", "
			+ "uim.default_value \"defaultValue\", "
			+ "uim.display_ind \"displayInd\", "
			+ "uim.order_nbr \"orderNbr\", "
			+ "uim.required_ind \"requiredInd\", "
			// +"uim.tab_order_id \"tabOrderId\", "
			// +"uim.tab_name \"tabName\", "
			+ "uim.add_time \"addTime\", "
			+ "uim.add_user_id \"addUserId\", "
			+ "uim.last_chg_time \"lastChgTime\", "
			+ "uim.last_chg_user_id \"lastChgUserId\", "
			+ "uim.record_status_cd \"recordStatusCd\", "
			+ "uim.record_status_time \"recordStatusTime\", "
			+ "uim.max_length \"maxLength\", "
			// +"uim.css_style \"cssStyle\", "
			+ "uim.version_ctrl_nbr \"versionCtrlNbr\", "
			+ "uim.admin_comment \"adminComment\", "
			+ "uim.field_size \"fieldSize\", "
			+ "uim.future_date_ind_cd \"futureDateIndCd\", "
			+ "uim.coinfection_ind_cd \"coinfectionIndCd\", "
			+ "uim.local_id \"localId\", "
			+ "uim.block_nm \"blockName\", "
			
			+ "FROM WA_UI_metadata uim "
			+ "WHERE uim.wa_ui_metadata_uid = ? ";

	private static final String DELETE_D_PATIENT = "DELETE FROM wa_rdb_metadata WHERE question_identifier in"+
			"('DEM167_W','DEM160_W','DEM161_W','DEM162_W','DEM163_W','DEM175_W','DEM159_W','DEM168_W','DEM165_W')";
	
	
	private static final String FIND_PAGE_ELEMENT_DETAILS = "SELECT ui.wa_ui_metadata_uid \"waUiMetadataWaUiMetadataUid\", "
			+ "ui.wa_template_uid \"waUiMetadataWaTemplateUid\", "
			+ "ui.nbs_ui_component_uid \"waUiMetadataNbsUiComponentUid\", "
			+
			// "ui.wa_question_uid \"waUiMetadataWaQuestionUid\", " +
			"ui.parent_uid \"waUiMetadataParentUid\", "
			+ "ui.question_label \"waUiMetadataQuestionLabel\", "
			+ "ui.question_tool_tip \"waUiMetadataQuestionToolTip\", "
			+ "ui.enable_ind \"waUiMetadataEnableInd\", "
			+ "ui.default_value \"waUiMetadataDefaultValue\", "
			+ "ui.display_ind \"waUiMetadataDisplayInd\", "
			+ "ui.order_nbr \"waUiMetadataOrderNbr\", "
			+ "ui.required_ind \"waUiMetadataRequiredInd\", "
			+ "ui.question_type \"waUiMetadataQuestionType\", "
            + "ui.entry_method \"waUiMetadataEntryMethod\", "
			+
			// "ui.tab_order_id \"waUiMetadataTabOrderId\", " +
			// "ui.tab_name \"waUiMetadataTabName\", " +
			"ui.add_time \"waUiMetadataAddTime\", "
			+ "ui.add_user_id \"waUiMetadataAddUserId\", "
			+ "ui.last_chg_time \"waUiMetadataLastChgTime\", "
			+ "ui.last_chg_user_id \"waUiMetadataLastChgUserId\", "
			+ "ui.record_status_cd \"waUiMetadataRecordStatusCd\", "
			+ "ui.record_status_time \"waUiMetadataRecordStatusTime\", "
			+ "ui.max_length \"waUiMetadataMaxLength\", "
			+ "ui.admin_comment \"waUiMetadataAdminComment\", "
			+ "ui.version_ctrl_nbr \"waUiMetadataVersionCtrlNbr\", "
			+ "ui.field_size \"waUiMetadataFieldSize\", "
			+ "ui.future_date_ind_cd \"waUiMetadataFutureDateIndCd\", "
			+ "ui.local_id \"waUiMetadataLocalId\", "
			+ "ui.code_set_group_id \"codeSetGroupId\", "
			+ "ui.question_identifier \"questionIdentifier\", "
			+ "ui.data_type \"waUiMetadataDataType\", "
			+ "ui.mask \"waUiMetadataMask\", "
			+ "ui.standard_nnd_ind_cd \"standardNndIndCd\", "
			+ "ui.question_unit_identifier \"questionUnitIdentifier\", "
			+ "ui.unit_parent_identifier \"unitParentIdentifier\", "
			+ "ui.part_type_cd \"partTypeCd\", "
			+ "ui.data_cd \"dataCd\", "
			+ "ui.legacy_data_location \"legacyDataLocation\", "
			+ "ui.data_location \"dataLocation\", "
			+ "ui.data_use_cd \"dataUseCd\", "
			+ "ui.question_oid \"questionOid\", "
			+ "ui.question_oid_system_txt \"questionOidSystemTxt\", "
			+ "ui.repeats_ind_cd \"repeatsIndCd\", "
			+ "ui.group_nm \"groupNm\", "
			+ "ui.sub_group_nm \"subGroupNm\", "
			+ "ui.desc_txt \"descTxt\", "
			+ "ui.standard_question_ind_cd \"standardQuestionIndCd\", "
			+ "ui.question_nm \"questionNm\", "
			+ "ui.min_value \"minValue\", "
			+ "ui.max_value \"maxValue\", "
			+ "ui.unit_Type_cd \"unitTypeCd\", "
			+ "ui.unit_value \"unitValue\", "
			+ "ui.other_value_ind_cd \"otherValIndCd\", "
			+ "ui.publish_ind_cd \"publishIndCd\", "
			+ "ui.question_group_seq_nbr \"waUiMetadataQueGrpSeqNbr\", "
			+ "ui.batch_table_column_width \"batchTableColumnWidth\", "
			+ "ui.batch_table_header \"batchTableHeader\", "
			+ "ui.batch_table_appear_ind_cd \"batchTableAppearIndCd\", "
			+ "ui.coinfection_ind_cd \"waUiMetadataCoinfectionIndCd\", "
			+ "ui.block_nm \"waUiMetadataBlockName\", "
			+ "rdb.block_pivot_nbr \"waRdbMetadataDataMartRepeatNbr\", "
			+ "rdb.wa_rdb_metadata_uid \"waRdbWaRdbMetadataUid\", "
			+ "rdb.wa_ui_metadata_uid \"waRdbWaUiMetadataUid\", "
			+ "rdb.wa_template_uid \"waRdbWaTemplateUid\", "
			+ "rdb.rdb_table_nm \"waRdbRdbTableNm\", "
			+ "rdb.rdb_column_nm \"waRdbRdbColumnNm\", "
			+ "rdb.question_identifier \"waRdbQuestionIdentifier\", "
			+ "rdb.user_defined_column_nm \"waUserDefinedColumnNm\", "
			+ "rdb.rpt_admin_column_nm \"waRdbRptAdminColumnNm\", "
			+ "rdb.record_status_cd \"waRdbRecStatusCd\", "
			+ "rdb.record_status_time \"waRdbRecStatusTime\", "
			+ "rdb.add_user_id \"waRdbAddUserId\", "
			+ "rdb.add_time \"waRdbAddTime\", "
			+ "rdb.last_chg_time \"waRdbLastChgTime\", "
			+ "rdb.last_chg_user_id \"waRdbLastChgUserId\", "
			+ "rdb.local_id AS \"waRdbLocalId\", "
			+ "nnd.wa_nnd_metadata_uid \"waNndWaNndMetadataUid\", "
			+ "nnd.wa_ui_metadata_uid \"waNndWaUiMetadataUid\", "
			+ "nnd.wa_template_uid \"waNndWaTemplateUid\", "
			+ "nnd.question_identifier_nnd \"waNndQuestionIdentifierNnd\", "
			+ "nnd.question_label_nnd \"waNndQuestionLabelNnd\", "
			+ "nnd.question_required_nnd \"waNndQuestionRequiredNnd\", "
			+ "nnd.question_data_type_nnd \"waNndQuestionDataTypeNnd\", "
			+ "nnd.HL7_segment_field \"waNndHl7SegmentField\", "
			+ "nnd.order_group_id \"waNndOrderGroupId\", "
			+ "nnd.translation_table_nm \"waNndTranslationTableNm\", "
			+ "nnd.add_time \"waNndAddTime\", "
			+ "nnd.add_user_id \"waNndAddUserId\", "
			+ "nnd.last_chg_time \"waNndLastChgTime\", "
			+ "nnd.last_chg_user_id \"waNndLastChgUserId\", "
			+ "nnd.record_status_cd \"waNndRecStatusCd\", "
			+ "nnd.record_status_time \"waNndRecStatusTime\", "
			+ "nnd.question_identifier \"waNndQuestionIdentifier\", "
			+ "nnd.xml_path \"waNndXmlPath\", "
			+ "nnd.xml_tag \"waNndXmlTag\", "
			+ "nnd.xml_data_type \"waNndXmlDataType\", "
			+ "nnd.part_type_cd \"waNndPartTypeCd\", "
			+ "nnd.repeat_group_seq_nbr \"waNndRepeatGroupSeqNbr\", "
			+ "nnd.question_order_nnd \"waNndQuestionOrderNnd\", "
			+ "nnd.local_id AS \"waNndLocalId\", "
			+ "nnd.question_map AS \"waNndQuestionMap\", "
			+ "nnd.indicator_cd AS \"waNndIndicatorCd\" "
			+ "FROM  WA_UI_metadata ui "
			+ " LEFT OUTER JOIN WA_RDB_metadata rdb "
			+ "ON ui.wa_template_uid = rdb.wa_template_uid "
			+ "AND ui.wa_ui_metadata_uid =  rdb.wa_ui_metadata_uid "
			+ "LEFT OUTER JOIN WA_NND_metadata nnd  "
			+ "ON ui.wa_template_uid = nnd.wa_template_uid "
			+ "AND ui.wa_ui_metadata_uid =  nnd.wa_ui_metadata_uid "
			+ "WHERE (ui.wa_template_uid = ?) " + "ORDER BY ui.order_nbr";

	private static final String FIND_PAGE_ELEMENT_DETAILS_FOR_PAGE = "SELECT ui.wa_ui_metadata_uid \"waUiMetadataWaUiMetadataUid\", "
			+ "ui.wa_template_uid \"waUiMetadataWaTemplateUid\", "
			+ "ui.nbs_ui_component_uid \"waUiMetadataNbsUiComponentUid\", "
			+
			// "ui.wa_question_uid \"waUiMetadataWaQuestionUid\", " +
			"ui.parent_uid \"waUiMetadataParentUid\", "
			+ "ui.question_label \"waUiMetadataQuestionLabel\", "
			+ "ui.question_tool_tip \"waUiMetadataQuestionToolTip\", "
			+ "ui.enable_ind \"waUiMetadataEnableInd\", "
			+ "ui.default_value \"waUiMetadataDefaultValue\", "
			+ "ui.display_ind \"waUiMetadataDisplayInd\", "
			+ "ui.order_nbr \"waUiMetadataOrderNbr\", "
			+ "ui.required_ind \"waUiMetadataRequiredInd\", "
			+ "ui.question_type \"waUiMetadataQuestionType\", "
			+ "ui.entry_method \"waUiMetadataEntryMethod\", "
			+
			// "ui.tab_order_id \"waUiMetadataTabOrderId\", " +
			// "ui.tab_name \"waUiMetadataTabName\", " +
			"ui.add_time \"waUiMetadataAddTime\", "
			+ "ui.add_user_id \"waUiMetadataAddUserId\", "
			+ "ui.last_chg_time \"waUiMetadataLastChgTime\", "
			+ "ui.last_chg_user_id \"waUiMetadataLastChgUserId\", "
			+ "ui.record_status_cd \"waUiMetadataRecordStatusCd\", "
			+ "ui.record_status_time \"waUiMetadataRecordStatusTime\", "
			+ "ui.max_length \"waUiMetadataMaxLength\", "
			// "ui.css_style \"waUiMetadataCssStyle\", " +
			+ "ui.admin_comment \"waUiMetadataAdminComment\", "
			+ "ui.version_ctrl_nbr \"waUiMetadataVersionCtrlNbr\", "
			+ "ui.field_size \"waUiMetadataFieldSize\", "
			+ "ui.future_date_ind_cd \"waUiMetadataFutureDateIndCd\", "
			+ "ui.local_id \"waUiMetadataLocalId\", "
			+ "ui.coinfection_ind_cd \"waUiMetadataCoinfectionIndCd\", "
			+ "ui.code_set_group_id \"codeSetGroupId\", "
			+ "ui.question_identifier \"questionIdentifier\", "
			+ "ui.data_type \"waUiMetadataDataType\", "
			+ "ui.mask \"waUiMetadataMask\", "
			+ "ui.standard_nnd_ind_cd \"standardNndIndCd\", "
			+ "ui.question_unit_identifier \"questionUnitIdentifier\", "
			+ "ui.unit_parent_identifier \"unitParentIdentifier\", "
			+ "ui.part_type_cd \"partTypeCd\", "
			+ "ui.data_cd \"dataCd\", "
			+ "ui.legacy_data_location \"legacyDataLocation\", "
			+ "ui.data_location \"dataLocation\", "
			+ "ui.data_use_cd \"dataUseCd\", "
			+ "ui.question_oid \"questionOid\", "
			+ "ui.question_oid_system_txt \"questionOidSystemTxt\", "
			+ "ui.repeats_ind_cd \"repeatsIndCd\", "
			+ "ui.group_nm \"groupNm\", "
			+ "ui.sub_group_nm \"subGroupNm\", "
			+ "ui.desc_txt \"descTxt\", "
			+ "ui.standard_question_ind_cd \"standardQuestionIndCd\", "
			+ "ui.question_nm \"questionNm\", "
			+ "ui.min_value \"minValue\", "
			+ "ui.max_value \"maxValue\", "
			+ "ui.unit_Type_cd \"unitTypeCd\", "
			+ "ui.unit_value \"unitValue\", "
			+ "ui.other_value_ind_cd \"otherValIndCd\", "
			+ "ui.publish_ind_cd \"publishIndCd\", "
			+ "ui.question_group_seq_nbr \"waUiMetadataQueGrpSeqNbr\", "
			+ "ui.batch_table_column_width \"batchTableColumnWidth\", "
			+ "ui.batch_table_header \"batchTableHeader\", "
			+ "ui.batch_table_appear_ind_cd \"batchTableAppearIndCd\", "
			+ "ui.block_nm \"blockName\", "
			+ "rdb.block_pivot_nbr \"waRdbMetadataDataMartRepeatNbr\", "
			+ "rdb.wa_rdb_metadata_uid \"waRdbWaRdbMetadataUid\", "
			+ "rdb.wa_ui_metadata_uid \"waRdbWaUiMetadataUid\", "
			+ "rdb.wa_template_uid \"waRdbWaTemplateUid\", "
			+ "rdb.rdb_table_nm \"waRdbRdbTableNm\", "
			+ "rdb.rdb_column_nm \"waRdbRdbColumnNm\", "
			+ "rdb.question_identifier \"waRdbQuestionIdentifier\", "
			+ "rdb.user_defined_column_nm \"waUserDefinedColumnNm\", "
			+ "rdb.rpt_admin_column_nm \"waRdbRptAdminColumnNm\", "
			+ "rdb.record_status_cd \"waRdbRecStatusCd\", "
			+ "rdb.record_status_time \"waRdbRecStatusTime\", "
			+ "rdb.add_user_id \"waRdbAddUserId\", "
			+ "rdb.add_time \"waRdbAddTime\", "
			+ "rdb.last_chg_time \"waRdbLastChgTime\", "
			+ "rdb.last_chg_user_id \"waRdbLastChgUserId\", "
			+ "rdb.local_id AS \"waRdbLocalId\", "
			+ "nnd.wa_nnd_metadata_uid \"waNndWaNndMetadataUid\", "
			+ "nnd.wa_ui_metadata_uid \"waNndWaUiMetadataUid\", "
			+ "nnd.wa_template_uid \"waNndWaTemplateUid\", "
			+ "nnd.question_identifier_nnd \"waNndQuestionIdentifierNnd\", "
			+ "nnd.question_label_nnd \"waNndQuestionLabelNnd\", "
			+ "nnd.question_required_nnd \"waNndQuestionRequiredNnd\", "
			+ "nnd.question_data_type_nnd \"waNndQuestionDataTypeNnd\", "
			+ "nnd.HL7_segment_field \"waNndHl7SegmentField\", "
			+ "nnd.order_group_id \"waNndOrderGroupId\", "
			+ "nnd.translation_table_nm \"waNndTranslationTableNm\", "
			+ "nnd.add_time \"waNndAddTime\", "
			+ "nnd.add_user_id \"waNndAddUserId\", "
			+ "nnd.last_chg_time \"waNndLastChgTime\", "
			+ "nnd.last_chg_user_id \"waNndLastChgUserId\", "
			+ "nnd.record_status_cd \"waNndRecStatusCd\", "
			+ "nnd.record_status_time \"waNndRecStatusTime\", "
			+ "nnd.question_identifier \"waNndQuestionIdentifier\", "
			+
			// "nnd.msg_trigger_ind_cd \"waNndMsgTriggerIndCd\", " +
			"nnd.xml_path \"waNndXmlPath\", "
			+ "nnd.xml_tag \"waNndXmlTag\", "
			+ "nnd.xml_data_type \"waNndXmlDataType\", "
			+ "nnd.part_type_cd \"waNndPartTypeCd\", "
			+ "nnd.repeat_group_seq_nbr \"waNndRepeatGroupSeqNbr\", "
			+ "nnd.question_order_nnd \"waNndQuestionOrderNnd\", "
			+ "nnd.local_id AS \"waNndLocalId\", "
			+ "nnd.question_map AS \"waNndQuestionMap\", "
			+ "nnd.indicator_cd AS \"waNndIndicatorCd\" "
			+ "FROM  WA_UI_metadata ui "
			+ " LEFT OUTER JOIN WA_RDB_metadata rdb "
			+ "ON ui.wa_template_uid = rdb.wa_template_uid "
			+ "AND ui.wa_ui_metadata_uid =  rdb.wa_ui_metadata_uid "
			+ "LEFT OUTER JOIN WA_NND_metadata nnd  "
			+ "ON ui.wa_template_uid = nnd.wa_template_uid "
			+ "AND ui.wa_ui_metadata_uid =  nnd.wa_ui_metadata_uid "
			+ "WHERE (ui.wa_template_uid = ? AND (standard_nnd_ind_cd != 'T' or standard_nnd_ind_cd is null )) "
			+
			// "AND standard_nnd_ind_cd != 'T' "+
			"ORDER BY ui.order_nbr";

	private static final String RETRIEVE_WA_TEMPLATE_SUMMARIES ="SELECT tem.wa_template_uid \"waTemplateUid\", "
			+ " tem.template_type \"templateType\", "
			+ " tem.publish_version_nbr \"publishVersionNbr\", "
			+ " tem.bus_obj_type \"busObjType\", "
			+ " tem.template_nm \"templateNm\", "
			+ " tem.form_cd \"formCd\", "
			+ " tem.last_chg_time \"lastChgTime\", "
			+ " tem.record_status_cd \"recStatusCd\", "
			+ " tem.record_status_time \"recStatusTime\", "
			+ " tem.publish_ind_cd \"publishIndCd\", "
			+ " concat(concat(up.first_nm , ' '),  up.last_nm) \"firstLastName\" "
			+ " FROM WA_template tem left outer join user_profile up on " 
			+ " tem.last_chg_user_id = up.nedss_entry_id " 
			+ " where  tem.template_type in('Draft','Published')";

	private static final String RETRIEVE_WA_TEMPLATE_SUMMARIES_SQL ="SELECT tem.wa_template_uid \"waTemplateUid\", "
	 						+ " tem.template_type \"templateType\", "
	 						+ " tem.publish_version_nbr \"publishVersionNbr\", "
	 						+ " tem.bus_obj_type \"busObjType\", "
	 						+ " tem.template_nm \"templateNm\", "
	 						+ " tem.form_cd \"formCd\", "
	 						+ " tem.last_chg_time \"lastChgTime\", "
	 						+ " tem.record_status_cd \"recStatusCd\", "
	 						+ " tem.record_status_time \"recStatusTime\", "
	 						+ " tem.publish_ind_cd \"publishIndCd\", "
	 						+ " up.first_nm + ' '+ up.last_nm \"firstLastName\" "
	 						+ " FROM WA_template tem left outer join user_profile up on " 
	 						+ " tem.last_chg_user_id = up.nedss_entry_id " 
	 						+ " where  tem.template_type in('Draft','Published')";




	private static final String CREATE_WA_NND_METADATA = "INSERT INTO WA_NND_metadata(wa_ui_metadata_uid,wa_template_uid ,question_identifier_nnd ,question_label_nnd ,question_required_nnd ,"
			+ " question_data_type_nnd,HL7_segment_field ,order_group_id ,translation_table_nm ,question_identifier ,"
			+ " xml_path ,xml_tag ,add_time ,add_user_id ,last_chg_time ,last_chg_user_id ,record_status_cd  ,record_status_time ,"
			+ " xml_data_type ,part_type_cd ,repeat_group_seq_nbr,question_order_nnd,question_map,indicator_cd) "
			+ " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	private static final String CREATE_WA_QUESTION = "INSERT INTO WA_question(code_set_group_id ,data_cd ,data_location ,question_identifier ,question_oid ,"
			+ " question_oid_system_txt,question_unit_identifier ,data_type ,data_use_cd ,question_label ,question_tool_tip ,"
			+ " user_defined_column_nm ,part_type_cd ,add_time ,add_user_id ,last_chg_time ,last_chg_user_id ,default_value  ,"
			+ " version_ctrl_nbr ,unit_parent_identifier,question_group_seq_nbr,future_date_ind_cd ,legacy_data_location ,repeats_ind_cd ,"
			+ " local_id, question_nm, group_nm, sub_group_nm, desc_txt, mask, field_size,"
			+ " nbs_ui_component_uid, rpt_admin_column_nm,nnd_msg_ind,question_required_nnd ,"
			+ " question_identifier_nnd, question_label_nnd , question_data_type_nnd,hl7_segment_field,order_group_id,record_status_cd,"
			+ " record_status_time,question_type,entry_method,rdb_table_nm,rdb_column_nm, admin_comment, min_value, max_value, standard_nnd_ind_cd, standard_question_ind_cd,unit_type_cd,unit_value,other_value_ind_cd,coinfection_ind_cd) "
			+ " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	private static final String CREATE_WA_RDB_METADATA = "INSERT INTO WA_RDB_metadata(wa_template_uid , wa_ui_metadata_uid, rdb_table_nm, rdb_column_nm ,user_defined_column_nm,"
			+ " add_time ,add_user_id ,last_chg_time ,last_chg_user_id ,record_status_cd,record_status_time, rpt_admin_column_nm, question_identifier, block_pivot_nbr) "
			+ " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	private static final String CREATE_WA_TEMPLATE = "INSERT INTO WA_template(template_type, xml_payload, publish_version_nbr,"
			+ " form_cd ,condition_cd, bus_obj_type, datamart_nm, last_chg_time, last_chg_user_id, record_status_cd, record_status_time, "
			+ "  desc_txt, template_nm,add_time,add_user_id,source_nm,publish_ind_cd,version_note,nnd_entity_identifier) "
			+ " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	private static final String CREATE_WA_UI_METADATA = "INSERT INTO WA_UI_metadata(wa_template_uid ,nbs_ui_component_uid ,parent_uid ,question_label ,question_tool_tip ,"
			+ "enable_ind ,default_value ,display_ind ,order_nbr  ,required_ind ,add_time ,add_user_id  ,"
			+ "last_chg_time ,last_chg_user_id ,record_status_cd ,record_status_time ,max_length , "
			+ "version_ctrl_nbr ,admin_comment ,field_size ,future_date_ind_cd , local_id, code_set_group_id,data_type, question_identifier,mask, "
			+ "question_unit_identifier,publish_ind_cd,data_cd,data_location,standard_nnd_ind_cd,part_type_cd,unit_parent_identifier, "
			+ "data_use_cd,legacy_data_location,question_oid,question_oid_system_txt,question_nm, standard_question_ind_cd, question_type, "
			+ "group_nm, sub_group_nm, desc_txt, entry_method,unit_type_cd,unit_value,other_value_ind_cd, min_value, max_value, question_group_seq_nbr,batch_table_column_width,batch_table_header,batch_table_appear_ind_cd,coinfection_ind_cd,block_nm)"
			+ " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	private static final String UPDATE_WA_NND_METADATA = "UPDATE WA_NND_metadata "
			+ "SET nbs_ui_component_uid=?, "
			+ "wa_question_uid =?, "
			+ "wa_template_uid =?, "
			+ "question_identifier_nnd =?, "
			+ "question_label_nnd =?, "
			+ "question_required_nnd =?, "
			+ "question_data_type_nnd=?, "
			+ "HL7_segment_field =?, "
			+ "order_group_id =?, "
			+ "translation_table_nm =?, "
			+ "question_identifier =?, "
			// +"msg_trigger_ind_cd =?, "
			+ "xml_path =?, "
			+ "xml_tag =?, "
			+ "add_time =?, "
			+ "add_user_id =?, "
			+ "last_chg_time =?, "
			+ "last_chg_user_id =?, "
			+ "record_status_cd  =?, "
			+ "record_status_time =?, "
			+ "xml_data_type =?, "
			+ "part_type_cd =?, "
			+ "repeat_group_seq_nbr=?, "
			+ "question_order_nnd =? " + "WHERE wa_nnd_metadata_uid = ? ";

	private static final String UPDATE_WA_QUESTION = "UPDATE WA_question "
			+ "SET code_set_group_id=?, " + "data_cd =?, "
			+ "data_location =?, " + "question_identifier =?, "
			+ "question_oid =?, " + "question_oid_system_txt =?, "
			+ "question_unit_identifier=?, " + "data_type =?, "
			+ "data_use_cd =?, " + "question_label =?, "
			+ "question_tool_tip =?, " + "user_defined_column_nm =?, "
			+ "part_type_cd =?, " + "add_time =?, " + "add_user_id =?, "
			+ "last_chg_time =?, " + "last_chg_user_id =?, "
			+ "default_value   =?, " + "version_ctrl_nbr =?, "
			+ "unit_parent_identifier =?, " + "question_group_seq_nbr=?, "
			+ "future_date_ind_cd  =?, " + "legacy_data_location  =?, "
			+ "repeats_ind_cd  =?, " + "local_id =?, " + "question_nm =?, "
			+ "group_nm =?, " + "sub_group_nm =?, " + "desc_txt =?, "
			+ "mask =?, " + "field_size =?, " + "nbs_ui_component_uid =?, "
			+ "rpt_admin_column_nm =?, " + "nnd_msg_ind =?, "
			+ "question_required_nnd =?, " + "question_identifier_nnd =?, "
			+ "question_label_nnd  =?, " + "question_data_type_nnd=?, "
			+ "hl7_segment_field =?, " + "order_group_id =?, "
			+ "record_status_cd =?, " + "record_status_time =?, "
			+ "question_type  =?, " + "entry_method  =?, "
			+ "rdb_table_nm   =?, " + "rdb_column_nm   =?, "
			+ "admin_comment   =?, " + "min_value   =?, "
			+ "max_value   =?, unit_type_cd = ?, unit_value = ?, other_value_ind_cd = ?, " + "coinfection_ind_cd   = ? "
			+ "WHERE wa_question_uid  = ? " + "AND version_ctrl_nbr = ?";

	private static final String UPDATE_WA_RDB_METADATA = "UPDATE WA_RDB_metadata "
			// +"SET wa_question_uid=?, "
			+ "wa_template_uid =?, "
			+ "rdb_table_nm =?, "
			+ "user_defined_column_nm =?, "
			+ "add_time =?, "
			+ "add_user_id =?, "
			+ "last_chg_time =?, "
			+ "last_chg_user_id =?, "
			+ "record_status_cd   =?, "
			+ "record_status_time =?, "
			+ "version_ctrl_nbr =?, "
			+ "rpt_admin_column_nm=?, "
			+ "block_pivot_nbr=?, "
			+ "question_identifier=? " + "WHERE wa_rdb_metadata_uid  = ? ";

	private static final String UPDATE_WA_TEMPLATE_SQL = "UPDATE WA_template "
			+ "SET template_type=?, " + "xml_payload =?, "
			+ "publish_version_nbr =?, " + "form_cd =?, " + "condition_cd =?, "
			+ "bus_obj_type =?, " + "datamart_nm =?, " + "last_chg_time =?, "
			+ "last_chg_user_id =?, " + "record_status_cd   =?, "
			+ "record_status_time =?, " + "desc_txt =?, " + "publish_ind_cd=?, "
			+ "template_nm =?, " + "nnd_entity_identifier=?, " + "version_note=? "
			+ " WHERE wa_template_uid  = ? ";

	private static final String UPDATE_WA_TEMPLATE_ORACLE = "UPDATE WA_template "
		+ "SET template_type=?, "
		+ "publish_version_nbr =?, " + "form_cd =?, " + "condition_cd =?, "
		+ "bus_obj_type =?, " + "datamart_nm =?, " + "last_chg_time =?, "
		+ "last_chg_user_id =?, " + "record_status_cd   =?, "
		+ "record_status_time =?, " + "desc_txt =?, " + "publish_ind_cd=? ,"
		+ "template_nm =?, " + "nnd_entity_identifier=?, " + "version_note=? "
		+ "WHERE wa_template_uid  = ? " ;

	private static final String UPDATE_WA_UI_METADATA = "UPDATE WA_UI_metadata "
			+ "SET wa_template_uid =?, "
			+ "nbs_ui_component_uid =?, "
			+ "wa_question_uid=?, "
			+ "parent_uid =?, "
			+ "question_label =?, "
			+ "question_tool_tip =?, "
			+ "enable_ind =?, "
			+ "default_value =?, "
			+ "display_ind =?, "
			+ "order_nbr  =?, "
			+ "required_ind =?, "
			// +"tab_order_id =?, "
			// +"tab_name =?, "
			+ "add_time =?, "
			+ "add_user_id  =?, "
			+ "last_chg_time =?, "
			+ "last_chg_user_id =?, "
			+ "record_status_cd =?, "
			+ "record_status_time =?, "
			+ "max_length =?, "
			// +"css_style=?, "
			+ "version_ctrl_nbr =?, "
			+ "admin_comment =?, "
			+ "field_size =?, "
			+ "future_date_ind_cd =?, "
			+ "local_id =? "
			+ "mask = ? "
			+ "min_value = ? "
			+ "max_value = ? "
			+ "WHERE wa_ui_metadata_uid = ? ";

	private static final String UPDATE_WA_TEMPLATE_WITH_XML_CONTENTS = "UPDATE WA_template "
			+ "SET xml_payload =? " + "WHERE wa_template_uid  = ? ";

	private static final String DELETE_WA_NND_METADATA = "Delete WA_NND_metadata  WHERE nbs_ui_metadata_uid=? ";
	private static final String DELETE_WA_QUESTION = "Delete WA_question  WHERE wa_question_uid=? ";
	private static final String DELETE_WA_RDB_METADATA = "Delete WA_RDB_metadata  WHERE wa_rdb_metadata_uid=? ";
	private static final String DELETE_WA_TEMPLATE = "Delete WA_template  WHERE wa_template_uid=? ";
	private static final String DELETE_WA_UI_METADATA = "Delete WA_UI_metadata  WHERE wa_ui_metadata_uid=? ";

	private static final String DELETE_WA_NND_METADATA_BY_TEMPLATE_UID = "Delete WA_NND_metadata  WHERE wa_template_uid=? ";
	private static final String DELETE_WA_RDB_METADATA_BY_TEMPLATE_UID = "Delete WA_RDB_metadata  WHERE wa_template_uid=? ";
	private static final String DELETE_WA_UI_METADATA_BY_TEMPLATE_UID = "Delete WA_UI_metadata  WHERE wa_template_uid=? ";
	private static final String DELETE_WA_RULE_METADATA_BY_TEMPLATE_UID = "Delete WA_rule_metadata  WHERE wa_template_uid=? ";
	private static final String DELETE_WA_TEMPLATE_BY_TEMPLATE_UID = "Delete WA_Template  WHERE wa_template_uid=? ";
	private static final String DELETE_PAGE_COND_MAPPING_BY_TEMPLATE_UID = "Delete Page_Cond_Mapping WHERE wa_template_uid=? ";
	private static final String DELETE_PAGE_COND_MAPPING_BY_CONDITION_CD_TEMPLATE_UID = "Delete Page_Cond_Mapping WHERE wa_template_uid=? and condition_cd = ?";

	private static final String INSERT_WA_NND_METADATA_HISTORY = "INSERT INTO WA_NND_metadata_hist( wa_nnd_metadata_uid ,wa_template_uid ,question_identifier_nnd ,question_label_nnd ,question_required_nnd ,"
			+ " question_data_type_nnd,HL7_segment_field ,order_group_id ,translation_table_nm ,question_identifier ,"
			+ " xml_path ,xml_tag ,add_time ,add_user_id ,last_chg_time ,last_chg_user_id ,record_status_cd  ,record_status_time , wa_ui_metadata_uid, "
			+ " xml_data_type ,part_type_cd ,repeat_group_seq_nbr,question_order_nnd, question_map, indicator_cd, wa_template_hist_uid) "
			+ " SELECT wa_nnd_metadata_uid ,wa_template_uid ,question_identifier_nnd ,question_label_nnd ,question_required_nnd ,question_data_type_nnd,HL7_segment_field ,order_group_id ,translation_table_nm ,question_identifier ,xml_path ,xml_tag ,add_time ,add_user_id ,last_chg_time ,last_chg_user_id ,record_status_cd  ,record_status_time , wa_ui_metadata_uid, xml_data_type ,part_type_cd ,repeat_group_seq_nbr,question_order_nnd,question_map, indicator_cd, ";

	private static final String WHERE_WA_NND_META_DATA_UID = " WHERE wa_nnd_metadata_uid = ? ";

	private static final String INSERT_WA_QUESTION_HISTORY = "INSERT INTO WA_question_hist( wa_question_uid ,code_set_group_id ,data_cd ,data_location ,question_identifier ,question_oid ,"
			+ " question_oid_system_txt,question_unit_identifier ,data_type ,data_use_cd ,question_label ,question_tool_tip ,"
			+ " user_defined_column_nm ,part_type_cd ,add_time ,add_user_id ,last_chg_time ,last_chg_user_id ,default_value, "
			+ " version_ctrl_nbr ,unit_parent_identifier ,question_group_seq_nbr,future_date_ind_cd ,legacy_data_location ,repeats_ind_cd,local_id,question_nm,group_nm,sub_group_nm,desc_txt, mask, field_size, "
			+ " nbs_ui_component_uid, rpt_admin_column_nm,nnd_msg_ind ,question_identifier_nnd, question_label_nnd , question_required_nnd, question_data_type_nnd,hl7_segment_field,order_group_id,record_status_cd,record_status_time,rdb_table_nm,rdb_column_nm, admin_comment, standard_nnd_ind_cd, standard_question_ind_cd, entry_method, question_type, other_value_ind_cd, coinfection_ind_cd)"
			+ " SELECT wa_question_uid ,code_set_group_id ,data_cd ,data_location ,question_identifier ,question_oid , "
			+ "question_oid_system_txt,question_unit_identifier ,data_type ,data_use_cd ,question_label ,question_tool_tip , user_defined_column_nm ,part_type_cd ,"
			+ "add_time ,add_user_id ,last_chg_time ,last_chg_user_id ,default_value, "
			+ "version_ctrl_nbr ,unit_parent_identifier ,question_group_seq_nbr,future_date_ind_cd ,legacy_data_location ,repeats_ind_cd,local_id,question_nm,group_nm,sub_group_nm,desc_txt, mask, field_size, "
			+ " nbs_ui_component_uid, rpt_admin_column_nm, "
			+ "nnd_msg_ind ,question_identifier_nnd, question_label_nnd , question_required_nnd, question_data_type_nnd,hl7_segment_field,order_group_id,record_status_cd,record_status_time,rdb_table_nm,rdb_column_nm, admin_comment, standard_nnd_ind_cd, standard_question_ind_cd, entry_method, question_type, other_value_ind_cd, coinfection_ind_cd "
			+ " FROM WA_question" + " WHERE wa_question_uid = ? ";

	private static final String INSERT_WA_RDB_METADATA_HISTORY = "INSERT INTO WA_RDB_metadata_hist( wa_rdb_metadata_uid ,wa_template_uid ,rdb_table_nm, user_defined_column_nm,block_pivot_nbr, rpt_admin_column_nm ,rdb_column_nm,"
			+ " add_time ,add_user_id ,last_chg_time ,last_chg_user_id ,record_status_cd  ,record_status_time, question_identifier, wa_template_hist_uid)"
			+ " SELECT wa_rdb_metadata_uid ,wa_template_uid, rdb_table_nm, user_defined_column_nm,block_pivot_nbr, rpt_admin_column_nm,rdb_column_nm, add_time ,add_user_id ,last_chg_time ,last_chg_user_id ,record_status_cd  ,record_status_time ,question_identifier, ";

	private static final String WHERE_WA_RDB_META_DATA_UID = " WHERE wa_rdb_metadata_uid = ? ";

	private static final String INSERT_WA_TEMPLATE_HISTORY = "INSERT INTO WA_template_hist( wa_template_uid ,template_type ,xml_payload ,publish_version_nbr ,"
			+ " form_cd ,condition_cd ,bus_obj_type ,datamart_nm ,last_chg_time ,last_chg_user_id ,record_status_cd  ,record_status_time,  desc_txt, template_nm, version_note, publish_ind_cd,add_time,add_user_id)"
			+ " SELECT wa_template_uid ,template_type ,xml_payload ,publish_version_nbr ,form_cd ,condition_cd ,bus_obj_type ,datamart_nm ,last_chg_time ,last_chg_user_id ,record_status_cd, record_status_time, desc_txt, template_nm, version_note, publish_ind_cd,add_time,add_user_id FROM WA_template"
			+ " WHERE wa_template_uid = ? ";

	private static final String INSERT_WA_UI_METADATA_HISTORY = "INSERT INTO WA_UI_metadata_hist( wa_ui_metadata_uid ,wa_template_uid ,nbs_ui_component_uid ,parent_uid ,question_label ,block_nm, question_tool_tip , "
			+ " enable_ind ,default_value ,display_ind ,order_nbr  ,required_ind ,add_time ,add_user_id  , "
			+ " last_chg_time ,last_chg_user_id ,record_status_cd ,record_status_time ,max_length, "
			+ " admin_comment, field_size, future_date_ind_cd, local_id, mask, standard_question_ind_cd,min_value ,max_value, unit_type_cd, unit_value,other_value_ind_cd, publish_ind_cd, question_group_seq_nbr, batch_table_appear_ind_cd, batch_table_header, batch_table_column_width, wa_template_hist_uid)"
			+ " SELECT wa_ui_metadata_uid ,wa_template_uid ,nbs_ui_component_uid ,parent_uid ,question_label ,block_nm, question_tool_tip ,enable_ind ,default_value ,display_ind ,order_nbr  ,required_ind ,add_time ,add_user_id  ,last_chg_time ,last_chg_user_id ,record_status_cd ,record_status_time ,max_length, admin_comment, field_size, future_date_ind_cd, local_id, mask, standard_question_ind_cd,min_value ,max_value, unit_type_cd, unit_value,other_value_ind_cd, publish_ind_cd, question_group_seq_nbr, batch_table_appear_ind_cd, batch_table_header, batch_table_column_width, ";

	private static final String INSERT_PAGE_COND_MAPPING_HISTORY = "INSERT INTO PAGE_COND_MAPPING_HIST (wa_template_uid ,condition_cd ,add_time,  add_user_id,last_chg_time ,last_chg_user_id,page_cond_mapping_uid )"
		+ " SELECT wa_template_uid ,condition_cd ,add_time, add_user_id, last_chg_time ,last_chg_user_id,page_cond_mapping_uid   ";


	private static final String WHERE_WA_UI_META_DATA_UID = " WHERE wa_ui_metadata_uid = ? ";

	private static final String WHERE_WA_TEMPLATE_UID = " WHERE wa_template_uid = ? ";

	private static final String WHERE_PAGE_COND_MAPPING_UID = " WHERE page_cond_mapping_uid = ? ";

	private static final String UPDATE_WA_UI_METADATA_PUBLISHED = "UPDATE WA_ui_metadata set publish_ind_cd = '"
			+ NEDSSConstants.TRUE + "' where wa_template_uid=?";

	private static final String UPDATE_WA_TEMPLATE_PUBLISHED = "UPDATE WA_template set template_type = 'Published' where wa_template_uid=?";

	private static final String UPDATE_CONDITION_CD = "UPDATE condition_code set status_cd = 'A' where status_cd = 'P'";
	
	private static final String UPDATE_PPREPOP_MAPPING = "update lookup_question set to_form_cd = ? where to_form_cd = ?";

	private final String GetMaxGroupUid = "select max(question_group_seq_nbr)  from WA_UI_METADATA where wa_template_uid= ?";

	private static final String SEARCH_WA_QUESTIONS = "SELECT ques.wa_question_uid \"waQuestionUid\", "
			+ "ques.code_set_group_id \"codeSetGroupId\", "
			+ "ques.data_cd \"dataCd\", "
			+ "ques.data_location \"dataLocation\", "
			+ "ques.question_identifier \"questionIdentifier\", "
			+ "ques.question_oid \"questionOid\", "
			+ "ques.question_oid_system_txt \"questionOidSystemTxt\", "
			+ "ques.question_unit_identifier \"questionUnitIdentifier\", "
			+ "ques.data_type \"dataType\", "
			+ "ques.data_use_cd \"dataUseCd\", "
			+ "ques.question_label \"questionLabel\", "
			+ "ques.question_tool_tip \"questionToolTip\", "
			+ "ques.part_type_cd \"partTypeCd\", "
			+ "ques.add_time \"addTime\", "
			+ "ques.add_user_id \"addUserId\", "
			+ "ques.last_chg_time \"lastChgTime\", "
			+ "ques.last_chg_user_id \"lastChgUserId\", "
			+ "ques.default_value \"defaultValue\", "
			+ "ques.part_type_cd \"partTypeCd\", "
			+ "ques.version_ctrl_nbr \"versionCtrlNbr\", "
			+ "ques.unit_parent_identifier  \"unitParentIndentifier\", "
			+ "ques.question_group_seq_nbr \"questionGroupSeqNbr\", "
			+ "ques.future_date_ind_cd \"futureDateIndCd\", "
			+ "ques.legacy_data_location \"legacyDataLocation\", "
			+ "ques.repeats_ind_cd \"repeatsIndCd\", "
			+ "ques.local_id \"localId\", "
			+ "ques.question_type \"questionType\", "
			+ "ques.entry_method \"entryMethod\", "
			+ "ques.question_nm \"questionNm\", "
			+ "ques.group_nm \"groupNm\", "
			+ "ques.sub_group_nm \"subGroupNm\", "
			+ "ques.desc_txt \"description\", "
			+ "ques.mask \"mask\", "
			+ "ques.field_size \"fieldLength\", "
			+ "ques.nbs_ui_component_uid \"nbsUiComponentUid\", "
			+ "ques.rpt_admin_column_nm \"reportAdminColumnNm\", "
			+ "ques.nnd_msg_ind \"nndMsgInd\", "
			+ "ques.question_identifier_nnd \"questionIdentifierNnd\", "
			+ "ques.question_label_nnd  \"questionLabelNnd\", "
			+ "ques.question_required_nnd \"questionReqNnd\", "
			+ "ques.question_data_type_nnd \"questionDataTypeNnd\", "
			+ "ques.hl7_segment_field \"hl7Segment\", "
			+ "ques.order_group_id \"orderGrpId\", "
			+ "ques.record_status_cd \"recordStatusCd\", "
			+ "ques.rdb_table_nm \"rdbTableNm\", "
			+ "ques.rdb_column_nm \"rdbcolumnNm\", "
            + "ques.user_defined_column_nm \"userDefinedColumnNm\", "
			+ "ques.record_status_time \"recordStatusTime\", "
			+ "ques.standard_question_ind_cd \"standardQuestionIndCd\", "
			+ "ques.unit_type_cd  \"unitTypeCd\", "
			+ "ques.unit_value  \"unitValue\",  "
			+ "ques.admin_comment  \"adminComment\",  "
            + "ques.min_value \"minValue\", "
            + "ques.max_value \"maxValue\", "
			+ "ques.other_value_ind_cd  \"otherValIndCd\", "
			+ "ques.coinfection_ind_cd  \"coInfQuestion\" "
			+ "FROM WA_Question ques ";


	private static final String FIND_WA_UI_METADATA_BY_QUEST_IDENT = "SELECT wa_ui_metadata_uid \"waUiMetadataUid\", "
		+ "question_label \"questionLabel\" "
		+ "FROM WA_ui_metadata  "
		+ "WHERE question_identifier = ? ";

	private static final String RETRIEVE_PAGE_HISTORY_SQL = "SELECT temhist.wa_template_uid \"waTemplateUid\", "
		+ "temhist.template_type \"templateType\", "
		+ "temhist.publish_version_nbr \"publishVersionNbr\", "
		+ "temhist.form_cd \"formCd\", "
		+ "temhist.condition_cd \"conditionCd\", "
		+ "temhist.last_chg_user_id \"lastChgUserId\", "
		+ "temhist.last_chg_time \"lastChgTime\", "
		+ "temhist.record_status_cd \"recStatusCd\", "
		+ "temhist.record_status_time \"recStatusTime\","
		+ "temhist.version_note \"descTxt\","
		+ "temhist.publish_ind_cd \"publishIndCd\","
		+ "temhist.publish_version_nbr \"version\", "
		+ " up.first_nm + ' '+ up.last_nm \"firstLastName\" "
		+ " FROM WA_template_hist temhist left outer join user_profile up on "
	 	+ " temhist.last_chg_user_id = up.nedss_entry_id "
		+ "WHERE temhist.template_nm = ? "
	    + "UNION "
	    + "SELECT tem.wa_template_uid \"waTemplateUid\", "
		+ "tem.template_type \"templateType\", "
		+ "tem.publish_version_nbr \"publishVersionNbr\", "
		+ "tem.form_cd \"formCd\", "
		+ "tem.condition_cd \"conditionCd\", "
		+ "tem.last_chg_user_id \"lastChgUserId\", "
		+ "tem.last_chg_time \"lastChgTime\", "
		+ "tem.record_status_cd \"recStatusCd\", "
		+ "tem.record_status_time \"recStatusTime\","
		+ "tem.version_note \"descTxt\","
		+ "tem.publish_ind_cd \"publishIndCd\","
		+ "tem.publish_version_nbr \"version\", "
		+ " up.first_nm + ' '+ up.last_nm \"firstLastName\" "
		+ " FROM WA_template tem left outer join user_profile up on "
	 	+ " tem.last_chg_user_id = up.nedss_entry_id "
		+ " WHERE tem.template_nm = ? and tem.template_type in ('Published With Draft','Published')  " ;


	private static final String RETRIEVE_JSP_FILES_SQL = "SELECT jsp_payload \"jspPayload\" "
		+ "FROM nbs_page  "
		+ "WHERE form_cd = ? ";

	private static final String RETRIEVE_INV_FRM_CD_SQL = "SELECT form_cd \"formCd\" "
		+ "FROM nbs_page  ";

	private static final String RETRIEVE_MESSAGE_GUIDE_SQL =
	     "SELECT tem.wa_template_uid \"waTemplateUid\", "
		+ "tem.template_type \"templateType\", "
		+ "tem.publish_version_nbr \"publishVersionNbr\", "
		+ "tem.nnd_entity_identifier \"nndEntityIdentifier\" "
		+ " FROM WA_template tem  "
		+ " WHERE tem.condition_cd = ? and tem.template_type in ('Published With Draft','Published') order by tem.publish_version_nbr " ;


	private static final String RETRIVE_COUNT_PAGE_RULES_SQL = "SELECT WA_RULE_METADATA_UID \"waRuleMetadataUid\" FROM WA_RULE_METADATA WHERE WA_TEMPLATE_UID=? AND ( CHARINDEX(?,  source_question_identifier,1) >0 or CHARINDEX(?,  target_question_identifier,1) >0)";

	private static final String UPDATE_WA_UI_METADATA_BATCH = "UPDATE WA_ui_metadata set question_group_seq_nbr =? where wa_template_uid=? and question_identifier=?";

	private static final String INSERT_PAGE_COND_MAPPING = "INSERT INTO PAGE_COND_MAPPING(wa_template_uid, condition_cd, add_time,"
		+ " add_user_id ,last_chg_time,last_chg_user_id ) "
		+ " VALUES(?,?,?,?,?,?)";


	private static final String FIND_PAGE_COND_MAPPING_COLL_SQL = "SELECT  page_cond_mapping_uid pageCondMappingUid, "
		+ " wa_template_uid \"waTemplateUid\", "
		+ " pcm.condition_cd \"conditionCd\", "
		+ " cc.condition_short_nm \"conditionDesc\", "
		+ " cc.port_req_ind_cd \"portReqIndCd\", "
		+ " add_time \"addTime\", "
		+ " add_user_id \"addUserId\", "
		+ " last_chg_time  \"lastChgTime\", "
		+ " last_chg_user_id \"lastChgUserId\" "
		+ " FROM PAGE_COND_MAPPING pcm,nbs_srte..condition_code cc  "
		+ " WHERE wa_template_uid = ? "
		+ " and pcm.condition_cd = cc.condition_cd order by cc.condition_short_nm  ";
	
	private static final String FIND_PAGE_COND_MAPPING_DT_SQL = "SELECT  page_cond_mapping_uid pageCondMappingUid, "
		+ " pcm.wa_template_uid \"waTemplateUid\", "
		+ " pcm.condition_cd \"conditionCd\", "
		+ " cc.condition_short_nm \"conditionDesc\", "
		+ " cc.port_req_ind_cd \"portReqIndCd\", "
		+ " pcm.add_time \"addTime\", "
		+ " pcm.add_user_id \"addUserId\", "
		+ " pcm.last_chg_time  \"lastChgTime\", "
		+ " pcm.last_chg_user_id \"lastChgUserId\" "
		+ " FROM PAGE_COND_MAPPING pcm, WA_TEMPLATE wt, nbs_srte..condition_code cc  "
		+ " WHERE pcm.condition_cd = ? "
		+ " and wt.bus_obj_type = ? "
		+ " and wt.wa_template_uid = pcm.wa_template_uid "
		+ " and pcm.condition_cd = cc.condition_cd ";
	

	private static final String PAGE_COND_MAPPING_SUMMARY_SQL = "SELECT  page_cond_mapping_uid pageCondMappingUid, "
		+ "wa_template_uid \"waTemplateUid\", "
		+ "pcm.condition_cd \"conditionCd\", "
		+ "cc.condition_short_nm \"conditionDesc\", "
		+ "cc.port_req_ind_cd \"portReqIndCd\", "
		+ " add_time \"addTime\", "
		+ " add_user_id \"addUserId\", "
		+ " last_chg_time  \"lastChgTime\", "
		+ " last_chg_user_id \"lastChgUserId\" "
		+ "FROM PAGE_COND_MAPPING pcm,nbs_srte..condition_code cc "
		+ " where pcm.condition_cd = cc.condition_cd  ";

	
	private static final String UPDATE_PAGE_COND_MAPPING = "UPDATE PAGE_COND_MAPPING set wa_template_uid=?, condition_cd=?, add_time=?,"
		+ " add_user_id=? ,last_chg_time=?,last_chg_user_id=?  "
		+ "WHERE page_cond_mapping_uid = ? ";
	
	private static final String FIND_PAGE_COND_MAPPING_COLL_BY_COND_SQL = "SELECT  page_cond_mapping_uid pageCondMappingUid, "
		+ " pcm.wa_template_uid \"waTemplateUid\", "
		+ " pcm.condition_cd \"conditionCd\", "
		+ " cc.condition_short_nm \"conditionDesc\", "
		+ " cc.port_req_ind_cd \"portReqIndCd\", "
		+ " pcm.add_time \"addTime\", "
		+ " pcm.add_user_id \"addUserId\", "
		+ " pcm.last_chg_time  \"lastChgTime\", "
		+ " pcm.last_chg_user_id \"lastChgUserId\" "
		+ " FROM PAGE_COND_MAPPING pcm,nbs_srte..condition_code cc, wa_template wt  "
		+ " WHERE pcm.condition_cd = cc.condition_cd "
		+ " and pcm.wa_template_uid = wt.wa_template_uid "
		+ " and wt.bus_obj_type=? and pcm.condition_cd in ";
	private static final String FIND_PAGE_BY_BUS_OBJ_TYPE = "SELECT wa_template_uid \"waTemplateUid\", "
		+" template_type \"templateType\", "
		+" template_nm \"templateNm\", "
		+" form_cd \"formCd\", "
		+" publish_ind_cd \"publishIndCd\", "
		+" version_note \"versionNote\", "
		+" publish_version_nbr \"publishVersionNbr\", "
		+" desc_txt \"descTxt\" " 
		+" FROM WA_template" 
		+" WHERE template_type <> 'TEMPLATE' AND bus_obj_type=?";

	private static final String SELECT_ALL_NBS_BUS_OBJ_METADATA = "SELECT class_cd \"classCd\", "
		+" bus_obj_type \"busObjType\", "
		+" description_txt \"description\", "
		+" core_ods_table \"coreODSETable\", "
		+" answer_table \"answerTable\", "
		+" core_rdb_table \"coreRDBTable\","
		+" core_repeat_rdb_table \"coreRepeatRDBTable\","
		+" group_nm \"groupNm\", "
		+" record_status_cd \"recordStatusCd\"" 
		+" FROM NBS_BUS_OBJ_METADATA";
	
	private static final String SELECT_NBS_BUS_OBJ_METADATA_BY_BUS_OBJ_TYPE = "SELECT class_cd \"classCd\", "
			+" bus_obj_type \"busObjType\", "
			+" description_txt \"description\", "
			+" core_ods_table \"coreODSETable\", "
			+" answer_table \"answerTable\", "
			+" core_rdb_table \"coreRDBTable\","
			+" core_repeat_rdb_table \"coreRepeatRDBTable\","
			+" group_nm \"groupNm\", "
			+" record_status_cd \"recordStatusCd\"" 
			+" FROM NBS_BUS_OBJ_METADATA"
			+" WHERE bus_obj_type = ?";
	
	private static final String FIND_WA_TEMPLATE_BY_TEMPLATE_TYPE = "SELECT tem.wa_template_uid \"waTemplateUid\", "
			+ "tem.template_type \"templateType\", "
			+ "tem.xml_payload \"xmlPayload\", "
			+ "tem.publish_version_nbr \"publishVersionNbr\", "
			+ "tem.form_cd \"formCd\", "
			+ "tem.condition_cd \"conditionCd\", "
			+ "tem.bus_obj_type \"busObjType\", "
			+ "tem.datamart_nm \"dataMartNm\", "
			+ "tem.last_chg_user_id \"lastChgUserId\", "
			+ "tem.last_chg_time \"lastChgTime\", "
			+ "tem.record_status_cd \"recStatusCd\", "
			+ "tem.record_status_time \"recStatusTime\","
			+ "tem.desc_txt \"descTxt\","
			+ "tem.publish_ind_cd \"publishIndCd\","
			+ "tem.template_nm \"templateNm\","
			+ "tem.nnd_entity_identifier \"messageId\","
			+ "tem.version_note \"versionNote\","
			+ "tem.publish_version_nbr \"publishVersionNbr\" "
			+ "FROM WA_template tem "
			+ "WHERE tem.template_type = ? ";
	
	private static final String SELECT_WA_UI_METADATA_FOR_SUB_FORMS = "SELECT wa_ui_metadata_uid \"waUiMetadataUid\", "
			+ "wa_template_uid \"waTemplateUid\", "
			+ "nbs_ui_component_uid \"nbsUiComponentUid\", "
			+ "parent_uid \"parentUid\", "
			+ "question_label \"questionLabel\", "
			+ "question_tool_tip \"questionToolTip\", "
			+ "enable_ind \"enableInd\", "
			+ "default_value \"defaultValue\", "
			+ "display_ind \"displayInd\", "
			+ "order_nbr \"orderNbr\", "
			+ "required_ind \"requiredInd\", "
			+ "question_type \"questionType\", "
			+ "entry_method \"entryMethod\", "
			+ "add_time \"addTime\", "
			+ "add_user_id \"addUserId\", "
			+ "last_chg_time \"lastChgTime\", "
			+ "last_chg_user_id \"lastChgUserId\", "
			+ "record_status_cd \"recordStatusCd\", "
			+ "record_status_time \"recordStatusTime\", "
			+ "max_length \"maxLength\", "
			+ "admin_comment \"adminComment\", "
			+ "version_ctrl_nbr \"versionCtrlNbr\", "
			+ "field_size \"fieldLength\", "
			+ "future_date_ind_cd \"futureDateIndCd\", "
			+ "local_id \"localId\", "
			+ "coinfection_ind_cd \"coinfectionIndCd\", "
			+ "code_set_group_id \"codeSetGroupId\", "
			+ "question_identifier \"questionIdentifier\", "
			+ "data_type \"dataType\", "
			+ "mask \"mask\", "
			+ "standard_nnd_ind_cd \"standardNndIndCd\", "
			+ "question_unit_identifier \"questionUnitIdentifier\", "
			+ "unit_parent_identifier \"unitParentIdentifier\", "
			+ "part_type_cd \"partTypeCd\", "
			+ "data_cd \"dataCd\", "
			+ "legacy_data_location \"legacyDataLocation\", "
			+ "data_location \"dataLocation\", "
			+ "data_use_cd \"dataUseCd\", "
			+ "question_oid \"questionOid\", "
			+ "question_oid_system_txt \"questionOidSystemTxt\", "
			+ "repeats_ind_cd \"repeatsIndCd\", "
			+ "group_nm \"groupNm\", "
			+ "sub_group_nm \"subGroupNm\", "
			+ "desc_txt \"descTxt\", "
			+ "standard_question_ind_cd \"standardQuestionIndCd\", "
			+ "question_nm \"questionNm\", "
			+ "min_value \"minValue\", "
			+ "max_value \"maxValue\", "
			+ "unit_Type_cd \"unitTypeCd\", "
			+ "unit_value \"unitValue\", "
			+ "other_value_ind_cd \"otherValIndCd\", "
			+ "publish_ind_cd \"publishIndCd\", "
			+ "question_group_seq_nbr \"questionGroupSeqNbr\", "
			+ "batch_table_column_width \"batchTableColumnWidth\", "
			+ "batch_table_header \"batchTableHeader\", "
			+ "batch_table_appear_ind_cd \"batchTableAppearIndCd\", "
			+ "block_nm \"blockName\" "
			+ "FROM WA_UI_Metadata WHERE wa_template_uid = ? AND default_value like 'OpenForm: %' ";
	
	/**
	 * Returns findWaNndMetadata searching by WaNndMetadataUid
	 *
	 * @param WaNndMetadataUid
	 * @return findWaNndMetadata
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	@SuppressWarnings("unchecked")
	public WaNndMetadataDT findWaNndMetadata(Long WaNndMetadataUid)
			throws NEDSSDAOSysException, NEDSSSystemException {

		WaNndMetadataDT waNndMetadataDT = new WaNndMetadataDT();
		ArrayList<Object> paramList = new ArrayList<Object>();
		paramList.add(WaNndMetadataUid);
		try {
			paramList = (ArrayList<Object>) preparedStmtMethod(waNndMetadataDT,
					paramList, FIND_WA_NND_METADATA, NEDSSConstants.SELECT);
			if (paramList.size() > 0)
				return (WaNndMetadataDT) paramList.get(0);
		} catch (Exception ex) {
			logger.fatal("Exception in findWaNndMetadata: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return waNndMetadataDT;

	}

	/**
	 * creates an NBS UiMetadata
	 *
	 * @param dt
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	public int createWaNndMetadata(WaNndMetadataDT dt)
			throws NEDSSDAOSysException, NEDSSSystemException {
		int resultCount = -1;

		ArrayList<Object> paramList = new ArrayList<Object>();
		paramList.add(dt.getWaUiMetadataUid());
		paramList.add(dt.getWaTemplateUid());
		paramList.add(dt.getQuestionIdentifierNnd());
		paramList.add(dt.getQuestionLabelNnd());
		paramList.add(dt.getQuestionRequiredNnd());
		paramList.add(dt.getQuestionDataTypeNnd());
		paramList.add(dt.getHl7SegmentField());
		paramList.add(dt.getOrderGroupId());
		paramList.add(dt.getTranslationTableNm());
		paramList.add(dt.getQuestionIdentifier());
		// paramList.add(dt.getMsgTriggerIndCd());
		paramList.add(dt.getXmlPath());
		paramList.add(dt.getXmlTag());
		paramList.add(dt.getAddTime());
		paramList.add(dt.getAddUserId());
		paramList.add(dt.getLastChgTime());
		paramList.add(dt.getLastChgUserId());
		paramList.add(dt.getRecStatusCd());
		paramList.add(dt.getRecStatusTime());
		paramList.add(dt.getXmlDataType());
		paramList.add(dt.getPartTypeCd());
		paramList.add(dt.getRepeatGroupSeqNbr());
		paramList.add(dt.getQuestionOrderNnd());
		paramList.add(dt.getQuestionMap());
		paramList.add(dt.getIndicatorCd());
		try {
			resultCount = ((Integer) preparedStmtMethod(null, paramList,
					CREATE_WA_NND_METADATA, NEDSSConstants.UPDATE)).intValue();
			if (resultCount != 1) {
				logger.error("Exception in createWaNndMetadata: , "
						+ "resultCount = " + resultCount);
				throw new NEDSSSystemException(
						"Exception in createWaNndMetadata:");
			}
		} catch (Exception ex) {
			logger.fatal("Exception in createWaNndMetadata: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return resultCount;
	}

	/**
	 * Update NBSUiMetadata comprises of INSERTING exiting row to HIST, and
	 * updating the current record
	 *
	 * @param dt
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	public void updateWaNndMetadata(WaNndMetadataDT dt)
			throws NEDSSDAOSysException, NEDSSSystemException {

		try {
			// History
			updateWaNndMetadataHistory(dt.getWaNndMetadataUid(), BY_TABLE_UID,null);
	
			ArrayList<Object> paramList = new ArrayList<Object>();
			paramList.add(dt.getWaQuestionUid());
			paramList.add(dt.getWaTemplateUid());
			paramList.add(dt.getQuestionIdentifierNnd());
			paramList.add(dt.getQuestionLabelNnd());
			paramList.add(dt.getQuestionRequiredNnd());
			paramList.add(dt.getQuestionDataTypeNnd());
			paramList.add(dt.getHl7SegmentField());
			paramList.add(dt.getOrderGroupId());
			paramList.add(dt.getTranslationTableNm());
			paramList.add(dt.getQuestionIdentifier());
			// paramList.add(dt.getMsgTriggerIndCd());
	
			// String requiredInd = dt.getRequiredInd() == null ? "" : (String)
			// dt.getRequiredInd();
			// if(requiredInd.equals("1")) paramList.add("T");
			// else paramList.add("F");
	
			paramList.add(dt.getXmlPath());
			paramList.add(dt.getXmlTag());
			paramList.add(dt.getAddTime());
			paramList.add(dt.getAddUserId());
			paramList.add(dt.getLastChgTime());
			paramList.add(dt.getLastChgUserId());
			paramList.add(dt.getRecStatusCd());
			paramList.add(dt.getRecStatusTime());
			paramList.add(dt.getXmlDataType());
			paramList.add(dt.getPartTypeCd());
			paramList.add(dt.getRepeatGroupSeqNbr());
			paramList.add(dt.getQuestionOrderNnd());
	
			// where param
			paramList.add(dt.getWaNndMetadataUid());
		
			preparedStmtMethod(null, paramList, UPDATE_WA_NND_METADATA,
					NEDSSConstants.UPDATE);

		} catch (Exception ex) {
			logger.fatal("Exception in updateWaNndMetadata: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	/**
	 * Delete NBSQuestion by metadata Uid
	 *
	 * @param nbsUiMetadataUid
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	public void deleteWaNndMetadata(WaNndMetadataDT dt)
			throws NEDSSDAOSysException, NEDSSSystemException {

		try {
			// History
			updateWaNndMetadataHistory(dt.getWaNndMetadataUid(), BY_TABLE_UID,null);
	
			ArrayList<Object> paramList = new ArrayList<Object>();
			paramList.add(dt.getRecStatusTime());
			paramList.add(dt.getVersionCtrlNbr());
			paramList.add(dt.getLastChgTime());
			paramList.add(dt.getLastChgUserId());
			// where param
			paramList.add(dt.getWaNndMetadataUid());
		
			preparedStmtMethod(null, paramList, DELETE_WA_NND_METADATA,
					NEDSSConstants.UPDATE);

		} catch (Exception ex) {
			logger.fatal("Exception in deleteWaNndMetadata: ERROR = " + ex);
			throw new NEDSSSystemException(ex.toString());
		}

	}

	/**
	 * updateNBSUiMetadataHistory Copies the current record to the UiMetadata
	 * history table before updated
	 *
	 * @param questionUid
	 */
	public void updateWaNndMetadataHistory(Long waNndMetadataUid,
			String uidType, Long waTemplateHistUid) {

		String sql = INSERT_WA_NND_METADATA_HISTORY;
		String from = " FROM wa_nnd_metadata ";
		sql = sql + waTemplateHistUid + from;
		if (uidType!=null && uidType.equalsIgnoreCase(BY_TABLE_UID))
			sql += WHERE_WA_NND_META_DATA_UID;
		else
			sql += WHERE_WA_TEMPLATE_UID;

		ArrayList<Object> paramList = new ArrayList<Object>();
		// where param
		paramList.add(waNndMetadataUid);

		try {
			preparedStmtMethod(null, paramList, sql, NEDSSConstants.UPDATE);

		} catch (Exception ex) {
			logger.fatal("Exception in updateWaNndMetadataHistory: ERROR = "
					+ ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	/**
	 * Returns findWaNndMetadata searching by WaNndMetadataUid
	 *
	 * @param waQuestionUid
	 * @return WaQuestionDT
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	@SuppressWarnings("unchecked")
	public WaQuestionDT findWaQuestion(Long waQuestionUid)
			throws NEDSSDAOSysException, NEDSSSystemException {
		logger.debug("FIND_WA_QUESTION+WITH_QUESTION_UID=" + FIND_WA_QUESTION
				+ WITH_QUESTION_UID);

		WaQuestionDT waQuestionDT = new WaQuestionDT();
		ArrayList<Object> paramList = new ArrayList<Object>();
		paramList.add(waQuestionUid);
		try {
			paramList = (ArrayList<Object>) preparedStmtMethod(waQuestionDT,
					paramList, FIND_WA_QUESTION + WITH_QUESTION_UID,
					NEDSSConstants.SELECT);
			if (paramList.size() > 0)
				return (WaQuestionDT) paramList.get(0);
		} catch (Exception ex) {
			logger
					.fatal("Exception in PageManagementDAOImpl.findWaQuestion for question uid ="
							+ waQuestionUid + ": ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return waQuestionDT;

	}

	/**
	 * Returns findWaQuestion searching by WaQuestionIdentifier
	 *
	 * @param waQuestionIdentifier
	 * @return WaQuestionDT
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	@SuppressWarnings("unchecked")
	public WaQuestionDT findWaQuestion(String waQuestionIdentifier)
			throws NEDSSDAOSysException, NEDSSSystemException {
		logger.debug("FIND_WA_QUESTION+WITH_QUESTION_IDENTIFIER="
				+ FIND_WA_QUESTION + WITH_QUESTION_IDENTIFIER);

		WaQuestionDT waQuestionDT = new WaQuestionDT();
		ArrayList<Object> paramList = new ArrayList<Object>();
		paramList.add(waQuestionIdentifier);
		try {
			paramList = (ArrayList<Object>) preparedStmtMethod(waQuestionDT,
					paramList, FIND_WA_QUESTION + WITH_QUESTION_IDENTIFIER,
					NEDSSConstants.SELECT);
			if (paramList.size() > 0)
				return (WaQuestionDT) paramList.get(0);
		} catch (Exception ex) {
			logger
					.fatal("Exception in PageManagementDAOImpl.findWaQuestion for question identifer="
							+ waQuestionIdentifier + ": ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return waQuestionDT;

	}


	public WaQuestionDT findWaQuestionByUniqueNm(String uniqueNm)
			throws NEDSSDAOSysException, NEDSSSystemException {
		logger.debug("FIND_WA_QUESTION+WITH_QUESTION_IDENTIFIER="
				+ FIND_WA_QUESTION + WITH_QUESTION_NAME);

		WaQuestionDT waQuestionDT = new WaQuestionDT();
		ArrayList<Object> paramList = new ArrayList<Object>();
		paramList.add(uniqueNm);
		try {
			paramList = (ArrayList<Object>) preparedStmtMethod(waQuestionDT,
					paramList, FIND_WA_QUESTION + WITH_QUESTION_NAME,
					NEDSSConstants.SELECT);
			if (paramList.size() > 0)
				return (WaQuestionDT) paramList.get(0);
		} catch (Exception ex) {
			logger
					.fatal("Exception in PageManagementDAOImpl.findWaQuestionByUniqueNm for question name="
							+ uniqueNm + ": ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return waQuestionDT;

	}

	public WaQuestionDT findWaQuestionByDatamartColumn(String dmColumn)
			throws NEDSSDAOSysException, NEDSSSystemException {
		logger.debug("FIND_WA_QUESTION+WITH_QUESTION_IDENTIFIER="
				+ FIND_WA_QUESTION + WITH_DM_COLUMN);

		WaQuestionDT waQuestionDT = new WaQuestionDT();
		ArrayList<Object> paramList = new ArrayList<Object>();
		paramList.add(dmColumn);
		try {
			paramList = (ArrayList<Object>) preparedStmtMethod(waQuestionDT,
					paramList, FIND_WA_QUESTION + WITH_DM_COLUMN,
					NEDSSConstants.SELECT);
			if (paramList.size() > 0)
				return (WaQuestionDT) paramList.get(0);
		} catch (Exception ex) {
			logger
					.fatal("Exception in PageManagementDAOImpl.findWaQuestionByDMColumn for DMColumn name="
							+ dmColumn + ": ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return waQuestionDT;

	}
	
	public WaQuestionDT findWaQuestionByUserDefinedColumnName(String dmColumn)
			throws NEDSSDAOSysException, NEDSSSystemException {
		logger.debug("FIND_WA_QUESTION+WITH_QUESTION_IDENTIFIER="
				+ FIND_WA_QUESTION + WITH_USER_DEFINED_COLUMN_NM);

		WaQuestionDT waQuestionDT = new WaQuestionDT();
		ArrayList<Object> paramList = new ArrayList<Object>();
		paramList.add(dmColumn);
		try {
			paramList = (ArrayList<Object>) preparedStmtMethod(waQuestionDT,
					paramList, FIND_WA_QUESTION + WITH_USER_DEFINED_COLUMN_NM,
					NEDSSConstants.SELECT);
			if (paramList.size() > 0)
				return (WaQuestionDT) paramList.get(0);
		} catch (Exception ex) {
			logger
					.fatal("Exception in PageManagementDAOImpl.findWaQuestionByUserDefinedColumnName for userDefinedColumnNm name="
							+ dmColumn + ": ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return waQuestionDT;

	}
	

	/**
	 * creates an NBS UiMetadata
	 *
	 * @param dt
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	public Long insertWaQuestion(WaQuestionDT dt) throws NEDSSDAOSysException,
			NEDSSSystemException {
		
		try {
				// return insertSQLNBSDocument(nbsDocumentDT);
	
				Long waQuestionUid = null;
	
				ArrayList<Object> paramList = new ArrayList<Object>();
				// paramList.add(new Long(waQuestionUid));
				paramList.add(dt.getCodeSetGroupId());
				paramList.add(dt.getDataCd());
				paramList.add(dt.getDataLocation());
				paramList.add(dt.getQuestionIdentifier());
				paramList.add(dt.getQuestionOid());
				paramList.add(dt.getQuestionOidSystemTxt());

				// added by jayasudha to insert the code system name value 
				//paramList.add(dt.getCodeSysName());
				paramList.add(dt.getQuestionUnitIdentifier());
				paramList.add(dt.getDataType());
				paramList.add(dt.getDataUseCd());
				paramList.add(dt.getQuestionLabel());
				paramList.add(dt.getQuestionToolTip());
				paramList.add(dt.getUserDefinedColumnNm());
				paramList.add(dt.getPartTypeCd());
				paramList.add(dt.getAddTime());
				paramList.add(dt.getAddUserId());
				paramList.add(dt.getLastChgTime());
				paramList.add(dt.getLastChgUserId());
				paramList.add(dt.getDefaultValue());
				paramList.add(dt.getVersionCtrlNbr());
				paramList.add(dt.getUnitParentIndentifier());
				paramList.add(dt.getQuestionGroupSeqNbr());
				paramList.add(dt.getFutureDateIndCd());
				paramList.add(dt.getLegacyDataLocation());
				paramList.add(dt.getRepeatsIndCd());
				paramList.add(dt.getLocalId());
				paramList.add(dt.getQuestionNm());
				paramList.add(dt.getGroupNm());
				paramList.add(dt.getSubGroupNm());
				paramList.add(dt.getDescription());
				paramList.add(dt.getMask());
				paramList.add(dt.getFieldLength());
				paramList.add(dt.getNbsUiComponentUid());
				paramList.add(dt.getReportAdminColumnNm());
				paramList.add(dt.getNndMsgInd());
				paramList.add(dt.getQuestionReqNnd());
				paramList.add(dt.getQuestionIdentifierNnd());
				paramList.add(dt.getQuestionLabelNnd());
				paramList.add(dt.getQuestionDataTypeNnd());
				paramList.add(dt.getHl7Segment());
				paramList.add(dt.getOrderGrpId());
				paramList.add(dt.getRecordStatusCd());
				paramList.add(dt.getRecordStatusTime());
				paramList.add(dt.getQuestionType());
				paramList.add(dt.getEntryMethod());
				paramList.add(dt.getRdbTableNm());
				paramList.add(dt.getRdbcolumnNm());
				paramList.add(dt.getAdminComment());
				paramList.add(dt.getMinValue());
				paramList.add(dt.getMaxValue());
				paramList.add(dt.getStandardNndIndCd());
				paramList.add(dt.getStandardQuestionIndCd());
				paramList.add(dt.getUnitTypeCd());
				paramList.add(dt.getUnitValue());
				paramList.add(dt.getOtherValIndCd());
				paramList.add(dt.getCoInfQuestion());
				waQuestionUid = ((Long) preparedStmtMethodRetUid(null,
						paramList, CREATE_WA_QUESTION
								+ "  SELECT @@IDENTITY AS 'Identity'",
						NEDSSConstants.UPDATE));
				if (waQuestionUid == null) {
					logger.error("Exception in createWaQuestion: , "
							+ "waQuestionUid = " + waQuestionUid);
					throw new NEDSSSystemException(
							"Exception in createWaQuestion:");
				}

			
			return waQuestionUid;
		} catch (Exception ex) {
			logger.fatal("Exception in createWaQuestion: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}


	/**
	 * Update NBSUiMetadata comprises of INSERTING exiting row to HIST, and
	 * updating the current record
	 *
	 * @param dt
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	public void updateWaQuestion(WaQuestionDT dt) throws NEDSSDAOSysException,
			NEDSSSystemException {

		try {
			// History
			updateWaQuestionHistory(dt.getWaQuestionUid());
	
			dt.setVersionCtrlNbr(dt.getVersionCtrlNbr() + 1);
	
			ArrayList<Object> paramList = new ArrayList<Object>();
			paramList.add(dt.getCodeSetGroupId());
			paramList.add(dt.getDataCd());
			paramList.add(dt.getDataLocation());
			paramList.add(dt.getQuestionIdentifier());
			paramList.add(dt.getQuestionOid());
			paramList.add(dt.getQuestionOidSystemTxt());
			paramList.add(dt.getQuestionUnitIdentifier());
			paramList.add(dt.getDataType());
			paramList.add(dt.getDataUseCd());
			paramList.add(dt.getQuestionLabel());
			paramList.add(dt.getQuestionToolTip());
			paramList.add(dt.getUserDefinedColumnNm());
			paramList.add(dt.getPartTypeCd());
			paramList.add(dt.getAddTime());
			paramList.add(dt.getAddUserId());
			paramList.add(dt.getLastChgTime());
			paramList.add(dt.getLastChgUserId());
			paramList.add(dt.getDefaultValue());
			// paramList.add(dt.getPartTypeCd());
			paramList.add(dt.getVersionCtrlNbr());
			paramList.add(dt.getUnitParentIndentifier());
			paramList.add(dt.getQuestionGroupSeqNbr());
			paramList.add(dt.getFutureDateIndCd());
			paramList.add(dt.getLegacyDataLocation());
			paramList.add(dt.getRepeatsIndCd());
			paramList.add(dt.getLocalId());
			paramList.add(dt.getQuestionNm());
			paramList.add(dt.getGroupNm());
			paramList.add(dt.getSubGroupNm());
			paramList.add(dt.getDescription());
			paramList.add(dt.getMask());
			paramList.add(dt.getFieldLength());
			paramList.add(dt.getNbsUiComponentUid());
			paramList.add(dt.getReportAdminColumnNm());
			paramList.add(dt.getNndMsgInd());
			paramList.add(dt.getQuestionReqNnd());
			paramList.add(dt.getQuestionIdentifierNnd());
			paramList.add(dt.getQuestionLabelNnd());
			paramList.add(dt.getQuestionDataTypeNnd());
			paramList.add(dt.getHl7Segment());
			paramList.add(dt.getOrderGrpId());
			paramList.add(dt.getRecordStatusCd());
			paramList.add(dt.getRecordStatusTime());
			paramList.add(dt.getQuestionType());
			paramList.add(dt.getEntryMethod());
			paramList.add(dt.getRdbTableNm());
			paramList.add(dt.getRdbcolumnNm());
			paramList.add(dt.getAdminComment());
	        paramList.add(dt.getMinValue());
	        paramList.add(dt.getMaxValue());
	        paramList.add(dt.getUnitTypeCd());
			paramList.add(dt.getUnitValue());
			paramList.add(dt.getOtherValIndCd());
			paramList.add(dt.getCoInfQuestion());
	
	
			// where param
			paramList.add(dt.getWaQuestionUid());
			paramList.add(dt.getVersionCtrlNbr().intValue() - 1);
		
			preparedStmtMethod(null, paramList, UPDATE_WA_QUESTION,
					NEDSSConstants.UPDATE);

		} catch (Exception ex) {
			logger.fatal("Exception in updateWaQuestion: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	/**
	 * Delete NBSQuestion by metadata Uid
	 *
	 * @param nbsUiMetadataUid
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	public void deleteWaQuestion(WaQuestionDT dt) throws NEDSSDAOSysException,
			NEDSSSystemException {

		try {
			// History
			updateWaQuestionHistory(dt.getWaQuestionUid());
	
			ArrayList<Object> paramList = new ArrayList<Object>();
			paramList.add(dt.getRecordStatusTime());
			paramList.add(dt.getVersionCtrlNbr());
			paramList.add(dt.getLastChgTime());
			paramList.add(dt.getLastChgUserId());
			// where param
			paramList.add(dt.getWaQuestionUid());
		
			preparedStmtMethod(null, paramList, DELETE_WA_QUESTION,
					NEDSSConstants.UPDATE);

		} catch (Exception ex) {
			logger.fatal("Exception in deleteWaQuestion: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}

	}

	/**
	 * updateNBSUiMetadataHistory Copies the current record to the UiMetadata
	 * history table before updated
	 *
	 * @param questionUid
	 */
	public void updateWaQuestionHistory(Long waQuestionUid) {

		ArrayList<Object> paramList = new ArrayList<Object>();
		// where param
		paramList.add(waQuestionUid);

		try {
			preparedStmtMethod(null, paramList, INSERT_WA_QUESTION_HISTORY,
					NEDSSConstants.UPDATE);

		} catch (Exception ex) {
			logger.fatal("Exception in updateWaQuestionHistory: ERROR = " + ex.getMessage(),ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	/**
	 * Returns findWaNndMetadata searching by WaNndMetadataUid
	 *
	 * @param WaNndMetadataUid
	 * @return findWaNndMetadata
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	@SuppressWarnings("unchecked")
	public WaRdbMetadataDT findWaRdbMetadata(Long waRdbMetadataUid)
			throws NEDSSDAOSysException, NEDSSSystemException {

		WaRdbMetadataDT waRdbMetadataDT = new WaRdbMetadataDT();
		ArrayList<Object> paramList = new ArrayList<Object>();
		paramList.add(waRdbMetadataUid);
		try {
			paramList = (ArrayList<Object>) preparedStmtMethod(waRdbMetadataDT,
					paramList, FIND_WA_RDB_METADATA, NEDSSConstants.SELECT);
			if (paramList.size() > 0)
				return (WaRdbMetadataDT) paramList.get(0);
		} catch (Exception ex) {
			logger.fatal("Exception in findWaRdbMetadata: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return waRdbMetadataDT;

	}

	/**
	 * creates an NBS UiMetadata
	 *
	 * @param dt
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	public int createWaRdbMetadata(WaRdbMetadataDT dt)
			throws NEDSSDAOSysException, NEDSSSystemException {
		int resultCount = -1;
		try {
			
			//if(dt.getUserDefinedColumnNm()=="" && dt.getUserDefinedColumnNm()==null){
				
			//	dt.setUserDefinedColumnNm(dt.getRdbcolumNm()+"11111");
				
			//}
			ArrayList<Object> paramList = new ArrayList<Object>();
			paramList.add(dt.getWaTemplateUid());
			paramList.add(dt.getWaUiMetadataUid());
			paramList.add(dt.getRdbTableNm());
			paramList.add(dt.getRdbcolumNm());
			paramList.add(dt.getUserDefinedColumnNm());
			paramList.add(dt.getAddTime());
			paramList.add(dt.getAddUserId());
			paramList.add(dt.getLastChgTime());
			paramList.add(dt.getLastChgUserId());
			paramList.add(dt.getRecordStatusCd());
			paramList.add(dt.getRecordStatusTime());
			paramList.add(dt.getRptAdminColumnNm());
			paramList.add(dt.getQuestionIdentifier());
			paramList.add(dt.getDataMartRepeatNbr());
			
		
			resultCount = ((Integer) preparedStmtMethod(null, paramList,
					CREATE_WA_RDB_METADATA, NEDSSConstants.UPDATE)).intValue();
			if (resultCount != 1) {
				logger.error("Exception in createWaRdbMetadata: , "
						+ "resultCount = " + resultCount);
				throw new NEDSSSystemException(
						"Exception in createWaRdbMetadata:");
			}
			
			
		} catch (Exception ex) {
			String quesId =  "null";
			if (dt.getQuestionIdentifier() != null)
			  quesId = dt.getQuestionIdentifier();
			String tempUid =  "null";
			if (dt.getWaTemplateUid() != null)
				tempUid = dt.getWaTemplateUid().toString();
			logger.fatal("Exception in createWaRdbMetadata: TemplateUid = " + tempUid +  " QuestionId = " + quesId + " ERROR = " + ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return resultCount;
	}

	/**
	 * Update NBSUiMetadata comprises of INSERTING exiting row to HIST, and
	 * updating the current record
	 *
	 * @param dt
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	public void updateWaRdbMetadata(WaRdbMetadataDT dt)
			throws NEDSSDAOSysException, NEDSSSystemException {

		try {
			// History
			updateWaRdbMetadataHistory(dt.getWaQuestionUid(), BY_TABLE_UID,null);
	
			ArrayList<Object> paramList = new ArrayList<Object>();
	
			// paramList.add(dt.getWaQuestionUid());
			paramList.add(dt.getWaTemplateUid());
			paramList.add(dt.getRdbTableNm());
			paramList.add(dt.getUserDefinedColumnNm());
			paramList.add(dt.getAddTime());
			paramList.add(dt.getAddUserId());
			paramList.add(dt.getLastChgTime());
			paramList.add(dt.getLastChgUserId());
			paramList.add(dt.getRecordStatusCd());
			paramList.add(dt.getRecordStatusTime());
			paramList.add(dt.getVersionCtrlNbr());
			paramList.add(dt.getRptAdminColumnNm());
			paramList.add(dt.getDataMartRepeatNbr());
			
			// where param
			paramList.add(dt.getWaRdbMetadataUid());
			paramList.add(dt.getQuestionIdentifier());

		
			preparedStmtMethod(null, paramList, UPDATE_WA_RDB_METADATA,
					NEDSSConstants.UPDATE);

		} catch (Exception ex) {
			logger.fatal("Exception in updateWaRdbMetadata: ERROR = "  + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	/**
	 * Delete NBSQuestion by metadata Uid
	 *
	 * @param nbsUiMetadataUid
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	public void deleteWaRdbMetadata(WaRdbMetadataDT dt)
			throws NEDSSDAOSysException, NEDSSSystemException {

		try {
			// History
			updateWaRdbMetadataHistory(dt.getWaQuestionUid(), BY_TABLE_UID,null);
	
			ArrayList<Object> paramList = new ArrayList<Object>();
			paramList.add(dt.getRecStatusTime());
			paramList.add(dt.getVersionCtrlNbr());
			paramList.add(dt.getLastChgTime());
			paramList.add(dt.getLastChgUserId());
			// where param
			paramList.add(dt.getWaRdbMetadataUid());
		
			preparedStmtMethod(null, paramList, DELETE_WA_RDB_METADATA,
					NEDSSConstants.UPDATE);

		} catch (Exception ex) {
			logger.fatal("Exception in deleteWaRdbMetadata: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}

	}

	/**
	 * updateNBSUiMetadataHistory Copies the current record to the UiMetadata
	 * history table before updated
	 *
	 * @param questionUid
	 */
	public void updateWaRdbMetadataHistory(Long waRdbMetadataUid,
			String uidType, Long waTemplateHistUid)
			throws NEDSSDAOSysException, NEDSSSystemException {

		String sql = INSERT_WA_RDB_METADATA_HISTORY;
		String from = " FROM WA_RDB_metadata ";
		sql = sql + waTemplateHistUid + from;
		if (uidType.equalsIgnoreCase(BY_TABLE_UID))
			sql += WHERE_WA_RDB_META_DATA_UID;
		else
			sql += WHERE_WA_TEMPLATE_UID;

		ArrayList<Object> paramList = new ArrayList<Object>();
		// where param
		paramList.add(waRdbMetadataUid);

		try {
			preparedStmtMethod(null, paramList, sql, NEDSSConstants.UPDATE);

		} catch (Exception ex) {
			logger.fatal("Exception in updateWaQuestionHistory: ERROR = " + ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	/**
	 * Returns findWaNndMetadata searching by WaNndMetadataUid
	 *
	 * @param WaNndMetadataUid
	 * @return findWaNndMetadata
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	@SuppressWarnings("unchecked")
	public WaTemplateDT findWaTemplate(Long waTemplateUid)
			throws NEDSSDAOSysException, NEDSSSystemException {

		WaTemplateDT waTemplateDT = new WaTemplateDT();
		ArrayList<Object> paramList = new ArrayList<Object>();
		paramList.add(waTemplateUid);
		try {
			paramList = (ArrayList<Object>) preparedStmtMethod(waTemplateDT,
					paramList, FIND_WA_TEMPLATE, NEDSSConstants.SELECT);
			if (paramList.size() > 0)
				return (WaTemplateDT) paramList.get(0);
			
			if (waTemplateDT != null && waTemplateDT.getConditionCd() != null) {
				CachedDropDownValues cdv = new CachedDropDownValues();
				waTemplateDT.setConditionCdDesc(cdv.getConditionDesc(waTemplateDT
						.getConditionCd()));
			}
		} catch (Exception ex) {
			logger.fatal("Exception in findWaTemplate: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		
		return waTemplateDT;

	}
	
	
	/**
	 * createDataSourceDynamically():
	 * @param publicHealthCaseUid
	 */
	
		public void createDataSourceDynamically(Long templateUid, String dataMartName){

		 	Connection dbConnection = null;
	        CallableStatement sProc = null;

	         try
	         {
	             dbConnection = getConnection(NEDSSConstants.ODS);
	         }
	         catch(NEDSSSystemException nsex)
	         {
	             logger.fatal("Error obtaining dbConnection "+nsex.getMessage(), nsex);
	             throw new NEDSSSystemException(nsex.toString());
	         }

	         try
	         {
	        	 logger.debug("About to call stored procedure: createDataSource_sp");
	             String sQuery  = "{call createDataSource_sp(?,?)}"; 
	             logger.info("sQuery = " + sQuery);
	             sProc = dbConnection.prepareCall(sQuery);
	             logger.debug("After prepareCall");
	             sProc.setLong(1, templateUid);
	             sProc.setString(2, dataMartName);
	 	         
	             logger.debug("Before executing the query");
	             sProc.execute();
	             logger.debug("After executing the query");
	         }
	         catch(NEDSSSystemException nse)
	         {
	         	logger.fatal("Exception in NotificationMessageDAOImpl.createDataSourceDynamically: NEDSSSystemException  = "+nse.getMessage(), nse);
	             throw new NEDSSSystemException("Exception in NotificationMessageDAOImpl.createDataSourceDynamically: Error: while obtaining database connection.\n" + nse.getMessage(), nse);
	         }
	 		catch(Exception e)
	 		{
	 			logger.fatal("Exception in NotificationMessageDAOImpl.createDataSourceDynamically  = "+e.getMessage(), e);
	 		    throw new NEDSSSystemException("Exception in NotificationMessageDAOImpl.createDataSourceDynamically: = " + e.toString(),e);
	 		}
	         finally
	         {
	             closeCallableStatement(sProc);
	             releaseConnection(dbConnection);
	         }
		
	}
	
	
		/*
		 *This stored procedure resets the NBS_QUESTION data for datamart column name. 
		 * This was an issue as the datamart_column_nm field in NBS_QUESTION is used by TB_DATAMART and VAR_DATAMART 
		 * process to generate the datamart. Hence resetting as per https://nbsteamdev.atlassian.net/browse/ND-28853
		 * and https://cdcnbscentral.com/issues/15890 
		 */
		public void resetMetadataData(){

			 	Connection dbConnection = null;
		        CallableStatement sProc = null;

		         try
		         {
		             dbConnection = getConnection(NEDSSConstants.ODS);
		         }
		         catch(NEDSSSystemException nsex)
		         {
		             logger.fatal("Error obtaining dbConnection "+nsex.getMessage(), nsex);
		             throw new NEDSSSystemException(nsex.toString());
		         }

		         try
		         {
		        	 logger.debug("About to call stored procedure: RESET_METADATA_DATA_SP");
		             String sQuery  = "{call RESET_METADATA_DATA_SP()}"; 
		             logger.info("sQuery = " + sQuery);
		             sProc = dbConnection.prepareCall(sQuery);
		             logger.debug("After prepareCall");
 		 	         
		             logger.debug("Before executing the query");
		             sProc.execute();
		             logger.debug("After executing the query");
		         }
		         catch(NEDSSSystemException nse)
		         {
		         	logger.fatal("Exception in PageManagementDAOImpl.resetMetadataData: NEDSSSystemException  = "+nse.getMessage(), nse);
		             throw new NEDSSSystemException("Exception in PageManagementDAOImpl.resetMetadataData: Error: while obtaining database connection.\n" + nse.getMessage(), nse);
		         }
		 		catch(Exception e)
		 		{
		 			logger.fatal("Exception in PageManagementDAOImpl.resetMetadataData  = "+e.getMessage(), e);
		 		    throw new NEDSSSystemException("Exception in PageManagementDAOImpl.resetMetadataData: = " + e.toString(),e);
		 		}
		         finally
		         {
		             closeCallableStatement(sProc);
		             releaseConnection(dbConnection);
		         }
			
		}
		
	
	/**
	 * getWaRdbMetadaByTemplate: get the waRdbMetadata related to the template
	 * @param waTemplateUid
	 * @return
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	@SuppressWarnings("unchecked")
	public Collection<Object> getWaRdbMetadaByTemplate(Long waTemplateUid)
			throws NEDSSDAOSysException, NEDSSSystemException {

		WaRdbMetadataDT waRdbMetadataDT = new WaRdbMetadataDT();
		ArrayList<Object> paramList = new ArrayList<Object>();
		paramList.add(waTemplateUid);
		try {
			paramList = (ArrayList<Object>) preparedStmtMethod(waRdbMetadataDT,
					paramList, FIND_WA_RDB_METADATA_BY_TEMPLATE, NEDSSConstants.SELECT);
			//if (paramList.size() > 0)
				return (ArrayList<Object>) paramList;
			
		/*	if (waRdbMetadataDT != null && waRdbMetadataDT.getConditionCd() != null) {
				CachedDropDownValues cdv = new CachedDropDownValues();
				waRdbMetadataDT.setConditionCdDesc(cdv.getConditionDesc(waTemplateDT
						.getConditionCd()));
			}*/
		} catch (Exception ex) {
			logger.fatal("Exception in getWaRdbMetadaByTemplate: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		
	//	return waTemplateDT;

	}
	
	/**
	 * getPageMetadataByTemplate:
	 * @param waTemplateUid
	 * @return
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	@SuppressWarnings("unchecked")
	public Collection<Object> getPageMetadataByTemplate(Long waTemplateUid)
			throws NEDSSDAOSysException, NEDSSSystemException {

		PageMetadataDT pageMetadataDT = new PageMetadataDT();
		ArrayList<Object> paramList = new ArrayList<Object>();
		paramList.add(waTemplateUid);
		try {
			   paramList = (ArrayList<Object>) preparedStmtMethod(pageMetadataDT,
							paramList, FIND_PAGE_METADATA_BY_TEMPLATE_SQL, NEDSSConstants.SELECT);
	
				return (ArrayList<Object>) paramList;
			
		} catch (Exception ex) {
			logger.fatal("Exception in getPageMetadataByTemplate: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public Collection<Object> getPageVocabularyByTemplate(Long waTemplateUid)
			throws NEDSSDAOSysException, NEDSSSystemException {

		PageMetadataDT pageMetadataDT = new PageMetadataDT();
		ArrayList<Object> paramList = new ArrayList<Object>();
		paramList.add(waTemplateUid);
		paramList.add(waTemplateUid);
		
		try {
			   paramList = (ArrayList<Object>) preparedStmtMethod(pageMetadataDT,
							paramList, FIND_PAGE_VOCABULARY_BY_TEMPLATE_SQL, NEDSSConstants.SELECT);
	
				return (ArrayList<Object>) paramList;
			
		} catch (Exception ex) {
			logger.fatal("Exception in getPageMetadataByTemplate: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public Collection<Object> getPageQuestionVocabularyByTemplate(Long waTemplateUid)
			throws NEDSSDAOSysException, NEDSSSystemException {

		PageMetadataDT pageMetadataDT = new PageMetadataDT();
		ArrayList<Object> paramList = new ArrayList<Object>();
		paramList.add(waTemplateUid);
		try {
			   paramList = (ArrayList<Object>) preparedStmtMethod(pageMetadataDT,
							paramList, FIND_PAGE_QUESTION_VOCABULARY_BY_TEMPLATE_SQL, NEDSSConstants.SELECT);
	
				return (ArrayList<Object>) paramList;
			
		} catch (Exception ex) {
			logger.fatal("Exception in getPageMetadataByTemplate: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}
	
	

	/**
	 * Returns findWaNndMetadata searching by WaNndMetadataUid
	 *
	 * @param WaNndMetadataUid
	 * @return findWaNndMetadata
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	@SuppressWarnings("unchecked")
	public Collection<Object> findWaTemplateUidByCondition(String conditionCd)
			throws NEDSSDAOSysException, NEDSSSystemException {
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		ArrayList<Object> templateUids = new ArrayList<Object>();

		try {
			dbConnection = getConnection();
			preparedStmt = dbConnection
					.prepareStatement(FIND_WA_TEMPLATE_UID_BY_CONDITION);
			preparedStmt.setString(1, conditionCd);
			resultSet = preparedStmt.executeQuery();

			while (resultSet.next()) {
				templateUids.add(resultSet.getLong(1));
			}
		} catch (Exception ex) {
			logger.fatal("Exception in findWaTemplateUidByCondition: ERROR = "
					+ ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}

		return templateUids;
	}

	/**
	 * Returns Collection of WaTemplateDT's
	 *
	 * @param waQuestionIdentifier
	 * @return WaQuestionDT
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	@SuppressWarnings("unchecked")
	public Map<Object, Object> retrieveWaTemplateSummaries()
			throws NEDSSDAOSysException, NEDSSSystemException {
		logger.debug("RETRIEVE_WA_TEMPLATE_SUMMARIES="
				+ RETRIEVE_WA_TEMPLATE_SUMMARIES);
        Map<Object, Object> returnedMap = new HashMap<Object, Object>();
		WaTemplateDT waTemplateDT = new WaTemplateDT();
		PageCondMappingDT pageCondMappingDT = new PageCondMappingDT();
		ArrayList<Object> waTemplateDTCollection = new ArrayList<Object>();
		ArrayList<Object> pageCondMappingDTCollection = new ArrayList<Object>();
		try {


			waTemplateDTCollection = (ArrayList<Object>) preparedStmtMethod(
					waTemplateDT, waTemplateDTCollection,
					RETRIEVE_WA_TEMPLATE_SUMMARIES_SQL, NEDSSConstants.SELECT);
	    	pageCondMappingDTCollection = (ArrayList<Object>) preparedStmtMethod(
	    			pageCondMappingDT, pageCondMappingDTCollection,
					PAGE_COND_MAPPING_SUMMARY_SQL, NEDSSConstants.SELECT);
		    returnedMap.put(NEDSSConstants.TEMPLATE_SUMMARIES, waTemplateDTCollection);
		    returnedMap.put(NEDSSConstants.PAGE_COND_MAPPING_SUMMARIES, pageCondMappingDTCollection);
		} catch (Exception ex) {
			logger
					.fatal("Exception in PageManagementDAOImpl.retrieveWaTemplateSummaries - ERROR = "
							+ ex.getMessage(),ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return returnedMap;

	}

	/**
	 * creates an NBS UiMetadata
	 *
	 * @param dt
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	public Long createWaTemplate(WaTemplateDT dt) throws NEDSSDAOSysException,
			NEDSSSystemException {
		try{
			return createWaTemplateSQL(dt);
		}catch(Exception ex){
			logger.fatal("Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	private Long createWaTemplateSQL(WaTemplateDT dt)
			throws NEDSSDAOSysException, NEDSSSystemException {
		Long waTemplateUid = null;
		ArrayList<Object> paramList = new ArrayList<Object>();
		paramList.add(dt.getTemplateType());
		paramList.add(dt.getXmlPayload());
		paramList.add(dt.getPublishVersionNbr());
		paramList.add(dt.getFormCd());
		paramList.add(dt.getConditionCd());
		paramList.add(dt.getBusObjType());
		paramList.add(dt.getDataMartNm());
		paramList.add(dt.getLastChgTime());
		paramList.add(dt.getLastChgUserId());
		paramList.add(dt.getRecStatusCd());
		paramList.add(dt.getRecStatusTime());
		paramList.add(dt.getDescTxt());
		paramList.add(dt.getTemplateNm());
		paramList.add(dt.getAddTime());
		paramList.add(dt.getAddUserId());
		paramList.add(dt.getSourceNm());
		paramList.add(dt.getPublishIndCd());
		paramList.add(dt.getVersionNote());
		paramList.add(dt.getMessageId());

		try {
			waTemplateUid = ((Long) preparedStmtMethodRetUid(null, paramList,
					CREATE_WA_TEMPLATE + "  SELECT @@IDENTITY AS 'Identity'",
					NEDSSConstants.UPDATE));
		} catch (Exception ex) {
			logger
					.fatal("Exception in PageManagementDAOImpl.createWaTemplate: ERROR = "
							+ ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return waTemplateUid;
	}

	/**
	 * Update NBSUiMetadata comprises of INSERTING exiting row to HIST, and
	 * updating the current record
	 *
	 * @param dt
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	public void updateWaTemplate(WaTemplateDT dt) throws NEDSSDAOSysException,
			NEDSSSystemException {
		try{
			updateWaTemplateSQL(dt);
		}catch(Exception ex){
			logger.fatal("Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	private void updateWaTemplateSQL(WaTemplateDT dt)
			throws NEDSSDAOSysException, NEDSSSystemException {
		ArrayList<Object> paramList = new ArrayList<Object>();

		paramList.add(dt.getTemplateType());
		paramList.add(dt.getXmlPayload());
		paramList.add(dt.getPublishVersionNbr());
		paramList.add(dt.getFormCd());
		paramList.add(dt.getConditionCd());
		paramList.add(dt.getBusObjType());
		paramList.add(dt.getDataMartNm());
		paramList.add(dt.getLastChgTime());
		paramList.add(dt.getLastChgUserId());
		paramList.add(dt.getRecStatusCd());
		paramList.add(dt.getRecStatusTime());
		paramList.add(dt.getDescTxt());
		paramList.add(dt.getPublishIndCd());
		paramList.add(dt.getTemplateNm());
		paramList.add(dt.getMessageId());
		paramList.add(dt.getVersionNote());
		// where param
		paramList.add(dt.getWaTemplateUid());

		try {
			preparedStmtMethod(null, paramList, UPDATE_WA_TEMPLATE_SQL,
					NEDSSConstants.UPDATE);

		} catch (Exception ex) {
			logger.fatal("Exception in updateWaTemplate: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	/**
	 * Delete NBSQuestion by metadata Uid
	 *
	 * @param nbsUiMetadataUid
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	public void deleteWaTemplate(WaTemplateDT dt) throws NEDSSDAOSysException,
			NEDSSSystemException {

		// History
		updateWaTemplateHistory(dt.getWaTemplateUid());

		ArrayList<Object> paramList = new ArrayList<Object>();
		paramList.add(dt.getRecStatusTime());
		paramList.add(dt.getVersionCtrlNbr());
		paramList.add(dt.getLastChgTime());
		paramList.add(dt.getLastChgUserId());
		// where param
		paramList.add(dt.getWaTemplateUid());
		try {
			preparedStmtMethod(null, paramList, DELETE_WA_TEMPLATE,
					NEDSSConstants.UPDATE);

		} catch (Exception ex) {
			logger.fatal("Exception in deleteWaTemplate: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}

	}

	/**
	 * updateNBSUiMetadataHistory Copies the current record to the UiMetadata
	 * history table before updated
	 *
	 * @param questionUid
	 */
	public Long updateWaTemplateHistory(Long waTemplateUid)
			throws NEDSSDAOSysException, NEDSSSystemException {
		try{
					ArrayList<Object> paramList = new ArrayList<Object>();
					// where param
					paramList.add(waTemplateUid);
					Long waTemplateHistUid = null;
	
					try {
						waTemplateHistUid = ((Long) preparedStmtMethodRetUid(null,
								paramList, INSERT_WA_TEMPLATE_HISTORY
										+ "  SELECT @@IDENTITY AS 'Identity'",
								NEDSSConstants.UPDATE));
						// preparedStmtMethod(null, paramList, INSERT_WA_TEMPLATE_HISTORY,
						// NEDSSConstants.UPDATE);
	
					} catch (Exception ex) {
						logger.fatal("Exception in updateWaTemplateHistory: ERROR = " + ex);
						throw new NEDSSSystemException(ex.toString());
					}
					return waTemplateHistUid;
		}catch(Exception ex){
			logger.fatal("Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}


	/**
	 * Returns findWaNndMetadata searching by WaNndMetadataUid
	 *
	 * @param WaNndMetadataUid
	 * @return findWaNndMetadata
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	@SuppressWarnings("unchecked")
	public WaUiMetadataDT findUiMetadata(Long waUiMetadataUid)
			throws NEDSSDAOSysException, NEDSSSystemException {

		WaUiMetadataDT waUiMetadataDT = new WaUiMetadataDT();
		ArrayList<Object> paramList = new ArrayList<Object>();
		paramList.add(waUiMetadataUid);
		try {
			paramList = (ArrayList<Object>) preparedStmtMethod(waUiMetadataDT,
					paramList, FIND_WA_UI_METADATA, NEDSSConstants.SELECT);
			if (paramList.size() > 0)
				return (WaUiMetadataDT) paramList.get(0);
		} catch (Exception ex) {
			logger.fatal("Exception in findUiMetadata: ERROR = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return waUiMetadataDT;

	}

	/**
	 * creates an NBS UiMetadata
	 *
	 * @param dt
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	public Long createWaUiMetadata(WaUiMetadataDT dt)
			throws NEDSSDAOSysException, NEDSSSystemException {


		Long waUimetadataUid = null;
				ArrayList<Object> paramList = new ArrayList<Object>();
				paramList.add(dt.getWaTemplateUid());
				paramList.add(dt.getNbsUiComponentUid());
				// paramList.add(dt.getWaQuestionUid());
				paramList.add(dt.getParentUid());
				paramList.add(dt.getQuestionLabel());
				paramList.add(dt.getQuestionToolTip());
				paramList.add(dt.getEnableInd());
				paramList.add(dt.getDefaultValue());
				paramList.add(dt.getDisplayInd());
				paramList.add(dt.getOrderNbr());
				paramList.add(dt.getRequiredInd());
				// paramList.add(dt.getTabOrderId());
				// paramList.add(dt.getTabName());
				paramList.add(dt.getAddTime());
				paramList.add(dt.getAddUserId());
				paramList.add(dt.getLastChgTime());
				paramList.add(dt.getLastChgUserId());
				paramList.add(dt.getRecordStatusCd());
				paramList.add(dt.getRecordStatusTime());
				paramList.add(dt.getMaxLength());
				// paramList.add(dt.getCssStyle());
				paramList.add(dt.getVersionCtrlNbr());
				paramList.add(dt.getAdminComment());
				paramList.add(dt.getFieldLength());
				paramList.add(dt.getFutureDateIndCd());
				paramList.add(dt.getLocalId());
				paramList.add(dt.getCodeSetGroupId());
				paramList.add(dt.getDataType());
				paramList.add(dt.getQuestionIdentifier());
				paramList.add(dt.getMask());
				paramList.add(dt.getQuestionUnitIdentifier());
				paramList.add(dt.getPublishIndCd());
				paramList.add(dt.getDataCd());
				paramList.add(dt.getDataLocation());
				paramList.add(dt.getStandardNndIndCd());
				paramList.add(dt.getPartTypeCd());
				paramList.add(dt.getUnitParentIndentifier());
				paramList.add(dt.getDataUseCd());
				paramList.add(dt.getLegacyDataLocation());
				paramList.add(dt.getQuestionOid());
				paramList.add(dt.getQuestionOidSystemTxt());
				paramList.add(dt.getQuestionNm());
				paramList.add(dt.getStandardQuestionIndCd());
				paramList.add(dt.getQuestionType());
				paramList.add(dt.getGroupNm());
				paramList.add(dt.getSubGroupNm());
				paramList.add(dt.getDescTxt());
				paramList.add(dt.getEntryMethod());
				paramList.add(dt.getUnitTypeCd());
				paramList.add(dt.getUnitValue());
				paramList.add(dt.getOtherValIndCd());
		        paramList.add(dt.getMinValue());
		        paramList.add(dt.getMaxValue());
		        paramList.add(dt.getQuestionGroupSeqNbr());
		        paramList.add(dt.getBatchTableColumnWidth());
		        paramList.add(dt.getBatchTableHeader());
		        paramList.add(dt.getBatchTableAppearIndCd());
		        paramList.add(dt.getCoinfectionIndCd());
		        paramList.add(dt.getBlockName());
		       // paramList.add(dt.getDataMartRepeatNumber());
				try {
					waUimetadataUid = ((Long) preparedStmtMethodRetUid(
							null,
							paramList,
							CREATE_WA_UI_METADATA + "  SELECT @@IDENTITY AS 'Identity'",
							NEDSSConstants.UPDATE));
					if (waUimetadataUid == null) {
						logger.error("Exception in createWaUiMetadata: , "
								+ "waUimetadataUid = " + waUimetadataUid);
						throw new NEDSSSystemException(
								"Exception in createWaUiMetadata:");
					}
				} catch (Exception ex) {
					logger.fatal("Exception in createWaUiMetadata: ERROR = " +ex.getMessage(), ex);
					logger.error("Exception in createWaUiMetadata: /n ERROR" +dt.toString());
					throw new NEDSSSystemException(ex.toString());
				}
				return waUimetadataUid;
 }


	
	/**
	 * Update NBSUiMetadata comprises of INSERTING exiting row to HIST, and
	 * updating the current record
	 *
	 * @param dt
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	public void updateWaUiMetadata(WaUiMetadataDT dt)
			throws NEDSSDAOSysException, NEDSSSystemException {

		try {
			// History
			updateWaUiMetadataHistory(dt.getWaUiMetadataUid(), BY_TABLE_UID,null);
	
			ArrayList<Object> paramList = new ArrayList<Object>();
	
			paramList.add(dt.getWaTemplateUid());
			paramList.add(dt.getNbsUiComponentUid());
			paramList.add(dt.getWaQuestionUid());
			paramList.add(dt.getParentUid());
			paramList.add(dt.getQuestionLabel());
			paramList.add(dt.getQuestionToolTip());
			paramList.add(dt.getEnableInd());
			paramList.add(dt.getDefaultValue());
			paramList.add(dt.getDisplayInd());
			paramList.add(dt.getOrderNbr());
			paramList.add(dt.getRequiredInd());
			// paramList.add(dt.getTabOrderId());
			// paramList.add(dt.getTabName());
			paramList.add(dt.getAddTime());
			paramList.add(dt.getAddUserId());
			paramList.add(dt.getLastChgTime());
			paramList.add(dt.getLastChgUserId());
			paramList.add(dt.getRecordStatusCd());
			paramList.add(dt.getRecordStatusTime());
			paramList.add(dt.getMaxLength());
			// paramList.add(dt.getCssStyle());
			paramList.add(dt.getVersionCtrlNbr());
			paramList.add(dt.getAdminComment());
			paramList.add(dt.getFieldLength());
			paramList.add(dt.getFutureDateIndCd());
			paramList.add(dt.getLocalId());
			paramList.add(dt.getMask());
	        paramList.add(dt.getMinValue());
	        paramList.add(dt.getMaxValue());
	
	
			// where param
			paramList.add(dt.getWaUiMetadataUid());

			preparedStmtMethod(null, paramList, UPDATE_WA_UI_METADATA,
					NEDSSConstants.UPDATE);

		} catch (Exception ex) {
			logger.fatal("Exception in updateWaUiMetadata: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	/**
	 * Delete NBSQuestion by metadata Uid
	 *
	 * @param nbsUiMetadataUid
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	public void deleteWaUiMetadata(WaUiMetadataDT dt)
			throws NEDSSDAOSysException, NEDSSSystemException {

		try {
			// History
			updateWaUiMetadataHistory(dt.getWaUiMetadataUid(), BY_TABLE_UID,null);
	
			ArrayList<Object> paramList = new ArrayList<Object>();
			paramList.add(dt.getRecordStatusTime());
			paramList.add(dt.getVersionCtrlNbr());
			paramList.add(dt.getLastChgTime());
			paramList.add(dt.getLastChgUserId());
			// where param
			paramList.add(dt.getWaUiMetadataUid());
		
			preparedStmtMethod(null, paramList, DELETE_WA_UI_METADATA,
					NEDSSConstants.UPDATE);

		} catch (Exception ex) {
			logger.fatal("Exception in deleteWaUiMetadata: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}

	}

	/**
	 * updateNBSUiMetadataHistory Copies the current record to the UiMetadata
	 * history table before updated
	 *
	 * @param questionUid
	 */
	public void updateWaUiMetadataHistory(Long waUiMetatdataUid,
			String uidType, Long waTemplateHistUid)
			throws NEDSSDAOSysException, NEDSSSystemException {

		String sql = INSERT_WA_UI_METADATA_HISTORY;
		String from = " FROM wa_ui_metadata ";
		sql = sql + waTemplateHistUid + from;
		if (uidType.equalsIgnoreCase(BY_TABLE_UID))
			sql += WHERE_WA_UI_META_DATA_UID;
		else
			sql += WHERE_WA_TEMPLATE_UID;

		ArrayList<Object> paramList = new ArrayList<Object>();
		// where param
		paramList.add(waUiMetatdataUid);

		try {
			preparedStmtMethod(null, paramList, sql, NEDSSConstants.UPDATE);

		} catch (Exception ex) {
			logger.fatal("Exception in updateWaUiMetadataHistory: ERR	OR = "
					+ ex.getMessage(), ex);

			throw new NEDSSSystemException(ex.toString());
		}
	}

	public Long getMaxWaQuestionUid() throws NEDSSDAOSysException,
			NEDSSSystemException {

		ArrayList<Object> paramList = new ArrayList<Object>();
		Long maxwaQuestionUid = null;

		try {
			UidGeneratorHelper uidGen = new UidGeneratorHelper();
	        maxwaQuestionUid = uidGen.getNbsIDLong(UidClassCodes.NBS_QUESTION_ID_LDF_CLASS_CODE).longValue();
		} catch (Exception ex) {
			logger.fatal("Exception in findWaNndMetadata: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return maxwaQuestionUid;

	}

	/**
	 * Gets the WaQuestionDT Collection<Object> - library of Active questions
	 *
	 * @return Collection<Object> of WaQuestionDT
	 */
	public Collection<Object> getWaQuestionDTCollection()
			throws NEDSSSystemException {
		logger.debug("SELECT_WA_QUESTION_COLLECTION="
				+ SELECT_WA_QUESTION_COLLECTION);

		WaQuestionDT waQuestionDT = new WaQuestionDT();
		ArrayList<Object> waQuestionDTCollection = new ArrayList<Object>();

		try {
			waQuestionDTCollection = (ArrayList<Object>) preparedStmtMethod(
					waQuestionDT, waQuestionDTCollection,
					SELECT_WA_QUESTION_COLLECTION, NEDSSConstants.SELECT);
		} catch (Exception ex) {
			String exString = "Exception in PageManagementDAOImpl.getWaQuestionDTCollection:  ERROR = "
					+ ex.getMessage();
			logger.fatal(exString, ex);
			throw new NEDSSSystemException(exString);
		}

		return waQuestionDTCollection;
	}

	/**
	 * Returns findWaNndMetadata searching by WaNndMetadataUid
	 *
	 * @param WaNndMetadataUid
	 * @return findWaNndMetadata
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	@SuppressWarnings("unchecked")
	public ArrayList searchWaQuestions(String whereclause)
			throws NEDSSDAOSysException, NEDSSSystemException {

		ArrayList<Object> qList = new ArrayList<Object>();
		WaQuestionDT waQuestionDT = new WaQuestionDT();
		String codeSql = SEARCH_WA_QUESTIONS;
		codeSql = codeSql + whereclause;

		try {
			qList = (ArrayList<Object>) preparedStmtMethod(waQuestionDT, qList,
					codeSql, NEDSSConstants.SELECT);

		} catch (Exception ex) {
			logger.fatal("Exception in searchWaQuestions: ERROR = " + ex.getMessage(),ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return qList;

	}

	/**
	 * Returns Moves all data in WA UI, NND and RDB Metadata tables to history
	 *
	 * @param waTemplateUid
	 * @return void
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	@SuppressWarnings("unchecked")
	public void movePublishedPageToHistory(Long waTemplateUid, PageManagementProxyVO pageManagementProxyVO)
			throws NEDSSDAOSysException, NEDSSSystemException {
		try{
			Long waTemplateHistUid = updateWaTemplateHistory(waTemplateUid);
			updateWaUiMetadataHistory(waTemplateUid, BY_TEMPLATE_UID,
					waTemplateHistUid);
			updateWaRdbMetadataHistory(waTemplateUid, BY_TEMPLATE_UID,
					waTemplateHistUid);
			updateWaNndMetadataHistory(waTemplateUid, BY_TEMPLATE_UID,
					waTemplateHistUid);
			updatePageCondMappingHistory(waTemplateUid, BY_TEMPLATE_UID,
					waTemplateHistUid);

			//Moving rules to History
			WaRuleMetadataDaoImpl waRuleMetadao = new WaRuleMetadataDaoImpl();
			Collection<Object> waRuleMetadataColl = waRuleMetadao.selectWaRuleMetadataDTByTemplate(waTemplateUid);
			if (waRuleMetadataColl != null) {
				Iterator<Object> iter = waRuleMetadataColl.iterator();
				while (iter.hasNext()) {
					WaRuleMetadataDT waRuleMetadataDT  = (WaRuleMetadataDT) iter.next();
					waRuleMetadao.insertWaRulesHist(waRuleMetadataDT);
				}
			}

			deleteMetadataByWaTemplateUid(waTemplateUid);


			//Added new -  Deleting the wa template entry also - test it properly
			ArrayList<Object> paramList = new ArrayList<Object>();
			paramList.add(waTemplateUid);
			preparedStmtMethod(null, paramList,
					DELETE_WA_TEMPLATE_BY_TEMPLATE_UID,
					NEDSSConstants.UPDATE);
		}catch (Exception e){
			logger.error("Error in inserting to history and deleting from the template file ", e.getMessage(), e);
			 throw new NEDSSSystemException(e.toString());
		}
	}

	public void deleteMetadataByWaTemplateUid(Long waTemplateUid)
			throws NEDSSDAOSysException, NEDSSSystemException {
		ArrayList<Object> paramList = new ArrayList<Object>();
		paramList.add(waTemplateUid);
		try {
			preparedStmtMethod(null, paramList,
					DELETE_WA_NND_METADATA_BY_TEMPLATE_UID,
					NEDSSConstants.UPDATE);
			preparedStmtMethod(null, paramList,
					DELETE_WA_RDB_METADATA_BY_TEMPLATE_UID,
					NEDSSConstants.UPDATE);
			preparedStmtMethod(null, paramList,
					DELETE_WA_UI_METADATA_BY_TEMPLATE_UID,
					NEDSSConstants.UPDATE);
			preparedStmtMethod(null, paramList,
					DELETE_WA_RULE_METADATA_BY_TEMPLATE_UID,
					NEDSSConstants.UPDATE);
			preparedStmtMethod(null, paramList,
					DELETE_PAGE_COND_MAPPING_BY_TEMPLATE_UID,
					NEDSSConstants.UPDATE);

		} catch (Exception ex) {
			logger
					.fatal("Exception in PageManagementDAOImpl.moveDraftToHistory - for waTemplateUid = "
							+ waTemplateUid + ", ERROR = " + ex.getMessage(),ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	public void deleteTemplate(Long waTemplateUid) throws NEDSSDAOSysException,
			NEDSSSystemException {
		ArrayList<Object> paramList = new ArrayList<Object>();
		paramList.add(waTemplateUid);
		try {
			preparedStmtMethod(null, paramList, DELETE_WA_TEMPLATE_BY_TEMPLATE_UID, NEDSSConstants.UPDATE);
		} catch (Exception ex) {
			logger.fatal("Exception in PageManagementDAOImpl.deleteTemplate - for waTemplateUid = "
							+ waTemplateUid + ", ERROR = " + ex.getMessage(),ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	/**
	 * deleteDPatientQuestionsFromWaRdbMetadata: ND-12385 and ND-12377
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	public void deleteDPatientQuestionsFromWaRdbMetadata() throws NEDSSDAOSysException,
			NEDSSSystemException {
		String codeSql = null;
		try{
			codeSql = DELETE_D_PATIENT;
			logger.debug("DELETE_D_PATIENT=" + codeSql);
			ArrayList<Object> paramList = new ArrayList<Object>();		
			preparedStmtMethod(null, paramList, DELETE_D_PATIENT,
					NEDSSConstants.UPDATE);
		} catch (Exception ex) {
			String exString = "Exception in PageManagementDAOImpl.getPageElementDTCollection:  ERROR = "
					+ ex.getMessage();
			logger.fatal(exString, ex);
			throw new NEDSSSystemException(exString);
		}

		
	}
	
	
	
	public Collection<Object> getPageElementVOCollection(Long waTemplateUid,
			boolean pageBuilderInd) throws NEDSSDAOSysException,
			NEDSSSystemException {
		String codeSql = null;
		if (pageBuilderInd) {
			codeSql = FIND_PAGE_ELEMENT_DETAILS_FOR_PAGE;
		} else {
			codeSql = FIND_PAGE_ELEMENT_DETAILS;
		}
		logger.debug("FIND_PAGE_ELEMENT_DETAILS=" + codeSql);

		ArrayList<Object> pageElementQueryCollection = new ArrayList<Object>();
		ArrayList<Object> pageElementCollection = new ArrayList<Object>();
		WaAggregatePageElementDT waAggregatePageElementDT = new WaAggregatePageElementDT();
		ArrayList<Object> pageElementVOCollection = new ArrayList<Object>();
		pageElementQueryCollection.add(waTemplateUid);

		try {
			pageElementCollection = (ArrayList<Object>) preparedStmtMethod(
					waAggregatePageElementDT, pageElementQueryCollection,
					codeSql, NEDSSConstants.SELECT);
			
			Map<String, PageElementVO> mapRepeatingSubsection = new HashMap<String, PageElementVO>();
			
			Iterator<Object> pageElementIter = pageElementCollection.iterator();
			while (pageElementIter.hasNext()) {
				PageElementVO pageElementVO = copyToPageElementDT((WaAggregatePageElementDT) pageElementIter.next());
				//Store in the map those elements from a repeating subsection
				
				//storeInMapIfRepeatingSubsectionElement(mapRepeatingSubsection, pageElementVO);

				pageElementVOCollection.add(pageElementVO);
			}
			PageManagementHelper pageManagementHelper = new PageManagementHelper();
			pageManagementHelper.copyDataMartRepeatNumberInRepeatingSubsectionsFromRepeatingQuestions(pageElementVOCollection);
			
			//setDataMartRepeatNumberInSubsection(mapRepeatingSubsection, pageElementVOCollection);
			
		} catch (Exception ex) {
			String exString = "Exception in PageManagementDAOImpl.getPageElementDTCollection:  ERROR = "
					+ ex.getMessage();
			logger.fatal(exString, ex);
			throw new NEDSSSystemException(exString);
		}

		return pageElementVOCollection;
	}

	/**
	 * setDataMartRepeatNumberInSubsection: iterates through the collection, in case is a repeating section, it get from the map the data mart repeat number for any of the questions in the subsection
	 * @param mapRepeatingSubsection
	 * @param pageElementVOCollection
	 */
	public void setDataMartRepeatNumberInSubsection(Map<String, PageElementVO> mapRepeatingSubsection,  ArrayList<Object>  pageElementVOCollection){
		
		Iterator<Object> pageElementIter2 = pageElementVOCollection.iterator();
		
		while (pageElementIter2.hasNext()) {
			
			PageElementVO pageElem = (PageElementVO)pageElementIter2.next();
			if(((pageElem).getWaUiMetadataDT()).getQuestionGroupSeqNbr()!=null){//is a repeating subsection
				int questionGroupSeqNbr = ((pageElem).getWaUiMetadataDT()).getQuestionGroupSeqNbr();
				//if(questionGroupSeqNbr!=0){//is a repeating subsection
				
					if(pageElem.getWaUiMetadataDT().getNbsUiComponentUid()==1016){//subsection
					
						PageElementVO pageElementRepeating = mapRepeatingSubsection.get(questionGroupSeqNbr+"");
						if(pageElementRepeating!=null && pageElem.getWaRdbMetadataDT()!=null && pageElementRepeating.getWaRdbMetadataDT()!=null)
							pageElem.getWaRdbMetadataDT().setDataMartRepeatNbr(pageElementRepeating.getWaRdbMetadataDT().getDataMartRepeatNbr());
					}
					
					
			//	}
				
				
			}
		}
		
	}
	
	/**
	 * storeInMapIfRepeatingSubsectionElement: this method stores in a map each of the repeating question, with key the question group seq number.
	 * This will help to find the repeat data mart number for the subsection base of the repeat data mart number of each of the questions in the subsection
	 * @param mapRepeatingSubsection
	 * @param pageElementVO
	 */
	public void storeInMapIfRepeatingSubsectionElement(Map<String, PageElementVO>mapRepeatingSubsection, PageElementVO pageElementVO){
		try{
		if(pageElementVO!=null && pageElementVO.getWaUiMetadataDT()!=null && pageElementVO.getWaUiMetadataDT().getQuestionGroupSeqNbr()!=null){
			int groupSeqNbr = pageElementVO.getWaUiMetadataDT().getQuestionGroupSeqNbr();
			mapRepeatingSubsection.put(""+groupSeqNbr,pageElementVO);
		}
		}catch(Exception e){
			logger.error("Error in storeInMapIfRepeatingSubsectionElement "+pageElementVO.getWaUiMetadataDT().getQuestionIdentifier(), e);
			throw new NEDSSSystemException(e.toString());
			
		}
		
	}
	public void initializeNewPageWithTemplate(Long waTemplateUid,
			Long waTemplateRefUid,Long currentUser,boolean addInd) throws NEDSSDAOSysException,
			NEDSSSystemException {

		Collection<Object> pageElementDTCollection = getPageElementVOCollection(waTemplateRefUid, false);
		
		PageManagementHelper pageManagementHelper = new PageManagementHelper();
		
		pageManagementHelper.copyDataMartRepeatNumberFromRepeatingSubsectionsToQuestion(pageElementDTCollection);
		
		Iterator<Object> iter = pageElementDTCollection.iterator();
		Timestamp currentTime = new Timestamp(new Date().getTime());

		while (iter.hasNext()) {

			PageElementVO pageElementVO = (PageElementVO) iter.next();
			Long waUiMetadataUid = null;
			try {

				WaUiMetadataDT waUiMetaDataDT = pageElementVO.getWaUiMetadataDT();
				if (waUiMetaDataDT != null) {
					waUiMetaDataDT.setWaUiMetadataUid(null);
					waUiMetaDataDT.setWaTemplateUid(waTemplateUid);
					waUiMetaDataDT.setAddTime(currentTime);
					waUiMetaDataDT.setAddUserId(currentUser);
					/*
					 * Setting this to false while creating a new draft
					 * Setting to true while creating a draft from the published page.
					 */

					if(addInd){
						waUiMetaDataDT.setPublishIndCd(NEDSSConstants.FALSE);
					}else{
						waUiMetaDataDT.setPublishIndCd(NEDSSConstants.TRUE);
					}
					
					waUiMetadataUid = createWaUiMetadata(waUiMetaDataDT);
				}

				WaRdbMetadataDT WaRdbMetaDataDT = pageElementVO.getWaRdbMetadataDT();
				if (WaRdbMetaDataDT != null) {
					WaRdbMetaDataDT.setWaRdbMetadataUid(null);
					WaRdbMetaDataDT.setWaTemplateUid(waTemplateUid);
					WaRdbMetaDataDT.setAddTime(currentTime);
					WaRdbMetaDataDT.setAddUserId(currentUser);
					WaRdbMetaDataDT.setWaUiMetadataUid(waUiMetadataUid);
					createWaRdbMetadata(WaRdbMetaDataDT);
				}

				WaNndMetadataDT waNndMetaDataDT = pageElementVO.getWaNndMetadataDT();
				if (waNndMetaDataDT != null) {
					waNndMetaDataDT.setWaNndMetadataUid(null);
					waNndMetaDataDT.setWaTemplateUid(waTemplateUid);
					waNndMetaDataDT.setWaUiMetadataUid(waUiMetadataUid);
					createWaNndMetadata(waNndMetaDataDT);
				}
			} catch (Exception e) {
				logger.error("Error in creating the record for the page on waUiMetadataUid " + waUiMetadataUid, e);
				throw new NEDSSSystemException(e.toString());
			}
		}
	}

	public Collection<Object> retrieveWaQuestionForPageElements(
			Collection<Object> pageElementVOCollection)
			throws NEDSSDAOSysException, NEDSSSystemException {
		ArrayList<Object> updatedPageElementVOCollection = new ArrayList<Object>();

		try{
			Iterator<Object> iter = pageElementVOCollection.iterator();
			while (iter.hasNext()) {
				PageElementVO pageElementVO = (PageElementVO) iter.next();
				String questionIdentifier = pageElementVO.getWaUiMetadataDT().getQuestionIdentifier();
				pageElementVO.setWaQuestionDT(findWaQuestion(questionIdentifier));
				updatedPageElementVOCollection.add(pageElementVO);
			}
	
			return updatedPageElementVOCollection;
		}catch(Exception ex){
			logger.fatal("Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	/**
	 * Set WA_template template_type to 'Published'
	 *
	 * @param waTemplateUid
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	public void setTemplateTypeToPublished(Long templateUid)
			throws NEDSSDAOSysException, NEDSSSystemException {
		ArrayList<Object> paramList = new ArrayList<Object>();
		paramList.add(templateUid);
		try {
			preparedStmtMethod(null, paramList, UPDATE_WA_TEMPLATE_PUBLISHED,
					NEDSSConstants.UPDATE);

		} catch (Exception ex) {
			logger.fatal("Exception in setTemplateTypeToPublished: ERROR = "
					+ ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	/**
	 * Set WA_ui_metadata publish_ind_cd to true by wa_template_uid
	 *
	 * @param waTemplateUid
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	public void updateWaUiMetadataToPublished(Long waTemplateUid)
			throws NEDSSDAOSysException, NEDSSSystemException {

		ArrayList<Object> paramList = new ArrayList<Object>();
		paramList.add(waTemplateUid);
		try {
			preparedStmtMethod(null, paramList,
					UPDATE_WA_UI_METADATA_PUBLISHED, NEDSSConstants.UPDATE);

		} catch (Exception ex) {
			logger.fatal("Exception in updateWaUiMetadataToPublished: ERROR = "
					+ ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}

	}

	public static void main(String args[]) {
		logger.debug("starting create operation on table WA NND Metadata");
		PageManagementDAOImpl pageManagementDAOImpl = new PageManagementDAOImpl();
		WaNndMetadataDT dt = new WaNndMetadataDT();
		dt.setOrderGroupId("1");
		dt.setHl7SegmentField("1");
		dt.setMsgTriggerIndCd("M");
		dt.setPartTypeCd("P");
		// pageManagementDAOImpl.fetchInsertSql("wa_question", "wa_question_uid like '%INV%'");
		//logger.debug("insert operation result = " + result);
	}

	private PageElementVO copyToPageElementDT(WaAggregatePageElementDT waAggregatePageElementDT) {
		PageElementVO pageElementVO = new PageElementVO();
		try{
			pageElementVO.setPageElementUid(new Long(waAggregatePageElementDT.getWaUiMetadataOrderNbr()));
			CachedDropDownValues cdv = new CachedDropDownValues();

			WaUiMetadataDT uiDT = new WaUiMetadataDT();
			WaNndMetadataDT nndDT = null;
			WaRdbMetadataDT rdbDT = null;



			uiDT.setWaTemplateUid(waAggregatePageElementDT.getWaUiMetadataWaTemplateUid());
			uiDT.setNbsUiComponentUid(waAggregatePageElementDT.getWaUiMetadataNbsUiComponentUid());
			uiDT.setWaQuestionUid(waAggregatePageElementDT.getWaUiMetadataWaQuestionUid());
			uiDT.setParentUid(waAggregatePageElementDT.getWaUiMetadataParentUid());
			uiDT.setQuestionLabel(waAggregatePageElementDT.getWaUiMetadataQuestionLabel());
			uiDT.setBlockName(waAggregatePageElementDT.getWaUiMetadataBlockName());

			
	
			uiDT.setQuestionToolTip(waAggregatePageElementDT.getWaUiMetadataQuestionToolTip());
			uiDT.setEnableInd(waAggregatePageElementDT.getWaUiMetadataEnableInd());
			uiDT.setDefaultValue(waAggregatePageElementDT.getWaUiMetadataDefaultValue());
			uiDT.setDisplayInd(waAggregatePageElementDT.getWaUiMetadataDisplayInd());
			uiDT.setOrderNbr(waAggregatePageElementDT.getWaUiMetadataOrderNbr());
			uiDT.setRequiredInd(waAggregatePageElementDT.getWaUiMetadataRequiredInd());
			uiDT.setTabOrderId(waAggregatePageElementDT.getWaUiMetadataTabOrderId());
			uiDT.setTabName(waAggregatePageElementDT.getWaUiMetadataTabName());
			uiDT.setAddTime(waAggregatePageElementDT.getWaUiMetadataAddTime());
			uiDT.setAddUserId(waAggregatePageElementDT.getWaUiMetadataAddUserId());
			uiDT.setLastChgTime(waAggregatePageElementDT.getWaUiMetadataLastChgTime());
			uiDT.setLastChgUserId(waAggregatePageElementDT.getWaUiMetadataLastChgUserId());
			uiDT.setRecordStatusCd(waAggregatePageElementDT.getWaUiMetadataRecordStatusCd());
			uiDT.setRecordStatusTime(waAggregatePageElementDT.getWaUiMetadataRecordStatusTime());
			uiDT.setMaxLength(waAggregatePageElementDT.getWaUiMetadataMaxLength());
			uiDT.setCssStyle(waAggregatePageElementDT.getWaUiMetadataCssStyle());
			uiDT.setAdminComment(waAggregatePageElementDT.getWaUiMetadataAdminComment());
			uiDT.setVersionCtrlNbr(waAggregatePageElementDT.getWaUiMetadataVersionCtrlNbr());
			uiDT.setFieldLength(waAggregatePageElementDT.getWaUiMetadataFieldSize());
			uiDT.setFutureDateIndCd(waAggregatePageElementDT.getWaUiMetadataFutureDateIndCd());
			uiDT.setLocalId(waAggregatePageElementDT.getWaUiMetadataLocalId());
			uiDT.setCodeSetGroupId(waAggregatePageElementDT.getCodeSetGroupId());
			uiDT.setQuestionIdentifier(waAggregatePageElementDT.getQuestionIdentifier());
			uiDT.setDataType(waAggregatePageElementDT.getWaUiMetadataDataType());
			uiDT.setMask(waAggregatePageElementDT.getWaUiMetadataMask());
			uiDT.setStandardNndIndCd(waAggregatePageElementDT.getStandardNndIndCd());
			uiDT.setQuestionUnitIdentifier(waAggregatePageElementDT.getQuestionUnitIdentifier());
			uiDT.setUnitParentIndentifier(waAggregatePageElementDT.getUnitParentIdentifier());
			uiDT.setDataCd(waAggregatePageElementDT.getDataCd());
			uiDT.setLegacyDataLocation(waAggregatePageElementDT.getLegacyDataLocation());
			uiDT.setDataLocation(waAggregatePageElementDT.getDataLocation());
			uiDT.setDataUseCd(waAggregatePageElementDT.getDataUseCd());
			uiDT.setQuestionOid(waAggregatePageElementDT.getQuestionOid());
			uiDT.setQuestionOidSystemTxt(waAggregatePageElementDT.getQuestionOidSystemTxt());
			uiDT.setRepeatsIndCd(waAggregatePageElementDT.getRepeatsIndCd());
			uiDT.setGroupNm(waAggregatePageElementDT.getGroupNm());
			uiDT.setSubGroupNm(waAggregatePageElementDT.getSubGroupNm());
			uiDT.setDescTxt(waAggregatePageElementDT.getDescTxt());
			uiDT.setStandardQuestionIndCd(waAggregatePageElementDT.getStandardQuestionIndCd());
			uiDT.setQuestionNm(waAggregatePageElementDT.getQuestionNm());
			uiDT.setMinValue(waAggregatePageElementDT.getMinValue());
			uiDT.setMaxValue(waAggregatePageElementDT.getMaxValue());
			uiDT.setPartTypeCd(waAggregatePageElementDT.getPartTypeCd());
	        uiDT.setQuestionType(waAggregatePageElementDT.getWaUiMetadataQuestionType());
	        uiDT.setEntryMethod(waAggregatePageElementDT.getWaUiMetadataEntryMethod());
	        uiDT.setUnitTypeCd(waAggregatePageElementDT.getUnitTypeCd());
	        uiDT.setUnitValue(waAggregatePageElementDT.getUnitValue());
	        uiDT.setOtherValIndCd(waAggregatePageElementDT.getOtherValIndCd());
	        uiDT.setPublishIndCd(waAggregatePageElementDT.getPublishIndCd());
	        uiDT.setQuestionGroupSeqNbr(waAggregatePageElementDT.getWaUiMetadataQueGrpSeqNbr());
	        uiDT.setBatchTableAppearIndCd(waAggregatePageElementDT.getBatchTableAppearIndCd());
	        uiDT.setBatchTableColumnWidth(waAggregatePageElementDT.getBatchTableColumnWidth());
	        uiDT.setBatchTableHeader(waAggregatePageElementDT.getBatchTableHeader());
	        if ((waAggregatePageElementDT.getUnitTypeCd() != null && waAggregatePageElementDT.getUnitTypeCd() .equals("CODED")) && waAggregatePageElementDT.getUnitValue() != null )
	        	uiDT.setUnitValueCodeSetNm(cdv.getTheCodeSetNm(Long.parseLong(waAggregatePageElementDT.getUnitValue())));

	        // set values in uiMetadataDT (from rdb and nnd) for use in edit question within page builder
	        uiDT.setRdbTableNm(waAggregatePageElementDT.getWaRdbRdbTableNm());
	        uiDT.setUserDefinedColumnNm(waAggregatePageElementDT.getWaUserDefinedColumnNm());
	        uiDT.setRdbcolumnNm(waAggregatePageElementDT.getWaRdbRdbColumnNm());
	        uiDT.setReportLabel(waAggregatePageElementDT.getWaRdbRptAdminColumnNm());
	        uiDT.setNndMsgInd(waAggregatePageElementDT.getWaNndQuestionRequiredNnd());
	        uiDT.setQuestionIdentifierNnd(waAggregatePageElementDT.getWaNndQuestionIdentifierNnd());
	        uiDT.setQuestionLabelNnd(waAggregatePageElementDT.getWaNndQuestionLabelNnd());
	        uiDT.setQuestionReqNnd(waAggregatePageElementDT.getWaNndQuestionRequiredNnd());
	        uiDT.setQuestionDataTypeNnd(waAggregatePageElementDT.getWaNndQuestionDataTypeNnd());
	        uiDT.setHl7Segment(waAggregatePageElementDT.getWaNndHl7SegmentField());
	        uiDT.setOrderGrpId(waAggregatePageElementDT.getWaNndOrderGroupId());
	        uiDT.setCoinfectionIndCd(waAggregatePageElementDT.getWaUiMetadataCoinfectionIndCd()== null?"F" : waAggregatePageElementDT.getWaUiMetadataCoinfectionIndCd());	

			if (waAggregatePageElementDT.getCodeSetGroupId() != null) {

				uiDT.setCodeSetName(cdv.getTheCodeSetNm(waAggregatePageElementDT.getCodeSetGroupId()));
			}
			if (uiDT.getNbsUiComponentUid() != null
					&& (uiDT.getNbsUiComponentUid().longValue() != 1002
							&& uiDT.getNbsUiComponentUid().longValue() != 1010
							&& uiDT.getNbsUiComponentUid().longValue() != 1015
							&& uiDT.getNbsUiComponentUid().longValue() != 1016)
					&& waAggregatePageElementDT.getWaRdbRdbColumnNm() != null) {
				rdbDT = new WaRdbMetadataDT();
				rdbDT.setWaRdbMetadataUid(waAggregatePageElementDT.getWaRdbWaRdbMetadataUid());
				rdbDT.setWaUiMetadataUid(waAggregatePageElementDT.getWaRdbWaUiMetadataUid());
				rdbDT.setWaTemplateUid(waAggregatePageElementDT.getWaRdbWaTemplateUid());
				rdbDT.setRdbTableNm(waAggregatePageElementDT.getWaRdbRdbTableNm());
				rdbDT.setUserDefinedColumnNm(waAggregatePageElementDT.getWaUserDefinedColumnNm());
		
				rdbDT.setRptAdminColumnNm(waAggregatePageElementDT.getWaRdbRptAdminColumnNm());
				rdbDT.setRdbcolumNm(waAggregatePageElementDT.getWaRdbRdbColumnNm());
				rdbDT.setRecordStatusCd(waAggregatePageElementDT.getWaRdbRecStatusCd());
				rdbDT.setRecStatusTime(waAggregatePageElementDT.getWaRdbRecStatusTime());
				rdbDT.setRecordStatusTime(waAggregatePageElementDT.getWaRdbRecStatusTime());
				rdbDT.setAddUserId(waAggregatePageElementDT.getWaRdbAddUserId());
				rdbDT.setAddTime(waAggregatePageElementDT.getWaRdbAddTime());
				rdbDT.setLastChgTime(waAggregatePageElementDT.getWaRdbLastChgTime());
				rdbDT.setLastChgUserId(waAggregatePageElementDT.getWaRdbLastChgUserId());
				rdbDT.setLocalId(waAggregatePageElementDT.getWaRdbLocalId());
				rdbDT.setQuestionIdentifier(waAggregatePageElementDT.getQuestionIdentifier());
				rdbDT.setDataMartRepeatNbr(waAggregatePageElementDT.getWaRdbMetadataDataMartRepeatNbr());
			}
			if (waAggregatePageElementDT.getWaNndQuestionRequiredNnd() != null &&
				waAggregatePageElementDT.getWaNndQuestionRequiredNnd().trim().length() == 1) {
				nndDT = new WaNndMetadataDT();
				nndDT.setWaNndMetadataUid(waAggregatePageElementDT.getWaNndWaNndMetadataUid());
				nndDT.setWaUiMetadataUid(waAggregatePageElementDT.getWaNndWaUiMetadataUid());
				nndDT.setWaTemplateUid(waAggregatePageElementDT.getWaNndWaTemplateUid());
				nndDT.setQuestionIdentifierNnd(waAggregatePageElementDT.getWaNndQuestionIdentifierNnd());
				nndDT.setQuestionLabelNnd(waAggregatePageElementDT.getWaNndQuestionLabelNnd());
				nndDT.setQuestionRequiredNnd(waAggregatePageElementDT.getWaNndQuestionRequiredNnd());
				nndDT.setQuestionDataTypeNnd(waAggregatePageElementDT.getWaNndQuestionDataTypeNnd());
				nndDT.setHl7SegmentField(waAggregatePageElementDT.getWaNndHl7SegmentField());
				nndDT.setOrderGroupId(waAggregatePageElementDT.getWaNndOrderGroupId());
				nndDT.setTranslationTableNm(waAggregatePageElementDT.getWaNndTranslationTableNm());
				nndDT.setAddTime(waAggregatePageElementDT.getWaNndAddTime());
				nndDT.setAddUserId(waAggregatePageElementDT.getWaNndAddUserId());
				nndDT.setLastChgTime(waAggregatePageElementDT.getWaNndLastChgTime());
				nndDT.setLastChgUserId(waAggregatePageElementDT.getWaNndLastChgUserId());
				nndDT.setRecStatusCd(waAggregatePageElementDT.getWaNndRecStatusCd());
				nndDT.setRecStatusTime(waAggregatePageElementDT.getWaNndRecStatusTime());
				nndDT.setQuestionIdentifier(waAggregatePageElementDT.getWaNndQuestionIdentifier());
				nndDT.setMsgTriggerIndCd(waAggregatePageElementDT.getWaNndMsgTriggerIndCd());
				nndDT.setXmlPath(waAggregatePageElementDT.getWaNndXmlPath());
				nndDT.setXmlTag(waAggregatePageElementDT.getWaNndXmlTag());
				nndDT.setXmlDataType(waAggregatePageElementDT.getWaNndXmlDataType());
				nndDT.setPartTypeCd(waAggregatePageElementDT.getWaNndPartTypeCd());
				nndDT.setRepeatGroupSeqNbr(waAggregatePageElementDT.getWaNndRepeatGroupSeqNbr());
				nndDT.setQuestionOrderNnd(waAggregatePageElementDT.getWaNndQuestionOrderNnd());
				nndDT.setLocalId(waAggregatePageElementDT.getWaNndLocalId());
				nndDT.setQuestionIdentifier(waAggregatePageElementDT.getQuestionIdentifier());
				nndDT.setQuestionMap(waAggregatePageElementDT.getWaNndQuestionMap());
				nndDT.setIndicatorCd(waAggregatePageElementDT.getWaNndIndicatorCd());
			}


			pageElementVO.setWaUiMetadataDT(uiDT);
			if (rdbDT != null)
				pageElementVO.setWaRdbMetadataDT(rdbDT);
			if (nndDT != null)
				pageElementVO.setWaNndMetadataDT(nndDT);
		}catch(Exception e){
			logger.fatal("Error in Converting the data from WaAggregatePageElementDT to ui rdb and nnd"+e.getMessage(), e);
			logger.error("/n ERROR" +waAggregatePageElementDT.toString());
			throw new NEDSSSystemException(e.getMessage());
		}
		return pageElementVO;
	}

	/**
	 * Update NBSUiMetadata comprises of INSERTING exiting row to HIST, and
	 * updating the current record
	 *
	 * @param dt
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	public boolean saveXMLtoDB(String XMLContents, Long waTemplateUid)
			throws NEDSSDAOSysException, NEDSSSystemException {

		// History
		// updateWaTemplateHistory(dt.getWaTemplateUid());
		boolean saveStatus = true;
		ArrayList<Object> paramList = new ArrayList<Object>();

		paramList.add(XMLContents);
		paramList.add(waTemplateUid);

		try {
			preparedStmtMethod(null, paramList,
					UPDATE_WA_TEMPLATE_WITH_XML_CONTENTS, NEDSSConstants.UPDATE);

		} catch (Exception ex) {
			saveStatus = false;
			logger.fatal("Exception in updateWaTemplate: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return saveStatus;
	}



	@SuppressWarnings("unchecked")
	public WaUiMetadataDT findUiMetadataByQuestionIdentifier(String questionIdentifier)
			throws NEDSSDAOSysException, NEDSSSystemException {

		WaUiMetadataDT waUiMetadataDT = new WaUiMetadataDT();
		ArrayList<Object> paramList = new ArrayList<Object>();
		paramList.add(questionIdentifier);
		try {
			paramList = (ArrayList<Object>) preparedStmtMethod(waUiMetadataDT,
					paramList, FIND_WA_UI_METADATA_BY_QUEST_IDENT, NEDSSConstants.SELECT);
			if (paramList.size() > 0)
				return (WaUiMetadataDT) paramList.get(0);
		} catch (Exception ex) {
			logger.fatal("Exception in findUiMetadataByQuestionIdentifier: ERROR = " + ex.getMessage(),ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return waUiMetadataDT;

	}

	@SuppressWarnings("unchecked")
	public WaTemplateDT getPortReqIndCd(String conditionCode)
			throws NEDSSDAOSysException, NEDSSSystemException {

		WaTemplateDT waTemplateDT = new WaTemplateDT();
		ArrayList<Object> paramList = new ArrayList<Object>();
		paramList.add(conditionCode);
		String codeSql="";
	    codeSql = "SELECT port_req_ind_cd \"portReqIndCd\" from " +
	              NEDSSConstants.SYSTEM_REFERENCE_TABLE +"..condition_code WHERE condition_cd = ?";
	   try {
			paramList = (ArrayList<Object>) preparedStmtMethod(waTemplateDT,
					paramList, codeSql, NEDSSConstants.SELECT);
			if (paramList.size() > 0)
				return (WaTemplateDT) paramList.get(0);
		} catch (Exception ex) {
			logger.fatal("Exception in getPortReqIndCd: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return waTemplateDT;

	}

	 public void updateCondtionCodeForPublish(WaTemplateDT dt, String conditionCd)
				throws NEDSSDAOSysException, NEDSSSystemException {
		try {
			String portReqIndCd = null;
			WaTemplateDT tempDt = getPortReqIndCd(dt.getConditionCd());
			portReqIndCd = tempDt!=null?tempDt.getPortReqIndCd():"";
			dt.setPortReqIndCd(portReqIndCd);
			if(portReqIndCd != null && !"T".equalsIgnoreCase(portReqIndCd)){
				String codeSql="";
				codeSql = 	" UPDATE  " + NEDSSConstants.SYSTEM_REFERENCE_TABLE +"..condition_code "+
          			" SET nnd_entity_identifier=? " +
          			" WHERE condition_cd = ?";
			   ArrayList<Object> paramList = new ArrayList<Object>();
				paramList.add(dt.getMessageId());
				// where param
				paramList.add(conditionCd);
				preparedStmtMethod(null, paramList, codeSql , NEDSSConstants.UPDATE, NEDSSConstants.SRT);
			}

		} catch (Exception ex) {
			logger.fatal("Exception in updateCondtionCodeForPublish: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		} finally {
		}

	 }

		public WaTemplateDT findWaTemplateByTemplateNm(String waTemplateNm , String templateType)
		throws NEDSSDAOSysException, NEDSSSystemException {

			WaTemplateDT waTemplateDT = new WaTemplateDT();
			ArrayList<Object> paramList = new ArrayList<Object>();
			paramList.add(waTemplateNm);
			
			String sqlQuerry = FIND_WA_TEMPLATE_BY_TEMPLATE_NM;
			if(templateType != null && templateType.equalsIgnoreCase(NEDSSConstants.TEMPLATE)){
				paramList.add(templateType);
				sqlQuerry = FIND_WA_TEMPLATE_BY_TEMPLATE_NM+templateWhereClause;
		
		     }else 
				 sqlQuerry = FIND_WA_TEMPLATE_BY_TEMPLATE_NM+nonTemplateWhereClause;
			try {
				paramList = (ArrayList<Object>) preparedStmtMethod(waTemplateDT,
						paramList, sqlQuerry, NEDSSConstants.SELECT);
				if (paramList.size() > 0)
					return (WaTemplateDT) paramList.get(0);
			} catch (Exception ex) {
				logger.fatal("Exception in findWaTemplate By Template Name : ERROR = " + ex.getMessage(), ex);
				throw new NEDSSSystemException(ex.toString());
			}
			return waTemplateDT;

}
		
		
		/**
		 * findWaTemplateByDataMartNm: returns templates with the same data mart name
		 * @param dataMartNm
		 * @param templateType
		 * @return
		 * @throws NEDSSDAOSysException
		 * @throws NEDSSSystemException
		 */
		public WaTemplateDT findWaTemplateByDataMartNm(String dataMartNm , String templateType)
		throws NEDSSDAOSysException, NEDSSSystemException {

			WaTemplateDT waTemplateDT = new WaTemplateDT();
			ArrayList<Object> paramList = new ArrayList<Object>();
			paramList.add(dataMartNm);
			
			String sqlQuerry = FIND_WA_TEMPLATE_BY_DATAMART_NM;
			if(templateType != null && templateType.equalsIgnoreCase(NEDSSConstants.TEMPLATE)){
				paramList.add(templateType);
				sqlQuerry = FIND_WA_TEMPLATE_BY_DATAMART_NM+templateWhereClause;
		
		     }else 
				 sqlQuerry = FIND_WA_TEMPLATE_BY_DATAMART_NM+nonTemplateWhereClause;
			try {
				paramList = (ArrayList<Object>) preparedStmtMethod(waTemplateDT,
						paramList, sqlQuerry, NEDSSConstants.SELECT);
				if (paramList.size() > 0)
					return (WaTemplateDT) paramList.get(0);
			} catch (Exception ex) {
				logger.fatal("Exception in findWaTemplateByDataMartNm : ERROR = " + ex.getMessage(), ex);
				throw new NEDSSSystemException(ex.toString());
			}
			return waTemplateDT;

}
		
		
		public ArrayList<Object> findPageByCondAndBO(String condCollStr, String boType)
		throws NEDSSDAOSysException, NEDSSSystemException {

			PageCondMappingDT pageCondMappingDT = new PageCondMappingDT();
			ArrayList<Object> paramList = new ArrayList<Object>();	
			ArrayList<Object> returnList = new ArrayList<Object>();
			String sqlQuerry = null;
			sqlQuerry = FIND_PAGE_COND_MAPPING_COLL_BY_COND_SQL+condCollStr;
			try {			
				paramList.add(boType);
				returnList = (ArrayList<Object>) preparedStmtMethod(pageCondMappingDT,
						paramList, sqlQuerry, NEDSSConstants.SELECT);				
			} catch (Exception ex) {
				logger.fatal("Exception in findWaTemplate By Template Name : ERROR = " + ex.getMessage(), ex);
				throw new NEDSSSystemException(ex.toString());
			}
			return returnList;

}


		/**
		 * Returns Collection of WaTemplateDT's
		 *
		 * @param waQuestionIdentifier
		 * @return WaQuestionDT
		 * @throws NEDSSDAOSysException
		 * @throws NEDSSSystemException
		 */
		@SuppressWarnings("unchecked")
		public Collection<Object> retrievePageHistory(String conditionCd)
				throws NEDSSDAOSysException, NEDSSSystemException {


			WaTemplateDT waTemplateDT = new WaTemplateDT();
			ArrayList<Object> waTemplateDTCollection = new ArrayList<Object>();
			waTemplateDTCollection.add(conditionCd);
			waTemplateDTCollection.add(conditionCd);
			try {
				waTemplateDTCollection = (ArrayList<Object>) preparedStmtMethod(
							waTemplateDT, waTemplateDTCollection,
							RETRIEVE_PAGE_HISTORY_SQL, NEDSSConstants.SELECT);
			} catch (Exception ex) {
				logger
						.fatal("Exception in PageManagementDAOImpl.retrievePageHistory - ERROR = "
								+ ex.getMessage(),ex);
				throw new NEDSSSystemException(ex.toString());
			}
			return waTemplateDTCollection;

		}

	
		/**
		 * Returns Collection of WaTemplateDT's
		 *
		 * @param waQuestionIdentifier
		 * @return WaQuestionDT
		 * @throws NEDSSDAOSysException
		 * @throws NEDSSSystemException
		 */

		public Collection<Object>  retrieveJspFiles(String invFormCd)
		throws NEDSSDAOSysException,NEDSSSystemException {

			Connection dbConnection = null;
			PreparedStatement preparedStmt = null;
			ResultSet resultSet = null;
			ResultSetMetaData resultSetMetaData = null;
			ResultSetUtils resultSetUtils = new ResultSetUtils();
			String sqlQuery = null;
			ArrayList<Object> nbsPageDTCollection = new ArrayList<Object>();
			NbsPageDT pageDT = null;
			//nbsPageDTCollection.add(invFormCd);

			try {
				dbConnection = getConnection();
			} catch (NEDSSSystemException nsex) {
				logger.fatal("SQLException while obtaining database connection "
						+ "for getInvestigationFormCodeonServerStartup : PageManagementDAOImpl", nsex);
				throw new NEDSSSystemException(nsex.getMessage());
			}
		      //  logic to check if code has seperate table
		      sqlQuery = RETRIEVE_JSP_FILES_SQL;
		      try {
				preparedStmt = dbConnection
						.prepareStatement(sqlQuery);
				preparedStmt.setString(1,invFormCd);
				resultSet = preparedStmt.executeQuery();
					if(resultSet!=null && resultSet.next()){
					pageDT  = new NbsPageDT();
					pageDT.setJspPayload(resultSet.getBytes(1));
					nbsPageDTCollection.add(pageDT);
				}
					return nbsPageDTCollection;
				} catch (SQLException se) {
				throw new NEDSSDAOSysException("SQLException while selecting "
						+ "jsp files collection: PageManagementDAOImpl " +se.getMessage(), se);
			} finally {
				closeResultSet(resultSet);
				closeStatement(preparedStmt);
				releaseConnection(dbConnection);
			}
}


		/**
		 * Returns Collection of WaTemplateDT's
		 *
		 * @param waQuestionIdentifier
		 * @return WaQuestionDT
		 * @throws NEDSSDAOSysException
		 * @throws NEDSSSystemException
		 */

		public Collection<Object>  getInvestigationFormCodeonServerStartup()
		throws NEDSSDAOSysException,NEDSSSystemException {

			Connection dbConnection = null;
			PreparedStatement preparedStmt = null;
			ResultSet resultSet = null;
			ResultSetMetaData resultSetMetaData = null;
			ResultSetUtils resultSetUtils = new ResultSetUtils();
			String sqlQuery = null;
			ArrayList<Object> invFrmCdCollection = new ArrayList<Object>();

			try {
				dbConnection = getConnection();
			} catch (NEDSSSystemException nsex) {
				logger.fatal("SQLException while obtaining database connection "
						+ "for getInvestigationFormCodeonServerStartup : PageManagementDAOImpl", nsex);
				throw new NEDSSSystemException(nsex.getMessage());
			}
		      //  logic to check if code has seperate table
		      sqlQuery = RETRIEVE_INV_FRM_CD_SQL;
		      try {
				preparedStmt = dbConnection
						.prepareStatement(sqlQuery);
				resultSet = preparedStmt.executeQuery();

				if(resultSet!=null){
				resultSetMetaData = resultSet.getMetaData();
				invFrmCdCollection = (ArrayList<Object> ) resultSetUtils.mapRsToBeanList(resultSet,
						resultSetMetaData, NbsPageDT.class, invFrmCdCollection);

				logger.debug("returned questions list");
				}
				return invFrmCdCollection;
			} catch (SQLException se) {
				throw new NEDSSDAOSysException("SQLException while selecting "
						+ "form codes collection: PageManagementDAOImpl " +se.getMessage(), se);
			} catch (ResultSetUtilsException reuex) {
				logger
						.fatal(
								"Error in result set handling while selecting form codes: PageManagementDAOImpl."+reuex.getMessage(),
								reuex);
				throw new NEDSSDAOSysException(reuex.toString());
			} finally {
				closeResultSet(resultSet);
				closeStatement(preparedStmt);
				releaseConnection(dbConnection);
			}
}

		/**
		 * Returns Collection of WaTemplateDT's
		 *
		 * @param waQuestionIdentifier
		 * @return WaQuestionDT
		 * @throws NEDSSDAOSysException
		 * @throws NEDSSSystemException
		 */

		public Collection<Object>  fetchPublishedMessageGuide(String conditionCd)
		throws NEDSSDAOSysException,NEDSSSystemException {

			Connection dbConnection = null;
			PreparedStatement preparedStmt = null;
			ResultSet resultSet = null;
			ResultSetMetaData resultSetMetaData = null;
			ResultSetUtils resultSetUtils = new ResultSetUtils();
			String sqlQuery = null;
			ArrayList<Object> waTemplateDTCollection = new ArrayList<Object>();
			//nbsPageDTCollection.add(invFormCd);

			try {
				dbConnection = getConnection();
			} catch (NEDSSSystemException nsex) {
				logger.fatal("SQLException while obtaining database connection "
						+ "for getInvestigationFormCodeonServerStartup : PageManagementDAOImpl", nsex);
				throw new NEDSSSystemException(nsex.getMessage());
			}
		      //  logic to check if code has seperate table
		      sqlQuery = RETRIEVE_MESSAGE_GUIDE_SQL;
		      try {
				preparedStmt = dbConnection
						.prepareStatement(sqlQuery);
				preparedStmt.setString(1,conditionCd);
				resultSet = preparedStmt.executeQuery();

				if(resultSet!=null){
				resultSetMetaData = resultSet.getMetaData();
				waTemplateDTCollection = (ArrayList<Object> ) resultSetUtils.mapRsToBeanList(resultSet,
						resultSetMetaData, WaTemplateDT.class, waTemplateDTCollection);

				logger.debug("returned questions list");
				}
				return waTemplateDTCollection;
			} catch (SQLException se) {
				throw new NEDSSDAOSysException("SQLException while selecting "
						+ "jsp files collection: PageManagementDAOImpl " +se.getMessage(), se);
			} catch (ResultSetUtilsException reuex) {
				logger
						.fatal(
								"Error in result set handling while selecting jsp files: PageManagementDAOImpl."+reuex.getMessage(),
								reuex);
				throw new NEDSSDAOSysException(reuex.toString());
			} finally {
				closeResultSet(resultSet);
				closeStatement(preparedStmt);
				releaseConnection(dbConnection);
			}
}

		public String  checkRulesIdAssocRulesForQuestionId(Long waTemplateUid, String pageElementUid) {
			StringBuffer resultString = new StringBuffer();
			boolean flag = false;
			String sqlQuery = null;
			WaRuleMetadataDT waRuleMetadataDT = new WaRuleMetadataDT();
			ArrayList<Object>  waRuleMetadataDTCollection = new ArrayList<Object> ();
			ArrayList<Object> paramList = new ArrayList<Object> ();
			paramList.add(waTemplateUid);
			paramList.add(pageElementUid);
			paramList.add(pageElementUid);
			try {
				 sqlQuery = RETRIVE_COUNT_PAGE_RULES_SQL;
				 waRuleMetadataDTCollection = ((ArrayList<Object>)preparedStmtMethod(waRuleMetadataDT, paramList, sqlQuery , NEDSSConstants.SELECT));

				 if(waRuleMetadataDTCollection != null && waRuleMetadataDTCollection.size()>0)
					{
						Iterator<Object>  iter = waRuleMetadataDTCollection.iterator();
						while(iter.hasNext()){
							WaRuleMetadataDT dt = (WaRuleMetadataDT)iter.next();
							if(dt.getWaRuleMetadataUid() != null)
							{
								if(flag){
									resultString.append(", ");
								}
								resultString.append(dt.getWaRuleMetadataUid().toString());
								flag= true;
							}
						}
					}

			} catch (Exception ex) {
				logger.error("Exception in checkRulesIdAssocRulesForQuestionId: ERROR = " + ex.getMessage(), ex);
				throw new NEDSSSystemException(ex.toString());
			}
			return resultString.toString();
		}
		/**
		 * Set WA_template template_type to 'Published'
		 *
		 * @param waTemplateUid
		 * @throws NEDSSDAOSysException
		 * @throws NEDSSSystemException
		 */
		public void updateConditionCode()
				throws NEDSSDAOSysException, NEDSSSystemException {
			ArrayList<Object> paramList = new ArrayList<Object>();

			try {
				preparedStmtMethod(null, paramList, UPDATE_CONDITION_CD,
						NEDSSConstants.UPDATE, NEDSSConstants.SRT);

			} catch (Exception ex) {
				logger.fatal("Exception in updateConditionCode: ERROR = "
						+ ex.getMessage(), ex);
				throw new NEDSSSystemException(ex.toString());
			}
		}


		public Integer getMaxGroupIdForBatch(Long waTemplateUid)throws NEDSSDAOSysException, NEDSSSystemException
		{
			Connection dbConnection = null;
			dbConnection = getConnection();
			PreparedStatement preparedStmt2 = null;
			Integer  groupId = null;
			ResultSet rs = null;
			try {

			preparedStmt2 = dbConnection.prepareStatement(GetMaxGroupUid);
			preparedStmt2.setLong(1, waTemplateUid);
			rs = preparedStmt2.executeQuery();
			if (rs.next()) {
				logger.debug("waTemplateUid = " + rs.getLong(1));
				groupId=new Integer( rs.getInt(1));
			}
			logger.debug("resultCount in getMaxGroupIdForBAtch is ");
			}catch(SQLException sqlex)
			{
				logger.fatal("SQLException while getting max of groupId from wa_ui_metadata table "+ sqlex.getMessage(),sqlex);
				throw new NEDSSDAOSysException( sqlex.toString() );
			}
			catch(Exception ex)
			{
				logger.fatal("SQLException while getting max of groupId from wa_ui_metadata table "+ ex.getMessage(),ex);
				throw new NEDSSSystemException(ex.toString());
			}
			finally
			{
				closeResultSet(rs);
				closeStatement(preparedStmt2);
				releaseConnection(dbConnection);
			}

			return groupId;
		}

		
		// Its called from PageManagementProxyEJB and PortProxyEJB
		public void setPageCondiMapping(PageCondMappingDT dt)
		throws NEDSSDAOSysException, NEDSSSystemException {
			//Long waTemplateUid = null;
			ArrayList<Object> paramList = new ArrayList<Object>();
			paramList.add(dt.getWaTemplateUid());
			paramList.add(dt.getConditionCd());
			paramList.add(dt.getAddTime());
			paramList.add(dt.getAddUserId());
			paramList.add(dt.getLastChgTime());
			paramList.add(dt.getLastChgUserId());


	try {
		Integer resultCount = ((Integer) preparedStmtMethod(null, paramList,
				INSERT_PAGE_COND_MAPPING,
				NEDSSConstants.UPDATE));
		} catch (Exception ex) {
			logger
					.fatal("Exception in PageManagementDAOImpl.insertPageCondiMapping: ERROR = "
							+ ex.getMessage(),ex);
			throw new NEDSSSystemException(ex.toString());
		
			}
		}


		@SuppressWarnings("unchecked")
		public Collection<Object>  getPageCondMappingColl(Long waTemplateUid)
		throws NEDSSDAOSysException,NEDSSSystemException {

			Connection dbConnection = null;
			PreparedStatement preparedStmt = null;
			
			ArrayList<Object> pageCondMappingDTColl = new ArrayList<Object>();
			PageCondMappingDT pageCondMappingDT = new PageCondMappingDT();
			
			dbConnection = getConnection();

			ArrayList<Object> paramList = new ArrayList<Object>();
			paramList.add(waTemplateUid);
			try {
				pageCondMappingDTColl  = (ArrayList<Object>) preparedStmtMethod(pageCondMappingDT,
				paramList, FIND_PAGE_COND_MAPPING_COLL_SQL, NEDSSConstants.SELECT);
			    
			} catch (NEDSSSystemException nsex) {
				logger.fatal("SQLException while obtaining database connection "
						+ "for getPageCondMappingColl : PageManagementDAOImpl"+nsex.getMessage(), nsex);
				throw new NEDSSSystemException(nsex.getMessage());
			} finally {				
				closeStatement(preparedStmt);
				releaseConnection(dbConnection);
			}
			return pageCondMappingDTColl;


		}
		
		
		@SuppressWarnings("unchecked")
		public Collection<Object>   getPageCondMappingDTCollByCond(String  conditionCd, String busObjectType)
		throws NEDSSDAOSysException,NEDSSSystemException {

			Connection dbConnection = null;
			PreparedStatement preparedStmt = null;
			
			PageCondMappingDT pageCondMappingDT = new PageCondMappingDT();
			
			dbConnection = getConnection();
			ArrayList<Object> pageCondMappingDTColl = new ArrayList<Object>();
			ArrayList<Object> paramList = new ArrayList<Object>();
			paramList.add(conditionCd);
			paramList.add(busObjectType);
			try {
				pageCondMappingDTColl  = (ArrayList<Object>) preparedStmtMethod(pageCondMappingDT,
						paramList, FIND_PAGE_COND_MAPPING_DT_SQL, NEDSSConstants.SELECT);
				
			} catch (NEDSSSystemException nsex) {
				logger.fatal("SQLException while obtaining database connection "
						+ "for getPageCondMappingDT : PageManagementDAOImpl"+nsex.getMessage(), nsex);
				throw new NEDSSSystemException(nsex.getMessage());
			} finally {				
				closeStatement(preparedStmt);
				releaseConnection(dbConnection);
			}
			return pageCondMappingDTColl;


		}

		public void initializePageCondMapping(Long waTemplateUid, Long waTemplatePUid)throws NEDSSDAOSysException,NEDSSSystemException {

			Collection<Object> pageCondMappColl = getPageCondMappingColl(waTemplatePUid);
			if(pageCondMappColl != null && pageCondMappColl.size()>0){
				Iterator<Object>  iter = pageCondMappColl.iterator();
				while(iter.hasNext()){
					PageCondMappingDT pageCondMapp = (PageCondMappingDT)iter.next();
					pageCondMapp.setWaTemplateUid(waTemplateUid);
					try{
						setPageCondiMapping(pageCondMapp);
					}catch(Exception ex){
						logger.error("Error while inserting to pageCondition Mapping table: "+ ex.getMessage(), ex);
						throw new NEDSSSystemException(ex.getMessage());
					}
				}
			}
		}


		/**
		 * Update NBSUiMetadata comprises of INSERTING exiting row to HIST, and
		 * updating the current record
		 *
		 * @param dt
		 * @throws NEDSSDAOSysException
		 * @throws NEDSSSystemException
		 */
		public void updatePageCondiMapping(PageCondMappingDT dt) throws NEDSSDAOSysException,
				NEDSSSystemException {
			ArrayList<Object> paramList = new ArrayList<Object>();
			paramList.add(dt.getWaTemplateUid());
			paramList.add(dt.getConditionCd());
			paramList.add(dt.getAddTime());
			paramList.add(dt.getAddUserId());
			paramList.add(dt.getLastChgTime());
			paramList.add(dt.getLastChgUserId());
			// where param
			paramList.add(dt.getPageCondMappingUid());

			try {
				preparedStmtMethod(null, paramList, UPDATE_PAGE_COND_MAPPING,
						NEDSSConstants.UPDATE);

			} catch (Exception ex) {
				logger.fatal("Exception in updatePageCondiMapping: ERROR = " + ex.getMessage(),ex);
				throw new NEDSSSystemException(ex.toString());
			}
		}

		public void updatePageCondiMappingColl(Collection<Object> pageCondMapp,Long waTemplateUid){
			if(pageCondMapp != null && pageCondMapp.size()>0 ){
				Iterator iter = pageCondMapp.iterator();
				while(iter.hasNext()){
					PageCondMappingDT dt = (PageCondMappingDT)iter.next();
					dt.setWaTemplateUid(waTemplateUid);
			        try{
			        	updatePageCondiMapping(dt);
			        }catch(Exception e){
			        	logger.fatal("Error on updating the condition Mapping"+e.getMessage(),e);
			        	throw new NEDSSSystemException(e.toString());
			        }
				}

			}

		}

		public void updatePageCondMappingHistory(Long waTemplateUid,
				String uidType, Long waTemplateHistUid)
				throws NEDSSDAOSysException, NEDSSSystemException {

			String sql = INSERT_PAGE_COND_MAPPING_HISTORY;
			String from = " FROM page_cond_Mapping ";
			sql = sql + from;
			if (uidType.equalsIgnoreCase(BY_TABLE_UID))
				sql += WHERE_PAGE_COND_MAPPING_UID;
			else
				sql += WHERE_WA_TEMPLATE_UID;

			ArrayList<Object> paramList = new ArrayList<Object>();
			// where param
			paramList.add(waTemplateUid);

			try {
				preparedStmtMethod(null, paramList, sql, NEDSSConstants.UPDATE);

			} catch (Exception ex) {
				logger.fatal("Exception in updateWaUiMetadataHistory: ERR	OR = "
						+ ex.getMessage(), ex);

				throw new NEDSSSystemException(ex.toString());
			}
		}
		public void reinitializePageCondMapping(WaTemplateVO waTempVO)throws NEDSSDAOSysException, NEDSSSystemException {
			try{
				deletePageConditionMapping(waTempVO.getWaTemplateDT().getWaTemplateUid());
				Collection<Object> pageCondMappColl = waTempVO.getWaPageCondMappingDTColl();
				if(pageCondMappColl != null && pageCondMappColl.size()>0){
					Iterator<Object>  iter = pageCondMappColl.iterator();
					while(iter.hasNext()){
						PageCondMappingDT pageCondMapp = (PageCondMappingDT)iter.next();
						try{
							setPageCondiMapping(pageCondMapp);
						}catch(Exception ex){
							logger.error("Error while inserting to pageCondition Mapping table: "+ ex.getMessage());
							throw new NEDSSSystemException(ex.getMessage());
						}
					}
				}

			}catch(Exception e){
				logger.error("Error on reinitializePageCondMapping in PageMAnagementDAOImpl", e.getMessage(),e);
				throw new NEDSSSystemException(e.toString());
			}

		}

		public void deletePageConditionMapping(Long waTemplateUid)throws NEDSSDAOSysException, NEDSSSystemException {
			try{
				ArrayList<Object> paramList = new ArrayList<Object>();
				paramList.add(waTemplateUid);
				preparedStmtMethod(null, paramList,
						DELETE_PAGE_COND_MAPPING_BY_TEMPLATE_UID,
						NEDSSConstants.UPDATE);

			}catch(Exception e){
				logger.error("Error while deleting the Page Condition Mapping table " + e.getMessage(),e);
				throw new NEDSSSystemException(e.toString());
			}
		}
		
		public void deletePageConditionMapping(String conditionCd, Long waTemplateUid)throws NEDSSDAOSysException, NEDSSSystemException {
			try{
				ArrayList<Object> paramList = new ArrayList<Object>();
				paramList.add(waTemplateUid);
				paramList.add(conditionCd);
				preparedStmtMethod(null, paramList,
						DELETE_PAGE_COND_MAPPING_BY_CONDITION_CD_TEMPLATE_UID,
						NEDSSConstants.UPDATE);

			}catch(Exception e){
				logger.error("Error while deleting the record in Page Condition Mapping table " + e.getMessage(), e);
				throw new NEDSSSystemException(e.toString());
			}
		}
		
		public Map<Object,Object>  findBatchRecords(String invFrmCd) {
			Connection dbConnection = null;
			dbConnection = getConnection();
			PreparedStatement preparedStmt2 = null;			
			ResultSet rs = null;
			Map<Object,Object> batchMap = new HashMap<Object,Object>();
			String sql="";
			try {
				 	sql = " Select a.question_identifier,b.nbs_question_uid,b.question_identifier,b.batch_table_appear_ind_cd,b.batch_table_header,  "
						+ "b.batch_table_column_width,b.nbs_ui_component_uid,c.code_set_nm,b.order_nbr    from nbs_ui_metadata  a, " 
						+  "(select question_identifier, batch_table_appear_ind_cd,batch_table_header,  batch_table_column_width, "
		    		    +  "nbs_ui_component_uid,unit_value,nbs_question_uid,investigation_form_cd,question_group_seq_nbr,unit_type_cd,order_nbr from nbs_ui_metadata "
		    		     + "where (unit_type_cd is null or unit_type_cd = 'CODED' ) and investigation_form_cd = '"+invFrmCd+"' )  b  left outer join nbs_srte..codeset c  "
		                + " on b.unit_value=c.code_set_group_id  where  "
						+ "a.investigation_form_cd = '"+invFrmCd+"'  and a.nbs_ui_component_uid = 1016 and a.question_group_seq_nbr = b.question_group_seq_nbr "
						+ "and b.nbs_ui_component_uid in (1007,1008,1009,1013,1014,1017,1019,1024,1026,1031) and b.investigation_form_cd = '"+invFrmCd+"' "
						+ "union "
						+ "Select a.question_identifier,b.nbs_question_uid,b.question_identifier,b.batch_table_appear_ind_cd,b.batch_table_header,  "
						+ "b.batch_table_column_width,b.nbs_ui_component_uid,null,b.order_nbr    from nbs_ui_metadata  a, " 
						+  "(select question_identifier, batch_table_appear_ind_cd,batch_table_header,  batch_table_column_width, "
		    		    +  "nbs_ui_component_uid,unit_value,nbs_question_uid,investigation_form_cd,question_group_seq_nbr,unit_type_cd,order_nbr from nbs_ui_metadata "
		    		     + "where (unit_type_cd = 'Literal') and investigation_form_cd = '"+invFrmCd+"' )  b   "
		                + " where  "
						+ "a.investigation_form_cd = '"+invFrmCd+"'  and a.nbs_ui_component_uid = 1016 and a.question_group_seq_nbr = b.question_group_seq_nbr "
						+ "and b.nbs_ui_component_uid in (1007,1008,1009,1013,1014,1017,1019,1024,1026,1031) and b.investigation_form_cd = '"+invFrmCd+"' "
						+ "order by 1,9";
			    		
			    		
			    		/*"Select a.question_identifier,b.nbs_question_uid,b.question_identifier,b.batch_table_appear_ind_cd,b.batch_table_header, "
						+ "b.batch_table_column_width,b.nbs_ui_component_uid,c.code_set_nm,b.unit_type_cd from nbs_ui_metadata  a, nbs_ui_metadata b,(select unit_value from nbs_ui_metadata where unit_type_cd='CODED' and investigation_form_cd = '"+invFrmCd+"') d left outer join nbs_srte..codeset c  "
					    + " on d.unit_value=c.code_set_group_id  where  "
						+ "a.investigation_form_cd = '"+invFrmCd+"'  and a.nbs_ui_component_uid = 1016 and a.question_group_seq_nbr = b.question_group_seq_nbr "
						+ "and b.nbs_ui_component_uid in (1007,1008,1009,1013,1014) and b.investigation_form_cd = '"+invFrmCd+"' order by a.question_label,b.order_nbr";*/
			 
			preparedStmt2 = dbConnection.prepareStatement(sql);
			String [][] batch=new String[20][7];			

			rs = preparedStmt2.executeQuery();
			int count = 0;
			String previousSubSecNm ="";
			String subSecNm="";
			while(rs.next()) {
				subSecNm = rs.getString(1);
				if(previousSubSecNm.equalsIgnoreCase(subSecNm)){
				//logger.debug("nbs_conversion_master_uid = " + rs.getLong(1));
				//groupId=new Integer( rs.getInt(1));
				batch[count][0] = rs.getString(3);
				batch[count][1] = ((Long)rs.getLong(2)).toString();
				//batch[count][0] = "1033";
				//batch[count][1] = rs.getString(3);
				batch[count][2] = rs.getString(4);
				batch[count][3] = rs.getString(5);
				batch[count][4] = rs.getString(6);
				batch[count][5] = rs.getString(7);
				//if(rs.getString(9) != null && rs.getString(9).toString().equalsIgnoreCase("Coded"))
				batch[count][6] = rs.getString(8);
				
			    }else if(previousSubSecNm.equals("")){
			    	batch[count][0] = rs.getString(3);
					batch[count][1] = ((Long)rs.getLong(2)).toString();
					batch[count][2] = rs.getString(4);
					batch[count][3] = rs.getString(5);
					batch[count][4] = rs.getString(6);
					batch[count][5] = rs.getString(7);
					//if(rs.getString(9) != null && rs.getString(9).toString().equalsIgnoreCase("Coded"))
					batch[count][6] = rs.getString(8);
					//batchMap.put(rs.getString(1), batch);
				
			    }else{
			    	batchMap.put(previousSubSecNm, batch);
			    	batch=new String[20][7];
			    	count = 0;
			    	batch[count][0] = rs.getString(3);
					batch[count][1] = ((Long)rs.getLong(2)).toString();
					batch[count][2] = rs.getString(4);
					batch[count][3] = rs.getString(5);
					batch[count][4] = rs.getString(6);
					batch[count][5] = rs.getString(7);
					//if(rs.getString(9) != null && rs.getString(9).toString().equalsIgnoreCase("Coded"))
					batch[count][6] = rs.getString(8);
			    }
				previousSubSecNm = subSecNm;
				count++;
				
			}
			batchMap.put(subSecNm, batch);
			logger.debug("resultCount in getMaxGroupIdForBAtch is ");
			}catch(SQLException sqlex)
			{
				logger.fatal("SQLException while getting max of groupId from wa_ui_metadata table "+ sqlex.getMessage(),sqlex);
				throw new NEDSSDAOSysException( sqlex.toString() );
			}
			catch(Exception ex)
			{
				logger.fatal("SQLException while getting max of groupId from wa_ui_metadata table "+ ex.getMessage(),ex);
				throw new NEDSSSystemException(ex.toString());
			}
			finally
			{
				closeResultSet(rs);
				closeStatement(preparedStmt2);
				releaseConnection(dbConnection);
				
			}

			return batchMap;
		}
		
		
		public Map<Object,Object>  getBatchMap(Long waTemplateUid) {
			Connection dbConnection = null;
			dbConnection = getConnection();
			PreparedStatement preparedStmt2 = null;			
			ResultSet rs = null;	
			String sql ="";
			Map<Object,Object> batchMap = new HashMap<Object,Object>();
			try {
				 	sql = " Select a.question_identifier,b.wa_template_uid,b.question_identifier,b.batch_table_appear_ind_cd,b.batch_table_header,  "
						+ "b.batch_table_column_width,b.nbs_ui_component_uid,c.code_set_nm,b.order_nbr    from wa_ui_metadata  a, " 
						+  "(select question_identifier, batch_table_appear_ind_cd,batch_table_header,  batch_table_column_width, "
		    		    +  "nbs_ui_component_uid,unit_value,wa_template_uid,question_group_seq_nbr,unit_type_cd,order_nbr from wa_ui_metadata "
		    		     + "where (unit_type_cd is null or unit_type_cd = 'CODED' ) and wa_template_uid  = '"+waTemplateUid.toString()+"' )  b  left outer join nbs_srte..codeset c  "
		                + " on b.unit_value=c.code_set_group_id  where  "
						+ "a.wa_template_uid = '"+waTemplateUid.toString()+"'  and a.nbs_ui_component_uid = 1016 and a.question_group_seq_nbr = b.question_group_seq_nbr "
						+ "and b.nbs_ui_component_uid in (1007,1008,1009,1013,1014,1017,1019,1031) and b.wa_template_uid  = '"+waTemplateUid.toString()+"' "
						+ "union "
						+ "Select a.question_identifier,b.wa_template_uid,b.question_identifier,b.batch_table_appear_ind_cd,b.batch_table_header, "
						+ "b.batch_table_column_width,b.nbs_ui_component_uid,null,b.order_nbr    from wa_ui_metadata  a, " 
						+  "(select question_identifier, batch_table_appear_ind_cd,batch_table_header,  batch_table_column_width, "
		    		    +  "nbs_ui_component_uid,unit_value,wa_template_uid,question_group_seq_nbr,unit_type_cd,order_nbr from wa_ui_metadata "
		    		     + "where (unit_type_cd = 'Literal') and wa_template_uid  = '"+waTemplateUid.toString()+"' )  b   "
		                + " where  "
						+ "a.wa_template_uid = '"+waTemplateUid.toString()+"'  and a.nbs_ui_component_uid = 1016 and a.question_group_seq_nbr = b.question_group_seq_nbr "
						+ "and b.nbs_ui_component_uid in (1007,1008,1009,1013,1014,1017,1019,1031) and b.wa_template_uid  = '"+waTemplateUid.toString()+"' "
						+ "order by 1,9";
			preparedStmt2 = dbConnection.prepareStatement(sql);
			String [][] batch=new String[20][7];			

			rs = preparedStmt2.executeQuery();
			int count = 0;
			String previousSubSecNm ="";
			String subSecNm="";
			while(rs.next()) {
				subSecNm = rs.getString(1);
				if(previousSubSecNm.equalsIgnoreCase(subSecNm)){
				//logger.debug("nbs_conversion_master_uid = " + rs.getLong(1));
				//groupId=new Integer( rs.getInt(1));
				batch[count][0] = rs.getString(3);				
				batch[count][1] = ((Long)rs.getLong(2)).toString();
				batch[count][2] = rs.getString(4);
				batch[count][3] = rs.getString(5);
				batch[count][4] = rs.getString(6);
				batch[count][5] = rs.getString(7);
				//if(rs.getString(9) != null && rs.getString(9).toString().equalsIgnoreCase("Coded"))
				batch[count][6] = rs.getString(8);
				
			    }else if(previousSubSecNm.equals("")){
			    	batch[count][0] = rs.getString(3);	
			    	batch[count][1] = ((Long)rs.getLong(2)).toString();
			    	batch[count][2] = rs.getString(4);
					batch[count][3] = rs.getString(5);
					batch[count][4] = rs.getString(6);
					batch[count][5] = rs.getString(7);
					//if(rs.getString(9) != null && rs.getString(9).toString().equalsIgnoreCase("Coded"))
					batch[count][6] = rs.getString(8);
					//batchMap.put(rs.getString(1), batch);
				
			    }else{
			    	batchMap.put(previousSubSecNm, batch);
			    	batch=new String[20][7];
			    	count = 0;
			    	batch[count][0] = rs.getString(3);	
			    	batch[count][1] = ((Long)rs.getLong(2)).toString();
			    	batch[count][2] = rs.getString(4);
					batch[count][3] = rs.getString(5);
					batch[count][4] = rs.getString(6);
					batch[count][5] = rs.getString(7);
					//if(rs.getString(9) != null && rs.getString(9).toString().equalsIgnoreCase("Coded"))
					batch[count][6] = rs.getString(8);
			    }
				previousSubSecNm = subSecNm;
				count++;
				
			}
			batchMap.put(subSecNm, batch);
			logger.debug("resultCount in getMaxGroupIdForBAtch is ");
			}catch(SQLException sqlex)
			{
				logger.fatal("SQLException while getting max of groupId from wa_ui_metadata table "+ sqlex.getMessage(),sqlex);
				throw new NEDSSDAOSysException( sqlex.toString() );
			}
			catch(Exception ex)
			{
				logger.fatal("SQLException while getting max of groupId from wa_ui_metadata table "+ ex.getMessage(),ex);
				throw new NEDSSSystemException(ex.toString());
			}
			finally
			{
				closeResultSet(rs);
				closeStatement(preparedStmt2);
				releaseConnection(dbConnection);
				
			}

			return batchMap;
		}
		
    public Collection<Object> findWaTemplateUidByPageNm(String pageNm)
		  throws NEDSSDAOSysException, NEDSSSystemException {
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		ArrayList<Object> templateUids = new ArrayList<Object>();
	
		try {
			dbConnection = getConnection();
			preparedStmt = dbConnection
					.prepareStatement(FIND_WA_TEMPLATE_UID_BY_PAGENM);
			preparedStmt.setString(1, pageNm);
			resultSet = preparedStmt.executeQuery();
	
			while (resultSet.next()) {
				templateUids.add(resultSet.getLong(1));
			}
	} catch (Exception ex) {
		logger.fatal("Exception in findWaTemplateUidByPageNm: ERROR = "
				+ ex.getMessage(),ex);
		throw new NEDSSSystemException(ex.toString());
	} finally {
		closeResultSet(resultSet);
		closeStatement(preparedStmt);
		releaseConnection(dbConnection);
	}

	return templateUids;
}

	public WaTemplateDT findWaTemplateByCondition(String condition)
		throws NEDSSDAOSysException, NEDSSSystemException {

		WaTemplateDT waTemplateDT = new WaTemplateDT();
		ArrayList<Object> paramList = new ArrayList<Object>();
		paramList.add(condition);
		
		String sqlQuerry = FIND_WA_TEMPLATE_BY_CONDITION;
		
		try {
			paramList = (ArrayList<Object>) preparedStmtMethod(waTemplateDT,
					 paramList, sqlQuerry, NEDSSConstants.SELECT);
			if (paramList.size() > 0)
				return (WaTemplateDT) paramList.get(0);
		} catch (Exception ex) {
			logger.fatal("Exception in findWaTemplateByCondition By Condition : ERROR = " + ex.getMessage(),ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return waTemplateDT;

}
	public WaTemplateDT findWaTemplateByConditionTypeAndBusinessObj(String condition, String templateType, String busObj)
			throws NEDSSDAOSysException, NEDSSSystemException, NEDSSAppException {

			WaTemplateDT waTemplateDT = new WaTemplateDT();
			ArrayList<Object> paramList = new ArrayList<Object>();
			paramList.add(condition);
			//if published we need Published and Published with Draft
			if (!templateType.contains(NEDSSConstants.PUBLISHED))
				paramList.add(templateType);
			paramList.add(busObj);
			
			String sqlQuery = FIND_WA_TEMPLATE_BY_CONDITION;
			if (templateType.contains(NEDSSConstants.PUBLISHED))
				sqlQuery = sqlQuery.concat(PublishedWhereClause);
			else
				sqlQuery = sqlQuery.concat(templateWhereClause);
			sqlQuery = sqlQuery.concat(templateBusObjWhereClause);
			try {
				paramList = (ArrayList<Object>) preparedStmtMethod(waTemplateDT,
						 paramList, sqlQuery, NEDSSConstants.SELECT);
				if (paramList.size() > 0)
					return (WaTemplateDT) paramList.get(0);
			} catch (Exception ex) {
				String error = "Exception in findWaTemplateByConditionTypeAndBusinessObj : ERROR = condition: " + condition+ System.getProperty("line.separator")+" Business Object "+busObj;
				logger.fatal(error,ex);
				throw new NEDSSAppException(error, ex.toString());
			}
			return waTemplateDT;
	}	
	
	/**
	 * @param busObjType
	 * @return
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	public ArrayList<Object> findPageByBusinessObjType(String busObjType)
			throws NEDSSDAOSysException, NEDSSSystemException {

		ArrayList<Object> paramList = new ArrayList<Object>();	
		ArrayList<Object> pageList = new ArrayList<Object>();
		WaTemplateDT waTemplateDT = new WaTemplateDT();
		try {			
			paramList.add(busObjType);
			pageList = (ArrayList<Object>) preparedStmtMethod(waTemplateDT, paramList, FIND_PAGE_BY_BUS_OBJ_TYPE, NEDSSConstants.SELECT);				
		} catch (Exception ex) {
			logger.fatal("Exception in findPageByBusinessObjType, busObjType: "+busObjType+", Exception: " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return pageList;
	}

	/**
	 * @return list of NBS_BUS_OBJ_Metadata
	 */
	
	public ArrayList<Object> getNbsBusObjMetadataList(){
		ArrayList<Object> busObjList = new ArrayList<Object>();
		NbsBusObjMetadataDT nbsBusObjMetadata = new NbsBusObjMetadataDT();
		try {			
			busObjList = (ArrayList<Object>) preparedStmtMethod(nbsBusObjMetadata, null, SELECT_ALL_NBS_BUS_OBJ_METADATA, NEDSSConstants.SELECT);				
		} catch (Exception ex) {
			logger.fatal("Exception in getNbsBusObjMetadataList Exception: " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return busObjList;
	}
	
	
	/**
	 * @param busObjType
	 * @return NbsBusObjMetadataDT
	 */
	public NbsBusObjMetadataDT getNbsBusObjMetadataByBusObjType(String busObjType){
		ArrayList<Object> busObjList = new ArrayList<Object>();
		ArrayList<Object> paramList = new ArrayList<Object>();	
		NbsBusObjMetadataDT nbsBusObjMetadata = new NbsBusObjMetadataDT();
		try {
			paramList.add(busObjType);
			busObjList = (ArrayList<Object>) preparedStmtMethod(nbsBusObjMetadata, paramList, SELECT_NBS_BUS_OBJ_METADATA_BY_BUS_OBJ_TYPE, NEDSSConstants.SELECT);	
			if(busObjList!=null && busObjList.size()>0)
				return (NbsBusObjMetadataDT)busObjList.get(0);
		} catch (Exception ex) {
			logger.fatal("Exception in getNbsBusObjMetadataByBusObjType Exception: " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return nbsBusObjMetadata;
	}
	
	public void loadQuestionLookup()
			throws NEDSSDAOSysException, NEDSSSystemException {
		Connection dbConnection = null;
        CallableStatement callableStmt = null;
		try {
			dbConnection = getConnection(NEDSSConstants.MSGOUT);
            callableStmt = dbConnection.prepareCall("{call LoadQuestionLookup}");
            callableStmt.execute();
            logger.debug("Executed LoadQuestionLookup");
		} catch (Exception ex) {
			logger.fatal("Exception in updateQuestionLookup: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		finally{
			closeCallableStatement(callableStmt);
			releaseConnection(dbConnection);
		}
	}
	
	public void loadAnswerLookup()
			throws NEDSSDAOSysException, NEDSSSystemException {
		Connection dbConnection = null;
        CallableStatement callableStmt = null;
		try {
			dbConnection = getConnection(NEDSSConstants.MSGOUT);
            callableStmt = dbConnection.prepareCall("{call LoadAnswerLookup}");
            callableStmt.execute();
            logger.debug("Executed LoadAnswerLookup");
		} catch (Exception ex) {
			logger.fatal("Exception in LoadAnswerLookup: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		finally{
			closeCallableStatement(callableStmt);
			releaseConnection(dbConnection);
		}
	}
	
	
	/**
	 * @param templateType
	 * @return
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	public ArrayList<Object> findPageByTemplateType(String templateType) throws NEDSSDAOSysException, NEDSSSystemException {
		ArrayList<Object> pageList = new ArrayList<Object>();
		logger.debug("templateType: "+templateType);
		
		try{
				WaTemplateDT waTemplateDT = new WaTemplateDT();
				ArrayList<Object> paramList = new ArrayList<Object>();	
				
				String sqlQuerry = FIND_WA_TEMPLATE_BY_TEMPLATE_TYPE;
				
				paramList.add(templateType);
				pageList = (ArrayList<Object>) preparedStmtMethod(waTemplateDT, paramList, sqlQuerry, NEDSSConstants.SELECT);		
				
		}catch (Exception ex) {
			logger.fatal("Exception in findPageByTemplateType templateType:"+templateType+", ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		
		return pageList;
	}
	

	public void importLDFMetadataAndData(String businessObjectNm)
			throws NEDSSDAOSysException {
		logger.debug("businessObjectNm: " + businessObjectNm);
		Connection conn = null;
		CallableStatement cs = null;
		try {
			conn = getConnection();
			cs = conn.prepareCall("{call usp_moveLDFMetadatatoPBMetadata(?)}");
			cs.setString(1, businessObjectNm);
			logger.info("before executing usp_moveLDFMetadatatoPBMetadata");
			cs.execute();
			logger.info("executed usp_moveLDFMetadatatoPBMetadata successfully");
		} catch (Exception ex) {
			ex.printStackTrace();
			String message = "Error while importing LDF Metadata for Business Object: "
					+ businessObjectNm + ", Exception: "
					+ ex.getMessage();
			logger.error(message);
			throw new NEDSSDAOSysException(message + " "+ ex.toString(), ex);
		} finally {
			closeCallableStatement(cs);
			releaseConnection(conn);
		}
	}
	
	public void inActivateLDFPageSetId(String businessObjectNm,
			String conditionCd) throws NEDSSDAOSysException,
			NEDSSSystemException {
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		try {
			dbConnection = getConnection(NEDSSConstants.SRT);
			if (conditionCd == null) {
				preparedStmt = dbConnection
						.prepareStatement(INACTIVATE_LDF_PAGESET_ID_1);
				preparedStmt.setString(1, businessObjectNm);
			} else {
				preparedStmt = dbConnection
						.prepareStatement(INACTIVATE_LDF_PAGESET_ID_1
								+ INACTIVATE_LDF_PAGESET_ID_2);
				preparedStmt.setString(1, businessObjectNm);
				preparedStmt.setString(2, conditionCd);
			}
			preparedStmt.executeUpdate();
		} catch (Exception ex) {
			logger.fatal(
					"Exception in inActivateLDFPageSetId: ERROR = "
							+ ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}

	}
	
	
	/**
	 * @param waTemplateUid
	 * @return
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	public ArrayList<Object> findWaUiMetadataForSubFormByWaTemplateUid(Long waTemplateUid) throws NEDSSDAOSysException, NEDSSSystemException {
		ArrayList<Object> waUiMetadataList = new ArrayList<Object>();
		logger.debug("waTemplateUid: "+waTemplateUid);
		try{
				WaUiMetadataDT waUiMetadataDT = new WaUiMetadataDT();
				ArrayList<Object> paramList = new ArrayList<Object>();	
				
				String sqlQuerry = SELECT_WA_UI_METADATA_FOR_SUB_FORMS;
				
				paramList.add(waTemplateUid);
				waUiMetadataList = (ArrayList<Object>) preparedStmtMethod(waUiMetadataDT, paramList, sqlQuerry, NEDSSConstants.SELECT);		
				
		}catch (Exception ex) {
			logger.fatal("Exception in findWaUiMetadataForSubFormByWaTemplateUid templateType:"+waTemplateUid+", ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return waUiMetadataList;
	}
	
	
	@SuppressWarnings("unchecked")
	public Collection<Object> getAllPagesInPublishMode()
			throws NEDSSDAOSysException, NEDSSSystemException {

		WaTemplateDT waTemplate = new WaTemplateDT();
		ArrayList<Object> paramList = new ArrayList<Object>();
		try {
			   
			    paramList = (ArrayList<Object>) preparedStmtMethod(waTemplate,
							paramList, FIND_ALL_PUBLISHED_PAGES_SQL, NEDSSConstants.SELECT);
	
				return (ArrayList<Object>) paramList;
			
		} catch (Exception ex) {
			logger.fatal("Exception in getAllPagesInPublishMode: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}
	

	
	@SuppressWarnings("unchecked")
	public Collection<Object> getPagesByTemplateName(String templateNames)
			throws NEDSSDAOSysException, NEDSSSystemException {

		WaTemplateDT waTemplate = new WaTemplateDT();
		ArrayList<Object> paramList = new ArrayList<Object>();

		try {
			 String query = "";
		     query =FIND_PAGES_BY_TEMPLATE_NM_SQL;
		     
		     query+=" and template_nm in ("+templateNames+") order by 1 desc";
		    	 
			 paramList = (ArrayList<Object>) preparedStmtMethod(waTemplate,paramList, query, NEDSSConstants.SELECT);
	
			 return (ArrayList<Object>) paramList;
			
		} catch (Exception ex) {
			logger.fatal("Exception in getPagesByTemplateName: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}
	
	/**
	 * Update pre-pop mapping to reflect the right investigation form code
	 * 
	 * @param oldFormCd, newFormCd
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	public void updatePrepopMapping(String oldFormCd, String newFormCd)
			throws NEDSSDAOSysException, NEDSSSystemException {

		ArrayList<Object> paramList = new ArrayList<Object>();
		paramList.add(newFormCd);
		paramList.add(oldFormCd);

		try {
			preparedStmtMethod(null, paramList, UPDATE_PPREPOP_MAPPING, NEDSSConstants.UPDATE);

		} catch (Exception ex) {
			logger.fatal("Exception in updatePrepopMapping: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}
	
}
