package gov.cdc.nedss.proxy.ejb.queue.bean;


import java.rmi.RemoteException;

import javax.ejb.CreateException;

public interface QueueHome  extends javax.ejb.EJBHome {
	public Queue create() throws RemoteException, CreateException;
	
}
