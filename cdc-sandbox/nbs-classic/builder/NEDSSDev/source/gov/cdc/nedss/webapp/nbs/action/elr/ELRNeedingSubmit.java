package gov.cdc.nedss.webapp.nbs.action.elr;




import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.systemservice.nbscontext.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.ObservationSummaryVO;
import gov.cdc.nedss.util.*;


import java.io.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.struts.action.*;


public class ELRNeedingSubmit
    extends Action
{

    //For logging
    static final LogUtils logger = new LogUtils(ELRNeedingSubmit.class.getName());

    // protected MainSessionCommand msCommand = null;
    private String sBeanJndiName = "";
    private String sMethod = "";
    private Object[] oParams = null;

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
	logger.debug("contextAction :" + contextAction);

	String currentIndex = request.getParameter("currentIndex");
	if(currentIndex == null)
	    currentIndex = (String)request.getAttribute("currentIndex");
	logger.debug("currentIndex: " + currentIndex);

	if(currentIndex != null)
	    NBSContext.store(session, "DSFromIndex", currentIndex);


    NBSSecurityObj nbsSecurityObj = (NBSSecurityObj)session.getAttribute("NBSSecurityObject");
    if (nbsSecurityObj == null) {
            logger.fatal(
                    "Error: no nbsSecurityObj in the session, go back to login screen");
            return mapping.findForward("login");
    }
    if (!nbsSecurityObj.getPermission(NBSBOLookup.OBSERVATIONLABREPORT,
                                          NBSOperationLookup.ASSIGNSECURITY,
                                          ProgramAreaJurisdictionUtil.ANY_PROGRAM_AREA,
                                          ProgramAreaJurisdictionUtil.ANY_JURISDICTION)) {
       logger.fatal("Error: no permisstion to VIEW elrS Needing Program, go back to login screen");
       return mapping.findForward("login");
     }

      ObservationSummaryVO DSObservationUID = new ObservationSummaryVO();
      String sUid = request.getParameter("observationUid");
      logger.debug( "observationUid is: " + sUid );

     if( sUid !=null)

      NBSContext.store(session, NBSConstantUtil.DSObservationUID, sUid  );

       return mapping.findForward(contextAction);
    }

}