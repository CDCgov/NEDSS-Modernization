package gov.cdc.nedss.webapp.nbs.action.reportadmin;

import gov.cdc.nedss.reportadmin.dao.DataSourceColumnDAO;
import gov.cdc.nedss.reportadmin.dao.DataSourceDAO;
import gov.cdc.nedss.reportadmin.dao.ReportSectionDAO;
import gov.cdc.nedss.reportadmin.dao.UserProfileDAO;
import gov.cdc.nedss.reportadmin.dt.DataSourceDT;
import gov.cdc.nedss.reportadmin.dt.ReportSectionDT;
import gov.cdc.nedss.reportadmin.dt.UserProfileDT;
import gov.cdc.nedss.util.PropertyUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;

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
 * Prepares a blank record for the Report Section table.
 * @author Karthik Chinnayan
 */
public class NewReportSection extends Action
{

    /**
     * Logger.
     */
    private static final Logger logger = Logger.getLogger(NewReportSection.class);

    /**
     * String Buffer Size.
     */
    private static final int STRING_BUFFER_SIZE = 1024;

    /**
     * DAO for Data_Source.
     */
    private static final DataSourceDAO daoDataSource = new DataSourceDAO();

    /**
     * DAO for Report.
     */
    private static final ReportSectionDAO daoReportSection = new ReportSectionDAO();

    /**
     * DAO for USER_PROFILE.
     */
    private static final UserProfileDAO daoUserProfile = new UserProfileDAO();

    /**
     * DAO for Data_source_column.
     */
    private static final DataSourceColumnDAO daoDataSourceColumn = new DataSourceColumnDAO();


    /**
     * Constructor.
     */
    public NewReportSection()
    {
    }

    public ActionForward execute(ActionMapping mapping, ActionForm form, 
    		HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        HttpSession session = request.getSession(true);
        try
        {
            // Return Link
            String strLinkName = "Return to Report Sections";
            String strLinkAddr = "/nbs/ListReportSections.do";
            String strContext = request.getParameter("context");
           
            request.setAttribute("LinkName", strLinkName);
            request.setAttribute("LinkAddr", strLinkAddr);
            request.setAttribute("context", strContext);

            ReportSectionDT dtReportSection = new ReportSectionDT();
           
            session.setAttribute("ReportAdmin.dtReportSection", dtReportSection);
        }
        catch(Exception ex)
        {
       	 	logger.error("Error in Report NewReportSect: " +ex.getMessage());
       	 	ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            return mapping.findForward("default");
        }
        return mapping.findForward("default");
    }
}
