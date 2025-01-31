package gov.cdc.nedss.geocoding.geodata;

import gov.cdc.nedss.geocoding.util.GeoCodingUtils;

/**
 * Geodata factory loader.
 * 
 * @author rdodge
 *
 */
public class GeoDataFactoryLoader {

	/**
	 * Loads/returns the specified geodata factory configured with
	 * the specified address converter class.
	 * 
	 * @param factoryClassName
	 * @param addressConverterClassName
	 * @return
	 * @throws NEDSSGeoDataException
	 */
	public static GeoDataFactory loadFactory(String factoryClassName,
			String addressConverterClassName) throws NEDSSGeoDataException {

		// Load Factory //
		GeoDataFactory factory = null;
		try {
			Class<?> clazz = Class.forName(factoryClassName);
			factory = (GeoDataFactory) clazz.newInstance();
		}
		catch (ClassNotFoundException e) {
			throw (NEDSSGeoDataException) new NEDSSGeoDataException("Factory class not found: \"" +
					factoryClassName + "\": " + GeoCodingUtils.getStackTrace(e)).initCause(e);
		}
		catch (InstantiationException e) {
			throw (NEDSSGeoDataException) new NEDSSGeoDataException("Cannot instantiate factory class: \"" +
					factoryClassName + "\": " + GeoCodingUtils.getStackTrace(e)).initCause(e);
		}
		catch (IllegalAccessException e) {
			throw (NEDSSGeoDataException) new NEDSSGeoDataException("Cannot access factory class: \"" +
					factoryClassName + "\": " + GeoCodingUtils.getStackTrace(e)).initCause(e);
		}
		catch (ClassCastException e) {
			throw (NEDSSGeoDataException) new NEDSSGeoDataException("Cannot cast factory class to GeoDataFactory: \"" +
					factoryClassName + "\": " + GeoCodingUtils.getStackTrace(e)).initCause(e);
		}

		if (factory == null) {
			throw (NEDSSGeoDataException) new NEDSSGeoDataException("The following GeoDataFactory class is null: \"" +
					factoryClassName + "\".");
		}

		// Load Address Converter //
		GeoDataAddressConverter addressConverter = null;
		try {
			Class<?> clazz = Class.forName(addressConverterClassName);
			addressConverter = (GeoDataAddressConverter) clazz.newInstance();
		}
		catch (ClassNotFoundException e) {
			throw (NEDSSGeoDataException) new NEDSSGeoDataException("Address converter class not found: \"" +
					addressConverterClassName + "\"." + GeoCodingUtils.getStackTrace(e)).initCause(e);
		}
		catch (InstantiationException e) {
			throw (NEDSSGeoDataException) new NEDSSGeoDataException("Cannot instantiate address converter class: \"" +
					addressConverterClassName + "\"." + GeoCodingUtils.getStackTrace(e)).initCause(e);
		}
		catch (IllegalAccessException e) {
			throw (NEDSSGeoDataException) new NEDSSGeoDataException("Cannot access address converter class: \"" +
					addressConverterClassName + "\"." + GeoCodingUtils.getStackTrace(e)).initCause(e);
		}
		catch (ClassCastException e) {
			throw (NEDSSGeoDataException) new NEDSSGeoDataException("Cannot cast address converter class to GeoDataAddressConverter: \"" +
					addressConverterClassName + "\"." + GeoCodingUtils.getStackTrace(e)).initCause(e);
		}

		if (addressConverter == null) {
			throw new NEDSSGeoDataException("The following GeoDataAddressConverter class is null: \"" +
					addressConverterClassName + "\".");
		}

		// Bind Address Converter to Factory //
		factory.setAddressConverter(addressConverter);

		return factory;
	}
}
