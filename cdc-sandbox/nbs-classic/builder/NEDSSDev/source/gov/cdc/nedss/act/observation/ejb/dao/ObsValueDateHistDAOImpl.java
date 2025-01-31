package gov.cdc.nedss.act.observation.ejb.dao;

import java.util.*;
import java.sql.*;
import java.math.BigDecimal;

import gov.cdc.nedss.act.observation.vo.*;
import gov.cdc.nedss.act.observation.dt.*;
import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.systemservice.exception.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.systemservice.uidgenerator.*;
import gov.cdc.nedss.act.sqlscript.*;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author
 * @version 1.0
 */

public class ObsValueDateHistDAOImpl extends BMPBase{
  static final LogUtils logger = new LogUtils(ObsValueDateHistDAOImpl.class.getName());
  private long observationUid = -1;
  private short obsValueDateSeq = -1;
  private short versionCtrlNbr = 0;

  /**
   * Initializes the following class attribute: versionCtrlNbr
   * @param versionCtrlNbr : short
   */
  public ObsValueDateHistDAOImpl(short versionCtrlNbr)throws  NEDSSSystemException {
    this.versionCtrlNbr = versionCtrlNbr;
  }//end of constructor

  /**
  * Default Constructor
  */
  public ObsValueDateHistDAOImpl() {

  }//end of constructor

  /**
   * Results in the addition of an ObsValueDateDT record to history
   * @para obj : Object
   * @return void
   * @throws NEDSSSystemException
   */
  public void store(Object obj)
      throws  NEDSSSystemException {
        ObsValueDateDT dt = (ObsValueDateDT)obj;
        if(dt == null)
           throw new NEDSSSystemException("Error: try to store null ObsValueDateDT object.");
          insertObsValueDateHist(dt);

    }//end of store()

  /**
   * Results in the addtion of 0.* ObsValueDateDT objects to history
   * @param coll : Collection
   * @return void
   * @throws NEDSSSystemException
   */
  public void store(Collection<Object> coll)
	   throws  NEDSSSystemException {
       Iterator<Object>  iterator = null;
        if(coll != null)
        {
        	iterator = coll.iterator();
	        while(iterator.hasNext())
	        {
	        	store(iterator.next());
	        }//end of while
        }//end of if
  }//end of store(Collection)

  /**
   * Loads a collection of ObsValueNumericDT objects.
   * @param obsUid : Long
   * @param obsValueDateSeq : Integer
   * @param versionCtrlNbr : Integer
   * @return Collection
   * @throws NEDSSSystemException
   */
  public Collection<Object> load(Long obsUid, Integer obsValueDateSeq, Integer versionCtrlNbr) throws NEDSSSystemException
      {
	       logger.info("Starts loadObject() for a observation value date history...");
        Collection<Object> obsValueDateDTColl = selectObsValueDateHist(obsUid.longValue(), obsValueDateSeq.shortValue(), versionCtrlNbr.shortValue());

        return obsValueDateDTColl;

    }//end of load

  /**
   * Returns the versionCtrlNbr
   * @return versionCtrlNbr : short
   */
  public short getVersionCtrlNbr()
  {
    return this.versionCtrlNbr;
  }//end of getVersionCtrlNbr()



  ////////////////////////////////Private class methods///////////////////////////
  /**
   * Creates a collection of ObsValueDateDT objects
   * @param obsUid : long
   * @param obsValueDateSeq : int
   * @param versionCtrlNbr : int
   * @return Collection
   * @throws NEDSSSystemException
   */
  private Collection<Object> selectObsValueDateHist(long obsUid, short obsValueDateSeq, short versionCtrlNbr)
    throws NEDSSSystemException{
      ObsValueDateDT obsValueDateDT = new ObsValueDateDT();
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
                            "for selectObsValueDateHist ", nsex);
            throw new NEDSSSystemException(nsex.toString());
        }

        try
        {
            preparedStmt = dbConnection.prepareStatement(WumSqlQuery.SELECT_OBSERVATION_VALUE_DATE_HIST);
            preparedStmt.setLong(1, obsUid);
            preparedStmt.setShort(2, obsValueDateSeq);
	           preparedStmt.setShort(3, versionCtrlNbr);
            resultSet = preparedStmt.executeQuery();

	           resultSetMetaData = resultSet.getMetaData();
            ArrayList<Object>  prList = new ArrayList<Object> ();
            ArrayList<Object>  reSetList = new ArrayList<Object> ();

            prList = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData, obsValueDateDT.getClass(), prList);


            for(Iterator<Object> anIterator = prList.iterator(); anIterator.hasNext(); )
            {
                ObsValueDateDT reSetDate = (ObsValueDateDT)anIterator.next();
                reSetDate.setItNew(false);
                reSetDate.setItDirty(false);

                reSetList.add(reSetDate);
            }
            logger.debug("return observation date hist collection");
            return reSetList;
         }
        catch(SQLException se)
        {
            logger.fatal("SQLException while selecting " +
                            "observation value date history; id = " + obsUid, se);
            throw new NEDSSSystemException(se.toString());
        }
        catch(Exception ex)
        {
            logger.fatal("Exception while selecting " +
                            "observation value date vo; id = " + obsUid, ex);
            throw new NEDSSSystemException(ex.toString());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }



    }//end of select ObsValueDateHist

   /**
   * Results in the addtion of a ObsValueDateDT object to history
   * @param dt : ObsValueDateDT
   * @return void
   * @throws NEDSSSystemException
   */
   private void insertObsValueDateHist(ObsValueDateDT dt)throws
    NEDSSSystemException {
    if(dt.getObservationUid() != null && dt.getObsValueDateSeq() != null) {
        int resultCount = 0;
            //PreparedStatement preparedStmt = getPreparedStatement(NEDSSSqlQuery.INSERT_PERSON_HIST);
            int i = 1;
            Connection dbConnection = null;
            PreparedStatement pStmt = null;
            try
            {

                dbConnection = getConnection();
                pStmt = dbConnection.prepareStatement(WumSqlQuery.INSERT_OBSERVATION_VALUE_DATE_HIST);
                pStmt.setLong(i++, dt.getObservationUid().longValue());
                pStmt.setShort(i++, dt.getObsValueDateSeq().shortValue());
                pStmt.setShort(i++, versionCtrlNbr);

                if(dt.getDurationAmt() == null)
                  pStmt.setNull(i++, Types.VARCHAR);
                else
                  pStmt.setString(i++, dt.getDurationAmt());

                if(dt.getDurationUnitCd() == null)
                  pStmt.setNull(i++, Types.VARCHAR);
                else
                  pStmt.setString(i++, dt.getDurationUnitCd());

                if(dt.getFromTime() == null)
                  pStmt.setNull(i++, Types.TIMESTAMP);
                else
                  pStmt.setTimestamp(i++, dt.getFromTime());

                if(dt.getToTime() == null)
                  pStmt.setNull(i++, Types.TIMESTAMP);
                else
                  pStmt.setTimestamp(i++, dt.getToTime());

                resultCount = pStmt.executeUpdate();
                if ( resultCount != 1 )
                {
                  throw new NEDSSSystemException ("Error: none or more than one entity inserted at a time, resultCount = " + resultCount);
                }

            } catch(SQLException se) {
              se.printStackTrace();
              throw new NEDSSDAOSysException("Error: SQLException while inserting\n" + se.getMessage());
            } finally {
              closeStatement(pStmt);
              releaseConnection(dbConnection);
            }//end of finally
      }//end of if
  }//end of insertObsValueDateHist()


}//end of class
