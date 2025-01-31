package gov.cdc.nedss.webapp.nbs.action.observation.morbidityreport;

import gov.cdc.nedss.systemservice.nbscontext.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.webapp.nbs.action.util.CommonAction;
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
import gov.cdc.nedss.webapp.nbs.action.observation.labreport.*;

import java.util.*;
import java.io.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;
import gov.cdc.nedss.webapp.nbs.servlet.NedssCodeLookupServlet;

/**
 * Title:        EditObservationMorbidityReportLoad.java
 * Description:	This is a action class for the structs implementation
 * Copyright:	Copyright (c) 2001
 * Company: 	Computer Sciences Corporation
 * @author	NEDSS Development Team
 * @version	1.0
 */

public class EditObservationMorbidityReportLoad
    extends CommonAction
{

  //For logging
  static final LogUtils logger = new LogUtils(
      EditObservationMorbidityReportLoad.class.
      getName());

  public ActionForward execute(ActionMapping mapping, ActionForm aForm,
                               HttpServletRequest request,
                               HttpServletResponse response) throws IOException,
      ServletException
  {
	  try {
		  HttpSession session = request.getSession();
		  String contextAction = request.getParameter("ContextAction");
		  NBSSecurityObj secObj = (NBSSecurityObj) session.getAttribute("NBSSecurityObject");

		  TreeMap<Object,Object> tm = NBSContext.getPageContext(session, "PS138", contextAction);
		  String sCurrTask = NBSContext.getCurrentTask(session);


		  request.setAttribute("currentTask",sCurrTask);
		  Long observationUid = null;
		  MorbidityUtil morbidityUtil = new MorbidityUtil();
		  try
		  {
			  observationUid =  (Long) NBSContext.retrieve(session, "DSObservationUID");
		  }
		  catch (NullPointerException ne)
		  {
			  logger.fatal("Can not retrieve DSObservationUID from Object Store.");
			  ErrorMessageHelper.setErrMsgToRequest(request, "PS137");
		  }

		  MorbidityProxyVO morbidityProxyVO = null;
		  ObservationVO morbReportVO = null;
		  ObservationDT morbReportDT = null;
		  try
		  {
			  morbidityProxyVO = morbidityUtil.getProxyFromEJB(observationUid, session);
			  MorbidityForm form = (MorbidityForm)aForm;
			  form.reset();
			  form.setOldProxy(morbidityProxyVO);
			  PersonVO patientVO = morbidityUtil.findPatientRevision(morbidityProxyVO);
			  form.setPatient(patientVO);
			  Collection<ObservationVO>  observationVOcoll = morbidityProxyVO.getTheObservationVOCollection();
			  TreeMap<Object,Object> treeMap = mapObsQA(observationVOcoll);
			  form.setQaTreeMap(treeMap);
			  Collection<Object>  labResultColl = morbidityUtil.mapBatchEntry(morbidityProxyVO);
			  form.setLabResultsCollection(labResultColl);
			  Collection<Object>  treatmentColl = morbidityUtil.mapBatchEntryForTreatment(morbidityProxyVO);
			  form.setTreatmentsCollection(treatmentColl);
			  Collection<Object>  attachmentColl = morbidityUtil.mapBatchEntryForAttachment(morbidityProxyVO,request);
			  form.setAttachmentCollection(attachmentColl);
			  morbReportVO = morbidityUtil.getMorbReportObsVO(morbidityProxyVO);
			  form.setMorbidityReport(morbReportVO);
			  morbReportDT = morbReportVO.getTheObservationDT();
			  NBSContext.store(session, "DSObservationLocalID", morbReportDT.getLocalId());
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
			  ErrorMessageHelper.setErrMsgToRequest(request, "PS137");
		  }

		  Collection<ObservationVO>  observationVOcoll = morbidityProxyVO.getTheObservationVOCollection();

		  String progAreaCd = morbReportDT.getProgAreaCd();
		  String jurisdictionCd = morbReportDT.getJurisdictionCd();
		  String sharedInd = morbReportDT.getSharedInd();
		  String observationLocalUid = morbReportDT.getLocalId();
		  request.setAttribute("observationLocalUID", observationLocalUid);
		  CommonAction ca = new CommonAction();
		  ArrayList<String>  conditionList= new ArrayList<String> ();
		  conditionList.add(morbReportDT.getCd());
		  request.setAttribute("PDLogic", ca.checkIfSyphilisIsInConditionList(progAreaCd, conditionList, NEDSSConstants.MORBIDITY_REPORT));
		  // change when implementation is ready
		  boolean check10 = false;

		  boolean check1 = secObj.getPermission(NBSBOLookup.
				  OBSERVATIONMORBIDITYREPORT,
				  NBSOperationLookup.VIEW,
				  progAreaCd,
				  jurisdictionCd, sharedInd);
		  if (check1 == false)
		  {
			  session.setAttribute("error", "Failed at security checking.");
			  throw new ServletException("Failed at security checking.");
		  }
		  if(morbReportDT.getElectronicInd() != null && morbReportDT.getElectronicInd().equalsIgnoreCase("E"))
			  check10 = true;

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
		  }
		  catch (NullPointerException ne)
		  {
			  logger.fatal("Can not retrieve DSPatientPersonUID from Object Store.");
			  ErrorMessageHelper.setErrMsgToRequest(request, "PS137");
		  }
		  Map<Object,Object> orgMap = morbidityUtil.setOrgToString(morbidityProxyVO.getTheOrganizationVOCollection());
		  request.setAttribute("reportingSourceDetails", (String)orgMap.get("organization"));
		  Long reportingUID = (Long)orgMap.get("UID");


		  request.setAttribute("ContextAction", tm.get("Submit"));

		  // buttons
		  request.setAttribute("formHref", "/nbs/" + sCurrTask + ".do");

		  request.setAttribute("cancelButtonHref",
				  "/nbs/" + sCurrTask + ".do?ContextAction=" +
						  tm.get("Cancel"));

		  ErrorMessageHelper.setErrMsgToRequest(request, "PS049");

		  String jurisdictionValues = new CachedDropDownValues().getJurisdictionCodedSortedValues();
		  jurisdictionValues = "NONE$Unknown|" + jurisdictionValues;
		  request.setAttribute("jurisdictionCodedSortedValues", jurisdictionValues);

		  boolean check2 = secObj.getPermission(NBSBOLookup.
				  OBSERVATIONMORBIDITYREPORT,
				  NBSOperationLookup.DELETE,
				  progAreaCd,
				  jurisdictionCd, sharedInd);

		  boolean check3 = secObj.getPermission(NBSBOLookup.
				  OBSERVATIONMORBIDITYREPORT,
				  NBSOperationLookup.DELETEEXTERNAL,
				  progAreaCd,
				  jurisdictionCd, sharedInd);

		  boolean check4 = secObj.getPermission(NBSBOLookup.
				  OBSERVATIONMORBIDITYREPORT,
				  NBSOperationLookup.EDIT,
				  progAreaCd,
				  jurisdictionCd, sharedInd);

		  boolean check5 = secObj.getPermission(NBSBOLookup.INVESTIGATION,
				  NBSOperationLookup.ADD,
				  progAreaCd,
				  NBSOperationLookup.ANY);

		  boolean check6 = secObj.getPermission(NBSBOLookup.INVESTIGATION,
				  NBSOperationLookup.AUTOCREATE,
				  progAreaCd,
				  NBSOperationLookup.ANY);

		  boolean check7 = secObj.getPermission(NBSBOLookup.PATIENT,
				  NBSOperationLookup.VIEWWORKUP,
				  progAreaCd,
				  NBSOperationLookup.ANY);

		  boolean check8 = secObj.getPermission(NBSBOLookup.
				  OBSERVATIONMORBIDITYREPORT,
				  NBSOperationLookup.
				  TRANSFERPERMISSIONS,
				  progAreaCd,
				  NBSOperationLookup.ANY);

		  boolean check9 = false; //check if already has an investigation associated
		  try
		  {
			  check9 = morbidityUtil.investigationAssociated(observationUid, session);
		  }
		  catch (Exception e)
		  {
			  logger.error(e);
			  e.printStackTrace();
		  }

		  // button access attributes
		  if ((check10&&check2) || (check10&&check3))
		  {
			  request.setAttribute("checkDelete", "true");
		  }
		  if ((check10&&check4))
		  {
			  request.setAttribute("checkEdit", "true");
		  }
		  if ( (check5 && (!check9)) || (check6 && (!check9)))
		  {
			  request.setAttribute("checkCreateInvestigation", "true");
		  }
		  if (check7)
		  {
			  request.setAttribute("checkMarkAsReview", "true");
		  }
		  if (check8)
		  {
			  request.setAttribute("checkTransfer", "true");
		  }
		  if (check10)
		  {
			  request.setAttribute("checkAddComment", "true");
		  }

		  if (morbidityProxyVO.getAssociatedNotificationInd())
		  {
			  request.setAttribute("ERR137", "true");
		  }


		  // links
		  request.setAttribute("linkName", "Return to View File");
		  request.setAttribute("linkValue",
				  "/nbs/" + sCurrTask + ".do?ContextAction=" +
						  tm.get("ReturnToFileEvents"));
		  request.setAttribute("viewEdit", "true");

		  //morbidityUtil.mapEntityToRequest(morbidityProxyVO, request);
		  morbidityUtil.convertParticipationsToRequest(morbidityProxyVO, request);
		  morbidityUtil.convertParticipationsToRequest(morbidityProxyVO, request);
		  morbidityUtil.mapMorbReportToRequest(morbidityProxyVO, request);
		  mapObsQAToRequest(morbidityProxyVO.getTheObservationVOCollection(), request);
		  String conditionCode = morbReportVO.getTheObservationDT().getCd();
		  if(reportingUID!= null)
		  {
			  CommonLabUtil.getDropDownLists(NEDSSConstants.DEFAULT, NEDSSConstants.RESULTED_TEST_LOOKUP,conditionCode,
					  null,null,null,
					  request);
		  }
		  morbidityUtil.mapBatchEntryToRequest(morbidityProxyVO, request);
		  morbidityUtil.getFilteredProgramAreaJurisdiction(request, secObj);



		  //* Getting the values for the Treatment Drugs *//
		  String treatmentDrugsDropdown = NedssCodeLookupServlet.
				  convertTreatmentToRequest(NEDSSConstants.TREATMENT_SRT_DRUGS,
						  NEDSSConstants.TREATMENT_SRT_EDIT, null,
						  conditionCode);
		  request.setAttribute("teatmentDrugDropdown", treatmentDrugsDropdown);



		  // Data stored in ObjectStore
		  NBSContext.store(session, NBSConstantUtil.DSProgramArea, progAreaCd);
		  NBSContext.store(session, NBSConstantUtil.DSJurisdiction, jurisdictionCd);
		  NBSContext.store(session, "DSObservationSharedInd", sharedInd);
		  NBSContext.store(session, "DSObservationLocalID", observationUid);
		  NBSContext.store(session, "DSObservationUID", observationUid);
		  NBSContext.store(session, "DSObservationTypeCd", NEDSSConstants.MORBIDITY_CODE);
		  NBSContext.store(session, "DSPatientPersonUID", mprUid);

		  //LDF
		  CommonLabUtil commonLabUtil = new CommonLabUtil();

		  // Added business ObjectUID as a parameter for CreateXSP method in all Edit and View Load
		  Long observationUidforLdf = morbReportVO.getTheObservationDT().getObservationUid();
		  commonLabUtil.loadMorbLDFEdit(morbidityProxyVO, observationUidforLdf,request);
		  PropertyUtil propUtil = PropertyUtil.getInstance();
		  int maxSizeInMB = propUtil.getMaxFileAttachmentSizeInMB();
		  request.setAttribute("maxFileSizeInMB", String.valueOf(maxSizeInMB)); 

		  String allowedExtensions = propUtil.getFileAttachmentExtensions();
		  request.setAttribute("allowedExtensions", allowedExtensions); 
		  
		  request.setAttribute("addMorb", "true");
		  
		  // set tab order before we forward, This trumps any logic we may have before on tab selection xz 11/03/2004
		  request.setAttribute("DSFileTab", new Integer(PropertyUtil.getInstance().getDefaultMorbTabOrder()).toString());
	  }catch (Exception e) {
		  logger.error("Exception in Edit Morb Load: " + e.getMessage());
		  e.printStackTrace();
		  throw new ServletException("Error occurred in Edit Lab Report Submit : "+e.getMessage());
	  }  	

    return (mapping.findForward("XSP"));
  }

}
