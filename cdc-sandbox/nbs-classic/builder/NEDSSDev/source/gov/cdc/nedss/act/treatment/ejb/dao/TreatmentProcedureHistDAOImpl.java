/**
* Title:        TreatmentProcedureHistDAOImpl.java
* Description:  This is the implementation of NEDSSDAOInterface for the
*               TreatmentProcedureHist value object in the Treatment entity bean.
*               This class encapsulates all the JDBC calls made by the TreatmentEJB
*               for a TreatmentProcedureHist object. Actual logic of
*               inserting/reading/updating/deleting the data in relational
*               database tables to mirror the state of TreatmentEJB is
*               implemented here.
* Copyright:    Copyright (c) 2001
* Company:      Computer Science Corporation
* @author       Aaron Aycock & NEDSS Development Team
* @version      1.1
*/

package gov.cdc.nedss.act.treatment.ejb.dao;

import gov.cdc.nedss.act.sqlscript.WumSqlQuery;
import gov.cdc.nedss.act.treatment.dt.TreatmentProcedureDT;
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
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;


public class TreatmentProcedureHistDAOImpl
    extends BMPBase {

  static final LogUtils logger = new LogUtils(TreatmentProcedureHistDAOImpl.class.
                                              getName());

  public static final String SELECT_TREATMENT_PROCEDURE_HIST = "SELECT treatment_procedure_uid \"treatmentProcedureUid\", " +
      "approach_site_cd \"approachSiteCd\", approach_site_desc_txt \"approachSiteDescTxt\" " +
      "FROM " + DataTables.TREATMENT_PROCEDURE_TABLE + " WHERE treatment_uid = ?";

 // private long interventionUid = -1;
  private int nextSeqNum = 0;

  public TreatmentProcedureHistDAOImpl() {
  } //end of constructor

  public TreatmentProcedureHistDAOImpl(int nextSeqNum) throws NEDSSSystemException {
    this.nextSeqNum = nextSeqNum;
  } //end of constructor

  public void store(Object obj) throws NEDSSSystemException {
	  try{
	    TreatmentProcedureDT dt = (TreatmentProcedureDT) obj;
	    if (dt == null)
	      throw new NEDSSSystemException(
	          "Error: try to store null TreatmentProcedureDT object.");
	    insertProcedureHist(dt);
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

  public Collection<Object> load(Long intUid, Integer treatmentProcedureHistSeq) throws NEDSSSystemException {
	  try{
	    logger.info("Starts loadObject() for a procedures history...");
	    Collection<Object> procedure1DTColl = selectTreatmentProcedureHist(intUid.longValue(),
	        treatmentProcedureHistSeq.intValue());
	
	    return procedure1DTColl;
	  }catch(Exception ex){
		  logger.fatal("Exception  = "+ex.getMessage(), ex);
		  throw new NEDSSSystemException(ex.toString());
	  }

  } //end of load

  ///////////////////////private class methods///////////////////////////////////////

  private Collection<Object> selectTreatmentProcedureHist(long intUid, int treatmentProcedureHistSeq) throws NEDSSSystemException {

    TreatmentProcedureDT treatmentProcedureDT = new TreatmentProcedureDT();
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
                   "for selectTreatmentProcedureHist ", nsex);
      throw new NEDSSSystemException(nsex.toString());
    }

    try {
      preparedStmt = dbConnection.prepareStatement(SELECT_TREATMENT_PROCEDURE_HIST);
      preparedStmt.setLong(1, intUid);
      preparedStmt.setLong(2, treatmentProcedureHistSeq);
      resultSet = preparedStmt.executeQuery();

      resultSetMetaData = resultSet.getMetaData();
      ArrayList<Object>  prList = new ArrayList<Object> ();
      ArrayList<Object>  reSetList = new ArrayList<Object> ();

      prList = (ArrayList<Object> ) resultSetUtils.mapRsToBeanList(resultSet,
          resultSetMetaData, treatmentProcedureDT.getClass(), prList);

      for (Iterator<Object> anIterator = prList.iterator(); anIterator.hasNext(); ) {
        TreatmentProcedureDT reSetTreatmentTreatmentProcedureDT = (TreatmentProcedureDT) anIterator.next();
        reSetTreatmentTreatmentProcedureDT.setItNew(false);
        reSetTreatmentTreatmentProcedureDT.setItDirty(false);

        reSetList.add(reSetTreatmentTreatmentProcedureDT);
      }
      logger.debug("return treatmentProcedure hist collection");
      return reSetList;
    }
    catch (SQLException se) {
      logger.fatal("SQLException while selecting " +
                   "procedrues1 history; id = " + intUid, se);
      throw new NEDSSSystemException(se.toString());
    }
    catch (Exception ex) {
      logger.fatal("Exception while selecting " +
                   "procedrues1 vo; id = " + intUid, ex);
      throw new NEDSSSystemException(ex.toString());
    }
    finally {
      closeResultSet(resultSet);
      closeStatement(preparedStmt);
      releaseConnection(dbConnection);
    }

  } //end of selectTreatmentProcedureHist(...)

  private void insertProcedureHist(TreatmentProcedureDT dt) throws
      NEDSSSystemException {
    if (dt.getTreatmentUid() != null) {
      int resultCount = 0;

      int i = 1;
      Connection dbConnection = null;
      PreparedStatement pStmt = null;
      try {

        dbConnection = getConnection();
        pStmt = dbConnection.prepareStatement(WumSqlQuery.
                                              INSERT_PROCEDURE1_HIST);
        pStmt.setLong(i++, dt.getTreatmentUid().longValue());
        pStmt.setInt(i++, nextSeqNum);

        if (dt.getApproachSiteCd() == null)
          pStmt.setNull(i++, Types.VARCHAR);
        else
          pStmt.setString(i++, dt.getApproachSiteCd());

        if (dt.getApproachSiteDescTxt() == null)
          pStmt.setNull(i++, Types.VARCHAR);
        else
          pStmt.setString(i++, dt.getApproachSiteDescTxt());

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
  } //end of insertProcedureHist()

} //end of class