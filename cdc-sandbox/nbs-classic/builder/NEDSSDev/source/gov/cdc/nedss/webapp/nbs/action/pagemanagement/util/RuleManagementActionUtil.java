package gov.cdc.nedss.webapp.nbs.action.pagemanagement.util;

import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.bean.PageManagementProxyHome;
import gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.wa.dt.WaRuleSummaryDT;
import gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.wa.dt.WaUiMetadataSummaryDT;
import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.util.HTMLEncoder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.webapp.nbs.action.util.CallProxyEJB;
import gov.cdc.nedss.webapp.nbs.action.util.PaginationUtil;
import gov.cdc.nedss.webapp.nbs.action.util.QueueUtil;
import gov.cdc.nedss.webapp.nbs.form.pagemanagement.managerules.ManageRulesForm;




import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;




public class RuleManagementActionUtil
{
	static final LogUtils logger = new LogUtils(RuleManagementActionUtil.class.getName());
	//The action class changes the function i.e. from DATECOMPARE to Date Compare
	private static final String DATE_COMPARE ="Date Compare";
	private static final String REQUIRE_IF ="Require If";
	private static final String ENABLE ="Enable";
	private static final String DISABLE ="Disable";
	private static final String HIDE ="Hide";
	private static final String UNHIDE ="Unhide";	



	QueueUtil queueUtil = new QueueUtil();
	public static void updateConditionLinks(Collection<Object> coll, Map<Object,Object> hashMap, Map<Object,Object> targetHashMap) {
		if (coll != null ) {
			try {
			Iterator<Object> it = coll.iterator();
			while(it.hasNext()){
				WaRuleSummaryDT dt = (WaRuleSummaryDT) it.next();
				Long uid= dt.getWaRuleMetadataUid();
				dt.setSourceField(setFields(dt.getSourceField(),hashMap,""));
				if(dt.getTargetType()!=null && "Subsection".equalsIgnoreCase(dt.getTargetType())){
					dt.setTargetFieldForPrint(setFields(dt.getTargetField(),targetHashMap,","));
					dt.setTargetField(setFields(dt.getTargetField(),targetHashMap,"<br/>"));
				}else{
					dt.setTargetFieldForPrint(setFields(dt.getTargetField(),hashMap,","));
					dt.setTargetField(setFields(dt.getTargetField(),hashMap,"<br/>"));
				}				
				dt.setViewLink("View");
	    		dt.setEditLink("Edit");
				String viewUrl = "<a href=\"javascript:viewBusinessRule('" + uid + "');\"><img src=\"page_white_text.gif\" align=\"middle\" cellspacing=\"2\" cellpadding=\"3\" border=\"55\" title=\"View\" alt=\"View\"/></a>";
				dt.setViewLink(viewUrl);
				String editUrl = "<a href=\"javascript:editBusinessRule('" + uid + "');\"><img src=\"page_white_edit.gif\" align=\"middle\" cellspacing=\"2\" cellpadding=\"3\" border=\"55\" title=\"Edit\" alt=\"Edit\"/></a>";
				dt.setEditLink(editUrl);
			}
			} catch (Exception ex) {
				logger.error("Error in RuleManagementActionUtil.updateConditionLinks:: "+ex.getMessage());
				ex.printStackTrace();
			}

		}
	}

	public static String setFields(String targetField, Map<Object,Object> hashMap,String delimitor){
		StringTokenizer st = new StringTokenizer(targetField,",");
		StringBuffer sb = new StringBuffer();
		boolean flag =false;
		while (st.hasMoreTokens()) {
			if(flag){
				sb.append(delimitor);
			}
			WaUiMetadataSummaryDT SummaryDT = (WaUiMetadataSummaryDT)hashMap.get(st.nextToken().trim());
			if (SummaryDT != null)
				sb
				.append(SummaryDT
						.getQuestionLabelIdentifier());
			else
				sb.append("Invalid Data (" + targetField + ")"); //Field deleted from Page??
			flag=true;
	     }
		return sb.toString();
	}

