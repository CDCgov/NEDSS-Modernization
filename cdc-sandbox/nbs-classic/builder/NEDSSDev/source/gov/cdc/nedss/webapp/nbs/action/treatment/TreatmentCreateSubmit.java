package gov.cdc.nedss.webapp.nbs.action.treatment;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.Date;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;

import gov.cdc.nedss.act.treatment.vo.*;
import gov.cdc.nedss.association.dt.*;
import gov.cdc.nedss.entity.organization.vo.*;
import gov.cdc.nedss.entity.person.dt.*;
import gov.cdc.nedss.entity.person.vo.*;
import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.proxy.ejb.treatmentproxyejb.bean.TreatmentProxyHome;
import gov.cdc.nedss.proxy.ejb.treatmentproxyejb.vo.*;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.systemservice.nbscontext.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.systemservice.util.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.webapp.nbs.action.person.util.*;
import gov.cdc.nedss.webapp.nbs.action.util.CommonAction;
import gov.cdc.nedss.webapp.nbs.form.treatment.*;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.*;
import gov.cdc.nedss.systemservice.util.*;
import gov.cdc.nedss.ldf.dt.StateDefinedFieldDataDT;

/**
 * Title:        TreatmentSubmit
 * Description:  this class retrieves data from EJB and puts them into request object for use in the xml file
 * Copyright:    Copyright (c) 2001-2002
 * Company:      CSC
 * @version 1.0
 */

