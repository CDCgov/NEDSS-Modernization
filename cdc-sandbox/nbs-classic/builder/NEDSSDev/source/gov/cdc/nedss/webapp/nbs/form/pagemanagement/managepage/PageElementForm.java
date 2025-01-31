package gov.cdc.nedss.webapp.nbs.form.pagemanagement.managepage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.vo.PageElementVO;
import gov.cdc.nedss.pagemanagement.wa.dt.WaUiMetadataDT;
import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.webapp.nbs.form.util.BaseForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

/**
 * This form will be used to capture all the details about a page element. 
 * Page elements include tabs, sections, subsections, questions etc... 
 */
public class PageElementForm extends BaseForm 
{
    private static final long serialVersionUID = 1L;
    private String uiComponentDesc;
    
	PageElementVO pageEltVo = new PageElementVO();
	
    // to transfer errors during form submission
	ArrayList<Object> ErrorList = new ArrayList<Object>();
	
	// to hold cached values
	ArrayList<Object> displayControlList = new ArrayList<Object>();
	
	ArrayList<Object> defaultValueUnitColl = new ArrayList<Object>();
	
	ArrayList<WaUiMetadataDT> manageList = new ArrayList<WaUiMetadataDT>();
	
	//To hold all the batched question for the edit subsection
	Map<String,WaUiMetadataDT> batchQuestionMap = new LinkedHashMap<String,WaUiMetadataDT> ();
	

	public Map<String, WaUiMetadataDT> getBatchQuestionMap() {
		return batchQuestionMap;
	}

	public void setBatchQuestionMap(Map<String, WaUiMetadataDT> batchQuestionMap) {
		this.batchQuestionMap = batchQuestionMap;
	}

	
	public ArrayList<WaUiMetadataDT> getManageList() {
		return manageList;
	}

	public void setManageList(ArrayList<WaUiMetadataDT> manageList) {
		this.manageList = manageList;
	}

	// getters/setters
	public PageElementVO getPageEltVo() {
        return pageEltVo;
    }

    public void setPageEltVo(PageElementVO pageEltVo) {
        this.pageEltVo = pageEltVo;
        
        // default codes for the value set selected
        if (pageEltVo.getWaUiMetadataDT().getCodeSetGroupId() != null) {
            ArrayList<Object> codeList = 
                getDefaultValuesForValueSet(pageEltVo.getWaUiMetadataDT().getCodeSetGroupId().toString());
            setDefaultValueUnitColl(codeList);
        }
        
        // default display controls for the selected data type
        if (pageEltVo.getWaUiMetadataDT().getDataType() != null) {
            // this call also sets the displayControlList property
            getDefaultDisplayContl(pageEltVo.getWaUiMetadataDT().getDataType());
        }
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
                    code.equalsIgnoreCase(NEDSSConstants.NBS_QUESTION_DATATYPE_NUMERIC)) &&
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
    
    public ArrayList<Object> getDefaultValuesForValueSet(String groupID)
    {
        CachedDropDownValues cdv = new CachedDropDownValues();
        ArrayList<Object> retVal = CachedDropDowns.getCodedValueForType(cdv.getTheCodeSetNm(new Long(groupID)));
        return retVal;
    }
    
    public Object getUnitsType() {
        ArrayList<Object> aList = (ArrayList<Object> ) CachedDropDowns.getNbsUnitsType();
        return aList;
    }

	public ArrayList <Object> getCodedValue(String key) {
		ArrayList<Object> aList = (ArrayList<Object> ) CachedDropDowns.getCodedValueForType(key);
		return aList;
	}
	
	public void setErrorList(ArrayList<Object> errorList) {
		ErrorList = errorList;
	}

	public ArrayList<Object> getDisplayControlList() {
		return displayControlList;
	}

	public void setDisplayControlList(ArrayList<Object> displayControlList) {
		this.displayControlList = displayControlList;
	}
	
	public ArrayList<Object> getDefaultValueUnitColl() {
	    return defaultValueUnitColl;
	}

	public void setDefaultValueUnitColl(ArrayList<Object> defaultValueUnitColl) {
	    this.defaultValueUnitColl = defaultValueUnitColl;
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
	
	public Object getValueSets() {
		ArrayList<Object> aList = (ArrayList<Object> ) CachedDropDowns.getAllActiveCodeSetNms();
		return aList;
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
				if((dDownDT.getKey().indexOf(NEDSSConstants.NUMERIC_CODE)!= -1) && (dDownDT.getKey().indexOf(NEDSSConstants.DATE_DATATYPE)!= -1) ){
					aTextList.add(dDownDT);
				}
			}
		}		
		return aTextList;
	}
	
	public Object getDefaultDisplayControl(String code) {
		ArrayList<Object> aList = (ArrayList<Object> ) CachedDropDowns.getDefaultDisplayControl(code);
		return aList;
	}
	
	public Object setDefaultDisplayControl(String code) {
		ArrayList<Object> aList = (ArrayList<Object>)getDefaultDisplayControl(code);
		//setDisplayControlList(aList);
		return aList;
	}
	
	public ArrayList<Object> getDefaultCodeValues(){
		return null;
	}

	public String getUiComponentDesc() {
		return uiComponentDesc;
	}

	public void setUiComponentDesc(String uiComponentDesc) {
		this.uiComponentDesc = uiComponentDesc;
	}
}