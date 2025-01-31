package gov.cdc.nedss.pam.dao;

import gov.cdc.nedss.act.publichealthcase.vo.PublicHealthCaseVO;
import gov.cdc.nedss.act.sqlscript.WumSqlQuery;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.pam.act.NbsCaseAnswerDT;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.DataTables;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Name: NbsCaseAnswerDAO.java Description: DAO Object for Pam answers. Copyright:
 * Copyright (c) 2008 Company: Computer Sciences Corporation
 * 
 * @author Pradeep Sharma
 * @update: Pradeep Sharma: code refactored to change name from NbsAnswerDAO to NbsCaseAnswerDAO (Release 4.5)
 */

public class NbsCaseAnswerDAO extends DAOBase
{
    static final LogUtils       logger                       = new LogUtils(NbsCaseAnswerDAO.class.getName());
    private final String        SELECT_NBS_ANSWER_COLLECTION = "SELECT nbs_case_answer_uid \"nbsCaseAnswerUid\", seq_nbr \"seqNbr\", add_time \"addTime\", add_user_id \"addUserId\", answer_txt \"answerTxt\", last_chg_time \"lastChgTime\", last_chg_user_id \"lastChgUserId\", nbs_question_uid \"nbsQuestionUid\", act_uid \"actUid\", nbs_question_version_ctrl_nbr \"nbsQuestionVersionCtrlNbr\", answer_large_txt \"answerLargeTxt\",nbs_table_metadata_uid \"nbsTableMetadataUid\", answer_group_seq_nbr \"answerGroupSeqNbr\"	 FROM "
                                                                     + DataTables.NBS_CASE_ANSWER_TABLE
                                                                     + " where act_uid=? order by nbs_question_uid, answer_group_seq_nbr";
    private final String        INSERT_NBS_ANSWER            = "INSERT INTO "
                                                                     + DataTables.NBS_CASE_ANSWER_TABLE
                                                                     + "(seq_nbr, add_time, add_user_id, answer_txt, last_chg_time, last_chg_user_id, nbs_question_uid, act_uid, nbs_question_version_ctrl_nbr, record_status_cd, record_status_time, nbs_table_metadata_uid, answer_group_seq_nbr) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private final String        UPDATE_NBS_ANSWER            = "UPDATE  "
                                                                     + DataTables.NBS_CASE_ANSWER_TABLE
                                                                     + " SET  seq_nbr=?,  answer_txt=?, last_chg_time=?, last_chg_user_id=?, nbs_question_version_ctrl_nbr=?, record_status_cd=?, record_status_time=?, nbs_table_metadata_uid=?, answer_group_seq_nbr=? WHERE nbs_case_answer_uid=?";
    
    private final String        UPDATE_NBS_ANSWER_QUESTION_UID            = "UPDATE  "
            														+ DataTables.NBS_CASE_ANSWER_TABLE
            														+ " SET  seq_nbr=?,  answer_txt=?, last_chg_time=?, last_chg_user_id=?, nbs_question_version_ctrl_nbr=?, record_status_cd=?, record_status_time=?, nbs_table_metadata_uid=?, answer_group_seq_nbr=?, nbs_question_uid=? WHERE nbs_case_answer_uid=?";
    
    private static final String DELETE_NBS_ANSWER            = "DELETE from  " + DataTables.NBS_CASE_ANSWER_TABLE
                                                                     + " where nbs_case_answer_uid= ?";
    private static final String LOGICALLY_DELETE_NBS_ANSWER  = "UPDATE  "
                                                                     + DataTables.NBS_CASE_ANSWER_TABLE
                                                                     + " SET  last_chg_time=?, last_chg_user_id=?,  record_status_cd=?, record_status_time=? WHERE nbs_case_answer_uid=?";

