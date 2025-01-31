package gov.cdc.nedss.webapp.nbs.action.notification;

import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.InvestigationSummaryVO;
import gov.cdc.nedss.proxy.ejb.notificationproxyejb.vo.NotificationSummaryVO;
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
import gov.cdc.nedss.util.StringUtils;
import gov.cdc.nedss.webapp.nbs.action.notification.util.NotificationUtil;
import gov.cdc.nedss.webapp.nbs.action.person.util.PersonUtil;
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

/**
 * Title:        ReviewNotificationLoad
 * Description:  This is a load action class for loading review notification page.
 * According to the Context Map<Object,Object> what the from page is and current task and security permission,
 * this method loads the review notification page.
 * Copyright:    Copyright (c) 2002
 * Company:      Computer Sciences Corporation
 * @author:      NEDSS Development Team
 * @version      NBS1.1
 */


public class ReviewNotificationLoad
    extends DispatchAction
{

    //For logging
    static final LogUtils logger = new LogUtils(ReviewNotificationLoad.class.getName());
    protected InvestigationSummaryVO invSumVO = null;
    protected NotificationSummaryVO notSumVO = null;
    static final String APPROVE = "Approve|Reject";
    NotificationUtil notifiUtil = new NotificationUtil();
    QueueUtil queueUtil = new QueueUtil();
    PropertyUtil properties = PropertyUtil.getInstance();



    /**
     * According to the Context Map<Object,Object> what the from page is and current task and security permission,
     * this method loads the review notification page.
     * @param ActionMapping
     * @param ActionForm
     * @param HttpServletRequest
     * @param HttpServletResponse
     * @return ActionForward
     * @throws IOException
     * @throws ServletException
     */

    @SuppressWarnings("unchecked")
	public ActionForward loadQueue(ActionMapping mapping, ActionForm aForm,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
                          throws IOException, ServletException
    {

		String confirmMessage= request.getAttribute("confirmationMessage") == null ? "" : (String)request.getAttribute("confirmationMessage");
		//When a confirmation Message is returned, a successful Approve/Reject action has been taken by the user, reload the queue
		if(confirmMessage.length() > 0) {
			request.setAttribute("confirmationMessage",confirmMessage);
		}

    	InvestigationForm invForm = (InvestigationForm) aForm;
		boolean forceEJBcall = false;
		// Reset Pagination first time
		boolean initLoad = request.getParameter("initLoad") == null ? false: Boolean.valueOf(request.getParameter("initLoad")).booleanValue();
		//Reset even if confirmMessage has value
		if ((initLoad && !PaginationUtil._dtagAccessed(request)) || (!confirmMessage.equals(""))  ) {
			if(initLoad && !PaginationUtil._dtagAccessed(request))
				invForm.clearAll();
			forceEJBcall = true;
			// get the number of records to be displayed per page
			Integer queueSize = properties.getQueueSize(NEDSSConstants.APPROVAL_NOTIFICATION_QUEUE_SIZE);
			invForm.getAttributeMap().put("queueSize", queueSize);
		}

		//To make sure SelectAll is checked, see if no criteria is applied
		if(invForm.getSearchCriteriaArrayMap().size() == 0)
			request.setAttribute("ActionMode", HTMLEncoder.encodeHtml("InitLoad"));

        HttpSession session = request.getSession();

        //get the pageContext
        String contextAction = request.getParameter("ContextAction");

        if (contextAction != null){
				if ( contextAction.equals("NNDApproval")) {

        	DSQueueObject dSQueueObject = new DSQueueObject();
        	dSQueueObject.setDSSortColumn("getAddTime");
        	dSQueueObject.setDSSortDirection("true");
        	dSQueueObject.setDSFromIndex("0");
        	dSQueueObject.setDSQueueType(NEDSSConstants.NND_NOTIFICATIONS_FOR_APPROVAL);
			NBSContext.store(session, "DSQueueObject", dSQueueObject);
		}else if((contextAction.equals("ReturnToReviewNotifications"))||(contextAction.equals("ReturnToNotificationApprovalQueue"))) {
			forceEJBcall = true;
		}

	    TreeMap<Object,Object> tm = NBSContext.getPageContext(session, "PS114", contextAction);
        ErrorMessageHelper.setErrMsgToRequest(request, "PS114");

        NBSContext.lookInsideTreeMap(tm);
        String sCurrTask = NBSContext.getCurrentTask(session);
        request.setAttribute("ViewInvestigationHref","/nbs/" + sCurrTask + ".do?ContextAction=" +tm.get("InvestigationID"));
        request.setAttribute("ViewFileHref","/nbs/" + sCurrTask + ".do?ContextAction=" + tm.get("ViewFile"));
        request.setAttribute("ApproveButton", "/nbs/" + sCurrTask + ".do");
        request.setAttribute("ContextActionApprove", tm.get("Approve"));
        request.setAttribute("RejectButton", "/nbs/" + sCurrTask + ".do");
        request.setAttribute("ContextActionReject", tm.get("Reject"));

        }

        try
        {
    			ArrayList<Object> notificationsForApproval = new ArrayList<Object> ();

                if(forceEJBcall) {

                    MainSessionCommand msCommand = null;
                    MainSessionHolder mainSessionHolder = new MainSessionHolder();
                    msCommand = mainSessionHolder.getMainSessionCommand(session);
                    logger.info("You are trying to GET_NOTIFICATIONS_FOR_APPROVAL");
                    String  sBeanJndiName = JNDINames.NOTIFICATION_PROXY_EJB;
                    String  sMethod = "getNotificationsForApproval";
                    logger.debug("calling getNotificationsForApproval on NotificationProxyEJB from taskListDisplayLoad");
                    ArrayList<Object> arr = (ArrayList<Object> )msCommand.processRequest(sBeanJndiName, sMethod, null);
                    notificationsForApproval = (ArrayList<Object> )arr.get(0);
                    //accessedEJB = true;  // Used to make sure that results gets sorted later on.

                    // Populate nndAssociated indicator based on the business rules
                 //   populateNndAssociated(notificationsForApproval);

                    NBSContext.store(session,"DSNotificationList",notificationsForApproval);
                    NBSContext.store(session,"DSNotificationListFull",notificationsForApproval);
                    invForm.initializeDropDowns(notificationsForApproval);
                    invForm.getAttributeMap().put("submittedByCount",new Integer(invForm.getSubmittedBy().size()));
                    invForm.getAttributeMap().put("ConditionsCount",new Integer(invForm.getConditions().size()));
                    invForm.getAttributeMap().put("StatusCount",new Integer(invForm.getCaseStatus().size()));
                    invForm.getAttributeMap().put("dateFilterListCount",new Integer(invForm.getDateFilterList().size()));
                    invForm.getAttributeMap().put("typeCount",new Integer(invForm.getType().size()));
                    invForm.getAttributeMap().put("recipientCount",new Integer(invForm.getRecipient().size()));

                } else {
                	notificationsForApproval = (ArrayList<Object> )NBSContext.retrieve(session ,"DSNotificationList");
                }

                boolean existing = request.getParameter("existing") == null ? false : true;
                if((contextAction != null && contextAction.equalsIgnoreCase("ReturnToReviewNotifications")) || (contextAction != null && contextAction.equalsIgnoreCase("ReturnToNotificationApprovalQueue")) || (contextAction != null && (contextAction.equalsIgnoreCase("Approve") || contextAction.equalsIgnoreCase("Reject")))){
    				updatePatPhcLinks(notificationsForApproval, request);
    				//FilterInvs return NULL if all the filters are applied, so make sure it is not null before assigning back the coll
    				Collection<Object>  filteredColl = filterNotification(invForm, request);
    				if(filteredColl != null)
    					notificationsForApproval = (ArrayList<Object> ) filteredColl;
    				sortNotifications(invForm, notificationsForApproval, true, request);
    			}
    			else {
    				sortNotifications(invForm, notificationsForApproval, existing, request);

    				if(!existing) {
    					updatePatPhcLinks(notificationsForApproval, request);
    				} else
    					filterNotification(invForm, request);
    			}
                request.setAttribute("notificationList", notificationsForApproval);
                PropertyUtil properties = PropertyUtil.getInstance();
    			Integer queueSize = properties.getQueueSize(NEDSSConstants.APPROVAL_NOTIFICATION_QUEUE_SIZE);
    			invForm.getAttributeMap().put("queueSize", queueSize);

    			request.setAttribute("queueCount",String.valueOf(notificationsForApproval.size()));
    			NBSContext.store(session,"DSNotificationList",notificationsForApproval);
    			request.setAttribute("PageTitle","Approval Queue for Initial Notifications");


        }
        catch (Exception e)
        {
            e.printStackTrace();
            logger.error(
                    "Error in taskListDisplayLoad in getting notifications for approval from EJB" + e.getMessage());
        }

        return PaginationUtil.paginateForCommonForm(invForm, request, "notificationqueue_review",mapping);


    }

    private String getPatientFullName(NotificationSummaryVO notificationSummaryVO) {
    	StringBuffer buff = new StringBuffer();
    	if ( notificationSummaryVO.getLastNm() != null && notificationSummaryVO.getLastNm().trim() != "")
    		buff.append(notificationSummaryVO.getLastNm().trim());
    	if (notificationSummaryVO.getLastNm() != null
    			&& notificationSummaryVO.getFirstNm() != null
    			&& notificationSummaryVO.getLastNm().trim() != ""
    			&& notificationSummaryVO.getFirstNm().trim() != "")
    		buff.append(",   ");
    	if (notificationSummaryVO.getFirstNm() != null
    			&& notificationSummaryVO.getFirstNm().trim() != "")
    		buff.append(notificationSummaryVO.getFirstNm().trim());
    	String patientFullName = buff.toString();
    	return patientFullName;
    }

    private String getAge(NotificationSummaryVO notSum){
 	   String currentAgeAndUnits = "";
 		String ageAndUnit="";

 		if (notSum.getBirthTimeCalc() != null) {
 			  	currentAgeAndUnits = PersonUtil.displayAge(StringUtils.formatDate(
 	         	notSum.getBirthTimeCalc()));
      	}

  		if (currentAgeAndUnits.length()>0) {
 			int pipe = currentAgeAndUnits.indexOf('|');
 				String age = currentAgeAndUnits.substring(0, pipe) ;
              		String unit = currentAgeAndUnits.substring(pipe + 1);

           	   		if (unit.equals("Y")){
         			ageAndUnit= age + " Years";
   	   			} else if(unit.equals("M")) {
   					ageAndUnit= age + " Months";
    				} else {
          				ageAndUnit = age + " Days";
              		}
          	}
  		return ageAndUnit;
    }


    private void updatePatPhcLinks(ArrayList<Object>  notificationList,
			HttpServletRequest request) {

    	String dateTimeOut="";
    	String ID="";
    	String sex="";
    	String ageAndUnit="";
		if (notificationList != null && notificationList.size() != 0) {
			for (int i = 0; i < notificationList.size(); i++) {
				NotificationSummaryVO notificationSummaryVO = (NotificationSummaryVO) notificationList.get(i);

				if(getAge(notificationSummaryVO)!=null){
				 ageAndUnit=getAge(notificationSummaryVO);
				}
				if(notificationSummaryVO.getBirthTimeCalc()!=null){
				java.text.SimpleDateFormat sdfInput = new java.text.SimpleDateFormat("MM/dd/yyyy",java.util.Locale.US);
				dateTimeOut=sdfInput.format(notificationSummaryVO.getBirthTimeCalc());
				}
				if(notificationSummaryVO.getPublicHealthCaseLocalId()!=null){
				String localId = notificationSummaryVO.getPublicHealthCaseLocalId();
				 //ID = PersonUtil.getDisplayLocalID(localId);
				}
				if(notificationSummaryVO.getMPRUid()!=null){
					ID = notificationSummaryVO.getMPRUid().toString();					 
					}
				
				if( notificationSummaryVO.getCurrSexCdDesc()!=null){
				 sex = notificationSummaryVO.getCurrSexCdDesc();
				}
				String patTitle= "Sex:&nbsp;&nbsp;&nbsp;" + sex + "&#13;" +
								  "DOB:&nbsp;&nbsp;&nbsp;" + dateTimeOut + "&#13;" +
								  "Age:&nbsp;&nbsp;&nbsp;" + ageAndUnit + "&#13;"+
								  "ID:&nbsp;&nbsp;&nbsp;" + ID + "&#13;";
				String patFullName = getPatientFullName(notificationSummaryVO);
				notificationSummaryVO.setPatientFullName(patFullName);
				if(notificationSummaryVO.getRecipient()==null){
					if(notificationSummaryVO.getNndInd()!=null && notificationSummaryVO.getNndInd().equals(NEDSSConstants.YES))
						notificationSummaryVO.setRecipient(NEDSSConstants.ADMINFLAGCDC);
	            	else
	            		notificationSummaryVO.setRecipient(NEDSSConstants.LOCAl_DESC);
				}
				//notificationSummaryVO.setRecipient(notificationSummaryVO.getRecipient()== null ? NEDSSConstants.ADMINFLAGCDC : notificationSummaryVO.getRecipient());
				String viewFileHref = request.getAttribute("ViewFileHref") == null ? "" : (String) request.getAttribute("ViewFileHref");
				if(!viewFileHref.equals("")) {
					String patLink = "<a title=\"" + patTitle + "\"href=\"" + viewFileHref  +"&MPRUid="+ String.valueOf(notificationSummaryVO.getMPRUid()) + "\">" + patFullName + "</a>";
					notificationSummaryVO.setPatientFullNameLnk(patLink);
				}
				String condDesc = notificationSummaryVO.getCdTxt();
				notificationSummaryVO.setCdTxt(condDesc);
				String viewHref = request.getAttribute("ViewInvestigationHref") == null ? "": (String) request.getAttribute("ViewInvestigationHref");
				if(!viewHref.equals("")) {
					String phcLink = "<a  href=\"" + viewHref+"&publicHealthCaseUID="+ String.valueOf(notificationSummaryVO.getPublicHealthCaseUid()) + "\">"+ condDesc + "</a>";
					notificationSummaryVO.setConditionCodeTextLnk(phcLink);
				}
				//reset
				 dateTimeOut="";
		    	 ID="";
		    	 sex="";
		    	 ageAndUnit="";

			}

		}
	}

    @SuppressWarnings("unchecked")
	public ActionForward filterNotificationSubmit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

    	InvestigationForm invForm = (InvestigationForm) form;
		Collection<Object>  notificationSummaryVOs = filterNotification(invForm, request);
		if(notificationSummaryVOs != null){
			request.setAttribute("ActionMode", HTMLEncoder.encodeHtml("Search"));
		//When all filters are applied (equivalent to no filter applied), Collection<Object>  is set to null in which case retrieve it from Form.
		}else{
			try {
			notificationSummaryVOs = (ArrayList<Object> ) NBSContext.retrieve(request.getSession() ,"DSNotificationListFull");
		}catch(Exception ex) {
			logger.debug("DSNotificationListFull is null in review notification load");
			notificationSummaryVOs = (ArrayList<Object> ) NBSContext.retrieve(request.getSession(true) , "DSNotificationList");
		}
		}
		HttpSession session = request.getSession();

        //get the pageContext
        String contextAction = request.getParameter("ContextAction");
		TreeMap tm = NBSContext.getPageContext(session, "PS114", contextAction);
	    ErrorMessageHelper.setErrMsgToRequest(request, "PS114");

	    NBSContext.lookInsideTreeMap(tm);
	    String sCurrTask = NBSContext.getCurrentTask(session);
	   // NBSContext.store(session,"DSNotificationList",notificationSummaryVOs);
		sortNotifications(invForm, notificationSummaryVOs, true, request);
		request.setAttribute("notificationList", notificationSummaryVOs);
		request.setAttribute("queueCount", String.valueOf(notificationSummaryVOs.size()));
		request.setAttribute("PageTitle","Approval Queue for Initial Notifications");
		request.setAttribute("ViewInvestigationHref","/nbs/" + sCurrTask + ".do?ContextAction=" +tm.get("InvestigationID"));
        request.setAttribute("ViewFileHref","/nbs/" + sCurrTask + ".do?ContextAction=" + tm.get("ViewFile"));
        request.setAttribute("ApproveButton", "/nbs/" + sCurrTask + ".do");
        request.setAttribute("ContextActionApprove", tm.get("Approve"));
        request.setAttribute("RejectButton", "/nbs/" + sCurrTask + ".do");
        request.setAttribute("ContextActionReject", tm.get("Reject"));
		invForm.getAttributeMap().put("PageNumber", "1");

		return PaginationUtil.paginateForCommonForm(invForm, request, "notificationqueue_review",mapping);

	}
    private Collection<Object>  filterNotification(InvestigationForm investigationForm, HttpServletRequest request) {

		Collection<Object>  investigationSummaryVOs = new ArrayList<Object> ();

		String srchCriteriaInv = null;
		String srchCriteriaCond = null;
		String srchCriteriaStatus = null;
		String sortOrderParam = null;
		String srchCriteriaDate = null;
		String srchCriteriaNotif = null;
		String srchCriteriaRecip=null;

		try {

			Map searchCriteriaMap = investigationForm.getSearchCriteriaArrayMap();
			// Get the existing SummaryVO collection in the form
			@SuppressWarnings("unchecked")
			ArrayList<Object> invSummaryVOs = null;
			try {
			  invSummaryVOs = (ArrayList<Object> ) NBSContext.retrieve(request.getSession() ,"DSNotificationListFull");
			}
			catch(Exception ex) {
				logger.debug("DSNotificationListFull is null in Program Area Load filterInvs");
				invSummaryVOs = (ArrayList<Object> ) NBSContext.retrieve(request.getSession(true), "DSNotificationList");
			}

			// Filter by the investigator
			investigationSummaryVOs = notifiUtil.getFilteredInvestigation(invSummaryVOs, searchCriteriaMap);
			NBSContext.store(request.getSession(true), "DSNotificationList",  investigationSummaryVOs);
			String[] inv = (String[]) searchCriteriaMap.get("SUBMITTEDBY");
			String[] cond = (String[]) searchCriteriaMap.get("CONDITION");
			String[] status = (String[]) searchCriteriaMap.get("STATUS");
			String[] startDate = (String[]) searchCriteriaMap.get("SUBMITDATE");
			String[] notif = (String[]) searchCriteriaMap.get("TYPE");
			String[] recipient = (String[]) searchCriteriaMap.get("RECIPIENT");

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

			// Do not filter if the selected values for filter is same as filtered list, but put the sortMethod, direction and criteria stuff
			if(invCount.equals((investigationForm.getAttributeMap().get("submittedByCount"))) &&

					(condCount.equals(investigationForm.getAttributeMap().get("ConditionsCount"))) &&
					(statusCount.equals(investigationForm.getAttributeMap().get("StatusCount"))) &&
					(startDateCount.equals(investigationForm.getAttributeMap().get("dateFilterListCount"))) &&
					(notifCount.equals(investigationForm.getAttributeMap().get("typeCount")))&&
					(recipientCount.equals(investigationForm.getAttributeMap().get("recipientCount")))&&
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
				//if(investigationForm.getAttributeMap().get("searchCriteria") == null)
				investigationForm.getAttributeMap().put("searchCriteria", searchCriteriaColl);
				return null;
			}


			ArrayList<Object> submitterList = investigationForm.getSubmittedBy();
			ArrayList<Object> condList = investigationForm.getConditions();
			ArrayList<Object> statusList = investigationForm.getCaseStatus();
			ArrayList<Object> dateList = investigationForm.getDateFilterList();
			ArrayList<Object> notiftypeList = investigationForm.getType();
			ArrayList<Object> recipientList = investigationForm.getRecipient();

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

private void sortNotifications(InvestigationForm investigationForm, Collection<Object>  notificationSummaryVOs, boolean existing, HttpServletRequest request) {

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
	if (sortMethod != null && notificationSummaryVOs != null
			&& notificationSummaryVOs.size() > 0) {
		updateNotificationSummaryVObeforeSort(notificationSummaryVOs);
		util.sortObjectByColumnGeneric(sortMethod,
				(Collection<Object>) notificationSummaryVOs, invDirectionFlag);
		//after sorting if sortMethod is getActivityFromTime replace fictious time with nulls
		_updateSummaryVosForDate(notificationSummaryVOs);
		updateNotificationSummaryVOAfterSort(notificationSummaryVOs);

	}
	if(!existing) {
		//Finally put sort criteria in form
		String sortOrderParam = notifiUtil.getSortCriteria(invDirectionFlag == true ? "1" : "2", sortMethod);
		Map searchCriteriaColl = new TreeMap();
		searchCriteriaColl.put("sortSt", sortOrderParam);
		investigationForm.getAttributeMap().put("searchCriteria", searchCriteriaColl);
	}

}
private void _updateSummaryVosForDate(Collection<Object> notifiSummaryVOs) {

	Iterator iter = notifiSummaryVOs.iterator();
	while (iter.hasNext()) {
		NotificationSummaryVO notificationSummaryVO  = (NotificationSummaryVO) iter.next();
		if (notificationSummaryVO.getAddTime() != null && notificationSummaryVO.getAddTime().getTime() >  System.currentTimeMillis()) {
			notificationSummaryVO.setAddTime(null);
		}
	}
}
private void updateNotificationSummaryVOAfterSort(Collection<Object> notifiSummaryVOs) {
	Iterator iter = notifiSummaryVOs.iterator();
	while (iter.hasNext()) {
		NotificationSummaryVO notificationSummaryVO  = (NotificationSummaryVO) iter.next();
		if (notificationSummaryVO.getAddUserName() != null && notificationSummaryVO.getAddUserName().equals("ZZZZZ")) {
			notificationSummaryVO.setAddUserName("");
		}
		if (notificationSummaryVO.getCaseClassCdTxt() != null && notificationSummaryVO.getCaseClassCdTxt().equals("ZZZZZ")) {
			notificationSummaryVO.setCaseClassCdTxt("");
		}
	}

}
private void updateNotificationSummaryVObeforeSort(Collection<Object> notifiSummaryVOs) {
	Iterator iter = notifiSummaryVOs.iterator();
	while (iter.hasNext()) {
		NotificationSummaryVO notificationSummaryVO  = (NotificationSummaryVO) iter.next();

		if (notificationSummaryVO.getAddUserName() == null || (notificationSummaryVO.getAddUserName() != null && notificationSummaryVO.getAddUserName().equals(""))) {
			notificationSummaryVO.setAddUserName("ZZZZZ");
		}

		if (notificationSummaryVO.getCaseClassCdTxt() == null || notificationSummaryVO.getCaseClassCdTxt() != null && notificationSummaryVO.getCaseClassCdTxt().equals("")) {
			notificationSummaryVO.setCaseClassCdTxt("ZZZZZ");
		}
		if(notificationSummaryVO.getAddTime() == null || (notificationSummaryVO.getAddTime()!= null && notificationSummaryVO.equals(""))) {
		   Timestamp ts = new Timestamp (0) ;
		   Calendar cal = GregorianCalendar.getInstance();
		   cal.setTimeInMillis (0);
		   cal.set( 5000,0,1) ;
		   ts.setTime(cal.getTimeInMillis());
		   notificationSummaryVO.setAddTime(ts);
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
