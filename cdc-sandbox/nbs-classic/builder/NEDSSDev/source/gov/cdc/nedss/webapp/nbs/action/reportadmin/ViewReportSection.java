package gov.cdc.nedss.webapp.nbs.action.reportadmin;

import gov.cdc.nedss.reportadmin.dao.ReportSectionDAO;
import gov.cdc.nedss.reportadmin.dt.ReportDT;
import gov.cdc.nedss.reportadmin.dt.ReportSectionDT;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ViewReportSection extends Action{
	


    /**
     * Logger.
     */
    private static final Logger logger = Logger.getLogger(ViewReport.class);

    /**
     * DAO for Report.
     */
    private static final ReportSectionDAO daoReportSection = new ReportSectionDAO();

    /**
     * Constructor.
     */
    public ViewReportSection()
    {
    }

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        HttpSession session = request.getSession(true);
        try
        {
            String section_cd_s = request.getParameter("section_cd");
            long section_cd = Long.parseLong(section_cd_s);
            String strLinkName = "Return to Reports Section";
            String strLinkAddr = "/nbs/ListReportSections.do";
            String strContext = request.getParameter("context");
           
            request.setAttribute("LinkName", strLinkName);
            request.setAttribute("LinkAddr", strLinkAddr);
            request.setAttribute("context", strContext);
           
            ReportSectionDT dtReportSection = daoReportSection.view(section_cd);
            ArrayList<Object> alReportSections = daoReportSection.viewReport(dtReportSection.getSectionCd());
            if(alReportSections != null && alReportSections.size()>0 )
            {
	           Iterator<Object>  iter = alReportSections.iterator();
	            StringBuffer sb = new StringBuffer();
	            while(iter.hasNext()) {
	            	ReportDT dtReport = (ReportDT) iter.next();
	            	String title = dtReport.getReportTitle();
	            	if(title != null && title.trim().length() > 0)
	            		sb.append(title).append("<br/>");
	            }
	            String displaySt = sb.toString();
	            displaySt = displaySt.substring(0, displaySt.length()-2);
	            session.setAttribute("ReportAdmin.dtReportList", displaySt);
            }
            session.setAttribute("ReportAdmin.dtReportSection", dtReportSection);
            // Carry over error messages through a redirect.
            String strError = (String)session.getAttribute("error");
            if(strError != null)
            {
                request.setAttribute("error", strError);
                session.removeAttribute("error");
            }
        }
        catch(Exception ex)
        {
       	 	logger.error("Error in Report ViewReportSection: " +ex.getMessage());
       	 	ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            throw new ServletException(ex.getMessage(),ex);
        }

        return mapping.findForward("default");
    }
}
