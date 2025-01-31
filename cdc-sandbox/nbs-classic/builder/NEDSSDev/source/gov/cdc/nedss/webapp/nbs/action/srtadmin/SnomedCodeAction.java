package gov.cdc.nedss.webapp.nbs.action.srtadmin;

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

public class SnomedCodeAction extends DispatchAction{
	static final LogUtils logger = new LogUtils(SnomedCodeAction.class.getName());
	static final String VIEW="VIEW";
	static final String EDIT="EDIT";


	public ActionForward manageSnomedCodeLoad(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		try{
			SrtManageForm manageForm = (SrtManageForm) form;
			manageForm.clearSelections();
			manageForm.setSearchCriteria("SNOMED", "");
			manageForm.setActionMode(SRTAdminConstants.MANAGE);
			manageForm.setReturnToLink("<a href=\"/nbs/SrtAdministration.do?method=manageAdmin\">Return to System Management Main Menu</a> ");

		} catch (Exception e) {
			logger.error("Exception in manageSnomedCodeLoad: " + e.getMessage());
			e.printStackTrace();
			request.setAttribute("error", e.getMessage());
		}finally {
			request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.MANAGE_SNOMED_CODE);
		}

		return (mapping.findForward("default"));

	}
	public ActionForward searchSnomedSubmit(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)
	{
		SrtManageForm manageForm = (SrtManageForm) form;
		try
		{
			manageForm.setActionMode(null);
			String whereClause = SRTAdminUtil.snomedSrchWhereClause(manageForm.getSearchMap());
			manageForm.getAttributeMap().clear();
			Object[] searchParams = {whereClause};

			Object[] oParams = new Object[] { JNDINames.SNOMED_DAO_CLASS, "getSnomedDTCollection", searchParams };
			ArrayList<?> dtList = (ArrayList<?> ) SRTAdminUtil.processRequest(oParams, request.getSession());
			if(dtList.size() > 0) {
			Iterator<?> iter = dtList.iterator();
				while(iter.hasNext()) {
					SnomedDT dt = (SnomedDT) iter.next();
					String cd = dt.getSnomedCd();
					dt.setSnomedCd(cd);
					SRTAdminUtil.makeSnomedLink(dt, EDIT);
					if(dt.getPaDerivationExcludeCd() != null && dt.getPaDerivationExcludeCd().equals("Y"))
						dt.setPaDerivationExcludeCd("1");
				}
			}else {
				manageForm.getAttributeMap().put("NORESULT","NORESULT");
			}
			manageForm.setManageList(dtList);

	} catch (Exception e) {
		logger.error("Exception in searchSnomedSubmit: " + e.getMessage());
		e.printStackTrace();
		SRTAdminUtil.handleErrors(e, request, "search", "");
		return (mapping.findForward("default"));

	}finally {
		request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.MANAGE_SNOMED_CODE);
	}
		return (mapping.findForward("results"));
	}
public ActionForward resultsLoad(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)
{
	try
	{
		SrtManageForm manageForm = (SrtManageForm) form;
		request.setAttribute("manageList", manageForm.getManageList());
	} catch (Exception e) {
		request.setAttribute("error", e.getMessage());
		logger.error("Exception in resultsLoad: " + e.getMessage());
		e.printStackTrace();
	} finally {
		request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.MANAGE_SNOMED_CODE);
	}

	return (mapping.findForward("default"));
}
public ActionForward editSnomed(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {

	SrtManageForm manageForm = (SrtManageForm) form;
	try
	{
		manageForm.setActionMode(NEDSSConstants.EDIT_LOAD_ACTION);
		String snomedCd = request.getParameter("SnomedCd");

		if((snomedCd != null && snomedCd.length() > 0) ) {
			ArrayList<?> snomedList = manageForm.getManageList();
			Iterator<?> iter = snomedList.iterator();
			while(iter.hasNext()) {
				SnomedDT dt = (SnomedDT) iter.next();
				if( dt.getSnomedCd().equalsIgnoreCase(snomedCd)){
					manageForm.setSelection(dt);
					manageForm.setOldDT(dt);
					break;
				}
			}
		}
		manageForm.getAttributeMap().put("cancel", "/nbs/SnomedCode.do?method=viewSnomed&context=cancel#Snomed");
		manageForm.getAttributeMap().put("submit", "/nbs/SnomedCode.do?method=updateSnomed");
	} catch (Exception e) {
		request.setAttribute("error", e.getMessage());
		logger.error("Exception in editSnomed: " + e.getMessage());
		e.printStackTrace();
	} finally {
		request.setAttribute("manageList", manageForm.getManageList());
		request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.EDIT_SNOMED_CODE);
	}


		return (mapping.findForward("default"));

}
public ActionForward updateSnomed(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {

	SrtManageForm manageForm = (SrtManageForm) form;
	manageForm.setManageList(null);
	SnomedDT dt=(SnomedDT)manageForm.getSelection();
	try {
		SRTAdminUtil.trimSpaces(dt);
		Object[] searchParams = new Object[] {dt};
		Object[] oParams = new Object[] { JNDINames.SNOMED_DAO_CLASS, "updateSnomedCode", searchParams };
		SRTAdminUtil.processRequest(oParams, request.getSession());
		
		ActionMessages messages = new ActionMessages();
		messages.add(NBSPageConstants.SUCCESS_MESSAGES_PROPERTY,
				new ActionMessage(NBSPageConstants.SAVE_SUCCESS_MESSAGE_KEY, "SNOMED"));
		request.setAttribute("success_messages", messages);
	} catch (Exception e) {
		SRTAdminUtil.handleErrors(e, request, NEDSSConstants.EDIT, NEDSSConstants.SNOMEDCD);
		request.setAttribute("manageList", manageForm.getManageList());
		request.setAttribute("error", e.getMessage());
		logger.error("Exception in updateSnomed: " + e.getMessage());
		e.printStackTrace();
		request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.EDIT_SNOMED_CODE);

		return (mapping.findForward("default"));

	}finally {
	}
	manageForm.setActionMode(NEDSSConstants.VIEW_LOAD_ACTION);
	request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.VIEW_SNOMED_CODE);
	request.setAttribute("manageList", manageForm.getManageList());
	return (mapping.findForward("default"));
}