    /*
     * gets the NbsAnswerDT Collection<Object> Object for a given
     * publicHealthCaseUID
     * 
     * @return Collection<Object> of NbsAnswerDT
     */
    @SuppressWarnings("unchecked")
    public Map<Object, Object> getPamAnswerDTMaps(Long publicHealthCaseUID) throws NEDSSSystemException
    {
        NbsCaseAnswerDT nbsAnswerDT = new NbsCaseAnswerDT();
        ArrayList<Object> PamAnswerDTCollection = new ArrayList<Object>();
        PamAnswerDTCollection.add(publicHealthCaseUID);
        Map<Object, Object> nbsReturnAnswerMap = new HashMap<Object, Object>();
        Map<Object, Object> nbsAnswerMap = new HashMap<Object, Object>();
        Map<Object, Object> nbsRepeatingAnswerMap = new HashMap<Object, Object>();
        try
        {
            PamAnswerDTCollection = (ArrayList<Object>) preparedStmtMethod(nbsAnswerDT, PamAnswerDTCollection,
                    SELECT_NBS_ANSWER_COLLECTION, NEDSSConstants.SELECT);
            Iterator<Object> it = PamAnswerDTCollection.iterator();
            Long nbsQuestionUid = new Long(0);
            Collection<Object> coll = new ArrayList<Object>();
            while (it.hasNext())
            {
                NbsCaseAnswerDT pamAnsDT = (NbsCaseAnswerDT) it.next();
                
				if (pamAnsDT.getNbsQuestionUid() != null
						&& nbsQuestionUid.longValue() != 0
						&& pamAnsDT.getNbsQuestionUid().longValue() != nbsQuestionUid
								.longValue() && coll.size() > 0) {
					nbsAnswerMap.put(nbsQuestionUid, coll);
					coll = new ArrayList<Object>();
				}

                if (pamAnsDT.getAnswerGroupSeqNbr() != null && pamAnsDT.getAnswerGroupSeqNbr() > -1)
                {
                    if (nbsRepeatingAnswerMap.get(pamAnsDT.getNbsQuestionUid()) == null)
                    {
                        Collection collection = new ArrayList();
                        collection.add(pamAnsDT);
                        nbsRepeatingAnswerMap.put(pamAnsDT.getNbsQuestionUid(), collection);
                    }
                    else
                    {
                        Collection collection = (Collection) nbsRepeatingAnswerMap.get(pamAnsDT.getNbsQuestionUid());
                        collection.add(pamAnsDT);
                        nbsRepeatingAnswerMap.put(pamAnsDT.getNbsQuestionUid(), collection);
                    }
                }
                else if ((pamAnsDT.getNbsQuestionUid().compareTo(nbsQuestionUid) == 0) && pamAnsDT.getSeqNbr() != null
                        && pamAnsDT.getSeqNbr().intValue() > 0)
                {
                    coll.add(pamAnsDT);
                }
                else if (pamAnsDT.getSeqNbr() != null && pamAnsDT.getSeqNbr().intValue() > 0)
                {
                    if (coll.size() > 0)
                    {
                        nbsAnswerMap.put(nbsQuestionUid, coll);
                        coll = new ArrayList<Object>();
                    }
                    coll.add(pamAnsDT);
                }
                else
                {
                    if (coll.size() > 0)
                    {
                        nbsAnswerMap.put(nbsQuestionUid, coll);
                    }
                    nbsAnswerMap.put(pamAnsDT.getNbsQuestionUid(), pamAnsDT);
                    coll = new ArrayList<Object>();
                }
                nbsQuestionUid = pamAnsDT.getNbsQuestionUid();
                if (!it.hasNext() && coll.size() > 0)
                {
                    nbsAnswerMap.put(pamAnsDT.getNbsQuestionUid(), coll);
                }
            }
        }
        catch (Exception ex)
        {
            logger.fatal("Exception in getPamAnswerDTCollection:  ERROR = " + ex.getMessage(), ex);
            throw new NEDSSSystemException(ex.toString());
        }
        nbsReturnAnswerMap.put(NEDSSConstants.NON_REPEATING_QUESTION, nbsAnswerMap);
        nbsReturnAnswerMap.put(NEDSSConstants.REPEATING_QUESTION, nbsRepeatingAnswerMap);
        if(nbsAnswerMap!=null) {
		logger.debug("NbsCaseAnswerDAO nbsAnswerMap Size = "+nbsAnswerMap.size());
		logger.debug("NbsCaseAnswerDAO nbsAnswerMap Values = "+nbsAnswerMap.toString());
        }
		if(nbsRepeatingAnswerMap!=null) {
		logger.debug("NbsCaseAnswerDAO nbsRepeatingAnswerMap Size = "+nbsRepeatingAnswerMap.size());
		logger.debug("NbsCaseAnswerDAO nbsRepeatingAnswerMap Values = "+nbsRepeatingAnswerMap.toString());
		}
        return nbsReturnAnswerMap;
    }

