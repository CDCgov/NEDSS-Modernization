package gov.cdc.nedss.webapp.nbs.action.observation.review;

import gov.cdc.nedss.util.*;
import gov.cdc.nedss.webapp.nbs.action.observation.review.util.ObservationReviewQueueUtil;
import gov.cdc.nedss.webapp.nbs.action.util.DSQueueObject;
import gov.cdc.nedss.webapp.nbs.action.util.DecoratorUtil;
import gov.cdc.nedss.webapp.nbs.action.util.PaginationUtil;
import gov.cdc.nedss.webapp.nbs.action.util.QueueUtil;
import gov.cdc.nedss.webapp.nbs.form.observationsecurityassgn.ObservationNeedingSecurityReviewForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.webapp.nbs.queue.GenericForm;
import gov.cdc.nedss.webapp.nbs.queue.GenericQueueUtil;
import gov.cdc.nedss.webapp.nbs.queue.QueueColumnDT;
import gov.cdc.nedss.webapp.nbs.queue.QueueDT;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dt.SummaryDT;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.systemservice.nbscontext.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.*;
import java.io.*;
import java.lang.reflect.Method;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;
import org.apache.struts.actions.DispatchAction;

public class ObservationsAssignmentLoad extends DispatchAction {

	//For logging
	static final LogUtils logger = new LogUtils(
			ObservationsAssignmentLoad.class.getName());

	protected ObservationSummaryVO obsSumVO = null;
    ObservationReviewQueueUtil obsQueUtil = new ObservationReviewQueueUtil();
    QueueUtil queUtil = new QueueUtil();
    PropertyUtil propertyUtil= PropertyUtil.getInstance();
    GenericQueueUtil genericQueueUtil = new GenericQueueUtil();
	String className="gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.ObservationSummaryDisplayVO";
	
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
    	
    	
    	ObservationNeedingSecurityReviewForm obsRevForm = (ObservationNeedingSecurityReviewForm)aForm;
    	
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

    		//To make sure SelectAll is checked, see if no criteria is applied
    		if(obsRevForm.getSearchCriteriaArrayMap().size() == 0)
    			request.setAttribute("ActionMode", HTMLEncoder.encodeHtml("InitLoad"));

    		request.getSession().removeAttribute("SupplementalInfo");
    		/**
    		 * get page context
    		 */
    		String contextAction = request.getParameter("ContextAction");
    		if (contextAction == null)
    			contextAction = (String)request.getAttribute("ContextAction");

    		
    		
    		if (contextAction != null){
    			
    			if(!contextAction.equals("transferBulk"))
        			request.getSession().setAttribute("msgBlock", "");
    			
    			if(contextAction.equals("ObsAssign")) {
    				DSQueueObject dSQueueObject = new DSQueueObject();
    				dSQueueObject.setDSSortColumn("getDateReceived");
    				dSQueueObject.setDSSortDirection("true");
    				dSQueueObject.setDSFromIndex("0");
    				dSQueueObject.setDSQueueType(NEDSSConstants.OBSERVATIONS_ASSIGNMENT);
    				NBSContext.store(session, "DSQueueObject", dSQueueObject);

    		
    			} else if(contextAction.equals("ReturnToObservationsNeedingAssignment") || contextAction.equals("Delete")|| contextAction.equals("TransferOwnership")
    					|| contextAction.equals("transferBulk")) {
    				forceEJBcall = true;
    			}	
    			TreeMap<Object,Object> tm = NBSContext.getPageContext(session, "PS055", contextAction);
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

    			
    		}
    		
    		if (contextAction == null)
    			contextAction = (String)request.getAttribute("ContextAction");
    		logger.debug("before call getPageContext with contextAction of: " + contextAction);
    		/* Check permissions */
    		NBSSecurityObj nbsSecurityObj = (NBSSecurityObj)session.getAttribute("NBSSecurityObject");

    		boolean permissionLab = nbsSecurityObj.getPermission(NBSBOLookup.OBSERVATIONLABREPORT,NBSOperationLookup.TRANSFERPERMISSIONS);
    		boolean permissionMorb = nbsSecurityObj.getPermission(NBSBOLookup.OBSERVATIONMORBIDITYREPORT,NBSOperationLookup.TRANSFERPERMISSIONS);
    		boolean permissionCase = nbsSecurityObj.getPermission(NBSBOLookup.DOCUMENT,NBSOperationLookup.TRANSFERPERMISSIONS);
    		
    		obsRevForm.getAttributeMap().put("permissionLab",permissionLab);
    		obsRevForm.getAttributeMap().put("permissionMorb",permissionMorb);
    		obsRevForm.getAttributeMap().put("permissionCase",permissionCase);
    		obsRevForm.getAttributeMap().put("permissionTransfer",permissionLab||permissionMorb||permissionCase);
    		
    		
    		//Send information to a hidden element
    		//From javascript, read information: hide/show mark as review and checkboxes
    		//From javascript, enable/disable checkboxes
    		//boolean permissionCase = nbsSecurityObj.getPermission(NBSBOLookup.OBSERVATIONLABREPORT,NBSOperationLookup.ASSIGNSECURITY);
    		
    		
    		if (nbsSecurityObj == null) {
    			logger.fatal("Error: no nbsSecurityObj in the session, go back to login screen");
    			return mapping.findForward("login");
    		}
    		boolean viewObs = nbsSecurityObj.getPermission(NBSBOLookup.OBSERVATIONLABREPORT,NBSOperationLookup.VIEW);
    		if(viewObs!=true)
    			viewObs = nbsSecurityObj.getPermission(NBSBOLookup.OBSERVATIONMORBIDITYREPORT,NBSOperationLookup.VIEW);
    		request.setAttribute("viewObs",String.valueOf(viewObs));

