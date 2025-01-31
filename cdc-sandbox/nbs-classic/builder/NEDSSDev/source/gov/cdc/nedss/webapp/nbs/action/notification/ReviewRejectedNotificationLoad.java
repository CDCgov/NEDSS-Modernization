package gov.cdc.nedss.webapp.nbs.action.notification;

import gov.cdc.nedss.proxy.ejb.notificationproxyejb.bean.NotificationProxyHome;
import gov.cdc.nedss.proxy.ejb.notificationproxyejb.vo.RejectedNotificationSummaryVO;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.HTMLEncoder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.action.notification.util.RejectNotificationUtil;
import gov.cdc.nedss.webapp.nbs.action.util.DSQueueObject;
import gov.cdc.nedss.webapp.nbs.action.util.ErrorMessageHelper;
import gov.cdc.nedss.webapp.nbs.action.util.PaginationUtil;
import gov.cdc.nedss.webapp.nbs.action.util.QueueUtil;
import gov.cdc.nedss.webapp.nbs.form.investigation.InvestigationForm;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
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

public class ReviewRejectedNotificationLoad  extends DispatchAction{

	static final LogUtils logger = new LogUtils(ReviewRejectedNotificationLoad.class.getName());
	PropertyUtil properties = PropertyUtil.getInstance();
	RejectNotificationUtil notifiUtil = new RejectNotificationUtil();
    QueueUtil queueUtil = new QueueUtil();