	public static Collection<DropDownCodeDT>  getFunction(){

		DropDownCodeDT dt1 = new DropDownCodeDT();
		dt1.setKey("Date Compare"); dt1.setValue("Date Compare");
		DropDownCodeDT dt2 = new DropDownCodeDT();
		dt2.setKey("Enable"); dt2.setValue("Enable");
		DropDownCodeDT dt3 = new DropDownCodeDT();
		dt3.setKey("Disable"); dt3.setValue("Disable");
		DropDownCodeDT dt4 = new DropDownCodeDT();
		dt4.setKey("Require If"); dt4.setValue("Require If");		
		Collection<DropDownCodeDT> collFunction = new ArrayList<DropDownCodeDT>();
		collFunction.add(dt1);
		collFunction.add(dt2);
		collFunction.add(dt3);
		collFunction.add(dt4);
		return collFunction;
	}
	public static Collection<Object>  filterFunction(
			Collection<Object>  waUiRuleSummaryDTColl, Map<Object,Object> typeMap) throws Exception {
		Collection<Object>  newTypeColl = new ArrayList<Object> ();
		try{
		if (waUiRuleSummaryDTColl != null) {
			Iterator<Object> iter = waUiRuleSummaryDTColl.iterator();
			while (iter.hasNext()) {
				WaRuleSummaryDT dt = (WaRuleSummaryDT) iter.next();
				String functionNm = dt.getRuleCd().toUpperCase(); // this is javascript function
				functionNm=(functionNm.equals("DATE COMPARE")?"DATECOMPARE":functionNm);
				functionNm=(functionNm.equals("REQUIRE IF")?"REQUIREIF":functionNm);
				if (functionNm!= null
						&& typeMap != null
						&& typeMap.containsKey(functionNm)) {
					newTypeColl.add(dt);
				}
				if(functionNm == null || functionNm.equals("")){
					if(typeMap != null && typeMap.containsKey(NEDSSConstants.BLANK_KEY)){
						newTypeColl.add(dt);
					}
				}
			}
		}
		}catch(Exception ex){
			 logger.error("Error in accessing the filterFunction : "+ex.getMessage());
			 throw new NEDSSSystemException(ex.getMessage());
		}
		return newTypeColl;
	}


	public ArrayList<Object> getSourceFields(Collection<Object>  waUiRuleSummaryDTColl)throws Exception {
		Map<Object, Object> typeMap = new HashMap<Object,Object>();
		if (waUiRuleSummaryDTColl != null) {
			java.util.Iterator<?> iter = waUiRuleSummaryDTColl.iterator();
			while (iter.hasNext()) {
				WaRuleSummaryDT dt = (WaRuleSummaryDT) iter.next();
				if (dt.getSourceField()!= null) {
					typeMap.put(dt.getSourceField(), dt.getSourceField());
				}
				if(dt.getSourceField() == null || dt.getSourceField().trim().equals("")){
					typeMap.put(NEDSSConstants.BLANK_KEY, NEDSSConstants.BLANK_VALUE);
				}
			}
		}
		return queueUtil.getUniqueValueFromMap(typeMap);
	}


	
	public static Collection<Object>  filterSourceFields(
			Collection<Object>  waUiRuleSummaryDTColl, Map<Object,Object> typeMap) throws Exception {
		Collection<Object>  newTypeColl = new ArrayList<Object> ();
		try{
		if (waUiRuleSummaryDTColl != null) {
			Iterator<Object> iter = waUiRuleSummaryDTColl.iterator();
			while (iter.hasNext()) {
				WaRuleSummaryDT dt = (WaRuleSummaryDT) iter.next();
				String sourceField = dt.getSourceField();
				if (sourceField!= null
						&& typeMap != null
						&& typeMap.containsKey(sourceField)) {
					newTypeColl.add(dt);
				}
				if(sourceField == null || sourceField.equals("")){
					if(typeMap != null && typeMap.containsKey(NEDSSConstants.BLANK_KEY)){
						newTypeColl.add(dt);
					}
				}
			}
		}
		}catch(Exception ex){
			 logger.error("Error in accessing the filterSourceFields : "+ex.getMessage());
			 throw new NEDSSSystemException(ex.getMessage());
		}
		return newTypeColl;
	}

	public ArrayList<Object> getLogicValues(Collection<Object>  waUiRuleSummaryDTColl)throws Exception {
		Map<Object, Object> typeMap = new HashMap<Object,Object>();
		if (waUiRuleSummaryDTColl != null) {
			java.util.Iterator<?> iter = waUiRuleSummaryDTColl.iterator();
			while (iter.hasNext()) {
				WaRuleSummaryDT dt = (WaRuleSummaryDT) iter.next();
				if (dt.getLogicValues()!= null) {
					typeMap.put(dt.getLogicValues(), dt.getLogicValues());

				}

				if(dt.getLogicValues() == null || dt.getLogicValues().trim().equals("")){
					typeMap.put(NEDSSConstants.BLANK_KEY, NEDSSConstants.BLANK_VALUE);
				}
			}
		}
		return queueUtil.getUniqueValueFromMap(typeMap);
	}

