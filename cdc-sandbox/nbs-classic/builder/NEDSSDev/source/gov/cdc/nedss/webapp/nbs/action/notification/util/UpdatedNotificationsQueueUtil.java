package gov.cdc.nedss.webapp.nbs.action.notification.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.webapp.nbs.action.util.QueueUtil;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.proxy.ejb.notificationproxyejb.vo.RejectedNotificationSummaryVO;
import gov.cdc.nedss.proxy.ejb.notificationproxyejb.vo.UpdatedNotificationSummaryVO;

public class UpdatedNotificationsQueueUtil extends QueueUtil 
{
	static final LogUtils logger = new LogUtils(UpdatedNotificationsQueueUtil.class.getName());
	
	/**
	 * Get a list of DropDownCodeDT objects that represent all the 
	 * Jurisdiction options for a select box. 
	 * @param notifications
	 * @return
	 */
	public ArrayList<Object> getJurisdictionOptions(Collection<Object>  notifications) {
		Map valMap = new HashMap<Object,Object>();
		Iterator iter = (Iterator) notifications.iterator(); 
		while (iter.hasNext() == true) {
			UpdatedNotificationSummaryVO vo = (UpdatedNotificationSummaryVO) iter.next();
			if (vo.getJurisdictionCdTxt()!= null) {
				valMap.put(vo.getJurisdictionCdTxt(), vo.getJurisdictionCdTxt());
			}
		}

		return getUniqueValueFromMap(valMap);
	}
	
	/**
	 * Get a list of DropDownCodeDT objects that represent all the 
	 * submittedBy options for a select box. 
	 * @param notifications
	 * @return
	 */
	public ArrayList<Object> getSubmittedByOptions(Collection<Object>  notifications) {
		Map valMap = new HashMap<Object,Object>();
		Iterator iter = (Iterator) notifications.iterator(); 
		while (iter.hasNext() == true) {
			UpdatedNotificationSummaryVO vo = (UpdatedNotificationSummaryVO) iter.next();
			if (vo.getAddUserName() != null) {
				valMap.put(vo.getAddUserName(), vo.getAddUserName());
			}
		}

		return getUniqueValueFromMap(valMap);
	}

	/**
	 * Get a list of DropDownCodeDT objects that represent all the 
	 * case status options for a select box. 
	 * @param notifications
	 * @return
	 */
	public ArrayList<Object> getCaseStatusOptions(Collection<Object>  notifications) {
		Map valMap = new HashMap<Object,Object>();
		Iterator iter = (Iterator) notifications.iterator(); 
		while (iter.hasNext() == true) {
			UpdatedNotificationSummaryVO vo = (UpdatedNotificationSummaryVO) iter.next();
			if (vo.getCaseClassCdTxt() != null) {
				valMap.put(vo.getCaseClassCdTxt(), vo.getCaseClassCdTxt());
			}
			
			if (vo.getCaseClassCdTxt() == null || 
					(vo.getCaseClassCdTxt() != null && vo.getCaseClassCdTxt().trim().equals(""))){
				valMap.put(NEDSSConstants.BLANK_KEY, NEDSSConstants.BLANK_VALUE);
			}
		}

		return getUniqueValueFromMap(valMap);
	}

	/**
	 * Get a list of DropDownCodeDT objects that represent all the 
	 * condition options for a select box. 
	 * @param notifications
	 * @return
	 */
	public ArrayList<Object> getConditionOptions(Collection<Object>  notifications) {
		Map valMap = new HashMap<Object,Object>();
		Iterator iter = (Iterator) notifications.iterator(); 
		while (iter.hasNext() == true) {
			UpdatedNotificationSummaryVO vo = (UpdatedNotificationSummaryVO) iter.next();
			if (vo.getCdTxt() != null) {
				valMap.put(vo.getCdTxt(), vo.getCdTxt());
			}
		}

		return getUniqueValueFromMap(valMap);
	}

