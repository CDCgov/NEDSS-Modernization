

package gov.cdc.nedss.webapp.nbs.action.investigation.common;



import java.io.*;
import java.util.*;
import javax.servlet.http.*;
import javax.servlet.*;

import org.apache.struts.action.*;

import gov.cdc.nedss.util.*;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.*;
import gov.cdc.nedss.systemservice.nbscontext.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;

/**
 * Title:         ConfirmationLoad is a class
 * Description:   This is actions class to Provides to load Investigaion
 * Copyright:     Copyright (c) 2001
 * Company:       CSC
 * @author        NEDSS TEAM
 * @version       1.0
 */

public class ConfirmationLoad extends Action {

    /**
     *  This is constructor for this class
     */
  public ConfirmationLoad() {
  }


  /**
      * Get values from investigation form and forward to next action.
      * @param ActionMapping the mapping
      * @param ActionForm the form contain values
      * @param HttpServletRequest the request
      * @param HttpServletResponse the response
      * @return  ActionForward Object
      * @throws ServletException and IOException
      */
  public ActionForward execute(ActionMapping mapping,
				 ActionForm form,
				 HttpServletRequest request,
				 HttpServletResponse response)
   throws IOException, ServletException
   {
      HttpSession session = request.getSession(false);




      NBSSecurityObj secObj = (NBSSecurityObj)session.getAttribute("NBSSecurityObject");
      /***************************************
       *
       */
      TreeMap<Object,Object> tm = NBSContext.getPageContext(session,"PS155","NONE");
      String currentTaskName = NBSContext.getCurrentTask(session);
      //##!! System.out.println("Initializing Context manager " + currentTaskName);
      NBSContext.lookInsideTreeMap(tm);

      /****************************************
       *
       */
      String contextAction = request.getParameter("ContextAction");
      if (contextAction == null)
	    contextAction = (String) request.getAttribute("ContextAction");

      String sCurrTask = NBSContext.getCurrentTask(session);
      request.setAttribute("homeHref",
                             "/nbs/" + sCurrTask + ".do?ContextAction=" +
                             tm.get("TaskListLoad"));

      return mapping.findForward("XSP");

   }



}