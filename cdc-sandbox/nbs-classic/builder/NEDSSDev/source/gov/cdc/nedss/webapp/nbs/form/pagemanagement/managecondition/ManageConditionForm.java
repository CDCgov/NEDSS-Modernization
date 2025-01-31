package gov.cdc.nedss.webapp.nbs.form.pagemanagement.managecondition;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.webapp.nbs.form.util.BaseForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.PageManagementActionUtil;

public class ManageConditionForm extends BaseForm{
	PageManagementActionUtil manageCondUtil = new PageManagementActionUtil();
	Map<Object,Object> searchMap = new HashMap<Object,Object>();
	ArrayList<?> oldManageList = new ArrayList<Object> ();
	Object selection = new Object();
	Object oldDT = new Object();
	private String actionMode;
	private String returnToLink;
	private Map<Object,Object> attributeMap = new HashMap<Object,Object>();
	private int srchFldCount;

	//For Filtering/sorting
	Map<Object,Object> searchCriteriaArrayMap = new HashMap<Object,Object>();
	private ArrayList<Object> programArea = new ArrayList<Object> ();
    private ArrayList<Object> associatedPage = new ArrayList<Object> ();
	private ArrayList<Object> nndCondition = new ArrayList<Object> ();
	private ArrayList<Object> status = new ArrayList<Object> ();
	private ArrayList<Object> conditionFamily = new ArrayList<Object> ();
	private String sectionCondition;
	private ArrayList<Object> condition= new ArrayList<Object> ();
	private ArrayList<Object> code= new ArrayList<Object> ();
	private ArrayList<Object> coinfGroup = new ArrayList<Object> ();
	
//	private ArrayList<Object> coInfGroupList = new ArrayList<Object> ();
	
	
	
		
	public ArrayList<Object> getCondition() {
		return condition;
	}

	public void setCondition(ArrayList<Object> condition) {
		this.condition = condition;
	}

	public ArrayList<Object> getCode() {
		return code;
	}

	public void setCode(ArrayList<Object> code) {
		this.code = code;
	}

	public String getSectionCondition() {
		return sectionCondition;
	}

	public void setSectionCondition(String sectionCondition) {
		this.sectionCondition = sectionCondition;
	}

	public void initializeDropDowns(ArrayList<Object> conditionDTColl)throws Exception {
		programArea = manageCondUtil.getProgramAreaDowns(conditionDTColl);
		associatedPage = manageCondUtil.getAssociatedPageDropDowns(conditionDTColl);
		nndCondition = manageCondUtil.getNndConditionDropDowns(conditionDTColl);
		status = manageCondUtil.getStatusDropDowns(conditionDTColl);
		conditionFamily = manageCondUtil.getConditionFamilyDropDowns(conditionDTColl);
		coinfGroup = manageCondUtil.getCoInfGroupDropDowns(conditionDTColl);
     }
	
	public ArrayList<Object> getProgramArea() {
		return programArea;
	}

	public void setProgramArea(ArrayList<Object> programArea) {
		this.programArea = programArea;
	}

	public ArrayList<Object> getAssociatedPage() {
		return associatedPage;
	}

	public void setAssociatedPage(ArrayList<Object> associatedPage) {
		this.associatedPage = associatedPage;
	}

	public ArrayList<Object> getNndCondition() {
		return nndCondition;
	}

	public void setNndCondition(ArrayList<Object> nndCondition) {
		this.nndCondition = nndCondition;
	}

	public ArrayList<Object> getStatus() {
		return status;
	}

	public void setStatus(ArrayList<Object> status) {
		this.status = status;
	}

	public ArrayList<Object> getConditionFamily() {
		return conditionFamily;
	}

	public void setConditionFamily(ArrayList<Object> conditionFamily) {
		this.conditionFamily = conditionFamily;
	}

	public void clearAll() {
			getAttributeMap().clear();
			condition = new ArrayList();
			code = new ArrayList();
			searchCriteriaArrayMap = new HashMap<Object,Object>();
	}
	public Map<Object,Object> getSearchCriteriaArrayMap() {
		return searchCriteriaArrayMap;
	}
	public void setSearchCriteriaArrayMap(Map<Object,Object> searchCriteriaArrayMap) {
		this.searchCriteriaArrayMap = searchCriteriaArrayMap;
	}
	
	public int getSrchFldCount() {
		return srchFldCount;
	}
	public void setSrchFldCount(int srchFldCount) {
		this.srchFldCount = srchFldCount;
	}
	public Map<Object,Object> getSearchMap() {
		return searchMap;
	}
	public void setSearchMap(Map<Object,Object> searchMap) {
		this.searchMap = searchMap;
	}
	
