package gov.cdc.nedss.report.javaRepot.dao;

import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.report.javaRepot.dt.ReportQueryReaderDT;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.ResultSetUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.HashMap;
import java.util.Map;
/**
 * Utility class to read query data from report query database
 * @author Pradeep Kumar Sharma
 *
 */
public class ReportQueryReaderDAO extends DAOBase{
	static final LogUtils logger = new LogUtils(ReportQueryReaderDAO.class.getName());
	
	private String GET_REPORT_DATA="select report_query_uid \"reportQueryUid\", report_uid \"reportUid\", report_query_Constant \"reportQueryConstant\", query \"query\" from report_query where report_uid=?";

	
	public Map<Object,Object>getReportQueryMap(Long reportUid) throws NEDSSSystemException
	{
		Connection dbConnection = null;
		try
		{
			dbConnection = getConnection();
		}
		catch(NEDSSSystemException nsex)
		{
			logger.fatal("SQLException while obtaining database connection " + nsex.getMessage(), nsex);
			throw new NEDSSSystemException(nsex.toString());
		}
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		ResultSetMetaData resultSetMetaData = null;
		ResultSetUtils resultSetUtils = new ResultSetUtils();
	    HashMap<Object,Object> reportQueryMap = new HashMap<Object,Object>();
	    Map<Object,Object> map = null;
		try
		{
			preparedStmt=dbConnection.prepareStatement(GET_REPORT_DATA);
			preparedStmt.setLong(1,reportUid);
			resultSet = preparedStmt.executeQuery();
			resultSetMetaData = resultSet.getMetaData();
			reportQueryMap=(HashMap<Object,Object>) resultSetUtils.mapRsToBeanMap(resultSet, resultSetMetaData, ReportQueryReaderDT.class, "getReportQueryConstant", map);
		}catch (Exception ex)
		{
			logger.fatal("Exception in getReportQueryMap:  ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		finally
        {
			closeResultSet(resultSet);
        	closeStatement(preparedStmt);
            releaseConnection(dbConnection);
        }
		
		return reportQueryMap;
	}

}
