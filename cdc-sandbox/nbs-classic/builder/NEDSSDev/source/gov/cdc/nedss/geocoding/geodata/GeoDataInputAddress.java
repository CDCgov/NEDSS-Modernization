package gov.cdc.nedss.geocoding.geodata;

/**
 * Interface encapsulating a geodata input address.
 * 
 * @author rdodge
 * 
 */
public interface GeoDataInputAddress extends GeoDataAddress {

	/** Sets all relevant fields for an input address. */
	void setInputAddress(String addr1, String addr2,
			String city, String state, String zip);

	/** Retrieves address line 1. */
	String getAddressLine1();

	/** Retrieves address line 2. */
	String getAddressLine2();

	/** Retrieves city. */
	String getCity();

	/** Retrieves state. */
	String getState();

	/** Retrieves zip code (5-digit). */
	String getZip();

	/** Returns a string representation of the input address. */
	String inputToString();
}
