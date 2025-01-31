package gov.cdc.nedss.webapp.nbs.action.reportadmin;

import gov.cdc.nedss.reportadmin.dao.ReportFilterDAO;
import gov.cdc.nedss.reportadmin.dt.ReportFilterDT;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;

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
public class UpdateReportFilter extends Action
{

    /**
     * Logger.
     */
    private static final Logger logger = Logger.getLogger(UpdateReportFilter.class);

    /**
     * DAO for Report_Filter.
     */
    private static final ReportFilterDAO daoReportFilter = new ReportFilterDAO();

    /**
     * Constructor.
     */
    public UpdateReportFilter()
    {
    }

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
    	String strRedirect = null;
    	HttpSession session = request.getSession(true);
        NBSSecurityObj sec = (NBSSecurityObj)session.getAttribute("NBSSecurityObject");
        ActionForward af = new ActionForward();
        try
        {
            // Return Link
            String strLinkName = "Return to Reports";
            String strLinkAddr = "/nbs/ListReport.do";
            request.setAttribute("LinkName", strLinkName);
            request.setAttribute("LinkAddr", strLinkAddr);
            ReportFilterDT dtReportFilter = (ReportFilterDT)session.getAttribute("ReportAdmin.dtReportFilter");
            String strAction = request.getParameter("action");

            String data_source_uid_s = dtReportFilter.getDataSourceUID_s();
            String report_uid_s = dtReportFilter.getReportUID_s();
            strRedirect = "/ViewReport.do?data_source_uid=" + data_source_uid_s + "&report_uid=" + report_uid_s;

            af.setName("ViewDataSource");
            af.setPath(strRedirect);
            af.setRedirect(true);

            if(strAction != null)
            {
                if(strAction.equalsIgnoreCase("cancel"))
                {
                    return af;
                }
            }
            String reportFilterType = request.getParameter("reportFilterType");
            if(reportFilterType != null && reportFilterType.equalsIgnoreCase("SS")) {

            	dtReportFilter.setMinValueCount(1);
                dtReportFilter.setMaxValueCount(1);

            } else if(reportFilterType != null && reportFilterType.equalsIgnoreCase("MS")) {
            	dtReportFilter.setMinValueCount(1);
                dtReportFilter.setMaxValueCount(-1);

            }

            dtReportFilter.setReportFilterInd(request.getParameter("report_filter_ind"));

            dtReportFilter.setColumnUID(0L);
            String column_uid = request.getParameter("column_uid");
            if(column_uid != null)
            {
                if(!column_uid.equalsIgnoreCase(""))
                {
                    dtReportFilter.setColumnUID(column_uid);
                }
            }
            //dtReportFilter.validate();
            daoReportFilter.edit(dtReportFilter);
            session.setAttribute("ReportAdmin.dtReportFilter", dtReportFilter);
        }
        catch(Exception ex)
        {
       	 	logger.error("Error in Report updateReportFilter: " +ex.getMessage());
       	 	ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            return mapping.findForward("error");
        }
        //return mapping.findForward("default");
        return af;

    }

}
