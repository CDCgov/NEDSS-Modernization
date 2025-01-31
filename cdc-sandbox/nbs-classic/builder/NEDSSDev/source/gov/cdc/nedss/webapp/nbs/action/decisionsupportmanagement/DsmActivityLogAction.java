package gov.cdc.nedss.webapp.nbs.action.decisionsupportmanagement;

import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.systemservice.dt.EDXActivityLogDT;
import gov.cdc.nedss.systemservice.ejb.decisionsupportejb.dt.DsmLogSearchDT;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.util.HTMLEncoder;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.util.StringUtils;
import gov.cdc.nedss.webapp.nbs.action.decisionsupportmanagement.DecisionSupportClientVO.ActivityLogClientVO;
import gov.cdc.nedss.webapp.nbs.action.decisionsupportmanagement.util.DsmActivityLogUtil;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.DynamicPageConstants;
import gov.cdc.nedss.webapp.nbs.action.util.PaginationUtil;
import gov.cdc.nedss.webapp.nbs.action.util.QueueUtil;
import gov.cdc.nedss.webapp.nbs.form.decisionsupportmanagement.DsmActivityLogForm;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;

public class DsmActivityLogAction extends DispatchAction {

	static final LogUtils logger = new LogUtils(
			DsmActivityLogAction.class.getName());
	QueueUtil queueUtil = new QueueUtil();
	PropertyUtil properties = PropertyUtil.getInstance();

