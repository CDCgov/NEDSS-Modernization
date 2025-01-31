// *** Generated Source File ***
// Portions Copyright (c) 1996-2001, SilverStream Software, Inc., All Rights Reserved

/**
 * Name:		 ReportDAOImpl.java
 * Description:	 This is the implementation of NEDSSDAOInterface for the
 *               report value object in the reportEntity bean.
 *               This class encapsulates all the JDBC calls made by the reportEJB
 *               for a report object. Actual logic of
 *               inserting/reading/updating/deleting the data in relational
 *               database tables to mirror the state of reportEJB is
 *               implemented here.
 * Copyright:	 Copyright (c) 2001
 * Company: 	 Computer Sciences Corporation
 * @author	     Pradeep Sharma
 * @version	1.0
 */
package gov.cdc.nedss.report.ejb.reportejb.dao;


import gov.cdc.nedss.entity.sqlscript.NEDSSSqlQuery;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.report.dt.ReportDT;
import gov.cdc.nedss.report.util.ReportConstants;
import gov.cdc.nedss.systemservice.uidgenerator.UidClassCodes;
import gov.cdc.nedss.systemservice.uidgenerator.UidGeneratorHelper;
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
import java.util.Iterator;



public class ReportDAOImpl extends BMPBase
{
	static final LogUtils logger = new LogUtils((ReportDAOImpl.class).getName());
	private static final String ENTITY_UID = "ENTITY_UID";

	private long reportUID = -1;

	public ReportDAOImpl()
		throws NEDSSDAOSysException, NEDSSSystemException
	{

	}

