package gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.bean;

import java.rmi.RemoteException;

import javax.ejb.CreateException;

public interface PageManagementProxyHome extends javax.ejb.EJBHome {
	public PageManagementProxy create() throws RemoteException, CreateException;
}