package gov.cdc.nedss.webapp.nbs.action.person;

/**
 * Title:        MergePersonsEnterLoad
 * Description:  This class interacts with the user and retrieves and submits to the back end to Merge Persons.
 * Copyright:    Copyright (c) 2001
 * Company:      CSC
 * @author Karthik Murthy
 * @version 1.0
 */

import java.io.*;
import java.util.*;
import javax.servlet.http.*;
import javax.servlet.*;
import org.apache.struts.action.*;

import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.systemservice.nbscontext.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.*;


//for the old way using entity
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

public class MergePersonsEnterLoad extends Action {

	//For logging
	static final LogUtils logger = new LogUtils(MergePersonsEnterLoad.class.getName());

	public MergePersonsEnterLoad() {
	}

	/**
      * Handles the identifying of the two records to be merged.
      * @J2EE_METHOD  --  perform
      * @param mapping       the ActionMapping
      * @param form     the ActionForm
      * @return request    the  HttpServletRequest
      * @return response    the  HttpServletResponse
      * @throws IOException
      * @throws ServletException
      **/
	public ActionForward execute(ActionMapping mapping,
				 ActionForm form,
				 HttpServletRequest request,
				 HttpServletResponse response)
	throws IOException, ServletException
	{

	  	logger.debug("inside the MergePersonsEnterLoad Load");

		HttpSession session = request.getSession(false);
		NBSSecurityObj secObj = (NBSSecurityObj)session.getAttribute(
						"NBSSecurityObject");

		String contextAction = request.getParameter("ContextAction");

		if (contextAction == null) {
			contextAction = (String)request.getAttribute("ContextAction");
		}

	    //context
	    TreeMap<Object,Object> tm = NBSContext.getPageContext(session, "PS043",
						   contextAction);

	    String sCurrTask = NBSContext.getCurrentTask(session);
	    request.setAttribute("formHref", "/nbs/" + sCurrTask + ".do");
	    request.setAttribute("ContextAction", tm.get("Submit"));
	    request.setAttribute("cancelButtonHref",
				 "/nbs/" + sCurrTask +
				 ".do?ContextAction=" + tm.get("Cancel"));


	    boolean bStatusSubmitButton = secObj.getPermission(NBSBOLookup.PATIENT,
							   NBSOperationLookup.MERGE);
	    request.setAttribute("sec-status", String.valueOf(bStatusSubmitButton));

		return mapping.findForward("XSP");

	}

}