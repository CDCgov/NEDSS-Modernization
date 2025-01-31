package gov.cdc.nedss.webapp.nbs.action.observation.patientsearch;


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
import gov.cdc.nedss.webapp.nbs.action.observation.morbidityreport.ViewObservationMorbidityReportSubmit;
import gov.cdc.nedss.webapp.nbs.action.util.ErrorMessageHelper;


/**
 * Title:        Actions
 * Description:  Patient Search Load
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
public class PatientSearchLoad
    extends Action
{
    /**
     * This is the constructor for the PersonSearchLoad class.
     */
    public PatientSearchLoad()
    {
    }

    //For logging
    static final LogUtils logger = new LogUtils(PatientSearchLoad.class.getName());

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

    		if (contextAction.equalsIgnoreCase("GlobalPatient") || contextAction.equalsIgnoreCase("GlobalMP_ManualSearch")||contextAction.equalsIgnoreCase("ReturnToFindPatient") ||
    				contextAction.equalsIgnoreCase("NewSearch"))
    		{

    			// context
    			TreeMap<Object,Object> tm = NBSContext.getPageContext(session, "PS089",
    					contextAction);


    			String sCurrTask = NBSContext.getCurrentTask(session);
    			if(request.getParameter("Mode1")!=null && request.getParameter("Mode1")!="")
    			{
    				request.setAttribute("formHref", "/nbs/" + sCurrTask + ".do?Mode1="+request.getParameter("Mode1"));
    			}
    			else
    			{
    				request.setAttribute("formHref", "/nbs/" + sCurrTask + ".do");
    			}
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
    			request.setAttribute("DSSearchCriteria",
    					NBSContext.retrieve(session,
    							"DSSearchCriteria"));
    		}
    		else if (contextAction.equalsIgnoreCase("EntitySearch"))
    		{

    			request.setAttribute("formHref", "/nbs/FindLabSpecificPatient.do");
    			request.setAttribute("ContextAction", "EntitySearch");
    			request.setAttribute("roleSecurity",
    					String.valueOf(secObj.getPermission(
    							NBSBOLookup.PATIENT,
    							NBSOperationLookup.ROLEADMINISTRATION)));

    			String PersonName = request.getParameter("PersonName");


    			if(PersonName == null) {
    				PersonName = (String)session.getAttribute("PersonName");
    			}

    			request.setAttribute("PersonName", PersonName);

    			request.setAttribute("VOLookup",
    					(request.getParameter("VO") == null
    					? "" : (String)request.getParameter("VO")));




    			String mode=request.getParameter("mode");

    			if(mode==null)
    				request.setAttribute("DSSearchCriteria",session.getAttribute("SearchCriteria"));

    		}
    	}catch (Exception e) {
    		logger.error("Exception in Patient Search Load: " + e.getMessage());
    		e.printStackTrace();
    		throw new ServletException("Patient Search Load : "+e.getMessage());
    	}
	return mapping.findForward("XSP");
    }
}
