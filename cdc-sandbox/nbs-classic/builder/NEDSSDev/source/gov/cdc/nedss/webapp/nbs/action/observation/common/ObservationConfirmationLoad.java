package gov.cdc.nedss.webapp.nbs.action.observation.common;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;

import gov.cdc.nedss.proxy.ejb.observationproxyejb.bean.ObservationProxyHome;
import gov.cdc.nedss.systemservice.nbscontext.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;

public class ObservationConfirmationLoad extends Action
 {
   static final LogUtils logger = new LogUtils(ObservationConfirmationLoad.class.getName());
   public ObservationConfirmationLoad()
   {
   }
   public ActionForward execute(ActionMapping mapping,
                    ActionForm form,
                    HttpServletRequest request,
                    HttpServletResponse response)
    throws IOException, ServletException
    {
       HttpSession session = request.getSession(false);
       NBSSecurityObj secObj = (NBSSecurityObj)session.getAttribute("NBSSecurityObject");
       String contextAction = request.getParameter("ContextAction");
       if (contextAction == null)
       contextAction = (String) request.getAttribute("ContextAction");
       TreeMap<Object,Object> tm = NBSContext.getPageContext(session, "PS141",
                                                    contextAction);
             String sCurrTask = NBSContext.getCurrentTask(session);
             NBSContext.lookInsideTreeMap(tm);
       if(contextAction.equals("Home"))
        {
          return mapping.findForward(contextAction);
        }
        else
        {
          String jurisdiction = (String)NBSContext.retrieve(session, NBSConstantUtil.DSJurisdiction);
          String programArea =  (String)NBSContext.retrieve(session, NBSConstantUtil.DSProgramArea);
          Long observationUid = (Long)NBSContext.retrieve(session, "DSObservationUID");
          String localID = this.getObsLocalID(observationUid,session);
          request.setAttribute("jurisdiction", jurisdiction);
          request.setAttribute("programArea", programArea);
          request.setAttribute("ObservationUid", localID);

          request.setAttribute("homeHref", "/nbs/" + sCurrTask + ".do?ContextAction=" + tm.get("Home"));
          return mapping.findForward("XSP");
        }

 }

 private String getObsLocalID(Long uid, HttpSession session)
    {

      MainSessionCommand msCommand = null;
      String sBeanJndiName = JNDINames.OBSERVATION_PROXY_EJB;
      String sMethod = null;
      MainSessionHolder holder = new MainSessionHolder();
      ArrayList<?> resultUIDArr = null;
      try {
        msCommand = holder.getMainSessionCommand(session);
        sMethod = "getObservationLocalID";
        Object[] oParams = {
            uid};
        resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
      }
      catch (Exception ex) {
        logger.error("exception in getObservationLocalID");
        ex.printStackTrace();
      }

      String localID = (String) resultUIDArr.get(0);
      return localID;
    }

}





