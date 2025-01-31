package gov.cdc.nedss.webapp.nbs.action.observation.review;

import gov.cdc.nedss.webapp.nbs.queue.GenericForm;
import gov.cdc.nedss.webapp.nbs.queue.GenericQueueUtil;
import gov.cdc.nedss.webapp.nbs.queue.QueueColumnDT;
import gov.cdc.nedss.webapp.nbs.queue.QueueDT;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.InvestigationSummaryVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.LabReportSummaryVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.MorbReportSummaryVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.ObservationSummaryDisplayVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.ObservationSummaryVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.ReportSummaryInterface;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.ResultedTestSummaryVO;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dt.SummaryDT;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.HTMLEncoder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.util.StringUtils;
import gov.cdc.nedss.webapp.nbs.action.homepage.charts.ChartConstants;
import gov.cdc.nedss.webapp.nbs.action.observation.review.util.ObservationReviewQueueUtil;
import gov.cdc.nedss.webapp.nbs.action.util.DSQueueObject;
import gov.cdc.nedss.webapp.nbs.action.util.DecoratorUtil;
import gov.cdc.nedss.webapp.nbs.action.util.PaginationUtil;
import gov.cdc.nedss.webapp.nbs.action.util.QueueUtil;
import gov.cdc.nedss.webapp.nbs.form.observationreview.ObservationNeedingReviewForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;


