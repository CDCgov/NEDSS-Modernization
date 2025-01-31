package gov.cdc.nedss.localfields.ejb.dao;


import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.localfields.dt.NndMetadataDT;
import gov.cdc.nedss.systemservice.uidgenerator.UidClassCodes;
import gov.cdc.nedss.systemservice.uidgenerator.UidGeneratorHelper;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * NNDMetadataDAOImpl used to Load, Store or Delete NND Metadata
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Computer Sciences Corporation</p>
 * NNDMetadataDAOImpl.java
 * @version
 */
public class NNDMetadataDAOImpl extends DAOBase {

	private static final LogUtils logger = new LogUtils(NNDMetadataDAOImpl.class.getName());

	private static final String SELECT_NND_METADATA = "SELECT "
		                                            + "nnd_metadata_uid \"nndMetadataUid\", "
		                                            + "nbs_page_uid \"nbsPageUid\", "
		                                            + "investigation_form_cd \"investigationFormCd\", "
		                                            + "question_identifier_nnd \"questionIdentifierNnd\", "
		                                            + "question_label_nnd \"questionLabelNnd\", "
		                                            + "question_required_nnd \"questionRequiredNnd\", "
		                                            + "question_data_type_nnd \"questionDataTypeNnd\", "
		                                            + "HL7_segment_field \"HL7SegmentField\", "
		                                            + "order_group_id \"orderGroupId\", "
		                                            + "translation_table_nm \"translationTableNm\", "
		                                            + "add_time \"addTime\", "
		                                            + "add_user_id \"addUserId\", "
		                                            + "last_chg_time \"lastChgTime\", "
		                                            + "last_chg_user_id \"lastChgUserId\", "
		                                            + "record_status_cd \"recordStatusCd\", "
		                                            + "record_status_time \"recordStatusTime\", "
		                                            + "question_identifier \"questionIdentifier\", "
		                                            + "msg_trigger_ind_cd \"msgTriggerIndCd\", "
		                                            + "xml_path \"xmlPath\", "
		                                            + "xml_tag \"xmlTag\", "
		                                            + "xml_data_type \"xmlDataType\", "
		                                            + "part_type_cd \"partTypeCd\", "
		                                            + "repeat_group_seq_nbr \"repeatGroupSeqNbr\", "
		                                            + "question_order_nnd \"questionOrderNnd\", "
		                                            + "FROM nnd_metadata ";

	private static final String WHERE_NND_METADATA_UID = "WHERE nnd_metadata_uid=? ";


	private static final String CREATE_NND_METADATA = "INSERT INTO NND_Metadata "
													+ "(nnd_metadata_uid, "
												    + "nbs_page_uid, "
												    + "investigation_form_cd, "
												    + "question_identifier_nnd, "
												    + "question_label_nnd, "
												    + "question_required_nnd, "
												    + "question_data_type_nnd, "
												    + "HL7_segment_field, "
												    + "order_group_id, "
												    + "translation_table_nm, "
												    + "add_time, "
												    + "add_user_id, "
												    + "last_chg_time, "
												    + "last_chg_user_id, "
												    + "record_status_cd, "
												    + "record_status_time, "
												    + "question_identifier, "
												    + "msg_trigger_ind_cd, "
												    + "xml_path, "
												    + "xml_tag, "
												    + "xml_data_type, "
												    + "part_type_cd, "
												    + "repeat_group_seq_nbr, "
												    + "question_order_nnd, "
												    + "nbs_ui_metadata_uid, "
												    + "question_map, "
												    + "indicator_cd) "
												    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	private static final String UPDATE_NND_METADATA = "UPDATE nnd_metadata SET "
	                                                + "investigation_form_cd=?, "
	                                                + "question_identifier_nnd=?, "
	                                                + "question_label_nnd=?, "
	                                                + "question_required_nnd=?, "
	                                                + "question_data_type_nnd=?, "
	                                                + "HL7_segment_field=?, "
	                                                + "order_group_id=?, "
	                                                + "translation_table_nm=?, "
	                                                + "add_time=?, "
	                                                + "add_user_id=?, "
	                                                + "last_chg_time=?, "
	                                                + "last_chg_user_id=?, "
	                                                + "record_status_cd=?, "
	                                                + "record_status_time=?, "
	                                                + "question_identifier=?, "
	                                                + "msg_trigger_ind_cd=?, "
	                                                + "xml_path=?, "
	                                                + "xml_tag=?, "
	                                                + "xml_data_type=?, "
	                                                + "part_type_cd=?, "
	                                                + "repeat_group_seq_nbr=?, "
	                                                + "question_order_nnd=?, "
													+ "nbs_page_uid=?," 
													+ "nbs_ui_metadata_uid=? ";


