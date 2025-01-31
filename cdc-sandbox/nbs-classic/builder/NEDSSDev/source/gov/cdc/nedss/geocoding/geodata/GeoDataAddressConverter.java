package gov.cdc.nedss.geocoding.geodata;

/**
 * Geodata Address Converter interface.  Supports conversion of
 * an application input address to the geodata client and
 * vice versa.  (Note: the application input and output address
 * object types may be different.)
 * 
 * @author rdodge
 *
 */
public interface GeoDataAddressConverter {

	/** Returns a new input address instance. */
	public GeoDataInputAddress newInputAddress();

	/** Returns a new output address instance. */
	public GeoDataOutputAddress newOutputAddress();

	/** Converts an input address to geodata format. */
	public GeoDataInputAddress convertInputToGeoData(Object inputAddress);

	/** Converts a geodata address to output format. */
	public Object convertGeoDataToOutput(GeoDataOutputAddress outputAddress);
}
