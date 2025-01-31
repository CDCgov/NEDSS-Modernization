package gov.cdc.nedss.webapp.nbs.action.reportadmin;

import gov.cdc.nedss.reportadmin.dao.ReportSectionDAO;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.NedssUtils;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Lists all the report sections that are available for this account.
 * @author Karthik Chinnayan
 */
public class ListReportSections extends Action
{
    /**
     * Logger.
     */
    private static final Logger logger = Logger.getLogger(ListReportSections.class);

    /**
     * DAO for Report.
     */
    private static final ReportSectionDAO daoReportSection = new ReportSectionDAO();

    /**
     * Constructor.
     */
    public ListReportSections()
    {

    }

    public ActionForward execute(ActionMapping mapping, ActionForm form,
    		HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        HttpSession session = request.getSession(true);
        NBSSecurityObj sec = (NBSSecurityObj)session.getAttribute("NBSSecurityObject");
        try
        {
        	String displaySt = (String)session.getAttribute("ReportAdmin.dtReportList");
        	if(displaySt != null && displaySt.length() >0)
        	{
        		session.removeAttribute("ReportAdmin.dtReportList");
        	}
            // Return Link
            String strLinkName = "Return to System Management Main Menu";
            String strLinkAddr = "/nbs/ReportAdministration.do?focus=systemAdmin5";
            request.setAttribute("LinkName", strLinkName);
            request.setAttribute("LinkAddr", strLinkAddr);

            // retrieve all the report sections
            ArrayList<Object> alReportSections = daoReportSection.list();

            logger.info("# of reportSections retrieved from the database = " +
            		alReportSections.size());

            String contextAction = request.getParameter("ContextAction");

            //sorting
            boolean invDirectionFlag = false;
            String sortMethod = request.getParameter("sortMethod");

            if (sortMethod == null || sortMethod.equals(""))
				sortMethod = "getSectionDescTxt";

            String invSortDirection = request.getParameter("direction");

            session.setAttribute("sortMethod", sortMethod);
            session.setAttribute("direction", invSortDirection);

			if (invSortDirection != null && invSortDirection.equals("false"))
				invDirectionFlag = false;
			else
				invDirectionFlag = true;

			if (contextAction != null && contextAction.equals("Sort") && invDirectionFlag) {
				request.setAttribute("sortDirection", new Boolean(false));
			}
			else if (contextAction != null && contextAction.equals("Sort") && !invDirectionFlag) {
				request.setAttribute("sortDirection", new Boolean(true));
			}
			else {
				request.setAttribute("sortDirection", new Boolean(invDirectionFlag));
			}

			if (sortMethod != null && alReportSections != null && alReportSections.size() > 0) {
				NedssUtils util = new NedssUtils();
				util.sortObjectByColumnGeneric(sortMethod,alReportSections,invDirectionFlag);
			}
            session.setAttribute("ReportAdmin.alReportSections", alReportSections);
        }
        catch(Exception ex)
        {
       	 	logger.error("Error in Report ListReportSections: " +ex.getMessage());
       	 	ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            throw new ServletException(ex.getMessage(),ex);
        }
        return mapping.findForward("default");
    }
}
