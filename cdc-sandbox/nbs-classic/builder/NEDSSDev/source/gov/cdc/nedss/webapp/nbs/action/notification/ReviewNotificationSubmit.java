package gov.cdc.nedss.webapp.nbs.action.notification;


import gov.cdc.nedss.entity.entityid.dt.EntityIdDT;
import gov.cdc.nedss.exception.NEDSSAppConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.proxy.ejb.notificationproxyejb.vo.NotificationSummaryVO;
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
import gov.cdc.nedss.webapp.nbs.action.util.ErrorMessageHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.ejb.EJBException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.sas.rmi.RemoteException;

/**
 * Title:        ReviewNotificationSubmit
 * Description:  This is a summit action class for sending reviewed notification.
 * It uses Struts to route the forward action.
 * Copyright:    Copyright (c) 2002
 * Company:      Computer Sciences Corporation
 * @author:      NEDSS Development Team
 * @version      NBS1.1
 */

public class ReviewNotificationSubmit
    extends Action
{

    //For logging
    static final LogUtils logger = new LogUtils(ReviewNotificationSubmit.class.getName());

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
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
                          throws IOException, ServletException
    {
    	
    	
    	HttpSession session = request.getSession();
    	logger.debug("entering ReviewNotifications Submit" );
    	if (session == null)
    	{
    		return mapping.findForward("login");
    	}

    	try {
    		//get page context for debugging purposes
    		String contextAction = request.getParameter("ContextAction");
    		if (contextAction == null)
    			contextAction = (String)request.getAttribute("ContextAction");

    		String investigationID = request.getParameter("publicHealthCaseUID");
    		String type = request.getParameter("notificationCd");
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

    		NBSSecurityObj nbsSecurityObj = (NBSSecurityObj)session.getAttribute("NBSSecurityObject");

    		if (!nbsSecurityObj.getPermission(NBSBOLookup.NOTIFICATION,
    				NBSOperationLookup.REVIEW,
    				ProgramAreaJurisdictionUtil.ANY_PROGRAM_AREA,
    				ProgramAreaJurisdictionUtil.ANY_JURISDICTION))
    		{
    			logger.fatal("Error: no permisstion to review notification, go back to login screen");
    			throw new ServletException("Error: no permisstion to review notification, go back to login screen");
    		}

    		try
    		{
    			if (contextAction != null && contextAction.equals("Reject"))
    			{
    				this.rejectNotification(request, response);
    				return mapping.findForward(contextAction);
    			}
    			else if (contextAction != null && contextAction.equals("Approve"))
    			{

    				this.approveNotification(request, response);
    				return mapping.findForward(contextAction);
    			}
    			else if (contextAction != null && contextAction.equals("InvestigationID"))
    			{
    				NBSContext.store(session, NBSConstantUtil.DSInvestigationUid, investigationID);
    				return mapping.findForward(contextAction);
    			}
    			else if(contextAction != null && contextAction.equalsIgnoreCase("ViewFile")) {

    				String MPRUidString = (String)request.getParameter("MPRUid");
    				Long MPRUid =new Long(MPRUidString) ;
    				NBSContext.store(session, "DSPatientPersonUID", MPRUid );
    				NBSContext.store(session, "DSFileTab", "1");
    				return mapping.findForward(contextAction);

    			}
    			else
    			{
    				logger.info("Nothing matched. Simply forwarding on using ContextAction");
    				return mapping.findForward(contextAction);
    			}
    		}
    		catch(NEDSSAppConcurrentDataException concurrentException)
    		{
    			logger.fatal("ERROR - NEDSSAppConcurrentDataException, The data has been modified by another user, please recheck! ",	concurrentException);
    			return mapping.findForward("dataerror");
    		}
    		catch (Exception e)
    		{
    			logger.error("Problem happened in the ReviewNotificationSubmit struts class", e);
    			e.printStackTrace();
    			throw new ServletException("Problem happened in the ReviewNotificationSubmit struts class"+e.getMessage(),e);
    		}
    	}catch (Exception e) {
    		logger.error("Exception in Review Notification Submit: " + e.getMessage());
    		e.printStackTrace();
    		throw new ServletException("Error occurred in Review Notification Submit : "+e.getMessage());
    	}        
    }

    /**
     * This method calls approveNotification() method in NotificationProxyEJB through MainSessionCommandEJB
     * @param HttpServletRequest
     * @param HttpServletResponse
     * @throws NEDSSAppConcurrentDataException
     */

    private void approveNotification(HttpServletRequest request,
                                     HttpServletResponse response)
        throws Exception,NEDSSAppConcurrentDataException
    {

 	   String sBeanJndiName = "";
 	   String sMethod = "";
 	   Object[] oParams = null;
      HttpSession session = request.getSession();

      String publicHealthCaseLocalID = request.getParameter("publicHealthCaseLocalID");
      Long publicHealthCaseUID = new Long(request.getParameter("publicHealthCaseUID"));
      String type = request.getParameter("notificationCd");
           
         
      {
    	
        MainSessionCommand msCommand = null;
        String notID = "";
        String confirmationMessage = "";
        MainSessionHolder mainSessionHolder = new MainSessionHolder();
        try
        {

          //ArrayList<Object> notificationList = (ArrayList<Object> )session.getAttribute("notificationList");
        	ArrayList<?> notificationList  = (ArrayList<?> )NBSContext.retrieve(session ,"DSNotificationList");
       
         
    	  String value = request.getParameter("notificationIndex");
          if (value != null )
              value = value.trim();


          NotificationSummaryVO notSumVO = null;
          for(int i=0; i<notificationList.size(); i++)
          {
            notSumVO = (NotificationSummaryVO)notificationList.get(i);
            if( (notSumVO!=null) && (notSumVO.getNotificationUid().toString().equals( value ) ) )
            break;
          }
          if(notSumVO == null)
          {
             logger.info( "No record with PublicHealthCaseUid of " + value + " was found.  Ignoring and continuing." );
             return;
          }


            notSumVO.setItDirty(true);
            notSumVO.setItNew(false);

            String cd = notSumVO.getCd();
            session.setAttribute("DSInvestigationCondition", cd);

            //String newComments = request.getParameter("comments_" + value);
            String newComments = request.getParameter("apprRejComments");
            if (newComments != null )
              newComments = newComments.trim();
            else
              newComments = "";
            notSumVO.setTxt(newComments);
            notSumVO.setItDirty(true);
            notSumVO.setItNew(false);
            msCommand = mainSessionHolder.getMainSessionCommand(session);
            sBeanJndiName = JNDINames.NOTIFICATION_PROXY_EJB;

            //-----approveNotification
            sMethod = "approveNotification";
            oParams = new Object[] { notSumVO };
            msCommand = mainSessionHolder.getMainSessionCommand(session);
            ArrayList<?> arr = (ArrayList<?> )msCommand.processRequest(sBeanJndiName, sMethod, oParams);
            Boolean completed = (Boolean)arr.get(0);
            notID = notSumVO.getLocalId();
            if (notID == null)
            notID = "";

            if (completed.booleanValue() == true)
            {
              confirmationMessage = ErrorMessageHelper.makeErrMessage("ERR041");
              confirmationMessage = ErrorMessageHelper.replace(confirmationMessage, "Notification ID", notID);
            }
            else
                confirmationMessage = "The system could not approve notification " + notID +
                                      ".\n  Please create a new notification and try again.";

            request.setAttribute("confirmationMessage", confirmationMessage);
      
        }
        
        catch (NEDSSAppConcurrentDataException e)
        {
            logger.fatal( "ERROR - NEDSSAppConcurrentDataException, The data has been modified by another user, please recheck! ", e);
            throw new NEDSSAppConcurrentDataException(e.getMessage());
        }
      }
     
    }

    /**
     * This is a private method. This method calls rejectNotification() method in NotificationProxyEJB
     * through MainSessionCommandEJB
     * @param HttpServletRequest
     * @param HttpServletResponse
     * @throws NEDSSAppConcurrentDataException
     * @throws Exception
     */
    private void rejectNotification(HttpServletRequest request,
                                    HttpServletResponse response)
	    throws Exception, NEDSSAppConcurrentDataException

    {
      HttpSession session = request.getSession();

	   String sBeanJndiName = "";
	   String sMethod = "";
	   Object[] oParams = null;
      String publicHealthCaseLocalID = request.getParameter("publicHealthCaseLocalID");
      Long publicHealthCaseUID = new Long(request.getParameter("publicHealthCaseUID"));

      {

        MainSessionHolder mainSessionHolder = new MainSessionHolder();
        MainSessionCommand msCommand = null;
        String confirmationMessage = "";
        String notID = "";

        try
        {
            //ArrayList<Object> notificationList = (ArrayList<Object> )session.getAttribute("notificationList");
            
            ArrayList<?> notificationList  = (ArrayList<?> )NBSContext.retrieve(session ,"DSNotificationList");
            
            String value = request.getParameter("notificationIndex");
            if (value!=null) value = value.trim();
            logger.info("Value of the index you are rejecting is " + value);
            request.setAttribute("currentIndex", value);


            NotificationSummaryVO notSumVO = null;
            for(int i=0; i<notificationList.size(); i++)
            {
              notSumVO = (NotificationSummaryVO)notificationList.get(i);
              if( (notSumVO!=null) && (notSumVO.getNotificationUid().toString().equals( value ) ) )
              break;

            }
            if(notSumVO == null )
            {
              logger.info( "No record with PublicHealthCaseUid of " + value + " was found.  Ignoring and continuing." );
              return;
            }


            notSumVO.setItDirty(true);
            //String newComments = request.getParameter("comments_" + value);
            String newComments = request.getParameter("apprRejComments");
            if (newComments != null )
              newComments = newComments.trim();
            else
              newComments = "";
            String originalComments = notSumVO.getTxt();
            notID = notSumVO.getLocalId();

            if (notID == null)
            notID = "";

            confirmationMessage = ErrorMessageHelper.makeErrMessage("ERR040");
            confirmationMessage = ErrorMessageHelper.replace(confirmationMessage, "Notification ID", notID);

            if (newComments==null)
            newComments = "";
            if (originalComments==null)
            originalComments = "";

           if ((newComments.trim().length()==0) ||
            	(newComments.trim().compareTo(originalComments.trim()) == 0) )
            {
              //this means they didn't add a rejection reason.
              confirmationMessage = ErrorMessageHelper.makeErrMessage("ERR039");
              request.setAttribute("confirmationMessage", confirmationMessage);
              return;
            }

            notSumVO.setTxt(newComments);
            notSumVO.setItNew(false);
            notSumVO.setItDirty(true);
            msCommand = mainSessionHolder.getMainSessionCommand(session);
            sBeanJndiName = JNDINames.NOTIFICATION_PROXY_EJB;
            sMethod = "rejectNotification";
            oParams = new Object[] { notSumVO };

            ArrayList<?> arr = (ArrayList<?> )msCommand.processRequest(sBeanJndiName,
                                                                sMethod,
                                                                oParams);
            logger.debug("Confirmation Message: " + confirmationMessage);
            request.setAttribute("confirmationMessage", confirmationMessage);
        }
	catch (NEDSSAppConcurrentDataException e)
        {
            logger.fatal("ERROR - NEDSSAppConcurrentDataException, The data has been modified by another user, please recheck! ", e);
            throw new NEDSSAppConcurrentDataException(e.getMessage());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            logger.error("Error in reject Notification in ReviewNotificationSubmit.java :" + e.getMessage());
        }
      }
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
				HttpSession session)
			 throws java.rmi.RemoteException,
				javax.ejb.EJBException, NEDSSSystemException
    {
      try
      {
	MainSessionCommand msCommand = null;
	String sBeanJndiName = JNDINames.NOTIFICATION_PROXY_EJB;
	String sMethod = "validateNNDIndividualRequiredFields";
	Object[] oParams = {publicHealthCaseUID};
	MainSessionHolder holder = new MainSessionHolder();
	msCommand = holder.getMainSessionCommand(session);

	ArrayList<?> resultUIDArr = new ArrayList<Object> ();
	resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod,
						oParams);
	 if ((resultUIDArr != null) && (resultUIDArr.size() > 0))
	 {
	    ArrayList<?> result = (ArrayList<?> )resultUIDArr.get(0);
            return result;
	 }
	 else
         {
            return null;
	 }
        }
	catch (Exception e)
	{
	    e.printStackTrace();
            logger.fatal("ERROR calling mainsession control", e);
	    throw new NEDSSSystemException(e.getMessage());
	}
      } //sendProxyToEJB

    
    
    

}