/**
 *
 */
package gov.cdc.nedss.webapp.nbs.action.alert.alertAdmin.clientVO;
import gov.cdc.nedss.alert.vo.AlertVO;
import gov.cdc.nedss.reportadmin.dt.UserProfileDT;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

/**
 * Title:        AlertClientVO
 * Company:      CSC
 * @author aperiaswamy
 *
 */
public class AlertClientVO {



	private String event_desc;
	private String condition_desc;
	private String jurisdiction_desc;
	private String severity_desc;
	private String event_code;
	private String condition_code;
	private String jurisdiction_code;
	private String severity_code;
	private Map<Object,Object> userSelected = new HashMap<Object,Object>();
	private Long nedssEntryId;
	private Timestamp lastChgTime;
	private AlertVO  alertVO = new AlertVO() ;
	private Long alertUID;
	UserProfileDT userProfileDT = new UserProfileDT();
	private String[] alertUserIds;
	private String[] selectedAlertUserIds;
	private String fullName ;
	private ArrayList<Object> selectedUserIds = new ArrayList<Object> ();
	private String oldSeverityCode;
	private String alertExtendedMessage;



	public AlertVO getAlertVO() {
		return alertVO;
	}
	public void setAlertVO(AlertVO alertVO) {
		this.alertVO = alertVO;
	}

	public void reset(ActionMapping mapping, HttpServletRequest request) {

	}

	public Long getNedssEntryId() {
		return nedssEntryId;
	}
	public void setNedssEntryId(Long nedssEntryId) {
		this.nedssEntryId = nedssEntryId;
	}

	public Long getAlertUID() {
		return alertUID;
	}
	public void setAlertUID(Long alertUID) {
		this.alertUID = alertUID;
	}

	public String getEvent_desc() {
		return event_desc;
	}
	public void setEvent_desc(String event_desc) {
		this.event_desc = event_desc;
	}
	public String getCondition_desc() {
		return condition_desc;
	}
	public void setCondition_desc(String condition_desc) {
		this.condition_desc = condition_desc;
	}
	public String getJurisdiction_desc() {
		return jurisdiction_desc;
	}
	public void setJurisdiction_desc(String jurisdiction_desc) {
		this.jurisdiction_desc = jurisdiction_desc;
	}
	public String getSeverity_desc() {
		return severity_desc;
	}
	public void setSeverity_desc(String severity_desc) {
		this.severity_desc = severity_desc;
	}
	public String getEvent_code() {
		return event_code;
	}
	public void setEvent_code(String event_code) {
		this.event_code = event_code;
	}
	public String getCondition_code() {
		return condition_code;
	}
	public void setCondition_code(String condition_code) {
		this.condition_code = condition_code;
	}
	public String getJurisdiction_code() {
		return jurisdiction_code;
	}
	public void setJurisdiction_code(String jurisdiction_code) {
		this.jurisdiction_code = jurisdiction_code;
	}
	public String getSeverity_code() {
		return severity_code;
	}
	public void setSeverity_code(String severity_code) {
		this.severity_code = severity_code;
	}
	public Map<Object,Object> getUserSelected() {
		return userSelected;
	}
	public void setUserSelected(Map<Object, Object> userSelected) {
		this.userSelected = userSelected;
	}
	public String[] getUserArray(String key) {
		return (String[])userSelected.get(key);
	}

	public void setUserArray(String key, String[] id) {
		userSelected.put(key,id );
	}
	public String getAlertUser(String key) {
		return (String)userSelected.get(key);
	}

	public void setAlertUser(String key, String answer) {
		userSelected.put(key,answer );
	}
	public String[] getUserId() {
		return alertUserIds;
	}
	public void setUserId(String[] userId) {
		this.alertUserIds = userId;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String[] getSelectedAlertUserIds() {
		return selectedAlertUserIds;
	}
	public void setSelectedAlertUserIds(String[] selectedAlertUserIds) {
		this.selectedAlertUserIds = selectedAlertUserIds;
	}
	public ArrayList<Object> getSelectedUserIds() {
		return selectedUserIds;
	}
	public void setSelectedUserIds(ArrayList<Object> selectedUserIds) {
		this.selectedUserIds = selectedUserIds;
	}
	public Timestamp getLastChgTime() {
		return lastChgTime;
	}
	public void setLastChgTime(Timestamp lastChgTime) {
		this.lastChgTime = lastChgTime;
	}
	public String getOldSeverityCode() {
		return oldSeverityCode;
	}
	public void setOldSeverityCode(String oldSeverityCode) {
		this.oldSeverityCode = oldSeverityCode;
	}
	public String getAlertExtendedMessage() {
		return alertExtendedMessage;
	}
	public void setAlertExtendedMessage(String alertExtendedMessage) {
		this.alertExtendedMessage = alertExtendedMessage;
	}

}