    		if ((!nbsSecurityObj.getPermission(NBSBOLookup.OBSERVATIONLABREPORT,
    				NBSOperationLookup.ASSIGNSECURITY)) && !(nbsSecurityObj.getPermission(NBSBOLookup.OBSERVATIONMORBIDITYREPORT,NBSOperationLookup.ASSIGNSECURITY)) && !(nbsSecurityObj.getPermission(NBSBOLookup.DOCUMENT,NBSOperationLookup.ASSIGNSECURITY)))
    		{
    			logger
    			.fatal("Error: no permisstion to VIEW observations Needing Program, go back to login screen");
    			throw new ServletException("Error: no permisstion to VIEW observations Needing Program, go back to login screen");
    		}
    		try
    		{
    			ArrayList<Object> observationsNeedingAssignment = new ArrayList<Object> ();
    			Collection<Object>  displayVOColl  = new ArrayList<Object> ();
    		
    			//GENERIC QUEUE
    			GenericForm genericForm= translateFromObservationSummaryDisplayVOToGenericSummaryDisplayVO(obsRevForm, request);
    			//\GENERIC QUEUE
    			
    			if(forceEJBcall)
    			{
    				MainSessionCommand msCommand = null;
    				MainSessionHolder mainSessionHolder = new MainSessionHolder();
    				msCommand = mainSessionHolder.getMainSessionCommand(session);
    				logger
    				.info("You are trying to GET_Observation_Assignment_Load");
    				String sBeanJndiName = JNDINames.TASKLIST_PROXY_EJB;
    				String sMethod = "getObservationsNeedingSecurity";
    				logger
    				.debug("calling getObservationsNeedingSecurity on TaskListProxyEJB from ObservationAssignmentLoad");
    				ArrayList<Object> arr = (ArrayList<Object> )msCommand.processRequest(sBeanJndiName,sMethod, null);
    				observationsNeedingAssignment = (ArrayList<Object> )arr.get(0);
    				observationsNeedingAssignment=removeRecordsWithoutPermissionsToView(nbsSecurityObj, observationsNeedingAssignment, obsRevForm);
    				
    				displayVOColl= this.getDisplayVO(observationsNeedingAssignment,request);
    				//getting the documents from the Document table and adding to the observation collection
    				//Collection<Object>  docmentAssgnColl = getDocumentCollection(session);
    				// Collection<Object>  nDocColl = convertToDosplayVO(docmentColl);
    				// displayVOColl.addAll(nDocColl);
    				updatePatLinks(displayVOColl, request);
    				if(displayVOColl!=null){
    					NBSContext.store(session ,NBSConstantUtil.DSObservationList,displayVOColl); 
    					obsRevForm.setLabCount(new Integer(displayVOColl.size()).toString());
    					
    				}
    				NBSContext.store(session ,NBSConstantUtil.DSObservationList,displayVOColl);
    				NBSContext.store(session, "DSQueueObjectFull", displayVOColl);
    				//obsRevForm.setObservationColl(displayVOColl);
    				//genericForm.setElementColl(displayVOColl);
    				
    				//obsRevForm.initializeDropDowns();    
    				
    				//GENERIC QUEUE
    				genericForm.setElementColl(displayVOColl);
    				obsRevForm.initializeDropDowns(displayVOColl, genericForm, obsRevForm.getCLASS_NAME());
    				Map<Object, Object> map = genericQueueUtil.setQueueCount(genericForm, genericForm.getQueueDT());
    				obsRevForm.setAttributeMap(map);
    				}
    			else{
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
    			
    			if(contextAction != null && (contextAction.equalsIgnoreCase("ReturnToObservationsNeedingAssignment")||contextAction.equals("Delete")
    					|| contextAction.equals("transferBulk"))) {
    				updatePatLinks(displayVOColl, request);
    				Collection<Object>  filteredColl = (ArrayList<Object> ) genericQueueUtil.filterQueue(genericForm, request, className);
    				if(filteredColl != null)
    					displayVOColl = (ArrayList<Object> ) filteredColl;
    				genericQueueUtil.sortQueue(genericForm, displayVOColl, true, request,"getDateReceived");
    			} else {
    				genericQueueUtil.sortQueue(genericForm, displayVOColl, existing, request,"getDateReceived");

    				if(!existing) {
    					updatePatLinks(displayVOColl, request);
    				} else
    				{
    					
    					//GENERIC QUEUE
    					genericQueueUtil.filterQueue(genericForm, request, className);
    					//\GENERIC QUEUE
    					
    				}
    			}
    			request.setAttribute("queueCount", String.valueOf(displayVOColl.size()));
    			// Now that it has been sorted, set it to request.
    			request.setAttribute("observationList", displayVOColl);
    			request.setAttribute("PageTitle","Documents Requiring Security Assignment");
    			Integer queueSize = propertyUtil
    					.getQueueSize(NEDSSConstants.OBSERVATIONS_NEEDING_ASSIGNMENT_QUEUE_SIZE);
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
    		}
    		catch (Exception e)
    		{
    			e.printStackTrace();
    			logger.error(
    					"Error in ObservationReviewLoad in getting ObservationsNeedingReview from EJB");
    			throw new ServletException("Error in ObservationReviewLoad in getting ObservationsNeedingReview from EJB"+e.getMessage(),e);

    		}
    	}catch (Exception e) {
    		logger.error("Exception in ObservationsAssignmentLoad: " + e.getMessage());
    		e.printStackTrace();
    		throw new ServletException("General error occurred in Observations Needing Assignment Queue Load : "+e.getMessage());
    	} 
        return PaginationUtil.paginate(obsRevForm, request, "obsneedingsecurity_review",mapping);
    }
    
    /**
     * removeRecordsWithoutPermissionsToView: remove the records that the user doesn't have permissions for the program area/jurisdiction
     * from the observationsNeedingReview list
     * @param nbsSecurityObj
     * @param observationsNeedingReview
     * @param obsRevForm
     * @return
     */
 private ArrayList<Object> removeRecordsWithoutPermissionsToView(NBSSecurityObj nbsSecurityObj, ArrayList<Object> observationsNeedingReview, ObservationNeedingSecurityReviewForm obsRevForm){
		
    	boolean permissionsLab = true, permissionsMorb=true, permissionsCase=true;
    	String permissionView="";
    	ArrayList<Object> docsWithPermission = new ArrayList<Object>();
    	
    	for(int i=0; i<observationsNeedingReview.size(); i++){
    		
    		if(observationsNeedingReview.get(i) instanceof LabReportSummaryVO){
    			LabReportSummaryVO labReportSummaryVO = ((LabReportSummaryVO)observationsNeedingReview.get(i));
    			String programArea = labReportSummaryVO.getProgAreaCd()==null?"ANY":labReportSummaryVO.getProgAreaCd();
    			String jurisdiction = labReportSummaryVO.getJurisdictionCd()==null?"ANY":labReportSummaryVO.getJurisdictionCd();
    			//String sharedInd = labReportSummaryVO.getSharedInd()==null?"":labReportSummaryVO.getSharedInd();
			    
    			permissionsLab = nbsSecurityObj.getPermission(NBSBOLookup.OBSERVATIONLABREPORT,NBSOperationLookup.VIEW,programArea,jurisdiction);

    			if(permissionsLab)
    				docsWithPermission.add(observationsNeedingReview.get(i));
    		}
    		if(observationsNeedingReview.get(i) instanceof MorbReportSummaryVO){
    			
    			MorbReportSummaryVO morbReportSummaryVO = ((MorbReportSummaryVO)observationsNeedingReview.get(i));
    			String programArea = morbReportSummaryVO.getProgAreaCd()==null?"ANY":morbReportSummaryVO.getProgAreaCd();
    			String jurisdiction = morbReportSummaryVO.getJurisdictionCd()==null?"ANY":morbReportSummaryVO.getJurisdictionCd();
    			//String sharedInd = morbReportSummaryVO.getSharedInd()==null?"":morbReportSummaryVO.getSharedInd();
			    
    			permissionsMorb = nbsSecurityObj.getPermission(NBSBOLookup.OBSERVATIONMORBIDITYREPORT,NBSOperationLookup.VIEW,programArea,jurisdiction);

    			if(permissionsMorb)
    				docsWithPermission.add(observationsNeedingReview.get(i));
    		}
    		if(observationsNeedingReview.get(i) instanceof SummaryDT){
    		
    			SummaryDT doc = ((SummaryDT)observationsNeedingReview.get(i));
    			String programArea = doc.getProgAreaCd()==null?"ANY":doc.getProgAreaCd();
    			String jurisdiction = doc.getJurisdictionCd()==null?"ANY":doc.getJurisdictionCd();
    			//String sharedInd = doc.getSharedInd()==null?"":doc.getSharedInd();
    			
    			permissionsCase = nbsSecurityObj.getPermission(NBSBOLookup.DOCUMENT,NBSOperationLookup.VIEW,programArea,jurisdiction);
    			// nbsSecurityObj.getPermission(NBSBOLookup.DOCUMENT,NBSOperationLookup.VIEW,programArea);
    			
    			if(permissionsCase)
    				docsWithPermission.add(observationsNeedingReview.get(i));
    			}
    	}

		obsRevForm.getAttributeMap().put("permissionView",permissionView);

		return docsWithPermission;
	}
    
    /**
     * isPermissionJurisdiction: check if there's permission for the document type, for the jurisdiction with any of the program areas in case the program area
     * is empty
     * @param permission
     * @param nbsSecurityObj
     * @param jurisdiction
     * @return
     */
    
    private boolean permissionDocumentProgramAreaJurisdiction(NBSSecurityObj nbsSecurityObj, String documentType, String programArea, String jurisdiction){

    	boolean permission;
		if(programArea.isEmpty())
			programArea="ANY";
		if(jurisdiction.isEmpty())
			jurisdiction="ANY";

			permission = nbsSecurityObj.getPermission(documentType,NBSOperationLookup.VIEW,programArea,jurisdiction);

		return permission;
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
        			String type = observationSummaryDisplayVO.getType();
        			
        			if(condDesc!= null && condDesc.indexOf("<a") == -1){
        				ArrayList<String> conditions = new ArrayList<String>();
        				conditions.add(condDesc);
        				observationSummaryDisplayVO.setCondition(conditions);
        				observationSummaryDisplayVO.setTestsStringNoLnk(condDesc);//For sorting
        				
        			}
        			
        			observationSummaryDisplayVO.setTestsString(condDesc);	
        			//if(condDesc.indexOf("<a") == -1)
        				//observationSummaryDisplayVO.setTestsStringNoLnk(condDesc);
        			String viewHref = request.getAttribute("ViewMorbHref") == null ? "": (String) request.getAttribute("ViewMorbHref");
        			if(!viewHref.equals("")) {
        				String condLink = "<a href=\"#\" onclick=\"createLink(this,\'" + viewHref+ "&MPRUid="+ String.valueOf(observationSummaryDisplayVO.getMPRUid()) + "&observationUID="+String.valueOf(observationSummaryDisplayVO.getObservationUID())+"\'"+")"+"\" >"+ type + "</a>";
        				observationSummaryDisplayVO.setTypeLnk(condLink);
        				String patLink = "<a href=\"#\" onclick=\"createLink(this,\'" + viewFileHref+ "&MPRUid="+ String.valueOf(observationSummaryDisplayVO.getMPRUid()) + "&observationUID="+String.valueOf(observationSummaryDisplayVO.getObservationUID())+"\'"+")"+"\" >"+ patFullNm + "</a>";
        				observationSummaryDisplayVO.setFullName(patLink);        			}
        			}else if(observationSummaryDisplayVO.getType() != null && observationSummaryDisplayVO.getType().trim().equals("Case Report"))
        			{
            			String condDesc = observationSummaryDisplayVO.getTestsString();
            			String type = observationSummaryDisplayVO.getType();
            			
            			if(condDesc!= null && condDesc.indexOf("<a") == -1){
            				ArrayList<String> conditions = new ArrayList<String>();
            				conditions.add(condDesc);
            				observationSummaryDisplayVO.setCondition(conditions);
            				observationSummaryDisplayVO.setTestsStringNoLnk(condDesc);//For sorting
            			}
            			
            			observationSummaryDisplayVO.setTestsString(condDesc);	
            			//if(condDesc!= null && condDesc.indexOf("<a") == -1)
            			//	observationSummaryDisplayVO.setTestsStringNoLnk(condDesc);
            			String viewHref = request.getAttribute("ViewDocHref") == null ? "": (String) request.getAttribute("ViewDocHref");
            			if(!viewHref.equals("")) {
            				String condLink = "<a href=\"#\" onclick=\"createLink(this,\'" + viewHref+ "&MPRUid="+ String.valueOf(observationSummaryDisplayVO.getMPRUid()) + "&observationUID="+String.valueOf(observationSummaryDisplayVO.getObservationUID())+"\'"+")"+"\" >"+ type + "</a>";
            				observationSummaryDisplayVO.setTypeLnk(condLink);
            				String patLink = "<a href=\"#\" onclick=\"createLink(this,\'" + viewFileHref+ "&MPRUid="+ String.valueOf(observationSummaryDisplayVO.getMPRUid()) + "&observationUID="+String.valueOf(observationSummaryDisplayVO.getObservationUID())+"\'"+")"+"\" >"+ patFullNm + "</a>";
            				observationSummaryDisplayVO.setFullName(patLink);            			}       			
        			}else {
        				String condDesc = observationSummaryDisplayVO.getTestsString();
        				String type = observationSummaryDisplayVO.getType();
        				
            			if(condDesc!= null && condDesc.indexOf("<a") == -1){
            				ArrayList<String> conditions = new ArrayList<String>();
            				conditions.add(condDesc);
            				observationSummaryDisplayVO.setCondition(conditions);
            				observationSummaryDisplayVO.setTestsStringNoLnk(condDesc);//For sorting
            			}
        				String viewLabref = request.getAttribute("ViewLabHref") == null ? "": (String) request.getAttribute("ViewLabHref");
            			if(!viewFileHref.equals("")) {
            				String event =  "<a href=\"#\" onclick=\"createLink(this,\'" + viewLabref+ "&MPRUid="+ String.valueOf(observationSummaryDisplayVO.getMPRUid()) + "&observationUID="+String.valueOf(observationSummaryDisplayVO.getObservationUID())+"\'"+")"+"\" >"+ type + "</a>";
            				observationSummaryDisplayVO.setTypeLnk(event);
            				
            				util.appendElectronicLabIcon(observationSummaryDisplayVO);
            				/*
            				//Append Electronic Ind
            				if(observationSummaryDisplayVO.getType().trim().equals("Lab Report")){
	            				if(observationSummaryDisplayVO.getElectronicInd()!=null && observationSummaryDisplayVO.getElectronicInd().equals("Y")){
	            					observationSummaryDisplayVO.setTypeLnk(event+"<br><img src=\"ind_electronic.gif\" title=\"Electronic Lab\">");
	            					//if(tabName.equals(NEDSSConstants.EVENT))
	            					//	dt.setDateReceived(startDate+"<br><img src=\"ind_electronic.gif\" title=\"Electronic Lab\">"+"<br>"+processingDecision);
	            				}else
	            					observationSummaryDisplayVO.setTypeLnk(event.toString());
            				}*/
            				
            				
            				String patLink = "<a href=\"#\" onclick=\"createLink(this,\'" + viewFileHref+ "&MPRUid="+ String.valueOf(observationSummaryDisplayVO.getMPRUid()) + "&observationUID="+String.valueOf(observationSummaryDisplayVO.getObservationUID())+"\'"+")"+"\" >"+ patFullNm + "</a>";
            				observationSummaryDisplayVO.setFullName(patLink);            			}
            			
            			
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

    		ObservationNeedingSecurityReviewForm obsNeedRevForm = (ObservationNeedingSecurityReviewForm) form;
    		
    		//GENERIC QUEUE
    		//Translate from obsNeedRevForm (and observationSummaryDisplayVO) to genericSummaryDisplayVO
    		GenericForm genericForm= translateFromObservationSummaryDisplayVOToGenericSummaryDisplayVO(obsNeedRevForm, request);
    		
    		Collection<Object>  observationSummaryVOs = genericQueueUtil.filterQueue(genericForm, request, className);
    		//\GENERIC QUEUE
    		
    		if(observationSummaryVOs != null){
    			request.setAttribute("ActionMode", HTMLEncoder.encodeHtml("Search"));
    		//When all filters are applied (equivalent to no filter applied), Collection<Object>  is set to null in which case retrieve it from Form.
    		}else{
    			try {
    			observationSummaryVOs = (ArrayList<Object> ) NBSContext.retrieve(request.getSession(true) ,"DSQueueObjectFull");
    			}catch(Exception ex) {
    				logger.debug("DSQueueObjectFull is null in observation assignment load");
    				observationSummaryVOs = (ArrayList<Object> ) NBSContext.retrieve(request.getSession(true) ,NBSConstantUtil.DSObservationList);
    			}
    		}
    		NBSContext.store(request.getSession(true) ,NBSConstantUtil.DSObservationList,observationSummaryVOs);
    		genericQueueUtil.sortQueue(genericForm, observationSummaryVOs, true, request,"getDateReceived");
    		request.setAttribute("ActionMode", HTMLEncoder.encodeHtml("Search"));
    		request.setAttribute("observationList", observationSummaryVOs);
    		request.setAttribute("queueCount", String.valueOf(observationSummaryVOs.size()));			
    		request.setAttribute("PageTitle","Documents Requiring Security Assignment");
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
    		return PaginationUtil.paginate(obsNeedRevForm, request, "obsneedingsecurity_review",mapping);		
    		
    	}
       

	private Collection<Object>  getDisplayVO(Collection<Object>  observationsNeedingReviewColl,
			HttpServletRequest request) {
		
		ObservationReviewQueueUtil util = new ObservationReviewQueueUtil();
		Iterator<Object>  needingReviewIter = observationsNeedingReviewColl.iterator();
		Collection<Object>  displayVOColl = new ArrayList<Object> ();
		PropertyUtil propertyUtil = PropertyUtil.getInstance();
		int labCountFix = propertyUtil.getLabCount();
		int morbCountFix = propertyUtil.getMorbCount();
		int docCountFix = propertyUtil.getDocCount();
		int labCount = 0;
		int morbCount = 0;
		int docCount = 0;
		while (needingReviewIter.hasNext()) {
			ReportSummaryInterface report = (ReportSummaryInterface) needingReviewIter
					.next();
			if (report instanceof LabReportSummaryVO) {
				if (labCount < labCountFix) {
					
					LabReportSummaryVO labReportSummaryVO = (LabReportSummaryVO) report;
					ObservationSummaryDisplayVO observationSummaryDisplayVO = new ObservationSummaryDisplayVO();
					//Provider information
					
			           observationSummaryDisplayVO.setProviderFirstName(labReportSummaryVO.getProviderFirstName());
			           observationSummaryDisplayVO.setProviderLastName(labReportSummaryVO.getProviderLastName());
			           observationSummaryDisplayVO.setProviderPrefix(labReportSummaryVO.getProviderPrefix());
			           observationSummaryDisplayVO.setProviderSuffix(labReportSummaryVO.getProviderSuffix());
			           observationSummaryDisplayVO.setProviderUid(labReportSummaryVO.getProviderUid());
			           observationSummaryDisplayVO.setProviderDegree(labReportSummaryVO.getProviderDegree());
			           observationSummaryDisplayVO.setReportingFacility(labReportSummaryVO.getReportingFacility());
			           
			           
					//Electronic Indicator
					observationSummaryDisplayVO.setElectronicInd(labReportSummaryVO.getElectronicInd());
					
					observationSummaryDisplayVO.setTypePrint(labReportSummaryVO.getType());
					if(labReportSummaryVO.getElectronicInd()!=null && labReportSummaryVO.getElectronicInd().equalsIgnoreCase("Y"))
						
					observationSummaryDisplayVO.setTypePrint(labReportSummaryVO.getType()+"\n(E)");
					 
					
					util.setDateFormat(labReportSummaryVO, observationSummaryDisplayVO);
					
					observationSummaryDisplayVO.setType(labReportSummaryVO
							.getType());
					observationSummaryDisplayVO
							.setProgramArea(labReportSummaryVO.getProgramArea());
					observationSummaryDisplayVO
							.setJurisdiction(labReportSummaryVO
									.getJurisdiction());
					observationSummaryDisplayVO.setTestsString(this
							.getResultedTest(labReportSummaryVO));
					//observationSummaryDisplayVO.setTestsStringNoLnk(this
					//		.getResultedTest(labReportSummaryVO));
					observationSummaryDisplayVO.setObservationId(labReportSummaryVO.getLocalIdForUpdatedAndNewDoc());
    				observationSummaryDisplayVO.setObservationIdPrint(labReportSummaryVO.getLocalIdForUpdatedAndNewDocPrint());
    				observationSummaryDisplayVO.setLocalId(labReportSummaryVO.getLocalId());
    				
					observationSummaryDisplayVO
							.setObservationUID(labReportSummaryVO
									.getObservationUid());
					observationSummaryDisplayVO.setMPRUid(labReportSummaryVO
							.getMPRUid());
					observationSummaryDisplayVO.setFirstName(labReportSummaryVO.getPatientFirstName());
					observationSummaryDisplayVO.setLastName(labReportSummaryVO.getPatientLastName());
					observationSummaryDisplayVO.setPersonLocalId(labReportSummaryVO.getPersonLocalId());
			
					
					
					if(labReportSummaryVO.getBirthTime()!=null)
			        	   observationSummaryDisplayVO.setBirthTime(StringUtils.formatDate(labReportSummaryVO.getBirthTime()));
			           if(labReportSummaryVO.getCurrSexCd()!=null)
			        	   observationSummaryDisplayVO.setCurrSexCd(CachedDropDowns.getCodeDescTxtForCd(labReportSummaryVO.getCurrSexCd(), "SEX"));
			       	
			           util.formatProvider(observationSummaryDisplayVO, labReportSummaryVO);
			           
		           if(labReportSummaryVO.isLabFromDoc()){
		        	   observationSummaryDisplayVO.setDescription(labReportSummaryVO.getResultedTestString());
				 
		           }else{
					 observationSummaryDisplayVO.setDescription(DecoratorUtil.getResultedTestsStringForWorup(labReportSummaryVO.getTheResultedTestSummaryVOCollection()));
					 observationSummaryDisplayVO.setDescriptions(DecoratorUtil.getResultedDescription(labReportSummaryVO.getTheResultedTestSummaryVOCollection()));
					 
			           
		           }
		           
		           String descriptionPrint = util.getDescriptionPrint(observationSummaryDisplayVO.getDescription());
		           observationSummaryDisplayVO.setDescriptionPrint(descriptionPrint);
		           
		           
		           if(labReportSummaryVO.getBirthTime()!=null)
		        	   observationSummaryDisplayVO.setBirthTime(StringUtils.formatDate(labReportSummaryVO.getBirthTime()));
		           if(labReportSummaryVO.getCurrSexCd()!=null)
		        	   observationSummaryDisplayVO.setCurrSexCd(CachedDropDowns.getCodeDescTxtForCd(labReportSummaryVO.getCurrSexCd(), "SEX"));
					displayVOColl.add(observationSummaryDisplayVO);
				}
				labCount = labCount + 1;
			} else if (report instanceof MorbReportSummaryVO) {

				if (morbCount < morbCountFix) {
					// System.out.println("\n MorbReport");
					MorbReportSummaryVO morbReportSummaryVO = (MorbReportSummaryVO) report;
					
					
					ObservationSummaryDisplayVO observationSummaryDisplayVO = new ObservationSummaryDisplayVO();
					
					//GetProviderInformation
		            observationSummaryDisplayVO.setProviderFirstName(morbReportSummaryVO.getProviderFirstName());
		            observationSummaryDisplayVO.setProviderLastName(morbReportSummaryVO.getProviderLastName());
		            observationSummaryDisplayVO.setProviderPrefix(morbReportSummaryVO.getProviderPrefix());
		            observationSummaryDisplayVO.setProviderSuffix(morbReportSummaryVO.getProviderSuffix());
		            observationSummaryDisplayVO.setProviderUid(morbReportSummaryVO.getProviderUid());
		            observationSummaryDisplayVO.setProviderDegree(morbReportSummaryVO.getProviderDegree());
		            observationSummaryDisplayVO.setReportingFacility(morbReportSummaryVO.getReportingFacility());
		            observationSummaryDisplayVO.setPersonLocalId(morbReportSummaryVO.getPersonLocalId());
		            
		        	
		            util.setDateFormat(morbReportSummaryVO, observationSummaryDisplayVO);
		            
					observationSummaryDisplayVO.setType(morbReportSummaryVO
							.getType());
					observationSummaryDisplayVO.setTypePrint(morbReportSummaryVO.getType());
					observationSummaryDisplayVO.setStatus(morbReportSummaryVO
							.getStatus());
					observationSummaryDisplayVO
					.setProgramArea(morbReportSummaryVO
							.getProgramArea());
					observationSummaryDisplayVO
					.setJurisdiction(morbReportSummaryVO
							.getJurisdiction());
					observationSummaryDisplayVO
					.setTestsString(morbReportSummaryVO
							.getConditionDescTxt());
					observationSummaryDisplayVO
					.setObservationId(morbReportSummaryVO.getLocalId());
					observationSummaryDisplayVO.setObservationIdPrint(morbReportSummaryVO.getLocalId());
    				observationSummaryDisplayVO.setLocalId(morbReportSummaryVO.getLocalId());
					observationSummaryDisplayVO
					.setObservationUID(morbReportSummaryVO
							.getObservationUid());
					observationSummaryDisplayVO.setMPRUid(morbReportSummaryVO
							.getMPRUid());
					observationSummaryDisplayVO.setFirstName(morbReportSummaryVO.getPatientFirstName());
					observationSummaryDisplayVO.setLastName(morbReportSummaryVO.getPatientLastName());
					
					if(morbReportSummaryVO.getBirthTime()!=null)
			         	   observationSummaryDisplayVO.setBirthTime(StringUtils.formatDate(morbReportSummaryVO.getBirthTime()));
			            if(morbReportSummaryVO.getCurrSexCd()!=null)
			         	   observationSummaryDisplayVO.setCurrSexCd(CachedDropDowns.getCodeDescTxtForCd(morbReportSummaryVO.getCurrSexCd(), "SEX"));
			        	
					util.formatProvider(observationSummaryDisplayVO, morbReportSummaryVO);
					
					
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
		            
		            
					
					displayVOColl.add(observationSummaryDisplayVO);
				}
				morbCount = morbCount + 1;
			} else if (report instanceof SummaryDT) {
				if (docCount < docCountFix) {

					// System.out.println("\n MorbReport");
					SummaryDT summaryDt = (SummaryDT) report;
					           
					ObservationSummaryDisplayVO observationSummaryDisplayVO = new ObservationSummaryDisplayVO();
					
					//GetProviderInformation
		            observationSummaryDisplayVO.setProviderFirstName(summaryDt.getProviderFirstName());
		            observationSummaryDisplayVO.setProviderLastName(summaryDt.getProviderLastName());
		            observationSummaryDisplayVO.setProviderPrefix(summaryDt.getProviderPrefix());
		            observationSummaryDisplayVO.setProviderSuffix(summaryDt.getProviderSuffix());
		            observationSummaryDisplayVO.setProviderUid(summaryDt.getProviderUid());
		            observationSummaryDisplayVO.setProviderDegree(summaryDt.getProviderDegree());
		            observationSummaryDisplayVO.setSendingFacilityNm(summaryDt.getSendingFacilityNm());
		            observationSummaryDisplayVO.setPersonLocalId(summaryDt.getPersonLocalId());
					
		        	
		            util.setDateFormat(summaryDt, observationSummaryDisplayVO);
    		        
					observationSummaryDisplayVO.setType(summaryDt
							.getType());
					observationSummaryDisplayVO.setTypePrint(summaryDt
							.getType());
					observationSummaryDisplayVO
							.setProgramArea(summaryDt
									.getProgramArea());
					observationSummaryDisplayVO
							.setJurisdiction(summaryDt
									.getJurisdiction());
					observationSummaryDisplayVO
							.setTestsString(summaryDt
									.getCdDescTxt());
					observationSummaryDisplayVO.setObservationId(summaryDt.getLocalIdForUpdatedAndNewDoc());
    				observationSummaryDisplayVO.setObservationIdPrint(summaryDt.getLocalIdForUpdatedAndNewDocPrint());
    				observationSummaryDisplayVO.setLocalId(summaryDt.getLocalId());
    				
					observationSummaryDisplayVO
							.setObservationUID(summaryDt
									.getNbsDocumentUid());
					observationSummaryDisplayVO.setMPRUid(summaryDt
							.getMPRUid());
					observationSummaryDisplayVO.setFirstName(summaryDt.getFirstName());
					observationSummaryDisplayVO.setLastName(summaryDt.getLastName());
					if(summaryDt.getBirthTime()!=null)
		         	   observationSummaryDisplayVO.setBirthTime(StringUtils.formatDate(summaryDt.getBirthTime()));
		            if(summaryDt.getCurrSexCd()!=null)
		         	   observationSummaryDisplayVO.setCurrSexCd(CachedDropDowns.getCodeDescTxtForCd(summaryDt.getCurrSexCd(), "SEX"));
					
				
					observationSummaryDisplayVO.setDescription(summaryDt.getDescription());
					
    				ArrayList<String> descriptions = new ArrayList<String>();
    				descriptions.add(summaryDt.getCdDescTxt());
    				observationSummaryDisplayVO.setDescriptions(descriptions);
    				observationSummaryDisplayVO.setDescription(summaryDt.getCdDescTxt() == null ? "" :
    					"<b>"+summaryDt.getCdDescTxt()+"</b>");
    			
    				String descriptionPrint = util.getDescriptionPrint(observationSummaryDisplayVO.getDescription());
    				observationSummaryDisplayVO.setDescriptionPrint(descriptionPrint);
			
    				displayVOColl.add(observationSummaryDisplayVO);
    				
					util.formatProvider(observationSummaryDisplayVO, summaryDt);
				}
			}
			else
				logger.debug("Neither = " + report.getClass());
		}
		return displayVOColl;

	}
	
	 public QueueDT fillQueueDT(){
	    	
	    	//ArrayList<QueueColumnDT> queueDTcollection = new ArrayList<QueueColumnDT>();
	    	QueueDT queueDT = new QueueDT();

	    	QueueColumnDT queue = new QueueColumnDT();


	    	//First column: Document Type

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
	    	
	    	queue.setColumnStyle("width:11%");
	    	queue.setFilterType("2");//0 date, 1 text, 2 multiselect
	    	queue.setDropdownProperty("OBSERVATIONTYPE");
	    	queue.setDropdownStyleId("obsType");
	    	queue.setDropdownsValues("observationTypes");
	    	queue.setErrorIdFiltering("??");//Same than BackendId?
	    	queue.setConstantCount("OBSERVATION_TYPE_COUNT");
	    	//queue.setSearchCriteriaConstant("OBSERVATIONTYPE");
	    	queue.setMethodFromElement("getType");
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
	    	queue.setMethodGeneralFromForm("getColumn2List");//Only for date/multiselect
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
	    	//queue.setMethodFromForm("getColumn3List");
	    	queueDT.setColumn3(queue);
	    	
	    	

	    	//Forth column: Full Name
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
	    	queue.setColumnStyle("width:16%");
	    	queue.setFilterType("1");//0 date, 1 text, 2 multiselect
	    	queue.setDropdownProperty("SearchText1");
	    	queue.setDropdownStyleId("patient");
	    	queue.setDropdownsValues("noDataArray");
	    	queue.setErrorIdFiltering("??");//Same than BackendId?
	    	queue.setConstantCount("??");
	    	//queue.setSearchCriteriaConstant("??");//Same than property??
	    	queue.setMethodFromElement("getFullNameNoLnk");
	    	//queue.setMethodGeneralFromForm("getColumn4List");//Only for date/multiselect
	    	
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
	    	queue.setColumnStyle("width:22%");
	    	queue.setFilterType("2");//0 date, 1 text, 2 multiselect
	    	queue.setDropdownProperty("DESCRIPTION");
	    	queue.setDropdownStyleId("descrip");
	    	queue.setDropdownsValues("resultedDescription");
	    	queue.setErrorIdFiltering("??");//Same than BackendId?
	    	queue.setConstantCount("RESULTEDDES_COUNT");//?????????
	    	//queue.setSearchCriteriaConstant("??");//Same than property??
	    	queue.setMethodFromElement("getDescriptions");
	    	queue.setMultipleValues("true");
	    	queue.setMethodGeneralFromForm("getColumn3List");//Only for date/multiselect
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
	    	queue.setMethodGeneralFromForm("getColumn4List");//Only for date/multiselect
	    	queue.setFilterByConstant(NEDSSConstants.FILTERBYJURISDICTION);
	    	queueDT.setColumn6(queue);
	    	

	    	//Seventh column: Program area
	    	queue = new QueueColumnDT();
	    	queue.setColumnId("column7");
	    	queue.setColumnName("Program Area");
	    	//queue.setColumnPropertyName("programArea");
	    	queue.setBackendId("INV108");//??
	    	queue.setDefaultOrder("ascending");
	    	queue.setSortable("true");
	    	queue.setSortNameMethod("getProgramArea");
	    	queue.setMedia("html pdf csv");
	    	queue.setMediaHtmlProperty("programArea");
	    	queue.setMediaCsvProperty("programArea");
	    	queue.setMediaPdfProperty("programArea");
	    	queue.setDropdownStyleId("parea");
	    	queue.setColumnStyle("width:15%");
	    	queue.setFilterType("2");
	    	queue.setDropdownsValues("programArea");
	    	queue.setMethodFromElement("getProgramArea");
	    	queue.setMethodGeneralFromForm("getColumn5List");//Only for date/multiselect
			queue.setConstantCount("DATE_PRGAREA_COUNT");
			queue.setDropdownProperty("PROGRAMAREA");
	    	queueDT.setColumn7(queue);
	    	


	    	//Eighth column: Local id
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
	    	//queue.setMethodGeneralFromForm("getColumn8List");//Only for date/multiselect
	    	
	    	queueDT.setColumn8(queue);
	    	
	    	return queueDT;
	    	
	    }
	 
	 @SuppressWarnings("unchecked")
	private GenericForm translateFromObservationSummaryDisplayVOToGenericSummaryDisplayVO(ObservationNeedingSecurityReviewForm obsNeedRevForm, HttpServletRequest request){
 		
 		GenericForm genericForm = new GenericForm();
 		
 		
 		genericForm.setSearchCriteriaArrayMap(obsNeedRevForm.getSearchCriteriaArrayMap());
 		genericForm.setAttributeMap(obsNeedRevForm.getAttributeMap());
 		genericForm.setQueueDT(obsNeedRevForm.getQueueDT());
 		//general methods from form
 		genericForm.setColumn1List(obsNeedRevForm.getObservationTypes());
 		genericForm.setColumn2List(obsNeedRevForm.getDateFilterList());
 		genericForm.setColumn3List(obsNeedRevForm.getResultedDescription());
 		genericForm.setColumn4List(obsNeedRevForm.getJurisdictions());
 		genericForm.setColumn5List(obsNeedRevForm.getProgramArea());
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

		public void setQueueCount(ObservationNeedingSecurityReviewForm obsRevForm, QueueDT queueDT){
			
			Map<Object, Object> map = obsRevForm.getAttributeMap();
			
			
			String method ="getColumn";
	    	QueueColumnDT result;
	    	
	    	Class tClass = queueDT.getClass();
	    	
	    	for(int i=1; i<=10; i++){
	    		try{
				method+=i;
				Method gs1Method = tClass.getMethod(method, new Class[] {});
				result = (QueueColumnDT) gs1Method.invoke(queueDT, new Object[] {});
				
				
				String constantCount=result.getConstantCount();
				String method2=result.getMethodGeneralFromForm();
				String filterType = result.getFilterType();
				
				
				if(filterType!=null && (filterType=="0" || filterType=="2")){
					try{
					Class tClass2 = obsRevForm.getClass();
					Method gs1Method2 = tClass2.getMethod(method2, new Class[] {});
					ArrayList<Object> result2 = (ArrayList<Object>) gs1Method2.invoke(obsRevForm, new Object[] {});
					
					int size=result2.size();
					map.put(constantCount, new Integer (size));
					
					}catch(Exception e){
						//TODO
					}
					
					
				}
				
				
				
	    		}
	    		catch(Exception e){
	    			
	    		}
	    		}
	    		}
		
		


}