package gov.cdc.nedss.webapp.nbs.action.observation.review;


import gov.cdc.nedss.util.*;
import gov.cdc.nedss.webapp.nbs.action.util.DSQueueObject;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.systemservice.nbscontext.*;
import gov.cdc.nedss.entity.person.vo.*;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.struts.action.*;


public class ObservationsReviewSubmit
    extends Action
{

    //For logging
    static final LogUtils logger = new LogUtils("ObservationsReviewSubmit");

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
                          throws IOException, ServletException
    {

        HttpSession session = request.getSession();

        if (session == null){
            logger.fatal("error no session");
            return mapping.findForward("login");
        }

	//get page context for debugging purposes
	String contextAction = request.getParameter("ContextAction");
	try {
        if (contextAction == null)
            contextAction = (String)request.getAttribute("ContextAction");

        String currentIndex = request.getParameter("currentIndex");
    	String sortColumn = request.getParameter("sortMethod");
    	String sortDirection = request.getParameter("direction");
    	if(currentIndex == null)
    	    currentIndex = (String)request.getAttribute("currentIndex");
    	logger.debug("currentIndex: " + currentIndex);
    	//update the QueueObject with new values
    	DSQueueObject dsQueueObject = (DSQueueObject)NBSContext.retrieve(session, "DSQueueObject");
    	if(currentIndex != null)
    		dsQueueObject.setDSFromIndex(currentIndex);
        if(sortColumn != null)
        	dsQueueObject.setDSSortColumn(sortColumn);
        if(sortDirection !=null)
        	dsQueueObject.setDSSortDirection(sortDirection);


        NBSSecurityObj nbsSecurityObj = (NBSSecurityObj)session.getAttribute(
                                                "NBSSecurityObject");

        if (nbsSecurityObj == null){
            logger.fatal(
                    "Error: no nbsSecurityObj in the session, go back to login screen");

            return mapping.findForward("login");
        }


        PersonSummaryVO DSPersonSummary = new PersonSummaryVO();
        String sID = request.getParameter("subjectID");
        if( sID !=null)
        	DSPersonSummary.setPersonUid( new Long(sID) );
        
        String MPRUidString = (String)request.getParameter("MPRUid");
        String DSObservationUIDString = request.getParameter("observationUID");
        if(DSObservationUIDString!=null)
        	NBSContext.store(session,"DSObservationUID",DSObservationUIDString);
        if(MPRUidString!=null){
            Long MPRUid =new Long(MPRUidString) ;
            NBSContext.store(session, "DSPatientPersonUID", MPRUid );
        }
       	NBSContext.store(session, "DSFileTab", "1");
	}catch (Exception e) {
		logger.error("Exception in ObservationsReviewSubmit: " + e.getMessage());
		e.printStackTrace();
		throw new ServletException("General error occurred in Observations Review Submit : "+e.getMessage());
	}
     return mapping.findForward(contextAction);

    }
}