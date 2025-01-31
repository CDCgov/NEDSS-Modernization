package gov.cdc.nedss.webapp.nbs.form.epilink;

import gov.cdc.nedss.proxy.ejb.epilink.vo.EpilinkSummaryDisplayVO;
import gov.cdc.nedss.webapp.nbs.action.decisionsupportmanagement.DecisionSupportClientVO.ActivityLogClientVO;
import gov.cdc.nedss.webapp.nbs.action.epilink.EpilinkLogUtil;
import gov.cdc.nedss.webapp.nbs.action.util.NBSPageConstants;
import gov.cdc.nedss.webapp.nbs.form.util.BaseForm;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

public class EpiLinkLogForm extends BaseForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ActivityLogClientVO activityLogClientVO = new ActivityLogClientVO();
	
	private EpilinkSummaryDisplayVO epilinkSummaryDisplayVO;
	
	
	
	private List<Object> processedDate; 
	private List<Object> oldEpiLink; 
	private List<Object> newEpiLink; 
	
	private List<Object> name;
	
	private List<Object>epilinkList = new ArrayList<Object>();
	
	Map<Object, Object> searchCriteriaArrayMap = new HashMap<Object, Object>();
	
	public void initializeDropDowns(){
		EpilinkLogUtil util = new EpilinkLogUtil();
		try {
			Collection col = (ArrayList)this.getAttributeMap().get("epilinkList");
			processedDate = new ArrayList<Object>();
			oldEpiLink = new ArrayList<Object>();
			newEpiLink = new ArrayList<Object>();
			
			processedDate = util.getProcessedDateDropDowns(col);
			oldEpiLink = util.getOldEpilinkDropDowns(col);
			newEpiLink	=util.getNewEpilinkDropDowns(col);
					
					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public ActivityLogClientVO getActivityLogClientVO() {
		return activityLogClientVO;
	}

	public void setActivityLogClientVO(ActivityLogClientVO activityLogClientVO) {
		this.activityLogClientVO = activityLogClientVO;
	}

	
	public EpilinkSummaryDisplayVO getEpilinkSummaryDisplayVO() {
		return epilinkSummaryDisplayVO;
	}

	public void setEpilinkSummaryDisplayVO(
			EpilinkSummaryDisplayVO epilinkSummaryDisplayVO) {
		this.epilinkSummaryDisplayVO = epilinkSummaryDisplayVO;
	}

	
	public Map<Object, Object> getSearchCriteriaArrayMap() {
		return searchCriteriaArrayMap;
	}

	public void setSearchCriteriaArrayMap(Map<Object, Object> searchCriteriaArrayMap) {
		this.searchCriteriaArrayMap = searchCriteriaArrayMap;
	}
	

	public List<Object> getProcessedDate() {
		return processedDate;
	}

	public void setProcessedDate(List<Object> processedDate) {
		this.processedDate = processedDate;
	}

	public List<Object> getOldEpiLink() {
		return oldEpiLink;
	}

	public void setOldEpiLink(List<Object> oldEpiLink) {
		this.oldEpiLink = oldEpiLink;
	}

	public List<Object> getNewEpiLink() {
		return newEpiLink;
	}

	public void setNewEpiLink(List<Object> newEpiLink) {
		this.newEpiLink = newEpiLink;
	}

	public List<Object> getEpilinkList() {
		return epilinkList;
	}

	public void setEpilinkList(List<Object> epilinkList) {
		this.epilinkList = epilinkList;
	}
	
	public List<Object> getName() {
		return name;
	}

	public void setName(List<Object> name) {
		this.name = name;
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

	
	public void initDefaultSelections() {
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		Calendar cal = Calendar.getInstance();
		Date today = cal.getTime();
		cal.add(Calendar.DAY_OF_MONTH, -1);
		Date datePrevious = cal.getTime();
		
		activityLogClientVO.setProcessingDateFrom(formatter.format(datePrevious));

		activityLogClientVO.setProcessingDateTo(formatter.format(today));

	}
	public void setAnswerArrayText(String key, String answer) {
		if(answer!=null && answer.length() > 0) {
			String newKey = key+"_FILTER_TEXT";
				searchCriteriaArrayMap.put(newKey,answer);
		}
	}
	

	public void reset(ActionMapping mapping, HttpServletRequest request) {
			super.reset(mapping, request);
		}

	
	public void clearAll() {
		getAttributeMap().clear();
		searchCriteriaArrayMap = new HashMap<Object, Object>();
	}
	
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		return validateSearchCriteria(activityLogClientVO);
	}

	private ActionErrors validateSearchCriteria(ActivityLogClientVO activityLogClientVO2){
		ActionErrors errors = new ActionErrors();
		String processingDateFrom = activityLogClientVO.getProcessingDateFrom();
		String processingDateTo = activityLogClientVO.getProcessingDateTo();
	
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
		if(processingDateFrom != null && processingDateTo != null){
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
		}

		return errors;	
	}

	public boolean isDateValid(String date)

	{
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");		
		try
		{
			Date dt = sdf.parse(date);
			
//			Pattern p = Pattern.compile("(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)");
//		    return p.(date).matches();
			String [] dateStr = date.split("/");
			if(dateStr[2].length()<4){
				return false;
			}
			return true;
		}
		catch (ParseException e){
			return false;
		}
	}

}
