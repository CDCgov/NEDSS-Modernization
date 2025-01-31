package gov.cdc.nedss.webapp.nbs.action.observation.review;




import gov.cdc.nedss.systemservice.nbscontext.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.webapp.nbs.action.util.DSQueueObject;


import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.struts.action.*;


public class ObservationsAssignmentSubmit
    extends Action
{

    //For logging
    static final LogUtils logger = new LogUtils(ObservationsAssignmentSubmit.class.getName());

    

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
		logger.debug("contextAction :" + contextAction);

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


		NBSSecurityObj nbsSecurityObj = (NBSSecurityObj)session.getAttribute("NBSSecurityObject");
		if (nbsSecurityObj == null) {
			logger.fatal(
					"Error: no nbsSecurityObj in the session, go back to login screen");
			return mapping.findForward("login");
		}
		if (!nbsSecurityObj.getPermission(NBSBOLookup.OBSERVATIONLABREPORT,
				NBSOperationLookup.ASSIGNSECURITY,
				ProgramAreaJurisdictionUtil.ANY_PROGRAM_AREA,
				ProgramAreaJurisdictionUtil.ANY_JURISDICTION)
				&& !nbsSecurityObj.getPermission(NBSBOLookup.OBSERVATIONMORBIDITYREPORT,
						NBSOperationLookup.ASSIGNSECURITY,
						ProgramAreaJurisdictionUtil.ANY_PROGRAM_AREA,
						ProgramAreaJurisdictionUtil.ANY_JURISDICTION)) {
			logger.fatal("Error: no permisstion to VIEW elrS Needing Program, go back to login screen");
			return mapping.findForward("login");
		}

		String MPRUidString = (String)request.getParameter("MPRUid");
		String DSObservationUIDString = request.getParameter("observationUID");
		if(DSObservationUIDString!=null)
			NBSContext.store(session,"DSObservationUID",DSObservationUIDString);
		if(MPRUidString!=null){
			Long MPRUid =new Long(MPRUidString) ;
			NBSContext.store(session, "DSPatientPersonUID", MPRUid );
		}

	}catch (Exception e) {
		logger.error("Exception in ObservationsAssignmentSubmit: " + e.getMessage());
		e.printStackTrace();
		throw new ServletException("General error occurred in Observations Needing Assignment Queue Submit : "+e.getMessage());
	}
     return mapping.findForward(contextAction);
    }
}