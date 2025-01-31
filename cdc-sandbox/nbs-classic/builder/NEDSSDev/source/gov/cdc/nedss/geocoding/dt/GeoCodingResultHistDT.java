package gov.cdc.nedss.geocoding.dt;

import java.sql.Timestamp;

import gov.cdc.nedss.util.NedssUtils;

public class GeoCodingResultHistDT extends GeoCodingResultDT {
	private static final long serialVersionUID = 1L;

	// Members //
	private Integer versionCtrlNbr;

	/** Default constructor. */
	public GeoCodingResultHistDT() {
		super();
	}

	/** Constructor for memberwise initialization. */
	public GeoCodingResultHistDT(Long geocodingResultUid, Long postalLocatorUid, Integer versionCtrlNbr,
			Timestamp addTime, Long addUserId, Timestamp lastChgTime, Long lastChgUserId, String firmName,
			String streetAddr1, String streetAddr2, String city, String state, String zipCd,
			String country, String cntyCd, String houseNumber, String prefixDirection,
			String streetName, String streetType, String postfixDirection, String unitNumber,
			String unitType, String zip5Cd, String zip4Cd, Integer longitude, Integer latitude,
			String censusBlockId, String segmentId, String dataType,
			String locationQualityCd, String matchCd, Float score, String resultType,
			Integer numMatches, Float nextScoreDiff, Integer nextScoreCount, Long entityUid,
			String entityClassCd, String recordStatusCd, Timestamp recordStatusTime) {

		// Call parent constructor //
		super(geocodingResultUid, postalLocatorUid, addTime, addUserId, lastChgTime,
				lastChgUserId, firmName, streetAddr1, streetAddr2, city, state, zipCd,
				country, cntyCd, houseNumber, prefixDirection, streetName, streetType,
				postfixDirection, unitNumber, unitType, zip5Cd, zip4Cd, longitude, latitude,
				censusBlockId, segmentId, dataType, locationQualityCd, matchCd, score,
				resultType, numMatches, nextScoreDiff, nextScoreCount, entityUid, entityClassCd,
				recordStatusCd, recordStatusTime);

		this.versionCtrlNbr = versionCtrlNbr;
	}

	public Integer getVersionCtrlNbr() {
		return versionCtrlNbr;
	}

	public void setVersionCtrlNbr(Integer versionCtrlNbr) {
		this.versionCtrlNbr = versionCtrlNbr;
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

		voClass = ((GeoCodingResultHistDT)objectname1).getClass();

		NedssUtils compareObjs = new NedssUtils();
		return (compareObjs.equals(objectname1, objectname2, voClass));
	}

	/** Returns a string representation of this object's identifier.  @return */
	public String idToString() {
		return "GeoCodingResultHistDT [GR UID=" + getGeocodingResultUid() + "][PL UID=" +
				getPostalLocatorUid() + "][versionCtrlNbr=" + getVersionCtrlNbr() + "]";
	}
}
