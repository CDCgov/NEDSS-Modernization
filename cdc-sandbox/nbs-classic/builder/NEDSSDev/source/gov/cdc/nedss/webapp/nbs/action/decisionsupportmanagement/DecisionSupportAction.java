package gov.cdc.nedss.webapp.nbs.action.decisionsupportmanagement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gov.cdc.nedss.dsm.AlgorithmDocument;
import gov.cdc.nedss.dsm.AlgorithmDocument.Algorithm;
import gov.cdc.nedss.pagemanagement.wa.dt.BatchEntry;
import gov.cdc.nedss.systemservice.dt.EDXActivityLogDT;
import gov.cdc.nedss.systemservice.ejb.decisionsupportejb.dt.DSMAlgorithmDT;
import gov.cdc.nedss.systemservice.ejb.decisionsupportejb.util.DSMAlgorithmUtil;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.StringUtils;
import gov.cdc.nedss.webapp.nbs.action.decisionsupportmanagement.util.DecisionSupportConstants;
import gov.cdc.nedss.webapp.nbs.action.decisionsupportmanagement.util.DecisionSupportUtil;
import gov.cdc.nedss.webapp.nbs.action.decisionsupportmanagement.util.DsmActivityLogUtil;
import gov.cdc.nedss.webapp.nbs.action.util.NBSPageConstants;
import gov.cdc.nedss.webapp.nbs.form.decisionsupportmanagement.DecisionSupportForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;
import org.apache.xmlbeans.XmlException;

public class DecisionSupportAction extends DispatchAction {
	
	static final LogUtils logger = new LogUtils(DecisionSupportAction.class.getName());
	public static final String ACTION_PARAMETER	= "method";
	
	public ActionForward createLoad(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) throws
    IOException, ServletException  {
		String actionForward = "";
		try {
			DecisionSupportForm dsForm = (DecisionSupportForm) form;
			dsForm.reset();
			dsForm.setActionMode(NEDSSConstants.CREATE_LOAD_ACTION);
			//Setting Default values in the fields
			dsForm.getDecisionSupportClientVO().setAnswer(DecisionSupportConstants.FREQUENCY, DecisionSupportConstants.FREQUENCY_VALUE);
			dsForm.getDecisionSupportClientVO().setAnswer(DecisionSupportConstants.ON_FAILURE_TO_CREATE_NOTIFICATION, DecisionSupportConstants.ON_FAILURE_TO_CREATE_NOTIFICATION_VALUE);
			String array[] = {DecisionSupportConstants.APPLYTO_VALUE};
			dsForm.getDecisionSupportClientVO().setAnswerArray(DecisionSupportConstants.APPLY_TO, array);
			//dsForm.getDecisionSupportClientVO().setAnswer(DecisionSupportConstants.UPDATE_ACTION, DecisionSupportConstants.UPDATEACTION_VALUE);
			dsForm.getDecisionSupportClientVO().setAnswer(DecisionSupportConstants.QUEUE_FOR_APPROVAL, "N");
			dsForm.getDecisionSupportClientVO().setAnswer(DecisionSupportConstants.ON_FAILURE_TO_MARK_REVIEWED, "2");
			dsForm.getDecisionSupportClientVO().setAnswer(DecisionSupportConstants.AND_OR_LOGIC, DecisionSupportConstants.OR_AND_OR_LOGIC);
			dsForm.getDecisionSupportClientVO().setAnswer(DecisionSupportConstants.USE_EVENT_DATE_LOGIC, DecisionSupportConstants.USE_EVENT_DATE_LOGIC_NO);
			dsForm.getDecisionSupportClientVO().setAnswer(DecisionSupportConstants.NBS_EVENT_DATE_SELECTED, DecisionSupportConstants.NBS_EVENT_DATE_SELECTED_VALUE);
			dsForm.getCoreQuestionsList();
			dsForm.getAttributeMap().put(ACTION_PARAMETER, "createSubmit");
			dsForm.setPageTitle(DecisionSupportConstants.ADD_ALGORITHM, request);

			actionForward = "createAlgorithm";
			
			
		}catch (Exception e) {
			logger.error("Exception in createLoad: " + e.toString(),e);
			throw new ServletException("Error while create load Algorithm : "+e.getMessage(),e);
		}		
		return (mapping.findForward(actionForward));
	}
	
