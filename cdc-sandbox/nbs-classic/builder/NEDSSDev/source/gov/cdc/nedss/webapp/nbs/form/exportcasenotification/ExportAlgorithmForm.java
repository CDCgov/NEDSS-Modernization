package gov.cdc.nedss.webapp.nbs.form.exportcasenotification;

import gov.cdc.nedss.systemservice.ejb.casenotificationejb.dt.ExportAlgorithmDT;
import gov.cdc.nedss.systemservice.ejb.casenotificationejb.dt.ExportTriggerDT;
import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.webapp.nbs.action.exportcasenotification.util.CaseNotificationUtil;
import gov.cdc.nedss.webapp.nbs.action.exportcasenotification.util.ExportCaseNotificationConstants;
import gov.cdc.nedss.webapp.nbs.form.util.BaseForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;

public class ExportAlgorithmForm extends BaseForm{
	
  
	ArrayList<?> manageList = new ArrayList<Object> ();
	Object selection = new Object();
	ExportAlgorithmDT exADT = new ExportAlgorithmDT(); 
	private ArrayList<Object> receivingSystemList;
	private ArrayList<Object> statusList;
	ExportAlgorithmDT oldExADT = new ExportAlgorithmDT(); 	
	ExportTriggerDT exTrDT = new ExportTriggerDT();
	private String returnToLink;
	private Map<Object,Object> exTrDTMap =  new HashMap<Object,Object>();
	ArrayList<Object>  triggerFieldList = new ArrayList<Object> ();
	ArrayList<?>  triggerFields = new ArrayList<Object> ();
	ArrayList<Object> triggerLogicList = new ArrayList<Object> ();
	Map<Object,Object> searchMap = new HashMap<Object,Object>();
	CaseNotificationUtil cnUtil = new CaseNotificationUtil();
	ArrayList<Object> triggerFilterList = new ArrayList<Object> ();
	ArrayList<Object> levOfRevList = new ArrayList<Object> ();
	
