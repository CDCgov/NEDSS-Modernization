package gov.cdc.nedss.webapp.nbs.action.reportadmin;

import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.reportadmin.dao.ReportFilterDAO;
import gov.cdc.nedss.reportadmin.dt.ReportFilterDT;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.StringUtils;

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

/**
 * Creates records in the Report_Filter table.
 * @author Ed Jenkins
 */
public class CreateReportFilter extends Action
{

    /**
     * Logger.
     */
    private static final Logger logger = Logger.getLogger(CreateReportFilter.class);

    /**
     * DAO for Report_Filter.
     */
    private static final ReportFilterDAO daoReportFilter = new ReportFilterDAO();

    /**
     * Constructor.
     */
    public CreateReportFilter()
    {
    }

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        HttpSession session = request.getSession(true);
        NBSSecurityObj sec = (NBSSecurityObj)session.getAttribute("NBSSecurityObject");
        String strRedirect = null;
        try
        {
            // Return Link
            String data_source_uid_s = request.getParameter("data_source_uid") == null ? "" : StringUtils.escapeSql(request.getParameter("data_source_uid").trim());
            String report_uid_s = request.getParameter("report_uid");
            String strLinkName = "Return to Reports";
            String strLinkAddr = "/nbs/ListReport.do";
            request.setAttribute("LinkName", strLinkName);
            request.setAttribute("LinkAddr", strLinkAddr);
            long next_report_filter_uid = daoReportFilter.getNextUID();
            ReportFilterDT dtReportFilter = (ReportFilterDT)session.getAttribute("ReportAdmin.dtReportFilter");
            dtReportFilter.setReportFilterUID(next_report_filter_uid);
            dtReportFilter.setFilterUID(request.getParameter("filter_uid"));
            //set min, max, column Uid and Required Attributes based on the selection
            String filterUid_s = request.getParameter("filter_uid");
            int filterUid = Integer.parseInt(filterUid_s);
            String column_uid = StringUtils.escapeSql(request.getParameter("column_uid"));
            String reportFilterInd = StringUtils.escapeSql(request.getParameter("report_filter_ind"));
            String reportFilterType = request.getParameter("reportFilterType");


            switch (filterUid) {

            	//WhereClause Builder
            	case 7:
                    dtReportFilter.setMinValueCount(0);
                    dtReportFilter.setMaxValueCount(-1);
                    break;

                //TimeRange & TimePeriod
            	case 5: case 6: case 12 : case 13: case 14: case 15: case 17: case 18:
                    dtReportFilter.setMinValueCount(1);
                    dtReportFilter.setMaxValueCount(2);
                    dtReportFilter.setColumnUID(column_uid);
                    dtReportFilter.setReportFilterInd(reportFilterInd);
                    break;

                //Disease, State & County
            	case 1: case 2: case 3 : case 8: case 9 : case 10: case 16: case 19: case 20: case 21: 
            		if(reportFilterType != null && reportFilterType.equalsIgnoreCase("SS")) {

            			dtReportFilter.setMinValueCount(1);
                        dtReportFilter.setMaxValueCount(1);

            		} else if(reportFilterType != null && reportFilterType.equalsIgnoreCase("MS")) {

            			dtReportFilter.setMinValueCount(1);
                        dtReportFilter.setMaxValueCount(-1);
            		}
                    dtReportFilter.setColumnUID(column_uid);
                    dtReportFilter.setReportFilterInd(reportFilterInd);
                    break;

            }

            //dtReportFilter.validate();
            daoReportFilter.add(dtReportFilter);
            session.setAttribute("ReportAdmin.dtReportFilter", dtReportFilter);
            strRedirect = "/ViewReport.do?data_source_uid=" + data_source_uid_s + "&report_uid=" + report_uid_s;
        }
        catch(Exception ex)
        {
       	 logger.error("Error in Report CreateReportFilter: " +ex.getMessage());
       	 ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            return mapping.findForward("error");
        }
        ActionForward af = new ActionForward();
        af.setName("ViewReport");
        af.setPath(strRedirect);
        af.setRedirect(true);
        return af;
    }

}
