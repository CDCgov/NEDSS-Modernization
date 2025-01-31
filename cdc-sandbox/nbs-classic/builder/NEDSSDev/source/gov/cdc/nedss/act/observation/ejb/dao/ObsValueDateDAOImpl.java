/**
* Name:		ObsValueDateDAOImpl.java
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
import java.sql.Timestamp;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
import java.sql.SQLException;
import java.math.BigDecimal;

import javax.sql.DataSource;
import javax.naming.InitialContext;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.ejb.FinderException;
import javax.ejb.DuplicateKeyException;
import javax.ejb.CreateException;
import javax.ejb.RemoveException;

import gov.cdc.nedss.act.observation.vo.*;
import gov.cdc.nedss.act.observation.dt.*;
import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.systemservice.exception.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.systemservice.uidgenerator.*;
import gov.cdc.nedss.act.sqlscript.*;




public class ObsValueDateDAOImpl extends BMPBase
{
    //private static final String OBSERVATION_UID = "OBSERVATION_UID";
    //private long observationUID = -1;

    //For logging
    static final LogUtils logger = new LogUtils(ObsValueDateDAOImpl.class.getName());



    public ObsValueDateDAOImpl() throws NEDSSSystemException
    {
     //NedssUtils nu = new NedssUtils();
     //super.dbConnection = nu.getTestConnection();
    }

    public long create(long observationUID, Collection<Object> coll) throws NEDSSSystemException
    {
    	try{
	        insertObsValueDates(observationUID, coll);
	
	        return observationUID;
    	}catch(Exception ex){
    		logger.fatal("observationUID: "+observationUID+" Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    public void store(Collection<Object> coll) throws NEDSSDAOSysException, NEDSSSystemException
    {
        updateObsValueDates(coll);
    }

    public void remove(long observationUID) throws  NEDSSSystemException
    {
        removeObsValueDate(observationUID);
    }

    public Collection<Object> load(long observationUID) throws
		NEDSSSystemException
    {
        Collection<Object> prColl = selectObsValueDates(observationUID);
        return prColl;
    }

    public Long findByPrimaryKey(long observationUID) throws
		NEDSSSystemException
    {
        if (obsValueDateExists(observationUID))
            return (new Long(observationUID));
        else
            logger.error("No OBS_VALUE_DATE found for this primary key :" + observationUID);
            return null;
    }


    protected boolean obsValueDateExists (long observationUID) throws
            NEDSSSystemException
    {
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        boolean returnValue = false;

        try
        {
            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(WumSqlQuery.SELECT_OBS_VALUE_DATE_UID);
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
                            + " existing observation's uid in OBS_VALUE_DATE table-> " + observationUID , sex);
            throw new NEDSSDAOSysException( sex.getMessage());
        }
        catch(NEDSSSystemException nsex)
        {
            logger.fatal("Exception while getting dbConnection for checking for an"
                            + " existing observation's uid -> " + observationUID , nsex);
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


    private void insertObsValueDates(long observationUID, Collection<Object> obsValueDate)
                throws NEDSSSystemException
    {

        Iterator<Object> anIterator = null;

        try
        {
            /**
             * Inserts Observation Value Date
             */

             for (anIterator = obsValueDate.iterator(); anIterator.hasNext(); )
             {
                ObsValueDateDT obsValueDT = (ObsValueDateDT)anIterator.next();
                if (obsValueDT != null)
                insertObsValueDate(observationUID, obsValueDT);
                //throw new NEDSSPObservationDAOAppException("Empty Observation Date collection");
                obsValueDT.setObservationUid(new Long(observationUID));
             }
         }
         catch(Exception ex)
         {
            logger.fatal("observationUID: "+observationUID+" Exception while inserting " +
                        "observation into OBS_VALUE_DATE: \n" , ex);
            throw new NEDSSSystemException( ex.toString() );
         }
