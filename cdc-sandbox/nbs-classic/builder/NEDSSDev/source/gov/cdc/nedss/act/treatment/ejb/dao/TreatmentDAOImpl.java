/**
 * Title:        TreatmentDAOImpl.java
 * Description:  This is the implementation of NEDSSDAOInterface for the
 *               Treatment value object in the Treatment entity bean.
 *               This class encapsulates all the JDBC calls made by the TreatmentEJB
 *               for a Treatment object. Actual logic of
 *               inserting/reading/updating/deleting the data in relational
 *               database tables to mirror the state of TreatmentEJB is
 *               implemented here.
 * Copyright:    Copyright (c) 2001
 * Company:      Computer Sciences Corporation
 * @author       Aaron Aycock & NEDSS Development Team
 * @version      1.1
 */

package gov.cdc.nedss.act.treatment.ejb.dao;

import gov.cdc.nedss.act.treatment.dt.TreatmentDT;
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.systemservice.uidgenerator.UidClassCodes;
import gov.cdc.nedss.systemservice.uidgenerator.UidGeneratorHelper;
import gov.cdc.nedss.util.BMPBase;
import gov.cdc.nedss.util.DataTables;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.ResultSetUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;

public class TreatmentDAOImpl
    extends BMPBase {
  // private static final String ACT_UID = "ACTIVITY_UID";
  private long treatmentUID = -1;
  //For logging
  static final LogUtils logger = new LogUtils(TreatmentDAOImpl.class.getName());

  public static final String INSERT_ACTIVITY = "INSERT INTO " +
      DataTables.ACTIVITY_TABLE +
      "(act_uid, class_cd, mood_cd) VALUES (?, ?, ?)";

  public static final String SELECT_TREATMENT =
      "SELECT treatment_uid \"treatmentUid\", " +
      "local_id \"localId\", " +
      "activity_from_time \"activityFromTime\", " +
      "activity_to_time \"activityToTime\", " +
      "add_reason_cd \"addReasonCd\",  " +
      "add_user_id \"addUserId\", " +
      "add_time \"addTime\", " +
      "cd \"cd\", " +
      "cd_desc_txt \"cdDescTxt\", " +
      "cd_system_cd \"cdSystemCd\",  " +
      "cd_system_desc_txt \"cdSystemDescTxt\", " +
      "cd_version \"cdVersion\", " +
      "class_cd \"classCd\", " +
      "last_chg_reason_cd \"lastChgReasonCd\", " +
      "last_chg_time \"lastChgTime\", " +
      "last_chg_user_id \"lastChgUserId\", " +
      "prog_area_cd \"progAreaCd\", " +
      "program_jurisdiction_oid \"programJurisdictionOid\",  " +
      "record_status_cd \"recordStatusCd\", " +
      "record_status_time \"recordStatusTime\", " +
      "shared_ind \"sharedInd\", " +
      "status_cd \"statusCd\", " +
      "status_time \"statusTime\", " +
      "txt \"txt\", " +
      "version_ctrl_nbr \"versionCtrlNbr\" " +
      "FROM " + DataTables.TREATMENT_TABLE + " WHERE treatment_uid = ?";

  public static final String SELECT_TREATMENT_UID =
      "SELECT treatment_uid FROM " + DataTables.TREATMENT_TABLE +
      " WHERE treatment_uid = ?";

  public static final String DELETE_TREATMENT =
      "DELETE FROM " + DataTables.TREATMENT_TABLE + " WHERE treatment_uid = ?";

  public static final String UPDATE_TREATMENT =
      "UPDATE " + DataTables.TREATMENT_TABLE +
      " set activity_from_time = ?, activity_to_time = ?, " +
      "add_reason_cd = ?, add_user_id = ?, cd = ?, " +
      "cd_desc_txt = ?, cd_system_cd = ?, cd_system_desc_txt=? ,cd_version = ?, class_cd = ?, " +
      "last_chg_reason_cd = ?, last_chg_time = ?, last_chg_user_id = ?, " +
      //"prog_area_cd = ?, program_jurisdiction_oid = ?, " +
      "record_status_cd = ?, " +
      "record_status_time = ?, shared_ind = ?, status_cd = ?, status_time = ?, " +
      "txt = ?, version_ctrl_nbr = ? " +
      "WHERE treatment_uid = ? AND version_ctrl_nbr = ?";

  public static final String INSERT_TREATMENT =
      "INSERT INTO " + DataTables.TREATMENT_TABLE +
      "(treatment_uid, local_id, activity_from_time, activity_to_time, " +
      "add_reason_cd, add_user_id, add_time, cd, cd_desc_txt, cd_system_cd, " +
      "cd_system_desc_txt, cd_version, class_cd, last_chg_reason_cd, last_chg_time, " +
      "last_chg_user_id, prog_area_cd, program_jurisdiction_oid, record_status_cd, " +
      "record_status_time, shared_ind, status_cd, status_time, txt, version_ctrl_nbr)" +
      " VALUES (?, ?, ?, ?, ?, " +
               "?, ?, ?, ?, ?, ?," +
               "?, ?, ?, ?, ?," +
               "?, ?, ?, ?, ?, " +
               "?, ?, ?, ?)";

  /*public static final String INSERT_TREATMENT =
      "INSERT INTO " + DataTables.TREATMENT_TABLE +
      "(treatment_uid, activity_from_time, activity_to_time, " +
      "add_reason_cd, add_user_id, cd, cd_desc_txt, cd_system_cd, " +
      "cd_system_desc_txt, cd_version, " +
      "class_cd, last_chg_reason_cd, last_chg_time, " +
      "last_chg_user_id, prog_area_cd, program_jurisdiction_oid, " +
      "txt, version_ctrl_nbr) " +
      " VALUES (?, ?, ?, ?, ?, " +
      "?, ?, ?, ?, ?," +
      "?, ?, ?, ?, ?," +
      "?, ?, ?)";
   */
  public TreatmentDAOImpl() {
  }

  public long create(Object obj) throws NEDSSSystemException {
	  try{
	    treatmentUID = insertTreatment( (TreatmentDT) obj);
	    logger.debug("(From inserting treatment_table)treatment UID = " +
	                 treatmentUID);
	    ( (TreatmentDT) obj).setItNew(false);
	    ( (TreatmentDT) obj).setItDirty(false);
	    return treatmentUID;
	  }catch(Exception ex){
		  logger.fatal("Exception  = "+ex.getMessage(), ex);
		  throw new NEDSSSystemException(ex.toString());
	  }
  }

  public void storeTreatment(Object obj) throws  NEDSSSystemException, NEDSSConcurrentDataException {
	  try{
		  updateTreatment( (TreatmentDT) obj);
	  }catch(NEDSSConcurrentDataException ex){
		  logger.fatal("NEDSSConcurrentDataException  = "+ex.getMessage(), ex);
		  throw new NEDSSConcurrentDataException(ex.toString());
	  }catch(Exception ex){
		  logger.fatal("Exception  = "+ex.getMessage(), ex);
		  throw new NEDSSSystemException(ex.toString());
	  }
  }

  public void remove(long treatmentUID) throws NEDSSSystemException {
	  try{
		  removeTreatment(treatmentUID);
	  }catch(Exception ex){
		  logger.fatal("Exception  = "+ex.getMessage(), ex);
		  throw new NEDSSSystemException(ex.toString());
	  }
  }

  public Object loadObject(long treatmentUID) throws NEDSSSystemException {
	  try{
	    TreatmentDT treatmentDT = selectTreatment(treatmentUID);
	    treatmentDT.setItNew(false);
	    treatmentDT.setItDirty(false);
	    return treatmentDT;
	  }catch(Exception ex){
		  logger.fatal("Exception  = "+ex.getMessage(), ex);
		  throw new NEDSSSystemException(ex.toString());
	  }
  }

  public Long findByPrimaryKey(long treatmentUID) throws NEDSSSystemException {
	  try{
	    if (treatmentExists(treatmentUID))
	      return (new Long(treatmentUID));
	    else
	      logger.error("No treatment found for this primary key :" + treatmentUID);
	    return null;
	  }catch(Exception ex){
		  logger.fatal("Exception  = "+ex.getMessage(), ex);
		  throw new NEDSSSystemException(ex.toString());
	  }
  }

  protected boolean treatmentExists(long treatmentUID) throws NEDSSSystemException {
    Connection dbConnection = null;
    PreparedStatement preparedStmt = null;
    ResultSet resultSet = null;
    boolean returnValue = false;

    try {
      dbConnection = getConnection();
      preparedStmt = dbConnection.prepareStatement(SELECT_TREATMENT_UID);
      logger.debug("treatmentID = " + treatmentUID);
      preparedStmt.setLong(1, treatmentUID);
      resultSet = preparedStmt.executeQuery();
      if (!resultSet.next()) {
        returnValue = false;
      }
      else {
        treatmentUID = resultSet.getLong(1);
        returnValue = true;
      }
    }
    catch (SQLException sex) {
      logger.fatal("SQLException while checking for an"
                   + " existing treatment's uid in treatment table-> " +
                   treatmentUID, sex);
      throw new NEDSSSystemException(sex.getMessage());
    }
    catch (Exception ex) {
      logger.fatal("Exception while checking for an"
                   + " existing treatment's uid in treatment table-> " +
                   treatmentUID, ex);
      throw new NEDSSDAOSysException(ex.getMessage());
    }
    finally {
      closeResultSet(resultSet);
      closeStatement(preparedStmt);
      releaseConnection(dbConnection);
    }
    return returnValue;
  }

  private long insertTreatment(TreatmentDT treatmentDT) throws NEDSSSystemException {
    /**
     * Starts inserting a new treatment
     */

    logger.debug("TreatmentDT: " + treatmentDT.toString());

    Connection dbConnection = null;
    PreparedStatement preparedStmt = null;
   // ResultSet resultSet = null;
    //long treatmentUID = -1;
    String localUID = null;
    UidGeneratorHelper uidGen = null;
    int resultCount = 0;

    /**
     * Inserts into act table for treatment
     */
    try {
      uidGen = new UidGeneratorHelper();
      treatmentUID = uidGen.getNbsIDLong(UidClassCodes.NBS_CLASS_CODE).
          longValue();
      dbConnection = getConnection();
      preparedStmt = dbConnection.prepareStatement(INSERT_ACTIVITY);

      int i = 1;
      preparedStmt.setLong(i++, treatmentUID); //1
      preparedStmt.setString(i++, NEDSSConstants.TREATMENT_CLASS_CODE); //2
      preparedStmt.setString(i++, NEDSSConstants.EVENT_MOOD_CODE); //3
      resultCount = preparedStmt.executeUpdate();

    }
    catch (Exception e) {
      throw new NEDSSDAOSysException(e.getMessage());
    }
    finally {
      // close statement before reuse
      closeStatement(preparedStmt);
      releaseConnection(dbConnection);

    }
    if (resultCount != 1) {
      logger.error("Error while inserting " +
                   "uid into ACT_TABLE for a new treatment, resultCount = " +
                   resultCount);
    }

    /**
     * inserts into TREATMENT_TABLE
     */
    try {
      dbConnection = getConnection();
      preparedStmt = dbConnection.prepareStatement(INSERT_TREATMENT);
      uidGen = new UidGeneratorHelper();
      /** @todo Replace with proper class code */
      localUID = uidGen.getLocalID(UidClassCodes.TREATMENT_CLASS_CODE);
      //localUID = uidGen.getLocalID(UidClassCodes.INTTERVENTION_CLASS_CODE);

      int i = 1;

      preparedStmt.setLong(i++, treatmentUID); //1
      preparedStmt.setString(i++, localUID); //1
      if (treatmentDT.getActivityFromTime() == null)
        preparedStmt.setNull(i++, Types.TIMESTAMP); //2
      else
        preparedStmt.setTimestamp(i++, treatmentDT.getActivityFromTime()); //2
      if (treatmentDT.getActivityToTime() == null)
        preparedStmt.setNull(i++, Types.TIMESTAMP); //3
      else
        preparedStmt.setTimestamp(i++, treatmentDT.getActivityToTime()); //3
      preparedStmt.setString(i++, treatmentDT.getAddReasonCd()); //4
      if (treatmentDT.getAddUserId() == null)
        preparedStmt.setNull(i++, Types.INTEGER); //5
      else
        preparedStmt.setLong(i++, (treatmentDT.getAddUserId()).longValue()); //5
      if (treatmentDT.getAddTime() == null)
          preparedStmt.setNull(i++, Types.TIMESTAMP); //3
        else
          preparedStmt.setTimestamp(i++, treatmentDT.getAddTime()); //3
      preparedStmt.setString(i++, treatmentDT.getCd()); //6
      preparedStmt.setString(i++, treatmentDT.getCdDescTxt()); //7
      preparedStmt.setString(i++, treatmentDT.getCdSystemCd()); //8
      preparedStmt.setString(i++, treatmentDT.getCdSystemDescTxt()); //9
      preparedStmt.setString(i++, treatmentDT.getCdVersion()); //10
      preparedStmt.setString(i++, treatmentDT.getClassCd()); //11
      preparedStmt.setString(i++, treatmentDT.getLastChgReasonCd()); //12
      if (treatmentDT.getLastChgTime() == null)
        preparedStmt.setNull(i++, Types.TIMESTAMP); //13
      else
        preparedStmt.setTimestamp(i++, treatmentDT.getLastChgTime()); //13
      if (treatmentDT.getLastChgUserId() == null)
        preparedStmt.setNull(i++, Types.INTEGER); //14
      else
        preparedStmt.setLong(i++, (treatmentDT.getLastChgUserId()).longValue()); //14
      preparedStmt.setString(i++, treatmentDT.getProgAreaCd()); //15
      logger.debug("The program Jurisdiction code number :" +
                   treatmentDT.getProgramJurisdictionOid());
      if (treatmentDT.getProgramJurisdictionOid() == null)
        preparedStmt.setLong(i++, new Long(1).longValue()); //16
      else
        preparedStmt.setLong(i++,
                             treatmentDT.getProgramJurisdictionOid().longValue()); //16
      if (treatmentDT.getRecordStatusCd() == null)
        preparedStmt.setString(i++, treatmentDT.getRecordStatusCd()); //17
      else
        preparedStmt.setString(i++, treatmentDT.getRecordStatusCd().trim()); //17
      if (treatmentDT.getRecordStatusTime() == null)
        preparedStmt.setNull(i++, Types.TIMESTAMP); //18
      else
        preparedStmt.setTimestamp(i++, treatmentDT.getRecordStatusTime()); //18
      preparedStmt.setString(i++, treatmentDT.getSharedInd()); //19
      if (treatmentDT.getStatusCd() == null)
        preparedStmt.setString(i++, treatmentDT.getStatusCd()); //20
      else
        preparedStmt.setString(i++, treatmentDT.getStatusCd().trim()); //20
      if (treatmentDT.getStatusTime() == null) {
        preparedStmt.setNull(i++, Types.TIMESTAMP); //21
      }
      else
        preparedStmt.setTimestamp(i++, treatmentDT.getStatusTime()); //21
      preparedStmt.setString(i++, treatmentDT.getTxt()); //22
      //new ones for security, concurrence
      logger.debug("The version control number :" +
                   treatmentDT.getVersionCtrlNbr());
      preparedStmt.setInt(i++, treatmentDT.getVersionCtrlNbr().intValue()); //23


      resultCount = preparedStmt.executeUpdate();
      logger.debug("done insert treatment! treatmentUID = " + treatmentUID);
      return treatmentUID;
    }
    catch (SQLException sex) {
      logger.fatal("SQLException while inserting " +
                   "treatment into TREATMENT_TABLE: \n", sex);
      throw new NEDSSSystemException(sex.toString());
    }
    catch (Exception ex) {
      logger.fatal("Error while inserting into TREATMENT_TABLE, id = " +
                   treatmentUID, ex);
      throw new NEDSSSystemException(ex.toString());
    }
    finally {
      closeStatement(preparedStmt);
      releaseConnection(dbConnection);
    }
  } //end of inserting treatment

  private void updateTreatment(TreatmentDT treatmentDT) throws
      NEDSSSystemException, NEDSSConcurrentDataException {
    Connection dbConnection = null;
    PreparedStatement preparedStmt = null;
    int resultCount = 0;

    try {
      //Updates treatment table
      if (treatmentDT != null) {
        dbConnection = getConnection();
        logger.debug("Updating treatmentDT: UID = " +
                     treatmentDT.getTreatmentUid().longValue());
        preparedStmt = dbConnection.prepareStatement(UPDATE_TREATMENT);

        int i = 1;

        if (treatmentDT.getActivityFromTime() == null)
          preparedStmt.setNull(i++, Types.TIMESTAMP); //1
        else
          preparedStmt.setTimestamp(i++, treatmentDT.getActivityFromTime()); //1
        if (treatmentDT.getActivityToTime() == null)
          preparedStmt.setNull(i++, Types.TIMESTAMP); //2
        else
          preparedStmt.setTimestamp(i++, treatmentDT.getActivityToTime()); //2
        preparedStmt.setString(i++, treatmentDT.getAddReasonCd()); //3


        if (treatmentDT.getAddUserId() == null)
          preparedStmt.setNull(i++, Types.INTEGER); //5
        else
          preparedStmt.setLong(i++, (treatmentDT.getAddUserId()).longValue()); //5
        preparedStmt.setString(i++, treatmentDT.getCd()); //6
        preparedStmt.setString(i++, treatmentDT.getCdDescTxt()); //7
        preparedStmt.setString(i++, treatmentDT.getCdSystemCd()); //8
        preparedStmt.setString(i++, treatmentDT.getCdSystemDescTxt()); //9
        preparedStmt.setString(i++, treatmentDT.getCdVersion()); //10
        preparedStmt.setString(i++, treatmentDT.getClassCd()); //11
        preparedStmt.setString(i++, treatmentDT.getLastChgReasonCd()); //12
        if (treatmentDT.getLastChgTime() == null)
          preparedStmt.setNull(i++, Types.TIMESTAMP); //13
        else
          preparedStmt.setTimestamp(i++, treatmentDT.getLastChgTime()); //13
        if (treatmentDT.getLastChgUserId() == null)
          preparedStmt.setNull(i++, Types.INTEGER); //14
        else
          preparedStmt.setLong(i++, (treatmentDT.getLastChgUserId()).longValue()); //14


        if (treatmentDT.getRecordStatusCd() == null)
          preparedStmt.setString(i++, treatmentDT.getRecordStatusCd()); //17
        else
          preparedStmt.setString(i++, treatmentDT.getRecordStatusCd().trim()); //17
        if (treatmentDT.getRecordStatusTime() == null)
          preparedStmt.setNull(i++, Types.TIMESTAMP); //18
        else
          preparedStmt.setTimestamp(i++, treatmentDT.getRecordStatusTime()); //18

        preparedStmt.setString(i++, treatmentDT.getSharedInd()); //19

        if (treatmentDT.getStatusCd() == null)
          preparedStmt.setString(i++, treatmentDT.getStatusCd()); //20
        else
          preparedStmt.setString(i++, treatmentDT.getStatusCd().trim()); //20

        if (treatmentDT.getStatusTime() == null)

          preparedStmt.setNull(i++, Types.TIMESTAMP); //21

        else
          preparedStmt.setTimestamp(i++, treatmentDT.getStatusTime()); //21

        preparedStmt.setString(i++, treatmentDT.getTxt()); //22

        if (treatmentDT.getVersionCtrlNbr() == null) {
          logger.error("** VersionCtrlNbr cannot be null *** :" +
                       treatmentDT.getVersionCtrlNbr());
        }
        else {
          logger.debug("VersionCtrlNbr exists" + treatmentDT.getVersionCtrlNbr());
          preparedStmt.setInt(i++, (treatmentDT.getVersionCtrlNbr().intValue())); //23
          logger.debug("new versioncontrol number:" +
                       (treatmentDT.getVersionCtrlNbr().intValue()));
        }

        if (treatmentDT.getTreatmentUid() == null) //24
          preparedStmt.setNull(i++, Types.INTEGER);
        else
          preparedStmt.setLong(i++, (treatmentDT.getTreatmentUid()).longValue());
        if (treatmentDT.getVersionCtrlNbr() == null) { //25
          logger.error("** VersionCtrlNbr cannot be null here :" +
                       treatmentDT.getVersionCtrlNbr());
        }
        else {
          logger.debug("VersionCtrlNbr exists here:" +
                       treatmentDT.getVersionCtrlNbr());
          preparedStmt.setInt(i++,
                              treatmentDT.getVersionCtrlNbr().intValue() - 1);
        }
        logger.debug("sql query is :" + preparedStmt.toString());

        resultCount = preparedStmt.executeUpdate();
        logger.debug("Done updating treatment, UID = " +
                     (treatmentDT.getTreatmentUid()).longValue());
        if (resultCount <= 0) {
          logger.error
              ("Error: none or more than one treatment updated at a time, " +
               "resultCount = " + resultCount);
          throw new NEDSSConcurrentDataException("NEDSSConcurrentDataException: The data has been modified by other user, please verify!");
        }
      }
    }
    catch (SQLException sex) {
      logger.fatal("SQLException while updating " +
                   "treatment into TREATMENT_TABLE: \n", sex);
      throw new NEDSSDAOSysException(sex.toString());
    }
    finally {
      closeStatement(preparedStmt);
      releaseConnection(dbConnection);

    }
  } //end of updating treatment table

  private TreatmentDT selectTreatment(long treatmentUID) throws
      NEDSSSystemException {
    TreatmentDT treatmentDT = new TreatmentDT();
    Connection dbConnection = null;
    PreparedStatement preparedStmt = null;
    ResultSet resultSet = null;
    ResultSetMetaData resultSetMetaData = null;
    ResultSetUtils resultSetUtils = new ResultSetUtils();
    ArrayList<Object> pList = new ArrayList<Object> ();

    /**
     * Selects treatment from Treatment table
     */

    try {
      dbConnection = getConnection();
      preparedStmt = dbConnection.prepareStatement(SELECT_TREATMENT);
      preparedStmt.setLong(1, treatmentUID);
      resultSet = preparedStmt.executeQuery();
      logger.debug("treatmentDT object for: treatmentUID = " + treatmentUID);
      /* if(!resultSet.next())
           throw new NEDSSSystemException("No record for this primary key, PK = " + treatmentUID);
       */
     // int i = 1;
      resultSetMetaData = resultSet.getMetaData();

      pList = (ArrayList<Object> ) resultSetUtils.mapRsToBeanList(resultSet,
          resultSetMetaData, treatmentDT.getClass(), pList);
      // logger.debug("Size of treatment list in treatmentDaoImpl is: " +  pList.size());
      for (Iterator<Object> anIterator = pList.iterator(); anIterator.hasNext(); ) {
        treatmentDT = (TreatmentDT) anIterator.next();
        treatmentDT.setItNew(false);
        treatmentDT.setItDirty(false);
      }

    }
    catch (SQLException sex) {
      logger.fatal("SQLException while selecting " +
                   "treatment vo; id = " + treatmentUID, sex);
      throw new NEDSSSystemException(sex.getMessage());
    }
    catch (Exception ex) {
      logger.fatal("Exception while selecting " +
                   "treatment vo; id = " + treatmentUID, ex);
      throw new NEDSSDAOSysException(ex.getMessage());
    }
    finally {
      closeResultSet(resultSet);
      closeStatement(preparedStmt);
      releaseConnection(dbConnection);
    }
    logger.debug("return treatment object");
    return treatmentDT;
  } //end of selecting treatment ethnic groups

  private void removeTreatment(long treatmentUID) throws NEDSSSystemException {
    Connection dbConnection = null;
    PreparedStatement preparedStmt = null;
    int resultCount = 0;

    /**
     * Deletes treatment ethnic groups
     */
    try {
      dbConnection = getConnection();
      preparedStmt = dbConnection.prepareStatement(DELETE_TREATMENT);
      preparedStmt.setLong(1, treatmentUID);
      resultCount = preparedStmt.executeUpdate();

      if (resultCount != 1) {
        logger.error
            (
            "Error: cannot delete treatment from TREATMENT_TABLE!! resultCount = " +
            resultCount);
      }
    }
    catch (SQLException sex) {
      logger.fatal("SQLException while removing " +
                   "treatment; id = " + treatmentUID, sex);
      throw new NEDSSDAOSysException(sex.getMessage());
    }
    finally {
      closeStatement(preparedStmt);
      releaseConnection(dbConnection);
    }
  } //end of removing treatment

} //end of TreatmentDAOImpl class