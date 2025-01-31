//
// -- Java Code Generation Process --

package gov.cdc.nedss.proxy.ejb.observationproxyejb.bean;

public interface ObservationProxyHome extends javax.ejb.EJBHome
{

    /**
     * @roseuid 3C03AB4100D1
     * @J2EE_METHOD  --  create
     * Called by the client to create an EJB bean instance. It requires a matching pair in
     * the bean class, i.e. ejbCreate(...).
     */
    public ObservationProxy create    ()
                throws java.rmi.RemoteException, javax.ejb.CreateException;
}