package gov.cdc.nedss.locator.dt;

import java.sql.Timestamp;
import  gov.cdc.nedss.util.*;

/**
* Name:		    PhysicalLocatorDT
* Description:	PhysicalLocator data table object
* Copyright:	Copyright (c) 2002
* Company: 	    Computer Sciences Corporation
* @author	    NEDSS Development Team
* @version	    1.0
*/
public class PhysicalLocatorDT extends AbstractVO
{
	private static final long serialVersionUID = 1L;
    private Long physicalLocatorUid;
    private String addReasonCd;
    private Timestamp addTime;
    private Long addUserId;
    private byte[] imageTxt;
    private String lastChgReasonCd;
    private Timestamp lastChgTime;
    private Long lastChgUserId;
    private String locatorTxt;
    private String recordStatusCd;
    private Timestamp recordStatusTime;
    private String userAffiliationTxt;
    private String progAreaCd = null;
    private String jurisdictionCd = null;
    private Long programJurisdictionOid = null;
    private String sharedInd = null;
    private boolean itDirty = false;
    private boolean itNew = true;
    private boolean itDelete = false;
    public PhysicalLocatorDT() {}
    public PhysicalLocatorDT(
      Long physicalLocatorUid,
      String addReasonCd,
      Timestamp addTime,
      Long addUserId,
      byte[] imageTxt,
      String lastChgReasonCd,
      Timestamp lastChgTime,
      Long lastChgUserId,
      String locatorTxt,
      String recordStatusCd,
      Timestamp recordStatusTime,
      String userAffiliationTxt
     )
      {
      this.physicalLocatorUid = physicalLocatorUid;
      this.addReasonCd = addReasonCd;
      this.addTime = addTime;
      this.addUserId = addUserId;
      this.imageTxt = imageTxt;
      this.lastChgReasonCd = lastChgReasonCd;
      this.lastChgTime = lastChgTime;
      this.lastChgUserId = lastChgUserId;
      this.locatorTxt = locatorTxt;
      this.recordStatusCd = recordStatusCd;
      this.recordStatusTime = recordStatusTime;
      this.userAffiliationTxt = userAffiliationTxt;
    }

   /**
   Access method for the physicalLocatorUid property.

   @return   the current value of the physicalLocatorUid property
    */
    public Long getPhysicalLocatorUid()
    {
        return physicalLocatorUid;
    }

   /**
   Sets the value of the physicalLocatorUid property.

   @param aPhysicalLocatorUid the new value of the physicalLocatorUid property
    */
    public void setPhysicalLocatorUid(Long aPhysicalLocatorUid)
    {
        physicalLocatorUid = aPhysicalLocatorUid;
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
      itDirty = true;
   }

   /**
   Access method for the imageTxt property.

   @return   the current value of the imageTxt property
    */
   public byte[] getImageTxt()
   {
      return imageTxt;
   }

   /**
   Sets the value of the imageTxt property.

   @param aImageTxt the new value of the imageTxt property
    */
   public void setImageTxt(byte[] aImageTxt)
   {
      imageTxt = aImageTxt;
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
   Sets the value of the lastChgTime property.
    takes aString and converts to Timestamp
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
      itDirty = true;
   }

   /**
   Access method for the locatorTxt property.

   @return   the current value of the locatorTxt property
    */
   public String getLocatorTxt()
   {
      return locatorTxt;
   }

   /**
   Sets the value of the locatorTxt property.

   @param aLocatorTxt the new value of the locatorTxt property
    */
   public void setLocatorTxt(String aLocatorTxt)
   {
      locatorTxt = aLocatorTxt;
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
   @param objectname1
   @param objectname2
   @param voClass
   @return boolean
   @roseuid 3BB9CAEC033D
    */
   public boolean isEqual(Object objectname1, Object objectname2, Class<?> voClass)
   {
      voClass =  ((PhysicalLocatorDT) objectname1).getClass();
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
}
