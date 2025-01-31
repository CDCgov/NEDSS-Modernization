package gov.cdc.nedss.geocoding.geodata;

import java.util.Properties;

/**
 * Geodata client interface.  Must support initialization (which is
 * expected to verify connectivity to the geodata source) as well as
 * address lookup.
 * 
 * @author rdodge
 *
 */
public interface GeoDataClient {

	String DEFAULT_CLIENT_TYPE = "default";

	/**
	 * Must initialize the geodata client.
	 * 
	 * @param properties
	 * @param clientType Property substring used to identify a unique set
	 *                   of property names within the indicated properties object.
	 *                   <code>DEFAULT_CLIENT_TYPE</code> is the default.
	 * 
	 * @throws NEDSSGeoDataException
	 */
	void init(Properties properties, String clientType) throws NEDSSGeoDataException;

	/** Must indicate whether the geodata client has been initialized.  @return */
	boolean isInitialized();

	/**
	 * Must perform address lookup and geocoding on the specified address.
	 * 
	 * @param geoDataAddress
	 * @return
	 * @throws NEDSSGeoDataException
	 */
	GeoDataResult lookupAddress(GeoDataAddress geoDataAddress) throws NEDSSGeoDataException;

	/**
	 * Must perform address lookup and geocoding on the specified array of addresses,
	 * if the client implementation supports it (otherwise an exception should be thrown).
	 * 
	 * @param geoDataAddresses
	 * @return
	 * @throws NEDSSGeoDataException
	 */
	GeoDataResult[] lookupAddresses(GeoDataAddress[] geoDataAddress) throws NEDSSGeoDataException;
}