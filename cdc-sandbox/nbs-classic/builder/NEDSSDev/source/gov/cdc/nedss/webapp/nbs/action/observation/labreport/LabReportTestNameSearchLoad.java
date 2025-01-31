package gov.cdc.nedss.webapp.nbs.action.observation.labreport;


import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.systemservice.nbscontext.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.*;

import gov.cdc.nedss.webapp.nbs.form.person.*;
import gov.cdc.nedss.entity.person.vo.*;

//for the old way using entity
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;
import gov.cdc.nedss.webapp.nbs.action.util.ErrorMessageHelper;


/**
 * Title:        Actions
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      CSC
 * @author       Pradeep Kumar Sharma
 * @version 1.0
 */
import java.io.*;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;


/**
 * This is the action class that is used to
 * prepare the Test Name Search page for use.
 */
public class LabReportTestNameSearchLoad
    extends Action
{
    /**
     * This is the constructor for the LabReportTestNameSearchLoad class.
     */
    public LabReportTestNameSearchLoad()
    {
    }
    static final LogUtils logger = new LogUtils(LabReportTestNameSearchLoad.class.getName());
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
    	try {
    		HttpSession session = request.getSession(false);
    		NBSSecurityObj secObj = (NBSSecurityObj)session.getAttribute(
    				"NBSSecurityObject");
    		String strType = request.getParameter("OperationType");

    		if (strType == null)
    			strType = (String)request.getAttribute("OperationType");

    		String contextAction = request.getParameter("ContextAction");

    		if (contextAction == null)
    			contextAction = (String)request.getAttribute("ContextAction");

    		ErrorMessageHelper.setErrMsgToRequest(request, "ps089");

    		if (contextAction.equalsIgnoreCase("GlobalPerson") ||
    				contextAction.equalsIgnoreCase("NewSearch"))
    		{

    			// context
    			TreeMap<Object,Object> tm = NBSContext.getPageContext(session, "PS089",
    					contextAction);


    			String sCurrTask = NBSContext.getCurrentTask(session);
    			request.setAttribute("formHref", "/nbs/" + sCurrTask + ".do");

    			request.setAttribute("ContextAction", tm.get("Submit"));

    			PersonSearchForm psForm = (PersonSearchForm)form;
    			psForm.reset();

    			boolean bStatusCheckbox = secObj.getPermission(NBSBOLookup.PATIENT,
    					NBSOperationLookup.FINDINACTIVE);
    			request.setAttribute("sec-status", String.valueOf(bStatusCheckbox));
    		}
    		else if (contextAction.equalsIgnoreCase("RefineSearch"))
    		{

    			TreeMap<Object,Object> tm = NBSContext.getPageContext(session, "PS089",
    					contextAction);

    			String sCurrTask = NBSContext.getCurrentTask(session);
    			request.setAttribute("formHref", "/nbs/" + sCurrTask + ".do");

    			request.setAttribute("ContextAction", tm.get("Submit"));

    			boolean bStatusCheckbox = secObj.getPermission(NBSBOLookup.PATIENT,
    					NBSOperationLookup.FINDINACTIVE);
    			request.setAttribute("sec-status", String.valueOf(bStatusCheckbox));
    			request.setAttribute("DSSearchCriteria", NBSContext.retrieve(session, "DSSearchCriteria"));
    		}
    		else if (contextAction.equalsIgnoreCase("EntitySearch"))
    		{

    			request.setAttribute("formHref", "/nbs/FindLabReportTestName3.do?type=" + request.getParameter("type"));
    			request.setAttribute("ContextAction", "EntitySearch");
    			request.setAttribute("roleSecurity",String.valueOf(secObj.getPermission(NBSBOLookup.PATIENT, NBSOperationLookup.ROLEADMINISTRATION)));

    			String PersonName = request.getParameter("type");
    			if(PersonName == null) {
    				PersonName = (String)session.getAttribute("type");
    			}

    			if (request.getParameter("method") != null || request.getParameter("method") != ""){
    				request.setAttribute("method", request.getParameter("method"));
    			}

    			if (request.getParameter("Org-ReportingOrganizationUID") != null || request.getParameter("Org-ReportingOrganizationUID") != ""){
    				request.setAttribute("Org-ReportingOrganizationUID", request.getParameter("Org-ReportingOrganizationUID"));
    			}


    			if (request.getParameter("labId") != null || request.getParameter("labId") != ""){
    				request.setAttribute("labId", request.getParameter("labId"));
    			}

    			if (request.getParameter("SearchType") != null || request.getParameter("SearchType") != ""){
    				request.setAttribute("SearchType", request.getParameter("SearchType"));
    			}

    			request.setAttribute("PersonName", PersonName);
    			String mode=request.getParameter("mode");

    			if(mode==null || mode.length() == 0)
    				request.setAttribute("DSSearchCriteria",session.getAttribute("SearchCriteria"));

    			if (request.getParameter("SearchString") != null)
    			{
    				request.setAttribute("SearchString", request.getParameter("SearchString"));
    			}
    		}
    	}catch (Exception e) {
    		logger.error("Exception in Lab Report Test Name Search Load: " + e.getMessage());
    		e.printStackTrace();
    		throw new ServletException("Error occurred in Lab Report Test Name Search Load: "+e.getMessage());
    	}  
	return mapping.findForward("XSP");
    }
}
