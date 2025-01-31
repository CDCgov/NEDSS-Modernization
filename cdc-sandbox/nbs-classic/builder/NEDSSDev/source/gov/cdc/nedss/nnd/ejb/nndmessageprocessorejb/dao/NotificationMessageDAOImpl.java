package gov.cdc.nedss.nnd.ejb.nndmessageprocessorejb.dao;

import gov.cdc.nedss.act.notification.dt.NotificationDT;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.nnd.dt.CaseNotificationDataDT;
import gov.cdc.nedss.nnd.dt.LaboratoryDT;
import gov.cdc.nedss.nnd.dt.NotificationParticipationDT;
import gov.cdc.nedss.nnd.dt.SummaryCaseRepeatingDataDT;
import gov.cdc.nedss.nnd.dt.VaccinationDT;
import gov.cdc.nedss.nnd.util.NNDConstantUtil;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.DataTables;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.TreeSet;


/**
* Name:		NotificationMessageDAOImpl.java
* Description:	DAO Object for to Retrieve Case Notification data 
* Copyright:	Copyright (c) 2008
* Company: 	Computer Sciences Corporation
* @author	Beau Bannerman
*/

public class NotificationMessageDAOImpl extends DAOBase{
	static final LogUtils logger = new LogUtils(NotificationMessageDAOImpl.class.getName());
    private static PropertyUtil propertyUtil= PropertyUtil.getInstance();

	private final String SELECT_STANDARD_CASE_REPORT_EXPORT_DATA_COLLECTION = "SELECT nndm.question_identifier_nnd AS \"questionIdentifierNND\", nndm.question_label_nnd AS \"questionLabelNND\", nbsq.question_oid AS \"questionOID\", "
		+"nbsq.question_oid_system_txt AS \"questionOIDSystemTxt\", nbsq.question_identifier AS \"questionIdentifier\", "
		+"COALESCE(num.question_label, nbsq.question_label) AS \"uiMetadataQuestionLabel\", COALESCE(num.question_label, nbsq.question_label) AS \"nbsQuestionLabel\", nbsq.question_unit_identifier AS \"questionUnitIdentifier\", "
		+"nbsq.unit_parent_identifier AS \"unitParentIdentifier\", nndm.HL7_segment_field AS \"hl7SegmentField\", nndm.order_group_id AS \"orderGroupId\", "
		+"nndm.question_required_nnd AS \"questionRequiredNND\", nndm.question_data_type_nnd AS \"questionDataTypeNND\", "
		+"nndm.translation_table_nm AS \"translationTableNm\", COALESCE(num.code_set_group_id, nbsq.code_set_group_id) AS \"codesetGroupId\", nbsa.answer_txt AS \"answerTxt\", "
		+"nndm.xml_path AS \"xmlPath\", nndm.xml_tag AS \"xmlTag\", nndm.xml_data_type AS \"xmlDataType\", nbsa.answer_large_txt AS \"answerLargeTxt\", "
		+"nbsq.data_location AS \"dataLocation\", nbsq.data_use_cd AS \"dataUseCd\", nndm.part_type_cd AS \"partTypeCd\", nbsq.data_cd AS \"dataCd\", "
		+"nbsq.data_type AS \"dataType\", nbsq.question_group_seq_nbr AS \"questionGroupSeqNbr\", nbsq.legacy_data_location AS \"legacyDataLocation\", "
		+"nndm.repeat_group_seq_nbr AS \"repeatGroupSeqNbr\", nndm.question_map AS \"questionMap\", nndm.indicator_cd AS \"indicatorCd\" "
		+"FROM NND_Metadata nndm INNER JOIN "
		+"NBS_Question nbsq ON nndm.question_identifier = nbsq.question_identifier " 
		+"LEFT OUTER JOIN "
		+"(SELECT answer_txt, nbs_question_uid, act_uid, answer_large_txt "
		+"FROM NBS_case_answer "
		+"WHERE (act_uid = ?)) nbsa ON nbsq.nbs_question_uid = nbsa.nbs_question_uid "
		//Need to join with both nbs_question(To fetch data for case report form code (CR_FORM) since the data may not exists in nbs_ui_metadata table if non page builder page) 
		//and nbs_ui_metadata(fetch data for override question behavior) tables. 
		+"LEFT OUTER JOIN NBS_ui_metadata num on "
		+"nbsq.question_identifier = num.question_identifier "
		+"and num.investigation_form_cd in (select investigation_form_cd from nbs_srte..condition_code where condition_cd = ?) "
		+"WHERE (nndm.question_required_nnd = 'R') AND (nndm.investigation_form_cd = '" + NEDSSConstants.NOTF_CR_FORM + "') OR "
		+"(nndm.question_required_nnd = 'O') AND (nndm.investigation_form_cd = '" + NEDSSConstants.NOTF_CR_FORM + "') AND (UPPER(nbsq.data_location) "
		+"<> 'NBS_CASE_ANSWER.ANSWER_TXT') OR "
		+"(nndm.question_required_nnd = 'O') AND (nndm.investigation_form_cd = '" + NEDSSConstants.NOTF_CR_FORM + "') AND (nbsa.answer_txt IS NOT NULL) AND "
		+"(UPPER(nbsq.data_location) = 'NBS_CASE_ANSWER.ANSWER_TXT')";
	

	private final String SELECT_DISEASE_SPECIFIC_CASE_REPORT_EXPORT_DATA_COLLECTION_SQL = "SELECT nuim.question_identifier AS \"questionIdentifierNND\", nuim.question_label AS \"questionLabelNND\", nuim.question_oid AS \"questionOID\", "
		+"nuim.question_oid_system_txt AS \"questionOIDSystemTxt\", nuim.question_identifier AS \"questionIdentifier\", "
		+"nuim.question_label AS \"uiMetadataQuestionLabel\", nuim.question_label AS \"nbsQuestionLabel\", nuim.question_unit_identifier AS \"questionUnitIdentifier\", "
		+"nuim.unit_parent_identifier AS \"unitParentIdentifier\", nuim.batch_table_header AS \"batchTableHeader\", nuim.batch_table_column_width AS \"batchTableColumnWidth\", "
		+"nuim.code_set_group_id AS \"codesetGroupId\", nbsa.answer_txt AS \"answerTxt\", "
		+"nbsa.answer_large_txt AS \"answerLargeTxt\", nbsa.answer_group_seq_nbr AS \"answerGroupSeqNbr\", nuim.unit_type_cd AS \"unitTypeCd\", nuim.unit_value AS \"unitValue\", nuim.other_value_ind_cd AS \"otherValueIndCd\", "
		+"nuim.data_location AS \"dataLocation\", nuim.data_use_cd AS \"dataUseCd\", nuim.part_type_cd AS \"partTypeCd\", nuim.data_cd AS \"dataCd\", "
		+"nuim.data_type AS \"dataType\", nuim.question_group_seq_nbr AS \"questionGroupSeqNbr\", nuim.legacy_data_location AS \"legacyDataLocation\" "
		+"FROM NBS_ui_metadata AS nuim LEFT OUTER JOIN "
        +"(SELECT answer_txt, nbs_question_uid, act_uid, answer_large_txt, answer_group_seq_nbr FROM NBS_case_answer "
        +"WHERE (act_uid = ?)) AS nbsa ON nbsa.nbs_question_uid = nuim.nbs_question_uid LEFT OUTER JOIN "
		+"(SELECT investigation_form_cd "
		+"FROM " + NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..Condition_code "
		+"WHERE (condition_cd = ?)) cc ON cc.investigation_form_cd = nuim.investigation_form_cd "        
        +"WHERE (UPPER(nuim.data_location) = 'NBS_CASE_ANSWER.ANSWER_TXT') AND " 
        +"(nbsa.answer_txt IS NOT NULL) AND (cc.investigation_form_cd = nuim.investigation_form_cd)";
		

