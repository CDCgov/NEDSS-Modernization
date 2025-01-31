package gov.cdc.nedss.webapp.nbs.action.report;

import gov.cdc.nedss.report.dt.ReportDT;
import gov.cdc.nedss.report.util.ReportConstantUtil;
import gov.cdc.nedss.reportadmin.dao.ReportDAO;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.HTMLEncoder;
import gov.cdc.nedss.util.JNDINames;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
 * @author Karthik Chinnayan
 */
public class ListReports extends Action
{

    /**
     * Logger.
     */
    private static final Logger logger = Logger.getLogger(ListReports.class);

    /**
     * DAO for Report.
     */
    private static final ReportDAO daoReport = new ReportDAO();


    /**
     * Constructor.
     */
    public ListReports()
    {

    }

    public ActionForward execute(ActionMapping mapping, ActionForm form, 
    		HttpServletRequest request, HttpServletResponse response) 
    		throws IOException, ServletException
    {
        try
        {
        	            // Return Link
            String strLinkName = "Return to System Management Main Menu";
            String strLinkAddr = "/nbs/ReportAdministration.do?focus=systemAdmin5";
            String pageTitle = "Reports";
            request.setAttribute("LinkName", strLinkName);
            request.setAttribute("LinkAddr", strLinkAddr);
            request.setAttribute("PageTitle", pageTitle);
            request.setAttribute("errorDataSource",request.getSession().getAttribute("errorDataSource"));
            request.getSession().removeAttribute("errorDataSource");
            removeSessionAttributes(request.getSession());
            makeReportList(request);

            request.getSession().setAttribute(ReportConstantUtil.MODE, "edit");
        }
        catch(Exception ex)
        {
            logger.error("Error in ListReports: " +ex.getMessage());
            ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            throw new ServletException(ex.getMessage(),ex);
        }

        return mapping.findForward("default");
    }

    /**
     * removeSesionAttributes - removes all other sessionAttributes excluding list attributes
     * @param HttpSession
     */
    private void removeSessionAttributes(HttpSession session) {
      session.removeAttribute(ReportConstantUtil.OLD_RESULT);
      session.removeAttribute(ReportConstantUtil.RESULT);
      session.removeAttribute(ReportConstantUtil.REPORT_UID);
      session.removeAttribute(ReportConstantUtil.DATASOURCE_UID);
      session.removeAttribute(ReportConstantUtil.DISEASE_CODE);
      session.removeAttribute(ReportConstantUtil.STATE_CODE);
      session.removeAttribute(ReportConstantUtil.REGION_CODE);
      session.removeAttribute(ReportConstantUtil.COUNTY_CODE);
      session.removeAttribute(ReportConstantUtil.TIME_PERIOD_CODE);
      session.removeAttribute(ReportConstantUtil.TIME_RANGE_CODE);
      session.removeAttribute(ReportConstantUtil.MONTH_YEAR_RANGE_CODE);
      session.removeAttribute(ReportConstantUtil.CRITERIA_LIST);
      session.removeAttribute(ReportConstantUtil.FILTERS);
      session.removeAttribute(ReportConstantUtil.FILTERABLE_COLUMNS);
      session.removeAttribute(ReportConstantUtil.AVAILABLE_COLUMNS);
      session.removeAttribute(ReportConstantUtil.AVAILABLE_COLUMN_LIST);
      session.removeAttribute(ReportConstantUtil.SELECTED_COLUMNS);
      session.removeAttribute(ReportConstantUtil.CURRENT_STATE);
      session.removeAttribute(ReportConstantUtil.SELECT_ALL_DISEASES);
      session.removeAttribute(ReportConstantUtil.SELECT_ALL_COUNTIES);
      session.removeAttribute(ReportConstantUtil.COUNTIES_SELECTED);
      session.removeAttribute(ReportConstantUtil.BASIC_FILTERS_ENTERED);
      session.removeAttribute(ReportConstantUtil.ADVANCE_FILTERS_ENTERED);   
      session.removeAttribute(ReportConstantUtil.BASIC_REQUIRED_ENTERED);
      session.removeAttribute(ReportConstantUtil.ANY_BASIC_REQUIRED);
      session.removeAttribute(ReportConstantUtil.IS_BASICDATA_VALID);   
      
      
      session.removeAttribute("dateType");
    } //removeSessionAttribues