	@SuppressWarnings("unchecked")
	public ActionForward loadQueue(
			ActionMapping mapping,
			ActionForm aForm,
			HttpServletRequest request,
			HttpServletResponse response)
			throws IOException, ServletException {


		InvestigationForm invForm = (InvestigationForm) aForm;
		boolean forceEJBcall = false;
		// Reset Pagination first time
		boolean initLoad = request.getParameter("initLoad") == null ? false: Boolean.valueOf(request.getParameter("initLoad")).booleanValue();
		if (initLoad && !PaginationUtil._dtagAccessed(request) ) {
			invForm.clearAll();
			forceEJBcall = true;
			// get the number of records to be displayed per page
			Integer queueSize = properties.getQueueSize(NEDSSConstants.REJECTED_NOTIFICATION_QUEUE_SIZE);
			invForm.getAttributeMap().put("queueSize", queueSize);
		}

		//To make sure SelectAll is checked, see if no criteria is applied
		if(invForm.getSearchCriteriaArrayMap().size() == 0)
			request.setAttribute("ActionMode", HTMLEncoder.encodeHtml("InitLoad"));


			HttpSession session = request.getSession();

			logger.debug("entering ReviewRejectedNotificationLoad Load");

			if (session == null) {
				logger.fatal("error no session");
				return mapping.findForward("login");
			}

			String contextAction = request.getParameter("ContextAction");

			if (contextAction == null)
				contextAction = (String) request.getAttribute("ContextAction");

	        if (contextAction != null){
					if(contextAction.equals("NNDRejectedNotifications")) {

	        	DSQueueObject dSQueueObject = new DSQueueObject();
	        	dSQueueObject.setDSSortColumn("getAddTime");
	        	dSQueueObject.setDSSortDirection("true");
	        	dSQueueObject.setDSFromIndex("0");
	        	dSQueueObject.setDSQueueType(NEDSSConstants.NND_REJECTED_NOTIFICATIONS_FOR_APPROVAL);
				NBSContext.store(session, "DSQueueObject", dSQueueObject);
			}else if((contextAction.equals("ReturnToRejectedNotifications"))||(contextAction.equals("ReturnToRejectedNotificationsQueue"))){
				forceEJBcall = true;
			}
			TreeMap tm = NBSContext.getPageContext(session, "PS232", contextAction);
			ErrorMessageHelper.setErrMsgToRequest(request, "PS232");


			String sCurrTask = NBSContext.getCurrentTask(session);

			request.setAttribute("ViewInvestigationHref","/nbs/" + sCurrTask + ".do?ContextAction=" + tm.get("InvestigationID"));

			request.setAttribute("ViewFileHref", "/nbs/" + sCurrTask + ".do?ContextAction=" + tm.get("ViewFile"));

			request.setAttribute(
				"nextHref",
				"/nbs/" + sCurrTask + ".do?ContextAction=" + tm.get("Next"));
			request.setAttribute(
				"prevHref",
				"/nbs/" + sCurrTask + ".do?ContextAction=" + tm.get("Prev"));
			request.setAttribute(
				"sortHref",
				"/nbs/" + sCurrTask + ".do?ContextAction=" + tm.get("Sort"));

			String sortMethod = request.getParameter("sortMethod");
			}

			boolean accessedEJB = false;


	       /* if(contextAction!=null && (contextAction.equals("Next") ||contextAction.equals("Prev") ||contextAction.equals("Sort")))
	         	forceEJBcall = false;*/

			try {

        		ArrayList<Object> rejectedNotifications = new ArrayList<Object> ();

        		if(forceEJBcall)
	        	{

	        		MainSessionHolder mainSessionHolder = new MainSessionHolder();
		            MainSessionCommand msCommand = mainSessionHolder.getMainSessionCommand(session);
		            String  sBeanJndiName = JNDINames.NOTIFICATION_PROXY_EJB;
		            String  sMethod = "getRejectedNotifications";
		            ArrayList<Object> arr = (ArrayList<Object> )msCommand.processRequest(sBeanJndiName, sMethod, null);
		            rejectedNotifications = (ArrayList<Object> )arr.get(0);
		            accessedEJB = true;
		            NBSContext.store(session,"DSRejectedNotificationList",rejectedNotifications);
    				//Set InvSummaryVOColl as property of Form first time since this list is used for distinct dropdown values in the form
		            NBSContext.store(session ,"DSRejectedNotificationListFull", rejectedNotifications);
                    invForm.initializeRejectedDropDowns(rejectedNotifications);
                    invForm.getAttributeMap().put("submittedByCount",new Integer(invForm.getRejectedQsubmittedBy().size()));
                    invForm.getAttributeMap().put("ConditionsCount",new Integer(invForm.getRejectedQconditions().size()));
                    invForm.getAttributeMap().put("StatusCount",new Integer(invForm.getRejectedQcaseStatus().size()));
                    invForm.getAttributeMap().put("dateFilterListCount",new Integer(invForm.getRejectedQdateFilterList().size()));
                    invForm.getAttributeMap().put("typeCount",new Integer(invForm.getRejectedQtype().size()));
                    invForm.getAttributeMap().put("recipientCount",new Integer(invForm.getRejectedQrecipient().size()));
                    invForm.getAttributeMap().put("rejectedByCount",new Integer(invForm.getRejectedQRejectedBy().size()));
	        	} else {
	        		rejectedNotifications = (ArrayList<Object> )NBSContext.retrieve(session ,"DSRejectedNotificationList");
	        	}


        		 boolean existing = request.getParameter("existing") == null ? false : true;
     			if((contextAction != null && contextAction.equalsIgnoreCase("ReturnToRejectedNotifications"))||(contextAction != null && contextAction.equalsIgnoreCase("ReturnToRejectedNotificationsQueue"))) {
     				updatePatPhcLinks(rejectedNotifications, request);
     				//FilterInvs return NULL if all the filters are applied, so make sure it is not null before assigning back the coll
     				Collection<Object>  filteredColl = filterRejectedNotification(invForm, request);
     				if(filteredColl != null)
     					rejectedNotifications = (ArrayList<Object> ) filteredColl;
     				sortNotifications(invForm, rejectedNotifications, true, request);
     			}
     			else {
     				sortNotifications(invForm, rejectedNotifications, existing, request);

     				if(!existing) {
     					updatePatPhcLinks(rejectedNotifications, request);
     				} else
     					filterRejectedNotification(invForm, request);
     			}

                request.setAttribute("rejectedNotificationList", rejectedNotifications);
                request.setAttribute("PageTitle","Rejected Notifications Queue");
                // get the number of records to be displayed per page
                PropertyUtil properties = PropertyUtil.getInstance();
    			Integer queueSize = properties.getQueueSize(NEDSSConstants.REJECTED_NOTIFICATION_QUEUE_SIZE);
    			invForm.getAttributeMap().put("queueSize", queueSize);
    			request.setAttribute("queueCount", String.valueOf(rejectedNotifications.size()));
    			NBSContext.store(session, "DSRejectedNotificationList", rejectedNotifications);

			} catch (Exception e) {
				e.printStackTrace();
				logger.error(
					"Error in taskListDisplayLoad in getting updated notifications for audit from EJB");
			}

			return (mapping.findForward("rejectedQueue_notification_review"));

		}
    private String getPatientFullName(RejectedNotificationSummaryVO rejectedNotifSummaryVO) {
    	StringBuffer buff = new StringBuffer();
    	if ( rejectedNotifSummaryVO.getLastName()!= null && rejectedNotifSummaryVO.getLastName().trim() != "")
    		buff.append(rejectedNotifSummaryVO.getLastName().trim());
    	if (rejectedNotifSummaryVO.getLastName() != null
    			&& rejectedNotifSummaryVO.getFirstName() != null
    			&& rejectedNotifSummaryVO.getLastName().trim() != ""
    			&& rejectedNotifSummaryVO.getFirstName().trim() != "")
    		buff.append(",   ");
    	if (rejectedNotifSummaryVO.getFirstName() != null
    			&& rejectedNotifSummaryVO.getFirstName().trim() != "")
    		buff.append(rejectedNotifSummaryVO.getFirstName().trim());
    	String patientFullName = buff.toString();
    	return patientFullName;
    }

