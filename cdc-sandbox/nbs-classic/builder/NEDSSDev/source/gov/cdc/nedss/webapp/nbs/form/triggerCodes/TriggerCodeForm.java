package gov.cdc.nedss.webapp.nbs.form.triggerCodes;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import gov.cdc.nedss.systemservice.ejb.triggercodesejb.dt.TriggerCodesDT;
import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.webapp.nbs.action.triggercodes.util.TriggerCodesUtil;
import gov.cdc.nedss.webapp.nbs.form.util.BaseForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;
import gov.cdc.nedss.webapp.nbs.queue.GenericForm;
import gov.cdc.nedss.webapp.nbs.queue.GenericQueueUtil;
import gov.cdc.nedss.webapp.nbs.queue.QueueColumnDT;
import gov.cdc.nedss.webapp.nbs.queue.QueueDT;

public class TriggerCodeForm extends BaseForm {

	private static final long serialVersionUID = 1L;
	TriggerCodesUtil rsUtil = new TriggerCodesUtil();
	private String CLASS_NAME ="gov.cdc.nedss.systemservice.ejb.triggercodesejb.dt.TriggerCodesDT";
	GenericQueueUtil genericQueueUtil = new GenericQueueUtil();
	private ArrayList<Object> codingSystemList = new ArrayList<Object>();
	private ArrayList<Object> conditionList = new ArrayList<Object>();
	private ArrayList<Object> conditionListView = new ArrayList<Object>();
	private ArrayList<Object> operatorList = new ArrayList<Object>();
	
	
	Map<Object, Object> searchMap = new HashMap<Object, Object>();
	ArrayList<Object> manageList = new ArrayList<Object>();
	ArrayList<Object> queueList = new ArrayList<Object>();
	ArrayList<Object> oldManageList = new ArrayList<Object>();
	Object selection = new Object();
	Object oldDT = new Object();
	private String actionMode;
	private String returnToLink;
	private Map<Object, Object> attributeMap = new HashMap<Object, Object>();
	private int srchFldCount;
	private TriggerCodesDT trigCodesDT = new TriggerCodesDT();
	private String codingSystemSelected;
	private String codeSelected;
	private String displayNameOperatorSelected;
	private String displayNameSelected;


	private String defaultConditionSelected;
	TriggerCodesDT oldTrigCodesDT = new TriggerCodesDT();
	//ArrayList<Object> sendSysList = new ArrayList<Object>();
	
	private ArrayList<Object> programAreas = new ArrayList<Object>();
	private ArrayList<Object> codingSystems = new ArrayList<Object>();
	private ArrayList<Object> statusValues = new ArrayList<Object>();
	private ArrayList<Object> conditions = new ArrayList<Object>();


	private QueueDT queueDT;
	private ArrayList<QueueColumnDT> queueCollection;
	private String stringQueueCollection;
	// For Filtering/sorting
	Map<Object, Object> searchCriteriaArrayMap = new HashMap<Object, Object>();
	private Collection<Object> triggerCodesDTColl;
	
	
	
	public String getDisplayNameOperatorSelected() {
		return displayNameOperatorSelected;
	}

	public void setDisplayNameOperatorSelected(String displayNameOperatorSelected) {
		this.displayNameOperatorSelected = displayNameOperatorSelected;
	}

	public String getCodeSelected() {
		return codeSelected;
	}

	public void setCodeSelected(String codeSelected) {
		this.codeSelected = codeSelected;
	}

	public String getDisplayNameSelected() {
		return displayNameSelected;
	}

	public void setDisplayNameSelected(String displayNameSelected) {
		this.displayNameSelected = displayNameSelected;
	}

	public String getDefaultConditionSelected() {
		return defaultConditionSelected;
	}

	public void setDefaultConditionSelected(String defaultConditionSelected) {
		this.defaultConditionSelected = defaultConditionSelected;
	}

	public String getCodingSystemSelected() {
		return codingSystemSelected;
	}

	public void setCodingSystemSelected(String codingSystemSelected) {
		this.codingSystemSelected = codingSystemSelected;
	}

	/**
	 * @return the trigCodesDT
	 */
	public TriggerCodesDT getTrigCodesDT() {
		return trigCodesDT;
	}

	/**
	 * @param trigCodesDT the trigCodesDT to set
	 */
	public void setTrigCodesDT(TriggerCodesDT trigCodesDT) {
		this.trigCodesDT = trigCodesDT;
	}

	/**
	 * @return the oldTrigCodesDT
	 */
	public TriggerCodesDT getOldTrigCodesDT() {
		return oldTrigCodesDT;
	}

