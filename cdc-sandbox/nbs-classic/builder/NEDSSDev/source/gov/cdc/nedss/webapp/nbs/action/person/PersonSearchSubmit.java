package gov.cdc.nedss.webapp.nbs.action.person;


import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.*;
import gov.cdc.nedss.systemservice.nbscontext.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
//for the old way using entity
import gov.cdc.nedss.util.*;
import gov.cdc.nedss.webapp.nbs.form.person.*;
import gov.cdc.nedss.entity.person.util.DisplayPersonList;
import gov.cdc.nedss.entity.person.vo.*;
import gov.cdc.nedss.webapp.nbs.action.person.util.PersonUtil;
import gov.cdc.nedss.webapp.nbs.action.person.util.SearchResultPersonUtil;
import gov.cdc.nedss.webapp.nbs.action.util.DSQueueObject;
import gov.cdc.nedss.webapp.nbs.action.util.PaginationUtil;
/**
 * Title:        Actions
 * Description:  Person Search Submit
 * Copyright:    Copyright (c) 2001
 * Company:      CSC
 * @author:
 * @version 1.0
 */
import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;


public class PersonSearchSubmit
    extends Action
{
	
	private static PropertyUtil propertyUtil= PropertyUtil.getInstance();
	public PersonSearchSubmit()
    {
    }
    static final LogUtils logger = new LogUtils(PersonSearchSubmit.class.getName());
    @SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
				 HttpServletRequest request,
				 HttpServletResponse response)
			  throws IOException, ServletException
    {
    String queueName=null;	
    SearchResultPersonUtil srpUtil = new SearchResultPersonUtil();
	HttpSession session = request.getSession(false);
	NBSSecurityObj secObj = (NBSSecurityObj)session.getAttribute("NBSSecurityObject");
	PersonSearchForm personSearchForm =(PersonSearchForm) form;
	String contextAction = request.getParameter("ContextAction");
	boolean homePageSrch =  request.getParameter("homePageSrch") == null ? false : true;
	if(homePageSrch && !"sortingByColumn".equalsIgnoreCase(contextAction))
		contextAction = "Submit";

	if (contextAction == null) {
	    contextAction = (String)request.getAttribute("ContextAction");
	}
     request.setAttribute("PersonName",session.getAttribute("PersonName"));

         //pass the VOLookup for doing VO for entity search
      request.setAttribute("VOLookup",
                                  (request.getAttribute("VOLookup") == null
                                   ? "" : (String)request.getAttribute("VOLookup")));


	/*****************************
	 * SUBMIT ACTION
	 */

        String mode = request.getParameter("mode");
        String initLoad = request.getParameter("initLoad");
        if(initLoad!=null && initLoad.equals("true") && !PaginationUtil._dtagAccessed(request))
        {
        	//for initial load reset the value from context
        	NBSContext.store(session, NBSConstantUtil.DSAttributeMap, new HashMap<Object, Object>());
        	NBSContext.store(session, NBSConstantUtil.DSSearchCriteriaMap, new HashMap<Object, Object>());
        	NBSContext.store(session, NBSConstantUtil.DSPersonList, new ArrayList<Object>());
        	NBSContext.store(session, NBSConstantUtil.DSPersonListFull, new ArrayList<Object>());
        	NBSContext.store(session, NBSConstantUtil.DSSearchCriteriaMap, new HashMap<Object, Object>());
        	personSearchForm.setSearchCriteriaArrayMap(new HashMap<Object, Object>());
        	personSearchForm.setAttributeMap(new HashMap<Object, Object>());
        }

        String customQueue = (String)personSearchForm.getAttributeMap().get("custom");
        String permissionPublicQueue=secObj.getPermission(NBSBOLookup.PUBLICQUEUES, NBSOperationLookup.ADDINACTIVATE)+"";
        request.setAttribute("permissionPublicQueue", permissionPublicQueue);
        

        
       if(contextAction!=null && contextAction.equalsIgnoreCase("InvestigationID")){//In case we are accessing the investigation from custom queue, we need to have available some data for the return link
    	   if(customQueue!=null && customQueue.equalsIgnoreCase("true")){
    		   
    		   
    		   request.setAttribute("custom","true");
    		   request.setAttribute("queueName", (String)personSearchForm.getAttributeMap().get("queueName"));
    		   request.setAttribute("reportType", "I");//TODO: it will need to be read from somewhere else when this is extended for other business object types
    		   
    		   
    	   }

       }
       //In case we are coming back from Return to Search Results
       if(customQueue==null){
    	   customQueue =  (String)request.getAttribute("custom");   
       }
    	   
       
       
       //In case it is a customQueue, we need to keep the name of the custom queue at the top
       if(customQueue!=null && customQueue.equalsIgnoreCase("true")){
    	   queueName =  (String)personSearchForm.getAttributeMap().get("queueName");
 		  request.setAttribute("PageTitle",queueName);  
       } else {
    	   request.setAttribute("PageTitle","Search Results"); 
       }
      
	    //This is useful to store the type of queue in the DB if we save the queue as a custom queue.
	   	String queueType = (String)personSearchForm.getPersonSearch().getReportType();
	   	request.setAttribute("queueType",queueType);//In case we extend it in future for other type of events
	   	
	   	logger.debug("context action:::::::"+contextAction);
       
       if(contextAction!=null && (contextAction.equalsIgnoreCase("investigation")|| contextAction.equalsIgnoreCase("markAsReviewedLabReports"))){//Custom
    	   
       	String finalQuery = "";
       	String searchCriteriaDesc = "";
       	String searchCriteriaCd = "";
       	String customType="";
       	HashMap<String, ArrayList<String>> searchCriteriaCustomMap = (HashMap<String, ArrayList<String>>)request.getSession().getAttribute("customQueuesSearchCriteriaMap");
    	queueName=(String)request.getParameter("queueName");
       	String markAsReview=request.getParameter("markAsReview");
     	if("true".equalsIgnoreCase(markAsReview) && null == queueName ) queueName=(String)request.getSession().getAttribute("custom_queue_in_session");
       	  
       	if(searchCriteriaCustomMap!=null && searchCriteriaCustomMap.get(queueName)!=null){//The list with the search criteria to be applied. It is coming from custom_queues.query_string
       		 ArrayList<String> criterias = (ArrayList<String>)searchCriteriaCustomMap.get(queueName);
       	
       		finalQuery = criterias.get(0);
       		searchCriteriaDesc = criterias.get(1);
       		searchCriteriaCd = criterias.get(2);//Available in case needed in the future, for example, to prepopulate in order to edit
       		customType=criterias.get(3); //Event Type
           	request.setAttribute("searchCriteriaDesc",searchCriteriaDesc);
            String pageNumber = request.getParameter("pageNumber");
            if(pageNumber!=null)
            		((PersonSearchForm)form).getAttributeMap().put("PageNumber",pageNumber);
       		}
       	
       	
    	//	customQueue = true;
    		homePageSrch=true;
    		if(NEDSSConstants.INVESTIGATION_EVENT_ID.equalsIgnoreCase(customType)) {
    		    personSearchForm.getAttributeMap().put("reportType", NEDSSConstants.INVESTIGATION_EVENT_ID);
    		    personSearchForm.getPersonSearch().setReportType(NEDSSConstants.INVESTIGATION_EVENT_ID);
    		}
            if(NEDSSConstants.LAB_REPORT_EVENT_ID.equalsIgnoreCase(customType)) {	
    			personSearchForm.getAttributeMap().put("reportType", NEDSSConstants.LAB_REPORT_EVENT_ID);
    		    personSearchForm.getPersonSearch().setReportType(NEDSSConstants.LAB_REPORT_EVENT_ID);
    		    request.getSession().setAttribute("custom_queue_in_session",queueName); 
    		}

		    personSearchForm.getPersonSearch().setCustom("true");
		    personSearchForm.getAttributeMap().put("custom", "true");
		    personSearchForm.getAttributeMap().put("queueName", queueName);
		    personSearchForm.getAttributeMap().put("searchCriteriaDesc", searchCriteriaDesc);
	
		    
			  
	  	    request.setAttribute("PageTitle",queueName);
	  	    
	  	    Map<Object, Object> attributeMap = null;
	  	    try {
	  	    	  attributeMap = (Map<Object, Object>) NBSContext.retrieve(session, NBSConstantUtil.DSAttributeMap);
	  	    }catch(Exception ex) {
	  	    	logger.debug( "Attribute Map is null in context");
	  	    }
	  	    if(attributeMap!=null && attributeMap.size()>0)
	  	    	personSearchForm.setAttributeMap(attributeMap);
	  	    
    		findPatientsCustomQuery(personSearchForm, finalQuery, request);
    		request.setAttribute("mode",request.getParameter("mode"));
			if(personSearchForm.getSearchCriteriaArrayMap().size() == 0)
				request.setAttribute("ActionMode", HTMLEncoder.encodeHtml("InitLoad"));
	
//	    	request.setAttribute("ActionMode", "InitLoad");//Without this, the filter dropdowns won't be checked by default when the queue loads first time.
	    	
            return mapping.findForward(contextAction);
	} 
        

      if( (contextAction.equalsIgnoreCase("Submit") || contextAction.equalsIgnoreCase("EntitySearch")) && mode == null)
      {
    	  
    	personSearchForm.getPersonSearch().setCustom("false");//Set to false as default unless it is a Custom queue, and It will be changed to True. This will avoid showing buttons like Save from a Custom Queue.

    	
    //	String searchCriteriaCd="testing1=[hello]|testing2=[bye1,bye2]";
    	String searchCriteriaCd=translateSearchIntoString(personSearchForm);
    	request.getSession().setAttribute("searchCriteriaCd", searchCriteriaCd);
    		 
	    PersonUtil.findPeople(personSearchForm, session, request, homePageSrch, false);

	    // add button security
	    boolean bAddButton = secObj.getPermission(NBSBOLookup.PATIENT,
						      NBSOperationLookup.ADD);
	    request.setAttribute("addButton", String.valueOf(bAddButton));

	    String strCurrentIndex = (String)request.getParameter("currentIndex");


		request.setAttribute("DSFromIndex",strCurrentIndex);
		request.setAttribute("mode",request.getParameter("mode"));
        return mapping.findForward(contextAction);

	}
	/****************************
	 * NEXT action
	 */
	else if (contextAction.equalsIgnoreCase("Next")  && mode == null)
	{
	    String strCurrentIndex = (String)request.getParameter(
					     "currentIndex");

	    NBSContext.store(session,"DSFromIndex",strCurrentIndex);
	    return mapping.findForward(contextAction);
	}
	/*****************************************
	 * PREVIOUS ACTION
	 */
	else if (contextAction.equalsIgnoreCase("Prev"))
	{
	    String strCurrentIndex = (String)request.getParameter(
					     "currentIndex");

	    NBSContext.store(session,"DSFromIndex",strCurrentIndex);
	    return mapping.findForward(contextAction);
	}
	/******
	/****************************
	 * ADD action
	 */
	else if (contextAction.equalsIgnoreCase("Add"))
	{

	    return mapping.findForward(contextAction);
	}
	/******************************
	 * refine search action
	 */
	else if (contextAction.equalsIgnoreCase("RefineSearch"))
	{
		PersonSearchForm psForm = (PersonSearchForm)form;
		psForm.setSearchCriteriaArrayMap(new HashMap<Object,Object>());
	    return mapping.findForward(contextAction);
	}
  	/******************************
  	 * NEW SEARCH ACTION
  	 */
  	else if (contextAction.equalsIgnoreCase("NewSearch"))
  	{
  		PersonSearchForm psForm = (PersonSearchForm)form;
  		psForm.clearAll();
  	    return mapping.findForward(contextAction);
  	}
      
    /******************************
  	* VIEW INVESTIGATION
  	*/
      
  	else if (contextAction.equalsIgnoreCase("InvestigationID"))
  	{
  		String publicHealthCaseUid = (String)request.getParameter("publicHealthCaseUID");
        NBSContext.store(session, "DSInvestigationUID", publicHealthCaseUid);
  	    return mapping.findForward(contextAction);
  	}
	/*********************************
	 * VIEW ACTION
	 */
	else if (contextAction.equalsIgnoreCase("View"))
	{
            Long personUID = new Long((String)request.getParameter("uid"));
            NBSContext.store(session, "DSPatientPersonUID", personUID);
	    return mapping.findForward(contextAction);
	}
    //View File Action
    else if (contextAction.equalsIgnoreCase("ViewFile"))
    {
        Long personUID = new Long((String)request.getParameter("uid"));
        NBSContext.store(session, "DSPatientPersonUID", personUID);
        NBSContext.store(session,"DSFileTab","2");
        return mapping.findForward(contextAction);
    }//View Lab
    else if (contextAction.equalsIgnoreCase("ViewLab"))
    {
        Long observationUid = new Long((String)request.getParameter("observationUID"));
        NBSContext.store(session, "DSObservationUID", observationUid);
        request.setAttribute("PageTitle", "View Lab Report");
        if(customQueue!=null && "true".equalsIgnoreCase(customQueue) && null != queueName){ //setting values for return search results
    		DSQueueObject dSQueueObject = new DSQueueObject();
			dSQueueObject.setDSQueueType(NEDSSConstants.LAB_REPORT_EVENT_ID);
			NBSContext.store(session, "DSQueueObject", dSQueueObject);
        }
        return mapping.findForward(contextAction);
    }
    //View Inv
    else if (contextAction.equalsIgnoreCase("ViewInv"))
    {
        Long observationUid = new Long((String)request.getParameter("publicHealthCaseUID"));
        NBSContext.store(session, "DSObservationUID", observationUid);
        //NBSContext.store(session,"DSFileTab","2");
        return mapping.findForward(contextAction);
    }

        else if("Next".equalsIgnoreCase(mode))
		{
			String strCurrentIndex = (String)request.getParameter("currentIndex");
			request.setAttribute("mode",request.getParameter("mode"));


			request.setAttribute("DSFromIndex",strCurrentIndex);
			request.setAttribute("mode",request.getParameter("mode"));


			return mapping.findForward(contextAction);
		}
	    else if("ReturnToSearchResults".equalsIgnoreCase(contextAction) || "Cancel".equalsIgnoreCase(contextAction))
	    {
	    	PersonSearchForm psForm = (PersonSearchForm)form;
	    	psForm.clearSelections();
	    	return mapping.findForward("Submit");
	    }
	    else if("filterPatientSubmit".equalsIgnoreCase(contextAction)){
	    	PersonSearchForm psForm = (PersonSearchForm)form;
	    	String custom = (String)psForm.getAttributeMap().get("custom");
	    	String reportType = (String)psForm.getAttributeMap().get("reportType");

    		String searchCriteriaDesc =(String)psForm.getAttributeMap().get("searchCriteriaDesc");
    		
	    	srpUtil.showButton(request,(String) psForm.getAttributeMap().get("DSSearchCriteriaString"));
	    	psForm.getPersonSearch().setCustom(custom);

			psForm.getAttributeMap().put("searchCriteriaDesc",searchCriteriaDesc);
			request.setAttribute("searchCriteriaDesc",searchCriteriaDesc);
			psForm.getPersonSearch().setReportType(reportType);
	    	return srpUtil.filterPatientSubmit( mapping,  form,  request,  response);
	      }
		else if("removeFilter".equalsIgnoreCase(contextAction))
		{
			PersonSearchForm psForm = (PersonSearchForm)form;
			
			String reportType = (String)psForm.getAttributeMap().get("reportType");
    		String custom = (String)psForm.getAttributeMap().get("custom");
    		queueName =(String)psForm.getAttributeMap().get("queueName");
    		String searchCriteriaDesc =(String)psForm.getAttributeMap().get("searchCriteriaDesc");
			Boolean permissionMarkAsReviewed=(Boolean)psForm.getAttributeMap().get("permissionMarkAsReviewed");
			String processLogic=(String)psForm.getAttributeMap().get("processDecissionConst");
           	
			srpUtil.showButton(request,(String) psForm.getAttributeMap().get("DSSearchCriteriaString"));
			String scString = (String) psForm.getAttributeMap().get("DSSearchCriteriaString");
			String reportTp = (String) psForm.getAttributeMap().get("reportType");
			ArrayList<Object> investigatorDDList = (ArrayList<Object>) psForm.getAttributeMap().get("investigatorDDList");
			ArrayList<Object> jurisdictionDDList = (ArrayList<Object>) psForm.getAttributeMap().get("jurisdictionDDList");
			ArrayList<Object> conditionDDList = (ArrayList<Object>) psForm.getAttributeMap().get("conditionDDList");
			ArrayList<Object> CaseStatusDDList = (ArrayList<Object>) psForm.getAttributeMap().get("CaseStatusDDList");
			ArrayList<Object> startDateDDList = (ArrayList<Object>) psForm.getAttributeMap().get("startDateDDList");
			ArrayList<Object> notificationDDList = (ArrayList<Object>) psForm.getAttributeMap().get("notificationDDList");
	    	
			ArrayList<Object> conditionDD = (ArrayList<Object>) psForm.getAttributeMap().get("conditionDD");
			ArrayList<Object> observationTypeDDList = (ArrayList<Object>) psForm.getAttributeMap().get("observationTypeDDList");
			ArrayList<Object> descriptionDDList = (ArrayList<Object>) psForm.getAttributeMap().get("descriptionDDList");
			String totalRecords = psForm.getAttributeMap().get("totalRecords")+"";
			psForm.clearAll();
			
			psForm.getAttributeMap().put("DSSearchCriteriaString", scString);
	    	psForm.getAttributeMap().put("reportType", reportTp);
	    	psForm.getAttributeMap().put("investigatorDDList", investigatorDDList);
	    	psForm.getAttributeMap().put("jurisdictionDDList", jurisdictionDDList);
	    	psForm.getAttributeMap().put("conditionDDList", conditionDDList);
	    	psForm.getAttributeMap().put("CaseStatusDDList", CaseStatusDDList);
	    	psForm.getAttributeMap().put("startDateDDList", startDateDDList);
	    	psForm.getAttributeMap().put("notificationDDList", notificationDDList);
	    	
	    	psForm.getAttributeMap().put("conditionDD", conditionDD);
	    	psForm.getAttributeMap().put("observationTypeDDList", observationTypeDDList);
	    	psForm.getAttributeMap().put("descriptionDDList", descriptionDDList);
	    	psForm.getAttributeMap().put("totalRecords", totalRecords);
	    	
	    	psForm.getPersonSearch().setReportType(reportType);
			psForm.getPersonSearch().setCustom(custom);
			psForm.getAttributeMap().put("reportType",reportType);
			psForm.getAttributeMap().put("custom",custom);
			psForm.getAttributeMap().put("queueName",queueName);
			psForm.getAttributeMap().put("permissionMarkAsReviewed",null!=permissionMarkAsReviewed?permissionMarkAsReviewed.booleanValue():false);
			psForm.getAttributeMap().put("processDecissionConst",null!=processLogic?processLogic:NEDSSConstants.NBS_NO_ACTION_RSN);
			
			psForm.getAttributeMap().put("searchCriteriaDesc",searchCriteriaDesc);
			
			
			request.setAttribute("searchCriteriaDesc",searchCriteriaDesc);
			if(psForm.getSearchCriteriaArrayMap().size() == 0)
				request.setAttribute("ActionMode", HTMLEncoder.encodeHtml("InitLoad"));

	    	return srpUtil.filterPatientSubmit( mapping,  form,  request,  response);
		}
	    else if("sortingByColumn".equalsIgnoreCase(contextAction)){
	    	
	    	Collection<Object> patientVoCollection = null;
	    	PersonSearchForm psForm = (PersonSearchForm)form;
	    	try{
	    		
	    		if(null != psForm.getPersonSearch().getReportType() && NEDSSConstants.LAB_REPORT_EVENT_ID.equalsIgnoreCase(psForm.getPersonSearch().getReportType())){
	    			psForm.getAttributeMap().put("reportType",NEDSSConstants.LAB_REPORT_EVENT_ID);
	    		}
	    		String reportType = (String)psForm.getAttributeMap().get("reportType");
	    		
	    		srpUtil.filterPatientSubmit(mapping, psForm, request, response);
	    		patientVoCollection = (ArrayList<Object> )NBSContext.retrieve(request.getSession() ,NBSConstantUtil.DSPersonList);
	    		srpUtil.showButton(request,(String) psForm.getAttributeMap().get("DSSearchCriteriaString"));
	    		
	    		String custom = (String)psForm.getAttributeMap().get("custom");
	    		String searchCriteriaDesc =(String)psForm.getAttributeMap().get("searchCriteriaDesc");
				
	    		//if psForm.getSearchCriteriaArrayMap();
	    		SearchResultPersonUtil sr = new SearchResultPersonUtil();
	    		
				if(reportType != null && reportType.equalsIgnoreCase("I")){
					patientVoCollection = sr.filterInvs(psForm, request);//ND-19318: in case there's already a filter applied
					srpUtil.sortInvs(psForm,patientVoCollection,true,request);
				}
				else if(reportType != null && reportType.equalsIgnoreCase("LR") || reportType.equalsIgnoreCase("LMC")){
					patientVoCollection = sr.filterObservations(psForm, request);//ND-19318: in case there's already a filter applied
					srpUtil.sortObservations(psForm,patientVoCollection,true,request);
				}
				else{
					patientVoCollection = sr.filterPatient(psForm, request);//ND-19318
					srpUtil.sortPatientLibarary(psForm,patientVoCollection,true,request);	
						
				}
				
	    		psForm.getAttributeMap().put("queueCount", String.valueOf(patientVoCollection.size()));
	    		
				request.setAttribute("queueCount", String.valueOf(patientVoCollection.size()));
				request.setAttribute("personList", patientVoCollection);
				
				psForm.getPersonSearch().setReportType(reportType);
				psForm.getPersonSearch().setCustom(custom);

				psForm.getAttributeMap().put("searchCriteriaDesc",searchCriteriaDesc);
				request.setAttribute("searchCriteriaDesc",searchCriteriaDesc);
				if(psForm.getSearchCriteriaArrayMap().size() == 0)
					request.setAttribute("ActionMode", HTMLEncoder.encodeHtml("InitLoad"));

				NBSContext.store(request.getSession(), NBSConstantUtil.DSAttributeMap, psForm.getAttributeMap());
				
	    	}catch(Exception e){

	    	}
	    	String currTask = NBSContext.getCurrentTask(session);
	    	if(currTask!=null && currTask.equalsIgnoreCase("PatientSearchResults1"))
	    		request.setAttribute("PageTitle", "Search Results ");

	    	return PaginationUtil.personPaginate(psForm, request, "searchResultLoad",mapping);
	      }
		return null;
    }
    
    private String translateSearchIntoString(PersonSearchForm personSearchForm){
    	
    	PatientSearchVO pat = personSearchForm.getPersonSearch();
    	String searchCriteriaCd="";
    	
    	//Event type
    	String eventType = pat.getReportType();

    	if(eventType!=null && !eventType.isEmpty())
    	 searchCriteriaCd = "EventType=["+eventType+"]";
    	
    	//Condition
    	String[] condition = pat.getConditionSelected();

    	if(condition!=null && condition.length!=0)
    	 searchCriteriaCd += "|Condition=["+Arrays.asList(condition)+"]";
    	
    	//Program Area
    	String[] pa = pat.getProgramAreaInvestigationSelected();
    	
    	if(pa!=null && pa.length!=0)
    		searchCriteriaCd+="|ProgArea=["+Arrays.asList(pa)+"]";
    	
    	
    	//Jurisdiction
    	
    	
    	String[] jurisdiction = pat.getJurisdictionSelected();
    	
    	if(jurisdiction!=null && jurisdiction.length!=0)
    		searchCriteriaCd+="|Jurisdiction=["+Arrays.asList(jurisdiction)+"]";
    	
    	
    	
    	
    	//Pregnancy Status
    	
    	
    	String pregnant = pat.getPregnantSelected();
    	
    	if(pregnant!=null && !pregnant.isEmpty())
    		searchCriteriaCd+="|Pregnancy=["+pregnant+"]";
    	
    	
    	//Event ID Type
    	
    	String eventIDType = pat.getActType();
    	
    	if(eventIDType!=null && !eventIDType.isEmpty()){
    		searchCriteriaCd+="|EventIDType=["+eventIDType+"]";
    	
	    	//Event ID
    		
    		String eventIDOp = pat.getDocOperator();
			String eventID = pat.getActId();
	    
			
			if(eventIDOp!=null && !eventIDOp.isEmpty() && eventIDOp.equalsIgnoreCase("="))
				eventIDOp = "Eq";//To not get confused with separator =
			
	    	if(eventID!=null && !eventID.isEmpty())// 	Event ID
	    		searchCriteriaCd+="|EventID="+eventIDOp+"["+eventID+"]";	

    	}
    	
  
    	//Event Date Type
    	
    	String eventDateType = pat.getDateType();
    	
    	if(eventDateType!=null && !eventDateType.isEmpty()){
    		searchCriteriaCd+="|EventDateType=["+eventDateType+"]";
    	
	    	//Event Date
    		
    		String eventDateOp = pat.getDateOperator();
			String eventDateFrom = pat.getDateFrom();
			String eventDateTo = pat.getDateTo();
			
			if(eventDateOp!=null && !eventDateOp.isEmpty() && eventDateOp.equalsIgnoreCase("="))
				eventDateOp = "Eq";//To not get confused with separator =
			
	    	if((eventDateFrom!=null && !eventDateFrom.isEmpty()) || (eventDateTo!=null && !eventDateTo.isEmpty())){// 	Event ID
	    		
	    		if(eventDateOp!=null & eventDateOp.equalsIgnoreCase("BET"))//Between
	    			searchCriteriaCd+="|EventDate=["+eventDateOp+","+eventDateFrom+","+eventDateTo+"]";	
	    		else
	    			searchCriteriaCd+="|EventDate=["+eventDateOp+","+eventDateFrom+"]";	
	    		
	    		
	    	}

    	}
    	
    	//Event Status
    	
    	
    	String eventStatusNew = pat.getEventStatusInitialSelected();
    	String eventStatusUpdate = pat.getEventStatusUpdateSelected();
    	
    	if((eventStatusNew!=null && !eventStatusNew.isEmpty())||(eventStatusUpdate!=null && !eventStatusUpdate.isEmpty()))
    		searchCriteriaCd+="|EventStatus=["+eventStatusNew+","+eventStatusUpdate+"]";

    	//Event Created By User
    	
    	
    	String createdByUser = pat.getDocumentCreateSelected();
    	
    	if(createdByUser!=null && !createdByUser.isEmpty())
    		searchCriteriaCd+="|CreatedByUser=["+createdByUser+"]";
    	
    
    	//Processed/Unprocessed Labreports
    	
    	String processed = pat.getProcessedState();
    	String unProcessed = pat.getUnProcessedState();
    	logger.debug("processed::::::::::"+processed);
    	logger.debug("unprocessed::::::::::"+unProcessed);
    	if((processed!=null && !processed.isEmpty())||(unProcessed!=null && !unProcessed.isEmpty()))
    		searchCriteriaCd+="|Processing Status=["+processed+","+unProcessed+"]";
    	
    	
    	//Event Last Updated By User
    	
    	
    	String updatedByUser = pat.getDocumentUpdateSelected();
    	
    	if(updatedByUser!=null && !updatedByUser.isEmpty())
    		searchCriteriaCd+="|UpdatedByUser=["+updatedByUser+"]";
    	

    	
    	//Event Provider/Facility Type
    	
    	String providerFacility = pat.getProviderFacilitySelected();
    	
    	if(providerFacility!=null && !providerFacility.isEmpty()){
    		searchCriteriaCd+="|ProviderFacilityType=["+providerFacility+"]";
    	
		    	//Provider/Facility value:
		    	
		   	 if(pat.getProviderFacilitySelected().equals(PersonSearchVO.ORDERING_FACILITY)){
			   	   String desc = pat.getOrderingFacilitySelected();
			   	searchCriteriaCd+="|OrderingFacility=["+desc+"]";
			     
			          } 
			     else if(pat.getProviderFacilitySelected().equals(PersonSearchVO.ORDERING_PROVIDER)){
			    	 String desc = pat.getOrderingProviderSelected();
			    		
			    		searchCriteriaCd+="|OrderingProvider=["+desc+"]";
					      
			           } 
			           
			     else if(pat.getProviderFacilitySelected().equals(PersonSearchVO.REPORTING_FACILITY)){
			    	 String desc = pat.getReportingFacilitySelected();
			    		
					searchCriteriaCd+="|ReportingFacility=["+desc+"]";
			           } 
			      
			     else if(pat.getProviderFacilitySelected().equals(PersonSearchVO.REPORTING_PROVIDER)){
			    	 String desc = pat.getReportingProviderSelected();
			    		
			    	 searchCriteriaCd+="|ReportingProvider=["+desc+"]";
				     
			           } 
			   	
		    
    	}
    
    	//Investigator
    	String investigatorSelected = pat.getInvestigatorSelected();
    	
    	if(investigatorSelected!=null && !investigatorSelected.isEmpty())
    		searchCriteriaCd+="|Investigator=["+investigatorSelected+"]";
    	
    	//Investigation Status
    	
    	String investigationStatus = pat.getInvestigationStatusSelected();
    	
    	if(investigationStatus!=null && !investigationStatus.isEmpty())
    		searchCriteriaCd+="|InvestigationStatus=["+investigationStatus+"]";
    	
    	
    	//Outbreak Name
    	
    	String[] outbreakName = pat.getOutbreakNameSelected();
    	
    	if(outbreakName!=null && outbreakName.length!=0)
    		searchCriteriaCd+="|OutbreakName=["+ Arrays.asList(outbreakName)+"]";
    	
    	
    	
    	//Case Status
    	
    	
    	
    	
    	String caseStatus = "";
    	
    	if(pat.getCaseStatusCodedValuesSelected()!=null)
    		caseStatus = pat.getCaseStatusCodedValuesSelected().trim();
		
		if(pat.getCaseStatusListValuesSelected()!=null && pat.getCaseStatusListValuesSelected().contains("UNASSIGNED") && 
				!((pat.getCaseStatusCodedValuesSelected() != null)
						&& (pat.getCaseStatusCodedValuesSelected().trim().length() != 0)
						))
			caseStatus+="UNASSIGNED";
		else
			if(pat.getCaseStatusListValuesSelected()!=null && pat.getCaseStatusListValuesSelected().contains("UNASSIGNED"))
				caseStatus+=",UNASSIGNED";
		
		
		if(caseStatus!=null && !caseStatus.isEmpty())
    		searchCriteriaCd+="|CaseStatus=["+caseStatus+"]";
		
    	
    	//Notification Status 	
    	
		String notification = "";
		
		if(pat.getNotificationCodedValuesSelected()!=null)
			notification = pat.getNotificationCodedValuesSelected().trim();
			
		if(!((pat.getNotificationCodedValuesSelected() != null)
				&& (pat.getNotificationCodedValuesSelected().trim().length() != 0))
				&& (pat.getNotificationValuesSelected()!=null && pat.getNotificationValuesSelected().contains("UNASSIGNED")))
			notification+="UNASSIGNED";
		else
			if(pat.getNotificationValuesSelected()!=null && pat.getNotificationValuesSelected().contains("UNASSIGNED"))
				notification+=",UNASSIGNED";
		
		if(notification!=null && !notification.isEmpty())
    		searchCriteriaCd+="|NotificationStatus=["+notification+"]";
		
		
    	//Current Processing Status
    	
		String currentProcessStatus = "";
		
		if(pat.getCurrentProcessCodedValuesSelected()!=null)
			currentProcessStatus = pat.getCurrentProcessCodedValuesSelected().trim();
		
		if ((!(pat.getCurrentProcessCodedValuesSelected() != null
				&& pat.getCurrentProcessCodedValuesSelected().trim().length() != 0))
				&& pat.getCurrentProcessStateValuesSelected()!=null && pat.getCurrentProcessStateValuesSelected().contains("UNASSIGNED"))
			currentProcessStatus+=",UNASSIGNED";
		else
		if(pat.getCurrentProcessStateValuesSelected()!=null && pat.getCurrentProcessStateValuesSelected().contains("UNASSIGNED"))
			currentProcessStatus+=",UNASSIGNED";
    	
		if(currentProcessStatus!=null && !currentProcessStatus.isEmpty())
			searchCriteriaCd+="|CurrentProcessingStatus=["+currentProcessStatus+"]";
		
		logger.debug("searchCriteriaCd::::::::"+searchCriteriaCd);
		
    	return searchCriteriaCd;
    	
    }
                                                                      
    
    /**
     * findPatientsCustomQuery: instead of building the query from the VO, it will get the query as a parameter and will get the list of patients from there.
     * @param psForm
     * @param finalQuery
     * @param request
     */
    @SuppressWarnings("unchecked")
	public void findPatientsCustomQuery(PersonSearchForm psForm, String finalQuery, HttpServletRequest request ){
 	   
	   	ArrayList<?> personList = new ArrayList<Object> ();
		PatientSearchVO psVO = null;
		SearchResultPersonUtil srpUtil = new SearchResultPersonUtil();
	       ArrayList list = new ArrayList();
	       boolean bEntitySearch = false;//TODO: not sure about this
	    try
	    {
	    	NedssUtils nedssUtils = null;
		    MainSessionCommand msCommand = null;
		    String sBeanJndiName = "";
		    String sMethod = "";
		    Object[] oParams = null;
		    sBeanJndiName = JNDINames.ENTITY_PROXY_EJB;
		    sMethod = "findPatientByQuery";
		    psVO = psForm.getPersonSearch();
		    oParams = new Object[]{
		    		psVO, new Integer(propertyUtil.getNumberOfRows()),
		    		new Integer(0), finalQuery};

			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(request.getSession());
	        ArrayList<?> arrList = msCommand.processRequest(sBeanJndiName,  sMethod, oParams);
	        personList = (ArrayList<?> )arrList.get(0);
	
	        if(personList.size()>1){//finalQuery was saved in position 1
	        	personList.remove(1);
	        }
			
	    }
	    catch (Exception e)
	    {
   		logger.warn("Exception in PersonSearchSubmit: " + e.getMessage());
   		e.printStackTrace();

	    }
	    if( personList != null && personList.size() > 0  &&  personList.get(0)!=null){
	    	DisplayPersonList displayPersonList = (DisplayPersonList) personList.get(0);
       	list = displayPersonList.getList();
			try{
			      NBSSecurityObj secObj = (NBSSecurityObj) request.getSession().getAttribute(
			          "NBSSecurityObject");
				boolean checkSummaryPermission = secObj.getPermission(NBSBOLookup.PATIENT,
				          NBSOperationLookup.VIEWWORKUP);
				
				if(psVO.getReportType()!=null && psVO.getReportType().equalsIgnoreCase("I")){
					
					srpUtil.setDisplayInfoInvestigation(list,checkSummaryPermission);
					srpUtil.sortInvs(psForm, list, false, request);
				}else
					if(psVO.getReportType()!=null && psVO.getReportType().equalsIgnoreCase("LR")){
						
						srpUtil.setDisplayInfoLaboratoryReport(list,checkSummaryPermission, request);
						srpUtil.sortObservationsQueue(psForm, list, false, request);
						srpUtil.updateMarkAsReviewdPermission(secObj,psForm, list, request);
					}
					else{
					
						srpUtil.setDisplayInfo(list,checkSummaryPermission, bEntitySearch, request);
						srpUtil.sortPatientLibarary(psForm, list, false, request);
					}
				
				request.setAttribute("personList", list);
				//request.getSession().setAttribute("personList", list);
				
				//set the Mark as reviewd permission
				
				 
				psForm.setIntqueueCount(displayPersonList.getTotalCounts());
			//	psForm.setPatientVoCollection(list);

				NBSContext.store(request.getSession() ,NBSConstantUtil.DSPersonList,list);
				NBSContext.store(request.getSession() ,NBSConstantUtil.DSPersonListFull,list);
				
				psForm.initializeDropDowns(list);
			
			/*	psVO.setJurisdictionsDD(psForm.getJurisdictionsDD());
				psVO.setNotifications(psForm.getNotifications());
				psVO.setCaseStatuses(psForm.getCaseStatusesDD());
				
				psVO.setConditions(psForm.getConditions());
				psVO.setDateFilterList(psForm.getStartDateDropDowns());*/
				psForm.getAttributeMap().put("notificationDDList", psForm.getNotifications());
				psForm.getAttributeMap().put("investigatorDDList", psForm.getInvestigators());
				psForm.getAttributeMap().put("CaseStatusDDList", psForm.getCaseStatusesDD());
				psForm.getAttributeMap().put("jurisdictionDDList", psForm.getJurisdictionsDD());
				psForm.getAttributeMap().put("startDateDDList", psForm.getStartDateDropDowns());
				psForm.getAttributeMap().put("conditionDDList", psForm.getConditions());
				
				psForm.getAttributeMap().put("observationTypeDDList", psForm.getObservationTypesDD());
				psForm.getAttributeMap().put("descriptionDDList", psForm.getDescriptionDD());
				psForm.getAttributeMap().put("conditionDD", psForm.getConditionDD());
				
				psForm.getAttributeMap().put("InvestigatorsCount",new Integer(psForm.getInvestigators().size()));
				psForm.getAttributeMap().put("JurisdictionsCount",new Integer(psForm.getJurisdictionsDD().size()));
				psForm.getAttributeMap().put("ConditionsCount",new Integer(psForm.getConditions().size()));
				psForm.getAttributeMap().put("caseStatusCount",new Integer(psForm.getCaseStatusesDD().size()));
				psForm.getAttributeMap().put("dateFilterListCount",new Integer(psForm.getStartDateDropDowns().size()));
				psForm.getAttributeMap().put("notificationsCount",new Integer(psForm.getNotifications().size()));
				
				psForm.getAttributeMap().put("DescriptionCount",new Integer(psForm.getDescriptionDD().size()));
				psForm.getAttributeMap().put("observationTypeCount",new Integer(psForm.getObservationTypesDD().size()));
				psForm.getAttributeMap().put("ConditionsDDCount",new Integer(psForm.getConditionDD().size()));
				psForm.getAttributeMap().put("totalRecords",list.size());
				
				
				psForm.getAttributeMap().put("queueCount", list.size());
			    
				psForm.getAttributeMap().put("TOTALCOUNT", psForm.getIntqueueCount());
				//session.setAttribute("notificationDDList", psForm.getNotifications());
				NBSContext.store(request.getSession(), NBSConstantUtil.DSAttributeMap, psForm.getAttributeMap());
				//request.getSession().setAttribute("attributeMap", psForm.getAttributeMap());
				
			}catch(Exception e){

	        }
	    }

	    
   }
}