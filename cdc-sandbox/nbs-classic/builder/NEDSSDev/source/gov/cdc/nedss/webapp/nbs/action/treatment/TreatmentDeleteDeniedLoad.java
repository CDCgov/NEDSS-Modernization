/**
 * Title: VaccinationDeleteDeniedLoad class.
 * Description: This class displays deletedenied message on the user interface.
 * Copyright:    Copyright (c) 2001
 * Company: Computer Sciences Corporation
 * @author Aaron Aycock
 * @version 1.0
 */

package gov.cdc.nedss.webapp.nbs.action.treatment;


import gov.cdc.nedss.systemservice.nbscontext.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.util.*;

//for the old way using entity
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.struts.action.*;


public class TreatmentDeleteDeniedLoad extends Action
{
    public TreatmentDeleteDeniedLoad()
    {
    }

  /**
   * According to the Context Map<Object,Object> what the from page is and current task and security permission,
   * this method loads the treatment deletedenied page.
   * @param mapping an ActionMapping object
   * @param form an ActionForm object
   * @param request the current request object
   * @param response the current response object
   * @return ActionForward
   * @throws IOException
   * @throws ServletException
   */
    public ActionForward execute(ActionMapping mapping, ActionForm form,
				 HttpServletRequest request,
				 HttpServletResponse response)
			  throws IOException, ServletException
    {
      HttpSession session = request.getSession(false);
      NBSSecurityObj nbsSecurityObj = (NBSSecurityObj)session.getAttribute("NBSSecurityObject");
      String contextAction = request.getParameter("ContextAction");
      String strUID = request.getParameter("treatmentUID");

      if (strUID == null)
        strUID = (String) request.getAttribute("treatmentUID");

      if (contextAction.equalsIgnoreCase(NBSConstantUtil.DELETEDENIED))
      {
        boolean viewSecurityCheck =  nbsSecurityObj.getPermission(NBSBOLookup.TREATMENT, NBSOperationLookup.VIEW);
        request.setAttribute("viewSecurityCheck",String.valueOf(viewSecurityCheck));
        TreeMap<Object,Object> tm = NBSContext.getPageContext(session, "PS066", contextAction);
        String sCurrTask = NBSContext.getCurrentTask(session);
        request.setAttribute("treatmentUID",strUID);
        //NBSContext.lookInsideTreeMap(tm);
        //request.setAttribute("DSPersonInfo", NBSContext.retrieve(session, "DSPersonInfo"));
        request.setAttribute("returnToViewTreatmentHref",
                                   "/nbs/" + sCurrTask +
                                   ".do?ContextAction=" +
                                   tm.get("ReturnToViewTreatment"));
      }
      else
      {
        session.setAttribute("error", "no context action");
        throw new ServletException("no context action");
      }
    return mapping.findForward("XSP");
    }
}