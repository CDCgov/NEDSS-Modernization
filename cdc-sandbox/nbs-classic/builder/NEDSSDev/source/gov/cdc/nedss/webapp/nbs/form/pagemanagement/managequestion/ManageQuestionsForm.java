package gov.cdc.nedss.webapp.nbs.form.pagemanagement.managequestion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

import gov.cdc.nedss.pagemanagement.wa.dt.WaQuestionDT;
import gov.cdc.nedss.systemservice.uidgenerator.UidClassCodes;
import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.util.HTMLEncoder;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.PageManagementActionUtil;
import gov.cdc.nedss.webapp.nbs.form.util.BaseForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

public class ManageQuestionsForm extends BaseForm {

	private static final long serialVersionUID = 1L;
	PageManagementActionUtil pageManagementUtil = new PageManagementActionUtil();

	Object selection = new Object();
	Object selectionUnit = new Object();
	Object oldDT = new Object();
	private String actionMode;
	private String questionIndentifier;
	ArrayList<Object> displayControlList = new ArrayList<Object>();
	ArrayList<Object> defaultValueUnitColl = new ArrayList<Object>();
	Map<Object,Object> searchMap = new HashMap<Object,Object>();
	private String returnToLink;
	private String questionUnitIndentifier;
	ArrayList<Object> ErrorList = new ArrayList<Object>();
	private String importedQUIDList ="";
	//For Filtering/sorting
	Map<Object,Object> searchCriteriaArrayMap = new HashMap<Object,Object>();
	private ArrayList<Object> type = new ArrayList<Object> ();
	private ArrayList<Object> group = new ArrayList<Object> ();
	private ArrayList<Object> subGroup = new ArrayList<Object> ();
	private ArrayList<Object> status = new ArrayList<Object> ();
	

	private String defaultValue;
	
	
	public ArrayList<Object> getType() {
		return type;
	}

	public void setType(ArrayList<Object> type) {
		this.type = type;
	}

	public ArrayList<Object> getGroup() {
		return group;
	}

	public void setGroup(ArrayList<Object> group) {
		this.group = group;
	}

	public ArrayList<Object> getSubGroup() {
		return subGroup;
	}

	public void setSubGroup(ArrayList<Object> subGroup) {
		this.subGroup = subGroup;
	}

	public ArrayList<Object> getStatus() {
		return status;
	}

	public void setStatus(ArrayList<Object> status) {
		this.status = status;
	}


	public Object getOldDT() {
		return oldDT;
	}

	public void setOldDT(Object oldDT) {
		this.oldDT = oldDT;
	}

	public String getActionMode() {
		return actionMode;
	}

	public void setActionMode(String actionMode) {
		this.actionMode = actionMode;
	}

	public Map<Object, Object> getAttributeMap() {
		return attributeMap;
	}

	public void setAttributeMap(Map<Object, Object> attributeMap) {
		this.attributeMap = attributeMap;
	}
	private Map<Object,Object> attributeMap = new HashMap<Object,Object>();
	
	public Object getSelection() {
		return selection;
	}

	public void setSelection(Object selection) {
		this.selection = selection;
		
		// set other supporting properties here //
		WaQuestionDT qDt = (WaQuestionDT) this.selection;
		
		// default codes for the value set selected
		if (qDt.getCodeSetGroupId() != null) {
		    ArrayList<Object> codeList = 
		        getDefaultValuesForValueSet(qDt.getCodeSetGroupId().toString());
		    setDefaultValueUnitColl(codeList);
		}
		
		// default display controls for the selected data type
		if (qDt.getDataType() != null) {
		    // this call also sets the displayControlList property
            getDefaultDisplayContl(qDt.getDataType());
        }
	}
	
	public Object getSelectionUnit() {
		return selectionUnit;
	}

	public void setSelectionUnit(Object selectionUnit) {
		this.selectionUnit = selectionUnit;
	}

	public void resetSelection() {
		this.selection = new WaQuestionDT();
		this.selectionUnit = new WaQuestionDT();
		this.oldDT = new WaQuestionDT();
	}
	
	/**
	 * Get a list of HTML display controls for a given data type.
	 * @param code
	 * @return
	 */
	public ArrayList<Object> getDefaultDisplayContl(String code) 
	{
        ArrayList<Object> aList = (ArrayList<Object>)getDefaultDisplayControl(code);
        ArrayList<Object> newList = new ArrayList<Object>();
        
        // NOTE: filter 'multi-line' text as a possible display control
        // for date, date/time and numeric fields 
        for (Object item : aList) {
            DropDownCodeDT ddcDt = (DropDownCodeDT) item;
            if ((code.equalsIgnoreCase(NEDSSConstants.NBS_QUESTION_DATATYPE_DATE) ||
                    code.equalsIgnoreCase(NEDSSConstants.NBS_QUESTION_DATATYPE_DATETIME) ||
                    code.equalsIgnoreCase("datetime") ||
                    code.equalsIgnoreCase(NEDSSConstants.NBS_QUESTION_DATATYPE_NUMERIC) )&&
                    (ddcDt.getKey().equals("1009") || ddcDt.getKey().equals("1019"))) {
                continue;
            }
            else {
                newList.add(item);
            }
        }
        
        setDisplayControlList(newList);
        
        return newList;
    }
	
