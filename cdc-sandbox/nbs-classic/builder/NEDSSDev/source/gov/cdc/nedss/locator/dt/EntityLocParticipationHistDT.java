//Source file: C:\\Project Stuff\\RationalRoseDevelopment\\gov\\cdc\\nedss\\cdm\\helpers\\EntityLocParticipationHistDT.java
/**
* Name:		    EntityLocParticipationHistDT
* Description:	EntityLocatorParticipation data table object
* Copyright:	Copyright (c) 2002
* Company: 	    Computer Sciences Corporation
* @author	    NEDSS Development Team
* @version	    1.0
*/

package gov.cdc.nedss.locator.dt;

import  gov.cdc.nedss.util.*;
import java.sql.Timestamp;

public class EntityLocParticipationHistDT extends AbstractVO
{
   private Integer entityLocPartHistSeq;
   private String addReasonCd;
   private Timestamp addTime;
   private Long addUserId;
   private Timestamp asOfDate;
   private String cd;
   private String cdDescTxt;
   private String chgReasonCd;
   private Timestamp chgTime;
   private Long chgUserId;
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
   private Timestamp toTime;
   private String useCd;
   private String userAffiliationTxt;
   private Timestamp validTimeTxt;
   private Long locatorUid;
   private Long entityUid;
   public PostalLocatorHistDT thePostalLocatorHistDT;
   public PhysicalLocatorHistDT thePhysicalLocatorHistDT;
   public TeleLocatorHistDT theTeleLocatorHistDT;

   /**
    * @roseuid 3C57001003AA
    */
   public EntityLocParticipationHistDT()
   {

   }

   /**
    * Access method for the entityLocPartHistSeq property.
    *
    * @return   the current value of the entityLocPartHistSeq property
    */
   public Integer getEntityLocPartHistSeq()
   {
      return entityLocPartHistSeq;
   }

