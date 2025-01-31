package gov.cdc.nedss.webapp.nbs.action.reportadmin;

import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.reportadmin.dao.ReportDAO;
import gov.cdc.nedss.reportadmin.dt.ReportDT;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.HTMLEncoder;

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
 * Creates records in the Report table.
 * @author Ed Jenkins
 */
public class CreateReport extends Action
{

    /**
     * Logger.
     */
    private static final Logger logger = Logger.getLogger(CreateReport.class);

    /**
     * DAO for Report.
     */
    private static final ReportDAO daoReport = new ReportDAO();

    /**
     * Constructor.
     */
    public CreateReport()
    {
    }

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        HttpSession session = request.getSession(true);
        session.removeAttribute("ReportAdmin.allReportFilters");
        String strRedirect="";
        NBSSecurityObj sec = (NBSSecurityObj)session.getAttribute("NBSSecurityObject");
        try
        {
            // Return Link
            String data_source_uid_s = request.getParameter("data_source_uid") == null ? "" : request.getParameter("data_source_uid").trim();

            String strLinkName = "Return to Reports";
            String strLinkAddr = "/nbs/ListReport.do";
            String strContext = request.getParameter("context");
            if(strContext != null)
            {
                if(strContext.equalsIgnoreCase("ViewDataSource"))
                {
                    strLinkName = "Return to View Data Source";
                    String direction = (String) session.getAttribute("direction");
                    String sortMethod = (String) session.getAttribute("sortMethod");
                    strLinkAddr = "/nbs/ViewDataSource.do?data_source_uid=" + data_source_uid_s + "&contextAction=Sort&direction=" + direction + "&sortMethod=" + sortMethod;
                }
            }
            request.setAttribute("LinkName", strLinkName);
            request.setAttribute("LinkAddr", strLinkAddr);
            request.setAttribute("context", strContext);
            long next_report_uid = daoReport.getNextUID();
            strRedirect = "/ViewReport.do?data_source_uid=" + request.getParameter("data_source_uid") + "&report_uid=" + next_report_uid;
            ReportDT dtReport = (ReportDT)session.getAttribute("ReportAdmin.dtReport");
            dtReport.setReportUID(next_report_uid);
            dtReport.setDataSourceUID(request.getParameter("data_source_uid") == null ? "" : request.getParameter("data_source_uid").trim());
            dtReport.setDescTxt(HTMLEncoder.encodeHtml(request.getParameter("desc_txt")));
            dtReport.setLocation(request.getParameter("location"));
            dtReport.setOwnerUID(request.getParameter("owner_uid"));
            dtReport.setReportTitle(HTMLEncoder.encodeHtml(request.getParameter("report_title")));
            dtReport.setReportTypeCode(request.getParameter("report_type_code"));
            dtReport.setShared(request.getParameter("shared"));
            dtReport.setReportSectionCode(request.getParameter("section_cd"));
            dtReport.validate();
            daoReport.add(dtReport);
            session.setAttribute("ReportAdmin.dtReport", dtReport);
        }
        catch(Exception ex)
        {
        	 logger.error("Error in Report CreateReport: " +ex.getMessage());
        	 ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            return mapping.findForward("error");
        }
        ActionForward af = new ActionForward();
        af.setName("ViewReport");
        af.setPath(strRedirect);
        af.setRedirect(true);
        return af;
        //return mapping.findForward("default");
    }

}
