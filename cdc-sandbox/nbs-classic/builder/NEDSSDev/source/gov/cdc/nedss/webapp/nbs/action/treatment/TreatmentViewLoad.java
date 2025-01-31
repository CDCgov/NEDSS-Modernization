package gov.cdc.nedss.webapp.nbs.action.treatment;

import java.io.*;
import java.util.*;

import javax.rmi.*;
import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;

import gov.cdc.nedss.act.treatment.dt.*;
import gov.cdc.nedss.act.treatment.vo.*;
import gov.cdc.nedss.association.dt.*;
import gov.cdc.nedss.entity.organization.dt.*;
//import gov.cdc.nedss.entity.material.vo.*;
//import gov.cdc.nedss.entity.material.dt.*;
import gov.cdc.nedss.entity.organization.vo.*;
//import gov.cdc.nedss.entity.vo.*;
import gov.cdc.nedss.entity.person.dt.*;
import gov.cdc.nedss.entity.person.vo.*;
import gov.cdc.nedss.locator.dt.*;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.bean.ObservationProxyHome;
import gov.cdc.nedss.proxy.ejb.treatmentproxyejb.bean.TreatmentProxyHome;
import gov.cdc.nedss.proxy.ejb.treatmentproxyejb.vo.*;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.systemservice.ejb.srtmapejb.bean.*;
import gov.cdc.nedss.systemservice.nbscontext.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.systemservice.util.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.webapp.nbs.action.person.util.*;
import gov.cdc.nedss.webapp.nbs.action.util.*;
import gov.cdc.nedss.webapp.nbs.form.treatment.*;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.*;
import gov.cdc.nedss.proxy.ejb.treatmentproxyejb.vo.TreatmentProxyVO;
import gov.cdc.nedss.webapp.nbs.action.ldf.BaseLdf;
import gov.cdc.nedss.webapp.nbs.servlet.NedssCodeLookupServlet;

/**
 * Title:        TreatmentLoad
 * Description:  this class retrieves data from EJB and puts them into request object
 * for use in the xml file.
 * Copyright:    Copyright (c) 2001-2002
 * Company:      CSC
 * @author       Aaron Aycock
 * @version 1.0
 */

