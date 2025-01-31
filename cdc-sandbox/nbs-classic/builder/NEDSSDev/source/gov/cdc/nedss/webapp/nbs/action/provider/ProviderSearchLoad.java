package gov.cdc.nedss.webapp.nbs.action.provider;


import gov.cdc.nedss.systemservice.nbscontext.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.webapp.nbs.form.provider.*;
import gov.cdc.nedss.webapp.nbs.action.util.ErrorMessageHelper;


/**
 * Title:        Actions
 * Description:  Provider Search Load
 * Copyright:    Copyright (c) 2001
 * Company:      CSC
 * @author
 * @version 1.0
 */
import java.io.*;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;


/**
 * This is the action class that is used to
 * prepare the Person Search page for use.
 */
public class ProviderSearchLoad
    extends Action
{
    /**
     * This is the constructor for the PersonSearchLoad class.
     */
    public ProviderSearchLoad()
    {
    }

    /**
     * The execute method is the method that gets executed first when
     * a request is made to this action class.
     *
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @exception IOException
     * @exception ServletException
     * @return ActionForward
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form,
				 HttpServletRequest request,
				 HttpServletResponse response)
			  throws IOException, ServletException
    {

	HttpSession session = request.getSession(false);
	NBSSecurityObj secObj = (NBSSecurityObj)session.getAttribute(
					"NBSSecurityObject");
	String strType = request.getParameter("OperationType");

	if (strType == null)
	    strType = (String)request.getAttribute("OperationType");

	String contextAction = request.getParameter("ContextAction");
    	if (contextAction == null)
	    contextAction = (String)request.getAttribute("ContextAction");
      if(request.getParameter("PersonName")!=null && !request.getParameter("PersonName").equals(""))
        session.setAttribute("PersonName",request.getParameter("PersonName"));
         String PersonName = (String)session.getAttribute("PersonName");

        ErrorMessageHelper.setErrMsgToRequest(request, "ps186");

	if (contextAction.equalsIgnoreCase("GlobalProvider") ||
	    contextAction.equalsIgnoreCase("NewSearch"))
	{

	    // context
	    TreeMap<Object,Object> tm = NBSContext.getPageContext(session, "PS186",
						   contextAction);

         request.setAttribute("PersonName", PersonName);
	    String sCurrTask = NBSContext.getCurrentTask(session);
         if(sCurrTask.startsWith("FindProvider1"))
         {
            session.setAttribute("PersonName", null);
            PersonName = (String)session.getAttribute("PersonName");
         }
	    //request.setAttribute("formHref", "/nbs/" + sCurrTask + ".do");
            request.setAttribute("formHref", "/nbs/FindProvider1.do");
            request.setAttribute("ContextAction", tm.get("Submit"));
            request.setAttribute("PersonName", PersonName);
	    ProviderSearchForm psForm = (ProviderSearchForm)form;
	    psForm.reset();

	    boolean bStatusCheckbox = secObj.getPermission(NBSBOLookup.PATIENT,
							   NBSOperationLookup.FINDINACTIVE);
	    request.setAttribute("sec-status", String.valueOf(bStatusCheckbox));
	}
	else if (contextAction.equalsIgnoreCase("RefineSearch"))
	{

	    TreeMap<Object,Object> tm = NBSContext.getPageContext(session, "PS186",
						   contextAction);
         request.setAttribute("PersonName", PersonName);
	    String sCurrTask = NBSContext.getCurrentTask(session);
	    request.setAttribute("formHref", "/nbs/" + sCurrTask + ".do");

	    request.setAttribute("ContextAction", tm.get("Submit"));

	    boolean bStatusCheckbox = secObj.getPermission(NBSBOLookup.PATIENT,
							   NBSOperationLookup.FINDINACTIVE);
	    request.setAttribute("sec-status", String.valueOf(bStatusCheckbox));
	    request.setAttribute("DSSearchCriteria",
				 NBSContext.retrieve(session,
						     "DSSearchCriteria"));
	}
	else if (contextAction.equalsIgnoreCase("EntitySearch"))
	{

	    request.setAttribute("formHref", "/nbs/FindProvider3.do");
	    request.setAttribute("ContextAction", "EntitySearch");
	    request.setAttribute("roleSecurity",
			     			String.valueOf(secObj.getPermission(
						    NBSBOLookup.PROVIDER,
						    NBSOperationLookup.ROLEADMINISTRATION)));

		request.setAttribute("PersonName", PersonName);
		String mode=request.getParameter("mode");

			if(mode==null)
		        request.setAttribute("DSSearchCriteria",session.getAttribute("SearchCriteria"));

	}
        else if(contextAction.equalsIgnoreCase("MergePerson")||contextAction.equalsIgnoreCase("NewSearch1"))
        {
          //TreeMap tm = NBSContext.getPageContext(session, "PS089",contextAction);


         String sCurrTask = NBSContext.getCurrentTask(session);
         request.setAttribute("formHref", "/nbs/FindPerson4.do");

         request.setAttribute("ContextAction", "MergePerson1");

         ProviderSearchForm psForm = (ProviderSearchForm)form;
         psForm.reset();

         boolean bStatusCheckbox = secObj.getPermission(NBSBOLookup.PATIENT,
                                                        NBSOperationLookup.FINDINACTIVE);
         request.setAttribute("sec-status", String.valueOf(bStatusCheckbox));

        }
        else if(contextAction.equalsIgnoreCase("RefineSearch1"))
        {
          //TreeMap tm = NBSContext.getPageContext(session, "PS089",contextAction);


         String sCurrTask = NBSContext.getCurrentTask(session);
         request.setAttribute("formHref", "/nbs/FindPerson4.do");

         request.setAttribute("ContextAction", "MergePerson1");

         ProviderSearchForm psForm = (ProviderSearchForm)form;
         psForm.reset();

         boolean bStatusCheckbox = secObj.getPermission(NBSBOLookup.PATIENT,
                                                        NBSOperationLookup.FINDINACTIVE);
         request.setAttribute("sec-status", String.valueOf(bStatusCheckbox));
         request.setAttribute("DSSearchCriteria",session.getAttribute("DSSearchCriteria"));
                                // NBSContext.retrieve(session,
                                                     //"DSSearchCriteria"));

        }

	return mapping.findForward("XSP");
    }
}
