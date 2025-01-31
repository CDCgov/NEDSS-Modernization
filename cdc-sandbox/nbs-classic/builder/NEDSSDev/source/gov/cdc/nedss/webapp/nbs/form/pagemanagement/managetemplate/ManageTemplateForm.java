package gov.cdc.nedss.webapp.nbs.form.pagemanagement.managetemplate;

import gov.cdc.nedss.systemservice.dt.EDXActivityLogDT;
import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.PageManagementActionUtil;
import gov.cdc.nedss.webapp.nbs.action.util.QueueUtil;
import gov.cdc.nedss.webapp.nbs.form.util.BaseForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.webapp.nbs.queue.GenericForm;
import gov.cdc.nedss.webapp.nbs.queue.GenericQueueUtil;
import gov.cdc.nedss.webapp.nbs.queue.QueueColumnDT;
import gov.cdc.nedss.webapp.nbs.queue.QueueDT;

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
import org.apache.struts.upload.FormFile;

public class ManageTemplateForm extends BaseForm{
	
	private static final long serialVersionUID = 1L;
	PageManagementActionUtil manageTempUtil = new PageManagementActionUtil();
	GenericQueueUtil genericQueueUtil = new GenericQueueUtil();
	Map<Object,Object> searchMap = new HashMap<Object,Object>();
	ArrayList<?> manageList = new ArrayList<Object> ();
	ArrayList<?> oldManageList = new ArrayList<Object> ();
	Object selection = new Object();
	Object oldDT = new Object();
	private String actionMode;
	private String returnToLink;
	private Map<Object,Object> attributeMap = new HashMap<Object,Object>();
	private int srchFldCount;
	private String CLASS_NAME = "gov.cdc.nedss.pagemanagement.wa.dt.WaTemplateDT";
	//For Filtering/sorting
	Map<Object,Object> searchCriteriaArrayMap = new HashMap<Object,Object>();
	private Collection<Object>  waTemplateDTColl;
	private ArrayList<Object> tempalateName = new ArrayList<Object> ();
    private ArrayList<Object> templateDescription = new ArrayList<Object> ();
    private QueueDT queueDT;
    private ArrayList<QueueColumnDT> queueCollection;
    private String stringQueueCollection;
	private ArrayList<Object> lastUpdated = new ArrayList<Object> ();
    private ArrayList<Object> lastUpdatedBy = new ArrayList<Object> ();
	private ArrayList<Object> source = new ArrayList<Object> ();
	private ArrayList<Object> status = new ArrayList<Object> ();
	private String sectionCondition;
	private EDXActivityLogDT edxActivityLogDT = new EDXActivityLogDT();
    private boolean clearFilter;

	public EDXActivityLogDT getEdxActivityLogDT() {
		return edxActivityLogDT;
	}

	public void setEdxActivityLogDT(EDXActivityLogDT edxActivityLogDT) {
		this.edxActivityLogDT = edxActivityLogDT;
	}

	private FormFile importFile ; 
	
	public String getSectionCondition() {
		return sectionCondition;
	}

	public void setSectionCondition(String sectionCondition) {
		this.sectionCondition = sectionCondition;
	}

