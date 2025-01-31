package gov.cdc.nedss.systemservice.ejb.mprupdateejb.bean;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface MPRUpdateEngineHome extends EJBHome
{
    
    /**
     * @J2EE_METHOD  --  create     * Called by the client to create an EJB bean instance. It requires a matching pair in     * the bean class, i.e. ejbCreate().     * @throws java.rmi.RemoteException     * @throws javax.ejb.CreateException
     */
    public MPRUpdateEngine create    () 
                throws CreateException, RemoteException;
}