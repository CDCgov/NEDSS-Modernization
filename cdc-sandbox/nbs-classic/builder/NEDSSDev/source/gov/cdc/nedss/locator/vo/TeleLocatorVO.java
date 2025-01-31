/**
 * Title:        NEDSS
 * Description: This is a value object for the TeleLocator.
 * Copyright:    Copyright (c) 2001
 * Company:      CSC
 * @author CSC EMPLOYEE
 * @version 1.0
 */
package gov.cdc.nedss.locator.vo;

import gov.cdc.nedss.util.*;

import java.io.Serializable;

import java.sql.Timestamp;


public class TeleLocatorVO
    implements Serializable,
               DirtyMarkerInterface {

    private Integer locatorUID; // Unique Locator
    private String addReasonCode; // Reason Code for addittion
    private Timestamp addTime; // Added on date and time
    private Integer addUserId; // UserID of the person who added// Reason for last change
    private Timestamp lastChngTime; // Last Change Date Time
    private Integer lastChngUserId; // Last Changes Made By User
    private String recordStatusCode; // Status of Record Active/Deleted ?
    private Timestamp recordStatusTime; // Status validitiy time
    private String userAffliationText; // Affliation of the user
    private String teleUseCode;
    private String teleTypeCode;
    private String teleTypeCodeDesc;
    private String countryCode;
    private String emailAddress;
    private String webAddress;
    private String phoneNumberText;
    private String extensionText;
    private String lastChngReasonCode;
    private String classCode;
    private String activeStatusCode;
    private Timestamp activeStatusCodeDateTime;
    private Timestamp fromDateTime;
    private Timestamp toDateTime;
    private String locatorDescTxt; // instruction and descriptions to locate the property
    private String locatorValidTimeTxt;
    private boolean itNew;
    private boolean itDirty;
    private boolean itDelete;
    private boolean equal; // eg. person is available between the following hours or days etc..

    public TeleLocatorVO(String classCode, Integer locatorUID, String addReasonCode, Timestamp addTime, Integer addUserId, String lastChngReasonCode, Timestamp lastChngTime, Integer lastChngUserId, String recordStatusCode, Timestamp recordStatusTime, String userAffliationText, String countryCode, String emailAddress, String webAddress, String phoneNumberText, String extensionText, String teleUseCode, String teleTypeCode, String teleTypeCodeDesc, String activeStatusCode, Timestamp activeStatusCodeDateTime, Timestamp fromDateTime, Timestamp toDateTime, String locatorDescTxt, String locatorValidTimeTxt) {
        this.classCode = classCode;
        this.locatorUID = locatorUID;
        this.addReasonCode = addReasonCode;
        this.addTime = addTime;
        this.addUserId = addUserId;
        this.countryCode = countryCode;
        this.lastChngReasonCode = lastChngReasonCode;
        this.lastChngTime = lastChngTime;
        this.lastChngUserId = lastChngUserId;
        this.recordStatusCode = recordStatusCode;
        this.recordStatusTime = recordStatusTime;
        this.userAffliationText = userAffliationText;
        this.teleUseCode = teleUseCode;
        this.teleTypeCode = teleTypeCode;
        this.teleTypeCodeDesc = teleTypeCodeDesc;
        this.emailAddress = emailAddress;
        this.extensionText = extensionText;
        this.webAddress = webAddress;
        this.phoneNumberText = phoneNumberText;
        this.activeStatusCode = activeStatusCode;
        this.activeStatusCodeDateTime = activeStatusCodeDateTime;
        this.fromDateTime = fromDateTime;
        this.toDateTime = toDateTime;
        this.locatorDescTxt = locatorDescTxt;
        this.locatorValidTimeTxt = locatorValidTimeTxt;
    }

    /**
     *
     * @return
     */
    public Integer getLocatorUID() {

        return locatorUID;
    }

    /**
     *
     * @param locatorUID
     */
    public void setLocatorUID(Integer locatorUID) {
        this.locatorUID = locatorUID;
    }

    /**
     *
     * @return
     */
    public String getAddReasonCode() {

        return addReasonCode;
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
     * @return
     */
    public Integer getAddUserId() {

        return addUserId;
    }

    /**
     *
     * @return
     */
    public String getCountryCode() {

        return countryCode;
    }

    /**
     *
     * @return
     */
    public Timestamp getLastChngTime() {

        return lastChngTime;
    }

    /**
     *
     * @return
     */
    public Integer getLastChngUserId() {

        return lastChngUserId;
    }

    /**
     *
     * @return
     */
    public String getRecordStatusCode() {

        return recordStatusCode;
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
     * @return
     */
    public String getUserAffliationText() {

        return userAffliationText;
    }

    /**
     *
     * @param addReasonCode
     */
    public void setAddReasonCode(String addReasonCode) {
        this.addReasonCode = addReasonCode;
    }

    /**
     *
     * @param addTime
     */
    public void setAddTime(Timestamp addTime) {
        this.addTime = addTime;
    }

    /**
     *
     * @param addUserId
     */
    public void setAddUserId(Integer addUserId) {
        this.addUserId = addUserId;
    }

    /**
     *
     * @param countryCode
     */
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    /**
     *
     * @param lastChngTime
     */
    public void setLastChngTime(Timestamp lastChngTime) {
        this.lastChngTime = lastChngTime;
    }

    /**
     *
     * @param lastChngUserId
     */
    public void setLastChngUserId(Integer lastChngUserId) {
        this.lastChngUserId = lastChngUserId;
    }

    /**
     *
     * @param recordStatusCode
     */
    public void setRecordStatusCode(String recordStatusCode) {
        this.recordStatusCode = recordStatusCode;
    }

    /**
     *
     * @param recordStatusTime
     */
    public void setRecordStatusTime(Timestamp recordStatusTime) {
        this.recordStatusTime = recordStatusTime;
    }

    /**
     *
     * @param userAffliationText
     */
    public void setUserAffliationText(String userAffliationText) {
        this.userAffliationText = userAffliationText;
    }

    /**
     *
     * @return
     */
    public String getTeleTypeCode() {

        return teleTypeCode;
    }

    /**
     *
     * @param teleTypeCode
     */
    public void setTeleTypeCode(String teleTypeCode) {
        this.teleTypeCode = teleTypeCode;
    }

    /**
     *
     * @return
     */
    public String getTeleTypeCodeDesc() {

        return teleTypeCodeDesc;
    }

    /**
     *
     * @param teleTypeCodeDesc
     */
    public void setTeleTypeCodeDesc(String teleTypeCodeDesc) {
        this.teleTypeCodeDesc = teleTypeCodeDesc;
    }

    /**
     *
     * @return
     */
    public String getTeleUseCode() {

        return teleUseCode;
    }

    /**
     *
     * @param teleUseCode
     */
    public void setTeleUseCode(String teleUseCode) {
        this.teleUseCode = teleUseCode;
    }

    /**
     *
     * @param emailAddress
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
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
     * @param webAddress
     */
    public void setWebAddress(String webAddress) {
        this.webAddress = webAddress;
    }

    /**
     *
     * @return
     */
    public String getWebAddress() {

        return webAddress;
    }

    /**
     *
     * @param phoneNumberText
     */
    public void setPhoneNumberText(String phoneNumberText) {
        this.phoneNumberText = phoneNumberText;
    }

    /**
     *
     * @return
     */
    public String getPhoneNumberText() {

        return phoneNumberText;
    }

    /**
     *
     * @param extensionText
     */
    public void setExtensionText(String extensionText) {
        this.extensionText = extensionText;
    }

    /**
     *
     * @return
     */
    public String getExtensionText() {

        return extensionText;
    }

    /**
     *
     * @param lastChngReasonCode
     */
    public void setLastChngReasonCode(String lastChngReasonCode) {
        this.lastChngReasonCode = lastChngReasonCode;
    }

    /**
     *
     * @return
     */
    public String getLastChngReasonCode() {

        return lastChngReasonCode;
    }

    /**
     *
     * @param classCode
     */
    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    /**
     *
     * @return
     */
    public String getClassCode() {

        return classCode;
    }

    /**
     *
     * @param activeStatusCode
     */
    public void setActiveStatusCode(String activeStatusCode) {
        this.activeStatusCode = activeStatusCode;
    }

    /**
     *
     * @return
     */
    public String getActiveStatusCode() {

        return activeStatusCode;
    }

    /**
     *
     * @param activeStatusCodeDateTime
     */
    public void setActiveStatusCodeDateTime(java.sql.Timestamp activeStatusCodeDateTime) {
        this.activeStatusCodeDateTime = activeStatusCodeDateTime;
    }

    /**
     *
     * @return
     */
    public java.sql.Timestamp getActiveStatusCodeDateTime() {

        return activeStatusCodeDateTime;
    }

    /**
     *
     * @param fromDateTime
     */
    public void setFromDateTime(java.sql.Timestamp fromDateTime) {
        this.fromDateTime = fromDateTime;
    }

    /**
     *
     * @return
     */
    public java.sql.Timestamp getFromDateTime() {

        return fromDateTime;
    }

    /**
     *
     * @param toDateTime
     */
    public void setToDateTime(java.sql.Timestamp toDateTime) {
        this.toDateTime = toDateTime;
    }

    /**
     *
     * @return
     */
    public java.sql.Timestamp getToDateTime() {

        return toDateTime;
    }

    /**
     *
     * @return
     */
    public String getLocatorDescTxt() {

        return locatorDescTxt;
    }

    /**
     *
     * @param locatorDescTxt
     */
    public void setLocatorDescTxt(String locatorDescTxt) {
        this.locatorDescTxt = locatorDescTxt;
    }

    /**
     *
     * @return
     */
    public String getLocatorValidTimeTxt() {

        return locatorValidTimeTxt;
    }

    /**
     *
     * @param locatorValidTimeTxt
     */
    public void setLocatorValidTimeTxt(String locatorValidTimeTxt) {
        this.locatorValidTimeTxt = locatorValidTimeTxt;
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
     *
     * @param TeleLocatorVO1
     * @param TeleLocatorVO2
     * @param TeleLocatorVOClass
     * @return
     */
    public boolean isEqual(Object TeleLocatorVO1, Object TeleLocatorVO2, Class TeleLocatorVOClass) {

        NedssUtils compareObj = new NedssUtils();

        return compareObj.equals(TeleLocatorVO1, TeleLocatorVO2, TeleLocatorVOClass);
    }
}