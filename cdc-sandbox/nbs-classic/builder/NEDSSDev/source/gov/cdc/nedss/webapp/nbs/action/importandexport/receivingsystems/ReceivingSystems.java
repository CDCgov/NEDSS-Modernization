package gov.cdc.nedss.webapp.nbs.action.importandexport.receivingsystems;

import gov.cdc.nedss.systemservice.ejb.casenotificationejb.dt.ExportReceivingFacilityDT;
import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.util.HTMLEncoder;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.action.importandexport.util.ImpAndExpConstants;
import gov.cdc.nedss.webapp.nbs.action.importandexport.util.ReceivingSystemUtil;
import gov.cdc.nedss.webapp.nbs.action.util.PaginationUtil;
import gov.cdc.nedss.webapp.nbs.action.util.QueueUtil;
import gov.cdc.nedss.webapp.nbs.form.importandexport.ReceivingFacilityForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;



public class ReceivingSystems extends DispatchAction {
	static final LogUtils logger = new LogUtils(ReceivingSystems.class.getName());
	static final String VIEW = "VIEW";
	static final String EDIT = "EDIT";
	ReceivingSystemUtil rsUtil = new ReceivingSystemUtil();
	PropertyUtil properties = PropertyUtil.getInstance();
	QueueUtil queueUtil = new QueueUtil();
	//static final String CONFIRM_MSG_EDIT = "This Receiving facility has been successfully updated";
	//static final String CONFIRM_MSG_ADD = "This Receiving facility has been successfully added";
	
