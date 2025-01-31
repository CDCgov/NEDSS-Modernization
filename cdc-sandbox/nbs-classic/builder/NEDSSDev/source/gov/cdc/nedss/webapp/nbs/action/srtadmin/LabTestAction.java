package gov.cdc.nedss.webapp.nbs.action.srtadmin;

import gov.cdc.nedss.srtadmin.dt.LabTestDT;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.webapp.nbs.action.srtadmin.util.SRTAdminConstants;
import gov.cdc.nedss.webapp.nbs.action.srtadmin.util.SRTAdminUtil;
import gov.cdc.nedss.webapp.nbs.action.util.NBSPageConstants;
import gov.cdc.nedss.webapp.nbs.form.srtadmin.SrtManageForm;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

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

public class LabTestAction extends DispatchAction {
	
	static final LogUtils logger = new LogUtils(LabTestAction.class.getName());
	static final String VIEW = "VIEW";
	static final String EDIT = "EDIT";
	private static CachedDropDownValues cdv = new CachedDropDownValues();
	private static final String daoName = JNDINames.LABTEST_DAO_CLASS;
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward searchLabLoad(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		
		try {
			SrtManageForm manageForm = (SrtManageForm) form;			
			manageForm.clearSelections();
			manageForm.setSearchCriteria("LABTEST", "");
			manageForm.setActionMode(SRTAdminConstants.MANAGE);
			manageForm.setReturnToLink("<a href=\"/nbs/SrtAdministration.do?method=manageAdmin\">Return to System Management Main Menu</a> ");			
		} catch (Exception e) {
			logger.error("Exception in searchLabLoad: " + e.getMessage());
			e.printStackTrace();
			request.setAttribute("error", e.getMessage());
		} finally {
			request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.MANAGE_LD_LABTESTS);
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
	public ActionForward searchLabSubmit(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		
		try {
			SrtManageForm manageForm = (SrtManageForm) form;
			manageForm.setActionMode(null);
			manageForm.resetSelection();
			String whereClause = SRTAdminUtil.labTestSrchWhereClause(manageForm.getSearchMap());
			manageForm.getAttributeMap().clear();			
			Object[] searchParams = {whereClause};

				
			Object[] oParams = new Object[] { daoName, "findLocallyDefinedTests", searchParams };
			ArrayList<?> dtList = (ArrayList<?> ) SRTAdminUtil.processRequest(oParams, request.getSession());
			if(dtList.size() > 0) {
				Iterator<?> iter = dtList.iterator();
				while(iter.hasNext()) {
					LabTestDT dt = (LabTestDT) iter.next();					
					SRTAdminUtil.makeLabTestLink(dt, VIEW);
					SRTAdminUtil.makeLabTestLink(dt, EDIT);

					if(dt.getDrugTestInd() != null && dt.getDrugTestInd().equals("Y"))
						dt.setDrugTestInd("1");
					if(dt.getOrganismResultTestInd() != null && dt.getOrganismResultTestInd().equals("Y"))
						dt.setOrganismResultTestInd("1");
					if(dt.getPaDerivationExcludeCd() != null && dt.getPaDerivationExcludeCd().equals("Y"))
						dt.setPaDerivationExcludeCd("1");
					if(dt.getTestTypeCd().equals("O")) dt.setTestTypeDescTxt("Ordered");
					if(dt.getTestTypeCd().equals("R")) dt.setTestTypeDescTxt("Resulted");			
					if(dt.getDefaultConditionCd() != null && dt.getDefaultConditionCd().length() > 0)
						dt.setConditionDescTxt(cdv.getConditionDesc(dt.getDefaultConditionCd()));
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
			request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.MANAGE_LD_LABTESTS);
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
			request.setAttribute("manageList", manageForm.getManageList());
		} catch (Exception e) {
			request.setAttribute("error", e.getMessage());
			logger.error("Exception in resultsLoad: " + e.getMessage());
			e.printStackTrace();
		} finally {
			request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.MANAGE_LD_LABTESTS);
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
	public ActionForward viewLab(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		
		SrtManageForm manageForm = (SrtManageForm) form;
		manageForm.setActionMode(NEDSSConstants.VIEW_LOAD_ACTION);
		try {
			String cnxt = request.getParameter("context");
			if(cnxt != null && cnxt.equalsIgnoreCase("cancel")) {				
				manageForm.setSelection(manageForm.getOldDT());
				return (mapping.findForward("default"));
			}			
			manageForm.setReturnToLink("<a href=\"/nbs/LDLabTests.do?method=searchLabSubmit\">Return To Manage Results</a> ");
			String labTestCd = request.getParameter("labTestCd");
			String laboratoryId = request.getParameter("laboratoryId");
			if((labTestCd != null && labTestCd.length() > 0) && (laboratoryId != null && laboratoryId.length() > 0)) {
				ArrayList<?> testList = manageForm.getManageList();
				Iterator<?> iter = testList.iterator();
				while(iter.hasNext()) {			
					LabTestDT dt = (LabTestDT) iter.next();
					if(dt.getLaboratoryId().equalsIgnoreCase(laboratoryId) && dt.getLabTestCd().equalsIgnoreCase(labTestCd)) {
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
			request.setAttribute("TestTypeCd", ((LabTestDT)manageForm.getSelection()).getTestTypeCd());
			request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.VIEW_LD_LABTESTS);
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
	public ActionForward editLab(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		
		SrtManageForm manageForm = (SrtManageForm) form;		
		try {
			manageForm.setActionMode(NEDSSConstants.EDIT_LOAD_ACTION);
			String labTestCd = request.getParameter("labTestCd");
			String laboratoryId = request.getParameter("laboratoryId");			
			if((labTestCd != null && labTestCd.length() > 0) && (laboratoryId != null && laboratoryId.length() > 0)) {
				ArrayList<?> testList = manageForm.getManageList();
				Iterator<?> iter = testList.iterator();
				while(iter.hasNext()) {			
					LabTestDT dt = (LabTestDT) iter.next();
					if(dt.getLaboratoryId().equalsIgnoreCase(laboratoryId) && dt.getLabTestCd().equalsIgnoreCase(labTestCd)) {
						manageForm.setSelection(dt);						
						manageForm.setOldDT(dt);
						break;
					}
				}				
			}				
			manageForm.getAttributeMap().put("cancel", "/nbs/LDLabTests.do?method=viewLab&context=cancel#labtests");
			manageForm.getAttributeMap().put("submit", "/nbs/LDLabTests.do?method=updateLab");
		} catch (Exception e) {
			request.setAttribute("error", e.getMessage());
			logger.error("Exception in editLab: " + e.getMessage());
			e.printStackTrace();
		} finally {
			request.setAttribute("manageList", manageForm.getManageList());
			request.setAttribute("TestTypeCd", ((LabTestDT)manageForm.getSelection()).getTestTypeCd());
			request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.EDIT_LD_LABTESTS);
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
	public ActionForward createLoadLab(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		
		SrtManageForm manageForm = (SrtManageForm) form;
		try {
			manageForm.resetSelection();
			manageForm.setSelection(new LabTestDT());
			manageForm.setActionMode(NEDSSConstants.CREATE_LOAD_ACTION);
			manageForm.getAttributeMap().put("cancel", "/nbs/LDLabTests.do?method=searchLabSubmit");
			manageForm.getAttributeMap().put("submit", "/nbs/LDLabTests.do?method=createSubmitLab");
			
			LabTestDT dt = (LabTestDT)manageForm.getSelection();
			//prepopulate dt based on search criteria
			dt.setLaboratoryId(manageForm.getSearchCriteria("LABID"));
			dt.setTestTypeCd(manageForm.getSearchCriteria("TEST_TYPE"));
			String labTest = (String) manageForm.getSearchCriteria("LABTEST");
			if(labTest.length() > 0)dt.setLabTestCd(labTest);
			String desc = (String) manageForm.getSearchCriteria("TEST_DESC");
			if(desc.length() > 0)dt.setLabTestDescTxt(desc);
			
		} catch (Exception e) {
			request.setAttribute("error", e.getMessage());
			logger.error("Exception in createLoadLab: " + e.getMessage());
			e.printStackTrace();
		} finally {
			request.setAttribute("manageList", manageForm.getManageList());
			request.setAttribute("TestTypeCd", ((LabTestDT)manageForm.getSelection()).getTestTypeCd());
			request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.CREATE_LD_LABTESTS);
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
	public ActionForward createSubmitLab(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		
		SrtManageForm manageForm = (SrtManageForm) form;
		manageForm.setManageList(null);
		LabTestDT dt = (LabTestDT) manageForm.getSelection();
		request.setAttribute("manageList", manageForm.getManageList());
		if(dt.getIndentLevelNbr() != null && dt.getIndentLevelNbr().equals(new Integer(0)))
			dt.setIndentLevelNbr(null);		
		try {
			SRTAdminUtil.trimSpaces(dt);
			manageForm.setReturnToLink("<a href=\"/nbs/LDLabTests.do?method=searchLabSubmit\">Return To Manage Results</a> ");
			Object[] searchParams = new Object[] {dt};
			Object[] oParams = new Object[] { daoName, "createLocallyDefinedTest", searchParams};
			SRTAdminUtil.processRequest(oParams, request.getSession());
			
			ActionMessages messages = new ActionMessages();
			messages.add(NBSPageConstants.SUCCESS_MESSAGES_PROPERTY,
					new ActionMessage(NBSPageConstants.SAVE_SUCCESS_MESSAGE_KEY, "Lab Test"));
			request.setAttribute("success_messages", messages);
		} catch (Exception e) {
			SRTAdminUtil.handleErrors(e, request, "create",NEDSSConstants.LABTESTCD);
			request.setAttribute("manageList", manageForm.getManageList());
			request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.CREATE_LD_LABTESTS);
			logger.error("Exception in createSubmitLab: " + e.getMessage());
			e.printStackTrace();
			manageForm.setActionMode(NEDSSConstants.CREATE_LOAD_ACTION);
			request.setAttribute("TestTypeCd", ((LabTestDT)manageForm.getSelection()).getTestTypeCd());
			return (mapping.findForward("default"));
		}
		if(dt.getTestTypeCd().equals("O")) dt.setTestTypeDescTxt("Ordered");
		if(dt.getTestTypeCd().equals("R")) dt.setTestTypeDescTxt("Resulted");

		dt.setConditionDescTxt(cdv.getConditionDesc(dt.getDefaultConditionCd()));
		manageForm.setSelection(dt); 
		manageForm.setOldDT(dt);
		manageForm.setSearchCriteria("LABTEST", dt.getLabTestCd());
		manageForm.setActionMode(NEDSSConstants.VIEW_LOAD_ACTION);
		manageForm.getAttributeMap().remove("NORESULT");
		request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.VIEW_LD_LABTESTS);
		request.setAttribute("TestTypeCd", ((LabTestDT)manageForm.getSelection()).getTestTypeCd());
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
	public ActionForward updateLab(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		
		SrtManageForm manageForm = (SrtManageForm) form;
		manageForm.setManageList(null);
		LabTestDT dt = (LabTestDT) manageForm.getSelection();
		if(dt.getIndentLevelNbr() != null && dt.getIndentLevelNbr().equals(new Integer(0)))
			dt.setIndentLevelNbr(null);
		try {
			SRTAdminUtil.trimSpaces(dt);
			manageForm.setReturnToLink("<a href=\"/nbs/LDLabTests.do?method=searchLabSubmit\">Return To Manage Results</a> ");		
			Object[] searchParams = new Object[] {dt};
			Object[] oParams = new Object[] { daoName, "updateLocallyDefinedTest", searchParams };
			SRTAdminUtil.processRequest(oParams, request.getSession());
			
			ActionMessages messages = new ActionMessages();
			messages.add(NBSPageConstants.SUCCESS_MESSAGES_PROPERTY,
					new ActionMessage(NBSPageConstants.SAVE_SUCCESS_MESSAGE_KEY, "Lab Test"));
			request.setAttribute("success_messages", messages);
			
		} catch (Exception e) {
			SRTAdminUtil.handleErrors(e, request, "edit",NEDSSConstants.LABTESTCD);
			request.setAttribute("manageList", manageForm.getManageList());
			logger.error("Exception in updateLab: " + e.getMessage());
			e.printStackTrace();
			request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.EDIT_LD_LABTESTS);
			request.setAttribute("TestTypeCd", ((LabTestDT)manageForm.getSelection()).getTestTypeCd());
			return (mapping.findForward("default"));
		} 
		dt.setConditionDescTxt(cdv.getConditionDesc(dt.getDefaultConditionCd()));
		manageForm.setActionMode(NEDSSConstants.VIEW_LOAD_ACTION);
		request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.VIEW_LD_LABTESTS);
		request.setAttribute("TestTypeCd", ((LabTestDT)manageForm.getSelection()).getTestTypeCd());
		request.setAttribute("manageList", manageForm.getManageList());

		return (mapping.findForward("default"));		
	}
}
