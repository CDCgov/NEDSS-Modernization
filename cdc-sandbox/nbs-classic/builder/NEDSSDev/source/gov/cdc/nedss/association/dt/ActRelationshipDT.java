//Source file: C:\\Development\\Source\\gov\\cdc\\nedss\\helpers\\ActRelationshipDT.java

package gov.cdc.nedss.association.dt;



  import gov.cdc.nedss.util.*;
  import java.sql.Timestamp;
  import gov.cdc.nedss.systemservice.util.*;

  /**
   * Title:         ActRelationshipDT is a class.
   * Description:   This class is used for bean utility. Which sets/gets(retrieves)
   *                all the fields to the ActRelationShip table
   * Copyright:     Copyright (c) 2001
   * Company:       Computer Sciences Corporation
   * @author        NEDSS TEAM
   * @version       1.0
   */
public class ActRelationshipDT extends AbstractVO implements AssocDTInterface
{
   private static final long serialVersionUID = 1L;
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
   private Integer sequenceNbr;
   private String statusCd;
   private Timestamp statusTime;
   private Timestamp toTime;

   private String userAffiliationTxt;

   private Long sourceActUid;
   private String typeDescTxt;
   private Long targetActUid;
   private String sourceClassCd;
   private String targetClassCd;
   private String typeCd;
   private boolean isShareInd;
   private boolean isNNDInd;
   private boolean isExportInd;
   


   /**
    * @roseuid 3BF16BB3034B
    */
   public ActRelationshipDT()
   {

   }

   /**
    * Access method for the typeCd property.
    *
    * @return   the current value of the type Cd property
    */
   public String getTypeCd()
   {
      return typeCd;
   }

   /**
    * Sets the value of the typeCd property.
    *
    * @param aTypeCd the new value of the type Cd property
    */
   public void setTypeCd(String aTypeCd)
   {
      typeCd = aTypeCd;
   }

   /**
    * Access method for the addReasonCd property.
    *
    * @return   the current value of the add Reason Cd property
    */
   public String getAddReasonCd()
   {
      return addReasonCd;
   }

   /**
    * Sets the value of the addReasonCd property.
    *
    * @param aAddReasonCd the new value of the add Reason Cd property
    */
   public void setAddReasonCd(String aAddReasonCd)
   {
      setItDirty(true);
      //addReasonCd = aAddReasonCd;
      addReasonCd = aAddReasonCd;

   }

   /**
    * Access method for the addTime property.
    *
    * @return   the current value of the add Time property
    */
   public Timestamp getAddTime()
   {
      return addTime;
   }

   /**
    * Sets the value of the addTime property.
    *
    * @param aAddTime the new value of the add Time property
    */
   public void setAddTime(Timestamp aAddTime)
   {
      setItDirty(true);
      //addTime = aAddTime;
      addTime = aAddTime;

   }

   /**
    * Access method for the addUserId property.
    *
    * @return   the current value of the add User Id property
    */
   public Long getAddUserId()
   {
      return addUserId;
   }

   /**
    * Sets the value of the addUserId property.
    *
    * @param aAddUserId the new value of the add User Id property
    */
   public void setAddUserId(Long aAddUserId)
   {
      setItDirty(true);
      //addUserId = aAddUserId;
      addUserId = aAddUserId;

   }

   /**
    * Access method for the durationAmt property.
    *
    * @return   the current value of the duration amount property
    */
   public String getDurationAmt()
   {
      return durationAmt;
   }

   /**
    * Sets the value of the durationAmt property.
    *
    * @param aDurationAmt the new value of the duration amount property
    */
   public void setDurationAmt(String aDurationAmt)
   {
      setItDirty(true);
      //durationAmt = aDurationAmt;
      durationAmt = aDurationAmt;

   }

   /**
    * Access method for the durationUnitCd property.
    *
    * @return   the current value of the duration Unit Cd property
    */
   public String getDurationUnitCd()
   {
      return durationUnitCd;
   }

   /**
    * Sets the value of the durationUnitCd property.
    *
    * @param aDurationUnitCd the new value of the duration Unit Cd property
    */
   public void setDurationUnitCd(String aDurationUnitCd)
   {
      setItDirty(true);
      //durationUnitCd = aDurationUnitCd;
      durationUnitCd = aDurationUnitCd;

   }

   /**
    * Access method for the fromTime property.
    *
    * @return   the current value of the from Time property
    */
   public Timestamp getFromTime()
   {
      return fromTime;
   }

   /**
    * Sets the value of the fromTime property.
    *
    * @param aFromTime the new value of the from Time property
    */
   public void setFromTime(Timestamp aFromTime)
   {
      setItDirty(true);
      //fromTime = aFromTime;
      fromTime = aFromTime;

   }

   /**
    * Access method for the lastChgReasonCd property.
    *
    * @return   the current value of the last change reason Cd property
    */
   public String getLastChgReasonCd()
   {
      return lastChgReasonCd;
   }

