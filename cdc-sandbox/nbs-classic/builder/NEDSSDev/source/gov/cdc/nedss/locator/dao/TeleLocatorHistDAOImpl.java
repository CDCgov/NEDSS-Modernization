/**
* Name:        TeleLocatorHistDAOImpl.java
* Description:    This is the implementation of NEDSSDAOInterface for the
*               TeleLocatorHist value object.
*               This class encapsulates all the JDBC calls required to
*               load and store values into the Tele_locator_hist table
*
* Copyright:    Copyright (c) 2001
* Company:     Computer Sciences Corporation
* @author    Ning Peng
*/
package gov.cdc.nedss.locator.dao;

import gov.cdc.nedss.entity.sqlscript.NEDSSSqlQuery;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.locator.dt.TeleLocatorDT;
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


public class TeleLocatorHistDAOImpl
    extends BMPBase
{

    //For logging
    static final LogUtils logger = new LogUtils(TeleLocatorHistDAOImpl.class.getName());
    private int nextSeqNum = -1;

    //for the time being, using default change_user_id for testing
    private long defaultChangeUserId = 0;

    /*
        //for testing
        NedssUtils nu = new NedssUtils();
        Connection dbConnection = nu.getTestConnection();

    */

    /**
    * TeleLocatorDAOImpl is the default constructor requires no parameters
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSDAOSysException
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSSystemException
    */
    public TeleLocatorHistDAOImpl()
                           throws NEDSSDAOSysException, NEDSSSystemException
    {
    }

    /**
    * TeleLocatorDAOImpl is the a constructor requires one parameters
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSDAOSysException
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSSystemException
    */
    public TeleLocatorHistDAOImpl(int seqNum)
                           throws NEDSSDAOSysException, NEDSSSystemException
    {
        this.nextSeqNum = seqNum;
    }

    /**
     * A data access method used to retrieve the tele locator history objects
     * @param entityUID a Long entity uid object
     * @param histSeq an Integer history sequence object
     * @return a Collection<Object> tele locator history objects
     * @throws NEDSSSystemException
     */
    public Collection<Object> load(Long entityUID, Integer histSeq)
                    throws NEDSSSystemException
    {
    	try{

	        Collection<Object> coll = selectloadTeleLocatorHist(entityUID.longValue(),
	                                                    histSeq.intValue());
	        return coll;
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    private Collection<Object> selectloadTeleLocatorHist(long entityUID, int histSeq)
                                          throws NEDSSSystemException
    {

        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        TeleLocatorDT dt = new TeleLocatorDT();
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
                    "for selectloadTeleLocatorHist ", nsex);
            throw new NEDSSSystemException(nsex.toString());
        }

        try
        {
            preparedStmt = dbConnection.prepareStatement(
                                   NEDSSSqlQuery.SELECT_TELE_LOCATOR_HIST);
            preparedStmt.setLong(1, entityUID);
            preparedStmt.setLong(2, histSeq);
            resultSet = preparedStmt.executeQuery();
            resultSetMetaData = resultSet.getMetaData();

            ArrayList<Object>  locatorList = new ArrayList<Object> ();
            ArrayList<Object>  reSetList = new ArrayList<Object> ();
            locatorList = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet,
                                                                    resultSetMetaData,
                                                                    dt.getClass(),
                                                                    locatorList);

            for (Iterator<Object> anIterator = locatorList.iterator();
                 anIterator.hasNext();)
            {

                TeleLocatorDT reSetName = (TeleLocatorDT)anIterator.next();
                reSetName.setItNew(false);
                reSetName.setItDirty(false);
                reSetList.add(reSetName);
            }

            logger.debug("Return Tele locator history collection");

            return reSetList;
        }
        catch (ResultSetUtilsException resue)
        {
        	logger.fatal("Exception  = "+resue.getMessage(), resue);
            return null;
        }
        catch (SQLException sex)
        {
            logger.fatal(
                    "SQLException while loading " +
                    "a Tele locator history, Tele_locator_UID = " +
                    entityUID, sex);
            throw new NEDSSDAOSysException(sex.toString());
        }
        catch (Exception ex)
        {
            logger.fatal(
                    "Exception while loading " +
                    "a Tele locator history, Tele_locator_UID = " +
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
     * A data access method used to update the tele locator history objects
     * @param coll a Collection<Object> tele locator history objects to be updated
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
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSDAOSysException(ex.toString());
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    /**
     * A data access method used to update the tele locator history object
     * @param obj an Object tele locator history object to be updated
     * @throws NEDSSDAOSysException
     * @throws NEDSSSystemException
     */
    public void store(Object obj)
               throws NEDSSDAOSysException, NEDSSSystemException
    {

    	try{
	        TeleLocatorDT dt = (TeleLocatorDT)obj;
	
	        if (dt == null)
	            logger.info("No TeleLocatorDT object is stored.");
	        else if (dt.getTeleLocatorUid()!=null)
	            insertTeleLocatorHist(dt);
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    private void insertTeleLocatorHist(TeleLocatorDT dt)
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
                                   NEDSSSqlQuery.INSERT_TELE_LOCATOR_HIST);

            //setPreparedStatement(preparedStmt, dt);
            int i = 1;
            preparedStmt.setLong(i++, dt.getTeleLocatorUid().longValue());
            preparedStmt.setInt(i++, nextSeqNum);
            preparedStmt.setString(i++, dt.getAddReasonCd());
            preparedStmt.setTimestamp(i++, dt.getAddTime());

            if (dt.getAddUserId() == null)
                preparedStmt.setNull(i++, Types.INTEGER);
            else
                preparedStmt.setLong(i++, dt.getAddUserId().longValue());

            preparedStmt.setString(i++, dt.getCntryCd());
            preparedStmt.setString(i++, dt.getEmailAddress());
            preparedStmt.setString(i++, dt.getExtensionTxt());
            preparedStmt.setString(i++, dt.getLastChgReasonCd());
            preparedStmt.setTimestamp(i++, dt.getLastChgTime());

            if (dt.getLastChgUserId() == null)
                preparedStmt.setNull(i++, Types.INTEGER);
            else
                preparedStmt.setLong(i++, dt.getLastChgUserId().longValue());

            preparedStmt.setString(i++, dt.getPhoneNbrTxt());
            preparedStmt.setString(i++, dt.getUrlAddress());
            preparedStmt.setString(i++, dt.getUserAffiliationTxt());
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