	public static Collection<Object>  filterLogicValues(
			Collection<Object>  waUiRuleSummaryDTColl, Map<Object,Object> typeMap) throws Exception {
		Collection<Object>  newTypeColl = new ArrayList<Object> ();
		try{
		if (waUiRuleSummaryDTColl != null) {
			Iterator<Object> iter = waUiRuleSummaryDTColl.iterator();
			while (iter.hasNext()) {
				WaRuleSummaryDT dt = (WaRuleSummaryDT) iter.next();
				String logicValue = dt.getLogicValues();
				if (logicValue!= null
						&& typeMap != null
						&& typeMap.containsKey(logicValue)) {
					newTypeColl.add(dt);
				}
				if(logicValue == null || logicValue.equals("")){
					if(typeMap != null && typeMap.containsKey(NEDSSConstants.BLANK_KEY)){
						newTypeColl.add(dt);
					}
				}
			}
		}
		}catch(Exception ex){
			 logger.error("Error in accessing the filterLogicValues : "+ex.getMessage());
			 throw new NEDSSSystemException(ex.getMessage());
		}
		return newTypeColl;
	}

	public ArrayList<Object> getSourceValues(Collection<Object>  waUiRuleSummaryDTColl)throws Exception {
		Map<Object, Object> typeMap = new HashMap<Object,Object>();
		if (waUiRuleSummaryDTColl != null) {
			java.util.Iterator<?> iter = waUiRuleSummaryDTColl.iterator();
			while (iter.hasNext()) {
				WaRuleSummaryDT dt = (WaRuleSummaryDT) iter.next();
				if (dt.getSourceValues()!= null) {
					this.setTypeMap(dt.getSourceValues().trim(),typeMap);
				}
				if(dt.getSourceValues() == null || dt.getSourceValues().trim().equals("")){
					typeMap.put(NEDSSConstants.BLANK_KEY, NEDSSConstants.BLANK_VALUE);
				}
			}
		}
		return queueUtil.getUniqueValueFromMap(typeMap);
	}

	public static Collection<Object>  filterSourceValues(
			Collection<Object>  waUiRuleSummaryDTColl, Map<Object,Object> typeMap) throws Exception {
		Collection<Object>  newTypeColl = new ArrayList<Object> ();
		try{
		if (waUiRuleSummaryDTColl != null) {
			Iterator<Object> iter = waUiRuleSummaryDTColl.iterator();
			while (iter.hasNext()) {
				WaRuleSummaryDT dt = (WaRuleSummaryDT) iter.next();
				String sourceValue = dt.getSourceValues();
				if (sourceValue!= null
						&& typeMap != null) {
					StringTokenizer st = new StringTokenizer(sourceValue,",");
					boolean added = false;
					while (st.hasMoreTokens() && !added) {
						String token = st.nextToken();
						if(typeMap.containsKey(token) || typeMap.containsKey(token.trim())){
							added=true;
							newTypeColl.add(dt);
						}
				     }
				}
				if(sourceValue == null || sourceValue.equals("")){
					if(typeMap != null && typeMap.containsKey(NEDSSConstants.BLANK_KEY)){
						newTypeColl.add(dt);
					}
				}
			}
		}
		}catch(Exception ex){
			 logger.error("Error in accessing the filterSourceValues : "+ex.getMessage());
			 throw new NEDSSSystemException(ex.getMessage());
		}
		return newTypeColl;
	}

	public ArrayList<Object> getTargetFields(Collection<Object>  waUiRuleSummaryDTColl)throws Exception {
		Map<Object, Object> typeMap = new HashMap<Object,Object>();
		if (waUiRuleSummaryDTColl != null) {
			java.util.Iterator<?> iter = waUiRuleSummaryDTColl.iterator();
			while (iter.hasNext()) {
				WaRuleSummaryDT dt = (WaRuleSummaryDT) iter.next();
				if (dt.getTargetField() != null) {
					this.setTypeMap(dt.getTargetField(),typeMap);
				}
				if(dt.getTargetField() == null || dt.getTargetField().trim().equals("")){
					typeMap.put(NEDSSConstants.BLANK_KEY, NEDSSConstants.BLANK_VALUE);
				}
			}
		}
		return queueUtil.getUniqueValueFromMap(typeMap);
	}

