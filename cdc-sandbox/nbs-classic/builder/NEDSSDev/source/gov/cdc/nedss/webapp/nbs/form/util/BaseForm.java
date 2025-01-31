package gov.cdc.nedss.webapp.nbs.form.util;

import gov.cdc.nedss.act.interview.dt.InterviewSummaryDT;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.entity.place.vo.PlaceSearchVO;
import gov.cdc.nedss.entity.place.vo.PlaceVO;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.ldf.dt.StateDefinedFieldDataDT;
import gov.cdc.nedss.locator.dt.EntityLocatorParticipationDT;
import gov.cdc.nedss.locator.dt.PostalLocatorDT;
import gov.cdc.nedss.locator.dt.TeleLocatorDT;
import gov.cdc.nedss.pagemanagement.wa.dt.BatchEntry;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.util.HTMLEncoder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.util.StringUtils;
import gov.cdc.nedss.webapp.nbs.action.client.ClientUtil;
import gov.cdc.nedss.webapp.nbs.action.client.ClientVO;
import gov.cdc.nedss.webapp.nbs.action.contacttracing.util.CTConstants;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageConstants;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageLoadUtil;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageStoreUtil;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageTBHelper;
import gov.cdc.nedss.webapp.nbs.action.pam.util.DWRUtil;
import gov.cdc.nedss.webapp.nbs.action.place.PlaceUtil;
import gov.cdc.nedss.webapp.nbs.action.util.CallProxyEJB;
import gov.cdc.nedss.webapp.nbs.form.page.PageForm;
import gov.cdc.nedss.webapp.nbs.form.pam.FormField;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;
import gov.cdc.nedss.webapp.nbs.servlet.NedssCodeLookupServlet;

import java.beans.PropertyDescriptor;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;

import com.google.gson.JsonObject;

/**
 * BaseForm is the root bean for all JSP rendering Struts Forms
 * @author nmallela
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: Computer Sciences Corporation</p>
 * BaseForm.java
 * Aug 21, 2008
 * @version
 */
public class BaseForm extends ValidatorForm {
	
	private static final LogUtils logger = new LogUtils(BaseForm.class.
		      getName());

	private static final long serialVersionUID = 1L;
	private String actionMode;
	private String actionModeParent;//BatchEntries clicked from View or Edit icon
	private String mode;
	private String businessObjectType;
	private String genericType;

	private String pageTitle;
	private String pageFormCd;
	private String dwrInvestigatorUid;
	private String dwrInvestigatorDetails;
	private String dwrOrganizationDetails;
	private String dwrOrganizationUid;

	
	/**
	 * tabId - Represents the index of a single tab in a tab group UI element. 
	 * Tabs are ordered from left to right and index starts from 0, 1, 2, etc...
	 */
	private String tabId;
	
	/**
	 * errorTabs - An array that represents all the IDs of the
	 * tabs in a tab group that has been marked as "errored". i.e.,
	 * a errorTab by definition has 1 or more fields in it that has a
	 * data entry error.
	 */
	private Object[] errorTabs = new String[0];
	
	private Map<Object,Object> attributeMap = new HashMap<Object,Object>();
	private Map<Object,Object> attributeMap2 = new HashMap<Object,Object>();
	private Map<Object,Object> ldfMap = new HashMap<Object,Object>();
	private ArrayList<Object> errorList = new ArrayList<Object> ();
	private Map<Object,Object> securityMap = new HashMap<Object,Object>();
	private Map<Object,Object> securityMap2 = new HashMap<Object,Object>();
	private ArrayList<Object> noDataArray = new ArrayList<Object> ();
	protected Map<Object, Object> formFieldMap = new HashMap<Object, Object>();
	
	//store error fields for Edit page
	private String errorFieldList;

	Object selection = new Object();
	
	//repeating block fields
	ArrayList<BatchEntry> alist = new ArrayList<BatchEntry>();
	private Map<Object,Object> subSecStructureMap = new HashMap<Object,Object>();
	private Map<Object,Object> subSecStructureMap2 = new HashMap<Object,Object>();
	private Map<Object,ArrayList<BatchEntry>> batchEntryMap = new HashMap<Object,ArrayList<BatchEntry>>();
	private Map<Object,ArrayList<BatchEntry>> batchEntryMap2 = new HashMap<Object,ArrayList<BatchEntry>>();
	
	private static int nextId = 1;
	private Map<Object,Object> questionMetadataMap = new HashMap<Object,Object>();
	private Map<Object,Object> updatedRepeatingCommentsMap = new HashMap<Object,Object>();
	
	private String cityList;
	private ArrayList<Object> dwrCounties = new ArrayList<Object>();
	
	private ArrayList<Object> dwrImportedCounties = new ArrayList<Object>();
	private ArrayList<Object> dwrImportedCounties2 = new ArrayList<Object>();
	
	private ArrayList<Object> dwrStateSiteCounties = new ArrayList<Object> ();
	

	public String getGenericType() {
		return genericType;
	}
	public void setGenericType(String genericType) {
		this.genericType = genericType;
	}

	public String getActionMode() {
		if(actionMode == null) return null;
		else return HTMLEncoder.encodeHtml(actionMode);
	}
	public void setActionMode(String actionMode) {
		this.actionMode = actionMode;
	}

	public String getBusinessObjectType() {
		return businessObjectType;
	}

	public void setBusinessObjectType(String businessObjectType) {
		this.businessObjectType = businessObjectType;
	}	
	public String getPageTitle() {
		if(pageTitle == null || (pageTitle != null && pageTitle.trim().length() == 0))
			pageTitle = "NEDSS Base System";
		return HTMLEncoder.encodeHtml(pageTitle);
	}

	public void setPageTitle(String pageTitle, HttpServletRequest req) {
		this.pageTitle = pageTitle;
		req.getSession().setAttribute("BaseForm", this);		
	}
    public String getErrorFieldList() {
    	return errorFieldList;
    }

    public void setErrorFieldList(String  errorFieldList) {
    	this.errorFieldList= errorFieldList;
    }	

	public Object getSelection() {
		return selection;
	}

	public void setSelection(Object selection) {
		this.selection = selection;
	}	
	
	public String getTabId() {
		return tabId;
	}
	public void setTabId(String tabId) {
		this.tabId = tabId;
	}
	
	public Object[] getErrorTabs() {
		return errorTabs;
	}

	public void setErrorTabs(Object[] errorTabs) {
		this.errorTabs = errorTabs;
	}
	
	public Map<Object,Object> getAttributeMap() {
		return attributeMap;
	}
	public Map<Object,Object> getLdfMap() {
		return ldfMap;
	}
	public void setLdfMap(Map<Object,Object> ldfMap) {
		this.ldfMap = ldfMap;
	}
	
	public ArrayList<Object> getErrorList() {
		return errorList;
	}

	public void setErrorList(ArrayList<Object>  errorList) {
		this.errorList = errorList;
	}
	
	public void setSecurityMap(Map<Object,Object> securityMap) {
		this.securityMap = securityMap;
	}

	public Map<Object,Object> getSecurityMap() {
		return securityMap;
	}
	
