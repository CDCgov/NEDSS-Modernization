package gov.cdc.nedss.act.ctcontact.ejb.dao;

import gov.cdc.nedss.act.ctcontact.dt.CTContactAnswerDT;
import gov.cdc.nedss.act.ctcontact.dt.CTContactDT;
import gov.cdc.nedss.act.ctcontact.vo.CTContactVO;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.page.ejb.pageproxyejb.dt.NbsAnswerDT;
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
* Name:		CTContactAnswerDAO.java
* Description:	DAO Object for CT_contact answers.
* Copyright:	Copyright (c) 2009
* Company: 	Computer Sciences Corporation
*/

public class CTContactAnswerDAO extends DAOBase{
	static final LogUtils logger = new LogUtils(CTContactAnswerDAO.class.getName());
	//private static final String SELECT_CT_CONTACT_ANSWER_COLLECTION = "SELECT ct_contact_answer_uid \"ctContactAnswerUid\", seq_nbr \"seqNbr\", add_time \"addTime\", add_user_id \"addUserId\", answer_txt \"answerTxt\", last_chg_time \"lastChgTime\", last_chg_user_id \"lastChgUserId\", nbs_question_uid \"nbsQuestionUid\", ct_contact_uid \"ctContactUid\", nbs_question_version_ctrl_nbr \"nbsQuestionVersionCtrlNbr\" FROM "+ DataTables.CT_CONTACT_ANSWER +" where ct_contact_uid = ? ORDER BY nbs_question_uid";
	private static final String SELECT_CT_CONTACT_ANSWER_COLLECTION = "SELECT ct_contact_answer_uid \"ctContactAnswerUid\", seq_nbr \"seqNbr\",  answer_txt \"answerTxt\", last_chg_time \"lastChgTime\", last_chg_user_id \"lastChgUserId\", nbs_question_uid \"nbsQuestionUid\", ct_contact_uid \"ctContactUid\", nbs_question_version_ctrl_nbr \"nbsQuestionVersionCtrlNbr\", answer_group_seq_nbr \"answerGroupSeqNbr\" FROM "+ DataTables.CT_CONTACT_ANSWER +" where ct_contact_uid = ? ORDER BY nbs_question_uid";
	private static final String INSERT_CT_CONTACT_ANSWER = "INSERT INTO " + DataTables.CT_CONTACT_ANSWER + "(seq_nbr, answer_txt, last_chg_time, last_chg_user_id, nbs_question_uid, ct_contact_uid, nbs_question_version_ctrl_nbr, record_status_cd, record_status_time, answer_group_seq_nbr) VALUES(?,?,?,?,?,?,?,?,?,?)";
	private static final String UPDATE_CT_CONTACT_ANSWER = "UPDATE "+ DataTables.CT_CONTACT_ANSWER +" SET seq_nbr=?, answer_txt=?, last_chg_time=?, last_chg_user_id=?, nbs_question_version_ctrl_nbr=?, record_status_cd=?, record_status_time=?, answer_group_seq_nbr=? WHERE ct_contact_answer_uid=?";
	private static final String DELETE_CT_CONTACT_ANSWER = "DELETE FROM "+ DataTables.CT_CONTACT_ANSWER + " WHERE ct_contact_answer_uid= ?";
	private static final String LOGICALLY_DELETE_CT_CONTACT_ANSWER = "UPDATE " + DataTables.CT_CONTACT_ANSWER + " SET last_chg_time=?, last_chg_user_id=?,  record_status_cd=?, record_status_time=? WHERE ct_contact_uid=?";
	private static final String LOGICALLY_DELETE_CT_CONTACT_ANSWER_BY_ANSWER_UID = "UPDATE " + DataTables.CT_CONTACT_ANSWER + " SET last_chg_time=?, last_chg_user_id=?,  record_status_cd=?, record_status_time=? WHERE ct_contact_uid=? AND ct_contact_answer_uid =?";
	
