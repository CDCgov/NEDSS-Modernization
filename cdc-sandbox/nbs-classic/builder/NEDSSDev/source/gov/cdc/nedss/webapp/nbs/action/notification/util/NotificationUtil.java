package gov.cdc.nedss.webapp.nbs.action.notification.util;

import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.InvestigationSummaryVO;
import gov.cdc.nedss.proxy.ejb.notificationproxyejb.vo.NotificationSummaryVO;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.StringUtils;
import gov.cdc.nedss.webapp.nbs.action.util.QueueUtil;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


/**
 * Title:        NotificationUtil
 * Description:  This is a utiltiy class for building Notifications section on View Investigation
 * Copyright:    Copyright (c) 2006
 * Company:      Computer Sciences Corporation
 * @author:      NEDSS Development Team
 * @version      NBS1.1
 */
public class NotificationUtil {
	QueueUtil queueUtil = new QueueUtil();
	/**
	 *
	 * @param notificationSummaryVOCollection
	 * @return
	 */

	public Map<Object,Object> createIndividualNotificationMap(Collection<Object>  notificationSummaryVOCollection){
		Map notificationMap = new HashMap<Object,Object>();
		if(notificationSummaryVOCollection!=null && notificationSummaryVOCollection.size()>0){

			Iterator ite = notificationSummaryVOCollection.iterator();
			while(ite.hasNext()){
				NotificationSummaryVO notSum = (NotificationSummaryVO)ite.next();
				if (notificationMap.containsKey(notSum.getNotificationUid())) {
					((Collection<Object>) notificationMap.get(notSum
							.getNotificationUid())).add(notSum);
				} else {
					Collection<Object>  notCollection  = new ArrayList<Object> ();
					notCollection.add(notSum);
					notificationMap.put(notSum.getNotificationUid(),notCollection);
				}
			}
		}
		return notificationMap;
	}
	public String buildNotificationList(Collection<Object>  notificationSummaryVOCollection) {

		boolean isFirst = true;
		int recordIndex = 0;
		Map notificationMap = createIndividualNotificationMap(notificationSummaryVOCollection);

		int notificationsSize = notificationMap.size();

		StringBuffer sb = new StringBuffer();

		sb.append(displayPrimaryNotification());


		if(notificationsSize == 0) {

			sb.append("<tr>");
			sb.append("<td colspan=\"7\">");
			sb.append("<p>");
			sb.append("<center>");
			sb.append("<b>There is no information to display</b>");
			sb.append("</center>");
			sb.append("</p>");
			sb.append("</td>");
			sb.append("</tr>");
			sb.append("</table></td></tr></table>");

			return sb.toString();
		}

		else{
			Set notificationKeys = notificationMap.keySet();
			Iterator ite = notificationKeys.iterator();
			while(ite.hasNext()){
				Long key = (Long)ite.next();
				Collection<Object>  notificationCollection  = (Collection)notificationMap.get(key);
				int notCollection  =notificationCollection.size();
				isFirst = true;
			CachedDropDownValues cdv = new CachedDropDownValues();
			Iterator anIter = notificationCollection.iterator();

			String positionClass = "";
			if (recordIndex % 2 == 1) {
				positionClass = "even";
			}
			else {
				positionClass = "odd";
			}

			while(anIter.hasNext()) {

				String dateCreated ="";
				String dateSent = "";
				String jurisdictionDescTxt ="";
				String caseStatus = "";
				String recordStatus = "";
				String comments = "";
				String type ="";
				String recipient="";


				NotificationSummaryVO notSum = (NotificationSummaryVO) anIter.next();

				//ignore records that have recordStatus as "IN_BATCH_PROCESS"
				if(notSum.getRecordStatusCd().equals("IN_BATCH_PROCESS"))
					continue;

				recordStatus = notSum.getRecordStatusCd();
				dateCreated = StringUtils.formatDate(notSum.getRecordStatusTime());

				if(recordStatus.equals("COMPLETED"))
					dateSent = StringUtils.formatDate(notSum.getRptSentTime());


				jurisdictionDescTxt = cdv.getJurisdictionDesc(notSum.getJurisdictionCd());
				caseStatus = notSum.getCaseClassCdTxt() == null ? "" : notSum.getCaseClassCdTxt();
				comments = notSum.getTxt() == null ? "" : notSum.getTxt();

				type = notSum.getNotificationSrtDescCd();
				recipient = notSum.getRecipient();

				String parentClass = "parent_" + notSum.getNotificationUid();
				String childClass = "child_" + notSum.getNotificationUid();
				String invisibleChildClass = "none " + childClass;

				if(isFirst) {
					sb.append("<tr class=\"" + parentClass + " " + positionClass + "\" >");
					if(notCollection  > 1 )
						sb.append("<td align=\"center\" valign=\"middle\"><img alt=\"\" border=\"0\" " +
								"src=\"plus_sign.gif\" onclick=\"displayNotificationHistory('" + parentClass + "');\"/></td>");

					else {
						sb.append("<td></td>");
					}
				} else {
					sb.append("<tr class=\"" + invisibleChildClass + " " + positionClass+ "\">");
					sb.append("<td></td>");
				}

				sb.append("<td>" + dateCreated +"</td>");
				sb.append("<td>" + dateSent +"</td>");
				sb.append("<td>" + "<![CDATA["+ jurisdictionDescTxt +"]]>"+ "</td>");
				sb.append("<td>" + caseStatus + "</td>");
				sb.append("<td>" + recordStatus + "</td>");
				sb.append("<td>" + type + "</td>");
				sb.append("<td>" + recipient + "</td>");
				sb.append("<td> </td>");
				sb.append("</tr>");

				if(isFirst)
					sb.append("<tr class=\"" + positionClass + "\">");
				else
					sb.append("<tr class=\"" + invisibleChildClass + " " + positionClass+ "\">");

				sb.append("<td></td>");
				sb.append("<td class=\"ColumnHeader\" colspan=\"8\"><b>Comments: </b>" +"<![CDATA["+ comments +"]]>"+ "</td>");
				sb.append("</tr>");

				if(anIter.hasNext())
				{
					sb.append("<tr class=\"" + invisibleChildClass + "\">");
					sb.append("<td colspan=\"8\">");
					sb.append("<img class=\"DarkGray\" border=\"0\" height=\"2\" width=\"100%\" src=\"transparent.gif\" alt=\"\"/>");
					sb.append("</td>");
					sb.append("</tr>");
				}
				else {
					sb.append("<tr>");
					sb.append("<td colspan=\"8\">");
					sb.append("<img class=\"DarkGray\" border=\"0\" height=\"2\" width=\"100%\" src=\"transparent.gif\" alt=\"\"/>");
					sb.append("</td>");
					sb.append("</tr>");
				}

				isFirst = false;
			}
			recordIndex++;
		}
		sb.append("</table></td></tr></table>");
		return sb.toString();
		}
	}

