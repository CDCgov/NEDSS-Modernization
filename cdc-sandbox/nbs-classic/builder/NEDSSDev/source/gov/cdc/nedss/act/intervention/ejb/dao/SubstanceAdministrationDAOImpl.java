/**
* Title:        SubstanceAdminstrationDAOImpl.java
* Description:  This is the implementation of NEDSSDAOInterface for the
*               Procedure1 value object in the Intervention entity bean.
*               This class encapsulates all the JDBC calls made by the InterventionEJB
*               for a Procedure1 object. Actual logic of
*               inserting/reading/updating/deleting the data in relational
*               database tables to mirror the state of InterventionEJB is
*               implemented here.
* Copyright:    Copyright (c) 2001
* Company:      Computer Science Corporation
* @author       Pradeep Kumar Sharma
* @version 1.0
*/

package gov.cdc.nedss.act.intervention.ejb.dao;

import gov.cdc.nedss.act.intervention.dt.SubstanceAdministrationDT;
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

public class SubstanceAdministrationDAOImpl extends BMPBase
{

    //for logging
    static final LogUtils logger = new LogUtils(SubstanceAdministrationDAOImpl.class.getName());

    public static final String SELECT_SUBSTANCE_ADMINISTRATION_UID =
    "SELECT intervention_uid FROM  " +
     DataTables.SUBSTANCE_ADMINISTRATION_TABLE +
    " WHERE intervention_uid = ?";

    public static final String INSERT_SUBSTANCE_ADMINISTRATION =
     "INSERT INTO " + DataTables.SUBSTANCE_ADMINISTRATION_TABLE  +
     "(intervention_uid, dose_qty, dose_qty_unit_cd, form_cd, form_desc_txt, "+
     "rate_qty, rate_qty_unit_cd, route_cd, route_desc_txt ) "+
     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public static final String UPDATE_SUBSTANCE_ADMINISTRATION =
    "UPDATE " + DataTables.SUBSTANCE_ADMINISTRATION_TABLE +
    "SET dose_qty = ?, dose_qty_unit_cd = ?, form_cd = ?, form_desc_txt = ?, "+
    "rate_qty = ?, rate_qty_unit_cd = ?, route_cd = ?, route_desc_txt = ?, "+
    "WHERE intervention_uid = ?";

    public static final String SELECT_SUBSTANCE_ADMINISTRATION =
    "SELECT intervention_uid \"interventionUid\", dose_qty \"doseQty\", "+
    "dose_qty_unit_cd \"doseQtyUnitCd\", form_cd \"formCd\",  "+
    "form_desc_txt \"formDescTxt\", rate_qty \"rateQty\", "+
    "rate_qty_unit_cd \"rateQtyUnitCd\", route_cd \"routeCd\", "+
    "route_desc_txt \"routeDescTxt\" "+
    "FROM " +
    DataTables.SUBSTANCE_ADMINISTRATION_TABLE +" WHERE intervention_uid = ?";

    public static final String DELETE_SUBSTANCE_ADMINISTRATION ="DELETE FROM "
     + DataTables.SUBSTANCE_ADMINISTRATION_TABLE +
    " WHERE intervention_uid = ?";


    public SubstanceAdministrationDAOImpl()
    {
    }

