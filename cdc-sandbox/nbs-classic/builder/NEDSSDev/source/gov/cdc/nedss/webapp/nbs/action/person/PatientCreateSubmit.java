package gov.cdc.nedss.webapp.nbs.action.person;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import gov.cdc.nedss.association.dt.RoleDT;
import gov.cdc.nedss.entity.entityid.dt.EntityIdDT;
import gov.cdc.nedss.entity.person.dt.PersonDT;
import gov.cdc.nedss.entity.person.dt.PersonNameDT;
import gov.cdc.nedss.entity.person.dt.PersonRaceDT;
import gov.cdc.nedss.entity.person.vo.PersonSummaryVO;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.exception.NEDSSAppConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.locator.dt.EntityLocatorParticipationDT;
import gov.cdc.nedss.locator.dt.PostalLocatorDT;
import gov.cdc.nedss.locator.dt.TeleLocatorDT;
import gov.cdc.nedss.pagemanagement.wa.dt.BatchEntry;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.webapp.nbs.action.pam.PamClientVO.PamClientVO;
import gov.cdc.nedss.webapp.nbs.action.person.util.PersonUtil;
import gov.cdc.nedss.webapp.nbs.action.util.CommonAction;
import gov.cdc.nedss.webapp.nbs.action.util.ErrorMessageHelper;
import gov.cdc.nedss.webapp.nbs.form.person.CompleteDemographicForm;

/** The PersonSubmit class is class that is accessed from the front-end so that person data can be submitted to the back end.  It is used in creating new persons into the NEDSS system.  It is also used for edits to existing persons in the system.
 */