    private void updatePatPhcLinks(ArrayList<Object>  rejectedNotifications,
			HttpServletRequest request) {
		if (rejectedNotifications != null && rejectedNotifications.size() != 0) {
			for (int i = 0; i < rejectedNotifications.size(); i++) {
				RejectedNotificationSummaryVO rejectedNotifSummaryVO = (RejectedNotificationSummaryVO) rejectedNotifications.get(i);
				String patFullName = getPatientFullName(rejectedNotifSummaryVO);
				rejectedNotifSummaryVO.setPatientFullName(patFullName);
				if(rejectedNotifSummaryVO.getRecipient()==null){
					if(rejectedNotifSummaryVO.getNndInd()!=null && rejectedNotifSummaryVO.getNndInd().equals(NEDSSConstants.YES))
						rejectedNotifSummaryVO.setRecipient(NEDSSConstants.ADMINFLAGCDC);
	            	else
	            		rejectedNotifSummaryVO.setRecipient(NEDSSConstants.LOCAl_DESC);
				}
				//Added to default the recipient to CDC when it is null...
				//rejectedNotifSummaryVO.setRecipient(rejectedNotifSummaryVO.getRecipient()== null ? NEDSSConstants.ADMINFLAGCDC : rejectedNotifSummaryVO.getRecipient());
				String viewFileHref = request.getAttribute("ViewFileHref") == null ? "" : (String) request.getAttribute("ViewFileHref");
				if(!viewFileHref.equals("")) {
					String patLink = "<a href=\"" + viewFileHref  +"&MPRUid="+ String.valueOf(rejectedNotifSummaryVO.getPatientUid()) + "\">" + patFullName + "</a>";
					rejectedNotifSummaryVO.setPatientFullNameLnk(patLink);
				}
				String condDesc = rejectedNotifSummaryVO.getCondition();
				rejectedNotifSummaryVO.setCondition(condDesc);
				String viewHref = request.getAttribute("ViewInvestigationHref") == null ? "": (String) request.getAttribute("ViewInvestigationHref");
				if(!viewHref.equals("")) {
					String phcLink = "<a href=\"" + viewHref+"&publicHealthCaseUID="+ String.valueOf(rejectedNotifSummaryVO.getPublicHealthCaseUid()) + "\">"+ condDesc + "</a>";
					rejectedNotifSummaryVO.setConditionCodeTextLnk(phcLink);
				}

			}

		}
	}
    @SuppressWarnings("unchecked")
	public ActionForward filterRejectedNotificationSubmit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

