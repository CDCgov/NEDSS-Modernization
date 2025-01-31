package gov.cdc.nedss.webapp.nbs.action.myinvestigation;

import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.nbssecurity.ProgramAreaJurisdictionUtil;
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

 /**
 * Title:         ProgramAreaSubmit is an action class
 * Description:   This is actions class to Provides next action
 * Copyright:     Copyright (c) 2001
 * Company:       CSC
 * @author        Nedss Team
 * @version       1.0
 */
public class ProgramAreaSubmit extends Action {

    //For logging
    static final LogUtils logger = new LogUtils("ProgramAreaSubmit");

     /**
      * Get values from investigation form and forward to next action.
      * @param ActionMapping the mapping
      * @param ActionForm the form contain values
      * @param HttpServletRequest the request
      * @param HttpServletResponse the response
      * @return  ActionForward Object
      * @throws ServletException and IOException
      */

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
	if (contextAction == null)
		contextAction = (String)request.getAttribute("ContextAction");
    NBSSecurityObj nbsSecurityObj = (NBSSecurityObj)session.getAttribute("NBSSecurityObject");

    if (nbsSecurityObj == null) {
            logger.fatal("Error: no nbsSecurityObj in the session, go back to login screen");
            return mapping.findForward("login");
        }
    try {
    	logger.debug(NBSBOLookup.INVESTIGATION);
    	logger.debug(NBSOperationLookup.VIEW);
    	logger.debug(ProgramAreaJurisdictionUtil.ANY_PROGRAM_AREA);
    	logger.debug(ProgramAreaJurisdictionUtil.ANY_JURISDICTION);
    	logger.debug("get permissions = " + nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,
    			NBSOperationLookup.VIEW,
    			ProgramAreaJurisdictionUtil.ANY_PROGRAM_AREA,
    			ProgramAreaJurisdictionUtil.ANY_JURISDICTION));

    	if (!nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,
    			NBSOperationLookup.VIEW,
    			ProgramAreaJurisdictionUtil.ANY_PROGRAM_AREA,
    			ProgramAreaJurisdictionUtil.ANY_JURISDICTION))
    	{
    		logger.fatal(
    				"Error: no permisstion for ProgramArea, go back to login screen");

    						return mapping.findForward("login");
    	}



    	String sUid = request.getParameter("publicHealthCaseUID");

    	if( sUid !=null)

    		NBSContext.store(session, NBSConstantUtil.DSInvestigationUid, sUid);

    	if(contextAction != null && contextAction.equalsIgnoreCase("ViewFile")) {

    		String MPRUidString = (String)request.getParameter("MPRUid");

    		Long MPRUid =new Long(MPRUidString) ;
    		NBSContext.store(session, "DSPatientPersonUID", MPRUid );
    		NBSContext.store(session, "DSFileTab", "1");        	
    	}
    }catch (Exception e) {
    	logger.error("Exception in PASubmit: " + e.getMessage());
    	e.printStackTrace();
    	throw new ServletException("Error occurred in ProgramAreaSubmit : "+e.getMessage());
    }
        
     return mapping.findForward(contextAction);

    }

}