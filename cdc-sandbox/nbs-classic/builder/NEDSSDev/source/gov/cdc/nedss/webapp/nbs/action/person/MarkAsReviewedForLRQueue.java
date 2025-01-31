package gov.cdc.nedss.webapp.nbs.action.person;




import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;

import gov.cdc.nedss.exception.NEDSSAppConcurrentDataException;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.webapp.nbs.form.person.PersonSearchForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;


public class MarkAsReviewedForLRQueue extends Action{

	 
    //For logging
    static final LogUtils logger = new LogUtils(MarkAsReviewedForLRQueue.class.getName());

	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
     throws IOException, ServletException{
		String observationUIDString="";
		int labProcessed=0;
		PersonSearchForm observationGeneralForm = (PersonSearchForm)form;
		String queueName=(String)observationGeneralForm.getAttributeMap().get("queueName");
		String processingDecisionSelected = observationGeneralForm.getProcessingDecisionSelected();
        String processingDecisionSelectedStd=observationGeneralForm.getProcessingDecisionSelectedSTD();
		String processingDecisionSelectedText="";
		String processingDecissionCode="";
		String processLogic=(String)observationGeneralForm.getMarkAsReviewdReason();
		if(null == processLogic) processLogic=request.getParameter("markAsReviewdReason");
		ArrayList<Object> dropdownsNonSTDHIVProcessingDecision=null;
		if(NEDSSConstants.NBS_NO_ACTION_RSN.equalsIgnoreCase(processLogic)) {
			dropdownsNonSTDHIVProcessingDecision = CachedDropDowns.getCodedValueForType(NEDSSConstants.NBS_NO_ACTION_RSN);
			for(int i=0; i<dropdownsNonSTDHIVProcessingDecision.size();i++){
					if(((DropDownCodeDT)dropdownsNonSTDHIVProcessingDecision.get(i)).getKey().equalsIgnoreCase(processingDecisionSelected))
						processingDecisionSelectedText=((DropDownCodeDT)dropdownsNonSTDHIVProcessingDecision.get(i)).getValue();
				}
			if(null != processingDecisionSelectedText && processingDecisionSelectedText.trim().length() > 0) processingDecissionCode=processingDecisionSelected;
			
		} else if(NEDSSConstants.STD_PROCESSING_DECISION_LIST_SYPHILIS_AND_NONSYPHILIS.equalsIgnoreCase(processLogic)) {
			 dropdownsNonSTDHIVProcessingDecision = CachedDropDowns.getCodedValueForType(NEDSSConstants.STD_PROCESSING_DECISION_LIST_SYPHILIS_AND_NONSYPHILIS);
			for(int i=0; i<dropdownsNonSTDHIVProcessingDecision.size();i++){
				if(((DropDownCodeDT)dropdownsNonSTDHIVProcessingDecision.get(i)).getKey().equalsIgnoreCase(processingDecisionSelectedStd))
					processingDecisionSelectedText=((DropDownCodeDT)dropdownsNonSTDHIVProcessingDecision.get(i)).getValue();
			}
			if(null != processingDecisionSelectedText && processingDecisionSelectedText.trim().length() > 0) processingDecissionCode=processingDecisionSelectedStd;
		} else {};
		
		String markAsReviewedComments = observationGeneralForm.getMarkAsReviewedComments();
		String checkBoxIdsLabs = observationGeneralForm.getSelectedcheckboxIdsLabs();
		ArrayList<String> UIDLabs = convertFromStringToArray(checkBoxIdsLabs);
		for (int i = 0; i < UIDLabs.size(); i++) {
			observationUIDString = UIDLabs.get(i);
			if (!observationUIDString.isEmpty()) {
			
				markAsReviewLab(mapping, observationGeneralForm, request, response, Long.parseLong(observationUIDString), 0L,
						processingDecissionCode, markAsReviewedComments);
				labProcessed++;
			}
		}
		
	//Success and error messages
		
		int processedDocuments = labProcessed;
		//int errorDocuments = labErrorBFP+labErrorPriority+morbErrorBFP+morbErrorPriority;
		int totalDocuments = UIDLabs.size();
		int errorDocuments=0;
		
		String confirmationMessage ="", errorMessage="";
		if(processedDocuments>1 && errorDocuments==0)
			confirmationMessage="The selected <b>"+processedDocuments+"</b> documents have been successfully marked as reviewed as <b>'"+processingDecisionSelectedText+"'</b>.";
		if(processedDocuments==1 && errorDocuments==0)
			confirmationMessage="The selected <b>"+processedDocuments+"</b> document has been successfully marked as reviewed as <b>'"+processingDecisionSelectedText+"'</b>.";
		if(processedDocuments==0 && errorDocuments>1)
			errorMessage="The selected <b>"+errorDocuments+"</b> documents can not have the processing decision of <b>'"+processingDecisionSelectedText+"'</b> applied. Please, select another processing decision and try again.";
		if(processedDocuments==0 && errorDocuments==1)
			errorMessage="The selected <b>"+errorDocuments+"</b> document can not have the processing decision of <b>'"+processingDecisionSelectedText+"'</b> applied. Please, select another processing decision and try again.";
		if(processedDocuments>0 && errorDocuments>0)
			confirmationMessage="<b>"+processedDocuments+"</b> of the selected <b>"+totalDocuments+"</b> documents were successfully marked as reviewed as <b>'"+processingDecisionSelectedText+"'</b>."+
		" The remaining documents could not be marked as reviewed and will need to have another processing decision selected.";

		
		request.getSession().setAttribute("msgBlock", confirmationMessage);
		request.getSession().setAttribute("errorBlock", errorMessage);
		//observationGeneralForm.setProcessingDecisionSelected("");
		ActionRedirect redirect = new ActionRedirect(mapping.findForward("markAsReviewedBulkConfirmation"));
		redirect.addParameter("queueName", queueName);
		redirect.addParameter("msgBlock", confirmationMessage);
		redirect.addParameter("errorBlock", errorMessage);
		return redirect;
		//return mapping.findForward("markAsReviewedBulkConfirmation");
		
	}
	
