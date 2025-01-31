package gov.cdc.nedss.webapp.nbs.action.notification.util;

import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.proxy.ejb.notificationproxyejb.vo.NotificationSummaryVO;
import gov.cdc.nedss.proxy.ejb.notificationproxyejb.vo.RejectedNotificationSummaryVO;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.webapp.nbs.action.util.QueueUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class RejectNotificationUtil {

	QueueUtil queueUtil = new QueueUtil();

	public String getSortCriteria(String sortOrder, String methodName){
		String sortOrdrStr = null;
		if(methodName != null) {
			if(methodName.equals("getAddTime"))
				sortOrdrStr = "Submit Date";
			else if(methodName.equals("getAddUserName"))
				sortOrdrStr = "Submitted By";
			else if(methodName.equals("getPatientFullName"))
				sortOrdrStr = "Patient";
			else if(methodName.equals("getCondition"))
				sortOrdrStr = "Condition";
			else if(methodName.equals("getCaseStatus"))
				sortOrdrStr = "Status";
			else if(methodName.equals("getNotificationCd"))
				sortOrdrStr = "Type";
			else if(methodName.equals("getRecipient"))
				sortOrdrStr = "Recipient";
			else if(methodName.equals("getCdTxt"))
				sortOrdrStr = "Comments";
			else if(methodName.equals("getRejectedByUserName"))
				sortOrdrStr = "Rejected By";
		} else {
			sortOrdrStr = "Submit Date";
		}

		if(sortOrder == null || (sortOrder != null && sortOrder.equals("1")))
			sortOrdrStr = sortOrdrStr+" in ascending order ";
		else if(sortOrder != null && sortOrder.equals("2"))
			sortOrdrStr = sortOrdrStr+" in descending order ";

		return sortOrdrStr;

	}
	public Collection<Object>  getFilteredRejectedQNotifs(Collection<Object>  rejectNotifSummaryVOCollection,
			Map searchCriteriaMap) {

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
		Map rejectedByMap = new HashMap<Object,Object>();

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
		if (rejectedBy != null && rejectedBy.length >0)
			rejectedByMap = queueUtil.getMapFromStringArray(rejectedBy);

		if(invMap != null && invMap.size()>0)
			rejectNotifSummaryVOCollection  = filterNotificationSubmittedBy(
					rejectNotifSummaryVOCollection, invMap);
		if (recipientMap != null && recipientMap.size()>0)
			rejectNotifSummaryVOCollection  = filterNotificationsbyRecipient(
					rejectNotifSummaryVOCollection, recipientMap);
		if (condMap != null && condMap.size()>0)
			rejectNotifSummaryVOCollection  = filterNotificationsbyCondition(
					rejectNotifSummaryVOCollection, condMap);
		if (statusMap != null && statusMap.size()>0)
			rejectNotifSummaryVOCollection  = filterNotificationsbyCaseStatus(
					rejectNotifSummaryVOCollection, statusMap);
		if(notifMap != null && notifMap.size()>0)
			rejectNotifSummaryVOCollection  = filterNotiType(rejectNotifSummaryVOCollection,notifMap);
		if(rejectedByMap != null && rejectedByMap.size()>0)
			rejectNotifSummaryVOCollection  = filterNotificationRejectedBy(rejectNotifSummaryVOCollection,rejectedByMap);
		if(dateMap != null && dateMap.size()>0)
			rejectNotifSummaryVOCollection  = filterNotificationsbySubmitdate(rejectNotifSummaryVOCollection,dateMap);
		if(filterPatient!= null){
			rejectNotifSummaryVOCollection = filterByText(rejectNotifSummaryVOCollection, filterPatient, NEDSSConstants.INV_PATIENT);
		}
		if(filterComment!= null){
			rejectNotifSummaryVOCollection = filterByText(rejectNotifSummaryVOCollection, filterComment, NEDSSConstants.INV_COMMENT);
		}
		return rejectNotifSummaryVOCollection;

	}

	public Collection<Object>  filterByText(
			Collection<Object>  rejectNotifSummaryVOCollection, String filterByText,String column) {
		Collection<Object>  newInvColl = new ArrayList<Object> ();
		try{
		if (rejectNotifSummaryVOCollection != null) {
			Iterator<Object> iter = rejectNotifSummaryVOCollection.iterator();
			while (iter.hasNext()) {
				RejectedNotificationSummaryVO rejectedNotifSummaryVO = (RejectedNotificationSummaryVO) iter.next();
				if(column.equals(NEDSSConstants.INV_PATIENT) && rejectedNotifSummaryVO.getPatientFullNameLnk() != null 
						&& rejectedNotifSummaryVO.getPatientFullNameLnk().toUpperCase().contains(filterByText.toUpperCase())){
					newInvColl.add(rejectedNotifSummaryVO);
				}
				if(column.equals(NEDSSConstants.INV_COMMENT) && rejectedNotifSummaryVO.getCdTxt() != null 
						&& rejectedNotifSummaryVO.getCdTxt().toUpperCase().contains(filterByText.toUpperCase())){
					newInvColl.add(rejectedNotifSummaryVO);
				}
				
			}
		}
		}catch(Exception ex){			 
			 throw new NEDSSSystemException(ex.getMessage());
		}
		return newInvColl;
	}
	public Collection<Object>  filterNotificationSubmittedBy(
			Collection<Object>  rejectNotifSummaryVOCollection, Map<Object,Object> investgatorMap) {
		Collection<Object>  newInvColl = new ArrayList<Object> ();
		if (rejectNotifSummaryVOCollection  != null) {
			Iterator iter = rejectNotifSummaryVOCollection.iterator();
			while (iter.hasNext()) {
				RejectedNotificationSummaryVO rejectedNotifSummaryVO = (RejectedNotificationSummaryVO) iter.next();
				if (rejectedNotifSummaryVO.getAddUserId().toString() != null
						&& investgatorMap != null
						&& investgatorMap.containsKey(rejectedNotifSummaryVO
								.getAddUserId().toString())) {
					newInvColl.add(rejectedNotifSummaryVO);
				}
				if(rejectedNotifSummaryVO.getAddUserId() == null || rejectedNotifSummaryVO.getAddUserId().equals("")){
					if(investgatorMap != null && investgatorMap.containsKey(NEDSSConstants.BLANK_KEY)){
						newInvColl.add(rejectedNotifSummaryVO);
					}
				}

			}

		}
		return newInvColl;

	}
	public Collection<Object>  filterNotificationRejectedBy(
			Collection<Object>  rejectNotifSummaryVOCollection, Map<Object,Object> rejectedByMap) {
		Collection<Object>  newInvColl = new ArrayList<Object> ();
		if (rejectNotifSummaryVOCollection  != null) {
			Iterator iter = rejectNotifSummaryVOCollection.iterator();
			while (iter.hasNext()) {
				RejectedNotificationSummaryVO rejectedNotifSummaryVO = (RejectedNotificationSummaryVO) iter.next();
				if (rejectedNotifSummaryVO.getRejectedByUserId().toString() != null
						&& rejectedByMap != null
						&& rejectedByMap.containsKey(rejectedNotifSummaryVO
								.getRejectedByUserId().toString())) {
					newInvColl.add(rejectedNotifSummaryVO);
				}
				if(rejectedNotifSummaryVO.getRejectedByUserId() == null || rejectedNotifSummaryVO.getRejectedByUserId().equals("")){
					if(rejectedByMap != null && rejectedByMap.containsKey(NEDSSConstants.BLANK_KEY)){
						newInvColl.add(rejectedNotifSummaryVO);
					}
				}

			}

		}
		return newInvColl;

	}
	public Collection<Object>  filterNotiType(Collection<Object>  rejectNotifSummaryVOCollection, Map<Object,Object> notifRecStatMap) {
		Collection<Object>  newInvColl = new ArrayList<Object> ();
		if (rejectNotifSummaryVOCollection  != null) {
			Iterator iter = rejectNotifSummaryVOCollection.iterator();
			while (iter.hasNext()) {
				RejectedNotificationSummaryVO rejectedNotifSummaryVO = (RejectedNotificationSummaryVO) iter.next();
				String notifCd = rejectedNotifSummaryVO.getNotificationCd();
				if (notifCd != null && notifRecStatMap != null && notifRecStatMap.containsKey(notifCd)) {
					newInvColl.add(rejectedNotifSummaryVO);
				}
				if(notifCd == null || (notifCd != null && notifCd.trim().equals(""))){
					if(notifRecStatMap != null && notifRecStatMap.containsKey(NEDSSConstants.BLANK_KEY)){
						newInvColl.add(rejectedNotifSummaryVO);
					}
				}
			}
		}
		return newInvColl;
	}
	public Collection<Object>  filterNotificationsbyCondition(Collection<Object>  rejectNotifSummaryVOCollection,
			Map conditionMap) {
		Collection<Object>  newNotiColl = new ArrayList<Object> ();
		if (rejectNotifSummaryVOCollection  != null) {
			Iterator iter = rejectNotifSummaryVOCollection.iterator();
			while (iter.hasNext()) {
				RejectedNotificationSummaryVO rejectedNotifSummaryVO = (RejectedNotificationSummaryVO) iter.next();
				if (rejectedNotifSummaryVO.getCondition() != null && conditionMap != null
						&& conditionMap.containsKey(rejectedNotifSummaryVO.getCondition())) {
					newNotiColl.add(rejectedNotifSummaryVO);
				}

			}

		}
		return newNotiColl;

	}
	public Collection<Object>  filterNotificationsbyCaseStatus(
			Collection<Object>  rejectNotifSummaryVOCollection, Map<Object,Object> statusMap) {
		Collection<Object>  newNotiColl = new ArrayList<Object> ();
		if (rejectNotifSummaryVOCollection  != null) {
			Iterator iter = rejectNotifSummaryVOCollection.iterator();
			while (iter.hasNext()) {
				RejectedNotificationSummaryVO rejectedNotifSummaryVO = (RejectedNotificationSummaryVO) iter.next();
				if (rejectedNotifSummaryVO.getCaseStatusCd() != null && statusMap != null
						&& statusMap.containsKey(rejectedNotifSummaryVO.getCaseStatusCd())) {
					newNotiColl.add(rejectedNotifSummaryVO);
				}
				if(rejectedNotifSummaryVO.getCaseStatusCd() == null || rejectedNotifSummaryVO.getCaseStatusCd().trim().equals("")){
					if(statusMap != null && statusMap.containsKey(NEDSSConstants.BLANK_KEY)){
						newNotiColl.add(rejectedNotifSummaryVO);
					}
				}

			}

		}
		return newNotiColl;

	}
	public Collection<Object>  filterNotificationsbyRecipient(
			Collection<Object>  rejectNotifSummaryVOCollection, Map<Object,Object> recipientMap) {
		Collection<Object>  newNotiColl = new ArrayList<Object> ();
		if (rejectNotifSummaryVOCollection  != null) {
			Iterator iter = rejectNotifSummaryVOCollection.iterator();
			while (iter.hasNext()) {
				RejectedNotificationSummaryVO rejectedNotifSummaryVO = (RejectedNotificationSummaryVO) iter.next();
				if (rejectedNotifSummaryVO.getRecipient() != null && recipientMap != null
						&& recipientMap.containsKey(rejectedNotifSummaryVO.getRecipient())) {
					newNotiColl.add(rejectedNotifSummaryVO);
				}
				if(rejectedNotifSummaryVO.getRecipient() == null || rejectedNotifSummaryVO.getRecipient().trim().equals("")){
					if(recipientMap != null && recipientMap.containsKey(NEDSSConstants.BLANK_KEY)){
						newNotiColl.add(rejectedNotifSummaryVO);
					}
				}

			}

		}
		return newNotiColl;

	}
	public Collection<Object>  filterNotificationsbySubmitdate(Collection<Object>  rejectNotifSummaryVOCollection, Map<Object,Object> dateMap) {
		Map newInvMap = new HashMap<Object,Object>();
		String strDateKey = null;
		if (rejectNotifSummaryVOCollection  != null) {
			Iterator iter = rejectNotifSummaryVOCollection.iterator();
			while (iter.hasNext()) {
				RejectedNotificationSummaryVO rejectedNotifSummaryVO = (RejectedNotificationSummaryVO) iter.next();
				if (rejectedNotifSummaryVO.getAddTime() != null && dateMap != null
						&& (dateMap.size()>0 )) {
					Collection<Object>  dateSet = dateMap.keySet();
					if(dateSet != null){
						Iterator iSet = dateSet.iterator();
					while (iSet.hasNext()){
						 strDateKey = (String)iSet.next();
						if(!(strDateKey.equals(NEDSSConstants.DATE_BLANK_KEY))){
                    	   if(queueUtil.isDateinRange(rejectedNotifSummaryVO.getAddTime(),strDateKey)){
                    		   newInvMap.put(rejectedNotifSummaryVO.getPublicHealthCaseUid().toString(), rejectedNotifSummaryVO);
                    	   }

						}
                       }
					  }
					}

				if(rejectedNotifSummaryVO.getAddTime() == null || rejectedNotifSummaryVO.getAddTime().equals("")){
					if(dateMap != null && dateMap.containsKey(NEDSSConstants.DATE_BLANK_KEY)){
						 newInvMap.put(rejectedNotifSummaryVO.getPublicHealthCaseUid().toString(), rejectedNotifSummaryVO);
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


	public ArrayList<Object> getSubmittedByForRejectedQueueDropDowns(Collection<Object>  rejectNotifSummaryVOCollection) {
		Map invMap = new HashMap<Object,Object>();
		if (rejectNotifSummaryVOCollection  != null) {
			Iterator iter = rejectNotifSummaryVOCollection.iterator();
			while (iter.hasNext()) {
				RejectedNotificationSummaryVO rejectedNotifSummaryVO = (RejectedNotificationSummaryVO) iter.next();
				String notiSubName = rejectedNotifSummaryVO.getAddUserName()== null? NEDSSConstants.NO_FIRST_NAME_INVESTIGATOR : rejectedNotifSummaryVO.getAddUserName();

				if (rejectedNotifSummaryVO.getAddUserId()!= null) {
					invMap.put(rejectedNotifSummaryVO.getAddUserId().toString(), notiSubName);
				}
				if(rejectedNotifSummaryVO.getAddUserId() == null || rejectedNotifSummaryVO.getAddUserId().equals("")){
					invMap.put(NEDSSConstants.BLANK_KEY, NEDSSConstants.BLANK_INVESTIGATOR_VALUE);
				}
			}

		}

		return queueUtil.getUniqueValueFromMap(invMap);
	}
	public ArrayList<Object> getConditionForRejectedQueueDropDowns(Collection<Object>  rejectNotifSummaryVOCollection) {
		Map invMap = new HashMap<Object,Object>();
		if (rejectNotifSummaryVOCollection  != null) {
			Iterator iter = rejectNotifSummaryVOCollection.iterator();
			while (iter.hasNext()) {
				RejectedNotificationSummaryVO rejectedNotifSummaryVO = (RejectedNotificationSummaryVO) iter.next();
				if (rejectedNotifSummaryVO.getCondition() != null) {
					invMap.put(rejectedNotifSummaryVO.getCondition(), rejectedNotifSummaryVO.getCondition());

				}
			}
		}
		return queueUtil.getUniqueValueFromMap(invMap);
	}
	public ArrayList<Object> getCaseStatusForRejectedQueueDropDowns(Collection<Object>  rejectNotifSummaryVOCollection) {
		Map invMap = new HashMap<Object,Object>();
		if (rejectNotifSummaryVOCollection  != null) {
			Iterator iter = rejectNotifSummaryVOCollection.iterator();
			while (iter.hasNext()) {
				RejectedNotificationSummaryVO rejectedNotifSummaryVO = (RejectedNotificationSummaryVO) iter.next();
				if (rejectedNotifSummaryVO.getCaseStatusCd() != null) {
					invMap.put(rejectedNotifSummaryVO.getCaseStatusCd(), rejectedNotifSummaryVO.getCaseStatus());

				}
				if(rejectedNotifSummaryVO.getCaseStatusCd() == null || rejectedNotifSummaryVO.getCaseStatusCd().trim().equals("")){
					invMap.put(NEDSSConstants.BLANK_KEY, NEDSSConstants.BLANK_VALUE);
				}
			}
		}
		return queueUtil.getUniqueValueFromMap(invMap);
	}
	public ArrayList<Object> getTypeForRejectedQueueDropDowns(Collection<Object>  rejectNotifSummaryVOCollection) {
		Map invMap = new HashMap<Object,Object>();
		if (rejectNotifSummaryVOCollection  != null) {
			Iterator iter = rejectNotifSummaryVOCollection.iterator();
			while (iter.hasNext()) {
				RejectedNotificationSummaryVO rejectedNotifSummaryVO = (RejectedNotificationSummaryVO) iter.next();
				if (rejectedNotifSummaryVO.getNotificationCd() != null) {
					invMap.put(rejectedNotifSummaryVO.getNotificationCd(), rejectedNotifSummaryVO.getNotificationCd());
				}
				if(rejectedNotifSummaryVO.getNotificationCd() == null || (rejectedNotifSummaryVO.getNotificationCd() !=null && rejectedNotifSummaryVO.getNotificationCd().trim().equals(""))){
					invMap.put(NEDSSConstants.BLANK_KEY, NEDSSConstants.BLANK_VALUE);
				}
			}
		}
		return queueUtil.getUniqueValueFromMap(invMap);
	}
	public ArrayList<Object> getRecipientForRejectedQueueDropDowns(Collection<Object>  rejectNotifSummaryVOCollection) {
		Map invMap = new HashMap<Object,Object>();
		if (rejectNotifSummaryVOCollection  != null) {
			Iterator iter = rejectNotifSummaryVOCollection.iterator();
			while (iter.hasNext()) {
				RejectedNotificationSummaryVO rejectedNotifSummaryVO = (RejectedNotificationSummaryVO) iter.next();
				if (rejectedNotifSummaryVO.getRecipient() != null) {
					invMap.put(rejectedNotifSummaryVO.getRecipient(), rejectedNotifSummaryVO.getRecipient());
				}
				if(rejectedNotifSummaryVO.getRecipient() == null || (rejectedNotifSummaryVO.getRecipient() !=null && rejectedNotifSummaryVO.getRecipient().trim().equals(""))){
					if(rejectedNotifSummaryVO.getNndInd()!=null && rejectedNotifSummaryVO.getNndInd().equals(NEDSSConstants.YES))
						invMap.put(NEDSSConstants.ADMINFLAGCDC, NEDSSConstants.ADMINFLAGCDC);
	            	else
	            		invMap.put(NEDSSConstants.LOCAl_DESC, NEDSSConstants.LOCAl_DESC);
				}
			}
		}
		return queueUtil.getUniqueValueFromMap(invMap);
	}

	public ArrayList<Object> getRejectedByForRejectedQueueDropDowns(Collection<Object>  rejectNotifSummaryVOCollection) {
		Map invMap = new HashMap<Object,Object>();
		if (rejectNotifSummaryVOCollection  != null) {
			Iterator iter = rejectNotifSummaryVOCollection.iterator();
			while (iter.hasNext()) {
				RejectedNotificationSummaryVO rejectedNotifSummaryVO = (RejectedNotificationSummaryVO) iter.next();
				String notiSubName = rejectedNotifSummaryVO.getRejectedByUserName()== null? NEDSSConstants.NO_FIRST_NAME_INVESTIGATOR : rejectedNotifSummaryVO.getRejectedByUserName();

				if (rejectedNotifSummaryVO.getRejectedByUserId()!= null) {
					invMap.put(rejectedNotifSummaryVO.getRejectedByUserId().toString(), notiSubName);
				}
				if(rejectedNotifSummaryVO.getRejectedByUserId() == null || rejectedNotifSummaryVO.getRejectedByUserId().equals("")){
					invMap.put(NEDSSConstants.BLANK_KEY, NEDSSConstants.BLANK_INVESTIGATOR_VALUE);
				}
			}

		}
		return queueUtil.getUniqueValueFromMap(invMap);
	}
}