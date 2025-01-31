package gov.cdc.nedss.webapp.nbs.action.notification;

import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
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


public class ReviewRejectedNotificationSubmit extends Action {

	static final LogUtils logger =
		new LogUtils(ReviewUpdatedNotificationSubmit.class.getName());


	public ActionForward execute(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws IOException, ServletException {

		HttpSession session = request.getSession();
		logger.debug("entering ReviewNotifications Submit");

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
		String mprID = request.getParameter("MPRUid");

		Long MPRUid = null;
		if(mprID!=null || (mprID!=null && mprID.trim().equalsIgnoreCase("")))
			MPRUid =new Long(mprID) ;

		NBSSecurityObj nbsSecurityObj =
			(NBSSecurityObj) session.getAttribute("NBSSecurityObject");

		if(!(nbsSecurityObj.getPermission(NBSBOLookup.NOTIFICATION, NBSOperationLookup.CREATENEEDSAPPROVAL) 
				|| (nbsSecurityObj.getPermission(NBSBOLookup.NOTIFICATION, NBSOperationLookup.CREATE)))){
					logger.fatal(
						"Error: no permisstion to CREATE/CREATENEEDSAPPROVAL  notification, go back to login screen");
					throw new ServletException("Error: no permisstion to CREATE/CREATENEEDSAPPROVAL  notification, go back to login screen");
				}

		try {
			if (contextAction != null && contextAction.equals("ViewFile")) {

				NBSContext.store(session, "DSPatientPersonUID", MPRUid );
	            NBSContext.store(session, "DSFileTab", "1");
	            
			} else if (contextAction != null && contextAction.equals("InvestigationID")) {
				
				NBSContext.store(session, NBSConstantUtil.DSInvestigationUid,investigationID);
				
			} else {
				logger.info(
					"Nothing matched. Simply forwarding on using ContextAction");
			}
			
			return mapping.findForward(contextAction);
			
		} catch (Exception e) {
			logger.error(
				"Problem happened in the ReviewUpdatedNotificationSubmit struts class",
				e);
			e.printStackTrace();
			throw new ServletException("Problem happened in the ReviewUpdatedNotificationSubmit struts class :"+e.getMessage(),e);
		}
	}

}
