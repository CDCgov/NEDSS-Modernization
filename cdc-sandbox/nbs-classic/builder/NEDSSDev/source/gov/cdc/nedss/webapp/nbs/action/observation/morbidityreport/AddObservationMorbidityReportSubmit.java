package gov.cdc.nedss.webapp.nbs.action.observation.morbidityreport;

import gov.cdc.nedss.util.*;
import gov.cdc.nedss.act.observation.vo.*;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.systemservice.nbscontext.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.CoinfectionSummaryVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.*;
import gov.cdc.nedss.association.dt.*;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.act.observation.dt.*;
import gov.cdc.nedss.webapp.nbs.form.morbidity.*;
import gov.cdc.nedss.webapp.nbs.util.BatchEntryHelper;
import gov.cdc.nedss.webapp.nbs.action.util.*;
import gov.cdc.nedss.act.treatment.vo.TreatmentVO;
import gov.cdc.nedss.act.treatment.dt.*;
import gov.cdc.nedss.webapp.nbs.action.observation.morbidityreport.util.*;
import gov.cdc.nedss.systemservice.util.*;
import gov.cdc.nedss.webapp.nbs.action.observation.labreport.CommonLabUtil;

import java.util.*;
import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;

import java.sql.Timestamp;

/**
 * Title:        AddObservationMorbidityReportSubmit.java
 * Description:	This is a action class for the struts implementation
 * Copyright:	Copyright (c) 2001
 * Company: 	Computer Sciences Corporation
 * @author	NEDSS Development Team
 * @version	1.0
 */

