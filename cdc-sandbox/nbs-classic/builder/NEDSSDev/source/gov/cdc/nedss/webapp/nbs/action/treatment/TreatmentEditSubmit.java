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
import gov.cdc.nedss.ldf.dt.StateDefinedFieldDataDT;

/**
 * Title:        TreatmentSubmit
 * Description:  this class retrieves data from EJB and puts them into request object for use in the xml file
 * Copyright:    Copyright (c) 2001-2002
 * Company:      CSC
 * @version 1.0
 */

public class TreatmentEditSubmit
    extends CommonAction {
   static final LogUtils logger = new LogUtils(TreatmentEditSubmit.class.
                                               getName());

   public TreatmentEditSubmit() {
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
      Long sPersonUID = (Long) NBSContext.retrieve(session,
          "DSPatientPersonUID");
      request.setAttribute("personUID", sPersonUID);
      String strUID = request.getParameter("treatmentUID");

      //need to process any parameters that come inside here - Chenchen
      Enumeration<?> params = request.getParameterNames();
      while (params.hasMoreElements()) {
         String sParamName = (String) params.nextElement();
         request.setAttribute(sParamName, request.getParameter(sParamName));
      }

      TreatmentProxyVO proxy = null;
      Long UID = null;

      // context action check - Chenchen
      String sCurrentTask = NBSContext.getCurrentTask(session);
      logger.debug("TreatmentSubmit:  ContextAction = " + contextAction +
                   " \nwith CurrentTask = " + sCurrentTask);
      if (sCurrentTask == null) {
         session.setAttribute("error",
             "current task is null, required for treatment submit");
         throw new ServletException("current task is null, required for treatment submit");
      }

      // ContextAction = Cancel
      if (contextAction.equalsIgnoreCase(NBSConstantUtil.CANCEL)) {
         ActionForward af = mapping.findForward(contextAction);
         ActionForward forwardNew = new ActionForward();
         StringBuffer strURL = new StringBuffer(af.getPath());
         NBSContext.store(session, "DSFileTab", "2");
         if (sCurrentTask.startsWith("EditTreatment")) {
            //NBSContext.store(session,"DSTreatmentUID", strUID);
            strURL.append("?ContextAction=" + contextAction);
         }
         else {
            session.setAttribute("error",
                                 "ContextAction(Cancel): Invalid current task.");
            throw new ServletException("ContextAction(Cancel): Invalid current task.");
         }
         forwardNew.setPath(strURL.toString());
         forwardNew.setRedirect(true);
         return forwardNew;
      }

      if (contextAction.equalsIgnoreCase(NBSConstantUtil.SUBMIT)) {

         //##!!VOTester.createReport(form.getProxy(), "obs-edit-store-pre");
         //logger.debug("The org UID in edit Handler is in 292:" +
         //form.getOrganizationManufacturer().
         //getTheOrganizationDT().getOrganizationUid());
         proxy = editHandler(form, nbsSecurityObj, session, request, response);
         //##!!VOTester.createReport(proxy, "obs-edit-store-post");

         // use the new API to retrieve custom field collection
         // to handle multiselect fields (xz 01/11/2005)
		Collection<Object>  coll = extractLdfDataCollection(form, request);
                 ArrayList<Object> list = new ArrayList<Object> ();
                 if(coll!= null)
                 {
                  Iterator<Object>  it = coll.iterator();
                   while (it.hasNext()) {
                     StateDefinedFieldDataDT stateDT = (StateDefinedFieldDataDT) it.next();
                     if (stateDT != null && stateDT.getBusinessObjNm() != null) {
                       if (stateDT.getBusinessObjNm().equalsIgnoreCase(NEDSSConstants.
                           TREATMENT_LDF) && stateDT.getLdfValue()!= null && !stateDT.getLdfValue().trim().equals("")) {
                         stateDT.setItDirty(true);
                         list.add(stateDT);
                       }
                     }
                   }
                 }
                  proxy.setTheStateDefinedFieldDataDTCollection(list);


         if (proxy != null) {
            try {
               UID = sendProxyToTreatmentEJB(proxy, session);
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

            form.reset();
            logger.debug(
                "inside the proxy update called to EJB method Got UID :" + UID);
         }
         if (proxy == null || UID == null) {
            logger.error("....Error building proxy,invalidating session.");
            throw new ServletException("....Error building proxy,invalidating session.");
         }
         logger.info("Done storing treatment. UID= " + UID);
         if (UID != null) {
            //NBSContext.store(session,"DSTreatmentUID", UID);
            ActionForward af = mapping.findForward(contextAction);
            ActionForward forwardNew = new ActionForward();
            StringBuffer strURL = new StringBuffer(af.getPath());
            strURL.append("?ContextAction=" + contextAction);
            forwardNew.setPath(strURL.toString());
            forwardNew.setRedirect(true);



            return forwardNew;
         }

      }
      throw new ServletException();
   }

   /**
        * This private method is used as a handler for editing the treatment object.
    * It prepares the TreatmentProxyVO object with certain default values.
    * @param form TreatmentForm object
    * @param nbsSecurityObj NBSSecurityObj  object
    * @param request HttpServletRequest  object
    * @param response HttpServletResponse  object
    * @return TreatmentProxyVO object
    */
   private TreatmentProxyVO editHandler(TreatmentForm form,
                                        NBSSecurityObj nbsSecurityObj,
                                        HttpSession session,
                                        HttpServletRequest request,
                                        HttpServletResponse response) {



      TreatmentProxyVO proxy = form.getProxy();

      proxy.setItDirty(true);
      proxy.getTheTreatmentVO().setItDirty(true);
      proxy.getTheTreatmentVO().getTheTreatmentDT().setItDirty(true);
      boolean flag = false;
      Collection<Object>  patientColl = new ArrayList<Object> ();
      NEDSSVOUtils nvu = NEDSSVOUtils.getInstance();


      TreatmentVO treatmentVO = form.getTreatmentVO();
      //MaterialVO mvo = proxy.getTheMaterialVO();
      if (treatmentVO != null) {
         treatmentVO.setItNew(false);
         treatmentVO.setItDirty(true);
         treatmentVO.getTreatmentAdministeredDT_s(0).setItDirty(true);
         treatmentVO.getTreatmentAdministeredDT_s(0).setItNew(false);

		 //Fix for civ8100
		 treatmentVO.getTreatmentAdministeredDT_s(0).setEffectiveFromTime(
		 treatmentVO.getTheTreatmentDT().getActivityFromTime());

         //set the predefined fields
         CachedDropDownValues cachedDropDownValues = new CachedDropDownValues();
         PreDefinedTreatmentDT preDefinedTreatmentDT = null;
         if(treatmentVO.getTheTreatmentDT().getCd()!=null && !treatmentVO.getTheTreatmentDT().getCd().equals("OTH")){
            preDefinedTreatmentDT = cachedDropDownValues.getPreDefinedTreatmentDT(treatmentVO.getTheTreatmentDT().getCd());
            treatmentVO.getTreatmentAdministeredDT_s(0).setCd(treatmentVO.getTheTreatmentDT().getCd());
            treatmentVO.getTreatmentAdministeredDT_s(0).setDoseQty(preDefinedTreatmentDT.getDoseQty());
            treatmentVO.getTreatmentAdministeredDT_s(0).setDoseQtyUnitCd(preDefinedTreatmentDT.getDoseQtyUnitCd());
            treatmentVO.getTreatmentAdministeredDT_s(0).setRouteCd(preDefinedTreatmentDT.getRouteCd());
            treatmentVO.getTreatmentAdministeredDT_s(0).setIntervalCd(preDefinedTreatmentDT.getIntervalCd());
            treatmentVO.getTreatmentAdministeredDT_s(0).setEffectiveDurationAmt(preDefinedTreatmentDT.getDurationAmt());
            treatmentVO.getTreatmentAdministeredDT_s(0).setEffectiveDurationUnitCd(preDefinedTreatmentDT.getDurationUnitCd());
            treatmentVO.getTheTreatmentDT().setCdDescTxt(treatmentVO.getTheTreatmentDT().getCd()==null? "" : " "+
                   cachedDropDownValues.getCachedTreatmentDescription(treatmentVO.getTheTreatmentDT().getCd()));
            treatmentVO.getTheTreatmentDT().setCdSystemCd(preDefinedTreatmentDT.getCodeSystemCd());
            treatmentVO.getTheTreatmentDT().setCdSystemDescTxt(preDefinedTreatmentDT.getCdSystemDescTxt());
            treatmentVO.getTheTreatmentDT().setClassCd("TA");
         }
         else
         {
           //treatmentVO.getTheTreatmentDT().setCd(cachedDropDownValues.getCachedTreatmentDescription(treatmentVO.getTheTreatmentDT().getCd()));
           //System.out.println("treatmentVO.getTheTreatmentDT().setCd    === " + treatmentVO.getTheTreatmentDT().getCd() );
           treatmentVO.getTheTreatmentDT().setCdSystemCd("NBS");
           treatmentVO.getTheTreatmentDT().setCdSystemDescTxt("NEDSS Base System");
           treatmentVO.getTheTreatmentDT().setClassCd(null);
         }
         if (treatmentVO != null) {
            proxy.setTheTreatmentVO(treatmentVO);
         }
      }

      /** @todo clean these method calls */
      OrganizationVO treatmentOrgGiverVO = null;
      Collection<Object>  treatmentOrgColl = new ArrayList<Object> ();
      setAssociationsForEdit(proxy, form, request, session);
      return proxy;
   }



   /**
    * This private method is used to create or update a treatment
    * @param proxy TreatmentProxyVO object
    * @param session HttpSession object
    * @throws NEDSSAppConcurrentDataException
    * @throws RemoteException
    * @throws EJBException
    * @throws Exception
    * @return : Long object representing the treatment
    */
   private Long sendProxyToTreatmentEJB(TreatmentProxyVO proxy,
                                        HttpSession session) throws
       NEDSSAppConcurrentDataException, java.rmi.RemoteException,
       javax.ejb.EJBException, Exception {
      /**
       * Call the mainsessioncommand
       */
      MainSessionCommand msCommand = null;
      // try {
      String sBeanJndiName = JNDINames.TREATMENT_PROXY_EJB;
      String sMethod = "setTreatmentProxy";
      logger.debug("sending proxy to treatment, via mainsession");

      /**
       * Output TreatmentProxyVO for debugging
       */Object[] oParams = {proxy};

      // if(msCommand == null)
      // {
      MainSessionHolder holder = new MainSessionHolder();
      msCommand = holder.getMainSessionCommand(session);
      // }
      ArrayList<?> resultUIDArr = new ArrayList<Object> ();
      resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
      if ( (resultUIDArr != null) && (resultUIDArr.size() > 0)) {
         logger.info("store treatment worked!!! treatmentUID = " +
                     resultUIDArr.get(0));
         Long result = (Long) resultUIDArr.get(0);
         return result;
      }
      else {
         return null;
      }
   }



   /**
    * This private method is used to associations for a given treatmentProxyVO object during
    * edit treatment process
    * @param treatmentProxyVO TreatmentProxyVO object
    * @param form TreatmentForm object
    * @param request HttpServletRequest  object
    * @param session HttpSession object
    */
   private void setAssociationsForEdit(TreatmentProxyVO treatmentProxyVO,
                                       TreatmentForm form,
                                       HttpServletRequest request,
                                       HttpSession session) {
      //MaterialVO materialVO = treatmentProxyVO.getTheMaterialVO();

      TreatmentVO treatmentVO = treatmentProxyVO.getTheTreatmentVO();


      //////////////////////////////Update Participation////////////////////////
      Collection<Object>  partsColl = new ArrayList<Object> ();
      TreatmentProxyVO proxy = form.getProxy();

      //PAR114
      //creates the participation between treatment and treatmentGiver(Person)

      String treatmentGiverUID = (String) request.getParameter(
          "Prov-entity.entityProvUID");

      Long treatmentGiverUIDOld = form.getTreatmentGiver().getThePersonDT().
                                  getPersonUid();

      if ( (treatmentGiverUID != null || !treatmentGiverUID.trim().equals("")) &&
          treatmentGiverUIDOld != null &&
          !treatmentGiverUID.equals(treatmentGiverUIDOld.toString())) {

         ParticipationDT participationDTOld = new ParticipationDT();
         participationDTOld.setSubjectEntityUid(treatmentGiverUIDOld);
         participationDTOld.setActUid(treatmentVO.getTheTreatmentDT().
                                      getTreatmentUid());
         participationDTOld.setTypeCd(NEDSSConstants.PROVIDER_OF_TREATMENT);
         participationDTOld.setItDelete(true);
         participationDTOld.setItDirty(false);
         partsColl.add(participationDTOld);

      }

      if (treatmentGiverUIDOld != null && treatmentGiverUID == null &&
          treatmentGiverUID.trim().equals("")) {
         logger.debug("\n\n\n2treatmentGiverUIDOld:" + treatmentGiverUIDOld);
         logger.debug("2treatmentGiverUID:" + treatmentGiverUID);
         ParticipationDT participationDTOld = new ParticipationDT();
         participationDTOld.setSubjectEntityUid(treatmentGiverUIDOld);
         participationDTOld.setActUid(treatmentVO.getTheTreatmentDT().
                                      getTreatmentUid());
         participationDTOld.setTypeCd(NEDSSConstants.PROVIDER_OF_TREATMENT);
         participationDTOld.setItDelete(true);
         participationDTOld.setItDirty(false);
         partsColl.add(participationDTOld);
      }
      if (treatmentVO != null && treatmentGiverUID != null &&
          !treatmentGiverUID.trim().equals("") &&
          (treatmentGiverUIDOld == null ||
           !treatmentGiverUID.equals(treatmentGiverUIDOld.toString()))) {
         logger.debug("\n\n\n3\n\n");
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

      String treatmentOrgGiverUID = (String) request.getParameter(
          "Org-ReportingOrganizationUID");

      Long treatmentOrgGiverUIDOld = form.getOrganizationTreatmentGiver().
                                     getTheOrganizationDT().getOrganizationUid();



      //sets the case for new!! Thus deletes the old record
      if ( (treatmentOrgGiverUID != null ||
            !treatmentOrgGiverUID.trim().equals("")) && treatmentOrgGiverUIDOld != null &&
          !treatmentOrgGiverUID.equals(treatmentOrgGiverUIDOld.toString())) {


         ParticipationDT pDT = new ParticipationDT();
         pDT.setSubjectEntityUid(treatmentOrgGiverUIDOld);
         pDT.setActUid(treatmentVO.getTheTreatmentDT().getTreatmentUid());
         pDT.setTypeCd(NEDSSConstants.RPT_FACILITY_OF_TREATMENT);
         pDT.setItDelete(true);
         pDT.setItDirty(false);
         partsColl.add(pDT);

      }
      //checks out in case it is to be set to delete


      if ( (treatmentOrgGiverUID == null &&
            treatmentOrgGiverUID.trim().equals("")) &&
          treatmentOrgGiverUIDOld.toString() != null) {

         ParticipationDT pDT = new ParticipationDT();
         pDT.setSubjectEntityUid(treatmentOrgGiverUIDOld);
         pDT.setActUid(treatmentVO.getTheTreatmentDT().getTreatmentUid());
         pDT.setTypeCd(NEDSSConstants.RPT_FACILITY_OF_TREATMENT);
         pDT.setItDelete(true);
         pDT.setItDirty(false);
         partsColl.add(pDT);

      }

      if (treatmentVO != null && treatmentOrgGiverUID != null &&
          !treatmentOrgGiverUID.trim().equals("") &&
          (treatmentOrgGiverUIDOld == null ||
           !treatmentOrgGiverUID.equals(treatmentOrgGiverUIDOld.toString()))) {

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

         //////////////////////////edit Roles//////////////////////////////
      }

      /** @todo clean this method call */
      Collection<Object>  rolesColl = new ArrayList<Object> ();

   }



}