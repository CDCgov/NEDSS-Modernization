package gov.cdc.nedss.webapp.nbs.action.person;

import gov.cdc.nedss.systemservice.nbscontext.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.util.*;

/**
 * Title:        Actions
 * Description:  Handles the denial of a person delete.
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

import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;


public class PersonDeleteDeniedLoad
    extends Action
{
    public PersonDeleteDeniedLoad()
    {
    }

    /**
      * Handles the denial of a person delete.
      * @J2EE_METHOD  --  perform
      * @param mapping       the ActionMapping
      * @param form     the ActionForm
      * @return request    the  HttpServletRequest
      * @return response    the  HttpServletResponse
      * @throws IOException
      * @throws ServletException
      **/
    public ActionForward execute(ActionMapping mapping, ActionForm form,
				 HttpServletRequest request,
				 HttpServletResponse response)
			  throws IOException, ServletException
    {

	HttpSession session = request.getSession(false);

        NBSSecurityObj secObj = (NBSSecurityObj)session.getAttribute("NBSSecurityObject");

        String contextAction = (String)request.getAttribute("ContextAction");

	if (contextAction.equalsIgnoreCase("DeleteDenied"))
	{

          boolean linkHrefPermission = secObj.getPermission(NBSBOLookup.PATIENT, NBSOperationLookup.VIEW);


            System.out.println("linkHref Perm = " + linkHrefPermission);
            request.setAttribute("returnLink", String.valueOf(linkHrefPermission));


	    TreeMap<Object,Object> tm = NBSContext.getPageContext(session, "PS087",
						   contextAction);
	    NBSContext.lookInsideTreeMap(tm);

	    String strUID = request.getParameter("uid");
            Long personUid = (Long)NBSContext.retrieve(session, NEDSSConstants.DSPatientPersonUID);
            strUID = personUid.toString();
            if(strUID != null && strUID.trim().length() > 0)
              request.setAttribute("personUID",strUID);

            String sCurrTask = NBSContext.getCurrentTask(session);

	    request.setAttribute("returnToViewPersonHref",
				 "/nbs/" + sCurrTask +
				 ".do?ContextAction=" +
				 tm.get("ReturnToViewPatient"));

	}
	else
	{
	    session.setAttribute("error",
				     "no context action");

	    throw new ServletException("no context action");
	}
	return mapping.findForward("XSP");
    }
}