package gov.cdc.nedss.proxy.ejb.pamproxyejb.bean;

import java.rmi.RemoteException;

import javax.ejb.CreateException;

public interface PamProxyHome extends javax.ejb.EJBHome {
	public PamProxy create() throws RemoteException, CreateException;
}