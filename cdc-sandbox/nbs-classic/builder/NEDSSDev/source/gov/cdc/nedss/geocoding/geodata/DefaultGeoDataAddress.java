package gov.cdc.nedss.geocoding.geodata;

import java.util.HashMap;
import java.util.Map;

/**
 * Default geodata address.  Uses a map with keyed fields.
 * 
 * @author rdodge
 *
 */
public class DefaultGeoDataAddress implements GeoDataAddress {

	protected Map<Object,Object> addressFields = new HashMap<Object,Object>();

	public void clear() {
		addressFields.clear();
	}
	public Object getField(String key) {
		return addressFields.get(key);
	}
	public String getString(String key) {
		return (String) addressFields.get(key);
	}
	public Integer getInteger(String key) {
		return (Integer) addressFields.get(key);
	}
	public Float getFloat(String key) {
		return (Float) addressFields.get(key);
	}
	public void setField(String key, Object value) {
		addressFields.put(key, value);
	}
}
