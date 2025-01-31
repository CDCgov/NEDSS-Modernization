package gov.cdc.nedss.nbsactentity.dao;

import gov.cdc.nedss.act.publichealthcase.vo.PublicHealthCaseVO;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.nbsactentity.dt.NbsActEntityDT;
import gov.cdc.nedss.pam.act.NbsCaseAnswerDT;
import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.DataTables;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

/**
 * Name: NbsHistoryDAO.java Description: DAO Object for Pam History answers.
 * Copyright: Copyright (c) 2008 Company: Computer Sciences Corporation
 *
 * @author Pradeep Sharma
 */

public class NbsActEntityHistoryDAO extends DAOBase {

	private static final LogUtils logger = new LogUtils(NbsActEntityHistoryDAO.class
			.getName());
	private final String SELECT_NBS_ANSWER_COLLECTION = "SELECT nbs_case_answer_uid \"nbsCaseAnswerUid\", seq_nbr \"seqNbr\", add_time \"addTime\", add_user_id \"addUserId\", answer_txt \"answerTxt\", last_chg_time \"lastChgTime\", last_chg_user_id \"lastChgUserId\", nbs_question_uid \"nbsQuestionUid\", act_uid \"actUid\", nbs_question_version_ctrl_nbr \"nbsQuestionVersionCtrlNbr\", answer_large_txt \"answerLargeTxt\",nbs_table_metadata_uid \"nbsTableMetadataUid\", answer_group_seq_nbr \"answerGroupSeqNbr\" FROM "
		+ DataTables.NBS_CASE_ANSWER_TABLE	+ " where act_uid=? order by nbs_question_uid";
	private final String SELECT_CASE_ENTITY_COLLECTION = "SELECT nbs_act_entity_uid \"nbsActEntityUid\", add_time \"addTime\", add_user_id \"addUserId\", last_chg_time \"lastChgTime\", last_chg_user_id \"lastChgUserId\", act_uid \"actUid\", entity_uid \"entityUid\", type_cd \"typeCd\",  entity_version_ctrl_nbr \"entityVersionCtrlNbr\" FROM "
		+ DataTables.NBS_ACT_ENTITY_TABLE	+ " where act_uid=? ";

	private final String INSERT_NBS_ANSWER_HIST = "INSERT INTO "	+ DataTables.NBS_CASE_ANSWER_TABLE_HIST
	+ "(nbs_case_answer_uid, seq_nbr, add_time, add_user_id, answer_txt, last_chg_time, last_chg_user_id, nbs_question_uid, act_uid, nbs_question_version_ctrl_nbr, act_version_ctrl_nbr, record_status_cd, record_status_time, nbs_table_metadata_uid,answer_group_seq_nbr) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	private final String INSERT_PAM_CASE_ENTITY_HIST = "INSERT INTO "
		+ DataTables.NBS_CASE_ENTITY_TABLE_HIST + "(nbs_act_entity_uid, add_time, add_user_id, last_chg_time, last_chg_user_id, act_uid, entity_uid, type_cd,act_version_ctrl_nbr, entity_version_ctrl_nbr,record_status_cd, record_status_time) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";

