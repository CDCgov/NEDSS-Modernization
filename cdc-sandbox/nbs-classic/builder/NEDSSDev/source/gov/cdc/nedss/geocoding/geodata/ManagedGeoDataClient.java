package gov.cdc.nedss.geocoding.geodata;

import java.util.Hashtable;
import java.util.Properties;

/**
 * Decorator that enables centralized control and management of geodata clients.
 * 
 * Maintains a registry of client types; allows at most one instance of each
 * client type to be instantiated.  Synchronizes access to registered clients.
 * 
 * @author rdodge
 *
 */
public class ManagedGeoDataClient implements GeoDataClient {

	/** Geodata client registry. */
	protected static Hashtable<Object, Object> clientByType = new Hashtable<Object, Object>();

	/** Geodata client instance. */
	protected GeoDataClient clientInstance;

	/** Geodata client type. */
	protected String clientType;

	/**
	 * Constructor.  Decorates client with a unique string used to
	 * indicate client type.
	 * 
	 * Note: Purposely does not use class name or other indicator
	 * unique to a class.  Thus the caller is permitted to load
	 * multiple instances of a specific concrete geodata client class,
	 * each with a different client type label (and potentially, different
	 * configuration settings).
	 * 
	 * @param clientInstance
	 * @param clientType
	 */
	public ManagedGeoDataClient(GeoDataClient clientInstance, String clientType) {
		this.clientInstance = clientInstance;
		this.clientType = clientType;
	}

	/**
	 * Synchronizes access to the initializer.
	 * 
	 * @param properties
	 * @param clientType
	 * @throws NEDSSGeoDataException
	 */
	public void init(Properties properties, String clientType) throws NEDSSGeoDataException {
		synchronized(this) {
			clientInstance.init(properties, clientType);
		}
	}

	/** No synchronization is applied to this accessor.  @return */ 
	public boolean isInitialized() {
		return clientInstance.isInitialized();
	}

	/**
	 * Synchronizes access to address lookups.
	 * 
	 * @param geoDataAddress
	 * @throws NEDSSGeoDataException
	 */
	public GeoDataResult lookupAddress(GeoDataAddress geoDataAddress) throws NEDSSGeoDataException {
		synchronized(this) {
			return clientInstance.lookupAddress(geoDataAddress);
		}
	}

	/**
	 * Synchronizes access to address array lookups.
	 * 
	 * @param geoDataAddresses
	 * @throws NEDSSGeoDataException
	 */
	public GeoDataResult[] lookupAddresses(GeoDataAddress[] geoDataAddresses) throws NEDSSGeoDataException {
		synchronized(this) {
			return clientInstance.lookupAddresses(geoDataAddresses);
		}
	}

	/**
	 * Obtains an instance of the geodata client.
	 * 
	 * @param clientType
	 * @param clazz The <code>Class</code> object of the desired geodata client class
	 * @return
	 */
	public static GeoDataClient getInstance(String clientType, Class<?> clazz) {

		GeoDataClient newInstance = null;
		synchronized(clientByType) {

			// Return existing instance (if any) //
			if (clientByType.contains(clientType)) {
				newInstance = (GeoDataClient) clientByType.get(clientType);
			}
			else {
				// Otherwise, create new instance & return //
				try {
					newInstance = (GeoDataClient) clazz.newInstance();
					clientByType.put(clientType, newInstance);
				}
				catch (ClassCastException e) {}
				catch (IllegalAccessException e) {}
				catch (InstantiationException e) {}
			}
		}
		return newInstance;
	}
}
