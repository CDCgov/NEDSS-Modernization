package gov.cdc.nedss.webapp.nbs.action.reportadmin;

import gov.cdc.nedss.reportadmin.dao.DataSourceColumnDAO;
import gov.cdc.nedss.reportadmin.dao.DataSourceDAO;
import gov.cdc.nedss.reportadmin.dao.ReportDAO;
import gov.cdc.nedss.reportadmin.dt.DataSourceDT;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

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
 * Views records in the Data_Source table.
 * @author Ed Jenkins
 */
public class ViewDataSource extends Action
{

    /**
     * Logger.
     */
    private static final Logger logger = Logger.getLogger(ViewDataSource.class);

    /**
     * DAO for Data_Source.
     */
    private static final DataSourceDAO daoDataSource = new DataSourceDAO();

    /**
     * DAO for Data_source_column.
     */
    private static final DataSourceColumnDAO daoDataSourceColumn = new DataSourceColumnDAO();

    /**
     * DAO for Report.
     */
    private static final ReportDAO daoReport = new ReportDAO();

    /**
     * Constructor.
     */
    public ViewDataSource()
    {
    }

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        HttpSession session = request.getSession(true);
        try
        {
            // Return Link
            String strLinkName = "Return to Data Sources";
            String strLinkAddr = "/nbs/ListDataSource.do";
            request.setAttribute("LinkName", strLinkName);
            request.setAttribute("LinkAddr", strLinkAddr);
            String contextAction = request.getParameter("ContextAction");

            String data_source_uid_s = request.getParameter("data_source_uid") == null ? "" : request.getParameter("data_source_uid").trim();
            long data_source_uid = 0L;
            if(data_source_uid_s.trim().length() > 0)
            {
                data_source_uid = Long.parseLong(data_source_uid_s);
            }
            DataSourceDT dtDataSource = daoDataSource.view(data_source_uid);
            session.setAttribute("ReportAdmin.dtDataSource", dtDataSource);
            ArrayList<Object> alDataSourceColumn = daoDataSourceColumn.list(data_source_uid);

            //sorting
            boolean invDirectionFlag = false;
            String sortMethod = request.getParameter("sortMethod");

            if (sortMethod == null || sortMethod.equals(""))
				sortMethod = "getColumnName";

            String invSortDirection = request.getParameter("direction");

            session.setAttribute("sortMethod", sortMethod);


			if (invSortDirection != null && invSortDirection.equals("false"))
				invDirectionFlag = false;
			else
				invDirectionFlag = true;

            session.setAttribute("direction", String.valueOf(invDirectionFlag));

			if (contextAction != null && contextAction.equals("Sort") && invDirectionFlag) {
				request.setAttribute("sortDirection", new Boolean(false));

			} else if (contextAction != null && contextAction.equals("Sort") && !invDirectionFlag)
					request.setAttribute("sortDirection", new Boolean(true));
			else
				request.setAttribute("sortDirection", new Boolean(invDirectionFlag));

			if (sortMethod != null && alDataSourceColumn != null && alDataSourceColumn.size() > 0) {
				NedssUtils util = new NedssUtils();
				util.sortObjectByColumn(sortMethod,(Collection<Object>) alDataSourceColumn,invDirectionFlag);
			}
			ArrayList<Object> dsColumnsFromDb = daoDataSourceColumn.getDSMetaData(dtDataSource);
			if(dsColumnsFromDb.size() == alDataSourceColumn.size()) {
				request.setAttribute("anyDsColumnsAvailable", "true");
			}
            session.setAttribute("ReportAdmin.alDataSourceColumn", alDataSourceColumn);
            ArrayList<Object> alReport = daoReport.listDataSource(data_source_uid);
            session.setAttribute("ReportAdmin.alReport", alReport);
            //
            setSRTsInSession(session);
        }
        catch(Exception ex)
        {
        	if(ex!=null && ex.getMessage()!=null && (ex.getMessage().toLowerCase().contains("invalid object name")
        			|| ex.getMessage().toLowerCase().contains("table or view does not exist"))){
        	 request.setAttribute("errorMessageNotValidDataSource",NEDSSConstants.DATA_SOURCE_NOT_AVAILABLE);
             return mapping.findForward("listDSource");
        	}
             
       	 	logger.error("Error in Report ViewDataSource: " +ex.getMessage());
       	 	ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            throw new ServletException(ex.getMessage(),ex);
           
        }
        return mapping.findForward("default");
    }

    private void setSRTsInSession(HttpSession session) {

    	ReportAdminUtil util = new ReportAdminUtil();

    	if(session.getAttribute("ReportAdmin.CodeValueGeneralSRT") == null)
    		session.setAttribute("ReportAdmin.CodeValueGeneralSRT", util.getDistinctCodeSetNm("CODE_VALUE_GENERAL"));

    	if(session.getAttribute("ReportAdmin.CodeValueClinicalSRT") == null)
    		session.setAttribute("ReportAdmin.CodeValueClinicalSRT", util.getDistinctCodeSetNm("CODE_VALUE_CLINICAL"));
    }

}
