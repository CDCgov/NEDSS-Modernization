package gov.cdc.nedss.geocoding.ejb.geocodingservice.bean;

import java.rmi.RemoteException;
import javax.ejb.*;

/**
 * Geocoding Service home interface.
 * 
 * @author rdodge
 */
public interface GeoCodingServiceHome extends EJBHome
{
	/**
	 * Create method.
	 *
	 * @throws java.rmi.RemoteException
	 * @throws javax.ejb.CreateException
	 */
	public GeoCodingService create() throws RemoteException, CreateException;
}