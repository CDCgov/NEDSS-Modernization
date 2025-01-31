package gov.cdc.nedss.webapp.nbs.action.exportcasenotification;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import gov.cdc.nedss.srtadmin.dt.CodeSetDT;
import gov.cdc.nedss.srtadmin.dt.CodeValueGeneralDT;
import gov.cdc.nedss.systemservice.ejb.casenotificationejb.dt.ExportAlgorithmDT;
import gov.cdc.nedss.systemservice.ejb.casenotificationejb.dt.ExportTriggerDT;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.webapp.nbs.action.exportcasenotification.util.CaseNotificationUtil;
import gov.cdc.nedss.webapp.nbs.action.exportcasenotification.util.ExportCaseNotificationConstants;
import gov.cdc.nedss.webapp.nbs.action.srtadmin.util.SRTAdminUtil;
import gov.cdc.nedss.webapp.nbs.form.exportcasenotification.ExportAlgorithmForm;
import gov.cdc.nedss.webapp.nbs.form.util.BaseForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.util.NEDSSConstants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction; 



public class ExportAlgorithmAction extends DispatchAction {
	static final LogUtils logger = new LogUtils(ExportAlgorithmAction.class.getName());
	static final String VIEW = "VIEW";
	static final String EDIT = "EDIT";
	CaseNotificationUtil cnUtil = new CaseNotificationUtil();
	
	public ActionForward algorithmLoad(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		
		ExportAlgorithmForm algorithmForm = (ExportAlgorithmForm) form;
		algorithmForm.setActionMode(NEDSSConstants.RESULT_LOAD_ACTION);
		try{
			HttpSession session = request.getSession(true);
			ArrayList<?> algorithmList = new ArrayList<Object> ();
			algorithmList = cnUtil.getExportAlgorithm(session); 
			Iterator<?> iter = algorithmList.iterator();
			while(iter.hasNext()) {
				ExportAlgorithmDT dt = (ExportAlgorithmDT) iter.next();
				cnUtil.makeExportAlgLink(dt, VIEW);
				cnUtil.makeExportAlgLink(dt, EDIT);
				dt.setRecordStatusCdDescTxt(cnUtil.getRecordSatusCodeDesc(dt.getRecordStatusCd()));
			    dt.setLevelOfReviewDescTxt(CachedDropDowns.getCodeDescTxtForCd(dt.getLevelOfReview(),"YN"));
			    dt.setDocumentTypeDescTxt(cnUtil.getCaseReportDesc(dt.getDocumentType()));
			    
			}			
			
			request.setAttribute("manageList", algorithmList);
			algorithmForm.setManageList(algorithmList);
		
						
		} catch (Exception e) {
			logger.error("Exception in algorithmLoad: " + e.getMessage());
			request.setAttribute("error", e.getMessage());
		} finally {
			algorithmForm.setPageTitle(ExportCaseNotificationConstants.ADD_EXPORT_ALGORITHM, request);
			//request.setAttribute(ExportCaseNotificationConstants.PAGE_TITLE ,ExportCaseNotificationConstants.ADD_EXPORT_ALGORITHM);
		}
		return (mapping.findForward("default"));
		
	}
	