public class PatientCreateSubmit
    extends CommonAction {

	static final LogUtils logger = new LogUtils(PatientCreateSubmit.class.getName());

   /**
    * Instance of a String to hold value for
    * userID.
    */
   private String userID = "";
   


   /** A constructor for the PersonSubmit class.
    */
   public PatientCreateSubmit() {
   }

   /** This is the method that is automatically called first
    * when the PersonSubmit class is called.
    *
    * @exception IOException
    * @exception ServletException
    * @param mapping ActionMapping
    * @param form ActionForm
    * @param request HttpServletRequest
    * @param response HttpServletResponse
    * @return an ActionForward
    */
   public ActionForward execute(ActionMapping mapping, ActionForm form,
                                HttpServletRequest request,
                                HttpServletResponse response) throws
       IOException, ServletException {
	   
	   Long UID = null;

      NBSSecurityObj securityObj = null;
      CompleteDemographicForm personForm = (CompleteDemographicForm) form;
      HttpSession session = request.getSession();
      String sCurrentTask = NBSContext.getCurrentTask(session);
      NBSContext.
              retrieve(session, "DSSearchCriteria");
      if (session == null) {
         logger.debug("error no session");
         return mapping.findForward("login");
      }

      Object obj = session.getAttribute("NBSSecurityObject");

      if (obj != null) {
         securityObj = (NBSSecurityObj) obj;

      }
      
      securityObj.getTheUserProfile().getTheUser().getEntryID();
      // are we edit or create?
      String contextAction = request.getParameter("ContextAction");
      logger.info("contextAction is: " + contextAction);

      if (contextAction == null) {
         contextAction = (String) request.getAttribute("ContextAction");

         //Long UID = null;
      }
      try {
         UID = (request.getParameter("personUID") == null ? null :
                new Long(request.getParameter("personUID").trim()));

         if (UID == null) {
            UID = (request.getAttribute("personUID") == null ? null :
                   new Long( ( (String) request.getAttribute("personUID")).trim()));
            //if(UID == null)
            //UID = new Long(((String)request.getAttribute("entity.personUID")).trim());

         }
         logger.info("UID is: " + UID);
      }
      catch (java.lang.NumberFormatException e) {

      }

      /******************************************
           * CREATE A NEW ONE , ADD ACTION or EDIT ACTION current task distinguishes them
       */
      if (contextAction.equalsIgnoreCase("Submit")) {
         logger.info("contextAction was SUBMIT");
         // we need to determine what kind of submit this is: add or edit
         // determine this from the current task
         NBSContext.store(session, "DSFileTab", "2");

         logger.info("Value of the sCurrentTask: " + sCurrentTask);

         PersonVO personVO = null;

         if (sCurrentTask == null) {
            session.setAttribute("error",
                "current task is null, required for person submit");

            throw new ServletException("current task is null, required for person submit");
         }
         else if (sCurrentTask.startsWith("AddPatientBasic")) {
            NBSContext.store(session, "DSFileTab", "1");
            personVO = createBasicHandler(personForm, securityObj, session, request);
         }
         else if (sCurrentTask.startsWith("AddPatientExtended")) {
            NBSContext.store(session, "DSFileTab", "2");
            personVO = createHandler(personForm, securityObj, session, request,
                                     response);

         }

         else {
            session.setAttribute("error",
                "didn't find a match for current task for the submit action");

            throw new ServletException("Didn't find a match for current task for the submit action");
         }

         try {
            logger.info("Going to send personVO to EJB");
            UID = sendProxyToEJB(personVO, "setMPR", session);
            PersonSummaryVO DSPersonSummary = new PersonSummaryVO();
            logger.info(
                "LINE 150: About to set the PERSONUID on the DSPersonSummary to: " +
                UID);
            DSPersonSummary.setPersonUid(UID);
            NBSContext.store(session, "DSPatientPersonUID", UID);

         }
         catch (NEDSSAppConcurrentDataException e) {
            logger.fatal("ERROR - NEDSSAppConcurrentDataException, The data has been modified by another user, please recheck! ",
                         e);

            return mapping.findForward("dataerror");
         }
         catch (Exception e) {
             logger.fatal("ERROR - General error while updating person! ", e);
             throw new ServletException("ERROR - General error while updating person! "+e.getMessage(),e);
          }
         if (UID == null) {
            throw new ServletException();
         }

         //reset the form
         //personForm.reset();

      }

      /*******************************************
       * CANCEL ACTION
       */
      else if (contextAction.equalsIgnoreCase("Cancel")) {
         logger.info("You are attempting to cancel. CurrentTask is: " +
                     sCurrentTask);
         NBSContext.store(session, "DSFileTab", "2");
         ErrorMessageHelper.setErrMsgToRequest(request, "ps166");
        
         personForm.setAddressCollection(null);
         personForm.setTelephoneCollection(null);
         personForm.setPamClientVO(new PamClientVO());
         //personForm.reset();
         personForm.resetBatch();
      }

      /*******************************************
       * ADD PERSON EXTENDED
       */
      else if (contextAction.equalsIgnoreCase("AddExtended")) {

         logger.info("Value of the sCurrentTask: " + sCurrentTask);
         logger.info("Value of the contextAction: " + contextAction);

         PersonVO personVO = createBasicHandler(personForm, securityObj, session, request);
         
         NBSContext.store(session, "DSPatientPersonVO", personVO);
         NBSContext.store(session, "DSFileTab", "2");
         //personForm.reset();
      }

      request.setAttribute("ContextAction", contextAction);

      logger.info("VALUE OF PERSONUID IN PERSON SUBMIT VIEW BLOCK: " + UID);
      request.setAttribute("personUID", UID);

       return mapping.findForward(contextAction);
   }

   /** This method is called to prepare the PersonVO object
    * for creating a new person in the system.
    *
    * @param personForm CompleteDemographicForm
    * @param securityObj NBSSecurityObj
    * @param session HttpSession
    * @param request HttpServletRequest
    * @param response HttpServletResponse
    * @return a PersonVO
    */
   private PersonVO createHandler(CompleteDemographicForm personForm,
                                  NBSSecurityObj securityObj,
                                  HttpSession session,
                                  HttpServletRequest request,
                                  HttpServletResponse response) {

      PersonVO personVO = null;

      try {
         personVO = personForm.getPerson();
         personVO.setItNew(true);
         personVO.setItDirty(false);

         // set up the DT for the EJB
         PersonDT personDT = personVO.getThePersonDT();
         personDT.setItNew(true);
         personDT.setItDirty(false);
         personDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
         personDT.setPersonUid(new Long( -1));
         personDT.setCd("PAT");
         personDT.setAddTime(new Timestamp(new Date().getTime()));
         personDT.setLastChgTime(new Timestamp(new Date().getTime()));
         personDT.setRecordStatusTime(new Timestamp(new Date().getTime()));
         personDT.setStatusTime(new Timestamp(new Date().getTime()));
         personDT.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
         personDT.setElectronicInd(NEDSSConstants.NO);

         String sCurrentTask = NBSContext.getCurrentTask(session);
         logger.info("sCurrentTask is: " + sCurrentTask);

         // set up the person names dt
         logger.info("Calling setNames");
         PersonUtil pUtil = new PersonUtil();
         pUtil.setToNamesCollection(personForm);
         setNames(personVO, request);

         request.getParameter("ContextAction");

       /*  if (!sCurrentTask.startsWith("AddPatientBasic")) {
            logger.info(
                "Inside block to setBirthAddresses and setDeceasedAddress");
            setBirthAddress(personVO, personForm.getBirthAddress());
            setDeceasedAddress(personVO, personForm.getDeceasedAddress());

         }*/
         pUtil.setToIdsCollection(personForm);
         setIds(personVO, request);

         logger.info("CreateHandler sCurrentTask: " + sCurrentTask);
         String userId = securityObj.getTheUserProfile().getTheUser().getEntryID();
         pUtil.setEthnicity(personVO,personForm, request,userId);
         //String userId = securityObj.getTheUserProfile().getTheUser().getEntryID();
         pUtil.setToAddressCollection(personVO, personForm,userId);
         setAddresses(personVO, personForm.getAddressCollection());
         pUtil.setToPhoneCollection(personVO, personForm,userId);
         setTelephones(personVO, personForm.getTelephoneCollection());
        
         if (!sCurrentTask.startsWith("AddPatientBasic")) {
             logger.info(
                 "Inside block to setBirthAddresses and setDeceasedAddress");
             setBirthAddress(personVO, personForm.getBirthAddress());
             setDeceasedAddress(personVO, personForm.getDeceasedAddress());

          }
         
         setPhysicalLocations(personVO, personForm.getPhysicalCollection());
         setRoles(personVO, request);
       
         pUtil.setRace(personVO, personForm,userId);
         //if there is any LDF - process it
         if (!personForm.getPamClientVO().getAnswerMap().isEmpty() ||
        		 !personForm.getPamClientVO().getArrayAnswerMap().isEmpty()) {
        	 personVO.setTheStateDefinedFieldDataDTCollection(PersonUtil.extractPatientLDFs(personForm));
         }
      }
      catch (Exception e) {
  		logger.error("Exception in Patient Create Submit: " + e.getMessage());
  		e.printStackTrace();
      }

      return personVO;
   }


   //
   /**
    * This method will set the nameDT(s) to the PersonVO.
    *
    * @param personVO PersonVO
    * @param request HttpServletRequest
    */
   public void setNames(PersonVO personVO, HttpServletRequest request) {

      Collection<Object>  names = personVO.getThePersonNameDTCollection();
      Long personUID = personVO.getThePersonDT().getPersonUid();
      HttpSession session = request.getSession();
      String currentTask = NBSContext.getCurrentTask(session);
      logger.info("ContextAction in setNames method: " + currentTask);

      if (currentTask.startsWith("AddPatientBasic")) {
         logger.info(
             "Setting Names because currentTask starts with AddPatientBasic");
         String lastNm = request.getParameter("person.lastNm");
         String firstNm = request.getParameter(
             "person.firstNm");
         String middleNm = request.getParameter(
             "person.middleNm");

         String suffix = request.getParameter(
             "person.nmSuffix");

         if ( (lastNm != null && !lastNm.trim().equals("")) ||
             (firstNm != null && !firstNm.trim().equals("")) ||
             (middleNm != null && !middleNm.trim().equals("")) ||
             (suffix != null && !suffix.trim().equals(""))) {
            PersonNameDT pdt = new PersonNameDT();
            pdt.setItNew(true);
            pdt.setItDirty(false);
            pdt.setNmUseCd(NEDSSConstants.LEGAL_NAME);
            pdt.setPersonNameSeq(new Integer(1));
            pdt.setStatusTime(new Timestamp(new Date().getTime()));
            pdt.setAddTime(new Timestamp(new Date().getTime()));
            pdt.setRecordStatusTime(new Timestamp(new Date().getTime()));
            pdt.setPersonUid(personUID);
            pdt.setRecordStatusCd(NEDSSConstants.ACTIVE);
            pdt.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
            pdt.setLastNm(lastNm);
            pdt.setFirstNm(firstNm);
            pdt.setMiddleNm(middleNm);
            pdt.setAsOfDate_s(request.getParameter("generalAsOfDate") == null ?
                               "" :
                               (String) request.getParameter("generalAsOfDate"));
            pdt.setNmSuffix(suffix);
            Collection<Object>  pdts = new ArrayList<Object> ();
            pdts.add(pdt);
            personVO.setThePersonNameDTCollection(pdts);
         }
      }

      if (names != null) {

         logger.info(
             "NAMES COLLECTION wan't null in personSubmit createHandler");
        Iterator<Object>  itrCount = names.iterator();

         //need to find the max seq nbr for existing names
         Integer maxSeqNbr = new Integer(0);

         while (itrCount.hasNext()) {

            PersonNameDT nameDT = (PersonNameDT) itrCount.next();
            if (nameDT.getStatusCd().equals(NEDSSConstants.I)) {
               logger.info("REMOVING: " + nameDT.getFirstNm() + " " +
                           nameDT.getLastNm());
               if (NBSContext.getPrevPageName(session).startsWith("Add")) {
                  names.remove(nameDT);
                  continue;
               }
            }

            if (nameDT.getPersonNameSeq() != null) {

               if (nameDT.getPersonNameSeq().compareTo(maxSeqNbr) > 0) { // update the maxSeqNbr when you find a bigger one
                  maxSeqNbr = nameDT.getPersonNameSeq();
               }
            }
         }

        Iterator<Object>  itrNames = names.iterator();

         while (itrNames.hasNext()) {

            PersonNameDT nameDT = (PersonNameDT) itrNames.next();

            if (nameDT.getPersonNameSeq() == null) { //  this is a new one
               nameDT.setItNew(true);
               nameDT.setItDirty(false);
               maxSeqNbr = new Integer(maxSeqNbr.intValue() + 1);
               nameDT.setPersonNameSeq(maxSeqNbr);

               //nameDT.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
               if (nameDT.getStatusCd() != null &&
                   nameDT.getStatusCd().equals(NEDSSConstants.STATUS_ACTIVE)) {
                  nameDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
               }
               else {
                  nameDT.setRecordStatusCd(NEDSSConstants.
                                           RECORD_STATUS_INACTIVE);

               }
               nameDT.setStatusTime(new Timestamp(new Date().getTime()));
               nameDT.setAddTime(new Timestamp(new Date().getTime()));
               nameDT.setRecordStatusTime(new Timestamp(new Date().getTime()));
               nameDT.setPersonUid(personUID);
            } //this is an old one
            else {

               //nameDT.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
               nameDT.setItNew(false);
               nameDT.setItDirty(true);

               if (nameDT.getStatusCd() != null &&
                   nameDT.getStatusCd().equals(NEDSSConstants.STATUS_ACTIVE)) {
                  nameDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
               }
               else {
                  nameDT.setRecordStatusCd(NEDSSConstants.
                                           RECORD_STATUS_INACTIVE);

               }
               nameDT.setPersonUid(personUID);
            }

            nameDT.setLastChgTime(new Timestamp(new Date().getTime()));
         }
      }
   }

   public static void setIds(PersonVO personVO, HttpServletRequest request) {
      Collection<Object>  ids = personVO.getTheEntityIdDTCollection();

      Long personUID = personVO.getThePersonDT().getPersonUid();
      String patientSSNAsOfDate = request.getParameter("patientSSNAsOfDate");
      String patientSSN = request.getParameter("patientSSN");

      boolean isThereSSN = false;
      if (ids != null) {
        Iterator<Object>  itrCount = ids.iterator();
         //need to find the max seq nbr for existing names
         Integer maxSeqNbr = new Integer(0);
         while (itrCount.hasNext()) {
        	 maxSeqNbr = maxSeqNbr+1;
            EntityIdDT idDT = (EntityIdDT) itrCount.next();
            idDT.setEntityIdSeq(maxSeqNbr);
         }
        Iterator<Object>  itrIds = ids.iterator();

         while (itrIds.hasNext()) {
            EntityIdDT id = (EntityIdDT) itrIds.next();

            if (id.getEntityUid() == null  || ( id.getEntityUid()==0)) { // this is a new one
               
               maxSeqNbr = new Integer(maxSeqNbr.intValue());
               id.setEntityIdSeq(maxSeqNbr);
               maxSeqNbr= maxSeqNbr+1;

               
               if (id.getStatusCd() != null &&
                   id.getStatusCd().equals(NEDSSConstants.STATUS_ACTIVE)) {
                  id.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
                  id.setItNew(true);
                  id.setItDirty(false);
                  id.setAddTime(new Timestamp(new Date().getTime()));
                  id.setLastChgTime(new Timestamp(new Date().getTime()));
                  id.setRecordStatusTime(new Timestamp(new Date().getTime()));
                  id.setStatusTime(new Timestamp(new Date().getTime()));

                  if (id.getAsOfDate() == null) {
                     id.setAsOfDate_s(request.getParameter("generalAsOfDate") == null ?
                                      "" :
                                      (String) request.getParameter("generalAsOfDate"));

                  }
                  id.setEntityUid(personUID);

               }
               else {
                  //if inactive from batch , just remove from collection
                  itrIds.remove();
               }

            }
            else { //this is old one
               //check if ssn exists in the collection already
               if (id.getTypeCd() != null && id.getAssigningAuthorityCd() != null &&
                   id.getTypeCd().equals("SS") &&
                   id.getAssigningAuthorityCd().equals("SSA")) {
                  isThereSSN = true;
                  id.setRootExtensionTxt(patientSSN);
                  id.setAsOfDate_s(patientSSNAsOfDate);
                  id.setItNew(false);
                  id.setItDirty(true);
                  id.setEntityIdSeq(new Integer(0));
                  id.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
                  id.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
               }
               else if (id.getStatusCd() != null &&
                        id.getStatusCd().equals(NEDSSConstants.STATUS_ACTIVE)) {
                  id.setItNew(false);
                  id.setItDirty(true);
                  id.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);

               }
               else {
                  id.setItNew(false);
                  id.setItDelete(true);
                  id.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_INACTIVE);
               }

            }
            id.setLastChgTime(new Timestamp(new Date().getTime()));
         }
         //don't have ssn in batch but do have one outside batch
         if (isThereSSN == false && patientSSN != null && !patientSSN.equals("")) {
            EntityIdDT iddt = null;
            iddt = new EntityIdDT();
            maxSeqNbr = new Integer(maxSeqNbr.intValue() + 1);
            iddt.setEntityIdSeq(maxSeqNbr);
            iddt.setAddTime(new Timestamp(new Date().getTime()));
            iddt.setLastChgTime(new Timestamp(new Date().getTime()));
            iddt.setRecordStatusTime(new Timestamp(new Date().getTime()));
            iddt.setStatusTime(new Timestamp(new Date().getTime()));
            iddt.setEntityUid(personUID);
            iddt.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
            iddt.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
            iddt.setTypeCd("SS");
            iddt.setTypeDescTxt("Social Security Number");
            iddt.setAssigningAuthorityCd("SSA");
            iddt.setAssigningAuthorityDescTxt("Social Security Administration");
            iddt.setRootExtensionTxt(patientSSN);
            iddt.setAsOfDate_s(request.getParameter("generalAsOfDate") == null ?
                               patientSSNAsOfDate :
                               (String) request.getParameter("generalAsOfDate"));

            ids.add(iddt);
         }
      } //don't have any batch id's but do have a social security number
      else if (patientSSN != null && !patientSSN.equals("")) {
         EntityIdDT iddt = null;
         iddt = new EntityIdDT();
         iddt.setEntityIdSeq(new Integer(0));
         iddt.setAddTime(new Timestamp(new Date().getTime()));
         iddt.setLastChgTime(new Timestamp(new Date().getTime()));
         iddt.setRecordStatusTime(new Timestamp(new Date().getTime()));
         iddt.setStatusTime(new Timestamp(new Date().getTime()));
         iddt.setEntityUid(personUID);
         iddt.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
         iddt.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
         iddt.setTypeCd("SS");
         iddt.setTypeDescTxt("Social Security Number");
         iddt.setAssigningAuthorityCd("SSA");
         iddt.setAssigningAuthorityDescTxt("Social Security Administration");
         iddt.setRootExtensionTxt(patientSSN);
         iddt.setAsOfDate_s(request.getParameter("generalAsOfDate") == null ?
                            patientSSNAsOfDate :
                            (String) request.getParameter("generalAsOfDate"));

         ArrayList<Object> idList = new ArrayList<Object> ();
         idList.add(iddt);
         personVO.setTheEntityIdDTCollection(idList);
      }
      PersonUtil.setAssigningAuthorityforIds(personVO.getTheEntityIdDTCollection(), NEDSSConstants.EI_AUTH);

   }


   /**
    * This method will set the birthAddress onto the
    * PersonVO.
    *
    * @param personVO PersonVO
    * @param birthELP EntityLocatorParticipationDT
    */
   private void setBirthAddress(PersonVO personVO,
                                EntityLocatorParticipationDT birthELP) {

      //when adding new birth check if the user inputted anything
      PostalLocatorDT postal = birthELP.getThePostalLocatorDT();

      ArrayList<Object> arrELP = (ArrayList<Object> ) personVO.
                         getTheEntityLocatorParticipationDTCollection();

      if (arrELP == null) {
         arrELP = new ArrayList<Object> ();
      }

      if (birthELP != null) {
         if ( (birthELP.getLocatorUid() == null  || birthELP.getLocatorUid().longValue()==0) &&
             ( ( (postal.getCityDescTxt() == null) ||
                (postal.getCityDescTxt().trim().equals("")) &&
                ( (postal.getCntryDescTxt() == null) ||
                 (postal.getCntryDescTxt().trim().equals(""))) &&
                 ( (postal.getCntyDescTxt() == null) ||
                 (postal.getCntyDescTxt().trim().equals(""))) &&
                 ( (postal.getStateCd() == null) ||
                  (postal.getStateCd().trim().equals(""))) &&
                 ( (postal.getCntryCd() == null) ||
                 (postal.getCntryCd().trim().equals("")))

             ))) {
            //A birth address has not been entered.....EVER.
            //So do nothing
            return;
         }

         if ( (birthELP.getLocatorUid() == null || birthELP.getLocatorUid().longValue()==0) &&
             ( ( (postal.getCityDescTxt() != null) &&
                (!postal.getCityDescTxt().trim().equals("")) ||
                ( (postal.getCntryDescTxt() != null) &&
                 (!postal.getCntryDescTxt().trim().equals(""))) ||
                ( (postal.getCntyDescTxt() != null) &&
                 (!postal.getCntyDescTxt().trim().equals(""))) ||
                ( (postal.getStateCd() != null) &&
                 (!postal.getStateCd().trim().equals(""))) ||
                 ((postal.getCntryCd() != null) &&
                 (!postal.getCntryCd().trim().equals("")))
                 ))) { //a new one
            birthELP.setItNew(true);
            birthELP.setItDirty(false);
            birthELP.getThePostalLocatorDT().setItNew(true);
            birthELP.getThePostalLocatorDT().setItDirty(false);
            birthELP.setAsOfDate(personVO.getThePersonDT().getAsOfDateSex());
            birthELP.setClassCd("PST");
            birthELP.setCd("F");
            birthELP.setUseCd("BIR");
            birthELP.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
            birthELP.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
            birthELP.setEntityUid(personVO.getThePersonDT().getPersonUid());

            /*
                   // If birth city/place are reset back to "blank" or empty value,
                                 // set the ELP status_cd and record_status_cd to be "Inactive"
                 if (postal != null && postal.getCityDescTxt() != null &&
                postal.getStateCd() != null && postal.getCntyCd() != null &&
                postal.getCntryCd() != null &&
                postal.getCityDescTxt().trim().equals("") &&
                postal.getStateCd().trim().equals("") &&
                postal.getCntyCd().equals("") &&
                postal.getCntryCd().trim().equals(""))
                                 {
                birthELP.setStatusCd(NEDSSConstants.STATUS_INACTIVE);
                birthELP.setRecordStatusCd(
                        NEDSSConstants.RECORD_STATUS_INACTIVE);
                                 }
             */arrELP.add(birthELP);

         } // an old one
         else if ( (birthELP.getLocatorUid() != null) &&
                  ( ( (postal.getCityDescTxt() != null) &&
                     (!postal.getCityDescTxt().trim().equals("")) ||
                     ( (postal.getCntryDescTxt() != null) &&
                      (!postal.getCntryDescTxt().trim().equals(""))) ||
                     ( (postal.getCntyDescTxt() != null) &&
                      (!postal.getCntyDescTxt().trim().equals(""))) ||
                     ( (postal.getStateCd() != null) &&
                      (!postal.getStateCd().trim().equals(""))) ||
                      ((postal.getCntryCd() != null) &&
                       (!postal.getCntryCd().trim().equals("")))))) {
            birthELP.setItNew(false);
            birthELP.setItDirty(true);
            birthELP.getThePostalLocatorDT().setItNew(false);
            birthELP.getThePostalLocatorDT().setItDirty(true);
            birthELP.setClassCd("PST");
            birthELP.setCd("F");
            birthELP.setUseCd("BIR");
            birthELP.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
            birthELP.setStatusCd(NEDSSConstants.STATUS_ACTIVE);

            arrELP.add(birthELP);
         }
         else {
            // If birth city/place are reset back to "blank" or empty value,
            // set the ELP status_cd and record_status_cd to be "Inactive"
            if ( ( (postal.getCityDescTxt() == null) ||
                  (postal.getCityDescTxt().trim().equals("")) &&
                  (postal.getCntryDescTxt() == null) ||
                  (postal.getCntryDescTxt().trim().equals("")) &&
                  (postal.getCntyDescTxt() == null) ||
                  (postal.getCntyDescTxt().trim().equals("")) &&
                  (postal.getStateCd() == null) ||
                  (postal.getStateCd().trim().equals("")) &&
                  (postal.getCntryCd() == null) ||
                  (postal.getCntryCd().trim().equals("")))) {
               birthELP.setItNew(false);
               birthELP.setItDirty(true);
               birthELP.getThePostalLocatorDT().setItNew(false);
               birthELP.getThePostalLocatorDT().setItDirty(true);
               birthELP.setClassCd("PST");
               birthELP.setCd("F");
               birthELP.setUseCd("BIR");
               birthELP.setStatusCd(NEDSSConstants.STATUS_INACTIVE);
               birthELP.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_INACTIVE);
            }
            arrELP.add(birthELP);
         }

         personVO.setTheEntityLocatorParticipationDTCollection(arrELP);
      }
   }

   /**
    * This method will set the Deceased Address of the person
    * to the PersonVO.
    *
    * @param personVO PersonVO
    * @param deceasedELP EntityLocatorParticipationDT
    */
   private void setDeceasedAddress(PersonVO personVO,
                                   EntityLocatorParticipationDT deceasedELP) {
      ArrayList<Object> arrELP = (ArrayList<Object> ) personVO.
                         getTheEntityLocatorParticipationDTCollection();
      PostalLocatorDT postal = deceasedELP.getThePostalLocatorDT();

      if (arrELP == null) {
         arrELP = new ArrayList<Object> ();

      }
      if ( (personVO.getThePersonDT().getDeceasedIndCd() != null &&
           ( deceasedELP.getLocatorUid() == null || deceasedELP.getLocatorUid().longValue()==0) &&
            personVO.getThePersonDT().getDeceasedIndCd().equals("Y")) &&
          ( ( (postal.getCityDescTxt() == null) ||
             (postal.getCityDescTxt().trim().equals("")) &&
             ( (postal.getCntryDescTxt() == null) ||
              (postal.getCntryDescTxt().trim().equals(""))) &&
             ( (postal.getCntyDescTxt() == null) ||
              (postal.getCntyDescTxt().trim().equals(""))) &&
             ( (postal.getStateCd() == null) ||
              (postal.getStateCd().trim().equals("")))))) {
         //There has never been a death address entered
         return;
      }

      if (personVO.getThePersonDT().getDeceasedIndCd() != null &&
          personVO.getThePersonDT().getDeceasedIndCd().equals("Y")) {
         if (deceasedELP != null) {
            if ( (deceasedELP.getLocatorUid() == null || deceasedELP.getLocatorUid().longValue()==0) &&
                ( (postal.getCityDescTxt() != null) &&
                 (!postal.getCityDescTxt().trim().equals("")) ||
                 ( (postal.getCntryDescTxt() != null) &&
                  (!postal.getCntryDescTxt().trim().equals(""))) ||
                 ( (postal.getCntyDescTxt() != null) &&
                  (!postal.getCntyDescTxt().trim().equals(""))) ||
                 ( (postal.getStateCd() != null) &&
                  (!postal.getStateCd().trim().equals("")))))
            //a new one
            {
               deceasedELP.setItNew(true);
               deceasedELP.setItDelete(false);
               deceasedELP.setItDirty(false);
               deceasedELP.getThePostalLocatorDT().setItNew(true);
               deceasedELP.getThePostalLocatorDT().setItDirty(false);
               deceasedELP.getThePostalLocatorDT().setItDelete(false);
               deceasedELP.setAsOfDate(personVO.getThePersonDT().getAsOfDateMorbidity());
               deceasedELP.setClassCd("PST");
               deceasedELP.setCd("U");
               deceasedELP.setUseCd("DTH");
               deceasedELP.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
               deceasedELP.setRecordStatusCd(NEDSSConstants.
                                             RECORD_STATUS_ACTIVE);
               deceasedELP.setEntityUid(personVO.getThePersonDT().getPersonUid());

               arrELP.add(deceasedELP);
            }
            else if ( (deceasedELP.getLocatorUid() != null) &&
                     ( (postal.getCityDescTxt() != null) &&
                      (!postal.getCityDescTxt().trim().equals("")) ||
                      ( (postal.getCntryDescTxt() != null) &&
                       (!postal.getCntryDescTxt().trim().equals(""))) ||
                      ( (postal.getCntyDescTxt() != null) &&
                       (!postal.getCntyDescTxt().trim().equals(""))) ||
                      ( (postal.getStateCd() != null) &&
                       (!postal.getStateCd().trim().equals(""))))) {
               //You are editing an existing death address
               deceasedELP.setItNew(false);
               deceasedELP.setItDirty(true);
               deceasedELP.getThePostalLocatorDT().setItNew(false);
               deceasedELP.getThePostalLocatorDT().setItDirty(true);
               deceasedELP.setAsOfDate(personVO.getThePersonDT().getAsOfDateMorbidity());
               deceasedELP.setClassCd("PST");
               deceasedELP.setCd("U");
               deceasedELP.setUseCd("DTH");
               deceasedELP.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
               deceasedELP.setRecordStatusCd(NEDSSConstants.
                                             RECORD_STATUS_ACTIVE);

               arrELP.add(deceasedELP);
            }
            else {
               // If deceased city/place are reset back to "blank" or empty value,
               // set the ELP status_cd and record_status_cd to be "Inactive"
               if (postal != null && postal.getCityDescTxt() != null &&
                   postal.getStateCd() != null && postal.getCntyCd() != null &&
                   postal.getCntryCd() != null &&
                   postal.getCityDescTxt().trim().equals("") &&
                   postal.getStateCd().trim().equals("") &&
                   postal.getCntyCd().equals("") &&
                   postal.getCntryCd().trim().equals("")) {
                  //Keeping the "YES" deceased indicator but deleting the address fields
                  deceasedELP.setItNew(false);
                  deceasedELP.setItDirty(true);
                  deceasedELP.getThePostalLocatorDT().setItNew(false);
                  deceasedELP.getThePostalLocatorDT().setItDirty(true);
                  deceasedELP.setAsOfDate(personVO.getThePersonDT().getAsOfDateMorbidity());
                  deceasedELP.setClassCd("PST");
                  deceasedELP.setCd("U");
                  deceasedELP.setUseCd("DTH");
                  deceasedELP.setStatusCd(NEDSSConstants.STATUS_INACTIVE);
                  deceasedELP.setRecordStatusCd(NEDSSConstants.
                                                RECORD_STATUS_INACTIVE);
                  arrELP.add(deceasedELP);
               }

            }
         }

         personVO.setTheEntityLocatorParticipationDTCollection(arrELP);
      } //inactivate the deceased address if it exists
      else if (personVO.getThePersonDT().getDeceasedIndCd() != null &&
               !personVO.getThePersonDT().getDeceasedIndCd().equals("Y")) {

         if (deceasedELP.getLocatorUid() != null) { //one that was created before
            deceasedELP.setItNew(false);
            deceasedELP.setItDirty(true);
            deceasedELP.getThePostalLocatorDT().setItNew(false);
            deceasedELP.getThePostalLocatorDT().setItDirty(true);
            deceasedELP.setAsOfDate(personVO.getThePersonDT().getAsOfDateMorbidity());
            deceasedELP.setClassCd("PST");
            deceasedELP.setCd("U");
            deceasedELP.setUseCd("DTH");
            deceasedELP.setStatusCd(NEDSSConstants.STATUS_INACTIVE);
            deceasedELP.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_INACTIVE);
            arrELP.add(deceasedELP);
            personVO.setTheEntityLocatorParticipationDTCollection(arrELP);
         }
      }

   }

   /** This method will set the addresses onto the PersonVO object in preparation for sending to the database.
    *
    * @param personVO PersonVO
    * @param addressList ArrayList
    */
   private void setAddresses(PersonVO personVO, ArrayList<Object> addressList) {

      Long personUID = personVO.getThePersonDT().getPersonUid();

      if (addressList != null) {

        Iterator<Object>  itrAddress = addressList.iterator();
        ArrayList<Object> arrELP =null;

        if (arrELP == null) {
           arrELP = new ArrayList<Object> ();

        }
        //ArrayList<Object> arrELP = new ArrayList<Object> ();
         while (itrAddress.hasNext()) {

            EntityLocatorParticipationDT elp = (EntityLocatorParticipationDT)
                                               itrAddress.next();

            if (elp.getLocatorUid() == null || elp.getLocatorUid().longValue()==0) {
               elp.setItNew(true);
               elp.setItDirty(false);
               elp.getThePostalLocatorDT().setItNew(true);
               elp.getThePostalLocatorDT().setItDirty(false);
               elp.setEntityUid(personUID);

               //elp.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
               if (elp.getStatusCd() != null &&
                   elp.getStatusCd().equals(NEDSSConstants.STATUS_ACTIVE)) {
                  elp.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
               }
               else {
                  elp.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_INACTIVE);

               }
               arrELP.add(elp);
            }
            else {
               elp.setItNew(false);
               elp.setItDirty(true);
               elp.getThePostalLocatorDT().setItNew(false);
               elp.getThePostalLocatorDT().setItDirty(true);

               if (elp.getStatusCd() != null &&
                   elp.getStatusCd().equals(NEDSSConstants.STATUS_ACTIVE)) {
                  elp.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
               }
               else {
                  elp.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_INACTIVE);

               }
               arrELP.add(elp);
            }
         }

         personVO.setTheEntityLocatorParticipationDTCollection(arrELP);
      }
   }


   /**
    * This method will set the person's telephone
    * numbers to the PersonVO.
    *
    * @param personVO PersonVO
    * @param telephoneList ArrayList
    */
   private void setTelephones(PersonVO personVO, ArrayList<Object> telephoneList) {

      if (telephoneList != null) {

         Long personUID = personVO.getThePersonDT().getPersonUid();
        Iterator<Object>  itr = telephoneList.iterator();
         ArrayList<Object> arrELP = (ArrayList<Object> ) personVO.
                            getTheEntityLocatorParticipationDTCollection();

         if (arrELP == null) {
            arrELP = new ArrayList<Object> ();

         }
        //ArrayList<Object> arrELP = new ArrayList<Object> ();
         while (itr.hasNext()) {

            EntityLocatorParticipationDT elp = (EntityLocatorParticipationDT)
                                               itr.next();

            if (elp.getLocatorUid() == null) { // new one
               elp.setItNew(true);
               elp.setItDirty(false);
               elp.getTheTeleLocatorDT().setItNew(true);
               elp.getTheTeleLocatorDT().setItDirty(false);
               elp.setEntityUid(personUID);

               if (elp.getStatusCd() != null &&
                   elp.getStatusCd().equals(NEDSSConstants.STATUS_ACTIVE)) {
                  elp.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
               }
               else {
                  elp.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_INACTIVE);

                  //elp.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
               }
               arrELP.add(elp);
            }
            else {
               elp.setItNew(false);
               elp.setItDirty(true);
               elp.getTheTeleLocatorDT().setItNew(false);
               elp.getTheTeleLocatorDT().setItDirty(true);

               if (elp.getStatusCd() != null &&
                   elp.getStatusCd().equals(NEDSSConstants.STATUS_ACTIVE)) {
                  elp.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
               }
               else {
                  elp.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_INACTIVE);

               }
               arrELP.add(elp);
            }
         }

         personVO.setTheEntityLocatorParticipationDTCollection(arrELP);
      }
   }




   /**
    * This method is used to set the physical locations
    * onto the PersonVO.
    *
    * @param personVO PersonVO
    * @param physicalList ArrayList
    */
   private void setPhysicalLocations(PersonVO personVO, ArrayList<Object> physicalList) {

      if (physicalList != null) {

         Long personUID = personVO.getThePersonDT().getPersonUid();
        Iterator<Object>  itr = physicalList.iterator();
         ArrayList<Object> arrELP = (ArrayList<Object> ) personVO.
                            getTheEntityLocatorParticipationDTCollection();

         if (arrELP == null) {
            arrELP = new ArrayList<Object> ();

         }
         while (itr.hasNext()) {

            EntityLocatorParticipationDT elp = (EntityLocatorParticipationDT)
                                               itr.next();

            if (elp.getLocatorUid() == null) { // new one
               elp.setItNew(true);
               elp.setItDirty(false);
               elp.getThePhysicalLocatorDT().setItNew(true);
               elp.getThePhysicalLocatorDT().setItDirty(false);
               elp.setEntityUid(personUID);

               if (elp.getStatusCd() != null &&
                   elp.getStatusCd().equals(NEDSSConstants.STATUS_ACTIVE)) {
                  elp.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
               }
               else {
                  elp.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_INACTIVE);

                  //elp.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
               }
               arrELP.add(elp);
            }
            else {
               elp.setItNew(false);
               elp.setItDirty(true);
               elp.getThePhysicalLocatorDT().setItNew(false);
               elp.getThePhysicalLocatorDT().setItDirty(true);

               if (elp.getStatusCd() != null &&
                   elp.getStatusCd().equals(NEDSSConstants.STATUS_ACTIVE)) {
                  elp.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
               }
               else {
                  elp.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_INACTIVE);

               }
               arrELP.add(elp);
            }
         }

         personVO.setTheEntityLocatorParticipationDTCollection(arrELP);
      }
   }



   /**
    * This method is used to set the Role data onto
    * the PersonVO.
    *
    * @param personVO PersonVO
    * @param request HttpServletRequest
    */
   private void setRoles(PersonVO personVO, HttpServletRequest request) {
      HttpSession session = request.getSession();
      Object obj = session.getAttribute("NBSSecurityObject");

      if (obj != null) {

      }

      Long personUID = personVO.getThePersonDT().getPersonUid();
      String[] arrRoles = request.getParameterValues("rolesList");
      Long maxSeqNbr = new Long(0);
      ArrayList<Object> roleList = new ArrayList<Object> ();

      Collection<Object>  roleColl = personVO.getTheRoleDTCollection();

      if (roleColl != null) {
        Iterator<Object>  iter = roleColl.iterator();
         if (iter != null) {
            while (iter.hasNext()) {
               RoleDT currRoleDT = (RoleDT) iter.next();
               if (currRoleDT != null) {
                  logger.info(
                      "ROLE COLLECTION WASN'T NULL.  SETTING FOR DELETE");
                  currRoleDT.setItNew(false);
                  currRoleDT.setItDelete(true);
                  currRoleDT.setItDirty(false);
                  roleList.add(currRoleDT);

               }
            }
         }
      }
      


      if (arrRoles != null) {
         for (int i = 0, len = arrRoles.length; i < len; i++) {
            String strVal = arrRoles[i];
            RoleDT roleDT = new RoleDT();
            if (strVal != null && !strVal.equals("")) {
               roleDT.setItNew(true);
               roleDT.setItDelete(false);
               roleDT.setItDirty(false);
               roleDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
               roleDT.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
               roleDT.setSubjectEntityUid(personUID);
               roleDT.setSubjectClassCd(NEDSSConstants.PERSON);
               roleDT.setCd(strVal);
               roleDT.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
               roleDT.setStatusTime(new Timestamp(new Date().getTime()));
               roleDT.setAddUserId(new Long(userID));
               roleDT.setLastChgUserId(new Long(userID));
               maxSeqNbr = new Long(maxSeqNbr.intValue() + 1);
               roleDT.setRoleSeq(maxSeqNbr);
               roleList.add(roleDT);
            }
         }
      }

      personVO.setTheRoleDTCollection(roleList);

   }

   /**
    * This method is to take a PersonVO and send it to
    * the EJB for persistence to the database.
    *
    * @param person PersonVO
    * @param paramMethodName String
    * @param session HttpSession
    * @exception NEDSSAppConcurrentDataException
    * @exception NEDSSAppException
    * @exception javax.ejb.EJBException
    * @exception Exception
    * @return a Long
    */
   private Long sendProxyToEJB(PersonVO person, String paramMethodName,
                               HttpSession session) throws
       NEDSSAppConcurrentDataException, NEDSSAppException,
       javax.ejb.EJBException, Exception {

      logger.debug("address or telephone: " +
                   person.getTheEntityLocatorParticipationDTCollection());
      logger.info("You are inside sendProxyToEJB method of PersonSubmit");
      /**
       * Call the mainsessioncommand
       */MainSessionCommand msCommand = null;


      String sBeanJndiName = JNDINames.ENTITY_PROXY_EJB;
       
      String sMethod = paramMethodName;

   Object temp = null;

      if (paramMethodName.equals("setMPR")) {
         temp = person;
      }
      else if (paramMethodName.equals("deletePerson")) {
         temp = person.getThePersonDT().getPersonUid();

      }
      Object[] oParams = {temp};

      // if(msCommand == null)
      // {
      MainSessionHolder holder = new MainSessionHolder();
      msCommand = holder.getMainSessionCommand(session);

      // }
      ArrayList<?> resultUIDArr = new ArrayList<Object> ();
      resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);

      if ( (resultUIDArr != null) && (resultUIDArr.size() > 0)) {
         logger.info("Created or updated a person = " + resultUIDArr.get(0));

         Long result = (Long) resultUIDArr.get(0);

         logger.info("Long returned from EJB is: " + result);
         return result;
      }
      else {

         return null;
      }
   }
   /** This method is called to prepare the PersonVO object
    * for creating a new person in the system.
    *
    * @param personForm CompleteDemographicForm
    * @param securityObj NBSSecurityObj
    * @param session HttpSession
    * @param request HttpServletRequest
    * @return
    */
   private PersonVO createBasicHandler(CompleteDemographicForm personForm,
                                  NBSSecurityObj securityObj,
                                  HttpSession session,
                                  HttpServletRequest request) {

	   PersonVO personVO = personForm.getPerson();
	   String patientAsOfDateGeneral = personForm.getPatientAsOfDateGeneral();
	   String patientBirthTime = personForm.getPatientBirthTime();
	   String patientDeceasedDate = personForm.getPatientDeceasedDate();

      try {
    	 String strUserId = securityObj.getTheUserProfile().getTheUser().getEntryID();
    	 Long userId = new Long(strUserId);
         personVO.setItNew(true);
         personVO.setItDirty(false);
         Timestamp currentDate = new Timestamp(new Date().getTime());
         // set up the DT for the EJB
         PersonDT personDT = personVO.getThePersonDT();
         personDT.setItNew(true);
         personDT.setItDirty(false);
         personDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
         personDT.setPersonUid(new Long( -1));
         personDT.setCd("PAT");
         personDT.setAddTime(currentDate);
         if (patientAsOfDateGeneral != null && !patientAsOfDateGeneral.isEmpty())
        	 personDT.setAsOfDateAdmin_s(patientAsOfDateGeneral);
         else
        	 personDT.setAsOfDateAdmin(currentDate);
         if (patientBirthTime != null && !patientBirthTime.isEmpty())
        	 personDT.setBirthTime_s(patientBirthTime);
         if (personDT.getDeceasedIndCd() != null && personDT.getDeceasedIndCd().equalsIgnoreCase("Y")
        		 && patientDeceasedDate != null && !patientDeceasedDate.isEmpty())
        	 personDT.setDeceasedTime_s(patientDeceasedDate);
         personDT.setLastChgTime(currentDate);
         personDT.setLastChgUserId(userId);
         personDT.setRecordStatusTime(currentDate);
         personDT.setStatusTime(currentDate);
         personDT.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
         personDT.setElectronicInd(NEDSSConstants.NO);
         if (personDT.getDeceasedIndCd() != null ){
        	 personDT.setAsOfDateMorbidity_s(patientAsOfDateGeneral);
         }
         if (personDT.getMaritalStatusCd() != null || personDT.getEharsId() != null){
            	 personDT.setAsOfDateGeneral(personDT.getAsOfDateAdmin());
         }
         if (personDT.getEthnicGroupInd() != null ){
        	 personDT.setAsOfDateEthnicity_s(patientAsOfDateGeneral);
         }

         // set up the person names dt
         setBasicNames(personForm);

         // set the id's
         setBasicIds(personForm);

         setBasicRace(personForm);

         setBasicAddress(personForm);

         setBasicTelephones(personForm,userId);

         //if there is any LDF - process it
 	         if (!personForm.getPamClientVO().getAnswerMap().isEmpty() ||
	        		 !personForm.getPamClientVO().getArrayAnswerMap().isEmpty()) {
	        	 personVO.setTheStateDefinedFieldDataDTCollection(PersonUtil.extractPatientLDFs(personForm));
         }
      }
      catch (Exception e) {
  		logger.error("Exception in Patient Create Submit.editHandler: " + e.getMessage());
  		e.printStackTrace();
      }

      return personVO;
   }

   /**
    * This method will set the nameDT(s) to the PersonVO`
    *
    * @param CompleteDemographicForm personForm
    * @param
    */
   public void setBasicNames(CompleteDemographicForm personForm) {

	  PersonVO personVO = personForm.getPerson();
      PersonDT personDT = personForm.getPerson().getThePersonDT();
      Long personUID = personDT.getUid();
      String patientAsOfDateGeneral = personForm.getPatientAsOfDateGeneral();
      String lastNm = personDT.getLastNm();
      String firstNm = personDT.getFirstNm();
      String middleNm = personDT.getMiddleNm();
      String suffix = personDT.getNmSuffix();
      Integer maxSeqNbr = new Integer(0);


     //Name isn't even required - could be no name field entered
      if ( (lastNm != null && !lastNm.trim().equals("")) ||
          (firstNm != null && !firstNm.trim().equals("")) ||
          (middleNm != null && !middleNm.trim().equals("")) ||
          (suffix != null && !suffix.trim().equals(""))) {
    	  	Timestamp currentDate = new Timestamp(new Date().getTime());
          	PersonNameDT pdt = new PersonNameDT();
          	pdt.setItNew(true);
          	pdt.setItDirty(false);
            maxSeqNbr = new Integer(maxSeqNbr.intValue() + 1);
            pdt.setPersonNameSeq(maxSeqNbr);
          	pdt.setNmUseCd(NEDSSConstants.LEGAL_NAME);
          	pdt.setPersonNameSeq(new Integer(1));
          	pdt.setStatusTime(currentDate);
          	pdt.setAddTime(currentDate);
          	pdt.setRecordStatusTime(currentDate);
          	pdt.setPersonUid(personUID);
          	pdt.setRecordStatusCd(NEDSSConstants.ACTIVE);
          	pdt.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
          	pdt.setLastNm(lastNm);
          	pdt.setFirstNm(firstNm);
          	pdt.setMiddleNm(middleNm);
          	if (patientAsOfDateGeneral != null && !patientAsOfDateGeneral.isEmpty()){
          		pdt.setAsOfDate_s(patientAsOfDateGeneral);
              	personDT.setAsOfDateSex_s(patientAsOfDateGeneral);
          	}else{
          		pdt.setAsOfDate(currentDate);
          		personDT.setAsOfDateSex(currentDate);
          	}
          	pdt.setNmSuffix(suffix);
          	Collection<Object>  pdts = new ArrayList<Object> ();
          	pdts.add(pdt);
          	personVO.setThePersonNameDTCollection(pdts);
      } //if name field exists

   } // setBasicNames()

   /**
    * This method will set the EntityIdDT(s) to the PersonVO.
    * SSN is a field on the screen while the other IDs come from batch entry.
    * @param CompleteDemographicForm personForm
    * 
    * 
    */
   public void setBasicIds(CompleteDemographicForm personForm) {

	    Long personUID = personForm.getPerson().getThePersonDT().getPersonUid();
	    String patientAsOfDateGeneral = personForm.getPatientAsOfDateGeneral();
	    String patientSSN = personForm.getPerson().getThePersonDT().getSSN();
	    Integer maxSeqNbr = new Integer(0);
	    
	    Collection<Object> idList = personForm.getPerson().getTheEntityIdDTCollection();
	    if (idList == null) {
	    	idList = new ArrayList<Object> ();
	    	personForm.getPerson().setTheEntityIdDTCollection(idList);
	    }

        //SSN is on a field on the screen
	    if (patientSSN != null && !patientSSN.isEmpty()) {
	    	 Timestamp currentDate = new Timestamp(new Date().getTime());
	         EntityIdDT entityIdDt = null;
	         entityIdDt = new EntityIdDT();
	         maxSeqNbr = new Integer(maxSeqNbr.intValue() + 1);
	         entityIdDt.setEntityIdSeq(maxSeqNbr);
	         entityIdDt.setAddTime(currentDate);
	         entityIdDt.setLastChgTime(currentDate);
	         entityIdDt.setRecordStatusTime(currentDate);
	         entityIdDt.setStatusTime(currentDate);
	         entityIdDt.setEntityUid(personUID);
	         entityIdDt.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
	         entityIdDt.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
	         entityIdDt.setTypeCd("SS");
	         entityIdDt.setTypeDescTxt("Social Security Number");
	         entityIdDt.setAssigningAuthorityCd("SSA");
	         entityIdDt.setAssigningAuthorityDescTxt("Social Security Administration");
	         entityIdDt.setRootExtensionTxt(patientSSN);
	         if (patientAsOfDateGeneral != null && !patientAsOfDateGeneral.isEmpty())
	        	 entityIdDt.setAsOfDate_s(patientAsOfDateGeneral);
	         else
	        	 entityIdDt.setAsOfDate(currentDate);
	         idList.add(entityIdDt);
	   }
	    //The Identification section is a repeating batch entry 
	    //ID Type and ID Value are required, Assigning Authority is optional
	    ArrayList<BatchEntry> beList = personForm.getIdBatchEntryList();
	    Iterator<BatchEntry>  itrBatchEntry = beList.iterator();
        while (itrBatchEntry.hasNext()) {
        	BatchEntry idAnswer = (BatchEntry) itrBatchEntry.next();
        	Map<String,String> answerMap = idAnswer.getAnswerMap();
	    	Timestamp currentDate = new Timestamp(new Date().getTime());
	        EntityIdDT entityIdDt = new EntityIdDT();
	        maxSeqNbr = new Integer(maxSeqNbr.intValue() + 1);
	        entityIdDt.setEntityIdSeq(maxSeqNbr);
	        entityIdDt.setAddTime(currentDate);
	        entityIdDt.setLastChgTime(currentDate);
	        entityIdDt.setRecordStatusTime(currentDate);
	        entityIdDt.setStatusTime(currentDate);
	        entityIdDt.setEntityUid(personUID);
	        entityIdDt.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
	        entityIdDt.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
            entityIdDt.setItNew(true);
            entityIdDt.setItDirty(false);
	        if (patientAsOfDateGeneral != null && !patientAsOfDateGeneral.isEmpty())
	        	 entityIdDt.setAsOfDate_s(patientAsOfDateGeneral);
	        else
	        	 entityIdDt.setAsOfDate(currentDate);
	         String typeCd = answerMap.get("typeID");
	        if (typeCd == null || typeCd.isEmpty()) {
	        	 logger.error("Error: SetBasicIDs ID Type not found?");
	        	 continue;
	        }
	        entityIdDt.setTypeCd(typeCd);
	        String typeCdTxt = answerMap.get("typeIDTxt");
	        if (typeCdTxt != null && !typeCdTxt.isEmpty()) 
	        	 entityIdDt.setTypeDescTxt(typeCdTxt);
	        String assigningAuthority = answerMap.get("assigningAuthority");
	        if (assigningAuthority != null && !assigningAuthority.isEmpty())
	        	 entityIdDt.setAssigningAuthorityCd(assigningAuthority);
	        String assigningAuthorityTxt = answerMap.get("assigningAuthorityTxt");
	        if (assigningAuthorityTxt != null && !assigningAuthorityTxt.isEmpty())
	        	 entityIdDt.setAssigningAuthorityDescTxt(assigningAuthorityTxt);
	        String idValue = answerMap.get("idValue");
	        if (idValue == null || idValue.isEmpty()) {
	        	 logger.error("Error: SetBasicIDs ID Type was found but not the ID Value?");
	        	 continue;
	        }
	        entityIdDt.setRootExtensionTxt(idValue);
	        idList.add(entityIdDt);
         } //hasNext
	      PersonUtil.setAssigningAuthorityforIds(idList, NEDSSConstants.EI_AUTH);
        
       
   }
   /**
    * This method is used by the Person-Add Basic page.
    * Set the value of the race chosen set in personForm.ClientVO.
    * Always assumes new person. (No existing race collection.)
    * 
    * @param CompleteDemographicForm personForm
    */
   public void setBasicRace(CompleteDemographicForm personForm) {

		  PersonVO personVO = personForm.getPerson();
		  String patientAsOfDateGeneral = personForm.getPatientAsOfDateGeneral();
	      personForm.getPerson().getThePersonDT();
	      ArrayList<Object> raceList = new ArrayList<Object> ();
	      PamClientVO clientVO = personForm.getPamClientVO();

	          int unknownRace = clientVO.getUnKnownRace();
	          if (unknownRace != 0) {
	        	 Timestamp currentDate = new Timestamp(new Date().getTime());
	             PersonRaceDT raceDT = new PersonRaceDT();
	             raceDT.setItNew(true);
	             raceDT.setItDelete(false);
	             raceDT.setItDirty(false);
	             raceDT.setRaceCategoryCd(NEDSSConstants.UNKNOWN);
	             raceDT.setRaceCd(NEDSSConstants.UNKNOWN);
	             raceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
	             raceDT.setRaceDescTxt("Unknown");
	             if (patientAsOfDateGeneral != null && !patientAsOfDateGeneral.isEmpty())
	            	 raceDT.setAsOfDate_s(patientAsOfDateGeneral);
	             else
	            	 raceDT.setAsOfDate(currentDate);
	             raceList.add(raceDT);
	          }
	          int otherRace = clientVO.getOtherRace();
	          if (otherRace != 0) {
	        	 Timestamp currentDate = new Timestamp(new Date().getTime());
	             PersonRaceDT raceDT = new PersonRaceDT();
	             raceDT.setItNew(true);
	             raceDT.setItDelete(false);
	             raceDT.setItDirty(false);
	             raceDT.setRaceCategoryCd(NEDSSConstants.OTHER_RACE);
	             raceDT.setRaceCd(NEDSSConstants.OTHER_RACE);
	             raceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
	             raceDT.setRaceDescTxt("Other Race");
	             if (patientAsOfDateGeneral != null && !patientAsOfDateGeneral.isEmpty())
	            	 raceDT.setAsOfDate_s(patientAsOfDateGeneral);
	             else
	            	 raceDT.setAsOfDate(currentDate);
	             raceList.add(raceDT);
	          }
	          
	          int refusedToAnswer = clientVO.getRefusedToAnswer();
	          if (refusedToAnswer != 0) {
	        	 Timestamp currentDate = new Timestamp(new Date().getTime());
	             PersonRaceDT raceDT = new PersonRaceDT();
	             raceDT.setItNew(true);
	             raceDT.setItDelete(false);
	             raceDT.setItDirty(false);
	             raceDT.setRaceCategoryCd(NEDSSConstants.REFUSED_TO_ANSWER);
	             raceDT.setRaceCd(NEDSSConstants.REFUSED_TO_ANSWER);
	             raceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
	             raceDT.setRaceDescTxt("Refuser to answer");
	             if (patientAsOfDateGeneral != null && !patientAsOfDateGeneral.isEmpty())
	            	 raceDT.setAsOfDate_s(patientAsOfDateGeneral);
	             else
	            	 raceDT.setAsOfDate(currentDate);
	             raceList.add(raceDT);
	          }
	          int notAsked = clientVO.getNotAsked();
	          if (notAsked != 0) {
	        	 Timestamp currentDate = new Timestamp(new Date().getTime());
	             PersonRaceDT raceDT = new PersonRaceDT();
	             raceDT.setItNew(true);
	             raceDT.setItDelete(false);
	             raceDT.setItDirty(false);
	             raceDT.setRaceCategoryCd(NEDSSConstants.NOT_ASKED);
	             raceDT.setRaceCd(NEDSSConstants.NOT_ASKED);
	             raceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
	             raceDT.setRaceDescTxt("not asked");
	             if (patientAsOfDateGeneral != null && !patientAsOfDateGeneral.isEmpty())
	            	 raceDT.setAsOfDate_s(patientAsOfDateGeneral);
	             else
	            	 raceDT.setAsOfDate(currentDate);
	             raceList.add(raceDT);
	          }

	          int americanIndianCategory = clientVO.getAmericanIndianAlskanRace();
	          if (americanIndianCategory != 0) {
	        	 Timestamp currentDate = new Timestamp(new Date().getTime());
	             PersonRaceDT raceDT = new PersonRaceDT();
	             raceDT.setItNew(true);
	             raceDT.setItDelete(false);
	             raceDT.setItDirty(false);
	             raceDT.setRaceCategoryCd(NEDSSConstants.
	                                      AMERICAN_INDIAN_OR_ALASKAN_NATIVE);
	             raceDT.setRaceCd(NEDSSConstants.AMERICAN_INDIAN_OR_ALASKAN_NATIVE);
	             raceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
	             if (patientAsOfDateGeneral != null && !patientAsOfDateGeneral.isEmpty())
	            	 raceDT.setAsOfDate_s(patientAsOfDateGeneral);
	             else
	            	 raceDT.setAsOfDate(currentDate);
	             raceList.add(raceDT);
	          }

	          int whiteCategory = clientVO.getWhiteRace();
              if (whiteCategory != 0) {
            	 Timestamp currentDate = new Timestamp(new Date().getTime());
	             PersonRaceDT raceDT = new PersonRaceDT();
	             raceDT.setItNew(true);
	             raceDT.setItDelete(false);
	             raceDT.setItDirty(false);
	             raceDT.setRaceCategoryCd(NEDSSConstants.WHITE);
	             raceDT.setRaceCd(NEDSSConstants.WHITE);
	             raceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
	             if (patientAsOfDateGeneral != null && !patientAsOfDateGeneral.isEmpty())
	            	 raceDT.setAsOfDate_s(patientAsOfDateGeneral);
	             else
	            	 raceDT.setAsOfDate(currentDate);
	             raceDT.setAddTime(new Timestamp(new Date().getTime()));
	             raceList.add(raceDT);
	          }

	          int africanCategory = clientVO.getAfricanAmericanRace();
             if (africanCategory != 0) {
	             Timestamp currentDate = new Timestamp(new Date().getTime());
	             PersonRaceDT raceDT = new PersonRaceDT();
	             raceDT.setItNew(true);
	             raceDT.setItDelete(false);
	             raceDT.setItDirty(false);
	             raceDT.setRaceCategoryCd(NEDSSConstants.AFRICAN_AMERICAN);
	             raceDT.setRaceCd(NEDSSConstants.AFRICAN_AMERICAN);
	             raceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
	             if (patientAsOfDateGeneral != null && !patientAsOfDateGeneral.isEmpty())
	            	 raceDT.setAsOfDate_s(patientAsOfDateGeneral);
	             else
	            	 raceDT.setAsOfDate(currentDate);
	             raceDT.setAddTime(new Timestamp(new Date().getTime()));
	             raceList.add(raceDT);
	          }

	          int asianCategory = clientVO.getAsianRace();
              if (asianCategory != 0) {
            	 Timestamp currentDate = new Timestamp(new Date().getTime());
	             PersonRaceDT raceDT = new PersonRaceDT();
	             raceDT.setItNew(true);
	             raceDT.setItDelete(false);
	             raceDT.setItDirty(false);
	             raceDT.setRaceCategoryCd(NEDSSConstants.ASIAN);
	             raceDT.setRaceCd(NEDSSConstants.ASIAN);
	             raceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
	             if (patientAsOfDateGeneral != null && !patientAsOfDateGeneral.isEmpty())
	            	 raceDT.setAsOfDate_s(patientAsOfDateGeneral);
	             else
	            	 raceDT.setAsOfDate(currentDate);
	             raceDT.setAddTime(new Timestamp(new Date().getTime()));
	             raceList.add(raceDT);
	          }

	          int hawaiianCategory = clientVO.getHawaiianRace();
	          if (hawaiianCategory != 0) {
	        	 Timestamp currentDate = new Timestamp(new Date().getTime());
	             PersonRaceDT raceDT = new PersonRaceDT();
	             raceDT.setItNew(true);
	             raceDT.setItDelete(false);
	             raceDT.setItDirty(false);
	             raceDT.setRaceCategoryCd(NEDSSConstants.
	                                      NATIVE_HAWAIIAN_OR_PACIFIC_ISLANDER);
	             raceDT.setRaceCd(NEDSSConstants.NATIVE_HAWAIIAN_OR_PACIFIC_ISLANDER);
	             raceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
	             if (patientAsOfDateGeneral != null && !patientAsOfDateGeneral.isEmpty())
	            	 raceDT.setAsOfDate_s(patientAsOfDateGeneral);
	             else
	            	 raceDT.setAsOfDate(currentDate);
	             raceDT.setAddTime(new Timestamp(new Date().getTime()));
	             raceList.add(raceDT);
	          }

         // prepare the VO
	     if (!raceList.isEmpty())
	    	 personVO.setThePersonRaceDTCollection(raceList);
   }

   /**
    * This method is used by the Person-Add Basic screen.  
    * The address entered will be set as a home address.  
    * This data goes into PersonVO.
    *
    * @param CompleteDemographicForm personForm
    */
   public void setBasicAddress(CompleteDemographicForm personForm) {

	PersonVO personVO = personForm.getPerson();
	String asOfDateStr = personForm.getPatientAsOfDateGeneral();
	PersonDT personDT = personForm.getPerson().getThePersonDT();
	Long personUID = personDT.getUid();
	PostalLocatorDT postalLocatorDT =   personForm.getAddress(0).getThePostalLocatorDT();
	String city = postalLocatorDT.getCityCd();
	String street1 = postalLocatorDT.getStreetAddr1();
    String street2 = postalLocatorDT.getStreetAddr2();
    String zip = postalLocatorDT.getZipCd();
    String state = postalLocatorDT.getStateCd();
    String county = postalLocatorDT.getCntyCd();
    String country = postalLocatorDT.getCntryCd();
    String censusTract = postalLocatorDT.getCensusTract();
    if(country == null)
    	  country = "840";  //USA

    logger.info("street1: " + street1 + " street2: " + street2 + " City: " + city+ " state: " + state + " zip: " + zip + "county: " + county);
    if ( (city == null || city.isEmpty()) &&
          (street1 == null || street1.isEmpty()) &&
          (street2 == null || street2.isEmpty()) &&
          (zip == null || zip.isEmpty()) &&
          (county == null || county.isEmpty()) &&
          (state==null || state.isEmpty()) && (censusTract==null || censusTract.isEmpty())) {
    	    logger.info("No addresses info to process in setBasicAddress()");
    	  	//personForm.setAddressCollection(null);
    	    return;
      }

      logger.info("Inside setBasicAddress()");
      Collection<Object>  arrELP = personVO.
                             getTheEntityLocatorParticipationDTCollection();
      if (arrELP == null) {
            arrELP = new ArrayList<Object> ();
            personVO.setTheEntityLocatorParticipationDTCollection(arrELP);
      }
      EntityLocatorParticipationDT elp = personForm.getAddress(0);
      Timestamp currentDate = new Timestamp(new Date().getTime());
      elp.setItNew(true);
      elp.setItDirty(false);
      elp.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
      elp.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
      elp.setEntityUid(personUID);
      elp.setCd(NEDSSConstants.HOME);
      elp.setClassCd(NEDSSConstants.POSTAL);
      elp.setUseCd(NEDSSConstants.HOME);
      if (asOfDateStr != null && !asOfDateStr.isEmpty())
    	  elp.setAsOfDate_s(asOfDateStr);
      else
    	  elp.setAsOfDate(currentDate);

      postalLocatorDT.setItNew(true);
      postalLocatorDT.setItDirty(false);
      postalLocatorDT.setAddTime(currentDate);
      postalLocatorDT.setRecordStatusTime(currentDate);
      postalLocatorDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);

      //elp.setThePostalLocatorDT(postalLocatorDT);
      arrELP.add(elp);
      logger.info("Number of address in setBasicAddresses: " + arrELP.size());
      personVO.setTheEntityLocatorParticipationDTCollection(arrELP);
   } //end of setBasicAddress()

   /**
    * This method is used by the Person-Add Basic page.  
    * Cell, home, work and email address can be entered. 
    * This data is set into the PersonVO.
    * Note that the email is stored as part of the phone.
    *
    * @param CompleteDemographicForm personForm
    */
   public void setBasicTelephones(CompleteDemographicForm personForm, Long userId) {
	   logger.info("Inside setBasicTelephones");

	  PersonVO personVO = personForm.getPerson();
	  String asOfDateStr = personForm.getPatientAsOfDateGeneral();
	  PersonDT personDT = personForm.getPerson().getThePersonDT();
	  Long personUID = personDT.getUid();

      Collection<Object>  arrELP = personVO.getTheEntityLocatorParticipationDTCollection();

      EntityLocatorParticipationDT elpHome = new EntityLocatorParticipationDT();
      TeleLocatorDT teleDTHome = new TeleLocatorDT();
      EntityLocatorParticipationDT elpWork = new EntityLocatorParticipationDT();
      TeleLocatorDT teleDTWork = new TeleLocatorDT();
      EntityLocatorParticipationDT elpCell = new EntityLocatorParticipationDT();
      TeleLocatorDT teleDTCell = new TeleLocatorDT();
      EntityLocatorParticipationDT elpEmail = new EntityLocatorParticipationDT();
      TeleLocatorDT teleDTEmail = new TeleLocatorDT();
      
      String homePhone = personForm.getPatientHomePhone();
      String workPhone = personForm.getPatientWorkPhone();
      String workExt = personForm.getPatientWorkPhoneExt();
      String cellPhone = personForm.getPatientCellPhone();
      String email = personForm.getPatientEmail();
      boolean emailSet = false;
      
      logger.info("homePHone: " + homePhone + "   :workPhone: " + workPhone + "Ext:" + workExt);

      if ( (homePhone == null || homePhone.isEmpty()) &&
    		  (workPhone == null || workPhone.isEmpty()) &&
    		  (cellPhone == null || cellPhone.isEmpty()) &&
    		  (email == null || email.isEmpty()) ){
    	  //nothing to do -- return
    	  return;
      }
      //have an telephone to process..
      if (arrELP == null) {
          arrELP = new ArrayList<Object> ();
          personVO.setTheEntityLocatorParticipationDTCollection(arrELP);
       }

      //Cell Phone
      if (cellPhone != null && !cellPhone.isEmpty() ){
    	 Timestamp currentDate = new Timestamp(new Date().getTime());
         elpCell.setItNew(true);
         elpCell.setItDirty(false);
         elpCell.setEntityUid(personUID);
         elpCell.setAddTime(new Timestamp(new Date().getTime()));
         elpCell.setAddUserId(Long.valueOf(userId));
         elpCell.setClassCd(NEDSSConstants.TELE);
         elpCell.setCd(NEDSSConstants.CELL);
         elpCell.setUseCd(NEDSSConstants.MOBILE);
         elpCell.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
         elpCell.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
         if (asOfDateStr != null && !asOfDateStr.isEmpty())
        	 elpCell.setAsOfDate_s(asOfDateStr);
         else
        	 elpCell.setAsOfDate(currentDate);
         teleDTCell.setAddTime(new Timestamp(new Date().getTime()));
         teleDTCell.setAddUserId(Long.valueOf(userId));
         teleDTCell.setPhoneNbrTxt(cellPhone);
         teleDTCell.setItNew(true);
         teleDTCell.setItDirty(false);
         teleDTCell.setAddTime(currentDate);
         teleDTCell.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);         
         elpCell.setTheTeleLocatorDT(teleDTCell);
         arrELP.add(elpCell);
      } //end cell
      
      
      
      //Home Phone
      if (homePhone != null && !homePhone.isEmpty()) {
    	 Timestamp currentDate = new Timestamp(new Date().getTime());
         elpHome.setItNew(true);
         elpHome.setAsOfDate(currentDate);
         elpHome.setItDirty(false);
         elpHome.setEntityUid(personUID);
         elpHome.setAddTime(new Timestamp(new Date().getTime()));
		 elpHome.setAddUserId(Long.valueOf(userId));
         elpHome.setClassCd(NEDSSConstants.TELE);
         elpHome.setCd(NEDSSConstants.PHONE);
         elpHome.setUseCd(NEDSSConstants.HOME);
         elpHome.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
         elpHome.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
         if (asOfDateStr != null && !asOfDateStr.isEmpty())
        	 elpHome.setAsOfDate_s(asOfDateStr);
         else
        	 elpHome.setAsOfDate(currentDate);
         teleDTHome.setAddTime(new Timestamp(new Date().getTime()));
         teleDTHome.setAddUserId(Long.valueOf(userId));
         teleDTHome.setPhoneNbrTxt(homePhone);
         //teleDTHome.setExtensionTxt(homeExt);
         teleDTHome.setItNew(true);
         teleDTHome.setItDirty(false);
         teleDTHome.setAddTime(currentDate);
         teleDTHome.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
         
         elpHome.setTheTeleLocatorDT(teleDTHome);
         arrELP.add(elpHome);
      } //end home

      //Work Phone
      if (workPhone != null && !workPhone.isEmpty()) {
    	 Timestamp currentDate = new Timestamp(new Date().getTime());
         elpWork.setItNew(true);
         elpWork.setItDirty(false);
         elpWork.setEntityUid(personUID);
         elpWork.setAddTime(new Timestamp(new Date().getTime()));
		 elpWork.setAddUserId(Long.valueOf(userId));
         elpWork.setClassCd(NEDSSConstants.TELE);
         elpWork.setCd(NEDSSConstants.PHONE);
         elpWork.setUseCd(NEDSSConstants.WORK_PHONE);
         elpWork.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
         elpWork.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
         if (asOfDateStr != null && !asOfDateStr.isEmpty())
        	 elpWork.setAsOfDate_s(asOfDateStr);
         else
        	 elpHome.setAsOfDate(currentDate);
         teleDTWork.setAddTime(new Timestamp(new Date().getTime()));
         teleDTWork.setAddUserId(Long.valueOf(userId));
         teleDTWork.setExtensionTxt(workExt);
         teleDTWork.setPhoneNbrTxt(workPhone);
         teleDTWork.setItNew(true);
         teleDTWork.setItDirty(false);
         teleDTWork.setAddTime(currentDate);
         teleDTWork.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
         
         elpWork.setTheTeleLocatorDT(teleDTWork);
         arrELP.add(elpWork);
      } //end work
 
      
      //if email still not set...
      if (email != null && !email.isEmpty() && !emailSet) { 
     	 Timestamp currentDate = new Timestamp(new Date().getTime());
         elpEmail.setItNew(true);
         elpEmail.setItDirty(false);
         elpEmail.setEntityUid(personUID);
         elpEmail.setAddTime(new Timestamp(new Date().getTime()));
		 elpEmail.setAddUserId(Long.valueOf(userId));         
         elpEmail.setClassCd(NEDSSConstants.TELE);
         elpEmail.setCd(NEDSSConstants.NET);
         elpEmail.setUseCd(NEDSSConstants.HOME);
         elpEmail.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
         elpEmail.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
         if (asOfDateStr != null && !asOfDateStr.isEmpty())
        	 elpEmail.setAsOfDate_s(asOfDateStr);
         else
        	 elpEmail.setAsOfDate(currentDate);
         teleDTEmail.setAddTime(new Timestamp(new Date().getTime()));
         teleDTEmail.setAddUserId(Long.valueOf(userId));
         teleDTEmail.setItNew(true);
         teleDTEmail.setItDirty(false);
         teleDTEmail.setAddTime(currentDate);
         teleDTEmail.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
         teleDTEmail.setEmailAddress(email);
         elpEmail.setTheTeleLocatorDT(teleDTEmail);
         arrELP.add(elpEmail);
      } //email only    
      
   } //end setBasicTelephones()
}// end of Class
