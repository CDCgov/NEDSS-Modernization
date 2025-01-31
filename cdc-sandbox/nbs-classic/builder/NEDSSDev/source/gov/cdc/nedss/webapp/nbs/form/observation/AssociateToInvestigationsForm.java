package gov.cdc.nedss.webapp.nbs.form.observation;

import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.InvestigationSummaryVO;
import gov.cdc.nedss.webapp.nbs.action.myinvestigation.ProgramAreaUtil;
import gov.cdc.nedss.webapp.nbs.action.util.QueueUtil;
import gov.cdc.nedss.webapp.nbs.form.util.BaseForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


/**
 * @author TuckerGS
 *
 */
public class AssociateToInvestigationsForm extends BaseForm {
	private static final long serialVersionUID = 1L;
	Map searchCriteriaArrayMap = new HashMap<Object,Object>();
	private Collection<Object>  invSummaryVOColl ;
	Map searchMap = new HashMap<Object,Object>();
	ProgramAreaUtil prgAreaUtil = new ProgramAreaUtil();

	private Long obsUid = null;
	private String obsTypeCd = null;
	private Long mprUid = null;
	private String[] selectedcheckboxIds;
	private ArrayList<Object> dateFilterList = new ArrayList<Object> ();
	private ArrayList<Object> conditions = new ArrayList<Object> ();
	private ArrayList<Object> caseStatus = new ArrayList<Object> ();
	private ArrayList<Object> jurisdictions = new ArrayList<Object> ();
	private ArrayList<Object> investigators = new ArrayList<Object> ();
	private ArrayList<Object> invStatuses = new ArrayList<Object> ();
    private String formHref;
    private String cancelButtonHref;
    private String viewHref;
    private String processingDecision = null;
    private String processingDecisionSRT = "NBS_LAB_REW_PROC_DEC";
    private String processingDecisionLogic = "none"; 

    

	//private ArrayList<Object> notifications = new ArrayList<Object> ();

	private InvestigationSummaryVO investigationSummaryVO = new InvestigationSummaryVO();

	/**
	 * initializeDropDowns returns the latest set of distinct dropdown values for Investigator,
	 * Conditions etc Dropdown Lists in queue
	 */
	public void initializeDropDowns() {
		QueueUtil queueUtil = new QueueUtil();
		investigators = prgAreaUtil.getInvestigatorDropDownsForAssociated(getInvSummaryVOColl());
		jurisdictions = prgAreaUtil.getJurisDropDowns(getInvSummaryVOColl());
		conditions = prgAreaUtil.getConditionDropDowns(getInvSummaryVOColl());
		caseStatus = prgAreaUtil.getCaseStatusDropDowns(getInvSummaryVOColl());
		dateFilterList = queueUtil.getStartDateDropDownValues();
		invStatuses = prgAreaUtil.getInvestigationStatusDropDowns(getInvSummaryVOColl());
	}

	public InvestigationSummaryVO getInvestigationSummaryVO() {
		return investigationSummaryVO;
	}

	public void setInvestigationSummaryVO(InvestigationSummaryVO investigationSummaryVO) {
		this.investigationSummaryVO = investigationSummaryVO;
	}

	public Collection<Object>  getInvSummaryVOColl() {
		return invSummaryVOColl;
	}

	public Long getMprUid() {
		return mprUid;
	}

	public void setMprUid(Long mprUid) {
		this.mprUid = mprUid;
	}
	
	public Long getObsUid() {
		return obsUid;
	}

	public void setObsUid(Long obsUid) {
		this.obsUid = obsUid;
	}
	
	public String getObsTypeCd() {
		return obsTypeCd;
	}

	public void setObsTypeCd(String obsTypeCd) {
		this.obsTypeCd = obsTypeCd;
	}
	
	public String[] getSelectedcheckboxIds() {
		return selectedcheckboxIds;
	}

	public void setSelectedcheckboxIds(String[] selectedcheckboxIds) {
		this.selectedcheckboxIds = selectedcheckboxIds;
	}

	/**
	 * @param invSummaryVOColl
	 */
	public void setInvSummaryVOColl(Collection<Object> invSummaryVOColl) {
		this.invSummaryVOColl = invSummaryVOColl;
	}

	public ArrayList<Object> getInvestigators(){
		return investigators;
	}

	public ArrayList<Object> getInvStatuses(){
		return invStatuses;
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
				searchCriteriaArrayMap.put(newKey,answer);
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
	
	public void clearContext() {
	    this.setFormHref("");
	    this.setCancelButtonHref("");
	    this.setViewHref("");
	}
	
   	public String getFormHref() {
		return formHref;
	}

	public void setFormHref(String formHref) {
		this.formHref = formHref;
	}

	public String getCancelButtonHref() {
		return cancelButtonHref;
	}

	public void setCancelButtonHref(String cancelButtonHref) {
		this.cancelButtonHref = cancelButtonHref;
	}
	public String getViewHref() {
		return viewHref;
	}

	public void setViewHref(String viewHref) {
		this.viewHref = viewHref;
	}
	public String getProcessingDecision() {
		return processingDecision;
	}

	public void setProcessingDecision(String processingDecision) {
		this.processingDecision = processingDecision;
	}
	
	//SRT used for the processing decision dropdown
	public String getProcessingDecisionSRT() {
		return processingDecisionSRT;
	}
	public void setProcessingDecisionSRT(String processingDecisionSRT) {
		this.processingDecisionSRT = processingDecisionSRT;
	}	
	//What list (if any) do we show for the processing decision dropdown
	public String getProcessingDecisionLogic() {
		return processingDecisionLogic;
	}
	public void setProcessingDecisionLogic(String processingDecisionLogic) {
		this.processingDecisionLogic = processingDecisionLogic;
	}	
}