	public void markAsReviewLab(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, long obsUID, long mprUid, String processingDecision,
			String markAsReviewedComment) throws IOException, ServletException {

		HttpSession session = request.getSession();

		if (session == null) {
			logger.fatal("error no session");
			// return mapping.findForward("login");
		}

		NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session.getAttribute("NBSSecurityObject");
		if (nbsSecurityObj == null) {
			logger.fatal("Error: No securityObj in the session, go back to login screen");
			// return mapping.findForward("login");
		}

		try {

			try {

				String result = markAsReviewedLabHandler(obsUID, processingDecision, markAsReviewedComment,
						nbsSecurityObj, session, request, response);
				if (result.equals(NEDSSConstants.RECORD_STATUS_PROCESSED)) {
					String successMsg = "The Lab Report has been successfully marked as Reviewed";
					logger.info(successMsg);
					request.setAttribute("displayInformationExists", successMsg);
				} else {
					logger.info("The Lab Report was not able to be set to Processed");
				}
			}

			catch (Exception ncde) {
				logger.fatal("Data Concurrency Error being raised ", ncde);
				// return mapping.findForward("dataerror");
			} finally {
				session.setAttribute("DSPatientPersonLocalID", null);
				session.removeAttribute("DSPatientPersonLocalID");
			}
		} catch (Exception e) {
			logger.error("General exception in View Lab Report Submit: " + e.getMessage());
			e.printStackTrace();
			throw new ServletException("An error occurred in View Lab Report Submit: " + e.getMessage());
		}
		// return mapping.findForward(contextAction);

	}	

	  private String markAsReviewedLabHandler(Long observationUid, String processingDecision, 
			  String markAsReviewedComment,
				NBSSecurityObj nbsSecurityObj, HttpSession session,
				HttpServletRequest request, HttpServletResponse response)
				throws NEDSSAppConcurrentDataException, java.rmi.RemoteException,
	      javax.ejb.EJBException, Exception {

	    String markAsReviewedFlag = "";
	    /**
	     * Call the mainsessioncommand
	     */
	    MainSessionCommand msCommand = null;
	    //ObservationUtil obsUtil = new ObservationUtil();
	    
	    String sBeanJndiName = JNDINames.OBSERVATION_PROXY_EJB;
	    String sMethod="processObservation";
	    Object[] oParams = new Object[]{observationUid};
	    if(processingDecision!=null && !processingDecision.trim().equals("")){
	     sMethod = "processObservationWithProcessingDecision";
	     //Map<String, Object> observationMap = obsUtil.createProcessingDecisionObservation(observationUid,"Lab",processingDecision,request);
	     oParams = new Object[]{
	             observationUid, processingDecision, markAsReviewedComment};
	    }
	    
			logger.debug("ObservationSubmit: markAsReviewedHandler with observationID = " + observationUid);

	    /**
	     * Output ObservationProxyVO for debugging
	     */
	    MainSessionHolder holder = new MainSessionHolder();
	    msCommand = holder.getMainSessionCommand(session);
	    List<?> resultUIDArr = new ArrayList<Object> ();
			resultUIDArr = msCommand
					.processRequest(sBeanJndiName, sMethod, oParams);

	    boolean result = false;
	    if ( (resultUIDArr != null) && (resultUIDArr.size() > 0)) {
				logger.debug("Marked as Reviewed result and arg = "
						+ resultUIDArr.get(0));
	      result = ( (Boolean) resultUIDArr.get(0)).booleanValue();
	      logger.debug("The Marked AS Reviewed result is:" + result);
	      if (result) {
	        markAsReviewedFlag = "PROCESSED";
				} else {
	        markAsReviewedFlag = "UNPROCESSED";
	      }
	    }
	    return markAsReviewedFlag;
	  }
	  
	
	/**
	 * convertFromStringToArray: returns an ArrayList<String> with the UIDs parsed from the checkBoxIds string
	 * @param checkBoxIds
	 * @return
	 */

	public ArrayList<String> convertFromStringToArray(String checkBoxIds){
		
		ArrayList<String> UIDarray = new ArrayList<String>();
		if(checkBoxIds!=null){
			StringTokenizer st = new StringTokenizer(checkBoxIds,"|");
			
			while (st.hasMoreTokens()) { 
				String token = st.nextToken();
				UIDarray.add((token.toString()));
			}
		}
		return UIDarray;
	}
   

	
	
}