	/**
	 * @param oldTrigCodesDT the oldTrigCodesDT to set
	 */
	public void setOldTrigCodesDT(TriggerCodesDT oldTrigCodesDT) {
		this.oldTrigCodesDT = oldTrigCodesDT;
	}

	/**
	 * @return the triggerCodesDTColl
	 */
	public Collection<Object> getTriggerCodesDTColl() {
		return triggerCodesDTColl;
	}
	
	public ArrayList<Object> getConditionList(){
		
		//DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
		//dropDownCodeDT.setKey("(null)");
		//dropDownCodeDT.setValue("(null)");

		conditionList = CachedDropDowns.getAllConditions();
		//conditionList.add(1, dropDownCodeDT);
		
		
		return conditionList;
	}
	
	public ArrayList<Object> getConditionListView(){
		
		DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
		dropDownCodeDT.setKey("(null)");
		dropDownCodeDT.setValue("(null)");

		conditionListView = CachedDropDowns.getAllConditions();
		conditionListView.add(1, dropDownCodeDT);
		
		
		return conditionListView;
	}

	/**
	 * @param triggerCodesDTColl the triggerCodesDTColl to set
	 */
	public void setTriggerCodesDTColl(Collection<Object> triggerCodesDTColl) {
		this.triggerCodesDTColl = triggerCodesDTColl;
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

	public void initializeDropDowns(Collection<Object> observationColls, GenericForm genericForm, String className) {

		ArrayList<ArrayList<Object>> dropdownsToInitialize = new ArrayList<ArrayList<Object>>();

		dropdownsToInitialize = genericQueueUtil.initializeDropdowns(observationColls, queueDT, CLASS_NAME);

		codingSystems=dropdownsToInitialize.get(0);
		

		conditions=dropdownsToInitialize.get(1);

		programAreas=dropdownsToInitialize.get(2);

		statusValues=dropdownsToInitialize.get(3);
	}
	
	public void clearAll() {
		getAttributeMap().clear();
		searchCriteriaArrayMap = new HashMap<Object, Object>();
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

	public void setAnswerArrayText(String key, String answer) {
		if(answer!=null && answer.length() > 0) {
			String newKey = key+"_FILTER_TEXT";
				searchCriteriaArrayMap.put(newKey,answer);
		}
	}
	
	public int getSrchFldCount() {
		return srchFldCount;
	}

	public void setSrchFldCount(int srchFldCount) {
		this.srchFldCount = srchFldCount;
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

	public ArrayList<Object> getManageList() {
		return manageList;
	}

	public void setManageList(ArrayList<Object> manageList) {
		this.manageList = manageList;
	}

	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
	}

	public Object getSelection() {
		return selection;
	}

	public void setSelection(Object selection) {
		this.selection = copyObject(selection);
	}

	public void resetSelection() {
		this.selection = new Object();
		this.oldDT = new Object();
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

	public void clearSelections() {
		this.setSearchMap(new HashMap<Object, Object>());
		this.setManageList(new ArrayList<Object>());
		this.resetSelection();
		this.setAttributeMap(new HashMap<Object, Object>());
		this.setActionMode(null);
		this.setReturnToLink(null);

	}

	public Object getOldDT() {
		return oldDT;
	}

	public void setOldDT(Object oldDT) {
		this.oldDT = copyObject(oldDT);
	}

	private static Object copyObject(Object param) {
		Object deepCopy = null;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(param);
			ByteArrayInputStream bais = new ByteArrayInputStream(
					baos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);
			deepCopy = ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return deepCopy;
	}

	public ArrayList<Object> getOldManageList() {
		return oldManageList;
	}

	public void setOldManageList(ArrayList<Object> oldManageList) {
		this.oldManageList = oldManageList;
	}

	public ArrayList<Object> getStatusList() {
		ArrayList<Object> statusList = new ArrayList<Object>();
		DropDownCodeDT cdDT1 = new DropDownCodeDT();
		cdDT1.setKey(NEDSSConstants.STATUS_ACTIVE);
		cdDT1.setValue(NEDSSConstants.RECORD_STATUS_ACTIVE);
		statusList.add(cdDT1);
		DropDownCodeDT cdDT2 = new DropDownCodeDT();
		cdDT2.setKey(NEDSSConstants.STATUS_INACTIVE);
		cdDT2.setValue(NEDSSConstants.RECORD_STATUS_INACTIVE);
		statusList.add(cdDT2);
		return statusList;
	}

	public ArrayList<Object> getSendSysList() {
		return CachedDropDowns.getCodedValueForType("YN");
	}
/*
	public void setSendSysList(ArrayList<Object> sendSysList) {
		this.sendSysList = sendSysList;
	}
*/
	public Map<Object, Object> getSearchCriteriaArrayMap() {
		return searchCriteriaArrayMap;
	}

	public void setSearchCriteriaArrayMap(
			Map<Object, Object> searchCriteriaArrayMap) {
		this.searchCriteriaArrayMap = searchCriteriaArrayMap;
	}

	public ArrayList<Object> getQueueList() {
		return queueList;
	}

	public void setQueueList(ArrayList<Object> queueList) {
		this.queueList = queueList;
	}

	public Object getCodedValue(String key) {
		ArrayList<Object> aList = (ArrayList<Object>) CachedDropDowns
				.getCodedValueForType(key);

		if ("PUBLIC_HEALTH_EVENT".equals(key)) {
			Iterator iterator = aList.iterator();
			ArrayList<Object> tempList = new ArrayList<Object>();

			while (iterator.hasNext()) {
				DropDownCodeDT dropDownCodeDT = (DropDownCodeDT) iterator
						.next();
				if (!"CMR".equals(dropDownCodeDT.getKey()) && !"Z32".equals(dropDownCodeDT.getKey()))
					tempList.add(dropDownCodeDT);
			}
			aList = tempList;
		}

		return aList;
	}
	
	public ArrayList<Object> getLaboratoryIds(){
		return CachedDropDowns.getLaboratoryIds();
	}
	

	public String getCLASS_NAME() {
		return CLASS_NAME;
	}

	public void setCLASS_NAME(String cLASS_NAME) {
		CLASS_NAME = cLASS_NAME;
	}

	/**
	 * @return the programAreas
	 */
	public ArrayList<Object> getProgramAreas() {
		return programAreas;
	}

	/**
	 * @param programAreas the programAreas to set
	 */
	public void setProgramAreas(ArrayList<Object> programAreas) {
		this.programAreas = programAreas;
	}

	/**
	 * @return the codingSystems
	 */
	public ArrayList<Object> getCodingSystems() {
		return codingSystems;
	}

	/**
	 * @param codingSystems the codingSystems to set
	 */
	public void setCodingSystems(ArrayList<Object> codingSystems) {
		this.codingSystems = codingSystems;
	}

	/**
	 * @return the statusValues
	 */
	public ArrayList<Object> getStatusValues() {
		return statusValues;
	}

	/**
	 * @param statusValues the statusValues to set
	 */
	public void setStatusValues(ArrayList<Object> statusValues) {
		this.statusValues = statusValues;
	}

	/**
	 * @return the conditions
	 */
	public ArrayList<Object> getConditions() {
		return conditions;
	}

	/**
	 * @param conditions the conditions to set
	 */
	public void setConditions(ArrayList<Object> conditions) {
		this.conditions = conditions;
	}

	/**
	 * getCodingSystemList: this method returns the list of coding system for the dropdown in the trigger codes search screen
	 * @return
	 */
	public ArrayList<Object> getCodingSystemList(){
		DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
		dropDownCodeDT.setKey("");
		dropDownCodeDT.setValue("");

		codingSystemList = new CachedDropDownValues()
				.getCodedValuesList("CODE_SYSTEM_CRLM");
		codingSystemList.add(0, dropDownCodeDT);

		return codingSystemList;
	}
	
	
	public ArrayList<Object> getOperatorList(){
		DropDownCodeDT dropDownCodeDT = new DropDownCodeDT();
		dropDownCodeDT.setKey("CT");
		dropDownCodeDT.setValue("Contains");
		operatorList=new ArrayList<Object>();
		operatorList.add(0, dropDownCodeDT);
		DropDownCodeDT dropDownCodeDT2 = new DropDownCodeDT();
		dropDownCodeDT2.setKey("=");
		dropDownCodeDT2.setValue("Equal");
		operatorList.add(1, dropDownCodeDT2);
		
		return operatorList;
	}
	
	
	
	public String getDiseaseNameFromCode(String conditionCd){
		String diseaseNm = "";
		
		ArrayList<Object> conditionList = getConditionList();
		if(conditionList!=null)
		for(int i=0; i<conditionList.size(); i++){
			DropDownCodeDT dropdown = (DropDownCodeDT)(conditionList.get(i));
			String key = dropdown.getKey();
			
			if(key!=null && !key.isEmpty() && key.equalsIgnoreCase(conditionCd))
				diseaseNm = dropdown.getValue();
		}
		return diseaseNm;
	}
}
