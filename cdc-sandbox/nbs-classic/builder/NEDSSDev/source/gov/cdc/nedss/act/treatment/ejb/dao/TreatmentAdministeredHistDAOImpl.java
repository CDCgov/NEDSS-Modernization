/**
* Title:        TreatmentAdministeredHistDAOImpl.java
* Description:  This is the implementation of NEDSSDAOInterface for the
*               TreatmentAdministeredHist value object in the Treatment entity bean.
*               This class encapsulates all the JDBC calls made by the TreatmentEJB
*               for a TreatmentAdministeredHist object. Actual logic of
*               inserting/reading/updating/deleting the data in relational
*               database tables to mirror the state of TreatmentEJB is
*               implemented here.
* Copyright:    Copyright (c) 2001
* Company:      Computer Sciences Corporation
* @author       Aaron Aycock & NEDSS Development Team
* @version      1.1
*/

package gov.cdc.nedss.act.treatment.ejb.dao;

import gov.cdc.nedss.act.treatment.dt.TreatmentAdministeredDT;
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


public class TreatmentAdministeredHistDAOImpl
    extends BMPBase {

  static final LogUtils logger = new LogUtils(TreatmentAdministeredHistDAOImpl.class.
                                              getName());

  public static final String INSERT_TREATMENT_ADMINISTERED_HIST =
      "INSERT INTO " + DataTables.TREATMENT_ADMINISTERED_HIST_TABLE +
      "(treatment_administered_uid, dose_qty, dose_qty_unit_cd, form_cd, form_desc_txt, " +
      "rate_qty, rate_qty_unit_cd, route_cd, route_desc_txt ) " +
      "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

  public static final String SELECT_TREATMENT_ADMINISTERED_HIST =
      "SELECT treatment_administered_uid \"treatmentAdministeredUid\", dose_qty \"doseQty\", " +
      "dose_qty_unit_cd \"doseQtyUnitCd\", form_cd \"formCd\",  " +
      "form_desc_txt \"formDescTxt\", rate_qty \"rateQty\", " +
      "rate_qty_unit_cd \"rateQtyUnitCd\", route_cd \"routeCd\", " +
      "route_desc_txt \"routeDescTxt\" " +
      "FROM " +
      DataTables.TREATMENT_ADMINISTERED_HIST_TABLE +
      " WHERE treatment_administered_uid = ?";

 // private long interventionUid = -1;
  private int nextSeqNum = 0;

  public TreatmentAdministeredHistDAOImpl() {
  } //end of constructor

  public TreatmentAdministeredHistDAOImpl(int nextSeqNum) throws
      NEDSSSystemException {
    this.nextSeqNum = nextSeqNum;
  } //end of constructor

  public void store(Object obj) throws NEDSSSystemException {
	  try{
	    TreatmentAdministeredDT dt = (TreatmentAdministeredDT) obj;
	    if (dt == null)
	      throw new NEDSSSystemException(
	          "Error: try to store null TreatmentAdministeredDT object.");
	    insertTreatmentAdministeredHist(dt);
	  }catch(Exception ex){
		  logger.fatal("Exception  = "+ex.getMessage(), ex);
		  throw new NEDSSSystemException(ex.toString());
	  }
  } //end of store()

  public void store(Collection<Object> coll) throws NEDSSSystemException {
    Iterator<Object> iterator = null;
    try{
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

  public Collection<Object> load(Long intUid, Integer substanceAdminHistSeq) throws
      NEDSSSystemException {
    logger.info("Starts loadObject() for a substance administration history...");
    try{
	    Collection<Object> substanceAdministrationDTColl = selectTreatmentAdministeredHist(
	        intUid.longValue(), substanceAdminHistSeq.intValue());
	
	    return substanceAdministrationDTColl;
    }catch(Exception ex){
		  logger.fatal("Exception  = "+ex.getMessage(), ex);
		  throw new NEDSSSystemException(ex.toString());
	  }
  } //end of load

  ////////////////////////////////////private class methods////////////////////////////////

  private Collection<Object> selectTreatmentAdministeredHist(long intUid,
      int substanceAdminHistSeq) throws
      NEDSSSystemException {

    TreatmentAdministeredDT substanceAdministrationDT = new
        TreatmentAdministeredDT();
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
                   "for selectTreatmentAdministeredHist ", nsex);
      throw new NEDSSSystemException(nsex.toString());
    }

    try {
      preparedStmt = dbConnection.prepareStatement(
          SELECT_TREATMENT_ADMINISTERED_HIST);
      preparedStmt.setLong(1, intUid);
      preparedStmt.setLong(2, substanceAdminHistSeq);
      resultSet = preparedStmt.executeQuery();

      resultSetMetaData = resultSet.getMetaData();
      ArrayList<Object>  prList = new ArrayList<Object> ();
      ArrayList<Object>  reSetList = new ArrayList<Object> ();

      prList = (ArrayList<Object> ) resultSetUtils.mapRsToBeanList(resultSet,
          resultSetMetaData, substanceAdministrationDT.getClass(), prList);

      for (Iterator<Object> anIterator = prList.iterator(); anIterator.hasNext(); ) {
        TreatmentAdministeredDT reSetSubstanceAdmin = (TreatmentAdministeredDT)
            anIterator.next();
        reSetSubstanceAdmin.setItNew(false);
        reSetSubstanceAdmin.setItDirty(false);

        reSetList.add(reSetSubstanceAdmin);
      }
      logger.debug("return substance administration hist collection");
      return reSetList;
    }
    catch (SQLException se) {
      logger.fatal("SQLException while selecting " +
                   "substance administration history; id = " + intUid, se);
      throw new NEDSSSystemException(se.toString());
    }
    catch (Exception ex) {
      logger.fatal("Exception while selecting " +
                   "substance administration vo; id = " + intUid, ex);
      throw new NEDSSSystemException(ex.toString());
    }
    finally {
      closeResultSet(resultSet);
      closeStatement(preparedStmt);
      releaseConnection(dbConnection);
    }

  } //end of selectTreatmentAdministeredHist(...)

  private void insertTreatmentAdministeredHist(TreatmentAdministeredDT dt) throws
      NEDSSSystemException {
    if (dt.getTreatmentUid() != null) {
      int resultCount = 0;

      int i = 1;
      Connection dbConnection = null;
      PreparedStatement pStmt = null;
      try {

        dbConnection = getConnection();
        pStmt = dbConnection.prepareStatement(
            INSERT_TREATMENT_ADMINISTERED_HIST);
        pStmt.setLong(i++, dt.getTreatmentUid().longValue());
        pStmt.setInt(i++, nextSeqNum);

        if (dt.getDoseQty() == null)
          pStmt.setNull(i++, Types.VARCHAR);
        else
          pStmt.setString(i++, dt.getDoseQty());

        if (dt.getDoseQtyUnitCd() == null)
          pStmt.setNull(i++, Types.VARCHAR);
        else
          pStmt.setString(i++, dt.getDoseQtyUnitCd());

        if (dt.getFormCd() == null)
          pStmt.setNull(i++, Types.VARCHAR);
        else
          pStmt.setString(i++, dt.getFormCd());

        if (dt.getFormDescTxt() == null)
          pStmt.setNull(i++, Types.VARCHAR);
        else
          pStmt.setString(i++, dt.getFormDescTxt());

        if (dt.getRateQty() == null)
          pStmt.setNull(i++, Types.VARCHAR);
        else
          pStmt.setString(i++, dt.getRateQty());

        if (dt.getRateQtyUnitCd() == null)
          pStmt.setNull(i++, Types.VARCHAR);
        else
          pStmt.setString(i++, dt.getRateQtyUnitCd());

        if (dt.getRouteCd() == null)
          pStmt.setNull(i++, Types.VARCHAR);
        else
          pStmt.setString(i++, dt.getRouteCd());

        if (dt.getRouteDescTxt() == null)
          pStmt.setNull(i++, Types.VARCHAR);
        else
          pStmt.setString(i++, dt.getRouteDescTxt());

        resultCount = pStmt.executeUpdate();
        ////##!! System.out.println("number of rows inserted into substance admin hist: "+resultCount);
        if (resultCount != 1) {
          throw new NEDSSSystemException(
              "Error: none or more than one entity inserted at a time, resultCount = " +
              resultCount);
        }

      }
      catch (SQLException se) {
    	  logger.fatal("Exception  = "+se.getMessage(), se);
    	  throw new NEDSSDAOSysException("Error: SQLException while inserting\n" +
                                       se.getMessage());
      }
      finally {
        closeStatement(pStmt);
        releaseConnection(dbConnection);
      } //end of finally
    } //end of if
  } //end of insertSubstancAdministrationHist()

} //end of class