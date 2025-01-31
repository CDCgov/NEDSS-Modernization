package gov.cdc.nedss.webapp.nbs.form.pagemanagement.managetemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import gov.cdc.nedss.systemservice.dt.EDXActivityLogDT;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.PageManagementActionUtil;
import gov.cdc.nedss.webapp.nbs.action.util.QueueUtil;
import gov.cdc.nedss.webapp.nbs.form.util.BaseForm;

public class ManageImportExportLogForm extends BaseForm{
	private static final long serialVersionUID = 1L;
	Map<Object,Object> searchMap = new HashMap<Object,Object>();
	ArrayList<?> manageList = new ArrayList<Object> ();
	ArrayList<?> oldManageList = new ArrayList<Object> ();
	Object selection = new Object();
	Object oldDT = new Object();
	private String actionMode;
	private String returnToLink;
	ArrayList<?> manageVocabList = new ArrayList<Object> ();
	ArrayList<?> manageQuesList = new ArrayList<Object> ();
	ArrayList<?> manageErrorList = new ArrayList<Object> ();

	private ArrayList<Object> processedTime = new ArrayList<Object> ();
	private ArrayList<Object> type = new ArrayList<Object> ();
	private ArrayList<Object> templateName = new ArrayList<Object> ();
	private ArrayList<Object> source = new ArrayList<Object> ();
	private ArrayList<Object> status = new ArrayList<Object> ();
	private ArrayList<Object> activityLogColl;
	
	private Map<Object,Object> attributeMap = new HashMap<Object,Object>();
	private int srchFldCount;
	private EDXActivityLogDT edxActivityLogDT = new EDXActivityLogDT();
	
	//For Filtering/sorting
	Map<Object,Object> searchCriteriaArrayMap = new HashMap<Object,Object>();	
	
	PageManagementActionUtil util = new PageManagementActionUtil();
	public void initializeDropDowns()throws Exception {
		QueueUtil queueUtil = new QueueUtil();
		processedTime = queueUtil.getStartDateDropDownValues();
		type = util.getLogTypeDropDowns(activityLogColl);
		templateName = util.getLogTemplateNmDropDowns(activityLogColl);
		source =  util.getLogSourceNmDropDowns(activityLogColl);
		status = util.getLogStatusDropDowns(activityLogColl);
	}
	
	
	
	public ArrayList<Object> getActivityLogColl() {
		return activityLogColl;
	}



	public void setActivityLogColl(ArrayList<Object> activityLogColl) {
		this.activityLogColl = activityLogColl;
	}




	public ArrayList<Object> getProcessedTime() {
		return processedTime;
	}



	public void setProcessedTime(ArrayList<Object> processedTime) {
		this.processedTime = processedTime;
	}



	public ArrayList<Object> getType() {
		return type;
	}


	public void setType(ArrayList<Object> type) {
		this.type = type;
	}


	public ArrayList<Object> getTemplateName() {
		return templateName;
	}


	public void setTemplateName(ArrayList<Object> templateName) {
		this.templateName = templateName;
	}


	public ArrayList<Object> getSource() {
		return source;
	}


	public void setSource(ArrayList<Object> source) {
		this.source = source;
	}


	public ArrayList<Object> getStatus() {
		return status;
	}


	public void setStatus(ArrayList<Object> status) {
		this.status = status;
	}


	public EDXActivityLogDT getEdxActivityLogDT() {
		return edxActivityLogDT;
	}


	public void setEdxActivityLogDT(EDXActivityLogDT edxActivityLogDT) {
		this.edxActivityLogDT = edxActivityLogDT;
	}


	public Map<Object, Object> getSearchMap() {
		return searchMap;
	}

	public void setSearchMap(Map<Object, Object> searchMap) {
		this.searchMap = searchMap;
	}

	public ArrayList<?> getManageList() {
		return manageList;
	}

	public void setManageList(ArrayList<?> manageList) {
		this.manageList = manageList;
	}



	public ArrayList<?> getOldManageList() {
		return oldManageList;
	}



	public void setOldManageList(ArrayList<?> oldManageList) {
		this.oldManageList = oldManageList;
	}



	public Object getSelection() {
		return selection;
	}



	public void setSelection(Object selection) {
		this.selection = selection;
	}



	public Object getOldDT() {
		return oldDT;
	}



	public void setOldDT(Object oldDT) {
		this.oldDT = oldDT;
	}



	public String getActionMode() {
		return actionMode;
	}



	public void setActionMode(String actionMode) {
		this.actionMode = actionMode;
	}



	public String getReturnToLink() {
		return returnToLink;
	}



	public void setReturnToLink(String returnToLink) {
		this.returnToLink = returnToLink;
	}



	public Map<Object, Object> getAttributeMap() {
		return attributeMap;
	}



	public void setAttributeMap(Map<Object, Object> attributeMap) {
		this.attributeMap = attributeMap;
	}



	public int getSrchFldCount() {
		return srchFldCount;
	}



	public void setSrchFldCount(int srchFldCount) {
		this.srchFldCount = srchFldCount;
	}



	public Map<Object, Object> getSearchCriteriaArrayMap() {
		return searchCriteriaArrayMap;
	}



	public void setSearchCriteriaArrayMap(Map<Object, Object> searchCriteriaArrayMap) {
		this.searchCriteriaArrayMap = searchCriteriaArrayMap;
	}



	public void clearAll() {
		getAttributeMap().clear();
		searchCriteriaArrayMap = new HashMap<Object,Object>();
}
	
	
	public ArrayList<?> getManageVocabList() {
		return manageVocabList;
	}

	public void setManageVocabList(ArrayList<?> manageVocabList) {
		this.manageVocabList = manageVocabList;
	}

	public ArrayList<?> getManageQuesList() {
		return manageQuesList;
	}

	public void setManageQuesList(ArrayList<?> manageQuesList) {
		this.manageQuesList = manageQuesList;
	}

	public ArrayList<?> getManageErrorList() {
		return manageErrorList;
	}



	public void setManageErrorList(ArrayList<?> manageErrorList) {
		this.manageErrorList = manageErrorList;
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

}
