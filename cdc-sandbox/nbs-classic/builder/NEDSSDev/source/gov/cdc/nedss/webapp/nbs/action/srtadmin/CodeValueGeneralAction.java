package gov.cdc.nedss.webapp.nbs.action.srtadmin;

import gov.cdc.nedss.srtadmin.dt.CodeValueGeneralDT;
import gov.cdc.nedss.systemservice.util.CodeValueGeneralCachedDT;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.webapp.nbs.action.srtadmin.util.SRTAdminConstants;
import gov.cdc.nedss.webapp.nbs.action.srtadmin.util.SRTAdminUtil;
import gov.cdc.nedss.webapp.nbs.form.srtadmin.SrtManageForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class CodeValueGeneralAction extends DispatchAction {
	static final LogUtils logger = new LogUtils(CodeValueGeneralAction.class.getName());
	static final String VIEW = "VIEW";
	static final String EDIT = "EDIT";
	static final String ASSGN_AUTHORITY = "ASSGN_AUTHORITY";
	static final String CODE_SYSTEM = "CODE_SYSTEM";
	static final String CONFIRM_MSG = "This code set has been successfully updated. Please restart Wildfly to reflect the changes.";
	
	public ActionForward searchCodeValGenLoad(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		
		try{
			SrtManageForm manageForm = (SrtManageForm) form;
			manageForm.clearSelections();
			manageForm.setSearchCriteria("CODEVALGEN", "");
			manageForm.setManageList(new ArrayList<Object>());		
			manageForm.setActionMode(SRTAdminConstants.MANAGE);
			manageForm.setReturnToLink("<a href=\"/nbs/SrtAdministration.do?method=manageAdmin&focus=systemAdmin4\">Return to System Management Main Menu</a> ");
			request.setAttribute("SearchResult", "");
			
		} catch (Exception e) {
			logger.error("Exception in searchCodeValGenLoad: " + e.getMessage());
			request.setAttribute("error", e.getMessage());
		} finally {
			request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.MANAGE_CODE_VALUE_GEN);
		}
		return (mapping.findForward("default"));
		
	}

	public ActionForward resultsLoad(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		
		try{SrtManageForm manageForm = (SrtManageForm) form;
		manageForm.setActionMode(null);	
		request.setAttribute("SearchResult", "SearchResult");
		request.setAttribute("manageList", manageForm.getManageList());
		}catch(Exception e){
			request.setAttribute("error", e.getMessage());
			logger.error("Exception in resultsLoad: " + e.getMessage());
			e.printStackTrace();
		} finally {
			request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.MANAGE_CODE_VALUE_GEN);
		}
		return (mapping.findForward("default"));
		
	}
	
	public ActionForward searchCodeValGenSubmit(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		     
		try {
			searchSubmit(form, request, response);
		} catch (Exception e) {
			logger.error("Error while searchCodeValGenSubmit: " + e.toString());
			e.printStackTrace();
			SRTAdminUtil.handleErrors(e, request, "search", "");			
			return (mapping.findForward("default"));
		} finally {
			request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.MANAGE_CODE_VALUE_GEN);
		}
		return (mapping.findForward("results"));
		
	}	

	public ActionForward viewCodeValGenCode(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
			SrtManageForm manageForm = (SrtManageForm) form;
		try {
			manageForm.setReturnToLink("<a href=\"/nbs/CodeValueGeneral.do?method=searchCodeValGenSubmit\">Return To Manage Results</a> ");
			
			String cnxt = request.getParameter("context");
			if(cnxt != null && cnxt.equalsIgnoreCase("cancel")) {					
				manageForm.setSelection(manageForm.getOldDT());
				setViewActionMode(manageForm);
				return (mapping.findForward("view"));
			}
			String codeValGenCode = request.getParameter("code");
			if(codeValGenCode != null && codeValGenCode.length() > 0) {
				ArrayList<?> testList = manageForm.getManageList();
				Iterator<?> iter = testList.iterator();
				while(iter.hasNext()) {			
					CodeValueGeneralDT dt = (CodeValueGeneralDT) iter.next();
					if(dt.getCode().equalsIgnoreCase(codeValGenCode)) {
						manageForm.setSelection(dt);
						manageForm.setOldDT(dt);
						break;
					}
				}
			}			
			setViewActionMode(manageForm);
		} catch (Exception e) {
			request.setAttribute("error", e.getMessage());
			logger.error("Exception in viewCodeValGenCode: " + e.getMessage());
			e.printStackTrace();
		} finally {
			request.setAttribute("SearchResult", "SearchResult");
			request.setAttribute("manageList", manageForm.getManageList());
			request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.VIEW_CODE_VALUE_GEN);
		}
		
		
		return (mapping.findForward("view"));
	}
	