	public String getSecurity(String key) {
		return (String)securityMap.get(key);
	}
	
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		errorList = new ArrayList<Object> ();
	}
	
	private ArrayList<Object> ldfCollection  = null;

	public ArrayList<Object> getLdfCollection() {
          if(ldfCollection  != null && ldfCollection.size()>0)
          {
           Iterator<Object>  it = ldfCollection.iterator();
            while (it.hasNext()) {
              StateDefinedFieldDataDT stateDT = (StateDefinedFieldDataDT) it.next();
              if (stateDT.getBusinessObjNm() == null)
                it.remove();
            }
          }
		return ldfCollection  ;

	}


	public void setLdfCollection(ArrayList<Object>  aLdfCollection) {
		this.ldfCollection  = aLdfCollection;
	}
	/**
	 * retrieve ldf dt from index
	 * @param index
	 * @return
	 */
	public StateDefinedFieldDataDT getLdf(int index) {
		// this should really be in the constructor
		if (this.ldfCollection  == null) {
			this.ldfCollection  = new ArrayList<Object> ();
		}

		int currentSize = this.ldfCollection.size();

		// check if we have a this many organizationNameDTs
		if (index < currentSize) {
			try {
				Object[] tempArray = this.ldfCollection.toArray();

				Object tempObj = tempArray[index];

				StateDefinedFieldDataDT tempDT = (StateDefinedFieldDataDT) tempObj;

				return tempDT;
			}
			catch (Exception e) {
				logger.debug(e);
			} // do nothing just continue
		}
		StateDefinedFieldDataDT tempDT = null;

		for (int i = currentSize; i < index + 1; i++) {
			tempDT = new StateDefinedFieldDataDT();
			this.ldfCollection.add(tempDT);
		}
		return tempDT;
	}

	protected void resetLDF() {
		ldfCollection  = null;
	}


	public void setAttributeMap(Map<Object,Object> attributeMap) {
		this.attributeMap = attributeMap;
	}
	
	public Map<Object, Object> getFormFieldMap() {
		return formFieldMap;
	}

	public void setFormFieldMap(Map<Object, Object> formFieldMap) {
		this.formFieldMap = formFieldMap;
	}

	public FormField getField(String key) {
		return (FormField) formFieldMap.get(key);
	}
	

	public Object getCodedValue(String key) {
		ArrayList<Object> aList = new ArrayList<Object>();
		try {
			
		if (key.equals("PHC_TYPE")) 
				aList = CachedDropDowns.getConditionDropDown(this.getPageFormCd());
		else if (formFieldMap.containsKey(key)) {
			FormField fField = (FormField) formFieldMap.get(key);
			aList = CachedDropDowns.getCodedValueForType(fField
					.getCodeSetNm());
			//aList = fField.getAList();
		}
		else
			aList = CachedDropDowns.getCodedValueForType(key);
		} catch (Exception e) {
				logger.error("getCodedValue error for " + key + "/n " + e);
		} 
		return aList;
	}
	

	@SuppressWarnings("unused")
	public Object getCodedValueNoBlnk(String key) {
		ArrayList<?> list = (ArrayList<?> ) getCodedValue(key);
		DropDownCodeDT dt = (DropDownCodeDT) list.get(0);
		if(dt.getKey() != null && dt.getKey().equals("") && dt.getValue() != null && dt.getValue().equals(""))
			list.remove(0);
		if(list != null)
			return list;
		return new ArrayList<Object>();
	}
    public Map<Object, Object> getSubSecStructureMap() {
		return subSecStructureMap;
	}
    public Map<Object, Object> getSubSecStructureMap2() {
		return subSecStructureMap2;
	}

	public void setSubSecStructureMap(Map<Object, Object> subSecStructureMap) {
		this.subSecStructureMap = subSecStructureMap;
	}
	public void setSubSecStructureMap2(Map<Object, Object> subSecStructureMap) {
		this.subSecStructureMap2 = subSecStructureMap;
	}

	/**
	 * setAnswersBatchEntry: This method has been created to process multiple entries in the batch at once instead of calling setAnswer method for each of the rows from the UI.
	 * BatchEntry be contains an array of maps, and each map represents each of the rows.
	 * @param be
	 * @param session
	 */
	public void setAnswersBatchEntry (BatchEntry be,HttpSession session) {
		
		//subsecNm:subSecNm, id:viewed,answerMap:arrayOfMaps};
				
		ArrayList<Map<String, String>> arrayMaps = be.getAnswerMaps();
		String subsectionName = be.getSubsecNm();
		int id = be.getId();
		
		int length = arrayMaps.size();
		
		for(int i = 0 ; i< length ; i++){
			Map<String, String> map = arrayMaps.get(i);
			
			BatchEntry be2 = new BatchEntry();
			be2.setAnswerMap(map);
			be2.setSubsecNm(subsectionName);
			be2.setId(id);
			
			setAnswer(be2,session);
		}
	}
	
	
    public void setAnswer(BatchEntry be,HttpSession session) {
    	
    	
    	 if(businessObjectType!=null && !businessObjectType.equalsIgnoreCase("LAB")){
    		 be.getAnswerMap().remove("NBS459");
    		 be.getAnswerMap().remove("NBS457");
    		 
    		 
    		 
    	 }
    	 
    	 
    	DropDownCodeDT ddDT = new DropDownCodeDT();
    	boolean isAddOp= false;
		String stateCodeValue="";
		ArrayList<BatchEntry> sublist = new ArrayList<BatchEntry>();
		try {
			if(be.getId()==-1){

				if(batchEntryMap != null){
					Iterator<Entry<Object, ArrayList<BatchEntry>>> itt = batchEntryMap.entrySet().iterator();
					alist = new ArrayList<BatchEntry>();
					while(itt.hasNext()){
						Map.Entry<Object,ArrayList<BatchEntry>> pairs = (Map.Entry<Object,ArrayList<BatchEntry>>)itt.next();
						alist.addAll((ArrayList<BatchEntry>)pairs.getValue());
					}
					Iterator<BatchEntry> it1 = alist.iterator();
					int seq=0;
					while(it1.hasNext()){
						BatchEntry dtt = (BatchEntry)it1.next();
						if(seq < dtt.getId()){
							seq = dtt.getId();
						}
					}
					if(seq == 0 ){
						be.setId(getNextId());
					}
					else {
						be.setId(seq+1);
					}

				}else{
					be.setId(getNextId());
				}
				be.setAnswerGrpSeqID(be.getId());
			}else{
				if(batchEntryMap != null && batchEntryMap.get(be.getSubsecNm()) != null){
					boolean realUpdate=false;
					ArrayList<BatchEntry> list = batchEntryMap.get(be.getSubsecNm());
					for(int c1=0;c1<list.size();c1++){
						if(be.getId()== list.get(c1).getId()){
							realUpdate = true;
							break;
						}
					}
					if(!realUpdate){
						if(batchEntryMap != null){
							Iterator<Entry<Object, ArrayList<BatchEntry>>> itt = batchEntryMap.entrySet().iterator();
							alist = new ArrayList<BatchEntry>();
							while(itt.hasNext()){
								Map.Entry<Object,ArrayList<BatchEntry>> pairs = (Map.Entry<Object,ArrayList<BatchEntry>>)itt.next();
								alist.addAll((ArrayList<BatchEntry>)pairs.getValue());
							}
							Iterator<BatchEntry> it1 = alist.iterator();
							int seq=0;
							while(it1.hasNext()){
								BatchEntry dtt = (BatchEntry)it1.next();
								if(seq < dtt.getId()){
									seq = dtt.getId();
								}
							}
							if(seq == 0 ){
								be.setId(getNextId());
							}
							else{
								be.setId(seq+1);
							}
						}
					}

				}
			}
		} catch (Exception e) {
			logger.error("Exception raised in BaseForm.setAnswer-1: " + e);
			e.printStackTrace();
		}

		try {
    	Iterator<Entry<String, String>> it = be.getAnswerMap().entrySet().iterator();
    	
    	
		while(it.hasNext()){

	    ArrayList<Object> srtValues = new ArrayList<Object> ();
		Map.Entry<String,String> pairs = (Map.Entry<String,String>)it.next();

		HashMap<Object,Object> formMap = new HashMap<Object,Object>();
		formMap = (HashMap<Object,Object>) getFormFieldMap();

		Iterator<?> it1 = formMap.entrySet().iterator();
		while(it1.hasNext()){
		Map.Entry<?,?> formfieldpair = (Map.Entry<?,?>)it1.next();
	
		if(pairs.getKey().equals(formfieldpair.getKey())){
		FormField ff = (FormField)formfieldpair.getValue();
			if(ff.getFieldType().toString().equalsIgnoreCase("Coded")){
				
				if(((String)ff.getFieldId()).equalsIgnoreCase(PageConstants.NBS_LAB280) ||
						((String)ff.getFieldId()).equalsIgnoreCase(PageConstants.NBS367))
				{//ND-13895: Coded Result from Lab Report. The value is not coming from: CODED_LAB_RESULT. It is coming from LAB_RESULT
					ArrayList<Object> codedResultData  = new ArrayList<Object>();
					ArrayList<Object> organismDropdownList  = new ArrayList<Object>();
					
					PageLoadUtil.getCodedResultList(codedResultData);
					PageLoadUtil.getOrganismList(organismDropdownList);
					srtValues = codedResultData;
					srtValues.addAll(organismDropdownList);
				}
				else
					if(((String)ff.getFieldId()).equalsIgnoreCase(PageConstants.NBS377)){//Result Method is coming from OBS_METH_SUSC
						ArrayList<Object> resultMethodData  = new ArrayList<Object>();
						PageLoadUtil.getResultMethodSuscList(resultMethodData);
						srtValues = resultMethodData;
					}
				else
					srtValues = (ArrayList<Object>)getCodedValue(ff.getCodeSetNm());
				if(ff.getLabel()!=null && ff.getLabel().contains("State"))
					stateCodeValue = pairs.getValue();
				if(ff.getCodeSetNm()!=null && ff.getCodeSetNm().equals("COUNTY_CCD"))
					srtValues = (ArrayList<Object>)this.getDwrCountiesForState(stateCodeValue);
					 
			if(pairs.getValue()!=null && pairs.getValue().indexOf(";") != -1){
				//handling multi-select
					String values = pairs.getValue().toString();
					String mulVal ="";
					values = values.substring(0, values.length()-1);
					while(values.indexOf(";") != -1){
					String val = values.substring(0, values.indexOf(";"));
					values = values.substring(values.indexOf(";")+1);

					for(int i=0;i<srtValues.size();i++){
						ddDT = (DropDownCodeDT)srtValues.get(i);
						if(val.indexOf("$MulOth$") != -1){

							//val = val.substring(0, val.indexOf("$MulOth$"));
							if(ddDT.getKey().equals(val.substring(0, val.indexOf("$MulOth$")))){
								mulVal =  mulVal + ddDT.getKey()+"$$"+ ddDT.getValue()+"$MulOth$"+val.substring(val.indexOf("$MulOth$")+8, val.indexOf("#MulOth#"))+"#MulOth#"+"||";
							    break;
							}
						}else if(ddDT.getKey().equals(val) && !val.equals("")){
						mulVal =  mulVal + ddDT.getKey()+"$$"+ddDT.getValue()+"||";
					    break;
					}
					}
					}
					for(int i=0;i<srtValues.size();i++){
						ddDT = (DropDownCodeDT)srtValues.get(i);

                           if(values.indexOf("$MulOth$") != -1){

                        	//values = values.substring(0, values.indexOf("$MulOth$"));
							if(ddDT.getKey().equals(values.substring(0, values.indexOf("$MulOth$")))){
								mulVal =  mulVal + ddDT.getKey()+"$$"+ ddDT.getValue()+"$MulOth$"+values.substring(values.indexOf("$MulOth$")+8, values.indexOf("#MulOth#"))+"#MulOth#"+"||";
							    break;
							}

                           }else if(ddDT.getKey().toString().equals(values) && !values.equals("")){
						mulVal =  mulVal + ddDT.getKey()+"$$"+ddDT.getValue()+"||";
					    break;
					    }
					}


					pairs.setValue(mulVal);
			}else{
				for(int i=0;i<srtValues.size();i++){
					ddDT = (DropDownCodeDT)srtValues.get(i);
					String str = pairs.getValue();
					if(str != null && str.indexOf(":") != -1  && str.indexOf("OTH") != -1){
						if(ddDT.getKey().equals(str.substring(0,str.indexOf(":")))){
							pairs.setValue(ddDT.getKey()+"$$"+ddDT.getValue()+":"+str.substring(str.indexOf(":")+1));
						}
					}
					if(str != null && str.indexOf("$sn$") != -1){
						if(ddDT.getKey().equals(str.substring(str.indexOf("$sn$")+4))){
							pairs.setValue(str.substring(0,str.indexOf("$sn$"))+"$sn$"+ddDT.getKey()+"$val$"+ddDT.getValue());
						}
					}
					else{
						if(ddDT.getKey().equals(pairs.getValue())){
						pairs.setValue(ddDT.getKey()+"$$"+ddDT.getValue());
						break;
						}
						else//Resulted Test
							{
							String questionIdentifierResulted = "NBS_LAB220";//Resulted Test
							String questionIdentifierOrganism = "NBS_LAB280";//Organism Test
							
							
							
							String questionIdentifier = "";
							
							if(ff.getFieldId()!=null && (((String)ff.getFieldId()).equalsIgnoreCase(questionIdentifierResulted)
									|| ((String)ff.getFieldId()).equalsIgnoreCase(questionIdentifierOrganism)
									)){
								questionIdentifier = (String)ff.getFieldId();
							
						
								String code = be.getAttributeMap().get(questionIdentifier+"_Code");
								String value = be.getAttributeMap().get(questionIdentifier+"_Name");
								pairs.setValue(code+"$$"+value);

								
								break;
							 }						
					}
					}
				}
			}
			}
			break;
		}

		  }
		}
		
		String code = "NBS459";
		String value = be.getAttributeMap().get("NBS459");
		//pairs.setValue(code+"$$"+value);
		be.getAnswerMap().put(code, value);
		
		
		} catch (Exception e) {
			logger.error("Exception raised in BaseForm.setAnswer-2: " + e);
			e.printStackTrace();
		}
		
		try {
		if(batchEntryMap != null){
			Iterator<Entry<Object, ArrayList<BatchEntry>>> itt = batchEntryMap.entrySet().iterator();
			alist = new ArrayList<BatchEntry>();
			while(itt.hasNext()){
				Map.Entry<Object,ArrayList<BatchEntry>> pairs = (Map.Entry<Object,ArrayList<BatchEntry>>)itt.next();
				alist.addAll((ArrayList<BatchEntry>)pairs.getValue());
			}

		}

		
    	Map<String,String> map  = new HashMap<String,String>();
    	Iterator<BatchEntry> it1 = alist.iterator();
      	 while(it1.hasNext()){
      		BatchEntry dtt = (BatchEntry)it1.next();
      		if(dtt.getId()== be.getId()){
      			alist.remove(dtt);
      			alist.add(be);
      			isAddOp = true;
      		    break;
      		}
      	 }
		} catch (Exception e) {
			logger.error("Exception raised in BaseForm.setAnswer-3: " + e);
			e.printStackTrace();
		}      	 
		
		try {		
      	if(!(isAddOp == true))
    	alist.add(be);

      	 int n = alist.size();
		    for (int pass=1; pass < n; pass++) {  // count how many times
		        // This next loop becomes shorter and shorter
		        for (int i=0; i < n-pass; i++) {
		            if (alist.get(i).getId() > alist.get(i+1).getId()) {
		            	 BatchEntry be1 = alist.get(i);
		            	 BatchEntry be2 = alist.get(i+1);
		                // exchange elements
		               // int temp = x[i];
		            	 alist.remove(i);
		            	 alist.add(i, be2);
		            	 alist.remove(i+1);
		            	 alist.add(i+1, be1);
		                //blist.get(i+1) = be;
		            }
		        }
		    }

    	Iterator<BatchEntry> it2 = alist.iterator();
    	while(it2.hasNext()){
           BatchEntry b = (BatchEntry) it2.next();
           if(b.getSubsecNm().toString().equals(be.getSubsecNm().toString()))
        	   sublist.add(b);
    	}
		} catch (Exception e) {
			logger.error("Exception raised in BaseForm.setAnswer-4: " + e);
			e.printStackTrace();
		}
		try {	
    	batchEntryMap.remove(be.getSubsecNm());
    	batchEntryMap.put(be.getSubsecNm(), sublist);
			if (actionMode != null
					&& actionMode.equalsIgnoreCase(NEDSSConstants.VIEW)) {
				PageStoreUtil.saveBatchRecordFromViewPage(be, session,
						this.businessObjectType, attributeMap);
			}
		} catch (Exception e) {
			logger.error("Exception raised in BaseForm.setAnswer-5: " + e);
			e.printStackTrace();
		}
    }
    public void clearRepeatingblk(String SubSecNm){
    	try {
    	if (batchEntryMap.containsKey(SubSecNm)) {
    		ArrayList<BatchEntry> beList 	= (ArrayList<BatchEntry>)batchEntryMap.get(SubSecNm);
    		if (!beList.isEmpty()) {
    			batchEntryMap.remove(SubSecNm);
    			batchEntryMap.put(SubSecNm, new ArrayList<BatchEntry>());
    		}
    	} else logger.warn("Warning: clearRepeatingblk called for non-batch subsection?  " + SubSecNm);
		} catch (Exception e) {
			logger.error("Exception raised in BaseForm.clearRepeatingblk: " + e);
			e.printStackTrace();
		}
    }

    public void deleteAnswer(BatchEntry be) {
    try {
    if(batchEntryMap != null){
			Iterator<Entry<Object, ArrayList<BatchEntry>>> itt = batchEntryMap.entrySet().iterator();
			alist = new ArrayList<BatchEntry>();
			while(itt.hasNext()){
				Map.Entry<Object,ArrayList<BatchEntry>> pairs = (Map.Entry<Object,ArrayList<BatchEntry>>)itt.next();
				alist.addAll((ArrayList<BatchEntry>)pairs.getValue());
			}

		}
       	 Iterator it = alist.iterator();
   	ArrayList<BatchEntry> sublist = new ArrayList<BatchEntry>();
   	 while(it.hasNext()){
   		BatchEntry dtt = (BatchEntry)it.next();
   		if(dtt.getId()== be.getId()){
   			alist.remove(dtt);
   		    break;
   		}
   	 }
   	Iterator<BatchEntry> it2 = alist.iterator();
	while(it2.hasNext()){
       BatchEntry b = (BatchEntry) it2.next();
       if( b.getSubsecNm().toString().equals(be.getSubsecNm().toString()))
    	   sublist.add(b);
	}

	batchEntryMap.remove(be.getSubsecNm());
	batchEntryMap.put(be.getSubsecNm(), sublist);
	} catch (Exception e) {
		logger.error("Exception raised in BaseForm.deleteAnswer(): " + e);
		e.printStackTrace();
	}

   }

    public BatchEntry updateAnswer(BatchEntry be) {

    	 Iterator<Entry<String, String>> it = be.getAnswerMap().entrySet().iterator();
    	 try {
      	 while(it.hasNext()){
      		Map.Entry<String,String> pairs = (Map.Entry<String,String>)it.next();
      		if(pairs.getValue() != null){
      		String val =pairs.getValue().toString();
      		 if(val.indexOf("||") != -1){
					String  mulVal ="";
					String othVal="";
					val = val.substring(0, val.length()-2);
					if(val.indexOf("||") != -1){
					mulVal  =  val.substring(0, val.indexOf("||"));
					val = val.substring(val.indexOf("||")+2);
                    //mulVal   = mulVal.substring(0,mulVal.indexOf("$$"));
					 if(mulVal.indexOf("$MulOth$") != -1){
						 	othVal =  mulVal.substring(mulVal.indexOf("$MulOth$")+8, mulVal.indexOf("#MulOth#"));
					        mulVal   = mulVal.substring(0,mulVal.indexOf("$$"));
				            mulVal =  mulVal +"$MulOth$" + othVal +"#MulOth#";
				            othVal="";

				        }else{
				        	 mulVal   = mulVal.substring(0,mulVal.indexOf("$$"));
                          }
					}

					while(val.indexOf("||") != -1){
					String val1 =  val.substring(0, val.indexOf("||"));
					//mulVal  = mulVal  +","+  val1.substring(0,val1.indexOf("$$"));
					  if(val1.indexOf("$MulOth$") != -1){
						   mulVal  = mulVal  +","+  val1.substring(0,val1.indexOf("$$"));
				           othVal =  val1.substring(val1.indexOf("$MulOth$")+8, val1.indexOf("#MulOth#"));
				           mulVal =  mulVal +"$MulOth$" + othVal +"#MulOth#";
				           othVal="";
				        }else{
				        	mulVal  = mulVal  +","+  val1.substring(0,val1.indexOf("$$"));

                   }
					val = val.substring(val.indexOf("||")+2);
					}
					if(!mulVal.equals("")){
						  if(val.indexOf("$MulOth$") != -1){
							    mulVal  = mulVal  +","+  val.substring(0,val.indexOf("$$"));
 					            othVal =  val.substring(val.indexOf("$MulOth$")+8, val.indexOf("#MulOth#"));
 					           mulVal =  mulVal +"$MulOth$" + othVal +"#MulOth#";
 					            othVal="";
 					        }else{
 					        	mulVal  = mulVal  +","+  val.substring(0,val.indexOf("$$"));

	                }

					//mulVal  = mulVal  +","+  val.substring(0,val.indexOf("$$"));
					}
					else{
						 if(val.indexOf("$MulOth$") != -1){
							  mulVal  =   val.substring(0,val.indexOf("$$"));
					           othVal =  val.substring(val.indexOf("$MulOth$")+8, val.indexOf("#MulOth#"));
					           mulVal =  mulVal +"$MulOth$" + othVal +"#MulOth#";
					          othVal="";
					        }else{
					        	mulVal  =   val.substring(0,val.indexOf("$$"));

	                         }

					//mulVal  =   val.substring(0,val.indexOf("$$"));
					}
					pairs.setValue(mulVal);

      		 }else if(pairs.getValue().indexOf(":") != -1 && pairs.getValue().indexOf("$$") != -1 && pairs.getValue().indexOf("OTH")!=-1){
          		 val = pairs.getValue();
           		val = val.substring(0,val.indexOf("$$"))+ ":"+ val.substring(val.indexOf(":")+1,val.length());
           		pairs.setValue(val);

           	}else if(pairs.getValue().indexOf("$$") != -1){
      		 val = pairs.getValue();
      		val = val.substring(0,val.indexOf("$$"));
      		pairs.setValue(val);

      		 }
      		else if(pairs.getValue().indexOf("$sn$") != -1){
         		 val = pairs.getValue();
         		val = val.substring(0,val.indexOf("$sn$"))+ "$sn$" +val.substring(val.indexOf("$sn$")+4,val.indexOf("$val$"));
         		pairs.setValue(val);

         		 }
      	  }

      	 }

	} catch (Exception e) {
		logger.error("Exception raised in BaseForm.updateAnswer: " + e);
		e.printStackTrace();
	}
    	 
    	 if(businessObjectType!=null && !businessObjectType.equalsIgnoreCase("LAB")){
    		 be.getAnswerMap().remove("NBS459");
    		 be.getAnswerMap().remove("NBS457");
    		 
    		 
    		 
    	 }
 	 return be;
      }

    public ArrayList<BatchEntry> getAllAnswer(String subSectionNm) {

    	ArrayList<BatchEntry> be = new ArrayList<BatchEntry> ();
    	try {
    		be 	= (ArrayList<BatchEntry>)batchEntryMap.get(subSectionNm);
    	} catch (Exception e) {
    		logger.error("Exception raised in BaseForm.getAllAnswer for : " +subSectionNm + "/n " + e);
    		e.printStackTrace();
    	} 
    	
    	if(businessObjectType!=null && !businessObjectType.equalsIgnoreCase("LAB"))
    			if(be!=null && be.size()>0 && be.get(0)!=null){
    				((BatchEntry)be.get(0)).getAnswerMap().remove("NBS459");
    				((BatchEntry)be.get(0)).getAnswerMap().remove("NBS457");
        				
    			}
    	
        return be;
    }
    
    public ArrayList<BatchEntry> getAllAnswer2(String subSectionNm) {

    	ArrayList<BatchEntry> be = new ArrayList<BatchEntry> ();
    	try {
    		be 	= (ArrayList<BatchEntry>)batchEntryMap2.get(subSectionNm);
    	} catch (Exception e) {
    		logger.error("Exception raised in BaseForm.getAllAnswer for : " +subSectionNm + "/n " + e);
    		e.printStackTrace();
    	} 
        return be;
    }

 

    public Map<Object, ArrayList<BatchEntry>> getBatchEntryMap() 
    { 
    	return batchEntryMap;
	}
    public Map<Object, ArrayList<BatchEntry>> getBatchEntryMap2() 
    { 
    	return batchEntryMap2;
	}

	public void setBatchEntryMap(Map<Object, ArrayList<BatchEntry>> batchEntryMap) 
	{
		this.batchEntryMap = batchEntryMap;
		//this.batchEntryMap.remove(subSectionNm);
		//this.batchEntryMap.put(subSectionNm, sublist);
	}
	public void setBatchEntryMap2(Map<Object, ArrayList<BatchEntry>> batchEntryMap) 
	{
		this.batchEntryMap2 = batchEntryMap;
		//this.batchEntryMap.remove(subSectionNm);
		//this.batchEntryMap.put(subSectionNm, sublist);
	}
	public static synchronized int getNextId()
    {
		return nextId++;
    }
	public Map<Object, Object> getUpdatedRepeatingCommentsMap() {
		return updatedRepeatingCommentsMap;
	}

	public void setUpdatedRepeatingCommentsMap(
			Map<Object, Object> updatedRepeatingCommentsMap) {
		this.updatedRepeatingCommentsMap = updatedRepeatingCommentsMap;
	}

	public Map<Object, Object> getQuestionMetadataMap() {
		return questionMetadataMap;
	}

	public void setQuestionMetadataMap(
			Map<Object, Object> questionMetadataMap) {
		this.questionMetadataMap = questionMetadataMap;
	}

	public String getPageFormCd() {
		return pageFormCd;
	}

	public void setPageFormCd(String pageFormCd) {
		this.pageFormCd = pageFormCd;
	}
	// TODO:This method needs to be revisited
	public ArrayList<Object> getOnLoadCityList(String stateCode,
			HttpServletRequest req) {
		ArrayList<Object> cityList = new ArrayList<Object>();
		try {
		cityList = CachedDropDowns.getAllCitiesList(stateCode);
		req.getSession().setAttribute("cityList", cityList);
    	} catch (Exception e) {
    		logger.error("Exception raised in BaseForm.getOnLoadCityList()/n" + e);
    		e.printStackTrace();
    	} 
		return cityList;
	}
	
	public ArrayList<Object> getDwrCountiesForState(String state) {
		try {
		 this.dwrCounties = CachedDropDowns.getCountyCodes(state);
    	} catch (Exception e) {
    		logger.error("Exception raised in BaseForm.getDwrCountiesForState()/n" + e);
    	} 
		return dwrCounties;
	}
	
	public ArrayList<Object> getDwrImportedCountiesForState(String state) {
		try {
			this.dwrImportedCounties = CachedDropDowns.getCountyCodes(state);
		} catch (Exception e) {
			logger.error("Exception raised in BaseForm.getDwrCountiesForState()/n" + e);
		} 
		return dwrImportedCounties;
	}
	
	public ArrayList<Object> getDwrImportedCountiesForState2(String state) {
		try {
			this.dwrImportedCounties2 = CachedDropDowns.getCountyCodes(state);
		} catch (Exception e) {
			logger.error("Exception raised in BaseForm.getDwrCountiesForState()/n" + e);
		} 
		return dwrImportedCounties2;
	}
	
	
	public ArrayList<Object> getDwrStateSiteCounties() {
		return dwrStateSiteCounties;
	}

	public void setDwrStateSiteCounties(ArrayList<Object>  dwrStateSiteCounties) {
		this.dwrStateSiteCounties = dwrStateSiteCounties;
	}


	public ArrayList<Object> getDwrImportedCounties() {
		return dwrImportedCounties;
	}
	public ArrayList<Object> getDwrImportedCounties2() {
		return dwrImportedCounties2;
	}

	public void setDwrImportedCounties(ArrayList<Object> dwrImportedCounties) {
		this.dwrImportedCounties = dwrImportedCounties;
	}
	public void setDwrImportedCounties2(ArrayList<Object> dwrImportedCounties) {
		this.dwrImportedCounties2 = dwrImportedCounties;
	}
	
	public void clearDWRContact(String identifier) {
		getAttributeMap().remove(identifier + "Uid");
		getAttributeMap().remove(identifier + "SearchResult");
	}
	public void clearDWRInvestigator(String identifier) {
		getAttributeMap().remove(identifier + "Uid");
		getAttributeMap().remove(identifier + "SearchResult");
	}
	
	public void clearDWROrganization(String identifier) {
		getAttributeMap().remove(identifier + "Uid");
		getAttributeMap().remove(identifier + "SearchResult");
		if(this instanceof PageForm)
			((PageForm)this).getPageClientVO().setAnswer("NBS_LAB112",null);//Remove the Ordered Test ND-14240
	}
	
	public String getDwrInvestigatorDetails(String code, String identifier) {
		if (code != null && code.trim().equals(""))
			return "<font color=\"red\"><b>Please enter a code and try again or use the Search functionality.</b></font>";
		WebContext ctx = WebContextFactory.get();
		HttpServletRequest req = ctx.getHttpServletRequest();
		Map<?, ?> returnMap = new HashMap<Object, Object>();
		StringBuffer sb = new StringBuffer();
		try {
			returnMap = NedssCodeLookupServlet.getPersonValue(null, code, req
					.getSession());
		} catch (Exception e) {
			dwrInvestigatorUid = "";
			dwrInvestigatorDetails = "";
			logger.error("Exception raised in PergetDwrInvestigatorDetails: "
					+ e);
			e.printStackTrace();
		}
		String errorMsg = "<font color=\"red\"><b>'"
				+ code
				+ "' cannot be found. Please modify the entry and try again or use the Search functionality.</b></font>";
		dwrInvestigatorUid = returnMap.get("UID") == null ? "" : returnMap.get(
				"UID").toString();
		if (dwrInvestigatorUid.equals(""))
			return errorMsg;

		dwrInvestigatorDetails = (String) returnMap.get("result");
		String versionCtrlNbr = (String) returnMap.get("versionCtrlNbr");
		getAttributeMap().put(identifier + "Uid",
				(dwrInvestigatorUid + "|" + versionCtrlNbr));
		getAttributeMap().put(identifier + "SearchResult",
				dwrInvestigatorDetails);
		sb.append(dwrInvestigatorUid).append("$$$$$").append(
				dwrInvestigatorDetails);
		return sb.toString();
	}
	
	public String getDwrOrganizationDetails(String code, String identifier) {
		if (code != null && code.trim().equals(""))
			return "<font color=\"red\"><b>Please enter a code and try again or use the Search functionality.</b></font>";
		WebContext ctx = WebContextFactory.get();
		HttpServletRequest req = ctx.getHttpServletRequest();
		Map<?, ?> returnMap = new HashMap<Object, Object>();
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
		dwrOrganizationUid = returnMap.get("UID") == null ? "" : returnMap.get(
				"UID").toString();
		if (dwrOrganizationUid.equals(""))
			return errorMsg;

		dwrOrganizationDetails = (String) returnMap.get("result");
		String versionCtrlNbr = (String) returnMap.get("versionCtrlNbr");
		getAttributeMap().put(identifier + "Uid",
				(dwrOrganizationUid + "|" + versionCtrlNbr));

		getAttributeMap().put(identifier + "SearchResult",
				dwrOrganizationDetails);
		sb.append(dwrOrganizationUid).append("$$$$$").append(
				dwrOrganizationDetails);
		return sb.toString();
	}


	public String getDwrInvestigatorDetailsByUid(String providerUid,
			String identifier) {

		WebContext ctx = WebContextFactory.get();
		HttpServletRequest req = ctx.getHttpServletRequest();
		Map<Object, Object> returnMap = new HashMap<Object, Object>();
		
		try {

			ArrayList<?> list = (ArrayList<?>) req.getSession().getAttribute(
					"ProviderSrchResults");

			if (providerUid != null && providerUid.trim().length() > 0)

				returnMap = NedssCodeLookupServlet.buildProviderResult(list,
						providerUid, req.getSession());

		} catch (Exception e) {
			dwrInvestigatorUid = "";
			dwrInvestigatorDetails = "";
			logger.error("Exception raised in getDwrInvestigatorDetailsByUid: "
					+ e);
		}
		dwrInvestigatorDetails = (String) returnMap.get("result");
		String versionCtrlNbr = (String) returnMap.get("versionCtrlNbr");
		if(versionCtrlNbr==null || versionCtrlNbr.trim().equals(""))
			versionCtrlNbr="1";
		if(providerUid!=null && providerUid.indexOf("|")==-1)
			getAttributeMap().put(identifier + "Uid",
				(providerUid + "|" + versionCtrlNbr));
		else
			getAttributeMap().put(identifier + "Uid",
					providerUid);
		getAttributeMap().put(identifier + "SearchResult",
				dwrInvestigatorDetails);	
		return dwrInvestigatorDetails;

	}

	public String getDwrOrganizationDetailsByUid(String organizationUid,
			String identifier) {

		WebContext ctx = WebContextFactory.get();
		HttpServletRequest req = ctx.getHttpServletRequest();
		Map<Object, Object> returnMap = new HashMap<Object, Object>();

		try {

			ArrayList<?> list = (ArrayList<?>) req.getSession().getAttribute(
					"OrganizationSrchResults");

			if (organizationUid != null && organizationUid.trim().length() > 0)

				returnMap = NedssCodeLookupServlet.buildOrganizationResult(
						list, organizationUid, req.getSession());
		} catch (Exception e) {
			dwrOrganizationDetails = "";
			dwrOrganizationUid = "";
			dwrOrganizationUid = organizationUid.toString();
			logger.error("Exception raised in getDwrOrganizationDetailsByUid: "
					+ e);

		}
		dwrOrganizationUid = organizationUid.toString();
		dwrOrganizationDetails = (String) returnMap.get("result");
		String versionCtrlNbr = (String) returnMap.get("versionCtrlNbr");

		getAttributeMap().put(identifier + "Uid",
				(dwrOrganizationUid + "|" + versionCtrlNbr));
		getAttributeMap().put(identifier + "SearchResult",
				dwrOrganizationDetails);
		return dwrOrganizationDetails;

	}

	
	public String getDwrPopulateInvestigatorByUid(String providerUid,
			String investigator) {

		WebContext ctx = WebContextFactory.get();
		HttpServletRequest req = ctx.getHttpServletRequest();
		Map<Object, Object> returnMap = new HashMap<Object, Object>();
		getAttributeMap().put(investigator + "Uid", providerUid);
		dwrInvestigatorDetails = (String) req.getSession().getAttribute(
				"oneProvider");
		getAttributeMap().put(investigator + "SearchResult",
				dwrInvestigatorDetails);
		return dwrInvestigatorDetails;
	}
	
	public String getDwrPopulateOrganizationByUid(String organizationUid,
			String identifier) {

		WebContext ctx = WebContextFactory.get();
		HttpServletRequest req = ctx.getHttpServletRequest();
		Map<Object, Object> returnMap = new HashMap<Object, Object>();
		getAttributeMap().put(identifier + "Uid", organizationUid);
		dwrInvestigatorDetails = (String) req.getSession().getAttribute(
				"oneOrganization");
		getAttributeMap().put(identifier + "SearchResult",
				dwrInvestigatorDetails);
		return dwrInvestigatorDetails;
	}
	
	public  ArrayList<Object> getDwrCityList(String stateCode){
		WebContext ctx = WebContextFactory.get();
		HttpServletRequest req = ctx.getHttpServletRequest();
		ArrayList<Object> cityList = new ArrayList<Object> ();
		try {
			cityList = CachedDropDowns.getAllCitiesList(stateCode);
			req.getSession().setAttribute("cityList", cityList);
    	} catch (Exception e) {
    		logger.error("Exception raised in BaseForm.getDwrCityList()/n" + e);
    	} 			
		return cityList;
	}
	public ArrayList<Object> getStateList(){
		ArrayList<Object> stateList = new ArrayList<Object> ();
		try {
			stateList = CachedDropDowns.getStateList();
		} catch (Exception e) {
			logger.error("Exception raised in BaseForm.getStateList()/n" + e);
		}
		return stateList;
	}
	public ArrayList<Object> getCountryList(){
		ArrayList<Object> countryList = new ArrayList<Object> ();
		try {
			countryList= CachedDropDowns.getCountryList();
		} catch (Exception e) {
			logger.error("Exception raised in BaseForm.getCountryList()/n" + e);
		}			
		 return countryList;
	}

	public ArrayList<Object> getDwrDefaultStateCounties() {
		ArrayList<Object> countryList = new ArrayList<Object> ();
		try {
			countryList = CachedDropDowns.getCountyCodes(PropertyUtil.getInstance().getNBS_STATE_CODE());
		} catch (Exception e) {
			logger.error("Exception raised in BaseForm.getCountryList()/n" + e);
		}
		return countryList;
	}	
	// TODO:This method needs to be revisited
	public void setCityList(String cityList) {
		this.cityList = cityList;
	}

	// TODO:This method needs to be revisited
	public String getCityList() {
		return cityList;
	}
	public Object getRaceList(String key) {
		ArrayList<Object> aList = new ArrayList<Object> ();
		aList = CachedDropDowns.getRaceCodes(key);
		return aList;
	}

	public ArrayList<Object> getJurisdictionList() {
		return CachedDropDowns.getJurisdictionList();
	}

	public ArrayList<Object> getJurisdictionListNoExport(){
		return CachedDropDowns.getJurisdictionNoExpList();
	}
	public ArrayList<Object> getProgramAreaList() {
		return CachedDropDowns.getProgramAreaList();
	}
	public ArrayList<Object> getIsoCountryList() {
		return CachedDropDowns.getIsoCountryList();
	}
	public String getExportJurisdictionList() {
		Collection<Object> coll = CachedDropDowns.getExportJurisdictionList();
		StringBuffer buff = new StringBuffer();
		if (coll != null && coll.iterator() != null) {
			java.util.Iterator<?> it = coll.iterator();
			while (it.hasNext()) {
				DropDownCodeDT dropdownDT = (DropDownCodeDT) it.next();
				buff.append(dropdownDT.getKey() + "|");

			}
		}
		return buff.toString();
	}
	
	public ArrayList<Object> getExportFacilityList(){
		return CachedDropDowns.getExportFacilityListForTransferOwnership();
	}
	public ArrayList<Object> getPrimaryOccupationCodes(){
		return CachedDropDowns.getPrimaryOccupationCodes();
	}	
    public String getCodeShortDescTxt(String code, String codeSetNm){
    	CachedDropDownValues cddV = new CachedDropDownValues();
    	String ret = cddV.getDescForCode( codeSetNm,code);
    	return ret;
    }

    public ArrayList<Object> getDropDownValues(String value){

		ArrayList<Object> srtValues = new ArrayList<Object> ();
		try{
			DropDownCodeDT ddDT = new DropDownCodeDT();
		HashMap<Object,Object> formMap = new HashMap<Object,Object>();
		formMap = (HashMap<Object,Object>) getFormFieldMap();

		Iterator<?> it = formMap.entrySet().iterator();
		while(it.hasNext()){
		Map.Entry<?,?> formfieldpair = (Map.Entry<?,?>)it.next();
		if(formfieldpair.getKey().equals(value)){
			FormField ff = (FormField)formfieldpair.getValue();
			if(ff.getFieldType().toString().equalsIgnoreCase("Coded")){
				srtValues = (ArrayList<Object>)getCodedValue(ff.getCodeSetNm());
				}
		    }

		}
		}catch (Exception e){
			logger.error("Exception raised in BaseForm getDropDownValues(" +value + ") " + e);
			e.printStackTrace();
		}
		return srtValues;

	}	
	
	public ArrayList<Object> getBirthCountry(){
		return CachedDropDowns.getBirthCountryCodes();
	}

	public ArrayList<Object> getLanguageCodes(){
		return CachedDropDowns.getLanguageCodes();
	}
	public ArrayList<Object> getDwrCounties() {
		return dwrCounties;
	}

	public void setDwrCounties(ArrayList<Object> dwrCounties) {
		this.dwrCounties = dwrCounties;
	}

	public ArrayList<Object> getNoDataArray() {
		return noDataArray;
	}

	public void setNoDataArray(ArrayList<Object> noDataArray) {
		this.noDataArray = noDataArray;
	}
	
    public String getDwrPlaceDetails(String code, String identifier)
    {
        if (StringUtils.isEmpty(code) )
            return "<font color=\"red\"><b>Please enter a code and try again or use the Search functionality.</b></font>";
        WebContext ctx = WebContextFactory.get();
        HttpServletRequest req = ctx.getHttpServletRequest();
        StringBuffer sb = new StringBuffer();
         
        String errorMsg = "<font color=\"red\"><b>'"
                + code
                + "' cannot be found. Please modify the entry and try again or use the Search functionality.</b></font>";
        
        PlaceSearchVO placeSearch = new PlaceSearchVO(); 
        placeSearch.setRootExtensionTxt(code);
        List placeList = PlaceUtil.getPlaceResults(placeSearch, req.getSession());
        if( placeList == null || placeList.size() == 0 )
        {
            return errorMsg;
        }
        try { 
        	PlaceVO p = (PlaceVO)placeList.get(0); 
        	String nm = p.getNm();
        	String ad = p.getLatestAddress().getEntitySearchAddress();
        	String te = p.getLatestPhone().getEntitySearchTelephone();
        	String t = p.getTypeDesc();
        	if(te == null ){
        		te = "";
        	} 
        	
        	String uIds = "";
        	
        	if(p.getLatestAddress().getLocatorUid()!=null && p.getLatestAddress().getLocatorUid()!=-1){
        		uIds = uIds+p.getLatestAddress().getLocatorUid();
        	}
        	
        	if(p.getLatestPhone().getLocatorUid()!=null && p.getLatestPhone().getLocatorUid()!=-1){
        		uIds=uIds+"^"+p.getLatestPhone().getLocatorUid();
        	}
        	
        	String u = p.getPlaceUid() + "^" + uIds;
        	getAttributeMap().put(identifier, (u + "|" + p.getThePlaceDT().getVersionCtrlNbr() ));
        	getAttributeMap().put(identifier + "SearchResult", ad + te );
        	sb.append(u).append("$$$$$").append(StringUtils.combine(new String[]{nm,ad,te}, ", ") ).append("$$$$$").append(t);
		}catch (Exception e){
			logger.error("Exception raised in BaseForm getDwrPlaceDetails " + e);
			e.printStackTrace();
		}
        return sb.toString();
    }
    
    public String getDwrPlaceDetailsByName(String name)
    {
    	WebContext ctx = WebContextFactory.get();
    	HttpServletRequest req = ctx.getHttpServletRequest();
        StringBuffer sb = new StringBuffer();
        PlaceSearchVO placeSearch = new PlaceSearchVO(); 
        placeSearch.setNm(name);
        placeSearch.setNmOperator("CT");
        try {
        	List placeList = PlaceUtil.getPlaceResults(placeSearch, req.getSession());
        	PlaceVO p = (PlaceVO)placeList.get(0); 
        	String nm = p.getNm();
        	String ad = p.getLatestAddress().getEntitySearchAddress();
        	String te = p.getLatestPhone().getEntitySearchTelephone();
        
        	String t = p.getTypeDesc();
        	if(te == null ){
        		te = "";
        	} 
        	String u = p.getPlaceUid() + "^" + p.getLatestAddress().getLocatorUid() + "^" + p.getLatestPhone().getLocatorUid();
        	sb.append(u).append("$$$$$").append(StringUtils.combine(new String[]{nm,ad,te}, ", ") ).append("$$$$$").append(t);
		}catch (Exception e){
			logger.error("Exception raised in BaseForm getDwrPlaceDetailsByName " + e);
			e.printStackTrace();
		}
        return sb.toString();
    }
    
    public String getDwrPlaceDetailsByUid(String placeUidStr)
    {
    	WebContext ctx = WebContextFactory.get();
    	HttpServletRequest req = ctx.getHttpServletRequest();
        StringBuffer sb = new StringBuffer();
        try {
        	Long placeUid = new Long(placeUidStr);
        	PlaceVO p = PlaceUtil.getThePlaceVO(placeUid, req.getSession());
        	
        	ArrayList<Object> pstList = new ArrayList<Object>();
        	ArrayList<Object> teleList = new ArrayList<Object>();
        	
        	Collection<Object>  elpColl = p.getTheEntityLocatorParticipationDTCollection();
        	Iterator elpIter = elpColl.iterator();
            while( elpIter.hasNext() )
            {
            	EntityLocatorParticipationDT theELPDT = (EntityLocatorParticipationDT)elpIter.next();
            	if (NEDSSConstants.POSTAL.equalsIgnoreCase(theELPDT.getClassCd())){
            		pstList.add(theELPDT);
            	}else if (NEDSSConstants.TELE.equalsIgnoreCase(theELPDT.getClassCd())){
            		teleList.add(theELPDT);
            	}
            	
            }
            
            //Sort list based on most recent asOfDate
            
            Collections.sort( pstList, new Comparator()
	        {
	        public int compare( Object a, Object b )
	           {
	            return((Timestamp) ((EntityLocatorParticipationDT)b).getAsOfDate()).compareTo((Timestamp) ((EntityLocatorParticipationDT) a).getAsOfDate());
	           }
	        } );
            
            Collections.sort( teleList, new Comparator()
	        {
	        public int compare( Object a, Object b )
	           {
	            return((Timestamp) ((EntityLocatorParticipationDT)b).getAsOfDate()).compareTo((Timestamp) ((EntityLocatorParticipationDT) a).getAsOfDate());
	           }
	        } );
            
            
            EntityLocatorParticipationDT elpDTforPostal = null;
            PostalLocatorDT plDT = null;
            String ad = "";
            if(pstList!=null && pstList.size()>0){
            	elpDTforPostal = (EntityLocatorParticipationDT) pstList.get(0);
            	plDT = (PostalLocatorDT)elpDTforPostal.getThePostalLocatorDT();
            	ad = PlaceUtil.getEntitySearchAddress(plDT);
            }
            
            EntityLocatorParticipationDT elpDTforTele = null;
            TeleLocatorDT tlDT = null;
            String te = "";
            if(teleList!=null && teleList.size()>0){
            	elpDTforTele = (EntityLocatorParticipationDT) teleList.get(0);
            	tlDT = (TeleLocatorDT)elpDTforTele.getTheTeleLocatorDT();
            	te = PlaceUtil.getEntitySearchTelephone(tlDT);
            }
            
        	String nm = p.getNm();
        
        	String t = p.getTypeDesc();
        	
        	String uIDs = "";
        	
        	if(plDT!=null){
        		uIDs = uIDs+plDT.getPostalLocatorUid();
        	}
        	
        	if(tlDT!=null){
        		uIDs = uIDs+"^"+tlDT.getTeleLocatorUid();
        	}
        	
        	String u = p.getPlaceUid() + "^" + uIDs;
        	sb.append(u).append("$$$$$").append(StringUtils.combine(new String[]{nm,ad,te}, ", ") ).append("$$$$$").append(t);
		}catch (Exception e){
			logger.error("Exception raised in BaseForm getDwrPlaceDetailsByUid " + e);
			e.printStackTrace();
		}
        return sb.toString();
    }
    
    /*
     * dwr Call to get the list of Interviews associated with the investigation
     * Because we don't have access to the clientVO we expect the selected value
     * (if any) to be placed in the attribute map on edit.
     * @param Key is the field id of the field CON143- currently not used.
     * @returns dropdown list for the Std Contact Named field.
     */
	public Object getNamedInterviews(String key) {
		ArrayList<Object> namedDuringList = new ArrayList<Object>();
		try {
		 DropDownCodeDT dDownDT = new DropDownCodeDT();
	     dDownDT.setKey(""); dDownDT.setValue("");
	     namedDuringList.add(dDownDT); //add blank line
	     dDownDT = new DropDownCodeDT();
         dDownDT.setKey(CTConstants.StdInitiatedWithoutInterviewKey); dDownDT.setValue(CTConstants.StdInitiatedWithoutInterviewValue);
         namedDuringList.add(dDownDT);
         Collection<Object> theInterviewSummary =(ArrayList<Object>) attributeMap.get("InterviewList");
         if(theInterviewSummary != null) {
        	    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
				Iterator<Object> ite = theInterviewSummary.iterator();
				while (ite.hasNext()) {
					InterviewSummaryDT ixsDT = (InterviewSummaryDT) ite.next();
					if (ixsDT.getInterviewerFullName() != null && ixsDT.getInterviewUid() != null) {
						String keyStr = ixsDT.getInterviewUid().toString();
						String interviewerStr = ixsDT.getInterviewerFullName();
				        Date date = null;
				        if (ixsDT.getInterviewDate() != null)
				        {
				            date = new Date(ixsDT.getInterviewDate().getTime());
				        }
				        if (date != null)
				        {
				            interviewerStr = interviewerStr.concat(" - " + formatter.format(date));
				        }
				        dDownDT = new DropDownCodeDT();
				        dDownDT.setKey(keyStr); dDownDT.setValue(interviewerStr);
				        namedDuringList.add(dDownDT);
					}
					
				}
				String selectedInterviewUid = (String)attributeMap.get(CTConstants.StdInterviewSelectedKey);
				if (selectedInterviewUid != null && !selectedInterviewUid.isEmpty()) {
			        dDownDT = new DropDownCodeDT();
			        dDownDT.setKey("selected"); dDownDT.setValue(selectedInterviewUid);
			        namedDuringList.add(dDownDT);
				}
	         }
		} catch (Exception ex) {
			logger.error("Error while DWR Request in getNamedInterviews: " + ex.toString());	
		}
		return namedDuringList;
	}  
    /*
     * dwr Call to get the selected Interviews associated with the contact
     * Because we don't have access to the clientVO we expect the selected value
     * (if any) to be placed in the attribute map on view.
     * @param Key is the field id of the field CON143- currently not used.
     * @returns String of Investigator and Date
     */
	public String getTheNamedInterview(String key) {
		String selectedInterviewUid = (String)attributeMap.get(CTConstants.StdInterviewSelectedKey);
		if (selectedInterviewUid == null)
			return "";
		if (selectedInterviewUid.equals(CTConstants.StdInitiatedWithoutInterviewKey))
			return CTConstants.StdInitiatedWithoutInterviewValue;
		try {
		WebContext ctx = WebContextFactory.get();
		HttpServletRequest request = ctx.getHttpServletRequest();
         Collection<Object> theInterviewSummary = (ArrayList<Object>)  NBSContext.retrieve(request.getSession(), NBSConstantUtil.DSInterviewList);
         if(theInterviewSummary != null) {
        	    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
				Iterator<Object> ite = theInterviewSummary.iterator();
				while (ite.hasNext()) {
					InterviewSummaryDT ixsDT = (InterviewSummaryDT) ite.next();
					if (selectedInterviewUid.equals(ixsDT.getInterviewUid().toString())) {
						if (ixsDT.getInterviewerFullName() != null && ixsDT.getInterviewUid() != null) {
							String interviewerStr = ixsDT.getInterviewerFullName();
							Date date = null;
							if (ixsDT.getInterviewDate() != null)
							{
								date = new Date(ixsDT.getInterviewDate().getTime());
							}
				        if (date != null)
				        {
				            interviewerStr = interviewerStr.concat(" - " + formatter.format(date));
				        }
				        return(interviewerStr);
					  } //not null
				    } //selected equal 
				} //has next
           } //Interview Summary not null
		} catch (Exception ex) {
			logger.error("Error while DWR Request in getTheNamedInterview: " + ex.toString());	
		}
		return ""; //could have been deleted
	}
	
	public String getDwrContactPatientDetailsByUid(String patientUid, 
			String investigationUid,
			String identifier,
			String investigatorId) {

		WebContext ctx = WebContextFactory.get();
		HttpServletRequest req = ctx.getHttpServletRequest();
		String contactPatientDetails = "";
		
		try {
			contactPatientDetails  = (String) req.getSession().getAttribute(
					"ContactPatientDetails");

		} catch (Exception e) {
			dwrInvestigatorUid = "";
			dwrInvestigatorDetails = "";
			logger.error("Exception raised in getDwrContactPatientDetailsByUid: "
					+ e);
		}
		try {
			if (contactPatientDetails != null)
				getAttributeMap().put(identifier + "SearchResult",
						contactPatientDetails);	
			if (patientUid != null)
				getAttributeMap().put(CTConstants.StdThirdParthEntityUid, patientUid);
			if (investigationUid != null)
				getAttributeMap().put(CTConstants.StdThirdParthEntityPhcUid, investigationUid);
			if (investigatorId != null)
				getAttributeMap().put(CTConstants.StdThirdParthInvestigatorLocalId, investigatorId);
		} catch (Exception e) {
			logger.error("Exception raised in getDwrContactPatientDetailsByUid2: "
					+ e);
		}
		return contactPatientDetails;

	}	
	
	
	public static JsonObject toJson(Object obj, String[] propNames)
    {
        JsonObject jobj = new JsonObject();
        try
        {
            for (String name : propNames)
            {

                PropertyDescriptor pd = PropertyUtils.getPropertyDescriptor(obj, name);
                if ("java.sql.Timestamp".equalsIgnoreCase(pd.getPropertyType().getName()))
                {
                    Timestamp t = (Timestamp) PropertyUtils.getProperty(obj, name);
                    jobj.addProperty(name, StringUtils.formatDate(t) ); 
                }
                else
                {
                    jobj.addProperty(name, BeanUtils.getProperty(obj, name));
                } 
            }
        }
        catch (Exception e)
        {
        	logger.error("Exception raised in BaseForm toJson() " + e);
        }

        return jobj;
    }
    
	public ArrayList<Object> getfilteredStatesByCountry(String contryCode){
		ArrayList<Object> filteredStatesByCountry = new ArrayList<Object> ();
		try {
			filteredStatesByCountry = CachedDropDowns.getfilteredStatesByCountry(contryCode);
		} catch (Exception e)  {
	        	logger.error("Exception raised in getfilteredStatesByCountry() " + e);
	    }
		return filteredStatesByCountry;
	}
	
	public boolean isSyphilisCondition(String cd) {
		if (cd == null || cd.equals(""))
			return false;
		ArrayList<Object> syphilisConditionList = new ArrayList<Object>();
		try {
		syphilisConditionList = new CachedDropDownValues()
				.getCodedValuesList("STD_SYPHILIS_CODE_LIST");
		} catch (Exception e)  {
        	logger.error("Exception raised in isSyphilisCondition() " + e);
		}		
		Iterator<Object> condIter = syphilisConditionList.iterator();
		while (condIter.hasNext()) {
			DropDownCodeDT dropDownDT = (DropDownCodeDT) condIter.next();
			if (cd.equalsIgnoreCase(dropDownDT.getKey()))
				return true;
		}
		return false;
	}

	public String getDwrPopulateResultedTestByUid(String description,
			String testCode) {
		String descriptionWithCode=description+" ("+testCode+")";
		getAttributeMap().put("ResultedTestDescriptionWithCode", descriptionWithCode);
		getAttributeMap().put("ResultDescription", description);
		getAttributeMap().put("ResultCode", testCode);
		return descriptionWithCode;
	}
	public String getDwrPopulateCodeResultByUid(String description,
			String testCode) {
		getAttributeMap().put("TestDescription", description);
		getAttributeMap().put("TestCode", testCode);
		return description;
	}
	public Map<Object, Object> getAttributeMap2() {
		return attributeMap2;
	}
	public void setAttributeMap2(Map<Object, Object> attributeMap2) {
		this.attributeMap2 = attributeMap2;
	}
	public Map<Object, Object> getSecurityMap2() {
		return securityMap2;
	}
	public void setSecurityMap2(Map<Object, Object> securityMap2) {
		this.securityMap2 = securityMap2;
	}
	public String getMode() {
		if(mode == null)  return null;
		else return HTMLEncoder.encodeHtml(mode);
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	
	public Map<Object, Object> getDwrPatientDetails(String mprUid) {
		Map<Object, Object> answerMap = new HashMap<Object, Object>();
		if (mprUid == null || mprUid.trim().length() == 0)
			return answerMap;
		Long personUid = new Long(mprUid);
		WebContext ctx = WebContextFactory.get();
		HttpServletRequest req = ctx.getHttpServletRequest();
		PersonVO personVO = null;
		ClientVO clientVO = new ClientVO();
		clientVO.setAnswerMap(answerMap);
		try {
			personVO = PageLoadUtil.findMasterPatientRecord(personUid,
					req.getSession());
			if (personVO != null ) {
				String uidSt = personVO.getThePersonDT().getPersonUid()
						.toString()
						+ "|"
						+ personVO.getThePersonDT().getVersionCtrlNbr()
								.toString();
				this.getAttributeMap().put(PageConstants.LAB_PATIENT + "Uid",
						uidSt);
			}
			ClientUtil.setPatientInformation(NEDSSConstants.CREATE_LOAD_ACTION,
					personVO, clientVO, req, "LAB");
			answerMap.put("americanIndianAlskanRace", clientVO.getAmericanIndianAlskanRace()+"");
			answerMap.put("asianRace", clientVO.getAsianRace()+"");
			answerMap.put("africanAmericanRace", clientVO.getAfricanAmericanRace()+"");
			answerMap.put("hawaiianRace", clientVO.getHawaiianRace()+"");
			answerMap.put("whiteRace", clientVO.getWhiteRace()+"");
			answerMap.put("otherRace", clientVO.getOtherRace()+"");
			answerMap.put("refusedToAnswer", clientVO.getRefusedToAnswer()+"");
			answerMap.put("notAsked", clientVO.getNotAsked()+"");
			answerMap.put("unKnownRace", clientVO.getUnKnownRace()+"");
			ClientUtil.setPersonIdDetails(personVO, this);
		} catch (Exception ex) {
			logger.error("Exception while get patient information for patient entity search");
			ex.printStackTrace(); 
		}
		return clientVO.getAnswerMap();
	}
	
	public Map<Object, Object> clearDwrPatientDetails() {
		Map<Object, Object> answerMap = new HashMap<Object, Object>();
		try {
				this.getAttributeMap().remove(PageConstants.LAB_PATIENT + "Uid");
				ClientVO clientVO = new ClientVO();
				clientVO.setAnswerMap(answerMap);
				//ClientVO clientVO = new ClientVO();
				//clientVO.setAnswerMap(answerMap);
			 
			//this.getBatchEntryMap().clear();;
		} catch (Exception ex) {
			logger.error("Exception while get patient information for patient entity search");
			ex.printStackTrace();
		}
		return answerMap;
	}
	
	public String getDwrOrganizationUid() {
		return dwrOrganizationUid;
	}
	public void setDwrOrganizationUid(String dwrOrganizationUid) {
		this.dwrOrganizationUid = dwrOrganizationUid;
	}
	public String getActionModeParent() {
		return actionModeParent;
	}
	public void setActionModeParent(String actionModeParent) {
		this.actionModeParent = actionModeParent;
	}
	
	

	/**
	 * showHideOrganism: based on the resulted test selected in the new lab report page, the organism field is shown/hidden
	 * @throws NEDSSAppException 
	 */
	public boolean showHideOrganism(String progAreaCd, Long organizationUID, String testNameCondition, int key, int positionKeyInTable) throws NEDSSAppException{
		
		boolean labIndicator;
		PageLoadUtil pageLoadUtil  = new PageLoadUtil();
		try {
			WebContext ctx = WebContextFactory.get();
			HttpServletRequest request = ctx.getHttpServletRequest();
			labIndicator = false;
			//If we are on view lab report, the code is not in the view, we need to get the value from the batchEntryMap
			if((testNameCondition==null || testNameCondition.equalsIgnoreCase("")
					&& this.getBatchEntryMap().get(PageConstants.RESULTED_TEST_BATCH_CONTAINER)!=null
					&& positionKeyInTable>0)){
				
				int length = this.getBatchEntryMap().get(PageConstants.RESULTED_TEST_BATCH_CONTAINER).size();
				
				if(positionKeyInTable-1<length)
					testNameCondition=this.getBatchEntryMap().get(PageConstants.RESULTED_TEST_BATCH_CONTAINER).get(positionKeyInTable-1).getAnswerMap().get("NBS_LAB220CodeId"); 
			}
			  
			
			if((testNameCondition==null || testNameCondition.equalsIgnoreCase("")
					&& this.getBatchEntryMap().get(PageConstants.RESULTED_TEST_BATCH_CONTAINER_MORB)!=null
					&& positionKeyInTable>0)){
				
				int length = this.getBatchEntryMap().get(PageConstants.RESULTED_TEST_BATCH_CONTAINER_MORB).size();
				
				if(positionKeyInTable-1<length)
					testNameCondition=this.getBatchEntryMap().get(PageConstants.RESULTED_TEST_BATCH_CONTAINER_MORB).get(positionKeyInTable-1).getAnswerMap().get("NBS_LAB220CodeId"); 
			}
			
			  
			
			if(organizationUID==null || (organizationUID!=null && organizationUID==-1)){//View Lab Report, the Reporting Facility UID is not in the view, the value needs to be read from the session:
				String labUid=getReportingFacilityUid();
				
				 if(labUid!=null && labUid.indexOf("|")!=-1)
					 organizationUID = Long.parseLong(labUid.substring(0,labUid.indexOf("|")));
				 if(labUid!=null && labUid.indexOf("|")==-1)
					 organizationUID = Long.parseLong(labUid);
			}
				
			
			if(organizationUID!=null && organizationUID !=-1){
			 String labClia = "";
			
			 String uid = organizationUID+"";
			 
			 if(organizationUID!=null){
				 labClia = pageLoadUtil.getCliaValue(uid, request.getSession());
			     
			     if (labClia == null || labClia == "") {
			    	 labClia = NEDSSConstants.DEFAULT;
			     }
			 
			 labIndicator = pageLoadUtil.organismRequiredIndicator(testNameCondition, labClia, organizationUID, progAreaCd, request.getSession());
			
			 }
			 
			}
		} catch (NumberFormatException e) {
			logger.fatal(e.getMessage(), e);
			throw new NEDSSAppException(e.getMessage(), e);
		}/* catch (NEDSSAppException e) {
			logger.fatal(e.getMessage(), e);
			throw e;
		}*/ catch (Exception e) {
			logger.fatal(e.getMessage(), e);
			throw new NEDSSAppException(e.getMessage(), e);
		}
		 return labIndicator;
	
	}
	
	
	
	
	/**
	 * showHideOrganism: based on the resulted test selected in the new lab report page, the organism field is shown/hidden
	 */
	public String showHideOrganismByID(String progAreaCd, Long organizationUID, String testNameCondition, String elementId) throws NEDSSAppException{
		PageLoadUtil pageLoadUtil  = new PageLoadUtil();
		WebContext ctx = WebContextFactory.get();
		HttpServletRequest request = ctx.getHttpServletRequest();
		String 	valueToReturn="0";
		boolean labIndicator=false;
				
		if(elementId!=null && !elementId.startsWith("editNBS_UI_"))
			elementId = elementId.replace("edit", "editNBS_UI_");//This is in order to apply changes for ND-17070 when NBS_UI scripts have not been applied to 6.0.4
		if(elementId!=null && !elementId.startsWith("viewNBS_UI_"))
			elementId = elementId.replace("view", "viewNBS_UI_");//This is in order to apply changes for ND-17070 when NBS_UI scripts have not been applied to 6.0.4
		
		//If we are on view lab report, the code is not in the view, we need to get the value from the batchEntryMap
		int id=-1;
		if (elementId != null)
			if(elementId.indexOf("NBS_UI_RESULTED_TEST_CONTAINER")!=-1)
			id = Integer.valueOf(
					elementId.substring(
							elementId.indexOf("NBS_UI_RESULTED_TEST_CONTAINER")+30,
							elementId.length())).intValue();
						
			else
				id = Integer.valueOf(
						elementId.substring(
								elementId.indexOf("NBS_UI_29")+9,
								elementId.length())).intValue();
		
		
		if((testNameCondition==null || testNameCondition.equalsIgnoreCase(""))
				&& this.getBatchEntryMap().get("NBS_UI_RESULTED_TEST_CONTAINER")!=null
				&& id>=0){
			ArrayList<BatchEntry> bList = this.getBatchEntryMap().get("NBS_UI_RESULTED_TEST_CONTAINER");
			BatchEntry testBatchEntry = null;
			for(BatchEntry be: bList){
				if(be.getId()==id){
					testBatchEntry = be;
				break;
				}
			}
			if(testBatchEntry!=null)
				testNameCondition=testBatchEntry.getAnswerMap().get("NBS_LAB220CodeId"); 
		}else
			if((testNameCondition==null || testNameCondition.equalsIgnoreCase(""))
					&& this.getBatchEntryMap().get("RESULTED_TEST_CONTAINER")!=null
					&& id>=0){
				ArrayList<BatchEntry> bList = this.getBatchEntryMap().get("RESULTED_TEST_CONTAINER");
				BatchEntry testBatchEntry = null;
				for(BatchEntry be: bList){
					if(be.getId()==id){
						testBatchEntry = be;
					break;
					}
				}
				if(testBatchEntry!=null)
					testNameCondition=testBatchEntry.getAnswerMap().get("NBS_LAB220CodeId"); 
			}
			else
			if((testNameCondition==null || testNameCondition.equalsIgnoreCase(""))
					&& this.getBatchEntryMap().get("NBS_UI_29")!=null
					&& id>=0){
				ArrayList<BatchEntry> bList = this.getBatchEntryMap().get("NBS_UI_29");
				BatchEntry testBatchEntry = null;
				for(BatchEntry be: bList){
					if(be.getId()==id){
						testBatchEntry = be;
					break;
					}
				}
				if(testBatchEntry!=null)
					testNameCondition=testBatchEntry.getAnswerMap().get("NBS_LAB220CodeId"); 
			}
			  
		  
		  
		
		if(organizationUID==null || (organizationUID!=null && organizationUID==-1)){//View Lab Report, the Reporting Facility UID is not in the view, the value needs to be read from the session:
			String labUid=getReportingFacilityUid();
			
			 if(labUid!=null && labUid.indexOf("|")!=-1)
				 organizationUID = Long.parseLong(labUid.substring(0,labUid.indexOf("|")));
		}
			
		
		if(organizationUID!=null && organizationUID !=-1){
		 String labClia = "";
	    
		 String uid = organizationUID+"";
		 
		 if(organizationUID!=null){
			 labClia = pageLoadUtil.getCliaValue(uid, request.getSession());
		     
		     if (labClia == null || labClia == "") {
		    	 labClia = NEDSSConstants.DEFAULT;
		     }
	     
		 labIndicator = pageLoadUtil.organismRequiredIndicator(testNameCondition, labClia, organizationUID, progAreaCd, request.getSession());
		
		 }
		 
		}
		
		if(labIndicator==true)
			valueToReturn="1";
		else
			valueToReturn="2";
		 return valueToReturn;
	
	}
	
		
	
	public boolean showHideOrganismByTestCode(String progAreaCd,
			String organizationUID, String testCode) {

		WebContext ctx = WebContextFactory.get();
		HttpServletRequest request = ctx.getHttpServletRequest();
		boolean labIndicator = false;
		if(organizationUID==null || organizationUID.trim().equals(""))
			organizationUID   = (String) attributeMap.get("NBS_LAB365Uid");
		if (organizationUID != null && organizationUID.indexOf("|") != -1)
			organizationUID = organizationUID.substring(0,
					organizationUID.indexOf("|"));
		if (organizationUID != null) {
			String labClia = "";

			if (organizationUID != null) {
				labClia = PageLoadUtil.getCliaValue(organizationUID,
						request.getSession());

				if (labClia == null || labClia == "") {
					labClia = NEDSSConstants.DEFAULT;
				}
				labIndicator = PageLoadUtil.organismRequiredIndicator(testCode,
						labClia, new Long(organizationUID), progAreaCd,
						request.getSession());
			}
		}
		return labIndicator;
	}

	
	/**
	 * getCodedResultData: returns the values to insert in Coded Result dropdown in Lab Report Page, under Resulted Test
	 * @return
	 */
	public ArrayList<Object> getCodedResultData (){
			
			
			ArrayList<Object> codedResultData  = new ArrayList<Object>();
			
			PageLoadUtil.getCodedResultList(codedResultData);
			
			
			return codedResultData;
		}
	
	
	
	/**
	 * getOrganismData: calls the getOrganismList from PageLoadUtil in order to get an arrayList of dropdownDT with each of the organism
	 * @return
	 */
	public ArrayList<Object> getOrganismData (){
		
		
		ArrayList<Object> organismData  = new ArrayList<Object>();
		
		PageLoadUtil.getOrganismList(organismData);
		
		
		return organismData;
	}
	

	/**
	 * getReportingFacilityUid: Reporting facility in Lab is coming from NBS_LAB365 and from morb is coming from NBS517
	 * @return
	 */
	
	public String getReportingFacilityUid(){
		
		String labUid=(String)this.getAttributeMap().get("NBS_LAB365Uid");
		
		if(labUid=="" || labUid ==null)
			 labUid=(String)this.getAttributeMap().get("NBS517");
		
		return labUid;
	}
	
	
	/**
	 * deleteSubFormFromSession: delete the subFormPage from session which key is the key received as a parameter
	 * @param key
	 * @throws NEDSSAppException
	 */
	
	public void deleteSubFormFromSession(String key) throws NEDSSAppException {
		
		PageLoadUtil.deleteSubFormFromSession(key);
	}
	
	
	public String getDisableSubmitBeforePageLoadsFlag() {
		return PropertyUtil.getInstance().getDisableSubmitBeforePageLoadsFlag();
	}
	
	public String isTbConditionCode(String conditionCode){
		
		String isTB = "false";
		
		if(PropertyUtil.isTBCode(conditionCode))
			isTB="true";
		
		return isTB;
	}
	
	/**
	 * isStateCaseNumberUnique: check if state case number (INV173) is unique across pages in the system. This validation will be only executed if the
	 * condition code is the conditions indicated under the column nbs_configuration..config_key = TB_CONDITION_CODES
	 * plus the condition code for new LTBI (505282).
	 * This method has been created to implement the Jira ticket ND-27276
	 * it returns true if it is either unique or not a TB condition code, so we don't validate the uniqueness of State Case Number
	 * @param stateCaseNumber
	 * @param conditionCd
	 * @return
	 */
	
	public boolean isStateCaseNumberUnique(String stateCaseNumber, String conditionCd){
		
		WebContext ctx = WebContextFactory.get();
		HttpServletRequest req = ctx.getHttpServletRequest();
		String publicHealthCaseUid = "";
		try{
			publicHealthCaseUid =  (String)NBSContext.retrieve(req.getSession(),NBSConstantUtil.DSInvestigationUid);
		}catch (Exception e){
			logger.info("Unable to read DSInvestigationUid because of create investigation event: "+e.getMessage());
		}
		boolean isUnique=true;
		//This validation is only applied to the condition codes under nbs_configuration.config_key = TB_CONDITION_CODES (currently 102201,502582) and 11020
		if(PropertyUtil.isTBCode(conditionCd) || (conditionCd!=null && conditionCd.equalsIgnoreCase("10220"))){
	
		
			
			PageTBHelper pageTBHelper = new PageTBHelper();
			isUnique = pageTBHelper.isCaseNumberUnique(stateCaseNumber, publicHealthCaseUid);

			}
		
		return isUnique;
		}

		public String getValueFromAttributeMap(String key) {
	 		return (String) attributeMap.get(key);
		}
	
		@SuppressWarnings("unchecked")
		public String checkForExistingInvestigations() {
			Long observationUid = null;
			String ObservationLocalId = null;
			String message = "";
			WebContext ctx = WebContextFactory.get();
			HttpServletRequest req = ctx.getHttpServletRequest();
			try {
				// this is from morb and for generic investigation only
				TreeMap<Object, Object> DSMorbMap = (TreeMap<Object, Object>) NBSContext.retrieve(req.getSession(),
						NBSConstantUtil.DSMorbMap);
				Object sObservationUID = NBSContext.retrieve(req.getSession(), NBSConstantUtil.DSObservationUID);
				observationUid = new Long(sObservationUID.toString());
				if (observationUid != null) {
					ObservationLocalId = DWRUtil.checkMorbForCaseAssociations(observationUid, req);
					if (ObservationLocalId != null)
						message = "An investigation, " + ObservationLocalId
								+ ", has already been created for this morbidity report. Another investigation cannot be created for this morbidity report.";
				}
			} catch (Exception ex) {
				logger.debug("DSMorbMap is null, create investigation context is not from morbidity report");
			}
			return message;
		}
		
		
		/**
		 * checkForExistingNotificationsByPublicHealthCaseUid: returns if there's any notification associated to the investigation.
		 * It is used from a DWR call.
		 * @param publicHealthCaseUid
		 * @return
		 */
		public boolean checkForExistingNotificationsByPublicHealthCaseUid(String publicHealthCaseUid){
			boolean isNotificationExist=false;
			try{
				WebContext context = WebContextFactory.get();
				HttpServletRequest request = context.getHttpServletRequest();
				
				String sBeanJndiName = JNDINames.PAGE_PROXY_EJB;
		        String sMethod = "checkForExistingNotificationsByPublicHealthUid";
		        
		        Long uid = null;
		        if(publicHealthCaseUid!=null){
		        	uid = Long.parseLong(publicHealthCaseUid);
		        }
		        Object[] oParams = new Object[] {uid};
		        Object obj = CallProxyEJB.callProxyEJB(oParams,sBeanJndiName,sMethod,request.getSession());
		        isNotificationExist = (boolean) obj;

			}catch(Exception ex){
				logger.error("Error in calling the checkForExistingNotificationsByPublicHealthCaseUid, actUidStr: "+publicHealthCaseUid+", Exception: "+ex.getMessage(),ex);
			}
			return isNotificationExist;
		}
}