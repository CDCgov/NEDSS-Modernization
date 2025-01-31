package gov.cdc.nedss.webapp.nbs.action.transferowner;


import gov.cdc.nedss.systemservice.nbscontext.*;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.webapp.nbs.action.util.DSQueueObject;



/**

 * Title:        TransferOwnershipConfirmLoad
 * Description:  This class places appropriate attributes in request object when
 * user persses submit on transfer ownership page..
 * Copyright:    Copyright (c) 2001
 * Company:      CSC
 * @version 1.0

 */
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;


public class TransferOwnershipConfirmLoad
    extends Action
{

    //For logging
    static final LogUtils logger = new LogUtils(TransferOwnershipConfirmLoad.class.getName());
  /**
   * Default constructor
   */
    public TransferOwnershipConfirmLoad()
    {
    }

    /**
     * This method binds appropriate varibales to request object.
     * @param ActionMapping mapping
     * @param ActionForm Form
     * @param HttpServletRequest request
     * @param HttpServletResponse response
     * @return ActionForward
     * @throws IOException, ServletException
     */



    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
                          throws IOException, ServletException
    {
        logger.debug(
                "remove the result session holding the organizationSearch object");
        request.getSession().removeAttribute("result");
        logger.debug("inside the OrganizationLoad");

        HttpSession session = request.getSession(false);

        if (session == null)
        {
            logger.debug("Error due to null session");

            throw new ServletException("Error due to null session");
        }

        String contextAction = request.getParameter("ContextAction");

        if (contextAction == null)
            contextAction = (String)request.getAttribute("ContextAction");

        String strUID = request.getParameter("organizationUID");

        if (strUID == null)
        {
            strUID = (String)request.getAttribute("organizationUID");
        }
        else
        {
            strUID = request.getParameter("organizationUID");
        }

        if (contextAction.equalsIgnoreCase("Submit"))
        {

            //context
            TreeMap<Object,Object> tm = NBSContext.getPageContext(session, "PS152",
                                                   contextAction);
            NBSContext.lookInsideTreeMap(tm);

            String sCurrTask = NBSContext.getCurrentTask(session);
            Long personUID =(Long)NBSContext.retrieve(session,"DSPatientPersonUID");
            NBSContext.store(session, "DSFileTab", "1");
            try{
            	if(NBSContext.retrieve(session,"DSQueueObject")!=null){
            		String queueType = ((DSQueueObject)NBSContext.retrieve(session,"DSQueueObject")).getDSQueueType();
            		if(queueType!=null && queueType.equals(NEDSSConstants.NEW_LAB_REPORTS_FOR_REVIEW))
            		{
            			request.setAttribute("linkName", "Return to Documents Requiring Review");
            	        request.setAttribute("linkValue",
            	                             "/nbs/" + sCurrTask + ".do?ContextAction=" +
            	                            tm.get("ReturnToObservationNeedingReview"));
            		}
            		else if (queueType != null
							&& queueType
									.equals(NEDSSConstants.MY_PROGRAM_AREAS_INVESTIGATIONS)) {
						request.setAttribute("linkName", NEDSSConstants.RETURN_TO_OPEN_INVESTIGATIONS);
						request.setAttribute("linkValue", "/nbs/" + sCurrTask
								+ ".do?ContextAction="
								+ tm.get("ReturnToMyInvestigations"));
					}
            		else if(queueType!=null && queueType.equals(NEDSSConstants.OBSERVATIONS_ASSIGNMENT))
            		{
            			request.setAttribute("linkName", "Return to Documents Requiring Security Assignment");
            	        request.setAttribute("linkValue",
            	                             "/nbs/" + sCurrTask + ".do?ContextAction=" +
            	                            tm.get("ReturnToObservationsNeedingAssignment"));
            		}
            	}
            }
            catch(NullPointerException ex){
             //Let it go as if object is not in the context don't show the link
            }

            request.setAttribute("ViewFile",
                                 "/nbs/" + sCurrTask +
                                 ".do?ContextAction=ReturnToFileEvents" + "&MPRUid="+personUID);
            request.setAttribute("ContextAction", tm.get("Submit"));
            request.setAttribute("cancelButtonHref",
                                 "/nbs/" + sCurrTask +
                                 ".do?ContextAction=" + tm.get("Cancel"));

            return mapping.findForward("XSP");
        }
        else if (contextAction.equalsIgnoreCase("Home"))
        {
           return mapping.findForward("Home");
        }
        throw new ServletException();
    }
}