   /**
    * Sets the value of the lastReasonCd property.
    *
    * @param aLastChgReasonCd the new value of the last change reason Cd property
    */
   public void setLastChgReasonCd(String aLastChgReasonCd)
   {
      setItDirty(true);
      //lastChgReasonCd = aLastChgReasonCd;
      lastChgReasonCd = aLastChgReasonCd;

   }

   /**
    * Access method for the lastChgTime property.
    *
    * @return   the current value of the last change Time property
    */
   public Timestamp getLastChgTime()
   {
      return lastChgTime;
   }

   /**
    * Sets the value of the lastChgTime property.
    *
    * @param aLastChgTime the new value of the last change Time property
    */
   public void setLastChgTime(Timestamp aLastChgTime)
   {
      setItDirty(true);
      //lastChgTime = aLastChgTime;
      lastChgTime = aLastChgTime;

   }

   /**
    * Access method for the lastChgUserId property.
    *
    * @return   the current value of the lastchange User Id property
    */
   public Long getLastChgUserId()
   {
      return lastChgUserId;
   }

   /**
    * Sets the value of the lastChgUserId property.
    *
    * @param aLastChgUserId the new value of the last change User Id property
    */
   public void setLastChgUserId(Long aLastChgUserId)
   {
      setItDirty(true);
      //lastChgUserId = aLastChgUserId;
      lastChgUserId = aLastChgUserId;

   }

   /**
    * Access method for the recordStatusCd property.
    *
    * @return   the current value of the record Status Cd property
    */
   public String getRecordStatusCd()
   {
      return recordStatusCd;
   }

   /**
    * Sets the value of the recordStatusCd property.
    *
    * @param aRecordStatusCd the new value of the record Status Cd property
    */
   public void setRecordStatusCd(String aRecordStatusCd)
   {
      setItDirty(true);
      //recordStatusCd = aRecordStatusCd;
      recordStatusCd = aRecordStatusCd;

   }

   /**
    * Access method for the recordStatusTime property.
    *
    * @return   the current value of the record Status Time property
    */
   public Timestamp getRecordStatusTime()
   {
      return recordStatusTime;
   }

   /**
    * Sets the value of the recordStatusTime property.
    *
    * @param aRecordStatusTime the new value of the record Status Time property
    */
   public void setRecordStatusTime(Timestamp aRecordStatusTime)
   {
      setItDirty(true);
      //recordStatusTime = aRecordStatusTime;
      recordStatusTime = aRecordStatusTime;

   }

   /**
    * Access method for the sequenceNbr property.
    *
    * @return   the current value of the sequence number property
    */
   public Integer getSequenceNbr()
   {
      return sequenceNbr;
   }

   /**
    * Sets the value of the sequenceNbr property.
    *
    * @param aSequenceNbr the new value of the sequence number property
    */
   public void setSequenceNbr(Integer aSequenceNbr)
   {
      setItDirty(true);
      //sequenceNbr = aSequenceNbr;
      sequenceNbr = aSequenceNbr;

   }

   /**
    * Access method for the statusCd property.
    *
    * @return   the current value of the status Cd property
    */
   public String getStatusCd()
   {
      return statusCd;
   }

   /**
    * Sets the value of the statusCd property.
    *
    * @param aStatusCd the new value of the status Cd property
    */
   public void setStatusCd(String aStatusCd)
   {
      setItDirty(true);
      //statusCd = aStatusCd;
      statusCd = aStatusCd;

   }

   /**
    * Access method for the statusTime property.
    *
    * @return   the current value of the status Time property
    */
   public Timestamp getStatusTime()
   {
      return statusTime;
   }

   /**
    * Sets the value of the statusTime property.
    *
    * @param aStatusTime the new value of the status Time property
    */
   public void setStatusTime(Timestamp aStatusTime)
   {
      setItDirty(true);
      //statusTime = aStatusTime;
      statusTime = aStatusTime;

   }

   /**
    * Access method for the toTime property.
    *
    * @return   the current value of the to Time property
    */
   public Timestamp getToTime()
   {
      return toTime;
   }

   /**
    * Sets the value of the toTime property.
    *
    * @param aToTime the new value of the to Time property
    */
   public void setToTime(Timestamp aToTime)
   {
      setItDirty(true);
      //toTime = aToTime;
      toTime = aToTime;






















   }

   /**
    * Access method for the userAffiliationTxt property.
    *
    * @return   the current value of the user Affiliation Text property
    */
   public String getUserAffiliationTxt()
   {
      return userAffiliationTxt;
   }

   /**
    * Sets the value of the userAffiliationTxt property.
    *
    * @param aUserAffiliationTxt the new value of the user Affiliation Text property
    */
   public void setUserAffiliationTxt(String aUserAffiliationTxt)
   {
      setItDirty(true);
      //userAffiliationTxt = aUserAffiliationTxt;
      userAffiliationTxt = aUserAffiliationTxt;






















   }

