package gov.cdc.nedss.webapp.nbs.action.elr;

import gov.cdc.nedss.util.*;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.systemservice.nbscontext.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.ObservationSummaryVO;
import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;


public class ELRNeedingLoad
    extends Action
{

    //For logging
    static final LogUtils logger = new LogUtils(ELRNeedingLoad.class.getName());
    protected ObservationSummaryVO obsSumVO = null;

    public ActionForward execute(ActionMapping mapping, ActionForm aForm,
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


       /* Check permissions */
       NBSSecurityObj nbsSecurityObj = (NBSSecurityObj)session.getAttribute("NBSSecurityObject");
       if (nbsSecurityObj == null)
       {
            logger.fatal("Error: no nbsSecurityObj in the session, go back to login screen");
            return mapping.findForward("login");
       }
       if (!nbsSecurityObj.getPermission(NBSBOLookup.OBSERVATIONLABREPORT, NBSOperationLookup.ASSIGNSECURITY))
       {
          logger.fatal("Error: no permisstion to VIEW elr Needing Program, go back to login screen");
          throw new ServletException("Error: no permisstion to VIEW elr Needing Program, go back to login screen");
       }


        /**+
     * get page context
     */
        String contextAction = request.getParameter("ContextAction");

        if (contextAction == null)
            contextAction = (String)request.getAttribute("ContextAction");
        logger.debug("before call getPageContext with contextAction of: " + contextAction);
        TreeMap<Object,Object> tm = NBSContext.getPageContext(session, "PS055", contextAction);
        NBSContext.lookInsideTreeMap(tm);

        String sCurrTask = NBSContext.getCurrentTask(session);
        logger.debug("sCurrTask: " + sCurrTask);
        request.setAttribute("viewHref",
                             "/nbs/" + sCurrTask + ".do?ContextAction=" +
                             tm.get("View"));


		request.setAttribute("nextHref",
				 "/nbs/" + sCurrTask +
				 ".do?ContextAction=" + tm.get("Next"));
		request.setAttribute("prevHref",
				 "/nbs/" + sCurrTask +
				 ".do?ContextAction=" + tm.get("Prev"));
        request.setAttribute("sortHref",
				 "/nbs/" + sCurrTask +
				 ".do?ContextAction=" + tm.get("Sort"));

        // Get sort method, if available.
        String sortMethod = request.getParameter("SortMethod");

		// onELRNeedingProgramPage is preserved while doing sorts and navigating pages.
		// If it is not present, we need to reload collection from EJB.
		String onELRNeedingProgramPage = request.getParameter("onELRNeedingProgramPage");
		logger.debug( "onELRNeedingProgramPage = " + onELRNeedingProgramPage );


       ArrayList<?> observationsNeedingReview = (ArrayList<?> )session.getAttribute("observationList"); //make it do update
       boolean accessedEJB = false;

        try
        {
        //    if (sortMethod != null || observationsNeedingReview == null)

        	/* Hit EJB if observationsNeedingReview=null (meaning it was never loaded into
        	   session; we are visiting it for the 1st time), or if onELRNeedingProgramPage=null
        	   (meaning we either visiting it for the 1st time, or came back to it after navigating
        	   other pages)
        	 */
            if (observationsNeedingReview==null  ||  onELRNeedingProgramPage==null)
            {
              {
                accessedEJB = true;  // Used to make sure that results gets sorted later on.
                MainSessionCommand msCommand = null;
                MainSessionHolder mainSessionHolder = new MainSessionHolder();
                msCommand = mainSessionHolder.getMainSessionCommand(session);
                logger.info("You are trying to GET_ELR_Needing_Load");
                String  sBeanJndiName = JNDINames.TASKLIST_PROXY_EJB;
                String  sMethod = "getObservationsNeedingSecurityAssignment";
                logger.debug( "calling getObservationsNeedingSecurityAssignment on TaskListProxyEJB from ELRNeedingLoad");

                ArrayList<?> arr = (ArrayList<?> )msCommand.processRequest(sBeanJndiName, sMethod, null);
                observationsNeedingReview = (ArrayList<?> )arr.get(0);
              }

              logger.info( "Number of observations needing for review in array is: " + observationsNeedingReview.size());

              if (observationsNeedingReview == null)
                logger.debug("THERE ARE NO ObservationsNeedingReview");

              if (observationsNeedingReview.size() == 0)
              {
                session.setAttribute("observationList",observationsNeedingReview);
                return (mapping.findForward("XSP"));
              }
              else
              {


              }
            }
            session.setAttribute("observationList", observationsNeedingReview);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            logger.error(
                    "Error in ELRNeedingLoad in getting ObservationsNeedingReview from EJB");
        }

        /* Sorting Part
           We sort collection if it was requested to be sorted in URL, AND if accessed EJB
           for latest records.  If we accessed EJB, and no sorting was requested, we'll sort
           using "getAddTime"
           By now we should have "observationsNeedingReview" set. Ethier loaded from EJB or
           retrieved from session.
        */
        if( ( sortMethod != null &&  (!sortMethod.equals("null"))) || accessedEJB ) {

               if( sortMethod==null || sortMethod.equals("null") ) {
                   sortMethod = "getAddTime";
                }

                String obsSortDirection = request.getParameter("direction");
                boolean obsDirectionFlag = false;

                if (obsSortDirection != null && obsSortDirection.equals("false"))
                {
	              obsDirectionFlag = false;
                  request.setAttribute("sortDirection", new Boolean(true));
                } else {
                  obsDirectionFlag = true;
                  request.setAttribute("sortDirection", new Boolean(false));
                }

                NedssUtils util = new NedssUtils();
                if(sortMethod != null && observationsNeedingReview != null && observationsNeedingReview.size() > 0)
                {
                  util.sortObjectByColumn(sortMethod,
                                        (Collection<Object>)observationsNeedingReview,
                                        obsDirectionFlag);
                }
                // Now that it has been sorted, save it in session.
                session.setAttribute("observationList", observationsNeedingReview);
        }


        return (mapping.findForward("XSP"));
    }
 }