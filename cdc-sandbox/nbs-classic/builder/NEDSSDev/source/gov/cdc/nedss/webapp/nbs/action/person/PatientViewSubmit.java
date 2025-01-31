package gov.cdc.nedss.webapp.nbs.action.person;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;

import gov.cdc.nedss.entity.person.vo.*;
import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.proxy.ejb.entityproxyejb.bean.EntityProxyHome;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.systemservice.nbscontext.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.systemservice.util.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.webapp.nbs.action.util.*;
import gov.cdc.nedss.webapp.nbs.form.person.*;

/** The PersonSubmit class is class that is accessed from the front-end so that person data can be submitted to the back end.  It is used in creating new persons into the NEDSS system.  It is also used for edits to existing persons in the system.
 */
public class PatientViewSubmit
    extends CommonAction {



   static final LogUtils logger = new LogUtils(PatientViewSubmit.class.getName());

   /** A constructor for the PersonSubmit class.
    */
   public PatientViewSubmit() {
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
      Long personUID = null;
      // are we edit or create?
      String contextAction = request.getParameter("ContextAction");
      logger.info("contextAction is: " + contextAction);
      personUID = (Long)NBSContext.retrieve(session, NEDSSConstants.DSPatientPersonUID);


      if (contextAction == null) {
         contextAction = (String) request.getAttribute("ContextAction");

         
      }

      /*******************************************
       * CANCEL ACTION
       */
      if (contextAction.equalsIgnoreCase("Cancel")) {
         NBSContext.store(session, "DSFileTab", "2");
         ErrorMessageHelper.setErrMsgToRequest(request, "ps166");
         personForm.reset();
      }

      /********************************************
       * DELETE ACTION
       */
      else if (contextAction.equalsIgnoreCase("Delete")) {
         logger.debug("Beginning the Delete Person Functionality");
         String result = null;

        try
        {
            PersonVO personVO = personForm.getPerson();
            NBSContext.store(session, NEDSSConstants.DSPatientPersonUID, personUID);
            result = sendProxyToEJBDeleteMPR(personVO, "deleteMPR", session);
         }
         catch (NEDSSAppConcurrentDataException e) {
            logger.fatal("ERROR - NEDSSAppConcurrentDataException, The data has been modified by another user, please recheck! ",
                         e);

            return mapping.findForward("dataerror");
         }

         if (result.equals("viewDelete")) {

            personForm.reset();
         }
         else if (result.equals("deleteDenied")) {
            NBSContext.store(session, NEDSSConstants.DSPatientPersonUID, personUID);
            contextAction = "DeleteDenied";
         }
         else {
            session.setAttribute("error",
                "unexpected return statement from delete person");

            throw new ServletException("unexpected return statement from delete person");
         }
      }

      /*********************************************
       * VIEW FILE
       */
      else if (contextAction.equalsIgnoreCase("ViewFile")) {
         NBSContext.store(session, "DSFileTab", "1");
      }
      request.setAttribute("ContextAction", contextAction);


      request.setAttribute("personUID", personUID);
      return mapping.findForward(contextAction);
   }

 
   private String sendProxyToEJBDeleteMPR(PersonVO personVO,
                                       String paramMethodName,
                                       HttpSession session) throws
       NEDSSAppConcurrentDataException {




      MainSessionCommand msCommand = null;

      try {

         String sBeanJndiName = JNDINames.ENTITY_PROXY_EJB;
         String sMethod = paramMethodName;

         Object[] oParams = {personVO};

         MainSessionHolder holder = new MainSessionHolder();
         msCommand = holder.getMainSessionCommand(session);

         ArrayList<?> resultArr = new ArrayList<Object> ();
         resultArr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
         logger.debug("value of resultArr1 " + resultArr);

         boolean result;
         String deleteFlag = "";

         if ( (resultArr != null) && (resultArr.size() > 0)) {
            logger.info("Delete person = " + resultArr.get(0));
            result = ( (Boolean) resultArr.get(0)).booleanValue();

            if (result) {
               deleteFlag = "viewDelete";
            }
            else {
               deleteFlag = "deleteDenied";
            }

            return deleteFlag;
         }
         else {
            deleteFlag = "error";

            return deleteFlag;
         }
      }
      catch (NEDSSAppConcurrentDataException ncde) {
         ncde.printStackTrace();
         logger.fatal(
             "Error: Could not delete record because of data concurrency.");
         throw new NEDSSAppConcurrentDataException();

      }
      catch (Exception e) {
         e.printStackTrace();

         if (session == null) {
            logger.error("Error: no session, please login");
            e.printStackTrace();

            return null;
         }

         logger.fatal("ERROR calling mainsession control", e);

         return null;
      }
   }


}
