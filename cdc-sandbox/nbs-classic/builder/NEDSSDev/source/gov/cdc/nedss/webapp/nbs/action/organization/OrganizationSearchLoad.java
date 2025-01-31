package gov.cdc.nedss.webapp.nbs.action.organization;

import java.io.*;
import java.util.*;
import javax.servlet.http.*;
import javax.servlet.*;

import org.apache.struts.action.*;

import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;

import gov.cdc.nedss.entity.organization.vo.OrganizationSearchVO;
import gov.cdc.nedss.entity.organization.util.OrganizationList;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.webapp.nbs.action.util.*;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;
import gov.cdc.nedss.webapp.nbs.form.organization.*;

//for the old way using entity
import gov.cdc.nedss.util.PropertyUtil;

/**
* Name:		OrganizationSearchLoad.java
* Description:	This is a action class for the structs implementation
* Copyright:	Copyright (c) 2001
* Company: 	Computer Sciences Corporation
* @author	NEDSS Development Team
* @version	1.0
*/


public class OrganizationSearchLoad extends Action {

  public OrganizationSearchLoad() {
  }
  static final LogUtils logger = new LogUtils(OrganizationSearchLoad.class.getName());

/**
 * Based on the context action setting certain values and redirecting to the appropriate page
 * @param mapping   the ActionMapping
 * @param form    the  ActionForm
 * @param request   the HttpServletRequest
 * @param response    the HttpServletResponse
 * @return   ActionForward
 * @throws IOException
 * @throws ServletException
 */

  public ActionForward execute(ActionMapping mapping,
				 ActionForm form,
				 HttpServletRequest request,
				 HttpServletResponse response)
   throws IOException, ServletException
   {
      HttpSession session = request.getSession(false);

      try {


    	  NBSSecurityObj secObj = (NBSSecurityObj)session.getAttribute("NBSSecurityObject");

    	  if(request.getParameter("OrganizationName")!=null && !request.getParameter("OrganizationName").equals(""))
    		  session.setAttribute("OrganizationName",request.getParameter("OrganizationName"));
    	  String OrganizationName = (String)session.getAttribute("OrganizationName");

    	  String strType = request.getParameter("OperationType");
    	  if (strType == null)
    		  strType = (String) request.getAttribute("OperationType");

    	  String contextAction = request.getParameter("ContextAction");
    	  if (contextAction == null)
    		  contextAction = (String) request.getAttribute("ContextAction");

    	  if (contextAction.equalsIgnoreCase("GlobalOrganization") || contextAction.equalsIgnoreCase("NewSearch")){
    		  // context
    		  TreeMap<Object,Object> tm = NBSContext.getPageContext(session,"PS102",contextAction);
    		  NBSContext.lookInsideTreeMap(tm);

    		  /**
    		   * Added for the error Messages
    		   */
    		  ErrorMessageHelper.setErrMsgToRequest(request, "PS102");

    		  String sCurrTask = NBSContext.getCurrentTask(session);
    		  if(sCurrTask.startsWith("FindOrganization1"))
    		  {
    			  session.setAttribute("OrganizationName", null);
    			  OrganizationName = (String)session.getAttribute("OrganizationName");
    		  }
    		  request.setAttribute("OrganizationName", OrganizationName);
    		  request.setAttribute("formHref", "/nbs/"+sCurrTask+".do");
    		  request.setAttribute("ContextAction",tm.get("Submit"));

    		  OrganizationSearchForm osForm = (OrganizationSearchForm) form;
    		  osForm.reset();
    		 

    	  }
    	  else if(contextAction.equalsIgnoreCase("RefineSearch")){
    		  TreeMap<Object,Object> tm = NBSContext.getPageContext(session,"PS102",contextAction);
    		  NBSContext.lookInsideTreeMap(tm);

    		  /**
    		   * Added for the error Messages
    		   */
    		  ErrorMessageHelper.setErrMsgToRequest(request, "PS102");
    		  request.setAttribute("OrganizationName", OrganizationName);
    		  request.setAttribute("DSSearchCriteria", NBSContext.retrieve(session,"DSSearchCriteria"));

    		  String sCurrTask = NBSContext.getCurrentTask(session);
    		  request.setAttribute("formHref", "/nbs/"+sCurrTask+".do");
    		  request.setAttribute("ContextAction",tm.get("Submit"));

    	  }

    	  else if (contextAction.equalsIgnoreCase("EntitySearch"))
    	  {
    		  request.setAttribute("formHref", "/nbs/FindOrganization3.do");
    		  request.setAttribute("ContextAction", "EntitySearch");

    		  request.setAttribute("OrganizationName", OrganizationName);

    		  String mode=request.getParameter("mode");
    		  if(mode==null)
    			  request.setAttribute("DSSearchCriteria",session.getAttribute("SearchCriteria"));
    	  }


      }catch (Exception e) {
    	  logger.error("Exception in Organization Search Load: " + e.getMessage());
    	  e.printStackTrace();
    	  throw new ServletException("General error occurred in Organization Search Load : "+e.getMessage());
      } 
	return mapping.findForward("XSP");


      }


}