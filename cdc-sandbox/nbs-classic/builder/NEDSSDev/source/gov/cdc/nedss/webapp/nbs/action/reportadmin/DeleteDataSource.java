package gov.cdc.nedss.webapp.nbs.action.reportadmin;

import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.reportadmin.dao.DataSourceColumnDAO;
import gov.cdc.nedss.reportadmin.dao.DataSourceDAO;
import gov.cdc.nedss.reportadmin.dt.DataSourceColumnDT;

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

/**
 * Delete Data Source.
 * @author Ed Jenkins
 */
public class DeleteDataSource extends Action
{

    /**
     * Logger.
     */
    private static final Logger logger = Logger.getLogger(DeleteDataSource.class);

    /**
     * String Buffer Size.
     */
    private static final int STRING_BUFFER_SIZE = 1024;

    /**
     * DAO for Data_Source.
     */
    private static final DataSourceDAO daoDataSource = new DataSourceDAO();
    private static final DataSourceColumnDAO dataSourceColumnDAO = new DataSourceColumnDAO();

    /**
     * Constructor.
     */
    public DeleteDataSource()
    {
    }

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {

    	HttpSession session = request.getSession(true);
        try
        {
            // Return Link
            String strLinkName = "Return to System Management Main Menu";
            String strLinkAddr = "/nbs/ReportAdministration.do?focus=systemAdmin5";
            request.setAttribute("LinkName", strLinkName);
            request.setAttribute("LinkAddr", strLinkAddr);
            String data_source_uid_s = request.getParameter("data_source_uid") == null ? "" : request.getParameter("data_source_uid").trim();
            long data_source_uid = 0L;
            if(data_source_uid_s.trim().length() > 0)
            {
                data_source_uid = Long.parseLong(data_source_uid_s);
            }
            // Report
            int intReport = daoDataSource.countReport(data_source_uid);
            if(intReport > 0)
            {
                StringBuffer sbReport = new StringBuffer(STRING_BUFFER_SIZE);
                sbReport.append("This data source cannot be deleted because there");
                if(intReport == 1)
                {
                    sbReport.append(" is a report ");
                }
                else
                {
                    sbReport.append(" are ");
                    sbReport.append(intReport);
                    sbReport.append(" reports ");
                }
                sbReport.append("associated with it.");
                if(intReport == 1)
                {
                    sbReport.append("  The report");
                }
                else
                {
                    sbReport.append("  The reports");
                }
                sbReport.append(" must be deleted before the data source can be deleted.");
                String strReport = sbReport.toString();
                throw new NEDSSDAOSysException(strReport);
            }


            //check for if any datasource columns added to this datasource and delete them all
            int countDSColumns = daoDataSource.countDataSourceColumn(data_source_uid);
            if (countDSColumns > 0) {

            	ArrayList<Object> alDataSourceColumn = (ArrayList<Object> )session.getAttribute("ReportAdmin.alDataSourceColumn");

            	Iterator<Object> iter = alDataSourceColumn.iterator();
            	//Iterate through the list and delete individual DSColumns (to make sure if any associated codeset gets deleted from Data_Source_Codeset
            	while(iter.hasNext()) {

            		DataSourceColumnDT dt = (DataSourceColumnDT) iter.next();
            		dataSourceColumnDAO.delete(dataSourceColumnDAO.view(dt.getColumnUID()));
            	}
            }
            //then delete the datasource itself
            daoDataSource.delete(data_source_uid);
        }
        catch(Exception ex)
        {
       	 logger.error("Error in Report Delete Data Source: " +ex.getMessage());
       	 ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            return mapping.findForward("error");
        }
        return mapping.findForward("default");
    }

}
