package gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean;

import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;

import java.rmi.RemoteException;
import java.util.*;

import javax.ejb.CreateException;
import javax.ejb.EJBObject;

public interface MainSessionCommand extends EJBObject
{

    public void logout(String uFirstNm,String uLastNm, String pSessionID, String pRemoteAddress, String pRemoteHost, Long nedssEntryId) throws RemoteException;
    public NBSSecurityObj nbsSecurityLogin(String username, String password) throws RemoteException, NEDSSAppException;
    public ArrayList<?>  processRequest(String sBeanJndiName, String sMethod, Object[] oParams) throws NEDSSAppException,NedssAppLogException, RemoteException, CreateException, javax.naming.NamingException, java.lang.reflect.InvocationTargetException, java.lang.IllegalAccessException, NEDSSConcurrentDataException, javax.ejb.EJBException;
    public String test() throws RemoteException;

}
