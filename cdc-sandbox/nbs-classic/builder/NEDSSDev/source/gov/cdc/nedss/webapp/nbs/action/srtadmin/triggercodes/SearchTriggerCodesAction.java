package gov.cdc.nedss.webapp.nbs.action.srtadmin.triggercodes;

import gov.cdc.nedss.srtadmin.dt.LabResultDT;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.webapp.nbs.action.srtadmin.util.SRTAdminConstants;
import gov.cdc.nedss.webapp.nbs.action.srtadmin.util.SRTAdminUtil;
import gov.cdc.nedss.webapp.nbs.action.util.NBSPageConstants;
import gov.cdc.nedss.webapp.nbs.form.triggerCodes.TriggerCodeForm;

import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

public class SearchTriggerCodesAction extends DispatchAction{
	
	static final LogUtils logger = new LogUtils(SearchTriggerCodesAction.class.getName());
	static final String VIEW = "VIEW";
	static final String EDIT = "EDIT";
	private static CachedDropDownValues cdv = new CachedDropDownValues();
	

	public ActionForward searchLabLoad(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		try{	
			TriggerCodeForm manageForm = (TriggerCodeForm) form;
			manageForm.clearSelections();
		} catch (Exception e) {
			logger.error("Exception in searchLabLoad: " + e.getMessage());
			e.printStackTrace();
			request.setAttribute("error", e.getMessage());
		}finally {
			request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.MANAGE_TRIGGER_CODES);
		}

		return (mapping.findForward("default"));
		
	}
	
	public ActionForward resultsLoad(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		try
		{
			TriggerCodeForm manageForm = (TriggerCodeForm) form;
			request.setAttribute("manageList", manageForm.getManageList());
		} catch (Exception e) {
			request.setAttribute("error", e.getMessage());
			logger.error("Exception in resultsLoad: " + e.getMessage());
			e.printStackTrace();
		} finally {
			request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.MANAGE_TRIGGER_CODES);
		}

		return (mapping.findForward("default"));
	}
	
	
public ActionForward searchLabSubmit(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		
		try {
			TriggerCodeForm manageForm = (TriggerCodeForm) form;
			manageForm.setActionMode(null);
			String whereClause = SRTAdminUtil.labResultSrchWhereClause(manageForm.getSearchMap());
			manageForm.getAttributeMap().clear();			
			Object[] searchParams = {whereClause};
				
			Object[] oParams = new Object[] { JNDINames.LOCALLY_DEFINED_LABORATORY_RESULT_DAO_CLASS, "getLabResultDTCollection", searchParams };
			ArrayList<?> dtList = (ArrayList<?> ) SRTAdminUtil.processRequest(oParams, request.getSession());
			if(dtList.size() > 0) {
			Iterator<?> iter = dtList.iterator();
				while(iter.hasNext()) {
					LabResultDT dt = (LabResultDT) iter.next();
					String cd = dt.getLabResultCd();
					dt.setLabResultCd(cd);
					SRTAdminUtil.makeResultLink(dt, VIEW);
					SRTAdminUtil.makeResultLink(dt, EDIT);
					if(dt.getOrganismNameInd() != null && dt.getOrganismNameInd().equals("Y"))
						dt.setOrganismNameInd("1");
					if(dt.getPaDerivationExcludeCd() != null && dt.getPaDerivationExcludeCd().equals("Y"))
						dt.setPaDerivationExcludeCd("1");
					if(dt.getDefaultConditionCd() != null && dt.getDefaultConditionCd().length() > 0)
						dt.setConditionDescTxt(cdv.getConditionDesc(dt.getDefaultConditionCd()));
					
				}
			}else {
				manageForm.getAttributeMap().put("NORESULT","NORESULT");
			}
			//manageForm.setManageList(dtList);
		
		} catch (Exception e) {
			logger.error("Exception in searchLabSubmit: " + e.getMessage());
			e.printStackTrace();
			SRTAdminUtil.handleErrors(e, request, "search", "");
			return (mapping.findForward("default"));

		}finally {
			request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.MANAGE_TRIGGER_CODES);
		}		
		return (mapping.findForward("results"));
		
	}	

