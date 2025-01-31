package gov.cdc.nedss.webapp.nbs.action.reportadmin;

import gov.cdc.nedss.reportadmin.dao.ReportDAO;
import gov.cdc.nedss.reportadmin.dao.ReportSectionDAO;
import gov.cdc.nedss.reportadmin.dt.ReportDT;
import gov.cdc.nedss.reportadmin.dt.ReportSectionDT;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;

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

public class CreateReportSection extends Action{

	


    /**
     * Logger.
     */
    private static final Logger logger = Logger.getLogger(CreateReport.class);

    /**
     * DAO for Report.
     */
    private static final ReportSectionDAO daoReportSection = new ReportSectionDAO();

    /**
     * Constructor.
     */
    public CreateReportSection()
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
            String strLinkName = "Return to Reports Section";
            String strLinkAddr = "/nbs/ListReportSections.do";
            String strContext = request.getParameter("context");
           
            request.setAttribute("LinkName", strLinkName);
            request.setAttribute("LinkAddr", strLinkAddr);
            request.setAttribute("context", strContext);
            NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session.getAttribute("NBSSecurityObject");
            String userId = nbsSecurityObj.getTheUserProfile().getTheUser().getEntryID();
            long lastChangUid=Long.parseLong(userId);

            long next_section_cd = daoReportSection.getNextCode();
            strRedirect = "/ViewReportSection.do?section_cd=" + next_section_cd ;
            ReportSectionDT dtReportSection = (ReportSectionDT)session.getAttribute("ReportAdmin.dtReportSection");
            dtReportSection.setDuplicateError(null);
            dtReportSection.setSectionCd(next_section_cd);
            dtReportSection.setSectionDescTxt(request.getParameter("section_desc_txt"));
            dtReportSection.setComments(request.getParameter("comments"));
            dtReportSection.setLastChgUserUID(lastChangUid);
            dtReportSection.setAddUserUID(lastChangUid);
            ArrayList<Object> alSectionsNm = daoReportSection.selectName();
            if(alSectionsNm.contains(dtReportSection.getSectionDescTxt()))
            {
            	dtReportSection.setDuplicateError("Duplicate");
            }
            	
            dtReportSection.validate();
            daoReportSection.add(dtReportSection);
        }
        catch(Exception ex)
        {
       	 logger.error("Error in Report CreateReportSection: " +ex.getMessage());
       	 ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            return mapping.findForward("error");
        }
        ActionForward af = new ActionForward();
        af.setName("ViewReportSection");
        af.setPath(strRedirect);
        af.setRedirect(true);
        return af;
        //return mapping.findForward("default");
    }


}