	private final String INSERT_NND_METADATA_HISTORY = "INSERT INTO NND_metadata_hist(nnd_metadata_uid, investigation_form_cd, question_identifier_nnd, question_label_nnd, question_required_nnd, question_data_type_nnd, HL7_segment_field, order_group_id, translation_table_nm, add_time, add_user_id, last_chg_time, last_chg_user_id, record_status_cd, record_status_time, question_identifier, msg_trigger_ind_cd, xml_path, xml_tag, xml_data_type, part_type_cd, repeat_group_seq_nbr, question_order_nnd, nbs_page_uid, nbs_ui_metadata_uid, version_ctrl_nbr, )"
		                                             + " SELECT nnd_metadata_uid, investigation_form_cd, question_identifier_nnd, question_label_nnd, question_required_nnd, question_data_type_nnd, HL7_segment_field, order_group_id, translation_table_nm, add_time, add_user_id, last_chg_time, last_chg_user_id, record_status_cd, record_status_time, question_identifier, msg_trigger_ind_cd, xml_path, xml_tag, xml_data_type, part_type_cd, repeat_group_seq_nbr, question_order_nnd, nbs_page_uid, nbs_ui_metadata_uid, ? from NND_metadata"
		                                             + " where nnd_metadata_uid = ? ";

	private final String INSERT_NND_METADATA_HISTORY_BY_NBS_PAGE_UID = "INSERT INTO NND_metadata_hist(nnd_metadata_uid, investigation_form_cd, question_identifier_nnd, question_label_nnd, question_required_nnd, question_data_type_nnd, HL7_segment_field, order_group_id, translation_table_nm, add_time, add_user_id, last_chg_time, last_chg_user_id, record_status_cd, record_status_time, question_identifier, msg_trigger_ind_cd, xml_path, xml_tag, xml_data_type, part_type_cd, repeat_group_seq_nbr, question_order_nnd, nbs_page_uid, nbs_ui_metadata_uid, question_map, indicator_cd, version_ctrl_nbr)"
        + " SELECT nnd_metadata_uid, investigation_form_cd, question_identifier_nnd, question_label_nnd, question_required_nnd, question_data_type_nnd, HL7_segment_field, order_group_id, translation_table_nm, add_time, add_user_id, last_chg_time, last_chg_user_id, record_status_cd, record_status_time, question_identifier, msg_trigger_ind_cd, xml_path, xml_tag, xml_data_type, part_type_cd, repeat_group_seq_nbr, question_order_nnd, nbs_page_uid, nbs_ui_metadata_uid, question_map, indicator_cd, ? from NND_metadata"
        + " where nbs_page_uid = ? ";
	
	private final String SELECT_MAX_HISTORY_VERSION = "SELECT MAX(version_ctrl_nbr) FROM NND_metadata_hist WHERE (nbs_page_uid = ?)";
	
	private final String DELETE_NND_METADATA_FOR_PAGE = "DELETE FROM nnd_metadata where nbs_page_uid = ?";

