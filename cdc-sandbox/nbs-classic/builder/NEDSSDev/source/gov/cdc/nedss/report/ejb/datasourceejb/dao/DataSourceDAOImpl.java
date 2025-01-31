

/**
* Name:		data_sourceDAOImpl.java
* Description:	This is the implementation of NEDSSDAOInterface for the
*               data_source value object in the data_source entity bean.
*               This class encapsulates all the JDBC calls made by the data_sourceEJB
*               for a data_source object. Actual logic of
*               inserting/reading/updating/deleting the data in relational
*               database tables to mirror the state of data_sourceEJB is
*               implemented here.
* Copyright:	Copyright (c) 2001
* Company: 	Computer Sciences Corporation
* @author	Pradeep Sharma
* @version	1.0
*/


package gov.cdc.nedss.report.ejb.datasourceejb.dao;

import gov.cdc.nedss.entity.sqlscript.NEDSSSqlQuery;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.report.dt.DataSourceDT;
import gov.cdc.nedss.report.util.ReportConstants;
import gov.cdc.nedss.systemservice.uidgenerator.UidClassCodes;
import gov.cdc.nedss.systemservice.uidgenerator.UidGeneratorHelper;
import gov.cdc.nedss.util.BMPBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.ResultSetUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;



public class DataSourceDAOImpl extends BMPBase
{
	static final LogUtils logger = new LogUtils((DataSourceDAOImpl.class).getName());
	private static final String ENTITY_UID = "ENTITY_UID";



	private long datasourceUID = -1;

	public DataSourceDAOImpl()
		throws NEDSSDAOSysException, NEDSSSystemException
	{

	}