	private final String SELECT_AGGREGATE_SUMMARY_EXPORT_DATA_COLLECTION_SQL = "SELECT nndm.question_identifier_nnd AS \"questionIdentifierNND\", nndm.question_label_nnd AS \"questionLabelNND\", "
		+"nuim.question_oid AS \"questionOID\", nuim.question_oid_system_txt AS \"questionOIDSystemTxt\", nuim.question_identifier AS \"questionIdentifier\", nuim.question_label AS \"uiMetadataQuestionLabel\", "
		+"nuim.question_label AS \"nbsQuestionLabel\", nuim.question_unit_identifier AS \"questionUnitIdentifier\", nuim.unit_parent_identifier AS \"unitParentIdentifier\", "
		+"nndm.HL7_segment_field AS \"hl7SegmentField\", nndm.order_group_id AS \"orderGroupId\", nndm.question_required_nnd AS \"questionRequiredNND\", "
		+"nndm.question_data_type_nnd AS \"questionDataTypeNND\", nndm.translation_table_nm AS \"translationTableNm\", nuim.code_set_group_id AS \"codesetGroupId\", nbsa.answer_txt AS \"answerTxt\", "
		+"nndm.xml_path AS \"xmlPath\", nndm.xml_tag AS \"xmlTag\", nndm.xml_data_type AS \"xmlDataType\", nndm.question_order_nnd AS \"questionOrderNND\", "
		+"nbsa.answer_large_txt AS \"answerLargeTxt\", nuim.data_location AS \"dataLocation\", nuim.data_use_cd AS \"dataUseCd\", nndm.part_type_cd AS \"partTypeCd\", nuim.data_cd AS \"dataCd\", "
		+"unit_type_cd AS \"unitTypeCd\", unit_value AS \"unitValue\", other_value_ind_cd AS \"otherValueIndCd\", "
		+"nuim.data_type AS \"dataType\", nuim.question_group_seq_nbr \"questionGroupSeqNbr\", nuim.legacy_data_location AS \"legacyDataLocation\", "
		+"nndm.repeat_group_seq_nbr AS \"repeatGroupSeqNbr\", nndm.question_map AS \"questionMap\", nndm.indicator_cd AS \"indicatorCd\" "
		+"FROM NND_Metadata nndm INNER JOIN "
		+"NBS_UI_metadata nuim ON nndm.nbs_ui_metadata_uid = nuim.nbs_ui_metadata_uid LEFT OUTER JOIN "
		+"(SELECT answer_txt, nbs_question_uid, act_uid, answer_large_txt FROM NBS_case_answer "
		+"WHERE (act_uid = ?)) nbsa "
		+"ON nuim.nbs_question_uid = nbsa.nbs_question_uid LEFT OUTER JOIN "
		+"(SELECT summary_investigation_form_cd FROM " + NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..Condition_code "
		+"WHERE (condition_cd = ?)) cc ON cc.summary_investigation_form_cd = nndm.investigation_form_cd "
		+"WHERE (nndm.repeat_group_seq_nbr IS NULL) AND (nndm.question_required_nnd = 'R') AND (cc.summary_investigation_form_cd = nndm.investigation_form_cd) OR "
		+"(nndm.repeat_group_seq_nbr IS NULL) AND (nndm.question_required_nnd = 'O') AND (UPPER(nuim.data_location) <> 'NBS_CASE_ANSWER.ANSWER_TXT') AND "
		+"(cc.summary_investigation_form_cd = nndm.investigation_form_cd) OR (nndm.repeat_group_seq_nbr IS NULL) AND "
		+"(nndm.question_required_nnd = 'O') AND (UPPER(nuim.data_location) = 'NBS_CASE_ANSWER.ANSWER_TXT') AND (nbsa.answer_txt IS NOT NULL) AND "
		+"(cc.summary_investigation_form_cd = nndm.investigation_form_cd)";
	

	private final String SELECT_NND_NBS_CASE_DATA_COLLECTION_SQL = "SELECT nndm.question_identifier_nnd AS \"questionIdentifierNND\", nndm.question_label_nnd AS \"questionLabelNND\", nuim.question_oid AS \"questionOID\", "
		+"nuim.question_oid_system_txt AS \"questionOIDSystemTxt\", nuim.question_identifier AS \"questionIdentifier\", "
		+"nuim.question_label AS \"uiMetadataQuestionLabel\", nuim.question_label AS \"nbsQuestionLabel\", nuim.question_unit_identifier AS \"questionUnitIdentifier\", "
		+"nuim.unit_parent_identifier AS \"unitParentIdentifier\", nndm.HL7_segment_field AS \"hl7SegmentField\", nndm.order_group_id AS \"orderGroupId\", "
		+"nndm.question_required_nnd AS \"questionRequiredNND\", nndm.question_data_type_nnd AS \"questionDataTypeNND\", "
		+"nndm.translation_table_nm AS \"translationTableNm\", nuim.code_set_group_id AS \"codesetGroupId\", nbsa.answer_txt AS \"answerTxt\", "
		+"nbsa.answer_large_txt AS \"answerLargeTxt\", nbsa.answer_group_seq_nbr AS \"observationSubID\"," 
		+"nuim.data_location AS \"dataLocation\", nuim.legacy_data_location AS \"legacyDataLocation\", "
		+"unit_type_cd AS \"unitTypeCd\", unit_value AS \"unitValue\", other_value_ind_cd AS \"otherValueIndCd\", "
		+"nuim.data_use_cd AS \"dataUseCd\", nuim.part_type_cd AS \"partTypeCd\", nuim.data_cd AS \"dataCd\", "
		+"nuim.question_group_seq_nbr AS \"questionGroupSeqNbr\", "
		+"nndm.repeat_group_seq_nbr AS \"repeatGroupSeqNbr\", nndm.question_map AS \"questionMap\", nndm.indicator_cd AS \"indicatorCd\" "
		+"FROM NND_Metadata nndm INNER JOIN "
		+"NBS_UI_Metadata nuim ON nndm.nbs_ui_metadata_uid = nuim.nbs_ui_metadata_uid LEFT OUTER JOIN "
		+"(SELECT answer_txt, nbs_question_uid, act_uid, answer_large_txt, answer_group_seq_nbr "
		+"FROM NBS_case_answer "
		+"WHERE (act_uid = ?)) nbsa ON nuim.nbs_question_uid = nbsa.nbs_question_uid LEFT OUTER JOIN "
		+"(SELECT investigation_form_cd "
		+"FROM " + NEDSSConstants.SYSTEM_REFERENCE_TABLE + "..Condition_code "
		+"WHERE (condition_cd = ?)) cc ON cc.investigation_form_cd = nndm.investigation_form_cd "
		+"WHERE (nndm.question_required_nnd = 'R') AND (cc.investigation_form_cd = nndm.investigation_form_cd) OR "
		+"(nndm.question_required_nnd = 'O') AND (UPPER(nuim.data_location) <> 'NBS_CASE_ANSWER.ANSWER_TXT') AND "
		+"(cc.investigation_form_cd = nndm.investigation_form_cd) OR "
		+"(nndm.question_required_nnd = 'O') AND (UPPER(nuim.data_location) = 'NBS_CASE_ANSWER.ANSWER_TXT') AND "
		+"(cc.investigation_form_cd = nndm.investigation_form_cd) AND (nbsa.answer_txt IS NOT NULL)";

	private final String SELECT_NND_NBS_CASE_DATA_COLLECTION_VAC_SQL = "SELECT nndm.question_identifier_nnd AS \"questionIdentifierNND\", nndm.question_label_nnd AS \"questionLabelNND\", nuim.question_oid AS \"questionOID\", "
			+"nuim.question_oid_system_txt AS \"questionOIDSystemTxt\", nuim.question_identifier AS \"questionIdentifier\", "
			+"nuim.question_label AS \"uiMetadataQuestionLabel\", nuim.question_label AS \"nbsQuestionLabel\", nuim.question_unit_identifier AS \"questionUnitIdentifier\", "
			+"nuim.unit_parent_identifier AS \"unitParentIdentifier\", nndm.HL7_segment_field AS \"hl7SegmentField\", nndm.order_group_id AS \"orderGroupId\", "
			+"nndm.question_required_nnd AS \"questionRequiredNND\", nndm.question_data_type_nnd AS \"questionDataTypeNND\", "
			+"nndm.translation_table_nm AS \"translationTableNm\", nuim.code_set_group_id AS \"codesetGroupId\", '' AS \"answerTxt\", "
			+"'' AS \"answerLargeTxt\", '' AS \"observationSubID\"," 
			+"nuim.data_location AS \"dataLocation\", nuim.legacy_data_location AS \"legacyDataLocation\", "
			+"unit_type_cd AS \"unitTypeCd\", unit_value AS \"unitValue\", other_value_ind_cd AS \"otherValueIndCd\", "
			+"nuim.data_use_cd AS \"dataUseCd\", nuim.part_type_cd AS \"partTypeCd\", nuim.data_cd AS \"dataCd\", "
			+"nuim.question_group_seq_nbr AS \"questionGroupSeqNbr\" "
			+"FROM NND_Metadata nndm INNER JOIN "
			+"NBS_UI_Metadata nuim ON nndm.nbs_ui_metadata_uid = nuim.nbs_ui_metadata_uid where nndm.investigation_form_cd = "
			+ "(select form_cd from wa_template where BUS_OBJ_TYPE = 'VAC' and TEMPLATE_TYPE in ('Published', 'Published With Draft'))"; //should only be one or none VAC form
	
	private final String SELECT_NND_NBS_CASE_DATA_COLLECTION_LAB_SQL =  "SELECT nndm.question_order_nnd AS questionOrderNND, nndm.question_identifier_nnd AS \"questionIdentifierNND\", nndm.question_label_nnd AS \"questionLabelNND\", nuim.question_oid AS \"questionOID\", "
			+"nuim.question_oid_system_txt AS \"questionOIDSystemTxt\", nuim.question_identifier AS \"questionIdentifier\", "
			+"nuim.question_label AS \"uiMetadataQuestionLabel\", nuim.question_label AS \"nbsQuestionLabel\", nuim.question_unit_identifier AS \"questionUnitIdentifier\", "
			+"nuim.unit_parent_identifier AS \"unitParentIdentifier\", nndm.HL7_segment_field AS \"hl7SegmentField\", nndm.order_group_id AS \"orderGroupId\", "
			+"nndm.question_required_nnd AS \"questionRequiredNND\", nndm.question_data_type_nnd AS \"questionDataTypeNND\", "
			+"nndm.translation_table_nm AS \"translationTableNm\", nuim.code_set_group_id AS \"codesetGroupId\", '' AS \"answerTxt\", "
			+"'' AS \"answerLargeTxt\", '' AS \"observationSubID\"," 
			+"nuim.data_location AS \"dataLocation\", nuim.legacy_data_location AS \"legacyDataLocation\", "
			+"unit_type_cd AS \"unitTypeCd\", unit_value AS \"unitValue\", other_value_ind_cd AS \"otherValueIndCd\", "
			+"nuim.data_use_cd AS \"dataUseCd\", nuim.part_type_cd AS \"partTypeCd\", nuim.data_cd AS \"dataCd\", "
			+"nuim.question_group_seq_nbr AS \"questionGroupSeqNbr\" "
			+"FROM NND_Metadata nndm INNER JOIN "
			+"NBS_UI_Metadata nuim ON nndm.nbs_ui_metadata_uid = nuim.nbs_ui_metadata_uid where nndm.investigation_form_cd = 'LAB_EVENT'"; //should only be one or none LAB form
	

