package gov.cdc.nedss.webapp.nbs.action.srtadmin;

import java.util.ArrayList;

import gov.cdc.nedss.srtadmin.dt.SnomedConditionDT;

import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.webapp.nbs.action.srtadmin.util.SRTAdminConstants;
import gov.cdc.nedss.webapp.nbs.action.srtadmin.util.SRTAdminUtil;
import gov.cdc.nedss.webapp.nbs.action.util.NBSPageConstants;
import gov.cdc.nedss.webapp.nbs.form.srtadmin.SrtManageForm;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

public class SnomedtoConditionLinkAction extends DispatchAction {
	static final LogUtils logger = new LogUtils(LoinctoConditionLinkAction.class.getName());
	
	
	public ActionForward searchSnomedtoCondLinkLoad(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		
		try {
			SrtManageForm manageForm = (SrtManageForm) form;			
			manageForm.clearSelections();
			manageForm.setSearchCriteria("SNOMED", "");
			manageForm.setSearchCriteria("CONDITION", "");
			manageForm.setSrchFldCount(0);
			
			manageForm.setActionMode(SRTAdminConstants.MANAGE);
			manageForm.setReturnToLink("<a href=\"/nbs/SrtAdministration.do?method=manageAdmin\">Return to System Management Main Menu</a> ");			
		} catch (Exception e) {
			logger.error("Exception in searchSnomedtoCondLinkLoad: " + e.getMessage());
			e.printStackTrace();
			request.setAttribute("error", e.getMessage());
		} finally {
			request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.MANAGE_SNOMED_CONDITION_LINK);
		}
		return (mapping.findForward("default"));
		
	}
	
	public ActionForward searchSnomedSubmit(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		SrtManageForm manageForm = (SrtManageForm) form;
		try {
			manageForm.setActionMode(null);
			String snomedCd = manageForm.getSearchCriteria("SNOMED");
			String conditionCd = manageForm.getSearchCriteria("CONDITION");
			if((snomedCd != null && snomedCd.length()>0)&& (conditionCd != null && conditionCd.length()>0))
				manageForm.setSrchFldCount(0);
			if((snomedCd == null || (snomedCd!=null && snomedCd.equals(""))) && (conditionCd != null && conditionCd.length()>0))
				manageForm.setSrchFldCount(2);
			if((snomedCd != null && snomedCd.length()>0 ) && (conditionCd == null || (conditionCd!= null && conditionCd.equals(""))))
				manageForm.setSrchFldCount(0);
			String whereClause = SRTAdminUtil.snomedCondSrchWhereClause(manageForm.getSearchMap());
			manageForm.getAttributeMap().clear();			
			Object[] searchParams = {whereClause};
			Object[] oParams = new Object[] { JNDINames.SNOMED_CONDITION_DAO_CLASS, "findSnomedConditionLink", searchParams };
			ArrayList<?> dtList = (ArrayList<?> ) SRTAdminUtil.processRequest(oParams, request.getSession());
			if(dtList.size() == 0)
				manageForm.getAttributeMap().put("NORESULT","NORESULT");
			
			manageForm.setManageList(dtList);	
			manageForm.setOldManageList(dtList);

		
		} catch (Exception e) {
			logger.error("Exception in searchSnomedSubmit: " + e.getMessage());
			e.printStackTrace();
			SRTAdminUtil.handleErrors(e, request, "search", "");
			return (mapping.findForward("default"));
		} finally {
			request.setAttribute("manageList", manageForm.getManageList());
			request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.MANAGE_SNOMED_CONDITION_LINK);
		}
		return (mapping.findForward("results"));
		
	}	
	
                  

	public ActionForward resultsLoad(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		
		
			SrtManageForm manageForm = (SrtManageForm) form;
			manageForm.setActionMode(null);
		try {
			request.setAttribute("manageList", manageForm.getManageList());
		} catch (Exception e) {
			request.setAttribute("error", e.getMessage());
			logger.error("Exception in resultsLoad: " + e.getMessage());
			e.printStackTrace();
		} finally {
			request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.MANAGE_SNOMED_CONDITION_LINK);
			request.setAttribute("manageList", manageForm.getManageList());
		}
		
		return (mapping.findForward("default"));
	}
	
	public ActionForward createLoadLink(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		
		SrtManageForm manageForm = (SrtManageForm) form;
		try {
			manageForm.resetSelection();
			manageForm.setSelection(new SnomedConditionDT());
			manageForm.setActionMode(NEDSSConstants.CREATE_LOAD_ACTION);
			manageForm.getAttributeMap().put("cancel", "/nbs/SnomedtoConditionLink.do?method=searchSnomedSubmit");
			manageForm.getAttributeMap().put("submit", "/nbs/SnomedtoConditionLink.do?method=createSubmitLink");
			
			SnomedConditionDT dt = (SnomedConditionDT)manageForm.getSelection();
			//prepopulate dt based on search criteria
			dt.setSnomedCd(manageForm.getSearchCriteria("SNOMED"));			
			//manageForm.setSearchCriteria("CONDITION1", manageForm.getSearchCriteria("CONDITION"));
			dt.setConditionCd(manageForm.getSearchCriteria("CONDITION"));
			
				

		} catch (Exception e) {
			request.setAttribute("error", e.getMessage());
			logger.error("Exception in createLoadLink: " + e.getMessage());
			e.printStackTrace();
		} finally {
			request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.CREATE_SNOMED_CONDITION_LINK);
			request.setAttribute("manageList", manageForm.getManageList());
		}
		
		return (mapping.findForward("default"));		
	}	
	
