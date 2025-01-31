/**
 *
 */
package gov.cdc.nedss.webapp.nbs.action.alert.alertAdmin.util;

import gov.cdc.nedss.alert.dt.AlertDT;
import gov.cdc.nedss.alert.dt.AlertUserDT;
import gov.cdc.nedss.alert.vo.AlertVO;
import gov.cdc.nedss.reportadmin.dt.UserProfileDT;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.webapp.nbs.action.alert.alertAdmin.clientVO.AlertClientSeachParamsVO;
import gov.cdc.nedss.webapp.nbs.action.alert.alertAdmin.clientVO.AlertClientVO;
import gov.cdc.nedss.webapp.nbs.form.alert.alertAdmin.AlertUserForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
/**
 * @author aperiaswamy
 *2008
 */
public class AlertUserUtil {
	static final LogUtils logger = new LogUtils(AlertUserUtil.class.getName());
	static final String VIEW = "VIEW";
	static final String EDIT = "EDIT";
	static final String DELETE = "DELETE";

	public static void loadUserAlert(AlertUserForm form, HttpServletRequest request){
		 	ArrayList<?> proxyVOList = new ArrayList<Object> ();
		 	form.setConfirmationMessage(new String());
		 	AlertClientVO alertClientVO =new AlertClientVO();
		 	HttpSession session = request.getSession();
		 	form.setConfirmationMessage(new String());
		 	form.setSelectedUserList(new ArrayList<Object>());
		 	CachedDropDownValues cachedValues = new CachedDropDownValues() ;
		 	String event_cd = form.getSearchParamsVO().getEvent_CD()==null ?"":form.getSearchParamsVO().getEvent_CD();
			String condition_cd = form.getSearchParamsVO().getCondition_CD()== null?"":form.getSearchParamsVO().getCondition_CD();
			String jurisdiction_cd = form.getSearchParamsVO().getJurisdiction_CD()==null?"":form.getSearchParamsVO().getJurisdiction_CD();


			if(event_cd.trim().length()>0 && condition_cd.trim().length()>0 && jurisdiction_cd.trim().length()>0 ){
			proxyVOList = getProxyObject(condition_cd,jurisdiction_cd,NEDSSConstants.RECORD_STATUS_ACTIVE,event_cd,session);
			}
			else{
				proxyVOList = new ArrayList<Object> ();
				request.setAttribute("error", "Please enter required field(s) and try again");
				session.setAttribute("searchParamsVO", form.getSearchParamsVO());
			}

			if (proxyVOList != null && proxyVOList.size() > 0) {
				Iterator<?> it = proxyVOList.iterator();
				alertClientVO = null;
				while (it.hasNext()) {
					alertClientVO =new AlertClientVO();
					AlertVO alertVO = (AlertVO)it.next();
					alertClientVO =prepareUIAlert(alertVO, alertClientVO);
					form.setAlertClientVO(alertClientVO);
					form.setSelectedUserList(alertClientVO.getSelectedUserIds());
					form.setActionMode("Update");
				}
				request.setAttribute("alertClientVO", alertClientVO);
			}else if(event_cd.trim().length()>0 && condition_cd.trim().length()>0 && jurisdiction_cd.trim().length()>0 && proxyVOList.size()<=0 ){
				form.setConfirmationMessage("No Alerts Found For The Selected Search Criteria");
				alertClientVO.setCondition_code(condition_cd);
				alertClientVO.setJurisdiction_code(jurisdiction_cd);
				alertClientVO.setEvent_code(event_cd);
				form.setAlertClientVO(alertClientVO);
				form.setActionMode("Add");
			}
		 session.setAttribute("searchParamsVO", form.getSearchParamsVO());
	}

