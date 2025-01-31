package gov.cdc.nedss.alert.ejb.dao;


import gov.cdc.nedss.alert.dt.AlertDT;
import gov.cdc.nedss.alert.dt.AlertUserDT;
import gov.cdc.nedss.alert.dt.UserEmailDT;
import gov.cdc.nedss.alert.util.AlertStateConstant;
import gov.cdc.nedss.alert.vo.AlertVO;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.DataTables;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class AlertDAO extends DAOBase {
	static final LogUtils logger = new LogUtils(AlertDAO.class.getName());
	private String SELECT_ALERTS = "SELECT alert_uid \"alertUid\" , type_cd \"typeCd\",  condition_cd \"conditionCd\", jurisdiction_cd \"jurisdictionCd\", add_time \"addTime\", last_chg_time \"lastChgTime\", record_status_cd \"recordStatusCd\", add_user_id \"addUserId\", last_chg_user_id \"lastChgUserId\", severity_cd \"severityCd\", alert_msg_txt \"alertMsgTxt\" from "+ DataTables.ALERT ;
	private String SELECT_ALERT_USER = "SELECT alert_user_uid \"alertUserUid\", alert_uid \"alertUid\", nedss_entry_id \"nedssEntryId\" FROM "
			+ DataTables.ALERT_USER + " where alert_uid=?";
	private String SELECT_EMAIL_ALERT_USER = "SELECT user_email_uid \"userEmailUid\", nedss_entry_id \"nedssEntryId\", seq_nbr \"seqNbr\", email_id \"emailId\", add_time \"addTime\", last_chg_time \"lastChgTime\", record_status_cd \"recordStatusCd\", add_user_id \"addUserId\",last_chg_user_id \"lastChgUserId\" FROM  "
			+ DataTables.USER_EMAIL + " where nedss_entry_id=?";

	private String INSERT_ALERT_USER = "INSERT INTO " + DataTables.ALERT_USER
			+ "(alert_uid, nedss_entry_id) Values(?,?)";
	private String INSERT_ALERT = "INSERT INTO "
			+ DataTables.ALERT
			+ "(type_cd, condition_cd, jurisdiction_cd, add_time, last_chg_time, record_status_cd, add_user_id, last_chg_user_id, severity_cd, alert_msg_txt) VALUES(?,?,?,?,?,?,?,?,?,?)";
	private static final String DELETE_ALERT_USER= "DELETE from  "+ DataTables.ALERT_USER+ " where alert_uid = ?";
	private static final String DELETE_ALERT= "DELETE from "+ DataTables.ALERT + " where alert_uid = ?";
	private static final String LOGICAL_DELETE_ALERT = "Update "+ DataTables.ALERT + " SET record_status_cd ='LOG_DEL' where alert_uid = ?";
	private static final String UPDATE_ALERT= "Update "+ DataTables.ALERT + " SET severity_cd = ?"
	                                          +", alert_msg_txt=? , last_chg_time = ? , last_chg_user_id = ? where alert_uid = ?";

	public void setAlertVO(AlertVO alertVO)  throws NEDSSSystemException {
		try{
			// set the alert table
			Long alertUid = insertAlertDT(alertVO.getAlertDT());
			Collection<Object> alertUserColl = alertVO.getAlertUserDTCollection();
			Iterator iter = alertUserColl.iterator();
			while (iter.hasNext()) {
				AlertUserDT alertUserDT = (AlertUserDT) iter.next();
				alertUserDT.setAlertUid(alertUid);
				insertAlertUserDT(alertUserDT);
			}
		}catch(NEDSSSystemException ex){
			logger.fatal("Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	
	public void updateAlert(AlertVO alertVO,Long oldAlertUid)throws NEDSSSystemException {
		try{
			//Update the alert table
			updateAlertDT(alertVO.getAlertDT());
			// set the alert user table
			Collection<Object> alertUserColl = alertVO.getAlertUserDTCollection();
			Iterator<Object> iter = alertUserColl.iterator();
			while (iter.hasNext()) {
				AlertUserDT alertUserDT = (AlertUserDT) iter.next();
				alertUserDT.setAlertUid(oldAlertUid);
				insertAlertUserDT(alertUserDT);
			}
		}catch(Exception ex){
			logger.fatal("Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}
	

	private Long  insertAlertDT(AlertDT alertDT) throws NEDSSSystemException {
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		PreparedStatement preparedStmt2 = null;
		Long alertUid = null;
		
		logger.debug("INSERT_ALERT=" + INSERT_ALERT);
		
		try{
			dbConnection = getConnection();
        }catch(NEDSSSystemException nsex){
            logger.fatal("SQLException while obtaining database connection for insertAlertDT ", nsex);
            throw new NEDSSSystemException(nsex.toString());
        }
		
		int resultCount = 0;
		try {
			preparedStmt = dbConnection.prepareStatement(INSERT_ALERT);
			int i = 1;
			preparedStmt.setString(i++, alertDT.getTypeCd()); // 1
			preparedStmt.setString(i++, alertDT.getConditionCd()); // 2
			preparedStmt.setString(i++, alertDT.getJurisdictionCd()); // 3
			preparedStmt.setTimestamp(i++, alertDT.getAddTime()); // 4
			preparedStmt.setTimestamp(i++, alertDT.getLastChgTime()); // 5
			preparedStmt.setString(i++, alertDT.getRecordStatusCd()); // 6
			preparedStmt.setLong(i++, alertDT.getAddUserId().longValue()); // 7
			preparedStmt.setLong(i++, alertDT.getLastChgUserId().longValue()); // 8
			preparedStmt.setString(i++, alertDT.getSeverityCd()); // 9
			preparedStmt.setString(i++, alertDT.getAlertMsgTxt()); //10

			resultCount = preparedStmt.executeUpdate();
			preparedStmt2 = dbConnection.prepareStatement("SELECT MAX(ALERT_UID) from ALERT");

			ResultSet  rs = preparedStmt2.executeQuery();
			 if (rs.next()) {
			        logger.debug("ID = " + rs.getLong(1));
				 alertUid=new Long( rs.getLong(1));
			      }
		} catch (SQLException sqlex) {
			logger.fatal("SQLException while inserting "
					+ "alertDT  insertAlertDT:" + alertDT.toString(),
					sqlex);
			throw new NEDSSDAOSysException(sqlex.toString());
		} catch (Exception ex) {
			logger.fatal("Error while inserting into PAM_ANSWER, alertDT="
					+ alertDT.toString(), ex);
			throw new NEDSSSystemException(ex.toString());
		} finally {
			closeStatement(preparedStmt2);
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
		return alertUid;
	}// end of insert method

	private void insertAlertUserDT(AlertUserDT alertUserDT)
			throws NEDSSSystemException {
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		int resultCount = 0;
		logger.debug("INSERT_ALERT_USER=" + INSERT_ALERT_USER);
		
		try{
			dbConnection = getConnection();
        }catch(NEDSSSystemException nsex){
            logger.fatal("SQLException while obtaining database connection for insertAlertUserDT ", nsex);
            throw new NEDSSSystemException(nsex.toString());
        }
		
		try {
			preparedStmt = dbConnection.prepareStatement(INSERT_ALERT_USER);
			int i = 1;
			preparedStmt.setLong(i++, alertUserDT.getAlertUid().longValue()); // 1
			preparedStmt
					.setLong(i++, alertUserDT.getNedssEntryId().longValue()); // 2
			resultCount = preparedStmt.executeUpdate();
		} catch (SQLException sqlex) {
			logger.fatal("SQLException while inserting "
					+ "AlertUserDT in insertAlertUserDT:" + alertUserDT.toString(),
					sqlex);
			throw new NEDSSDAOSysException(sqlex.toString());
		} catch (Exception ex) {
			logger.fatal("Error while inserting into insertAlertUserDT, alertUserDT="
					+ alertUserDT.toString(), ex);
			throw new NEDSSSystemException(ex.toString());
		} finally {
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
	}// end of insert method

	
	private void  updateAlertDT(AlertDT alertDT) throws NEDSSSystemException {
		 	Connection dbConnection = null;
	        PreparedStatement preparedStmt = null;
	        int resultCount = 0;
	        try{
				dbConnection = getConnection();
	        }catch(NEDSSSystemException nsex){
	            logger.fatal("SQLException while obtaining database connection for updateAlertDT ", nsex);
	            throw new NEDSSSystemException(nsex.toString());
	        }
	        try {
				preparedStmt = dbConnection.prepareStatement(UPDATE_ALERT);
				int i = 1;
				preparedStmt.setString(i++, alertDT.getSeverityCd()); // 1
				preparedStmt.setString(i++, alertDT.getAlertMsgTxt()); // 2
				preparedStmt.setTimestamp(i++, alertDT.getLastChgTime()); // 3
				preparedStmt.setLong(i++, alertDT.getLastChgUserId().longValue()); // 4
				preparedStmt.setLong(i++, alertDT.getAlertUid().longValue()); // 5
				
				
				resultCount = preparedStmt.executeUpdate();
	             if (resultCount != 1)
	                logger.error
	                            ("Error: none or more than one alert uid updated at a time, " +
	                              "resultCount = " + resultCount);
	            }
	            catch(SQLException auid)
	            {
	                logger.fatal("SQLException while updating " +
	                    "alert uids, \n", auid);
	                throw new NEDSSDAOSysException( auid.toString() );
	            }
	            finally
	            {
	                closeStatement(preparedStmt);
	                releaseConnection(dbConnection);
	            }

	        
	}// end of update method
	
	public Collection<Object> getAlertCollection( String conditionCd,
			String jurisdictionCd,String recordStatusCd, String typeCode, boolean isadminUser, boolean isUICall) throws NEDSSSystemException {
		Collection<Object> alertVOCollection  = new ArrayList<Object> ();
		
		try{
			Collection<Object> coll = getAlertDTCollection(conditionCd,
					jurisdictionCd,recordStatusCd, typeCode, isadminUser, isUICall);
			if (coll != null && coll.size()>0) {
				AlertVO alertVO = null;
				Iterator<Object> it = coll.iterator();
				while (it.hasNext()) {
					alertVO = new AlertVO();
					AlertDT alertDT = (AlertDT) it.next();
					alertVO.setAlertDT(alertDT);
					Collection<Object> nedssUserDTCollection  = getAlertUserDTCollection(alertDT.getAlertUid());
					alertVO.setAlertUserDTCollection(nedssUserDTCollection);
					
					if(nedssUserDTCollection!=null){
						Iterator<Object> iter = nedssUserDTCollection.iterator();
						while(iter.hasNext()){
							AlertUserDT alertUserDT = (AlertUserDT)iter.next();
							Collection<Object> nedssEmailDTCollection  = getEmailAlertDTCollection(alertUserDT.getNedssEntryId());
							alertUserDT.setUserEmailCollection(nedssEmailDTCollection);
						}
					}
					alertVOCollection.add(alertVO);
				}
			}
		}catch(Exception ex){
			logger.fatal("Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		
		return alertVOCollection;
	}

	@SuppressWarnings("unchecked")
	private Collection<Object> getAlertDTCollection( String conditionCd, String jurisdictionCd, String recordStatusCd, String typeCode, boolean isadminUser, boolean isUICall)
			throws NEDSSSystemException {

		AlertDT alertDT = new AlertDT();
		ArrayList<Object>  alertDTCollection  = new ArrayList<Object> ();
		StringBuffer sbf = new StringBuffer();
		sbf.append(SELECT_ALERTS);
		if (conditionCd != null && jurisdictionCd != null && recordStatusCd !=null) {
			sbf.append(" where ");
		}
		if (conditionCd != null)
			sbf.append(" condition_cd ='" + conditionCd+"'");
		//if((jurisdictionCd != null && eventType != null) || (jurisdictionCd != null && conditionCd != null))
			if(isadminUser){
			sbf.append(" and " );
				if (jurisdictionCd != null)
			sbf.append(" jurisdiction_cd ='" + jurisdictionCd+"'");
			}
			else{
				sbf.append(" and " );
				if (!AlertStateConstant.JURISDICTION_NOT_ASSIGNED.equals(jurisdictionCd))
					sbf.append(" jurisdiction_cd in ('" +AlertStateConstant.JURISDICTION_ALL+"', '" +jurisdictionCd +"')");
				else if(AlertStateConstant.JURISDICTION_NOT_ASSIGNED.equals(jurisdictionCd)) 
					sbf.append(" jurisdiction_cd ='" + jurisdictionCd+"'");
			}
			sbf.append(" and " );
			if (recordStatusCd != null)
				sbf.append(" record_status_cd ='" + recordStatusCd+"'");
			if (typeCode != null){
				if(isUICall)
				sbf.append(" and type_cd ='" + typeCode+"'");
				else
					sbf.append(" and type_cd like '%" + typeCode+"%'");
			}
			sbf.append(" order by type_cd");
		try {
			alertDTCollection  = (ArrayList<Object> ) preparedStmtMethod(alertDT,
					alertDTCollection, sbf.toString(), NEDSSConstants.SELECT);

		} catch (Exception ex) {
			logger.fatal("Exception in getAlertDTCollection:  ERROR = "
					+ ex.getMessage(),ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return alertDTCollection;
	}

	@SuppressWarnings("unchecked")
	public Collection<Object> getAlertUserDTCollection(Long alertUid)
			throws NEDSSSystemException {
		AlertUserDT alertUserDT = new AlertUserDT();
		ArrayList<Object>  alertUserDTCollection  = new ArrayList<Object> ();
		alertUserDTCollection.add(alertUid);
		try {
			alertUserDTCollection  = (ArrayList<Object> ) preparedStmtMethod(alertUserDT,
					alertUserDTCollection, SELECT_ALERT_USER,
					NEDSSConstants.SELECT);
		} catch (Exception ex) {
			logger.fatal("Exception in getAlertUserDTCollection:  ERROR = "
					+ ex.getMessage(),ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return alertUserDTCollection;
	}

	@SuppressWarnings("unchecked")
	private Collection<Object> getEmailAlertDTCollection(Long nedssEntryUid)
			throws NEDSSSystemException {
		UserEmailDT userEmailDT = new UserEmailDT();
		ArrayList<Object>  alertUserDTCollection  = new ArrayList<Object> ();
		alertUserDTCollection.add(nedssEntryUid);
		try {
			alertUserDTCollection  = (ArrayList<Object> ) preparedStmtMethod(
					userEmailDT, alertUserDTCollection,
					SELECT_EMAIL_ALERT_USER, NEDSSConstants.SELECT);
		} catch (Exception ex) {
			logger.fatal("Exception in getEmailAlertDTCollection:  ERROR = "
					+ ex.getMessage(),ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return alertUserDTCollection;
	}
	
	private void removeAlert(Long oldAlertUid)
	throws NEDSSDAOSysException, NEDSSSystemException {

		PreparedStatement preparedStmt = null;
		Connection dbConnection = null;

		try {
			logger
			.debug("$$$$###Delete DELETE_ALERT being called :"
					+ DELETE_ALERT);
			dbConnection = getConnection();
			preparedStmt = dbConnection
			.prepareStatement(DELETE_ALERT);
			preparedStmt.setLong(1, oldAlertUid.longValue());
			preparedStmt.executeUpdate();
			preparedStmt.executeUpdate();
		} catch (SQLException se) {
			logger.fatal("SQLException  = "+se.getMessage(), se);
			throw new NEDSSDAOSysException(
					"Error: SQLException while deleting\n" + se.getMessage());
		} finally {
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
	}
	
	private void logicalRemoveAlert(Long oldAlertUid)
	throws NEDSSDAOSysException, NEDSSSystemException {

		PreparedStatement preparedStmt = null;
		Connection dbConnection = null;

		try {
			logger
			.debug("$$$$###Delete LOGICAL_DELETE_ALERT being called :"
					+ LOGICAL_DELETE_ALERT);
			dbConnection = getConnection();
			preparedStmt = dbConnection
			.prepareStatement(LOGICAL_DELETE_ALERT);
			preparedStmt.setLong(1, oldAlertUid.longValue());
			preparedStmt.executeUpdate();
		preparedStmt.executeUpdate();
		} catch (SQLException se) {
			logger.fatal("SQLException  = "+se.getMessage(), se);
			throw new NEDSSDAOSysException(
					"Error: SQLException while deleting\n" + se.getMessage());
		} finally {
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
	}
	

	private void removeAlertUser(Long oldAlertUid)
	throws NEDSSDAOSysException, NEDSSSystemException {

		PreparedStatement preparedStmt = null;
		Connection dbConnection = null;

		try {
			logger
			.debug("$$$$###Delete DELETE_ALERT_USER being called :"
					+ DELETE_ALERT_USER);
			dbConnection = getConnection();
			preparedStmt = dbConnection
			.prepareStatement(DELETE_ALERT_USER);
			preparedStmt.setLong(1, oldAlertUid.longValue());
			preparedStmt.executeUpdate();
		preparedStmt.executeUpdate();
		} catch (SQLException se) {
			logger.fatal("SQLException  = "+se.getMessage(), se);
			throw new NEDSSDAOSysException(
					"Error: SQLException while deleting\n" + se.getMessage());
		} finally {
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
	}

	public void updateAlertVO(AlertVO alertVO, Long oldAlertUid) throws NEDSSDAOSysException, NEDSSSystemException{
		removeAlertUser(oldAlertUid);
		//removeAlert(oldAlertUid);
		updateAlert(alertVO, oldAlertUid);
	}

	public void deletetAlertVO(Long alertUid) throws NEDSSDAOSysException, NEDSSSystemException{
		removeAlertUser(alertUid);
		logicalRemoveAlert(alertUid);
	}
	
	
	private static final String DELETE_ALERT_USER_FOR_NEDSS_ENTRY_ID= "DELETE from  "+ DataTables.ALERT_USER+ " where nedss_entry_id = ?";
	
	public void removeAlertUserForNedssEntryUId(Long NedssEntryId)
			throws NEDSSDAOSysException, NEDSSSystemException {

		PreparedStatement preparedStmt = null;
		Connection dbConnection = null;

		try {
			logger
			.debug("$$$$###Delete DELETE_ALERT_USER_FOR_NEDSS_ENTRY_ID being called :"
					+ DELETE_ALERT_USER_FOR_NEDSS_ENTRY_ID);
			dbConnection = getConnection();
			preparedStmt = dbConnection
			.prepareStatement(DELETE_ALERT_USER_FOR_NEDSS_ENTRY_ID);
			preparedStmt.setLong(1, NedssEntryId.longValue());
			preparedStmt.executeUpdate();
			preparedStmt.executeUpdate();
		} catch (SQLException se) {
			logger.fatal("SQLException  = "+se.getMessage(), se);
			throw new NEDSSDAOSysException(
					"Error: SQLException while deleting\n" + se.getMessage());
		} finally {
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
	}

}