   /**
    * Access method for the sourceActUid property.
    *
    * @return   the current value of the source Act Uid property
    */
   public Long getSourceActUid()
   {
      return sourceActUid;
   }

   /**
    * Sets the value of the sourceActUid property.
    *
    * @param aSourceActUid the new value of the source Act Uid property
    */
   public void setSourceActUid(Long aSourceActUid)
   {
      setItDirty(true);
      //sourceActUid = aSourceActUid;
      sourceActUid = aSourceActUid;

   }

   /**
    * Access method for the typeDescTxt property.
    *
    * @return   the current value of the type Description Text property
    */
   public String getTypeDescTxt()
   {
      return typeDescTxt;
   }

   /**
    * Sets the value of the typeDescTxt property.
    *
    * @param aTypeDescTxt the new value of the type Description Text property
    */
   public void setTypeDescTxt(String aTypeDescTxt)
   {
      setItDirty(true);
      //typeDescTxt = aTypeDescTxt;
      typeDescTxt = aTypeDescTxt;
   }

   /**
    * Access method for the targetActUid property.
    *
    * @return   the current value of the target Act Uid property
    */
   public Long getTargetActUid()
   {
      return targetActUid;
   }

   /**
    * Sets the value of the targetActUid property.
    *
    * @param aTargetActUid the new value of the target Act Uid property
    */
   public void setTargetActUid(Long aTargetActUid)
   {
      targetActUid = aTargetActUid;
   }

   /**
    * Access method for the sourceClassCd property.
    *
    * @return   the current value of the source Class Cd property
    */
   public String getSourceClassCd()
   {
      return sourceClassCd;
   }

   /**
    * Sets the value of the sourceClassCd property.
    *
    * @param aSourceClassCd the new value of the source Class Cd property
    */
   public void setSourceClassCd(String aSourceClassCd)
   {
      sourceClassCd = aSourceClassCd;
   }

   /**
    * Access method for the targetClassCd property.
    *
    * @return   the current value of the target Class Cd property
    */
   public String getTargetClassCd()
   {
      return targetClassCd;
   }

   /**
    * Sets the value of the targetClassCd property.
    *
    * @param aTargetClassCd the new value of the target Class Cd property
    */
   public void setTargetClassCd(String aTargetClassCd)
   {
      targetClassCd = aTargetClassCd;
   }


   /**
    * @roseuid 3BF13DB30044
    */
   public void ActRelationship()
   {

   }

   /**
    * get the value of the boolean property.
    * @param objectname1 Object the object name
    * @param objectname2 Object the object name
    * @param voClass Class the class
    * @return isEqual the value of the boolean value
    */
   public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass)
   {
      voClass =  ((ActRelationshipDT) objectname1).getClass();
      NedssUtils compareObjs = new NedssUtils();
      return (compareObjs.equals(objectname1,objectname2,voClass));
   }

   /**
    * Sets the value of the itDirty property.
    *
    * @param boolean itDirty the new value of the ItDirty property
    */
   public void setItDirty(boolean itDirty)
   {
	this.itDirty = itDirty;
   }

  /**
    * get the value of the boolean property.
    *
    * @return ItDirty the value of the boolean value
    */
   public boolean isItDirty()
   {
    return this.itDirty;
   }


   /**
    * Sets the value of the ItNew property.
    *
    * @param itNew boolean the new value of the boolean property
    */
   public void setItNew(boolean itNew)
   {
	this.itNew = itNew;
   }

      //Wrapper method to invoke setItnew(boolean)...
      /**
       * Sets the value of the ItNew property.
       *
       * @param itNew boolean the new value of the boolean property
       */
      public void setItNew(Boolean itNew)
      {
       this.itNew = itNew.booleanValue();
      }



    /**
    * get the value of the itNew property.
    *
    * @return itNew the value of the boolean property
    */
   public boolean isItNew()
   {
    return this.itNew;
   }

   /**
    * Sets the value of the ItDelete property.
    *
    * @param itDelete boolean the value of the boolean property
    */
   public void setItDelete(boolean itDelete)
   {
        this.itDelete = itDelete;
   }

   /**
    * gets the value of the ItDelete property.
    *
    * @return ItDelete the new value of the boolean property
    */
   public boolean isItDelete()
   {
    return this.itDelete;
   }

public boolean isShareInd() {
	return isShareInd;
}

public void setShareInd(boolean isShareInd) {
	this.isShareInd = isShareInd;
}

public boolean isNNDInd() {
	return isNNDInd;
}

public void setNNDInd(boolean isNNDInd) {
	this.isNNDInd = isNNDInd;
}

public boolean isExportInd() {
	return isExportInd;
}

public void setExportInd(boolean isExportInd) {
	this.isExportInd = isExportInd;
}


























}

