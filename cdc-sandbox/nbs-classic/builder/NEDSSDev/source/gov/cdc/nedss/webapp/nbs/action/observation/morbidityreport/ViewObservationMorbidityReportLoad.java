package gov.cdc.nedss.webapp.nbs.action.observation.morbidityreport;

import gov.cdc.nedss.systemservice.nbscontext.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.webapp.nbs.action.util.CommonAction;
import gov.cdc.nedss.webapp.nbs.action.util.DSQueueObject;
import gov.cdc.nedss.webapp.nbs.action.util.ErrorMessageHelper;
import gov.cdc.nedss.entity.person.vo.*;
import gov.cdc.nedss.entity.person.dt.*;
import gov.cdc.nedss.entity.entityid.dt.*;
import gov.cdc.nedss.locator.dt.*;
import gov.cdc.nedss.webapp.nbs.action.util.*;
import gov.cdc.nedss.webapp.nbs.action.person.util.*;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.MorbidityProxyVO;
import gov.cdc.nedss.act.observation.dt.*;
import gov.cdc.nedss.act.observation.vo.*;
import gov.cdc.nedss.webapp.nbs.action.observation.morbidityreport.util.*;
import gov.cdc.nedss.webapp.nbs.form.morbidity.MorbidityForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.webapp.nbs.action.observation.common.AddCommentUtil;
import gov.cdc.nedss.webapp.nbs.action.observation.labreport.*;
import gov.cdc.nedss.entity.organization.vo.*;
import gov.cdc.nedss.association.dt.*;

import java.util.*;
import java.io.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;
import gov.cdc.nedss.webapp.nbs.action.observation.labreport.CommonLabUtil;
import gov.cdc.nedss.webapp.nbs.servlet.NedssCodeLookupServlet;



/**
 * Title:        ViewObservationMorbidityReportLoad.java
 * Description:	This is a action class for the structs implementation
 * Copyright:	Copyright (c) 2001
 * Company: 	Computer Sciences Corporation
 * @author	NEDSS Development Team
 * @version	1.0
 */