	/**
	 * Get a list of values for a given code and key
	 * @param code and key
	 * @return
	 */
	public ArrayList<Object> getCodedValueBycodeAndDesc(String code, String codeSetNm) {
		//ArrayList<Object> aList = (ArrayList<Object> ) CachedDropDowns.getCodeDescTxtForCd(code, key);
		ArrayList<Object> returnVal = new ArrayList<Object>(); 
		ArrayList<Object> aList = CachedDropDowns.getCodedValueForType(codeSetNm);
		DropDownCodeDT ddcDT =  null;
        if (aList != null && aList.size() > 0) {
            Iterator<Object>  ite = aList.iterator();
            while (ite.hasNext()) {
                ddcDT = (DropDownCodeDT) ite.next();
                if (ddcDT.getKey().equals(code)) {
                	returnVal.add(ddcDT);
                }
            }
        }
        return returnVal;
		
	}
	
	/**
	 * Get a list of values for a given key
	 * @param key
	 * @return
	 */
	public ArrayList <Object> getCodedValue(String key) {
		ArrayList<Object> aList = (ArrayList<Object> ) CachedDropDowns.getCodedValueForType(key);
		ArrayList<Object> returnVal = new ArrayList<Object>(); 
		
		// handle business specific cases here //

		// for question types - filter out unsupported values
		if (key.equals(NEDSSConstants.NBS_QUESTION_TYPE)) {
		    for (Object singleVal : aList) {
		        DropDownCodeDT singleDt = (DropDownCodeDT) singleVal;
		        if (
		                singleDt.getKey().equals(NEDSSConstants.PHIN_QUESTIONTYPE_CODE) ||
		                singleDt.getKey().equals(NEDSSConstants.STATE_QUESTIONTYPE_CODE) ||
		                singleDt.getKey().equals("")
		        ) {
		            returnVal.add(singleVal);
		        }
		            
		    }
		}
		// for question data types - filter out the 'participation' data type option
		else if (key.equals(NEDSSConstants.PAGE_DATATYPE)) {
		    for (Object singleVal : aList) {
                DropDownCodeDT singleDt = (DropDownCodeDT) singleVal;
                if (!singleDt.getKey().equals(NEDSSConstants.PART_DATATYPE)) {
                    returnVal.add(singleVal);
                }
		    }
		}

		else if (actionMode!=null && actionMode.equals(NEDSSConstants.CREATE_LOAD_ACTION) && key.equals(NEDSSConstants.PAGE_GROUP)) {
            for (Object singleVal : aList) {
                DropDownCodeDT singleDt = (DropDownCodeDT) singleVal;
                // allow only investigation group
                if (!Arrays.asList(NEDSSConstants.PAGE_GROUP_EXC).contains(singleDt.getKey())) {
                    returnVal.add(singleVal);
                }
            }
        }
		else if (key.equals(NEDSSConstants.PAGE_SUBGROUP)) {
	            for (Object singleVal : aList) {
	                DropDownCodeDT singleDt = (DropDownCodeDT) singleVal;
	                // do not allow 'Investigation Generic Questions' subgroup (i.e., INV)
	                if (!singleDt.getKey().equals("INV")) {
	                    returnVal.add(singleVal);
	                }
	            }
	        }
        else {
            returnVal = aList;
        }
		
		return returnVal;
	}
	
	public Object getMaskforDate(String key) {
		ArrayList<Object> aList = (ArrayList<Object> ) CachedDropDowns.getCodedValueForType(key);
		ArrayList<Object> aDateList = new ArrayList<Object>();
		if(aList != null ){
			Iterator<Object> iter = aList.iterator();
			while(iter.hasNext()){
				DropDownCodeDT dDownDT  = (DropDownCodeDT)iter.next();
				if(dDownDT.getKey().equals(NEDSSConstants.DATE_DATATYPE)){
					aDateList.add(dDownDT);
				}
			}
		}
		
		return aDateList;
	}
	
