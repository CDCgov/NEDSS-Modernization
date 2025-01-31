package gov.cdc.nedss.webapp.nbs.action.reportadmin;

import gov.cdc.nedss.reportadmin.dao.DataSourceColumnDAO;
import gov.cdc.nedss.reportadmin.dao.DataSourceDAO;
import gov.cdc.nedss.reportadmin.dao.ReportDAO;
import gov.cdc.nedss.reportadmin.dao.UserProfileDAO;
import gov.cdc.nedss.reportadmin.dt.DataSourceDT;
import gov.cdc.nedss.reportadmin.dt.ReportDT;
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
 * Prepares a blank record for the Report table.
 * @author Ed Jenkins
 */
public class NewReport extends Action
{

    /**
     * Logger.
     */
    private static final Logger logger = Logger.getLogger(NewReport.class);

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
    private static final ReportDAO daoReport = new ReportDAO();

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
    public NewReport()
    {
    }

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        HttpSession session = request.getSession(true);
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
                    if(direction == null) direction = "true";
                    String sortMethod = (String) session.getAttribute("sortMethod");
                    strLinkAddr = "/nbs/ViewDataSource.do?data_source_uid=" + data_source_uid_s + "&contextAction=Sort&direction=" + direction + "&sortMethod=" + sortMethod;
                }
            }
            request.setAttribute("LinkName", strLinkName);
            request.setAttribute("LinkAddr", strLinkAddr);
            request.setAttribute("context", strContext);

            ReportDT dtReport = new ReportDT();
            // If coming from ViewDataSource, the data_source_uid will be pre-populated.
            // If coming from ListReport, it will not.
            long data_source_uid = 0L;
            if(data_source_uid_s.trim().length() > 0)
            {
                data_source_uid = Long.parseLong(data_source_uid_s);
                dtReport.setDataSourceUID(data_source_uid);
            }
            // Set default owner to "SYSTEM".
            dtReport.setOwnerUID(0L);
            session.setAttribute("ReportAdmin.dtReport", dtReport);
            // ReportAdmin.alDataSourceColumn
            ArrayList<Object> alDataSourceColumn = daoDataSourceColumn.getAll(data_source_uid);
            session.setAttribute("ReportAdmin.alDataSourceColumn", alDataSourceColumn);
            // ReportAdmin.alDisplayColumn
            ArrayList<Object> alDisplayColumn = new ArrayList<Object> ();
            session.setAttribute("ReportAdmin.alDisplayColumn", alDisplayColumn);
            // ReportAdmin.tmFilterName
            TreeMap<Object,Object> tmFilterName = new TreeMap<Object,Object>();
            session.setAttribute("ReportAdmin.tmFilterName", tmFilterName);
            // ReportAdmin.tmDisplayableNameMap
            TreeMap<Object,Object> tmDisplayableNameMap = daoDataSourceColumn.getDisplayableNameMap(data_source_uid);
            session.setAttribute("ReportAdmin.tmDisplayableNameMap", tmDisplayableNameMap);
            // ReportAdmin.AvailableColumnsSRT
            StringBuffer sbAvailableColumns = new StringBuffer(STRING_BUFFER_SIZE);
           Iterator<Object>  iAvailableColumns = alDataSourceColumn.iterator();
            boolean booSelected = false;
            while(iAvailableColumns.hasNext())
            {
                Long L = (Long)iAvailableColumns.next();
                long column_uid = L.longValue();
                String column_uid_s = L.toString();
                String column_name = (String)tmDisplayableNameMap.get(L);
               Iterator<Object>  iSelectedColumns = alDisplayColumn.iterator();
                booSelected = false;
                while(iSelectedColumns.hasNext())
                {
                    Long CUID = (Long)iSelectedColumns.next();
                    long cuid = CUID.longValue();
                    if(cuid == column_uid)
                    {
                        booSelected = true;
                        break;
                    }
                }
                if(booSelected == false)
                {
                    sbAvailableColumns.append(column_uid_s);
                    sbAvailableColumns.append("$");
                    sbAvailableColumns.append(column_name);
                    sbAvailableColumns.append("|");
                }
            }
            String strAvailableColumns = sbAvailableColumns.toString();
            session.setAttribute("ReportAdmin.AvailableColumnsSRT", strAvailableColumns);
            // ReportAdmin.SelectedColumnsSRT
            StringBuffer sbSelectedColumns = new StringBuffer(STRING_BUFFER_SIZE);
           Iterator<Object>  iSelectedColumns = alDisplayColumn.iterator();
            while(iSelectedColumns.hasNext())
            {
                Long L = (Long)iSelectedColumns.next();
                String column_uid_s = L.toString();
                String column_name = (String)tmDisplayableNameMap.get(L);
                sbSelectedColumns.append(column_uid_s);
                sbSelectedColumns.append("$");
                sbSelectedColumns.append(column_name);
                sbSelectedColumns.append("|");
            }
            String strSelectedColumns = sbSelectedColumns.toString();
            session.setAttribute("ReportAdmin.SelectedColumnsSRT", strSelectedColumns);
            if (session.getAttribute("ReportAdmin.ReportSectionCode") != null)
            		session.removeAttribute("ReportAdmin.ReportSectionCode");
            setSRTsInSession(session);
        }
        catch(Exception ex)
        {
       	 	logger.error("Error in Report NewReport: " +ex.getMessage());
       	 	ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            return mapping.findForward("default");
        }
        return mapping.findForward("default");
    }

    private void setSRTsInSession(HttpSession session) throws Exception {

    	ReportAdminUtil util = new ReportAdminUtil();

   		session.setAttribute("ReportAdmin.SASProgramSRT", util.getSASProgramSRT());

    	if(session.getAttribute("ReportAdmin.ReportTypeCodeSRT") == null)
    		session.setAttribute("ReportAdmin.ReportTypeCodeSRT", util.getReportTypeCdSRT());

    	if(session.getAttribute("ReportAdmin.SharedSRT") == null)
    		session.setAttribute("ReportAdmin.SharedSRT", util.getSharedSRT());

    	if(session.getAttribute("ReportAdmin.UserProfileSRT") == null)
    		session.setAttribute("ReportAdmin.UserProfileSRT", util.getUserProfileSRT());
    	
    	if(session.getAttribute("ReportAdmin.ReportSectionCode") == null)
    		session.setAttribute("ReportAdmin.ReportSectionCode", util.getSectionCode());

        session.setAttribute("ReportAdmin.DataSourceSRT", util.getDataSourceSRT());
        session.setAttribute("ReportAdmin.DataSourceTitleSRT", util.getDataSourceTitleSRT());

    }

}
