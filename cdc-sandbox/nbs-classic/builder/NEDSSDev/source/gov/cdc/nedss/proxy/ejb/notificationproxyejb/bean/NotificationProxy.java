/**
 * Title: NotificationProxy
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:CSC
 * @author nedss project team
 * @version 1.0
 */
package gov.cdc.nedss.proxy.ejb.notificationproxyejb.bean;

// Import Statements
import gov.cdc.nedss.act.publichealthcase.dt.PublicHealthCaseDT;
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.proxy.ejb.notificationproxyejb.vo.NotificationProxyVO;
import gov.cdc.nedss.proxy.ejb.notificationproxyejb.vo.NotificationSummaryVO;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;

import java.util.Collection;
import java.util.Map;


public interface NotificationProxy extends javax.ejb.EJBObject
{

    /**
     * @roseuid 3C43688D02FE
     * @J2EE_METHOD  --  getNotificationProxy
     */
    public NotificationProxyVO getNotificationProxy    (Long notificationUID, NBSSecurityObj nbsSecurityObj)
            throws java.rmi.RemoteException, javax.ejb.EJBException,NEDSSSystemException;

    public NotificationProxyVO getNotificationProxyForMerge    (Long notificationUID, NBSSecurityObj nbsSecurityObj)
            throws java.rmi.RemoteException, javax.ejb.EJBException,NEDSSSystemException;

    /**
     * @roseuid 3C43691701D0
     * @J2EE_METHOD  --  setNotificationProxy
     */
    public Long setNotificationProxy    (NotificationProxyVO notificationProxyVO, NBSSecurityObj nbsSecurityObj)
          throws java.rmi.RemoteException, javax.ejb.EJBException,NEDSSSystemException,
                NEDSSConcurrentDataException;

    /**
     * @roseuid 3C436D0B012A
     * @J2EE_METHOD  --  rejectNotification
     */
    public void rejectNotification    (NotificationSummaryVO notificationSummaryVO, NBSSecurityObj nbsSecurityObj)
          throws java.rmi.RemoteException, javax.ejb.EJBException,NEDSSSystemException,
                  NEDSSConcurrentDataException;

    /**
     * @roseuid 3C436DB00178
     * @J2EE_METHOD  --  approveNotification
     */
    public Boolean approveNotification (NotificationSummaryVO notificationSummaryVO, NBSSecurityObj nbsSecurityObj)
        throws java.rmi.RemoteException, javax.ejb.EJBException, javax.ejb.CreateException,
              NEDSSSystemException,NEDSSConcurrentDataException;


	/**
	 * This method returns a collection of all Updated Notifications that need 
	 * to be audited 
	 *
	 * @param nbsSecurityObj
	 * @throws javax.ejb.EJBException
	 */
	public Collection<Object>  getUpdatedNotificationsForAudit (NBSSecurityObj nbsSecurityObj)
		throws java.rmi.RemoteException, javax.ejb.EJBException, NEDSSSystemException;

	/**
	 * This method removes Updated Notifications from the Updated Notifications 
	 * Queue
	 *
	 * @param Collection<Object>  updated Notifications
	 * @param nbsSecurityObj
	 * @throws javax.ejb.EJBException
	 */
	public void removeUpdatedNotifications(Collection<Object> updatedNotifications, 
		NBSSecurityObj nbsSecurityObj)
		throws java.rmi.RemoteException, javax.ejb.EJBException, NEDSSSystemException;


    /**
     * This method returns a collection of all Notifications with recordStatusCd =
     * 'Pending Approval'
     *
     * @param nbsSecurityObj
     * @throws javax.ejb.EJBException
     */
    public Collection<Object>  getNotificationsForApproval    (NBSSecurityObj nbsSecurityObj)
        throws java.rmi.RemoteException, javax.ejb.EJBException, NEDSSSystemException;

    /**
     * @roseuid 3C4625DA0005
     * @J2EE_METHOD  --  getPublicHealthCaseVO
     */
    public PublicHealthCaseDT getPublicHealthCaseDT    (Long publicHealthCaseUID, NBSSecurityObj nbsSecurityObj)
         throws java.rmi.RemoteException, javax.ejb.EJBException, NEDSSSystemException;

   /**
     * @roseuid 3D6A8919007D
     * @J2EE_METHOD  --  setSummaryNotificationProxy
     */
    public Long setSummaryNotificationProxy    (NotificationProxyVO notificationProxyVO, NBSSecurityObj nbsSecurityObj)
           throws java.rmi.RemoteException, javax.ejb.EJBException, NEDSSSystemException, NEDSSConcurrentDataException;

    /**
     * @roseuid 3D6CE0A501FB
     * @J2EE_METHOD  --  validateNNDIndividualRequiredFields
     */

    /**
     * Validate that required fields are present in the Investigation. For example,
     * Diagnosis is required for all Hepatitis investigations.
     */
    public Collection<Object>  validateNNDIndividualRequiredFields    (Long publicHealthCaseUid, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException;

    /**
     * @roseuid 3D6E314302D9
     * @J2EE_METHOD  --  validateNNDSummaryRequiredFields
     */

    /**
     * Validate that the required fields are present in a Summary Notification.
     */
    public Collection<Object>  validateNNDSummaryRequiredFields    (Long publicHealthCaseUid, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, javax.ejb.EJBException;
    public Collection<Object>  getRejectedNotifications(NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException,java.rmi.RemoteException, NEDSSSystemException;
    public Map<Object,Object> validatePAMNotficationRequiredFields(Long publicHealthCaseUid, Map<Object,Object> reqFields, String formCd , NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException,java.rmi.RemoteException, NEDSSSystemException;
    public  Map<Object, Object>  validatePAMNotficationRequiredFieldsGivenPageProxy(Object pageObj, Long publicHealthCaseUid,  Map<Object, Object>  reqFields,String formCd, NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException,java.rmi.RemoteException, NEDSSSystemException;
    
}