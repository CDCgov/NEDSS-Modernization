package gov.cdc.nedss.webapp.nbs.action.organization;

import gov.cdc.nedss.entity.organization.vo.OrganizationSearchVO;
import gov.cdc.nedss.proxy.ejb.entityproxyejb.bean.EntityProxyHome;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.webapp.nbs.form.organization.*;
import gov.cdc.nedss.entity.organization.vo.OrganizationSrchResultVO;

//for the old way using entity
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;

/**
* Name:		OrganizationSearchResultLoad.java
* Description:	This is a action class for the structs implementation
* Copyright:	Copyright (c) 2001
* Company: 	Computer Sciences Corporation
* @author	NEDSS Development Team
* @version	1.0
*/

public class OrganizationSearchResultsLoad
    extends Action
{

    static final LogUtils logger = new LogUtils(OrganizationSearchResultsLoad.class.getName());

    public OrganizationSearchResultsLoad()
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
    	try {
    		PropertyUtil propertyUtil= PropertyUtil.getInstance();
    		HttpSession session = request.getSession(false);
    		NBSSecurityObj secObj = (NBSSecurityObj)session.getAttribute(
    				"NBSSecurityObject");
    		String contextAction = request.getParameter("ContextAction");
    		String OrganizationName  = (String)session.getAttribute("OrganizationName");
    		request.setAttribute("OrganizationName",OrganizationName);
    		if (contextAction == null)
    			contextAction = (String)request.getAttribute("ContextAction");

    		if (contextAction.equalsIgnoreCase("Submit") ||
    				(contextAction.equalsIgnoreCase("EntitySearch") &&
    						request.getParameter("mode") == null))
    		{

    			if (contextAction.equalsIgnoreCase("EntitySearch"))
    			{
    				request.setAttribute("refineSearchHref",
    						"/nbs/LoadFindOrganization3.do?ContextAction=EntitySearch");
    				request.setAttribute("newSearchHref",
    						"/nbs/LoadFindOrganization3.do?ContextAction=EntitySearch&mode=new");
    				request.setAttribute("nextHref",
    						"/nbs/FindOrganization3.do?ContextAction=EntitySearch&mode=next");
    				request.setAttribute("prevHref",
    						"/nbs/FindOrganization3.do?ContextAction=EntitySearch&mode=next");
    			}
    			else
    			{

    				TreeMap<Object,Object> tm = NBSContext.getPageContext(session, "PS101",
    						contextAction);
    				String sCurrTask = NBSContext.getCurrentTask(session);
    				NBSContext.lookInsideTreeMap(tm);
    				request.setAttribute("addButtonHref",
    						"/nbs/" + sCurrTask +
    						".do?ContextAction=" + tm.get("Add"));
    				request.setAttribute("refineSearchHref",
    						"/nbs/" + sCurrTask +
    						".do?ContextAction=" +
    						tm.get("RefineSearch"));
    				request.setAttribute("newSearchHref",
    						"/nbs/" + sCurrTask +
    						".do?ContextAction=" +
    						tm.get("NewSearch"));
    				request.setAttribute("viewHref",
    						"/nbs/" + sCurrTask +
    						".do?ContextAction=" + tm.get("View"));
    				request.setAttribute("nextHref",
    						"/nbs/" + sCurrTask +
    						".do?ContextAction=" + tm.get("Next"));
    				request.setAttribute("prevHref",
    						"/nbs/" + sCurrTask +
    						".do?ContextAction=" + tm.get("Prev"));
    			}

    			ArrayList<Object> organizationList = new ArrayList<Object> ();
    			OrganizationSearchForm osForm = (OrganizationSearchForm)form;
    			OrganizationSearchVO osVO = (OrganizationSearchVO)osForm.getOrganizationSearch();
    			NBSContext.store(session, "DSSearchCriteria", osVO);
    			session.setAttribute("SearchCriteria", osVO);

    			if (osVO != null)
    			{

    				NedssUtils nedssUtils = null;
    				MainSessionCommand msCommand = null;
    				String sBeanJndiName = "";
    				String sMethod = "";
    				Object[] oParams = null;
    				session.setAttribute("organizationFind", osVO);
    				sBeanJndiName = JNDINames.ENTITY_PROXY_EJB;
    				sMethod = "findOrganization";
    				oParams = new Object[]
    						{
    						osVO, new Integer(propertyUtil.getNumberOfRows()),
    						new Integer(0)
    						};
    				logger.debug(
    						"HHHHHHHHHHHHHHHHHHH" +
    								propertyUtil.getNumberOfRows());

    				try
    				{

    					MainSessionHolder holder = new MainSessionHolder();
    					msCommand = holder.getMainSessionCommand(session);

    					ArrayList<?> arrList = msCommand.processRequest(sBeanJndiName,
    							sMethod,
    							oParams);

    					if (arrList.isEmpty())
    						logger.error("fdsdfsdfsdfsdf size  " +
    								arrList.size());

    					organizationList = (ArrayList<Object> )arrList.get(0);
    				}
    				catch (Exception e)
    				{
    					e.printStackTrace();
    				}

    				//session.setAttribute("result", organizationList);
    				// session.setAttribute("OrganizationSearchCollection",
    				//                      organizationList);
    				NBSContext.store(session, "DSSearchResults", organizationList);
    				request.setAttribute("DSSearchResults",
    						NBSContext.retrieve(session,
    								"DSSearchResults"));

    				//  build the n  string
    				StringBuffer sQuery = new StringBuffer("");
    				CachedDropDownValues cache = new CachedDropDownValues();

    				if (osVO.getNmTxt() != null && !osVO.getNmTxt().equals(""))
    					sQuery.append("Name").append(
    							" " +
    									cache.getDescForCode("SEARCH_SNDX",
    											osVO.getNmTxtOperator()) +
    							" ").append("'" + osVO.getNmTxt() + "'").append(
    									", ");

    				if (osVO.getStreetAddr1() != null &&
    						!osVO.getStreetAddr1().equals(""))
    					sQuery.append("Street Address").append(
    							" " +
    									cache.getDescForCode("SEARCH_SNDX",
    											osVO.getStreetAddr1Operator()) +
    							" ").append("'" + osVO.getStreetAddr1() + "'").append(
    									", ");

    				if (osVO.getCityDescTxt() != null &&
    						!osVO.getCityDescTxt().equals(""))
    					sQuery.append("City").append(
    							" " +
    									cache.getDescForCode("SEARCH_SNDX",
    											osVO.getCityDescTxtOperator()) +
    							" ").append("'" + osVO.getCityDescTxt() + "'").append(
    									", ");

    				if (osVO.getStateCd() != null &&
    						!osVO.getStateCd().equals(""))
    					sQuery.append("State Equal ").append(
    							"'" + getStateDescTxt(osVO.getStateCd()) + "'").append(
    									", ");

    				if (osVO.getZipCd() != null && !osVO.getZipCd().equals(""))
    					sQuery.append("Zip Code Equal").append(
    							" ").append("'" + osVO.getZipCd() + "'").append(
    									", ");
    				if (osVO.getPhoneNbrTxt() != null &&
    						!osVO.getPhoneNbrTxt().equals(""))
    					sQuery.append("Phone Number Contains ").append(
    							"'" + osVO.getPhoneNbrTxt() + "'").append(", ");


    				if (osVO.getLocalID() != null &&
    						!osVO.getLocalID().equals(""))
    					sQuery.append("Organization ID Equal ").append(
    							"'" + osVO.getLocalID() + "'").append(", ");

    				if (osVO.getRootExtensionTxt() != null &&
    						!osVO.getRootExtensionTxt().equals(""))
    				{

    					if (osVO.getTypeCd() != null &&
    							!osVO.getTypeCd().equals(""))
    						sQuery.append("ID Type Equal ").append(
    								"'" +
    										cache.getDescForCode("EI_TYPE_ORG",
    												osVO.getTypeCd()) +
    								"' ,").append("ID Value Equal ").append(
    										"'" + osVO.getRootExtensionTxt() + "'").append(
    												", ");
    				}

    				if (osVO.getRoleCd() != null &&
    						!osVO.getRoleCd().equals(""))
    					sQuery.append("Role Equal ").append(
    							"'" +
    									cache.getDescForCode("RL_TYPE_ORG", osVO.getRoleCd()) +
    							"'").append(", ");

    				session.setAttribute("searchCriteria", sQuery.toString());

    				String strCurrentIndex = (String)request.getParameter(
    						"currentIndex");
    				return mapping.findForward("XSP");
    			}
    		}

    		//return mapping.findForward("error");
    		//}
    		else if (contextAction.equalsIgnoreCase("Next") ||
    				contextAction.equalsIgnoreCase("Prev") ||
    				contextAction.equalsIgnoreCase("ReturnToSearchResults") ||
    				contextAction.equalsIgnoreCase("Cancel") ||
    				contextAction.equalsIgnoreCase("EntitySearch"))
    		{

    			if (contextAction.equalsIgnoreCase("EntitySearch"))
    			{
    				request.setAttribute("refineSearchHref",
    						"/nbs/LoadFindOrganization3.do?ContextAction=EntitySearch");
    				request.setAttribute("newSearchHref",
    						"/nbs/LoadFindOrganization3.do?ContextAction=EntitySearch&mode=new");
    				request.setAttribute("nextHref",
    						"/nbs/FindOrganization3.do?ContextAction=EntitySearch&mode=next");
    				request.setAttribute("prevHref",
    						"/nbs/FindOrganization3.do?ContextAction=EntitySearch&mode=next");
    			}
    			else
    			{

    				TreeMap<Object,Object> tm = NBSContext.getPageContext(session, "PS101",
    						contextAction);
    				String sCurrTask = NBSContext.getCurrentTask(session);

    				boolean bAddButton = secObj.getPermission(NBSBOLookup.ORGANIZATION,
    						NBSOperationLookup.MANAGE);
    				request.setAttribute("addButton", String.valueOf(bAddButton));

    				request.setAttribute("addButtonHref",
    						"/nbs/" + sCurrTask +
    						".do?ContextAction=" + tm.get("Add"));
    				request.setAttribute("refineSearchHref",
    						"/nbs/" + sCurrTask +
    						".do?ContextAction=" +
    						tm.get("RefineSearch"));
    				request.setAttribute("newSearchHref",
    						"/nbs/" + sCurrTask +
    						".do?ContextAction=" +
    						tm.get("NewSearch"));
    				request.setAttribute("viewHref",
    						"/nbs/" + sCurrTask +
    						".do?ContextAction=" + tm.get("View"));
    				request.setAttribute("nextHref",
    						"/nbs/" + sCurrTask +
    						".do?ContextAction=" + tm.get("Next"));
    				request.setAttribute("prevHref",
    						"/nbs/" + sCurrTask +
    						".do?ContextAction=" + tm.get("Prev"));
    			}

    			request.setAttribute("DSSearchResults",
    					NBSContext.retrieve(session,
    							"DSSearchResults"));


    			return mapping.findForward("XSP");
    		}
    	}catch (Exception e) {
    		logger.error("Exception in Organization Search Results Load: " + e.getMessage());
    		e.printStackTrace();
    		throw new ServletException("General error occurred in Organization Search Results Load: "+e.getMessage());
    	} 
        throw new ServletException();
    }
 /**
   * Gets the state description based on the StateCd
   * @param sStateCd   the String
   * @return   string
   */
    private String getStateDescTxt(String sStateCd)
    {

        CachedDropDownValues srtValues = new CachedDropDownValues();
        TreeMap<Object,Object> treemap = srtValues.getStateCodes2("USA");
        String desc = "";

        if (sStateCd != null && treemap.get(sStateCd) != null)
            desc = (String)treemap.get(sStateCd);

        return desc;
    }
}