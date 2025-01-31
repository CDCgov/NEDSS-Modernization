package gov.cdc.nedss.geocoding.geodata;

/**
 * Interface for the geodata client implementation.  Supports
 * lookup of an input address and the return of all matching
 * output address records (and corresponding attributes).
 * 
 * @author rdodge
 *
 */
public interface GeoDataClientImpl {

	/** Performs a lookup on the specified input address. */
	public GeoDataResult lookupAddress(GeoDataAddress geoDataAddress) throws NEDSSGeoDataException;
}
