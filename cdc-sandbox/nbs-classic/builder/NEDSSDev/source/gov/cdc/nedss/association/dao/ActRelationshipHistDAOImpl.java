package gov.cdc.nedss.association.dao;

import gov.cdc.nedss.association.dt.ActRelationshipDT;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.systemservice.exception.ResultSetUtilsException;
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

public class ActRelationshipHistDAOImpl extends BMPBase{
  private final String SELECT_ACT_RELATIONSHIP_SEQ_NUMBER_HIST =
  "SELECT version_ctrl_nbr from act_relationship_hist where target_act_uid = ? and source_act_uid = ? and type_cd = ?";
  private final String INSERT_ACT_RELATIONSHIP_HIST =
    "insert into act_relationship_hist(target_act_uid, source_act_uid, type_cd, "+
    "version_ctrl_nbr, "+
    "add_reason_cd, add_time, add_user_id, duration_amt, "+
    "duration_unit_cd, from_time, last_chg_reason_cd, last_chg_time, "+
    "last_chg_user_id, record_status_cd, record_status_time, "+
    "sequence_nbr, status_cd, status_time, source_class_cd, "+
    "target_class_cd, to_time, type_desc_txt, user_affiliation_txt) "+
    "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
  private final String SELECT_ACT_RELATIONSHIP_HIST =
    "select target_act_uid \"targetActUid\", source_act_uid \"sourceActUid\", type_cd \"typeCd\", "+
    "version_ctrl_nbr \"versionCtrlNbr\", "+
    "add_reason_cd \"addReasonCd\", add_time \"addTime\", add_user_id \"addUserId\", duration_amt \"durationAmt\", "+
    "duration_unit_cd \"durationUnitCd\", from_time \"fromTime\", last_chg_reason_cd \"lastChgReasonCd\", last_chg_time \"lastChgTime\", "+
    "last_chg_user_id \"lastChgUserId\", record_status_cd \"recordStatusCd\", record_status_time \"recordStatusTime\", "+
    "sequence_nbr \"sequenceNbr\", status_cd \"statusCd\", status_time \"statusTime\", source_class_cd \"sourceClassCd\", "+
    "target_class_cd \"targetClassCd\", to_time \"toTime\", type_desc_txt \"typeDescTxt\", user_affiliation_txt \"userAffiliationTxt\" "+
    "from act_relationship_hist where target_act_uid = ? and source_act_uid = ? and type_cd = ? and version_ctrl_nbr = ?";

  static final LogUtils logger = new LogUtils(ActRelationshipHistDAOImpl.class.getName());
  private long targetActUid = -1;
  private long sourceActUid = -1;
  private String typeCd = "";
  private short versionCtrlNbr = 0;

  /**
   * Default Constructor
   */
  public ActRelationshipHistDAOImpl() throws NEDSSDAOSysException, NEDSSSystemException {

  }//end of constructor

  /**
   * Sets the class versionCtrlNbr
   * @param versionCtrlNbr : short
   * @throws NEDSSDAOSysException, NEDSSSystemException
   */
  public ActRelationshipHistDAOImpl(short versionCtrlNbr) throws NEDSSDAOSysException, NEDSSSystemException {
    this.versionCtrlNbr = versionCtrlNbr;
  }//end of constructor

  /**
   * Initializes the following class variables: targetActUid, sourceActUid, typeCd, versionCtrlNbr
   * @param targetActUid : long
   * @param sourceActUid : long
   * @param typeCd : String
   * @throws NEDSSDAOSysException, NEDSSSystemException
   */
  public ActRelationshipHistDAOImpl(long targetActUid, long sourceActUid, String typeCd)throws NEDSSDAOSysException,
  NEDSSSystemException {
    this.targetActUid = targetActUid;
    this.sourceActUid = sourceActUid;
    this.typeCd = typeCd;
    getNextActRelationshipHistId();
  }//end of constructor

