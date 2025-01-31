package gov.cdc.nedss.webapp.nbs.action.pagemanagement.managerules;
        


import gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.vo.PageManagementProxyVO;
import gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.wa.dt.RuleElementDT;
import gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.wa.dt.WaRuleMetadataDT;
import gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.wa.dt.WaUiMetadataSummaryDT;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.util.HTMLEncoder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageConstants;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.RuleManagementActionUtil;
import gov.cdc.nedss.webapp.nbs.action.util.CallProxyEJB;
import gov.cdc.nedss.webapp.nbs.action.util.PaginationUtil;
import gov.cdc.nedss.webapp.nbs.form.pagemanagement.managerules.ManageRulesForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;


public class ManageRulesAction extends DispatchAction
{
	static final LogUtils logger = new LogUtils(ManageRulesAction.class.getName());
	
	private PropertyUtil properties = PropertyUtil.getInstance();
	
	private static final String ADD_BUSINESS_RULE ="addBusinessRule";	
	private static final String EDIT_BUSINESS_RULE ="editBusinessRule";	
	private static final String VIEW_BUSINESS_RULE ="viewBusinessRule";	
	private static final String VIEW_RULES_LIST ="viewRulesList";
	private static final String RELOAD_VIEW_RULE ="reloadViewRule";	
	private static final String DATE_COMPARE ="DATECOMPARE";
	private static final String REQUIRE_IF ="REQUIREIF";
	private static final String ENABLE ="ENABLE";
	private static final String DISABLE ="DISABLE";
	private static final String HIDE = "HIDE";
	private static final String UNHIDE = "UNHIDE";



    
    @SuppressWarnings("unchecked")
	public ActionForward addBusinessRule(ActionMapping mapping, ActionForm form,
    		HttpServletRequest request, HttpServletResponse response) throws Exception
    {
    	logger.debug("Begin ManageRulesAction add rule");
    	try{
	    	request.setAttribute("PageTitle", "Manage Pages: Add Rule ");
	    	ManageRulesForm mrForm = (ManageRulesForm)form;
	    	mrForm.clearForm();
	    	
	    	Long waTemplateUid = null;    	
	     	waTemplateUid = (Long)request.getSession().getAttribute("waTemplateUid");
	     	mrForm.setWaTemplateUid(waTemplateUid);
	    	Object[] oParams = new Object[] {waTemplateUid,"DATE"};
			String sBeanJndiName = JNDINames.PAGE_MANAGEMENT_PROXY_EJB;
			String sMethod = "getWaUiElementDTDropDown";
			Object obDC = CallProxyEJB.callProxyEJB(oParams, sBeanJndiName, sMethod, request.getSession());		
			mrForm.setSourceDateCompare((Collection)obDC);
			mrForm.setTargetDateCompare((Collection)obDC);
				    	
		  	// setting function drop down values		
	    	mrForm.initializeDropDowns();
			
	    	// For Enabled and Disabled, Require Id and Hide/Unhide set Page Questions List    	
			Object[] oParamsED = new Object[] {waTemplateUid,"CODED"};
			String sBeanJndiNameED = JNDINames.PAGE_MANAGEMENT_PROXY_EJB;
			String sMethodED = "getWaUiElementDTDropDown";
			Object obED = CallProxyEJB.callProxyEJB(oParamsED, sBeanJndiNameED, sMethodED, request.getSession());
			Collection<Object> collED=(Collection)obED;
			mrForm.setSourceDisOrEnabled(collED);
			mrForm.setSourceHideOrUnhide(collED);
			mrForm.setSourceRequired(collED);
			
			mrForm.setSelectedTargetType("QUESTION");
			mrForm.setSelectedTargetTypeHD("QUESTION");
			// The Values and Target Fields are set with the Source is selected
			//Initialize Enable Disable Source value and Target fields
			mrForm.setSourceValDisOrEnabled(new ArrayList());
			mrForm.setTarDisOrEnabled(new ArrayList());
			//Initialize Required Source values and Target
			mrForm.setSourceValRequired(new ArrayList());
			mrForm.setTargetRequired(new ArrayList());
			//Initialize Hide Unhide Source value and Target fields
			mrForm.setSourceValHideOrUnhide(new ArrayList());
			mrForm.setTargetHideOrUnhide(new ArrayList());			
	    	
    	}catch(Exception e){
    		logger.error("Error in ManagerulesAction.addBusinessRule: "+e);
    		throw new ServletException("Error while calling Add business rule: " +e.getMessage(),e);
    	}
		
		logger.debug("End ManageRulesAction add rule");
        return mapping.findForward(ManageRulesAction.ADD_BUSINESS_RULE);
    }

    
    