    @SuppressWarnings("unchecked")
    public Collection<Object> getSummaryPamAnswerDTCollection(Long publicHealthCaseUID) throws NEDSSSystemException
    {
        NbsCaseAnswerDT nbsAnswerDT = new NbsCaseAnswerDT();
        ArrayList<Object> PamAnswerDTCollection = new ArrayList<Object>();
        PamAnswerDTCollection.add(publicHealthCaseUID);
        try
        {
            PamAnswerDTCollection = (ArrayList<Object>) preparedStmtMethod(nbsAnswerDT, PamAnswerDTCollection,
                    SELECT_NBS_ANSWER_COLLECTION, NEDSSConstants.SELECT);
        }
        catch (Exception ex)
        {
            logger.fatal("Exception in getPamAnswerDTCollection:  ERROR = " + ex.getMessage(), ex);
            throw new NEDSSSystemException(ex.toString());
        }
        return PamAnswerDTCollection;
    }

    public Long getAnswerSeqNbr(Long publicHealthCaseUid, String questionIdentifier )
    {
        Long maxSeqNbr = new Long(0);
        Connection connection = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;
        try
        {
           connection = getConnection();
           pStmt = connection.prepareStatement(WumSqlQuery.ANSWER_GROUP_SEQ_NBR);
           pStmt.setLong(1, publicHealthCaseUid);
           pStmt.setString(2, questionIdentifier);
           rs = pStmt.executeQuery();
           if( rs.next() )
           {
               maxSeqNbr = rs.getLong(1);
           }
        }
        catch(Exception e)
        {
            logger.fatal("Exception in getAnswerSeqNbr()::" + publicHealthCaseUid, e);
            throw new NEDSSSystemException(e.toString());
        }
        finally
        { 
           if (rs != null)
        	   closeResultSet(rs);
           closeStatement(pStmt);  
           releaseConnection(connection);
        }
        return maxSeqNbr;
    }
    public void storePamAnswerDTCollection(Collection<Object> pamDTColl, PublicHealthCaseVO publicHealthCaseVO)
            throws NEDSSSystemException
    {
        try
        {
            if (pamDTColl != null)
            {
				int addSize= 0;
            	int updateSize= 0;
            	int deleteSize = 0;
            	StringBuffer deletedAnswersUids = new StringBuffer("");				
                Iterator<Object> it = pamDTColl.iterator();
                while (it.hasNext())
                {
                    Object object = it.next();
                    if (object instanceof NbsCaseAnswerDT)
                    {
                        NbsCaseAnswerDT nbsAnswerDT = (NbsCaseAnswerDT) object;
                        if (nbsAnswerDT.isItDirty())
                        {
                            updatePamAnswerDT(nbsAnswerDT, publicHealthCaseVO);
							updateSize++;
                        }
                        else if (nbsAnswerDT.isItDelete())
                        {
                            removePamAnswer(nbsAnswerDT);
							deleteSize++;
                            deletedAnswersUids.append(nbsAnswerDT.getNbsQuestionUid().longValue()).append(" | ");
                        }
                        else if (nbsAnswerDT.isItNew())
                        {
                            insertPamAnswerDT(nbsAnswerDT, publicHealthCaseVO);
							addSize++;
                        }
                    }
                    else if (object instanceof ArrayList<?>)
                    {
                        Collection<?> innerCollection = (ArrayList<?>) object;
                        Iterator<?> iter = innerCollection.iterator();
                        while (iter.hasNext())
                        {
                            NbsCaseAnswerDT nbsAnswerDT1 = (NbsCaseAnswerDT) iter.next();
                            if (nbsAnswerDT1.isItDirty())
                            {
                                updatePamAnswerDT(nbsAnswerDT1, publicHealthCaseVO);
								updateSize++;
                            }
                            else if (nbsAnswerDT1.isItDelete())
                            {
                                removePamAnswer(nbsAnswerDT1);
								deleteSize++;
                                deletedAnswersUids.append(nbsAnswerDT1.getNbsQuestionUid().longValue()).append(" | ");
                            }
                            else if (nbsAnswerDT1.isItNew())
                            {
                                insertPamAnswerDT(nbsAnswerDT1, publicHealthCaseVO);
								addSize++;
                            }
                        }
                    }
                }
                logger.debug("********#Answers New: "+addSize+""+ " Updated; "+updateSize+""+" Deleted: "+deleteSize+"");
                logger.debug("********#Deleted Answers: "+deletedAnswersUids.toString());
			}
        }
        catch (NEDSSSystemException ex)
        {
            logger.error("error thrown at NbsAnswerDAO inser method"+ex.getMessage(), ex);
            throw new NEDSSSystemException(ex.toString());
        }
    }

