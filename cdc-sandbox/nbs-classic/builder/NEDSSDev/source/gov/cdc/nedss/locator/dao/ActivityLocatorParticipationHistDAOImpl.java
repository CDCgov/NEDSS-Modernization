package gov.cdc.nedss.locator.dao;

import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.locator.dt.ActivityLocatorParticipationDT;
import gov.cdc.nedss.util.BMPBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.ResultSetUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
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

public class ActivityLocatorParticipationHistDAOImpl extends BMPBase {
  static final LogUtils logger = new LogUtils(ActivityLocatorParticipationHistDAOImpl.class.getName());
  private long actUid = -1;
  private long locatorUid = -1;
  private long entityUid = -1;
  private short versionCtrlNbr = 0;

  private final String SELECT_ACTIVITY_LOCATOR_PARTICIPATION_VERSION_CTRL_NBR_HIST =
    "select version_ctrl_nbr from act_locator_participation_hist where act_uid = ? and locator_uid = ? and entity_uid = ?";
  private final String INSERT_ACTIVITY_LOCATOR_PARTICIPATION_HIST_HIST =
    "insert into act_locator_participation_hist(act_uid, "+
    "locator_uid, "+
    "entity_uid, "+
    "version_ctrl_nbr, "+
    "add_reason_cd, "+
    "add_time, "+
    "add_user_id, "+
    "duration_amt, "+
    "duration_unit_cd, "+
    "from_time, "+
    "last_chg_reason_cd, "+
    "last_chg_time, "+
    "last_chg_user_id, "+
    "record_status_cd, "+
    "record_status_time, "+
    "to_time, "+
    "status_cd, "+
    "status_time, "+
    "type_cd, "+
    "type_desc_txt, "+
    "user_affiliation_txt) "+
    "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

  private final String SELECT_ACTIVITY_LOCATOR_PARTICIPATION_HIST =
    "select act_uid \"actUid\", "+
    "locator_uid \"locatorUid\", "+
    "entity_uid \"entityUid\", "+
    "version_ctrl_nbr \"versionCtrlNbr\", "+
    "add_reason_cd \"addReasonCd\", "+
    "add_time \"addTime\", "+
    "add_user_id \"addUserId\", "+
    "duration_amt \"durationAmt\", "+
    "duration_unit_cd \"durationUnitCd\", "+
    "from_time \"fromTime\", "+
    "last_chg_reason_cd \"lastChgReasonCd\", "+
    "last_chg_time \"lastChgTime\", "+
    "last_chg_user_id \"lastChgUserId\", "+
    "record_status_cd \"recordStatusCd\", "+
    "record_status_time \"recordStatusTime\", "+
    "to_time \"toTime\", "+
    "status_cd \"statusCd\", "+
    "status_time \"statusTime\", "+
    "type_cd \"typeCd\", "+
    "type_desc_txt \"typeDescTxt\", "+
    "user_affiliation_txt \"userAffiliationTxt\" "+
    "from act_locator_participation_hist where act_uid = ? and "+
    "locator_uid = ? and entity_uid = ? and version_ctrl_nbr = ?";

  /**
   * Default constructor
   */
  public ActivityLocatorParticipationHistDAOImpl() {
  }//end of constructor

 /**
  * Sets the class variable versionCtrlNbr
  * @param versionCtrlNBr : short
  */
  public ActivityLocatorParticipationHistDAOImpl(short versionCtrlNbr) {
    this.versionCtrlNbr = versionCtrlNbr;
  }//end of constructor

  /**
   * Initializes the following class attributes: actUid, locatorUid,
   * entityUid, versionCtrlNbr
   *
   * @param actUid : long
   * @param locatorUid : long
   * @param entityUid : long
   * @throws NEDSSSystemException
   */
  public ActivityLocatorParticipationHistDAOImpl(long actUid, long locatorUid, long entityUid)throws
  NEDSSSystemException {
    this.actUid = actUid;
    this.locatorUid = locatorUid;
    this.entityUid = entityUid;
    getNextActivityLocatorParticipationVersionCtrlNbr();
  }//end of constructor

  /**
   * Stores the passed in ActivityLocatorParticipationDT object.
   * @param obj : Object
   * @return void
   * @throws NEDSSSystemException
   */
  public void store(Object obj)
      throws  NEDSSSystemException {
	  	try{
	        ActivityLocatorParticipationDT dt = (ActivityLocatorParticipationDT)obj;
	        if(dt == null)
	           throw new NEDSSSystemException("Error: try to store null ActivityLocatorParticipationDT object.");
	        insertActivityLocatorParticipationHist(dt);
	  	}catch(Exception ex){
	  		logger.fatal("Exception  = "+ex.getMessage(), ex);
	  		throw new NEDSSSystemException(ex.toString());
	  	}
    }//end of store()

