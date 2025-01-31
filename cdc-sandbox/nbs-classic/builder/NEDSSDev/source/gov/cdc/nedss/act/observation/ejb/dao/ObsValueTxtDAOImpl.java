/**
* Name:		ObsValueTxtDAOImpl.java
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
import java.io.*;
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



public class ObsValueTxtDAOImpl extends BMPBase
{
    //private static final String OBSERVATION_UID = "OBSERVATION_UID";
    //private long observationUID = -1;
    //For logging
    static final LogUtils logger = new LogUtils(ObsValueTxtDAOImpl.class.getName());

    public ObsValueTxtDAOImpl()
    {
    }

    public long create(long observationUID, Collection<Object> coll) throws NEDSSSystemException
    {
    	try{
	        insertObsValueTxts(observationUID, coll);
	
	        return observationUID;
    	}catch(Exception ex){
    		logger.fatal("observationUID: "+observationUID+" Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    public void store(Collection<Object> coll) throws  NEDSSSystemException

    {
        updateObsValueTxts(coll);
    }

    public void remove(long observationUID) throws  NEDSSSystemException
    {
        removeObsValueTxt (observationUID);
    }

    public Collection<Object> load(long observationUID) throws NEDSSDAOSysException,
		NEDSSSystemException
    {
        Collection<Object> prColl = selectObsValueTxts(observationUID);
        return prColl;
    }

    public Long findByPrimaryKey(long observationUID) throws
		NEDSSSystemException
    {
        if (obsValueTxtExists(observationUID))
            return (new Long(observationUID));
        else
            logger.error("No OBS_VALUE_TXT found for this primary key :" + observationUID);
            return null;
    }


    protected boolean obsValueTxtExists (long observationUID) throws NEDSSSystemException
    {
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        boolean returnValue = false;

        try
        {
            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(WumSqlQuery.SELECT_OBS_VALUE_TXT_UID);
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
                            + " existing observation's uid in OBS_VALUE_TXT table-> " + observationUID , sex);
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


    private void insertObsValueTxts(long observationUID, Collection<Object> obsValueTxt)
                throws NEDSSSystemException
    {

        Iterator<Object> anIterator = null;

        try
        {
            /**
             * Inserts Observation Value Text
             */

             for (anIterator = obsValueTxt.iterator(); anIterator.hasNext(); )
             {
                ObsValueTxtDT obsValueDT = (ObsValueTxtDT)anIterator.next();
                if (obsValueDT != null)
                insertObsValueTxt(observationUID, obsValueDT);
                //throw new NEDSSPObservationDAOAppException("Empty Observation text collection");
                obsValueDT.setObservationUid(new Long(observationUID));
             }
         }
         catch(NEDSSSystemException noduex)
         {
            logger.fatal("observationUID: "+observationUID+" Update exception while inserting " +
                        "observation into OBS_VALUE_TXT: \n" , noduex);
            throw new NEDSSSystemException( noduex.toString() );
         }
         catch(Exception ex)
         {
            logger.fatal("observationUID: "+observationUID+" Exception while inserting " +
                        "observation into OBS_VALUE_TXT: \n", ex);
            throw new NEDSSSystemException( ex.toString() );
         }