	private final String SELECT_V_VACCIONATION_DATA_SQL = "Select VAC_UID AS \"vaccUid\","
			+ "CASE_UID AS \"caseUid\","
			+ "VAC_LOCAL_ID AS \"vaccLocalId\","
			+ "VAC_ADMIN_DT AS \"vaccAdminDt\","
			+ "VAC_TYPE_CD AS \"vaccTypeCd\","
			+ "VAC_TYPE AS \"vaccType\","
			+ "VAC_EXP_DT AS \"vaccExpDt\","
			+ "VAC_LOT_NBR AS \"vaccLotNbr\","
			+ "VAC_BODY_SITE_CD AS \"vaccBodySiteCd\","
			+ "VAC_BODY_SITE AS \"vaccBodySite\","
			+ "VAC_MFGR_CD AS \"vaccMfgrCd\","
			+ "VAC_MFGR AS \"vaccMfgr\","
			+ "VAC_SUBJ_UID AS \"vaccSubjUid\","
			+ "VAC_SUBJ_FNAME AS \"vaccSubjFname\","
			+ "VAC_SUBJ_LOCAL_ID AS \"vaccSubjLname\","
			+ "VAC_SUBJ_LNAME AS \"vaccSubjLocalId\","
			+ "VAC_ADD_TIME AS \"vaccAddTime\","
			+ "VAC_ADD_USER AS \"vaccAddUser\","
			+ "VAC_LAST_CHG_TIME AS \"vacclastChgTime\","
			+ "VAC_LAST_CHG_USER AS \"vaccLastChgUser\","
			+ "VAC_VERSION_NBR AS \"vaccVersionNbr\","
			+ "VAC_DOSE_NBR AS \"vaccDoseNbr\","
			+ "VAC_INFO_SOURCE_CD AS \"vaccInfoSourceCd\","
			+ "VAC_INFO_SOURCE AS \"vaccInfoSource\""
			+ " from v_vaccination where case_uid =?";
	

	 private final String SELECT_NOTIFICATION_PARTICIPATION_COLLECTION = "SELECT subject_entity_uid \"subjectEntityUID\", type_cd \"typeCd\" FROM Participation WHERE act_uid = ? " ;

	 private final String SELECT_PHCR_PARTICIPATION_COLLECTION = "SELECT subject_entity_uid \"subjectEntityUID\", type_cd \"typeCd\", subject_class_cd \"subjectClassCd\" FROM Participation WHERE act_uid = ? " ;

	 private final String SELECT_SUMMARY_CASE_REPEATING_DATA_COLLECTION = 
		 "SELECT NBS_indicator.code AS \"indAnswerTxt\", NBS_Question_Indicator.question_identifier AS \"indQuestionIdentifier\", " +
		 "NND_Metadata_Indicator.question_identifier_nnd AS \"indQuestionIdentifierNND\", " +
		 "NBS_Question_Indicator.code_set_group_id AS \"indCodesetGroupId\", NBS_Question_Indicator.question_oid AS \"indQuestionOID\", " +
		 "NBS_Question_Indicator.question_oid_system_txt AS \"indQuestionOIDSystemTxt\", " +
		 "NND_Metadata_Indicator.order_group_id AS \"indOrderGroupId\", " +
		 "NND_Metadata_Indicator.question_required_nnd AS \"indQuestionRequiredNND\", " +
		 "NBS_Question_Indicator.data_location AS \"indDataLocation\", " +
		 "NND_Metadata_Indicator.question_data_type_nnd AS \"indQuestionDataTypeNND\", " +
		 "NND_Metadata_Indicator.HL7_segment_field AS \"indHl7SegmentField\", " +
		 "NBS_Question_Indicator.question_label AS \"indNbsQuestionLabel\", " +
		 "NND_Metadata_Indicator.question_label_nnd AS \"indQuestionLabelNND\", NBS_aggregate.code AS \"aggAnswerTxt\", " +
		 "NBS_Question_Aggregate.question_identifier AS \"aggQuestionIdentifier\", " +
		 "NND_Metadata_Aggregate.question_identifier_nnd AS \"aggQuestionIdentifierNND\", " +
		 "NBS_Question_Aggregate.code_set_group_id AS \"aggCodesetGroupId\", NBS_Question_Aggregate.question_oid AS \"aggQuestionOID\", " +
		 "NBS_Question_Aggregate.question_oid_system_txt AS \"aggQuestionOIDSystemTxt\", " +
		 "NND_Metadata_Aggregate.order_group_id AS \"aggOrderGroupId\", " +
		 "NND_Metadata_Aggregate.question_required_nnd AS \"aggQuestionRequiredNND\", " +
		 "NBS_Question_Aggregate.data_location AS \"aggDataLocation\", " +
		 "NND_Metadata_Aggregate.question_data_type_nnd AS \"aggQuestionDataTypeNND\", " +
		 "NND_Metadata_Aggregate.HL7_segment_field AS \"aggHl7SegmentField\", " +
		 "NBS_Question_Aggregate.question_label AS \"aggNbsQuestionLabel\", " +
		 "NND_Metadata_Aggregate.question_label_nnd AS \"aggQuestionLabelNND\", NBS_case_answer.answer_txt AS \"tcAnswerTxt\", " +
		 "NBS_Question_TotalCount.question_identifier AS \"tcQuestionIdentifier\", " +
		 "NND_Metadata_TotalCount.question_identifier_nnd AS \"tcQuestionIdentifierNND\", " +
		 "NBS_Question_TotalCount.code_set_group_id AS \"tcCodesetGroupId\", " +
		 "NBS_Question_TotalCount.question_oid AS \"tcQuestionOID\", " +
		 "NBS_Question_TotalCount.question_oid_system_txt AS \"tcQuestionOIDSystemTxt\", " +
		 "NND_Metadata_TotalCount.order_group_id AS \"tcOrderGroupId\", " +
		 "NND_Metadata_TotalCount.question_required_nnd AS \"tcQuestionRequiredNND\", " +
		 "NBS_Question_TotalCount.data_location AS \"tcDataLocation\", " +
		 "NND_Metadata_TotalCount.question_data_type_nnd AS \"tcQuestionDataTypeNND\", " +
		 "NND_Metadata_TotalCount.HL7_segment_field AS \"tcHl7SegmentField\", " +
		 "NBS_Question_TotalCount.question_label AS \"tcNbsQuestionLabel\", " +
		 "NND_Metadata_TotalCount.question_label_nnd AS \"tcQuestionLabelNND\", " +
		 "NBS_table_metadata.aggregate_seq_nbr AS \"aggSeqNbr\", " +
         "NBS_table_metadata.indicator_seq_nbr AS \"indSeqNbr\" " +
		 "FROM NBS_case_answer INNER JOIN " +
		 "NBS_table_metadata ON NBS_case_answer.nbs_table_metadata_uid = NBS_table_metadata.nbs_table_metadata_uid INNER JOIN " +
		 "NBS_aggregate ON NBS_table_metadata.nbs_aggregate_uid = NBS_aggregate.nbs_aggregate_uid INNER JOIN " +
		 "NBS_indicator ON NBS_table_metadata.nbs_indicator_uid = NBS_indicator.nbs_indicator_uid INNER JOIN " +
		 "NBS_Question NBS_Question_Aggregate ON NBS_aggregate.nbs_question_uid = NBS_Question_Aggregate.nbs_question_uid INNER JOIN " +
		 "NBS_Question NBS_Question_Indicator ON NBS_indicator.nbs_question_uid = NBS_Question_Indicator.nbs_question_uid INNER JOIN " +
		 "NBS_Question NBS_Question_TotalCount ON " +
		 "NBS_table_metadata.nbs_question_uid = NBS_Question_TotalCount.nbs_question_uid INNER JOIN " +
		 "NND_Metadata NND_Metadata_Indicator ON " +
		 "NBS_Question_Indicator.question_identifier = NND_Metadata_Indicator.question_identifier INNER JOIN " +
		 "NND_Metadata NND_Metadata_Aggregate ON " +
		 "NBS_Question_Aggregate.question_identifier = NND_Metadata_Aggregate.question_identifier INNER JOIN " +
		 "NND_Metadata NND_Metadata_TotalCount ON " +
		 "NBS_Question_TotalCount.question_identifier = NND_Metadata_TotalCount.question_identifier " +
		 "WHERE (NBS_case_answer.act_uid = ?) AND (NBS_table_metadata.msg_exclude_ind_cd IS NULL) OR " +
		 "(NBS_table_metadata.msg_exclude_ind_cd = 'N') " +
		 "ORDER BY NBS_table_metadata.aggregate_seq_nbr, NBS_table_metadata.indicator_seq_nbr";	 
	/**
	 * gets the CaseNotificationDataDT Collection<Object>  Object for a given publicHealthCaseUID
	 * @return Collection<Object>  of CaseNotificationDataDT
	 */
	@SuppressWarnings("unchecked")
	public Collection<Object>  getCaseNotificationDataDTCollection(Long publicHealthCaseUID, NotificationDT notificationDT) throws NEDSSSystemException{
		String sql;
       sql = SELECT_NND_NBS_CASE_DATA_COLLECTION_SQL;

		
		logger.debug("SELECT_CASE_NOTIFICATION_DATA_COLLECTION="+sql);

		CaseNotificationDataDT caseNotificationDataDT  = new CaseNotificationDataDT();
		ArrayList<Object> caseNotificationDataDTCollection  = new ArrayList<Object> ();
		caseNotificationDataDTCollection.add(publicHealthCaseUID);
		caseNotificationDataDTCollection.add(notificationDT.getCaseConditionCd());

		//If this is an EXP_NOTF or SHARE_NOTF - Case Report export/share - then we don't use condition code to look up 
		//investigation form, and instead just use 'CR_FORM'
		if ( notificationDT.getCd().equals(NEDSSConstants.CLASS_CD_EXP_NOTF) || notificationDT.getCd().equals(NEDSSConstants.CLASS_CD_EXP_NOTF_PHDC) ||
			 notificationDT.getCd().equals(NEDSSConstants.CLASS_CD_SHARE_NOTF) || notificationDT.getCd().equals(NEDSSConstants.CLASS_CD_SHARE_NOTF_PHDC)) {
			sql = SELECT_STANDARD_CASE_REPORT_EXPORT_DATA_COLLECTION;
		}
		else if (notificationDT.getCd().equals(NEDSSConstants.AGGREGATE_NOTIFICATION_CD)) {
	        sql = SELECT_AGGREGATE_SUMMARY_EXPORT_DATA_COLLECTION_SQL;
			
		}
		
		try{
			caseNotificationDataDTCollection  = (ArrayList<Object> )preparedStmtMethod(caseNotificationDataDT, caseNotificationDataDTCollection, sql, NEDSSConstants.SELECT);
		}
		catch (Exception ex) {
			String exString = "Exception in NotificationMessageDAOImpl.getCaseNotificationDataDTCollection:  ERROR = " + ex.getMessage();
			logger.fatal("Exception  = "+exString, ex);
			throw new NEDSSSystemException(exString);
		}
		
		return caseNotificationDataDTCollection;
	}
	
