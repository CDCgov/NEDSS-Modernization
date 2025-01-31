package gov.cdc.nedss.geocoding.geodata;

import java.util.Properties;

/**
 * Geodata client base class.  Encapsulates access to
 * a client implementation object that may be either short-lived
 * (where a fresh instance is created for each call) or long-lived.
 * 
 * @author rdodge
 *
 */
public abstract class GeoDataClientBase implements GeoDataClient {

	protected boolean initialized;

	protected GeoDataFactory factory;
	protected GeoDataConfig config;
	protected GeoDataClientImpl clientImpl;

	/**
	 * Default initializer for the geodata client.  Should
	 * be overridden by subclasses.
	 * 
	 * @param properties
	 * @param clientType
	 * @throws NEDSSGeoDataException
	 */
	public void init(Properties properties, String clientType) throws NEDSSGeoDataException {
		initialized = true;
	}

	/** Indicates whether the client has been successfully initialized.  @return */
	public boolean isInitialized() {
		return initialized;
	}


	/** Returns client implementation object.  @return */
	public GeoDataClientImpl getClientImpl() {
		return clientImpl;
	}

	/** Returns config object.  @return */
	public GeoDataConfig getConfig() {
		return config;
	}

	/** Returns geodata factory object.  @return */
	public GeoDataFactory getFactory() {
		return factory;
	}

	/**
	 * Must perform address lookup and geocoding on the specified address.
	 * 
	 * @param geoDataAddress
	 * @return
	 * @throws NEDSSGeoDataException
	 */
	public abstract GeoDataResult lookupAddress(GeoDataAddress geoDataAddress) throws NEDSSGeoDataException;

	/**
	 * Must perform address lookup and geocoding on the specified array of addresses,
	 * if the client implementation supports it (otherwise an exception should be thrown).
	 * 
	 * @param geoDataAddresses
	 * @return
	 * @throws NEDSSGeoDataException
	 */
	public abstract GeoDataResult[] lookupAddresses(GeoDataAddress[] geoDataAddress) throws NEDSSGeoDataException;
}
