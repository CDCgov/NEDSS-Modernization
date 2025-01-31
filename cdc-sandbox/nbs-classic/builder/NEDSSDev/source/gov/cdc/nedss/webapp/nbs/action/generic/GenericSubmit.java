package gov.cdc.nedss.webapp.nbs.action.generic;

/**
 * Title:        Actions
 * Description:  Generic Submit
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
import org.jfree.util.Log;

import gov.cdc.nedss.util.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.systemservice.nbscontext.*;

public class GenericSubmit extends Action {

  public GenericSubmit() {
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
      String strContextAction = request.getParameter("ContextAction");
      if (strContextAction == null)
	 strContextAction = (String) request.getAttribute("ContextAction");

      //System.out.println("(GENERIC SUBMIT)  what is the context action ============ " + strContextAction);
      //need to process any parameters that come inside here

      Enumeration<?> params = request.getParameterNames();
      while(params.hasMoreElements()){
	String sParamName = (String)params.nextElement();
	request.setAttribute(sParamName,request.getParameter(sParamName));
	//System.out.println("(GENERIC SUBMIT) param to attribute name = " +  sParamName + "     value = " + request.getParameter(sParamName));
      }

      ActionForward actionForward = mapping.findForward(strContextAction);
      if (actionForward != null ) { // debug
    	  String cmd = actionForward.getCommand();
    	  String name = actionForward.getName();
    	  String module = actionForward.getModule();
    	  String path = actionForward.getPath();
    	  Log.debug("GenericSubmit:\tcmd:"+cmd+"\tname:"+name+"\tmodule:"+module+"\tpath:"+path);
      }
      
      if(strContextAction!=null && strContextAction.equalsIgnoreCase("AddLabDataEntry")){
    	  request.setAttribute("TabtoFocus","Patient");
    	  request.setAttribute("retainNextEntry","true");
    		
      }
      return actionForward;

   }



}