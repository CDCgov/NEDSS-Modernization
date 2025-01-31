package gov.cdc.nedss.webapp.nbs.form.decisionsupportmanagement;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import gov.cdc.nedss.webapp.nbs.action.decisionsupportmanagement.DecisionSupportClientVO.ActivityLogClientVO;
import gov.cdc.nedss.webapp.nbs.action.decisionsupportmanagement.util.DsmActivityLogUtil;
import gov.cdc.nedss.webapp.nbs.action.util.NBSPageConstants;
import gov.cdc.nedss.webapp.nbs.action.util.QueueUtil;
import gov.cdc.nedss.webapp.nbs.form.util.BaseForm;

public class DsmActivityLogForm extends BaseForm {

	private static final long serialVersionUID = 1L;
	Map<Object, Object> searchMap = new HashMap<Object, Object>();
	Map<Object, Object> searchCriteriaArrayMap = new HashMap<Object, Object>();
	DsmActivityLogUtil util = new DsmActivityLogUtil();
	private ArrayList<Object> processedTime = new ArrayList<Object>();
	private ArrayList<Object> eventID = new ArrayList<Object>();
	private ArrayList<Object> action = new ArrayList<Object>();
	private ArrayList<Object> algorithmName = new ArrayList<Object>();
	private ArrayList<Object> status = new ArrayList<Object>();
	private ArrayList<Object> exceptiontext = new ArrayList<Object>();
	private ArrayList<Object> messageId = new ArrayList<Object>();
	private ArrayList<Object> entityNm = new ArrayList<Object>();
	private ArrayList<Object> accnNbr = new ArrayList<Object>();
	private ArrayList<Object> srcName = new ArrayList<Object>();
	private ArrayList<Object> businessObjLocalId = new ArrayList<Object>();
	private ArrayList<Object> manageList = new ArrayList<Object>();
	public ArrayList<Object> getManageList() {
		return manageList;
	}

	public void setManageList(ArrayList<Object> manageList) {
		this.manageList = manageList;
	}

	Object oldDT = new Object();
	private String returnToLink;
	private String docType;
	Object selection = new Object();
	private ActivityLogClientVO activityLogClientVO = new ActivityLogClientVO();

	public void initializeDropDowns(ArrayList<Object> activityLogColl) throws Exception {
		QueueUtil queueUtil = new QueueUtil();
		processedTime = queueUtil.getStartDateDropDownValues();
		//eventID = util.getEventIDDropDowns(activityLogColl);
		action = util.getLogActionNmDropDowns(activityLogColl);
		algorithmName = util.getLogAlgorithmNmDropDowns(activityLogColl);
		status = util.getLogStatusDropDowns(activityLogColl);
		exceptiontext = util.getExceptionTextDropDowns(activityLogColl);
	}

	public void clearSelections() {
		this.setSearchMap(new HashMap<Object, Object>());
		this.activityLogClientVO = new ActivityLogClientVO();
		this.setAttributeMap(new HashMap<Object, Object>());
		this.setActionMode(null);
		this.setReturnToLink(null);
	}
	
	public void setAnswerArrayText(String key, String answer) {
		if(answer!=null && answer.length() > 0) {
			String newKey = key+"_FILTER_TEXT";
				searchCriteriaArrayMap.put(newKey,answer);
		}
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public Map<Object, Object> getSearchMap() {
		return searchMap;
	}

	public void setSearchMap(Map<Object, Object> searchMap) {
		this.searchMap = searchMap;
	}

	public void setSearchCriteria(String key, String answer) {
		searchMap.put(key, answer);
	}

	public String getSearchCriteria(String key) {
		return (String) searchMap.get(key);
	}

	public String getReturnToLink() {
		return returnToLink;
	}

	public void setReturnToLink(String returnToLink) {
		this.returnToLink = returnToLink;
	}

	public void reset(ActionMapping mapping, HttpServletRequest request) {
		String resetIndicator = request.getParameter("context");
		setManageList(new ArrayList<Object>());
		if(resetIndicator != null && resetIndicator.equals("ReturnToManage"))
		{
			return;
		}
		super.reset(mapping, request);
		this.activityLogClientVO = new ActivityLogClientVO();
	}

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		return validateSearchCriteria(activityLogClientVO);
	}

