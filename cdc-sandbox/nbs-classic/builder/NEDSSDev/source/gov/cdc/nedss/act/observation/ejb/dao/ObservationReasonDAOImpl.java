/**
* Name:		ObservationReasonDAOImpl.java
* Description:	This is the implementation of NEDSSDAOInterface for the
*               ObservationReason value object in the Observation entity bean.
*               This class encapsulates all the JDBC calls made by the ObservationEJB
*               for a ObservationReason object. Actual logic of
*               inserting/reading/updating/deleting the data in relational
*               database tables to mirror the state of ObservationEJB is
*               implemented here.
* Copyright:	Copyright (c) 2001
* Company: 	Computer Sciences Corporation
* @author	John Park, Brent Chen, & NEDSS Development Team
* @version	1.0
*/

package gov.cdc.nedss.act.observation.ejb.dao;


import gov.cdc.nedss.act.observation.dt.ObservationReasonDT;
import gov.cdc.nedss.act.sqlscript.WumSqlQuery;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.systemservice.exception.ResultSetUtilsException;
import gov.cdc.nedss.util.BMPBase;
import gov.cdc.nedss.util.DataTables;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.ResultSetUtils;

import java.util.*;
import java.sql.*;
import java.math.BigDecimal;

import javax.sql.DataSource;
import javax.naming.InitialContext;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.ejb.FinderException;
import javax.ejb.DuplicateKeyException;
import javax.ejb.CreateException;
import javax.ejb.RemoveException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;


public class ObservationReasonDAOImpl extends BMPBase
{
    //For logging
    static final LogUtils logger = new LogUtils(ObservationReasonDAOImpl.class.getName());

    public ObservationReasonDAOImpl()
    {
    }

