package gov.cdc.nedss.webapp.nbs.action.observation.morbidityreport;

import gov.cdc.nedss.util.*;
import gov.cdc.nedss.act.observation.vo.*;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.systemservice.nbscontext.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;
import gov.cdc.nedss.page.ejb.pageproxyejb.dt.NBSAttachmentDT;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.*;
import gov.cdc.nedss.association.dt.*;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.act.observation.dt.*;
import gov.cdc.nedss.entity.person.vo.*;
import gov.cdc.nedss.act.attachment.dt.AttachmentDT;
import gov.cdc.nedss.act.attachment.vo.AttachmentVO;
import gov.cdc.nedss.webapp.nbs.util.BatchEntryHelper;
import gov.cdc.nedss.webapp.nbs.action.util.*;
import gov.cdc.nedss.act.treatment.vo.TreatmentVO;
import gov.cdc.nedss.act.treatment.dt.*;
import gov.cdc.nedss.systemservice.util.*;

import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;
import java.nio.file.Files;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.swing.JOptionPane;

import org.apache.struts.action.*;
import org.apache.struts.upload.FormFile;

import gov.cdc.nedss.webapp.nbs.action.observation.morbidityreport.util.
    MorbidityUtil;

import java.sql.Timestamp;

import gov.cdc.nedss.webapp.nbs.action.observation.labreport.CommonLabUtil;
import gov.cdc.nedss.webapp.nbs.form.morbidity.MorbidityForm;
import gov.cdc.nedss.systemservice.nbssecurity.ProgramAreaJurisdictionUtil;


/**
 * Title:        MorbiditySubmit.java
 * Description:	This is a action class for the struts implementation
 * Copyright:	Copyright (c) 2001
 * Company: 	Computer Sciences Corporation
 * @author	NEDSS Development Team
 * @version	1.0
 */

