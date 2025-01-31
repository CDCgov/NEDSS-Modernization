package gov.cdc.nedss.webapp.nbs.form.person;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

/**
 * Title:        Actions
 * Description:  This is definately some code
 * Copyright:    Copyright (c) 2001
 * Company:      CSC
 * @author
 * @version 1.0
 */




import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;

import gov.cdc.nedss.entity.person.vo.PatientSearchVO;
import gov.cdc.nedss.entity.person.vo.PersonSearchVO;
import gov.cdc.nedss.entity.person.vo.PersonSrchResultVO;
import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.webapp.nbs.action.person.PersonSearchSubmit;
import gov.cdc.nedss.webapp.nbs.action.person.util.SearchResultPersonUtil;
import gov.cdc.nedss.webapp.nbs.action.util.CallProxyEJB;
import gov.cdc.nedss.webapp.nbs.action.util.QueueUtil;
import gov.cdc.nedss.webapp.nbs.form.util.BaseForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

public class PersonSearchForm extends BaseForm {

  private static final long serialVersionUID = 1L;	
  private PatientSearchVO patientSearch = null;
  private PatientSearchVO oldPatientSearch = null;
  private Collection<Object> patientVoCollection;
  static final LogUtils logger = new LogUtils(PersonSearchSubmit.class.getName());
	
  
  private String queueCount;
  Map<Object,Object> searchCriteriaArrayMap = new HashMap<Object,Object>();
  
  

public String getQueueCount() {
	return queueCount;
  }
	
  public void setQueueCount(String queueCount) {
		this.queueCount = queueCount;
  }
  private int intqueueCount;
  public int getIntqueueCount() {
		return intqueueCount;
	}

	public void setIntqueueCount(int intqueueCount) {
		this.intqueueCount = intqueueCount;
	}
	
  private ArrayList<Object> resultTypeList = new ArrayList<Object>();
  private ArrayList<Object> outbreakNameList = new ArrayList<Object>();
  private ArrayList<Object> investigationStatusList = new ArrayList<Object>();
  private ArrayList<Object> pregnantList = new ArrayList<Object>();
  private ArrayList<Object> labResultList = new ArrayList<Object>();
  private ArrayList<Object> organismList = new ArrayList<Object>();
  private String userCreateList;
  private String programAreasLab;
  private String programAreasMorb;
  private String programAreasCase;
  private String programAreasInvestigation;
  private ArrayList<Object> jurisdictions  = new ArrayList<Object>();
  private ArrayList<Object> conditionL  = new ArrayList<Object>();
  private ArrayList<Object> userList = new ArrayList<Object>();
  private ArrayList<Object> programListLabs = new ArrayList<Object>(); 
  private ArrayList<Object> programListMorb = new ArrayList<Object>(); 
  private ArrayList<Object> programListCase = new ArrayList<Object>(); 
  private ArrayList<Object> programListInvestigation = new ArrayList<Object>(); 
  private ArrayList<Object> jurisdictionL = new ArrayList<Object>();
  private ArrayList<Object> investigators = new ArrayList<Object> ();
	private ArrayList<Object> jurisdictionsDD = new ArrayList<Object> ();
	private ArrayList<Object> conditions = new ArrayList<Object> ();
	private ArrayList<Object> caseStatus = new ArrayList<Object> ();
	private ArrayList<Object> dateFilterList = new ArrayList<Object> ();
	private ArrayList<Object> notifications = new ArrayList<Object> ();
	
