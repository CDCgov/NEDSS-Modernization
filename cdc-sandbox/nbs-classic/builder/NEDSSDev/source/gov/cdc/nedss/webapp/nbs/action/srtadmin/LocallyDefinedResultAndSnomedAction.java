package gov.cdc.nedss.webapp.nbs.action.srtadmin;

import gov.cdc.nedss.srtadmin.dt.LabResultDT;
import gov.cdc.nedss.srtadmin.dt.LabResultSnomedDT;
import gov.cdc.nedss.srtadmin.dt.SnomedDT;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.webapp.nbs.action.srtadmin.util.SRTAdminConstants;
import gov.cdc.nedss.webapp.nbs.action.srtadmin.util.SRTAdminUtil;
import gov.cdc.nedss.webapp.nbs.action.util.NBSPageConstants;
import gov.cdc.nedss.webapp.nbs.form.srtadmin.SrtManageForm;

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

public class LocallyDefinedResultAndSnomedAction extends DispatchAction{
	static final LogUtils logger = new LogUtils(LocallyDefinedResultAndSnomedAction.class.getName());

	public ActionForward searchResultSnomedLinkLoad(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		try{
			SrtManageForm manageForm = (SrtManageForm) form;
			//manageForm.setManageList(new ArrayList<Object>());
			manageForm.clearSelections();
			manageForm.setSearchCriteria("LABRESULT", "");
			manageForm.setActionMode(SRTAdminConstants.MANAGE);
			manageForm.setReturnToLink("<a href=\"/nbs/SrtAdministration.do?method=manageAdmin\">Return to System Management Main Menu</a> ");

		} catch (Exception e) {
			logger.error("Exception in searchResultSnomedLinkLoad: " + e.getMessage());
			request.setAttribute("error", e.getMessage());
		}finally {
			request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.MANAGE_RESULT_SNOMED_LINK);
		}

		return (mapping.findForward("default"));

	}
	
	

	public ActionForward searchSnomedLinkSubmit(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {

		try {
			SrtManageForm manageForm = (SrtManageForm) form;
			manageForm.setActionMode(null);
			String whereClause = SRTAdminUtil.labResultSnomedSrchWhereClause(manageForm.getSearchMap());
			manageForm.getAttributeMap().clear();
			Object[] searchParams = {whereClause};

			Object[] oParams = new Object[] { JNDINames.LABRESULT_SNOMED_DAO_CLASS, "getLabResultSnomedDTCollection", searchParams };
			ArrayList<?> dtList = (ArrayList<?> ) SRTAdminUtil.processRequest(oParams, request.getSession());
			if(dtList.size() > 0) {
			Iterator<?> iter = dtList.iterator();
				while(iter.hasNext()) {
					LabResultSnomedDT dt = (LabResultSnomedDT) iter.next();
				}
			}else {
				manageForm.getAttributeMap().put("NORESULT","NORESULT");
			}
			manageForm.setManageList(dtList);
			manageForm.setOldManageList(dtList);

		} catch (Exception e) {
			logger.error("Exception in searchLabSubmit: " + e.getMessage());
			SRTAdminUtil.handleErrors(e, request, "search", "");
			return (mapping.findForward("default"));

		}finally {
			request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.MANAGE_RESULT_SNOMED_LINK);
		}
		return (mapping.findForward("results"));

	}

public ActionForward resultsLoad(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {

		try {
			SrtManageForm manageForm = (SrtManageForm) form;
			manageForm.setActionMode(null);
			request.setAttribute("manageList", manageForm.getManageList());
		} catch (Exception e) {
			request.setAttribute("error", e.getMessage());
			logger.error("Exception in resultsLoad: " + e.getMessage());
		} finally {
			request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.MANAGE_RESULT_SNOMED_LINK);
		}

		return (mapping.findForward("default"));

	}