  /**
   * Results in the addition of a Act Relationship record to history
   * @param obj : Object
   * @return void
   * @throws NEDSSDAOSysException, NEDSSSystemException
   */
  public void store(Object obj)
      throws NEDSSDAOSysException, NEDSSSystemException {
        ActRelationshipDT dt = (ActRelationshipDT)obj;
        if(dt == null)
           throw new NEDSSSystemException("Error: try to store null ActRelationshipDT object.");
        insertActRelationshipHist(dt);

    }//end of store()

    /**
     * Results in the addition of multiple ActRelationship records into history
     * @param coll : Collection
     * @throws NEDSSDAOSysException, NEDSSSystemException
     */
    public void store(Collection<Object> coll)
	   throws NEDSSDAOSysException, NEDSSSystemException {
       Iterator<Object>  iterator = null;
       try{
	        if(coll != null)
	        {
	        	iterator = coll.iterator();
		        while(iterator.hasNext())
		        {
	                  store(iterator.next());
		        }//end of while
	        }//end of if
       }catch(NEDSSDAOSysException dex){
    	   logger.fatal("Exception  = "+dex.getMessage(), dex);
    	   throw new NEDSSSystemException(dex.toString());
       }catch(NEDSSSystemException sex){
    	   logger.fatal("Exception  = "+sex.getMessage(), sex);
    	   throw new NEDSSSystemException(sex.toString());
       }
    }//end of store(Collection)

    /**
     * Builds an ActRelationshipDT object based on the parameters passed into the mehtod.
     * @param targetActUid : Long
     * @param sourceActUid : Long
     * @param typeCd : String
     * @param versionCtrlNbr : Integer
     * @return ActRelationshipDT
     * @throws NEDSSSystemException
     */
    public ActRelationshipDT load(Long targetActUid, Long sourceActUid, String typeCd, Integer versionCtrlNbr) throws NEDSSSystemException
      {
    	try{
	        logger.info("Starts loadObject() for a act relationship history...");
	        ActRelationshipDT arDT = selectActRelationshipHist(targetActUid.longValue(), sourceActUid.longValue(), typeCd, versionCtrlNbr.shortValue());
	        arDT.setItNew(false);
	        arDT.setItDirty(false);
	        logger.info("Done loadObject() for a act relationship history - return: " + arDT.toString());
	        return arDT;
    	}catch(NEDSSSystemException ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }//end of load

    /**
     * @return versionCtrlNbr : short
     */
    public short getVersionCtrlNbr()
    {
      return this.versionCtrlNbr;
    }

  ///////////////////////////private class methods///////////////////////////////

  /**
   * Builds an ActRelationshipDT based on the parameters provided.
   * @param targetActUid : long
   * @param sourceActUid : long
   * @param typeCd : String
   * @param versionCtrlNbr : short
   * @param ActRelationshipDT
   * @throws NEDSSSystemException
   */
  private ActRelationshipDT selectActRelationshipHist(long targetActUid, long sourceActUid, String typeCd, short versionCtrlNbr)throws NEDSSSystemException,
  NEDSSSystemException {

        ActRelationshipDT arDT = new ActRelationshipDT();
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
                            "for selectActRelationshipHist ", nsex);
            throw new NEDSSSystemException(nsex.toString());
        }

        try
        {
            preparedStmt = dbConnection.prepareStatement(SELECT_ACT_RELATIONSHIP_HIST);
            preparedStmt.setLong(1, targetActUid);
            preparedStmt.setLong(2, sourceActUid);
            preparedStmt.setString(3, typeCd);
            preparedStmt.setShort(4, versionCtrlNbr);
            resultSet = preparedStmt.executeQuery();

	           logger.debug("ActRelationshipDT object for Act Relationship History: targetActUid = " + targetActUid + ", sourceActUid = "+sourceActUid+", typeCd = "+typeCd);

            resultSetMetaData = resultSet.getMetaData();

            arDT = (ActRelationshipDT)resultSetUtils.mapRsToBean(resultSet, resultSetMetaData, arDT.getClass());

            arDT.setItNew(false);
            arDT.setItDirty(false);
         }
        catch(SQLException se)
        {
            logger.fatal("SQLException while selecting " +
                            "act relationship history; targetActUid = " + targetActUid + ", sourceActUid = "+sourceActUid+", typeCd = "+typeCd, se);
            throw new NEDSSSystemException(se.toString());
        }
        catch(Exception ex)
        {
            logger.fatal("Exception while selecting " +
                            "act relationship dt; targetActUid = " + targetActUid + ", sourceActUid = "+sourceActUid+", typeCd = "+typeCd, ex);
            throw new NEDSSSystemException(ex.toString());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
	       logger.info("return ActRelationshipDT for act relationship history");

        return arDT;


  }//end of selectActRelationshipHist(...)

