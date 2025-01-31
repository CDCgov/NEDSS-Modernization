package gov.cdc.nedss.webapp.nbs.action.provider;


import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.systemservice.nbscontext.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.webapp.nbs.action.place.PlaceUtil;
import gov.cdc.nedss.webapp.nbs.form.provider.*;
import gov.cdc.nedss.entity.person.vo.*;
//for the old way using entity
import gov.cdc.nedss.util.PropertyUtil;
/**
 * Title:        Actions
 * Description:  Provider Search Submit
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


public class ProviderSearchSubmit
    extends Action
{
    public ProviderSearchSubmit()
    {
    }
    static final LogUtils logger = new LogUtils(ProviderSearchSubmit.class.getName());
    
    public ActionForward execute(ActionMapping mapping, ActionForm form,
				 HttpServletRequest request,
				 HttpServletResponse response)
			  throws IOException, ServletException
    {

	HttpSession session = request.getSession(false);
	NBSSecurityObj secObj = (NBSSecurityObj)session.getAttribute(
					"NBSSecurityObject");
	String contextAction = request.getParameter("ContextAction");

	if (contextAction == null)
	    contextAction = (String)request.getAttribute("ContextAction");


	/*****************************
	 * SUBMIT ACTION
	 */
     
        String mode = request.getParameter("mode");
        request.setAttribute("PersonName",session.getAttribute("PersonName"));
      
        if( (contextAction.equalsIgnoreCase("Submit") || contextAction.equalsIgnoreCase("EntitySearch")  && mode == null))
	{

	    findProvider(form, session, request, false);

	    // add button security
	    boolean bAddButton = secObj.getPermission(NBSBOLookup.PROVIDER,"MANAGE");
	       request.setAttribute("addButton", String.valueOf(bAddButton));
               String strCurrentIndex = (String)request.getParameter("currentIndex");
		

		request.setAttribute("DSFromIndex",strCurrentIndex);
		request.setAttribute("mode",request.getParameter("mode"));
		String PersonName  = (String)session.getAttribute("PersonName");
	
		request.setAttribute("PersonName", PersonName);
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
	/******
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
                //##!! logger.debug("Current index of Submit is: " + strCurrentIndex);

		request.setAttribute("DSFromIndex",strCurrentIndex);
		request.setAttribute("mode",request.getParameter("mode"));


		return mapping.findForward(contextAction);
	}
	return null;
    }


    private void findProvider(ActionForm form, HttpSession session,
                                HttpServletRequest request, boolean bEntitySearch)
        {

            ArrayList<?> personList = new ArrayList<Object> ();
            ProviderSearchForm psForm = (ProviderSearchForm)form;
            ProviderSearchVO psVO = (ProviderSearchVO)psForm.getProviderSearch();
            String contextAction = request.getParameter("ContextAction");

            if (contextAction == null)
                contextAction = (String)request.getAttribute("ContextAction");

            psVO.setActive(true);
            psVO.setStatusCodeActive("ACTIVE");
            session.setAttribute("DSSearchCriteria", psVO);
            NBSContext.store(session, "DSSearchCriteria", psVO);
            session.setAttribute("SearchCriteria",psVO);
           // //##!! logger.debug("Role in search is: "+ psVO.getRole());
            if (psVO != null)
            {

                NedssUtils nedssUtils = null;
                MainSessionCommand msCommand = null;
                String sBeanJndiName = "";
                String sMethod = "";
                Object[] oParams = null;
                sBeanJndiName = JNDINames.ENTITY_PROXY_EJB;
                sMethod = "findProvider";
                oParams = new Object[]
                {
                    psVO, new Integer(PropertyUtil.getInstance().getNumberOfRows()),
                    new Integer(0)
                };

                try
                {

                    MainSessionHolder holder = new MainSessionHolder();
                    msCommand = holder.getMainSessionCommand(session);
                    ArrayList<?> arrList = msCommand.processRequest(sBeanJndiName,
                                                                 sMethod, oParams);
                    personList = (ArrayList<?> )arrList.get(0);
                }
                catch (Exception ex)
                {
        			logger.error("Error: "+ex.getMessage());
        			ex.printStackTrace();
                }

                session.setAttribute("result", personList);
                session.setAttribute("DSSearchResults", personList);
                request.setAttribute("DSSearchResults", personList);
                //set temperory for merge person
                session.setAttribute("PerMerge",personList);
                NBSContext.store(session, "DSSearchResults", personList);


            }
        }
}