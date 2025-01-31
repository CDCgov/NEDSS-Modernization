package gov.cdc.nedss.webapp.nbs.action.notification;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
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
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;

import gov.cdc.nedss.exception.NEDSSAppConcurrentDataException;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.InvestigationSummaryVO;
import gov.cdc.nedss.proxy.ejb.notificationproxyejb.vo.NotificationSummaryVO;
import gov.cdc.nedss.proxy.ejb.notificationproxyejb.vo.UpdatedNotificationSummaryVO;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.nbssecurity.ProgramAreaJurisdictionUtil;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.HTMLEncoder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.action.notification.util.UpdatedNotificationsQueueUtil;
import gov.cdc.nedss.webapp.nbs.action.util.DSQueueObject;
import gov.cdc.nedss.webapp.nbs.action.util.PaginationUtil;
import gov.cdc.nedss.webapp.nbs.form.notification.UpdatedNotificationsQueueForm;

public class UpdatedNotifications extends DispatchAction 
{
	static final LogUtils logger = new LogUtils(UpdatedNotifications.class.getName());
	protected InvestigationSummaryVO invSumVO = null;
	protected NotificationSummaryVO notSumVO = null;

	/**
	 * Retrieve all the updated notifications in the system.
	 * @param mapping
	 * @param aForm
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
    @SuppressWarnings("unchecked")
	public ActionForward loadQueue(ActionMapping mapping, ActionForm aForm, HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException
    {
		UpdatedNotificationsQueueForm updatedNotificationsForm = (UpdatedNotificationsQueueForm) aForm;
		ArrayList<Object> updatedNotifications = new ArrayList<Object> ();
		boolean forceEJBcall = false;
		
		// handle context action parameter
		String contextAction = request.getParameter("ContextAction");
		if (contextAction == null) {
			contextAction = (String) request.getAttribute("ContextAction");
		}
		if (contextAction != null) 
		{
			if (contextAction.equals("NNDUpdatedNotificationsAudit")) {
	        	DSQueueObject dSQueueObject = new DSQueueObject();
	        	dSQueueObject.setDSSortColumn("getCaseClassCdTxt");
	        	dSQueueObject.setDSSortDirection("true");
	        	dSQueueObject.setDSFromIndex("0");
	        	dSQueueObject.setDSQueueType(NEDSSConstants.NND_UPDATED_NOTIFICATIONS_FOR_APPROVAL);
				NBSContext.store(request.getSession(), "DSQueueObject", dSQueueObject);
				
			} else if(contextAction.equals("ReturnToReviewUpdatedNotifications")) {
				forceEJBcall = true;
			}	
		}

		// handle initLoad attribute and reset pagination for first time load
		boolean initLoad = request.getParameter("initLoad") == null ? false: 
			Boolean.valueOf(request.getParameter("initLoad")).booleanValue();
		if (initLoad && !PaginationUtil._dtagAccessed(request)) 
		{
			forceEJBcall = true;

			// reset the form only when the context is not one of the conditions below.
			if  (! contextAction.equalsIgnoreCase("ReturnToReviewUpdatedNotifications") && 
					! contextAction.equals("Delete") && ! contextAction.equals("Submit")) {
				updatedNotificationsForm.clearAll();
			}
		}
		
		try {
			if(updatedNotificationsForm.getSearchCriteriaArrayMap().size() == 0) {
				request.setAttribute("ActionMode", HTMLEncoder.encodeHtml("InitLoad"));
			}
			
			// TODO: check NBS security permissions
	
			// get the list of updated notifications
			if(forceEJBcall) {
	    		MainSessionHolder mainSessionHolder = new MainSessionHolder();
	            MainSessionCommand msCommand = mainSessionHolder.getMainSessionCommand(request.getSession());
	            String  sBeanJndiName = JNDINames.NOTIFICATION_PROXY_EJB;
	            String  sMethod = "getUpdatedNotificationsForAudit";
	            ArrayList<Object> arr = (ArrayList<Object> )msCommand.processRequest(sBeanJndiName, sMethod, null);
	            updatedNotifications = (ArrayList<Object> )arr.get(0);
	            NBSContext.store(request.getSession(),"DSUpdatedNotificationList",updatedNotifications);
	            NBSContext.store(request.getSession(),"DSUpdatedNotificationListFull",updatedNotifications);
			}
			else {
				updatedNotifications = (ArrayList<Object> ) NBSContext.retrieve(request.getSession(),"DSUpdatedNotificationList");
	    	}
			
			// decorate condition, patient links, case status and recipient 
			// properties in 'updatedNotifications VOs'
			TreeMap tm = NBSContext.getPageContext(request.getSession(), "PS230", contextAction);
			NBSContext.lookInsideTreeMap(tm);
			String sCurrTask = NBSContext.getCurrentTask(request.getSession());
			request.setAttribute("ViewInvestigationHref","/nbs/" + sCurrTask + ".do?ContextAction=" + tm.get("ViewInvestigation"));
			request.setAttribute("ViewFileHref", "/nbs/" + sCurrTask + ".do?ContextAction=" + tm.get("ViewFile"));
			this.decorateProperties(updatedNotifications, request);
			
	        // set the drop down options used to filter records in updated notifications queue
	        updatedNotificationsForm.initializeDropDowns(updatedNotifications);
	        
	        // filter and sort the notifications in certain scenarios like 'return from investigation' etc...
	        boolean existing = request.getParameter("existing") == null ? false : true;
	        if (contextAction != null && 
	        		(contextAction.equalsIgnoreCase("ReturnToReviewUpdatedNotifications") 
	        				|| contextAction.equals("Delete") || contextAction.equals("Submit"))) 
	        {
				updatedNotifications = (ArrayList<Object> ) filterNotificationsHelper(updatedNotificationsForm, request);
				if(updatedNotifications != null) {
					sortNotificationsHelper(updatedNotificationsForm, updatedNotifications, true, request);
				}
			} else {
				sortNotificationsHelper(updatedNotificationsForm, updatedNotifications, existing, request);
				
				if(!existing) {
					this.decorateProperties(updatedNotifications, request);
				} else
					filterNotificationsHelper(updatedNotificationsForm, request);
			}
	        
	        // FIXME: handle all properties 
	        // update the possible filter item counts property for each criteria in the attribute map
	        updatedNotificationsForm.getAttributeMap().put(NEDSSConstants.RESULTEDTEST_COUNT,
	        		new Integer(updatedNotificationsForm.getConditionOptions().size()));
	        updatedNotificationsForm.getAttributeMap().put(NEDSSConstants.JURISDICTIONS_COUNT,
	        		new Integer(updatedNotificationsForm.getJurisdictionOptions().size()));
	        updatedNotificationsForm.getAttributeMap().put(NEDSSConstants.CASE_STATUS_FILTER_ITEMS_COUNT,
	        		new Integer(updatedNotificationsForm.getCaseStatusOptions().size()));
	        updatedNotificationsForm.getAttributeMap().put(NEDSSConstants.SUBMITTED_BY_FILTER_ITEMS_COUNT,
	        		new Integer(updatedNotificationsForm.getSubmittedByOptions().size()));
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error(
				"Error in ReviewUpdatedNotificationLoad in getting updated notifications for audit from EJB");
		}
		
        // set the number of records to be displayed per page
		PropertyUtil properties = PropertyUtil.getInstance();
		Integer queueSize = properties.getQueueSize(NEDSSConstants.UPD_QUEUE_FOR_NOTIF_DISP_SIZE);
		request.setAttribute("maxRowCount", queueSize);
        
		// set the notifications in request for use in JSP
        request.setAttribute("updatedNotifications", updatedNotifications);
		
		// set page title in request
		request.setAttribute("PageTitle","Updated Notifications Queue");
		
		// set the total # of results for display
		request.setAttribute("queueCount", String.valueOf(updatedNotifications.size()));
		
		// set the form in session
		request.getSession().setAttribute("updatedNotificationsQueueForm", updatedNotificationsForm);
		NBSContext.store(request.getSession(), "DSUpdatedNotificationList",updatedNotifications);
		return PaginationUtil.paginate(updatedNotificationsForm, request, "viewNotifications",mapping);
	}
    
    /**
     * Remove notification items selected from the queue and direct the users to the updated queue.
     * @param mapping
     * @param aForm
     * @param request
     * @param response
     * @return
     * @throws IOException
     * @throws ServletException
     */
    public ActionForward removeNotifications(ActionMapping mapping, ActionForm aForm, HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException
    {
    	MainSessionCommand msCommand = null;
		MainSessionHolder mainSessionHolder = new MainSessionHolder();
		UpdatedNotificationSummaryVO notSumVO = null;
		ArrayList<Object> removedUpdatedNotificationUids = new ArrayList<Object> ();

		try {
			ArrayList<Object> updatedNotifications = (ArrayList<Object> )NBSContext.retrieve(request.getSession(), 
					"DSUpdatedNotificationList");
			logger.info( "# of updated notifications: " + updatedNotifications.size());

			// create a list of notification UIDs to be removed
			for (int i = 0; i < updatedNotifications.size(); i++) {
				notSumVO = (UpdatedNotificationSummaryVO) updatedNotifications.get(i);
				   String flag = request.getParameter("isRemoved-" +
				   		notSumVO.getNotificationUid().toString() + "_" +
				   		notSumVO.getVersionCtrlNbr().toString());
				   if(flag != null)
				   {
						removedUpdatedNotificationUids.add(notSumVO);
         		   }
			}

			// remove items from the notifications queue
			String sBeanJndiName = JNDINames.NOTIFICATION_PROXY_EJB;
			String sMethod = "removeUpdatedNotifications";
			Object[] oParams = new Object[] { removedUpdatedNotificationUids};
			msCommand = mainSessionHolder.getMainSessionCommand(request.getSession());
			ArrayList<Object> arr = (ArrayList<Object> ) msCommand.processRequest(sBeanJndiName, sMethod, oParams);
			logger.info("# of items retrived from backend = " + arr.size());
		} 
		catch (NEDSSAppConcurrentDataException e) {
			logger.fatal("ERROR - NEDSSAppConcurrentDataException, " +
					"The data has been modified by another user, please recheck! ", e);
			return mapping.findForward("dataerror");
		}
		catch (Exception e) {
			logger.error("Problem happened in the UpdatedNotifications " +
					"struts class", e);
			e.printStackTrace();
			throw new ServletException("Problem happened in the UpdatedNotifications " +
					"struts class:" + e.getMessage(), e);
		}

		return mapping.findForward("removedNotifications");
    }
    
    /**
     * Apply the search filter(s) to the currently displayed list of notifications and return the
     * result
     * @param mapping
     * @param aForm
     * @param request
     * @param response
     * @return
     * @throws IOException
     * @throws ServletException
     */
    @SuppressWarnings("unchecked")
	public ActionForward filterNotifications(ActionMapping mapping, ActionForm aForm, HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException
    {
    	UpdatedNotificationsQueueForm notificationsForm = (UpdatedNotificationsQueueForm) aForm;
    	
    	// apply search filters
		Collection<Object>  notificationVOs = filterNotificationsHelper(notificationsForm, request);
		
		// update attribute map
		if (notificationVOs != null) {
			request.setAttribute("ActionMode", HTMLEncoder.encodeHtml("Search"));;
		} else {
			try {
			notificationVOs = (ArrayList<Object> ) NBSContext.retrieve(request.getSession(), 
					"DSUpdatedNotificationListFull");
			}
			catch(Exception ex) {
				logger.debug("DSUpdatedNotificationListFull is null in Program Area Load");
				notificationVOs = (ArrayList<Object> ) NBSContext.retrieve(request.getSession(true), "DSUpdatedNotificationList");
			}
		}
				
		// sort the results
		sortNotificationsHelper(notificationsForm, notificationVOs, true, request);
		
        // set the number of records to be displayed per page
		PropertyUtil properties = PropertyUtil.getInstance();
		Integer queueSize = properties.getQueueSize(NEDSSConstants.UPD_QUEUE_FOR_NOTIF_DISP_SIZE);
		request.setAttribute("maxRowCount", queueSize);
		
		// set the results in scope
		request.setAttribute("updatedNotifications", notificationVOs);
		request.setAttribute("PageTitle", "Updated Notifications Queue");
		request.setAttribute("queueCount", String.valueOf(notificationVOs.size()));
		
		return PaginationUtil.paginate(notificationsForm, request, "viewNotifications",mapping);
    }
    
    /**
     * Navigate to external pages from the 'Updated Notifications' list page.
     * @param mapping
     * @param aForm
     * @param request
     * @param response
     * @return
     * @throws IOException
     * @throws ServletException
     */
    public ActionForward externalNavigation(ActionMapping mapping, ActionForm aForm, HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException
    {
    	HttpSession session = request.getSession();
		String contextAction = request.getParameter("ContextAction");
    	String currentIndex = request.getParameter("currentIndex");
    	String sortColumn = request.getParameter("sortMethod");
    	String sortDirection = request.getParameter("direction");
    	DSQueueObject dsQueueObject = (DSQueueObject)NBSContext.retrieve(session, "DSQueueObject");
    	String investigationID = request.getParameter("publicHealthCaseUID");
    	NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session.getAttribute("NBSSecurityObject");

        if (contextAction == null) {
        	contextAction = (String)request.getAttribute("ContextAction");
        }

    	if (currentIndex == null) {
    		currentIndex = (String)request.getAttribute("currentIndex");
    	}
    	else if (currentIndex != null) {
    		dsQueueObject.setDSFromIndex(currentIndex);
    	}
    		
        if (sortColumn != null) {
        	dsQueueObject.setDSSortColumn(sortColumn);
        }
        
        if (sortDirection !=null) {
        	dsQueueObject.setDSSortDirection(sortDirection);
        }
        	
		if (!nbsSecurityObj.getPermission(NBSBOLookup.NOTIFICATION, NBSOperationLookup.REVIEW,
				ProgramAreaJurisdictionUtil.ANY_PROGRAM_AREA, ProgramAreaJurisdictionUtil.ANY_JURISDICTION))
		{
			logger.fatal( "Error: no permisstion to review notification, go back to login screen");
			throw new ServletException("Error: no permisstion to review notification, go back to login screen");
		}

		try {
			if (contextAction != null && contextAction.equals("ViewInvestigation")) {
				NBSContext.store(session, NBSConstantUtil.DSInvestigationUid, investigationID);
				return mapping.findForward(contextAction);
			} else {
				logger.info("Nothing matched. Simply forwarding on using ContextAction");
				return mapping.findForward(contextAction);
			}
		} catch (Exception e) {
			logger.error("Problem happened in the ReviewUpdatedNotificationSubmit struts class", e);
			e.printStackTrace();
			throw new ServletException("Problem happened in the ReviewUpdatedNotificationSubmit " +
					"struts class:" + e.getMessage(), e);
		}
    }
    
    /**
     * Helper method to filter a list of notifications based on a given
     * search/filter criteria
     * @param obsNeedRevForm
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
	private Collection<Object>  filterNotificationsHelper(UpdatedNotificationsQueueForm notificationsForm,
    		HttpServletRequest request) 
    {
    	Collection<Object>  filteredNotifications = new ArrayList<Object> ();
		UpdatedNotificationsQueueUtil notificationsQueueUtil = new UpdatedNotificationsQueueUtil();
		String sortOrderParam = null;
		
		try {
			Map searchCriteriaMap = notificationsForm.getSearchCriteriaArrayMap();

			// get all the existing notifications displayed to user
			ArrayList<Object> currentNotifications = null;
			try {
			 currentNotifications = (ArrayList<Object> ) NBSContext.retrieve(request.getSession(), 
					"DSUpdatedNotificationListFull");
			}
			catch(Exception ex) {
				logger.debug("DSUpdatedNotificationListFull is null in Program Area Load filterInvs");
				currentNotifications = (ArrayList<Object> ) NBSContext.retrieve(request.getSession(true), "DSUpdatedNotificationList");
			}
			
			// filter the results
			filteredNotifications = notificationsQueueUtil.getFilteredNotifications(currentNotifications, 
					searchCriteriaMap);
			NBSContext.store(request.getSession(true), "DSRejectedNotificationList",  filteredNotifications);
			// get selected items in each criteria (selected using multi-select control in JSP)
			String[] conditionFilter = (String[]) searchCriteriaMap.get("CONDITION");
			String[] jurisdictionFilter = (String[]) searchCriteriaMap.get("JURISDICTION");
			String[] caseStatusFilter = (String[]) searchCriteriaMap.get("CASESTATUS");
			String[] submittedByFilter = (String[]) searchCriteriaMap.get("SUBMITTEDBY");
			String[] notificationCodeFilter = (String[]) searchCriteriaMap.get("NOTIFICATIONCODE");
			String[] recipientFilter = (String[]) searchCriteriaMap.get("RECIPIENT");
			String[] updateDateFilter = (String[]) searchCriteriaMap.get("UPDATEDATE");
			String filterPatient = null;
			if(searchCriteriaMap.get("SearchText1_FILTER_TEXT")!=null){
				filterPatient = (String) searchCriteriaMap.get("SearchText1_FILTER_TEXT");
				request.setAttribute("PATIENT", filterPatient);
			}
			
			// get the # of selected items in each criteria
			Integer conditionFilterCount = new Integer(conditionFilter == null  ? 0 : conditionFilter.length);
			Integer jurisdictionFilterCount = new Integer(jurisdictionFilter == null  ? 0 : jurisdictionFilter.length);
			Integer caseStatusFilterCount = new Integer(caseStatusFilter == null  ? 0 : caseStatusFilter.length);
			Integer submittedByFilterCount = new Integer(submittedByFilter == null  ? 0 : submittedByFilter.length);
			Integer notificationCodeFilterCount = new Integer(notificationCodeFilter == null  ? 0 : notificationCodeFilter.length);
			Integer recipientFilterCount = new Integer(recipientFilter == null  ? 0 : recipientFilter.length);
			Integer updateDateFilterCount = new Integer(updateDateFilter == null  ? 0 : updateDateFilter.length);
			
			// perform actions if all items/option in all search criterias are selected
			if((conditionFilterCount.equals(notificationsForm.getAttributeMap().get(NEDSSConstants.RESULTEDTEST_COUNT))) &&
					(jurisdictionFilterCount.equals(notificationsForm.getAttributeMap().get(NEDSSConstants.JURISDICTIONS_COUNT))) &&
					(caseStatusFilterCount.equals(notificationsForm.getAttributeMap().get(NEDSSConstants.CASE_STATUS_FILTER_ITEMS_COUNT))) &&
					(submittedByFilterCount.equals(notificationsForm.getAttributeMap().get(NEDSSConstants.SUBMITTED_BY_FILTER_ITEMS_COUNT))) && 
					(notificationCodeFilterCount.equals(notificationsForm.getAttributeMap().get(NEDSSConstants.NOTIFICATION_CODE_FILTER_ITEMS_COUNT))) &&
					(recipientFilterCount.equals(notificationsForm.getAttributeMap().get(NEDSSConstants.RECIPIENT_FILTER_ITEMS_COUNT))) &&
					(updateDateFilterCount.equals(notificationsForm.getAttributeMap().get(NEDSSConstants.UPDATE_DATE_FILTER_ITEMS_COUNT)))&&
					filterPatient == null)
			{
				String sortMethod = getSortMethod(request, notificationsForm);
				String direction = getSortDirection(request, notificationsForm);			
				if(sortMethod == null || (sortMethod != null && sortMethod.equals("none"))) {
					Map sColl =  notificationsForm.getAttributeMap().get("searchCriteria") == null ? 
							new TreeMap() : (TreeMap) notificationsForm.getAttributeMap().get("searchCriteria");
					sortOrderParam = sColl.get("sortSt") == null ? "" : (String) sColl.get("sortSt");
				} else {
					sortOrderParam = notificationsQueueUtil.getSortCriteriaDescription(direction, sortMethod);
				}				
				Map searchCriteriaColl = new TreeMap();
				searchCriteriaColl.put("sortSt", sortOrderParam);
				notificationsForm.getAttributeMap().put("searchCriteria", searchCriteriaColl);			
				return null;				
			}
			
			// perform actions if 1 or more items (but not all) are selected in 1 or more search criteria
			ArrayList<Object> availableConditions = notificationsForm.getConditionOptions();
			ArrayList<Object> availableJurisdictions = notificationsForm.getJurisdictionOptions();
			ArrayList<Object> availableCaseStatuses = notificationsForm.getCaseStatusOptions();
			ArrayList<Object> availableSubmittedBys = notificationsForm.getSubmittedByOptions();
			ArrayList<Object> availableNotificationCodes = notificationsForm.getNotificationCodeOptions();
			ArrayList<Object> availableRecipients = notificationsForm.getRecipientOptions();
			ArrayList<Object> availableUpdateDates = notificationsForm.getUpdateDateOptions();
			
			Map searchCriteriaColl = new TreeMap();
			String sortMethod = getSortMethod(request, notificationsForm);
			String direction = getSortDirection(request, notificationsForm);			
			if(sortMethod == null || (sortMethod != null && sortMethod.equals("none"))) {
				Map sColl =  notificationsForm.getAttributeMap().get("searchCriteria") == null ? 
						new TreeMap() : (TreeMap) notificationsForm.getAttributeMap().get("searchCriteria");
				sortOrderParam = sColl.get("sortSt") == null ? "" : (String) sColl.get("sortSt");
			} else {
				sortOrderParam = notificationsQueueUtil.getSortCriteriaDescription(direction, sortMethod);
			}
			
			String srchCriteriaConditions = notificationsQueueUtil.getSearchCriteria(
					availableConditions, conditionFilter, NEDSSConstants.FILTERBYCONDITION);
			String srchCriteriaCaseStatuses = notificationsQueueUtil.getSearchCriteria(
					availableCaseStatuses, caseStatusFilter, NEDSSConstants.FILTERBYSTATUS);
			String srchCriteriaSubmittedBys = notificationsQueueUtil.getSearchCriteria(
					availableSubmittedBys, submittedByFilter, NEDSSConstants.FILTERBYSUBMITTEDBY);
			String srchCriteriaNotificationCodes = notificationsQueueUtil.getSearchCriteria(
					availableNotificationCodes, notificationCodeFilter, NEDSSConstants.FILTERBYNOTIF);
			String srchCriteriaRecipients = notificationsQueueUtil.getSearchCriteria(
					availableRecipients, recipientFilter, NEDSSConstants.FILTERBYRECIPIENT);
			String srchCriteriaUpdateDate = notificationsQueueUtil.getSearchCriteria(
					availableUpdateDates, updateDateFilter, NEDSSConstants.FILTERBYUPDATEDATE);
			
			// set the error message to the form
			if(sortOrderParam != null)
				searchCriteriaColl.put("sortSt", sortOrderParam);
			if(srchCriteriaUpdateDate != null)
				searchCriteriaColl.put("INV147", srchCriteriaUpdateDate);
			if(srchCriteriaSubmittedBys != null)
				searchCriteriaColl.put("DEM102", srchCriteriaSubmittedBys);
			if(srchCriteriaRecipients != null)
				searchCriteriaColl.put("NOT119", srchCriteriaRecipients);
			if(srchCriteriaNotificationCodes != null)
				searchCriteriaColl.put("NOT118", srchCriteriaNotificationCodes);
			if(srchCriteriaConditions != null)
				searchCriteriaColl.put("INV169", srchCriteriaConditions);
			if(srchCriteriaCaseStatuses != null)
				searchCriteriaColl.put("INV163", srchCriteriaCaseStatuses);
			if(filterPatient != null)
				searchCriteriaColl.put("PATIENT", filterPatient);
			
			notificationsForm.getAttributeMap().put("searchCriteria", searchCriteriaColl);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while filtering the investigation by Investigator: "+ e.toString());
			
		} 
		return filteredNotifications;
	}
    
    /**
     * Helper method to sort the notifications based on sort method and direction.
     * @param notificationsForm
     * @param notificationVOs
     * @param existing
     * @param request
     */
    private void sortNotificationsHelper(UpdatedNotificationsQueueForm notificationsForm, Collection<Object>  notificationVOs, 
    		boolean existing, HttpServletRequest request) 
    {
    	UpdatedNotificationsQueueUtil notificationsQueueUtil = new UpdatedNotificationsQueueUtil();
		String sortMethod = getSortMethod(request, notificationsForm);
		String direction = getSortDirection(request, notificationsForm);

		boolean invDirectionFlag = true;
		if (direction != null && direction.equals("2")) {
			invDirectionFlag = false;
		}

		//Read from properties file to determine default sort order
		if (sortMethod == null || (sortMethod != null && sortMethod.equals("none"))) {
			sortMethod = "getAddTime";
			invDirectionFlag = true;
		}
		
		NedssUtils util = new NedssUtils();
		if (sortMethod != null && notificationVOs != null && notificationVOs.size() > 0) {
			util.sortObjectByColumnGeneric(sortMethod, (Collection<Object>) notificationVOs, invDirectionFlag);
		}
		
		if(!existing) {
			//Finally put sort criteria in form
			String sortOrderParam = 
				notificationsQueueUtil.getSortCriteriaDescription(invDirectionFlag == true 
						? "1" : "2", sortMethod);
			Map searchCriteriaColl = new TreeMap();
			searchCriteriaColl.put("sortSt", sortOrderParam);
			notificationsForm.getAttributeMap().put("searchCriteria", searchCriteriaColl);
		}
	}
    
    /**
     * Helper method to get the sort method as a string.
     * @param request
     * @param notificationsForm
     * @return
     */
	private String getSortMethod(HttpServletRequest request, UpdatedNotificationsQueueForm notificationsForm) {
		if (PaginationUtil._dtagAccessed(request)) {
			return request.getParameter((new ParamEncoder("parent")).encodeParameterName(TableTagParameters.PARAMETER_SORT));
		} else{
			return notificationsForm.getAttributeMap().get("methodName") == null ? 
					null : (String) notificationsForm.getAttributeMap().get("methodName");
		}
	}

	/**
	 * Helper method to get the sort direction as a string.
	 * @param request
	 * @param notificationsForm
	 * @return
	 */
	private String getSortDirection(HttpServletRequest request, UpdatedNotificationsQueueForm notificationsForm) {
		if (PaginationUtil._dtagAccessed(request)) {
			return request.getParameter((new ParamEncoder("parent")).encodeParameterName(TableTagParameters.PARAMETER_ORDER));
		} else{
			return notificationsForm.getAttributeMap().get("sortOrder") == null ? 
					"1": (String) notificationsForm.getAttributeMap().get("sortOrder");
		}
	}

	/**
	 * Helper method to decorate the properties of certain VO properties for display purposes.
	 * @param updatedNotificationsForAudit
	 * @param request
	 */
	private void decorateProperties(ArrayList<Object>  updatedNotificationsForAudit, HttpServletRequest request)
	{
		Iterator iter = updatedNotificationsForAudit.iterator();
		while (iter.hasNext()) {
			UpdatedNotificationSummaryVO updated = (UpdatedNotificationSummaryVO) iter.next();
			
			// condition link - create a link to the investigation
			String viewInvestigationHref= (String)request.getAttribute("ViewInvestigationHref");
			String phcUid = String.valueOf(updated.getPublicHealthCaseUid());
			String condLink = "<a href=\"#\" onclick=\"javascript:loadInvestigation(\'" + viewInvestigationHref + 
				"&method=externalNavigation&publicHealthCaseUID="+ phcUid + "\'"+")"+"\" >"+ updated.getCdTxt() + "</a>";
			updated.setConditionLink(condLink);
			
			// patient link - create a link to the patient's file
			String patFullName = getPatientFullName(updated);
			updated.setPatientFullName(patFullName);
			String viewFileHref = request.getAttribute("ViewFileHref") == null ? "" : (String) request.getAttribute("ViewFileHref");
			if (!viewFileHref.equals(""))
			{
				String patLink = "<a href=\"" + viewFileHref  +"&MPRUid="+ String.valueOf(1) + "\">" + patFullName + "</a>";
				updated.setPatientFullNameLnk(patLink);
			}
			
			// case status - color code certain values of case status
			if (updated.getCaseClassCdTxt() != null) {
				if (updated.getCaseClassCdTxt().trim().length() > 0) {
					if (updated.getCaseClassCdTxt().indexOf("*") >= 0) {
						String decoratedText = "<span style=\"font-weight:bold;color:#CC0000;\">" + updated.getCaseClassCdTxt() + "</span>";
						updated.setDecoratedCaseClassCdTxt(decoratedText);
					}
					else {
						updated.setDecoratedCaseClassCdTxt(updated.getCaseClassCdTxt());
					}
				}
				else {
					updated.setDecoratedCaseClassCdTxt("");
				}
			}
			else {
				updated.setDecoratedCaseClassCdTxt("");
			}
			
			// recipient - if recipient is null or blank, use 'CDC' as the default recipient
			if (updated.getRecipient() == null || 
					(updated.getRecipient() != null && updated.getRecipient().trim().equals(""))) {
					if(updated.getNndInd()!=null && updated.getNndInd().equals(NEDSSConstants.YES))
						updated.setRecipient(NEDSSConstants.ADMINFLAGCDC);
	            	else
	            		updated.setRecipient(NEDSSConstants.LOCAl_DESC);
			}
		}
	}
	
	/** Create a string that represents the full name of the patient in the form
	 * 'Last Name, First Name'.
	 * @param notification
	 * @return
	 */
    private String getPatientFullName(UpdatedNotificationSummaryVO notification)
    {
    	StringBuffer buff = new StringBuffer();
    	if ( notification.getLastNm() != null && notification.getLastNm().trim() != "") {
    		buff.append(notification.getLastNm().trim());
    	}
    		
    	if (notification.getLastNm() != null
    			&& notification.getFirstNm() != null
    			&& notification.getLastNm().trim() != ""
    			&& notification.getFirstNm().trim() != "") {
    		buff.append(",   ");
    	}
    		
    	if (notification.getFirstNm() != null && notification.getFirstNm().trim() != "") {
    		buff.append(notification.getFirstNm().trim());
    	}
    		
    	String patientFullName = buff.toString();
    	return patientFullName;
    }
}