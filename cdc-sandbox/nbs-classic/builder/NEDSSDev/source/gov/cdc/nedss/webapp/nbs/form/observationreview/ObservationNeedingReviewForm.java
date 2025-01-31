package gov.cdc.nedss.webapp.nbs.form.observationreview;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;




import gov.cdc.nedss.util.HTMLEncoder;
import gov.cdc.nedss.webapp.nbs.action.observation.review.util.ObservationReviewQueueUtil;
import gov.cdc.nedss.webapp.nbs.form.util.BaseForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.webapp.nbs.queue.GenericForm;
import gov.cdc.nedss.webapp.nbs.queue.GenericQueueUtil;
import gov.cdc.nedss.webapp.nbs.queue.QueueColumnDT;
import gov.cdc.nedss.webapp.nbs.queue.QueueDT;

public class ObservationNeedingReviewForm extends BaseForm {
	
	ObservationReviewQueueUtil obsReviewUtil = new ObservationReviewQueueUtil();
	GenericQueueUtil genericQueueUtil = new GenericQueueUtil();
	
	Map<Object,Object> searchCriteriaArrayMap = new HashMap<Object,Object>();
	private String CLASS_NAME ="gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.ObservationSummaryDisplayVO";

	private ArrayList<Object> jurisdictions = new ArrayList<Object> ();
	private ArrayList<Object> dateFilterList = new ArrayList<Object> ();
	private ArrayList<Object> observationTypes = new ArrayList<Object> ();
	private ArrayList<Object> resultedTestandConditions = new ArrayList<Object> (); 
	private ArrayList<Object> resultedDescription = new ArrayList<Object> (); 
	private String labCount;
	private String processingDecisionSelected;
	private String nonSTDProcessingDecisionSelected;
	
	private String markAsReviewedCommentsSTD;
	private String markAsReviewedComments;
	
	private String selectedcheckboxIdsLabs;
	private String selectedcheckboxMprIdsLabs;
	
	private String selectedcheckboxIdsMorbs;
	private String selectedcheckboxMprIdsMorbs;
	
	private String selectedcheckboxIdsCases;
	private String selectedcheckboxMprIdsCases;
	 private String processingDecisionLogic = "none";
	 private String nonSTDProcessingDecisionLogic = "none";
	private QueueDT queueDT;
	private ArrayList<QueueColumnDT> queueCollection;
	 private String stringQueueCollection;
	 
	 /*
	public ArrayList<QueueColumnDT> getQueueCollection() {
		return queueCollection;
	}

	public void setQueueCollection(ArrayList<QueueColumnDT> queueCollection) {
		this.queueCollection = queueCollection;
	}
*/
	public String getProcessingDecisionSelected() {
		return processingDecisionSelected;
	}
	
	public void setProcessingDecisionSelected(String processingDecisionSelected) {
		this.processingDecisionSelected = processingDecisionSelected;
	}
	public String getNonSTDProcessingDecisionSelected() {
		return nonSTDProcessingDecisionSelected;
	}
	
	public void setNonSTDProcessingDecisionSelected(String nprocessingDecisionSelected) {
		this.nonSTDProcessingDecisionSelected = nprocessingDecisionSelected;
	}
	
	public ArrayList<Object> getProgramAreaList(){
		return CachedDropDowns.getProgramAreaList();
	}
	

	public String getProcessingDecisionLogic() {
		return processingDecisionLogic;
	}
	public String getNonSTDProcessingDecisionLogic() {
		return nonSTDProcessingDecisionLogic;
	}

	/**
	 * @param processingDecisionLogic the processingDecisionLogic to set
	 */
	public void setProcessingDecisionLogic(String processingDecisionLogic) {
		this.processingDecisionLogic = processingDecisionLogic;
	}
	public Object getProcessingDecisionList(){
		return CachedDropDowns.getCodedValueForType(getProcessingDecisionLogic());
	}
	
	public void setNonSTDProcessingDecisionLogic(String nprocessingDecisionLogic) {
		this.nonSTDProcessingDecisionLogic = nprocessingDecisionLogic;
	}
	public Object getNonSTDProcessingDecisionList(){
		return CachedDropDowns.getCodedValueForType(getNonSTDProcessingDecisionLogic());
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
	
	public void initializeDropDowns(Collection<Object> observationColls, GenericForm genericForm, String className) {
		
		ArrayList<ArrayList<Object>> dropdownsToInitialize = new ArrayList<ArrayList<Object>>();


		
		dropdownsToInitialize = genericQueueUtil.initializeDropdowns(observationColls, queueDT, CLASS_NAME);
		
		observationTypes=dropdownsToInitialize.get(0);
		dateFilterList=dropdownsToInitialize.get(1);
		resultedDescription=dropdownsToInitialize.get(2);
		jurisdictions=dropdownsToInitialize.get(3);
		resultedTestandConditions=dropdownsToInitialize.get(4);
		
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

	public String getSelectedcheckboxMprIdsLabs() {
		return selectedcheckboxMprIdsLabs;
	}

	public void setSelectedcheckboxMprIdsLabs(String selectedcheckboxMprIdsLabs) {
		this.selectedcheckboxMprIdsLabs = selectedcheckboxMprIdsLabs;
	}

	public String getSelectedcheckboxMprIdsMorbs() {
		return selectedcheckboxMprIdsMorbs;
	}

	public void setSelectedcheckboxMprIdsMorbs(String selectedcheckboxMprIdsMorbs) {
		this.selectedcheckboxMprIdsMorbs = selectedcheckboxMprIdsMorbs;
	}

	public String getSelectedcheckboxMprIdsCases() {
		return selectedcheckboxMprIdsCases;
	}

	public void setSelectedcheckboxMprIdsCases(String selectedcheckboxMprIdsCases) {
		this.selectedcheckboxMprIdsCases = selectedcheckboxMprIdsCases;
	}

	public String getMarkAsReviewedCommentsSTD() {
		return markAsReviewedCommentsSTD;
	}

	public void setMarkAsReviewedCommentsSTD(String markAsReviewedCommentsSTD) {
		this.markAsReviewedCommentsSTD = markAsReviewedCommentsSTD;
	}

	public String getMarkAsReviewedComments() {
		return markAsReviewedComments;
	}

	public void setMarkAsReviewedComments(String markAsReviewedComments) {
		this.markAsReviewedComments = markAsReviewedComments;
	}

	public String getStringQueueCollection() {
		return stringQueueCollection;
	}

	public void setStringQueueCollection(String stringQueueCollection) {
		this.stringQueueCollection = stringQueueCollection;
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

	public String getCLASS_NAME() {
		return CLASS_NAME;
	}

	public void setCLASS_NAME(String cLASS_NAME) {
		CLASS_NAME = cLASS_NAME;
	}
	

}
