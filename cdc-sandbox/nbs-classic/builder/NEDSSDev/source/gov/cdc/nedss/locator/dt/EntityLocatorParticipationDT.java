/**
* Name:		    EntityLocatorParticipationDT
* Description:	EntityLocatorParticipation data table object
* Copyright:	Copyright (c) 2002
* Company: 	    Computer Sciences Corporation
* @author	    NEDSS Development Team
* @version	    1.0
*/

package gov.cdc.nedss.locator.dt;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.ArrayList;
import java.io.*;
import  gov.cdc.nedss.util.*;
import gov.cdc.nedss.association.dt.*;
import gov.cdc.nedss.systemservice.util.*;

public class EntityLocatorParticipationDT extends AbstractVO implements java.io.Serializable,AssocDTInterface
{
   private static final long serialVersionUID = 1L;
	
   private Long locatorUid;
   private String addReasonCd;
   private Timestamp addTime;
   private Long addUserId;
   private Timestamp asOfDate;
   private String cd;
   private String cdDescTxt;
   private String classCd;
   private String durationAmt;
   private String durationUnitCd;
   private Timestamp fromTime;
   private String lastChgReasonCd;
   private Timestamp lastChgTime;
   private Long lastChgUserId;
   private String locatorDescTxt;
   private String recordStatusCd;
   private Timestamp recordStatusTime;
   private String statusCd;
   private Timestamp statusTime;
   private Timestamp toTime;
   private String useCd;
   private String userAffiliationTxt;
   private String validTimeTxt;
   private Long entityUid;
   private PostalLocatorDT thePostalLocatorDT;
   private PhysicalLocatorDT thePhysicalLocatorDT;
   private TeleLocatorDT theTeleLocatorDT;
   private Integer versionCtrlNbr;

   /**
   @roseuid 3BB9CB1101CE
    */
   public EntityLocatorParticipationDT()
   {
      setItNew(true);
   }

   public EntityLocatorParticipationDT(
    Long locatorUid,
    String addReasonCd,
    Timestamp addTime,
    Long addUserId,
    Timestamp asOfDate,
    String cd,
    String cdDescTxt,
    String classCd,
    String durationAmt,
    String durationUnitCd,
    Timestamp fromTime,
    String lastChgReasonCd,
    Timestamp lastChgTime,
    Long lastChgUserId,
    String locatorDescTxt,
    String recordStatusCd,
    Timestamp recordStatusTime,
    String statusCd,
    Timestamp statusTime,
    Timestamp toTime,
    String useCd,
    String userAffiliationTxt,
    String validTimeTxt,
    Long entityUid,
    PostalLocatorDT thePostalLocatorDT,
    PhysicalLocatorDT thePhysicalLocatorDT,
    TeleLocatorDT theTeleLocatorDT
   )
   {
   this.locatorUid=locatorUid;
   this.addReasonCd=addReasonCd;
   this.addTime=addTime;
   this.asOfDate=asOfDate;
   this.addUserId=addUserId;
   this.cd=cd;
   this.cdDescTxt=cdDescTxt;
   this.classCd=classCd;
   this.durationAmt=durationAmt;
   this.durationUnitCd=durationUnitCd;
   this.fromTime=fromTime;
   this.lastChgReasonCd=lastChgReasonCd;
   this.lastChgTime=lastChgTime;
   this.lastChgUserId=lastChgUserId;
   this.locatorDescTxt=locatorDescTxt;
   this.recordStatusCd=recordStatusCd;
   this.recordStatusTime=recordStatusTime;
   this.statusCd=statusCd;
   this.statusTime=statusTime;
   this.toTime=toTime;
   this.useCd=useCd;
   this.userAffiliationTxt=userAffiliationTxt;
   this.validTimeTxt=validTimeTxt;
   this.entityUid=entityUid;
   this.thePostalLocatorDT=thePostalLocatorDT;
   this.thePhysicalLocatorDT=thePhysicalLocatorDT;
   this.theTeleLocatorDT=theTeleLocatorDT;
   setItNew(true);
   }

   /**
    * Access method for the locatorUid property.
    * @return   the current value of the locatorUid property
    */
   public Long getLocatorUid()
   {
      return locatorUid;
   }

   /**
    * Sets the value of the locatorUid property.
    * @param aLocatorUid the new value of the locatorUid property
    */
   public void setLocatorUid(Long aLocatorUid)
   {
      locatorUid = aLocatorUid;
      itDirty = true;
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
      itDirty = true;
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
      itDirty = true;
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
      itDirty = true;
   }

	/**
 	* gets AsOfDate
 	* @return Timestamp
 	*/
	public Timestamp getAsOfDate(){
		return this.asOfDate;
	}

	/**
	 * sets AsOfDate
	 * @param aAsOfDate
	 */
	public void setAsOfDate(Timestamp aAsOfDate){
		this.asOfDate = aAsOfDate;
		setItDirty(true);
	}

