/**
* Title:        TreatmentProcedure.java
* Description:  This is the implementation of NEDSSDAOInterface for the
*               TreatmentProcedure value object in the Treatment entity bean.
*               This class encapsulates all the JDBC calls made by the TreatmentEJB
*               for a TreatmentProcedure object. Actual logic of
*               inserting/reading/updating/deleting the data in relational
*               database tables to mirror the state of TreatmentEJB is
*               implemented here.
* Copyright:    Copyright (c) 2001
* Company:      Computer Sciences Corporation
* @author       Aaron Aycock & NEDSS Development Team
* @version      1.1
*/

package gov.cdc.nedss.act.treatment.ejb.dao;

import gov.cdc.nedss.act.treatment.dt.TreatmentProcedureDT;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.systemservice.exception.ResultSetUtilsException;
import gov.cdc.nedss.util.BMPBase;
import gov.cdc.nedss.util.DataTables;
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

public class TreatmentProcedureDAOImpl
    extends BMPBase {

  static final LogUtils logger = new LogUtils(TreatmentProcedureDAOImpl.class.
                                              getName());

  public static final String SELECT_TREATMENT_PROCEDURE =
      "SELECT treatment_uid \"treatmentProcedureUid\", " +
      "approach_site_cd \"approachSiteCd\", approach_site_desc_txt \"approachSiteDescTxt\" " +
      "FROM " + DataTables.TREATMENT_PROCEDURE_TABLE + " WHERE treatment_uid = ?";

  public static final String SELECT_TREATMENT_PROCEDURE_UID =
      "SELECT treatment_uid FROM " +
      DataTables.TREATMENT_PROCEDURE_TABLE + " WHERE treatment_uid = ?";

  public static final String DELETE_TREATMENT_PROCEDURE = "DELETE FROM " +
      DataTables.TREATMENT_PROCEDURE_TABLE +
      " WHERE treatment_uid = ?";

  public static final String UPDATE_TREATMENT_PROCEDURE = "UPDATE " +
      DataTables.TREATMENT_PROCEDURE_TABLE +
      " set approach_site_cd = ?, approach_site_desc_txt  = ?    WHERE  treatment_uid = ?";

  public static final String INSERT_TREATMENT_PROCEDURE = "INSERT INTO " +
      DataTables.TREATMENT_PROCEDURE_TABLE +
      "(treatment_uid, approach_site_cd, approach_site_desc_txt, treatment_procedure_seq) Values (?, ?, ?, ?)";

  public TreatmentProcedureDAOImpl() {
  }

  public long create(long treatmentProcedureUID, Collection<Object> coll) throws
      NEDSSSystemException {

	  try{
	    logger.debug("In create method");
	    insertTreatmentProcedures(treatmentProcedureUID, coll);
	
	    return treatmentProcedureUID;
	  }catch(Exception ex){
		  logger.fatal("Exception  = "+ex.getMessage(), ex);
		  throw new NEDSSSystemException(ex.toString());
	  }
  }

  public void store(Collection<Object> coll) throws  NEDSSSystemException {
	  try{
		  updateTreatmentProcedures(coll);
	  }catch(Exception ex){
		  logger.fatal("Exception  = "+ex.getMessage(), ex);
		  throw new NEDSSSystemException(ex.toString());
	  }
  }

  public void remove(long treatmentProcedureUID) throws NEDSSSystemException {
	  try{
		  removeTreatmentProcedures(treatmentProcedureUID);
	  }catch(Exception ex){
		  logger.fatal("Exception  = "+ex.getMessage(), ex);
		  throw new NEDSSSystemException(ex.toString());
	  }
  }

  public Collection<Object> load(long treatmentProcedureUID) throws NEDSSSystemException {
	  try{
	    Collection<Object> inColl = selectTreatmentProcedures(treatmentProcedureUID);
	    return inColl;
	  }catch(Exception ex){
		  logger.fatal("Exception  = "+ex.getMessage(), ex);
		  throw new NEDSSSystemException(ex.toString());
	  }
  }

  public Long findByPrimaryKey(long treatmentProcedureUID) throws NEDSSSystemException {
	  try{
	    if (TreatmentProcedureExists(treatmentProcedureUID))
	      return (new Long(treatmentProcedureUID));
	    else
	      logger.error("No TreatmentProcedure found for THIS primary key :" +
	                   treatmentProcedureUID);
	    return null;
	  }catch(Exception ex){
		  logger.fatal("Exception  = "+ex.getMessage(), ex);
		  throw new NEDSSSystemException(ex.toString());
	  }
  }

  protected boolean TreatmentProcedureExists(long treatmentProcedureUID) throws
      NEDSSSystemException {
    Connection dbConnection = null;
    PreparedStatement preparedStmt = null;
    ResultSet resultSet = null;
    boolean returnValue = false;

    try {
      dbConnection = getConnection();
      preparedStmt = dbConnection.prepareStatement(
          SELECT_TREATMENT_PROCEDURE_UID);
      logger.debug("treatmentProcedureUID = " + treatmentProcedureUID);
      preparedStmt.setLong(1, treatmentProcedureUID);
      resultSet = preparedStmt.executeQuery();

      if (!resultSet.next()) {
        returnValue = false;
      }
      else {
        treatmentProcedureUID = resultSet.getLong(1);
        returnValue = true;
      }
    }
    catch (SQLException sex) {
      logger.fatal("SQLException while checking for an"
                   + " TreatmentProcedure = " + treatmentProcedureUID, sex);
      throw new NEDSSSystemException(sex.getMessage());
    }
    catch (NEDSSSystemException nsex) {
      logger.fatal("Exception while getting dbConnection for checking for an"
                   + " TreatmentProcedure =  " + treatmentProcedureUID, nsex);
      throw new NEDSSSystemException(nsex.getMessage());
    }
    finally {
      closeResultSet(resultSet);
      closeStatement(preparedStmt);
      releaseConnection(dbConnection);
    }
    return returnValue;
  }

  private void insertTreatmentProcedures(long treatmentProcedureUID,
                                         Collection<Object> TreatmentProcedures) throws
      NEDSSSystemException {
    Iterator<Object> anIterator = null;

    try {
      /**
       * Inserts treatment_procedure
       */
      logger.debug("in insertTreatmentProcedures method");
      for (anIterator = TreatmentProcedures.iterator(); anIterator.hasNext(); ) {
        TreatmentProcedureDT TreatmentProcedure = (TreatmentProcedureDT)
            anIterator.next();
        if (TreatmentProcedure != null)
          insertTreatmentProcedure(treatmentProcedureUID, TreatmentProcedure);
        TreatmentProcedure.setTreatmentUid(new Long(treatmentProcedureUID));
        logger.debug("###About to set isItNew to false, current: " + TreatmentProcedure.isItNew());
        //TreatmentProcedure.setItNew(false);
        //TreatmentProcedure.setItDirty(false);
      }
    }
    catch (Exception ex) {
      logger.fatal("Exception while inserting " +
                   "procedure into TREATMENT_PROCEDURE TABLE: \n", ex);
      throw new NEDSSSystemException(ex.toString());
    }
    logger.debug("Done inserting TreatmentProcedure");
  } //end of inserting TreatmentProcedure

  private void insertTreatmentProcedure(long treatmentProcedureUID,
                                        TreatmentProcedureDT TreatmentProcedure) throws
      NEDSSSystemException {
    logger.debug("in insertTreatmentProcedure method");
    Connection dbConnection = null;
    PreparedStatement preparedStmt = null;
    int resultCount = 0;

    try {
      dbConnection = getConnection();
    }
    catch (NEDSSSystemException nsex) {
      logger.fatal("Error obtaining db connection " +
                   "while inserting TreatmentProcedure", nsex);
      throw new NEDSSSystemException(nsex.toString());
    }

    try {
      /**
       * Inserts a TreatmentProcedure
       */
      preparedStmt = dbConnection.prepareStatement(INSERT_TREATMENT_PROCEDURE);
      int i = 1;

      logger.debug("TreatmentProcedure = " + TreatmentProcedure);
      preparedStmt.setLong(i++, treatmentProcedureUID);
      preparedStmt.setString(i++, TreatmentProcedure.getApproachSiteCd());
      preparedStmt.setString(i++, TreatmentProcedure.getApproachSiteDescTxt());
      preparedStmt.setLong(i++, TreatmentProcedure.getTreatmentProcedureSeq().longValue());

      resultCount = preparedStmt.executeUpdate();

      if (resultCount != 1)
        logger.error
            ("Error: none or more than one treatment_procedure inserted at a time, " +
             "resultCount = " + resultCount);
      else {
        TreatmentProcedure.setItNew(false);
        TreatmentProcedure.setItDirty(false);
      }
    }
    catch (SQLException sex) {
      logger.fatal("SQLException while inserting " +
                   "treatment into TREATMENT_PROCEDURE_TABLE: \n", sex);
      throw new NEDSSSystemException(sex.toString());
    }
    finally {
      // closeResultSet(resultSet);
      closeStatement(preparedStmt);
      releaseConnection(dbConnection);
    }
    logger.debug("Done inserting a TreatmentProcedure");
  } //end of inserting a TreatmentProcedure

  private void updateTreatmentProcedures(Collection<Object> TreatmentProcedures) throws
      NEDSSSystemException {
    TreatmentProcedureDT TreatmentProcedure = null;
    Iterator<Object> anIterator = null;

    if (TreatmentProcedures != null) {
      /**
       * Updates TreatmentProcedure
       */
      try {
        for (anIterator = TreatmentProcedures.iterator(); anIterator.hasNext(); ) {
          TreatmentProcedure = (TreatmentProcedureDT) anIterator.next();
          if (TreatmentProcedure == null)
            logger.error("Error: Empty Procedure collection");

          if (TreatmentProcedure.isItNew()) {
            logger.debug("is it new? " + TreatmentProcedure.isItNew());
            logger.debug("updateTreatmentProcedures isItNew");
            insertTreatmentProcedure( (TreatmentProcedure.getTreatmentUid()).
                                     longValue(), TreatmentProcedure);
          }
          if (TreatmentProcedure.isItDirty()) {
            logger.debug("is it Dirty? " + TreatmentProcedure.isItDirty());
            logger.debug("updateTreatmentProcedures isItDirty");
            updateTreatmentProcedure(TreatmentProcedure);
          }
          if (TreatmentProcedure.isItDelete()) {
            updateTreatmentProcedure(TreatmentProcedure);
          }
        }
      }
      catch (Exception ex) {
        logger.fatal("Exception while updating " +
                     "Procedure, \n"+ex.getMessage(), ex);
        throw new NEDSSSystemException(ex.toString());
      }
    }
  } //end of updating procedure table

  private void updateTreatmentProcedure(TreatmentProcedureDT treatmentProcedure) throws
      NEDSSSystemException {
    Connection dbConnection = null;
    PreparedStatement preparedStmt = null;
    int resultCount = 0;

    /**
     * Updates a treatment_procedure
     */

    if (treatmentProcedure != null) {
      try {
        dbConnection = getConnection();
      }
      catch (NEDSSSystemException nsex) {
        logger.fatal("Error obtaining dbConnection " +
                     "for updating procedure", nsex);
        throw new NEDSSSystemException(nsex.toString());
      }

      try {
        preparedStmt = dbConnection.prepareStatement(UPDATE_TREATMENT_PROCEDURE);

        int i = 1;

        logger.debug("treatment_procedure = " + treatmentProcedure.getApproachSiteCd());
        preparedStmt.setString(i++, treatmentProcedure.getApproachSiteCd());
        preparedStmt.setString(i++, treatmentProcedure.getApproachSiteDescTxt());
        preparedStmt.setLong(i++,
                             (treatmentProcedure.getTreatmentUid()).longValue());

        resultCount = preparedStmt.executeUpdate();
        logger.debug("Done updating a treatment_procedure");
        if (resultCount != 1)
          logger.error
              ("Error: none or more than one treatment_procedure updated at a time, " +
               "resultCount = " + resultCount);
      }
      catch (SQLException sex) {
        logger.fatal("SQLException while updating " +
                     " a treatment_procedure, \n", sex);
        throw new NEDSSSystemException(sex.toString());
      }
      catch (Exception ex) {
        logger.fatal("Exception while updating " +
                     " a treatment_procedure, \n", ex);
        throw new NEDSSSystemException(ex.toString());
      }
      finally {
        closeStatement(preparedStmt);
        releaseConnection(dbConnection);
      }
    }
  } //end of updating a TreatmentProcedure

  private Collection<Object> selectTreatmentProcedures(long treatmentProcedureUID) throws
      NEDSSSystemException {

    Connection dbConnection = null;
    PreparedStatement preparedStmt = null;
    ResultSet resultSet = null;
    ResultSetMetaData resultSetMetaData = null;
    ResultSetUtils resultSetUtils = new ResultSetUtils();
    TreatmentProcedureDT treatmentProcedure = new TreatmentProcedureDT();

    try {
      dbConnection = getConnection();
    }
    catch (NEDSSSystemException nsex) {
      logger.fatal("SQLException while obtaining database connection " +
                   "for selectTreatmentProcedures ", nsex);
      throw new NEDSSSystemException(nsex.getMessage());
    }

    /**
     * Selects TreatmentProcedure
     */
    try {
      preparedStmt = dbConnection.prepareStatement(SELECT_TREATMENT_PROCEDURE);
      preparedStmt.setLong(1, treatmentProcedureUID);
      resultSet = preparedStmt.executeQuery();

      resultSetMetaData = resultSet.getMetaData();
      ArrayList<Object>  orList = new ArrayList<Object> ();
      ArrayList<Object>  reSetList = new ArrayList<Object> ();

      orList = (ArrayList<Object> ) resultSetUtils.mapRsToBeanList(resultSet,
          resultSetMetaData, treatmentProcedure.getClass(), orList);

      for (Iterator<Object> anIterator = orList.iterator(); anIterator.hasNext(); ) {
        TreatmentProcedureDT reSetProcedure = (TreatmentProcedureDT) anIterator.
            next();
        reSetProcedure.setItNew(false);
        reSetProcedure.setItDirty(false);
        logger.debug("value of Procedure_cd: " +
                     reSetProcedure.getApproachSiteCd());
        reSetList.add(reSetProcedure);
      }
      logger.debug("return TreatmentProcedure collection");

      return reSetList;
    }
    catch (SQLException se) {
      logger.fatal("SQLException while selecting " +
                   "treatment_procedure; id = " + treatmentProcedureUID, se);
      throw new NEDSSSystemException(se.getMessage());
    }
    catch (ResultSetUtilsException rsuex) {
      logger.fatal("Exception while handling result set in selecting " +
                   "TreatmentProcedure; id = " + treatmentProcedureUID, rsuex);
      throw new NEDSSSystemException(rsuex.getMessage());
    }
    catch (Exception ex) {
      logger.fatal("Exception while selecting " +
                   "treatment_procedure; id = " + treatmentProcedureUID, ex);
      throw new NEDSSSystemException(ex.getMessage());
    }
    finally {
      closeResultSet(resultSet);
      closeStatement(preparedStmt);
      releaseConnection(dbConnection);
    }
  } //end of selecting treatment_procedure

  private void removeTreatmentProcedures(long treatmentProcedureUID) throws
      NEDSSSystemException {
    Connection dbConnection = null;
    PreparedStatement preparedStmt = null;
    int resultCount = 0;

    try {
      dbConnection = getConnection();
    }
    catch (NEDSSSystemException nsex) {
      logger.fatal("SQLException while obtaining database connection " +
                   "for deleting treatment_procedure ", nsex);
      throw new NEDSSSystemException(nsex.getMessage());
    }

    /**
     * Deletes TreatmentProcedure
     */
    try {
      preparedStmt = dbConnection.prepareStatement(DELETE_TREATMENT_PROCEDURE);
      preparedStmt.setLong(1, treatmentProcedureUID);
      resultCount = preparedStmt.executeUpdate();

      if (resultCount != 1) {
        logger.error
            ("Error: cannot delete treatment_procedure from TREATMENT_PROCEDURE_TABLE!! resultCount = " +
             resultCount);
      }
    }
    catch (SQLException sex) {
      logger.fatal("SQLException while removing " +
                   "treatment_procedure; id = " + treatmentProcedureUID, sex);
      throw new NEDSSSystemException(sex.getMessage());
    }
    finally {
      closeStatement(preparedStmt);
      releaseConnection(dbConnection);
    }
  } //end of removing TreatmentProcedure

} //end of TreatmentProcedureDAOImpl class