	public ActionForward createSubmit(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws
    IOException, ServletException {
		String actionForward = "viewAlgorithm";
		DecisionSupportForm dsForm = (DecisionSupportForm) form;
		DSMAlgorithmDT dsmAlgoDt = new DSMAlgorithmDT();
		try {
			
			dsForm.getAttributeMap().clear();
			NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) request.getSession().getAttribute("NBSSecurityObject");
			String userId = nbsSecurityObj.getTheUserProfile().getTheUser().getEntryID();
			DecisionSupportUtil.createBatchMap(dsForm);
			DecisionSupportUtil.createAdvCriteriaBatchMap(dsForm);
			DecisionSupportUtil.createAdvInvCriteriaBatchMap(dsForm);
		
			DecisionSupportUtil.setDecisionSupportDtforCreateEdit(dsForm, dsmAlgoDt);
			DecisionSupportUtil.setAnswerArrayMapAnswers(dsForm, dsmAlgoDt);
			dsmAlgoDt.setLastChgUserId(new Long(userId));
			dsForm.getAttributeMap().put("FormCd",dsForm.getDecisionSupportClientVO().getAnswer(DecisionSupportConstants.INVESTIGATION_TYPE_RELATED_PAGE));
			
			DSMAlgorithmUtil dsmAlgorithmUtil = new DSMAlgorithmUtil();
			dsmAlgorithmUtil.createAlgorithmPayload(dsForm.getQuestionMap(), dsmAlgoDt, dsForm.getDecisionSupportClientVO(), dsForm.getAdvInvQuestionMap());
			
			dsForm.setActionMode(NEDSSConstants.VIEW_LOAD_ACTION);
			String eventTypeCode = dsForm.getDecisionSupportClientVO().getAnswer(DecisionSupportConstants.EVENT_TYPE);
			if("11648804".equals(eventTypeCode))
				request.setAttribute("EventTypeName", "Laboratory Report");
			if(NEDSSConstants.PHC_236.equals(eventTypeCode))
				request.setAttribute("EventTypeName", "Case Report");
			//Saving data 
			Long algorithmUid = DecisionSupportUtil.sendDtToEJBForCreate(dsmAlgoDt, request);
			dsmAlgoDt.setDsmAlgorithmUid(algorithmUid);
			dsForm.setOldDT(dsmAlgoDt);
			dsForm.setPageTitle(DecisionSupportConstants.VIEW_ALGORITHM, request);
			if(dsForm.getDecisionSupportClientVO().getAnswer(DecisionSupportConstants.QUEUE_FOR_APPROVAL).equals("Y"))
				dsForm.getDecisionSupportClientVO().setAnswer(DecisionSupportConstants.QUEUE_FOR_APPROVAL, "Yes");
			else
				dsForm.getDecisionSupportClientVO().setAnswer(DecisionSupportConstants.QUEUE_FOR_APPROVAL, "No");
			request.getSession().setAttribute("DSForm", dsForm);
			//data for algorithm summary
			dsForm.getAttributeMap().put("AlgorithmName", dsmAlgoDt.getAlgorithmNm());
			dsForm.getAttributeMap().put("AlgorithmId", algorithmUid.toString());
			dsForm.getAttributeMap().put("EventType", CachedDropDowns.getCodeDescTxtForCd(dsmAlgoDt.getEventType(),"PUBLIC_HEALTH_EVENT"));
			dsForm.getAttributeMap().put("TestStatus", "");
			dsForm.getAttributeMap().put("StatusCd", "Inactive");
			dsForm.getAttributeMap().put("Action", CachedDropDowns.getCodeDescTxtForCd(dsForm.getDecisionSupportClientVO().getAnswer(DecisionSupportConstants.ACTION),"NBS_EVENT_ACTION"));
			dsForm.getAttributeMap().put("TotalRecordProcessed", "");
			dsForm.getAttributeMap().put("DateLastRun", "N/A");
			
			String activeInd = dsmAlgoDt.getStatusCd();
			if(activeInd != null && activeInd.equals("A"))
			{
				request.setAttribute("ActiveInd", "Inactive");
			}else if(activeInd != null && activeInd.equals("I"))
			{
				request.setAttribute("ActiveInd", "Active");
			}
			request.setAttribute("algorithmUid", algorithmUid);
		}catch (Exception e) {
			if(e.toString().indexOf("The Algorithm Name already exists") != -1)
			{
				request.setAttribute("FormCd",dsForm.getDecisionSupportClientVO().getAnswer(DecisionSupportConstants.INVESTIGATION_TYPE_RELATED_PAGE));
				request.setAttribute("Conditions", dsForm.getDecisionSupportClientVO().getAnswerArray(DecisionSupportConstants.CONDITIONS));
				dsForm.setActionMode(NEDSSConstants.EDIT_LOAD_ACTION);
				dsForm.setFromImport("Yes");
				dsForm.getAttributeMap().put(ACTION_PARAMETER, "createSubmit");
				dsForm.setPageTitle(DecisionSupportConstants.ADD_ALGORITHM, request);
				request.setAttribute("error","The Algorithm Name already exists. Please enter a unique Algorithm Name to create a new record.");
				logger.error("Exception in createAlgorithmSubmit: " + e.getMessage());
				return (mapping.findForward("createAlgorithm"));
			}else{
				logger.error("Exception in createSubmit: " + e.toString(),e);
				throw new ServletException("Error while create Store Algorithm : "+e.getMessage(),e);
			}
		}finally {
			request.setAttribute("AlgorithmNm", dsmAlgoDt.getAlgorithmNm());
			Long algorithmUid = dsmAlgoDt.getDsmAlgorithmUid();
			dsForm.getAttributeMap().put("Edit", "/nbs/DecisionSupport.do?method=editSubmit&algorithmUid="+algorithmUid);
			dsForm.getAttributeMap().put("MakeInactive", "/nbs/DecisionSupport.do?method=makeAlgorithmInactive&algorithmUid="+algorithmUid+"&algorithmNm="+dsmAlgoDt.getAlgorithmNm());
			dsForm.getAttributeMap().put("MakeActive", "/nbs/DecisionSupport.do?method=makeAlgorithmActive&algorithmUid="+algorithmUid+"&algorithmNm="+dsmAlgoDt.getAlgorithmNm());
			
		}	
		StringBuffer confirmMesg = new StringBuffer();
		confirmMesg.append(" has been successfully added to the system.");
		request.setAttribute("ConfirmMesg", confirmMesg.toString());
		return (mapping.findForward(actionForward));
	}
	
