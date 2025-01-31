package gov.cdc.nedss.webapp.nbs.action.transferowner;


import gov.cdc.nedss.systemservice.nbscontext.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.webapp.nbs.form.observation.*;



/**

 * Title:        TransferOwnershipLoad
 * Description:  This class places appropriate attributes in request object when
 * user initiates transfer ownership.
 * Copyright:    Copyright (c) 2001
 * Company:      CSC
 * @version 1.0

 */
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;


public class TransferOwnershipLoad
    extends Action
{

    //For logging
    static final LogUtils logger = new LogUtils(TransferOwnershipLoad.class.getName());
  /**
   * Default constructor
   */
    public TransferOwnershipLoad()
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

        	throw new ServletException("Session is null");
        }

        NBSSecurityObj secObj = (NBSSecurityObj)session.getAttribute(
                                        "NBSSecurityObject");

        String contextAction = request.getParameter("ContextAction");

        try {
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

        	ObservationGeneralForm observationGeneralForm = (ObservationGeneralForm)form;


        	//  Transfer action
        	if (contextAction.equalsIgnoreCase("TransferOwnership"))
        	{
        		//context
        		TreeMap<Object,Object> tm = NBSContext.getPageContext(session, "PS151",
        				contextAction);
        		NBSContext.lookInsideTreeMap(tm);

        		String sCurrTask = NBSContext.getCurrentTask(session);

        		request.setAttribute("formHref", "/nbs/" + sCurrTask + ".do");

        		request.setAttribute("ContextAction", tm.get("Submit"));

        		request.setAttribute("cancelButtonHref",
        				"/nbs/" + sCurrTask +
        				".do?ContextAction=" + tm.get("Cancel"));
        		

        		return mapping.findForward("XSP");
        	}
        }catch (Exception e) {
        	logger.error("Exception in Transfer Ownership Load: " + e.getMessage());
        	e.printStackTrace();
        	throw new ServletException("Error occurred in Exception in Transfer Ownership Load : "+e.getMessage());
        } 
        throw new ServletException();
    }
}