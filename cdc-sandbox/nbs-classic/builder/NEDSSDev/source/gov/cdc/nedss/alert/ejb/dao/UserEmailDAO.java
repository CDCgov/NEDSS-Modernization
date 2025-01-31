package gov.cdc.nedss.alert.ejb.dao;

import gov.cdc.nedss.alert.dt.UserEmailDT;
import gov.cdc.nedss.alert.vo.UserEmailVO;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.DataTables;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;



public class UserEmailDAO extends DAOBase {
	static final LogUtils logger = new LogUtils(UserEmailDAO.class.getName());
	private String SELECT_USER_EMAIL = "SELECT  nedss_entry_id \"nedssEntryId\", seq_nbr \"seqNbr\", email_id \"emailId\", add_time \"addTime\", last_chg_time \"lastChgTime\", record_status_cd \"recordStatusCd\", add_user_id \"addUserId\", last_chg_user_id \"lastChgUserId\" FROM "+ DataTables. USER_EMAIL +"  where nedss_entry_id=?" ;
	private String INSERT_USER_EMAIL = "INSERT INTO "+ DataTables. USER_EMAIL +" (nedss_entry_id, seq_nbr, email_id, add_time, last_chg_time, record_status_cd, add_user_id, last_chg_user_id) VALUES(?,?,?,?,?,?,?,?)";
	private String DELETE_USER_EMAIL = "DELETE FROM "+ DataTables. USER_EMAIL +" WHERE nedss_entry_id =?";
	public void setAlertVO(UserEmailVO userEmailVO){
		try{
			if(userEmailVO.getUserEmailDTCollection()!=null){
				Collection<Object> coll= userEmailVO.getUserEmailDTCollection();
				Iterator<Object> it = coll.iterator();
				while(it.hasNext()){
					UserEmailDT userEmailDT= (UserEmailDT)it.next();
					insertUserEmailDT(userEmailDT);
				}
			}
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}
	
	public void insertUserEmailDT(UserEmailDT userEmailDT)  throws NEDSSSystemException{
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		int resultCount = 0;
		logger.debug("INSERT_ALERT_USER=" + INSERT_USER_EMAIL);
		dbConnection = getConnection();
		try {
			preparedStmt = dbConnection.prepareStatement(INSERT_USER_EMAIL);
			int i = 1;
			preparedStmt.setLong(i++, userEmailDT.getNedssEntryId().longValue()); // 1
			preparedStmt.setInt(i++, userEmailDT.getSeqNbr().intValue()); // 2
			preparedStmt.setString(i++, userEmailDT.getEmailId()); // 3
			preparedStmt.setTimestamp(i++, userEmailDT.getAddTime()); // 4
			preparedStmt.setTimestamp(i++, userEmailDT.getLastChgTime()); // 5
			preparedStmt.setString(i++, userEmailDT.getRecordStatusCd()); //6
			preparedStmt.setLong(i++, userEmailDT.getAddUserId().longValue()); //7
			preparedStmt.setLong(i++, userEmailDT.getLastChgUserId().longValue()); // 8
			resultCount = preparedStmt.executeUpdate();
		} catch (SQLException sqlex) {
			logger.fatal("SQLException while inserting "
					+ "userEmailDT insertUserEmailDT:" + userEmailDT.toString(),
					sqlex);
			throw new NEDSSDAOSysException(sqlex.toString());
		} catch (Exception ex) {
			logger.fatal("Error while inserting into insertUserEmailDT, userEmailDT="
					+ userEmailDT.toString(), ex);
			throw new NEDSSSystemException(ex.toString());
		} finally {
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}

	}
	public UserEmailVO getEmailAlertVO(Long nedssEntryId) throws NEDSSSystemException {
		try{
			Collection<Object> userEmailDTCollection  = getUserEmailDTCollection(nedssEntryId);
			UserEmailVO userEmailVO= new UserEmailVO();
			userEmailVO.setUserEmailDTCollection(userEmailDTCollection);
			return userEmailVO;
		}catch(Exception ex){
			logger.fatal("Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}
		
		@SuppressWarnings("unchecked")
		public Collection<Object> getUserEmailDTCollection(Long nedssEntryId) throws NEDSSSystemException {
			logger.debug("SELECT_USER_EMAIL"+SELECT_USER_EMAIL);
			UserEmailDT userEmailDT = new UserEmailDT();
			ArrayList<Object>  userEmailDTCollection  = new ArrayList<Object> ();
			userEmailDTCollection.add(nedssEntryId);
			try {
				userEmailDTCollection  = (ArrayList<Object> ) preparedStmtMethod(userEmailDT,
						userEmailDTCollection,SELECT_USER_EMAIL, NEDSSConstants.SELECT);

			} catch (Exception ex) {
				logger.fatal("Exception in getUserEmailDTCollection:  ERROR = "
						+ ex.getMessage(), ex);
				throw new NEDSSSystemException(ex.toString());
			}
			return userEmailDTCollection;
		}
	

	public void updateAlertVO(UserEmailVO userEmailVO, Long nedssEntryUid) throws NEDSSSystemException {
		try{
			logger.debug("Exception in updateAlertVO:DEBUG nedssEntryUid = "+ nedssEntryUid);
			logger.debug("Exception in updateAlertVO:DEBUG getUserEmailDTCollection  = "+ userEmailVO.getUserEmailDTCollection());
			
			removeUserEmail(nedssEntryUid);
			if(userEmailVO!=null && userEmailVO.getUserEmailDTCollection()!=null)
			setAlertVO(userEmailVO);
		}catch(Exception ex){
			logger.fatal("Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}
	
	private void removeUserEmail(Long nedssEntryUid) throws NEDSSDAOSysException, NEDSSSystemException {

		PreparedStatement preparedStmt = null;
		Connection dbConnection = null;

		try {
			logger
			.debug("$$$$###Delete DELETE_USER_EMAIL being called :"
					+ DELETE_USER_EMAIL);
			dbConnection = getConnection();
			preparedStmt = dbConnection
			.prepareStatement(DELETE_USER_EMAIL);
			preparedStmt.setLong(1, nedssEntryUid.longValue());
			preparedStmt.executeUpdate();
		preparedStmt.executeUpdate();
		} catch (SQLException se) {
			logger.fatal("Exception  = "+se.getMessage(), se);
			throw new NEDSSDAOSysException(
					"Error: SQLException while deleting\n" + se.getMessage());
		} finally {
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
	}
}