    public long create(long observationUID, Collection<Object> coll) throws NEDSSSystemException
    {

    	try{
	        logger.debug("In create method");
	        insertObservationReasons(observationUID, coll);
	
	        return observationUID;
    	}catch(Exception ex){
    		logger.fatal("observationUID: "+observationUID+" Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    public void store(Collection<Object> coll) throws  NEDSSSystemException
    {
    	try{
    		updateObservationReasons(coll);
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    public void remove(long observationUID) throws  NEDSSSystemException
    {
    	try{
    		removeObservationReasons(observationUID);
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    public Collection<Object> load(long observationUID) throws
        NEDSSSystemException
    {
    	try{
	        Collection<Object> orColl = selectObservationReasons(observationUID);
	        return orColl;
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    public Long findByPrimaryKey(long observationUID) throws
		NEDSSSystemException
    {
    	try{
	        if (observationReasonExists(observationUID))
	            return (new Long(observationUID));
	        else
	            logger.error("No observation reason found for THIS primary key :" + observationUID);
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
            return null;
    }


    protected boolean observationReasonExists (long observationUID) throws
            NEDSSSystemException
    {
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        boolean returnValue = false;

        try
        {
            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(WumSqlQuery.SELECT_OBSERVATION_UID);
            logger.debug("observationUID = " + observationUID);
            preparedStmt.setLong(1, observationUID);
            resultSet = preparedStmt.executeQuery();

            if (!resultSet.next())
            {
                returnValue = false;
            }
            else
            {
                observationUID = resultSet.getLong(1);
                returnValue = true;
            }
        }
        catch(SQLException sex)
        {
            logger.fatal("SQLException while checking for an"
                            + " observation reason-> " + observationUID, sex);
            throw new NEDSSDAOSysException( sex.getMessage());
        }
        catch(NEDSSSystemException nsex)
        {
            logger.fatal("Exception while getting dbConnection for checking for an"
                            + " observation reason -> " + observationUID, nsex);
            throw new NEDSSDAOSysException( nsex.getMessage());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
        return returnValue;
    }


    private void insertObservationReasons(long observationUID, Collection<Object> observationReasons)
                throws NEDSSSystemException
    {
        Iterator<Object> anIterator = null;

        try
        {
            /**
             * Inserts observation reasons
             */
             logger.debug("in insertObservationReasons method");
             for (anIterator = observationReasons.iterator(); anIterator.hasNext(); )
             {
                ObservationReasonDT observationReason = (ObservationReasonDT)anIterator.next();
                if (observationReason != null)
                insertObservationReason(observationUID, observationReason);
                //throw new NEDSSObservationDAOAppException("Empty observation reason collection");
                observationReason.setObservationUid(new Long(observationUID));
              }
        }
        catch(Exception ex)
        {
            logger.fatal("observationUID: "+observationUID+" Exception while inserting " +
                        "observation reason into OBSERVATION_REASON_TABLE: \n", ex);
            throw new NEDSSDAOSysException( ex.toString() );
        }
        logger.debug("Done inserting Observation Reasons");
    }//end of inserting observation Reasons

    private void insertObservationReason(long observationUID, ObservationReasonDT observationReason)
                throws NEDSSSystemException
    {
        logger.debug("in insertObservationReason method");
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
                "while inserting Observation reason", nsex);
            throw new NEDSSSystemException(nsex.toString());
        }

        try
        {
            /**
             * Inserts a observation reason
             */
             logger.debug("1 insertobservation");
             preparedStmt = dbConnection.prepareStatement(WumSqlQuery.INSERT_OBSERVATION_REASON);
             logger.debug("2 insertobservation");
             int i = 1;

             logger.debug("observation Reason = " + observationReason);
             preparedStmt.setLong(i++, observationUID);
             preparedStmt.setString(i++, observationReason.getReasonCd());
             preparedStmt.setString(i++, observationReason.getReasonDescTxt());
             resultCount = preparedStmt.executeUpdate();

             if (resultCount != 1)
                    logger.error
                            ("Error: none or more than one observation reasons inserted at a time, " +
                             "resultCount = " + resultCount);
            else
            {
                observationReason.setItNew(false);
                observationReason.setItDirty(false);
                observationReason.setItDelete(false);
            }
        }
        catch(SQLException sex)
        {
                logger.fatal("SQLException while inserting " +
                        "observation reason into OBSERVATION_REASON_TABLE: \n" , sex);
                throw new NEDSSDAOSysException( "Table Name : "+ DataTables.OBSERVATION_REASON_TABLE + "  For observationUID: "+observationUID+"  "+sex.toString(), sex );
        }
        finally
        {
           // closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
logger.debug("Done inserting a observation reason");
    }//end of inserting a observation reason


    private void updateObservationReasons (Collection<Object> observationReasons) throws NEDSSSystemException
    {
        ObservationReasonDT observationReason = null;
        Iterator<Object> anIterator = null;
        if(observationReasons != null)
        {
            /**
             * Updates obseration reasons
             */
            try
            {

                logger.debug("Updating Observation Reasons");
                for(anIterator = observationReasons.iterator(); anIterator.hasNext(); )
                {
                    observationReason = (ObservationReasonDT)anIterator.next();
                    if(observationReason != null && observationReason.getObservationUid() != null)
                   {
                      //follow the order of precedence
                      if(observationReason.isItDelete())
                      {
                        remove (observationReason.getObservationUid().longValue());
                      }
                      else if(observationReason.isItNew())
                      {
                        insertObservationReason((observationReason.getObservationUid()).longValue(), observationReason);
                      }
                      else if(observationReason.isItDirty())
                      {
                        updateObservationReason(observationReason);
                      }
                  }
                  else
                    logger.error("Error: Empty observation reason collection");
                }
            }
            catch(Exception ex)
            {
                logger.fatal("Exception while updating " +
                    "observation reasons, \n", ex);
                throw new NEDSSDAOSysException( ex.toString() );
            }
        }
    }//end of updating observation reaon table

    private void updateObservationReason(ObservationReasonDT observationReason)
              throws NEDSSSystemException
    {
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        int resultCount = 0;

        /**
         * Updates a observation reason
         */

        if(observationReason != null && observationReason.getObservationUid() != null)
        {
            try
            {
                dbConnection = getConnection();
            }
            catch(NEDSSSystemException nsex)
            {
                logger.fatal("Error obtaining dbConnection " +
                    "for updating observation reason", nsex);
                throw new NEDSSSystemException(nsex.toString());
            }

            try
            {
                preparedStmt = dbConnection.prepareStatement(WumSqlQuery.UPDATE_OBSERVATION_REASON);

                int i = 1;

                logger.debug("observation reason = " + observationReason.getReasonCd());

                preparedStmt.setString(i++, observationReason.getReasonDescTxt());
                preparedStmt.setLong(i++, (observationReason.getObservationUid()).longValue());
                preparedStmt.setString(i++, observationReason.getReasonCd());

                resultCount = preparedStmt.executeUpdate();
                logger.debug("Done updating a observation reason");
                if (resultCount != 1)
                logger.error
                            ("Error: none or more than one observation reason updated at a time, " +
                              "resultCount = " + resultCount);
            }
            catch(SQLException sex)
            {
                logger.fatal("SQLException while updating " +
                    " a observation reason, \n", sex);
                throw new NEDSSDAOSysException("Table Name : "+ DataTables.OBSERVATION_REASON_TABLE +"  "+sex.toString(), sex);
            }
            catch(Exception ex)
            {
                logger.fatal("Exception while updating " +
                    " a observation reason, \n" , ex);
                throw new NEDSSDAOSysException( ex.toString() );
            }
            finally
            {
                closeStatement(preparedStmt);
                releaseConnection(dbConnection);
            }
        }
    }//end of updating a observation reason

    private Collection<Object> selectObservationReasons (long observationUID) throws NEDSSSystemException
    {

        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        ResultSetUtils resultSetUtils = new ResultSetUtils();
        ObservationReasonDT observationReason = new ObservationReasonDT();

        try
        {
            dbConnection = getConnection();
        }
        catch(NEDSSSystemException nsex)
        {
            logger.fatal("SQLException while obtaining database connection " +
                            "for selectObservationReasons " , nsex);
            throw new NEDSSSystemException(nsex.getMessage());
        }

        /**
         * Selects observation reasons
         */
        try
        {
            preparedStmt = dbConnection.prepareStatement(WumSqlQuery.SELECT_OBSERVATION_REASONS);
            preparedStmt.setLong(1, observationUID);
            resultSet = preparedStmt.executeQuery();
            resultSetMetaData = resultSet.getMetaData();
            ArrayList<Object>  orList = new ArrayList<Object> ();
            ArrayList<Object>  reSetList = new ArrayList<Object> ();

            orList = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData, observationReason.getClass(), orList);

            for(Iterator<Object> anIterator = orList.iterator(); anIterator.hasNext(); )
            {
                ObservationReasonDT reSetReason = (ObservationReasonDT)anIterator.next();
                reSetReason.setItNew(false);
                reSetReason.setItDirty(false);
                logger.debug("value of reason_cd: " + reSetReason.getReasonCd());
                reSetList.add(reSetReason);
            }
            logger.debug("return observation reason collection");

            return reSetList;
        }
        catch(SQLException se)
        {
            logger.fatal("SQLException while selecting " +
                            "observation reason; id = " + observationUID, se);
            throw new NEDSSDAOSysException( se.getMessage());
        }
        catch(ResultSetUtilsException rsuex)
        {
            logger.fatal("Exception while handling result set in selecting " +
                            "observation reason; id = " + observationUID, rsuex);
            throw new NEDSSDAOSysException( rsuex.getMessage());
        }
        catch(Exception ex)
        {
            logger.fatal("Exception while selecting " +
                            "observation reason; id = " + observationUID, ex);
            throw new NEDSSDAOSysException( ex.getMessage());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
    }//end of selecting observation reasons

    private void removeObservationReasons (long observationUID) throws NEDSSSystemException
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
                            "for deleting observation reasons " , nsex);
            throw new NEDSSSystemException( nsex.getMessage());
        }

        /**
         * Deletes Observation Reasons
         */
        try
        {
            preparedStmt = dbConnection.prepareStatement(WumSqlQuery.DELETE_OBSERVATION_REASONS);
            preparedStmt.setLong(1, observationUID);
            resultCount = preparedStmt.executeUpdate();

            if (resultCount != 1)
            {
                logger.error
                ("Error: cannot delete observation reason from OBSERVATION_REASON_TABLE!! resultCount = " +
                 resultCount);
            }
        }
        catch(SQLException sex)
        {
            logger.fatal("SQLException while removing " +
                            "observation reasons; id = " + observationUID , sex);
            throw new NEDSSDAOSysException( sex.getMessage());
        }
        finally
        {
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
    }//end of removing observation reasons

}//end of ObservationReasonDAOImpl class
