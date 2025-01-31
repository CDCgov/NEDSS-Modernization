package gov.cdc.nedss.geocoding.geodata.centrus;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import qms.addressbroker.client.QMSABConst;
import qms.addressbroker.client.QMSAddressBroker;
import qms.addressbroker.client.QMSAddressBrokerFactory;

import gov.cdc.nedss.geocoding.geodata.GeoDataBatchableClientImpl;
import gov.cdc.nedss.geocoding.geodata.GeoDataOutputAddress;
import gov.cdc.nedss.geocoding.geodata.GeoDataReusableClientImpl;
import gov.cdc.nedss.geocoding.geodata.GeoDataAddress;
import gov.cdc.nedss.geocoding.geodata.GeoDataResult;
import gov.cdc.nedss.geocoding.geodata.NEDSSGeoDataException;
import gov.cdc.nedss.geocoding.util.GeoCodingUtils;

/**
 * Centrus AddressBroker client implementation.
 * 
 * @author rdodge
 *
 */
public class CentrusABClientImpl implements GeoDataBatchableClientImpl, GeoDataReusableClientImpl {

	static Logger logger = Logger.getLogger(CentrusABClientImpl.class.getName());
	private static GeoDataResult[] resultPrototype = new GeoDataResult[0];

	/** Config object. */
	private CentrusABConfig config;

	/** Centrus AddressBroker object interface. */
	private QMSAddressBroker ab;

	/**
	 * Constructor.  Authenticates to the AB server.
	 * 
	 * @param config
	 * @throws NEDSSGeoDataException
	 */
	public CentrusABClientImpl(CentrusABConfig config) throws NEDSSGeoDataException {

		String errorString = null;
		this.config = config;

		// Obtain AB connection //
		try {
			ab = QMSAddressBrokerFactory.make(
					config.abServer.trim() + ":" + config.abPort,
					config.abTransport, config.abUser, config.abPassword);

			// Configure AB //
			ab.setSocketReadTimeout(config.readTimeoutInSeconds);
			ab.setProperty(QMSABConst.AB_MIXED_CASE, config.mixedCase);
			ab.setProperty(QMSABConst.AB_INPUT_MODE, config.inputMode);
			ab.setProperty(QMSABConst.AB_MATCH_MODE, config.matchMode);
			ab.setProperty(QMSABConst.AB_KEEP_MULTIMATCH, config.multiMatch);
			ab.setProperty(QMSABConst.AB_INIT_LIST, config.initList);
			ab.setProperty(QMSABConst.AB_INPUT_FIELD_LIST, config.inputFields);
			ab.setProperty(QMSABConst.AB_OUTPUT_FIELD_LIST, config.outputFields);

			// Validate AB configuration //
			ab.validateProperties();
		}
		catch (InstantiationException e) {
			errorString = "Cannot instantiate AB Client Impl.";
			logger.error(errorString, e);
			throw (NEDSSGeoDataException) new NEDSSGeoDataException(errorString +
					": " + GeoCodingUtils.getStackTrace(e)).initCause(e);
		}
		catch (Exception e) {
			errorString = "Cannot configure AB Client Impl.";
			logger.error(errorString, e);
			throw (NEDSSGeoDataException) new NEDSSGeoDataException(errorString +
					": " + GeoCodingUtils.getStackTrace(e)).initCause(e);
		}
	}

	/**
	 * Clears the current state in preparation for the next lookup.
	 * 
	 * @throws NEDSSGeoDataException
	 */
	public void clear() throws NEDSSGeoDataException {

		String errorString = null;
		try {
			ab.clear();
		}
		catch (Exception e) {
			errorString = "Cannot clear AB Client Impl.";
			logger.error(errorString, e);
			throw (NEDSSGeoDataException) new NEDSSGeoDataException(errorString +
					": " + GeoCodingUtils.getStackTrace(e)).initCause(e);
		}
	}

	/**
	 * Performs a lookup on the specified input address.
	 * 
	 * @param geoDataAddress
	 * @return
	 * @throws NEDSSGeoDataException
	 */
	public GeoDataResult lookupAddress(GeoDataAddress geoDataAddress) throws NEDSSGeoDataException {

		String errorString = null;

		CentrusAddress inputAddress = null;
		GeoDataResult result = null;
		GeoDataOutputAddress[] outputAddresses = null;

		try {
			// Set input record //
			inputAddress = (CentrusAddress) geoDataAddress;
			setInputFields(inputAddress);
			ab.setRecord();

			// Call AddressBroker //
			ab.processRecords();

			// Calculate size //
			int listSize = 0;
			while (ab.getRecord()) {
				listSize++;
			}
			ab.resetRecord();

			// Build result address block //
			CentrusAddress outputAddress = null;
			outputAddresses = new GeoDataOutputAddress[listSize];
			result = new GeoDataResult();

			for (int i = 0; i < listSize; i++) {
				ab.getRecord();

				// Process fields for each output address //
				outputAddress = new CentrusAddress();
				setOutputFields(outputAddress);
				outputAddresses[i] = outputAddress;
			}

			// Add output addresses, match statistics to result //
			result.setOutputAddresses(outputAddresses);
			this.addMatchStatistics(result);
		}
		catch (Exception e) {
			errorString = "Cannot perform AB Client Impl. address lookup on \"" +
					inputAddress.inputToString() + "\"";
			logger.error(errorString, e);
			throw (NEDSSGeoDataException) new NEDSSGeoDataException(errorString + ": " +
					GeoCodingUtils.getStackTrace(e)).initCause(e);
		}

		// Exit //
		return result;
	}

