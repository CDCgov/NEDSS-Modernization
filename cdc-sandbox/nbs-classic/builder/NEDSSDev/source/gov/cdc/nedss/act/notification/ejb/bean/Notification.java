//
// -- Java Code Generation Process --

package gov.cdc.nedss.act.notification.ejb.bean;

// Import Statements
import javax.ejb.EJBObject;
import java.rmi.RemoteException;
import javax.ejb.*;

import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.act.notification.vo.*;
import gov.cdc.nedss.act.notification.dt.*;

/**
* Name:		Remote interface for Notification EJB
* Description:	The bean is an entity bean for notification
* Copyright:    Copyright (c) 2002
* Company:      Computer Sciences Corporation
* @author:      NEDSS Development Team
* @version      NBS1.1
*/
public interface Notification extends javax.ejb.EJBObject
{

    /**
     * A getting method to get NotificationVO
     * @return NotificationVO
     * @throws java.rmi.RemoteException
     */
    public NotificationVO getNotificationVO()
    	throws java.rmi.RemoteException;

    /**
     * A setting method to set NotificationVO
     * @return NotificationVO
     * @throws java.rmi.RemoteException
     * @throws NEDSSConcurrentDataException
     */
    public void setNotificationVO(NotificationVO notificationVO)
    	throws java.rmi.RemoteException, NEDSSConcurrentDataException;

    /**
     * A getting method to get NotificationDT
     * @return NotificationDT
     * @throws java.rmi.RemoteException
     */
    public NotificationDT getNotificationInfo()
    	throws java.rmi.RemoteException;
}