	public static Collection<Object>  filterTargetField(
			Collection<Object>  waUiRuleSummaryDTColl, Map<Object,Object> typeMap) throws Exception {
		Collection<Object>  newTypeColl = new ArrayList<Object> ();
		try{
		if (waUiRuleSummaryDTColl != null) {
			Iterator<Object> iter = waUiRuleSummaryDTColl.iterator();
			while (iter.hasNext()) {
				WaRuleSummaryDT dt = (WaRuleSummaryDT) iter.next();
				String targetField = dt.getTargetField();
				if (targetField!= null
						&& typeMap != null) {
					StringTokenizer st = new StringTokenizer(targetField,",");
					while (st.hasMoreTokens()) {
						if(typeMap.containsKey(st.nextToken())){
							newTypeColl.add(dt);
						}
				     }
				}
				if(targetField == null || targetField.equals("")){
					if(typeMap != null && typeMap.containsKey(NEDSSConstants.BLANK_KEY)){
						newTypeColl.add(dt);
					}
				}
			}
		}
		}catch(Exception ex){
			 logger.error("Error in accessing the filterTargetField : "+ex.getMessage());
			 throw new NEDSSSystemException(ex.getMessage());
		}
		return newTypeColl;
	}

	private void setTypeMap(String str, Map<Object,Object> typeMap ){

		StringTokenizer st = new StringTokenizer(str,",");
		while (st.hasMoreTokens()) {
			String strValue = st.nextToken().trim();
			typeMap.put(strValue,strValue);
	     }
	}
	/*
	 *  This Rule Expression was never used for anything.
	 */
	public static String getRulesExpression(ManageRulesForm mrForm) {

		//" TUB101 < ^ D ( VAR100 )" this is date compare
		// NBS_1_194 ( II,   III ) &&  NBS_1_195 ( II,   III ) <^ E  ( NBS_1_55  , NBS_1_40  , NBS_1_79  ) this source multi selection.
		// but this release code support single source selection "&&" will not added

		StringBuffer ruleExpre =new StringBuffer();
		try {
		if(DATE_COMPARE.contains(mrForm.getSeletedFunction())){

			ruleExpre.append(mrForm.getSeletedSourceDateComp()).append(" ");
			ruleExpre.append(mrForm.getSeletedLogicDateComp()).append(" ").append(" ^ DT ( ");
			// Store target values
			String[] targetED =mrForm.getSeletedTargetDateComp();
			boolean flag =false;
			for(String str: targetED){
				if(flag){
					ruleExpre.append(" , ");
				}
				ruleExpre.append(str);
				flag = true;
			}
			ruleExpre.append(" ) ");

		}else if(ENABLE.contains(mrForm.getSeletedFunction()) || DISABLE.contains(mrForm.getSeletedFunction())){
			String source =mrForm.getSeletedSourceDisOrEnabled();
			String comparator =mrForm.getSeletedLogDisOrEnabled();
			String[] sourceVal =mrForm.getSeletedSourceValDisOrEnabled();
			String[] targetED =mrForm.getSeletedTarDisOrEnabled();

			ruleExpre.append(source);
			ruleExpre.append(" ( ");
			boolean flag =false;
			for(String str: sourceVal){
				if(flag){
					ruleExpre.append(" , ");
				}
				ruleExpre.append(str);
				flag = true;
			}
			ruleExpre.append(" ) ");
			ruleExpre.append(comparator).append(" ");
			if(ENABLE.contains(mrForm.getSeletedFunction())){
				ruleExpre.append("^").append(" E");
			}else if(DISABLE.contains(mrForm.getSeletedFunction())){
				ruleExpre.append("^").append(" D");
			}
			// Store target values
			ruleExpre.append(" ( ");
			flag =false;
			for(String str: targetED){
				if(flag){
					ruleExpre.append(" , ");
				}
				ruleExpre.append(str);
				flag = true;
			}
			ruleExpre.append(" ) ");
		}else if(REQUIRE_IF.contains(mrForm.getSeletedFunction())){
			String source =mrForm.getSelectedSourceRequired();
			String comparator =mrForm.getSelectedLogicRequired();
			String[] sourceVal =mrForm.getSelectedSourceValRequired();
			String[] targetRQ =mrForm.getSelectedTargetRequired();

			ruleExpre.append(source);
			ruleExpre.append(" ( ");
			boolean flag =false;
			for(String str: sourceVal){
				if(flag){
					ruleExpre.append(" , ");
				}
				ruleExpre.append(str);
				flag = true;
			}
			ruleExpre.append(" ) ");
			ruleExpre.append(comparator).append(" ");
			ruleExpre.append("^").append(" R");

			// Store target values
			ruleExpre.append(" ( ");
			flag =false;
			for(String str: targetRQ){
				if(flag){
					ruleExpre.append(" , ");
				}
				ruleExpre.append(str);
				flag = true;
			}
			ruleExpre.append(" ) ");

		}else if(HIDE.contains(mrForm.getSeletedFunction()) || UNHIDE.contains(mrForm.getSeletedFunction())){
			String source =mrForm.getSelectedSourceHideOrUnhide();
			String comparator =mrForm.getSelectedLogicHideOrUnhide();
			String[] sourceVal =mrForm.getSelectedSourceValHideOrUnhide();
			String[] targetHD =mrForm.getSelectedTargetHideOrUnhide();

			ruleExpre.append(source);
			ruleExpre.append(" ( ");
			boolean flag =false;
			for(String str: sourceVal){
				if(flag){
					ruleExpre.append(" , ");
				}
				ruleExpre.append(str);
				flag = true;
			}
			ruleExpre.append(" ) ");
			ruleExpre.append(comparator).append(" ");
			if(UNHIDE.contains(mrForm.getSeletedFunction())){
				ruleExpre.append("^").append(" S");
			}else if(HIDE.contains(mrForm.getSeletedFunction())){
				ruleExpre.append("^").append(" H");
			}
			// Store target values
			ruleExpre.append(" ( ");
			flag =false;
			for(String str: targetHD){
				if(flag){
					ruleExpre.append(" , ");
				}
				ruleExpre.append(str);
				flag = true;
			}
			ruleExpre.append(" ) ");

		}
		} catch (Exception ex) {
			logger.warn("Error in getRulesExpression: "+ex.getMessage());
			ex.printStackTrace();
		}
		return ruleExpre.toString();
	}