	public ActionForward searchActivityLog(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException{

		try {
			DsmActivityLogForm activitylogForm = (DsmActivityLogForm) form;
			activitylogForm.clearSelections();
			activitylogForm.initDefaultSelections();
			String docType = request.getParameter("param1");
			activitylogForm.setDocType(docType);
			if (docType.equalsIgnoreCase(NEDSSConstants.PHC_236)){
			request.setAttribute("PageTitle",
					"Manage PHCR Activity Log");
						
			}
			else if(docType.equalsIgnoreCase(NEDSSConstants.ADMIN_ALERT_LAB)) {
				request.setAttribute("PageTitle",
						"Manage ELR Activity Log");
			}
		} catch (Exception e) {
			logger.error("Exception in DsmActivityLogAction.searchActivityLog: " + e.toString(),e);
			throw new ServletException("Error while entering DSM Activity Log Page: "+e.getMessage(),e);
		} 
		return (mapping.findForward("default"));

	}

	@SuppressWarnings("unchecked")
	public ActionForward searchActivityLogSubmit(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException{

		DsmActivityLogForm activitylogForm = (DsmActivityLogForm) form;
		String docType = activitylogForm.getDocType();
		ActionMessages errors = activitylogForm.validate(mapping,
				request);
		if (!errors.isEmpty()) {
			activitylogForm.getAttributeMap().put("SEARCHRESULT", null);
			request.setAttribute("error_messages", errors);
			return (mapping.findForward("default"));
		}
		
		try {
			DsmLogSearchDT dsmLogSearchDT = convertVOToDT(activitylogForm.getActivityLogClientVO());
			dsmLogSearchDT.setDocType(docType);
			boolean forceEJBcall = false;
			boolean initLoad = request.getParameter("initLoad") == null ? false
					: Boolean.valueOf(request.getParameter("initLoad"))
							.booleanValue();
			if (initLoad && !PaginationUtil._dtagAccessed(request)) {
				activitylogForm.clearAll();
				forceEJBcall = true;
				Integer queueSize = properties
						.getQueueSize(NEDSSConstants.MESSAGING_MANAGEMENT_ACTIVITYLOG_LIBRARY_QUEUE_SIZE);
				activitylogForm.getAttributeMap().put("queueSize", queueSize);
			}
	
			String contextAction = request.getParameter("context");
			if (contextAction != null && contextAction.equals("ReturnToManage")) 
				forceEJBcall = true;
			// To make sure SelectAll is checked, see if no criteria is applied
			if (activitylogForm.getSearchCriteriaArrayMap().size() == 0)
				request.setAttribute("ActionMode", HTMLEncoder.encodeHtml("InitLoad"));
			String actionMd = request.getParameter("actionMode");
			if (activitylogForm.getActionMode() == null
					|| (actionMd != null && actionMd.equalsIgnoreCase("Manage"))
					|| activitylogForm.getActionMode().equals("Manage"))
				activitylogForm.setActionMode(DynamicPageConstants.MANAGE);
			else {
				activitylogForm.setActionMode(activitylogForm.getActionMode());
			}
			ArrayList<Object> activityLogList;
			if (forceEJBcall) {
				activityLogList = DsmActivityLogUtil.retrieveDsmActivityLogs(
						dsmLogSearchDT, request.getSession(true));
				
				NBSContext.store(request.getSession(), "DSActivityLogList", activityLogList);
				NBSContext.store(request.getSession(), "DSActivityLogListFull", activityLogList);
				activitylogForm.initializeDropDowns(activityLogList);
				activitylogForm.getAttributeMap().put("processedTime",
						new Integer(activitylogForm.getProcessedTime().size()));
				activitylogForm.getAttributeMap().put("action",
						new Integer(activitylogForm.getAction().size()));
				activitylogForm.getAttributeMap().put("algorithmName",
						new Integer(activitylogForm.getAlgorithmName().size()));
				activitylogForm.getAttributeMap().put("status",
						new Integer(activitylogForm.getStatus().size()));
				/*activitylogForm.getAttributeMap().put("messageId",
						new Integer(activitylogForm.getStatus().size()));
				activitylogForm.getAttributeMap().put("srcName",
						new Integer(activitylogForm.getStatus().size()));
				activitylogForm.getAttributeMap().put("entityNm",
						new Integer(activitylogForm.getStatus().size()));
				activitylogForm.getAttributeMap().put("accessionNbr",
						new Integer(activitylogForm.getStatus().size()));*/


				DsmActivityLogUtil.updateActivityLogLinks(activityLogList, request);

			} else {
				activityLogList = (ArrayList<Object>) NBSContext.retrieve(request.getSession(), "DSActivityLogList");
				request.setAttribute("activityLogList", activityLogList);
			}
			boolean existing = request.getParameter("existing") == null ? false
					: true;
			if (contextAction != null
					&& contextAction.equalsIgnoreCase("ReturnToManage")) {
				Collection<Object> filteredColl = filterActivityLogColl(
						activitylogForm, request);
				if (filteredColl != null)
					activityLogList = (ArrayList<Object>) filteredColl;
				sortManageLog(activitylogForm, activityLogList, true, request);
			} else {
				sortManageLog(activitylogForm, activityLogList, existing, request);
				if (!existing) {
					DsmActivityLogUtil.updateActivityLogLinks(activityLogList,
							request);
				} else {
					filterActivityLogColl(activitylogForm, request);
					DsmActivityLogUtil.updateActivityLogLinks(activityLogList,
							request);
				}
			}
			activitylogForm.getAttributeMap().put("queueCount",
					String.valueOf(activityLogList.size()));
			NBSContext.store(request.getSession(), "DSActivityLogList", activityLogList);
			activitylogForm.getAttributeMap().put("SEARCHRESULT", "SEARCHRESULT");
			request.setAttribute("activityLogList", activityLogList);
			request.setAttribute("queueCount", String.valueOf(activityLogList.size()));
			if (docType.equalsIgnoreCase(NEDSSConstants.PHC_236)){
			request.setAttribute(DynamicPageConstants.PAGE_TITLE,
					"Manage PHCR Activity Log");
			}
			else if(docType.equalsIgnoreCase(NEDSSConstants.ADMIN_ALERT_LAB)) {
				request.setAttribute(DynamicPageConstants.PAGE_TITLE,
						"Manage ELR Activity Log");
			}
			
		} catch (Exception e) {
			logger.error("Exception in searchActivityLogSubmit: "
					+ e.toString(),e);
			throw new ServletException("Error while searching DSM Activity Logs: "+e.getMessage(),e);
		} 
		//return (mapping.findForward("default"));
		return PaginationUtil.paginate(activitylogForm, request, "default",mapping);
	}

	private DsmLogSearchDT convertVOToDT(ActivityLogClientVO activityLogClientVO) throws ParseException {
		DsmLogSearchDT dsmLogSearchDT = new DsmLogSearchDT();
		Date fromDateTime = StringUtils.formatStringToDatewithHrMin(
				activityLogClientVO.getProcessingDateFrom(), activityLogClientVO.getProcessingTimeFrom());
		dsmLogSearchDT.setFromDateTime(fromDateTime);
		Date toDateTime = StringUtils.formatStringToDatewithHrMin(
				activityLogClientVO.getProcessingDateTo(), activityLogClientVO.getProcessingTimeTo());
		dsmLogSearchDT.setToDateTime(toDateTime);
		dsmLogSearchDT.setProcessStatus((
				activityLogClientVO.getProcessStatus() == null)?null:Arrays.asList(activityLogClientVO
						.getProcessStatus()));
		return dsmLogSearchDT;
	}

	@SuppressWarnings("unchecked")
	public ActionForward filterActivityLogSubmit(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException {

		try {
		DsmActivityLogForm logForm = (DsmActivityLogForm) form;
		Collection<Object> activitylist = filterActivityLogColl(logForm, request);
		String docType = logForm.getDocType();
		if (activitylist != null) {
			request.setAttribute("ActionMode", HTMLEncoder.encodeHtml("Search"));
		} 
   		NBSContext.store(request.getSession(true) ,"DSActivityLogList",activitylist);

		request.setAttribute("queueList", activitylist);
		sortManageLog(logForm, activitylist, true, request);
		request.setAttribute("activityLogList", activitylist);
		logForm.getAttributeMap().put("queueCount",
				String.valueOf(activitylist.size()));
		if (docType.equalsIgnoreCase(NEDSSConstants.PHC_236)){
		request.setAttribute("PageTitle",
				"Manage PHCR Activity Log");
		}
		else if(docType.equalsIgnoreCase(NEDSSConstants.ADMIN_ALERT_LAB)) {
			request.setAttribute("PageTitle",
					"Manage ELR Activity Log");
			
		}
		logForm.getAttributeMap().put("PageNumber", "1");

		return PaginationUtil.paginate(logForm, request, "default", mapping);
		} catch (Exception e) {
			logger.error("Exception in filterActivityLogSubmit: "
					+ e.toString(),e);
			throw new ServletException("Error while filtering DSM Activity Logs: "+e.getMessage(),e);
		} 
	}

	@SuppressWarnings("unchecked")
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

	private String getSortMethod(HttpServletRequest request,
			DsmActivityLogForm logForm, String sortType) {
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
			DsmActivityLogForm logForm, String sortType) {
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

	private void updateListBeforeSort(Collection<Object> impExpLogList) {
		Iterator<Object> iter = impExpLogList.iterator();
		while (iter.hasNext()) {
			EDXActivityLogDT dt = (EDXActivityLogDT) iter.next();

			if (dt.getEdxActivityLogUid() == null
					|| (dt.getEdxActivityLogUid().toString() != null && dt
							.getEdxActivityLogUid().toString().equals(""))) {
				dt.setEdxActivityLogUid(null);
			}

			if (dt.getExceptionShort() == null
					|| dt.getExceptionShort() != null
					&& dt.getExceptionShort().equals("")) {
				dt.setExceptionShort("ZZZZZ");
			}
		}
	}

	private void updateListAfterSort(Collection<Object> notifiSummaryVOs) {
		Iterator<Object> iter = notifiSummaryVOs.iterator();
		while (iter.hasNext()) {
			EDXActivityLogDT dt = (EDXActivityLogDT) iter.next();
			if (dt.getEdxActivityLogUid() != null
					&& dt.getEdxActivityLogUid().toString().equals("ZZZZZ")) {
				dt.setEdxActivityLogUid(null);
			}
			if (dt.getExceptionShort() != null
					&& dt.getExceptionShort().equals("ZZZZZ")) {
				dt.setExceptionShort("");
			}
		}
	}

	@SuppressWarnings("unchecked")
	private Collection<Object> filterActivityLogColl(
			DsmActivityLogForm logForm, HttpServletRequest request)
			throws Exception {

		Collection<Object> activitylist = new ArrayList<Object>();

		String srchCriteriaProcessedTime = null;
		String srchCriteriaAction = null;
		String srchCriteriaAlgorithmName = null;
		String srchCriteriaStatus = null;
		String srchCriteriaExceptionText = null;
		String sortOrderParam = null;

		try {
			Map<Object, Object> searchCriteriaMap = logForm
					.getSearchCriteriaArrayMap();
			ArrayList<Object> activityLogColl = null;
			try {
				    activityLogColl = (ArrayList<Object>) NBSContext.retrieve(request.getSession(), "DSActivityLogListFull");
			}catch(Exception ex) {
				logger.debug("DSActivityLogListFull is null in filterActivityLogColl");
				activityLogColl = (ArrayList<Object> ) NBSContext.retrieve(request.getSession(true) , "DSActivityLogList");

			}
			activitylist = getFilteredActivityLogLib(activityLogColl,
					searchCriteriaMap);
			String[] processedTime = (String[]) searchCriteriaMap
					.get("PROCESSEDTIME");
			String[] action = (String[]) searchCriteriaMap.get("ACTION");
			String[] algorithmName = (String[]) searchCriteriaMap
					.get("ALGORITHMNAME");
			String[] status = (String[]) searchCriteriaMap.get("STATUS");
			String[] exceptionText = (String[]) searchCriteriaMap.get("EXCEPTIONTEXT");
			String filterByEventID = null;
			String filterByMessageID = null;
			String filterBySrcNM = null;
			String filterByPatientNM = null;
			String filterByAccessionNO = null;
			String filterByObservationID = null;
			if(searchCriteriaMap.get("SearchText1_FILTER_TEXT")!=null){
				filterByEventID = (String) searchCriteriaMap.get("SearchText1_FILTER_TEXT");
			}
			if(searchCriteriaMap.get("SearchText2_FILTER_TEXT")!=null){
				filterByMessageID = (String) searchCriteriaMap.get("SearchText2_FILTER_TEXT");
			}
			if(searchCriteriaMap.get("SearchText3_FILTER_TEXT")!=null){
				filterBySrcNM = (String) searchCriteriaMap.get("SearchText3_FILTER_TEXT");
			}
			if(searchCriteriaMap.get("SearchText4_FILTER_TEXT")!=null){
				filterByPatientNM = (String) searchCriteriaMap.get("SearchText4_FILTER_TEXT");
			}
			if(searchCriteriaMap.get("SearchText5_FILTER_TEXT")!=null){
				filterByAccessionNO = (String) searchCriteriaMap.get("SearchText5_FILTER_TEXT");
			}
			if(searchCriteriaMap.get("SearchText6_FILTER_TEXT")!=null){
				filterByObservationID = (String) searchCriteriaMap.get("SearchText6_FILTER_TEXT");
			}


			Integer processedTimeCount = new Integer(processedTime == null ? 0
					: processedTime.length);
			Integer actionCount = new Integer(action == null ? 0
					: action.length);

			Integer algorithmNameCount = new Integer(algorithmName == null ? 0
					: algorithmName.length);
			Integer statusCount = new Integer(status == null ? 0
					: status.length);
			Integer exceptionTextCount = new Integer(exceptionText == null ? 0
					: exceptionText.length);

			// Do not filter if the selected values for filter is same as
			// filtered list, but put the sortMethod, direction and criteria
			// stuff
			if (processedTimeCount.equals((logForm.getAttributeMap()
					.get("processedTimeCount")))
					&& (actionCount.equals(logForm.getAttributeMap().get(
							"actionCount")))
					&& (algorithmNameCount.equals(logForm.getAttributeMap()
							.get("algorithmNameCount")))
					&& (exceptionTextCount.equals(logForm.getAttributeMap()
							.get("exceptionTextCount")))
					&& (statusCount.equals(logForm.getAttributeMap().get(
							"statusCount")))
					&& filterByEventID == null
					&& filterByMessageID == null
					&& filterBySrcNM == null
					&& filterByPatientNM == null
					&& filterByAccessionNO== null
					&& filterByObservationID== null) {
					

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
					sortOrderParam = DsmActivityLogUtil
							.getSortCriteriaForActivityLog(direction, sortMethod);
				}
				Map<Object, Object> searchCriteriaColl = new TreeMap<Object, Object>();
				searchCriteriaColl.put("sortSt", sortOrderParam);
				logForm.getAttributeMap().put("searchCriteria",
						searchCriteriaColl);
				return null;
			}
			ArrayList<Object> processedTimeList = logForm.getProcessedTime();
			ArrayList<Object> actionList = logForm.getAction();
			ArrayList<Object> algorithmNameList = logForm.getAlgorithmName();
			ArrayList<Object> statusList = logForm.getStatus();
			ArrayList<Object> exceptionTextList = logForm.getExceptiontext();

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
				sortOrderParam = DsmActivityLogUtil.getSortCriteriaForActivityLog(
						direction, sortMethod);
			}

			srchCriteriaProcessedTime = queueUtil.getSearchCriteria(
					processedTimeList, processedTime,
					NEDSSConstants.DSMLOGACTIVITY_FILTERBYPROCESSEDTIME);

			srchCriteriaAction = queueUtil.getSearchCriteria(actionList,
					action, NEDSSConstants.DSMLOGACTIVITY_FILTERBYACTION);
			srchCriteriaAlgorithmName = queueUtil.getSearchCriteria(
					algorithmNameList, algorithmName,
					NEDSSConstants.DSMLOGACTIVITY_FILTERBYALGORITHMNAME);
			srchCriteriaStatus = queueUtil.getSearchCriteria(statusList,
					status, NEDSSConstants.DSMLOGACTIVITY_FILTERBYSTATUS);
			srchCriteriaExceptionText = queueUtil.getSearchCriteria(exceptionTextList,
					exceptionText, NEDSSConstants.DSMLOGACTIVITY_FILTERBYEXCEPTIONTEXT);

			// set the error message to the form
			if (sortOrderParam != null)
				searchCriteriaColl.put("sortSt", sortOrderParam);
			if (srchCriteriaProcessedTime != null)
				searchCriteriaColl.put("INV147", srchCriteriaProcessedTime);
			if (filterByEventID != null)
				searchCriteriaColl.put("INV100", filterByEventID);
			if (srchCriteriaAction != null)
				searchCriteriaColl.put("INV150", srchCriteriaAction);
			if (srchCriteriaAlgorithmName != null)
				searchCriteriaColl.put("INV163", srchCriteriaAlgorithmName);
			if (srchCriteriaStatus != null)
				searchCriteriaColl.put("NOT118", srchCriteriaStatus);
			if (srchCriteriaExceptionText != null)
				searchCriteriaColl.put("DEM102", srchCriteriaExceptionText);
			if (filterByMessageID != null)
                searchCriteriaColl.put("INV101", filterByMessageID);
			if (filterBySrcNM != null)
                searchCriteriaColl.put("INV102", filterBySrcNM);
			if (filterByPatientNM != null)
                searchCriteriaColl.put("INV103", filterByPatientNM);
			if (filterByAccessionNO != null)
                searchCriteriaColl.put("INV104", filterByAccessionNO);
			if (filterByObservationID != null)
                searchCriteriaColl.put("INV105", filterByObservationID);


			logForm.getAttributeMap().put("searchCriteria", searchCriteriaColl);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while filtering the DSM activity Log  by  : "
					+ e.toString(),e);
			throw new ServletException("Error  in filterActivityLogColl method: "
					+ e.getMessage());
		}
		return activitylist;
	}

	private void sortManageLog(DsmActivityLogForm logForm,
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
			sortMethod = "getRecordStatusTime";
			invDirectionFlag = false;
		}

		if (sortMethod != null && activityLogList != null
				&& activityLogList.size() > 0) {
			updateListBeforeSort(activityLogList);
			//util.sortObjectByColumn(sortMethod,
					//(Collection<Object>) activityLogList, invDirectionFlag);
			util.sortObjectByColumnGeneric(sortMethod,
					(Collection<Object>) activityLogList, invDirectionFlag);
			updateListAfterSort(activityLogList);

		}

		if (!existing) {
			// Finally put sort criteria in form
			String sortOrderParam = DsmActivityLogUtil
					.getSortCriteriaForActivityLog(invDirectionFlag == true ? "1"
							: "2", sortMethod);
			Map<Object, Object> searchCriteriaColl = new TreeMap<Object, Object>();
			searchCriteriaColl.put("sortSt", sortOrderParam);
			logForm.getAttributeMap().put("searchCriteria", searchCriteriaColl);
		}
	}

