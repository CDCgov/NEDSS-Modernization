package gov.cdc.nedss.webapp.nbs.action.homepage.util;

import gov.cdc.nedss.proxy.ejb.tasklistproxyejb.vo.TaskListItemVO;
import gov.cdc.nedss.report.util.ReportConstantUtil;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.ejb.nbschartsejb.dt.ChartReportMetadataDT;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.webapp.nbs.action.report.ListReports;
import gov.cdc.nedss.webapp.nbs.action.report.ReportListWebProcessor;
import gov.cdc.nedss.webapp.nbs.action.report.ReportWebProcessor;
import gov.cdc.nedss.webapp.nbs.form.homepage.HomePageForm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class HomePageUtil {

	static final LogUtils logger = new LogUtils(HomePageUtil.class.getName());
	
    /**
     * Helper method to create a list of shortcut links to be displayed on the home page. These links are
     * created by taking the NBSSecurity object into account. 
     * @param request
     * @return
     * @throws Exception
     */
    public static Collection<Object>  getShortcutsByNBSSecurity(HttpServletRequest request)
    	throws Exception
    {
    	Collection<Object>  shortCuts = new ArrayList<Object> ();
    
        request.getSession().removeAttribute("SupplementalInfo");
        HttpSession session = request.getSession(false);
        Collection<Object>  shortCutsCustomPublic = new ArrayList<Object> ();
        Collection<Object>  shortCutsCustomPrivate = new ArrayList<Object> ();
        
        
        
        HashMap<String, ArrayList<String>> customQueuesSearchCriteria = new HashMap<String, ArrayList<String>>();//Position 0 = Storing the query, Position 1 = Storing the search criteria description, Position 2 = Storing the search criteria (code friendly)
    	
       
        // create shortcut links
        try {
            // get page context map
            TreeMap<Object,Object> tm = NBSContext.getPageContext(session,"PS072","GlobalHome");
            
            
            // pdg debug
            Set keys = tm.keySet();
            Iterator kit = keys.iterator();
            String debugKeys = "";
            while(kit.hasNext()) {
            	debugKeys += kit.next().toString()+"\t";            	
            }
            logger.debug("getShortcutsByNBSSecurity: debugKeys = " + debugKeys);
            // pdg debug end
            
            NBSContext.lookInsideTreeMap(tm);

            // get security object
        	NBSSecurityObj secObj = (NBSSecurityObj) session.getAttribute("NBSSecurityObject");

        	// get all the task list items to be displayed on the home page
            String sBeanJndiName = JNDINames.TASKLIST_PROXY_EJB;
            String sMethod = "getTaskListItems";
            MainSessionHolder mainSessionHolder = new MainSessionHolder();
            MainSessionCommand msCommand = mainSessionHolder.getMainSessionCommand(session);
        	ArrayList<Object> arr = (ArrayList<Object> ) msCommand.processRequest(sBeanJndiName, sMethod, null);
        	ArrayList<?> taskListItemVOs = (ArrayList<?> ) arr.get(0);
        	
        	// iterate through task list items and create shortcut links 
        	if (taskListItemVOs!=null) {
        		Iterator<?> taskListVOIter = taskListItemVOs.iterator();
        		while(taskListVOIter.hasNext())
        		{
        			TaskListItemVO taskListItemVO = (TaskListItemVO) taskListVOIter.next();
        			Integer count = taskListItemVO.getTaskListItemCount();
        			String taskListCount = count.toString();
        			String taskListName = taskListItemVO.getTaskListItemName();
        			
        			boolean custom = taskListItemVO.isCustom();
        			String sourceTable = taskListItemVO.getSourceTable();
        			String searchCriteria1 = taskListItemVO.getQueryStringPart1();
        			String searchCriteria2 = taskListItemVO.getQueryStringPart2();
        			String description = taskListItemVO.getDescription();
        			String listOfUsers = taskListItemVO.getListOfUsers();
        			String searchCriteriaDesc = taskListItemVO.getSearchCriteriaDesc();
        			String searchCriteriaCd = taskListItemVO.getSearchCriteriaCd();
        			
        			boolean publicQueue = false;
        			
        			if(listOfUsers!=null && listOfUsers.equalsIgnoreCase("ALL"))
        				publicQueue=true;
        			
        			if((custom && (sourceTable.equalsIgnoreCase(NEDSSConstants.INVESTIGATION_EVENT_ID) || sourceTable.equalsIgnoreCase(NEDSSConstants.LAB_REPORT_EVENT_ID)))){
        				
        		
    					ArrayList<String> criterias = new ArrayList<String>();
    					criterias.add(searchCriteria1+searchCriteria2);//query
    					criterias.add(searchCriteriaDesc);//search criteria description
    					criterias.add(searchCriteriaCd);//search criteria code
    					criterias.add(sourceTable); //Event Type
    					String sCurrTask = NBSContext.getCurrentTask(session);
    					customQueuesSearchCriteria.put(taskListName, criterias);
    					
    					session.setAttribute("customQueuesSearchCriteriaMap", customQueuesSearchCriteria);//This is to avoid sending the query in the request. The query associated to the task List Name is sent through the session as a HashMap
    					//TODO fatima: Investigation should be replaced by the corresponding:  tm.get("ObsAssign") 
    					String href = "/nbs/" + sCurrTask + ".do?ContextAction=" +"investigation"+ "&initLoad=true&queueName="+taskListName;
    					String link =  "<a title=\""+description+"\" href=\"" + href + "\">" + taskListName + "</a>";
    					if(publicQueue){
    						if (shortCutsCustomPublic.isEmpty())//first item
    							shortCutsCustomPublic.add("--Public__Custom__Queues--");
    						shortCutsCustomPublic.add(link);
    						
    					}
    					else{
    						if (shortCutsCustomPrivate.isEmpty())//first item
    							shortCutsCustomPrivate.add("--Private__Custom__Queues--");
    						shortCutsCustomPrivate.add(link);
    					}
    					
    					
    					
        			}
                    if (taskListName.equals(NEDSSConstants.OPEN_INVESTIGATIONS))
                    {
                        if (count.intValue() > 0)
                        {
                            String href="/nbs/LoadNavbar.do?ContextAction=GlobalInvestigations&initLoad=true";
                            String link = "<a href=\"" + href + "\">" + NEDSSConstants.OPEN_INVESTIGATIONS
                                    + " (" + count.intValue() + ")" + "</a>";
                            shortCuts.add(link);
                        }
                        else
                        {
                            String desc = NEDSSConstants.OPEN_INVESTIGATIONS + " (" + count.intValue() + ")";
                            shortCuts.add(desc);
                        }
                    }

        			// NND notifications
        			if (taskListName.equals(NEDSSConstants.NND_NOTIFICATIONS_FOR_APPROVAL)) {
        				if (count.intValue() > 0) {
        					String sCurrTask = NBSContext.getCurrentTask(session);
        					String href = "/nbs/" + sCurrTask + ".do?ContextAction=" + tm.get("NNDApproval") + "&initLoad=true";
        					String link = "<a href=\"" + href + "\">" + NEDSSConstants.NND_NOTIFICATIONS_FOR_APPROVAL + " (" + 
        						count.intValue() + ")" + "</a>";
        					shortCuts.add(link);
        				}
        				else {
        					String desc = NEDSSConstants.NND_NOTIFICATIONS_FOR_APPROVAL + " (" + count.intValue() + ")";
        					shortCuts.add(desc);
        				}
        			}
        			
        			// ELRs needing program or jurisdiction assignment (security permissions controlled)
        			if (taskListName.equals(NEDSSConstants.ELRS_NEEDING_PROGRAM_OR_JURISDICTION_ASSIGNMENT)) {
        				if (secObj.getPermission(NBSBOLookup.OBSERVATIONLABREPORT, NBSOperationLookup.ASSIGNSECURITY) ||
        						secObj.getPermission(NBSBOLookup.OBSERVATIONMORBIDITYREPORT, NBSOperationLookup.ASSIGNSECURITY )
        								||secObj.getPermission(NBSBOLookup.DOCUMENT, NBSOperationLookup.ASSIGNSECURITY))
    						 {
            				if (count.intValue() > 0) {
            					String sCurrTask = NBSContext.getCurrentTask(session);
            					String href = "/nbs/" + sCurrTask + ".do?ContextAction=" + tm.get("ObsAssign") + "&initLoad=true";
            					String link = "<a href=\"" + href + "\">" + "Documents Requiring Security Assignment" + " (" + 
            						count.intValue() + ")" + "</a>";
            					shortCuts.add(link);
            				}
            				else {
            					String desc = "Documents Requiring Security Assignment" + " (" + count.intValue() + ")";
            					shortCuts.add(desc);
            				}
        				}
        			}
        			
        			// Documents requiring review
        			if (taskListName.equals(NEDSSConstants.NEW_LAB_REPORTS_FOR_REVIEW)) {
        				if (secObj.getPermission(NBSBOLookup.OBSERVATIONLABREPORT, NBSOperationLookup.VIEW) ||
        						secObj.getPermission(NBSBOLookup.OBSERVATIONMORBIDITYREPORT, NBSOperationLookup.VIEW)
        								||secObj.getPermission(NBSBOLookup.DOCUMENT, NBSOperationLookup.VIEW))
    						 {
            				if (count.intValue() > 0) {
            					String sCurrTask = NBSContext.getCurrentTask(session);
            					String href = "/nbs/" + sCurrTask + ".do?ContextAction=" + tm.get("Review") + "&initLoad=true&labReportsCount=" + count;
            					String link = "<a href=\"" + href + "\">" + "Documents Requiring Review" + " (" + 
            						count.intValue() + ")" + "</a>";
            					shortCuts.add(link);
            				}
            				else {
            					String desc = "Documents Requiring Review" + " (" + count.intValue() + ")";
            					shortCuts.add(desc);
            				}
        				}
        			}
        			
        			// Rejected notifications
        			if (taskListName.equals(NEDSSConstants.NND_REJECTED_NOTIFICATIONS_FOR_APPROVAL)) {
        				if (secObj.getPermission(NBSBOLookup.NOTIFICATION, NBSOperationLookup.CREATENEEDSAPPROVAL) ||
        						secObj.getPermission(NBSBOLookup.NOTIFICATION, NBSOperationLookup.CREATE)
    						) {
            				if (count.intValue() > 0) {
            					String sCurrTask = NBSContext.getCurrentTask(session);
            					String href = "/nbs/" + sCurrTask + ".do?ContextAction=" + tm.get("NNDRejectedNotifications") + "&initLoad=true";
            					String link = "<a href=\"" + href + "\">" + NEDSSConstants.NND_REJECTED_NOTIFICATIONS_FOR_APPROVAL + " (" + 
            						count.intValue() + ")" + "</a>";
            					shortCuts.add(link);
            				}
            				else {
            					String desc = NEDSSConstants.NND_REJECTED_NOTIFICATIONS_FOR_APPROVAL + " (" + count.intValue() + ")";
            					shortCuts.add(desc);
            				}
        				}
        			}
        			
        			// Updated notifications
        			if (taskListName.equals(NEDSSConstants.NND_UPDATED_NOTIFICATIONS_FOR_APPROVAL)) {
        				if (secObj.getPermission(NBSBOLookup.NOTIFICATION, NBSOperationLookup.REVIEW)) {
            				if (count.intValue() > 0) {
            					String sCurrTask = NBSContext.getCurrentTask(session);
            					String href = "/nbs/" + sCurrTask + ".do?ContextAction=" + tm.get("NNDUpdatedNotificationsAudit") + "&initLoad=true";
            					String link = "<a href=\"" + href + "\">" + NEDSSConstants.NND_UPDATED_NOTIFICATIONS_FOR_APPROVAL + " (" + 
            						count.intValue() + ")" + "</a>";
            					shortCuts.add(link);
            				}
            				else {
            					String desc = NEDSSConstants.NND_UPDATED_NOTIFICATIONS_FOR_APPROVAL + " (" + count.intValue() + ")";
            					shortCuts.add(desc);
            				}
        				}
        			}
        			
        			// Manage Queues.
                    if( taskListName.equals(NEDSSConstants.MESSAGE_QUEUE))
                    {
                        String sCurrTask = NBSContext.getCurrentTask(session);
                        if (count.intValue() >  0)
                        {
                            String href = "/nbs/" + sCurrTask + ".do?ContextAction=" + tm.get("MessageQueue")
                                    + "&initLoad=true";
                            String link = "<a href=\"" + href + "\">" + NEDSSConstants.MESSAGE_QUEUE + " ("
                                    + count.intValue() + ")" + "</a>";
                            shortCuts.add(link);
                        }
                        else
                        {
                            String desc = NEDSSConstants.MESSAGE_QUEUE + " (" + count.intValue() + ")";
                            shortCuts.add(desc);
                        }
                    }
                    
                    if( NEDSSConstants.SUPERVISOR_REVIEW_QUEUE.equalsIgnoreCase(taskListName))
                    {
                        String sCurrTask = NBSContext.getCurrentTask(session);
                        if (count.intValue() >  0)
                        {
                            String href = "/nbs/" + sCurrTask + ".do?ContextAction=" + tm.get("SupervisorReviewQueue")
                                    + "&initLoad=true";
                            String link = "<a href=\"" + href + "\">" + NEDSSConstants.SUPERVISOR_REVIEW_QUEUE + " ("
                                    + count.intValue() + ")" + "</a>";
                            shortCuts.add(link);
                        }
                        else
                        {
                            String desc = NEDSSConstants.SUPERVISOR_REVIEW_QUEUE + " (" + count.intValue() + ")";
                            shortCuts.add(desc);
                        }
                    }
        		}
        		
        		shortCuts.addAll(shortCutsCustomPrivate);
        		shortCuts.addAll(shortCutsCustomPublic);
        		
        		
        	}
        } 
        catch(Exception e) 
        {
        	e.printStackTrace();
        	throw e;
        } 
    	return shortCuts;
    }
    
    /**
     * Retrieve a map of private reports grouped into different categories.
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
	public static Map<Object,Object> getMyReports(HttpServletRequest request)
    	throws Exception
    {
    	Map<Object, Object> categorizedReports = null;
    	try {
			ArrayList<?> reportList = null;
			ArrayList<Object> myReportList = null;
			HttpSession session = request.getSession(false);
			NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session.getAttribute("NBSSecurityObject");
			String sBeanJndiName = JNDINames.REPORT_CONTROLLER_EJB;
			String sMethod = "getReportList";
			Object[] oParams = new Object[] {};
			MainSessionCommand msCommand = null;
			MainSessionHolder mainSessionHolder = new MainSessionHolder();
			if (msCommand == null) {
				msCommand = mainSessionHolder.getMainSessionCommand(request.getSession());
			}

			// get a list of all reports
			reportList = (ArrayList<?> ) msCommand.processRequest(sBeanJndiName, sMethod, oParams).get(0);
			myReportList = (ArrayList<Object> ) reportList.get(0);
			String privateReportList = ReportListWebProcessor.getPrivateReportList(myReportList).toString();
			session.setAttribute(ReportConstantUtil.PRIVATE_REPORT_LIST,privateReportList);
			categorizedReports = ListReports.categorizeReportsIntoSections(myReportList, 
					null, null, null, nbsSecurityObj);
			ReportWebProcessor.removeSessionAttributes(session);
		} catch (Exception e) {
			e.printStackTrace();
        	throw e;
		} 
		
		// get private reports from map
		TreeMap<Object, Object> privateReportsBySubSection = (TreeMap<Object, Object>) categorizedReports.get("Private");
		return privateReportsBySubSection;
	}
    
	@SuppressWarnings("unchecked")
	public static void loadAvailableChartList(HomePageForm form, HttpServletRequest req) {
		if(form.getChartMetadataMap().size() == 0) {
			NBSSecurityObj secObj = (NBSSecurityObj)req.getSession().getAttribute("NBSSecurityObject");			
			Object[] searchParams = new Object[] {secObj};			
			Object[] oParams = new Object[] { JNDINames.NBS_CHART_METADATA_DAO_CLASS, "selectChartMetaData", searchParams};
			ArrayList list = (ArrayList)processRequest(oParams, req.getSession());
			ArrayList<Object> chartDDList = new ArrayList<Object> ();
			if (list != null && list.size() > 0) {
				Map<Object,Object> results  = (Map)list.get(0); 
				form.setChartMetadataMap(results);
				//Also create dropDowns from MetaData and put it in Form
				try {
					chartDDList = _chartDropDownList(form, results, req);
				} catch (Exception e) {
					logger.error("Error while creating Chart Dropdown List: " + e.getMessage());
					e.printStackTrace();
				}
			}
			form.setChartList(chartDDList);			
		}
	}	
	 

	/**
	 * _chartDropDownList populates HOMEPAGE Chart List based on SECURITY Privilages of the USER 
	 * @param results
	 * @param req
	 * @return java.util.ArrayList<Object>
	 * @throws Exception
	 */
	private static ArrayList<Object> _chartDropDownList(HomePageForm form, Map<Object,Object> results, HttpServletRequest req) throws Exception {

		NBSSecurityObj secObj = (NBSSecurityObj) req.getSession().getAttribute("NBSSecurityObject");
		boolean permission = false;
		Iterator<?> iter = results.values().iterator();
		ArrayList<Object> retList = new ArrayList<Object>();
		boolean flagDefault = true;
		try {
			while(iter.hasNext()) {
				ChartReportMetadataDT cmDT = (ChartReportMetadataDT) iter.next();
				//First see if SECURED_BY_OBJECT_OPERATION is 'Y' then only check for permission with OBJECT_NM - OPERATION_NM combination
				if(cmDT.getSecured_by_object_operation() != null && cmDT.getSecured_by_object_operation().equalsIgnoreCase(NEDSSConstants.YES)) {
					String objNm = cmDT.getObject_nm() == null ? "" : cmDT.getObject_nm().toUpperCase();
					String operNm = cmDT.getOperation_nm() == null ? "" : cmDT.getOperation_nm().toUpperCase();
					//see if Permission is TRUE, add it to list
					permission = secObj.getPermission(objNm , operNm);
					if(permission) {
						retList.add(_newDropDownDT(cmDT));					
					}
					permission = false;
				} else {
					retList.add(_newDropDownDT(cmDT));				
				}		
				/*
			//If atleast 1 Chart is present (First One), load the metadata for Servlet
			if(retList.size() == 1) {
				DropDownCodeDT dt = (DropDownCodeDT) retList.get(0);
				form.getAttributeMap().put("charts", dt.getKey());
				form.getAttributeMap().put("chartTitle", dt.getValue());
			}
				 */
				String defaultIndCd = cmDT.getDefaultIndCd();
				if(defaultIndCd != null && defaultIndCd.equalsIgnoreCase("Y")){
					String chartCode = cmDT.getChart_report_cd();
					String chartTitle = cmDT.getChart_report_short_desc_txt();	

					form.getAttributeMap().put("charts", chartCode);
					form.getAttributeMap().put("chartTitle", chartTitle);
					form.getAttributeMap().put("currentChart",chartCode);
					req.setAttribute("ChartId", chartCode);
					flagDefault = false;
				}

			}		
			/*
			 * if(flagDefault){ String chartCode = "C001"; String chartTitle =
			 * "Cases created - Last 7 Days";
			 * 
			 * form.getAttributeMap().put("charts", chartCode);
			 * form.getAttributeMap().put("chartTitle", chartTitle);
			 * form.getAttributeMap().put("currentChart",chartCode);
			 * req.setAttribute("ChartId", chartCode); flagDefault = false; }
			 */
		} catch (Exception ex) {
			logger.error("Exception in _chartDropDownList: "+ex.getMessage());
			ex.printStackTrace();
		}	
		return retList;
	}
	
	private static DropDownCodeDT _newDropDownDT(ChartReportMetadataDT cmDT) {
		
		DropDownCodeDT dt = new DropDownCodeDT();
		dt.setKey(cmDT.getChart_report_cd());			
		dt.setValue(cmDT.getChart_report_short_desc_txt());
		return dt;
	}
	
	private static Object processRequest(Object[] oParams, HttpSession session) {

		MainSessionCommand msCommand = null;
		Object obj = null;

		try {
			String sBeanJndiName = JNDINames.NBS_CHART_EJB;
			String sMethod = "processNBSChartRequest";
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(session);
			ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
			obj = arr.get(0);

			} catch (Exception ex) {
				logger.error("Exception calling processNBSChartRequest " + ex.getMessage());
				ex.printStackTrace();
			}
			return obj;	
		}		
}