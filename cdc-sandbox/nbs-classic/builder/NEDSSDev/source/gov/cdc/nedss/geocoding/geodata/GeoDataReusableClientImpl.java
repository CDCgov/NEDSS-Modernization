package gov.cdc.nedss.geocoding.geodata;

/**
 * Interface extension representing a geodata client implementation
 * that can support reuse of an existing server configuration between
 * method calls.  (Otherwise it is assumed that the client implementation
 * will re-initialize from scratch on every method call.)
 * 
 * Implementors must guarantee that they are re-initialized and
 * ready for reuse after a call to <code>clear()</code>.
 * 
 * @author rdodge
 *
 */
public interface GeoDataReusableClientImpl {

	/**
	 * Clears the current client implementation state
	 * in preparation for the next address lookup.
	 */
	public void clear() throws NEDSSGeoDataException;
}
