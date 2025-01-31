package gov.cdc.nedss.webapp.nbs.form.pagemanagement.managerules;


import gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.wa.dt.WaRuleMetadataDT;
import gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.wa.dt.WaUiMetadataSummaryDT;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageConstants;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.RuleManagementActionUtil;
import gov.cdc.nedss.webapp.nbs.form.util.BaseForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;

/**

 */
public class ManageRulesForm extends BaseForm
{
	private static final long serialVersionUID = 1L;
	private Long waTemplateUid;
	private Long ruleId;
	private String ruleDescription;
	private Collection<Object> function;
	public String seletedFunction;
	
	//Date Compare for Validity
	private Collection<Object> sourceDateCompare;
	public String seletedSourceDateComp="";
	private Collection<Object> logicDateCompare;
	public String seletedLogicDateComp="";
	private Collection<Object> targetDateCompare;
	public String[] seletedTargetDateComp={};
	
	//Enable or Disable Target if Source is a particular value(s)
	private Collection<Object> sourceDisOrEnabled;
	public String seletedSourceDisOrEnabled="";
	private boolean anySourceValueIndDisOrEnabled = false;   
	private Collection<Object> sourceValDisOrEnabled;
	public String[] seletedSourceValDisOrEnabled={};
	public String viewSourceValDisOrEnabled="";
	private Collection<Object> logicDisOrEnabled;
	public String seletedLogDisOrEnabled="";
	private Collection<Object> tarDisOrEnabled;
	public String[] seletedTarDisOrEnabled={};
	public String viewTarDisOrEnabled = "";
	
	//Hide or Unhide Target if Source is a particular value(s)
	private Collection<Object> sourceHideOrUnhide;
	public String selectedSourceHideOrUnhide="";
	private boolean anySourceValueIndHideOrUnhide = false;   
	private Collection<Object> sourceValHideOrUnhide;
	public String[] selectedSourceValHideOrUnhide={};
	public String viewSourceValHideOrUnhide="";
	private Collection<Object> logicHideOrUnhide;
	public String selectedLogicHideOrUnhide="";
	private Collection<Object> targetHideOrUnhide;
	public String[] selectedTargetHideOrUnhide={};
	public String viewTargetHideOrUnhide = "";
	
	//Target(s) is required if Source had a particular value(s)
	private Collection<Object> sourceRequired;
	public String selectedSourceRequired = "";
	private Collection<Object> sourceValRequired;
	public String[] selectedSourceValRequired={};
	public String viewSourceValRequired = "";
	private Collection<Object> logicRequired;
	public String selectedLogicRequired = "";
	private Collection<Object> targetRequired;
	public String[] selectedTargetRequired={};
	public String viewTargetRequired="";
	//If any selected source value will fire rule this field is true
	//else if it is false the selected source values specify the selections to key on
	private boolean anySourceValueInd = false;   
	
	private Map<Object,Object> attributeMap = new HashMap<Object,Object>();
	private String errorMessage;
	private String templateType;
	private String viewTargetDateComp;
	
	Object selection = new Object();
	
	private WaRuleMetadataDT waRuleMetadataDT;
	
	private Collection<Object> waUiRuleSummaryDTColl;
	Map<Object,Object> searchCriteriaArrayMap = new HashMap<Object,Object>();
	
	private String selectedTargetDC;
	private String selectedTargetED;
	private String selectedTargetRQ;
	private String selectedTargetHD;
	private String selectedSourceValueED;
	private String selectedSourceValueRQ;
	private String selectedSourceValueHD;
	private String selectedTargetType; 
	private String selectedTargetTypeHD; 
	
	private Collection<Object> sourceFields= new ArrayList<Object>();
	private Collection<Object> sourceValues=  new ArrayList<Object>();
	private Collection<Object> logicValues=  new ArrayList<Object>();
	private Collection<Object> targetFields=  new ArrayList<Object>();
	private Collection<Object> targetType=  new ArrayList<Object>();
	
