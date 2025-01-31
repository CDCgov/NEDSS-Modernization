package gov.cdc.nedss.webapp.nbs.action.reportadmin;

import gov.cdc.nedss.reportadmin.dao.FilterValueDAO;
import gov.cdc.nedss.reportadmin.dao.ReportFilterDAO;
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
 * Delete Report Filter.
 * @author Ed Jenkins
 */
public class DeleteReportFilter extends Action
{

    /**
     * Logger.
     */
    private static final Logger logger = Logger.getLogger(DeleteReportFilter.class);

    /**
     * DAO for Report Filter.
     */
    private static final ReportFilterDAO daoReportFilter = new ReportFilterDAO();

    /**
     * DAO for Filter_value.
     */
    private static final FilterValueDAO daoFilterValue = new FilterValueDAO();

    /**
     * Constructor.
     */
    public DeleteReportFilter()
    {
    }

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
    	HttpSession session = request.getSession(true);
    	String strRedirect = null;
        try
        {
            // Delete
            String report_filter_uid_s = request.getParameter("report_filter_uid");
            long report_filter_uid = 0L;
            if(report_filter_uid_s != null)
            {
                report_filter_uid = Long.parseLong(report_filter_uid_s);
            }
            daoFilterValue.deleteAll(report_filter_uid);
            ReportFilterDT dtReportFilter = daoReportFilter.view(report_filter_uid);
            daoReportFilter.delete(dtReportFilter);
            strRedirect = "/ViewReport.do?data_source_uid=" + dtReportFilter.getDataSourceUID() + "&report_uid=" + dtReportFilter.getReportUID();
        }
        catch(Exception ex)
        {
       	 	logger.error("Error in Report DeleteReportFilter: " +ex.getMessage());
       	 	ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            return mapping.findForward("error");
        }
        if(strRedirect != null)
        {
            ActionForward af = new ActionForward();
            af.setName("ViewReport");
            af.setPath(strRedirect);
            af.setRedirect(true);
            return af;
        }
        return mapping.findForward("default");
    }

}
