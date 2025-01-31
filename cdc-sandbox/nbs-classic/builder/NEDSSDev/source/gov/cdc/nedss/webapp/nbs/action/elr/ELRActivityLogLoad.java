package gov.cdc.nedss.webapp.nbs.action.elr;

import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.systemservice.nbscontext.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.elr.helper.*;
import gov.cdc.nedss.webapp.nbs.action.util.*;

import java.io.*;

import java.sql.Timestamp;

import java.text.*;

import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.rmi.PortableRemoteObject;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;

/**
 * Title:        ELRActivityLogLoad
 * Description:  This is a load action class for submmiting the conditions to generate ELR activity log report.
 * Copyright:    Copyright (c) 2002
 * Company:      Computer Sciences Corporation
 * @author:      NEDSS Development Team
 * @version      NBS1.1
 */

public class ELRActivityLogLoad
    extends Action {

  //For logging
  static final LogUtils logger = new LogUtils(ELRActivityLogLoad.class.
                                              getName());

  /**
   * This method that sets the request attribute data before displaying the
   * ElR Search screen.
   * @param ActionMapping
   * @param ActionForm
   * @param HttpServletRequest
   * @param HttpServletResponse
   * @return ActionForward
   * @throws IOException
   * @throws ServletException
   */
  public ActionForward execute(ActionMapping mapping, ActionForm aForm,
                               HttpServletRequest request,
                               HttpServletResponse response) throws IOException,
      ServletException {
    System.out.println("Inside ELRActivityLOg");
    HttpSession session = request.getSession();

    if (session == null) {
      logger.fatal("error no session");
      throw new ServletException("error no session");
    }
    // Return Link
    String strLinkName = "Return to System Management Main Menu";
    String strLinkAddr = "/nbs/SystemAdmin.do?focus=systemAdmin2";
    request.setAttribute("LinkName", strLinkName);
    request.setAttribute("LinkAddr", strLinkAddr);
    //error message framework call
   // TreeMap<Object,Object> tm = NBSContext.getPageContext(session, "PS159",                                   contextAction);
   // NBSContext.lookInsideTreeMap(tm);
    ErrorMessageHelper.setErrMsgToRequest(request, "PS159");
    //find forward

    return mapping.findForward("XSP");
  }


}