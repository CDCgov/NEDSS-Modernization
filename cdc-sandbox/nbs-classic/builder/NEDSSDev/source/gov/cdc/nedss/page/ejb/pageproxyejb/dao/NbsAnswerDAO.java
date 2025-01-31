package gov.cdc.nedss.page.ejb.pageproxyejb.dao;

import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.page.ejb.pageproxyejb.dt.NbsAnswerDT;
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Name: NbsAnswerDAO.java 
 * @Description: DAO Object for answer objects. 
 * @Copyright (c) 2014 
 * @Company: Leidos
 * @author Pradeep Sharma
 */
public class NbsAnswerDAO extends DAOBase
{
	static final LogUtils logger = new LogUtils(NbsAnswerDAO.class.getName());
		
	private static final String SELECT_NBS_ANSWER = "SELECT nbs_answer_uid \"nbsAnswerUid\", seq_nbr \"seqNbr\",  answer_txt \"answerTxt\", ca.last_chg_time \"lastChgTime\", ca.last_chg_user_id \"lastChgUserId\", ca.nbs_question_uid \"nbsQuestionUid\", act_uid \"actUid\", nbs_question_version_ctrl_nbr \"nbsQuestionVersionCtrlNbr\", record_status_cd \"recordStatusCd\", record_status_time \"recordStatusTime\", answer_group_seq_nbr \"answerGroupSeqNbr\"  ";
	private static final String SELECT_NBS_ANSWER_COLLECTION = SELECT_NBS_ANSWER +  " FROM "+ DataTables.NBS_ANSWER_TABLE + " ca where ca.act_uid = ? ORDER BY nbs_question_uid";
	private static final String INSERT_NBS_ANSWER = "INSERT INTO " + DataTables.NBS_ANSWER_TABLE + "(seq_nbr, answer_txt, last_chg_time, last_chg_user_id, nbs_question_uid, act_uid, nbs_question_version_ctrl_nbr, record_status_cd, record_status_time, answer_group_seq_nbr) VALUES(?,?,?,?,?,?,?,?,?,?)";
	private static final String UPDATE_NBS_ANSWER = "UPDATE "+ DataTables.NBS_ANSWER_TABLE +" SET seq_nbr=?, answer_txt=?, last_chg_time=?, last_chg_user_id=?, nbs_question_version_ctrl_nbr=?, record_status_cd=?, record_status_time=?, answer_group_seq_nbr=? WHERE NBS_answer_uid=?";
	private static final String DELETE_NBS_ANSWER = "DELETE FROM "+ DataTables.NBS_ANSWER_TABLE + " WHERE nbs_answer_uid= ?";
	private static final String LOGICALLY_DELETE_NBS_ANSWER = "UPDATE " + DataTables.NBS_ANSWER_TABLE + " SET last_chg_time=?, last_chg_user_id=?,  record_status_cd=?, record_status_time=? WHERE act_uid=?";
	private static final String INSERT_NBS_ANSWER_HIST = "INSERT INTO "	+ DataTables.NBS_ANSWER_HIST_TABLE + "(nbs_answer_uid, seq_nbr, answer_txt, last_chg_time, last_chg_user_id, nbs_question_uid, act_uid, nbs_question_version_ctrl_nbr,  record_status_cd, record_status_time, answer_group_seq_nbr) VALUES(?,?,?,?,?,?,?,?,?,?,?)";

