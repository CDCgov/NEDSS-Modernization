package gov.cdc.nedss.webapp.nbs.form.pam;


import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.action.pam.PamClientVO.PamClientVO;
import gov.cdc.nedss.webapp.nbs.action.pam.util.DWRUtil;
import gov.cdc.nedss.webapp.nbs.action.pam.util.PamConstants;
import gov.cdc.nedss.webapp.nbs.action.util.FileUploadUtil;
import gov.cdc.nedss.webapp.nbs.action.util.RulesEngineUtil;
import gov.cdc.nedss.webapp.nbs.form.managectassociation.ManageCTAssociateForm;
import gov.cdc.nedss.webapp.nbs.form.util.BaseForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.QuestionsCache;
import gov.cdc.nedss.webapp.nbs.servlet.NedssCodeLookupServlet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;

/**
 * Title: PamForm class. Description: Provides classes for creating and
 * modifying Pam Form Copyright: Copyright (c) 2008 Company: CSC
 *
 * @author Pradeep Sharma
 * @version
 */
/**
 * @author habraham2
 *
 */
public class PamForm extends BaseForm {

	private static final long serialVersionUID = 1L;

	private PamClientVO pamClientVO = new PamClientVO();

	private ArrayList<Object> dwrCounties = new ArrayList<Object> ();
	
	private ArrayList<Object> dwrStateSiteCounties = new ArrayList<Object> ();

	private String cityList;

	private String pamFormCd;

	private Map<Object,Object> formFieldMap = new HashMap<Object,Object>();

	private String dwrInvestigatorUid;
	private String dwrInvestigatorDetails;
	static final LogUtils logger = new LogUtils(PamForm.class.getName());

	//ORG
	private String dwrOrganizationDetails;
	private String dwrOrganizationUid;
	
	private String noNotfReqFieldsCheck;

	public PamClientVO getPamClientVO() {
		return pamClientVO;
	}

