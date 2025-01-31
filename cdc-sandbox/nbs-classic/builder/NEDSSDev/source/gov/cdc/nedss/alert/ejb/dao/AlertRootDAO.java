package gov.cdc.nedss.alert.ejb.dao;


import gov.cdc.nedss.alert.vo.AlertEmailMessageVO;
import gov.cdc.nedss.alert.vo.AlertLogVO;
import gov.cdc.nedss.alert.vo.AlertVO;
import gov.cdc.nedss.alert.vo.UserEmailVO;
import gov.cdc.nedss.exception.NEDSSSystemException;

import java.util.Collection;



public class AlertRootDAO {
	
	
	public Collection<Object> getAlertVOCollection(String conditionCd,String jurisdictionCd , String recordStatusCd,String typeCode,  boolean isadminUser, boolean isUICall)  throws NEDSSSystemException{
		AlertDAO alertDAO = new AlertDAO();
		Collection<Object> coll = alertDAO.getAlertCollection(conditionCd, jurisdictionCd,recordStatusCd, typeCode,  isadminUser, isUICall);
		return coll;
	}
	
	public void insertAlertVO(AlertVO alertVO) throws NEDSSSystemException{
		AlertDAO alertDAO = new AlertDAO();
		 alertDAO.setAlertVO(alertVO);
	}
	
	public void updateAlertVO(AlertVO alertVO, Long oldAlertUid) throws NEDSSSystemException{
		AlertDAO alertDAO = new AlertDAO();
		 alertDAO.updateAlertVO(alertVO, oldAlertUid);
	}
	
	public void deleteAlertVO(Long alertUid) throws NEDSSSystemException{
		AlertDAO alertDAO = new AlertDAO();
		 alertDAO.deletetAlertVO(alertUid);
	}
		
	public UserEmailVO getUserEmailVO( Long nedssEntryId)  throws NEDSSSystemException{
		UserEmailDAO userEmailDAO = new UserEmailDAO();
		UserEmailVO userEmailVO = userEmailDAO.getEmailAlertVO(nedssEntryId);
		return userEmailVO;
	}
	
	public void insertUserEmailVO(UserEmailVO userEmailVO) throws NEDSSSystemException{
		UserEmailDAO userEmailDAO = new UserEmailDAO();
		userEmailDAO.setAlertVO(userEmailVO);
	}
	
	public void updateUserEmailVO(UserEmailVO userEmailVO, Long nedssEntryId) throws NEDSSSystemException{
		UserEmailDAO userEmailDAO = new UserEmailDAO();
		userEmailDAO.updateAlertVO(userEmailVO,nedssEntryId);
		AlertDAO alertDAO = new AlertDAO();
		Collection<Object> coll = userEmailVO.getUserEmailDTCollection();
		if(coll==null || (coll!=null && coll.size()==0))
				alertDAO.removeAlertUserForNedssEntryUId(nedssEntryId);
	}
	
	public static void updateAlertEmailMessageVO(AlertEmailMessageVO alertEmailMessageVO) throws NEDSSSystemException{
		AlertEmailMessageDAO AlertEmailMessageDAO = new AlertEmailMessageDAO();
		AlertEmailMessageDAO.updateAlertEmailMessageVO(alertEmailMessageVO);
	}

	public Collection<Object> getAlertEmailMessageVOCollection() throws NEDSSSystemException{
		AlertEmailMessageDAO alertEmailMessageDAO = new AlertEmailMessageDAO();
		return alertEmailMessageDAO.getAlertEmailMessageVOCollection();
	}
	
	public static void  insertAlertEmailVO(AlertEmailMessageVO alertEmailMessageVO) throws NEDSSSystemException{
		AlertEmailMessageDAO alertEmailMessageDAO = new AlertEmailMessageDAO();
		 alertEmailMessageDAO.insertAlertEmailVO(alertEmailMessageVO);
	}
	
	public static void  insertEmailLogVO(AlertLogVO alertLogVO) throws NEDSSSystemException{
		AlertEmailMessageDAO alertEmailMessageDAO = new AlertEmailMessageDAO();
		 alertEmailMessageDAO.insertEmailLogVO(alertLogVO);
	}
	
	public static void resetQueue(){
		AlertEmailMessageDAO alertEmailMessageDAO = new AlertEmailMessageDAO();
		 alertEmailMessageDAO.resetQueue();
		
	}
	
}
