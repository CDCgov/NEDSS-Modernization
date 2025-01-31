package gov.cdc.nedss.act.observation.ejb.dao;

import gov.cdc.nedss.act.observation.dt.ObservationReasonDT;
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

public class ObservationReasonHistDAOImpl extends BMPBase{
  static final LogUtils logger = new LogUtils(ObservationReasonHistDAOImpl.class.getName());
  private long observationUid = -1;
  private String reasonCd = "";
  private short versionCtrlNbr = 0;


  /**
   * Results in the initialization of the class attribute versionCtrlNbr
   */
  public ObservationReasonHistDAOImpl(short versionCtrlNbr) {
    this.versionCtrlNbr = versionCtrlNbr;
  }//end of constructor

  /**
   * Default constructor
   */
  public ObservationReasonHistDAOImpl() {


  }//end of constructor

  /**
   * Results in the addition of an ObservationReasonDT object to history
   * @param obj : Object
   * @return void
   * @throws NEDSSSystemException
   */
  public void store(Object obj)
      throws  NEDSSSystemException {
	  try{
        ObservationReasonDT dt = (ObservationReasonDT)obj;
        if(dt == null)
           throw new NEDSSSystemException("Error: try to store null ObservationReasonDT object.");
          insertObservationReasonHist(dt);
	  }catch(Exception ex){
		  logger.fatal("Exception  = "+ex.getMessage(), ex);
		  throw new NEDSSSystemException(ex.toString());
	  }
    }//end of store()

  /**
   * Stores a collection of ObservationReasonDT objects
   * @param coll : Collection
   * @return void
   * @throws NEDSSSystemException
   */
  public void store(Collection<Object> coll)
	   throws  NEDSSSystemException {
	  try{
	       Iterator<Object>  iterator = null;
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

  /**
   * Loads a collection of ObsReasonDT objects based on the parameters provided.
   * @param obsUid : Long
   * @param obsReasonCd : String
   * @param versionCtrlNbr
   * @return Collection
   * @throws NEDSSSystemException
   */
  public Collection<Object> load(Long obsUid, String obsReasonCd, Integer versionCtrlNbr) throws NEDSSSystemException
      {
	  try{
	       logger.info("Starts loadObject() for a observation reason history...");
	        Collection<Object> obsReasonDTColl = selectObservationReasonHist(obsUid.longValue(), obsReasonCd, versionCtrlNbr.intValue());
	
	        return obsReasonDTColl;
	  }catch(Exception ex){
		  logger.fatal("Exception  = "+ex.getMessage(), ex);
		  throw new NEDSSSystemException(ex.toString());
	  }

    }//end of load

  /**
   * Returns the versionCtrlNbr
   * @return versionCtrlNbr : short
   */
  public short getVersionCtrlNbr()
    {
      return this.versionCtrlNbr;
    }//end of getSeqNumber()

  
  /////////////////////////////////////Private class methods//////////////////////////////

  /**
   * Returns a collection of ObservationReasonDT objects from history.
   * @param obsUid : long
   * @param obsReasonCd : String
   * @param versionCtrlNbr : int
   * @return Collection
   * @throws NEDSSSystemException
   */
  private Collection<Object> selectObservationReasonHist(long obsUid, String obsReasonCd, int versionCtrlNbr)throws NEDSSSystemException
   {


        ObservationReasonDT obsReasonDT = new ObservationReasonDT();
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
                            "for selectObservationReasonHist ", nsex);
            throw new NEDSSSystemException(nsex.toString());
        }

        try
        {
            preparedStmt = dbConnection.prepareStatement(WumSqlQuery.SELECT_OBSERVATION_REASON_HIST);
            preparedStmt.setLong(1, obsUid);
            preparedStmt.setString(2, obsReasonCd);
	           preparedStmt.setLong(3, versionCtrlNbr);
            resultSet = preparedStmt.executeQuery();

	           resultSetMetaData = resultSet.getMetaData();
            ArrayList<Object>  prList = new ArrayList<Object> ();
            ArrayList<Object>  reSetList = new ArrayList<Object> ();

            prList = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData, obsReasonDT.getClass(), prList);


            for(Iterator<Object> anIterator = prList.iterator(); anIterator.hasNext(); )
            {
                ObservationReasonDT reSetReason = (ObservationReasonDT)anIterator.next();
                reSetReason.setItNew(false);
                reSetReason.setItDirty(false);

                reSetList.add(reSetReason);
            }
            logger.debug("return observation reason hist collection");
            return reSetList;
         }
        catch(SQLException se)
        {
            logger.fatal("SQLException while selecting " +
                            "observation reason history; id = " + obsUid, se);
            throw new NEDSSSystemException(se.toString());
        }
        catch(Exception ex)
        {
            logger.fatal("Exception while selecting " +
                            "observation reason vo; id = " + obsUid, ex);
            throw new NEDSSSystemException(ex.toString());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }



  }//end of selectObservationReasonHist(...)

  /**
   * Results in the addition of an ObservationReasonDT object to history
   * @param dt : ObservationReasonDT
   * @return void
   * @throws NEDSSSystemException
   */
  private void insertObservationReasonHist(ObservationReasonDT dt)throws
    NEDSSSystemException {
      if(dt.getObservationUid() != null && dt.getReasonCd() != null) {
        int resultCount = 0;
            int i = 1;
            Connection dbConnection = null;
            PreparedStatement pStmt = null;
            try
            {


                dbConnection = getConnection();
                pStmt = dbConnection.prepareStatement(WumSqlQuery.INSERT_OBSERVATION_REASON_HIST);
                pStmt.setLong(i++, dt.getObservationUid().longValue());
                pStmt.setString(i++, dt.getReasonCd());
                pStmt.setShort(i++, versionCtrlNbr);
                if(dt.getReasonDescTxt() == null)
                  pStmt.setNull(i++, Types.VARCHAR);
                else
                  pStmt.setString(i++, dt.getReasonDescTxt());

                resultCount = pStmt.executeUpdate();
                if ( resultCount != 1 )
                {
                  throw new NEDSSSystemException ("Error: none or more than one entity inserted at a time, resultCount = " + resultCount);
                }

            } catch(SQLException se) {
            	logger.fatal("Exception  = "+se.getMessage(), se);
            	throw new NEDSSDAOSysException("Error: SQLException while inserting\n" + se.getMessage());
            } finally {
            	closeStatement(pStmt);
            	releaseConnection(dbConnection);
            }//end of finally
      }//end of if
  }//end of insertObservationReasonHist()

}//end of class
