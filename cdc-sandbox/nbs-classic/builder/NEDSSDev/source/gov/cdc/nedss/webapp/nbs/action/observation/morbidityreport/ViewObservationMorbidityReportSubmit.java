package gov.cdc.nedss.webapp.nbs.action.observation.morbidityreport;

import gov.cdc.nedss.util.*;
import gov.cdc.nedss.act.observation.vo.*;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.systemservice.nbscontext.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.CoinfectionSummaryVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.*;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.act.observation.dt.*;
import gov.cdc.nedss.entity.person.vo.*;
import gov.cdc.nedss.webapp.nbs.form.morbidity.*;
import gov.cdc.nedss.webapp.nbs.action.util.*;
import gov.cdc.nedss.webapp.nbs.action.observation.morbidityreport.util.*;

import java.util.*;
import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;

/**
 * Title:        ViewObservationMorbidityReportSubmit.java
 * Description:	This is a action class for the struts implementation
 * Copyright:	Copyright (c) 2001
 * Company: 	Computer Sciences Corporation
 * @author	NEDSS Development Team
 * @version	1.0
 */

public class ViewObservationMorbidityReportSubmit
    extends CommonAction {

  protected static NedssUtils nedssUtils = null;

  private String sBeanJndiName = "";
  private String sMethod = "";
  private Object[] oParams = null;
  private String userID = "";

  //For logging
  static final LogUtils logger = new LogUtils(
      ViewObservationMorbidityReportSubmit.class.getName());

  public ViewObservationMorbidityReportSubmit() {
  }

  public ActionForward execute(ActionMapping mapping, ActionForm aForm,
                               HttpServletRequest request,
                               HttpServletResponse response) throws IOException,
      ServletException {
    HttpSession session = request.getSession();

    //get contextAction and pass to forward
    String contextAction = request.getParameter("ContextAction");

    String sCurrTask = NBSContext.getCurrentTask(session);

    if (contextAction == null || sCurrTask == null) {
      session.setAttribute("error",
                           "contextAction is " + HTMLEncoder.encodeHtml(contextAction) + " sCurrTask " +
                           sCurrTask);
      throw new ServletException("contextAction is " + contextAction + " sCurrTask " +
              sCurrTask);
    }
    try {
    	NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session.getAttribute(
    			"NBSSecurityObject");
    	MorbidityForm morbidityForm = null;
    	String investigationType = (String)request.getParameter("investigationType");
    	if(investigationType!=null)
    		NBSContext.store(session, NBSConstantUtil.DSInvestigationType, investigationType);
    	morbidityForm = (MorbidityForm) aForm;
    	MorbidityProxyVO morbidityProxyVO = morbidityForm.getMorbidityProxy();
    	MorbidityUtil morbidityUtil = new MorbidityUtil();
    	ObservationVO morbReportVO = morbidityForm.getMorbidityReport();
    	ObservationDT morbReportDT = morbReportVO.getTheObservationDT();

    	String progAreaCd = morbReportDT.getProgAreaCd();
    	String jurisdictionCd = morbReportDT.getJurisdictionCd();
    	String sharedInd = morbReportDT.getSharedInd();
    	String observationLocalUid = morbReportDT.getLocalId();
    	Long observationUid = morbReportDT.getObservationUid();
    	String observationCd = morbReportDT.getCd();

    	PersonVO personVO = morbidityUtil.findPatientRevision(morbidityProxyVO);
    	Long personUid = personVO.getThePersonDT().getPersonUid();
    	Long personParentUid = personVO.getThePersonDT().getPersonParentUid();
    	String personLocalId = personVO.getThePersonDT().getLocalId();

    	if (progAreaCd != null)
    		NBSContext.store(session, NBSConstantUtil.DSProgramArea, progAreaCd);
    	if (jurisdictionCd != null)
    		NBSContext.store(session, NBSConstantUtil.DSJurisdiction, jurisdictionCd);

    	NBSContext.store(session, "DSObservationSharedInd", sharedInd);
    	NBSContext.store(session, "DSObservationLocalID", observationLocalUid);
    	NBSContext.store(session, "DSObservationUID", observationUid);
    	NBSContext.store(session, "DSObservationTypeCd",
    			NEDSSConstants.MORBIDITY_CODE);
    	
    	NBSContext.store(session, "DSPatientPersonUID", personParentUid);
    	NBSContext.store(session, NBSConstantUtil.DSPatientPersonLocalID,
    			personLocalId);
    	


    	if (contextAction.equalsIgnoreCase("ReturnToFileEvents")) {
    		NBSContext.store(session, NBSConstantUtil.DSFileTab, "3");
    	}

    	else if (contextAction.equalsIgnoreCase("ReturnToFileSummary") || contextAction.equalsIgnoreCase("ViewEventsPopup")) {
    		NBSContext.store(session, NBSConstantUtil.DSFileTab, "1");
    	}

    	else if (contextAction.equalsIgnoreCase("MarkAsReviewed")) {

    		try {


    			String processingDecision = (String)request.getParameter("markAsReviewReason");

    			String result = markAsReviewedHandler(observationUid, processingDecision,nbsSecurityObj,
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
    			return mapping.findForward("dataerror");
    		}
    	}

    	else if (contextAction
    			.equalsIgnoreCase(NBSConstantUtil.ClearMarkAsReviewed)) {
    		try {
    			String result = clearMarkAsReviewedHandler(observationUid,
    					nbsSecurityObj, session, request, response);

    			if (result.equals(NEDSSConstants.RECORD_STATUS_UNPROCESSED)) {
    				String successMsg = "The Morbidity Report has been successfully marked as Unreviewed";
    				logger.info(successMsg);
    				request.setAttribute("displayInformationExists", successMsg);
    			} else {
    				logger.info("The Morb Report was not able to be set to UnProcessed");
    			}
    		} catch (Exception e) {
    			logger.fatal("Error being raised ", e);
    			return mapping.findForward("dataerror");
    		}

    	}

    	else if (contextAction.equalsIgnoreCase("CreateInvestigation")) {

    		NBSContext.store(session, "DSInvestigationJurisdiction", jurisdictionCd);
    		NBSContext.store(session, "DSInvestigationCondition", observationCd);
    		NBSContext.store(session, "DSInvestigationProgramArea", progAreaCd);
    		NBSContext.store(session, "DSInvestigationCondition", observationCd);
    		NBSContext.store(session, "DSObservationTypeCd",
    				NEDSSConstants.MORBIDITY_CODE);
    		//NBSContext.store(session, "DSInvestigationCode", );
    		String processingDecision = (String)request.getParameter("markAsReviewReason");
    		FieldMappingBuilder mapBuilder = new FieldMappingBuilder();
    		TreeMap<Object,Object> loadTreeMap = mapBuilder.createMorbidityLoadTreeMap(
    				morbidityProxyVO,processingDecision);
    		NBSContext.store(session, "DSMorbMap", loadTreeMap);
    	}else if (contextAction.equalsIgnoreCase("CreateInvestigationFromMorbidity")){

    		CoinfectionSummaryVO conifectionSummaryVO = (CoinfectionSummaryVO)NBSContext.retrieve(session,NBSConstantUtil.DSCoinfectionInvSummVO);
    		try {
    			Long phcUid = null;
    			if(conifectionSummaryVO!=null){
    				phcUid = conifectionSummaryVO.getPublicHealthCaseUid();
    			}

    			createInvestigationFromMorbidityHandler(observationUid, phcUid,nbsSecurityObj,session,request, response);

    			String successMsg = "The Morbidity Report has been successfully associated with investigation";
    			request.setAttribute("displayInformationExists", successMsg);

    		}catch (Exception e) {
    			e.printStackTrace();
    			logger.error("Error in AssociateToInvestigationsLoad in storing new relations");
    			session.setAttribute("error", "Application Error while associating Investigations to an Observation");
    			return mapping.findForward(NEDSSConstants.ERROR_PAGE);
    		}

    	}
    	else if (contextAction.equalsIgnoreCase("ReturnToManageTreatments")) {

    	}
    	else if(contextAction.equalsIgnoreCase("ManageEvents"))
    	{
    		return mapping.findForward(contextAction);
    	}
    	else if (contextAction.equalsIgnoreCase(NBSConstantUtil.DELETE)) {
    		if (sCurrTask.endsWith("Morb3")) {
    			NBSContext.store(session, "DSFileTab", "3");
    		}
    		else if (sCurrTask.endsWith("Morb10")) {
    			NBSContext.store(session, NBSConstantUtil.DSFileTab, "1");
    		}

    		logger.debug("observationUID in Delete is :" + observationUid);
    		try {
    			String result = deleteHandler(observationUid, nbsSecurityObj, session,
    					request, response);

    			//##!!VOTester.createReport(form.getProxy(), "obs-delete-store-post");
    			if(result.equals("viewDelete") && sCurrTask.endsWith("Morb9"))
    			{
    				logger.debug("ObservationSubmit: viewDelete");

    				ActionForward af = mapping.findForward(contextAction);
    				ActionForward forwardNew = new ActionForward();
    				StringBuffer strURL = new StringBuffer(af.getPath());
    				strURL.append("?ContextAction=" + contextAction);
    				strURL.append("&method=loadQueue");
    				forwardNew.setPath(strURL.toString());
    				forwardNew.setRedirect(true);
    				return forwardNew;
    			}
    			else if (result.equals("viewDelete")&& !sCurrTask.endsWith("Morb9")) {
    				logger.debug("ObservationSubmit: viewDelete");

    				ActionForward af = mapping.findForward(contextAction);
    				ActionForward forwardNew = new ActionForward();
    				StringBuffer strURL = new StringBuffer(af.getPath());
    				if(strURL.indexOf(NEDSSConstants.ManageEvents)>0){
    					strURL.append("&ContextAction=" + contextAction);
    				}
    				else
    					strURL.append("?ContextAction=" + contextAction);
    				forwardNew.setPath(strURL.toString());
    				forwardNew.setRedirect(true);
    				return forwardNew;

    			}
    			else if (result.equals("deleteDenied")) {
    				logger.debug("ObservationSubmit: deleteDenied");
    				NBSContext.store(session, "DSObservationUID", observationUid);

    				ActionForward af = mapping.findForward("DeleteDenied");
    				ActionForward forwardNew = new ActionForward();
    				StringBuffer strURL = new StringBuffer(af.getPath());
    				strURL.append("?ContextAction=DeleteDenied");
    				forwardNew.setPath(strURL.toString());
    				forwardNew.setRedirect(true);
    				return forwardNew;
    			}
    		}
    		catch (Exception e) {
    			logger.fatal("Error being raised ", e);
    			return mapping.findForward("dataerror");
    		}
    	}
    	if (!contextAction.equalsIgnoreCase("ViewEventsPopup")) {
    		morbidityForm.reset();
    	}

    }catch (Exception e) {
    	logger.error("Exception in View Morb: " + e.getMessage());
    	e.printStackTrace();
    	throw new ServletException("Error occurred in View Morbidity : "+e.getMessage());
    }
    return (mapping.findForward(contextAction));
  }


  private String markAsReviewedHandler(Long observationUid, String processingDecision,
			NBSSecurityObj nbsSecurityObj, HttpSession session,
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
           observationUid, processingDecision, null};
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
  
  private String clearMarkAsReviewedHandler(Long observationUid, 
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
String sMethod="unProcessObservation";
Object[] oParams = new Object[]{observationUid};
logger.debug("ObservationSubmit: clearMarkAsReviewedHandler with observationID = " + observationUid);

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
			logger.debug("Clear Marked as Reviewed result and arg = "
					+ resultUIDArr.get(0));
  result = ( (Boolean) resultUIDArr.get(0)).booleanValue();
  logger.debug("The Clear Marked AS Reviewed result is:" + result);
  if (result) {
    markAsReviewedFlag = "UNPROCESSED";
			} else {
    markAsReviewedFlag = "PROCESSED";
  }
}
return markAsReviewedFlag;
}

  private String deleteHandler(Long UID, NBSSecurityObj nbsSecurityObj,
                              HttpSession session, HttpServletRequest request,
                              HttpServletResponse response) throws
     NEDSSAppConcurrentDataException, java.rmi.RemoteException,
     javax.ejb.EJBException, Exception {
   /**
    * Call the mainsessioncommand
    */
   MainSessionCommand msCommand = null;
   String deleteFlag = "";
   String sBeanJndiName = JNDINames.OBSERVATION_PROXY_EJB;
   String sMethod = "deleteMorbidityProxy";

   System.out.println("sending proxy to delete observation, and UID : " + UID);
   System.out.println("ObservationSubmit: deleteHandler with observationID = " +
                      UID);

   /**
    * Output ObservationProxyVO for debugging
    */Object[] oParams = {
       UID};
   MainSessionHolder holder = new MainSessionHolder();
   msCommand = holder.getMainSessionCommand(session);
   ArrayList<?> resultUIDArr = new ArrayList<Object> ();
   resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
   boolean result;
   if ( (resultUIDArr != null) && (resultUIDArr.size() > 0)) {
     logger.debug("delete observation worked!!! and arg = " +
                  resultUIDArr.get(0));
     result = ( (Boolean) resultUIDArr.get(0)).booleanValue();
     logger.debug("\n\n\n\n The result value is:" + result);
     if (result) {
       deleteFlag = "viewDelete";
     }
     else {
       deleteFlag = "deleteDenied";
     }
   }
   else {
     deleteFlag = "error";

   }
   return deleteFlag;
 }


  private void createInvestigationFromMorbidityHandler(Long observationUid, Long investigationUid,
			NBSSecurityObj nbsSecurityObj, HttpSession session,
			HttpServletRequest request, HttpServletResponse response)
			throws NEDSSAppConcurrentDataException, java.rmi.RemoteException,
		  javax.ejb.EJBException, Exception {
		
	    Collection<Object>  addedRelationsCollection= new ArrayList<Object> ();
	    Collection<Object>  deletedRelationsCollection= new ArrayList<Object> ();
	    
	    addedRelationsCollection.add(investigationUid);

		ObservationUtil obsUtil = new ObservationUtil();
		
		MainSessionHolder mainSessionHolder = new MainSessionHolder();
		MainSessionCommand msCommand = mainSessionHolder.getMainSessionCommand(session);
		
		String sBeanJndiName = JNDINames.OBSERVATION_PROXY_EJB;
		String sMethod="setAssociatedInvestigations";
		Object[] oParms = new Object[] {observationUid, NEDSSConstants.DISPLAY_FORM, null, addedRelationsCollection, deletedRelationsCollection, null};		

		msCommand.processRequest(sBeanJndiName, sMethod, oParms);

  }

}