	public Object getMaskforDateTime(String key) {
		ArrayList<Object> aList = (ArrayList<Object> ) CachedDropDowns.getCodedValueForType(key);
		ArrayList<Object> aDateList = new ArrayList<Object>();
		if(aList != null ){
			Iterator<Object> iter = aList.iterator();
			while(iter.hasNext()){
				DropDownCodeDT dDownDT  = (DropDownCodeDT)iter.next();
				if(dDownDT.getKey().equals(NEDSSConstants.DATETIME_DATATYPE)){
					aDateList.add(dDownDT);
				}
			}
		}
		
		return aDateList;
	}
	public ArrayList<Object> setStateQuestionIdentifier(){
		CachedDropDownValues cachedDropDownValues =  new CachedDropDownValues();
		String stateSpec = UidClassCodes.NBS_CLASS_CODE;
		ArrayList<Object> list = new ArrayList <Object> ();
		PageManagementActionUtil util = new PageManagementActionUtil();
		Long uniqueIdL = util.getUniqueQuestionIdentifier();
		String uniqueId = stateSpec+uniqueIdL.toString();
		Long uniqueIdUnitL = uniqueIdL+1;
		String uniqueIdUnit = stateSpec+(uniqueIdUnitL).toString();;
		WaQuestionDT dt = (WaQuestionDT)this.getSelection();
		dt.setQuestionIdentifier(uniqueId);
		this.setSelection(dt);
		//To set the phinDefined Indicator 
		WaQuestionDT dtUnit = (WaQuestionDT)this.getSelectionUnit();
		dtUnit.setQuestionType(dt.getQuestionType());
		dtUnit.setQuestionTypeDesc( cachedDropDownValues.getDescForCode(NEDSSConstants.NBS_QUESTION_TYPE,dt.getQuestionType()));
		dtUnit.setQuestionIdentifier(uniqueIdUnit);
		this.setSelectionUnit(dtUnit);
		setQuestionIndentifier(uniqueId);
		setQuestionUnitIndentifier(uniqueIdUnit);
		list.add(uniqueId);
		list.add(uniqueIdUnit);
		return list;
	}
	public String setQuestionType(String ind){
		//To set the phinDefined Indicator 
		WaQuestionDT dtUnit = (WaQuestionDT)this.getSelectionUnit();
		String indDesc = (ind.equals("LOCAL") ? NEDSSConstants.STATE_DEFINED_QUESTIONTYPE : NEDSSConstants.PHIN_DEFINED_QUESTIONTYPE);
		dtUnit.setQuestionType(ind);
		dtUnit.setQuestionTypeDesc(indDesc);
		this.setSelectionUnit(dtUnit);
		return indDesc;
	}
	/*public String setStateQuestionIdentifierUnit(){
		String stateSpec = UidClassCodes.NBS_CLASS_CODE;
		PageManagementActionUtil util = new PageManagementActionUtil();
		Long uniqueIdL = util.getUniqueQuestionIdentifier();
		Long uniqueIdUnitL = uniqueIdL+1;
		String uniqueIdUnit = stateSpec+(uniqueIdUnitL).toString();		
		//To set the phinDefined Indicator 
		WaQuestionDT dtUnit = (WaQuestionDT)this.getSelectionUnit();
		dtUnit.setQuestionIdentifier(uniqueIdUnit);
		this.setSelectionUnit(dtUnit);
		
		return uniqueIdUnit;
	}
	*/
	
	public Object getValueSets() {
		ArrayList<Object> aList = (ArrayList<Object> ) CachedDropDowns.getAllActiveCodeSetNms();
		return aList;
	}
	
	public Object getUnitsType() {
		ArrayList<Object> aList = (ArrayList<Object> ) CachedDropDowns.getNbsUnitsType();
		return aList;
	}

	public String getQuestionIndentifier() {
		return questionIndentifier;
	}

	public void setQuestionIndentifier(String questionIndentifier) {
		this.questionIndentifier = questionIndentifier;
	}	
	
	
	public String getQuestionUnitIndentifier() {
		return questionUnitIndentifier;
	}

	public void setQuestionUnitIndentifier(String questionUnitIndentifier) {
		this.questionUnitIndentifier = questionUnitIndentifier;
	}

	public Object getMaskforNumeric(String key) {
		ArrayList<Object> aList = (ArrayList<Object> ) CachedDropDowns.getCodedValueForType(key);
		ArrayList<Object> aNumList = new ArrayList<Object>();
		if(aList != null ){
			Iterator<Object> iter = aList.iterator();
			while(iter.hasNext()){
				DropDownCodeDT dDownDT  = (DropDownCodeDT)iter.next();
				if(dDownDT.getKey().indexOf("NUM")!= -1){
					aNumList.add(dDownDT);
				}
			}
		}		
		return aNumList;
	}
	public Object getMaskforText(String key) {
		ArrayList<Object> aList = (ArrayList<Object> ) CachedDropDowns.getCodedValueForType(key);
		ArrayList<Object> aTextList = new ArrayList<Object>();
		if(aList != null ){
			Iterator<Object> iter = aList.iterator();
			while(iter.hasNext()){
				DropDownCodeDT dDownDT  = (DropDownCodeDT)iter.next();
				if(dDownDT.getKey().indexOf(NEDSSConstants.NUMERIC_CODE)== -1) 
						if(dDownDT.getKey().indexOf(NEDSSConstants.DATE_DATATYPE)== -1){
							aTextList.add(dDownDT);
				}
			}
		}		
		return aTextList;
	}
	
	

