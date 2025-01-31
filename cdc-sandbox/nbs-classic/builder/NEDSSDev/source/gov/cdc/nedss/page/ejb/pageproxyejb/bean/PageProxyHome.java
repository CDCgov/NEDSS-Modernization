package gov.cdc.nedss.page.ejb.pageproxyejb.bean;

import java.rmi.RemoteException;

import javax.ejb.CreateException;

public interface PageProxyHome extends javax.ejb.EJBHome {
	public PageProxy create() throws RemoteException, CreateException;
}