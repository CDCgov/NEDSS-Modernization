package gov.cdc.nedss.geocoding.geodata;

import java.util.Properties;

/**
 * GeoData Factory.  Must support instantiation of
 * compatible client implementations and config objects.
 * 
 * Also provides binding for input/output address conversion;
 * there may be more than one address conversion possible for
 * a given geodata client type (though a given factory instance
 * must bind to only one converter).  For instance, batch geocoding
 * may need to map output to a batch output table format instead
 * of the postal locator address format.  
 * 
 * @author rdodge
 *
 */
public abstract class GeoDataFactory {

	GeoDataAddressConverter addressConverter;

	/** Must return a new geodata config instance. */ 
	public abstract GeoDataConfig newConfig(Properties properties, String prefix);

	/** Must return a new geodata client implementation instance. */
	public abstract GeoDataClientImpl newClientImpl(GeoDataConfig config) throws NEDSSGeoDataException;

	/** Must return an address converter compatible with the config/client impl. */
	public GeoDataAddressConverter getAddressConverter() throws NEDSSGeoDataException {
		if (addressConverter == null) {
			throw new NEDSSGeoDataException("No address converter set.");
		}
		return addressConverter;
	}

	/** Must be set prior to use by the client. */
	public void setAddressConverter(GeoDataAddressConverter addressConverter) {
		this.addressConverter = addressConverter; 
	}
}
