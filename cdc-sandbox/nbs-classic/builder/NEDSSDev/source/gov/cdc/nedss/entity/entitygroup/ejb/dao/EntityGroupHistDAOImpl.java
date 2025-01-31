package gov.cdc.nedss.entity.entitygroup.ejb.dao;

import java.util.*;
import java.sql.*;
import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.entity.entitygroup.dt.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.association.dao.*;

/**
 * Title:
 * Description: is the implementation of NEDSSDAOInterface for the EntityGroupHist
 * value object inserting into EntityGroupHist table.
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author Nedss Development Team
 * @version 1.0
 */

public class EntityGroupHistDAOImpl extends BMPBase{

  static final LogUtils logger = new LogUtils(ParticipationHistDAOImpl.class.getName());
  private long entityGroupUid = -1;
  private short versionCtrlNbr = 0;

  /**
   * Description: final String SELECT_ENTITY_GROUP_HIST : Sql statement to select from Entity Group History table.
   */
  public final String SELECT_ENTITY_GROUP_HIST =
    "select entity_group_uid \"entityGroupUid\", version_ctrl_nbr \"versionCtrlNbr\", add_reason_cd \"addReasonCd\", add_time \"addTime\", add_user_id \"addUserId\", cd \"cd\", "+
    "cd_desc_txt \"cdDescTxt\", description \"description\", duration_amt \"durationAmt\", duration_unit_cd \"durationUnitCd\", from_time \"fromTime\", "+
    "group_cnt \"groupCnt\", last_chg_reason_cd \"lastChgReasonCd\", last_chg_time \"lastChgTime\", last_chg_user_id \"lastChgUserId\", local_id \"localId\", "+
    "nm \"nm\", record_status_cd \"recordStatusCd\", "+
    "record_status_time \"recordStatusTime\", status_cd \"statusCd\", status_time \"statusTime\", to_time \"toTime\", user_affiliation_txt \"userAffiliationTxt\" "+
    "from entity_group_hist where entity_group_uid = ? and version_ctrl_nbr = ?";

  /**
   * Description: final String INSERT_ENTITY_GROUP_HIST : Sql statement to insert into Entity Group History table.
   */
  public final String INSERT_ENTITY_GROUP_HIST =
    "insert into entity_group_hist(entity_group_uid, version_ctrl_nbr, add_reason_cd, add_time, add_user_id, cd, cd_desc_txt, description, duration_amt, duration_unit_cd, "+
    "from_time, group_cnt, last_chg_reason_cd, last_chg_time, last_chg_user_id, local_id, nm, record_status_cd, record_status_time, status_cd, "+
    "status_time, to_time, user_affiliation_txt) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";


  /**
   * Description: Constructor for EntityGroupHistDAOImpl object.
   * @throws NEDSSDAOSysException
   * @throws NEDSSSystemException
   */
  public EntityGroupHistDAOImpl() throws NEDSSDAOSysException, NEDSSSystemException {

  }//end of constructor


  /**
   * Description: Constructor for EntityGroupHistDAOImpl object for a given entityGroupUid and versionCtrlNbr.
   * @param entityGroupUid : long value
   * @param versionCtrlNbr : short value
   * @throws NEDSSDAOSysException
   * @throws NEDSSSystemException
   */
  public EntityGroupHistDAOImpl(long entityGroupUid, short versionCtrlNbr)throws NEDSSDAOSysException,
  NEDSSSystemException {
    this.entityGroupUid = entityGroupUid;
    this.versionCtrlNbr = versionCtrlNbr;
    //getNextEntityGroupHistId();
  }//end of constructor

  /**
   * Description: This method stors the EntityGroupDT object. This method is used to store one
   * EntityGroupDT object at a time.
   * @param obj :Object (EntityGroupDT object)
   * @throws NEDSSDAOSysException
   * @throws NEDSSSystemException
   */
  public void store(Object obj)
      throws NEDSSDAOSysException, NEDSSSystemException {
        EntityGroupDT dt = (EntityGroupDT)obj;
        if(dt == null)
           throw new NEDSSSystemException("Error: try to store null EntityGroupDT object.");
        insertEntityGroupHist(dt);

    }//end of store()

    /**
     * Description: This method is useful in storing a collection of EntityGroupDT objects. This method
     * internally calls the store(Object obj) method.
     * @param coll : Collection<Object>  object representing a collection of EntityGroupDT objects.
     * @throws NEDSSDAOSysException
     * @throws NEDSSSystemException
     */
  public void store(Collection<Object> coll)throws NEDSSDAOSysException, NEDSSSystemException {
   Iterator<Object>  it = coll.iterator();
    while(it.hasNext()) {
      this.store(it.next());
    }//end of while
  }//end of store

  /**
   * Description: This method is used to load EntityGroupDT for a given entityGroupUid and entityGroupHistSeq.
   * @param entityGroupUid : Long entityGroupUid
   * @param entityGroupHistSeq : Integer entityGroupHistSeq
   * @return : EntityGroupDT
   * @throws NEDSSSystemException
   */
  public EntityGroupDT load(Long entityGroupUid, Integer entityGroupHistSeq) throws NEDSSSystemException
      {
	       logger.info("Starts loadObject() for a entity group history...");
        EntityGroupDT egDT = selectEntityGroupHist(entityGroupUid.longValue(), entityGroupHistSeq.intValue());
        egDT.setItNew(false);
        egDT.setItDirty(false);
        logger.info("Done loadObject() for a entity group history - return: " + egDT.toString());
        return egDT;
    }//end of load

