package gov.cdc.nedss.webapp.nbs.action.reportadmin;

import gov.cdc.nedss.reportadmin.dao.DisplayColumnDAO;

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
 * Edits records in the Display_column table.
 * @author Ed Jenkins
 */
public class UpdateReportColumn extends Action
{

    /**
     * Logger.
     */
    private static final Logger logger = Logger.getLogger(UpdateReportColumn.class);

    /**
     * DAO for Display_column.
     */
    private static final DisplayColumnDAO daoDisplayColumn = new DisplayColumnDAO();

    /**
     * Constructor.
     */
    public UpdateReportColumn()
    {
    }

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        HttpSession session = request.getSession(true);
        String strRedirect = null;
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
            String strLinkName = "Return to View Report";
            String strLinkAddr = "/nbs/ViewReport.do?data_source_uid=" + data_source_uid_s + "&amp;report_uid=" + report_uid_s;
            request.setAttribute("LinkName", strLinkName);
            request.setAttribute("LinkAddr", strLinkAddr);
            strRedirect = "/ViewReport.do?data_source_uid=" + data_source_uid_s + "&report_uid=" + report_uid_s;
            String strAction = request.getParameter("action");
            if(strAction != null)
            {
                if(strAction.equalsIgnoreCase("cancel"))
                {
                    ActionForward af = new ActionForward();
                    af.setName("ViewReport");
                    af.setPath(strRedirect);
                    af.setRedirect(true);
                    return af;
                }
            }
            String strColumnUIDArray = request.getParameter("column_uid_array");
            String[] strSelectedColumns = strColumnUIDArray.split(",");
            ArrayList<Object> alSelectedColumns = new ArrayList<Object> ();
            int x = 0;
            int y = strSelectedColumns.length;
            for(x=0; x<y; x++)
            {
                try
                {
                    Long L = Long.valueOf(strSelectedColumns[x]);
                    alSelectedColumns.add(L);
                }
                catch(Exception ex)
                {
                    logger.error(ex, ex);
                }
            }
            daoDisplayColumn.clearAll(data_source_uid, report_uid);
            daoDisplayColumn.setAll(data_source_uid, report_uid, alSelectedColumns);
        }
        catch(Exception ex)
        {
       	 	logger.error("Error in Report updateReportCol: " +ex.getMessage());
       	 	ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            return mapping.findForward("error");
        }

        session.setAttribute("error", "If column selections have changed, please check sorting and update if necessary.");
        ActionForward af = new ActionForward();
        af.setName("ViewReport");
        af.setPath(strRedirect);
        af.setRedirect(true);
        return af;
    }

}
