
package gov.cdc.nedss.webapp.nbs.action.person;

import gov.cdc.nedss.entity.person.vo.PatientSearchVO;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.HTMLEncoder;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.action.person.util.PersonUtil;
import gov.cdc.nedss.webapp.nbs.action.person.util.SearchResultPersonUtil;
import gov.cdc.nedss.webapp.nbs.action.util.ErrorMessageHelper;
import gov.cdc.nedss.webapp.nbs.action.util.PaginationUtil;
import gov.cdc.nedss.webapp.nbs.form.person.PersonSearchForm;
import gov.cdc.nedss.webapp.nbs.queue.GenericQueueUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;

/**
 * This class is for preparing the Person Search Results page
 * for display of search results.
 */
public class PersonSearchResultsLoad
    extends Action {
	private static PropertyUtil propUtil = PropertyUtil.getInstance();
	
	static final LogUtils logger = new LogUtils(PersonSearchResultsLoad.class.getName());
   /**
    * This is the constructor for the PersonSearchResultsLoad
    * class
    */
   public PersonSearchResultsLoad() {
   }

   /**
    * This method is controls the execution of the
    * PersonSearchResultsLoad logic, and dictates
    * the navigation.
    *
    * @param mapping ActionMapping
    * @param form ActionForm
    * @param request HttpServletRequest
    * @param response HttpServletResponse
    * @exception IOException
    * @exception ServletException
    * @return ActionForward
    */
   @SuppressWarnings("unchecked")
public ActionForward execute(ActionMapping mapping, ActionForm form,
                                HttpServletRequest request,
                                HttpServletResponse response) throws
       IOException, ServletException {

	   

		  
	  GenericQueueUtil genericQueueUtil = new GenericQueueUtil();
      HttpSession session = request.getSession(false);
      NBSSecurityObj secObj = (NBSSecurityObj) session.getAttribute(
          "NBSSecurityObject");
      String contextAction = request.getParameter("ContextAction");
	  boolean homePageSrch =  request.getParameter("homePageSrch") == null ? false : true;
	  if(homePageSrch) {
		  request.setAttribute("ContextAction", "Submit");
		  contextAction = "Submit";			
	  }
	  
      request.setAttribute("PersonName",session.getAttribute("PersonName"));
      if (contextAction == null) {
         contextAction = (String) request.getAttribute("ContextAction");
         /***************************************************
          * SUBMIT ACTION
          */
      }
      
     // PersonSearchForm personSearchForm = (PersonSearchForm)request.getAttribute("personSearchForm");
      PatientSearchVO psVO = null;
      PersonSearchForm personSearchForm =(PersonSearchForm) form;
     
      boolean customQueue = false;
  	
      if (contextAction.equalsIgnoreCase("investigation") ||
    	  contextAction.equalsIgnoreCase("markAsReviewedLabReports") ||
    	  contextAction.equalsIgnoreCase("Submit") ||
          contextAction.equalsIgnoreCase("Next") ||
          contextAction.equalsIgnoreCase("Prev") ||
          contextAction.equalsIgnoreCase("ReturnToSearchResults") ||
          contextAction.equalsIgnoreCase("Cancel")) {

    	  
    	  if(contextAction.equalsIgnoreCase("investigation")||contextAction.equalsIgnoreCase("markAsReviewedLabReports")){
    		  customQueue = true;
    		  homePageSrch=true;
    	  }
         TreeMap<Object,Object> tm = NBSContext.getPageContext(session, "PS090", contextAction);
         ErrorMessageHelper.setErrMsgToRequest(request, "PS090");
         String sCurrTask = NBSContext.getCurrentTask(session);
         String previousPageID = NBSContext.getPrevPageID(session);
         NBSContext.lookInsideTreeMap(tm);
         
         String returnToLRQResults= request.getParameter("returnresults");
         
         String markAsReview = request.getParameter("markAsReview");
 		 //if ( (previousPageID != null && previousPageID.equals("PS036")) || "true".equalsIgnoreCase(returnToLRQResults) || ("true".equalsIgnoreCase(markAsReview) && "PatientSearchResults1".equalsIgnoreCase(sCurrTask))) {
 			if ( (previousPageID != null && previousPageID.equals("PS036")) || "true".equalsIgnoreCase(returnToLRQResults) || ("true".equalsIgnoreCase(markAsReview) && "markAsReviewedLabReports".equalsIgnoreCase(contextAction))) {
				try {
					Map<Object, Object> searchCriteriaMap = (HashMap<Object, Object>) NBSContext.retrieve(session,
							NBSConstantUtil.DSSearchCriteriaMap);
					if(null != searchCriteriaMap) {
						personSearchForm.setSearchCriteriaArrayMap(searchCriteriaMap);
						SearchResultPersonUtil srpUtil = new SearchResultPersonUtil();
						srpUtil.filterPatientSubmit(mapping, personSearchForm, request, response);
						personSearchForm.setAttributeMap((Map<Object, Object>) NBSContext.retrieve(request.getSession(),
							NBSConstantUtil.DSAttributeMap));
						String reportType = (String) personSearchForm.getAttributeMap().get("reportType");
						ArrayList<Object> patientVoCollection = (ArrayList<Object>) request.getAttribute("personList");
						if (reportType != null && reportType.equalsIgnoreCase("I")) {
							srpUtil.sortInvs(personSearchForm, patientVoCollection, true, request);
						} else if (reportType != null && reportType.equalsIgnoreCase("LR")) {
							srpUtil.sortObservations(personSearchForm, patientVoCollection, true, request);
						} else {
							srpUtil.sortPatientLibarary(personSearchForm, patientVoCollection, true, request);
						}
						request.setAttribute("personList", patientVoCollection);
					}
				} catch (Exception ex) {
					logger.debug(NBSConstantUtil.DSSearchCriteriaMap + " is null");
				}
			}

         // add button security
         boolean bAddButton = secObj.getPermission(NBSBOLookup.PATIENT,
             NBSOperationLookup.ADD);
         request.setAttribute("addButton", String.valueOf(bAddButton));

         if (secObj.getPermission(NBSBOLookup.PATIENT, NBSOperationLookup.ADD)) {
            request.setAttribute("addButtonHref",
                                 "/nbs/" + sCurrTask + ".do?ContextAction=" +
                                 tm.get("Add"));
         }
        boolean permissionLab = secObj.getPermission(NBSBOLookup.OBSERVATIONLABREPORT,NBSOperationLookup.VIEW);
 		boolean permissionMorb = secObj.getPermission(NBSBOLookup.OBSERVATIONMORBIDITYREPORT,NBSOperationLookup.VIEW);
 		boolean permissionCase = secObj.getPermission(NBSBOLookup.DOCUMENT,NBSOperationLookup.VIEW);
 		boolean permissionInvestigation = secObj.getPermission(NBSBOLookup.INVESTIGATION,NBSOperationLookup.VIEW);
 		
 		if(personSearchForm.getAttributeMap()==null)
 			personSearchForm.setAttributeMap(new HashMap<Object,Object>());
 			
 		personSearchForm.getAttributeMap().put("permissionLab",permissionLab);
 		personSearchForm.getAttributeMap().put("permissionMorb",permissionMorb);
 		personSearchForm.getAttributeMap().put("permissionCase",permissionCase);
 		personSearchForm.getAttributeMap().put("permissionInvestigation",permissionInvestigation);
 		
         request.setAttribute("refineSearchHref",
                              "/nbs/" + sCurrTask + ".do?ContextAction=" +
                              tm.get("RefineSearch"));
         request.setAttribute("newSearchHref",
                              "/nbs/" + sCurrTask + ".do?ContextAction=" +
                              tm.get("NewSearch"));
         request.setAttribute("viewHref",
                              "/nbs/" + sCurrTask + ".do?ContextAction=" +
                              tm.get("View"));

         request.setAttribute("viewFileHref",
                              "/nbs/" + sCurrTask + ".do?ContextAction=" +
                              tm.get("ViewFile"));
         
         request.setAttribute("addPatHref",
                 "/nbs/" + sCurrTask + ".do?ContextAction=Add");

         // View File Link permission
         if (! (secObj.getPermission(NBSBOLookup.PATIENT,
                                     NBSOperationLookup.VIEWWORKUP))) {
            request.setAttribute("viewFileHref", "");

         }

         request.setAttribute("viewHref",
                              "/nbs/" + sCurrTask + ".do?ContextAction=" +
                              tm.get("View"));
         request.setAttribute("nextHref",
                              "/nbs/" + sCurrTask + ".do?ContextAction=" +
                              tm.get("Next"));
         request.setAttribute("prevHref",
                              "/nbs/" + sCurrTask + ".do?ContextAction=" +
                              tm.get("Prev"));

         // retrieve the from index if there is one
         try {
            request.setAttribute("DSFromIndex",
                                 NBSContext.retrieve(session, "DSFromIndex"));

         }
         catch (Exception e) {
         }

         if(!homePageSrch) 
        	 request.setAttribute("DSSearchResults", NBSContext.retrieve(session, "DSSearchResults"));
         else {        	 
        	 request.setAttribute("DSSearchResults", session.getAttribute("DSSearchResults"));        	 
         }

      }
      /*********************************************************
       * ENTITY SEARCH
       */
      else if (contextAction.equalsIgnoreCase("EntitySearch")) {

         request.setAttribute("refineSearchHref",
             "/nbs/LoadFindPatient3.do?ContextAction=EntitySearch");
         request.setAttribute("newSearchHref",
             "/nbs/LoadFindPatient3.do?ContextAction=EntitySearch&mode=new");
         request.setAttribute("nextHref",
             "/nbs/FindPatient3.do?ContextAction=EntitySearch&mode=Next");
         request.setAttribute("prevHref",
             "/nbs/FindPatient3.do?ContextAction=EntitySearch&mode=Next");

         request.setAttribute("DSSearchResults",
                              NBSContext.retrieve(session, "DSSearchResults"));        
      }
      String scString ="";
      try {
    	  
    	  if(!homePageSrch && psVO==null) 
        	  psVO = (PatientSearchVO) NBSContext.retrieve(session,"DSSearchCriteria");
          else if(psVO==null) {
        	  psVO = (PatientSearchVO) session.getAttribute("DSSearchCriteria");        	 
          }    	  
       //  scString = PersonUtil.buildSearchCriteriaString(psVO);
    	
         if(!customQueue){//From custom queue
    		 scString = PersonUtil.buildSearchCriteriaString(psVO);
    		 request.getSession().setAttribute("searchCriteriaDesc", scString);
         }
         request.setAttribute("DSSearchCriteriaString", scString);
      }
      catch (Exception e) {
    	  logger.warn("PersonSearchResults search criteria missing");
         session.setAttribute("error",
                              "DSSearchCriteria not available in Object Store");
         e.printStackTrace();
      }
      if(("ReturnToSearchResults").equalsIgnoreCase(contextAction) || ("Cancel").equalsIgnoreCase(contextAction)){
  		 ArrayList<Object> list = (ArrayList<Object> )NBSContext.retrieve(request.getSession() ,NBSConstantUtil.DSPersonList);
  		 request.setAttribute("personList", list);
  		 request.setAttribute("queueCount", list.size());
  		 personSearchForm.getAttributeMap().put("queueCount", list.size());
  		 personSearchForm.clearSelections(); 
  	 }
      if(contextAction.equalsIgnoreCase("EntitySearch"))
    	  return mapping.findForward("XSP");
      else{
    	  
    	  try {
    		  personSearchForm.setAttributeMap((Map<Object,Object>)NBSContext.retrieve(request.getSession(), NBSConstantUtil.DSAttributeMap));
    	  } catch(Exception ex) {
    		  logger.debug (NBSConstantUtil.DSAttributeMap +" is not in contect store Patient Search Results Load");
    	  }
    	  
    	  personSearchForm.getAttributeMap().put("DSSearchCriteriaString", scString);
    	  if(personSearchForm.getSearchCriteriaArrayMap().size() == 0)
				request.setAttribute("ActionMode", HTMLEncoder.encodeHtml("InitLoad"));
    	//  personSearchForm.getPatientVoCollection();
    	 // personSearchForm.initializeDropDowns();
			
    		//Put the pagination, Because return from different form.. form will lose pagination AttributeMap. 
    		// due to this reason we put into session and take it back in pagination.
    		 String pageParam = new ParamEncoder("searchResultsTable").encodeParameterName(TableTagParameters.PARAMETER_PAGE);
    		 if(pageParam != null && request.getParameter(pageParam) != null) {
    			String pageNo = request.getParameter(pageParam);
    			personSearchForm.getAttributeMap().put("PageNumber", pageNo);
    			
    		 }else{
    			 if(session.getAttribute("personPageNo")!=null){
    				 String pageNumber ="1";
    				 pageNumber = (String)session.getAttribute("personPageNo");
    				 session.removeAttribute("personPageNo");
    				 personSearchForm.getAttributeMap().put("PageNumber", pageNumber); 
    			 }    			
    		 }
    	  return PaginationUtil.personPaginate(personSearchForm, request, "searchResultLoad",mapping);
      }
      
   }

   
} //PersonSearchResultsLoad
