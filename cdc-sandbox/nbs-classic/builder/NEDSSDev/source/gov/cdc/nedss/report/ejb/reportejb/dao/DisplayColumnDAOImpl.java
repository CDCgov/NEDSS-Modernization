// *** Generated Source File ***
// Portions Copyright (c) 1996-2001, SilverStream Software, Inc., All Rights Reserved



/**
* Name:		    display_columnDAOImpl.java
* Description:	This is the implementation of NEDSSDAOInterface for the
*               display_column value object in the display_column entity bean.
*               This class encapsulates all the JDBC calls made by the display_columnEJB
*               for a display_column object. Actual logic of
*               inserting/reading/updating/deleting the data in relational
*               database tables to mirror the state of display_columnEJB is
*               implemented here.
* Copyright:	Copyright (c) 2001
* Company: 	    Computer Sciences Corporation
* @author	    Pradeep Sharma
* @version	    1.0
*/


package gov.cdc.nedss.report.ejb.reportejb.dao;

import gov.cdc.nedss.entity.sqlscript.NEDSSSqlQuery;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.report.dt.DataSourceColumnDT;
import gov.cdc.nedss.report.dt.DisplayColumnDT;
import gov.cdc.nedss.report.dt.ReportSortColumnDT;
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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DisplayColumnDAOImpl extends BMPBase
{

	static final LogUtils logger = new LogUtils((DisplayColumnDAOImpl.class).getName());
	private static final String ENTITY_UID = "ENTITY_UID";


	private long displaycolumnUID = -1;
	
	private long reportColumnSortUID = -1;

	public DisplayColumnDAOImpl()
		throws NEDSSDAOSysException, NEDSSSystemException
	{

	}

	/**
	 * create method for displayColumns based on given reportUID and given list of displayColumnList
	 */

	 public List<Object> create(long reportUID, List<Object> disColumnList ) throws NEDSSDAOSysException, NEDSSSystemException
	 {

	 	//insertDispColumnListColl(reportUID, disColumnList);
		List<Object> displayColumns = insertDispColumnListColl(reportUID, disColumnList);

		List<Object> list1 = disColumnList;
		DisplayColumnDT displayColumnDT= new DisplayColumnDT();
		Iterator<Object> anIterator = null;
		for(anIterator = list1.iterator(); anIterator.hasNext(); )
	    {
	        displayColumnDT = (DisplayColumnDT)anIterator.next();
	    }
		return displayColumns;
	 }

	/**
	 * store method for displayColumns which update given list of displayColumnList. In this update
	 * method the list may contain DisplayColumnsDT's with flag set to new, delete and dirty.
	 * The List<Object> is updated and returned according to the flag setting(delete removes the record from the database,
	 * new creates another one and diry just updates it).
	 */

	public List<Object> store(List<Object> disColumnList )
		throws NEDSSDAOSysException, NEDSSSystemException
	{
	    List<Object> displayColumns = updateDisColumnListColl(disColumnList);
		return displayColumns;
	}

	/**
	 * Remove method for displayColumns based on given reportUID that removes all displayColumns linked
	 * to that reportUID
	 */

	public void remove(long reportUID)
		throws NEDSSDAOSysException, NEDSSSystemException
	{
	    removeDisplayColumns(reportUID);
	}

	/**
	 * Loads the displayColumns based on given reportUID
	 */

    public List<Object> loadDisplayColumns(long reportUID) throws NEDSSDAOSysException,
		 NEDSSSystemException
    {
        List<Object> disColumnList = new ArrayList<Object> ();
		disColumnList = selectDisColumn(reportUID);
        return disColumnList;
    }

	/**
	 * Finder method for displayColumns based on given displaycolumnUID
	 */

    public Long findByPrimaryKey(long displaycolumnUID)
    	throws NEDSSDAOSysException, NEDSSSystemException
    {
        if (itemExists(displaycolumnUID))
            return (new Long(displaycolumnUID));
        else
            throw new NEDSSDAOSysException("No displaycolumn found for this primary key :" + displaycolumnUID);
    }

	/**
	 * Protected method to support if the DisplayColumnDT exists for a given reportUID.
	 */


    protected boolean itemExists (long displaycolumnUID) throws NEDSSDAOSysException,
            NEDSSSystemException
    {
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        boolean returnValue = false;


        try
        {
            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(ReportConstants.DC_SELECT_DISPLAYCOLUMN_UID);
            preparedStmt.setLong(1, displaycolumnUID);
            resultSet = preparedStmt.executeQuery();

            if (!resultSet.next())
            {
                returnValue = false;
            }
            else
            {
                displaycolumnUID = resultSet.getLong(1);
                returnValue = true;
            }
        }
        catch(SQLException sex)
        {
            sex.printStackTrace();
            throw new NEDSSDAOSysException("SQLException while checking for an"
                            + " existing displaycolumn's uid in displaycolumn table-&gt; " + displaycolumnUID + " :\n" + sex.getMessage());
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            throw new NEDSSDAOSysException("Exception while checking for an"
                            + " existing displaycolumn's uid in displaycolumn table-&gt; " +
                            displaycolumnUID + " :\n" + ex.getMessage());
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
	 * Inserts a list of DisplayColumnDT's for a given reportUID and returns it to the public method(create).
	 */
    private List<Object> insertDispColumnListColl(long reportUID, List<Object> disColumnList)
                throws NEDSSSystemException
    {
        /**
         * Starts inserting a new displaycolumn
         */
		List<Object> displayColumnList =  new ArrayList<Object> ();
		Iterator<Object> anIterator = null;
		DisplayColumnDT displayColumnDTResult = null;

		for(anIterator = disColumnList.iterator(); anIterator.hasNext();)
		{
			DisplayColumnDT displayColumnDT = (DisplayColumnDT) anIterator.next();
			if(displayColumnDT!= null)
			{
				displayColumnDTResult = insertDispColumn(reportUID,displayColumnDT);
				displayColumnList.add(displayColumnDTResult);
			}
		}
		return displayColumnList;
    }

/**
 * Takes a DisplayColumnDT from the list from insertDispColumnListColl and insert the record into
 * the database. This method is also called from the updateDisColumnList method when the flag for a
 * DisplayColumnDT is set to  true.
 */

private DisplayColumnDT insertDispColumn(long reportUID, DisplayColumnDT displayColumnDT)
                throws NEDSSSystemException
    {
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        ResultSet resultSet = null;
        String localUID = null;
        UidGeneratorHelper uidGen = null;
        int resultCount = 0;
	try
	{
		uidGen = new UidGeneratorHelper();
		displaycolumnUID = uidGen.getNbsIDLong(UidClassCodes.NBS_CLASS_CODE).longValue();
		dbConnection = getConnection();
                preparedStmt = dbConnection.prepareStatement(NEDSSSqlQuery.INSERT_ENTITY);
        	int i = 1;
		preparedStmt.setLong(i++, displaycolumnUID);
		preparedStmt.setString(i++, NEDSSConstants.DISPLAYCOLUMN);
		resultCount = preparedStmt.executeUpdate();
	}
	catch(SQLException sex)
	{
		sex.printStackTrace();
		throw new NEDSSSystemException("SQLException while inserting " +
					"uid for DISPLAY_COLUMN_TABLE: \n" + sex.toString() + " \n" + sex.getMessage());
	}
	catch(Exception ex)
	{
		ex.printStackTrace();
		throw new NEDSSSystemException("Exception while inserting " +
				"uid for DISPLAY_COLUMN_TABLE: \n" + ex.toString() + " \n" + ex.getMessage());
	}
        finally
        {
              closeStatement(preparedStmt);
              releaseConnection(dbConnection);

        }
	try
	{
		if (displaycolumnUID < 1)
		{
			throw new NEDSSSystemException(
					  "Error in reading new entity uid, entity uid = " + displaycolumnUID);
		}
			 // insert into DISPLAYCOLUMN_TABLE
		dbConnection = getConnection();
                preparedStmt = dbConnection.prepareStatement(ReportConstants.DC_INSERT_DISPLAYCOLUMN);
		int i = 1;
		// Set auto generated PK field
	        preparedStmt.setLong(i++,displaycolumnUID);
		displayColumnDT.setReportUid(new Long(reportUID));
		// Set all non generated fields
		 if (displayColumnDT.getColumnUid() == null)
			 throw new NEDSSSystemException("field columnUid is null and the database tables don't allow it.");
		else
			 preparedStmt.setLong(i++,displayColumnDT.getColumnUid().longValue());
		if (displayColumnDT.getDataSourceUid() == null)
			 throw new NEDSSSystemException("field dataSourceUid is null and the database tables don't allow it.");
		else
			 preparedStmt.setLong(i++,displayColumnDT.getDataSourceUid().longValue());
		if (displayColumnDT.getReportUid() == null)
			 throw new NEDSSSystemException("field reportUid is null and the database tables don't allow it.");
		else
			 preparedStmt.setLong(i++,displayColumnDT.getReportUid().longValue());
		if (displayColumnDT.getSequenceNbr() == null)
			 throw new NEDSSSystemException("field sequenceNbr is null and the database tables don't allow it.");
		else
			 preparedStmt.setInt(i++,displayColumnDT.getSequenceNbr().intValue());
		preparedStmt.setString(i++,displayColumnDT.getStatusCd());

		resultCount = preparedStmt.executeUpdate();
		displayColumnDT.setDisplayColumnUid(new Long(displaycolumnUID));
		displayColumnDT.setItNew(false);
		displayColumnDT.setItDelete(false);
		displayColumnDT.setItDirty(false);
		if(displayColumnDT.getTheReportSortColumnDT()!=null){
			insertSortColumnOrder(displayColumnDT);
		}
		logger.debug("done insert displaycolumn! displaycolumnUID = " + displayColumnDT.getDisplayColumnUid());

		}
		catch(SQLException sex)
		{
			sex.printStackTrace();
			throw new NEDSSSystemException("SQLException while inserting " +
					"displaycolumn into DISPLAYCOLUMN_TABLE: \n" + sex.toString() +
					" \n" + sex.getMessage());
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw new NEDSSSystemException("Error while inserting into DISPLAYCOLUMN_TABLE, id = " + displaycolumnUID);
		}

              finally
              {
                closeResultSet(resultSet);
                closeStatement(preparedStmt);
                releaseConnection(dbConnection);
              }

	return displayColumnDT;
    }//end of inserting displaycolumn

/**
 * Takes a DisplayColumnDT from the list from insertDispColumnListColl and insert the record into
 * the database. This method is also called from the updateDisColumnList method when the flag for a
 * DisplayColumnDT is set to  true.
 */

	private DisplayColumnDT insertSortColumnOrder(
			DisplayColumnDT displayColumnDT) throws NEDSSSystemException {
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		UidGeneratorHelper uidGen = null;
		int resultCount = 0;
		try {
			uidGen = new UidGeneratorHelper();
			reportColumnSortUID = uidGen.getNbsIDLong(
					UidClassCodes.NBS_CLASS_CODE).longValue();
			dbConnection = getConnection();
			preparedStmt = dbConnection
					.prepareStatement(NEDSSSqlQuery.INSERT_ENTITY);
			int i = 1;
			preparedStmt.setLong(i++, reportColumnSortUID);
			preparedStmt.setString(i++, NEDSSConstants.SORTCOLUMN);
			resultCount = preparedStmt.executeUpdate();
		} catch (SQLException sex) {
			sex.printStackTrace();
			throw new NEDSSSystemException("SQLException while inserting "
					+ "uid for REPORT_SORT_COLUMN  table: \n" + sex.toString()
					+ " \n" + sex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new NEDSSSystemException("Exception while inserting "
					+ "uid for REPORT_SORT_COLUMN table: \n" + ex.toString()
					+ " \n" + ex.getMessage());
		} finally {
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);

		}
		try {
			if (reportColumnSortUID < 1) {
				throw new NEDSSSystemException(
						"Error in reading new entity uid, entity uid = "
								+ displaycolumnUID);
			}
			// insert into SORTCOLUMN_TABLE
			dbConnection = getConnection();
			preparedStmt = dbConnection
					.prepareStatement(ReportConstants.INSERT_SORTCOLUMN_ORDER);
			int i = 1;
			ReportSortColumnDT rscDT = displayColumnDT
					.getTheReportSortColumnDT();
			rscDT.setReportUid(displayColumnDT.getReportUid());
			rscDT.setColumnUid(displayColumnDT.getColumnUid());
			// Set auto generated PK field
			preparedStmt.setLong(i++, reportColumnSortUID);
			// Set all non generated fields
			if (rscDT.getReportSortOrderCode() == null)
				throw new NEDSSSystemException("field sort order code is null");
			else
				preparedStmt.setString(i++, rscDT.getReportSortOrderCode());
			preparedStmt.setInt(i++, rscDT.getReportSortSequenceNum()
					.intValue());
			if (rscDT.getDataSourceUid() == null)
				throw new NEDSSSystemException(
						"REPORT_SORT_ORDER field dataSourceUid is null and the database tables don't allow it.");
			else
				preparedStmt.setLong(i++, rscDT.getDataSourceUid().longValue());
			if (rscDT.getReportUid() == null)
				throw new NEDSSSystemException(
						"field reportUid is null and the database tables don't allow it.");
			else
				preparedStmt.setLong(i++, rscDT.getReportUid()
						.longValue());
			preparedStmt.setLong(i++, rscDT.getColumnUid().longValue());
			preparedStmt.setString(i++, rscDT.getStatusCd());
			preparedStmt.setTimestamp(i++, rscDT.getStatusTime());

			resultCount = preparedStmt.executeUpdate();
			rscDT.setReportSortColumnUid(new Long(reportColumnSortUID));
			rscDT.setItNew(false);
			rscDT.setItDelete(false);
			rscDT.setItDirty(false);
			displayColumnDT.setTheReportSortColumnDT(rscDT);
			logger.debug("done insert reportsortcolumn! reportsortcolumnUID = "
					+ rscDT.getReportSortColumnUid());

		} catch (SQLException sex) {
			sex.printStackTrace();
			throw new NEDSSSystemException("SQLException while inserting "
					+ "displaycolumn into DISPLAYCOLUMN_TABLE: \n"
					+ sex.toString() + " \n" + sex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new NEDSSSystemException(
					"Error while inserting into DISPLAYCOLUMN_TABLE, id = "
							+ displaycolumnUID);
		}

		finally {
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}

		return displayColumnDT;
	}// end of inserting displaycolumn

	/**
	 * This update method iterates through the list and check for the IsItNew(
	 * to insert new record), IsItDelete(to delete entry from the database)
	 * flags.
	 */

    private List<Object> updateDisColumnListColl(List<Object>disColumnList) throws NEDSSSystemException
    {
		Iterator<Object> anIterator = null;
		List<Object> displayColumnList = new ArrayList<Object> ();
		DisplayColumnDT displayColumnDTResult = null;

		try
		{
			 for(anIterator = disColumnList.iterator(); anIterator.hasNext();)
			 {
				DisplayColumnDT displayColumnDT = (DisplayColumnDT) anIterator.next();
				//Updates DisplayColumnDT table
				if (displayColumnDT != null)
				{
					if(displayColumnDT.isItDelete())
					{   
						if(displayColumnDT.getTheReportSortColumnDT()!=null)
							this.removeReportSortColumn(displayColumnDT.getTheReportSortColumnDT().getReportSortColumnUid().longValue());
						removeDisplayColumn(displayColumnDT.getDisplayColumnUid().longValue());
						
						logger.debug("The Remove was successful from the updateList's new method");
					}
					else if(displayColumnDT.isItNew())
					{
						displayColumnDTResult = insertDispColumn(displayColumnDT.getReportUid().longValue(), displayColumnDT);
						displayColumnList.add(displayColumnDTResult);
						logger.debug("The insert was successful from the updateList's new method");
					}
					else
					{
						displayColumnDTResult = updateDisColumn(displayColumnDT);
						displayColumnList.add(displayColumnDTResult);
						logger.debug("The update was successful from the updateList's new method");
					}
				}
			 }
		}
		catch(NEDSSSystemException nsex)
        {
            nsex.printStackTrace();
            throw new NEDSSSystemException("Error obtaining db connection " +
                "while updating displaycolumn table");
        }
		logger.debug("The size of displayColumnList is now:" + displayColumnList.size());
		return displayColumnList;
    }

	/**
	 * Updates the DisplayColumnDT that exists in the database.
	 */
    private DisplayColumnDT updateDisColumn(DisplayColumnDT displayColumnDT) throws NEDSSSystemException
    {
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        int resultCount = 0;

		try
		{
		  dbConnection = getConnection();
                  preparedStmt = dbConnection.prepareStatement(ReportConstants.DC_UPDATE_DISPLAYCOLUMN);

			int i = 1;

			// first set non-PK on UPDATE statement
			if (displayColumnDT.getColumnUid() == null)
				 throw new NEDSSSystemException("field columnUid is null and the database tables don't allow it.");
			else
				 preparedStmt.setLong(i++,displayColumnDT.getColumnUid().longValue());
			if (displayColumnDT.getDataSourceUid() == null)
				throw new NEDSSSystemException("field dataSourceUid is null and the database tables don't allow it.");
			else
				 preparedStmt.setLong(i++,displayColumnDT.getDataSourceUid().longValue());
			if (displayColumnDT.getReportUid() == null)
				throw new NEDSSSystemException("field reportUid is null and the database tables don't allow it.");
			else
				preparedStmt.setLong(i++,displayColumnDT.getReportUid().longValue());
			if (displayColumnDT.getSequenceNbr() == null)
				throw new NEDSSSystemException("field sequenceNbr is null and the database tables don't allow it.");
			else
				preparedStmt.setInt(i++,displayColumnDT.getSequenceNbr().intValue());
			preparedStmt.setString(i++,displayColumnDT.getStatusCd());

			// next set PK items on UPDATE's WHERE clause
			if (displayColumnDT.getDisplayColumnUid() == null)
				throw new NEDSSSystemException("field displayColumnUid is null and the database tables don't allow it.");
			else
				preparedStmt.setLong(i++,displayColumnDT.getDisplayColumnUid().longValue());
			 resultCount = preparedStmt.executeUpdate();
			 if ( resultCount != 1 )
			{
				throw new NEDSSSystemException("Error: none or more than one displaycolumn updated at a time, " + "resultCount = " + resultCount);
			}
		}
		catch(SQLException sex)
		{
			sex.printStackTrace();
			throw new NEDSSSystemException("SQLException while updating " +
					"displaycolumn into DISPLAYCOLUMN_TABLE: \n" + sex.toString() +
					" \n" + sex.getMessage());
		}
		finally
		{
	          //Cleans up resources
                  closeStatement(preparedStmt);
                  releaseConnection(dbConnection);
		}
		if (displayColumnDT.getTheReportSortColumnDT() != null)
			return updateReportSortColumn(displayColumnDT);
		else
			return displayColumnDT;
    }
    
	/**
	 * Updates the DisplayColumnDT that exists in the database.
	 */
    private DisplayColumnDT updateReportSortColumn(DisplayColumnDT displayColumnDT) throws NEDSSSystemException
    {
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        int resultCount = 0;

		try
		{
		  dbConnection = getConnection();
                  preparedStmt = dbConnection.prepareStatement(ReportConstants.UPDATE_SORTCOLUMN_ORDER);
			int i = 1;

			// first set non-PK on UPDATE statement
			
			ReportSortColumnDT rscDT = displayColumnDT.getTheReportSortColumnDT();
			preparedStmt.setString(i++,rscDT.getReportSortOrderCode());
			preparedStmt.setInt(i++,rscDT.getReportSortSequenceNum().intValue());
			if (rscDT.getDataSourceUid() == null)
				throw new NEDSSSystemException("field dataSourceUid is null and the database tables don't allow it.");
			else
				 preparedStmt.setLong(i++,rscDT.getDataSourceUid().longValue());
			if (rscDT.getReportUid() == null)
				throw new NEDSSSystemException("field reportUid is null and the database tables don't allow it.");
			else
				preparedStmt.setLong(i++,displayColumnDT.getReportUid().longValue());
			if (rscDT.getColumnUid() == null)
				 throw new NEDSSSystemException("field columnUid is null and the database tables don't allow it.");
			else
				 preparedStmt.setLong(i++,rscDT.getColumnUid().longValue());

			preparedStmt.setString(i++,rscDT.getStatusCd());
			preparedStmt.setTimestamp(i++,rscDT.getStatusTime());

			// next set PK items on UPDATE's WHERE clause
			if (rscDT.getReportSortColumnUid()== null)
				throw new NEDSSSystemException("field displayColumnUid is null and the database tables don't allow it.");
			else
				preparedStmt.setLong(i++,rscDT.getReportSortColumnUid().longValue());
			 resultCount = preparedStmt.executeUpdate();
			 if ( resultCount != 1 )
			{
				throw new NEDSSSystemException("Error: none or more than one displaycolumn updated at a time, " + "resultCount = " + resultCount);
			}
			 displayColumnDT.setTheReportSortColumnDT(rscDT);
		}
		catch(SQLException sex)
		{
			sex.printStackTrace();
			throw new NEDSSSystemException("SQLException while updating " +
					"displaycolumn into DISPLAYCOLUMN_TABLE: \n" + sex.toString() +
					" \n" + sex.getMessage());
		}
		finally
		{
	          //Cleans up resources
                  closeStatement(preparedStmt);
                  releaseConnection(dbConnection);
		}
		return displayColumnDT;
    }


	/**
	 * Based on a ReportUID, returns the List<Object> object consisting of DisplayColumnDT Objects.
	 */
     private List<Object> selectDisColumn(long reportUID) throws NEDSSSystemException
	{
		Connection dbConnection = null;
                PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
                ResultSetMetaData resultSetMetaData = null;
		DisplayColumnDT  displayColumnDT = new DisplayColumnDT();
		ArrayList<Object> pList = new ArrayList<Object> ();
		ResultSetUtils resultSetUtils = new ResultSetUtils();
                ArrayList<Object> reSetList = new ArrayList<Object> ();
		/**
		 * Selects the displayColumnDT's
		 */
	     try
            {
              dbConnection = getConnection();
              preparedStmt = dbConnection.prepareStatement(ReportConstants.DC_SELECT_DISPLAYCOLUMN);
              preparedStmt.setLong(1, reportUID);
              resultSet = preparedStmt.executeQuery();

              resultSetMetaData = resultSet.getMetaData();

              pList = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData, displayColumnDT.getClass(), pList);
		    logger.info("DisplayCOlumnDAOImpl:selectDisColumn : pList.size() is :"+ pList.size());

              for(Iterator<Object> anIterator = pList.iterator(); anIterator.hasNext(); )
              {
                  DisplayColumnDT displayColumnRes = (DisplayColumnDT)anIterator.next();
				DataSourceColumnDT dataSourceColumn = null;
		        try
			{
				dataSourceColumn = (DataSourceColumnDT) selectDataSourceColumn(displayColumnRes.getColumnUid().longValue());
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				throw new NEDSSSystemException("Exception while selecting " +
			        	"displaySurceColumn :\n" + ex.getMessage());
			}
			displayColumnRes.setTheDataSourceColumnDT(dataSourceColumn);
			displayColumnRes.setItNew(false);
                        displayColumnRes.setItDirty(false);
			reSetList.add(displayColumnRes);
			logger.info("DisplayCOlumnDAOImpl:selectDisColumn :The reSetList size is " + reSetList.size());
              }
          }
	  catch(SQLException sex)
          {
            sex.printStackTrace();
            throw new NEDSSSystemException("SQLException while selecting " +
                            "reportid = " + reportUID + " :\n" + sex.getMessage());
          }
          catch(Exception ex)
          {
            ex.printStackTrace();
            throw new NEDSSSystemException("Exception while selecting " +
                            "reportid = " + reportUID + " :\n" + ex.getMessage());
          }
          finally
          {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
          }

	  logger.debug("returning displayColumnDT object reSetList's Size: "+ reSetList.size());
	  if(reSetList!=null && reSetList.size()>0)
		  selectSortColumnOrder(reportUID,reSetList);
	  return reSetList;
        }
     
     /**
 	 * Based on a ReportUID, returns the List<Object> object consisting of DisplayColumnDT Objects.
 	 */
      private void selectSortColumnOrder(long reportUID, List<Object> displayColumnList) throws NEDSSSystemException
 	{
 		Connection dbConnection = null;
                 PreparedStatement preparedStmt = null;
 		ResultSet resultSet = null;
                 ResultSetMetaData resultSetMetaData = null;
 		ReportSortColumnDT  rscDT = new ReportSortColumnDT();
 		ArrayList<Object> pList = new ArrayList<Object> ();
 		ResultSetUtils resultSetUtils = new ResultSetUtils();
 		/**
 		 * Selects the displayColumnDT's
 		 */
 	     try
             {
               dbConnection = getConnection();
               preparedStmt = dbConnection.prepareStatement(ReportConstants.SELECT_SORTCOLUMN_ORDER);
               preparedStmt.setLong(1, reportUID);
               resultSet = preparedStmt.executeQuery();

               resultSetMetaData = resultSet.getMetaData();

               pList = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData, rscDT.getClass(), pList);
 		    logger.info("DisplayCOlumnDAOImpl:selectDisColumn : pList.size() is :"+ pList.size());
               if (pList != null && pList.size()>0) {
				for (Iterator<Object> anIterator = pList.iterator(); anIterator
						.hasNext();) {
					ReportSortColumnDT rsColumnDT = (ReportSortColumnDT) anIterator
							.next();
					for (Iterator<Object> ite = displayColumnList.iterator(); ite
							.hasNext();) {
						DisplayColumnDT dcDT = (DisplayColumnDT) ite.next();

						if (rsColumnDT.getColumnUid().longValue() == dcDT
								.getColumnUid().longValue())
							dcDT.setTheReportSortColumnDT(rsColumnDT);
					}

				}
			}
		}
 	  catch(SQLException sex)
           {
             sex.printStackTrace();
             throw new NEDSSSystemException("SQLException while selecting " +
                             "reportid = " + reportUID + " :\n" + sex.getMessage());
           }
           catch(Exception ex)
           {
             ex.printStackTrace();
             throw new NEDSSSystemException("Exception while selecting " +
                             "reportid = " + reportUID + " :\n" + ex.getMessage());
           }
           finally
           {
             closeResultSet(resultSet);
             closeStatement(preparedStmt);
             releaseConnection(dbConnection);
           }
         }

	/**
	 * Returns the DataSourceColumnDT Object based on a particular dataSourceColumnUID.
	 */

	 private DataSourceColumnDT selectDataSourceColumn(long dataSourceColumnUID) throws NEDSSSystemException
	 {
         DataSourceColumnDT dataSourceColumnDT = new DataSourceColumnDT();
         Connection dbConnection = null;
         PreparedStatement preparedStmt = null;
         ResultSet resultSet = null;
         ResultSetMetaData resultSetMetaData = null;
         ResultSetUtils resultSetUtils = new ResultSetUtils();
         ArrayList<Object> pList = new ArrayList<Object> ();


        /**
         * Selects datasourcecolumn from data_source_column table
         */
        try
        {
            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(ReportConstants.DC_SELECT_DATASOURCECOLUMN);
            preparedStmt.setLong(1, dataSourceColumnUID);
            resultSet = preparedStmt.executeQuery();
            resultSetMetaData = resultSet.getMetaData();
            pList = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData, dataSourceColumnDT.getClass(), pList);
            for(Iterator<Object> anIterator = pList.iterator(); anIterator.hasNext(); )
            {
                dataSourceColumnDT = (DataSourceColumnDT)anIterator.next();
                dataSourceColumnDT.setItNew(false);
                dataSourceColumnDT.setItDirty(false);
                dataSourceColumnDT.setItDelete(false);
            }

        }
        catch(SQLException sex)
        {
            sex.printStackTrace();
            throw new NEDSSSystemException("SQLException while selecting " +
                            "dataSourceColumn vo; id = " + dataSourceColumnUID + " :\n" + sex.getMessage());
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            throw new NEDSSSystemException("Exception while selecting " +
                            "dataSourceColumn vo; id = " + dataSourceColumnUID + " :\n" + ex.getMessage());
        }
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }

	logger.debug("returning dataSourceColumn DT object");
        return dataSourceColumnDT;
    }//end of selecting item


	private void removeDisplayColumns(long reportUID) throws NEDSSSystemException
	{
	    List<Object> displayColumns = selectDisColumn(reportUID);
		Iterator<Object> anIterator = null;
		long displayColumnUID = -1;
		try
		{
			/**
			 * Delete ReportFilter
			 */
			for(anIterator = displayColumns.iterator(); anIterator.hasNext();)
			{
			    logger.info("DisplayColumnDAOImpl:removeDisplayColumns : inside try and For loop");

				DisplayColumnDT displayColumnDT = (DisplayColumnDT) anIterator.next();
				if (displayColumnDT != null) {
					displayColumnUID = displayColumnDT.getDisplayColumnUid()
							.longValue();
					logger
							.debug("DisplayColumnDAOImpl:removeDisplayColumns : displayColumnUID:"
									+ displayColumnUID);
					displayColumnDT.setDisplayColumnUid(new Long(
							displayColumnUID));
					if (displayColumnDT.getTheReportSortColumnDT() != null)
						removeReportSortColumn(displayColumnDT
								.getTheReportSortColumnDT()
								.getReportSortColumnUid().longValue());
					removeDisplayColumn(displayColumnUID);
				}
			}
		}
        catch(Exception ex)
        {
           ex.printStackTrace();
            throw new NEDSSSystemException("Exception while getting hold of individual displayColumnDT object \n"
				+ ex.toString() + " \n" + ex.getMessage());
        }
	}
	
    private void removeReportSortColumn(long reportSortcolumnUID) throws NEDSSSystemException
    {
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        int resultCount = 0;


        /**
         * Deletes displaycolumns
         */
        try
        {
            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(ReportConstants.DELETE_SORTCOLUMN_ORDER);
            preparedStmt.setLong(1, reportSortcolumnUID);
            resultCount = preparedStmt.executeUpdate();
			logger.debug("Delete was successful for displaycolumnUID" + displaycolumnUID);

            if (resultCount != 1)
            {
                throw new NEDSSSystemException
                ("Error: cannot delete displaycolumn from REPORT_SORT_COLUMN_TABLE!! resultCount = " +
                 resultCount);
            }
        }
        catch(SQLException sex)
        {
            sex.printStackTrace();
            throw new NEDSSSystemException("SQLException while removing " +
                            "sortcolumn; id = " + reportSortcolumnUID + " :\n" + sex.getMessage());
        }
        finally
        {
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }

    }


    private void removeDisplayColumn(long displaycolumnUID) throws NEDSSSystemException
    {
        Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
        int resultCount = 0;


        /**
         * Deletes displaycolumns
         */
        try
        {
            dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(ReportConstants.DC_DELETE_DISPLAYCOLUMN);
            preparedStmt.setLong(1, displaycolumnUID);
            resultCount = preparedStmt.executeUpdate();
			logger.debug("Delete was successful for displaycolumnUID" + displaycolumnUID);

            if (resultCount != 1)
            {
                throw new NEDSSSystemException
                ("Error: cannot delete displaycolumn from DISPLAYCOLUMN_TABLE!! resultCount = " +
                 resultCount);
            }
        }
        catch(SQLException sex)
        {
            sex.printStackTrace();
            throw new NEDSSSystemException("SQLException while removing " +
                            "displaycolumn; id = " + displaycolumnUID + " :\n" + sex.getMessage());
        }
        finally
        {
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }

    }

}