public ActionForward editLab(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
	
	TriggerCodeForm manageForm = (TriggerCodeForm) form;
	try
	{
		manageForm.setActionMode(NEDSSConstants.EDIT_LOAD_ACTION);
		String labResultCd = request.getParameter("labResultCd");
		String laboratoryId = request.getParameter("laboratoryId");
		
		if((labResultCd != null && labResultCd.length() > 0) && (laboratoryId != null && laboratoryId.length() > 0)) {
			ArrayList<?> testList = manageForm.getManageList();
			Iterator<?> iter = testList.iterator();
			while(iter.hasNext()) {			
				LabResultDT dt = (LabResultDT) iter.next();
				if(dt.getLaboratoryId().equalsIgnoreCase(laboratoryId) && dt.getLabResultCd().equalsIgnoreCase(labResultCd)){			
					manageForm.setSelection(dt);
					manageForm.setOldDT(dt);
					break;
				}
			}
			
		} 
		manageForm.getAttributeMap().put("cancel", "/nbs/ExistingLocallyDefinedLabResults.do?method=viewLab&context=cancel#labresults");
		manageForm.getAttributeMap().put("submit", "/nbs/ExistingLocallyDefinedLabResults.do?method=updateLab");
	} catch (Exception e) {
		request.setAttribute("error", e.getMessage());
		logger.error("Exception in editLab: " + e.getMessage());
		e.printStackTrace();
	} finally {
		request.setAttribute("manageList", manageForm.getManageList());
		request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.EDIT_LD_LABRESULTS);
	}

	
		return (mapping.findForward("default"));		
		
}

public ActionForward viewLab(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
	
	TriggerCodeForm manageForm = (TriggerCodeForm) form;
	try
	{
		manageForm.setActionMode(NEDSSConstants.VIEW_LOAD_ACTION);
		manageForm.setReturnToLink("<a href=\"/nbs/ExistingLocallyDefinedLabResults.do?method=resultsLoad\">Return To Manage Results</a> ");
		
		String cnxt = request.getParameter("context");
		if(cnxt != null && cnxt.equalsIgnoreCase("cancel")) {				
			manageForm.setSelection(manageForm.getOldDT());
			return (mapping.findForward("default"));
		}
			String labResultCd = request.getParameter("labResultCd");
			String laboratoryId = request.getParameter("laboratoryId");
			if((labResultCd != null && labResultCd.length() > 0) && (laboratoryId != null && laboratoryId.length() > 0)) {
				ArrayList<?> testList = manageForm.getManageList();
				Iterator<?> iter = testList.iterator();
				while(iter.hasNext()) {			
					LabResultDT dt = (LabResultDT) iter.next();
					if(dt.getLaboratoryId().equalsIgnoreCase(laboratoryId) && dt.getLabResultCd().equalsIgnoreCase(labResultCd)){			
						manageForm.setSelection(dt);
						manageForm.setOldDT(dt);
						break;
					}
				}
				
			}
		
	} catch (Exception e) {
		request.setAttribute("error", e.getMessage());
		logger.error("Exception in viewLab: " + e.getMessage());
		e.printStackTrace();
	}finally {
		request.setAttribute("manageList", manageForm.getManageList());
		request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.VIEW_LD_LABRESULTS);
	}
	return (mapping.findForward("default"));
}

public ActionForward updateLab(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
	
	TriggerCodeForm manageForm = (TriggerCodeForm) form;
	manageForm.setManageList(null);
	LabResultDT dt = (LabResultDT) manageForm.getSelection();
	try {
		SRTAdminUtil.trimSpaces(dt);
		manageForm.setReturnToLink("<a href=\"/nbs/ExistingLocallyDefinedLabResults.do?method=searchLabSubmit\">Return To Manage Results</a> ");
		Object[] searchParams = new Object[] {dt};
		Object[] oParams = new Object[] { JNDINames.LOCALLY_DEFINED_LABORATORY_RESULT_DAO_CLASS, "updateLocallyDefinedResult", searchParams };
		dt.setConditionDescTxt(cdv.getConditionDesc(dt.getDefaultConditionCd()));
		SRTAdminUtil.processRequest(oParams, request.getSession());
		
		ActionMessages messages = new ActionMessages();
		messages.add(NBSPageConstants.SUCCESS_MESSAGES_PROPERTY,
				new ActionMessage(NBSPageConstants.SAVE_SUCCESS_MESSAGE_KEY, "Lab Result"));
		request.setAttribute("success_messages", messages);
	} catch (Exception e) {
		SRTAdminUtil.handleErrors(e, request, "edit",NEDSSConstants.LABRESULTCD);
		logger.error("Exception in updateLab: " + e.getMessage());
		e.printStackTrace();
		request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.EDIT_LD_LABRESULTS);
		return (mapping.findForward("default"));

	}
	manageForm.setActionMode(NEDSSConstants.VIEW_LOAD_ACTION);
	request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.VIEW_LD_LABRESULTS);
	request.setAttribute("manageList", manageForm.getManageList());
	return (mapping.findForward("default"));		
}