	public static ArrayList<?> getProxyObject(String condition,String jurisdiction,String recordStatusCd,String event, HttpSession session) {

		ArrayList<?> proxyList = null;
		MainSessionCommand msCommand = null;

		try {
			NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session.getAttribute("NBSSecurityObject");
			String sBeanJndiName = JNDINames.ALERT_EJB;
			String sMethod = "getAlertVOCollection";
			Object[] oParams = new Object[] {condition,jurisdiction,NEDSSConstants.RECORD_STATUS_ACTIVE,event};
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(session);
			ArrayList<?> arr = (ArrayList<?> )msCommand.processRequest(sBeanJndiName, sMethod, oParams);
			Object obj= arr.get(0);
			proxyList= (ArrayList<?>)obj;
          	} catch (Exception ex) {
			logger.fatal("getProxyObject: ", ex);
		}

		return proxyList;
	}
	public static AlertClientVO prepareUIAlert(AlertVO alertVO, AlertClientVO alertClientVO){
		AlertDT alertDT = null ;
		alertDT = alertVO.getAlertDT();
		AlertUserDT alertUserDT = null;
		UserProfileDT userProfileDT = null;
		ArrayList<Object> alertUsersList  = new ArrayList<Object> ();
		ArrayList<Object> alertUsers  = new ArrayList<Object> ();
		CachedDropDowns.resetCachedValues(NEDSSConstants.Users_With_Valid_Email);
		ArrayList<Object>  usersList = CachedDropDowns.getUsersWithValidEmailLst();
		CachedDropDownValues cachedValues = new CachedDropDownValues() ;
		if(alertDT !=null){
		alertClientVO.setAlertUID(alertDT.getAlertUid());
		alertClientVO.setEvent_code(alertDT.getTypeCd());
		alertClientVO.setCondition_code(alertDT.getConditionCd());
		alertClientVO.setJurisdiction_code(alertDT.getJurisdictionCd());
		alertClientVO.setSeverity_code(alertDT.getSeverityCd());
		alertClientVO.setOldSeverityCode(alertDT.getSeverityCd());
		alertClientVO.setAlertExtendedMessage(alertDT.getAlertMsgTxt());
		alertClientVO.setCondition_desc((cachedValues.getConditionDesc(alertDT.getConditionCd()==null ? "":alertDT.getConditionCd())));
		alertClientVO.setJurisdiction_desc(cachedValues.getJurisdictionDesc(alertDT.getJurisdictionCd()== null ? "":alertDT.getJurisdictionCd()));
		alertClientVO.setSeverity_desc(cachedValues.getCodeDescTxt(alertDT.getSeverityCd()== null ? "":alertDT.getSeverityCd()));
		alertClientVO.setEvent_desc(cachedValues.getCodeDescTxt(alertDT.getTypeCd()== null ? "":alertDT.getTypeCd()));
		alertClientVO.setAlertUID(alertDT.getAlertUid());
		alertClientVO.setLastChgTime(alertDT.getLastChgTime());
		}
		alertUsers = (ArrayList<Object> )alertVO.getAlertUserDTCollection();
		if (alertUsers != null ) {
			for(int i = 0 ; i<alertUsers.size() ; i++){
				alertUserDT = new AlertUserDT();
				alertUserDT = (AlertUserDT)alertUsers.get(i);
				String neddsID = alertUserDT.getNedssEntryId().toString();
				userProfileDT = new UserProfileDT();
				userProfileDT.setNEDSS_ENTRY_ID(neddsID);

					for(int j = 0 ; j<usersList.size() ; j++){
						UserProfileDT temp = new UserProfileDT();
						temp = (UserProfileDT)usersList.get(j);
						if(userProfileDT.getNEDSS_ENTRY_ID_s().equals(temp.getNEDSS_ENTRY_ID_s())){
							alertUsersList.add(temp);
						};

					}
			}
			alertClientVO.setSelectedUserIds(alertUsersList);
		}
		return alertClientVO;
	}
	public static void loadAlertDetails(AlertUserForm form, HttpServletRequest request){
		 form.setErrorList(new ArrayList<Object>());
		 AlertClientSeachParamsVO searchParamsVO =(AlertClientSeachParamsVO)request.getSession().getAttribute("searchParamsVO");
		 String alertUID = request.getParameter("aUID");
		 Long uid = new Long(alertUID);
		 HttpSession session = request.getSession();
		 ArrayList<Object> alertList = form.getAlertList();
		 AlertClientVO alertClientVO = null;
		 if (alertList != null) {
				Iterator<Object> it = alertList.iterator();
				while (it.hasNext()) {
					alertClientVO =new AlertClientVO();
					alertClientVO = (AlertClientVO)it.next();
					if(uid == alertClientVO.getAlertUID());
					break;
				}
			}
		 form.setAlertClientVO(alertClientVO);
		 session.setAttribute("searchParamsVO", searchParamsVO);
	}


