package gov.cdc.nedss.geocoding.dt;

import gov.cdc.nedss.util.AbstractVO;
import gov.cdc.nedss.util.NedssUtils;

import java.sql.Timestamp;

public class GeoCodingResultDT extends AbstractVO {
	private static final long serialVersionUID = 1L;
	// Constants //
	public static final String RESULT_TYPE_NONE = "N";
	public static final String RESULT_TYPE_ADDRESS_MATCH = "A";
	public static final String RESULT_TYPE_ZIP_MATCH = "Z";
	public static final String RESULT_TYPE_ERROR = "E";

	// Members //
	private Long geocodingResultUid;
	private Long postalLocatorUid;
	private Timestamp addTime;
	private Long addUserId;
	private Timestamp lastChgTime;
	private Long lastChgUserId;

	private String firmName;
	private String streetAddr1;
	private String streetAddr2;
	private String city;
	private String state;
	private String zipCd;
	private String country;
	private String cntyCd;

	private String houseNumber;
	private String prefixDirection;
	private String streetName;
	private String streetType;
	private String postfixDirection;
	private String unitNumber;
	private String unitType;
	private String zip5Cd;
	private String zip4Cd;

	private Integer longitude;
	private Integer latitude;
	private String censusBlockId;

	private String segmentId;
	private String dataType;
	private String locationQualityCd;
	private String matchCd;
	private Float score;

	private String resultType;
	private Integer numMatches;
	private Float nextScoreDiff;
	private Integer nextScoreCount;

	private Long entityUid;
	private String entityClassCd;

	private String recordStatusCd;
	private Timestamp recordStatusTime;

	private boolean itDirty = false;
	private boolean itNew = false;
	private boolean itDelete = false;


	/** Default (empty) constructor. */
	public GeoCodingResultDT() {
	}

	/** Constructor for memberwise initialization. */
	public GeoCodingResultDT(Long geocodingResultUid, Long postalLocatorUid, Timestamp addTime,
			Long addUserId, Timestamp lastChgTime, Long lastChgUserId, String firmName,
			String streetAddr1, String streetAddr2, String city, String state, String zipCd,
			String country, String cntyCd, String houseNumber, String prefixDirection,
			String streetName, String streetType, String postfixDirection, String unitNumber,
			String unitType, String zip5Cd, String zip4Cd, Integer longitude, Integer latitude,
			String censusBlockId, String segmentId, String dataType,
			String locationQualityCd, String matchCd, Float score, String resultType,
			Integer numMatches, Float nextScoreDiff, Integer nextScoreCount, Long entityUid,
			String entityClassCd, String recordStatusCd, Timestamp recordStatusTime) {

		this.geocodingResultUid = geocodingResultUid;
		this.postalLocatorUid = postalLocatorUid;
		this.addTime = addTime;
		this.addUserId = addUserId;
		this.lastChgTime = lastChgTime;
		this.lastChgUserId = lastChgUserId;

		this.firmName = firmName;
		this.streetAddr1 = streetAddr1;
		this.streetAddr2 = streetAddr2;
		this.city = city;
		this.state = state;
		this.zipCd = zipCd;
		this.country = country;
		this.cntyCd = cntyCd;

		this.houseNumber = houseNumber;
		this.prefixDirection = prefixDirection;
		this.streetName = streetName;
		this.streetType = streetType;
		this.postfixDirection = postfixDirection;
		this.unitNumber = unitNumber;
		this.unitType = unitType;
		this.zip5Cd = zip5Cd;
		this.zip4Cd = zip4Cd;

		this.longitude = longitude;
		this.latitude = latitude;
		this.censusBlockId = censusBlockId;

		this.segmentId = segmentId;
		this.dataType = dataType;
		this.locationQualityCd = locationQualityCd;
		this.matchCd = matchCd;
		this.score = score;

		this.resultType = resultType;
		this.numMatches = numMatches;
		this.nextScoreDiff = nextScoreDiff;
		this.nextScoreCount = nextScoreCount;

		this.entityUid = entityUid;
		this.entityClassCd = entityClassCd;

		this.recordStatusCd = recordStatusCd;
		this.recordStatusTime = recordStatusTime;
	}

	public Long getGeocodingResultUid() {
		return geocodingResultUid;
	}

	public void setGeocodingResultUid(Long geocodingResultUid) {
		this.geocodingResultUid = geocodingResultUid;
		setItDirty(true);
	}

	public Long getPostalLocatorUid() {
		return postalLocatorUid;
	}

	public void setPostalLocatorUid(Long postalLocatorUid) {
		this.postalLocatorUid = postalLocatorUid;
		setItDirty(true);
	}

	public Timestamp getAddTime() {
		return addTime;
	}

	public void setAddTime(Timestamp addTime) {
		this.addTime = addTime;
		setItDirty(true);
	}

	public Long getAddUserId() {
		return addUserId;
	}

	public void setAddUserId(Long addUserId) {
		this.addUserId = addUserId;
		setItDirty(true);
	}

	public Timestamp getLastChgTime() {
		return lastChgTime;
	}

	public void setLastChgTime(Timestamp lastChgTime) {
		this.lastChgTime = lastChgTime;
		setItDirty(true);
	}