public ActionForward createLoadLink(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {

	SrtManageForm manageForm = (SrtManageForm) form;
	try {
		manageForm.resetSelection();manageForm.setSelection(new LabResultSnomedDT());
		manageForm.setActionMode(NEDSSConstants.CREATE_LOAD_ACTION);
		manageForm.getAttributeMap().put("cancel", "/nbs/ExistingResultsSnomedLink.do?method=searchSnomedLinkSubmit");
		manageForm.getAttributeMap().put("submit", "/nbs/ExistingResultsSnomedLink.do?method=createSubmitLink");

		LabResultSnomedDT dt = (LabResultSnomedDT)manageForm.getSelection();
		//prepopulate dt based on search criteria
		dt.setLaboratoryID(manageForm.getSearchCriteria("LABID"));
		String labResult = manageForm.getSearchCriteria("LABRESULT");
		if(labResult.length() > 0)dt.setLabResultCd(labResult);
	} catch (Exception e) {
		request.setAttribute("error", e.getMessage());
		logger.error("Exception in createLoadLink: " + e.getMessage());
	} finally {

		request.setAttribute("manageList", manageForm.getManageList());
		request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.CREATE_RESULT_SNOMED_LINK);
	}

	return (mapping.findForward("default"));
}


public ActionForward createSubmitLink(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {

	SrtManageForm manageForm = (SrtManageForm) form;
	manageForm.setSelection(new LabResultSnomedDT());
	manageForm.setManageList(manageForm.getOldManageList());
	manageForm.setActionMode(NEDSSConstants.CREATE_LOAD_ACTION);
	manageForm.getAttributeMap().put("cancel", "/nbs/ExistingResultsSnomedLink.do?method=resultsLoad");
	manageForm.getAttributeMap().put("submit", "/nbs/ExistingResultsSnomedLink.do?method=createSubmitLink");
	LabResultSnomedDT dt = (LabResultSnomedDT)manageForm.getSelection();
	dt.setLaboratoryID(manageForm.getSearchCriteria("LABID"));
	String labResult = manageForm.getSearchCriteria("LABRESULT");
	if(labResult!= null && labResult.length() > 0)dt.setLabResultCd(labResult);
	String smoned = manageForm.getSearchCriteria("SNOMED");
	if(smoned !=null && smoned.length() > 0) dt.setSnomedCd(smoned);

	//request.setAttribute("manageList", manageForm.getManageList());
	try {
		SRTAdminUtil.trimSpaces(dt);
		manageForm.setReturnToLink("<a href=\"/nbs/ExistingResultsSnomedLink.do?method=searchSnomedLinkSubmit\">Return To Manage Results</a> ");
		Object[] searchParams = new Object[] {manageForm.getSelection()};
		Object[] oParams = new Object[] { JNDINames.LABRESULT_SNOMED_DAO_CLASS, "createLabResultSnomedLink", searchParams};
		SRTAdminUtil.processRequest(oParams, request.getSession());
		
		ActionMessages messages = new ActionMessages();
		messages.add(NBSPageConstants.SUCCESS_MESSAGES_PROPERTY,
				new ActionMessage(NBSPageConstants.SRT_ADMIN_LINK_CREATION_SUCCESS_MESSAGE_KEY, "Lab Result", "SNOMED"));
		request.setAttribute("success_messages", messages);
	} catch (Exception e) {
		SRTAdminUtil.handleErrors(e, request, NEDSSConstants.CREATE, NEDSSConstants.LABRESULTCD_SNOMEDCD);
		request.setAttribute("manageList", manageForm.getManageList());
		//request.setAttribute("error", e.getMessage());
		request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.CREATE_RESULT_SNOMED_LINK);
		logger.error("Exception in createSubmitLab: " + e.getMessage());
		return (mapping.findForward("default"));
	}
	manageForm.setOldDT(dt);
	manageForm.setActionMode(NEDSSConstants.VIEW_LOAD_ACTION);
	request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.VIEW_RESULT_SNOMED_LINK);
	return (mapping.findForward("default"));
}