    /**
     * Description: This method returns the version control number
     * @return : short value giving version control number.
     */
    public short getVersionCtrlNbr()
    {
      return this.versionCtrlNbr;
    }

  /////////////////////private class methods/////////////////////////////////////////

  /**
   * Description: this method returns a EntityGroupDT for a given entityGroupUid and entityGroupHistSeq.
   * @param entityGroupUid : long entityGroupUid
   * @param entityGroupHistSeq : int entityGroupHistSeq
   * @return EntityGroupDT
   * @throws NEDSSSystemException
   */
    private EntityGroupDT selectEntityGroupHist(long entityGroupUid, int entityGroupHistSeq)throws NEDSSSystemException
   {


        EntityGroupDT egDT = new EntityGroupDT();
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
                            "for selectEntityGroupHist ", nsex);
            throw new NEDSSSystemException(nsex.toString());
        }

        try
        {
            preparedStmt = dbConnection.prepareStatement(SELECT_ENTITY_GROUP_HIST);
            preparedStmt.setLong(1, entityGroupUid);
            preparedStmt.setLong(2, entityGroupHistSeq);
            resultSet = preparedStmt.executeQuery();

            logger.debug("EntityGroupDT object for entity group History: entityGroupUid = " + entityGroupUid);

            resultSetMetaData = resultSet.getMetaData();

            egDT = (EntityGroupDT)resultSetUtils.mapRsToBean(resultSet, resultSetMetaData, egDT.getClass());

            egDT.setItNew(false);
            egDT.setItDirty(false);
         }
        catch(SQLException se)
        {
            logger.fatal("SQLException while selecting " +
                            "entity group history; EntityGroupUid = " + entityGroupUid, se);
            throw new NEDSSSystemException(se.toString());
        }
        catch(Exception ex)
        {
            logger.fatal("Exception while selecting " +
                            "entity group dt; entityGroupUid = " + entityGroupUid, ex);
            throw new NEDSSSystemException(ex.toString());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
	       logger.info("return EntityGroupDT for Entity Group history");

        return egDT;
  }//end of selectEntityGroupHist(...)


  /**
   * Description: This method inserts history for EntityGroup
   * @param dt : EntityGroupDT object
   * @throws NEDSSDAOSysException
   * @throws NEDSSSystemException
   */
  private void insertEntityGroupHist(EntityGroupDT dt)throws NEDSSDAOSysException,
    NEDSSSystemException {
    if(dt.getEntityGroupUid() != null) {
      int resultCount = 0;

          int i = 1;
          Connection dbConnection = null;
          PreparedStatement pStmt = null;
          try
          {
              dbConnection = getConnection();
              pStmt = dbConnection.prepareStatement(INSERT_ENTITY_GROUP_HIST);
              pStmt.setLong(i++, dt.getEntityGroupUid().longValue());
              pStmt.setShort(i++, dt.getVersionCtrlNbr().shortValue());

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

              if(dt.getCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getCd());

              if(dt.getCdDescTxt() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getCdDescTxt());

              if(dt.getDescription() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getDescription());

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

              if(dt.getGroupCnt() == null)
                pStmt.setNull(i++, Types.SMALLINT);
              else
                pStmt.setShort(i++, dt.getGroupCnt().shortValue());

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

              if(dt.getLocalId() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getLocalId());

              if(dt.getNm() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getNm());

              if(dt.getRecordStatusCd() == null)
                pStmt.setNull(i++, Types.VARCHAR);
              else
                pStmt.setString(i++, dt.getRecordStatusCd());

              if(dt.getRecordStatusTime() == null)
                pStmt.setNull(i++, Types.TIMESTAMP);
              else
                pStmt.setTimestamp(i++, dt.getRecordStatusTime());

              pStmt.setString(i++, dt.getStatusCd());

              pStmt.setTimestamp(i++, dt.getStatusTime());

              if(dt.getToTime() == null)
                pStmt.setNull(i++, Types.TIMESTAMP);
              else
                pStmt.setTimestamp(i++, dt.getToTime());

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
            se.printStackTrace();
            throw new NEDSSDAOSysException("Error: SQLException while inserting\n" + se.getMessage());
          } finally {
            closeStatement(pStmt);
            releaseConnection(dbConnection);
          }//end of finally
    }//end of if
  }//end of insertPatientEncounterHist()

  /*private void getNextEntityGroupHistId() {
    ResultSet resultSet = null;
    short seqTemp = -1;
    Connection dbConnection = null;
    PreparedStatement pStmt = null;

    try
    {
        dbConnection = getConnection();
        pStmt = dbConnection.prepareStatement(NEDSSSqlQuery.SELECT_ENTITY_GROUP_SEQ_NUMBER_HIST);
        pStmt.setLong(1, entityGroupUid);
        resultSet = pStmt.executeQuery();
        while ( resultSet.next() )
        {
            seqTemp = resultSet.getShort(1);
            logger.debug("EntityGroupHistDAOImpl--seqTemp: " + seqTemp);
            if ( seqTemp > nextSeqNum ) nextSeqNum = seqTemp;
        }
        ++nextSeqNum;
        logger.debug("EntityGroupHistDAOImpl--nextSeqNum: " + nextSeqNum);
    } catch(SQLException se) {
        se.printStackTrace();
        throw new NEDSSDAOSysException("Error: SQLException while selecting \n" + se.getMessage());
    } finally {
        closeResultSet(resultSet);
        closeStatement(pStmt);
        releaseConnection(dbConnection);
    }//end of finally
  }//end of getNextEntityGroupHistId()*/
}//end of EntityGroupHistDAOImpl