	/**
	 * public method to create the dataSourceDT Object
	 */
	public DataSourceDT createDataSource(Object obj ) throws NEDSSDAOSysException, NEDSSSystemException
	{
		try{
			DataSourceDT dataSourceDT = (DataSourceDT) obj;
	
			DataSourceDT dataSource = insertItem(dataSourceDT);
			dataSourceDT.setItNew(false);
			dataSourceDT.setItDirty(false);
			dataSourceDT.setItDelete(false);
			return dataSource;
		}catch(NEDSSDAOSysException ex){
			logger.fatal("NEDSSDAOSysException  = "+ex.getMessage(), ex);
			throw new NEDSSDAOSysException(ex.toString());
		}catch(NEDSSSystemException ex){
			logger.fatal("NEDSSSystemException  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	public DataSourceDT storeDataSource(Object obj)
		throws NEDSSDAOSysException, NEDSSSystemException
	{
		try{
			logger.debug("DataSourceDAOImpl:Store Inside store");
			DataSourceDT dataSourceDT = updateItem( (DataSourceDT) obj);
			return dataSourceDT;
		}catch(NEDSSDAOSysException ex){
			logger.fatal("NEDSSDAOSysException  = "+ex.getMessage(), ex);
			throw new NEDSSDAOSysException(ex.toString());
		}catch(NEDSSSystemException ex){
			logger.fatal("NEDSSSystemException  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	public void remove(long datasourceUID)
		throws NEDSSDAOSysException, NEDSSSystemException
	{
		removeItem(datasourceUID);
	}

	public Object loadObject(long datasourceUID) throws NEDSSDAOSysException,
		 NEDSSSystemException
	{
		try{
			DataSourceDT dataSourceDT = selectItem(datasourceUID);
			dataSourceDT.setItNew(false);
			dataSourceDT.setItDirty(false);
			dataSourceDT.setItDelete(false);
			return dataSourceDT;
		}catch(NEDSSDAOSysException ex){
			logger.fatal("NEDSSDAOSysException  = "+ex.getMessage(), ex);
			throw new NEDSSDAOSysException(ex.toString());
		}catch(NEDSSSystemException ex){
			logger.fatal("NEDSSSystemException  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	public Long findByPrimaryKey(long datasourceUID)
		throws NEDSSDAOSysException, NEDSSSystemException
	{
		try{
			if (itemExists(datasourceUID))
			{
				logger.debug("DataSourceDAOImpl:findByPrimaryKey the primaryKey value for DataSource found is : " + datasourceUID);
				return (new Long(datasourceUID));
			}
			else
			{
				logger.debug("DataSourceDAOImpl:findByPrimaryKey No primaryKey value for DataSource found is : " + datasourceUID);
				throw new NEDSSDAOSysException("No datasource found for this primary key :" + datasourceUID);
			}
		}catch(NEDSSDAOSysException ex){
			logger.fatal("NEDSSDAOSysException  = "+ex.getMessage(), ex);
			throw new NEDSSDAOSysException(ex.toString());
		}catch(NEDSSSystemException ex){
			logger.fatal("NEDSSSystemException  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}


	protected boolean itemExists (long datasourceUID) throws NEDSSDAOSysException,
		NEDSSSystemException
	{
		Connection dbConnection = null;
                PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		boolean returnValue = false;

		try
		{
			dbConnection = getConnection();
                        preparedStmt = dbConnection.prepareStatement(ReportConstants.SELECT_DATASOURCE_UID);
			preparedStmt.setLong(1, datasourceUID);
			resultSet = preparedStmt.executeQuery();

			if (!resultSet.next())
			{
				returnValue = false;
			}
			else
			{
				datasourceUID = resultSet.getLong(1);
				returnValue = true;
			}
		}
		catch(SQLException sex)
		{
			logger.fatal("SQLException = "+sex.getMessage(), sex);
			throw new NEDSSDAOSysException("SQLException while checking for an"
							+ " existing datasource's uid in datasource table-&gt; " + datasourceUID + " :\n" + sex.getMessage());
		}
		catch(Exception ex)
		{
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSDAOSysException("Exception while checking for an"
							+ " existing datasource's uid in datasource table-&gt; " +
							datasourceUID + " :\n" + ex.getMessage());
		}
		finally
		{
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
		return returnValue;
	}

	private DataSourceDT insertItem(DataSourceDT dataSourceDT)
			throws NEDSSSystemException
	{
		/**
		 * Starts inserting a new datasource
		 */
		Connection dbConnection = null;
                PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		String localUID = null;
		UidGeneratorHelper uidGen = null;
		int resultCount = 0;

		/**
		* Inserts data source uid into entity table for dataSource
		*/
        try
        {
			try
			{
				uidGen = new UidGeneratorHelper();
				datasourceUID = uidGen.getNbsIDLong(UidClassCodes.NBS_CLASS_CODE).longValue();
	
				dbConnection = getConnection();
	                        preparedStmt = dbConnection.prepareStatement(NEDSSSqlQuery.INSERT_ENTITY);
	
				int i = 1;
				preparedStmt.setLong(i++, datasourceUID);
				preparedStmt.setString(i++, NEDSSConstants.DATASOURCE);
				resultCount = preparedStmt.executeUpdate();
			}
			catch(SQLException sex)
			{
				logger.fatal("Exception = "+sex.getMessage(), sex);
				throw new NEDSSSystemException("SQLException while inserting " +
						"uid for DATA_SOURCE_TABLE: \n" + sex.toString() + " \n" + sex.getMessage());
			}
			catch(Exception ex)
			{
				logger.fatal("Exception = "+ex.getMessage(), ex);
				throw new NEDSSSystemException("Exception while inserting " +
						"uid for DATA_SOURCE_TABLE: \n" + ex.toString() + " \n" + ex.getMessage());
			}
            finally
            {
            	closeResultSet(resultSet);
                closeStatement(preparedStmt);
                releaseConnection(dbConnection);
            }
			try
			{
				if (datasourceUID < 1)
				{
					throw new NEDSSSystemException(
							  "Error in reading new entity uid, entity uid = " + datasourceUID);
				}
	
				 // insert into DATASOURCE_TABLE
	
				dbConnection = getConnection();
	                        preparedStmt = dbConnection.prepareStatement(ReportConstants.INSERT_DATASOURCE);
	
	
				int i = 1;
				// Set auto generated PK field
				dataSourceDT.setDataSourceUid(new Long(datasourceUID));
				preparedStmt.setLong(i++,datasourceUID);
	
				// Set all non generated fields
				if (dataSourceDT.getDataSourceName() == null)
				 throw new NEDSSSystemException("field dataSourceName is null and the database tables don't allow it.");
				else
				 preparedStmt.setString(i++,dataSourceDT.getDataSourceName());
				preparedStmt.setString(i++,dataSourceDT.getDataSourceTitle());
				preparedStmt.setString(i++,dataSourceDT.getDataSourceTypeCode());
				preparedStmt.setString(i++,dataSourceDT.getDescTxt());
				preparedStmt.setString(i++,dataSourceDT.getConditionSecurity());
				if (dataSourceDT.getEffectiveFromTime() == null)
					preparedStmt.setNull(i++, Types.TIMESTAMP);
				else
					preparedStmt.setTimestamp(i++,dataSourceDT.getEffectiveFromTime());
				if (dataSourceDT.getEffectiveToTime() == null)
					preparedStmt.setNull(i++, Types.TIMESTAMP);
				else
					preparedStmt.setTimestamp(i++,dataSourceDT.getEffectiveToTime());
				preparedStmt.setString(i++,dataSourceDT.getJurisdictionSecurity());
				preparedStmt.setString(i++,dataSourceDT.getOrgAccessPermis());
				preparedStmt.setString(i++,dataSourceDT.getProgAreaAccessPermis());
				preparedStmt.setString(i++,dataSourceDT.getStatusCd());
				if (dataSourceDT.getStatueTime() == null)
					preparedStmt.setNull(i++, Types.TIMESTAMP);
				else
					preparedStmt.setTimestamp(i++,dataSourceDT.getStatueTime());
	
	
				resultCount = preparedStmt.executeUpdate();
				logger.debug("DataSourceDAOImpl:insertItem done insert datasource! datasourceUID = " + datasourceUID);
	
				return dataSourceDT;
			}
			catch(SQLException sex)
			{
				logger.fatal("Exception = "+sex.getMessage(), sex);
				throw new NEDSSSystemException("SQLException while inserting " +
						"datasource into DATASOURCE_TABLE: \n" + sex.toString() +
						" \n" + sex.getMessage());
			}
			catch(Exception ex)
			{
				logger.fatal("Exception = "+ex.getMessage(), ex);
				throw new NEDSSSystemException("Error while inserting into DATASOURCE_TABLE, id = " + datasourceUID);
			}
            finally
            {
              closeResultSet(resultSet);
              closeStatement(preparedStmt);
              releaseConnection(dbConnection);

            }
        }
        finally
        {
          closeResultSet(resultSet);
          closeStatement(preparedStmt);
          releaseConnection(dbConnection);
        }

	}//end of inserting datasource


	private DataSourceDT updateItem(DataSourceDT dataSourceDT) throws NEDSSSystemException
	{
		Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
		int resultCount = 0;

		try
		{
			//Updates DataSourceDT table
			if (dataSourceDT != null)
			{
				logger.debug("DataSourceDAOImpl:updateItem Inside update");
				dbConnection = getConnection();
                                preparedStmt = dbConnection.prepareStatement(ReportConstants.UPDATE_DATASOURCE);
				logger.debug("DataSourceDAOImpl:updateItem Inside update 2");


				int i = 1;

				// first set non-PK on UPDATE statement
				 if (dataSourceDT.getDataSourceName() == null)
				 {
					 throw new NEDSSSystemException("field dataSourceName is null and the database tables don't allow it.");
				 }
				 else
					 preparedStmt.setString(i++,dataSourceDT.getDataSourceName());
				 preparedStmt.setString(i++,dataSourceDT.getDataSourceTitle());
				 preparedStmt.setString(i++,dataSourceDT.getDataSourceTypeCode());
				 preparedStmt.setString(i++,dataSourceDT.getDescTxt());
				 preparedStmt.setString(i++,dataSourceDT.getConditionSecurity());
				 if (dataSourceDT.getEffectiveFromTime() == null)
								preparedStmt.setNull(i++, Types.TIMESTAMP);
				 else
					 preparedStmt.setTimestamp(i++,dataSourceDT.getEffectiveFromTime());
				 if (dataSourceDT.getEffectiveToTime() == null)
								preparedStmt.setNull(i++, Types.TIMESTAMP);
				 else
					 preparedStmt.setTimestamp(i++,dataSourceDT.getEffectiveToTime());
				 preparedStmt.setString(i++,dataSourceDT.getJurisdictionSecurity());
				 preparedStmt.setString(i++,dataSourceDT.getOrgAccessPermis());
				 preparedStmt.setString(i++,dataSourceDT.getProgAreaAccessPermis());
				 preparedStmt.setString(i++,dataSourceDT.getStatusCd());
				 if (dataSourceDT.getStatueTime() == null)
								preparedStmt.setNull(i++, Types.TIMESTAMP);
				 else
					 preparedStmt.setTimestamp(i++,dataSourceDT.getStatueTime());

				 // next set PK items on UPDATE's WHERE clause
				 if (dataSourceDT.getDataSourceUid() == null)
					 throw new NEDSSSystemException("field dataSourceUid is null and the database tables don't allow it.");
				else
					 preparedStmt.setLong(i++,dataSourceDT.getDataSourceUid().longValue());


				 resultCount = preparedStmt.executeUpdate();
				 logger.debug("the final value is " + dataSourceDT.getDataSourceTitle());
				if ( resultCount != 1 )
				{
					throw new NEDSSSystemException("Error: none or more than one datasource updated at a time, " + "resultCount = " + resultCount);
				}
			}
		}
		catch(SQLException sex)
		{
			logger.fatal("Exception = "+sex.getMessage(), sex);
			throw new NEDSSSystemException("SQLException while updating " +
					"datasource into DATASOURCE_TABLE: \n" + sex.toString() +
					" \n" + sex.getMessage());
		}
        finally
        {
          closeStatement(preparedStmt);
          releaseConnection(dbConnection);
        }
		return dataSourceDT;
	}

	private DataSourceDT selectItem(long datasourceUID)
		throws NEDSSSystemException
	{
		DataSourceDT dataSourceDT = new DataSourceDT();
		Connection dbConnection = null;
                PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		ResultSetMetaData resultSetMetaData = null;
		ResultSetUtils resultSetUtils = new ResultSetUtils();
		ArrayList<Object> pList = new ArrayList<Object> ();

		/**
		 * Selects datasource from data_source table
		 */
		try
		{
			dbConnection = getConnection();
                        preparedStmt = dbConnection.prepareStatement(ReportConstants.SELECT_DATASOURCE);
			preparedStmt.setLong(1, datasourceUID);
			resultSet = preparedStmt.executeQuery();

			logger.debug("dataSourceDT object for: datasourceUID = " + datasourceUID);

			resultSetMetaData = resultSet.getMetaData();

			pList = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData, dataSourceDT.getClass(), pList);

			for(Iterator<Object> anIterator = pList.iterator(); anIterator.hasNext(); )
			{
				dataSourceDT = (DataSourceDT)anIterator.next();
				dataSourceDT.setItNew(false);
				dataSourceDT.setItDirty(false);
				dataSourceDT.setItDelete(false);
			}

		}
		catch(SQLException sex)
		{
			logger.fatal("Exception = "+sex.getMessage(), sex);
			throw new NEDSSSystemException("SQLException while selecting " +
							"dataSource vo; id = " + datasourceUID + " :\n" + sex.getMessage());
		}
		catch(Exception ex)
		{
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException("Exception while selecting " +
							"dataSource vo; id = " + datasourceUID + " :\n" + ex.getMessage());
		}
		finally
		{
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}

		logger.debug("returning dataSource DT object");
		return dataSourceDT;
	}//end of selecting item

	private void removeItem(long datasourceUID) throws NEDSSSystemException
	{
		Connection dbConnection = null;
                PreparedStatement preparedStmt = null;
		int resultCount = 0;

		/**
		 * Deletes datasources
		 */
		try
		{
			dbConnection = getConnection();
                        preparedStmt = dbConnection.prepareStatement(ReportConstants.DELETE_DATASOURCE);
			preparedStmt.setLong(1, datasourceUID);
			resultCount = preparedStmt.executeUpdate();

			if (resultCount != 1)
			{
				throw new NEDSSSystemException
				("Error: cannot delete datasource from DATASOURCE_TABLE!! resultCount = " +
				 resultCount);
			}
		}
		catch(SQLException sex)
		{
			logger.fatal("Exception = "+sex.getMessage(), sex);
			throw new NEDSSSystemException("SQLException while removing " +
							"datasource; id = " + datasourceUID + " :\n" + sex.getMessage());
		}
		finally
		{
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
	}


	/**	public static void main(String args[]){
	logger.debug("DataSourceDAOImpl - Doing the main thing");


        try{

	        DataSourceDAOImpl dataSourceDaoImpl = new DataSourceDAOImpl();
	        DataSourceDT dataSourceDT = new DataSourceDT();
		    //create Method
			dataSourceDT.setConditionSecurity("test1");
			dataSourceDT.setDataSourceName("tesg");
			dataSourceDT.setDataSourceTitle("test1");
			dataSourceDT.setDataSourceTypeCode("test");
			dataSourceDT.setDescTxt("test");
			dataSourceDT.setEffectiveFromTime(new Timestamp(new java.util.Date().getTime()));
			dataSourceDT.setEffectiveToTime(new Timestamp(new java.util.Date().getTime()));
		    dataSourceDT.setJurisdictionSecurity("test");
			dataSourceDT.setOrgAccessPermis("test");
			dataSourceDT.setProgAreaAccessPermis("test");
			dataSourceDT.setStatueTime(new Timestamp(new java.util.Date().getTime()));
			dataSourceDT.setStatusCd("test");
			dataSourceDT.setProgAreaAccessPermis("tevdf");
			dataSourceDaoImpl.create(dataSourceDT);
	//To test find Method
	       // dataSourceDT.set
		   //long uid = 470003431;
			//dataSourceDaoImpl.findByPrimaryKey(uid);
	//To Test set(change in values)
			dataSourceDT.setDataSourceUid(new Long(470003432));
			dataSourceDT.setDataSourceName("test");
            dataSourceDT.setDataSourceTitle("ABCD");
			//dataSourceDaoImpl.store(dataSourceDT);


	    } catch(Exception e){}
	}
*/

}
