package gov.cdc.nedss.geocoding.geodata;

import gov.cdc.nedss.geocoding.util.GeoCodingUtils;

import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * Loadable geodata client.  Uses a properties object to specify
 * the geodata client to be loaded.
 * 
 * @author rdodge
 *
 */
public class LoadableGeoDataClient extends GeoDataClientBase {

	public static final String GEODATA_PREFIX = "geodata.client";

	public static final String SUBPROPERTY_FACTORY_CLASS = "factoryClass";
	public static final String SUBPROPERTY_ADDRESS_CONVERTER_CLASS = "addressConverterClass";

	static Logger logger = Logger.getLogger(LoadableGeoDataClient.class.getName());

	/**
	 * Initializes the geodata client.
	 * 
	 * @param properties
	 * @param clientType Additional property substring appended to the
	 *                   geodata prefix, if available; if null or blank,
	 *                   no substring is appended to the geodata prefix.
	 */
	public void init(Properties properties, String clientType) throws NEDSSGeoDataException {

		String errorString = null;

		String factoryClass = null;
		String addressConverterClass = null;

		// Ensure properties object is available //
		if (properties == null) {
			errorString = "Cannot instantiate loadable geodata client: the properties object is null.";
			logger.error(errorString);
			throw new NEDSSGeoDataException(errorString);
		}

		// Attempt to initialize client //
		try {
			String clientTypeStr = (clientType != null && clientType.length() > 0) ?
					clientType + "." : "";

			String prefix = GEODATA_PREFIX + "." + clientTypeStr;
			factoryClass = properties.getProperty(prefix + 
					SUBPROPERTY_FACTORY_CLASS, null);
			addressConverterClass = properties.getProperty(prefix + 
					SUBPROPERTY_ADDRESS_CONVERTER_CLASS, null);

			factory = GeoDataFactoryLoader.loadFactory(factoryClass, addressConverterClass);
			config = factory.newConfig(properties, prefix);
			clientImpl = factory.newClientImpl(config);

			// Keep persistent reference to impl. if and only if it is reusable //
			if (!(clientImpl instanceof GeoDataReusableClientImpl)) {
				clientImpl = null;
			}
	
			initialized = true;
		}
		catch (Exception e) {
			initialized = false;
			errorString = "Cannot instantiate GeoData client with client type \"" +
					clientType + "\", factory \"" + factoryClass + "\", address converter \"" +
					addressConverterClass + "\"";

			logger.error(errorString, e);
			throw (NEDSSGeoDataException) new NEDSSGeoDataException(errorString +
					": " + GeoCodingUtils.getStackTrace(e)).initCause(e); 
		}
	}

	/**
	 * Performs address lookup and geocoding on the specified address.
	 * 
	 * @param geoDataAddress
	 * @return
	 * @throws NEDSSGeoDataException
	 */
	public GeoDataResult lookupAddress(GeoDataAddress geoDataAddress) throws NEDSSGeoDataException {

		// Ensure client has been initialized //
		if (!isInitialized()) {
			throw new NEDSSGeoDataException("GeoData client is not initialized.");
		}

		// Reuse or allocate new client implementation as appropriate //
		GeoDataClientImpl impl = null;
		if (clientImpl != null && clientImpl instanceof GeoDataReusableClientImpl) {
			((GeoDataReusableClientImpl) clientImpl).clear();
			impl = clientImpl;
		}
		else {
			impl = factory.newClientImpl(config);
		}

		// Perform lookup //
		return impl.lookupAddress(geoDataAddress);
	}

	/**
	 * Performs address lookup and geocoding on the specified address.
	 * 
	 * @param geoDataAddresses
	 * @return
	 * @throws NEDSSGeoDataException
	 */
	public GeoDataResult[] lookupAddresses(GeoDataAddress[] geoDataAddresses) throws NEDSSGeoDataException {

		// Ensure client has been initialized //
		if (!isInitialized()) {
			throw new NEDSSGeoDataException("GeoData client is not initialized.");
		}

		// Reuse or allocate new client implementation as appropriate //
		GeoDataClientImpl impl = null;
		if (clientImpl != null && clientImpl instanceof GeoDataReusableClientImpl) {
			((GeoDataReusableClientImpl) clientImpl).clear();
			impl = clientImpl;
		}
		else {
			impl = factory.newClientImpl(config);
		}

		// Ensure client implementation supports this operation //
		if (impl == null || !(impl instanceof GeoDataBatchableClientImpl)) {
			throw new NEDSSGeoDataException("GeoData client implementation does not support this operation.");
		}

		// Perform lookup //
		return ((GeoDataBatchableClientImpl) impl).lookupAddresses(geoDataAddresses);
	}

	/**
	 * Main method.  Instantiates a loadable geodata client
	 * and attempts to read from the indicated address input
	 * file.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		// Process command line //
		GeoDataUtils.CommandLineOptions options = new GeoDataUtils.CommandLineOptions();
		if (!GeoDataUtils.processArgs(args, options)) {
			System.err.println("Usage: java " + LoadableGeoDataClient.class.getName() +
					" [-p <properties filename>] [-c <client type>] <address input filename>");
			return;  // EARLY EXIT
		}

		// Run session //
		GeoDataUtils.runClientSession(options, new LoadableGeoDataClient());
	}
}