    @SuppressWarnings("unchecked")
	public ActionForward createBusinessRule(ActionMapping mapping, ActionForm form,
    		HttpServletRequest request, HttpServletResponse response) throws Exception
    {
    	logger.debug("Begin ManageRulesAction create rule");
    	request.setAttribute("PageTitle", "Add Business Rule");
    	ManageRulesForm mrForm = (ManageRulesForm)form;
    	List<Object> pageList = new ArrayList<Object>(); 
    	StringBuffer sourceValueSB = new StringBuffer(); 
    	try{
    		
    		WaRuleMetadataDT ruleMetaDataDT = new WaRuleMetadataDT();    		
    		ruleMetaDataDT.setRuleCd(mrForm.getSeletedFunction());
    		ruleMetaDataDT.setRuleDescTxt(mrForm.getRuleDescription());
    		Map<Integer,RuleElementDT> ruleElementMap = new HashMap<Integer,RuleElementDT>();
    		int i =1;
    		Collection<String> sourceLocalIdColl = new ArrayList<String>();
    		Collection<String> targetLocalIdColl = new ArrayList<String>();
    		Long waTemplateUid = null;    	
	     	waTemplateUid = (Long)request.getSession().getAttribute("waTemplateUid");
			//////////////// Date Compare /////////////////////////////
    		if(DATE_COMPARE.equalsIgnoreCase(mrForm.getSeletedFunction())){
    			mrForm.setSeletedFunction("Date Compare");
    			sourceLocalIdColl.add(mrForm.getSeletedSourceDateComp());
    			ruleMetaDataDT.setSourceQuestionIdentifierDTColl(sourceLocalIdColl);
    			Object[] oParams = new Object[] {waTemplateUid,"DATE"};
    			String sBeanJndiName = JNDINames.PAGE_MANAGEMENT_PROXY_EJB;
    			String sMethod = "getWaUiElementDTDropDown";
    			Object obDC = CallProxyEJB.callProxyEJB(oParams, sBeanJndiName, sMethod, request.getSession());	
    			Collection<Object> collDC=(Collection)obDC;
    			mrForm.setSourceDateCompare((Collection)obDC);
    			mrForm.setTargetDateCompare((Collection)obDC);
    			
    			Iterator<Object> itTemp = collDC.iterator();
    			Map<String, Object> hashMap = new HashMap<String, Object>();
    			Map<String, String> lableMap = new HashMap<String, String>();
    			
    			while(itTemp.hasNext()){
    				WaUiMetadataSummaryDT SummaryDT = (WaUiMetadataSummaryDT)itTemp.next();
    				hashMap.put(SummaryDT.getQuestionIdentifier(), SummaryDT);
    				lableMap.put(SummaryDT.getQuestionIdentifier(), SummaryDT.getQuestionLabel());
    			}    			
    			
    			
    			String selectedTarget[] =  mrForm.getSeletedTargetDateComp();
    			StringBuffer errorMessage=new StringBuffer();
    			if(selectedTarget != null){    				
    				boolean flag = false;    				
    				for (String target : selectedTarget) {
    					RuleElementDT elementDT = new RuleElementDT();
    					elementDT.setSourceQuestionIdentifier(mrForm.getSeletedSourceDateComp());
    					elementDT.setComparator(mrForm.getSeletedLogicDateComp());
    	    			
    	    			Collection<String> collTarget = new ArrayList();
    	    			collTarget.add(target);    	    			
    	    			elementDT.setTargetQuestionIdentifierColl(collTarget);
    	    			targetLocalIdColl.add(target);
    	    			ruleElementMap.put(new Integer(i++), elementDT);
    	    			
    	    			if(flag){
    						errorMessage.append(", ");
    					}
    	    			WaUiMetadataSummaryDT hasMapSummaryDT = (WaUiMetadataSummaryDT)hashMap.get(target);
    	    			if(hasMapSummaryDT!=null){
    	    				errorMessage.append(((WaUiMetadataSummaryDT)hashMap.get(mrForm.getSeletedSourceDateComp())).getQuestionLabel()).append("  must be ");
    	    				errorMessage.append(mrForm.getSeletedLogicDateComp()).append("  ");
							errorMessage
									.append(((WaUiMetadataSummaryDT) hashMap
											.get(target)).getQuestionLabel() != null ? ((WaUiMetadataSummaryDT) hashMap
											.get(target)).getQuestionLabel()
											: ((WaUiMetadataSummaryDT) hashMap
													.get(target))
													.getComponentName());
    	    				flag = true;
    	    			}
    				}
    			}
    			ruleMetaDataDT.setTargetQuestionIdentifierDTColl(targetLocalIdColl);
    			ruleMetaDataDT.setErrMsgTxt(errorMessage.toString());
    			ruleMetaDataDT.setLogicValues(mrForm.getSeletedLogicDateComp());
    			ruleMetaDataDT.setRuleElementMap(ruleElementMap);
    			ruleMetaDataDT.setLableMap(lableMap);
    			
    		}
    		/////////////////////ENABLE/DISABLE//////////////////////
    		else if( ENABLE.equalsIgnoreCase(mrForm.getSeletedFunction()) ||
    				 DISABLE.equalsIgnoreCase(mrForm.getSeletedFunction())){  
    			
    			Object[] oParamsED = new Object[] {waTemplateUid,""};
    			String sBeanJndiNameED = JNDINames.PAGE_MANAGEMENT_PROXY_EJB;
    			String sMethodED = "getWaUiElementDTDropDown";
    			Object obED = CallProxyEJB.callProxyEJB(oParamsED, sBeanJndiNameED, sMethodED, request.getSession());
    			Collection<Object> collED=(Collection)obED;
    			mrForm.setSourceDisOrEnabled(collED);    			
    			Iterator<Object> itTemp = collED.iterator();
    			Map<String, Object> hashMap = new HashMap<String, Object>();
    			
    			while(itTemp.hasNext()){
    				WaUiMetadataSummaryDT SummaryDT = (WaUiMetadataSummaryDT)itTemp.next();
    				hashMap.put(SummaryDT.getQuestionIdentifier(), SummaryDT);
    			}
    			
    			String selectedTarget[] =  mrForm.getSeletedTarDisOrEnabled();
    			//String checkPreviouTarget = this.getPreviousSeletedTargets(selectedTarget,waTemplateUid,"View",null, request);
    			boolean flagCheck = false;
    			ArrayList<String> obColl = this.getPreviousSeletedTargets(selectedTarget,waTemplateUid,"View",null, mrForm.getSeletedFunction(), request);
    			for( String s: selectedTarget){
    				if(obColl.contains(s)){    					
    					flagCheck = true;
    					break;
    				}
    			}
    			if(flagCheck){
    				Iterator<String> itTempTarget = obColl.iterator();
        			Map<String, Object> hashMapTarget = new HashMap<String, Object>();
        			
        			while(itTempTarget.hasNext()){
        				String str = (String)itTempTarget.next();
        				hashMapTarget.put(str, str);
        			}
    				mrForm.getErrorList().add("The Target Field, "+ HTMLEncoder.encodeHtml(convertArrayToString(selectedTarget, hashMapTarget, hashMap)) +" can only be associated with one enable/disable Source Field. ");
    				request.setAttribute("errors", mrForm.getErrorList());  
    				request.setAttribute("reload", "true");
   			       return (mapping.findForward(ManageRulesAction.ADD_BUSINESS_RULE));
    			}
    			
    			if(mrForm.getSeletedFunction().equalsIgnoreCase(ENABLE)){
    				mrForm.setSeletedFunction("Enable");
    			}
    			else if(mrForm.getSeletedFunction().equalsIgnoreCase(DISABLE)){
    				mrForm.setSeletedFunction("Disable");
    				
    			}

    			StringBuffer errorMessage = new StringBuffer();
    			Map<String, Object> sourceValMap = new HashMap<String, Object>();
    			Iterator sourceValColl = mrForm.getSourceValDisOrEnabled().iterator();			
    			while(sourceValColl.hasNext()){
    				DropDownCodeDT dt = (DropDownCodeDT)sourceValColl.next();
    				sourceValMap.put(dt.getKey() , dt);
    			}
    			
    			sourceLocalIdColl.add(mrForm.getSeletedSourceDisOrEnabled());
    			ruleMetaDataDT.setSourceQuestionIdentifierDTColl(sourceLocalIdColl);
    			
    			
				WaUiMetadataSummaryDT waUiMetadataSummaryDT = (WaUiMetadataSummaryDT)hashMap.get(mrForm.getSeletedSourceDisOrEnabled());
				if(waUiMetadataSummaryDT!=null){						
					errorMessage.append(waUiMetadataSummaryDT.getQuestionLabel());
					errorMessage.append(" ").append(mrForm.getSeletedLogDisOrEnabled());					
        			errorMessage.append(" must be ( ");
        			boolean flag =false;    			
        			String selectedSourceVal[] =  mrForm.getSeletedSourceValDisOrEnabled();    			
        			List<String> soruceValList = new ArrayList<String>();    			
        			if(selectedSourceVal != null && selectedSourceVal.length > 0){    				
        				for (String sourceVal : selectedSourceVal) {
        					if(sourceVal.trim().length()>0){
	        					if(flag){
	        						errorMessage.append(", ");	
	        						sourceValueSB.append(", ");
	        					}
	        					DropDownCodeDT dt = (DropDownCodeDT)sourceValMap.get(sourceVal);
	        					errorMessage.append(dt.getValue());	
	        					soruceValList.add(new String(sourceVal));
	        					sourceValueSB.append(dt.getValue());
	        					flag = true;
        					}
        				}
        			} else if (mrForm.isAnySourceValueIndDisOrEnabled()) { //empty and any val checkbox is set
           				errorMessage.append(NEDSSConstants.ANY_SOURCE_VALUE);
           				sourceValueSB.append(NEDSSConstants.ANY_SOURCE_VALUE);
           				soruceValList.add(new String(NEDSSConstants.ANY_SOURCE_VALUE));
           			}    
        			errorMessage.append(" ) ");
        			flag = false;
        			
        			RuleElementDT elementDT1 = new RuleElementDT();	    			
					elementDT1.setSourceQuestionIdentifier(mrForm.getSeletedSourceDisOrEnabled());
					elementDT1.setSourceValues(soruceValList);
					if("<>".equals(mrForm.getSeletedLogDisOrEnabled())){
						elementDT1.setComparator(" != ");
					}else if (mrForm.getSeletedLogDisOrEnabled().isEmpty() && mrForm.isAnySourceValueIndDisOrEnabled()){
						elementDT1.setComparator(NEDSSConstants.ANY_SOURCE_VALUE_COMPARATER);
					}else{
						elementDT1.setComparator(mrForm.getSeletedLogDisOrEnabled());
					}
					Collection<String> collTarget = new ArrayList();
        			if(selectedTarget != null){        				
        				for (String target : selectedTarget) {
        	    			collTarget.add(target);
        	    			targetLocalIdColl.add(target);
        	    			if(flag){
        						errorMessage.append(", ");
        					}
        	    			WaUiMetadataSummaryDT hasMapSummaryDT = (WaUiMetadataSummaryDT)hashMap.get(target);
        	    			if(hasMapSummaryDT!=null){
								errorMessage
										.append(hasMapSummaryDT
												.getQuestionLabel() != null ? hasMapSummaryDT
												.getQuestionLabel()
												: hasMapSummaryDT
														.getComponentName());
        	    				flag = true;
        	    			}
        				}
        			} // End of If (selectedTarget != null)    				
    				elementDT1.setTargetQuestionIdentifierColl(collTarget);
	    			ruleElementMap.put(new Integer(i++), elementDT1);
				}
				
    			ruleMetaDataDT.setTargetQuestionIdentifierDTColl(targetLocalIdColl);
    			ruleMetaDataDT.setErrMsgTxt(errorMessage.toString());
    			ruleMetaDataDT.setSourceValues(sourceValueSB.toString());
       			if (mrForm.isAnySourceValueIndDisOrEnabled() && (mrForm.getSeletedLogDisOrEnabled() == null || mrForm.getSeletedLogDisOrEnabled().isEmpty()))
       				ruleMetaDataDT.setLogicValues(NEDSSConstants.ANY_SOURCE_VALUE_COMPARATER);
       			else
       				ruleMetaDataDT.setLogicValues(mrForm.getSeletedLogDisOrEnabled());
    			ruleMetaDataDT.setTargetType(mrForm.getSelectedTargetType());
    		} //end is an Enable or Disable rule
    		////////////////////////////REQUIRE/////////////////////////////
       		else if( REQUIRE_IF.equalsIgnoreCase(mrForm.getSeletedFunction())){  
       			mrForm.setSeletedFunction("Require If");
       			Object[] oParamsED = new Object[] {waTemplateUid,""};
       			String sBeanJndiNameED = JNDINames.PAGE_MANAGEMENT_PROXY_EJB;
       			String sMethodED = "getWaUiElementDTDropDown";
       			Object obRQ = CallProxyEJB.callProxyEJB(oParamsED, sBeanJndiNameED, sMethodED, request.getSession());
       			Collection<Object> collRQ=(Collection)obRQ;
       			mrForm.setSourceRequired(collRQ);    			
       			Iterator<Object> itTemp = collRQ.iterator();
       			Map<String, Object> hashMap = new HashMap<String, Object>();
   			
       			while(itTemp.hasNext()){
       				WaUiMetadataSummaryDT SummaryDT = (WaUiMetadataSummaryDT)itTemp.next();
       				hashMap.put(SummaryDT.getQuestionIdentifier(), SummaryDT);
       			}
   			
       			String selectedTarget[] =  mrForm.getSelectedTargetRequired();
       			//String checkPreviouTarget = this.getPreviousSeletedTargets(selectedTarget,waTemplateUid,"View",null, request);
       			boolean flagCheck = false;
       			ArrayList<String> obColl = this.getPreviousSeletedTargets(selectedTarget,waTemplateUid,"View",null, mrForm.getSeletedFunction(), request);
       			for( String s: selectedTarget){
       				if(obColl.contains(s)){    					
       					flagCheck = true;
       					break;
       				}
       			}
       			if(flagCheck){
       				Iterator<String> itTempTarget = obColl.iterator();
       				Map<String, Object> hashMapTarget = new HashMap<String, Object>();
       			
       				while(itTempTarget.hasNext()){
       					String str = (String)itTempTarget.next();
       					hashMapTarget.put(str, str);
       				}
       				mrForm.getErrorList().add("The Target Field, "+ HTMLEncoder.encodeHtml(convertArrayToString(selectedTarget, hashMapTarget, hashMap)) +" can only be associated with one Require IF Source Field. ");
       				request.setAttribute("errors", mrForm.getErrorList());  
       				request.setAttribute("reload", "true");
       				return (mapping.findForward(ManageRulesAction.ADD_BUSINESS_RULE));
       			}

       			StringBuffer errorMessage = new StringBuffer();
       			Map<String, Object> sourceValMap = new HashMap<String, Object>();
       			Iterator sourceValColl = mrForm.getSourceValRequired().iterator();			
       			while(sourceValColl.hasNext()){
       				DropDownCodeDT dt = (DropDownCodeDT)sourceValColl.next();
       				sourceValMap.put(dt.getKey() , dt);
       			}
   			
       			sourceLocalIdColl.add(mrForm.getSelectedSourceRequired());
       			ruleMetaDataDT.setSourceQuestionIdentifierDTColl(sourceLocalIdColl);
   			
   			
				WaUiMetadataSummaryDT waUiMetadataSummaryDT = (WaUiMetadataSummaryDT)hashMap.get(mrForm.getSelectedSourceRequired());
				if(waUiMetadataSummaryDT!=null){						
					errorMessage.append(waUiMetadataSummaryDT.getQuestionLabel());
					errorMessage.append(" ").append(mrForm.getSelectedLogicRequired());					
       			errorMessage.append(" ( ");
       			boolean flag =false;    			
       			String selectedSourceVal[] =  mrForm.getSelectedSourceValRequired();    			
       			List<String> soruceValList = new ArrayList<String>();  
       			if(selectedSourceVal != null && selectedSourceVal.length > 0){    				
       				for (String sourceVal : selectedSourceVal) {
       					if(sourceVal.trim().length()>0){
	        					if(flag){
	        						errorMessage.append(", ");	
	        						sourceValueSB.append(", ");
	        					}
	        					DropDownCodeDT dt = (DropDownCodeDT)sourceValMap.get(sourceVal);
	        					errorMessage.append(dt.getValue());	
	        					soruceValList.add(new String(sourceVal));
	        					sourceValueSB.append(dt.getValue());
	        					flag = true;
       					}
       				}
       			} else if (mrForm.isAnySourceValueInd()) { //empty and any val checkbox is set
       				errorMessage.append(NEDSSConstants.ANY_SOURCE_VALUE);
       				sourceValueSB.append(NEDSSConstants.ANY_SOURCE_VALUE);
       				soruceValList.add(new String(NEDSSConstants.ANY_SOURCE_VALUE));
       				
       			}
       			errorMessage.append(" ) ");
       			flag = false;
       			
       			RuleElementDT elementDT1 = new RuleElementDT();	    			
					elementDT1.setSourceQuestionIdentifier(mrForm.getSelectedSourceRequired());
					elementDT1.setSourceValues(soruceValList);
					if("<>".equals(mrForm.getSelectedLogicRequired())){
						elementDT1.setComparator(" != ");
					}else if (mrForm.getSelectedLogicRequired().isEmpty() && mrForm.isAnySourceValueInd()){
						elementDT1.setComparator(NEDSSConstants.ANY_SOURCE_VALUE_COMPARATER);
					} else {
						elementDT1.setComparator(mrForm.getSelectedLogicRequired());
					}
					Collection<String> collTarget = new ArrayList();
       			if(selectedTarget != null){        				
       				for (String target : selectedTarget) {
       	    			collTarget.add(target);
       	    			targetLocalIdColl.add(target);
       	    			if(flag){
       						errorMessage.append(", ");
       					}
       	    			WaUiMetadataSummaryDT hasMapSummaryDT = (WaUiMetadataSummaryDT)hashMap.get(target);
       	    			if(hasMapSummaryDT!=null){
       	    				errorMessage.append(hasMapSummaryDT.getQuestionLabel());
       	    				flag = true;
       	    			}
       				}
       				errorMessage.append(" is required");
       			} // End of If (selectedTarget != null)    				
   				elementDT1.setTargetQuestionIdentifierColl(collTarget);
	    			ruleElementMap.put(new Integer(i++), elementDT1);
				}
				
   			ruleMetaDataDT.setTargetQuestionIdentifierDTColl(targetLocalIdColl);
   			ruleMetaDataDT.setErrMsgTxt(errorMessage.toString());
   			ruleMetaDataDT.setSourceValues(sourceValueSB.toString());
   			if (mrForm.isAnySourceValueInd() && (mrForm.getSelectedLogicRequired() == null || mrForm.getSelectedLogicRequired().isEmpty()))
   				ruleMetaDataDT.setLogicValues(NEDSSConstants.ANY_SOURCE_VALUE_COMPARATER);
   			else
   				ruleMetaDataDT.setLogicValues(mrForm.getSelectedLogicRequired());
   			ruleMetaDataDT.setTargetType(NEDSSConstants.PAGE_ELEMENT_TYPE_QUESTION);
   		} //End is a Required rule
    		/////////////////////HIDE/UNHIDE//////////////////////
    		else if( HIDE.equalsIgnoreCase(mrForm.getSeletedFunction()) ||
    				 UNHIDE.equalsIgnoreCase(mrForm.getSeletedFunction())){  
    			
    			Object[] oParamsHD = new Object[] {waTemplateUid,""};
    			String sBeanJndiNameHD = JNDINames.PAGE_MANAGEMENT_PROXY_EJB;
    			String sMethodHD = "getWaUiElementDTDropDown";
    			Object obHD = CallProxyEJB.callProxyEJB(oParamsHD, sBeanJndiNameHD, sMethodHD, request.getSession());
    			Collection<Object> collHD=(Collection)obHD;
    			mrForm.setSourceHideOrUnhide(collHD);    			
    			Iterator<Object> itTemp = collHD.iterator();
    			Map<String, Object> hashMap = new HashMap<String, Object>();
    			
    			while(itTemp.hasNext()){
    				WaUiMetadataSummaryDT SummaryDT = (WaUiMetadataSummaryDT)itTemp.next();
    				hashMap.put(SummaryDT.getQuestionIdentifier(), SummaryDT);
    			}
    			
    			String selectedTarget[] =  mrForm.getSelectedTargetHideOrUnhide();
    			boolean flagCheck = false;
    			ArrayList<String> obColl = this.getPreviousSeletedTargets(selectedTarget,waTemplateUid,"View",null, mrForm.getSeletedFunction(), request);
    			for( String s: selectedTarget){
    				if(obColl.contains(s)){    					
    					flagCheck = true;
    					break;
    				}
    			}
    			if(flagCheck){
    				Iterator<String> itTempTarget = obColl.iterator();
        			Map<String, Object> hashMapTarget = new HashMap<String, Object>();
        			
        			while(itTempTarget.hasNext()){
        				String str = (String)itTempTarget.next();
        				hashMapTarget.put(str, str);
        			}
    				mrForm.getErrorList().add("The Target Field, "+ HTMLEncoder.encodeHtml(convertArrayToString(selectedTarget, hashMapTarget, hashMap)) +" can only be associated with one hide/unhide Source Field. ");
    				request.setAttribute("errors", mrForm.getErrorList());  
    				request.setAttribute("reload", "true");
   			       return (mapping.findForward(ManageRulesAction.ADD_BUSINESS_RULE));
    			}
    			//gt Karthic displays the key instead of the description in the list and the view
    			// so we have to continue the tradition..
    			if(mrForm.getSeletedFunction().equalsIgnoreCase("HIDE")){
    				mrForm.setSeletedFunction("Hide");
    			}
    			else if(mrForm.getSeletedFunction().equalsIgnoreCase("UNHIDE")){
    				mrForm.setSeletedFunction("Unhide");
    			}

    			StringBuffer errorMessage = new StringBuffer();
    			Map<String, Object> sourceValMap = new HashMap<String, Object>();
    			Iterator sourceValColl = mrForm.getSourceValHideOrUnhide().iterator();			
    			while(sourceValColl.hasNext()){
    				DropDownCodeDT dt = (DropDownCodeDT)sourceValColl.next();
    				sourceValMap.put(dt.getKey() , dt);
    			}
    		
    			sourceLocalIdColl.add(mrForm.getSelectedSourceHideOrUnhide());
    			ruleMetaDataDT.setSourceQuestionIdentifierDTColl(sourceLocalIdColl);
    			
    			
				WaUiMetadataSummaryDT waUiMetadataSummaryDT = (WaUiMetadataSummaryDT)hashMap.get(mrForm.getSelectedSourceHideOrUnhide());
				if(waUiMetadataSummaryDT!=null){
					//error message isn't actually used anywhere
					errorMessage.append(waUiMetadataSummaryDT.getQuestionLabel());
					errorMessage.append(" ").append(mrForm.getSelectedLogicHideOrUnhide());					
        			errorMessage.append(" must be ( ");
        			boolean flag =false;    			
        			String selectedSourceVal[] =  mrForm.getSelectedSourceValHideOrUnhide();    			
        			List<String> soruceValList = new ArrayList<String>();    			
        			if(selectedSourceVal != null && selectedSourceVal.length > 0){    				
        				for (String sourceVal : selectedSourceVal) {
        					if(sourceVal.trim().length()>0){
	        					if(flag){
	        						errorMessage.append(", ");	
	        						sourceValueSB.append(", ");
	        					}
	        					DropDownCodeDT dt = (DropDownCodeDT)sourceValMap.get(sourceVal);
	        					errorMessage.append(dt.getValue());	
	        					soruceValList.add(new String(sourceVal));
	        					sourceValueSB.append(dt.getValue());
	        					flag = true;
        					}
        				}
        			} else if (mrForm.isAnySourceValueIndHideOrUnhide()) { //empty and any val checkbox is set
           				errorMessage.append(NEDSSConstants.ANY_SOURCE_VALUE);
           				sourceValueSB.append(NEDSSConstants.ANY_SOURCE_VALUE);
           				soruceValList.add(new String(NEDSSConstants.ANY_SOURCE_VALUE));
           			}    
        			errorMessage.append(" ) ");
        			flag = false;
        			
        			RuleElementDT elementDT1 = new RuleElementDT();	    			
					elementDT1.setSourceQuestionIdentifier(mrForm.getSelectedSourceHideOrUnhide());
					elementDT1.setSourceValues(soruceValList);
					if("<>".equals(mrForm.getSelectedLogicHideOrUnhide())){
						elementDT1.setComparator(" != ");
					}else if (mrForm.getSelectedLogicHideOrUnhide().isEmpty() && mrForm.isAnySourceValueIndHideOrUnhide()){
						elementDT1.setComparator(NEDSSConstants.ANY_SOURCE_VALUE_COMPARATER);
					}else{
						elementDT1.setComparator(mrForm.getSelectedLogicHideOrUnhide());
					}
					Collection<String> collTarget = new ArrayList(); 
        			if(selectedTarget != null){        				
        				for (String target : selectedTarget) {
        	    			collTarget.add(target);
        	    			targetLocalIdColl.add(target);
        	    			if(flag){
        						errorMessage.append(", ");
        					}
        	    			WaUiMetadataSummaryDT hasMapSummaryDT = (WaUiMetadataSummaryDT)hashMap.get(target);
        	    			if(hasMapSummaryDT!=null){
        	    				errorMessage
								.append(hasMapSummaryDT
										.getQuestionLabel() != null ? hasMapSummaryDT
										.getQuestionLabel()
										: hasMapSummaryDT
												.getComponentName());
        	    				flag = true;
        	    			}
        				}
        			} // End of If (selectedTarget != null)    				
    				elementDT1.setTargetQuestionIdentifierColl(collTarget);
	    			ruleElementMap.put(new Integer(i++), elementDT1);
				}
				
    			ruleMetaDataDT.setTargetQuestionIdentifierDTColl(targetLocalIdColl);
    			ruleMetaDataDT.setErrMsgTxt(errorMessage.toString());
    			ruleMetaDataDT.setSourceValues(sourceValueSB.toString());
       			if (mrForm.isAnySourceValueIndHideOrUnhide() && (mrForm.getSelectedLogicHideOrUnhide() == null || mrForm.getSelectedLogicHideOrUnhide().isEmpty()))
       				ruleMetaDataDT.setLogicValues(NEDSSConstants.ANY_SOURCE_VALUE_COMPARATER);
       			else
       				ruleMetaDataDT.setLogicValues(mrForm.getSelectedLogicHideOrUnhide());
    			ruleMetaDataDT.setTargetType(mrForm.getSelectedTargetTypeHD());
    		} //end of Hide or Unhide rule    		
    		
    		NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) request.getSession().getAttribute("NBSSecurityObject");
    		String userId = nbsSecurityObj.getTheUserProfile().getTheUser().getEntryID();
    		ruleMetaDataDT.setRecordStatusCd(NEDSSConstants.ACTIVE);
    		ruleMetaDataDT.setAddUserId(Long.valueOf(userId));
    		ruleMetaDataDT.setAddTime(new Timestamp(new Date().getTime()));
    		ruleMetaDataDT.setLastChgUserId(Long.valueOf(userId));
       		ruleMetaDataDT.setLastChgTime(new Timestamp(new Date().getTime()));
       		ruleMetaDataDT.setRecordStatusTime(new Timestamp(new Date().getTime()));
       	    ruleMetaDataDT.setRuleElementMap(ruleElementMap);
    		ruleMetaDataDT.setRuleCd(mrForm.getSeletedFunction());
         	ruleMetaDataDT.setWaTemplateUid(waTemplateUid);
         	String ruleExpression = RuleManagementActionUtil.getRulesExpression(mrForm);
         	ruleMetaDataDT.setRuleExpression(ruleExpression);
         	
         	
    		Object[] oParams = new Object[] { ruleMetaDataDT };
    		String sBeanJndiName = JNDINames.PAGE_MANAGEMENT_PROXY_EJB;
			String sMethod = "insertRuleMetadataDT";
    		Object ob = CallProxyEJB.callProxyEJB(oParams, sBeanJndiName, sMethod, request.getSession());
    		WaRuleMetadataDT waRuleMetadataDT =(WaRuleMetadataDT)ob;
    		//request.setAttribute("waRuleMetadataDT", waRuleMetadataDT);
    		request.getSession().setAttribute("waRuleMetadataDT", waRuleMetadataDT);
    		
    		
    		
    	}catch(Exception e){
    		logger.error("Error in ManageRulesAction.createBusinessRule: "+ e.getMessage());
    		e.printStackTrace();
    		throw new ServletException("Error while Creating business rule: "+e.getMessage(),e);
    	}
    	request.setAttribute("manageFormList", pageList);
    	