	/**
	 * TODO: Get a list of DropDownCodeDT objects that represent all the 
	 * patient name options for a select box. 
	 * @param notifications
	 * @return
	 */
	public ArrayList<Object> getPatientNameOptions(Collection<Object>  notifications) {
		Map valMap = new HashMap<Object,Object>();

		return getUniqueValueFromMap(valMap);
	}
	
	/**
	 * Get a list of DropDownCodeDT objects that represent all the 
	 * notification code options for a select box. 
	 * @param notifications
	 * @return
	 */
	public ArrayList<Object> getNotificationCodeOptions(Collection<Object>  notifications) {
		Map valMap = new HashMap<Object,Object>();
		Iterator iter = (Iterator) notifications.iterator(); 
		while (iter.hasNext() == true) {
			UpdatedNotificationSummaryVO vo = (UpdatedNotificationSummaryVO) iter.next();
			if (vo.getNotificationCd() != null) {
				valMap.put(vo.getNotificationCd(), vo.getNotificationCd());
			}
		}

		return getUniqueValueFromMap(valMap);
	}

	/**
	 * Get a list of DropDownCodeDT objects that represent all the 
	 * recipient options for a select box. 
	 * @param notifications
	 * @return
	 */
	public ArrayList<Object> getRecipientOptions(Collection<Object>  notifications) {
		Map valMap = new HashMap<Object,Object>();
		Iterator iter = (Iterator) notifications.iterator(); 
		while (iter.hasNext() == true) {
			UpdatedNotificationSummaryVO vo = (UpdatedNotificationSummaryVO) iter.next();
			if (vo.getRecipient() != null) {
				valMap.put(vo.getRecipient(), vo.getRecipient());
			}
		}

		return getUniqueValueFromMap(valMap);
	}
	
	/**
	 * Get a list of DropDownCodeDT objects that represent all the 
	 * update date options for a selecy box.
	 * @param notifications
	 * @return
	 */
	public ArrayList<Object> getUpdateDateOptions(Collection<Object>  notifications) {
		Map valMap = new HashMap<Object,Object>();
		Iterator iter = (Iterator) notifications.iterator(); 
		while (iter.hasNext() == true) {
			UpdatedNotificationSummaryVO vo = (UpdatedNotificationSummaryVO) iter.next();
			if (vo.getAddTime() != null) {
				valMap.put(vo.getAddTime(), vo.getAddTime());
			}
		}

		return getUniqueValueFromMap(valMap);
	}
	
	/**
	 * Apply the filters in the search criteria map to the existing set of 
	 * notifications and return the filtered results. 
	 * @param obsSummaryVOs
	 * @param searchCriteriaMap
	 * @return
	 */
	public Collection<Object>  getFilteredNotifications(Collection<Object>  notificationVOs,
			Map searchCriteriaMap) 
	{
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
		}
		Map conditionsFilterMap = new HashMap<Object,Object>(), 
				jurisdictionsFilterMap = new HashMap<Object,Object>(), 
				caseStatusFilterMap = new HashMap<Object,Object>(), 
				submittedByFilterMap = new HashMap<Object,Object>(),
				notificationCodeFilterMap = new HashMap<Object,Object>(),
				recipientFilterMap = new HashMap<Object,Object>(),
				updateDateFilterMap = new HashMap<Object,Object>();
		
		if (conditionFilter != null && conditionFilter.length > 0)
			conditionsFilterMap = getMapFromStringArray(conditionFilter);
		if (jurisdictionFilter != null && jurisdictionFilter.length > 0)
			jurisdictionsFilterMap = getMapFromStringArray(jurisdictionFilter);
		if (caseStatusFilter != null && caseStatusFilter.length >0)
			caseStatusFilterMap = getMapFromStringArray(caseStatusFilter);
		if (submittedByFilter != null && submittedByFilter.length >0)
			submittedByFilterMap = getMapFromStringArray(submittedByFilter);
		if (notificationCodeFilter != null && notificationCodeFilter.length >0)
			notificationCodeFilterMap = getMapFromStringArray(notificationCodeFilter);
		if (recipientFilter != null && recipientFilter.length >0)
			recipientFilterMap = getMapFromStringArray(recipientFilter);
		if (updateDateFilter != null && updateDateFilter.length >0)
			updateDateFilterMap = getMapFromStringArray(updateDateFilter);

