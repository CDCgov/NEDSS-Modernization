package gov.cdc.nedss.webapp.nbs.action.alert.manage;


import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.webapp.nbs.action.alert.AlertAdminConstants;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class ManageAlertAdminAction extends DispatchAction {
		private static final Logger logger = Logger.getLogger(ManageAlertAdminAction.class);
	    private static CachedDropDownValues cdv = new CachedDropDownValues();

	    public ManageAlertAdminAction()
	    {
	    }

	    public ActionForward manageAlert(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	    {
	    	try{
	    		HttpSession session = request.getSession();
			    NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session.getAttribute(
			        "NBSSecurityObject");
			    boolean securityCheck = nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,NBSOperationLookup.DECISION_SUPPORT_ADMIN);
			    if (securityCheck != true) {
			      session.setAttribute("error", "Failed at security checking.");
			      throw new ServletException("Failed at security checking.");
			    }  	
	    	logger.debug("manageAlert called");
	        return mapping.findForward("manageAlert");
	    }	
	    finally{
			request.setAttribute(AlertAdminConstants.PAGE_TITLE ,AlertAdminConstants.ALERT_ADMIN);
		}
	}
}