public class AddObservationMorbidityReportSubmit
    extends CommonAction
{

  //For logging
  static final LogUtils logger = new LogUtils(
      AddObservationMorbidityReportSubmit.class.getName());

  public AddObservationMorbidityReportSubmit()
  {
  }

  public ActionForward execute(ActionMapping mapping, ActionForm aForm,
                               HttpServletRequest request,
                               HttpServletResponse response) throws IOException,
      ServletException
  {

    HttpSession session = request.getSession();

    //get contextAction and pass to forward
    String contextAction = request.getParameter("ContextAction");

    String sCurrTask = NBSContext.getCurrentTask(session);

    if (contextAction.equalsIgnoreCase(NBSConstantUtil.CANCEL))
    {
      NBSContext.store(session, "DSFileTab", "3");
      return (mapping.findForward(contextAction));
    }

    NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session.getAttribute(
        "NBSSecurityObject");
    MorbidityForm morbidityForm = (MorbidityForm) aForm;
    ObservationVO morbidityReportObsVO = morbidityForm.getMorbidityReport();
    ObservationDT morbidityReportObsDT = morbidityReportObsVO.
        getTheObservationDT();
    String processingDecision = morbidityReportObsDT.getProcessingDecisionCd();
    //set it to null since the processing decision needs to be saved with the act relationship of morb and Inv
    morbidityReportObsDT.setProcessingDecisionCd(null);
    MorbidityProxyVO proxy = createHandler(morbidityForm, nbsSecurityObj,
                                           session, request);
    
    MorbidityUtil morbUtil = new MorbidityUtil();
    
    
    
    Long observationUid = null;
    Long personMRPUid = null;
    ArrayList<?> result = null;
    String DSInvestigationProgramArea = null;
    String DSInvestigationJurisdiction = null;
    try
    {
      result = (ArrayList<?> ) sendProxyToEJB(proxy, session, sCurrTask, processingDecision);
      observationUid = (Long) result.get(0);
      personMRPUid = (Long) result.get(1);
      String investigationType = (String) request
				.getParameter("investigationType");
		if (investigationType != null)
			NBSContext.store(session, NBSConstantUtil.DSInvestigationType,
					investigationType);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      morbidityForm.reset();
      if (e instanceof NEDSSAppException)
      {
        NEDSSAppException nae = (NEDSSAppException) e;
        if (nae.getErrorCd().equals("ERR109"))
        {
          logger.info("ERROR ,  DataConcurrent exception recieved.", e);
          //redirect to dataconcurrent message page.
          return ErrorMessageHelper.redirectToPage("PS158", nae.getErrorCd());
        }
        //redirect to generic error page
        return ErrorMessageHelper.redirectToPage("PS175", nae.getErrorCd());
      }
      return ErrorMessageHelper.redirectToPage("PS175", null);
    }

    request.setAttribute("morbidityReportUid", observationUid);
    morbUtil.createAttachment(morbidityForm, nbsSecurityObj,session, request);
    
    
    if (sCurrTask.equalsIgnoreCase("AddObservationMorb2"))
    {
      

      if (morbidityReportObsDT.getJurisdictionCd() == null ||
          morbidityReportObsDT.getJurisdictionCd().trim().equalsIgnoreCase(
          "NONE"))
      {
        morbidityReportObsDT.setJurisdictionCd(ProgramAreaJurisdictionUtil.
                                               JURISDICTION_NONE);

      }
      DSInvestigationProgramArea = morbidityReportObsDT.getProgAreaCd();
      DSInvestigationJurisdiction = morbidityReportObsDT.getJurisdictionCd();
      String DSInvestigationCondition = morbidityReportObsDT.getCd();
      //this for submit and create investigation
      NBSContext.store(session, "DSInvestigationProgramArea",
                       DSInvestigationProgramArea);
      NBSContext.store(session, "DSInvestigationJurisdiction",
                       DSInvestigationJurisdiction);
      NBSContext.store(session, "DSInvestigationCondition",
                       DSInvestigationCondition);
      NBSContext.store(session, "DSObservationUID",
                       observationUid);
      NBSContext.store(session, "DSObservationTypeCd",
                       NEDSSConstants.MORBIDITY_CODE);

      //this is for observationConfirmationPage
      NBSContext.store(session, NBSConstantUtil.DSJurisdiction,
                       DSInvestigationJurisdiction);
      NBSContext.store(session, NBSConstantUtil.DSProgramArea,
                       DSInvestigationProgramArea);

      /**
       * Create a map and store in object store for use in investigation
       */
      FieldMappingBuilder mapBuilder = new FieldMappingBuilder();

      //Call getMorbidityProxyForSubmitAndCreateInv to get Org Facility /Provider / Reporter Info
      // which are really missing in the form Proxy !
      MorbidityProxyVO newProxy = null;
      TreeMap<Object,Object> loadTreeMap = null;
      String ContextAction = request.getParameter("ContextAction");
      if (ContextAction != null &&
          ! (ContextAction.equals("SubmitNoViewAccess") ||
             ContextAction.equals("SubmitAndCreateInvestigationNoViewAccess")))
      {
        try
        {
          MorbidityUtil util = new MorbidityUtil();
          newProxy = util.getMorbidityProxyForSubmitAndCreateInv(observationUid,
              session);

        }
        catch (Exception e)
        {
          //do nothing, use the Submit Class Proxy
        }
      }
      if (newProxy != null)
      {
        loadTreeMap = mapBuilder.createMorbidityLoadTreeMap(newProxy, processingDecision);
      }
      else
      {
        loadTreeMap = mapBuilder.createMorbidityLoadTreeMap(proxy, processingDecision);

      }

      NBSContext.store(session, "DSMorbMap", loadTreeMap);

      if (nbsSecurityObj.getPermission("INVESTIGATION", "AUTOCREATE",
                                       DSInvestigationProgramArea,
                                       DSInvestigationJurisdiction) == false)
      {
        String DSConfirmationMessage =
            "Security Failure for investigation autocreate.";
        NBSContext.store(session, "DSConfirmationMessage",
                         DSConfirmationMessage);
      }

    }

    morbidityForm.reset();
    proxy = null;
    NBSContext.store(session, "DSFileTab", "3");

    return (mapping.findForward(contextAction));
  }

  /**
   * @method       : createHandler
   * @returnType   : void
   * @params       : ActionMapping, ActionForm, HttpServletRequest, HttpSession, NBSSecurityObj
   */
  private MorbidityProxyVO createHandler(MorbidityForm form,
                                         NBSSecurityObj securityObj,
                                         HttpSession session,
                                         HttpServletRequest request)
  {
	  MorbidityProxyVO proxy = null;
	  try {
		  String patientLocalId = (String) session.getAttribute("personLocalID");

		  // observation create initial uid
		  int i = -10;

		  ArrayList<ObservationVO> observations = new ArrayList<ObservationVO> ();
		  ArrayList<Object> actrelations = new ArrayList<Object> ();
		  ArrayList<Object> roles = new ArrayList<Object> ();
		  ArrayList<Object> participations = new ArrayList<Object> ();
		  ArrayList<Object> treatments = new ArrayList<Object> ();

		  // setup the observations(MorbFormQuestion & MorbFromComment) from morbidityForm
		  proxy = form.getMorbidityProxy();
		  i = setObservationForCreate(proxy.getTheObservationVOCollection(),
				  observations, request, i);
		  //check for external user
		  String userType = securityObj.getTheUserProfile().getTheUser().getUserType();

		  // Build the morbidityReport (a dummy observation)
		  ObservationVO morbidityReportObsVO = form.getMorbidityReport();
		  ObservationDT morbidityReportObsDT = morbidityReportObsVO.
				  getTheObservationDT();
		  morbidityReportObsDT.setObservationUid(new Long(i--));
		  morbidityReportObsDT.setObsDomainCdSt1(NEDSSConstants.
				  ORDERED_TEST_OBS_DOMAIN_CD);
		  morbidityReportObsDT.setCtrlCdDisplayForm(NEDSSConstants.MORBIDITY_CODE);
		  if (userType != null &&
				  userType.equalsIgnoreCase(NEDSSConstants.SEC_USERTYPE_EXTERNAL))
		  {
			  morbidityReportObsDT.setElectronicInd(NEDSSConstants.EXTERNAL_USER_IND);
		  }
		  else
			  morbidityReportObsDT.setElectronicInd(NEDSSConstants.ELECTRONIC_IND);
		  morbidityReportObsDT.setStatusTime(new java.sql.Timestamp(new Date().
				  getTime()));
		  morbidityReportObsDT.setStatusCd("D");
		  morbidityReportObsDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
		  morbidityReportObsDT.setRecordStatusTime(new java.sql.Timestamp(new Date().
				  getTime()));
		  morbidityReportObsDT.setCdDescTxt("Condition");
		  morbidityReportObsDT.setCdSystemCd("NBS");
		  morbidityReportObsDT.setCdSystemDescTxt("NEDSS Base System");
		  morbidityReportObsDT.setCdVersion("1.0");
		  String sCurrTask = NBSContext.getCurrentTask(session);
		  /*If given the folllowing three taskes, the shared ind is
    defaulted to T in view page and readonly; herein set it to "T"*/
		  if (morbidityReportObsDT.getSharedInd() == null &&
				  (sCurrTask.equalsIgnoreCase("AddObservationMorb1") ||
						  sCurrTask.equalsIgnoreCase("AddObservationMorb3") ||
						  sCurrTask.equalsIgnoreCase("AddObservationMorb4")))
		  {
			  morbidityReportObsDT.setSharedInd("T");
		  }
		  if (morbidityReportObsDT.getSharedInd() == null)
		  {
			  morbidityReportObsDT.setSharedInd("F");
		  }

		  if (sCurrTask != null && ( (sCurrTask.equalsIgnoreCase("AddObservationMorb1")) || (sCurrTask.equalsIgnoreCase("AddObservationMorb3")) ||
				  (sCurrTask.equalsIgnoreCase("AddObservationMorb4")) ) )
		  {
			  String jurisdictioncd = (String) NBSContext.retrieve(session,
					  NBSConstantUtil.DSJurisdiction);
			  morbidityReportObsDT.setJurisdictionCd(jurisdictioncd);
		  }

		  morbidityReportObsDT.setItNew(true);
		  morbidityReportObsDT.setItDirty(false);
		  morbidityReportObsVO.setItNew(true);
		  morbidityReportObsVO.setItDirty(false);
		  observations.add(morbidityReportObsVO);

		  //get all the person Uids from request object for build relationships
		  Long personSubjectUid = new Long( -1);
		  Long personReporterUid = null;
		  Long personPhysicianUid = null;

		  // Patient
		  String sPatient = request.getParameter("Patient.personUid");
		  if (sPatient != null)
		  {
			  if (sPatient.equals(""))
			  {
				  logger.info("sPatient is empty");
			  }
			  else
			  {
				  personSubjectUid = new Long(sPatient);
			  }
		  }

		  // Reporter
		  String sReporter = request.getParameter("Prov-entity.reporterUID");
		  if (sReporter != null)
		  {
			  if (sReporter.equals(""))
			  {
				  logger.info("sReporter is empty");
			  }
			  else
			  {
				  personReporterUid = new Long(sReporter);
			  }
		  }

		  // Provider
		  String sProvider = request.getParameter("Prov-entity.providerUID");
		  if (sProvider != null)
		  {
			  if (sProvider.equals(""))
			  {
				  logger.info("sProvider is empty");
			  }
			  else
			  {
				  personPhysicianUid = new Long(sProvider);
			  }
		  }

		  //get all the organization Uids from request object for build relationships
		  Long organizationReportingUid = null;
		  Long organizationHospitalUid = null;

		  // Reporting Facility
		  String sReportingFacility = request.getParameter(
				  "Org-ReportingOrganizationUID");
		  if (sReportingFacility != null)
		  {
			  if (sReportingFacility.equals(""))
			  {
				  logger.info("sReportingFacility is empty");
			  }
			  else
			  {
				  organizationReportingUid = new Long(sReportingFacility);
			  }
		  }

		  // Hospital
		  String sHospital = request.getParameter("Org-HospitalUID");
		  if (sHospital != null)
		  {
			  if (sHospital.equals(""))
			  {
				  logger.info("sHospital is empty");
			  }
			  else
			  {
				  organizationHospitalUid = new Long(sHospital);
			  }
		  }

		  /**
		   * Build Role
		   */
		  //ROL113 Person as Provider
		  if (personPhysicianUid != null)
		  {
			  RoleDT rol113 = new RoleDT();
			  rol113.setCd("ICP");
			  rol113.setCdDescTxt("Individual Care Provider");
			  rol113.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
			  rol113.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
			  rol113.setSubjectClassCd("PROV");
			  rol113.setSubjectEntityUid(personPhysicianUid);
			  roles.add(rol113);
		  }

		  //ROL114 Organization as General Healthcare Facility
		  if (organizationReportingUid != null)
		  {
			  RoleDT rol114 = new RoleDT();
			  rol114.setCd("NI");
			  rol114.setCdDescTxt("No Information");
			  rol114.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
			  rol114.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
			  rol114.setSubjectClassCd("HCFAC");
			  rol114.setSubjectEntityUid(organizationReportingUid);
			  roles.add(rol114);
		  }

		  //ROL115 Person as Event Subject
		  if (personSubjectUid != null)
		  {
			  RoleDT rol115 = new RoleDT();
			  rol115.setCd("P");
			  rol115.setCdDescTxt("Patient");
			  rol115.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
			  rol115.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
			  rol115.setSubjectClassCd("PAT");
			  rol115.setSubjectEntityUid(personSubjectUid);
			  roles.add(rol115);
		  }

		  //ROL125 Organization as Admitting Facility
		  if (organizationHospitalUid != null)
		  {
			  RoleDT rol125 = new RoleDT();
			  rol125.setCd("ADM");
			  rol125.setEffectiveFromTime(null);
			  rol125.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
			  rol125.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
			  rol125.setSubjectClassCd("HCFAC");
			  rol125.setSubjectEntityUid(organizationHospitalUid);
			  roles.add(rol125);
		  }

		  //PAR130 Person(Patient) as Subject of Morbidity Report
		  if (personSubjectUid != null)
		  {
			  logger.info("personSubjectUid = " + personSubjectUid);
			  ParticipationDT par130 = new ParticipationDT();
			  par130.setActClassCd(NEDSSConstants.PART_ACT_CLASS_CD);
			  par130.setActUid(morbidityReportObsDT.getObservationUid());
			  par130.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
			  par130.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
			  par130.setSubjectClassCd(NEDSSConstants.PAR110_SUB_CD);
			  par130.setSubjectEntityUid(personSubjectUid);
			  par130.setTypeCd(NEDSSConstants.MOB_SUBJECT_OF_MORB_REPORT);
			  par130.setTypeDescTxt("Subject Of Morbidity Report");
			  par130.setItNew(true);
			  participations.add(par130);
		  }

		  //PAR132 Person(NPP) as Reporter of Morbidity Report
		  if (personReporterUid != null)
		  {
			  logger.info("personReporterUid = " + personReporterUid);
			  ParticipationDT par132 = new ParticipationDT();
			  par132.setActClassCd(NEDSSConstants.PART_ACT_CLASS_CD);
			  par132.setActUid(morbidityReportObsDT.getObservationUid());
			  par132.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
			  par132.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
			  par132.setSubjectClassCd(NEDSSConstants.PAR110_SUB_CD);
			  par132.setSubjectEntityUid(personReporterUid);
			  par132.setTypeCd(NEDSSConstants.MOB_REPORTER_OF_MORB_REPORT);
			  par132.setTypeDescTxt("Reporter Of Morbidity Report");
			  par132.setItNew(true);
			  participations.add(par132);
		  }

		  //PAR133 Person(NPP) as Physician in Morbidity Report
		  if (personPhysicianUid != null)
		  {
			  logger.info("personPhysicianUid = " + personPhysicianUid);
			  ParticipationDT par133 = new ParticipationDT();
			  par133.setActClassCd(NEDSSConstants.PART_ACT_CLASS_CD);
			  par133.setActUid(morbidityReportObsDT.getObservationUid());
			  par133.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
			  par133.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
			  par133.setSubjectClassCd(NEDSSConstants.PAR110_SUB_CD);
			  par133.setSubjectEntityUid(personPhysicianUid);
			  par133.setTypeCd(NEDSSConstants.MOB_PHYSICIAN_OF_MORB_REPORT);
			  par133.setTypeDescTxt("Physician Of Morbidity Report");
			  par133.setItNew(true);
			  participations.add(par133);
		  }

		  //PAR131 Org as Reporting Source of Morb Report
		  if (organizationReportingUid != null)
		  {
			  logger.info("organizationReportingUid = " + organizationReportingUid);
			  ParticipationDT par131 = new ParticipationDT();
			  par131.setActClassCd(NEDSSConstants.PART_ACT_CLASS_CD);
			  par131.setActUid(morbidityReportObsDT.getObservationUid());
			  par131.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
			  par131.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
			  par131.setSubjectClassCd(NEDSSConstants.PAR111_SUB_CD);
			  par131.setSubjectEntityUid(organizationReportingUid);
			  par131.setTypeCd(NEDSSConstants.MOB_REPORTER_OF_MORB_REPORT);
			  par131.setTypeDescTxt("Reporter Of Morbidity Report");
			  par131.setItNew(true);
			  participations.add(par131);
		  }

		  //PAR134 Org as Hospital in Morbidigy Report
		  if (organizationHospitalUid != null)
		  {
			  logger.info("organizationHospitalUid = " + organizationHospitalUid);
			  ParticipationDT par134 = new ParticipationDT();
			  par134.setActClassCd(NEDSSConstants.PART_ACT_CLASS_CD);
			  par134.setActUid(morbidityReportObsDT.getObservationUid());
			  par134.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
			  par134.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
			  par134.setSubjectClassCd(NEDSSConstants.PAR111_SUB_CD);
			  par134.setSubjectEntityUid(organizationHospitalUid);
			  par134.setTypeCd(NEDSSConstants.MOB_HOSP_OF_MORB_REPORT);
			  par134.setTypeDescTxt("Hospital Of Morbidity Report");
			  par134.setItNew(true);
			  participations.add(par134);
		  }

		  // set up actRelationships(Act117) between morbidityReport to MorbFormQuestion & MorbFromComment
		  Collection<ObservationVO>  obsColl = proxy.getTheObservationVOCollection();
		  if (obsColl != null)
		  {

			  for (Iterator<ObservationVO>  iter = obsColl.iterator(); iter.hasNext(); )
			  {

				  ObservationVO obsVO = (ObservationVO) iter.next();
				  if ( (obsVO.getTheObservationDT().getObservationUid() != null) &&
						  (morbidityReportObsDT.getObservationUid() != null))
				  {
					  ActRelationshipDT act117 = new ActRelationshipDT();
					  act117.setSourceActUid(obsVO.getTheObservationDT().getObservationUid());
					  act117.setSourceClassCd("OBS");
					  act117.setTargetActUid(morbidityReportObsDT.getObservationUid());
					  act117.setTargetClassCd("OBS");
					  act117.setTypeCd(NEDSSConstants.MORBIDITY_FORM_Q);
					  act117.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
					  act117.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
					  act117.setStatusTime(new java.sql.Timestamp(new Date().getTime()));
					  act117.setItNew(true);
					  act117.setItDirty(false);
					  // add to the collection for the proxy
					  actrelations.add(act117);
					  act117 = null;
				  }
			  }
		  }

		  /******************************************************************
		   * batch entry observations
		   */
		  Collection<Object>  labResultsColl = form.getLabResultsCollection();

		  if (labResultsColl != null)
		  {
			  for (Iterator<Object>  iter = labResultsColl.iterator(); iter.hasNext(); )
			  {
				  BatchEntryHelper helper = (BatchEntryHelper) iter.next();

				  MorbidityUtil morbidityUtil = new MorbidityUtil();
				  boolean deleteFlag = morbidityUtil.findDeleteOrderTest(helper);

				  if (!deleteFlag)
				  {
					  /**
					   * ObservationVO 0 is order test - LAB112
					   */
					  ObservationVO rowVO = helper.getObservationVO_s(0);
					  ObservationDT rowDT = rowVO.getTheObservationDT();
					  rowDT.setObservationUid(new Long(i--));
					  rowDT.setCd("NI");
					  rowDT.setCdDescTxt("No Information Given");
					  rowDT.setCdSystemCd("2.16.840.1.113883"); // changed as per WPD
					  rowDT.setCdSystemDescTxt("Health Level Seven");
					  rowDT.setObsDomainCdSt1("Order");
					  rowDT.setCtrlCdDisplayForm(NEDSSConstants.LAB_REPORT_MORB);
					  rowDT.setStatusTime(new java.sql.Timestamp(new Date().
							  getTime()));
					  rowDT.setStatusCd("D"); // changed from F as per WPD
					  rowDT.setStatusTime(new java.sql.Timestamp(new Date().
							  getTime()));
					  rowDT.setRecordStatusCd(NEDSSConstants.
							  RECORD_STATUS_ACTIVE);
					  rowDT.setRecordStatusTime(new java.sql.Timestamp(new
							  Date().getTime()));

					  Timestamp rptToStateTime = morbidityReportObsDT.getRptToStateTime();
					  rowDT.setRptToStateTime(rptToStateTime);
					  rowDT.setItNew(true);
					  rowDT.setItDirty(false);
					  rowVO.setTheObservationDT(rowDT);
					  rowVO.setItNew(true);
					  rowVO.setItDirty(false);
					  // add to the collection for the proxy
					  observations.add(rowVO);

					  //PAR110 Person(Patient) as Subject of Lab Test Observation
					  // each Ordered Test has a Participation Relationship to the Patient
					  if (personSubjectUid != null)
					  {
						  logger.info("personSubjectUid = " + personSubjectUid);
						  ParticipationDT par110 = new ParticipationDT();
						  par110.setActClassCd(NEDSSConstants.PART_ACT_CLASS_CD);
						  par110.setActUid(rowDT.getObservationUid());
						  par110.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
						  par110.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
						  par110.setSubjectClassCd(NEDSSConstants.PAR110_SUB_CD);
						  par110.setSubjectEntityUid(personSubjectUid);
						  par110.setTypeCd(NEDSSConstants.PAR110_TYP_CD);
						  par110.setTypeDescTxt("Patient Subject");
						  par110.setItNew(true);
						  participations.add(par110);
					  }

					  //  setup act relationship(Act129) of rowObservation( an ordered test) to a morbidity report
					  if ( (rowVO.getTheObservationDT().getObservationUid() != null) &&
							  (morbidityReportObsDT.getObservationUid() != null))
					  {
						  ActRelationshipDT act129 = new ActRelationshipDT();
						  act129.setSourceActUid(rowVO.getTheObservationDT().
								  getObservationUid());
						  act129.setSourceClassCd("OBS");
						  act129.setTargetActUid(morbidityReportObsDT.getObservationUid());
						  act129.setTargetClassCd("OBS");
						  act129.setTypeCd("LabReport");
						  act129.setTypeDescTxt("Laboratory Report");
						  act129.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
						  act129.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
						  act129.setStatusTime(new java.sql.Timestamp(new Date().getTime()));
						  act129.setItNew(true);

						  // add to the collection for the proxy
						  actrelations.add(act129);
					  }

					  //  setup the observation vo's and add to the observation collection
					  ArrayList<ObservationVO> helperColl = new ArrayList<ObservationVO> ();
					  // only want result test (only one of them)
					  ObservationVO resultTest = helper.getObservationVO_s(1);
					  resultTest.getTheObservationDT().setObsDomainCdSt1(NEDSSConstants.
							  RESULTED_TEST_OBS_DOMAIN_CD);
					  // need it in a collection for setObservationForCreate method
					  helperColl.add(resultTest);

					  // sets up the observations in BatchEntryHelps and adds to the observations collection for the proxy
					  i = setObservationForCreate(helperColl, observations, request,i);

					  // Chris additons
					  String cdSystemCdRT = resultTest.getTheObservationDT().
							  getCdSystemCdRT();
					  String labId = request.getParameter("labId");
					  String searchResultRT = resultTest.getTheObservationDT().
							  getSearchResultRT();
					  if (searchResultRT != null && searchResultRT.trim().length() > 0)
					  {
						  resultTest.getTheObservationDT().setCdDescTxt(searchResultRT);
					  }
					  if (cdSystemCdRT != null && cdSystemCdRT.trim().length() > 0)
					  {
						  resultTest.getTheObservationDT().setCdSystemCd(cdSystemCdRT);
						  if (cdSystemCdRT.equalsIgnoreCase("LN"))
						  {
							  resultTest.getTheObservationDT().setCdSystemDescTxt("LOINC");
						  }
					  }
					  else if (labId != null && labId.length() > 0)
					  {
						  resultTest.getTheObservationDT().setCdSystemCd(labId);
					  }
					  String hiddenCd = resultTest.getTheObservationDT().getHiddenCd();
					  if (hiddenCd != null && hiddenCd.trim().length() > 0)
					  {
						  resultTest.getTheObservationDT().setCd(hiddenCd);

					  }

					  CachedDropDownValues cdv = new CachedDropDownValues();

					  ObsValueCodedDT obsValueDT = resultTest.getObsValueCodedDT_s(0);
					  if (resultTest.getTheObservationDT().getCtrlCdUserDefined1().
							  equalsIgnoreCase("Y"))
					  {

						  if (obsValueDT.getDisplayName() != null &&
								  !obsValueDT.getDisplayName().equals(""))
						  {
							  obsValueDT.setCode(obsValueDT.getDisplayName());

							  String temp = cdv.getOrganismListDesc(obsValueDT.getDisplayName());
							  obsValueDT.setDisplayName(temp);
						  }
						  if (obsValueDT.getSearchResultRT() != null &&
								  !obsValueDT.getSearchResultRT().equals(""))
						  {
							  obsValueDT.setCodeSystemCd(obsValueDT.getCdSystemCdRT());
							  obsValueDT.setDisplayName(obsValueDT.getHiddenCd());
							  obsValueDT.setCode(obsValueDT.getSearchResultRT());

						  }
					  }
					  else
					  {
						  if (obsValueDT.getCode() == null ||
								  obsValueDT.getCode().trim().length() == 0)
						  {
							  obsValueDT.setCode("NI");
							  resultTest.getTheObservationDT().setCtrlCdUserDefined1("N");
						  }
						  else
						  {

							  String temp = cdv.getCodedResultDesc(obsValueDT.getCode());
							  obsValueDT.setDisplayName(temp);
							  resultTest.getTheObservationDT().setCtrlCdUserDefined1("N");
						  }

					  }

					  // setup the act relationships in each BatchEntryHelp
					  if (helperColl != null)
					  {

						  for (Iterator<ObservationVO>  helperIter = helperColl.iterator();
								  helperIter.hasNext(); )
						  {

							  // item to row act relationship (Act108)
							  ObservationVO helperObsVO = (ObservationVO) helperIter.next();

							  Collection<Object>  numericColl = helperObsVO.
									  getTheObsValueNumericDTCollection();
							  if (numericColl != null)
							  {

								  ObsValueNumericDT obsValueNumericDT = null;
								  // Iterate through the collection
								  for (Iterator<Object> numericCollIter = numericColl.iterator();
										  numericCollIter.hasNext(); )
								  {

									  obsValueNumericDT = (ObsValueNumericDT) numericCollIter.next();
									  ObservationUtil obsUtil = new ObservationUtil();
									  if (obsValueNumericDT.getNumericValue() != null)
									  {
										  obsValueNumericDT = obsUtil.parseNumericResult(
												  obsValueNumericDT);
									  }
								  }
							  }

							  ObsValueTxtDT obsValueTxtDT = helperObsVO.getObsValueTxtDT_s(1);
							  if (obsValueTxtDT != null)
							  {
								  // Result Comments Text Area
								  obsValueTxtDT.setTxtTypeCd("N");
							  }

							  if ( (helperObsVO.getTheObservationDT().getObservationUid() != null) &&
									  (rowVO.getTheObservationDT().getObservationUid() != null))
							  {
								  ActRelationshipDT act108 = new ActRelationshipDT();

								  act108.setSourceActUid(helperObsVO.getTheObservationDT().
										  getObservationUid());
								  act108.setSourceClassCd("OBS");
								  act108.setTargetActUid(rowVO.getTheObservationDT().
										  getObservationUid());
								  act108.setTargetClassCd("OBS");
								  act108.setTypeCd("COMP");
								  act108.setTypeDescTxt("Has Component");
								  act108.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
								  act108.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
								  act108.setStatusTime(new java.sql.Timestamp(new Date().getTime()));
								  act108.setItNew(true);
								  // add to the collection for the proxy
								  actrelations.add(act108);
							  }
						  }
					  }
				  }
			  }
		  }

		  /******************************************************************
		   * batch entry treatments
		   */
		  Collection<Object>  treatmentsColl = form.getTreatmentCollection();

		  if (treatmentsColl != null)
		  {
			  for (Iterator<Object>  iter = treatmentsColl.iterator(); iter.hasNext(); )
			  {
				  BatchEntryHelper helper = (BatchEntryHelper) iter.next();

				  MorbidityUtil morbidityUtil = new MorbidityUtil();
				  boolean deleteFlag = morbidityUtil.findDeleteOrderTest(helper);

				  if (!deleteFlag)
				  {

					  Collection<Object>  treatmentHelperColl = helper.getTreatmentVOCollection();
					  i = setTreatmentsForCreate(treatmentHelperColl, treatments, i, form);

					  if (treatmentHelperColl != null)
					  {
						  for (Iterator<Object>  helperIter = treatmentHelperColl.iterator();
								  helperIter.hasNext(); )
						  {
							  //ActRelationship between treatment and morbreport
							  TreatmentVO treatmenthHelperObsVO = (TreatmentVO) helperIter.next();

							  if (treatmenthHelperObsVO != null)
							  {
								  treatmenthHelperObsVO.getTheTreatmentDT().setTreatmentUid(new
										  Long(i--));
								  treatmenthHelperObsVO.setItNew(true);
								  treatmenthHelperObsVO.setItDirty(false);
								  //treatments.add(treatmenthHelperObsVO);
								  if (!treatmenthHelperObsVO.getTheTreatmentDT().getStatusCd().
										  trim().equals("I"))
								  {

									  ActRelationshipDT act131 = new ActRelationshipDT();
									  act131.setSourceActUid(treatmenthHelperObsVO.
											  getTheTreatmentDT().
											  getTreatmentUid());
									  act131.setSourceClassCd("TRMT");
									  act131.setTargetActUid(morbidityReportObsDT.getObservationUid());
									  act131.setTargetClassCd("OBS");
									  act131.setTypeCd("TreatmentToMorb");
									  act131.setTypeDescTxt("Treatment To Morbidity Report");
									  act131.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
									  act131.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
									  act131.setStatusTime(new java.sql.Timestamp(new Date().
											  getTime()));
									  act131.setItNew(true);
									  // add to the collection for the proxy
									  actrelations.add(act131);

									  ParticipationDT par100 = new ParticipationDT();
									  par100.setActClassCd("TRMT");
									  par100.setActUid(treatmenthHelperObsVO.getTheTreatmentDT().
											  getTreatmentUid());
									  par100.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
									  par100.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
									  par100.setSubjectClassCd(NEDSSConstants.PAR110_SUB_CD);
									  par100.setSubjectEntityUid(personSubjectUid);
									  par100.setTypeCd("SubjOfTrmt");
									  par100.setTypeDescTxt("Treatment Subject");
									  par100.setItNew(true);
									  participations.add(par100);

								  }
							  }
						  }
					  }
				  }
			  }
		  }

		  try
		  {
			  Long mprUid = (Long) NBSContext.retrieve(session, "DSPatientPersonUID");
			  MorbidityUtil morbidityUtil = new MorbidityUtil();
			  morbidityUtil.setPatientRevisionForCreate(mprUid, personSubjectUid,
					  patientLocalId, proxy, form,
					  request);
		  }
		  catch (NullPointerException npe)
		  {
			  logger.fatal("cannot retrieve DSPatientPersonUID from object store");
		  }

		  /*****************************************************************
		   * set the collections
		   */
		  proxy.setTheObservationVOCollection(observations);
		  proxy.setTheParticipationDTCollection(participations);
		  proxy.setTheActRelationshipDTCollection(actrelations);
		  proxy.setTheTreatmentVOCollection(treatments);
		  proxy.setTheRoleDTCollection(roles);

		  //LDF
		  CommonLabUtil commonLabUtil = new CommonLabUtil();
		  commonLabUtil.submitMorbLDF(proxy,form, true, request);
		  commonLabUtil.submitPatientLDFMorb(form.getPatient(),form, true, request);

		  proxy.setItNew(true);

		  observations = null;
		  actrelations = null;
		  roles = null;
		  participations = null;
		  treatments = null;
		  session.setAttribute("personLocalID", null);
	  } catch (Exception ex) {
		  logger.error("Error occurred in Add Morbidity Create Handler: "+ex.getMessage());
		  ex.printStackTrace();
	  }
    return proxy;
  }

  /**
   * sets negative tempId and newFlag to true for observations
   * @param inColl -- the Collection<Object>  passed in to set tempID and flags
       * @param outColl -- the collection came out after setting the tempIDs and flags
   * @param tempID -- the temperary UID for Main observation
   * @return tempID -- the integer value of temp UID
   */

  private int setObservationForCreate(Collection<ObservationVO>  inColl, Collection<ObservationVO>  outColl,
                                      HttpServletRequest request, int tempID)
  {
    if (inColl != null)
    {
     Iterator<ObservationVO>  itor = inColl.iterator();

      while (itor.hasNext())
      {
        ObservationVO obsVO = (ObservationVO) itor.next();
        if (obsVO != null)
        {
          // check to see if question not answered
          boolean hasAnswer = super.checkForEmptyObs(obsVO, request);

          if (hasAnswer)
          {
            ObservationUtils.trimEmptyObsValueCodedDTCollections(obsVO);

            ObservationDT obsDT = obsVO.getTheObservationDT();

            if (obsVO.getTheObsValueCodedDTCollection() != null)
            {
             Iterator<Object>  it = obsVO.getTheObsValueCodedDTCollection().iterator();
              while (it.hasNext())
              {
                ObsValueCodedDT obsValDT = (ObsValueCodedDT) it.next();
                obsValDT.setItNew(true);
                obsValDT.setItDirty(false);
              }
            }

            if (obsVO.getTheObsValueDateDTCollection() != null)
            {
             Iterator<Object>  it = obsVO.getTheObsValueDateDTCollection().iterator();
              while (it.hasNext())
              {
                ObsValueDateDT obsValDT = (ObsValueDateDT) it.next();
                obsValDT.setItNew(true);
                obsValDT.setItDirty(false);
              }
            }

            if (obsVO.getTheObsValueNumericDTCollection() != null)
            {
             Iterator<Object>  it = obsVO.getTheObsValueNumericDTCollection().iterator();

              while (it.hasNext())
              {
                ObsValueNumericDT obsValDT = (ObsValueNumericDT) it.next();
                obsValDT.setItNew(true);
                obsValDT.setItDirty(false);
              }
            }

            if (obsVO.getTheObsValueTxtDTCollection() != null)
            {
             Iterator<Object>  it = obsVO.getTheObsValueTxtDTCollection().iterator();

              while (it.hasNext())
              {
                ObsValueTxtDT obsValDT = (ObsValueTxtDT) it.next();
                obsValDT.setItNew(true);
                obsValDT.setItDirty(false);
              }
            }

            obsDT.setStatusTime(new java.sql.Timestamp(new Date().getTime()));
            obsDT.setStatusCd("D");

            obsDT.setObservationUid(new Long(tempID--));
            obsDT.setCdSystemCd("NBS");
            obsDT.setCdSystemDescTxt("NEDSS Base System");
            obsDT.setCdVersion("1.0");
            obsDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
            obsDT.setRecordStatusTime(new java.sql.Timestamp(new Date().getTime()));
            obsDT.setItNew(true);
            obsDT.setItDirty(false);
            obsVO.setItNew(true);
            obsVO.setItDirty(false);
            outColl.add(obsVO);
          } //end of while loop
        } // end of if (obsColl != null)
      }
    }

    return tempID;
  }

//---------------Treatment--------------------------------------------------
  /**
   * sets negative tempId and newFlag to true for treatments
   * @param inColl -- the Collection<Object>  passed in to set tempID and flags
       * @param outColl -- the collection came out after setting the tempIDs and flags
   * @param tempID -- the temperary UID for Main observation
   * @return tempID -- the integer value of temp UID
   */
  private int setTreatmentsForCreate(Collection<Object>  inColl, Collection<Object>  outColl,
                                     int tempID, MorbidityForm form)
  {
    if (inColl != null)
    {
      new MorbidityUtil().setPredefinedTreatment(inColl);
     Iterator<Object>  itor = inColl.iterator();
      while (itor.hasNext())
      {
        TreatmentVO treatmentVO = (TreatmentVO) itor.next();
        TreatmentDT treatmentDT = treatmentVO.getTheTreatmentDT();
        Timestamp effectiveFromTime = null;
        if (treatmentVO.getTheTreatmentAdministeredDTCollection() != null)
        {
         Iterator<Object>  it = treatmentVO.getTheTreatmentAdministeredDTCollection().
              iterator();

          while (it.hasNext())
          {
            TreatmentAdministeredDT treatmentAdministeredDT = (
                TreatmentAdministeredDT) it.next();
            effectiveFromTime = treatmentAdministeredDT.getEffectiveFromTime();
            treatmentAdministeredDT.setItNew(true);
            treatmentAdministeredDT.setItDirty(false);
            break;
          }
        }

        if (treatmentVO.getTheTreatmentProcedureDTCollection() != null)
        {
         Iterator<Object>  it = treatmentVO.getTheTreatmentProcedureDTCollection().
              iterator();

          while (it.hasNext())
          {
            TreatmentProcedureDT treatmentProcedureDT = (TreatmentProcedureDT)
                it.next();
            treatmentProcedureDT.setItNew(true);
            treatmentProcedureDT.setItDirty(false);
          }
        }

        if (effectiveFromTime != null)
        {
          treatmentDT.setActivityToTime(effectiveFromTime);
        }

        treatmentDT.setStatusTime(new java.sql.Timestamp(new Date().getTime()));
        if (!treatmentDT.getStatusCd().trim().equals("I"))
        {
          treatmentDT.setStatusCd("D");
        }
        treatmentDT.setRecordStatusTime(new java.sql.Timestamp(new Date().
            getTime()));
        treatmentDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);

        treatmentDT.setProgAreaCd(form.getMorbidityReport().
                                  getTheObservationDT().getProgAreaCd());
        treatmentDT.setTreatmentUid(new Long(tempID--));
        PreDefinedTreatmentDT preDefinedTreatmentDT = null;
        CachedDropDownValues cachedDropDownValues = new CachedDropDownValues();
        preDefinedTreatmentDT = cachedDropDownValues.getPreDefinedTreatmentDT(
            treatmentVO.getTheTreatmentDT().getCd());
        if (treatmentVO.getTheTreatmentDT().getCd() != null &&
            !treatmentVO.getTheTreatmentDT().getCd().equals("OTH"))
        {
          treatmentDT.setClassCd("TA");
          treatmentVO.getTheTreatmentDT().setCdDescTxt(treatmentVO.
              getTheTreatmentDT().getCd() == null ? "" : " " +
              cachedDropDownValues.getCachedTreatmentDescription(treatmentVO.
              getTheTreatmentDT().getCd()));
          treatmentVO.getTheTreatmentDT().setCdSystemCd(preDefinedTreatmentDT.
              getCodeSystemCd());
          treatmentVO.getTheTreatmentDT().setCdSystemDescTxt(
              preDefinedTreatmentDT.getCdSystemDescTxt());
        }
        else
        {
          treatmentVO.getTheTreatmentDT().setCdSystemCd("NBS");
          treatmentVO.getTheTreatmentDT().setCdSystemDescTxt(
              "NEDSS Base System");
        }

        treatmentDT.setItNew(true);
        treatmentDT.setItDirty(false);
        treatmentVO.setItNew(true);
        treatmentVO.setItDirty(false);
        if (!treatmentDT.getStatusCd().trim().equals("I"))
        {
          outColl.add(treatmentVO);
        }
      } //end of while loop
    } // end of if (obsColl != null)
    return tempID;
  } // end of treatment

