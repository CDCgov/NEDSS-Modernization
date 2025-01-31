package gov.cdc.nedss.proxy.ejb.epilink.dao;

import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.proxy.ejb.epilink.dt.EpilinkDT;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.DataTables;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class EpilinkActivityLogDaoImpl extends DAOBase{
	
	static final LogUtils logger = new LogUtils(EpilinkActivityLogDaoImpl.class.getName());


	private static final String SELECT_EPILINK_COLLECTION = "SELECT l.activity_log_uid \"activityLogUid\", l.source_Id \"oldEpilinkId\",  l.target_Id \"newEpilinkId\", l.add_time \"processedDate\",  u.first_nm \"firstName\", u.last_nm \"lastName\", l.message_txt \"investigationsString\""
			+ " FROM "+ DataTables.ACTIVITY_LOG_TABLE +" l "
			+"LEFT OUTER JOIN " +  DataTables.USER_PROFILE_TABLE +" u "  
			 + "on l.add_user_id = u.nedss_entry_id "
			+" where  l.add_time between ? and  ? ";
	
	private static final String INSERT_EPILINK_LOG = "INSERT INTO " + DataTables.ACTIVITY_LOG_TABLE + "(doc_type ,doc_nm, record_status_cd , record_status_time , message_txt , action_txt , source_type_cd  ,target_type_cd ,source_Id ,target_Id ,add_user_id  ,add_time ) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";

	@SuppressWarnings("unchecked")
	public ArrayList<Object> getEpilinkDTCollection(Date fromDate, Date toDate) throws NEDSSSystemException{
		EpilinkDT  epilinkDT  = new EpilinkDT();
		ArrayList<Object>  epilinkDTCollection  = new ArrayList<Object> ();
		Calendar cal  = Calendar.getInstance();
		cal.setTime(toDate);
		cal.add(Calendar.DATE, 1);
		epilinkDTCollection.add(new Timestamp(fromDate.getTime()) );//fromDate
		epilinkDTCollection.add(new Timestamp(cal.getTimeInMillis()));
		try
		{
			epilinkDTCollection  = (ArrayList<Object> )preparedStmtMethod(epilinkDT, epilinkDTCollection, SELECT_EPILINK_COLLECTION, NEDSSConstants.SELECT);
			
		}
		 catch (Exception ex) {
			logger.fatal("Exception in getCTAnswerDTCollection:  ERROR = " + ex);
					throw new NEDSSSystemException(ex.toString());
		}
		return epilinkDTCollection;
	}
	
	
	public void insertEpilinkLog(EpilinkDT epilinkDT) throws  NEDSSSystemException {
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		int resultCount = 0;
		logger.debug("EpilinkDT ="+epilinkDT);
		dbConnection = getConnection();
		try{
		preparedStmt = dbConnection.prepareStatement(INSERT_EPILINK_LOG);
		int i = 1;
	
	    preparedStmt.setString(i++, epilinkDT.getDocType());//1
	    
	    preparedStmt.setString(i++, epilinkDT.getDocName());//2
	    preparedStmt.setString(i++, epilinkDT.getRecordStatusCd());//3
	    
	    if(epilinkDT.getRecordStatusTime()!= null){	
	    	preparedStmt.setTimestamp(i++, epilinkDT.getRecordStatusTime());//4
	    }else{
	    	 preparedStmt.setNull(i++, Types.TIMESTAMP); //4
	    }
	    
	    preparedStmt.setString(i++, epilinkDT.getInvestigationsString());//5
	    preparedStmt.setString(i++, epilinkDT.getActionTxt());//6
	    preparedStmt.setString(i++, epilinkDT.getSourceTypeCd());//7
	    preparedStmt.setString(i++, epilinkDT.getTargetTypeCd());//8
	    preparedStmt.setString(i++, epilinkDT.getOldEpilinkId());//9
	    preparedStmt.setString(i++, epilinkDT.getNewEpilinkId());//10
	    if(epilinkDT.getAddUserId()!= null){	
	    	preparedStmt.setLong(i++, epilinkDT.getAddUserId());//11
	    }else{
	    	 preparedStmt.setNull(i++, Types.BIGINT); //11
	    }
	    
	    if(epilinkDT.getProcessedDate()!= null){	
	    	preparedStmt.setTimestamp(i++, new Timestamp( epilinkDT.getProcessedDate().getTime()));//12
	    }else{
	    	 preparedStmt.setNull(i++, Types.TIMESTAMP); //12
	    }
	    
	    resultCount = preparedStmt.executeUpdate();
	    logger.debug("resultCount in insertEpilinkLog is " + resultCount);
	}
	catch(SQLException sqlex)
	{
	    logger.fatal("SQLException while inserting EpilinkDT into activity_logr: " + epilinkDT.toString(), sqlex);
	    throw new NEDSSDAOSysException( sqlex.toString() );
	}
	catch(Exception ex)
	{
	    logger.fatal("Error while inserting EpilinkDT , interviewAnswerDT = " + epilinkDT.toString(), ex);
	    throw new NEDSSSystemException(ex.toString());
	}
	finally
	{
	  closeStatement(preparedStmt);
	  releaseConnection(dbConnection);
	}

}
	
	
	
}