    /**
     *
     * @param strTime
     */
    public void setAsOfDate_s(String strTime) {

        if (strTime == null)

            return;

        this.setAsOfDate(StringUtils.stringToStrutsTimestamp(strTime));
    }



   /**
   Access method for the cd property.

   @return   the current value of the cd property
    */
   public String getCd()
   {
      return cd;
   }

   /**
   Sets the value of the cd property.

   @param aCd the new value of the cd property
    */
   public void setCd(String aCd)
   {
      cd = aCd;
      itDirty = true;
   }

   /**
   Access method for the cdDescTxt property.

   @return   the current value of the cdDescTxt property
    */
   public String getCdDescTxt()
   {
      return cdDescTxt;
   }

   /**
   Sets the value of the cdDescTxt property.

   @param aCdDescTxt the new value of the cdDescTxt property
    */
   public void setCdDescTxt(String aCdDescTxt)
   {
      cdDescTxt = aCdDescTxt;
      itDirty = true;
   }

   /**
   Access method for the classCd property.

   @return   the current value of the classCd property
    */
   public String getClassCd()
   {
      return classCd;
   }

   /**
   Sets the value of the classCd property.

   @param aClassCd the new value of the classCd property
    */
   public void setClassCd(String aClassCd)
   {
      classCd = aClassCd;
      itDirty = true;
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
      itDirty = true;
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
      itDirty = true;
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
      itDirty = true;
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
      itDirty = true;
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
      itDirty = true;
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
      itDirty = true;
   }

   /**
   Access method for the locatorDescTxt property.

   @return   the current value of the locatorDescTxt property
    */
   public String getLocatorDescTxt()
   {
      return locatorDescTxt;
   }

   /**
   Sets the value of the locatorDescTxt property.

   @param aLocatorDescTxt the new value of the locatorDescTxt property
    */
   public void setLocatorDescTxt(String aLocatorDescTxt)
   {
      locatorDescTxt = aLocatorDescTxt;
      itDirty = true;
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
      itDirty = true;
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
      itDirty = true;
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
      itDirty = true;
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
      itDirty = true;
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
      itDirty = true;
   }

   /**
   Access method for the useCd property.

   @return   the current value of the useCd property
    */
   public String getUseCd()
   {
      return useCd;
   }

   /**
   Sets the value of the useCd property.

   @param aUseCd the new value of the useCd property
    */
   public void setUseCd(String aUseCd)
   {
      useCd = aUseCd;
      itDirty = true;
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
      itDirty = true;
   }

   /**
   Access method for the validTimeTxt property.

   @return   the current value of the validTimeTxt property
    */
   public String getValidTimeTxt()
   {
      return validTimeTxt;
   }

   /**
   Sets the value of the validTimeTxt property.

   @param aValidTimeTxt the new value of the validTimeTxt property
    */
   public void setValidTimeTxt(String aValidTimeTxt)
   {
      validTimeTxt = aValidTimeTxt;
      itDirty = true;
   }

   /**
   Access method for the entityUid property.

   @return   the current value of the entityUid property
    */
   public Long getEntityUid()
   {
      return entityUid;
   }

   /**
   Sets the value of the entityUid property.

   @param aEntityUid the new value of the entityUid property
    */
   public void setEntityUid(Long aEntityUid)
   {
      entityUid = aEntityUid;
      itDirty = true;
   }

   /**
    * getVersionCtrlNbr
    * @return Integer versionCtrlNbr
    */
   public Integer getVersionCtrlNbr()
    {
        return versionCtrlNbr;
    }

    /**
     * setVersionCtrlNbr
     * @param Integer aVersionCtrlNbr
     */
    public void setVersionCtrlNbr(Integer aVersionCtrlNbr)
    {
        versionCtrlNbr = aVersionCtrlNbr;
        setItDirty(true);
    }

