package gov.cdc.nedss.webapp.nbs.action.srtadmin;


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

public class ManageLoincsAction extends DispatchAction {

	static final LogUtils logger = new LogUtils(ManageLoincsAction.class.getName());
	static final String VIEW = "VIEW";
	static final String EDIT = "EDIT";

	public ActionForward manageLoincs(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {

		try {
			SrtManageForm manageForm = (SrtManageForm) form;
			manageForm.clearSelections();
			manageForm.setSearchCriteria("LOINC", "");

			manageForm.setActionMode(SRTAdminConstants.MANAGE);
			manageForm.setReturnToLink("<a href=\"/nbs/SrtAdministration.do?method=manageAdmin\">Return to System Management Main Menu</a> ");
		} catch (Exception e) {
			logger.error("Exception in manageLoincs: " + e.getMessage());
			e.printStackTrace();
			request.setAttribute("error", e.getMessage());
		} finally {
			request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.MANAGE_LOINC);
		}
		return (mapping.findForward("default"));

	}

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

		} catch (Exception e) {
			logger.error("Exception in searchLoincSubmit: " + e.getMessage());
			e.printStackTrace();
			SRTAdminUtil.handleErrors(e, request, "search", "");
			return (mapping.findForward("default"));
		} finally {
			request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.MANAGE_LOINC);
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
			request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.MANAGE_LOINC);
		}

		return (mapping.findForward("default"));

	}

	public ActionForward loincSearchLoad(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {

		try {
			SrtManageForm manageForm = (SrtManageForm) form;
			manageForm.clearSelections();
		} catch (Exception e) {
			logger.error("Exception in loincSearchLoad: " + e.getMessage());
			e.printStackTrace();
			request.setAttribute("error", e.getMessage());
		}
		request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.LOINC_SEARCH);
		return (mapping.findForward("loincSearch"));

	}

	public ActionForward loincSearchSubmit(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {

		try {
			SrtManageForm manageForm = (SrtManageForm) form;
			manageForm.setActionMode(null);
			String whereClause = SRTAdminUtil.loincSrchWhereClause(manageForm.getSearchMap());
			manageForm.getAttributeMap().clear();
			Object[] searchParams = {whereClause};
			Object[] oParams = new Object[] { JNDINames.LABTEST_LOINC_DAO_CLASS, "findManageLoinc", searchParams };
			ArrayList<?> dtList = (ArrayList<?> ) SRTAdminUtil.processRequest(oParams, request.getSession());
			if(dtList.size() > 0) {
				Iterator<?> iter = dtList.iterator();
				while(iter.hasNext()) {
					LoincDT dt = (LoincDT) iter.next();
					SRTAdminUtil.makeLoincLink(dt,VIEW);
					SRTAdminUtil.makeLoincLink(dt,EDIT);
					if(dt.getPaDerivationExcludeCd() != null && dt.getPaDerivationExcludeCd().equals("Y"))
						dt.setPaDerivationExcludeCd("1");
				}
			} else {
				manageForm.getAttributeMap().put("NORESULT","NORESULT");
			}
			manageForm.setManageList(dtList);

		} catch (Exception e) {
			logger.error("Exception in loincSearchSubmit: " + e.getMessage());
			e.printStackTrace();
			request.setAttribute("error", e.getMessage());
			return (mapping.findForward("loincSearch"));
		}
		return (mapping.findForward("loincResults"));

	}
	public ActionForward loincResultsLoad(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {

		try {
			SrtManageForm manageForm = (SrtManageForm) form;
			request.setAttribute("manageList", manageForm.getManageList());
		} catch (Exception e) {
			request.setAttribute("error", e.getMessage());
			logger.error("Exception in loincResultsLoad: " + e.getMessage());
			e.printStackTrace();
		}
		request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.MANAGE_LOINC);
		return (mapping.findForward("loincSearch"));
	}


public ActionForward viewLoinc(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {

		SrtManageForm manageForm = (SrtManageForm) form;
		try {
			manageForm.setActionMode(NEDSSConstants.VIEW_LOAD_ACTION);
			manageForm.setReturnToLink("<a href=\"/nbs/ManageLoincs.do?method=loincSearchSubmit\">Return To Manage Results</a> ");
			String cnxt = request.getParameter("context");
			if(cnxt != null && cnxt.equalsIgnoreCase("cancel")) {
				manageForm.setSelection(manageForm.getOldDT());
				return (mapping.findForward("default"));
			}
				String loincCd = request.getParameter("loinc_cd");

				if(loincCd != null && loincCd.length() > 0 ) {
					ArrayList<?> testList = manageForm.getManageList();
					Iterator<?> iter = testList.iterator();
					while(iter.hasNext()) {
						LoincDT dt = (LoincDT) iter.next();
						if(dt.getLoincCd().equalsIgnoreCase(loincCd)) {
							manageForm.setSelection(dt);
							manageForm.setOldDT(dt);
							break;
						}
					}
				}

		} catch (Exception e) {
			request.setAttribute("error", e.getMessage());
			logger.error("Exception in viewLoinc: " + e.getMessage());
			e.printStackTrace();
		} finally {
			request.setAttribute("manageList", manageForm.getManageList());
			request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.VIEW_LOINC);
		}
		return (mapping.findForward("default"));
	}

	public ActionForward editLoinc(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {

		SrtManageForm manageForm = (SrtManageForm) form;
		try {
			manageForm.setActionMode(NEDSSConstants.EDIT_LOAD_ACTION);

				String loincCd = request.getParameter("loinc_cd");
				if(loincCd != null && loincCd.length() > 0) {
					ArrayList<?> testList = manageForm.getManageList();
					Iterator<?> iter = testList.iterator();
					while(iter.hasNext()) {
						LoincDT dt = (LoincDT) iter.next();
						if(dt.getLoincCd().equalsIgnoreCase(loincCd)) {
							manageForm.setSelection(dt);
							manageForm.setOldDT(dt);
							break;
						}
					}
				}

			manageForm.getAttributeMap().put("cancel", "/nbs/ManageLoincs.do?method=viewLoinc&context=cancel#loinc");
			manageForm.getAttributeMap().put("submit", "/nbs/ManageLoincs.do?method=updateLoinc#loinc");
		} catch (Exception e) {
			SRTAdminUtil.handleErrors(e, request, "edit",NEDSSConstants.LOINCCD);
			request.setAttribute("manageList", manageForm.getManageList());
			logger.error("Exception in editLoinc: " + e.getMessage());
			e.printStackTrace();
		} finally {
			request.setAttribute("manageList", manageForm.getManageList());
			request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.EDIT_LOINC);
		}

		return (mapping.findForward("default"));

	}

   public ActionForward updateLoinc(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {

		SrtManageForm manageForm = (SrtManageForm) form;
		manageForm.setManageList(null);
		LoincDT dt = (LoincDT) manageForm.getSelection();

		try {
			SRTAdminUtil.trimSpaces(dt);
			manageForm.setReturnToLink("<a href=\"/nbs/ManageLoincs.do?method=loincSearchSubmit\">Return To Manage Results</a> ");
			Object[] searchParams = new Object[] {dt};
			Object[] oParams = new Object[] { JNDINames.MANAGE_LOINCS_DAO_CLASS, "updateLoincs", searchParams };
			SRTAdminUtil.processRequest(oParams, request.getSession());
			
			ActionMessages messages = new ActionMessages();
			messages.add(NBSPageConstants.SUCCESS_MESSAGES_PROPERTY,
					new ActionMessage(NBSPageConstants.SAVE_SUCCESS_MESSAGE_KEY, "LOINC"));
			request.setAttribute("success_messages", messages);
			
		} catch (Exception e) {
			SRTAdminUtil.handleErrors(e, request, NEDSSConstants.EDIT,NEDSSConstants.LOINCCD);
			request.setAttribute("manageList", manageForm.getManageList());
			logger.error("Exception in updateLoinc: " + e.getMessage());
			e.printStackTrace();
			request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.EDIT_LOINC);
			return (mapping.findForward("default"));
		}
		
		manageForm.setActionMode(NEDSSConstants.VIEW_LOAD_ACTION);
		request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.VIEW_LOINC);
		request.setAttribute("manageList", manageForm.getManageList());

		return (mapping.findForward("default"));
	}

   public ActionForward createManageLoinc(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {

	   SrtManageForm manageForm = (SrtManageForm) form;
	   try {

		manageForm.resetSelection();
		LoincDT loincDT =  new LoincDT();
		manageForm.setSelection(loincDT);
		LoincDT dt = (LoincDT)manageForm.getSelection();
		dt.setLoincCd(manageForm.getSearchCriteria("LOINC_CD"));
		dt.setComponentName(manageForm.getSearchCriteria("LOINC"));
		manageForm.setActionMode(NEDSSConstants.CREATE_LOAD_ACTION);
		manageForm.getAttributeMap().put("cancel", "/nbs/ManageLoincs.do?method=loincSearchSubmit");
		manageForm.getAttributeMap().put("submit", "/nbs/ManageLoincs.do?method=createLoincSubmit");

	} catch (Exception e) {
		request.setAttribute("error", e.getMessage());
		logger.error("Exception in createManageLoinc: " + e.getMessage());
		e.printStackTrace();
	} finally {
		request.setAttribute("manageList", manageForm.getManageList());
		request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.CREATE_LOINC);
	}

	return (mapping.findForward("default"));
}

   public ActionForward createLoincSubmit(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {

		SrtManageForm manageForm = (SrtManageForm) form;
		manageForm.setManageList(null);
		LoincDT dt = (LoincDT) manageForm.getSelection();
		try {
			SRTAdminUtil.trimSpaces(dt);
			manageForm.setReturnToLink("<a href=\"/nbs/ManageLoincs.do?method=loincSearchSubmit\">Return To Manage Results</a> ");
			Object[] searchParams = new Object[] { dt };
			Object[] oParams = new Object[] { JNDINames.MANAGE_LOINCS_DAO_CLASS, "createLoincs", searchParams};
			SRTAdminUtil.processRequest(oParams, request.getSession());
			
			ActionMessages messages = new ActionMessages();
			messages.add(NBSPageConstants.SUCCESS_MESSAGES_PROPERTY,
					new ActionMessage(NBSPageConstants.SAVE_SUCCESS_MESSAGE_KEY, "LOINC"));
			request.setAttribute("success_messages", messages);
		} catch (Exception e) {
			SRTAdminUtil.handleErrors(e, request, NEDSSConstants.CREATE,NEDSSConstants.LOINCCD);
			request.setAttribute("manageList", manageForm.getManageList());
			request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.CREATE_LOINC);
			logger.error("Exception in createLoincSubmit: " + e.getMessage());
			e.printStackTrace();
			return (mapping.findForward("default"));
		}

		manageForm.setSelection(dt);
		manageForm.setOldDT(dt);
		manageForm.setActionMode(NEDSSConstants.VIEW_LOAD_ACTION);
		manageForm.getAttributeMap().remove("NORESULT");
		request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.VIEW_LOINC);
		return (mapping.findForward("default"));
	}
}
