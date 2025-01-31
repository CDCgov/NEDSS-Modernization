package gov.cdc.nedss.geocoding.geodata.centrus;

import java.util.Properties;

import gov.cdc.nedss.geocoding.geodata.GeoDataClientImpl;
import gov.cdc.nedss.geocoding.geodata.GeoDataConfig;
import gov.cdc.nedss.geocoding.geodata.GeoDataFactory;
import gov.cdc.nedss.geocoding.geodata.NEDSSGeoDataException;

/**
 * Centrus AB factory object.
 * 
 * @author rdodge
 *
 */
public class CentrusABFactory extends GeoDataFactory {

	/**
	 * Returns a new Centrus geodata config object.
	 * 
	 * @param properties
	 * @return
	 */ 
	public GeoDataConfig newConfig(Properties properties, String prefix) {

		CentrusABConfig config = new CentrusABDefaultConfig();
		config.init(properties, prefix);

		return config;
	}

	/**
	 * Returns a new Centrus geodata client implementation.
	 * 
	 * @param config
	 * @return
	 */ 
	public GeoDataClientImpl newClientImpl(GeoDataConfig config) throws NEDSSGeoDataException {
		return new CentrusABClientImpl((CentrusABConfig) config);
	}
}
