package gov.cdc.nedss.localfields.ejb.dao;


import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.localfields.dt.NbsQuestionDT;
import gov.cdc.nedss.systemservice.uidgenerator.UidClassCodes;
import gov.cdc.nedss.systemservice.uidgenerator.UidGeneratorHelper;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * NBSQuestionDAOImpl used to Load, Store or Delete an NBS Question Metadata
 * @author nmallela
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: Computer Sciences Corporation</p>
 * NBSQuestionDAOImpl.java
 * Sep 2, 2008
 * @version
 */
public class NBSQuestionDAOImpl extends DAOBase {

	private static final LogUtils logger = new LogUtils(NBSQuestionDAOImpl.class.getName());
    private long nbsQuestionUid = -1;
    private long questionIdentifier = -1;
	
	private static final String FIND_NBS_QUESTION = "SELECT  nbs_question_uid \"nbsQuestionUid\", "
													+"add_time \"addTime\", "
													+"add_user_id \"addUserId\", "
													+"code_set_group_id \"codeSetGroupId\", "
													+"data_cd \"dataCd\", "
													+"data_location \"dataLocation\", "
													+"question_identifier \"questionIdentifier\", "
													+"question_oid \"questionOid\", "
													+"question_oid_system_txt \"questionOidSystemTxt\", "
													+"question_unit_identifier \"questionUnitIdentifier\", "
													+"data_type \"dataType\", "
													+"data_use_cd \"dataUseCd\", "
													+"last_chg_time \"lastChgTime\", "
													+"last_chg_user_id \"lastChgUserId\", "
													+"question_label \"questionLabel\", "
													+"question_tool_tip \"questionToolTip\", "
													+"datamart_column_nm \"datamartColumnNm\", "
													+"part_type_cd \"partTypeCd\", "
													+"default_value \"defaultValue\", "
													+"version_ctrl_nbr \"versionCtrlNbr\" "
													+"FROM NBS_Question ";
													
	private static final String WHERE_NBS_QUESTION_UID = "WHERE nbs_question_uid=? ";
	private static final String WHERE_QUESTION_IDENTIFIER = "WHERE question_identifier=? ";


