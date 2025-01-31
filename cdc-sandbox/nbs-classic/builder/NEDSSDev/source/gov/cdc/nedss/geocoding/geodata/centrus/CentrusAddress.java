package gov.cdc.nedss.geocoding.geodata.centrus;

import gov.cdc.nedss.geocoding.geodata.DefaultGeoDataAddress;
import gov.cdc.nedss.geocoding.geodata.GeoDataInputAddress;
import gov.cdc.nedss.geocoding.geodata.GeoDataOutputAddress;

/**
 * Centrus geodata address.  Adds map getters/setters.
 * 
 * @author rdodge
 *
 */
public class CentrusAddress extends DefaultGeoDataAddress implements
		GeoDataInputAddress, GeoDataOutputAddress {

	// Interface Implementation //

	/**
	 * Sets all relevant fields for an input address.
	 * 
	 * @param addr1
	 * @param addr2
	 * @param city
	 * @param state
	 * @param zip
	 */
	public void setInputAddress(String addr1, String addr2,
			String city, String state, String zip) {

		this.setAddressLine1(addr1);
		this.setAddressLine2(addr2);
		this.setCity(city);
		this.setState(state);
		this.setZip(zip);
	}

	/**
	 * Sets all relevant fields for an output address.
	 * 
	 * @param addr1
	 * @param addr2
	 * @param city
	 * @param state
	 * @param zip
	 * 
	 * @param zip10
	 * @param countyCode
	 * @param longitude
	 * @param latitude
	 * @param censusBlockId
	 * 
	 * @param segmentId
	 * @param dataType
	 * @param locationQualityCode
	 * @param matchCode
	 * @param score
	 */
//	public void setOutputAddress(String addr1, String addr2,
//			String city, String state, String zip, String zip10,
//			String countyCode, Integer longitude, Integer latitude,
//			String censusBlockId, String segmentId, String dataType,
//			String locationQualityCode, String matchCode, Float score) {
//
//		this.setAddressLine1(addr1);
//		this.setAddressLine2(addr2);
//		this.setCity(city);
//		this.setState(state);
//		this.setZip(zip);
//
//		this.setZip10(zip10);
//		this.setCountyCode(countyCode);
//		this.setLongitude(longitude);
//		this.setLatitude(latitude);
//		this.setCensusBlockId(censusBlockId);
//
//		this.setSegmentId(segmentId);
//		this.setDataType(dataType);
//		this.setLocationQualityCode(locationQualityCode);
//		this.setMatchCode(matchCode);
//		this.setScore(score);
//	}

	/** Returns a string representation of the input address.  @return */
	public String inputToString() {
		return toString(true, false);
	}

	/** Returns a string representation of the output address.  @return */
	public String outputToString() {
		return toString(false, true);
	}


	// Accessors / Mutators //

	public String getFirmName() {
		return getString(AddressFields.FIRM_NAME);
	}
	public void setFirmName(String firmName) {
		setField(AddressFields.FIRM_NAME, firmName);
	}
	public String getAddressLine1() {
		return getString(AddressFields.ADDRESS_LINE_1);
	}
	public void setAddressLine1(String addressLine1) {
		setField(AddressFields.ADDRESS_LINE_1, addressLine1);
	}
	public String getAddressLine2() {
		return getString(AddressFields.ADDRESS_LINE_2);
	}
	public void setAddressLine2(String addressLine2) {
		setField(AddressFields.ADDRESS_LINE_2, addressLine2);
	}
	public String getCity() {
		return getString(AddressFields.CITY);
	}
	public void setCity(String city) {
		setField(AddressFields.CITY, city);
	}
	public String getState() {
		return getString(AddressFields.STATE);
	}
	public void setState(String state) {
		setField(AddressFields.STATE, state);
	}
	public String getZip() {
		return getString(AddressFields.ZIP);
	}
	public void setZip(String zip) {
		setField(AddressFields.ZIP, zip);
	}
	public String getZip10() {
		return getString(AddressFields.ZIP10);
	}
	public void setZip10(String zip10) {
		setField(AddressFields.ZIP10, zip10);
	}
	public String getCountyCode() {
		return getString(AddressFields.COUNTY_CODE);
	}
	public void setCountyCode(String countyCode) {
		setField(AddressFields.COUNTY_CODE, countyCode);
	}
	public String getCountyName() {
		return getString(AddressFields.COUNTY_NAME);
	}
	public void setCountyName(String countyName) {
		setField(AddressFields.COUNTY_NAME, countyName);
	}
	public String getCountryName() {
		return getString(AddressFields.COUNTRY_NAME);
	}
	public void setCountryName(String countryName) {
		setField(AddressFields.COUNTRY_NAME, countryName);
	}

	public String getHouseNumber() {
		return getString(AddressFields.HOUSE_NUMBER);
	}
	public void setHouseNumber(String houseNumber) {
		setField(AddressFields.HOUSE_NUMBER, houseNumber);
	}
	public String getPrefixDirection() {
		return getString(AddressFields.PREFIX_DIRECTION);
	}
	public void setPrefixDirection(String prefixDirection) {
		setField(AddressFields.PREFIX_DIRECTION, prefixDirection);
	}
	public String getStreetName() {
		return getString(AddressFields.STREET_NAME);
	}
	public void setStreetName(String streetName) {
		setField(AddressFields.STREET_NAME, streetName);
	}
	public String getStreetType() {
		return getString(AddressFields.STREET_TYPE);
	}
	public void setStreetType(String streetType) {
		setField(AddressFields.STREET_TYPE, streetType);
	}
	public String getPostfixDirection() {
		return getString(AddressFields.POSTFIX_DIRECTION);
	}
	public void setPostfixDirection(String postfixDirection) {
		setField(AddressFields.POSTFIX_DIRECTION, postfixDirection);
	}
	public String getUnitNumber() {
		return getString(AddressFields.UNIT_NUMBER);
	}
	public void setUnitNumber(String unitNumber) {
		setField(AddressFields.UNIT_NUMBER, unitNumber);
	}
	public String getUnitType() {
		return getString(AddressFields.UNIT_TYPE);
	}
	public void setUnitType(String unitType) {
		setField(AddressFields.UNIT_TYPE, unitType);
	}
	public String getZip4() {
		return getString(AddressFields.ZIP4);
	}
	public void setZip4(String zip4) {
		setField(AddressFields.ZIP4, zip4);
	}

	public Integer getLongitude() {
		return getInteger(AddressFields.LONGITUDE);
	}
	public void setLongitude(Integer longitude) {
		setField(AddressFields.LONGITUDE, longitude);
	}
	public Integer getLatitude() {
		return getInteger(AddressFields.LATITUDE);
	}
	public void setLatitude(Integer latitude) {
		setField(AddressFields.LATITUDE, latitude);
	}
	public String getCensusBlockId() {
		return getString(AddressFields.CENSUS_BLOCK_ID);
	}
	public void setCensusBlockId(String censusBlockId) {
		setField(AddressFields.CENSUS_BLOCK_ID, censusBlockId);
	}

	public String getSegmentId() {
		return getString(AddressFields.SEGMENT_ID);
	}
	public void setSegmentId(String segmentId) {
		setField(AddressFields.SEGMENT_ID, segmentId);
	}
	public String getDataType() {
		return getString(AddressFields.DATA_TYPE);
	}
	public void setDataType(String dataType) {
		setField(AddressFields.DATA_TYPE, dataType);
	}
	public String getLocationQualityCode() {
		return getString(AddressFields.LOCATION_QUALITY_CODE);
	}
	public void setLocationQualityCode(String locationQualityCode) {
		setField(AddressFields.LOCATION_QUALITY_CODE, locationQualityCode);
	}
	public String getMatchCode() {
		return getString(AddressFields.MATCH_CODE);
	}
	public void setMatchCode(String matchCode) {
		setField(AddressFields.MATCH_CODE, matchCode);
	}
	public Float getScore() {
		return getFloat(AddressFields.SCORE);
	}
	public void setScore(Float score) {
		setField(AddressFields.SCORE, score);
	}

	/** Override(s) of the <code>toString()</code> method. */
	public String toString() {
		return toString(true, true);
	}
	public String toString(boolean isInput, boolean isOutput) {

		StringBuffer sb = new StringBuffer();
		sb.append("CentrusAddress ");
		sb.append("[RecordID=").append(this.getField(AddressFields.RECORD_ID)).append("]");
		if (isOutput) {
			sb.append("[FirmName=").append(this.getFirmName()).append("]");
		}
		sb.append("[Addr1=").append(this.getAddressLine1());
		sb.append("][Addr2=").append(this.getAddressLine2());
		sb.append("][City=").append(this.getCity());
		sb.append("][State=").append(this.getState());
		sb.append("][Zip=").append(this.getZip()).append("]");
		if (isOutput) {
			sb.append("[Zip10=").append(this.getZip10());
			sb.append("][CountyCode=").append(this.getCountyCode());
			sb.append("][CountryName=").append(this.getCountryName());

			sb.append("][HouseNum=").append(this.getHouseNumber());
			sb.append("][PrefixDir=").append(this.getPrefixDirection());
			sb.append("][StreetName=").append(this.getStreetName());
			sb.append("][StreetType=").append(this.getStreetType());
			sb.append("][PostfixDir=").append(this.getPostfixDirection());
			sb.append("][UnitNum=").append(this.getUnitNumber());
			sb.append("][UnitType=").append(this.getUnitType());
			sb.append("][Zip4=").append(this.getZip4());

			sb.append("][Longitude=").append(this.getLongitude());
			sb.append("][Latitude=").append(this.getLatitude());
			sb.append("][CensusBlockID=").append(this.getCensusBlockId());
	
			sb.append("][SegmentID=").append(this.getSegmentId());
			sb.append("][DataType=").append(this.getDataType());
			sb.append("][LQCode=").append(this.getLocationQualityCode());
			sb.append("][MatchCode=").append(this.getMatchCode());
			sb.append("][Score=").append(this.getScore()).append("]");
		}

		// Exit //
		return sb.toString();
	}
}