public ActionForward createLoadLab(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
	
	TriggerCodeForm manageForm = (TriggerCodeForm) form;
	try
	{
		manageForm.resetSelection();manageForm.setSelection(new LabResultDT());
		manageForm.setActionMode(NEDSSConstants.CREATE_LOAD_ACTION);
		manageForm.getAttributeMap().put("cancel", "/nbs/ExistingLocallyDefinedLabResults.do?method=searchLabSubmit");
		manageForm.getAttributeMap().put("submit", "/nbs/ExistingLocallyDefinedLabResults.do?method=createSubmitLab");
		
		LabResultDT dt = (LabResultDT)manageForm.getSelection();
		//prepopulate dt based on search criteria
		dt.setLaboratoryId(manageForm.getSearchCriteria("LABID"));
		
		String labTest = (String) manageForm.getSearchCriteria("LABTEST");
		if(labTest.length() > 0)dt.setLabResultCd(labTest);
		String desc = (String) manageForm.getSearchCriteria("RESULT_DESC");
		if(desc.length() > 0)dt.setLabResultDescTxt(desc);
		
	} catch (Exception e) {
		request.setAttribute("error", e.getMessage());
		logger.error("Exception in createLoadLab: " + e.getMessage());
		e.printStackTrace();
	} finally {
		request.setAttribute("manageList", manageForm.getManageList());
		request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.CREATE_LD_LABRESULTS);
	}

	return (mapping.findForward("default"));		
}	


public ActionForward createSubmitLab(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
	
	TriggerCodeForm manageForm = (TriggerCodeForm) form;
	manageForm.setManageList(null);
	LabResultDT dt = (LabResultDT) manageForm.getSelection();
	request.setAttribute("manageList", manageForm.getManageList());
	try {
		manageForm.getAttributeMap().clear();
		manageForm.setReturnToLink("<a href=\"/nbs/ExistingLocallyDefinedLabResults.do?method=searchLabSubmit\">Return To Manage Results</a> ");
		SRTAdminUtil.trimSpaces(dt);
		Object[] searchParams = new Object[] {manageForm.getSelection()};
		Object[] oParams = new Object[] { JNDINames.LOCALLY_DEFINED_LABORATORY_RESULT_DAO_CLASS, "createLocallyDefinedResult", searchParams};
		SRTAdminUtil.processRequest(oParams, request.getSession());
		
		ActionMessages messages = new ActionMessages();
		messages.add(NBSPageConstants.SUCCESS_MESSAGES_PROPERTY,
				new ActionMessage(NBSPageConstants.SAVE_SUCCESS_MESSAGE_KEY, "Lab Result"));
		request.setAttribute("success_messages", messages);
	} catch (Exception e) {
		
		logger.error("Exception in createSubmitLab: " + e.getMessage());
		e.printStackTrace();
		SRTAdminUtil.handleErrors(e, request, "create",NEDSSConstants.LABRESULTCD);
		request.setAttribute("manageList", manageForm.getManageList());
		manageForm.getAttributeMap().put("cancel", "/nbs/ExistingLocallyDefinedLabResults.do?method=searchLabSubmit");
		request.setAttribute(SRTAdminConstants.PAGE_TITLE, SRTAdminConstants.CREATE_LD_LABRESULTS);
		manageForm.setActionMode(NEDSSConstants.CREATE_LOAD_ACTION);
		return (mapping.findForward("default"));
	}
	dt.setConditionDescTxt(cdv.getConditionDesc(dt.getDefaultConditionCd()));
	manageForm.setOldDT(dt);
	manageForm.setSelection(dt);
	manageForm.getAttributeMap().remove("NORESULT");
	manageForm.setSearchCriteria("LABTEST", dt.getLabResultCd());
	manageForm.setActionMode(NEDSSConstants.VIEW_LOAD_ACTION);
	request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.CREATE_LD_LABRESULTS);
	
	return (mapping.findForward("default"));		
}	
}