	public Collection<Object> getFilteredActivityLogLib(
			Collection<Object> activityLogColl,
			Map<Object, Object> searchCriteriaMap) {

		String[] processedTime = (String[]) searchCriteriaMap
				.get("PROCESSEDTIME");
		String[] action = (String[]) searchCriteriaMap.get("ACTION");
		String[] algorithmName = (String[]) searchCriteriaMap
				.get("ALGORITHMNAME");
		String[] status = (String[]) searchCriteriaMap.get("STATUS");
		String[] exceptionText = (String[]) searchCriteriaMap.get("EXCEPTIONTEXT");
		String filterByEventID = null;
		String filterByMessageID = null;
		String filterBySrcNM = null;
		String filterByPatientNM = null;
		String filterByAccessionNO = null;
		String filterByObservationID = null;
		
		if(searchCriteriaMap.get("SearchText1_FILTER_TEXT")!=null){
			filterByEventID = (String) searchCriteriaMap.get("SearchText1_FILTER_TEXT");
		}
		if(searchCriteriaMap.get("SearchText2_FILTER_TEXT")!=null){
			filterByMessageID = (String) searchCriteriaMap.get("SearchText2_FILTER_TEXT");
		}
		if(searchCriteriaMap.get("SearchText3_FILTER_TEXT")!=null){
			filterBySrcNM = (String) searchCriteriaMap.get("SearchText3_FILTER_TEXT");
		}
		if(searchCriteriaMap.get("SearchText4_FILTER_TEXT")!=null){
			filterByPatientNM = (String) searchCriteriaMap.get("SearchText4_FILTER_TEXT");
		}
		if(searchCriteriaMap.get("SearchText5_FILTER_TEXT")!=null){
			filterByAccessionNO = (String) searchCriteriaMap.get("SearchText5_FILTER_TEXT");
		}
		if(searchCriteriaMap.get("SearchText6_FILTER_TEXT")!=null){
			filterByObservationID = (String) searchCriteriaMap.get("SearchText6_FILTER_TEXT");
		}

		Map<Object, Object> processedTimeMap = new HashMap<Object, Object>();
		Map<Object, Object> actionMap = new HashMap<Object, Object>();
		Map<Object, Object> algorithmNameMap = new HashMap<Object, Object>();
		Map<Object, Object> statusMap = new HashMap<Object, Object>();
		Map<Object, Object> exceptionTextMap = new HashMap<Object, Object>();

		if (processedTime != null && processedTime.length > 0)
			processedTimeMap = queueUtil.getMapFromStringArray(processedTime);
		if (action != null && action.length > 0)
			actionMap = queueUtil.getMapFromStringArray(action);
		if (algorithmName != null && algorithmName.length > 0)
			algorithmNameMap = queueUtil.getMapFromStringArray(algorithmName);
		if (status != null && status.length > 0)
			statusMap = queueUtil.getMapFromStringArray(status);
		if (exceptionText != null && status.length > 0)
			exceptionTextMap = queueUtil.getMapFromStringArray(exceptionText);

		if (processedTimeMap != null && processedTimeMap.size() > 0)
			activityLogColl = filterProcessedTime(activityLogColl,
					processedTimeMap);
		if (actionMap != null && actionMap.size() > 0)
			activityLogColl = filterActionName(activityLogColl, actionMap);
		if (algorithmNameMap != null && algorithmNameMap.size() > 0)
			activityLogColl = filterAlgorithmName(activityLogColl, algorithmNameMap);
		if (statusMap != null && statusMap.size() > 0)
			activityLogColl = filterStatus(activityLogColl, statusMap);
		if (exceptionTextMap != null && exceptionTextMap.size() > 0)
			activityLogColl = filterExceptionText(activityLogColl, exceptionTextMap);
		if(filterByEventID!= null){
			activityLogColl = filterByText(activityLogColl, filterByEventID, NEDSSConstants.FILTERBYEVENTID);
		}
		if(filterByMessageID!= null){
			activityLogColl = filterByText(activityLogColl, filterByMessageID, NEDSSConstants.FILTERBYMESSAGEID);
		}
		if(filterBySrcNM!= null){
			activityLogColl = filterByText(activityLogColl, filterBySrcNM, NEDSSConstants.FILTERBYSOURCENAME);
		}
		if(filterByPatientNM!= null){
			activityLogColl = filterByText(activityLogColl, filterByPatientNM, NEDSSConstants.FILTERBYPATIENTNAME);
		}
		if(filterByAccessionNO!= null){
			activityLogColl = filterByText(activityLogColl, filterByAccessionNO, NEDSSConstants.FILTERBYACCESSIONNO);
		}
		if(filterByObservationID!= null){
			activityLogColl = filterByText(activityLogColl, filterByObservationID, NEDSSConstants.FILTERBYOBSERVATIONID);
		}
		

		return activityLogColl;
	}
	
