package gov.cdc.nedss.localfields.ejb.localfieldsejb.bean;


import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBObject;
import javax.ejb.FinderException;

/**
 * LocalFields is the Remote Interface of the LocalFields Session Bean.
 * @author nmallela
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: Computer Sciences Corporation</p>
 * LocalFields.java
 * Sep 4, 2008
 * @version
 */
public interface LocalFields extends EJBObject {
	
    public Collection<Object>  processLDFMetaDataRequest(String daoName, String sMethod, Object[] oParams, NBSSecurityObj nbsSecurityObj) throws RemoteException,EJBException, NEDSSSystemException, FinderException, CreateException;
    
} //LDFMetaData