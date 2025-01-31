package gov.cdc.nedss.webapp.nbs.action.organization;

import gov.cdc.nedss.entity.organization.vo.OrganizationSearchVO;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.webapp.nbs.form.organization.*;

//for the old way using entity
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;

/**
* Name:		OrganizationSearchSubmit.java
* Description:	This is a action class for the structs implementation
* Copyright:	Copyright (c) 2001
* Company: 	Computer Sciences Corporation
* @author	NEDSS Development Team
* @version	1.0
*/

public class OrganizationSearchSubmit
    extends Action
{

    static final LogUtils logger = new LogUtils(OrganizationSearchSubmit.class.getName());

    public OrganizationSearchSubmit()
    {
    }
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
    public ActionForward execute(ActionMapping mapping, ActionForm form,
				 HttpServletRequest request,
				 HttpServletResponse response)
			  throws IOException, ServletException
    {

	HttpSession session = request.getSession(false);
	NBSSecurityObj secObj = (NBSSecurityObj)session.getAttribute(
					"NBSSecurityObject");
	String contextAction = request.getParameter("ContextAction");
     String OrganizationName  = (String)session.getAttribute("OrganizationName");
     request.setAttribute("OrganizationName",OrganizationName);
	if (contextAction == null)
	    contextAction = (String)request.getAttribute("ContextAction");

	if (contextAction.equalsIgnoreCase("Submit"))
	{

	    OrganizationSearchForm osForm = (OrganizationSearchForm)form;
	    osForm.reset();



	    boolean bAddButton = secObj.getPermission(NBSBOLookup.ORGANIZATION,
						      NBSOperationLookup.MANAGE);
	    request.setAttribute("addButton", String.valueOf(bAddButton));

	    String strCurrentIndex = (String)request.getParameter("currentIndex");
		request.setAttribute("DSFromIndex",strCurrentIndex);


	    return mapping.findForward(contextAction);
	}
		/****************************
	 * NEXT action
	 */
	else if (contextAction.equalsIgnoreCase("Next"))
	{
	    String strCurrentIndex = (String)request.getParameter(
					     "currentIndex");

	    NBSContext.store(session,"DSFromIndex",strCurrentIndex);
	    return mapping.findForward(contextAction);
	}
	else if (contextAction.equalsIgnoreCase("Prev"))
	{
	    String strCurrentIndex = (String)request.getParameter(
					     "currentIndex");

	    NBSContext.store(session,"DSFromIndex",strCurrentIndex);
	    return mapping.findForward(contextAction);
	}
	else if (contextAction.equalsIgnoreCase("Add"))
	{

	    return mapping.findForward(contextAction);
	}
	else if (contextAction.equalsIgnoreCase("RefineSearch"))
	{

	    return mapping.findForward(contextAction);
	}
	else if (contextAction.equalsIgnoreCase("NewSearch"))
	{

	    return mapping.findForward(contextAction);
	}
	else if (contextAction.equalsIgnoreCase("View"))
	{

	    return mapping.findForward(contextAction);
	}
	else if(contextAction.equalsIgnoreCase("EntitySearch"))
	{
		String strCurrentIndex = (String)request.getParameter("currentIndex");

		request.setAttribute("DSFromIndex",strCurrentIndex);
		request.setAttribute("mode",request.getParameter("mode"));



		return mapping.findForward(contextAction);
	}

	return null;
    }

  /**
   * Gets the State Code Description
   * @param sStateCd  the String
   * @return desc   the String of stateCode Description
   */
    private String getStateDescTxt(String sStateCd)
    {

	CachedDropDownValues srtValues = new CachedDropDownValues();
	TreeMap<?,?> treemap = srtValues.getStateCodes1("USA");
	String desc = "";

	if (sStateCd != null && treemap.get(sStateCd) != null)
	    desc = (String)treemap.get(sStateCd);

	return desc;
    }
}