package gov.cdc.nedss.webapp.nbs.action.reportadmin;

import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.reportadmin.dao.DisplayColumnDAO;
import gov.cdc.nedss.reportadmin.dao.FilterValueDAO;
import gov.cdc.nedss.reportadmin.dao.ReportDAO;
import gov.cdc.nedss.reportadmin.dao.ReportFilterDAO;
import gov.cdc.nedss.reportadmin.dao.ReportFilterValidationDAO;
import gov.cdc.nedss.reportadmin.dao.ReportSortColumnDAO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

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
 * Delete Report.
 * @author Ed Jenkins
 */
public class DeleteReport extends Action
{

    /**
     * Logger.
     */
    private static final Logger logger = Logger.getLogger(DeleteReport.class);

    /**
     * String Buffer Size.
     */
    private static final int STRING_BUFFER_SIZE = 1024;

    /**
     * DAO for Report.
     */
    private static final ReportDAO daoReport = new ReportDAO();

    /**
     * DAO for Report_Sort_Column.
     */
    private static final ReportSortColumnDAO daoReportSortColumn = new ReportSortColumnDAO();
    private static final DisplayColumnDAO displayColumnDAO = new DisplayColumnDAO();
    private static final ReportFilterDAO reportFilterDAO = new ReportFilterDAO();
    private static final FilterValueDAO filterValueDAO = new FilterValueDAO();
    private static final ReportFilterValidationDAO reportFilterValidationDAO = new ReportFilterValidationDAO();


    /**
     * Constructor.
     */
    public DeleteReport()
    {
    }

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        HttpSession session = request.getSession(true);
        String strRedirect = null;
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
                    strRedirect = "/ViewDataSource.do?data_source_uid=" + data_source_uid_s + "&contextAction=Sort&direction=" + direction + "&sortMethod=" + sortMethod;
                }
            }
            request.setAttribute("LinkName", strLinkName);
            request.setAttribute("LinkAddr", strLinkAddr);
            request.setAttribute("context", strContext);
            // Display_column and Report_Filter
            int intDisplayColumn = daoReport.countDisplayColumn(data_source_uid, report_uid);
            int intReportFilter = daoReport.countReportFilter(data_source_uid, report_uid);
            //get all report_filter_uids from session, iterate through each and find any filter values to delete first
            String filterString = (String) session.getAttribute("ReportAdmin.allReportFilters");
            ArrayList<Object> filterUids = fetchFilterUidsFromSt(filterString);
           Iterator<Object>  uidIter = filterUids.iterator();

            while(uidIter.hasNext()) {

            	long reportFilterUid = Long.valueOf((String)uidIter.next()).longValue();

            		filterValueDAO.deleteAll(reportFilterUid);
            		reportFilterValidationDAO.delete(reportFilterUid);

          
            }




            //Delete Report Filtes
        	if(intReportFilter > 0)
        		reportFilterDAO.deleteAll(data_source_uid, report_uid);
            //Delete Display Column
            if(intDisplayColumn > 0)
            	displayColumnDAO.clearAll(data_source_uid, report_uid);
            // Report_Sort_Column
            daoReportSortColumn.delete(data_source_uid, report_uid);
            // Report
            daoReport.delete(data_source_uid, report_uid);
        }
        catch(Exception ex)
        {
       	 logger.error("Error in Report DeleteReport: " +ex.getMessage());
       	 ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            // Return Link
            String strLinkName = "Return to Reports";
            String strLinkAddr = "/nbs/ListReport.do";
            String strContext = request.getParameter("context");
            request.setAttribute("LinkName", strLinkName);
            request.setAttribute("LinkAddr", strLinkAddr);
            request.setAttribute("context", strContext);
            return mapping.findForward("error");
        }
        if(strRedirect != null)
        {
            ActionForward af = new ActionForward();
            af.setName("ViewDataSource");
            af.setPath(strRedirect);
            af.setRedirect(true);
            return af;
        }
        return mapping.findForward("default");
    }

    private ArrayList<Object> fetchFilterUidsFromSt(String filterString) {

    	ArrayList<Object> returnList = new ArrayList<Object> ();

    	if (filterString != null && filterString.length() > 0) {

       	 	StringTokenizer st = new StringTokenizer(filterString, "|");

   			while(st.hasMoreTokens()) {

                    String rowElmnt = st.nextToken();
                    StringTokenizer innerSt = new StringTokenizer(rowElmnt, "$");
                    String reportFilterUid =  innerSt.nextToken();
                    returnList.add(reportFilterUid);
   			}
    	}
    	return returnList;
    }

}