	public Object createReport(Object obj ) throws NEDSSDAOSysException, NEDSSSystemException
	{
		try{
			ReportDT reportDT = (ReportDT) obj;
			ReportDT rDT = insertItem(reportDT);
			logger.debug("ReportDAOImpl:CreateReport and reportUID is " + reportDT.getReportUid());
			reportDT.setItNew(false);
			reportDT.setItDirty(false);
			reportDT.setItDelete(false);
			return rDT;
		}catch(NEDSSDAOSysException ex){
			logger.fatal("NEDSSDAOSysException  = "+ex.getMessage(), ex);
			throw new NEDSSDAOSysException(ex.toString());
		}catch(Exception ex){
			logger.fatal("Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	public Object storeReport(Object obj)
		throws NEDSSDAOSysException, NEDSSSystemException
	{
		try{
			ReportDT reportDT = null;
			logger.debug("ReportDAOImpl:store and obj :" + obj);
			reportDT = updateItem( (ReportDT) obj);
			return reportDT;
		}catch(NEDSSDAOSysException ex){
			logger.fatal("NEDSSDAOSysException  = "+ex.getMessage(), ex);
			throw new NEDSSDAOSysException(ex.toString());
		}catch(Exception ex){
			logger.fatal("Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	public void remove(long reportUID)
		throws NEDSSDAOSysException, NEDSSSystemException
	{
		try{
			removeItem(reportUID);
		}catch(NEDSSDAOSysException ex){
			logger.fatal("NEDSSDAOSysException  = "+ex.getMessage(), ex);
			throw new NEDSSDAOSysException(ex.toString());
		}catch(Exception ex){
			logger.fatal("reportUID: "+reportUID+" Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	public Object loadObject(long reportUID) throws NEDSSDAOSysException,
		NEDSSSystemException
	{
		try{
			ReportDT reportDT = selectItem(reportUID);
			reportDT.setItNew(false);
			reportDT.setItDirty(false);
			reportDT.setItDelete(false);
			return reportDT;
		}catch(NEDSSDAOSysException ex){
			logger.fatal("reportUID: "+reportUID+" NEDSSDAOSysException  = "+ex.getMessage(), ex);
			throw new NEDSSDAOSysException(ex.toString());
		}catch(Exception ex){
			logger.fatal("reportUID: "+reportUID+" Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	public Long findByPrimaryKey(long reportUID)
		throws NEDSSDAOSysException, NEDSSSystemException
	{
		try{
			if (itemExists(reportUID))
			{
				logger.debug("the report uid found is " + reportUID);
				return (new Long(reportUID));
			}
			else
				throw new NEDSSDAOSysException("No report found for this primary key :" + reportUID);
		}catch(NEDSSDAOSysException ex){
			logger.fatal("reportUID: "+reportUID+" NEDSSDAOSysException  = "+ex.getMessage(), ex);
			throw new NEDSSDAOSysException(ex.toString());
		}catch(Exception ex){
			logger.fatal("reportUID: "+reportUID+" Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}


	protected boolean itemExists (long reportUID) throws NEDSSDAOSysException,
		NEDSSSystemException
	{
		Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		boolean returnValue = false;

		try
		{
			logger.debug("ReportDAOImpl:findByPrimartyKey  and reportUID is " + reportUID);
			dbConnection = getConnection();
                        preparedStmt = dbConnection.prepareStatement(ReportConstants.SELECT_REPORT_UID);
			preparedStmt.setLong(1, reportUID);
			resultSet = preparedStmt.executeQuery();

			if (!resultSet.next())
			{
				returnValue = false;
			}
            else
            {
              reportUID = resultSet.getLong(1);
              returnValue = true;
            }
        }
        catch(SQLException sex)
        {
        	logger.fatal("reportUID: "+reportUID+" SQLException  = "+sex.getMessage(), sex);
            throw new NEDSSDAOSysException("SQLException while checking for an"
                    + " existing report's uid in report table-&gt; " + reportUID + " :\n" + sex.getMessage());
        }
        catch(Exception ex)
        {
        	logger.fatal("reportUID: "+reportUID+" Exception  = "+ex.getMessage(), ex);
            throw new NEDSSDAOSysException("Exception while checking for an"
                            + " existing report's uid in report table-&gt; " +
                            reportUID + " :\n" + ex.getMessage());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
		}
		logger.debug("ReportDAOImpl:findByPrimartyKey  and returnValue is " +  returnValue);
		return returnValue;
	}

	private ReportDT insertItem(ReportDT reportDT)
		throws NEDSSSystemException
	{
		/**
		 * Starts inserting a new report
		 */
		Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
		String localUID = null;
		UidGeneratorHelper uidGen = null;
		int resultCount = 0;

		/**
		* Inserts data source uid into entity table for REPORT table
		*/

		try
		{
			uidGen = new UidGeneratorHelper();
			reportUID = uidGen.getNbsIDLong(UidClassCodes.NBS_CLASS_CODE).longValue();
			logger.debug("REPORT reportUID is" + reportUID);

			dbConnection = getConnection();
                        preparedStmt = dbConnection.prepareStatement(NEDSSSqlQuery.INSERT_ENTITY);

			int i = 1;
			preparedStmt.setLong(i++, reportUID);
			preparedStmt.setString(i++, "REP");
			resultCount = preparedStmt.executeUpdate();
		}
		catch(SQLException sex)
		{
			logger.fatal("SQLException  = "+sex.getMessage(), sex);
			throw new NEDSSSystemException("SQLException while inserting " +
					"uid for REPORT_TABLE: \n" + sex.toString() + " \n" + sex.getMessage());
		}
		catch(Exception ex)
		{
			logger.fatal("Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException("Exception while inserting " +
					"uid for REPORT_TABLE: \n" + ex.toString() + " \n" + ex.getMessage());
		}
        finally
        {
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);

        }
		logger.debug("REPORT reportUID out of trycatch is" + reportUID);
                try
                {
                	logger.debug("REPORT reportUID is inside the try block is " + reportUID);

                    if (reportUID < 1)
                    {
                        throw new NEDSSSystemException(
                            "Error in reading new entity uid, entity uid = " + reportUID);
                    }
                   // insert into REPORT_TABLE
                    dbConnection = getConnection();
                    preparedStmt = dbConnection.prepareStatement(ReportConstants.INSERT_REPORT);

                    int i = 1;
                    // Set auto generated PK field
                    preparedStmt.setLong(i++,reportUID);
                    // Set all non generated fields
                    if (reportDT.getDataSourceUid() == null)
                       throw new NEDSSSystemException("field dataSourceUid is null and the database tables don't allow it.");
                    else
                        preparedStmt.setLong(i++,reportDT.getDataSourceUid().longValue());
                    preparedStmt.setString(i++,reportDT.getAddReasonCd());
                    if (reportDT.getAddTime() == null)
                     	    preparedStmt.setNull(i++, Types.TIMESTAMP);
                    else
                        preparedStmt.setTimestamp(i++,reportDT.getAddTime());
                    if (reportDT.getAddUserUid() == null)
                     	    preparedStmt.setNull(i++, Types.INTEGER);
                    else
                        preparedStmt.setLong(i++,reportDT.getAddUserUid().longValue());
                      preparedStmt.setString(i++,reportDT.getDescTxt());
                      if (reportDT.getEffectiveFromTime() == null)
                     	    preparedStmt.setNull(i++, Types.TIMESTAMP);
                      else
                          preparedStmt.setTimestamp(i++,reportDT.getEffectiveFromTime());
                      if (reportDT.getEffectiveToTime() == null)
                                      preparedStmt.setNull(i++, Types.TIMESTAMP);
                      else
                           preparedStmt.setTimestamp(i++,reportDT.getEffectiveToTime());
                      if (reportDT.getFilterMode() == null)
                           throw new NEDSSSystemException("field filterMode is null and the database tables don't allow it.");
                      else
                           preparedStmt.setString(i++,reportDT.getFilterMode());
                      if (reportDT.getIsModifiableInd() == null)
                           throw new NEDSSSystemException("field isModifiableInd is null and the database tables don't allow it.");
                      else
                           preparedStmt.setString(i++,reportDT.getIsModifiableInd());
                      preparedStmt.setString(i++,reportDT.getLocation());
                      if (reportDT.getOwnerUid() == null)
                                      preparedStmt.setNull(i++, Types.INTEGER);
                      else
                           preparedStmt.setLong(i++,reportDT.getOwnerUid().longValue());
                      preparedStmt.setString(i++,reportDT.getOrgAccessPermis());
                      preparedStmt.setString(i++,reportDT.getProgAreaAccessPermis());
                      preparedStmt.setString(i++,reportDT.getReportTitle());
                      preparedStmt.setString(i++,reportDT.getReportTypeCode());
                      if (reportDT.getShared() == null)
                           throw new NEDSSSystemException("field shared is null and the database tables don't allow it.");
                      else
                           preparedStmt.setString(i++,reportDT.getShared());
                      if (reportDT.getStatusCd() == null)
                           throw new NEDSSSystemException("field statusCd is null and the database tables don't allow it.");
                      else
                           preparedStmt.setString(i++,reportDT.getStatusCd());
                      if (reportDT.getStatusTime() == null)
                                      preparedStmt.setNull(i++, Types.TIMESTAMP);
                      else
                           preparedStmt.setTimestamp(i++,reportDT.getStatusTime());
                      preparedStmt.setString(i++,reportDT.getCategory());
                      preparedStmt.setLong(i++,(reportDT.getSectionCd().longValue()));


                      resultCount = preparedStmt.executeUpdate();
                              logger.debug("done insert report! reportUID = " + reportUID);

                              reportDT.setReportUid(new Long(reportUID));
                      return reportDT;
          }
          catch(SQLException sex)
          {
        	  logger.fatal("SQLException  = "+sex.getMessage(), sex);
            throw new NEDSSSystemException("SQLException while inserting " +
                    "report into REPORT_TABLE: \n" + sex.toString() +
                    " \n" + sex.getMessage());
          }
          catch(Exception ex)
          {
        	  logger.fatal("Exception  = "+ex.getMessage(), ex);
              throw new NEDSSSystemException("Error while inserting into REPORT_TABLE, id = " + reportUID);
          }
          finally
          {
              closeStatement(preparedStmt);
              releaseConnection(dbConnection);
          }

    }//end of inserting report


    private ReportDT updateItem(ReportDT reportDT) throws NEDSSSystemException
    {
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        int resultCount = 0;


        try
        {
            logger.debug("ReportDAOImpl:updateItem  and reportDT :" + reportDT);
                //Updates ReportDT table
            if (reportDT != null)
            {

				dbConnection = getConnection();
                                preparedStmt = dbConnection.prepareStatement(ReportConstants.UPDATE_REPORT);

				int i = 1;
				logger.debug("ReportDAOImpl:update and the value is " + reportDT.getReportUid());
				 // first set non-PK on UPDATE statement
					  preparedStmt.setString(i++,reportDT.getAddReasonCd());
				if (reportDT.getAddTime() == null)
								preparedStmt.setNull(i++, Types.TIMESTAMP);
				else
					 preparedStmt.setTimestamp(i++,reportDT.getAddTime());
				if (reportDT.getAddUserUid() == null)
								preparedStmt.setNull(i++, Types.INTEGER);
				else
					 preparedStmt.setLong(i++,reportDT.getAddUserUid().longValue());
				preparedStmt.setString(i++,reportDT.getDescTxt());
				if (reportDT.getEffectiveFromTime() == null)
								preparedStmt.setNull(i++, Types.TIMESTAMP);
				else
					 preparedStmt.setTimestamp(i++,reportDT.getEffectiveFromTime());
				if (reportDT.getEffectiveToTime() == null)
								preparedStmt.setNull(i++, Types.TIMESTAMP);
				else
					 preparedStmt.setTimestamp(i++,reportDT.getEffectiveToTime());
				if (reportDT.getFilterMode() == null)
					 throw new NEDSSSystemException("field filterMode is null and the database tables don't allow it.");
				else
					 preparedStmt.setString(i++,reportDT.getFilterMode());
				if (reportDT.getIsModifiableInd() == null)
					 throw new NEDSSSystemException("field isModifiableInd is null and the database tables don't allow it.");
				else
					 preparedStmt.setString(i++,reportDT.getIsModifiableInd());
				preparedStmt.setString(i++,reportDT.getLocation());
				if (reportDT.getOwnerUid() == null)
								preparedStmt.setNull(i++, Types.INTEGER);
				else
					 preparedStmt.setLong(i++,reportDT.getOwnerUid().longValue());
				preparedStmt.setString(i++,reportDT.getOrgAccessPermis());
				preparedStmt.setString(i++,reportDT.getProgAreaAccessPermis());
				preparedStmt.setString(i++,reportDT.getReportTitle());
				preparedStmt.setString(i++,reportDT.getReportTypeCode());
				if (reportDT.getShared() == null)
					 throw new NEDSSSystemException("field shared is null and the database tables don't allow it.");
				else
					 preparedStmt.setString(i++,reportDT.getShared());
				if (reportDT.getStatusCd() == null)
					 throw new NEDSSSystemException("field statusCd is null and the database tables don't allow it.");
				else
					 preparedStmt.setString(i++,reportDT.getStatusCd());
				if (reportDT.getStatusTime() == null)
								preparedStmt.setNull(i++, Types.TIMESTAMP);
				else
					 preparedStmt.setTimestamp(i++,reportDT.getStatusTime());
				preparedStmt.setString(i++,reportDT.getCategory());

				 // next set PK items on UPDATE's WHERE clause
				 if (reportDT.getReportUid() == null)
					 throw new NEDSSSystemException("field reportUid is null and the database tables don't allow it.");
				else
					 preparedStmt.setLong(i++,reportDT.getReportUid().longValue());
				logger.debug("\n\n\nReportDAOImpl:update and the getReportUid is " + reportDT.getReportUid());
				if (reportDT.getDataSourceUid() == null)
					 throw new NEDSSSystemException("field dataSourceUid is null and the database tables don't allow it.");
				else
					 preparedStmt.setLong(i++,reportDT.getDataSourceUid().longValue());
				if(reportDT.getSectionCd()!=null)
				 preparedStmt.setLong(i++,(reportDT.getSectionCd().longValue()));
				else
					preparedStmt.setNull(i++,Types.INTEGER);

				 resultCount = preparedStmt.executeUpdate();

                 if ( resultCount > 1 )
                 {
                    throw new NEDSSSystemException("Error: none or more than one report updated at a time, " + "resultCount = " + resultCount);
                 }
            }
        }
        catch(SQLException sex)
        {
        	logger.fatal("SQLException  = "+sex.getMessage(), sex);
            throw new NEDSSSystemException("SQLException while updating " +
                    "report into REPORT_TABLE: \n" + sex.toString() +
                    " \n" + sex.getMessage());
        }
        catch(Exception ex)
        {
        	logger.fatal("Exception  = "+ex.getMessage(), ex);
            throw new NEDSSDAOSysException("Exception while updating "
                            + " report table-&gt; " + ex.getMessage());
        }
        finally
        {
            //Cleans up resources
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
		return reportDT;
    }

    private ReportDT selectItem(long reportUID)
    	throws NEDSSSystemException
    {
        ReportDT reportDT = new ReportDT();
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData = null;
        ResultSetUtils resultSetUtils = new ResultSetUtils();
        ArrayList<Object> pList = new ArrayList<Object> ();

        /**
         * Selects report from report table
         */
        try
        {
            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(ReportConstants.SELECT_REPORT);
            preparedStmt.setLong(1, reportUID);
            resultSet = preparedStmt.executeQuery();

            logger.debug("reportDT object for: reportUID = " + reportUID);

            resultSetMetaData = resultSet.getMetaData();

            pList = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData, reportDT.getClass(), pList);

            for(Iterator<Object> anIterator = pList.iterator(); anIterator.hasNext(); )
            {
                reportDT = (ReportDT)anIterator.next();
                reportDT.setItNew(false);
                reportDT.setItDirty(false);
                reportDT.setItDelete(false);
            }

        }
        catch(SQLException sex)
        {
        	logger.fatal("reportUID: "+reportUID+" SQLException  = "+sex.getMessage(), sex);
            throw new NEDSSSystemException("SQLException while selecting " +
                            "report vo; id = " + reportUID + " :\n" + sex.getMessage());
        }
        catch(Exception ex)
        {
        	logger.fatal("reportUID: "+reportUID+" Exception  = "+ex.getMessage(), ex);
            throw new NEDSSSystemException("Exception while selecting " +
                            "report vo; id = " + reportUID + " :\n" + ex.getMessage());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }

		logger.debug("returning report DT object");
        return reportDT;
    }//end of selecting item

    private void removeItem(long reportUID) throws NEDSSSystemException
    {
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        int resultCount = 0;


        /**
         * Deletes reports
         */
        try
        {
            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(ReportConstants.DELETE_REPORT);
            preparedStmt.setLong(1, reportUID);
            resultCount = preparedStmt.executeUpdate();

            if (resultCount != 1)
            {
                throw new NEDSSSystemException
                ("Error: cannot delete report from REPORT_TABLE!! resultCount = " +
                 resultCount);
            }
        }
        catch(SQLException sex)
        {
        	logger.fatal("reportUID: "+reportUID+" SQLException  = "+sex.getMessage(), sex);
            throw new NEDSSSystemException("SQLException while removing " +
                            "report; id = " + reportUID + " :\n" + sex.getMessage());
        }
        finally
        {
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }

    }


/**		public static void main(String args[]){
        logger.debug("DataSourceDAOImpl - Doing the main thing");


        try{

	        ReportDAOImpl reportDAOImpl = new ReportDAOImpl();
	        ReportDT reportDT = new ReportDT();
			reportDT.setAddReasonCd("test");
			reportDT.setAddUserUid(new Long(20));
			reportDT.setFilterMode("test");
			reportDT.setDataSourceUid(new Long(470003427));
			reportDT.setIsModifiableInd("test");
			reportDT.setShared("test d");
			reportDT.setStatusCd("test");
			reportDAOImpl.create(reportDT);

	    //To test find Method
            long uid = 1385447555;
			//reportDAOImpl.findByPrimaryKey(uid);
	    //To Test set(change in values)
			reportDT.setAddReasonCd("test abc");
			reportDT.setAddUserUid(new Long(20));
			reportDT.setFilterMode("test");
			reportDT.setDataSourceUid(new Long(470003432));
			reportDT.setIsModifiableInd("test");
			reportDT.setShared("test");
			reportDT.setStatusCd("test");
			reportDT.setReportUid(new Long(1385447555));
			//reportDAOImpl.store(reportDT);


	    } catch(Exception e){}
	}
*/

}