public ActionForward createSubmitLink(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		
		SrtManageForm manageForm = (SrtManageForm) form;
		manageForm.setSelection(new SnomedConditionDT());
		//manageForm.setManageList(manageForm.getOldManageList());
		manageForm.setManageList(null);
		request.setAttribute("manageList", manageForm.getManageList());
		manageForm.setActionMode(NEDSSConstants.CREATE_LOAD_ACTION);
		manageForm.getAttributeMap().put("cancel", "/nbs/SnomedtoConditionLink.do?method=resultsLoad");
		manageForm.getAttributeMap().put("submit", "/nbs/SnomedtoConditionLink.do?method=createSubmitLink");
		SnomedConditionDT dt = (SnomedConditionDT)manageForm.getSelection();
		dt.setSnomedCd(manageForm.getSearchCriteria("SNOMED"));
		dt.setConditionCd(manageForm.getSearchCriteria("CONDITION"));
		CachedDropDownValues cdv = new CachedDropDownValues();
		dt.setDiseaseNm(cdv.getConditionDesc(manageForm.getSearchCriteria("CONDITION")));
		
		try {
			SRTAdminUtil.trimSpaces(dt);
			manageForm.setReturnToLink("<a href=\"/nbs/SnomedtoConditionLink.do?method=searchSnomedSubmit\">Return To Manage Results</a> ");
			Object[] searchParams = new Object[] {manageForm.getSelection()};
			Object[] oParams = new Object[] { JNDINames.SNOMED_CONDITION_DAO_CLASS, "createSnomedConditionLink", searchParams};
			SRTAdminUtil.processRequest(oParams, request.getSession());
			
			ActionMessages messages = new ActionMessages();
			messages.add(NBSPageConstants.SUCCESS_MESSAGES_PROPERTY,
					new ActionMessage(NBSPageConstants.SRT_ADMIN_LINK_CREATION_SUCCESS_MESSAGE_KEY, "SNOMED", "Condition"));
			request.setAttribute("success_messages", messages);
		} catch (Exception e) {
			SRTAdminUtil.handleErrors(e, request, NEDSSConstants.CREATE,NEDSSConstants.SNOMED_CD);
			request.setAttribute("manageList", manageForm.getManageList());
			request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.CREATE_SNOMED_CONDITION_LINK);
			logger.error("Exception in createSubmitLink: " + e.getMessage());
			e.printStackTrace();
			return (mapping.findForward("default"));
		}finally {
			request.setAttribute("manageList", manageForm.getManageList());
		}
		manageForm.setOldDT(dt);
		manageForm.setActionMode(NEDSSConstants.VIEW_LOAD_ACTION);
		manageForm.getAttributeMap().remove("NORESULT");
		request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.VIEW_SNOMED_CONDITION_LINK);
		return (mapping.findForward("default"));		
	}
	
}
