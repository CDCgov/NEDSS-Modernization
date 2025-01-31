/**
 * 
 */
package gov.cdc.nedss.webapp.nbs.form.alert.alertAdmin;

import gov.cdc.nedss.reportadmin.dt.UserProfileDT;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.webapp.nbs.action.alert.alertAdmin.clientVO.AlertClientSeachParamsVO;
import gov.cdc.nedss.webapp.nbs.action.alert.alertAdmin.clientVO.AlertClientVO;

import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

/**
 * Description: Data holder for the UserAlert related pages
 * @author aperiaswamy
 *	2008
 *
 */

public class AlertUserForm extends ValidatorForm {
	private static final long serialVersionUID = 1L;

    private AlertClientVO alertClientVO = new AlertClientVO();
    private AlertClientSeachParamsVO searchParamsVO = new AlertClientSeachParamsVO();
    UserProfileDT userProfileDT = new UserProfileDT();
    private ArrayList<Object> errorList = new ArrayList<Object> ();
    private ArrayList<Object>  eventList = new ArrayList<Object> ();
    private ArrayList<Object>  jurisdictionList ;
	private ArrayList<Object>  conditionList ;
	private ArrayList<Object>  severityList ;
	private ArrayList<Object>  userList ;
	private ArrayList<Object>  selectedUserList ;
	private ArrayList<Object>  alertList ;
	private String actionMode;
	private String confirmationMessage ;
	private String[] alertUserIds;
	private String[] selectedAlertUserIds;
	/**
	 * 
	 */
	public AlertUserForm() {
		// TODO Auto-generated constructor stub
	}
	
	public AlertClientVO getAlertClientVO() {
		return alertClientVO;
	}
	public void setAlertClientVO(AlertClientVO alertClientVO) {
		this.alertClientVO = alertClientVO;
	}
	public void reset() {
		searchParamsVO = new AlertClientSeachParamsVO();
		alertClientVO = new AlertClientVO();
		errorList = new ArrayList<Object> ();
	}
	
	public ArrayList<Object> getJurisdictionList() {
		this.jurisdictionList =CachedDropDowns.getAlertJurisdictionList();    
		return jurisdictionList;
	}
	public void setJurisdictionList(ArrayList<Object> jurisdictionList) {
		this.jurisdictionList = jurisdictionList;
	}
	public ArrayList<Object> getConditionList() {
		this.conditionList = CachedDropDowns.getAllConditions();
		return conditionList;
	}
	public void setConditionList(ArrayList<Object> conditionList) {
		this.conditionList = conditionList;
	}
	
	public Object getCodedValue(String codesetNm) {
		return CachedDropDowns.getCodedValueForType(codesetNm);
	} 
	
	public ArrayList<Object> getUserList() {
		CachedDropDowns.resetCachedValues(NEDSSConstants.Users_With_Valid_Email);
		this.userList = CachedDropDowns.getUsersWithValidEmailLst();
		return userList;
	}

	public ArrayList<Object> getAlertList() {
		return alertList;
	}

	public void setAlertList(ArrayList<Object> alertList) {
		this.alertList = alertList;
	}

	public AlertClientSeachParamsVO getSearchParamsVO() {
		return searchParamsVO;
	}

	public void setSearchParamsVO(AlertClientSeachParamsVO searchParamsVO) {
		this.searchParamsVO = searchParamsVO;
	}

	public ArrayList<Object> getErrorList() {
		return errorList;
	}

	public void setErrorList(ArrayList<Object> errorList) {
		this.errorList = errorList;
	}

	public ArrayList<Object> getEventList() {
	   this.eventList = CachedDropDowns.getCodedValueForType("ALRT_PUBLIC_HEALTH_EVENT");
	   return eventList;
	}

	public void setEventList(ArrayList<Object> eventList) {
		this.eventList = eventList;
	}

	public String getActionMode() {
		return actionMode;
	}

	public void setActionMode(String actionMode) {
		this.actionMode = actionMode;
	}

	public String getConfirmationMessage() {
		return confirmationMessage;
	}

	public void setConfirmationMessage(String confirmationMessage) {
		this.confirmationMessage = confirmationMessage;
	}

	public String[] getAlertUserIds() {
		return alertUserIds;
	}

	public void setAlertUserIds(String[] alertUserIds) {
		this.alertUserIds = alertUserIds;
	}

	public String[] getSelectedAlertUserIds() {
		return selectedAlertUserIds;
	}

	public void setSelectedAlertUserIds(String[] selectedAlertUserIds) {
		this.selectedAlertUserIds = selectedAlertUserIds;
	}

	public ArrayList<Object> getSelectedUserList() {
		return selectedUserList;
	}

	public void setSelectedUserList(ArrayList<Object> selectedUserList) {
		this.selectedUserList = selectedUserList;
	}
	
}
