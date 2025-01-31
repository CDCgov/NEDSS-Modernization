
package gov.cdc.nedss.controller.ejb.actcontrollerejb.bean;

// Import Statements
import javax.ejb.EJBHome;
import java.rmi.RemoteException;
import javax.ejb.*;

public interface ActControllerHome extends javax.ejb.EJBHome
{

    /**
     * @roseuid 3BF3D720030A
     * @J2EE_METHOD  --  create
     * Called by the client to create an EJB bean instance. It requires a matching pair in
     * the bean class, i.e. ejbCreate(...).
     */
    public ActController create()
                throws java.rmi.RemoteException, javax.ejb.CreateException;
}