	public ActionForward editLoad(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) throws
    IOException, ServletException {
		String actionForward = "createAlgorithm";
		String algorithmUid = null;
		try {
			DecisionSupportForm dsForm = (DecisionSupportForm) form;
			dsForm.reset();
			dsForm.setActionMode(NEDSSConstants.EDIT_LOAD_ACTION);
			dsForm.getAttributeMap().put(ACTION_PARAMETER, "editSubmit");
			dsForm.setFromImport("No");
			DSMAlgorithmDT dsmAlgoDt = null;
			algorithmUid = request.getParameter("algorithmUid");
			if(algorithmUid == null){
				dsmAlgoDt = (DSMAlgorithmDT)dsForm.getOldDT();
				algorithmUid = "" + dsmAlgoDt.getDsmAlgorithmUid();
			}
			else{
				// for editLoad from Library
				dsmAlgoDt = DecisionSupportUtil.getDtfromEJBForViewEdit(Long.valueOf(algorithmUid), request);
				dsForm.setOldDT(dsmAlgoDt);
			}
			DecisionSupportUtil.setAlgorithmInfoOnForm(dsForm, dsmAlgoDt);
			DecisionSupportUtil.setDtToAnswerArrayMap(dsForm, dsmAlgoDt);
			dsForm.getCoreQuestionsList();
			
			// clean the batchMaps from the form to reload fresh one
			dsForm.getDecisionSupportClientVO().setBatchEntryList(new ArrayList<BatchEntry>());
			dsForm.getDecisionSupportClientVO().setAdvancedCriteriaBatchEntryList(new ArrayList<BatchEntry>());
			
			DSMAlgorithmUtil dsmAlgorithmUtil = new DSMAlgorithmUtil();
			dsmAlgorithmUtil.parseAlgorithmPayload(dsmAlgoDt.getAlgorithmPayload(), dsForm.getDecisionSupportClientVO(), request.getSession());
			
			dsForm.getDecisionSupportClientVO().setAnswer(DecisionSupportConstants.NBS_EVENT_DATE_SELECTED, DecisionSupportConstants.NBS_EVENT_DATE_SELECTED_VALUE);
			
			if(dsForm.getDecisionSupportClientVO().getAnswer(DecisionSupportConstants.QUEUE_FOR_APPROVAL) != null && dsForm.getDecisionSupportClientVO().getAnswer(DecisionSupportConstants.QUEUE_FOR_APPROVAL).equals("Yes"))
				dsForm.getDecisionSupportClientVO().setAnswer(DecisionSupportConstants.QUEUE_FOR_APPROVAL, "Y");
			else
				dsForm.getDecisionSupportClientVO().setAnswer(DecisionSupportConstants.QUEUE_FOR_APPROVAL, "N");
			String typeRelPage = dsForm.getDecisionSupportClientVO().getAnswer(DecisionSupportConstants.INVESTIGATION_TYPE_RELATED_PAGE);
			request.setAttribute("FormCd",dsForm.getDecisionSupportClientVO().getAnswer(DecisionSupportConstants.INVESTIGATION_TYPE_RELATED_PAGE));
			request.setAttribute("Conditions", dsForm.getDecisionSupportClientVO().getAnswerArray(DecisionSupportConstants.CONDITIONS));
			String eventTypeCode = dsForm.getDecisionSupportClientVO().getAnswer(DecisionSupportConstants.EVENT_TYPE);
			if("11648804".equals(eventTypeCode))
				request.setAttribute("EventTypeName", "Laboratory Report");
			if(NEDSSConstants.PHC_236.equals(eventTypeCode))
				request.setAttribute("EventTypeName", "Case Report");
		
			request.setAttribute("algorithmUid", algorithmUid);
			//create default UI batch map from java batchMap
			if(dsForm.getDecisionSupportClientVO().getAnswer(DecisionSupportConstants.ACTION).equals("CREATE_INV"))
			{
				dsForm.getDecisionSupportClientVO().setAnswer(DecisionSupportConstants.NOTIFICATION_COMMENTS, null);
			}
			if(dsForm.getDecisionSupportClientVO().getAnswer(DecisionSupportConstants.ON_FAILURE_TO_CREATE_NOTIFICATION)== null)
				dsForm.getDecisionSupportClientVO().setAnswer(DecisionSupportConstants.ON_FAILURE_TO_CREATE_NOTIFICATION, DecisionSupportConstants.ON_FAILURE_TO_CREATE_NOTIFICATION_VALUE);
			dsForm.getDecisionSupportClientVO().setAnswer(DecisionSupportConstants.ON_FAILURE_TO_MARK_REVIEWED, "2");
			// create UI BatchMap from XML BatchMap
			DecisionSupportUtil.createDefaultUIBatchMap(dsForm, typeRelPage);
			DecisionSupportUtil.createAdvancedCriteriaUIBatchMap(dsForm, typeRelPage);
			DecisionSupportUtil.createAdvancedInvCriteriaUIBatchMap(dsForm, DecisionSupportConstants.CORE_INV_FORM);
			dsForm.setPageTitle(DecisionSupportConstants.EDIT_ALGORITHM, request);
			
			//data for algorithm summary
			String statusCd = "";
			if(dsmAlgoDt.getStatusCd() != null && dsmAlgoDt.getStatusCd().equals("I"))
				statusCd = "Inactive";
			else if(dsmAlgoDt.getStatusCd() != null && dsmAlgoDt.getStatusCd().equals("A"))
				statusCd = "Active";
			dsForm.getAttributeMap().put("AlgorithmName", dsmAlgoDt.getAlgorithmNm());
			dsForm.getAttributeMap().put("AlgorithmId", algorithmUid.toString());
			dsForm.getAttributeMap().put("EventType", CachedDropDowns.getCodeDescTxtForCd(dsmAlgoDt.getEventType(),"PUBLIC_HEALTH_EVENT"));
			dsForm.getAttributeMap().put("TestStatus", "");
			dsForm.getAttributeMap().put("StatusCd", statusCd);
			dsForm.getAttributeMap().put("Action", CachedDropDowns.getCodeDescTxtForCd(dsForm.getDecisionSupportClientVO().getAnswer(DecisionSupportConstants.ACTION),"NBS_EVENT_ACTION"));
			dsForm.getAttributeMap().put("TotalRecordProcessed", "");
			EDXActivityLogDT activityLogDT = DsmActivityLogUtil.retrieveLatestDsmActivityLog(
					dsmAlgoDt.getAlgorithmNm(), request.getSession(true));
			if(activityLogDT == null)
				dsForm.getAttributeMap().put("DateLastRun", "N/A");
			else
				dsForm.getAttributeMap().put("DateLastRun", StringUtils.formatDate(activityLogDT.getRecordStatusTime()));
			
		}catch (Exception e) {
			logger.error("Exception in editLoad: " + e.toString(),e);
			throw new ServletException("Error while edit load Algorithm : "+e.getMessage(),e);
		}
		
		return (mapping.findForward(actionForward));
	}
	