		// apply filters 
		if (conditionsFilterMap != null && conditionsFilterMap.size() > 0)
			notificationVOs = filterNotificationsbyCondition(notificationVOs, conditionsFilterMap);
		
		if (jurisdictionsFilterMap != null && jurisdictionsFilterMap.size() > 0)
			notificationVOs = filterNotificationsbyJurisdiction(notificationVOs, jurisdictionsFilterMap);
		
		if (caseStatusFilterMap != null && caseStatusFilterMap.size() > 0)
			notificationVOs = filterNotificationsbyCaseStatus(notificationVOs, caseStatusFilterMap);
		
		if (submittedByFilterMap != null && submittedByFilterMap.size() > 0)
			notificationVOs = filterNotificationsbySubmittedBy(notificationVOs, submittedByFilterMap);
		
		if (notificationCodeFilterMap != null && notificationCodeFilterMap.size() > 0)
			notificationVOs = filterNotificationsbyNotificationCode(notificationVOs, notificationCodeFilterMap);

		if (recipientFilterMap != null && recipientFilterMap.size() > 0)
			notificationVOs = filterNotificationsbyRecipient(notificationVOs, recipientFilterMap);

		if (updateDateFilterMap != null && updateDateFilterMap.size() > 0)
			notificationVOs = filterNotificationsbyUpdateDate(notificationVOs, updateDateFilterMap);
		if(filterPatient!= null){
			notificationVOs = filterByText(notificationVOs, filterPatient, NEDSSConstants.INV_PATIENT);
		}
		
