package gov.cdc.nedss.act.intervention.dt;

import gov.cdc.nedss.util.*;

import java.sql.Timestamp;

/**
 * Title: Procedure1HistDT
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:CSC
 * @author nedss project team
 * @version 1.0
 */

public class Procedure1HistDT
    extends AbstractVO {
	private static final long serialVersionUID = 1L;

    private Long interventionUid;
    private Integer versionCtrlNbr;
    private String approachSiteCd;
    private String approachSiteDescTxt;
    private String progAreaCd = null;
    private String jurisdictionCd = null;
    private Long programJurisdictionOid = null;
    private String sharedInd = null;
    private boolean itDirty = false;
    private boolean itNew = true;
    private boolean itDelete = false;

    /**
     *
     * @return Long interventionUid
     */
    public Long getInterventionUid() {

        return interventionUid;
    }

    /**
     *
     * @param aInterventionUid
     */
    public void setInterventionUid(Long aInterventionUid) {
        interventionUid = aInterventionUid;
        setItDirty(true);
    }

    /**
     *
     * @return Integer versionCtrlNbr
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
     * @return String approachSiteCd
     */
    public String getApproachSiteCd() {

        return approachSiteCd;
    }

    /**
     *
     * @param aApproachSiteCd
     */
    public void setApproachSiteCd(String aApproachSiteCd) {
        approachSiteCd = aApproachSiteCd;
        setItDirty(true);
    }

    /**
     *
     * @return String approachSiteDescTxt
     */
    public String getApproachSiteDescTxt() {

        return approachSiteDescTxt;
    }

    /**
     *
     * @param aApproachSiteDescTxt
     */
    public void setApproachSiteDescTxt(String aApproachSiteDescTxt) {
        approachSiteDescTxt = aApproachSiteDescTxt;
        setItDirty(true);
    }

    /**
     *
     * @return String progAreaCd
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
     * @return String jurisdictionCd
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
     * @return Long programJurisdictionOid
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
     * @return String sharedInd
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
     * @return boolean
     */
    public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass) {
        voClass = ((Procedure1HistDT)objectname1).getClass();

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
     * @return boolean
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
     * @return boolean
     */
    public boolean isItNew() {

        return itNew;
    }

    /**
     *
     * @return boolean
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