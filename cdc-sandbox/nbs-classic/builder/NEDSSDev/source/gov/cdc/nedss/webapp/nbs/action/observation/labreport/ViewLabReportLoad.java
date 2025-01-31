package gov.cdc.nedss.webapp.nbs.action.observation.labreport;

import gov.cdc.nedss.act.observation.dt.ObservationDT;
import gov.cdc.nedss.act.observation.vo.ObservationVO;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.LabResultProxyVO;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.nbssecurity.User;
import gov.cdc.nedss.systemservice.nbssecurity.UserProfile;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.action.observation.common.AddCommentUtil;
import gov.cdc.nedss.webapp.nbs.action.person.util.PersonUtil;
import gov.cdc.nedss.webapp.nbs.action.util.CommonAction;
import gov.cdc.nedss.webapp.nbs.action.util.DSQueueObject;
import gov.cdc.nedss.webapp.nbs.action.util.ErrorMessageHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Title:        ViewLabReportLoad.java
 * Description:	This is a action class for the struts implementation to view lab
 * Copyright:	Copyright (c) 2001
 * Company: 	Computer Sciences Corporation
 * @author	Pradeep Kumar Sharma
 * @version	1.0
 */

public class ViewLabReportLoad
    extends CommonLabUtil
{
  static final LogUtils logger = new LogUtils(ViewLabReportLoad.class.getName());

  public ActionForward execute(ActionMapping mapping, ActionForm aForm,
                               HttpServletRequest request,
                               HttpServletResponse response) throws IOException,
      ServletException {

    HttpSession session = request.getSession();
    logger.info("ViewLabReportLoad is being called");

    if (session == null) {
      logger.fatal("error no session");
      return mapping.findForward("login");
    }
    
    try {
    	String contextAction = null;
    	contextAction = request.getParameter("ContextAction");
    	String sCurrTask = null;
    	TreeMap<Object,Object> tm = new TreeMap<Object,Object>();
    	if (contextAction == null)
    		contextAction = (String) request.getAttribute("ContextAction");

    	//setting PageContext to PS018 ViewObservationLab
    	if (!contextAction.equalsIgnoreCase("DeleteDenied")){

    		tm = NBSContext.getPageContext(session, "PS018", contextAction);
    		sCurrTask = NBSContext.getCurrentTask(session);

    		ErrorMessageHelper.setErrMsgToRequest(request, "PS018");
    	}
    	else{//setting Page Contex for Delete Denied
    		tm  = NBSContext.getPageContext(session, "PS032", contextAction);
    		sCurrTask = NBSContext.getCurrentTask(session);
    		request.setAttribute("linkName", "Return to View Observation");
    		request.setAttribute("linkValue",
    				"/nbs/" + sCurrTask + ".do?ContextAction=" +
    						tm.get("ReturnToViewObservationLab"));
    		return mapping.findForward("XSP");

    	}
    	try{
    		if(NBSContext.retrieve(session,"DSQueueObject")!=null){
    			String queueType = ((DSQueueObject)NBSContext.retrieve(session,"DSQueueObject")).getDSQueueType();
    			if(queueType!=null && queueType.equals(NEDSSConstants.NEW_LAB_REPORTS_FOR_REVIEW))
    			{
    				request.setAttribute("linkName1", "Return to Documents Requiring Review");
    				request.setAttribute("linkValue1",
    						"/nbs/" + sCurrTask + ".do?ContextAction=" +
    								tm.get("ReturnToObservationNeedingReview"));
    			}
    			else if(queueType!=null && queueType.equals(NEDSSConstants.OBSERVATIONS_ASSIGNMENT))
    			{
    				request.setAttribute("linkName1", "Return to Documents Requiring Security Assignment");
    				request.setAttribute("linkValue1",
    						"/nbs/" + sCurrTask + ".do?ContextAction=" +
    								tm.get("ReturnToObservationsNeedingAssignment"));
    			}
    		}
    	}
    	catch(NullPointerException ex){
    		//Let it go as if object is not in the context don't show the link
    	}


    	NBSSecurityObj secObj = (NBSSecurityObj) session.getAttribute( "NBSSecurityObject");
    	logger.info("\n\n contextAction in  ViewLabReportLoad:" + contextAction);


    	Object objObsUID = NBSContext.retrieve(session,
    			"DSObservationUID");
    	Long obsUID = null;

    	objObsUID = NBSContext.retrieve(session,
    			"DSObservationUID");
    	if (objObsUID instanceof String) {
    		obsUID = new Long((String) NBSContext.retrieve(session, "DSObservationUID"));
    	}
    	else if (objObsUID instanceof Long) {
    		obsUID =  (Long)objObsUID;
    	}



    	logger.info("ViewLabReportLoad observationUID:" + obsUID);



    	LabResultProxyVO labResultProxyVO = getLabResultProxyVO(obsUID,
    			request.getSession());
    	//lab report might be assoc. to investigation that might have an exiting notification on it...must do check
    	if (labResultProxyVO.getAssociatedNotificationInd()) {
    		request.setAttribute("NotificationExists", "true");
    	}
    	else {
    		request.setAttribute("NotificationExists", "false");

    	}

    	request.setAttribute("labResultProxyVO",labResultProxyVO);
    	Long lab112UID = null;
    	ObservationVO obs112 = convertProxyToObs112(labResultProxyVO, request);
    	convertProxyToRequest(labResultProxyVO, request, secObj);
    	convertBatchObservationsToRequest(obs112, labResultProxyVO, request, secObj);
    	lab112UID = obs112.getTheObservationDT().getObservationUid();
    	//retrieve the patient's localId

    	PersonVO perVO = super.getPersonVO(NEDSSConstants.PAR110_TYP_CD, labResultProxyVO, obsUID);
    	String personLocalId = perVO.getThePersonDT().getLocalId();
    	session.setAttribute("DSPatientPersonLocalID", personLocalId);
    	//gst - also put MPR and PersonDT in request if present for AssociateToInvestigations
    	Long mprUID = null;
    	mprUID = perVO.getThePersonDT().getPersonParentUid();
    	if (mprUID != null)
    		NBSContext.store(session,"DSPatientPersonUID",mprUID);
    	if ( (sCurrTask.endsWith("Lab2")) || (sCurrTask.endsWith("Lab3")) || (sCurrTask.endsWith("Lab10")) )
    		NBSContext.store(session,"DSPatientPersonVO",perVO); //needed by Assoc Inv

    	/*retrieve the main ObservationDT related to the Lab by using a TreeMap<Object,Object> which will sort ascendingly
      the DT's according to obervationUid ...the lowest observationUid is the correct one to use*/
    	TreeMap<Object,Object> observationTree = new TreeMap<Object,Object>();
    	ObservationDT obsDTTemp = new ObservationDT();
    	List<ObservationVO> obsColl = (ArrayList<ObservationVO> ) labResultProxyVO.getTheObservationVOCollection();
    	for (Iterator<ObservationVO> iter = obsColl.iterator(); iter.hasNext(); ) {
    		ObservationVO obsVO = (ObservationVO) iter.next();
    		obsDTTemp = obsVO.getTheObservationDT();
    		//System.out.println("ObservationLocalID=" + obsDTTemp.getLocalId() + " ObservationUid = " + obsDTTemp.getObservationUid());
    		observationTree.put(obsDTTemp.getObservationUid(), obsDTTemp);
    	}

    	Long obsUid = (Long) observationTree.firstKey();
    	//System.out.println("**********This is the observationUid retrieved from the TreeMap<Object,Object> " + obsUid.toString() + "*************");
    	ObservationDT obsDT = (ObservationDT)observationTree.get(obsUid);

    	String progAreaCd = obsDT.getProgAreaCd();
    	String jurisdictionCd = obsDT.getJurisdictionCd();
    	String sharedInd = obsDT.getSharedInd();
    	String observationLocalUID = obsDT.getLocalId();
    	//gst - get the program area/condition list for the Associate to Investigations disposition logic
    	ArrayList<String> conditionList = labResultProxyVO.getTheConditionsList();
    	if (conditionList != null) 
    		NBSContext.store(session, "DSConditionList",conditionList);
    	if ((progAreaCd != null) && !progAreaCd.isEmpty()) 
    		NBSContext.store(session, "DSProgramArea",progAreaCd); 	
    	CommonAction ca = new CommonAction();
    	request.setAttribute("PDLogic", ca.checkIfSyphilisIsInConditionList(progAreaCd, conditionList, NEDSSConstants.LAB_REPORT));
    	request.setAttribute("PDLogicCreateInv", ca.checkIfSyphilisIsInConditionListForCreateInv(progAreaCd, conditionList, NEDSSConstants.LAB_REPORT));
    	boolean isProcessed = false;

    	if (obsDT.getRecordStatusCd()!=null && (obsDT.getRecordStatusCd().equalsIgnoreCase(NEDSSConstants.OBS_PROCESSED))){
    		isProcessed = true;
    	}



    	boolean isElecIndYes = false;
    	boolean  isElecIndY = false;
    	boolean isElecIndNo = false;
    	boolean viewPerm = false;
    	boolean deletePerm = false;
    	boolean deleteExPerm = false;
    	boolean editPerm = false;
    	boolean addInvestPerm = false;
    	boolean viewWorkUpPerm = false;
    	boolean transferPerm = false;
    	boolean markAsReviewed = false;
    	boolean clearMarkAsReviewed = false;


    	UserProfile userprofile = secObj.getTheUserProfile();
    	User user = userprofile.getTheUser();
    	String userType = user.getUserType();

    	boolean strReportExteranlUser = userType.equalsIgnoreCase(
    			NEDSSConstants.SEC_USERTYPE_EXTERNAL);
    	if (userType!=null && strReportExteranlUser){
    		request.setAttribute("user",  NEDSSConstants.SEC_USERTYPE_EXTERNAL);
    	}
    	else
    		request.setAttribute("user", "internalUser");
    	/**
    	 * When you view it from ObsNeedingAssignment the progAreaCd and jurisdictionCd are null
    	 */
    	if(sCurrTask.startsWith("ViewObservationLab9"))
    	{
    		if(progAreaCd != null && jurisdictionCd == null)
    		{
    			viewPerm = secObj.getPermission(NBSBOLookup.OBSERVATIONLABREPORT,
    					NBSOperationLookup.VIEW, progAreaCd,
    					"ANY", sharedInd);
    			transferPerm = secObj.getPermission(NBSBOLookup.OBSERVATIONLABREPORT,
    					NBSOperationLookup.
    					TRANSFERPERMISSIONS,
    					progAreaCd,
    					NBSOperationLookup.ANY);
    		}

    		else if (jurisdictionCd != null && progAreaCd == null)
    		{
    			viewPerm = secObj.getPermission(NBSBOLookup.OBSERVATIONLABREPORT,
    					NBSOperationLookup.VIEW, "ANY",
    					jurisdictionCd, sharedInd);
    			transferPerm = secObj.getPermission(NBSBOLookup.OBSERVATIONLABREPORT,
    					NBSOperationLookup.
    					TRANSFERPERMISSIONS,
    					NBSOperationLookup.ANY,
    					jurisdictionCd);

    		}
    		else if(jurisdictionCd == null && progAreaCd == null)
    		{
    			viewPerm = secObj.getPermission(NBSBOLookup.OBSERVATIONLABREPORT,
    					NBSOperationLookup.VIEW, NBSOperationLookup.ANY,
    					NBSOperationLookup.ANY, sharedInd);
    			transferPerm = secObj.getPermission(NBSBOLookup.OBSERVATIONLABREPORT,
    					NBSOperationLookup.
    					TRANSFERPERMISSIONS,
    					NBSOperationLookup.ANY,
    					NBSOperationLookup.ANY);


    		}

    	}
    	else
    	{
    		viewPerm = secObj.getPermission(NBSBOLookup.OBSERVATIONLABREPORT,
    				NBSOperationLookup.VIEW,
    				progAreaCd,
    				jurisdictionCd, sharedInd);

    		deletePerm = secObj.getPermission(NBSBOLookup.
    				OBSERVATIONLABREPORT,
    				NBSOperationLookup.DELETE,
    				progAreaCd,
    				jurisdictionCd, sharedInd);

    		deleteExPerm = secObj.getPermission(NBSBOLookup.
    				OBSERVATIONLABREPORT,
    				NBSOperationLookup.DELETEEXTERNAL,
    				progAreaCd,
    				jurisdictionCd, sharedInd);

    		editPerm = secObj.getPermission(NBSBOLookup.
    				OBSERVATIONLABREPORT,
    				NBSOperationLookup.EDIT,
    				progAreaCd,
    				jurisdictionCd, sharedInd);

    		addInvestPerm = secObj.getPermission(NBSBOLookup.INVESTIGATION,
    				NBSOperationLookup.ADD,
    				progAreaCd,
    				jurisdictionCd);

    		viewWorkUpPerm = secObj.getPermission(NBSBOLookup.PATIENT,
    				NBSOperationLookup.VIEWWORKUP,
    				progAreaCd,
    				NBSOperationLookup.ANY);

    		markAsReviewed = secObj.getPermission(NBSBOLookup.OBSERVATIONLABREPORT,
    				NBSOperationLookup.MARKREVIEWED,
    				progAreaCd,
    				jurisdictionCd, sharedInd);

    		transferPerm = secObj.getPermission(NBSBOLookup.
    				OBSERVATIONLABREPORT,
    				NBSOperationLookup.
    				TRANSFERPERMISSIONS,
    				progAreaCd,
    				jurisdictionCd);
    		if (progAreaCd!=null && PropertyUtil.isStdOrHivProgramArea(progAreaCd)&& markAsReviewed
    				&& !labResultProxyVO.isAssociatedInvInd()
    				&& obs112.getTheObservationDT().getProcessingDecisionCd() != null
    				&& !obs112.getTheObservationDT().getProcessingDecisionCd()
    				.trim().equals(""))
    			clearMarkAsReviewed = true;
    	}

    	if (!viewPerm)
    	{
    		session.setAttribute("error",
    				"User does not have View Observation Lab Report Permission.");
    		return mapping.findForward("error");

    		//      throw new ServletException("User does not have View Observation Lab Report Permission.");
    	}

    	if(obsDT.getElectronicInd() != null && obsDT.getElectronicInd().equalsIgnoreCase("N"))
    	{
    		isElecIndNo = true;
    	}
    	else if (obsDT.getElectronicInd() != null && obsDT.getElectronicInd().equalsIgnoreCase("E")){
    		isElecIndYes = true;
    		request.setAttribute("electronicInd", "Y");
    		request.setAttribute("ELRelectronicInd", "E");
    	}
    	else if(obsDT.getElectronicInd() != null && obsDT.getElectronicInd().equalsIgnoreCase("Y"))
    	{  isElecIndY = true;
    	request.setAttribute("ELRelectronicInd", "Y");
    	}




    	//buttons that a ViewObservationLab may have based on NBSContext
    	boolean hasMarkAsReviewed = false;
    	boolean hasTransferOwnership = false;
    	boolean hasDelete = false;
    	boolean hasEdit = false;
    	boolean hasCreateInvestigation = false;
    	boolean hasAddComment = false;
    	boolean hasClearMarkAsReviewed = false;

    	//setting PageContext to PS018 ViewObservationLab
    	/*  tm = NBSContext.getPageContext(session, "PS018", contextAction);
    String sCurrTask = NBSContext.getCurrentTask(session);
    sCurrTask = NBSContext.getCurrentTask(session);
    ErrorMessageHelper.setErrMsgToRequest(request, "PS018"); */

    	if (contextAction.equalsIgnoreCase("ObservationLabIDOnEvents") ||
    			contextAction.equalsIgnoreCase("ObservationLabIDOnSummary") ||
    			contextAction.equalsIgnoreCase(NBSConstantUtil.ObservationLabID) ||
    			contextAction.equalsIgnoreCase(NBSConstantUtil.MarkAsReviewed) ||
    			contextAction.equalsIgnoreCase(NBSConstantUtil.ClearMarkAsReviewed) ||
    			contextAction.equalsIgnoreCase(NBSConstantUtil.AddCommentLab) ||
    			contextAction.equalsIgnoreCase(NBSConstantUtil.SUBMIT) ||
    			contextAction.equalsIgnoreCase(NBSConstantUtil.CANCEL) ||
    			contextAction.equalsIgnoreCase("ReturnToViewObservationLab") ||
    			contextAction.equalsIgnoreCase("ViewLab") ||
    			contextAction.equalsIgnoreCase("DeleteDenied")||
    			contextAction.equalsIgnoreCase("ViewObservationLab")
    			) {

    		//System.out.println("****sCurrTak is: " + sCurrTask + " ***********");
    		logger.info("sCurrTask in ViewLabLoad: " + sCurrTask);
    		//ViewObservationLab 2 & ViewObservationLab3 from ViewFile Summary & Events tab
    		if (sCurrTask.endsWith("Lab2") || sCurrTask.endsWith("Lab3")) {
    			if (sCurrTask.endsWith("Lab2")) {
    				request.setAttribute("linkName", "View File");
    				request.setAttribute("linkValue",
    						"/nbs/" + sCurrTask + ".do?ContextAction=" +
    								tm.get("ReturnToFileSummary"));
    			}
    			else if (sCurrTask.endsWith("Lab3")) {
    				request.setAttribute("linkName", "Return to File: Events");
    				request.setAttribute("linkValue",
    						"/nbs/" + sCurrTask + ".do?ContextAction=" +
    								tm.get("ReturnToFileEvents"));
    			}
    			hasEdit = true;
    			hasMarkAsReviewed = true;
    			hasClearMarkAsReviewed = true;
    			hasTransferOwnership = true;
    			hasDelete = true;
    			hasCreateInvestigation = true;
    			hasAddComment = true;

    		}
    		//ViewObservationLab 1,5,7 accessed from ViewInvestigation
    		else if (sCurrTask.endsWith("Lab1") || sCurrTask.endsWith("Lab5") ||
    				sCurrTask.endsWith("Lab7") || sCurrTask.endsWith("Lab14") ||
    				sCurrTask.endsWith("Lab15") || sCurrTask.endsWith("Lab16")) {
    			request.setAttribute("linkName", "Return to View Investigation");
    			request.setAttribute("linkValue",
    					"/nbs/" + sCurrTask + ".do?ContextAction=" +
    							tm.get("ReturnToViewInvestigation"));

    			hasEdit = true;
    			hasTransferOwnership = true;
    			hasAddComment = true;
    			hasMarkAsReviewed = true;
    			hasClearMarkAsReviewed = true;

    		}
    		//ViewObservationLab 4,6,8 accessed from ManageObservation
    		else if (sCurrTask.endsWith("Lab4") || sCurrTask.endsWith("Lab6") ||
    				sCurrTask.endsWith("Lab8") || sCurrTask.endsWith("Lab11")
    				|| sCurrTask.endsWith("Lab12") || sCurrTask.endsWith("Lab13")) {
    			request.setAttribute("linkName", "Return to Manage Associations");
    			request.setAttribute("linkValue",
    					"/nbs/" + sCurrTask + ".do?ContextAction=" +
    							tm.get("ManageEvents"));
    			hasEdit = true;
    			hasMarkAsReviewed = true;
    			hasClearMarkAsReviewed = true;
    			hasTransferOwnership = true;
    			hasDelete = true;
    			hasCreateInvestigation = true;
    			hasAddComment = true;
    		}
    		//ViewObservationLab9
    		else if (sCurrTask.endsWith("Lab9")) {
    			hasTransferOwnership = true;
    			hasAddComment = true;
    		}
    		//ViewObservationLab10
    		else if (sCurrTask.endsWith("Lab10")) {
    			hasEdit = true;
    			hasDelete = true;
    			hasCreateInvestigation = true;
    			hasAddComment = true;
    			hasMarkAsReviewed = true;
    			hasClearMarkAsReviewed = true;
    			hasTransferOwnership = true;

    			request.setAttribute("linkName", "View File");
    			String viewFileLink = "/nbs/" + sCurrTask + ".do?ContextAction=" + tm.get("ReturnToFileSummary");
    			request.setAttribute("linkValue", viewFileLink);

    			if (contextAction.equals("ViewLab") || contextAction.equals(NBSConstantUtil.CANCEL)) {
    				request.setAttribute("linkNameViewEvents", "View Events");
    				String viewEventsLink = "/nbs/" + sCurrTask + ".do?ContextAction=ViewEventsPopup";        
    				request.setAttribute("linkValueViewEvents",
    						"javascript:function popupViewEvents(){window.open('"+viewEventsLink+"');};popupViewEvents();");
    			}

    		}
    		else if (contextAction.equalsIgnoreCase("DeleteDenied")){
    			TreeMap<Object,Object> deleteDeniedTm  = NBSContext.getPageContext(session, "PS032", contextAction);
    			request.setAttribute("linkName", "Return to View Observation");
    			request.setAttribute("linkValue",
    					"/nbs/" + sCurrTask + ".do?ContextAction=" +
    							deleteDeniedTm.get("ReturnToViewObservationLab"));
    			//"ReturnToViewObservationLab" + "&observationUID=" + obsUID);

    		}
    		/*else if (sCurrTask.endsWith("DeleteDenied1")||sCurrTask.endsWith("DeleteDenied2") || sCurrTask.endsWith("DeleteDenied3") || sCurrTask.endsWith("DeleteDenied4") || sCurrTask.endsWith("DeleteDenied5")){


      }*/

    		request.setAttribute("editButtonHref",
    				"/nbs/" + sCurrTask + ".do?ContextAction=" +
    						tm.get(NBSConstantUtil.EDIT));
    		request.setAttribute("markAsReviewButtonHref",
    				"/nbs/" + sCurrTask + ".do?ContextAction=" +
    						tm.get(NBSConstantUtil.MarkAsReviewed));
    		request.setAttribute("clearMarkAsReviewButtonHref",
    				"/nbs/" + sCurrTask + ".do?ContextAction=" +
    						tm.get(NBSConstantUtil.ClearMarkAsReviewed));
    		request.setAttribute("transferOwnership",
    				"/nbs/" + sCurrTask + ".do?ContextAction=" +
    						tm.get(NBSConstantUtil.TransferOwnership));

    		request.setAttribute("deleteButtonHref",
    				"/nbs/" + sCurrTask + ".do?ContextAction=" +
    						tm.get(NBSConstantUtil.DELETE));

    		request.setAttribute("creatInvestigationButtonHref",
    				"/nbs/" + sCurrTask + ".do?ContextAction=" +
    						tm.get(NBSConstantUtil.CreateInvestigation));
    		request.setAttribute("associateToInvestigationsButtonHref",
    				"/nbs/" + sCurrTask + ".do?ContextAction=" +
    						tm.get(NBSConstantUtil.AssociateToInvestigations)+ "&initLoad=true");
    		request.setAttribute("addCommentButtonHref",
    				"/nbs/" + sCurrTask + ".do?ContextAction=" +
    						tm.get(NBSConstantUtil.AddCommentLab));

    		request.setAttribute("PrintPage", "/nbs/LoadViewObservationLab1.do?ContextAction=PrintPage");

    		//setting button values


    		//setting button values
    		//Edit Button
    		if (hasEdit && editPerm && isElecIndNo) {
    			request.setAttribute("checkEdit", "true");
    		}
    		else{
    			request.setAttribute("checkEdit", "false");
    		}
    		//MarkAsReviewed Button
    		if (hasMarkAsReviewed && markAsReviewed && !isProcessed) {
    			request.setAttribute("checkMarkAsReview", "true");
    		}
    		else{
    			request.setAttribute("checkMarkAsReview", "false");
    		}

    		if (hasClearMarkAsReviewed && clearMarkAsReviewed) {
    			request.setAttribute("checkClearMarkAsReview", "true");
    		}
    		else{
    			request.setAttribute("checkClearMarkAsReview", "false");
    		}

    		//TransferOwnership Button
    		if (hasTransferOwnership && transferPerm) {
    			request.setAttribute("checkTransfer", "true");
    		}
    		else{
    			request.setAttribute("checkTransfer", "false");
    		}

    		//Delete Button
    		if (hasDelete &&
    				( (deletePerm && isElecIndNo) || (deleteExPerm && isElecIndY) || (deleteExPerm && isElecIndYes))) {
    			request.setAttribute("checkDelete", "true");
    		}
    		else{
    			request.setAttribute("checkDelete", "false");
    		}
    		//Create Investigation Button
    		if (hasCreateInvestigation && addInvestPerm) {
    			request.setAttribute("checkCreateInvestigation", "true");
    		}
    		else{
    			request.setAttribute("checkCreateInvestigation", "false");
    		}
    		//Add Comment button
    		if (hasAddComment && isElecIndYes) {
    			request.setAttribute("checkAddComment", "true");
    		}
    		else{
    			request.setAttribute("checkAddComment", "false");
    		}

    	}
    	request.setAttribute("personLocalID", PersonUtil.getDisplayLocalID(personLocalId));
    	request.setAttribute("observationLocalUID", observationLocalUID);

    	this.setAddUserComments(labResultProxyVO,request, secObj);

    	/**sets the ProgramArea and Jurisdiction to request from security object**/
    	super.getNBSSecurityJurisdictionsPA(request, secObj, contextAction);
    	
    	 /**
         * @TBD Release 6.0, Commented out as LDF will be planned out as new type of answers
      	loadLabLDFView(labResultProxyVO ,lab112UID, request);
		*/
    	if(contextAction.equalsIgnoreCase("PrintPage")) {
    		return new ActionForward("/observation/labreport/labreport_print");
    	}

    }catch (Exception e) {
    	logger.error("Exception in View Lab Report Load: " + e.getMessage());
    	e.printStackTrace();
    	throw new ServletException("An error occurred in View Lab Report Load : "+e.getMessage());
    }      
     return mapping.findForward("XSP");

  }

 private void  setAddUserComments(LabResultProxyVO lrProxyVO, HttpServletRequest request, NBSSecurityObj nbsObj){
   AddCommentUtil acu = new AddCommentUtil();
   acu.setAddUserComments(lrProxyVO,request,nbsObj);
 }


}