    public long create(long interventionUID, Collection<Object> coll) throws NEDSSSystemException
    {
    	try{

	        logger.debug("In create method");
	        insertSubstanceAdministrations(interventionUID, coll);
	
	        return interventionUID;
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    public void store(Collection<Object> coll) throws NEDSSSystemException
    {
    	try{
    		updateSubstanceAdministrations(coll);
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    public void remove(long interventionUID) throws NEDSSDAOSysException, NEDSSSystemException
    {
    	try{
    		removeSubstanceAdministrations(interventionUID);
    	}catch(NEDSSDAOSysException ex){
    		logger.fatal("NEDSSDAOSysException  = "+ex.getMessage(), ex);
    		throw new NEDSSDAOSysException(ex.toString());
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    public Collection<Object> load(long interventionUID) throws NEDSSSystemException
    {
    	try{
    		Collection<Object> inColl = selectSubstanceAdministrations(interventionUID);
    		return inColl;
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    public Long findByPrimaryKey(long interventionUID) throws NEDSSSystemException
    {
    	try{
	        if (SubstanceAdministrationExists(interventionUID))
	            return (new Long(interventionUID));
	        else
	            logger.error("No SubstanceAdministration found for THIS primary key :" + interventionUID);
	            return null;
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
            
    }


    protected boolean SubstanceAdministrationExists (long interventionUID) throws NEDSSSystemException
    {
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        boolean returnValue = false;

        try
        {
            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(SELECT_SUBSTANCE_ADMINISTRATION_UID);
		    logger.debug("interventionUID = " + interventionUID);
            preparedStmt.setLong(1, interventionUID);
            resultSet = preparedStmt.executeQuery();

            if (!resultSet.next())
            {
                returnValue = false;
            }
            else
            {
                interventionUID = resultSet.getLong(1);
                returnValue = true;
            }
        }
        catch(SQLException sex)
        {
            logger.fatal("SQLException while checking for an"
                            + " SubstanceAdministration-> " + interventionUID , sex);
            throw new NEDSSSystemException( sex.getMessage());
        }
        catch(NEDSSSystemException nsex)
        {
            logger.fatal("Exception while getting dbConnection for checking for an"
                            + " SubstanceAdministration -> " + interventionUID , nsex);
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


    private void insertSubstanceAdministrations(long interventionUID, Collection<Object> SubstanceAdministrations)
                throws NEDSSSystemException
    {
        Iterator<Object> anIterator = null;

        try
        {
            /**
             * Inserts substanceAdministration1
             */
             logger.debug("in insertSubstanceAdministrations method");
             for (anIterator = SubstanceAdministrations.iterator(); anIterator.hasNext(); )
             {
                SubstanceAdministrationDT SubstanceAdministration = (SubstanceAdministrationDT)anIterator.next();
                if (SubstanceAdministration != null)
                insertSubstanceAdministration(interventionUID, SubstanceAdministration);
                SubstanceAdministration.setInterventionUid(new Long(interventionUID));
				//SubstanceAdministration.setItNew(false);
				//SubstanceAdministration.setItDirty(false);
              }
        }
        catch(Exception ex)
        {
            logger.fatal("Exception while inserting " +
                        "substanceAdministration into SUBSTANCE_ADMINISTRATION TABLE: \n"+ex.getMessage(), ex);
            throw new NEDSSSystemException( ex.getMessage());
        }
        logger.debug("Done inserting SubstanceAdministration");
    }//end of inserting SubstanceAdministration

    private void insertSubstanceAdministration(long interventionUID, SubstanceAdministrationDT SubstanceAdministration)
                throws NEDSSSystemException
    {
        logger.debug("in insertSubstanceAdministration method");
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
                "while inserting SubstanceAdministration", nsex);
            throw new NEDSSSystemException(nsex.toString());
        }

        try
        {
            /**
             * Inserts a SubstanceAdministration
             */
             preparedStmt = dbConnection.prepareStatement(INSERT_SUBSTANCE_ADMINISTRATION);
             int i = 1;

             logger.debug("SubstanceAdministration = " + SubstanceAdministration);
             preparedStmt.setLong(i++, interventionUID);
             preparedStmt.setString(i++, SubstanceAdministration.getDoseQty());
             preparedStmt.setString(i++, SubstanceAdministration.getDoseQtyUnitCd());
             preparedStmt.setString(i++, SubstanceAdministration.getFormCd());
             preparedStmt.setString(i++, SubstanceAdministration.getFormDescTxt());
             preparedStmt.setString(i++, SubstanceAdministration.getRateQty());
             preparedStmt.setString(i++, SubstanceAdministration.getRateQtyUnitCd());
			 preparedStmt.setString(i++, SubstanceAdministration.getRouteCd());
			 preparedStmt.setString(i++, SubstanceAdministration.getRouteDescTxt());

            preparedStmt.setLong(i++,SubstanceAdministration.getProgramJurisdictionOid().longValue());
            preparedStmt.setString(i++,SubstanceAdministration.getSharedInd());


             resultCount = preparedStmt.executeUpdate();

             if (resultCount != 1)
                    logger.error
                            ("Error: none or more than one substanceAdministration1 inserted at a time, " +
                             "resultCount = " + resultCount);
            else
            {
                SubstanceAdministration.setItNew(false);
                SubstanceAdministration.setItDirty(false);
            }
        }
        catch(SQLException sex)
        {
                logger.fatal("SQLException while inserting " +
                        "intervention into SUBSTANCE_ADMINISTRATION_TABLE: \n", sex);
                throw new NEDSSSystemException( sex.toString() );
        }
                finally
        {
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
logger.debug("Done inserting a SubstanceAdministration");
    }//end of inserting a SubstanceAdministration


    private void updateSubstanceAdministrations (Collection<Object> SubstanceAdministrations) throws NEDSSSystemException
    {
        SubstanceAdministrationDT SubstanceAdministration = null;
        Iterator<Object> anIterator = null;

        if(SubstanceAdministrations != null)
        {
            /**
             * Updates SubstanceAdministration
             */
            try
            {
                logger.debug("Updating SubstanceAdministration");
                for(anIterator = SubstanceAdministrations.iterator(); anIterator.hasNext(); )
                {
                    SubstanceAdministration = (SubstanceAdministrationDT)anIterator.next();
                    if(SubstanceAdministration == null)
                    logger.error("Error: Empty SubstanceAdministration collection");

                    if(SubstanceAdministration.isItNew()){
                      logger.debug("is it new? " + SubstanceAdministration.isItNew());
                      logger.debug("updateSubstanceAdministrations isItNew");
                      insertSubstanceAdministration((SubstanceAdministration.getInterventionUid()).longValue(), SubstanceAdministration);
                    }
                    if(SubstanceAdministration.isItDirty()){
                      logger.debug("is it Dirty? " + SubstanceAdministration.isItDirty());
                      logger.debug("updateSubstanceAdministrations isItDirty");
                      updateSubstanceAdministration(SubstanceAdministration);
                    }
                    if(SubstanceAdministration.isItDelete()){
                      updateSubstanceAdministration(SubstanceAdministration);
                    }
                }
            }
            catch(Exception ex)
            {
                logger.fatal("Exception while updating " +
                    "SubstanceAdministration, \n", ex);
                throw new NEDSSSystemException( ex.toString() );
            }
        }
    }//end of updating substanceAdministration table

    private void updateSubstanceAdministration(SubstanceAdministrationDT SubstanceAdministration)
              throws NEDSSSystemException
    {
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        int resultCount = 0;

        /**
         * Updates a substanceAdministration1
         */

        if(SubstanceAdministration != null)
        {
            try
            {
                dbConnection = getConnection();
            }
            catch(NEDSSSystemException nsex)
            {
                logger.fatal("Error obtaining dbConnection " +
                    "for updating substanceAdministration", nsex);
                throw new NEDSSSystemException(nsex.toString());
            }

            try
            {
                preparedStmt = dbConnection.prepareStatement(UPDATE_SUBSTANCE_ADMINISTRATION);

                int i = 1;

                logger.debug("substanceAdministration1 = " + SubstanceAdministration.getDoseQty());
                preparedStmt.setString(i++, SubstanceAdministration.getDoseQty());
                preparedStmt.setString(i++, SubstanceAdministration.getDoseQtyUnitCd());
                preparedStmt.setString(i++, SubstanceAdministration.getFormCd());
                preparedStmt.setString(i++, SubstanceAdministration.getFormDescTxt());
				preparedStmt.setString(i++, SubstanceAdministration.getRateQty());
                preparedStmt.setString(i++, SubstanceAdministration.getRateQtyUnitCd());
			    preparedStmt.setString(i++, SubstanceAdministration.getRouteCd());
			    preparedStmt.setString(i++, SubstanceAdministration.getRouteDescTxt());
                preparedStmt.setLong(i++, (SubstanceAdministration.getInterventionUid()).longValue());


	            preparedStmt.setLong(i++,SubstanceAdministration.getProgramJurisdictionOid().longValue());
	            preparedStmt.setString(i++,SubstanceAdministration.getSharedInd());

                resultCount = preparedStmt.executeUpdate();
                logger.debug("Done updating a substanceAdministration1");
                if (resultCount != 1)
                logger.error
                            ("Error: none or more than one substanceAdministration1 updated at a time, " +
                              "resultCount = " + resultCount);
            }
            catch(SQLException sex)
            {
                logger.fatal("SQLException while updating " +
                    " a substanceAdministration1, \n" , sex);
                throw new NEDSSSystemException(sex.toString() );
            }
            catch(Exception ex)
            {
                logger.fatal("Exception while updating " +
                    " a substanceAdministration1, \n", ex);
                throw new NEDSSSystemException(ex.toString() );
            }
            finally
            {
                closeStatement(preparedStmt);
                releaseConnection(dbConnection);
            }
        }
    }//end of updating a SubstanceAdministration

    private Collection<Object> selectSubstanceAdministrations (long interventionUID) throws NEDSSSystemException
    {

        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        ResultSetUtils resultSetUtils = new ResultSetUtils();
        SubstanceAdministrationDT SubstanceAdministration = new SubstanceAdministrationDT();

        try
        {
            dbConnection = getConnection();
        }
        catch(NEDSSSystemException nsex)
        {
            logger.fatal("SQLException while obtaining database connection " +
                            "for selectSubstanceAdministrations " , nsex);
            throw new NEDSSSystemException( nsex.getMessage());
        }

        /**
         * Selects SubstanceAdministration
         */
        try
        {
            preparedStmt = dbConnection.prepareStatement(SELECT_SUBSTANCE_ADMINISTRATION);
            preparedStmt.setLong(1, interventionUID);
			logger.debug("test q1");
            resultSet = preparedStmt.executeQuery();
			logger.debug("test q2");

            resultSetMetaData = resultSet.getMetaData();
            ArrayList<Object>  orList = new ArrayList<Object> ();
            ArrayList<Object>  reSetList = new ArrayList<Object> ();

            orList = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData, SubstanceAdministration.getClass(), orList);

            for(Iterator<Object> anIterator = orList.iterator(); anIterator.hasNext(); )
            {
                SubstanceAdministrationDT reSetSubstanceAdministration = (SubstanceAdministrationDT)anIterator.next();
                reSetSubstanceAdministration.setItNew(false);
                reSetSubstanceAdministration.setItDirty(false);
                logger.debug("value of SubstanceAdministration_cd: " + reSetSubstanceAdministration.getDoseQty());
                reSetList.add(reSetSubstanceAdministration);
            }
            logger.debug("return SubstanceAdministration collection");

            return reSetList;
        }
        catch(SQLException se)
        {
            throw new NEDSSSystemException("SQLException while selecting " +
                            "substanceAdministration1; id = " + interventionUID + " :\n" + se.getMessage(), se);
        }
        catch(ResultSetUtilsException rsuex)
        {
            logger.fatal("Exception while handling result set in selecting " +
                            "SubstanceAdministration; id = " + interventionUID, rsuex);
            throw new NEDSSSystemException( rsuex.getMessage());
        }
        catch(Exception ex)
        {
            logger.fatal("Exception while selecting " +
                            "substanceAdministration1; id = " + interventionUID , ex);
            throw new NEDSSSystemException( ex.getMessage());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
    }//end of selecting substanceAdministration1

    private void removeSubstanceAdministrations (long interventionUID) throws NEDSSSystemException
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
                            "for deleting substanceAdministration1 " , nsex);
            throw new NEDSSSystemException(nsex.getMessage());
        }

        /**
         * Deletes SubstanceAdministration
         */
        try
        {
            preparedStmt = dbConnection.prepareStatement(DELETE_SUBSTANCE_ADMINISTRATION);
            preparedStmt.setLong(1, interventionUID);
            resultCount = preparedStmt.executeUpdate();

            if (resultCount != 1)
            {
                logger.error
                ("Error: cannot delete substanceAdministration1 from SUBSTANCE_ADMINISTRATION_TABLE!! resultCount = " +
                 resultCount);
            }
        }
        catch(SQLException sex)
        {
            logger.fatal("SQLException while removing " +
                            "substanceAdministration1; id = " + interventionUID, sex);
            throw new NEDSSSystemException( sex.getMessage());
        }
        finally
        {
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
    }//end of removing SubstanceAdministration



    //overriding nedssutils getconnection in order to run testmain
/*    protected synchronized Connection getConnection(){
    Connection conn = null;
    try{
      Class.forName("com.sssw.jdbc.mss.odbc.AgOdbcDriver");
      dbConnection = DriverManager.getConnection("jdbc:sssw:odbc:nedss1", "sa","sapasswd");

    } catch (ClassNotFoundException cnf) {
      logger.debug("Can not load Database Driver in main try");
    } catch (SQLException se) {
      logger.debug(se);
    }
    return dbConnection;
}
*/
//test main
/*    public static void main(String args[])
    {
      logger.debug("SubstanceAdminstrationDAOImpl - Doing the main thing");
      try
      {

//        OrganizationNameDAOImpl orgNameDAOI = new OrganizationNameDAOImpl();


        SubstanceAdminstrationDAOImpl obsDAOI = new SubstanceAdminstrationDAOImpl();
        Long obsuid = new Long(10);
        Long tobsuid = new Long(12);
        SubstanceAdministrationDT obserDT = new SubstanceAdministrationDT();
        obserDT.setInterventionUid(obsuid);
        obserDT.setApproachSiteCd("newSubstanceAdministrationCdstore4");
        obserDT.setApproachSiteDescTxt("Lots of text for store4");
        obserDT.setItNew(false);
        obserDT.setItDirty(true);

        SubstanceAdministrationDT tobserDT = new SubstanceAdministrationDT();
        tobserDT.setInterventionUid(tobsuid);
        tobserDT.setApproachSiteCd("newSubstanceAdministrationCD");
        tobserDT.setApproachSiteDescTxt("second collection entry");
        tobserDT.setItNew(false);
        tobserDT.setItDirty(true);

        Collection<Object>  cObserDT = new ArrayList<Object> ();
        logger.debug("obserDT.isItNew: " + obserDT.isItNew());
        logger.debug("obserDT.isDirty: " + obserDT.isItDirty());
        cObserDT.add(obserDT);
        cObserDT.add(tobserDT);
        //NedssUtils nu = new NedssUtils();
        //Connection dbConnection = nu.getTestConnection();

        obsDAOI.store(cObserDT);
        logger.debug("went through without errors...i think.. " );//+ obserDT.getInterventionUid);
        //dbConnection.close();
//        ArrayList<Object> cObserDT = new ArrayList<Object> ();
//        cObserDT.add(obserDT);


      }
      catch(Exception e)
      {
        logger.debug("\n\nInterventionRootDAOImpl ERROR : Not good = \n" + e);
      }
    }
*/
//end of test main
}//end of SubstanceAdminstrationDAOImpl class