	private static final String CREATE_NBS_QUESTION = "INSERT INTO NBS_Question(nbs_question_uid, add_time, add_user_id, code_set_group_id, data_cd, data_location, question_identifier, question_oid, question_oid_system_txt, question_unit_identifier, data_type, data_use_cd, last_chg_time, last_chg_user_id, question_label, question_tool_tip, datamart_column_nm, part_type_cd, default_value, version_ctrl_nbr) "
			+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?)";

	private static final String UPDATE_NBS_QUESTION = 	"UPDATE NBS_Question "+
														"SET " +
														"code_set_group_id=?, "+
														"data_cd=?, "+
														"data_location=?, "+
														"question_identifier=?, "+
														"question_oid=?, "+
														"question_oid_system_txt=?, "+
														"question_unit_identifier=?, "+
														"data_type=?, "+
														"data_use_cd=?, "+
														"last_chg_time=?, "+
														"last_chg_user_id=?, "+
														"question_label=?, "+
														"question_tool_tip=?, "+
														"datamart_column_nm=?, "+
														"part_type_cd=?, "+
														"default_value=?, "+
														"version_ctrl_nbr=? " +
														"WHERE nbs_question_uid=? ";

	private static final String DELETE_NBS_QUESTION = "UPDATE NBS_Question SET version_ctrl_nbr=?,last_chg_time=?,last_chg_user_id=? WHERE nbs_question_uid=?";
	
	  private final String INSERT_HISTORY = "INSERT INTO NBS_Question_Hist(nbs_question_uid, add_time, add_user_id, code_set_group_id, data_cd, data_location, question_identifier, question_oid, question_oid_system_txt, question_unit_identifier, data_type, data_use_cd, last_chg_time, last_chg_user_id, question_label, question_tool_tip, datamart_column_nm, part_type_cd, default_value, version_ctrl_nbr)"
	      + " SELECT nbs_question_uid, add_time, add_user_id, code_set_group_id, data_cd, data_location, question_identifier, question_oid, question_oid_system_txt, question_unit_identifier, data_type, data_use_cd, last_chg_time, last_chg_user_id, question_label, question_tool_tip, datamart_column_nm, part_type_cd, default_value, version_ctrl_nbr FROM NBS_Question"
	      + " where nbs_question_uid = ? ";	
	  
	/**
	 * Returns a NbsQuestionDT searching by nbsQuestionUid
	 * @param nbsQuestionUid
	 * @return NbsQuestionDT
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	@SuppressWarnings("unchecked")
	public NbsQuestionDT findNBSQuestion(Long nbsQuestionUid) throws NEDSSDAOSysException, NEDSSSystemException {
		
		NbsQuestionDT questionDT = new NbsQuestionDT();
		ArrayList<Object> paramList = new ArrayList<Object> ();
		paramList.add(nbsQuestionUid);
		
		try {			
			paramList = (ArrayList<Object> ) preparedStmtMethod(questionDT, paramList, FIND_NBS_QUESTION + WHERE_NBS_QUESTION_UID, NEDSSConstants.SELECT);
			if(paramList.size() > 0)
				return (NbsQuestionDT)paramList.get(0);
			
		} catch (Exception ex) {
			logger.fatal("Exception in findNBSQuestion: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return questionDT;
		
	}
	
    public List<NbsQuestionDT> findNBSQuestions(String nbsQuestionUidStr) throws NEDSSDAOSysException, NEDSSSystemException
    {

        NbsQuestionDT questionDT = new NbsQuestionDT();
        ArrayList<Object> paramList = new ArrayList<Object>(); 

        try
        {
            String sql = FIND_NBS_QUESTION + " WHERE nbs_question_uid in ( " + nbsQuestionUidStr + " ) ";
            List<NbsQuestionDT> reList = (ArrayList<NbsQuestionDT>) preparedStmtMethod(questionDT, paramList, sql, NEDSSConstants.SELECT);
            return reList;
        }
        catch (Exception ex)
        {
            logger.fatal("Exception in findNBSQuestion: ERROR = " + ex.getMessage(), ex);
            throw new NEDSSSystemException(ex.toString());
        } 

    }

	@SuppressWarnings("unchecked")
	public NbsQuestionDT findNBSQuestionByQuestionIdentifier(String questionIdentifier) throws NEDSSDAOSysException, NEDSSSystemException {
		
		NbsQuestionDT questionDT = new NbsQuestionDT();
		ArrayList<Object> paramList = new ArrayList<Object> ();
		paramList.add(questionIdentifier);
		
		try {			
			paramList = (ArrayList<Object> ) preparedStmtMethod(questionDT, paramList, FIND_NBS_QUESTION + WHERE_QUESTION_IDENTIFIER, NEDSSConstants.SELECT);
			if(paramList.size() > 0)
				return (NbsQuestionDT)paramList.get(0);
			
		} catch (Exception ex) {
			logger.fatal("Exception in findNBSQuestion: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return questionDT;
		
	}

	
	/**
	 * creates an NBSQuestion - used by LDF
	 * @param dt
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	public long createNBSQuestion(NbsQuestionDT dt) throws NEDSSDAOSysException, NEDSSSystemException {
		try {
			//Generate questionUid
			UidGeneratorHelper uidGen = new UidGeneratorHelper();
			questionIdentifier = uidGen.getNbsIDLong(UidClassCodes.NBS_QUESTION_ID_LDF_CLASS_CODE).longValue();
		} catch (Exception e) {
			logger.error("Exception while calling UID Generator for NBS_QUESTION_ID_LDF: " + e.getMessage(), e);
			throw new NEDSSSystemException(e.toString());
		}
		dt.setQuestionIdentifier("LDF"+questionIdentifier);
		return createNBSQuestionBase(dt);
	}
		
		
	public long createNBSQuestionBase(NbsQuestionDT dt) throws NEDSSDAOSysException, NEDSSSystemException {
		try {
			//Generate questionUid
			UidGeneratorHelper uidGen = new UidGeneratorHelper();
			nbsQuestionUid = uidGen.getNbsIDLong(UidClassCodes.NBS_QUESTION_LDF_CLASS_CODE).longValue();
		} catch (Exception e) {
			logger.error("Exception while calling UID Generator for NBS_QUESTION_LDF: " + e.getMessage(), e);
			throw new NEDSSSystemException(e.toString());
		}
		
		ArrayList<Object> paramList = new ArrayList<Object> ();
		paramList.add(new Long(nbsQuestionUid));
		paramList.add(dt.getAddTime());
		paramList.add(dt.getAddUserId());
		paramList.add(dt.getCodeSetGroupId());		
		paramList.add(dt.getDataCd());
		paramList.add(dt.getDataLocation());
		paramList.add(dt.getQuestionIdentifier());
		paramList.add(dt.getQuestionOid());		
		paramList.add(dt.getQuestionOidSystemTxt());
		paramList.add(dt.getQuestionUnitIdentifier());
		paramList.add(dt.getDataType());
		paramList.add(dt.getDataUseCd());		
		paramList.add(dt.getLastChgTime());
		paramList.add(dt.getLastChgUserId());
		paramList.add(dt.getQuestionLabel());
		paramList.add(dt.getQuestionToolTip());		
		paramList.add(dt.getDatamartColumnNm());
		paramList.add(dt.getPartTypeCd());
		paramList.add(dt.getDefaultValue());
		paramList.add(dt.getVersionCtrlNbr());		
		
		try {			
			int resultCount = ((Integer)preparedStmtMethod(null, paramList, CREATE_NBS_QUESTION, NEDSSConstants.UPDATE)).intValue();
            if (resultCount != 1) {
                logger.error("Exception in createNBSQuestion: , " + "resultCount = " + resultCount);
    			throw new NEDSSSystemException("Exception in createNBSQuestion:");
            }			
			
		} catch (Exception ex) {
			logger.error("Exception in createNBSQuestion: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}		
		return nbsQuestionUid;
	}

	/**
	 * Update NBSQuestion comprises of INSERTING exiting row to HIST, and updating the current record 
	 * @param dt
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	public void updateNBSQuestion (NbsQuestionDT dt) throws NEDSSDAOSysException, NEDSSSystemException {
		
		//History
		updateNBSQuestionHistory(dt.getNbsQuestionUid());
		
		ArrayList<Object> paramList = new ArrayList<Object> ();
		paramList.add(dt.getCodeSetGroupId());		
		paramList.add(dt.getDataCd());
		paramList.add(dt.getDataLocation());
		paramList.add(dt.getQuestionIdentifier());
		paramList.add(dt.getQuestionOid());		
		paramList.add(dt.getQuestionOidSystemTxt());
		paramList.add(dt.getQuestionUnitIdentifier());
		paramList.add(dt.getDataType());
		paramList.add(dt.getDataUseCd());		
		paramList.add(dt.getLastChgTime());
		paramList.add(dt.getLastChgUserId());
		paramList.add(dt.getQuestionLabel());
		paramList.add(dt.getQuestionToolTip());		
		paramList.add(dt.getDatamartColumnNm());
		paramList.add(dt.getPartTypeCd());
		paramList.add(dt.getDefaultValue());
		paramList.add(dt.getVersionCtrlNbr());
		//where param
		paramList.add(dt.getNbsQuestionUid());
		
		try {			
			preparedStmtMethod(null, paramList, UPDATE_NBS_QUESTION, NEDSSConstants.UPDATE);
			
		} catch (Exception ex) {
			logger.fatal("Exception in updateNBSQuestion: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}		
	}
	
	/**
	 * Delete NBSQuestion by questionUid
	 * @param dt
	 * @throws NEDSSDAOSysException
	 * @throws NEDSSSystemException
	 */
	public void deleteNBSQuestion (NbsQuestionDT dt) throws NEDSSDAOSysException, NEDSSSystemException {
		
		//History
		updateNBSQuestionHistory(dt.getNbsQuestionUid());
		
		ArrayList<Object> paramList = new ArrayList<Object> ();
		paramList.add(dt.getVersionCtrlNbr());
		paramList.add(dt.getLastChgTime());
		paramList.add(dt.getLastChgUserId());

		//where param
		paramList.add(dt.getNbsQuestionUid());
		
		try {			
			preparedStmtMethod(null, paramList, DELETE_NBS_QUESTION, NEDSSConstants.UPDATE);
			
		} catch (Exception ex) {
			logger.fatal("Exception in deleteNBSQuestion: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}			
		
	}
	
	/**
	 * updateNBSQuestionHistory Copies the current record to the Question history table before updated
	 * @param questionUid
	 */
	public void updateNBSQuestionHistory(Long questionUid) {

		ArrayList<Object> paramList = new ArrayList<Object> ();
		//where param
		paramList.add(questionUid);
		
		try {			
			preparedStmtMethod(null, paramList, INSERT_HISTORY, NEDSSConstants.UPDATE);
			
		} catch (Exception ex) {
			logger.fatal("Exception in updateNBSQuestionHistory: ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}
	
	
}