	/**
	 * Performs a lookup on the specified input addresses.
	 * 
	 * Note: The caller must be sure to pass an array small enough to
	 * meet the needs of AddressBroker.
	 * 
	 * @param geoDataAddress
	 * @return
	 * @throws NEDSSGeoDataException
	 */
	public GeoDataResult[] lookupAddresses(GeoDataAddress[] geoDataAddress) throws NEDSSGeoDataException {

		String errorString = null;

		CentrusAddress inputAddress = null;
		List<Object> resultList = new ArrayList<Object> ();

		try {
			// Set input addresses //
			for (int i = 0; i < geoDataAddress.length; i++) {
				ab.setField(AddressFields.RECORD_ID, Integer.toString(i));
	
				// Set / advance input record //
				inputAddress = (CentrusAddress) geoDataAddress[i];
				setInputFields(inputAddress);
				ab.setRecord();
			}

			// Call AddressBroker //
			ab.processRecords();

			// Process Results (if any) //
			GeoDataResult result = null;
			List<Object>  outputAddresses = new ArrayList<Object> ();
			CentrusAddress outputAddress = null;

			int currentRecordID = -1;
			int nextRecordID = -1;
			Integer nextRecordIDField = null;

			// Output records are in a flat list with a single iterator //
			while (ab.getRecord()) {
				nextRecordIDField = parseIntegerField(ab.getField(AddressFields.RECORD_ID), null);
				if (nextRecordIDField != null) {
					nextRecordID = nextRecordIDField.intValue();
				}

				// Advance current input address if appropriate //
				if (nextRecordID != currentRecordID) {

					// Finalize results for current input address //
					if (outputAddresses.size() > 0) {
						result = new GeoDataResult();

						// Add output addresses, match statistics to result //
						result.setOutputAddresses((GeoDataOutputAddress[]) outputAddresses.toArray(
								new GeoDataOutputAddress[outputAddresses.size()]));
						this.addMatchStatistics(result);
						resultList.add(result);
						outputAddresses.clear();
					}
					currentRecordID = nextRecordID;
				}

				// Process fields for the current output address //
				outputAddress = new CentrusAddress();
				setOutputFields(outputAddress);

				// Add output address //
				outputAddress.setField(AddressFields.RECORD_ID, ab.getField(AddressFields.RECORD_ID));
				outputAddresses.add(outputAddress);
			}

			// Finalize results (if any) for the last input address //
			if (outputAddresses.size() > 0) {
				result = new GeoDataResult();

				// Add output addresses, match statistics to result //
				result.setOutputAddresses((GeoDataOutputAddress[]) outputAddresses.toArray(
						new GeoDataOutputAddress[outputAddresses.size()]));
				this.addMatchStatistics(result);
				resultList.add(result);
				outputAddresses.clear();
			}
		}
		catch (Exception e) {
			errorString = "Cannot perform AB Client Impl. address lookup on \"" +
					inputAddress.inputToString() + "\"";
			logger.error(errorString, e);
			throw (NEDSSGeoDataException) new NEDSSGeoDataException(errorString + ": " +
					GeoCodingUtils.getStackTrace(e)).initCause(e);
		}

		// Exit //
		return (GeoDataResult[]) resultList.toArray(resultPrototype);
	}

	/**
	 * Sets input fields for Centrus AddressBroker.
	 * 
	 * @param inputAddress
	 */
	private void setInputFields(CentrusAddress inputAddress) {
		
		// Set input fields //
		String field = inputAddress.getAddressLine1();
		if (field != null) {
			ab.setField(AddressFields.ADDRESS_LINE_1, field);
		}
		field = inputAddress.getAddressLine2();
		if (field != null) {
			ab.setField(AddressFields.ADDRESS_LINE_2, field);
		}
		field = inputAddress.getCity();
		if (field != null) {
			ab.setField(AddressFields.CITY, field);
		}
		field = inputAddress.getState();
		if (field != null) {
			ab.setField(AddressFields.STATE, field);
		}
		field = inputAddress.getZip();
		if (field != null) {
			ab.setField(AddressFields.ZIP, field);
		}
	}