	public PublicHealthCaseVO getPamHistory(PublicHealthCaseVO publicHealthCaseVO)
	throws NEDSSSystemException {
		try{
			Collection<Object>  pamEntityColl = getPamCaseEntityDTCollection(publicHealthCaseVO.getThePublicHealthCaseDT());
			publicHealthCaseVO.setNbsCaseEntityCollection(pamEntityColl);
			Collection<Object>  pamAnswerColl = getPamAnswerDTCollection(publicHealthCaseVO.getThePublicHealthCaseDT());
			publicHealthCaseVO.setNbsAnswerCollection(pamAnswerColl);
			return publicHealthCaseVO;
		}catch(Exception ex){
			logger.fatal("Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}
	public void insertPamHistory(PublicHealthCaseVO publicHealthCaseVO,  PublicHealthCaseVO oldPublicHealthCaseVO)
	throws NEDSSSystemException {
		try{
			insertPamEntityHistoryDTCollection( publicHealthCaseVO.getNbsCaseEntityCollection(), oldPublicHealthCaseVO.getThePublicHealthCaseDT());
			insertPamAnswerHistoryDTCollection( publicHealthCaseVO.getNbsAnswerCollection(), oldPublicHealthCaseVO.getThePublicHealthCaseDT());
		}catch(Exception ex){
			logger.fatal("Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}


	@SuppressWarnings("unchecked")
	public Collection<Object>  getPamAnswerDTCollection( RootDTInterface rootDTInterface)
	throws NEDSSSystemException {
		try{
			NbsCaseAnswerDT pamAnswerDT = new NbsCaseAnswerDT();
			ArrayList<Object> nbsAnswerDTCollection  = new ArrayList<Object> ();
			nbsAnswerDTCollection.add(rootDTInterface.getUid());
			try {
				nbsAnswerDTCollection  = (ArrayList<Object> ) preparedStmtMethod(
						pamAnswerDT, nbsAnswerDTCollection,
						SELECT_NBS_ANSWER_COLLECTION, NEDSSConstants.SELECT);
			} catch (Exception ex) {
				logger.fatal("Exception in getPamAnswerDTCollection:  ERROR = "
						+ ex);
				throw new NEDSSSystemException(ex.toString());
			}
			return nbsAnswerDTCollection;
		}catch(Exception ex){
			logger.fatal("Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}


	@SuppressWarnings("unchecked")
	public Collection<Object>  getPamCaseEntityDTCollection( RootDTInterface rootDTInterface) {
		NbsActEntityDT pamCaseEntityDT = new NbsActEntityDT();
		ArrayList<Object> pamEntityDTCollection  = new ArrayList<Object> ();
		pamEntityDTCollection.add(rootDTInterface.getUid());
		try {
			pamEntityDTCollection  = (ArrayList<Object> ) preparedStmtMethod(
					pamCaseEntityDT, pamEntityDTCollection,
					SELECT_CASE_ENTITY_COLLECTION, NEDSSConstants.SELECT);
		} catch (Exception ex) {
			logger.fatal("Exception in getPamEntityDTCollection:  ERROR = "
					+ ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return pamEntityDTCollection;
	}

	public void insertPamAnswerHistoryDTCollection(Collection<Object> nbsCaseAnswerDTColl,  RootDTInterface oldrootDTInterface) throws NEDSSSystemException {
		try {

			if (nbsCaseAnswerDTColl != null) {
				Iterator<Object> it = nbsCaseAnswerDTColl.iterator();
				while (it.hasNext()) {
					NbsCaseAnswerDT pamAnswerDT = (NbsCaseAnswerDT) it
					.next();
					insertPamAnswerDTHistory(pamAnswerDT,
							oldrootDTInterface);
				}
			}
		} catch (NEDSSSystemException ex) {
			logger.error("error thrown at PamAnswerDAO inser method"+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	private void insertPamAnswerDTHistory(NbsCaseAnswerDT pamAnswerDT,
			RootDTInterface oldrootDTInterface) throws NEDSSSystemException {
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		int resultCount = 0;
		logger.debug("INSERT_PAM_ANSWER_HIST=" + INSERT_NBS_ANSWER_HIST);
		dbConnection = getConnection();
		try {
			preparedStmt = dbConnection
			.prepareStatement(INSERT_NBS_ANSWER_HIST);
			int i = 1;
			preparedStmt.setLong(i++, pamAnswerDT.getNbsCaseAnswerUid()
					.longValue()); // 1
			preparedStmt.setInt(i++, pamAnswerDT.getSeqNbr().intValue()); // 2
			preparedStmt.setTimestamp(i++, oldrootDTInterface.getAddTime());// 3
			preparedStmt.setLong(i++, pamAnswerDT.getAddUserId()
					.longValue()); // 4
			preparedStmt.setString(i++, pamAnswerDT.getAnswerTxt()); // 5
			if (oldrootDTInterface.getLastChgTime() == null)
				preparedStmt.setNull(i++, Types.TIMESTAMP); // 6
			else
				preparedStmt.setTimestamp(i++, oldrootDTInterface.getLastChgTime()); // 6
			if (oldrootDTInterface
					.getLastChgUserId() == null)// 7
				preparedStmt.setNull(i++, Types.INTEGER);
			else
				preparedStmt.setLong(i++, oldrootDTInterface.getLastChgUserId()
						.longValue()); // 7
			preparedStmt.setLong(i++, pamAnswerDT.getNbsQuestionUid()
					.longValue());// 8
			preparedStmt.setLong(i++, pamAnswerDT.getActUid()
					.longValue());// 9
			preparedStmt.setInt(i++, pamAnswerDT
					.getNbsQuestionVersionCtrlNbr().intValue()); // 10
			preparedStmt.setInt(i++,
					oldrootDTInterface
					.getVersionCtrlNbr().intValue());// 11
			preparedStmt.setString(i++,
					 oldrootDTInterface
					.getRecordStatusCd());// 12
			preparedStmt.setTimestamp(i++,
					oldrootDTInterface
					.getRecordStatusTime());// 13
			   if(pamAnswerDT.getNbsTableMetadataUid()!=null)
		    		preparedStmt.setLong(i++, pamAnswerDT.getNbsTableMetadataUid().longValue()); //12
		    else
		    	 preparedStmt.setNull(i++, Types.INTEGER);
			if(pamAnswerDT.getAnswerGroupSeqNbr()!=null)
		    		preparedStmt.setLong(i++, pamAnswerDT.getAnswerGroupSeqNbr()); //13
			else
			    	preparedStmt.setNull(i++, Types.INTEGER);
			resultCount = preparedStmt.executeUpdate();
			logger.debug("resultCount is " + resultCount);
		} catch (SQLException sqlex) {
			logger.fatal("SQLException while inserting "
					+ "NbsAnswerHistDT into NBS_ANSWER_HISTORY:"
					+ pamAnswerDT.toString(), sqlex);
			throw new NEDSSDAOSysException(sqlex.toString());
		} catch (Exception ex) {
			logger.fatal(
					"Error while inserting into NBS_ANSWER_HISTORY, nbsAnswerHistDT="
					+ pamAnswerDT.toString(), ex);
			throw new NEDSSSystemException(ex.toString());
		} finally {
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}

	}// end of insert method
	


	public void insertPageEntityHistoryDTCollection(Collection<Object> nbsCaseEntityDTColl,  RootDTInterface oldrootDTInterface) throws NEDSSSystemException {
		try {

			if (nbsCaseEntityDTColl != null) {
				Iterator<Object> it = nbsCaseEntityDTColl.iterator();
				while (it.hasNext()) {
					NbsActEntityDT nbsActEntityDT = (NbsActEntityDT) it.next();
					removeNbsActEntity(nbsActEntityDT);
					insertPamCaseEntityHistDT(nbsActEntityDT,oldrootDTInterface);
				}
			}
		} catch (NEDSSSystemException ex) {
			logger.error("NbsActEntityHistoryDAO.insertPamEntityHistoryDTCollection inser method failed"+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}
	public void insertPamEntityHistoryDTCollection(Collection<Object> nbsCaseEntityDTColl,  RootDTInterface oldrootDTInterface) throws NEDSSSystemException {
		try {

			if (nbsCaseEntityDTColl != null) {
				Iterator<Object> it = nbsCaseEntityDTColl.iterator();
				while (it.hasNext()) {
					NbsActEntityDT nbsActEntityDT = (NbsActEntityDT) it.next();
					insertPamCaseEntityHistDT(nbsActEntityDT,oldrootDTInterface);
				}
			}
		} catch (NEDSSSystemException ex) {
			logger.error("NbsActEntityHistoryDAO.insertPamEntityHistoryDTCollection inser method failed"+ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}
	private final String DELETE_NBS_ACT_ENTITY= "DELETE from  "+ DataTables.NBS_ACT_ENTITY_TABLE 	+ " where nbs_act_entity_uid= ?";
	
	private void removeNbsActEntity(NbsActEntityDT nbsCaseEntityDT)	throws NEDSSDAOSysException, NEDSSSystemException {

		PreparedStatement preparedStmt = null;
		Connection dbConnection = null;

		try {
			logger.debug("$$$$###Delete DELETE_NBS_CASE_ENTITY being called :"+ DELETE_NBS_ACT_ENTITY);
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(DELETE_NBS_ACT_ENTITY);
			preparedStmt.setLong(1,nbsCaseEntityDT.getNbsActEntityUid().longValue());
			preparedStmt.executeUpdate();
		} catch (SQLException se) {
		    logger.fatal("SQLException while removeNbsCaseEntity " +nbsCaseEntityDT.toString(), se);
			throw new NEDSSDAOSysException("Error: SQLException while deleting\n" + se.getMessage());
		} finally {
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
	}
	private void insertPamCaseEntityHistDT(
			NbsActEntityDT nbsActEntityDT,
			RootDTInterface oldrootDTInterface) throws NEDSSSystemException {
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		logger.debug("INSERT_PAM_CASE_ENTITY_HIST="
				+ INSERT_PAM_CASE_ENTITY_HIST);
		
		try {
			dbConnection = getConnection();
			int resultCount = 0;
			preparedStmt = dbConnection
			.prepareStatement(INSERT_PAM_CASE_ENTITY_HIST);
			int i = 1;
			preparedStmt.setLong(i++, nbsActEntityDT.getNbsActEntityUid()
					.longValue()); // 1
			if (nbsActEntityDT.getAddTime() == null)
				preparedStmt.setTimestamp(i++, new java.sql.Timestamp(
						new Date().getTime())); // 2
			else
				preparedStmt
				.setTimestamp(i++, nbsActEntityDT.getAddTime()); // 2
			preparedStmt.setLong(i++, nbsActEntityDT.getAddUserId()
					.longValue()); // 3
			if (oldrootDTInterface.getLastChgTime() == null)
				preparedStmt.setTimestamp(i++, new java.sql.Timestamp(
						new Date().getTime())); // 4
			else
				preparedStmt.setTimestamp(i++, oldrootDTInterface.getLastChgTime()); // 4
			if (oldrootDTInterface
					.getLastChgUserId() == null)
				preparedStmt.setNull(i++, Types.INTEGER);// 5
			else
				preparedStmt.setLong(i++, oldrootDTInterface.getLastChgUserId()
						.longValue()); // 5
			preparedStmt.setLong(i++, nbsActEntityDT
					.getActUid().longValue()); // 6
			preparedStmt.setLong(i++, nbsActEntityDT.getEntityUid()
					.longValue()); // 7
			preparedStmt.setString(i++, nbsActEntityDT.getTypeCd()); // 8
			preparedStmt.setInt(i++,
					(oldrootDTInterface
							.getVersionCtrlNbr().intValue())); // 9
			preparedStmt.setInt(i++,
					(nbsActEntityDT.getEntityVersionCtrlNbr().intValue())); // 9
			preparedStmt.setString(i++,
					oldrootDTInterface
					.getRecordStatusCd());// 10
			preparedStmt.setTimestamp(i++,
					oldrootDTInterface
					.getRecordStatusTime());// 11

			resultCount = preparedStmt.executeUpdate();
			if (resultCount != 1) {
				logger
				.fatal("Exception while inserting into PAM_CASE_ENTITY, pamCaseEntityHistDT="
						+ nbsActEntityDT.toString());
				throw new NEDSSSystemException(
						"Error: none or more than one nbsCaseEntityDT inserted at a time, resultCount = "
						+ resultCount);
			}
		} catch (SQLException sex) {
			logger.fatal("SQLException while inserting "
					+ "NbsCaseEntityDT into Nbs_Case_Entity: \n", sex);
			logger.fatal(
					"SQLException while inserting into Nbs_Case_Entity, pamCaseEntityHistDT="
					+ nbsActEntityDT.toString(), sex);
			throw new NEDSSSystemException(sex.toString());
		} catch (Exception ex) {
			logger.fatal(
					"Error while inserting into Pam_Case_Entity, pamCaseEntityHistDT="
					+ nbsActEntityDT.toString(), ex);
			throw new NEDSSSystemException(ex.toString());
		} finally {
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}

	}// end of insert method

	
}