	public ArrayList<Object> getDisplayControlList() {
		return displayControlList;
	}

	public void setDisplayControlList(ArrayList<Object> displayControlList) {
		this.displayControlList = displayControlList;
	}

	public Object getDefaultDisplayControl(String code) {
		ArrayList<Object> aList = (ArrayList<Object> ) CachedDropDowns.getDefaultDisplayControl(code);
		return aList;
	}
	
	public Object setDefaultDisplayControl(String code) {
		ArrayList<Object> aList = (ArrayList<Object>)getDefaultDisplayControl(code);
		setDisplayControlList(aList);
		return aList;
	}
	
	public void clearSelections() {
		this.setSearchMap(new HashMap<Object,Object>());
		this.resetSelection();
		//this.setAttributeMap(new HashMap<Object,Object>());
		this.setActionMode(null);
		this.setReturnToLink(null);
		
	}

	
	public void clearForm() {
		this.defaultValueUnitColl = new ArrayList();
		
	}
	
	public Map<Object,Object> getSearchMap() {
		return searchMap;
	}
	public void setSearchMap(Map<Object,Object> searchMap) {
		this.searchMap = searchMap;
	}
	public String getReturnToLink() {
		return returnToLink;
	}
	public void setReturnToLink(String returnToLink) {
		this.returnToLink = returnToLink;
	}
	public void setSearchCriteria(String key, String answer) {
		searchMap.put(key, answer);
	}

	public String getSearchCriteria(String key) {
		return (String) searchMap.get(key);
	}
	
	public ArrayList<Object> getDefaultCodeValues(){
		return null;
	}
	
	public String setUnitQuestionNm(String quesnm){
		String unitQuesNm = quesnm + " (unit)";		
		WaQuestionDT dt = (WaQuestionDT)this.getSelectionUnit();		
		dt.setQuestionNm(unitQuesNm);
		this.setSelectionUnit(dt);
		return unitQuesNm;
	}

	public ArrayList<Object> getDefaultValueUnitColl() {
		return defaultValueUnitColl;
	}

	public void setDefaultValueUnitColl(ArrayList<Object> defaultValueUnitColl) {
		this.defaultValueUnitColl = defaultValueUnitColl;
	}
	
	public Object isQuestionImported(String questionUid) {
		String retString = "notimported";
		
		StringTokenizer st =
		      new StringTokenizer(importedQUIDList, ",");

		   while(st.hasMoreTokens()){
			   if(questionUid.equals(st.nextToken())){
					retString = "imported";
					break;
				     }
		     }	
		
		return retString;
	}

	public void setImportedQUIDList(String importedQUIDListtoadd) {		
		
		 StringTokenizer st =
		      new StringTokenizer(importedQUIDListtoadd, ",");

		   while(st.hasMoreTokens()){
			   importedQUIDList = importedQUIDList +","+ st.nextToken();
		     }
		  
	}
	
	public ArrayList<Object> getDefaultValuesForValueSet(String groupID)
	{
	    CachedDropDownValues cdv = new CachedDropDownValues();
	    ArrayList<Object> retVal = CachedDropDowns.getCodedValueForType(cdv.getTheCodeSetNm(new Long(groupID)));
	    return retVal;
	}
	public void clearAll() {
		getAttributeMap().clear();
		searchCriteriaArrayMap = new HashMap<Object,Object>();
	}
	public Map<Object,Object> getSearchCriteriaArrayMap() {
		return searchCriteriaArrayMap;
	}
	public void setSearchCriteriaArrayMap(Map<Object,Object> searchCriteriaArrayMap) {
		this.searchCriteriaArrayMap = searchCriteriaArrayMap;
	}	
	public void initializeDropDowns(ArrayList<Object> manageList)throws Exception {
		type = pageManagementUtil.getType(manageList);
		group = pageManagementUtil.getGroup(manageList);
		subGroup = pageManagementUtil.getSubGroup(manageList);
		status = pageManagementUtil.getStatus(manageList);
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

	public void setAnswerArrayText(String key, String answer) {
		if(answer!=null && answer.length() > 0) {
			String newKey = key+"_FILTER_TEXT";
				searchCriteriaArrayMap.put(newKey,HTMLEncoder.encodeHtml(answer));
		}
	}
	
	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	
	
	
}

