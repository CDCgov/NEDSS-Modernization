package gov.cdc.nedss.webapp.nbs.action.reportadmin;

import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.reportadmin.dao.ReportDAO;

import java.io.IOException;

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
 * Edits records in the Report table.
 * @author Ed Jenkins
 */
public class EditReport extends Action
{

    /**
     * Logger.
     */
    private static final Logger logger = Logger.getLogger(EditReport.class);

    /**
     * String Buffer Size.
     */
    private static final int STRING_BUFFER_SIZE = 1024;

    /**
     * DAO for Report.
     */
    private static final ReportDAO daoReport = new ReportDAO();

    /**
     * Constructor.
     */
    public EditReport()
    {
    }

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        HttpSession session = request.getSession(true);
        try
        {
            // Return Link
            String data_source_uid_s = request.getParameter("data_source_uid") == null ? "" : request.getParameter("data_source_uid").trim();
            long data_source_uid = 0L;
            if(data_source_uid_s.trim().length() > 0)
            {
                data_source_uid = Long.parseLong(data_source_uid_s);
            }
            String report_uid_s = request.getParameter("report_uid");
            long report_uid = Long.parseLong(report_uid_s);
            String strLinkName = "Return to Reports";
            String strLinkAddr = "/nbs/ListReport.do";
            String strContext = request.getParameter("context");
            if(strContext != null)
            {
                if(strContext.equalsIgnoreCase("ViewDataSource"))
                {
                    strLinkName = "Return to View Data Source";
                    String direction = (String) session.getAttribute("direction");
                    String sortMethod = (String) session.getAttribute("sortMethod");
                    strLinkAddr = "/nbs/ViewDataSource.do?data_source_uid=" + data_source_uid_s + "&contextAction=Sort&direction=" + direction + "&sortMethod=" + sortMethod;
                }
            }
            request.setAttribute("LinkName", strLinkName);
            request.setAttribute("LinkAddr", strLinkAddr);
            request.setAttribute("context", strContext);
           

        }
        catch(Exception ex)
        {
       	 	logger.error("Error in Report EditReport: " +ex.getMessage());
       	 	ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            // Return Link
            String strLinkName = "Return to Reports";
            String strLinkAddr = "/nbs/ListReport.do";
            String strContext = request.getParameter("context");
            request.setAttribute("LinkName", strLinkName);
            request.setAttribute("LinkAddr", strLinkAddr);
            request.setAttribute("context", strContext);
            return mapping.findForward("error");
        }
        return mapping.findForward("default");
    }

}
