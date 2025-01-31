package gov.cdc.nedss.report.ejb.sas.bean;

import java.rmi.*;

import javax.ejb.*;

public interface SASEngineHome extends EJBHome
{

    public SASEngine create() throws RemoteException, CreateException;

}
