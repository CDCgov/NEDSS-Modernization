package gov.cdc.nedss.webapp.nbs.form.localfields;

import gov.cdc.nedss.localfields.vo.NbsQuestionVO;
import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.webapp.nbs.form.util.BaseForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

/**
 * FormBean for LocalFields
 * @author nmallela
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: Computer Sciences Corporation</p>
 * LocalFieldsForm.java
 * Sep 2, 2008
 * @version
 */
public class LocalFieldsForm extends BaseForm {

	private static final long serialVersionUID = 1L;
	Map<Object,Object> searchMap = new HashMap<Object,Object>();
	ArrayList<Object> manageList = new ArrayList<Object> ();
	ArrayList<Object> oldManageList = new ArrayList<Object> ();
	Object selection = new NbsQuestionVO();
	Object oldDT = new NbsQuestionVO();
	private String returnToLink;
	private String pageName;
	private String ldfPageId;
	private String formCd;
	
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
	
	
	public ArrayList<Object> getManageList() {
		return manageList;
	}
	public void setManageList(ArrayList<Object>  manageList) {
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
	
	public String getReturnToLink() {
		return returnToLink;
	}
	public void setReturnToLink(String returnToLink) {
		this.returnToLink = returnToLink;
	}

	public void clearSelections() {
		this.setSearchMap(new HashMap<Object,Object>());
		this.setManageList(new ArrayList<Object>());
		this.resetSelection();
		getAttributeMap().clear();
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

	public ArrayList<Object> getAllCodeSystemCdDescs(){
		String codeSetNm = searchMap.get("CODEVALGEN") == null ? "" : (String) searchMap.get("CODEVALGEN");
		return CachedDropDowns.getAllCodeSystemCdDescs(codeSetNm);
	}
	
	public ArrayList<Object> getLdfHtmlTypes() {
		return CachedDropDowns.getLdfHtmlTypes(getFormCd());
	}
	
	/**
	 * returns a List<Object> of Tabs available for the appropriate Form Code
	 * @return
	 */
	public ArrayList<Object> getAvailableTabs() {		
		return CachedDropDowns.getAvailableTabs(getFormCd());
	}

	/**
	 * returns Sections for which LDFs can be added at the end
	 * @param id
	 * @return
	 */
	public ArrayList<Object> getLDFSections(String tabId) {
		Long tabUid = new Long(0);
		if(tabId != null && !tabId.equals("")) {
			if(tabId.equals("1")) {
				tabUid = ((NbsQuestionVO)getSelection()).getTabId();
			} else {
				tabUid = Long.valueOf(tabId);
			}
			return CachedDropDowns.getLDFSections(tabUid);			
		}
		return new ArrayList<Object> ();
	}	
	
	
	
	public ArrayList<Object> getLDFSubSections(String sectionId) {
		Long sectionUid = new Long(0);
		if(sectionId != null && sectionId.equals("1")) {
			sectionUid = ((NbsQuestionVO)getSelection()).getSectionId();
		} else {
			sectionUid = Long.valueOf(sectionId);
		}
		return CachedDropDowns.getLDFSubSections(sectionUid);
	}
	
	public ArrayList<Object> getCodesetNames() {
		return CachedDropDowns.getCodesetNames();
	}
	
	public ArrayList<Object> getOldManageList() {
		return oldManageList;
	}
	public void setOldManageList(ArrayList<Object>  oldManageList) {
		this.oldManageList = oldManageList;
	}
	public String getPageName() {
		return pageName;
	}
	public void setPageName(HttpServletRequest req) {
		String page = req.getSession().getAttribute("page") == null ? "NBS Page" : (String)req.getSession().getAttribute("page");
		page = getActionMode() + " " + page + " " + "LDFs";
		setPageTitle(page, req);
	}
	public String getLdfPageId() {
		return ldfPageId;
	}
	public void setLdfPageId(String ldfPageId) {
		this.ldfPageId = ldfPageId;
	}
	public String getFormCd() {
		return formCd;
	}
	public void setFormCd(String formCd) {
		this.formCd = formCd;
	}


}