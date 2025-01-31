package gov.cdc.nedss.webapp.nbs.action.notification;

import gov.cdc.nedss.exception.NEDSSAppConcurrentDataException;
import gov.cdc.nedss.proxy.ejb.notificationproxyejb.vo.UpdatedNotificationSummaryVO;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.nbssecurity.ProgramAreaJurisdictionUtil;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.webapp.nbs.action.util.DSQueueObject;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Title:        ReviewNotificationSubmit
 * Description:  This is a summit action class for sending reviewed notification.
 * It uses Struts to route the forward action.
 * Copyright:    Copyright (c) 2002
 * Company:      Computer Sciences Corporation
 * @author:      NEDSS Development Team
 * @version      NBS1.1.3 SP2
 */

public class ReviewUpdatedNotificationSubmit extends Action {

	//For logging
	static final LogUtils logger =
		new LogUtils(ReviewUpdatedNotificationSubmit.class.getName());

	// protected MainSessionCommand msCommand = null;
	

	/**
	 * According to the Context Map<Object,Object> what the from page is and current task and security permission,
	 * this method submits reviewed notification. Depends on the request value if approve or reject,
	 * this method calls a private method: approveNotification or rejectNotification.
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward
	 * @throws IOException
	 * @throws ServletException
	 */
	public ActionForward execute(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws IOException, ServletException {

		HttpSession session = request.getSession();
		logger.debug("entering ReviewNotifications Submit");

		//get page context for debugging purposes
		String contextAction = request.getParameter("ContextAction");

        if (contextAction == null)
            contextAction = (String)request.getAttribute("ContextAction");


    	String currentIndex = request.getParameter("currentIndex");
    	String sortColumn = request.getParameter("sortMethod");
    	String sortDirection = request.getParameter("direction");

    	if(currentIndex == null)
    	    currentIndex = (String)request.getAttribute("currentIndex");

    	DSQueueObject dsQueueObject = (DSQueueObject)NBSContext.retrieve(session, "DSQueueObject");
    	if(currentIndex != null)
    		dsQueueObject.setDSFromIndex(currentIndex);
        if(sortColumn != null)
        	dsQueueObject.setDSSortColumn(sortColumn);
        if(sortDirection !=null)
        	dsQueueObject.setDSSortDirection(sortDirection);


		String investigationID = request.getParameter("publicHealthCaseUID");



		NBSSecurityObj nbsSecurityObj =
			(NBSSecurityObj) session.getAttribute("NBSSecurityObject");

		if (!nbsSecurityObj
			.getPermission(
				NBSBOLookup.NOTIFICATION,
				NBSOperationLookup.REVIEW,
				ProgramAreaJurisdictionUtil.ANY_PROGRAM_AREA,
				ProgramAreaJurisdictionUtil.ANY_JURISDICTION)) {
			logger.fatal(
				"Error: no permisstion to review notification, go back to login screen");
			throw new ServletException("Error: no permisstion to review notification, go back to login screen");
		}

		try {
			if (contextAction != null && contextAction.equals("Submit")) {

				this.removeSelectedUpdatedNotifications(request, response);
				return mapping.findForward(contextAction);
			} else if (
				contextAction != null
					&& contextAction.equals("InvestigationID")) {
				NBSContext.store(
					session,
					NBSConstantUtil.DSInvestigationUid,
					investigationID);
				return mapping.findForward(contextAction);
			} else {
				logger.info(
					"Nothing matched. Simply forwarding on using ContextAction");
				return mapping.findForward(contextAction);
			}
		} catch (NEDSSAppConcurrentDataException concurrentException) {
			logger.fatal(
				"ERROR - NEDSSAppConcurrentDataException, The data has been modified by another user, please recheck! ",
				concurrentException);
			return mapping.findForward("dataerror");
		} catch (Exception e) {
			logger.error(
				"Problem happened in the ReviewUpdatedNotificationSubmit struts class",
				e);
			e.printStackTrace();
			throw new ServletException("Problem happened in the ReviewUpdatedNotificationSubmit struts class:"+e.getMessage(),e);
		}
	}

	/**
	 * This method calls removeSelectedUpdatedNotifications() method in NotificationProxyEJB through MainSessionCommandEJB
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @throws NEDSSAppConcurrentDataException
	 */

	private void removeSelectedUpdatedNotifications(
		HttpServletRequest request,
		HttpServletResponse response)
		throws Exception, NEDSSAppConcurrentDataException {
		HttpSession session = request.getSession();
		
		String sBeanJndiName = "";
		String sMethod = "";
		Object[] oParams = null;

		MainSessionCommand msCommand = null;
		MainSessionHolder mainSessionHolder = new MainSessionHolder();
		try {

			ArrayList<Object> updatedNotificationList =
				(ArrayList<Object> )NBSContext.retrieve(session ,"DSUpdatedNotificationList");
			logger.info(
				"number of items in the list is: "
					+ updatedNotificationList.size());

			UpdatedNotificationSummaryVO notSumVO = null;

			ArrayList<Object> removedUpdatedNotificationUids = new ArrayList<Object> ();

			for (int i = 0; i < updatedNotificationList.size(); i++) {
				notSumVO = (UpdatedNotificationSummaryVO) updatedNotificationList.get(i);
				   String flag = request.getParameter("isRemoved-" +
				   		notSumVO.getNotificationUid().toString() + "_" +
				   		notSumVO.getVersionCtrlNbr().toString());
				   if(flag != null)
				   {
						removedUpdatedNotificationUids.add(notSumVO);
         		   }
			}

			msCommand = mainSessionHolder.getMainSessionCommand(session);
			sBeanJndiName = JNDINames.NOTIFICATION_PROXY_EJB;

			//-----approveNotification
			sMethod = "removeUpdatedNotifications";
			oParams = new Object[] { removedUpdatedNotificationUids};
			msCommand = mainSessionHolder.getMainSessionCommand(session);
			ArrayList<Object> arr =
				(ArrayList<Object> ) msCommand.processRequest(
					sBeanJndiName,
					sMethod,
					oParams);

		} catch (NEDSSAppConcurrentDataException e) {
			logger.fatal(
				"ERROR - NEDSSAppConcurrentDataException, The data has been modified by another user, please recheck! ",
				e);
			throw new NEDSSAppConcurrentDataException(e.getMessage());
		}
	}
}
