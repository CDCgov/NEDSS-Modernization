package gov.cdc.nedss.webapp.nbs.action.reportadmin;

import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.reportadmin.dao.DataSourceColumnDAO;
import gov.cdc.nedss.reportadmin.dt.DataSourceColumnDT;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.XMLRequestHelper;

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

/**
 * Edits records in the Data_source_column table.
 * @author Ed Jenkins
 */
public class EditDataSourceColumn extends Action
{

    /**
     * Logger.
     */
    private static final Logger logger = Logger.getLogger(EditDataSourceColumn.class);

    /**
     * String Buffer Size.
     */
    private static final int STRING_BUFFER_SIZE = 1024;

    /**
     * DAO for Data_source_column.
     */
    private static final DataSourceColumnDAO daoDataSourceColumn = new DataSourceColumnDAO();

    /**
     * Constructor.
     */
    public EditDataSourceColumn()
    {
    }

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        HttpSession session = request.getSession(true);
        ReportAdminUtil util = new ReportAdminUtil();
        NBSSecurityObj sec = (NBSSecurityObj)session.getAttribute("NBSSecurityObject");
        try
        {
            // Return Link
            String data_source_uid_s = request.getParameter("data_source_uid") == null ? "" : request.getParameter("data_source_uid").trim();

            String direction = (String) session.getAttribute("direction");
            String sortMethod = (String) session.getAttribute("sortMethod");

        	String strLinkName = "Return to View Data Source";
            String strLinkAddr = "/nbs/ViewDataSource.do?data_source_uid=" + data_source_uid_s + "&contextAction=Sort&direction=" + direction + "&sortMethod=" + sortMethod;
            request.setAttribute("LinkName", strLinkName);
            request.setAttribute("LinkAddr", strLinkAddr);

            String column_uid_s = request.getParameter("column_uid");
            long column_uid = Long.parseLong(column_uid_s);
            DataSourceColumnDT dtDataSourceColumn = daoDataSourceColumn.view(column_uid);
            request.setAttribute("ReportAdmin.dtDataSourceColumn", dtDataSourceColumn);
            //Set ColumnTypeCodeSRT
            session.setAttribute("ReportAdmin.ColumnTypeCodeSRT", util.getDataSourceColumnTypes());

            StringBuffer sb = new StringBuffer(STRING_BUFFER_SIZE);

            // 1. Basic Filter (Report_Filter)
            int intReportFilter = daoDataSourceColumn.countReportFilter(column_uid);
            if(intReportFilter > 0)
            {
                sb.append("Data Source Column used as Basic Filter$");
            }
            // 2. Where Clause (Filter_Value)
            int intFilterValues = daoDataSourceColumn.countFilterValue(column_uid);
            if(intFilterValues > 0)
            {
            	sb.append("Data Source Column used in the Where Clause of the Advanced Filter$");
            }
            // 3. Column Selection (Display_column)
            int intDisplayColumn = daoDataSourceColumn.countDisplayColumn(column_uid);
            if(intDisplayColumn > 0)
            {
                sb.append("Data Source Column selected as a Displayable Column$");

            }

            if(sb.toString().length() > 0) {
            	request.setAttribute("error01", "This Column cannot be edited for the following reason(s):");
            	throw new NEDSSDAOSysException(sb.toString());
            }
        }
        catch(Exception ex)
        {
       	 	logger.error("Error in Report EditDataSourceCol: " +ex.getMessage());
       	 	ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            return mapping.findForward("error");
        }
        return mapping.findForward("default");
    }

}