public class TreatmentViewLoad
    extends BaseLdf {
   static final LogUtils logger = new LogUtils(TreatmentViewLoad.class.getName());
   static final QuickEntryEventHelper helper = new QuickEntryEventHelper();

   boolean bReports = false;
   public TreatmentViewLoad() {
   }

   /**
        * An ActionForward represents a destination to which the controller servlet,
    * ActionServlet, might be directed to execute a RequestDispatcher.forward()
        * or HttpServletResponse.sendRedirect() to, as a result of processing activities
    * of an Action class. This is the only public method in TreatmentLoad class
    * that handles the HttpServletRequest request object with ActionMapping and actionForm to generate
    * HttpServletResponse response object.
    * @param mapping ActionMapping object
    * @param aForm ActionForm object
    * @param request HttpServletRequest  object
    * @param response HttpServletResponse  object
    * @throws IOException
    * @throws ServletException
    * @return : ActionForward object
    */
   public ActionForward execute(ActionMapping mapping, ActionForm aForm,
                                HttpServletRequest request,
                                HttpServletResponse response) throws
       IOException, ServletException {


      String sPersonUID = "";
      logger.debug("inside the TreatmentLoad");

      TreatmentForm form = (TreatmentForm) aForm;
      //security checks including check for session
      HttpSession session = request.getSession(false);
      if (session == null) {
         logger.debug("error no session");
         throw new ServletException("error no session");
      }

      NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session.getAttribute(
          "NBSSecurityObject");
      if (nbsSecurityObj == null) {
         logger.fatal(
             "Error: No securityObj in the session, go back to login screen");
         return mapping.findForward("login");
      }

      // Get Context Action
      String contextAction = request.getParameter("ContextAction");
      if (contextAction == null) {
         contextAction = (String) request.getAttribute("ContextAction");
      }


      logger.debug("TreatmentLoad: ContextAction = " + contextAction);

      Long mprUid = (Long) NBSContext.retrieve(session, "DSPatientPersonUID");
     // String conditionCode = (String)NBSContext.retrieve(session,NBSConstantUtil.DSConditionCode);
      request.setAttribute("patientPersonLocalID",PersonUtil.getDisplayLocalID((String) NBSContext.retrieve(session,
          "DSPatientPersonLocalID")));
      String strUID = null;

      strUID = request.getParameter("treatmentUID");
      // Get it from attribute if treatmentUID is not found in parameter
      if (strUID == null) {
         strUID = (String) request.getAttribute("treatmentUID");

      } // Get it from object store if treatmentUID is still null
      if (strUID == null) {
         strUID = (String) NBSContext.retrieve(session, "DSTreatmentUID");
      }
      NBSContext.store(session, "DSTreatmentUID", new Long(strUID));
      
      request.setAttribute("DSTreatmentUID", strUID);
      
      logger.debug("TreatmentLoad: treatmentUID = " + strUID);



      /*   PersonVO SubjectPatient = new PersonVO();
         SubjectPatient.getThePersonDT().setPersonUid(pvo.getThePersonDT().getPersonUid());
         SubjectPatient.setItNew(false);
         SubjectPatient.setItDirty(false);
       */

      logger.debug("The Operation strUID :" + strUID);

      if (contextAction.equalsIgnoreCase(NBSConstantUtil.DELETE)) {
         logger.debug("The Operation Type is delete");
         TreatmentProxyVO proxy = getOldProxyObject(strUID, form, session);
         if (bReports) {

            return (mapping.findForward("delete"));
         }
      }

      boolean check1 = nbsSecurityObj.getPermission(NBSBOLookup.TREATMENT,
          NBSOperationLookup.VIEW);
      if (!check1) {
         logger.fatal("Error: Do not have permission to view treatment.");
         throw new ServletException("Error: Do not have permission to view treatment.");
      }
      else {
         NBSContext.store(session, "DSTreatmentUID", strUID);
         // context layer
         TreeMap<Object,Object> tm = NBSContext.getPageContext(session, "PS201", contextAction);
         String sCurrTask = NBSContext.getCurrentTask(session);

         //NBSContext.lookInsideTreeMap(tm);
         request.setAttribute("sCurrTask", sCurrTask);
         request.setAttribute("editButtonHref",
                              "/nbs/" + sCurrTask + ".do?ContextAction=" +
                              tm.get("Edit"));
         request.setAttribute("deleteButtonHref",
                              "/nbs/" + sCurrTask + ".do?ContextAction=" +
                              tm.get("Delete"));
         request.setAttribute("PrintPage", "/nbs/LoadViewTreatment1.do?ContextAction=PrintPage");

         if (tm.get("ReturnToFileEvents") != null) {
            request.setAttribute("ReturnToFileEvents",
                                 "/nbs/" + sCurrTask + ".do?ContextAction=" +
                                 tm.get("ReturnToFileEvents"));
         }
         if (tm.get("ReturnToManageTreatments") != null) {
            request.setAttribute("ReturnToManageTreatments",
                                 "/nbs/" + sCurrTask + ".do?ContextAction=" +
                                 tm.get("ReturnToManageTreatments"));
         }
         if (tm.get("ReturnToViewInvestigation") != null) {
            request.setAttribute("ReturnToViewInvestigation",
                                 "/nbs/" + sCurrTask + ".do?ContextAction=" +
                                 tm.get("ReturnToViewInvestigation"));

         }
         if (tm.get("ManageEvents") != null) {
             request.setAttribute("ManageEvents",
                                  "/nbs/" + sCurrTask + ".do?ContextAction=" +
                                  tm.get("ManageEvents"));

          }
         request.setAttribute("fileName", String.valueOf(check1));
         boolean isAssociatedtWithMorb = isAssociatedWithMorb(new Long(strUID), request.getSession());
         boolean checkDelete = nbsSecurityObj.getPermission(NBSBOLookup.
             TREATMENT, NBSOperationLookup.MANAGE);

         if (checkDelete && !isAssociatedtWithMorb) {
           request.setAttribute("deletePerm", "true");
         }
         else {
           request.setAttribute("deletePerm", "false");
       }

         boolean checkEdit = nbsSecurityObj.getPermission(NBSBOLookup.TREATMENT,
             NBSOperationLookup.MANAGE);

         if(checkEdit && !isAssociatedtWithMorb)
         {
           request.setAttribute("editPerm", "true");
         }
         else
         {
           request.setAttribute("editPerm", "false");
         }

         boolean checkViewWorkup = nbsSecurityObj.getPermission(NBSBOLookup.
             PATIENT, NBSOperationLookup.VIEWWORKUP);
         request.setAttribute("checkWorkup", String.valueOf(checkViewWorkup));
         logger.debug("\n\n\nstrUID is " + strUID);
         boolean checkViewInvestigation = nbsSecurityObj.getPermission(
             NBSBOLookup.INVESTIGATION, NBSOperationLookup.VIEW);
         request.setAttribute("checkViewInvestigation",
                              String.valueOf(checkViewInvestigation));

         /** @todo Add correct associate check here */boolean
             checkViewManageTreatment = nbsSecurityObj.getPermission(
             NBSBOLookup.INVESTIGATION,
             NBSOperationLookup.ASSOCIATEINTERVENTIONVACCINERECORDS);
         //boolean checkViewManageTreatment = nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION, NBSOperationLookup.ASSOCIATETREATMENTRECORDS);
         request.setAttribute("checkViewManageTreatment",
                              String.valueOf(checkViewManageTreatment));

         if (strUID.length() == 0 || strUID == null ||
             (new Long(strUID).longValue()) < 0) {
            logger.debug("UID value here is :" + strUID);
            strUID = form.getTreatmentVO().getTheTreatmentDT().getTreatmentUid().
                     toString();
         }
         //System.out.println("\n\n\nstrUID" + strUID);
         TreatmentProxyVO proxy = getOldProxyObject(strUID, form, session);

         if (bReports) {

            logger.debug("TreatmentProxy:inside VIEW" + proxy);
         }
         //System.out.println("\n\n\nOne");
         convertProxyToRequestObj(proxy, form, request, session, nbsSecurityObj);
         request.setAttribute("treatmentUID", strUID);
         /**
          * Rolled back from Rel1.1.3
          * Getting the treatment dropdown values from the new SRT filtering methods for Rel 1.1.3
          */
        /* String treatmentDropdown = NedssCodeLookupServlet.
         convertTreatmentToRequest(NEDSSConstants.TREATMENT_SRT_VALUES,
                                       NEDSSConstants.TREATMENT_SRT_EDIT, null,
                                       conditionCode);
         request.setAttribute("treatmentdropdown", treatmentDropdown); */

         //* Getting thr values for the Treatment Drugs *//
         /*String treatmentDrugsDropdown = NedssCodeLookupServlet.
         convertTreatmentToRequest(NEDSSConstants.TREATMENT_SRT_DRUGS,
                                       NEDSSConstants.TREATMENT_SRT_EDIT, null,
                                       conditionCode);
         request.setAttribute("teatmentDrugDropdown",treatmentDrugsDropdown); */



         createXSP(NEDSSConstants.TREATMENT_LDF, proxy.getTheTreatmentVO().getTheTreatmentDT().getTreatmentUid(), proxy, null, request ) ;
      }

      if(contextAction.equalsIgnoreCase("PrintPage")) {
        return new ActionForward("/treatment/treatment_print");
      }

      // Forward to XSP
      return mapping.findForward("XSP");

   }

   /**
    * This private method is used to get the TreatmentProxyVO object for a given strUID
    * @param strUID String object
    * @param form TreatmentForm object
    * @param session HttpSession object
    * @return TreatmentProxyVO object
    */
   private TreatmentProxyVO getOldProxyObject(String strUID, TreatmentForm form,
                                              HttpSession session) {
      TreatmentProxyVO proxy = null;
      MainSessionCommand msCommand = null;
      if (strUID != null) {
         try {
            Long UID = new Long(strUID);
            String sBeanJndiName = JNDINames.TREATMENT_PROXY_EJB;
            String sMethod = "getTreatmentProxy";
            Object[] oParams = new Object[] {UID};
            logger.debug("The treatmentUID is: " + UID);
            MainSessionHolder holder = new MainSessionHolder();
            msCommand = holder.getMainSessionCommand(session);
            ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod,
                oParams);

            proxy = (TreatmentProxyVO) arr.get(0);
            logger.debug("getOldProxyObject: got  TreatmentProxyVO: " + proxy);

            form.reset();
            form.setProxy(proxy);
         }
         catch (Exception ex) {
            if (session == null) {
               logger.error("Error: no session, please login");
            }
            logger.fatal("getOldProxyObject: ", ex);
         }
      }
      else { // if the request didn't povide the PHCuid, look in form
         //proxy = form.getProxy();
      }

      return proxy;
   }

   /**
    * This private method is used to convert TreatmentProxyVO to request object
    * @param proxy TreatmentProxyVO object
    * @param request HttpServletRequest object
    * @param session HttpSession object
    * @param nbsSecurityObj NBSSecurityObj object
    */
   private void convertProxyToRequestObj(TreatmentProxyVO proxy,
                                         TreatmentForm form,
                                         HttpServletRequest request,
                                         HttpSession session,
                                         NBSSecurityObj nbsSecurityObj) {
      CachedDropDownValues cddv = new CachedDropDownValues();
      String sStates = cddv.getStateCodes("1");


      //printAge and printAgeUnits information is REQUIRED for displaying patient Age as header on Printable Forms (PDF)
      PersonDT person = getPersonVO(NEDSSConstants.SUBJECT_OF_TREATMENT, proxy).getThePersonDT();
      String personAge = person.getAgeReported();
      String ageUnits = cddv.getDescForCode("P_AGE_UNIT", person.getAgeReportedUnitCd());
      request.setAttribute("printAge", (String) person.getAgeReported());
      request.setAttribute("printAgeUnits", ageUnits);

      TreatmentVO tVO = proxy.getTheTreatmentVO();

      if (tVO != null) {
         TreatmentDT tDT = tVO.getTheTreatmentDT();
         TreatmentAdministeredDT taDT = tVO.getTreatmentAdministeredDT_s(0);
         request.setAttribute("treatmentLocalID", tDT.getLocalId());
         //String allowedProgAreaCD = progAreaCdOnSecurity(session, secObj);
         //logger.debug("\n\n\n!!!!allowedProgAreaCD"+allowedProgAreaCD);
         //request.setAttribute("programAreaCd", allowedProgAreaCD );
         request.setAttribute("treatmentId", tDT.getCd());
         //request.setAttribute("dateAdministered",
                             // StringUtils.formatDate(tDT.getActivityFromTime()));

         request.setAttribute("programAreaCdStored", tDT.getProgAreaCd());
         request.setAttribute("dateAdministered",
                              StringUtils.formatDate(taDT.getEffectiveFromTime()));
         request.setAttribute("versioncontrolNumber", tDT.getVersionCtrlNbr());
         request.setAttribute("txt", tDT.getTxt());
         request.setAttribute("treatmentVO.theTreatmentDT.cdDescTxt",
                              tDT.getCdDescTxt());

         request.setAttribute("treatmentVO.treatmentAdministeredDT_s[0].cd",
                              taDT.getCd());
         request.setAttribute(
             "treatmentVO.treatmentAdministeredDT_s[0].doseQty",
             taDT.getDoseQty());
         request.setAttribute(
             "treatmentVO.treatmentAdministeredDT_s[0].doseQtyUnitCd",
             taDT.getDoseQtyUnitCd());
         request.setAttribute(
             "treatmentVO.treatmentAdministeredDT_s[0].routeCd",
             taDT.getRouteCd());
         //Fix for civ8102
         /*request.setAttribute(
                   "treatmentVO.treatmentAdministeredDT_s[0].rateQtyUnitCd",
                   taDT.getRateQtyUnitCd()); */request.setAttribute(
             "treatmentVO.treatmentAdministeredDT_s[0].intervalCd",
             taDT.getIntervalCd());
         request.setAttribute(
             "treatmentVO.treatmentAdministeredDT_s[0].effectiveDurationAmt",
             taDT.getEffectiveDurationAmt());
         request.setAttribute(
             "treatmentVO.treatmentAdministeredDT_s[0].effectiveDurationUnitCd",
             taDT.getEffectiveDurationUnitCd());

         logger.debug("versioncontrolNumber" + tDT.getVersionCtrlNbr());
      }

      logger.debug("\n\nAbout to look for PerfOfTrt\n\n");
      PersonVO givenByPersonVO = this.getPersonVO(NEDSSConstants.
                                                  PROVIDER_OF_TREATMENT, proxy); // "PerfOfTrt"
      if (givenByPersonVO != null) {
         logger.debug("PerfOfTrt Name: " +
                      givenByPersonVO.getThePersonDT().getLastNm());
      }
      else {
         logger.debug("givenByPersonVO is null");
      }
      logger.debug("\n\nAbout to look for SubjOfTrt\n\n");
      PersonVO personVO = this.getPersonVO(NEDSSConstants.SUBJECT_OF_TREATMENT,
                                           proxy);
      form.setOldPatient(personVO);
      //retrieve the patient name collection to populate the lst and first name
      String firstName = "";
      String lastName = "";
      Collection<Object>  petientNameCol = personVO.getThePersonNameDTCollection();
      if (petientNameCol != null) {
        Iterator<Object>  nameIterator = petientNameCol.iterator();
         while (nameIterator.hasNext()) {
            PersonNameDT pdt = (PersonNameDT) nameIterator.next();
            logger.debug("PersonSummaryVO.getNmUseCd:" + pdt.getNmUseCd());
            if (pdt.getRecordStatusCd().equalsIgnoreCase(NEDSSConstants.ACTIVE) &&
                pdt.getNmUseCd() != null &&
                pdt.getNmUseCd().equalsIgnoreCase(NEDSSConstants.LEGAL_NAME)) {
               logger.debug(
                   "PersonSummaryVO.getPersonSummary and nameDoesnotExist is " +
                   pdt.getNmUseCd());
               firstName = pdt.getFirstNm();
               lastName = pdt.getLastNm();
            } //end if for checking nameused cd of legal type
         } // end while
      } // end if

      String strPatientFirstName = (firstName == null ? "" : firstName);
      String strPatientLastName = (lastName == null ? "" : lastName);
      request.setAttribute("legalFirstName", strPatientFirstName);
      request.setAttribute("legalLastName", strPatientLastName);
      // end of setting Names
      request.setAttribute("birthTime",
                           StringUtils.formatDate(personVO.getThePersonDT().
                                                  getBirthTime()));
      request.setAttribute("currSexCd", personVO.getThePersonDT().getCurrSexCd());

      logger.debug("givenByPersonVO:" + givenByPersonVO);

      if (givenByPersonVO != null) {
		String displayString = helper.makePRVDisplayString(givenByPersonVO);
		request.setAttribute("providerSourceDetails", displayString);

      } else {
      	request.setAttribute("providerSourceDetails", "There is no Provider Selected");
      }

      OrganizationVO orgGiverVO = this.getOrganizationVO(NEDSSConstants.
          RPT_FACILITY_OF_TREATMENT, proxy);

      if (orgGiverVO != null) {
		String displayString = helper.makeORGDisplayString(orgGiverVO);
		request.setAttribute("reportingSourceDetails", displayString);

      } else {
      	request.setAttribute("reportingSourceDetails", "There is no Reporting Facility Selected");
      }
   }

   /**
    * This private method is used to get PersonVO object from a  TreatmentProxyVO for a given type_cd
    * @param type_cd String object
    * @param vVO TreatmentProxyVO object
    * @return PersonVO object
    */
   private PersonVO getPersonVO(String type_cd, TreatmentProxyVO tpVO) {

      Collection<Object>  participationDTCollection  = null;
      Collection<Object>  personVOCollection  = null;
      ParticipationDT participationDT = null;
      PersonVO personVO = null;
      TreatmentVO tVO = tpVO.getTheTreatmentVO();
      participationDTCollection  = tVO.getTheParticipationDTCollection();
      personVOCollection  = tpVO.getThePersonVOCollection();

      if (participationDTCollection  != null) {
        Iterator<Object>  anIterator1 = null;
        Iterator<Object>  anIterator2 = null;
         for (anIterator1 = participationDTCollection.iterator();
                            anIterator1.hasNext(); ) {
            participationDT = (ParticipationDT) anIterator1.next();
            if (participationDT.getTypeCd() != null &&
                (participationDT.getTypeCd()).compareTo(type_cd) == 0) {

               for (anIterator2 = personVOCollection.iterator();
                                  anIterator2.hasNext(); ) {
                  personVO = (PersonVO) anIterator2.next();

                  if (personVO.getThePersonDT().getPersonUid().longValue() ==
                      participationDT.getSubjectEntityUid().longValue()) {

                     return personVO;
                  }
                  else {
                     continue;
                  }
               }
            }
            else {
               continue;
            }
         }
      }
      return null;
   }

   /**
    * This private method is used to get PersonNameDT object from a  personVO for a given nameUserCd
    * @param nameUserCd String object
    * @param personVO PersonVO object
    * @return PersonNameDT object
    */
   private PersonNameDT getPersonNameDT(String nameUserCd, PersonVO personVO) {

      PersonNameDT personNameDT = null;
      Collection<Object>  personNameDTCollection  = personVO.getThePersonNameDTCollection();
      if (personNameDTCollection  != null) {
        Iterator<Object>  anIterator = null;
         for (anIterator = personNameDTCollection.iterator();
                           anIterator.hasNext(); ) {
            personNameDT = (PersonNameDT) anIterator.next();

            if (personNameDT.getNmUseCd() != null &&
                personNameDT.getNmUseCd().equalsIgnoreCase(nameUserCd)) {

               return personNameDT;
            }
            else {
               continue;
            }
         }
      }
      return null;
   }

   /**
    * This private method is used to get OrganizationNameDT object from a  OrganizationVO for a given nameUserCd
    * @param nameUserCd String object
    * @param organizationVO OrganizationVO object
    * @return OrganizationNameDT  object
    */
   private OrganizationNameDT getOrganizationNameDT(String nameUserCd,
       OrganizationVO organizationVO) {
      logger.debug("Looking for org nameDT: " + nameUserCd);
      OrganizationNameDT organizationNameDT = null;
      Collection<Object>  organizationNameDTCollection  = organizationVO.
          getTheOrganizationNameDTCollection();
      if (organizationNameDTCollection  != null) {
        Iterator<Object>  anIterator = null;
         for (anIterator = organizationNameDTCollection.iterator();
                           anIterator.hasNext(); ) {
            organizationNameDT = (OrganizationNameDT) anIterator.next();
            if (organizationNameDT.getNmUseCd() != null &&
                organizationNameDT.getNmUseCd().equalsIgnoreCase(nameUserCd)) {
               return organizationNameDT;
            }
            else {
               continue;
            }
         }
      }
      return null;
   }

   /**
    * This private method is used to get OrganizationVO object from a  TreatmentProxyVO for a given type_cd
    * @param type_cd String object
    * @param vVO TreatmentProxyVO object
    * @return OrganizationVO object
    */
   private OrganizationVO getOrganizationVO(String type_cd,
                                            TreatmentProxyVO tpVO) {
      logger.debug("Got into the orgVO finder");
      logger.debug("Looking for type_cd: " + type_cd);
      Collection<Object>  participationDTCollection  = null;
      Collection<Object>  organizationVOCollection  = null;
      ParticipationDT participationDT = null;
      OrganizationVO organizationVO = null;
      TreatmentVO tVO = tpVO.getTheTreatmentVO();
      participationDTCollection  = tVO.getTheParticipationDTCollection();
      organizationVOCollection  = tpVO.getTheOrganizationVOCollection();
      if (participationDTCollection  != null && organizationVOCollection  != null) {
        Iterator<Object>  anIterator1 = null;
        Iterator<Object>  anIterator2 = null;
         for (anIterator1 = participationDTCollection.iterator();
                            anIterator1.hasNext(); ) {
            participationDT = (ParticipationDT) anIterator1.next();
            if (participationDT.getTypeCd() != null &&
                (participationDT.getTypeCd().trim()).equals(type_cd)) {
               for (anIterator2 = organizationVOCollection.iterator();
                                  anIterator2.hasNext(); ) {
                  organizationVO = (OrganizationVO) anIterator2.next();
                  if (organizationVO.getTheOrganizationDT().getOrganizationUid().
                      longValue() ==
                      participationDT.getSubjectEntityUid().longValue()) {
                     return organizationVO;
                  }
                  else {
                     continue;
                  }
               }
            }
            else {
               continue;
            }
         }
      }
      return null;
   }

   private EntityLocatorParticipationDT getAddressDT(Collection<Object>
       entityLocatorParticipationDTCollection) {

      if (entityLocatorParticipationDTCollection  != null) {

        Iterator<?>  anIterator = null;

         for (anIterator = entityLocatorParticipationDTCollection.iterator();
                           anIterator.hasNext(); ) {

            EntityLocatorParticipationDT entityLocatorParticipationDT = (
                EntityLocatorParticipationDT) anIterator.next();

            if (entityLocatorParticipationDT.getClassCd() != null &&
                entityLocatorParticipationDT.getClassCd().equals(NEDSSConstants.
                POSTAL) && entityLocatorParticipationDT.getUseCd() != null &&
                entityLocatorParticipationDT.getUseCd().equals("WP") &&
                entityLocatorParticipationDT.getCd() != null &&
                entityLocatorParticipationDT.getCd().equals("O")) {

               return entityLocatorParticipationDT;
            }
            else {

               continue;
            }
         }
      }

      return null;

   }

   private EntityLocatorParticipationDT getTeleLocatorDT(Collection<Object>
       entityLocatorParticipationDTCollection) {

      if (entityLocatorParticipationDTCollection  != null) {

        Iterator<?>  anIterator = null;

         for (anIterator = entityLocatorParticipationDTCollection.iterator();
                           anIterator.hasNext(); ) {

            EntityLocatorParticipationDT entityLocatorParticipationDT = (
                EntityLocatorParticipationDT) anIterator.next();

            if (entityLocatorParticipationDT.getClassCd() != null &&
                entityLocatorParticipationDT.getClassCd().equals(NEDSSConstants.
                TELE) && entityLocatorParticipationDT.getUseCd() != null &&
                entityLocatorParticipationDT.getUseCd().equals(NEDSSConstants.
                WORK_PHONE) && entityLocatorParticipationDT.getCd() != null &&
                entityLocatorParticipationDT.getCd().equals(NEDSSConstants.
                PHONE) &&
                entityLocatorParticipationDT.getTheTeleLocatorDT().getPhoneNbrTxt() != null) {

               return entityLocatorParticipationDT;
            }
            else {

               continue;
            }
         }
      }

      return null;

   }

   public boolean isAssociatedWithMorb(Long sourceActUid,  HttpSession session)
   {
     boolean isAssociatedWithMorb = false;
     MainSessionCommand msCommand = null;
     ArrayList<Object> list = new ArrayList<Object> ();

     try {
       logger.debug("sourceActUid inside getActRelationShipDTColl method is: " +
               sourceActUid);
       String sBeanJndiName = JNDINames.OBSERVATION_PROXY_EJB;
       String sMethod = "isAssociatedWithMorb";
       Object[] oParams = new Object[] {sourceActUid};
       MainSessionHolder holder = new MainSessionHolder();
       msCommand = holder.getMainSessionCommand(session);

       ArrayList<?> arr = msCommand.processRequest(sBeanJndiName,
                                           sMethod, oParams);
       isAssociatedWithMorb = ((Boolean) arr.get(0)).booleanValue();
     }
     catch (Exception ex) {

       if (session == null) {
         logger.debug("Error: no session, please login");

       }
         logger.fatal("getCodeSystemDescription: ", ex);
     }


     return isAssociatedWithMorb;
   }

}