	public static String getSortRulesLibrary(String sortOrder, String methodName)throws Exception{
		try{
		String sortOrdrStr = null;
		if(methodName != null) {
			if(methodName.equals("getRuleCd"))
				sortOrdrStr = "Function";
			else if(methodName.equals("getSourceField"))
				sortOrdrStr = "Source Field";
			else if(methodName.equals("getLogicValues"))
				sortOrdrStr = "Logic";
			else if(methodName.equals("getSourceValues"))
				sortOrdrStr = "Value(s)";
			else if(methodName.equals("getTargetField"))
				sortOrdrStr = "Target Field(s)";
			else if(methodName.equals("getWaRuleMetadataUid"))
				sortOrdrStr = "ID";
		} else {
				sortOrdrStr = "ID";
		}

		if(sortOrder == null || (sortOrder != null && sortOrder.equals("1")))
			sortOrdrStr = sortOrdrStr+" @ in ascending order ";
		else if(sortOrder != null && sortOrder.equals("2"))
			sortOrdrStr = sortOrdrStr+" @ in descending order ";

		return sortOrdrStr;
		}catch (Exception ex) {
			logger.fatal("Error in getSortPageLibrary in Action Util: ", ex);
			throw new Exception(ex.toString());

		}

	}



	public static Collection<Object>  filterPageLibrary(ManageRulesForm manageRulesForm, HttpServletRequest request) throws Exception {

		Collection<Object>  manageCondlist = new ArrayList<Object> ();


		String sortOrderParam = null;
		try {

			Map<Object, Object> searchCriteriaMap = manageRulesForm.getSearchCriteriaArrayMap();
			ArrayList<Object> waUiRuleSummaryDTColl = (ArrayList<Object> ) manageRulesForm.getWaUiRuleSummaryDTColl();

			manageCondlist = getFilteredCodesetLib(waUiRuleSummaryDTColl, searchCriteriaMap,manageRulesForm);
			//FUNCTION

			String[] function = (String[]) searchCriteriaMap.get("FUNCTION");
			String[] sourceFields = (String[]) searchCriteriaMap.get("SOURCEFIELDS");
			String[] sourceValues = (String[]) searchCriteriaMap.get("SOURCEVALUES");
			String[] logicValues = (String[]) searchCriteriaMap.get("LOGICVALUES");
			String[] targetFields = (String[]) searchCriteriaMap.get("TARGETFIELDS");

			Integer functionCount = new Integer(function == null ? 0 : function.length);
			Integer sourceFieldsCount = new Integer(sourceFields == null ? 0 : sourceFields.length);
			Integer sourceValuesCount = new Integer(sourceValues == null ? 0 : sourceValues.length);
			Integer logicValuesCount = new Integer(logicValues == null ? 0 : logicValues.length);
			Integer targetFieldsCount = new Integer(targetFields == null ? 0 : targetFields.length);

			// Do not filter if the selected values for filter is same as filtered list, but put the sortMethod, direction and criteria stuff
			if(functionCount.equals(manageRulesForm.getAttributeMap().get("function")) &&
					sourceFieldsCount.equals(manageRulesForm.getAttributeMap().get("sourceFields")) &&
					sourceValuesCount.equals(manageRulesForm.getAttributeMap().get("sourceValues")) &&
					logicValuesCount.equals(manageRulesForm.getAttributeMap().get("logicValues")) &&
					targetFieldsCount.equals(manageRulesForm.getAttributeMap().get("targetFields"))
			   ){

				String sortMethod = getSortMethod(request, manageRulesForm);
				String direction = getSortDirection(request, manageRulesForm);
				if(sortMethod == null || (sortMethod != null && sortMethod.equals("none"))) {
					Map<?,?> sColl =  manageRulesForm.getAttributeMap().get("searchCriteria") == null ?
							new TreeMap<Object, Object>() : (TreeMap<?,?>) manageRulesForm.getAttributeMap().get("searchCriteria");
					sortOrderParam = sColl.get("sortSt") == null ? "" : (String) sColl.get("sortSt");
				} else {
					sortOrderParam = RuleManagementActionUtil.getSortRulesLibrary(direction, sortMethod);
				}
				Map<Object, Object> searchCriteriaColl = new TreeMap<Object, Object>();
				searchCriteriaColl.put("sortSt", sortOrderParam);
				manageRulesForm.getAttributeMap().put("searchCriteria", searchCriteriaColl);
				return null;
			}

			Collection<Object> functionList = getListFromObject(manageRulesForm.getFunction());
			Collection<Object> sourceFieldList = manageRulesForm.getSourceFields();
			Collection<Object> sourceValueList = manageRulesForm.getSourceValues();
			Collection<Object> logicValueList = manageRulesForm.getLogicValues();
			Collection<Object> targetFieldList = manageRulesForm.getTargetFields();

			Map<Object, Object> searchCriteriaColl = new TreeMap<Object, Object>();
			String sortMethod = getSortMethod(request, manageRulesForm);
			String direction = getSortDirection(request, manageRulesForm);
			if(sortMethod == null || (sortMethod != null && sortMethod.equals("none"))) {
				Map<Object, Object> sColl =  manageRulesForm.getAttributeMap().get("searchCriteria") == null ? new TreeMap<Object, Object>() :(TreeMap<Object, Object>) manageRulesForm.getAttributeMap().get("searchCriteria");
				sortOrderParam = sColl.get("sortSt") == null ? "" : (String) sColl.get("sortSt");
			} else {
				sortOrderParam = RuleManagementActionUtil.getSortRulesLibrary(direction, sortMethod);
			}

			QueueUtil queueUtil = new QueueUtil();
			String srchCriteriaFunction = null;
			srchCriteriaFunction = queueUtil.getSearchCriteria((ArrayList<Object>)functionList, function, NEDSSConstants.FILTERBYDATE);

			String srchCriteriaSourceField = null;
			srchCriteriaSourceField = queueUtil.getSearchCriteria((ArrayList<Object>)sourceFieldList, sourceFields, NEDSSConstants.FILTERBYDATE);

			String srchCriteriaSourceValues = null;
			srchCriteriaSourceValues = queueUtil.getSearchCriteria((ArrayList<Object>)sourceValueList, sourceValues, NEDSSConstants.FILTERBYDATE);

			String srchCriteriaLogicValues = null;
			srchCriteriaLogicValues = queueUtil.getSearchCriteria((ArrayList<Object>)logicValueList, logicValues, NEDSSConstants.FILTERBYDATE);

			String srchCriteriaTargetField = null;
			srchCriteriaTargetField = queueUtil.getSearchCriteria((ArrayList<Object>)targetFieldList, targetFields, NEDSSConstants.FILTERBYDATE);

			//set the error message to the form
			if(sortOrderParam != null)
				searchCriteriaColl.put("sortSt", sortOrderParam);
			if(srchCriteriaFunction != null)
				searchCriteriaColl.put("INV111", srchCriteriaFunction);
			if(srchCriteriaSourceField != null)
				searchCriteriaColl.put("INV222", srchCriteriaSourceField);
			if(srchCriteriaSourceValues != null)
				searchCriteriaColl.put("INV333", srchCriteriaSourceValues);
			if(srchCriteriaLogicValues != null)
				searchCriteriaColl.put("INV444", srchCriteriaLogicValues);
			if(srchCriteriaTargetField != null)
				searchCriteriaColl.put("INV555", srchCriteriaTargetField);
			
			manageRulesForm.getAttributeMap().put("searchCriteria", searchCriteriaColl);
			//ND-10333
			if(!(searchCriteriaColl.size()>1))
				request.setAttribute("ActionMode", HTMLEncoder.encodeHtml("InitLoad"));
				
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while filtering the filterPageLib: "+ e.toString());
			throw new Exception(e.getMessage());

		}
		return manageCondlist;
	}