	public void setSearchCriteria(String key, String answer) {
		searchMap.put(key, answer);
	}

	public String getSearchCriteria(String key) {
		return (String) searchMap.get(key);
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
	public Map<Object,Object> getAttributeMap() {
		return attributeMap;
	}
	public void setAttributeMap(Map<Object, Object> attributeMap) {
		this.attributeMap = attributeMap;
	}	
	
	public ArrayList<Object> getCoinfGroup() {
		return coinfGroup;
	}

	public void setCoinfGroup(ArrayList<Object> coinfGroup) {
		this.coinfGroup = coinfGroup;
	}

	public void clearSelections() {
		this.setSearchMap(new HashMap<Object,Object>());
		this.resetSelection();
		this.setAttributeMap(new HashMap<Object,Object>());
		this.setActionMode(null);
		this.setReturnToLink(null);
		
	}
	public ArrayList<Object> getProgramAreaList(){
		return CachedDropDowns.getProgramAreaList();
	}

	public ArrayList<Object> getConditionList(){
		return CachedDropDowns.getAllConditions();
	}
	
	public ArrayList<Object> getLaboratoryIds(){
		return CachedDropDowns.getLaboratoryIds();
	}

	public ArrayList<Object> getTestTypeList(){
		return CachedDropDowns.getTestTypeList();
	}

	public ArrayList<Object> getAllCodeSetNms(){
		return CachedDropDowns.getAllCodeSetNms();
	}
	public ArrayList<Object> getCodedValues(String codeSetNm){
		return CachedDropDowns.getCachedDropDownList(codeSetNm);
	}
	
	

//	public ArrayList<Object> getCoInfGroupList() {
//		coInfGroupList=CachedDropDowns.getConditionCoInfGroup();
//		return coInfGroupList;
//	}

//	public void setCoInfGroupList(ArrayList<Object> coInfGroupList) {
//		this.coInfGroupList = coInfGroupList;
//	}

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
			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);
			deepCopy= ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return deepCopy;
	}	

	public Object getCodedValueNoBlnk(String key) {
		ArrayList<?> list = (ArrayList<?> ) getCodedValue(key);
		DropDownCodeDT dt = (DropDownCodeDT) list.get(0);
		if(dt.getKey() != null && dt.getKey().equals("") && dt.getValue() != null && dt.getValue().equals(""))
			list.remove(0);
		return list;
	}
	public Object getCodedValue(String key) {
		ArrayList<Object> aList = (ArrayList<Object> ) CachedDropDowns.getCodedValueForType(key);
		return aList;
	}

	public Object getSearchCriteriaDropDown() {
		ArrayList<Object> list = new ArrayList<Object> ();
		DropDownCodeDT dt = new DropDownCodeDT();DropDownCodeDT dt1 = new DropDownCodeDT();DropDownCodeDT dt2 = new DropDownCodeDT();
		//dt.setKey("");dt.setValue("");list.add(dt);
		dt1.setKey("CT"); dt1.setValue("Contains"); list.add(dt1);
		dt2.setKey("="); dt2.setValue("Equal"); list.add(dt2);
		return list;
	}
	public ArrayList<Object> getAllCodeSystemCdDescs(){
		String codeSetNm = searchMap.get("CODEVALGEN") == null ? "" : (String) searchMap.get("CODEVALGEN");
		return CachedDropDowns.getAllCodeSystemCdDescs(codeSetNm);
	}
	
	public ArrayList<?> getOldManageList() {
		return oldManageList;
	}
	public void setOldManageList(ArrayList<?>  oldManageList) {
		this.oldManageList = oldManageList;
	}

	public ArrayList<Object> getSRTAdminAssignAuth(){
		return CachedDropDowns.getSRTAdminAssignAuth();
	}
	public ArrayList<Object> getSRTAdminCodingSysCd(){
		return CachedDropDowns.getSRTAdminCodingSysCd();
	}

	public ArrayList<Object> getCodingSystemCodes(String assignAuthority) {
		return CachedDropDowns.getCodingSystemCodes(assignAuthority);

	}
	
	public ArrayList<Object> getConditionShortNm() throws Exception {
		return manageCondUtil.getConditionShortNm();

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
	public String getParentIsCdDesc(String parentIsCd) throws Exception
	{
		String codeSrtNm = null;
		codeSrtNm = manageCondUtil.getConditionShortNmDesc(parentIsCd);
		
		return codeSrtNm;
		
	}
    
	public void setAnswerArrayText(String key, String answer) {
		if(answer!=null && answer.length() > 0) {
			String newKey = key+"_FILTER_TEXT";
				searchCriteriaArrayMap.put(newKey,answer);
		}
	}
}
