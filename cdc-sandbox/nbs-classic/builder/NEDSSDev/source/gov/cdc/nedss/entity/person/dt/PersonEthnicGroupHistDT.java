//Source file: C:\\Project Stuff\\RationalRoseDevelopment\\gov\\cdc\\nedss\\cdm\\helpers\\PersonEthnicGroupHistDT.java

package gov.cdc.nedss.entity.person.dt;

import  gov.cdc.nedss.util.*;
import java.sql.Timestamp;

public class PersonEthnicGroupHistDT extends AbstractVO
{
   private Long personUid;
   private Integer ethnicGroupCd;
   private Integer personEthnicGroupHistSeq;
   private String addReasonCd;
   private Timestamp addTime;
   private Long addUserId;
   private String chgReasonCd;
   private Timestamp chgTime;
   private Long chgUserId;
   private String ethnicGroupDescTxt;
   private String lastChgReasonCd;
   private Timestamp lastChgTime;
   private Long lastChgUserId;
   private String recordStatusCd;
   private Timestamp recordStatusTime;
   private String userAffiliationTxt;

   /**
    * @roseuid 3C56FF54020F
    */
   public PersonEthnicGroupHistDT()
   {

   }

   /**
    * Access method for the personUid property.
    *
    * @return   the current value of the personUid property
    */
   public Long getPersonUid()
   {
      return personUid;
   }

   /**
    * Sets the value of the personUid property.
    *
    * @param aPersonUid the new value of the personUid property
    */
   public void setPersonUid(Long aPersonUid)
   {
      personUid = aPersonUid;
   }

   /**
    * Access method for the ethnicGroupCd property.
    *
    * @return   the current value of the ethnicGroupCd property
    */
   public Integer getEthnicGroupCd()
   {
      return ethnicGroupCd;
   }

   /**
    * Sets the value of the ethnicGroupCd property.
    *
    * @param aEthnicGroupCd the new value of the ethnicGroupCd property
    */
   public void setEthnicGroupCd(Integer aEthnicGroupCd)
   {
      ethnicGroupCd = aEthnicGroupCd;
   }

   /**
    * Access method for the personEthnicGroupHistSeq property.
    *
    * @return   the current value of the personEthnicGroupHistSeq property
    */
   public Integer getPersonEthnicGroupHistSeq()
   {
      return personEthnicGroupHistSeq;
   }

   /**
    * Sets the value of the personEthnicGroupHistSeq property.
    *
    * @param aPersonEthnicGroupHistSeq the new value of the personEthnicGroupHistSeq property
    */
   public void setPersonEthnicGroupHistSeq(Integer aPersonEthnicGroupHistSeq)
   {
      personEthnicGroupHistSeq = aPersonEthnicGroupHistSeq;
   }

   /**
    * Access method for the addReasonCd property.
    *
    * @return   the current value of the addReasonCd property
    */
   public String getAddReasonCd()
   {
      return addReasonCd;
   }

   /**
    * Sets the value of the addReasonCd property.
    *
    * @param aAddReasonCd the new value of the addReasonCd property
    */
   public void setAddReasonCd(String aAddReasonCd)
   {
      addReasonCd = aAddReasonCd;
   }

   /**
    * Access method for the addTime property.
    *
    * @return   the current value of the addTime property
    */
   public Timestamp getAddTime()
   {
      return addTime;
   }

   /**
    * Sets the value of the addTime property.
    *
    * @param aAddTime the new value of the addTime property
    */
   public void setAddTime(Timestamp aAddTime)
   {
      addTime = aAddTime;
   }

   /**
    * Access method for the addUserId property.
    *
    * @return   the current value of the addUserId property
    */
   public Long getAddUserId()
   {
      return addUserId;
   }

   /**
    * Sets the value of the addUserId property.
    *
    * @param aAddUserId the new value of the addUserId property
    */
   public void setAddUserId(Long aAddUserId)
   {
      addUserId = aAddUserId;
   }

   /**
    * Access method for the chgReasonCd property.
    *
    * @return   the current value of the chgReasonCd property
    */
   public String getChgReasonCd()
   {
      return chgReasonCd;
   }

   /**
    * Sets the value of the chgReasonCd property.
    *
    * @param aChgReasonCd the new value of the chgReasonCd property
    */
   public void setChgReasonCd(String aChgReasonCd)
   {
      chgReasonCd = aChgReasonCd;
   }

   /**
    * Access method for the chgTime property.
    *
    * @return   the current value of the chgTime property
    */
   public Timestamp getChgTime()
   {
      return chgTime;
   }

