package gov.cdc.nedss.webapp.nbs.action.notification;

import gov.cdc.nedss.webapp.nbs.form.person.*;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.systemservice.ejb.srtmapejb.bean.*;
import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.systemservice.nbscontext.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.*;
import gov.cdc.nedss.proxy.ejb.notificationproxyejb.vo.*;
import gov.cdc.nedss.entity.person.vo.*;
import gov.cdc.nedss.act.publichealthcase.dt.*;
import gov.cdc.nedss.systemservice.util.*;
import gov.cdc.nedss.webapp.nbs.action.util.ErrorMessageHelper;

import java.io.*;
import java.util.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.struts.action.*;
import gov.cdc.nedss.webapp.nbs.action.person.util.PersonUtil;
import gov.cdc.nedss.act.publichealthcase.vo.PublicHealthCaseVO;

/**
 * Title:        NotificationLoad
 * Description:  This is a load action class for a create notification page to send notification.
 * It uses Struts to route the forward action.
 * Copyright:    Copyright (c) 2002
 * Company:      Computer Sciences Corporation
 * @author:      NEDSS Development Team
 * @version      NBS1.1
 */

public class NotificationLoad
    extends Action {

  //For logging
  static final LogUtils logger = new LogUtils(NotificationLoad.class.getName());

  /**
   * According to the Context Map<Object,Object> what the from page is and current task and security permission,
   * this method loads the create notification page.
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
      ServletException
  {
    HttpSession session = request.getSession();
    String contextAction = request.getParameter("ContextAction");

    TreeMap<Object,Object> tm = NBSContext.getPageContext(session, "PS029", contextAction);
    ErrorMessageHelper.setErrMsgToRequest(request, "PS029");
    String sCurrTask = NBSContext.getCurrentTask(session);


    request.setAttribute("CancelButtonHref",  "/nbs/" + sCurrTask + ".do?ContextAction=" +  tm.get("Cancel"));
    request.setAttribute("SubmitButton", "/nbs/" + sCurrTask + ".do");
    request.setAttribute("ContextAction", tm.get("Submit"));
    request.setAttribute("EditInvHref", "/nbs/" + sCurrTask + ".do?ContextAction=EditInvestigation");

    //get PersonLocalID from ObjectStore for id bar
    String personLocalID = (String)NBSContext.retrieve(session, NBSConstantUtil.DSPatientPersonLocalID);
    request.setAttribute("personLocalID", PersonUtil.getDisplayLocalID(personLocalID));

    
    String programeAreaCode = (String)NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationProgramArea);
    String jurisdictionCode = (String)NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationJurisdiction);
    String shared = (String)NBSContext.retrieve(session, NBSConstantUtil.DSSharedInd);
    
    //security stuff
    NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session.getAttribute("NBSSecurityObject");

    boolean check1 = nbsSecurityObj.getPermission(NBSBOLookup.NOTIFICATION, NBSOperationLookup.CREATE, programeAreaCode, jurisdictionCode, shared);
    boolean check2 = nbsSecurityObj.getPermission(NBSBOLookup.NOTIFICATION, NBSOperationLookup.CREATENEEDSAPPROVAL, programeAreaCode, jurisdictionCode, shared);

    if ( (!check1) && (!check2))
    {
        logger.fatal("Error: Do not have permission to create notification.");
        throw new ServletException("Error: Do not have permission to create notification.");
    }

    boolean check3 = nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,  NBSOperationLookup.EDIT);

    if (!check3)
    {
        logger.fatal("Error: Do not have permission to edit investigation.");
        request.setAttribute("canEditInvestigation", new Boolean(false));
    }
    else
    {
        logger.info("have permission to edit investigation.");
        request.setAttribute("canEditInvestigation", new Boolean(true));
    }
    //calls NotificationProxy, and returns values if validated fields are missing
	String investigationUid=(String)NBSContext.retrieve(request.getSession(), NBSConstantUtil.DSInvestigationUid);
	Long publicHealthCaseUid = new Long(investigationUid);
	
   
    ArrayList<?> resultFromEJB = null;
    try
    {
        resultFromEJB = (ArrayList<?> )this.sendProxyToEJBValidate(publicHealthCaseUid, session);
        request.setAttribute("resultFromEJB", resultFromEJB);
    }
    catch (Exception ex)
    {
    	logger.error("Error: "+ex.getMessage());
		ex.printStackTrace();
        return mapping.findForward(NEDSSConstants.ERROR_PAGE);
    }

    String publicHealthCaseLocalUid = (String)NBSContext.retrieve(request.getSession(), NBSConstantUtil.DSInvestigationLocalID);
    String conditionCdDescTxt = (String)NBSContext.retrieve(request.getSession(), NBSConstantUtil.DSConditionCdDescTxt);
    String caseStatus = (String)NBSContext.retrieve(request.getSession(), NBSConstantUtil.DSCaseStatus);
    request.setAttribute("publicHealthCaseLocalUid", publicHealthCaseLocalUid);
    request.setAttribute("conditionCdDescTxt", conditionCdDescTxt);
    request.setAttribute("CaseStatus", caseStatus);

    if (sCurrTask.equals("CreateNotification2") ||
        sCurrTask.equals("CreateNotification4") ||
        sCurrTask.equals("CreateNotification6"))
    {
        Object comments = NBSContext.retrieve(session, NBSConstantUtil.DSNotificationComments);
        if (comments != null && comments.toString().trim().length() > 0)
        request.setAttribute("comments", comments.toString());
    }

    return mapping.findForward("XSP");

  }

  /**
   * This is a private method to validate required fields for notification.
   * This method calls validateNNDIndividualRequiredFields() method in NotificationProxyEJB
   * through MainSessionCommandEJB
   * @param HttpServletRequest
   * @param HttpServletResponse
   * @throws RemoteException
   * @throws EJBException
   * @throws NEDSSSystemException
   */
  private Collection<?>  sendProxyToEJBValidate(Long publicHealthCaseUID,
                                            HttpSession session) throws Exception
   {
    MainSessionCommand msCommand = null;
    try
    {

      String sBeanJndiName = JNDINames.NOTIFICATION_PROXY_EJB;
      String sMethod = "validateNNDIndividualRequiredFields";
      Object[] oParams = {publicHealthCaseUID};
      MainSessionHolder holder = new MainSessionHolder();
      msCommand = holder.getMainSessionCommand(session);

      ArrayList<?> resultUIDArr = new ArrayList<Object> ();
      resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
      if ( (resultUIDArr != null) && (resultUIDArr.size() > 0))
      {
        ArrayList<?> result = (ArrayList<?> ) resultUIDArr.get(0);
        return result;
      }
      else
      {
        return null;
      }
    }
    catch (Exception e)
    {
      logger.fatal("ERROR calling mainsession control", e);
      throw new Exception(e.getMessage());
    }
  } //sendProxyToEJB

}