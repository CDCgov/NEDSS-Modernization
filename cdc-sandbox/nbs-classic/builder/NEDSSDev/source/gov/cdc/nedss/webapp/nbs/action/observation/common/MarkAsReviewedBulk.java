package gov.cdc.nedss.webapp.nbs.action.observation.common;




import gov.cdc.nedss.act.observation.vo.ObservationVO;
import gov.cdc.nedss.exception.NEDSSAppConcurrentDataException;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.LabResultProxyVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.MorbidityProxyVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.ObservationSummaryDisplayVO;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.vo.NBSDocumentVO;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.ObservationUtil;
import gov.cdc.nedss.webapp.nbs.action.nbsDocument.util.NBSDocumentActionUtil;
import gov.cdc.nedss.webapp.nbs.action.observation.labreport.CommonLabUtil;
import gov.cdc.nedss.webapp.nbs.action.observation.morbidityreport.util.MorbidityUtil;
import gov.cdc.nedss.webapp.nbs.form.observationreview.ObservationNeedingReviewForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;

import java.io.*;
import java.sql.Timestamp;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;


public class MarkAsReviewedBulk extends Action{

	 
    //For logging
    static final LogUtils logger = new LogUtils(MarkAsReviewedBulk.class.getName());

	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
     throws IOException, ServletException{
		//This is temporary until we decide where to save the 'Reason for not further action'
		
		ObservationNeedingReviewForm observationGeneralForm = (ObservationNeedingReviewForm)form;
		String processingDecisionSelected = observationGeneralForm.getProcessingDecisionSelected();
		String processingDecisionSelectedText="";
		ArrayList<Object> dropdownsProcessingDecision = CachedDropDowns.getCodedValueForType(observationGeneralForm.getProcessingDecisionLogic());
		ArrayList<Object> dropdownsNonSTDHIVProcessingDecision = CachedDropDowns.getCodedValueForType(NEDSSConstants.NBS_NO_ACTION_RSN);
		
		
		for(int i=0; i<dropdownsProcessingDecision.size();i++){
			if(((DropDownCodeDT)dropdownsProcessingDecision.get(i)).getKey().equalsIgnoreCase(processingDecisionSelected))
				processingDecisionSelectedText=((DropDownCodeDT)dropdownsProcessingDecision.get(i)).getValue();
		}
		if(processingDecisionSelectedText.isEmpty()){
			for(int i=0; i<dropdownsNonSTDHIVProcessingDecision.size();i++){
				if(((DropDownCodeDT)dropdownsNonSTDHIVProcessingDecision.get(i)).getKey().equalsIgnoreCase(processingDecisionSelected))
					processingDecisionSelectedText=((DropDownCodeDT)dropdownsNonSTDHIVProcessingDecision.get(i)).getValue();
			}
			
		}
		
		String markAsReviewedComments = observationGeneralForm.getMarkAsReviewedComments();
	
		
		
		String checkBoxIdsLabs = observationGeneralForm.getSelectedcheckboxIdsLabs();
		String checkBoxMprIdsLabs = observationGeneralForm.getSelectedcheckboxMprIdsLabs();
		String checkBoxIdsMorbs = observationGeneralForm.getSelectedcheckboxIdsMorbs();
		String checkBoxMprIdsMorbs = observationGeneralForm.getSelectedcheckboxMprIdsMorbs();
		String checkBoxIdsCases = observationGeneralForm.getSelectedcheckboxIdsCases();
		String checkBoxMprIdsCases = observationGeneralForm.getSelectedcheckboxMprIdsCases();
		
		
		ArrayList<String> UIDLabs = convertFromStringToArray(checkBoxIdsLabs);
		ArrayList<String> mprUIDLabs = convertFromStringToArray(checkBoxMprIdsLabs);
		
		ArrayList<String> UIDMorb = convertFromStringToArray(checkBoxIdsMorbs);
		ArrayList<String> mprUIDMorbs = convertFromStringToArray(checkBoxMprIdsMorbs);
		
		ArrayList<String> UIDCase = convertFromStringToArray(checkBoxIdsCases);
		ArrayList<String> mprUIDCases = convertFromStringToArray(checkBoxMprIdsCases);
		
		
		String processingDecisionBFP="BFP";
		String processingDecisionPriority="NPP";
		
		int labErrorBFP=0, labErrorPriority=0, labProcessed=0;
		int morbErrorBFP=0, morbErrorPriority=0, morbProcessed=0;
		
		String notProcessedDocUid="";
		
		String observationUIDString, mprUIDString;
		Long observationUID, mprUID;
		Collection<Object> observationSummaryVOs = (ArrayList<Object>) NBSContext.retrieve(request.getSession(true),
				NBSConstantUtil.DSObservationList);
		Map<Object, Object> obsMap = new HashMap<Object, Object>();
		if (observationSummaryVOs != null && observationSummaryVOs.size() > 0) {
			for (Object obj : observationSummaryVOs) {
				ObservationSummaryDisplayVO observationSummaryDisplayVO = (ObservationSummaryDisplayVO) obj;
				obsMap.put(observationSummaryDisplayVO.getObservationUID().toString(), observationSummaryDisplayVO);
			}
		}

		// LOOP FOR PROCESSING LABS
		for (int i = 0; i < UIDLabs.size(); i++) {
			observationUIDString = UIDLabs.get(i);
			mprUIDString = mprUIDLabs.get(i);
			if (!observationUIDString.isEmpty()) {
				boolean validated = true;

				if (obsMap.get(observationUIDString) != null
						&& ((ObservationSummaryDisplayVO) obsMap.get(observationUIDString))
								.getProgramAreaSTDHIV() != null
						&& ((ObservationSummaryDisplayVO) obsMap.get(observationUIDString)).getProgramAreaSTDHIV()
								.equalsIgnoreCase("true")) {

					CommonLabUtil labUtil = new CommonLabUtil();
					LabResultProxyVO labResultProxyVO = labUtil.getLabResultProxyVO(new Long(observationUIDString),
							request.getSession());

					ArrayList<String> conditionList = labResultProxyVO.getTheConditionsList();
					if (conditionList != null) {
						for (int j = 0; j < conditionList.size() && validated; j++) {
							String labCondition = conditionList.get(j);
							labCondition = CachedDropDowns.getConditionDesc(labCondition);
							if (processingDecisionSelected.equals(processingDecisionBFP)
									&& !labCondition.contains("Syphilis")) {
								labErrorBFP++;
								validated = false;
								notProcessedDocUid += observationUIDString + "|";
							} else if (processingDecisionSelected.equals(processingDecisionPriority)
									&& labCondition.contains("Syphilis")) {
								labErrorPriority++;
								validated = false;
								notProcessedDocUid += observationUIDString + "|";
							}
						}
					}
				}

				if (validated) {
					observationUID = Long.parseLong(observationUIDString);
					mprUID = Long.parseLong(mprUIDString);
					// This is temporary until we decide where to save the 'Reason for not further
					// action'

					markAsReviewLab(mapping, observationGeneralForm, request, response, observationUID, mprUID,
							processingDecisionSelected, markAsReviewedComments);
					labProcessed++;
				}
			}

		}
		
		//LOOP FOR PROCESSING MORBS
		for(int i=0; i<UIDMorb.size(); i++){
			observationUIDString = UIDMorb.get(i);
			mprUIDString = mprUIDMorbs.get(i);
			boolean validated = true;
			if (obsMap.get(observationUIDString) != null
					&& ((ObservationSummaryDisplayVO) obsMap.get(observationUIDString))
							.getProgramAreaSTDHIV() != null
					&& ((ObservationSummaryDisplayVO) obsMap.get(observationUIDString)).getProgramAreaSTDHIV()
							.equalsIgnoreCase("true")) {
	    	
	    	try{
		    	MorbidityUtil morbidityUtil = new MorbidityUtil();
		    	MorbidityProxyVO morbidityProxyVO = morbidityUtil.getProxyFromEJB(new Long(observationUIDString), request.getSession());
		    	
		    	Collection<ObservationVO> observationVOCollection = morbidityProxyVO.getTheObservationVOCollection();
		    	Iterator<ObservationVO> iter = observationVOCollection.iterator();
		    	
		    	while(iter.hasNext() && validated){
		    		ObservationVO obsVO=iter.next();
		    		String morbCondition = obsVO.getTheObservationDT().getCd();
		    		morbCondition=CachedDropDowns.getConditionDesc(morbCondition);
					if(processingDecisionSelected.equals(processingDecisionBFP)){
						morbErrorBFP++;
						validated=false;
						notProcessedDocUid+=observationUIDString+"|";
					}else
						if(processingDecisionSelected.equals(processingDecisionPriority) && morbCondition.contains("Syphilis")){
							morbErrorPriority++;
							validated=false;
							notProcessedDocUid+=observationUIDString+"|";
						}
		    	}
		    	
	    	}catch(Exception e){
	    		
	    		//TODO: handle exception
	    	}
			}

			if(validated){
				if(!observationUIDString.isEmpty()){
					observationUID=Long.parseLong(observationUIDString);
					mprUID = Long.parseLong(mprUIDString);

					markAsReviewMorb(mapping, observationGeneralForm, request, response, observationUID, mprUID, processingDecisionSelected, markAsReviewedComments);
					morbProcessed++;
				}
			}
		}
		
		//LOOP FOR PROCESSING CASES
		for(int i=0; i<UIDCase.size(); i++){
			observationUIDString = UIDCase.get(i);
			mprUIDString = mprUIDCases.get(i);
			if(!observationUIDString.isEmpty()){
				observationUID=Long.parseLong(observationUIDString);

				markAsReviewCase(mapping, form, request, response, observationUID, processingDecisionSelected, markAsReviewedComments);  
			}
		}
		
		
		//Success and error messages
		
		int processedDocuments = labProcessed + morbProcessed + UIDCase.size();
		int errorDocuments = labErrorBFP+labErrorPriority+morbErrorBFP+morbErrorPriority;
		int totalDocuments = UIDLabs.size()+UIDMorb.size()+UIDCase.size();
		
		/*
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
		request.getSession().setAttribute("notProcessedDocUid", notProcessedDocUid);
		*/
		
		request.setAttribute("processedDocuments", processedDocuments);
		request.setAttribute("errorDocuments", errorDocuments);
		request.setAttribute("processingDecisionSelectedText", processingDecisionSelectedText);
		request.setAttribute("notProcessedDocUid", notProcessedDocUid);
		request.setAttribute("totalDocuments", totalDocuments);
		return mapping.findForward("markAsReviewedBulkConfirmation");
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
	  
		public void markAsReviewMorb(ActionMapping mapping, ActionForm form,
	            HttpServletRequest request,
	            HttpServletResponse response,
	            long obsUID, long mprUid, String processingDecision, String markAsReviewedComment)
	     throws IOException, ServletException{
	
	  HttpSession session = request.getSession();

	    try {
	    	NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session.getAttribute(
	    			"NBSSecurityObject");

	    		try {

	    			String result = markAsReviewedMorbHandler(obsUID, processingDecision, markAsReviewedComment, nbsSecurityObj,
	    					session,
	    					request, response);

	    			if (result.equals(NEDSSConstants.RECORD_STATUS_PROCESSED)) {
	    				String successMsg = "The Morbidity Report has been successfully marked as Reviewed";
	    				logger.info(successMsg);
	    				request.setAttribute("displayInformationExists", successMsg);
	    			}
	    			else {
	    				logger.info("The Morb Report was not able to be set to Processed");
	    			}

	    		}
	    		catch (Exception e) {
	    			logger.fatal("Error being raised ", e);
	    		}

	    }catch (Exception e) {
	    	logger.error("Exception in View Morb: " + e.getMessage());
	    	e.printStackTrace();
	    	throw new ServletException("Error occurred in View Morbidity : "+e.getMessage());
	    }
	    //return (mapping.findForward(contextAction));
}
		
		private String markAsReviewedMorbHandler(Long observationUid, String processingDecision,
				String markAsReviewedComment, NBSSecurityObj nbsSecurityObj, HttpSession session,
				HttpServletRequest request, HttpServletResponse response)
				throws NEDSSAppConcurrentDataException, java.rmi.RemoteException,
	    javax.ejb.EJBException, Exception {

	  String markAsReviewedFlag = "";
	  /**
	   * Call the mainsessioncommand
	   */
	  MainSessionCommand msCommand = null;
	  ObservationUtil obsUtil = new ObservationUtil();
	  
	  String sBeanJndiName = JNDINames.OBSERVATION_PROXY_EJB;
	  String sMethod="processObservation";
	  Object[] oParams = new Object[]{observationUid};
	  if(processingDecision!=null && !processingDecision.trim().equals("")){
	   sMethod = "processObservationWithProcessingDecision";
	   //Map<String, Object> observationMap = obsUtil.createProcessingDecisionObservation(observationUid,"Morb",processingDecision,request);
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
	      markAsReviewedFlag = NEDSSConstants.RECORD_STATUS_PROCESSED;
				} else {
	      markAsReviewedFlag = NEDSSConstants.RECORD_STATUS_UNPROCESSED;
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
   
	public void markAsReviewCase(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response,
            long documentUid, String processingDecision, String markAsReviewedComment)
     throws IOException, ServletException{
		try{
		NBSDocumentActionUtil util = new NBSDocumentActionUtil();
		//NbsDocumentForm docForm = (NbsDocumentForm)form;
		HttpSession session = request.getSession();
		logger.info("markAsReviewed() in ViewDocumentAction is being called");
		//Long documentUid = null;
	//	documentUid = (Long)NBSContext.retrieve(session, "DSDocumentUID");
		//if(documentUid == null )  
		//	documentUid = new Long((String)request.getAttribute("docUid"));
		//get the document from the NBSDocument table

		NBSDocumentVO nbsDocVO = new NBSDocumentVO();

		try{
			nbsDocVO = util.getNBSDocument(session,documentUid);
		}catch(Exception e){
			logger.error("Error in markAsReviewedLoad1: "+e.getMessage());
			e.printStackTrace();
			throw new ServletException(e.toString());
		}
		nbsDocVO.getNbsDocumentDT().setRecordStatusCd(NEDSSConstants.OBS_PROCESSED);
		Date dat = new Date();
		Timestamp aupdateTime = new Timestamp(dat.getTime());
		nbsDocVO.getNbsDocumentDT().setRecordStatusTime(aupdateTime);
		nbsDocVO.getNbsDocumentDT().setLastChgTime(aupdateTime);
		nbsDocVO.getNbsDocumentDT().setProcessingDecisionCd(processingDecision);
		if(!markAsReviewedComment.isEmpty())
			nbsDocVO.getNbsDocumentDT().setProcessingDecisiontxt(markAsReviewedComment);
		String DSDocumentLocalID = nbsDocVO.getNbsDocumentDT().getLocalId();
		String firstNm = nbsDocVO.getPatientVO().getThePersonDT().getFirstNm() == null ? "" : nbsDocVO.getPatientVO().getThePersonDT().getFirstNm();
		String	lastNm = nbsDocVO.getPatientVO().getThePersonDT().getLastNm() == null ? "" : nbsDocVO.getPatientVO().getThePersonDT().getLastNm();

		nbsDocVO = util.setNBSDocumentVOforUpdate(nbsDocVO);
		try{
			util.setDocumentforUpdate(session,nbsDocVO);
		}catch(Exception e){
			logger.error("Error in markAsReviewedLoad2: "+e.getMessage());
			e.printStackTrace();
			throw new ServletException(e.toString());
		}
		StringBuffer sb = new StringBuffer();
		if(DSDocumentLocalID != null)
		{
			sb.append("Case Report <b>").append(DSDocumentLocalID).append("</b> for <b>").append(firstNm).append("</b>  <b>").append(lastNm).append("</b> has been successfully Marked As Reviewed.");
			request.setAttribute("docMarkReviewConfMsg", sb.toString());
		}
		request.setAttribute("docUid",documentUid+"");
	} catch (Exception ex) {
		logger.error("Error in markAsReviewedLoad: "+ex.getMessage());
		ex.printStackTrace();
		throw new ServletException("Error while markAsReviewedLoad : "+ex.getMessage());
	}
//	return mapping.findForward("cdaview");
}
	
}
