/**
 * Title: NotificationProxyHome
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:CSC
 * @author nedss project team
 * @version 1.0
 */

package gov.cdc.nedss.proxy.ejb.notificationproxyejb.bean;

// Import Statements
import javax.ejb.EJBHome;
import java.rmi.RemoteException;
import javax.ejb.*;

public interface NotificationProxyHome extends javax.ejb.EJBHome
{

    /**
     * @roseuid 3C43680D00BF
     * @J2EE_METHOD  --  create
     * Called by the client to create an EJB bean instance. It requires a matching pair in
     * the bean class, i.e. ejbCreate(...).
     */
    public NotificationProxy create    ()
                throws java.rmi.RemoteException, javax.ejb.CreateException;
}