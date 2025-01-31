package gov.cdc.nedss.webapp.nbs.action.reportadmin;

import gov.cdc.nedss.reportadmin.dao.FilterCodeDAO;
import gov.cdc.nedss.reportadmin.dao.ReportFilterDAO;
import gov.cdc.nedss.reportadmin.dt.FilterCodeDT;
import gov.cdc.nedss.reportadmin.dt.ReportFilterDT;

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
 * Edits records in the Report_Filter table.
 * @author Ed Jenkins
 */
public class EditReportFilter extends Action
{

    /**
     * Logger.
     */
    private static final Logger logger = Logger.getLogger(EditReportFilter.class);

    private static final ReportFilterDAO daoReportFilter = new ReportFilterDAO();
    private static final FilterCodeDAO daoFilterCode = new FilterCodeDAO();

    /**
     * Constructor.
     */
    public EditReportFilter()
    {
    }

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
    	HttpSession session = request.getSession(true);
        try
        {

            String strContext = request.getParameter("context");
            request.setAttribute("context", strContext);

            String report_filter_uid_s = request.getParameter("report_filter_uid");
            long report_filter_uid = 0L;
            if(report_filter_uid_s != null)
            {
                report_filter_uid = Long.parseLong(report_filter_uid_s);
            }
            ReportFilterDT dtReportFilter = daoReportFilter.view(report_filter_uid);
            session.setAttribute("ReportAdmin.dtReportFilter", dtReportFilter);
            // ReportAdmin.dtFilterCode
            long filter_uid = dtReportFilter.getFilterUID();
            FilterCodeDT dtFilterCode = daoFilterCode.view(filter_uid);
            session.setAttribute("ReportAdmin.dtFilterCode", dtFilterCode);

            String data_source_uid_s = dtReportFilter.getDataSourceUID_s();
            String report_uid_s = dtReportFilter.getReportUID_s();
            String strLinkName = "Return to View Report";
            String strLinkAddr = "/nbs/ViewReport.do?data_source_uid=" + data_source_uid_s + "&amp;report_uid=" + report_uid_s;
            request.setAttribute("LinkName", strLinkName);
            request.setAttribute("LinkAddr", strLinkAddr);
        }
        catch(Exception ex)
        {
       	 	logger.error("Error in Report EditReportFilter: " +ex.getMessage());
       	 	ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            return mapping.findForward("error");
        }
        return mapping.findForward("default");
    }

}
