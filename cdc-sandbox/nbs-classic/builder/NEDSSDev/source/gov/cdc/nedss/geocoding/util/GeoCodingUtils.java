package gov.cdc.nedss.geocoding.util;

import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.PropertyUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * Geocoding utilities class.
 * 
 * @author rdodge
 *
 */
public class GeoCodingUtils {

	public static final String PROPERTIES_FILENAME = "geocoder.properties";

	public static final String nedssDirectory = new StringBuffer(System
			.getProperty("nbs.dir")).append(File.separator).toString().intern();
	public static final String propertiesDirectory = new StringBuffer(
			nedssDirectory).append("Properties").append(File.separator).toString().intern();

    static final LogUtils logger = new LogUtils(GeoCodingUtils.class.getName());

    /** Geocoder properties object. */
	private static Properties properties = null;

	/** Set to <code>false</code> after initial load is attempted.  Can be [re]set manually. */
	private static boolean attemptLoad = true;


	/**
	 * Returns the geocoder properties object.  If indicated, the object
	 * will be loaded from the NEDSS properties file before it is returned.
	 * 
	 * @return
	 */
	public static synchronized Properties getNEDSSProperties() {

		if (willAttemptLoad()) {
			loadNEDSSProperties();
		}
		return properties;
	}

	/** Resets the properties object. */
	public static synchronized void loadNEDSSProperties() {

		properties = extractGeocoderProperties(PropertyUtil.getInstance().getProperties());
		setAttemptLoad(false);
	}

	/**
	 * Returns the geocoder properties object.  If indicated, the object
	 * will be loaded from the default properties file before it is returned.
	 * 
	 * @return
	 */
	public static synchronized Properties getProperties() {

		if (willAttemptLoad()) {
			loadProperties();
		}
		return properties;
	}

	/** Resets the properties object. */
	public static synchronized void loadProperties() {

		properties = loadPropertiesFile(propertiesDirectory + PROPERTIES_FILENAME);
		setAttemptLoad(false);
	}

	/**
	 * Loads the specified properties file and returns the properties object.
	 * 
	 * @param filename
	 * @return
	 */
	protected static Properties loadPropertiesFile(String filename) {

		Properties properties = null;
		FileInputStream propertiesFile = null;

		try {
			propertiesFile = new FileInputStream(filename);
			if (propertiesFile != null) {
				properties = new Properties();
				properties.load(propertiesFile);
			}
			else {
				logger.fatal("The geocoder properties file cannot be located.");
			}
		}
		catch (IOException e) {
			logger.fatal("Exception while attempting to load properties file \"" +
					filename + "\".", getStackTrace(e));
		}
		finally {
			if (propertiesFile != null) {
				try {
					// Close properties file //
					propertiesFile.close();
				}
				catch (IOException e) {
					logger.fatal("Exception while attempting to close properties file \"" +
							filename + "\".", getStackTrace(e));
				}
			}
		}
		return properties;
	}

	/** Returns value of the load attempt flag.  @return */
	public static boolean willAttemptLoad() {
		return attemptLoad;
	}

	/**
	 * Sets the load attempt flag to the indicated value.
	 * @param newValue
	 */
	public static void setAttemptLoad(boolean willAttemptLoad) {
		attemptLoad = willAttemptLoad;
	}


	// Properties Support //

	/**
	 * Fetches the indicated boolean property, or the default value
	 * if it is not found.
	 * 
	 * @param properties
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public static boolean getBooleanProperty(Properties properties, String name, boolean defaultValue) {

		String stringValue = properties.getProperty(name);
		return defaultValue ? !"false".equalsIgnoreCase(stringValue) : "true".equalsIgnoreCase(stringValue);
	}

	/**
	 * Fetches the indicated integer property, or the default value
	 * if it is not found.
	 * 
	 * @param properties
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public static int getIntegerProperty(Properties properties, String name, int defaultValue) {

		int result = defaultValue;

		String stringValue = properties.getProperty(name);
		if (stringValue != null && stringValue.length() > 0) {
			try {
				result = Integer.parseInt(stringValue);
			}
			catch (NumberFormatException e) {}
		}
		return result;
	}

	/**
	 * Fetches the indicated floating-point number property, or the default value
	 * if it is not found.
	 * 
	 * @param properties
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public static float getFloatProperty(Properties properties, String name, float defaultValue) {

		float result = defaultValue;

		String stringValue = properties.getProperty(name);
		if (stringValue != null && stringValue.length() > 0) {
			try {
				result = Float.parseFloat(stringValue);
			}
			catch (NumberFormatException e) {}
		}
		return result;
	}


	// Helpers //

	/**
	 * Extracts all properties beginning with "GEO"
	 * from the specified NEDSS properties object.
	 * 
	 * @param nedssProperties
	 * @return
	 */
	public static Properties extractGeocoderProperties(Properties nedssProperties) {

		Properties result = new Properties();

		Map.Entry entry = null;
		String key = null, adaptedKey = null, value = null;

		// Iterate through all properties //
		if (nedssProperties != null) {
			for (Iterator it = nedssProperties.entrySet().iterator(); it.hasNext(); ) {
				entry = (Map.Entry) it.next();
				key = (String) entry.getKey();

				// Migrate property if it is a geocoder property //
				if (key.toUpperCase().startsWith("GEO")) {
					adaptedKey = adaptFromNEDSS(key);
					value = (String) entry.getValue();
					result.setProperty(adaptedKey, value);

					logger.debug("Setting geocoder property \"" + key + "\" -> \"" + adaptedKey + "\".");
				}
			}
		}
		return result;
	}

	/**
	 * Adapts a NEDSS properties string to a geocoder property string.
	 * 
	 * @param nedssString
	 * @return
	 */
	private static String adaptFromNEDSS(String nedssString) {

		int firstSubstring = 0;
		String[] substrings = null;
		StringBuffer toSB = new StringBuffer();

		// Split NEDSS string //
		if (nedssString != null) {
			substrings = nedssString.toLowerCase().split("_");
		}

		if (substrings != null && substrings.length > 0) {

			// Rule: GEOCODER_* -> geocoder.
			if ("geocoder".equals(substrings[0])) {
				toSB.append("geocoder");
				firstSubstring = 1;
			}

			// Rule: GEODATA_ -> geodata.
			// Rule: GEODATA_CLIENT_<next word>_ -> geodata.client.<next word>.
			else if ("geodata".equals(substrings[0])) {
				toSB.append("geodata");
				firstSubstring = 1;
				if (substrings.length > 1 && "client".equals(substrings[1])) {
					toSB.append(".client");
					firstSubstring = 2;
					if (substrings.length > 2) {
						toSB.append(".").append(substrings[2]);
						firstSubstring = 3;
					}
				}
			}

			// Rule: For the remaining substring, WORD1_WORD2_WORD3 ... -> word1Word2Word3 ...
			if (firstSubstring < substrings.length) {

				// First word remains lowercase //
				toSB.append(".").append(substrings[firstSubstring]);

				// Remaining words are uppercased //
				for (int i = firstSubstring + 1; i < substrings.length; i++) {
					toSB.append(substrings[i].length() > 0 ? substrings[i].substring(0, 1).toUpperCase() : "");
					toSB.append(substrings[i].length() > 1 ? substrings[i].substring(1) : "");
				}
			}
		}
		return toSB.toString();
	}

	/**
	 * Writes the stack trace to a string.
	 * 
	 * @param t
	 * @return
	 */
	public static String getStackTrace(Throwable t) {

		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		t.printStackTrace(pw);
		pw.flush();

		return sw.toString();
	}
}
