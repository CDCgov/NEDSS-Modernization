package gov.cdc.nedss.geocoding.geodata.centrus;

import gov.cdc.nedss.geocoding.dt.GeoCodingPostalLocatorDT;
import gov.cdc.nedss.geocoding.dt.GeoCodingResultDT;
import gov.cdc.nedss.geocoding.geodata.GeoDataAddressConverter;
import gov.cdc.nedss.geocoding.geodata.GeoDataInputAddress;
import gov.cdc.nedss.geocoding.geodata.GeoDataOutputAddress;

/**
 * Address Converter for batch processing with Centrus AddressBroker.
 * 
 * @author rdodge
 *
 */
public class CentrusBatchAddressConverter implements GeoDataAddressConverter {

	/** Returns a new input address instance.  @return */
	public GeoDataInputAddress newInputAddress() {
		return new CentrusAddress();
	}

	/** Returns a new output address instance.  @return */
	public GeoDataOutputAddress newOutputAddress() {
		return new CentrusAddress();
	}

	/**
	 * Converts an input address to geodata format.
	 * 
	 * @param inputAddress
	 * @return
	 */
	public GeoDataInputAddress convertInputToGeoData(Object inputAddress) {

		GeoCodingPostalLocatorDT sourceAddress = (GeoCodingPostalLocatorDT) inputAddress;
		GeoDataInputAddress targetAddress = new CentrusAddress();

		targetAddress.setInputAddress(sourceAddress.getStreetAddr1(),
				sourceAddress.getStreetAddr2(), sourceAddress.getCityDescTxt(),
				sourceAddress.getStatePostalCd(), sourceAddress.getZipCd());

		return targetAddress;
	}

	/**
	 * Converts a geodata address to output format.
	 * 
	 * @param geoDataAddress
	 * @return
	 */
	public Object convertGeoDataToOutput(GeoDataOutputAddress outputAddress) {

		CentrusAddress sourceAddress = (CentrusAddress) outputAddress;
		GeoCodingResultDT targetAddress = new GeoCodingResultDT(
				null, null, null, null, null, null, nullify(sourceAddress.getFirmName()),
				nullify(sourceAddress.getAddressLine1()), nullify(sourceAddress.getAddressLine2()),
				nullify(sourceAddress.getCity()), nullify(sourceAddress.getState()),
				nullify(sourceAddress.getZip10()), nullify(sourceAddress.getCountryName()),
				nullify(sourceAddress.getCountyCode()), nullify(sourceAddress.getHouseNumber()),
				nullify(sourceAddress.getPrefixDirection()), nullify(sourceAddress.getStreetName()),
				nullify(sourceAddress.getStreetType()), nullify(sourceAddress.getPostfixDirection()),
				nullify(sourceAddress.getUnitNumber()), nullify(sourceAddress.getUnitType()),
				nullify(sourceAddress.getZip()), nullify(sourceAddress.getZip4()),
				sourceAddress.getLongitude(), sourceAddress.getLatitude(),
				nullify(sourceAddress.getCensusBlockId()), nullify(sourceAddress.getSegmentId()),
				nullify(sourceAddress.getDataType()), nullify(sourceAddress.getLocationQualityCode()),
				nullify(sourceAddress.getMatchCode()), sourceAddress.getScore(),
				null, null, null, null, null, null, null, null);

		return targetAddress;
	}

	/**
	 * Converts blank or whitespace-only strings to <code>null</code>.
	 * 
	 * @param inputString
	 * @return
	 */
	private String nullify(String inputString) {
		return inputString == null || inputString.trim().length() <= 0 ? null : inputString;
	}
}