		return notificationVOs;
	}
	
	public Collection<Object>  filterByText(
			Collection<Object>  UpdatedNotificationSummaryVOCollection, String filterByText,String column) {
		Collection<Object>  newInvColl = new ArrayList<Object> ();
		try{
		if (UpdatedNotificationSummaryVOCollection != null) {
			Iterator<Object> iter = UpdatedNotificationSummaryVOCollection.iterator();
			while (iter.hasNext()) {
				UpdatedNotificationSummaryVO updatedNotifSummaryVO = (UpdatedNotificationSummaryVO) iter.next();
				if(column.equals(NEDSSConstants.INV_PATIENT) && updatedNotifSummaryVO.getPatientFullNameLnk() != null 
						&& updatedNotifSummaryVO.getPatientFullNameLnk().toUpperCase().contains(filterByText.toUpperCase())){
					newInvColl.add(updatedNotifSummaryVO);
				}				
				
			}
		}
		}catch(Exception ex){			 
			 throw new NEDSSSystemException(ex.getMessage());
		}
		return newInvColl;
	}
	
	/**
	 * Generate a sort criteria description string for a given sort method name
	 * and sort order.
	 * @param sortOrder
	 * @param methodName
	 * @return
	 */
	public String getSortCriteriaDescription(String sortOrder, String methodName){
		String sortOrdrStr = null;
		if(methodName != null) {
			if(methodName.equals("getAddTime"))
				sortOrdrStr = "Update Date";
			else if(methodName.equals("getAddUserName"))
				sortOrdrStr = "Updated By";
			else if(methodName.equals("getRecipient"))
				sortOrdrStr = "Recipient";
			else if(methodName.equals("getNotificationCd"))
				sortOrdrStr = "Type";
			else if(methodName.equals("getPatientFullName"))				
				sortOrdrStr = "Patient";
			else if(methodName.equals("getCdTxt"))				
				sortOrdrStr = "Condition";
			else if(methodName.equals("getCaseClassCdTxt"))				
				sortOrdrStr = "Case Status";
		} else {
			sortOrdrStr = "Update Date";
		}
		
		if(sortOrder == null || (sortOrder != null && sortOrder.equals("1")))
			sortOrdrStr = sortOrdrStr+" in ascending order ";
		else if(sortOrder != null && sortOrder.equals("2"))
			sortOrdrStr = sortOrdrStr+" in descending order ";

		return sortOrdrStr;
	}
	
	/**
	 * Helper method to filter notifications by condition
	 * @param notifications
	 * @param testMap
	 * @return
	 */
	private Collection<Object>  filterNotificationsbyCondition(Collection<Object>  notifications, Map<Object,Object> testMap)
	{
		Collection<Object>  filteredNotifications = new ArrayList<Object> ();
		Collection<Object>  includeCondition = testMap.keySet();
		Iterator iter = includeCondition.iterator();
		
		while (iter.hasNext()) {
			String condition = (String) iter.next();
			Iterator iter2 = notifications.iterator();
			
			while (iter2.hasNext()) {
				UpdatedNotificationSummaryVO notification = 
					(UpdatedNotificationSummaryVO) iter2.next();
				if (notification.getCdTxt() != null && 
						notification.getCdTxt().equals(condition)) {
					filteredNotifications.add(notification);
				}
			}
		}
		
		return filteredNotifications;
	}
	
	/**
	 * Helper method to filter notifications by jurisdiction
	 * @param notifications
	 * @param testMap
	 * @return
	 */
	private Collection<Object>  filterNotificationsbyJurisdiction(Collection<Object>  notifications, Map<Object,Object> testMap)
	{
		Collection<Object>  filteredNotifications = new ArrayList<Object> ();
		Collection<Object>  includeJurisdiction = testMap.keySet();
		Iterator iter = includeJurisdiction.iterator();
		
		while (iter.hasNext()) {
			String juri = (String) iter.next();
			Iterator iter2 = notifications.iterator();
			
			while (iter2.hasNext()) {
				UpdatedNotificationSummaryVO notification = 
					(UpdatedNotificationSummaryVO) iter2.next();
				if (notification.getJurisdictionCdTxt() != null && 
						notification.getJurisdictionCdTxt().equals(juri)) {
					filteredNotifications.add(notification);
				}
			}
		}
		
		return filteredNotifications;
	}
	
	/**
	 * Helper method to filter notifications by case status
	 * @param notifications
	 * @param testMap
	 * @return
	 */
	private Collection<Object>  filterNotificationsbyCaseStatus(Collection<Object>  notifications, Map<Object,Object> testMap)
	{
		Collection<Object>  filteredNotifications = new ArrayList<Object> ();
		Collection<Object>  includeCaseStatus = testMap.keySet();
		Iterator iter = includeCaseStatus.iterator();
		
		while (iter.hasNext()) {
			String cs = (String) iter.next();
			Iterator iter2 = notifications.iterator();
			
			while (iter2.hasNext()) {
				UpdatedNotificationSummaryVO notification = 
					(UpdatedNotificationSummaryVO) iter2.next();
				if (notification.getCaseClassCdTxt() != null && 
						notification.getCaseClassCdTxt().equals(cs)) {
					filteredNotifications.add(notification);
				}
				
				// add a notification with blank case status if 'blank' is one of the filter values. 
				if (notification.getCaseClassCdTxt() == null || 
						(notification.getCaseClassCdTxt() != null && notification.getCaseClassCdTxt().trim().equals(""))) {
					if(testMap != null && testMap.containsKey(NEDSSConstants.BLANK_KEY)) {
						filteredNotifications.add(notification);
					}
				}
			}
		}

		return filteredNotifications;
	}
	
	/**
	 * Helper method to filter notifications by 'Submitted By' 
	 * @param notifications
	 * @param testMap
	 * @return
	 */
	private Collection<Object>  filterNotificationsbySubmittedBy(Collection<Object>  notifications, Map<Object,Object> testMap)
	{
		Collection<Object>  filteredNotifications = new ArrayList<Object> ();
		Collection<Object>  includeSubmittedBy = testMap.keySet();
		Iterator iter = includeSubmittedBy.iterator();
		
		while (iter.hasNext()) {
			String sb = (String) iter.next();
			Iterator iter2 = notifications.iterator();
			
			while (iter2.hasNext()) {
				UpdatedNotificationSummaryVO notification = 
					(UpdatedNotificationSummaryVO) iter2.next();
				if (notification.getAddUserName() != null && 
						notification.getAddUserName().equals(sb)) {
					filteredNotifications.add(notification);
				}
			}
		}
		
		return filteredNotifications;
	}
	
	/**
	 * Helper method to filter notifications by Notification Code 
	 * @param notifications
	 * @param testMap
	 * @return
	 */
	private Collection<Object>  filterNotificationsbyNotificationCode(Collection<Object>  notifications, Map<Object,Object> testMap)
	{
		Collection<Object>  filteredNotifications = new ArrayList<Object> ();
		Collection<Object>  includeNotificationCode = testMap.keySet();
		Iterator iter = includeNotificationCode.iterator();
		
		while (iter.hasNext()) {
			String nc = (String) iter.next();
			Iterator iter2 = notifications.iterator();
			
			while (iter2.hasNext()) {
				UpdatedNotificationSummaryVO notification = 
					(UpdatedNotificationSummaryVO) iter2.next();
				if (notification.getNotificationCd() != null && 
						notification.getNotificationCd().equals(nc)) {
					filteredNotifications.add(notification);
				}
			}
		}
		
		return filteredNotifications;
	}
	
	/**
	 * Helper method to filter notifications by Recipient
	 * @param notifications
	 * @param testMap
	 * @return
	 */
	private Collection<Object>  filterNotificationsbyRecipient(Collection<Object>  notifications, Map<Object,Object> testMap)
	{
		Collection<Object>  filteredNotifications = new ArrayList<Object> ();
		Collection<Object>  includeRecipient = testMap.keySet();
		Iterator iter = includeRecipient.iterator();
		
		while (iter.hasNext()) {
			String re = (String) iter.next();
			Iterator iter2 = notifications.iterator();
			
			while (iter2.hasNext()) {
				UpdatedNotificationSummaryVO notification = 
					(UpdatedNotificationSummaryVO) iter2.next();
				if (notification.getRecipient() != null && 
						notification.getRecipient().equals(re)) {
					filteredNotifications.add(notification);
				}
			}
		}
		
		return filteredNotifications;
	}
	
	/**
	 * Helper method to filter notifications by Update Date
	 * @param notifications
	 * @param testMap
	 * @return
	 */
	public Collection<Object>  filterNotificationsbyUpdateDate(Collection<Object>  notifications, Map<Object,Object> dateMap)
	{
		Map filteredNotifications = new HashMap<Object,Object>();
		String strDateKey = null;
		if (notifications != null)
		{
			Iterator iter = notifications.iterator();
			while (iter.hasNext())
			{
				UpdatedNotificationSummaryVO notification = (UpdatedNotificationSummaryVO) iter.next();
				if (notification.getAddTime() != null && dateMap != null && (dateMap.size() > 0))
				{
					Collection<Object>  dateSet = dateMap.keySet();
					if(dateSet != null) {
						Iterator iSet = dateSet.iterator();
						while (iSet.hasNext())
						{
							strDateKey = (String)iSet.next();
							if(!(strDateKey.equals(NEDSSConstants.DATE_BLANK_KEY)))
							{
								if(isDateinRange(notification.getAddTime(), strDateKey)) {
									filteredNotifications.put(String.valueOf(notification.getNotificationUid())+ String.valueOf(notification.getVersionCtrlNbr()), notification);
								}	
							}
						}  
					}
				}
		
				if (notification.getAddTime() == null || notification.getAddTime().equals("")) {
					if (dateMap != null && dateMap.containsKey(NEDSSConstants.DATE_BLANK_KEY)){
						filteredNotifications.put(String.valueOf(notification.getNotificationUid())+ String.valueOf(notification.getVersionCtrlNbr()), notification);
					}
				}
			}
		}
		
		return convertMaptoColl(filteredNotifications);
	}
}