logger.debug("Done inserting observation");
    }//end of inserting OBS_VALUE_TXT

    private void insertObsValueTxt(long observationUID, ObsValueTxtDT obsValueTxtDT)
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
                "while inserting a obsValueTxtDT", nsex);
            throw new NEDSSSystemException(nsex.toString());
        }

        try
        {
            preparedStmt = dbConnection.prepareStatement(WumSqlQuery.INSERT_OBS_VALUE_TXT);

            int i = 1;

            logger.debug("obsValueTxtDT = " + obsValueTxtDT);
            preparedStmt.setLong(i++, observationUID);
            if(obsValueTxtDT.getObsValueTxtSeq() == null)
                preparedStmt.setNull(i++, Types.INTEGER);
            else
            preparedStmt.setInt(i++, (obsValueTxtDT.getObsValueTxtSeq()).intValue());
            preparedStmt.setString(i++, obsValueTxtDT.getDataSubtypeCd());
            preparedStmt.setString(i++, obsValueTxtDT.getEncodingTypeCd());
            preparedStmt.setString(i++, obsValueTxtDT.getTxtTypeCd());
            //BLOB specific changes
            if(obsValueTxtDT.getValueImageTxt()!=null ){
            		InputStream fis = new ByteArrayInputStream(obsValueTxtDT.getValueImageTxt());
                    preparedStmt.setBinaryStream(i++,fis, obsValueTxtDT.getValueImageTxt().length);
            }else{
            	preparedStmt.setNull(i++, Types.BINARY);
            }
            preparedStmt.setString(i++, obsValueTxtDT.getValueTxt());

            resultCount = preparedStmt.executeUpdate();

            if ( resultCount != 1 )
                    logger.error
                            ("Error: none or more than one obsValueTxtDT inserted at a time, " +
                            "resultCount = " + resultCount);
            else
            {
                obsValueTxtDT.setItNew(false);
                obsValueTxtDT.setItDirty(false);
                obsValueTxtDT.setItDelete(false);
            }
        }
        catch(SQLException sex)
        {
            logger.fatal("observationUID: "+observationUID+" SQLException while inserting " +
                        "a observation value text into OBS_VALUE_TXT_TABLE: \n", sex);
            throw new NEDSSDAOSysException("Table Name : "+ DataTables.OBS_VALUE_TXT_TABLE + "  For observationUID: "+observationUID+"  "+sex.toString(), sex );
        }
        catch(Exception ex)
        {
            logger.fatal("observationUID: "+observationUID+" Error while inserting a OBS_VALUE_TXT.", ex);
            throw new NEDSSSystemException (ex.toString());
        }
        finally
        {
           // closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
    }//end of inserting a Obs_Value_Txt


    private void updateObsValueTxts(Collection<Object> obsValueTxts) throws NEDSSSystemException
    {
        ObsValueTxtDT obsValueTxt = null;
        Iterator<Object> anIterator = null;

        if(obsValueTxts != null  )
        {
            /**
             * Updates obsValueTxt
             */
            try
            {
                logger.debug("Updating obsValueTxt");
                for(anIterator = obsValueTxts.iterator(); anIterator.hasNext(); )
                {
                    obsValueTxt = (ObsValueTxtDT)anIterator.next();
                    if(obsValueTxt != null && obsValueTxt.getObservationUid() != null)
                    {
                     //in the order of precedence
                      if (obsValueTxt.isItDelete())
                      {
                          removeObsValueTxt (obsValueTxt.getObservationUid().longValue());
                      }
                      else if (obsValueTxt.isItNew())
                      {
                          insertObsValueTxt(obsValueTxt.getObservationUid().longValue(), obsValueTxt);
                      }
                      else if (obsValueTxt.isItDirty())
                      {
                           updateObsValueTxt(obsValueTxt.getObservationUid().longValue(), obsValueTxt);
                      }
                    }
                    else
                      logger.error("Error: Empty OBS_VALUE_TXT collection");
                  }
            }
            catch(Exception sex)
            {
                logger.fatal("Exception while updating " +
                    "obsValueTxt, \n", sex);
                throw new NEDSSSystemException( sex.toString() );
            }
        }
    }//end of updating OBS_VALUE_TXT

    private void updateObsValueTxt(long observationUid, ObsValueTxtDT obsValueTxt) throws NEDSSSystemException
    {
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        int resultCount = 0;

        /**
         * Updates OBS_VALUE_TXT
         */

        if(obsValueTxt != null  )
        {
            try
            {
                dbConnection = getConnection();
            }
            catch(NEDSSSystemException nsex)
            {
                logger.fatal("Error obtaining dbConnection " +
                    "for updating obsValueTxt", nsex);
                throw new NEDSSSystemException(nsex.toString());
            }

            try
            {
                preparedStmt = dbConnection.prepareStatement(WumSqlQuery.UPDATE_OBS_VALUE_TXT);

                int i = 1;

                logger.debug("OBS_VALUE_TXT = " + obsValueTxt.getValueTxt());

                preparedStmt.setString(i++, obsValueTxt.getDataSubtypeCd());
                preparedStmt.setString(i++, obsValueTxt.getEncodingTypeCd());
                preparedStmt.setString(i++, obsValueTxt.getTxtTypeCd());
                //preparedStmt.setString(i++, obsValueTxt.getValueImageTxt());
//              BLOB specific changes
                if(obsValueTxt.getValueImageTxt()!=null ){
                		InputStream fis = new ByteArrayInputStream(obsValueTxt.getValueImageTxt());
                        preparedStmt.setBinaryStream(i++,fis, obsValueTxt.getValueImageTxt().length);
                }else{
                	preparedStmt.setNull(i++, Types.BINARY);
                }
                preparedStmt.setString(i++, obsValueTxt.getValueTxt());
                preparedStmt.setLong(i++, observationUid);
                preparedStmt.setInt(i++, (obsValueTxt.getObsValueTxtSeq()).intValue());

                resultCount = preparedStmt.executeUpdate();
    logger.debug("Done updating OBS_VALUE_TXT");
                if (resultCount != 1)
                logger.error
                            ("Error: none or more than one OBS_VALUE_TXT updated at a time, " +
                              "resultCount = " + resultCount);
            }
            catch(SQLException sex)
            {
                logger.fatal("SQLException while updating " +
                    " OBS_VALUE_TXT, \n", sex);
                throw new NEDSSDAOSysException("Table Name : "+ DataTables.OBS_VALUE_TXT_TABLE + "  For observationUid: "+observationUid+"  "+sex.toString(), sex);
            }
            catch(Exception ex)
            {
                logger.fatal("Error while updating a OBS_VALUE_TXT", ex);
                throw new NEDSSSystemException(ex.toString());
            }
            finally
            {
                closeStatement(preparedStmt);
                releaseConnection(dbConnection);
            }
        }
    }//end of updating OBS_VALUE_TXT

    private Collection<Object> selectObsValueTxts (long observationUID) throws NEDSSSystemException
    {

        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        ResultSetUtils resultSetUtils = new ResultSetUtils();
        ObsValueTxtDT obsValueTxt = new ObsValueTxtDT();

        try
        {
            dbConnection = getConnection();
        }
        catch(NEDSSSystemException nsex)
        {
            logger.fatal("SQLException while obtaining database connection " +
                            "for selectObsValueTxts " , nsex);
            throw new NEDSSSystemException( nsex.getMessage());
        }

        /**
         * Selects obsValueTxt
         */
        try
        {
            preparedStmt = dbConnection.prepareStatement(WumSqlQuery.SELECT_OBS_VALUE_TXT);
            preparedStmt.setLong(1, observationUID);
            resultSet = preparedStmt.executeQuery();
            resultSetMetaData = resultSet.getMetaData();
            ArrayList<Object>  otList = new ArrayList<Object> ();
            ArrayList<Object>  reSetList = new ArrayList<Object> ();

            otList = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData, obsValueTxt.getClass(), otList);


            for(Iterator<Object> anIterator = otList.iterator(); anIterator.hasNext(); )
            {
                ObsValueTxtDT reSetObs = (ObsValueTxtDT)anIterator.next();
                reSetObs.setValueImageTxt(null);
                reSetObs.setItNew(false);
                reSetObs.setItDirty(false);

                reSetList.add(reSetObs);
            }
            logger.debug("return OBS_VALUE_TXT collection");
            return reSetList;
        }
        catch(SQLException se)
        {
            logger.fatal("SQLException while selecting " +
                            "OBS_VALUE_TXT collection; id = " + observationUID, se);
            throw new NEDSSDAOSysException( se.getMessage());
        }
        catch(ResultSetUtilsException reuex)
        {
            logger.fatal("Error in result set handling while selecting obsValueTxt.", reuex);
            throw new NEDSSDAOSysException(reuex.toString());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
    }//end of selecting obsValueTxt

    private void removeObsValueTxt (long observationUID) throws NEDSSSystemException
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
                            "for deleting obsValueTxt " , nsex);
            throw new NEDSSSystemException( nsex.getMessage());
        }

        /**
         * Deletes obsValueTxt
         */
        try
        {
            preparedStmt = dbConnection.prepareStatement(WumSqlQuery.DELETE_OBS_VALUES_TXT);
            preparedStmt.setLong(1, observationUID);
            resultCount = preparedStmt.executeUpdate();

            if (resultCount != 1)
            {
                logger.error
                ("Error: cannot delete obsValueTxt from OBS_VALUES_TXT_TABLE!! resultCount = " +
                 resultCount);
            }
        }
        catch(SQLException sex)
        {
            logger.fatal("SQLException while removing " +
                            "obsValueTxt; id = " + observationUID , sex);
            throw new NEDSSDAOSysException( sex.getMessage());
        }
        finally
        {
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
    }//end of removing obsValueTxt

/**   public static void main(String args[])
   {
      logger.debug("ObsValueDateDAOImpl - Doing the main thing");
    try
      {
        ObsValueTxtDAOImpl otd = new ObsValueTxtDAOImpl();
        Long uid = new Long(12);
        ObsValueTxtDT txtDT = new ObsValueTxtDT();
        txtDT.setObservationUid(uid);
        txtDT.setObsValueTxtSeq(new Integer(1));
        txtDT.setTxtTypeCd("");
        txtDT.setValueTxt("");
        txtDT.setItNew(false);
        txtDT.setItDirty(true);
        ArrayList<Object> al = new ArrayList<Object> ();
        al.add(txtDT);
        //otd.create(uid.longValue(), al);
        //Long l = otd.findByPrimaryKey(12);
        Collection<Object>  coll = otd.load(12);
        //otd.store(12, al);
        logger.debug("Executed Observation Txt: " + txtDT.getValueTxt());
      }
      catch(Exception e)
      {
        logger.debug("\n\nObsValueTxtDAOImpl ERROR : turkey no worky = \n" + e);

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
    try{
          dbConnection.close();
        }
    catch(SQLException e)
        {
        }
  }// end of finalize() method  **/


}//end of ObsValueTxtDAOImpl class