    public void insertPamAnswerDT(NbsCaseAnswerDT nbsAnswerDT, PublicHealthCaseVO publicHealthCaseVO)
            throws NEDSSSystemException
    {
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        int resultCount = 0;
        logger.debug("INSERT_NBS_ANSWER=" + INSERT_NBS_ANSWER);
        dbConnection = getConnection();
        try
        {
            preparedStmt = dbConnection.prepareStatement(INSERT_NBS_ANSWER);
            int i = 1;
            preparedStmt.setInt(i++, nbsAnswerDT.getSeqNbr().intValue()); // 1
            preparedStmt.setTimestamp(i++, publicHealthCaseVO.getThePublicHealthCaseDT().getAddTime()); // 2
            preparedStmt.setLong(i++, nbsAnswerDT.getAddUserId().longValue()); // 3
            preparedStmt.setString(i++, nbsAnswerDT.getAnswerTxt()); // 4
            preparedStmt.setTimestamp(i++, publicHealthCaseVO.getThePublicHealthCaseDT().getLastChgTime()); // 5
            if (nbsAnswerDT.getLastChgUserId() == null)
                preparedStmt.setLong(i++, nbsAnswerDT.getAddUserId().longValue());// 6
            else
                preparedStmt.setLong(i++, nbsAnswerDT.getLastChgUserId().longValue()); // 6
            preparedStmt.setLong(i++, nbsAnswerDT.getNbsQuestionUid().longValue()); // 7
            preparedStmt.setLong(i++, publicHealthCaseVO.getThePublicHealthCaseDT().getPublicHealthCaseUid()
                    .longValue()); // 8

            // inserting 1 for the question version control . This needs to be
            // changed after the database is fixed
            if (nbsAnswerDT.getNbsQuestionVersionCtrlNbr() == null)
                nbsAnswerDT.setNbsQuestionVersionCtrlNbr(new Integer(1));
            preparedStmt.setInt(i++, nbsAnswerDT.getNbsQuestionVersionCtrlNbr().intValue()); // 9

            preparedStmt.setString(i++, publicHealthCaseVO.getThePublicHealthCaseDT().getRecordStatusCd()); // 10
            preparedStmt.setTimestamp(i++, publicHealthCaseVO.getThePublicHealthCaseDT().getRecordStatusTime()); // 11
            if (nbsAnswerDT.getNbsTableMetadataUid() != null)
                preparedStmt.setLong(i++, nbsAnswerDT.getNbsTableMetadataUid().longValue()); // 12
            else
                preparedStmt.setNull(i++, Types.INTEGER);
            if (nbsAnswerDT.getAnswerGroupSeqNbr() != null)
                preparedStmt.setLong(i++, nbsAnswerDT.getAnswerGroupSeqNbr()); // 13
            else
                preparedStmt.setNull(i++, Types.INTEGER);
            resultCount = preparedStmt.executeUpdate();
            logger.debug("resultCount in insertPamAnswerDT is " + resultCount);
        }
        catch (SQLException sqlex)
        {
            logger.fatal("SQLException while inserting " + "nbsAnswerDT into NBS_ANSWER:" + nbsAnswerDT.toString(),
                    sqlex);
            throw new NEDSSDAOSysException(sqlex.toString());
        }
        catch (Exception ex)
        {
            logger.fatal("Error while inserting into NBS_ANSWER, nbsAnswerDT=" + nbsAnswerDT.toString(), ex);
            throw new NEDSSSystemException(ex.toString());
        }
        finally
        {
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }

    }// end of insert method