	public Collection<Object> getDwrSourceValues(String sourceValue) {
		Long codeGroupId=1l;
	    Iterator<Object> i =this.getSourceDisOrEnabled().iterator();
	    while(i.hasNext()) {
	    	WaUiMetadataSummaryDT waSumDT =(WaUiMetadataSummaryDT) i.next();
	    	if(waSumDT.getQuestionIdentifier().equalsIgnoreCase(sourceValue)){
	    		codeGroupId = waSumDT.getCodeSetGroupId();
	    		break;
	    	}
	    }

		CachedDropDownValues cdv = new CachedDropDownValues();		
		Collection<Object> sourceValDE = null;
		if (sourceValue.equals(PageConstants.CONDITION_CD)) 
			sourceValDE = CachedDropDowns.getConditionDropDownForTemplate(this.getWaTemplateUid());
		else 
			sourceValDE = CachedDropDowns.getCodedValueForType(cdv.getTheCodeSetNm(codeGroupId));
		
		this.sourceValDisOrEnabled = sourceValDE;
		
		return sourceValDisOrEnabled;
	}
	
	public Collection<Object> getDwrRequiredSourceValues(String sourceValue) {
		Long codeGroupId=1l;
	    Iterator<Object> i =this.getSourceRequired().iterator();
	    while(i.hasNext()) {
	    	WaUiMetadataSummaryDT waSumDT =(WaUiMetadataSummaryDT) i.next();
	    	if(waSumDT.getQuestionIdentifier().equalsIgnoreCase(sourceValue)){
	    		codeGroupId = waSumDT.getCodeSetGroupId();
	    		break;
	    	}
	    }

		CachedDropDownValues cdv = new CachedDropDownValues();		
		Collection<Object> sourceValRQ = CachedDropDowns.getCodedValueForType(cdv.getTheCodeSetNm(codeGroupId));
		
		//update our form value
		this.sourceValRequired = sourceValRQ;
		
		return sourceValRQ;
	}
	
	public Collection<Object> getDwrHideShowSourceValues(String sourceValue) {
		Long codeGroupId=1l;
	    Iterator<Object> i =this.getSourceRequired().iterator();
	    while(i.hasNext()) {
	    	WaUiMetadataSummaryDT waSumDT =(WaUiMetadataSummaryDT) i.next();
	    	if(waSumDT.getQuestionIdentifier().equalsIgnoreCase(sourceValue)){
	    		codeGroupId = waSumDT.getCodeSetGroupId();
	    		break;
	    	}
	    }

		CachedDropDownValues cdv = new CachedDropDownValues();
		Collection<Object> sourceValHD = null;
		if (sourceValue.equals(PageConstants.CONDITION_CD)) 
			sourceValHD = CachedDropDowns.getConditionDropDownForTemplate(this.getWaTemplateUid());
		else 
			sourceValHD = CachedDropDowns.getCodedValueForType(cdv.getTheCodeSetNm(codeGroupId));
		
		//update our form value
		this.sourceValHideOrUnhide = sourceValHD;
		
		return sourceValHD;
	}
	
	public Collection<Object> getDWRTargetTypeValues(Long templateUid, String targetType,String source, String mode, String ruleId) {
		
		RuleManagementActionUtil util = new RuleManagementActionUtil();
		WebContext ctx = WebContextFactory.get();
		 HttpServletRequest req = ctx.getHttpServletRequest();
		try {
			targetFields= util.getTargetTypeValues(templateUid,targetType,source, mode, ruleId, "EnableDisable", req);			
		} catch (Exception e) {			
			e.printStackTrace();
		}
		
		 this.tarDisOrEnabled =targetFields ;
		return targetFields;
	}
	
	public Collection<Object> getDWRRequiredTargetTypeValues(Long templateUid, String targetType,String source, String mode, String ruleId) {
		
		RuleManagementActionUtil util = new RuleManagementActionUtil();
		WebContext ctx = WebContextFactory.get();
		 HttpServletRequest req = ctx.getHttpServletRequest();
		try {
			targetFields= util.getTargetTypeValues(templateUid,targetType,source, mode, ruleId, "Require If", req);			
		} catch (Exception e) {			
			e.printStackTrace();
		}
		
		 this.targetRequired = targetFields ;
		return targetFields;
	}
	
	public Collection<Object> getDWRHideShowTargetTypeValues(Long templateUid, String targetType,String source, String mode, String ruleId) {
		
		RuleManagementActionUtil util = new RuleManagementActionUtil();
		WebContext ctx = WebContextFactory.get();
		 HttpServletRequest req = ctx.getHttpServletRequest();
		try {
			targetFields= util.getTargetTypeValues(templateUid,targetType,source, mode, ruleId, "HideShow", req);			
		} catch (Exception e) {			
			e.printStackTrace();
		}
		
		 this.targetHideOrUnhide = targetFields ;
		 if ("Subsection".equalsIgnoreCase(targetType))
		     setSelectedTargetTypeHD("SUBSECTION");
		 else
			 setSelectedTargetTypeHD("QUESTION");
		 
		return targetFields;
	}
	