logger.debug("Done inserting observation");
    }//end of inserting OBS_VALUE_DATE

    private void insertObsValueDate(long observationUID, ObsValueDateDT obsValueDateDT)
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
                "while inserting a obsValueDateDT", nsex);
            throw new NEDSSSystemException(nsex.toString());
        }

        try
        {
            preparedStmt = dbConnection.prepareStatement(WumSqlQuery.INSERT_OBS_VALUE_DATE);

            int i = 1;

    logger.debug("obsValueDateDT = " + obsValueDateDT);
            preparedStmt.setLong(i++, observationUID);
            if(obsValueDateDT.getObsValueDateSeq() == null)
            {
            logger.debug(" berfore set ");
                preparedStmt.setNull(i++, Types.INTEGER);}
            else
            preparedStmt.setInt(i++, (obsValueDateDT.getObsValueDateSeq()).intValue());
            preparedStmt.setString(i++, obsValueDateDT.getDurationAmt());
            preparedStmt.setString(i++, obsValueDateDT.getDurationUnitCd());
            if (obsValueDateDT.getFromTime() == null)
              preparedStmt.setNull(i++, Types.TIMESTAMP);
            else
              preparedStmt.setTimestamp(i++, obsValueDateDT.getFromTime());
            if (obsValueDateDT.getToTime() == null)
              preparedStmt.setNull(i++, Types.TIMESTAMP);
            else
              preparedStmt.setTimestamp(i++, obsValueDateDT.getToTime());

            resultCount = preparedStmt.executeUpdate();

            if ( resultCount != 1 )
                    logger.error
                            ("Error: none or more than one obsValueDateDT inserted at a time, " +
                            "resultCount = " + resultCount);
            else
            {
                obsValueDateDT.setItNew(false);
                obsValueDateDT.setItDirty(false);
                obsValueDateDT.setItDelete(false);
            }
        }
        catch(SQLException sex)
        {
            logger.fatal("observationUID: "+observationUID+" SQLException while inserting " +
                        "a observation date into OBS_VALUE_DATE: \n" , sex);
            throw new NEDSSDAOSysException("Table Name : "+ DataTables.OBS_VALUE_DATE_TABLE + "  For observationUID: "+observationUID+"  "+sex.toString(), sex );
        }
        catch(Exception ex)
        {
            logger.fatal("observationUID: "+observationUID+" Error while inserting a observation value date.", ex);
            throw new NEDSSSystemException (ex.toString());
        }
                finally
        {
           // closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
    }//end of inserting a Obs_Value_Date


    private void updateObsValueDates(Collection<Object> obsValueDates) throws  NEDSSSystemException
    {
        ObsValueDateDT obsValueDate = null;
        Iterator<Object> anIterator = null;

        if(obsValueDates != null)
        {
            /**
             * Updates obsValueDate
             */
            try
            {
logger.debug("Updating obsValueDate");
                for(anIterator = obsValueDates.iterator(); anIterator.hasNext(); )
                {
                    obsValueDate = (ObsValueDateDT)anIterator.next();
/*
                    if(obsValueDate == null && obsValueDate.getObservationUid() != null)
                    logger.warn("Error: Empty OBS_VALUE_DATE collection");
*/
                    logger.debug("ejenkin6.obsValueDate.getObservationUid()=" + obsValueDate.getObservationUid());
                    if(obsValueDate.isItDelete())
                    {
                      removeObsValueDate(obsValueDate.getObservationUid().longValue());
                    }
                    else if(obsValueDate.isItNew())
                    {
                      insertObsValueDate(obsValueDate.getObservationUid().longValue(), obsValueDate);
                    }
                    else if(obsValueDate.isItDirty())
                    {
                      updateObsValueDate(obsValueDate.getObservationUid().longValue(), obsValueDate);
                    }
                }
            }
            catch(Exception sex)
            {
                logger.fatal("Exception while updating " +
                    "obsValueDate, \n", sex);
                throw new NEDSSSystemException( sex.toString() );
            }

        }
    }//end of updating OBS_VALUE_DATE table

    private void updateObsValueDate(long observationUid, ObsValueDateDT obsValueDate) throws NEDSSSystemException
    {
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        int resultCount = 0;

        /**
         * Updates OBS_VALUE_DATE
         */

        if(obsValueDate != null)
        {
            try
            {
                dbConnection = getConnection();
            }
            catch(NEDSSSystemException nsex)
            {
                logger.fatal("Error obtaining dbConnection " +
                    "for updating obsValueDate", nsex);
                throw new NEDSSSystemException(nsex.toString());
            }

            try
            {
                preparedStmt = dbConnection.prepareStatement(WumSqlQuery.UPDATE_OBS_VALUE_DATE);

                int i = 1;

   logger.debug("OBS_VALUE_DATE = " + obsValueDate.getDurationAmt());

                preparedStmt.setString(i++, obsValueDate.getDurationAmt());
                preparedStmt.setString(i++, obsValueDate.getDurationUnitCd());
              if (obsValueDate.getFromTime() == null)
                preparedStmt.setNull(i++, Types.TIMESTAMP);
              else
                preparedStmt.setTimestamp(i++, obsValueDate.getFromTime());

              if (obsValueDate.getToTime() == null)
                preparedStmt.setNull(i++, Types.TIMESTAMP);
              else
                preparedStmt.setTimestamp(i++, obsValueDate.getToTime());
                preparedStmt.setLong(i++, observationUid);
                preparedStmt.setInt(i++, (obsValueDate.getObsValueDateSeq()).intValue());

                resultCount = preparedStmt.executeUpdate();
    logger.debug("Done updating OBS_VALUE_DATE");
                if (resultCount != 1)
                logger.error
                            ("Error: none or more than one OBS_VALUE_DATE updated at a time, " +
                              "resultCount = " + resultCount);
            }
            catch(SQLException sex)
            {
                logger.fatal("SQLException while updating " +
                    " OBS_VALUE_DATE, \n" , sex);
                throw new NEDSSDAOSysException("Table Name : "+ DataTables.OBS_VALUE_DATE_TABLE + "  For observationUid: "+observationUid+"  "+sex.toString(), sex );
            }
            catch(Exception ex)
            {
                logger.fatal("Error while updating a OBS_VALUE_DATE", ex);
                throw new NEDSSSystemException(ex.toString());
            }
            finally
            {
                closeStatement(preparedStmt);
                releaseConnection(dbConnection);
            }
        }
    }//end of updating OBS_VALUE_DATE

    private Collection<Object> selectObsValueDates (long observationUID) throws NEDSSSystemException
    {

        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        ResultSetUtils resultSetUtils = new ResultSetUtils();
        ObsValueDateDT obsValueDate = new ObsValueDateDT();

        try
        {
            dbConnection = getConnection();
        }
        catch(NEDSSSystemException nsex)
        {
            logger.fatal("SQLException while obtaining database connection " +
                            "for selectObsValueDates " , nsex);
            throw new NEDSSSystemException( nsex.getMessage());
        }

        /**
         * Selects obsValueDate
         */
        try
        {
            preparedStmt = dbConnection.prepareStatement(WumSqlQuery.SELECT_OBS_VALUE_DATE);
            preparedStmt.setLong(1, observationUID);
            resultSet = preparedStmt.executeQuery();
            resultSetMetaData = resultSet.getMetaData();
            ArrayList<Object>  otList = new ArrayList<Object> ();
            ArrayList<Object>  reSetList = new ArrayList<Object> ();

            otList = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData, obsValueDate.getClass(), otList);


            for(Iterator<Object> anIterator = otList.iterator(); anIterator.hasNext(); )
            {
                ObsValueDateDT reSetObs = (ObsValueDateDT)anIterator.next();
                reSetObs.setItNew(false);
                reSetObs.setItDirty(false);

                reSetList.add(reSetObs);
            }
            logger.debug("return OBS_VALUE_DATE collection");
            return reSetList;
        }
        catch(SQLException se)
        {
            throw new NEDSSDAOSysException("SQLException while selecting " +
                            "OBS_VALUE_DATE collection; id = " + observationUID + " :\n" + se.getMessage());
        }
        catch(ResultSetUtilsException reuex)
        {
            logger.fatal("Error in result set handling while selecting obsValueDate.", reuex);
            throw new NEDSSDAOSysException(reuex.toString());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
    }//end of selecting obsValueDate

    private void removeObsValueDate (long observationUID) throws NEDSSSystemException
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
                            "for deleting obsValueDate " , nsex);
            throw new NEDSSSystemException(nsex.getMessage());
        }

        /**
         * Deletes obsValueDate
         */
        try
        {
            preparedStmt = dbConnection.prepareStatement(WumSqlQuery.DELETE_OBS_VALUES_DATE);
            preparedStmt.setLong(1, observationUID);
            resultCount = preparedStmt.executeUpdate();

            if (resultCount != 1)
            {
                logger.error
                ("Error: cannot delete obsValueDate from OBS_VALUES_DATE_TABLE!! resultCount = " +
                 resultCount);
            }
        }
        catch(SQLException sex)
        {
            logger.fatal("SQLException while removing " +
                            "obsValueDate; id = " + observationUID, sex);
            throw new NEDSSDAOSysException( sex.getMessage());
        }
        finally
        {
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
    }//end of removing obsValueDate

