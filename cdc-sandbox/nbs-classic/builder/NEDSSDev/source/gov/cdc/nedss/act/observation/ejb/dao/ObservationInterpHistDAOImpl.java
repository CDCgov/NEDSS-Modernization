package gov.cdc.nedss.act.observation.ejb.dao;

import gov.cdc.nedss.act.observation.dt.ObservationInterpDT;
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

public class ObservationInterpHistDAOImpl extends BMPBase {
  static final LogUtils logger = new LogUtils(ObservationInterpHistDAOImpl.class.getName());
  private long observationUid = -1;
  private String interpretationCd = "";
  private short versionCtrlNbr = 0;
  private static final String BLANK_STRING = " ";
  /**
   * Initializes the class attribute versionCtrlNbr
   */
  public ObservationInterpHistDAOImpl(short versionCtrlNbr){
    this.versionCtrlNbr = versionCtrlNbr;
  }//end of constructor

  /**
   * Default Constructor
   */
 public ObservationInterpHistDAOImpl(){


  }//end of constructor

  /**
   * Results in the addition of an ObservationInterpDT record to history
   * @param obj : Object
   * @return void
   * @throws NEDSSSystemException
   */
  public void store(Object obj)
      throws  NEDSSSystemException {
	  try{
        ObservationInterpDT dt = (ObservationInterpDT)obj;
        if(dt == null)
           throw new NEDSSSystemException("Error: try to store null ObservationDT object.");
          insertObservationInterpHist(dt);
	  }catch(Exception ex){
		  logger.fatal("Exception  = "+ex.getMessage(), ex);
		  throw new NEDSSSystemException(ex.toString());
	  }
    }//end of store()

  /**
   * Results in the addition of 0.* ObservationInterpDT object to history
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
     * Results in the loading of ObservationInterpDT objects from history.
     * @param obsUid : Long
     * @param obsInterpCd : String
     * @param versionCtrlNbr : Integer
     * @return Collection
     * @throws NEDSSSystemException
     */
    public Collection<Object> load(Long obsUid, String obsInterpCd, Integer versionCtrlNbr) throws NEDSSSystemException
      {
    	try{
	       logger.info("Starts loadObject() for a observation interp history...");
	        Collection<Object> obsInterpDTColl = selectObservationInterpHist(obsUid.longValue(), obsInterpCd, versionCtrlNbr.intValue());
	        return obsInterpDTColl;
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
    }//end of getVersionCtrlNbr()




  ///////////////////////////////////////private class methods///////////////////////////

  /**
   * Builds a collection of ObservationInterpDT object from history
   * @param obsUid : long
   * @param obsInterpCd : String
   * @param versionCtrlNbr : int
   * @return Collection
   * @throws NEDSSSystemException
   */
  private Collection<Object> selectObservationInterpHist(long obsUid, String obsInterpCd, int versionCtrlNbr)throws NEDSSSystemException
   {
        ObservationInterpDT obsInterpDT = new ObservationInterpDT();
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
                            "for selectObservationInterpHist ", nsex);
            throw new NEDSSSystemException(nsex.toString());
        }

        try
        {
            preparedStmt = dbConnection.prepareStatement(WumSqlQuery.SELECT_OBSERVATION_INTERP_HIST);
            preparedStmt.setLong(1, obsUid);
            preparedStmt.setString(2, obsInterpCd);
	           preparedStmt.setLong(3, versionCtrlNbr);
            resultSet = preparedStmt.executeQuery();
            resultSetMetaData = resultSet.getMetaData();
            ArrayList<Object>  prList = new ArrayList<Object> ();
            ArrayList<Object>  reSetList = new ArrayList<Object> ();

            prList = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData, obsInterpDT.getClass(), prList);


            for(Iterator<Object> anIterator = prList.iterator(); anIterator.hasNext(); )
            {
                ObservationInterpDT reSetInterp = (ObservationInterpDT)anIterator.next();
                reSetInterp.setItNew(false);
                reSetInterp.setItDirty(false);

                reSetList.add(reSetInterp);
            }
            logger.debug("return observation interp hist collection");
            return reSetList;

         }
        catch(SQLException se)
        {
            logger.fatal("SQLException while selecting " +
                            "observation interp history; id = " + obsUid, se);
            throw new NEDSSSystemException(se.toString());
        }
        catch(Exception ex)
        {
            logger.fatal("Exception while selecting " +
                            "observation interp vo; id = " + obsUid, ex);
            throw new NEDSSSystemException(ex.toString());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }


  }//end of selectObservationInterpHist(...)

  /**
   * Results in the addition of an ObservationInterpDT record to history
   * @param dt : ObservationInterpDT
   * @return void
   * @throws NEDSSSystemException
   */
  private void insertObservationInterpHist(ObservationInterpDT dt)throws
    NEDSSSystemException {
      if(dt.getObservationUid() != null && dt.getInterpretationCd() != null) {
        int resultCount = 0;
            //PreparedStatement preparedStmt = getPreparedStatement(NEDSSSqlQuery.INSERT_PERSON_HIST);
            int i = 1;
            Connection dbConnection = null;
            PreparedStatement pStmt = null;
            try
            {


                dbConnection = getConnection();
                pStmt = dbConnection.prepareStatement(WumSqlQuery.INSERT_OBSERVATION_INTERP_HIST);
                pStmt.setLong(i++, dt.getObservationUid().longValue());
                pStmt.setString(i++, dt.getInterpretationCd());
                pStmt.setShort(i++, versionCtrlNbr);
                if(dt.getInterpretationDescTxt()!=null)
                	pStmt.setString(i++, dt.getInterpretationDescTxt());
                else
                	pStmt.setString(i++, BLANK_STRING);
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
    }//end of insertObservationInterpHist()


}//end of class
