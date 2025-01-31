package gov.cdc.nedss.webapp.nbs.form.observationsecurityassgn;

import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.util.HTMLEncoder;
import gov.cdc.nedss.webapp.nbs.action.observation.review.util.ObservationReviewQueueUtil;
import gov.cdc.nedss.webapp.nbs.form.util.BaseForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.webapp.nbs.queue.GenericForm;
import gov.cdc.nedss.webapp.nbs.queue.GenericQueueUtil;
import gov.cdc.nedss.webapp.nbs.queue.QueueColumnDT;
import gov.cdc.nedss.webapp.nbs.queue.QueueDT;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ObservationNeedingSecurityReviewForm extends BaseForm {
	
ObservationReviewQueueUtil obsReviewUtil = new ObservationReviewQueueUtil();
	
	Map<Object,Object> searchCriteriaArrayMap = new HashMap<Object,Object>();
	private String CLASS_NAME ="gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.ObservationSummaryDisplayVO";
	GenericQueueUtil genericQueueUtil = new GenericQueueUtil();
	private ArrayList<Object> jurisdictions = new ArrayList<Object> ();
	private ArrayList<Object> dateFilterList = new ArrayList<Object> ();
	private ArrayList<Object> observationTypes = new ArrayList<Object> ();
	private ArrayList<Object> resultedTestandConditions = new ArrayList<Object> (); 
	private ArrayList<Object> programArea = new ArrayList<Object> ();
	private ArrayList<Object> resultedDescription = new ArrayList<Object> (); 
	private Collection<Object>  observationColl;	
	private String labCount;
	private String programAreaSelected;
	private String jurisdictionSelected;
	
	private String selectedcheckboxIdsLabs;
	private String selectedcheckboxIdsMorbs;
	private String selectedcheckboxIdsCases;
	private QueueDT queueDT;
	private ArrayList<QueueColumnDT> queueCollection;
	 private String stringQueueCollection;
	
	public ArrayList<Object> getProgramAreaList(){
		return CachedDropDowns.getProgramAreaList();
	}
	public ArrayList<Object> getJurisdictionList() {
		
		ArrayList<Object> jurisdictionList = CachedDropDowns.getAlertJurisdictionList(); 
		Iterator<Object> it = jurisdictionList.iterator();
		ArrayList<Object> jurisdictionList2 = new ArrayList<Object>();
		
		while(it.hasNext()){
			DropDownCodeDT dropdown = ((DropDownCodeDT)it.next());
			String key = dropdown.getKey();
			if(!key.equals("Not Assigned") && !key.equals("999999") && !key.equals("ALL"))
				jurisdictionList2.add(dropdown);
		}
			
		return jurisdictionList2;
	}
	public void initializeDropDowns(Collection<Object> observationColls, GenericForm genericForm, String className) {
			/*
		jurisdictions = obsReviewUtil.getJurisDropDowns(getObservationColl());
		observationTypes  = obsReviewUtil.getObservationType(getObservationColl());
		dateFilterList = obsReviewUtil.getStartDateDropDownValues();
		resultedTestandConditions = obsReviewUtil.getResultedTestandCondition(getObservationColl());
		programArea = obsReviewUtil.getProgramArea(getObservationColl());
		resultedDescription = obsReviewUtil.getResultedDescription(getObservationColl());
		
		*/
		ArrayList<ArrayList<Object>> dropdownsToInitialize = new ArrayList<ArrayList<Object>>();


		
		dropdownsToInitialize = genericQueueUtil.initializeDropdowns(observationColls, queueDT, CLASS_NAME);
		
		observationTypes=dropdownsToInitialize.get(0);
		dateFilterList=dropdownsToInitialize.get(1);
		resultedDescription=dropdownsToInitialize.get(2);
		jurisdictions=dropdownsToInitialize.get(3);
		programArea=dropdownsToInitialize.get(4);
		
		//genericForm.initializeDropDowns(observationColls, className,5);//5 is the number of dropdowns column
		
		
		
	}
	
	public ArrayList<Object> getJurisdictions() {
		return jurisdictions;
	}
	
	public ArrayList<Object> getDateFilterList() {
		return dateFilterList;
	}
	
	public ArrayList<Object> getObservationTypes() {
		return observationTypes;
	}
		
	public ArrayList<Object> getResultedTestandConditions() {
		return resultedTestandConditions;
	}
	
	public ArrayList<Object> getResultedDescription() {
		return resultedDescription;
	}
	
	public ArrayList<Object> getProgramArea() {
		return programArea;
	}
	
	public ArrayList<Object> getStartDateDropDowns(){
		return dateFilterList;
	}
	
	public String[] getAnswerArray(String key) {
		return (String[])searchCriteriaArrayMap.get(key);
	}

	/**
	 * @param key
	 * @param answer
	 */
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
	public String getLabCount() {
		return labCount;
	}
	public void setLabCount(String labCount) {
		this.labCount = labCount;
	}
	
	public String getProgramAreaSelected() {
		return programAreaSelected;
	}
	public void setProgramAreaSelected(String programAreaSelected) {
		this.programAreaSelected = programAreaSelected;
	}
	public String getJurisdictionSelected() {
		return jurisdictionSelected;
	}
	public void setJurisdictionSelected(String jurisdictionSelected) {
		this.jurisdictionSelected = jurisdictionSelected;
	}
	public String getSelectedcheckboxIdsLabs() {
		return selectedcheckboxIdsLabs;
	}
	public void setSelectedcheckboxIdsLabs(String selectedcheckboxIdsLabs) {
		this.selectedcheckboxIdsLabs = selectedcheckboxIdsLabs;
	}
	public String getSelectedcheckboxIdsMorbs() {
		return selectedcheckboxIdsMorbs;
	}
	public void setSelectedcheckboxIdsMorbs(String selectedcheckboxIdsMorbs) {
		this.selectedcheckboxIdsMorbs = selectedcheckboxIdsMorbs;
	}
	public String getSelectedcheckboxIdsCases() {
		return selectedcheckboxIdsCases;
	}
	public void setSelectedcheckboxIdsCases(String selectedcheckboxIdsCases) {
		this.selectedcheckboxIdsCases = selectedcheckboxIdsCases;
	}
	public QueueDT getQueueDT() {
		return queueDT;
	}
	public void setQueueDT(QueueDT queueDT) {
		this.queueDT = queueDT;
	}
	public ArrayList<QueueColumnDT> getQueueCollection() {
		return queueCollection;
	}
	public void setQueueCollection(ArrayList<QueueColumnDT> queueCollection) {
		this.queueCollection = queueCollection;
	}
	public String getStringQueueCollection() {
		return stringQueueCollection;
	}
	public void setStringQueueCollection(String stringQueueCollection) {
		this.stringQueueCollection = stringQueueCollection;
	}
	public String getCLASS_NAME() {
		return CLASS_NAME;
	}
	public void setCLASS_NAME(String cLASS_NAME) {
		CLASS_NAME = cLASS_NAME;
	}
}
