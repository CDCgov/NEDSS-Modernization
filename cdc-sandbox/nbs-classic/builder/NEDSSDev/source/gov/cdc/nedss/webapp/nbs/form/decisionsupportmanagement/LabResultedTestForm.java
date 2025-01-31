package gov.cdc.nedss.webapp.nbs.form.decisionsupportmanagement;

import gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.vo.PhinVadsSystemVO;
import gov.cdc.nedss.srtadmin.dt.CodeSetDT;
import gov.cdc.nedss.srtadmin.dt.CodeValueGeneralDT;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.util.CodeValueGeneralCachedDT;
import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.webapp.nbs.action.srtadmin.util.SRTAdminConstants;
import gov.cdc.nedss.webapp.nbs.action.srtadmin.util.SRTAdminUtil;
import gov.cdc.nedss.webapp.nbs.form.util.BaseForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;

public class LabResultedTestForm extends BaseForm {

	private static final long serialVersionUID = 1L;
	
	SRTAdminUtil srtAdminUtil = new SRTAdminUtil();
	Map<Object,Object> searchMap = new HashMap<Object,Object>();
	ArrayList<?> manageList = new ArrayList<Object> ();
	ArrayList<?> oldManageList = new ArrayList<Object> ();
	ArrayList<?> codeValueGnList = new ArrayList<Object> ();
	Object selection = new Object();
	Object codeValGnSelection = new Object();
	Object oldDT = new Object();
	Object tmpCVGDT = new Object();
	private String actionMode;
	private String returnToLink;
	private Map<Object,Object> attributeMap = new HashMap<Object,Object>();
	private int srchFldCount;
	private PhinVadsSystemVO phinVadsSystemVo;
	
	//For Filtering/sorting
	Map<Object,Object> searchCriteriaArrayMap = new HashMap<Object,Object>();
	private Collection<Object>  codeSetDTColl;
	private ArrayList<Object> type = new ArrayList<Object> ();
	private ArrayList<Object> status = new ArrayList<Object> ();
	static final LogUtils logger = new LogUtils(LabResultedTestForm.class.getName());
	
	private ArrayList<Object> code= new ArrayList<Object> ();
	private ArrayList<Object> name= new ArrayList<Object> ();	
	private ArrayList<Object> description= new ArrayList<Object> ();	
	
	public Collection<Object> getCodeSetDTColl() {
		return codeSetDTColl;
	}
	public ArrayList<Object> getType() {
		return type;
	}
	public void setType(ArrayList<Object> type) {
		this.type = type;
	}
	public ArrayList<Object> getStatus() {
		return status;
	}
	public void setStatus(ArrayList<Object> status) {
		this.status = status;
	}
	public void setCodeSetDTColl(Collection<Object> codeSetDTColl) {
		this.codeSetDTColl = codeSetDTColl;
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
	
	public Map<Object,Object> getSearchCriteriaArrayMap() {
		return searchCriteriaArrayMap;
	}
	public void setSearchCriteriaArrayMap(Map<Object,Object> searchCriteriaArrayMap) {
		this.searchCriteriaArrayMap = searchCriteriaArrayMap;
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
		this.setAttributeMap(new HashMap<Object,Object>());
		this.setActionMode(null);
		this.setReturnToLink(null);
		
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

	public void clearAll() {
		getAttributeMap().clear();
		searchCriteriaArrayMap = new HashMap<Object,Object>();
		code= new ArrayList<Object> ();
		name= new ArrayList<Object> ();	
		description= new ArrayList<Object> ();
	}
	
	public ArrayList<Object> getCode() {
		return code;
	}
	public void setCode(ArrayList<Object> code) {
		this.code = code;
	}
	public ArrayList<Object> getName() {
		return name;
	}
	public void setName(ArrayList<Object> name) {
		this.name = name;
	}
	public ArrayList<Object> getDescription() {
		return description;
	}
	public void setDescription(ArrayList<Object> description) {
		this.description = description;
	}
}
