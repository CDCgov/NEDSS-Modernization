package gov.cdc.nedss.webapp.nbs.action.reportadmin;

import gov.cdc.nedss.reportadmin.dao.DisplayColumnDAO;
import gov.cdc.nedss.reportadmin.dao.FilterValueDAO;
import gov.cdc.nedss.reportadmin.dao.ReportFilterDAO;
import gov.cdc.nedss.reportadmin.dao.ReportFilterValidationDAO;
import gov.cdc.nedss.reportadmin.dao.ReportSectionDAO;
import gov.cdc.nedss.reportadmin.dao.ReportSortColumnDAO;
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

public class DeleteReportSection extends Action{


    /**
     * Logger.
     */
    private static final Logger logger = Logger.getLogger(DeleteReport.class);


    /**
     * DAO for Report.
     */
    private static final ReportSectionDAO daoReportSection = new ReportSectionDAO();
   

    /**
     * Constructor.
     */
    public DeleteReportSection()
    {
    }

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
    	HttpSession session = request.getSession(true);
        try
        {
            // Return Link
            String report_uid_s = request.getParameter("report_section_uid");
            long report_uid = Long.parseLong(report_uid_s);
            ReportSectionDT dtReportSection = (ReportSectionDT)session.getAttribute("ReportAdmin.dtReportSection");
            String strLinkName = "Return to Reports Section";
            String strLinkAddr = "/nbs/ListReportSections.do";
            String strContext = request.getParameter("context");
            
            request.setAttribute("LinkName", strLinkName);
            request.setAttribute("LinkAddr", strLinkAddr);
            request.setAttribute("context", strContext);
            long sectionCd = dtReportSection.getSectionCd();
            daoReportSection.editReport(sectionCd);
            daoReportSection.delete(report_uid);
        }
        catch(Exception ex)
        {
       	 	logger.error("Error in Report DeleteReportSection: " +ex.getMessage());
       	 	ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            // Return Link
            String strLinkName = "Return to Reports Section";
            String strLinkAddr = "/nbs/ListReportSections.do";
            String strContext = request.getParameter("context");
            request.setAttribute("LinkName", strLinkName);
            request.setAttribute("LinkAddr", strLinkAddr);
            request.setAttribute("context", strContext);
            return mapping.findForward("error");
        }
        
        return mapping.findForward("default");
    }

}