	public ActionForward manageLoad(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		boolean forceEJBcall = true;
		ReceivingFacilityForm recFacilityForm = (ReceivingFacilityForm) form;
		boolean initLoad = request.getParameter("initLoad") == null ? false: Boolean.valueOf(request.getParameter("initLoad")).booleanValue();
		if (initLoad && !PaginationUtil._dtagAccessed(request)) {
			recFacilityForm.clearAll();
			forceEJBcall = true;
			// get the number of records to be displayed per page
			Integer queueSize = properties.getQueueSize(NEDSSConstants.MY_PROGRAM_AREAS_INVESTIGATIONS_QUEUE_SIZE);
			recFacilityForm.getAttributeMap().put("queueSize", queueSize);
		}
		
		//To make sure SelectAll is checked, see if no criteria is applied
		if(recFacilityForm.getSearchCriteriaArrayMap().size() == 0)
			request.setAttribute("ActionMode", HTMLEncoder.encodeHtml("InitLoad"));
		
		recFacilityForm.setActionMode(NEDSSConstants.RESULT_LOAD_ACTION);
		try{
			HttpSession session = request.getSession(true);
			ArrayList<Object> recFacilityList = new ArrayList<Object> ();
			if(forceEJBcall){
			recFacilityList = rsUtil.getReceivingSystemList(session); 
			recFacilityForm.getAttributeMap().put("manageList",recFacilityList);
			//Set InvSummaryVOColl as property of Form first time since this list is used for distinct dropdown values in the form
			recFacilityForm.setReceivingSysDTColl(recFacilityList);
			recFacilityForm.initializeDropDowns();
			recFacilityForm.getAttributeMap().put("ownerCount",new Integer(recFacilityForm.getOwner().size()));
			recFacilityForm.getAttributeMap().put("senderCount",new Integer(recFacilityForm.getSender().size()));
			recFacilityForm.getAttributeMap().put("recipientCount",new Integer(recFacilityForm.getReceipient().size()));
			recFacilityForm.getAttributeMap().put("transferCount",new Integer(recFacilityForm.getTransfer().size()));
			recFacilityForm.getAttributeMap().put("reportTypeCount",new Integer(recFacilityForm.getReportType().size()));
			Iterator<Object> iter = recFacilityList.iterator();
			while(iter.hasNext()) {
				ExportReceivingFacilityDT dt = (ExportReceivingFacilityDT) iter.next();
				String exportReceivingFacilityUid = dt.getExportReceivingFacilityUid().toString();
				request.setAttribute("exportReceivingFacilityUid", exportReceivingFacilityUid);
				dt.setRecordStatusCdDescTxt(rsUtil.getRecordSatusCodeDesc(dt.getRecordStatusCd()));
				dt.setSendingIndDescTxt(CachedDropDowns.getCodeDescTxtForCd(dt.getSendingIndCd(),"YN"));
				dt.setReceivingIndDescTxt(CachedDropDowns.getCodeDescTxtForCd(dt.getReceivingIndCd(),"YN"));
				dt.setJurDeriveDescTxt(CachedDropDowns.getCodeDescTxtForCd(dt.getJurDeriveIndCd(),"YN"));
				dt.setAllowTransferIndDescTxt(CachedDropDowns.getCodeDescTxtForCd(dt.getAllowTransferIndCd(),"YN"));
				dt.setReportTypeDescTxt(CachedDropDowns.getCodeDescTxtForCd(dt.getReportType(),"PUBLIC_HEALTH_EVENT"));
				}			
			}
			else {
				recFacilityList = (ArrayList<Object> ) recFacilityForm.getAttributeMap().get("manageList");
			}
			boolean existing = request.getParameter("existing") == null ? false : true;
			//FilterInvs return NULL if all the filters are applied, so make sure it is not null before assigning back the coll
			if(existing) {
			Collection<Object>  filteredColl = filterReceivingSystems(recFacilityForm, request);
				if(filteredColl != null)
					recFacilityList = (ArrayList<Object> ) filteredColl;
					sortReceivingSystems(recFacilityForm, recFacilityList, existing, request);
			}
			else {
				filterReceivingSystems(recFacilityForm, request);
				sortReceivingSystems(recFacilityForm, recFacilityList, existing, request);
			}
			//Collection<Object>  filteredColl = (ArrayList<Object> )filterReceivingSystems(recFacilityForm, request);
			ArrayList<Object> filterdList = new ArrayList<Object> ();
			filterdList = (ArrayList<Object> )recFacilityList;
			if(filterdList!= null){
				request.setAttribute("manageList",filterdList);
				
			}
			else {
			request.setAttribute("manageList", recFacilityList);
			}
			//request.setAttribute("queueList",filterdList);
			//request.setAttribute("manageList", recFacilityList);
			recFacilityForm.getAttributeMap().put("queueCount", String.valueOf(recFacilityList.size()));
			//request.setAttribute("queueCount", String.valueOf(recFacilityList.size()));
			recFacilityForm.setManageList(recFacilityList);
			//recFacilityForm.setQueueList(filterdList);

		} catch (Exception e) {
			logger.error("Exception in algorithmLoad: " + e.getMessage());
			request.setAttribute("error", e.getMessage());
		} finally {
			request.setAttribute(ImpAndExpConstants.PAGE_TITLE ,ImpAndExpConstants.ADD_RECEING_FACILITY);
		}
		return (mapping.findForward("default"));
		
	}
public ActionForward createLoadRecFacility(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		
	ReceivingFacilityForm recFacilityForm = (ReceivingFacilityForm) form;
	try{
		recFacilityForm.resetSelection();
		recFacilityForm.setSelection(new ExportReceivingFacilityDT());
		recFacilityForm.setActionMode(NEDSSConstants.CREATE_LOAD_ACTION);
		//((ExportReceivingFacilityDT)recFacilityForm.getSelection()).setJurDeriveIndCd("Y");
		recFacilityForm.setPageTitle(ImpAndExpConstants.ADD_RECEIVING_SYSTEM, request);
		request.setAttribute("accessMode", "createSystem");
		recFacilityForm.getAttributeMap().put("cancel", "/nbs/ReceivingSystem.do?method=manageLoad");
		recFacilityForm.getAttributeMap().put("submit", "/nbs/ReceivingSystem.do?method=createLoadRecFacilitySubmit"+"#"+"expAlg");
	}catch (Exception e){
		request.setAttribute("error", e.getMessage());
			}
	finally {
		request.setAttribute(ImpAndExpConstants.PAGE_TITLE ,ImpAndExpConstants.ADD_RECEIVING_SYSTEM);
	}
	Collection<Object>  recFacilityList = recFacilityForm.getManageList();
	Collection<?>  queueList = (ArrayList<?> ) recFacilityForm.getAttributeMap().get("queueList");
	
	if(queueList!= null){
		request.setAttribute("manageList",queueList);
		
	}
	else {
	request.setAttribute("manageList", recFacilityList);
	}
	return (mapping.findForward("create"));			
}


public ActionForward createLoadRecFacilitySubmit(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws ServletException {
	ReceivingFacilityForm recFacilityForm = (ReceivingFacilityForm) form;
	
	recFacilityForm.setActionMode(NEDSSConstants.CREATE_SUBMIT_ACTION);
	recFacilityForm.setPageTitle(ImpAndExpConstants.ADD_RECEIVING_SYSTEM, request);
	recFacilityForm.getAttributeMap().put("cancel", "/nbs/ReceivingSystem.do?method=manageLoad");
	HttpSession session = request.getSession(true);
	ExportReceivingFacilityDT dt = (ExportReceivingFacilityDT)recFacilityForm.getSelection();
	dt = rsUtil.setReceivingSysForCreateEdit(dt, session,recFacilityForm.getActionMode());
	Map<Object, Object> errorMap = null;
	try{
		errorMap = rsUtil.setReceivingSystems(session, dt);
		if(errorMap.size()==0){
			String CONFIRM_MSG_ADD = "The  "+HTMLEncoder.encodeHtml(dt.getReceivingSystemShortName())+"  successfully added to the System.";
			request.setAttribute("confirmMsg" ,CONFIRM_MSG_ADD);
			recFacilityForm.setActionMode(NEDSSConstants.VIEW_LOAD_ACTION);
			String exportReceivingFacilityUid = rsUtil.getReceivingSystem(session, dt);
			recFacilityForm.getAttributeMap().put("Edit", "/nbs/ReceivingSystem.do?method=editRecFacility&exportReceivingFacilityUid="+exportReceivingFacilityUid+"#"+"expAlg");
			
		}
		else if(errorMap.size()!=0){	
			recFacilityForm.setActionMode(NEDSSConstants.CREATE_LOAD_ACTION);
			Iterator<Object> iter = errorMap.values().iterator();
			StringBuffer buff= new StringBuffer();
			while(iter.hasNext()){
				String errText = iter.next().toString();
				buff.append("<ul>").append("<li>").append(errText).append("</li>").append("</ul>");
			}
			request.setAttribute("error",buff.toString());
			
		}		
				
	}catch (Exception e){
		request.setAttribute("manageList", recFacilityForm.getManageList());
		recFacilityForm.getAttributeMap().put("cancel", "/nbs/ReceivingSystem.do?method=manageLoad");
		request.setAttribute(ImpAndExpConstants.PAGE_TITLE ,ImpAndExpConstants.ADD_RECEIVING_SYSTEM);
		recFacilityForm.setActionMode(NEDSSConstants.CREATE_LOAD_ACTION);
		logger.error("Exception in createCodeSetSubmit: " + e.getMessage());
		return (mapping.findForward("default"));
	}

	
	ArrayList<Object> algorithmList = new ArrayList<Object> ();
	try{
		algorithmList = rsUtil.getReceivingSystemList(session);  
		Iterator<Object> iter = algorithmList.iterator();
		while(iter.hasNext()) {
			ExportReceivingFacilityDT edt = (ExportReceivingFacilityDT) iter.next();
			edt.setSendingIndDescTxt(CachedDropDowns.getCodeDescTxtForCd(edt.getSendingIndCd(),"YN"));
			edt.setReceivingIndDescTxt(CachedDropDowns.getCodeDescTxtForCd(edt.getReceivingIndCd(),"YN"));
			edt.setJurDeriveDescTxt(CachedDropDowns.getCodeDescTxtForCd(edt.getJurDeriveIndCd(),"YN"));
			edt.setAllowTransferIndDescTxt(CachedDropDowns.getCodeDescTxtForCd(edt.getAllowTransferIndCd(),"YN"));
			edt.setReportTypeDescTxt(CachedDropDowns.getCodeDescTxtForCd(edt.getReportType(),"PUBLIC_HEALTH_EVENT"));
		}			
		request.setAttribute("manageList", algorithmList);
		recFacilityForm.setManageList(algorithmList);
	}catch (Exception ex){
		request.setAttribute("error", ex.getMessage());
	}
	finally {
		request.setAttribute(ImpAndExpConstants.PAGE_TITLE ,ImpAndExpConstants.ADD_RECEIVING_SYSTEM);
	}
	dt.setSendingIndDescTxt(CachedDropDowns.getCodeDescTxtForCd(dt.getSendingIndCd(),"YN"));
	dt.setReceivingIndDescTxt(CachedDropDowns.getCodeDescTxtForCd(dt.getReceivingIndCd(),"YN"));
	dt.setJurDeriveDescTxt(CachedDropDowns.getCodeDescTxtForCd(dt.getJurDeriveIndCd(),"YN"));
	//dt.setAllowTransferIndDescTxt(CachedDropDowns.getCodeDescTxtForCd(dt.getAllowTransferIndCd(),"YN"));
	dt.setReportTypeDescTxt(CachedDropDowns.getCodeDescTxtForCd(dt.getReportType(),"PUBLIC_HEALTH_EVENT"));
	if(dt.getReceivingIndDescTxt().equals("No")){
		dt.setAllowTransferIndDescTxt("");
	}
	else {
		dt.setAllowTransferIndDescTxt(CachedDropDowns.getCodeDescTxtForCd(dt.getAllowTransferIndCd(),"YN"));
	}

	recFacilityForm.setSelection(dt);
    recFacilityForm.setOldExpRecFacDT(dt);
    recFacilityForm.setExportRecFacDT(dt);
   
    recFacilityForm.getAttributeMap().put("queueCount", String.valueOf(algorithmList.size()));
    recFacilityForm.setReceivingSysDTColl(algorithmList);
    //reset to rebuild the dropdown values
    recFacilityForm.initializeDropDowns();
    
    //Add the newly selected value to the SearchCriteriaMap 
    recFacilityForm.setAnswerArray("owner", _convertDropDownsToArray(recFacilityForm.getOwner()));

    
   	return (mapping.findForward("default"));		
}

private static String[] _convertDropDownsToArray(ArrayList<Object> list) {
	String[] returnSt = new String[list.size()];
	try {
	Iterator<Object> iter = list.iterator();
	int i = 0;
	while(iter.hasNext()) {
		DropDownCodeDT dt = (DropDownCodeDT) iter.next();
		returnSt[i] = dt.getValue();
		i++;
	}
	} catch (Exception ex) {
		logger.error("Error in _convertDropDownsToArray: "+ex.getMessage());
		ex.printStackTrace();
	}	
	return returnSt;
}

public ActionForward viewRecFacility(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws ServletException {
		ReceivingFacilityForm recFacilityForm  = (ReceivingFacilityForm) form;
		ExportReceivingFacilityDT dt = new ExportReceivingFacilityDT();
		ArrayList<?> filQueueList = new ArrayList<Object> ();
		try {
			recFacilityForm.setActionMode(NEDSSConstants.VIEW_LOAD_ACTION);
			
			String cnxt = request.getParameter("context");
			if(cnxt != null && cnxt.equalsIgnoreCase("cancel")) {				
				recFacilityForm.setSelection(recFacilityForm.getOldExpRecFacDT());
				return (mapping.findForward("default"));
			}			
			String recFacilityUid = request.getParameter("exportReceivingFacilityUid");
			if(recFacilityUid != null && recFacilityUid.length() > 0) {
				Collection<?>  queueList = (ArrayList<?> ) recFacilityForm.getAttributeMap().get("queueList");
				 filQueueList = (ArrayList<?> )queueList;
				if(filQueueList!= null){
					Iterator<?> iter = filQueueList.iterator();
					while(iter.hasNext()) {			
						 dt = (ExportReceivingFacilityDT) iter.next();
						if(dt.getExportReceivingFacilityUid().toString().equalsIgnoreCase(recFacilityUid) ) {
							recFacilityForm.setExportRecFacDT(dt);
							recFacilityForm.setOldExpRecFacDT(dt);
							break;
						}
					}
					
				}
				else {

				ArrayList<Object> testList = recFacilityForm.getManageList();
				Iterator<Object> iter = testList.iterator();
				while(iter.hasNext()) {			
					 dt = (ExportReceivingFacilityDT) iter.next();
					if(dt.getExportReceivingFacilityUid().toString().equalsIgnoreCase(recFacilityUid) ) {
						recFacilityForm.setExportRecFacDT(dt);
						recFacilityForm.setOldExpRecFacDT(dt);
						break;
					}
				}
			 }
			}
			
			
		} catch (Exception e) {
			request.setAttribute("error", e.getMessage());
			logger.error("Exception in viewLab: " + e.getMessage());
		} finally {
			Collection<Object>  recFacilityList = recFacilityForm.getManageList();
			//Collection<Object>  queueList = (ArrayList<Object> ) recFacilityForm.getAttributeMap().get("queueList");
			
			if(filQueueList!= null){
				request.setAttribute("manageList",filQueueList);
				
			}
			else {
			request.setAttribute("manageList", recFacilityList);
			}
			String exportReceivingFacilityUid = dt.getExportReceivingFacilityUid().toString();
			request.setAttribute("exportReceivingFacilityUid", exportReceivingFacilityUid);
			recFacilityForm.getAttributeMap().put("Edit", "/nbs/ReceivingSystem.do?method=editRecFacility&exportReceivingFacilityUid="+exportReceivingFacilityUid+"#"+"expAlg");
			request.setAttribute(ImpAndExpConstants.PAGE_TITLE ,ImpAndExpConstants.VIEW_RECEIVING_FACILITY);
		}
		return (mapping.findForward("default"));	
	}
	
public ActionForward editRecFacility(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		
	ReceivingFacilityForm recFacilityForm  = (ReceivingFacilityForm) form;
	ExportReceivingFacilityDT dt = new ExportReceivingFacilityDT();
	ArrayList<?> filQueueList = new ArrayList<Object> ();
	try{
	recFacilityForm.setActionMode(NEDSSConstants.EDIT_LOAD_ACTION);
	String cnxt = request.getParameter("context");
	if(cnxt != null && cnxt.equalsIgnoreCase("cancel")) {					
		recFacilityForm.setOldExpRecFacDT(recFacilityForm.getOldExpRecFacDT());
		return (mapping.findForward("edit"));
	}
	String recFacilityUid = request.getParameter("exportReceivingFacilityUid");
	if(recFacilityUid != null && recFacilityUid.length() > 0) {
		Collection<?>  queueList = (ArrayList<?> ) recFacilityForm.getAttributeMap().get("queueList");
		 filQueueList = (ArrayList<?> )queueList;
		if(filQueueList!= null){
			Iterator<?> iter = filQueueList.iterator();
			while(iter.hasNext()) {			
				 dt = (ExportReceivingFacilityDT) iter.next();
				if(dt.getExportReceivingFacilityUid().toString().equalsIgnoreCase(recFacilityUid) ) {
					recFacilityForm.setSelection(dt);
					recFacilityForm.setOldExpRecFacDT(dt);
					break;
				}
			}
			
		}
		else {
		ArrayList<Object> testList = recFacilityForm.getManageList();
		Iterator<Object> iter = testList.iterator();
		while(iter.hasNext()) {			
			dt = (ExportReceivingFacilityDT) iter.next();
			if(dt.getExportReceivingFacilityUid().toString().equalsIgnoreCase(recFacilityUid) ) {
				recFacilityForm.setSelection(dt);
				recFacilityForm.setOldExpRecFacDT(dt);
				break;
			}
		}
	  }
	}
	
	}
	catch (Exception e) {
		request.setAttribute("error", e.getMessage());
		logger.error("Exception in viewLab: " + e.getMessage());
	} finally {
		Collection<Object>  recFacilityList = recFacilityForm.getManageList();
		
		if(filQueueList!= null){
			request.setAttribute("manageList",filQueueList);
			
		}
		else {
			request.setAttribute("manageList", recFacilityList);
		 }
		String exportReceivingFacilityUid = dt.getExportReceivingFacilityUid().toString();
		request.setAttribute("exportReceivingFacilityUid", exportReceivingFacilityUid);
		recFacilityForm.getAttributeMap().put("cancel", "/nbs/ReceivingSystem.do?method=viewRecFacility&exportReceivingFacilityUid="+exportReceivingFacilityUid);
		recFacilityForm.getAttributeMap().put("submit", "/nbs/ReceivingSystem.do?method=updateRecFacility&exportReceivingFacilityUid="+exportReceivingFacilityUid+"#"+"expAlg");
		request.setAttribute(ImpAndExpConstants.PAGE_TITLE ,ImpAndExpConstants.EDIT_RECEIVING_FACILITY);
	}
	return (mapping.findForward("edit"));		
}
	
public ActionForward updateRecFacility(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws ServletException {
	
	String forward = null;
	ReceivingFacilityForm recFacilityForm  = (ReceivingFacilityForm) form;
	recFacilityForm.setManageList(null);
	HttpSession session = request.getSession(true);
	ExportReceivingFacilityDT dt = (ExportReceivingFacilityDT)recFacilityForm.getSelection();
	dt = rsUtil.setReceivingSysForCreateEdit(dt, session,recFacilityForm.getActionMode());
	Map<Object, Object> errorMap = null;
	try{
		errorMap = rsUtil.updateReceivingSystems(session, dt);
		if(errorMap.size()==0){
			String CONFIRM_MSG_EDIT = "This "+HTMLEncoder.encodeHtml(dt.getReceivingSystemShortName())+" has been successfully updated." ;
			request.setAttribute("confirmMsg" ,CONFIRM_MSG_EDIT);
			recFacilityForm.setActionMode(NEDSSConstants.VIEW_LOAD_ACTION);
			forward = "view";
		}
		else if(errorMap.size()!=0){	
			recFacilityForm.setActionMode(NEDSSConstants.EDIT_LOAD_ACTION);
			Iterator<Object> iter = errorMap.values().iterator();
			StringBuffer buff= new StringBuffer();
			while(iter.hasNext()){
				String errText = iter.next().toString();
				buff.append("<ul>").append("<li>").append(errText).append("</li>").append("</ul>");
			}
			request.setAttribute("error",buff.toString());
			forward = "default";
			
		}	
		
	}catch (Exception e){
		logger.error("Error in createNewBatchExportSubmit method" );
		request.setAttribute("error", e.getMessage());
		throw new ServletException("Error in Creating the Export Algorithm"+e.getMessage(),e);
	}
	ArrayList<Object> algorithmList = new ArrayList<Object> ();
	try{
		algorithmList = rsUtil.getReceivingSystemList(session);  
		Iterator<Object> iter = algorithmList.iterator();
		while(iter.hasNext()) {
			ExportReceivingFacilityDT edt = (ExportReceivingFacilityDT) iter.next();
			edt.setRecordStatusCdDescTxt(rsUtil.getRecordSatusCodeDesc(edt.getRecordStatusCd()));
			edt.setSendingIndDescTxt(CachedDropDowns.getCodeDescTxtForCd(edt.getSendingIndCd(),"YN"));
			edt.setReceivingIndDescTxt(CachedDropDowns.getCodeDescTxtForCd(edt.getReceivingIndCd(),"YN"));
			edt.setJurDeriveDescTxt(CachedDropDowns.getCodeDescTxtForCd(edt.getJurDeriveIndCd(),"YN"));
			edt.setAllowTransferIndDescTxt(CachedDropDowns.getCodeDescTxtForCd(edt.getAllowTransferIndCd(),"YN"));
			edt.setReportTypeDescTxt(CachedDropDowns.getCodeDescTxtForCd(edt.getReportType(),"PUBLIC_HEALTH_EVENT")); 
			}			
		request.setAttribute("manageList", algorithmList);
		recFacilityForm.setManageList(algorithmList);
	}catch (Exception e){
		request.setAttribute("error", e.getMessage());
	}
	dt.setRecordStatusCdDescTxt(rsUtil.getRecordSatusCodeDesc(dt.getRecordStatusCd()));
	dt.setSendingIndDescTxt(CachedDropDowns.getCodeDescTxtForCd(dt.getSendingIndCd(),"YN"));
	dt.setReceivingIndDescTxt(CachedDropDowns.getCodeDescTxtForCd(dt.getReceivingIndCd(),"YN"));
	dt.setJurDeriveDescTxt(CachedDropDowns.getCodeDescTxtForCd(dt.getJurDeriveIndCd(),"YN"));
	//dt.setAllowTransferIndDescTxt(CachedDropDowns.getCodeDescTxtForCd(dt.getAllowTransferIndCd(),"YN"));
	if(dt.getReceivingIndDescTxt().equals("No")){
		dt.setAllowTransferIndDescTxt("");
	}
	else {
		dt.setAllowTransferIndDescTxt(CachedDropDowns.getCodeDescTxtForCd(dt.getAllowTransferIndCd(),"YN"));
	}

	
	recFacilityForm.setOldExpRecFacDT(dt);
    recFacilityForm.setExportRecFacDT(dt);
    
    request.setAttribute(ImpAndExpConstants.PAGE_TITLE ,ImpAndExpConstants.VIEW_RECEIVING_FACILITY);
	return (mapping.findForward(forward));		
    		
	}




public ActionForward filterReceivingSystemsSubmit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

	ReceivingFacilityForm recFacilityForm  = (ReceivingFacilityForm) form;
	Collection<Object>  recFacilityList = filterReceivingSystems(recFacilityForm, request);
	if(recFacilityList != null){
		request.setAttribute("ActionMode", HTMLEncoder.encodeHtml("Search"));
	//When all filters are applied (equivalent to no filter applied), Collection<Object>  is set to null in which case retrieve it from Form.
	}else{
		recFacilityList = (ArrayList<Object> ) recFacilityForm.getReceivingSysDTColl();
	}
	recFacilityForm.getAttributeMap().put("manageList",recFacilityList);
	recFacilityForm.getAttributeMap().put("queueList",recFacilityList);
	request.setAttribute("queueList", recFacilityList);
	sortReceivingSystems(recFacilityForm, recFacilityList, true, request);
	request.setAttribute("manageList", recFacilityList);
	//request.setAttribute("queueCount", String.valueOf(recFacilityList.size()));
	recFacilityForm.getAttributeMap().put("queueCount", String.valueOf(recFacilityList.size()));
	request.setAttribute("PageTitle","Manage Sending and Receiving Systems");
	recFacilityForm.getAttributeMap().put("PageNumber", "1");

	return PaginationUtil.paginate(recFacilityForm, request, "default",mapping);
	
} 
private Collection<Object>  filterReceivingSystems(ReceivingFacilityForm recFacilityForm, HttpServletRequest request) {
	
	Collection<Object>  receivingSystemslist = new ArrayList<Object> ();
	
	String srchCriteriaOwner = null;
	String srchCriteriaSender = null;
	String srchCriteriaRecipient = null;
	String srchCriteriaTransfer = null;
	String srchCriteriaReportType = null;
	String sortOrderParam = null;
	
	
	
	try {
		
		Map<Object, Object> searchCriteriaMap = recFacilityForm.getSearchCriteriaArrayMap();
		// Get the existing SummaryVO collection in the form
		ArrayList<Object> recFacilityList = (ArrayList<Object> ) recFacilityForm.getReceivingSysDTColl();
		
		// Filter by the investigator
		receivingSystemslist = rsUtil.getFilteredReceivingSystems(recFacilityList, searchCriteriaMap);
		
		String filterByApplicationName = null;
        String filterByDisplayName = null;
		if(searchCriteriaMap.get("SearchText1_FILTER_TEXT")!=null){
			filterByApplicationName = (String) searchCriteriaMap.get("SearchText1_FILTER_TEXT");
	     }
	     if(searchCriteriaMap.get("SearchText2_FILTER_TEXT")!=null){
	    	 filterByDisplayName = (String) searchCriteriaMap.get("SearchText2_FILTER_TEXT");
	     }
		String[] owner = (String[]) searchCriteriaMap.get("Owner");
    	String[] sender = (String[]) searchCriteriaMap.get("Sender");
		String[] recipient = (String[]) searchCriteriaMap.get("Recipient");
		String[] transfer = (String[]) searchCriteriaMap.get("Transfer");
		String[] reportType = (String[]) searchCriteriaMap.get("ReportType");

		Integer ownerCount = new Integer(owner == null ? 0 : owner.length);
		Integer senderCount = new Integer(sender == null  ? 0 : sender.length);
		Integer recipientCount = new Integer(recipient == null  ? 0 : recipient.length);
		Integer transferCount = new Integer(transfer == null ? 0 : transfer.length);
		Integer reportTypeCount = new Integer(reportType== null ? 0 : reportType.length);
		

		// Do not filter if the selected values for filter is same as filtered list, but put the sortMethod, direction and criteria stuff
		if(ownerCount.equals((recFacilityForm.getAttributeMap().get("ownerCount"))) &&
				
				(senderCount.equals(recFacilityForm.getAttributeMap().get("senderCount"))) &&
				(recipientCount.equals(recFacilityForm.getAttributeMap().get("recipientCount"))) &&
				(transferCount.equals(recFacilityForm.getAttributeMap().get("transferCount"))) && 
				(reportTypeCount.equals(recFacilityForm.getAttributeMap().get("reportTypeCount"))) &&
				filterByApplicationName == null &&
                filterByDisplayName == null)
				
			 {
			
			String sortMethod = getSortMethod(request, recFacilityForm);
			String direction = getSortDirection(request, recFacilityForm);			
			if(sortMethod == null || (sortMethod != null && sortMethod.equals("none"))) {
				Map<?,?> sColl =  recFacilityForm.getAttributeMap().get("searchCriteria") == null ? new TreeMap<Object, Object>() : (TreeMap<?,?>) recFacilityForm.getAttributeMap().get("searchCriteria");
				sortOrderParam = sColl.get("sortSt") == null ? "" : (String) sColl.get("sortSt");
			} else {
				sortOrderParam = rsUtil.getSortCriteria(direction, sortMethod);
			}				
			Map<Object, Object> searchCriteriaColl = new TreeMap<Object, Object>();
			searchCriteriaColl.put("sortSt", sortOrderParam);
			recFacilityForm.getAttributeMap().put("searchCriteria", searchCriteriaColl);			
			return null;				
		}
					
		ArrayList<Object> ownerList = recFacilityForm.getOwner();
		ArrayList<Object> senderList = recFacilityForm.getSender();
		ArrayList<Object> receipientList = recFacilityForm.getReceipient();
		ArrayList<Object> transferList = recFacilityForm.getTransfer();
		ArrayList<Object> reportTypeList = recFacilityForm.getReportType();
		
		
		Map<Object, Object> searchCriteriaColl = new TreeMap<Object, Object>();
		String sortMethod = getSortMethod(request, recFacilityForm);
		String direction = getSortDirection(request, recFacilityForm);			
		if(sortMethod == null || (sortMethod != null && sortMethod.equals("none"))) {
			Map<Object, Object> sColl =  recFacilityForm.getAttributeMap().get("searchCriteria") == null ? new TreeMap<Object, Object>() : (TreeMap<Object, Object>) recFacilityForm.getAttributeMap().get("searchCriteria");
			sortOrderParam = sColl.get("sortSt") == null ? "" : (String) sColl.get("sortSt");
		} else {
			sortOrderParam = rsUtil.getSortCriteria(direction, sortMethod);
		}
		
		srchCriteriaOwner = queueUtil.getSearchCriteria(ownerList, owner, NEDSSConstants.FILTERBYSUBMITTEDBY);
		
		srchCriteriaSender = queueUtil.getSearchCriteria(senderList, sender, NEDSSConstants.FILTERBYCONDITION);
		srchCriteriaRecipient = queueUtil.getSearchCriteria(receipientList, recipient, NEDSSConstants.FILTERBYSTATUS);
		srchCriteriaTransfer = queueUtil.getSearchCriteria(transferList, transfer, NEDSSConstants.FILTERBYDATE);
		srchCriteriaReportType = queueUtil.getSearchCriteria(reportTypeList, reportType, NEDSSConstants.FILTERBYREPORTTYPE);
		
		
		//set the error message to the form
		if(sortOrderParam != null)
			searchCriteriaColl.put("sortSt", sortOrderParam);
		if(srchCriteriaOwner != null)
			searchCriteriaColl.put("INV147", srchCriteriaOwner);
		if(srchCriteriaSender != null)
				searchCriteriaColl.put("INV100", srchCriteriaSender);
		if(srchCriteriaRecipient != null)
			searchCriteriaColl.put("INV163", srchCriteriaRecipient);
		if(srchCriteriaTransfer != null)
			searchCriteriaColl.put("NOT118", srchCriteriaTransfer);
		if(srchCriteriaReportType != null)
			searchCriteriaColl.put("DEM102", srchCriteriaReportType);
	
		
		if(filterByApplicationName != null) {
            request.setAttribute("FILTERBYAPPLICATIONNAME", filterByApplicationName);
            searchCriteriaColl.put("INV001", filterByApplicationName);
     }
     if(filterByDisplayName != null) {
            request.setAttribute("FILTERBYDISPLAYNAME", filterByDisplayName);
            searchCriteriaColl.put("INV002", filterByDisplayName);
     }
     
     
		recFacilityForm.getAttributeMap().put("searchCriteria", searchCriteriaColl);
		
	} catch (Exception e) {
		e.printStackTrace();
		logger.error("Error while filtering the investigation by Investigator: "+ e.toString());
		
	} 
	return receivingSystemslist;
}

private void sortReceivingSystems(ReceivingFacilityForm recFacilityForm, Collection<Object>  recFacilityList, boolean existing, HttpServletRequest request) {

// Retrieve sort-order and sort-direction from displaytag params
String sortMethod = getSortMethod(request, recFacilityForm);
String direction = getSortDirection(request, recFacilityForm);

boolean invDirectionFlag = true;
if (direction != null && direction.equals("2"))
	invDirectionFlag = false;


NedssUtils util = new NedssUtils();
if (sortMethod != null && recFacilityList != null
		&& recFacilityList.size() > 0) {
	updateNotificationSummaryVObeforeSort(recFacilityList);
	//util.sortObjectByColumn(sortMethod,
	//		(Collection<Object>) recFacilityList, invDirectionFlag);
	util.sortObjectByColumnGeneric(sortMethod,
			(Collection<Object>) recFacilityList, invDirectionFlag);
	updateNotificationSummaryVOAfterSort(recFacilityList);
	
}
if(!existing) {
	//Finally put sort criteria in form
	String sortOrderParam = rsUtil.getSortCriteria(invDirectionFlag == true ? "1" : "2", sortMethod);
	Map<Object, Object> searchCriteriaColl = new TreeMap<Object, Object>();
	searchCriteriaColl.put("sortSt", sortOrderParam);
	recFacilityForm.getAttributeMap().put("searchCriteria", searchCriteriaColl);
}
}
private String getSortMethod(HttpServletRequest request, ReceivingFacilityForm recFacilityForm ) {
	if (PaginationUtil._dtagAccessed(request)) {
		return request.getParameter((new ParamEncoder("parent")).encodeParameterName(TableTagParameters.PARAMETER_SORT));
} else{
	return recFacilityForm.getAttributeMap().get("methodName") == null ? null : (String) recFacilityForm.getAttributeMap().get("methodName");
	}
	
}
private String getSortDirection(HttpServletRequest request, ReceivingFacilityForm recFacilityForm) {
	if (PaginationUtil._dtagAccessed(request)) {
		return request.getParameter((new ParamEncoder("parent")).encodeParameterName(TableTagParameters.PARAMETER_ORDER));
	} else{
		return recFacilityForm.getAttributeMap().get("sortOrder") == null ? "1": (String) recFacilityForm.getAttributeMap().get("sortOrder");
	}
	
}


private void updateNotificationSummaryVOAfterSort(Collection<Object>  notifiSummaryVOs) {
	Iterator<Object> iter = notifiSummaryVOs.iterator();
	while (iter.hasNext()) {
		ExportReceivingFacilityDT dt = (ExportReceivingFacilityDT)  iter.next();
		if (dt.getReceivingSystemNm() != null && dt.getReceivingSystemNm().equals("ZZZZZ")) {
			dt.setReceivingSystemNm("");
		}
		/*if (dt.getReceivingSystemShortName() != null && dt.getReceivingSystemShortName().equals("ZZZZZ")) {
			dt.setReceivingSystemShortName("");
		}*/
		
	}
	
}
private void updateNotificationSummaryVObeforeSort(Collection<Object>  recFacilityList) {
	Iterator<Object> iter = recFacilityList.iterator();
	while (iter.hasNext()) {
		ExportReceivingFacilityDT dt = (ExportReceivingFacilityDT)  iter.next();
		
		if (dt.getReceivingSystemNm()== null || (dt.getReceivingSystemNm() != null &&dt.getReceivingSystemNm().equals(""))) {
			dt.setReceivingSystemNm("ZZZZZ");
		}
	}
	
}
}
