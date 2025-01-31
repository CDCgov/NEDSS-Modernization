package gov.cdc.nedss.webapp.nbs.action.nbssecurity;

/**
 * Title:        UsersSort
 * Description:  This file is used to sort the columns of the sort page of SecurityAdmin screens
 * Copyright:    Copyright (c) 2001
 * Company:      CSC
 * @author       Wade Steele
 * @version 1.0
 */

import java.io.*;
import java.util.*;
import javax.servlet.http.*;
import javax.servlet.*;

import org.apache.struts.action.*;



import java.sql.Timestamp;

import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.systemservice.nbscontext.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.webapp.nbs.form.nbssecurity.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

public class UsersSort extends Action {
  //For logging
  static final LogUtils logger = new LogUtils("UsersSort");


  public UsersSort() {
  }

   public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
   throws IOException, ServletException
   {
      logger.debug("inside the UsersSort");

      HttpSession session = request.getSession(false);
      if (session == null)
      {
        logger.debug("error no session");
        throw new ServletException("error no session");
      }

      String contextAction = request.getParameter("ContextAction");
      if (contextAction == null)
          contextAction = (String)request.getAttribute("ContextAction");
          if (contextAction == null)
              contextAction = "Sort";
	logger.debug("before call getPageContext with contextAction of: " + contextAction);
      TreeMap<Object,Object> tm = NBSContext.getPageContext(session, "PS999", contextAction);
      logger.debug("doing getCurrentTask(session)");
      String sCurrTask = NBSContext.getCurrentTask(session);
      sCurrTask = "userList";
      logger.debug("sCurrTask: " + sCurrTask);


	request.setAttribute("nextHref",
				 "/nbs/" + sCurrTask +
				 ".do?ContextAction=" + tm.get("Next"));
	request.setAttribute("prevHref",
				 "/nbs/" + sCurrTask +
				 ".do?ContextAction=" + tm.get("Prev"));
      request.setAttribute("sortHref",
				 "/nbs/" + sCurrTask +
				 ".do?ContextAction=" + tm.get("Sort"));


      String strReturnURL = request.getHeader("REFERER");
      ArrayList<Object> usersCollection  = (ArrayList<Object> )session.getAttribute("usersCollection");
      logger.debug("inside the user sort:" + strReturnURL);
      logger.debug("UsersSort: execute() usersCollection:" + usersCollection);
      if(usersCollection  != null)
      {
	String userSortDirection = request.getParameter("direction");
        boolean userDirectionFlag = true;
        if( userSortDirection != null && userSortDirection.equals("false"))
        {
          userDirectionFlag = false;
          session.setAttribute ("sortDirection", new Boolean (true));
        } else
        { userDirectionFlag = true;
          session.setAttribute ("sortDirection", new Boolean (false));
        }
        String sortMethod = request.getParameter("SortMethod");
        logger.debug("Sort method is:" + sortMethod);
        if(sortMethod != null && usersCollection  != null && usersCollection.size() > 0)
	{
	    logger.debug("List is : " + usersCollection);
            NedssUtils util = new NedssUtils();
            util.sortObjectByColumn(sortMethod, usersCollection, userDirectionFlag);
	    logger.debug("Just finished sorting by column");
        }
	logger.debug("Setting the session attribute for the usersCollection");
	session.setAttribute("usersCollection", usersCollection);
      }
      else
      {
	logger.error("execute usersCollection  is null");
      }
      //return (mapping.findForward("sortDisplay"));

        ActionForward af = mapping.findForward("sortDisplay");
        ActionForward forwardNew = new ActionForward();
        String strURL = af.getPath();
       // strURL = strURL + "?publicHealthCaseUID=" + publicHealthCaseUID + "&ObjectType=" + NEDSSConstantUtil.NOTIFICATION;
	//logger.debug("execute: strURL" + strURL);
        forwardNew.setPath(strURL);
	logger.debug("Value of forwardNew URL is: " + strURL);
        forwardNew.setRedirect(false);
        return forwardNew;
   }

}