	public static Collection<Object>  getFilteredCodesetLib(Collection<Object>  waRuleMetadataDTColl,
			Map<Object, Object> searchCriteriaMap,ManageRulesForm manageForm) throws Exception{
		QueueUtil queueUtil = new QueueUtil();
      try{
	    	String[] function = (String[]) searchCriteriaMap.get("FUNCTION");
			String[] sourceFields = (String[]) searchCriteriaMap.get("SOURCEFIELDS");
			String[] sourceValues = (String[]) searchCriteriaMap.get("SOURCEVALUES");
			String[] logicValues = (String[]) searchCriteriaMap.get("LOGICVALUES");
			String[] targetFields = (String[]) searchCriteriaMap.get("TARGETFIELDS");

			Map<Object, Object> functionMap = new HashMap<Object,Object>();
			Map<Object, Object> sourceFieldsMap = new HashMap<Object,Object>();
			Map<Object, Object> sourceValuesMap = new HashMap<Object,Object>();
			Map<Object, Object> logicValuesMap = new HashMap<Object,Object>();
			Map<Object, Object> targetFieldsMap = new HashMap<Object,Object>();

	        try{
				if (function != null && function.length >0){
					functionMap = queueUtil.getMapFromStringArray(function);
				}
				/**
				 * Following methods are helping for page sorting
				 */
				if(function != null && functionMap != null && functionMap.size()>0){
					waRuleMetadataDTColl = RuleManagementActionUtil.filterFunction(waRuleMetadataDTColl, functionMap);
				}

				if (sourceFields != null && sourceFields.length >0){
					sourceFieldsMap = queueUtil.getMapFromStringArray(sourceFields);
				}
				/**
				 * Following methods are helping for page sorting
				 */
				if(sourceFields != null && sourceFieldsMap != null && sourceFieldsMap.size()>0){
					waRuleMetadataDTColl = RuleManagementActionUtil.filterSourceFields(waRuleMetadataDTColl, sourceFieldsMap);
				}

				if (sourceValues != null && sourceValues.length >0){
					sourceValuesMap = queueUtil.getMapFromStringArray(sourceValues);
				}
				/**
				 * Following methods are helping for page sorting
				 */
				if(sourceValues != null && sourceValuesMap != null && sourceValuesMap.size()>0){
					waRuleMetadataDTColl = RuleManagementActionUtil.filterSourceValues(waRuleMetadataDTColl, sourceValuesMap);
				}

				if (logicValues != null && logicValues.length >0){
					logicValuesMap = queueUtil.getMapFromStringArray(logicValues);
				}
				/**
				 * Following methods are helping for page sorting
				 */
				if(logicValues != null && logicValuesMap != null && logicValuesMap.size()>0){
					waRuleMetadataDTColl = RuleManagementActionUtil.filterLogicValues(waRuleMetadataDTColl, logicValuesMap);
				}

				if (targetFields != null && targetFields.length >0){
					targetFieldsMap = queueUtil.getMapFromStringArray(targetFields);
				}
				/**
				 * Following methods are helping for page sorting
				 */
				if(targetFields != null && targetFieldsMap != null && targetFieldsMap.size()>0){
					waRuleMetadataDTColl = RuleManagementActionUtil.filterTargetField(waRuleMetadataDTColl, targetFieldsMap);
				}

	        }catch (Exception e) {
				e.printStackTrace();
				logger.error("Error while filtering the getFilteredCodesetLib: "+ e.toString());
				throw new Exception(e.getMessage());
	        }
        }catch (Exception e) {
			e.printStackTrace();
			logger.error("Error occurred while filtering the getFilteredCodesetLib: "+ e.toString());
			throw new Exception(e.getMessage());
		}
		return waRuleMetadataDTColl;
	}
	private static String getSortMethod(HttpServletRequest request, ManageRulesForm manageForm ) {
		if (PaginationUtil._dtagAccessed(request)) {
			return request.getParameter((new ParamEncoder("parent")).encodeParameterName(TableTagParameters.PARAMETER_SORT));
	} else{
		return manageForm.getAttributeMap().get("methodName") == null ? null : (String) manageForm.getAttributeMap().get("methodName");
		}

	}