	/**
	 * getCaseNotificationDataDTCollectionVacc(): method for reading the vaccination metadata
	 * @param publicHealthCaseUID
	 * @param notificationDT
	 * @return
	 * @throws NEDSSSystemException
	 */
	@SuppressWarnings("unchecked")
	public Collection<Object>  getCaseNotificationDataDTCollectionVacc(Long publicHealthCaseUID, NotificationDT notificationDT) throws NEDSSSystemException{
		String sql;
        sql = SELECT_NND_NBS_CASE_DATA_COLLECTION_VAC_SQL;

		
		logger.debug("SELECT_CASE_NOTIFICATION_DATA_COLLECTION_VAC="+sql);

		CaseNotificationDataDT caseNotificationDataDT  = new CaseNotificationDataDT();
		ArrayList<Object> caseNotificationDataDTCollection  = new ArrayList<Object> ();
		//caseNotificationDataDTCollection.add(publicHealthCaseUID);
		//caseNotificationDataDTCollection.add(notificationDT.getCaseConditionCd());
		
		/* TODO: add or delete it?
		 
		//If this is an EXP_NOTF or SHARE_NOTF - Case Report export/share - then we don't use condition code to look up 
		//investigation form, and instead just use 'CR_FORM'
		if ( notificationDT.getCd().equals(NEDSSConstants.CLASS_CD_EXP_NOTF) || notificationDT.getCd().equals(NEDSSConstants.CLASS_CD_EXP_NOTF_PHDC) ||
			 notificationDT.getCd().equals(NEDSSConstants.CLASS_CD_SHARE_NOTF) || notificationDT.getCd().equals(NEDSSConstants.CLASS_CD_SHARE_NOTF_PHDC)) {
			if (propertyUtil.getDatabaseServerType() != null && propertyUtil.getDatabaseServerType().equalsIgnoreCase(NEDSSConstants.ORACLE_ID))
				sql = SELECT_STANDARD_CASE_REPORT_EXPORT_DATA_COLLECTION_ORACLE;
			else
				sql = SELECT_STANDARD_CASE_REPORT_EXPORT_DATA_COLLECTION;
		}
		else if (notificationDT.getCd().equals(NEDSSConstants.AGGREGATE_NOTIFICATION_CD)) {
	        if (propertyUtil.getDatabaseServerType() != null && propertyUtil.getDatabaseServerType().equalsIgnoreCase(NEDSSConstants.ORACLE_ID))
	        	sql = SELECT_AGGREGATE_SUMMARY_EXPORT_DATA_COLLECTION_ORACLE;
	        else
	        	sql = SELECT_AGGREGATE_SUMMARY_EXPORT_DATA_COLLECTION_SQL;
			
		}*/
		
		try{
			caseNotificationDataDTCollection  = (ArrayList<Object> )preparedStmtMethod(caseNotificationDataDT, caseNotificationDataDTCollection, sql, NEDSSConstants.SELECT);
		}
		catch (Exception ex) {
			String exString = "Exception in NotificationMessageDAOImpl.getCaseNotificationDataDTCollectionVacc:  ERROR = " + ex.getMessage();
			logger.fatal("Exception  = "+exString, ex);
			throw new NEDSSSystemException(exString);
		}
		
		return caseNotificationDataDTCollection;
	}
	
	/**
	 * getCaseNotificationDataDTCollectionLab(): method for reading the Lab metadata
	 * @param publicHealthCaseUID
	 * @param notificationDT
	 * @return
	 * @throws NEDSSSystemException
	 */
	@SuppressWarnings("unchecked")
	public Collection<Object>  getCaseNotificationDataDTCollectionLab(Long publicHealthCaseUID, NotificationDT notificationDT) throws NEDSSSystemException{
		String sql;
        sql = SELECT_NND_NBS_CASE_DATA_COLLECTION_LAB_SQL;

		
		logger.debug("Query to execute SELECT_CASE_NOTIFICATION_DATA_COLLECTION_LAB = "+sql);

		CaseNotificationDataDT caseNotificationDataDT  = new CaseNotificationDataDT();
		ArrayList<Object> caseNotificationDataDTCollection  = new ArrayList<Object> ();

		
		try{
			logger.debug("Before executing query");
			caseNotificationDataDTCollection  = (ArrayList<Object> )preparedStmtMethod(caseNotificationDataDT, caseNotificationDataDTCollection, sql, NEDSSConstants.SELECT);
			logger.debug("After executing query");
		}
		catch (Exception ex) {
			String exString = "NotificationMessageDAOImpl.getCaseNotificationDataDTCollectionLab:  ERROR = " + ex.getMessage();
			logger.fatal("Exception = "+exString, ex);
			throw new NEDSSSystemException(exString, ex);
		}
		
		return caseNotificationDataDTCollection;
	}
	
	
	
	/**
	 * retrieveCollectionLab: returns the objects which question_order_nnd = n from the collectionLabs. n=1 -> Order, n=2 -> Result, n=3 -> Susceptibility
	 * @param collectionLabs
	 * @param n
	 * @return
	 */
	public Collection<Object> retrieveCollectionLab (Collection<Object> caseDataDTCollectionLab, int n){
		
		
		Collection<Object> caseDataDTCollectionLabsQuestionOrderNND =  new ArrayList<Object>();
		try{
		Iterator<?> it  = caseDataDTCollectionLab.iterator();
		while (it.hasNext()) {
		
			CaseNotificationDataDT caseNotif = (CaseNotificationDataDT)it.next();
			
			int questionOrder = caseNotif.getQuestionOrderNND();
			
			if(questionOrder == n){
				logger.info("Adding CaseNotificationDataDT to the collection with Question identifier: "+ caseNotif.getQuestionIdentifier() +" and questionOrder: "+n);
				caseDataDTCollectionLabsQuestionOrderNND.add(caseNotif);
			}
		}
		
		}catch(NEDSSSystemException nse)
        {
        	logger.fatal("Exception in NotificationMessageDAOImpl.retrieveCollectionLab: NEDSSSystemException  = "+nse.getMessage(), nse);
            throw new NEDSSSystemException("Exception in NotificationMessageDAOImpl.retrieveCollectionLab: Error: while obtaining database connection.\n" + nse.getMessage(), nse);
        }
		catch(Exception e)
		{
			logger.fatal("Exception in NotificationMessageDAOImpl.retrieveCollectionLab  = "+e.getMessage(), e);
		    throw new NEDSSSystemException("Exception in NotificationMessageDAOImpl.retrieveCollectionLab: = " + e.toString(),e);
		}
		
		
		return caseDataDTCollectionLabsQuestionOrderNND;	
	}
	/**
	 * retrieveDataInFlatTableForLabNotification: call store procedures to fill the lab flat data in the LAB_EVENT and LAB_EVENT_HIST tables
	 * @param publicHealthCaseUid
	 */
	
