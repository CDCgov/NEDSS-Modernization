 /**
    * Name:	    PostalLocatorDT.java
    * Description:  This is the value object for holding locator attributes.
    * Copyright:	Copyright (c) 2001
    * Company: 	Computer Sciences Corporation
    * @version	1.0
    */
package gov.cdc.nedss.locator.dt;

import gov.cdc.nedss.util.*;

import java.sql.Timestamp;


public class PostalLocatorDT
    extends AbstractVO {
	private static final long serialVersionUID = 1L;
    private Long postalLocatorUid;
    private String addReasonCd;
    private Timestamp addTime;
    private Long addUserId;
    private String censusBlockCd;
    private String censusMinorCivilDivisionCd;
    private String censusTrackCd;
    private String cityCd;
    private String cityDescTxt;
    private String cntryCd;
    private String cntryDescTxt;
    private String cntyCd;
    private String cntyDescTxt;
    private String lastChgReasonCd;
    private Timestamp lastChgTime;
    private Long lastChgUserId;
    private String MSACongressDistrictCd;
    private String recordStatusCd;
    private Timestamp recordStatusTime;
    private String regionDistrictCd;
    private String stateCd;
    private String streetAddr1;
    private String streetAddr2;
    private String userAffiliationTxt;
    private String zipCd;
    private String geocodeMatchInd;
    private String withinCityLimitsInd;
    private String progAreaCd = null;
    private String jurisdictionCd = null;
    private Long programJurisdictionOid = null;
    private String sharedInd = null;
    private boolean itDirty = false;
    private boolean itNew = false;
    private boolean itDelete = false;
    private String censusTract;

    public PostalLocatorDT() {
    }

    public PostalLocatorDT(Long postalLocatorUid, String addReasonCd, Timestamp addTime, Long addUserId, String censusBlockCd, String censusMinorCivilDivisionCd, String censusTrackCd, String cityCd, String cityDescTxt, String cntryCd, String cntryDescTxt, String cntyCd, String cntyDescTxt, String lastChgReasonCd, Timestamp lastChgTime, Long lastChgUserId, String MSACongressDistrictCd, String recordStatusCd, Timestamp recordStatusTime, String regionDistrictCd, String stateCd, String streetAddr1, String streetAddr2, String userAffiliationTxt, String zipCd) {
        this.postalLocatorUid = postalLocatorUid;
        this.addReasonCd = addReasonCd;
        this.addTime = addTime;
        this.addUserId = addUserId;
        this.censusBlockCd = censusBlockCd;
        this.censusMinorCivilDivisionCd = censusMinorCivilDivisionCd;
        this.censusTrackCd = censusTrackCd;
        this.cityCd = cityCd;
        this.cityDescTxt = cityDescTxt;
        this.cntryCd = cntryCd;
        this.cntryDescTxt = cntryDescTxt;
        this.cntyCd = cntyCd;
        this.cntyDescTxt = cntyDescTxt;
        this.lastChgReasonCd = lastChgReasonCd;
        this.lastChgTime = lastChgTime;
        this.lastChgUserId = lastChgUserId;
        this.MSACongressDistrictCd = MSACongressDistrictCd;
        this.recordStatusCd = recordStatusCd;
        this.recordStatusTime = recordStatusTime;
        this.regionDistrictCd = regionDistrictCd;
        this.stateCd = stateCd;
        this.streetAddr1 = streetAddr1;
        this.streetAddr2 = streetAddr2;
        this.userAffiliationTxt = userAffiliationTxt;
        this.zipCd = zipCd;
        this.geocodeMatchInd = null;
        this.withinCityLimitsInd = null;
        this.censusTract=null;
    }

    /**
     *
     * @return
     */
    public Long getPostalLocatorUid() {

        return postalLocatorUid;
    }

    /**
     *
     * @param aPostalLocatorUid
     */
    public void setPostalLocatorUid(Long aPostalLocatorUid) {
        postalLocatorUid = aPostalLocatorUid;
        setItDirty(true);
    }

    /**
     *
     * @return
     */
    public String getAddReasonCd() {

        return addReasonCd;
    }

    /**
     *
     * @param aAddReasonCd
     */
    public void setAddReasonCd(String aAddReasonCd) {
        addReasonCd = aAddReasonCd;
        setItDirty(true);
    }

    /**
     *
     * @return
     */
    public Timestamp getAddTime() {

        return addTime;
    }

    /**
     *
     * @param aAddTime
     */
    public void setAddTime(Timestamp aAddTime) {
        addTime = aAddTime;
        setItDirty(true);
    }

    /**
     *
     * @param strTime
     */
    public void setAddTime_s(String strTime) {

        if (strTime == null)

            return;

        this.setAddTime(StringUtils.stringToStrutsTimestamp(strTime));
    }

    /**
     *
     * @return
     */
    public Long getAddUserId() {

        return addUserId;
    }

    /**
     *
     * @param aAddUserId
     */
    public void setAddUserId(Long aAddUserId) {
        addUserId = aAddUserId;
        setItDirty(true);
    }

    /**
     *
     * @return
     */
    public String getCensusBlockCd() {

        return censusBlockCd;
    }

    /**
     *
     * @param aCensusBlockCd
     */
    public void setCensusBlockCd(String aCensusBlockCd) {
        censusBlockCd = aCensusBlockCd;
        setItDirty(true);
    }

    /**
     *
     * @return
     */
    public String getCensusMinorCivilDivisionCd() {

        return censusMinorCivilDivisionCd;
    }

    /**
     *
     * @param aCensusMinorCivilDivisionCd
     */
    public void setCensusMinorCivilDivisionCd(String aCensusMinorCivilDivisionCd) {
        censusMinorCivilDivisionCd = aCensusMinorCivilDivisionCd;
        setItDirty(true);
    }

    /**
     *
     * @return
     */
    public String getCensusTrackCd() {

        return censusTrackCd;
    }

    /**
     *
     * @param aCensusTrackCd
     */
    public void setCensusTrackCd(String aCensusTrackCd) {
        censusTrackCd = aCensusTrackCd;
        setItDirty(true);
    }

    /**
     *
     * @return
     */
    public String getCityCd() {

        return cityCd;
    }

    /**
     *
     * @param aCityCd
     */
    public void setCityCd(String aCityCd) {
        cityCd = aCityCd;
        setItDirty(true);
    }

    /**
     *
     * @return
     */
    public String getCityDescTxt() {

        return cityDescTxt;
    }

    /**
     *
     * @param aCityDescTxt
     */
    public void setCityDescTxt(String aCityDescTxt) {
        cityDescTxt = aCityDescTxt;
        setItDirty(true);
    }

    /**
     *
     * @return
     */
    public String getCntryCd() {

        return cntryCd;
    }

    /**
     *
     * @param aCntryCd
     */
    public void setCntryCd(String aCntryCd) {
        cntryCd = aCntryCd;
        setItDirty(true);
    }

    /**
     *
     * @return
     */
    public String getCntryDescTxt() {

        return cntryDescTxt;
    }

    /**
     *
     * @param aCntryDescTxt
     */
    public void setCntryDescTxt(String aCntryDescTxt) {
        cntryDescTxt = aCntryDescTxt;
        setItDirty(true);
    }

    /**
     *
     * @return
     */
    public String getCntyCd() {

        return cntyCd;
    }

    /**
     *
     * @param aCntyCd
     */
    public void setCntyCd(String aCntyCd) {
        cntyCd = aCntyCd;
        setItDirty(true);
    }

    /**
     *
     * @return
     */
    public String getCntyDescTxt() {

        return cntyDescTxt;
    }

    /**
     *
     * @param aCntyDescTxt
     */
    public void setCntyDescTxt(String aCntyDescTxt) {
        cntyDescTxt = aCntyDescTxt;
        setItDirty(true);
    }

    /**
     *
     * @return
     */
    public String getLastChgReasonCd() {

        return lastChgReasonCd;
    }

    /**
     *
     * @param aLastChgReasonCd
     */
    public void setLastChgReasonCd(String aLastChgReasonCd) {
        lastChgReasonCd = aLastChgReasonCd;
        setItDirty(true);
    }

    /**
     *
     * @return
     */
    public Timestamp getLastChgTime() {

        return lastChgTime;
    }

    /**
     *
     * @param aLastChgTime
     */
    public void setLastChgTime(Timestamp aLastChgTime) {
        lastChgTime = aLastChgTime;
        setItDirty(true);
    }

    /**
     *
     * @param strTime
     */
    public void setLastChgTime_s(String strTime) {

        if (strTime == null)

            return;

        this.setLastChgTime(StringUtils.stringToStrutsTimestamp(strTime));
    }

    /**
     *
     * @return
     */
    public Long getLastChgUserId() {

        return lastChgUserId;
    }

    /**
     *
     * @param aLastChgUserId
     */
    public void setLastChgUserId(Long aLastChgUserId) {
        lastChgUserId = aLastChgUserId;
        setItDirty(true);
    }

    /**
     *
     * @return
     */
    public String getMSACongressDistrictCd() {

        return MSACongressDistrictCd;
    }

    /**
     *
     * @param aMSACongressDistrictCd
     */
    public void setMSACongressDistrictCd(String aMSACongressDistrictCd) {
        MSACongressDistrictCd = aMSACongressDistrictCd;
        setItDirty(true);
    }

    /**
     *
     * @return
     */
    public String getRecordStatusCd() {

        return recordStatusCd;
    }

    /**
     *
     * @param aRecordStatusCd
     */
    public void setRecordStatusCd(String aRecordStatusCd) {
        recordStatusCd = aRecordStatusCd;
        setItDirty(true);
    }

    /**
     *
     * @return
     */
    public Timestamp getRecordStatusTime() {

        return recordStatusTime;
    }

    /**
     *
     * @param aRecordStatusTime
     */
    public void setRecordStatusTime(Timestamp aRecordStatusTime) {
        recordStatusTime = aRecordStatusTime;
        setItDirty(true);
    }

    /**
     *
     * @param strTime
     */
    public void setRecordStatusTime_s(String strTime) {

        if (strTime == null)

            return;

        this.setRecordStatusTime(StringUtils.stringToStrutsTimestamp(strTime));
    }

    /**
     *
     * @return
     */
    public String getRegionDistrictCd() {

        return regionDistrictCd;
    }

    /**
     *
     * @param aRegionDistrictCd
     */
    public void setRegionDistrictCd(String aRegionDistrictCd) {
        regionDistrictCd = aRegionDistrictCd;
        setItDirty(true);
    }

    /**
     *
     * @return
     */
    public String getStateCd() {

        return stateCd;
    }

    /**
     *
     * @param aStateCd
     */
    public void setStateCd(String aStateCd) {
        stateCd = aStateCd;
        setItDirty(true);
    }

    /**
     *
     * @return
     */
    public String getStreetAddr1() {

        return streetAddr1;
    }

    /**
     *
     * @param aStreetAddr1
     */
    public void setStreetAddr1(String aStreetAddr1) {
        streetAddr1 = aStreetAddr1;
        setItDirty(true);
    }

    /**
     *
     * @return
     */
    public String getStreetAddr2() {

        return streetAddr2;
    }

    /**
     *
     * @param aStreetAddr2
     */
    public void setStreetAddr2(String aStreetAddr2) {
        streetAddr2 = aStreetAddr2;
        setItDirty(true);
    }

    /**
     *
     * @return
     */
    public String getUserAffiliationTxt() {

        return userAffiliationTxt;
    }

    /**
     *
     * @param aUserAffiliationTxt
     */
    public void setUserAffiliationTxt(String aUserAffiliationTxt) {
        userAffiliationTxt = aUserAffiliationTxt;
        setItDirty(true);
    }

    /**
     *
     * @return
     */
    public String getZipCd() {

        return zipCd;
    }

    /**
     *
     * @param aZipCd
     */
    public void setZipCd(String aZipCd) {
        zipCd = aZipCd;
        setItDirty(true);
    }

    /**
    *
    * @return
    */
   public String getGeocodeMatchInd() {

       return geocodeMatchInd;
   }

   /**
   *
   * @param aGeocodeMatchInd
   */
  public void setGeocodeMatchInd(String aGeocodeMatchInd) {
  	geocodeMatchInd = aGeocodeMatchInd;
      setItDirty(true);
  }

   /**
    *
    * @param aWithinCityLimitsInd
    */
   public void setWithinCityLimitsInd(String aWithinCityLimitsInd) {
	   withinCityLimitsInd = aWithinCityLimitsInd;
       setItDirty(true);
   }
    
   /**
   *
   * @return
   */
  public String getWithinCityLimitsInd() {

      return withinCityLimitsInd;
  }

    /**
     *
     * @return
     */
    public String getProgAreaCd() {

        return progAreaCd;
    }

    /**
     *
     * @param aProgAreaCd
     */
    public void setProgAreaCd(String aProgAreaCd) {
        progAreaCd = aProgAreaCd;
        setItDirty(true);
    }

    /**
     *
     * @return
     */
    public String getJurisdictionCd() {

        return jurisdictionCd;
    }

    /**
     *
     * @param aJurisdictionCd
     */
    public void setJurisdictionCd(String aJurisdictionCd) {
        jurisdictionCd = aJurisdictionCd;
        setItDirty(true);
    }

    /**
     *
     * @return Long
     */
    public Long getProgramJurisdictionOid() {

        return programJurisdictionOid;
    }

    /**
     *
     * @param aProgramJurisdictionOid
     */
    public void setProgramJurisdictionOid(Long aProgramJurisdictionOid) {
        programJurisdictionOid = aProgramJurisdictionOid;
        setItDirty(true);
    }

    /**
     *
     * @return
     */
    public String getSharedInd() {

        return sharedInd;
    }

    /**
     *
     * @param aSharedInd
     */
    public void setSharedInd(String aSharedInd) {
        sharedInd = aSharedInd;
        setItDirty(true);
    }

    /**
     *
     * @param objectname1
     * @param objectname2
     * @param voClass
     * @return
     */
    public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass) {
        voClass = ((PostalLocatorDT)objectname1).getClass();

        NedssUtils compareObjs = new NedssUtils();

        return (compareObjs.equals(objectname1, objectname2, voClass));
    }

    /**
     *
     * @param itDirty
     */
    public void setItDirty(boolean itDirty) {
        this.itDirty = itDirty;
    }

    /**
     *
     * @return
     */
    public boolean isItDirty() {

        return itDirty;
    }

    /**
     *
     * @param itNew
     */
    public void setItNew(boolean itNew) {
        this.itNew = itNew;
    }

    /**
     *
     * @return
     */
    public boolean isItNew() {

        return itNew;
    }

    /**
     *
     * @return
     */
    public boolean isItDelete() {

        return itDelete;
    }

    /**
     *
     * @param itDelete
     */
    public void setItDelete(boolean itDelete) {
        this.itDelete = itDelete;
    }

	/**
	 * @return the censusTract
	 */
	public String getCensusTract() {
		return censusTract;
	}

	/**
	 * @param censusTract the censusTract to set
	 */
	public void setCensusTract(String censusTract) {
		this.censusTract = censusTract;
	}
}