	public static void addNewAlert(AlertUserForm form, HttpServletRequest request){

		AlertVO alertVO  =new AlertVO();
		AlertDT alertDT  = null;
		AlertUserDT alertUserDT = null;
		form.setConfirmationMessage(new String());
		List<Object> alertUserDTList = new ArrayList<Object> ();
		ArrayList<Object> alertList = new ArrayList<Object> ();

		AlertClientSeachParamsVO searchParamsVO =(AlertClientSeachParamsVO)request.getSession().getAttribute("searchParamsVO");
		AlertClientVO alertClientVO = form.getAlertClientVO();
		HttpSession session = request.getSession();

		if(searchParamsVO !=null ){
			NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session.getAttribute("NBSSecurityObject");
			String userId = nbsSecurityObj.getTheUserProfile().getTheUser().getEntryID();
			String[] alertUserIds = form.getSelectedAlertUserIds();
			alertDT = new AlertDT();
			alertDT.setTypeCd(alertClientVO.getEvent_code()== null ? "":alertClientVO.getEvent_code());
			alertDT.setConditionCd(alertClientVO.getCondition_code()== null ? "":alertClientVO.getCondition_code());
			alertDT.setSeverityCd(alertClientVO.getSeverity_code()== null ? "":alertClientVO.getSeverity_code());
			alertDT.setAlertMsgTxt(alertClientVO.getAlertExtendedMessage()== null ? "":alertClientVO.getAlertExtendedMessage());
			alertDT.setJurisdictionCd(alertClientVO.getJurisdiction_code()== null ? "":alertClientVO.getJurisdiction_code());
			alertDT.setAddUserId(Long.valueOf(userId));
			alertDT.setAddTime(new Timestamp(new Date().getTime()));
			alertDT.setLastChgUserId(Long.valueOf(userId));
			alertDT.setLastChgTime(new Timestamp(new Date().getTime()));
			alertDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
			alertVO.setAlertDT(alertDT);
			if(alertUserIds !=null){
				for(int i = 0 ; i<alertUserIds.length ; i++){
				alertUserDT = new AlertUserDT();
				String neddsID = alertUserIds[i];
				alertUserDT.setNedssEntryId(Long.valueOf(neddsID));
				alertUserDTList.add(alertUserDT);
				}
			}
			alertVO.setAlertUserDTCollection(alertUserDTList);
			alertClientVO= insertAlert(alertVO,session,alertDT, request);
			form.setAlertClientVO(alertClientVO);
			loadUserAlert(form,request);

			form.setConfirmationMessage("New Alert Added Successfully");
			}else{
				alertList = new ArrayList<Object> ();
				request.setAttribute("error", "Search Parameters Must Match With New Alert");
		    }


	    session.setAttribute("searchParamsVO", searchParamsVO);

	}

	public static AlertClientVO insertAlert(AlertVO alertvo , HttpSession session ,AlertDT alertDT,HttpServletRequest request) {
		MainSessionCommand msCommand = null;
		ArrayList<?> newAlert = null;
		ArrayList<Object> alertList = new ArrayList<Object> ();
		AlertClientVO alertClientVO = new AlertClientVO();
		AlertClientSeachParamsVO searchParamsVO =(AlertClientSeachParamsVO)request.getSession().getAttribute("searchParamsVO");
		try {
			NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session.getAttribute("NBSSecurityObject");
			String sBeanJndiName = JNDINames.ALERT_EJB;
			String sMethod = "insertAlertVO";
			Object[] oParams = new Object[] {alertvo};
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(session);
			ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
			newAlert = getProxyObject(searchParamsVO.getCondition_CD(), searchParamsVO.getJurisdiction_CD(),NEDSSConstants.RECORD_STATUS_ACTIVE, searchParamsVO.getEvent_CD(),session);
			AlertVO newAlertVO =(AlertVO)newAlert.get(0);
			alertClientVO =prepareUIAlert(newAlertVO,alertClientVO);

          	} catch (Exception ex) {
			logger.fatal("insertAlert: ", ex);
		}
		return alertClientVO ;
	}



