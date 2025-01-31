package gov.cdc.nedss.webapp.nbs.action.srtadmin;

import gov.cdc.nedss.srtadmin.dt.LabCodingSystemDT;
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

public class LaboratoryAction extends DispatchAction {
	
	static final LogUtils logger = new LogUtils(LaboratoryAction.class.getName());
	static final String VIEW = "VIEW";
	static final String EDIT = "EDIT";
	private static final String daoName = JNDINames.LABORATORY_DAO_CLASS;
	
	public ActionForward searchLab(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		
		try {
			SrtManageForm manageForm = (SrtManageForm) form;			
			manageForm.clearSelections();
			manageForm.setSearchCriteria("LAB", "");
			manageForm.setActionMode(SRTAdminConstants.MANAGE);
			manageForm.setReturnToLink("<a href=\"/nbs/SrtAdministration.do?method=manageAdmin\">Return to System Management Main Menu</a> ");			
		} catch (Exception e) {
			logger.error("Exception in searchLab: " + e.getMessage());
			e.printStackTrace();
			request.setAttribute("error", e.getMessage());
		} finally {
			request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.MANAGE_LABORATORY);
		}
		return (mapping.findForward("default"));
		
	}

	public ActionForward searchLabSubmit(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		
		try {
			SrtManageForm manageForm = (SrtManageForm) form;
			manageForm.setActionMode(null);
			manageForm.resetSelection();
			String whereClause = SRTAdminUtil.laboratoryWhereClause(manageForm.getSearchMap());
			manageForm.getAttributeMap().clear();			
			Object[] searchParams = {whereClause};

				
			Object[] oParams = new Object[] { daoName, "findLaboratory", searchParams };
			ArrayList<?> dtList = (ArrayList<?> ) SRTAdminUtil.processRequest(oParams, request.getSession());
			if(dtList.size() > 0) {
				Iterator<?> iter = dtList.iterator();
				while(iter.hasNext()) {
					LabCodingSystemDT dt = (LabCodingSystemDT) iter.next();
					SRTAdminUtil.makeLabLink(dt, VIEW);
					SRTAdminUtil.makeLabLink(dt, EDIT);

				}
			} else {
				manageForm.getAttributeMap().put("NORESULT","NORESULT");
			}
			manageForm.setManageList(dtList);				
		
		} catch (Exception e) {
			logger.error("Exception in searchLabSubmit: " + e.getMessage());
			e.printStackTrace();
			SRTAdminUtil.handleErrors(e, request, "search", "");
			return (mapping.findForward("default"));
		} finally {
			request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.MANAGE_LABORATORY);
		}
		return (mapping.findForward("results"));
		
	}	
	
	
	public ActionForward resultsLoad(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		
		try {
			SrtManageForm manageForm = (SrtManageForm) form;		
			request.setAttribute("manageList", manageForm.getManageList());
		} catch (Exception e) {
			request.setAttribute("error", e.getMessage());
			logger.error("Exception in resultsLoad: " + e.getMessage());
			e.printStackTrace();
		} finally {
			request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.MANAGE_LABORATORY);
		}
		
		return (mapping.findForward("default"));
		
	}
	
	
	public ActionForward viewLab(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		
		SrtManageForm manageForm = (SrtManageForm) form;
		try {
			manageForm.setActionMode(NEDSSConstants.VIEW_LOAD_ACTION);
			
			String cnxt = request.getParameter("context");
			if(cnxt != null && cnxt.equalsIgnoreCase("cancel")) {				
				manageForm.setSelection(manageForm.getOldDT());
				return (mapping.findForward("default"));
			}			
			String laboratoryId = request.getParameter("laboratoryId");
			if(laboratoryId != null && laboratoryId.length() > 0) {
				ArrayList<?> testList = manageForm.getManageList();
				Iterator<?> iter = testList.iterator();
				while(iter.hasNext()) {			
					LabCodingSystemDT dt = (LabCodingSystemDT) iter.next();
					if(dt.getLaboratoryId().equalsIgnoreCase(laboratoryId) ) {
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
		} finally {
			request.setAttribute("manageList", manageForm.getManageList());
			request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.VIEW_LABORATORY);
		}
		return (mapping.findForward("default"));
	}
	
	public ActionForward editLab(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		
		SrtManageForm manageForm = (SrtManageForm) form;		
		try {
			manageForm.setActionMode(NEDSSConstants.EDIT_LOAD_ACTION);				
			String laboratoryId = request.getParameter("laboratoryId");
			
			if(laboratoryId != null && laboratoryId.length() > 0) {
				ArrayList<?> testList = manageForm.getManageList();
				Iterator<?> iter = testList.iterator();
				while(iter.hasNext()) {			
					LabCodingSystemDT dt = (LabCodingSystemDT) iter.next();
					if(dt.getLaboratoryId().equalsIgnoreCase(laboratoryId)) {
						manageForm.setSelection(dt);						
						manageForm.setOldDT(dt);
						break;
					}
				}				
			}				
			manageForm.getAttributeMap().put("cancel", "/nbs/Laboratories.do?method=viewLab&context=cancel#laboratory");
			manageForm.getAttributeMap().put("submit", "/nbs/Laboratories.do?method=updateLab");
		} catch (Exception e) {
			request.setAttribute("error", e.getMessage());
			logger.error("Exception in editLab: " + e.getMessage());
			e.printStackTrace();
		} finally {
			request.setAttribute("manageList", manageForm.getManageList());
			request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.EDIT_LABORATORY);
		}
		
		return (mapping.findForward("default"));		
		
	}
	
	public ActionForward createLoadLab(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		
		SrtManageForm manageForm = (SrtManageForm) form;
		try {
			manageForm.resetSelection();
			manageForm.setSelection(new LabCodingSystemDT());
			manageForm.setActionMode(NEDSSConstants.CREATE_LOAD_ACTION);
			manageForm.getAttributeMap().put("cancel", "/nbs/Laboratories.do?method=searchLabSubmit");
			manageForm.getAttributeMap().put("submit", "/nbs/Laboratories.do?method=createSubmitLab");
			
			LabCodingSystemDT dt = (LabCodingSystemDT)manageForm.getSelection();
			//prepopulate dt based on search criteria
			dt.setLaboratorySystemDescTxt(manageForm.getSearchCriteria("LAB"));
			
		} catch (Exception e) {
			request.setAttribute("error", e.getMessage());
			logger.error("Exception in createLoadLab: " + e.getMessage());
			e.printStackTrace();
		} finally {
			request.setAttribute("manageList", manageForm.getManageList());
			request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.CREATE_LABORATORY);
		}
		
		return (mapping.findForward("default"));		
	}	
	
	public ActionForward createSubmitLab(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		
		SrtManageForm manageForm = (SrtManageForm) form;
		manageForm.setManageList(null);
		LabCodingSystemDT dt = (LabCodingSystemDT) manageForm.getSelection();
		request.setAttribute("manageList", manageForm.getManageList());
		try {
			SRTAdminUtil.trimSpaces(dt);
			Object[] searchParams = new Object[] {dt};
			Object[] oParams = new Object[] { daoName, "createLaboratory", searchParams};
			SRTAdminUtil.processRequest(oParams, request.getSession());
			
			ActionMessages messages = new ActionMessages();
			messages.add(NBSPageConstants.SUCCESS_MESSAGES_PROPERTY,
					new ActionMessage(NBSPageConstants.SAVE_SUCCESS_MESSAGE_KEY, "Laboratory"));
			request.setAttribute("success_messages", messages);
			
			
		} catch (Exception e) {
			SRTAdminUtil.handleErrors(e, request, NEDSSConstants.CREATE,NEDSSConstants.LABID);		
			request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.CREATE_LABORATORY);
			logger.error("Exception in createSubmitLab: " + e.getMessage());
			e.printStackTrace();
			return (mapping.findForward("default"));
		}
		manageForm.setSelection(dt); 
		manageForm.setOldDT(dt);
		manageForm.setSearchCriteria("LAB", dt.getLaboratorySystemDescTxt());
		manageForm.setActionMode(NEDSSConstants.VIEW_LOAD_ACTION);
		manageForm.getAttributeMap().remove("NORESULT");
		request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.VIEW_LABORATORY);
		return (mapping.findForward("default"));		
	}	

	public ActionForward updateLab(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		
		SrtManageForm manageForm = (SrtManageForm) form;
		manageForm.setManageList(null);
		LabCodingSystemDT dt = (LabCodingSystemDT) manageForm.getSelection();

		try {
			SRTAdminUtil.trimSpaces(dt);
			Object[] searchParams = new Object[] {dt};
			Object[] oParams = new Object[] { daoName, "updateLaboratory", searchParams };
			SRTAdminUtil.processRequest(oParams, request.getSession());

			ActionMessages messages = new ActionMessages();
			messages.add(NBSPageConstants.SUCCESS_MESSAGES_PROPERTY,
					new ActionMessage(NBSPageConstants.SAVE_SUCCESS_MESSAGE_KEY, "Laboratory"));
			request.setAttribute("success_messages", messages);

		} catch (Exception e) {
			SRTAdminUtil.handleErrors(e, request, "edit",NEDSSConstants.LABID);
			request.setAttribute("manageList", manageForm.getManageList());
			logger.error("Exception in updateLab: " + e.getMessage());
			e.printStackTrace();
			request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.EDIT_LABORATORY);
			return (mapping.findForward("default"));
		} 
		manageForm.setActionMode(NEDSSConstants.VIEW_LOAD_ACTION);
		request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.VIEW_LABORATORY);
		request.setAttribute("manageList", manageForm.getManageList());

		return (mapping.findForward("default"));		
	}
}
