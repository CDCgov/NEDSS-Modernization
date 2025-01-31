package gov.cdc.nedss.webapp.nbs.action.reportadmin;

import gov.cdc.nedss.reportadmin.dao.ReportSortColumnDAO;
import gov.cdc.nedss.reportadmin.dt.ReportSortColumnDT;

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
 * Edits records in the Report_Sort_Column table.
 * @author Ed Jenkins
 */
public class UpdateReportSort extends Action
{

    /**
     * Logger.
     */
    private static final Logger logger = Logger.getLogger(UpdateReportSort.class);

    /**
     * DAO for Report_Sort_Column.
     */
    private static final ReportSortColumnDAO daoReportSortColumn = new ReportSortColumnDAO();

    /**
     * Constructor.
     */
    public UpdateReportSort()
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
            long report_sort_column_uid = daoReportSortColumn.getNextUID();
            ReportSortColumnDT dtReportSortColumn = new ReportSortColumnDT();
            dtReportSortColumn.setReportSortColumnUID(report_sort_column_uid);
            dtReportSortColumn.setReportSortOrderCode(request.getParameter("report_sort_order_code"));
            dtReportSortColumn.setReportSortSequenceNumber(1);
            dtReportSortColumn.setDataSourceUID(data_source_uid);
            dtReportSortColumn.setReportUID(report_uid);
            dtReportSortColumn.setColumnUID(request.getParameter("column_uid"));
            dtReportSortColumn.validate();
            daoReportSortColumn.delete(data_source_uid, report_uid);
            daoReportSortColumn.add(dtReportSortColumn);
            session.setAttribute("ReportAdmin.dtReportSortColumn", dtReportSortColumn);
        }
        catch(Exception ex)
        {
       	 	logger.error("Error in Report updateReportSort: " +ex.getMessage());
       	 	ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            return mapping.findForward("error");
        }

        ActionForward af = new ActionForward();
        af.setName("ViewReport");
        af.setPath(strRedirect);
        af.setRedirect(true);
        return af;
    }

}