	/**
	 * Sets output fields for Centrus AddressBroker.
	 * 
	 * @param outputAddress
	 */
	private void setOutputFields(CentrusAddress outputAddress) {

		outputAddress.setFirmName(ab.getField(AddressFields.FIRM_NAME));
		outputAddress.setAddressLine1(ab.getField(AddressFields.ADDRESS_LINE_1));
		outputAddress.setAddressLine2(ab.getField(AddressFields.ADDRESS_LINE_2));
		outputAddress.setCity(ab.getField(AddressFields.CITY));
		outputAddress.setState(ab.getField(AddressFields.STATE));
		outputAddress.setZip(ab.getField(AddressFields.ZIP));
		outputAddress.setZip10(ab.getField(AddressFields.ZIP10));
		outputAddress.setCountyCode(ab.getField(AddressFields.COUNTY_CODE));
		outputAddress.setCountryName(ab.getField(AddressFields.COUNTRY_NAME));

		outputAddress.setHouseNumber(ab.getField(AddressFields.HOUSE_NUMBER));
		outputAddress.setPrefixDirection(ab.getField(AddressFields.PREFIX_DIRECTION));
		outputAddress.setStreetName(ab.getField(AddressFields.STREET_NAME));
		outputAddress.setStreetType(ab.getField(AddressFields.STREET_TYPE));
		outputAddress.setPostfixDirection(ab.getField(AddressFields.POSTFIX_DIRECTION));
		outputAddress.setUnitNumber(ab.getField(AddressFields.UNIT_NUMBER));
		outputAddress.setUnitType(ab.getField(AddressFields.UNIT_TYPE));
		outputAddress.setZip4(ab.getField(AddressFields.ZIP4));

		outputAddress.setLongitude(parseIntegerField(ab.getField(AddressFields.LONGITUDE), null));
		outputAddress.setLatitude(parseIntegerField(ab.getField(AddressFields.LATITUDE), null));
		outputAddress.setCensusBlockId(ab.getField(AddressFields.CENSUS_BLOCK_ID));
		outputAddress.setSegmentId(ab.getField(AddressFields.SEGMENT_ID));

		outputAddress.setDataType(ab.getField(AddressFields.DATA_TYPE));
		outputAddress.setLocationQualityCode(ab.getField(AddressFields.LOCATION_QUALITY_CODE));
		outputAddress.setMatchCode(ab.getField(AddressFields.MATCH_CODE));
		outputAddress.setScore(parseFloatField(ab.getField(AddressFields.SCORE), null));
	}

	/** Augments geodata result with match statistics.   @param result */
	private void addMatchStatistics(GeoDataResult result) {

		int numAddresses = result.getNumOutputAddresses();

		// Count matches of each type //
		int numErrors = 0, numZipMatches = 0, numAddressMatches = 0;
		for (int i = 0; i < numAddresses; i++) {
			CentrusAddress outputAddress = (CentrusAddress) result.getOutputAddresses()[i];
			if (outputAddress.getAddressLine1() == null || outputAddress.getAddressLine1().length() <= 0 ||
					outputAddress.getMatchCode() == null || outputAddress.getMatchCode().startsWith("E")) {

				if (outputAddress.getLongitude() == null || outputAddress.getLatitude() == null ||
						outputAddress.getZip10() == null || outputAddress.getZip10().length() <= 0) {
					numErrors++;
				}
				else {
					numZipMatches++;
				}
			}
		}
		numAddressMatches = numAddresses - numErrors - numZipMatches;

		// Set result type to the strongest match //
		if (numAddressMatches > 0) {
			result.setResultTypeAddressMatch();  // at least one address matched
		}
		else if (numZipMatches > 0) {
			result.setResultTypeZipMatch();  // at least one zip-only match encountered
		}
		else if (numErrors > 0) {
			result.setResultTypeError();  // only error(s) encountered
		}
		else {
			result.setResultTypeNone();  // no results returned
		}
		
		// Set match count class //
		if (numAddresses > 1) {
			result.setMatchCountClassMulti();
		}
		else if (numAddresses <= 0 || result.isResultTypeError()) {
			result.setMatchCountClassZero();
		}
		else {  // numAddresses == 1 && resultType in (Address, Zip)
			result.setMatchCountClassSingle();
		}
	}

	/**
	 * Parses an integer from a string field.
	 * 
	 * @param field
	 * @param defaultValue Value to return if parsing fails
	 * @return
	 */
	private Integer parseIntegerField(String field, Integer defaultValue) {
		Integer result = defaultValue;
		try {
			if (field != null && field.trim().length() > 0) {
				result = new Integer(Integer.parseInt(field.trim()));
			}
		}
		catch (NumberFormatException e) {}

		return result;
	}

	/**
	 * Parses a floating-point number from a string field.
	 * 
	 * @param field
	 * @param defaultValue Value to return if parsing fails
	 * @return
	 */
	private Float parseFloatField(String field, Float defaultValue) {
		Float result = defaultValue;
		try {
			if (field != null && field.trim().length() > 0) {
				result = new Float(Float.parseFloat(field.trim()));
			}
		}
		catch (NumberFormatException e) {}

		return result;
	}
}
