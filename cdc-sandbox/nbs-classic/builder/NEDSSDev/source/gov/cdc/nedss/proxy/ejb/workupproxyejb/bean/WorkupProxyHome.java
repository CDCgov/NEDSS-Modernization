package gov.cdc.nedss.proxy.ejb.workupproxyejb.bean;

// Import Statements
import javax.ejb.EJBHome;
import java.rmi.RemoteException;
import javax.ejb.*;

public interface WorkupProxyHome extends javax.ejb.EJBHome
{

    /**
     * @roseuid 3BFBFEBF0074
     * @J2EE_METHOD  --  create
     * Called by the client to create an EJB bean instance. It requires a matching pair in
     * the bean class, i.e. ejbCreate(...).
     */
  //  public WorkupProxy create    (SecurityObj securityObj)
    //            throws java.rmi.RemoteException, javax.ejb.CreateException;

	  public WorkupProxy create()
                throws java.rmi.RemoteException, javax.ejb.CreateException, EJBException;
}