	public static Collection<Object>  filterByText(
			Collection<Object>  edxActivityLogDT, String filterByText,String column) {
		Collection<Object>  newTypeColl = new ArrayList<Object> ();
		try{
		if (edxActivityLogDT != null) {
			Iterator<Object> iter = edxActivityLogDT.iterator();
			while (iter.hasNext()) {
				EDXActivityLogDT dt = (EDXActivityLogDT) iter.next();
				if(column.equals(NEDSSConstants.FILTERBYEVENTID) && dt.getTargetUid()!= null && dt.getTargetUid().toString().toUpperCase().contains(filterByText.toUpperCase())){
					newTypeColl.add(dt);
				}
				if(column.equals(NEDSSConstants.FILTERBYMESSAGEID) && dt.getMessageId()!= null && dt.getMessageId().contains(filterByText.toUpperCase())){
					newTypeColl.add(dt);
				}
				if(column.equals(NEDSSConstants.FILTERBYSOURCENAME) && dt.getSrcName()!= null && dt.getSrcName().contains(filterByText.toUpperCase())){
					newTypeColl.add(dt);
				}
				if(column.equals(NEDSSConstants.FILTERBYPATIENTNAME) && dt.getEntityNm()!= null && dt.getEntityNm().toString().toUpperCase().contains(filterByText.toUpperCase())){
					newTypeColl.add(dt);
				}
				if(column.equals(NEDSSConstants.FILTERBYACCESSIONNO) && dt.getAccessionNbr()!= null && dt.getAccessionNbr().contains(filterByText.toUpperCase())){
					newTypeColl.add(dt);
				}
				if(column.equals(NEDSSConstants.FILTERBYOBSERVATIONID) && dt.getBusinessObjLocalId()!= null && dt.getBusinessObjLocalId().toUpperCase().contains(filterByText.toUpperCase())){
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

	public Collection<Object> filterStatus(Collection<Object> activityLogColl,
			Map<Object, Object> statusMap) {
		Collection<Object> newStatusColl = new ArrayList<Object>();
		if (activityLogColl != null) {
			Iterator<Object> iter = activityLogColl.iterator();
			while (iter.hasNext()) {
				EDXActivityLogDT dt = (EDXActivityLogDT) iter.next();
				if (dt.getRecordStatusCd() != null && statusMap != null
						&& statusMap.containsKey(dt.getRecordStatusCd())) {
					newStatusColl.add(dt);
				}
				if (dt.getRecordStatusCd() == null
						|| dt.getRecordStatusCd().equals("")) {
					if (statusMap != null
							&& statusMap.containsKey(NEDSSConstants.BLANK_KEY)) {
						newStatusColl.add(dt);
					}
				}

			}

		}
		return newStatusColl;

	}
	
	public Collection<Object> filterExceptionText(Collection<Object> activityLogColl,
			Map<Object, Object> exceptionMap) {
		Collection<Object> newExceptionColl = new ArrayList<Object>();
		if (activityLogColl != null) {
			Iterator<Object> iter = activityLogColl.iterator();
			while (iter.hasNext()) {
				EDXActivityLogDT dt = (EDXActivityLogDT) iter.next();
				if (dt.getException() != null && exceptionMap != null
						&& exceptionMap.containsKey(dt.getException())) {
					newExceptionColl.add(dt);
				}
				if (dt.getException() == null
						|| dt.getException().equals("")) {
					if (exceptionMap != null
							&& exceptionMap.containsKey(NEDSSConstants.BLANK_KEY)) {
						newExceptionColl.add(dt);
					}
				}

			}

		}
		return newExceptionColl;

	}


	public Collection<Object> filterAlgorithmName(Collection<Object> activityLogColl,
			Map<Object, Object> statusMap) {
		Collection<Object> newStatusColl = new ArrayList<Object>();
		if (activityLogColl != null) {
			Iterator<Object> iter = activityLogColl.iterator();
			while (iter.hasNext()) {
				EDXActivityLogDT dt = (EDXActivityLogDT) iter.next();
				if (dt.getAlgorithmName() != null && statusMap != null
						&& statusMap.containsKey(dt.getAlgorithmName())) {
					newStatusColl.add(dt);
				}
				if (dt.getAlgorithmName() == null || dt.getAlgorithmName().equals("")) {
					if (statusMap != null
							&& statusMap.containsKey(NEDSSConstants.BLANK_KEY)) {
						newStatusColl.add(dt);
					}
				}

			}

		}
		return newStatusColl;
	}

	public Collection<Object> filterEventType(Collection<Object> activityLogColl,
			Map<Object, Object> typeMap) {
		Collection<Object> newTypeColl = new ArrayList<Object>();
		if (activityLogColl != null) {
			Iterator<Object> iter = activityLogColl.iterator();
			while (iter.hasNext()) {
				EDXActivityLogDT dt = (EDXActivityLogDT) iter.next();
				if (dt.getDocType() != null && typeMap != null
						&& typeMap.containsKey(dt.getDocType())) {
					newTypeColl.add(dt);
				}
				if (dt.getDocType() == null
						|| dt.getDocType().equals("")) {
					if (typeMap != null
							&& typeMap.containsKey(NEDSSConstants.BLANK_KEY)) {
						newTypeColl.add(dt);
					}
				}

			}

		}
		return newTypeColl;
	}
	public Collection<Object> filterActionName(
			Collection<Object> activityLogColl,
			Map<Object, Object> templateNameMap) {
		Collection<Object> newtemplateNameColl = new ArrayList<Object>();
		if (activityLogColl != null) {
			Iterator<Object> iter = activityLogColl.iterator();
			while (iter.hasNext()) {
				EDXActivityLogDT dt = (EDXActivityLogDT) iter.next();
				if (dt.getAlgorithmAction() != null && templateNameMap != null
						&& templateNameMap.containsKey(dt.getAlgorithmAction())) {
					newtemplateNameColl.add(dt);
				}
				if (dt.getAlgorithmAction() == null || dt.getAlgorithmAction().equals("")) {
					if (templateNameMap != null
							&& templateNameMap
									.containsKey(NEDSSConstants.BLANK_KEY)) {
						newtemplateNameColl.add(dt);
					}
				}

			}

		}
		return newtemplateNameColl;
	}

	public Collection<Object> filterProcessedTime(
			Collection<Object> activityLogColl,
			Map<Object, Object> processedTimeMap) {
		Map<Object, Object> newTempMap = new HashMap<Object, Object>();
		String strDateKey = null;
		if (activityLogColl != null) {
			Iterator<Object> iter = activityLogColl.iterator();
			while (iter.hasNext()) {
				EDXActivityLogDT dt = (EDXActivityLogDT) iter.next();
				if (dt.getRecordStatusTime() != null
						&& processedTimeMap != null
						&& (processedTimeMap.size() > 0)) {
					Collection<Object> dateSet = processedTimeMap.keySet();
					if (dateSet != null) {
						Iterator<Object> iSet = dateSet.iterator();
						while (iSet.hasNext()) {
							strDateKey = (String) iSet.next();
							if (!(strDateKey
									.equals(NEDSSConstants.DATE_BLANK_KEY))) {
								if (queueUtil.isDateinRange(
										dt.getRecordStatusTime(), strDateKey)) {
									newTempMap.put(dt.getEdxActivityLogUid()
											.toString(), dt);
								}

							}
						}
					}
				}

				if (dt.getRecordStatusTime() == null
						|| dt.getRecordStatusTime().equals("")) {
					if (processedTimeMap != null
							&& processedTimeMap
									.containsKey(NEDSSConstants.DATE_BLANK_KEY)) {
						newTempMap
								.put(dt.getEdxActivityLogUid().toString(), dt);
					}
				}

			}
		}

		return convertTempMaptoColl(newTempMap);
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
}