	public static void deleteAlert(AlertUserForm form, HttpServletRequest request){
		Long AlertUID = form.getAlertClientVO().getAlertUID();
		form.setConfirmationMessage(new String());
		MainSessionCommand msCommand = null;
		HttpSession session = request.getSession();
		boolean isValidAlert = true;
		AlertClientSeachParamsVO searchParamsVO =(AlertClientSeachParamsVO)request.getSession().getAttribute("searchParamsVO");
		AlertClientVO alertClientVO = form.getAlertClientVO();

		isValidAlert = isValidAlert( form ,  isValidAlert);
		if(isValidAlert == true){
			try {
				NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session.getAttribute("NBSSecurityObject");
				String sBeanJndiName = JNDINames.ALERT_EJB;
				String sMethod = "deleteAlertVO";
				Object[] oParams = new Object[] {AlertUID};
				MainSessionHolder holder = new MainSessionHolder();
				msCommand = holder.getMainSessionCommand(session);
				ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
				//loadUserAlert(form,request);
				form.setAlertClientVO(new AlertClientVO());
				form.setSelectedUserList(new ArrayList<Object>());
				form.setActionMode(" ");
				form.setConfirmationMessage("Alert Deleted Successfully");
	          	} catch (Exception ex) {
				logger.fatal("deleteAlert: ", ex);
			}
		}else{
			 request.setAttribute("error", "Please Update Selected Alert Before Delete");
			 form.setActionMode("Validate");
		}

	}

	public static void updateAlert(AlertVO alertVO,Long alertUID, HttpServletRequest request){

		 MainSessionCommand msCommand = null;
		 HttpSession session = request.getSession();
			try {
				NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session.getAttribute("NBSSecurityObject");
				String sBeanJndiName = JNDINames.ALERT_EJB;
				String sMethod = "updateAlertVO";
				Object[] oParams = new Object[] {alertVO,alertUID};
				MainSessionHolder holder = new MainSessionHolder();
				msCommand = holder.getMainSessionCommand(session);
				ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
	          	} catch (Exception ex) {
				logger.fatal("updateAlert: ", ex);
			}


	}
	public static void loadUpdateAlert(AlertUserForm form, HttpServletRequest request){

		AlertVO alertVO  =new AlertVO();
		AlertDT alertDT  = null;
		AlertUserDT alertUserDT = null;
		List<Object> alertUserDTList = new ArrayList<Object> ();
		form.setConfirmationMessage(new String());
		HttpSession session = request.getSession();
		NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session.getAttribute("NBSSecurityObject");
		String userId = nbsSecurityObj.getTheUserProfile().getTheUser().getEntryID();
		AlertClientVO alertClientVO = form.getAlertClientVO();
		AlertClientSeachParamsVO searchParamsVO =(AlertClientSeachParamsVO)request.getSession().getAttribute("searchParamsVO");
		Long alertUID = alertClientVO.getAlertUID();
		String[] alertUserIds = form.getSelectedAlertUserIds();
		if(alertClientVO != null){
					alertDT = new AlertDT();
					alertDT.setTypeCd(alertClientVO.getEvent_code()== null ? "":alertClientVO.getEvent_code());
					alertDT.setConditionCd(alertClientVO.getCondition_code()== null ? "":alertClientVO.getCondition_code());
					alertDT.setSeverityCd(alertClientVO.getSeverity_code()== null ? "":alertClientVO.getSeverity_code());
					alertDT.setAlertMsgTxt(alertClientVO.getAlertExtendedMessage()== null ? "":alertClientVO.getAlertExtendedMessage());
					alertDT.setJurisdictionCd(alertClientVO.getJurisdiction_code()== null ? "":alertClientVO.getJurisdiction_code());
					alertDT.setAddUserId(Long.valueOf(userId));
					alertDT.setAddTime(new Timestamp(new Date().getTime()));
					alertDT.setLastChgUserId(Long.valueOf(userId));
					alertDT.setLastChgTime(new Timestamp(new Date().getTime()));
					alertDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
					alertDT.setAlertUid(alertUID);
					alertVO.setAlertDT(alertDT);
					if(alertUserIds !=null && alertUserIds.length >0){
							for(int i = 0 ; i<alertUserIds.length ; i++){
								alertUserDT = new AlertUserDT();
								String neddsID = alertUserIds[i];
								alertUserDT.setNedssEntryId(Long.valueOf(neddsID));
								alertUserDTList.add(alertUserDT);
							}
					}

					alertVO.setAlertUserDTCollection(alertUserDTList);
					updateAlert(alertVO,alertUID, request);
					loadUserAlert(form,request);
					form.setConfirmationMessage("Alert Updated Successfully");
		}else{
			request.setAttribute("error", "Please Select Valid Condition,Jurisdiction,Public Health Event");
		}

	}


