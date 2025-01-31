package gov.cdc.nedss.geocoding.geodata.centrus;

import java.util.Properties;

/**
 * Default config object for Centrus AB geocoding; provides
 * hard-coded values for all of the settings necessary to
 * connect to an AddressBroker server running on the local
 * machine.
 * 
 * @author rdodge
 *
 */
public class CentrusABDefaultConfig extends CentrusABConfig {

	/**
	 * Initializes the config object with default settings.
	 * 
	 * (Note: Ignores the properties object.)
	 * 
	 * @param properties
	 */
	public void init(Properties properties) {
		this.applyDefaultSettings();
	}
}
