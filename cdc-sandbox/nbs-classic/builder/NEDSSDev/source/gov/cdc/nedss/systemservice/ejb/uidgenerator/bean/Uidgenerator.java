package gov.cdc.nedss.systemservice.ejb.uidgenerator.bean;

import gov.cdc.nedss.exception.*;
import gov.cdc.nedss.systemservice.nbssecurity.*;

import java.rmi.RemoteException;
import java.util.*;

import javax.ejb.CreateException;
import javax.ejb.EJBObject;

public interface Uidgenerator extends EJBObject
{
    public HashMap<Object,Object> getLocalID(String theClass, short cacheCount) throws Exception, RemoteException, CreateException;
    public String test() throws RemoteException;
}
