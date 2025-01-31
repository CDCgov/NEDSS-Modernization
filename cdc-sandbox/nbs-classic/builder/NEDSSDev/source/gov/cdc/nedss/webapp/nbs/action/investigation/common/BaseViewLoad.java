package gov.cdc.nedss.webapp.nbs.action.investigation.common;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.*;
import gov.cdc.nedss.systemservice.nbscontext.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.webapp.nbs.form.investigation.*;
import gov.cdc.nedss.association.dt.*;
import gov.cdc.nedss.entity.person.vo.*;
import gov.cdc.nedss.webapp.nbs.action.util.*;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;


public class BaseViewLoad
    extends CommonAction
{

    //For logging
    static final LogUtils logger = new LogUtils(BaseViewLoad.class.getName());

     /**
       * This is constructor for BaseViewLoad
       *
       */
    public BaseViewLoad()
    {
    }

    /**
      * Get values from investigation form and forward to next action.
      * @param ActionMapping the mapping
      * @param ActionForm the form contain values
      * @param HttpServletRequest the request
      * @param HttpServletResponse the response
      * @return  ActionForward Object
      * @throws ServletException and IOException
      */
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
				 HttpServletResponse response)
			  throws IOException, ServletException
    {

    	throw new ServletException();
    }



     /**
      * Get Public Health Case LocalID from session object.
      * @param Long the investigationId
      * @param HttpSession the session
      * @param HttpServletRequest the request
      * @return  String with value of Public Health Case Local id
      * @throws Exception
      */
     protected String getPublicHealthCaseLocalID(Long investigationId,
					     HttpSession session, HttpServletRequest request)
				      throws Exception
    {

	MainSessionCommand msCommand = null;
	String publicHealthCaseLocalID = null;

	try
	{

	    String sBeanJndiName = JNDINames.INVESTIGATION_PROXY_EJB;
	    String sMethod = "publicHealthCaseLocalID";
	    logger.debug("sending investigationProxyVO to investigationproxyejb, via mainsession");

	    Object[] oParams = { investigationId };
	    MainSessionHolder holder = new MainSessionHolder();
	    msCommand = holder.getMainSessionCommand(session);
	    logger.info("mscommand in InvestigationCreate No View Submit class is: " + msCommand);
	    ArrayList<?> resultUIDArr = new ArrayList<Object> ();
	    resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);

	    if ((resultUIDArr != null) && (resultUIDArr.size() > 0))
	    {
		publicHealthCaseLocalID = (String)resultUIDArr.get(0);
	    }

	}
	catch (Exception e)
	{
	    logger.fatal("ERROR calling mainsession control", e);
	    throw e;
	}

	return publicHealthCaseLocalID;
    }



     /**
      * Get InvestigationProxyVO from the session object.
      * @param String the sPublicHealthCaseUID
      * @param HttpSession the session
      * @param HttpServletRequest the request
      * @return  InvestigationProxyVO
      */
    protected InvestigationProxyVO getOldProxyObject(String sPublicHealthCaseUID,
						   InvestigationForm investigationForm,
						   HttpSession session)
    {

	InvestigationProxyVO proxy = null;
	MainSessionCommand msCommand = null;

	    try
	    {
              //long beforeLoabObjClass = new Date().getTime();
              //long timeDiff = 0;


		Long publicHealthCaseUID = new Long(sPublicHealthCaseUID);
		String sBeanJndiName = JNDINames.INVESTIGATION_PROXY_EJB;
		String sMethod = "getInvestigationProxy";
		Object[] oParams = new Object[] { publicHealthCaseUID };

		//  if(msCommand == null)
		//{
		MainSessionHolder holder = new MainSessionHolder();
		msCommand = holder.getMainSessionCommand(session);

		// }
		ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
                //long afterLoabObjClass = new Date().getTime();
                //timeDiff = afterLoabObjClass - beforeLoabObjClass;
                //System.out.println(" load proxy from backend : " + timeDiff);


		proxy = (InvestigationProxyVO)arr.get(0);
		investigationForm.reset();
		investigationForm.setOldProxy(proxy);
		//investigationForm.setProxy(proxy);
                PersonVO personVO = getPersonVO(NEDSSConstants.PHC_PATIENT,proxy);
                investigationForm.setPatient(personVO);
                investigationForm.setOldRevision((PersonVO)personVO.deepCopy());

		investigationForm.setLoadedFromDB(true);
	    }
	    catch (Exception ex)
	    {
		logger.fatal("getOldProxyObject: ", ex);
	    }

	return proxy;
    }



     /**
      * To check permission action for next level from NBSSecurityObj.
      * @param HttpServletRequest the request
      * @param NBSSecurityObj the nbsSecurityObj
      */
    protected void setSecurityForView(InvestigationProxyVO oldInvestigationProxyVO, HttpServletRequest request, NBSSecurityObj nbsSecurityObj)
    {
       String programAreaCd = oldInvestigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getProgAreaCd();
       String condtionCd = oldInvestigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd();
       String jurisdictionCd = oldInvestigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getJurisdictionCd();
       String sharedInd = oldInvestigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getSharedInd();

       //created required checks for security in the front end
       boolean checkViewFilePermission = nbsSecurityObj.getPermission(NBSBOLookup.PATIENT,
								       NBSOperationLookup.VIEWWORKUP);
       boolean checkViewEditInvPermission = nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,
									  NBSOperationLookup.EDIT, programAreaCd, jurisdictionCd, sharedInd);

       //03/15/04 Narendra (Updated with jurisdictionCd as param, not conditionCd)
       boolean checkViewTransferPermission = nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,
									  NBSOperationLookup.TRANSFERPERMISSIONS, programAreaCd, jurisdictionCd, sharedInd);
      

       boolean checkViewVaccPermission = nbsSecurityObj.getPermission(
						  NBSBOLookup.INTERVENTIONVACCINERECORD,
						  NBSOperationLookup.VIEW);
       boolean checkViewObsLabPermission = nbsSecurityObj.getPermission(
						    NBSBOLookup.OBSERVATIONLABREPORT,
						    NBSOperationLookup.VIEW);
       boolean checkViewObsMorbPermission = nbsSecurityObj.getPermission(
						    NBSBOLookup.OBSERVATIONMORBIDITYREPORT,
						    NBSOperationLookup.VIEW);
       boolean assocLabPermission = nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,
               NBSOperationLookup.ASSOCIATEOBSERVATIONLABREPORTS,
               oldInvestigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getProgAreaCd(),
               oldInvestigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getJurisdictionCd(), sharedInd);
         boolean assocMorbPermission = nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,
               NBSOperationLookup.ASSOCIATEOBSERVATIONMORBIDITYREPORTS,
               oldInvestigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getProgAreaCd(),
               oldInvestigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getJurisdictionCd(), sharedInd);
        boolean checkViewEditObsLabPermission = nbsSecurityObj.getPermission(
							NBSBOLookup.OBSERVATIONLABREPORT,
							NBSOperationLookup.EDIT);
       boolean checkViewEditVaccPermission = nbsSecurityObj.getPermission(
						      NBSBOLookup.INTERVENTIONVACCINERECORD,
						      NBSOperationLookup.EDIT);

       boolean checkCreateNotific = nbsSecurityObj.getPermission(NBSBOLookup.NOTIFICATION,
						      NBSOperationLookup.CREATE, oldInvestigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getProgAreaCd(),
				               oldInvestigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getJurisdictionCd(), sharedInd);

       boolean checkCreateNeedsApprovalNotific = nbsSecurityObj.getPermission(NBSBOLookup.NOTIFICATION,
						      NBSOperationLookup.CREATENEEDSAPPROVAL,oldInvestigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getProgAreaCd(),
				               oldInvestigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getJurisdictionCd(), sharedInd);
       
       boolean checkCreateCaseRep = nbsSecurityObj.getPermission(NBSBOLookup.CASEREPORTING,
			      NBSOperationLookup.CREATE, oldInvestigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getProgAreaCd(),
	               oldInvestigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getJurisdictionCd(), sharedInd);
       
       boolean checkCreateNeedsApprovalCaseRep = nbsSecurityObj.getPermission(NBSBOLookup.CASEREPORTING,
			      NBSOperationLookup.CREATENEEDSAPPROVAL,oldInvestigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getProgAreaCd(),
	               oldInvestigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getJurisdictionCd(), sharedInd);
       
       boolean manageTreatment = nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,
               NBSOperationLookup.ASSOCIATETREATMENTS,
               oldInvestigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getProgAreaCd(),
               oldInvestigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getJurisdictionCd(), sharedInd);

       boolean manageVaccination = nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,
               NBSOperationLookup.ASSOCIATEINTERVENTIONVACCINERECORDS,
               oldInvestigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getProgAreaCd(),
               oldInvestigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getJurisdictionCd(), sharedInd);

       boolean deleteInvestigation = nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,
               NBSOperationLookup.DELETE,
               oldInvestigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getProgAreaCd(),
               oldInvestigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getJurisdictionCd(), sharedInd);
	request.setAttribute("deleteInvestigation", String.valueOf(deleteInvestigation));


	//To check permission for file link
	request.setAttribute("checkFile", String.valueOf(checkViewFilePermission));

	//to check security for Transfer Ownership button to be displayed
	CommonInvestigationUtil util = new CommonInvestigationUtil();
	 boolean showTransferOwnershipButton = util.showTransferOwnerShipButton(oldInvestigationProxyVO, nbsSecurityObj);
	request.setAttribute("checkTransfer", String.valueOf(checkViewTransferPermission && showTransferOwnershipButton));
	

    request.setAttribute("checkManageEvents", String.valueOf(assocLabPermission || assocMorbPermission||manageVaccination ||manageTreatment));

	//to check security for manage Notifications button to be displayed
        // When auto resend ind on Notification table is true, Create Notification button shall not be displayed)
        
        boolean showNotificationCreateButton = util.showCreateNotificationButton(oldInvestigationProxyVO, nbsSecurityObj);

        request.setAttribute("checkManageNotific", String.valueOf((checkCreateNotific || checkCreateNeedsApprovalNotific) &&(showNotificationCreateButton)));
        
     // same for case reporting button(share button)
       // boolean showCaseReportingCreateButton = util.showCreateCaseRepButton(oldInvestigationProxyVO, nbsSecurityObj);
        
        request.setAttribute("checkCaseReporting", String.valueOf((checkCreateCaseRep || checkCreateNeedsApprovalCaseRep)));


	//to check for Edit button to be displayed or not
	request.setAttribute("editInv", String.valueOf(checkViewEditInvPermission));

	//to check for Observation Display box to be displayed or not
	request.setAttribute("ObsDisplay", String.valueOf(checkViewObsLabPermission));
	//to check for Observation Display box to be displayed or not
	request.setAttribute("MorbDisplay", String.valueOf(checkViewObsMorbPermission));

	//to check for Observation Display box EDIT hyperlink to be displayed or not
	request.setAttribute("ObsEdit", String.valueOf(checkViewEditObsLabPermission));

	//to check for vaccination record box to be displayed
	request.setAttribute("VaccDisplay", String.valueOf(checkViewVaccPermission));

	//to check for edit hyperlink vaccination record to be displayed
	request.setAttribute("VaccEdit", String.valueOf(checkViewEditVaccPermission));
    }


      /**
      * To set context action for view.
      * @param HttpServletRequest the request
      * @param NBSSecurityObj the nbsSecurityObj
      * @return  String as context
      */
    protected Map<Object, Object> setContextForView(HttpServletRequest request, HttpSession session)
    { 
    Map<Object, Object> map =new HashMap<Object, Object> ();
	String passedContextAction = request.getParameter("ContextAction");
	if(passedContextAction==null){
		String ContactTracing = (String)NBSContext.retrieve(request.getSession(), NBSConstantUtil.DSInvestigationPath);
		passedContextAction=ContactTracing;
	}
	TreeMap tm = NBSContext.getPageContext(session, "PS036", passedContextAction);
	String urlForViewFile=tm.get("ReturnToContactFileEvents").toString();
	String urlFOrContactCase=tm.get("ContactCase").toString();
	map.put("urlForViewFile", urlForViewFile);
	map.put("ContactCase", urlFOrContactCase);
	request.setAttribute("urlForViewFile", urlForViewFile);
	request.setAttribute("ContactCase", urlFOrContactCase);
	

        ErrorMessageHelper.setErrMsgToRequest(request);
	String sCurrentTask = NBSContext.getCurrentTask(session);	
	request.setAttribute("sCurrentTask", sCurrentTask);

	map.put("sCurrentTask", sCurrentTask);
	request.setAttribute("CurrentTask", sCurrentTask);
	request.setAttribute("Submit",
			     "/nbs/" + sCurrentTask + ".do?ContextAction=" + tm.get("Submit"));
	request.setAttribute("Cancel",
			     "/nbs/" + sCurrentTask + ".do?ContextAction=" + tm.get("Cancel"));
	  request.setAttribute("deleteButtonHref",
  		     "/nbs/" + sCurrentTask + ".do?ContextAction=" + tm.get("Cancel"));
	request.setAttribute("SubmitNoViewAccess",
			     "/nbs/" + sCurrentTask + ".do?ContextAction=" +
			     tm.get("SubmitNoViewAccess"));
	request.setAttribute("Sort",
			     "/nbs/" + sCurrentTask + ".do?ContextAction=" + tm.get("Sort"));
	request.setAttribute("InvestigationID",
			     "/nbs/" + sCurrentTask + ".do?ContextAction=" +
			     tm.get("InvestigationID"));
	request.setAttribute("ReturnToViewInvestigation",
			     "/nbs/" + sCurrentTask + ".do?ContextAction=" +
			     tm.get("ReturnToViewInvestigation"));
	request.setAttribute("InvestigationIDOnSummary",
			     "/nbs/" + sCurrentTask + ".do?ContextAction=" +
			     tm.get("InvestigationIDOnSummary"));
	request.setAttribute("InvestigationIDOnInv",
			     "/nbs/" + sCurrentTask + ".do?ContextAction=" +
			     tm.get("InvestigationIDOnInv"));
	request.setAttribute("createNotification",
			     "/nbs/" + sCurrentTask + ".do?ContextAction=" +
			     tm.get("CreateNotification"));
	request.setAttribute("caseReporting",
		     "/nbs/" + sCurrentTask + ".do?ContextAction=" +
		     tm.get("CaseReporting"));
	request.setAttribute("TransferOwnership",
		     "/nbs/" + sCurrentTask + ".do?ContextAction=" +
		     tm.get("TransferOwnership"));
	request.setAttribute("CaseReporting",
		     "/nbs/" + sCurrentTask + ".do?ContextAction=" +
		     tm.get("CaseReporting"));
	/*request.setAttribute(NBSConstantUtil.ViewVaccinationFromInv,
			     "/nbs/" + sCurrentTask + ".do?ContextAction=" +
			     tm.get(NBSConstantUtil.ViewVaccinationFromInv));*/
	//request.setAttribute(NBSConstantUtil.ViewVaccinationFromInv,"javascript:contactRecordPopUp('/nbs/PageAction.do?method=viewGenericLoad&businessObjectType=VAC&Action=InvPath&interventionUID=");
	request.setAttribute("ObservationLabID",
			     "/nbs/" + sCurrentTask + ".do?ContextAction=" +
			     tm.get("ObservationLabID"));
	request.setAttribute("ObservationMorbID",
			     "/nbs/" + sCurrentTask + ".do?ContextAction=" +
			     tm.get("ObservationMorbID"));
        request.setAttribute("treatmentEventRef",
                           "/nbs/" + sCurrentTask + ".do?ContextAction=" +
                           tm.get("TreatmentID"));
        request.setAttribute("DocumentIDFromInv",
   		     "/nbs/" + sCurrentTask + ".do?ContextAction=" +
   		     tm.get("DocumentIDFromInv"));

	request.setAttribute("PrintPage", "/nbs/LoadViewInvestigation1.do?ContextAction=PrintPage");

	if (sCurrentTask.equalsIgnoreCase("ViewInvestigation3"))
	{
	    request.setAttribute("linkName", "Return To File: Events");
	    request.setAttribute("linkValue",
				 "/nbs/" + sCurrentTask + ".do?ContextAction=" +
				 tm.get("ReturnToFileEvents"));
	  request.setAttribute("deleteButtonHref",
	  		     "/nbs/" + sCurrentTask + ".do?ContextAction=" + tm.get("ReturnToFileEvents")+"&delete="+"true");

	}
	else if (sCurrentTask.equalsIgnoreCase("ViewInvestigation2") )
	{
	    request.setAttribute("linkName", "View File");
	    request.setAttribute("linkValue",
				 "/nbs/" + sCurrentTask + ".do?ContextAction=" +
				 tm.get("FileSummary"));
	    
	    
	  //Custom queues: In future, the context action will depend on the object type? Right now only investigation is available
		 String custom = (String)request.getAttribute("custom");
		 String queueName =  (String)request.getAttribute("queueName");
		 String reportType =  (String)request.getAttribute("reportType");//TODO: it will need to be read from somewhere else when this is extended for other business object types
		   
		//Showing the investigation from custom queues
		if(custom!=null && custom.equalsIgnoreCase("true")){
			

			String pageNumberCustom = (String)request.getSession().getAttribute("pageNumberCustom");
			request.getSession().removeAttribute("pageNumberCustom");
			
			if(pageNumberCustom==null)
				pageNumberCustom = "1";

			request.setAttribute("linkReturnToCustomQueueName", "Return to Search Results");
 
			request.setAttribute(
					"linkReturnToCustomQueueValue",
					"/nbs/" + sCurrentTask + ".do?ContextAction="
							+ tm.get("investigation") + "&custom=" +custom+"&queueName="+queueName+"&reportType="+reportType+"&pageNumber="+pageNumberCustom);
		}
		
		
	    request.setAttribute("deleteButtonHref",
	  		     "/nbs/" + sCurrentTask + ".do?ContextAction=" + tm.get("FileSummary")+"&delete="+"true");
	    try {
				DSQueueObject queueObject = (DSQueueObject) NBSContext
						.retrieve(session, "DSQueueObject");
				if (queueObject.getDSQueueType() != null
						&& queueObject.getDSQueueType().equals(
								NEDSSConstants.MY_PROGRAM_AREAS_INVESTIGATIONS)) {
					request.setAttribute("linkName1",NEDSSConstants.RETURN_TO_OPEN_INVESTIGATIONS);
					request.setAttribute("linkValue1", "/nbs/" + sCurrentTask
							+ ".do?ContextAction="
							+ tm.get("ReturnToMyInvestigations"));
				} else if (queueObject.getDSQueueType() != null
						&& queueObject.getDSQueueType().equals(
								NEDSSConstants.NEW_LAB_REPORTS_FOR_REVIEW)) {
					request.setAttribute("linkName1",
							"Return to Documents Requiring Review");
					request.setAttribute("linkValue1", "/nbs/" + sCurrentTask
							+ ".do?ContextAction="
							+ tm.get("ReturnToObservationNeedingReview"));
				}
			} catch (Exception ex) {
				logger
						.info("Link: cannot be shown in this context");
				// exception means that navigation went out of context, so the
				// above link cannot be shown to user.
			}
		}else if(sCurrentTask.equalsIgnoreCase("ViewInvestigation13")){
			
			/*
			  request.setAttribute("linkName", "View File");
			    request.setAttribute("linkValue",
						 "/nbs/" + sCurrentTask + ".do?ContextAction=" +
						 tm.get("FileSummary"));*/
			    request.setAttribute("deleteButtonHref",
			  		     "/nbs/" + sCurrentTask + ".do?ContextAction=" + tm.get("FileSummary")+"&delete="+"true");
			    try {
						DSQueueObject queueObject = (DSQueueObject) NBSContext
								.retrieve(session, "DSQueueObject");
						if (queueObject.getDSQueueType() != null
								&& queueObject.getDSQueueType().equals(
										NEDSSConstants.MY_PROGRAM_AREAS_INVESTIGATIONS)) {
							request.setAttribute("linkName1",NEDSSConstants.RETURN_TO_OPEN_INVESTIGATIONS);
							request.setAttribute("linkValue1", "/nbs/" + sCurrentTask
									+ ".do?ContextAction="
									+ tm.get("ReturnToMyInvestigations"));
						} else if (queueObject.getDSQueueType() != null
								&& queueObject.getDSQueueType().equals(
										NEDSSConstants.NEW_LAB_REPORTS_FOR_REVIEW)) {
							if(passedContextAction.equalsIgnoreCase("ViewInv")){
								request.setAttribute("linkName1",
										"Return to Documents Requiring Review");
								request.setAttribute("linkValue1", "/nbs/" + sCurrentTask
										+ ".do?ContextAction="
										+ tm.get("ReturnToObservationNeedingReview"));
							}
						    request.setAttribute("linkValue",
									 "/nbs/" + sCurrentTask + ".do?ContextAction=" +
									 tm.get("ReturnToFileEvents"));
							//add ContextAction=ReturnToFileEvents
							request.setAttribute("linkName",
									"Return To File: Events");
						}
					} catch (Exception ex) {
						logger
								.info("Link: cannot be shown in this context");
						// exception means that navigation went out of context, so the
						// above link cannot be shown to user.
					}
			
			
			
			
			
		}
	else if (sCurrentTask.equalsIgnoreCase("ViewInvestigation1"))
	{
	    request.setAttribute("linkName", "Return to File: Summary");
	    request.setAttribute("linkValue",
				 "/nbs/" + sCurrentTask + ".do?ContextAction=" +
				 tm.get("ReturnToFileSummary"));
		  request.setAttribute("deleteButtonHref",
		  		     "/nbs/" + sCurrentTask + ".do?ContextAction=" + tm.get("ReturnToFileSummary")+"&delete="+"true");
	}
	else if (sCurrentTask.equalsIgnoreCase("ViewInvestigation4"))
	{
			try {
				DSQueueObject queueObject = (DSQueueObject) NBSContext
						.retrieve(session, "DSQueueObject");
				if (NEDSSConstants.MESSAGE_QUEUE.equals(queueObject
						.getDSQueueType())) {
					request.setAttribute("linkName", "Return to Messages Queue");
					request.setAttribute(
							"linkValue",
							"/nbs/" + sCurrentTask + ".do?ContextAction="
									+ tm.get("ReturnToMessageQueue"));
					request.setAttribute(
							"deleteButtonHref",
							"/nbs/" + sCurrentTask + ".do?ContextAction="
									+ tm.get("ReturnToMessageQueue")
									+ "&delete=" + "true");
				} else if (NEDSSConstants.NND_NOTIFICATIONS_FOR_APPROVAL
						.equals(queueObject.getDSQueueType())) {
					request.setAttribute("linkName",
							"Return to Approval Queue for Initial Notifications");
					request.setAttribute(
							"linkValue",
							"/nbs/" + sCurrentTask + ".do?ContextAction="
									+ tm.get("ReturnToReviewNotifications"));
					request.setAttribute(
							"deleteButtonHref",
							"/nbs/" + sCurrentTask + ".do?ContextAction="
									+ tm.get("ReturnToReviewNotifications")
									+ "&delete=" + "true");
				} else if (NEDSSConstants.SUPERVISOR_REVIEW_QUEUE
						.equals(queueObject.getDSQueueType())) {
					request.setAttribute("linkName",
							"Return to Supervisor Review Queue");
					request.setAttribute(
							"linkValue",
							"/nbs/" + sCurrentTask + ".do?ContextAction="
									+ tm.get("ReturnToSupervisorReviewQueue"));

					request.setAttribute(
							"deleteButtonHref",
							"/nbs/" + sCurrentTask + ".do?ContextAction="
									+ tm.get("ReturnToSupervisorReviewQueue")
									+ "&delete=" + "true");

				}
			} catch (Exception ex) {
				logger.info("Link: cannot be shown in this context");
			}
	}
	else if (sCurrentTask.equalsIgnoreCase("ViewInvestigation5"))
	{
	    request.setAttribute("linkName", "Return to Updated Notifications Queue");
	    request.setAttribute("linkValue",
				 "/nbs/" + sCurrentTask + ".do?ContextAction=" +
				 tm.get("ReturnToReviewUpdatedNotifications"));
		  request.setAttribute("deleteButtonHref",
		  		     "/nbs/" + sCurrentTask + ".do?ContextAction=" + tm.get("ReturnToReviewUpdatedNotifications")+"&delete="+"true");
	}
	else if (sCurrentTask.equalsIgnoreCase("ViewInvestigation6"))
	{
	    request.setAttribute("linkName", "Return to Rejected Notifications Queue");
	    request.setAttribute("linkValue",
				 "/nbs/" + sCurrentTask + ".do?ContextAction=" +
				 tm.get("ReturnToRejectedNotifications"));
		  request.setAttribute("deleteButtonHref",
		  		     "/nbs/" + sCurrentTask + ".do?ContextAction=" + tm.get("ReturnToRejectedNotifications")+"&delete="+"true");
	}
	else if ( sCurrentTask.equalsIgnoreCase("ViewInvestigation7") || sCurrentTask.equalsIgnoreCase("ViewInvestigation8") ||
			sCurrentTask.equalsIgnoreCase("ViewInvestigation9")  ||  sCurrentTask.equalsIgnoreCase("ViewInvestigation10") ||
			sCurrentTask.equalsIgnoreCase("ViewInvestigation11") ||  sCurrentTask.equalsIgnoreCase("ViewInvestigation12") ) 
	{
	    request.setAttribute("linkName", NEDSSConstants.RETURN_TO_ASSOCIATE_TO_INVESTIGATIONS);
	    request.setAttribute("linkValue",
				 "/nbs/" + sCurrentTask + ".do?ContextAction=" +
				 tm.get("ReturnToAssociateToInvestigations"));
	}
	request.setAttribute("Edit",
			     "/nbs/" + sCurrentTask + ".do?ContextAction=" + tm.get("Edit"));
	request.setAttribute("ManageEvents",
			     "/nbs/" + sCurrentTask + ".do?ContextAction=" +
			     tm.get("ManageEvents"));
		return map;
    }

    /**
     * looks for patient in InvestigationProxyVO and returns it
     * @param type_cd : String
     * @param investigationProxyVO : InvestigationProxyVO
     * @return : PersonVO
     */
    private PersonVO getPersonVO(String type_cd, InvestigationProxyVO investigationProxyVO)
    {
        Collection<Object>  participationDTCollection   = null;
        Collection<Object>  personVOCollection  = null;
        ParticipationDT participationDT = null;
        PersonVO personVO = null;
        //Long phcUID = investigationProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid();
        participationDTCollection  = investigationProxyVO.getPublicHealthCaseVO().getTheParticipationDTCollection();
        //logger.debug("convertProxyToRequestObj() after participationDTCollection  size: " + participationDTCollection.size());
        personVOCollection  = investigationProxyVO.getThePersonVOCollection();
        //logger.debug("convertProxyToRequestObj() after personVOCollection  size: " + personVOCollection.size());
        if(participationDTCollection  != null)
        {
           Iterator<Object>  anIterator1 = null;
           Iterator<Object>  anIterator2 = null;
            for(anIterator1 = participationDTCollection.iterator(); anIterator1.hasNext();)
            {
                participationDT = (ParticipationDT)anIterator1.next();
                if(participationDT.getTypeCd() != null && (participationDT.getTypeCd()).compareTo(type_cd) == 0)
                {
                    //logger.debug("convertProxyToRequestObj() got participationDT for  type_cd: " + participationDT.getTypeCd());
                    for(anIterator2 = personVOCollection.iterator(); anIterator2.hasNext();)
                    {
                        personVO = (PersonVO)anIterator2.next();
                        if(personVO.getThePersonDT().getPersonUid().longValue() == participationDT.getSubjectEntityUid().longValue())
                        {
                            //logger.debug("convertProxyToRequestObj() got personVO for  person_uid: " + personVO.getThePersonDT().getPersonUid().longValue() + " and type_cd " + participationDT.getTypeCd());
                            return personVO;
                        }
                        else continue;
                    }
                }
                else continue;
            }
        }
        return null;
    }


}