	public static void sendSimulate(AlertUserForm form, HttpServletRequest request){
		AlertClientSeachParamsVO searchParamsVO =(AlertClientSeachParamsVO)request.getSession().getAttribute("searchParamsVO");
		AlertClientVO alertClientVO = form.getAlertClientVO();
		boolean isValidAlert = true;
		isValidAlert = isValidAlert( form ,  isValidAlert);
		HttpSession session = request.getSession();
		MainSessionCommand msCommand = null;
		if(isValidAlert == true){
		 try {
				NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session.getAttribute("NBSSecurityObject");
				String sBeanJndiName = JNDINames.ALERT_EJB;
				String sMethod = "sendSimulatedMessage";
				Object[] oParams = new Object[] {searchParamsVO.getCondition_CD(),searchParamsVO.getJurisdiction_CD(),"simulate",searchParamsVO.getEvent_CD(),alertClientVO.getLastChgTime()};
				MainSessionHolder holder = new MainSessionHolder();
				msCommand = holder.getMainSessionCommand(session);
				ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
				loadUserAlert(form,request);
				form.setConfirmationMessage("Alert Simulated Successfully");
	          	} catch (Exception ex) {
				logger.fatal("deleteAlert: ", ex);
			}

		}else{
				request.setAttribute("error", "Please Update Selected Alert Before Simulate");
				form.setActionMode("Validate");
		}
	}
	public static boolean isValidAlert(AlertUserForm form , boolean isValidAlert){

		     String oldSeverityCode =  form.getAlertClientVO().getOldSeverityCode();
		     String severityCode = form.getAlertClientVO().getSeverity_code();
		     String[] selectedUsers = form.getSelectedAlertUserIds();
		     ArrayList<Object> updatedUsersList = form.getSelectedUserList();
		     ArrayList<Object> validatedUserList = new ArrayList<Object> ();
		     ArrayList<Object> modifiedUIList = new ArrayList<Object> ();
		     CachedDropDowns.resetCachedValues(NEDSSConstants.Users_With_Valid_Email);
			 ArrayList<Object>  usersList = CachedDropDowns.getUsersWithValidEmailLst();
		    if (!oldSeverityCode.equalsIgnoreCase(severityCode)){
		    	isValidAlert = false;
		     }
		    if(selectedUsers.length != updatedUsersList.size()){
		    	isValidAlert = false;
		    	modifiedUIList = createUserList(selectedUsers,usersList);
				form.setSelectedUserList(modifiedUIList);
		    	return isValidAlert;
		    }
			if(selectedUsers.length == updatedUsersList.size()){
				validatedUserList = 	createUserList(selectedUsers,updatedUsersList);
					if( validatedUserList.size() != selectedUsers.length){
						isValidAlert = false;
						modifiedUIList = createUserList(selectedUsers,usersList);
						form.setSelectedUserList(modifiedUIList);
					}
			}

				return isValidAlert ;
	}

	public  static ArrayList<Object>   createUserList (String [] selectedUsers, ArrayList<Object> usersList){
		ArrayList<Object> modifiedUIList = new ArrayList<Object> ();
		for(int i = 0 ; i<selectedUsers.length ; i++){
			String neddsID = selectedUsers[i];
			UserProfileDT userProfileDT = new UserProfileDT();
			userProfileDT.setNEDSS_ENTRY_ID(neddsID);
			for(int j = 0 ; j<usersList.size() ; j++){
					UserProfileDT temp = new UserProfileDT();
					temp = (UserProfileDT)usersList.get(j);
					if(userProfileDT.getNEDSS_ENTRY_ID_s().equals(temp.getNEDSS_ENTRY_ID_s())){
						modifiedUIList.add(temp);
					};

				}

		}
		return modifiedUIList ;
	}


}
