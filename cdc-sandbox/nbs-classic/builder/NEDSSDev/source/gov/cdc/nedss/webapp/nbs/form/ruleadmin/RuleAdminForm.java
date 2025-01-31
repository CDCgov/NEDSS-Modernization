package gov.cdc.nedss.webapp.nbs.form.ruleadmin;


import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.webapp.nbs.action.pam.PamClientVO.PamClientVO;
import gov.cdc.nedss.webapp.nbs.action.pam.util.DWRUtil;
import gov.cdc.nedss.webapp.nbs.action.pam.util.PamConstants;
import gov.cdc.nedss.webapp.nbs.action.util.RulesEngineUtil;
import gov.cdc.nedss.webapp.nbs.form.pam.FormField;
import gov.cdc.nedss.webapp.nbs.form.pam.PamForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.QuestionsCache;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.RuleAdminHelper;
import gov.cdc.nedss.webapp.nbs.servlet.NedssCodeLookupServlet;



import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;

/**
 * Title: PamForm class. Description: Provides classes for creating and
 * modifying Pam Form Copyright: Copyright (c) 2008 Company: CSC
 *
 * @author Pradeep Sharma
 * @version
 */
public class RuleAdminForm extends PamForm {

	private static final long serialVersionUID = 1L;


	
	Map<Object,Object> searchMap = new HashMap<Object,Object>();
	ArrayList<Object> manageList = new ArrayList<Object> ();
	ArrayList<Object> oldManageList = new ArrayList<Object> ();
	
	
	ArrayList<Object> ruleInsList = new ArrayList<Object> ();
	ArrayList<Object> srcFieldList = new ArrayList<Object> ();
	ArrayList<Object> srcValueList = new ArrayList<Object> ();
	ArrayList<Object> tarFieldList = new ArrayList<Object> ();
	ArrayList<Object> tarValueList = new ArrayList<Object> ();
	ArrayList<Object> frmCodeList = new ArrayList<Object> ();
	
	Object selection = new Object();
	Object oldDT = new Object();
	Object oldSFDT = new Object();
	Object oldTFDT = new Object();
	Object oldSVDT = new Object();
	Object oldTVDT = new Object();
	Object tmpCVGDT = new Object();
	
	private String returnToLink;
	
	private int srchFldCount;
	
	static final LogUtils logger = new LogUtils(RuleAdminForm.class.getName());
	
	
	
	/*public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		pamClientVO.reset(mapping, request);
		errorList = new ArrayList<Object> ();
	}*/

	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		ActionErrors errors = super.validate(mapping, request);
		if (errors == null)
			errors = new ActionErrors();
		return errors;
	}

