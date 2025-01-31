package gov.cdc.nedss.webapp.nbs.action.navbar;

/**
 * Title:        Actions
 * Description:  Navigation Bar Load
 * Copyright:    Copyright (c) 2001
 * Company:      CSC
 * @author
 * @version 1.0
 */

import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.util.LogUtils;

import java.io.IOException;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class NavbarLoad extends Action
{
    static final LogUtils logger = new LogUtils(NavbarLoad.class.getName());
    static final String TASK_HREF = "/nbs/MyTaskList1.do?ContextAction=";

    public NavbarLoad()
    {
    }

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException
    {

        HttpSession session = request.getSession(false);

        /**
         * INITIALIZE THE NAVBAR
         */
        TreeMap<Object, Object> tm = NBSContext.getPageContext(session, "GLOBAL_CONTEXT", "NONE");
        String currentTaskName = NBSContext.getCurrentTask(session);
        NBSContext.lookInsideTreeMap(tm);

        request.setAttribute("mergePersonHref", TASK_HREF + tm.get("MP_ManualSearch")  + "&Mode1=ManualMerge");
        request.setAttribute("systemIdentifiedHref",
                "/nbs/LoadMergeCandidateList2.do?ContextAction=" + tm.get("MP_SystemIdentified"));
        request.setAttribute("personHref", TASK_HREF + tm.get("Patient"));

        request.setAttribute("organizationHref", TASK_HREF + tm.get("Organization"));
        request.setAttribute("placeHref", TASK_HREF + ("GlobalPlace"));
        request.setAttribute("providerHref", TASK_HREF + tm.get("Provider"));

        request.setAttribute("labReport", "/nbs/MyTaskList1.do?ContextAction=AddLabDataEntry");
        request.setAttribute("viewReport", "/nbs/ViewLabReport1.do?ContextAction=ViewLabReport");
        request.setAttribute("morbReport", "/nbs/MyTaskList1.do?ContextAction=AddMorbDataEntry");

        String contextAction = request.getParameter("ContextAction");
        if (contextAction == null)
            contextAction = (String) request.getAttribute("ContextAction");
        return mapping.findForward(contextAction);

    }

}
