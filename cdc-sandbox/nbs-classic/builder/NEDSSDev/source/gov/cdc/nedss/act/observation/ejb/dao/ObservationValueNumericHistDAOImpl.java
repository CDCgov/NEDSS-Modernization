package gov.cdc.nedss.act.observation.ejb.dao;


import gov.cdc.nedss.act.observation.dt.ObsValueNumericDT;
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

public class ObservationValueNumericHistDAOImpl extends BMPBase{
  static final LogUtils logger = new LogUtils(ObservationValueNumericHistDAOImpl.class.getName());
  private long observationUid = -1;
  private short obsValueNumericSeq = -1;
  private short versionCtrlNbr = 0;

  /**
   * Initializes the following class attribute: versionCtrlNbr
   * @param versionCtrlNbr : short
   */
  public ObservationValueNumericHistDAOImpl(short versionCtrlNbr) {
    this.versionCtrlNbr = versionCtrlNbr;
  }//end of constructor

 /**
  * Default Constructor
  */
 public ObservationValueNumericHistDAOImpl() {

  }//end of constructor

  /**
   * Results in the addition of an ObsValueNumericDT record to history
   * @para obj : Object
   * @return void
   * @throws NEDSSSystemException
   */
  public void store(Object obj)
      throws  NEDSSSystemException {
	  try{
        ObsValueNumericDT dt = (ObsValueNumericDT)obj;
        if(dt == null)
           throw new NEDSSSystemException("Error: try to store null ObsValueNumericDT object.");
          insertObservationValueNumericHist(dt);
	  }catch(Exception ex){
		  logger.fatal("Exception ="+ex.getMessage(), ex);
		  throw new NEDSSSystemException(ex.toString(), ex);
	  }
    }//end of store()