public class MorbReportSubmit
    extends CommonAction
{

  protected static NedssUtils nedssUtils = null;

  private String sBeanJndiName = "";
  private String sMethod = "";
  private Object[] oParams = null;
  private String userID = "";

  //For logging
  static final LogUtils logger = new LogUtils(MorbReportSubmit.class.getName());

  public MorbReportSubmit()
  {
  }

  public ActionForward execute(ActionMapping mapping, ActionForm aForm,
                               HttpServletRequest request,
                               HttpServletResponse response) throws IOException,
      ServletException
  {
	  String contextAction = "";
	  try {
		  HttpSession session = request.getSession();

		  //get contextAction and pass to forward
		  contextAction = request.getParameter("ContextAction");

		  String sCurrTask = NBSContext.getCurrentTask(session);

		  if (contextAction.equalsIgnoreCase(NBSConstantUtil.CANCEL))
		  {
			  return (mapping.findForward(contextAction));
		  }

		  NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session.getAttribute(
				  "NBSSecurityObject");

		  MorbidityForm morbidityForm = (MorbidityForm) aForm;

		  MorbidityProxyVO proxy = createHandler(morbidityForm, nbsSecurityObj,
				  session, request);

		 /* Boolean attachmentErrors = isAttachmentErrors(morbidityForm,session, request);
		  if(attachmentErrors)
			  return (mapping.findForward(contextAction));
		 */ Long obsUid = null;
		  Long mprUID = null;
		  String obsLocalUid = null;
		  String patientLocalId = null;

		  try
		  {
			  ArrayList<Object> returnedAr = sendProxyToEJB(proxy, session);
			  obsUid = (Long) returnedAr.get(0);
			  mprUID = (Long) returnedAr.get(1);
			  obsLocalUid = (String) returnedAr.get(2);
			  patientLocalId = (String) returnedAr.get(3);
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
		  
		  request.setAttribute("morbidityReportUid", obsUid);
		  MorbidityUtil morbUtil = new MorbidityUtil();
		  
		  morbUtil.createAttachment(morbidityForm, nbsSecurityObj,session, request);
		  String retainPatient = request.getParameter("retainPatient");
		  PersonVO personVO = null;

		  if (retainPatient != null && retainPatient.equalsIgnoreCase("T") && mprUID != null)
		  {
			  personVO = morbidityForm.getPatient();

			  // use the new API to retrieve custom field collection
			  // to handle multiselect fields (xz 01/11/2005)
			  Collection<Object>  coll = extractLdfDataCollection(morbidityForm, request);
			  if (coll!= null)
			  {
				  personVO.setTheStateDefinedFieldDataDTCollection(coll);
			  }

			  personVO.getThePersonDT().setPersonParentUid(mprUID);
			  personVO.getThePersonDT().setLocalId(patientLocalId);
			  if (personVO != null)
			  {
				  NBSContext.store(session, "DSPatientPersonVO", personVO);
			  }
		  }

		  morbidityForm.reset();
		  proxy = null;
	  }catch (Exception e) {
		  logger.error("Exception in Morb Submit: " + e.getMessage());
		  e.printStackTrace();
		  throw new ServletException("Error occurred in Morbidity Submit : "+e.getMessage());
	  } 
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
    // observation create initial uid

    ArrayList<ObservationVO> observations = new ArrayList<ObservationVO> ();
    ArrayList<Object> actrelations = new ArrayList<Object> ();
    ArrayList<Object> roles = new ArrayList<Object> ();
    ArrayList<Object> participations = new ArrayList<Object> ();
    ArrayList<Object> organizations = new ArrayList<Object> ();
    ArrayList<Object> persons = new ArrayList<Object> ();
    ArrayList<Object> treatments = new ArrayList<Object> ();
    ArrayList<Object> attachments = new ArrayList<Object> ();
    

    int i = -1;

    // is this an EXTERNAL user
    UserProfile userprofile = securityObj.getTheUserProfile();
    User user = userprofile.getTheUser();
    String userType = user.getUserType();
    Long userReportingFacilityUid = user.getReportingFacilityUid();
    CommonLabUtil commonutil = new CommonLabUtil();
    boolean reportExteranlUser = false;
    if (userType != null)
    {
      reportExteranlUser = userType.equalsIgnoreCase(NEDSSConstants.
          SEC_USERTYPE_EXTERNAL);
    }


    // setup the observations(MorbFormQuestion & MorbFromComment) from morbidityForm
    MorbidityProxyVO proxy = form.getMorbidityProxy();
    i = setObservationForCreate(proxy.getTheObservationVOCollection(),
                                observations, request, i, reportExteranlUser);

    // Build the morbidityReport (a dummy observation)
    ObservationVO morbidityReportObsVO = form.getMorbidityReport();
    ObservationDT morbidityReportObsDT = morbidityReportObsVO.
        getTheObservationDT();
    morbidityReportObsDT.setObservationUid(new Long(i--));
    morbidityReportObsDT.setObsDomainCdSt1(NEDSSConstants.
                                           ORDERED_TEST_OBS_DOMAIN_CD);
    morbidityReportObsDT.setCtrlCdDisplayForm(NEDSSConstants.MORBIDITY_CODE);
    morbidityReportObsDT.setCdDescTxt("Condition");
    morbidityReportObsDT.setCdSystemCd("NBS");
    morbidityReportObsDT.setCdSystemDescTxt("NEDSS Base System");
    morbidityReportObsDT.setCdVersion("1.0");
    if (morbidityReportObsDT.getJurisdictionCd() == null ||
        morbidityReportObsDT.getJurisdictionCd().trim().equalsIgnoreCase("NONE"))
    {
      morbidityReportObsDT.setJurisdictionCd(ProgramAreaJurisdictionUtil.
                                             JURISDICTION_NONE);

    }
    if (reportExteranlUser)
    {
      morbidityReportObsDT.setElectronicInd("E");
      morbidityReportObsDT.setSharedInd("T");
      morbidityReportObsDT.setRptToStateTime(new java.sql.Timestamp(new Date().
          getTime()));
    }
    else
      morbidityReportObsDT.setElectronicInd(NEDSSConstants.ELECTRONIC_IND);
    morbidityReportObsDT.setItNew(true);
    morbidityReportObsDT.setItDirty(false);
    morbidityReportObsVO.setItNew(true);
    morbidityReportObsVO.setItDirty(false);
    observations.add(morbidityReportObsVO);

    //get all the Entity Uids from request object for build relationships
    Long personSubjectUid = null;
    Long personReporterUid = null;
    Long personPhysicianUid = null;
    Long organizationReportingUid = null;
    Long organizationHospitalUid = null;

    // Patient
    String sPatient = request.getParameter("Patient.personUid");
    if (sPatient != null && sPatient.length() > 0)
    {
      personSubjectUid = new Long(sPatient);

      // Reporter
    }
    String sReporter = request.getParameter("Prov-entity.reporterUID");
    if (sReporter != null && sReporter.length() > 0)
    {
      personReporterUid = new Long(sReporter);

      // Provider
    }
    String sProvider = request.getParameter("Prov-entity.providerUID");
    if (sProvider != null && sProvider.length() > 0)
    {
      personPhysicianUid = new Long(sProvider);

      // Reporting Facility
    }
    String sReportingFacility = request.getParameter(
        "Org-ReportingOrganizationUID");
    if (sReportingFacility != null && sReportingFacility.length() > 0)
    {
      organizationReportingUid = new Long(sReportingFacility);
    }
    else if (reportExteranlUser)
    {
      organizationReportingUid = userReportingFacilityUid;
    }

    // Hospital
    String sHospital = request.getParameter("Org-HospitalUID");
    if (sHospital != null && sHospital.length() > 0)
    {
      organizationHospitalUid = new Long(sHospital);

    }

    CommonLabUtil labUtil = new CommonLabUtil();
    i = labUtil.setPatientLabMorbForCreate(personSubjectUid, persons,
                                           form.getPatient(), request, i);
    personSubjectUid = form.getPatient().getThePersonDT().getPersonUid();

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

      for (Iterator<ObservationVO> iter = obsColl.iterator(); iter.hasNext(); )
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
      for (Iterator<Object> iter = labResultsColl.iterator(); iter.hasNext(); )
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
//        ObservationUtils.trimEmptyObsValueCodedDTCollections(rowVO);
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
          resultTest.getTheObservationDT().setCdDescTxt(resultTest.getTheObservationDT().getSearchResultRT());
          // need it in a collection for setObservationForCreate method
          helperColl.add(resultTest);

          // sets up the observations in BatchEntryHelps and adds to the observations collection for the proxy
          i = setObservationForCreate(helperColl, observations, request, i,
                                      reportExteranlUser);

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

            for (Iterator<ObservationVO> helperIter = helperColl.iterator();
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
      for (Iterator<Object> iter = treatmentsColl.iterator(); iter.hasNext(); )
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

            for (Iterator<Object> helperIter = treatmentHelperColl.iterator();
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
    
    

    
    // retainPatient

    /*****************************************************************
     * set the collections
     */
    proxy.setTheObservationVOCollection(observations);
    proxy.setTheParticipationDTCollection(participations);
    proxy.setTheActRelationshipDTCollection(actrelations);
    proxy.setTheTreatmentVOCollection(treatments);
    proxy.setThePersonVOCollection(persons);

    //LDF
   CommonLabUtil commonLabUtil = new CommonLabUtil();
   commonLabUtil.submitMorbLDF(proxy,form, true, request);
   commonLabUtil.submitPatientLDFMorb(form.getPatient(),form, true, request);

    proxy.setItNew(true);

    observations = null;
    actrelations = null;
    roles = null;
    participations = null;
    organizations = null;
    treatments = null;
    persons = null;

    return proxy;
  }

  /**
   * sets negative tempId and newFlag to true for observations
   * @param inColl -- the Collection<Object>  passed in to set tempID and flags
       * @param outColl -- the collection came out after setting the tempIDs and flags
   * @param tempID -- the temperary UID for Main observation
   * @return tempID -- the integer value of temp UID
   */

  private int setObservationForCreate(Collection<ObservationVO>  inColl, Collection<ObservationVO>  outColl, HttpServletRequest request,
                                      int tempID, boolean reportExteranlUser)
  {
    if (inColl != null)
    {
     Iterator<ObservationVO>  itor = inColl.iterator();

      while (itor.hasNext())
      {
        ObservationVO obsVO = (ObservationVO) itor.next();
        if (obsVO != null)
        {

          boolean hasAnswer = checkForEmptyObs(obsVO, request);

          ObservationUtils.trimEmptyObsValueCodedDTCollections(obsVO);

          ObservationDT obsDT = obsVO.getTheObservationDT();

          // if external user set to Web Entry
          if (obsDT != null && obsDT.getCd() != null)
          {
            if (reportExteranlUser && obsDT.getCd().equalsIgnoreCase("MRB161"))
            {
              ObsValueCodedDT obsValDT = new ObsValueCodedDT();
              obsValDT.setCode("Web");
              obsValDT.setItNew(true);
              obsValDT.setItDirty(false);
              ArrayList<Object> ar = new ArrayList<Object> ();
              ar.add(obsValDT);
              obsVO.setTheObsValueCodedDTCollection(ar);
            }
          }

          if (hasAnswer)
          {

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
                logger.debug(
                    "date CD - " + obsDT.getCd() + "     date  uid - " +
                    obsValDT.getObservationUid());
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

            String strCode = obsDT.getCd();

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
            ObservationUtils.trimEmptyObsValueCodedDTCollections(obsVO);
            outColl.add(obsVO);
          } //end of while loop
        }
      }
    } // end of if (obsColl != null)

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
        treatmentDT.setProgAreaCd(form.getMorbidityReport().getTheObservationDT().
                                  getProgAreaCd());
        treatmentDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);

        String strCode = treatmentDT.getCd();

        treatmentDT.setTreatmentUid(new Long(tempID--));
        PreDefinedTreatmentDT preDefinedTreatmentDT = null;
        CachedDropDownValues cachedDropDownValues = new CachedDropDownValues();
        preDefinedTreatmentDT = cachedDropDownValues.getPreDefinedTreatmentDT(
            treatmentVO.getTheTreatmentDT().getCd());
        if (treatmentVO.getTheTreatmentDT().getCd() != null &&
            !treatmentVO.getTheTreatmentDT().getCd().equals("OTH"))
        {
          treatmentVO.getTheTreatmentDT().setCdDescTxt(treatmentVO.
              getTheTreatmentDT().getCd() == null ? "" : " " +
              cachedDropDownValues.getCachedTreatmentDescription(treatmentVO.
              getTheTreatmentDT().getCd()));
          treatmentVO.getTheTreatmentDT().setCdSystemCd(preDefinedTreatmentDT.
              getCodeSystemCd());
          treatmentVO.getTheTreatmentDT().setCdSystemDescTxt(
              preDefinedTreatmentDT.getCdSystemDescTxt());
          treatmentDT.setClassCd("TA");
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
  private ArrayList<Object> sendProxyToEJB(MorbidityProxyVO proxy, HttpSession session) throws
      NEDSSAppConcurrentDataException, java.rmi.RemoteException,
      javax.ejb.EJBException, Exception
  {

    MainSessionCommand msCommand = null;

    //try {
    String sBeanJndiName = JNDINames.OBSERVATION_PROXY_EJB;
    String sMethod = null;
    Long investigationUid = null;
    MainSessionHolder holder = new MainSessionHolder();
    ArrayList<?> resultUIDArr = new ArrayList<Object> ();

    sMethod = "setMorbidityProxy";
    Object[] oParams =
        {
        proxy};
    msCommand = holder.getMainSessionCommand(session);
    resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);

    for (int i = 0; i < resultUIDArr.size(); i++)
    {
      logger.info("Result " + i + " is: " + resultUIDArr.get(i));
    }

    if ( (resultUIDArr != null) && (resultUIDArr.size() > 0))
    {
      logger.debug("store observation worked!!! observationUID = " +
                   resultUIDArr.get(0));

      return (ArrayList<Object> ) resultUIDArr.get(0);
    }

    return null;
  } //sendProxyToEJB

  private String findMasterPatientRecord(Long mprUId, HttpSession session,
                                         NBSSecurityObj secObj)
  {

    PersonVO personVO = null;
    MainSessionCommand msCommand = null;

    try
    {

      String sBeanJndiName = JNDINames.ENTITY_PROXY_EJB;
      String sMethod = "getMPR";
      Object[] oParams = new Object[]
          {
          mprUId};
      MainSessionHolder holder = new MainSessionHolder();
      msCommand = holder.getMainSessionCommand(session);

      ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
      personVO = (PersonVO) arr.get(0);
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
      if (session == null)
      {
        logger.error("Error: no session, please login");
      }

      logger.fatal("personVO: ", ex);
    }

    return personVO.getThePersonDT().getLocalId();
  } //findMasterPatientRecord

}