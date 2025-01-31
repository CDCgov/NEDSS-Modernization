/**
* Name:        EntityLocParticipationHistDAOImpl.java
* Description:    This is the implementation of NEDSSDAOInterface for old the
*               EntityLocParticipationHist value object inserting into EntityLocParticipationHist table.
* Copyright:    Copyright (c) 2001
* Company:     Computer Sciences Corporation
* @author    nedss development team
* @version    1.0
*/
package gov.cdc.nedss.locator.dao;

import gov.cdc.nedss.entity.sqlscript.NEDSSSqlQuery;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.locator.dt.EntityLocatorParticipationDT;
import gov.cdc.nedss.systemservice.exception.ResultSetUtilsException;
import gov.cdc.nedss.util.BMPBase;
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


public class EntityLocatorParticipationHistDAOImpl
    extends BMPBase
{

    //For logging
    static final LogUtils logger = new LogUtils(EntityLocatorParticipationHistDAOImpl.class.getName());
    private int versionCtrlNbr = -1;

    //for the time being, using default change_user_id for testing
    private long defaultChangeUserId = 0;

    /*
        //for testing
        NedssUtils nu = new NedssUtils();
        Connection dbConnection = nu.getTestConnection();

    */

    /**
     * Creates a new EntityLocatorParticipationHistDAOImpl object.
     *
     * @throws NEDSSDAOSysException
     * @throws NEDSSSystemException
     */
    public EntityLocatorParticipationHistDAOImpl()
                                          throws NEDSSDAOSysException,
                                                 NEDSSSystemException
    {
    }

    /**
     * Creates a new EntityLocatorParticipationHistDAOImpl object.
     *
     * @param versionCtrlNbr an Integer primitive type object
     * @throws NEDSSDAOSysException
     * @throws NEDSSSystemException
     */
    public EntityLocatorParticipationHistDAOImpl(int versionCtrlNbr)
                                          throws NEDSSDAOSysException,
                                                 NEDSSSystemException
    {
        this.versionCtrlNbr = versionCtrlNbr;
    }

    /**
     * A data access method used to load the entity locator participation object
     * @param entityUID a Long entity uid object
     * @param histSeq an Integer entity history sequence object
     * @return a Collection<Object> object
     * @throws NEDSSSystemException
     */
    public Collection<Object> load(Long entityUID, Integer histSeq)
                    throws NEDSSSystemException
    {
    	try{
	        Collection<Object> elpColl = selectloadEntityLocatorParticipationHist(entityUID.longValue(),
	                                                                      histSeq.intValue());
	
	        return elpColl;
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    /**
     * A private data access method used to load the entity locator participation object
     * used by the load method
     * @param entityUID a Long entity uid object
     * @param histSeq an Integer entity history sequence object
     * @return a Collection<Object> object
     * @throws NEDSSSystemException
     */
    private Collection<Object> selectloadEntityLocatorParticipationHist(long entityUID,
                                                                int histSeq)
        throws NEDSSSystemException
    {

        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        EntityLocatorParticipationDT entityLocatorDT = new EntityLocatorParticipationDT();
        ResultSetUtils resultSetUtils = new ResultSetUtils();
        Connection dbConnection = null;

        try
        {
            dbConnection = getConnection();
        }
        catch (NEDSSSystemException nsex)
        {
            logger.fatal(
                    "SQLException while obtaining database connection " +
                    "for selectloadEntityLocatorParticipationHist ", nsex);
            throw new NEDSSSystemException(nsex.toString());
        }

        try
        {
            preparedStmt = dbConnection.prepareStatement(
                                   NEDSSSqlQuery.SELECT_ENTITY_LOCATOR_PARTICIPATION_HIST);
            preparedStmt.setLong(1, entityUID);
            preparedStmt.setLong(2, histSeq);
            resultSet = preparedStmt.executeQuery();
            resultSetMetaData = resultSet.getMetaData();

            ArrayList<Object>  entityLocList = new ArrayList<Object> ();
            ArrayList<Object>  reSetList = new ArrayList<Object> ();
            entityLocList = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(
                                    resultSet, resultSetMetaData,
                                    entityLocatorDT.getClass(), entityLocList);

            for (Iterator<Object> anIterator = entityLocList.iterator();
                 anIterator.hasNext();)
            {

                EntityLocatorParticipationDT reSetName = (EntityLocatorParticipationDT)anIterator.next();
                reSetName.setItNew(false);
                reSetName.setItDirty(false);
                reSetList.add(reSetName);
            }

            logger.debug("Return entity locator history collection");

            return reSetList;
        }
        catch (ResultSetUtilsException resue)
        {
        	logger.fatal("ResultSetUtilsException  = "+resue.getMessage(), resue);

            return null;
        }
        catch (SQLException sex)
        {
            logger.fatal(
                    "SQLException while loading " +
                    "a EntityLocatorParticipation, entityUID = " +
                    entityUID, sex);
            throw new NEDSSDAOSysException(sex.toString());
        }
        catch (Exception ex)
        {
            logger.fatal(
                    "Exception while loading " +
                    "a EntityLocatorParticipation, entityUID = " +
                    entityUID, ex);
            throw new NEDSSSystemException(ex.toString());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
    }

    /**
     * A data access method used to update the entity locator participation object
     * @param coll a Collection<Object> object
     * @throws NEDSSDAOSysException
     * @throws NEDSSSystemException
     */
    public void store(Collection<Object> coll)
               throws NEDSSDAOSysException, NEDSSSystemException
    {
    	try{
	        Iterator<Object> iterator = null;
	
	        if (coll != null)
	        {
	            iterator = coll.iterator();
	
	            while (iterator.hasNext())
	            {
	                store(iterator.next());
	            }
	        }
    	}catch(NEDSSDAOSysException ex){
    		logger.fatal("NEDSSDAOSysException  = "+ex.getMessage(), ex);
    		throw new NEDSSDAOSysException(ex.toString());
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    /**
     * A data access method used to update the entity locator participation object
     * @param obj an Object object
     * @throws NEDSSDAOSysException
     * @throws NEDSSSystemException
     */
    public void store(Object obj)
               throws NEDSSDAOSysException, NEDSSSystemException
    {
    	try{
	        EntityLocatorParticipationDT dt = (EntityLocatorParticipationDT)obj;
	
	        if (dt == null)
	            logger.error(
	                    "Error: try to store null EntityLocatorParticipationDT object.");
	
	        insertEntityLocParticipationHist(dt);
    	}catch(NEDSSDAOSysException ex){
    		logger.fatal("NEDSSDAOSysException  = "+ex.getMessage(), ex);
    		throw new NEDSSDAOSysException(ex.toString());
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    /**
     * A private data access method used to update the entity locator participation object
     * used by the store method
     * @param obj an Object object
     * @throws NEDSSDAOSysException
     * @throws NEDSSSystemException
     */
    private void insertEntityLocParticipationHist(EntityLocatorParticipationDT dt)
                                           throws NEDSSDAOSysException,
                                                  NEDSSSystemException
    {

        int resultCount = 0;

        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;

        try
        {
            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(
                                   NEDSSSqlQuery.INSERT_ENTITY_LOC_PARC_HIST);

            //setPreparedStatement(preparedStmt, dt);
            int i = 1;
            preparedStmt.setLong(i++, dt.getEntityUid().longValue());
            preparedStmt.setLong(i++, dt.getLocatorUid().longValue());
            preparedStmt.setInt(i++, versionCtrlNbr);
            preparedStmt.setString(i++, dt.getAddReasonCd());

            if (dt.getAddTime() == null)
                preparedStmt.setNull(i++, Types.TIMESTAMP);
            else
                preparedStmt.setTimestamp(i++, dt.getAddTime());

            if (dt.getAddUserId() == null)
                preparedStmt.setNull(i++, Types.BIGINT);
            else
                preparedStmt.setLong(i++, dt.getAddUserId().longValue());

            preparedStmt.setString(i++, dt.getCd());
            preparedStmt.setString(i++, dt.getCdDescTxt());
            preparedStmt.setString(i++, dt.getClassCd());
            preparedStmt.setString(i++, dt.getDurationAmt());
            preparedStmt.setString(i++, dt.getDurationUnitCd());

            if (dt.getFromTime() == null)
                preparedStmt.setNull(i++, Types.TIMESTAMP);
            else
                preparedStmt.setTimestamp(i++, dt.getFromTime());

            preparedStmt.setString(i++, dt.getLastChgReasonCd());

            if (dt.getLastChgTime() == null)
                preparedStmt.setNull(i++, Types.TIMESTAMP);
            else
                preparedStmt.setTimestamp(i++, dt.getLastChgTime());

            if (dt.getLastChgUserId() == null)
                preparedStmt.setNull(i++, Types.BIGINT);
            else
                preparedStmt.setLong(i++, dt.getLastChgUserId().longValue());

            preparedStmt.setString(i++, dt.getLocatorDescTxt());
            preparedStmt.setString(i++, dt.getRecordStatusCd());

            if (dt.getRecordStatusTime() == null)
                preparedStmt.setNull(i++, Types.TIMESTAMP);
            else
                preparedStmt.setTimestamp(i++, dt.getRecordStatusTime());

            preparedStmt.setString(i++, dt.getStatusCd());

            if (dt.getStatusTime() == null)
                preparedStmt.setNull(i++, Types.TIMESTAMP);
            else
                preparedStmt.setTimestamp(i++, dt.getStatusTime());

            if (dt.getToTime() == null)
                preparedStmt.setNull(i++, Types.TIMESTAMP);
            else
                preparedStmt.setTimestamp(i++, dt.getToTime());

            preparedStmt.setString(i++, dt.getUseCd());
            preparedStmt.setString(i++, dt.getUserAffiliationTxt());
            preparedStmt.setString(i++, dt.getValidTimeTxt());

            if(dt.getAsOfDate() == null)
              preparedStmt.setNull(i++, Types.TIMESTAMP );
            else
              preparedStmt.setTimestamp(i++,dt.getAsOfDate());

            resultCount = preparedStmt.executeUpdate();

            if (resultCount != 1)
            {
                logger.error(
                        "Error: none or more than one entity inserted at a time, resultCount = " +
                        resultCount);
            }
        }
        catch (SQLException se)
        {
            logger.fatal("Error: SQLException while inserting\n"+se.getMessage(), se);
            throw new NEDSSDAOSysException(se.getMessage());
        }
        finally
        {
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
    }
}