	public ActionForward editSubmit(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) throws
    IOException, ServletException {
		
		String actionForward = "viewAlgorithm";
		DecisionSupportForm dsForm = (DecisionSupportForm) form;
		DSMAlgorithmDT dsmAlgoDt = (DSMAlgorithmDT)dsForm.getOldDT();
		try {
			dsForm.getAttributeMap().clear();
			request.getSession().removeAttribute("DSForm");
			NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) request.getSession().getAttribute("NBSSecurityObject");
			String userId = nbsSecurityObj.getTheUserProfile().getTheUser().getEntryID();
			DecisionSupportUtil.createBatchMap(dsForm);
			DecisionSupportUtil.createAdvCriteriaBatchMap(dsForm);
			DecisionSupportUtil.createAdvInvCriteriaBatchMap(dsForm);
			DecisionSupportUtil.setDecisionSupportDtforCreateEdit(dsForm, dsmAlgoDt);
			DecisionSupportUtil.setAnswerArrayMapAnswers(dsForm, dsmAlgoDt);
			dsmAlgoDt.setLastChgUserId(new Long(userId));
			dsForm.getAttributeMap().put("FormCd",dsForm.getDecisionSupportClientVO().getAnswer(DecisionSupportConstants.INVESTIGATION_TYPE_RELATED_PAGE));
			
			DSMAlgorithmUtil dsmAlgorithmUtil = new DSMAlgorithmUtil();
			dsmAlgorithmUtil.createAlgorithmPayload(dsForm.getQuestionMap(), dsmAlgoDt, dsForm.getDecisionSupportClientVO(), dsForm.getAdvInvQuestionMap());
			dsForm.setActionMode(NEDSSConstants.VIEW_LOAD_ACTION);
			//Saving data 
			Long algorithmUid = DecisionSupportUtil.sendDtToEJBForEdit(dsmAlgoDt, request);
			dsForm.setPageTitle(DecisionSupportConstants.VIEW_ALGORITHM, request);
			
			if(dsForm.getDecisionSupportClientVO().getAnswer(DecisionSupportConstants.QUEUE_FOR_APPROVAL).equals("Y"))
				dsForm.getDecisionSupportClientVO().setAnswer(DecisionSupportConstants.QUEUE_FOR_APPROVAL, "Yes");
			else
				dsForm.getDecisionSupportClientVO().setAnswer(DecisionSupportConstants.QUEUE_FOR_APPROVAL, "No");
			request.getSession().setAttribute("DSForm", dsForm);
			
			String statusCd = "";
			if(dsmAlgoDt.getStatusCd() != null && dsmAlgoDt.getStatusCd().equals("I"))
				statusCd = "Inactive";
			else if(dsmAlgoDt.getStatusCd() != null && dsmAlgoDt.getStatusCd().equals("A"))
				statusCd = "Active";
			//data for algorithm summary
			dsForm.getAttributeMap().put("AlgorithmName", dsmAlgoDt.getAlgorithmNm());
			dsForm.getAttributeMap().put("AlgorithmId", algorithmUid.toString());
			dsForm.getAttributeMap().put("EventType", CachedDropDowns.getCodeDescTxtForCd(dsmAlgoDt.getEventType(),"PUBLIC_HEALTH_EVENT"));
			dsForm.getAttributeMap().put("TestStatus", statusCd);
			dsForm.getAttributeMap().put("StatusCd", statusCd);
			dsForm.getAttributeMap().put("Action", CachedDropDowns.getCodeDescTxtForCd(dsForm.getDecisionSupportClientVO().getAnswer(DecisionSupportConstants.ACTION),"NBS_EVENT_ACTION"));
			dsForm.getAttributeMap().put("TotalRecordProcessed", "");
			dsForm.getAttributeMap().put("DateLastRun", "N/A");
			
			String eventTypeCode = dsForm.getDecisionSupportClientVO().getAnswer(DecisionSupportConstants.EVENT_TYPE);
			if("11648804".equals(eventTypeCode))
				request.setAttribute("EventTypeName", "Laboratory Report");
			if(NEDSSConstants.PHC_236.equals(eventTypeCode))
				request.setAttribute("EventTypeName", "Case Report");
			
			String activeInd = dsmAlgoDt.getStatusCd();
			if(activeInd != null && activeInd.equals("A"))
			{
				request.setAttribute("ActiveInd", "Inactive");
			}else if(activeInd != null && activeInd.equals("I"))
			{
				request.setAttribute("ActiveInd", "Active");
			}
			request.setAttribute("algorithmUid", algorithmUid);
		}catch (Exception e) {
			if(e.toString().indexOf("The Algorithm Name already exists") != -1)
			{
				request.setAttribute("FormCd",dsForm.getDecisionSupportClientVO().getAnswer(DecisionSupportConstants.INVESTIGATION_TYPE_RELATED_PAGE));
				request.setAttribute("Conditions", dsForm.getDecisionSupportClientVO().getAnswerArray(DecisionSupportConstants.CONDITIONS));
				dsForm.setActionMode(NEDSSConstants.EDIT_LOAD_ACTION);
				dsForm.getAttributeMap().put(ACTION_PARAMETER, "editSubmit");
				dsForm.setPageTitle(DecisionSupportConstants.EDIT_ALGORITHM, request);
				request.setAttribute("error","The Algorithm Name already exists. Please enter a unique Algorithm Name.");
				logger.error("Exception in editAlgorithmSubmit: " + e.getMessage());
				return (mapping.findForward("createAlgorithm"));
			}else{
				logger.error("Exception in editSubmit: " + e.toString(),e);
				throw new ServletException("Error while update Algorithm : "+e.getMessage(),e);
			}
		}finally {
			request.setAttribute("AlgorithmNm", dsmAlgoDt.getAlgorithmNm());
			Long algorithmUid = dsmAlgoDt.getDsmAlgorithmUid();
			dsForm.getAttributeMap().put("Edit", "/nbs/DecisionSupport.do?method=editSubmit&algorithmUid="+algorithmUid);
			dsForm.getAttributeMap().put("MakeInactive", "/nbs/DecisionSupport.do?method=makeAlgorithmInactive&algorithmUid="+algorithmUid+"&algorithmNm="+dsmAlgoDt.getAlgorithmNm());
			dsForm.getAttributeMap().put("MakeActive", "/nbs/DecisionSupport.do?method=makeAlgorithmActive&algorithmUid="+algorithmUid+"&algorithmNm="+dsmAlgoDt.getAlgorithmNm());
			
		}	

