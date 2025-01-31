package gov.cdc.nedss.webapp.nbs.action.reportadmin;

import gov.cdc.nedss.reportadmin.dao.ReportFilterDAO;
import gov.cdc.nedss.reportadmin.dt.ReportFilterDT;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.PropertyUtil;

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
 * Prepares a blank record for the Report_Filter table.
 * @author Ed Jenkins
 */
public class NewReportFilter extends Action
{

    /**
     * Logger.
     */
    private static final Logger logger = Logger.getLogger(NewReportFilter.class);

    //Properties Util
    
    PropertyUtil pu = PropertyUtil.getInstance();
    /**
     * String Buffer Size.
     */
    private static final int STRING_BUFFER_SIZE = 1024;

    /**
     * DAO for Report_Filter.
     */
    private static final ReportFilterDAO daoReportFilter = new ReportFilterDAO();

    /**
     * Constructor.
     */
    public NewReportFilter()
    {
    }

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        HttpSession session = request.getSession(true);
        NBSSecurityObj sec = (NBSSecurityObj)session.getAttribute("NBSSecurityObject");
        try
        {
            // Return Link
            String strLinkName = "Return to Reports";
            String strLinkAddr = "/nbs/ListReport.do";
            request.setAttribute("LinkName", strLinkName);
            request.setAttribute("LinkAddr", strLinkAddr);

            String strContext = request.getParameter("context");
            request.setAttribute("context", strContext);

            String data_source_uid_s = request.getParameter("data_source_uid") == null ? "" : request.getParameter("data_source_uid").trim();
            long data_source_uid = 0L;
            if(data_source_uid_s.trim().length() > 0)
            {
                data_source_uid = Long.parseLong(data_source_uid_s);
            }
            String report_uid_s = request.getParameter("report_uid");
            long report_uid = 0L;
            if(report_uid_s != null)
            {
                report_uid = Long.parseLong(report_uid_s);
            }
            // ReportAdmin.AvailableFilterCodeSRT
            TreeMap<Object,Object> tmAvailableFilterCode = daoReportFilter.getAvailableFilterMap(data_source_uid, report_uid);
            StringBuffer sbAvailableFilterCode = new StringBuffer(STRING_BUFFER_SIZE);
            Set<Object> setAvailableFilterCode = tmAvailableFilterCode.keySet();
           Iterator<Object>  iAvailableFilterCode = setAvailableFilterCode.iterator();
            while(iAvailableFilterCode.hasNext())
            {
                String k = (String)iAvailableFilterCode.next();
                Long L = (Long)tmAvailableFilterCode.get(k);
                //Temporary fix to remove the new custom filters from list
//                if(L!=null && L.longValue() > (new Long(pu.getMaxReportFilterUid())).longValue())
//                	continue;
                String v = L.toString();
                sbAvailableFilterCode.append(v);
                sbAvailableFilterCode.append("$");
                sbAvailableFilterCode.append(k);
                sbAvailableFilterCode.append("|");
            }
            String strAvailableFilterCode = sbAvailableFilterCode.toString();
            session.setAttribute("ReportAdmin.AvailableFilterCodeSRT", strAvailableFilterCode);
            int y = tmAvailableFilterCode.size();
            if(y == 0)
            {
                request.setAttribute("error", "This report is already using all available filters.  No more can be added.");
                return mapping.findForward("error");
            }
            // ReportAdmin.dtReportFilter
            ReportFilterDT dtReportFilter = new ReportFilterDT();
            dtReportFilter.setReportFilterUID(0L);
            dtReportFilter.setDataSourceUID(data_source_uid);
            dtReportFilter.setReportUID(report_uid);
            dtReportFilter.setMaxValueCount(1);
            dtReportFilter.setMinValueCount(1);
            session.setAttribute("ReportAdmin.dtReportFilter", dtReportFilter);
        }
        catch(Exception ex)
        {
       	 	logger.error("Error in Report NewReportFilter: " +ex.getMessage());
       	 	ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            return mapping.findForward("error");
        }
        return mapping.findForward("default");
    }

}
