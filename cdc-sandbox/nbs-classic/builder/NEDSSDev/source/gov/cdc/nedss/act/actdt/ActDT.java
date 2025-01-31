 
package gov.cdc.nedss.act.actdt;

import gov.cdc.nedss.util.*;

import java.sql.Timestamp;

/**
 * Title: ActDT
 * Description: A holder for data from the
 * Act table
 * 
 * @author nedss project team
 * @version 1.0
 */
public class ActDT
    extends AbstractVO {
	private static final long serialVersionUID = 1L;
    /**
     * A Long object holding the value of an ActUid.
     */
    private Long actUid;
    /**
     * A String holding the value of the classCd.
     */
    private String classCd;
    /**
     * A String holding the value of the moodCd.
     */
    private String moodCd;
    /**
     * A String holding the value of the program area.
     */
    private String progAreaCd = null;
    /**
     * A String holding the value of a jurisdictionCd.
     */
    private String jurisdictionCd = null;
    /**
     * A Long holding the value of a programJurisdictionUid.
     */
    private Long programJurisdictionOid = null;
    /**
     * A String holding the value of the shared
     * indicator.
     */
    private String sharedInd = null;
    /**
     * A boolean variable that indicates if
     * the object is Dirty or not.
     */
    private boolean itDirty = false;
    /**
     * A boolean variable that indicates if
     * the object is New or not.
     */
    private boolean itNew = true;
    /**
     * A boolean variable that indicates if
     * the object is to be deleted or not.
     */
    private boolean itDelete = false;

    /**
     * Returns the ActUid.
     * 
     * @return Long
     */
    public Long getActUid() {

        return actUid;
    }

    /**
     * Sets the value of the actUid variable.
     * 
     * @param aActUid Long
     */
    public void setActUid(Long aActUid) {
        actUid = aActUid;
        setItDirty(true);
    }

    /**
     * Gets the value of the classCd variable.
     * 
     * @return String
     */
    public String getClassCd() {

        return classCd;
    }

    /**
     * Sets the value of the classCd variable.
     * 
     * @param aClassCd String
     */
    public void setClassCd(String aClassCd) {
        classCd = aClassCd;
        setItDirty(true);
    }

    /**
     * Gets the value of the moonCd variable.
     * 
     * @return String
     */
    public String getMoodCd() {

        return moodCd;
    }

    /**
     * Sets the value of the moonCd variable.
     * 
     * @param aMoodCd String
     */
    public void setMoodCd(String aMoodCd) {
        moodCd = aMoodCd;
        setItDirty(true);
    }

    /**
     * Gets the value of the progAreaCd variable.
     * 
     * @return String
     */
    public String getProgAreaCd() {

        return progAreaCd;
    }

    /**
     * Sets the value of the progAreaCd variable.
     * 
     * @param aProgAreaCd String
     */
    public void setProgAreaCd(String aProgAreaCd) {
        progAreaCd = aProgAreaCd;
        setItDirty(true);
    }

    /**
     * Gets the value of the jurisdictionCd variable.
     * 
     * @return String
     */
    public String getJurisdictionCd() {

        return jurisdictionCd;
    }

    /**
     * Gets the value of the jurisdictionCd variable.
     * 
     * @param aJurisdictionCd String
     */
    public void setJurisdictionCd(String aJurisdictionCd) {
        jurisdictionCd = aJurisdictionCd;
        setItDirty(true);
    }

    /**
     * Gets the value of the programJurisdictionOid variable.
     * 
     * @return Long
     */
    public Long getProgramJurisdictionOid() {

        return programJurisdictionOid;
    }

    /**
     * Sets the value of the programJurisdictionOid variable.
     * 
     * @param aProgramJurisdictionOid Long
     */
    public void setProgramJurisdictionOid(Long aProgramJurisdictionOid) {
        programJurisdictionOid = aProgramJurisdictionOid;
        setItDirty(true);
    }

    /**
     * Gets the value of the sharedInd variable.
     * 
     * @return String
     */
    public String getSharedInd() {

        return sharedInd;
    }

    /**
     * Sets the value of the sharedInd variable.
     * 
     * @param aSharedInd String
     */
    public void setSharedInd(String aSharedInd) {
        sharedInd = aSharedInd;
        setItDirty(true);
    }

    /**
     * Returns the value of the itEqual variable.
     * 
     * @param objectname1 java.lang.Object
     * @param objectname2 java.lang.Object
     * @param voClass Class
     * @return boolean
     */
    public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass) {
        voClass = ((ActDT)objectname1).getClass();

        NedssUtils compareObjs = new NedssUtils();

        return (compareObjs.equals(objectname1, objectname2, voClass));
    }

    /**
     * Sets the value of the itDirty variable.
     * 
     * @param itDirty boolean
     */
    public void setItDirty(boolean itDirty) {
        this.itDirty = itDirty;
    }

    /**
     * Returns the value of the itDirty variable.
     * 
     * @return boolean
     */
    public boolean isItDirty() {

        return itDirty;
    }

    /**
     * Sets the value of the itNew variable.
     * 
     * @param itNew boolean
     */
    public void setItNew(boolean itNew) {
        this.itNew = itNew;
    }

    /**
     * Returns the value of the itNew variable.
     * 
     * @return boolean
     */
    public boolean isItNew() {

        return itNew;
    }

    /**
     * Returns the value of the itDelete variable.
     * 
     * @return boolean
     */
    public boolean isItDelete() {

        return itDelete;
    }

    /**
     * Sets the value of the itDelete variable.
     * 
     * @param itDelete boolean
     */
    public void setItDelete(boolean itDelete) {
        this.itDelete = itDelete;
    }
}