		logger.debug("End ManageRulesAction create rule");
		request.setAttribute("messageInd", "ADD");
		return mapping.findForward(RELOAD_VIEW_RULE);
    }
    

	
    
	@SuppressWarnings("unchecked")
	public ActionForward editBusinessRule(ActionMapping mapping, ActionForm form,
    		HttpServletRequest request, HttpServletResponse response) throws Exception
    {
		try {
    	logger.debug("Begin ManageRulesAction edit rule");
    	request.setAttribute("PageTitle", "Manage Pages: Edit Rule");
    	String from = request.getParameter("from")!=null?(String)request.getParameter("from"):null;
    	request.setAttribute("from", from);
    	ManageRulesForm mrForm = (ManageRulesForm)form;
    	mrForm.clearForm();
    	Long waTemplateUid = null;    	
     	waTemplateUid = (Long)request.getSession().getAttribute("waTemplateUid");
     	mrForm.setWaTemplateUid(waTemplateUid);	
    	String waRuleMetadataUidStr = (String)request.getParameter("waRuleMetadataUid");
    	Long waRuleMetadataUid = Long.valueOf(waRuleMetadataUidStr);
    	WaRuleMetadataDT waRuleMetadataDT = new WaRuleMetadataDT();
    	request.setAttribute("waRuleMetadataUid", waRuleMetadataUid);
    	try{
    		Object[] oParams = new Object[] { waRuleMetadataUid };
    		String sBeanJndiName = JNDINames.PAGE_MANAGEMENT_PROXY_EJB;
			String sMethod = "selectWaRuleMetadataDT";
    		Object ob = CallProxyEJB.callProxyEJB(oParams, sBeanJndiName, sMethod, request.getSession());
    		waRuleMetadataDT =(WaRuleMetadataDT)ob;
    	}catch(Exception e){
    		throw new ServletException("Error while Editing business rule: "+e.getMessage(),e);
    	}    	
    	
       	mrForm.setRuleId(waRuleMetadataDT.getWaRuleMetadataUid());
    	mrForm.setRuleDescription(waRuleMetadataDT.getRuleDescTxt());
    	mrForm.setSeletedFunction(waRuleMetadataDT.getRuleCd());
    	Map<Integer,RuleElementDT> mapRuleElementDT = waRuleMetadataDT.getRuleElementMap();
    	
    	
    	String selectedSource="";    	
    	StringBuffer selectedTarget=new StringBuffer();
    	String selectedLogicDateComp="";
    	
    	String sBeanJndiName = JNDINames.PAGE_MANAGEMENT_PROXY_EJB;
		String sMethod = "getWaUiElementDTDropDown";
		
    	Object[] oParamsED = new Object[] {waRuleMetadataDT.getWaTemplateUid(),"CODED"};		
		Object obED = CallProxyEJB.callProxyEJB(oParamsED, sBeanJndiName, sMethod, request.getSession());		
		mrForm.setSourceDisOrEnabled((Collection)obED);
		mrForm.setSourceRequired((Collection)obED);
		mrForm.setSourceHideOrUnhide((Collection)obED);

		Object[] oParamsDC = new Object[] {waRuleMetadataDT.getWaTemplateUid(),"DATE"};		
		Object obDC = CallProxyEJB.callProxyEJB(oParamsDC, sBeanJndiName, sMethod, request.getSession());
		mrForm.setSourceDateCompare((Collection)obDC);
    	mrForm.setTargetDateCompare((Collection)obDC);
    	
		mrForm.setSourceValDisOrEnabled(new ArrayList());
		mrForm.setSourceValHideOrUnhide(new ArrayList());
		mrForm.setSourceValRequired(new ArrayList());
		mrForm.setTargetRequired(new ArrayList());
		
		// this is set Function drop down values
		mrForm.initializeDropDowns();
		//////////////////////////Date Compare/////////////////////////////////
		if("Date Compare".equalsIgnoreCase(waRuleMetadataDT.getRuleCd())){
			Map<Object, Object> targetHashMap = new HashMap<Object, Object>();
			Iterator<Object> itTargetMap = ((Collection)obDC).iterator();			
			while(itTargetMap.hasNext()){
				WaUiMetadataSummaryDT SummaryDT = (WaUiMetadataSummaryDT)itTargetMap.next();
				targetHashMap.put(SummaryDT.getQuestionIdentifier(), SummaryDT);
			}
			
			mrForm.setSeletedFunction("Date Compare");
			Iterator<Object> it = ((Collection)obDC).iterator();
			// Setting target values
	    	 StringBuffer targetVal = new StringBuffer();
	    	for(int i =0; mapRuleElementDT.size()>i;i++){
	    		RuleElementDT ruleElementDT =(RuleElementDT) mapRuleElementDT.get(new Integer(i));
		    	selectedLogicDateComp =ruleElementDT.getComparator();
		    	Iterator<String> itTarget = ruleElementDT.getTargetQuestionIdentifierColl().iterator();		    	
		    	selectedSource = ruleElementDT.getSourceQuestionIdentifier();
		    	
		    	ruleElementDT.getTargetQuestionIdentifierColl();		    	
		    	while(itTarget.hasNext()){
		    		selectedTarget.append((String)itTarget.next());
		    		//selectedTarget.append(",");
		    	}		    	
				 mrForm.setSeletedSourceDateComp(selectedSource);
		    	 mrForm.setSeletedLogicDateComp(this.getSeletedLogicCompartor(selectedLogicDateComp));
		    	 
		    	 if(ruleElementDT.getTargetQuestionIdentifierColl()!=null){
			    		Iterator<String> itTargetDC = ruleElementDT.getTargetQuestionIdentifierColl().iterator();
			    		int size = ruleElementDT.getTargetQuestionIdentifierColl().size();
			    		int increment =0;
			    		while(itTargetDC.hasNext()){
			    			String str =((String)itTargetDC.next()).trim();		    			
			    			targetVal.append(str);   
		    				if(increment < (size-1)){
		    					targetVal.append(",");
				    			increment++;
				    		}
			    			
				    	}	 
		    		}
		    	 mrForm.setSeletedTargetDateComp(convertStringToArray(targetVal.toString()));
	    	}
	    	mrForm.setSelectedTargetDC(RuleManagementActionUtil.setFields(targetVal.toString(), targetHashMap, " | ") );
	    	mrForm.setErrorMessage(waRuleMetadataDT.getErrMsgTxt());
	    ////////////////////////////Enable Disable/////////////////////////////	
		}else if(ENABLE.equalsIgnoreCase(waRuleMetadataDT.getRuleCd()) ||
				DISABLE.equalsIgnoreCase(waRuleMetadataDT.getRuleCd())){

			if(mrForm.getSeletedFunction().equalsIgnoreCase(ENABLE)){
				mrForm.setSeletedFunction("Enable");
			} else if(mrForm.getSeletedFunction().equalsIgnoreCase(DISABLE)){
				mrForm.setSeletedFunction("Disable");
			}


			Object[] oParamsTarget = new Object[] {waRuleMetadataDT.getWaTemplateUid(),""};
			Object obTarget = CallProxyEJB.callProxyEJB(oParamsTarget, sBeanJndiName, sMethod, request.getSession());
			mrForm.setTarDisOrEnabled((Collection)obTarget);

			RuleManagementActionUtil util = new RuleManagementActionUtil();
			Map<Object, Object> targetHashMap = new HashMap<Object, Object>();
			Map<Object, Object> hashMap = new HashMap<Object, Object>();
			Iterator<Object> itMap = ((Collection)obED).iterator();
			while(itMap.hasNext()){
				WaUiMetadataSummaryDT SummaryDT = (WaUiMetadataSummaryDT)itMap.next();
				hashMap.put(SummaryDT.getQuestionIdentifier(), SummaryDT);
			}

			ArrayList<Object> list = util.getTargetTypeValues(waRuleMetadataDT.getWaTemplateUid(),waRuleMetadataDT.getTargetType(),
						waRuleMetadataDT.getSourceQuestionIdentifierString(),"Edit", waRuleMetadataUidStr, mrForm.getSeletedFunction(), request);
			mrForm.setTarDisOrEnabled(list);

			Iterator<Object> itTargetMap = list.iterator();
			while(itTargetMap.hasNext()){
				WaUiMetadataSummaryDT SummaryDT = (WaUiMetadataSummaryDT)itTargetMap.next();
				targetHashMap.put(SummaryDT.getQuestionIdentifier(), SummaryDT);
			}

			mrForm.setSourceValDisOrEnabled(new ArrayList<Object>());
			StringBuffer source = new StringBuffer();
			String comparator = new String();
			StringBuffer sourceVal = new StringBuffer();
			StringBuffer targetVal = new StringBuffer();

	    	for(int i =0; mapRuleElementDT.size()>i;i++){
	    		RuleElementDT ruleElementDT =(RuleElementDT) mapRuleElementDT.get(new Integer(i));

	    		// Setting source value
	    		if(hashMap.get(ruleElementDT.getSourceQuestionIdentifier())!=null){
	    			WaUiMetadataSummaryDT waUiMetadataSummaryDT = (WaUiMetadataSummaryDT)hashMap.get(ruleElementDT.getSourceQuestionIdentifier());

	    			source.append(ruleElementDT.getSourceQuestionIdentifier());

	    			Long codeSetGroupId = waUiMetadataSummaryDT.getCodeSetGroupId();

	    			CachedDropDownValues cdv = new CachedDropDownValues();
	    			Collection<Object> sourceValDE = null;
	    			if (ruleElementDT.getSourceQuestionIdentifier().equals(PageConstants.CONDITION_CD)) 
	    				sourceValDE = CachedDropDowns.getConditionDropDownForTemplate(mrForm.getWaTemplateUid());
	    			else
	    				sourceValDE = CachedDropDowns.getCodedValueForType(cdv.getTheCodeSetNm(codeSetGroupId));
	    			mrForm.setSourceValDisOrEnabled(sourceValDE);

	    			Collection<String> collSourceVal = ruleElementDT.getSourceValues();
		    		Iterator<String> itSourceVal = collSourceVal.iterator();
		    		int size = collSourceVal.size();
		    		int increment =0;
		    		while(itSourceVal.hasNext()){
		    			sourceVal.append(itSourceVal.next().trim());
		    			if(increment < (size-1)){
		    				sourceVal.append(",");
		    				increment++;
		    			}
		    		}
	    		}
	    		// Setting target values
	    		if(ruleElementDT.getTargetQuestionIdentifierColl()!=null){
		    		Iterator<String> itTarget = ruleElementDT.getTargetQuestionIdentifierColl().iterator();
		    		int size = ruleElementDT.getTargetQuestionIdentifierColl().size();
		    		int increment =0;
		    		while(itTarget.hasNext()){
		    			String str =((String)itTarget.next()).trim();
		    			targetVal.append(str);
	    				if(increment < (size-1)){
	    					targetVal.append(",");
			    			increment++;
			    		}

			    	}
	    		}
	    		comparator = ruleElementDT.getComparator();

	    	}
	    	mrForm.setSeletedSourceDisOrEnabled(source.toString());
	    	mrForm.setSeletedLogDisOrEnabled(waRuleMetadataDT.getLogicValues());
	    	mrForm.setSeletedSourceValDisOrEnabled(convertStringToArray(sourceVal.toString()));
	    	
	    	//See if Any Source Value checkbox is set - indicated by 'Any Source Value' in source value field
	    	if (waRuleMetadataDT.getSourceValues() != null && waRuleMetadataDT.getSourceValues().equalsIgnoreCase(NEDSSConstants.ANY_SOURCE_VALUE)) {
	    		mrForm.setAnySourceValueIndDisOrEnabled(true);
	    		mrForm.setSelectedSourceValueED("");
	    	} else 	mrForm.setSelectedSourceValueED(waRuleMetadataDT.getSourceValues().replaceAll(",", " | "));
	    	
	    	
    		mrForm.setSeletedTarDisOrEnabled(convertStringToArray(targetVal.toString()));
    		mrForm.setSelectedTargetED(RuleManagementActionUtil.setFields(targetVal.toString(), targetHashMap, " | ") );
    		mrForm.setSelectedTargetType(waRuleMetadataDT.getTargetType());
    		if(ENABLE.contains(waRuleMetadataDT.getRuleCd())){
				mrForm.setSeletedFunction(ENABLE);
			}else if(DISABLE.contains(waRuleMetadataDT.getRuleCd())){
				mrForm.setSeletedFunction(DISABLE);
			}
    		mrForm.setErrorMessage(waRuleMetadataDT.getErrMsgTxt());
    	////////////////////////////////////Require If///////////////////////////	
		} else if ("Require If".equalsIgnoreCase(waRuleMetadataDT.getRuleCd())){
			
			mrForm.setSeletedFunction("Require If");
			Object[] oParamsTarget = new Object[] {waRuleMetadataDT.getWaTemplateUid(),""};		
			Object obTarget = CallProxyEJB.callProxyEJB(oParamsTarget, sBeanJndiName, sMethod, request.getSession());
			mrForm.setTargetRequired((Collection)obTarget);
			
			RuleManagementActionUtil util = new RuleManagementActionUtil();
			Map<Object, Object> targetHashMap = new HashMap<Object, Object>();
			Map<Object, Object> hashMap = new HashMap<Object, Object>();
			Iterator<Object> itMap = ((Collection)obED).iterator();					
			while(itMap.hasNext()){
				WaUiMetadataSummaryDT SummaryDT = (WaUiMetadataSummaryDT)itMap.next();
				hashMap.put(SummaryDT.getQuestionIdentifier(), SummaryDT);
			}
			
			ArrayList<Object> list = util.getTargetTypeValues(waRuleMetadataDT.getWaTemplateUid(),waRuleMetadataDT.getTargetType(),
						waRuleMetadataDT.getSourceQuestionIdentifierString(),"Edit", waRuleMetadataUidStr , mrForm.getSeletedFunction(), request);
			mrForm.setTargetRequired(list);
			
			Iterator<Object> itTargetMap = list.iterator();			
			while(itTargetMap.hasNext()){
				WaUiMetadataSummaryDT SummaryDT = (WaUiMetadataSummaryDT)itTargetMap.next();
				targetHashMap.put(SummaryDT.getQuestionIdentifier(), SummaryDT);
			}
			
			mrForm.setSourceValRequired(new ArrayList<Object>()); 			
			StringBuffer source = new StringBuffer();
			String comparator = new String();
			StringBuffer sourceVal = new StringBuffer();
			StringBuffer targetVal = new StringBuffer();
			
	    	for(int i =0; mapRuleElementDT.size()>i;i++){
	    		RuleElementDT ruleElementDT =(RuleElementDT) mapRuleElementDT.get(new Integer(i));	 
	    		
	    		// Setting source value
	    		if(hashMap.get(ruleElementDT.getSourceQuestionIdentifier())!=null){	    			
	    			WaUiMetadataSummaryDT waUiMetadataSummaryDT = (WaUiMetadataSummaryDT)hashMap.get(ruleElementDT.getSourceQuestionIdentifier());
	    			
	    			source.append(ruleElementDT.getSourceQuestionIdentifier());
	    			
	    			Long codeSetGroupId = waUiMetadataSummaryDT.getCodeSetGroupId();
	    			
	    			CachedDropDownValues cdv = new CachedDropDownValues();		
	    			Collection<Object> sourceValRQ = CachedDropDowns.getCodedValueForType(cdv.getTheCodeSetNm(codeSetGroupId));
	    			mrForm.setSourceValRequired(sourceValRQ);
	    			
	    			Collection<String> collSourceVal = ruleElementDT.getSourceValues();
		    		Iterator<String> itSourceVal = collSourceVal.iterator();
		    		int size = collSourceVal.size();
		    		int increment =0;
		    		while(itSourceVal.hasNext()){
		    			sourceVal.append(itSourceVal.next().trim());
		    			if(increment < (size-1)){
		    				sourceVal.append(",");
		    				increment++;
		    			}
		    		}
	    		}
	    		// Setting target values
	    		if(ruleElementDT.getTargetQuestionIdentifierColl()!=null){
		    		Iterator<String> itTarget = ruleElementDT.getTargetQuestionIdentifierColl().iterator();
		    		int size = ruleElementDT.getTargetQuestionIdentifierColl().size();
		    		int increment =0;
		    		while(itTarget.hasNext()){
		    			String str =((String)itTarget.next()).trim();		    			
		    			targetVal.append(str);   
	    				if(increment < (size-1)){
	    					targetVal.append(",");
			    			increment++;
			    		}
		    			
			    	}	 
	    		}
	    		comparator = ruleElementDT.getComparator();
	    	}
	    	mrForm.setSelectedSourceRequired(source.toString());
	    	mrForm.setSelectedLogicRequired(waRuleMetadataDT.getLogicValues()); 
	    	mrForm.setSelectedSourceValRequired(convertStringToArray(sourceVal.toString()));
	    	//See if Any Source Value checkbox is set - indicated by 'Any Source Value' in source value field
	    	if (waRuleMetadataDT.getSourceValues() != null && waRuleMetadataDT.getSourceValues().equalsIgnoreCase(NEDSSConstants.ANY_SOURCE_VALUE)) {
	    		mrForm.setAnySourceValueInd(true);
	    		mrForm.setSelectedSourceValueRQ("");
	    	} else 	mrForm.setSelectedSourceValueRQ(waRuleMetadataDT.getSourceValues().replaceAll(",", " | "));
    		mrForm.setSelectedTargetRequired(convertStringToArray(targetVal.toString()));
    		mrForm.setSelectedTargetRQ(RuleManagementActionUtil.setFields(targetVal.toString(), targetHashMap, " | ") );
    		mrForm.setSelectedTargetType("Question");
			mrForm.setSeletedFunction("Require If");
    		mrForm.setErrorMessage(waRuleMetadataDT.getErrMsgTxt());
    ////////////////////////////Hide Unhide/////////////////////////////	
	} else if(HIDE.equalsIgnoreCase(waRuleMetadataDT.getRuleCd()) ||
			UNHIDE.equalsIgnoreCase(waRuleMetadataDT.getRuleCd())){

		if(mrForm.getSeletedFunction().equalsIgnoreCase(HIDE)){
			mrForm.setSeletedFunction("Hide");
		} else if(mrForm.getSeletedFunction().equalsIgnoreCase(UNHIDE)){
			mrForm.setSeletedFunction("Unhide");
		}

		Object[] oParamsTarget = new Object[] {waRuleMetadataDT.getWaTemplateUid(),""};
		Object obTarget = CallProxyEJB.callProxyEJB(oParamsTarget, sBeanJndiName, sMethod, request.getSession());
		mrForm.setTargetHideOrUnhide((Collection)obTarget);

		RuleManagementActionUtil util = new RuleManagementActionUtil();
		Map<Object, Object> targetHashMap = new HashMap<Object, Object>();
		Map<Object, Object> hashMap = new HashMap<Object, Object>();
		Iterator<Object> itMap = ((Collection)obED).iterator();
		while(itMap.hasNext()){
			WaUiMetadataSummaryDT SummaryDT = (WaUiMetadataSummaryDT)itMap.next();
			hashMap.put(SummaryDT.getQuestionIdentifier(), SummaryDT);
		}

		ArrayList<Object> list = util.getTargetTypeValues(waRuleMetadataDT.getWaTemplateUid(),waRuleMetadataDT.getTargetType(),
					waRuleMetadataDT.getSourceQuestionIdentifierString(),"Edit", waRuleMetadataUidStr, mrForm.getSeletedFunction(), request);
		mrForm.setTargetHideOrUnhide(list);

		Iterator<Object> itTargetMap = list.iterator();
		while(itTargetMap.hasNext()){
			WaUiMetadataSummaryDT SummaryDT = (WaUiMetadataSummaryDT)itTargetMap.next();
			targetHashMap.put(SummaryDT.getQuestionIdentifier(), SummaryDT);
		}

		mrForm.setSourceValHideOrUnhide(new ArrayList<Object>());
		StringBuffer source = new StringBuffer();
		String comparator = new String();
		StringBuffer sourceVal = new StringBuffer();
		StringBuffer targetVal = new StringBuffer();

    	for(int i =0; mapRuleElementDT.size()>i;i++){
    		RuleElementDT ruleElementDT =(RuleElementDT) mapRuleElementDT.get(new Integer(i));

    		// Setting source value
    		if(hashMap.get(ruleElementDT.getSourceQuestionIdentifier())!=null){
    			WaUiMetadataSummaryDT waUiMetadataSummaryDT = (WaUiMetadataSummaryDT)hashMap.get(ruleElementDT.getSourceQuestionIdentifier());
    			source.append(ruleElementDT.getSourceQuestionIdentifier());
    			Long codeSetGroupId = waUiMetadataSummaryDT.getCodeSetGroupId();

    			CachedDropDownValues cdv = new CachedDropDownValues();
    			Collection<Object> sourceValHU = null;
    			if (ruleElementDT.getSourceQuestionIdentifier().equals(PageConstants.CONDITION_CD)) 
    				sourceValHU = CachedDropDowns.getConditionDropDownForTemplate(mrForm.getWaTemplateUid());
    			else
    				sourceValHU = CachedDropDowns.getCodedValueForType(cdv.getTheCodeSetNm(codeSetGroupId));
    			mrForm.setSourceValHideOrUnhide(sourceValHU);

    			Collection<String> collSourceVal = ruleElementDT.getSourceValues();
	    		Iterator<String> itSourceVal = collSourceVal.iterator();
	    		int size = collSourceVal.size();
	    		int increment = 0;
	    		while(itSourceVal.hasNext()){
	    			sourceVal.append(itSourceVal.next().trim());
	    			if(increment < (size-1)){
	    				sourceVal.append(",");
	    				increment++;
	    			}
	    		}
    		}
    		// Setting target values
    		if(ruleElementDT.getTargetQuestionIdentifierColl()!=null){
	    		Iterator<String> itTarget = ruleElementDT.getTargetQuestionIdentifierColl().iterator();
	    		int size = ruleElementDT.getTargetQuestionIdentifierColl().size();
	    		int increment = 0;
	    		while(itTarget.hasNext()){
	    			String str =((String)itTarget.next()).trim();
	    			targetVal.append(str);
    				if(increment < (size-1)){
    					targetVal.append(",");
		    			increment++;
		    		}
		    	}
    		}
    		comparator = ruleElementDT.getComparator();

    	}
    	mrForm.setSelectedSourceHideOrUnhide(source.toString());
    	mrForm.setSelectedLogicHideOrUnhide(waRuleMetadataDT.getLogicValues());
    	mrForm.setSelectedSourceValHideOrUnhide(convertStringToArray(sourceVal.toString()));
    	
    	//See if Any Source Value checkbox is set - indicated by 'Any Source Value' in source value field
    	if (waRuleMetadataDT.getSourceValues() != null && waRuleMetadataDT.getSourceValues().equalsIgnoreCase(NEDSSConstants.ANY_SOURCE_VALUE)) {
    		mrForm.setAnySourceValueIndHideOrUnhide(true);
    		mrForm.setSelectedSourceValueHD("");
    	} else 	mrForm.setSelectedSourceValueHD(waRuleMetadataDT.getSourceValues().replaceAll(",", " | "));
    	
    	
		mrForm.setSelectedTargetHideOrUnhide(convertStringToArray(targetVal.toString()));
		mrForm.setSelectedTargetTypeHD(waRuleMetadataDT.getTargetType());
		mrForm.setSelectedTargetHD(RuleManagementActionUtil.setFields(targetVal.toString(), targetHashMap, " | ") );
		mrForm.setSelectedTargetType(waRuleMetadataDT.getTargetType());
		if(HIDE.contains(waRuleMetadataDT.getRuleCd())){
			mrForm.setSeletedFunction(HIDE);
		}else if(UNHIDE.contains(waRuleMetadataDT.getRuleCd())){
			mrForm.setSeletedFunction(UNHIDE);
		}
		mrForm.setErrorMessage(waRuleMetadataDT.getErrMsgTxt());
	}
    	}catch(Exception e){
    		logger.error("Error in ManageRulesAction.editBusinessRule: "+ e.getMessage());
    		e.printStackTrace();
    		throw new ServletException("Error occurred while Editing business rule: "+e.getMessage(),e);
    	}
        return mapping.findForward(ManageRulesAction.EDIT_BUSINESS_RULE);
    }

 
    public ActionForward updateBusinessRule(ActionMapping mapping, ActionForm form,
    		HttpServletRequest request, HttpServletResponse response) throws Exception
    {
    	logger.debug("Begin ManageRulesAction edit rule");
    	request.setAttribute("PageTitle", "Manage Pages: Edit Rule");
    	
    	WaRuleMetadataDT ruleMetaDataDT = new WaRuleMetadataDT();

    	try{
        	ManageRulesForm mrForm = (ManageRulesForm)form;   		
    				
    		ruleMetaDataDT.setRuleCd(mrForm.getSeletedFunction());
    		ruleMetaDataDT.setRuleDescTxt(mrForm.getRuleDescription());
    		Map<Integer,RuleElementDT> ruleElementMap = new HashMap<Integer,RuleElementDT>();
    		int i =1;
    		Collection<String> sourceLocalIdColl = new ArrayList<String>();
    		Collection<String> targetLocalIdColl = new ArrayList<String>();
    		StringBuffer sourceValueSB = new StringBuffer(); 
			/////////////////////Date Compare/////////////////////////////
    		if("Date Compare".equalsIgnoreCase(mrForm.getSeletedFunction())){
    			mrForm.setSeletedFunction("Date Compare");
    			sourceLocalIdColl.add(mrForm.getSeletedSourceDateComp());
    			ruleMetaDataDT.setSourceQuestionIdentifierDTColl(sourceLocalIdColl);
    			Long waTemplateUid = null;    	
    	     	waTemplateUid = (Long)request.getSession().getAttribute("waTemplateUid");    			
    			Object[] oParamsED = new Object[] {waTemplateUid,""};
    			String sBeanJndiNameED = JNDINames.PAGE_MANAGEMENT_PROXY_EJB;
    			String sMethodED = "getWaUiElementDTDropDown";
    			Object obED = CallProxyEJB.callProxyEJB(oParamsED, sBeanJndiNameED, sMethodED, request.getSession());
    			Collection<Object> collED=(Collection)obED;
    			//mrForm.setSourceDisOrEnabled(collED); //was this an error? 
    			mrForm.setSourceDateCompare(collED); //added GT 10/2014
    			Iterator<Object> itTemp = collED.iterator();
    			Map<String, Object> hashMap = new HashMap<String, Object>();
    			Map<String, String> lableMap = new HashMap<String, String>();
    			
    			while(itTemp.hasNext()){
    				WaUiMetadataSummaryDT SummaryDT = (WaUiMetadataSummaryDT)itTemp.next();
    				hashMap.put(SummaryDT.getQuestionIdentifier(), SummaryDT);
    				lableMap.put(SummaryDT.getQuestionIdentifier(), SummaryDT.getQuestionLabel());
    			}    			
    			
    			
    			String selectedTarget[] =  mrForm.getSeletedTargetDateComp();
    			StringBuffer errorMessage=new StringBuffer();
    			if(selectedTarget != null){    				
    				boolean flag = false;    				
    				for (String target : selectedTarget) {
    					RuleElementDT elementDT = new RuleElementDT();
    					elementDT.setSourceQuestionIdentifier(mrForm.getSeletedSourceDateComp());
    					elementDT.setComparator(mrForm.getSeletedLogicDateComp());
    	    			
    	    			Collection<String> collTarget = new ArrayList<String>();
    	    			collTarget.add(target);    	    			
    	    			elementDT.setTargetQuestionIdentifierColl(collTarget);
    	    			targetLocalIdColl.add(target);
    	    			ruleElementMap.put(new Integer(i++), elementDT);
    	    			
    	    			if(flag){
    						errorMessage.append(", ");
    					}
    	    			WaUiMetadataSummaryDT hasMapSummaryDT = (WaUiMetadataSummaryDT)hashMap.get(target);
    	    			if(hasMapSummaryDT!=null){
    	    				errorMessage.append(((WaUiMetadataSummaryDT)hashMap.get(mrForm.getSeletedSourceDateComp())).getQuestionLabel()).append("  must be ");
    	    				errorMessage.append(mrForm.getSeletedLogicDateComp()).append("  ");
    	    				errorMessage.append(((WaUiMetadataSummaryDT)hashMap.get(target)).getQuestionLabel());
    	    				flag = true;
    	    			}
    				}
    			}
    			ruleMetaDataDT.setTargetQuestionIdentifierDTColl(targetLocalIdColl);
    			ruleMetaDataDT.setErrMsgTxt(errorMessage.toString());
    			ruleMetaDataDT.setLogicValues(mrForm.getSeletedLogicDateComp());
    			ruleMetaDataDT.setRuleElementMap(ruleElementMap);
    			ruleMetaDataDT.setLableMap(lableMap);
    		}
    		/////////////////////Enable Disable//////////////////////////////
    		else if( ENABLE.equalsIgnoreCase(mrForm.getSeletedFunction()) ||
   				 DISABLE.equalsIgnoreCase(mrForm.getSeletedFunction())){  
    			Long waTemplateUid = null;    	
    	     	waTemplateUid = (Long)request.getSession().getAttribute("waTemplateUid");
    			
    	     	Object[] oParamsED = new Object[] {waTemplateUid,""};
    			String sBeanJndiNameED = JNDINames.PAGE_MANAGEMENT_PROXY_EJB;
    			String sMethodED = "getWaUiElementDTDropDown";
    			Object obED = CallProxyEJB.callProxyEJB(oParamsED, sBeanJndiNameED, sMethodED, request.getSession());
    			Collection<Object> collED=(Collection)obED;    			
    			Iterator<Object> itTemp = collED.iterator();
    			
    			Map<String, Object> hashMap = new HashMap<String, Object>();    			
    			while(itTemp.hasNext()){
    				WaUiMetadataSummaryDT SummaryDT = (WaUiMetadataSummaryDT)itTemp.next();
    				hashMap.put(SummaryDT.getQuestionIdentifier(), SummaryDT);
    			}
    			
    			String selectedTarget[] =  mrForm.getSeletedTarDisOrEnabled();
    			//String checkPreviouTarget = this.getPreviousSeletedTargets(selectedTarget,waTemplateUid,"View",null, request);
    			boolean flagCheck = false;
    			ArrayList<String> obColl = this.getPreviousSeletedTargets(selectedTarget,waTemplateUid,"Update",mrForm.getRuleId().toString(), mrForm.getSeletedFunction(), request);
    			for( String s: selectedTarget){
    				if(obColl.contains(s)){    					
    					flagCheck = true;
    					break;
    				}
    			}
    			if(flagCheck){
    				Iterator<String> itTempTarget = obColl.iterator();
        			Map<String, Object> hashMapTarget = new HashMap<String, Object>();
        			
        			while(itTempTarget.hasNext()){
        				String str = (String)itTempTarget.next();
        				hashMapTarget.put(str, str);
        			}
    				mrForm.getErrorList().add("The Target Field, "+ HTMLEncoder.encodeHtml(convertArrayToString(selectedTarget, hashMapTarget, hashMap)) +" can only be associated with one enable/disable Source Field. ");
    				request.setAttribute("errors", mrForm.getErrorList());  
    				request.setAttribute("reload", "true");
    				return (mapping.findForward(ManageRulesAction.EDIT_BUSINESS_RULE));
    			}
    			
    			
    			
    			
	   			if(mrForm.getSeletedFunction().equalsIgnoreCase(ENABLE)){
	   				mrForm.setSeletedFunction("Enable");
	   			}
	   			else if(mrForm.getSeletedFunction().equalsIgnoreCase(DISABLE)){
	   				mrForm.setSeletedFunction("Disable");
	   				
	   			}    			
    			

    			
    			StringBuffer errorMessage = new StringBuffer();
    			Map<String, Object> sourceValMap = new HashMap<String, Object>();
    			Iterator sourceValColl = mrForm.getSourceValDisOrEnabled().iterator();			
    			while(sourceValColl.hasNext()){
    				DropDownCodeDT dt = (DropDownCodeDT)sourceValColl.next();
    				sourceValMap.put(dt.getKey() , dt);
    			}
    			
    			sourceLocalIdColl.add(mrForm.getSeletedSourceDisOrEnabled());
    			ruleMetaDataDT.setSourceQuestionIdentifierDTColl(sourceLocalIdColl);
    			
    			
				WaUiMetadataSummaryDT waUiMetadataSummaryDT = (WaUiMetadataSummaryDT)hashMap.get(mrForm.getSeletedSourceDisOrEnabled());
				if(waUiMetadataSummaryDT!=null){						
					errorMessage.append(waUiMetadataSummaryDT.getQuestionLabel());
					errorMessage.append(" ").append(mrForm.getSeletedLogDisOrEnabled());
        			errorMessage.append(" must be ( ");
        			boolean flag =false;    			
        			String selectedSourceVal[] =  mrForm.getSeletedSourceValDisOrEnabled();    			
        			List<String> soruceValList = new ArrayList<String>();    			
        			if(selectedSourceVal != null && selectedSourceVal.length > 0){ 
           				if (mrForm.isAnySourceValueIndDisOrEnabled() && selectedSourceVal.length == 1
           						&& selectedSourceVal[0].isEmpty()) {
           					errorMessage.append(NEDSSConstants.ANY_SOURCE_VALUE);
               				sourceValueSB.append(NEDSSConstants.ANY_SOURCE_VALUE);
               				soruceValList.add(new String(NEDSSConstants.ANY_SOURCE_VALUE));
               			} else for (String sourceVal : selectedSourceVal) {
        					if(sourceVal.trim().length()>0){
	        					if(flag){
	        						errorMessage.append(", ");	
	        						sourceValueSB.append(", ");
	        					}
	        					DropDownCodeDT dt = (DropDownCodeDT)sourceValMap.get(sourceVal);
	        					errorMessage.append(dt.getValue());
	        					sourceValueSB.append(dt.getValue());
	        					soruceValList.add(new String(sourceVal));
	        					flag = true;
        					}
        				}
        			} else if (mrForm.isAnySourceValueIndDisOrEnabled()) { //empty and any val checkbox is set
           				errorMessage.append(NEDSSConstants.ANY_SOURCE_VALUE);
           				sourceValueSB.append(NEDSSConstants.ANY_SOURCE_VALUE);
           				soruceValList.add(new String(NEDSSConstants.ANY_SOURCE_VALUE));
           			}     
        			errorMessage.append(" ) ");
        			flag = false;
        			
        			
        			if(selectedTarget != null){    				
        				for (String target : selectedTarget) {
        	    			targetLocalIdColl.add(target);
        	    			if(flag){
        						errorMessage.append(", ");
        					}
        	    			WaUiMetadataSummaryDT hasMapSummaryDT = (WaUiMetadataSummaryDT)hashMap.get(target);
        	    			if(hasMapSummaryDT!=null){
        	    				errorMessage.append(hasMapSummaryDT.getQuestionLabel()!=null?hasMapSummaryDT.getQuestionLabel():hasMapSummaryDT.getComponentName());
        	    				flag = true;
        	    			}
        				}
        			}
				}
    			ruleMetaDataDT.setTargetQuestionIdentifierDTColl(targetLocalIdColl);
    			ruleMetaDataDT.setErrMsgTxt(errorMessage.toString());
    			ruleMetaDataDT.setSourceValues(sourceValueSB.toString());
    			ruleMetaDataDT.setLogicValues(mrForm.getSeletedLogDisOrEnabled());
    			
        		// Start new approach
        		RuleElementDT elementDT1 = new RuleElementDT();
        		//Source setting  		
        		elementDT1.setSourceQuestionIdentifier(mrForm.getSeletedSourceDisOrEnabled());
        		// Set sourceValue
        		String selectedSourceVal[] =  mrForm.getSeletedSourceValDisOrEnabled();    			
    			List<String> soruceValList = new ArrayList<String>();    			
    			if(selectedSourceVal != null){    				
    				for (String sourceVal : selectedSourceVal) {						
    					soruceValList.add(new String(sourceVal));					
    				}
    			} 
       			//if Any Source Value checkbox is set 
       			if (mrForm.isAnySourceValueIndDisOrEnabled() && soruceValList.isEmpty())
       				soruceValList.add(new String(NEDSSConstants.ANY_SOURCE_VALUE));   			
    			
    			elementDT1.setSourceValues(soruceValList);
    			if("<>".equals(mrForm.getSeletedLogDisOrEnabled())){
					elementDT1.setComparator(" != ");
				}else{
					elementDT1.setComparator(mrForm.getSeletedLogDisOrEnabled());
				}
    			//Set Target Collection	
    			Collection<String> collTarget = new ArrayList<String>();
    			if(selectedTarget != null){    				
    				for (String target : selectedTarget) {	    			
    	    			collTarget.add(target);
    				}
    			}
    			ruleMetaDataDT.setTargetType(mrForm.getSelectedTargetType());
    			elementDT1.setTargetQuestionIdentifierColl(collTarget);    			
    			ruleElementMap.put(new Integer(1), elementDT1);
    			ruleMetaDataDT.setRuleElementMap(ruleElementMap);
    			ruleMetaDataDT.setTargetType(mrForm.getSelectedTargetType());
    		}
    		/////////////////////Require If//////////////////////////////
    		else if( "Require If".equalsIgnoreCase(mrForm.getSeletedFunction())){  
       			Long waTemplateUid = null;    	
       	     	waTemplateUid = (Long)request.getSession().getAttribute("waTemplateUid");
       			
       	     	Object[] oParamsRQ = new Object[] {waTemplateUid,""};
       			String sBeanJndiNameRQ = JNDINames.PAGE_MANAGEMENT_PROXY_EJB;
       			String sMethodRQ = "getWaUiElementDTDropDown";
       			Object obRQ = CallProxyEJB.callProxyEJB(oParamsRQ, sBeanJndiNameRQ, sMethodRQ, request.getSession());
       			Collection<Object> collRQ=(Collection)obRQ;    			
       			Iterator<Object> itTemp = collRQ.iterator();
       			
       			Map<String, Object> hashMap = new HashMap<String, Object>();    			
       			while(itTemp.hasNext()){
       				WaUiMetadataSummaryDT SummaryDT = (WaUiMetadataSummaryDT)itTemp.next();
       				hashMap.put(SummaryDT.getQuestionIdentifier(), SummaryDT);
       			}
       			
       			String selectedTarget[] =  mrForm.getSelectedTargetRequired();
       			//String checkPreviouTarget = this.getPreviousSeletedTargets(selectedTarget,waTemplateUid,"View",null, request);
       			boolean flagCheck = false;
       			ArrayList<String> obColl = this.getPreviousSeletedTargets(selectedTarget,waTemplateUid,"Update",mrForm.getRuleId().toString(),  mrForm.getSeletedFunction(), request);
       			for( String s: selectedTarget){
       				if(obColl.contains(s)){    					
       					flagCheck = true;
       					break;
       				}
       			}
       			if(flagCheck){
       				Iterator<String> itTempTarget = obColl.iterator();
           			Map<String, Object> hashMapTarget = new HashMap<String, Object>();
           			
           			while(itTempTarget.hasNext()){
           				String str = (String)itTempTarget.next();
           				hashMapTarget.put(str, str);
           			}
           			//gst todo check if this is valid
       				mrForm.getErrorList().add("The Target Field, "+ HTMLEncoder.encodeHtml(convertArrayToString(selectedTarget, hashMapTarget, hashMap)) +" can only be associated with one Require If Source Field. ");
       				request.setAttribute("errors", mrForm.getErrorList());  
       				request.setAttribute("reload", "true");
       				return (mapping.findForward(ManageRulesAction.EDIT_BUSINESS_RULE));
       			}

       			StringBuffer errorMessage = new StringBuffer();
       			Map<String, Object> sourceValMap = new HashMap<String, Object>();
       			Iterator sourceValColl = mrForm.getSourceValRequired().iterator();			
       			while(sourceValColl.hasNext()){
       				DropDownCodeDT dt = (DropDownCodeDT)sourceValColl.next();
       				sourceValMap.put(dt.getKey() , dt);
       			}
       			
       			sourceLocalIdColl.add(mrForm.getSelectedSourceRequired());
       			ruleMetaDataDT.setSourceQuestionIdentifierDTColl(sourceLocalIdColl);
       			
       			
   				WaUiMetadataSummaryDT waUiMetadataSummaryDT = (WaUiMetadataSummaryDT)hashMap.get(mrForm.getSelectedSourceRequired());
   				if(waUiMetadataSummaryDT!=null){						
   					errorMessage.append(waUiMetadataSummaryDT.getQuestionLabel());
   					errorMessage.append(" ").append(mrForm.getSelectedLogicRequired());
           			errorMessage.append(" must be ( ");
           			boolean flag =false;    			
           			String selectedSourceVal[] =  mrForm.getSelectedSourceValRequired();    			
           			List<String> soruceValList = new ArrayList<String>();    			
           			if(selectedSourceVal != null && selectedSourceVal.length > 0){ 
           				if (mrForm.isAnySourceValueInd() && selectedSourceVal.length == 1
           						&& selectedSourceVal[0].isEmpty()) {
           					errorMessage.append(NEDSSConstants.ANY_SOURCE_VALUE);
               				sourceValueSB.append(NEDSSConstants.ANY_SOURCE_VALUE);
               				soruceValList.add(new String(NEDSSConstants.ANY_SOURCE_VALUE));
               			} else for (String sourceVal : selectedSourceVal) {
           					if(sourceVal.trim().length()>0){
   	        					if(flag){
   	        						errorMessage.append(", ");	
   	        						sourceValueSB.append(", ");
   	        					}
   	        					DropDownCodeDT dt = (DropDownCodeDT)sourceValMap.get(sourceVal);
   	        					errorMessage.append(dt.getValue());
   	        					sourceValueSB.append(dt.getValue());
   	        					soruceValList.add(new String(sourceVal));
   	        					flag = true;
           					}
           				}
           			} else if (mrForm.isAnySourceValueInd()) { //empty and any val checkbox is set
           				errorMessage.append(NEDSSConstants.ANY_SOURCE_VALUE);
           				sourceValueSB.append(NEDSSConstants.ANY_SOURCE_VALUE);
           				soruceValList.add(new String(NEDSSConstants.ANY_SOURCE_VALUE));
           			}   
           			errorMessage.append(" ) ");
           			flag = false;
           			
           			
           			if(selectedTarget != null){    				
           				for (String target : selectedTarget) {
           	    			targetLocalIdColl.add(target);
           	    			if(flag){
           						errorMessage.append(", ");
           					}
           	    			WaUiMetadataSummaryDT hasMapSummaryDT = (WaUiMetadataSummaryDT)hashMap.get(target);
           	    			if(hasMapSummaryDT!=null){
           	    				errorMessage.append(hasMapSummaryDT.getQuestionLabel());
           	    				flag = true;
           	    			}
           				}
           			}
   				}
       			ruleMetaDataDT.setTargetQuestionIdentifierDTColl(targetLocalIdColl);
       			ruleMetaDataDT.setErrMsgTxt(errorMessage.toString());

       			ruleMetaDataDT.setSourceValues(sourceValueSB.toString());
       			if (mrForm.isAnySourceValueInd() && (mrForm.getSelectedLogicRequired() == null || mrForm.getSelectedLogicRequired().isEmpty()))
       				ruleMetaDataDT.setLogicValues(NEDSSConstants.ANY_SOURCE_VALUE_COMPARATER);
       			else
       				ruleMetaDataDT.setLogicValues(mrForm.getSelectedLogicRequired());
       			
           		// Start new approach
           		RuleElementDT elementDT1 = new RuleElementDT();
           		//Source setting   		
           		elementDT1.setSourceQuestionIdentifier(mrForm.getSelectedSourceRequired());
           		// Set sourceValue
           		String selectedSourceVal[] =  mrForm.getSelectedSourceValRequired();    			
       			List<String> soruceValList = new ArrayList<String>();    			
       			if(selectedSourceVal != null){    				
       				for (String sourceVal : selectedSourceVal) {						
       					soruceValList.add(new String(sourceVal));					
       				}
       			} 
       			//if Any Source Value checkbox is set 
       			if (mrForm.isAnySourceValueInd() && soruceValList.isEmpty())
       				soruceValList.add(new String(NEDSSConstants.ANY_SOURCE_VALUE));
       			
       			elementDT1.setSourceValues(soruceValList);
       			if("<>".equals(mrForm.getSelectedLogicRequired())){
   					elementDT1.setComparator(" != ");
   				}else{
   					elementDT1.setComparator(mrForm.getSelectedLogicRequired());
   				}

       			//Set Target Collection       			
       			Collection<String> collTarget = new ArrayList<String>();
       			if(selectedTarget != null){    				
       				for (String target : selectedTarget) {	    			
       	    			collTarget.add(target);
       				}
       			}
       			ruleMetaDataDT.setTargetType(mrForm.getSelectedTargetType());
       			elementDT1.setTargetQuestionIdentifierColl(collTarget);    			
       			ruleElementMap.put(new Integer(1), elementDT1);
       			ruleMetaDataDT.setRuleElementMap(ruleElementMap);

       		}    		/////////////////////Hide Unhide//////////////////////////////
        		else if( HIDE.equalsIgnoreCase(mrForm.getSeletedFunction()) ||
          				 UNHIDE.equalsIgnoreCase(mrForm.getSeletedFunction())){  
           			Long waTemplateUid = null;    	
           	     	waTemplateUid = (Long)request.getSession().getAttribute("waTemplateUid");
           			
           	     	Object[] oParamsHD = new Object[] {waTemplateUid,""};
           			String sBeanJndiNameHD = JNDINames.PAGE_MANAGEMENT_PROXY_EJB;
           			String sMethodHD = "getWaUiElementDTDropDown";
           			Object obHD = CallProxyEJB.callProxyEJB(oParamsHD, sBeanJndiNameHD, sMethodHD, request.getSession());
           			Collection<Object> collHD=(Collection)obHD;    			
           			Iterator<Object> itTemp = collHD.iterator();
           			
           			Map<String, Object> hashMap = new HashMap<String, Object>();    			
           			while(itTemp.hasNext()){
           				WaUiMetadataSummaryDT SummaryDT = (WaUiMetadataSummaryDT)itTemp.next();
           				hashMap.put(SummaryDT.getQuestionIdentifier(), SummaryDT);
           			}
           			
           			String selectedTarget[] =  mrForm.getSelectedTargetHideOrUnhide();
           			boolean flagCheck = false;
           			ArrayList<String> obColl = this.getPreviousSeletedTargets(selectedTarget,waTemplateUid,"Update",mrForm.getRuleId().toString(), mrForm.getSeletedFunction(), request);
           			for( String s: selectedTarget){
           				if(obColl.contains(s)){    					
           					flagCheck = true;
           					break;
           				}
           			}
           			if(flagCheck){
           				Iterator<String> itTempTarget = obColl.iterator();
               			Map<String, Object> hashMapTarget = new HashMap<String, Object>();
               			
               			while(itTempTarget.hasNext()){
               				String str = (String)itTempTarget.next();
               				hashMapTarget.put(str, str);
               			}
           				mrForm.getErrorList().add("The Target Field, "+ HTMLEncoder.encodeHtml(convertArrayToString(selectedTarget, hashMapTarget, hashMap)) +" can only be associated with one hide/unhide Source Field. ");
           				request.setAttribute("errors", mrForm.getErrorList());  
           				request.setAttribute("reload", "true");
           				return (mapping.findForward(ManageRulesAction.EDIT_BUSINESS_RULE));
           			}
           			
       	   			if(mrForm.getSeletedFunction().equalsIgnoreCase(HIDE)){
       	   				mrForm.setSeletedFunction("Hide");
       	   			} else if(mrForm.getSeletedFunction().equalsIgnoreCase(UNHIDE)){
       	   				mrForm.setSeletedFunction("Unhide");
       	   			}    			
 
           			StringBuffer errorMessage = new StringBuffer();
           			Map<String, Object> sourceValMap = new HashMap<String, Object>();
           			Iterator sourceValColl = mrForm.getSourceValHideOrUnhide().iterator();			
           			while(sourceValColl.hasNext()){
           				DropDownCodeDT dt = (DropDownCodeDT)sourceValColl.next();
           				sourceValMap.put(dt.getKey() , dt);
           			}
           			
           			sourceLocalIdColl.add(mrForm.getSelectedSourceHideOrUnhide());
           			ruleMetaDataDT.setSourceQuestionIdentifierDTColl(sourceLocalIdColl);
           			
           			
       				WaUiMetadataSummaryDT waUiMetadataSummaryDT = (WaUiMetadataSummaryDT)hashMap.get(mrForm.getSelectedSourceHideOrUnhide());
       				if(waUiMetadataSummaryDT!=null){						
       					errorMessage.append(waUiMetadataSummaryDT.getQuestionLabel());
       					errorMessage.append(" ").append(mrForm.getSelectedLogicHideOrUnhide());
               			errorMessage.append(" must be ( ");
               			boolean flag =false;    			
               			String selectedSourceVal[] =  mrForm.getSelectedSourceValHideOrUnhide();    			
               			List<String> soruceValList = new ArrayList<String>();    			
               			if(selectedSourceVal != null && selectedSourceVal.length > 0){ 
                  				if (mrForm.isAnySourceValueIndHideOrUnhide() && selectedSourceVal.length == 1
                  						&& selectedSourceVal[0].isEmpty()) {
                  					errorMessage.append(NEDSSConstants.ANY_SOURCE_VALUE);
                      				sourceValueSB.append(NEDSSConstants.ANY_SOURCE_VALUE);
                      				soruceValList.add(new String(NEDSSConstants.ANY_SOURCE_VALUE));
                      			} else for (String sourceVal : selectedSourceVal) {
               					if(sourceVal.trim().length()>0){
       	        					if(flag){
       	        						errorMessage.append(", ");	
       	        						sourceValueSB.append(", ");
       	        					}
       	        					DropDownCodeDT dt = (DropDownCodeDT)sourceValMap.get(sourceVal);
       	        					errorMessage.append(dt.getValue());
       	        					sourceValueSB.append(dt.getValue());
       	        					soruceValList.add(new String(sourceVal));
       	        					flag = true;
               					}
               				}
               			} else if (mrForm.isAnySourceValueIndHideOrUnhide()) { //empty and any val checkbox is set
                  				errorMessage.append(NEDSSConstants.ANY_SOURCE_VALUE);
                  				sourceValueSB.append(NEDSSConstants.ANY_SOURCE_VALUE);
                  				soruceValList.add(new String(NEDSSConstants.ANY_SOURCE_VALUE));
                  		}     
               			errorMessage.append(" ) ");
               			flag = false;
               			
               			
               			if(selectedTarget != null){    				
               				for (String target : selectedTarget) {
               	    			targetLocalIdColl.add(target);
               	    			if(flag){
               						errorMessage.append(", ");
               					}
               	    			WaUiMetadataSummaryDT hasMapSummaryDT = (WaUiMetadataSummaryDT)hashMap.get(target);
               	    			if(hasMapSummaryDT!=null){
               	    				errorMessage.append(hasMapSummaryDT.getQuestionLabel()!=null?hasMapSummaryDT.getQuestionLabel():hasMapSummaryDT.getComponentName());
               	    				flag = true;
               	    			}
               				}
               			}
       				}
           			ruleMetaDataDT.setTargetQuestionIdentifierDTColl(targetLocalIdColl);
           			ruleMetaDataDT.setErrMsgTxt(errorMessage.toString());
           			ruleMetaDataDT.setSourceValues(sourceValueSB.toString());
           			ruleMetaDataDT.setLogicValues(mrForm.getSelectedLogicHideOrUnhide());
           			
               		// Start new approach
               		RuleElementDT elementDT1 = new RuleElementDT();
               		//Source setting  		
               		elementDT1.setSourceQuestionIdentifier(mrForm.getSelectedSourceHideOrUnhide());
               		// Set sourceValue
               		String selectedSourceVal[] =  mrForm.getSelectedSourceValHideOrUnhide();    			
           			List<String> soruceValList = new ArrayList<String>();    			
           			if(selectedSourceVal != null){    				
           				for (String sourceVal : selectedSourceVal) {						
           					soruceValList.add(new String(sourceVal));					
           				}
           			} 
              		//if Any Source Value checkbox is set 
              		if (mrForm.isAnySourceValueIndHideOrUnhide() && soruceValList.isEmpty())
              				soruceValList.add(new String(NEDSSConstants.ANY_SOURCE_VALUE));   			
           			
           			elementDT1.setSourceValues(soruceValList);
           			if("<>".equals(mrForm.getSelectedLogicHideOrUnhide())){
       					elementDT1.setComparator(" != ");
       				}else{
       					elementDT1.setComparator(mrForm.getSelectedLogicHideOrUnhide());
       				}
           			//Set Target Collection	
           			Collection<String> collTarget = new ArrayList<String>();
           			if(selectedTarget != null){    				
           				for (String target : selectedTarget) {	    			
           	    			collTarget.add(target);
           				}
           			}
           			ruleMetaDataDT.setTargetType(mrForm.getSelectedTargetType());
           			elementDT1.setTargetQuestionIdentifierColl(collTarget);    			
           			ruleElementMap.put(new Integer(1), elementDT1);
           			ruleMetaDataDT.setRuleElementMap(ruleElementMap);
           			ruleMetaDataDT.setTargetType(mrForm.getSelectedTargetType());
           		} //end of Hide/Unhide
       		

			
    		NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) request.getSession().getAttribute("NBSSecurityObject");
    		String userId = nbsSecurityObj.getTheUserProfile().getTheUser().getEntryID();
    		ruleMetaDataDT.setRecordStatusCd(NEDSSConstants.ACTIVE);
    		ruleMetaDataDT.setAddUserId(Long.valueOf(userId));
    		ruleMetaDataDT.setAddTime(new Timestamp(new Date().getTime()));
    		ruleMetaDataDT.setLastChgUserId(Long.valueOf(userId));
       		ruleMetaDataDT.setLastChgTime(new Timestamp(new Date().getTime()));
       		ruleMetaDataDT.setRecordStatusTime(new Timestamp(new Date().getTime()));
       	    //ruleMetaDataDT.setRuleElementMap(ruleElementMap);
    		ruleMetaDataDT.setRuleCd(mrForm.getSeletedFunction());    		
    		Long waTemplateUid = null;    	
         	waTemplateUid = (Long)request.getSession().getAttribute("waTemplateUid");
         	ruleMetaDataDT.setWaTemplateUid(waTemplateUid);
         	String ruleExpression = RuleManagementActionUtil.getRulesExpression(mrForm);
         	ruleMetaDataDT.setRuleExpression(ruleExpression);
         	ruleMetaDataDT.setWaRuleMetadataUid(mrForm.getRuleId());
         	Object[] oParams = new Object[] { ruleMetaDataDT };
    		String sBeanJndiName = JNDINames.PAGE_MANAGEMENT_PROXY_EJB;
			String sMethod = "updateRuleMetadataDT";
    		Object ob = CallProxyEJB.callProxyEJB(oParams, sBeanJndiName, sMethod, request.getSession());
    		ruleMetaDataDT =(WaRuleMetadataDT)ob;
    		request.getSession().setAttribute("waRuleMetadataDT", ruleMetaDataDT);
    	}catch(Exception e){
    		logger.error("Error in ManageRulesAction.updateBusinessRule"+ e.getMessage());
    		e.printStackTrace();
    		throw new ServletException("Error while updating business rule: "+e.getMessage(),e);
    	}
    	request.setAttribute("messageInd", "UPDATE");
        return mapping.findForward(ManageRulesAction.RELOAD_VIEW_RULE);
    }
    
 
    @SuppressWarnings("unchecked")
	public ActionForward viewBuinessRule(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception
    {

    	logger.debug("Begin ManageRulesAction view rule");    	
    	request.setAttribute("PageTitle", "Manage Pages:  View Rule");
    	ManageRulesForm mrForm = (ManageRulesForm)form;
    	mrForm.clearForm();
    	
        try{
	    	Long waRuleMetadataUid = request.getParameter("waRuleMetadataUid")!=null?
	    				Long.valueOf((String)request.getParameter("waRuleMetadataUid")):null;
	    	if(waRuleMetadataUid== null){
	    		WaRuleMetadataDT	waRuleMetadataDT = (WaRuleMetadataDT)request.getSession().getAttribute("waRuleMetadataDT");
	    		waRuleMetadataUid = waRuleMetadataDT.getWaRuleMetadataUid();
	    	}
	    	WaRuleMetadataDT waRuleMetadataDT = new WaRuleMetadataDT();
	    	request.setAttribute("waRuleMetadataUid", waRuleMetadataUid);
	    	
	    	try{
	    		Object[] oParams = new Object[] { waRuleMetadataUid };
	    		String sBeanJndiName = JNDINames.PAGE_MANAGEMENT_PROXY_EJB;
				String sMethod = "selectWaRuleMetadataDT";
	    		Object ob = CallProxyEJB.callProxyEJB(oParams, sBeanJndiName, sMethod, request.getSession());
	    		waRuleMetadataDT =(WaRuleMetadataDT)ob;
	    		String templateType = mrForm.getTemplateType();    		
	    		request.setAttribute("templateType", templateType);
	    	}catch(Exception e){
	    		throw new ServletException("Error while View business rule: "+e.getMessage(),e);
	    	}
	    	
	    	HashMap<Object, Object> ccMap = (HashMap<Object, Object>)request.getSession().getAttribute("ccMap");
	    	String codeDesc = ccMap!=null?(String)ccMap.get(waRuleMetadataDT.getWaTemplateUid()):new String();
	    	request.setAttribute("codeDesc", HTMLEncoder.encodeHtml(codeDesc));
	    	
	    	mrForm.setRuleId(waRuleMetadataDT.getWaRuleMetadataUid());
	    	mrForm.setRuleDescription(waRuleMetadataDT.getRuleDescTxt());
	    	mrForm.setSeletedFunction(waRuleMetadataDT.getRuleCd());
	    	Map<Integer,RuleElementDT> mapRuleElementDT = waRuleMetadataDT.getRuleElementMap();
	    	
	    	
	    	String selectedSource="";    	
	    	String selectedTarget="";
	    	String selectedLogicDateComp="";
	    	
			
			if("Date Compare".equalsIgnoreCase(waRuleMetadataDT.getRuleCd())){
				
				//mrForm.setSeletedFunction(DATE_COMPARE);
				mrForm.setSeletedFunction("Date Compare");
				Object[] oParamsED = new Object[] {waRuleMetadataDT.getWaTemplateUid(),"DATE"};
				String sBeanJndiNameED = JNDINames.PAGE_MANAGEMENT_PROXY_EJB;
				String sMethodED = "getWaUiElementDTDropDown";
				Object obDC = CallProxyEJB.callProxyEJB(oParamsED, sBeanJndiNameED, sMethodED, request.getSession());				
				Iterator<Object> it = ((Collection)obDC).iterator();
				Map hashMap = new HashMap();
		    	for(int i =0; mapRuleElementDT.size()>i;i++){
		    		RuleElementDT ruleElementDT =(RuleElementDT) mapRuleElementDT.get(new Integer(i));
			    	selectedLogicDateComp =ruleElementDT.getComparator();
			    	
	    			while(it.hasNext()){
	    				WaUiMetadataSummaryDT SummaryDT = (WaUiMetadataSummaryDT)it.next();
	    				hashMap.put(SummaryDT.getQuestionIdentifier(), SummaryDT);
	    			} 
			    	
			    	selectedSource = ((WaUiMetadataSummaryDT)hashMap.get(ruleElementDT.getSourceQuestionIdentifier())).getQuestionLabel();
					mrForm.setSeletedSourceDateComp(selectedSource +"("+ruleElementDT.getSourceQuestionIdentifier()+")");
			    	mrForm.setSeletedLogicDateComp(selectedLogicDateComp);
			    	
		    		// Setting target values
					StringBuffer  targetValDC= new StringBuffer();	
		    		if(ruleElementDT.getTargetQuestionIdentifierColl()!=null){
			    		Iterator<String> itTargetDC = ruleElementDT.getTargetQuestionIdentifierColl().iterator();
			    		int size = ruleElementDT.getTargetQuestionIdentifierColl().size();
			    		int increment =0;
			    		while(itTargetDC.hasNext()){
			    			String str =((String)itTargetDC.next()).trim();
			    			WaUiMetadataSummaryDT waUiMetadataSummaryDT = (WaUiMetadataSummaryDT)hashMap.get(str);
			    			if(waUiMetadataSummaryDT!=null){
			    				if(waUiMetadataSummaryDT.getQuestionLabel()!=null)
		    					 targetValDC.append(waUiMetadataSummaryDT.getQuestionLabel());
			    				else
			    					targetValDC.append(waUiMetadataSummaryDT.getComponentName());
		    					targetValDC.append(" (").append(waUiMetadataSummaryDT.getQuestionIdentifier()).append(")");
		    					if(increment < (size-1)){
		    						targetValDC.append(" | ");
				    				increment++;
				    			}
			    			}
				    	}	 
		    		}
			    		
			    	 mrForm.setSeletedTargetDateComp(new String[]{targetValDC.toString()});
			    	 mrForm.setViewTargetDateComp(targetValDC.toString());
		    	}
		    	
		    	mrForm.setErrorMessage(waRuleMetadataDT.getErrMsgTxt());
		     ///////////////////////ENABLE/DISABLE///////////////////////////
			}else if(ENABLE.equalsIgnoreCase(waRuleMetadataDT.getRuleCd()) || 
					DISABLE.equalsIgnoreCase(waRuleMetadataDT.getRuleCd())){
				
				Object[] oParamsED = new Object[] {waRuleMetadataDT.getWaTemplateUid(),""};
				String sBeanJndiNameED = JNDINames.PAGE_MANAGEMENT_PROXY_EJB;
				String sMethodED = "getWaUiElementDTDropDown";
				Object obED = CallProxyEJB.callProxyEJB(oParamsED, sBeanJndiNameED, sMethodED, request.getSession());
				
				ArrayList<Object> targetCodeList = new ArrayList();
				if("Question".equalsIgnoreCase(waRuleMetadataDT.getTargetType())){
					RuleManagementActionUtil util = new RuleManagementActionUtil();
					try {
						targetCodeList = util.getTargetTypeValues(waRuleMetadataDT.getWaTemplateUid(),
								waRuleMetadataDT.getTargetType(),waRuleMetadataDT.getSourceQuestionIdentifierString(),
								"View",null, mrForm.getSeletedFunction(), request);
						mrForm.setTarDisOrEnabled(targetCodeList);
					} catch (Exception e) {			
						e.printStackTrace();
					}
				}else{
					RuleManagementActionUtil util = new RuleManagementActionUtil();
					try {
						targetCodeList = util.getTargetTypeValues(waRuleMetadataDT.getWaTemplateUid(),waRuleMetadataDT.getTargetType(),
								waRuleMetadataDT.getSourceQuestionIdentifierString(),"View", null, mrForm.getSeletedFunction(), request);
						mrForm.setTarDisOrEnabled(targetCodeList);		
					} catch (Exception e) {			
						e.printStackTrace();
					}
				}
				
				Iterator<Object> itED = ((Collection)obED).iterator();
				Map<String, Object> hashMap = new HashMap<String, Object>();
				while(itED.hasNext()){
					WaUiMetadataSummaryDT SummaryDT = (WaUiMetadataSummaryDT)itED.next();
					hashMap.put(SummaryDT.getQuestionIdentifier(), SummaryDT);
				}
				
				Iterator<Object> itTargetED = targetCodeList.iterator();
				Map<String, Object> targetHashMap = new HashMap<String, Object>();
				while(itTargetED.hasNext()){
					WaUiMetadataSummaryDT SummaryDT = (WaUiMetadataSummaryDT)itTargetED.next();
					targetHashMap.put(SummaryDT.getQuestionIdentifier(), SummaryDT);
				}
				
				Map<String, Object> sourceValMap = new HashMap<String, Object>();
				
				
				StringBuffer source = new StringBuffer();
				String comparator = new String();
				StringBuffer sourceVal = new StringBuffer();
				StringBuffer targetVal = new StringBuffer();
		    	for(int i =0; mapRuleElementDT.size()>i;i++){
		    		RuleElementDT ruleElementDT =(RuleElementDT) mapRuleElementDT.get(new Integer(i));	 
		    		
		    		// Setting source value
		    		if(hashMap.get(ruleElementDT.getSourceQuestionIdentifier())!=null){	    			
		    			WaUiMetadataSummaryDT waUiMetadataSummaryDT = (WaUiMetadataSummaryDT)hashMap.get(ruleElementDT.getSourceQuestionIdentifier());
		    			source.append(waUiMetadataSummaryDT.getQuestionLabel()).append(" (");
		    			source.append(ruleElementDT.getSourceQuestionIdentifier()).append(")");
		    			
		    			Long codeSetGroupId = waUiMetadataSummaryDT.getCodeSetGroupId();
		    			
		    			CachedDropDownValues cdv = new CachedDropDownValues();
		    			Collection<Object> sourceValDE = null;
		    			if (ruleElementDT.getSourceQuestionIdentifier().equals(PageConstants.CONDITION_CD)) 
		    				sourceValDE = CachedDropDowns.getConditionDropDownForTemplate(waRuleMetadataDT.getWaTemplateUid());
		    			else 
		    				sourceValDE = CachedDropDowns.getCodedValueForType(cdv.getTheCodeSetNm(codeSetGroupId));
		    			Iterator sourceValColl = sourceValDE.iterator();			
		    			while(sourceValColl.hasNext()){
		    				DropDownCodeDT dt = (DropDownCodeDT)sourceValColl.next();
		    				sourceValMap.put(dt.getKey() , dt);
		    			}
		    			Collection<String> collSourceVal = ruleElementDT.getSourceValues();
			    		Iterator<String> itSourceVal = collSourceVal.iterator();
			    		int size = collSourceVal.size();
			    		int increment =0;
			    		while(itSourceVal.hasNext()){
			    			DropDownCodeDT dt = (DropDownCodeDT)sourceValMap.get(itSourceVal.next());
			    			if(dt!=null){
				    			sourceVal.append(dt.getValue());
				    			if(increment < (size-1)){
				    				sourceVal.append(" | ");
				    				increment++;
				    			}
			    			}
			    		}
		    		}
		    		// Setting target values
		    		if(ruleElementDT.getTargetQuestionIdentifierColl()!=null){
			    		Iterator<String> itTarget = ruleElementDT.getTargetQuestionIdentifierColl().iterator();
			    		int size = ruleElementDT.getTargetQuestionIdentifierColl().size();
			    		int increment =0;
			    		while(itTarget.hasNext()){
			    			String str =((String)itTarget.next()).trim();
			    			WaUiMetadataSummaryDT waUiMetadataSummaryDT = (WaUiMetadataSummaryDT)targetHashMap.get(str);
			    			if(waUiMetadataSummaryDT!=null){
			    				if(waUiMetadataSummaryDT.getQuestionLabel()!=null)
			    					targetVal.append(waUiMetadataSummaryDT.getQuestionLabel());
			    				else
			    					targetVal.append(waUiMetadataSummaryDT.getComponentName());
		    					targetVal.append(" (").append(waUiMetadataSummaryDT.getQuestionIdentifier()).append(")");
		    					if(increment < (size-1)){
		    						targetVal.append(" | ");
				    				increment++;
				    			}
			    			}
				    	}	 
		    		}
		    		comparator = ruleElementDT.getComparator();
		    		
		    	}
		    	mrForm.setSelectedTargetType(waRuleMetadataDT.getTargetType());
		    	mrForm.setSeletedSourceDisOrEnabled(source.toString());
		    	mrForm.setSeletedLogDisOrEnabled(waRuleMetadataDT.getLogicValues());
		    	mrForm.setSeletedSourceValDisOrEnabled(new String[]{sourceVal.toString()});
	    		mrForm.setSeletedTarDisOrEnabled(new String[]{targetVal.toString()});
	    		if(ENABLE.contains(waRuleMetadataDT.getRuleCd())){
					mrForm.setSeletedFunction(ENABLE);
				}else if(DISABLE.contains(waRuleMetadataDT.getRuleCd())){
					mrForm.setSeletedFunction(DISABLE);
				}
	    		mrForm.setErrorMessage(waRuleMetadataDT.getErrMsgTxt());
	    	////////////////REQUIRE IF////////////////////////
			} else if("Require If".equalsIgnoreCase(waRuleMetadataDT.getRuleCd())){
			
			Object[] oParamsRQ = new Object[] {waRuleMetadataDT.getWaTemplateUid(),""};
			String sBeanJndiNameRQ = JNDINames.PAGE_MANAGEMENT_PROXY_EJB;
			String sMethodRQ = "getWaUiElementDTDropDown";
			Object obRQ = CallProxyEJB.callProxyEJB(oParamsRQ, sBeanJndiNameRQ, sMethodRQ, request.getSession());
			
			ArrayList<Object> targetCodeList = new ArrayList();
			RuleManagementActionUtil util = new RuleManagementActionUtil();
			try {
					targetCodeList = util.getTargetTypeValues(waRuleMetadataDT.getWaTemplateUid(),
							waRuleMetadataDT.getTargetType(),waRuleMetadataDT.getSourceQuestionIdentifierString(),
							"View",null, mrForm.getSeletedFunction(), request);
					mrForm.setTargetRequired(targetCodeList);
			} catch (Exception e) {			
					e.printStackTrace();
			}

			
			Iterator<Object> itRQ = ((Collection)obRQ).iterator();
			Map<String, Object> hashMap = new HashMap<String, Object>();
			while(itRQ.hasNext()){
				WaUiMetadataSummaryDT SummaryDT = (WaUiMetadataSummaryDT)itRQ.next();
				hashMap.put(SummaryDT.getQuestionIdentifier(), SummaryDT);
			}
			
			Iterator<Object> itTargetRQ = targetCodeList.iterator();
			Map<String, Object> targetHashMap = new HashMap<String, Object>();
			while(itTargetRQ.hasNext()){
				WaUiMetadataSummaryDT SummaryDT = (WaUiMetadataSummaryDT)itTargetRQ.next();
				targetHashMap.put(SummaryDT.getQuestionIdentifier(), SummaryDT);
			}
			
			Map<String, Object> sourceValMap = new HashMap<String, Object>();
			
			
			StringBuffer source = new StringBuffer();
			String comparator = new String();
			StringBuffer sourceVal = new StringBuffer();
			StringBuffer targetVal = new StringBuffer();
	    	for(int i =0; mapRuleElementDT.size()>i;i++){
	    		RuleElementDT ruleElementDT =(RuleElementDT) mapRuleElementDT.get(new Integer(i));	 
	    		
	    		// Setting source value
	    		if(hashMap.get(ruleElementDT.getSourceQuestionIdentifier())!=null){	    			
	    			WaUiMetadataSummaryDT waUiMetadataSummaryDT = (WaUiMetadataSummaryDT)hashMap.get(ruleElementDT.getSourceQuestionIdentifier());
	    			source.append(waUiMetadataSummaryDT.getQuestionLabel()).append(" (");
	    			source.append(ruleElementDT.getSourceQuestionIdentifier()).append(")");
	    			
	    			Long codeSetGroupId = waUiMetadataSummaryDT.getCodeSetGroupId();
	    			
	    			CachedDropDownValues cdv = new CachedDropDownValues();		
	    			Collection<Object> sourceValDE = CachedDropDowns.getCodedValueForType(cdv.getTheCodeSetNm(codeSetGroupId));
	    			Iterator sourceValColl = sourceValDE.iterator();			
	    			while(sourceValColl.hasNext()){
	    				DropDownCodeDT dt = (DropDownCodeDT)sourceValColl.next();
	    				sourceValMap.put(dt.getKey() , dt);
	    			}
	    			Collection<String> collSourceVal = ruleElementDT.getSourceValues();
		    		Iterator<String> itSourceVal = collSourceVal.iterator();
		    		int size = collSourceVal.size();
		    		int increment =0;
		    		while(itSourceVal.hasNext()){
		    			DropDownCodeDT dt = (DropDownCodeDT)sourceValMap.get(itSourceVal.next());
		    			if(dt!=null){
			    			sourceVal.append(dt.getValue());
			    			if(increment < (size-1)){
			    				sourceVal.append(" | ");
			    				increment++;
			    			}
		    			}
		    		}
	    		}
	    		// Setting target values
	    		if(ruleElementDT.getTargetQuestionIdentifierColl()!=null){
		    		Iterator<String> itTarget = ruleElementDT.getTargetQuestionIdentifierColl().iterator();
		    		int size = ruleElementDT.getTargetQuestionIdentifierColl().size();
		    		int increment =0;
		    		while(itTarget.hasNext()){
		    			String str =((String)itTarget.next()).trim();
		    			WaUiMetadataSummaryDT waUiMetadataSummaryDT = (WaUiMetadataSummaryDT)targetHashMap.get(str);
		    			if(waUiMetadataSummaryDT!=null){
	    					targetVal.append(waUiMetadataSummaryDT.getQuestionLabel());   
	    					targetVal.append(" (").append(waUiMetadataSummaryDT.getQuestionIdentifier()).append(")");
	    					if(increment < (size-1)){
	    						targetVal.append(" | ");
			    				increment++;
			    			}
		    			}
			    	}	 
	    		}
	    		comparator = ruleElementDT.getComparator();
	    		
	    	}
	    	mrForm.setSelectedTargetType(waRuleMetadataDT.getTargetType());
	    	mrForm.setSelectedSourceRequired(source.toString());
	    	mrForm.setSelectedLogicRequired(waRuleMetadataDT.getLogicValues());
	    	mrForm.setSelectedSourceValRequired(new String[]{sourceVal.toString()});
    		mrForm.setSelectedTargetRequired(new String[]{targetVal.toString()});
			mrForm.setSeletedFunction("Require If");
    		mrForm.setErrorMessage(waRuleMetadataDT.getErrMsgTxt());
		} else if (HIDE.equalsIgnoreCase(waRuleMetadataDT.getRuleCd()) || 
				UNHIDE.equalsIgnoreCase(waRuleMetadataDT.getRuleCd())){
			
			Object[] oParamsHD = new Object[] {waRuleMetadataDT.getWaTemplateUid(),""};
			String sBeanJndiNameHD = JNDINames.PAGE_MANAGEMENT_PROXY_EJB;
			String sMethodHD = "getWaUiElementDTDropDown";
			Object obHD = CallProxyEJB.callProxyEJB(oParamsHD, sBeanJndiNameHD, sMethodHD, request.getSession());
			
			ArrayList<Object> targetCodeList = new ArrayList();
			if("Question".equalsIgnoreCase(waRuleMetadataDT.getTargetType())){
				RuleManagementActionUtil util = new RuleManagementActionUtil();
				try {
					targetCodeList = util.getTargetTypeValues(waRuleMetadataDT.getWaTemplateUid(),
							waRuleMetadataDT.getTargetType(),waRuleMetadataDT.getSourceQuestionIdentifierString(),
							"View",null, mrForm.getSeletedFunction(), request);
					mrForm.setTargetHideOrUnhide(targetCodeList);
				} catch (Exception e) {			
					e.printStackTrace();
				}
			}else{
				RuleManagementActionUtil util = new RuleManagementActionUtil();
				try {
					targetCodeList = util.getTargetTypeValues(waRuleMetadataDT.getWaTemplateUid(),waRuleMetadataDT.getTargetType(),
							waRuleMetadataDT.getSourceQuestionIdentifierString(),"View", null, mrForm.getSeletedFunction(), request);
					mrForm.setTargetHideOrUnhide(targetCodeList);		
				} catch (Exception e) {			
					e.printStackTrace();
				}
			}
			
			Iterator<Object> itHD = ((Collection)obHD).iterator();
			Map<String, Object> hashMap = new HashMap<String, Object>();
			while(itHD.hasNext()){
				WaUiMetadataSummaryDT SummaryDT = (WaUiMetadataSummaryDT)itHD.next();
				hashMap.put(SummaryDT.getQuestionIdentifier(), SummaryDT);
			}
			
			Iterator<Object> itTargetHD = targetCodeList.iterator();
			Map<String, Object> targetHashMap = new HashMap<String, Object>();
			while(itTargetHD.hasNext()){
				WaUiMetadataSummaryDT SummaryDT = (WaUiMetadataSummaryDT)itTargetHD.next();
				targetHashMap.put(SummaryDT.getQuestionIdentifier(), SummaryDT);
			}
			
			Map<String, Object> sourceValMap = new HashMap<String, Object>();
			
			StringBuffer source = new StringBuffer();
			String comparator = new String();
			StringBuffer sourceVal = new StringBuffer();
			StringBuffer targetVal = new StringBuffer();
	    	for(int i =0; mapRuleElementDT.size()>i;i++){
	    		RuleElementDT ruleElementDT =(RuleElementDT) mapRuleElementDT.get(new Integer(i));	 
	    		
	    		// Setting source value
	    		if(hashMap.get(ruleElementDT.getSourceQuestionIdentifier())!=null){	    			
	    			WaUiMetadataSummaryDT waUiMetadataSummaryDT = (WaUiMetadataSummaryDT)hashMap.get(ruleElementDT.getSourceQuestionIdentifier());
	    			source.append(waUiMetadataSummaryDT.getQuestionLabel()).append(" (");
	    			source.append(ruleElementDT.getSourceQuestionIdentifier()).append(")");
	    			
	    			Long codeSetGroupId = waUiMetadataSummaryDT.getCodeSetGroupId();
	    			
	    			CachedDropDownValues cdv = new CachedDropDownValues();	
	    			Collection<Object> sourceValHD = null;
	    			if (ruleElementDT.getSourceQuestionIdentifier().equals(PageConstants.CONDITION_CD)) 
	    				sourceValHD = CachedDropDowns.getConditionDropDownForTemplate(waRuleMetadataDT.getWaTemplateUid());
	    			else 
	    				sourceValHD = CachedDropDowns.getCodedValueForType(cdv.getTheCodeSetNm(codeSetGroupId));
	    			Iterator sourceValColl = sourceValHD.iterator();			
	    			while(sourceValColl.hasNext()){
	    				DropDownCodeDT dt = (DropDownCodeDT)sourceValColl.next();
	    				sourceValMap.put(dt.getKey() , dt);
	    			}
	    			Collection<String> collSourceVal = ruleElementDT.getSourceValues();
		    		Iterator<String> itSourceVal = collSourceVal.iterator();
		    		int size = collSourceVal.size();
		    		int increment = 0;
		    		while(itSourceVal.hasNext()){
		    			DropDownCodeDT dt = (DropDownCodeDT)sourceValMap.get(itSourceVal.next());
		    			if(dt!=null){
			    			sourceVal.append(dt.getValue());
			    			if(increment < (size-1)){
			    				sourceVal.append(" | ");
			    				increment++;
			    			}
		    			}
		    		}
	    		}
	    		// Setting target values
	    		if(ruleElementDT.getTargetQuestionIdentifierColl()!=null){
		    		Iterator<String> itTarget = ruleElementDT.getTargetQuestionIdentifierColl().iterator();
		    		int size = ruleElementDT.getTargetQuestionIdentifierColl().size();
		    		int increment =0;
		    		while(itTarget.hasNext()){
		    			String str =((String)itTarget.next()).trim();
		    			WaUiMetadataSummaryDT waUiMetadataSummaryDT = (WaUiMetadataSummaryDT)targetHashMap.get(str);
		    			if(waUiMetadataSummaryDT!=null){
		    				if(waUiMetadataSummaryDT.getQuestionLabel()!=null)
		    					targetVal.append(waUiMetadataSummaryDT.getQuestionLabel());
		    				else
		    					targetVal.append(waUiMetadataSummaryDT.getComponentName());
	    					targetVal.append(" (").append(waUiMetadataSummaryDT.getQuestionIdentifier()).append(")");
	    					if(increment < (size-1)){
	    						targetVal.append(" | ");
			    				increment++;
			    			}
		    			}
			    	}	 
	    		}
	    		comparator = ruleElementDT.getComparator(); //not used?
	    		
	    	}
	    	mrForm.setSelectedTargetTypeHD(waRuleMetadataDT.getTargetType());
	    	mrForm.setSelectedSourceHideOrUnhide(source.toString());
	    	mrForm.setSelectedLogicHideOrUnhide(waRuleMetadataDT.getLogicValues());
	    	mrForm.setSelectedSourceValHideOrUnhide(new String[]{sourceVal.toString()});
    		mrForm.setSelectedTargetHideOrUnhide(new String[]{targetVal.toString()});

    		mrForm.setErrorMessage(waRuleMetadataDT.getErrMsgTxt());
		 }//end of hide/unhide
			
        }catch(Exception e){
    		logger.error("Error in ManageRulesAction.viewBusinessRule"+ e.getMessage());
    		e.printStackTrace();
        	throw new ServletException("Error occurredin view business rule: "+e.getMessage(),e);
        }
        return mapping.findForward(ManageRulesAction.VIEW_BUSINESS_RULE);
    }

    /**

     */
    public ActionForward deleteBusinessRule(ActionMapping mapping, ActionForm form,
    		HttpServletRequest request, HttpServletResponse response) throws Exception
    {
    	logger.debug("Begin ManageRulesAction delete rule");
    	
    	String waRuleMetadataUidStr = (String)request.getParameter("waRuleMetadataUid");
    	Long waRuleMetadataUid = Long.valueOf(waRuleMetadataUidStr);    	
    	request.setAttribute("waRuleMetadataUid", waRuleMetadataUid);
    	ManageRulesForm mrForm = (ManageRulesForm)form;
    	try{
    		Object[] oParams = new Object[] { waRuleMetadataUid };
    		String sBeanJndiName = JNDINames.PAGE_MANAGEMENT_PROXY_EJB;
			String sMethod = "deleteRuleMetadata";
    		CallProxyEJB.callProxyEJB(oParams, sBeanJndiName, sMethod, request.getSession());
    		Map<Object, Object> mapRules = mrForm.getSearchCriteriaArrayMap();
   			
    		mrForm.clearAll();
   			Long waTemplateUid = null;    	
   			
   			mrForm.setSearchCriteriaArrayMap(mapRules);
			request.setAttribute("ActionMode", HTMLEncoder.encodeHtml("Search"));
			
   	    	Collection<Object> pageList = new ArrayList<Object>();
   	    	waTemplateUid = (Long)request.getSession().getAttribute("waTemplateUid");
    		oParams = new Object[] {waTemplateUid };
    		sBeanJndiName = JNDINames.PAGE_MANAGEMENT_PROXY_EJB;
			sMethod = "getWaRuleSummaryDTCollection";
    		Object ob = CallProxyEJB.callProxyEJB(oParams, sBeanJndiName, sMethod, request.getSession());
    		pageList = (Collection<Object>)ob;    		
    		Map hashMap = getDateAndCodeMap(waTemplateUid,request);
    		
    		Map targetHashMap = getDateAndCodeMap(waTemplateUid,request);
    		
    		RuleManagementActionUtil.updateConditionLinks(pageList,hashMap,targetHashMap);
    		mrForm.setWaUiRuleSummaryDTColl(pageList);
    		
    		mrForm.getAttributeMap().put("queueCount", String.valueOf(pageList.size()));
    		Integer queueSize = properties.getQueueSize(NEDSSConstants.PAGE_MANAGEMENT_PAGE_LIBRARY_QUEUE_SIZE);
    		
    		
    		Collection<Object> coll = this.convertSummaryDtToDropDownDT(mrForm.getTargetFields());
    		mrForm.setTargetFields(coll);
    		Collection<Object> sortedList = RuleManagementActionUtil.filterPageLibrary(mrForm,request);
    		if(sortedList != null){
    			pageList = sortedList;
    			boolean existing = request.getParameter("existing") == null ? false : true;
    			
    			Map<Object, Object> searchCriteriaColl = (Map<Object, Object>)mrForm.getAttributeMap().get("searchCriteria");
    			
    			RuleManagementActionUtil.sortRulesLibarary(mrForm,pageList,existing, request);
    			 mrForm.getAttributeMap().put("searchCriteria", searchCriteriaColl);
    		}
    		
    		mrForm.getAttributeMap().put("queueSize", queueSize);
    		request.setAttribute("manageFormList", pageList);
    		request.setAttribute("messageInd", "DELETE");
    		
    	}catch(Exception e){
    		logger.error("Error in ManageRulesAction.deleteBusinessRule"+ e.getMessage());
    		e.printStackTrace();
    		throw new ServletException("Error occurred in Delete business rule: "+e.getMessage(),e);
    	}    
    	mrForm.getAttributeMap().get("searchCriteria");
    	
    	 if(request.getAttribute("queueCount")==null){
			Collection<Object> pageList2 = (Collection<Object>)request.getAttribute("manageFormList");
		 	request.setAttribute("queueCount", pageList2.size());
		 	mrForm.getAttributeMap().put("queueCount", pageList2.size());
		}
    	 
    	 
    	//return viewRulesList(mapping, mrForm, request, response);
        return PaginationUtil.paginate(mrForm, request, ManageRulesAction.VIEW_RULES_LIST,mapping);
    }

   
    
    /**

     */
    @SuppressWarnings("unchecked")
	public ActionForward viewRulesList(ActionMapping mapping, ActionForm form,
    		HttpServletRequest request, HttpServletResponse response) throws Exception
    {
      logger.debug("Begin ManageRulesAction View Rules List");
      try {
    	ManageRulesForm mrForm = (ManageRulesForm)form;
    	Long waTemplateUid = null;    	 
    	Collection<Object> pageList = new ArrayList<Object>();
    	waTemplateUid = (Long)request.getSession().getAttribute("waTemplateUid");
     	mrForm.setWaTemplateUid(waTemplateUid);	
    	HashMap<Object, Object> ccMap = (HashMap<Object, Object>)request.getSession().getAttribute("ccMap");
    	String codeDesc = (String)request.getSession().getAttribute("pgPageName");
    	request.setAttribute("codeDesc", HTMLEncoder.encodeHtml(codeDesc));    	
    	//request.setAttribute("ActionMode", "InitLoad");
    	boolean initLoad = request.getParameter("initLoad") == null ? false: Boolean.valueOf(request.getParameter("initLoad")).booleanValue();
    	if(initLoad){
    		try{
    	    	if(request.getParameterMap()!=null && request.getParameterMap().size() >0){
    	    		request.getParameterMap().clear(); 	    	
    	    		String[] strInitLoad = new String[] {String.valueOf(initLoad)};
    	    		 request.getParameterMap().put("initLoad", strInitLoad);
    	    	}
    	    	}catch(Exception e){
    	    		logger.debug(e.toString());
    	    }
    		
    		
    	}
    	boolean callEJB = false;
    	String context = request.getParameter("context") == null ? null: (String)request.getParameter("context");
    	boolean existing = request.getParameter("existing") == null ? false : true;

    	try{
    		
    		if(initLoad && !PaginationUtil._dtagAccessed(request)){    			
    			request.setAttribute("ActionMode", HTMLEncoder.encodeHtml("InitLoad"));
    			callEJB = true;
    			Map<Object, Object> mapRules = mrForm.getSearchCriteriaArrayMap();
    			mrForm.clearAll();
    			if(context!=null && (context.equalsIgnoreCase("ReturnToPage")|| context.equalsIgnoreCase("Cancel"))){
    				mrForm.setSearchCriteriaArrayMap(mapRules);
    				request.setAttribute("ActionMode", HTMLEncoder.encodeHtml("Search"));
    			}
    		}
    		
	    	if(context != null){
	    		callEJB = true;
    	    }
    		if(callEJB){
    			
    			
    			
	    		Object[] oParams = new Object[] {waTemplateUid };
	    		String sBeanJndiName = JNDINames.PAGE_MANAGEMENT_PROXY_EJB;
				String sMethod = "getWaRuleSummaryDTCollection";
	    		Object ob = CallProxyEJB.callProxyEJB(oParams, sBeanJndiName, sMethod, request.getSession());
	    		pageList = (Collection<Object>)ob;
	    		Map hashMap = getDateAndCodeMap(waTemplateUid,request); 
	    	    
	    		RuleManagementActionUtil util = new RuleManagementActionUtil();				
	    		ArrayList targetCodeList = util.getTargetTypeValues(waTemplateUid, "Subsection","","View",null, mrForm.getSeletedFunction(), request);
	    		Iterator<Object> itTargetED = targetCodeList.iterator();
				Map<Object, Object> targetHashMap = new HashMap<Object, Object>();
				while(itTargetED.hasNext()){
					WaUiMetadataSummaryDT SummaryDT = (WaUiMetadataSummaryDT)itTargetED.next();
					targetHashMap.put(SummaryDT.getQuestionIdentifier(), SummaryDT);
				}
	    		
	    		RuleManagementActionUtil.updateConditionLinks(pageList,hashMap,targetHashMap);
	    		mrForm.setWaUiRuleSummaryDTColl(pageList);	    		
    		}
    		mrForm.getAttributeMap().put("queueCount", String.valueOf(pageList.size()));
        	Integer queueSize = properties.getQueueSize(NEDSSConstants.PAGE_MANAGEMENT_PAGE_LIBRARY_QUEUE_SIZE);
        	mrForm.getAttributeMap().put("queueSize", queueSize);
    		
    		mrForm.initializeDropDowns();    		
    		mrForm.getAttributeMap().put("function",new Integer(mrForm.getFunction().size()));
    		mrForm.getAttributeMap().put("sourceFields",new Integer(mrForm.getSourceFields().size()));
    		mrForm.getAttributeMap().put("sourceValues",new Integer(mrForm.getSourceValues().size()));
    		mrForm.getAttributeMap().put("logicValues",new Integer(mrForm.getLogicValues().size()));
    		mrForm.getAttributeMap().put("targetFields",new Integer(mrForm.getTargetFields().size()));
    		
    	
    		Collection sortedList = RuleManagementActionUtil.filterPageLibrary(mrForm,request);
    		
        	if(sortedList != null){
        		pageList = sortedList;
        		
        		RuleManagementActionUtil.sortRulesLibarary(mrForm,pageList,existing, request);
        	}
        	request.setAttribute("manageFormList", pageList);
    	}catch(Exception e){
    		logger.warn("Exception occurred - continue " + e);
    		throw new ServletException("Error while View business rule list: "+e.getMessage(),e);
    	}
    	mrForm.getAttributeMap().put("queueCount", String.valueOf(pageList.size()));
    	PageManagementProxyVO vo = getTemplateType(waTemplateUid,request);
    	String templateType = vo.getWaTemplateDT().getTemplateType();
		mrForm.setTemplateType(templateType);
		request.setAttribute("templateType", templateType);
		if(codeDesc!=null && codeDesc.length()==0){
			codeDesc = vo.getWaTemplateDT().getTemplateNm();
			request.setAttribute("codeDesc", HTMLEncoder.encodeHtml(codeDesc));
		}
		if("TEMPLATE".equalsIgnoreCase(templateType)){
			request.setAttribute("PageTitle", "Manage Template: " + codeDesc + " Template Rules");
		}else{
			request.setAttribute("PageTitle", "Manage Page: " + codeDesc + " Page Rules");
		}

		
		
   	}catch (Exception e) {
  		logger.error("Exception in viewRulesList: " + e.getMessage());
  		e.printStackTrace();
  		throw new ServletException("Error occurred in viewRulesList : "+e.getMessage());
  	}  
		
	 	
        return mapping.findForward(ManageRulesAction.VIEW_RULES_LIST);
    }


	
	public ActionForward filterPageLibrarySubmit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {
		ManageRulesForm manageForm = (ManageRulesForm) form;
		try{			
			Collection<Object>  waUiRuleSummaryDTColl = RuleManagementActionUtil.filterPageLibrary(manageForm, request);		
			
			if(waUiRuleSummaryDTColl != null){
				request.setAttribute("ActionMode", HTMLEncoder.encodeHtml("Search"));
			//When all filters are applied (equivalent to no filter applied), Collection<Object>  is set to null in which case retrieve it from Form.
			}else{
				waUiRuleSummaryDTColl = (ArrayList<Object> ) manageForm.getWaUiRuleSummaryDTColl();
			}
			
			RuleManagementActionUtil.sortRulesLibarary(manageForm, waUiRuleSummaryDTColl, true, request);
			request.setAttribute("manageFormList", waUiRuleSummaryDTColl);		
			if(waUiRuleSummaryDTColl!= null){
				manageForm.getAttributeMap().put("queueCount", String.valueOf(waUiRuleSummaryDTColl.size()));
				request.setAttribute("queueCount", String.valueOf(waUiRuleSummaryDTColl.size()));
			}else{
				manageForm.getAttributeMap().put("queueCount", String.valueOf(0));
				request.setAttribute("queueCount", "0");
			}		
			manageForm.getAttributeMap().put("PageNumber", "1");
			manageForm.getAttributeMap().put("queueCount", String.valueOf(waUiRuleSummaryDTColl.size()));
			request.setAttribute("templateType", manageForm.getTemplateType());
		
		}catch(Exception e){
			throw new ServletException("Error while Filter business rule: "+e.getMessage(),e);
		}		
		return PaginationUtil.paginate(manageForm, request, ManageRulesAction.VIEW_RULES_LIST,mapping);
	}
	
	private String getSeletedLogicCompartor(String comprator) {
		if(">".equalsIgnoreCase(comprator.trim()))
			return "&gt;";
		if("<".equalsIgnoreCase(comprator.trim()))
			return "&lt;";		
		return comprator.trim();
	}
	
	private String[] convertStringToArray(String sourceVal) {
		String[] sourceValArray = sourceVal.split(",");
		return sourceValArray;
	}
	
	private String convertArrayToString(String[] sourceVal, Map hashMap, Map hashMapTarget) {
		StringBuffer sb = new StringBuffer();
		boolean flag = false;
		for( String s: sourceVal){
			if(hashMap.get(s) != null){
				if(flag){
					sb.append(", ");
				}
				WaUiMetadataSummaryDT SummaryDT = (WaUiMetadataSummaryDT)hashMapTarget.get(s);
				if (SummaryDT == null)
					sb.append(s);
				else
					sb.append(SummaryDT.getQuestionLabel()).append(" (").append(s).append(")");
				flag = true;
			}
			
		}
		return sb.toString();
	}
	
	private Map<String, WaUiMetadataSummaryDT> getDateAndCodeMap(Long waTemplateUid,HttpServletRequest request)
	throws ServletException{
		Map<String, WaUiMetadataSummaryDT> hashMap = new HashMap<String, WaUiMetadataSummaryDT>();
		try{
			Object[] oParamsED = new Object[] {waTemplateUid,""};
			String sBeanJndiNameED = JNDINames.PAGE_MANAGEMENT_PROXY_EJB;
			String sMethodED = "getWaUiElementDTDropDown";
			Object obED = CallProxyEJB.callProxyEJB(oParamsED, sBeanJndiNameED, sMethodED, request.getSession());
			
			Iterator<Object> itED = ((Collection)obED).iterator();
			
			while(itED.hasNext()){
				WaUiMetadataSummaryDT SummaryDT = (WaUiMetadataSummaryDT)itED.next();
				hashMap.put(SummaryDT.getQuestionIdentifier(), SummaryDT);
			}
			
		}catch(Exception e){
			throw new ServletException("Error while getting business rule: "+e.getMessage(),e);
		}
		return hashMap;
	}
	
	private PageManagementProxyVO getTemplateType(Long waTemplateUid, HttpServletRequest request){
		//String returnString=null;
		PageManagementProxyVO vo = new PageManagementProxyVO();
		try{
			Object[] oParams = new Object[] {waTemplateUid,true };
			String sBeanJndiName = JNDINames.PAGE_MANAGEMENT_PROXY_EJB;
			String sMethod = "getPageDraft";
			Object ob = CallProxyEJB.callProxyEJB(oParams, sBeanJndiName, sMethod, request.getSession());
			vo = (PageManagementProxyVO)ob;
			//returnString = vo.getWaTemplateDT().getTemplateType();
		}catch(Exception e){
			
		}
		return vo;
	}
	
	private Collection<Object> convertSummaryDtToDropDownDT(Collection<Object> inColl){
		Collection<Object> coll = new ArrayList<Object>();
		DropDownCodeDT cdDT = null;
		
		Iterator<Object> iIter = inColl.iterator();
		while (iIter.hasNext()) {
			Object obj = iIter.next();
			if(obj instanceof  WaUiMetadataSummaryDT ){
				WaUiMetadataSummaryDT summaryDt = (WaUiMetadataSummaryDT)obj;
				cdDT = new DropDownCodeDT();
				cdDT.setKey(summaryDt.getQuestionIdentifier());
				cdDT.setValue(summaryDt.getQuestionLabelIdentifier());
				coll.add(cdDT);
			}
			
		}
		
		return  coll;
	}
	
	public ArrayList<String> getPreviousSeletedTargets(String[] seletedTarget, Long waTemplateUid, String mode, String ruleId, String ruleCode, HttpServletRequest request){
		Object[] oParamsED = new Object[] {waTemplateUid, mode, ruleId, ruleCode};
		String sBeanJndiNameED = JNDINames.PAGE_MANAGEMENT_PROXY_EJB;
		String sMethodED = "getPreviousSeletedTargets";
		Object obED = CallProxyEJB.callProxyEJB(oParamsED, sBeanJndiNameED, sMethodED, request.getSession());		
		ArrayList<String> obColl = (ArrayList<String>)obED;		
		return obColl;
	}
}
