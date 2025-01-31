package gov.cdc.nedss.webapp.nbs.action.observation.labreport;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.Date;

import javax.servlet.*;
import javax.servlet.http.*;

import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

import org.apache.struts.action.*;

import gov.cdc.nedss.act.actid.dt.*;
import gov.cdc.nedss.act.observation.dt.*;
import gov.cdc.nedss.act.observation.vo.*;
import gov.cdc.nedss.association.dt.*;
import gov.cdc.nedss.entity.entityid.dt.*;
import gov.cdc.nedss.entity.material.dt.*;
import gov.cdc.nedss.entity.person.dt.*;
import gov.cdc.nedss.entity.person.vo.*;
import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.locator.dt.*;
import gov.cdc.nedss.proxy.ejb.entityproxyejb.bean.EntityProxyHome;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.bean.ObservationProxyHome;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.*;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.systemservice.nbscontext.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.systemservice.util.*;
import gov.cdc.nedss.webapp.nbs.action.person.util.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.webapp.nbs.action.util.*;
import gov.cdc.nedss.webapp.nbs.form.observation.*;
import gov.cdc.nedss.entity.material.vo.*;
import gov.cdc.nedss.webapp.nbs.servlet.NedssCodeLookupServlet;
import gov.cdc.nedss.systemservice.ejb.srtcachemanagerejb.dt.SRTCode;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.action.observation.common.LabReportFieldMappingBuilder;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.LabResultProxyVO;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class LabReportSubmit extends Action{

  //For logging
  static final LogUtils logger = new LogUtils(LabReportSubmit.class.getName());
  static String strLock = "lock";

  public LabReportSubmit() {
  }


    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response)
                          throws IOException, ServletException
    {
        HttpSession session = request.getSession(false);

        ObservationForm obsForm =(ObservationForm)form;
        obsForm.setOldProxy(null);
        obsForm.setOldResultedTestVOCollection(null);
        
        
        
        String contextAction = request.getParameter("ContextAction");

        LabResultProxyVO labResultProxyVO;
        NBSSecurityObj nbsSecurityObj = (NBSSecurityObj)session.getAttribute("NBSSecurityObject");
        String sCurrTask = NBSContext.getCurrentTask(session);
        try
        {
          if (contextAction.equalsIgnoreCase(NBSConstantUtil.CANCEL))
          {
            NBSContext.store(session, "DSFileTab", "3");
            return (mapping.findForward(contextAction));
          }
          else
          {
        	  String reportingSourceLabClia = (String) request.getParameter("labId");
        	  //String markAsReviewReason = (String) request.getParameter("markAsReviewReason");
        	  obsForm.getProxy().setLabClia(reportingSourceLabClia);
        	  obsForm.getProxy().setManualLab(true);
              LabResultProxyVO labResultProxy = obsForm.getProxy();
              
              ObservationVO mainObservation = findObservationByCode(labResultProxy.getTheObservationVOCollection(), "LAB112");
              String processingDecision = mainObservation.getTheObservationDT().getProcessingDecisionCd();
              mainObservation.getTheObservationDT().setProcessingDecisionCd(null);
              
            if(sCurrTask != null && sCurrTask.equals("AddObservationLab2"))
            {

               String dSInvestigationProgramArea = mainObservation.getTheObservationDT().getProgAreaCd();
               String dSInvestigationJurisdiction = mainObservation.getTheObservationDT().getJurisdictionCd();

               NBSContext.store(session, NBSConstantUtil.DSJurisdiction, dSInvestigationJurisdiction);
               NBSContext.store(session, "DSInvestigationJurisdiction", dSInvestigationJurisdiction);
               NBSContext.store(session,NBSConstantUtil.DSInvestigationProgramArea, dSInvestigationProgramArea);
               NBSContext.store(session, NBSConstantUtil.DSProgramArea, dSInvestigationProgramArea);
               NBSContext.store(session, "DSObservationTypeCd",
                         NEDSSConstants.LABRESULT_CODE);
               request.setAttribute("programAreaCd1",dSInvestigationProgramArea);
               request.setAttribute("LabReport","LabReport");
            }
            labResultProxyVO = this.createHandler(mapping, form, request, session,
                                                  nbsSecurityObj,contextAction);


            Long uidMRP = null;
            ObservationForm aform = (ObservationForm)form;
            PersonVO personVO = aform.getPatient();
            // set up the ldf collection
            try
            {
               Map<?,?> mprUid = (Map<?,?>)sendProxyToEJB(labResultProxyVO, session, sCurrTask);
              uidMRP = (Long)mprUid.get(NEDSSConstants.SETLAB_RETURN_MPR_UID);
              Long obsUid = (Long)mprUid.get(NEDSSConstants.SETLAB_RETURN_OBS_UID);
              NBSContext.store(session,"DSObservationUID",obsUid);

              if(personVO != null){
                personVO.getThePersonDT().setPersonParentUid(uidMRP);
                //this.findMasterPatientRecord(personVO,uidMRP,session,nbsSecurityObj);
                personVO.getThePersonDT().setLocalId(mprUid.get(NEDSSConstants.SETLAB_RETURN_MPR_LOCAL).toString());

              }
              // for SubmitAndCreateInvestigation need to prepopulate reportingSource and Ordering Physician
              if(contextAction.equals("SubmitAndCreateInvestigation"))
              {
                this.defaultValuesForCreateInvestigation(session, obsUid, processingDecision);
              }

            } 
            catch (Exception e) {

              obsForm.setResultedTestVOCollection(null);
              labResultProxyVO.reset();
              labResultProxyVO = null;
              obsForm.reset();
              obsForm = null;
              logger.error("Exception in Lab Report Submit: " + e.getMessage());
              e.printStackTrace();
              session.setAttribute("error", "");
              throw new ServletException(e);
            }
            // VOTester.createReport(labResultProxyVO, "labResultProxyVO from backend");

            String retainPatientValue = request.getParameter("retainPatientValue");

            if (retainPatientValue != null &&
                retainPatientValue.equalsIgnoreCase("T")) {
              NBSContext.store(session, "DSPatientPersonVO", personVO);
              session.setAttribute("patientFalse", "true");
              NBSContext.store(session, "DSFileTab", "2");
              request.setAttribute("patientLocalId", personVO.getThePersonDT().getLocalId());
            }else{
              session.setAttribute("patientFalse", "false");

            }

            String userTypeValue = nbsSecurityObj.getTheUserProfile().getTheUser().
                getUserType();
            boolean reportExteranlUser = false;
            if (userTypeValue != null) {
              reportExteranlUser = userTypeValue.equalsIgnoreCase(
                  NEDSSConstants.SEC_USERTYPE_EXTERNAL);
            }

            String organizationReportingUID = null;
            if (reportExteranlUser)
              organizationReportingUID = request.getParameter(
                  "reportingOrganizationUID");
            else
              organizationReportingUID = request.getParameter(
                  "Org-ReportingOrganizationUID");



            String retainReportingValue = request.getParameter(
                "retainReportingValue");

            if (retainReportingValue != null &&
                retainReportingValue.equalsIgnoreCase("T")) {
              LabInformationVO labInformationVO = new LabInformationVO();

              String reportingSourceDetails = (String) request.getParameter(
                  "Org-ReportingOrganizationDetails");

              String reportingSourceLabId = (String) request.getParameter(
                                    "labId");


              //System.out.println("\n\n  1111111   reportingSourceDetail =  " +reportingSourceDetails);

              Map<Object,Object> map = new HashMap<Object,Object>();
              map.put("0", organizationReportingUID);

              map.put("1", reportingSourceDetails);
              map.put("2", reportingSourceLabId);
              labInformationVO.setDescriptionText(map);
              NBSContext.store(session, "DSLabInformation", labInformationVO);
              session.setAttribute("reportingLabFalse", "true");
            }else{
              session.setAttribute("reportingLabFalse", "false");
            }

            obsForm.setResultedTestVOCollection(null);
            labResultProxyVO.reset();
            labResultProxyVO = null;
            obsForm.reset();
            obsForm = null;

            NBSContext.store(session, "DSFileTab", "3");
          }
        }
        catch(NEDSSAppConcurrentDataException ncde)
        {
          logger.fatal("ERROR - NEDSSAppConcurrentDataException, The data has been modified by another user, please recheck! ", ncde);
          //System.out.println(ncde.toString() + " 1");
          ncde.printStackTrace();
          return mapping.findForward("dataerror");
        }
        catch (javax.ejb.EJBException ex)
        {
            session.setAttribute("error", "");
                     // System.out.println(ex.toString() + " 2");
                      ex.printStackTrace();
            return (mapping.findForward("error"));
        }
        catch (Exception ex)
        {
            session.setAttribute("error", "");
            logger.error("General Exception in Lab Report Submit: " + ex.getMessage());
            ex.printStackTrace();
                     // System.out.println(ex.toString() + " 3");
            return (mapping.findForward("error"));
        }


   return mapping.findForward(contextAction);
 }

    private LabResultProxyVO createHandler(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                               HttpSession session, NBSSecurityObj nbsSecurityObj,String contextAction)
                        throws NEDSSAppConcurrentDataException, ServletException
    {


      String userTypeValue = nbsSecurityObj.getTheUserProfile().getTheUser().getUserType();
      boolean reportExteranlUser= false;
      if (userTypeValue != null) {
         reportExteranlUser = userTypeValue.equalsIgnoreCase(
             NEDSSConstants.SEC_USERTYPE_EXTERNAL);
      }

      int obsUid =-1;
      ObservationForm observationForm = (ObservationForm) form;

      String personParentUid =(String) request.getParameter("Patient.personUid");
      String localUID =(String) request.getParameter("patient.localID");

      PersonVO patient = observationForm.getPatient();

      // this is from entity serach OR retain patient

      if(personParentUid != null && !personParentUid.equals("")){
        patient.getThePersonDT().setPersonParentUid(new Long(personParentUid));
      }

      //electrinicInd is always set to "N" unless it is ELR as per defect 9169
      patient.getThePersonDT().setElectronicInd("N");
      String sCurrTask = NBSContext.getCurrentTask(session);
      // This is from file event tab, means not through leftNav
      if(!sCurrTask.equalsIgnoreCase("AddObservationLabDataEntry1"))
              personParentUid = NBSContext.retrieve(session, "DSPatientPersonUID").toString();

      //String patientUID = null;
      String organizationReportingUID=null;
      if(reportExteranlUser)
       organizationReportingUID= request.getParameter("reportingOrganizationUID");
      else
        organizationReportingUID= request.getParameter("Org-ReportingOrganizationUID");
      String orderingFacilityUID = request.getParameter("Org-OrderingFacilityUID");
      String providerUID = request.getParameter("Prov-entity.entityProvUID");

      ArrayList<ObservationVO> obsCollection  = new ArrayList<ObservationVO> ();
      ArrayList<Object> roleCollection  = new ArrayList<Object> ();
      ArrayList<Object> participationColl = new ArrayList<Object> ();
      ArrayList<Object> actRelationColl = new ArrayList<Object> ();
      ArrayList<Object> actIdColl = new ArrayList<Object> ();
      ArrayList<Object> personsColl = new ArrayList<Object> ();
      ArrayList<Object> materialColl = new ArrayList<Object> ();
      ArrayList<Object> personTeleColl = new ArrayList<Object> ();
      ArrayList<Object> personPostalColl = new ArrayList<Object> ();

      LabResultProxyVO labResultProxyVO = observationForm.getProxy();



      //get all the person Uids from request object for build relationships
      Long personSubjectUid = null;
      Long personProviderUid = null;
      Long organizationReportingUid = null;
      Long orderingFacilityUid = null;

      if(personParentUid != null){
        if(personParentUid.equals(""))
          logger.info("sPatient is empty");
        else
        personSubjectUid = new Long(personParentUid.toString());
      }

      if(organizationReportingUID != null){
        if (organizationReportingUID.equals(""))
          logger.info("reportingFacilityUID is empty");
        else
          organizationReportingUid = new Long(organizationReportingUID.toString());
      }

      if(orderingFacilityUID != null){
        if (orderingFacilityUID.equals(""))
          logger.info("sPatient is empty");
        else
          orderingFacilityUid = new Long(orderingFacilityUID.toString());
      }

      if (providerUID != null){
        if (providerUID.equals(""))
          logger.info("providerUID is empty");
        else
          personProviderUid = new Long(providerUID.toString());
      }
      obsUid = this.setObservationForCreate(labResultProxyVO.getTheObservationVOCollection(),
                                            obsCollection,obsUid,reportExteranlUser,
                                            request,nbsSecurityObj);
      ObservationVO obsVOLab112 = findObservationByCode(obsCollection, "LAB112");
      

      obsUid = setPatientTrackingVO(obsVOLab112, observationForm.getPatientStatusVO(), obsUid, 
      		 actRelationColl,  obsCollection);
      obsUid= this.temp214ObservationVO(labResultProxyVO.getTheObservationVOCollection(),
                                            obsCollection,obsUid);
      
      obsUid = this.setObsForCreateFromResulted(obsVOLab112, observationForm.getResultedTestVOCollection(),
                                                obsCollection,obsUid, actRelationColl, request);
      this.setActRelationshipForCreate(obsCollection,actRelationColl);

      this.setActIdForCreate(obsCollection,actIdColl, request);

      obsUid = this.setMaterialForCreate(labResultProxyVO.getTheMaterialVOCollection(),
                                materialColl,obsUid);
      CommonLabUtil labUtil = new CommonLabUtil();
      obsUid = labUtil.setPatientLabMorbForCreate(personSubjectUid,personsColl,
                              patient,request,obsUid);

      PersonVO personVO = (PersonVO) observationForm.getPatient();
      if (contextAction.equalsIgnoreCase("SubmitAndLoadLabDE1")) {
        RoleDT roleDT115 = this.genericRoleCreate("P", "patient", "ACTIVE", null, null, null,
                                                  "A", "PAT",
                                                  personVO.getThePersonDT().
                                                  getPersonUid());
        roleDT115.setRoleSeq(new Long(0));
        roleCollection.add(roleDT115);
      }

      if(materialColl != null){
       Iterator<Object>  materialIt = materialColl.iterator();
        while (materialIt.hasNext()) {
          MaterialDT materialDT = (MaterialDT) materialIt.next();
          RoleDT roleDT109 = this.genericRoleCreate("NI","No Information Given",
                                                    "ACTIVE","PSN",personVO.getThePersonDT().getPersonUid(),"PAT",
                                                    "A","SPEC",materialDT.getMaterialUid());
          roleDT109.setRoleSeq(new Long(0));
          roleCollection.add(roleDT109);
        }

      }
//personVO.getThePersonDT().getPersonUid()
      ObservationVO lab112 = findObservationByCode(labResultProxyVO.getTheObservationVOCollection(), "LAB112");

      ObservationDT observationDTLab112 = lab112.getTheObservationDT();



     ParticipationDT participationDT110 = this.genericParticipationCreate("OBS",
         observationDTLab112.getObservationUid(),
         null, "ACTIVE", "A", "PSN", personVO.getThePersonDT().getPersonUid(),
         "PATSBJ", "Patient Subject");

     participationColl.add(participationDT110);

      if(personProviderUid != null){
        ParticipationDT participationDT101 = this.genericParticipationCreate("OBS",observationDTLab112.getObservationUid(),
                                             null,"ACTIVE","A","PSN",personProviderUid,"ORD","Orderer");
        participationColl.add(participationDT101);
      }

      if(organizationReportingUid != null){
        ParticipationDT participationDT111 = this.genericParticipationCreate("OBS",observationDTLab112.getObservationUid(),
                                             null,"ACTIVE","A","ORG",organizationReportingUid,"AUT","Author");

        participationColl.add(participationDT111);
      }

      if(orderingFacilityUid != null){
        ParticipationDT participationDT102 = this.genericParticipationCreate("OBS",observationDTLab112.getObservationUid(),
                                             null,"ACTIVE","A","ORG",orderingFacilityUid,"ORD","Orderer");
        participationColl.add(participationDT102);
      }

      if(materialColl != null ){
       Iterator<Object>  materialIt = materialColl.iterator();
        while (materialIt.hasNext()) {
          MaterialDT materialDT = (MaterialDT) materialIt.next();
          ParticipationDT participationDT104 = this.genericParticipationCreate("OBS",observationDTLab112.getObservationUid(),
                                                     observationDTLab112.getEffectiveFromTime(),"ACTIVE",
                                                     "A","MAT",materialDT.getMaterialUid(),"SPC",
                                                     "Specimen");
          participationColl.add(participationDT104);
          //System.out.println("\n material uid " + materialDT.getMaterialUid());
        }
      }
      labResultProxyVO.setThePersonVOCollection(personsColl);
      labResultProxyVO.setTheObservationVOCollection(obsCollection);
      labResultProxyVO.setTheParticipationDTCollection(participationColl);
      labResultProxyVO.setTheRoleDTCollection(roleCollection);
      labResultProxyVO.setTheActIdDTCollection(actIdColl);
      labResultProxyVO.setTheActRelationshipDTCollection(actRelationColl);
      labResultProxyVO.setItNew(true);
      labResultProxyVO.setItDirty(false);
      this.setLAB112(labResultProxyVO.getTheObservationVOCollection());

      //LDF
     /**
      * @TBD Release 6.0, Commented out as LDF will be planned out as new type of answers
     CommonLabUtil commonLabUtil = new CommonLabUtil();
   
     commonLabUtil.submitLabLDF(labResultProxyVO,observationForm, true, request);
     commonLabUtil.submitPatientLDF(personVO, observationForm, true, request);
*/
      obsCollection  = null;
      roleCollection  =  null;
      participationColl =  null;
      actRelationColl =  null;
      actIdColl =  null;
      personsColl =  null;
      materialColl =  null;
      personTeleColl =  null;
      personPostalColl =  null;
      observationForm= null;
      obsUid = -1;
      return labResultProxyVO;
    }

    private ActRelationshipDT genericActRelationShipCreate(String recordStatusCd,
        Long sourceActUid, String sourceClassCd, String statusCd,
        Long targetActUid, String targetClassCd, String typeCd, String typeDescTxt)
    {
      ActRelationshipDT actRelDT = new ActRelationshipDT();
      actRelDT.setRecordStatusCd(recordStatusCd);
      actRelDT.setSourceActUid(sourceActUid);
      actRelDT.setSourceClassCd(sourceClassCd);
      actRelDT.setStatusCd(statusCd);
      actRelDT.setTargetActUid(targetActUid);
      actRelDT.setTargetClassCd(targetClassCd);
      actRelDT.setTypeCd(typeCd);
      actRelDT.setTypeDescTxt(typeDescTxt);
      actRelDT.setItNew(true);
      logger.debug("\n\n\n sourceActUid = "  + sourceActUid + " targetActUid== "  + targetActUid +  " typeDescTxt== "  + typeDescTxt);

      return actRelDT;
    }


    private EntityIdDT genericEntityIdDT(String assigningAuthorityCd,String assigningAuthorityDesc,
                                         String entityIdSeq, Long entityUid,String  recordStatusCd,
                                         String rootExtensionTxt, String type_cd, String typeDescTxt){
      EntityIdDT entityIdDT = new EntityIdDT();


      return entityIdDT;
    }

    private ParticipationDT genericParticipationCreate(String actClassCd,
        Long actUid, Timestamp fromTime, String recordStatusCd, String statusCd,
        String subjectClassCd, Long subjectEntityUid, String typeCd, String typeDescTxt)
    {
      ParticipationDT participationDT = new ParticipationDT();
      participationDT.setActClassCd(actClassCd);
      participationDT.setActUid(actUid);
      participationDT.setFromTime(fromTime);
      participationDT.setRecordStatusCd(recordStatusCd);
      participationDT.setStatusCd(statusCd);
      participationDT.setSubjectClassCd(subjectClassCd);
      participationDT.setSubjectEntityUid(subjectEntityUid);
      participationDT.setTypeCd(typeCd);
      participationDT.setTypeDescTxt(typeDescTxt);
      participationDT.setItNew(true);


      return participationDT;
    }


    private RoleDT genericRoleCreate(String cd,
        String cdDescTxt, String recordStatusCd, String scopingClassCd, Long entityUid,
        String scopingRoleCd, String statusCd, String subjectClassCd, Long subjectEntityUid)
    {
      RoleDT roleDT = new RoleDT();
      roleDT.setCd(cd);
      roleDT.setCdDescTxt(cdDescTxt);
      roleDT.setRecordStatusCd(recordStatusCd);
      roleDT.setScopingClassCd(scopingClassCd);
      roleDT.setScopingEntityUid(entityUid);
      roleDT.setScopingRoleCd(scopingRoleCd);
      roleDT.setStatusCd(statusCd);
      roleDT.setSubjectClassCd(subjectClassCd);
      roleDT.setSubjectEntityUid(subjectEntityUid);
      roleDT.setItNew(true);


      return roleDT;
    }

    private String getSystemDescTxt(String systemValue, HttpSession session) throws
        NEDSSAppConcurrentDataException, java.rmi.RemoteException,
        javax.ejb.EJBException, Exception
    {

      MainSessionCommand msCommand = null;
      String systemDescTxt = null;
      //try {
      String sBeanJndiName = JNDINames.OBSERVATION_PROXY_EJB;
      String sMethod = null;
      MainSessionHolder holder = new MainSessionHolder();
      ArrayList<?> resultUIDArr = null;

      sMethod = "getLaboratorySystemDescTxt";
      Object[] oParams ={systemValue};
      msCommand = holder.getMainSessionCommand(session);
      resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);

      for (int i = 0; i < resultUIDArr.size(); i++)
      {
        logger.info("Result " + i + " is: " + resultUIDArr.get(i));
      }

      if ( (resultUIDArr != null) && (resultUIDArr.size() > 0))
      {
        /*System.out.println("calling getLaboratorySystemDescTxt worked!!! getLaboratorySystemDescTxt = " +
                     resultUIDArr.get(0)); */


        systemDescTxt = (String) resultUIDArr.get(0);

        return systemDescTxt;
      }

      return null;
    } //sendProxyToEJB


    private Map<?,?> sendProxyToEJB(LabResultProxyVO proxy, HttpSession session, String sCurrTask) throws
        NEDSSAppConcurrentDataException, java.rmi.RemoteException,
        javax.ejb.EJBException, Exception
    {

      MainSessionCommand msCommand = null;

      //try {
      String sBeanJndiName = JNDINames.OBSERVATION_PROXY_EJB;
      String sMethod = null;
      Long obsUid = null;
      MainSessionHolder holder = new MainSessionHolder();
      ArrayList<?> resultUIDArr = null;



      msCommand = holder.getMainSessionCommand(session);
      if(sCurrTask != null && (sCurrTask.equalsIgnoreCase("AddObservationLab4")
                               || sCurrTask.equalsIgnoreCase("AddObservationLab1")
                               || sCurrTask.equalsIgnoreCase("AddObservationLab3")))
      {
        //AddObservationLab4 is from manage observation to Add morb, need to assoc with investigation
        String sInvestigationUid = (String)NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationUid);
        Long investigationUid = new Long(sInvestigationUid);
        sMethod = "setLabResultProxyWithAutoAssoc";
        Object[] oParams = {proxy, investigationUid};
        resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
      }
      else
      {
        sMethod = "setLabResultProxy";
        Object[] oParams = {proxy};
        resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
      }



      for (int i = 0; i < resultUIDArr.size(); i++)
      {
        logger.info("Result " + i + " is: " + resultUIDArr.get(i));
      }

      if ( (resultUIDArr != null) && (resultUIDArr.size() > 0))
      {
        logger.debug("store observation worked!!! observationUID = " +
                     resultUIDArr.get(0));


        Map<?,?> result = (Map<?,?>) resultUIDArr.get(0);

        //obsUid = (Long)result.get(NEDSSConstants.SETLAB_RETURN_OBS_UID);
        obsUid = (Long)result.get(NEDSSConstants.SETLAB_RETURN_MPR_UID);
       return result;

      }

      return null;
    } //sendProxyToEJB

    private void defaultValuesForCreateInvestigation(HttpSession session, Long labUid, String processingDecision)
    {
      /**
       * Create a map and store in object store for use in investigation
       */

      LabResultProxyVO labResultProxy = this.getLabResultProxyVO(labUid, session);
      LabReportFieldMappingBuilder mapBuilder = new LabReportFieldMappingBuilder();

      //Call getLabResultProxyVO to get Org Facility /Provider / Reporter Info
      // which are really missing in the form Proxy !

      TreeMap<Object,Object> loadTreeMap = null;

      if (labResultProxy != null)
      {
        loadTreeMap = mapBuilder.createLabReportLoadTreeMap(labResultProxy, labUid, processingDecision);
      }
      NBSContext.store(session, "DSLabMap", loadTreeMap);

    }


    private int setObsForCreateFromResulted(ObservationVO obsVOLab112,
    										Collection<Object>  resultedVOColl,
                                            Collection<ObservationVO>  obsCollection,
                                            int tempID, Collection<Object>  actRelationColl, HttpServletRequest request){
      if(resultedVOColl != null){
       Iterator<Object>  resulted = resultedVOColl.iterator();
        while (resulted.hasNext()) {
          ResultedTestVO resultTestVO = (ResultedTestVO) resulted.next();
          ObservationVO isloatedVO =resultTestVO.getTheIsolateVO();

          ObservationVO theSusceptibilityVO = resultTestVO.getTheSusceptibilityVO();
          boolean flag = false;
          if(isloatedVO != null){

            if (theSusceptibilityVO != null&&
                (theSusceptibilityVO.getTheObservationDT().getStatusCd() == null ||
                 theSusceptibilityVO.getTheObservationDT().getStatusCd().equals("")||
                 theSusceptibilityVO.getTheObservationDT().getStatusCd().equals("I")))
            {

               //System.out.println("\n\n\n Data is not avaivlable");
            }

            else{
              ObservationDT obsDT = isloatedVO.getTheObservationDT();

              if (isloatedVO.getTheObsValueCodedDTCollection() != null) {
               Iterator<Object>  obsValueCode = isloatedVO.
                    getTheObsValueCodedDTCollection().
                    iterator();
                CachedDropDownValues cdv = new CachedDropDownValues();
                while (obsValueCode.hasNext()) {
                  ObsValueCodedDT obsValueDT = (ObsValueCodedDT) obsValueCode.
                      next();
                //This is to do translation for dropdown
                  if(isloatedVO.getTheObservationDT().getCtrlCdUserDefined1().equalsIgnoreCase("Y")){
                    if (obsValueDT.getDisplayName() != null &&
                        !obsValueDT.getDisplayName().equals("")) {
                      flag = true;
                      obsValueDT.setCode(obsValueDT.getDisplayName());
                      TreeMap<Object,Object> treeMap = (TreeMap<Object,Object>) cdv.getOrganismListDesc();
                      String temp = (String) treeMap.get(obsValueDT.getCode());
                      obsValueDT.setDisplayName(temp);
                      isloatedVO.getTheObservationDT().setCtrlCdUserDefined1("Y");
                    }
                //This is for Search/Select
                else if (obsValueDT.getSearchResultRT() != null &&
                        !obsValueDT.getSearchResultRT().equals("")) {
                      obsValueDT.setDisplayName(obsValueDT.getHiddenCd());
                      obsValueDT.setCode(obsValueDT.getSearchResultRT());
                      isloatedVO.getTheObservationDT().setCtrlCdUserDefined1("Y");
                      flag = true;
                    }
                  }
                  else{
                    if (obsValueDT.getCode() == null ||
                        obsValueDT.getCode().trim().length() == 0) {
                      obsValueDT.setCode("NI");
                      isloatedVO.getTheObservationDT().setCtrlCdUserDefined1("N");
                    }
                    else {

                      String temp = cdv.getCodedResultDesc(obsValueDT.getCode());
                      obsValueDT.setDisplayName(temp);
                      isloatedVO.getTheObservationDT().setCtrlCdUserDefined1("N");
                    }
                  }
                  obsValueDT.setItNew(true);
                    if (obsValueDT != null && obsValueDT.getCdSystemCdRT()!= null && obsValueDT.getCdSystemCdRT().equals("SNM")) {
                      obsValueDT.setCodeSystemCd("SNM");
                    }
                    else{
                      obsValueDT.setCodeSystemCd("NBS");
                    }
                   obsValueDT.setCodeSystemDescTxt("NEDSS Base System");
                }
              }
              if (isloatedVO.getTheObsValueDateDTCollection() != null) {
               Iterator<Object>  obsValueDate = isloatedVO.getTheObsValueDateDTCollection().
                    iterator();
                while (obsValueDate.hasNext()) {
                  ObsValueDateDT obsValueDT = (ObsValueDateDT) obsValueDate.next();
                  obsValueDT.setItNew(true);
                }
              }
              if (isloatedVO.getTheObsValueNumericDTCollection() != null) {
               Iterator<Object>  obsValueNumeric = isloatedVO.
                    getTheObsValueNumericDTCollection().
                    iterator();
                while (obsValueNumeric.hasNext()) {
                  ObsValueNumericDT obsValueDT = (ObsValueNumericDT)
                      obsValueNumeric.next();
                  obsValueDT.setItNew(true);
                  if (obsValueDT.getNumericValue() != null)
                    if (! (obsValueDT.getNumericValue().equals(""))) {
                      ObservationUtil obsUtil = new ObservationUtil();
                      obsValueDT = obsUtil.parseNumericResult(obsValueDT);
                    }
                }
              }
              if (isloatedVO.getTheObsValueTxtDTCollection() != null) {
               Iterator<Object>  obsValueText = isloatedVO.getTheObsValueTxtDTCollection().
                    iterator();
                while (obsValueText.hasNext()) {

                  ObsValueTxtDT obsValueDT = (ObsValueTxtDT) obsValueText.next();
                  obsValueDT.setItNew(true);
                }
                 isloatedVO.getObsValueTxtDT_s(1).setTxtTypeCd("N");
              }
              obsDT.setStatusTime(new java.sql.Timestamp(new Date().getTime()));
              obsDT.setObservationUid(new Long(tempID--));
              obsDT.setCtrlCdDisplayForm(NEDSSConstants.LAB_REPORT);
              obsDT.setObsDomainCdSt1(NEDSSConstants.RESULTED_TEST_OBS_DOMAIN_CD);
              // Setting Cd here for Rel 1.1.3 , because the new SRT Filtering stores the code in CdDescTxt from XSP
              obsDT.setCd(obsDT.getCdDescTxt());

              if (obsDT.getSearchResultRT() != null &&
                  obsDT.getSearchResultRT().length() != 0) {
                String Resulted = obsDT.getSearchResultRT();
                obsDT.setCdDescTxt(Resulted);
              }

              if(obsDT.getHiddenCd()!= null && !obsDT.getHiddenCd().equals("")){
                  obsDT.setCd(obsDT.getHiddenCd());
              }

              String cdSystemCdRT = obsDT.getCdSystemCdRT();
              String labId = (String)request.getParameter("labId");

              if (cdSystemCdRT != null && !cdSystemCdRT.equals("")) {
                obsDT.setCdSystemCd(cdSystemCdRT);
                if(cdSystemCdRT.equalsIgnoreCase("LN"))
                  obsDT.setCdSystemDescTxt("LOINC");
                 else
                   obsDT.setCdSystemDescTxt("NEDSS Base System");
              }
              else {
                if (labId != null && !labId.equals("") && !labId.equals("null")) {
                  obsDT.setCdSystemCd(labId);
                  obsDT.setCdSystemDescTxt("NEDSS Base System");
                }else{
                  //obsDT.setCdSystemCd("2.16.840.1.113883");
                  obsDT.setCdSystemCd("NBS");
                  obsDT.setCdSystemDescTxt("NEDSS Base System");
                }
              }
              obsDT.setCdDescTxt(obsDT.getSearchResultRT());
              obsDT.setItNew(true);
              isloatedVO.setTheObservationDT(obsDT);
              isloatedVO.setItNew(true);
              actRelationColl.add(genericActRelationShipCreate(
                  NEDSSConstants.RECORD_STATUS_ACTIVE,
                  obsDT.getObservationUid(),
                  NEDSSConstants.CLASS_CD_OBS,
                  NEDSSConstants.STATUS_ACTIVE,
                  obsVOLab112.getTheObservationDT().getObservationUid(),
                  NEDSSConstants.CLASS_CD_OBS,
                  NEDSSConstants.ACT108_TYP_CD,
                  NEDSSConstants.HAS_COMPONENT));

              obsCollection.add(isloatedVO);

              if (theSusceptibilityVO != null && flag) {
                theSusceptibilityVO.getTheObservationDT().setObservationUid(new Long(tempID--));
                theSusceptibilityVO.setItNew(true);
                theSusceptibilityVO.getTheObservationDT().setCtrlCdDisplayForm(NEDSSConstants.LAB_REPORT);
                theSusceptibilityVO.getTheObservationDT().setObsDomainCdSt1(NEDSSConstants.R_ORDER);
                theSusceptibilityVO.getTheObservationDT().setStatusTime(new java.sql.Timestamp(new Date().getTime()));

                theSusceptibilityVO.getTheObservationDT().setCdDescTxt("No Information Given");
                theSusceptibilityVO.getTheObservationDT().setItNew(true);
                actRelationColl.add(genericActRelationShipCreate(
                        "ACTIVE",
                        theSusceptibilityVO.getTheObservationDT().
                        getObservationUid(), "OBS", "A", obsDT.getObservationUid(),
                        "OBS", "REFR", "Refer to"));
                actRelationColl.add(genericActRelationShipCreate(
                        "ACTIVE",
                        theSusceptibilityVO.getTheObservationDT().
                        getObservationUid(), "OBS", "A",
                        obsVOLab112.getTheObservationDT().getObservationUid(),
                        "OBS", "SPRT", "Has Support"));

              Iterator<Object>  codeDTTheSusceptibilityVO = theSusceptibilityVO.
                   getTheObsValueCodedDTCollection().iterator();
               while (codeDTTheSusceptibilityVO.hasNext()) {
                 ObsValueCodedDT obsValueCodeDT = (ObsValueCodedDT) codeDTTheSusceptibilityVO.
                     next();
                 if (obsValueCodeDT.getCode() == null ||
                     obsValueCodeDT.getCode().trim().length() == 0)
                   obsValueCodeDT.setCode("NI");

                 obsValueCodeDT.setItNew(true);
                }
                obsCollection.add(theSusceptibilityVO);

                if (theSusceptibilityVO.getTheObsValueCodedDTCollection() != null) {
                 Iterator<Object>  codeIT = theSusceptibilityVO.
                      getTheObsValueCodedDTCollection().iterator();
                  while (codeIT.hasNext()) {
                    ObsValueCodedDT obsValueCodeDT = (ObsValueCodedDT) codeIT.
                        next();
                    if (obsValueCodeDT.getCode() == null ||
                        obsValueCodeDT.getCode().trim().length() == 0)
                      obsValueCodeDT.setCode("NI");

                    obsValueCodeDT.setItNew(true);

                    if (obsValueCodeDT.getCode().equalsIgnoreCase("y")) {
                      Collection<Object>  observationVOColl = resultTestVO.
                          getTheSusceptibilityCollection();
                      if (observationVOColl != null) { // This is LAB110 observation
                       Iterator<Object>  obsVOIt = observationVOColl.iterator();

                        while (obsVOIt.hasNext()) {
                          ObservationVO obsVO = (ObservationVO) obsVOIt.next();
                          if(!obsVO.getTheObservationDT().getStatusCd().equalsIgnoreCase("I")){
                            ObservationUtils.trimEmptyObsValueCodedDTCollections(
                                obsVO);
                            ObservationDT obsSusDT = obsVO.getTheObservationDT();
                            obsSusDT.setObservationUid(new Long(tempID--));

                            actRelationColl.add(genericActRelationShipCreate(
                                "ACTIVE",
                                obsSusDT.getObservationUid(), "OBS", "A",
                                theSusceptibilityVO.getTheObservationDT().
                                getObservationUid(), "OBS", "COMP",
                                "Has Component"));
                            if (obsVO.getTheObsValueCodedDTCollection() != null) {
                             Iterator<Object>  obsValueCode =
                                  obsVO.getTheObsValueCodedDTCollection().
                                  iterator();
                              while (obsValueCode.hasNext()) {
                                ObsValueCodedDT obsValueDT = (ObsValueCodedDT)
                                    obsValueCode.next();
                                if (obsValueDT.getCode() == null ||
                                    obsValueDT.getCode().trim().length() == 0)
                                  obsValueDT.setCode("NI");
                                obsValueDT.setItNew(true);
                                //setDisplayName
                              	CachedDropDownValues cdv = new CachedDropDownValues();
                              	String result =  cdv.getCodedResultDesc(obsValueDT.getCode());
                              	obsValueDT.setDisplayName(result);
      
                              }
                              
                            }
                            if (obsVO.getTheObsValueTxtDTCollection() != null) {
                             Iterator<Object>  obsValueText =
                                  obsVO.getTheObsValueTxtDTCollection().iterator();
                              while (obsValueText.hasNext()) {
                                ObsValueTxtDT obsValueDT = (ObsValueTxtDT)
                                    obsValueText.next();
                                obsValueDT.setItNew(true);
                              }
                            }

                            if (obsVO.getTheObsValueNumericDTCollection() != null) {
                             Iterator<Object>  obsValueNumeric =
                                  obsVO.getTheObsValueNumericDTCollection().
                                  iterator();
                              while (obsValueNumeric.hasNext()) {
                                ObsValueNumericDT obsValueDT = (ObsValueNumericDT)
                                    obsValueNumeric.next();
                                if (obsValueDT.getNumericValue() != null)
                                  if (! (obsValueDT.getNumericValue().equals(""))) {
                                    ObservationUtil obsUtil = new ObservationUtil();
                                    obsValueDT = obsUtil.parseNumericResult(
                                        obsValueDT);
                                  }
                                obsValueDT.setItNew(true);
                              }
                            }
                            if (obsVO.getTheObservationInterpDTCollection() != null) {
                             Iterator<Object>  interpretation = obsVO.
                                  getTheObservationInterpDTCollection().iterator();
                              while (interpretation.hasNext()) {
                                ObservationInterpDT observationInterpDT =
                                    (ObservationInterpDT) interpretation.next();
                                String temp= this.getDescTxt("OBS_INTRP",
                                     observationInterpDT.getInterpretationCd());
                                if(temp != null){
                                  observationInterpDT.setInterpretationDescTxt(temp);
                                }
                                observationInterpDT.setItNew(true);
                              }
                            }

                            if (obsSusDT.getSearchResultRT() != null &&
                                obsSusDT.getSearchResultRT().length() !=
                                0) {

                              obsSusDT.setCdDescTxt(obsSusDT.getSearchResultRT());
                            }

                            String cdSystemCdDrugRT = obsSusDT.getCdSystemCdRT();

                            if (cdSystemCdDrugRT != null &&
                                !cdSystemCdDrugRT.equals("")) {
                              obsSusDT.setCdSystemCd(cdSystemCdDrugRT);
                              if(cdSystemCdDrugRT.equalsIgnoreCase("LN"))
                                obsSusDT.setCdSystemDescTxt("LOINC");

                            }
                            else {
                            	/** The system should not use labID(Facility ID 
                               	 *  from Reporting Facility) to derive Drug Name.
                               	 *  See the  NBS Laboratory Report Table Maintenance Guide.doc
                               	 *  for detail information.It should always use 'DEFAULT'
                               	 */

                              obsSusDT.setCdSystemCd("DEFAULT");
                              obsSusDT.setCdSystemDescTxt("NEDSS Base System");
                            }


                            if(obsSusDT.getHiddenCd() != null && !obsSusDT.getHiddenCd().equals("")){
                              obsSusDT.setCd(obsSusDT.getHiddenCd());
                            }
                            else  {
                              obsSusDT.setCd(obsSusDT.getCdDescTxt());
                              CachedDropDownValues cdv = new CachedDropDownValues();
                              String cdSystemCd = null;
                              String obsCd = null;
                              String cdDescTxt = null;
                              if (obsSusDT.getCdSystemCd() != null)
                                cdSystemCd = obsSusDT.getCdSystemCd();

                              if (obsSusDT.getCd() != null) // use getCd() instead of  getCdDescTxt
                                obsCd = obsSusDT.getCd(); // Use getCd()    "                           "

                              if (cdSystemCd != null && obsCd != null) {
                                if (cdSystemCd.equals("LN"))
                                  cdDescTxt = cdv.getResultedTestDesc(obsCd);
                                else if (!cdSystemCd.equals("LN"))
                                  cdDescTxt = cdv.getResultedTestDescLab(cdSystemCd, obsCd);
                              }
                              if (cdDescTxt != null)
                                obsSusDT.setCdDescTxt(cdDescTxt);

                            }

                            obsSusDT.setCtrlCdDisplayForm(NEDSSConstants.
                                LAB_REPORT);
                            obsSusDT.setObsDomainCdSt1(NEDSSConstants.R_RESULT);
                            obsSusDT.setStatusTime(new java.sql.Timestamp(new
                                Date().
                                getTime()));

                            obsSusDT.setItNew(true);
                            obsVO.setTheObservationDT(obsSusDT);
                            obsVO.setItNew(true);
                            obsCollection.add(obsVO);
                            setObsValueCodedToDefaultValues(obsCollection);


                            //VOTester.createReport(obsVO,"observationVO from backend");
                          }
                        }
                      } // end of if (observationVOColl != null)
                    }
                  }
                }
              } // this is end of if(theSusceptibilityVO != null)

           } // End status Cd value check with I
            //obsCollection.addAll(
            tempID = setTheTrackingInformation(obsVOLab112, resultTestVO, tempID, 
            		actRelationColl, obsCollection);
          }

      } //while Loop ends
    }
    return tempID;
   }


    /*
     * sets negative tempId and newFlag to true for observations
     */
    private int setObservationForCreate(Collection<ObservationVO>  obsColl,Collection<ObservationVO>  obsCollection,
                                        int tempID,boolean reportExteranlUser,
                                        HttpServletRequest request,
                                        NBSSecurityObj nbsSecurityObj){

       if(obsColl != null){
    	   //Remove observation LAB235 if processingDecision is not selected
    	   //ObservationVO removeLAB235 = null;
         Iterator<ObservationVO>  it = obsColl.iterator();
          while(it.hasNext()){
            ObservationVO obsVO = (ObservationVO)it.next();
            ObservationUtils.trimEmptyObsValueCodedDTCollections(obsVO);
            ObservationDT obsDT = obsVO.getTheObservationDT();
            if(obsVO.getTheObsValueCodedDTCollection() != null){
              Iterator<Object>  obsValueCode = obsVO.getTheObsValueCodedDTCollection().iterator();
               while(obsValueCode.hasNext()){
                 ObsValueCodedDT obsValueDT = (ObsValueCodedDT) obsValueCode.next();
                 if(obsValueDT.getCode() == null || obsValueDT.getCode().trim().length() == 0)
                   obsValueDT.setCode("NI");
                 obsValueDT.setItNew(true);
               }
            }
            if(obsVO.getTheObsValueDateDTCollection()!=null){
             Iterator<Object>  obsValueDate = obsVO.getTheObsValueDateDTCollection().iterator();
              while(obsValueDate.hasNext()){
                ObsValueDateDT obsValueDT =(ObsValueDateDT) obsValueDate.next();
                obsValueDT.setItNew(true);
                //obsValueDT.setItDirty(true);
              }
            }
            if (obsVO.getTheObsValueNumericDTCollection() != null)
            {
             Iterator<Object>  obsValueNumeric = obsVO.getTheObsValueNumericDTCollection().iterator();
              while (obsValueNumeric.hasNext()){
                ObsValueNumericDT obsValueDT = (ObsValueNumericDT)obsValueNumeric.next();
                obsValueDT.setItNew(true);
              }
            }

            if (obsVO.getTheObsValueTxtDTCollection() != null)
            {
             Iterator<Object>  obsValueText = obsVO.getTheObsValueTxtDTCollection().iterator();
              while (obsValueText.hasNext()) {

                ObsValueTxtDT obsValueDT = (ObsValueTxtDT) obsValueText.next();
                obsValueDT.setItNew(true);
                if (obsDT.getCd()!=null && obsDT.getCd().equalsIgnoreCase("LAB214")) {
                  obsValueDT.setTxtTypeCd("N");
                  //LabReportComment(LAB214) Date goes as Observation.activity_to_time
                  obsDT.setActivityToTime(new java.sql.Timestamp(new Date().getTime()));
                }
              }

            }
            obsDT.setStatusTime(new java.sql.Timestamp(new Date().getTime()));
          
            //obsDT.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
            obsDT.setObservationUid(new Long(tempID--));
            obsDT.setItNew(true);

            if(obsDT.getCd() != null){
              if (obsDT.getCd().equalsIgnoreCase("LAB112")) {

                obsDT.setObsDomainCdSt1(NEDSSConstants.ORDERED_TEST_OBS_DOMAIN_CD);
                obsDT.setCtrlCdDisplayForm(NEDSSConstants.LAB_REPORT);
              //  obsDT.setLastChgTime(new java.sql.Timestamp(new Date().getTime()));  // Don't set this for create

                String cdSystemCdOT = obsDT.getCdSystemCdOT();
                String labId = (String) request.getParameter("labId");

                // If user select from search Button
                if (obsDT.getSearchResultOT() != null &&
                    obsDT.getSearchResultOT().length() != 0) {
                  String ordered = obsDT.getSearchResultOT();
                  obsDT.setCdDescTxt(ordered);
                  obsDT.setTxt("");

                  if(cdSystemCdOT.equalsIgnoreCase("LN"))
                     obsDT.setCdSystemDescTxt("LOINC");
                  else if(labId != null && !labId.equals("")&&!labId.equals("null")){
                    try{
                      obsDT.setCdSystemDescTxt(getSystemDescTxt(labId,request.getSession()));
                    }catch(Exception e){}
                  }

                  if (cdSystemCdOT != null && !cdSystemCdOT.equals("")) {
                    obsDT.setCdSystemCd(cdSystemCdOT);
                  }
                  else {
                    if (labId != null && !labId.equals("") && !labId.equals("null")) {
                      obsDT.setCdSystemCd(labId);
                    }
                  }
                }
                // If user select from Drop down
                else if(obsDT.getTxt() != null && obsDT.getTxt().length() !=0)
                {
                   obsDT.setCdDescTxt(obsDT.getTxt());
                    //obsDT.setTxt("");
                    if (labId != null && !labId.equals("")&& !labId.equals("null")) {
                      obsDT.setCdSystemCd(labId);
                      try{
                      obsDT.setCdSystemDescTxt(getSystemDescTxt(labId,
                          request.getSession()));
                      }catch(Exception e){}

                    }
                }
                else
                {
                  obsDT.setCdDescTxt("No Information Given");
                  //obsDT.setCd("NI");
                  obsDT.setCdSystemCd("2.16.840.1.113883");
                  obsDT.setCdSystemDescTxt("Health Level Seven");
                }
                 //obsDT.setCdSystemDescTxt("Health Level Seven");
                obsDT.setStatusCd("D");
                obsDT.setRecordStatusCd(NEDSSConstants.ACTIVE);
                if (obsDT.getTargetSiteCd() == null ||
                    obsDT.getTargetSiteCd().equals("")) {
                  obsDT.setTargetSiteCd("NI");

                }
                if (reportExteranlUser == true)
                  obsDT.setElectronicInd("E");
                else
                  obsDT.setElectronicInd("N");

                String programAreaCd= request.getParameter("programAreaCd1");
                if(programAreaCd != null  && !programAreaCd.equals(""))
                  obsDT.setProgAreaCd(programAreaCd);

                if (obsDT.getProgAreaCd() == null) {
                  obsDT.setProgAreaCd("ANY");
                }
                if (obsDT.getProgAreaCd().equalsIgnoreCase("NONE")) {
                  obsDT.setProgAreaCd("ANY");
                }

                String jurisdictionCd= request.getParameter("jurisdictionCd1");
                if(jurisdictionCd != null && !jurisdictionCd.equals(""))
                  obsDT.setJurisdictionCd(jurisdictionCd);

                if(obsDT.getJurisdictionCd() == null)
                  obsDT.setJurisdictionCd("ANY");


                String shared =request.getParameter("proxy.observationVO_s[0].theObservationDT.sharedInd");

                if ( shared == null) {
                      obsVO.getTheObservationDT().setSharedInd("F");
                }
                /*
                 * AS per Rel1.1.3 we are not doing a reverse translation for ordered test rather
                 * we are translating the code to descrition and setting the Description to cdDescTxt.
                 * We are storing the orderedtest Code in txt field in lab XSP
                */
                if(programAreaCd == null)
                  programAreaCd = obsDT.getProgAreaCd();

                //SRTCode  srtcodeDT = NedssCodeLookupServlet.getUniqueDTForCodeValueLookup(labId,NEDSSConstants.ORDERED_TEST_LOOKUP,null,obsDT.getTxt(),null,request);

               //if ( srtcodeDT != null && srtcodeDT.getCode()!= null && srtcodeDT.getCode().equalsIgnoreCase(obsDT.getTxt())) 
               //  obsDT.setCdDescTxt(srtcodeDT.getDesc());
					
                //BB - updated for R1.1.4 - There is no need to make this call unless obsDT.cdDescTxt 
                //needs to be populated.  Also, in some cases the code to lookup will be in obsDT.cd 
                //rather than obsDT.txt
                if ( !obsDT.getCdDescTxt().equalsIgnoreCase(NEDSSConstants.NOINFORMATIONGIVEN) &&
						(obsDT.getTxt() != null || obsDT.getCd() != null) && 
						(obsDT.getCdDescTxt() == null || obsDT.getCdDescTxt().trim().length() == 0) ) {

                	SRTCode srtcodeDT = null;
                	
                	if (obsDT.getTxt() != null && obsDT.getTxt().trim().length() > 0)
    					srtcodeDT = NedssCodeLookupServlet.getUniqueDTForCodeValueLookup(labId,
								NEDSSConstants.ORDERED_TEST_LOOKUP, null, obsDT.getTxt(), null, 
								request);
                	else if (obsDT.getCd() != null && obsDT.getCd().trim().length() > 0)
    					srtcodeDT = NedssCodeLookupServlet.getUniqueDTForCodeValueLookup(labId,
								NEDSSConstants.ORDERED_TEST_LOOKUP, null, obsDT.getCd(), null, 
								request);
	
					if (srtcodeDT != null && srtcodeDT.getCode() != null &&
							srtcodeDT.getCode().equalsIgnoreCase(obsDT.getTxt()))
						obsDT.setCdDescTxt(srtcodeDT.getDesc());
                }

                boolean permissionForProgJuriShar = nbsSecurityObj.getPermission(
                                           NBSBOLookup.OBSERVATIONLABREPORT,
                                           NBSOperationLookup.ASSIGNSECURITYFORDATAENTRY);


                if(permissionForProgJuriShar == false)
                  obsVO.getTheObservationDT().setSharedInd("T");

                 String targetSite = obsDT.getTargetSiteCd();
                 if(targetSite != null && !targetSite.equals("NI"))
                 {
                   obsDT.setTargetSiteDescTxt(getDescTxt("ANATOMIC_SITE",targetSite));
                 }
              }
              if (obsDT.getCd().equalsIgnoreCase("LAB214")) {


                obsDT.setObsDomainCdSt1(NEDSSConstants.C_RESULT);
                obsDT.setCtrlCdDisplayForm(NEDSSConstants.LABCOMMENT);
                obsDT.setCdDescTxt("User Report Comment");
                obsDT.setCdSystemCd("NBS");
                obsDT.setCdSystemDescTxt("NEDSS Base System");
                obsDT.setStatusCd("D");
                //obsDT.setRecordStatusCd(NEDSSConstants.ACTIVE);

              }

            }
            String addUserID= nbsSecurityObj.getEntryID();
            if (addUserID != null)
              obsDT.setAddUserId(new Long(addUserID));

            obsVO.setTheObservationDT(obsDT);
            obsVO.setItNew(true);
            obsVO.setItDirty(false);
            obsCollection.add(obsVO);
            setObsValueCodedToDefaultValues(obsCollection);
            }// End of while loop

          }
        return tempID;
      }


  private int temp214ObservationVO(Collection<ObservationVO>  obsColl,Collection<ObservationVO>  outColl, int tempID){
    ObservationVO lab112 = findObservationByCode(obsColl, "LAB112");
    ObservationVO temp214 = new ObservationVO();
    temp214.setItNew(true);
    temp214.setTheObservationDT(new ObservationDT());
    temp214.getTheObservationDT().setItNew(true);
    temp214.getTheObservationDT().setObservationUid(new Long(tempID--));
    temp214.getTheObservationDT().setCd("NI");
    temp214.getTheObservationDT().setStatusCd("D");
    temp214.getTheObservationDT().setCdDescTxt("No Information Given");
    temp214.getTheObservationDT().setCdSystemCd("2.16.840.1.113883");
    temp214.getTheObservationDT().setCdSystemDescTxt(NEDSSConstants.LABCOMMENT);
    temp214.getTheObservationDT().setObsDomainCdSt1(NEDSSConstants.C_ORDER);
    temp214.getTheObservationDT().setCtrlCdDisplayForm(NEDSSConstants.LAB_REPORT_DESC);

    if(lab112 != null){
      temp214.getTheObservationDT().setEffectiveFromTime(lab112.getTheObservationDT().getEffectiveFromTime());
      temp214.getTheObservationDT().setActivityToTime(lab112.getTheObservationDT().getActivityToTime());
      temp214.getTheObservationDT().setRptToStateTime(lab112.getTheObservationDT().getRptToStateTime());
    }
    outColl.add(temp214);
    return tempID;
  }

   private void setActIdForCreate(Collection<ObservationVO>  obsColl,Collection<Object>  outColl,  HttpServletRequest request){
     ObservationVO lab112 = findObservationByCode(obsColl, "LAB112");
     Collection<Object>  collLab112 = lab112.getTheActIdDTCollection();
    if( collLab112 != null){
     Iterator<Object>  itor = collLab112.iterator();
      while (itor.hasNext())
      {
        ActIdDT actIdDT = (ActIdDT)itor.next();
        actIdDT.setActUid(lab112.getTheObservationDT().getObservationUid());
        //actIdDT.setAssigningAuthorityCd("CLIA");


		    CommonLabUtil labUtil = new CommonLabUtil();

				TreeMap<?,?> treeMap = (TreeMap<?,?>)labUtil.getCodeSystemDescription(request.getParameter("labId"), request.getSession());
				actIdDT.setAssigningAuthorityCd(request.getParameter("labId"));
        actIdDT.setAssigningAuthorityDescTxt((String)treeMap.get(NEDSSConstants.KEY_CODESYSTEMDESCTXT));
        actIdDT.setRecordStatusCd("ACTIVE");
        actIdDT.setRootExtensionTxt(actIdDT.getRootExtensionTxt());
        actIdDT.setStatusCd("A");
        actIdDT.setStatusTime(new Timestamp(new Date().getTime()));
        actIdDT.setTypeCd("FN");
        actIdDT.setTypeDescTxt("Filler Number");
        actIdDT.setItNew(true);
        actIdDT.setActIdSeq(new Integer(0));
        outColl.add(actIdDT);
      }
    }
   }

   private int setMaterialForCreate(Collection<Object>  inColl,
                                            Collection<Object>  outColl,int tempID){
    if(inColl != null){
     Iterator<Object>  itor = inColl.iterator();
      while (itor.hasNext())
      {
        MaterialVO materialVO = (MaterialVO)itor.next();
        if(materialVO.getTheMaterialDT() != null){
           MaterialDT materialDT = materialVO.getTheMaterialDT();
           materialDT.setMaterialUid(new Long(tempID--));
           String descTxt = getDescTxt("SPECMN_SRC",materialDT.getCd());
           materialDT.setCdDescTxt(descTxt);
           materialDT.setItNew(true);
           materialVO.setItNew(true);
           materialVO.setItDirty(false);
           outColl.add(materialDT);
        }
      } // End of while loop
    }// if loop
    return tempID;
   }

   private EntityLocatorParticipationDT genericEntityLocatorForCreate(String cd, String cdDescTxt,String classCd,
                                             Long entityUID,Long locatorUID,
                                             String recordStatusCD,String statusCD,
                                             String useCD){

        EntityLocatorParticipationDT elpDT = new EntityLocatorParticipationDT();
        PostalLocatorDT plDT = new PostalLocatorDT();
        TeleLocatorDT tlDT = new TeleLocatorDT();
        return elpDT;
    }




   private void setActRelationshipForCreate(Collection<ObservationVO>  obsColl,
                                            Collection<Object>  actRelationColl)
   {
        ObservationVO lab112 = findObservationByCode(obsColl, "LAB112");
        ObservationVO temp214 = findObservationByCode(obsColl, "NI");
        if (obsColl != null)
        {
           Iterator<ObservationVO>  itor = obsColl.iterator();
            while (itor.hasNext())
            {
                ObservationVO obsVO = (ObservationVO)itor.next();
                if(obsVO.getTheObservationDT().getCd() != null){
                  if (obsVO.getTheObservationDT().getCd().equals("NI") && lab112 != null) {
                    ActRelationshipDT act130 = genericActRelationShipCreate(
                        NEDSSConstants.RECORD_STATUS_ACTIVE,
                        obsVO.getTheObservationDT().getObservationUid(),
                        NEDSSConstants.CLASS_CD_OBS,
                        NEDSSConstants.STATUS_ACTIVE,
                        lab112.getTheObservationDT().getObservationUid(),
                        NEDSSConstants.CLASS_CD_OBS, NEDSSConstants.APND,
                        NEDSSConstants.APPENDS);

                    actRelationColl.add(act130);
                    logger.debug(
                        "parent_obs_uid: " +
                        lab112.getTheObservationDT().getObservationUid() +
                        "child_obs_uid : " +
                        obsVO.getTheObservationDT().getObservationUid());
                  }
                }
                if(obsVO.getTheObservationDT().getCd() != null){
                  if (obsVO.getTheObservationDT().getCd().equals("LAB214") && temp214 != null) {
                    ActRelationshipDT act130temp = genericActRelationShipCreate(
                        NEDSSConstants.RECORD_STATUS_ACTIVE,
                        obsVO.getTheObservationDT().getObservationUid(),
                        NEDSSConstants.CLASS_CD_OBS,
                        NEDSSConstants.STATUS_ACTIVE,
                        temp214.getTheObservationDT().getObservationUid(),
                        NEDSSConstants.CLASS_CD_OBS, NEDSSConstants.COMP,
                        NEDSSConstants.IS_CAUSE_FOR);

                    actRelationColl.add(act130temp);
                    logger.debug(
                        "parent_obs_uid: " +
                        lab112.getTheObservationDT().getObservationUid() +
                        "child_obs_uid : " +
                        obsVO.getTheObservationDT().getObservationUid());
                  }
                }
                if(obsVO.getTheObservationDT().getCd() != null){
                    if (obsVO.getTheObservationDT().getCd().equals("LAB235") && lab112 != null) {
                      ActRelationshipDT act130temp = genericActRelationShipCreate(
                          NEDSSConstants.RECORD_STATUS_ACTIVE,
                          obsVO.getTheObservationDT().getObservationUid(),
                          NEDSSConstants.CLASS_CD_OBS,
                          NEDSSConstants.STATUS_ACTIVE,
                          lab112.getTheObservationDT().getObservationUid(),
                          NEDSSConstants.CLASS_CD_OBS, NEDSSConstants.ACT_TYPE_PROCESSING_DECISION,
                          NEDSSConstants.ACT_TYPE_PROCESSING_DECISION);

                      actRelationColl.add(act130temp);
                      logger.debug(
                          "parent_obs_uid: " +
                          lab112.getTheObservationDT().getObservationUid() +
                          "child_obs_uid : " +
                          obsVO.getTheObservationDT().getObservationUid());
                    }
                  }
            }//while (itor.hasNext())
        }//if (obsColl != null)
    }

    /*
     *   checks each observation collection & ensures that for each, each
     *     obsvaluecodedDT has at least a default value.   This will not
     *     overwrite default values.
     */
    private void setObsValueCodedToDefaultValues(Collection<ObservationVO>  observations)
    {

       Iterator<ObservationVO>  collItor = observations.iterator();

        if (collItor == null)

            return;

        String _blank = "";

        while (collItor.hasNext())
        {

            ObservationVO obsVO = (ObservationVO)collItor.next();
            Collection<Object>  obsValDTs = obsVO.getTheObsValueCodedDTCollection();

            if (obsValDTs == null)

                continue;

           Iterator<Object>  dtItor = obsValDTs.iterator();

            while (dtItor.hasNext())
            {

                ObsValueCodedDT dt = (ObsValueCodedDT)dtItor.next();

                if ((dt.getCode() == null) || (dt.getCode().trim().length() == 0))
                    dt.setCode("NI");
            }
        }
    }

    private ParticipationDT createParticipation(String actUid, String subjectEntityUid,
                                                String subjectClassCd, String typeCd)
    {

        ParticipationDT participationDT = new ParticipationDT();
        participationDT.setActClassCd(NEDSSConstants.CLASS_CD_CASE);
        participationDT.setActUid(new Long(actUid));
        participationDT.setSubjectClassCd(subjectClassCd);
        participationDT.setSubjectEntityUid(new Long(subjectEntityUid.trim()));
        participationDT.setTypeCd(typeCd);
        participationDT.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
        participationDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
        participationDT.setStatusTime(new java.sql.Timestamp(new Date().getTime()));
        participationDT.setItNew(true);
        //participationDT.setItDirty(false);

        return participationDT;
    }




   private static ObservationVO findObservationByCode(Collection<ObservationVO>  coll, String strCode)
   {
     if (coll == null)
       return null;

    Iterator<ObservationVO>  itor = coll.iterator();

     while (itor.hasNext()) {
       ObservationVO obsVO = (ObservationVO) itor.next();
       ObservationDT obsDT = obsVO.getTheObservationDT();

       if (obsDT == null)
         continue;

       if (obsDT.getCd() == null) {
         continue;
       }
       if (obsDT.getCd().trim().equalsIgnoreCase(strCode.trim()))
         return obsVO; // found it!
     }

     // didn't find one
     return null;
   }

   /**
    *
    * @param obsCOll Collection<Object>  object
    */
   private void setLAB112(Collection<ObservationVO>  obsCOll){
    ObservationVO obsVOLab112 = findObservationByCode(obsCOll, "LAB112");
    ObservationDT obsDT = obsVOLab112.getTheObservationDT();
    if(obsDT.getHiddenCd()!= null && !obsDT.getHiddenCd().equals("")){
                  obsDT.setCd(obsDT.getHiddenCd());
     }
     /*
        Setting the code for "No Information Given" for Lab112, Added the Cd in the Property File
      */
     else if(obsDT.getCdDescTxt().equalsIgnoreCase(NEDSSConstants.NOINFORMATIONGIVEN))
     {
       PropertyUtil propertyUtil = PropertyUtil.getInstance();
       String lab112Cd =  propertyUtil.getLab112Cd();
       obsDT.setCd(lab112Cd);
     }
    else if(obsDT.getTxt()!= null && !obsDT.getTxt().equals("")) {    	
		obsDT.setCd(obsDT.getTxt());
		CachedDropDownValues cdv = new CachedDropDownValues();
		String desc = cdv.getLabTestDesc(obsDT.getTxt());
		if(desc != "")
			obsDT.setCdDescTxt(desc);
        obsDT.setTxt("");
    }

  }
  private PersonVO findMasterPatientRecord(PersonVO personVO,Long mprUId, HttpSession session, NBSSecurityObj secObj)
  {

     PersonVO tempPersonVO = null;
     MainSessionCommand msCommand = null;

     try
     {

         String sBeanJndiName = JNDINames.ENTITY_PROXY_EJB;
         String sMethod = "getMPR";
         Object[] oParams = new Object[] { mprUId };
         MainSessionHolder holder = new MainSessionHolder();
         msCommand = holder.getMainSessionCommand(session);

         ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
         tempPersonVO = (PersonVO)arr.get(0);
         if(personVO != null && tempPersonVO != null)
           personVO.getThePersonDT().setLocalId(tempPersonVO.getThePersonDT().getLocalId());
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

     return personVO;
 }//findMasterPatientRecord

 private String getDescTxt(String srt,String code){
  String descTxt=null;
  try{
  CachedDropDownValues cdv = new CachedDropDownValues();
  descTxt= cdv.getDescForCode(srt,code);
  }catch(Exception e){
    //System.out.println("Error found");
  }
  return descTxt;
 }
 private String getObsLocalID(Long uid, HttpSession session) throws
        NEDSSAppConcurrentDataException, java.rmi.RemoteException,
        javax.ejb.EJBException, Exception
    {

      MainSessionCommand msCommand = null;
      String sBeanJndiName = JNDINames.OBSERVATION_PROXY_EJB;
      String sMethod = null;
      Long obsUid = null;
      MainSessionHolder holder = new MainSessionHolder();
      ArrayList<?> resultUIDArr = null;
      msCommand = holder.getMainSessionCommand(session);
      sMethod = "getObservationLocalID";
      Object[] oParams = {uid};
      resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
      String localID = (String) resultUIDArr.get(0);
      return localID;
    }

    /**
     * @method      : getLabResultProxyVO
     * @params      : String, HttpSession
     * @returnType  : LabResultProxyVO
     */
    private LabResultProxyVO getLabResultProxyVO(Long observationUID,
                                                HttpSession session) {
      LabResultProxyVO labResultProxyVO = null;
      MainSessionCommand msCommand = null;

      if (observationUID == null) {
        logger.error("observationUID is null inside getLabResultProxyVO method");
        return null;
      }
      else if (observationUID != null) {
        try {
          logger.debug("observationUID inside getLabResultProxyVO method is: " +
                       observationUID);

          String sBeanJndiName = JNDINames.OBSERVATION_PROXY_EJB;
          String sMethod = "getLabResultProxy";
          Object[] oParams = new Object[] {
              observationUID};
          MainSessionHolder holder = new MainSessionHolder();
          msCommand = holder.getMainSessionCommand(session);

          ArrayList<?> arr = msCommand.processRequest(sBeanJndiName,
                                                   sMethod, oParams);
          labResultProxyVO = (LabResultProxyVO) arr.get(0);
        }
        catch (Exception ex) {

          if (session == null) {
            logger.debug("Error: no session, please login");

          }
          logger.fatal("getLabResultProxyVO: ", ex);
        }
      } 
      return labResultProxyVO;
    }

    private int setTheTrackingInformation(ObservationVO lab112VO, ResultedTestVO resultedTestVO, int tempID, 
    		Collection<Object>  actRelationColl, Collection<ObservationVO>  obsCollection){
    	try{
            Collection<Object>  observationVOColl = resultedTestVO.getTheTrackIsolateCollection();
            if (observationVOColl != null) {
            	boolean breakOutOfLoop = false;
            	ObservationVO obs329VO =null;
               	ObservationDT obsDT329DT =null;
            	Iterator<Object> obsVOIt = observationVOColl.iterator();
                while (obsVOIt.hasNext()) {
               	breakOutOfLoop = false;
               	ObservationVO obsVO = (ObservationVO) obsVOIt.next();
               	
               	if((obsVO.getObsValueCodedDT_s(0)==null) || 
               			(obsVO.getObsValueCodedDT_s(0)!=null && 
               					((obsVO.getObsValueCodedDT_s(0).getCode()==null || obsVO.getObsValueCodedDT_s(0).getCode().equalsIgnoreCase("")))))
               	{
               		obsVO.setTheObsValueCodedDTCollection(null);
               	}
                if((obsVO.getObsValueTxtDT_s(0)==null 
                		|| (obsVO.getObsValueTxtDT_s(0)!=null &&
                				(obsVO.getObsValueTxtDT_s(0).getValueTxt()==null)||(obsVO.getObsValueTxtDT_s(0).getValueTxt()!=null)&& obsVO.getObsValueTxtDT_s(0).getValueTxt().equalsIgnoreCase("")))){
               		obsVO.setTheObsValueTxtDTCollection(null);
               	}
                if((obsVO.getObsValueDateDT_s(0)==null 
                		|| (obsVO.getObsValueDateDT_s(0)!=null && obsVO.getObsValueDateDT_s(0).getFromTime()==null)
                				||(obsVO.getObsValueDateDT_s(0).getFromTime()!=null && obsVO.getObsValueDateDT_s(0).getFromTime().getTime()<=0))){
                	obsVO.setTheObsValueDateDTCollection(null);
                }
                if(obsVO.getTheObsValueCodedDTCollection()==null 
                		&& obsVO.getTheObsValueDateDTCollection()==null
                		&& obsVO.getTheObsValueTxtDTCollection()==null
                		&& !obsVO.getTheObservationDT().getCd().equalsIgnoreCase("NOLAB329ADDED")
                		&& !obsVO.getTheObservationDT().getCd().equalsIgnoreCase(NEDSSConstants.LAB_329)
                		)
                {
                	continue;
                }
                else if(obsVO.getTheObservationDT().getCd().equalsIgnoreCase("NOLAB329ADDED"))
              		break;
            	if(obsVO.getTheObservationDT().getCd().equalsIgnoreCase(NEDSSConstants.LAB_329)){
            	//Setting up parent Observation 329a for isolate VOS
            	obs329VO=obsVO;
            	obsDT329DT = obs329VO.getTheObservationDT();
            	obsDT329DT.setObservationUid(new Long(tempID--));
            	obsDT329DT.setCd(NEDSSConstants.LAB_329);
            	obsDT329DT.setCdSystemCd(NEDSSConstants.CD_SYSTEM_CD_NBS);
            	obsDT329DT.setCdDescTxt(NEDSSConstants.LAB329CD_DESC_TXT);
            	obsDT329DT.setCdSystemDescTxt(NEDSSConstants.NEDSS_BASE_SYSTEM);
            	obsDT329DT.setEffectiveFromTime(lab112VO.getTheObservationDT().getEffectiveFromTime());
            	obsDT329DT.setEffectiveToTime(lab112VO.getTheObservationDT().getEffectiveToTime());
            	obsDT329DT.setStatusCd(NEDSSConstants.STATUS_CD_D);
            	obsDT329DT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
            	//obsDT329ADT.setCtrlCdDisplayForm(NEDSSConstants.LAB_REPORT);
            	obsDT329DT.setObsDomainCdSt1(NEDSSConstants.I_ORDER);
                obsDT329DT.setItNew(true);
                obs329VO.setTheObservationDT(obsDT329DT);
                obs329VO.setItNew(true);
                obsCollection.add(obs329VO);
                //ACT115 between lab112 and 329A 
            	actRelationColl.add(genericActRelationShipCreate(NEDSSConstants.RECORD_STATUS_ACTIVE, obsDT329DT.getObservationUid(),
            			NEDSSConstants.CLASS_CD_OBS, NEDSSConstants.STATUS_ACTIVE,
            			lab112VO.getTheObservationDT().getObservationUid(), NEDSSConstants.CLASS_CD_OBS, 
            			NEDSSConstants.TYPE_CD_115, NEDSSConstants.TYPE_DESC_TXT_115));
              	//ACT109 between 329 and Organism 
                actRelationColl.add(genericActRelationShipCreate(NEDSSConstants.RECORD_STATUS_ACTIVE, obsDT329DT.getObservationUid(),
            			NEDSSConstants.CLASS_CD_OBS, NEDSSConstants.STATUS_ACTIVE,
            			resultedTestVO.getTheIsolateVO().getTheObservationDT().getObservationUid(), NEDSSConstants.CLASS_CD_OBS, 
          			NEDSSConstants.ACT109_TYP_CD, NEDSSConstants.TYPE_DESC_TXT_109));
            	}
            	else{
                  if(obsVO.getTheObservationDT().getCd()==null || 
                		 obsVO.getTheObservationDT().getCd()!=null && obsVO.getTheObservationDT().getCd().trim().equalsIgnoreCase(""))
                  {
                	  continue;
                  }
                  ObservationDT obsTrackDT = obsVO.getTheObservationDT();

                  obsTrackDT.setObservationUid(new Long(tempID--));
                  
                  if(obsVO.getTheObservationDT().getCd().equalsIgnoreCase(NEDSSConstants.LAB_329)){

                  //obsTrackDT.setCdDescTxt(NEDSSConstants.LAB329CD_DESC_TXT);
                  obsTrackDT.setCdSystemDescTxt(NEDSSConstants.NEDSS_BASE_SYSTEM);
                  obsTrackDT.setEffectiveFromTime(lab112VO.getTheObservationDT().getEffectiveFromTime());
                  obsTrackDT.setActivityToTime(lab112VO.getTheObservationDT().getActivityToTime());
                  obsTrackDT.setStatusCd(NEDSSConstants.STATUS_CD_D);
                  obsTrackDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
                  }
                  obsTrackDT.setCdSystemCd(NEDSSConstants.CD_SYSTEM_CD_NBS);
                  obsTrackDT.setObsDomainCdSt1(NEDSSConstants.I_RESULT);
                  
				  
                  if (obsVO.getTheObsValueCodedDTCollection()!= null) {
                      if (obsVO.getObsValueCodedDT_s(0).getCode()!=null 
                    		 && obsVO.getObsValueCodedDT_s(0).getCode().trim().length() == 0){
                    	  breakOutOfLoop = true;
                      }
                      else{
                    	  if(obsVO.getTheObservationDT().getCd().equalsIgnoreCase(NEDSSConstants.LAB_329) 
                    			  ||obsVO.getTheObservationDT().getCd().equalsIgnoreCase(NEDSSConstants.LAB_331) 
                    			  || obsVO.getTheObservationDT().getCd().equalsIgnoreCase(NEDSSConstants.LAB_336)
                    			  || obsVO.getTheObservationDT().getCd().equalsIgnoreCase(NEDSSConstants.LAB_337) 
                    			  || obsVO.getTheObservationDT().getCd().equalsIgnoreCase(NEDSSConstants.LAB_338)
                    			  ||obsVO.getTheObservationDT().getCd().equalsIgnoreCase(NEDSSConstants.LAB_345)
                    			  || obsVO.getTheObservationDT().getCd().equalsIgnoreCase(NEDSSConstants.LAB_346) 
                			  || obsVO.getTheObservationDT().getCd().equalsIgnoreCase(NEDSSConstants.LAB_358) 
                			  || obsVO.getTheObservationDT().getCd().equalsIgnoreCase(NEDSSConstants.LAB_363))
                              		  obsVO.getObsValueCodedDT_s(0).setCodeSystemCd(NEDSSConstants.CODED_CD_SYSTEM_CD_HL7);
                    	  
                    	  else if(obsVO.getTheObservationDT().getCd().equalsIgnoreCase(NEDSSConstants.LAB_332))
                    		  obsVO.getObsValueCodedDT_s(0).setCodeSystemCd(NEDSSConstants.CODED_CD_SYSTEM_CD);
                    	  else if(obsVO.getTheObservationDT().getCd().equalsIgnoreCase(NEDSSConstants.LAB_347) )
                    		  obsVO.getObsValueCodedDT_s(0).setCodeSystemCd(NEDSSConstants.CODED_CD_SYSTEM_CD_LAB347);
                    	  
                    	  else if(obsVO.getTheObservationDT().getCd().equalsIgnoreCase(NEDSSConstants.LAB_353)  
                    			  ||obsVO.getTheObservationDT().getCd().equalsIgnoreCase(NEDSSConstants.LAB_359))
                    		  obsVO.getObsValueCodedDT_s(0).setCodeSystemCd(NEDSSConstants.CODED_CD_SYSTEM_CD_LAB359_LAB353);  
                    	  
                    	  else if(obsVO.getTheObservationDT().getCd().equalsIgnoreCase(NEDSSConstants.LAB_352)  
                    			  && (obsVO.getObsValueCodedDT_s(0).getCode().equalsIgnoreCase("Y") 
                    					  || obsVO.getObsValueCodedDT_s(0).getCode().equalsIgnoreCase("N") ))
                    		  obsVO.getObsValueCodedDT_s(0).setCodeSystemCd(NEDSSConstants.CODED_CD_SYSTEM_CD_LAB352_YN);
                    	  else if(obsVO.getTheObservationDT().getCd().equalsIgnoreCase(NEDSSConstants.LAB_352)  
                    			  && !(obsVO.getObsValueCodedDT_s(0).getCode().equalsIgnoreCase("Y") 
                    					  || obsVO.getObsValueCodedDT_s(0).getCode().equalsIgnoreCase("N") ))
                    		  	obsVO.getObsValueCodedDT_s(0).setCodeSystemCd(NEDSSConstants.CODED_CD_SYSTEM_CD_LAB352_ELSE);
                    	  else if(obsVO.getTheObservationDT().getCd().equalsIgnoreCase(NEDSSConstants.LAB_355) )
                    		  obsVO.getObsValueCodedDT_s(0).setCodeSystemCd(NEDSSConstants.CODED_CD_SYSTEM_CD_LAB355);
                    	  
                    	  
                    	  //obsVO.getObsValueCodedDT_s(0).setCodeSystemDescTxt(NEDSSConstants.CODED_SYSTEM_DESC_TXT);
                    	  obsVO.getObsValueCodedDT_s(0).setItNew(true);
                    	  CommonLabUtil labUtil = new CommonLabUtil();
                          labUtil.getDIsplayValueOfDropDownForIsolate(obsVO);
                      }
                    }
                  if (obsVO.getTheObsValueTxtDTCollection()!= null) {
                        if(obsVO.getObsValueTxtDT_s(0).getValueTxt()==null ||
                        		(obsVO.getObsValueTxtDT_s(0).getValueTxt()!=null 
                        				&& obsVO.getObsValueTxtDT_s(0).getValueTxt().trim().equals(""))){
                    	  breakOutOfLoop = true;
                      }else
                    	  obsVO.getObsValueTxtDT_s(0).setItNew(true);
                      }
                  if (obsVO.getTheObsValueDateDTCollection()!= null) {
                        if(obsVO.getObsValueDateDT_s(0).getFromTime()==null && 
                        		(obsVO.getObsValueDateDT_s(0).getFromTime()!=null && obsVO.getObsValueDateDT_s(0).getFromTime().toString().trim().equals(""))){
                    	  breakOutOfLoop = true;
                        }else
                        	obsVO.getObsValueDateDT_s(0).setItNew(true);
                      }
                  if(breakOutOfLoop) 
                      continue;
                     
  		    	//ACT110 between 329a and child observations
				  actRelationColl.add(genericActRelationShipCreate(NEDSSConstants.RECORD_STATUS_ACTIVE, obsTrackDT.getObservationUid(),
						NEDSSConstants.CLASS_CD_OBS, NEDSSConstants.STATUS_ACTIVE,
						obsDT329DT.getObservationUid(), NEDSSConstants.CLASS_CD_OBS, 
						NEDSSConstants.TYPE_CD_110, NEDSSConstants.TYPE_DESC_TXT_110));
  

                  //obsTrackDT.setCtrlCdDisplayForm(NEDSSConstants. LAB_REPORT);
                  obsTrackDT.setStatusTime(new java.sql.Timestamp(new Date(). getTime()));
                  obsTrackDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
                  obsTrackDT.setItNew(true);
                  obsVO.setTheObservationDT(obsTrackDT);
                  obsVO.setItNew(true);
                  obsCollection.add(obsVO);
                  setObsValueCodedToDefaultValues(obsCollection);
                
              }
            } // end of if (observationVOColl != null)
            } 

        } catch(Exception ex){
            logger.error("Error thrown within setThetrackingInformation:"+ex);	
        }
        return tempID;
    }
    
    private int setPatientTrackingVO(ObservationVO lab112VO, ObservationVO patientStatusVO, int tempID, 
    		Collection<Object>  actRelationColl, Collection<ObservationVO>  obsCollection){
    	try{
            
            	ObservationDT obsDT330ADT = patientStatusVO.getTheObservationDT();
            	obsDT330ADT.setCdSystemCd(NEDSSConstants.CD_SYSTEM_CD_NBS);
            	obsDT330ADT.setCdDescTxt(NEDSSConstants.LAB_330_CD_DESC_TXT);
            	obsDT330ADT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
            	obsDT330ADT.setObsDomainCdSt1(NEDSSConstants.PATIENT_STATUS_OBS_DOMAIN_CD);
            	//ACT108 between lab112 and 108 
            	  
            	if (patientStatusVO.getObsValueCodedDT_s(0)== null || 
            			( patientStatusVO.getObsValueCodedDT_s(0)!=null && 
            					(patientStatusVO.getObsValueCodedDT_s(0).getCode()==null || 
            							(patientStatusVO.getObsValueCodedDT_s(0).getCode()!=null && patientStatusVO.getObsValueCodedDT_s(0).getCode().trim().equalsIgnoreCase(""))))) {
                	return tempID;
                    }else{
                    	obsDT330ADT.setItNew(true);
                    	patientStatusVO.getObsValueCodedDT_s(0).setItNew(true);
                    }
                patientStatusVO.getObsValueCodedDT_s(0).setCodeSystemCd(NEDSSConstants.CD_SYSTEM_CD_NBS);
              	obsDT330ADT.setObservationUid(new Long(tempID--));
                actRelationColl.add(genericActRelationShipCreate(
                        NEDSSConstants.RECORD_STATUS_ACTIVE,
                        obsDT330ADT.getObservationUid(),
                        NEDSSConstants.CLASS_CD_OBS,
                        NEDSSConstants.STATUS_ACTIVE,
                        lab112VO.getTheObservationDT().getObservationUid(),
                        NEDSSConstants.CLASS_CD_OBS,
                        NEDSSConstants.ACT108_TYP_CD,
                        NEDSSConstants.HAS_COMPONENT));
                CommonLabUtil labUtil = new CommonLabUtil();
                labUtil.getDIsplayValueOfDropDownForIsolate(patientStatusVO);
                patientStatusVO.setItNew(true);
             	obsCollection.add(patientStatusVO);
             
             } catch(Exception ex){
            logger.error("Error thrown within setThetrackingInformation:"+ex);	
        }
        return tempID;
    }
    
  
}