	public ArrayList<Object> getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(ArrayList<Object> lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public ArrayList<Object> getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(ArrayList<Object> lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public ArrayList<Object> getSource() {
		return source;
	}

	public void setSource(ArrayList<Object> source) {
		this.source = source;
	}
/*
	public void initializeDropDowns()throws Exception {
		QueueUtil queueUtil = new QueueUtil();
		lastUpdated = queueUtil.getStartDateDropDownValues();
		lastUpdatedBy = manageTempUtil.getLastUpdatedByDropDowns(waTemplateDTColl);
		source = manageTempUtil.getSourceDropDowns(waTemplateDTColl);
		status = manageTempUtil.getRecordStatusDropDowns(waTemplateDTColl);
     }*/
	
	public void initializeDropDowns(Collection<Object> templateColls, GenericForm genericForm, String className) {

        ArrayList<ArrayList<Object>> dropdownsToInitialize = new ArrayList<ArrayList<Object>>();

        dropdownsToInitialize = genericQueueUtil.initializeDropdowns(templateColls, queueDT, CLASS_NAME);
        lastUpdated=dropdownsToInitialize.get(0);
        lastUpdatedBy=dropdownsToInitialize.get(1);
        source=dropdownsToInitialize.get(2);
        status=dropdownsToInitialize.get(3);
    }

	public ArrayList<Object> getStatus() {
		return status;
	}

	public void setStatus(ArrayList<Object> status) {
		this.status = status;
	}

	
	public void clearAll() {
			getAttributeMap().clear();
			searchCriteriaArrayMap = new HashMap<Object,Object>();
			setClearFilter(false);
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
	
	
	public ArrayList<?> getManageList() {
		return manageList;
	}
	public void setManageList(ArrayList<?>  manageList) {
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
	public Map<Object,Object> getAttributeMap() {
		return attributeMap;
	}
	public void setAttributeMap(Map<Object, Object> attributeMap) {
		this.attributeMap = attributeMap;
	}	
	
	public void clearSelections() {
		this.setSearchMap(new HashMap<Object,Object>());
		this.setManageList(new ArrayList<Object>());
		this.resetSelection();
		this.setAttributeMap(new HashMap<Object,Object>());
		this.setActionMode(null);
		this.setReturnToLink(null);
		
	}
	

	public ArrayList<Object> getAllCodeSetNms(){
		return CachedDropDowns.getAllCodeSetNms();
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
		return manageTempUtil.getConditionShortNm();

	}
	
	public String[] getAnswerArray(String key) {
		return (String[])searchCriteriaArrayMap.get(key);
	}
	public Collection<Object> getWaTemplateDTColl() {
		return waTemplateDTColl;
	}

	public void setWaTemplateDTColl(Collection<Object> waTemplateDTColl) {
		this.waTemplateDTColl = waTemplateDTColl;
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
	public String getParentIsCdDesc(String parentIsCd) throws Exception
	{
		String codeSrtNm = null;
		codeSrtNm = manageTempUtil.getConditionShortNmDesc(parentIsCd);
		
		return codeSrtNm;
		
	}

	public FormFile getImportFile() {
		return importFile;
	}

	public void setImportFile(FormFile importFile) {
		this.importFile = importFile;
	}
	/**
	 * @return the queueDT
	 */
	public QueueDT getQueueDT() {
		return queueDT;
	}

	/**
	 * @param queueDT the queueDT to set
	 */
	public void setQueueDT(QueueDT queueDT) {
		this.queueDT = queueDT;
	}

	/**
	 * @return the queueCollection
	 */
	public ArrayList<QueueColumnDT> getQueueCollection() {
		return queueCollection;
	}

	/**
	 * @param queueCollection the queueCollection to set
	 */
	public void setQueueCollection(ArrayList<QueueColumnDT> queueCollection) {
		this.queueCollection = queueCollection;
	}

	/**
	 * @return the stringQueueCollection
	 */
	public String getStringQueueCollection() {
		return stringQueueCollection;
	}

	/**
	 * @param stringQueueCollection the stringQueueCollection to set
	 */
	public void setStringQueueCollection(String stringQueueCollection) {
		this.stringQueueCollection = stringQueueCollection;
	}

	/**
	 * @return the tempalateName
	 */
	public ArrayList<Object> getTempalateName() {
		return tempalateName;
	}

	/**
	 * @param tempalateName the tempalateName to set
	 */
	public void setTempalateName(ArrayList<Object> tempalateName) {
		this.tempalateName = tempalateName;
	}

	/**
	 * @return the templateDescription
	 */
	public ArrayList<Object> getTemplateDescription() {
		return templateDescription;
	}

	/**
	 * @param templateDescription the templateDescription to set
	 */
	public void setTemplateDescription(ArrayList<Object> templateDescription) {
		this.templateDescription = templateDescription;
	}

	public String getCLASS_NAME() {
		return CLASS_NAME;
	}

	public void setCLASS_NAME(String cLASS_NAME) {
		CLASS_NAME = cLASS_NAME;
	}

	public boolean isClearFilter() {
		return clearFilter;
	}

	public void setClearFilter(boolean clearFilter) {
		this.clearFilter = clearFilter;
	}

	
    
}

