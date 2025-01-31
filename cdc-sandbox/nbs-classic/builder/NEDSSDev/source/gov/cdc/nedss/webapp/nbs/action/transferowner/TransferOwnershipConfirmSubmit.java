package gov.cdc.nedss.webapp.nbs.action.transferowner;


import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.LogUtils;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class TransferOwnershipConfirmSubmit
    extends Action
{

    //For logging
    static final LogUtils logger = new LogUtils("TransferOwnershipConfirmSubmit");


    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
                          throws IOException, ServletException
    {

        HttpSession session = request.getSession();

        if (session == null)
        {
            logger.fatal("error no session");
            return mapping.findForward("login");
        }
    	//get page context for debugging purposes
    	String contextAction = request.getParameter("ContextAction");
    	try {

    		if (contextAction == null)
    			contextAction = (String)request.getAttribute("ContextAction");

    		NBSSecurityObj nbsSecurityObj = (NBSSecurityObj)session.getAttribute(
    				"NBSSecurityObject");
    		if (nbsSecurityObj == null)
    		{
    			logger.fatal(
    					"Error: no nbsSecurityObj in the session, go back to login screen");
    			return mapping.findForward("login");
    		}
    		if(request.getParameter("MPRUid")!=null){
    			Long personUID =new Long(request.getParameter("MPRUid"));
    			NBSContext.store(session,"DSPatientPersonUID",personUID);
    		}
    		NBSContext.store(session, "DSFileTab", "3");
    	}catch (Exception e) {
    		logger.error("Exception in Transfer Ownership Confirm Submit: " + e.getMessage());
    		e.printStackTrace();
    		throw new ServletException("An error occurred in Transfer Ownership Confirm Submit : "+e.getMessage());
    	} 
     return mapping.findForward(contextAction);

    }
}