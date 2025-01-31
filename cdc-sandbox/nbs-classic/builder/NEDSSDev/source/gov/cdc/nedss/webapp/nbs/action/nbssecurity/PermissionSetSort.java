package gov.cdc.nedss.webapp.nbs.action.nbssecurity;

/**
 * Title:        PermissionSetSort
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

import gov.cdc.nedss.util.*;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.webapp.nbs.form.nbssecurity.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

public class PermissionSetSort extends Action {
  //For logging
  static final LogUtils logger = new LogUtils("PermissionSetSort");


  public PermissionSetSort() {
  }

   public ActionForward execute(ActionMapping mapping,
				 ActionForm form,
				 HttpServletRequest request,
				 HttpServletResponse response)
   throws IOException, ServletException
   {
	   logger.debug("inside the PermissionSetSort");

	   HttpSession session = request.getSession(false);
	   if (session == null)
	   {
		   logger.debug("error no session");
		   throw new ServletException("error no session");
	   }
	   ActionForward forwardNew = new ActionForward();
	   try {
		   String strReturnURL = request.getHeader("REFERER");
		   String group = request.getParameter("group");
		   logger.debug("VALUE OF THE GROUP IS: " + group);
		   ArrayList<Object> permsCollection  = null;
		   if(group.equals("system"))
			   permsCollection  = (ArrayList<Object> )session.getAttribute("SystemDefinedPermissionSets");
		   else if(group.equals("user"))
			   permsCollection  = (ArrayList<Object> )session.getAttribute("UserDefinedPermissionSets");

		   logger.debug("inside the permission set sort:" + strReturnURL);
		   logger.debug("PermissionSetSort: execute() permsCollection:" + permsCollection);
		   if(permsCollection  != null)
		   {
			   String permsSortDirection = request.getParameter("direction");
			   boolean permsDirectionFlag = true;
			   if( permsSortDirection != null && permsSortDirection.equals("false"))
			   {
				   permsDirectionFlag = false;
				   session.setAttribute ("sortDirection", new Boolean (true));
			   } else
			   { permsDirectionFlag = true;
			   session.setAttribute ("sortDirection", new Boolean (false));
			   }
			   String sortMethod = request.getParameter("SortMethod");
			   logger.debug("Sort method is:" + sortMethod);
			   if(sortMethod != null && permsCollection  != null && permsCollection.size() > 0)
			   {
				   logger.debug("List is : " + permsCollection);
				   NedssUtils util = new NedssUtils();
				   util.sortObjectByColumn(sortMethod, permsCollection, permsDirectionFlag);
				   logger.debug("Just finished sorting by column");
			   }
			   logger.debug("Setting the session attribute for the permsCollection");

			   if(group.equals("system"))
				   session.setAttribute("SystemDefinedPermissionSets", permsCollection);
			   else if(group.equals("user"))
				   session.setAttribute("UserDefinedPermissionSets", permsCollection);
		   }
		   else
		   {
			   logger.error("execute permsCollection  is null");
		   }
		   //return (mapping.findForward("sortDisplay"));

		   ActionForward af = mapping.findForward("sortPermissions");

		   String strURL = af.getPath();
		   forwardNew.setPath(strURL + "?" + PageConstants.OPERATIONTYPE + "=" + NEDSSConstants.LOAD_PERMISSION_SETS);
		   logger.debug("Value of forwardNew URL is: " + strURL);
		   forwardNew.setRedirect(false);
	   }catch (Exception e) {
		   logger.error("Exception in PermissionSetSort: " + e.getMessage());
		   e.printStackTrace();
		   throw new ServletException("Error while sorting permission sets: "+e.getMessage());
	   }

	   return forwardNew;
   }

}