//-----------------------------------------------------------------

  /** Send the proxy to the backend for persistance
   * @param proxy -- The fully populated Value Object
   * @request session -- The HTTP Session object
   * @return result -- The UID for the persisted data or return null if not persisted
   * @throws NEDSSAppConcurrentDataException
   * @throws RemoteException
   * @throws EJBException
   */
  private Collection<?>  sendProxyToEJB(MorbidityProxyVO proxy,
                                    HttpSession session, String sCurrTask, String processingDecision) throws
      NEDSSAppConcurrentDataException, java.rmi.RemoteException,
      javax.ejb.EJBException, Exception
  {

    MainSessionCommand msCommand = null;

    //try {
    String sBeanJndiName = JNDINames.OBSERVATION_PROXY_EJB;
    String sMethod = null;
    MainSessionHolder holder = new MainSessionHolder();
    ArrayList<?> resultUIDArr = new ArrayList<Object> ();

    msCommand = holder.getMainSessionCommand(session);
    Long coinfectionInvestigationUid = null;
    String type = null;
    try{
    	if(processingDecision==null || !processingDecision.equals(NEDSSConstants.NONE)){
    	coinfectionInvestigationUid = ((CoinfectionSummaryVO)NBSContext.retrieve(session,
    	          NBSConstantUtil.DSCoinfectionInvSummVO)).getPublicHealthCaseUid();
    	type = (String)NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationType);
    	}
    }catch(Exception ex){
    	logger.debug("No co-infection context for current task: "+sCurrTask);
    }
    if (sCurrTask != null &&
        (sCurrTask.equalsIgnoreCase("AddObservationMorb4")
         || sCurrTask.equalsIgnoreCase("AddObservationMorb3")
         || sCurrTask.equalsIgnoreCase("AddObservationMorb1")))
    {
      String sInvestigationUid = (String) NBSContext.retrieve(session,
          NBSConstantUtil.DSInvestigationUid);
      Long investigationUid = new Long(sInvestigationUid);
      sMethod = "setMorbidityProxyWithAutoAssoc";
      Object[] oParams =
          {
          proxy, investigationUid};
      resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
    }
    //check if the morbidity report will be associated to existing investigation for STD/HIV conditions
    else if(type!=null && type.equals(NEDSSConstants.ASSOCIATE)){
    	sMethod = "setMorbidityProxyWithAutoAssoc";
        Object[] oParams =
            {
            proxy, coinfectionInvestigationUid};
        resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
    }
    
    else
    {
      sMethod = "setMorbidityProxy";
      Object[] oParams =
          {
          proxy};
      resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
    }
    for (int i = 0; i < resultUIDArr.size(); i++)
    {
      logger.info("Result " + i + " is: " + resultUIDArr.get(i));
    }

    if ( (resultUIDArr != null) && (resultUIDArr.size() > 0))
    {

      Collection<?>  result = (Collection<?>) resultUIDArr.get(0);
      return result;
    }

    return null;
  } //sendProxyToEJB

}