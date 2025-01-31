package gov.cdc.nedss.nnd.ejb.nndmessageprocessorejb.bean;

public interface NNDMessageProcessorHome extends javax.ejb.EJBHome
{

    /**
     * @roseuid 3C0648F20073
     * @J2EE_METHOD -- create Called by the client to create an EJB bean
     *              instance. It requires a matching pair in the bean class,
     *              i.e. ejbCreate(...).
     */
    public NNDMessageProcessor create() throws java.rmi.RemoteException, javax.ejb.CreateException;
}