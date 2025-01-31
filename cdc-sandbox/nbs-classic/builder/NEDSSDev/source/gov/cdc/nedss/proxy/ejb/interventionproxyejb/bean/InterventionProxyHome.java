/**
 * Title: InterventionProxyHome class.
 * Description: An EJB Home object for InterventionProxy
 * Copyright:    Copyright (c) 2001
 * Company: Computer Sciences Corporation
 * @author NEDSS Development Team
 * @version 1.0
 */

package gov.cdc.nedss.proxy.ejb.interventionproxyejb.bean;

import javax.ejb.EJBHome;
import java.rmi.RemoteException;
import javax.ejb.*;

public interface InterventionProxyHome extends javax.ejb.EJBHome
{
    /**
     * Called by the client to create an EJB bean instance. It requires a matching pair in
     * the bean class, i.e. ejbCreate(...).
     * @throws java.rmi.RemoteException, javax.ejb.CreateException
     */
    public InterventionProxy create () throws java.rmi.RemoteException, javax.ejb.CreateException;
}