  /**
   * Results in 0.* Activity Locator Participation records being stored in the database
   * @param coll : Collection
   * @return void
   * @throws NEDSSSystemException
   */
  public void store(Collection<Object> coll)throws  NEDSSSystemException {
	  try{
	    Iterator<Object>  it = coll.iterator();
	    while(it.hasNext()) {
	      this.store(it.next());
	    }//end of while
	  }catch(Exception ex){
		  logger.fatal("Exception  = "+ex.getMessage(), ex);
		  throw new NEDSSSystemException(ex.toString());
	  }
  }//end of store

  /**
   * Loads an ActivityLocatorParticipationDT object
   * @param actUid : Long
   * @param locatorUid : Long
   * @param entityUid : Long
   * @param versionCtrlNbr : Integer
   * @return alpDT : ActivityLocatorParticipationDT
   * throws NEDSSSystemException
   */
  public ActivityLocatorParticipationDT load(Long actUid, Long locatorUid, Long entityUid, Integer versionCtrlNbr)
  throws
     NEDSSSystemException {
	    logger.info("Starts loadObject() for a activity locator participation history...");
	    try{
	        ActivityLocatorParticipationDT alpDT = selectActivityLocatorParticipationHist(actUid.longValue(), locatorUid.longValue(), entityUid.longValue(), versionCtrlNbr.intValue());
	        alpDT.setItNew(false);
	        alpDT.setItDirty(false);
	        logger.info("Done loadObject() for a activity locator participation history - return: " + alpDT.toString());
	        return alpDT;
	    }catch(Exception ex){
	    	logger.fatal("Exception  = "+ex.getMessage(), ex);
	    	throw new NEDSSSystemException(ex.toString());
	    }
    }//end of load

    /**
     * Returns the version number of the record
     * @return versionCtrlNbr : short
     */
    public short getVersionCtrlNbr()
    {
      return this.versionCtrlNbr;
    }

   ////////////////////////////private class methods//////////////////

