package gov.cdc.nedss.act.intervention.ejb.dao;

import gov.cdc.nedss.act.intervention.dt.Procedure1DT;
import gov.cdc.nedss.act.sqlscript.WumSqlQuery;
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

public class ProceduresHistDAOImpl extends BMPBase{
  static final LogUtils logger = new LogUtils(ProceduresHistDAOImpl.class.getName());
  private long interventionUid = -1;
  private int nextSeqNum = 0;

  public ProceduresHistDAOImpl() {
  }//end of constructor

  public ProceduresHistDAOImpl(int nextSeqNum) throws  NEDSSSystemException{
    this.nextSeqNum = nextSeqNum;
  }//end of constructor

  public void store(Object obj)
    throws  NEDSSSystemException {
	  try{
	      Procedure1DT dt = (Procedure1DT)obj;
	      if(dt == null)
	         throw new NEDSSSystemException("Error: try to store null Procedure1DT object.");
	      insertProcedureHist(dt);
	  }catch(Exception ex){
		  logger.fatal("Exception  = "+ex.getMessage(), ex);
		  throw new NEDSSSystemException(ex.toString());
	  }
  }//end of store()

  public void store(Collection<Object> coll)
	   throws  NEDSSSystemException {
	  	try{
	        Iterator<Object> iterator = null;
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

  public Collection<Object> load(Long intUid, Integer procedures1HistSeq) throws NEDSSSystemException {
	  	try{
		    logger.info("Starts loadObject() for a procedures history...");
	        Collection<Object> procedure1DTColl = selectProceduresHist(intUid.longValue(), procedures1HistSeq.intValue());
	
	        return procedure1DTColl;
	  	}catch(Exception ex){
	  		logger.fatal("Exception  = "+ex.getMessage(), ex);
	  		throw new NEDSSSystemException(ex.toString());
	  	}
    }//end of load

  ///////////////////////private class methods///////////////////////////////////////

  private Collection<Object> selectProceduresHist(long intUid, int procedures1HistSeq)throws NEDSSSystemException {


        Procedure1DT procedures1DT = new Procedure1DT();
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
                            "for selectProceduresHist ", nsex);
            throw new NEDSSSystemException(nsex.toString());
        }

        try
        {
            preparedStmt = dbConnection.prepareStatement(WumSqlQuery.SELECT_PROCEDURES_1_HIST);
            preparedStmt.setLong(1, intUid);
	           preparedStmt.setLong(2, procedures1HistSeq);
            resultSet = preparedStmt.executeQuery();

	           resultSetMetaData = resultSet.getMetaData();
            ArrayList<Object>  prList = new ArrayList<Object> ();
            ArrayList<Object>  reSetList = new ArrayList<Object> ();

            prList = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData, procedures1DT.getClass(), prList);


            for(Iterator<Object> anIterator = prList.iterator(); anIterator.hasNext(); )
            {
                Procedure1DT reSetProcedures1 = (Procedure1DT)anIterator.next();
                reSetProcedures1.setItNew(false);
                reSetProcedures1.setItDirty(false);

                reSetList.add(reSetProcedures1);
            }
            logger.debug("return procedures1 hist collection");
            return reSetList;
         }
        catch(SQLException se)
        {
            logger.fatal("SQLException while selecting " +
                            "procedrues1 history; id = " + intUid, se);
            throw new NEDSSSystemException(se.toString());
        }
        catch(Exception ex)
        {
            logger.fatal("Exception while selecting " +
                            "procedrues1 vo; id = " + intUid, ex);
            throw new NEDSSSystemException(ex.toString());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }



  }//end of selectProceduresHist(...)

  private void insertProcedureHist(Procedure1DT dt)throws
    NEDSSSystemException {
      if(dt.getInterventionUid() != null) {
        int resultCount = 0;

            int i = 1;
            Connection dbConnection = null;
            PreparedStatement pStmt = null;
            try
            {

                dbConnection = getConnection();
                pStmt = dbConnection.prepareStatement(WumSqlQuery.INSERT_PROCEDURE1_HIST);
                pStmt.setLong(i++, dt.getInterventionUid().longValue());
                pStmt.setInt(i++, nextSeqNum);

                if(dt.getApproachSiteCd() == null)
                  pStmt.setNull(i++, Types.VARCHAR);
                else
                  pStmt.setString(i++, dt.getApproachSiteCd());

                if(dt.getApproachSiteDescTxt() == null)
                  pStmt.setNull(i++, Types.VARCHAR);
                else
                  pStmt.setString(i++, dt.getApproachSiteDescTxt());

                resultCount = pStmt.executeUpdate();
                if ( resultCount != 1 )
                {
                  throw new NEDSSSystemException ("Error: none or more than one entity inserted at a time, resultCount = " + resultCount);
                }

            } catch(SQLException se) {
            	logger.fatal("SQLException  = "+se.getMessage(), se);
            	throw new NEDSSDAOSysException("Error: SQLException while inserting\n" + se.getMessage());
            } finally {
            	closeStatement(pStmt);
            	releaseConnection(dbConnection);
            }//end of finally
      }//end of if
  }//end of insertProcedureHist()

}//end of class