		public void retrieveDataInFlatTableForLabNotification(Long publicHealthCaseUid){

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
	        	 logger.debug("About to call stored procedure: addLabEventData_sp");
	             String sQuery  = "{call addLabEventData_sp(?)}"; 
	             logger.info("sQuery = " + sQuery);
	             sProc = dbConnection.prepareCall(sQuery);
	             logger.debug("After prepareCall");
	 	         sProc.setLong(1, new Long(publicHealthCaseUid));
	             logger.debug("Before executing the query");
	             sProc.execute();
	             logger.debug("After executing the query");
	         }
	         catch(NEDSSSystemException nse)
	         {
	         	logger.fatal("Exception in NotificationMessageDAOImpl.retrieveDataInFlatTableForLabNotification: NEDSSSystemException  = "+nse.getMessage(), nse);
	             throw new NEDSSSystemException("Exception in NotificationMessageDAOImpl.retrieveDataInFlatTableForLabNotification: Error: while obtaining database connection.\n" + nse.getMessage(), nse);
	         }
	 		catch(Exception e)
	 		{
	 			logger.fatal("Exception in NotificationMessageDAOImpl.retrieveDataInFlatTableForLabNotification  = "+e.getMessage(), e);
	 		    throw new NEDSSSystemException("Exception in NotificationMessageDAOImpl.retrieveDataInFlatTableForLabNotification: = " + e.toString(),e);
	 		}
	         finally
	         {
	             closeCallableStatement(sProc);
	 			 releaseConnection(dbConnection);
	         }
		
	}
		/**
		 * createFlatTableForLabNotification: call store procedures to fill the flat table with data from labs
		 * @param publicHealthCaseUid
		 */
		
			public void insertDataInFlatTableForLabNotification(Collection<Object> caseDataDTCollectionLab){/*
			if(caseDataDTCollectionLab!= null && !caseDataDTCollectionLab.isEmpty()){
			Iterator it = caseDataDTCollectionLab.iterator();
			if(it.hasNext())
			{	
				LaboratoryDT dt = (LaboratoryDT) it.next();
				
				
				Connection dbConnection = null;
				PreparedStatement pStmt = null;
				int resultCount = 0;
				int i = 1;
				int count = 0;

				try {
					dbConnection = getConnection();
				} catch (NEDSSSystemException nsex) {
					logger.fatal("SQLException while obtaining database connection "
							+ "for insertDataInFlatTableForLabNotification", nsex);
					throw new NEDSSSystemException(nsex.getMessage(), nsex);
				}

				int countReturned =0;   
				boolean isExist=false;

				//Insert into History table if record already exists
				countReturned=UniqueFieldLabEventForCreate(dt);
				if(countReturned >= 1) {
					isExist=true;
				}
				
				
			   	if(countReturned >= 1) {
			   	 isExist=true;
			   	}

				if( isExist!=true){
					try {
						String sqlQuery ="";
						
						if (NEDSSConstants.ORACLE_ID.equalsIgnoreCase(propertyUtil.getDatabaseServerType())){
							sqlQuery = WumSqlQuery.CREATE_TRIGGER_CODE_ORA;
						}else
							sqlQuery = WumSqlQuery.CREATE_TRIGGER_CODE_SQL;
						
						dbConnection = getConnection();
						pStmt = dbConnection.prepareStatement(sqlQuery);
						
						String codingSystemCd = dt.getCodingSystem();
						
						String codingSystemDesc = "";
						if(codingSystemCd.equalsIgnoreCase("ICD_10_CM"))
							codingSystemDesc="ICD-10-CM Diagnosis";
						else
							if(codingSystemCd.equalsIgnoreCase("SNOMED_CT_HL7"))
							codingSystemDesc="SNOMED-CT";
						
						
						String codeSystemCd = getCodeSystemCdFromCodeValueGeneral(dt, codingSystemCd);
						String code = dt.getCodeColumn();
						String codeDescriptionTxt = dt.getDisplayName();
						String codeSystemVersionId = dt.getCodeSystemVersionId();
						String conditionCd = dt.getConditionCd();
						if(conditionCd==null || conditionCd.isEmpty())
							conditionCd=null;
						String diseaseNm = dt.getDiseaseNm();
						
						if(diseaseNm==null || diseaseNm.isEmpty())
							diseaseNm=null;
						
						Integer nbsUid = getMaxNbsUid(dt)+1;
						
						pStmt.setString(i++, codeSystemCd);
						pStmt.setString(i++, codingSystemDesc);
						pStmt.setString(i++, code);
						pStmt.setString(i++, codeDescriptionTxt);
						pStmt.setString(i++, codeSystemVersionId);
						pStmt.setString(i++, conditionCd);
						pStmt.setString(i++, diseaseNm);
						pStmt.setInt(i++, nbsUid);
						
						resultCount = pStmt.executeUpdate();

						if (resultCount != 1)
						{
							throw new NEDSSSystemException("Error: none or more than one entity inserted at a time, resultCount = " +
									resultCount);
						}
					}

					catch (SQLException se)
					{
						logger.fatal("Error: SQLException while updating code_to_condition table "+se.getMessage(),se);
						throw new NEDSSDAOSysException("Error: SQLException while updating code_to_condition table\n" +
								se.getMessage(), se);
					}
					catch (Exception se)
					{
						logger.fatal("Error: Exception while updating code_to_condition table "+se.getMessage(),se);
						throw new NEDSSDAOSysException("Error: Exception while updating code_to_condition table\n" +
								se.getMessage(), se);
					}
					finally
					{
						closeStatement(pStmt);
						releaseConnection(dbConnection);
					}
				}
				else {
					//TO DO: code to insert into lab_event_hist history table
				}
				}
				}
		*/}
			
			public int UniqueFieldLabEventForCreate(LaboratoryDT dt)throws NEDSSDAOSysException, NEDSSSystemException
			{
				int count = 0;
				/*Integer intCount = null;
				String codeSql = "";
				ArrayList<Object>  paramList = new ArrayList<Object> ();

				//Throw exception if duplicate System Name is entered 
				codeSql = "select count(*) from  NBS_SRTE..code_to_condition where code_system_cd =? and code = ?";
				if (propertyUtil.getDatabaseServerType() != null && propertyUtil.getDatabaseServerType().equalsIgnoreCase(NEDSSConstants.ORACLE_ID))
					codeSql = "select count(*) from  NBS_SRTE.code_to_condition where code_system_cd =? and code = ?";
				try {			
					paramList.add(dt.getCodingSystem());
					paramList.add(dt.getCodeColumn());
					intCount =  (Integer) preparedStmtMethod(dt, paramList, codeSql, NEDSSConstants.SELECT_COUNT);
					count =intCount.intValue();
					//If a duplicate System Name is found, throw an exception 
					if(intCount != null && intCount.intValue() >= 1) {
						logger.error("Import and Export Administration - Edit ManageSystems will not permit to add duplicate combination of SystemOid and SystemOwnerOid");
						//throw new NEDSSSystemException("SQLException PK Violated - Duplicate Code " + dt.getReceivingSystemOid() + " entered for codeset: " );
					}		
				} catch (Exception ex) {
					logger.fatal("Exception while getting count of combination of 'SystemOid' and 'SystemOwnerOid': ERROR = " + ex.getMessage(), ex);
					throw new NEDSSSystemException(ex.toString(), ex);
				} finally {
					paramList = new ArrayList<Object> ();
				}
				 */
				return count;
			}
		/**
		 * retrieveVaccionationViewData(): method for retrieving the laboratory data from the LAB_EVENT flat table
		 * @param publicHealthCaseUid
		 * @return
		 */
		
		public Collection<Object> retrieveLabViewData(Long publicHealthCaseUid){

			LaboratoryDT labDT  = new LaboratoryDT();
			ArrayList<Object> labDTCollection  = new ArrayList<Object> ();

			
			return labDTCollection;
		}
		
	/**
	 * retrieveVaccionationViewData(): method for retrieving the vaccination data from the v_vaccination view
	 * @param publicHealthCaseUid
	 * @return
	 */
	
	public Collection<Object> retrieveVaccionationViewData(Long publicHealthCaseUid){
		
		String sql;
        sql = SELECT_V_VACCIONATION_DATA_SQL;

		
		logger.debug("SELECT_V_VACCINATION="+sql);

		VaccinationDT vaccinationDT  = new VaccinationDT();
		ArrayList<Object> vaccinationDTCollection  = new ArrayList<Object> ();
		vaccinationDTCollection.add(publicHealthCaseUid);

		try{
			vaccinationDTCollection  = (ArrayList<Object> )preparedStmtMethod(vaccinationDT, vaccinationDTCollection, sql, NEDSSConstants.SELECT);
		}
		catch (Exception ex) {
			String exString = "Exception in NotificationMessageDAOImpl.retrieveVaccionationViewData:  ERROR = " + ex.getMessage();
			logger.fatal("Exception  = "+exString, ex);
			throw new NEDSSSystemException(exString);
		}
		
		return vaccinationDTCollection;
	}

	/**
	 * gets the CaseNotificationDataDT Collection<Object>  Object for a given publicHealthCaseUID
	 * @return Collection<Object>  of CaseNotificationDataDT
	 */
	@SuppressWarnings("unchecked")
	public Collection<Object>  getDiseaseSpecificCaseNotificationDataDTCollection(Long publicHealthCaseUID, NotificationDT notificationDT) throws NEDSSSystemException{
		String sql = "";
       sql = SELECT_DISEASE_SPECIFIC_CASE_REPORT_EXPORT_DATA_COLLECTION_SQL;

		logger.debug("SELECT_DISEASE_SPECIFIC_CASE_REPORT_EXPORT_DATA_COLLECTION="+sql);

		CaseNotificationDataDT caseNotificationDataDT  = new CaseNotificationDataDT();
		ArrayList<Object> caseNotificationDataDTCollection  = new ArrayList<Object> ();
		caseNotificationDataDTCollection.add(publicHealthCaseUID);
		caseNotificationDataDTCollection.add(notificationDT.getCaseConditionCd());
		
		try{
			caseNotificationDataDTCollection  = (ArrayList<Object> )preparedStmtMethod(caseNotificationDataDT, caseNotificationDataDTCollection, sql, NEDSSConstants.SELECT);
		}
		catch (Exception ex) {
			String exString = "Exception in NotificationMessageDAOImpl.getCaseNotificationDataDTCollection:  ERROR = " + ex.getMessage();
			logger.fatal("Exception  = "+exString, ex);
			throw new NEDSSSystemException(exString);
		}
		
		return caseNotificationDataDTCollection;
	}

	
	/**
	 * gets the SummaryCaseRepeatingDataDT Collection<Object>  Object for a given publicHealthCaseUID returning repeating Case Summary data from NBS_table_metadata,
	 * NBS_case_answer, NBS_Question, NND_metadata, NBS_aggregate, NBS_indicator
	 * @return Collection<Object>  of SummaryCaseRepeatingDataDT
	 */
	@SuppressWarnings("unchecked")
	public Collection<Object>  getSummaryCaseRepeatingData(Long publicHealthCaseUID) throws NEDSSSystemException{
		String sql = SELECT_SUMMARY_CASE_REPEATING_DATA_COLLECTION;
		
		logger.debug("SELECT_SUMMARY_CASE_AGGREGATE_DATA_COLLECTION="+sql);

		SummaryCaseRepeatingDataDT summaryCaseRepeatingDataDT  = new SummaryCaseRepeatingDataDT();
		ArrayList<Object> summaryCaseRepeatingDataDTCollection  = new ArrayList<Object> ();
		summaryCaseRepeatingDataDTCollection.add(publicHealthCaseUID);
		
		try{
			summaryCaseRepeatingDataDTCollection  = (ArrayList<Object> )preparedStmtMethod(summaryCaseRepeatingDataDT, summaryCaseRepeatingDataDTCollection, sql, NEDSSConstants.SELECT);
		}
		catch (Exception ex) {
			String exString = "Exception in NotificationMessageDAOImpl.getSummaryCaseRepeatingData:  ERROR = " + ex.getMessage();
			logger.fatal("Exception  = "+exString, ex);
			throw new NEDSSSystemException(exString);
		}
		
		return summaryCaseRepeatingDataDTCollection;
	}
	
	/**
	 * gets TreeMap<Object, Object> of Participations associated with a given publicHealthCaseUID
	 * @return TreeMap<Object, Object> of part_type / subject_entity_uid 
	 */
	@SuppressWarnings("unchecked")
	public TreeMap<Object, Object> getPublicHealthCaseEntityParticipations(Long publicHealthCaseUID) throws NEDSSSystemException{
		logger.debug("SELECT_NOTIFICATION_PARTICIPATION_COLLECTION="+SELECT_NOTIFICATION_PARTICIPATION_COLLECTION);
		TreeMap<Object, Object> phcEntityParticipationsMap = new TreeMap<Object, Object>();
		NotificationParticipationDT nndParticipationDT  = new NotificationParticipationDT();
		ArrayList<Object> nndParticipationDTCollection  = new ArrayList<Object> ();
		nndParticipationDTCollection.add(publicHealthCaseUID);
		
		try{
			nndParticipationDTCollection  = (ArrayList<Object> )preparedStmtMethod(nndParticipationDT, nndParticipationDTCollection, SELECT_NOTIFICATION_PARTICIPATION_COLLECTION, NEDSSConstants.SELECT);
		}
		catch (Exception ex) {
			String exString = "Exception in NotificationMessageDAOImpl.getPublicHealthCaseEntityParticipations:  ERROR = " + ex.getMessage();
			logger.fatal("Exception  = "+exString, ex);
			throw new NEDSSSystemException(exString);
		}
		
		for (int count = 0; count < nndParticipationDTCollection.size(); count++) {
			NotificationParticipationDT dt = (NotificationParticipationDT) nndParticipationDTCollection.get(count);
			phcEntityParticipationsMap.put(dt.getTypeCd(), dt.getSubjectEntityUID());
		}

		return phcEntityParticipationsMap;
	}

	/**
	 * gets Collection<Object> of Participations associated with a given PHCR's publicHealthCaseUID
	 * @return Collection<Object>  of CaseNotificationDataDT
	 */
	@SuppressWarnings("unchecked")
	public Collection<Object> getPHCREntityParticipations(Long publicHealthCaseUID) throws NEDSSSystemException{
		logger.debug("SELECT_PHCR_PARTICIPATION_COLLECTION="+SELECT_PHCR_PARTICIPATION_COLLECTION);
		NotificationParticipationDT nndParticipationDT  = new NotificationParticipationDT();
		ArrayList<Object> nndParticipationDTCollection  = new ArrayList<Object> ();
		nndParticipationDTCollection.add(publicHealthCaseUID);
		
		try{
			nndParticipationDTCollection  = (ArrayList<Object> )preparedStmtMethod(nndParticipationDT, nndParticipationDTCollection, SELECT_PHCR_PARTICIPATION_COLLECTION, NEDSSConstants.SELECT);
		}
		catch (Exception ex) {
			String exString = "Exception in NotificationMessageDAOImpl.getPHCREntityParticipations:  ERROR = " + ex.getMessage();
			logger.fatal("Exception  = "+exString, ex);
			throw new NEDSSSystemException(exString);
		}

		return nndParticipationDTCollection;
	}
	
	
	public void getCaseObservationLegacyData(Long publicHealthCaseUid, CaseNotificationDataDT caseNotificationDataDT, String legacyTableName, String legacyColumnName) {
		String queryString = "SELECT "+ legacyTableName + "." + legacyColumnName + " AS \"answerTxt\" FROM Act_relationship INNER JOIN Public_health_case ON " +
		"Act_relationship.target_act_uid = Public_health_case.public_health_case_uid INNER JOIN " + legacyTableName + 
		" INNER JOIN Observation ON " + legacyTableName + ".observation_uid = Observation.observation_uid INNER JOIN " +
		"Observation Observation_1 INNER JOIN Act_relationship Act_relationship_1 ON " +
		"Observation_1.observation_uid = Act_relationship_1.target_act_uid ON Observation.observation_uid = Act_relationship_1.source_act_uid "+
		"ON Act_relationship.source_act_uid = Observation_1.observation_uid " +
		"WHERE (Public_health_case.public_health_case_uid = " + publicHealthCaseUid + ") AND (Observation.cd = '" + caseNotificationDataDT.getQuestionIdentifier() + "')";
		
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		try{
		    dbConnection = getConnection();
		
		    preparedStmt = dbConnection.prepareStatement(queryString);
		    resultSet = preparedStmt.executeQuery();

		    //Retrieve ResultSet and retrieve column (returned as 'answerTxt') and set to string for populating
		    //CaseNotificationDataDT.answerTxt
			if(resultSet!=null && resultSet.next()){
				caseNotificationDataDT.setAnswerTxt(resultSet.getObject(1).toString().trim());
			}
		    
			return;
		}
		catch(Exception ex){
			String exString = "Exception in NotificationMessageDAOImpl.getCaseObservationLegacyData:  ERROR = " + ex.getMessage();
			logger.fatal("Exception  = "+exString, ex);
			throw new NEDSSSystemException(exString);
		}
		finally{
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
		
	}
	
	public Collection<Object>  getUpdatedNonAnswerCaseNotificationDataDTs(String queryString, HashMap<?,?> groupColumnsMap, String tableName, int nTypeObs)
	{
		logger.debug("getUpdatedNonAnswerCaseNotificationDataDTs SQL="+queryString);
		
 		Collection<Object>  updatedCaseNotificationDataDTCollection  = new ArrayList<Object> ();
 		
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		try{
		    //dbConnection = getConnection();
			 try
	         {
	             dbConnection = getConnection(NEDSSConstants.ODS);
	         }
	         catch(NEDSSSystemException nsex)
	         {
	             logger.fatal("Error obtaining dbConnection "+nsex.getMessage(), nsex);
	             throw new NEDSSSystemException(nsex.toString(), nsex);
	         } 
			 preparedStmt = dbConnection.prepareStatement(queryString);
			 logger.debug("Before executing the query");
			 resultSet = preparedStmt.executeQuery();
			 logger.debug("After executing the query");
		    
		    //Iterate through ResultSet and set retrieved values into corresponding CaseNotificationDataDT
			if(resultSet!=null && resultSet.next()){
				ResultSetMetaData metaData = resultSet.getMetaData();
				
				//If we are processing the Person_race table, we must handle the possibility of multiple rows
				//as well as the fact that the values that are of P_RACE_CAT will get stored in both the 
				//race_cd and the race_category_cd columns and race categories are only valid for the P_RACE_CAT 
				//coded questions (race_cd are valid for the P_RACE coded questions).
				if (tableName.equalsIgnoreCase(DataTables.PERSON_RACE_TABLE))
				{
					logger.info("tableName: "+tableName);
			  		HashSet<Object> processedRaceCategories = new HashSet<Object>();

			  		//Loop through result set and build CaseNotificationDataDT's for each unique race_category_cd and for each race_cd
					CaseNotificationDataDT raceCategoryDT=null;
					CaseNotificationDataDT raceCdDT=null;

					do {
						//Retrieve the current row's race_cd and race_category_cd
						for (int i = 1; i <= groupColumnsMap.size(); i++) {
							String queryColumnValue = resultSet.getObject(i) == null ? "" : resultSet.getObject(i).toString().trim();
							String queryColumnName = metaData.getColumnName(i).toUpperCase(); //to be used as key, force to upper

							if (groupColumnsMap.containsKey(queryColumnName) && queryColumnName.equalsIgnoreCase(NNDConstantUtil.PERSON_RACE_TABLE_RACE_CATEGORY_CD_COLUMN)){
								Collection<?>  columnDTCollection  = (Collection<?>)groupColumnsMap.get(queryColumnName);
								Iterator<?> it  = columnDTCollection.iterator();
								while (it.hasNext()) {
									raceCategoryDT = createNewCaseNotificationDataDT((CaseNotificationDataDT)it.next());
									raceCategoryDT.setAnswerTxt(queryColumnValue);
									if (!processedRaceCategories.contains(queryColumnValue)){
										processedRaceCategories.add(queryColumnValue);
										updatedCaseNotificationDataDTCollection.add(raceCategoryDT);
									}
								}
							} else if (groupColumnsMap.containsKey(queryColumnName) && queryColumnName.equalsIgnoreCase(NNDConstantUtil.PERSON_RACE_TABLE_RACE_CD_COLUMN)){
								Collection<?>  columnDTCollection  = (Collection<?>)groupColumnsMap.get(queryColumnName);
								Iterator<?> it  = columnDTCollection.iterator();
								while (it.hasNext()) {
									raceCdDT = createNewCaseNotificationDataDT((CaseNotificationDataDT)it.next());
									raceCdDT.setAnswerTxt(queryColumnValue);
								}
							}
						}
						
						//If race_cd != race_category_cd then this is a valid race_cd to store 
						if (raceCdDT != null && raceCategoryDT != null && !raceCdDT.getAnswerTxt().equals(raceCategoryDT.getAnswerTxt())){
							updatedCaseNotificationDataDTCollection.add(raceCdDT);
						}
					} while (resultSet.next());
				}
				//This handles repeating non-answer data - confirmation only right now
				//TODO:  Find/Create repeat attribute and check here and call this for any attribute listed as repeating
				else if (tableName.equalsIgnoreCase(DataTables.CONFIRMATION_METHOD_TABLE)){
					do {
						logger.info("tableName: "+tableName);
						for (int i = 1; i <= groupColumnsMap.size(); i++) {
							String queryColumnValue = resultSet.getObject(i) == null ? "" : resultSet.getObject(i).toString().trim();
							String queryColumnName = metaData.getColumnName(i).toUpperCase(); //to be used as key, force to upper
	
							if (groupColumnsMap.containsKey(queryColumnName)) {
								Collection<?>  columnDTCollection  = (Collection<?>)groupColumnsMap.get(queryColumnName);
								Iterator<?> it  = columnDTCollection.iterator();
								while (it.hasNext()) {
									CaseNotificationDataDT caseNotificationDataDT = createNewCaseNotificationDataDT((CaseNotificationDataDT)it.next());
								
									//Only set if required or if optional with a queryColumnValue
									if (caseNotificationDataDT.getQuestionRequiredNND().equals(NEDSSConstants.NND_REQUIRED_FIELD) || 
											   (caseNotificationDataDT.getQuestionRequiredNND().equals(NEDSSConstants.NND_OPTIONAL_FIELD) && queryColumnValue != null && queryColumnValue.trim().length() > 0) ){
										caseNotificationDataDT.setAnswerTxt(queryColumnValue);
										updatedCaseNotificationDataDTCollection.add(caseNotificationDataDT);
									} else if (caseNotificationDataDT.getLegacyDataLocation() != null && caseNotificationDataDT.getLegacyDataLocation().trim().length() > 0) {
										//Put this back in since it has a legacy data location and we should try grabbing it from there before dropping it 
										updatedCaseNotificationDataDTCollection.add(caseNotificationDataDT);
									}
								}
							}
						}
					} while (resultSet.next());
				}
				else if (tableName.equalsIgnoreCase(DataTables.LAB_EVENT)){
					
					try{
					int index=0;
					do {
						logger.info("tableName: "+tableName);
						Long uid = null;
						Long observationUid = null;
						
						index++;
						int totalToAdd=0;
						
						//This is because of the number of the extra columns that we have at the beginning of the select - couldn't use previous logic because of subquery
						if( nTypeObs == 1)
							totalToAdd=1;
						else
							if(nTypeObs == 2 || nTypeObs == 3 || nTypeObs == 4)
									totalToAdd=2;
							
						
						for (int i = 1; i <= groupColumnsMap.size()+totalToAdd; i++) {
							String queryColumnValue = resultSet.getObject(i) == null ? "" : resultSet.getObject(i).toString().trim();
							String queryColumnName = metaData.getColumnName(i).toUpperCase(); //to be used as key, force to upper
							logger.info("queryColumnValue: "+queryColumnValue+" queryColumnName: "+queryColumnName);
							//uid
							if((queryColumnName.equalsIgnoreCase("observation_uid") &&  nTypeObs == 1) || (queryColumnName.equalsIgnoreCase("result_uid") &&  nTypeObs == 2)  || (queryColumnName.equalsIgnoreCase("susceptibility_uid") &&  nTypeObs == 3) || (queryColumnName.equalsIgnoreCase("susceptibility_uid") &&  nTypeObs == 4)){
								observationUid = Long.parseLong(queryColumnValue);
							}
							
							if((queryColumnName.equalsIgnoreCase("result_uid") &&  nTypeObs == 4) || (queryColumnName.equalsIgnoreCase("result_uid") &&  nTypeObs == 3) || (queryColumnName.equalsIgnoreCase("observation_uid") &&  nTypeObs == 2)){
									uid = Long.parseLong(queryColumnValue);
							}
							
							logger.info("observationUid: "+observationUid+" uid: "+uid);
							logger.info("observationSubID: "+index);
							
							if (groupColumnsMap.containsKey(queryColumnName)) {
								Collection<?>  columnDTCollection  = (Collection<?>)groupColumnsMap.get(queryColumnName);
								Iterator<?> it  = columnDTCollection.iterator();
								while (it.hasNext()) {
									CaseNotificationDataDT caseNotificationDataDT = createNewCaseNotificationDataDT((CaseNotificationDataDT)it.next());
									caseNotificationDataDT.setUid(uid);//parent
									caseNotificationDataDT.setObservationUid(observationUid);//lab uid, result uid, susceptibility uid
									
									caseNotificationDataDT.setObservationSubID(index);
									//Only set if required or if optional with a queryColumnValue
									if (caseNotificationDataDT.getQuestionRequiredNND().equals(NEDSSConstants.NND_REQUIRED_FIELD) || 
											   (caseNotificationDataDT.getQuestionRequiredNND().equals(NEDSSConstants.NND_OPTIONAL_FIELD) && queryColumnValue != null && queryColumnValue.trim().length() > 0) ){
										caseNotificationDataDT.setAnswerTxt(queryColumnValue);
										updatedCaseNotificationDataDTCollection.add(caseNotificationDataDT);
									} else if (caseNotificationDataDT.getLegacyDataLocation() != null && caseNotificationDataDT.getLegacyDataLocation().trim().length() > 0) {
										//Put this back in since it has a legacy data location and we should try grabbing it from there before dropping it 
										updatedCaseNotificationDataDTCollection.add(caseNotificationDataDT);
									}
								}
							}
						}
					} while (resultSet.next());
					
					}catch(NEDSSSystemException e)
			         {
						String errMessage = "NotificationMessageDAOImpl.getUpdatedNonAnswerCaseNotificationDataDTs: tbaleName = :LAB_EVENT. Exception: "+e.getMessage();
			            logger.fatal(errMessage, e);
			            throw new NEDSSSystemException(errMessage, e);
			         } 
				}
				else { //For all other tables:
					for (int i = 1; i <= groupColumnsMap.size(); i++) {
						logger.info("Other tables");
						String queryColumnValue = resultSet.getObject(i) == null ? "" : resultSet.getObject(i).toString().trim();
						String queryColumnName = metaData.getColumnName(i).toUpperCase();  //to be used as key, force to upper
						 
						//Get and update CaseNotificationDataDT that corresponds to this column
						if(groupColumnsMap.containsKey(queryColumnName)){
							Collection<?>  columnDTCollection  = (Collection<?>)groupColumnsMap.get(queryColumnName);
							Iterator<?> it  = columnDTCollection.iterator();
							while (it.hasNext()) {
								CaseNotificationDataDT caseNotificationDataDT = (CaseNotificationDataDT)it.next();
								
								//Only set if required or if optional with a queryColumnValue
								if (caseNotificationDataDT.getQuestionRequiredNND().equals(NEDSSConstants.NND_REQUIRED_FIELD) || 
								   (caseNotificationDataDT.getQuestionRequiredNND().equals(NEDSSConstants.NND_OPTIONAL_FIELD) && queryColumnValue != null && queryColumnValue.trim().length() > 0) ){
									caseNotificationDataDT.setAnswerTxt(queryColumnValue);
									updatedCaseNotificationDataDTCollection.add(caseNotificationDataDT);
								} else if (caseNotificationDataDT.getLegacyDataLocation() != null && caseNotificationDataDT.getLegacyDataLocation().trim().length() > 0) {
									//Put this back in since it has a legacy data location and we should try grabbing it from there before dropping it 
									updatedCaseNotificationDataDTCollection.add(caseNotificationDataDT);
								}
							}
						}
					}
				}
			} else { // No result set - need to put required caseNotificationDataDT's and ones that have legacy_data_locations set back into the collection.
				TreeSet<Object> keys = new TreeSet<Object>(groupColumnsMap.keySet());
			   Iterator<Object>  keyIter = keys.iterator();
			    while (keyIter.hasNext()) {
					Collection<?>  columnDTCollection  = (Collection<?>)groupColumnsMap.get((String)(keyIter.next()));
					Iterator<?> it  = columnDTCollection.iterator();
					while (it.hasNext()) {
						CaseNotificationDataDT caseNotificationDataDT = (CaseNotificationDataDT)it.next();
						// If required, or has legacy_data_location populated, put it back in collection
						if ( caseNotificationDataDT.getQuestionRequiredNND().equals(NEDSSConstants.NND_REQUIRED_FIELD) ||
							(caseNotificationDataDT.getLegacyDataLocation() != null && caseNotificationDataDT.getLegacyDataLocation().trim().length() > 0) ) {
							updatedCaseNotificationDataDTCollection.add(caseNotificationDataDT);
						}
					}
				}
			}
		}
		catch(Exception ex){
			String exString = "Exception in NotificationMessageDAOImpl.getUpdatedNonAnswerCaseNotificationDataDTs:  ERROR = " + ex.getMessage();
			logger.fatal("Exception  = "+exString, ex);
			throw new NEDSSSystemException(exString, ex);
		}
		finally{
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
		
		return updatedCaseNotificationDataDTCollection;
	}
	
	private static CaseNotificationDataDT createNewCaseNotificationDataDT(CaseNotificationDataDT existingCaseNotificationDataDT) {
		//Copies existing CaseNotificationDataDT minus the answer values
		CaseNotificationDataDT CaseNotificationDataDT = new CaseNotificationDataDT();
		try{
			CaseNotificationDataDT.setQuestionIdentifierNND(existingCaseNotificationDataDT.getQuestionIdentifierNND());
			CaseNotificationDataDT.setQuestionLabelNND(existingCaseNotificationDataDT.getQuestionLabelNND());
			CaseNotificationDataDT.setQuestionOID(existingCaseNotificationDataDT.getQuestionOID());
			CaseNotificationDataDT.setQuestionOIDSystemTxt(existingCaseNotificationDataDT.getQuestionOIDSystemTxt());
			CaseNotificationDataDT.setQuestionIdentifier(existingCaseNotificationDataDT.getQuestionIdentifier());
			CaseNotificationDataDT.setUiMetadataQuestionLabel(existingCaseNotificationDataDT.getUiMetadataQuestionLabel());
			CaseNotificationDataDT.setNbsQuestionLabel(existingCaseNotificationDataDT.getNbsQuestionLabel());
			CaseNotificationDataDT.setHl7SegmentField(existingCaseNotificationDataDT.getHl7SegmentField());
			CaseNotificationDataDT.setOrderGroupId(existingCaseNotificationDataDT.getOrderGroupId());
			CaseNotificationDataDT.setQuestionRequiredNND(existingCaseNotificationDataDT.getQuestionRequiredNND());
			CaseNotificationDataDT.setQuestionDataTypeNND(existingCaseNotificationDataDT.getQuestionDataTypeNND());
			CaseNotificationDataDT.setTranslationTableNm(existingCaseNotificationDataDT.getTranslationTableNm());
			CaseNotificationDataDT.setCodesetGroupId(existingCaseNotificationDataDT.getCodesetGroupId());
			CaseNotificationDataDT.setCodeSetNm(existingCaseNotificationDataDT.getCodeSetNm());
			CaseNotificationDataDT.setClassCd(existingCaseNotificationDataDT.getClassCd());
			CaseNotificationDataDT.setDataLocation(existingCaseNotificationDataDT.getDataLocation());	
			CaseNotificationDataDT.setPartTypeCd(existingCaseNotificationDataDT.getPartTypeCd());
			CaseNotificationDataDT.setQuestionGroupSeqNbr(existingCaseNotificationDataDT.getQuestionGroupSeqNbr());
			CaseNotificationDataDT.setQuestionUnitIdentifier(existingCaseNotificationDataDT.getQuestionUnitIdentifier());
			CaseNotificationDataDT.setUnitParentIdentifier(existingCaseNotificationDataDT.getUnitParentIdentifier());
			CaseNotificationDataDT.setLegacyDataLocation(existingCaseNotificationDataDT.getLegacyDataLocation());
			CaseNotificationDataDT.setDataCd(existingCaseNotificationDataDT.getDataCd());
			CaseNotificationDataDT.setDataType(existingCaseNotificationDataDT.getDataType());
			CaseNotificationDataDT.setDataUseCd(existingCaseNotificationDataDT.getDataUseCd());
			CaseNotificationDataDT.setXmlPath(existingCaseNotificationDataDT.getXmlPath());
			CaseNotificationDataDT.setXmlTag(existingCaseNotificationDataDT.getXmlTag());
			CaseNotificationDataDT.setXmlDataType(existingCaseNotificationDataDT.getXmlDataType());
			CaseNotificationDataDT.setQuestionOrderNND(existingCaseNotificationDataDT.getQuestionOrderNND());
			CaseNotificationDataDT.setUid(existingCaseNotificationDataDT.getUid());
			CaseNotificationDataDT.setObservationUid(existingCaseNotificationDataDT.getObservationUid());
			
			
			
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return CaseNotificationDataDT;
	}
	
	/**
	 * getNumberLabsAssociatedToInvestigation: this method returns the number of labs associated to an investigation
	 * @param publicHealthCaseUid
	 * @return
	 */
	
	public int getNumberLabsAssociatedToInvestigation(Long publicHealthCaseUid){
		
		int numberLabs = 0;
		if(publicHealthCaseUid==null)
			return numberLabs;
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;

		String queryString = "select count(*) from observation with (nolock) "
				+ "inner join act_relationship with (nolock) on observation.observation_uid = act_relationship.source_act_uid "
				+ "inner join Public_health_case with (nolock) on Public_health_case.public_health_case_uid = act_relationship.target_act_uid "
				+ " where Public_health_case.public_health_case_uid=? and act_relationship.type_cd = '"
				+ NEDSSConstants.LAB_REPORT + "'";
		try{
			logger.debug("Getting Connection");
		    dbConnection = getConnection();
		    logger.debug("Query to execute: "+queryString);
		    preparedStmt = dbConnection.prepareStatement(queryString);
		    logger.debug("Parameter: "+publicHealthCaseUid.longValue());
		    preparedStmt.setLong(1, publicHealthCaseUid.longValue());
		    logger.debug("Before executing the query");
		    resultSet = preparedStmt.executeQuery();
		    logger.debug("After executing the query");
            if (resultSet.next())
            	numberLabs = resultSet.getInt(1);
		}catch(Exception ex){
			logger.fatal("NotificationMessageDAOImpl.getNumberLabsAssociatedToInvestigation. Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString(), ex);
		}
		finally{
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
		
		return numberLabs;
	}
	
	
	
	/**
	 * getNumberVacAssociatedToInvestigation: this method returns the number of labs associated to an investigation
	 * @param publicHealthCaseUid
	 * @return
	 */
	
	public int getNumberVacAssociatedToInvestigation(Long publicHealthCaseUid){
		
		int numberVacs = 0;
		if(publicHealthCaseUid==null)
			return numberVacs;
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		
		String queryString = "select count(*) from Intervention with (nolock) "
				+ "inner join act_relationship with (nolock) on Intervention.intervention_uid = act_relationship.source_act_uid "
				+ "inner join Public_health_case with (nolock) on Public_health_case.public_health_case_uid = act_relationship.target_act_uid "
				+ "where Public_health_case.public_health_case_uid=? and act_relationship.type_cd = '"
				+ NEDSSConstants.AR_TYPE_CODE + "'";
		try{
		    dbConnection = getConnection();
		    preparedStmt = dbConnection.prepareStatement(queryString);
		    preparedStmt.setLong(1, publicHealthCaseUid.longValue());
		    resultSet = preparedStmt.executeQuery();
            if (resultSet.next())
            	numberVacs = resultSet.getInt(1);
		}catch(Exception ex){
			logger.fatal("NotificationMessageDAOImpl.getNumberVacAssociatedToInvestigation. Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		finally{
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
		
		return numberVacs;
	}

}