public class ViewObservationMorbidityReportLoad
    extends CommonAction
{

  //For logging
  static final LogUtils logger = new LogUtils(
      ViewObservationMorbidityReportLoad.class.
      getName());

  public ActionForward execute(ActionMapping mapping, ActionForm aForm,
                               HttpServletRequest request,
                               HttpServletResponse response) throws IOException,
      ServletException
  {
	  try {
		  HttpSession session = request.getSession();

		  String contextAction = request.getParameter("ContextAction");

		  NBSSecurityObj secObj = (NBSSecurityObj) session.getAttribute(
				  "NBSSecurityObject");
		  String sCurrTask = "";
		  MorbidityUtil morbidityUtil = new MorbidityUtil();
		  TreeMap<Object,Object> tm = null;
		  this.setSTDandHIVPAsToRequest(request);
	NBSObjectStore NBSObjectStore = (NBSObjectStore)session.getAttribute(
			 NBSConstantUtil.OBJECT_STORE);
	NBSObjectStore.remove(NBSConstantUtil.DSInvestigationType);
	NBSObjectStore.remove(NBSConstantUtil.DSCoinfectionInvSummVO);
		  if (!contextAction.equalsIgnoreCase("DeleteDenied"))
		  {
			  tm = NBSContext.getPageContext(session, "PS137", contextAction);
			  sCurrTask = NBSContext.getCurrentTask(session);
		  }

		  else
		  { //setting page context to delete denied page
			  tm = NBSContext.getPageContext(session, "PS032", contextAction);
			  sCurrTask = NBSContext.getCurrentTask(session);
			  request.setAttribute("linkName", "Return To Observation");
			  request.setAttribute("linkValue",
					  "/nbs/" + sCurrTask + ".do?ContextAction=" +
							  tm.get("ReturnToViewObservationMorb"));
			  return (mapping.findForward("XSP"));
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

		  request.setAttribute("currentTask", sCurrTask);

		  //Long observationUid = (Long) session.getAttribute("DSObservationUID");
		  //if (observationUid==null){
		  Long observationUid = null;
		  Object obj = NBSContext.retrieve(session, "DSObservationUID");

		  try
		  {
			  if (obj instanceof String)
			  {
				  observationUid = new Long( (String) NBSContext.retrieve(session,
						  "DSObservationUID"));
			  }
			  else if (obj instanceof Long)
			  {
				  observationUid = (Long) NBSContext.retrieve(session,
						  "DSObservationUID");
			  }

		  }
		  catch (NullPointerException ne)
		  {
			  logger.fatal("Can not retrieve DSObservationUID from Object Store.");
			  ErrorMessageHelper.setErrMsgToRequest(request, "PS137");
		  }
		  //}

		  MorbidityProxyVO morbidityProxyVO = null;
		  ObservationVO morbReportVO = null;
		  ObservationDT morbReportDT = null;
		  MorbidityForm morbForm = null;

		  String jurisdictionValues = new CachedDropDownValues().getJurisdictionCodedSortedValues();
		  jurisdictionValues = "NONE$Unknown|" + jurisdictionValues;
		  request.setAttribute("jurisdictionCodedSortedValues", jurisdictionValues);

		  if (aForm instanceof MorbidityForm)
		  {
			  morbForm = (MorbidityForm) aForm;
			  morbForm.reset();

			  try
			  {
				  morbidityProxyVO = morbidityUtil.getProxyFromEJB(observationUid,
						  session);

				  //morb report might be assoc. to investigation that might have an exiting notification on it...must do check
				  if (morbidityProxyVO.getAssociatedNotificationInd()) {
					  request.setAttribute("NotificationExists", "true");
				  }
				  else {
					  request.setAttribute("NotificationExists", "false");

				  }

				  morbidityUtil.loadToTheForm(morbForm, morbidityProxyVO);
				  morbReportVO = morbidityUtil.getMorbReportObsVO(morbidityProxyVO);
				  morbReportDT = morbReportVO.getTheObservationDT();
				  NBSContext.store(session, "DSObservationLocalID",
						  morbReportVO.getTheObservationDT().getLocalId());
				  ErrorMessageHelper.setErrMsgToRequest(request, "PS137");
				  if(morbReportDT.getProcessingDecisionCd()!=null && ! morbReportDT.getProcessingDecisionCd().trim().equals("")){
					  request.setAttribute("markAsReviewReasonDesc",
							  CachedDropDowns.getCodeDescTxtForCd(morbReportDT.getProcessingDecisionCd(),
									  NEDSSConstants.STD_PROCESSING_DECISION_LIST_SYPHILIS_AND_NONSYPHILIS));
					  request.setAttribute("markAsReviewReason", morbReportDT.getProcessingDecisionCd());
				  }
			  }
			  catch (Exception e)
			  {
				  logger.fatal("Failed to getProxyFromEJB");

			  }

			  Collection<ObservationVO>  observationVOcoll = morbidityProxyVO.
					  getTheObservationVOCollection();

			  String progAreaCd = morbReportDT.getProgAreaCd();
			  String jurisdictionCd = morbReportDT.getJurisdictionCd();
			  String sharedInd = morbReportDT.getSharedInd();
			  String observationLocalUid = morbReportDT.getLocalId();

			  boolean isProcessed = morbReportDT.getRecordStatusCd().equalsIgnoreCase(
					  NEDSSConstants.RECORD_STATUS_PROCESSED);

			  boolean check10 = false;
			  boolean check1 = false;
			  boolean check8 = false;
			  boolean check2 = false;
			  boolean check3 = false;
			  boolean check4 = false;
			  boolean check5 = false;
			  boolean check6 = false;
			  boolean check7 = false;
			  boolean clearMarkAsReviewed = false;

			  if (sCurrTask.startsWith("ViewObservationMorb8"))
			  {
				  if (progAreaCd != null && jurisdictionCd == null)
				  {
					  check1 = secObj.getPermission(NBSBOLookup.OBSERVATIONMORBIDITYREPORT,
							  NBSOperationLookup.VIEW, progAreaCd,
							  "ANY", sharedInd);
					  check8 = secObj.getPermission(NBSBOLookup.OBSERVATIONMORBIDITYREPORT,
							  NBSOperationLookup.
							  TRANSFERPERMISSIONS,
							  progAreaCd,
							  NBSOperationLookup.ANY);
				  }

				  else if (jurisdictionCd != null && progAreaCd == null)
				  {
					  check1 = secObj.getPermission(NBSBOLookup.OBSERVATIONMORBIDITYREPORT,
							  NBSOperationLookup.VIEW, "ANY",
							  jurisdictionCd, sharedInd);
					  check8 = secObj.getPermission(NBSBOLookup.OBSERVATIONMORBIDITYREPORT,
							  NBSOperationLookup.
							  TRANSFERPERMISSIONS,
							  NBSOperationLookup.ANY,
							  jurisdictionCd);

				  }
				  else if (jurisdictionCd == null && progAreaCd == null)
				  {
					  check1 = secObj.getPermission(NBSBOLookup.OBSERVATIONMORBIDITYREPORT,
							  NBSOperationLookup.VIEW,
							  NBSOperationLookup.ANY,
							  NBSOperationLookup.ANY, sharedInd);
					  check8 = secObj.getPermission(NBSBOLookup.OBSERVATIONMORBIDITYREPORT,
							  NBSOperationLookup.
							  TRANSFERPERMISSIONS,
							  NBSOperationLookup.ANY,
							  NBSOperationLookup.ANY);

				  }

			  }
			  else
			  {

				  check1 = secObj.getPermission(NBSBOLookup.
						  OBSERVATIONMORBIDITYREPORT,
						  NBSOperationLookup.VIEW,
						  progAreaCd,
						  jurisdictionCd, sharedInd);
				  check8 = secObj.getPermission(NBSBOLookup.
						  OBSERVATIONMORBIDITYREPORT,
						  NBSOperationLookup.
						  TRANSFERPERMISSIONS,
						  progAreaCd,
						  jurisdictionCd);
				  check2 = secObj.getPermission(NBSBOLookup.
						  OBSERVATIONMORBIDITYREPORT,
						  NBSOperationLookup.DELETE,
						  progAreaCd,
						  jurisdictionCd, sharedInd);

				  check3 = secObj.getPermission(NBSBOLookup.
						  OBSERVATIONMORBIDITYREPORT,
						  NBSOperationLookup.DELETEEXTERNAL,
						  progAreaCd,
						  jurisdictionCd, sharedInd);

				  check4 = secObj.getPermission(NBSBOLookup.
						  OBSERVATIONMORBIDITYREPORT,
						  NBSOperationLookup.EDIT,
						  progAreaCd,
						  jurisdictionCd, sharedInd);

				  check5 = secObj.getPermission(NBSBOLookup.INVESTIGATION,
						  NBSOperationLookup.ADD,
						  progAreaCd,
						  jurisdictionCd);

				   check7 = secObj.getPermission(NBSBOLookup.OBSERVATIONMORBIDITYREPORT,
						   NBSOperationLookup.MARKREVIEWED,
						   progAreaCd,
						   jurisdictionCd, sharedInd);
						  if (progAreaCd != null
								  && PropertyUtil.isStdOrHivProgramArea(progAreaCd)
								  && check7
								  && !morbidityProxyVO.isAssociatedInvInd()
								  && morbReportDT.getProcessingDecisionCd() != null
								  && !morbReportDT.getProcessingDecisionCd().trim()
								  .equals(""))
							  clearMarkAsReviewed = true;

			  }
			  if (check1 == false)
			  {
				  session.setAttribute("error", "Failed at security checking.");
				  throw new ServletException("Failed at security checking.");
			  }
			  if (morbReportDT.getElectronicInd() != null &&
					  morbReportDT.getElectronicInd().equalsIgnoreCase("E"))
			  {
				  check10 = true;
				  request.setAttribute("electronicInd", "true");
				  request.setAttribute("morbReport.electronicInd",
						  morbReportDT.getElectronicInd());
			  }

			  Long mprUid = null;
			  ArrayList<Object> stateList = new ArrayList<Object> ();
			  try
			  {
				  //using personUid to get personvo from back-end

				  mprUid = (Long) NBSContext.retrieve(session, "DSPatientPersonUID");
				  PersonVO personVO = morbidityUtil.findPatientRevision(morbidityProxyVO);

				  PersonUtil.convertPersonToRequestObj(personVO, request,
						  "PatientFromEvent", stateList);
				  createXSP(NEDSSConstants.PATIENT_LDF, personVO.getThePersonDT().getPersonUid(), personVO, null, request);
				  if (sCurrTask.equals("ViewObservationMorb3") || sCurrTask.equals("ViewObservationMorb9") || sCurrTask.equals("ViewObservationMorb10")) {
					  NBSContext.store(session,"DSPatientPersonVO",personVO); //needed by AssocInv
					  NBSContext.store(session,"DSProgramArea",progAreaCd); //needed for assoc to inv processing decision
					  Collection<String>  conditionList= new ArrayList<String> (); //neded for assoc to inv processing decision
					  if (morbReportDT.getCd() != null && !morbReportDT.getCd().isEmpty()) {
						  conditionList.add(morbReportDT.getCd());
						  NBSContext.store(session,"DSConditionList", conditionList); //needed by AssocInv
					  }
				  }
				  CommonAction ca = new CommonAction();
				  ArrayList<String>  conditionList= new ArrayList<String> ();
				  conditionList.add(morbReportDT.getCd());
				  request.setAttribute("PDLogic", ca.checkIfSyphilisIsInConditionList(progAreaCd, conditionList, NEDSSConstants.MORBIDITY_REPORT));
				  request.setAttribute("PDLogicCreateInv", ca.checkIfSyphilisIsInConditionListForCreateInv(progAreaCd, conditionList, NEDSSConstants.MORBIDITY_REPORT));


			  }
			  catch (NullPointerException ne)
			  {
				  logger.fatal("Can not retrieve DSPatientPersonUID from Object Store.");
				  ErrorMessageHelper.setErrMsgToRequest(request, "PS137");
			  }

			  Map<Object,Object> orgMap = morbidityUtil.setOrgToString(morbidityProxyVO.
					  getTheOrganizationVOCollection());

			  Collection<Object>  perColl = morbidityProxyVO.getThePersonVOCollection();
			  for (Iterator<Object> iter = perColl.iterator(); iter.hasNext(); )
			  {
				  PersonVO perVO = (PersonVO) iter.next();
				  if (perVO.getThePersonDT().getCd().equalsIgnoreCase("PAT"))
				  {
					  request.setAttribute("currSexCd", perVO.getThePersonDT().getCurrSexCd());
					  request.setAttribute("birthTime",
							  StringUtils.formatDate(perVO.getThePersonDT().
									  getBirthTime()));
				  }
			  }

			  boolean hasMarkAsReviewed = false;
			  boolean hasClearMarkAsReviewed = false;
			  boolean hasTransOwn = false;
			  boolean hasDelete = false;
			  boolean hasEdit = false;
			  boolean hasCrInv = false;
			  //boolean hasAddComment = false;

			  if (contextAction.equalsIgnoreCase(NBSConstantUtil.MarkAsReviewed) ||
					  contextAction.equalsIgnoreCase(NBSConstantUtil.ClearMarkAsReviewed) ||
					  contextAction.equalsIgnoreCase("AddCommentMorb") ||
					  contextAction.equalsIgnoreCase(NBSConstantUtil.CANCEL) ||
					  contextAction.equalsIgnoreCase("ObservationMorbIDOnEvents") ||
					  contextAction.equalsIgnoreCase("ReturnToViewObservationMorb") ||
					  contextAction.equalsIgnoreCase(NBSConstantUtil.SUBMIT) ||
					  contextAction.equalsIgnoreCase("ViewMorbTreatment") ||
					  contextAction.equalsIgnoreCase("ViewMorb") ||
					  contextAction.equalsIgnoreCase("ObservationMorbIDOnSummary") ||
					  contextAction.equalsIgnoreCase("ObservationMorbID") ||
					  contextAction.equalsIgnoreCase("DeleteDenied")||
					  contextAction.equalsIgnoreCase("ViewObservationMorb")||
					  contextAction.equalsIgnoreCase("CreateInvestigationFromMorbidity"))
			  {

				  if (sCurrTask.endsWith("Morb3"))
				  {
					  request.setAttribute("linkName", "Return to File: Events");
					  request.setAttribute("linkValue",
							  "/nbs/" + sCurrTask + ".do?ContextAction=" +
									  tm.get("ReturnToFileEvents"));
					  request.setAttribute("associateToInvestigationsButtonHref",
							  "/nbs/" + sCurrTask + ".do?ContextAction=" +
									  tm.get(NBSConstantUtil.AssociateToInvestigations)+ "&initLoad=true");
					  hasMarkAsReviewed = true;
					  hasClearMarkAsReviewed = true;
					  hasTransOwn = true;
					  hasDelete = true;
					  hasEdit = true;
					  hasCrInv = true;

				  }
				  else if (sCurrTask.endsWith("Morb1")
						  || sCurrTask.endsWith("Morb4")
						  || sCurrTask.endsWith("Morb6")
						  || sCurrTask.endsWith("Morb17")
						  || sCurrTask.endsWith("Morb18")
						  || sCurrTask.endsWith("Morb19")) {
					  /*if (formCd.equals(NBSConstantUtil.INV_FORM_RVCT)) {
						request.setAttribute("linkName",
								"Return to View Investigation");
						request.setAttribute("linkValue", "/nbs/" + sCurrTask
								+ ".do?ContextAction="
								+ tm.get("ReturnToViewInvestigationTB"));
					} else*/
					  {
						  request.setAttribute("linkName",
								  "Return To View Investigation");
						  request.setAttribute("linkValue", "/nbs/" + sCurrTask
								  + ".do?ContextAction="
								  + tm.get("ReturnToViewInvestigation"));
					  }
					  hasEdit = true;
					  hasTransOwn = true;
					  hasMarkAsReviewed = true;
					  hasClearMarkAsReviewed = true;
					  // hasAddComment = true;
				  }

				  else if (sCurrTask.endsWith("Morb8"))
				  {
					  hasTransOwn = true;
					  //hasAddComment = true;

				  }

				  else if (sCurrTask.endsWith("Morb9"))
				  {
					  request.setAttribute("linkName", "View File");
					  request.setAttribute("linkValue",
							  "/nbs/" + sCurrTask + ".do?ContextAction=" +
									  tm.get("ReturnToFileSummary"));
					  request.setAttribute("associateToInvestigationsButtonHref",
							  "/nbs/" + sCurrTask + ".do?ContextAction=" +
									  tm.get(NBSConstantUtil.AssociateToInvestigations));
					  hasDelete = true;
					  hasEdit = true;
					  hasCrInv = true;
					  hasTransOwn = true;
					  hasMarkAsReviewed= true;
					  hasClearMarkAsReviewed = true;

				  }
				  else if (sCurrTask.endsWith("Morb10"))
				  {
					  request.setAttribute("linkName", "View File");
					  request.setAttribute("linkValue",
							  "/nbs/" + sCurrTask + ".do?ContextAction=" +
									  tm.get("ReturnToFileSummary"));
					  request.setAttribute("associateToInvestigationsButtonHref",
							  "/nbs/" + sCurrTask + ".do?ContextAction=" +
									  tm.get(NBSConstantUtil.AssociateToInvestigations));
					  hasDelete = true;
					  hasEdit = true;
					  hasCrInv = true;
					  hasTransOwn = true;
					  hasMarkAsReviewed = true;
					  hasClearMarkAsReviewed = true;

				  }
				  else if(sCurrTask.endsWith("ViewObservationMorb11") ||
						  sCurrTask.endsWith("ViewObservationMorb12") ||
						  sCurrTask.endsWith("ViewObservationMorb13"))
				  {
					  request.setAttribute("linkName", "Return To Manage Associations");
					  request.setAttribute("linkValue",
							  "/nbs/" + sCurrTask + ".do?ContextAction=" +
									  tm.get("ManageEvents"));
				  }
        else if (sCurrTask.endsWith("Morb2") ||
						  sCurrTask.endsWith("Morb5") ||
                sCurrTask.endsWith("Morb7") ||
                sCurrTask.endsWith("Morb14")||
                sCurrTask.endsWith("Morb15") ||
						  sCurrTask.endsWith("Morb16"))
				  {
					  request.setAttribute("linkName", "Return To Manage Associations");
					  request.setAttribute("linkValue",
							  "/nbs/" + sCurrTask + ".do?ContextAction=" +
									  tm.get("ManageEvents"));
					  hasEdit = true;
					  hasDelete = true;
					  hasTransOwn = true;
					  hasMarkAsReviewed = true;
					  hasClearMarkAsReviewed = true;
					  //hasAddComment = true;
				  }


				  morbidityUtil.convertParticipationsToRequest(morbidityProxyVO, request);
				  request.setAttribute("CurrentTask", sCurrTask);
				  request.setAttribute("formHref", "/nbs/" + sCurrTask + ".do");

				  // buttons
				  request.setAttribute("markAsReviewButtonHref",
						  "/nbs/" + sCurrTask + ".do?ContextAction=" +
								  tm.get("MarkAsReviewed"));
				  request.setAttribute("clearMarkAsReviewButtonHref",
						  "/nbs/" + sCurrTask + ".do?ContextAction=" +
								  tm.get("ClearMarkAsReviewed"));

				  request.setAttribute("transferOwnership",
						  "/nbs/" + sCurrTask + ".do?ContextAction=" +
								  tm.get("TransferOwnership"));

				  request.setAttribute("editButtonHref",
						  "/nbs/" + sCurrTask + ".do?ContextAction=" +
								  tm.get("Edit"));

				  request.setAttribute("deleteButtonHref",
						  "/nbs/" + sCurrTask + ".do?ContextAction=" +
								  tm.get("Delete"));

				  request.setAttribute("creatInvestigationButtonHref",
						  "/nbs/" + sCurrTask + ".do?ContextAction=" +
								  tm.get("CreateInvestigation"));

				  request.setAttribute("viewEdit", "true");

				  // View Events
        if (contextAction.equals("ViewMorb") && !sCurrTask.equalsIgnoreCase("ViewObservationMorb8")) { //ND-910 GT || contextAction.equals(NBSConstantUtil.CANCEL)) {
					  request.setAttribute("linkNameViewEvents", "View Events");
	        String viewEventsLink = "/nbs/" + sCurrTask + ".do?ContextAction=ViewEventsPopup";
					  request.setAttribute("linkValueViewEvents",
							  "javascript:function popupViewEvents(){window.open('"+viewEventsLink+"');};popupViewEvents();");
				  }


				  request.setAttribute("PrintPage", "/nbs/LoadViewObservationMorb1.do?ContextAction=PrintPage");

				  boolean check9 = false; //check if already has an investigation associated
				  try
				  {
					  check9 = morbidityUtil.investigationAssociated(observationUid,
							  session);
				  }
				  catch (Exception e)
				  {
					  logger.error(e);
					  e.printStackTrace();
				  }

				  // button access attributes
				  if (hasDelete && ( (!check10 && check2) || (check10 && check3)))
				  {
					  request.setAttribute("checkDelete", "true");
				  }
				  else
				  {
					  request.setAttribute("checkDelete", "false");
				  }
				  if (hasEdit && ( (!check10 && check4)))
				  {
					  request.setAttribute("checkEdit", "true");
				  }
				  else
				  {
					  request.setAttribute("checkEdit", "false");
				  }
				  if (hasCrInv && ( (check5 && (!check9))))
				  {
					  request.setAttribute("checkCreateInvestigation", "true");
				  }
				  else
				  {
					  request.setAttribute("checkCreateInvestigation", "false");
				  }
				  if (check7 && hasMarkAsReviewed && !isProcessed)
				  {
					  request.setAttribute("checkMarkAsReview", "true");
				  }
				  else
				  {
					  request.setAttribute("checkMarkAsReview", "false");
				  }
				  if (clearMarkAsReviewed && hasClearMarkAsReviewed)
				  {
					  request.setAttribute("checkClearMarkAsReview", "true");
				  }
				  else
				  {
					  request.setAttribute("checkClearMarkAsReview", "false");
				  }
				  if (check8 && hasTransOwn)
				  {
					  request.setAttribute("checkTransfer", "true");
				  }
				  else
				  {
					  request.setAttribute("checkTransfer", "false");
				  }
				  if (check10)
				  {
					  request.setAttribute("checkAddComment", "true");
				  }
				  else
				  {
					  request.setAttribute("checkAddComment", "false");
				  }

				  if (morbidityProxyVO.getAssociatedNotificationInd())
				  {
					  request.setAttribute("ERR137", "true");
				  }
				  else
				  {
					  request.setAttribute("ERR137", "false");
				  }

			  }
			  request.setAttribute("observationLocalUID", observationLocalUid);
			  morbidityUtil.convertParticipationsToRequest(morbidityProxyVO, request);
			  morbidityUtil.mapMorbReportToRequest(morbidityProxyVO, request);
			  mapObsQAToRequest(morbidityProxyVO.getTheObservationVOCollection(), request);
			  morbidityUtil.mapBatchEntryToRequest(morbidityProxyVO, request);
			  AddCommentUtil acu = new AddCommentUtil();
			  acu.setAddUserComments(morbidityProxyVO, request, secObj);
			  morbidityUtil.getFilteredProgramAreaJurisdiction(request, secObj);
		  }

		  String conditionCode = morbReportVO.getTheObservationDT().getCd();

		  //* Getting the values for the Treatment Drugs *//
		  String treatmentDrugsDropdown = NedssCodeLookupServlet.
				  convertTreatmentToRequest(NEDSSConstants.TREATMENT_SRT_DRUGS,
						  NEDSSConstants.TREATMENT_SRT_VIEW, null,
						  conditionCode);
		  request.setAttribute("teatmentDrugDropdown", treatmentDrugsDropdown);

		  //LDF
		  /*
		   *  Added business ObjectUID as a parameter for CreateXSP method in all Edit and View Load
		   *  as a part of CDF changes
		   */
		  CommonLabUtil commonLabUtil = new CommonLabUtil();
		  Long observationUidforLDF = morbReportVO.getTheObservationDT().getObservationUid();
		  commonLabUtil.loadMorbLDFView(morbidityProxyVO,observationUidforLDF,request);

		  if(contextAction.equalsIgnoreCase("PrintPage")) {
			  return new ActionForward("/observation/morbreport/morbreport_print");
		  }

		  // set tab order before we forward, This trumps any logic we may have before on tab selection xz 11/03/2004
		  // get the value for DSFileTab from the properties only if it is null in the session. see InvestigationViewSubmit.java for detail
		  if(session.getAttribute("MorbReportAddCommentDSTab")!=null)
			  request.setAttribute("DSFileTab", (String)session.getAttribute("MorbReportAddCommentDSTab"));
		  else
			  request.setAttribute("DSFileTab", new Integer(PropertyUtil.getInstance().getDefaultMorbTabOrder()).toString());
		  session.removeAttribute("MorbReportAddCommentDSTab");
	  }catch (Exception e) {
		  logger.error("Exception in Morb Report Load: " + e.getMessage());
		  e.printStackTrace();
		  throw new ServletException("An error occurred in Morbidity Load : "+e.getMessage());
	  }
        return (mapping.findForward("XSP"));
  }

}