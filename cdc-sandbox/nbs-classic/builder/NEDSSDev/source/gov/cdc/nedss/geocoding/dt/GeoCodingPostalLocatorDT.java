package gov.cdc.nedss.geocoding.dt;

import java.sql.Timestamp;

import gov.cdc.nedss.locator.dt.PostalLocatorDT;
import gov.cdc.nedss.util.NedssUtils;

/**
 * Geocoding-specific Postal Locator subclass that includes
 * fields required for geocoding.
 * 
 * @author bbannerm
 * @author rdodge
 *
 */
public class GeoCodingPostalLocatorDT extends PostalLocatorDT {

	private static final long serialVersionUID = 1L;
	private String statePostalCd;

	private Long entityUid;
	private String entityClassCd;

	/** Default constructor. */
	public GeoCodingPostalLocatorDT() {
		super();
	}

	/** Memberwise initialization constructor. */
	public GeoCodingPostalLocatorDT(Long postalLocatorUid, String addReasonCd, Timestamp addTime, Long addUserId, String censusBlockCd,
			String censusMinorCivilDivisionCd, String censusTrackCd, String cityCd, String cityDescTxt, String cntryCd,
			String cntryDescTxt, String cntyCd, String cntyDescTxt, String lastChgReasonCd, Timestamp lastChgTime, Long lastChgUserId,
			String MSACongressDistrictCd, String recordStatusCd, Timestamp recordStatusTime, String regionDistrictCd,
			String stateCd, String streetAddr1, String streetAddr2, String userAffiliationTxt, String zipCd, String statePostalCd,
			Long entityUid, String entityClassCd) {

    	super(postalLocatorUid, addReasonCd, addTime, addUserId, censusBlockCd,
				censusMinorCivilDivisionCd, censusTrackCd, cityCd, cityDescTxt,
				cntryCd, cntryDescTxt, cntyCd, cntyDescTxt, lastChgReasonCd,
				lastChgTime, lastChgUserId, MSACongressDistrictCd,
				recordStatusCd, recordStatusTime, regionDistrictCd, stateCd,
				streetAddr1, streetAddr2, userAffiliationTxt, zipCd);

    	this.statePostalCd = statePostalCd;

    	this.entityUid = entityUid;
		this.entityClassCd = entityClassCd;
	}


	/** Retrieves state two-letter postal code.  @return String */
	public String getStatePostalCd() {
		return statePostalCd;
	}

	/** Sets state two-letter postal code.  @param statePostalCd */
	public void setStatePostalCd(String aStatePostalCd) {
		statePostalCd = aStatePostalCd;
		setItDirty(true);
	}


	/** Retrieves Entity UID.  @return Long */
	public Long getEntityUid() {
		return entityUid;
	}

	/** Sets Entity UID.  @param aEntityUid */
	public void setEntityUid(Long aEntityUid) {
		entityUid = aEntityUid;
		setItDirty(true);
	}

	/** Retrieves Entity Class Code.  @return String */
	public String getEntityClassCd() {
		return entityClassCd;
	}

	/** Sets Entity Class Code.  @param aEntityClassCd */
	public void setEntityClassCd(String aEntityClassCd) {
		entityClassCd = aEntityClassCd;
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

		voClass = ((GeoCodingPostalLocatorDT) objectname1).getClass();

		NedssUtils compareObjs = new NedssUtils();
		return (compareObjs.equals(objectname1, objectname2, voClass));
	}
}
