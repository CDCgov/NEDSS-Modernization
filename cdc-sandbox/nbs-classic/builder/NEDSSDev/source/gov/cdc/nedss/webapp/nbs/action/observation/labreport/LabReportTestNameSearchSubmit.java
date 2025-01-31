package gov.cdc.nedss.webapp.nbs.action.observation.labreport;

import gov.cdc.nedss.proxy.ejb.observationproxyejb.bean.ObservationProxyHome;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.systemservice.nbscontext.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.webapp.nbs.form.person.*;
import gov.cdc.nedss.entity.person.vo.*;
import gov.cdc.nedss.webapp.nbs.form.observation.*;
import gov.cdc.nedss.entity.observation.vo.*;
//for the old way using entity
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;



/**
 * Title:        Actions
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      CSC
 * @author      Pradeep Kumar Sharma
 * @version 1.0
 */
import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;


public class LabReportTestNameSearchSubmit
    extends Action
{
    public LabReportTestNameSearchSubmit()
    {
    }
    static final LogUtils logger = new LogUtils(LabReportTestNameSearchSubmit.class.getName());
    
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

    		String PersonName  = request.getParameter("perTitle");
    		request.setAttribute("PersonName", PersonName);
    		session.setAttribute("PersonName",PersonName);

    		/*****************************
    		 * SUBMIT ACTION
    		 */

    		String mode = request.getParameter("mode");
    		if( (contextAction.equalsIgnoreCase("Submit") || contextAction.equalsIgnoreCase("EntitySearch") ) && mode == null)
    		{


    			findObservation(form, session, request, false);

    			// add button security
    			boolean bAddButton = secObj.getPermission(NBSBOLookup.PATIENT,
    					NBSOperationLookup.ADD);
    			request.setAttribute("addButton", String.valueOf(bAddButton));

    			String strCurrentIndex = (String)request.getParameter("currentIndex");
    			////##!! System.out.println("waht is the current index - submity    " + strCurrentIndex);

    			request.setAttribute("DSFromIndex",strCurrentIndex);
    			request.setAttribute("mode",request.getParameter("mode"));

    			return mapping.findForward(contextAction);

    		}
    		/****************************
    		 * NEXT action
    		 */
    		else if (contextAction.equalsIgnoreCase("Next")  && mode == null)
    		{
    			String strCurrentIndex = (String)request.getParameter(
    					"currentIndex");

    			NBSContext.store(session,"DSFromIndex",strCurrentIndex);
    			return mapping.findForward(contextAction);
    		}
    		/*****************************************
    		 * PREVIOUS ACTION
    		 */
    		else if (contextAction.equalsIgnoreCase("Prev"))
    		{
    			String strCurrentIndex = (String)request.getParameter(
    					"currentIndex");

    			NBSContext.store(session,"DSFromIndex",strCurrentIndex);
    			return mapping.findForward(contextAction);
    		}

			/****************************
    		 * ADD action
    		 */
    		else if (contextAction.equalsIgnoreCase("Add"))
    		{

    			return mapping.findForward(contextAction);
    		}
    		/******************************
    		 * refine search action
    		 */
    		else if (contextAction.equalsIgnoreCase("RefineSearch"))
    		{

    			return mapping.findForward(contextAction);
    		}
    		/******************************
    		 * NEW SEARCH ACTION
    		 */
    		else if (contextAction.equalsIgnoreCase("NewSearch"))
    		{

    			//System.out.println("Getting a new search");
    			return mapping.findForward(contextAction);
    		}
    		/*********************************
    		 * VIEW ACTION
    		 */
    		else if (contextAction.equalsIgnoreCase("View"))
    		{

    			return mapping.findForward(contextAction);
    		}
    		else if(mode.equalsIgnoreCase("Next"))
    		{
    			String strCurrentIndex = (String)request.getParameter("currentIndex");
    			request.setAttribute("mode",request.getParameter("mode"));
    			//##!! System.out.println("Current index of Submit is: " + strCurrentIndex);

    			request.setAttribute("DSFromIndex",strCurrentIndex);
    			request.setAttribute("mode",request.getParameter("mode"));

    			return mapping.findForward(contextAction);
    		}
    	}catch (Exception e) {
    		logger.error("Exception in Lab Report Test Name Search Submit: " + e.getMessage());
    		e.printStackTrace();
    		throw new ServletException("Error occurred in Lab Report Test Name Search Submit: "+e.getMessage());
    	}  
	return null;
    }

    private void findObservation(ActionForm form, HttpSession session,
			    HttpServletRequest request, boolean bEntitySearch)
    {
    PropertyUtil propertyUtil= PropertyUtil.getInstance();
	ArrayList<?> personList = new ArrayList<Object> ();

	ObservationSearchForm psForm = (ObservationSearchForm)form;
	ObservationSearchVO psVO = (ObservationSearchVO)psForm.getObservationSearch();
	NBSContext.store(session, "DSSearchCriteria", psVO);
	session.setAttribute("SearchCriteria",psVO);

	if (psVO != null)
	{

	    NedssUtils nedssUtils = null;
	    MainSessionCommand msCommand = null;
	    String sBeanJndiName = "";
	    String sMethod = "";
	    Object[] oParams = null;
			Long labId = null;
	    if (request.getParameter("Org-ReportingOrganizationUID") != null){
				labId = new Long((String)request.getParameter("Org-ReportingOrganizationUID"));
			}
	    logger.debug("lab id in param-->" + request.getAttribute("labId"));
	    logger.debug("lab id in attrib->" + request.getParameter("labId"));
		String clia = (String)request.getParameter("labId");
		request.setAttribute("labId", clia);
		request.setAttribute("Org-ReportingOrganizationUID", request.getParameter("type"));
		request.setAttribute("type", clia);
		request.setAttribute("SearchString", psVO.getLastName());

		String method = "";
		String type = "";
		if ((String)request.getParameter("method") != null){
			method = (String)request.getParameter("method");
			request.setAttribute("method", method);
		}

		sBeanJndiName = JNDINames.OBSERVATION_PROXY_EJB;
		String searchType = request.getParameter("type");
		if (searchType.equals("Ordered") || searchType.equals("Resulted")){
			type = searchType + "TestName";
		}else{
			type = searchType + "sByName";
		}

		sMethod = "find" + type;
		if (searchType.equals("Ordered") || searchType.equals("Resulted") || searchType.equals("Organism")){
			oParams = new Object[]{clia, labId, psVO.getLastName(), psVO.getSearchType(), new Integer(propertyUtil.getNumberOfRows()),	new Integer(0)};
		}else{
			oParams = new Object[]{clia, labId, psVO.getLastName(), psVO.getSearchType(), method, new Integer(propertyUtil.getNumberOfRows()),	new Integer(0)};
		}

	    try
	    {

			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(session);
			ArrayList<?> arrList = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
			personList = (ArrayList<?> )arrList.get(0);
	    }
	    catch (Exception e)
	    {
			e.printStackTrace();
	    }

		NBSContext.store(session, "DSSearchResults", personList);
		request.setAttribute("SearchType", psVO.getSearchType());
		request.setAttribute("SearchSubject", request.getParameter("type"));


	}
    }


    private String getRaceDescTxt(String raceCode, String descTxt){

      String returnStr ="";
      if (raceCode != null){
        if(!raceCode.equals("") && descTxt != null){
          String findRace = descTxt.substring(descTxt.indexOf(raceCode));
          String s = findRace.substring(findRace.indexOf("$")+1);
          ////##!! System.out.println("\n\n\n ---------------" + s.substring(0,s.indexOf("|")));
          return s.substring(0,s.indexOf("|")+1);
        }
      }
      return returnStr;
    }

    private String getStateDescTxt(String sStateCd)
    {

	CachedDropDownValues srtValues = new CachedDropDownValues();
	TreeMap<Object,Object> treemap = srtValues.getStateCodes2("USA");
	String desc = "";

	if (sStateCd != null && treemap.get(sStateCd) != null)
	    desc = (String)treemap.get(sStateCd);

	return desc;
    }

    private String getIDDescTxt(String id)
    {

	CachedDropDownValues srtValues = new CachedDropDownValues();
	String desc = srtValues.getCodeDescTxt(id);

	return desc;
    }

    private String getRoleDescTxt(String role , String descTxt)
    {
         String returnStr ="";
         if (role != null){
	  if(!role.equals("") && descTxt != null){
            String findRole = descTxt.substring(descTxt.indexOf(role));
            String s = findRole.substring(findRole.indexOf("$")+1);
            return s.substring(0,s.indexOf("|")+1);
          }
        }
      return returnStr;
    }


}