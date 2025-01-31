package gov.cdc.nedss.webapp.nbs.form.decisionsupportmanagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts.upload.FormFile;

import gov.cdc.nedss.util.HTMLEncoder;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.webapp.nbs.action.myinvestigation.ProgramAreaUtil;
import gov.cdc.nedss.webapp.nbs.action.util.QueueUtil;
import gov.cdc.nedss.webapp.nbs.form.util.BaseForm;
import gov.cdc.nedss.webapp.nbs.action.decisionsupportmanagement.util.DSMLibraryActionUtil;;

public class DecisionSupportLibraryForm extends BaseForm {
	
	static final LogUtils logger = new LogUtils(DecisionSupportLibraryForm.class.getName());	

	
	/**
	 * This will contain a reference to the PageManagementProxyVO object
	 */
	Object selection = new Object();
	
	private FormFile importFile ;
	
	/**
	 * To hold the current step in the page builder process 
	 * (add, edit, view etc...)
	 */
	Map<Object,Object> searchCriteriaArrayMap = new HashMap<Object,Object>();
	String actionMode =null;
	ArrayList<Object> algorithmList =null;
	
	DSMLibraryActionUtil dsmLibUtil = new DSMLibraryActionUtil();
	
	private ArrayList<Object> eventType = new ArrayList<Object> ();
	//private ArrayList<Object> algorithmName = new ArrayList<Object> ();
	//private ArrayList<Object> relatedConditions = new ArrayList<Object> ();
	private ArrayList<Object> action = new ArrayList<Object> ();
	//private ArrayList<Object> lastUpdatedBy = new ArrayList<Object> ();
	private ArrayList<Object> status = new ArrayList<Object> ();
	
	private ArrayList<Object> dateFilterList = new ArrayList<Object> ();
	
	public ArrayList<Object> getDateFilterList() {
		return dateFilterList;
	}

	public void setDateFilterList(ArrayList<Object> dateFilterList) {
		this.dateFilterList = dateFilterList;
	}

	public void initializeDropDowns() {
		eventType = dsmLibUtil.getEventTypeDropDowns(getAlgorithmList());
		action = dsmLibUtil.getActionDropDowns(getAlgorithmList());
		status = dsmLibUtil.getStatusDropDowns(getAlgorithmList());
		dateFilterList = dsmLibUtil.getStartDateDropDownValues();
	}
	
	public void clearAll() {
		getAttributeMap().clear();
		searchCriteriaArrayMap = new HashMap<Object,Object>();
	}
	public Object getSelection() {
		return selection;
	}
	public void setSelection(Object selection) {
		this.selection = selection;
	}
	public String getActionMode() {
		return actionMode;
	}
	public void setActionMode(String actionMode) {
		this.actionMode = actionMode;
	}
	public ArrayList<Object> getAlgorithmList() {
		return algorithmList;
	}
	public void setAlgorithmList(ArrayList<Object> algorithmList) {
		this.algorithmList = algorithmList;
	}
	
	public String[] getAnswerArray(String key) {
		return (String[])searchCriteriaArrayMap.get(key);
	}
	
	
	public ArrayList<Object> getEventType() {
		return eventType;
	}
	public void setEventType(ArrayList<Object> eventType) {
		this.eventType = eventType;
	}
	public ArrayList<Object> getAction() {
		return action;
	}
	public void setAction(ArrayList<Object> action) {
		this.action = action;
	}
	public ArrayList<Object> getStatus() {
		return status;
	}
	public void setStatus(ArrayList<Object> status) {
		this.status = status;
	}
	public Map<Object, Object> getSearchCriteriaArrayMap() {
		return searchCriteriaArrayMap;
	}
	public void setSearchCriteriaArrayMap(Map<Object, Object> searchCriteriaArrayMap) {
		this.searchCriteriaArrayMap = searchCriteriaArrayMap;
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
				searchCriteriaArrayMap.put(newKey,HTMLEncoder.encodeHtml(answer));
		}
	}
	
	public FormFile getImportFile() {
		return importFile;
	}

	public void setImportFile(FormFile importFile) {
		this.importFile = importFile;
	}	
}
