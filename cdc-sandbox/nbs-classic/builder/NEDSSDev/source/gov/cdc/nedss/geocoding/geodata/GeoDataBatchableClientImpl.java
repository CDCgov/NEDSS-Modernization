package gov.cdc.nedss.geocoding.geodata;

/**
 * Interface extension representing a geodata client implementation that
 * supports lookup of multiple addresses per method call.
 * 
 * Note: Callers must be aware that the server is likely to support
 * a maximum address array size, and it is the responsiblity of the
 * caller either to know the limit ahead of time or to expose the
 * limit as a configurable parameter.  The server must also support
 * a way to correlate output addresses with the input address that
 * generated them, and callers must make use of this feature.
 * 
 * @author rdodge
 *
 */
public interface GeoDataBatchableClientImpl extends GeoDataClientImpl {

	/** Performs a lookup on the specified input addresses. */
	public GeoDataResult[] lookupAddresses(GeoDataAddress[] geoDataAddress) throws NEDSSGeoDataException;
}