public ActionForward editCodeValGenCode(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		SrtManageForm manageForm = (SrtManageForm) form;
		CodeValueGeneralDT dt = new CodeValueGeneralDT();
		try {			

			String codeValGenCode = request.getParameter("code");		
			
			if(codeValGenCode != null && codeValGenCode.length() > 0) {
				ArrayList<?> testList = manageForm.getManageList();
				Iterator<?> iter = testList.iterator();				
				while(iter.hasNext()) {			
					 dt= (CodeValueGeneralDT) iter.next();
					if(dt.getCode().equalsIgnoreCase(codeValGenCode)) {
						SRTAdminUtil.updateCodingSystemforEdit(dt);
						manageForm.setSelection(dt);						
						manageForm.setOldDT(dt);
						request.setAttribute(NEDSSConstants.ADD_TIME_FOR_SRT_FILTERING, dt.getAddTime());
						break;
					}
					
				}
			} 			
			String codeSetNm = dt.getCodeSetNm();
			if(! codeSetNm.equals(ASSGN_AUTHORITY)  && ! codeSetNm.equals(CODE_SYSTEM))
				manageForm.setActionMode(NEDSSConstants.EDIT_LOAD_ACTION);
			else
				manageForm.setActionMode("EditSP");			
			manageForm.getAttributeMap().put("cancel", "/nbs/CodeValueGeneral.do?method=viewCodeValGenCode&context=cancel#codeval");
			manageForm.getAttributeMap().put("submit", "/nbs/CodeValueGeneral.do?method=updateCodeValGenCode#codeval");
		} catch (Exception e) {
			SRTAdminUtil.handleErrors(e, request, NEDSSConstants.EDIT_LOAD_ACTION,NEDSSConstants.CODE);
			logger.error("Exception in editCodeValueGeneral: " + e.getMessage());
			e.printStackTrace();
		} finally {
			request.setAttribute("SearchResult", "SearchResult");
			
			request.setAttribute("manageList", manageForm.getManageList());
			request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.EDIT_CODE_VALUE_GEN);
		}
		
		return (mapping.findForward("edit"));		
		
	}
	public ActionForward updateCodeValGenCode(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		
		SrtManageForm manageForm = (SrtManageForm) form;
		CodeValueGeneralDT dt = (CodeValueGeneralDT)manageForm.getSelection();
			
		try {
			SRTAdminUtil.trimSpaces(dt);
			manageForm.setReturnToLink("<a href=\"/nbs/CodeValueGeneral.do?method=searchCodeValGenSubmit\">Return To Manage Results</a> ");		
			Object[] searchParams = new Object[] {dt};
			Object[] oParams = new Object[] { JNDINames.CODE_VALUE_GENERAL_DAO_CLASS, "updateCodeValueGeneral", searchParams };
			SRTAdminUtil.processRequest(oParams, request.getSession());
		} catch (Exception e) {
			SRTAdminUtil.handleErrors(e, request, NEDSSConstants.EDIT,NEDSSConstants.CODE);
			logger.error("Exception in updateCodeValGenCode: " + e.getMessage());
			e.printStackTrace();
			request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.EDIT_CODE_VALUE_GEN);
			return (mapping.findForward("edit"));
		} finally {
			request.setAttribute("SearchResult", "SearchResult");
			request.setAttribute("manageList", manageForm.getManageList());
		}
		setViewActionMode(manageForm);
		request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.VIEW_CODE_VALUE_GEN);
		request.setAttribute("confirmMsg" ,CONFIRM_MSG);
		try {
			searchSubmit(form, request, response);
			request.setAttribute("manageList", manageForm.getManageList());
		} catch (Exception e) {
			logger.error("Error while updateCodeValGenCode : " + e.toString());
			e.printStackTrace();
			SRTAdminUtil.handleErrors(e, request, "search", "");			
			return (mapping.findForward("default"));
		}
		return (mapping.findForward("view"));		
	}
	public ActionForward createCodeValueGenCode(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		   
		   SrtManageForm manageForm = (SrtManageForm) form;
		   try {			 
			manageForm.resetSelection();
			CodeValueGeneralDT codeValueGeneralDT =  new CodeValueGeneralDT();
			String codeSetNm = manageForm.getSearchCriteria("CODEVALGEN") == null ? "" : (String)manageForm.getSearchCriteria("CODEVALGEN");
			codeValueGeneralDT.setCodeSetNm(codeSetNm);
		
			codeValueGeneralDT.setCodeSystemCd("L");
	
			codeValueGeneralDT.setCodeSystemDescTxt("Local");
	
		    manageForm.setSelection(codeValueGeneralDT);
		    if(! codeSetNm.equals(ASSGN_AUTHORITY)  && ! codeSetNm.equals(CODE_SYSTEM))
		    	manageForm.setActionMode(NEDSSConstants.CREATE_LOAD_ACTION);
		    else
		    	manageForm.setActionMode("CreateSP");
			manageForm.getAttributeMap().put("cancel", "/nbs/CodeValueGeneral.do?method=searchCodeValGenSubmit");
			manageForm.getAttributeMap().put("submit", "/nbs/CodeValueGeneral.do?method=createCodeValGenSubmit#codeval");
			
			//for SRT dropdown values
		    Date date = new java.util.Date();
			Timestamp currentTime = new Timestamp(date.getTime());
			request.setAttribute(NEDSSConstants.ADD_TIME_FOR_SRT_FILTERING, currentTime);
			
		} catch (Exception e) {
			SRTAdminUtil.handleErrors(e, request, NEDSSConstants.CREATE, NEDSSConstants.CODE);
			request.setAttribute("manageList", manageForm.getManageList());
			request.setAttribute("SearchResult", "SearchResult");
			logger.error("Exception in createCodeValueGenCode: " + e.getMessage());
			e.printStackTrace();
		} finally {
			request.setAttribute("SearchResult", "SearchResult");
			request.setAttribute("manageList", manageForm.getManageList());
			request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.CREATE_CODE_VALUE_GEN);
		}
		
		return (mapping.findForward("create"));		
	}
	
	
	public ActionForward createCodeValGenSubmit(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		
		SrtManageForm manageForm = (SrtManageForm) form;
		CodeValueGeneralDT dt = (CodeValueGeneralDT)manageForm.getSelection();
		
		dt.setIndentLevelNbr(new Integer(1));
		dt.setStatusCd("A");
		dt.setStatusCdDescTxt(CachedDropDowns.getCodeDescTxtForCd(dt.getStatusCd(),"NBS_STATUS_CD"));
		dt.setIsModifiableInd("Y");
		
		try {
			SRTAdminUtil.trimSpaces(dt);
			manageForm.setReturnToLink("<a href=\"/nbs/CodeValueGeneral.do?method=searchCodeValGenSubmit\">Return To Manage Results</a> ");
			Object[] searchParams = new Object[] {dt};
			Object[] oParams = new Object[] { JNDINames.CODE_VALUE_GENERAL_DAO_CLASS, "createCodeValueGeneral", searchParams};
			SRTAdminUtil.processRequest(oParams, request.getSession());
		} catch (Exception e) {
			SRTAdminUtil.handleErrors(e, request, NEDSSConstants.CREATE, NEDSSConstants.CODE);
			request.setAttribute("manageList", manageForm.getManageList());
			request.setAttribute("SearchResult", "SearchResult");
			request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.CREATE_CODE_VALUE_GEN);
			logger.error("Exception in createCodeValGenSubmit: " + e.getMessage());
			e.printStackTrace();
			return (mapping.findForward("create"));
		}
		request.setAttribute("SearchResult", "SearchResult");
		request.setAttribute("manageList", manageForm.getManageList());
		manageForm.setOldDT(dt);
		manageForm.setSearchCriteria("CODEVALGEN", dt.getCodeSetNm());
		setViewActionMode(manageForm);
		request.setAttribute(SRTAdminConstants.PAGE_TITLE ,SRTAdminConstants.VIEW_CODE_VALUE_GEN);
		request.setAttribute("confirmMsg" ,CONFIRM_MSG);
		try {
			searchSubmit(form, request, response);
			request.setAttribute("manageList", manageForm.getManageList());
			request.setAttribute("SearchResult", "SearchResult");
		} catch (Exception e) {
			logger.error("Error in createCodeValueGenSubmit : " + e.toString());
			SRTAdminUtil.handleErrors(e, request, "search", "");			
			return (mapping.findForward("default"));
		}		
		return (mapping.findForward("view"));		
	}		

	private void mapAssignAuthAndCodingSysCdOnSubmit(CodeValueGeneralDT dt) {

		//If codesetNm = 'CODE_SYSTEM' or 'ASSGN_AUTHORITY' these fields dont need any translation as they are plain text fields
		if(!dt.getCodeSetNm().equals(CODE_SYSTEM) && !dt.getCodeSetNm().equals(ASSGN_AUTHORITY)) {
			String csc = dt.getCodeSystemDescTxt();
			if(csc != null && csc.length() > 0) {
				dt.setCodeSystemCd(csc);
				dt.setCodeSystemDescTxt(CachedDropDowns.getSRTAdminCVGDesc(csc, CODE_SYSTEM));
			}
		}
	}
	
	private void mapAssignAuthAndCodingSysCdOnLoad(SrtManageForm form, CodeValueGeneralDT dt) {

		//If codesetNm = 'CODE_SYSTEM' or 'ASSGN_AUTHORITY' these fields dont need any translation as they are plain text fields
		if(!dt.getCodeSetNm().equals(CODE_SYSTEM) && !dt.getCodeSetNm().equals(ASSGN_AUTHORITY)) {
			String csc = dt.getCodeSystemCd();
			if(csc != null && csc.length() > 0) {
				dt.setCodeSystemDescTxt(csc);
			}			
		}
		
		form.setSelection(dt);
	}
	private void searchSubmit(ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception {
		try {
		SrtManageForm manageForm = (SrtManageForm) form;
		manageForm.getSearchMap().remove("CODESYSTEMCDDESC");
		Map<Object,Object> searchMap = manageForm.getSearchMap();
		Object[] searchParams = null;
		
		if(searchMap.size() > 0)
			searchParams = searchMap.values().toArray();
			
		Object[] oParams = new Object[] { JNDINames.CODE_VALUE_GENERAL_DAO_CLASS, "retrieveCodeSetValGenFields", searchParams };
		ArrayList<?> dtList = (ArrayList<?> ) SRTAdminUtil.processRequest(oParams, request.getSession());
		Iterator<?> iter = dtList.iterator();
		while(iter.hasNext()) {
			CodeValueGeneralDT dt = (CodeValueGeneralDT) iter.next();
			dt.setStatusCdDescTxt(CachedDropDowns.getCodeDescTxtForCd(dt.getStatusCd(),"NBS_STATUS_CD"));
			SRTAdminUtil.makeCodeValGenLink(dt, VIEW);
			SRTAdminUtil.makeCodeValGenLink(dt, EDIT);
		}			
		manageForm.setManageList(dtList);
     	}catch (Exception ex) {
    		logger.error("Exception in CodeValueGenAction.search Submit: " + ex.getMessage());
    		ex.printStackTrace();
    		throw ex;
    	}  
	
	}
	
	private void setViewActionMode(SrtManageForm manageForm) {
		
		CodeValueGeneralDT dt = (CodeValueGeneralDT) manageForm.getSelection();
		String codeSetNm = dt.getCodeSetNm();
		if(! codeSetNm.equals(ASSGN_AUTHORITY)  && ! codeSetNm.equals(CODE_SYSTEM))
			manageForm.setActionMode(NEDSSConstants.VIEW_LOAD_ACTION);
		else
			manageForm.setActionMode("ViewSP");						
	}
	
	

}
