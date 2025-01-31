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
import java.sql.Timestamp;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;

import gov.cdc.nedss.controller.ejb.entitycontrollerejb.bean.EntityControllerHome;
import gov.cdc.nedss.entity.person.dt.PersonDT;
import gov.cdc.nedss.entity.person.vo.*;
import gov.cdc.nedss.locator.dt.EntityLocatorParticipationDT;
import gov.cdc.nedss.locator.dt.PostalLocatorDT;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.systemservice.nbscontext.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.systemservice.util.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.webapp.nbs.action.person.util.*;
import gov.cdc.nedss.webapp.nbs.action.util.*;
import gov.cdc.nedss.webapp.nbs.form.person.*;
import gov.cdc.nedss.webapp.nbs.action.ldf.BaseLdf;
import gov.cdc.nedss.webapp.nbs.action.localfields.util.LocalFieldGenerator;

/**
 * This class is used for populating a PersonVO object that will be
 * utilized in populating a the "View Person" screen.
 */
public class PatientEditLoad
    extends BaseLdf{

   //For logging
   /**
    * An instance of the LogUtils object.
    */
   static final LogUtils logger = new LogUtils(PatientEditLoad.class.getName());

   /**
    * Constructor for the PersonLoad class.
    */
   public PatientEditLoad() {
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

      CompleteDemographicForm personForm = (CompleteDemographicForm) form;
      personForm.reset();
      personForm.setActionMode("Edit");

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
      ErrorMessageHelper.setErrMsgToRequest(request, "ps170");
      //context
      TreeMap<Object,Object> tm = NBSContext.getPageContext(session, "PS170", contextAction);

      String sCurrTask = NBSContext.getCurrentTask(session);
      request.setAttribute("formHref", "/nbs/" + HTMLEncoder.encodeHtml(sCurrTask) + ".do");
      request.setAttribute("ContextAction", tm.get("Submit"));
      request.setAttribute("cancelButtonHref",
                           "/nbs/" + HTMLEncoder.encodeHtml(sCurrTask) + ".do?ContextAction=" +
                        		   HTMLEncoder.encodeHtml(tm.get("Cancel").toString()));

      if (!patientUID.equals("")) {

         PersonVO personVO = getOldPersonObject(patientUID, personForm, session);
         try {
         PersonUtil.convertPersonToRequestObj(personVO, request, contextAction, null);
         }catch(Exception ex) {
 			throw new ServletException(ex.getMessage());
 		}
         if(personVO.getThePersonDT()==null)
        	personVO.setThePersonDT(new PersonDT());
         if(personVO.getThePersonDT().getBirthTime()==null)
        	 personVO.getThePersonDT().setBirthTime_s(null);

         PersonUtil util = new PersonUtil();
          
         util.getBirthAddress(personVO,personForm);
         util.getDeceasedAddress(personVO,personForm);
         request.setAttribute("personVO", personVO);
         
        	 try {
        		 //places NEWPAT_LDFS into request if LDFs are present for the Patient
        		 LocalFieldGenerator.loadPatientLDFs(personForm.getPamClientVO(), NEDSSConstants.EDIT_LOAD_ACTION, request,":");
				//String newPatLDFs = (String) request.getAttribute("NEWPAT_LDFS");
			} catch (Exception e) {
				 logger.info("PatientEditLoad error generating the LDFs? ->" + e.getMessage());
			}


         // store the proxy object in the form, so that when we submit
         // form, struts will write directly into the form
         personForm.setPerson(personVO);


      }
      else {
         logger.error("!!!!!!!!!!!!    no person UID");

         throw new ServletException("!!!!!!!!!!!!    no person UID");
      }

      if (patientUID != null) {
         NBSContext.store(session, "DSPatientPersonUID", new Long(patientUID));

      }
      personForm.setPageTitle("Edit Patient - Extended", request);

      
      return mapping.findForward("XSP");
   }

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

            //  if(msCommand == null)
            //{
            MainSessionHolder holder = new MainSessionHolder();
            msCommand = holder.getMainSessionCommand(session);

            // }
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
      else { // if the request didn't provide the PHCuid, look in form
         person = form.getPerson();
      }

      return person;
   }
   
   

}
