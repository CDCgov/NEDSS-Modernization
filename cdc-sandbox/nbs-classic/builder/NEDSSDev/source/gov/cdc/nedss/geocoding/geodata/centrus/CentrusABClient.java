package gov.cdc.nedss.geocoding.geodata.centrus;

import java.util.Properties;

import org.apache.log4j.Logger;

import gov.cdc.nedss.geocoding.geodata.GeoDataBatchableClientImpl;
import gov.cdc.nedss.geocoding.geodata.GeoDataReusableClientImpl;
import gov.cdc.nedss.geocoding.geodata.GeoDataAddress;
import gov.cdc.nedss.geocoding.geodata.GeoDataClientBase;
import gov.cdc.nedss.geocoding.geodata.GeoDataResult;
import gov.cdc.nedss.geocoding.geodata.GeoDataUtils;
import gov.cdc.nedss.geocoding.geodata.NEDSSGeoDataException;
import gov.cdc.nedss.geocoding.util.GeoCodingUtils;

/**
 * Centrus AddressBroker client.
 * 
 * @author rdodge
 *
 */
public class CentrusABClient extends GeoDataClientBase {

	static Logger logger = Logger.getLogger(CentrusABClient.class.getName());

	/**
	 * Initializes the Centrus AddressBroker geodata client.
	 * 
	 * @param properties
	 * @param clientType Additional property substring appended to the
	 *                   geodata prefix, if available; if null or blank,
	 *                   no substring is appended to the geodata prefix.
	 */
	public void init(Properties properties, String clientType) throws NEDSSGeoDataException {

		String errorString = null;

		// Attempt to initialize client //
		try {
			factory = new CentrusABFactory();
			factory.setAddressConverter(new CentrusBatchAddressConverter());
			config = factory.newConfig(properties, clientType);
			clientImpl = factory.newClientImpl(config);

			initialized = true;
		}
		catch (Exception e) {
			initialized = false;
			errorString = "Cannot instantiate Centrus AB GeoData client";
			logger.error(errorString, e);
			throw (NEDSSGeoDataException) new NEDSSGeoDataException(errorString + ": " +
					GeoCodingUtils.getStackTrace(e)).initCause(e); 
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
			throw new NEDSSGeoDataException("Centrus AB GeoData client is not initialized.");
		}

		// Clear client implementation for reuse //
		((GeoDataReusableClientImpl) clientImpl).clear();

		// Perform lookup //
		return clientImpl.lookupAddress(geoDataAddress);
	}

	/**
	 * Performs address lookup and geocoding on the specified array of addresses.
	 * 
	 * @param geoDataAddresses
	 * @return
	 * @throws NEDSSGeoDataException
	 */
	public GeoDataResult[] lookupAddresses(GeoDataAddress[] geoDataAddresses) throws NEDSSGeoDataException {

		// Ensure client has been initialized //
		if (!isInitialized()) {
			throw new NEDSSGeoDataException("Centrus AB GeoData client is not initialized.");
		}

		// Clear client implementation for reuse //
		((GeoDataReusableClientImpl) clientImpl).clear();

		// Perform lookup //
		return ((GeoDataBatchableClientImpl) clientImpl).lookupAddresses(geoDataAddresses);
	}


	/**
	 * Main method.  Instantiates a Centrus geodata client
	 * and attempts to read from the indicated address input
	 * file.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		// Process command line //
		GeoDataUtils.CommandLineOptions options = new GeoDataUtils.CommandLineOptions();
		if (!GeoDataUtils.processArgs(args, options)) {
			System.err.println("Usage: java " + CentrusABClient.class.getName() +
					" [-p <properties filename>] [-c <client type>] <address input filename>");
			return;  // EARLY EXIT
		}

		// Run session //
		GeoDataUtils.runClientSession(options, new CentrusABClient());
	}
}
