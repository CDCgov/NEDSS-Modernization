package gov.cdc.nedss.webapp.nbs.action.file;

/**
 * Title:        Actions
 * Description:  WorkupSubmit Action Class
 * Copyright:    Copyright (c) 2001
 * Company:      CSC
 * @author
 * @version 1.0
 */

import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.exception.NEDSSAppConcurrentDataException;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.systemservice.nbscontext.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;




import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;

public class WorkupSubmit
    extends Action {
   /**
    * Default Constructor
    */

   public WorkupSubmit() {
   }
   static final LogUtils logger = new LogUtils(WorkupSubmit.class.getName());
   /**
    * This method does following:
    * 1.Stores appropriate object in to contrext's objectstore.
    * 2. Resends all the parameter in request object.
    * 3. Forwards to next page according to context.
    * @param ActionMapping mapping
    * @param ActionForm aForm
    * @param HttpServletRequest request
    * @param HttpServletResponse response
    * @return ActionForward
    * @throws IOException, ServletException
    */

   public ActionForward execute(ActionMapping mapping, ActionForm form,
		   HttpServletRequest request,
		   HttpServletResponse response) throws
		   IOException, ServletException {
	   String strContextAction = request.getParameter("ContextAction");
	   HttpSession session = request.getSession(false);
	   NBSSecurityObj secObj = (NBSSecurityObj) session.getAttribute(
			   "NBSSecurityObject");
	   try {
		   String result = null;

		   //VACCINATION
		   if (strContextAction.equals(NBSConstantUtil.ViewVaccination)) {
			   String interventionUID = request.getParameter("interventionUID");
			   NBSContext.store(session, "DSVaccinationUID", interventionUID);
			   NBSContext.store(session, "DSFileTab", "2");
		   }
		   //LAB
		   else if (strContextAction.equals("ObservationLabIDOnEvents")) {
			   String observationUID = request.getParameter("observationUID");
			   NBSContext.store(session, "DSObservationUID", observationUID);

		   }
		   else if (strContextAction.equals("ObservationLabIDOnSummary")) {
			   String publicHealthCaseUID = request.getParameter("observationUID");
			   NBSContext.store(session, "DSObservationUID", publicHealthCaseUID);
		   }
		   //MORB
		   else if (strContextAction.equals("ObservationMorbIDOnEvents")) {
			   String observationUID = request.getParameter("observationUID");
			   NBSContext.store(session, "DSObservationUID", observationUID);

		   }
		   else if (strContextAction.equals("ObservationMorbIDOnSummary")) {
			   String publicHealthCaseUID = request.getParameter("observationUID");
			   NBSContext.store(session, "DSObservationUID", publicHealthCaseUID);
		   }

		   //INVESTIGATION
		   else if (strContextAction.equals("InvestigationIDOnEvents")) {
			   String investgationUid = request.getParameter("publicHealthCaseUID");
			   NBSContext.store(session, "DSInvestigationUID", investgationUid);

		   }
		   //Compare investigations
		   else if (strContextAction.equals("CompareInvestigations")) {
			   String investigationUid = request.getParameter("publicHealthCaseUID0");
			   String investigationUid1 = request.getParameter("publicHealthCaseUID1");
			  
			   NBSContext.store(session, NBSConstantUtil.DSInvestigationUid, investigationUid);
			   NBSContext.store(session, NBSConstantUtil.DSInvestigationUid1, investigationUid1);
		   }
		   

		   else if (strContextAction.equals("InvestigationIDOnSummary")) {
			   String publicHealthCaseUID = request.getParameter(
					   "publicHealthCaseUID");
			   NBSContext.store(session, "DSInvestigationUID", publicHealthCaseUID);
		   }
		   //Treatment
		   else if (strContextAction.equals("TreatmentIDOnEvents")) {

			   String treatmentUID = request.getParameter("treatmentUID");
			   NBSContext.store(session, "DSTreatmentUID", treatmentUID);
			   NBSContext.store(session, "DSFileTab", "2");
		   }

		   //Document
		   else if (strContextAction.equals("DocumentIDOnEvents")) {
			   String nbsDocumentUid = request.getParameter("nbsDocumentUid");
			   NBSContext.store(session, "DSDocumentUID", nbsDocumentUid);
			   NBSContext.store(session, "DSFileTab", "2");
		   }
		   else if (strContextAction.equals("Delete")){
			   String personUID = NBSContext.retrieve(session, NEDSSConstants.DSPatientPersonUID).toString();
			   try
			   {
				   PersonVO personVO = getOldPersonObject(personUID, session);
				   NBSContext.store(session, NEDSSConstants.DSPatientPersonUID, personUID);
				   result = sendProxyToEJBDeleteMPR(personVO, "deleteMPR", session);
			   }
			   catch (NEDSSAppConcurrentDataException e) {
				   logger.fatal("ERROR - NEDSSAppConcurrentDataException, The data has been modified by another user, please recheck! ",
						   e);
				   return mapping.findForward("dataerror");
			   }

		   }

		   //need to process any parameters that come inside here
		   Enumeration<?> params = request.getParameterNames();

		   while (params.hasMoreElements()) {

			   String sParamName = (String) params.nextElement();
			   request.setAttribute(sParamName, request.getParameter(sParamName));
			   //##!! System.out.println( "(WORKUP SUBMIT) param to attribute name = " +    sParamName + "     value = " +	  request.getParameter(sParamName));
		   }
	   } catch (Exception ex) {
		   logger.error("Exception encountered in WorkupSubmit.execute(): ", ex);
		   ex.printStackTrace();
	   }
	   return mapping.findForward(strContextAction);
   }
   
	private PersonVO getOldPersonObject(String patientUID, HttpSession session) {

		PersonVO person = null;
		MainSessionCommand msCommand = null;

		if (patientUID != null) {

			try {

				Long UID = new Long(patientUID.trim());
				String sBeanJndiName = JNDINames.EntityControllerEJB;
				String sMethod = "getMPR";
				Object[] oParams = new Object[] { UID };

				// if(msCommand == null)
				// {
				MainSessionHolder holder = new MainSessionHolder();
				msCommand = holder.getMainSessionCommand(session);

				// }
				ArrayList<?> arr = msCommand.processRequest(sBeanJndiName,
						sMethod, oParams);
				person = (PersonVO) arr.get(0);
			} catch (NumberFormatException e) {
				logger.error("Error: no person UID");

				return null;
			} catch (Exception ex) {

				if (session == null) {
					logger.error("Error: no session, please login");
				}

				logger.fatal("getOldPersonObject: ", ex);

				return null;
			} finally {
				msCommand = null;
			}
		} 

		return person;
	}
	
	private String sendProxyToEJBDeleteMPR(PersonVO personVO,
			String paramMethodName, HttpSession session)
			throws NEDSSAppConcurrentDataException {

		MainSessionCommand msCommand = null;

		try {

			String sBeanJndiName = JNDINames.ENTITY_PROXY_EJB;
			String sMethod = paramMethodName;

			Object[] oParams = { personVO };

			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(session);

			ArrayList<?> resultArr = new ArrayList<Object>();
			resultArr = msCommand.processRequest(sBeanJndiName, sMethod,
					oParams);
			logger.debug("value of resultArr1 " + resultArr);

			boolean result;
			String deleteFlag = "";

			if ((resultArr != null) && (resultArr.size() > 0)) {
				logger.info("Delete person = " + resultArr.get(0));
				result = ((Boolean) resultArr.get(0)).booleanValue();

				if (result) {
					deleteFlag = "viewDelete";
				} else {
					deleteFlag = "deleteDenied";
				}

				return deleteFlag;
			} else {
				deleteFlag = "error";

				return deleteFlag;
			}
		} catch (NEDSSAppConcurrentDataException ncde) {
			ncde.printStackTrace();
			logger
					.fatal("Error: Could not delete record because of data concurrency.");
			throw new NEDSSAppConcurrentDataException();

		} catch (Exception e) {
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