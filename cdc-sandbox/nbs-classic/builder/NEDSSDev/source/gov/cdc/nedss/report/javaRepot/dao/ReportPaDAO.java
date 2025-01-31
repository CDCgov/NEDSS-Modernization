package gov.cdc.nedss.report.javaRepot.dao;

import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.report.javaRepot.dt.ReportPatientDT;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.ResultSetUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
/**
 * Util class to get Report specific data
 * @author Pradeep Kumar Sharma
 *
 */
public class ReportPaDAO extends DAOBase{
	static final LogUtils logger = new LogUtils(ReportPaDAO.class.getName());
	
	public Collection<Object> getQueryResults(Timestamp fromConfirmTime, Timestamp toConfirmTime, String query, String worker,String diagnosis, String orderByClause,
			NBSSecurityObj nbsSecurityObj) throws NEDSSSystemException{
		
		logger.debug("ReportPaDAO.getQueryResults: The query variables are :\n1. fromConfirmTime :"+ fromConfirmTime+"\n2. toConfirmTime :"+toConfirmTime+"\n3. query :"+query+"\n4. worker :"+ worker+"\n5. diagnosis:"+diagnosis+"\n6. orderByClause:"+orderByClause+"\n7. nbsSecurityObj :"+nbsSecurityObj);
		Connection dbConnection = null;
		
		if(nbsSecurityObj==null)
			logger.debug("ReportPaDAO.getQueryResults nbsSecurityObj is null");
		else
			logger.debug("ReportPaDAO.getQueryResults nbsSecurityObj is NOT null and dataAccessWhereClause will be applied!!!!");
			
		try
		{
			dbConnection = getConnection(NEDSSConstants.RDB);
		}
		catch(NEDSSSystemException nsex)
		{
			logger.fatal("ReportPaDAO.getQueryResults: The query variables are :\n1. fromConfirmTime :"+ fromConfirmTime+"\n2. toConfirmTime :"+toConfirmTime+"\n3. query :"+query+"\n4. worker :"+ worker+"\n5. diagnosis:"+diagnosis+"\n6. orderByClause:"+orderByClause+"\n7. nbsSecurityObj :"+nbsSecurityObj);
			logger.fatal("ReportPaDAO.getQueryResults SQLException while obtaining database connection " + nsex.getMessage(), nsex);
			throw new NEDSSSystemException(nsex.toString());
		}

		if(nbsSecurityObj!=null){
			String dataAccessWhereClause = nbsSecurityObj.getDataAccessWhereClause(NBSBOLookup.INVESTIGATION, NBSOperationLookup.VIEW);
			logger.debug("ReportPaDAO.getQueryResults dataAccessWhereClause = " + dataAccessWhereClause);
			if (dataAccessWhereClause == null) {
				dataAccessWhereClause = "";
			}
			else {
				dataAccessWhereClause = " AND " + dataAccessWhereClause + orderByClause;
			}
			query=query+dataAccessWhereClause;
		}
		
		logger.debug("ReportPaDAO.getQueryResults: final query = " + query);
		//ReportPatientDT reportPatientDT = new ReportPatientDT();
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		ResultSetMetaData resultSetMetaData = null;
		ResultSetUtils resultSetUtils = new ResultSetUtils();
		ArrayList<Object> retval = new ArrayList<Object> ();
		try
		{
			preparedStmt = dbConnection.prepareStatement(query);
			int i =1;
			if(fromConfirmTime!=null)
				preparedStmt.setTimestamp(i++,fromConfirmTime);
			if(toConfirmTime!=null)
				preparedStmt.setTimestamp(i++,toConfirmTime);
			if(worker!=null && worker.trim().length()>0)
				preparedStmt.setString(i++,worker);
			if(diagnosis!=null && diagnosis.trim().length()>0)
				preparedStmt.setString(i++,diagnosis);
			
			resultSet = preparedStmt.executeQuery();
			resultSetMetaData = resultSet.getMetaData();
			retval = (ArrayList<Object> )resultSetUtils.mapRsToBeanList(resultSet, resultSetMetaData, ReportPatientDT.class, retval);
		}catch (Exception ex)
		{
			logger.fatal("ReportPaDAO.getQueryResults: The query variables are :\n1. fromConfirmTime :"+ fromConfirmTime+"\n2. toConfirmTime :"+toConfirmTime+"\n3. query :"+query+"\n4. worker :"+ worker+"\n5. diagnosis:"+diagnosis+"\n6. orderByClause:"+orderByClause+"\n7. nbsSecurityObj :"+nbsSecurityObj);
			logger.fatal("ReportPaDAO.getQueryResults: Exception in getCasesByWorker:  ERROR = " + ex.getMessage(), ex);
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
