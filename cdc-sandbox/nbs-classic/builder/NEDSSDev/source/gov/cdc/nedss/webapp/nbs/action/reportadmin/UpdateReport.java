package gov.cdc.nedss.webapp.nbs.action.reportadmin;

import gov.cdc.nedss.reportadmin.dao.ReportDAO;
import gov.cdc.nedss.reportadmin.dt.ReportDT;

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
public class UpdateReport extends Action
{

    /**
     * Logger.
     */
    private static final Logger logger = Logger.getLogger(UpdateReport.class);

    /**
     * DAO for Report.
     */
    private static final ReportDAO daoReport = new ReportDAO();

    /**
     * Constructor.
     */
    public UpdateReport()
    {
    }

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        HttpSession session = request.getSession(true);
        try
        {
            // Return Link
            String data_source_uid_s = request.getParameter("data_source_uid") == null ? "" : request.getParameter("data_source_uid").trim();
            String report_uid_s = request.getParameter("report_uid");
            long report_uid = 0L;
            ReportDT dtReport = (ReportDT)session.getAttribute("ReportAdmin.dtReport");
            if(report_uid_s != null)
            {
                report_uid = Long.parseLong(report_uid_s);
            }
            String strLinkName = "Return to Reports";
            String strLinkAddr = "/nbs/ListReport.do";
            String strContext = request.getParameter("context");
            if(strContext != null)
            {
                if(strContext.equalsIgnoreCase("ViewDataSource"))
                {
                	data_source_uid_s = dtReport.getDataSourceUID_s();
                    strLinkName = "Return to View Data Source";
                    String direction = (String) session.getAttribute("direction");
                    String sortMethod = (String) session.getAttribute("sortMethod");
                    strLinkAddr = "/nbs/ViewDataSource.do?data_source_uid=" + data_source_uid_s + "&contextAction=Sort&direction=" + direction + "&sortMethod=" + sortMethod;
                }
            }
            request.setAttribute("LinkName", strLinkName);
            request.setAttribute("LinkAddr", strLinkAddr);
            request.setAttribute("context", strContext);
            String strAction = request.getParameter("action");
            if(strAction != null)
            {
                if(strAction.equalsIgnoreCase("cancel"))
                {
                    data_source_uid_s = dtReport.getDataSourceUID_s();
                    report_uid_s = dtReport.getReportUID_s();
                    String strRedirect = "/ViewReport.do?data_source_uid=" + data_source_uid_s + "&report_uid=" + report_uid_s;
                    ActionForward af = new ActionForward();
                    af.setName("ViewDataSource");
                    af.setPath(strRedirect);
                    af.setRedirect(true);
                    return af;
                }
            }
            dtReport.setReportUID(request.getParameter("report_uid"));
            //dtReport.setDataSourceUID(request.getParameter("data_source_uid") == null ? "" : request.getParameter("data_source_uid").trim());
            dtReport.setDescTxt(request.getParameter("desc_txt"));
            dtReport.setLocation(request.getParameter("location"));
            dtReport.setOwnerUID(request.getParameter("owner_uid"));
            dtReport.setReportTitle(request.getParameter("report_title"));
            dtReport.setReportTypeCode(request.getParameter("report_type_code"));
            dtReport.setShared(request.getParameter("shared"));
            dtReport.setReportSectionCode(request.getParameter("section_cd"));
            dtReport.validate();
            daoReport.edit(dtReport);
            session.setAttribute("ReportAdmin.dtReport", dtReport);
        }
        catch(Exception ex)
        {
       	 	logger.error("Error in Report updateReport: " +ex.getMessage());
       	 	ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            return mapping.findForward("error");
        }
        return mapping.findForward("default");
    }

}