  /**
   * Builds an ActivityLocatorParticipationDT object
   * @param actUid : Long
   * @param locatorUid : Long
   * @param entityUid : Long
   * @param versionCtrlNbr : int
   * @return alpDT : ActivityLocatorParticipationDT
   * @throws NEDSSSystemException
   */
  private ActivityLocatorParticipationDT selectActivityLocatorParticipationHist(long actUid, long locatorUid, long entityUid, int versionCtrlNbr)
  throws
  NEDSSSystemException {


        ActivityLocatorParticipationDT alpDT = new ActivityLocatorParticipationDT();
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
                            "for selectActivityLocatorParticipationHist ", nsex);
            throw new NEDSSSystemException(nsex.toString());
        }

        try
        {
            preparedStmt = dbConnection.prepareStatement(SELECT_ACTIVITY_LOCATOR_PARTICIPATION_HIST);
            preparedStmt.setLong(1, actUid);
            preparedStmt.setLong(2, locatorUid);
            preparedStmt.setLong(3, entityUid);
            preparedStmt.setLong(4, versionCtrlNbr);
            resultSet = preparedStmt.executeQuery();

            logger.debug("ActivityLocatorParticipationDT object for activity locator participation History: actUid = " + actUid+", locatorUid = "+locatorUid+", entityUid = "+entityUid);

            resultSetMetaData = resultSet.getMetaData();

            alpDT = (ActivityLocatorParticipationDT)resultSetUtils.mapRsToBean(resultSet, resultSetMetaData, alpDT.getClass());

            alpDT.setItNew(false);
            alpDT.setItDirty(false);
         }
        catch(SQLException se)
        {
            logger.fatal("SQLException while selecting " +
                            "Activity Locator Participation history; actUid = " + actUid+", locatorUid = "+locatorUid+", entityUid = "+entityUid, se);
            throw new NEDSSSystemException(se.toString());
        }
        catch(Exception ex)
        {
            logger.fatal("Exception while selecting " +
                            "Activity Locator Participation history; actUid = " + actUid+", locatorUid = "+locatorUid+", entityUid = "+entityUid, ex);
            throw new NEDSSSystemException(ex.toString());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
	       logger.info("return ActivityLocatorParticipationDT for activity locator participation history");

        return alpDT;
  }//end of selectActivityLocatorParticipationHist(...)

  /**
   * Results in the addition of an ActivityLocatorParticipation record into history
   * @param dt : ActivityLocatorParticipationDT
   * @return void
   * @throws NEDSSSystemException
   */
  private void insertActivityLocatorParticipationHist(ActivityLocatorParticipationDT dt)
  throws
    NEDSSSystemException {
    if(dt.getActUid() != null && dt.getLocatorUid() != null && dt.getEntityUid() != null) {
      int resultCount = 0;

          int i = 1;
          Connection dbConnection = null;
          PreparedStatement pStmt = null;
          try
          {
              dbConnection = getConnection();
              pStmt = dbConnection.prepareStatement(INSERT_ACTIVITY_LOCATOR_PARTICIPATION_HIST_HIST);
              pStmt.setLong(i++, dt.getActUid().longValue());
              pStmt.setLong(i++, dt.getLocatorUid().longValue());
              pStmt.setLong(i++, dt.getEntityUid().longValue());
              pStmt.setShort(i++, versionCtrlNbr);

              if(dt.getAddReasonCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getAddReasonCd());

              if(dt.getAddTime() == null)
                pStmt.setNull(i++, Types.TIMESTAMP);
              else
                pStmt.setTimestamp(i++, dt.getAddTime());

              if(dt.getAddUserId() == null)
                pStmt.setNull(i++, Types.BIGINT);
              else
                pStmt.setLong(i++, dt.getAddUserId().longValue());

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

              if(dt.getLastChgReasonCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getLastChgReasonCd());

              if(dt.getLastChgTime() == null)
                pStmt.setNull(i++, Types.TIMESTAMP);
              else
                pStmt.setTimestamp(i++, dt.getLastChgTime());

              if(dt.getLastChgUserId() == null)
                pStmt.setNull(i++, Types.BIGINT);
              else
                pStmt.setLong(i++, dt.getLastChgUserId().longValue());

              if(dt.getRecordStatusCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getRecordStatusCd());

              if(dt.getRecordStatusTime() == null)
                pStmt.setNull(i++, Types.TIMESTAMP);
              else
                pStmt.setTimestamp(i++, dt.getRecordStatusTime());

              if(dt.getToTime() == null)
                pStmt.setNull(i++, Types.TIMESTAMP);
              else
                pStmt.setTimestamp(i++, dt.getToTime());


              pStmt.setString(i++, dt.getStatusCd());
              pStmt.setTimestamp(i++, dt.getStatusTime());

              if(dt.getTypeCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getTypeCd());

              if(dt.getTypeDescTxt() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getTypeDescTxt());

              if(dt.getUserAffiliationTxt() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getUserAffiliationTxt());

              resultCount = pStmt.executeUpdate();
              if ( resultCount != 1 )
              {
                throw new NEDSSSystemException ("Error: none or more than one entity inserted at a time, resultCount = " + resultCount);
              }

          } catch(SQLException se) {
            logger.fatal("SQLException ="+se.getMessage(), se);
            throw new NEDSSDAOSysException("Error: SQLException while inserting\n" + se.getMessage());
          } finally {
            closeStatement(pStmt);
            releaseConnection(dbConnection);
          }//end of finally
    }//end of if
  }//end of insertMaterialEncounterHist()

  /**
   * Results in the instantiation of the versionCtrlNbr
   *
   * @return void
   * @throws NEDSSDAOSysException
   */
  private void getNextActivityLocatorParticipationVersionCtrlNbr() {
    ResultSet resultSet = null;
    short seqTemp = -1;
    Connection dbConnection = null;
    PreparedStatement pStmt = null;

    try
    {
        dbConnection = getConnection();
        pStmt = dbConnection.prepareStatement(SELECT_ACTIVITY_LOCATOR_PARTICIPATION_VERSION_CTRL_NBR_HIST);
        pStmt.setLong(1, actUid);
        pStmt.setLong(2, locatorUid);
        pStmt.setLong(3, entityUid);

        resultSet = pStmt.executeQuery();
        while ( resultSet.next() )
        {
            seqTemp = resultSet.getShort(1);
            logger.debug("NonPersonHistDAOImpl--seqTemp: " + seqTemp);
            if ( seqTemp > versionCtrlNbr ) versionCtrlNbr = seqTemp;
        }
        ++versionCtrlNbr;
        logger.debug("NonPersonHistDAOImpl--versionCtrlNbr: " + versionCtrlNbr);
    } catch(SQLException se) {
    	logger.fatal("SQLException ="+se.getMessage(), se);
        throw new NEDSSDAOSysException("Error: SQLException while selecting \n" + se.getMessage());
    } finally {
        closeResultSet(resultSet);
        closeStatement(pStmt);
        releaseConnection(dbConnection);
    }//end of finally
  }//end of getNextActivityLocatorParticipationVersionCtrlNbr()
}//end of ActivityLocatorParticipationHistDAO
