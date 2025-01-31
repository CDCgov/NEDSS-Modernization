package gov.cdc.nedss.locator.dt;
 /**
    * Name:	    TeleLocatorDT.java
    * Description:  This is the value object for holding locator attributes.
    * Copyright:	Copyright (c) 2001
    * Company: 	Computer Sciences Corporation
    * @version	1.0
    */

import gov.cdc.nedss.util.*;

import java.sql.Timestamp;


public class TeleLocatorDT
    extends AbstractVO {
	private static final long serialVersionUID = 1L;
    private Long teleLocatorUid;
    private String addReasonCd;
    private Timestamp addTime;
    private Long addUserId;
    private String cntryCd;
    private String emailAddress;
    private String extensionTxt;
    private String lastChgReasonCd;
    private Timestamp lastChgTime;
    private Long lastChgUserId;
    private String phoneNbrTxt;
    private String recordStatusCd;
    private Timestamp recordStatusTime;
    private String urlAddress;
    private String userAffiliationTxt;
    private String progAreaCd = null;
    private String jurisdictionCd = null;
    private Long programJurisdictionOid = null;
    private String sharedInd = null;
    private boolean itDirty = false;
    private boolean itNew = true;
    private boolean itDelete = false;

    public TeleLocatorDT() {
    }

    public TeleLocatorDT(Long teleLocatorUid, String addReasonCd, Timestamp addTime, Long addUserId, String cntryCd, String emailAddress, String extensionTxt, String lastChgReasonCd, Timestamp lastChgTime, Long lastChgUserId, String phoneNbrTxt, String recordStatusCd, Timestamp recordStatusTime, String urlAddress, String userAffiliationTxt) {
        this.teleLocatorUid = teleLocatorUid;
        this.addReasonCd = addReasonCd;
        this.addTime = addTime;
        this.addUserId = addUserId;
        this.cntryCd = cntryCd;
        this.emailAddress = emailAddress;
        this.extensionTxt = extensionTxt;
        this.lastChgReasonCd = lastChgReasonCd;
        this.lastChgTime = lastChgTime;
        this.lastChgUserId = lastChgUserId;
        this.phoneNbrTxt = phoneNbrTxt;
        this.recordStatusCd = recordStatusCd;
        this.recordStatusTime = recordStatusTime;
        this.urlAddress = urlAddress;
        this.userAffiliationTxt = userAffiliationTxt;
    }

    /**
     *
     * @return
     */
    public Long getTeleLocatorUid() {

        return teleLocatorUid;
    }

    /**
     *
     * @param aTeleLocatorUid
     */
    public void setTeleLocatorUid(Long aTeleLocatorUid) {
        teleLocatorUid = aTeleLocatorUid;
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
    public String getEmailAddress() {

        return emailAddress;
    }

    /**
     *
     * @param aEmailAddress
     */
    public void setEmailAddress(String aEmailAddress) {
        emailAddress = aEmailAddress;
        setItDirty(true);
    }

    /**
     *
     * @return
     */
    public String getExtensionTxt() {

        return extensionTxt;
    }

    /**
     *
     * @param aExtensionTxt
     */
    public void setExtensionTxt(String aExtensionTxt) {
        extensionTxt = aExtensionTxt;
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
    public String getPhoneNbrTxt() {

        return phoneNbrTxt;
    }

    /**
     *
     * @param aPhoneNbrTxt
     */
    public void setPhoneNbrTxt(String aPhoneNbrTxt) {
        phoneNbrTxt = aPhoneNbrTxt;
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
    public String getUrlAddress() {

        return urlAddress;
    }

    /**
     *
     * @param aUrlAddress
     */
    public void setUrlAddress(String aUrlAddress) {
        urlAddress = aUrlAddress;
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
     * @param aProgramJurisdictionOid Long
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
        voClass = ((TeleLocatorDT)objectname1).getClass();

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
}