  /**
   * Results in the addition of an ActRelationship record into history
   * @param dt : ActRelationshipDT
   * @return void
   * @throws NEDSSDAOSysException, NEDSSSystemException
   */
  private void insertActRelationshipHist(ActRelationshipDT dt)throws NEDSSDAOSysException,
    NEDSSSystemException {
    if(dt.getTargetActUid() != null && dt.getSourceActUid() != null && dt.getTypeCd() != null) {
      int resultCount = 0;

          int i = 1;
          Connection dbConnection = null;
          PreparedStatement pStmt = null;
          try
          {
              dbConnection = getConnection();
              pStmt = dbConnection.prepareStatement(INSERT_ACT_RELATIONSHIP_HIST);
              pStmt.setLong(i++, dt.getTargetActUid().longValue());
              pStmt.setLong(i++, dt.getSourceActUid().longValue());
              pStmt.setString(i++, dt.getTypeCd());
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

              if(dt.getSequenceNbr() == null)
                pStmt.setNull(i++, Types.SMALLINT);
              else
                pStmt.setShort(i++, dt.getSequenceNbr().shortValue());


                pStmt.setString(i++, dt.getStatusCd());

              pStmt.setTimestamp(i++, dt.getStatusTime());

              if(dt.getSourceClassCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getSourceClassCd());

              if(dt.getTargetClassCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getTargetClassCd());

              if(dt.getToTime() == null)
                pStmt.setNull(i++, Types.TIMESTAMP);
              else
                pStmt.setTimestamp(i++, dt.getToTime());

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
        	  logger.fatal("Exception  = "+se.getMessage(), se);
            throw new NEDSSDAOSysException("Error: SQLException while inserting\n" + se.getMessage());
          } finally {
            closeStatement(pStmt);
            releaseConnection(dbConnection);
          }//end of finally
    }//end of if
  }//end of insertPatientEncounterHist()

  /**
   * Determines what the next versionCtrlNbr should be.
   * @return void
   */
  private void getNextActRelationshipHistId() {
    ResultSet resultSet = null;
    short seqTemp = -1;
    Connection dbConnection = null;
    PreparedStatement pStmt = null;

    try
    {
        dbConnection = getConnection();
        pStmt = dbConnection.prepareStatement(SELECT_ACT_RELATIONSHIP_SEQ_NUMBER_HIST);
        pStmt.setLong(1, targetActUid);
        pStmt.setLong(2, sourceActUid);
        pStmt.setString(3, typeCd);
        resultSet = pStmt.executeQuery();
        while ( resultSet.next() )
        {
            seqTemp = resultSet.getShort(1);
            logger.debug("ActRelationshipHistDAOImpl--seqTemp: " + seqTemp);
            if ( seqTemp > versionCtrlNbr ) versionCtrlNbr = seqTemp;
        }
        ++versionCtrlNbr;
        logger.debug("PatientEncounterHistDAOImpl--versionCtrlNbr: " + versionCtrlNbr);
    } catch(SQLException se) {
    	logger.fatal("Exception  = "+se.getMessage(), se);
        throw new NEDSSDAOSysException("Error: SQLException while selecting \n" + se.getMessage());
    } finally {
        closeResultSet(resultSet);
        closeStatement(pStmt);
        releaseConnection(dbConnection);
    }//end of finally
  }//end of getNextActRelationshipHistId()
}//end of ActRelationshipHistDAOImpl
