package gov.cdc.nedss.webapp.nbs.form.summary;

import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.webapp.nbs.form.util.BaseForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

public class SearchSummaryDataForm extends BaseForm {

	
	private String condition;
	private String reportingArea;
	private Date reportDate;
	private static final long serialVersionUID = 1L;
	Map<Object,Object> searchMap = new HashMap<Object,Object>();
	ArrayList<Object> manageList = new ArrayList<Object> ();
	ArrayList<Object> oldManageList = new ArrayList<Object> ();
	Object selection = new Object();
	Object oldDT = new Object();
	Object tmpCVGDT = new Object();
	private String actionMode;
	private String returnToLink;
	private Map<Object,Object> attributeMap = new HashMap<Object,Object>();
	private int srchFldCount;
	
	

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
	public void setAttributeMap(Map<Object,Object> attributeMap) {
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

	public ArrayList<Object> getOldManageList() {
		return oldManageList;
	}
	public void setOldManageList(ArrayList<Object>  oldManageList) {
		this.oldManageList = oldManageList;
	}

	
	public ArrayList<Object> getSRTAdminCodingSysCd(){
		return CachedDropDowns.getSRTAdminCodingSysCd();
	}

	
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public String getReportingArea() {
		return reportingArea;
	}
	public void setReportingArea(String reportingArea) {
		this.reportingArea = reportingArea;
	}
	public Date getReportDate() {
		return reportDate;
	}
	public void setDate(Date reportDate) {
		this.reportDate = reportDate;
	}
}