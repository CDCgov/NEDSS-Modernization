   package gov.cdc.nedss.locator.dt;

   import java.sql.Timestamp;

   import  gov.cdc.nedss.util.*;
   import gov.cdc.nedss.systemservice.util.*;
 /**
    * Name:	    ActivityLocatorParticipationDT.java
    * Description:  This is the value object for holding activity locator participation attributes.
    * Copyright:	Copyright (c) 2001
    * Company: 	Computer Sciences Corporation
    * @author	Keith Welch  Matthew Pease
    * @version	1.0
    */


    public class ActivityLocatorParticipationDT extends AbstractVO implements AssocDTInterface {
   	private static final long serialVersionUID = 1L;
    private Long actUid;
    private Long locatorUid;
    private Long entityUid;
    private String addReasonCd;
    private Timestamp addTime;
    private Long addUserId;
    private String durationAmt;
    private String durationUnitCd;
    private Timestamp fromTime;
    private String lastChgReasonCd;
    private Timestamp lastChgTime;
    private Long lastChgUserId;
    private String recordStatusCd;
    private Timestamp recordStatusTime;
    private Timestamp toTime;
    private String statusCd;
    private Timestamp statusTime;
    private String typeCd;
    private String typeDescTrxt;
    private String userAffiliationTxt;

    //private PostalLocatorDT thePostalLocatorDT;
    //private PhysicalLocatorDT thePhysicalLocatorDT;
    //private TeleLocatorDT theTeleLocatorDT;

    /**
     * Empty constructor
     */
     public ActivityLocatorParticipationDT() {}


   /**
     * Construct a new dependent object instance.
     */
    public ActivityLocatorParticipationDT(Long actUid, Long locatorUid, Long entityUid, String addReasonCd, Timestamp addTime, Long addUserId, String durationAmt, String durationUnitCd, Timestamp fromTime, String lastChgReasonCd, Timestamp lastChgTime, Long lastChgUserId, String recordStatusCd, Timestamp recordStatusTime, Timestamp toTime, String statusCd, Timestamp statusTime, String typeCd, String typeDescTrxt, String userAffiliationTxt
                                          //PostalLocatorDT thePostalLocatorDT,
                                          //PhysicalLocatorDT thePhysicalLocatorDT,
                                          //TeleLocatorDT theTeleLocatorDT
                                          )
    {
        this.actUid = actUid;
        this.locatorUid = locatorUid;
        this.entityUid = entityUid;
        this.addReasonCd = addReasonCd;
        this.addTime = addTime;
        this.addUserId = addUserId;
        this.durationAmt = durationAmt;
        this.durationUnitCd = durationUnitCd;
        this.fromTime = fromTime;
        this.lastChgReasonCd = lastChgReasonCd;
        this.lastChgTime = lastChgTime;
        this.lastChgUserId = lastChgUserId;
        this.recordStatusCd = recordStatusCd;
        this.recordStatusTime = recordStatusTime;
        this.toTime = toTime;
        this.statusCd = statusCd;
        this.statusTime = statusTime;
        this.typeCd = typeCd;
        this.typeDescTrxt = typeDescTrxt;
        this.userAffiliationTxt = userAffiliationTxt;
        //this.thePostalLocatorDT = thePostalLocatorDT;
        //this.thePhysicalLocatorDT = thePhysicalLocatorDT;
        //this.theTeleLocatorDT = theTeleLocatorDT;
    }

    /**
     * getter method for ActUid
     * @return    Long value representing ActUid
     */
    public Long getActUid() {
        return this.actUid;
    }

    /**
     * getter method for LocatorUid
     * @return    Long value representing LocatorUid
     */
    public Long getLocatorUid() {
        return this.locatorUid;
    }

    /**
     * getter method for EntityUid
     * @return    Long value representing EntityUid
     */
    public Long getEntityUid() {
        return this.entityUid;
    }

    /**
     * getter method for AddReasonCd
     * @return    String value representing AddReasonCd
     */
    public String getAddReasonCd() {
        return this.addReasonCd;
    }

    /**
     * getter method for AddTime
     * @return    Timestamp value representing AddTime
     */
    public Timestamp getAddTime() {
        return this.addTime;
    }

    /**
     * getter method for AddUserId
     * @return    Long value representing AddUserId
     */
    public Long getAddUserId() {
        return this.addUserId;
    }

    /**
     * getter method for DurationAmt
     * @return    String value representing DurationAmt
     */
    public String getDurationAmt() {
        return this.durationAmt;
    }

    /**
     * getter method for DurationUnitCd
     * @return    String value representing DurationUnitCd
     */
    public String getDurationUnitCd() {
        return this.durationUnitCd;
    }

    /**
     * getter method for FromTime
     * @return    Timestamp value representing FromTime
     */
    public Timestamp getFromTime() {
        return this.fromTime;
    }

    /**
     * getter method for LastChgReasonCd
     * @return    String value representing LastChgReasonCd
     */
    public String getLastChgReasonCd() {
        return this.lastChgReasonCd;
    }

    /**
     * getter method for LastChgTime
     * @return    Timestamp value representing LastChgTime
     */
    public Timestamp getLastChgTime() {
        return this.lastChgTime;
    }

    /**
     * getter method for LastChgUserId
     * @return    Long value representing LastChgUserId
     */
    public Long getLastChgUserId() {
        return this.lastChgUserId;
    }

    /**
     * getter method for RecordStatusCd
     * @return    String value representing RecordStatusCd
     */
    public String getRecordStatusCd() {
        return this.recordStatusCd;
    }

    /**
     * getter method for RecordStatusTime
     * @return    Timestamp value representing RecordStatusTime
     */
    public Timestamp getRecordStatusTime() {
        return this.recordStatusTime;
    }

    /**
     * getter method for ToTime
     * @return    Timestamp value representing ToTime
     */
    public Timestamp getToTime() {
        return this.toTime;
    }

    /**
     * getter method for StatusCd
     * @return    String value representing StatusCd
     */
    public String getStatusCd() {
        return this.statusCd;
    }

    /**
     * getter method for StatusTime
     * @return    Timestamp value representing StatusTime
     */
    public Timestamp getStatusTime() {
        return this.statusTime;
    }

    /**
     * getter method for TypeCd
     * @return    String value representing TypeCd
     */
    public String getTypeCd() {
        return this.typeCd;
    }

    /**
     * getter method for TypeDescTxt
     * @return    String value representing TypeDescTxt
     */
    public String getTypeDescTxt() {
        return this.typeDescTrxt;
    }

    /**
     * getter method for UserAffiliationTxt
     * @return    String value representing UserAffiliationTxt
     */
    public String getUserAffiliationTxt() {
        return this.userAffiliationTxt;
    }


    /**
    *sets ActUid
    * @param val : Long value
    */
    public void  setActUid(Long val) {
        this.actUid = val;
    }

    /**
    *sets LocatorUid
    * @param val : Long value
    */
    public void  setLocatorUid(Long val) {
        this.locatorUid = val;
    }

    /**
    *sets ActUid
    * @param val : Long value
    */
    public void  setEntityUid(Long val) {
        this.entityUid = val;
    }

    /**
    *sets AddReasonCd
    * @param val : String value
    */
    public void  setAddReasonCd(String val) {
        this.addReasonCd = val;
        setItDirty(true);
    }

    /**
    *sets AddTime
    * @param val : Timestamp value
    */
    public void  setAddTime(Timestamp val) {
        this.addTime = val;
        setItDirty(true);
    }

    /**
    *sets AddUserId
    * @param val : Long value
    */
    public void  setAddUserId(Long val) {
        this.addUserId = val;
        setItDirty(true);
    }

    /**
    *sets DurationAmt
    * @param val : String value
    */
    public void  setDurationAmt(String val) {
        this.durationAmt = val;
        setItDirty(true);
    }

    /**
    *sets DurationUnitCd
    * @param val : String value
    */
    public void  setDurationUnitCd(String val) {
        this.durationUnitCd = val;
        setItDirty(true);
    }

    /**
    *sets FromTime
    * @param val : Timestamp value
    */
    public void  setFromTime(Timestamp val) {
        this.fromTime = val;
        setItDirty(true);
    }

    /**
    *sets LastChgReasonCd
    * @param val : String value
    */
    public void  setLastChgReasonCd(String val) {
        this.lastChgReasonCd = val;
        setItDirty(true);
    }

    /**
    *sets LastChgTime
    * @param val : Timestamp value
    */
    public void  setLastChgTime(Timestamp val) {
        this.lastChgTime = val;
        setItDirty(true);
    }

    /**
    *sets LastChgUserId
    * @param val : Long value
    */
    public void  setLastChgUserId(Long val) {
        this.lastChgUserId = val;
        setItDirty(true);
    }

    /**
    *sets RecordStatusCd
    * @param val : String value
    */
    public void  setRecordStatusCd(String val) {
        this.recordStatusCd = val;
        setItDirty(true);
    }

    /**
    *sets RecordStatusTime
    * @param val : Timestamp value
    */
    public void  setRecordStatusTime(Timestamp val) {
        this.recordStatusTime = val;
        setItDirty(true);
    }

    /**
    *sets ToTime
    * @param val : Timestamp value
    */
    public void  setToTime(Timestamp val) {
        this.toTime = val;
        setItDirty(true);
    }

    /**
    *sets StatusCd
    * @param val : String value
    */
    public void  setStatusCd(String val) {
        this.statusCd = val;
        setItDirty(true);
    }

    /**
    *sets StatusTime
    * @param val : Timestamp value
    */
    public void  setStatusTime(Timestamp val) {
        this.statusTime = val;
        setItDirty(true);
    }

    /**
    *sets TypeCd
    * @param val : String value
    */
    public void  setTypeCd(String val) {
        this.typeCd = val;
        setItDirty(true);
    }

    /**
    *sets TypeDescTxt
    * @param val : String value
    */
    public void  setTypeDescTxt(String val) {
        this.typeDescTrxt = val;
        setItDirty(true);
    }

    /**
    *sets UserAffiliationTxt
    * @param val : String value
    */
    public void  setUserAffiliationTxt(String val) {
        this.userAffiliationTxt = val;
        setItDirty(true);
    }

/*    public PhysicalLocatorDT getThePhysicalLocatorDT() {
    return thePhysicalLocatorDT;
  }
  public void setThePhysicalLocatorDT(PhysicalLocatorDT thePhysicalLocatorDT) {
    this.thePhysicalLocatorDT = thePhysicalLocatorDT;
    itDirty = true;
  }
  public PostalLocatorDT getThePostalLocatorDT() {
    return thePostalLocatorDT;
  }
  public void setThePostalLocatorDT(PostalLocatorDT thePostalLocatorDT) {
    this.thePostalLocatorDT = thePostalLocatorDT;
    itDirty = true;
  }
  public TeleLocatorDT getTheTeleLocatorDT() {
    return theTeleLocatorDT;
  }
  public void setTheTeleLocatorDT(TeleLocatorDT theTeleLocatorDT) {
    this.theTeleLocatorDT = theTeleLocatorDT;
    itDirty = true;
  }*/

   /**
    *sets itDelete
    * @param bVal : boolean value
    */
    public void setItDelete(boolean bVal)
   {
    this.itDelete = bVal;
   }

   /**
    *sets itDirty
    * @param bVal : boolean value
    */
    public void setItDirty(boolean bVal)
   {
    this.itDirty = bVal;
   }

   /**
    *sets itNew
    * @param bVal : boolean value
    */
    public void setItNew(boolean bVal)
   {
    this.itNew = bVal;
   }

   /**
    *sets ActUid
    * @return : itDirty boolean value
    */
    public boolean isItDirty()
   {
    return itDirty;
   }

   /**
    *sets ActUid
    * @return : itNew boolean value
    */
    public boolean isItNew()
   {
    return itNew;
    }

   /**
    *gets itDelete
    * @return : itDelete boolean value
    */
    public boolean isItDelete()
   {
    return this.itDelete;
    }

       /**
     * compare to find if two objects are equal
     * @param objectname1 : first object
     * @param objectname2 : second object
     * @param voClass : ((ActivityLocatorParticipationDT) objectname1).getClass
     * @return boolean value
     */
     public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass)
   {
      voClass =  (( ActivityLocatorParticipationDT) objectname1).getClass();
      NedssUtils compareObjs = new NedssUtils();
      return (compareObjs.equals(objectname1,objectname2,voClass));
   }


   /**
    *sets AddTime (convenient struts method)
    * @param strTime : String value
    */
    public void setAddTime_s(String strTime)
   {
      if (strTime == null) return;
      this.setAddTime(StringUtils.stringToStrutsTimestamp(strTime));
   }

    /**
    *sets FromTime (convenient struts method)
    * @param strTime : String value
    */
    public void setFromTime_s(String strTime)
   {
      if (strTime == null) return;
      this.setFromTime(StringUtils.stringToStrutsTimestamp(strTime));
   }

    /**
    *sets LastChgTime (convenient struts method)
    * @param strTime : String value
    */
    public void setLastChgTime_s(String strTime)
   {
     if (strTime == null) return;
     this.setLastChgTime(StringUtils.stringToStrutsTimestamp(strTime));
   }

   /**
    *sets RecordStatusTime (convenient struts method)
    * @param strTime : String value
    */
    public void setRecordStatusTime_s(String strTime)
   {
      if (strTime == null) return;
      this.setRecordStatusTime(StringUtils.stringToStrutsTimestamp(strTime));
   }

   /**
    *sets ToTime (convenient struts method)
    * @param strTime : String value
    */
    public void setToTime_s(String strTime)
   {
      if (strTime == null) return;
      this.setToTime(StringUtils.stringToStrutsTimestamp(strTime));
   }

    /**
    *sets StatusTime (convenient struts method)
    * @param strTime : String value
    */
    public void setStatusTime_s(String strTime)
   {
      if (strTime == null) return;
      this.setStatusTime(StringUtils.stringToStrutsTimestamp(strTime));
   }

}