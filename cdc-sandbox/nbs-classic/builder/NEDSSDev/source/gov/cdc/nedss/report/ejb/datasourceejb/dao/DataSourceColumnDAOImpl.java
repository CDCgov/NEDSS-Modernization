/**
* Name:		data_sourceColumnDAOImpl.java
* Description:	This is the implementation of NEDSSDAOInterface for the
*               data_source_column value object in the data_source_column entity bean.
*               This class encapsulates all the JDBC calls made by the dataSourceEJB
*               for a data_source_column object. Actual logic of
*               inserting/reading/updating/deleting the data in relational
*               database tables to mirror the state of dataSourceEJB is
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
import gov.cdc.nedss.report.dt.DataSourceColumnDT;
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
import java.util.Collection;
import java.util.Iterator;


public class DataSourceColumnDAOImpl extends BMPBase
{
	static final LogUtils logger = new LogUtils((DataSourceColumnDAOImpl.class).getName());
	private static final String ENTITY_UID = "ENTITY_UID";
	private long datasourcecolumnUID = -1;

	public DataSourceColumnDAOImpl()
		throws NEDSSDAOSysException, NEDSSSystemException
	{
	}

	/**
	 * This public method creates a collection DataSourceColumnDT based for a given dataSourceUID.
	 */

	public Collection<Object>  createDataSourceColumns(long dataSourceUID, Collection<Object>  dataSourceColumns) throws NEDSSDAOSysException, NEDSSSystemException
	{
		ArrayList<Object> list = new ArrayList<Object> ();
		list = (ArrayList<Object> )insertDataSourceColumns(dataSourceUID, dataSourceColumns);
		return list;
	}

	/**
	 * Stores(updates) the DataSourceColumn collection into the database(for existing values)
	 */
	public Collection<Object>  storeDataSourceColumns(Collection<Object> coll)
		throws NEDSSDAOSysException, NEDSSSystemException
	{
		ArrayList<Object> displayColumnsList = null;
		logger.debug("inside the store");
		//updateItem( (DataSourceColumnDT) obj);
		displayColumnsList =  (ArrayList<Object> )updateDataSourceColumn(coll);
		return displayColumnsList;
	}

	/**
	 * Removes a dataSourceColumn Object from the database for a given UID
	 */
	public void remove(long dataSourceColumnUID)
	    throws NEDSSDAOSysException, NEDSSSystemException
	{
		removeDataSourceColumns(dataSourceColumnUID);
	}

	/**
	 * Public method to find and load a list of DataSourceColumns objects for a given dataSourceUID.
	 */
	public Collection<Object>  load(long dataSourceUID) throws NEDSSDAOSysException,
		 NEDSSSystemException
	{
		Collection<Object>  pnColl = selectDataSourceColumns(dataSourceUID);
		return pnColl;
	}

	/**
	 * This method check if the dataSourceColumnUID exists in the database
	 */
	public Long findByPrimaryKey(long datasourcecolumnUID)
		throws NEDSSDAOSysException, NEDSSSystemException
	{
		if (itemExists(datasourcecolumnUID))
		{
			logger.debug("the DataSource Uid is " + 	datasourcecolumnUID);
			return (new Long(datasourcecolumnUID));
		}
		else
			throw new NEDSSDAOSysException("No datasourcecolumn found for this primary key :" + datasourcecolumnUID);
    }


	/**
	 * Protected method that supports the findByPrimaryKey method if a dataSourceColumnUID exists in the database or not.
	 */
	protected boolean itemExists (long datasourcecolumnUID) throws NEDSSDAOSysException,
			NEDSSSystemException
	{
		Connection dbConnection = null;
                PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		boolean returnValue = false;

		try
		{
			dbConnection = getConnection();
                        preparedStmt = dbConnection.prepareStatement(ReportConstants.SELECT_DATASOURCECOLUMN_UID);
			preparedStmt.setLong(1, datasourcecolumnUID);
			resultSet = preparedStmt.executeQuery();

		if (!resultSet.next())
		{
			returnValue = false;
		}
			else
			{
				datasourcecolumnUID = resultSet.getLong(1);
				returnValue = true;
			}
		}
		catch(SQLException sex)
		{
			logger.fatal("SQLException = "+sex.getMessage(), sex);
			throw new NEDSSDAOSysException("SQLException while checking for an"
							+ " existing datasourcecolumn's uid in datasourcecolumn table-&gt; " + datasourcecolumnUID + " :\n" + sex.getMessage());
		}
		catch(Exception ex)
		{
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSDAOSysException("Exception while checking for an"
							+ " existing datasourcecolumn's uid in datasourcecolumn table-&gt; " +
							datasourcecolumnUID + " :\n" + ex.getMessage());
		}
		finally
		{
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
		return returnValue;
	}

	/**
	 * For a given a collection of dataSourceColumn objects, this method iterates through the collection and
	 * creates the DataSourceColumnDT objects tied to the given dataSourceUID.
	 */

	private Collection<Object>  insertDataSourceColumns(long dataSourceUID, Collection<Object>  dataSourceColumns)throws NEDSSSystemException
	{
		ArrayList<Object> dataSourceColumnsResult = new ArrayList<Object> ();
		Iterator anIterator = null;
		try
		{
			for(anIterator = dataSourceColumns.iterator(); anIterator.hasNext();)
			{
				DataSourceColumnDT dataSourceColumnDT = (DataSourceColumnDT)anIterator.next();
				if(dataSourceColumnDT!= null)
				{
					dataSourceColumnDT = insertDataSourceColumn(dataSourceUID, dataSourceColumnDT);
					dataSourceColumnsResult.add(dataSourceColumnDT);
				}
			}
		}
		catch(NEDSSSystemException nsex)
		{
			logger.fatal("Exception = "+nsex.getMessage(), nsex);
			throw new NEDSSSystemException("SQLException while obtaining database connection " +
						"for deleting datasourcecolumn " + nsex.getMessage());
		}
		return dataSourceColumnsResult;
	}

	/**
	 * This is the method that actually creates the DataSourceColumnDT object.
	 */
	private DataSourceColumnDT insertDataSourceColumn(long dataSourceUID, DataSourceColumnDT dataSourceColumnDT)
			throws NEDSSSystemException
	{
	/**
	 * Starts inserting a new datasourcecolumn
	 */
		Connection dbConnection = null;
                PreparedStatement preparedStmt = null;
		String localUID = null;
		UidGeneratorHelper uidGen = null;
		int resultCount = 0;
        try
        {
		  /**
		  * Inserts data source uid into entity table for data source column table
		  */

	  	  try
		  {
			uidGen = new UidGeneratorHelper();
			datasourcecolumnUID = uidGen.getNbsIDLong(UidClassCodes.NBS_CLASS_CODE).longValue();

			dbConnection = getConnection();
                        preparedStmt = dbConnection.prepareStatement(NEDSSSqlQuery.INSERT_ENTITY);

			int i = 1;
			preparedStmt.setLong(i++, datasourcecolumnUID);
			preparedStmt.setString(i++, NEDSSConstants.DATASOURCECOLUMN);
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
        	  closeStatement(preparedStmt);
        	  releaseConnection(dbConnection);
          }

		  try
		  {
			if (datasourcecolumnUID < 1)
			{
				throw new NEDSSSystemException(
						  "Error in reading new entity uid, entity uid = " + datasourcecolumnUID);
			}
			 // insert into DATASOURCECOLUMN_TABLE

			dbConnection = getConnection();
                        preparedStmt = dbConnection.prepareStatement(ReportConstants.INSERT_DATASOURCECOLUMN);


			int i = 1;
			// Set auto generated PK field
			dataSourceColumnDT.setColumnUid(new Long(datasourcecolumnUID));
			dataSourceColumnDT.setDataSourceUid(new Long(dataSourceUID));
			 preparedStmt.setLong(i++,datasourcecolumnUID);

			// Set all non generated fields
			 if (dataSourceColumnDT.getColumnName() == null)
				 throw new NEDSSSystemException("field columnName is null and the database tables don't allow it.");
			else
				 preparedStmt.setString(i++,dataSourceColumnDT.getColumnName());
			preparedStmt.setString(i++,dataSourceColumnDT.getColumnTitle());
			preparedStmt.setString(i++,dataSourceColumnDT.getColumnTypeCode());
			if (dataSourceColumnDT.getDataSourceUid() == null)
				 throw new NEDSSSystemException("field dataSourceUid is null and the database tables don't allow it.");
			else
				 preparedStmt.setLong(i++,dataSourceColumnDT.getDataSourceUid().longValue());
			preparedStmt.setString(i++,dataSourceColumnDT.getDescTxt());
			preparedStmt.setString(i++,dataSourceColumnDT.getDisplayable());
			if (dataSourceColumnDT.getEffectiveFromTime() == null)
							preparedStmt.setNull(i++, Types.TIMESTAMP);
			else
				 preparedStmt.setTimestamp(i++,dataSourceColumnDT.getEffectiveFromTime());
			if (dataSourceColumnDT.getEffectiveToTime() == null)
							preparedStmt.setNull(i++, Types.TIMESTAMP);
			else
				 preparedStmt.setTimestamp(i++,dataSourceColumnDT.getEffectiveToTime());
			preparedStmt.setString(i++,dataSourceColumnDT.getFilterable());
			preparedStmt.setString(i++,dataSourceColumnDT.getStatusCd());
			if (dataSourceColumnDT.getStatusTime() == null)
							preparedStmt.setNull(i++, Types.TIMESTAMP);
			else
				 preparedStmt.setTimestamp(i++,dataSourceColumnDT.getStatusTime());
			if(dataSourceColumnDT.getColumnMaxLen() ==  null)
						   preparedStmt.setNull(i++, Types.INTEGER);
			else
				 preparedStmt.setInt(i++,dataSourceColumnDT.getColumnMaxLen().intValue());

			resultCount = preparedStmt.executeUpdate();
			logger.debug("done insert datasourcecolumn! datasourcecolumnUID = " + datasourcecolumnUID);

			return dataSourceColumnDT;
		}
		catch(SQLException sex)
		{
			logger.fatal("Exception = "+sex.getMessage(), sex);
			throw new NEDSSSystemException("SQLException while inserting " +
					"datasourcecolumn into DATASOURCECOLUMN_TABLE: \n" + sex.toString() +
					" \n" + sex.getMessage());
		}
		catch(Exception ex)
		{
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException("Error while inserting into DATASOURCECOLUMN_TABLE, id = " + datasourcecolumnUID);
		}
        finally
        {
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
        }


	  }
      finally
	  {
		closeStatement(preparedStmt);
		releaseConnection(dbConnection);
      }

	}//end of inserting datasourcecolumn

	/**
	 * This method updates the dataSourceColumn Collection. This method iterates through the collection and
	 * checks for the flags(IsItNew, IsItDirty and IsItDelete).It creates the new dataSourceColumnDT object if
	 * the flag is set to true, it updates the record if the flag is set to dirty and deletes the entry if it
	 * is set to delete
	 */

	private Collection<Object>  updateDataSourceColumn(Collection<Object> DataSourceColumns) throws NEDSSSystemException
	{
		DataSourceColumnDT dataSourceColumnDT = null;
		Iterator anIterator = null;
		ArrayList<Object> dataSourceColumnList = new ArrayList<Object> ();

		if(DataSourceColumns != null)
		{
			/**
			 * Updates Data Source Columns
			 */
			try
			{
				logger.debug("Updating DataSourceColumns");
				for(anIterator = DataSourceColumns.iterator(); anIterator.hasNext(); )
				{
					dataSourceColumnDT = (DataSourceColumnDT)anIterator.next();
					if(dataSourceColumnDT == null)
					throw new NEDSSSystemException("Error: Empty DataSourceColumn collection");

					if(dataSourceColumnDT.isItNew())
					{
						insertDataSourceColumn(dataSourceColumnDT.getDataSourceUid().longValue(), dataSourceColumnDT);
					}
					if(dataSourceColumnDT.isItDirty())
					{
						dataSourceColumnDT =  updateItem(dataSourceColumnDT);
						dataSourceColumnList.add(dataSourceColumnDT);
					}
					if(dataSourceColumnDT.isItDelete())
					{
						removeDataSourceColumn(dataSourceColumnDT.getColumnUid().longValue());
					}
                }
            }
			catch(Exception ex)
			{
				logger.fatal("Exception = "+ex.getMessage(), ex);
				throw new NEDSSSystemException("Exception while updating " +
					"DataSourceColumn, \n" + ex.toString() + " \n" + ex.getMessage());
			}
		}
		return dataSourceColumnList;
	}//end of updating person ethnic group table


	/**
	 * This is the method that updates the existing DataSourceColumnDT object if it is set to dirty(IsItDirty).
	 */
	private DataSourceColumnDT updateItem(DataSourceColumnDT dataSourceColumnDT) throws NEDSSSystemException
	{
		Connection dbConnection = null;PreparedStatement preparedStmt = null;
		int resultCount = 0;

		try
		{
			//Updates DataSourceColumnDT table
			if (dataSourceColumnDT != null)
			{
				dbConnection = getConnection();
                                preparedStmt = dbConnection.prepareStatement(ReportConstants.UPDATE_DATASOURCECOLUMN);

				int i = 1;

				// first set non-PK on UPDATE statement
				if (dataSourceColumnDT.getColumnName() == null)
				throw new NEDSSSystemException("field columnName is null and the database tables don't allow it.");
				else
				preparedStmt.setString(i++,dataSourceColumnDT.getColumnName());
				preparedStmt.setString(i++,dataSourceColumnDT.getColumnTitle());
				preparedStmt.setString(i++,dataSourceColumnDT.getColumnTypeCode());
				if (dataSourceColumnDT.getDataSourceUid() == null)
				throw new NEDSSSystemException("field dataSourceUid is null and the database tables don't allow it.");
				else
				preparedStmt.setLong(i++,dataSourceColumnDT.getDataSourceUid().longValue());
				preparedStmt.setString(i++,dataSourceColumnDT.getDescTxt());
				preparedStmt.setString(i++,dataSourceColumnDT.getDisplayable());
				if (dataSourceColumnDT.getEffectiveFromTime() == null)
						preparedStmt.setNull(i++, Types.TIMESTAMP);
				else
				preparedStmt.setTimestamp(i++,dataSourceColumnDT.getEffectiveFromTime());
				if (dataSourceColumnDT.getEffectiveToTime() == null)
						preparedStmt.setNull(i++, Types.TIMESTAMP);
				else
				preparedStmt.setTimestamp(i++,dataSourceColumnDT.getEffectiveToTime());
				preparedStmt.setString(i++,dataSourceColumnDT.getFilterable());
				preparedStmt.setString(i++,dataSourceColumnDT.getStatusCd());
				if (dataSourceColumnDT.getStatusTime() == null)
						preparedStmt.setNull(i++, Types.TIMESTAMP);
				else
				preparedStmt.setTimestamp(i++,dataSourceColumnDT.getStatusTime());
				if(dataSourceColumnDT.getColumnMaxLen() == null)
					   preparedStmt.setNull(i++, Types.INTEGER);
				else
				preparedStmt.setInt(i++,dataSourceColumnDT.getColumnMaxLen().intValue());

				// next set PK items on UPDATE's WHERE clause
				if (dataSourceColumnDT.getColumnUid() == null)
				throw new NEDSSSystemException("field columnUid is null and the database tables don't allow it.");
				else
				preparedStmt.setLong(i++,dataSourceColumnDT.getColumnUid().longValue());


				resultCount = preparedStmt.executeUpdate();

				if ( resultCount != 1 )
				{
					throw new NEDSSSystemException("Error: none or more than one datasourcecolumn updated at a time, " + "resultCount = " + resultCount);
				}
			}
		}
		catch(SQLException sex)
		{
			logger.fatal("Exception = "+sex.getMessage(), sex);
			throw new NEDSSSystemException("SQLException while updating " +
					"datasourcecolumn into DATASOURCECOLUMN_TABLE: \n" + sex.toString() +
					" \n" + sex.getMessage());
		}
		finally
		{
			//Cleans up resources
       		closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
		return dataSourceColumnDT;
	}

	/**
	 * For a given dataSourceUID, this method returns a collection of DataSourceColumnDTs tied to that particular dataSourceUID.
	 */
	private Collection<Object>  selectDataSourceColumns (long dataSourceUID) throws NEDSSSystemException
	{
		logger.debug("DataSourceColumnDAOImpl - selectDataSourceColumns dataSourceUID = " + dataSourceUID);
		Connection dbConnection = null;
                PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		ResultSetMetaData resultSetMetaData = null;
		DataSourceColumnDT dataSourceColumnDT = new DataSourceColumnDT();
		ResultSetUtils resultSetUtils = new ResultSetUtils();
		ArrayList<Object> pnList = new ArrayList<Object> ();
		ArrayList<Object> reSetList = new ArrayList<Object> ();


		try
		{
			dbConnection = getConnection();
                        preparedStmt = dbConnection.prepareStatement(ReportConstants.SELECT_DATASOURCECOLUMN);
			preparedStmt.setLong(1, dataSourceUID);
			resultSet = preparedStmt.executeQuery();

			resultSetMetaData = resultSet.getMetaData();

			pnList = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData, dataSourceColumnDT.getClass(), pnList);

			for(Iterator<Object> anIterator = pnList.iterator(); anIterator.hasNext(); )
			{
				DataSourceColumnDT reSetName = (DataSourceColumnDT)anIterator.next();
				reSetName.setItNew(false);
				reSetName.setItDirty(false);
				reSetList.add(reSetName);
			}
		}
		catch(SQLException sex)
		{
			logger.fatal("Exception = "+sex.getMessage(), sex);
			throw new NEDSSSystemException("SQLException while selecting " +
							"dataSourceColumn vo; id = " + datasourcecolumnUID + " :\n" + sex.getMessage());
		}
		catch(Exception ex)
		{
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException("Exception while selecting " +
							"dataSourceColumn vo; id = " + datasourcecolumnUID + " :\n" + ex.getMessage());
		}
		finally
		{
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
		logger.debug("returning dataSourceColumn DT object");
		return reSetList;
	}// end selectDataSourceColumns        ------------------------------------------


	/**
	 *This method removes the DataSourceColumn objects tied to the given dataSourceUID.
	 *First it finds the DataSourceColumns attached to that dataSourceUID(by using selectDataSourceColumns method).
	 *Then delete the entry from database by calling another method called removeDataSourceColumn.
	 */
	private void removeDataSourceColumns(long dataSourceUID)throws NEDSSSystemException
	{
		ArrayList<Object> dataSourceColumns = (ArrayList<Object> )selectDataSourceColumns(dataSourceUID);
		Iterator anIterator = null;
		try
		{
			/**
			 * Deletes the dataSourceColumns
			 */
			for(anIterator = dataSourceColumns.iterator(); anIterator.hasNext();)
			{
				DataSourceColumnDT dataSourceColumnDT = (DataSourceColumnDT)anIterator.next();
				if(dataSourceColumnDT!= null)
				{
					removeDataSourceColumn(dataSourceColumnDT.getColumnUid().longValue());
				}
			}
		}
		catch(NEDSSSystemException nsex)
		{
			logger.fatal("Exception = "+nsex.getMessage(), nsex);
			throw new NEDSSSystemException("SQLException while obtaining database connection " +
							"for deleting datasourcecolumn " + nsex.getMessage());
		}
	}

	/**
	 * Actually removes the dataSourceColumnDT object from the database.
	 */

	private void removeDataSourceColumn(long datasourcecolumnUID) throws  NEDSSSystemException
	{
		Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
		int resultCount = 0;

		/**
		 * Deletes datasourcecolumns
		 */
		try
		{
		  dbConnection = getConnection();
          preparedStmt = dbConnection.prepareStatement(ReportConstants.DELETE_DATASOURCECOLUMN);
		  preparedStmt.setLong(1, datasourcecolumnUID);
		  resultCount = preparedStmt.executeUpdate();

		if (resultCount != 1)
		{
			throw new NEDSSSystemException
			("Error: cannot delete datasourcecolumn from DATASOURCECOLUMN_TABLE!! resultCount = " +
			 resultCount);
			}
		}
		catch(SQLException sex)
		{
			logger.fatal("Exception = "+sex.getMessage(), sex);
			throw new NEDSSSystemException("SQLException while removing " +
						"datasourcecolumn; id = " + datasourcecolumnUID + " :\n" + sex.getMessage());
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

	        DataSourceColumnDAOImpl dataSourceDaoImpl = new DataSourceColumnDAOImpl();
	        DataSourceColumnDT dataSourceColumnDT = new DataSourceColumnDT();
		//create Method
		dataSourceColumnDT.setColumnName("test");
		dataSourceColumnDT.setColumnTitle("test");
		dataSourceColumnDT.setColumnTypeCode("test");
		dataSourceColumnDT.setDataSourceUid(new Long(470003432));
		dataSourceColumnDT.setDisplayable("test");
		//dataSourceDaoImpl.create(dataSourceColumnDT);


	    //To test find Method
            long uid = 470003434;
			//dataSourceDaoImpl.findByPrimaryKey(uid);
	    //To Test set(change in values)
		dataSourceColumnDT.setColumnName("a");
		dataSourceColumnDT.setColumnTypeCode("b");
		dataSourceColumnDT.setDescTxt("test");
		dataSourceColumnDT.setDataSourceUid(new Long(470003432));
		dataSourceColumnDT.setColumnUid(new Long(470003437));
		dataSourceColumnDT.setColumnName("test123");
		Collection<Object>  coll = new ArrayList<Object> ();
		coll.add(dataSourceColumnDT);

		dataSourceDaoImpl.store(coll);


	    } catch(Exception e){}
	}

*/

}
