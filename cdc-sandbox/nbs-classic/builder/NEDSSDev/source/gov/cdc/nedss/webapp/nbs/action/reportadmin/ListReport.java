package gov.cdc.nedss.webapp.nbs.action.reportadmin;

import gov.cdc.nedss.reportadmin.dao.DataSourceDAO;
import gov.cdc.nedss.reportadmin.dao.ReportDAO;
import gov.cdc.nedss.reportadmin.dt.DataSourceDT;
import gov.cdc.nedss.reportadmin.dt.ReportDT;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.NedssUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
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
 * Lists records in the Report table.
 * @author Ed Jenkins
 */
public class ListReport extends Action
{

    /**
     * Logger.
     */
    private static final Logger logger = Logger.getLogger(ListReport.class);

    /**
     * DAO for Report.
     */
    private static final ReportDAO daoReport = new ReportDAO();


    /**
     * Constructor.
     */
    public ListReport()
    {

    }

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        HttpSession session = request.getSession(true);
        NBSSecurityObj sec = (NBSSecurityObj)session.getAttribute("NBSSecurityObject");
        try
        {
            // Return Link
            String strLinkName = "Return to System Management Main Menu";
            String strLinkAddr = "/nbs/ReportAdministration.do?focus=systemAdmin5";
            request.setAttribute("LinkName", strLinkName);
            request.setAttribute("LinkAddr", strLinkAddr);
            ArrayList<Object> alReport = daoReport.list();

            String contextAction = request.getParameter("ContextAction");

            //sorting
            boolean invDirectionFlag = false;
            String sortMethod = request.getParameter("sortMethod");

            if (sortMethod == null || sortMethod.equals(""))
				sortMethod = "getReportTitle";

            String invSortDirection = request.getParameter("direction");

            session.setAttribute("sortMethod", sortMethod);
            session.setAttribute("direction", invSortDirection);

			if (invSortDirection != null && invSortDirection.equals("false"))
				invDirectionFlag = false;
			else
				invDirectionFlag = true;

			if (contextAction != null && contextAction.equals("Sort") && invDirectionFlag) {
				request.setAttribute("sortDirection", new Boolean(false));

			} else if (contextAction != null && contextAction.equals("Sort") && !invDirectionFlag)
					request.setAttribute("sortDirection", new Boolean(true));
			else
				request.setAttribute("sortDirection", new Boolean(invDirectionFlag));

			if (sortMethod != null && alReport != null && alReport.size() > 0) {
				NedssUtils util = new NedssUtils();
				//util.sortObjectByColumn(sortMethod,(Collection<Object>) alReport,invDirectionFlag);
				
		
				
				translateBeforeSorting(alReport);//Added for fixing sorting issue in Group column and Data Source Title
				if(sortMethod.equalsIgnoreCase("getDataSourceUID_s"))
					sortMethod="getDataSourceUID_s2";
				if(sortMethod.equalsIgnoreCase("getShared"))
					sortMethod="getShared2";
				
				util.sortObjectByColumnGeneric(sortMethod,(Collection<Object>) alReport,invDirectionFlag);
		
					
				
			}
            session.setAttribute("ReportAdmin.alReport", alReport);
            
            session.removeAttribute("ReportAdmin.ReportSectionCode");
            
            setSRTsInSession(session);
        }
        catch(Exception ex)
        {
       	 	logger.error("Error in Report ListReport: " +ex.getMessage());
       	 	ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            throw new ServletException(ex.getMessage(),ex);
        }

        return mapping.findForward("default");
    }

    private void translateBeforeSorting(ArrayList<Object> alReport){
    	
    	TreeMap<Object,Object> tmShared = new TreeMap<Object,Object>();
    	
        tmShared.put("P", "Private");
        tmShared.put("S", "Public");
        tmShared.put("T", "Template");
        tmShared.put("R", "Reporting Facility");
  
        
        DataSourceDAO daoDataSource = new DataSourceDAO();
        HashMap<String, String> map = new HashMap<String, String>();
        
        ArrayList<Object> alDataSource = daoDataSource.list();
        Iterator<Object>  iDataSource = alDataSource.iterator();
         while(iDataSource.hasNext())
         {
             DataSourceDT dtDataSource = (DataSourceDT)iDataSource.next();
             String k = dtDataSource.getDataSourceUID_s();
             String title = dtDataSource.getDataSourceTitle();
             map.put(k,  title);
         } 
         
        
		for(int i=0; i<alReport.size(); i++){
			ReportDT report = ((ReportDT)alReport.get(i));
			String shared = report.getShared();
			String dataSource = report.getDataSourceUID_s();
			report.setShared2((String)tmShared.get(shared));
			report.setDataSourceUID_s2(map.get(dataSource));
		}
		
		
    }
    
   
    
    private void setSRTsInSession(HttpSession session) throws Exception {

    	ReportAdminUtil util = new ReportAdminUtil();

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


    /**
     * Gets a list of SAS programs.
     * @return an SRT options string.
     */


}
