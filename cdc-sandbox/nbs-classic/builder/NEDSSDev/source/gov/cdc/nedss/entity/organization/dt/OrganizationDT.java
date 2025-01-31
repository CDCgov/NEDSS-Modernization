/**
* Name:		    OrganizationDT
* Description:	Organization data table object
* Copyright:	Copyright (c) 2002
* Company: 	    Computer Sciences Corporation
* @author	    NEDSS Development Team
* @version	    1.0
*/

package gov.cdc.nedss.entity.organization.dt;

import gov.cdc.nedss.systemservice.util.*;
import gov.cdc.nedss.util.*;

import java.sql.Timestamp;


public class OrganizationDT
    extends AbstractVO
    implements RootDTInterface {
	private static final long serialVersionUID = 1L;
    private Long organizationUid;
    private String addReasonCd;
    private Timestamp addTime;
    private Long addUserId;
    private String cd;
    private String cdDescTxt;
    private String description;
    private String durationAmt;
    private String durationUnitCd;
    private Timestamp fromTime;
    private String lastChgReasonCd;
    private Timestamp lastChgTime;
    private Long lastChgUserId;
    private String localId;
    private String recordStatusCd;
    private Timestamp recordStatusTime;
    private String standardIndustryClassCd;
    private String standardIndustryDescTxt;
    private String statusCd;
    private Timestamp statusTime;
    private Timestamp toTime;
    private String userAffiliationTxt;
    private String displayNm;
    private String streetAddr1;
    private String streetAddr2;
    private String cityCd;
    private String cityDescTxt;
    private String stateCd;
    private String cntyCd;
    private String cntryCd;
    private String zipCd;
    private String phoneNbr;
    private String phoneCntryCd;
    private String electronicInd;
    private Integer versionCtrlNbr;
    private String progAreaCd = null;
    private String jurisdictionCd = null;
    private Long programJurisdictionOid = null;
    private String sharedInd = null;
    private boolean itDirty = false;
    private boolean itNew = true;
    private boolean itDelete = false;
    private String edxInd = null;

    /**
     *
     * @return  Long
     */
    public Long getOrganizationUid() {

        return organizationUid;
    }

    /**
     *
     * @param aOrganizationUid
     */
    public void setOrganizationUid(Long aOrganizationUid) {
        organizationUid = aOrganizationUid;
        setItDirty(true);
    }

    /**
     *
     * @return  String
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
     * @return  Timestamp
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
     * @return  Long
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
     * @return  String
     */
    public String getCd() {

        return cd;
    }

    /**
     *
     * @param aCd
     */
    public void setCd(String aCd) {
        cd = aCd;
        setItDirty(true);
    }

    /**
     *
     * @return  String
     */
    public String getCdDescTxt() {

        return cdDescTxt;
    }

    /**
     *
     * @param aCdDescTxt
     */
    public void setCdDescTxt(String aCdDescTxt) {
        cdDescTxt = aCdDescTxt;
        setItDirty(true);
    }

    /**
     *
     * @return  String
     */
    public String getDescription() {

        return description;
    }

    /**
     *
     * @param aDescription
     */
    public void setDescription(String aDescription) {
        description = aDescription;
        setItDirty(true);
    }

    /**
     *
     * @return  String
     */
    public String getDurationAmt() {

        return durationAmt;
    }

    /**
     *
     * @param aDurationAmt
     */
    public void setDurationAmt(String aDurationAmt) {
        durationAmt = aDurationAmt;
        setItDirty(true);
    }

    /**
     *
     * @return  String
     */
    public String getDurationUnitCd() {

        return durationUnitCd;
    }

    /**
     *
     * @param aDurationUnitCd
     */
    public void setDurationUnitCd(String aDurationUnitCd) {
        durationUnitCd = aDurationUnitCd;
        setItDirty(true);
    }

    /**
     *
     * @return  String
     */
    public Timestamp getFromTime() {

        return fromTime;
    }

    /**
     *
     * @param aFromTime
     */
    public void setFromTime(Timestamp aFromTime) {
        fromTime = aFromTime;
        setItDirty(true);
    }

    /**
     *
     * @param strTime
     */
    public void setFromTime_s(String strTime) {

        if (strTime == null)

            return;

        this.setFromTime(StringUtils.stringToStrutsTimestamp(strTime));
    }

    /**
     *
     * @return  String
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
     * @return  Timestamp
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
     * @return  Long
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
     * @return  String
     */
    public String getLocalId() {

        return localId;
    }

    /**
     *
     * @param aLocalId
     */
    public void setLocalId(String aLocalId) {
        localId = aLocalId;
        setItDirty(true);
    }

    /**
     *
     * @return  String
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
     * @return  Timestamp
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
     * @return  String
     */
    public String getStandardIndustryClassCd() {

        return standardIndustryClassCd;
    }

    /**
     *
     * @param aStandardIndustryClassCd
     */
    public void setStandardIndustryClassCd(String aStandardIndustryClassCd) {
        standardIndustryClassCd = aStandardIndustryClassCd;
        setItDirty(true);
    }

    /**
     *
     * @return  String
     */
    public String getStandardIndustryDescTxt() {

        return standardIndustryDescTxt;
    }

    /**
     *
     * @param aStandardIndustryDescTxt
     */
    public void setStandardIndustryDescTxt(String aStandardIndustryDescTxt) {
        standardIndustryDescTxt = aStandardIndustryDescTxt;
        setItDirty(true);
    }

    /**
     *
     * @return  String
     */
    public String getStatusCd() {

        return statusCd;
    }

    /**
     *
     * @param aStatusCd
     */
    public void setStatusCd(String aStatusCd) {
        statusCd = aStatusCd;
        setItDirty(true);
    }

    /**
     *
     * @return  Timestamp
     */
    public Timestamp getStatusTime() {

        return statusTime;
    }

    /**
     *
     * @param aStatusTime
     */
    public void setStatusTime(Timestamp aStatusTime) {
        statusTime = aStatusTime;
        setItDirty(true);
    }

    /**
     *
     * @param strTime
     */
    public void setStatusTime_s(String strTime) {

        if (strTime == null)

            return;

        this.setStatusTime(StringUtils.stringToStrutsTimestamp(strTime));
    }

    /**
     *
     * @return  Timestamp
     */
    public Timestamp getToTime() {

        return toTime;
    }

    /**
     *
     * @param aToTime
     */
    public void setToTime(Timestamp aToTime) {
        toTime = aToTime;
        setItDirty(true);
    }

    /**
     *
     * @param strTime
     */
    public void setToTime_s(String strTime) {

        if (strTime == null)

            return;

        this.setToTime(StringUtils.stringToStrutsTimestamp(strTime));
    }

    /**
     *
     * @return  String
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
     * @return  String
     */
    public String getDisplayNm() {

        return displayNm;
    }

    /**
     *
     * @param aDisplayNm
     */
    public void setDisplayNm(String aDisplayNm) {
        displayNm = aDisplayNm;
        setItDirty(true);
    }

    /**
     *
     * @return  String
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
     * @return  String
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
     * @return  String
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
     * @return  String
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
     * @return  String
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
     * @return  String
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
     * @return  String
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
     * @return  String
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
     * @return  String
     */
    public String getPhoneNbr() {

        return phoneNbr;
    }

    /**
     *
     * @param aPhoneNbr
     */
    public void setPhoneNbr(String aPhoneNbr) {
        phoneNbr = aPhoneNbr;
        setItDirty(true);
    }

    /**
     *
     * @return  String
     */
    public String getPhoneCntryCd() {

        return phoneCntryCd;
    }

    /**
     *
     * @param aPhoneCntryCd
     */
    public void setPhoneCntryCd(String aPhoneCntryCd) {
        phoneCntryCd = aPhoneCntryCd;
        setItDirty(true);
    }

    /**
     *
     * @return  Integer
     */
    public Integer getVersionCtrlNbr() {

        return versionCtrlNbr;
    }

    /**
     *
     * @param aVersionCtrlNbr
     */
    public void setVersionCtrlNbr(Integer aVersionCtrlNbr) {
        versionCtrlNbr = aVersionCtrlNbr;
        setItDirty(true);
    }

    /**
     *
     * @return  String
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
     * @return  String
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
     * @return  Long
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
     * @return  String
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
     * @return  boolean
     */
    public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass) {
        voClass = ((OrganizationDT)objectname1).getClass();

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
     * @return  boolean
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
     * @return  boolean
     */
    public boolean isItNew() {

        return itNew;
    }

    /**
     *
     * @return  boolean
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
     *
     * @return  Long
     */
    public Long getUid() {

        return organizationUid;
    }

    /**
     *
     * @return  String
     */
    public String getSuperclass() {

        return NEDSSConstants.CLASSTYPE_ENTITY;
    }
    /**
     *
     * @return  String
     */

  public String getElectronicInd() {
    return electronicInd;
  }
  /**
   *
   * @param electronicInd   the String
   */
  public void setElectronicInd(String electronicInd) {
    this.electronicInd = electronicInd;
  }

public String getEdxInd() {
	return edxInd;
}

public void setEdxInd(String edxInd) {
	this.edxInd = edxInd;
}
  
}