    	InvestigationForm invForm = (InvestigationForm) form;
		Collection<Object>  rejectedNotifSummaryVOs = filterRejectedNotification(invForm, request);
		if(rejectedNotifSummaryVOs != null){
			request.setAttribute("ActionMode", HTMLEncoder.encodeHtml("Search"));
		//When all filters are applied (equivalent to no filter applied), Collection<Object>  is set to null in which case retrieve it from Form.
		}else{
			try {
			rejectedNotifSummaryVOs = (ArrayList<Object> ) NBSContext.retrieve(request.getSession() ,"DSRejectedNotificationListFull");
			}
			catch(Exception ex) {
				logger.debug("DSRejectedNotificationListFull is null in filterRejectedNotificationSubmit");
				rejectedNotifSummaryVOs = (ArrayList<Object> ) NBSContext.retrieve(request.getSession(true), "DSRejectedNotificationList");
			}
		}
		//NBSContext.store(request.getSession() ,"DSRejectedNotificationList", rejectedNotifSummaryVOs);
		sortNotifications(invForm, rejectedNotifSummaryVOs, true, request);
		request.setAttribute("rejectedNotificationList", rejectedNotifSummaryVOs);
		request.setAttribute("queueCount", String.valueOf(rejectedNotifSummaryVOs.size()));
		request.setAttribute("PageTitle","Rejected Notifications Queue");
		invForm.getAttributeMap().put("PageNumber", "1");