public class ObservationsReviewLoad
    extends DispatchAction
{

    //For logging
    static final LogUtils logger = new LogUtils(ObservationsReviewLoad.class.getName());
    protected ObservationSummaryVO obsSumVO = null;
    ObservationReviewQueueUtil obsQueUtil = new ObservationReviewQueueUtil();
    GenericQueueUtil genericQueueUtil = new GenericQueueUtil();
	String className="gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.ObservationSummaryDisplayVO";
    QueueUtil queUtil = new QueueUtil();
    PropertyUtil propertyUtil= PropertyUtil.getInstance();

    /**
     * The initial method to load the queue 
     * @param mapping
     * @param aForm
     * @param request
     * @param response
     * @return
     * @throws IOException
     * @throws ServletException
     */

    @SuppressWarnings("unchecked")
	public ActionForward loadQueue(ActionMapping mapping, ActionForm aForm,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
                          throws IOException, ServletException
    {
    	NBSSecurityObj nbsSecurityObj;
    	Collection<Object>  docmentColl=null;
    	ArrayList<Object> observationsNeedingReview = new ArrayList<Object> ();
    	ObservationNeedingReviewForm obsRevForm = (ObservationNeedingReviewForm)aForm;
    	

    	
    	//GENERIC QUEUE
    	QueueDT queueDT = fillQueueDT();
    	obsRevForm.setQueueDT(queueDT);
    	ArrayList<QueueColumnDT> queueCollection = genericQueueUtil.convertQueueDTToList(queueDT);
    	obsRevForm.setQueueCollection(queueCollection);
    	String queueString = genericQueueUtil.convertToString(queueDT);
    	obsRevForm.setStringQueueCollection(queueString);
    	request.setAttribute("queueCollection",queueCollection);
    	request.setAttribute("stringQueueCollection",queueString);
    	//\GENERIC QUEUE
    	
    	
    	
    	try {
    		boolean forceEJBcall = false;
    		HttpSession session = request.getSession();
    		// Reset Pagination first time    
    		boolean initLoad = request.getParameter("initLoad") == null ? false: Boolean.valueOf(request.getParameter("initLoad")).booleanValue();
    		if (initLoad && !PaginationUtil._dtagAccessed(request)) {
    			obsRevForm.clearAll();
    			forceEJBcall = true;
    			// We are getting the size of lab and morb separately  
    			Integer queueSize = new Integer(propertyUtil.getLabCount()+ propertyUtil.getMorbCount());
    			obsRevForm.getAttributeMap().put("queueSize", queueSize);
    		}

    		request.getSession().removeAttribute("SupplementalInfo");
    		/**
    		 * get page context
    		 */
    		String contextAction = request.getParameter("ContextAction");
    		if (contextAction == null)
    			contextAction = (String)request.getAttribute("ContextAction");

    		if (contextAction != null){
    			
        		if(!contextAction.equals("markAsReviewedBulk")){
        			request.getSession().setAttribute("msgBlock", "");
        			request.getSession().setAttribute("errorBlock", "");
        			request.getSession().setAttribute("notProcessedDocUid", "");
        			
        		}        		
    			if(contextAction.equals("Review")) {
    				DSQueueObject dSQueueObject = new DSQueueObject();
    				dSQueueObject.setDSSortColumn("getDateReceived");
    				dSQueueObject.setDSSortDirection("true");
    				dSQueueObject.setDSFromIndex("0");
    				dSQueueObject.setDSQueueType(NEDSSConstants.NEW_LAB_REPORTS_FOR_REVIEW);
    				NBSContext.store(session, "DSQueueObject", dSQueueObject);

    			} else if(contextAction.equals("ReturnToObservationNeedingReview") || contextAction.equals("Delete")|| contextAction.equals("TransferOwnership")
    					|| contextAction.equals("markAsReviewedBulk")) {
    				forceEJBcall = true;
    			}	
    			TreeMap<Object,Object> tm = NBSContext.getPageContext(session, "PS056", contextAction);
    			NBSContext.lookInsideTreeMap(tm);
    			String sCurrTask = NBSContext.getCurrentTask(session);
    			logger.debug("sCurrTask: " + sCurrTask);
    			request.setAttribute("ViewMorbHref",
    					"/nbs/" + sCurrTask + ".do?ContextAction=" +
    							tm.get("ViewMorb"));
    			request.setAttribute("ViewLabHref",
    					"/nbs/" + sCurrTask + ".do?ContextAction=" +
    							tm.get("ViewLab"));
    			request.setAttribute("ViewFileHref",
    					"/nbs/" + sCurrTask + ".do?ContextAction=" +
    							tm.get("ViewFile"));
    			request.setAttribute("ViewDocHref",
    					"/nbs/" + sCurrTask + ".do?method=loadDocument&ContextAction=" +

    							tm.get("ViewDoc"));  			
    			request.setAttribute("ViewInvHref",
    					"/nbs/" + sCurrTask + ".do?ContextAction=" +
    							tm.get("ViewInv"));
    		}

    		if (contextAction == null)
    			contextAction = (String)request.getAttribute("ContextAction");
    		logger.debug("before call getPageContext with contextAction of: " + contextAction);
    		/* Check permissions */
    		nbsSecurityObj = (NBSSecurityObj)session.getAttribute("NBSSecurityObject");


    		
    		if (nbsSecurityObj == null) {
    			logger.fatal("Error: no nbsSecurityObj in the session, go back to login screen");
    			return mapping.findForward("login");
    		}
    		boolean viewObs = nbsSecurityObj.getPermission(NBSBOLookup.OBSERVATIONLABREPORT,NBSOperationLookup.VIEW);
    		if(viewObs!=true)
    			viewObs = nbsSecurityObj.getPermission(NBSBOLookup.OBSERVATIONMORBIDITYREPORT,NBSOperationLookup.VIEW);
    		request.setAttribute("viewObs",String.valueOf(viewObs));
    		if ((!nbsSecurityObj.getPermission(NBSBOLookup.OBSERVATIONLABREPORT,
    				NBSOperationLookup.VIEW)) && !(nbsSecurityObj.getPermission(NBSBOLookup.OBSERVATIONMORBIDITYREPORT,NBSOperationLookup.VIEW)) && !(nbsSecurityObj.getPermission(NBSBOLookup.DOCUMENT,NBSOperationLookup.VIEW)))
    		{
    			logger.fatal(
    					"Error: no permisstion to VIEW LabReport, go back to login screen");

    			throw new ServletException("Error: no permisstion to VIEW LabReport, go back to login screen");
    		}
    		try
    		{
    			
    			//GENERIC QUEUE
    			GenericForm genericForm= translateFromObservationSummaryDisplayVOToObservationSummaryDisplayVO(obsRevForm, request);
    			//\GENERIC QUEUE
    			
    			Collection<Object>  displayVOColl  = new ArrayList<Object> ();
    			
    			if(forceEJBcall)
    			{
    				MainSessionCommand msCommand = null;
    				MainSessionHolder mainSessionHolder = new MainSessionHolder();
    				msCommand = mainSessionHolder.getMainSessionCommand(session);
    				logger.info("You are trying to getObservationsNeedingReview");
    				String  sBeanJndiName = JNDINames.TASKLIST_PROXY_EJB;
    				String  sMethod = "getObservationForReview";
    				logger.debug(
    						"calling getObservationsNeedingReview on TaskListProxyEJB from observationsReviewLoad");
    				ArrayList<Object> arr = (ArrayList<Object> )msCommand.processRequest(sBeanJndiName,sMethod, null);
    				observationsNeedingReview = (ArrayList<Object> )arr.get(0);
    				displayVOColl= this.getDisplayVO(observationsNeedingReview,request);
    				//getting the documents from the Document table and adding to the observation collection
    				docmentColl = getDocumentCollection(session);
    				Collection<Object>  nDocColl = convertToDosplayVO(docmentColl, request);
    				displayVOColl.addAll(nDocColl);
    				
    				permissionsMarkAsReviewObservations(nbsSecurityObj, observationsNeedingReview, docmentColl, obsRevForm);
    				//updatePatLinks(displayVOColl, request);//Fatima: Deleted for not having repeated conditions
    				
    				
    				//updateProviderReportingFacility(displayVOColl, request);
    				
    				
    				
    				if(displayVOColl!=null){
    					
    					
    					NBSContext.store(session ,NBSConstantUtil.DSObservationList,displayVOColl);
    				}
    				NBSContext.store(session ,NBSConstantUtil.DSObservationList,displayVOColl);
    				NBSContext.store(session, "DSQueueObjectFull", displayVOColl);
    				genericForm.setElementColl(displayVOColl);
    				
    				//GENERIC QUEUE
    				obsRevForm.initializeDropDowns(displayVOColl, genericForm, obsRevForm.getCLASS_NAME());
    				Map<Object, Object> map = genericQueueUtil.setQueueCount(genericForm, genericForm.getQueueDT());
    				obsRevForm.setAttributeMap(map);
    				//\GENERIC QUEUE
    				}
    			else{
    				//displayVOColl = (ArrayList<Object> ) obsRevForm.getAttributeMap().get("DSObservationList");
       				displayVOColl = (ArrayList<Object> ) NBSContext.retrieve(session ,NBSConstantUtil.DSObservationList); 
       			     			}

    			logger.info(
    					"Number of observations needing for review in array is: " +
    							displayVOColl.size());
    			/**
    			 * Revisit  From 
    			 */   

    			int totalNoOfAllowedObservations = propertyUtil.getMorbCount() + propertyUtil.getLabCount();
    			String labReportsCount=null;
    			if(request.getParameter("labReportsCount")!=null){
    				labReportsCount = request.getParameter("labReportsCount");
    				obsRevForm.setLabCount(labReportsCount);
    			}
    			else{
    				labReportsCount =  obsRevForm.getLabCount(); 

    			}
    			int totalCountOfLabs  = Integer.parseInt(labReportsCount.toString());
    			String ERROR144 = null;
    			if(totalCountOfLabs>totalNoOfAllowedObservations){
    				ERROR144 = "ERR144";
    			}
    			request.setAttribute("ERR144", ERROR144);

    			/**
    			 * Revisit To 
    			 */     
    			// Make PatientName and Condition as Hyperlinks and put the list in the request for new lists
    			boolean existing = request.getParameter("existing") == null ? false : true;
    			if(contextAction != null && (contextAction.equalsIgnoreCase("ReturnToObservationNeedingReview")||contextAction.equals("Delete")
    					|| contextAction.equals("markAsReviewedBulk") || contextAction.equals("TransferOwnership"))) {
    				updatePatLinks(displayVOColl, request);
    				Collection<Object>  filteredColl = (ArrayList<Object> ) genericQueueUtil.filterQueue(genericForm, request, className);
    				if(filteredColl != null)
    					displayVOColl = (ArrayList<Object> ) filteredColl;
    				//GENERIC QUEUE
    				genericQueueUtil.sortQueue(genericForm, displayVOColl, true, request,"getDateReceived");//getDateReceived is the default method to sort by
    			} else {
    				genericQueueUtil.sortQueue(genericForm, displayVOColl, existing, request,"getDateReceived");//getDateReceived is the default method to sort by
    				//\GENERIC QUEUE
    				if(!existing) {
    					updatePatLinks(displayVOColl, request);
    				} else{
    					//GENERIC QUEUE
    					genericQueueUtil.filterQueue(genericForm, request, className);
    					//\GENERIC QUEUE
    				}
    				}
    			
    			
    			displayVOColl = (ArrayList<Object> )_handleFilterForCharts(obsRevForm, displayVOColl, request);

    			//To make sure SelectAll is checked, see if no criteria is applied
    			if(obsRevForm.getSearchCriteriaArrayMap().size() == 0)
    				request.setAttribute("ActionMode", HTMLEncoder.encodeHtml("InitLoad")); 			

    			request.setAttribute("queueCount", String.valueOf(displayVOColl.size()));
    			// Now that it has been sorted, set it to request.
    			request.setAttribute("observationList", displayVOColl);
    			request.setAttribute("PageTitle","Documents Requiring Review");
    			Integer queueSize = propertyUtil
    					.getQueueSize(NEDSSConstants.OBSERVATIONS_NEEDING_REVIEW_QUEUE_SIZE);
    			obsRevForm.getAttributeMap().put("maxRowCount", queueSize);
    			// adjust the index to previous page if last element of the page is
    			// deleted
    			String fromIndex = ((DSQueueObject) NBSContext.retrieve(session,
    					"DSQueueObject")).getDSFromIndex();
    			int index = new Integer(fromIndex).intValue();
    			if (index == displayVOColl.size()) {
    				index = index - queueSize.intValue();
    				fromIndex = String.valueOf(index);
    			}
    			request.setAttribute("DSFromIndex", fromIndex);  
    			//TransferOwnership Confirmation Msg related stuff
    			String confMsg = session.getAttribute("docTOwnershipConfMsg") == null ? null : (String) session.getAttribute("docTOwnershipConfMsg");
    			String clickHereLink = session.getAttribute("clickHereLink") == null ? null : (String) session.getAttribute("clickHereLink");
    			if(confMsg != null && confMsg.trim().length() > 0) {

    				request.setAttribute("DocTOwnershipMsg", confMsg);
    				session.removeAttribute("docTOwnershipConfMsg");
    			}
    			if(clickHereLink != null && clickHereLink.trim().length() > 0) {

    				request.setAttribute("clickHereLk", clickHereLink.trim());
    				session.removeAttribute("clickHereLink");
    			}
    			obsRevForm.setProcessingDecisionLogic(NEDSSConstants.STD_PROCESSING_DECISION_LIST_SYPHILIS_AND_NONSYPHILIS);
    			
				obsRevForm.setNonSTDProcessingDecisionLogic(NEDSSConstants.NBS_NO_ACTION_RSN);    		}
    		catch (Exception e)
    		{
    			e.printStackTrace();
    			logger.error(
    					"Error in ObservationReviewLoad in getting ObservationsNeedingReview from EJB");
    			throw new ServletException("Error in ObservationReviewLoad in getting ObservationsNeedingReview from EJB"+e.getMessage(),e);

    		}
    	}catch (Exception e) {
    		logger.error("Exception in ObservationsReviewLoad: " + e.getMessage());
    		e.printStackTrace();
    		throw new ServletException("General error occurred in Observations Review Load Submit : "+e.getMessage());
    	}
    	

    	logger.info("\n DRR Performance - Retruning Document Collection to front-end at " +LocalDateTime.now());
        return PaginationUtil.paginate(obsRevForm, request, "newreports_review",mapping);
    }
    
	
	
	

	
	
    public QueueDT fillQueueDT(){
    	
    	//ArrayList<QueueColumnDT> queueDTcollection = new ArrayList<QueueColumnDT>();
    	QueueDT queueDT = new QueueDT();

    	//First column: Document Type
    	QueueColumnDT queue = new QueueColumnDT();
    	queue.setColumnId("column1");
    	queue.setColumnName("Document Type");
    	//queue.setColumnPropertyName("typeLnk");
    	queue.setBackendId("OBS118");
    	queue.setDefaultOrder("ascending");
    	queue.setSortable("true");
    	queue.setSortNameMethod("getType");
    	queue.setMedia("html pdf csv");
    	queue.setMediaHtmlProperty("typeLnk");
    	queue.setMediaPdfProperty("typePrint");
    	queue.setMediaCsvProperty("typePrint");
    	
    	queue.setColumnStyle("width:12%");
    	queue.setFilterType("2");//0 date, 1 text, 2 multiselect
    	queue.setDropdownProperty("OBSERVATIONTYPE");
    	queue.setDropdownStyleId("obsType");
    	queue.setDropdownsValues("observationTypes");
    	queue.setErrorIdFiltering("??");//Same than BackendId?
    	queue.setConstantCount("OBSERVATION_TYPE_COUNT");
    	//queue.setSearchCriteriaConstant("OBSERVATIONTYPE");
    	queue.setMethodFromElement("getType");//TODO; change name??
    	queue.setMethodGeneralFromForm("getColumn1List");//Only for date/multiselect
    	queue.setFilterByConstant(NEDSSConstants.FILTERBYOBSERVATIONTYPE);
    	queueDT.setColumn1(queue);

    	//Second column: Date Received
    	queue = new QueueColumnDT();
    	queue.setColumnId("column2");
    	queue.setColumnName("Date Received");
    	//queue.setColumnPropertyName("dateReceivedS");
    	queue.setBackendId("INV147");
    	queue.setDefaultOrder("ascending");
    	queue.setSortable("true");
    	queue.setSortNameMethod("getDateReceived");
    	queue.setMedia("html pdf csv");
    	queue.setMediaHtmlProperty("dateReceivedS");
    	queue.setMediaPdfProperty("dateReceivedPrint");
    	queue.setMediaCsvProperty("dateReceivedPrint");
    	
    	queue.setColumnStyle("width:10%");
    	queue.setFilterType("0");//0 date, 1 text, 2 multiselect
    	queue.setDropdownProperty("STARTDATE");
    	queue.setDropdownStyleId("sdate");
    	queue.setDropdownsValues("startDateDropDowns");
    	queue.setErrorIdFiltering("??");//Same than BackendId?
    	queue.setConstantCount("DATE_FILTER_COUNT");
    	//queue.setSearchCriteriaConstant("STARTDATE");
    	queue.setMethodFromElement("getDateReceived");
    	queue.setMethodGeneralFromForm("getColumn5List");//Only for date/multiselect
    	queue.setFilterByConstant(NEDSSConstants.FILTERBYDATE);
    	queueDT.setColumn2(queue);


    	//Third column: Provider
    	queue = new QueueColumnDT();
    	queue.setColumnId("column3");
    	queue.setColumnName("Reporting Facility/Provider");
    	//queue.setColumnPropertyName("providerReportingFacility");
    	queue.setBackendId("PROVIDER");//??
    	queue.setDefaultOrder("ascending");
    	queue.setSortable("true");
    	queue.setSortNameMethod("getProviderReportingFacility");
    	queue.setMedia("html pdf csv");
    	queue.setMediaHtmlProperty("providerReportingFacility");
    	queue.setMediaPdfProperty("providerReportingFacilityPrint");
    	queue.setMediaCsvProperty("providerReportingFacilityPrint");
    	
    	queue.setColumnStyle("width:17%");
    	queue.setFilterType("1");//0 date, 1 text, 2 multiselect
    	queue.setDropdownProperty("Provider");
    	queue.setDropdownStyleId("provider");
    	queue.setDropdownsValues("noDataArray");
    	queue.setErrorIdFiltering("??");//Same than BackendId?
    	queue.setConstantCount("??");
    	//queue.setSearchCriteriaConstant("??");//Same than property??
    	queue.setMethodFromElement("getProviderReportingFacility");
    	queueDT.setColumn3(queue);

    	//Firth column: Full Name
    	queue = new QueueColumnDT();
    	queue.setColumnId("column4");
    	queue.setColumnName("Patient");
    	//queue.setColumnPropertyName("fullName");
    	queue.setBackendId("PATIENT");//??
    	queue.setDefaultOrder("ascending");
    	queue.setSortable("true");
    	queue.setSortNameMethod("getFullNameNoLnk");
    	queue.setMedia("html pdf csv");
    	queue.setMediaHtmlProperty("fullName");
    	queue.setMediaPdfProperty("fullNameNoLnk");
    	queue.setMediaCsvProperty("fullNameNoLnk");
    	queue.setColumnStyle("width:12%");
    	queue.setFilterType("1");//0 date, 1 text, 2 multiselect
    	queue.setDropdownProperty("SearchText1");
    	queue.setDropdownStyleId("patient");
    	queue.setDropdownsValues("noDataArray");
    	queue.setErrorIdFiltering("??");//Same than BackendId?
    	queue.setConstantCount("??");
    	//queue.setSearchCriteriaConstant("??");//Same than property??
    	queue.setMethodFromElement("getFullNameNoLnk");
    	queueDT.setColumn4(queue);

    	//Fifth column: description
    	queue = new QueueColumnDT();
    	queue.setColumnId("column5");
    	queue.setColumnName("Description");
    	//queue.setColumnPropertyName("description");
    	queue.setBackendId("DESCI");//??
    	queue.setDefaultOrder("ascending");
    	queue.setSortable("true");
    	queue.setSortNameMethod("getDescriptionPrint");
    	queue.setMedia("html pdf csv");
    	queue.setMediaHtmlProperty("description");
    	queue.setMediaPdfProperty("descriptionPrint");
    	queue.setMediaCsvProperty("descriptionPrint");
    	queue.setColumnStyle("width:25%");
    	queue.setFilterType("2");//0 date, 1 text, 2 multiselect
    	queue.setDropdownProperty("DESCRIPTION");
    	queue.setDropdownStyleId("descrip");
    	queue.setDropdownsValues("resultedDescription");
    	queue.setErrorIdFiltering("??");//Same than BackendId?
    	queue.setConstantCount("RESULTEDDES_COUNT");
    	//queue.setSearchCriteriaConstant("??");//Same than property??
    	queue.setMethodFromElement("getDescriptions");
    	queue.setMultipleValues("true");
    	queue.setMethodGeneralFromForm("getColumn4List");//Only for date/multiselect
    	queue.setFilterByConstant(NEDSSConstants.FILTERBYDESCRIPTION);
    	
    	queueDT.setColumn5(queue);

    	//Sixth column: Jurisdiction
    	queue = new QueueColumnDT();
    	queue.setColumnId("column6");
    	queue.setColumnName("Jurisdiction");
    	//queue.setColumnPropertyName("jurisdiction");
    	queue.setBackendId("INV107");
    	queue.setDefaultOrder("ascending");
    	queue.setSortable("true");
    	queue.setSortNameMethod("getJurisdiction");
    	queue.setMedia("html pdf csv");
    	queue.setMediaHtmlProperty("jurisdiction");
    	queue.setMediaPdfProperty("jurisdiction");
    	queue.setMediaCsvProperty("jurisdiction");
    	queue.setColumnStyle("width:12%");
    	queue.setFilterType("2");//0 date, 1 text, 2 multiselect
    	queue.setDropdownProperty("JURISDICTION");
    	queue.setDropdownStyleId("juris");
    	queue.setDropdownsValues("jurisdictions");
    	queue.setErrorIdFiltering("??");//Same than BackendId?
    	queue.setConstantCount("JURISDICTIONS_COUNT");
    	//queue.setSearchCriteriaConstant("??");//Same than property??
    	queue.setMethodFromElement("getJurisdiction");
    	queue.setMethodGeneralFromForm("getColumn2List");//Only for date/multiselect
    	queue.setFilterByConstant(NEDSSConstants.FILTERBYJURISDICTION);
    	queueDT.setColumn6(queue);
    	
    	//Seventh column: Associated With
    	queue = new QueueColumnDT();
    	queue.setColumnId("column7");
    	queue.setColumnName("Associated With");
    	//queue.setColumnPropertyName("testsString");
    	queue.setBackendId("INV169");//??
    	queue.setDefaultOrder("ascending");
    	queue.setSortable("true");
    	queue.setSortNameMethod("getTestsStringNoLnk");
    	queue.setMedia("html pdf csv");
    	queue.setMediaHtmlProperty("testsString");
    	queue.setMediaPdfProperty("testsStringPrint");
    	queue.setMediaCsvProperty("testsStringPrint");
    	queue.setColumnStyle("width:12%");
    	queue.setFilterType("2");//0 date, 1 text, 2 multiselect
    	queue.setDropdownProperty("CONDITION");
    	queue.setDropdownStyleId("testCond");
    	queue.setDropdownsValues("resultedTestandConditions");
    	queue.setErrorIdFiltering("??");//Same than BackendId?
    	queue.setConstantCount("RESULTEDTEST_COUNT");
    	//queue.setSearchCriteriaConstant("??");//Same than property??
    	queue.setMethodFromElement("getCondition");
    	queue.setMethodGeneralFromForm("getColumn3List");//Only for date/multiselect
    	queue.setMultipleValues("true");
    	queue.setFilterByConstant(NEDSSConstants.FILTERBYCONDITION);
    	queueDT.setColumn7(queue);

    	//Eigth column: Local id
    	queue = new QueueColumnDT();
    	queue.setColumnId("column8");
    	queue.setColumnName("Local ID");
    	//queue.setColumnPropertyName("observationId");
    	queue.setBackendId("LOCALID");//??
    	queue.setDefaultOrder("ascending");
    	queue.setSortable("true");
    	queue.setSortNameMethod("getObservationIdPrint");
    	queue.setMedia("html pdf csv");
    	queue.setMediaHtmlProperty("observationId");
    	queue.setMediaPdfProperty("observationIdPrint");
    	queue.setMediaCsvProperty("observationIdPrint");
    	queue.setColumnStyle("width:21%");
    	queue.setFilterType("1");//0 date, 1 text, 2 multiselect
    	queue.setDropdownProperty("SearchText2");
    	queue.setDropdownStyleId("localId");
    	queue.setDropdownsValues("noDataArray");
    	queue.setErrorIdFiltering("??");//Same than BackendId?
    	queue.setConstantCount("??");
    	//queue.setSearchCriteriaConstant("??");//Same than property??
    	queue.setMethodFromElement("getLocalId");
    	queueDT.setColumn8(queue);

    	//sixteen column: Program area
    	queue = new QueueColumnDT();
    	queue.setColumnId("columnHidden1");
    	queue.setColumnName("Program Area");
    	//queue.setColumnPropertyName("programArea");
    	queue.setBackendId("??");
    	queue.setDefaultOrder("ascending");
    	queue.setSortable("true");
    	queue.setSortNameMethod("getProgramAreaSTDHIV");
    	queue.setMedia("html");
    	queue.setMediaHtmlProperty("programAreaSTDHIV");

    	queue.setColumnStyle("width:21%");
    	queue.setClassName("hidden");
    	queue.setHeaderClass("hidden");
    	queue.setFilterType("1");
    	queue.setDropdownsValues("noDataArray");
    	queue.setMethodFromElement("getProgramArea");
    	queueDT.setColumnHidden1(queue);
    	
    	return queueDT;
    	
    }
    
    
    private void permissionsMarkAsReviewObservations(NBSSecurityObj nbsSecurityObj, ArrayList<Object> observationsNeedingReview, Collection<Object> docmentColl, ObservationNeedingReviewForm obsRevForm){
		
    	boolean permissionsLab = true, permissionsMorb=true, permissionsCase=true;
    	boolean permissions=false;
    	String permissionLab="";
    	String permissionMorb="";
    	String permissionCase="";
    	String uid="";
    	
    	ArrayList<Object> jurisdictions = CachedDropDowns.getJurisdictionList();
    	HashMap<String, String> jurisdictionDescCode = new HashMap<String, String>();
    	
    	for(int i=0; i< jurisdictions.size(); i++){
    		DropDownCodeDT dropdown = (DropDownCodeDT)jurisdictions.get(i);
    		jurisdictionDescCode.put(dropdown.getValue(), dropdown.getKey());
    	}
    	
    	
    	for(int i=0; i<observationsNeedingReview.size(); i++){
    		
    		if(observationsNeedingReview.get(i) instanceof LabReportSummaryVO){
    			LabReportSummaryVO labReportSummaryVO = ((LabReportSummaryVO)observationsNeedingReview.get(i));
    			String programArea = labReportSummaryVO.getProgAreaCd();
    		
    			String jurisdiction = labReportSummaryVO.getJurisdictionCd();
    			String sharedInd = labReportSummaryVO.getSharedInd();
    			uid=labReportSummaryVO.getObservationUid()+"";
    			
    			permissionsLab = nbsSecurityObj.getPermission(NBSBOLookup.OBSERVATIONLABREPORT,NBSOperationLookup.MARKREVIEWED,programArea,jurisdiction, sharedInd);

    			if(!permissionsLab){
        			permissionLab+=uid+"|";
        		}
    			else
    				permissions=true;
    			
    		}
    		if(observationsNeedingReview.get(i) instanceof MorbReportSummaryVO){
    			
    			MorbReportSummaryVO morbReportSummaryVO = ((MorbReportSummaryVO)observationsNeedingReview.get(i));
    			String programArea = morbReportSummaryVO.getProgAreaCd();
    		
    			String jurisdiction = morbReportSummaryVO.getJurisdictionCd();
    			String sharedInd = morbReportSummaryVO.getSharedInd();
    			uid=morbReportSummaryVO.getObservationUid()+"";
    			
    			permissionsMorb = nbsSecurityObj.getPermission(NBSBOLookup.OBSERVATIONMORBIDITYREPORT,NBSOperationLookup.MARKREVIEWED,programArea,jurisdiction, sharedInd);
    		
    			if(!permissionsMorb){
        			permissionMorb+=uid+"|";
        		}
    			else
    				permissions=true;
    			
    		}
    		
    		

    	}
    	if(docmentColl!=null){
    		Iterator<Object> docs = docmentColl.iterator();
	    	while(docs.hasNext()){
	    		SummaryDT doc = (SummaryDT)docs.next();
	    		
    			String programArea = doc.getProgAreaCd();
    			
    			String jurisdiction = doc.getJurisdictionCd();
    			String sharedInd = doc.getSharedInd();
    			
    			uid=doc.getNbsDocumentUid()+"";
    			permissionsCase = nbsSecurityObj.getPermission(NBSBOLookup.DOCUMENT,NBSOperationLookup.MARKREVIEWED,programArea,jurisdiction, sharedInd);
    		
    			if(!permissionsCase)
    				permissionCase+=uid+"|";
    			else
    				if(docmentColl.size()>0)
    					permissions=true;
	    	}
	    	
			
	    
    	}
		
		//boolean permissionLab = nbsSecurityObj.getPermission(NBSBOLookup.OBSERVATIONLABREPORT,NBSOperationLookup.TRANSFERPERMISSIONS);
		//boolean permissionMorb = nbsSecurityObj.getPermission(NBSBOLookup.OBSERVATIONMORBIDITYREPORT,NBSOperationLookup.TRANSFERPERMISSIONS);
		//boolean permissionCase = nbsSecurityObj.getPermission(NBSBOLookup.DOCUMENT,NBSOperationLookup.TRANSFERPERMISSIONS);
    	
		obsRevForm.getAttributeMap().put("permissionLab",permissionLab);
		obsRevForm.getAttributeMap().put("permissionMorb",permissionMorb);
		obsRevForm.getAttributeMap().put("permissionCase",permissionCase);
		obsRevForm.getAttributeMap().put("permissionMarkAsReviewed",permissions);
		
		
		
	
	}
    	
    /**
     * Get the maximum number(which is from the property file) of morb and lab from the common collection  
     * @param observationsNeedingReviewColl
     * @param request
     * @return
     */
    private Collection<Object>  getDisplayVO(Collection<Object>   observationsNeedingReviewColl, HttpServletRequest request)
    {
     Iterator<Object>  needingReviewIter = observationsNeedingReviewColl.iterator();
      Collection<Object>  displayVOColl =  new ArrayList<Object> ();
      ObservationReviewQueueUtil util = new ObservationReviewQueueUtil();
      
      
      int labCountFix = propertyUtil.getLabCount();
      int morbCountFix = propertyUtil.getMorbCount();
      int labCount = 0;  int morbCount = 0;
      while(needingReviewIter.hasNext()){
          ReportSummaryInterface report = (ReportSummaryInterface)needingReviewIter.next();
       if(report instanceof LabReportSummaryVO){
         if (labCount < labCountFix) {
           LabReportSummaryVO labReportSummaryVO = (LabReportSummaryVO) report;
           ObservationSummaryDisplayVO observationSummaryDisplayVO = new
        		   ObservationSummaryDisplayVO();
           
           util.setDateFormat(labReportSummaryVO, observationSummaryDisplayVO);
          
          observationSummaryDisplayVO.setType(labReportSummaryVO.getType());
          observationSummaryDisplayVO.setTypePrint(labReportSummaryVO.getType());
          
           if(labReportSummaryVO.getElectronicInd().equalsIgnoreCase("Y"))
        	   observationSummaryDisplayVO.setTypePrint(labReportSummaryVO.getType()+"\n(E)");
           observationSummaryDisplayVO.setStatus(labReportSummaryVO.getStatus());
           observationSummaryDisplayVO.setFirstName(labReportSummaryVO.
               getPatientFirstName());
           observationSummaryDisplayVO.setLastName(labReportSummaryVO.
                                                   getPatientLastName());
           observationSummaryDisplayVO.setPersonLocalId(labReportSummaryVO.getPersonLocalId());

           observationSummaryDisplayVO.setJurisdiction(labReportSummaryVO.getJurisdiction());
           observationSummaryDisplayVO.setProgramArea(labReportSummaryVO.getProgramArea());
           observationSummaryDisplayVO.setProgramAreaSTDHIV(labReportSummaryVO.getProgAreaCd());
          // observationSummaryDisplayVO.setTestsString(this.getResultedTest(
          //         labReportSummaryVO));
         //  observationSummaryDisplayVO.setTestsStringNoLnk(this.getResultedTest(
          //         labReportSummaryVO).replaceAll("<BR>",""));
           //observationSummaryDisplayVO.setTestsString(DecoratorUtil.getResultedTestsStringForWorup(labReportSummaryVO.getTheResultedTestSummaryVOCollection()));
           observationSummaryDisplayVO.setObservationId(labReportSummaryVO.getLocalIdForUpdatedAndNewDoc());
           observationSummaryDisplayVO.setLocalId(labReportSummaryVO.getLocalId());

			observationSummaryDisplayVO
			.setObservationIdPrint(labReportSummaryVO.getLocalIdForUpdatedAndNewDocPrint());
           observationSummaryDisplayVO.setObservationUID(labReportSummaryVO.
               getObservationUid());
           observationSummaryDisplayVO.setMPRUid(labReportSummaryVO.getMPRUid());
           
          // observationSummaryDisplayVO.setDescription(labReportSummaryVO.getDescription());
           observationSummaryDisplayVO.setProviderFirstName(labReportSummaryVO.getProviderFirstName());
           observationSummaryDisplayVO.setProviderLastName(labReportSummaryVO.getProviderLastName());
           observationSummaryDisplayVO.setProviderPrefix(labReportSummaryVO.getProviderPrefix());
           observationSummaryDisplayVO.setProviderSuffix(labReportSummaryVO.getProviderSuffix());
           observationSummaryDisplayVO.setProviderUid(labReportSummaryVO.getProviderUid());
           observationSummaryDisplayVO.setProviderDegree(labReportSummaryVO.getProviderDegree());
           observationSummaryDisplayVO.setReportingFacility(labReportSummaryVO.getReportingFacility());
           observationSummaryDisplayVO.setElectronicInd(labReportSummaryVO.getElectronicInd());
           observationSummaryDisplayVO.setInvSummaryVOs(labReportSummaryVO.getInvSummaryVOs());
           observationSummaryDisplayVO.setSharedInd(labReportSummaryVO.getSharedInd());
           
           if(labReportSummaryVO.isLabFromDoc()){
        	   observationSummaryDisplayVO.setDescription(labReportSummaryVO.getResultedTestString());
		 
           }else{
			 observationSummaryDisplayVO.setDescription(DecoratorUtil.getResultedTestsStringForWorup(labReportSummaryVO.getTheResultedTestSummaryVOCollection()));
			 observationSummaryDisplayVO.setDescriptions(DecoratorUtil.getResultedDescription(labReportSummaryVO.getTheResultedTestSummaryVOCollection()));
			
           }
         
          
           
           String descriptionPrint = util.getDescriptionPrint(observationSummaryDisplayVO.getDescription());
           observationSummaryDisplayVO.setDescriptionPrint(descriptionPrint);
           getAssociatedInvestigations(observationSummaryDisplayVO, observationSummaryDisplayVO.getObservationUID(), request);

           util.formatProvider(observationSummaryDisplayVO, labReportSummaryVO);
			
			
           if(labReportSummaryVO.getBirthTime()!=null)
        	   observationSummaryDisplayVO.setBirthTime(StringUtils.formatDate(labReportSummaryVO.getBirthTime()));
           if(labReportSummaryVO.getCurrSexCd()!=null)
        	   observationSummaryDisplayVO.setCurrSexCd(CachedDropDowns.getCodeDescTxtForCd(labReportSummaryVO.getCurrSexCd(), "SEX"));
           
           
           
           displayVOColl.add(observationSummaryDisplayVO);
          
         }
         labCount =labCount+1;
       }
        else if (report instanceof MorbReportSummaryVO)
        {
          if (morbCount < morbCountFix) {
            MorbReportSummaryVO morbReportSummaryVO = (MorbReportSummaryVO)
                report;
            ObservationSummaryDisplayVO observationSummaryDisplayVO = new
            		ObservationSummaryDisplayVO();
            util.setDateFormat(morbReportSummaryVO, observationSummaryDisplayVO);

            observationSummaryDisplayVO.setType(morbReportSummaryVO.getType());
            observationSummaryDisplayVO.setTypePrint(morbReportSummaryVO.getType());
            
            
            observationSummaryDisplayVO.setStatus(morbReportSummaryVO.
                                                  getReportTypeDescTxt());
            observationSummaryDisplayVO.setJurisdiction(morbReportSummaryVO.getJurisdiction());
            observationSummaryDisplayVO.setProgramArea(morbReportSummaryVO.getProgramArea());
            observationSummaryDisplayVO.setProgramAreaSTDHIV(morbReportSummaryVO.getProgAreaCd());
            observationSummaryDisplayVO.setFirstName(morbReportSummaryVO.
                getPatientFirstName());
            observationSummaryDisplayVO.setLastName(morbReportSummaryVO.
                                                    getPatientLastName());
            observationSummaryDisplayVO.setPersonLocalId(morbReportSummaryVO.getPersonLocalId());

            observationSummaryDisplayVO.setTestsString(morbReportSummaryVO.getConditionDescTxt());
            //observationSummaryDisplayVO.setTestsStringNoLnk(morbReportSummaryVO.getConditionDescTxt());
            observationSummaryDisplayVO.setObservationId(morbReportSummaryVO.
                    getLocalId());
            observationSummaryDisplayVO.setObservationIdPrint(morbReportSummaryVO.
                    getLocalId());
            observationSummaryDisplayVO.setLocalId(morbReportSummaryVO.getLocalId());
            observationSummaryDisplayVO.setObservationUID(morbReportSummaryVO.
                getObservationUid());
            observationSummaryDisplayVO.setMPRUid(morbReportSummaryVO.getMPRUid());
            
            // observationSummaryDisplayVO.setDescription(labReportSummaryVO.getDescription());
            observationSummaryDisplayVO.setProviderFirstName(morbReportSummaryVO.getProviderFirstName());
            observationSummaryDisplayVO.setProviderLastName(morbReportSummaryVO.getProviderLastName());
            observationSummaryDisplayVO.setProviderPrefix(morbReportSummaryVO.getProviderPrefix());
            observationSummaryDisplayVO.setProviderSuffix(morbReportSummaryVO.getProviderSuffix());
            observationSummaryDisplayVO.setProviderUid(morbReportSummaryVO.getProviderUid());
            observationSummaryDisplayVO.setProviderDegree(morbReportSummaryVO.getProviderDegree());
            observationSummaryDisplayVO.setReportingFacility(morbReportSummaryVO.getReportingFacility());
            observationSummaryDisplayVO.setSharedInd(morbReportSummaryVO.getSharedInd());
            //observationSummaryDisplayVO.setProviderReportingFacility(morbReportSummaryVO.getReportingFacility());
            
            String description="";
            
            description=morbReportSummaryVO.getConditionDescTxt() == null ? "" :
				"<b>"+morbReportSummaryVO.getConditionDescTxt()+"</b>";
            
            ArrayList<String> descriptions = new ArrayList<String>();
            descriptions.add(morbReportSummaryVO.getConditionDescTxt());
            //observationSummaryDisplayVO.setDescriptions(descriptions);
            
            if( morbReportSummaryVO.getTheLabReportSummaryVOColl()!=null){
	            Iterator<Object>  iterator = morbReportSummaryVO.getTheLabReportSummaryVOColl().iterator();
	            
	            while(iterator.hasNext()){
	            	LabReportSummaryVO lab = (LabReportSummaryVO)iterator.next();
	            	description+=DecoratorUtil.getResultedTestsStringForWorup(lab.getTheResultedTestSummaryVOCollection());
	          
	            	   descriptions.addAll(DecoratorUtil.getResultedDescription(lab.getTheResultedTestSummaryVOCollection()));
	             }
            }
           
            observationSummaryDisplayVO.setDescriptions(descriptions);
            observationSummaryDisplayVO.setDescription(description);
           
            String descriptionPrint = util.getDescriptionPrint(observationSummaryDisplayVO.getDescription());
            observationSummaryDisplayVO.setDescriptionPrint(descriptionPrint);
            
           
            if(morbReportSummaryVO.getBirthTime()!=null)
         	   observationSummaryDisplayVO.setBirthTime(StringUtils.formatDate(morbReportSummaryVO.getBirthTime()));
            if(morbReportSummaryVO.getCurrSexCd()!=null)
         	   observationSummaryDisplayVO.setCurrSexCd(CachedDropDowns.getCodeDescTxtForCd(morbReportSummaryVO.getCurrSexCd(), "SEX"));

            
            util.formatProvider(observationSummaryDisplayVO, morbReportSummaryVO);

			
            displayVOColl.add(observationSummaryDisplayVO);
            
          }
          morbCount = morbCount+1;
        }
        else
             System.out.println("Neither = " + report.getClass());
         }
           int totalCount = observationsNeedingReviewColl.size();
         request.setAttribute("Count", new Integer(totalCount).toString());
         return  displayVOColl;

        }
    
    	

        private String getResultedTest(LabReportSummaryVO labReportSummaryVO)
        {
          StringBuffer tests = new StringBuffer("");
          Collection<Object>  labTestColl = new ArrayList<Object> ();
          labTestColl = labReportSummaryVO.getTheResultedTestSummaryVOCollection();
          if(labTestColl != null)
          {
           Iterator<Object>  iter = labTestColl.iterator();
            while (iter.hasNext()) {
              ResultedTestSummaryVO resultedTestSummaryVO = (ResultedTestSummaryVO)
                  iter.next();
              tests.append(resultedTestSummaryVO.getResultedTest()+("<BR>"));
            }
          }
          return tests.toString();
        }
        private String getPatientFullName(ObservationSummaryDisplayVO observationSummaryDisplayVO) {
        	StringBuffer buff = new StringBuffer();
        	if (observationSummaryDisplayVO.getLastName() != null && observationSummaryDisplayVO.getLastName() != "")
        		buff.append(observationSummaryDisplayVO.getLastName());
        	if (observationSummaryDisplayVO.getLastName() != null
        			&& observationSummaryDisplayVO.getFirstName() != null
        			&& observationSummaryDisplayVO.getLastName() != ""
        			&& observationSummaryDisplayVO.getFirstName() != "")
        		buff.append(",   ");
        	if (observationSummaryDisplayVO.getFirstName() != null
        			&& observationSummaryDisplayVO.getFirstName() != "")
        		buff.append(observationSummaryDisplayVO.getFirstName());
        	String patientFullName = buff.toString();
        	return patientFullName;
        }
        
        private  Collection<Object>  updatePatLinks(Collection<Object>  displayVOColl,HttpServletRequest request) {
        	Collection<Object>  newObsColl = new ArrayList<Object> ();
        	ObservationReviewQueueUtil util = new ObservationReviewQueueUtil();
        	
        	try{
        	if (displayVOColl != null && displayVOColl.size() != 0) {
        	Iterator<Object>  iter = displayVOColl.iterator();
        	while(iter.hasNext()) {
        		ObservationSummaryDisplayVO observationSummaryDisplayVO = (ObservationSummaryDisplayVO) iter.next();
        		String patFullNm ="";
        		try{
        				patFullNm = getPatientFullName(observationSummaryDisplayVO);
        		   }catch(Exception e){
        			   e.printStackTrace();
        		   }
        			observationSummaryDisplayVO.setFullName(patFullNm);
        			observationSummaryDisplayVO.setFullNameNoLnk(patFullNm);
        			String viewFileHref = request.getAttribute("ViewFileHref") == null ? "" : (String) request.getAttribute("ViewFileHref");		
        			if(observationSummaryDisplayVO.getType() != null && observationSummaryDisplayVO.getType().trim().equals("Morbidity Report"))
        			{
        			String condDesc = observationSummaryDisplayVO.getTestsString();
        			observationSummaryDisplayVO.setTestsString(condDesc);	
        			//if(condDesc.indexOf("<a") == -1)
        			//observationSummaryDisplayVO.setTestsStringNoLnk(condDesc);
        			String viewHref = request.getAttribute("ViewMorbHref") == null ? "": (String) request.getAttribute("ViewMorbHref");
        			
        			//String result = getAssociatedInvestigationsLink(observationSummaryDisplayVO, observationSummaryDisplayVO.getObservationUID(), viewHref);
    				observationSummaryDisplayVO.setTestsString("");
    				observationSummaryDisplayVO.setTestsStringNoLnk(""); //ND-4620 	


    				
        			if(!viewHref.equals("")) {
        			//	String condLink = "<a href=\"#\" onclick=\"createLink(this,\'" + viewHref+ "&MPRUid="+ String.valueOf(observationSummaryDisplayVO.getMPRUid()) + "&observationUID="+String.valueOf(observationSummaryDisplayVO.getObservationUID())+"\'"+")"+"\" >"+ condDesc + "</a>";
        				//observationSummaryDisplayVO.setTestsString(condLink);
        				StringBuffer patLink = new StringBuffer("<a href=\"#\" onclick=\"createLink(this,\'" + viewFileHref+ "&MPRUid="+ String.valueOf(observationSummaryDisplayVO.getMPRUid()) + "&observationUID="+String.valueOf(observationSummaryDisplayVO.getObservationUID())+"\'"+")"+"\" >"+ patFullNm + "</a>");
            			observationSummaryDisplayVO.setFullName(patLink.toString());
        			}
        			
        			String type = observationSummaryDisplayVO.getType();
        			String viewMorbref = request.getAttribute("ViewMorbHref") == null ? "": (String) request.getAttribute("ViewMorbHref");
        			StringBuffer event = new StringBuffer("<a href=\"#\" onclick=\"createLink(this,\'" + viewMorbref+ "&MPRUid="+ String.valueOf(observationSummaryDisplayVO.getMPRUid()) + "&observationUID="+String.valueOf(observationSummaryDisplayVO.getObservationUID())+"\'"+")"+"\" >"+ type + "</a>");
    				observationSummaryDisplayVO.setTypeLnk(event.toString());
        			
        			
        			}else if(observationSummaryDisplayVO.getType() != null && observationSummaryDisplayVO.getType().trim().equals("Case Report"))
        			{
            			String condDesc = observationSummaryDisplayVO.getTestsString();
            			observationSummaryDisplayVO.setTestsString(condDesc);	
            			//if(condDesc != null && condDesc.indexOf("<a") == -1)
            			//observationSummaryDisplayVO.setTestsStringNoLnk(condDesc);
            			String viewHref = request.getAttribute("ViewDocHref") == null ? "": (String) request.getAttribute("ViewDocHref");
            			
            			//String result = getAssociatedInvestigationsLink(observationSummaryDisplayVO, observationSummaryDisplayVO.getObservationUID(), viewHref);
            			//String condLink = "<a href=\"#\" onclick=\"createLink(this,\'" + viewHref+ "&MPRUid="+ String.valueOf(observationSummaryDisplayVO.getMPRUid()) + "&observationUID="+String.valueOf(observationSummaryDisplayVO.getObservationUID())+"\'"+")"+"\" >"+ condDesc + "</a>";
        				observationSummaryDisplayVO.setTestsString("");
        				observationSummaryDisplayVO.setTestsStringNoLnk(""); //ND-4620 	


            			if(!viewHref.equals("")) {
            				
            				StringBuffer patLink = new StringBuffer("<a href=\"#\" onclick=\"createLink(this,\'" + viewFileHref+ "&MPRUid="+ String.valueOf(observationSummaryDisplayVO.getMPRUid()) + "&observationUID="+String.valueOf(observationSummaryDisplayVO.getObservationUID())+"\'"+")"+"\" >"+ patFullNm + "</a>");
            				observationSummaryDisplayVO.setFullName(patLink.toString());
            			}
            			observationSummaryDisplayVO.setTypeLnk(observationSummaryDisplayVO.getType());
            			
            			String type = observationSummaryDisplayVO.getType();
            			String viewDocref = request.getAttribute("ViewDocHref") == null ? "": (String) request.getAttribute("ViewDocHref");
            			StringBuffer event = new StringBuffer("<a href=\"#\" onclick=\"createLink(this,\'" + viewDocref+ "&MPRUid="+ String.valueOf(observationSummaryDisplayVO.getMPRUid()) + "&observationUID="+String.valueOf(observationSummaryDisplayVO.getObservationUID())+"\'"+")"+"\" >"+ type + "</a>");
        				observationSummaryDisplayVO.setTypeLnk(event.toString());
        				
        				
        			}else {
        				//String condDesc = observationSummaryDisplayVO.getTestsString();
        				String type = observationSummaryDisplayVO.getType();
            			//if(condDesc.indexOf("<a") == -1)
            			//observationSummaryDisplayVO.setTestsStringNoLnk(condDesc);

            			String viewLabref = request.getAttribute("ViewLabHref") == null ? "": (String) request.getAttribute("ViewLabHref");
            			//String viewLabDocHref = request.getAttribute("ViewLabDocHref") == null ? "": (String) request.getAttribute("ViewLabDocHref");
        				
            			
            			
            			
            	   		
            			
            			if(!viewFileHref.equals("")) {
            				
            				StringBuffer patLink = new StringBuffer("<a href=\"#\" onclick=\"createLink(this,\'" + viewFileHref+ "&MPRUid="+ String.valueOf(observationSummaryDisplayVO.getMPRUid()) + "&observationUID="+String.valueOf(observationSummaryDisplayVO.getObservationUID())+"\'"+")"+"\" >"+ patFullNm + "</a>");
            				observationSummaryDisplayVO.setFullName(patLink.toString());
            				StringBuffer event = new StringBuffer("<a href=\"#\" onclick=\"createLink(this,\'" + viewLabref+ "&MPRUid="+ String.valueOf(observationSummaryDisplayVO.getMPRUid()) + "&observationUID="+String.valueOf(observationSummaryDisplayVO.getObservationUID())+"\'"+")"+"\" >"+ type + "</a>");
            				observationSummaryDisplayVO.setTypeLnk(event.toString());
            				
            				
            				util.appendElectronicLabIcon(observationSummaryDisplayVO);
            				//Append Electronic Ind
            				
            				/*if(observationSummaryDisplayVO.getType().trim().equals("Lab Report")){
	            				if(observationSummaryDisplayVO.getElectronicInd()!=null && observationSummaryDisplayVO.getElectronicInd().equals("Y")){
	            					observationSummaryDisplayVO.setTypeLnk(event+"<br><img src=\"ind_electronic.gif\" title=\"Electronic Lab\">");
	            					//if(tabName.equals(NEDSSConstants.EVENT))
	            					//	dt.setDateReceived(startDate+"<br><img src=\"ind_electronic.gif\" title=\"Electronic Lab\">"+"<br>"+processingDecision);
	            				}else
	            					observationSummaryDisplayVO.setTypeLnk(event.toString());
            			
            				}*/
            				
            			
            			
            			}
        			}
        			
    				
    				util.setPatientFormat(observationSummaryDisplayVO);
    				
    				
    				
        			newObsColl.add(observationSummaryDisplayVO);	
        	}
        	 	}	
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        	return newObsColl;
        	}
        
        
        HashMap <String, String> mapClassCd = new HashMap<String, String>();

	public void getAssociatedInvestigations(ObservationSummaryDisplayVO observationSummaryDisplayVO,
			Long observationUid, HttpServletRequest request) {

		String result = "";

		if (mapClassCd.size() == 0) {
			mapClassCd.put("C", "Confirmed");
			mapClassCd.put("P", "Probable");
			mapClassCd.put("N", "Not A Case");
			mapClassCd.put("S", "Suspect");
		}

		ArrayList<String> conditions = new ArrayList<String>();
		Collection<Object> invSummaryVOs = observationSummaryDisplayVO.getInvSummaryVOs();
		String testsStringNoLnk = "";
		String testsStringPrint = "";
		String viewInvref = request.getAttribute("ViewInvHref") == null ? ""
				: (String) request.getAttribute("ViewInvHref");
		Iterator<Object> caseIterator2 = null;

		if (invSummaryVOs != null) {
			for (caseIterator2 = invSummaryVOs.iterator(); caseIterator2.hasNext();) {
				InvestigationSummaryVO invSummary = (InvestigationSummaryVO) caseIterator2.next();

				String localId = invSummary.getLocalId();
				String condition = invSummary.getConditionCodeText();
				String classCodeDesc = mapClassCd.get(invSummary.getCaseClassCd());
				String classCode = classCodeDesc == null ? "" : classCodeDesc;
				Long uid = invSummary.getPublicHealthCaseUid();

				conditions.add(condition);
				testsStringNoLnk += " " + condition;
				testsStringPrint += localId + " " + condition + " " + classCode;

				result += "<a href=\"#\" onclick=\"createLink(this,\'" + viewInvref + "&publicHealthCaseUID="
						+ String.valueOf(uid) + "\'" + ")" + "\" >" + localId + "</a><br><b>" + condition + "</b><br>"
						+ classCode + "<br>";
				observationSummaryDisplayVO.setTestsString(result);

			}
		}
		observationSummaryDisplayVO.setCondition(conditions);
		observationSummaryDisplayVO.setTestsStringNoLnk(testsStringNoLnk);// For sorting
		observationSummaryDisplayVO.setTestsStringPrint(testsStringPrint);
	}

        
        /**
         * Filter the observations based on the searchCriteria Selection
         * @param mapping
         * @param form
         * @param request
         * @param response
         * @return
         */
    	@SuppressWarnings("unchecked")
		public ActionForward filterObservationsSubmit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

    		ObservationNeedingReviewForm obsNeedRevForm = (ObservationNeedingReviewForm) form;
    		
    		//GENERIC QUEUE
    		//Translate from obsNeedRevForm (and observationSummaryDisplayVO) to genericSummaryDisplayVO
    		GenericForm genericForm= translateFromObservationSummaryDisplayVOToObservationSummaryDisplayVO(obsNeedRevForm, request);
    		
    		Collection<Object>  observationSummaryVOs = genericQueueUtil.filterQueue(genericForm, request, className);
    		//\GENERIC QUEUE
    		
    		if(observationSummaryVOs != null){
    			request.setAttribute("ActionMode", HTMLEncoder.encodeHtml("Search"));
    		//When all filters are applied (equivalent to no filter applied), Collection<Object>  is set to null in which case retrieve it from Form.
    		}else{
    			try {
    			observationSummaryVOs = (ArrayList<Object> ) NBSContext.retrieve(request.getSession(true) ,"DSQueueObjectFull"); 
    			}catch(Exception ex) {
    				logger.debug("DSQueueObjectFull is null in observation review load");
    				observationSummaryVOs = (ArrayList<Object> ) NBSContext.retrieve(request.getSession(true) ,NBSConstantUtil.DSObservationList);
    			}
    		}
    		NBSContext.store(request.getSession(true) ,NBSConstantUtil.DSObservationList,observationSummaryVOs);
    		
    		genericQueueUtil.sortQueue(genericForm, observationSummaryVOs, true, request, "getDateReceived");
    		
    		request.setAttribute("ActionMode", HTMLEncoder.encodeHtml("Search"));
    		request.setAttribute("observationList", observationSummaryVOs);
    		request.setAttribute("queueCount", String.valueOf(observationSummaryVOs.size()));			
    		request.setAttribute("PageTitle","Documents Requiring Review");
    		//On Filter, take user always to first page
    		obsNeedRevForm.getAttributeMap().put("PageNumber", "1");
    		int totalNoOfAllowedObservations = propertyUtil.getMorbCount() + propertyUtil.getLabCount();
            if(obsNeedRevForm.getLabCount()!=null){
           	int totalCountOfLabs  = Integer.parseInt(obsNeedRevForm.getLabCount().toString());
            	String ERROR144 = null;
            	if(totalCountOfLabs>totalNoOfAllowedObservations){
            		ERROR144 = "ERR144";
            	}
            	request.setAttribute("ERR144", ERROR144);
            }
            
            request.setAttribute("queueCollection",obsNeedRevForm.getQueueCollection());
            request.setAttribute("stringQueueCollection",obsNeedRevForm.getStringQueueCollection());
    		return PaginationUtil.paginate(obsNeedRevForm, request, "newreports_review",mapping);		
    		
    	}
        
    	

    	private Collection<Object>  getDocumentCollection(HttpSession session)throws Exception{
    		 Collection<Object>  documentColl = new ArrayList<Object> ();
    		try{
            MainSessionCommand msCommand = null;
            MainSessionHolder mainSessionHolder = new MainSessionHolder();
            msCommand = mainSessionHolder.getMainSessionCommand(session);
            logger.info("You are trying to get the documents which are UNPROCESSED");
            String  sBeanJndiName = JNDINames.TASKLIST_PROXY_EJB;
            String  sMethod = "getDocumentForReview";
            logger.debug("calling getDocumentForReview() in DocumentEJB from observationsReviewLoad");
            ArrayList<Object> arr = (ArrayList<Object> )msCommand.processRequest(sBeanJndiName,sMethod, null);
            documentColl  = (ArrayList<Object> )arr.get(0);
    		}catch (Exception e){
    			 logger.error(
                 "Error in ObservationReviewLoad in getting Documents needing Review from EJB");
         throw new ServletException("Error in ObservationReviewLoad in getting  Documents needing Review from EJB"+e.getMessage(),e);
    		}
            return documentColl;
    	}
    	
    	private Collection<Object>  convertToDosplayVO(Collection<Object>  docColl, HttpServletRequest request){
    		Collection<Object>  displayVOColl = new ArrayList<Object> ();
    		ObservationReviewQueueUtil util = new ObservationReviewQueueUtil();
    		if(docColl != null){
    			Iterator<Object>  iter = docColl.iterator();
    			while(iter.hasNext()){
    				SummaryDT summaryDt = (SummaryDT)iter.next();
    				ObservationSummaryDisplayVO observationSummaryDisplayVO = new
    						ObservationSummaryDisplayVO();

    				 util.setDateFormat(summaryDt, observationSummaryDisplayVO);
    		          
    				observationSummaryDisplayVO.setType(summaryDt.getDocType());
    				
    				observationSummaryDisplayVO.setTypePrint(summaryDt.getDocType());
    				observationSummaryDisplayVO.setStatus(summaryDt.getRecordStatusCd());
    				observationSummaryDisplayVO.setFirstName(summaryDt.getFirstName());
    				observationSummaryDisplayVO.setLastName(summaryDt.getLastName());
    				observationSummaryDisplayVO.setPersonLocalId(summaryDt.getPersonLocalId());
    				observationSummaryDisplayVO.setJurisdiction(summaryDt.getJurisdiction());
    				observationSummaryDisplayVO.setProgramArea(summaryDt.getProgramArea());
    				observationSummaryDisplayVO.setProgramAreaSTDHIV(summaryDt.getProgAreaCd());
    				observationSummaryDisplayVO.setTestsString(summaryDt.getCdDescTxt());
    				//observationSummaryDisplayVO.setTestsStringNoLnk(summaryDt.getCdDescTxt());
    				observationSummaryDisplayVO.setObservationId(summaryDt.getLocalIdForUpdatedAndNewDoc());
    				observationSummaryDisplayVO.setObservationIdPrint(summaryDt.getLocalIdForUpdatedAndNewDocPrint());
    				observationSummaryDisplayVO.setLocalId(summaryDt.getLocalId());
    				observationSummaryDisplayVO.setObservationUID(summaryDt.getNbsDocumentUid());
    				observationSummaryDisplayVO.setMPRUid(summaryDt.getMPRUid());
    				observationSummaryDisplayVO.setSharedInd(summaryDt.getSharedInd());
    				
    				
    				observationSummaryDisplayVO.setDescription(summaryDt.getDescription());//TODO: delete?
    				
    				
    				observationSummaryDisplayVO.setProviderFirstName(summaryDt.getProviderFirstName());
    				observationSummaryDisplayVO.setProviderLastName(summaryDt.getProviderLastName());
    				observationSummaryDisplayVO.setProviderSuffix(summaryDt.getProviderSuffix());
    				observationSummaryDisplayVO.setProviderPrefix(summaryDt.getProviderPrefix());
    				observationSummaryDisplayVO.setProviderSuffix(summaryDt.getProviderSuffix());
    				observationSummaryDisplayVO.setProviderUid(summaryDt.getProviderUid());
    				observationSummaryDisplayVO.setProviderDegree(summaryDt.getProviderDegree());
    				observationSummaryDisplayVO.setReportingFacility(summaryDt.getReportingFacility());
    				observationSummaryDisplayVO.setSendingFacilityNm(summaryDt.getSendingFacilityNm());
    				observationSummaryDisplayVO.setElectronicInd(summaryDt.getElectronicInd());
    				
    				ArrayList<String> descriptions = new ArrayList<String>();
    				descriptions.add(summaryDt.getCdDescTxt());
    				observationSummaryDisplayVO.setDescriptions(descriptions);
    				observationSummaryDisplayVO.setDescription(summaryDt.getCdDescTxt() == null ? "" :
    					"<b>"+summaryDt.getCdDescTxt()+"</b>");
    			
    				
    				String descriptionPrint = util.getDescriptionPrint(observationSummaryDisplayVO.getDescription());
    				observationSummaryDisplayVO.setDescriptionPrint(descriptionPrint);
    				//observationSummaryDisplayVO.setProviderReportingFacility(observationSummaryDisplayVO.getSendingFacilityNm() == null ? "" : "<b>Sending Facility</b><br>"+observationSummaryDisplayVO.getSendingFacilityNm());
    				//observationSummaryDisplayVO.setProviderReportingFacilityPrint(observationSummaryDisplayVO.getSendingFacilityNm() == null ? "" : " Sending Facility "+observationSummaryDisplayVO.getSendingFacilityNm());

    				//Associated investigations
    				
    				//observationSummaryDisplayVO.setInvSummaryVOs(summaryDt.getInvSummaryVOs());
    				//getAssociatedInvestigations(observationSummaryDisplayVO, observationSummaryDisplayVO.getObservationUID(), request);
    				
    				//Provider
    			    util.formatProvider(observationSummaryDisplayVO, summaryDt);
    			    
    			    if(summaryDt.getBirthTime()!=null)
			         	   observationSummaryDisplayVO.setBirthTime(StringUtils.formatDate(summaryDt.getBirthTime()));
			            if(summaryDt.getCurrSexCd()!=null)
			         	   observationSummaryDisplayVO.setCurrSexCd(CachedDropDowns.getCodeDescTxtForCd(summaryDt.getCurrSexCd(), "SEX"));
			           displayVOColl.add(observationSummaryDisplayVO);
			           
    			}
    		}
    		return displayVOColl;
    		
    	}
    	
    	/**
    	 * This method serves the purpose of filtering queue Information from the Context of Graphical Charts
    	 * @param paForm
    	 * @param request
    	 */
    	private Collection<Object>  _handleFilterForCharts(ObservationNeedingReviewForm obsForm, Collection<Object>  displayVOs, HttpServletRequest request) {
    		
    		GenericForm genericForm= translateFromObservationSummaryDisplayVOToObservationSummaryDisplayVO(obsForm, request);
    		
    		String chartId = request.getParameter("chartId") == null ? "" : request.getParameter("chartId");
    		if(chartId == "") return displayVOs;

    		if(chartId.equalsIgnoreCase(ChartConstants.C002)) {			
    			//Date
    			String [] dateList = new String[1]; 
    			dateList[0] = "7DAYS";		
    			obsForm.setAnswerArray("STARTDATE",dateList);
    		}
    		if(chartId.equalsIgnoreCase(ChartConstants.C004)) {			
    			if(obsForm.getStartDateDropDowns().size() > 0) {
        			obsForm.getSearchCriteriaArrayMap().put("STARTDATE", _makeAnswerList(obsForm.getStartDateDropDowns()));
        		}
    		}    		
    		
    		if(chartId.equalsIgnoreCase(ChartConstants.C002) || chartId.equalsIgnoreCase(ChartConstants.C004)) {
    			//Observation Type
    			String [] typeList = new String[1]; 
    			typeList[0] = "Lab Report";		
    			obsForm.setAnswerArray("OBSERVATIONTYPE",typeList);

    			if(obsForm.getJurisdictions().size() > 0) {
        			obsForm.getSearchCriteriaArrayMap().put("JURISDICTION", _makeAnswerList(obsForm.getJurisdictions()));
        		}
        		if(obsForm.getResultedTestandConditions().size() > 0) {
        			obsForm.getSearchCriteriaArrayMap().put("CONDITION", _makeAnswerList(obsForm.getResultedTestandConditions()));
        		 		
        		}    
        		if(obsForm.getResultedDescription().size() > 0) {
        			obsForm.getSearchCriteriaArrayMap().put("DESCRIPTION", _makeAnswerList(obsForm.getResultedDescription()));
        		}    
    		}	
    		
    		displayVOs = genericQueueUtil.filterQueue(genericForm, request, className);	    		
	
    		return displayVOs;		
    	}
    	
    	private String[] _makeAnswerList (ArrayList<Object> list) {
    		String[] returnSt = new String[list.size()];
    		for(int i=0; i<list.size(); i++) {
    			DropDownCodeDT cdDT = (DropDownCodeDT) list.get(i);
    			returnSt[i] = cdDT.getKey();
    		}
    		return returnSt;		
    	}
    	
    	@SuppressWarnings("unchecked")
		private GenericForm translateFromObservationSummaryDisplayVOToObservationSummaryDisplayVO(ObservationNeedingReviewForm obsNeedRevForm, HttpServletRequest request){
    		
    		GenericForm genericForm = new GenericForm();
    		
    		
    		genericForm.setSearchCriteriaArrayMap(obsNeedRevForm.getSearchCriteriaArrayMap());
    		genericForm.setAttributeMap(obsNeedRevForm.getAttributeMap());
    		genericForm.setQueueDT(obsNeedRevForm.getQueueDT());
    		//general methods from form
    		genericForm.setColumn1List(obsNeedRevForm.getObservationTypes());
    		genericForm.setColumn2List(obsNeedRevForm.getJurisdictions());
    		genericForm.setColumn3List(obsNeedRevForm.getResultedTestandConditions());
    		genericForm.setColumn4List(obsNeedRevForm.getResultedDescription());
    		genericForm.setColumn5List(obsNeedRevForm.getDateFilterList());
    		Collection<Object> observationCollection = null;
    		try {
     			observationCollection = (ArrayList<Object> ) NBSContext.retrieve(request.getSession(true) ,"DSQueueObjectFull"); 
     		//Collection<Object> genericCollection = new ArrayList<Object>();
     		
    		} catch (Exception ex) {
    			logger.info("DSQueueObjectFull is null for the first time");
    		}
    		if (observationCollection == null)

    			try {
    				observationCollection = (ArrayList<Object>) NBSContext.retrieve(request.getSession(true),
    						NBSConstantUtil.DSObservationList);

    			} catch (Exception ex) {
    				logger.info(NBSConstantUtil.DSObservationList + " is null for the first time");
    			}
    		genericForm.setElementColl(observationCollection);
    		return genericForm;
    	}
    	
    	
   }

