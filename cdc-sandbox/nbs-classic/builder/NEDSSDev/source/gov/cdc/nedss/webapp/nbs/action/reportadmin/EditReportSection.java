package gov.cdc.nedss.webapp.nbs.action.reportadmin;

import gov.cdc.nedss.reportadmin.dao.ReportDAO;
import gov.cdc.nedss.reportadmin.dt.ReportSectionDT;

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

public class EditReportSection extends Action{



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
    public EditReportSection()
    {
    }

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        HttpSession session = request.getSession(true);
        try
        {
            String report_uid_s = request.getParameter("report_section_uid");
            long report_uid = Long.parseLong(report_uid_s);
            String strLinkName = "Return to Reports Section";
            String strLinkAddr = "/nbs/ListReportSection.do";
            String strContext = request.getParameter("context");
            ReportSectionDT dtReportSection = (ReportSectionDT)session.getAttribute("ReportAdmin.dtReportSection");
            request.setAttribute("LinkName", strLinkName);
            request.setAttribute("LinkAddr", strLinkAddr);
            request.setAttribute("context", strContext);
           
        }
        catch(Exception ex)
        {
       	 	logger.error("Error in Report EditReportSection: " +ex.getMessage());
       	 	ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            // Return Link
            String strLinkName = "Return to Reports Section";
            String strLinkAddr = "/nbs/ListReportSection.do";
            String strContext = request.getParameter("context");
            request.setAttribute("LinkName", strLinkName);
            request.setAttribute("LinkAddr", strLinkAddr);
            request.setAttribute("context", strContext);
            return mapping.findForward("error");
        }
        return mapping.findForward("default");
    }


}
