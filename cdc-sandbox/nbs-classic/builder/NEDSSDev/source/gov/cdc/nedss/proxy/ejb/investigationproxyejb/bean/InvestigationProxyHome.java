//
// -- Java Code Generation Process --

package gov.cdc.nedss.proxy.ejb.investigationproxyejb.bean;

// Import Statements
import javax.ejb.EJBHome;
import java.rmi.RemoteException;
import javax.ejb.*;

import gov.cdc.nedss.proxy.ejb.investigationproxyejb.bean.*;

public interface InvestigationProxyHome extends javax.ejb.EJBHome
{

    /**
     * @roseuid 3BF98CCD01FC
     * @J2EE_METHOD  --  create
     * Called by the client to create an EJB bean instance. It requires a matching pair in
     * the bean class, i.e. ejbCreate(...).
     */
  public InvestigationProxy create() throws RemoteException, CreateException;

}