	private static final String INSERT_CT_CONTACT_ANSWER_HIST = "INSERT INTO "	+ DataTables.CT_CONTACT_ANSWER_HIST + "(ct_contact_answer_uid, seq_nbr,   answer_txt, last_chg_time, last_chg_user_id, nbs_question_uid, ct_contact_uid, nbs_question_version_ctrl_nbr, ct_contact_version_ctrl_nbr, record_status_cd, record_status_time, answer_group_seq_nbr) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
	
	/*
	 * Get CTContactAnswerDT Collection<Object>  Object for a given ctContactUid
	 * @return Collection<Object> of CTContactAnswerDT
	 */
	@SuppressWarnings("unchecked")
	public Map<Object, Object> getCTAnswerDTCollection(Long ctContactUid) throws NEDSSSystemException{
		CTContactAnswerDT  ctContactAnswerDT  = new CTContactAnswerDT();
		ArrayList<Object>  CTContactDTCollection  = new ArrayList<Object> ();
		CTContactDTCollection.add(ctContactUid);
		Map<Object, Object> ctContactReturnAnswerMap = new HashMap<Object, Object>();
		Map<Object, Object> ctContactAnswerMap = new HashMap<Object, Object>();
		Map<Object, Object> ctContactRepeatingAnswerMap = new HashMap<Object, Object>();
		try
		{
			CTContactDTCollection  = (ArrayList<Object> )preparedStmtMethod(ctContactAnswerDT, CTContactDTCollection, SELECT_CT_CONTACT_ANSWER_COLLECTION, NEDSSConstants.SELECT);
			Iterator<Object> it = CTContactDTCollection.iterator();
			Long nbsQuestionUid = new Long(0);
			Collection<Object>  coll = new ArrayList<Object> ();
			while(it.hasNext()){
				CTContactAnswerDT ctConAnsDT = (CTContactAnswerDT)it.next();
				if (ctConAnsDT.getAnswerGroupSeqNbr() != null && ctConAnsDT.getAnswerGroupSeqNbr() > -1)
                {
                    if (ctContactRepeatingAnswerMap.get(ctConAnsDT.getNbsQuestionUid()) == null)
                    {
                        Collection collection = new ArrayList();
                        collection.add(ctConAnsDT);
                        ctContactRepeatingAnswerMap.put(ctConAnsDT.getNbsQuestionUid(), collection);
                    }
                    else
                    {
                        Collection collection = (Collection) ctContactRepeatingAnswerMap.get(ctConAnsDT.getNbsQuestionUid());
                        collection.add(ctConAnsDT);
                        ctContactRepeatingAnswerMap.put(ctConAnsDT.getNbsQuestionUid(), collection);
                    }
                }

				else if((ctConAnsDT.getNbsQuestionUid().compareTo(nbsQuestionUid)==0) && ctConAnsDT.getSeqNbr()!=null && ctConAnsDT.getSeqNbr().intValue()>0){
					coll.add(ctConAnsDT);
				}
				else if(ctConAnsDT.getSeqNbr()!=null && ctConAnsDT.getSeqNbr().intValue()>0) {
					if(coll.size()>0) {
						ctContactAnswerMap.put(nbsQuestionUid, coll);
						coll= new ArrayList<Object> ();
					}
					coll.add(ctConAnsDT);
				}
				else {
					if(coll.size()>0) {
						ctContactAnswerMap.put(nbsQuestionUid, coll);
					}
					ctContactAnswerMap.put(ctConAnsDT.getNbsQuestionUid(), ctConAnsDT);
					coll= new ArrayList<Object> ();
				}
				nbsQuestionUid = ctConAnsDT.getNbsQuestionUid();
				if(!it.hasNext() && coll.size()>0) {
					ctContactAnswerMap.put(ctConAnsDT.getNbsQuestionUid(), coll);
				}
			}
		}
		 catch (Exception ex) {
			logger.fatal("Exception in getCTAnswerDTCollection:  ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		ctContactReturnAnswerMap.put(NEDSSConstants.NON_REPEATING_QUESTION, ctContactAnswerMap);
		ctContactReturnAnswerMap.put(NEDSSConstants.REPEATING_QUESTION, ctContactRepeatingAnswerMap);
		return ctContactReturnAnswerMap;
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	public Collection<Object> getSummaryCTContactDTCollection(Long ctContactUid) throws NEDSSSystemException{
		CTContactAnswerDT ctContactAnswerDT = new CTContactAnswerDT();
		ArrayList<Object> ctContactDTCollection = new ArrayList<Object> ();
		ctContactDTCollection.add(ctContactUid);
		try {
			ctContactDTCollection  = (ArrayList<Object> )preparedStmtMethod(ctContactAnswerDT, ctContactDTCollection, SELECT_CT_CONTACT_ANSWER_COLLECTION, NEDSSConstants.SELECT);
		} catch (Exception ex) {
			logger.fatal("Exception in getSummaryCTContactDTCollection:  ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		
		return ctContactDTCollection;
	}
	
	public void storeCTContactDTCollection(Collection<Object> ctContactDTColl,
			CTContactDT ctContactDT) throws NEDSSSystemException {
		try {
			if (ctContactDTColl != null) {
				Iterator<Object> it = ctContactDTColl.iterator();
				while (it.hasNext()) {
					Object object = it.next();
					if (object instanceof NbsAnswerDT) {
						CTContactAnswerDT ctContactAnswerDT = new CTContactAnswerDT(
								(NbsAnswerDT) object);
						if (ctContactAnswerDT.isItDirty()) {
							updateCTContactAnswer(ctContactAnswerDT,
									ctContactDT);
						} else if (ctContactAnswerDT.isItDelete()) {
							removeCTContactAnswer(ctContactAnswerDT);
						} else if (ctContactAnswerDT.isItNew()) {
							insertCTContactAnswer(ctContactAnswerDT,
									ctContactDT);
						}
					} else if (object instanceof ArrayList<?>) {
						Collection<?> innerCollection = (ArrayList<?>) object;
						Iterator<?> iter = innerCollection.iterator();
						while (iter.hasNext()) {
							Object ans = iter.next();
							if (ans instanceof NbsAnswerDT) {
								CTContactAnswerDT ctContactAnswerDT1 = new CTContactAnswerDT(
										(NbsAnswerDT) ans);
								if (ctContactAnswerDT1.isItDirty()) {
									updateCTContactAnswer(ctContactAnswerDT1,
											ctContactDT);
								} else if (ctContactAnswerDT1.isItDelete()) {
									removeCTContactAnswer(ctContactAnswerDT1);
								} else if (ctContactAnswerDT1.isItNew()) {
									insertCTContactAnswer(ctContactAnswerDT1,
											ctContactDT);
								}
							}
						}
					}
				}
			}
		} catch (NEDSSSystemException ex) {
			logger.error("Exception in storeCTContactDTCollection:  ERROR ="
					+ ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	private void insertCTContactAnswer(CTContactAnswerDT ctContactAnswerDT, CTContactDT ctContactDT) throws  NEDSSSystemException {
			Connection dbConnection = null;
			PreparedStatement preparedStmt = null;
			int resultCount = 0;
			logger.debug("INSERT_CT_CONTACT_ANSWER="+INSERT_CT_CONTACT_ANSWER);
			try{
				dbConnection = getConnection();
			}catch(NEDSSSystemException nsex){
				logger.fatal("SQLException while obtaining database connection for insertCTContactAnswer ", nsex);
				throw new NEDSSSystemException(nsex.toString());
			}
			try{
				preparedStmt = dbConnection.prepareStatement(INSERT_CT_CONTACT_ANSWER);
				int i = 1;
			    preparedStmt.setInt(i++, ctContactAnswerDT.getSeqNbr().intValue()); //1
			    preparedStmt.setString(i++, ctContactAnswerDT.getAnswerTxt()); //2
				preparedStmt.setTimestamp(i++, ctContactDT.getLastChgTime()); //3
				if(ctContactDT.getLastChgUserId()==null)
				    preparedStmt.setLong(i++, ctContactDT.getAddUserId().longValue());//4
			    else
			    	preparedStmt.setLong(i++, ctContactDT.getLastChgUserId().longValue()); //4
				preparedStmt.setLong(i++, ctContactAnswerDT.getNbsQuestionUid().longValue()); //5
				preparedStmt.setLong(i++, ctContactDT.getCtContactUid().longValue()); //6
				// inserting 1 for the question version control . This needs to be changed after the database is fixed
			    if(ctContactAnswerDT.getNbsQuestionVersionCtrlNbr() == null)
			    	ctContactAnswerDT.setNbsQuestionVersionCtrlNbr(new Integer(1));
			    preparedStmt.setInt(i++, ctContactAnswerDT.getNbsQuestionVersionCtrlNbr().intValue()); //7
			    preparedStmt.setString(i++, ctContactDT.getRecordStatusCd()); //8
			    preparedStmt.setTimestamp(i++, ctContactDT.getRecordStatusTime()); //9
			    if(ctContactAnswerDT.getAnswerGroupSeqNbr()!=null)
			    	preparedStmt.setInt(i++, ctContactAnswerDT.getAnswerGroupSeqNbr().intValue()); //10
			    else
			    	preparedStmt.setNull(i++, Types.INTEGER); //10
			    
			    resultCount = preparedStmt.executeUpdate();
			    logger.debug("resultCount in insertCTContactAnswer is " + resultCount);
			}
			catch(SQLException sqlex)
			{
			    logger.fatal("SQLException while inserting ctContactAnswerDT into CT_contact_answer: " + ctContactAnswerDT.toString(), sqlex);
			    throw new NEDSSDAOSysException( sqlex.toString() );
			}
			catch(Exception ex)
			{
			    logger.fatal("Error while inserting into CT_contact_answer, ctContactAnswerDT = " + ctContactAnswerDT.toString(), ex);
			    throw new NEDSSSystemException(ex.toString());
			}
			finally
			{
			  closeStatement(preparedStmt);
			  releaseConnection(dbConnection);
			}

	}
	
	private void updateCTContactAnswer(CTContactAnswerDT ctContactAnswerDT, CTContactDT ctContactDT)throws  NEDSSSystemException {
			Connection dbConnection = null;
			PreparedStatement preparedStmt = null;
			int resultCount = 0;
			logger.debug("UPDATE_CT_CONTACT_ANSWER = " + UPDATE_CT_CONTACT_ANSWER);
			try{
				dbConnection = getConnection();
			}catch(NEDSSSystemException nsex){
				logger.fatal("SQLException while obtaining database connection for updateCTContactAnswer ", nsex);
				throw new NEDSSSystemException(nsex.toString());
			}
			try{
				preparedStmt = dbConnection.prepareStatement(UPDATE_CT_CONTACT_ANSWER);
				int i = 1;
			    preparedStmt.setInt(i++, ctContactAnswerDT.getSeqNbr().intValue()); //1
			    preparedStmt.setString(i++, ctContactAnswerDT.getAnswerTxt()); //2
			   	preparedStmt.setTimestamp(i++, ctContactDT.getLastChgTime()); //3
			    preparedStmt.setLong(i++, ctContactDT.getLastChgUserId().longValue());//4
			    if(ctContactAnswerDT.getNbsQuestionVersionCtrlNbr() == null)
			    	ctContactAnswerDT.setNbsQuestionVersionCtrlNbr(new Integer(1));
			    else 
			    	preparedStmt.setInt(i++, ctContactAnswerDT.getNbsQuestionVersionCtrlNbr().intValue()); //5
		      preparedStmt.setString(i++, ctContactDT.getRecordStatusCd()); //6
			    preparedStmt.setTimestamp(i++, ctContactDT.getRecordStatusTime()); //7
			    if(ctContactAnswerDT.getAnswerGroupSeqNbr()!=null)
			    	preparedStmt.setInt(i++, ctContactAnswerDT.getAnswerGroupSeqNbr().intValue()); //8
			    else
			    	preparedStmt.setNull(i++, Types.INTEGER); //8
			    preparedStmt.setLong(i++, ctContactAnswerDT.getCtContactAnswerUid().longValue());//9
			    
			    resultCount = preparedStmt.executeUpdate();
				logger.debug("resultCount is " + resultCount);
			} catch(SQLException sqlex) {
			    logger.fatal("SQLException while updateCTContactAnswer ctContactAnswerDT into CT_contact_answer:" + ctContactAnswerDT.toString(), sqlex);
			    throw new NEDSSDAOSysException( sqlex.toString() );
			} catch(Exception ex) {
			    logger.fatal("Error while update into CT_contact_answer, ctContactAnswerDT = " + ctContactAnswerDT.toString(), ex);
			    throw new NEDSSSystemException(ex.toString());
			} finally {
			  closeStatement(preparedStmt);
			  releaseConnection(dbConnection);
			}
    }
	
	public void logDelCTContactAnswerDTCollection(CTContactVO contactVO) throws NEDSSSystemException {
		try {
   			logDelCTContactAnswers(contactVO);
		} catch(NEDSSSystemException ex) {
			logger.fatal("Exception in logDelCTContactAnswerDTCollection:  ERROR =" + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}
	
	private void logDelCTContactAnswers(CTContactVO contactVO) throws  NEDSSSystemException {
		Collection<Object> ctContactAnswerDTColl = new ArrayList<Object>();
		
		try{
			if (contactVO.getCtContactAnswerDTMap() != null){
				ctContactAnswerDTColl = contactVO.getCtContactAnswerDTMap().values();
			} else
				return;
	
			CTContactDT ctContactDT = contactVO.getcTContactDT();
			
			Iterator<Object> it = ctContactAnswerDTColl.iterator();
			while(it.hasNext()){
				Object object = it.next();
				if(object instanceof CTContactAnswerDT)	{
					logDelCTContactAnswerDT((CTContactAnswerDT) object, ctContactDT);
				} else if(object instanceof ArrayList<?>) {
					ArrayList<Object> list = (ArrayList<Object>)it.next();
					if(list!=null && list.size()>0){
						Iterator<Object> iter = list.iterator();
						while(iter.hasNext()){
							CTContactAnswerDT cTContactAnswerDT =(CTContactAnswerDT)iter.next();
							logDelCTContactAnswerDT(cTContactAnswerDT, ctContactDT);
						}
					}
				} else logger.error("Unexpected Error (object type) while logically deleting ctContactAnswerDT");
			}
		}catch(Exception ex){
			logger.fatal("Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}
		
	private void logDelCTContactAnswerDT(CTContactAnswerDT ctContactAnswerDT, CTContactDT ctContactDT){
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;

		logger.debug("LOGICALLY_DELETE_CT_CONTACT_ANSWER = " + LOGICALLY_DELETE_CT_CONTACT_ANSWER);
		try {
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(LOGICALLY_DELETE_CT_CONTACT_ANSWER);
			int i = 1;
			preparedStmt.setTimestamp(i++, ctContactDT.getLastChgTime()); //1
			preparedStmt.setLong(i++, ctContactAnswerDT.getLastChgUserId().longValue());//2 

			preparedStmt.setString(i++, ctContactDT.getRecordStatusCd()); //3
			preparedStmt.setTimestamp(i++, ctContactDT.getRecordStatusTime()); //4
			preparedStmt.setLong(i++, ctContactDT.getCtContactUid()); //5

			int resultCount = preparedStmt.executeUpdate();
			logger.debug("resultCount is " + resultCount);
		} catch(SQLException sqlex) {
			logger.fatal("SQLException while logDelCTContactAnswers - ctContactAnswerDT into CT_contact_answer: " + ctContactAnswerDT.toString(), sqlex);
			throw new NEDSSDAOSysException(sqlex.toString());
		} catch(Exception ex) {
			logger.fatal("Error while update into CT_contact_answer for logical delete, ctContactAnswerDT = " + ctContactAnswerDT.toString(), ex);
			throw new NEDSSSystemException(ex.toString());
		} finally {
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
	}
	
	
	private void logDelCTContactAnswerDTByAnswerUid(CTContactAnswerDT ctContactAnswerDT, CTContactDT ctContactDT){
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;

		logger.debug("LOGICALLY_DELETE_CT_CONTACT_ANSWER = " + LOGICALLY_DELETE_CT_CONTACT_ANSWER);
		try {
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(LOGICALLY_DELETE_CT_CONTACT_ANSWER_BY_ANSWER_UID);
			int i = 1;
			preparedStmt.setTimestamp(i++, ctContactDT.getLastChgTime()); //1
			preparedStmt.setLong(i++, ctContactAnswerDT.getLastChgUserId().longValue());//2 

			preparedStmt.setString(i++, "LOG_DEL"); //3
			preparedStmt.setTimestamp(i++, ctContactDT.getRecordStatusTime()); //4
			preparedStmt.setLong(i++, ctContactDT.getCtContactUid()); //5
			preparedStmt.setLong(i++, ctContactAnswerDT.getCtContactAnswerUid()); //6
			
			int resultCount = preparedStmt.executeUpdate();
			logger.debug("resultCount is " + resultCount);
		} catch(SQLException sqlex) {
			logger.fatal("SQLException while logDelCTContactAnswers - ctContactAnswerDT into CT_contact_answer: " + ctContactAnswerDT.toString(), sqlex);
			throw new NEDSSDAOSysException(sqlex.toString());
		} catch(Exception ex) {
			logger.fatal("Error while update into CT_contact_answer for logical delete, ctContactAnswerDT = " + ctContactAnswerDT.toString(), ex);
			throw new NEDSSSystemException(ex.toString());
		} finally {
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
	}
	
	private void removeCTContactAnswer(CTContactAnswerDT ctContactAnswerDT)	throws NEDSSDAOSysException, NEDSSSystemException {
		PreparedStatement preparedStmt = null;
		Connection dbConnection = null;

		try {
			logger.debug("DELETE_CT_CONTACT_ANSWER = "+ DELETE_CT_CONTACT_ANSWER);
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(DELETE_CT_CONTACT_ANSWER);
			preparedStmt.setLong(1,ctContactAnswerDT.getCtContactAnswerUid().longValue());
			preparedStmt.executeUpdate();
		} catch (SQLException sqlex) {
		    logger.fatal("SQLException while removeCTContactAnswer: " + ctContactAnswerDT.toString(), sqlex);
			throw new NEDSSDAOSysException(sqlex.toString());
		} finally {
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
	}
	
	public void insertCTContactAnswerHistoryDTCollection(CTContactVO oldCtContactVO, String fromAction) throws NEDSSSystemException {
		try {
			Collection<Object> oldCtContactAnswerDTCollection = new ArrayList<Object> ();
			if(oldCtContactVO.getCtContactAnswerDTMap() != null) {
				oldCtContactAnswerDTCollection.addAll(oldCtContactVO.getCtContactAnswerDTMap().values());
			}
			if(oldCtContactVO.getRepeatingAnswerDTMap()!=null){
				oldCtContactAnswerDTCollection.addAll(oldCtContactVO.getRepeatingAnswerDTMap().values());
			}
			if (oldCtContactAnswerDTCollection != null) {
				Iterator<Object> it = oldCtContactAnswerDTCollection.iterator();
				while (it.hasNext()) {
					Object obj=it.next();
					if(obj!=null && obj instanceof ArrayList<?> && ((ArrayList<Object>)obj).size()>0) {
						Iterator<CTContactAnswerDT> iter = ((ArrayList<CTContactAnswerDT>)obj).iterator();
						while(iter.hasNext()){
							CTContactAnswerDT ctContactAnswerDT = iter.next();
							insertCTContactAnswerHistory(ctContactAnswerDT, oldCtContactVO.getcTContactDT());
							if(fromAction!=null && fromAction.equalsIgnoreCase("update")){//ND-12425
								oldCtContactVO.getcTContactDT().setRecordStatusCd("LOG_DEL");
								logDelCTContactAnswerDT(ctContactAnswerDT, oldCtContactVO.getcTContactDT());
								removeCTContactAnswer(ctContactAnswerDT);
							}
							else//remove
								logDelCTContactAnswerDTByAnswerUid(ctContactAnswerDT, oldCtContactVO.getcTContactDT());
							
							
						}
					}
					else if(obj!=null && obj instanceof CTContactAnswerDT){
						CTContactAnswerDT ctContactAnswerDT = (CTContactAnswerDT)obj;
						insertCTContactAnswerHistory(ctContactAnswerDT, oldCtContactVO.getcTContactDT());

						if(fromAction!=null && fromAction.equalsIgnoreCase("update")){//ND-12425
							removeCTContactAnswer(ctContactAnswerDT);
						}
						else//remove
							logDelCTContactAnswerDTByAnswerUid(ctContactAnswerDT, oldCtContactVO.getcTContactDT());
					}
				}
			}
		} catch (NEDSSSystemException ex) {
			logger.fatal("Exception in insertCTContactAnswerHistoryDTCollection:  ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	private void insertCTContactAnswerHistory(CTContactAnswerDT ctContactAnswerDT, CTContactDT oldCtContactDT) throws NEDSSSystemException {
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		int resultCount = 0;
		logger.debug("INSERT_CT_CONTACT_ANSWER_HIST = " + INSERT_CT_CONTACT_ANSWER_HIST);
		try{
			dbConnection = getConnection();
		}catch(NEDSSSystemException nsex){
			logger.fatal("SQLException while obtaining database connection for insertCTContactAnswerHistory ", nsex);
			throw new NEDSSSystemException(nsex.toString());
		}
		try {
			preparedStmt = dbConnection
			.prepareStatement(INSERT_CT_CONTACT_ANSWER_HIST);
			int i = 1;

			preparedStmt.setLong(i++, ctContactAnswerDT.getCtContactAnswerUid().longValue()); // 1
			preparedStmt.setInt(i++, ctContactAnswerDT.getSeqNbr().intValue()); // 2
			preparedStmt.setString(i++, ctContactAnswerDT.getAnswerTxt()); // 3
			if (oldCtContactDT.getLastChgTime() == null)
				preparedStmt.setNull(i++, Types.TIMESTAMP); // 4
			else
				preparedStmt.setTimestamp(i++, oldCtContactDT.getLastChgTime()); // 4
			
			if (oldCtContactDT.getLastChgUserId() == null)// 5
				preparedStmt.setNull(i++, Types.INTEGER);
			else
				preparedStmt.setLong(i++, oldCtContactDT.getLastChgUserId().longValue()); // 5
			preparedStmt.setLong(i++, ctContactAnswerDT.getNbsQuestionUid().longValue());// 6
			preparedStmt.setLong(i++, ctContactAnswerDT.getCtContactUid().longValue());// 7
			preparedStmt.setInt(i++, ctContactAnswerDT.getNbsQuestionVersionCtrlNbr().intValue()); // 8
			preparedStmt.setInt(i++, oldCtContactDT.getVersionCtrlNbr().intValue());// 9
			preparedStmt.setString(i++, oldCtContactDT.getRecordStatusCd());// 10
			preparedStmt.setTimestamp(i++, oldCtContactDT.getRecordStatusTime());// 11
		    if(ctContactAnswerDT.getAnswerGroupSeqNbr()!=null)
		    	preparedStmt.setInt(i++, ctContactAnswerDT.getAnswerGroupSeqNbr().intValue()); //12
		    else
		    	preparedStmt.setNull(i++, Types.INTEGER); //12
			resultCount = preparedStmt.executeUpdate();
			logger.debug("resultCount is " + resultCount);
		} catch (SQLException sqlex) {
			logger.fatal("SQLException while inserting "
					+ "ctContactAnswerHistDT into CT_CONTACT_HISTORY: " + ctContactAnswerDT.toString(), sqlex);
			throw new NEDSSDAOSysException(sqlex.toString());
		} catch (Exception ex) {
			logger.fatal(
					"Error while inserting into CT_CONTACT_HISTORY, ctContactAnswerHistDT = "  + ctContactAnswerDT.toString(), ex);
			throw new NEDSSSystemException(ex.toString());
		} finally {
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
	}
}