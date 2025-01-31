//
// -- Java Code Generation Process --

package gov.cdc.nedss.proxy.ejb.tasklistproxyejb.bean;

// Import Statements
import javax.ejb.EJBHome;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.*;

public interface TaskListProxyHome extends javax.ejb.EJBHome
{

    /**
     * @roseuid 3C1BA547027E
     * @J2EE_METHOD  --  create
     * Called by the client to create an EJB bean instance. It requires a matching pair in
     * the bean class, i.e. ejbCreate(...).
     */
    public TaskListProxy create    ()
                throws java.rmi.RemoteException, javax.ejb.CreateException;
    
}