    private void updatePamAnswerDT(NbsCaseAnswerDT nbsAnswerDT, PublicHealthCaseVO publicHealthCaseVO)
            throws NEDSSSystemException
    {
        ArrayList<Object> paramList = new ArrayList<Object>();
        
        try
        {
	        paramList.add(nbsAnswerDT.getSeqNbr().intValue());
	        paramList.add(nbsAnswerDT.getAnswerTxt());
	        paramList.add(publicHealthCaseVO.getThePublicHealthCaseDT().getLastChgTime());
	        if (nbsAnswerDT.getLastChgUserId() == null)
	            paramList.add(nbsAnswerDT.getAddUserId().longValue());
	        else
	            paramList.add(nbsAnswerDT.getLastChgUserId().longValue());
	
	        if (nbsAnswerDT.getNbsQuestionVersionCtrlNbr() == null)
                nbsAnswerDT.setNbsQuestionVersionCtrlNbr(new Integer(1));
	        paramList.add(nbsAnswerDT.getNbsQuestionVersionCtrlNbr().intValue());
	        paramList.add(publicHealthCaseVO.getThePublicHealthCaseDT().getRecordStatusCd());
	        paramList.add(publicHealthCaseVO.getThePublicHealthCaseDT().getRecordStatusTime());
	        if (nbsAnswerDT.getNbsTableMetadataUid() != null)
	            paramList.add(nbsAnswerDT.getNbsTableMetadataUid().longValue()); // 8
	        else
	            paramList.add(null);
	        if (nbsAnswerDT.getAnswerGroupSeqNbr() != null)
	            paramList.add(nbsAnswerDT.getAnswerGroupSeqNbr()); // 13
	        else
	            paramList.add(null);
	        
	        if(nbsAnswerDT.isUpdateNbsQuestionUid()){
	        	paramList.add(nbsAnswerDT.getNbsQuestionUid());
	        }
	        
	        paramList.add(nbsAnswerDT.getNbsCaseAnswerUid().longValue());
	        
	        if(nbsAnswerDT.isUpdateNbsQuestionUid()){
	        	preparedStmtMethod(null, paramList, UPDATE_NBS_ANSWER_QUESTION_UID, NEDSSConstants.UPDATE);
	        	logger.debug("Page Builder to PageBuilder conversion nbsQuestionUid update for ActUid: "+nbsAnswerDT.getActUid()+", NbsQuestionUid: "+nbsAnswerDT.getNbsQuestionUid());
	        }else{
	        	preparedStmtMethod(null, paramList, UPDATE_NBS_ANSWER, NEDSSConstants.UPDATE);
	        	logger.debug("Updated actUid: "+nbsAnswerDT.getActUid());
	        }

        }
        catch (Exception ex)
        {
            logger.fatal("Exception in updatePamAnswerDT: ERROR = " + ex.getMessage(), ex);
            throw new NEDSSSystemException(ex.toString());
        }

    }// end of update method

