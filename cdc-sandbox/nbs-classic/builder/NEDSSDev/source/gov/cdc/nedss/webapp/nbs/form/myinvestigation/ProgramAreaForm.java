package gov.cdc.nedss.webapp.nbs.form.myinvestigation;

import gov.cdc.nedss.util.HTMLEncoder;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.webapp.nbs.action.myinvestigation.ProgramAreaUtil;
import gov.cdc.nedss.webapp.nbs.action.pam.PamClientVO.PamClientVO;
import gov.cdc.nedss.webapp.nbs.action.util.QueueUtil;
import gov.cdc.nedss.webapp.nbs.form.pam.FormField;
import gov.cdc.nedss.webapp.nbs.form.pam.PamForm;
import gov.cdc.nedss.webapp.nbs.form.util.BaseForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.webapp.nbs.servlet.NedssCodeLookupServlet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;


/**
 * @author habraham2
 *
 */
public class ProgramAreaForm extends BaseForm {
	private static final long serialVersionUID = 1L;
	private PamClientVO pamClientVO = new PamClientVO();
	Map<Object, Object> searchCriteriaArrayMap = new HashMap<Object,Object>();
	Map<Object, Object> searchMap = new HashMap<Object,Object>();
	ProgramAreaUtil prgAreaUtil = new ProgramAreaUtil();

	private ArrayList<Object> investigators = new ArrayList<Object> ();
	private ArrayList<Object> jurisdictions = new ArrayList<Object> ();
	private ArrayList<Object> conditions = new ArrayList<Object> ();
	private ArrayList<Object> caseStatus = new ArrayList<Object> ();
	private ArrayList<Object> dateFilterList = new ArrayList<Object> ();
	private ArrayList<Object> notifications = new ArrayList<Object> ();
	private String dwrInvestigatorUid;
	private String investigatorLocalId;
	private String dwrInvestigatorDetails;
	static final LogUtils logger = new LogUtils(ProgramAreaForm.class.getName());
	private String dateAssigned;
	private String investigatorSelected;
	private String investigationFormCode;
	
	private String selectedChkboxIdsInfo;
	
private String msgBlock="";


	
	public String getMsgBlock() {
	return msgBlock;
}
public void setMsgBlock(String msgBlock) {
	this.msgBlock = msgBlock;
}
	public String getInvestigatorLocalId() {
		return investigatorLocalId;
	}
	public void setInvestigatorLocalId(String investigatorLocalId) {
		this.investigatorLocalId = investigatorLocalId;
	}
	public String getSelectedChkboxIdsInfo() {
		return selectedChkboxIdsInfo;
	}
	public void setSelectedChkboxIdsInfo(String selectedChkboxIdsInfo) {
		this.selectedChkboxIdsInfo = selectedChkboxIdsInfo;
	}
	public String getInvestigationFormCode() {
		return investigationFormCode;
	}
	public void setInvestigationFormCode(String investigationFormCode) {
		this.investigationFormCode = investigationFormCode;
	}
	public String getInvestigatorSelected() {
		return investigatorSelected;
	}
	public void setInvestigatorSelected(String investigatorSelected) {
		this.investigatorSelected = investigatorSelected;
	}
	public String getDateAssigned() {
		return dateAssigned;
	}
	public void setDateAssigned(String dateAssigned) {
		this.dateAssigned = dateAssigned;
	}
	/**
	 * initializeDropDowns returns the latest set of distinct dropdown values for Investigator, 
	 * Patient etc Dropdown Lists in queue
	 */
	public void initializeDropDowns( ArrayList<Object> invSummaryVOColl) {
		QueueUtil queueUtil = new QueueUtil();
		investigators = prgAreaUtil.getInvestigatorDropDowns(invSummaryVOColl);
		jurisdictions = prgAreaUtil.getJurisDropDowns(invSummaryVOColl);
		conditions = prgAreaUtil.getConditionDropDowns(invSummaryVOColl);
		caseStatus = prgAreaUtil.getCaseStatusDropDowns(invSummaryVOColl);
		dateFilterList = queueUtil.getStartDateDropDownValues();
		notifications = prgAreaUtil.getNotificationValuesfromColl(invSummaryVOColl);
	}
	public void clearDWRInvestigator(String identifier) {
		getAttributeMap().remove(identifier+"Uid");
		getAttributeMap().remove(identifier+"SearchResult");
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
			investigatorLocalId="";
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
		
		investigatorLocalId= returnMap.get("LOCALID") == null ? "" : returnMap.get("LOCALID").toString();
		if (investigatorLocalId.equals(""))
			return errorMsg;
		
		
		dwrInvestigatorDetails = (String) returnMap.get("result");
		String versionCtrlNbr = (String) returnMap.get("versionCtrlNbr");		
		getAttributeMap().put(identifier+"LOCALID", (investigatorLocalId));
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
			investigatorLocalId="";
			dwrInvestigatorDetails =   "";
			logger.error("Exception raised in getDwrInvestigatorDetailsByUid: " + e);
		}
		
		investigatorLocalId= returnMap.get("LOCALID") == null ? "" : returnMap.get("LOCALID").toString();
		if (investigatorLocalId.equals(""))
			return "";
		
		dwrInvestigatorDetails =   (String)returnMap.get("result");
		String versionCtrlNbr = (String) returnMap.get("versionCtrlNbr");
		getAttributeMap().put(identifier+"Uid", (providerUid + "|" + versionCtrlNbr));
		getAttributeMap().put(identifier+"SearchResult",dwrInvestigatorDetails);
		getAttributeMap().put(identifier+"LOCALID", (investigatorLocalId));
	    return dwrInvestigatorDetails;

	  }

	
	public PamClientVO getPamClientVO() {
		return pamClientVO;
	}



	public void setPamClientVO(PamClientVO pamClientVO) {
		this.pamClientVO = pamClientVO;
	}



	public ArrayList<Object> getInvestigators(){
		return investigators;		
	}

	
	public ArrayList<Object> getJurisdictions(){
		return jurisdictions;
		
	}
	public ArrayList<Object> getConditions(){
		return conditions;
		
	}
	
	public ArrayList<Object> getCaseStatuses(){
		return caseStatus;
		
	}
	
	public ArrayList<Object> getStartDateDropDowns(){
		return dateFilterList;
	}

	/**
	 * @param key
	 * @return
	 */
	public String getSearchCriteria(String key) {
		return (String) searchMap.get(key);
	}

	
	public void setSearchCriteria(String key, String answer) {
		searchMap.put(key, answer);
	}
	
	public Object getCodedValue(String key) {
		ArrayList<Object> aList = (ArrayList<Object> ) CachedDropDowns.getCodedValueForType(key);
		return aList;
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

	public void setSearchCriteriaArrayMap(Map searchCriteriaArrayMap) {
		this.searchCriteriaArrayMap = searchCriteriaArrayMap;
	}


	public void clearAll() {
		getAttributeMap().clear();
		searchCriteriaArrayMap = new HashMap<Object,Object>();
	}

	public ArrayList<Object> getNotifications() {
		return notifications;
	}

	public void setNotifications(ArrayList<Object>  notifications) {
		this.notifications = notifications;
	}
	 
}
