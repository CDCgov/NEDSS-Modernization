package gov.cdc.nedss.systemservice.ejb.nbschartsejb.bean;

/**
 * 
 * @author nmallela
 *
 */
public interface NBSChartHome extends javax.ejb.EJBHome {

	/**
	 * 
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws javax.ejb.CreateException
	 */
	public NBSChart create() throws java.rmi.RemoteException, javax.ejb.CreateException;
}