	public NbsAnswerDT getNbsAnswerDT(Long actUid, String questionIdentifier)
	{
	    NbsAnswerDT nbsAnswerDT = new NbsAnswerDT();
	    ArrayList<Object> pageAnswerDTCollection = new ArrayList<Object>();
	    try
	    {
	        pageAnswerDTCollection.add(actUid);
	        pageAnswerDTCollection.add(questionIdentifier);
	        String sql = SELECT_NBS_ANSWER + "  FROM " + DataTables.NBS_ANSWER_TABLE + " ca , Nbs_Question nq WHERE act_uid = ? AND nq.question_identifier = ? AND ca.nbs_question_uid = nq.nbs_question_uid ";
	        pageAnswerDTCollection = (ArrayList<Object>) preparedStmtMethod(nbsAnswerDT, pageAnswerDTCollection,
                    sql, NEDSSConstants.SELECT);
	        
	        if( pageAnswerDTCollection != null && pageAnswerDTCollection.size() == 1 )
	            return (NbsAnswerDT) pageAnswerDTCollection.get(0);
	    }
	    catch(Exception e)
	    {
	    	logger.fatal("Exception = "+e.getMessage(), e);
	    	throw new NEDSSSystemException(e.toString(), e);
	    }
	    return null;
	}
	  
    public Map<Object, Object> getPageAnswerDTMaps(Long actUid) throws NEDSSSystemException
    {
        NbsAnswerDT nbsAnswerDT = new NbsAnswerDT();
        ArrayList<Object> pageAnswerDTCollection = new ArrayList<Object>();
        pageAnswerDTCollection.add(actUid);
        Map<Object, Object> nbsReturnAnswerMap = new HashMap<Object, Object>();
        Map<Object, Object> nbsAnswerMap = new HashMap<Object, Object>();
        Map<Object, Object> nbsRepeatingAnswerMap = new HashMap<Object, Object>();
        try
        {
            pageAnswerDTCollection = (ArrayList<Object>) preparedStmtMethod(nbsAnswerDT, pageAnswerDTCollection,
                    SELECT_NBS_ANSWER_COLLECTION, NEDSSConstants.SELECT);
            Iterator<Object> it = pageAnswerDTCollection.iterator();
            Long nbsQuestionUid = new Long(0);
            Collection<Object> coll = new ArrayList<Object>();
            while (it.hasNext())
            {
                NbsAnswerDT pageAnsDT = (NbsAnswerDT) it.next();

                if (pageAnsDT.getAnswerGroupSeqNbr() != null && pageAnsDT.getAnswerGroupSeqNbr() > -1)
                {
                    if (nbsRepeatingAnswerMap.get(pageAnsDT.getNbsQuestionUid()) == null)
                    {
                        Collection collection = new ArrayList();
                        collection.add(pageAnsDT);
                        nbsRepeatingAnswerMap.put(pageAnsDT.getNbsQuestionUid(), collection);
                    }
                    else
                    {
                        Collection collection = (Collection) nbsRepeatingAnswerMap.get(pageAnsDT.getNbsQuestionUid());
                        collection.add(pageAnsDT);
                        nbsRepeatingAnswerMap.put(pageAnsDT.getNbsQuestionUid(), collection);
                    }
                }
                else if ((pageAnsDT.getNbsQuestionUid().compareTo(nbsQuestionUid) == 0)
                        && pageAnsDT.getSeqNbr() != null && pageAnsDT.getSeqNbr().intValue() > 0)
                {
                    coll.add(pageAnsDT);
                }
                else if (pageAnsDT.getSeqNbr() != null && pageAnsDT.getSeqNbr().intValue() > 0)
                {
                    if (coll.size() > 0)
                    {
                        nbsAnswerMap.put(nbsQuestionUid, coll);
                        coll = new ArrayList<Object>();
                    }
                    coll.add(pageAnsDT);
                }
                else
                {
                    if (coll.size() > 0)
                    {
                        nbsAnswerMap.put(nbsQuestionUid, coll);
                    }
                    nbsAnswerMap.put(pageAnsDT.getNbsQuestionUid(), pageAnsDT);
                    coll = new ArrayList<Object>();
                }
                nbsQuestionUid = pageAnsDT.getNbsQuestionUid();
                if (!it.hasNext() && coll.size() > 0)
                {
                    nbsAnswerMap.put(pageAnsDT.getNbsQuestionUid(), coll);
                }
            }
        }
        catch (Exception ex)
        {
            logger.fatal("NbsAnswerDAO:Exception in getPageAnswerDTMaps:  ERROR = " + ex.getMessage(), ex);
            throw new NEDSSSystemException(ex.toString());
        }
        nbsReturnAnswerMap.put(NEDSSConstants.NON_REPEATING_QUESTION, nbsAnswerMap);
        nbsReturnAnswerMap.put(NEDSSConstants.REPEATING_QUESTION, nbsRepeatingAnswerMap);

        return nbsReturnAnswerMap;
    }