	public boolean getDWSourceIsRepeatBlock(String source) {		
		try {
			Collection<Object> objEDSource = this.sourceDisOrEnabled;	
			Iterator<Object> itEDSource = objEDSource.iterator();
			while(itEDSource.hasNext()){
				WaUiMetadataSummaryDT dt =(WaUiMetadataSummaryDT)itEDSource.next();
				if(source.equalsIgnoreCase(dt.getQuestionIdentifier())){
					Integer questionGroupSeqNbr = dt.getQuestionGroupSeqNbr(); 
					if(questionGroupSeqNbr!= null){						
						return true;
					}
						
				}
			}
		} catch (Exception e) {			
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean getDWRequiredSourceIsRepeatBlock(String source) {		
		try {
			Collection<Object> objEDSource = this.sourceRequired;	
			Iterator<Object> itEDSource = objEDSource.iterator();
			while(itEDSource.hasNext()){
				WaUiMetadataSummaryDT dt =(WaUiMetadataSummaryDT)itEDSource.next();
				if(source.equalsIgnoreCase(dt.getQuestionIdentifier())){
					Integer questionGroupSeqNbr = dt.getQuestionGroupSeqNbr(); 
					if(questionGroupSeqNbr!= null){						
						return true;
					}
						
				}
			}
		} catch (Exception e) {			
			e.printStackTrace();
		}
		
		return false;
	}
	
	public Collection<Object> getTargetOrderByAtoZ(boolean flag,String from) {		
		NedssUtils util = new NedssUtils();
		Collection<Object> returnList = new ArrayList<Object>();
		if("DateCompare".equalsIgnoreCase(from)){
			util.sortObjectByColumn("getQuestionLabelIdentifier",this.targetDateCompare,flag);
			returnList = this.targetDateCompare;
		} else if("RequireIf".equalsIgnoreCase(from)) {
			util.sortObjectByColumn("getQuestionLabelIdentifier",this.targetRequired,flag);
			returnList = this.targetRequired;
		}else if("HideUnhide".equalsIgnoreCase(from)) {
			util.sortObjectByColumn("getQuestionLabelIdentifier",this.targetHideOrUnhide,flag);
			returnList = this.targetHideOrUnhide;
		} else {
			if(this.tarDisOrEnabled!=null && tarDisOrEnabled.size()>0){
				util.sortObjectByColumn("getQuestionLabelIdentifier",this.tarDisOrEnabled,flag);
				returnList = this.tarDisOrEnabled;
			}else{
				this.tarDisOrEnabled = new ArrayList<Object>();
				returnList = this.tarDisOrEnabled;
			}
		}
		return returnList;
	}
	
	public Collection<Object> getTargetOrderByPage(String from) {
		NedssUtils util = new NedssUtils();
		// Due call this function again because if tarDisOrEnabled 
		// collection is order by ZtoA then it will show different order 
		Collection<Object> returnList = new ArrayList<Object>();
		if("DateCompare".equalsIgnoreCase(from)){
			util.sortObjectByColumn("getQuestionLabelIdentifier",this.targetDateCompare,true);
			util.sortObjectByColumn("getOrderNbr",this.targetDateCompare,true);
			returnList = this.targetDateCompare;
		}else if ("RequireIf".equalsIgnoreCase(from)){
			if(this.targetRequired!=null && targetRequired.size()>0){
				util.sortObjectByColumn("getQuestionLabelIdentifier",this.targetRequired,true);  
				util.sortObjectByColumn("getOrderNbr",this.targetRequired,true);
				returnList = this.targetRequired;
			}else{
				this.targetRequired = new ArrayList<Object>();
				returnList = this.targetRequired;
			}
		}else if ("HideUnhide".equalsIgnoreCase(from)){
			if(this.targetHideOrUnhide !=null && targetHideOrUnhide.size()>0){
				util.sortObjectByColumn("getQuestionLabelIdentifier",this.targetHideOrUnhide,true);  
				util.sortObjectByColumn("getOrderNbr",this.targetHideOrUnhide,true);
				returnList = this.targetHideOrUnhide;
			}else{
				this.targetHideOrUnhide = new ArrayList<Object>();
				returnList = this.targetHideOrUnhide;
			}			
		}else{
			if(this.tarDisOrEnabled!=null && tarDisOrEnabled.size()>0){
				util.sortObjectByColumn("getQuestionLabelIdentifier",this.tarDisOrEnabled,true);  
				util.sortObjectByColumn("getOrderNbr",this.tarDisOrEnabled,true);
				returnList = this.tarDisOrEnabled;
			}else{
				this.tarDisOrEnabled = new ArrayList<Object>();
				returnList = this.tarDisOrEnabled;
			}
		}
		return returnList;
	}
	public Long getWaTemplateUid() {
		return waTemplateUid;
	}

	public void setWaTemplateUid(Long waTemplateUid) {
		this.waTemplateUid = waTemplateUid;
	}
	
	public Long getRuleId() {
		return ruleId;
	}

	public void setRuleId(Long ruleId) {
		this.ruleId = ruleId;
	}

	public String getRuleDescription() {
		return ruleDescription;
	}

	public void setRuleDescription(String ruleDescription) {
		this.ruleDescription = ruleDescription;
	}

	public Collection<Object> getFunction() {
		return function;
	}

	public void setFunction(Collection<Object> function) {
		this.function = function;
	}

	public String getSeletedFunction() {
		return seletedFunction;
	}

	public void setSeletedFunction(String seletedFunction) {
		this.seletedFunction = seletedFunction;
	}

	public Collection<Object> getSourceDateCompare() {
		return sourceDateCompare;
	}

	public void setSourceDateCompare(Collection<Object> sourceDateCompare) {
		this.sourceDateCompare = sourceDateCompare;
	}

	public String getSeletedSourceDateComp() {
		return seletedSourceDateComp;
	}

	public void setSeletedSourceDateComp(String seletedSourceDateComp) {
		this.seletedSourceDateComp = seletedSourceDateComp;
	}

	public Collection<Object> getLogicDateCompare() {
		return logicDateCompare;
	}

	public void setLogicDateCompare(Collection<Object> logicDateCompare) {
		this.logicDateCompare = logicDateCompare;
	}

	public String getSeletedLogicDateComp() {
		if(seletedLogicDateComp!=null){
			if("&gt;".equalsIgnoreCase(seletedLogicDateComp.trim()))
				seletedLogicDateComp= ">";
			if("&lt;".equalsIgnoreCase(seletedLogicDateComp.trim()))
				seletedLogicDateComp= "<";
		}
		return seletedLogicDateComp;
	}

	public void setSeletedLogicDateComp(String seletedLogicDateComp) {
		this.seletedLogicDateComp = seletedLogicDateComp;
	}

	public Collection<Object> getTargetDateCompare() {
		return targetDateCompare;
	}

	public void setTargetDateCompare(Collection<Object> targetDateCompare) {
		this.targetDateCompare = targetDateCompare;
	}

	public String[] getSeletedTargetDateComp() {
		return seletedTargetDateComp;
	}

	public void setSeletedTargetDateComp(String[] seletedTargetDateComp) {
		this.seletedTargetDateComp = seletedTargetDateComp;
	}

	public Collection<Object> getSourceDisOrEnabled() {
		return sourceDisOrEnabled;
	}

	public void setSourceDisOrEnabled(Collection<Object> sourceDisOrEnabled) {
		this.sourceDisOrEnabled = sourceDisOrEnabled;
	}

	public Collection<Object> getSourceFields() {
		return sourceFields;
	}

	public void setSourceFields(Collection<Object> sourceFields) {
		this.sourceFields = sourceFields;
	}

	public Collection<Object> getSourceValues() {
		return sourceValues;
	}

	public void setSourceValues(Collection<Object> sourceValues) {
		this.sourceValues = sourceValues;
	}

	public Collection<Object> getLogicValues() {
		return logicValues;
	}

	public void setLogicValues(Collection<Object> logicValues) {
		this.logicValues = logicValues;
	}

	public Collection<Object> getTargetFields() {
		return targetFields;
	}

	public void setTargetFields(Collection<Object> targetFields) {
		this.targetFields = targetFields;
	}

	public String getSeletedSourceDisOrEnabled() {
		return seletedSourceDisOrEnabled;
	}

	public void setSeletedSourceDisOrEnabled(String seletedSourceDisOrEnabled) {
		this.seletedSourceDisOrEnabled = seletedSourceDisOrEnabled;
	}

	public Collection<Object> getSourceValDisOrEnabled() {
		return sourceValDisOrEnabled;
	}

	public void setSourceValDisOrEnabled(Collection<Object> sourceValDisOrEnabled) {
		this.sourceValDisOrEnabled = sourceValDisOrEnabled;
	}

	public String[] getSeletedSourceValDisOrEnabled() {
		return seletedSourceValDisOrEnabled;
	}

	public Map<Object, Object> getAttributeMap() {
		return attributeMap;
	}

	public void setAttributeMap(Map<Object, Object> attributeMap) {
		this.attributeMap = attributeMap;
	}

	public void setSeletedSourceValDisOrEnabled(String[] seletedSourceValDisOrEnabled) {
		this.seletedSourceValDisOrEnabled = seletedSourceValDisOrEnabled;
	}

	public Collection<Object> getLogicDisOrEnabled() {
		return logicDisOrEnabled;
	}

	public void setLogicDisOrEnabled(Collection<Object> logicDisOrEnabled) {
		this.logicDisOrEnabled = logicDisOrEnabled;
	}

	public String getSeletedLogDisOrEnabled() {
		if(seletedLogDisOrEnabled!=null){
			if("&gt;".equalsIgnoreCase(seletedLogDisOrEnabled.trim()))
				seletedLogDisOrEnabled= ">";
			if("&lt;".equalsIgnoreCase(seletedLogDisOrEnabled.trim()))
				seletedLogDisOrEnabled= "<";
		}
		return seletedLogDisOrEnabled;
	}

	public void setSeletedLogDisOrEnabled(String seletedLogDisOrEnabled) {
		this.seletedLogDisOrEnabled = seletedLogDisOrEnabled;
	}

	public Collection<Object> getTarDisOrEnabled() {
		return tarDisOrEnabled;
	}

	public void setTarDisOrEnabled(Collection<Object> tarDisOrEnabled) {
		this.tarDisOrEnabled = tarDisOrEnabled;
	}

	public String[] getSeletedTarDisOrEnabled() {
		return seletedTarDisOrEnabled;
	}

	public void setSeletedTarDisOrEnabled(String[] seletedTarDisOrEnabled) {
		this.seletedTarDisOrEnabled = seletedTarDisOrEnabled;
	}

	public Object getSelection() {
		return selection;
	}

	public void setSelection(Object selection) {
		this.selection = selection;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public void clearForm(){
		
		ruleDescription="";
		seletedFunction="";
		seletedSourceDateComp="";
		seletedLogicDateComp="";
		seletedTargetDateComp=new String[]{};
		seletedSourceDisOrEnabled="";
		seletedSourceValDisOrEnabled=new String[]{};
		seletedTarDisOrEnabled=new String[]{};
		viewSourceValDisOrEnabled="";
		viewTarDisOrEnabled="";
		seletedLogDisOrEnabled="";
		
		selectedSourceHideOrUnhide="";
		selectedSourceValHideOrUnhide=new String[]{};
		selectedTargetHideOrUnhide=new String[]{};
		viewSourceValHideOrUnhide="";
		viewTargetHideOrUnhide="";
		selectedLogicHideOrUnhide="";
		selectedTargetTypeHD ="";
		anySourceValueIndHideOrUnhide = false;
		
		selectedSourceRequired="";
		selectedSourceValRequired=new String[]{};
		selectedTargetRequired=new String[]{};
		viewSourceValRequired="";
		viewTargetRequired="";
		
		selectedLogicRequired="";
		selectedTargetType ="";
		anySourceValueInd = false;
		anySourceValueIndDisOrEnabled = false;
		
	}

	

	public WaRuleMetadataDT getWaRuleMetadataDT() {
		return waRuleMetadataDT;
	}

	public void setWaRuleMetadataDT(WaRuleMetadataDT waRuleMetadataDT) {
		this.waRuleMetadataDT = waRuleMetadataDT;
	}

	public String getViewTarDisOrEnabled() {
		if (getSeletedTarDisOrEnabled() == null || getSeletedTarDisOrEnabled(). length == 0)
			return("");
		boolean flag = false;
		StringBuffer sb = new StringBuffer();
		for (String sourceVal : getSeletedTarDisOrEnabled()) {
			if(flag){
				sb.append(" | ");					
			}
			sb.append(sourceVal);
			flag = true;
		}
		viewTarDisOrEnabled=sb.toString();
		return viewTarDisOrEnabled;
	}

	public String getViewSourceValDisOrEnabled() {
		boolean flag = false;
		StringBuffer sb = new StringBuffer();
		for (String sourceVal : getSeletedSourceValDisOrEnabled()) {
			if(flag){
				sb.append(" | ");					
			}
			sb.append(sourceVal);
			flag = true;
		}
		viewSourceValDisOrEnabled=sb.toString();
		return viewSourceValDisOrEnabled;
	}

	public Collection<Object> getWaUiRuleSummaryDTColl() {
		return waUiRuleSummaryDTColl;
	}

	public void setWaUiRuleSummaryDTColl(Collection<Object> waUiRuleSummaryDTColl) {
		this.waUiRuleSummaryDTColl = waUiRuleSummaryDTColl;
	}

	public Map<Object, Object> getSearchCriteriaArrayMap() {
		return searchCriteriaArrayMap;
	}

	public void setSearchCriteriaArrayMap(Map<Object, Object> searchCriteriaArrayMap) {
		this.searchCriteriaArrayMap = searchCriteriaArrayMap;
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
	
	public void initializeDropDowns() throws Exception {	
		RuleManagementActionUtil util = new RuleManagementActionUtil();
		//function = RuleManagementActionUtil.getFunction();
		Collection<Object> functionFromSRT = CachedDropDowns.getCodedValueForType("NBS_BRE_FUNCTION");
		function = functionFromSRT;
		sourceFields= util.getSourceFields(waUiRuleSummaryDTColl);
		sourceValues=  util.getSourceValues(waUiRuleSummaryDTColl);
		logicValues=  util.getLogicValues(waUiRuleSummaryDTColl);
		targetFields=  util.getTargetFields(waUiRuleSummaryDTColl);
		Collection<Object> logicDC = CachedDropDowns.getCodedValueForType("NBS_BRE_LOGIC_1");
		logicDateCompare = logicDC; //<. <=. >, >=
		Collection<Object> logicED = CachedDropDowns.getCodedValueForType("NBS_BRE_LOGIC_2");
		logicDisOrEnabled = logicED; // = or <>
		setLogicRequired(logicED);
		setLogicHideOrUnhide(logicED);
		targetType = CachedDropDowns.getCodedValueForType("NBS_BRE_TARGET_TYPE",false); //question or subsection
		this.tarDisOrEnabled = new ArrayList();
		this.targetHideOrUnhide = new ArrayList();
     }
	
	public void clearAll() {
		getAttributeMap().clear();
		searchCriteriaArrayMap = new HashMap<Object,Object>();
	}

	public String getSelectedTargetED() {
		return selectedTargetED;
	}

	public void setSelectedTargetED(String selectedTargetED) {
		this.selectedTargetED = selectedTargetED;
	}

	public String getSelectedTargetRQ() {
		return selectedTargetRQ;
	}

	public void setSelectedTargetRQ(String selectedTargetRQ) {
		this.selectedTargetRQ = selectedTargetRQ;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getSelectedSourceValueED() {
		return selectedSourceValueED;
	}

	public void setSelectedSourceValueED(String selectedSourceValueED) {
		this.selectedSourceValueED = selectedSourceValueED;
	}

	public String getSelectedSourceValueRQ() {
		return selectedSourceValueRQ;
	}

	public void setSelectedSourceValueRQ(String selectedSourceValueRQ) {
		this.selectedSourceValueRQ = selectedSourceValueRQ;
	}	
	
	public String getTemplateType() {
		return templateType;
	}

	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}

	public String getViewTargetDateComp() {
		return viewTargetDateComp;
	}

	public void setViewTargetDateComp(String viewTargetDateComp) {
		this.viewTargetDateComp = viewTargetDateComp;
	}

	public String getSelectedTargetType() {
		return selectedTargetType;
	}

	public void setSelectedTargetType(String selectedTargetType) {
		this.selectedTargetType = selectedTargetType;
	}

	public Collection<Object> getTargetType() {
		return targetType;
	}

	public void setTargetType(Collection<Object> targetType) {
		this.targetType = targetType;
	}

	public String getSelectedTargetDC() {
		return selectedTargetDC;
	}

	public void setSelectedTargetDC(String selectedTargetDC) {
		this.selectedTargetDC = selectedTargetDC;
	}

	public Collection<Object> getSourceRequired() {
		return sourceRequired;
	}

	public void setSourceRequired(Collection<Object> sourceRequired) {
		this.sourceRequired = sourceRequired;
	}


	public String getSelectedSourceRequired() {
		return selectedSourceRequired;
	}

	public void setSelectedSourceRequired(String selectedSourceRequired) {
		this.selectedSourceRequired = selectedSourceRequired;
	}
	
	public Collection<Object> getSourceValRequired() {
		return sourceValRequired;
	}

	public void setSourceValRequired(Collection<Object> sourceValRequired) {
		this.sourceValRequired = sourceValRequired;
	}
	
	public String[] getSelectedSourceValRequired() {
			return selectedSourceValRequired;
	}
	
	public void setSelectedSourceValRequired(String[] selectedSourceValRequired) {
			this.selectedSourceValRequired = selectedSourceValRequired;
	}
	public Collection<Object> getLogicRequired() {
		return logicRequired;
	}

	public void setLogicRequired(Collection<Object> logicRequired) {
		this.logicRequired = logicRequired;
	}

	public String getSelectedLogicRequired() {
		if(selectedLogicRequired!=null && !selectedLogicRequired.isEmpty()){
			if("&gt;".equalsIgnoreCase(selectedLogicRequired.trim()))
				selectedLogicRequired= ">";
			if("&lt;".equalsIgnoreCase(selectedLogicRequired.trim()))
				selectedLogicRequired= "<";
		}
		return selectedLogicRequired;
	}

	public void setSelectedLogicRequired(String selectedLogicRequired) {
		this.selectedLogicRequired = selectedLogicRequired;
	}

	public Collection<Object> getTargetRequired() {
		return targetRequired;
	}

	public void setTargetRequired(Collection<Object> targetRequired) {
		this.targetRequired = targetRequired;
	}

	public String[] getSelectedTargetRequired() {
		return selectedTargetRequired;
	}

	public void setSelectedTargetRequired(String[] selectedTargetRequired) {
		this.selectedTargetRequired = selectedTargetRequired;
	}


	public void setViewTargetRequired(String viewTargetRequired) {
		this.viewTargetRequired = viewTargetRequired;
	}
	
	public String getViewTargetRequired() {
		if (getSelectedTargetRequired() == null || getSelectedTargetRequired().length == 0)
			return "";
		boolean flag = false;
		StringBuffer sb = new StringBuffer();

		for (String sourceVal : getSelectedTargetRequired()) {
			if(flag){
				sb.append(" | ");					
			}
			sb.append(sourceVal);
			flag = true;
		}
		viewTargetRequired=sb.toString();
		return viewTargetRequired;
	}

	public String getViewSourceValRequired() {
		boolean flag = false;
		StringBuffer sb = new StringBuffer();
		for (String sourceVal : getSelectedSourceValRequired()) {
			if(flag){
				sb.append(" | ");					
			}
			sb.append(sourceVal);
			flag = true;
		}
		viewSourceValRequired=sb.toString();
		return viewSourceValRequired;
	}
	
	public boolean isAnySourceValueInd() {
		return anySourceValueInd;
	}

	public void setAnySourceValueInd(boolean anySourceValueInd) {
		this.anySourceValueInd = anySourceValueInd;
	}	
	
	public boolean isAnySourceValueIndDisOrEnabled() {
		return anySourceValueIndDisOrEnabled;
	}

	public void setAnySourceValueIndDisOrEnabled(boolean anySourceValueIndDisOrEnabled) {
		this.anySourceValueIndDisOrEnabled = anySourceValueIndDisOrEnabled;
	}		
	//The method was needed because disabled multiselect fields were not getting reset
	public boolean dwrResetSelectedSourceValues(){
		selectedSourceValRequired=new String[]{};
		seletedSourceValDisOrEnabled=new String[]{};
		selectedSourceValHideOrUnhide=new String[]{};
		return true;
	}
	
	public Collection<Object> getSourceHideOrUnhide() {
		return sourceHideOrUnhide;
	}

	public void setSourceHideOrUnhide(Collection<Object> sourceHideOrUnhide) {
		this.sourceHideOrUnhide = sourceHideOrUnhide;
	}
	public String getSelectedSourceHideOrUnhide() {
		return selectedSourceHideOrUnhide;
	}

	public void setSelectedSourceHideOrUnhide(String selectedSourceHideOrUnhide) {
		this.selectedSourceHideOrUnhide = selectedSourceHideOrUnhide;
	}
	
	public boolean isAnySourceValueIndHideOrUnhide() {
		return anySourceValueIndHideOrUnhide;
	}
	public void setAnySourceValueIndHideOrUnhide(boolean anySourceValueIndHideOrUnhide) {
		this.anySourceValueIndHideOrUnhide = anySourceValueIndHideOrUnhide;
	}
	public Collection<Object> getSourceValHideOrUnhide() {
		return sourceValHideOrUnhide;
	}

	public void setSourceValHideOrUnhide(Collection<Object> sourceValHideOrUnhide) {
		this.sourceValHideOrUnhide = sourceValHideOrUnhide;
	}
	public String[] getSelectedSourceValHideOrUnhide() {
			return selectedSourceValHideOrUnhide;
	}
	public void setSelectedSourceValHideOrUnhide(String[] selectedSourceValHideOrUnhide) {
		this.selectedSourceValHideOrUnhide = selectedSourceValHideOrUnhide;
	}
	public String getViewSourceValHideOrUnhide() {
		boolean flag = false;
		StringBuffer sb = new StringBuffer();
		for (String sourceVal : getSelectedSourceValHideOrUnhide()) {
			if(flag){
				sb.append(" | ");					
			}
			sb.append(sourceVal);
			flag = true;
		}
		viewSourceValHideOrUnhide=sb.toString();
		return viewSourceValHideOrUnhide;
	}
	public Collection<Object> getLogicHideOrUnhide() {
		return logicHideOrUnhide;
	}

	public void setLogicHideOrUnhide(Collection<Object> logicHideOrUnhide) {
		this.logicHideOrUnhide = logicHideOrUnhide;
	}

	public String getSelectedLogicHideOrUnhide() {
		if(selectedLogicHideOrUnhide!=null){
			if("&gt;".equalsIgnoreCase(selectedLogicHideOrUnhide.trim()))
				selectedLogicHideOrUnhide= ">";
			if("&lt;".equalsIgnoreCase(selectedLogicHideOrUnhide.trim()))
				selectedLogicHideOrUnhide= "<";
		}
		return selectedLogicHideOrUnhide;
	}

	public void setSelectedLogicHideOrUnhide(String selectedLogicHideOrUnhide) {
		this.selectedLogicHideOrUnhide = selectedLogicHideOrUnhide;
	}
	
	public Collection<Object> getTargetHideOrUnhide() {
		return targetHideOrUnhide;
	}

	public void setTargetHideOrUnhide(Collection<Object> targetHideOrUnhide) {
		this.targetHideOrUnhide = targetHideOrUnhide;
	}
	public String[] getSelectedTargetHideOrUnhide() {
		return selectedTargetHideOrUnhide;
	}

	public void setSelectedTargetHideOrUnhide(String[] selectedTargetHideOrUnhide) {
		this.selectedTargetHideOrUnhide = selectedTargetHideOrUnhide;
	}

	public String getViewTargetHideOrUnhide() {
		if (getSelectedTargetHideOrUnhide() == null || getSelectedTargetHideOrUnhide(). length == 0)
			return("");
		boolean flag = false;
		StringBuffer sb = new StringBuffer();
		for (String sourceVal : getSelectedTargetHideOrUnhide()) {
			if(flag){
				sb.append(" | ");					
			}
			sb.append(sourceVal);
			flag = true;
		}
		viewTargetHideOrUnhide=sb.toString();
		return viewTargetHideOrUnhide;
	}		
	public String getSelectedSourceValueHD() {
		return selectedSourceValueHD;
	}

	public void setSelectedSourceValueHD(String selectedSourceValueHD) {
		this.selectedSourceValueHD = selectedSourceValueHD;
	}
	public String getSelectedTargetHD() {
		return selectedTargetHD;
	}

	public void setSelectedTargetHD(String selectedTargetHD) {
		this.selectedTargetHD = selectedTargetHD;
	}
	
	public String getSelectedTargetTypeHD() {
		return selectedTargetTypeHD;
	}

	public void setSelectedTargetTypeHD(String selectedTargetTypeHD) {
		this.selectedTargetTypeHD = selectedTargetTypeHD;
	}	
}