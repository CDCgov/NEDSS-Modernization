package gov.cdc.nedss.webapp.nbs.action.reportadmin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Edits records in the Display_column table.
 * @author Ed Jenkins
 */
public class EditReportColumn extends Action
{

    /**
     * Logger.
     */
    private static final Logger logger = Logger.getLogger(EditReportColumn.class);

    /**
     * Constructor.
     */
    public EditReportColumn()
    {
    }

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        try
        {
            // Return Link
            String data_source_uid_s = request.getParameter("data_source_uid") == null ? "" : request.getParameter("data_source_uid").trim();
            String report_uid_s = request.getParameter("report_uid");
            String strLinkName = "Return to View Report";
            String strLinkAddr = "/nbs/ViewReport.do?data_source_uid=" + data_source_uid_s + "&amp;report_uid=" + report_uid_s;
            request.setAttribute("LinkName", strLinkName);
            request.setAttribute("LinkAddr", strLinkAddr);
        }
        catch(Exception ex)
        {
       	 	logger.error("Error in Report EditReportColumn: " +ex.getMessage());
       	 	ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            return mapping.findForward("error");
        }
        return mapping.findForward("default");
    }

}
