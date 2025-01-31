package gov.cdc.nedss.webapp.nbs.action.reportadmin;

import gov.cdc.nedss.reportadmin.dao.DataSourceColumnDAO;
import gov.cdc.nedss.reportadmin.dt.DataSourceColumnDT;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

import java.io.IOException;
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
 * Views records in the Data_source_column table.
 * @author Ed Jenkins
 */
public class ViewDataSourceColumn extends Action
{

    /**
     * Logger.
     */
    private static final Logger logger = Logger.getLogger(ViewDataSourceColumn.class);

    /**
     * String Buffer Size.
     */
    private static final int STRING_BUFFER_SIZE = 1024;

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
    public ViewDataSourceColumn()
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
        try
        {
            // Return Link
            String data_source_uid_s = request.getParameter("data_source_uid") == null ? "" : request.getParameter("data_source_uid").trim();

            String report_uid_s = request.getParameter("report_uid");
            long report_uid = 0L;
            if(report_uid_s != null)
            {
                report_uid = Long.parseLong(report_uid_s);
            }
            String context = request.getParameter("context");
            String strLinkName = null;
            String strLinkAddr = null;
            if(context == null)
            {
                strLinkName = "Return to View Data Source";
                String direction = (String) session.getAttribute("direction");
                String sortMethod = (String) session.getAttribute("sortMethod");
                strLinkAddr = "/nbs/ViewDataSource.do?data_source_uid=" + data_source_uid_s + "&contextAction=Sort&direction=" + direction + "&sortMethod=" + sortMethod;
            }
            else
            {
                strLinkName = "Return to View Report";
                strLinkAddr = "/nbs/ViewReport.do?data_source_uid=" + data_source_uid_s + "&amp;report_uid=" + report_uid_s;
            }
            request.setAttribute("LinkName", strLinkName);
            request.setAttribute("LinkAddr", strLinkAddr);
            // ReportAdmin.ColumnTypeCodeSRT
            session.setAttribute("ReportAdmin.ColumnTypeCodeSRT", strColumnTypeCode);
            // ReportAdmin.dtDataSourceColumn
            String column_uid_s = request.getParameter("column_uid");
            long column_uid = Long.parseLong(column_uid_s);
            DataSourceColumnDT dtDataSourceColumn = daoDataSourceColumn.view(column_uid);


            request.setAttribute("ReportAdmin.dtDataSourceColumn", dtDataSourceColumn);
        }
        catch(Exception ex)
        {
            logger.error(ex, ex);
            request.setAttribute("error", ex.getMessage());
            throw new ServletException(ex.getMessage(),ex);
        }
        return mapping.findForward("default");
    }

}
