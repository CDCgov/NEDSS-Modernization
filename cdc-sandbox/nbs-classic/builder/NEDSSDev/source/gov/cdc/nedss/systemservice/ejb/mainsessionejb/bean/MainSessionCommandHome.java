package gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface MainSessionCommandHome extends EJBHome
{

    public MainSessionCommand create() throws RemoteException, CreateException;
    public MainSessionCommand create(String username, String password, String sessionId, String pRemoteAddress, String pRemoteHost) throws RemoteException, CreateException;

}
