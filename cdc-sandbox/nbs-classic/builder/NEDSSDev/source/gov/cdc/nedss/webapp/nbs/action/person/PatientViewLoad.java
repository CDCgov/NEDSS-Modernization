package gov.cdc.nedss.webapp.nbs.action.person;

/**
 * Title:        CoreDemographic
 * Description:  this class retrieves data from EJB and puts them into request object for use in the xml file
 * Copyright:    Copyright (c) 2001
 * Company:      CSC
 * @author Jay Kim
 * @version 1.0
 */
import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;

import gov.cdc.nedss.entity.person.vo.*;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.systemservice.nbscontext.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.systemservice.util.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.webapp.nbs.action.person.util.*;
import gov.cdc.nedss.webapp.nbs.form.person.*;
import gov.cdc.nedss.webapp.nbs.action.ldf.BaseLdf;

/**
 * This class is used for populating a PersonVO object that will be
 * utilized in populating a the "View Person" screen.
 */
public class PatientViewLoad
     extends BaseLdf{

   //For logging
   /**
    * An instance of the LogUtils object.
    */
   static final LogUtils logger = new LogUtils(PatientViewLoad.class.getName());

   /**
    * Constructor for the PersonLoad class.
    */
   public PatientViewLoad() {
   }

   /**
    * This is the method that gets executed first when a request
    * is made to this class.
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

      ArrayList<Object> stateList = null;

      request.getSession().removeAttribute("result");

      CompleteDemographicForm personForm = (CompleteDemographicForm) form;
      HttpSession session = request.getSession(false);

      if (session == null) {
         logger.debug("error no session");

         throw new ServletException("error no session");
      }

      NBSSecurityObj secObj = (NBSSecurityObj) session.getAttribute(
          "NBSSecurityObject");
      String contextAction = request.getParameter("ContextAction");

      if (contextAction == null) {
         contextAction = (String) request.getAttribute("ContextAction");

      }

      String sCurrentTask = NBSContext.getCurrentTask(session);
      String patientUID = request.getParameter("uid");

      if (patientUID == null) {
         patientUID = (String) request.getParameter("uid");
      }
      if (patientUID == null) {
         patientUID = NBSContext.retrieve(session, "DSPatientPersonUID").
                      toString();
      }

      TreeMap<Object,Object> tm = NBSContext.getPageContext(session, "PS172", contextAction);

      String sCurrTask = NBSContext.getCurrentTask(session);
      request.setAttribute("addButtonHref",
                           "/nbs/" + sCurrTask + ".do?ContextAction=" +
                           tm.get("Add"));
      request.setAttribute("editButtonHref",
                           "/nbs/" + sCurrTask + ".do?ContextAction=" +
                           tm.get("Edit"));
      request.setAttribute("viewFileButtonHref",
                           "/nbs/" + sCurrTask + ".do?ContextAction=" +
                           tm.get("ViewFile"));
      request.setAttribute("deleteButtonHref",
                           "/nbs/" + sCurrTask + ".do?ContextAction=" +
                           tm.get("Delete"));
      request.setAttribute("returnToSearchResultsHref",
                           "/nbs/" + sCurrTask + ".do?ContextAction=" +
                           tm.get("ReturnToSearchResults"));

      request.setAttribute("PrintPage", "/nbs/LoadViewPatient1.do?ContextAction=PrintPage");

      NBSContext.store(session, "DSFileTab", "2");

      //Link back to observation
      request.setAttribute("sCurrTask", sCurrTask);

      request.setAttribute("linkName", "Return to View Observation Lab Report");
      request.setAttribute("linkValue",
                           "/nbs/" + sCurrTask + ".do?ContextAction=" +
                           tm.get("ReturnToViewObs"));

      //check security for buttons
      personForm.reset();

      if (!patientUID.equals("")) {

         logger.info("About to getOldPersonObject");
         PersonVO person = getOldPersonObject(patientUID, personForm, session);

         if (person != null) {

            try {

               boolean bEditButton = secObj.getPermission(NBSBOLookup.PATIENT,
                   NBSOperationLookup.EDIT);
               boolean bDeleteButton = secObj.getPermission(NBSBOLookup.PATIENT,
                   NBSOperationLookup.DELETE);
               boolean bFileButton = secObj.getPermission(NBSBOLookup.PATIENT,
                   NBSOperationLookup.VIEWWORKUP);

               if (person.getThePersonDT().getRecordStatusCd() != null &&
                   person.getThePersonDT().getRecordStatusCd().trim().equals(
                   NEDSSConstants.RECORD_STATUS_LOGICAL_DELETE) ||
                   person.
                   getThePersonDT().getRecordStatusCd().trim().equals("SUPERCEDED")) {
                  bEditButton = false;
                  bDeleteButton = false;
                  bFileButton = false;
               }

               request.setAttribute("deleteButton",
                                    String.valueOf(bDeleteButton));
               request.setAttribute("editButton", String.valueOf(bEditButton));
               request.setAttribute("fileButton", String.valueOf(bFileButton));

               boolean bAddButton = secObj.getPermission(NBSBOLookup.PATIENT,
                   NBSOperationLookup.ADD);
               request.setAttribute("addButton", String.valueOf(bAddButton));
            }
            catch (Exception e) {
               e.printStackTrace();
               logger.error("getting permissions for the buttons failed");
            }

            stateList = new ArrayList<Object> ();
           try {
            PersonUtil.convertPersonToRequestObj(person, request, contextAction,
                                                 stateList);
           }catch(Exception ex) {
   			throw new ServletException(ex.getMessage());
   		}
            PersonUtil.prepareAddressCounties(request, stateList);


         }
         else {

        	 throw new ServletException();
         }

      }
      else {
         logger.error("!!!!!!!!!!!!    no person UID");

         throw new ServletException("!!!!!!!!!!!!    no person UID");
      }

      if (patientUID != null) {
         NBSContext.store(session, "DSPatientPersonUID", new Long(patientUID));

      }
      personForm.getPerson().setItDirty(true);
      createXSP(NEDSSConstants.PATIENT_LDF, personForm.getPerson().getThePersonDT().getPersonUid() , personForm.getPerson(), null, request ) ;

      if(contextAction.equalsIgnoreCase("PrintPage")) {
        return  new ActionForward("/person/view_patient_print");
      }

      return mapping.findForward("XSP");


   }

   /**
    * This method is to get the old PersonVO object
    * for a person.
    *
    * @param patientUID String
    * @param form CompleteDemographicForm
    * @param session HttpSession
    * @return PersonVO
    */
   private PersonVO getOldPersonObject(String patientUID,
                                       CompleteDemographicForm form,
                                       HttpSession session) {

      PersonVO person = null;
      MainSessionCommand msCommand = null;

      if (patientUID != null) {

         try {

            Long UID = new Long(patientUID.trim());
            String sBeanJndiName = JNDINames.EntityControllerEJB;
            String sMethod = "getMPR";
            Object[] oParams = new Object[] {UID};

    
            MainSessionHolder holder = new MainSessionHolder();
            msCommand = holder.getMainSessionCommand(session);

            
            ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod,
                oParams);
            person = (PersonVO) arr.get(0);
            form.setPerson(person);
         }
         catch (NumberFormatException e) {
            logger.error("Error: no person UID");

            return null;
         }
         catch (Exception ex) {

            if (session == null) {
               logger.error("Error: no session, please login");
            }

            logger.fatal("getOldPersonObject: ", ex);

            return null;
         }
         finally {
            msCommand = null;
         }
      }
      else { // if the request didn't povide the PHCuid, look in form
         person = form.getPerson();
      }

      return person;
   }

}
