package gov.cdc.nedss.webapp.nbs.action.reportadmin;

import gov.cdc.nedss.reportadmin.dao.DataSourceColumnDAO;
import gov.cdc.nedss.reportadmin.dao.DataSourceDAO;
import gov.cdc.nedss.reportadmin.dao.DisplayColumnDAO;
import gov.cdc.nedss.reportadmin.dao.ReportDAO;
import gov.cdc.nedss.reportadmin.dao.ReportFilterDAO;
import gov.cdc.nedss.reportadmin.dao.ReportSortColumnDAO;
import gov.cdc.nedss.reportadmin.dt.DataSourceDT;
import gov.cdc.nedss.reportadmin.dt.ReportDT;
import gov.cdc.nedss.reportadmin.dt.ReportSortColumnDT;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
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
 * Views records in the Report table.
 * @author Ed Jenkins
 */
public class ViewReport extends Action
{

    /**
     * Logger.
     */
    private static final Logger logger = Logger.getLogger(ViewReport.class);

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
     * DAO for Display_column.
     */
    private static final DisplayColumnDAO daoDisplayColumn = new DisplayColumnDAO();

    /**
     * DAO for Data_source_column.
     */
    private static final DataSourceColumnDAO daoDataSourceColumn = new DataSourceColumnDAO();

    /**
     * DAO for Report_Filter.
     */
    private static final ReportFilterDAO daoReportFilter = new ReportFilterDAO();

    /**
     * DAO for Report_Sort_Column.
     */
    private static final ReportSortColumnDAO daoReportSortColumn = new ReportSortColumnDAO();

    /**
     * Constructor.
     */
    public ViewReport()
    {
    }

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        HttpSession session = request.getSession(true);
        ReportAdminUtil util = new ReportAdminUtil();
        try
        {
            // Return Link
            String data_source_uid_s = request.getParameter("data_source_uid") == null ? "" : request.getParameter("data_source_uid").trim();
            long data_source_uid = 0L;
            if(data_source_uid_s.trim().length() > 0)
            {
                data_source_uid = Long.parseLong(data_source_uid_s);
            }
            String report_uid_s = request.getParameter("report_uid");
            long report_uid = Long.parseLong(report_uid_s);
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
            
            //ND-11698
            session.setAttribute("ReportAdmin.DataSourceTitleSRT", util.getDataSourceTitleSRT());
            
            // ReportAdmin.dtReport
            ReportDT dtReport = daoReport.view(data_source_uid, report_uid);
            session.setAttribute("ReportAdmin.dtReport", dtReport);
            // ReportAdmin.dtDataSource
            DataSourceDT dtDataSource = daoDataSource.view(data_source_uid);
            session.setAttribute("ReportAdmin.dtDataSource", dtDataSource);
            // ReportAdmin.alDataSourceColumn
            ArrayList<Object> alDataSourceColumn = daoDataSourceColumn.getAll(data_source_uid);
            session.setAttribute("ReportAdmin.alDataSourceColumn", alDataSourceColumn);
            // ReportAdmin.alDisplayColumn
            ArrayList<Object> alDisplayColumn = daoDisplayColumn.getAll(data_source_uid, report_uid);
            session.setAttribute("ReportAdmin.alDisplayColumn", alDisplayColumn);
            // ReportAdmin.tmFilterName
            String allReportFilters = daoReportFilter.listAllFilters(data_source_uid, report_uid);

            session.setAttribute("ReportAdmin.allReportFilters", allReportFilters);
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
                long column_uid = L.longValue();
                String column_uid_s = L.toString();
                String column_name = (String)tmDisplayableNameMap.get(L);
                sbSelectedColumns.append(column_uid_s);
                sbSelectedColumns.append("$");
                sbSelectedColumns.append(column_name);
                sbSelectedColumns.append("|");
            }
            String strSelectedColumns = sbSelectedColumns.toString();
            session.setAttribute("ReportAdmin.SelectedColumnsSRT", strSelectedColumns);
            // ReportAdmin.tmFilterableNameMap
            TreeMap<Object,Object> tmFilterableNameMap = daoDataSourceColumn.getFilterableNameMap(data_source_uid);
            session.setAttribute("ReportAdmin.tmFilterableNameMap", tmFilterableNameMap);
            // ReportAdmin.FilterableColumnSRT
            StringBuffer sbFilterableColumn = new StringBuffer(STRING_BUFFER_SIZE);
            Set<Object> setFilterableColumn = tmFilterableNameMap.keySet();
           Iterator<Object>  iFilterableColumn = setFilterableColumn.iterator();
            while(iFilterableColumn.hasNext())
            {
                String v = (String)iFilterableColumn.next();
                Long L = (Long)tmFilterableNameMap.get(v);
                String k = L.toString();
                sbFilterableColumn.append(k);
                sbFilterableColumn.append("$");
                sbFilterableColumn.append(v);
                sbFilterableColumn.append("|");
            }
            String strFilterableColumn = sbFilterableColumn.toString();
            session.setAttribute("ReportAdmin.FilterableColumnSRT", strFilterableColumn);
            // ReportAdmin.dtReportSortColumn
            ReportSortColumnDT dtReportSortColumn = daoReportSortColumn.view(data_source_uid, report_uid);
            session.setAttribute("ReportAdmin.dtReportSortColumn", dtReportSortColumn);

            // Carry over error messages through a redirect.
            String strError = (String)session.getAttribute("error");
            if(strError != null)
            {
                request.setAttribute("error", strError);
                session.removeAttribute("error");
            }
            session.removeAttribute("ReportAdmin.ReportSectionCode");
            
            setSRTsInSession(session);
        }
        catch(Exception ex)
        {
       	 	logger.error("Error in Report ViewReport: " +ex.getMessage());
       	 	ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            throw new ServletException(ex.getMessage(),ex);
        }

        return mapping.findForward("default");
    }

    private void setSRTsInSession(HttpSession session) throws Exception {

    	ReportAdminUtil util = new ReportAdminUtil();

    	if(session.getAttribute("ReportAdmin.SASProgramSRT") == null)
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
    }

}
