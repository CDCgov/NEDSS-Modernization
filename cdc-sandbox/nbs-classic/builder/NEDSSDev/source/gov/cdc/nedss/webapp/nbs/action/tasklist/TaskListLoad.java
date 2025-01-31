package gov.cdc.nedss.webapp.nbs.action.tasklist;

/**
 * Title:        Actions
 * Description:  TaskListLoad
 * Copyright:    Copyright (c) 2001
 * Company:      CSC
 * @author
 * @version 1.0
 */

import gov.cdc.nedss.proxy.ejb.tasklistproxyejb.vo.TaskListItemVO;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.webapp.nbs.action.ldf.BaseLdf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;



public class TaskListLoad extends BaseLdf{

	static final LogUtils logger = new LogUtils(TaskListLoad.class.getName());

  public TaskListLoad() {
  }


  public ActionForward execute(ActionMapping mapping,
				 ActionForm form,
				 HttpServletRequest request,
				 HttpServletResponse response)
   throws IOException, ServletException
   {
      MainSessionCommand msCommand = null;
      MainSessionHolder mainSessionHolder = new MainSessionHolder();

      //Temp fix for Varicella PAM default tab
      request.getSession().removeAttribute("SupplementalInfo");
      HttpSession session = request.getSession(false);
      NBSSecurityObj secObj = (NBSSecurityObj)session.getAttribute("NBSSecurityObject");

      /***************************************
       *
       */
      TreeMap<Object,Object> tm = NBSContext.getPageContext(session,"PS072","GlobalHome");
      String currentTaskName = NBSContext.getCurrentTask(session);
      NBSContext.lookInsideTreeMap(tm);

      /****************************************
       *
       */
      String contextAction = request.getParameter("ContextAction");
      if (contextAction == null)
	 contextAction = (String) request.getAttribute("ContextAction");
      String sCurrTask = NBSContext.getCurrentTask(session);

      request.setAttribute("NNDApprovalHref",
			     "/nbs/" + sCurrTask + ".do?ContextAction=" +
			     tm.get("NNDApproval")+"&initLoad=true");


      request.setAttribute("NNDUpdatedNotificationsAudit",
			     "/nbs/" + sCurrTask + ".do?ContextAction=" +
  				 tm.get("NNDUpdatedNotificationsAudit")+"&initLoad=true");

      request.setAttribute("NNDRejectedNotifications",
			     "/nbs/" + sCurrTask + ".do?ContextAction=" +
			     tm.get("NNDRejectedNotifications")+"&initLoad=true");

	  request.setAttribute("ObsAssignHref",
			     "/nbs/" + sCurrTask + ".do?ContextAction=" +
			     tm.get("ObsAssign")+"&initLoad=true");
      request.setAttribute("ReviewHref",
			     "/nbs/" + sCurrTask + ".do?ContextAction=" +
			     tm.get("Review")+"&initLoad=true");


   logger.debug("Begin execution of TaskListLoad.execute method");

	String sBeanJndiName = JNDINames.TASKLIST_PROXY_EJB;
	String sMethod = "getTaskListItems";


	try{
	  msCommand = mainSessionHolder.getMainSessionCommand(session);

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
	       logger.info("Got the taskListItem: " + taskListName + " - count = " + taskListCount);
	       request.setAttribute(taskListName + "Size", taskListCount);

	    }
	    logger.debug("End execution of TaskListLoad.execute method before LDF creation");
	      
	    createLDFsForHomePage(request, null, "extXSP");

	    logger.debug("End execution of TaskListLoad.execute method after LDF creation");
	  }
	}catch(Exception e){
	   logger.error("Error in taskListProcessor in getting taskListItems from EJB: " + e.getMessage());
	  e.printStackTrace();
	}
      return mapping.findForward("XSP");
   }


}
