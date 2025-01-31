package gov.cdc.nedss.webapp.nbs.action.person;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import gov.cdc.nedss.association.dt.RoleDT;
import gov.cdc.nedss.entity.entityid.dt.EntityIdDT;
import gov.cdc.nedss.entity.person.dt.PersonNameDT;
import gov.cdc.nedss.entity.person.vo.PersonSummaryVO;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.exception.NEDSSAppConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.locator.dt.EntityLocatorParticipationDT;
import gov.cdc.nedss.locator.dt.PostalLocatorDT;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.webapp.nbs.action.person.util.PersonUtil;
import gov.cdc.nedss.webapp.nbs.action.util.CommonAction;
import gov.cdc.nedss.webapp.nbs.action.util.ErrorMessageHelper;
import gov.cdc.nedss.webapp.nbs.form.person.CompleteDemographicForm;

/** The PersonSubmit class is class that is accessed from the front-end so that person data can be submitted to the back end.  It is used in creating new persons into the NEDSS system.  It is also used for edits to existing persons in the system.
 */
public class PatientEditSubmit
    extends CommonAction {

   /**
    * Instance of a String to hold value for
    * userID.
    */
   private String userID = "";
    
   //For logging
   /**
    * Instance of the LogUtils class.
    */
   static final LogUtils logger = new LogUtils(PatientEditSubmit.class.getName());

   /** A constructor for the PersonSubmit class.
    */
   public PatientEditSubmit() {
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
      if (session == null) {
         logger.debug("error no session");
         return mapping.findForward("login");
      }

      Object obj = session.getAttribute("NBSSecurityObject");

      if (obj != null) {
         securityObj = (NBSSecurityObj) obj;

      }

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

         NBSContext.store(session, "DSFileTab", "2");
         personVO = editHandler(personForm, securityObj, session, request,
                                response);

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
         personForm.reset();
      }

      /*******************************************
       * CANCEL ACTION
       */
      else if (contextAction.equalsIgnoreCase("Cancel")) {
         logger.info("You are attempting to cancel. CurrentTask is: " +
                     sCurrentTask);
         NBSContext.store(session, "DSFileTab", "2");
         ErrorMessageHelper.setErrMsgToRequest(request, "ps166");
         personForm.reset();
      }
      
      EntityLocatorParticipationDT elp = new EntityLocatorParticipationDT();
	   PostalLocatorDT dt = new PostalLocatorDT();
	   dt.setCityDescTxt(null);
	   dt.setStateCd("");
	   
	   dt.setCntyCd("");
	   dt.setCntryCd("");  
	   elp.setThePostalLocatorDT(dt);
	   personForm.setDeceasedAddress(elp);
	   personForm.setBirthAddress(elp);
	   

      request.setAttribute("ContextAction", contextAction);


      request.setAttribute("personUID", UID);
      return mapping.findForward(contextAction);
   }


   /**
    * This method prepares the PersonVO before an edit
    * occurs to person data.
    *
    * @param personForm CompleteDemographicForm
    * @param securityObj NBSSecurityObj
    * @param session HttpSession
    * @param request HttpServletRequest
    * @param response HttpServletResponse
    * @return a PersonVO that is ready to be sent to the EJB for updating.
    */
   private PersonVO editHandler(CompleteDemographicForm personForm,
                                NBSSecurityObj securityObj, HttpSession session,
                                HttpServletRequest request,
                                HttpServletResponse response) {

      PersonVO personVO = null;
      PersonUtil util = new PersonUtil();

      try {
         personVO = personForm.getPerson();
         personVO.setItDirty(true);
         personVO.setItNew(false);
         String userId = securityObj.getTheUserProfile().getTheUser().getEntryID();
         //handle HIV Questions
         if(!securityObj.getPermission(NBSBOLookup.GLOBAL,
	   				NBSOperationLookup.HIVQUESTIONS)){
        	 personVO.getThePersonDT().setEharsId((String)personForm.getAttributeMap().get(NEDSSConstants.EHARS_ID));
	   		}
         Collection<Object>  namesCollection= personVO.getThePersonNameDTCollection();
         util.setToNamesCollection(personForm);
         setNames(personVO, request,namesCollection,userId);
         Collection<Object>  idsCollection= personVO.getTheEntityIdDTCollection();
         util.setToIdsCollection(personForm);
         setIds(personVO, request,idsCollection,userId);
        // setIds(personVO, request);
          ArrayList<Object> arrELP = (ArrayList<Object> ) personVO.getTheEntityLocatorParticipationDTCollection();
          util.setToAddressCollection( personVO,personForm,userId);
         setAddresses(personVO, personForm.getAddressCollection(),arrELP,userId);
         util.setToPhoneCollection( personVO,personForm,userId);
         setTelephones(personVO, personForm.getTelephoneCollection(),arrELP,userId);
         setPhysicalLocations(personVO, personForm.getPhysicalCollection(), userId);
         setBirthAddress(personVO, personForm.getBirthAddress(),userId);
         setDeceasedAddress(personVO, personForm.getDeceasedAddress(),userId);
         util.setEthnicity(personVO, personForm, request,userId);
         //setRace(personVO, request);
         util.setRace(personVO, personForm,userId);
         setRoles(personVO, request);
         if (!personForm.getPamClientVO().getAnswerMap().isEmpty() ||
        		 !personForm.getPamClientVO().getArrayAnswerMap().isEmpty()) {
        	 personForm.setActionMode(NEDSSConstants.EDIT_SUBMIT_ACTION);
        	 personVO.setTheStateDefinedFieldDataDTCollection(PersonUtil.extractPatientLDFs(personForm));
         }

      }
      catch (Exception e) {
  		logger.error("Exception in PatientEditSubmit: " + e.getMessage());
  		e.printStackTrace();
      }

      return personVO;
   }
   
   /**
    * This method will set the nameDT(s) to the PersonVO.
    *
    * @param personVO PersonVO
    * @param request HttpServletRequest
    */
   public void setNames(PersonVO personVO, HttpServletRequest request,Collection<Object> oldNameCollection,String userId) {

      Collection<Object>  names = personVO.getThePersonNameDTCollection();
      Long personUID = personVO.getThePersonDT().getPersonUid();
      HttpSession session = request.getSession();
      String currentTask = NBSContext.getCurrentTask(session);
     
      logger.info("ContextAction in setNames method: " + currentTask);

      if (currentTask.startsWith("AddPatientBasic")) {
         logger.info(
             "Setting Names because currentTask starts with AddPatientBasic");
         String lastNm = request.getParameter("person.personNameDT_s[i].lastNm");
         String firstNm = request.getParameter(
             "person.personNameDT_s[i].firstNm");
         String middleNm = request.getParameter(
             "person.personNameDT_s[i].middleNm");
         String asOfDate = request.getParameter(
             "person.personNameDT_s[i].asOfDate_s");
         String suffix = request.getParameter(
             "person.personNameDT_s[i].nmSuffix");

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
            pdt.setAsOfDate_s(asOfDate);
            pdt.setNmSuffix(suffix);
            pdt.setAddUserId(Long.valueOf(userId));
            pdt.setLastChgUserId(Long.valueOf(userId));
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
            if (nameDT.getStatusCd() != null && nameDT.getStatusCd().equals(NEDSSConstants.I)) {
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
         
         Iterator<Object>  itrOldCount = oldNameCollection.iterator();
         while (itrOldCount.hasNext()) {

             PersonNameDT nameDT = (PersonNameDT) itrOldCount.next();             
             if (nameDT.getPersonNameSeq() != null) {

                if (nameDT.getPersonNameSeq().compareTo(maxSeqNbr) > 0) { // update the maxSeqNbr when you find a bigger one in the old collection
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
               nameDT.setLastChgTime(new Timestamp(new Date().getTime()));
               nameDT.setAddUserId(Long.valueOf(userId));
               nameDT.setLastChgUserId(Long.valueOf(userId));
               nameDT.setPersonUid(personUID);
            } //this is an old one
            else {

               //nameDT.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
               nameDT.setItNew(false);
               nameDT.setItDirty(true);
               
               nameDT.setStatusTime(new Timestamp(new Date().getTime()));
              // nameDT.setAddTime(new Timestamp(new Date().getTime()));
               nameDT.setRecordStatusTime(new Timestamp(new Date().getTime()));
               //nameDT.setAddUserId(Long.valueOf(userId));
               nameDT.setLastChgUserId(Long.valueOf(userId));
               nameDT.setLastChgTime(new Timestamp(new Date().getTime()));

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
      
      Iterator<Object>  itrNameOld = oldNameCollection.iterator();
      //Iterator<Object>  itrAddress = addressList.iterator();
      while (itrNameOld.hasNext()) {
    	  PersonNameDT nameOld = (PersonNameDT)itrNameOld.next();
          boolean exists=false;
          Iterator<Object> itrNames = names.iterator();
          while (itrNames.hasNext()) {
        	  PersonNameDT name = (PersonNameDT)itrNames.next();
              if(name.getPersonNameSeq() != null && name.getPersonNameSeq().equals(nameOld.getPersonNameSeq())){
             	 exists= true;
             	 break;
             	 
              }
          }
          if(!exists){
        	  nameOld.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_INACTIVE);
        	  names.add(nameOld);            	 
          }           
          
      }       
      
   }
   
   public void setIds(PersonVO personVO, HttpServletRequest request,Collection<Object> oldIdsCollection,String userId) {

	      Collection<Object>  ids = personVO.getTheEntityIdDTCollection();
	      Long personUID = personVO.getThePersonDT().getPersonUid();
	      HttpSession session = request.getSession();
	      NBSContext.getCurrentTask(session);    
	   
	      if (ids != null) {

	         logger.info(
	             "Ids COLLECTION wan't null in personSubmit createHandler");
	        Iterator<Object>  itrCount = ids.iterator();

	         //need to find the max seq nbr for existing names
	         Integer maxSeqNbr = new Integer(0);

	         while (itrCount.hasNext()) {

	        	 EntityIdDT idDT = (EntityIdDT) itrCount.next();
	          

	            if (idDT.getEntityIdSeq() != null) {

	               if (idDT.getEntityIdSeq().compareTo(maxSeqNbr) > 0) { // update the maxSeqNbr when you find a bigger one
	                  maxSeqNbr = idDT.getEntityIdSeq();
	               }
	            }
	         }
	         
	         Iterator<Object>  itrOldCount = oldIdsCollection.iterator();
	         while (itrOldCount.hasNext()) {

	        	 EntityIdDT idDT = (EntityIdDT) itrOldCount.next();             
	             if (idDT.getEntityIdSeq() != null) {

	                if (idDT.getEntityIdSeq().compareTo(maxSeqNbr) > 0) { // update the maxSeqNbr when you find a bigger one in the old collection
	                   maxSeqNbr = idDT.getEntityIdSeq();
	                }
	             }
	          }
	         

	        Iterator<Object>  itrIds = ids.iterator();

	         while (itrIds.hasNext()) {

	        	 EntityIdDT idDT = (EntityIdDT) itrIds.next();

	            if (idDT.getEntityIdSeq() == null) { //  this is a new one
	            	idDT.setItNew(true);
	            	idDT.setItDirty(false);
	               maxSeqNbr = new Integer(maxSeqNbr.intValue() + 1);
	               idDT.setEntityIdSeq(maxSeqNbr);

	               //nameDT.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
	               if (idDT.getStatusCd() != null &&
	            		   idDT.getStatusCd().equals(NEDSSConstants.STATUS_ACTIVE)) {
	            	   idDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
	               }
	               else {
	            	   idDT.setRecordStatusCd(NEDSSConstants.
	                                           RECORD_STATUS_INACTIVE);

	               }
	               idDT.setStatusTime(new Timestamp(new Date().getTime()));
	               idDT.setAddTime(new Timestamp(new Date().getTime()));
	               idDT.setRecordStatusTime(new Timestamp(new Date().getTime()));
	               idDT.setAddUserId(Long.valueOf(userId));
	               idDT.setLastChgUserId(Long.valueOf(userId));
	               idDT.setLastChgTime(new Timestamp(new Date().getTime()));
	               idDT.setEntityUid(personUID);
	            } //this is an old one
	            else {

	               //nameDT.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
	            	idDT.setItNew(false);
	            	idDT.setItDirty(true);
	            	idDT.setStatusTime(new Timestamp(new Date().getTime()));
	            	 //idDT.setAddUserId(Long.valueOf(userId));
		               idDT.setLastChgUserId(Long.valueOf(userId));
	                 // nameDT.setAddTime(new Timestamp(new Date().getTime()));
	            	idDT.setRecordStatusTime(new Timestamp(new Date().getTime()));
	            	 idDT.setLastChgTime(new Timestamp(new Date().getTime()));

	               if (idDT.getStatusCd() != null &&
	            		   idDT.getStatusCd().equals(NEDSSConstants.STATUS_ACTIVE)) {
	            	   idDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
	               }
	               else {
	            	   idDT.setRecordStatusCd(NEDSSConstants.
	                                           RECORD_STATUS_INACTIVE);

	               }
	               idDT.setEntityUid(personUID);
	            }

	            idDT.setLastChgTime(new Timestamp(new Date().getTime()));
		    	  }
	         }
	      
	      Iterator<Object>  itrIdsOld = oldIdsCollection.iterator();
	      //Iterator<Object>  itrAddress = addressList.iterator();
	      while (itrIdsOld.hasNext()) {
	    	  EntityIdDT idOld = (EntityIdDT)itrIdsOld.next();
	          boolean exists=false;
	          Iterator<Object> itrIds = ids.iterator();
	          while (itrIds.hasNext()) {
	        	  EntityIdDT idDt = (EntityIdDT)itrIds.next();
	              if(idDt.getEntityIdSeq() != null && idDt.getEntityIdSeq().equals(idOld.getEntityIdSeq())){	            	  
	             	 exists= true;
	             	 //This is for ELR and PHCR patients where assigning authority code does not exists in SRT - 08/20/2012
	             	 if(idOld.getAssigningAuthorityCd()!=null && (idDt.getAssigningAuthorityCd() == null || idDt.getAssigningAuthorityCd().equals("") || idDt.getAssigningAuthorityCd().equals(idOld.getAssigningAuthorityCd()))){
	             		idDt.setAssigningAuthorityCd(idOld.getAssigningAuthorityCd());
	             		idDt.setAssigningAuthorityDescTxt(idOld.getAssigningAuthorityDescTxt());
	             		idDt.setAssigningAuthorityIdType(idOld.getAssigningAuthorityIdType());
	             	 }
	             	 break;
	             	 
	              }
	          }
	          if(!exists){
	        	  idOld.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_INACTIVE);
	        	  idOld.setItDelete(true);
	        	  ids.add(idOld);            	 
	          }           
	          
	      }
	      PersonUtil.setAssigningAuthorityforIds(ids,NEDSSConstants.EI_AUTH);
   	}


   /**
    * This method will set the birthAddress onto the
    * PersonVO.
    *
    * @param personVO PersonVO
    * @param birthELP EntityLocatorParticipationDT
    */
   private void setBirthAddress(PersonVO personVO,
                                EntityLocatorParticipationDT birthELP, String userId) {

      //when adding new birth check if the user inputted anything
      PostalLocatorDT postal = birthELP.getThePostalLocatorDT();

      ArrayList<Object> arrELP = (ArrayList<Object> ) personVO.
                         getTheEntityLocatorParticipationDTCollection();

      if (arrELP == null) {
         arrELP = new ArrayList<Object> ();
      }

      if (birthELP != null) {
         
    	  
    	  if ( (birthELP.getLocatorUid() == null || birthELP.getLocatorUid().longValue()==0) 
    				&&( (postal.getCityDescTxt() == null) || (postal.getCityDescTxt().trim().equals(""))) 
    				&&( (postal.getCntryDescTxt() == null) ||(postal.getCntryDescTxt().trim().equals(""))) 
    				&&( (postal.getCntyDescTxt() == null) || (postal.getCntyDescTxt().trim().equals(""))) 
    				&&( (postal.getStateCd() == null) ||     (postal.getStateCd().trim().equals(""))) 
    				&&( (postal.getCntryCd() == null) ||     (postal.getCntryCd().trim().equals("")))
    				){
            //A birth address has not been entered.....EVER.
            //So do nothing
            return;
         }

    	  if ( (birthELP.getLocatorUid() == null || birthELP.getLocatorUid().longValue()==0) 
    			  &&	( ( (postal.getCityDescTxt() != null) && (!postal.getCityDescTxt().trim().equals(""))) 
    			 ||( (postal.getCntryDescTxt() != null) && (!postal.getCntryDescTxt().trim().equals(""))) 
    			 ||( (postal.getCntyDescTxt() != null) && (!postal.getCntyDescTxt().trim().equals("")))
    			 ||( (postal.getStateCd() != null) &&     (!postal.getStateCd().trim().equals("")))
    			 ||( (postal.getCntryCd() != null) &&       (!postal.getCntryCd().trim().equals("")))))	 { //a new one
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
            
            birthELP.setStatusTime(new Timestamp(new Date().getTime()));
            birthELP.setAddTime(new Timestamp(new Date().getTime()));
            birthELP.setRecordStatusTime(new Timestamp(new Date().getTime()));
            birthELP.setAddUserId(Long.valueOf(userId));
            birthELP.setLastChgUserId(Long.valueOf(userId));
            birthELP.setLastChgTime(new Timestamp(new Date().getTime()));
            arrELP.add(birthELP);

         } // an old one
    	  else if ( (birthELP.getLocatorUid() != null) 
    			  && ( ( (postal.getCityDescTxt() != null) && (!postal.getCityDescTxt().trim().equals(""))) 
    			  || ( (postal.getCntryDescTxt() != null) && (!postal.getCntryDescTxt().trim().equals(""))) 
    			  || ( (postal.getCntyDescTxt() != null) &&  (!postal.getCntyDescTxt().trim().equals(""))) 
    			  || ( (postal.getStateCd() != null) &&  (!postal.getStateCd().trim().equals(""))) 
    			  || ((postal.getCntryCd() != null) &&    (!postal.getCntryCd().trim().equals(""))))) {
            birthELP.setItNew(false);
            birthELP.setItDirty(true);
            birthELP.getThePostalLocatorDT().setItNew(false);
            birthELP.getThePostalLocatorDT().setItDirty(true);
            birthELP.setAsOfDate(personVO.getThePersonDT().getAsOfDateSex());
            birthELP.setClassCd("PST");
            birthELP.setCd("F");
            birthELP.setUseCd("BIR");
            
            birthELP.setStatusTime(new Timestamp(new Date().getTime()));
           // birthELP.setAddTime(new Timestamp(new Date().getTime()));
            birthELP.setRecordStatusTime(new Timestamp(new Date().getTime()));
           // birthELP.setAddUserId(Long.valueOf(userId));
            birthELP.setLastChgUserId(Long.valueOf(userId));
            birthELP.setLastChgTime(new Timestamp(new Date().getTime()));

            birthELP.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
            birthELP.setStatusCd(NEDSSConstants.STATUS_ACTIVE);

            arrELP.add(birthELP);
         }
         else {
            // If birth city/place are reset back to "blank" or empty value,
            // set the ELP status_cd and record_status_cd to be "Inactive"
        	 if (((postal.getCityDescTxt() == null) || (postal.getCityDescTxt().trim().equals(""))) 
        			 && ((postal.getCntryDescTxt() == null) || (postal.getCntryDescTxt().trim().equals(""))) 
        			 && ((postal.getCntyDescTxt() == null) || (postal.getCntyDescTxt().trim().equals(""))) 
        			 && ((postal.getStateCd() == null) || (postal.getStateCd().trim().equals(""))) 
        			 && ((postal.getCntryCd() == null) || (postal.getCntryCd().trim().equals("")))) {
               birthELP.setItNew(false);
               birthELP.setItDirty(true);
               birthELP.getThePostalLocatorDT().setItNew(false);
               birthELP.getThePostalLocatorDT().setItDirty(true);
               birthELP.setAsOfDate(personVO.getThePersonDT().getAsOfDateSex());
               birthELP.setClassCd("PST");
               birthELP.setCd("F");
               birthELP.setUseCd("BIR");
               birthELP.setStatusCd(NEDSSConstants.STATUS_INACTIVE);
               birthELP.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_INACTIVE);
               birthELP.setStatusTime(new Timestamp(new Date().getTime()));
           
               birthELP.setRecordStatusTime(new Timestamp(new Date().getTime()));
              
               birthELP.setLastChgUserId(Long.valueOf(userId));
               birthELP.setLastChgTime(new Timestamp(new Date().getTime()));
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
                                   EntityLocatorParticipationDT deceasedELP, String userId) {
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
               
               deceasedELP.setStatusTime(new Timestamp(new Date().getTime()));
               deceasedELP.setAddTime(new Timestamp(new Date().getTime()));
               deceasedELP.setRecordStatusTime(new Timestamp(new Date().getTime()));
               deceasedELP.setAddUserId(Long.valueOf(userId));
               deceasedELP.setLastChgUserId(Long.valueOf(userId));
               deceasedELP.setLastChgTime(new Timestamp(new Date().getTime()));
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
               deceasedELP.setStatusTime(new Timestamp(new Date().getTime()));
               
               deceasedELP.setRecordStatusTime(new Timestamp(new Date().getTime()));
              
               deceasedELP.setLastChgUserId(Long.valueOf(userId));
               deceasedELP.setLastChgTime(new Timestamp(new Date().getTime()));

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
                  deceasedELP.setStatusTime(new Timestamp(new Date().getTime()));
                  //deceasedELP.setAddTime(new Timestamp(new Date().getTime()));
                  deceasedELP.setRecordStatusTime(new Timestamp(new Date().getTime()));
                  //deceasedELP.setAddUserId(Long.valueOf(userId));
                  deceasedELP.setLastChgUserId(Long.valueOf(userId));
                  deceasedELP.setLastChgTime(new Timestamp(new Date().getTime()));
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
            deceasedELP.setStatusTime(new Timestamp(new Date().getTime()));
            //deceasedELP.setAddTime(new Timestamp(new Date().getTime()));
            deceasedELP.setRecordStatusTime(new Timestamp(new Date().getTime()));
           // deceasedELP.setAddUserId(Long.valueOf(userId));
            deceasedELP.setLastChgUserId(Long.valueOf(userId));
            deceasedELP.setLastChgTime(new Timestamp(new Date().getTime()));
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
   private void setAddresses(PersonVO personVO, ArrayList<Object> addressList, ArrayList<Object> arrELPOld,String userId) {

      Long personUID = personVO.getThePersonDT().getPersonUid();

      if (addressList != null) {

        Iterator<Object>  itrAddress = addressList.iterator();
        ArrayList<Object> arrELP =null;

         if (arrELP == null) {
            arrELP = new ArrayList<Object> ();

         }
         while (itrAddress.hasNext()) {

            EntityLocatorParticipationDT elp = (EntityLocatorParticipationDT)
                                               itrAddress.next();

            if (elp.getLocatorUid() == null || elp.getLocatorUid().longValue()==0) {
               elp.setItNew(true);
               elp.setItDirty(false);
               elp.getThePostalLocatorDT().setItNew(true);
               elp.getThePostalLocatorDT().setItDirty(false);
               elp.setEntityUid(personUID);
               elp.setStatusTime(new Timestamp(new Date().getTime()));
               elp.setAddTime(new Timestamp(new Date().getTime()));
             	elp.setRecordStatusTime(new Timestamp(new Date().getTime())); 
             	 elp.setAddUserId(Long.valueOf(userId));
	               elp.setLastChgUserId(Long.valueOf(userId));
	               elp.setLastChgTime(new Timestamp(new Date().getTime()));
              
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
               elp.setStatusTime(new Timestamp(new Date().getTime()));
             
          	   elp.setRecordStatusTime(new Timestamp(new Date().getTime()));
          	 
               elp.setLastChgUserId(Long.valueOf(userId));
               elp.setLastChgTime(new Timestamp(new Date().getTime()));

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
         
         Iterator<Object>  itrElpOld = arrELPOld.iterator();
         while (itrElpOld.hasNext()) {
             EntityLocatorParticipationDT elpOld = (EntityLocatorParticipationDT)
                                                itrElpOld.next();
            
             if(elpOld.getClassCd().equals(NEDSSConstants.POSTAL)){
            	 boolean exists=false;
             itrAddress = arrELP.iterator();
             while (itrAddress.hasNext()) {
                 EntityLocatorParticipationDT elp = (EntityLocatorParticipationDT)
                                                    itrAddress.next();
                 if(elp.getLocatorUid() != null && elp.getLocatorUid().equals(elpOld.getLocatorUid())){
                	 exists= true;
                	 break;
                	 
                 }
             }
             if(!exists){
            	 elpOld.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_INACTIVE);
            	 elpOld.setStatusCd(NEDSSConstants.STATUS_INACTIVE);
            	 
            	 arrELP.add(elpOld);            	 
              } 
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
   private void setTelephones(PersonVO personVO, ArrayList<Object> telephoneList,ArrayList<Object> arrELPOld,String userId) {

      if (telephoneList != null) {

         Long personUID = personVO.getThePersonDT().getPersonUid();
        Iterator<Object>  itr = telephoneList.iterator();
         ArrayList<Object> arrELP = (ArrayList<Object> ) personVO.
                            getTheEntityLocatorParticipationDTCollection();

         if (arrELP == null) {
            arrELP = new ArrayList<Object> ();

         }
         while (itr.hasNext()) {

            EntityLocatorParticipationDT elp = (EntityLocatorParticipationDT)
                                               itr.next();

            if (elp.getLocatorUid() == null || elp.getLocatorUid().longValue()==0) { // new one
               elp.setItNew(true);
               elp.setItDirty(false);
               elp.getTheTeleLocatorDT().setItNew(true);
               elp.getTheTeleLocatorDT().setItDirty(false);
               elp.setEntityUid(personUID);
               elp.setStatusTime(new Timestamp(new Date().getTime()));
               elp.setAddTime(new Timestamp(new Date().getTime()));
          	   elp.setRecordStatusTime(new Timestamp(new Date().getTime()));
          	    elp.setAddUserId(Long.valueOf(userId));
               elp.setLastChgUserId(Long.valueOf(userId));
               elp.setLastChgTime(new Timestamp(new Date().getTime()));

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
               elp.getTheTeleLocatorDT().setItNew(false);
               elp.getTheTeleLocatorDT().setItDirty(true);
               elp.setStatusTime(new Timestamp(new Date().getTime()));
           
            	elp.setRecordStatusTime(new Timestamp(new Date().getTime()));
            	
                elp.setLastChgUserId(Long.valueOf(userId));
                elp.setLastChgTime(new Timestamp(new Date().getTime()));

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
         Iterator<Object>  itrElpOld = arrELPOld.iterator();
        
         while (itrElpOld.hasNext()) {
             EntityLocatorParticipationDT elpOld = (EntityLocatorParticipationDT)
                                                itrElpOld.next();
             if(elpOld.getClassCd().equals(NEDSSConstants.TELE)){
             boolean exists=false;
             itr = arrELP.iterator();
             while (itr.hasNext()) {
                 EntityLocatorParticipationDT elp = (EntityLocatorParticipationDT)
                                                    itr.next();
                 if(elp.getLocatorUid() != null && elp.getLocatorUid().equals(elpOld.getLocatorUid())){
                	 exists= true;
                	 break;                	 
                 }
             }
             if(!exists){
            	 elpOld.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_INACTIVE);
            	 arrELP.add(elpOld);            	 
             } 
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
   private void setPhysicalLocations(PersonVO personVO, ArrayList<Object> physicalList,String userId) {

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

            if (elp.getLocatorUid() == null || elp.getLocatorUid().longValue()==0) { // new one
               elp.setItNew(true);
               elp.setItDirty(false);
               elp.getThePhysicalLocatorDT().setItNew(true);
               elp.getThePhysicalLocatorDT().setItDirty(false);
               elp.setEntityUid(personUID);
               elp.setStatusTime(new Timestamp(new Date().getTime()));
               elp.setAddTime(new Timestamp(new Date().getTime()));
            	elp.setRecordStatusTime(new Timestamp(new Date().getTime()));
            	 elp.setAddUserId(Long.valueOf(userId));
                 elp.setLastChgUserId(Long.valueOf(userId));
                 elp.setLastChgTime(new Timestamp(new Date().getTime()));

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
               elp.getThePhysicalLocatorDT().setItNew(false);
               elp.getThePhysicalLocatorDT().setItDirty(true);
               elp.setStatusTime(new Timestamp(new Date().getTime()));
             
            	elp.setRecordStatusTime(new Timestamp(new Date().getTime()));
            	 elp.setAddUserId(Long.valueOf(userId));
                 elp.setLastChgUserId(Long.valueOf(userId));
                 elp.setLastChgTime(new Timestamp(new Date().getTime()));

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
                  
                  currRoleDT.setStatusTime(new Timestamp(new Date().getTime()));
                  currRoleDT.setAddTime(new Timestamp(new Date().getTime()));
                  currRoleDT.setRecordStatusTime(new Timestamp(new Date().getTime()));
                  currRoleDT.setAddUserId(Long.valueOf(userID));
                  currRoleDT.setLastChgUserId(Long.valueOf(userID));
                  currRoleDT.setLastChgTime(new Timestamp(new Date().getTime()));
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
              
               roleDT.setAddTime(new Timestamp(new Date().getTime()));
               roleDT.setRecordStatusTime(new Timestamp(new Date().getTime()));
               roleDT.setLastChgTime(new Timestamp(new Date().getTime()));
             
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
      MainSessionHolder holder = new MainSessionHolder();
      msCommand = holder.getMainSessionCommand(session);

      
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

}