	@SuppressWarnings("unchecked")
	public Collection<Object> getAnswerDTCollectionForRemoveToHist(Long answerUid) throws NEDSSSystemException{
		NbsAnswerDT  answerDT  = new NbsAnswerDT();
		ArrayList<Object>  answerDTCollection  = new ArrayList<Object> ();
		answerDTCollection.add(answerUid);
		Map<Object, Object> answerMap = new HashMap<Object, Object>();
		try
		{
			answerDTCollection  = (ArrayList<Object> )preparedStmtMethod(answerDT, answerDTCollection, SELECT_NBS_ANSWER_COLLECTION, NEDSSConstants.SELECT);
		}
		 catch (Exception ex) {
			logger.fatal("NbsAnswerDAO:getAnswerDTCollection -Exception in getAnswerDTCollectionForRemoveToHist:  ERROR = " + ex.getMessage(), ex);
					throw new NEDSSSystemException(ex.toString());
		}
		return answerDTCollection;
	}
	
	@SuppressWarnings("unchecked")
	public Collection<Object> getSummaryRootDTInterfaceCollection(Long answerUid) throws NEDSSSystemException{
		NbsAnswerDT answerDT = new NbsAnswerDT();
		ArrayList<Object> ctContactDTCollection = new ArrayList<Object> ();
		ctContactDTCollection.add(answerUid);
		try {
			ctContactDTCollection  = (ArrayList<Object> )preparedStmtMethod(answerDT, ctContactDTCollection, SELECT_NBS_ANSWER_COLLECTION, NEDSSConstants.SELECT);
		} catch (Exception ex) {
			logger.fatal("NbsAnswerDAO.getSummaryRootDTInterfaceCollection :Exception in getSummaryRootDTInterfaceCollection:  ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		
		return ctContactDTCollection;
	}
	
	public void storeAnswerDTCollection(Collection<Object> answerDTColl, RootDTInterface interfaceDT) throws NEDSSSystemException{
		try {
			if (answerDTColl != null){
				Iterator<Object> it  = answerDTColl.iterator();
				while(it.hasNext()) {
					Object object = it.next();
				    if(object instanceof NbsAnswerDT)	{
				    	NbsAnswerDT answerDT = new NbsAnswerDT((NbsAnswerDT) object);
						if(answerDT.isItDirty()){
							updateAnswer(answerDT, interfaceDT);
						} else if(answerDT.isItDelete()) {
							removeAnswer(answerDT);
						}  else if(answerDT.isItNew()) {
							insertAnswer(answerDT, interfaceDT);
						}
					}  else if(object instanceof ArrayList<?>) {
				   		Collection<?>  innerCollection  = (ArrayList<?> )object;
				   		Iterator<?> iter = innerCollection.iterator();
				   		while(iter.hasNext()){
				   			Object nextObject = iter.next();
				   			NbsAnswerDT answerDT1 = new NbsAnswerDT((NbsAnswerDT)nextObject);
				   			if(answerDT1.isItDirty()) {
				   				updateAnswer(answerDT1,interfaceDT);
							} else if(answerDT1.isItDelete()) {
								removeAnswer(answerDT1);
							} else if(answerDT1.isItNew()) {
								insertAnswer(answerDT1, interfaceDT);
							}
				   		}
				   	}
				}
			}
		} catch(NEDSSSystemException ex) {
			logger.error("NbsAnswerDAO.storeAnswerDTCollection:Exception in storeAnswerDTCollection:  ERROR =" + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}
	

	private void insertAnswer(NbsAnswerDT answerDT, RootDTInterface interfaceDT) throws  NEDSSSystemException {
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		int resultCount = 0;
		logger.debug("INSERT_NBS_ANSWER="+INSERT_NBS_ANSWER);
		try{
			dbConnection = getConnection();
		}catch(NEDSSSystemException nsex){
			logger.fatal("SQLException while obtaining database connection for insertAnswer ", nsex);
			throw new NEDSSSystemException(nsex.toString());
		}
		try{
			preparedStmt = dbConnection.prepareStatement(INSERT_NBS_ANSWER);
			int i = 1;
		    if(answerDT.getSeqNbr()!= null)
		    	preparedStmt.setInt(i++, answerDT.getSeqNbr().intValue());
		    else 
		    	preparedStmt.setNull(i++, Types.INTEGER); //1
		    preparedStmt.setString(i++, answerDT.getAnswerTxt());//2
			preparedStmt.setTimestamp(i++, interfaceDT.getLastChgTime()); //3
			if(interfaceDT.getLastChgUserId()==null)
			    preparedStmt.setLong(i++, interfaceDT.getAddUserId().longValue());//4
		    else
		    	preparedStmt.setLong(i++, interfaceDT.getLastChgUserId().longValue()); //4
			preparedStmt.setLong(i++, answerDT.getNbsQuestionUid().longValue()); //5
			preparedStmt.setLong(i++, interfaceDT.getUid().longValue()); //6
			// inserting 1 for the question version control . This needs to be changed after the database is fixed
		    if(answerDT.getNbsQuestionVersionCtrlNbr() == null)
		    	answerDT.setNbsQuestionVersionCtrlNbr(new Integer(1));
		    preparedStmt.setInt(i++, answerDT.getNbsQuestionVersionCtrlNbr().intValue()); //7
		    preparedStmt.setString(i++, interfaceDT.getRecordStatusCd()); //8
		    preparedStmt.setTimestamp(i++, interfaceDT.getRecordStatusTime()); //9
		    if(answerDT.getAnswerGroupSeqNbr()!=null)
		    	preparedStmt.setInt(i++, answerDT.getAnswerGroupSeqNbr().intValue()); //10
		    else
		    	preparedStmt.setNull(i++, Types.INTEGER); //10
		    
		    resultCount = preparedStmt.executeUpdate();
		    logger.debug("resultCount in insertAnswer is " + resultCount);
		}
		catch(SQLException sqlex)
		{
		    logger.fatal("NbsAnswerDAO.insertAnswer:SQLException while inserting answerDT into NBS_answer: " + answerDT.toString(), sqlex);
		    throw new NEDSSDAOSysException( sqlex.toString() );
		}
		catch(Exception ex)
		{
		    logger.fatal("NbsAnswerDAO.insertAnswer:Error while inserting into insertAnswer, answerDT = " + answerDT.toString(), ex);
		    throw new NEDSSSystemException(ex.toString());
		}
		finally
		{
		  closeStatement(preparedStmt);
		  releaseConnection(dbConnection);
		}

	}
	
	private void updateAnswer(NbsAnswerDT answerDT, RootDTInterface interfaceDT)throws  NEDSSSystemException {
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		int resultCount = 0;
		logger.debug("UPDATE_NBS_ANSWER = " + UPDATE_NBS_ANSWER);
		try{
			dbConnection = getConnection();
		}catch(NEDSSSystemException nsex){
			logger.fatal("SQLException while obtaining database connection for updateAnswer ", nsex);
			throw new NEDSSSystemException(nsex.toString());
		}
		try{
			preparedStmt = dbConnection.prepareStatement(UPDATE_NBS_ANSWER);
			int i = 1;
			preparedStmt.setInt(i++, answerDT.getSeqNbr().intValue()); //1
		    preparedStmt.setString(i++, answerDT.getAnswerTxt()); //2
		   	preparedStmt.setTimestamp(i++, interfaceDT.getLastChgTime()); //3
		    preparedStmt.setLong(i++, interfaceDT.getLastChgUserId().longValue());//4
		    if(answerDT.getNbsQuestionVersionCtrlNbr() == null)
		    	answerDT.setNbsQuestionVersionCtrlNbr(new Integer(1));
		    
		   preparedStmt.setInt(i++, answerDT.getNbsQuestionVersionCtrlNbr().intValue()); //5
	       preparedStmt.setString(i++, interfaceDT.getRecordStatusCd()); //6
		    preparedStmt.setTimestamp(i++, interfaceDT.getRecordStatusTime()); //7
		    if(answerDT.getAnswerGroupSeqNbr()!=null)
		    	preparedStmt.setInt(i++, answerDT.getAnswerGroupSeqNbr().intValue()); //8
		    else
		    	preparedStmt.setNull(i++, Types.INTEGER); //8
		    preparedStmt.setLong(i++, answerDT.getNbsAnswerUid().longValue());//9
		    
		    resultCount = preparedStmt.executeUpdate();
			logger.debug("resultCount is " + resultCount);
		} catch(SQLException sqlex) {
		    logger.fatal("NbsAnswerDAO.updateAnswer:SQLException while updateAnswer answerDT into CT_contact_answer:" + answerDT.toString(), sqlex);
		    throw new NEDSSDAOSysException( sqlex.toString() );
		} catch(Exception ex) {
		    logger.fatal("NbsAnswerDAO.updateAnswer:Error while update into updateAnswer, answerDT = " + answerDT.toString(), ex);
		    throw new NEDSSSystemException(ex.toString());
		} finally {
		  closeStatement(preparedStmt);
		  releaseConnection(dbConnection);
		}
    }
	
		
	@SuppressWarnings("unused")
	public void logDelAnswerDT(RootDTInterface interfaceDT){
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;

		logger.debug("LOGICALLY_DELETE_NBS_ANSWER = " + LOGICALLY_DELETE_NBS_ANSWER);
		try {
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(LOGICALLY_DELETE_NBS_ANSWER);
			int i = 1;
			preparedStmt.setTimestamp(i++, interfaceDT.getLastChgTime()); //1
			preparedStmt.setLong(i++, interfaceDT.getLastChgUserId().longValue());//2 

			preparedStmt.setString(i++, interfaceDT.getRecordStatusCd()); //3
			preparedStmt.setTimestamp(i++, interfaceDT.getRecordStatusTime()); //4
			preparedStmt.setLong(i++, interfaceDT.getUid()); //5

			int resultCount = preparedStmt.executeUpdate();
			logger.debug("resultCount is " + resultCount);
		} catch(SQLException sqlex) {
			logger.fatal("NbsAnswerDAO.logDelAnswerDT:SQLException while logDelAnswerDT - : " + interfaceDT.toString(), sqlex);
			throw new NEDSSDAOSysException(sqlex.toString());
		} catch(Exception ex) {
			logger.fatal("NbsAnswerDAO.logDelAnswerDT:Error while update into logDelAnswerDT for logical delete, answerDT = " + interfaceDT.toString(), ex);
			throw new NEDSSSystemException(ex.toString());
		} finally {
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
	}
	private void removeAnswer(NbsAnswerDT answerDT)	throws NEDSSDAOSysException, NEDSSSystemException {
		PreparedStatement preparedStmt = null;
		Connection dbConnection = null;

		try {
			logger.debug("DELETE_NBS_ANSWER = "+ DELETE_NBS_ANSWER);
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(DELETE_NBS_ANSWER);
			preparedStmt.setLong(1,answerDT.getNbsAnswerUid().longValue());
			preparedStmt.executeUpdate();
		} catch (SQLException sqlex) {
			logger.fatal("SQLException  = "+sqlex.getMessage(), sqlex);
		    logger.fatal("NbsAnswerDAO.removeAnswer:SQLException while removeAnswer: " + answerDT.toString(), sqlex);
			throw new NEDSSDAOSysException(sqlex.toString());
		} finally {
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
	}
	
	public void insertAnswerHistoryDTCollection(Collection<Object> oldAnswerDTCollection) throws NEDSSSystemException {
		try {
			if (oldAnswerDTCollection != null) {
				Iterator<Object> it = oldAnswerDTCollection.iterator();
				while (it.hasNext()) {
					Object obj=it.next();
					if(obj!=null && obj instanceof ArrayList<?> && ((ArrayList<Object>)obj).size()>0) {
						Iterator<NbsAnswerDT> iter = ((ArrayList<NbsAnswerDT>)obj).iterator();
						while(iter.hasNext()){
							NbsAnswerDT answerDT = iter.next();
							removeAnswer(answerDT);
							insertAnswerHistory(answerDT);
						}
					}
					else if(obj!=null && obj instanceof NbsAnswerDT){
						NbsAnswerDT answerDT = (NbsAnswerDT)obj;
						removeAnswer(answerDT);
						insertAnswerHistory(answerDT);
					}
				}
			}
		} catch (NEDSSSystemException ex) {
			logger.error("NbsAnswerDAO.insertAnswerHistoryDTCollection:Exception in insertAnswerHistoryDTCollection:  ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	private void insertAnswerHistory(NbsAnswerDT answerDT) throws NEDSSSystemException {
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		int resultCount = 0;
		logger.debug("INSERT_NBS_ANSWER_HIST = " + INSERT_NBS_ANSWER_HIST);
		try{
			dbConnection = getConnection();
		}catch(NEDSSSystemException nsex){
			logger.fatal("SQLException while obtaining database connection for insertAnswerHistory ", nsex);
			throw new NEDSSSystemException(nsex.toString());
		}
		try {
			preparedStmt = dbConnection
			.prepareStatement(INSERT_NBS_ANSWER_HIST);
			int i = 1;

			preparedStmt.setLong(i++, answerDT.getNbsAnswerUid().longValue()); // 1
			preparedStmt.setInt(i++, answerDT.getSeqNbr().intValue()); // 2
			preparedStmt.setString(i++, answerDT.getAnswerTxt()); // 3
			if (answerDT.getLastChgTime() == null)
				preparedStmt.setNull(i++, Types.TIMESTAMP); // 4
			else
				preparedStmt.setTimestamp(i++, answerDT.getLastChgTime()); // 4
			
			if (answerDT.getLastChgUserId() == null)// 5
				preparedStmt.setNull(i++, Types.INTEGER);
			else
				preparedStmt.setLong(i++, answerDT.getLastChgUserId().longValue()); // 5
			preparedStmt.setLong(i++, answerDT.getNbsQuestionUid().longValue());// 6
			preparedStmt.setLong(i++, answerDT.getActUid().longValue());// 7
			preparedStmt.setInt(i++, answerDT.getNbsQuestionVersionCtrlNbr().intValue()); // 8
			preparedStmt.setString(i++, answerDT.getRecordStatusCd());// 9
			preparedStmt.setTimestamp(i++, answerDT.getRecordStatusTime());// 10
		    if(answerDT.getAnswerGroupSeqNbr()!=null)
		    	preparedStmt.setInt(i++, answerDT.getAnswerGroupSeqNbr().intValue()); //11
		    else
		    	preparedStmt.setNull(i++, Types.INTEGER); //11
			resultCount = preparedStmt.executeUpdate();
			logger.debug("resultCount is " + resultCount);
		} catch (SQLException sqlex) {
			logger.fatal("insertAnswerHistory:SQLException while inserting "
					+ "insertAnswerHistory into NBS_HISTORY: " + answerDT.toString(), sqlex);
			throw new NEDSSDAOSysException(sqlex.toString());
		} catch (Exception ex) {
			logger.fatal(
					"NbsAnswerDAO.insertAnswerHistory: Error while inserting into NBS_HISTORY, answerHistDT = "  + answerDT.toString(), ex);
			throw new NEDSSSystemException(ex.toString());
		} finally {
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
	}




}