	private static String getSortDirection(HttpServletRequest request, ManageRulesForm manageForm) {
		if (PaginationUtil._dtagAccessed(request)) {
			return request.getParameter((new ParamEncoder("parent")).encodeParameterName(TableTagParameters.PARAMETER_ORDER));
		} else{
			return manageForm.getAttributeMap().get("sortOrder") == null ? "1": (String) manageForm.getAttributeMap().get("sortOrder");
		}

	}

	public static void sortRulesLibarary(ManageRulesForm manageForm, Collection<Object>  codesetList, boolean existing, HttpServletRequest request) throws Exception {

		// Retrieve sort-order and sort-direction from display tag parameters
		String sortMethod = getSortMethod(request, manageForm);
		String direction = getSortDirection(request, manageForm);
		
		boolean invDirectionFlag = true;
		if (direction != null && direction.equals("2"))
			invDirectionFlag = false;

		NedssUtils util = new NedssUtils();

		//Read from properties file to determine default sort order
		if (sortMethod == null || (sortMethod != null && sortMethod.equals("none"))) {
			sortMethod = "getRuleCd";
			invDirectionFlag = true;
		}

		if (sortMethod != null && codesetList != null && codesetList.size() > 0) {
			util.sortObjectByColumn(sortMethod,(Collection<Object>) codesetList, invDirectionFlag);
		}
		if(!existing) {
			//Finally put sort criteria in form
			String sortOrderParam = RuleManagementActionUtil.getSortRulesLibrary(invDirectionFlag == true ? "1" : "2", sortMethod);
			Map<Object, Object> searchCriteriaColl = new TreeMap<Object, Object>();
			searchCriteriaColl.put("sortSt", sortOrderParam);
			manageForm.getAttributeMap().put("searchCriteria", searchCriteriaColl);
		}
	}

