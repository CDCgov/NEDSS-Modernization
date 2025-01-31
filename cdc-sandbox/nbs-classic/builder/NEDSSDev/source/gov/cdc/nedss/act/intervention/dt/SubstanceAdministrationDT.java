package gov.cdc.nedss.act.intervention.dt;

import gov.cdc.nedss.util.*;

import java.sql.Timestamp;

/**
 * Title: SubstanceAdministrationDT
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:CSC
 * @author nedss project team
 * @version 1.0
 */

public class SubstanceAdministrationDT
    extends AbstractVO {
	private static final long serialVersionUID = 1L;

    private Long interventionUid;
    private String doseQty;
    private String doseQtyUnitCd;
    private String formCd;
    private String formDescTxt;
    private String rateQty;
    private String rateQtyUnitCd;
    private String routeCd;
    private String routeDescTxt;
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
     * @return String doseQty
     */
    public String getDoseQty() {

        return doseQty;
    }

    /**
     *
     * @param aDoseQty
     */
    public void setDoseQty(String aDoseQty) {
        doseQty = aDoseQty;
        setItDirty(true);
    }

    /**
     *
     * @return String doseQtyUnitCd
     */
    public String getDoseQtyUnitCd() {

        return doseQtyUnitCd;
    }

    /**
     *
     * @param aDoseQtyUnitCd
     */
    public void setDoseQtyUnitCd(String aDoseQtyUnitCd) {
        doseQtyUnitCd = aDoseQtyUnitCd;
        setItDirty(true);
    }

    /**
     *
     * @return String formCd
     */
    public String getFormCd() {

        return formCd;
    }

    /**
     *
     * @param aFormCd
     */
    public void setFormCd(String aFormCd) {
        formCd = aFormCd;
        setItDirty(true);
    }

    /**
     *
     * @return String formDescTxt
     */
    public String getFormDescTxt() {

        return formDescTxt;
    }

    /**
     *
     * @param aFormDescTxt
     */
    public void setFormDescTxt(String aFormDescTxt) {
        formDescTxt = aFormDescTxt;
        setItDirty(true);
    }

    /**
     *
     * @return String rateQty
     */
    public String getRateQty() {

        return rateQty;
    }

    /**
     *
     * @param aRateQty
     */
    public void setRateQty(String aRateQty) {
        rateQty = aRateQty;
        setItDirty(true);
    }

    /**
     *
     * @return String rateQtyUnitCd
     */
    public String getRateQtyUnitCd() {

        return rateQtyUnitCd;
    }

    /**
     *
     * @param aRateQtyUnitCd
     */
    public void setRateQtyUnitCd(String aRateQtyUnitCd) {
        rateQtyUnitCd = aRateQtyUnitCd;
        setItDirty(true);
    }

    /**
     *
     * @return String routeCd
     */
    public String getRouteCd() {

        return routeCd;
    }

    /**
     *
     * @param aRouteCd
     */
    public void setRouteCd(String aRouteCd) {
        routeCd = aRouteCd;
        setItDirty(true);
    }

    /**
     *
     * @return String routeDescTxt
     */
    public String getRouteDescTxt() {

        return routeDescTxt;
    }

    /**
     *
     * @param aRouteDescTxt
     */
    public void setRouteDescTxt(String aRouteDescTxt) {
        routeDescTxt = aRouteDescTxt;
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
        voClass = ((SubstanceAdministrationDT)objectname1).getClass();

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