    /**
    * This Method deletes pvt, pub, template and reportingFacility lists and 
    * creates new lists
    * @param HttSession
    */
    private void makeReportList(HttpServletRequest request) throws Exception 
    {
    	ArrayList<Object> reportList = null;
    	ArrayList<Object> myReportList = null;
    	ArrayList<Object> sharedReportList = null;
    	ArrayList<Object> templateReportList = null;
    	ArrayList<Object> reportingFacilityList = null;
    	HttpSession session = request.getSession(false);
        NBSSecurityObj nbsSecurityObj = (NBSSecurityObj)session.getAttribute("NBSSecurityObject");

	   	String sBeanJndiName = JNDINames.REPORT_CONTROLLER_EJB;
		String sMethod = "getReportList";
		Object[] oParams = new Object[] {};
		MainSessionCommand msCommand = null;
		MainSessionHolder mainSessionHolder = new MainSessionHolder();
		if (msCommand == null) {
		  msCommand = mainSessionHolder.getMainSessionCommand(request.getSession());
		}

		//All my Report List, private, shared, template and reportingFacility
		reportList = (ArrayList<Object> ) msCommand.processRequest(sBeanJndiName, 
				sMethod, oParams).get(0);
		myReportList = (ArrayList<Object> ) reportList.get(0);
		sharedReportList = (ArrayList<Object> ) reportList.get(1);
		templateReportList = (ArrayList<Object> ) reportList.get(2);
		reportingFacilityList = (ArrayList<Object> ) reportList.get(3);
		HashMap<Object,Object> categorizedReports = categorizeReportsIntoSections(myReportList,
				sharedReportList, templateReportList,reportingFacilityList, nbsSecurityObj);
		request.setAttribute("ReportsTree", categorizedReports);

		//Private Report List
	    if (myReportList != null && myReportList.size() > 0) {
	        String privateReportList = ReportListWebProcessor.getPrivateReportList(myReportList).toString();
	        session.setAttribute(ReportConstantUtil.PRIVATE_REPORT_LIST,privateReportList);
	      } else {
	        session.setAttribute(ReportConstantUtil.PRIVATE_REPORT_LIST, "");
	      }
	      //Public Report List
	      if (sharedReportList != null && sharedReportList.size() > 0) {
	        String publicReportList = ReportListWebProcessor.getPublicReportList(sharedReportList);
	        session.setAttribute(ReportConstantUtil.PUBLIC_REPORT_LIST,publicReportList);
	      } else {
	        session.setAttribute(ReportConstantUtil.PUBLIC_REPORT_LIST, "");
	      }
	      //Template Report List
	      if (templateReportList != null && templateReportList.size() > 0) {
	        String reportTemplateList = ReportListWebProcessor.getReportTemplateList(templateReportList);
	        session.setAttribute(ReportConstantUtil.TEMPLATE_REPORT_LIST, reportTemplateList);
	      } else {
	        session.setAttribute(ReportConstantUtil.TEMPLATE_REPORT_LIST, "");
	      }
	      
	      //Reporting Facility Report List
	      if (reportingFacilityList != null && reportingFacilityList.size() > 0) {
	        String reportFacilityList = ReportListWebProcessor.getReportingFacilityReportList(reportingFacilityList);
	        session.setAttribute(ReportConstantUtil.REPORTING_FACILITY_REPORT_LIST, reportFacilityList);
	      } else {
	        session.setAttribute(ReportConstantUtil.REPORTING_FACILITY_REPORT_LIST, "");
	      }	      
		
		
   } //makeReportList