		StringBuffer confirmMesg = new StringBuffer();
		confirmMesg.append(" has been successfully updated to the system.");
		request.setAttribute("ConfirmMesg", confirmMesg.toString());
		return (mapping.findForward(actionForward));
	}
	
	public ActionForward viewLoad(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) throws ServletException {
		String actionForward = "viewAlgorithm";
		DecisionSupportForm dsForm = (DecisionSupportForm) form;
		dsForm.getDecisionSupportClientVO().getAnswerMap().clear();
		String algorithmUid = request.getParameter("algorithmUid");
		DSMAlgorithmDT dsmAlgoDt = null;
		dsForm.setActionMode(NEDSSConstants.VIEW_LOAD_ACTION);
		dsForm.setPageTitle(DecisionSupportConstants.VIEW_ALGORITHM, request);
		
		try{
			// get the Library data from backend
			request.getSession().removeAttribute("DSForm");
			dsmAlgoDt = DecisionSupportUtil.getDtfromEJBForViewEdit(Long.valueOf(algorithmUid), request);
			DecisionSupportUtil.setAlgorithmInfoOnForm(dsForm, dsmAlgoDt);
			DecisionSupportUtil.setDtToAnswerArrayMap(dsForm, dsmAlgoDt);
			dsForm.setOldDT(dsmAlgoDt);
			String activeInd = dsmAlgoDt.getStatusCd();
			if(activeInd != null && activeInd.equals("A"))
			{
				request.setAttribute("ActiveInd", "Inactive");
			}else if(activeInd != null && activeInd.equals("I"))
			{
				request.setAttribute("ActiveInd", "Active");
			}
			// clean the batchMaps from the form to reload fresh one
			dsForm.getDecisionSupportClientVO().setBatchEntryList(new ArrayList<BatchEntry>());
			dsForm.getDecisionSupportClientVO().setAdvancedCriteriaBatchEntryList(new ArrayList<BatchEntry>());
			
			DSMAlgorithmUtil dsmAlgorithmUtil = new DSMAlgorithmUtil();
			dsmAlgorithmUtil.parseAlgorithmPayload(dsmAlgoDt.getAlgorithmPayload(), dsForm.getDecisionSupportClientVO(), request.getSession());
			
			if(dsForm.getDecisionSupportClientVO().getAnswer(DecisionSupportConstants.QUEUE_FOR_APPROVAL) != null && dsForm.getDecisionSupportClientVO().getAnswer(DecisionSupportConstants.QUEUE_FOR_APPROVAL).equals("Y"))
				dsForm.getDecisionSupportClientVO().setAnswer(DecisionSupportConstants.QUEUE_FOR_APPROVAL, "Yes");
			else
				dsForm.getDecisionSupportClientVO().setAnswer(DecisionSupportConstants.QUEUE_FOR_APPROVAL, "No");
			dsForm.getAttributeMap().put("FormCd",dsForm.getDecisionSupportClientVO().getAnswer(DecisionSupportConstants.INVESTIGATION_TYPE_RELATED_PAGE));
			// reload the questionMap in the form
			dsForm.getDwrQuestionsList(dsForm.getDecisionSupportClientVO().getAnswer(DecisionSupportConstants.INVESTIGATION_TYPE_RELATED_PAGE));
			request.getSession().setAttribute("DSForm", dsForm);
			
			// create UI BatchMap from XML BatchMap
			DecisionSupportUtil.createDefaultUIBatchMap(dsForm, dsForm.getDecisionSupportClientVO().getAnswer(DecisionSupportConstants.INVESTIGATION_TYPE_RELATED_PAGE));
			DecisionSupportUtil.createAdvancedCriteriaUIBatchMap(dsForm, dsForm.getDecisionSupportClientVO().getAnswer(DecisionSupportConstants.INVESTIGATION_TYPE_RELATED_PAGE));
			DecisionSupportUtil.createAdvancedInvCriteriaUIBatchMap(dsForm, DecisionSupportConstants.CORE_INV_FORM);
			
			String statusCd = "";
			if(dsmAlgoDt.getStatusCd() != null && dsmAlgoDt.getStatusCd().equals("I"))
				statusCd = "Inactive";
			else if(dsmAlgoDt.getStatusCd() != null && dsmAlgoDt.getStatusCd().equals("A"))
				statusCd = "Active";
			//data for algorithm summary
			String eventTypeCode = dsForm.getDecisionSupportClientVO().getAnswer(DecisionSupportConstants.EVENT_TYPE);
			if("11648804".equals(eventTypeCode))
				request.setAttribute("EventTypeName", "Laboratory Report");
			if(NEDSSConstants.PHC_236.equals(eventTypeCode))
				request.setAttribute("EventTypeName", "Case Report");
			
			dsForm.getAttributeMap().put("AlgorithmName", dsmAlgoDt.getAlgorithmNm());
			dsForm.getAttributeMap().put("AlgorithmId", algorithmUid.toString());
			dsForm.getAttributeMap().put("EventType", CachedDropDowns.getCodeDescTxtForCd(dsmAlgoDt.getEventType(),"PUBLIC_HEALTH_EVENT"));
			dsForm.getAttributeMap().put("TestStatus", "");
			dsForm.getAttributeMap().put("StatusCd", statusCd);
			dsForm.getAttributeMap().put("Action", CachedDropDowns.getCodeDescTxtForCd(dsForm.getDecisionSupportClientVO().getAnswer(DecisionSupportConstants.ACTION),"NBS_EVENT_ACTION"));
			dsForm.getAttributeMap().put("TotalRecordProcessed", "");
			EDXActivityLogDT activityLogDT = DsmActivityLogUtil.retrieveLatestDsmActivityLog(
					dsmAlgoDt.getAlgorithmNm(), request.getSession(true));
			if(activityLogDT == null)
				dsForm.getAttributeMap().put("DateLastRun", "N/A");
			else
				dsForm.getAttributeMap().put("DateLastRun", StringUtils.formatDate(activityLogDT.getRecordStatusTime()));
		}catch (Exception e) {
			logger.error("Exception in viewAlgorithmLoad: " + e.toString(), e);
			throw new ServletException("Error while viewing Algorithm : "+e.getMessage(),e);
		}
		request.setAttribute("mode", request.getParameter("mode"));
		request.setAttribute("AlgorithmNm", dsmAlgoDt.getAlgorithmNm());
		request.setAttribute("algorithmUid", algorithmUid);	
		dsForm.getAttributeMap().put("Edit", "/nbs/DecisionSupport.do?method=editSubmit&algorithmUid="+algorithmUid);
		dsForm.getAttributeMap().put("MakeInactive", "/nbs/DecisionSupport.do?method=makeAlgorithmInactive&algorithmUid="+algorithmUid+"&algorithmNm="+dsmAlgoDt.getAlgorithmNm());
		dsForm.getAttributeMap().put("MakeActive", "/nbs/DecisionSupport.do?method=makeAlgorithmActive&algorithmUid="+algorithmUid+"&algorithmNm="+dsmAlgoDt.getAlgorithmNm());
		return (mapping.findForward(actionForward));
	}
	
	public ActionForward makeAlgorithmInactive(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) throws ServletException {
		String actionForward = "viewAlgorithm";
		DecisionSupportForm dsForm = (DecisionSupportForm) form;
		DSMAlgorithmDT dsmAlgoDt = (DSMAlgorithmDT)dsForm.getOldDT();
		try{
			String algorithmUid = request.getParameter("algorithmUid");
			DecisionSupportUtil.sendDtToEJBForActivationInactivation(Long.valueOf(algorithmUid),"I", request);
			dsmAlgoDt.setStatusCd("I");
			String eventTypeCode = dsForm.getDecisionSupportClientVO().getAnswer(DecisionSupportConstants.EVENT_TYPE);
			if("11648804".equals(eventTypeCode))
				request.setAttribute("EventTypeName", "Laboratory Report");
			if(NEDSSConstants.PHC_236.equals(eventTypeCode))
				request.setAttribute("EventTypeName", "Case Report");
			
			String activeInd = "I";
			if(activeInd != null && activeInd.equals("A"))
			{
				request.setAttribute("ActiveInd", "Inactive");
			}else if(activeInd != null && activeInd.equals("I"))
			{
				request.setAttribute("ActiveInd", "Active");
			}
			request.setAttribute("AlgorithmNm", dsmAlgoDt.getAlgorithmNm());
			request.setAttribute("algorithmUid", algorithmUid);
		}catch (Exception e) {
			request.setAttribute("error", e.toString());
			logger.error("Exception in makeAlgorithmInactive: " + e.toString(),e);
		}
		
		StringBuffer confirmMesg = new StringBuffer();
		confirmMesg.append(" has been made inactive in the system.");
		request.setAttribute("ConfirmMesg", confirmMesg.toString());
		dsForm.getAttributeMap().put("StatusCd", "Inactive");
		dsForm.getAttributeMap().put("Edit", "/nbs/DecisionSupport.do?method=editSubmit&algorithmUid="+dsmAlgoDt.getDsmAlgorithmUid());
		dsForm.getAttributeMap().put("MakeInactive", "/nbs/DecisionSupport.do?method=makeAlgorithmInactive&algorithmUid="+dsmAlgoDt.getDsmAlgorithmUid()+"&algorithmNm="+dsmAlgoDt.getAlgorithmNm());
		dsForm.getAttributeMap().put("MakeActive", "/nbs/DecisionSupport.do?method=makeAlgorithmActive&algorithmUid="+dsmAlgoDt.getDsmAlgorithmUid()+"&algorithmNm="+dsmAlgoDt.getAlgorithmNm());
		return (mapping.findForward(actionForward));
	}
	
	public ActionForward makeAlgorithmActive(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) throws ServletException {
		String actionForward = "viewAlgorithm";
		DecisionSupportForm dsForm = (DecisionSupportForm) form;
		DSMAlgorithmDT dsmAlgoDt = (DSMAlgorithmDT)dsForm.getOldDT();
		try{
			String algorithmUid = request.getParameter("algorithmUid");
			DecisionSupportUtil.sendDtToEJBForActivationInactivation( Long.valueOf(algorithmUid),"A", request);
			dsmAlgoDt.setStatusCd("A");
			String eventTypeCode = dsForm.getDecisionSupportClientVO().getAnswer(DecisionSupportConstants.EVENT_TYPE);
			if("11648804".equals(eventTypeCode))
				request.setAttribute("EventTypeName", "Laboratory Report");
			if(NEDSSConstants.PHC_236.equals(eventTypeCode))
				request.setAttribute("EventTypeName", "Case Report");
			
			String activeInd = "A";
			if(activeInd != null && activeInd.equals("A"))
			{
				request.setAttribute("ActiveInd", "Inactive");
			}else if(activeInd != null && activeInd.equals("I"))
			{
				request.setAttribute("ActiveInd", "Active");
			}
			request.setAttribute("algorithmUid", algorithmUid);
		}catch (Exception e) {
			request.setAttribute("error", e.toString());
			logger.error("Exception in makeAlgorithmActive: " + e.toString(),e);
		}
		request.setAttribute("AlgorithmNm", dsmAlgoDt.getAlgorithmNm());
		StringBuffer confirmMesg = new StringBuffer();
		confirmMesg.append(" has been made active in the system.");
		request.setAttribute("ConfirmMesg", confirmMesg.toString());
		dsForm.getAttributeMap().put("StatusCd", "Active");
		dsForm.getAttributeMap().put("Edit", "/nbs/DecisionSupport.do?method=editSubmit&algorithmUid="+dsmAlgoDt.getDsmAlgorithmUid());
		dsForm.getAttributeMap().put("MakeInactive", "/nbs/DecisionSupport.do?method=makeAlgorithmInactive&algorithmUid="+dsmAlgoDt.getDsmAlgorithmUid()+"&algorithmNm="+dsmAlgoDt.getAlgorithmNm());
		dsForm.getAttributeMap().put("MakeActive", "/nbs/DecisionSupport.do?method=makeAlgorithmActive&algorithmUid="+dsmAlgoDt.getDsmAlgorithmUid()+"&algorithmNm="+dsmAlgoDt.getAlgorithmNm());
		return (mapping.findForward(actionForward));
	}
	
	public ActionForward exportAlgorithm(
			ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)
	{
		DecisionSupportForm dsForm = (DecisionSupportForm) form;
		DSMAlgorithmDT dsmAlgoDt = (DSMAlgorithmDT)dsForm.getOldDT();
		String algorithmName = dsmAlgoDt.getAlgorithmNm();
		String status="success";
		try{
			String xmlString = retrieveExportImportXML(dsmAlgoDt);
			DecisionSupportUtil.writeXMLResponseStream(response, xmlString, algorithmName);
		}catch(Exception ex)
		{
			status="fail";
			request.setAttribute("error", ex.getMessage());
			logger.error("Exception in exportAlgorithm: " + ex.toString(),ex);
		}
		
		dsForm.setActionMode(NEDSSConstants.VIEW_LOAD_ACTION);
		request.setAttribute("algorithmUid", dsmAlgoDt.getDsmAlgorithmUid());
		String strURL ="/DecisionSupport.do?method=viewLoad&algorithmUid="+dsmAlgoDt.getDsmAlgorithmUid()+"&src=export&status="+status;
		
		ActionForward forward = new ActionForward();
		forward.setPath(strURL.toString());
		return forward;	
	}
	
	private String retrieveExportImportXML(DSMAlgorithmDT dsmAlgoDt)
	{
		String xmlText = dsmAlgoDt.getAlgorithmPayload();
		return xmlText;
	}
	
	public ActionForward importAlgorithm(ActionMapping mapping, 
			ActionForm aForm, 
			HttpServletRequest request, 
			HttpServletResponse response) throws ServletException {
		String actionForward = "createAlgorithm";
		DecisionSupportForm dsForm = (DecisionSupportForm) aForm;
		ActionMessages messages = new ActionMessages();
		
		FormFile file = dsForm.getImportFile(); 
        //InputStream is = null;
        DSMAlgorithmDT dsmAlgoDt = null;
        try{
        	dsForm.reset();
			//is = new BufferedInputStream(file.getInputStream());
			DSMAlgorithmUtil dsmAlgorithmUtil = new DSMAlgorithmUtil();
	        //String xmlInputString = DecisionSupportUtil.parseISToString(is);
	        //is.close();
	        //AlgorithmDocument algorithmDoc = AlgorithmDocument.Factory.parse(xmlInputString);
	        
			AlgorithmDocument algorithmDoc = AlgorithmDocument.Factory.parse(file.getInputStream());
			//Convert algorithm to string to transform it to new format
	        String inputXML = algorithmDoc.copy().xmlText();
	        //Transform algorithm
	        String transormedXML = dsmAlgorithmUtil.transformDSMAlgorith(inputXML);
	        //Parse transformed algorithm
	        algorithmDoc = AlgorithmDocument.Factory.parse(transormedXML);
	        
	        boolean isXmlValid = algorithmDoc.validate();
	        if(!isXmlValid)
	        	throw new XmlException("Xml Validation failed.");
	        
			Algorithm algorithm = algorithmDoc.getAlgorithm();
			Map<String, ActionMessage> sendingSystemMessageMap = new HashMap<String, ActionMessage>();
			Map<String, ActionMessage> pageConditionMessageMap = new HashMap<String, ActionMessage>();
			Map<String, ActionMessage> questionMessageMap = new HashMap<String, ActionMessage>();
			Map<String, ActionMessage> advQuestionMessageMap = new HashMap<String, ActionMessage>();
			
			Map<String, ActionMessage> resultedTestMessageMap = new HashMap<String, ActionMessage>();
			Map<String, ActionMessage> codedResultMessageMap = new HashMap<String, ActionMessage>();
			Map<String, ActionMessage> numericTypeMessageMap = new HashMap<String, ActionMessage>();
			
			dsForm.getCoreQuestionsList();
			
	        dsmAlgoDt = DecisionSupportUtil.parseAlgorithmXMLToDT(algorithm, sendingSystemMessageMap, pageConditionMessageMap, request);
	        if(!pageConditionMessageMap.isEmpty() && pageConditionMessageMap.containsKey("error"))
	        {
				logger.error("error in importAlgorithm: " + " no match conditions or related page exist in the system.");
				messages.add(NBSPageConstants.ERROR_MESSAGES_PROPERTY, pageConditionMessageMap.get("error"));
	    		request.setAttribute(NBSPageConstants.ERROR_MESSAGES_PROPERTY, messages);
				return mapping.findForward("importError");
	        }
	        
	        DecisionSupportUtil.processQuestionList(algorithm, advQuestionMessageMap, questionMessageMap, dsForm);
	        DecisionSupportUtil.processElrResultedTest(algorithm, resultedTestMessageMap, codedResultMessageMap, numericTypeMessageMap, dsForm, request);
	        dsmAlgoDt.setAlgorithmPayload(algorithmDoc.toString());
	        
	        if(!sendingSystemMessageMap.isEmpty() && sendingSystemMessageMap.containsKey("warning"))
	        {
	        	messages.add(NBSPageConstants.WARNING_MESSAGES_PROPERTY, sendingSystemMessageMap.get("warning"));
	        }
	        if(!pageConditionMessageMap.isEmpty() && pageConditionMessageMap.containsKey("warning"))
	        {
	        	messages.add(NBSPageConstants.WARNING_MESSAGES_PROPERTY, pageConditionMessageMap.get("warning"));
	        }
	        if(!advQuestionMessageMap.isEmpty() && advQuestionMessageMap.containsKey("warning"))
	        {
	        	messages.add(NBSPageConstants.WARNING_MESSAGES_PROPERTY, advQuestionMessageMap.get("warning"));
	        }
	        if(!questionMessageMap.isEmpty() && questionMessageMap.containsKey("warning"))
	        {
	        	messages.add(NBSPageConstants.WARNING_MESSAGES_PROPERTY, questionMessageMap.get("warning"));
	        }     
	        if(!resultedTestMessageMap.isEmpty() && resultedTestMessageMap.containsKey("warning"))
	        {
	        	messages.add(NBSPageConstants.WARNING_MESSAGES_PROPERTY, resultedTestMessageMap.get("warning"));
	        }
	        if(!codedResultMessageMap.isEmpty() && codedResultMessageMap.containsKey("warning"))
	        {
	        	messages.add(NBSPageConstants.WARNING_MESSAGES_PROPERTY, codedResultMessageMap.get("warning"));
	        }
	        if(!numericTypeMessageMap.isEmpty() && numericTypeMessageMap.containsKey("warning"))
	        {
	        	messages.add(NBSPageConstants.WARNING_MESSAGES_PROPERTY, numericTypeMessageMap.get("warning"));
	        }
		
	    dsForm.setOldDT(dsmAlgoDt);
		DecisionSupportUtil.setAlgorithmInfoOnForm(dsForm, dsmAlgoDt);
		DecisionSupportUtil.setDtToAnswerArrayMap(dsForm, dsmAlgoDt);
		
		// clean the batchMaps from the form to reload fresh one
		dsForm.getDecisionSupportClientVO().setBatchEntryList(new ArrayList<BatchEntry>());
		dsForm.getDecisionSupportClientVO().setAdvancedCriteriaBatchEntryList(new ArrayList<BatchEntry>());
		
		dsmAlgorithmUtil.parseAlgorithmPayload(dsmAlgoDt.getAlgorithmPayload(), dsForm.getDecisionSupportClientVO(), request.getSession());
		
		if(dsForm.getDecisionSupportClientVO().getAnswer(DecisionSupportConstants.QUEUE_FOR_APPROVAL) != null && dsForm.getDecisionSupportClientVO().getAnswer(DecisionSupportConstants.QUEUE_FOR_APPROVAL).equals("Yes"))
			dsForm.getDecisionSupportClientVO().setAnswer(DecisionSupportConstants.QUEUE_FOR_APPROVAL, "Y");
		else
			dsForm.getDecisionSupportClientVO().setAnswer(DecisionSupportConstants.QUEUE_FOR_APPROVAL, "N");
		if(dsForm.getDecisionSupportClientVO().getAnswer(DecisionSupportConstants.ON_FAILURE_TO_CREATE_NOTIFICATION)== null)
			dsForm.getDecisionSupportClientVO().setAnswer(DecisionSupportConstants.ON_FAILURE_TO_CREATE_NOTIFICATION, DecisionSupportConstants.ON_FAILURE_TO_CREATE_NOTIFICATION_VALUE);
		String typeRelPage = dsForm.getDecisionSupportClientVO().getAnswer(DecisionSupportConstants.INVESTIGATION_TYPE_RELATED_PAGE);
		request.setAttribute("FormCd",dsForm.getDecisionSupportClientVO().getAnswer(DecisionSupportConstants.INVESTIGATION_TYPE_RELATED_PAGE));
		request.setAttribute("Conditions", dsForm.getDecisionSupportClientVO().getAnswerArray(DecisionSupportConstants.CONDITIONS));
		
		String eventTypeCode = dsForm.getDecisionSupportClientVO().getAnswer(DecisionSupportConstants.EVENT_TYPE);
		if("11648804".equals(eventTypeCode))
			request.setAttribute("EventTypeName", "Laboratory Report");
		if(NEDSSConstants.PHC_236.equals(eventTypeCode))
			request.setAttribute("EventTypeName", "Case Report");
		
		//create default UI batch map from java batchMap
		if(dsForm.getDecisionSupportClientVO().getAnswer(DecisionSupportConstants.ACTION).equals("CREATE_INV"))
		{
			dsForm.getDecisionSupportClientVO().setAnswer(DecisionSupportConstants.NOTIFICATION_COMMENTS, null);
			dsForm.getDecisionSupportClientVO().setAnswer(DecisionSupportConstants.ON_FAILURE_TO_CREATE_NOTIFICATION, null);
		}
		
		// create UI BatchMap from XML BatchMap
		DecisionSupportUtil.createDefaultUIBatchMap(dsForm, typeRelPage);
		DecisionSupportUtil.createAdvancedCriteriaUIBatchMap(dsForm, typeRelPage);
		DecisionSupportUtil.createAdvancedInvCriteriaUIBatchMap(dsForm, DecisionSupportConstants.CORE_INV_FORM);
		
		dsForm.setActionMode(NEDSSConstants.EDIT_LOAD_ACTION);
		dsForm.setFromImport("Yes");
		dsForm.getAttributeMap().put(ACTION_PARAMETER, "createSubmit");
		dsForm.setPageTitle(DecisionSupportConstants.ADD_ALGORITHM, request);
		
		if(messages.isEmpty())
		{
			messages.add(NBSPageConstants.SUCCESS_MESSAGES_PROPERTY,
					new ActionMessage(NBSPageConstants.IMPORT_SUCCESS_MESSAGE_KEY, dsmAlgoDt.getAlgorithmNm()));
			request.setAttribute(NBSPageConstants.SUCCESS_MESSAGES_PROPERTY, messages);
		}else
			request.setAttribute(NBSPageConstants.WARNING_MESSAGES_PROPERTY, messages);
        }
		catch(XmlException ex)
		{
			request.setAttribute("error","Import failed. Please validate the file and try again.");
			logger.error("Exception in importAlgorithm: " + ex.toString(),ex);
			return mapping.findForward("importError");

		}
        catch (Exception e) {
        	logger.error("Exception in importAlgorithm: " + e.toString(),e);
        	throw new ServletException("Error while importing Algorithm : "+e.getMessage(),e);
		}catch(Throwable thr)
		{
			logger.error("Exception in importAlgorithm: " + thr.toString(),thr);
        	throw new ServletException("Error while importing Algorithm : "+thr.getMessage(),thr);
		}
		
		 return (mapping.findForward(actionForward));
	  }
}
