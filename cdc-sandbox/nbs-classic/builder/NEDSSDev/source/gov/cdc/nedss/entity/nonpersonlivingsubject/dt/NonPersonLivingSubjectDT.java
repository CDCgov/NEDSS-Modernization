/**
* Name:		    NonPersonLivingSubjectDT
* Description:	NonPersonLivingSubjec data table object
* Copyright:	Copyright (c) 2002
* Company: 	    Computer Sciences Corporation
* @author	    NEDSS Development Team
* @version	    1.0
*/
package gov.cdc.nedss.entity.nonpersonlivingsubject.dt;

import gov.cdc.nedss.systemservice.util.*;
import gov.cdc.nedss.util.*;

import java.sql.Timestamp;


public class NonPersonLivingSubjectDT
    extends AbstractVO
    implements RootDTInterface {
	private static final long serialVersionUID = 1L;
    private Long nonPersonUid;
    private String addReasonCd;
    private Timestamp addTime;
    private Long addUserId;
    private String birthSexCd;
    private Integer birthOrderNbr;
    private Timestamp birthTime;
    private String breedCd;
    private String breedDescTxt;
    private String cd;
    private String cdDescTxt;
    private String deceasedIndCd;
    private Timestamp deceasedTime;
    private String description;
    private String lastChgReasonCd;
    private Timestamp lastChgTime;
    private Long lastChgUserId;
    private String localId;
    private String multipleBirthInd;
    private String nm;
    private String recordStatusCd;
    private Timestamp recordStatusTime;
    private String statusCd;
    private Timestamp statusTime;
    private String taxonomicClassificationCd;
    private String taxonomicClassificationDesc;
    private String userAffiliationTxt;
    private Integer versionCtrlNbr;
    private String progAreaCd = null;
    private String jurisdictionCd = null;
    private Long programJurisdictionOid = null;
    private String sharedInd = null;
    private boolean itDirty = false;
    private boolean itNew = true;
    private boolean itDelete = false;

    /**
     * gets nonPersonUid
     * @return Long nonPersonUid
     */
    public Long getNonPersonUid() {

        return nonPersonUid;
    }

    /**
     * sets nonPersonUid
     * @param Long nonPersonUid
     */
    public void setNonPersonUid(Long aNonPersonUid) {
        nonPersonUid = aNonPersonUid;
        setItDirty(true);
    }

    /**
     * gets addReasonCd
     * @return String addReasonCd
     */
    public String getAddReasonCd() {

        return addReasonCd;
    }

    /**
     * sets addReasonCd
     * @param String addReasonCd
     */
    public void setAddReasonCd(String aAddReasonCd) {
        addReasonCd = aAddReasonCd;
        setItDirty(true);
    }

    /**
     * gets addTime
     * @return Timestamp addTime
     */
    public Timestamp getAddTime() {

        return addTime;
    }

    /**
     * sets addTime
     * @param Timestamp addTime
     */
    public void setAddTime(Timestamp aAddTime) {
        addTime = aAddTime;
        setItDirty(true);
    }

    /**
     * sets addTime
     * takes a String adn converts it to a Timestamp
     * @param String strTime
     */
    public void setAddTime_s(String strTime) {

        if (strTime == null)

            return;

        this.setAddTime(StringUtils.stringToStrutsTimestamp(strTime));
    }

    /**
     * gets addUserId
     * @return Long addUserId
     */
    public Long getAddUserId() {

        return addUserId;
    }

    /**
     * sets addUserId
     * @param Long addUserId
     */
    public void setAddUserId(Long aAddUserId) {
        addUserId = aAddUserId;
        setItDirty(true);
    }

    /**
     * gets bitrhSexCd
     * @return String birthSexCd
     */
    public String getBirthSexCd() {

        return birthSexCd;
    }

    /**
     * sets birthSexCd
     * @param String birthSexCd
     */
    public void setBirthSexCd(String aBirthSexCd) {
        birthSexCd = aBirthSexCd;
        setItDirty(true);
    }

    /**
     * gets birthOrderNbr
     * @return Integer birthOrderNbr
     */
    public Integer getBirthOrderNbr() {

        return birthOrderNbr;
    }

    /**
     * sets birthOrderNbr
     * @param Interger birthOrderNbr
     */
    public void setBirthOrderNbr(Integer aBirthOrderNbr) {
        birthOrderNbr = aBirthOrderNbr;
        setItDirty(true);
    }

    /**
     * gets birthTime
     * @return Timestamp birthTime
     */
    public Timestamp getBirthTime() {

        return birthTime;
    }

    /**
     * sets birthTime
     * @param Timestamp birthTime
     */
    public void setBirthTime(Timestamp aBirthTime) {
        birthTime = aBirthTime;
        setItDirty(true);
    }

    /**
     * sets birthTime
     * takes a String and converts to Timestamp
     * @param String strTime
     */
    public void setBirthTime_s(String strTime) {

        if (strTime == null)

            return;

        this.setBirthTime(StringUtils.stringToStrutsTimestamp(strTime));
    }

    /**
     * gets breedCd
     * @return String breedCd
     */
    public String getBreedCd() {

        return breedCd;
    }

    /**
     * sets breedCd
     * @param String breedCd
     */
    public void setBreedCd(String aBreedCd) {
        breedCd = aBreedCd;
        setItDirty(true);
    }

    /**
     * gets breedDescTxt
     * @return String breedDescTxt
     */
    public String getBreedDescTxt() {

        return breedDescTxt;
    }

    /**
     * sets breedDescTxt
     * @param String breedDescTxt
     */
    public void setBreedDescTxt(String aBreedDescTxt) {
        breedDescTxt = aBreedDescTxt;
        setItDirty(true);
    }

    /**
     * gets Cd
     * @return  String cd
     */
    public String getCd() {

        return cd;
    }

    /**
     * sets cd
     * @param String cd
     */
    public void setCd(String aCd) {
        cd = aCd;
        setItDirty(true);
    }

    /**
     * gets cdDescTxt
     * @return  String cdDescTxt
     */
    public String getCdDescTxt() {

        return cdDescTxt;
    }

    /**
     * sets CdDescTxt
     * @param String cdDescTxt
     */
    public void setCdDescTxt(String aCdDescTxt) {
        cdDescTxt = aCdDescTxt;
        setItDirty(true);
    }

    /**
     *
     * @return
     */
    public String getDeceasedIndCd() {

        return deceasedIndCd;
    }

    /**
     *
     * @param aDeceasedIndCd
     */
    public void setDeceasedIndCd(String aDeceasedIndCd) {
        deceasedIndCd = aDeceasedIndCd;
        setItDirty(true);
    }

    /**
     *
     * @return
     */
    public Timestamp getDeceasedTime() {

        return deceasedTime;
    }

    /**
     *
     * @param aDeceasedTime
     */
    public void setDeceasedTime(Timestamp aDeceasedTime) {
        deceasedTime = aDeceasedTime;
        setItDirty(true);
    }

    /**
     *
     * @param strTime
     */
    public void setDeceasedTime_s(String strTime) {

        if (strTime == null)

            return;

        this.setDeceasedTime(StringUtils.stringToStrutsTimestamp(strTime));
    }

    /**
     * gets description
     * @return  String description
     */
    public String getDescription() {

        return description;
    }

    /**
     * sets description
     * @param String description
     */
    public void setDescription(String aDescription) {
        description = aDescription;
        setItDirty(true);
    }

    /**
     * gets lastChgReasonCd
     * @return String lastChgReasonCd
     */
    public String getLastChgReasonCd() {

        return lastChgReasonCd;
    }

    /**
     * sets LastChgReasonCd
     * @param String lastChgReasonCd
     */
    public void setLastChgReasonCd(String aLastChgReasonCd) {
        lastChgReasonCd = aLastChgReasonCd;
        setItDirty(true);
    }

    /**
     * gets LastChgTime
     * @return Timestamp lastChgTime
     */
    public Timestamp getLastChgTime() {

        return lastChgTime;
    }

    /**
     * sets lastChgTime
     * @param Timestamp lastChgTime
     */
    public void setLastChgTime(Timestamp aLastChgTime) {
        lastChgTime = aLastChgTime;
        setItDirty(true);
    }

    /**
     * sets lastChgTime
     * takes a String and converts to Timestamp
     * @param String strTime
     */
    public void setLastChgTime_s(String strTime) {

        if (strTime == null)

            return;

        this.setLastChgTime(StringUtils.stringToStrutsTimestamp(strTime));
    }

    /**
     * gets lastChgUserId
     * @return Long lastChgUserId
     */
    public Long getLastChgUserId() {

        return lastChgUserId;
    }

    /**
     * sets lastChgUserId
     * @param Long lastChgUserId
     */
    public void setLastChgUserId(Long aLastChgUserId) {
        lastChgUserId = aLastChgUserId;
        setItDirty(true);
    }

    /**
     * gets localId
     * @return String localId
     */
    public String getLocalId() {

        return localId;
    }

    /**
     * sets localId
     * @param String localId
     */
    public void setLocalId(String aLocalId) {
        localId = aLocalId;
        setItDirty(true);
    }

    /**
     * gets multipleBirthInd
     * @return String multipleBirthInd
     */
    public String getMultipleBirthInd() {

        return multipleBirthInd;
    }

    /**
     * sets multipleBirthInd
     * @param String multipleBirthInd
     */
    public void setMultipleBirthInd(String aMultipleBirthInd) {
        multipleBirthInd = aMultipleBirthInd;
        setItDirty(true);
    }

    /**
     * gets nm
     * @return String nm
     */
    public String getNm() {

        return nm;
    }

    /**
     * set nm
     * @param String nm
     */
    public void setNm(String aNm) {
        nm = aNm;
        setItDirty(true);
    }

    /**
     * gets recordStatusCd
     * @return String recordStatusCd
     */
    public String getRecordStatusCd() {

        return recordStatusCd;
    }

    /**
     * sets recordStatusCd
     * @param String recordStatusCd
     */
    public void setRecordStatusCd(String aRecordStatusCd) {
        recordStatusCd = aRecordStatusCd;
        setItDirty(true);
    }

    /**
     * gets recordStatusTime
     * @return Timestamp recordStatusTime
     */
    public Timestamp getRecordStatusTime() {

        return recordStatusTime;
    }

    /**
     * sets recordStatusTime
     * @param Timestamp recordStatusTime
     */
    public void setRecordStatusTime(Timestamp aRecordStatusTime) {
        recordStatusTime = aRecordStatusTime;
        setItDirty(true);
    }

    /**
     * sets recordStatusTime
     * takes String and converts to Timestamp
     * @param String strTime
     */
    public void setRecordStatusTime_s(String strTime) {

        if (strTime == null)

            return;

        this.setRecordStatusTime(StringUtils.stringToStrutsTimestamp(strTime));
    }

    /**
     * gets statusCd
     * @return String statusCd
     */
    public String getStatusCd() {

        return statusCd;
    }

    /**
     * sets statusCd
     * @param String statusCd
     */
    public void setStatusCd(String aStatusCd) {
        statusCd = aStatusCd;
        setItDirty(true);
    }

    /**
     * gets statusTime
     * @return Timestamp statusTime
     */
    public Timestamp getStatusTime() {

        return statusTime;
    }

    /**
     * sets statusTime
     * @param Timestamp statusTime
     */
    public void setStatusTime(Timestamp aStatusTime) {
        statusTime = aStatusTime;
        setItDirty(true);
    }

    /**
     * sets statusTime
     * takes String and converts to a Timestamp
     * @param String strTime
     */
    public void setStatusTime_s(String strTime) {

        if (strTime == null)

            return;

        this.setStatusTime(StringUtils.stringToStrutsTimestamp(strTime));
    }

    /**
     * gets taxonomicClassificationCd
     * @return String taxonomicClassificationCd
     */
    public String getTaxonomicClassificationCd() {

        return taxonomicClassificationCd;
    }

    /**
     * sets taxonomicClassificationCd
     * @param String taxonomicClassificationCd
     */
    public void setTaxonomicClassificationCd(String aTaxonomicClassificationCd) {
        taxonomicClassificationCd = aTaxonomicClassificationCd;
        setItDirty(true);
    }

    /**
     * gets taxonomicClassificationDesc
     * @return String taxonomicClassificationDesc
     */
    public String getTaxonomicClassificationDesc() {

        return taxonomicClassificationDesc;
    }

    /**
     * sets taxonomicClassificationDesc
     * @param String taxonomicClassificationDesc
     */
    public void setTaxonomicClassificationDesc(String aTaxonomicClassificationDesc) {
        taxonomicClassificationDesc = aTaxonomicClassificationDesc;
        setItDirty(true);
    }

    /**
     * gets userAffiliationTxt
     * @return String userAffiliationTxt
     */
    public String getUserAffiliationTxt() {

        return userAffiliationTxt;
    }

    /**
     * sets userAffiliationTxt
     * @param aUserAffiliationTxt
     */
    public void setUserAffiliationTxt(String aUserAffiliationTxt) {
        userAffiliationTxt = aUserAffiliationTxt;
        setItDirty(true);
    }

    /**
     * gets versionCtrlNbr
     * @return Integer versionCtrlNbr
     */
    public Integer getVersionCtrlNbr() {

        return versionCtrlNbr;
    }

    /**
     * sets versionCtrlNbr
     * @param aVersionCtrlNbr
     */
    public void setVersionCtrlNbr(Integer aVersionCtrlNbr) {
        versionCtrlNbr = aVersionCtrlNbr;
        setItDirty(true);
    }

    /**
     * gets progAreaCd
     * @return String progAreaCd
     */
    public String getProgAreaCd() {

        return progAreaCd;
    }

    /**
     * sets progAreaCd
     * @param aProgAreaCd
     */
    public void setProgAreaCd(String aProgAreaCd) {
        progAreaCd = aProgAreaCd;
        setItDirty(true);
    }

    /**
     * gets jurisdictionCd
     * @return String jurisdictionCd
     */
    public String getJurisdictionCd() {

        return jurisdictionCd;
    }

    /**
     * sets jurisdictionCd
     * @param aJurisdictionCd
     */
    public void setJurisdictionCd(String aJurisdictionCd) {
        jurisdictionCd = aJurisdictionCd;
        setItDirty(true);
    }

    /**
     * gets programJurisdictionOid
     * @return Long programJurisdictionOid
     */
    public Long getProgramJurisdictionOid() {

        return programJurisdictionOid;
    }

    /**
     * sets programJurisdictionOid
     * @param aProgramJurisdictionOid
     */
    public void setProgramJurisdictionOid(Long aProgramJurisdictionOid) {
        programJurisdictionOid = aProgramJurisdictionOid;
        setItDirty(true);
    }

    /**
     * gets sharedInd
     * @return String sharedInd
     */
    public String getSharedInd() {

        return sharedInd;
    }

    /**
     * sets sharedInd
     * @param aSharedInd
     */
    public void setSharedInd(String aSharedInd) {
        sharedInd = aSharedInd;
        setItDirty(true);
    }

    /**
     * checks
     * @param objectname1
     * @param objectname2
     * @param voClass
     * @return boolean
     */
    public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass) {
        voClass = ((NonPersonLivingSubjectDT)objectname1).getClass();

        NedssUtils compareObjs = new NedssUtils();

        return (compareObjs.equals(objectname1, objectname2, voClass));
    }

    /**
     * sets itDirty
     * @param boolean itDirty
     */
    public void setItDirty(boolean itDirty) {
        this.itDirty = itDirty;
    }

    /**
     * gets itDirty
     * @return boolean itDirty
     */
    public boolean isItDirty() {

        return itDirty;
    }

    /**
     * sets itNew
     * @param boolean itNew
     */
    public void setItNew(boolean itNew) {
        this.itNew = itNew;
    }

    /**
     * gets itNew
     * @return boolean itNew
     */
    public boolean isItNew() {

        return itNew;
    }

    /**
     * gets itDelete
     * @return boolean itDelete
     */
    public boolean isItDelete() {

        return itDelete;
    }

    /**
     * sets itDelete
     * @param itDelete
     */
    public void setItDelete(boolean itDelete) {
        this.itDelete = itDelete;
    }

    /**
     * gets uid
     * @return Long uid
     */
    public Long getUid() {

        return nonPersonUid;
    }

    /**
     * gets superclass
     * @return String superclass
     */
    public String getSuperclass() {

        return NEDSSConstants.CLASSTYPE_ENTITY;
    }
}