   /**
    * Sets the value of the entityLocPartHistSeq property.
    *
    * @param aEntityLocPartHistSeq the new value of the entityLocPartHistSeq property
    */
   public void setEntityLocPartHistSeq(Integer aEntityLocPartHistSeq)
   {
      entityLocPartHistSeq = aEntityLocPartHistSeq;
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
	 * gets AsOfDate
	 * @return Timestamp
	 */
	public Timestamp getAsOfDate(){
		return this.asOfDate;
	}

	public void setAsOfDate(Timestamp aAsOfDate){
		this.asOfDate = aAsOfDate;
		setItDirty(true);
	}

   /**
    * Access method for the cd property.
    *
    * @return   the current value of the cd property
    */
   public String getCd()
   {
      return cd;
   }

   /**
    * Sets the value of the cd property.
    *
    * @param aCd the new value of the cd property
    */
   public void setCd(String aCd)
   {
      cd = aCd;
   }

   /**
    * Access method for the cdDescTxt property.
    *
    * @return   the current value of the cdDescTxt property
    */
   public String getCdDescTxt()
   {
      return cdDescTxt;
   }

   /**
    * Sets the value of the cdDescTxt property.
    *
    * @param aCdDescTxt the new value of the cdDescTxt property
    */
   public void setCdDescTxt(String aCdDescTxt)
   {
      cdDescTxt = aCdDescTxt;
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
    * Access method for the classCd property.
    *
    * @return   the current value of the classCd property
    */
   public String getClassCd()
   {
      return classCd;
   }

   /**
    * Sets the value of the classCd property.
    *
    * @param aClassCd the new value of the classCd property
    */
   public void setClassCd(String aClassCd)
   {
      classCd = aClassCd;
   }

   /**
    * Access method for the durationAmt property.
    *
    * @return   the current value of the durationAmt property
    */
   public String getDurationAmt()
   {
      return durationAmt;
   }

   /**
    * Sets the value of the durationAmt property.
    *
    * @param aDurationAmt the new value of the durationAmt property
    */
   public void setDurationAmt(String aDurationAmt)
   {
      durationAmt = aDurationAmt;
   }

   /**
    * Access method for the durationUnitCd property.
    *
    * @return   the current value of the durationUnitCd property
    */
   public String getDurationUnitCd()
   {
      return durationUnitCd;
   }

   /**
    * Sets the value of the durationUnitCd property.
    *
    * @param aDurationUnitCd the new value of the durationUnitCd property
    */
   public void setDurationUnitCd(String aDurationUnitCd)
   {
      durationUnitCd = aDurationUnitCd;
   }

   /**
    * Access method for the fromTime property.
    *
    * @return   the current value of the fromTime property
    */
   public Timestamp getFromTime()
   {
      return fromTime;
   }

   /**
    * Sets the value of the fromTime property.
    *
    * @param aFromTime the new value of the fromTime property
    */
   public void setFromTime(Timestamp aFromTime)
   {
      fromTime = aFromTime;
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
    * Access method for the locatorDescTxt property.
    *
    * @return   the current value of the locatorDescTxt property
    */
   public String getLocatorDescTxt()
   {
      return locatorDescTxt;
   }

   /**
    * Sets the value of the locatorDescTxt property.
    *
    * @param aLocatorDescTxt the new value of the locatorDescTxt property
    */
   public void setLocatorDescTxt(String aLocatorDescTxt)
   {
      locatorDescTxt = aLocatorDescTxt;
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
    * Access method for the toTime property.
    *
    * @return   the current value of the toTime property
    */
   public Timestamp getToTime()
   {
      return toTime;
   }

   /**
    * Sets the value of the toTime property.
    *
    * @param aToTime the new value of the toTime property
    */
   public void setToTime(Timestamp aToTime)
   {
      toTime = aToTime;
   }

   /**
    * Access method for the useCd property.
    *
    * @return   the current value of the useCd property
    */
   public String getUseCd()
   {
      return useCd;
   }

   /**
    * Sets the value of the useCd property.
    *
    * @param aUseCd the new value of the useCd property
    */
   public void setUseCd(String aUseCd)
   {
      useCd = aUseCd;
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
    * Access method for the validTimeTxt property.
    *
    * @return   the current value of the validTimeTxt property
    */
   public Timestamp getValidTimeTxt()
   {
      return validTimeTxt;
   }

   /**
    * Sets the value of the validTimeTxt property.
    *
    * @param aValidTimeTxt the new value of the validTimeTxt property
    */
   public void setValidTimeTxt(Timestamp aValidTimeTxt)
   {
      validTimeTxt = aValidTimeTxt;
   }

   /**
    * Access method for the locatorUid property.
    *
    * @return   the current value of the locatorUid property
    */
   public Long getLocatorUid()
   {
      return locatorUid;
   }

   /**
    * Sets the value of the locatorUid property.
    *
    * @param aLocatorUid the new value of the locatorUid property
    */
   public void setLocatorUid(Long aLocatorUid)
   {
      locatorUid = aLocatorUid;
   }

   /**
    * Access method for the entityUid property.
    *
    * @return   the current value of the entityUid property
    */
   public Long getEntityUid()
   {
      return entityUid;
   }

   /**
    * Sets the value of the entityUid property.
    *
    * @param aEntityUid the new value of the entityUid property
    */
   public void setEntityUid(Long aEntityUid)
   {
      entityUid = aEntityUid;
   }

   /**
    * Access method for the thePostalLocatorHistDT property.
    *
    * @return   the current value of the thePostalLocatorHistDT property
    */
   public PostalLocatorHistDT getThePostalLocatorHistDT()
   {
      return thePostalLocatorHistDT;
   }

   /**
    * Sets the value of the thePostalLocatorHistDT property.
    *
    * @param aThePostalLocatorHistDT the new value of the thePostalLocatorHistDT property
    */
   public void setThePostalLocatorHistDT(PostalLocatorHistDT aThePostalLocatorHistDT)
   {
      thePostalLocatorHistDT = aThePostalLocatorHistDT;
   }

   /**
    * Access method for the thePhysicalLocatorHistDT property.
    *
    * @return   the current value of the thePhysicalLocatorHistDT property
    */
   public PhysicalLocatorHistDT getThePhysicalLocatorHistDT()
   {
      return thePhysicalLocatorHistDT;
   }

   /**
    * Sets the value of the thePhysicalLocatorHistDT property.
    *
    * @param aThePhysicalLocatorHistDT the new value of the thePhysicalLocatorHistDT property
    */
   public void setThePhysicalLocatorHistDT(PhysicalLocatorHistDT aThePhysicalLocatorHistDT)
   {
      thePhysicalLocatorHistDT = aThePhysicalLocatorHistDT;
   }

   /**
    * Access method for the theTeleLocatorHistDT property.
    *
    * @return   the current value of the theTeleLocatorHistDT property
    */
   public TeleLocatorHistDT getTheTeleLocatorHistDT()
   {
      return theTeleLocatorHistDT;
   }

   /**
    * Sets the value of the theTeleLocatorHistDT property.
    *
    * @param aTheTeleLocatorHistDT the new value of the theTeleLocatorHistDT property
    */
   public void setTheTeleLocatorHistDT(TeleLocatorHistDT aTheTeleLocatorHistDT)
   {
      theTeleLocatorHistDT = aTheTeleLocatorHistDT;
   }

   /**
    * @param objectname1
    * @param objectname2
    * @param voClass
    * @return boolean
    * @roseuid 3C5700110026
    */
   public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass)
   {
    return true;
   }

   /**
    * @param itDirty
    * @roseuid 3C570011013E
    */
   public void setItDirty(boolean itDirty)
   {

   }

   /**
    * @return boolean
    * @roseuid 3C57001101B6
    */
   public boolean isItDirty()
   {
    return true;
   }

   /**
    * @param itNew
    * @roseuid 3C57001101DF
    */
   public void setItNew(boolean itNew)
   {

   }

   /**
    * @return boolean
    * @roseuid 3C5700110261
    */
   public boolean isItNew()
   {
    return true;
   }

   /**
    * @param itDelete
    * @roseuid 3C5700110289
    */
   public void setItDelete(boolean itDelete)
   {

   }

   /**
    * @return boolean
    * @roseuid 3C570011030B
    */
   public boolean isItDelete()
   {
    return true;
   }
}
