package gov.cdc.nedss.act.interview.ejb.dao;
//
//import gov.cdc.nedss.act.ctcontact.dt.CTContactDT;
//import gov.cdc.nedss.act.ctcontact.vo.CTContactVO;
import gov.cdc.nedss.act.interview.dt.InterviewAnswerDT;
import gov.cdc.nedss.act.interview.dt.InterviewDT;
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

public class InterviewAnswerDAOImpl extends DAOBase{
	static final LogUtils logger = new LogUtils(InterviewAnswerDAOImpl.class.getName());
	
	
	private static final String SELECT_ANSWER_COLLECTION = "SELECT interview_answer_uid \"interviewAnswerUid\", seq_nbr \"seqNbr\",  answer_txt \"answerTxt\", last_chg_time \"lastChgTime\", last_chg_user_id \"lastChgUserId\", nbs_question_uid \"nbsQuestionUid\", act_uid \"interviewUid\", nbs_question_version_ctrl_nbr \"nbsQuestionVersionCtrlNbr\", answer_group_seq_nbr \"answerGroupSeqNbr\" FROM "+ DataTables.NBS_ANSWER_TABLE +" where act_uid = ? ORDER BY nbs_question_uid";
	private static final String INSERT_ANSWER = "INSERT INTO " + DataTables.NBS_ANSWER_TABLE + "(seq_nbr, answer_txt, last_chg_time, last_chg_user_id, nbs_question_uid, act_uid, nbs_question_version_ctrl_nbr, record_status_cd, record_status_time, answer_group_seq_nbr) VALUES(?,?,?,?,?,?,?,?,?,?)";
	private static final String UPDATE_ANSWER = "UPDATE "+ DataTables.NBS_ANSWER_TABLE +" SET seq_nbr=?, answer_txt=?, last_chg_time=?, last_chg_user_id=?, nbs_question_version_ctrl_nbr=?, record_status_cd=?, record_status_time=?, answer_group_seq_nbr=? WHERE interview_answer_uid=?";
	private static final String DELETE_ANSWER = "DELETE FROM "+ DataTables.NBS_ANSWER_TABLE + " WHERE interview_answer_uid= ?";
	private static final String LOGICALLY_DELETE_ANSWER = "UPDATE " + DataTables.NBS_ANSWER_TABLE + " SET last_chg_time=?, last_chg_user_id=?,  record_status_cd=?, record_status_time=? WHERE act_uid=?";
	private static final String INSERT_ANSWER_HIST = "INSERT INTO "	+ DataTables.NBS_ANSWER_HIST_TABLE + "(interview_answer_uid, seq_nbr, answer_txt, last_chg_time, last_chg_user_id, nbs_question_uid, act_uid, nbs_question_version_ctrl_nbr,  record_status_cd, record_status_time, answer_group_seq_nbr) VALUES(?,?,?,?,?,?,?,?,?,?,?)";