	/**
	 * Returns a NNDMetadataDT searching by NNDMetadataUid
	 * @param NNDMetadataUid
	 * @return NNDMetadataDT
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	@SuppressWarnings("unchecked")
	public NndMetadataDT findNNDMetadata(Long nndMetadataUid) throws NEDSSDAOSysException, NEDSSSystemException {

		NndMetadataDT nndMetadataDT = new NndMetadataDT();
		ArrayList<Object> paramList = new ArrayList<Object> ();
		paramList.add(nndMetadataUid);

		try {
			paramList = (ArrayList<Object> ) preparedStmtMethod(nndMetadataDT, paramList, SELECT_NND_METADATA + WHERE_NND_METADATA_UID, NEDSSConstants.SELECT);
			if(paramList.size() > 0)
				return (NndMetadataDT)paramList.get(0);

		} catch (Exception ex) {
			logger.fatal("nndMetadataUid: "+nndMetadataUid+" Exception in findNNDMetadata: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return nndMetadataDT;

	}

	public void deleteByNbsPageUid(Long nbsPageUid) throws NEDSSDAOSysException, NEDSSSystemException {
		ArrayList<Object> paramList = new ArrayList<Object> ();
		paramList.add(nbsPageUid);

		try {
			preparedStmtMethod(null, paramList, DELETE_NND_METADATA_FOR_PAGE, NEDSSConstants.UPDATE);
		} catch (Exception ex) {
			logger.fatal("Exception in findNBSRdbMetadata: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}

		
	}

	public long createNNDMetadata(NndMetadataDT nndMetadataDT) throws NEDSSDAOSysException, NEDSSSystemException {
		Long nndMetadataUid = null;
		
		try {
			//Generate questionUid
			UidGeneratorHelper uidGen = new UidGeneratorHelper();
			nndMetadataUid = uidGen.getNbsIDLong(UidClassCodes.NND_METADATA_CLASS_CODE).longValue();
		} catch (Exception e) {
			logger.error("NNDMetadataDAOImpl.createNNDMetadata - Exception while calling UID Generator for NND_METADATA: " + e.getMessage(), e);
			throw new NEDSSSystemException(e.toString());
		}

		ArrayList<Object> paramList = new ArrayList<Object> ();
		paramList.add(nndMetadataUid);
		paramList.add(nndMetadataDT.getNbsPageUid());
		paramList.add(nndMetadataDT.getInvestigationFormCd());
		paramList.add(nndMetadataDT.getQuestionIdentifierNnd());
		paramList.add(nndMetadataDT.getQuestionLabelNnd());
		paramList.add(nndMetadataDT.getQuestionRequiredNnd());
		paramList.add(nndMetadataDT.getQuestionDataTypeNnd());
		paramList.add(nndMetadataDT.getHL7SegmentField());
		paramList.add(nndMetadataDT.getOrderGroupId());
		paramList.add(nndMetadataDT.getTranslationTableNm());
		paramList.add(nndMetadataDT.getAddTime());
		paramList.add(nndMetadataDT.getAddUserId());
		paramList.add(nndMetadataDT.getLastChgTime());
		paramList.add(nndMetadataDT.getLastChgUserId());
		paramList.add(nndMetadataDT.getRecordStatusCd());
		paramList.add(nndMetadataDT.getRecordStatusTime());
		paramList.add(nndMetadataDT.getQuestionIdentifier());
		paramList.add(nndMetadataDT.getMsgTriggerIndCd());
		paramList.add(nndMetadataDT.getXmlPath());
		paramList.add(nndMetadataDT.getXmlTag());
		paramList.add(nndMetadataDT.getXmlDataType());
		paramList.add(nndMetadataDT.getPartTypeCd());
		paramList.add(nndMetadataDT.getRepeatGroupSeqNbr());
		paramList.add(nndMetadataDT.getQuestionOrderNnd());
		paramList.add(nndMetadataDT.getNbsUiMetadataUid());
		paramList.add(nndMetadataDT.getQuestionMap());
		paramList.add(nndMetadataDT.getIndicatorCd());
		try {
			int resultCount = ((Integer)preparedStmtMethod(null, paramList, CREATE_NND_METADATA, NEDSSConstants.UPDATE)).intValue();
            if (resultCount != 1) {
                logger.error("Exception in createNNDMetadata: , " + "resultCount = " + resultCount);
    			throw new NEDSSSystemException("Exception in createNNDMetadata:");
            }

		} catch (Exception ex) {
			logger.error("Exception in createNNDMetadata: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return nndMetadataUid;
	}

	public void updateNNDMetadata(NndMetadataDT nndMetadataDT) throws NEDSSDAOSysException, NEDSSSystemException {

		//History
		updateNNDMetadataHistory(nndMetadataDT.getNndMetadataUid(), nndMetadataDT.getNbsPageUid());

		ArrayList<Object> paramList = new ArrayList<Object> ();
		paramList.add(nndMetadataDT.getInvestigationFormCd());
		paramList.add(nndMetadataDT.getQuestionIdentifierNnd());
		paramList.add(nndMetadataDT.getQuestionLabelNnd());
		paramList.add(nndMetadataDT.getQuestionRequiredNnd());
		paramList.add(nndMetadataDT.getQuestionDataTypeNnd());
		paramList.add(nndMetadataDT.getHL7SegmentField());
		paramList.add(nndMetadataDT.getOrderGroupId());
		paramList.add(nndMetadataDT.getTranslationTableNm());
		paramList.add(nndMetadataDT.getAddTime());
		paramList.add(nndMetadataDT.getAddUserId());
		paramList.add(nndMetadataDT.getLastChgTime());
		paramList.add(nndMetadataDT.getLastChgUserId());
		paramList.add(nndMetadataDT.getRecordStatusCd());
		paramList.add(nndMetadataDT.getRecordStatusTime());
		paramList.add(nndMetadataDT.getQuestionIdentifier());
		paramList.add(nndMetadataDT.getMsgTriggerIndCd());
		paramList.add(nndMetadataDT.getXmlPath());
		paramList.add(nndMetadataDT.getXmlTag());
		paramList.add(nndMetadataDT.getXmlDataType());
		paramList.add(nndMetadataDT.getPartTypeCd());
		paramList.add(nndMetadataDT.getRepeatGroupSeqNbr());
		paramList.add(nndMetadataDT.getQuestionOrderNnd());
		paramList.add(nndMetadataDT.getNbsPageUid());
		paramList.add(nndMetadataDT.getNbsUiMetadataUid());
		// Where clause paramater
		paramList.add(nndMetadataDT.getNndMetadataUid());

		try {
			preparedStmtMethod(null, paramList, UPDATE_NND_METADATA + WHERE_NND_METADATA_UID, NEDSSConstants.UPDATE);

		} catch (Exception ex) {
			logger.fatal("Exception in updateNNDMetadata: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}


	public void updateNNDMetadataHistoryByNBSPageUid(Long nbsPageUid) throws NEDSSDAOSysException, NEDSSSystemException {
		int nextVersionCtrlNbr = 1; 
		
		ArrayList<Object> paramList = new ArrayList<Object> ();
		//where param

		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;

		try {
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(SELECT_MAX_HISTORY_VERSION);
			preparedStmt.setLong(1, nbsPageUid);
			resultSet = preparedStmt.executeQuery();
			if (resultSet.next()) {
				nextVersionCtrlNbr = resultSet.getInt(1) + 1;
			}

			paramList.add(nextVersionCtrlNbr);
			paramList.add(nbsPageUid);
			
			preparedStmtMethod(null, paramList, INSERT_NND_METADATA_HISTORY_BY_NBS_PAGE_UID, NEDSSConstants.UPDATE);

		} catch (Exception ex) {
			logger.fatal("Exception in updateNNDMetadataHistory: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
	}

	
	public void updateNNDMetadataHistory(Long nndMetadataUid, Long nbsPageUid) throws NEDSSDAOSysException, NEDSSSystemException {
		int nextVersionCtrlNbr = 1; 

		
		ArrayList<Object> paramList = new ArrayList<Object> ();
		//where param

		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;

		try {
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(SELECT_MAX_HISTORY_VERSION);
			preparedStmt.setLong(1, nbsPageUid);
			resultSet = preparedStmt.executeQuery();
			if (resultSet.next()) {
				nextVersionCtrlNbr = resultSet.getInt(1) + 1;
			}

			paramList.add(nextVersionCtrlNbr);
			paramList.add(nndMetadataUid);
			
			preparedStmtMethod(null, paramList, INSERT_NND_METADATA_HISTORY, NEDSSConstants.UPDATE);

		} catch (Exception ex) {
			logger.fatal("Exception in updateNNDMetadataHistory: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		} finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
	}


}