public ActionForward createSnomedCode(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
{
	SrtManageForm manageForm = (SrtManageForm) form;
	try
	{
		manageForm.resetSelection();
		SnomedDT dt = new SnomedDT();
		dt.setSnomedCd(manageForm.getSearchCriteria("SNOMED_CD"));
		dt.setSnomedDescTx(manageForm.getSearchCriteria("SNOMED"));
		manageForm.setSelection(dt);
		manageForm.setActionMode(NEDSSConstants.CREATE_LOAD_ACTION);
		manageForm.getAttributeMap().put("cancel", "/nbs/SnomedCode.do?method=searchSnomedSubmit");
		manageForm.getAttributeMap().put("submit", "/nbs/SnomedCode.do?method=createSubmitSnomed");

	} catch (Exception e) {
		request.setAttribute("error1", e.getMessage());
		logger.error("Exception in createSnomedCode: " + e.getMessage());
		e.printStackTrace();
	} finally {
		request.setAttribute("manageList", manageForm.getManageList());
		request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.CREATE_SNOMED_CODE);
	}

	return (mapping.findForward("default"));
}

public ActionForward createSubmitSnomed(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
{
	SrtManageForm manageForm = (SrtManageForm) form;
	manageForm.setManageList(null);
	SnomedDT dt = (SnomedDT) manageForm.getSelection();
	request.setAttribute("manageList", manageForm.getManageList());
	try{
		manageForm.getAttributeMap().clear();
		SRTAdminUtil.trimSpaces(dt);
		Object[] searchParams = new Object[] {manageForm.getSelection()};
		Object[] oParams = new Object[] { JNDINames.SNOMED_DAO_CLASS, "CreateSnomedCode", searchParams};
		SRTAdminUtil.processRequest(oParams, request.getSession());
		
		ActionMessages messages = new ActionMessages();
		messages.add(NBSPageConstants.SUCCESS_MESSAGES_PROPERTY,
				new ActionMessage(NBSPageConstants.SAVE_SUCCESS_MESSAGE_KEY, "SNOMED"));
		request.setAttribute("success_messages", messages);

	}catch (Exception e){
		logger.error("Exception in createSubmitSnomed: " + e.getMessage());
		e.printStackTrace();
		//request.setAttribute("error","Error In Database, Please insert a different SNOMED code");
		SRTAdminUtil.handleErrors(e, request, NEDSSConstants.CREATE,NEDSSConstants.SNOMED_CD);
		request.setAttribute("manageList", manageForm.getManageList());
		manageForm.getAttributeMap().put("cancel", "/nbs/SnomedCode.do?method=searchSnomedSubmit");
		request.setAttribute(SRTAdminConstants.PAGE_TITLE, SRTAdminConstants.CREATE_SNOMED_CODE);
		return (mapping.findForward("default"));
	}
	request.setAttribute("manageList", manageForm.getManageList());
	manageForm.setSelection(dt);
	manageForm.getAttributeMap().remove("NORESULT");
	manageForm.setActionMode(NEDSSConstants.VIEW_LOAD_ACTION);
	request.setAttribute(SRTAdminConstants.PAGE_TITLE, SRTAdminConstants.VIEW_SNOMED_CODE);
	return (mapping.findForward("default"));
}

public ActionForward viewSnomed(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
{
	SrtManageForm manageForm = (SrtManageForm) form;
	try{
		manageForm.setActionMode(NEDSSConstants.VIEW_LOAD_ACTION);
		String ctnx = request.getParameter("Context");
		if(ctnx != null && ctnx.equalsIgnoreCase("cancel")) {
			manageForm.setSelection(manageForm.getOldDT());
			return (mapping.findForward("default"));
		}
		SnomedDT dt = (SnomedDT)manageForm.getSelection();
		String snomedCd = dt.getSnomedCd();

		if(snomedCd != null && snomedCd.length() > 0){
			ArrayList<?> somedList = manageForm.getManageList();
			Iterator<?> iter = somedList.iterator();
			while(iter.hasNext()) {
				SnomedDT sdt = (SnomedDT) iter.next();
				if(sdt.getSnomedCd().equalsIgnoreCase(snomedCd) && sdt.getSnomedCd().equalsIgnoreCase(snomedCd)){
					manageForm.setSelection(sdt);
					manageForm.setOldDT(sdt);
					break;
			}
		}
	}

	}catch(Exception e){
		request.setAttribute("error", e.getMessage());
		logger.error("Exception in viewSnomed: " + e.getMessage());
		e.printStackTrace();
	}finally {
		request.setAttribute("manageList", manageForm.getManageList());
		request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.VIEW_SNOMED_CODE);
	}

	return mapping.findForward("default");
}

}