	/**
	 *
	 * @return
	 */
	private String displayPrimaryNotification() {

		StringBuffer sb = new StringBuffer();

		sb.append("<table width=\"100%\" border=\"0\" class=\"TableOuter\">");
		sb.append("<tr>");
        sb.append("<td>");
		sb.append("<table id=\"notificationHistoryTable\" class=\"TableInner\" width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"4\">");
		sb.append("<thead class=\"nestedElementsTypeFootHeader\">");
		sb.append("<tr class=\"Shaded\">");
		sb.append("<td align=\"left\" class=\"ColumnHeader\"><b></b></td>");
		sb.append("<td align=\"left\" class=\"ColumnHeader\"><b>Status Change Date</b></td>");
		sb.append("<td align=\"left\" class=\"ColumnHeader\"><b>Date Sent</b></td>");
		sb.append("<td align=\"left\" class=\"ColumnHeader\"><b>Jurisdiction</b></td>");
		sb.append("<td align=\"left\" class=\"ColumnHeader\"><b>Case Status</b></td>");
		sb.append("<td align=\"left\" class=\"ColumnHeader\"><b>Status</b></td>");
		sb.append("<td align=\"left\" class=\"ColumnHeader\"><b>Type</b></td>");
		sb.append("<td align=\"left\" class=\"ColumnHeader\"><b>Recipient</b></td>");
		sb.append("<td align=\"left\" class=\"ColumnHeader\"><b></b></td>");
		sb.append("</tr>");
		sb.append("</thead>");

		return sb.toString();

	}
	public String getSortCriteria(String sortOrder, String methodName){
		String sortOrdrStr = null;
		if(methodName != null) {
			if(methodName.equals("getAddTime"))
				sortOrdrStr = "Submit Date";
			else if(methodName.equals("getAddUserName"))
				sortOrdrStr = "Submitted By";
			else if(methodName.equals("getPatientFullName"))
				sortOrdrStr = "Patient";
			else if(methodName.equals("getCdTxt"))
				sortOrdrStr = "Condition";
			else if(methodName.equals("getCaseClassCdTxt"))
				sortOrdrStr = "Status";
			else if(methodName.equals("getNotificationSrtDescCd"))
				sortOrdrStr = "Type";
			else if(methodName.equals("getRecipient"))
				sortOrdrStr = "Recipient";
			else if(methodName.equals("getTxt"))
				sortOrdrStr = "Comments";
		} else {
			sortOrdrStr = "Submit Date";
		}

		if(sortOrder == null || (sortOrder != null && sortOrder.equals("1")))
			sortOrdrStr = sortOrdrStr+" in ascending order ";
		else if(sortOrder != null && sortOrder.equals("2"))
			sortOrdrStr = sortOrdrStr+" in descending order ";

		return sortOrdrStr;

	}
	public Collection<Object>  getFilteredInvestigation(Collection<Object>  notificationSummaryVOCollection,
			Map searchCriteriaMap) {

    	String[] inv = (String[]) searchCriteriaMap.get("SUBMITTEDBY");
		String[] cond = (String[]) searchCriteriaMap.get("CONDITION");
		String[] status = (String[]) searchCriteriaMap.get("STATUS");
		String[] startDate = (String[]) searchCriteriaMap.get("SUBMITDATE");
		String[] notif = (String[]) searchCriteriaMap.get("TYPE");
		String[] recipient = (String[]) searchCriteriaMap.get("RECIPIENT");
		String filterPatient = null;
		if(searchCriteriaMap.get("SearchText1_FILTER_TEXT")!=null){
			filterPatient = (String) searchCriteriaMap.get("SearchText1_FILTER_TEXT");
		}
		String filterComment = null;
		if(searchCriteriaMap.get("SearchText2_FILTER_TEXT")!=null){
			filterComment = (String) searchCriteriaMap.get("SearchText2_FILTER_TEXT");
		}
		
		Map invMap = new HashMap<Object,Object>();

		Map condMap = new HashMap<Object,Object>();
		Map statusMap = new HashMap<Object,Object>();
		Map dateMap = new HashMap<Object,Object>();
		Map notifMap = new HashMap<Object,Object>();
		Map recipientMap = new HashMap<Object,Object>();

		if (inv != null && inv.length > 0)
			invMap = queueUtil.getMapFromStringArray(inv);

		if (cond != null && cond.length > 0)
			condMap = queueUtil.getMapFromStringArray(cond);
		if (status != null && status.length > 0)
			statusMap = queueUtil.getMapFromStringArray(status);
		if (startDate != null && startDate.length >0)
			dateMap = queueUtil.getMapFromStringArray(startDate);
		if (notif != null && notif.length >0)
			notifMap = queueUtil.getMapFromStringArray(notif);
		if (recipient != null && recipient.length >0)
			recipientMap = queueUtil.getMapFromStringArray(recipient);

		if(invMap != null && invMap.size()>0)
			notificationSummaryVOCollection  = filterNotificationSubmittedBy(
					notificationSummaryVOCollection, invMap);
		if (recipientMap != null && recipientMap.size()>0)
			notificationSummaryVOCollection  = filterNotificationsbyRecipient(
					notificationSummaryVOCollection, recipientMap);
		if (condMap != null && condMap.size()>0)
			notificationSummaryVOCollection  = filterNotificationsbyCondition(
					notificationSummaryVOCollection, condMap);
		if (statusMap != null && statusMap.size()>0)
			notificationSummaryVOCollection  = filterNotificationsbyCaseStatus(
					notificationSummaryVOCollection, statusMap);
		if(dateMap != null && dateMap.size()>0)
			notificationSummaryVOCollection  = filterNotificationsbySubmitdate(notificationSummaryVOCollection,dateMap);
		if(notifMap != null && notifMap.size()>0)
			notificationSummaryVOCollection  = filterNotiType(notificationSummaryVOCollection,notifMap);
		if(filterPatient!= null){
			notificationSummaryVOCollection = filterByText(notificationSummaryVOCollection, filterPatient, NEDSSConstants.INV_PATIENT);
		}
		if(filterComment!= null){
			notificationSummaryVOCollection = filterByText(notificationSummaryVOCollection, filterComment, NEDSSConstants.INV_COMMENT);
		}
		
		return notificationSummaryVOCollection;

	}