/**    public static void main(String args[])
    {
      logger.debug("ObsValueDateDAOImpl - Doing the main thing");
      try
      {
        ObsValueDateDAOImpl orgDAOI = new ObsValueDateDAOImpl();
         logger.debug("ObsValueDateDAOImpl - Doing the main thing");

        Long uid = new Long(12);

        ObsValueDateDT dateDT = new ObsValueDateDT();

        dateDT.setObservationUid(uid);
        dateDT.setObsValueDateSeq(new Integer(1));
        dateDT.setDurationUnitCd("test");
        dateDT.setItNew(false);
        dateDT.setItDirty(true);
        ArrayList<Object> al = new ArrayList<Object> ();
        al.add(dateDT);
        //orgDAOI.create(uid.longValue(), al);
        Long l = orgDAOI.findByPrimaryKey(13L);
        //orgDAOI.store(12, al);
        //orgDAOI.remove(12);
        //Collection<Object>  col = orgDAOI.load(12);
        logger.debug("found record for : " + dateDT.getDurationUnitCd());
      }catch(Exception e){
        logger.debug("\n\nObsValueDateDAOImpl ERROR : turkey no worky = \n" + e);

      }
    }

 protected synchronized Connection getConnection()
 {
    Connection conn = null;
    try
    {
      Class.forName("com.sssw.jdbc.mss.odbc.AgOdbcDriver");
      dbConnection = DriverManager.getConnection("jdbc:sssw:odbc:nedss1", "sa", "sapasswd");

    } catch (ClassNotFoundException cnf) {
      logger.debug("Can not load Database Driver");
    } catch (SQLException se) {
      logger.debug(se);
    }
    return dbConnection;
}// end of getConnection

public void finalize()
{
  try
    {
        dbConnection.close();
     }
  catch(SQLException e)
     {
     }
}**/



}//end of ObsValueDateDAOImpl class
