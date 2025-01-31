package gov.cdc.nedss.geocoding.geodata;

import java.util.Properties;

/**
 * Interface for the geodata config object.
 * 
 * @author rdodge
 *
 */
public interface GeoDataConfig {

	/** Initializes the config object. */ 
	public void init(Properties properties, String prefix);
}