	public Collection<Object>  filterByText(
			Collection<Object>  notificationSummaryVOCollection, String filterByText,String column) {
		Collection<Object>  newInvColl = new ArrayList<Object> ();
		try{
		if (notificationSummaryVOCollection != null) {
			Iterator<Object> iter = notificationSummaryVOCollection.iterator();
			while (iter.hasNext()) {
				NotificationSummaryVO notifiSummaryVO = (NotificationSummaryVO) iter.next();
				if(column.equals(NEDSSConstants.INV_PATIENT) && notifiSummaryVO.getPatientFullNameLnk() != null 
						&& notifiSummaryVO.getPatientFullNameLnk().toUpperCase().contains(filterByText.toUpperCase())){
					newInvColl.add(notifiSummaryVO);
				}
				if(column.equals(NEDSSConstants.INV_COMMENT) && notifiSummaryVO.getTxt() != null 
						&& notifiSummaryVO.getTxt().toUpperCase().contains(filterByText.toUpperCase())){
					newInvColl.add(notifiSummaryVO);
				}
				
			}
		}
		}catch(Exception ex){			 
			 throw new NEDSSSystemException(ex.getMessage());
		}
		return newInvColl;
	}
	
	public Collection<Object>  filterNotificationSubmittedBy(
			Collection<Object>  notificationSummaryVOCollection, Map<Object,Object> investgatorMap) {
		Collection<Object>  newInvColl = new ArrayList<Object> ();
		if (notificationSummaryVOCollection  != null) {
			Iterator iter = notificationSummaryVOCollection.iterator();
			while (iter.hasNext()) {
				NotificationSummaryVO notifiSummaryVO = (NotificationSummaryVO) iter
						.next();
				if (notifiSummaryVO.getAddUserId().toString() != null
						&& investgatorMap != null
						&& investgatorMap.containsKey(notifiSummaryVO
								.getAddUserId().toString())) {
					newInvColl.add(notifiSummaryVO);
				}
				if(notifiSummaryVO.getAddUserId() == null || notifiSummaryVO.getAddUserId().equals("")){
					if(investgatorMap != null && investgatorMap.containsKey(NEDSSConstants.BLANK_KEY)){
						newInvColl.add(notifiSummaryVO);
					}
				}

			}

		}
		return newInvColl;

	}
	public Collection<Object>  filterNotiType(Collection<Object>  notificationSummaryVOCollection, Map<Object,Object> notifRecStatMap) {
		Collection<Object>  newInvColl = new ArrayList<Object> ();
		if (notificationSummaryVOCollection  != null) {
			Iterator iter = notificationSummaryVOCollection.iterator();
			while (iter.hasNext()) {
				NotificationSummaryVO notifiSummaryVO= (NotificationSummaryVO) iter.next();
				String notifCd = notifiSummaryVO.getNotificationCd();
				if (notifCd != null && notifRecStatMap != null && notifRecStatMap.containsKey(notifCd)) {
					newInvColl.add(notifiSummaryVO);
				}
				if(notifCd == null || (notifCd != null && notifCd.trim().equals(""))){
					if(notifRecStatMap != null && notifRecStatMap.containsKey(NEDSSConstants.BLANK_KEY)){
						newInvColl.add(notifiSummaryVO);
					}
				}
			}
		}
		return newInvColl;
	}
	public Collection<Object>  filterNotificationsbyCondition(Collection<Object>  notificationSummaryVOCollection,
			Map conditionMap) {
		Collection<Object>  newNotiColl = new ArrayList<Object> ();
		if (notificationSummaryVOCollection  != null) {
			Iterator iter = notificationSummaryVOCollection.iterator();
			while (iter.hasNext()) {
				NotificationSummaryVO notifiSummaryVO = (NotificationSummaryVO) iter
						.next();
				if (notifiSummaryVO.getCd() != null && conditionMap != null
						&& conditionMap.containsKey(notifiSummaryVO.getCd())) {
					newNotiColl.add(notifiSummaryVO);
				}

			}

		}
		return newNotiColl;

	}
	public Collection<Object>  filterNotificationsbyCaseStatus(
			Collection<Object>  notificationSummaryVOCollection, Map<Object,Object> statusMap) {
		Collection<Object>  newNotiColl = new ArrayList<Object> ();
		if (notificationSummaryVOCollection  != null) {
			Iterator iter = notificationSummaryVOCollection.iterator();
			while (iter.hasNext()) {
				NotificationSummaryVO notifiSummaryVO = (NotificationSummaryVO) iter
						.next();
				if (notifiSummaryVO.getCaseClassCd() != null && statusMap != null
						&& statusMap.containsKey(notifiSummaryVO.getCaseClassCd())) {
					newNotiColl.add(notifiSummaryVO);
				}
				if(notifiSummaryVO.getCaseClassCd() == null || notifiSummaryVO.getCaseClassCd().trim().equals("")){
					if(statusMap != null && statusMap.containsKey(NEDSSConstants.BLANK_KEY)){
						newNotiColl.add(notifiSummaryVO);
					}
				}

			}

		}
		return newNotiColl;

	}
	public Collection<Object>  filterNotificationsbyRecipient(
			Collection<Object>  notificationSummaryVOCollection, Map<Object,Object> statusMap) {
		Collection<Object>  newNotiColl = new ArrayList<Object> ();
		if (notificationSummaryVOCollection  != null) {
			Iterator iter = notificationSummaryVOCollection.iterator();
			while (iter.hasNext()) {
				NotificationSummaryVO notifiSummaryVO = (NotificationSummaryVO) iter
						.next();
				if (notifiSummaryVO.getRecipient() != null && statusMap != null
						&& statusMap.containsKey(notifiSummaryVO.getRecipient())) {
					newNotiColl.add(notifiSummaryVO);
				}
				if(notifiSummaryVO.getRecipient() == null || notifiSummaryVO.getRecipient().trim().equals("")){
					if(statusMap != null && statusMap.containsKey(NEDSSConstants.BLANK_KEY)){
						newNotiColl.add(notifiSummaryVO);
					}
				}

			}

		}
		return newNotiColl;

	}
	public Collection<Object>  filterNotificationsbySubmitdate(Collection<Object>  notificationSummaryVOCollection, Map<Object,Object> dateMap) {
		Map newInvMap = new HashMap<Object,Object>();
		String strDateKey = null;
		if (notificationSummaryVOCollection  != null) {
			Iterator iter = notificationSummaryVOCollection.iterator();
			while (iter.hasNext()) {
				NotificationSummaryVO notifiSummaryVO = (NotificationSummaryVO) iter
						.next();
				if (notifiSummaryVO.getAddTime() != null && dateMap != null
						&& (dateMap.size()>0 )) {
					Collection<Object>  dateSet = dateMap.keySet();
					if(dateSet != null){
						Iterator iSet = dateSet.iterator();
					while (iSet.hasNext()){
						 strDateKey = (String)iSet.next();
						if(!(strDateKey.equals(NEDSSConstants.DATE_BLANK_KEY))){
                    	   if(queueUtil.isDateinRange(notifiSummaryVO.getAddTime(),strDateKey)){
                    		   newInvMap.put(notifiSummaryVO.getLocalId(), notifiSummaryVO);
                    	   }

						}
                       }
					  }
					}

				if(notifiSummaryVO.getAddTime() == null || notifiSummaryVO.getAddTime().equals("")){
					if(dateMap != null && dateMap.containsKey(NEDSSConstants.DATE_BLANK_KEY)){
						 newInvMap.put(notifiSummaryVO.getLocalId(), notifiSummaryVO);
					}
				}

			}
		}


		return convertInvMaptoColl(newInvMap);

	}
	private Collection<Object>  convertInvMaptoColl(Map invMap){
		   Collection<Object>  invColl = new ArrayList<Object> ();
		   if(invMap !=null && invMap.size()>0){
			   Collection<Object>  invKeyColl = invMap.keySet();
			  Iterator<Object>  iter = invKeyColl.iterator();
			   while(iter.hasNext()){
				   String invKey = (String)iter.next();
				   invColl.add(invMap.get(invKey));
			   }
		   }
		   return invColl;
	   }