	public Long getLastChgUserId() {
		return lastChgUserId;
	}

	public void setLastChgUserId(Long lastChgUserId) {
		this.lastChgUserId = lastChgUserId;
		setItDirty(true);
	}


	public String getFirmName() {
		return firmName;
	}

	public void setFirmName(String firmName) {
		this.firmName = firmName;
		setItDirty(true);
	}

	public String getStreetAddr1() {
		return streetAddr1;
	}

	public void setStreetAddr1(String streetAddr1) {
		this.streetAddr1 = streetAddr1;
		setItDirty(true);
	}

	public String getStreetAddr2() {
		return streetAddr2;
	}

	public void setStreetAddr2(String streetAddr2) {
		this.streetAddr2 = streetAddr2;
		setItDirty(true);
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
		setItDirty(true);
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
		setItDirty(true);
	}

	public String getZipCd() {
		return zipCd;
	}

	public void setZipCd(String zipCd) {
		this.zipCd = zipCd;
		setItDirty(true);
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
		setItDirty(true);
	}

	public String getCntyCd() {
		return cntyCd;
	}

	public void setCntyCd(String cntyCd) {
		this.cntyCd = cntyCd;
		setItDirty(true);
	}



	public String getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
		setItDirty(true);
	}

	public String getPrefixDirection() {
		return prefixDirection;
	}

	public void setPrefixDirection(String prefixDirection) {
		this.prefixDirection = prefixDirection;
		setItDirty(true);
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
		setItDirty(true);
	}

	public String getStreetType() {
		return streetType;
	}

	public void setStreetType(String streetType) {
		this.streetType = streetType;
		setItDirty(true);
	}

	public String getPostfixDirection() {
		return postfixDirection;
	}

	public void setPostfixDirection(String postfixDirection) {
		this.postfixDirection = postfixDirection;
		setItDirty(true);
	}

	public String getUnitNumber() {
		return unitNumber;
	}

	public void setUnitNumber(String unitNumber) {
		this.unitNumber = unitNumber;
		setItDirty(true);
	}

	public String getUnitType() {
		return unitType;
	}

	public void setUnitType(String unitType) {
		this.unitType = unitType;
		setItDirty(true);
	}

	public String getZip5Cd() {
		return zip5Cd;
	}

	public void setZip5Cd(String zip5Cd) {
		this.zip5Cd = zip5Cd;
		setItDirty(true);
	}

	public String getZip4Cd() {
		return zip4Cd;
	}

	public void setZip4Cd(String zip4Cd) {
		this.zip4Cd = zip4Cd;
		setItDirty(true);
	}


	public Integer getLongitude() {
		return longitude;
	}

	public void setLongitude(Integer longitude) {
		this.longitude = longitude;
		setItDirty(true);
	}

	public Integer getLatitude() {
		return latitude;
	}

	public void setLatitude(Integer latitude) {
		this.latitude = latitude;
		setItDirty(true);
	}

	public String getCensusBlockId() {
		return censusBlockId;
	}

	public void setCensusBlockId(String censusBlockId) {
		this.censusBlockId = censusBlockId;
		setItDirty(true);
	}


	public String getSegmentId() {
		return segmentId;
	}

	public void setSegmentId(String segmentId) {
		this.segmentId = segmentId;
		setItDirty(true);
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
		setItDirty(true);
	}

	public String getMatchCd() {
		return matchCd;
	}

	public void setMatchCd(String matchCd) {
		this.matchCd = matchCd;
		setItDirty(true);
	}

	public String getLocationQualityCd() {
		return locationQualityCd;
	}

	public void setLocationQualityCd(String locationQualityCd) {
		this.locationQualityCd = locationQualityCd;
		setItDirty(true);
	}

	public Float getScore() {
		return score;
	}

	public void setScore(Float score) {
		this.score = score;
		setItDirty(true);
	}


	public String getResultType() {
		return resultType;
	}

	/** True iff result type is None. */
	public boolean isResultTypeNone() {
		return RESULT_TYPE_NONE.equals(resultType);
	}

	/** True iff result type is Address Match. */
	public boolean isResultTypeAddressMatch() {
		return RESULT_TYPE_ADDRESS_MATCH.equals(resultType);
	}

	/** True iff result type is Zip Match. */
	public boolean isResultTypeZipMatch() {
		return RESULT_TYPE_ZIP_MATCH.equals(resultType);
	}

	/** True iff result type is Error. */
	public boolean isResultTypeError() {
		return RESULT_TYPE_ERROR.equals(resultType);
	}


	public void setResultType(String resultType) {
		this.resultType = resultType;
		setItDirty(true);
	}

	/** Sets Result Type to None. */
	public void setResultTypeNone() {
		setResultType(RESULT_TYPE_NONE);
	}

	/** Sets Result Type to None. */
	public void setResultTypeAddressMatch() {
		setResultType(RESULT_TYPE_ADDRESS_MATCH);
	}

	/** Sets Result Type to None. */
	public void setResultTypeZipMatch() {
		setResultType(RESULT_TYPE_ZIP_MATCH);
	}

	/** Sets Result Type to None. */
	public void setResultTypeError() {
		setResultType(RESULT_TYPE_ERROR);
	}


	public Integer getNumMatches() {
		return numMatches;
	}

	public void setNumMatches(Integer numMatches) {
		this.numMatches = numMatches;
		setItDirty(true);
	}

	public Float getNextScoreDiff() {
		return nextScoreDiff;
	}

	public void setNextScoreDiff(Float nextScoreDiff) {
		this.nextScoreDiff = nextScoreDiff;
	}

	public Integer getNextScoreCount() {
		return nextScoreCount;
	}

	public void setNextScoreCount(Integer nextScoreCount) {
		this.nextScoreCount = nextScoreCount;
		setItDirty(true);
	}
	public Long getEntityUid() {
		return entityUid;
	}

	public void setEntityUid(Long entityUid) {
		this.entityUid = entityUid;
		setItDirty(true);
	}

	public String getEntityClassCd() {
		return entityClassCd;
	}

	public void setEntityClassCd(String entityClassCd) {
		this.entityClassCd = entityClassCd;
		setItDirty(true);
	}

	public String getRecordStatusCd() {
		return recordStatusCd;
	}

	public void setRecordStatusCd(String recordStatusCd) {
		this.recordStatusCd = recordStatusCd;
		setItDirty(true);
	}

	public Timestamp getRecordStatusTime() {
		return recordStatusTime;
	}

	public void setRecordStatusTime(Timestamp recordStatusTime) {
		this.recordStatusTime = recordStatusTime;
		setItDirty(true);
	}

	/**
	 * Implements the isEqual() method.
	 * 
	 * @param objectname1
	 * @param objectname2
	 * @param voClass
	 * @return
	 */
	public boolean isEqual(Object objectname1, Object objectname2, Class<?> voClass) {

		voClass = ((GeoCodingResultDT)objectname1).getClass();

		NedssUtils compareObjs = new NedssUtils();
		return (compareObjs.equals(objectname1, objectname2, voClass));
	}

	/** Checks the itDirty flag.  @return */
	public boolean isItDirty() {
		return itDirty;
	}

	/** Sets the itDirty flag.  @param itDirty */
	public void setItDirty(boolean itDirty) {
		this.itDirty = itDirty;
	}

	/** Checks the itNew flag.  @return */
	public boolean isItNew() {
		return itNew;
	}

	/** Sets the itNew flag.  @param itNew */
	public void setItNew(boolean itNew) {
		this.itNew = itNew;
	}

	/** Checks the itDelete flag.  @return */
	public boolean isItDelete() {
		return itDelete;
	}

	/** Sets the itDelete flag.  @param itDelete */
	public void setItDelete(boolean itDelete) {
		this.itDelete = itDelete;
	}

	/** Overrides the object's <code>toString()</code> method.  @return */
	public String toString() {
		StringBuffer sb = new StringBuffer();

		sb.append(this.idToString());
		sb.append("[addTime=").append(addTime).append("][addUserId=").append(addUserId);
		sb.append("][lastChgTime=").append(lastChgTime).append("][lastChgUserId=").append(lastChgUserId);
		sb.append("][firmName=").append(firmName).append("][streetAddr1=").append(streetAddr1);
		sb.append("][streetAddr2=").append(streetAddr2).append("][city=").append(city);
		sb.append("][state=").append(state).append("][zipCd=").append(zipCd).append("][country=").append(country);
		sb.append("][cntyCd=").append(cntyCd).append("][houseNumber=").append(houseNumber);
		sb.append("][prefixDirection=").append(prefixDirection).append("][streetName=").append(streetName);
		sb.append("][streetType=").append(streetType).append("][postfixDirection=").append(postfixDirection);
		sb.append("][unitNumber=").append(unitNumber).append("][unitType=").append(unitType);
		sb.append("][zip5Cd=").append(zip5Cd).append("][zip4Cd=").append(zip4Cd);
		sb.append("][longitude=").append(longitude).append("][latitude=").append(latitude);
		sb.append("][censusBlockId=").append(censusBlockId).append("][segmentId=").append(segmentId);
		sb.append("][dataType=").append(dataType).append("][locationQualityCd=").append(locationQualityCd);
		sb.append("][matchCd=").append(matchCd).append("][score=").append(score);
		sb.append("][resultType=").append(resultType).append("][numMatches=").append(numMatches);
		sb.append("][nextScoreDiff=").append(nextScoreDiff).append("][nextScoreCount=").append(nextScoreCount);
		sb.append("][entityUid=").append(entityUid).append("][entityClassCd=").append(entityClassCd);
		sb.append("][recordStatusCd=").append(recordStatusCd).append("][recordStatusTime=").append(recordStatusTime);
		sb.append("]  [Dirty=").append(itDirty).append("][New=").append(itNew).append("][Delete=").append(itDelete);
		sb.append("].");

		return sb.toString();
	}

	/** Returns a string representation of this object's identifier.  @return */
	public String idToString() {
		return "GeoCodingResultDT [GR UID=" + geocodingResultUid + "][PL UID=" + postalLocatorUid + "]";
	}
}