	@SuppressWarnings("unchecked")
	public Map<Object, Object> getInterviewAnswerDTCollection(Long interviewUid) throws NEDSSSystemException{
		InterviewAnswerDT  interviewAnswerDT  = new InterviewAnswerDT();
		ArrayList<Object>  InterviewAnswerDTCollection  = new ArrayList<Object> ();
		InterviewAnswerDTCollection.add(interviewUid);
		Map<Object, Object> interviewAnswerMap = new HashMap<Object, Object>();
		try
		{
			InterviewAnswerDTCollection  = (ArrayList<Object> )preparedStmtMethod(interviewAnswerDT, InterviewAnswerDTCollection, SELECT_ANSWER_COLLECTION, NEDSSConstants.SELECT);
			Iterator<Object> it = InterviewAnswerDTCollection.iterator();
			Long nbsQuestionUid = new Long(0);
			Collection<Object>  coll = new ArrayList<Object> ();
			while(it.hasNext()){
				InterviewAnswerDT intAnsDT = (InterviewAnswerDT)it.next();

				if((intAnsDT.getNbsQuestionUid().compareTo(nbsQuestionUid)==0) && intAnsDT.getSeqNbr()!=null && intAnsDT.getSeqNbr().intValue()>0){
					coll.add(intAnsDT);
				}
				else if(intAnsDT.getSeqNbr()!=null && intAnsDT.getSeqNbr().intValue()>0) {
					if(coll.size()>0) {
						interviewAnswerMap.put(nbsQuestionUid, coll);
						coll= new ArrayList<Object> ();
					}
					coll.add(intAnsDT);
				}
				else {
					if(coll.size()>0) {
						interviewAnswerMap.put(nbsQuestionUid, coll);
					}
					interviewAnswerMap.put(intAnsDT.getNbsQuestionUid(), intAnsDT);
					coll= new ArrayList<Object> ();
				}
				nbsQuestionUid = intAnsDT.getNbsQuestionUid();
				if(!it.hasNext() && coll.size()>0) {
					interviewAnswerMap.put(intAnsDT.getNbsQuestionUid(), coll);
				}
			}
		}
		 catch (Exception ex) {
			logger.fatal("Exception in getCTAnswerDTCollection:  ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return interviewAnswerMap;
	}
	
	@SuppressWarnings("unchecked")
	public Collection<Object> getSummaryInterviewDTCollection(Long interviewUid) throws NEDSSSystemException{
		InterviewAnswerDT interviewAnswerDT = new InterviewAnswerDT();
		ArrayList<Object> ctContactDTCollection = new ArrayList<Object> ();
		ctContactDTCollection.add(interviewUid);
		try {
			ctContactDTCollection  = (ArrayList<Object> )preparedStmtMethod(interviewAnswerDT, ctContactDTCollection, SELECT_ANSWER_COLLECTION, NEDSSConstants.SELECT);
		} catch (Exception ex) {
			logger.fatal("Exception in getSummaryInterviewDTCollection:  ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		
		return ctContactDTCollection;
	}
	
	public void storeInterviewAnswerDTCollection(Collection<Object> interviewAnswerDTColl, InterviewDT interviewDT) throws NEDSSSystemException{
		try {
			if (interviewAnswerDTColl != null){
				Iterator<Object> it  = interviewAnswerDTColl.iterator();
				while(it.hasNext()) {
					Object object = it.next();
				    if(object instanceof NbsAnswerDT)	{
				    	InterviewAnswerDT interviewAnswerDT = new InterviewAnswerDT((NbsAnswerDT) object);
						if(interviewAnswerDT.isItDirty()){
							updateInterviewAnswer(interviewAnswerDT, interviewDT);
						} else if(interviewAnswerDT.isItDelete()) {
							removeInterviewAnswer(interviewAnswerDT);
						}  else if(interviewAnswerDT.isItNew()) {
							insertInterviewAnswer(interviewAnswerDT, interviewDT);
						}
					}  else if(object instanceof ArrayList<?>) {
				   		Collection<?>  innerCollection  = (ArrayList<?> )object;
				   		Iterator<?> iter = innerCollection.iterator();
				   		while(iter.hasNext()){
				   			Object nextObject = iter.next();
				   			InterviewAnswerDT interviewAnswerDT1 = new InterviewAnswerDT((NbsAnswerDT)nextObject);
				   			if(interviewAnswerDT1.isItDirty()) {
				   				updateInterviewAnswer(interviewAnswerDT1,interviewDT);
							} else if(interviewAnswerDT1.isItDelete()) {
								removeInterviewAnswer(interviewAnswerDT1);
							} else if(interviewAnswerDT1.isItNew()) {
								insertInterviewAnswer(interviewAnswerDT1, interviewDT);
							}
				   		}
				   	}
				}
			}
		} catch(NEDSSSystemException ex) {
			logger.error("Exception in storeInterviewAnswerDTCollection:  ERROR =" + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}
	

	private void insertInterviewAnswer(InterviewAnswerDT interviewAnswerDT, InterviewDT interviewDT) throws  NEDSSSystemException {
			Connection dbConnection = null;
			PreparedStatement preparedStmt = null;
			int resultCount = 0;
			logger.debug("INSERT_ANSWER="+INSERT_ANSWER);
			dbConnection = getConnection();
			try{
			preparedStmt = dbConnection.prepareStatement(INSERT_ANSWER);
			int i = 1;
		    if(interviewAnswerDT.getSeqNbr()!= null)
		    	preparedStmt.setInt(i++, interviewAnswerDT.getSeqNbr().intValue());
		    else 
		    	preparedStmt.setNull(i++, Types.INTEGER); //1
		    preparedStmt.setString(i++, interviewAnswerDT.getAnswerTxt());//2
			preparedStmt.setTimestamp(i++, interviewDT.getLastChgTime()); //3
			if(interviewDT.getLastChgUserId()==null)
			    preparedStmt.setLong(i++, interviewDT.getAddUserId().longValue());//4
		    else
		    	preparedStmt.setLong(i++, interviewDT.getLastChgUserId().longValue()); //4
			preparedStmt.setLong(i++, interviewAnswerDT.getNbsQuestionUid().longValue()); //5
			preparedStmt.setLong(i++, interviewDT.getInterviewUid().longValue()); //6
			// inserting 1 for the question version control . This needs to be changed after the database is fixed
		    if(interviewAnswerDT.getNbsQuestionVersionCtrlNbr() == null)
		    	interviewAnswerDT.setNbsQuestionVersionCtrlNbr(new Integer(1));
		    preparedStmt.setInt(i++, interviewAnswerDT.getNbsQuestionVersionCtrlNbr().intValue()); //7
		    preparedStmt.setString(i++, interviewDT.getRecordStatusCd()); //8
		    preparedStmt.setTimestamp(i++, interviewDT.getRecordStatusTime()); //9
		    if(interviewAnswerDT.getAnswerGroupSeqNbr()!=null)
		    	preparedStmt.setInt(i++, interviewAnswerDT.getAnswerGroupSeqNbr().intValue()); //10
		    else
		    	preparedStmt.setNull(i++, Types.INTEGER); //10
		    
		    resultCount = preparedStmt.executeUpdate();
		    logger.debug("resultCount in insertInterviewAnswer is " + resultCount);
		}
		catch(SQLException sqlex)
		{
		    logger.fatal("SQLException while inserting interviewAnswerDT into Interview_answer: " + interviewAnswerDT.toString(), sqlex);
		    throw new NEDSSDAOSysException( sqlex.toString() );
		}
		catch(Exception ex)
		{
		    logger.fatal("Error while inserting into CT_contact_answer, interviewAnswerDT = " + interviewAnswerDT.toString(), ex);
		    throw new NEDSSSystemException(ex.toString());
		}
		finally
		{
		  closeStatement(preparedStmt);
		  releaseConnection(dbConnection);
		}

	}
	
	private void updateInterviewAnswer(InterviewAnswerDT interviewAnswerDT, InterviewDT interviewDT)throws  NEDSSSystemException {
			Connection dbConnection = null;
			PreparedStatement preparedStmt = null;
			int resultCount = 0;
			logger.debug("UPDATE_ANSWER = " + UPDATE_ANSWER);
			dbConnection = getConnection();
			try{
			preparedStmt = dbConnection.prepareStatement(UPDATE_ANSWER);
			int i = 1;
			preparedStmt.setInt(i++, interviewAnswerDT.getSeqNbr().intValue()); //1
		    preparedStmt.setString(i++, interviewAnswerDT.getAnswerTxt()); //2
		   	preparedStmt.setTimestamp(i++, interviewDT.getLastChgTime()); //3
		    preparedStmt.setLong(i++, interviewDT.getLastChgUserId().longValue());//4
		    if(interviewAnswerDT.getNbsQuestionVersionCtrlNbr() == null)
		    	interviewAnswerDT.setNbsQuestionVersionCtrlNbr(new Integer(1));
		    
		   preparedStmt.setInt(i++, interviewAnswerDT.getNbsQuestionVersionCtrlNbr().intValue()); //5
	       preparedStmt.setString(i++, interviewDT.getRecordStatusCd()); //6
		    preparedStmt.setTimestamp(i++, interviewDT.getRecordStatusTime()); //7
		    if(interviewAnswerDT.getAnswerGroupSeqNbr()!=null)
		    	preparedStmt.setInt(i++, interviewAnswerDT.getAnswerGroupSeqNbr().intValue()); //8
		    else
		    	preparedStmt.setNull(i++, Types.INTEGER); //8
		    preparedStmt.setLong(i++, interviewAnswerDT.getInterviewAnswerUid().longValue());//9
		    
		    resultCount = preparedStmt.executeUpdate();
			logger.debug("resultCount is " + resultCount);
		} catch(SQLException sqlex) {
		    logger.fatal("SQLException while updateCTContactAnswer interviewAnswerDT into CT_contact_answer:" + interviewAnswerDT.toString(), sqlex);
		    throw new NEDSSDAOSysException( sqlex.toString() );
		} catch(Exception ex) {
		    logger.fatal("Error while update into CT_contact_answer, interviewAnswerDT = " + interviewAnswerDT.toString(), ex);
		    throw new NEDSSSystemException(ex.toString());
		} finally {
		  closeStatement(preparedStmt);
		  releaseConnection(dbConnection);
		}
    }
	
		
	@SuppressWarnings("unused")
	public void logDelInterviewAnswerDT(InterviewDT interviewDT){
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;

		logger.debug("LOGICALLY_DELETE_ANSWER = " + LOGICALLY_DELETE_ANSWER);
		try {
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(LOGICALLY_DELETE_ANSWER);
			int i = 1;
			preparedStmt.setTimestamp(i++, interviewDT.getLastChgTime()); //1
			preparedStmt.setLong(i++, interviewDT.getLastChgUserId().longValue());//2 

			preparedStmt.setString(i++, interviewDT.getRecordStatusCd()); //3
			preparedStmt.setTimestamp(i++, interviewDT.getRecordStatusTime()); //4
			preparedStmt.setLong(i++, interviewDT.getInterviewUid()); //5

			int resultCount = preparedStmt.executeUpdate();
			logger.debug("resultCount is " + resultCount);
		} catch(SQLException sqlex) {
			logger.fatal("SQLException while logDelCTContactAnswers - interviewAnswerDT into CT_contact_answer: " + interviewDT.toString(), sqlex);
			throw new NEDSSDAOSysException(sqlex.toString());
		} catch(Exception ex) {
			logger.fatal("Error while update into CT_contact_answer for logical delete, interviewAnswerDT = " + interviewDT.toString(), ex);
			throw new NEDSSSystemException(ex.toString());
		} finally {
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
	}
	private void removeInterviewAnswer(InterviewAnswerDT interviewAnswerDT)	throws NEDSSDAOSysException, NEDSSSystemException {
		PreparedStatement preparedStmt = null;
		Connection dbConnection = null;

		try {
			logger.debug("DELETE_ANSWER = "+ DELETE_ANSWER);
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(DELETE_ANSWER);
			preparedStmt.setLong(1,interviewAnswerDT.getInterviewAnswerUid().longValue());
			preparedStmt.executeUpdate();
		} catch (SQLException sqlex) {
		    logger.fatal("SQLException while removeCTContactAnswer: " + interviewAnswerDT.toString(), sqlex);
			throw new NEDSSDAOSysException(sqlex.toString());
		} finally {
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
	}
	
	public void insertInterviewAnswerAnswerHistoryDTCollection(Collection<Object> oldInterviewAnswerDTCollection) throws NEDSSSystemException {
		try {
			if (oldInterviewAnswerDTCollection != null) {
				Iterator<Object> it = oldInterviewAnswerDTCollection.iterator();
				while (it.hasNext()) {
					Object obj=it.next();
					if(obj!=null && obj instanceof ArrayList<?> && ((ArrayList<Object>)obj).size()>0) {
						Iterator<InterviewAnswerDT> iter = ((ArrayList<InterviewAnswerDT>)obj).iterator();
						while(iter.hasNext()){
							InterviewAnswerDT interviewAnswerDT = iter.next();
							insertInterviewAnswerHistory(interviewAnswerDT);
						}
					}
					else if(obj!=null && obj instanceof InterviewAnswerDT){
						InterviewAnswerDT interviewAnswerDT = (InterviewAnswerDT)obj;
						insertInterviewAnswerHistory(interviewAnswerDT);
					}
				}
			}
		} catch (NEDSSSystemException ex) {
			logger.error("Exception in insertCTContactAnswerHistoryDTCollection:  ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	private void insertInterviewAnswerHistory(InterviewAnswerDT interviewAnswerDT) throws NEDSSSystemException {
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		int resultCount = 0;
		logger.debug("INSERT_ANSWER_HIST = " + INSERT_ANSWER_HIST);
		dbConnection = getConnection();
		try {
			preparedStmt = dbConnection
			.prepareStatement(INSERT_ANSWER_HIST);
			int i = 1;

			preparedStmt.setLong(i++, interviewAnswerDT.getInterviewAnswerUid().longValue()); // 1
			preparedStmt.setInt(i++, interviewAnswerDT.getSeqNbr().intValue()); // 2
			preparedStmt.setString(i++, interviewAnswerDT.getAnswerTxt()); // 3
			if (interviewAnswerDT.getLastChgTime() == null)
				preparedStmt.setNull(i++, Types.TIMESTAMP); // 4
			else
				preparedStmt.setTimestamp(i++, interviewAnswerDT.getLastChgTime()); // 4
			
			if (interviewAnswerDT.getLastChgUserId() == null)// 5
				preparedStmt.setNull(i++, Types.INTEGER);
			else
				preparedStmt.setLong(i++, interviewAnswerDT.getLastChgUserId().longValue()); // 5
			preparedStmt.setLong(i++, interviewAnswerDT.getNbsQuestionUid().longValue());// 6
			preparedStmt.setLong(i++, interviewAnswerDT.getInterviewUid().longValue());// 7nbs_question_version_ctrl_nbr,  record_status_cd, record_status_time, answer_group_seq_nbr
			preparedStmt.setInt(i++, interviewAnswerDT.getNbsQuestionVersionCtrlNbr().intValue()); // 8
//			preparedStmt.setInt(i++, oldInterviewDT.getVersionCtrlNbr().intValue());// 9
			preparedStmt.setString(i++, interviewAnswerDT.getRecordStatusCd());// 9
			preparedStmt.setTimestamp(i++, interviewAnswerDT.getRecordStatusTime());// 10
		    if(interviewAnswerDT.getAnswerGroupSeqNbr()!=null)
		    	preparedStmt.setInt(i++, interviewAnswerDT.getAnswerGroupSeqNbr().intValue()); //11
		    else
		    	preparedStmt.setNull(i++, Types.INTEGER); //11
			resultCount = preparedStmt.executeUpdate();
			logger.debug("resultCount is " + resultCount);
		} catch (SQLException sqlex) {
			logger.fatal("SQLException while inserting "
					+ "ctContactAnswerHistDT into HISTORY: " + interviewAnswerDT.toString(), sqlex);
			throw new NEDSSDAOSysException(sqlex.toString());
		} catch (Exception ex) {
			logger.fatal(
					"Error while inserting into HISTORY, interviewAnswerHistDT = "  + interviewAnswerDT.toString(), ex);
			throw new NEDSSSystemException(ex.toString());
		} finally {
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
	}




}