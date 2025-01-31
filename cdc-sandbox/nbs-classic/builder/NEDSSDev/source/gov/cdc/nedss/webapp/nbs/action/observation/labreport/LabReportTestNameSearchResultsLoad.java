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



import java.io.*;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;


/**
 * Title:        Actions
 * Description:  This class is for preparing the Lab Report Test Name Search Results page
 *               for display of search results.
 * Copyright:    Copyright (c) 2001
 * Company:      CSC
 * @author       Pradeep Kumar Sharma
 * @version 1.0
 */


public class LabReportTestNameSearchResultsLoad
    extends Action
{
    /**
     * This is the constructor for the LabReportTestNameSearchResultsLoad
     * class
     */
    public LabReportTestNameSearchResultsLoad()
    {
    }
    static final LogUtils logger = new LogUtils(LabReportTestNameSearchResultsLoad.class.getName());
    /**
     * This method is controls the execution of the
     * LabReportTestNameSearchResultsLoad logic, and dictates
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
    		String searchType = request.getParameter("SearchType");
    		String searchSubject = request.getParameter("SearchSubject");

    		if (contextAction == null)
    			contextAction = (String)request.getAttribute("ContextAction");

    		if (searchType == null)
    			searchType = (String)request.getAttribute("SearchType");
    		if (searchType == null)
    			searchType = "none";

    		if (searchSubject == null)
    			searchSubject = (String)request.getAttribute("SearchSubject");


    		/***************************************************
    		 * SUBMIT ACTION
    		 */
    		if (contextAction.equalsIgnoreCase("Submit") ||
    				contextAction.equalsIgnoreCase("Next") ||
    				contextAction.equalsIgnoreCase("Prev") ||
    				contextAction.equalsIgnoreCase("ReturnToSearchResults") ||
    				contextAction.equalsIgnoreCase("Cancel"))
    		{

    			TreeMap<Object,Object> tm = NBSContext.getPageContext(session, "PS090",
    					contextAction);
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
    			request.setAttribute("refineSearchHref","/nbs/" + sCurrTask + ".do?ContextAction=" + tm.get("RefineSearch"));
    			request.setAttribute("newSearchHref","/nbs/" + sCurrTask +".do?ContextAction=" + tm.get("NewSearch"));

    			request.setAttribute("viewHref",
    					"/nbs/" + sCurrTask +
    					".do?ContextAction=" + tm.get("View"));
    			request.setAttribute("nextHref",
    					"/nbs/" + sCurrTask +
    					".do?ContextAction=" + tm.get("Next") + "&SearchType=" + searchType + "&SearchSubject=" + searchSubject);
    			request.setAttribute("prevHref",
    					"/nbs/" + sCurrTask +
    					".do?ContextAction=" + tm.get("Prev") + "&SearchType=" + searchType + "&SearchSubject=" + searchSubject);

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

    			request.setAttribute("DSSearchResults",
    					NBSContext.retrieve(session,
    							"DSSearchResults"));
    		}
    		/*********************************************************
    		 * ENTITY SEARCH
    		 */
    		else if (contextAction.equalsIgnoreCase("EntitySearch"))
    		{


    			String searchString = "";
    			if (request.getAttribute("SearchString") != null)
    			{
    				searchString = (String)request.getAttribute("SearchString");
    			}
    			else if (request.getParameter("SearchString") != null)
    			{
    				searchString = (String)request.getParameter("SearchString");
    			}
    			
    			searchString=NedssUtils.translateSpecialCharacterUrl(searchString);
    			
    			String name = "";

    			if (request.getAttribute("PersonName") != null)
    			{
    				name = (String)request.getAttribute("PersonName");
    			}
    			else if (request.getParameter("PersonName") != null)
    			{
    				name = (String)request.getParameter("PersonName");
    			}

    			request.setAttribute("PersonName", name);



    			request.setAttribute("newSearchHref","/nbs/LoadFindLabReportTestName3.do?ContextAction=EntitySearch&mode=new&method=" + request.getAttribute("method") + "&Org-ReportingOrganizationUID=" + request.getParameter("Org-ReportingOrganizationUID") + "&labId=" + request.getParameter("labId") + "&SearchSubject=" + searchSubject + "&type=" + request.getParameter("type"));
    			request.setAttribute("refineSearchHref","/nbs/LoadFindLabReportTestName3.do?ContextAction=EntitySearch&method=" + request.getParameter("method") + "&Org-ReportingOrganizationUID=" + request.getParameter("Org-ReportingOrganizationUID") + "&labId=" + request.getParameter("labId") + "&SearchType=" + searchType + "&SearchSubject=" + searchSubject + "&type=" + request.getParameter("type") + "&SearchString=" + searchString + "&PersonName= " + name);


    			request.setAttribute("nextHref", "/nbs/FindLabReportTestName3.do?ContextAction=EntitySearch&mode=Next&method=" + request.getAttribute("method") + "&Org-ReportingOrganizationUID=" + request.getParameter("Org-ReportingOrganizationUID") + "&labId=" + request.getParameter("labId") + "&SearchType=" + searchType + "&SearchSubject=" + searchSubject + "&type=" + request.getParameter("type") + "&SearchString=" + searchString + "&PersonName= " + name);
    			request.setAttribute("prevHref","/nbs/FindLabReportTestName3.do?ContextAction=EntitySearch&mode=Next&method=" + request.getAttribute("method") + "&Org-ReportingOrganizationUID=" + request.getParameter("Org-ReportingOrganizationUID") + "&labId=" + request.getParameter("labId") + "&SearchType=" + searchType + "&SearchSubject=" + searchSubject + "&type=" + request.getParameter("type")  + "&SearchString=" + searchString + "&PersonName= " + name);


    			
    			request.setAttribute("SearchString", searchString);


    			request.setAttribute("DSSearchResults",
    					NBSContext.retrieve(session,
    							"DSSearchResults"));
    		}

    		if (searchType.equals(NEDSSConstants.TEST_TYPE_LOINC))
    		{
    			if ( (searchSubject.equals("Ordered")) || (searchSubject.equals("Resulted")) )
    				return mapping.findForward("TESTLOINC");
    			else
    				return mapping.findForward("DRUGLOINC");
    		}else{
    			return mapping.findForward("XSP");
    		}

    	}catch (Exception e) {
    		logger.error("Exception in Lab Report Test Name: " + e.getMessage());
    		e.printStackTrace();
    		throw new ServletException("Error occurred in Lab Report Test Name: "+e.getMessage());
    	}  

    }
    	
}