    public void logDelPamAnswerDTCollection(Map<Object, Object> pamDTMap, PublicHealthCaseVO publicHealthCaseVO)
            throws NEDSSSystemException
    {
        try
        {
            if (pamDTMap != null)
            {
                Collection<Object> pamDTCollection = pamDTMap.values();
                Iterator<Object> it = pamDTCollection.iterator();
                while (it.hasNext())
                {
                    Object object = it.next();
                    if (object instanceof NbsCaseAnswerDT)
                    {
                        NbsCaseAnswerDT nbsAnswerDT = (NbsCaseAnswerDT) object;
                        logDelPamAnswerDT(nbsAnswerDT, publicHealthCaseVO);
                    }
                    else if (object instanceof ArrayList<?>)
                    {
                        Collection<?> innerCollection = (ArrayList<?>) object;
                        Iterator<?> iter = innerCollection.iterator();
                        while (iter.hasNext())
                        {
                            NbsCaseAnswerDT nbsAnswerDT = (NbsCaseAnswerDT) iter.next();
                            logDelPamAnswerDT(nbsAnswerDT, publicHealthCaseVO);
                        }
                    }
                }
            }
        }
        catch (NEDSSSystemException ex)
        {
            logger.fatal("error thrown at NbsAnswerDAO inser method"+ex.getMessage(), ex);
            throw new NEDSSSystemException(ex.toString());
        }
    }

    private void logDelPamAnswerDT(NbsCaseAnswerDT nbsAnswerDT, PublicHealthCaseVO publicHealthCaseVO)
            throws NEDSSSystemException
    {
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        int resultCount = 0;
        logger.debug(" LOGICALLY_DELETE_NBS_ANSWER=" + LOGICALLY_DELETE_NBS_ANSWER);
        
        try{
        	dbConnection = getConnection();
        }catch(NEDSSSystemException nsex){
        	logger.fatal("SQLException while obtaining database connection for logDelPamAnswerDT ", nsex);
        	throw new NEDSSSystemException(nsex.toString());
        }
        try
        {
            preparedStmt = dbConnection.prepareStatement(LOGICALLY_DELETE_NBS_ANSWER);
            int i = 1;
            preparedStmt.setTimestamp(i++, publicHealthCaseVO.getThePublicHealthCaseDT().getLastChgTime()); // 1
            preparedStmt.setLong(i++, nbsAnswerDT.getLastChgUserId().longValue());// 2

            preparedStmt.setString(i++, publicHealthCaseVO.getThePublicHealthCaseDT().getRecordStatusCd()); // 3
            preparedStmt.setTimestamp(i++, publicHealthCaseVO.getThePublicHealthCaseDT().getRecordStatusTime()); // 4
            preparedStmt.setLong(i++, nbsAnswerDT.getNbsCaseAnswerUid().longValue()); // 5

            resultCount = preparedStmt.executeUpdate();
            logger.debug("resultCount is " + resultCount);
        }
        catch (SQLException sqlex)
        {
            logger.fatal(
                    "SQLException while updatePamAnswerDT nbsAnswerDT into NBS_ANSWER:" + nbsAnswerDT.toString(),
                    sqlex);
            throw new NEDSSDAOSysException(sqlex.toString());
        }
        catch (Exception ex)
        {
            logger.fatal("Error while update into NBS_ANSWER, nbsAnswerDT=" + nbsAnswerDT.toString(), ex);
            throw new NEDSSSystemException(ex.toString());
        }
        finally
        {
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }

    }// end of update method

    private void removePamAnswer(NbsCaseAnswerDT nbsAnswerDT) throws NEDSSDAOSysException, NEDSSSystemException
    {

        PreparedStatement preparedStmt = null;
        Connection dbConnection = null;

        try
        {
            logger.debug("$$$$###Delete DELETE_PAM_ANSWER being called :" + DELETE_NBS_ANSWER);
            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(DELETE_NBS_ANSWER);
            preparedStmt.setLong(1, nbsAnswerDT.getNbsCaseAnswerUid().longValue());
            preparedStmt.executeUpdate();
        }
        catch (SQLException se)
        {
            logger.fatal("SQLException while removePamAnswer " + nbsAnswerDT.toString(), se);
            throw new NEDSSDAOSysException("Error: SQLException while deleting\n" + se.getMessage());
        }
        finally
        {
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
    }
}
