package gov.cdc.nedss.webapp.nbs.action.epilink;

import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.proxy.ejb.epilink.dt.EpilinkDT;
import gov.cdc.nedss.proxy.ejb.epilink.vo.EpilinkSummaryDisplayVO;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.HTMLEncoder;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.action.alert.AlertAdminConstants;
import gov.cdc.nedss.webapp.nbs.action.decisionsupportmanagement.util.DsmActivityLogUtil;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.DynamicPageConstants;
import gov.cdc.nedss.webapp.nbs.action.util.PaginationUtil;
import gov.cdc.nedss.webapp.nbs.action.util.QueueUtil;
import gov.cdc.nedss.webapp.nbs.form.decisionsupportmanagement.DsmActivityLogForm;
import gov.cdc.nedss.webapp.nbs.form.epilink.EpiLinkLogForm;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;

public class EpiLinkLogAdministrationAction extends DispatchAction{
	
	static final LogUtils logger = new LogUtils(EpiLinkLogAdministrationAction.class.getName());

	private QueueUtil queueUtil = new QueueUtil();
	private PropertyUtil properties = PropertyUtil.getInstance();
	
	public ActionForward loadActivityLog(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws IOException, ServletException {

		try {
			EpiLinkLogForm epiLinkLogform =(EpiLinkLogForm)form;
			HttpSession session = request.getSession();
		    NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session.getAttribute(
		        "NBSSecurityObject");
		    request.setAttribute(AlertAdminConstants.PAGE_TITLE ,"Manage Epi-Link ID Activity Log");

		    epiLinkLogform.getAttributeMap().put("SEARCHRESULT", null);
		    epiLinkLogform.initDefaultSelections();
		

		} catch (Exception e) {
			logger.error("Error while loading the epilink Activity Log: " + e.toString());
			throw new ServletException("Error while loading the epilink Activity Log: "+e.getMessage(),e);
		}

		
		return (mapping.findForward("manageEpiLinkLog"));
	}
	
	
	@SuppressWarnings("unchecked")
	public ActionForward searchEpilinkLogSubmit(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)throws IOException, ServletException {

		try {
			EpiLinkLogForm epiLinkLogform =(EpiLinkLogForm)form;
			HttpSession session = request.getSession();
		    NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session.getAttribute(
		        "NBSSecurityObject");
		    request.setAttribute(AlertAdminConstants.PAGE_TITLE ,"Manage Epi-Link ID Activity Log");
		    ActionMessages errors = epiLinkLogform.validate(mapping,
					request);
			if (!errors.isEmpty()) {
				epiLinkLogform.getAttributeMap().put("SEARCHRESULT", null);
				request.setAttribute("error_messages", errors);
				return (mapping.findForward("manageEpiLinkLog"));
			}
			
		    boolean forceEJBcall = false;
			boolean initLoad = request.getParameter("initLoad") == null ? false
					: Boolean.valueOf(request.getParameter("initLoad"))
							.booleanValue();
			if (initLoad && !PaginationUtil._dtagAccessed(request)) {
				epiLinkLogform.clearAll();
				forceEJBcall = true;
				Integer queueSize = properties
						.getQueueSize(NEDSSConstants.EPILINK_MANAGEMENT_ACTIVITYLOG_LIBRARY_QUEUE_SIZE);
				epiLinkLogform.getAttributeMap().put("queueSize", queueSize);
				
			}

			
			if (epiLinkLogform.getSearchCriteriaArrayMap().size() == 0)
				request.setAttribute("ActionMode", HTMLEncoder.encodeHtml("InitLoad"));
			String actionMd = request.getParameter("actionMode");
			if (epiLinkLogform.getActionMode() == null
					|| (actionMd != null && actionMd.equalsIgnoreCase("Manage"))
					|| epiLinkLogform.getActionMode().equals("Manage"))
				epiLinkLogform.setActionMode(DynamicPageConstants.MANAGE);
			else {
				epiLinkLogform.setActionMode(epiLinkLogform.getActionMode());
			}
			EpilinkSummaryDisplayVO vo = new EpilinkSummaryDisplayVO();
			List<Object> l;
			
			if(forceEJBcall){
			
			
			l = EpilinkLogUtil.retrieveAllEpilinkActivityLogs(new Date(epiLinkLogform.getActivityLogClientVO().getProcessingDateFrom()),new Date(epiLinkLogform.getActivityLogClientVO().getProcessingDateTo()),session);
			 vo.setTestCollection(l);
//			EpilinkSummaryDisplayVO vo = getMockEpilinkSummaryDisplayVO();
			 epiLinkLogform.setEpilinkList(l);
			
			epiLinkLogform.getAttributeMap().put("SEARCHRESULT", "SEARCHRESULT");
			
			epiLinkLogform.setEpilinkSummaryDisplayVO(vo);
			epiLinkLogform.getAttributeMap().put("epilinkList",  vo.getTestCollection());
			logger.debug("searchEpilinkLogSubmit : mock object " + vo.getTestCollection().size());
			request.setAttribute("epilinkList", vo.getTestCollection());
			epiLinkLogform.initializeDropDowns();
			epiLinkLogform.getAttributeMap().put("processedDate",
					new Integer(epiLinkLogform.getProcessedDate().size()));
			epiLinkLogform.getAttributeMap().put("oldEpiLink",
					new Integer(epiLinkLogform.getOldEpiLink().size()));
			epiLinkLogform.getAttributeMap().put("newEpiLink",
					new Integer(epiLinkLogform.getNewEpiLink().size()));
					
		} else {
			l = (ArrayList<Object>) epiLinkLogform
					.getAttributeMap().get("epilinkList");
			 vo.setTestCollection(l);
			request.setAttribute("epilinkList", l);
		}
			if(l != null)epiLinkLogform.getAttributeMap().put("queueCount",String.valueOf(l.size()));
			Map<Object, Object> searchCriteriaColl = new TreeMap<Object, Object>();
			searchCriteriaColl.put("sortSt", "");
			epiLinkLogform.getAttributeMap().put("searchCriteria",
					searchCriteriaColl);
			sortManageLog(epiLinkLogform, l, true, request);
			

		} catch (Exception e) {
			logger.error("Error while loading the user Alert: " + e.toString());
			throw new ServletException("Error while loading the user Alert: "+e.getMessage(),e);
		}

		
		return (mapping.findForward("manageEpiLinkLog"));
	}
	
	         
	public ActionForward filterActivityLogSubmit(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException {

		try {
			EpiLinkLogForm logForm = (EpiLinkLogForm) form;
		Collection<Object> activitylist = filterActivityLogColl(logForm, request);
		
		if (activitylist != null) {
			request.setAttribute("ActionMode", HTMLEncoder.encodeHtml("Search"));
		} 
		
		
		logForm.getAttributeMap().put("epilinkList", activitylist);
		logForm.getAttributeMap().put("queueList", activitylist);
		request.setAttribute("queueList", activitylist);
		
		sortManageLog(logForm, activitylist, true, request);
		
		request.setAttribute("epilinkList", activitylist);
		logForm.getAttributeMap().put("queueCount",
				String.valueOf(activitylist.size()));
		request.setAttribute("queueCount",
				String.valueOf(activitylist.size()));
		
			request.setAttribute("PageTitle",
					"Manage ELR Activity Log");
		
		logForm.getAttributeMap().put("PageNumber", "1");

		return PaginationUtil.paginate(logForm, request, "manageEpiLinkLog", mapping);
		} catch (Exception e) {
			logger.error("Exception in filterActivityLogSubmit: "
					+ e.toString(),e);
			throw new ServletException("Error while filtering Epilink Activity Logs: "+e.getMessage(),e);
		} 
	}

	
	private Collection<Object> filterActivityLogColl(
			EpiLinkLogForm logForm, HttpServletRequest request)
			throws Exception {

		Collection<Object> activitylist = new ArrayList<Object>();

		String srchCriteriaProcessedDate = null;
		String srchCriteriaOldEpiLink = null;
		String srchCriteriaNewEpiLink = null;
		   
		try {
			Map<Object, Object> searchCriteriaMap = logForm
					.getSearchCriteriaArrayMap();
			@SuppressWarnings("unchecked")
//			ArrayList<Object> activityLogColl = (ArrayList<Object>) logForm.getAttributeMap().get("epilinkList");
			ArrayList<Object> activityLogColl =(ArrayList<Object>) logForm.getEpilinkList();
			activitylist = getFilteredActivityLogLib(activityLogColl,
					searchCriteriaMap);

			String[] processedDate = (String[]) searchCriteriaMap
					.get("PROCESSEDDATE");
			String[] oldEpiLink = (String[]) searchCriteriaMap.get("OLDEPILINK");
			String[] newEpiLink = (String[]) searchCriteriaMap
					.get("NEWEPILINK");
			
			String filterByProcessedDate = null;
			String filterByUserName = null;
			String filterByOldEpilinkId = null;
			String filterByNewEpilinkId = null;

			String sortOrderParam = null;
			
		
			if(searchCriteriaMap.get("SearchText2_FILTER_TEXT")!=null){
				filterByUserName = (String) searchCriteriaMap.get("SearchText2_FILTER_TEXT");
			}
			
			if(searchCriteriaMap.get("SearchText1_FILTER_TEXT")!=null){
				filterByProcessedDate = (String) searchCriteriaMap.get("SearchText1_FILTER_TEXT");
			}
			
			if(searchCriteriaMap.get("SearchText3_FILTER_TEXT")!=null){
				filterByOldEpilinkId = (String) searchCriteriaMap.get("SearchText3_FILTER_TEXT");
			}
			if(searchCriteriaMap.get("SearchText4_FILTER_TEXT")!=null){
				filterByNewEpilinkId = (String) searchCriteriaMap.get("SearchText4_FILTER_TEXT");
			}
			

			Integer processedDateCount = new Integer(processedDate == null ? 0
					: processedDate.length);
			Integer oldEpiLinkCount = new Integer(oldEpiLink == null ? 0
					: oldEpiLink.length);
			Integer newEpiLinkCount = new Integer(newEpiLink == null ? 0
					: newEpiLink.length);
			
			// Do not filter if the selected values for filter is same as
			// filtered list, but put the sortMethod, direction and criteria
			// stuff
			if (processedDateCount.equals((logForm.getAttributeMap()
					.get("processedDateCount")))
					&& (oldEpiLinkCount.equals(logForm.getAttributeMap().get(
							"oldEpiLinkCount")))
					&& (newEpiLinkCount.equals(logForm.getAttributeMap()
							.get("newEpiLinkCount")))
					&& filterByProcessedDate == null
					&& filterByUserName == null
					&& filterByOldEpilinkId == null
					&& filterByNewEpilinkId == null) {
					

				String sortMethod = getSortMethod(request, logForm, "");
				String direction = getSortDirection(request, logForm, "");
				if (sortMethod == null
						|| (sortMethod != null && sortMethod.equals("none"))) {
					Map<?, ?> sColl = logForm.getAttributeMap().get(
							"searchCriteria") == null ? new TreeMap<Object, Object>()
							: (TreeMap<?, ?>) logForm.getAttributeMap().get(
									"searchCriteria");
					sortOrderParam = sColl.get("sortSt") == null ? ""
							: (String) sColl.get("sortSt");
				} else {
					sortOrderParam = EpilinkLogUtil
							.getSortCriteriaForActivityLog(direction, sortMethod);
				}
				Map<Object, Object> searchCriteriaColl = new TreeMap<Object, Object>();
				searchCriteriaColl.put("sortSt", sortOrderParam);
				logForm.getAttributeMap().put("searchCriteria",
						searchCriteriaColl);
				return null;
			}
			List<Object> processedDateList = logForm.getProcessedDate();
			List<Object> oldEpiLinList = logForm.getOldEpiLink();
			List<Object> newEpiLinkList = logForm.getNewEpiLink();
			

			Map<Object, Object> searchCriteriaColl = new TreeMap<Object, Object>();
			String sortMethod = getSortMethod(request, logForm, "");
			String direction = getSortDirection(request, logForm, "");
			if (sortMethod == null
					|| (sortMethod != null && sortMethod.equals("none"))) {
				Map<Object, Object> sColl = logForm.getAttributeMap().get(
						"searchCriteria") == null ? new TreeMap<Object, Object>()
						: (TreeMap<Object, Object>) logForm.getAttributeMap()
								.get("searchCriteria");
				sortOrderParam = sColl.get("sortSt") == null ? ""
						: (String) sColl.get("sortSt");
			} else {
				sortOrderParam = EpilinkLogUtil.getSortCriteriaForActivityLog(
						direction, sortMethod);
			}

			srchCriteriaProcessedDate = queueUtil.getSearchCriteria(
					(ArrayList)processedDateList, processedDate,
					NEDSSConstants.EPILINKLOGACTIVITY_FILTERBYPROCESSEDDATE);

			srchCriteriaOldEpiLink = queueUtil.getSearchCriteria((ArrayList)oldEpiLinList,
					oldEpiLink, NEDSSConstants.EPILINKLOGACTIVITY_FILTERBYOLDEPILINK);
			srchCriteriaNewEpiLink = queueUtil.getSearchCriteria(
					(ArrayList)newEpiLinkList, newEpiLink,
					NEDSSConstants.EPILINKLOGACTIVITY_FILTERBYNEWEPILINK);
			

			// set the error message to the form
			if (sortOrderParam != null)
				searchCriteriaColl.put("sortSt", sortOrderParam);
			if (srchCriteriaProcessedDate != null)
				searchCriteriaColl.put("EPL102", srchCriteriaProcessedDate);
			if (srchCriteriaOldEpiLink != null)
				searchCriteriaColl.put("EPL103", srchCriteriaOldEpiLink);
			if (srchCriteriaNewEpiLink != null)
				searchCriteriaColl.put("EPL104", srchCriteriaNewEpiLink);
			if (filterByUserName != null)
                searchCriteriaColl.put("EPL105", filterByUserName);
			


			logForm.getAttributeMap().put("searchCriteria", searchCriteriaColl);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while filtering the Epilink activity Log  by  : "
					+ e.toString(),e);
			throw new ServletException("Error  in filterActivityLogColl method: "
					+ e.getMessage());
		}
		return activitylist;
	}
	
	private void sortManageLog(EpiLinkLogForm logForm,
			Collection<Object> activityLogList, boolean existing,
			HttpServletRequest request) throws Exception {

		String sortMethod = getSortMethod(request, logForm, "");
		String direction = getSortDirection(request, logForm, "");

		boolean invDirectionFlag = true;
		if (direction != null && direction.equals("2"))
			invDirectionFlag = false;

		NedssUtils util = new NedssUtils();

		// Read from properties file to determine default sort order
		if (sortMethod == null
				|| (sortMethod != null && sortMethod.equals("none"))) {
			sortMethod = "getProcessedDate";
			invDirectionFlag = false;
		}

		if (sortMethod != null && activityLogList != null
				&& activityLogList.size() > 0) {
//			updateListBeforeSort(activityLogList);
			util.sortObjectByColumn(sortMethod,
					(Collection<Object>) activityLogList, invDirectionFlag);

//			updateListAfterSort(activityLogList);

		}

		if (!existing) {
			// Finally put sort criteria in form
			String sortOrderParam = EpilinkLogUtil
					.getSortCriteriaForActivityLog(invDirectionFlag == true ? "1"
							: "2", sortMethod);
			Map<Object, Object> searchCriteriaColl = new TreeMap<Object, Object>();
			searchCriteriaColl.put("sortSt", sortOrderParam);
			logForm.getAttributeMap().put("searchCriteria", searchCriteriaColl);
		}
	}

	
	private String getSortMethod(HttpServletRequest request,
			EpiLinkLogForm logForm, String sortType) {
		if (PaginationUtil._dtagAccessed(request)) {
			if (sortType != null && sortType.equalsIgnoreCase("Ques"))
				return request
						.getParameter((new ParamEncoder("parent1"))
								.encodeParameterName(TableTagParameters.PARAMETER_SORT));
			else
				return request
						.getParameter((new ParamEncoder("parent"))
								.encodeParameterName(TableTagParameters.PARAMETER_SORT));
		} else {
			return logForm.getAttributeMap().get("methodName") == null ? null
					: (String) logForm.getAttributeMap().get("methodName");
		}
	}

	private String getSortDirection(HttpServletRequest request,
			EpiLinkLogForm logForm, String sortType) {
		if (PaginationUtil._dtagAccessed(request)) {
			if (sortType != null && sortType.equalsIgnoreCase("Ques"))
				return request
						.getParameter((new ParamEncoder("parent1"))
								.encodeParameterName(TableTagParameters.PARAMETER_ORDER));
			else
				return request
						.getParameter((new ParamEncoder("parent"))
								.encodeParameterName(TableTagParameters.PARAMETER_ORDER));
		} else {
			return logForm.getAttributeMap().get("sortOrder") == null ? "1"
					: (String) logForm.getAttributeMap().get("sortOrder");
		}
	}

	
	public Collection<Object> getFilteredActivityLogLib(
			Collection<Object> activityLogColl,
			Map<Object, Object> searchCriteriaMap) {

		String[] processedDate = (String[]) searchCriteriaMap
				.get("PROCESSEDDATE");
		String[] oldEpilink = (String[]) searchCriteriaMap.get("OLDEPILINK");
		String[] newEpilink = (String[]) searchCriteriaMap
				.get("NEWEPILINK");
		String filterByUserName = null;

		if(searchCriteriaMap.get("SearchText2_FILTER_TEXT")!=null){
			filterByUserName = (String) searchCriteriaMap.get("SearchText2_FILTER_TEXT");
		}

		Map<Object, Object> processedDateMap = new HashMap<Object, Object>();
		Map<Object, Object> oldEpilinkMap = new HashMap<Object, Object>();
		Map<Object, Object> newEpilinkMap = new HashMap<Object, Object>();

		try {
			if (processedDate != null && processedDate.length > 0)
				processedDateMap = queueUtil.getMapFromStringArray(processedDate);
			if (oldEpilink != null && oldEpilink.length > 0)
				oldEpilinkMap = queueUtil.getMapFromStringArray(oldEpilink);
			if (newEpilink != null && newEpilink.length > 0)
				newEpilinkMap = queueUtil.getMapFromStringArray(newEpilink);

			if (processedDateMap != null && processedDateMap.size() > 0)

				activityLogColl = filterProcessedTime(activityLogColl,
						processedDateMap);
			if (oldEpilinkMap != null && oldEpilinkMap.size() > 0)
				activityLogColl = filterOldEpilink(activityLogColl, oldEpilinkMap);
			if (newEpilinkMap != null && newEpilinkMap.size() > 0)
				activityLogColl = filterNewEpilink(activityLogColl, newEpilinkMap);

			if(filterByUserName!= null){
				activityLogColl = filterByText(activityLogColl, filterByUserName, NEDSSConstants.FILTERBYUSERNAME);
			}
		} catch (Exception ex) {
			logger.error("Error while getFilteredActivityLogLib: " + ex.toString());
			throw new NEDSSSystemException(ex.getMessage());
		}

		return activityLogColl;
	}
	
	
	public static Collection<Object>  filterByText(
			Collection<Object>  epilinkDTList, String filterByText,String column) {
		Collection<Object>  newTypeColl = new ArrayList<Object> ();
		try{
		if (epilinkDTList != null) {
			Iterator<Object> iter = epilinkDTList.iterator();
			while (iter.hasNext()) {
				EpilinkDT dt = (EpilinkDT) iter.next();
				if(column.equals(NEDSSConstants.FILTERBYUSERNAME) && dt.getName()!= null && dt.getName().toString().toUpperCase().contains(filterByText.toUpperCase())){
					newTypeColl.add(dt);
				}
				
			}
		}
		}catch(Exception ex){
			 logger.error("Error filtering the filterByText : "+ ex.toString(),ex);
			 throw new NEDSSSystemException(ex.getMessage());
		}
		return newTypeColl;
	}



	public Collection<Object> filterProcessedTime(
			Collection<Object> activityLogColl,
			Map<Object, Object> processedTimeMap) {
		Map<Object, Object> newTempMap = new HashMap<Object, Object>();
		String strDateKey = null;
		if (activityLogColl != null) {
			Iterator<Object> iter = activityLogColl.iterator();
			while (iter.hasNext()) {
				EpilinkDT dt = (EpilinkDT) iter.next();
				if (dt.getProcessedDate() != null
						&& processedTimeMap != null
						&& (processedTimeMap.size() > 0)) {
					Collection<Object> dateSet = processedTimeMap.keySet();
					if (dateSet != null) {
						Iterator<Object> iSet = dateSet.iterator();
						while (iSet.hasNext()) {
							strDateKey = (String) iSet.next();
							if ((strDateKey
									.equals(DateFormat.getDateInstance(DateFormat.MEDIUM).format(dt.getProcessedDate())))) {
								newTempMap.put(dt.getActivityLogUid().toString(), dt);
							}
						}
					}
				}

				if (dt.getProcessedDate() == null
						|| dt.getProcessedDate().equals("")) {
					if (processedTimeMap != null
							&& processedTimeMap
									.containsKey(NEDSSConstants.DATE_BLANK_KEY)) {
						newTempMap.put(dt.getActivityLogUid().toString(), dt);
					}
				}
			}
		}
			

		return convertTempMaptoColl(newTempMap);
	}
	

	public Collection<Object> filterOldEpilink(Collection<Object> activityLogColl,
			Map<Object, Object> statusMap) {
		Collection<Object> newStatusColl = new ArrayList<Object>();
		if (activityLogColl != null) {
			Iterator<Object> iter = activityLogColl.iterator();
			while (iter.hasNext()) {
				EpilinkDT dt = (EpilinkDT) iter.next();
				if (dt.getOldEpilinkId() != null && statusMap != null
						&& statusMap.containsKey(dt.getOldEpilinkId())) {
					newStatusColl.add(dt);
				}
				if (dt.getOldEpilinkId() == null || dt.getOldEpilinkId().equals("")) {
					if (statusMap != null
							&& statusMap.containsKey(NEDSSConstants.BLANK_KEY)) {
						newStatusColl.add(dt);
					}
				}

			}

		}
		return newStatusColl;
	}

	
	public Collection<Object> filterNewEpilink(Collection<Object> activityLogColl,
			Map<Object, Object> statusMap) {
		Collection<Object> newStatusColl = new ArrayList<Object>();
		if (activityLogColl != null) {
			Iterator<Object> iter = activityLogColl.iterator();
			while (iter.hasNext()) {
				EpilinkDT dt = (EpilinkDT) iter.next();
				if (dt.getNewEpilinkId() != null && statusMap != null
						&& statusMap.containsKey(dt.getNewEpilinkId())) {
					newStatusColl.add(dt);
				}
				if (dt.getNewEpilinkId() == null || dt.getNewEpilinkId().equals("")) {
					if (statusMap != null
							&& statusMap.containsKey(NEDSSConstants.BLANK_KEY)) {
						newStatusColl.add(dt);
					}
				}

			}

		}
		return newStatusColl;
	}

	
	

	private Collection<Object> convertTempMaptoColl(
			Map<Object, Object> newTempMap) {
		Collection<Object> invColl = new ArrayList<Object>();
		if (newTempMap != null && newTempMap.size() > 0) {
			Collection<Object> tempKeyColl = newTempMap.keySet();
			Iterator<Object> iter = tempKeyColl.iterator();
			while (iter.hasNext()) {
				String tempKey = (String) iter.next();
				invColl.add(newTempMap.get(tempKey));
			}
		}
		return invColl;
	}
	
	public ActionForward viewActivityLogDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException {

		DsmActivityLogForm logForm = (DsmActivityLogForm) form;
		String docType = logForm.getDocType();
		ArrayList<?> testList = new ArrayList<Object>();
		String edxActivityLogId = "";
		String redirectReq = "viewActivityLogDetails";
		try {
			logForm.setActionMode(NEDSSConstants.VIEW_LOAD_ACTION);
			logForm.setReturnToLink("<a href=\"/nbs/LoadDSMActivityLog.do?method=searchActivityLogSubmit\">Return To Activity log</a> ");
			edxActivityLogId = request.getParameter("edxActivityLogUid");

			if (edxActivityLogId != null && edxActivityLogId.length() > 0) {
				testList = logForm.getManageList();
				if (testList.size() == 0) {
					testList = DsmActivityLogUtil
							.retrieveActivityLogDetailCollection(new Long(
									edxActivityLogId), request.getSession());
					Integer queueSize = properties
							.getQueueSize(NEDSSConstants.MESSAGING_MANAGEMENT_ACTIVITYLOG_LIBRARY_QUEUE_SIZE);
					logForm.getAttributeMap().put("queueSize", queueSize);
				}
				
				DsmActivityLogUtil.updateToHtmlFormat(testList);
				
				String algorithmName = request.getParameter("algorithmName1");
				String processedTime = request.getParameter("processedTime1");
				String action = request.getParameter("action1");
				String eventId = request.getParameter("eventId1");
				String messageId = request.getParameter("messageId1");
				String srcName = request.getParameter("srcName1");
				String entityNm = request.getParameter("entityNm1");
				String accessionNbr = request.getParameter("accessionNbr1");
				String businessObjLocalId = request.getParameter("businessObjLocalId1");

				logForm.getAttributeMap().put("algorithmName",algorithmName);
				logForm.getAttributeMap().put("processedTime",processedTime);
				logForm.getAttributeMap().put("action",action);
				logForm.getAttributeMap().put("eventId",eventId);
				logForm.getAttributeMap().put("messageId",messageId);
				logForm.getAttributeMap().put("srcName",srcName);
				logForm.getAttributeMap().put("entityNm",entityNm);
				logForm.getAttributeMap().put("accessionNbr",accessionNbr);
				logForm.getAttributeMap().put("businessObjLocalId",businessObjLocalId);
				logForm.getAttributeMap().put("queueCount",String.valueOf(testList.size()));
				request.setAttribute("queueCount", String.valueOf(testList.size()));
				request.setAttribute("activityDetailList", testList);
				request.setAttribute("edxActivityLogUid", HTMLEncoder.encodeHtml(edxActivityLogId));
				if (docType.equalsIgnoreCase(NEDSSConstants.PHC_236)){
				request.setAttribute("PageTitle", "Manage PHCR Activity Log: View Activity Details");
				}
				else if(docType.equalsIgnoreCase(NEDSSConstants.ADMIN_ALERT_LAB)) {
				request.setAttribute("PageTitle",
						"Manage ELR Activity Log");
				}
			}
		} catch (Exception e) {
			logger.error("Exception in viewActivityLogDetails: "
					+ e.toString(),e);
			throw new ServletException("Error while viewing DSM Activity Log Details: "+e.getMessage(),e);
		}
		return (mapping.findForward(redirectReq));
	}

	
	private EpilinkSummaryDisplayVO getMockEpilinkSummaryDisplayVO(){
		EpilinkSummaryDisplayVO vo = new EpilinkSummaryDisplayVO();
		try {
			List<Object> testCollection = new ArrayList<Object>();
			Calendar cal = Calendar.getInstance();


			EpilinkDT dt = new EpilinkDT();
			dt.setFirstName("std");
			dt.setLastName("1");
			cal.add(Calendar.DAY_OF_MONTH, -1);
			dt.setProcessedDate(cal.getTime());
			dt.setOldEpilinkId("GA12345670");
			dt.setNewEpilinkId("GA12345680");
			dt.setInvestigationsString("CAS10002012GA01 CAS10012145GA01");
			dt.setActivityLogUid(1L);

			EpilinkDT dt1 = new EpilinkDT();
			dt1.setFirstName("std");
			dt1.setLastName("2");
			cal.add(Calendar.DAY_OF_MONTH, -2);
			dt1.setProcessedDate(cal.getTime());
			dt1.setOldEpilinkId("GA12345671");
			dt1.setNewEpilinkId("GA12345681");

			dt.setInvestigationsString("CAS10002013GA01");
			dt1.setActivityLogUid(2L);

			EpilinkDT dt2 = new EpilinkDT();
			dt2.setFirstName("std");
			dt2.setLastName("3");
			cal.add(Calendar.DAY_OF_MONTH, -3);
			dt2.setProcessedDate(cal.getTime());
			dt2.setOldEpilinkId("GA12345672");
			dt2.setNewEpilinkId("GA12345682");

			dt.setInvestigationsString("CAS10002014GA01");
			dt2.setActivityLogUid(3L);


			EpilinkDT dt3 = new EpilinkDT();
			dt3.setFirstName("std");
			dt3.setLastName("4");
			cal.add(Calendar.DAY_OF_MONTH, -4);
			dt3.setProcessedDate(cal.getTime());
			dt3.setOldEpilinkId("GA12345673");
			dt3.setNewEpilinkId("GA12345683");

			dt.setInvestigationsString("CAS10002011GA01");
			dt3.setActivityLogUid(4L);

			testCollection.add(dt);
			testCollection.add(dt1);
			testCollection.add(dt2);
			testCollection.add(dt3);

			vo.setTestCollection(testCollection );
		} catch (Exception ex) {
			logger.error("Exception encountered in EpiLinkLogAdmin.getMockEpilinkSummaryDisplayVO() " + ex.getMessage());
			ex.printStackTrace();
		}
		return vo;
	}
	
	public static void main(String[] args){
		EpiLinkLogAdministrationAction a = new EpiLinkLogAdministrationAction();
		System.out.println(a.getMockEpilinkSummaryDisplayVO().getTestCollection().size());
	}
	
}