/*	public Object getCodedValue(String key) {
		ArrayList<Object> aList = new ArrayList<Object> ();
		if (formFieldMap.containsKey(key)) {
			FormField fField = (FormField) formFieldMap.get(key);
			aList = (ArrayList<Object> ) CachedDropDowns.getCodedValueForType(fField
					.getCodeSetNm());
		}
		return aList;
	}
	
	public Object getCodedValueNoBlnk(String key) {
		ArrayList<Object> list = (ArrayList<Object> ) getCodedValue(key);
		DropDownCodeDT dt = (DropDownCodeDT) list.get(0);
		if(dt.getKey() != null && dt.getKey().equals("") && dt.getValue() != null && dt.getValue().equals(""))
			list.remove(0);
		return list;
	}*/

	/*public String getActionMode() {
		return actionMode;
	}

	public void setActionMode(String actionMode) {
		this.actionMode = actionMode;
	}

	public String getSecurity(String key) {
		return (String)securityMap.get(key);
	}

	public void setSecurityMap(Map securityMap) {
		this.securityMap = securityMap;
	}

	public Map<Object,Object> getSecurityMap() {
		return securityMap;
	}

	public Map<Object,Object> getFormFieldMap() {
		return formFieldMap;
	}

	public void setFormFieldMap(Map formFieldMap) {
		this.formFieldMap = formFieldMap;
	}
	
	public FormField getField(String key) {
		return (FormField) formFieldMap.get(key);
	}
	
	public void initializeForm(ActionMapping mapping, HttpServletRequest request) {		
		//QECodes autocomplete
		ArrayList<Object> qecList = CachedDropDowns.getAllQECodes(false);
		request.getSession().setAttribute("qecList", qecList);		
	}

	public ArrayList<Object> getErrorList() {
		return errorList;
	}

	public void setErrorList(ArrayList<Object>  errorList) {
		this.errorList = errorList;
	}

	public Object[] getErrorTabs() {
		return errorTabs;
	}

	public void setErrorTabs(Object[] errorTabs) {
		this.errorTabs = errorTabs;
	}


	public String getTabId() {
		return tabId;
	}

	public void setTabId(String tabId) {
		this.tabId = tabId;
	}*/
	
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
	
	public ArrayList<Object> getRuleList(){
		return  RuleAdminHelper.getRules();
	}

	public ArrayList<Object> getConseqInd(){
		RuleAdminHelper ruleAdminHelper = new RuleAdminHelper();
		return  ruleAdminHelper.getConseqInd();
	}

	public ArrayList<Object> getErrorMessages(){
		return  RuleAdminHelper.getErrorMessages();
	}

   public ArrayList<Object> getOperatorTypes(){
		return  RuleAdminHelper.getOperatorTypes();
	}

	 public ArrayList<Object> getPAMLabels(){
		return  RuleAdminHelper.getPAMLabels();
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

	/*public Object getCodedValueNoBlnk(String key) {
		ArrayList<Object> list = (ArrayList<Object> ) getCodedValue(key);
		DropDownCodeDT dt = (DropDownCodeDT) list.get(0);
		if(dt.getKey() != null && dt.getKey().equals("") && dt.getValue() != null && dt.getValue().equals(""))
			list.remove(0);
		return list;
	}
	public Object getCodedValue(String key) {
		ArrayList<Object> aList = (ArrayList<Object> ) CachedDropDowns.getCodedValueForType(key);
		return aList;
	}*/

	public Object getSearchCriteriaDropDown() {
		ArrayList<Object> list = new ArrayList<Object> ();
		DropDownCodeDT dt = new DropDownCodeDT();DropDownCodeDT dt1 = new DropDownCodeDT();DropDownCodeDT dt2 = new DropDownCodeDT();
		//dt.setKey("");dt.setValue("");list.add(dt);
		dt1.setKey("CT"); dt1.setValue("Contains"); list.add(dt1);
		dt2.setKey("="); dt2.setValue("Equal"); list.add(dt2);
		return list;
	}
	/*public ArrayList<Object> getAllCodeSystemCdDescs(){
		String codeSetNm = searchMap.get("CODEVALGEN") == null ? "" : (String) searchMap.get("CODEVALGEN");
		return CachedDropDowns.getAllCodeSystemCdDescs(codeSetNm);
	}*/
	
	public ArrayList<Object> getOldManageList() {
		return oldManageList;
	}
	public void setOldManageList(ArrayList<Object>  oldManageList) {
		this.oldManageList = oldManageList;
	}

	public ArrayList<Object> getRuleInsList() {
		return ruleInsList;
	}

	public void setRuleInsList(ArrayList<Object>  ruleInsList) {
		this.ruleInsList = ruleInsList;
	}

	public ArrayList<Object> getSrcFieldList() {
		return srcFieldList;
	}

	public void setSrcFieldList(ArrayList<Object>  srcFieldList) {
		this.srcFieldList = srcFieldList;
	}

	public ArrayList<Object> getSrcValueList() {
		return srcValueList;
	}

	public void setSrcValueList(ArrayList<Object>  srcValueList) {
		this.srcValueList = srcValueList;
	}

	public ArrayList<Object> getTarFieldList() {
		return tarFieldList;
	}

	public void setTarFieldList(ArrayList<Object>  tarFieldList) {
		this.tarFieldList = tarFieldList;
	}

	public ArrayList<Object> getTarValueList() {
		return tarValueList;
	}

	public void setTarValueList(ArrayList<Object>  tarValueList) {
		this.tarValueList = tarValueList;
	}

	public Object getOldSFDT() {
		return oldSFDT;
	}

	public void setOldSFDT(Object oldSFDT) {
		this.oldSFDT = copyObject(oldSFDT);
	}

	public Object getOldTFDT() {
		return oldTFDT;
	}

	public void setOldTFDT(Object oldTFDT) {
		this.oldTFDT = copyObject(oldTFDT);
	}
	
	public Object getOldSVDT() {
		return oldSVDT;
	}

	public void setOldSVDT(Object oldSVDT) {
		this.oldSVDT = copyObject(oldSVDT);
	}
	
	public Object getOldTVDT() {
		return oldTVDT;
	}

	public void setOldTVDT(Object oldTVDT) {
		this.oldTVDT = copyObject(oldTVDT);
	}

	private PamClientVO pamClientVO = new PamClientVO();

	private ArrayList<Object> dwrCounties = new ArrayList<Object> ();

	private String actionMode;

	private String cityList;
	
	private String pamFormCd;
	
	private Map<Object,Object> securityMap = new HashMap<Object,Object>();
	
	private Map<Object,Object> formFieldMap = new HashMap<Object,Object>();
	
	private Map<Object,Object> attributeMap = new HashMap<Object,Object>();
	
	private ArrayList<Object> errorList = new ArrayList<Object> ();
	
	private Object[] errorTabs  =new String[0];
	
	private String tabId;
	

	private String dwrInvestigatorUid;
	private String dwrInvestigatorDetails;
	
	
	public PamClientVO getPamClientVO() {
		return pamClientVO;
	}

	public void setPamClientVO(PamClientVO pamClientVO) {
		this.pamClientVO = pamClientVO;
	}

	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		pamClientVO.reset(mapping, request);
		errorList = new ArrayList<Object> ();
	}

	
	public Object getCodedValue(String key) {
		ArrayList<Object> aList = new ArrayList<Object> ();
		if (formFieldMap.containsKey(key)) {
			FormField fField = (FormField) formFieldMap.get(key);
			aList = (ArrayList<Object> ) CachedDropDowns.getCodedValueForType(fField
					.getCodeSetNm());
		}
		return aList;
	}
	
	public Object getCodedValueNoBlnk(String key) {
		ArrayList<?> list = (ArrayList<?> ) getCodedValue(key);
		DropDownCodeDT dt = (DropDownCodeDT) list.get(0);
		if(dt.getKey() != null && dt.getKey().equals("") && dt.getValue() != null && dt.getValue().equals(""))
			list.remove(0);
		return list;
	}

	public Object getRaceList(String key) {
		ArrayList<Object> aList = new ArrayList<Object> ();
			aList = (ArrayList<Object> ) CachedDropDowns.getRaceCodes(key);
		return aList;
	}
	//TODO:This method needs to be revisited
	public  ArrayList<Object> getDwrCityList(String stateCode){
		 WebContext ctx = WebContextFactory.get();
		 HttpServletRequest req = ctx.getHttpServletRequest();
		ArrayList<Object> cityList = new ArrayList<Object> ();
	    cityList = CachedDropDowns.getAllCitiesList(stateCode);
		 req.getSession().setAttribute("cityList", cityList);
		return cityList;
	}
	
	
