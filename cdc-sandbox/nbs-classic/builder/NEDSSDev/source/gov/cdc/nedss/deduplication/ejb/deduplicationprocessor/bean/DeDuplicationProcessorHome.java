package gov.cdc.nedss.deduplication.ejb.deduplicationprocessor.bean;

import java.rmi.RemoteException;
import javax.ejb.*;

public interface DeDuplicationProcessorHome extends EJBHome
{
    //public DeDuplicationBusinessInterface  theDeDuplicationBusinessInterface;
    
    /**
     * @roseuid 3E65159F036B
     * @J2EE_METHOD  --  create
     * Called by the client to create an EJB bean instance. It requires a matching pair in
     * the bean class, i.e. ejbCreate().
     * @throws java.rmi.RemoteException
     * @throws javax.ejb.CreateException
     */
    public DeDuplicationProcessor create() throws RemoteException, CreateException;
}