   /**
    * Sets the value of the chgTime property.
    *
    * @param aChgTime the new value of the chgTime property
    */
   public void setChgTime(Timestamp aChgTime)
   {
      chgTime = aChgTime;
   }

   /**
    * Access method for the chgUserId property.
    *
    * @return   the current value of the chgUserId property
    */
   public Long getChgUserId()
   {
      return chgUserId;
   }

   /**
    * Sets the value of the chgUserId property.
    *
    * @param aChgUserId the new value of the chgUserId property
    */
   public void setChgUserId(Long aChgUserId)
   {
      chgUserId = aChgUserId;
   }

   /**
    * Access method for the ethnicGroupDescTxt property.
    *
    * @return   the current value of the ethnicGroupDescTxt property
    */
   public String getEthnicGroupDescTxt()
   {
      return ethnicGroupDescTxt;
   }

   /**
    * Sets the value of the ethnicGroupDescTxt property.
    *
    * @param aEthnicGroupDescTxt the new value of the ethnicGroupDescTxt property
    */
   public void setEthnicGroupDescTxt(String aEthnicGroupDescTxt)
   {
      ethnicGroupDescTxt = aEthnicGroupDescTxt;
   }

   /**
    * Access method for the lastChgReasonCd property.
    *
    * @return   the current value of the lastChgReasonCd property
    */
   public String getLastChgReasonCd()
   {
      return lastChgReasonCd;
   }

   /**
    * Sets the value of the lastChgReasonCd property.
    *
    * @param aLastChgReasonCd the new value of the lastChgReasonCd property
    */
   public void setLastChgReasonCd(String aLastChgReasonCd)
   {
      lastChgReasonCd = aLastChgReasonCd;
   }

   /**
    * Access method for the lastChgTime property.
    *
    * @return   the current value of the lastChgTime property
    */
   public Timestamp getLastChgTime()
   {
      return lastChgTime;
   }

   /**
    * Sets the value of the lastChgTime property.
    *
    * @param aLastChgTime the new value of the lastChgTime property
    */
   public void setLastChgTime(Timestamp aLastChgTime)
   {
      lastChgTime = aLastChgTime;
   }

   /**
    * Access method for the lastChgUserId property.
    *
    * @return   the current value of the lastChgUserId property
    */
   public Long getLastChgUserId()
   {
      return lastChgUserId;
   }

   /**
    * Sets the value of the lastChgUserId property.
    *
    * @param aLastChgUserId the new value of the lastChgUserId property
    */
   public void setLastChgUserId(Long aLastChgUserId)
   {
      lastChgUserId = aLastChgUserId;
   }

   /**
    * Access method for the recordStatusCd property.
    *
    * @return   the current value of the recordStatusCd property
    */
   public String getRecordStatusCd()
   {
      return recordStatusCd;
   }

   /**
    * Sets the value of the recordStatusCd property.
    *
    * @param aRecordStatusCd the new value of the recordStatusCd property
    */
   public void setRecordStatusCd(String aRecordStatusCd)
   {
      recordStatusCd = aRecordStatusCd;
   }

   /**
    * Access method for the recordStatusTime property.
    *
    * @return   the current value of the recordStatusTime property
    */
   public Timestamp getRecordStatusTime()
   {
      return recordStatusTime;
   }

   /**
    * Sets the value of the recordStatusTime property.
    *
    * @param aRecordStatusTime the new value of the recordStatusTime property
    */
   public void setRecordStatusTime(Timestamp aRecordStatusTime)
   {
      recordStatusTime = aRecordStatusTime;
   }

   /**
    * Access method for the userAffiliationTxt property.
    *
    * @return   the current value of the userAffiliationTxt property
    */
   public String getUserAffiliationTxt()
   {
      return userAffiliationTxt;
   }

   /**
    * Sets the value of the userAffiliationTxt property.
    *
    * @param aUserAffiliationTxt the new value of the userAffiliationTxt property
    */
   public void setUserAffiliationTxt(String aUserAffiliationTxt)
   {
      userAffiliationTxt = aUserAffiliationTxt;
   }


    /**
     *
     * @param objectname1
     * @param objectname2
     * @param voClass
     * @return
     */
    public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass) {
        voClass = ((PersonEthnicGroupHistDT)objectname1).getClass();

        NedssUtils compareObjs = new NedssUtils();

        return (compareObjs.equals(objectname1, objectname2, voClass));
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
