/**
* Name:		ObsValueCodedDAOImpl.java
* Description:	This is the implementation of NEDSSDAOInterface for the
*               Observation value object in the Observation entity bean.
*               This class encapsulates all the JDBC calls made by the ObservationEJB
*               for a Observation object. Actual logic of
*               inserting/reading/updating/deleting the data in relational
*               database tables to mirror the state of ObservationEJB is
*               implemented here.
* Copyright:	Copyright (c) 2001
* Company: 	Computer Sciences Corporation
* @author	Shailender & NEDSS Development Team
* @version	1.0
*/

package gov.cdc.nedss.act.observation.ejb.dao;


import java.util.*;
import java.sql.*;
import java.math.BigDecimal;

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




import gov.cdc.nedss.act.observation.vo.*;
import gov.cdc.nedss.act.observation.dt.*;
import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.systemservice.exception.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.systemservice.uidgenerator.*;
import gov.cdc.nedss.act.sqlscript.*;




public class ObsValueCodedDAOImpl extends BMPBase
{
    //private static final String OBSERVATION_UID = "OBSERVATION_UID";
    //private long observationUID = -1;

    //For logging
    static final LogUtils logger = new LogUtils(ObsValueCodedDAOImpl.class.getName());

    public ObsValueCodedDAOImpl()
    {
    }

