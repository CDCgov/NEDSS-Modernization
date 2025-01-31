/**
* Name:        PostalLocatorHistDAOImpl.java
* Description:    This is the implementation of NEDSSDAOInterface for the
*               PostalLocatorHist value object.
*               This class encapsulates all the JDBC calls required to
*               load and store values into the postal_locator_hist table
*
* Copyright:    Copyright (c) 2001
* Company:     Computer Sciences Corporation
* @author    Ning Peng
*/
package gov.cdc.nedss.locator.dao;

import gov.cdc.nedss.entity.sqlscript.NEDSSSqlQuery;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.locator.dt.PostalLocatorDT;
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


public class PostalLocatorHistDAOImpl
    extends BMPBase
{

    //For logging
    static final LogUtils logger = new LogUtils(PostalLocatorHistDAOImpl.class.getName());
    private int nextSeqNum = -1;

    //for the time being, using default change_user_id for testing
    private long defaultChangeUserId = 0;

    /*
        //for testing
        NedssUtils nu = new NedssUtils();
        Connection dbConnection = nu.getTestConnection();
    */

    /**
    * PostalLocatorDAOImpl is the default constructor requires no parameters
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSDAOSysException
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSSystemException
    */
    public PostalLocatorHistDAOImpl()
                             throws NEDSSDAOSysException, NEDSSSystemException
    {
    }

    /**
    * PostalLocatorDAOImpl is the a constructor requires one parameters
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSDAOSysException
    * @throws gov.cdc.nedss.dao.exceptions.NEDSSSystemException
    */
    public PostalLocatorHistDAOImpl(int seqNum)
                             throws NEDSSDAOSysException, NEDSSSystemException
    {
        this.nextSeqNum = seqNum;
    }

    /**
     * A data access method used to load the postal locator history object
     * @param entityUID a Long entity uid object
     * @param histSeq an Integer entity history sequence object
     * @return a Collection<Object> object
     * @throws NEDSSSystemException
     */
    public Collection<Object> load(Long entityUID, Integer histSeq)
                    throws NEDSSSystemException
    {
    	try{
	        Collection<Object> coll = selectloadPostalLocatorHist(entityUID.longValue(),
	                                                      histSeq.intValue());
	
	        return coll;
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    private Collection<Object> selectloadPostalLocatorHist(long entityUID, int histSeq)
                                            throws NEDSSSystemException
    {

        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        PostalLocatorDT dt = new PostalLocatorDT();
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
                    "for selectloadPostalLocatorHist ", nsex);
            throw new NEDSSSystemException(nsex.toString());
        }

        try
        {
            preparedStmt = dbConnection.prepareStatement(
                                   NEDSSSqlQuery.SELECT_POSTAL_LOCATOR_HIST);
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

                PostalLocatorDT reSetName = (PostalLocatorDT)anIterator.next();
                reSetName.setItNew(false);
                reSetName.setItDirty(false);
                reSetList.add(reSetName);
            }

            logger.debug("Return Postal locator history collection");

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
                    "a Postal locator history, Postal_locator_UID = " +
                    entityUID, sex);
            throw new NEDSSDAOSysException(sex.toString());
        }
        catch (Exception ex)
        {
            logger.fatal(
                    "Exception while loading " +
                    "a Postal locator history, Postal_locator_UID = " +
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
     * A data access method used to update the postal locator history object
     * @param coll a Collection<Object> of the history objects to be updated
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
     * A data access method used to update the postal locator history object
     * @param obj an Object history object to be updated
     * @throws NEDSSDAOSysException
     * @throws NEDSSSystemException
     */
    public void store(Object obj)
               throws NEDSSDAOSysException, NEDSSSystemException
    {
    	try{
	        PostalLocatorDT dt = (PostalLocatorDT)obj;
	
	        if (dt == null)
	            logger.info("No PostalLocatorDT object is stored.");
	        else if (dt.getPostalLocatorUid()!=null)
	            insertPostalLocatorHist(dt);
    	}catch(NEDSSDAOSysException ex){
  		  logger.fatal("NEDSSDAOSysException  = "+ex.getMessage(), ex);
  		  throw new NEDSSDAOSysException(ex.toString());
  	  	}catch(Exception ex){
  		  logger.fatal("Exception  = "+ex.getMessage(), ex);
  		  throw new NEDSSSystemException(ex.toString());
  	  	}
    }

    private void insertPostalLocatorHist(PostalLocatorDT dt)
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
                                   NEDSSSqlQuery.INSERT_POSTAL_LOCATOR_HIST);

            //setPreparedStatement(preparedStmt, dt);
            int i = 1;
            preparedStmt.setLong(i++, dt.getPostalLocatorUid().longValue());
            preparedStmt.setInt(i++, nextSeqNum);
            preparedStmt.setString(i++, dt.getAddReasonCd());
            preparedStmt.setTimestamp(i++, dt.getAddTime());

            if (dt.getAddUserId() == null)
                preparedStmt.setNull(i++, Types.INTEGER);
            else
                preparedStmt.setLong(i++, dt.getAddUserId().longValue());

            preparedStmt.setString(i++, dt.getCensusBlockCd());
            preparedStmt.setString(i++, dt.getCensusMinorCivilDivisionCd());
            preparedStmt.setString(i++, dt.getCensusTrackCd());
            preparedStmt.setString(i++, dt.getCityCd());
            preparedStmt.setString(i++, dt.getCityDescTxt());
            preparedStmt.setString(i++, dt.getCntryCd());
            preparedStmt.setString(i++, dt.getCntyCd());
            preparedStmt.setString(i++, dt.getLastChgReasonCd());
            preparedStmt.setTimestamp(i++, dt.getLastChgTime());

            if (dt.getLastChgUserId() == null)
                preparedStmt.setNull(i++, Types.INTEGER);
            else
                preparedStmt.setLong(i++, dt.getLastChgUserId().longValue());

            preparedStmt.setString(i++, dt.getMSACongressDistrictCd());
            preparedStmt.setString(i++, dt.getRegionDistrictCd());
            preparedStmt.setString(i++, dt.getStateCd());
            preparedStmt.setString(i++, dt.getStreetAddr1());
            preparedStmt.setString(i++, dt.getStreetAddr2());
            preparedStmt.setString(i++, dt.getUserAffiliationTxt());
            preparedStmt.setString(i++, dt.getZipCd());
            preparedStmt.setString(i++, dt.getGeocodeMatchInd());
            preparedStmt.setString(i++, dt.getWithinCityLimitsInd());
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