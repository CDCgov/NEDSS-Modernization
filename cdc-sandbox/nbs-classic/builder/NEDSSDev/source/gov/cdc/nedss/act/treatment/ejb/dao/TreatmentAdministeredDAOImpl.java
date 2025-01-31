/**
* Title:        TreatmentAdministeredDAOImpl.java
* Description:  This is the implementation of NEDSSDAOInterface for the
*               TreatmentAdministered value object in the Treatment entity bean.
*               This class encapsulates all the JDBC calls made by the TreatmentEJB
*               for a TreatmentAdministered object. Actual logic of
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

public class TreatmentAdministeredDAOImpl extends BMPBase
{

//for logging
    static final LogUtils logger = new LogUtils(TreatmentAdministeredDAOImpl.class.getName());

    public static final String SELECT_TREATMENT_ADMINISTERED_UID =
    "SELECT treatment_uid FROM  " +
     DataTables.TREATMENT_ADMINISTERED_TABLE +
    " WHERE treatment_uid = ?";

   /* public static final String INSERT_TREATMENT_ADMINISTERED =
     "INSERT INTO " + DataTables.TREATMENT_ADMINISTERED_TABLE  +
     "(treatment_uid, dose_qty, dose_qty_unit_cd, form_cd, form_desc_txt, "+
     "rate_qty, rate_qty_unit_cd, route_cd, route_desc_txt, treatment_administered_seq ) "+
     "VALUES (?, ?, ?, ?, ?, " +
             "?, ?, ?, ?, ?)";
    */

         public static final String INSERT_TREATMENT_ADMINISTERED =
          "INSERT INTO " + DataTables.TREATMENT_ADMINISTERED_TABLE  +
          "(treatment_uid, cd, cd_desc_txt, effective_duration_amt, effective_duration_unit_cd, " +
          "dose_qty, dose_qty_unit_cd, form_cd, form_desc_txt, "+
          "rate_qty, interval_cd, route_cd, route_desc_txt, treatment_administered_seq, effective_from_time ) "+
          "VALUES (?, ?, ?, ?, ?, " +
                  "?, ?, ?, ?, ?, " +
                  "?, ?, ?, ?, ?)";


    public static final String UPDATE_TREATMENT_ADMINISTERED =
    "UPDATE " + DataTables.TREATMENT_ADMINISTERED_TABLE +
    " SET cd = ?, cd_desc_txt = ?, effective_duration_amt = ?, " +
    "effective_duration_unit_cd = ?, dose_qty = ?, dose_qty_unit_cd = ?, form_cd = ?, " +
    "form_desc_txt = ?, "+
    "rate_qty = ?, interval_cd = ?, route_cd = ?, route_desc_txt = ?, effective_from_time = ? "+
    "WHERE treatment_uid = ?";

    public static final String SELECT_TREATMENT_ADMINISTERED =
    "SELECT treatment_uid \"treatmentUid\", cd \"cd\", cd_desc_txt \"cdDescTxt\", "+
    "effective_duration_amt \"effectiveDurationAmt\", effective_duration_unit_cd \"effectiveDurationUnitCd\", " +
    "effective_from_time \"effectiveFromTime\", " +
    "dose_qty \"doseQty\", "+
    "dose_qty_unit_cd \"doseQtyUnitCd\", form_cd \"formCd\",  "+
    "form_desc_txt \"formDescTxt\", rate_qty \"rateQty\", "+
    "interval_cd \"intervalCd\", route_cd \"routeCd\", "+
    "route_desc_txt \"routeDescTxt\" "+
    "FROM " +
    DataTables.TREATMENT_ADMINISTERED_TABLE +" WHERE treatment_uid = ?";

    public static final String DELETE_TREATMENT_ADMINISTERED ="DELETE FROM "
     + DataTables.TREATMENT_ADMINISTERED_TABLE +
    " WHERE treatment_uid = ?";


    public TreatmentAdministeredDAOImpl()
    {
    }

    public long create(long treatmentAdministeredUID, Collection<Object> coll) throws NEDSSSystemException
    {
    	try{
	        logger.debug("In create method");
	        insertTreatmentAdministereds(treatmentAdministeredUID, coll);
	
	        return treatmentAdministeredUID;
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    public void store(Collection<Object> coll) throws NEDSSSystemException
    {
    	try{
    		updateTreatmentAdministered(coll);
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    public void remove(long treatmentAdministeredUID) throws NEDSSDAOSysException, NEDSSSystemException
    {
    	try{
    		removeTreatmentAdministereds(treatmentAdministeredUID);
    	}catch(NEDSSDAOSysException ex){
    		logger.fatal("NEDSSDAOSysException  = "+ex.getMessage(), ex);
    		throw new NEDSSDAOSysException(ex.toString());
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    public Collection<Object> load(long treatmentAdministeredUID) throws NEDSSSystemException
    {
    	try{
	        Collection<Object> inColl = selectTreatmentAdministereds(treatmentAdministeredUID);
	        return inColl;
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    public Long findByPrimaryKey(long treatmentAdministeredUID) throws NEDSSSystemException
    {
    	try{
	        if (TreatmentAdministeredExists(treatmentAdministeredUID))
	            return (new Long(treatmentAdministeredUID));
	        else
	            logger.error("No TreatmentAdministered found for THIS primary key :" + treatmentAdministeredUID);
	            return null;
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }


    protected boolean TreatmentAdministeredExists (long treatmentAdministeredUID) throws NEDSSSystemException
    {
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        boolean returnValue = false;

        try
        {
            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(SELECT_TREATMENT_ADMINISTERED_UID);
                    logger.debug("treatmentAdministeredUID = " + treatmentAdministeredUID);
            preparedStmt.setLong(1, treatmentAdministeredUID);
            resultSet = preparedStmt.executeQuery();

            if (!resultSet.next())
            {
                returnValue = false;
            }
            else
            {
                treatmentAdministeredUID = resultSet.getLong(1);
                returnValue = true;
            }
        }
        catch(SQLException sex)
        {
            logger.fatal("SQLException while checking for an"
                            + " TreatmentAdministered-> " + treatmentAdministeredUID , sex);
            throw new NEDSSSystemException( sex.getMessage());
        }
        catch(NEDSSSystemException nsex)
        {
            logger.fatal("Exception while getting dbConnection for checking for an"
                            + " TreatmentAdministered -> " + treatmentAdministeredUID , nsex);
            throw new NEDSSSystemException( nsex.getMessage());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
        return returnValue;
    }


    private void insertTreatmentAdministereds(long treatmentAdministeredUID, Collection<Object> TreatmentAdministereds)
                throws NEDSSSystemException
    {
        Iterator<Object> anIterator = null;

        try
        {
            /**
             * Inserts substanceAdministration1
             */
             logger.debug("in insertTreatmentAdministereds method");
             for (anIterator = TreatmentAdministereds.iterator(); anIterator.hasNext(); )
             {
                TreatmentAdministeredDT TreatmentAdministered = (TreatmentAdministeredDT)anIterator.next();
                if (TreatmentAdministered != null)
                insertTreatmentAdministered(treatmentAdministeredUID, TreatmentAdministered);
                TreatmentAdministered.setTreatmentUid(new Long(treatmentAdministeredUID));

              }
        }
        catch(Exception ex)
        {
            logger.fatal("Exception while inserting " +
                        "treatmentAdministered into TREATMENT_ADMINISTERED TABLE: \n", ex);
            throw new NEDSSSystemException( ex.getMessage());
        }
        logger.debug("Done inserting TreatmentAdministered");
    }//end of inserting TreatmentAdministered

    private void insertTreatmentAdministered(long treatmentAdministeredUID, TreatmentAdministeredDT treatmentAdministered)
                throws NEDSSSystemException, NEDSSSystemException
    {
        logger.debug("in insertTreatmentAdministered method");
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        int resultCount = 0;

        try
        {
            dbConnection = getConnection();
        }
        catch(NEDSSSystemException nsex)
        {
            logger.fatal("Error obtaining db connection " +
                "while inserting TreatmentAdministered", nsex);
            throw new NEDSSSystemException(nsex.toString());
        }

        try
        {
            /**
             * Inserts a TreatmentAdministered
             */
             preparedStmt = dbConnection.prepareStatement(INSERT_TREATMENT_ADMINISTERED);
             int i = 1;

             logger.debug("TreatmentAdministered = " + treatmentAdministered);
             preparedStmt.setLong(i++, treatmentAdministeredUID);
             preparedStmt.setString(i++, treatmentAdministered.getCd());
             preparedStmt.setString(i++, treatmentAdministered.getCdDescTxt());
             preparedStmt.setString(i++, treatmentAdministered.getEffectiveDurationAmt());
             preparedStmt.setString(i++, treatmentAdministered.getEffectiveDurationUnitCd());
             preparedStmt.setString(i++, treatmentAdministered.getDoseQty());
             preparedStmt.setString(i++, treatmentAdministered.getDoseQtyUnitCd());
             preparedStmt.setString(i++, treatmentAdministered.getFormCd());
             preparedStmt.setString(i++, treatmentAdministered.getFormDescTxt());
             preparedStmt.setString(i++, treatmentAdministered.getRateQty());
             preparedStmt.setString(i++, treatmentAdministered.getIntervalCd());
             preparedStmt.setString(i++, treatmentAdministered.getRouteCd());
             preparedStmt.setString(i++, treatmentAdministered.getRouteDescTxt());
             preparedStmt.setLong(i++, new Long(1).longValue());
			 logger.debug("TreatmentAdministered effective_date = " + treatmentAdministered.getEffectiveFromTime());
			 preparedStmt.setTimestamp(i++, treatmentAdministered.getEffectiveFromTime());

			 logger.debug("Update treatment_administered: " + preparedStmt);
             resultCount = preparedStmt.executeUpdate();

             if (resultCount != 1)
                    logger.error
                            ("Error: none or more than one treatment_administered inserted at a time, " +
                             "resultCount = " + resultCount);
            else
            {
                treatmentAdministered.setItNew(false);
                treatmentAdministered.setItDirty(false);
            }
        }
        catch(SQLException sex)
        {
                logger.fatal("SQLException while inserting " +
                        "treatment_administered into TREATMENT_ADMINISTERED_TABLE: \n", sex);
                throw new NEDSSSystemException( sex.toString() );
        }
                finally
        {
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
logger.debug("Done inserting a TreatmentAdministered");
    }//end of inserting a TreatmentAdministered


    private void updateTreatmentAdministered (Collection<Object> TreatmentAdministereds) throws NEDSSSystemException
    {
        TreatmentAdministeredDT TreatmentAdministered = null;
        Iterator<Object> anIterator = null;

        if(TreatmentAdministereds != null)
        {
            /**
             * Updates TreatmentAdministered
             */
            try
            {
                logger.debug("Updating TreatmentAdministered");
                for(anIterator = TreatmentAdministereds.iterator(); anIterator.hasNext(); )
                {
                    TreatmentAdministered = (TreatmentAdministeredDT)anIterator.next();
                    if(TreatmentAdministered == null)
                    logger.error("Error: Empty TreatmentAdministered collection");

                    if(TreatmentAdministered.isItNew()){
                      logger.debug("is it new? " + TreatmentAdministered.isItNew());
                      logger.debug("updateTreatmentAdministereds isItNew");
                      insertTreatmentAdministered((TreatmentAdministered.getTreatmentUid()).longValue(), TreatmentAdministered);
                    }
                    if(TreatmentAdministered.isItDirty()){
                      logger.debug("is it Dirty? " + TreatmentAdministered.isItDirty());
                      logger.debug("updateTreatmentAdministereds isItDirty");
                      updateTreatmentAdministered(TreatmentAdministered);
                    }
                    if(TreatmentAdministered.isItDelete()){
                      updateTreatmentAdministered(TreatmentAdministered);
                    }
                }
            }
            catch(Exception ex)
            {
                logger.fatal("Exception while updating " +
                    "TreatmentAdministered, \n"+ex.getMessage(), ex);
                throw new NEDSSSystemException( ex.toString() );
            }
        }
    }//end of updating substanceAdministration table

    private void updateTreatmentAdministered(TreatmentAdministeredDT TreatmentAdministered)
              throws NEDSSSystemException
    {
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        int resultCount = 0;

        /**
         * Updates a substanceAdministration1
         */

        if(TreatmentAdministered != null)
        {
            try
            {
                dbConnection = getConnection();
            }
            catch(NEDSSSystemException nsex)
            {
                logger.fatal("Error obtaining dbConnection " +
                    "for updating treatment_administered", nsex);
                throw new NEDSSSystemException(nsex.toString());
            }

            try
            {
                preparedStmt = dbConnection.prepareStatement(UPDATE_TREATMENT_ADMINISTERED);

                int i = 1;

                logger.debug("treatmentAdministered = " + TreatmentAdministered.getTreatmentUid());
                preparedStmt.setString(i++, TreatmentAdministered.getCd());
                preparedStmt.setString(i++, TreatmentAdministered.getCdDescTxt());
                preparedStmt.setString(i++, TreatmentAdministered.getEffectiveDurationAmt());
                preparedStmt.setString(i++, TreatmentAdministered.getEffectiveDurationUnitCd());
                preparedStmt.setString(i++, TreatmentAdministered.getDoseQty());
                preparedStmt.setString(i++, TreatmentAdministered.getDoseQtyUnitCd());
                preparedStmt.setString(i++, TreatmentAdministered.getFormCd());
                preparedStmt.setString(i++, TreatmentAdministered.getFormDescTxt());
                preparedStmt.setString(i++, TreatmentAdministered.getRateQty());
                preparedStmt.setString(i++, TreatmentAdministered.getIntervalCd());
                preparedStmt.setString(i++, TreatmentAdministered.getRouteCd());
                preparedStmt.setString(i++, TreatmentAdministered.getRouteDescTxt());
				preparedStmt.setTimestamp(i++, TreatmentAdministered.getEffectiveFromTime());
                preparedStmt.setLong(i++, (TreatmentAdministered.getTreatmentUid()).longValue());


            //preparedStmt.setLong(i++,TreatmentAdministered.getProgramJurisdictionOid().longValue());
            //preparedStmt.setString(i++,TreatmentAdministered.getSharedInd());

                resultCount = preparedStmt.executeUpdate();
                logger.debug("Done updating a treatment_administered");
                if (resultCount != 1)
                logger.error
                            ("Error: none or more than one substanceAdministration1 updated at a time, " +
                              "resultCount = " + resultCount);
            }
            catch(SQLException sex)
            {
                logger.fatal("SQLException while updating " +
                    " a treatment_administered, \n" , sex);
                throw new NEDSSSystemException(sex.toString() );
            }
            catch(Exception ex)
            {
                logger.fatal("Exception while updating " +
                    " a treatment_administered, \n", ex);
                throw new NEDSSSystemException(ex.toString() );
            }
            finally
            {
                closeStatement(preparedStmt);
                releaseConnection(dbConnection);
            }
        }
    }//end of updating a TreatmentAdministered

    private Collection<Object> selectTreatmentAdministereds (long treatmentAdministeredUID) throws NEDSSSystemException
    {

        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        ResultSetUtils resultSetUtils = new ResultSetUtils();
        TreatmentAdministeredDT TreatmentAdministered = new TreatmentAdministeredDT();

        try
        {
            dbConnection = getConnection();
        }
        catch(NEDSSSystemException nsex)
        {
            logger.fatal("SQLException while obtaining database connection " +
                            "for selectTreatmentAdministered " , nsex);
            throw new NEDSSSystemException( nsex.getMessage());
        }

        /**
         * Selects TreatmentAdministered
         */
        try
        {
            preparedStmt = dbConnection.prepareStatement(SELECT_TREATMENT_ADMINISTERED);
            preparedStmt.setLong(1, treatmentAdministeredUID);
                        logger.debug("test q1");
            resultSet = preparedStmt.executeQuery();
                        logger.debug("test q2");

            resultSetMetaData = resultSet.getMetaData();
            ArrayList<Object>  orList = new ArrayList<Object> ();
            ArrayList<Object>  reSetList = new ArrayList<Object> ();

            orList = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData, TreatmentAdministered.getClass(), orList);

            for(Iterator<Object> anIterator = orList.iterator(); anIterator.hasNext(); )
            {
                TreatmentAdministeredDT reSetTreatmentAdministered = (TreatmentAdministeredDT)anIterator.next();
                reSetTreatmentAdministered.setItNew(false);
                reSetTreatmentAdministered.setItDirty(false);
                logger.debug("\n\nvalue of TreatmentAdministered_interval_cd: " + reSetTreatmentAdministered.getIntervalCd());
                reSetList.add(reSetTreatmentAdministered);
            }
            logger.debug("return TreatmentAdministered collection");

            return reSetList;
        }
        catch(SQLException se)
        {
        	logger.fatal("SQLException TreatmentAdministered; id = "+ treatmentAdministeredUID + " :\n" +se.getMessage(), se);
            throw new NEDSSSystemException("SQLException while selecting " +
                            "treatment_administered; id = " + treatmentAdministeredUID + " :\n" + se.getMessage());
        }
        catch(ResultSetUtilsException rsuex)
        {
            logger.fatal("Exception while handling result set in selecting " +
                            "TreatmentAdministered; id = " + treatmentAdministeredUID, rsuex);
            throw new NEDSSSystemException( rsuex.getMessage());
        }
        catch(Exception ex)
        {
            logger.fatal("Exception while selecting " +
                            "treatment_administered; id = " + treatmentAdministeredUID , ex);
            throw new NEDSSSystemException( ex.getMessage());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
    }//end of selecting substanceAdministration1

    private void removeTreatmentAdministereds (long treatmentAdministeredUID) throws NEDSSSystemException, NEDSSSystemException
    {
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        int resultCount = 0;

        try
        {
            dbConnection = getConnection();
        }
        catch(NEDSSSystemException nsex)
        {
            logger.fatal("SQLException while obtaining database connection " +
                            "for deleting treatment_administered " , nsex);
            throw new NEDSSSystemException(nsex.getMessage());
        }

        /**
         * Deletes TreatmentAdministered
         */
        try
        {
            preparedStmt = dbConnection.prepareStatement(DELETE_TREATMENT_ADMINISTERED);
            preparedStmt.setLong(1, treatmentAdministeredUID);
            resultCount = preparedStmt.executeUpdate();

            if (resultCount != 1)
            {
                logger.error
                ("Error: cannot delete treatment_administered from TREATMENT_ADMINISTERED_TABLE!! resultCount = " +
                 resultCount);
            }
        }
        catch(SQLException sex)
        {
            logger.fatal("SQLException while removing " +
                            "treatment_administered; id = " + treatmentAdministeredUID, sex);
            throw new NEDSSSystemException( sex.getMessage());
        }
        finally
        {
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
    }//end of removing TreatmentAdministered

//end of test main
}//end of SubstanceAdminstrationDAOImpl class
