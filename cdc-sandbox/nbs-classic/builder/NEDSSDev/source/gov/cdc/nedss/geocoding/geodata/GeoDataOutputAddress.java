package gov.cdc.nedss.geocoding.geodata;

/**
 * Interface encapsulating a geodata output address.
 * 
 * @author rdodge
 * 
 */
public interface GeoDataOutputAddress extends GeoDataAddress {

	/** Returns a string representation of the output address. */
	String outputToString();
}
