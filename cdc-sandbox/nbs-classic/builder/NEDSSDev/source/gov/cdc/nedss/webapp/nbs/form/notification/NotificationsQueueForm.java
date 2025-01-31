package gov.cdc.nedss.webapp.nbs.form.notification;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import gov.cdc.nedss.webapp.nbs.form.util.BaseForm;
import gov.cdc.nedss.webapp.nbs.action.notification.util.UpdatedNotificationsQueueUtil;

/**
 * This will serve as the base class for more specific notifications queue types.
 * Search filters common to all notification queues are handled here. These filters
 * help users to slice and dice notifications.
 * @author Karthik Muthukumaraswamy
 *
 */
public class NotificationsQueueForm extends BaseForm 
{
	UpdatedNotificationsQueueUtil notificationsQueueUtil = new UpdatedNotificationsQueueUtil();
	
	// private Map<Object,Object> answerMap = new HashMap<Object,Object>();
	
	// to hold all the search attributes
	Map<Object,Object> searchCriteriaArrayMap = new HashMap<Object,Object>();
	
	// to hold HTML select box options. This is a collection of DropDownCodeDT objects.
	private ArrayList<Object> jurisdictionOptions = new ArrayList<Object> ();
	private ArrayList<Object> submittedByOptions = new ArrayList<Object> ();
	private ArrayList<Object> caseStatusOptions = new ArrayList<Object> ();
	private ArrayList<Object> conditionOptions = new ArrayList<Object> ();
	private ArrayList<Object> patientNameOptions = new ArrayList<Object> ();
	private ArrayList<Object> notificationCodeOptions = new ArrayList<Object> ();
	private ArrayList<Object> recipientOptions = new ArrayList<Object> ();
	private ArrayList<Object> updateDateOptions = new ArrayList<Object> ();
	
	// to hold the list of notifications 
	private Collection<Object>  notifications;	

	// Helper methods //
	public void initializeDropDowns(ArrayList<Object> notificationColl)
	{
		jurisdictionOptions = notificationsQueueUtil.getJurisdictionOptions(notificationColl);
		submittedByOptions  = notificationsQueueUtil.getSubmittedByOptions(notificationColl);
		caseStatusOptions = notificationsQueueUtil.getCaseStatusOptions(notificationColl);
		conditionOptions = notificationsQueueUtil.getConditionOptions(notificationColl);
		patientNameOptions = notificationsQueueUtil.getPatientNameOptions(notificationColl);
		notificationCodeOptions = notificationsQueueUtil.getNotificationCodeOptions(notificationColl);
		recipientOptions = notificationsQueueUtil.getRecipientOptions(notificationColl);
		updateDateOptions = notificationsQueueUtil.getStartDateDropDownValues();
	}
	
	public ArrayList<Object> getJurisdictionOptions() {
		return jurisdictionOptions;
	}

	public ArrayList<Object> getSubmittedByOptions() {
		return submittedByOptions;
	}

	public ArrayList<Object> getCaseStatusOptions() {
		return caseStatusOptions;
	}

	public ArrayList<Object> getConditionOptions() {
		return conditionOptions;
	}

	public ArrayList<Object> getPatientNameOptions() {
		return patientNameOptions;
	}
	
	public ArrayList<Object> getNotificationCodeOptions() {
		return notificationCodeOptions;
	}

	public ArrayList<Object> getRecipientOptions() {
		return recipientOptions;
	}
	
	public ArrayList<Object> getUpdateDateOptions() {
		return updateDateOptions;
	}

	public String[] getAnswerArray(String key) {
		return (String[])searchCriteriaArrayMap.get(key);
	}

	public void setAnswerArray(String key, String[] answer) {
		if(answer.length > 0) {
			String [] answerList = new String[answer.length];
			boolean selected = false;
			for(int i=1; i<=answer.length; i++) {
				String answerTxt = answer[i-1];
				if(!answerTxt.equals("")) {
					selected = true;
					answerList[i-1] = answerTxt;
				}
			}
			if(selected)
				searchCriteriaArrayMap.put(key,answerList);
		}
	}
	
	
	public void setAnswerArrayText(String key, String answer) {
		if(answer!=null && answer.length() > 0) {
			String newKey = key+"_FILTER_TEXT";
				searchCriteriaArrayMap.put(newKey,answer);
		}
	}
	
	public Map<Object,Object> getSearchCriteriaArrayMap() {
		return searchCriteriaArrayMap;
	}

	public void setSearchCriteriaArrayMap(Map<Object,Object> searchCriteriaArrayMap) {
		this.searchCriteriaArrayMap = searchCriteriaArrayMap;
	}

	public void clearAll() {
		getAttributeMap().clear();
		searchCriteriaArrayMap = new HashMap<Object,Object>();
	}
	
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
	}
}