// *** Generated Source File ***
// Portions Copyright (c) 1996-2001, SilverStream Software, Inc., All Rights Reserved

package gov.cdc.nedss.report.ejb.reportejb.dao;

import gov.cdc.nedss.entity.sqlscript.NEDSSSqlQuery;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.report.dt.FilterCodeDT;
import gov.cdc.nedss.report.dt.FilterValueDT;
import gov.cdc.nedss.report.dt.ReportFilterDT;
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



public class ReportFilterDAOImpl extends BMPBase
{
	static final LogUtils logger = new LogUtils((ReportFilterDAOImpl.class).getName());
	private static final String ENTITY_UID = "ENTITY_UID";

	private long reportfilterUID = -1;
	private long reportfilterValidationUID = -1;
	long filtervalueUID = -1;

	public ReportFilterDAOImpl()
		throws NEDSSDAOSysException, NEDSSSystemException
	{

	}

	 public Collection<Object> createReportFilter(long reportUID, Collection<Object> coll) throws NEDSSDAOSysException, NEDSSSystemException
	 {
		 try{
			logger.debug("ReportFilterDAOImpl:create create method and reportUID is : " + reportUID);
			ArrayList<Object>  filterCollection  = (ArrayList<Object> )insertReportFilters(reportUID, coll);
			logger.debug("ReportFilterDAOImpl:Create completed create method and reportUID is : " + reportUID);
			return filterCollection;
		 }catch(NEDSSDAOSysException ex){
			 logger.fatal("NEDSSDAOSysException  = "+ex.getMessage(), ex);
			 throw new NEDSSDAOSysException(ex.toString());
		 }catch(Exception ex){
			 logger.fatal("Exception  = "+ex.getMessage(), ex);
			 throw new NEDSSSystemException(ex.toString());
		 }
	 }

	public Collection<Object> storeReportFilters(Collection<Object> reportFilters)
		throws NEDSSDAOSysException, NEDSSSystemException
	{
		try{
			ArrayList<Object>  reportFilter = (ArrayList<Object> )updateReportFilters( reportFilters);
			return reportFilter;
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
			removeReportFilters(reportUID);
		}catch(NEDSSDAOSysException ex){
			 logger.fatal("reportUID: "+reportUID+" NEDSSDAOSysException  = "+ex.getMessage(), ex);
			 throw new NEDSSDAOSysException(ex.toString());
		}catch(Exception ex){
			 logger.fatal("reportUID: "+reportUID+" Exception  = "+ex.getMessage(), ex);
			 throw new NEDSSSystemException(ex.toString());
		}
	}

	public Collection<Object> load(long reportUID) throws NEDSSDAOSysException,
	 NEDSSSystemException
	{
		try{
			logger.debug("ReportFilterDAOImpl:load and reportUID is " + reportUID);
			Collection<Object> reportFilerDTColl = selectReportFilter(reportUID);
			return reportFilerDTColl;
		}catch(NEDSSDAOSysException ex){
			 logger.fatal("reportUID: "+reportUID+" NEDSSDAOSysException  = "+ex.getMessage(), ex);
			 throw new NEDSSDAOSysException(ex.toString());
		}catch(Exception ex){
			 logger.fatal("reportUID: "+reportUID+" Exception  = "+ex.getMessage(), ex);
			 throw new NEDSSSystemException(ex.toString());
		}
	}

	public Long findByPrimaryKey(long reportfilterUID)
		throws NEDSSDAOSysException, NEDSSSystemException
	{
		try{
			if (reportExists(reportfilterUID))
				return (new Long(reportfilterUID));
			else
				throw new NEDSSDAOSysException("ReportFilterDAOImpl:findByPrimaryKey:- No reportfilter found for this primary key :" + reportfilterUID);
		}catch(NEDSSDAOSysException ex){
			 logger.fatal("reportfilterUID: "+reportfilterUID+" NEDSSDAOSysException  = "+ex.getMessage(), ex);
			 throw new NEDSSDAOSysException(ex.toString());
		}catch(Exception ex){
			 logger.fatal("reportfilterUID: "+reportfilterUID+" Exception  = "+ex.getMessage(), ex);
			 throw new NEDSSSystemException(ex.toString());
		}
	}


