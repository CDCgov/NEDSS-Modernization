package gov.cdc.nedss.webapp.nbs.action.srtadmin;

import gov.cdc.nedss.srtadmin.dt.LabTestDT;
import gov.cdc.nedss.srtadmin.dt.LabTestLoincDT;
import gov.cdc.nedss.srtadmin.dt.LoincDT;
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

public class LabTestLoincLinkAction extends DispatchAction {
	
	static final LogUtils logger = new LogUtils(LabTestLoincLinkAction.class.getName());

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward searchLoincLoad(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		
		try {
			SrtManageForm manageForm = (SrtManageForm) form;			
			manageForm.clearSelections();
			manageForm.setSearchCriteria("LABTEST", "");
			
			manageForm.setActionMode(SRTAdminConstants.MANAGE);
			manageForm.setReturnToLink("<a href=\"/nbs/SrtAdministration.do?method=manageAdmin\">Return to System Management Main Menu</a> ");			
		} catch (Exception e) {
			logger.error("Exception in searchLoincLoad: " + e.getMessage());
			e.printStackTrace();
			request.setAttribute("error", e.getMessage());
		} finally {
			request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.MANAGE_LABTEST_LOINC_LINK);
		}
		return (mapping.findForward("default"));
		
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward searchLoincSubmit(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		
		try {
			SrtManageForm manageForm = (SrtManageForm) form;
			manageForm.setActionMode(null);
			String whereClause = SRTAdminUtil.labTestLoincSrchWhereClause(manageForm.getSearchMap());
			manageForm.getAttributeMap().clear();
			Object[] searchParams = {whereClause};
			Object[] oParams = new Object[] { JNDINames.LABTEST_LOINC_DAO_CLASS, "findLabTestLoincLink", searchParams };
			ArrayList<?> dtList = (ArrayList<?> ) SRTAdminUtil.processRequest(oParams, request.getSession());
			if(dtList.size() == 0)
				manageForm.getAttributeMap().put("NORESULT","NORESULT");
			
			manageForm.setManageList(dtList);	
			manageForm.setOldManageList(dtList);

		} catch (Exception e) {
			logger.error("Exception in searchLoincSubmit: " + e.getMessage());
			e.printStackTrace();
			SRTAdminUtil.handleErrors(e, request, "search", "");			
			return (mapping.findForward("default"));
		} finally {
			request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.MANAGE_LABTEST_LOINC_LINK);
		}
		return (mapping.findForward("results"));
		
	}	

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward resultsLoad(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		
		try {
			SrtManageForm manageForm = (SrtManageForm) form;	
			manageForm.setActionMode(null);
			request.setAttribute("manageList", manageForm.getManageList());
		} catch (Exception e) {
			request.setAttribute("error", e.getMessage());
			logger.error("Exception in resultsLoad: " + e.getMessage());
		} finally {
			request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.MANAGE_LABTEST_LOINC_LINK);
		}
		
		return (mapping.findForward("default"));
		
	}
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward createLoadLink(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		
		SrtManageForm manageForm = (SrtManageForm) form;
		try {
			manageForm.resetSelection();
			manageForm.getSearchMap().remove("LOINC");
			manageForm.setSelection(new LabTestLoincDT());
			manageForm.setActionMode(NEDSSConstants.CREATE_LOAD_ACTION);
			manageForm.getAttributeMap().put("cancel", "/nbs/LabTestLoincLink.do?method=searchLoincSubmit");
			manageForm.getAttributeMap().put("submit", "/nbs/LabTestLoincLink.do?method=createSubmitLink");
			
			LabTestLoincDT dt = (LabTestLoincDT)manageForm.getSelection();
			//prepopulate dt based on search criteria
			dt.setLaboratoryId(manageForm.getSearchCriteria("LABID"));
			String labTest = (String) manageForm.getSearchCriteria("LABTEST");
			if(labTest.length() > 0)dt.setLabTestCd(labTest);

		} catch (Exception e) {
			request.setAttribute("error", e.getMessage());
			logger.error("Exception in createLoadLink: " + e.getMessage());
		} finally {
			request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.CREATE_LABTEST_LOINC_LINK);
		}
		
		return (mapping.findForward("default"));		
	}	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward createSubmitLink(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		
		SrtManageForm manageForm = (SrtManageForm) form;
		manageForm.setManageList(null);
		manageForm.setSelection(new LabTestLoincDT());
		manageForm.setManageList(manageForm.getOldManageList());
		manageForm.setActionMode(NEDSSConstants.CREATE_LOAD_ACTION);
		manageForm.getAttributeMap().put("cancel", "/nbs/LabTestLoincLink.do?method=resultsLoad");
		manageForm.getAttributeMap().put("submit", "/nbs/LabTestLoincLink.do?method=createSubmitLink");
		LabTestLoincDT dt = (LabTestLoincDT)manageForm.getSelection();
		dt.setLaboratoryId(manageForm.getSearchCriteria("LABID"));
		String labTest = (String) manageForm.getSearchCriteria("LABTEST");
		if(labTest.length() > 0)dt.setLabTestCd(labTest);
		String loinc = (String) manageForm.getSearchCriteria("LOINC");
		if(loinc.length() > 0) dt.setLoincCd(loinc);
		
		try {
			SRTAdminUtil.trimSpaces(dt);
			manageForm.setReturnToLink("<a href=\"/nbs/LabTestLoincLink.do?method=searchLoincSubmit\">Return To Manage Results</a> ");
			Object[] searchParams = new Object[] {manageForm.getSelection()};
			Object[] oParams = new Object[] { JNDINames.LABTEST_LOINC_DAO_CLASS, "createLabTestLoincLink", searchParams};
			SRTAdminUtil.processRequest(oParams, request.getSession());
			
			ActionMessages messages = new ActionMessages();
			messages.add(NBSPageConstants.SUCCESS_MESSAGES_PROPERTY,
					new ActionMessage(NBSPageConstants.SRT_ADMIN_LINK_CREATION_SUCCESS_MESSAGE_KEY, "Lab Test", "LOINC"));
			request.setAttribute("success_messages", messages);
		} catch (Exception e) {			
			SRTAdminUtil.handleErrors(e, request, NEDSSConstants.CREATE,NEDSSConstants.LABTESTCD_LOINCCD);
			request.setAttribute("manageList", manageForm.getManageList());
			request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.CREATE_LABTEST_LOINC_LINK);
			logger.error("Exception in createSubmitLab: " + e.getMessage());
			manageForm.setActionMode(NEDSSConstants.CREATE_LOAD_ACTION);
			return (mapping.findForward("default"));
		}
		manageForm.setOldDT(dt);
		manageForm.setActionMode(NEDSSConstants.VIEW_LOAD_ACTION);
		manageForm.getAttributeMap().remove("NORESULT");
		request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.VIEW_LABTEST_LOINC_LINK);
		return (mapping.findForward("default"));		
	}
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward loincSearchLoad(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		
		try {
			SrtManageForm manageForm = (SrtManageForm) form;			
			manageForm.clearSelections();
		} catch (Exception e) {
			logger.error("Exception in loincSearchLoad: " + e.getMessage());
			request.setAttribute("error", e.getMessage());
		} 
		request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.LOINC_SEARCH);	
		return (mapping.findForward("loincSearch"));
		
	}
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward loincSearchSubmit(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		
		try {
			SrtManageForm manageForm = (SrtManageForm) form;
			manageForm.setActionMode(null);
			String whereClause = SRTAdminUtil.loincSrchWhereClause(manageForm.getSearchMap());
			manageForm.getAttributeMap().clear();			
			Object[] searchParams = {whereClause};
			Object[] oParams = new Object[] { JNDINames.LABTEST_LOINC_DAO_CLASS, "findLoinc", searchParams };
			ArrayList<?> dtList = (ArrayList<?> ) SRTAdminUtil.processRequest(oParams, request.getSession());
			if(dtList.size() > 0) {
				Iterator<?> iter = dtList.iterator();
				while(iter.hasNext()) {
					LoincDT dt = (LoincDT) iter.next();
					SRTAdminUtil.makeLoincSelectLink(dt);
				}
			} else {
				manageForm.getAttributeMap().put("NORESULT","NORESULT");
			}
			manageForm.setManageList(dtList);				
		
		} catch (Exception e) {
			logger.error("Exception in searchLoincSubmit: " + e.getMessage());
			request.setAttribute("error", e.getMessage());
			return (mapping.findForward("loincSearch"));
		}	
		return (mapping.findForward("loincResults"));
		
	}
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward loincResultsLoad(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		
		try {
			SrtManageForm manageForm = (SrtManageForm) form;		
			request.setAttribute("manageList", manageForm.getManageList());
		} catch (Exception e) {
			request.setAttribute("error", e.getMessage());
			logger.error("Exception in loincResultsLoad: " + e.getMessage());
		} 
		request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.SEARCH_RESULTS);
		return (mapping.findForward("loincSearch"));		
	}	
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward labTestSearchLoad(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		
		try {
			SrtManageForm manageForm = (SrtManageForm) form;			
			manageForm.clearSelections();
		} catch (Exception e) {
			logger.error("Exception in labTestSearchLoad: " + e.getMessage());
			request.setAttribute("error", e.getMessage());
		} 
		request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.LABTEST_SEARCH);
		return (mapping.findForward("labTestSearch"));
		
	}
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward labTestSearchSubmit(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		
		try {
			SrtManageForm manageForm = (SrtManageForm) form;
			manageForm.setActionMode(null);
			String whereClause = SRTAdminUtil.labTestPopupSrchWhereClause(manageForm.getSearchMap());
			manageForm.getAttributeMap().clear();			
			Object[] searchParams = {whereClause};
			Object[] oParams = new Object[] { JNDINames.LABTEST_LOINC_DAO_CLASS, "findLabTest", searchParams };
			ArrayList<?> dtList = (ArrayList<?> ) SRTAdminUtil.processRequest(oParams, request.getSession());
			if(dtList.size() > 0) {
				Iterator<?> iter = dtList.iterator();
				while(iter.hasNext()) {
					LabTestDT dt = (LabTestDT) iter.next();
					SRTAdminUtil.makeLabTestSelectLink(dt);
				}
			} else {
				manageForm.getAttributeMap().put("NORESULT","NORESULT");
			}
			manageForm.setManageList(dtList);				
		
		} catch (Exception e) {
			logger.error("Exception in labTestSearchSubmit: " + e.getMessage());
			request.setAttribute("error", e.getMessage());
			return (mapping.findForward("labTestSearch"));
		} 
		return (mapping.findForward("labTestResults"));
		
	}
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward labTestResultsLoad(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		
		try {
			SrtManageForm manageForm = (SrtManageForm) form;		
			request.setAttribute("manageList", manageForm.getManageList());
		} catch (Exception e) {
			request.setAttribute("error", e.getMessage());
			logger.error("Exception in labTestResultsLoad: " + e.getMessage());
		} 		
		request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.SEARCH_RESULTS);
		return (mapping.findForward("labTestSearch"));
		
	}	
	
}
