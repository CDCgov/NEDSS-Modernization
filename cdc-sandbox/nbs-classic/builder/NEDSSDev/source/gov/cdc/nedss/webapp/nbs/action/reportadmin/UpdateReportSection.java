package gov.cdc.nedss.webapp.nbs.action.reportadmin;

import gov.cdc.nedss.reportadmin.dao.ReportSectionDAO;
import gov.cdc.nedss.reportadmin.dt.ReportSectionDT;
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

public class UpdateReportSection extends Action{
	


    /**
     * Logger.
     */
    private static final Logger logger = Logger.getLogger(UpdateReport.class);

    /**
     * DAO for Report.
     */
    private static final ReportSectionDAO daoReportSection = new ReportSectionDAO();

    /**
     * Constructor.
     */
    public UpdateReportSection()
    {
    }

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        HttpSession session = request.getSession(true);
        try
        {
            // Return Link
            String report_uid_s = request.getParameter("report_section_uid");
            long report_uid = 0L;
            if(report_uid_s != null)
            {
                report_uid = Long.parseLong(report_uid_s);
            }
            String strLinkName = "Return to Reports Section";
            String strLinkAddr = "/nbs/ListReportSections.do";
            String strContext = request.getParameter("context");
            ReportSectionDT dtReportSection = (ReportSectionDT)session.getAttribute("ReportAdmin.dtReportSection");
            if(strContext != null)
            {
                if(strContext.equalsIgnoreCase("ViewReportSection"))
                {
                	long section_cd = dtReportSection.getSectionCd();
                    strLinkName = "Return to View Report Section";
                    strLinkAddr = "/nbs/ViewReportSection.do?section_cd=" + section_cd;
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
                	long section_cd = dtReportSection.getSectionCd();
                    String strRedirect = "/ViewReportSection.do?section_cd=" + section_cd ;
                    ActionForward af = new ActionForward();
                    af.setName("ViewReportSections");
                    af.setPath(strRedirect);
                    af.setRedirect(true);
                    return af;
                }
            }
            NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session.getAttribute("NBSSecurityObject");
            String userId = nbsSecurityObj.getTheUserProfile().getTheUser().getEntryID();
            long lastChangUid=Long.parseLong(userId);
            dtReportSection.setReportSectionUID(request.getParameter("report_section_uid"));
            //dtReport.setDataSourceUID(request.getParameter("data_source_uid") == null ? "" : request.getParameter("data_source_uid").trim());
            dtReportSection.setSectionDescTxt(request.getParameter("section_desc_txt"));
            dtReportSection.setComments(request.getParameter("comments"));
            dtReportSection.setLastChgUserUID(lastChangUid);
            dtReportSection.validate();
            daoReportSection.edit(dtReportSection);
            session.setAttribute("ReportAdmin.dtReportSection", dtReportSection);
        }
        catch(Exception ex)
        {
       	 	logger.error("Error in Report updateReportSect: " +ex.getMessage());
       	 	ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            return mapping.findForward("error");
        }
        return mapping.findForward("default");
    }



}
