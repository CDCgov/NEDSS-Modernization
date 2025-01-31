package gov.cdc.nedss.webapp.nbs.action.tasklist;

import java.io.*;
import java.util.*;
import javax.servlet.http.*;
import javax.servlet.*;

import org.apache.struts.action.*;

import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.InvestigationSummaryVO;
import gov.cdc.nedss.proxy.ejb.notificationproxyejb.vo.NotificationSummaryVO;
import gov.cdc.nedss.proxy.ejb.tasklistproxyejb.vo.TaskListItemVO;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;


public class TaskListDisplayLoad extends Action {
  //For logging
  static final LogUtils logger = new LogUtils(TaskListDisplayLoad.class.getName());
  

   public ActionForward execute(ActionMapping mapping,
                                 ActionForm aForm,
                                 HttpServletRequest request,
                                 HttpServletResponse response)

   throws IOException, ServletException
   {
	 InvestigationSummaryVO invSumVO  = null;
	 NotificationSummaryVO notSumVO  = null;
	   
    String sBeanJndiName = JNDINames.TASKLIST_PROXY_EJB;
    String sMethod = "getMyInvestigations";

    MainSessionCommand msCommand = null;
    MainSessionHolder mainSessionHolder = new MainSessionHolder();
    logger.info("Inside execute method of TaskListDisplayLoad");
    int operationType=NEDSSConstants.UNKNOWN_OPERATION;
    String sObjectType;
    String sOperationType;
   
    HttpSession session = request.getSession(false);

    sObjectType = request.getParameter(PageConstants.OBJECTTYPE);
    sOperationType = request.getParameter(PageConstants.OPERATIONTYPE);
    logger.debug("The objectType is: " + sObjectType);
    logger.debug("The operationType is: " + sOperationType);
    if (sOperationType != null){
      try{
        operationType = ( new Integer(sOperationType).intValue());
      }
      catch(Exception ne) {
        logger.fatal("TaskListDisplayLoad had error:", ne);
        ne.printStackTrace();
        operationType = NEDSSConstants.UNKNOWN_COMMAND;
      }
    }
     else
      operationType = NEDSSConstants.UNKNOWN_COMMAND;

    switch (operationType){

       case(NEDSSConstants.GET_TASKLIST_ITEMS):
    	   logger.debug("calling getTaskListItems on TaskListProxyEJB");

         try
            {
	        msCommand = mainSessionHolder.getMainSessionCommand(session);
            } catch( Exception e )
            {
                  logger.error("Error in taskListDisplayLoad in getting mainSessionHolder: " +e.getMessage() );
                  throw new ServletException("Error in taskListDisplayLoad in getting mainSessionHolder"+e.getMessage(),e);
            }

      logger.info("About to call processRequest.");
      logger.debug("Checkpoint - #1");
      try{
        ArrayList<Object> arr = (ArrayList<Object> )msCommand.processRequest(sBeanJndiName, sMethod, null);
        logger.debug("Checkpoint - #2");
        ArrayList<Object> taskListItemVOs = (ArrayList<Object> )arr.get(0);
        if (taskListItemVOs!=null){
         Iterator<Object>  taskListVOIter = taskListItemVOs.iterator();
	  logger.info("THE NUMBER OF TASKLIST ITEMS IS: " + taskListItemVOs.size());
          while(taskListVOIter.hasNext())
          {
             TaskListItemVO taskListItemVO = (TaskListItemVO)taskListVOIter.next();
             logger.debug("Checkpoint - #3");
             Integer count = taskListItemVO.getTaskListItemCount();
             logger.debug("Checkpoint - #4");
	     String taskListCount = count.toString();
             String taskListName = taskListItemVO.getTaskListItemName();
	     logger.info("Got the taskListItem: " + taskListName );
             session.setAttribute(taskListName + "Size", taskListCount);

          }
        }
      }catch(Exception e){
    	  logger.error("Error in taskListDisplayLoad in getting taskListItems from EJB: " + e.getMessage());
    	  e.printStackTrace();

      }
   
      return(mapping.findForward("start"));


      case(NEDSSConstants.GET_MY_INVESTIGATIONS):

	  logger.info("You are trying to GET_MY_INVESTIGATIONS");
	  logger.debug("calling getMyInvestigations on TaskListProxyEJB from taskListDisplayLoad");
            try
            {
	        msCommand = mainSessionHolder.getMainSessionCommand(session);
            } catch( Exception e )
            {
                  logger.error("Error occurred in taskListDisplayLoad in getting mainSessionHolder: " + e.getMessage() );
                  e.printStackTrace();
                  throw new ServletException("Error in taskListDisplayLoad in getting mainSessionHolder"+e.getMessage(),e);
            }

	    logger.info("About to call processRequest.");
	    logger.debug("Checkpoint - #1");
	    try
            {
	        ArrayList<Object> arr = (ArrayList<Object> )msCommand.processRequest(sBeanJndiName, sMethod, null);
                logger.debug("Checkpoint - #2");
                ArrayList<Object> myInvSummaryVOs = (ArrayList<Object> )arr.get(0);
	        if(myInvSummaryVOs !=null && myInvSummaryVOs.size()!=0)
                {
		  NedssUtils util = new NedssUtils();
		  String sortMethod = "getActivityFromTime";
		  util.sortObjectByColumn(sortMethod, (Collection)myInvSummaryVOs, false);
		  session.setAttribute("sortDirection", new Boolean(true));
		  session.setAttribute("investigationList", myInvSummaryVOs);
	        }
	    } catch(Exception e)
	    {
		  logger.error("Error in taskListDisplayLoad in getting taskListItems from EJB: " + e.getMessage());
		  e.printStackTrace();
	    }
            session.setAttribute("sortDirection", new Boolean(true));
            return (mapping.findForward("MyProgramsInvestigations"));

         case (NEDSSConstants.INVESTIGATION_SORT):
          ArrayList<Object> investigationArray = (ArrayList<Object> )session.getAttribute("investigationList");
          if( investigationArray != null && investigationArray.size()!= 0)
	  {
              invSumVO = (InvestigationSummaryVO)investigationArray.get(0);
	  }
	  else
	  {
              return (mapping.findForward("MyProgramsInvestigations"));
	  }

	  String invSortDirection = request.getParameter("direction");
	  boolean invDirectionFlag = true;
	  if(invSortDirection!=null && invSortDirection.equals("false")){
	       invDirectionFlag = false;
	       session.setAttribute("sortDirection", new Boolean(true));
	  }
	  else{
	       invDirectionFlag = true;
	       session.setAttribute("sortDirection", new Boolean(false));
	  }
	  String sortMethod = request.getParameter("SortMethod");
          logger.debug("Sort method is:" + sortMethod);
          // just sort existing sku list because a sort mthhod was included
          if(sortMethod != null && invSumVO != null){
               NedssUtils util = new NedssUtils();
               util.sortObjectByColumn(sortMethod,(Collection<Object>) investigationArray, invDirectionFlag);
               session.setAttribute("investigationList", investigationArray);
          }
        return (mapping.findForward("MyProgramsInvestigations"));


         case(NEDSSConstants.GET_NOTIFICATIONS_FOR_APPROVAL):

	 String currentIndex = request.getParameter("currentIndex");
	 if(currentIndex==null)
	    currentIndex="";
	 logger.info("You are trying to GET_NOTIFICATIONS_FOR_APPROVAL");
	 sBeanJndiName = JNDINames.NOTIFICATION_PROXY_EJB;
	 sMethod = "getNotificationsForApproval";
	 logger.debug("calling getNotificationsForApproval on NotificationProxyEJB from taskListDisplayLoad");


	     try
            {
	        msCommand = mainSessionHolder.getMainSessionCommand(session);
            } catch( Exception e )
            {
                  logger.error("Error in taskListDisplayLoad in getting mainSessionHolder: " +e.getMessage() );
                  e.printStackTrace();
                  throw new ServletException("Error in taskListDisplayLoad in getting mainSessionHolder"+e.getMessage(),e);
            }

	 logger.info("About to call processRequest.");
	 logger.debug("Checkpoint - #1");
	 try{
	   ArrayList<Object> arr = (ArrayList<Object> )msCommand.processRequest(sBeanJndiName, sMethod, null);
           logger.debug("Checkpoint - #2");
           ArrayList<Object> notificationsForApproval = (ArrayList<Object> )arr.get(0);
	   logger.info("Number of notifications for approval in array is: " + notificationsForApproval.size());
	   if(notificationsForApproval==null)
	        logger.debug("THERE ARE NO NOTIFICATIONS FOR APPROVAL");
	   if(notificationsForApproval.size()==0)
	   {
	        session.setAttribute("notificationList", notificationsForApproval);
		
                return (mapping.findForward("NotificationReview"));
	   }
	   NedssUtils util = new NedssUtils();
	  sortMethod = "getAddTime";
           util.sortObjectByColumn(sortMethod, (Collection)notificationsForApproval, false);
	   session.setAttribute("notificationList", notificationsForApproval);
	 }
	 catch(Exception e)
	 {
	       logger.error("Error in taskListDisplayLoad in getting notifications for approval from EJB: " +e.getMessage());
	       e.printStackTrace();
	 }
	 if(currentIndex=="")
	    return (mapping.findForward("NotificationReview"));
	 else
	    request.setAttribute("currentIndex", currentIndex );
            return (mapping.findForward("NotificationReview"));

    	case (NEDSSConstants.SORT_NOTIFICATION_LIST):
          ArrayList<Object> notificationArray = (ArrayList<Object> )session.getAttribute("notificationList");
          if( notificationArray != null && notificationArray.size()!=0)
	  {
              notSumVO = (NotificationSummaryVO)notificationArray.get(0);
	  }
	  else
	  {
            return (mapping.findForward("NotificationReview"));
	  }

	  //The following code is needed to implement sorting ascending and descending
	  //The "sortDirection" session attribute it available to be used by any page.
	  //There is no need to create one for each page that implements sorting.

	  //1.  The "direction" Parameter is tagged to the urlString on the XSP page
	  String notifSortDirection = request.getParameter("direction");
	  //2.  declaring a boolean variable
	  boolean notifDirectionFlag = true;
	  //3.  Checking the value of the direction that was passed through the url from
	  //    the XSP.  This will tell you what directionFlag to pass to the sortObjectByColumn
	  //    method on NedssUtils.  You will also re-set the "sortDirection" attribute on the
	  //    session to the opposite value that was passed in by the url.
	  if(notifSortDirection!=null && notifSortDirection.equals("false")){
	       notifDirectionFlag = false;
	       session.setAttribute("sortDirection", new Boolean(true));
	  }
	  else{
	       notifDirectionFlag = true;
	       session.setAttribute("sortDirection", new Boolean(false));
	  }
	  //4.  Get the "sortMethod" parameter from the url that the XSP passed in.
	  sortMethod = request.getParameter("SortMethod");

          //5.  This makes sure that you have something to sort
          if(sortMethod != null && notSumVO != null){
               NedssUtils util = new NedssUtils();
          //6.  Now that you do have a list to sort call the sortObjectByColumn method
	       util.sortObjectByColumn(sortMethod, (Collection)notificationArray, notifDirectionFlag);
          //7.  Re-set the attribute used for the list with the newly sorted version.
	       session.setAttribute("notificationList", notificationArray);
          }
	  return (mapping.findForward("NotificationReview"));
       }
    throw new ServletException();
   }
}
