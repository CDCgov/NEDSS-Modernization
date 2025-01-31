package gov.cdc.nedss.webapp.nbs.action.deduplication;

/**
 * Title:        MergeConfirmationLoad
 * Description:
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


import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.systemservice.nbscontext.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.*;

import gov.cdc.nedss.entity.person.dt.*;
import gov.cdc.nedss.entity.person.vo.*;
import gov.cdc.nedss.locator.dt.*;
import gov.cdc.nedss.webapp.nbs.form.person.*;
import gov.cdc.nedss.entity.entityid.dt.EntityIdDT;
import gov.cdc.nedss.association.dt.RoleDT;
import gov.cdc.nedss.deduplication.vo.MergeConfirmationVO;
import gov.cdc.nedss.webapp.nbs.action.person.util.PersonUtil;

//for the old way using entity
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

public class MergeConfirmationLoad extends Action
{

	//For logging
	static final LogUtils logger = new LogUtils(MergeConfirmationLoad.class.getName());

	public MergeConfirmationLoad()
	{
	}

	   /**
      * Handles the loading of the page for comparing the two records for merging.
      * @J2EE_METHOD  --  execute
      * @param mapping       the ActionMapping
      * @param form     the ActionForm
      * @return request    the  HttpServletRequest
      * @return response    the  HttpServletResponse
      * @throws IOException
      * @throws ServletException
      **/
	public ActionForward execute(ActionMapping mapping,
				 ActionForm form,
				 HttpServletRequest request,
				 HttpServletResponse response)
	throws IOException, ServletException
	{
          HttpSession session = request.getSession(false);
          NBSSecurityObj secObj = (NBSSecurityObj)session.getAttribute(
                                        "NBSSecurityObject");
          String contextAction = request.getParameter("ContextAction");
            if (contextAction == null)
            contextAction = (String)request.getAttribute("ContextAction");

              if(contextAction.equals("Merge"))
              {
                TreeMap<Object,Object> tm = NBSContext.getPageContext(session, "PS208",
                                                       contextAction);
                String sCurrTask = NBSContext.getCurrentTask(session);
                request.setAttribute("TaskName",sCurrTask);
                NBSContext.lookInsideTreeMap(tm);
                request.setAttribute("returnToFindPatientHref",
                                     sCurrTask + ".do?ContextAction=" +
                                     tm.get("ReturnToFindPatient")+"&Mode1=ManualMerge");
                request.setAttribute("returnToHomePageHref",
                                     sCurrTask + ".do?ContextAction=" +
                                     tm.get("ReturnToHomePage"));
                request.setAttribute("returnToMergeCandidateList",sCurrTask + ".do?ContextAction=" +
                                     tm.get("ReturnToMergeCandidateList"));
                                request.setAttribute("retMergeConfirmationVO",
                                     NBSContext.retrieve(session, "DSMergeConfirmation"));
                                
             //   ArrayList<Object> mergeConfirmationVO1 =  (ArrayList<Object>)NBSContext.retrieve(session, "DSMergeConfirmation");
               // String confirmationMergeMessage = createConfirmationMessage(mergeConfirmationVO1);
               // session.setAttribute("confirmationMessage", confirmationMergeMessage);
                return mapping.findForward("submit");
               }
              else if(contextAction.equals("ReturnToHomePage"))
              {
                return mapping.findForward(contextAction);
              }
              else if(contextAction.equals("ReturnToFindPatient"))
              {
              	request.setAttribute("MergePatient", "true");
                return mapping.findForward(contextAction);
              }
               else if(contextAction.equals("ReturnToMergeCandidateList"))
              {
                return mapping.findForward(contextAction);
              }


              return null;
	}



}