public class TreatmentCreateSubmit
    extends CommonAction {
   static final LogUtils logger = new LogUtils(TreatmentCreateSubmit.class.
                                               getName());

   public TreatmentCreateSubmit() {
   }

   /**
        * An ActionForward represents a destination to which the controller servlet,
    * ActionServlet, might be directed to execute a RequestDispatcher.forward()
        * or HttpServletResponse.sendRedirect() to, as a result of processing activities
        * of an Action class. This is the only public method in TreatmentSubmit class
    * that handles the HttpServletRequest request object with ActionMapping and actionForm to generate
    * HttpServletResponse response object.
    * @param mapping ActionMapping object
    * @param actionForm ActionForm object
    * @param request HttpServletRequest  object
    * @param response HttpServletResponse  object
    * @throws IOException
    * @throws ServletException
    * @return : ActionForward object
    */
   public ActionForward execute(ActionMapping mapping, ActionForm actionForm,
                                HttpServletRequest request,
                                HttpServletResponse response) throws
       IOException, ServletException {
      logger.debug("Handling Treatment Store");
      TreatmentForm form = (TreatmentForm) actionForm;
      HttpSession session = request.getSession(false);
      if (session == null) {
         logger.debug("error no session");
         return mapping.findForward("login");
      }

      NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session.getAttribute(
          "NBSSecurityObject");
      if (nbsSecurityObj == null) {
         logger.fatal(
             "Error: No securityObj in the session, go back to login screen");
         return mapping.findForward("login");
      }

      String contextAction = request.getParameter("ContextAction");
      if (contextAction == null) {
         contextAction = (String) request.getAttribute("ContextAction");

      }
      logger.debug("TreatmentSubmit: ContextAction = " + contextAction);

      //use PersonSummary instead of PersonInfo - Chenchen
      //PersonSummaryVO personInfo = (PersonSummaryVO)NBSContext.retrieve(session, "DSPatientPersonUID");
      //String sPersonUID = personInfo.getPersonUid().toString();

      TreatmentProxyVO proxy = null;
      Long UID = null;

      // ContextAction = Cancel
      if (contextAction.equalsIgnoreCase(NBSConstantUtil.CANCEL)) {
         ActionForward af = mapping.findForward(contextAction);
         ActionForward forwardNew = new ActionForward();
         StringBuffer strURL = new StringBuffer(af.getPath());
         NBSContext.store(session, "DSFileTab", "2");
         strURL.append("&ContextAction=" + contextAction);
         forwardNew.setPath(strURL.toString());
         forwardNew.setRedirect(true);
         return forwardNew;
      }

      if (contextAction.equalsIgnoreCase(NBSConstantUtil.SUBMIT)) {

         logger.debug("Handling create action");
         NBSContext.store(session, "DSFileTab", "3");
         String coinfStr = form.getCoinfectionAssocList();
         ArrayList<Long> invList = null;
         if (coinfStr != null && !coinfStr.isEmpty()) {
        	 invList = parseCoinfectionList(coinfStr);
         }
         //##!!VOTester.createReport(form.getProxy(), "obs-create-store-pre");
         proxy = createHandler(form, nbsSecurityObj, session, request, response);
         //##!!VOTester.createReport(proxy, "obs-create-store-post");
         if (proxy != null) {

            Long sInvestigationUID = new Long(NBSContext.retrieve(session,
                "DSInvestigationUID").toString());
            logger.debug("sInvestigationUID is :" + sInvestigationUID.toString());
            if (invList==null) {
            	invList = new ArrayList<Long>();
            	invList.add(sInvestigationUID);
            }
            try {
               UID = this.sendProxyToTreatForManageEJB(proxy, invList,
                   session);
               //store the treatment uid into objectstore
               NBSContext.store(session, "DSTreatmentUID", UID);
            }
            catch (NEDSSAppConcurrentDataException e) {
               logger.fatal(
                   "ERROR , The data has been modified by another user, please recheck! ",
                   e);
               return mapping.findForward("dataerror");
            }
            catch (java.rmi.RemoteException e) {
               logger.fatal(
                   "ERROR , The remoteException has been thrown, please recheck! ",
                   e);
               throw new ServletException("ERROR , The remoteException has been thrown, please recheck! ");

            }
            catch (Exception e) {
               logger.fatal("ERROR , The error has occured, please recheck! ",
                            e);
               throw new ServletException("ERROR , The error has occured, please recheck! ");

            }
            //System.out.println("new TreatmentUID is :" + UID);
            ActionForward af = mapping.findForward(contextAction);
            ActionForward forwardNew = new ActionForward();
            StringBuffer strURL = new StringBuffer(af.getPath());
            strURL.append("&ContextAction=" + contextAction);
            forwardNew.setPath(strURL.toString());
            forwardNew.setRedirect(true);

            // set up the ldf collection
            return forwardNew;
         }
      }
      throw new ServletException();
   }


/**
        * This private method is used as a handler for creating the treatment object.
    * It prepares the TreatmentProxyVO object with certain default values.
    * @param form TreatmentForm object
    * @param nbsSecurityObj NBSSecurityObj  object
    * @param request HttpServletRequest  object
    * @param response HttpServletResponse  object
    * @return TreatmentProxyVO object
    */
   private TreatmentProxyVO createHandler(TreatmentForm form,
                                          NBSSecurityObj nbsSecurityObj,
                                          HttpSession session,
                                          HttpServletRequest request,
                                          HttpServletResponse response) {
      TreatmentProxyVO proxy = form.getProxy();
      proxy.setItNew(true);
      // proxy.getTheTreatmentVO().setItNew(true);
      //proxy.getTheTreatmentVO().getTheTreatmentDT().setItNew(true);
      TreatmentVO treatmentVO = null;
      Long uid = (Long) NBSContext.retrieve(session, "DSPatientPersonUID");

      int tempID = -1;

      //To ensure that the form has TreatmentVO value before it sets it
      if (form.getTreatmentVO() != null) {
         treatmentVO = form.getTreatmentVO();
         treatmentVO.setItNew(true);
         treatmentVO.getTheTreatmentDT().setTreatmentUid(new Long(tempID--));

         //********************MAY CHANGE -- THIS IS DONE TO ACCOMODATE FOR SECURITY INTEGERATION FOR BUILD C ***************
          treatmentVO.getTheTreatmentDT().setSharedInd(
             ProgramAreaJurisdictionUtil.SHAREDISTRUE);
         treatmentVO.getTheTreatmentDT().setVersionCtrlNbr(new Integer(1));
         treatmentVO.getTheTreatmentDT().setItNew(true);
         treatmentVO.getTheTreatmentDT().setAddTime(new Timestamp(new Date().getTime()));
         treatmentVO.getTreatmentAdministeredDT_s(0).setItNew(true);
         //need to get program area from object store

         treatmentVO.getTheTreatmentDT().setProgAreaCd( (String) NBSContext.
             retrieve(session, NBSConstantUtil.DSProgramArea));

		 //Fix for civ8100
			logger.debug("\n\n Effective from time: " + treatmentVO.getTheTreatmentDT().getActivityFromTime() + "\n\n");
			treatmentVO.getTreatmentAdministeredDT_s(0).setEffectiveFromTime(
				treatmentVO.getTheTreatmentDT().getActivityFromTime());

         //set the predefined fields
         if (treatmentVO.getTheTreatmentDT().getCd() != null &&
             !treatmentVO.getTheTreatmentDT().getCd().equals("OTH")) {
            CachedDropDownValues cachedDropDownValues = new
                CachedDropDownValues();
            PreDefinedTreatmentDT preDefinedTreatmentDT = null;
            preDefinedTreatmentDT = cachedDropDownValues.
                                    getPreDefinedTreatmentDT(treatmentVO.
                getTheTreatmentDT().getCd());
            treatmentVO.getTreatmentAdministeredDT_s(0).setCd(treatmentVO.
                getTheTreatmentDT().getCd());
            treatmentVO.getTreatmentAdministeredDT_s(0).setDoseQty(
                preDefinedTreatmentDT.getDoseQty());
            treatmentVO.getTreatmentAdministeredDT_s(0).setDoseQtyUnitCd(
                preDefinedTreatmentDT.getDoseQtyUnitCd());
            treatmentVO.getTreatmentAdministeredDT_s(0).setRouteCd(
                preDefinedTreatmentDT.getRouteCd());
            treatmentVO.getTreatmentAdministeredDT_s(0).setIntervalCd(
                preDefinedTreatmentDT.getIntervalCd());
            treatmentVO.getTreatmentAdministeredDT_s(0).setEffectiveDurationAmt(
                preDefinedTreatmentDT.getDurationAmt());
            treatmentVO.getTreatmentAdministeredDT_s(0).
                setEffectiveDurationUnitCd(preDefinedTreatmentDT.
                                           getDurationUnitCd());
            treatmentVO.getTheTreatmentDT().setCdDescTxt(treatmentVO.getTheTreatmentDT().getCd()==null? "" : " "+
                   cachedDropDownValues.getCachedTreatmentDescription(treatmentVO.getTheTreatmentDT().getCd()));
               treatmentVO.getTheTreatmentDT().setCdSystemCd(preDefinedTreatmentDT.getCodeSystemCd());
               treatmentVO.getTheTreatmentDT().setCdSystemDescTxt(preDefinedTreatmentDT.getCdSystemDescTxt());
            treatmentVO.getTheTreatmentDT().setClassCd("TA");
            }
            else
            {
              treatmentVO.getTheTreatmentDT().setCdSystemCd("NBS");
              treatmentVO.getTheTreatmentDT().setCdSystemDescTxt("NEDSS Base System");
            }

         if (treatmentVO != null) {
            proxy.setTheTreatmentVO(treatmentVO);
         }
      }
      /*
            OrganizationVO treatmentOrgGiverVO = null;
            PersonVO patient = null;
            PersonVO treatmentGiverVO = null;
            PersonVO subjectVO = new PersonVO();
            Collection<Object>  obsColl = new ArrayList<Object> ();
            Collection<Object>  patientColl = new ArrayList<Object> ();
            if (form.getPatient() != null) {
               System.out.println("if (form.getPatient() != null) { !!!!!!!!!!!!!!!!!!!!!!1111");
               patient = form.getPatient();
               patient.setItNew(true);
               patient.getThePersonDT().setItNew(true);
               //patient.getThePersonDT().setPersonParentUid(uid);
               patient.getThePersonDT().setPersonUid(uid);
               patient.getThePersonDT().setCd("PAT");
           patient.getThePersonDT().setAddTime(new Timestamp(new Date().getTime()));
               //patient.setLastChgTime(new Timestamp(new Date().getTime()));
           patient.getThePersonDT().setRecordStatusTime(new Timestamp(new Date().
                   getTime()));
               patient.getThePersonDT().setStatusTime(new Timestamp(new Date().
                   getTime()));
           patient.getThePersonDT().setStatusCd(NEDSSConstants.STATUS_ACTIVE);
               //patient.getThePersonDT().setAgeReported(request.getParameter("patientAgeReported"));
               //patient.getThePersonDT().setAgeReportedUnitCd(request.getParameter("patientAgeReportedUnitCd"));
               //patient.getThePersonDT().setAsOfDateEthnicity_s(request.getParameter("generalAsOfDate") == null ? "" :
               //(String) request.getParameter("generalAsOfDate"));
               // PersonSubmit personSubmit = new PersonSubmit();
               PersonUtil.setAddressesForCreate(patient, request);
               PersonUtil.setEthnicityForCreate(patient, request);
               PersonUtil.setRaceForCreate(patient, request);
               PersonUtil.setTelephones(patient, request);
               PersonUtil.setIds(patient, request);
               PersonUtil.setNames(patient, request);
               //this.setPhysicalLocations(patient);
               patientColl.add(patient);
               if (patientColl.size() > 0) {
                  proxy.setThePersonVOCollection(patientColl);
               }
            }
       */
      Collection<Object>  treatmentOrgColl = new ArrayList<Object> ();

      this.setAssociations(proxy, form, request, session);
      // use the new API to retrieve custom field collection
      // to handle multiselect fields (xz 01/11/2005)
		Collection<Object>  coll = extractLdfDataCollection(form, request);
      ArrayList<Object> list = new ArrayList<Object> ();
      if (coll!=null){
       Iterator<Object>  it = coll.iterator();
        while (it.hasNext()) {
          StateDefinedFieldDataDT stateDT = (StateDefinedFieldDataDT) it.next();
          if (stateDT != null && stateDT.getBusinessObjNm() != null) {
            if (stateDT.getBusinessObjNm().equalsIgnoreCase(NEDSSConstants.
                TREATMENT_LDF) && stateDT.getLdfValue()!= null && !stateDT.getLdfValue().trim().equals("") ) {
              stateDT.setItDirty(false);
              list.add(stateDT);
            }
          }
        }

      }

      proxy.setTheStateDefinedFieldDataDTCollection(list);




      return proxy;
   }

   /**
    * This private method is used to aasociate one or more Investigations with a treatment
    * @param proxy TreatmentProxyVO object
    * @param invList ArrayList of Long object representing the Investigation(s)
    * @param session HttpSession object
    * @return : Long object
    */
   private Long sendProxyToTreatForManageEJB(TreatmentProxyVO proxy,
                                             ArrayList<Long> invList,
                                             HttpSession session) throws
       NEDSSAppConcurrentDataException, java.rmi.RemoteException,
       javax.ejb.EJBException, Exception {
      /**
       * Call the mainsessioncommand
       */
      MainSessionCommand msCommand = null;
      // try {
      String sBeanJndiName = JNDINames.TREATMENT_PROXY_EJB;
      String sMethod = "setTreatmentProxyWithAutoAssoc";
      logger.debug(
          "sending proxy to treatment for setTreatmentProxyWithAutoAssoc, via mainsession");

      /**
       * Output TreatmentProxyVO for debugging
       */
      Object[] oParams = {proxy, NEDSSConstants.INVESTIGATION,
                            invList};

      // if(msCommand == null)
      // {
      MainSessionHolder holder = new MainSessionHolder();
      msCommand = holder.getMainSessionCommand(session);
      // }
      ArrayList<?> resultUIDArr = new ArrayList<Object> ();
      resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
      if ( (resultUIDArr != null) && (resultUIDArr.size() > 0)) {
         logger.debug("Store treatment through investigation worked!!! treatmentUID = " +
             resultUIDArr.get(0));
         Long result = (Long) resultUIDArr.get(0);

         return result;
      }
      else {
         return null;
      }
   }

   /**
    * This private method is used to create associations for a given TreatmentProxyVO object during
    * create treatment process
    * @param treatmentProxyVO TreatmentProxyVO object
    * @param form TreatmentForm object
    * @param request HttpServletRequest  object
    * @param session HttpSession object
    */
   private void setAssociations(TreatmentProxyVO treatmentProxyVO,
                                TreatmentForm form, HttpServletRequest request,
                                HttpSession session) {

      TreatmentVO treatmentVO = treatmentProxyVO.getTheTreatmentVO();

      Long mprUid = (Long) NBSContext.retrieve(session, "DSPatientPersonUID");
      //System.out.println("The uid is :" + mprUid);

      //////////////////////////////Creates Participation////////////////////////
      Collection<Object>  partsColl = new ArrayList<Object> ();

      //PAR112
      //Create participation between treatment and the subject(person)

      if (treatmentVO != null && mprUid != null) {

         ParticipationDT subjectPart = new ParticipationDT();
         subjectPart.setItNew(true);
         subjectPart.setActUid(treatmentVO.getTheTreatmentDT().getTreatmentUid());
         subjectPart.setSubjectEntityUid(mprUid);
         subjectPart.setStatusCd(NEDSSConstants.PARTICIPATION_STATUS_CD);
         subjectPart.setTypeCd(NEDSSConstants.SUBJECT_OF_TREATMENT);
         subjectPart.setRecordStatusCd(NEDSSConstants.ACTIVE);
         subjectPart.setFromTime(treatmentVO.getTheTreatmentDT().
                                 getActivityFromTime());
         subjectPart.setSubjectClassCd(NEDSSConstants.PERSON_CLASS_CODE); //needs to be verified
         subjectPart.setActClassCd(NEDSSConstants.TREATMENT_CLASS_CODE);
         partsColl.add(subjectPart);
      }

      //PAR114
      //creates the participation between treatment and treatmentGiver(Person)
      //System.out.println(" Prov-entity.entityProvUID = " +(String) request.getParameter("Prov-entity.entityProvUID"));
      String treatmentGiverUID = (String) request.getParameter(
          "Prov-entity.entityProvUID");
      if (treatmentVO != null && treatmentGiverUID != null &&
          !treatmentGiverUID.trim().equals("")) {
         ParticipationDT personPart = new ParticipationDT();
         personPart.setItNew(true);
         personPart.setStatusCd(NEDSSConstants.PARTICIPATION_STATUS_CD);
         personPart.setActUid(treatmentVO.getTheTreatmentDT().getTreatmentUid());
         personPart.setTypeCd(NEDSSConstants.PROVIDER_OF_TREATMENT);
         personPart.setRecordStatusCd(NEDSSConstants.ACTIVE);
         personPart.setFromTime(treatmentVO.getTheTreatmentDT().
                                getActivityFromTime());
         personPart.setSubjectClassCd(NEDSSConstants.PERSON_CLASS_CODE);
         personPart.setSubjectEntityUid(new Long(treatmentGiverUID.trim()));
         personPart.setActClassCd(NEDSSConstants.TREATMENT_CLASS_CODE);
         partsColl.add(personPart);
      }

      //PAR115
      //creates participation between treatment and treatmentOrganization giver(Organization)
      //System.out.println(" Org-ReportingOrganizationUID = " +(String)request.getParameter("Org-ReportingOrganizationUID"));
      String treatmentOrgGiverUID = (String) request.getParameter(
          "Org-ReportingOrganizationUID");
      if (treatmentVO != null && treatmentOrgGiverUID != null &&
          !treatmentOrgGiverUID.trim().equals("")) {
         ParticipationDT orgGiverPart = new ParticipationDT();
         orgGiverPart.setItNew(true);

         orgGiverPart.setSubjectEntityUid(new Long(treatmentOrgGiverUID.trim()));
         orgGiverPart.setStatusCd(NEDSSConstants.PARTICIPATION_STATUS_CD);
         orgGiverPart.setRecordStatusCd(NEDSSConstants.ACTIVE);
         orgGiverPart.setFromTime(treatmentVO.getTheTreatmentDT().
                                  getActivityFromTime());
         orgGiverPart.setSubjectClassCd(NEDSSConstants.ORGANIZATION_CLASS_CODE);
         orgGiverPart.setActUid(treatmentVO.getTheTreatmentDT().getTreatmentUid());
         orgGiverPart.setTypeCd(NEDSSConstants.RPT_FACILITY_OF_TREATMENT);
         orgGiverPart.setActClassCd(NEDSSConstants.TREATMENT_CLASS_CODE);
         partsColl.add(orgGiverPart);
      }
      if (partsColl.size() > 0) {
         treatmentProxyVO.setTheParticipationDTCollection(partsColl);
      }
      else {
         treatmentProxyVO.setTheParticipationDTCollection(null);

         //////////////////////////creates Roles//////////////////////////////
      }

      /** @todo clean method call */
      Collection<Object>  actColl = new ArrayList<Object> ();

      logger.debug("Version control number:" +
                   treatmentProxyVO.getTheTreatmentVO().getTheTreatmentDT().
                   getVersionCtrlNbr());
      logger.debug("Shared indicator :" +
                   treatmentProxyVO.getTheTreatmentVO().getTheTreatmentDT().
                   getSharedInd());

   }

   private ArrayList<Long> parseCoinfectionList(String coinfStr) {
	   		ArrayList<Long> invList = new ArrayList<Long>();
			StringTokenizer st = new StringTokenizer(coinfStr,":"); 
			while (st.hasMoreTokens()) { 
				String token = st.nextToken();
				try {
					Long invUid = new Long (token);
					invList.add(invUid);
				} catch (Exception e) {
					logger.error("Error parsing coinfection list " + coinfStr);
					e.printStackTrace();
				}
					
			}				
		
			return invList;
   }

}