	public String getReturnToLink() {
		return returnToLink;
	}
	public void setReturnToLink(String returnToLink) {
		this.returnToLink = returnToLink;
	}
	public ExportAlgorithmDT getOldExADT() {
		return oldExADT;
	}
	public void setOldExADT(ExportAlgorithmDT oldExADT) {
		this.oldExADT = oldExADT;
	}
	public ExportAlgorithmDT getExADT() {
		return exADT;
	}
	public void setExADT(ExportAlgorithmDT exADT) {
		this.exADT = exADT;
	}
	public void setStatusList(ArrayList<Object>  statusList) {
		this.statusList = statusList;
	}
	public ArrayList<?> getManageList() {
		return manageList;
	}
	public void setManageList(ArrayList<?>  manageList) {
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
		this.triggerFieldList = new ArrayList<Object> ();
		this.setExTrDTMap(new HashMap<Object,Object>());
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
	public ArrayList<Object> getReceivingSystemList() {
		return CachedDropDowns.getExportFacilityListForTransferOwnership();
	}
	
	public ArrayList<Object> getStatusList() {
		ArrayList<Object> statusList = new ArrayList<Object> ();
		DropDownCodeDT cdDT1 = new DropDownCodeDT();
		cdDT1.setKey(NEDSSConstants.STATUS_ACTIVE);
		cdDT1.setValue(NEDSSConstants.RECORD_STATUS_ACTIVE);
		statusList.add(cdDT1);
		DropDownCodeDT cdDT2 = new DropDownCodeDT();
		cdDT2.setKey(NEDSSConstants.STATUS_INACTIVE);
		cdDT2.setValue(NEDSSConstants.RECORD_STATUS_INACTIVE);
		statusList.add(cdDT2);
		return statusList;
	}	
	
	public ArrayList<Object> getDocumentList(){
		ArrayList<Object> docList = new ArrayList<Object> ();
		DropDownCodeDT cdDT1 = new DropDownCodeDT();
		cdDT1.setKey(ExportCaseNotificationConstants.CASE_REPORT_CD);
		cdDT1.setValue(ExportCaseNotificationConstants.CASE_REPORT_DESC);
		docList.add(cdDT1);
		return docList;
		
	}
	
	
	public ExportTriggerDT getExTrDT() {
		return exTrDT;
	}
	public void setExTrDT(ExportTriggerDT exTrDT) {
		if(exTrDT.getId() == -1)
			exTrDT.setId(getNextId());
		exTrDT.setTriggerFieldDesc(this.getDescFromList(this.triggerFieldList,exTrDT.getTriggerField()));
		exTrDT.setTriggerLogicDesc(this.getDescFromList(this.triggerLogicList,exTrDT.getTriggerLogic()));
		exTrDT.setTriggerFilterDesc(this.getDescFromList(this.triggerFilterList,exTrDT.getTriggerFilter()));
		this.exTrDT = exTrDT;
		exTrDTMap.put(String.valueOf(exTrDT.getId()), exTrDT);
	}
	
    public Collection<Object>  getExTrDTset() {
        return exTrDTMap.values();
    }

    public Map<Object,Object> getExTrDTMap(){
    	//this.exTrDTMap.put(String.valueOf(getExTrDT().getId()), getExTrDT());
		return exTrDTMap;
	}
	public void deleteTrigger(String id) {
		exTrDTMap.remove(id);
    }
	public void setExTrDTMap(Map<Object,Object> exTrDTMap) {
		this.exTrDTMap = exTrDTMap;
	}
	public ArrayList<Object> getTriggerFieldList() {
		return triggerFieldList;
	}
	public void setTriggerFieldList(ArrayList<Object>  triggerFieldList) {
		this.triggerFieldList = triggerFieldList;
	}
	public void setReceivingSystemList(ArrayList<Object>  receivingSystemList) {
		this.receivingSystemList = receivingSystemList;
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
	
	public ArrayList<Object> getTrigerLogicFromTriggerField(String value){
		String tFilter = this.getSearchCriteria("TRIGGERFIELD");
		ArrayList<Object> arrTriggerList = new ArrayList<Object> (); 
			if(value !=null)
				arrTriggerList=  cnUtil.getTriggerList(new Long(value));
			setTriggerLogicList(arrTriggerList);
			return arrTriggerList;
		
	}
	/**
	 * Set the trigger Filter values based on the 
	 * @param value
	 * @return
	 */
	public ArrayList<Object> getTrigerFilterValues(String value){
		
		ArrayList<Object> triggerFilterList = new ArrayList<Object> (); 
		WebContext ctx = WebContextFactory.get();
		HttpServletRequest req = ctx.getHttpServletRequest();
		HttpSession session = req.getSession();
		try{
		if(value !=null)
			triggerFilterList=  cnUtil.getFilterSRTValues(new Long(value), session);
		}catch (Exception e){
			e.printStackTrace();
			//throw new Exception(e);
		}
		setTriggerFilterList(triggerFilterList);
		return triggerFilterList;
		
	}
	 
	public ArrayList<Object> getTriggerLogicList() {
		return triggerLogicList;
	}
	public void setTriggerLogicList(ArrayList<Object>  triggerLogicList) {
		this.triggerLogicList = triggerLogicList;
	}
	public ArrayList<?> getTriggerFields() {
		return triggerFields;
	}
	public void setTriggerFields(ArrayList<?>  triggerFields) {
		this.triggerFields = triggerFields;
	}
	public ArrayList<Object> getTriggerFilterList() {
		return triggerFilterList;
	}
	public void setTriggerFilterList(ArrayList<Object>  triggerFilterList) {
		this.triggerFilterList = triggerFilterList;
	}
	public ArrayList<Object> getLevOfRevList() {
		return CachedDropDowns.getCodedValueForType("YN");
	}
	public void setLevOfRevList(ArrayList<Object>  levOfRevList) {
		this.levOfRevList = levOfRevList;
	}
	public String getDescFromList(ArrayList<Object>  list, String code) {
		if (list != null) {
			Iterator<Object> ite = list.iterator();
			while (ite.hasNext()) {
				DropDownCodeDT ddCodeDT = (DropDownCodeDT) ite.next();
				if (ddCodeDT.getKey().equals(code))
					return ddCodeDT.getValue();
			}
		}

		return null;
		
	}
	
	
	
	
	
	
	
}