public ActionForward SnomedSearchLoad(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {

	try {
		SrtManageForm manageForm = (SrtManageForm) form;
		manageForm.clearSelections();
	} catch (Exception e) {
		logger.error("Exception in SnomedSearchLoad: " + e.getMessage());
		request.setAttribute("error", e.getMessage());
	}

	return (mapping.findForward("searchPopup"));

}

public ActionForward LabResultCdSearchLoad(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {

	try {
		SrtManageForm manageForm = (SrtManageForm) form;
		manageForm.clearSelections();
	} catch (Exception e) {
		logger.error("Exception in LabResultCdSearchLoad: " + e.getMessage());
		request.setAttribute("error", e.getMessage());
	}

	return (mapping.findForward("LabResultPopup"));

}

public ActionForward labResultSearchSubmit(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {

	try {
		SrtManageForm manageForm = (SrtManageForm) form;
		manageForm.setActionMode(null);
		String whereClause = SRTAdminUtil.labResultSrchWhereClause(manageForm.getSearchMap());
		manageForm.getAttributeMap().clear();
		Object[] searchParams = {whereClause};
		Object[] oParams = new Object[] { JNDINames.LABRESULT_SNOMED_DAO_CLASS, "findLabResult", searchParams };
		ArrayList<Object> dtList = (ArrayList<Object> ) SRTAdminUtil.processRequest(oParams, request.getSession());
		if(dtList.size() > 0) {
			Iterator<Object> iter = dtList.iterator();
			while(iter.hasNext()) {
				LabResultDT dt = (LabResultDT) iter.next();
				SRTAdminUtil.makeLabResultSelectLink(dt);
			}
		} else {
			manageForm.getAttributeMap().put("NORESULT","NORESULT");
		}
		manageForm.setManageList(dtList);

	} catch (Exception e) {
		logger.error("Exception in labResultSearchSubmit: " + e.getMessage());
		request.setAttribute("error", e.getMessage());
		return (mapping.findForward("default"));
	} finally {
		request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.SEARCH_SNOMED);
	}
	return (mapping.findForward("LabResultCdResults"));

}
public ActionForward snomedSearchSubmit(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {

	try {
		SrtManageForm manageForm = (SrtManageForm) form;
		manageForm.setActionMode(null);
		String whereClause = SRTAdminUtil.snomedSrchWhereClause(manageForm.getSearchMap());
		manageForm.getAttributeMap().clear();
		Object[] searchParams = {whereClause};
		Object[] oParams = new Object[] { JNDINames.LABRESULT_SNOMED_DAO_CLASS, "findSnomed", searchParams };
		ArrayList<Object> dtList = (ArrayList<Object> ) SRTAdminUtil.processRequest(oParams, request.getSession());
		if(dtList.size() > 0) {
			Iterator<Object> iter = dtList.iterator();
			while(iter.hasNext()) {
				SnomedDT dt = (SnomedDT) iter.next();
				SRTAdminUtil.makeSnomedSelectLink(dt);
			}
		} else {
			manageForm.getAttributeMap().put("NORESULT","NORESULT");
		}
		manageForm.setManageList(dtList);

	} catch (Exception e) {
		logger.error("Exception in labResultSearchSubmit: " + e.getMessage());
		SRTAdminUtil.handleErrors(e, request, "search", "");
		return (mapping.findForward("default"));
	} finally {
		request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.SEARCH_SNOMED);
	}
	return (mapping.findForward("snomedResults"));

}
public ActionForward LabResultCdResultsLoad(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {

	try {
		SrtManageForm manageForm = (SrtManageForm) form;
		request.setAttribute("manageList", manageForm.getManageList());
	} catch (Exception e) {
		request.setAttribute("error", e.getMessage());
		logger.error("Exception in LabResultCdResultsLoad: " + e.getMessage());
	} finally {
		request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.SEARCH_SNOMED);
	}

	return (mapping.findForward("LabResultPopup"));

}
public ActionForward snomedResultsLoad(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {

	try {
		SrtManageForm manageForm = (SrtManageForm) form;
		request.setAttribute("manageList", manageForm.getManageList());
	} catch (Exception e) {
		request.setAttribute("error", e.getMessage());
		logger.error("Exception in snomedResultsLoad: " + e.getMessage());
	} finally {
		request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.SEARCH_SNOMED);
	}

	return (mapping.findForward("searchPopup"));

}
}
