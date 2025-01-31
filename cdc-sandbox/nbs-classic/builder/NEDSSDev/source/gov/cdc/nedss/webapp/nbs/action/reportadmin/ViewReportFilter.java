package gov.cdc.nedss.webapp.nbs.action.reportadmin;

import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.reportadmin.dao.DataSourceColumnDAO;
import gov.cdc.nedss.reportadmin.dao.FilterCodeDAO;
import gov.cdc.nedss.reportadmin.dao.ReportFilterDAO;
import gov.cdc.nedss.reportadmin.dt.DataSourceColumnDT;
import gov.cdc.nedss.reportadmin.dt.FilterCodeDT;
import gov.cdc.nedss.reportadmin.dt.ReportDT;
import gov.cdc.nedss.reportadmin.dt.ReportFilterDT;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;

import java.io.File;
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
 * Views records in the Report_Filter table.
 * @author Ed Jenkins
 */
public class ViewReportFilter extends Action
{

    /**
     * Logger.
     */
    private static final Logger logger = Logger.getLogger(ViewReportFilter.class);

    /**
     * String Buffer Size.
     */
    private static final int STRING_BUFFER_SIZE = 1024;

    /**
     * DAO for Report_Filter.
     */
    private static final ReportFilterDAO daoReportFilter = new ReportFilterDAO();

    /**
     * DAO for Filter_code.
     */
    private static final FilterCodeDAO daoFilterCode = new FilterCodeDAO();

    /**
     * DAO for Data_source_column.
     */
    private static final DataSourceColumnDAO daoDataSourceColumn = new DataSourceColumnDAO();

    /**
     * Column Type Code option string.
     */
    private static String strColumnTypeCode = null;

    /**
     * Constructor.
     */
    public ViewReportFilter()
    {
        // ReportAdmin.ColumnTypeCodeSRT
        TreeMap<Object,Object> tmColumnTypeCode = new TreeMap<Object,Object>();
        tmColumnTypeCode.put("STRING", "String");
        tmColumnTypeCode.put("INTEGER", "Number");
        tmColumnTypeCode.put("DATETIME", "Date/Time");
        StringBuffer sbColumnTypeCode = new StringBuffer(STRING_BUFFER_SIZE);
        Set<Object> setColumnTypeCode = tmColumnTypeCode.keySet();
       Iterator<Object>  iColumnTypeCode = setColumnTypeCode.iterator();
        while(iColumnTypeCode.hasNext())
        {
            String k = (String)iColumnTypeCode.next();
            String v = (String)tmColumnTypeCode.get(k);
            sbColumnTypeCode.append(k);
            sbColumnTypeCode.append("$");
            sbColumnTypeCode.append(v);
            sbColumnTypeCode.append("|");
        }
        strColumnTypeCode = sbColumnTypeCode.toString();
    }

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        HttpSession session = request.getSession(true);
        NBSSecurityObj sec = (NBSSecurityObj)session.getAttribute("NBSSecurityObject");
        try
        {
            // Return Link
            ReportDT dtReport = (ReportDT)session.getAttribute("ReportAdmin.dtReport");
            String data_source_uid_s = dtReport.getDataSourceUID_s();
            String report_uid_s = dtReport.getReportUID_s();
            String strLinkName = "Return to View Report";
            String strLinkAddr = "/nbs/ViewReport.do?data_source_uid=" + data_source_uid_s + "&amp;report_uid=" + report_uid_s;
            request.setAttribute("LinkName", strLinkName);
            request.setAttribute("LinkAddr", strLinkAddr);
            // ReportAdmin.ColumnTypeCodeSRT
            session.setAttribute("ReportAdmin.ColumnTypeCodeSRT", strColumnTypeCode);
            // ReportAdmin.dtReportFilter
            String report_filter_uid_s = request.getParameter("report_filter_uid");
            long report_filter_uid = 0L;
            if(report_filter_uid_s != null)
            {
                report_filter_uid = Long.parseLong(report_filter_uid_s);
            }
            ReportFilterDT dtReportFilter = daoReportFilter.view(report_filter_uid);
            session.setAttribute("ReportAdmin.dtReportFilter", dtReportFilter);
            // ReportAdmin.dtFilterCode
            long filter_uid = dtReportFilter.getFilterUID();
            FilterCodeDT dtFilterCode = daoFilterCode.view(filter_uid);
            session.setAttribute("ReportAdmin.dtFilterCode", dtFilterCode);
            // ReportAdmin.dtDataSourceColumn
            long column_uid = dtReportFilter.getColumnUID();
            if(column_uid == 0L)
            {
                session.removeAttribute("ReportAdmin.dtDataSourceColumn");
            }
            else
            {
                DataSourceColumnDT dtDataSourceColumn = daoDataSourceColumn.view(column_uid);
                request.setAttribute("ReportAdmin.dtDataSourceColumn", dtDataSourceColumn);
            }
        }
        catch(Exception ex)
        {
       	 	logger.error("Error in Report ViewReportFilter: " +ex.getMessage());
       	 	ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            throw new ServletException(ex.getMessage(),ex);
        }
        return mapping.findForward("default");
    }

}