    public long create(long observationUID, Collection<Object> coll) throws NEDSSSystemException
    {
    	try{
	        insertObsValueCodeds(observationUID, coll);
	
	        return observationUID;
    	}catch(Exception ex){
    		logger.fatal("observationUID: "+observationUID+" Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    public void store(Collection<Object> coll) throws  NEDSSSystemException,
                NEDSSDAOSysException

    {
    	try{
    		updateObsValueCodeds(coll);
    	}catch(NEDSSDAOSysException ex){
    		logger.fatal("NEDSSDAOSysException  = "+ex.getMessage(), ex);
    		throw new NEDSSDAOSysException(ex.toString());
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    public void remove(long observationUID) throws  NEDSSSystemException
    {
    	try{
	        removeObsValueCodedMod(observationUID);
	        removeObsValueCoded(observationUID);
    	}catch(Exception ex){
    		logger.fatal("observationUID: "+observationUID+" Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    public Collection<Object> load(long observationUID) throws  NEDSSSystemException
    {
    	try{
	        Collection<Object> obsValueCodedColl = selectObsValueCodeds(observationUID);
	        return obsValueCodedColl;
    	}catch(Exception ex){
    		logger.fatal("observationUID: "+observationUID+" Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    public Long findByPrimaryKey(long observationUID) throws
		NEDSSSystemException
    {
    	try{
	        if (obsValueCodedExists(observationUID))
	            return (new Long(observationUID));
	        else
	            logger.error("No OBS_VALUE_CODED found for this primary key :" + observationUID);
	            return null;
    	}catch(Exception ex){
    		logger.fatal("observationUID: "+observationUID+" Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }


    protected boolean obsValueCodedExists(long observationUID) throws
            NEDSSSystemException
    {
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        boolean returnValue = false;

        try
        {
            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(WumSqlQuery.SELECT_OBS_VALUE_CODED_UID);
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
                            + " existing observation's uid in OBS_VALUE_CODED table-> " + observationUID , sex);
            throw new NEDSSDAOSysException( sex.getMessage());
        }
        catch(NEDSSSystemException nsex)
        {
            logger.fatal("Exception while getting dbConnection for checking for an"
                            + " existing observation's uid -> " + observationUID, nsex);
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


    private void insertObsValueCodeds(long observationUID, Collection<Object> obsValueCoded)
                throws NEDSSSystemException
    {

        Iterator<Object> anIterator = null;

        try
        {
            /**
             * Inserts Observation Value Coded
             */

             for (anIterator = obsValueCoded.iterator(); anIterator.hasNext(); )
             {
                ObsValueCodedDT obsValueDT = (ObsValueCodedDT)anIterator.next();
                if (obsValueDT != null)
                insertObsValueCoded(observationUID, obsValueDT);
                //throw new NEDSSPObservationDAOAppException("Empty Observation text collection");
                obsValueDT.setObservationUid(new Long(observationUID));
             }
         }
         catch(Exception ex)
         {
            logger.fatal("observationUID: "+observationUID+" Exception while inserting " +
                        "observation into OBS_VALUE_CODED: \n", ex);
            throw new NEDSSSystemException( ex.toString() );
         }

        logger.debug("Done inserting observation");
    }//end of inserting OBS_VALUE_CODEDs

    private void insertObsValueCoded(long observationUID, ObsValueCodedDT obsValueCodedDT)
            throws NEDSSSystemException
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
            logger.fatal("Error obtaining db connection " +
                "while inserting a obsValueCodedDT", nsex);
            throw new NEDSSSystemException(nsex.toString());
        }

        try
        {
            preparedStmt = dbConnection.prepareStatement(WumSqlQuery.INSERT_OBS_VALUE_CODED);

            int i = 1;

            logger.debug("obsValueCodedDT = " + obsValueCodedDT);
            preparedStmt.setLong(i++, observationUID);
            preparedStmt.setString(i++, obsValueCodedDT.getCode());
            preparedStmt.setString(i++, obsValueCodedDT.getCodeSystemCd());
            preparedStmt.setString(i++, obsValueCodedDT.getCodeSystemDescTxt());
            preparedStmt.setString(i++, obsValueCodedDT.getCodeVersion());
            preparedStmt.setString(i++, obsValueCodedDT.getDisplayName());
            preparedStmt.setString(i++, obsValueCodedDT.getOriginalTxt());

            //changes for PIT
            preparedStmt.setString(i++, obsValueCodedDT.getAltCd());
            preparedStmt.setString(i++,obsValueCodedDT.getAltCdDescTxt());
            preparedStmt.setString(i++,obsValueCodedDT.getAltCdSystemCd());
            preparedStmt.setString(i++,obsValueCodedDT.getAltCdSystemDescTxt());
            preparedStmt.setString(i++,obsValueCodedDT.getCodeDerivedInd());


            resultCount = preparedStmt.executeUpdate();

            obsValueCodedDT.setItNew(false);
            obsValueCodedDT.setItDirty(false);
            obsValueCodedDT.setItDelete(false);

            if ( resultCount != 1 )
            {
                    logger.error
                            ("Error: none or more than one obsValueCodedDT inserted at a time, " +
                            "resultCount = " + resultCount);
             }
             else
             {
                 if(obsValueCodedDT.getTheObsValueCodedModDTCollection() != null)
                 {
                    insertObsValueCodedMods(observationUID, obsValueCodedDT.getCode().trim(), obsValueCodedDT.getTheObsValueCodedModDTCollection());
                 }
             }
        }
        catch(SQLException sex)
        {
            logger.fatal("observationUID: "+observationUID+" SQLException while inserting " +
                        "a Observation Code into OBS_VALUE_CODED: \n", sex);
            throw new NEDSSDAOSysException("Table Name : "+ DataTables.OBS_VALUE_CODED_TABLE + "  For observationUID: "+observationUID+"  "+sex.toString(), sex );
        }
        catch(Exception ex)
        {
            logger.fatal("observationUID: "+observationUID+" Error while inserting a OBS_VALUE_CODED", ex);
            throw new NEDSSSystemException (ex.toString());
        }
        finally
        {
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
    }//end of inserting a Obs_Value_Coded


    private void updateObsValueCodeds(Collection<Object> obsValueCodeds) throws NEDSSSystemException
    {
        ObsValueCodedDT obsValueCoded = null;
        Iterator<Object> anIterator = null;

        if(obsValueCodeds != null)
        {
            /**
             * Updates obsValueCoded
             */
            try
            {
            	logger.debug("Updating obsValueCoded");
                for(anIterator = obsValueCodeds.iterator(); anIterator.hasNext(); )
                {
                    obsValueCoded = (ObsValueCodedDT)anIterator.next();
                    logger.debug("UID is: " + obsValueCoded.getObservationUid());
                    if(obsValueCoded != null && obsValueCoded.getObservationUid() != null)
                    {
					  long obsUid = obsValueCoded.getObservationUid().longValue();
                      if(obsValueCoded.isItDelete())
                      {
                        remove (obsUid);
                      }
                      else if(obsValueCoded.isItNew())
                      {
                        insertObsValueCoded(obsUid, obsValueCoded);
                      }
                      else if(obsValueCoded.isItDirty())
                      {
                        updateObsValueCoded(obsUid, obsValueCoded);
                      }
                    }
                    else
                      logger.warn("Error: Empty OBS_VALUE_CODED collection");
                }
            }
            catch(Exception sex)
            {
                logger.fatal("Exception while updating " +
                    "obsValueCoded, \n" , sex);
                throw new NEDSSSystemException( sex.toString() );
            }
        }
    }//end of updating OBS_VALUE_CODED table

    private void updateObsValueCoded(long observationUid, ObsValueCodedDT obsValueCoded) throws NEDSSSystemException
    {
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        int resultCount = 0;

        /**
         * Updates OBS_VALUE_CODED
         */

        if(obsValueCoded != null)
        {
            try
            {
                dbConnection = getConnection();
            }
            catch(NEDSSSystemException nsex)
            {
                logger.fatal("Error obtaining dbConnection " +
                    "for updating obsValueCoded", nsex);
                throw new NEDSSSystemException(nsex.toString());
            }

            try
            {
                preparedStmt = dbConnection.prepareStatement(WumSqlQuery.UPDATE_OBS_VALUE_CODED);

                int i = 1;

                logger.debug("OBS_VALUE_CODED = " + obsValueCoded.getCodeSystemCd());

                preparedStmt.setString(i++, obsValueCoded.getCode());
                preparedStmt.setString(i++, obsValueCoded.getCodeSystemCd());
                preparedStmt.setString(i++, obsValueCoded.getCodeSystemDescTxt());
                preparedStmt.setString(i++, obsValueCoded.getCodeVersion());
                preparedStmt.setString(i++, obsValueCoded.getDisplayName());
                preparedStmt.setString(i++, obsValueCoded.getOriginalTxt());

                //changes for PIT
                preparedStmt.setString(i++, obsValueCoded.getAltCd());
                preparedStmt.setString(i++,obsValueCoded.getAltCdDescTxt());
                preparedStmt.setString(i++,obsValueCoded.getAltCdSystemCd());
                preparedStmt.setString(i++,obsValueCoded.getAltCdSystemDescTxt());
                preparedStmt.setString(i++,obsValueCoded.getCodeDerivedInd());


                preparedStmt.setLong(i++, observationUid);




                resultCount = preparedStmt.executeUpdate();
                logger.debug("Done updating OBS_VALUE_CODED");
                if (resultCount != 1)
                {
                logger.error
                            ("Error: none or more than one OBS_VALUE_CODED updated at a time, " +
                              "resultCount = " + resultCount);
                  }
                  else
                  {
                     if(obsValueCoded.getTheObsValueCodedModDTCollection() != null)
                     {
                        updateObsValueCodedMods(observationUid, obsValueCoded.getCode(), obsValueCoded.getTheObsValueCodedModDTCollection());
                     }
                  }
            }
            catch(SQLException sex)
            {
                logger.fatal("SQLException while updating " +
                    " OBS_VALUE_CODED, \n", sex);
                throw new NEDSSDAOSysException("Table Name : "+ DataTables.OBS_VALUE_CODED_TABLE +"  "+sex.toString(), sex);
            }
            catch(Exception ex)
            {
                logger.fatal("Error while updating a OBS_VALUE_CODED", ex);
                throw new NEDSSSystemException(ex.toString());
            }
            finally
            {
                closeStatement(preparedStmt);
                releaseConnection(dbConnection);
            }
        }
    }//end of updating OBS_VALUE_CODED

    private Collection<Object> selectObsValueCodeds (long observationUID) throws NEDSSSystemException
    {

        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        ResultSetUtils resultSetUtils = new ResultSetUtils();
        ObsValueCodedDT obsValueCoded = new ObsValueCodedDT();

        try
        {
            dbConnection = getConnection();
        }
        catch(NEDSSSystemException nsex)
        {
            logger.fatal("SQLException while obtaining database connection " +
                            "for selectObsValueCodeds " , nsex);
            throw new NEDSSSystemException( nsex.getMessage());
        }

        /**
         * Selects obsValueCoded
         */
        try
        {
            preparedStmt = dbConnection.prepareStatement(WumSqlQuery.SELECT_OBS_VALUE_CODED);
            preparedStmt.setLong(1, observationUID);
            resultSet = preparedStmt.executeQuery();
            resultSetMetaData = resultSet.getMetaData();
            ArrayList<Object>  otList = new ArrayList<Object> ();
            ArrayList<Object>  reSetList = new ArrayList<Object> ();

            otList = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData, obsValueCoded.getClass(), otList);

            for(Iterator<Object> anIterator = otList.iterator(); anIterator.hasNext(); )
            {
                ObsValueCodedDT reSetObs = (ObsValueCodedDT)anIterator.next();
                ArrayList<Object>  al = (ArrayList<Object> )selectObsValueCodedMods(observationUID, reSetObs.getCode());
                if( al != null)
                {
                    reSetObs.setTheObsValueCodedModDTCollection(al);
                }
                reSetObs.setItNew(false);
                reSetObs.setItDirty(false);
                reSetList.add(reSetObs);
            }
            logger.debug("return OBS_VALUE_CODED collection");
            return reSetList;
        }
        catch(SQLException se)
        {
            throw new NEDSSDAOSysException("SQLException while selecting " +
                            "OBS_VALUE_CODED collection; id = " + observationUID + " :\n" + se.getMessage());
        }
        catch(ResultSetUtilsException reuex)
        {
            logger.fatal("Error in result set handling while selecting obsValueCoded.", reuex);
            throw new NEDSSDAOSysException(reuex.toString());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
    }//end of selecting obsValueCoded


    private Collection<Object> selectObsValueCodedMods(long observationUID, String code) throws NEDSSSystemException
    {

        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        ResultSetUtils resultSetUtils = new ResultSetUtils();
        ObsValueCodedModDT obsValueCodedMod = new ObsValueCodedModDT();

        try
        {
            dbConnection = getConnection();
        }
        catch(NEDSSSystemException nsex)
        {
            logger.fatal("SQLException while obtaining database connection " +
                            "for selectObsValueCodedMods " , nsex);
            throw new NEDSSSystemException( nsex.getMessage());
        }

        /**
         * Selects obsValueCodedMod
         */
        try
        {
            preparedStmt = dbConnection.prepareStatement(WumSqlQuery.SELECT_OBS_VALUE_CODED_MOD);
            preparedStmt.setLong(1, observationUID);
            preparedStmt.setString(2, code);
            resultSet = preparedStmt.executeQuery();
            resultSetMetaData = resultSet.getMetaData();
            ArrayList<Object>  otList = new ArrayList<Object> ();
            ArrayList<Object>  reSetList = new ArrayList<Object> ();

            otList = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData, obsValueCodedMod.getClass(), otList);

            for(Iterator<Object> anIterator = otList.iterator(); anIterator.hasNext(); )
            {
                ObsValueCodedModDT reSetObs = (ObsValueCodedModDT)anIterator.next();
                reSetObs.setItNew(false);
                reSetObs.setItDirty(false);
                reSetList.add(reSetObs);
            }
            logger.debug("return OBS_VALUE_CODED_MOD collection");
            return reSetList;
        }
        catch(SQLException se)
        {
            throw new NEDSSDAOSysException("SQLException while selecting " +
                            "OBS_VALUE_CODED_MOD collection; id = " + observationUID + " :\n" + se.getMessage());
        }
        catch(ResultSetUtilsException reuex)
        {
            logger.fatal("Error in result set handling while selecting obsValueCodedMod.", reuex);
            throw new NEDSSDAOSysException(reuex.toString());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
    }//end of selecting obsValueCodedMod

    private void removeObsValueCoded (long observationUID) throws NEDSSSystemException
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
                            "for deleting obsValueCoded " , nsex);
            throw new NEDSSSystemException(nsex.getMessage());
        }

        /**
         * Deletes obsValueCoded
         */
        try
        {
            preparedStmt = dbConnection.prepareStatement(WumSqlQuery.DELETE_OBS_VALUES_CODED);
            preparedStmt.setLong(1, observationUID);
            resultCount = preparedStmt.executeUpdate();

            if (resultCount != 1)
            {
                logger.error
                ("Error: cannot delete obsValueCoded from OBS_VALUES_CODED_TABLE!! resultCount = " +
                 resultCount);
            }
        }
        catch(SQLException sex)
        {
            logger.fatal("SQLException while removing " +
                            "observationUID = " + observationUID, sex);
            throw new NEDSSDAOSysException( sex.getMessage());
        }
        finally
        {
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
    }//end of removing obsValueCoded

    private void removeObsValueCodedMod (long observationUID) throws NEDSSSystemException
    {
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        int resultCount = 0;

        try
        {
            dbConnection =getConnection();
        }
        catch(NEDSSSystemException nsex)
        {
            logger.fatal("SQLException while obtaining database connection " +
                            "for deleting obsValueCoded " , nsex);
            throw new NEDSSSystemException(nsex.getMessage());
        }

        /**
         * Deletes obsValueCoded
         */
        try
        {
            preparedStmt = dbConnection.prepareStatement(WumSqlQuery.DELETE_OBS_VALUES_CODED_MOD);
            preparedStmt.setLong(1, observationUID);
            resultCount = preparedStmt.executeUpdate();

            if (resultCount != 1)
            {
                logger.error
                ("Error: cannot delete obsValueCoded from OBS_VALUES_CODED_MOD_TABLE!! resultCount = " +
                 resultCount);
            }
        }
        catch(SQLException sex)
        {
            logger.fatal("SQLException while removing " +
                            "observationUID = " + observationUID, sex);
            throw new NEDSSDAOSysException( sex.getMessage());
        }
        finally
        {
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
    }//end of removing obsValueCoded

    private void insertObsValueCodedMods(long observationUID, String code, Collection<Object> collMod) throws NEDSSSystemException
    {
        ObsValueCodedModDT obsValueCodedModDT = null;
        Iterator<Object> anIterator = null;

        if(collMod != null)
        {
            /**
             * Inserts obsValueCodedMod
             */
            try
            {
            	logger.debug("Inserting obsValueCodedMod");

                for(anIterator = collMod.iterator(); anIterator.hasNext(); )
                {
                    obsValueCodedModDT = (ObsValueCodedModDT)anIterator.next();
                    if(obsValueCodedModDT != null)
                    insertObsValueCodedMod(observationUID, code, obsValueCodedModDT);
                    obsValueCodedModDT.setObservationUid(new Long(observationUID));
                    obsValueCodedModDT.setCode(code);
               }
            }
            catch(Exception sex)
            {
                logger.fatal("Exception while updating " +
                    "obsValueCodedMod, \n" , sex);
                throw new NEDSSSystemException( sex.toString());
            }

        }

    }

    private void insertObsValueCodedMod(long observationUID, String code, ObsValueCodedModDT obsValueCodedModDT)
            throws NEDSSSystemException
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
            logger.fatal("Error obtaining db connection " +
                "while inserting a obsValueCodedModDT", nsex);
            throw new NEDSSSystemException(nsex.toString());
        }

        try
        {
            preparedStmt = dbConnection.prepareStatement(WumSqlQuery.INSERT_OBS_VALUE_CODED_MOD);

            int i = 1;

            logger.debug("obsValueCodedModDT = " + obsValueCodedModDT.getCode() + "/" + obsValueCodedModDT.getCodeModCd());
            preparedStmt.setLong(i++, observationUID);
            preparedStmt.setString(i++, code);
            if(obsValueCodedModDT.getCodeModCd() == null)
                preparedStmt.setString(i++, "Modified");
            else
                preparedStmt.setString(i++, obsValueCodedModDT.getCodeModCd());
            preparedStmt.setString(i++, obsValueCodedModDT.getCodeSystemCd());
            preparedStmt.setString(i++, obsValueCodedModDT.getCodeSystemDescTxt());
            preparedStmt.setString(i++, obsValueCodedModDT.getCodeVersion());
            preparedStmt.setString(i++, obsValueCodedModDT.getDisplayName());
            preparedStmt.setString(i++, obsValueCodedModDT.getOriginalTxt());

            resultCount = preparedStmt.executeUpdate();

            if ( resultCount != 1 )
                    logger.error
                            ("Error: none or more than one obsValueCodedModDT inserted at a time, " +
                            "resultCount = " + resultCount);
            else
            {
                obsValueCodedModDT.setItNew(false);
                obsValueCodedModDT.setItDirty(false);
                obsValueCodedModDT.setItDelete(false);
            }
        }
        catch(SQLException sex)
        {
            logger.fatal("SQLException while inserting " +
                        "a observation date mod into OBS_VALUE_CODED_MOD: \n" , sex);
            throw new NEDSSDAOSysException("Table Name : "+ DataTables.OBS_VALUE_CODED_MOD_TABLE + "  For observationUID: "+observationUID+"  "+sex.toString(), sex);
        }
        catch(Exception ex)
        {
            logger.fatal("Error while inserting a observation value date.", ex);
            throw new NEDSSSystemException (ex.toString());
        }
       finally
        {
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
    }//end of inserting a Obs_Value_CodedMod

    private void updateObsValueCodedMods(long observationUid, String code, Collection<Object> obsValueCodedMods) throws NEDSSSystemException
    {
        ObsValueCodedModDT obsValueCodedMod = null;
        Iterator<Object> anIterator = null;

        if(obsValueCodedMods != null)
        {
            /**
             * Updates obsValueCoded
             */
            try
            {
            	logger.debug("Updating obsValueCodedMod");
                for(anIterator = obsValueCodedMods.iterator(); anIterator.hasNext(); )
                {
                    obsValueCodedMod = (ObsValueCodedModDT)anIterator.next();
                    if(obsValueCodedMod == null)
                    logger.error("Error: Empty OBS_VALUE_CODED_Mod collection");

                    if(obsValueCodedMod.isItNew())
                    insertObsValueCodedMod(observationUid, code, obsValueCodedMod);

                    if(obsValueCodedMod.isItDirty())
                    updateObsValueCodedMod(observationUid, code, obsValueCodedMod);
                }
            }
            catch(Exception sex)
            {
                logger.fatal("observationUid: "+observationUid+", code: "+code+" Exception while updating " +
                    "obsValueCodedMod, \n", sex);
                throw new NEDSSSystemException( sex.toString() );
            }
        }
    }//end of updating OBS_VALUE_CODED table

    private void updateObsValueCodedMod(long observationUid, String code, ObsValueCodedModDT obsValueCodedMod) throws NEDSSSystemException
    {
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        int resultCount = 0;

        // Updates OBS_VALUE_CODED_MOD


        if(obsValueCodedMod != null)
        {
            try
            {
                dbConnection = getConnection();
            }
            catch(NEDSSSystemException nsex)
            {
                logger.fatal("Error obtaining dbConnection " +
                    "for updating obsValueCodedMod", nsex);
                throw new NEDSSSystemException(nsex.toString());
            }


            try
            {
                preparedStmt = dbConnection.prepareStatement(WumSqlQuery.UPDATE_OBS_VALUE_CODED_MOD);

                int i = 1;

                logger.debug("OBS_VALUE_CODED_MOD = " + obsValueCodedMod.getCodeModCd());

                preparedStmt.setString(i++, obsValueCodedMod.getCodeSystemCd());
                preparedStmt.setString(i++, obsValueCodedMod.getCodeSystemDescTxt());
                preparedStmt.setString(i++, obsValueCodedMod.getCodeVersion());
                preparedStmt.setString(i++, obsValueCodedMod.getDisplayName());
                preparedStmt.setString(i++, obsValueCodedMod.getOriginalTxt());
                preparedStmt.setLong(i++, observationUid);
                preparedStmt.setString(i++, code);
                preparedStmt.setString(i++, obsValueCodedMod.getCodeModCd());
                resultCount = preparedStmt.executeUpdate();
                logger.debug("Done updating OBS_VALUE_CODED_MOD");
                if (resultCount != 1)
                logger.error
                            ("Error: none or more than one OBS_VALUE_CODED_MOD updated at a time, " +
                              "resultCount = " + resultCount);
            }
            catch(SQLException sex)
            {
                logger.fatal("observationUid: "+observationUid+", code: "+code+" SQLException while updating " +
                    " OBS_VALUE_CODED_MOD, \n", sex);
                throw new NEDSSDAOSysException("Table Name : "+ DataTables.OBS_VALUE_CODED_MOD_TABLE + "  For observationUid: "+observationUid+"  "+sex.toString(), sex);
            }
            catch(Exception ex)
            {
                logger.fatal("observationUid: "+observationUid+", code: "+code+" Error while updating a OBS_VALUE_CODED_MOD", ex);
                throw new NEDSSSystemException(ex.toString());
            }
            finally
            {
                closeStatement(preparedStmt);
                releaseConnection(dbConnection);
            }
        }
    }//end of updating OBS_VALUE_CODED_MOD

/**   public static void main(String args[])
   {
      logger.debug("ObsValueCodedDAOImpl - Doing the main thing");
    try
      {
        ObsValueCodedDAOImpl otd = new ObsValueCodedDAOImpl();
        Long uid = new Long(12);
        ObsValueCodedDT txtDT = new ObsValueCodedDT();
        ObsValueCodedModDT modDT = new ObsValueCodedModDT();
        modDT.setCodeModCd("MOD");
        modDT.setCodeSystemCd("MODSYS");
        modDT.setItNew(true);
        modDT.setItDirty(false);
        ArrayList<Object> modAl = new ArrayList<Object> ();
        modAl.add(modDT);


        txtDT.setObservationUid(uid);
        txtDT.setCode("A");
        txtDT.setCodeSystemCd("xxxxxxxxx");
        txtDT.setCodeVersion("abc");
        txtDT.setItNew(true);
        txtDT.setItDirty(false);
        txtDT.setTheObsValueCodedModDTCollection(modAl);
        ArrayList<Object> al = new ArrayList<Object> ();
        al.add(txtDT);
        otd.create(uid.longValue(), al);
        //Long l = otd.findByPrimaryKey(12);
        //Collection<Object>  coll = otd.load(12);
        //otd.store(12, al);
        //otd.remove(12);
        logger.debug("Executed Observation Coded: " + otd);
      }
      catch(Exception e)
      {
        logger.debug("\n\nObsValueCodedDAOImpl ERROR : turkey no worky = \n" + e);

      }
    }

 protected synchronized Connection getConnection()
 {

    try{
      Class.forName("com.sssw.jdbc.mss.odbc.AgOdbcDriver");
      dbConnection = DriverManager.getConnection("jdbc:sssw:odbc:nedss1", "sa", "sapasswd");

    } catch (ClassNotFoundException cnf) {
      logger.debug("Can not load Database Driver");
    } catch (SQLException se) {
      logger.debug(se);
    }
    return dbConnection;
  }// end of getConnection() method

  public void finalize()
  {
    try
    {
        dbConnection.close();
    }
    catch(SQLException e)
    {
    }
  }// end of finalize() method **/



}//end of ObsValueCodedDAOImpl class
