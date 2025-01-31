package gov.cdc.nedss.report.javaRepot.dao;

import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.report.javaRepot.dt.ReportInterviewDT;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.util.ResultSetUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Collection;
/**
 * ReportInterviewDAO for custom Report Generation
 * @author Pradeep Kumar Sharma
 *
 */
public class ReportInterviewDAO extends DAOBase{
	private static PropertyUtil propertyUtil= PropertyUtil.getInstance();
	   static final LogUtils logger = new LogUtils(ReportInterviewDAO.class.getName());
	   static String dbTypeSeperator = " RDB..";    


	
	 private String  INTERVIEW_EPILINK_ID_COLLECTION = "select   d_interview.d_interview_key \"interviewKey\","
	+ "	EPI_LINK_ID \"epiLinkId\", "
	+ " IX_LOCATION \"interviewLocation\",IX_TYPE \"interviewType\", "
	+ " interview_date \"interviewDate\", "
	+ " f_interview_case.provider_key \"interviewProviderKey\" , "
	+ " STD_HIV_DATAMART.INVESTIGATION_KEY \"investigationKey\""
	+ " from "
	+ dbTypeSeperator+"d_interview "
	+ " inner join " 
	+ dbTypeSeperator+" f_interview_case "
	+ " on "
	+ " f_interview_case.d_interview_key=d_interview.d_interview_key "
	+ " inner join "
	+ dbTypeSeperator+"STD_HIV_DATAMART "
	+ " on "
	+ " STD_HIV_DATAMART.INVESTIGATION_KEY = f_interview_case.INVESTIGATION_KEY "
	+ " where STD_HIV_DATAMART.EPI_LINK_ID=?";

	 public Collection<Object> getInterviewsForEpiLinkId(String epiLinkId,NBSSecurityObj nbsSecurityObj) throws NEDSSSystemException
	    {
		    String dataAccessWhereClause = nbsSecurityObj.getDataAccessWhereClause(
		            NBSBOLookup.INVESTIGATION, NBSOperationLookup.VIEW);
		        logger.debug(" dataAccessWhereClause = " + dataAccessWhereClause);
		        if (dataAccessWhereClause == null) {
		          dataAccessWhereClause = "";
		        }
		        else {
		          dataAccessWhereClause = " AND " + dataAccessWhereClause;

		        }
		        INTERVIEW_EPILINK_ID_COLLECTION = INTERVIEW_EPILINK_ID_COLLECTION+dataAccessWhereClause;
		        Connection dbConnection = null;
		        try
		        {
		           dbConnection = getConnection(NEDSSConstants.RDB);
		        }
		        catch(NEDSSSystemException nsex)
		        {
		            logger.fatal("SQLException while obtaining database connection " + nsex.getMessage(), nsex);
		            throw new NEDSSSystemException(nsex.toString());
		        }
		        ReportInterviewDT reportInterviewDT = new ReportInterviewDT();
			    PreparedStatement preparedStmt = null;
		        ResultSet resultSet = null;
		        ResultSetMetaData resultSetMetaData = null;
		        ResultSetUtils resultSetUtils = new ResultSetUtils();
		        ArrayList<Object> retval = new ArrayList<Object> ();
	            try
		        {
			        preparedStmt = dbConnection.prepareStatement(INTERVIEW_EPILINK_ID_COLLECTION);
			        preparedStmt.setString(1,epiLinkId);
			        resultSet = preparedStmt.executeQuery();
		            resultSetMetaData = resultSet.getMetaData();
		            retval = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData, reportInterviewDT.getClass(), retval);
		            
		        }
		        catch (Exception ex)
		        {
		            logger.fatal("epiLinkId: "+epiLinkId+" Exception in getInterviewsForEpiLinkId:  ERROR = " + ex.getMessage(), ex);
		            throw new NEDSSSystemException(ex.toString());
		        }
	            finally
	            {
	    			closeResultSet(resultSet);
	            	closeStatement(preparedStmt);
	                releaseConnection(dbConnection);
	            }	            
		        return retval;
	    }

}