	public void setPamClientVO(PamClientVO pamClientVO) {
		this.pamClientVO = pamClientVO;
	}

	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		pamClientVO.reset(mapping, request);
	}

	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		ActionErrors errors = super.validate(mapping, request);
		if (errors == null)
			errors = new ActionErrors();
		return errors;
	}

	public Object getCodedValue(String key) {
		ArrayList<Object> aList = new ArrayList<Object>();
		//if (formFieldMap.containsKey(key)) {
		FormField fField = (FormField) formFieldMap.get(key);
		if(fField.getCodeSetNm()!=null)
			aList = CachedDropDowns.getCodedValueForType(fField.getCodeSetNm());
		//aList = fField.getAList();
	//}

		return aList;
	}

	public Object getCodedValueNoBlnk(String key) {
		ArrayList<?> list = (ArrayList<?> ) getCodedValue(key);
		DropDownCodeDT dt = (DropDownCodeDT) list.get(0);
		if(dt.getKey() != null && dt.getKey().equals("") && dt.getValue() != null && dt.getValue().equals(""))
			list.remove(0);
		if(list != null)
			return list;
		else 
		return new ArrayList<Object>();
	}

	public Object getRaceList(String key) {
		ArrayList<Object> aList = new ArrayList<Object> ();
			aList = CachedDropDowns.getRaceCodes(key);
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
	public ArrayList<Object> getIsoCountryList(){
		return CachedDropDowns.getIsoCountryList();
	}	
	public ArrayList<Object> getDwrCountiesForState(String state) {
		this.dwrCounties = CachedDropDowns.getCountyCodes(state);
		return dwrCounties;
	}
	
	/*public ArrayList<Object> getDefaultDwrStateSiteCounties(String state) {
		this.dwrStateSiteCounties = CachedDropDowns.getCountyCodes(state);
		return dwrStateSiteCounties;
	}*/

	public void setDwrCounties(ArrayList<Object>  dwrCounties) {
		this.dwrCounties = dwrCounties;
	}

	public ArrayList<Object> getDwrCounties() {
		return dwrCounties;
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

	public String getDwrInvestigatorDetails(String code, String identifier) {
		if (code != null && code.trim().equals(""))
			return "<font color=\"red\"><b>Please enter a code and try again or use the Search functionality.</b></font>";
		WebContext ctx = WebContextFactory.get();
		HttpServletRequest req = ctx.getHttpServletRequest();
		Map<?,?> returnMap = new HashMap<Object,Object>();
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
		String errorMsg = "<font color=\"red\"><b>'"
				+ code
				+ "' cannot be found. Please modify the entry and try again or use the Search functionality.</b></font>";
		dwrInvestigatorUid = returnMap.get("UID") == null ? "" : returnMap.get("UID").toString();
		if (dwrInvestigatorUid.equals(""))
			return errorMsg;

		dwrInvestigatorDetails = (String) returnMap.get("result");
		String versionCtrlNbr = (String) returnMap.get("versionCtrlNbr");		
		getAttributeMap().put(identifier+"Uid", (dwrInvestigatorUid + "|" + versionCtrlNbr));		
		getAttributeMap().put(identifier+"SearchResult",dwrInvestigatorDetails);
		sb.append(dwrInvestigatorUid).append("$$$$$").append(
				dwrInvestigatorDetails);
		return sb.toString();
	}

	public String getDwrInvestigatorDetailsByUid(String providerUid, String identifier) {

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
		String versionCtrlNbr = (String) returnMap.get("versionCtrlNbr");
		getAttributeMap().put(identifier+"Uid", (providerUid + "|" + versionCtrlNbr));
		getAttributeMap().put(identifier+"SearchResult",dwrInvestigatorDetails);
	    return dwrInvestigatorDetails;

	  }

	public String getDwrPopulateInvestigatorByUid(String providerUid, String investigator) {

		   WebContext ctx = WebContextFactory.get();
		   HttpServletRequest req = ctx.getHttpServletRequest();
		   Map<Object,Object> returnMap= new HashMap<Object,Object>();
		   getAttributeMap().put(investigator+"Uid", providerUid);
		   dwrInvestigatorDetails = (String)req.getSession().getAttribute("oneProvider") ;
		   getAttributeMap().put(investigator+"SearchResult",dwrInvestigatorDetails);
		   return dwrInvestigatorDetails;
	  }

	public void clearDWRInvestigator(String identifier) {
		getAttributeMap().remove(identifier+"Uid");
		getAttributeMap().remove(identifier+"SearchResult");
	}

	public ArrayList<?> clearDetailsAsian() {
		getPamClientVO().getArrayAnswerMap().remove(PamConstants.DETAILED_RACE_ASIAN);
		return (ArrayList<?> ) getRaceList("2028-9");
	}
	public ArrayList<?> clearDetailsHawaii() {
		getPamClientVO().getArrayAnswerMap().remove(PamConstants.DETAILED_RACE_HAWAII);
		return (ArrayList<?> ) getRaceList("2076-8");
	}


	public ArrayList<Object> fireRule(String fieldAndValue) throws NEDSSAppException{
		long  start=0;
        start = System.currentTimeMillis();
		logger.debug("........start.:"+start);
		String key = "";
		String value = "";
		String keyVal="";
		if (fieldAndValue != null && fieldAndValue.indexOf(":") >= 0) {
			String[] tokens = fieldAndValue.split(":");
			key = tokens[0];
			if(tokens.length==1)// user selected null value.
				keyVal="abcxyz";
			if(tokens.length>1 && tokens[1] != null && !(tokens[1].equals("")))
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
				fField.setValue("");
				
			}
			else if((valList == null ) || (valList!=null && valList.size()==0)){
				fField.setValue(value);
				fField.getState().setMultiSelVals(null);
			}
			formFieldMap.put(fField.getFieldId(), fField);
		}
		RulesEngineUtil util = new RulesEngineUtil();

		return (ArrayList<Object> )util.fireRules(key,this, formFieldMap);

	}

	public ArrayList<Object> validateNotificationReqFields() {
		 WebContext ctx = WebContextFactory.get();
		 HttpServletRequest req = ctx.getHttpServletRequest();
		 Map<?,?> questionMap = (TreeMap<?,?>)QuestionsCache.getQuestionMap().get(this.getPamFormCd());
		 ArrayList<Object> returnList = new ArrayList<Object> ();
		 try {
			 Map<?,?> reqMap = DWRUtil.createNotification(pamClientVO.getOldPamProxyVO().getPublicHealthCaseVO() ,questionMap, this.pamFormCd, req); 
			 if(reqMap != null && reqMap.size() > 0) {
				Iterator<?>  iter = reqMap.keySet().iterator();
				 while(iter.hasNext()) {
					 String key = (String) iter.next();
					 returnList.add(reqMap.get(key));
				 }
				 return returnList;		 
			 }
		} catch (Exception e) {
			logger.error("Error while Validating Notification Required Fields: " + e.toString());
			returnList.add("ERROR");
			return returnList;
		}
		 return null;
	}

	public ArrayList<Object> updateNotifications(String comments) throws NEDSSAppException {

		 WebContext ctx = WebContextFactory.get();
		 HttpServletRequest req = ctx.getHttpServletRequest();
		 return DWRUtil.updateNotifications(pamClientVO.getOldPamProxyVO().getPublicHealthCaseVO(), comments, req, pamFormCd);
	}


	public void initializeForm(ActionMapping mapping, HttpServletRequest request) {
		//QECodes autocomplete
		ArrayList<Object> qecList = CachedDropDowns.getAllQECodes(false,"PRV");
		request.getSession().setAttribute("qecList", qecList);
		ArrayList<Object> qecListORG = CachedDropDowns.getAllQECodes(false,"ORG");
		request.getSession().setAttribute("qecListORG", qecListORG);
	}

	public String getPamFormCd() {
		return pamFormCd;
	}

	public void setPamFormCd(String pamFormCd) {
		this.pamFormCd = pamFormCd;
	}

	public void setNotfReqFields() {
		getAttributeMap().put(PamConstants.REQ_FOR_NOTIF, "true");
		getAttributeMap().put(PamConstants.NO_REQ_FOR_NOTIF_CHECK, "false");
		
	}
	
	public String getDwrOrganizationDetailsByUid(String organizationUid, String identifier) {

		   WebContext ctx = WebContextFactory.get();
		   HttpServletRequest req = ctx.getHttpServletRequest();
		   Map<Object,Object> returnMap= new HashMap<Object,Object>();

		try {

			ArrayList<?> list = (ArrayList<?> )req.getSession().getAttribute("OrganizationSrchResults");

			if(organizationUid != null && organizationUid.trim().length() > 0)

				returnMap = NedssCodeLookupServlet.buildOrganizationResult(list, organizationUid, req.getSession());
		} catch (Exception e) {
			dwrOrganizationDetails =   "";
			dwrOrganizationUid = "";
			dwrOrganizationUid = organizationUid.toString();
			logger.error("Exception raised in getDwrOrganizationDetailsByUid: " + e);

		}
		dwrOrganizationUid = organizationUid.toString();
		dwrOrganizationDetails =   (String)returnMap.get("result");
		String versionCtrlNbr = (String) returnMap.get("versionCtrlNbr");
	
		getAttributeMap().put(identifier+"Uid", (dwrOrganizationUid + "|" + versionCtrlNbr));
		getAttributeMap().put(identifier+"SearchResult",dwrOrganizationDetails);
	   return dwrOrganizationDetails;

	 }
	 public void clearDWROrganization(String identifier) {
			getAttributeMap().remove(identifier+"Uid");
			getAttributeMap().remove(identifier+"SearchResult");
		}
	 public String getDwrOrganizationDetails(String code, String identifier) {
			if (code != null && code.trim().equals(""))
				return "<font color=\"red\"><b>Please enter a code and try again or use the Search functionality.</b></font>";
			WebContext ctx = WebContextFactory.get();
			HttpServletRequest req = ctx.getHttpServletRequest();
			Map<?,?> returnMap = new HashMap<Object,Object>();
			StringBuffer sb = new StringBuffer();
			try {
				returnMap = NedssCodeLookupServlet.getOrgValue(code, req
						.getSession());
			} catch (Exception e) {
				dwrOrganizationUid = "";
				dwrOrganizationDetails = "";
				logger.error("Exception raised in getDwrOrganizationDetails: " + e);
				e.printStackTrace();
			}
			String errorMsg = "<font color=\"red\"><b>'"
					+ code
					+ "' cannot be found. Please modify the entry and try again or use the Search functionality.</b></font>";
			dwrOrganizationUid =  returnMap.get("UID") == null ? "" : returnMap.get("UID").toString();
			if (dwrOrganizationUid.equals(""))
				return errorMsg;

			dwrOrganizationDetails = (String) returnMap.get("result");
			String versionCtrlNbr = (String) returnMap.get("versionCtrlNbr");			
			getAttributeMap().put(identifier+"Uid", (dwrOrganizationUid + "|" + versionCtrlNbr));			

			getAttributeMap().put(identifier+"SearchResult",dwrOrganizationDetails);
			sb.append(dwrOrganizationUid).append("$$$$$").append(
					dwrOrganizationDetails);
			return sb.toString();
		}
	 public String getDwrPopulateOrganizationByUid(String organizationUid, String identifier) {

		   WebContext ctx = WebContextFactory.get();
		   HttpServletRequest req = ctx.getHttpServletRequest();
		   Map<Object,Object> returnMap= new HashMap<Object,Object>();
		   getAttributeMap().put(identifier+"Uid", organizationUid);
		   dwrInvestigatorDetails = (String)req.getSession().getAttribute("oneOrganization") ;
		   getAttributeMap().put(identifier+"SearchResult",dwrInvestigatorDetails);
		   return dwrInvestigatorDetails;
	  }

	public ArrayList<Object> getDwrStateSiteCounties() {
		return dwrStateSiteCounties;
	}

	public void setDwrStateSiteCounties(ArrayList<Object>  dwrStateSiteCounties) {
			this.dwrStateSiteCounties = dwrStateSiteCounties;
	}
	
	public ArrayList<Object> getDwrDefaultStateCounties() {
		return CachedDropDowns.getCountyCodes(PropertyUtil.getInstance().getNBS_STATE_CODE());
	}	
    public ArrayList<Object> getExportFacilityList(){
		return CachedDropDowns.getExportFacilityListForTransferOwnership();
	}
    
    public String getExportJurisdictionList(){
    	Collection<Object>  coll = CachedDropDowns.getExportJurisdictionList();
    	StringBuffer buff = new StringBuffer();
    	if(coll!=null && coll.iterator()!=null){
    		java.util.Iterator<?> it = coll.iterator();
    		while(it.hasNext()){
    			DropDownCodeDT dropdownDT= (DropDownCodeDT)it.next();
    			buff.append(dropdownDT.getKey() + "|");
    			
    		}
    	}
    	return buff.toString();
	}
    
	public ArrayList<Object> getJurisdictionListNoExport(){
		return CachedDropDowns.getJurisdictionNoExpList();
	}

	public String isNoNotfReqFieldsCheck() {
		return noNotfReqFieldsCheck;
	}

	public void setNoNotfReqFieldsCheck(String noNotfReqFieldsCheck) {
		this.noNotfReqFieldsCheck = noNotfReqFieldsCheck;
	}
    
	public void callChildForm(String filler) {
		
		   WebContext ctx = WebContextFactory.get();
		   HttpServletRequest req = ctx.getHttpServletRequest();
		   ManageCTAssociateForm childForm = (ManageCTAssociateForm)req.getSession().getAttribute("manageCTAssociateForm");
		   childForm.updateCheckboxIds(filler);
		
		
	}
	
	/**
	 * dwr call to delete the attachment and return String 
	 * @param uid
	 * @return
	 */
	public boolean deleteAttachment(String uid) {
		boolean isDeleted = false;
		Long nbsCaseAttachUid = Long.valueOf(uid);
		WebContext ctx = WebContextFactory.get();
		HttpServletRequest req = ctx.getHttpServletRequest();	
		FileUploadUtil fileUpUtil = new FileUploadUtil(); 
		try {
			fileUpUtil.processRequest(nbsCaseAttachUid, req.getSession());
			isDeleted = true;
		} catch (Exception e) {				
			e.printStackTrace();
			logger.error("Error while deleting NBS Case Attachment for uid: " + uid);
		}

		return isDeleted;
	}

	public void clearErrorList(){
		setErrorList(new ArrayList<Object> ());
	}
    
    
	public String getDwrPopulateResultedTestByUid(String description,
			String testCode) {
		String descriptionWithCode=description+" ("+testCode+")";
		getAttributeMap().put("ResultedTestDescriptionWithCode", descriptionWithCode);
		getAttributeMap().put("ResultDescription", description);
		getAttributeMap().put("ResultCode", testCode);
		return descriptionWithCode;
	}
	

}
