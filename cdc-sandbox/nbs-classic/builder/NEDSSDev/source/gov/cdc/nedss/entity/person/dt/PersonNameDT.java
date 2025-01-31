package gov.cdc.nedss.entity.person.dt;

import java.sql.Timestamp;
import java.io.*;

import  gov.cdc.nedss.util.*;
import gov.cdc.nedss.systemservice.util.*;

/**
* Name:		    PersonNameDT
* Description:	PersonName data table object
* Copyright:	Copyright (c) 2002
* Company: 	    Computer Sciences Corporation
* @author	    NEDSS Development Team
* @version	    1.0
*/
public class PersonNameDT
    extends AbstractVO
    implements RootDTInterface {
	private static final long serialVersionUID = 1L;
    private Long personUid;
    private Integer personNameSeq;   
    private String addReasonCd;
    private Timestamp addTime;
    private Long addUserId;
    private Timestamp asOfDate;
    private String defaultNmInd;
    private String durationAmt;
    private String durationUnitCd;
    private String firstNm;
    private String firstNmSndx;
    private Timestamp fromTime;
    private String lastChgReasonCd;
    private Timestamp lastChgTime;
    private Long lastChgUserId;
    private String lastNm;
    private String lastNmSndx;
    private String lastNm2;
    private String lastNm2Sndx;
    private String middleNm;
    private String middleNm2;
    private String nmDegree;
    private String nmPrefix;
    private String nmSuffix;
    private String nmSuffixCd;
    private String nmUseCd;
    private String recordStatusCd;
    private Timestamp recordStatusTime;
    private String statusCd;
    private Timestamp statusTime;
    private Timestamp toTime;
    private String userAffiliationTxt;
    private String progAreaCd = null;
    private String jurisdictionCd = null;
    private Long programJurisdictionOid = null;
    private String sharedInd = null;
    private boolean itDirty = false;
    private boolean itNew = true;
    private boolean itDelete = false;
    private Integer versionCtrlNbr;
    private String localId;

   /**
   Access method for the personUid property.

   @return   the current value of the personUid property
    */
    public Long getPersonUid()
    {
        return personUid;
    }

   /**
   Sets the value of the personUid property.

   @param aPersonUid the new value of the personUid property
    */
    public void setPersonUid(Long aPersonUid)
    {
        personUid = aPersonUid;
        setItDirty(true);
    }

   /**
   Access method for the personNameSeq property.

   @return   the current value of the personNameSeq property
    */
    public Integer getPersonNameSeq()
    {
    	return personNameSeq;
    }

   /**
   Sets the value of the personNameSeq property.

   @param aPersonNameSeq the new value of the personNameSeq property
    */
    public void setPersonNameSeq(Integer aPersonNameSeq)
    {
    	if(aPersonNameSeq!=null && aPersonNameSeq.intValue()==0)
    		personNameSeq=null;
    	else
    		personNameSeq = aPersonNameSeq;
        setItDirty(true);
    }

   /**
   Access method for the addReasonCd property.

   @return   the current value of the addReasonCd property
    */
    public String getAddReasonCd()
    {
        return addReasonCd;
    }

   /**
   Sets the value of the addReasonCd property.

   @param aAddReasonCd the new value of the addReasonCd property
    */
    public void setAddReasonCd(String aAddReasonCd)
    {
        addReasonCd = aAddReasonCd;
        setItDirty(true);
    }

   /**
   Access method for the addTime property.

   @return   the current value of the addTime property
    */
    public Timestamp getAddTime()
    {
        return addTime;
    }

   /**
   Sets the value of the addTime property.

   @param aAddTime the new value of the addTime property
    */
    public void setAddTime(Timestamp aAddTime)
    {
        addTime = aAddTime;
        setItDirty(true);
    }

   /**
   Sets the value of the addTime property.
    takes a String and converts to Timestamp
   @param aAddTime the new value of the addTime property
    */
   public void setAddTime_s(String strTime)
   {
      if (strTime == null) return;
      this.setAddTime(StringUtils.stringToStrutsTimestamp(strTime));
   }

   /**
   Access method for the addUserId property.

   @return   the current value of the addUserId property
    */
    public Long getAddUserId()
    {
        return addUserId;
    }

   /**
   Sets the value of the addUserId property.

   @param aAddUserId the new value of the addUserId property
    */
    public void setAddUserId(Long aAddUserId)
    {
        addUserId = aAddUserId;
        setItDirty(true);
    }


	/**
	 * gets asOfDate
	 * @return Timestamp
	 */
	public Timestamp getAsOfDate(){
		return this.asOfDate;
	}


	/**
	 * sets asOfDate
	 * @param aAsOfDate
	 */
	public void setAsOfDate(Timestamp aAsOfDate){
		this.asOfDate = aAsOfDate;
		setItDirty(true);
	}

  	public void setAsOfDate_s(String strTime)
   	{
             if (strTime == null)
               return;
             this.setAsOfDate(StringUtils.stringToStrutsTimestamp(strTime));
   	}

   /**
   Access method for the defaultNmInd property.

   @return   the current value of the defaultNmInd property
    */
    public String getDefaultNmInd()
    {
        return defaultNmInd;
    }

   /**
   Sets the value of the defaultNmInd property.

   @param aDefaultNmInd the new value of the defaultNmInd property
    */
    public void setDefaultNmInd(String aDefaultNmInd)
    {
        defaultNmInd = aDefaultNmInd;
        setItDirty(true);
    }

   /**
   Access method for the durationAmt property.

   @return   the current value of the durationAmt property
    */
    public String getDurationAmt()
    {
        return durationAmt;
    }

   /**
   Sets the value of the durationAmt property.

   @param aDurationAmt the new value of the durationAmt property
    */
    public void setDurationAmt(String aDurationAmt)
    {
        durationAmt = aDurationAmt;
        setItDirty(true);
    }

   /**
   Access method for the durationUnitCd property.

   @return   the current value of the durationUnitCd property
    */
    public String getDurationUnitCd()
    {
        return durationUnitCd;
    }

   /**
   Sets the value of the durationUnitCd property.

   @param aDurationUnitCd the new value of the durationUnitCd property
    */
    public void setDurationUnitCd(String aDurationUnitCd)
    {
        durationUnitCd = aDurationUnitCd;
        setItDirty(true);
    }

   /**
   Access method for the firstNm property.

   @return   the current value of the firstNm property
    */
    public String getFirstNm()
    {
        return firstNm;
    }

   /**
   Sets the value of the firstNm property.

   @param aFirstNm the new value of the firstNm property
    */
    public void setFirstNm(String aFirstNm)
    {
        firstNm = aFirstNm;
        setItDirty(true);
    }

   /**
   Access method for the firstNmSndx property.

   @return   the current value of the firstNmSndx property
    */
    public String getFirstNmSndx()
    {
        return firstNmSndx;
    }

   /**
   Sets the value of the firstNmSndx property.

   @param aFirstNmSndx the new value of the firstNmSndx property
    */
    public void setFirstNmSndx(String aFirstNmSndx)
    {
        firstNmSndx = aFirstNmSndx;
        setItDirty(true);
    }

   /**
   Access method for the fromTime property.

   @return   the current value of the fromTime property
    */
    public Timestamp getFromTime()
    {
        return fromTime;
    }

   /**
   Sets the value of the fromTime property.

   @param aFromTime the new value of the fromTime property
    */
    public void setFromTime(Timestamp aFromTime)
    {
        fromTime = aFromTime;
        setItDirty(true);
    }

   /**
   Sets the value of the fromTime property.
    takes a String and converts to Timestamp
   @param aFromTime the new value of the fromTime property
    */
   public void setFromTime_s(String strTime)
   {
      if (strTime == null) return;
      this.setFromTime(StringUtils.stringToStrutsTimestamp(strTime));
   }

   /**
   Access method for the lastChgReasonCd property.

   @return   the current value of the lastChgReasonCd property
    */
    public String getLastChgReasonCd()
    {
        return lastChgReasonCd;
    }

   /**
   Sets the value of the lastChgReasonCd property.

   @param aLastChgReasonCd the new value of the lastChgReasonCd property
    */
    public void setLastChgReasonCd(String aLastChgReasonCd)
    {
        lastChgReasonCd = aLastChgReasonCd;
        setItDirty(true);
    }

   /**
   Access method for the lastChgTime property.

   @return   the current value of the lastChgTime property
    */
    public Timestamp getLastChgTime()
    {
        return lastChgTime;
    }

   /**
   Sets the value of the lastChgTime property.

   @param aLastChgTime the new value of the lastChgTime property
    */
    public void setLastChgTime(Timestamp aLastChgTime)
    {
        lastChgTime = aLastChgTime;
        setItDirty(true);
    }

   /**
   Sets the value of the lastChgTime property.
    takes a String and converts it to Timestamp
   @param aLastChgTime the new value of the lastChgTime property
    */
   public void setLastChgTime_s(String strTime)
   {
      if (strTime == null) return;
      this.setLastChgTime(StringUtils.stringToStrutsTimestamp(strTime));
   }

   /**
   Access method for the lastChgUserId property.

   @return   the current value of the lastChgUserId property
    */
    public Long getLastChgUserId()
    {
        return lastChgUserId;
    }

   /**
   Sets the value of the lastChgUserId property.

   @param aLastChgUserId the new value of the lastChgUserId property
    */
    public void setLastChgUserId(Long aLastChgUserId)
    {
        lastChgUserId = aLastChgUserId;
        setItDirty(true);
    }

   /**
   Access method for the lastNm property.

   @return   the current value of the lastNm property
    */
    public String getLastNm()
    {
        return lastNm;
    }

   /**
   Sets the value of the lastNm property.

   @param aLastNm the new value of the lastNm property
    */
    public void setLastNm(String aLastNm)
    {
        lastNm = aLastNm;
        setItDirty(true);
    }

   /**
   Access method for the lastNmSndx property.

   @return   the current value of the lastNmSndx property
    */
    public String getLastNmSndx()
    {
        return lastNmSndx;
    }

   /**
   Sets the value of the lastNmSndx property.

   @param aLastNmSndx the new value of the lastNmSndx property
    */
    public void setLastNmSndx(String aLastNmSndx)
    {
        lastNmSndx = aLastNmSndx;
        setItDirty(true);
    }

   /**
   Access method for the lastNm2 property.

   @return   the current value of the lastNm2 property
    */
    public String getLastNm2()
    {
        return lastNm2;
    }

   /**
   Sets the value of the lastNm2 property.

   @param aLastNm2 the new value of the lastNm2 property
    */
    public void setLastNm2(String aLastNm2)
    {
        lastNm2 = aLastNm2;
        setItDirty(true);
    }

   /**
   Access method for the lastNm2Sndx property.

   @return   the current value of the lastNm2Sndx property
    */
    public String getLastNm2Sndx()
    {
        return lastNm2Sndx;
    }

   /**
   Sets the value of the lastNm2Sndx property.

   @param aLastNm2Sndx the new value of the lastNm2Sndx property
    */
    public void setLastNm2Sndx(String aLastNm2Sndx)
    {
        lastNm2Sndx = aLastNm2Sndx;
        setItDirty(true);
    }

   /**
   Access method for the middleNm property.

   @return   the current value of the middleNm property
    */
    public String getMiddleNm()
    {
        return middleNm;
    }

   /**
   Sets the value of the middleNm property.

   @param aMiddleNm the new value of the middleNm property
    */
    public void setMiddleNm(String aMiddleNm)
    {
        middleNm = aMiddleNm;
        setItDirty(true);
    }

   /**
   Access method for the middleNm2 property.

   @return   the current value of the middleNm2 property
    */
    public String getMiddleNm2()
    {
        return middleNm2;
    }

   /**
   Sets the value of the middleNm2 property.

   @param aMiddleNm2 the new value of the middleNm2 property
    */
    public void setMiddleNm2(String aMiddleNm2)
    {
        middleNm2 = aMiddleNm2;
        setItDirty(true);
    }

   /**
   Access method for the nmDegree property.

   @return   the current value of the nmDegree property
    */
    public String getNmDegree()
    {
        return nmDegree;
    }

   /**
   Sets the value of the nmDegree property.

   @param aNmDegree the new value of the nmDegree property
    */
    public void setNmDegree(String aNmDegree)
    {
        nmDegree = aNmDegree;
        setItDirty(true);
    }

   /**
   Access method for the nmPrefix property.

   @return   the current value of the nmPrefix property
    */
    public String getNmPrefix()
    {
        return nmPrefix;
    }

   /**
   Sets the value of the nmPrefix property.

   @param aNmPrefix the new value of the nmPrefix property
    */
    public void setNmPrefix(String aNmPrefix)
    {
        nmPrefix = aNmPrefix;
        setItDirty(true);
    }

   /**
   Access method for the nmSuffix property.

   @return   the current value of the nmSuffix property
    */
    public String getNmSuffix()
    {
        return nmSuffix;
    }

   /**
   Sets the value of the nmSuffix property.

   @param aNmSuffix the new value of the nmSuffix property
    */
    public void setNmSuffix(String aNmSuffix)
    {
        nmSuffix = aNmSuffix;
        setItDirty(true);
    }

   /**
   Access method for the nmSuffixCd property.

   @return   the current value of the nmSuffixCd property
    */
     public String getNmSuffixCd()
    {
        return nmSuffixCd;
    }

   /**
   Sets the value of the nmSuffixCd property.

   @param aNmSuffixCd the new value of the nmSuffixCd property
    */
    public void setNmSuffixCd(String aNmSuffixCd)
    {
        nmSuffixCd = aNmSuffixCd;
        setItDirty(true);
    }

   /**
   Access method for the nmUseCd property.

   @return   the current value of the nmUseCd property
    */
    public String getNmUseCd()
    {
        return nmUseCd;
    }

   /**
   Sets the value of the nmUseCd property.

   @param aNmUseCd the new value of the nmUseCd property
    */
    public void setNmUseCd(String aNmUseCd)
    {
        nmUseCd = aNmUseCd;
        setItDirty(true);
    }

   /**
   Access method for the recordStatusCd property.

   @return   the current value of the recordStatusCd property
    */
    public String getRecordStatusCd()
    {
        return recordStatusCd;
    }

   /**
   Sets the value of the recordStatusCd property.

   @param aRecordStatusCd the new value of the recordStatusCd property
    */
    public void setRecordStatusCd(String aRecordStatusCd)
    {
        recordStatusCd = aRecordStatusCd;
        setItDirty(true);
    }

   /**
   Access method for the recordStatusTime property.

   @return   the current value of the recordStatusTime property
    */
    public Timestamp getRecordStatusTime()
    {
        return recordStatusTime;
    }

   /**
   Sets the value of the recordStatusTime property.

   @param aRecordStatusTime the new value of the recordStatusTime property
    */
    public void setRecordStatusTime(Timestamp aRecordStatusTime)
    {
        recordStatusTime = aRecordStatusTime;
        setItDirty(true);
    }

   /**
   Sets the value of the recordStatusTime property.
    takes String and converts to a Timestring
   @param aRecordStatusTime the new value of the recordStatusTime property
    */
   public void setRecordStatusTime_s(String strTime)
   {
      if (strTime == null) return;
      this.setRecordStatusTime(StringUtils.stringToStrutsTimestamp(strTime));
   }

   /**
   Access method for the statusCd property.

   @return   the current value of the statusCd property
    */
    public String getStatusCd()
    {
        return statusCd;
    }

   /**
   Sets the value of the statusCd property.

   @param aStatusCd the new value of the statusCd property
    */
    public void setStatusCd(String aStatusCd)
    {
        statusCd = aStatusCd;
        setItDirty(true);
    }

   /**
   Access method for the statusTime property.

   @return   the current value of the statusTime property
    */
    public Timestamp getStatusTime()
    {
        return statusTime;
    }

   /**
   Sets the value of the statusTime property.

   @param aStatusTime the new value of the statusTime property
    */
    public void setStatusTime(Timestamp aStatusTime)
    {
        statusTime = aStatusTime;
        setItDirty(true);
    }

   /**
   Sets the value of the statusTime property.
    takes a String and converts to a Timestamp
   @param aStatusTime the new value of the statusTime property
    */
   public void setStatusTime_s(String strTime)
   {
      if (strTime == null) return;
      this.setStatusTime(StringUtils.stringToStrutsTimestamp(strTime));
   }

   /**
   Access method for the toTime property.

   @return   the current value of the toTime property
    */
    public Timestamp getToTime()
    {
        return toTime;
    }

   /**
   Sets the value of the toTime property.

   @param aToTime the new value of the toTime property
    */
    public void setToTime(Timestamp aToTime)
    {
        toTime = aToTime;
        setItDirty(true);
    }

   /**
   Sets the value of the toTime property.
    takes a String and converts to Timestamp
   @param aToTime the new value of the toTime property
    */
   public void setToTime_s(String strTime)
   {
      if (strTime == null) return;
      this.setToTime(StringUtils.stringToStrutsTimestamp(strTime));
   }

   /**
   Access method for the userAffiliationTxt property.

   @return   the current value of the userAffiliationTxt property
    */
    public String getUserAffiliationTxt()
    {
        return userAffiliationTxt;
    }

   /**
   Sets the value of the userAffiliationTxt property.

   @param aUserAffiliationTxt the new value of the userAffiliationTxt property
    */
    public void setUserAffiliationTxt(String aUserAffiliationTxt)
    {
        userAffiliationTxt = aUserAffiliationTxt;
        setItDirty(true);
    }

   /**
   Access method for the progAreaCd property.

   @return   the current value of the progAreaCd property
    */
   public String getProgAreaCd()
   {
      return progAreaCd;
   }

   /**
   Sets the value of the progAreaCd property.

   @param aProgAreaCd the new value of the progAreaCd property
    */
   public void setProgAreaCd(String aProgAreaCd)
   {
      progAreaCd = aProgAreaCd;
      setItDirty(true);
   }

   /**
   Access method for the jurisdictionCd property.

   @return   the current value of the jurisdictionCd property
    */
   public String getJurisdictionCd ()
   {
      return jurisdictionCd ;
   }

   /**
   Sets the value of the jurisdictionCd property.

   @param aJurisdictionCd the new value of the jurisdictionCd property
    */
   public void setJurisdictionCd (String aJurisdictionCd )
   {
      jurisdictionCd = aJurisdictionCd;
      setItDirty(true);
   }

   /**
   Access method for the programJurisdictionOid property.

   @return   the current value of the programJurisdictionOid property
    */
   public Long getProgramJurisdictionOid ()
   {
      return programJurisdictionOid;
   }

   /**
   Sets the value of the programJurisdictionOid property.

   @param aProgramJurisdictionOid the new value of the programJurisdictionOid property
    */
   public void setProgramJurisdictionOid (Long aProgramJurisdictionOid )
   {
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
     * @return boolean
     */
    public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass) {
    voClass =  (( PersonNameDT) objectname1).getClass();
    NedssUtils compareObjs = new NedssUtils();
    return (compareObjs.equals(objectname1,objectname2,voClass));
     }



    /**
     * Marks this object as a class that has been modified.
     * @param itDirty
     */
    public void setItDirty(boolean itDirty) {
      this.itDirty = itDirty;
    }

    /**
     * Returns boolean indicating if this object has been modified.
     * @return boolean itDirty
     */
    public boolean isItDirty() {

        return itDirty;
    }

    /**
     * Sets boolean value indicating if this object is new and that it does not
     * exist in the database.
     * @param itNew
     */
    public void setItNew(boolean itNew) {
      this.itNew = itNew;
    }

    /**
     * Returns boolean indicating if this is a new object.
     * @return itNew  boolean
     */
    public boolean isItNew() {

        return itNew;
    }

    /**
     * Marks this object for deletion from the database.s
     * @param itDelete
     */
    public void setItDelete(boolean itDelete) {
      this.itDelete = itDelete;
    }

    /**
     * Returns boolean indicating if object is marked for deletion from
     * database.
     * @return itDelete boolean
     */
    public boolean isItDelete() {

        return itDelete;
    }

    /**
     * returns an Object that is a complete copy of this object
     * @return Object deepCopy
     */
    public Object deepCopy() throws CloneNotSupportedException, IOException, ClassNotFoundException
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(this);
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);
        Object deepCopy = ois.readObject();

        return  deepCopy;
    }
    /**
     *
     * @return Integer
     */
    public Integer getVersionCtrlNbr() {

        return versionCtrlNbr;
    }
    /**
     *
     * @return Long
     */
    public Long getUid() {

        return personUid;
    }
    /**
     *
     * @return String
     */
    public String getSuperclass() {

        return NEDSSConstants.CLASSTYPE_ENTITY;
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
     * @return String
     */
    public String getLocalId() {

        return localId;
    }
    
    public String getFullName()
    {
        String n = StringUtils.combine(new String[]{ getLastNm(),  getFirstNm() }, ",");
        String n1 = StringUtils.combine(new String[]{n, getMiddleNm()}," " );
        return StringUtils.combine(new String[]{n1, getNmSuffix()},"," );
    }

}
