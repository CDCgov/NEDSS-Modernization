package gov.cdc.nedss.systemservice.ejb.uidgenerator.bean;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface UidgeneratorHome extends EJBHome
{
    public Uidgenerator create() throws RemoteException, CreateException;
}