	private static Collection<Object> getListFromObject(Collection<Object> list){
		Collection<Object> returnList = new ArrayList<Object>();
		Iterator<Object> it = list.iterator();
		while(it.hasNext()){
			DropDownCodeDT dropDownDt =(DropDownCodeDT)it.next();
			if(dropDownDt !=null && dropDownDt.getKey().length()>0){
				returnList.add(dropDownDt);
			}
		}

		return returnList;
	}

	public ArrayList<Object> getTargetTypeValues(Long waTemplateUid, String targetType,String source, String mode, String ruleId, String ruleCode, HttpServletRequest request )throws Exception {
		ArrayList<Object> targetED = new ArrayList();
		try {
		if("Question".equalsIgnoreCase(targetType)){
			Object[] oParamsED = new Object[] {waTemplateUid,source, mode, ruleId, ruleCode};
			String sBeanJndiNameED = JNDINames.PAGE_MANAGEMENT_PROXY_EJB;
			String sMethodED = "getUiMetaQuestionDropDown";
			Object obED = CallProxyEJB.callProxyEJB(oParamsED, sBeanJndiNameED, sMethodED, request.getSession());
			targetED=(ArrayList)obED;
		}else{
			Object[] oParamsED = new Object[] {waTemplateUid, source, mode, ruleId};
			String sBeanJndiNameED = JNDINames.PAGE_MANAGEMENT_PROXY_EJB;
			String sMethodED = "getUiMetaSubsectionDropDown";
			Object obED = CallProxyEJB.callProxyEJB(oParamsED, sBeanJndiNameED, sMethodED, request.getSession());
			targetED=(ArrayList)obED;
		}
		} catch (Exception ex) {
			logger.error("Error in getTargetTypeValues calling getUiMetaQuestionDropDown: "+ex.getMessage());
			ex.printStackTrace();
		}
		
		return targetED;
	}

	
}