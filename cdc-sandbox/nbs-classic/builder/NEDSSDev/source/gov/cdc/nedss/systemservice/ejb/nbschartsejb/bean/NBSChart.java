package gov.cdc.nedss.systemservice.ejb.nbschartsejb.bean;


import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBObject;
import javax.ejb.FinderException;

/**
 * 
 * @author Narendra Mallela
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: Computer Sciences Corporation</p>
 * NBSChart.java
 * Sep 25, 2009
 * @version
 */
public interface NBSChart extends EJBObject
{


    public Collection<Object> processNBSChartRequest(String daoName, String sMethod, Object[] oParams, NBSSecurityObj nbsSecurityObj) throws RemoteException,EJBException, NEDSSSystemException, FinderException, CreateException;

}
