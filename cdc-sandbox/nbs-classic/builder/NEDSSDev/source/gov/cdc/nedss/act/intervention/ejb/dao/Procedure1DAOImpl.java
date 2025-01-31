/**
* Title:        Procedure1.java
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

import gov.cdc.nedss.act.intervention.dt.Procedure1DT;
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


public class Procedure1DAOImpl extends BMPBase
{

    static final LogUtils logger =  new LogUtils(Procedure1DAOImpl.class.getName());

    public static final String SELECT_PROCEDURE1 =
    "SELECT intervention_uid \"interventionUid\", "+
    "approach_site_cd \"approachSiteCd\", approach_site_desc_txt \"approachSiteDescTxt\" "+
    "FROM " +
    DataTables.PROCEDURE1_TABLE  +" WHERE intervention_uid = ?";

     public static final String SELECT_PROCEDURE1_UID = "SELECT intervention_uid FROM " +
                      DataTables.PROCEDURE1_TABLE + " WHERE intervention_uid = ?";
     public static final String DELETE_PROCEDURE1 = "DELETE FROM " + DataTables.PROCEDURE1_TABLE +
                    " WHERE intervention_uid = ?";
     public static final String UPDATE_PROCEDURE1 = "UPDATE " + DataTables.PROCEDURE1_TABLE +
     " set approach_site_cd = ?, approach_site_desc_txt  = ?    WHERE  intervention_uid = ?";
     public static final String INSERT_PROCEDURE1 = "INSERT INTO " + DataTables.PROCEDURE1_TABLE +
     "(intervention_uid, approach_site_cd, approach_site_desc_txt) Values (?, ?, ?, ?, ?)";


    public Procedure1DAOImpl()
    {
    }

    public long create(long interventionUID, Collection<Object> coll) throws NEDSSSystemException
    {
    	try{
	        logger.debug("In create method");
	        insertProcedure1s(interventionUID, coll);
	
	        return interventionUID;
    	}catch(Exception ex){
    		logger.fatal("interventionUID: "+interventionUID+" Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    public void store(Collection<Object> coll) throws NEDSSSystemException
    {
    	try{
    		updateProcedure1s(coll);
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    public void remove(long interventionUID) throws  NEDSSSystemException
    {
    	try{
    		removeProcedure1s(interventionUID);
    	}catch(Exception ex){
    		logger.fatal("interventionUID: "+interventionUID+" Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    public Collection<Object> load(long interventionUID) throws NEDSSSystemException
    {
    	try{
	        Collection<Object> inColl = selectProcedure1s(interventionUID);
	        return inColl;
    	}catch(Exception ex){
    		logger.fatal("interventionUID: "+interventionUID+" Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    public Long findByPrimaryKey(long interventionUID) throws NEDSSSystemException
    {
    	try{
	        if (Procedure1Exists(interventionUID))
	            return (new Long(interventionUID));
	        else
	            logger.error("No Procedure1 found for THIS primary key :" + interventionUID);
	            return null;
    	}catch(Exception ex){
    		logger.fatal("interventionUID: "+interventionUID+" Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }


    protected boolean Procedure1Exists (long interventionUID) throws NEDSSSystemException
    {
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        boolean returnValue = false;

        try
        {
            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(SELECT_PROCEDURE1_UID);
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
                            + " Procedure1 = " + interventionUID  , sex);
            throw new NEDSSSystemException( sex.getMessage());
        }
        catch(NEDSSSystemException nsex)
        {
            logger.fatal("Exception while getting dbConnection for checking for an"
                            + " Procedure1 =  " + interventionUID , nsex);
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


    private void insertProcedure1s(long interventionUID, Collection<Object> Procedure1s)
                throws NEDSSSystemException
    {
        Iterator<Object> anIterator = null;

        try
        {
            /**
             * Inserts procedure1
             */
             logger.debug("in insertProcedure1s method");
             for (anIterator = Procedure1s.iterator(); anIterator.hasNext(); )
             {
                Procedure1DT Procedure1 = (Procedure1DT)anIterator.next();
                if (Procedure1 != null)
                insertProcedure1(interventionUID, Procedure1);
                Procedure1.setInterventionUid(new Long(interventionUID));
				//Procedure1.setItNew(false);
				//Procedure1.setItDirty(false);
              }
        }
        catch(Exception ex)
        {
            logger.fatal("Exception while inserting " +
                        "procedure into PROCEDURE1 TABLE: \n", ex);
            throw new NEDSSSystemException( ex.toString() );
        }
        logger.debug("Done inserting Procedure1");
    }//end of inserting Procedure1

    private void insertProcedure1(long interventionUID, Procedure1DT Procedure1)
                throws NEDSSSystemException
    {
        logger.debug("in insertProcedure1 method");
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
                "while inserting Procedure1", nsex);
            throw new NEDSSSystemException(nsex.toString());
        }

        try
        {
            /**
             * Inserts a Procedure1
             */
             preparedStmt = dbConnection.prepareStatement(INSERT_PROCEDURE1);
             int i = 1;

             logger.debug("Procedure1 = " + Procedure1);
             preparedStmt.setLong(i++, interventionUID);
             preparedStmt.setString(i++, Procedure1.getApproachSiteCd());
             preparedStmt.setString(i++, Procedure1.getApproachSiteDescTxt());



             resultCount = preparedStmt.executeUpdate();

             if (resultCount != 1)
                    logger.error
                            ("Error: none or more than one procedure1 inserted at a time, " +
                             "resultCount = " + resultCount);
            else
            {
                Procedure1.setItNew(false);
                Procedure1.setItDirty(false);
            }
        }
        catch(SQLException sex)
        {
                logger.fatal("SQLException while inserting " +
                        "intervention into PROCEDURE1_TABLE: \n", sex);
                throw new NEDSSSystemException( sex.toString() );
        }
               finally
        {
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
		logger.debug("Done inserting a Procedure1");
    }//end of inserting a Procedure1


    private void updateProcedure1s (Collection<Object> Procedure1s) throws NEDSSSystemException
    {
        Procedure1DT Procedure1 = null;
        Iterator<Object> anIterator = null;

        if(Procedure1s != null)
        {
            /**
             * Updates Procedure1
             */
            try
            {
                for(anIterator = Procedure1s.iterator(); anIterator.hasNext(); )
                {
                    Procedure1 = (Procedure1DT)anIterator.next();
                    if(Procedure1 == null)
                    logger.error("Error: Empty Procedure collection");

                    if(Procedure1.isItNew()){
                      logger.debug("is it new? " + Procedure1.isItNew());
                      logger.debug("updateProcedure1s isItNew");
                      insertProcedure1((Procedure1.getInterventionUid()).longValue(), Procedure1);
                    }
                    if(Procedure1.isItDirty()){
                      logger.debug("is it Dirty? " + Procedure1.isItDirty());
                      logger.debug("updateProcedure1s isItDirty");
                      updateProcedure1(Procedure1);
                    }
                    if(Procedure1.isItDelete()){
                      updateProcedure1(Procedure1);
                    }
                }
            }
            catch(Exception ex)
            {
                logger.fatal("Exception while updating " +
                    "Procedure, \n", ex);
                throw new NEDSSSystemException( ex.toString() );
            }
        }
    }//end of updating procedure table

    private void updateProcedure1(Procedure1DT Procedure1)
              throws NEDSSSystemException
    {
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        int resultCount = 0;

        /**
         * Updates a procedure1
         */

        if(Procedure1 != null)
        {
            try
            {
                dbConnection = getConnection();
            }
            catch(NEDSSSystemException nsex)
            {
                logger.fatal("Error obtaining dbConnection " +
                    "for updating procedure", nsex);
                throw new NEDSSSystemException(nsex.toString());
            }

            try
            {
                preparedStmt = dbConnection.prepareStatement(UPDATE_PROCEDURE1);

                int i = 1;

                logger.debug("procedure1 = " + Procedure1.getApproachSiteCd());
                preparedStmt.setString(i++, Procedure1.getApproachSiteCd());
                preparedStmt.setString(i++, Procedure1.getApproachSiteDescTxt());
                preparedStmt.setLong(i++, (Procedure1.getInterventionUid()).longValue());

                resultCount = preparedStmt.executeUpdate();
                logger.debug("Done updating a procedure1");
                if (resultCount != 1)
                logger.error
                            ("Error: none or more than one procedure1 updated at a time, " +
                              "resultCount = " + resultCount);
            }
            catch(SQLException sex)
            {
                logger.fatal("SQLException while updating " +
                    " a procedure1, \n", sex);
                throw new NEDSSSystemException( sex.toString() );
            }
            catch(Exception ex)
            {
                logger.fatal("Exception while updating " +
                    " a procedure1, \n", ex);
                throw new NEDSSSystemException( ex.toString() );
            }
            finally
            {
                closeStatement(preparedStmt);
                releaseConnection(dbConnection);
            }
        }
    }//end of updating a Procedure1

    private Collection<Object> selectProcedure1s (long interventionUID) throws NEDSSSystemException
    {

        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        ResultSetUtils resultSetUtils = new ResultSetUtils();
        Procedure1DT Procedure1 = new Procedure1DT();

        try
        {
            dbConnection = getConnection();
        }
        catch(NEDSSSystemException nsex)
        {
            logger.fatal("SQLException while obtaining database connection " +
                            "for selectProcedure1s " , nsex);
            throw new NEDSSSystemException( nsex.getMessage());
        }

        /**
         * Selects Procedure1
         */
        try
        {
            preparedStmt = dbConnection.prepareStatement(SELECT_PROCEDURE1);
            preparedStmt.setLong(1, interventionUID);
			resultSet = preparedStmt.executeQuery();

            resultSetMetaData = resultSet.getMetaData();
            ArrayList<Object>  orList = new ArrayList<Object> ();
            ArrayList<Object>  reSetList = new ArrayList<Object> ();

            orList = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData, Procedure1.getClass(), orList);

            for(Iterator<Object> anIterator = orList.iterator(); anIterator.hasNext(); )
            {
                Procedure1DT reSetProcedure = (Procedure1DT)anIterator.next();
                reSetProcedure.setItNew(false);
                reSetProcedure.setItDirty(false);
                logger.debug("value of Procedure_cd: " + reSetProcedure.getApproachSiteCd());
                reSetList.add(reSetProcedure);
            }
            logger.debug("return Procedure1 collection");

            return reSetList;
        }
        catch(SQLException se)
        {
            logger.fatal("SQLException while selecting " +
                            "procedure1; id = " + interventionUID , se);
            throw new NEDSSSystemException( se.getMessage());
        }
        catch(ResultSetUtilsException rsuex)
        {
            logger.fatal("Exception while handling result set in selecting " +
                            "Procedure1; id = " + interventionUID, rsuex);
            throw new NEDSSSystemException( rsuex.getMessage());
        }
        catch(Exception ex)
        {
            logger.fatal("Exception while selecting " +
                            "procedure1; id = " + interventionUID, ex);
            throw new NEDSSSystemException( ex.getMessage());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
    }//end of selecting procedure1

    private void removeProcedure1s (long interventionUID) throws NEDSSSystemException
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
                            "for deleting procedure1 " , nsex);
            throw new NEDSSSystemException(nsex.getMessage());
        }

        /**
         * Deletes Procedure1
         */
        try
        {
            preparedStmt = dbConnection.prepareStatement(DELETE_PROCEDURE1);
            preparedStmt.setLong(1, interventionUID);
            resultCount = preparedStmt.executeUpdate();

            if (resultCount != 1)
            {
                logger.error
                ("Error: cannot delete procedure1 from PROCEDURE1_TABLE!! resultCount = " +
                 resultCount);
            }
        }
        catch(SQLException sex)
        {
            logger.fatal("SQLException while removing " +
                            "procedure1; id = " + interventionUID, sex);
            throw new NEDSSSystemException( sex.getMessage());
        }
        finally
        {
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
    }//end of removing Procedure1



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
      logger.debug("Procedure1DAOImpl - Doing the main thing");
      try
      {

//        OrganizationNameDAOImpl orgNameDAOI = new OrganizationNameDAOImpl();


        Procedure1DAOImpl obsDAOI = new Procedure1DAOImpl();
        Long obsuid = new Long(10);
        Long tobsuid = new Long(12);
        Procedure1DT obserDT = new Procedure1DT();
        obserDT.setInterventionUid(obsuid);
        obserDT.setApproachSiteCd("newProcedureCdstore4");
        obserDT.setApproachSiteDescTxt("Lots of text for store4");
        obserDT.setItNew(false);
        obserDT.setItDirty(true);

        Procedure1DT tobserDT = new Procedure1DT();
        tobserDT.setInterventionUid(tobsuid);
        tobserDT.setApproachSiteCd("newProcedureCD");
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
}//end of Procedure1DAOImpl class