//	TODO:This method needs to be revisited
	public void setCityList(String cityList) {
		this.cityList = cityList;
	}
//	TODO:This method needs to be revisited
	public String getCityList() {
		return cityList;
	}
//	TODO:This method needs to be revisited
	public  ArrayList<Object> getOnLoadCityList(String stateCode, HttpServletRequest req){
		ArrayList<Object> cityList = new ArrayList<Object> ();
	    cityList = CachedDropDowns.getAllCitiesList(stateCode);
		 req.getSession().setAttribute("cityList", cityList);
		return cityList;
	}
	public ArrayList<Object> getJurisdictionList(){
		return CachedDropDowns.getJurisdictionList();
	}
	public ArrayList<Object> getProgramAreaList(){
		return CachedDropDowns.getProgramAreaList();
	}
	public ArrayList<Object> getStateList(){
		return CachedDropDowns.getStateList();
	}
	public ArrayList<Object> getCountryList(){
		return CachedDropDowns.getCountryList();
	}
	public ArrayList<Object> getDwrCountiesForState(String state) {
		this.dwrCounties = CachedDropDowns.getCountyCodes(state);
		return dwrCounties;
	}

	public void setDwrCounties(ArrayList<Object>  dwrCounties) {
		this.dwrCounties = dwrCounties;
	}

	public ArrayList<Object> getDwrCounties() {
		return dwrCounties;
	}

	public String getActionMode() {
		return actionMode;
	}

	public void setActionMode(String actionMode) {
		this.actionMode = actionMode;
	}

	public String getSecurity(String key) {
		return (String)securityMap.get(key);
	}

	public void setSecurityMap(Map securityMap) {
		this.securityMap = securityMap;
	}

	public Map<Object,Object> getSecurityMap() {
		return securityMap;
	}

	public Map<Object,Object> getFormFieldMap() {
		return formFieldMap;
	}

	public void setFormFieldMap(Map<Object,Object> formFieldMap) {
		this.formFieldMap = formFieldMap;
	}
	
	public FormField getField(String key) {
		return (FormField) formFieldMap.get(key);
	}
	
	public String getDwrInvestigatorDetails(String code) {
		if (code != null && code.trim().equals(""))
			return "<font color=\"red\"><b>Please enter a code and try again or use the Search functionality.</b></font>";
		WebContext ctx = WebContextFactory.get();
		HttpServletRequest req = ctx.getHttpServletRequest();
		Map<Object,Object> returnMap = new HashMap<Object,Object>();
		StringBuffer sb = new StringBuffer();
		try {
			returnMap = NedssCodeLookupServlet.getPersonValue(null, code, req
					.getSession());
		} catch (Exception e) {
			dwrInvestigatorUid = "";
			dwrInvestigatorDetails = "";
			logger.error("Exception raised in PergetDwrInvestigatorDetails: " + e);
			e.printStackTrace();
		}
		Long personUid = (Long) returnMap.get("UID");
		String errorMsg = "<font color=\"red\"><b>'"
				+ code
				+ "' cannot be found. Please modify the entry and try again or use the Search functionality.</b></font>";
		if (personUid == null)
			return errorMsg;
		dwrInvestigatorUid = personUid.toString();
		dwrInvestigatorDetails = (String) returnMap.get("result");
		getAttributeMap().put("investigatorUid", dwrInvestigatorUid);
		getAttributeMap().put("completePersonSearchResult",dwrInvestigatorDetails);
		sb.append(dwrInvestigatorUid).append("$$$$$").append(
				dwrInvestigatorDetails);
		return sb.toString();
	}
	
	public String getDwrInvestigatorDetailsByUid(String providerUid) {
		
		   WebContext ctx = WebContextFactory.get();
		   HttpServletRequest req = ctx.getHttpServletRequest();
		   Map<Object,Object> returnMap= new HashMap<Object,Object>();
		   
		try {
			
			ArrayList<?> list = (ArrayList<?> )req.getSession().getAttribute("ProviderSrchResults");	
			
			if(providerUid != null && providerUid.trim().length() > 0)
				
				returnMap = NedssCodeLookupServlet.buildProviderResult(list, providerUid, req.getSession());
			
		} catch (Exception e) {
			dwrInvestigatorUid=  "";
			dwrInvestigatorDetails =   "";
			logger.error("Exception raised in getDwrInvestigatorDetailsByUid: " + e);
		}
		dwrInvestigatorDetails =   (String)returnMap.get("result");
		getAttributeMap().put("investigatorUid", providerUid);
		getAttributeMap().put("completePersonSearchResult",dwrInvestigatorDetails);
	    return dwrInvestigatorDetails;
	    
	  }	
	
	public String getDwrPopulateInvestigatorByUid(String providerUid) {
		
		   WebContext ctx = WebContextFactory.get();
		   HttpServletRequest req = ctx.getHttpServletRequest();
		   Map<Object,Object> returnMap= new HashMap<Object,Object>();
		   getAttributeMap().put("investigatorUid", providerUid);
		   dwrInvestigatorDetails = (String)req.getSession().getAttribute("oneProvider") ;
		   getAttributeMap().put("completePersonSearchResult",dwrInvestigatorDetails);
		   return dwrInvestigatorDetails;	    
	  }	
	
	public void clearDWRInvestigator() {
		getAttributeMap().remove("investigatorUid");
		getAttributeMap().remove("completePersonSearchResult");		
	}

	public ArrayList<Object> clearDetailsAsian() {
		getPamClientVO().getArrayAnswerMap().remove(PamConstants.DETAILED_RACE_ASIAN);
		return (ArrayList<Object> ) getRaceList("2028-9");
	}	
	public ArrayList<Object> clearDetailsHawaii() {
		getPamClientVO().getArrayAnswerMap().remove(PamConstants.DETAILED_RACE_HAWAII);
		return (ArrayList<Object> ) getRaceList("2076-8");
	}	
	
	
	public ArrayList<Object> fireRule(String fieldAndValue) throws NEDSSAppException{
		long  start=0;
        long  end=0;
		start = System.currentTimeMillis();
		logger.debug("........start.:"+start);
		String key = "";
		String value = "";
		String keyVal="";
		if (fieldAndValue != null && fieldAndValue.indexOf(":") >= 0) {
			String[] tokens = fieldAndValue.split(":");
			key = tokens[0];
			if(tokens[1] != null && !(tokens[1].equals("")))
				keyVal = tokens[1];
			if(keyVal !=null && keyVal.indexOf(",")==0 && keyVal.lastIndexOf(",")==0){
				String[] vals = keyVal.split(",");
				value = vals[1];
			}
			else{
				value = keyVal;
			}
			if ("abcxyz".equals(value)) {
				value = null;
			}
		}
		List<Object> valList= new ArrayList<Object> ();
		if(value !=null && value.lastIndexOf(",")>0){
			String[] values = value.split(",");
			if(values != null && values.length>0){
				for(int i = 0;i<values.length;i++){
					if(values[i] != null && !values[i].equals("")){
						valList.add(values[i]);  
					}
				}
			}
			
		}
		
		if (formFieldMap != null && formFieldMap.size() > 0) {
			FormField fField = (FormField)formFieldMap.get(key);
			if(valList!=null && valList.size()>0){
				fField.getState().setMultiSelVals(valList);
			}
			else if((valList == null ) || (valList!=null && valList.size()==0)){
				fField.setValue(value);
			}
			formFieldMap.put(fField.getFieldId(), fField);
		}
		RulesEngineUtil util = new RulesEngineUtil();
		return (ArrayList<Object> )util.fireRules(key, this, formFieldMap);
	}

	/*public ArrayList<Object> validateNotificationReqFields() {
		 WebContext ctx = WebContextFactory.get();		 
		 HttpServletRequest req = ctx.getHttpServletRequest();
		 Map<Object,Object> questionMap = (TreeMap)QuestionsCache.getQuestionMap().get(this.getPamFormCd());
		 try {
			return DWRUtil.createNotification(pamClientVO.getOldPamProxyVO().getPublicHealthCaseVO() ,questionMap, req);
		} catch (Exception e) {
			ArrayList<Object> returnList = new ArrayList<Object> ();
			returnList.add("ERROR");
			return returnList;
		}
	}*/
	
	/*public ArrayList<Object> updateNotifications(String comments) {
		
		 WebContext ctx = WebContextFactory.get();		 
		 HttpServletRequest req = ctx.getHttpServletRequest();
		 return DWRUtil.updateNotifications(pamClientVO, comments, req);
	}*/
	
	
	public void initializeForm(ActionMapping mapping, HttpServletRequest request) {		
		//QECodes autocomplete
		//ArrayList<Object> qecList = CachedDropDowns.getAllQECodes(false);
		//request.getSession().setAttribute("qecList", qecList);		
	}

	public ArrayList<Object> getErrorList() {
		return errorList;
	}

	public void setErrorList(ArrayList<Object>  errorList) {
		this.errorList = errorList;
	}

	/*public Map<Object,Object> getAttributeMap() {
		return attributeMap;
	}

	public void setAttributeMap(Map attributeMap) {
		this.attributeMap = attributeMap;
	}*/

	public String getPamFormCd() {
		return pamFormCd;
	}

	public void setPamFormCd(String pamFormCd) {
		this.pamFormCd = pamFormCd;
	}

	public Object[] getErrorTabs() {
		return errorTabs;
	}

	public void setErrorTabs(Object[] errorTabs) {
		this.errorTabs = errorTabs;
	}

	public void setNotfReqFields() {
		attributeMap.put(PamConstants.REQ_FOR_NOTIF, "true");
	}

	public String getTabId() {
		return tabId;
	}

	public void setTabId(String tabId) {
		this.tabId = tabId;
	}

	public ArrayList<Object> getFrmCodeList() {
		return frmCodeList;
	}

	public void setFrmCodeList(ArrayList<Object>  frmCodeList) {
		this.frmCodeList = frmCodeList;
	}

	
	
}
