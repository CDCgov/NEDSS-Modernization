package gov.cdc.nedss.geocoding.geodata.centrus;

import gov.cdc.nedss.geocoding.geodata.GeoDataConfig;
import gov.cdc.nedss.geocoding.util.GeoCodingUtils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Properties;

import qms.addressbroker.client.QMSABConst;

/**
 * Config object for Centrus AB geocoding.
 * 
 * @author rdodge
 *
 */
public class CentrusABConfig implements GeoDataConfig {

	public static final String PIPE = "|";

	protected boolean mixedCase;
	protected boolean multiMatch;

	protected int inputMode;
	protected int matchMode; 

	protected String initList;

	protected String inputFields;
	protected String outputFields;

	protected String abServer;
	protected int abPort;
	protected String abTransport;
	protected String abUser;
	protected String abPassword;

	protected int readTimeoutInSeconds;

	/**
	 * Initializes the config object.
	 * 
	 * @param properties
	 */
	public void init(Properties properties, String prefix) {

		// If no properties or prefix specified, apply defaults only //
		if (properties == null || prefix == null) {
			this.applyDefaultSettings();
			return;  // EARLY EXIT
		}

		// If requested, apply default settings as a starting point // 
		if (GeoCodingUtils.getBooleanProperty(properties, prefix + "applyDefaultSettings", false)) {
			this.applyDefaultSettings();
		}

		// Override current settings with those from the properties object //
		mixedCase = GeoCodingUtils.getBooleanProperty(properties, prefix + "mixedCase", mixedCase);
		multiMatch = GeoCodingUtils.getBooleanProperty(properties, prefix + "multiMatch", multiMatch);

		inputMode = GeoCodingUtils.getIntegerProperty(properties, prefix + "inputMode", inputMode);
		matchMode = GeoCodingUtils.getIntegerProperty(properties, prefix + "matchMode", matchMode);

		initList = properties.getProperty(prefix + "initList", initList);

		abServer = properties.getProperty(prefix + "abServer", abServer);
		abPort = GeoCodingUtils.getIntegerProperty(properties, prefix + "abPort", abPort);
		abTransport = properties.getProperty(prefix + "abTransport", abTransport);
		abUser = properties.getProperty(prefix + "abUser", abUser);
		//abPassword = properties.getProperty(prefix + "abPassword", abPassword);

		readTimeoutInSeconds = GeoCodingUtils.getIntegerProperty(properties, prefix +
				"readTimeoutInSeconds", readTimeoutInSeconds);

		inputFields = properties.getProperty(prefix + "inputFields", inputFields);
		outputFields = properties.getProperty(prefix + "outputFields", outputFields);
	}

	/** Applies default settings to the config object. */
	protected void applyDefaultSettings() {

		mixedCase = true;
		multiMatch = true;

		inputMode = QMSABConst.AB_INPUT_PARSED_LASTLINE;
		matchMode = QMSABConst.AB_MODE_CLOSE; 

		initList = "Geostan|Geostan_Z9|Counties|States";

		abServer = "localhost";
		abPort = 4660;
		abTransport = "SOCKET";
		abUser = null;
		abPassword = null;

		readTimeoutInSeconds = 10;

		inputFields = buildInputFields();
		outputFields = buildOutputFields();
	}

	/** Builds input string spec for Centrus.  @return */
	protected String buildInputFields() {

		StringBuffer sb = new StringBuffer();

		sb.append(AddressFields.RECORD_ID).append(PIPE);
		sb.append(AddressFields.ADDRESS_LINE_1).append(PIPE);
		sb.append(AddressFields.ADDRESS_LINE_2).append(PIPE);
		sb.append(AddressFields.CITY).append(PIPE);
		sb.append(AddressFields.STATE).append(PIPE);
		sb.append(AddressFields.ZIP);

		return sb.toString();
	}

	/** Builds output string spec for Centrus.  @return */
	protected String buildOutputFields() {

		StringBuffer sb = new StringBuffer();

		sb.append(AddressFields.RECORD_ID).append(PIPE);
		sb.append(AddressFields.FIRM_NAME).append(PIPE);
		sb.append(AddressFields.ADDRESS_LINE_1).append(PIPE);
		sb.append(AddressFields.ADDRESS_LINE_2).append(PIPE);
		sb.append(AddressFields.CITY).append(PIPE);
		sb.append(AddressFields.STATE).append(PIPE);
		sb.append(AddressFields.ZIP).append(PIPE);
		sb.append(AddressFields.ZIP10).append(PIPE);
		sb.append(AddressFields.COUNTY_CODE).append(PIPE);
		sb.append(AddressFields.COUNTRY_NAME).append(PIPE);

		sb.append(AddressFields.HOUSE_NUMBER).append(PIPE);
		sb.append(AddressFields.PREFIX_DIRECTION).append(PIPE);
		sb.append(AddressFields.STREET_NAME).append(PIPE);
		sb.append(AddressFields.STREET_TYPE).append(PIPE);
		sb.append(AddressFields.POSTFIX_DIRECTION).append(PIPE);
		sb.append(AddressFields.UNIT_NUMBER).append(PIPE);
		sb.append(AddressFields.UNIT_TYPE).append(PIPE);
		sb.append(AddressFields.ZIP4).append(PIPE);

		sb.append(AddressFields.LONGITUDE).append(PIPE);
		sb.append(AddressFields.LATITUDE).append(PIPE);
		sb.append(AddressFields.CENSUS_BLOCK_ID).append(PIPE);

		sb.append(AddressFields.SEGMENT_ID).append(PIPE);
		sb.append(AddressFields.DATA_TYPE).append(PIPE);
		sb.append(AddressFields.LOCATION_QUALITY_CODE).append(PIPE);
		sb.append(AddressFields.MATCH_CODE).append(PIPE);
		sb.append(AddressFields.SCORE);

		return sb.toString();
	}

	/** Overrides the <code>toString()</code> method. */
	public String toString() {

		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);

		pw.println("Centrus Config Object");
		pw.println("=====================");

		pw.println("[mixedCase=" + mixedCase + "][multiMatch=" + multiMatch +
				"][inputMode=" + inputMode + "][matchMode=" + matchMode + "]");
		pw.println();
		pw.println("[initList=" + initList + "]");
		pw.println();
		pw.println("[inputFields=" + inputFields + "]");
		pw.println();
		pw.println("[outputFields=" + outputFields + "]");
		pw.println();
//		pw.println("[abServer=" + abServer + "][abPort=" + abPort +
//				"][abTransport=" + abTransport + "][abUser=" + abUser +
//				"][abPassword=" + abPassword + "][readTimeoutInSeconds=" + readTimeoutInSeconds + "]");

		return sw.toString();
	}
}