  /**
   * Results in the addtion of 0.* obsValueNumericDT objects to history
   * @param coll : Collection
   * @return void
   * @throws NEDSSSystemException
   */
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
			  logger.fatal("Exception ="+ex.getMessage(), ex);
			  throw new NEDSSSystemException(ex.toString(), ex);
		  }
  }//end of store(Collection)

  /**
   * Loads a collection of ObsValueNumericDT objects.
   * @param obsUid : Long
   * @param obsValueNumericSeq : Integer
   * @param versionCtrlNbr : Integer
   * @return Collection
   * @throws NEDSSSystemException
   */
  public Collection<Object> load(Long obsUid, Integer obsValueNumericSeq, Integer versionCtrlNbr) throws NEDSSSystemException
      {
	  	try{
	        logger.info("Starts loadObject() for a observation value numeric history...");
	        Collection<Object> obsValueNumericDTColl = selectObsValueNumericHist(obsUid.longValue(), obsValueNumericSeq.intValue(), versionCtrlNbr.intValue());
	
	        return obsValueNumericDTColl;
      }catch(Exception ex){
		  logger.fatal("Exception ="+ex.getMessage(), ex);
		  throw new NEDSSSystemException(ex.toString());
	  }

    }//end of load

  public short getVersionCtrlNbr()
    {
      return this.versionCtrlNbr;
    }//end of getVersionCtrlNbr()

  
  ////////////////////////////////private class methods/////////////////////////////////
  /**
   * Creates a collection of obsValueNumericDT objects
   * @param obsUid : long
   * @param obsValueNumericSeq : int
   * @param versionCtrlNbr : int
   * @return Collection
   * @throws NEDSSSystemException
   */
  private Collection<Object> selectObsValueNumericHist(long obsUid, int obsValueNumericSeq, int versionCtrlNbr)
    throws NEDSSSystemException, NEDSSSystemException {
      ObsValueNumericDT obsValueNumericDT = new ObsValueNumericDT();
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
                            "for selectObsValueNumericHist ", nsex);
            throw new NEDSSSystemException(nsex.toString());
        }

        try
        {
            preparedStmt = dbConnection.prepareStatement(WumSqlQuery.SELECT_OBSERVATION_VALUE_NUMERIC_HIST);
            preparedStmt.setLong(1, obsUid);
            preparedStmt.setInt(2, obsValueNumericSeq);
	           preparedStmt.setInt(3, versionCtrlNbr);
            resultSet = preparedStmt.executeQuery();

	           resultSetMetaData = resultSet.getMetaData();
            ArrayList<Object>  prList = new ArrayList<Object> ();
            ArrayList<Object>  reSetList = new ArrayList<Object> ();

            prList = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData, obsValueNumericDT.getClass(), prList);


            for(Iterator<Object> anIterator = prList.iterator(); anIterator.hasNext(); )
            {
                ObsValueNumericDT reSetNumeric = (ObsValueNumericDT)anIterator.next();
                reSetNumeric.setItNew(false);
                reSetNumeric.setItDirty(false);

                reSetList.add(reSetNumeric);
            }
            logger.debug("return observation numeric hist collection");
            return reSetList;
         }
        catch(SQLException se)
        {
            logger.fatal("SQLException while selecting " +
                            "observation value numeric history; id = " + obsUid, se);
            throw new NEDSSSystemException(se.toString());
        }
        catch(Exception ex)
        {
            logger.fatal("Exception while selecting " +
                            "observation value numeric vo; id = " + obsUid, ex);
            throw new NEDSSSystemException(ex.toString());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }


    }//end of select ObsValueNumericHist

  /**
   * Results in the addtion of a observationValueNumericDT object to history
   * @param dt : ObsValueNumericDT
   * @return void
   * @throws NEDSSSystemException
   */
  private void insertObservationValueNumericHist(ObsValueNumericDT dt)throws
    NEDSSSystemException {
    if(dt.getObservationUid() != null && dt.getObsValueNumericSeq() != null) {
        int resultCount = 0;
            int i = 1;
            Connection dbConnection = null;
            PreparedStatement pStmt = null;
            try
            {
                dbConnection = getConnection();
                pStmt = dbConnection.prepareStatement(WumSqlQuery.INSERT_OBSERVATION_VALUE_NUMERIC_HIST);
                pStmt.setLong(i++, dt.getObservationUid().longValue());
                pStmt.setShort(i++, dt.getObsValueNumericSeq().shortValue());
                pStmt.setShort(i++, versionCtrlNbr);

                if(dt.getHighRange() == null)
                  pStmt.setNull(i++, Types.VARCHAR);
                else
                  pStmt.setString(i++, dt.getHighRange());
                if(dt.getLowRange() == null)
                  pStmt.setNull(i++, Types.VARCHAR);
                else
                  pStmt.setString(i++, dt.getLowRange());

                if(dt.getComparatorCd1() == null)
                  pStmt.setNull(i++, Types.VARCHAR);
                else
                  pStmt.setString(i++, dt.getComparatorCd1());

                if(dt.getNumericValue1() == null)
                  pStmt.setNull(i++, Types.NUMERIC);
                else
                  pStmt.setInt(i++, dt.getNumericValue1().intValue());

                if(dt.getNumericValue2() == null)
                  pStmt.setNull(i++, Types.NUMERIC);
                else
                  pStmt.setInt(i++, dt.getNumericValue2().intValue());
                if(dt.getNumericScale1() == null)
                    pStmt.setNull(i++, Types.SMALLINT);
                  else
                    pStmt.setInt(i++, dt.getNumericScale1().intValue());
                if(dt.getNumericScale2() == null)
                    pStmt.setNull(i++, Types.SMALLINT);
                  else
                    pStmt.setInt(i++, dt.getNumericScale2().intValue());
                if(dt.getNumericUnitCd() == null)
                  pStmt.setNull(i++, Types.VARCHAR);
                else
                  pStmt.setString(i++, dt.getNumericUnitCd());

                if(dt.getSeparatorCd() == null)
                  pStmt.setNull(i++, Types.VARCHAR);
                else
                  pStmt.setString(i++, dt.getSeparatorCd());

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
  }//end of insertObservationValueNumericHist()

}//end of class
