/**
* Title:        TreatmentHistDAOImpl.java
* Description:
* Copyright:    Copyright (c) 2001
* Company:      Computer Sciences Corporation
* @author       Aaron Aycock & NEDSS Development Team
* @version      1.1
*/


package gov.cdc.nedss.act.treatment.ejb.dao;

import gov.cdc.nedss.act.treatment.dt.TreatmentDT;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.util.BMPBase;
import gov.cdc.nedss.util.DataTables;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.ResultSetUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Collection;
import java.util.Iterator;

public class TreatmentHistDAOImpl
    extends BMPBase {

  public static final String SELECT_TREATMENT_HIST =
      "SELECT treatment_uid \"treatmentUid\", " +
      "activity_from_time \"activityFromTime\", " +
      "activity_to_time \"activityToTime\", " +
      "add_reason_cd \"addReasonCd\",  " +
      "add_time \"addTime\", " +
      "add_user_id \"addUserTime\", " +
      "cd \"cd\", " +
      "cd_desc_txt \"cdDescTxt\", " +
      "cd_system_cd \"cdSystemCd\",  " +
      "cd_system_desc_txt \"cdSystemDescTxt\", " +
      "cd_version \"cdVersion\", " +
      "class_cd \"classCd\", " +
      "last_chg_reason_cd \"lastChgReasonCd\", " +
      "last_chg_time \"lastChgTime\", " +
      "last_chg_user_id \"lastChgUserId\", " +
      "program_area_cd \"programAreaCd\", " +
      "program_jurisdiction_oid \"programJurisdictionOid\",  " +
      "record_status_cd \"recordStatusCd\", " +
      "record_status_time \"recordStatusTime\", " +
      "shared_ind \"sharedInd\", " +
      "status_cd \"statusCd\", " +
      "status_time \"statusTime\", " +
      "txt \"txt\", " +
      "version_ctrl_nbr \"versionCtrlNbr\" " +
      "FROM " + DataTables.TREATMENT_HIST_TABLE + " WHERE treatment_uid = ?";

  public static final String INSERT_TREATMENT_HIST =
      "INSERT INTO " + DataTables.TREATMENT_HIST_TABLE +
      "(treatment_uid, activity_from_time, activity_to_time, " +
      "add_reason_cd, add_time, add_user_id, cd, cd_desc_txt, cd_system_cd, " +
      "cd_system_desc_txt, cd_version, class_cd, last_chg_reason_cd, last_chg_time, " +
      "last_chg_user_id, program_area_cd, program_jurisdiction_oid, record_status_cd, " +
      "record_status_time, shared_ind, status_cd, status_time, txt, version_ctrl_nbr)" +
      " VALUES (?, ?, ?, ?, ?, " +
      "?, ?, ?, ?, ?," +
      "?, ?, ?, ?, ?," +
      "?, ?, ?, ?, ?, " +
      "?, ?, ?, ?)";

  static final LogUtils logger = new LogUtils(TreatmentHistDAOImpl.class.
                                              getName());

  private long treatmentUid = -1;
  private short versionCtrlNbr = 0;

  public TreatmentHistDAOImpl() throws NEDSSSystemException {

  } //end of constructor

  public TreatmentHistDAOImpl(long treatmentUid, short versionCtrlNbr) throws
      NEDSSSystemException {
    this.treatmentUid = treatmentUid;
    this.versionCtrlNbr = versionCtrlNbr;
    // getNextTreatmentHistId();
  } //end of constructor

  public void store(Object obj) throws NEDSSSystemException {
	  try{
	    TreatmentDT dt = (TreatmentDT) obj;
	    if (dt == null)
	      throw new NEDSSSystemException(
	          "Error: try to store null TreatmentDT object.");
	    insertTreatmentHist(dt);
	  }catch(Exception ex){
		  logger.fatal("Exception  = "+ex.getMessage(), ex);
		  throw new NEDSSSystemException(ex.toString());
	  }
  } //end of store()

  public void store(Collection<Object> coll) throws NEDSSSystemException {
	  try{
	    Iterator<Object> iterator = null;
	    if (coll != null) {
	      iterator = coll.iterator();
	      while (iterator.hasNext()) {
	        store(iterator.next());
	      } //end of while
	    } //end of if
	  }catch(Exception ex){
		  logger.fatal("Exception  = "+ex.getMessage(), ex);
		  throw new NEDSSSystemException(ex.toString());
	  }
  } //end of store(Collection)

  public TreatmentDT load(Long intUid, Integer treatHistSeq) throws
      NEDSSSystemException {
	  try{
	    logger.info("Starts loadObject() for a treatment history...");
	    TreatmentDT intDT = selectTreatmentHist(intUid.longValue(),
	                                            treatHistSeq.intValue());
	    intDT.setItNew(false);
	    intDT.setItDirty(false);
	    logger.info("Done loadObject() for a treatment history - return: " +
	                intDT.toString());
	    return intDT;
	  }catch(Exception ex){
		  logger.fatal("Exception  = "+ex.getMessage(), ex);
		  throw new NEDSSSystemException(ex.toString());
	  }
  } //end of load

  public short getVersionCtrlNbr() {
    return this.versionCtrlNbr;
  }

  ////////////////////////////////////private class methods///////////////////////////

  private TreatmentDT selectTreatmentHist(long intUid, int versionCtrlNbr) throws
      NEDSSSystemException {

    TreatmentDT intDT = new TreatmentDT();
    Connection dbConnection = null;
    PreparedStatement preparedStmt = null;
    ResultSet resultSet = null;
    ResultSetMetaData resultSetMetaData = null;
    ResultSetUtils resultSetUtils = new ResultSetUtils();

    try {
      dbConnection = getConnection();
    }
    catch (NEDSSSystemException nsex) {
      logger.fatal("SQLException while obtaining database connection " +
                   "for selectInterventionHist ", nsex);
      throw new NEDSSSystemException(nsex.toString());
    }

    try {
      preparedStmt = dbConnection.prepareStatement(SELECT_TREATMENT_HIST);
      preparedStmt.setLong(1, intUid);
      preparedStmt.setLong(2, versionCtrlNbr);
      resultSet = preparedStmt.executeQuery();

      logger.debug("InterventionDT object for Intervention history: intUid = " +
                   intUid);

      resultSetMetaData = resultSet.getMetaData();

      intDT = (TreatmentDT) resultSetUtils.mapRsToBean(resultSet,
          resultSetMetaData, intDT.getClass());

      intDT.setItNew(false);
      intDT.setItDirty(false);
    }
    catch (SQLException se) {
      logger.fatal("SQLException while selecting " +
                   "intervention history; id = " + intUid, se);
      throw new NEDSSSystemException(se.toString());
    }
    catch (Exception ex) {
      logger.fatal("Exception while selecting " +
                   "intervention vo; id = " + intUid, ex);
      throw new NEDSSSystemException(ex.toString());
    }
    finally {
      closeResultSet(resultSet);
      closeStatement(preparedStmt);
      releaseConnection(dbConnection);
    }
    logger.info("return interventionDT for intervention history");

    return intDT;

  } //end of selectInterventionHist(...)

  private void insertTreatmentHist(TreatmentDT dt) throws
      NEDSSSystemException {
    if (dt.getTreatmentUid() != null) {
      int resultCount = 0;

      int i = 1;
      Connection dbConnection = null;
      PreparedStatement pStmt = null;
      try {

        dbConnection = getConnection();
        pStmt = dbConnection.prepareStatement(INSERT_TREATMENT_HIST);
        pStmt.setLong(i++, dt.getTreatmentUid().longValue());
        pStmt.setInt(i++, dt.getVersionCtrlNbr().intValue());

        if (dt.getActivityFromTime() == null)
          pStmt.setNull(i++, Types.TIMESTAMP);
        else
          pStmt.setTimestamp(i++, dt.getActivityFromTime());

        if (dt.getActivityToTime() == null)
          pStmt.setNull(i++, Types.TIMESTAMP);
        else
          pStmt.setTimestamp(i++, dt.getActivityToTime());

        if (dt.getAddReasonCd() == null)
          pStmt.setNull(i++, Types.VARCHAR);
        else
          pStmt.setString(i++, dt.getAddReasonCd());

        if (dt.getAddTime() == null)
          pStmt.setNull(i++, Types.TIMESTAMP);
        else
          pStmt.setTimestamp(i++, dt.getAddTime());

        if (dt.getAddUserId() == null)
          pStmt.setNull(i++, Types.BIGINT);
        else
          pStmt.setLong(i++, dt.getAddUserId().longValue());

        if (dt.getCd() == null)
          pStmt.setNull(i++, Types.VARCHAR);
        else
          pStmt.setString(i++, dt.getCd());

        if (dt.getCdDescTxt() == null)
          pStmt.setNull(i++, Types.VARCHAR);
        else
          pStmt.setString(i++, dt.getCdDescTxt());

        if (dt.getCdSystemCd() == null)
          pStmt.setNull(i++, Types.VARCHAR);
        else
          pStmt.setString(i++, dt.getCdSystemCd());

        if (dt.getCdSystemDescTxt() == null)
          pStmt.setNull(i++, Types.VARCHAR);
        else
          pStmt.setString(i++, dt.getCdSystemDescTxt());

        if (dt.getCdVersion() == null)
          pStmt.setNull(i++, Types.VARCHAR);
        else
          pStmt.setString(i++, dt.getCdVersion());


        if (dt.getCdSystemDescTxt() == null)
          pStmt.setNull(i++, Types.VARCHAR);
        else
          pStmt.setString(i++, dt.getCdSystemDescTxt());

        if (dt.getClassCd() == null)
          pStmt.setNull(i++, Types.VARCHAR);
        else
          pStmt.setString(i++, dt.getClassCd());

        if (dt.getLastChgReasonCd() == null)
          pStmt.setNull(i++, Types.VARCHAR);
        else
          pStmt.setString(i++, dt.getLastChgReasonCd());

        if (dt.getLastChgTime() == null)
          pStmt.setNull(i++, Types.TIMESTAMP);
        else
          pStmt.setTimestamp(i++, dt.getLastChgTime());

        if (dt.getLastChgUserId() == null)
          pStmt.setNull(i++, Types.BIGINT);
        else
          pStmt.setLong(i++, dt.getLastChgUserId().longValue());

        if (dt.getProgAreaCd() == null)
          pStmt.setNull(i++, Types.VARCHAR);
        else
          pStmt.setString(i++, dt.getProgAreaCd());

        if (dt.getProgramJurisdictionOid() == null)
          pStmt.setNull(i++, Types.VARCHAR);
        else
          pStmt.setLong(i++, dt.getProgramJurisdictionOid().longValue());

        if (dt.getRecordStatusCd() == null)
          pStmt.setNull(i++, Types.VARCHAR);
        else
          pStmt.setString(i++, dt.getRecordStatusCd());

        if (dt.getRecordStatusTime() == null)
          pStmt.setNull(i++, Types.TIMESTAMP);
        else
          pStmt.setTimestamp(i++, dt.getRecordStatusTime());

        pStmt.setString(i++, dt.getStatusCd());

        if (dt.getStatusTime() == null)
          pStmt.setTimestamp(i++, new Timestamp(new java.util.Date().getTime()));
        else
          pStmt.setTimestamp(i++, dt.getStatusTime());

        pStmt.setString(i++, dt.getSharedInd());

        if (dt.getTxt() == null)
          pStmt.setNull(i++, Types.VARCHAR);
        else
          pStmt.setString(i++, dt.getTxt());

        resultCount = pStmt.executeUpdate();
        if (resultCount != 1) {
          throw new NEDSSSystemException(
              "Error: none or more than one entity inserted at a time, resultCount = " +
              resultCount);
        }

      }
      catch (SQLException se) {
    	  logger.fatal("SQLException  = "+se.getMessage(), se);
        throw new NEDSSDAOSysException("Error: SQLException while inserting\n" +
                                       se.getMessage());
      }
      finally {
        closeStatement(pStmt);
        releaseConnection(dbConnection);
      } //end of finally
    } //end of if
  } //end of insertInterventionHist()

} //end of class