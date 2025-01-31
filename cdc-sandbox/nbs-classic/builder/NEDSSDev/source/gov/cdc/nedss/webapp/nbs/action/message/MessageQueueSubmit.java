package gov.cdc.nedss.webapp.nbs.action.message;


import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.nbssecurity.ProgramAreaJurisdictionUtil;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.webapp.nbs.action.util.DSQueueObject;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * Title:        MessageQueueSubmit
 * Description:  This is a summit action class for Message to Investigation.
 * It uses Struts to route the forward action.
 * Company:      Leidos
 * @author:      Pradeep Kumar Sharma
 * @version      NBS4.5
 */

public class MessageQueueSubmit
    extends Action
{

    //For logging
    static final LogUtils logger = new LogUtils(MessageQueueSubmit.class.getName());


    /**
     * According to the Context Map<Object,Object> what the from page is and current task and security permission,
     * this method works with the message queue.
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
        logger.debug("entering MessageQueues Submit" );
        if (session == null)
    	{
   	    return mapping.findForward("login");
    	}
        
     
	//get page context for debugging purposes
	String contextAction = request.getParameter("ContextAction");
	if (contextAction == null)
	    contextAction = (String)request.getAttribute("ContextAction");
	
	String investigationID = request.getParameter("publicHealthCaseUID");
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

        if (!nbsSecurityObj.getPermission(NBSBOLookup.QUEUES,
                                          NBSOperationLookup.MESSAGE,
                                          ProgramAreaJurisdictionUtil.ANY_PROGRAM_AREA,
                                          ProgramAreaJurisdictionUtil.ANY_JURISDICTION))
        {
            logger.fatal("Error: no permisstion to review notification, go back to login screen");
            throw new ServletException("Error: no permisstion to review notification, go back to login screen");
        }

        try
        {
         if (contextAction != null && contextAction.equals("InvestigationID"))
          {
            NBSContext.store(session, NBSConstantUtil.DSInvestigationUid, investigationID);
            return mapping.findForward(contextAction);
          }
          else
          {
            logger.info("Nothing matched. Simply forwarding on using ContextAction");
            return mapping.findForward(contextAction);
          }
        }
        
        catch (Exception e)
        {
            logger.error("Problem happened in the MessageQueueSubmit struts class", e);
            e.printStackTrace();
            throw new ServletException("Problem happened in the MessageQueueSubmit struts class"+e.getMessage(),e);
        }
    }

   
    

}