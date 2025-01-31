/**
 * Title:        ReviewUpdatedNotificationLoad
 * Description:  This is a load action class for loading review updated notification page.
 * According to the Context Map<Object,Object> what the from page is and current task and security permission,
 * this method loads the review notification page.
 * Copyright:    Copyright (c) 2002
 * Company:      Computer Sciences Corporation
 * @author:      NEDSS Development Team
 * @version      NBS1.1.3 SP2
 */

package gov.cdc.nedss.webapp.nbs.action.notification;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.InvestigationSummaryVO;
import gov.cdc.nedss.proxy.ejb.notificationproxyejb.bean.NotificationProxyHome;
import gov.cdc.nedss.proxy.ejb.notificationproxyejb.vo.NotificationSummaryVO;
import gov.cdc.nedss.proxy.ejb.notificationproxyejb.vo.UpdatedNotificationSummaryVO;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.action.util.DSQueueObject;
import gov.cdc.nedss.webapp.nbs.action.util.ErrorMessageHelper;
import gov.cdc.nedss.webapp.nbs.form.notification.UpdatedNotificationsQueueForm;

public class ReviewUpdatedNotificationLoad extends Action 
{
	//For logging
	static final LogUtils logger = new LogUtils(ReviewUpdatedNotificationLoad.class.getName());
	protected InvestigationSummaryVO invSumVO = null;
	protected NotificationSummaryVO notSumVO = null;

	/**
	 * According to the Context Map<Object,Object> what the from page is and current task and security permission,
	 * this method loads the review notification page.
	 * @param ActionMapping
	 * @param ActionForm
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ActionForward
	 * @throws IOException
	 * @throws ServletException
	 */