	private ArrayList<Object> observationTypesDD = new ArrayList<Object> ();
	private ArrayList<Object> descriptionDD = new ArrayList<Object> ();
	private ArrayList<Object> conditionDD = new ArrayList<Object> ();
	
//	private Collection<Object>  summaryVOColl ;
	private PersonSrchResultVO patientVO = null;
	private PersonSearchVO psVO = null;
	SearchResultPersonUtil personUtil = new SearchResultPersonUtil();
	/**
	 * initializeDropDowns returns the latest set of distinct dropdown values for Investigator, 
	 * Patient etc Dropdown Lists in queue
	 */
	public void initializeDropDowns(ArrayList<Object> patientVOColl) {
		QueueUtil queueUtil = new QueueUtil();
		investigators = personUtil.getInvestigatorDropDowns(patientVOColl);
		jurisdictionsDD = personUtil.getJurisDropDowns(patientVOColl);
		conditions = personUtil.getConditionDropDowns(patientVOColl);
		caseStatus = personUtil.getCaseStatusDropDowns(patientVOColl);
		dateFilterList = queueUtil.getStartDateDropDownValues();
		notifications = personUtil.getNotificationValuesfromColl(patientVOColl);
		observationTypesDD = personUtil.getObservationType(patientVOColl);
		descriptionDD = personUtil.getResultedDescription(patientVOColl);
		conditionDD = personUtil.getResultedTestandCondition(patientVOColl);
	}
	
	
	public ArrayList<Object> getNotifications(){
		
		return notifications;		
	}

	
	public ArrayList<Object> getInvestigators(){
		
			return investigators;	
		
	}

	
	public ArrayList<Object> getJurisdictionsDD(){
		return jurisdictionsDD;
		
	}
	public ArrayList<Object> getConditions(){
		return conditions;
		
	}
	
	public ArrayList<Object> getCaseStatusesDD(){
		return caseStatus;
		
	}
	
	public ArrayList<Object> getStartDateDropDowns(){
		return dateFilterList;
	}
  
 
	public ArrayList<Object> getResultTypeList() {
		DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
		dropDownCodeDT.setKey("");
		dropDownCodeDT.setValue("");

		resultTypeList = new CachedDropDownValues()
				.getCodedValuesList("UNIT_ISO");
		resultTypeList.add(0, dropDownCodeDT);

		return resultTypeList;
	}
	
	
	public Object getOutbreakNameList(){
		outbreakNameList = CachedDropDowns.getCodedValueForType("OUTBREAK_NM");
		return outbreakNameList;
	}
	
	public Object getInvestigationStatusList(){
		//Add a blank value for being able to de-select the previous selected option
		DropDownCodeDT dDownDT = null;
        dDownDT = new DropDownCodeDT();
        dDownDT.setKey(""); dDownDT.setValue("");
        investigationStatusList.add(dDownDT);
        
		investigationStatusList = CachedDropDowns.getCodedValueForType("PHC_IN_STS");
		
		return investigationStatusList;
	}
	
	public Object getPregnantList(){
		pregnantList = CachedDropDowns.getCodedValueForType("YNU");
		return pregnantList;
	}
	
	
public PatientSearchVO getPersonSearch(){
    if (patientSearch == null)
	patientSearch = new PatientSearchVO();

	return this.patientSearch;
  }
  
  public void setPersonSearch(PatientSearchVO psVo){
	   
		patientSearch = psVo;

		
	  }
  public void setOldPersonSearch(PatientSearchVO psVo){
	   
	  oldPatientSearch = psVo;

		
	  }
  
  public PatientSearchVO getOldPersonSearch(){
	    if (oldPatientSearch == null)
	    	oldPatientSearch = new PatientSearchVO();

		return this.oldPatientSearch;
	  }

  public void reset(){
   patientSearch=null;
 
  }
  
  Map<Object,Object> searchMap = new HashMap<Object,Object>();
	private String actionMode;
	private String returnToLink;

	
	private String personMprUid;
	
	public String getPersonMprUid() {
		return personMprUid;
	}
	public void setPersonMprUid(String personMprUid) {
		this.personMprUid = personMprUid;
	}
	