	public ArrayList<Object> getSubmittedByDropDowns(Collection<Object>  notificationSummaryVOCollection) {
		Map invMap = new HashMap<Object,Object>();
		if (notificationSummaryVOCollection  != null) {
			Iterator iter = notificationSummaryVOCollection.iterator();
			while (iter.hasNext()) {
				NotificationSummaryVO notifiSummaryVO = (NotificationSummaryVO) iter
						.next();
				String notiSubName = notifiSummaryVO.getAddUserName()== null? NEDSSConstants.NO_FIRST_NAME_INVESTIGATOR : notifiSummaryVO.getAddUserName();

				if (notifiSummaryVO.getAddUserId()!= null) {
					invMap.put(notifiSummaryVO.getAddUserId().toString(), notiSubName);
				}
				if(notifiSummaryVO.getAddUserId() == null || notifiSummaryVO.getAddUserId().equals("")){
					invMap.put(NEDSSConstants.BLANK_KEY, NEDSSConstants.BLANK_INVESTIGATOR_VALUE);
				}
			}

		}

		return queueUtil.getUniqueValueFromMap(invMap);
	}
	public ArrayList<Object> getConditionDropDowns(Collection<Object>  notificationSummaryVOCollection) {
		Map invMap = new HashMap<Object,Object>();
		if (notificationSummaryVOCollection  != null) {
			Iterator iter = notificationSummaryVOCollection.iterator();
			while (iter.hasNext()) {
				NotificationSummaryVO notifiSummaryVO = (NotificationSummaryVO) iter
						.next();
				if (notifiSummaryVO.getCd() != null) {
					invMap.put(notifiSummaryVO.getCd(), notifiSummaryVO.getCdTxt());

				}
			}
		}
		return queueUtil.getUniqueValueFromMap(invMap);
	}
	public ArrayList<Object> getCaseStatusDropDowns(Collection<Object>  notificationSummaryVOCollection) {
		Map invMap = new HashMap<Object,Object>();
		if (notificationSummaryVOCollection  != null) {
			Iterator iter = notificationSummaryVOCollection.iterator();
			while (iter.hasNext()) {
				NotificationSummaryVO notifiSummaryVO = (NotificationSummaryVO) iter
						.next();
				if (notifiSummaryVO.getCaseClassCd() != null) {
					invMap.put(notifiSummaryVO.getCaseClassCd(), notifiSummaryVO.getCaseClassCdTxt());

				}
				if(notifiSummaryVO.getCaseClassCd() == null || notifiSummaryVO.getCaseClassCd().trim().equals("")){
					invMap.put(NEDSSConstants.BLANK_KEY, NEDSSConstants.BLANK_VALUE);
				}
			}
		}
		return queueUtil.getUniqueValueFromMap(invMap);
	}
	public ArrayList<Object> getTypeDropDowns(Collection<Object>  notificationSummaryVOCollection) {
		Map invMap = new HashMap<Object,Object>();
		if (notificationSummaryVOCollection  != null) {
			Iterator iter = notificationSummaryVOCollection.iterator();
			while (iter.hasNext()) {
				NotificationSummaryVO notifiSummaryVO = (NotificationSummaryVO) iter
						.next();
				if (notifiSummaryVO.getNotificationCd() != null) {
					invMap.put(notifiSummaryVO.getNotificationCd(), notifiSummaryVO.getNotificationSrtDescCd());
				}
				if(notifiSummaryVO.getNotificationCd() == null || (notifiSummaryVO.getNotificationCd() !=null && notifiSummaryVO.getNotificationCd().trim().equals(""))){
					invMap.put(NEDSSConstants.BLANK_KEY, NEDSSConstants.BLANK_VALUE);
				}
			}
		}
		return queueUtil.getUniqueValueFromMap(invMap);
	}
	public ArrayList<Object> getRecipientDropDowns(Collection<Object>  notificationSummaryVOCollection) {
		Map invMap = new HashMap<Object,Object>();
		if (notificationSummaryVOCollection  != null) {
			Iterator iter = notificationSummaryVOCollection.iterator();
			while (iter.hasNext()) {
				NotificationSummaryVO notifiSummaryVO = (NotificationSummaryVO) iter
						.next();
				if (notifiSummaryVO.getRecipient() != null) {
					invMap.put(notifiSummaryVO.getRecipient(), notifiSummaryVO.getRecipient());
				}
				if(notifiSummaryVO.getRecipient() == null || (notifiSummaryVO.getRecipient() !=null && notifiSummaryVO.getRecipient().trim().equals(""))){
					if(notifiSummaryVO.getNndInd()!=null && notifiSummaryVO.getNndInd().equals(NEDSSConstants.YES))
						invMap.put(NEDSSConstants.ADMINFLAGCDC, NEDSSConstants.ADMINFLAGCDC);
	            	else
	            		invMap.put(NEDSSConstants.LOCAl_DESC, NEDSSConstants.LOCAl_DESC);
					
				}
			}
		}
		return queueUtil.getUniqueValueFromMap(invMap);
	}

	}




