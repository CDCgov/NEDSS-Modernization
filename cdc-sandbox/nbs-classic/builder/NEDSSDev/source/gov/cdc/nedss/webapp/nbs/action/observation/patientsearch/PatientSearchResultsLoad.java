package gov.cdc.nedss.webapp.nbs.action.observation.patientsearch;


import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.systemservice.nbscontext.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.webapp.nbs.action.util.ErrorMessageHelper;
import gov.cdc.nedss.webapp.nbs.form.person.*;
import gov.cdc.nedss.entity.person.vo.*;

//for the old way using entity
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;



import java.io.*;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;


/**
 * This class is for preparing the Person Search Results page
 * for display of search results.
 */
public class PatientSearchResultsLoad
    extends Action
{
    /**
     * This is the constructor for the PersonSearchResultsLoad
     * class
     */
    public PatientSearchResultsLoad()
    {
    }
  //For logging
    static final LogUtils logger = new LogUtils(PatientSearchResultsLoad.class.getName());
    /**
     * This method is controls the execution of the
     * PersonSearchResultsLoad logic, and dictates
     * the navigation.
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
    		String contextAction = request.getParameter("ContextAction");

    		if (contextAction == null)
    			contextAction = (String)request.getAttribute("ContextAction");


    		/***************************************************
    		 * SUBMIT ACTION
    		 */
    		if (contextAction.equalsIgnoreCase("Submit") ||
    				contextAction.equalsIgnoreCase("Next") ||
    				contextAction.equalsIgnoreCase("Prev") ||
    				contextAction.equalsIgnoreCase("ReturnToSearchResults") ||
    				contextAction.equalsIgnoreCase("Cancel"))// ||
    			//contextAction.equalsIgnoreCase("EntitySearch"))
    		{

    			TreeMap<Object,Object> tm = NBSContext.getPageContext(session, "PS090",
    					contextAction);
    			ErrorMessageHelper.setErrMsgToRequest(request, "PS090");
    			String sCurrTask = NBSContext.getCurrentTask(session);
    			NBSContext.lookInsideTreeMap(tm);

    			// add button security
    			boolean bAddButton = secObj.getPermission(NBSBOLookup.PATIENT,
    					NBSOperationLookup.ADD);
    			request.setAttribute("addButton", String.valueOf(bAddButton));

    			if(secObj.getPermission(NBSBOLookup.PATIENT, NBSOperationLookup.ADD))
    			{
    				request.setAttribute("addButtonHref",
    						"/nbs/" + sCurrTask +
    						".do?ContextAction=" + tm.get("Add"));
    			}
    			request.setAttribute("refineSearchHref",
    					"/nbs/" + sCurrTask +
    					".do?ContextAction=" +
    					tm.get("RefineSearch"));
    			request.setAttribute("newSearchHref",
    					"/nbs/" + sCurrTask +
    					".do?ContextAction=" + tm.get("NewSearch"));
    			request.setAttribute("viewHref",
    					"/nbs/" + sCurrTask +
    					".do?ContextAction=" + tm.get("View"));

    			request.setAttribute("viewFileHref",
    					"/nbs/" + sCurrTask +
    					".do?ContextAction=" + tm.get("ViewFile"));
    			request.setAttribute("viewHref",
    					"/nbs/" + sCurrTask +
    					".do?ContextAction=" + tm.get("View"));
    			request.setAttribute("nextHref",
    					"/nbs/" + sCurrTask +
    					".do?ContextAction=" + tm.get("Next"));
    			request.setAttribute("prevHref",
    					"/nbs/" + sCurrTask +
    					".do?ContextAction=" + tm.get("Prev"));

    			// retrieve the from index if there is one
    			try
    			{
    				request.setAttribute("DSFromIndex",
    						NBSContext.retrieve(session,
    								"DSFromIndex"));
    			}
    			catch (Exception e)
    			{

    				// OK
    			}

    		}
    		/*********************************************************
    		 * ENTITY SEARCH
    		 */
    		else if (contextAction.equalsIgnoreCase("EntitySearch"))
    		{


    			request.setAttribute("refineSearchHref",
    					"/nbs/LoadLabSpecificPatient.do?ContextAction=EntitySearch");
    			request.setAttribute("newSearchHref",
    					"/nbs/LoadLabSpecificPatient.do?ContextAction=EntitySearch&mode=new");
    			request.setAttribute("nextHref",
    					"/nbs/FindLabSpecificPatient.do?ContextAction=EntitySearch&mode=Next");
    			request.setAttribute("prevHref",
    					"/nbs/FindLabSpecificPatient.do?ContextAction=EntitySearch&mode=Next");


    			if(session.getAttribute("DSSearchResults") != null)
    			{
    				request.setAttribute("DSSearchResults",session.getAttribute("DSSearchResults"));
    			}

    		}

    	}catch (Exception e) {
    		logger.error("Exception in Patient Search Results Load : " + e.getMessage());
    		e.printStackTrace();
    		throw new ServletException("Patient Search Results Load: "+e.getMessage());
    	}
	return mapping.findForward("XSP");
    }


}
