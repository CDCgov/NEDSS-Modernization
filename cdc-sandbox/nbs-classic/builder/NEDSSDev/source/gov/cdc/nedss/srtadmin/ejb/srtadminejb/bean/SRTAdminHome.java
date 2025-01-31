package gov.cdc.nedss.srtadmin.ejb.srtadminejb.bean;

/**
 * 
 * @author nmallela
 *
 */
public interface SRTAdminHome extends javax.ejb.EJBHome {

	/**
	 * 
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws javax.ejb.CreateException
	 */
	public SRTAdmin create() throws java.rmi.RemoteException, javax.ejb.CreateException;
}
