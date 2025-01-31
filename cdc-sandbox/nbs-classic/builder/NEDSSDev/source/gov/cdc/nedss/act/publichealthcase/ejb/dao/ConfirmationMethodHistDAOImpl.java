package gov.cdc.nedss.act.publichealthcase.ejb.dao;

import gov.cdc.nedss.act.publichealthcase.dt.ConfirmationMethodDT;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.util.BMPBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.ResultSetUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author
 * @version 1.0
 */

public class ConfirmationMethodHistDAOImpl extends BMPBase{
  static final LogUtils logger = new LogUtils(ConfirmationMethodHistDAOImpl.class.getName());
 // private long phcUid = -1;
 // private String confirmationMethodCd = "";
  private int nextSeqNum = 0;
  public static final String SELECT_CONFIRMATION_METHOD_HIST =
        "SELECT public_health_case_uid \"PublicHealthCaseUid\", "
      + "confirmation_method_cd \"ConfirmationMethodCd\", "
      + "confirmation_method_desc_txt \"ConfirmationMethodDescTxt\","
      + "confirmation_method_time \"ConfirmationMethodTime\" "
      + "FROM confirmation_method_hist WHERE public_health_case_uid = ? "
      + "and confirmation_method_cd = ? and version_ctrl_nbr = ?";

  public static final String INSERT_CONFIRMATION_METHOD_HIST =
        "INSERT into confirmation_method_hist( "
      + "public_health_case_uid, confirmation_method_cd, "
      + "version_ctrl_nbr, confirmation_method_desc_txt,"
      + "confirmation_method_time) VALUES(?,?,?,?,?)";

  public ConfirmationMethodHistDAOImpl(int nextSeqNum)
                            {
    this.nextSeqNum = nextSeqNum;
  }//end of constructor

  public ConfirmationMethodHistDAOImpl()
                            {
  }//end of constructor

  public void store(Object obj)
      throws  NEDSSSystemException {
	  try{
        ConfirmationMethodDT dt = (ConfirmationMethodDT)obj;
        if(dt == null)
           throw new NEDSSSystemException("Error: try to store null ConfirmationMethodDT object.");
          insertConfirmationMethodHist(dt);
	  }catch(Exception ex){
		  logger.fatal("Exception  = "+ex.getMessage(), ex);
		  throw new NEDSSSystemException(ex.toString());
	  }
    }//end of store()

  public void store(Collection<Object> coll)
	                     throws  NEDSSSystemException {
        Iterator<Object> iterator = null;
        try{
	        if(coll != null)
	        {
	         iterator = coll.iterator();
			 while(iterator.hasNext())
			 {
			  store(iterator.next());
			  }//end of while
	        }//end of if
        }catch(Exception ex){
        	logger.fatal("Exception  = "+ex.getMessage(), ex);
        	throw new NEDSSSystemException(ex.toString());
        }
  }//end of store(Collection)

  public Collection<Object> load(Long phcUid, String confirmationMethodCd, Integer confirmationMethodHistSeq)
                                 throws NEDSSSystemException,NEDSSSystemException {
	  try{
		  	logger.info("Starts loadObject() for a confirmation method history...");
	        Collection<Object> confirmationMethodDTColl = selectConfirmationMethodHist(phcUid.longValue(), confirmationMethodCd, confirmationMethodHistSeq.shortValue());
	        return confirmationMethodDTColl;
	  }catch(Exception ex){
		  logger.fatal("Exception  = "+ex.getMessage(), ex);
		  throw new NEDSSSystemException(ex.toString());
	  }
    }//end of load

  ///////////////////////////////////private class methods//////////////////////

  private Collection<Object> selectConfirmationMethodHist(long phcUid, String confirmationMethodCd,int confirmationMethodHistSeq)
                                throws NEDSSSystemException, NEDSSSystemException {

        ConfirmationMethodDT confirmationMethodDT = new ConfirmationMethodDT();
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        ResultSetUtils resultSetUtils = new ResultSetUtils();
        try
        {
            dbConnection = getConnection();
        }
        catch(NEDSSSystemException nsex)
        {
            logger.fatal("SQLException while obtaining database connection " +
                            "for selectConfirmationMethodHist ", nsex);
            throw new NEDSSSystemException(nsex.toString());
        }
        try
        {
            preparedStmt = dbConnection.prepareStatement(ConfirmationMethodHistDAOImpl.SELECT_CONFIRMATION_METHOD_HIST);
            preparedStmt.setLong(1, phcUid);
            preparedStmt.setString(2, confirmationMethodCd);
            preparedStmt.setInt(3, confirmationMethodHistSeq);
            resultSet = preparedStmt.executeQuery();
            resultSetMetaData = resultSet.getMetaData();
            ArrayList<Object>  prList = new ArrayList<Object> ();
            ArrayList<Object>  reSetList = new ArrayList<Object> ();
            prList = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData, confirmationMethodDT.getClass(), prList);
            for(Iterator<Object> anIterator = prList.iterator(); anIterator.hasNext(); )
            {
                ConfirmationMethodDT reSetConfirmation = (ConfirmationMethodDT)anIterator.next();
                reSetConfirmation.setItNew(false);
                reSetConfirmation.setItDirty(false);
                reSetList.add(reSetConfirmation);
            }
            logger.debug("return confirmation method hist collection");
            return reSetList;
         }
        catch(SQLException se)
        {
            logger.fatal("SQLException while selecting " +
                            "confirmation method history; id = " + phcUid, se);
            throw new NEDSSSystemException(se.toString());
        }
        catch(Exception ex)
        {
            logger.fatal("Exception while selecting " +
                            "confirmation method vo; id = " + phcUid, ex);
            throw new NEDSSSystemException(ex.toString());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
  }//end of method

  private void insertConfirmationMethodHist(ConfirmationMethodDT dt)throws
    NEDSSSystemException {
      if(dt.getPublicHealthCaseUid() != null && dt.getConfirmationMethodCd() != null) {
        int resultCount = 0;
        int i = 1;
        Connection dbConnection = null;
        PreparedStatement pStmt = null;
        try
          {
           dbConnection = getConnection();
           pStmt = dbConnection.prepareStatement(ConfirmationMethodHistDAOImpl.INSERT_CONFIRMATION_METHOD_HIST);
           pStmt.setLong(i++, dt.getPublicHealthCaseUid().longValue());
           pStmt.setString(i++, dt.getConfirmationMethodCd());
           pStmt.setInt(i++, nextSeqNum);
           if(dt.getConfirmationMethodDescTxt() == null)
              pStmt.setNull(i++, Types.VARCHAR);
           else
              pStmt.setString(i++, dt.getConfirmationMethodDescTxt());

           if(dt.getConfirmationMethodTime() == null)
              pStmt.setNull(i++, Types.TIMESTAMP);
           else
              pStmt.setTimestamp(i++, dt.getConfirmationMethodTime());

           resultCount = pStmt.executeUpdate();
            if( resultCount != 1 )
             {
                  throw new NEDSSSystemException ("Error: none or more than one entity inserted at a time, resultCount = " + resultCount);
             }
            } catch(SQLException se) {
              logger.fatal("Error: SQLException while inserting "+se.getMessage(),se);
              throw new NEDSSDAOSysException("Error: SQLException while inserting\n" + se.getMessage());
            } finally {
              closeStatement(pStmt);
              releaseConnection(dbConnection);
            }//end of finally
      }//end of if
  }//end of insertObservationReasonHist()
}//end of class