		return PaginationUtil.paginateForCommonForm(invForm, request, "rejectedQueue_notification_review",mapping);

	}
    @SuppressWarnings("unchecked")
	private Collection<Object>  filterRejectedNotification(InvestigationForm investigationForm, HttpServletRequest request) {

		Collection<Object>  investigationSummaryVOs = new ArrayList<Object> ();

		String srchCriteriaInv = null;
		String srchCriteriaCond = null;
		String srchCriteriaStatus = null;
		String sortOrderParam = null;
		String srchCriteriaDate = null;
		String srchCriteriaNotif = null;
		String srchCriteriaRecip=null;
		String srchCriteriaRejectedBy=null;

		try {

			Map searchCriteriaMap = investigationForm.getSearchCriteriaArrayMap();
			// Get the existing SummaryVO collection in the form
			ArrayList<Object> notificationSummaryVOs = null;
			try {
			  notificationSummaryVOs = (ArrayList<Object> ) NBSContext.retrieve(request.getSession() ,"DSRejectedNotificationListFull");
			}
			catch(Exception ex) {
				logger.debug("DSRejectedNotificationListFull is null in filterRejectedNotification");
				notificationSummaryVOs = (ArrayList<Object> ) NBSContext.retrieve(request.getSession(true), "DSRejectedNotificationList");
			}
			// Filter by the investigator
			investigationSummaryVOs = notifiUtil.getFilteredRejectedQNotifs(notificationSummaryVOs, searchCriteriaMap);
			NBSContext.store(request.getSession(true), "DSRejectedNotificationList",  investigationSummaryVOs);
			String[] inv = (String[]) searchCriteriaMap.get("SUBMITTEDBY");
			String[] cond = (String[]) searchCriteriaMap.get("CONDITION");
			String[] status = (String[]) searchCriteriaMap.get("STATUS");
			String[] startDate = (String[]) searchCriteriaMap.get("SUBMITDATE");
			String[] notif = (String[]) searchCriteriaMap.get("TYPE");
			String[] recipient = (String[]) searchCriteriaMap.get("RECIPIENT");
			String[] rejectedBy = (String[]) searchCriteriaMap.get("REJECTEDBY");

			String filterPatient = null;
			if(searchCriteriaMap.get("SearchText1_FILTER_TEXT")!=null){
				filterPatient = (String) searchCriteriaMap.get("SearchText1_FILTER_TEXT");
				request.setAttribute("PATIENT", filterPatient);
			}
			String filterComment = null;
			if(searchCriteriaMap.get("SearchText2_FILTER_TEXT")!=null){
				filterComment = (String) searchCriteriaMap.get("SearchText2_FILTER_TEXT");
				request.setAttribute("COMMENT", filterComment);
			}
			
			Integer invCount = new Integer(inv == null ? 0 : inv.length);
			Integer condCount = new Integer(cond == null  ? 0 : cond.length);
			Integer statusCount = new Integer(status == null  ? 0 : status.length);
			Integer startDateCount = new Integer(startDate == null ? 0 : startDate.length);
			Integer notifCount = new Integer(notif == null ? 0 : notif.length);
			Integer recipientCount = new Integer(recipient == null ? 0 : recipient.length);
			Integer rejectedByCount = new Integer(rejectedBy == null ? 0 : rejectedBy.length);

			// Do not filter if the selected values for filter is same as filtered list, but put the sortMethod, direction and criteria stuff
			if(invCount.equals((investigationForm.getAttributeMap().get("submittedByCount"))) &&

					(condCount.equals(investigationForm.getAttributeMap().get("ConditionsCount"))) &&
					(statusCount.equals(investigationForm.getAttributeMap().get("StatusCount"))) &&
					(startDateCount.equals(investigationForm.getAttributeMap().get("dateFilterListCount"))) &&
					(notifCount.equals(investigationForm.getAttributeMap().get("typeCount")))&&
					(recipientCount.equals(investigationForm.getAttributeMap().get("recipientCount")))&&
					(rejectedByCount.equals(investigationForm.getAttributeMap().get("rejectedByCount")))&&
					filterPatient == null && filterComment == null) {

				String sortMethod = getSortMethod(request, investigationForm);
				String direction = getSortDirection(request, investigationForm);
				if(sortMethod == null || (sortMethod != null && sortMethod.equals("none"))) {
					Map sColl =  investigationForm.getAttributeMap().get("searchCriteria") == null ? new TreeMap() : (TreeMap) investigationForm.getAttributeMap().get("searchCriteria");
					sortOrderParam = sColl.get("sortSt") == null ? "" : (String) sColl.get("sortSt");
				} else {
					sortOrderParam = notifiUtil.getSortCriteria(direction, sortMethod);
				}
				Map searchCriteriaColl = new TreeMap();
				searchCriteriaColl.put("sortSt", sortOrderParam);
				investigationForm.getAttributeMap().put("searchCriteria", searchCriteriaColl);
				return null;
			}


			ArrayList<Object> submitterList = investigationForm.getRejectedQsubmittedBy();
			ArrayList<Object> condList = investigationForm.getRejectedQconditions();
			ArrayList<Object> statusList = investigationForm.getRejectedQcaseStatus();
			ArrayList<Object> dateList = investigationForm.getRejectedQdateFilterList();
			ArrayList<Object> notiftypeList = investigationForm.getRejectedQtype();
			ArrayList<Object> recipientList = investigationForm.getRejectedQrecipient();
			ArrayList<Object> rejectedByList = investigationForm.getRejectedQRejectedBy();

			Map searchCriteriaColl = new TreeMap();
			String sortMethod = getSortMethod(request, investigationForm);
			String direction = getSortDirection(request, investigationForm);
			if(sortMethod == null || (sortMethod != null && sortMethod.equals("none"))) {
				Map sColl =  investigationForm.getAttributeMap().get("searchCriteria") == null ? new TreeMap() : (TreeMap) investigationForm.getAttributeMap().get("searchCriteria");
				sortOrderParam = sColl.get("sortSt") == null ? "" : (String) sColl.get("sortSt");
			} else {
				sortOrderParam = notifiUtil.getSortCriteria(direction, sortMethod);
			}

			srchCriteriaInv = queueUtil.getSearchCriteria(submitterList, inv, NEDSSConstants.FILTERBYSUBMITTEDBY);

			srchCriteriaCond = queueUtil.getSearchCriteria(condList, cond, NEDSSConstants.FILTERBYCONDITION);
			srchCriteriaStatus = queueUtil.getSearchCriteria(statusList, status, NEDSSConstants.FILTERBYSTATUS);
			srchCriteriaDate = queueUtil.getSearchCriteria(dateList, startDate, NEDSSConstants.FILTERBYDATE);
			srchCriteriaNotif = queueUtil.getSearchCriteria(notiftypeList, notif, NEDSSConstants.FILTERBYTYPE);
			srchCriteriaRecip = queueUtil.getSearchCriteria(recipientList, recipient, NEDSSConstants.FILTERBYRECIPIENT);
			srchCriteriaRejectedBy = queueUtil.getSearchCriteria(rejectedByList, rejectedBy, NEDSSConstants.FILTERBYREJECTEDBY);

			//set the error message to the form
			if(sortOrderParam != null)
				searchCriteriaColl.put("sortSt", sortOrderParam);
			if(srchCriteriaDate != null)
				searchCriteriaColl.put("INV147", srchCriteriaDate);
			if(srchCriteriaInv != null)
					searchCriteriaColl.put("INV100", srchCriteriaInv);
			if(srchCriteriaCond != null)
				searchCriteriaColl.put("INV169", srchCriteriaCond);
			if(srchCriteriaStatus != null)
				searchCriteriaColl.put("INV163", srchCriteriaStatus);
			if(srchCriteriaNotif != null)
				searchCriteriaColl.put("NOT118", srchCriteriaNotif);
			if(srchCriteriaRecip != null)
				searchCriteriaColl.put("NOT119", srchCriteriaRecip);
			if(srchCriteriaRejectedBy != null)
				searchCriteriaColl.put("NOT120", srchCriteriaRejectedBy);
			if(filterPatient != null)
				searchCriteriaColl.put("PATIENT", filterPatient);
			if(filterComment != null)
				searchCriteriaColl.put("COMMENT", filterComment);

			investigationForm.getAttributeMap().put("searchCriteria", searchCriteriaColl);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while filtering the investigation by Investigator: "+ e.toString());

		}
		return investigationSummaryVOs;
	}

