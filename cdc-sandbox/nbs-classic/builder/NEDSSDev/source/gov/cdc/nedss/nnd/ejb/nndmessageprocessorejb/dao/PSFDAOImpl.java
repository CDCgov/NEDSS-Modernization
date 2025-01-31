package gov.cdc.nedss.nnd.ejb.nndmessageprocessorejb.dao;

import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.srtadmin.dt.LabTestDT;
import gov.cdc.nedss.systemservice.dt.ActivityLogDT;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.DataTables;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;


/**
* Name:		PSFDAOImpl.java
* Description:	DAO Object for to Populating Partner Services Tables
* Copyright:	Copyright (c) 2018
* Company: 	GDIT
* @author	Fatima Lopez Calzado
*/

public class PSFDAOImpl extends DAOBase{
	static final LogUtils logger = new LogUtils(PSFDAOImpl.class.getName());
	/**
	 * populateTablesForPartnerServicesFile: call store procedures to fill the Partner service tables, like PSF_SESSION
	 * @param publicHealthCaseUid
	 */
	
		public String populateTablesForPartnerServicesFile(String incrementalOrFull) {

		 	Connection dbConnection = null;
	        CallableStatement sProc = null;
	        String result = "";
	        
	         try
	         {
	             dbConnection = getConnection(NEDSSConstants.MSGOUT);
	            
	         }
	         catch(NEDSSSystemException nsex)
	         {
	             logger.fatal("Error obtaining dbConnection "+nsex.getMessage(), nsex);
	             throw new NEDSSSystemException(nsex.toString());
	         }

	         try
	         {
	        	 logger.debug("About to call stored procedure: populatePSFTables_sp");
	             String sQuery  = "{call populatePSFTables_sp(?)}"; 
	             logger.info("sQuery = " + sQuery);
	             sProc = dbConnection.prepareCall(sQuery);
	             logger.debug("After prepareCall");
	 	         sProc.setString(1, incrementalOrFull);
	             logger.debug("Before executing the query");
	             sProc.execute();
	             logger.debug("After executing the query");
	           //  sProc.getMoreResults()
	             
	             
	         	String codeSql = "SELECT TOP 1 message_status from NBS_MSGOUTE..MsgOut_activity_log order by 1 desc";
	    
	         	
	         	
	   		 PreparedStatement preparedStmt = dbConnection.prepareStatement(codeSql);
			 logger.debug("Before executing the query");
			 ResultSet resultSet = preparedStmt.executeQuery();
			 logger.debug("After executing the query");
			 if(resultSet.next())
				 result = resultSet.getString(1);
	    		
	         
	         }
	         catch(NEDSSSystemException nse)
	         {
	         	logger.fatal("Exception in PSFDAOImpl.populateTablesForPartnerServicesFile: NEDSSSystemException  = "+nse.getMessage(), nse);
	          //   throw new NEDSSSystemException("Exception in PSFDAOImpl.populateTablesForPartnerServicesFile: Error: while obtaining database connection.\n" + nse.getMessage(), nse);
	         }
	         catch(SQLException nse)
	         {
	         	logger.fatal("Exception in PSFDAOImpl.populateTablesForPartnerServicesFile: NEDSSSystemException  = "+nse.getMessage(), nse);
	           //  throw new NEDSSSystemException("Exception in PSFDAOImpl.populateTablesForPartnerServicesFile: Error: while obtaining database connection.\n" + nse.getMessage(), nse);
	         }
	 		catch(Exception e)
	 		{
	 			logger.fatal("Exception in PSFDAOImpl.populateTablesForPartnerServicesFile  = "+e.getMessage(), e);
	 		//    throw new NEDSSSystemException("Exception in PSFDAOImpl.populateTablesForPartnerServicesFile: = " + e.toString(),e);
	 		}
	         finally
	         {
	             closeCallableStatement(sProc);
	 			 releaseConnection(dbConnection);
	         }
	         
	         return result;
	}
		
		private static final String INSERT_ACTIVITY_LOG = "INSERT INTO " + DataTables.ACTIVITY_LOG_TABLE + "(doc_type ,doc_nm, record_status_cd , record_status_time , message_status, message_txt , action_txt , add_user_id ,add_time ) VALUES(?,?,?,?,?,?,?,?,?)";

		/**
		 * insertActivityLog: inserts into the activity_log table last time the populatePSDTables batch process was run.
		 * @param activityLogDT
		 * @throws NEDSSSystemException
		 */
		public void insertActivityLog(ActivityLogDT activityLogDT) throws  NEDSSSystemException {
			Connection dbConnection = null;
			PreparedStatement preparedStmt = null;
			int resultCount = 0;
			logger.debug("DT ="+activityLogDT);
			dbConnection = getConnection(NEDSSConstants.MSGOUT);
			try{
			preparedStmt = dbConnection.prepareStatement(INSERT_ACTIVITY_LOG);
			int i = 1;
		
		    preparedStmt.setString(i++, activityLogDT.getDocType());//1
		    
		    preparedStmt.setString(i++, activityLogDT.getDocName());//2
		    preparedStmt.setString(i++, activityLogDT.getRecordStatusCd());//3
		    
		    if(activityLogDT.getRecordStatusTime()!= null){	
		    	preparedStmt.setTimestamp(i++, activityLogDT.getRecordStatusTime());//4
		    }else{
		    	 preparedStmt.setNull(i++, Types.TIMESTAMP); //4
		    }
		    
		    preparedStmt.setString(i++, activityLogDT.getMessageStatus());//5
		    preparedStmt.setString(i++, activityLogDT.getMessageTxt());//6
		    
		    preparedStmt.setString(i++, activityLogDT.getActionTxt());//7
		    if(activityLogDT.getAddUserId()!= null){	
		    	preparedStmt.setLong(i++, activityLogDT.getAddUserId());//7
		    }else{
		    	 preparedStmt.setNull(i++, Types.BIGINT); //8
		    }
		    
		    if(activityLogDT.getAddTime()!= null){	
		    	preparedStmt.setTimestamp(i++, activityLogDT.getAddTime());//9
		    }else{
		    	 preparedStmt.setNull(i++, Types.TIMESTAMP); //9
		    }
		    
		    resultCount = preparedStmt.executeUpdate();
		    logger.debug("resultCount in insertActivityLog is " + resultCount);
		}
		catch(SQLException sqlex)
		{
		    logger.fatal("SQLException while inserting into activity_logr: " + activityLogDT.toString(), sqlex);
		    throw new NEDSSDAOSysException( sqlex.toString() );
		}
		catch(Exception ex)
		{
		    logger.fatal("Error while inserting into activity_log = " + activityLogDT.toString(), ex);
		    throw new NEDSSSystemException(ex.toString());
		}
		finally
		{
		  closeStatement(preparedStmt);
		  releaseConnection(dbConnection);
		}

	}
		
}