	private ActionErrors validateSearchCriteria(ActivityLogClientVO activityLogClientVO2){
		ActionErrors errors = new ActionErrors();
		String[] processStatus = activityLogClientVO.getProcessStatus();
		String processingDateFrom = activityLogClientVO.getProcessingDateFrom();
		String processingDateTo = activityLogClientVO.getProcessingDateTo();
		String processingTimeFrom = activityLogClientVO.getProcessingTimeFrom();
		String processingTimeTo = activityLogClientVO.getProcessingTimeTo();
		
		if (processingDateFrom == null || processingDateFrom.equals(""))
			errors.add(NBSPageConstants.ERROR_MESSAGES_PROPERTY,
					new ActionMessage(
							NBSPageConstants.FIELD_REQUIRED_ENTER_KEY,
							"From Date"));
		else if (!isDateValid(processingDateFrom))
				errors.add(NBSPageConstants.ERROR_MESSAGES_PROPERTY,
						new ActionMessage(NBSPageConstants.DATE_FORMAT_KEY,
								"From Date"));
		if (processingDateTo == null || processingDateTo.equals(""))
			errors.add(NBSPageConstants.ERROR_MESSAGES_PROPERTY,
					new ActionMessage(
							NBSPageConstants.FIELD_REQUIRED_ENTER_KEY,
							"To Date"));
		else if (!isDateValid(processingDateTo))
			errors.add(NBSPageConstants.ERROR_MESSAGES_PROPERTY,
					new ActionMessage(NBSPageConstants.DATE_FORMAT_KEY,
							"To Date"));
		if (processStatus == null || processStatus.length == 0)
			errors.add(NBSPageConstants.ERROR_MESSAGES_PROPERTY,
					new ActionMessage(
							NBSPageConstants.FIELD_REQUIRED_SELECT_KEY,
							"status"));
		if (processingTimeFrom != null && !processingTimeFrom.equals("")&& !isTimeValid(processingTimeFrom))
			errors.add(NBSPageConstants.ERROR_MESSAGES_PROPERTY,
					new ActionMessage(NBSPageConstants.TIME_FORMAT_KEY,
							"From Time"));
		if (processingTimeTo != null && !processingTimeTo.equals("") && !isTimeValid(processingTimeTo))
			errors.add(NBSPageConstants.ERROR_MESSAGES_PROPERTY,
					new ActionMessage(NBSPageConstants.TIME_FORMAT_KEY,
							"To Time"));
		
		String myFormatString = "MM/dd/yyyy"; 
		SimpleDateFormat df = new SimpleDateFormat(myFormatString);

		try {
			Date dateFrom = df.parse(processingDateFrom);
			Date dateTo = df.parse(processingDateTo);
			
			if (dateFrom.after(dateTo))
			{
				errors.add(NBSPageConstants.ERROR_MESSAGES_PROPERTY,
						new ActionMessage(NBSPageConstants.TIME_COMPARE_KEY,
								"From Date", "To Date"));
			}
		} catch (ParseException e) {
			// input date validation have been done, so no chance to have this exception.
		}

		return errors;	
	}

	public boolean isDateValid(String date)
  {
		boolean isDateValidFlag = true;
		SimpleDateFormat sdfValidationCheck = new SimpleDateFormat("MM/dd/yyyy");		
		try
		{
			Calendar calender = Calendar.getInstance();
		    calender.setTime(sdfValidationCheck.parse(date));
		     
		    if( calender.get(Calendar.YEAR) < 1000 ) {
		    	  isDateValidFlag = false;
	        }
		    
		}
		catch (ParseException e)
		{
			isDateValidFlag= false;
		}
		return isDateValidFlag;
		
	}

	public boolean isTimeValid(String inputString) {
		SimpleDateFormat format = new java.text.SimpleDateFormat("HH:mm");
		try {
			format.parse(inputString);
		    Pattern p = Pattern.compile("([01]?[0-9]|2[0-3]):[0-5][0-9]");
		    return p.matcher(inputString).matches();
		} catch (ParseException e) {
			return false;
		}
	}

