package gov.cdc.nedss.entity.material.dt;

import gov.cdc.nedss.systemservice.util.*;
import gov.cdc.nedss.util.*;

import java.sql.Timestamp;

/**
* Name:		    MaterialDT
* Description:	Material data table object
* Copyright:	Copyright (c) 2002
* Company: 	    Computer Sciences Corporation
* @author	    NEDSS Development Team
* @version	    1.0
*/

public class MaterialDT
    extends AbstractVO
    implements RootDTInterface {
	private static final long serialVersionUID = 1L;

    private Long materialUid;
    private String addReasonCd;
    private Timestamp addTime;
    private Long addUserId;
    private String cd;
    private String cdDescTxt;
    private String description;
    private String effectiveDurationAmt;
    private String effectiveDurationUnitCd;
    private Timestamp effectiveFromTime;
    private Timestamp effectiveToTime;
    private String formCd;
    private String formDescTxt;
    private String handlingCd;
    private String handlingDescTxt;
    private String lastChgReasonCd;
    private Timestamp lastChgTime;
    private Long lastChgUserId;
    private String localId;
    private String nm;
    private String qty;
    private String qtyUnitCd;
    private String recordStatusCd;
    private Timestamp recordStatusTime;
    private String riskCd;
    private String riskDescTxt;
    private String statusCd;
    private Timestamp statusTime;
    private String userAffiliationTxt;
    private Integer versionCtrlNbr;
    private String progAreaCd = null;
    private String jurisdictionCd = null;
    private Long programJurisdictionOid = null;
    private String sharedInd = null;
    private boolean itDirty = false;
    private boolean itNew = true;
    private boolean itDelete = false;

    public MaterialDT() {
    }

    public MaterialDT(Long materialUid, String addReasonCd, Timestamp addTime, Long addUserId, String cd, String cdDescTxt, String description, String effectiveDurationAmt, String effectiveDurationUnitCd, Timestamp effectiveFromTime, Timestamp effectiveToTime, String formCd, String formDescTxt, String handlingCd, String handlingDescTxt, String lastChgReasonCd, Timestamp lastChgTime, Long lastChgUserId, String localId, String nm, String qty, String qtyUnitCd, String recordStatusCd, Timestamp recordStatusTime, String riskCd, String riskDescTxt, String statusCd, Timestamp statusTime, String userAffiliationTxt, Integer versionCtrlNbr) {
        this.materialUid = materialUid;
        this.addReasonCd = addReasonCd;
        this.addTime = addTime;
        this.addUserId = addUserId;
        this.cd = cd;
        this.cdDescTxt = cdDescTxt;
        this.description = description;
        this.effectiveDurationAmt = effectiveDurationAmt;
        this.effectiveDurationUnitCd = effectiveDurationUnitCd;
        this.effectiveFromTime = effectiveFromTime;
        this.effectiveToTime = effectiveToTime;
        this.formCd = formCd;
        this.formDescTxt = formDescTxt;
        this.handlingCd = handlingCd;
        this.handlingDescTxt = handlingDescTxt;
        this.lastChgReasonCd = lastChgReasonCd;
        this.lastChgTime = lastChgTime;
        this.lastChgUserId = lastChgUserId;
        this.localId = localId;
        this.nm = nm;
        this.qty = qty;
        this.qtyUnitCd = qtyUnitCd;
        this.recordStatusCd = recordStatusCd;
        this.recordStatusTime = recordStatusTime;
        this.riskCd = riskCd;
        this.riskDescTxt = riskDescTxt;
        this.statusCd = statusCd;
        this.statusTime = statusTime;
        this.userAffiliationTxt = userAffiliationTxt;
        this.versionCtrlNbr = versionCtrlNbr;
    }

    /**
     * Access method for materialUid
     * @return  Long materialUid
     */
    public Long getMaterialUid() {

        return materialUid;
    }

    /**
     *
     * Sets the value of materialUid
     * @param Long materialUid
     */
    public void setMaterialUid(Long aMaterialUid) {
        materialUid = aMaterialUid;
        setItDirty(true);
    }

    /**
     * Access method for AddReasonCd
     * @return  String addReasonCd
     */
    public String getAddReasonCd() {

        return addReasonCd;
    }

    /**
     * Sets the value of AddReasonCd
     * @param String addReasonCd
     */
    public void setAddReasonCd(String aAddReasonCd) {
        addReasonCd = aAddReasonCd;
        setItDirty(true);
    }

    /**
     * Access method for addTime
     * @return  Timestamp addTime
     */
    public Timestamp getAddTime() {

        return addTime;
    }

    /**
     * Sets the value of AddTime
     * @param Timestamp addTime
     */
    public void setAddTime(Timestamp aAddTime) {
        addTime = aAddTime;
        setItDirty(true);
    }

    /**
     * Sets the value of AddTime_s
     * takes String and converts to Timestamp
     * @param String strTime
     */
    public void setAddTime_s(String strTime) {

        if (strTime == null)

            return;

        this.setAddTime(StringUtils.stringToStrutsTimestamp(strTime));
    }

    /**
     * Access method for addUserId
     * @return Long addUserId
     */
    public Long getAddUserId() {

        return addUserId;
    }

    /**
     * Sets the value of AddUserId
     * @param Long addUserId
     */
    public void setAddUserId(Long aAddUserId) {
        addUserId = aAddUserId;
        setItDirty(true);
    }

    /**
     * Access method for Cd
     * @return  String cd
     */
    public String getCd() {

        return cd;
    }

    /**
     * Sets the value of cd
     * @param String cd
     */
    public void setCd(String aCd) {
        cd = aCd;
        setItDirty(true);
    }

    /**
     * Access method for cdDescTxt
     * @return  String cdDescTxt
     */
    public String getCdDescTxt() {

        return cdDescTxt;
    }

    /**
     * Sets the value of CdDescTxt
     * @param String cdDescTxt
     */
    public void setCdDescTxt(String aCdDescTxt) {
        cdDescTxt = aCdDescTxt;
        setItDirty(true);
    }

    /**
     * Access method for description
     * @return  String description
     */
    public String getDescription() {

        return description;
    }

    /**
     * Sets the value of description
     * @param String description
     */
    public void setDescription(String aDescription) {
        description = aDescription;
        setItDirty(true);
    }

    /**
     * Access method for effectiveDurationAmt
     * @return String effectiveDurationAmt
     */
    public String getEffectiveDurationAmt() {

        return effectiveDurationAmt;
    }

    /**
     * Sets the value of effectiveDurationAmt
     * @param String effectiveDurationAmt
     */
    public void setEffectiveDurationAmt(String aEffectiveDurationAmt) {
        effectiveDurationAmt = aEffectiveDurationAmt;
        setItDirty(true);
    }

    /**
     * Access method for effectiveDurationUnitCd
     * @return String effectiveDurationUnitCd
     */
    public String getEffectiveDurationUnitCd() {

        return effectiveDurationUnitCd;
    }

    /**
     * Sets the value of effectiveDurationUnitCd
     * @param String effectiveDurationUnitCd
     */
    public void setEffectiveDurationUnitCd(String aEffectiveDurationUnitCd) {
        effectiveDurationUnitCd = aEffectiveDurationUnitCd;
        setItDirty(true);
    }

    /**
     * Access method for effectiveFromTime
     * @return Timestamp effectiveFromTime
     */
    public Timestamp getEffectiveFromTime() {

        return effectiveFromTime;
    }

    /**
     * Sets the value of effectiveFromTime
     * @param Timestamp effectiveFromTime
     */
    public void setEffectiveFromTime(Timestamp aEffectiveFromTime) {
        effectiveFromTime = aEffectiveFromTime;
        setItDirty(true);
    }

    /**
     * Sets the value of effectiveFromTime_s
     * takes string adn converts it to a Timestamp
     * @param String strTime
     */
    public void setEffectiveFromTime_s(String strTime) {

        if (strTime == null)

            return;

        this.setEffectiveFromTime(StringUtils.stringToStrutsTimestamp(strTime));
    }

    /**
     * Access method for effectiveToTime
     * @return Timestamp effectiveToTime
     */
    public Timestamp getEffectiveToTime() {

        return effectiveToTime;
    }

    /**
     * Sets the value of effectiveToTime
     * @param Timestamp effectiveToTime
     */
    public void setEffectiveToTime(Timestamp aEffectiveToTime) {
        effectiveToTime = aEffectiveToTime;
        setItDirty(true);
    }

    /**
     * Sets the value of effectiveToTime
     * takes a String and converts to Timestamp
     * @param String strTime
     */
    public void setEffectiveToTime_s(String strTime) {

        if (strTime == null)

            return;

        this.setEffectiveToTime(StringUtils.stringToStrutsTimestamp(strTime));
    }

    /**
     * Access method for FormCd
     * @return String formCd
     */
    public String getFormCd() {

        return formCd;
    }

    /**
     * Sets the value of formCd
     * @param String formCd
     */
    public void setFormCd(String aFormCd) {
        formCd = aFormCd;
        setItDirty(true);
    }

    /**
     * Access method for FormDescTxt
     * @return String formDescTxt
     */
    public String getFormDescTxt() {

        return formDescTxt;
    }

    /**
     * Sets the value of formDescTxt
     * @param String formDescTxt
     */
    public void setFormDescTxt(String aFormDescTxt) {
        formDescTxt = aFormDescTxt;
        setItDirty(true);
    }

    /**
     * Access method for handlingCd
     * @return String handlingCd
     */
    public String getHandlingCd() {

        return handlingCd;
    }

    /**
     * Sets the value of HandlingCd
     * @param String handlingCd
     */
    public void setHandlingCd(String aHandlingCd) {
        handlingCd = aHandlingCd;
        setItDirty(true);
    }

    /**
     * Access method for handlingDescTxt
     * @return String handlingDescTxt
     */
    public String getHandlingDescTxt() {

        return handlingDescTxt;
    }

    /**
     * Sets the value of handlingDescTxt
     * @param String handlingDescTxt
     */
    public void setHandlingDescTxt(String aHandlingDescTxt) {
        handlingDescTxt = aHandlingDescTxt;
        setItDirty(true);
    }

    /**
     * Access method for lastChgReasonCd
     * @return String lastChgReasonCd
     */
    public String getLastChgReasonCd() {

        return lastChgReasonCd;
    }

    /**
     * Sets the value of LastChgReasonCd
     * @param String lastChgReasonCd
     */
    public void setLastChgReasonCd(String aLastChgReasonCd) {
        lastChgReasonCd = aLastChgReasonCd;
        setItDirty(true);
    }

    /**
     * Access method for LastChgTime
     * @return Timestamp lastChgTime
     */
    public Timestamp getLastChgTime() {

        return lastChgTime;
    }

    /**
     * Sets the value of lastChgTime
     * @param Timestamp lastChgTime
     */
    public void setLastChgTime(Timestamp aLastChgTime) {
        lastChgTime = aLastChgTime;
        setItDirty(true);
    }

    /**
     * Sets the value of lastChgTime
     * takes a String and converts to Timestamp
     * @param String strTime
     */
    public void setLastChgTime_s(String strTime) {

        if (strTime == null)
            return;

        this.setLastChgTime(StringUtils.stringToStrutsTimestamp(strTime));
    }

    /**
     * Access method for lastChgUserId
     * @return Long lastChgUserId
     */
    public Long getLastChgUserId() {

        return lastChgUserId;
    }

    /**
     * Sets the value of lastChgUserId
     * @param Long lastChgUserId
     */
    public void setLastChgUserId(Long aLastChgUserId) {
        lastChgUserId = aLastChgUserId;
        setItDirty(true);
    }

    /**
     * Access method for localId
     * @return String localId
     */
    public String getLocalId() {

        return localId;
    }

    /**
     * Sets the value of localId
     * @param String localId
     */
    public void setLocalId(String aLocalId) {
        localId = aLocalId;
        setItDirty(true);
    }

    /**
     * Access method for nm
     * @return String nm
     */
    public String getNm() {

        return nm;
    }

    /**
     * Sets the value of nm
     * @param String nm
     */
    public void setNm(String aNm) {
        nm = aNm;
        setItDirty(true);
    }

    /**
     * Access method for qty
     * @return String qty
     */
    public String getQty() {

        return qty;
    }

    /**
     * Sets the value of qty
     * @param String qty
     */
    public void setQty(String aQty) {
        qty = aQty;
        setItDirty(true);
    }

    /**
     * Access method for QtyUnitCd
     * @return String qtyUnitCd
     */
    public String getQtyUnitCd() {

        return qtyUnitCd;
    }

    /**
     * set qtyUnitCd
     * @param String qtyUnitCd
     */
    public void setQtyUnitCd(String aQtyUnitCd) {
        qtyUnitCd = aQtyUnitCd;
        setItDirty(true);
    }

    /**
     * Access method for RecordStatusCd
     * @return String recordStatusCd
     */
    public String getRecordStatusCd() {

        return recordStatusCd;
    }

    /**
     * Sets the value of recordStatusCd
     * @param String recordStatusCd
     */
    public void setRecordStatusCd(String aRecordStatusCd) {
        recordStatusCd = aRecordStatusCd;
        setItDirty(true);
    }

    /**
     * Access method for RecordStatusTime
     * @return Timestamp recordStatusTime
     */
    public Timestamp getRecordStatusTime() {

        return recordStatusTime;
    }

    /**
     * Sets the value of recordStatusTime
     * @param recordStatusTime
     */
    public void setRecordStatusTime(Timestamp aRecordStatusTime) {
        recordStatusTime = aRecordStatusTime;
        setItDirty(true);
    }

    /**
     * Sets the value of recordStatusTime
     * takes a String adn converts to a Timestamp
     * @param String strTime
     */
    public void setRecordStatusTime_s(String strTime) {

        if (strTime == null)

            return;

        this.setRecordStatusTime(StringUtils.stringToStrutsTimestamp(strTime));
    }

    /**
     * Access method for riskCd
     * @return String riskCd
     */
    public String getRiskCd() {

        return riskCd;
    }

    /**
     * Sets the value of riskCd
     * @param String riskCd
     */
    public void setRiskCd(String aRiskCd) {
        riskCd = aRiskCd;
        setItDirty(true);
    }

    /**
     * Access method for RiskDescTxt
     * @return String riskDescTxt
     */
    public String getRiskDescTxt() {

        return riskDescTxt;
    }

    /**
     * Sets the value of riskDescTxt
     * @param String riskDescTxt
     */
    public void setRiskDescTxt(String aRiskDescTxt) {
        riskDescTxt = aRiskDescTxt;
        setItDirty(true);
    }

    /**
     * Access method for statusCd
     * @return String statusCd
     */
    public String getStatusCd() {

        return statusCd;
    }

    /**
     *Sets the value of statusCd
     * @param String statusCd
     */
    public void setStatusCd(String aStatusCd) {
        statusCd = aStatusCd;
        setItDirty(true);
    }

    /**
     * Access method for StatusTime
     * @return Timestamp statusTime
     */
    public Timestamp getStatusTime() {

        return statusTime;
    }

    /**
     * Sets the value of statusTime
     * @param Timestamp statusTime
     */
    public void setStatusTime(Timestamp aStatusTime) {
        statusTime = aStatusTime;
        setItDirty(true);
    }

    /**
     * Sets the value of satusTime
     * takes a String and converts to a Timestamp
     * @param String strTime
     */
    public void setStatusTime_s(String strTime) {

        if (strTime == null)

            return;

        this.setStatusTime(StringUtils.stringToStrutsTimestamp(strTime));
    }

    /**
     * Access method for UserAffiliationTxt
     * @return String userAffiliationTxt
     */
    public String getUserAffiliationTxt() {

        return userAffiliationTxt;
    }

    /**
     * Sets the value of  userAffiliationTxt
     * @param String userAffiliationTxt
     */
    public void setUserAffiliationTxt(String aUserAffiliationTxt) {
        userAffiliationTxt = aUserAffiliationTxt;
        setItDirty(true);
    }

    /**
     * Access method for versionCtrlNbr
     * @return Integer versionCtrlNbr
     */
    public Integer getVersionCtrlNbr() {

        return versionCtrlNbr;
    }

    /**
     * Sets the value of versionCtrlNbr
     * @param Integer versionCtrlNbr
     */
    public void setVersionCtrlNbr(Integer aVersionCtrlNbr) {
        versionCtrlNbr = aVersionCtrlNbr;
        setItDirty(true);
    }

    /**
     * Access method for progAreaCd
     * @return String progAreaCd
     */
    public String getProgAreaCd() {

        return progAreaCd;
    }

    /**
     * Sets the value of progAreaCd
     * @param String progAreaCd
     */
    public void setProgAreaCd(String aProgAreaCd) {
        progAreaCd = aProgAreaCd;
        setItDirty(true);
    }

    /**
     * Access method for jurisdictionCd
     * @return String jurisdictionCd
     */
    public String getJurisdictionCd() {

        return jurisdictionCd;
    }

    /**
     * Sets the value of jurisdictionCd
     * @param String jurisdictionCd
     */
    public void setJurisdictionCd(String aJurisdictionCd) {
        jurisdictionCd = aJurisdictionCd;
        setItDirty(true);
    }

    /**
     * Access method for programJurisdictionOid
     * @return Long programJurisdictionOid
     */
    public Long getProgramJurisdictionOid() {

        return programJurisdictionOid;
    }

    /**
     * Sets the value of programJurisdictionOid
     * @param Long programJurisdictionOid
     */
    public void setProgramJurisdictionOid(Long aProgramJurisdictionOid) {
        programJurisdictionOid = aProgramJurisdictionOid;
        setItDirty(true);
    }

    /**
     * Access method for sharedInd
     * @return String sharedInd
     */
    public String getSharedInd() {

        return sharedInd;
    }

    /**
     * Sets the value of sharedInd
     * @param String sharedInd
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
        voClass = ((MaterialDT)objectname1).getClass();

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
     * @return boolean itNew
     */
    public boolean isItNew() {

        return itNew;
    }

    /**
     *
     * @return boolean itDelete
     */
    public boolean isItDelete() {

        return itDelete;
    }

    /**
     *
     * @param boolean itDelete
     */
    public void setItDelete(boolean itDelete) {
        this.itDelete = itDelete;
    }

    /**
     *
     * @return Long materialUid
     */
    public Long getUid() {

        return materialUid;
    }

    /**
     * Access method for superclass
     * @return String NEDSSConstantUtil.CLASSTYPE_ENTITY
     */
    public String getSuperclass() {

        return NEDSSConstants.CLASSTYPE_ENTITY;
    }
}