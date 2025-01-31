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
import gov.cdc.nedss.webapp.nbs.form.treatment.*;

/**
 * Title:        TreatmentSubmit
 * Description:  this class retrieves data from EJB and puts them into request object for use in the xml file
 * Copyright:    Copyright (c) 2001-2002
 * Company:      CSC
 * @version 1.0
 */

public class TreatmentViewSubmit
    extends Action {
   static final LogUtils logger = new LogUtils(TreatmentViewSubmit.class.getName());

   public TreatmentViewSubmit() {
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


      //need to process any parameters that come inside here - Chenchen
      Enumeration params = request.getParameterNames();
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
      try {
        if (contextAction.equalsIgnoreCase("ReturnToFileEvents")) {
            ActionForward af = mapping.findForward(contextAction);
            ActionForward forwardNew = new ActionForward();
            StringBuffer strURL = new StringBuffer(af.getPath());
            NBSContext.store(session, "DSFileTab", "3");
            if (sCurrentTask.startsWith("ViewTreatment")) {

               strURL.append("?ContextAction=" + contextAction);
            }
            else {
               session.setAttribute("error",
                   "ContextAction(ReturnToFileEvents): Invalid current task.");
               throw new ServletException("ContextAction(ReturnToFileEvents): Invalid current task.");
            }
            forwardNew.setPath(strURL.toString());
            forwardNew.setRedirect(true);
            return forwardNew;
         }
         // ContextAction = ReturnToManageTreatments
         //else if (contextAction.equalsIgnoreCase(NBSConstantUtil.
         //                                       ReturnToManageTreatments)) {
         else if (contextAction.equalsIgnoreCase("ReturnToManageTreatments")) {
            ActionForward af = mapping.findForward(contextAction);
            ActionForward forwardNew = new ActionForward();
            StringBuffer strURL = new StringBuffer(af.getPath());
            strURL.append("?ContextAction=" + contextAction);
            if (!sCurrentTask.startsWith("ViewTreatment")) {
               session.setAttribute("error",
                   "ContextAction(ReturnToFileEvents): Invalid current task.");
               throw new ServletException("ContextAction(ReturnToFileEvents): Invalid current task.");
            }
            forwardNew.setPath(strURL.toString());
            forwardNew.setRedirect(true);
            return forwardNew;
         }
         // ContextAction = ReturnToViewInvestigation
         else if (contextAction.equalsIgnoreCase(NBSConstantUtil.
                                                 ReturnToViewInvestigation)) {
            ActionForward af = mapping.findForward(contextAction);
            ActionForward forwardNew = new ActionForward();
            StringBuffer strURL = new StringBuffer(af.getPath());
            strURL.append("?ContextAction=" + contextAction);
            if (!sCurrentTask.startsWith("ViewTreatment")) {
               session.setAttribute("error",
                   "ContextAction(ReturnToFileTreatments): Invalid current task.");
               throw new ServletException("ContextAction(ReturnToFileTreatments): Invalid current task.");
            }
            forwardNew.setPath(strURL.toString());
            forwardNew.setRedirect(true);
            return forwardNew;
         }

         // ContextAction = Delete
         else if (contextAction.equalsIgnoreCase(NBSConstantUtil.DELETE)) {
            NBSContext.store(session, "DSFileTab", "2");
            //##!!VOTester.createReport(form.getProxy(), "obs-delete-store-pre");
            Long treatmentUID = (request.getParameter("treatmentUID") == null ? null :
                                 new
                                 Long(request.getParameter("treatmentUID").trim()));
			if (treatmentUID == null) {
				String strUID = (String) NBSContext.retrieve(session, "DSTreatmentUID");
				treatmentUID = new Long(strUID);
			}
            logger.debug("treatmentUID in Delete is :" + treatmentUID);
            String result = deleteHandler(treatmentUID, nbsSecurityObj, session,
                                          request, response);
            //##!!VOTester.createReport(form.getProxy(), "obs-delete-store-post");
            if (result.equals("viewDelete")) {
               logger.debug("TreatmentSubmit: viewDelete");

                  ActionForward af = mapping.findForward(contextAction);
                  ActionForward forwardNew = new ActionForward();
                  StringBuffer strURL = new StringBuffer(af.getPath());
                  if(strURL.indexOf(NEDSSConstants.ManageEvents)>0){
                	  strURL.append("&ContextAction=" + contextAction);
                   }
                  else
                	  strURL.append("?ContextAction=" + contextAction);
                  forwardNew.setPath(strURL.toString());
                  forwardNew.setRedirect(true);
                  return forwardNew;

            }
            else if (result.equals("deleteDenied")) {
               logger.debug("TreatmentSubmit: deleteDenied");
               //NBSContext.store(session,"DSTreatmentUID", treatmentUID);

               ActionForward af = mapping.findForward("DeleteDenied");
               ActionForward forwardNew = new ActionForward();
               StringBuffer strURL = new StringBuffer(af.getPath());
               String strUID = (String) NBSContext.retrieve(session, "DSTreatmentUID");
               if(strURL.indexOf(NEDSSConstants.ManageEvents)>0){
            	   strURL.append("&ContextAction=DeleteDenied&treatmentUID=").
            	   	append(strUID);
               
            }else
               strURL.append("?ContextAction=DeleteDenied&treatmentUID=").
                   append(strUID);
               forwardNew.setPath(strURL.toString());
               forwardNew.setRedirect(true);
               return forwardNew;
            }
            else {
            	throw new ServletException();
            }
         }


      }
      catch (NEDSSAppConcurrentDataException e) {
         logger.fatal(
             "ERROR , The data has been modified by another user, please recheck! ",
             e);
         return mapping.findForward("dataerror");
      }
      catch (java.rmi.RemoteException e) {
         logger.fatal(
             "ERROR , The remoteException has been thrown, please recheck! ", e);
         throw new ServletException("ERROR , The remoteException has been thrown, please recheck! ");

      }
      catch (Exception e) {
         logger.fatal("ERROR , The error has occured, please recheck! ", e);
         throw new ServletException("ERROR , The error has occured, please recheck! ");
      }
      return mapping.findForward(contextAction);
   }



   /**
        * This private method is used as a handler for deleting the treatment object.
    * It logically deletes the TreatmentProxyVO object from the database.
    * @param UID Long value representing the Treatment object
    * @param nbsSecurityObj NBSSecurityObj  object
    * @param request HttpServletRequest  object
    * @param response HttpServletResponse  object
    * @throws NEDSSAppConcurrentDataException
    * @throws RemoteException
    * @throws EJBException
    * @throws Exception
    * @return String value representing the flag indicator for delete denied or viewDelete
    */

   private String deleteHandler(Long UID, NBSSecurityObj nbsSecurityObj,
                                HttpSession session, HttpServletRequest request,
                                HttpServletResponse response) throws
       NEDSSAppConcurrentDataException, java.rmi.RemoteException,
       javax.ejb.EJBException, Exception {
      /**
       * Call the mainsessioncommand
       */
      MainSessionCommand msCommand = null;
      String deleteFlag = "";
      String sBeanJndiName = JNDINames.TREATMENT_PROXY_EJB;
      String sMethod = "deleteTreatmentProxy";

      logger.debug("sending proxy to delete treatment, and UID : " + UID);
      logger.debug("TreatmentSubmit: deleteHandler with treatmentID = " + UID);

      /**
       * Output TreatmentProxyVO for debugging
       */Object[] oParams = {UID};
      MainSessionHolder holder = new MainSessionHolder();
      msCommand = holder.getMainSessionCommand(session);
      ArrayList<?> resultUIDArr = new ArrayList<Object> ();
      resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
      boolean result;
      if ( (resultUIDArr != null) && (resultUIDArr.size() > 0)) {
         logger.debug("delete treatment worked!!! and arg = " +
                      resultUIDArr.get(0));
         result = ( (Boolean) resultUIDArr.get(0)).booleanValue();
         logger.debug("\n\n\n\n The result value is:" + result);
         if (result) {
            deleteFlag = "viewDelete";
         }
         else {
            deleteFlag = "deleteDenied";
         }
      }
      else {
         deleteFlag = "error";

      }
      return deleteFlag;
   }







}