	public ActionForward createNewBatchExport(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws ServletException{
		ExportAlgorithmForm algorithmForm = (ExportAlgorithmForm) form;
		algorithmForm.resetSelection();
		algorithmForm.setExADT(new ExportAlgorithmDT());
		algorithmForm.setSelection(new ExportAlgorithmDT());
		
		algorithmForm.setActionMode(NEDSSConstants.CREATE_LOAD_ACTION);
		algorithmForm.setPageTitle(ExportCaseNotificationConstants.ADD_EXPORT_ALGORITHM, request);
		try{
			HttpSession session = request.getSession();
			ArrayList<?> triggerFieldList = cnUtil.getExportTriggerFields(session);
			//Setting the values to form upon Load
			algorithmForm.setTriggerFields(triggerFieldList);
			algorithmForm.setTriggerFieldList(cnUtil.getExportTriggerFieldDDs(algorithmForm.getTriggerFields()));
			
			
			
		}catch (Exception e){
			request.setAttribute("error", e.getMessage());
			throw new ServletException("Error in Creating the Export Algorithm"+e.getMessage(),e);
		}
		Collection<?>  algorithmList = algorithmForm.getManageList();
		request.setAttribute("manageList", algorithmList);
		return (mapping.findForward("create"));		
	}
	
	public ActionForward createNewBatchExportSubmit(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws ServletException {
		ExportAlgorithmForm algorithmForm = (ExportAlgorithmForm) form;
		
	 	algorithmForm.setActionMode(NEDSSConstants.CREATE_SUBMIT_ACTION);
		algorithmForm.setPageTitle(ExportCaseNotificationConstants.ADD_EXPORT_ALGORITHM, request);
		HttpSession session = request.getSession(true);
		ExportAlgorithmDT dt = (ExportAlgorithmDT)algorithmForm.getExADT();
		//Get the trigger List<Object> from the form and set to AlgorithmDT
		Collection<Object>  exTrSet = algorithmForm.getExTrDTset();
		ArrayList<Object> exTrList = new ArrayList<Object> (exTrSet);
		dt.setTriggerDTList(exTrList);
		dt = cnUtil.setExportAlgorithmforCreateEdit(dt, session,algorithmForm.getActionMode());
		try{
			cnUtil.setExportAlgorithm(session, dt); 
		}catch (Exception e){
			logger.error("Error in createNewBatchExportSubmit method" );
			request.setAttribute("error", e.getMessage());
			throw new ServletException("Error in Creating the Export Algorithm"+e.getMessage(),e);
		}
		
		ArrayList<?> algorithmList = new ArrayList<Object> ();
		try{
			algorithmList = cnUtil.getExportAlgorithm(session); 
			Iterator<?> iter = algorithmList.iterator();
			while(iter.hasNext()) {
				ExportAlgorithmDT edt = (ExportAlgorithmDT) iter.next();
				cnUtil.makeExportAlgLink(edt, VIEW);
				cnUtil.makeExportAlgLink(edt, EDIT);
				edt.setRecordStatusCdDescTxt(cnUtil.getRecordSatusCodeDesc(edt.getRecordStatusCd()));
			    edt.setLevelOfReviewDescTxt(CachedDropDowns.getCodeDescTxtForCd(edt.getLevelOfReview(),"YN"));
			    edt.setDocumentTypeDescTxt(cnUtil.getCaseReportDesc(edt.getDocumentType()));
			}			
			request.setAttribute("manageList", algorithmList);
			algorithmForm.setManageList(algorithmList);
		}catch (Exception e){
			request.setAttribute("error", e.getMessage());
		}
		
        dt.setRecordStatusCdDescTxt(cnUtil.getRecordSatusCodeDesc(dt.getRecordStatusCd()));
        dt.setLevelOfReviewDescTxt(CachedDropDowns.getCodeDescTxtForCd(dt.getLevelOfReview(),"YN"));
        dt.setDocumentTypeDescTxt(cnUtil.getCaseReportDesc(dt.getDocumentType()));
        dt.setReceivingSystemDescTxt(CachedDropDowns.getReceivingFacilityDescTxt(dt.getReceivingSystem()));
			
		algorithmForm.setOldExADT(dt);
		algorithmForm.setExADT(dt);
		algorithmForm.setActionMode(NEDSSConstants.VIEW_LOAD_ACTION);
		return (mapping.findForward("view"));		
	}
	
	public ActionForward viewBatchExport(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws ServletException {
		ExportAlgorithmForm algorithmForm = (ExportAlgorithmForm) form;
		try {
			//algorithmForm.setReturnToLink("<a href=\"/nbs/ExportCaseNotification.do?method=algorithmLoad\">Return To Export Algorithm List</a> ");
			
			String cnxt = request.getParameter("context");
			if(cnxt != null && cnxt.equalsIgnoreCase("cancel")) {					
				algorithmForm.setExADT(algorithmForm.getOldExADT());
				return (mapping.findForward("view"));
			}
			String algNameUid = request.getParameter("algNameUid");
			if(algNameUid != null && algNameUid.length() > 0) {
				ArrayList<?> testList = algorithmForm.getManageList();
				Iterator<?> iter = testList.iterator();
				while(iter.hasNext()) {			
					ExportAlgorithmDT dt = (ExportAlgorithmDT) iter.next();
					if(dt.getExportAlgorithmUid().toString().equalsIgnoreCase(algNameUid)) {
						if(dt.getTriggerDTList()!=null){
							Iterator<Object> ite = dt.getTriggerDTList().iterator();
							while(ite.hasNext()){
								ExportTriggerDT exDT = (ExportTriggerDT)ite.next();
								algorithmForm.getExTrDTMap().put(String.valueOf(exDT.getId()), exDT);
							}
						}
						//algorithmForm.setExTrDTset((Collection)dt.getTriggerDTList());
						algorithmForm.setExADT(dt);
						algorithmForm.setOldExADT(dt);
						break;
					}
				}
			}			
			//setViewActionMode(algorithmForm);
		} catch (Exception e) {
			request.setAttribute("error", e.getMessage());
			logger.error("Exception in viewCodeValGenCode: " + e.getMessage());
			throw new ServletException("Error in Viewing the Algorithm details"+e.getMessage(),e);
		}
		
		algorithmForm.setActionMode(NEDSSConstants.VIEW_LOAD_ACTION);
		algorithmForm.setPageTitle(ExportCaseNotificationConstants.VIEW_EXPORT_ALGORITHM, request);
		Collection<?>  algorithmList = algorithmForm.getManageList();
		
		
		request.setAttribute("manageList", algorithmList);
		return (mapping.findForward("view"));		
	}
	
	public ActionForward editBatchExport(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		ExportAlgorithmForm algorithmForm = (ExportAlgorithmForm) form;
		algorithmForm.resetSelection();
		algorithmForm.setSelection(new ExportAlgorithmDT());
		
		algorithmForm.setActionMode(NEDSSConstants.EDIT_LOAD_ACTION);
		algorithmForm.setPageTitle(ExportCaseNotificationConstants.VIEW_EXPORT_ALGORITHM, request);
		Collection<?>  algorithmList = algorithmForm.getManageList();
		request.setAttribute("manageList", algorithmList);
		
		String cnxt = request.getParameter("context");
		if(cnxt != null && cnxt.equalsIgnoreCase("cancel")) {					
			algorithmForm.setExADT(algorithmForm.getOldExADT());
			return (mapping.findForward("edit"));
		}
		String algNameUid = request.getParameter("algNameUid");
		if(algNameUid != null && algNameUid.length() > 0) {
			ArrayList<?> testList = algorithmForm.getManageList();
			Iterator<?> iter = testList.iterator();
			while(iter.hasNext()) {			
				ExportAlgorithmDT dt = (ExportAlgorithmDT) iter.next();
				if(dt.getExportAlgorithmUid().toString().equalsIgnoreCase(algNameUid)) {
					if(dt.getTriggerDTList()!=null){
						Iterator<Object> ite = dt.getTriggerDTList().iterator();
						while(ite.hasNext()){
							ExportTriggerDT exDT = (ExportTriggerDT)ite.next();
							algorithmForm.getExTrDTMap().put(String.valueOf(exDT.getId()), exDT);
						}
					}
					//algorithmForm.setExTrDTset(new HashSet(dt.getTriggerDTList()));
					algorithmForm.setExADT(dt);
					algorithmForm.setOldExADT(dt);
					break;
				}
			}
		}			
		return (mapping.findForward("edit"));		
	}
	
	
	public ActionForward AddTriggerLoad(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		ExportAlgorithmForm algorithmForm = (ExportAlgorithmForm) form;
	    algorithmForm.setActionMode(NEDSSConstants.ADD_TRIGGER_ACTION);
		algorithmForm.setPageTitle(ExportCaseNotificationConstants.ADD_ALGORITHM_TRIGGER, request);
		Collection<?>  algorithmList = algorithmForm.getManageList();
		String algNameUid = request.getParameter("algNameUid");
		if(algNameUid != null && algNameUid.length() > 0) {
			 
			Iterator<?> iter = algorithmList.iterator();
			while(iter.hasNext()) {			
				ExportAlgorithmDT dt = (ExportAlgorithmDT) iter.next();
				if(dt.getExportAlgorithmUid().toString().equalsIgnoreCase(algNameUid)) {
					algorithmForm.setExADT(dt);
					algorithmForm.setOldExADT(dt);
					break;
				}
			}
		}		
		 
			try{
				HttpSession session = request.getSession();
				ArrayList<?> triggerFieldList = cnUtil.getExportTriggerFields(session);
				//Setting the values to form upon Load
				algorithmForm.setTriggerFields(triggerFieldList);
				algorithmForm.setTriggerFieldList(cnUtil.getExportTriggerFieldDDs(algorithmForm.getTriggerFields()));	
				
			}catch (Exception e){
				request.setAttribute("error", e.getMessage());
			}
		 
		
		request.setAttribute("manageList", algorithmList);
		return (mapping.findForward("addTrigger"));		
	}
	
	public ActionForward AddTriggerSubmit(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) {
		ExportAlgorithmForm algorithmForm = (ExportAlgorithmForm) form;
	    algorithmForm.setActionMode(NEDSSConstants.ADD_TRIGGER_ACTION);
		algorithmForm.setPageTitle(ExportCaseNotificationConstants.ADD_ALGORITHM_TRIGGER, request);
		Collection<?>  algorithmList = algorithmForm.getManageList();
		ExportTriggerDT exTrDT = algorithmForm.getExADT().getExTrDT();		
				
		request.setAttribute("manageList", algorithmList);
		return (mapping.findForward("addTrigger"));		
	}
	

}