	public Object getOldDT() {
		return oldDT;
	}

	public void setOldDT(Object oldDT) {
		this.oldDT = oldDT;
	}

	public Object getSelection() {
		return selection;
	}

	public void setSelection(Object selection) {
		this.selection = selection;
	}

	public void clearAll() {
		getAttributeMap().clear();
		searchCriteriaArrayMap = new HashMap<Object, Object>();
	}

	public Map<Object, Object> getSearchCriteriaArrayMap() {
		return searchCriteriaArrayMap;
	}

	public void setSearchCriteriaArrayMap(
			Map<Object, Object> searchCriteriaArrayMap) {
		this.searchCriteriaArrayMap = searchCriteriaArrayMap;
	}

	public String[] getAnswerArray(String key) {
		return (String[]) searchCriteriaArrayMap.get(key);
	}

	public void setAnswerArray(String key, String[] answer) {
		if (answer.length > 0) {
			String[] answerList = new String[answer.length];
			boolean selected = false;
			for (int i = 1; i <= answer.length; i++) {
				String answerTxt = answer[i - 1];
				if (!answerTxt.equals("")) {
					selected = true;
					answerList[i - 1] = answerTxt;
				}
			}
			if (selected)
				searchCriteriaArrayMap.put(key, answerList);
		}
	}

	public ArrayList<Object> getProcessedTime() {
		return processedTime;
	}

	public void setProcessedTime(ArrayList<Object> processedTime) {
		this.processedTime = processedTime;
	}

	public ArrayList<Object> getEventID() {
		return eventID;
	}

	public void setEventID(ArrayList<Object> eventID) {
		this.eventID = eventID;
	}

	public ArrayList<Object> getAction() {
		return action;
	}

	public void setAction(ArrayList<Object> action) {
		this.action = action;
	}

	public ArrayList<Object> getAlgorithmName() {
		return algorithmName;
	}

	public void setAlgorithmName(ArrayList<Object> algorithmName) {
		this.algorithmName = algorithmName;
	}

	public ArrayList<Object> getStatus() {
		return status;
	}

	public void setStatus(ArrayList<Object> status) {
		this.status = status;
	}

	public void initDefaultSelections() {
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		Date dateToday = new Date();
		activityLogClientVO.setProcessingDateFrom(formatter.format(dateToday));

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, 1);
		Date dateTomorrow = cal.getTime();
		activityLogClientVO.setProcessingDateTo(formatter.format(dateTomorrow));

		activityLogClientVO.setProcessingTimeFrom("00:00");
		activityLogClientVO.setProcessingTimeTo("00:00");
		
		String[]status = {"Success", "Failure"};
		activityLogClientVO.setProcessStatus(status);

	}

	public ActivityLogClientVO getActivityLogClientVO() {
		return activityLogClientVO;
	}

	public void setActivityLogClientVO(ActivityLogClientVO activityLogClientVO) {
		this.activityLogClientVO = activityLogClientVO;
	}

	public ArrayList<Object> getExceptiontext() {
		return exceptiontext;
	}

	public void setExceptiontext(ArrayList<Object> exceptiontext) {
		this.exceptiontext = exceptiontext;
	}

	public ArrayList<Object> getMessageId() {
		return messageId;
	}

	public void setMessageId(ArrayList<Object> messageId) {
		this.messageId = messageId;
	}

	public ArrayList<Object> getEntityNm() {
		return entityNm;
	}

	public void setEntityNm(ArrayList<Object> entityNm) {
		this.entityNm = entityNm;
	}

	public ArrayList<Object> getAccessionNbr() {
		return accnNbr;
	}

	public void setAccessionNbr(ArrayList<Object> accessionNbr) {
		this.accnNbr = accessionNbr;
	}

	public ArrayList<Object> getSrcName() {
		return srcName;
	}

	public void setSrcName(ArrayList<Object> srcName) {
		this.srcName = srcName;
	}

	public ArrayList<Object> getBusinessObjLocalId() {
		return businessObjLocalId;
	}

	public void setBusinessObjLocalId(ArrayList<Object> businessObjLocalId) {
		this.businessObjLocalId = businessObjLocalId;
	}

	
	
}
