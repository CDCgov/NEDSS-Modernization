package gov.cdc.nedss.page.ejb.portproxyejb.bean;

import gov.cdc.nedss.page.ejb.portproxyejb.bean.PortProxy;

import java.rmi.RemoteException;

import javax.ejb.CreateException;

public interface PortProxyHome  extends javax.ejb.EJBHome{
	public PortProxy create() throws RemoteException, CreateException;
}