    /**
     * Categorize the reports in 4 major groups: private, public, template and reportingFacility. Reports
     * within each group are further classified into sections that are created by the 
     * user. Finally return a map of all the reports categorized into groups and further
     * into sections.
     * @param myReportList
     * @param sharedReportList
     * @param templateReportList
     * @param reportingFacilityList
     * @return
     */
    public static HashMap<Object,Object> categorizeReportsIntoSections(ArrayList<Object>  myReportList, 
    		ArrayList<Object> sharedReportList, ArrayList<Object> templateReportList, ArrayList<Object> reportingFacilityList, NBSSecurityObj nbsSecurityObj )
    {
    	LinkedHashMap<Object,Object> categorizedReportsTree = new LinkedHashMap<Object,Object>();
    	TreeMap<Object,Object> myReportsTree = new TreeMap<Object,Object>();
    	TreeMap<Object,Object> publicReportsTree = new TreeMap<Object,Object>();
    	TreeMap<Object,Object> templateReportsTree = new TreeMap<Object,Object>();
    	TreeMap<Object,Object> reportingFacilityReportsTree = new TreeMap<Object,Object>();
    	
    	String privateSectionId = "Private";
    	String publicSectionId = "Public";
    	String templateSectionId = "Template";
    	String reportingFacilityId = "Reporting_Facility";
    	
    	boolean myPrivateRunPerm = nbsSecurityObj.getPermission(NBSBOLookup.REPORTING, NBSOperationLookup.SELECTFILTERCRITERIAPRIVATE);
    	boolean myPublicRunPerm = nbsSecurityObj.getPermission(NBSBOLookup.REPORTING, NBSOperationLookup.SELECTFILTERCRITERIAPUBLIC);
    	boolean publicReportRunPerm = nbsSecurityObj.getPermission(NBSBOLookup.REPORTING, NBSOperationLookup.SELECTFILTERCRITERIAPUBLIC);
    	boolean templateReportRunPerm = nbsSecurityObj.getPermission(NBSBOLookup.REPORTING, NBSOperationLookup.SELECTFILTERCRITERIATEMPLATE);
    	boolean myPrivateDeletePerm=nbsSecurityObj.getPermission(NBSBOLookup.REPORTING, NBSOperationLookup.DELETEREPORTPRIVATE);
    	boolean myPublicDeletePerm=nbsSecurityObj.getPermission(NBSBOLookup.REPORTING, NBSOperationLookup.DELETEREPORTPUBLIC);;
        boolean viewPrivatePerm = nbsSecurityObj.getPermission(NBSBOLookup.REPORTING, NBSOperationLookup.VIEWREPORTPRIVATE);
        boolean viewPublicPerm = nbsSecurityObj.getPermission(NBSBOLookup.REPORTING, NBSOperationLookup.VIEWREPORTPUBLIC);
        boolean viewTemplatePerm = nbsSecurityObj.getPermission(NBSBOLookup.REPORTING, NBSOperationLookup.VIEWREPORTTEMPLATE); 
    	boolean myReportingFacilityRunPerm = nbsSecurityObj.getPermission(NBSBOLookup.REPORTING, NBSOperationLookup.SELECTFILTERCRITERIAREPORTINGFACILITY);
    	boolean reportingFacilityRunPerm = nbsSecurityObj.getPermission(NBSBOLookup.REPORTING, NBSOperationLookup.SELECTFILTERCRITERIAREPORTINGFACILITY);
    	boolean myReportingFacilityDeletePerm=nbsSecurityObj.getPermission(NBSBOLookup.REPORTING, NBSOperationLookup.DELETEREPORTREPORTINGFACILITY);
        boolean viewReportingFacilityPerm = nbsSecurityObj.getPermission(NBSBOLookup.REPORTING, NBSOperationLookup.VIEWREPORTREPORTINGFACILITY);
    	if (myReportList != null && myReportList.size() > 0) 
    	{
 			Iterator<Object> reportsIter = myReportList.iterator();
 			while (reportsIter.hasNext()) {
 				ReportDT singleReport = (ReportDT) reportsIter.next();	
 				singleReport.setReportActionRun("<a href=\"javascript:runReport('"+HTMLEncoder.encodeJavaScript(singleReport.getShared())+"',"+HTMLEncoder.encodeJavaScript(singleReport.getReportUid().toString())+","+HTMLEncoder.encodeJavaScript(singleReport.getDataSourceUid().toString())+")\">  Run </a>");
 				singleReport.setReportActionDetail("<a href=\"javascript:void(0)\" title=\""+HTMLEncoder.encodeHtml(singleReport.getDescTxt())+"\">"+HTMLEncoder.encodeHtml(singleReport.getReportTitle())+" </a>");
 				singleReport.setReportActionDelete("<a href=\"javascript:deleteReport('"+HTMLEncoder.encodeJavaScript(singleReport.getShared())+"',"+HTMLEncoder.encodeJavaScript(singleReport.getReportUid().toString())+","+HTMLEncoder.encodeJavaScript(singleReport.getDataSourceUid().toString())+")\"> Delete </a>");
 				 
 				if(singleReport.getShared()!=null && singleReport.getShared().equalsIgnoreCase("P"))
 				{
 				 if(myPrivateRunPerm)
 					singleReport.setRunPermission(true);
 				 if(myPrivateDeletePerm)
 					singleReport.setDeletePermission(true);
 				}
 				else if(singleReport.getShared()!=null && singleReport.getShared().equalsIgnoreCase("S"))
 				{
 	 				 if(myPublicRunPerm)
 	 					singleReport.setRunPermission(true);
 	 				 if(myPublicDeletePerm)
 	 					singleReport.setDeletePermission(true);
 	 			}
 				

 				ArrayList<Object> list = null;
 				if (myReportsTree.containsKey(String.valueOf(singleReport.getSectionDescTxt()))) {
 					list = (ArrayList<Object> ) myReportsTree.get(String
 							.valueOf(singleReport.getSectionDescTxt()));
 					list.add(singleReport);
 				} else {
 					list = new ArrayList<Object> ();
 					list.add(singleReport);
 				}
 				myReportsTree.put(String.valueOf(singleReport.getSectionDescTxt()), list);
 			}
 		}

    	if (sharedReportList != null && sharedReportList.size() > 0) 
    	{
 			Iterator<Object> reportsIter = sharedReportList.iterator();

 			while (reportsIter.hasNext()) {
 				ReportDT singleReport = (ReportDT) reportsIter.next();
 				singleReport.setReportActionRun("<a href=\"javascript:runReport('"+HTMLEncoder.encodeJavaScript(singleReport.getShared())+"',"+HTMLEncoder.encodeJavaScript(singleReport.getReportUid().toString())+","+HTMLEncoder.encodeJavaScript(singleReport.getDataSourceUid().toString())+")\">  Run </a>");
 				singleReport.setReportActionDetail("<a href=\"javascript:void(0)\" title=\""+HTMLEncoder.encodeHtml(singleReport.getDescTxt())+"\">"+HTMLEncoder.encodeHtml(singleReport.getReportTitle())+" </a>");
 				 
 				if(publicReportRunPerm)
 					singleReport.setRunPermission(true);
 				ArrayList<Object> list = null;
 				
 				if (publicReportsTree.containsKey(String.valueOf(singleReport.getSectionDescTxt()))) {
 					list = (ArrayList<Object> ) publicReportsTree.get(String.valueOf(singleReport.getSectionDescTxt()));
 					list.add(singleReport);
 				} else {
 					list = new ArrayList<Object> ();
 					list.add(singleReport);
 				}
 				publicReportsTree.put(String.valueOf(singleReport
 						.getSectionDescTxt()), list);
 			}
 		}

       if (templateReportList != null && templateReportList.size() > 0) 
       {

 			Iterator<Object> reportsIter = templateReportList.iterator();

 			while (reportsIter.hasNext()) {
 				ReportDT singleReport = (ReportDT) reportsIter.next();
 				singleReport.setReportActionRun("<a href=\"javascript:runReport('"+HTMLEncoder.encodeJavaScript(singleReport.getShared())+"',"+HTMLEncoder.encodeJavaScript(singleReport.getReportUid().toString())+","+HTMLEncoder.encodeJavaScript(singleReport.getDataSourceUid().toString())+")\">  Run </a>");
 				singleReport.setReportActionDetail("<a href=\"javascript:void(0)\" title=\""+HTMLEncoder.encodeHtml(singleReport.getDescTxt())+"\">"+HTMLEncoder.encodeHtml(singleReport.getReportTitle())+" </a>");

                 if(templateReportRunPerm)
                	 singleReport.setRunPermission(true);
 				ArrayList<Object> list = null;
 				
 				if (templateReportsTree.containsKey(String.valueOf(singleReport.getSectionDescTxt()))) {
 					list = (ArrayList<Object> ) templateReportsTree.get(String
 							.valueOf(singleReport.getSectionDescTxt()));
 					list.add(singleReport);
 				} else {
 					list = new ArrayList<Object> ();
 					list.add(singleReport);
 				}
 				templateReportsTree.put(String.valueOf(singleReport.getSectionDescTxt()), list);
 			}
 		}
       

   	if (reportingFacilityList != null && reportingFacilityList.size() > 0) 
	{
			Iterator<Object> reportsIter = reportingFacilityList.iterator();

			while (reportsIter.hasNext()) {
				ReportDT singleReport = (ReportDT) reportsIter.next();
 				singleReport.setReportActionRun("<a href=\"javascript:runReport('"+HTMLEncoder.encodeJavaScript(singleReport.getShared())+"',"+HTMLEncoder.encodeJavaScript(singleReport.getReportUid().toString())+","+HTMLEncoder.encodeJavaScript(singleReport.getDataSourceUid().toString())+")\">  Run </a>");
 				singleReport.setReportActionDelete("<a href=\"javascript:deleteReport('"+HTMLEncoder.encodeJavaScript(singleReport.getShared())+"',"+HTMLEncoder.encodeJavaScript(singleReport.getReportUid().toString())+","+HTMLEncoder.encodeJavaScript(singleReport.getDataSourceUid().toString())+")\"> Delete </a>");
 				singleReport.setReportActionDetail("<a href=\"javascript:void(0)\" title=\""+HTMLEncoder.encodeHtml(singleReport.getDescTxt())+"\">"+HTMLEncoder.encodeHtml(singleReport.getReportTitle())+" </a>");
				if(reportingFacilityRunPerm)
					singleReport.setRunPermission(true);
				if(myReportingFacilityDeletePerm)
	 				singleReport.setDeletePermission(true);
				ArrayList<Object> list = null;
				
				if (reportingFacilityReportsTree.containsKey(String.valueOf(singleReport.getSectionDescTxt()))) {
					list = (ArrayList<Object> ) reportingFacilityReportsTree.get(String.valueOf(singleReport.getSectionDescTxt()));
					list.add(singleReport);
				} else {
					list = new ArrayList<Object> ();
					list.add(singleReport);
				}
				reportingFacilityReportsTree.put(String.valueOf(singleReport
						.getSectionDescTxt()), list);
			}
		}       
       	    
	    if(viewPrivatePerm)
	    	categorizedReportsTree.put(privateSectionId, myReportsTree);
	    if(viewPublicPerm)
	    	categorizedReportsTree.put(publicSectionId, publicReportsTree);
	    if(viewTemplatePerm)
	    	categorizedReportsTree.put(templateSectionId, templateReportsTree);
	    if(viewReportingFacilityPerm)
	    	categorizedReportsTree.put(reportingFacilityId, reportingFacilityReportsTree); 
	    
	   	return categorizedReportsTree;
   }
}