	public Map<Object,Object> getSearchMap() {
		return searchMap;
	}
	public void setSearchMap(Map<Object,Object> searchMap) {
		this.searchMap = searchMap;
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

	@SuppressWarnings("unchecked")
	public void clearSelections() {
		Map<Object, Object> alterSearchCriteriaColl = new TreeMap<Object, Object>();
		alterSearchCriteriaColl.put("sortSt", "Full Name @ in ascending order" );
		getAttributeMap().put("searchCriteria", alterSearchCriteriaColl);
		
	}
	
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	
public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {


	  ActionErrors errors = super.validate(mapping,request);
	    if(errors == null)
	    	errors = new ActionErrors();
    return errors;
}

	public Object getCodedValue(String codesetNm) {
		return CachedDropDowns.getCodedValueForType(codesetNm);
	}
	
	public Object getCodedValueNoBlnk(String key) {
		ArrayList<Object> list = (ArrayList<Object> ) getCodedValue(key);
		DropDownCodeDT dt = (DropDownCodeDT) list.get(0);
		if(dt.getKey() != null && dt.getKey().equals("") && dt.getValue() != null && dt.getValue().equals(""))
			list.remove(0);
		return list;
	}
	public ArrayList<Object> getConditionList(){
		return CachedDropDowns.getAllConditions();
	}
	public String getProgramAreasLab(){
		return programAreasLab;
	}
	public String getProgramAreasMorb(){
		return programAreasMorb;
	}
	public String getProgramAreasCase(){
		return programAreasCase;
	}
	public String getProgramAreasInvestigation(){
		return programAreasInvestigation;
	}
	public void setProgramAreasLab(String programAreas){
		this.programAreasLab = programAreas;
	}
	public void setProgramAreasMorb(String programAreas){
		this.programAreasMorb = programAreas;
	}
	public void setProgramAreasCase(String programAreas){
		this.programAreasCase = programAreas;
	}
	public void setProgramAreasInvestigation(String programAreas){
		this.programAreasInvestigation = programAreas;
	}
	public ArrayList<Object> getProgramList(String userString, ArrayList<Object> programList) {
		try{
			userString = userString.replace("(", "");
			userString = userString.replace(")", "");
			userString = userString.replace("'", "");
			DropDownCodeDT dropdownEmpty = new DropDownCodeDT();
			programList.add(dropdownEmpty);
			
			String[] userArray = userString.split("\\,");
			for(int i=0; i< userArray.length; i++)
			{
				
				String key = userArray[i];
				String value = userArray[i];
				DropDownCodeDT dropdown = new DropDownCodeDT();
				dropdown.setKey(key);
				dropdown.setValue(value);
				
				programList.add(dropdown);
			}
		
	
			
			}catch(Exception e)//TODO: handle exception
			{
				
				
			}
			
			return programList;
		
	}
	public ArrayList<Object> getProgramListLabs(){

		return getProgramList(getProgramAreasLab(), programListLabs);
	}
	public ArrayList<Object> getProgramListMorb(){

		return getProgramList(getProgramAreasMorb(), programListMorb);
	}
	public ArrayList<Object> getProgramListCase(){

		return getProgramList(getProgramAreasCase(), programListCase);
	}
	public ArrayList<Object> getProgramListInvestigation(){

		return getProgramList(getProgramAreasInvestigation(), programListInvestigation);
	}
	
	
	public void setJurisdictions(ArrayList<Object> jurisdictions){
		this.jurisdictions = jurisdictions;
	}
	public ArrayList<Object> getJurisdictions(){
		return jurisdictions;
	}
	
	
	/*public ArrayList<Object> getFirstName(){
		DropDownCodeDT ddDT = new DropDownCodeDT();
		ArrayList<Object> list = new ArrayList<Object>();
		
		ddDT.setKey("SW");
		ddDT.setValue("Starts With");
        list.add(ddDT);
		
		ddDT = new DropDownCodeDT();	
		ddDT.setKey("CT");
		ddDT.setValue("Contains");
        list.add(ddDT);
		
		ddDT = new DropDownCodeDT();	
		ddDT.setKey("=");
		ddDT.setValue("Equal");
        list.add(ddDT);
		
		ddDT = new DropDownCodeDT();	
		ddDT.setKey("!=");
		ddDT.setValue("Not Equal");
        list.add(ddDT);
		
		ddDT = new DropDownCodeDT();	
		ddDT.setKey("SL");
		ddDT.setValue("Sounds Like");
		
		
		list.add(ddDT);
		return list;
	}
	
	public ArrayList<Object> getLastName(){
		DropDownCodeDT ddDT = new DropDownCodeDT();
		ArrayList<Object> list = new ArrayList<Object>();
		 
		ddDT.setKey("SW");
		ddDT.setValue("Starts With");
        list.add(ddDT);
		
		ddDT = new DropDownCodeDT();	
		ddDT.setKey("CT");
		ddDT.setValue("Contains");
        list.add(ddDT);
		
		ddDT = new DropDownCodeDT();	
		ddDT.setKey("=");
		ddDT.setValue("Equal");
        list.add(ddDT);
		
		ddDT = new DropDownCodeDT();	
		ddDT.setKey("!=");
		ddDT.setValue("Not Equal");
        list.add(ddDT);
		
		ddDT = new DropDownCodeDT();	
		ddDT.setKey("SL");
		ddDT.setValue("Sounds Like");
		
		
		list.add(ddDT);
		return list;
	}
	
	public ArrayList<Object> getDateOfBirth(){
		DropDownCodeDT ddDT = new DropDownCodeDT();	 
		ArrayList<Object> list = new ArrayList<Object>();
		
		ddDT.setKey("=");
		ddDT.setValue("Equal");
		list.add(ddDT);
		
		ddDT = new DropDownCodeDT();	
		ddDT.setKey("BET");
		ddDT.setValue("Between");
		list.add(ddDT);
		
		
		return list;
	}*/
	
	public ArrayList<Object> getStateList() {
		return CachedDropDowns.getStateList();
	}

	public Map<Object, Object> getSearchCriteriaArrayMap() {
		return searchCriteriaArrayMap;
	}

	public void setSearchCriteriaArrayMap(Map<Object, Object> searchCriteriaArrayMap) {
		this.searchCriteriaArrayMap = searchCriteriaArrayMap;
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
		String str = answer!=null?answer.trim():"";
		if(str.length() > 0) {
			String newKey = key+"_FILTER_TEXT";
				searchCriteriaArrayMap.put(newKey,str);
		}
	}
	
	public void clearAll() {
		getAttributeMap().clear();
		searchCriteriaArrayMap = new HashMap<Object,Object>();
	}

	public Collection<Object> getPatientVoCollection() {
		return patientVoCollection;
	}

	public void setPatientVoCollection(Collection<Object> patientVoCollection) {
		this.patientVoCollection = patientVoCollection;
	}

	public ArrayList<Object> getLabResultList() {
		
		CachedDropDownValues srtStatic = new CachedDropDownValues();
		String labs=srtStatic.getCodedResultValue();
		
		DropDownCodeDT dropdownEmpty = new DropDownCodeDT();
		labResultList.add(dropdownEmpty);
		
		String[] labArray = labs.split("\\|");
		for(int i=0; i< labArray.length; i++)
		{
			String[] pair = labArray[i].split("\\$");
			String key = pair[0];
			String value = pair[1];
			DropDownCodeDT dropdown = new DropDownCodeDT();
			dropdown.setKey(key);
			dropdown.setValue(value);
			
			labResultList.add(dropdown);
		}
		
		
		return labResultList;
	}

	public void setLabResultList(ArrayList<Object> labResultList) {
		this.labResultList = labResultList;
	}

	public ArrayList<Object> getOrganismList() {
		
		try{
		//TreeMap<Object,Object> treeMap = null;

		CachedDropDownValues srtStatic = new CachedDropDownValues();
		
		String organismString=srtStatic.getOrganismList();
		
		DropDownCodeDT dropdownEmpty = new DropDownCodeDT();
		organismList.add(dropdownEmpty);
		
		String[] organismArray = organismString.split("\\|");
		for(int i=0; i< organismArray.length; i++)
		{
			String[] pair = organismArray[i].split("\\$");
			String key = pair[0];
			String value = pair[1];
			DropDownCodeDT dropdown = new DropDownCodeDT();
			dropdown.setKey(key);
			dropdown.setValue(value);
			
			organismList.add(dropdown);
		}
		/*
		for(Map.Entry<Object,Object> entry : treeMap.entrySet()) {
			
			  organismList.add(entry);
			  
			}*/
		
		}catch(Exception e)//TODO: handle exception
		{
			
			
		}
		
		return organismList;
	}

	public void setOrganismList(ArrayList<Object> organismList) {
		this.organismList = organismList;
	}

public Object getEventIDList() {
		
		return CachedDropDowns.getCodedValueForType(NEDSSConstants.PHVS_EVN_SEARCH_ABC);
	}
	
public Object getResultedTestList() {
	ArrayList<Object> list = CachedDropDowns.getCodedValueForType("SEARCH_TEXT");
	
	for(int i=0; i<list.size(); i++){
		if(((DropDownCodeDT)list.get(i)).getKey().equalsIgnoreCase("") || ((DropDownCodeDT)list.get(i)).getKey().equalsIgnoreCase("NOTNULL")){
			list.remove(i);
			break;
		}
	}
	
	return list;
}


	/*public Object getDateList() {
		
		return CachedDropDowns.getCodedValueForType(NEDSSConstants.NBS_EVENT_SEARCH_DATES);
	}*/
	
	public ArrayList<Object> getUserList() {
		userList.clear();
		try{

			
			String userString=getUserCreateList();
			
			DropDownCodeDT dropdownEmpty = new DropDownCodeDT();
			userList.add(dropdownEmpty);
			
			String[] userArray = userString.split("\\|");
			for(int i=0; i< userArray.length; i++)
			{
				String[] pair = userArray[i].split("\\$");
				String key = pair[0];
				String value = pair[1];
				DropDownCodeDT dropdown = new DropDownCodeDT();
				dropdown.setKey(key);
				dropdown.setValue(value);
				
				userList.add(dropdown);
			}
		
	
			
			}catch(Exception e)//TODO: handle exception
			{
				
				
			}
			
			return userList;
		
	}
	/*public String docCreatedFullName(){
		return userList
	}*/
	public String getUserCreateList() {
		
		return userCreateList;
	}

	public void setUserCreateList(String userCreateList) {
		this.userCreateList = userCreateList;
	}

	public ArrayList<Object> getConditionL() {
		return conditionL;
	}

	public void setConditionL(ArrayList<Object> conditionL) {
		this.conditionL = conditionL;
	}

	public PersonSrchResultVO getPatientVO() {
		return patientVO;
	}

	public void setPatientVO(PersonSrchResultVO patientVO) {
		this.patientVO = patientVO;
	}

	public ArrayList<Object> getObservationTypesDD() {
		return observationTypesDD;
	}

	public ArrayList<Object> getDescriptionDD() {
		return descriptionDD;
	}

	public ArrayList<Object> getConditionDD() {
		return conditionDD;
	}

	/**
	 * storeCustomQueue:
	 * @param queueName
	 */
	public String storeCustomQueue(String queueName, String descriptionQueue, String sourceTable, boolean publicQueue, String query){
		//System.out.println("storing in DB..");
		String finalQuery="";
		String searchCriteriaDesc = "";
		String searchCriteriaCd = "";
		
		 String messageInserted = "";
		 WebContext context = WebContextFactory.get();
			HttpServletRequest request = context.getHttpServletRequest();
			
			
		//TODO fatima: remove query parameter
			
			finalQuery = (String)request.getSession().getAttribute("finalQueryString");
			searchCriteriaCd  = (String)request.getSession().getAttribute("searchCriteriaCd");
			searchCriteriaDesc = (String)request.getSession().getAttribute("searchCriteriaDesc");
			
		try{
			
			String sBeanJndiName = JNDINames.TASKLIST_PROXY_EJB;
	        String sMethod = "saveCustomQueue";
	        String listOfUsers = "";
	        
	        if(publicQueue)
	        	listOfUsers = "ALL";//This is temporal, until we get this information from somewhere. It represents a list of the users that can see this queue, for example, 1000000, or public, or all, or a couple of users, etc.
	        else
	        	listOfUsers = "Current";
	        Object[] oParams = new Object[] {queueName.trim(), sourceTable, finalQuery, descriptionQueue,listOfUsers, searchCriteriaDesc, searchCriteriaCd};
	        Object obj = CallProxyEJB.callProxyEJB(oParams,sBeanJndiName,sMethod,request.getSession());
	        messageInserted = (String) obj;
	        
		}catch(Exception e){
			logger.error("PersonSearchForm.storeCustomQueue: "+ e.toString());
			e.printStackTrace();
		}
		return messageInserted;		
	}
	
	
	/**
	 * deleteCustomQueue: the custom queue gets deleted from the DB if the current user created it.
	 * @param queueName
	 * @param descriptionQueue
	 * @param sourceTable
	 * @param publicQueue
	 * @param query
	 * @return
	 */
	public String deleteCustomQueue(String queueName){
		//System.out.println("deleting from DB..");

		String messageInserted = "";
		WebContext context = WebContextFactory.get();
		HttpServletRequest request = context.getHttpServletRequest();
			
		
		try{
			
			String sBeanJndiName = JNDINames.TASKLIST_PROXY_EJB;
	        String sMethod = "deleteCustomQueue";

	        Object[] oParams = new Object[] {queueName};
	        Object obj = CallProxyEJB.callProxyEJB(oParams,sBeanJndiName,sMethod,request.getSession());
	        messageInserted = (String) obj;
		}catch(Exception e){
			logger.error("PersonSearchForm.storeCustomQueue: "+ e.toString());
			e.printStackTrace();
		}

    	
		return messageInserted;		
	}
	
	/**
	 * getSelectDisplaySection: displays the search critria results for Lab Report or Morbidity Report or Case Report
	 * @return true or false based on report type
	 */
	public boolean getSelectDisplaySection() {
		String  reportType=null != getAttributeMap().get("reportType")? getAttributeMap().get("reportType").toString():NEDSSConstants.LAB_MOR_CASE_REPORT;
		if(NEDSSConstants.LAB_REPORT_EVENT_ID.equalsIgnoreCase(reportType) || NEDSSConstants.LAB_MOR_CASE_REPORT.equalsIgnoreCase(reportType)) return true;
		else return false;
	}
	
	private String selectedcheckboxIdsLabs;
	private String nonSTDProcessingDecisionLogic;
	private String markAsReviewedComments;
	private String processingDecisionSelected;
	
	private String markAsReviewdReason;
	
	private String processingDecisionSelectedSTD;


	

	public String getProcessingDecisionSelectedSTD() {
		return processingDecisionSelectedSTD;
	}

	public void setProcessingDecisionSelectedSTD(String processingDecisionSelectedSTD) {
		this.processingDecisionSelectedSTD = processingDecisionSelectedSTD;
	}

	public String getMarkAsReviewdReason() {
		return markAsReviewdReason;
	}

	public void setMarkAsReviewdReason(String markAsReviewdReason) {
		this.markAsReviewdReason = markAsReviewdReason;
	}

	public Object getStdHivProcessingDecisionList() {
		return CachedDropDowns.getCodedValueForType(NEDSSConstants.STD_PROCESSING_DECISION_LIST_SYPHILIS_AND_NONSYPHILIS);
	}

	public String getProcessingDecisionSelected() {
		return processingDecisionSelected;
	}
	
	public void setProcessingDecisionSelected(String processingDecisionSelected) {
		this.processingDecisionSelected = processingDecisionSelected;
	}
	public String getSelectedcheckboxIdsLabs() {
		return selectedcheckboxIdsLabs;
	}

	public void setSelectedcheckboxIdsLabs(String selectedcheckboxIdsLabs) {
		this.selectedcheckboxIdsLabs = selectedcheckboxIdsLabs;
	}
	
	

	public Object getNonSTDProcessingDecisionList(){
		return CachedDropDowns.getCodedValueForType(NEDSSConstants.NBS_NO_ACTION_RSN);
	}
	public String getNonSTDProcessingDecisionLogic() {
		return nonSTDProcessingDecisionLogic;
	}
	
	public void setNonSTDProcessingDecisionLogic(String nprocessingDecisionLogic) {
		this.nonSTDProcessingDecisionLogic = nprocessingDecisionLogic;
	}
	
	public String getMarkAsReviewedComments() {
		return markAsReviewedComments;
	}

	public void setMarkAsReviewedComments(String markAsReviewedComments) {
		this.markAsReviewedComments = markAsReviewedComments;
	}
	
}