	protected boolean reportExists (long reportfilterUID) throws NEDSSDAOSysException,
					NEDSSSystemException
	{
		Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		boolean returnValue = false;

		try
		{
			dbConnection = getConnection();
                        preparedStmt = dbConnection.prepareStatement(ReportConstants.SELECT_REPORTFILTER_UID);
			preparedStmt.setLong(1, reportfilterUID);
			resultSet = preparedStmt.executeQuery();

			if (!resultSet.next())
			{
				returnValue = false;
			}
			else
			{
				reportfilterUID = resultSet.getLong(1);
				returnValue = true;
			}
		}
		catch(SQLException sex)
		{
			logger.fatal("reportfilterUID: "+reportfilterUID+" SQLException  = "+sex.getMessage(), sex);
			throw new NEDSSDAOSysException("SQLException while checking for an"
											+ " existing reportfilter's uid in reportfilter table-&gt; " + reportfilterUID + " :\n" + sex.getMessage());
		}
		catch(Exception ex)
		{
			logger.fatal("reportfilterUID: "+reportfilterUID+" Exception  = "+ex.getMessage(), ex);
			throw new NEDSSDAOSysException("Exception while checking for an"
								+ " existing reportfilter's uid in reportfilter table-&gt; " +
								reportfilterUID + " :\n" + ex.getMessage());
		}
		finally
		{
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
		return returnValue;
  }

	private Collection<Object> insertReportFilters(long reportUID, Collection<Object> reportFilter)
	{
		Collection<Object> reportFilters = new ArrayList<Object> ();
		Iterator<Object> anIterator = null;
		try
		{
			/**
			 * Inserts the ReportFilter
			 */
			for(anIterator = reportFilter.iterator(); anIterator.hasNext();)
			{
				logger.debug("ReportFilterDAOImpl:insertReportFilters  inside the inserReportFilters" +anIterator.hasNext());

				ReportFilterDT reportFilterDT = (ReportFilterDT) anIterator.next();
				reportFilterDT.setReportUid(new Long(reportUID));
				if(reportFilterDT != null)
				{
					reportFilterDT =  insertReportFilter(reportUID, reportFilterDT);
					insertReportFilterValidation(reportFilterDT);
					reportFilters.add(reportFilterDT);
				}
			}
		}
		catch(Exception ex)
		{
			logger.fatal("reportUID: "+reportUID+"	Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException("Exception while getting hold of individual ReportFilterDT object \n"
		+ ex.toString() + " \n" + ex.getMessage());
		}
		return reportFilters;
	}

	/**
	 * Inserts a record into REPORT_FILTER_VALIDATION, ENTITY table
	 * @param reportFilterDT
	 * @return
	 */
	private void insertReportFilterValidation(ReportFilterDT reportFilterDT) throws NEDSSSystemException {
		
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		UidGeneratorHelper uidGen = null;
		int resultCount = 0;
		
 		try
		{
			uidGen = new UidGeneratorHelper();
			reportfilterValidationUID = uidGen.getNbsIDLong(UidClassCodes.NBS_CLASS_CODE).longValue();
			dbConnection = getConnection();
            preparedStmt = dbConnection.prepareStatement(NEDSSSqlQuery.INSERT_ENTITY);

			int i = 1;
			preparedStmt.setLong(i++, reportfilterValidationUID);
			preparedStmt.setString(i++, NEDSSConstants.REPORTFILTER_VALIDATION);
			resultCount = preparedStmt.executeUpdate();
		}
		catch(SQLException sex)
		{
			logger.fatal("SQLException  = "+sex.getMessage(), sex);
			throw new NEDSSSystemException("SQLException while inserting " +
							"uid for REPORT_FILTER_VALIDATION TABLE: \n" + sex.toString() + " \n" + sex.getMessage());
		}
		catch(Exception ex)
		{
			logger.fatal("Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException("Exception while inserting " +
							"uid for REPORT_FILTER_VALIDATION TABLE: \n" + ex.toString() + " \n" + ex.getMessage());
		}	
		finally
		{
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
		
        //insert into REPORT_FILTER_VALIDATION
		if (reportfilterValidationUID < 1)
		{
			throw new NEDSSSystemException(
		  	"Error in reading new entity uid, entity uid = " + reportfilterValidationUID);
		}
		try {
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(ReportConstants.INSERT_REPORTFILTER_VALIDATION);
			int i = 1;
			preparedStmt.setLong(i++,reportfilterValidationUID);
			if (reportFilterDT.getReportFilterUid() == null)
				 throw new NEDSSSystemException("field reportFilterUid is null and the database tables don't allow it.");
			else
				 preparedStmt.setLong(i++,reportFilterDT.getReportFilterUid().longValue());
			//if Report_Filter_ind(validation for basic filters) is null, set it to "N" as default
			if (reportFilterDT.getReportFilterInd() == null)
				preparedStmt.setString(i++,"N");
			else
				 preparedStmt.setString(i++,reportFilterDT.getReportFilterInd());
			
			resultCount = preparedStmt.executeUpdate();

			if(resultCount != 1)
			{
				throw new NEDSSSystemException("ReportFilterDAOImpl:insertReportFilterValidation Error: cannot be inserted into REPORT_FILTER_VALIDATION table, " + "resultCount = " + resultCount);
			}			
			
		} catch (NEDSSSystemException e) {
			// TODO Auto-generated catch block
			logger.fatal("NEDSSSystemException  = "+e.getMessage(), e);
			throw new NEDSSSystemException(e.toString());
		} catch (SQLException e) {
			logger.fatal("SQLException  = "+e.getMessage(), e);
			throw new NEDSSSystemException("SQLException while inserting reportfilter into REPORT_FILTER_VALIDATION: \n" + e.toString() +" \n" + e.getMessage());			
		}		
		
	}
	
	
	
	private ReportFilterDT insertReportFilter(long reportUID, ReportFilterDT reportFilterDT)
							throws NEDSSSystemException
	{
		/**
		*
		* Starts inserting a new reportfilter
		*/
		Connection dbConnection = null;
                PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		String localUID = null;
		UidGeneratorHelper uidGen = null;
		int resultCount = 0;


		/**
		* Inserts reprtFilterUID into entity table for reportfilter table
		*/
 		try
		{
			uidGen = new UidGeneratorHelper();
			reportfilterUID = uidGen.getNbsIDLong(UidClassCodes.NBS_CLASS_CODE).longValue();

			dbConnection = getConnection();
                        preparedStmt = dbConnection.prepareStatement(NEDSSSqlQuery.INSERT_ENTITY);

			int i = 1;
			preparedStmt.setLong(i++, reportfilterUID);
			preparedStmt.setString(i++, NEDSSConstants.REPORTFILTER);
			resultCount = preparedStmt.executeUpdate();
		}
		catch(SQLException sex)
		{
			logger.fatal("SQLException  = "+sex.getMessage(), sex);
			throw new NEDSSSystemException("SQLException while inserting " +
							"uid for REPORT_FILTER_TABLE: \n" + sex.toString() + " \n" + sex.getMessage());
		}
		catch(Exception ex)
		{
			logger.fatal("Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException("Exception while inserting " +
							"uid for REPORT_FILTER_TABLE: \n" + ex.toString() + " \n" + ex.getMessage());
		}
        finally
        {
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
        }

		try
		{
			if (reportfilterUID < 1)
			{
				throw new NEDSSSystemException(
			  	"Error in reading new entity uid, entity uid = " + reportfilterUID);
			}

			 // insert into REPORTFILTER_TABLE

			dbConnection = getConnection();
                        preparedStmt = dbConnection.prepareStatement(ReportConstants.INSERT_REPORTFILTER);

			int i = 1;
			// Set auto generated PK field
			 preparedStmt.setLong(i++,reportfilterUID);

			// Set all non generated fields
			reportFilterDT.setReportUid(new Long(reportUID));
			if (reportFilterDT.getReportUid() == null)
				 throw new NEDSSSystemException("field reportUid is null and the database tables don't allow it.");
			else
				 preparedStmt.setLong(i++,reportFilterDT.getReportUid().longValue());
			if (reportFilterDT.getDataSourceUid() == null)
				 throw new NEDSSSystemException("field dataSourceUid is null and the database tables don't allow it.");
			else
				 preparedStmt.setLong(i++,reportFilterDT.getDataSourceUid().longValue());
			if (reportFilterDT.getFilterUid() == null)
				 throw new NEDSSSystemException("field filterUid is null and the database tables don't allow it.");
			else
				 preparedStmt.setLong(i++,reportFilterDT.getFilterUid().longValue());
			if (reportFilterDT.getStatusCd() == null)
				 preparedStmt.setString(i++, "A");
			else
			preparedStmt.setString(i++, reportFilterDT.getStatusCd());
			if (reportFilterDT.getMaxValueCnt() == null)
							preparedStmt.setNull(i++, Types.INTEGER);
			else
				preparedStmt.setInt(i++,reportFilterDT.getMaxValueCnt().intValue());
			if (reportFilterDT.getMinValueCnt() == null)
							preparedStmt.setNull(i++, Types.INTEGER);
			else
				 preparedStmt.setInt(i++,reportFilterDT.getMinValueCnt().intValue());
			if (reportFilterDT.getColumnUid() == null)
							preparedStmt.setNull(i++, Types.BIGINT);
			else
				 preparedStmt.setLong(i++,reportFilterDT.getColumnUid().longValue());


			resultCount = preparedStmt.executeUpdate();
			reportFilterDT.setItNew(false);
			reportFilterDT.setItDirty(false);
			reportFilterDT.setItDelete(false);
			reportFilterDT.setReportFilterUid(new Long(reportfilterUID));
			logger.debug("\n\n\n\ndone insert reportfilter! reportfilterUID = " + reportFilterDT.getReportFilterUid());
			if(resultCount != 1)
			{
				throw new NEDSSSystemException(
					  "ReportFilterDAOImpl:insertReportFilter Error: none or more than one reportFilterDT inserted at a time, " + "resultCount = " + resultCount);
			}
			else
			{
				if(reportFilterDT.getTheFilterValueDTCollection()!= null)
				{
					logger.debug("ReportFIlterDAOImpl:insertReportFilter reportfilterUID :" + reportfilterUID);
					insertFilterValues(reportfilterUID, reportFilterDT.getTheFilterValueDTCollection());
				}
			}
            //return reportFilterDT;
                }
		catch(SQLException sex)
		{
			logger.fatal("reportUID:"+reportUID+" SQLException = "+sex.getMessage(), sex);
			throw new NEDSSSystemException("SQLException while inserting " +
				"reportfilter into REPORTFILTER_TABLE: \n" + sex.toString() +
				" \n" + sex.getMessage());
		}
		catch(Exception ex)
		{
			logger.fatal("reportUID:"+reportUID+" Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException("Error while inserting into REPORTFILTER_TABLE, id = " + reportfilterUID);
		}
        finally
        {
            closeResultSet(resultSet);
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
         }
               
         return reportFilterDT;
	}//end of inserting reportfilter

	private Collection<Object> insertFilterValues( long reportFilterUID, Collection<Object> filterValueDTColl) throws NEDSSSystemException
	{
		Collection<Object> filterValues = new ArrayList<Object> ();
		FilterValueDT filtervalueDT = null;
		Iterator<Object> anIterator = null;
		if(filterValueDTColl != null)
		{
			/**
			* Insert FilterValues
			*/
			try
			{
				for(anIterator = filterValueDTColl.iterator(); anIterator.hasNext(); )
				{
					filtervalueDT = (FilterValueDT)anIterator.next();
					if(filtervalueDT != null)
					{
						filtervalueDT = insertFilterValue(reportFilterUID, filtervalueDT);
						filterValues.add(filtervalueDT);
					}
				}
			}
			catch(Exception ex)
			{
				logger.fatal("reportFilterUID:"+reportFilterUID+" Exception  = "+ex.getMessage(), ex);
				throw new NEDSSSystemException("Error while inserting into REPORT_FILTER_VALUE_TABLE, id = " + reportfilterUID);
			}
		}
		return filterValues;
	}

	private FilterValueDT insertFilterValue(long reportFilterUID, FilterValueDT filterValueDT) throws NEDSSSystemException
	{
		//long filtervalueUID = -1;
		Connection dbConnection = null;
                PreparedStatement preparedStmt = null;
		String localUID = null;
		UidGeneratorHelper uidGen = null;
		int resultCount = 0;


		/**
		* Inserts filter value uid into entity table for filtervalue table
		*/
		try
		{
			uidGen = new UidGeneratorHelper();
			filtervalueUID = uidGen.getNbsIDLong(UidClassCodes.NBS_CLASS_CODE).longValue();

			dbConnection = getConnection();
                        preparedStmt = dbConnection.prepareStatement(NEDSSSqlQuery.INSERT_ENTITY);

			int i = 1;
			preparedStmt.setLong(i++, filtervalueUID);
			preparedStmt.setString(i++, NEDSSConstants.FILTERVALUE);
			resultCount = preparedStmt.executeUpdate();
		}
		catch(SQLException sex)
		{
			logger.fatal("reportFilterUID: "+reportFilterUID+" SQLException  = "+sex.getMessage(), sex);
			throw new NEDSSSystemException("SQLException while inserting " +
					"uid for FILTER_VALUE_TABLE: \n" + sex.toString() + " \n" + sex.getMessage());
		}
		catch(Exception ex)
		{
			logger.fatal("reportFilterUID: "+reportFilterUID+" Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException("Exception while inserting " +
					"uid for FILTER_VALUE_TABLE: \n" + ex.toString() + " \n" + ex.getMessage());
		}
        finally
        {
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }

		try
		{
			if (filtervalueUID < 1)
			{
				throw new NEDSSSystemException(
					  "Error in reading new entity uid, entity uid = " + filtervalueUID);
			}

			 // insert into FILTERVALUE_TABLE
			dbConnection = getConnection();
                        preparedStmt = dbConnection.prepareStatement(ReportConstants.INSERT_FILTERVALUE);

			int i = 1;
			// Set auto generated PK field
			 preparedStmt.setLong(i++,filtervalueUID);
			// Set all non generated fields
			filterValueDT.setReportFilterUid(new Long(reportFilterUID));
			if (filterValueDT.getReportFilterUid() == null)
				 throw new NEDSSSystemException("field reportFilterUid is null and the database tables don't allow it.");
			else
				 preparedStmt.setLong(i++,filterValueDT.getReportFilterUid().longValue());
			if(filterValueDT.getSequenceNbr() == null)
			    preparedStmt.setNull(i++, Types.INTEGER);
			else
				preparedStmt.setInt(i++, filterValueDT.getSequenceNbr().intValue());
			preparedStmt.setString(i++, filterValueDT.getValueType() );
			if(filterValueDT.getColumnUid() == null)
				preparedStmt.setNull(i++, Types.BIGINT);
			else
				preparedStmt.setLong(i++, filterValueDT.getColumnUid().longValue() );
			preparedStmt.setString(i++,filterValueDT.getFilterValueOperator());
			preparedStmt.setString(i++, filterValueDT.getValueTxt() );

			resultCount = preparedStmt.executeUpdate();
			filterValueDT.setValueUid(new Long(filtervalueUID));
			logger.debug("done insert filtervalue! filtervalueUID = " + filterValueDT.getValueUid());
			filterValueDT.setItDelete(false);
			filterValueDT.setItNew(false);
			filterValueDT.setItDirty(false);

			return filterValueDT;
		}
		catch(SQLException sex)
		{
			logger.fatal("reportFilterUID: "+reportFilterUID+" SQLException  = "+sex.getMessage(), sex);
			throw new NEDSSSystemException("SQLException while inserting " +
					"filtervalue into FILTERVALUE_TABLE: \n" + sex.toString() +
					" \n" + sex.getMessage());
		}
		catch(Exception ex)
		{
			logger.fatal("reportFilterUID: "+reportFilterUID+" Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException("Error while inserting into FILTERVALUE_TABLE, id = " + filtervalueUID);
		}

        finally
        {
          closeStatement(preparedStmt);
          releaseConnection(dbConnection);
        }
	}//end of inserting filtervalue

	private Collection<Object> updateReportFilters(Collection<Object> reportFilters) throws NEDSSSystemException
	{
		PreparedStatement preparedStmt = null;
		int resultCount = 0;
		Collection<Object> filterValues = new ArrayList<Object> ();
		Collection<Object> reportFiltersColl = new ArrayList<Object> ();
		Iterator<Object> anIterator = null;
		ReportFilterDT reportFilterDT =  null;
		logger.debug("\n\n\nreportFilters size is :" + reportFilters.size());
		for(anIterator = reportFilters.iterator(); anIterator.hasNext(); )
		{
			reportFilterDT = (ReportFilterDT)anIterator.next();
			try
			{
				logger.debug("\n\n\nreportFilterDT.IsItNew is :" + reportFilterDT.isItNew());
				logger.debug("\n\n\nreportFilterDT.IsItDelete is :" + reportFilterDT.isItDelete());
				logger.debug("\n\n\nreportFilterDT.IsItDirty is :" + reportFilterDT.isItDirty());
				if(reportFilterDT != null)
				{
					if(reportFilterDT.isItNew())
					{
						reportFilterDT = insertReportFilter(reportFilterDT.getReportUid().longValue(), reportFilterDT);
						logger.debug("\n\n\nreportFilters size in new is :" + reportFilters.size());
						reportFiltersColl.add(reportFilterDT);
					}
					if(reportFilterDT.isItDelete())
					{
						removeReportFilter(reportFilterDT);
					}
					if(reportFilterDT.isItDirty())
					{
						reportFilterDT = updateReportFilter(reportFilterDT);
						reportFiltersColl.add(reportFilterDT);
					}
				}
			}
			catch(Exception ex)
			{
				logger.fatal("Exception  = "+ex.getMessage(), ex);
				throw new NEDSSSystemException("SQLException while in upade for inserting/deleting " +
						"reportfilter into REPORTFILTER_TABLE: \n" + ex.toString() +
						" \n" + ex.getMessage());
			}
		}
		return reportFiltersColl;
	}

	private ReportFilterDT updateReportFilter(ReportFilterDT reportFilterDT) throws NEDSSSystemException, NEDSSSystemException
	{
		Connection dbConnection = null;
                PreparedStatement preparedStmt = null;
		int resultCount = 0;
		Collection<Object> filterValues = new ArrayList<Object> ();
		try
		{
		  if(reportFilterDT != null)
		  {
				logger.debug("\n\n\nrGot the reportFilter DT in update");
				dbConnection = getConnection();
                                preparedStmt = dbConnection.prepareStatement(ReportConstants.UPDATE_REPORTFILTER);

				int i = 1;
				// first set non-PK on UPDATE statement
				if (reportFilterDT.getReportUid() == null)
					 throw new NEDSSSystemException("field reportUid is null and the database tables don't allow it.");
				else
					 preparedStmt.setLong(i++,reportFilterDT.getReportUid().longValue());
				if (reportFilterDT.getDataSourceUid() == null)
					 throw new NEDSSSystemException("field dataSourceUid is null and the database tables don't allow it.");
				else
					 preparedStmt.setLong(i++,reportFilterDT.getDataSourceUid().longValue());
				if (reportFilterDT.getFilterUid() == null)
					 throw new NEDSSSystemException("field filterUid is null and the database tables don't allow it.");
				else
					 preparedStmt.setLong(i++,reportFilterDT.getFilterUid().longValue());
				if (reportFilterDT.getStatusCd() == null)
					 preparedStmt.setString(i++, "A");
				else
					 preparedStmt.setString(i++, reportFilterDT.getStatusCd());
				if (reportFilterDT.getMaxValueCnt() == null)
								preparedStmt.setNull(i++, Types.INTEGER);
				else
					 preparedStmt.setInt(i++,reportFilterDT.getMaxValueCnt().intValue());
				if (reportFilterDT.getMinValueCnt() == null)
								preparedStmt.setNull(i++, Types.INTEGER);
				else
					 preparedStmt.setInt(i++,reportFilterDT.getMinValueCnt().intValue());
				if (reportFilterDT.getColumnUid() == null)
								preparedStmt.setNull(i++, Types.BIGINT);
				else
					 preparedStmt.setLong(i++,reportFilterDT.getColumnUid().longValue());

				 // next set PK items on UPDATE's WHERE clause
				if (reportFilterDT.getReportFilterUid() == null)
					 throw new NEDSSSystemException("field reportFilterUid is null and the database tables don't allow it.");
				else
					 preparedStmt.setLong(i++,reportFilterDT.getReportFilterUid().longValue());
				resultCount = preparedStmt.executeUpdate();
				logger.debug("ReportFilterDAOImpl:UpdateReportFIlter and completed resultCount= " + resultCount);

				 if ( resultCount != 1 )
				 {
					 throw new NEDSSSystemException("Error: none or more than one reportfilter updated at a time, " + "resultCount = " + resultCount);
				 }
				 //reportFiltersColl.add(reportFilterDT);
				 if(reportFilterDT.getTheFilterCodeDT() == null)
				 {
					 throw new NEDSSSystemException("Error: FilterCodeDT cannot be null");
				 }
				 else
				 {
					if(reportFilterDT.getTheFilterValueDTCollection() != null)
					{
						filterValues = (ArrayList<Object> )updateFilterValues(reportFilterDT.getReportFilterUid().longValue(), reportFilterDT.getTheFilterValueDTCollection());
						reportFilterDT.setTheFilterValueDTCollection(filterValues);
					}
				 }
			}
		}
		catch(SQLException sex)
		{
			logger.fatal("SQLException  = "+sex.getMessage(), sex);
			throw new NEDSSSystemException("SQLException while updating " +
					"reportfilter into REPORTFILTER_TABLE: \n" + sex.toString() +
					" \n" + sex.getMessage());
		}
		finally
		{
				//Cleans up resources
                  closeStatement(preparedStmt);
                  releaseConnection(dbConnection);
		}
		return reportFilterDT;
    }


	private Collection<Object> updateFilterValues(long reportFilterUID, Collection<Object> filterValueDTColl) throws NEDSSSystemException
	{
		Collection<Object> filterValuesColl = new ArrayList<Object> ();
		FilterValueDT filtervalueDT = null;
		long filtervalueUID = -1;
		Iterator<Object> anIterator = null;
		if(filterValueDTColl != null)
		{
			/**
			 * Updates FilterValues
			 */
			try
			{
				for(anIterator = filterValueDTColl.iterator(); anIterator.hasNext(); )
				{
					filtervalueDT = (FilterValueDT)anIterator.next();
					logger.debug("ReportFilterDAOImpl:updateFilterValues and filterValueDTColl.size: " + filterValueDTColl.size());
					logger.debug("ReportFilterDAOImpl:updateFilterValue and filtervalueDT.>IsItDelete: " + filtervalueDT.isItDelete());
					logger.debug("ReportFilterDAOImpl:updateFilterValues and reportFilterDT IsITNew: " + filtervalueDT.isItNew());
					logger.debug("ReportFilterDAOImpl:updateFilterValues and reportFilterUID IsItDelete: " + filtervalueDT.isItDelete());
					if(filtervalueDT != null)
					{
						Long filtervalueUID1 = filtervalueDT.getValueUid();
						logger.debug("ReportFilterDAOImpl:updateFilterValues and filtervalueUID:" + filtervalueUID1);

						if(filtervalueDT.isItDelete())
						{
							removeFilterValue(filtervalueDT.getValueUid().longValue());
							logger.debug("\n\n\nReportFilterDAOImpl:updateFilterValues delete called");
						}
						else
						{
							if(filtervalueDT.isItNew())
						    {
						        filtervalueDT = insertFilterValue(reportFilterUID, filtervalueDT);
							    //filterValuesColl.add(filtervalueDT);
							    logger.debug("\n\n\nReportFilterDAOImpl:updateFilterValues new called");
						    }
						    else
							{                                      //(filtervalueDT.isItDirty())
						        filtervalueDT = updateFilterValue(filtervalueDT);
							    filterValuesColl.add(filtervalueDT);
							    logger.debug("\n\n\nReportFilterDAOImpl:updateFilterValues dirty called with ValueUId : " + filtervalueDT.getValueUid());
						    }
						}//end else
						logger.debug("ReportFilterDAOImpl:updateFilterValues and filterDT:" + filtervalueDT);
					}//end for
				}
			}
			catch(Exception ex)
			{
				logger.fatal("reportFilterUID: "+reportFilterUID+" Exception  = "+ex.getMessage(), ex);
				throw new NEDSSSystemException("Error while updating into REPORT_FILTER_VALUE_TABLE, id = " + filtervalueUID);
			}
		}
		return filterValuesColl;
	}
	private FilterValueDT updateFilterValue(FilterValueDT filterValueDT)throws NEDSSSystemException
	{
		Connection dbConnection = null;
        PreparedStatement preparedStmt = null;
		int resultCount = 0;


		try
		{
			//Updates FilterValueDT table
			if (filterValueDT != null)
			{

				dbConnection = getConnection();
                                preparedStmt = dbConnection.prepareStatement(ReportConstants.UPDATE_FILTERVALUE);

				int i = 1;
				 logger.debug("ReportFilterDAOImpl:updateFilterValue and valueUId is :" +filterValueDT.getValueUid());
				 // first set non-PK on UPDATE statement
				if (filterValueDT.getReportFilterUid() == null)
					 throw new NEDSSSystemException("field reportFilterUid is null and the database tables don't allow it.");
				else
					 preparedStmt.setLong(i++,filterValueDT.getReportFilterUid().longValue());
				if (filterValueDT.getSequenceNbr() == null)
					 preparedStmt.setNull(i++, Types.INTEGER);
				else
					 preparedStmt.setInt(i++,filterValueDT.getSequenceNbr().intValue());
				preparedStmt.setString(i++, filterValueDT.getValueType());
				if (filterValueDT.getColumnUid() == null)
					 preparedStmt.setLong(i++,Types.BIGINT);
				else
					 preparedStmt.setLong(i++,filterValueDT.getColumnUid().longValue());
				preparedStmt.setString(i++, filterValueDT.getFilterValueOperator());
				preparedStmt.setString(i++, filterValueDT.getValueTxt());
				if (filterValueDT.getValueUid() == null)
					 throw new NEDSSSystemException("field valueUid is null and the database tables don't allow it.");
				else
					 preparedStmt.setLong(i++,filterValueDT.getValueUid().longValue());
				resultCount = preparedStmt.executeUpdate();

				if ( resultCount != 1 )
				{
					throw new NEDSSSystemException("Error: none or more than one filtervalue updated at a time, " + "resultCount = " + resultCount);
				}
		    }
		}
		catch(SQLException sex)
		{
			logger.fatal("SQLException  = "+sex.getMessage(), sex);
			throw new NEDSSSystemException("SQLException while updating " +
				"filtervalue into FILTERVALUE_TABLE: \n" + sex.toString() +
				" \n" + sex.getMessage());
		}
		finally
		{
            closeStatement(preparedStmt);
            releaseConnection(dbConnection);
		}
		return filterValueDT;
	}

	private Collection<Object> selectReportFilter(long reportUID) throws NEDSSSystemException, NEDSSSystemException
	{
		logger.debug("ReportFilterDAOImpl:selectReportFilter reportUID : " + reportUID);
		ReportFilterDT reportFilterDT = new ReportFilterDT();
		Connection dbConnection = null;
                PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		ResultSetMetaData resultSetMetaData = null;
		ResultSetUtils resultSetUtils = new ResultSetUtils();
		ArrayList<Object>  pList = new ArrayList<Object> ();
		ArrayList<Object>  repFilterColl = new ArrayList<Object> ();


		/**
		 * Selects reportfilter from report_filter table
		 */
		try
		{
			dbConnection = getConnection();
                        preparedStmt = dbConnection.prepareStatement(ReportConstants.SELECT_REPORTFILTER);
			preparedStmt.setLong(1, reportUID);
			resultSet = preparedStmt.executeQuery();

			resultSetMetaData = resultSet.getMetaData();

			pList = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData, reportFilterDT.getClass(), pList);
			for(Iterator<Object> anIterator = pList.iterator(); anIterator.hasNext(); )
			{
				reportFilterDT = (ReportFilterDT)anIterator.next();
				FilterCodeDT  filterCodeDT = null;
				try
				{
					filterCodeDT = reportFilterDT.getTheFilterCodeDT();
					long filterCodeUID = reportFilterDT.getFilterUid().longValue();
					reportFilterDT.setFilterCodeDT(selectFilterCode(filterCodeUID));
				}
				catch(Exception ex)
				{
					logger.fatal("reportUID: "+reportUID+" Exception  = "+ex.getMessage(), ex);
					throw new NEDSSSystemException("Exception in filding ReportFilterDAOImpl select method" +
					" as FilterCode value cannot be null:\n" + ex.getMessage());
				}
				reportfilterUID = reportFilterDT.getReportFilterUid().longValue();
				ArrayList<Object>  al = (ArrayList<Object> ) selectFilterValues(reportfilterUID);
				if(al != null)
				{
					reportFilterDT.setTheFilterValueDTCollection(al);
				}
				reportFilterDT.setItNew(false);
				reportFilterDT.setItDirty(false);
				reportFilterDT.setItDelete(false);
				repFilterColl.add(reportFilterDT);
			}
		}
		catch(SQLException sex)
		{
			logger.fatal("reportUID: "+reportUID+" SQLException  = "+sex.getMessage(), sex);
			throw new NEDSSSystemException("SQLException while selecting " +
							"reportFilter vo; id = " + reportfilterUID + " :\n" + sex.getMessage());
		}
		catch(Exception ex)
		{
			logger.fatal("reportUID: "+reportUID+" Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException("Exception while selecting " +
							"reportFilter vo; id = " + reportfilterUID + " :\n" + ex.getMessage());
		}
		finally
		{
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
        }
		return repFilterColl;
	}//end of selecting report

	private FilterCodeDT selectFilterCode(long filtercodeUID) throws NEDSSSystemException
	{
		FilterCodeDT filterCodeDT = new FilterCodeDT();
		Connection dbConnection = null;
                PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		ResultSetMetaData resultSetMetaData = null;
		ResultSetUtils resultSetUtils = new ResultSetUtils();
		ArrayList<Object>  pList = new ArrayList<Object> ();


		/**
		 * Selects filtercode from filter_code table
		 */
		try
		{
			dbConnection = getConnection();
                        preparedStmt = dbConnection.prepareStatement(ReportConstants.RF_SELECT_FILTERCODE);
			preparedStmt.setLong(1, filtercodeUID);
			resultSet = preparedStmt.executeQuery();

			logger.debug("filterCodeDT object for: filtercodeUID = " + filtercodeUID);

			resultSetMetaData = resultSet.getMetaData();

			pList = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData, filterCodeDT.getClass(), pList);

			for(Iterator<Object> anIterator = pList.iterator(); anIterator.hasNext(); )
			{
				filterCodeDT = (FilterCodeDT)anIterator.next();
				filterCodeDT.setItNew(false);
				filterCodeDT.setItDirty(false);
				filterCodeDT.setItDelete(false);
			}
		}
		catch(SQLException sex)
		{
			logger.fatal("filtercodeUID: "+filtercodeUID+" SQLException  = "+sex.getMessage(), sex);
			throw new NEDSSSystemException("SQLException while selecting " +
							"filterCode vo; id = " + filtercodeUID + " :\n" + sex.getMessage());
		}
		catch(Exception ex)
		{
			logger.fatal("filtercodeUID: "+filtercodeUID+" Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException("Exception while selecting " +
							"filterCode vo; id = " + filtercodeUID + " :\n" + ex.getMessage());
		}
		finally
		{
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
		return filterCodeDT;
	}//end of selecting item

	private Collection<Object> selectFilterValues(long reportFilterUID) throws NEDSSSystemException
	{
		Connection dbConnection = null;
                PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		ResultSetMetaData resultSetMetaData = null;
		FilterValueDT  filterValueDT = new FilterValueDT();
		ArrayList<Object>  pList = new ArrayList<Object> ();
		ResultSetUtils resultSetUtils = new ResultSetUtils();
		ArrayList<Object>  reSetList = new ArrayList<Object> ();
		/**
		 * Selects the FilterValueDT's
		 */
		try
		{
			dbConnection = getConnection();
                        preparedStmt = dbConnection.prepareStatement(ReportConstants.SELECT_FILTERVALUE);
			preparedStmt.setLong(1, reportFilterUID);
			resultSet = preparedStmt.executeQuery();
			//logger.debug("filterValueDT object for: filtervalueUID = " + reportFilterUID + resultSet.getLong("reportFilterUid"));

			resultSetMetaData = resultSet.getMetaData();
			pList = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData, filterValueDT.getClass(), pList);
			for(Iterator<Object> anIterator = pList.iterator(); anIterator.hasNext(); )
			{
				filterValueDT = (FilterValueDT)anIterator.next();
				logger.debug("The filtervalue is " + filterValueDT.getValueUid());
				filterValueDT.setItNew(false);
				filterValueDT.setItDirty(false);
				filterValueDT.setItDelete(false);
				reSetList.add(filterValueDT);
			}
		}
		catch(SQLException sex)
		{
			logger.fatal("reportFilterUID: "+reportFilterUID+" SQLException  = "+sex.getMessage(), sex);
			throw new NEDSSSystemException("SQLException while selecting " +
						"filterValue vo; id = " + reportFilterUID + " :\n" + sex.getMessage());
		}
		catch(Exception ex)
		{
			logger.fatal("reportFilterUID: "+reportFilterUID+" Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException("Exception while selecting " +
							"filterValue vo; id = " + reportFilterUID + " :\n" + ex.getMessage());
		}
		finally
		{
			closeResultSet(resultSet);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
		logger.debug("returning filterValue DT object");
		return reSetList;
	}

	private void removeReportFilters(long reportUID) throws NEDSSSystemException
	{
		Collection<Object> reportFilter = selectReportFilter(reportUID);
		Iterator<Object> anIterator = null;
		long reportFilterUID = -1;
		try
		{
			/**
			 * Delete ReportFilter
			 */
			for(anIterator = reportFilter.iterator(); anIterator.hasNext();)
			{
				ReportFilterDT reportFilterDT = (ReportFilterDT) anIterator.next();
				if(reportFilterDT != null)
				{
					reportFilterUID = reportFilterDT.getReportFilterUid().longValue();
					logger.debug("ReportFIlterDAOImpl:removeReportFilters : reportFilterUID:" + reportFilterUID);
					reportFilterDT.setReportFilterUid(new Long(reportFilterUID));
					removeReportFilter(reportFilterDT);
				}
			}
		}
		catch(Exception ex)
		{
			logger.fatal("reportUID: "+reportUID+" Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException("Exception while getting old of individual ReportFilterDT object \n"
					+ ex.toString() + " \n" + ex.getMessage());
		}
	}

	private void removeReportFilter(ReportFilterDT reportFilterDT) throws NEDSSSystemException
	{
		reportfilterUID = reportFilterDT.getReportFilterUid().longValue();
		logger.debug("ReportFIlterDAOImpl:removeReportFilter started and reportfilterUID :" + reportfilterUID);
		Connection dbConnection = null;
                PreparedStatement preparedStmt = null;
		int resultCount = 0;
		Collection<Object> reportFilterDTCollection  = selectFilterValues(reportFilterDT.getReportFilterUid().longValue());

		if(reportFilterDTCollection!= null)
		{
			removeFilterValues(reportFilterDT.getReportFilterUid().longValue(), reportFilterDTCollection);
		}
		
        //Deletes from Report_Filter_Validation table
		try {
			if (reportFilterDT.getReportFilterInd() != null) {
				dbConnection = getConnection();
				preparedStmt = dbConnection
						.prepareStatement(ReportConstants.DELETE_REPORTFILTER_VALIDATION);
				preparedStmt.setLong(1, reportfilterUID);
				resultCount = preparedStmt.executeUpdate();

				if (resultCount != 1) {
					throw new NEDSSSystemException(
							"Error: cannot delete reportfilter from REPORTFILTER_VALIDATION TABLE!! resultCount = "
									+ resultCount);
				}
			}
		} catch (SQLException sex) {
			logger.fatal("Exception  = "+sex.getMessage(), sex);
			throw new NEDSSSystemException("SQLException while removing "
					+ "reportfilter; id = " + reportfilterUID + " :\n"
					+ sex.getMessage());
		}
        finally
        {
           closeStatement(preparedStmt);
           releaseConnection(dbConnection);
        }        

		/**
		 * Deletes reportfilter
		 */
		try
		{
			dbConnection = getConnection();
                        preparedStmt = dbConnection.prepareStatement(ReportConstants.DELETE_REPORTFILTER);
			preparedStmt.setLong(1, reportfilterUID);
			resultCount = preparedStmt.executeUpdate();

			if (resultCount != 1)
			{
				throw new NEDSSSystemException
				("Error: cannot delete reportfilter from REPORTFILTER_TABLE!! resultCount = " +
				 resultCount);
			}
		}
		catch(SQLException sex)
		{
			logger.fatal("Exception  = "+sex.getMessage(), sex);
			throw new NEDSSSystemException("SQLException while removing " +
							"reportfilter; id = " + reportfilterUID + " :\n" + sex.getMessage());
		}
        finally
        {
           closeStatement(preparedStmt);
           releaseConnection(dbConnection);
        }
        
	}

	private void removeFilterValues( long reportFilterUID, Collection<Object> filterValueDTColl) throws NEDSSSystemException
	{
		logger.debug("ReportFIlterDAOImpl:removeFilterValues: Started the remove of removeFilterValues");
		FilterValueDT filtervalueDT = null;
		Iterator<Object> anIterator = null;
		long filterValueUID = -1;
		if(filterValueDTColl != null)
		{
			/**
			 * Remove FilterValues
			 */
			try
			{
				for(anIterator = filterValueDTColl.iterator(); anIterator.hasNext(); )
				{
					filtervalueDT = (FilterValueDT)anIterator.next();
					filterValueUID = filtervalueDT.getValueUid().longValue();
					logger.debug("ReportFIlterDAOImpl:removeFilterValues: inside For Loop and filterValueUID:" + filterValueUID);
					if(filtervalueDT != null) removeFilterValue(filterValueUID);
				}
			}
			catch(Exception ex)
			{
				logger.fatal("Exception  = "+ex.getMessage(), ex);
				throw new NEDSSSystemException("Error while removing from REPORT_FILTER_VALUE_TABLE, id = " + filterValueUID);
			}
		}
	}

	private void removeFilterValue(long filterValueUID) throws NEDSSSystemException
	{
		logger.debug("ReportFIlterDAOImpl:removeFilterValue: Started the remove of removeFilterValue and filterValueUID is: "+ filterValueUID);
		FilterValueDT filterValueDT = null;
		//filterValueDT.setValueUid(new Long(filterValueUID));
		Connection dbConnection = null;
                PreparedStatement preparedStmt = null;
		int resultCount = 0;


		/**
		 * Deletes filterValues
		 */
		try
		{
			dbConnection = getConnection();
                        preparedStmt = dbConnection.prepareStatement(ReportConstants.DELETE_FILTERVALUE);
			preparedStmt.setLong(1, filterValueUID);
			resultCount = preparedStmt.executeUpdate();
			logger.debug("ReportFIlterDAOImpl:removeFilterValue: resultCount : " + resultCount);

			if (resultCount != 1)
			{
				throw new NEDSSSystemException
				("Error: cannot delete filterValue from FILTER_VALUE_TABLE!! resultCount = " +
				 resultCount);
			}
		}
		catch(SQLException sex)
		{
			logger.fatal("Exception  = "+sex.getMessage(), sex);
			throw new NEDSSSystemException("SQLException while removing " +
							"reportfilter; id = " + reportfilterUID + " :\n" + sex.getMessage());
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

	        ReportFilterDAOImpl reportFilterDAOImpl = new ReportFilterDAOImpl();
	        ReportFilterDT reportFilterDT = new ReportFilterDT();
	        FilterValueDT filtervalueDT = null;
		//create Method
		//reportFilterDT.setStatusCd("test");
		//reportFilterDT.setReportUid(new Long(470003478));
		//reportFilterDT.setDataSourceUid(new Long(470003426));
		//reportFilterDT.setFilterUid(new Long(470003454));
		//reportFilterDAOImpl.create(reportFilterDT);
	    //To test find Method
       Iterator<Object>  anIterator = null;
		long filterValueUID = 605036895;
		ArrayList<Object> list = (ArrayList<Object> )reportFilterDAOImpl.selectFilterValues(filterValueUID);
		//for(anIterator = list.iterator(); anIterator.hasNext(); )
		//		{
		//			logger.debug("ReportFIlterDAOImpl:Test");
		//			filtervalueDT = (FilterValueDT)anIterator.next();
		//		    filterValueUID = filtervalueDT.getValueUid().longValue();
		//			logger.debug("ReportFIlterDAOImpl:removeFilterValues: inside For Loop and filterValueUID:" + filterValueUID);
					//if(filtervalueDT != null) removeFilterValue(filterValueUID);
		//		}

			//dataSourceDaoImpl.findByPrimaryKey(uid);
	    //To Test set(change in values)
			//dataSourceDaoImpl.store(dataSourceDT);

	    } catch(Exception e){}
	}
*/
}