   /**
   @param objectname1
   @param objectname2
   @param voClass
   @return boolean
   @roseuid 3BB9CB110232
    */
   public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass)
   {
      voClass =  ((EntityLocatorParticipationDT) objectname1).getClass();
      NedssUtils compareObjs = new NedssUtils();
      return (compareObjs.equals(objectname1,objectname2,voClass));
   }

   /**
   @param itDirty
   @roseuid 3BB9CB11039A
    */
   public void setItDirty(boolean itDirty)
   {
      this.itDirty = itDirty;

   }

   /**
   @return boolean
   @roseuid 3BB9CB120048
    */
   public boolean isItDirty()
   {
    return itDirty;
   }

   /**
   @param itNew
   @roseuid 3BB9CB120085
    */
   public void setItNew(boolean itNew)
   {
        this.itNew = itNew;
   }


   /**
   @return boolean
   @roseuid 3BB9CB12011B
    */
   public boolean isItNew()
   {
    return itNew;
   }

   /**
    * isItDelete
    * @return boolean itDelete
    */
    public boolean isItDelete()
   {
    return itDelete;
   }

   /**
    * setItDelete
    * @param boolean itDelete
    */
    public void setItDelete(boolean itDelete)
   {
        this.itDelete = itDelete;
   }

    /**
     * getThePhysicalLocatorDT
     * @return PhysicalLocatorDT thePhysicalLocatorDT
     */
  public PhysicalLocatorDT getThePhysicalLocatorDT() {
    return thePhysicalLocatorDT;
  }


  /**
   * setThePhysicalLocatorDT
   * @param PhysicalLocatorDT thePhysicalLocatorDT
   */
  public void setThePhysicalLocatorDT(PhysicalLocatorDT thePhysicalLocatorDT) {
    this.thePhysicalLocatorDT = thePhysicalLocatorDT;
    itDirty = true;
  }

  /**
   * getThePostalLocatorDT
   * @return PostalLocatorDT thePostalLocatorDT
   */
  public PostalLocatorDT getThePostalLocatorDT() {
    return thePostalLocatorDT;
  }

  /**
   * setThePostalLocatorDT
   * @param PostalLocatorDT thePostalLocatorDT
   */
  public void setThePostalLocatorDT(PostalLocatorDT thePostalLocatorDT) {
    this.thePostalLocatorDT = thePostalLocatorDT;
    itDirty = true;
  }

  /**
   * getTheTeleLocatorDT
   * @return TeleLocatorDT theTeleLocatorDT
   */
  public TeleLocatorDT getTheTeleLocatorDT() {
    return theTeleLocatorDT;
  }

  /**
   * setTheTeleLocatorDT
   * @param TeleLocatorDT theTeleLocatorDT
   */
  public void setTheTeleLocatorDT(TeleLocatorDT theTeleLocatorDT) {
    this.theTeleLocatorDT = theTeleLocatorDT;
    itDirty = true;
  }

     /*
    *  time is passed as a string, convert into a timestamp
    */
   public void setAddTime_s(String strTime)
   {
      if (strTime == null) return;
      this.setAddTime(StringUtils.stringToStrutsTimestamp(strTime));
   }

     /*
    *  time is passed as a string, convert into a timestamp
    */
   public void setFromTime_s(String strTime)
   {
      if (strTime == null) return;
      this.setFromTime(StringUtils.stringToStrutsTimestamp(strTime));
   }

     /*
    *  time is passed as a string, convert into a timestamp
    */
   public void setLastChgTime_s(String strTime)
   {
      if (strTime == null) return;
      this.setLastChgTime(StringUtils.stringToStrutsTimestamp(strTime));
   }

     /*
    *  time is passed as a string, convert into a timestamp
    */
   public void setRecordStatusTime_s(String strTime)
   {
      if (strTime == null) return;
      this.setRecordStatusTime(StringUtils.stringToStrutsTimestamp(strTime));
   }

     /*
    *  time is passed as a string, convert into a timestamp
    */
   public void setStatusTime_s(String strTime)
   {
      if (strTime == null) return;
      this.setStatusTime(StringUtils.stringToStrutsTimestamp(strTime));
   }

     /*
    *  time is passed as a string, convert into a timestamp
    */
   public void setToTime_s(String strTime)
   {
      if (strTime == null) return;
      this.setToTime(StringUtils.stringToStrutsTimestamp(strTime));
   }

   /**
    * getTheTeleLocatorDT_s
    * @return TeleLocatorDT theTeleLocatorDT_s
    */
  public TeleLocatorDT getTheTeleLocatorDT_s() {
    if (this.theTeleLocatorDT == null)
        this.theTeleLocatorDT = new TeleLocatorDT();
    this.setClassCd("TELE");
    return theTeleLocatorDT;
  }
  // Struts only -- get with object create
  /**
   * getThePostalLocatorDT_s
   * @return PostalLocatorDT thePostalLocatorDT_s
   */
  public PostalLocatorDT getThePostalLocatorDT_s() {
    if (this.thePostalLocatorDT == null)
        this.thePostalLocatorDT = new PostalLocatorDT();
    this.setClassCd("PST");
    return thePostalLocatorDT;
  }
  // Struts only -- get with object create
  /**
   * getThePhysicalLocatorDT_s
   * @return PhysicalLocatorDT thePhysicalLocatorDT_s
   */
  public PhysicalLocatorDT getThePhysicalLocatorDT_s() {
    if (this.thePhysicalLocatorDT == null)
        this.thePhysicalLocatorDT = new PhysicalLocatorDT();
    this.setClassCd("PHYS");
    return thePhysicalLocatorDT;
  }

  /**
   * getTheLocatorDT
   * @return AbstractVO whatever the LocatorDT is
   */
  public AbstractVO getTheLocatorDT() {

    if (classCd.equals("TELE") ) {

     return getTheTeleLocatorDT();

    } else if (classCd.equals("PST")) {

      return getThePostalLocatorDT();

    } else if (classCd.equals("PHYS")) {

      return getThePhysicalLocatorDT();

    } else {
      return null;
    }
  }

   /**
    * deepCopy
    * @throw CloneNotSupportedException
    * @throw IOException
    * @throw ClassNotFoundException
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

}