	public ActionForward execute (ActionMapping mapping, ActionForm aForm, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException 
	{
		HttpSession session = request.getSession();
		logger.debug("entering ReviewNotifications Load");
		if (session == null) {
			logger.fatal("error no session");
			return mapping.findForward("login");
		}

		// handle contextAction parameter
		String contextAction = request.getParameter("ContextAction");
		if (contextAction == null) {
			contextAction = (String) request.getAttribute("ContextAction");
		}
		else if (contextAction != null && contextAction.equals("NNDUpdatedNotificationsAudit")) {
        	DSQueueObject dSQueueObject = new DSQueueObject();
        	dSQueueObject.setDSSortColumn("getCaseClassCdTxt");
        	dSQueueObject.setDSSortDirection("true");
        	dSQueueObject.setDSFromIndex("0");
        	dSQueueObject.setDSQueueType(NEDSSConstants.NND_UPDATED_NOTIFICATIONS_FOR_APPROVAL);
			NBSContext.store(session, "DSQueueObject", dSQueueObject);
		}

		TreeMap tm = NBSContext.getPageContext(session, "PS230", contextAction);
		ErrorMessageHelper.setErrMsgToRequest(request, "PS230");

		logger.debug("doing lookInsideTreeMap(tm)");

		NBSContext.lookInsideTreeMap(tm);

		logger.debug("doing getCurrentTask(session)");

		String sCurrTask = NBSContext.getCurrentTask(session);

		logger.debug("sCurrTask: " + sCurrTask);

		request.setAttribute("Submit",
					 "/nbs/" + "ReviewUpdatedNotifications1" + ".do?ContextAction=" +
					 tm.get("Submit"));

		request.setAttribute("SubmitButton", "/nbs/" + sCurrTask + ".do");
		request.setAttribute("ContextActionSubmit", tm.get("Submit"));

		request.setAttribute("ViewInvestigationHref", "/nbs/" + sCurrTask + ".do?ContextAction=" + tm.get("InvestigationID"));

		request.setAttribute(
			"nextHref",
			"/nbs/" + sCurrTask + ".do?ContextAction=" + tm.get("Next"));
		request.setAttribute(
			"prevHref",
			"/nbs/" + sCurrTask + ".do?ContextAction=" + tm.get("Prev"));
		request.setAttribute(
			"sortHref",
			"/nbs/" + sCurrTask + ".do?ContextAction=" + tm.get("Sort"));


		String sortMethod = request.getParameter("sortMethod");

		boolean forceEJBcall = true;
		boolean accessedEJB = false;

        if (contextAction != null && (contextAction.equals("Next") ||
        		contextAction.equals("Prev") ||contextAction.equals("Sort"))) {
        	forceEJBcall = false;
        }

        // prepare a collection of updated notifications, sort it and set it
        // in request
		try {
			ArrayList<Object> updatedNotificationsForAudit = new ArrayList<Object> ();

			// prepare the list of notifications
    		if(forceEJBcall)
        	{
        		MainSessionHolder mainSessionHolder = new MainSessionHolder();
	            MainSessionCommand msCommand = mainSessionHolder.getMainSessionCommand(session);
	            String  sBeanJndiName = JNDINames.NOTIFICATION_PROXY_EJB;
	            String  sMethod = "getUpdatedNotificationsForAudit";
	            ArrayList<Object> arr = (ArrayList<Object> )msCommand.processRequest(sBeanJndiName, sMethod, null);
	            updatedNotificationsForAudit = (ArrayList<Object> )arr.get(0);
	            accessedEJB = true;
	            NBSContext.store(session,"DSUpdatedNotificationList",updatedNotificationsForAudit);
	            NBSContext.store(session,"DSUpdatedNotificationListFull",updatedNotificationsForAudit);

        	} else {
        		try {
        		updatedNotificationsForAudit = (ArrayList<Object> )NBSContext.retrieve(session ,"DSUpdatedNotificationList");
        		}catch(Exception ex) {
        			logger.debug("DSUpdatedNotificationList is null in updated notification load");
        			updatedNotificationsForAudit = (ArrayList<Object> ) NBSContext.retrieve(request.getSession(true) , "DSUpdatedNotification");
        		}
        	}

    		// sort the list of notifications
    		if(( sortMethod != null &&  (!sortMethod.equals(""))) || accessedEJB ) 
    		{
            	if (sortMethod == null) {
            		sortMethod = ((DSQueueObject)NBSContext.retrieve(session, "DSQueueObject")).getDSSortColumn();
            	}
            		
                if (sortMethod==null || sortMethod.equals("")) {
                	sortMethod = "getCaseClassCdTxt";
                }

                String invSortDirection = request.getParameter("direction");
                if (invSortDirection == null) {
                	invSortDirection = ((DSQueueObject)NBSContext.retrieve(session, "DSQueueObject")).getDSSortDirection();
                }
                	
                boolean invDirectionFlag = false;
                if (invSortDirection != null && invSortDirection.equals("false")) {
                	invDirectionFlag = false;
                }
                else {
                	invDirectionFlag = true;
                }
                  	
                if (contextAction.equals("Sort") && invDirectionFlag) {
                	request.setAttribute("sortDirection", new Boolean(false));
                }
                else if (contextAction.equals("Sort") && !invDirectionFlag) {
                	request.setAttribute("sortDirection", new Boolean(true));
                }
                else {
                	request.setAttribute("sortDirection", new Boolean(invDirectionFlag));
                }
                	
                NedssUtils util = new NedssUtils();
                if (sortMethod != null && updatedNotificationsForAudit != null && 
                		updatedNotificationsForAudit.size() > 0) {
                  util.sortObjectByColumn(sortMethod, (Collection)updatedNotificationsForAudit, 
                		  invDirectionFlag);
                }
            }

    		// set the list of notifications in request
            request.setAttribute("updatedNotificationList", updatedNotificationsForAudit);
            this.createConditionLink(updatedNotificationsForAudit,request);

            // set the number of records to be displayed per page
			PropertyUtil properties = PropertyUtil.getInstance();
			Integer queueSize = properties.getQueueSize(NEDSSConstants.UPD_QUEUE_FOR_NOTIF_DISP_SIZE);
			request.setAttribute("maxRowCount", queueSize);

			// adjust the index to previous page if last element of the page is deleted
			String fromIndex = ((DSQueueObject) NBSContext.retrieve(session, "DSQueueObject")).getDSFromIndex();
			int index = new Integer(fromIndex).intValue();
			if (index == updatedNotificationsForAudit.size()) {
				index = index - queueSize.intValue();
				fromIndex = String.valueOf(index);
			}
			request.setAttribute("DSFromIndex", fromIndex);
			
			// page title
			request.setAttribute("PageTitle","Updated Notifications Queue");
			
			// prepare the form with filter options and set it in scope
			UpdatedNotificationsQueueForm updatedNotificationsForm = new UpdatedNotificationsQueueForm();
			NBSContext.store(session,"DSUpdatedNotificationList",updatedNotificationsForAudit);
		} 
		catch (Exception e) {
			e.printStackTrace();
			logger.error(
				"Error in ReviewUpdatedNotificationLoad in getting updated notifications for audit from EJB");
		}

		return (mapping.findForward("XSP1"));
	}
	
	public void createConditionLink(ArrayList<Object>  updatedNotificationsForAudit, HttpServletRequest request)
	{
		String viewInvestigationHref= (String)request.getAttribute("ViewInvestigationHref");
		Iterator iter = updatedNotificationsForAudit.iterator();
		
		while (iter.hasNext()) {
			UpdatedNotificationSummaryVO updated = (UpdatedNotificationSummaryVO) iter.next();
			String phcUid = String.valueOf(updated.getPublicHealthCaseUid());
			StringBuffer url = new StringBuffer();
			url.append("<a href='");
			url.append(viewInvestigationHref);
			url.append("&publicHealthCaseUID=");
			url.append(phcUid);
			url.append("'>").append(updated.getCdTxt()).append("</a>");
			updated.setConditionLink(url.toString());
		}
	}
}