private void sortNotifications(InvestigationForm investigationForm, Collection<Object>  rejectedNotifSummaryVOs, boolean existing, HttpServletRequest request) {

	// Retrieve sort-order and sort-direction from displaytag params
	String sortMethod = getSortMethod(request, investigationForm);
	String direction = getSortDirection(request, investigationForm);

	boolean invDirectionFlag = true;
	if (direction != null && direction.equals("2"))
		invDirectionFlag = false;

	//Read from properties file to determine default sort order
	if (sortMethod == null || (sortMethod != null && sortMethod.equals("none"))) {
			sortMethod = "getAddTime";
			invDirectionFlag = !properties.getMyProgramAreasQueueSortbyNewestInvStartdate();
	}

	NedssUtils util = new NedssUtils();
	if (sortMethod != null && rejectedNotifSummaryVOs != null
			&& rejectedNotifSummaryVOs.size() > 0) {
		updateNotificationSummaryVObeforeSort(rejectedNotifSummaryVOs);
		util.sortObjectByColumnGeneric(sortMethod,
				(Collection<Object>) rejectedNotifSummaryVOs, invDirectionFlag);
		//after sorting if sortMethod is getActivityFromTime replace fictious time with nulls
		_updateSummaryVosForDate(rejectedNotifSummaryVOs);
		updateNotificationSummaryVOAfterSort(rejectedNotifSummaryVOs);

	}
	if(!existing) {
		//Finally put sort criteria in form
		String sortOrderParam = notifiUtil.getSortCriteria(invDirectionFlag == true ? "1" : "2", sortMethod);
		Map searchCriteriaColl = new TreeMap();
		searchCriteriaColl.put("sortSt", sortOrderParam);
		investigationForm.getAttributeMap().put("searchCriteria", searchCriteriaColl);
	}

}
private void _updateSummaryVosForDate(Collection<Object> rejectedNotifSummaryVOs) {

	Iterator iter = rejectedNotifSummaryVOs.iterator();
	while (iter.hasNext()) {
		RejectedNotificationSummaryVO rejectedNotifSummaryVO = (RejectedNotificationSummaryVO) iter.next();
		if (rejectedNotifSummaryVO.getAddTime() != null && rejectedNotifSummaryVO.getAddTime().getTime() >  System.currentTimeMillis()) {
			rejectedNotifSummaryVO.setAddTime(null);
		}
	}
}
private void updateNotificationSummaryVOAfterSort(Collection<Object> rejectedNotifSummaryVOs) {
	Iterator iter = rejectedNotifSummaryVOs.iterator();
	while (iter.hasNext()) {
		RejectedNotificationSummaryVO rejectedNotifSummaryVO = (RejectedNotificationSummaryVO) iter.next();
		if (rejectedNotifSummaryVO.getAddUserName() != null && rejectedNotifSummaryVO.getAddUserName().equals("ZZZZZ")) {
			rejectedNotifSummaryVO.setAddUserName("");
		}
		if (rejectedNotifSummaryVO.getCaseClassCd() != null && rejectedNotifSummaryVO.getCaseClassCd().equals("ZZZZZ")) {
			rejectedNotifSummaryVO.setCaseClassCd("");
		}
	}

}
private void updateNotificationSummaryVObeforeSort(Collection<Object> rejectedNotifSummaryVOs) {
	Iterator iter = rejectedNotifSummaryVOs.iterator();
	while (iter.hasNext()) {
		RejectedNotificationSummaryVO rejectedNotifSummaryVO = (RejectedNotificationSummaryVO) iter.next();

		if (rejectedNotifSummaryVO.getAddUserName() == null || (rejectedNotifSummaryVO.getAddUserName() != null && rejectedNotifSummaryVO.getAddUserName().equals(""))) {
			rejectedNotifSummaryVO.setAddUserName("ZZZZZ");
		}

		if (rejectedNotifSummaryVO.getCaseClassCd() == null || rejectedNotifSummaryVO.getCaseClassCd() != null && rejectedNotifSummaryVO.getCaseClassCd().equals("")) {
			rejectedNotifSummaryVO.setCaseClassCd("ZZZZZ");
		}
		if(rejectedNotifSummaryVO.getAddTime() == null || (rejectedNotifSummaryVO.getAddTime()!= null && rejectedNotifSummaryVO.equals(""))) {
		   Timestamp ts = new Timestamp (0) ;
		   Calendar cal = GregorianCalendar.getInstance();
		   cal.setTimeInMillis (0);
		   cal.set( 5000,0,1) ;
		   ts.setTime(cal.getTimeInMillis());
		   rejectedNotifSummaryVO.setAddTime(ts);
	   }


	}

}
		private String getSortMethod(HttpServletRequest request, InvestigationForm investigationForm) {
			if (PaginationUtil._dtagAccessed(request)) {
				return request.getParameter((new ParamEncoder("parent")).encodeParameterName(TableTagParameters.PARAMETER_SORT));
		} else{
			return investigationForm.getAttributeMap().get("methodName") == null ? null : (String) investigationForm.getAttributeMap().get("methodName");
			}

		}
		private String getSortDirection(HttpServletRequest request, InvestigationForm investigationForm) {
			if (PaginationUtil._dtagAccessed(request)) {
				return request.getParameter((new ParamEncoder("parent")).encodeParameterName(TableTagParameters.PARAMETER_ORDER));
			} else{
				return investigationForm.getAttributeMap().get("sortOrder") == null ? "1": (String) investigationForm.getAttributeMap().get("sortOrder");
			}

		}

}
