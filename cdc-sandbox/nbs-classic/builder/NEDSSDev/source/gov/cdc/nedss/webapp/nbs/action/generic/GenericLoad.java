package gov.cdc.nedss.webapp.nbs.action.generic;

/**
 * Title:        Actions
 * Description:  Generic Load
 * Copyright:    Copyright (c) 2001
 * Company:      CSC
 * @author
 * @version 1.0
 */

import java.io.*;
import java.util.*;
import javax.servlet.http.*;
import javax.servlet.*;

import org.apache.struts.action.*;

import gov.cdc.nedss.util.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.systemservice.nbscontext.*;



public class GenericLoad extends Action {

  public GenericLoad() {
  }


  public ActionForward execute(ActionMapping mapping,
				 ActionForm form,
				 HttpServletRequest request,
				 HttpServletResponse response)
   throws IOException, ServletException
   {
      HttpSession session = request.getSession(false);




      NBSSecurityObj secObj = (NBSSecurityObj)session.getAttribute("NBSSecurityObject");
/*
      String strType = request.getParameter("OperationType");
      if (strType == null)
	 strType = (String) request.getAttribute("OperationType");
*/


      return mapping.findForward("XSP");

   }



}