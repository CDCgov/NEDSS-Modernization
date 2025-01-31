//Source file: C:\\rational_rose_dev\\source\\gov\\cdc\\nedss\\proxy\\ejb\\notificationproxyejb\\vo\\NotificationSummaryVO.java

//Source file: C:\\ProjectStuff\\RationalRoseDevelopment\\gov\\cdc\\nedss\\helpers\\NotificationSummaryVO.java

/**
 * Title:        NotificationSummaryVO
 * Description:  NotificationSummaryVO is a value object that represents a notification summary object .
 *               It extends AbstractVO. It has getting and setting methods, and flags for its
 *               properties.
 * Copyright:    Copyright (c) 2002
 * Company:      Computer Sciences Corporation
 * @author:      NEDSS Development Team
 * @version      NBS1.1
 */

package gov.cdc.nedss.proxy.ejb.notificationproxyejb.vo;

import gov.cdc.nedss.util.*;
import java.sql.Timestamp;

import gov.cdc.nedss.systemservice.util.*;

public class NotificationSummaryVO extends AbstractVO implements RootDTInterface
{

   /**
    * from NotificationDT.notificationtionUid
    * (Not for display purposes)
    */
   private Long notificationUid;

   /**
    * from NotificationDT.addTime
    */
   private Timestamp addTime;

   /**
    * from NotificationDT.rptSentTime
    */
   private Timestamp rptSentTime;
   

   private Timestamp recordStatusTime;
   

   /**
    * from PublicHealthCaseDT.cd
    */
   private String cd;

   /**
    * from PublicHealthCase.caseClassCd
    */
   private String caseClassCd;

   /**
    * from NotificationDT.localId
    */
   private String localId;

   /**
    * from Notification.txt
    */
   private String txt;

   /**
    * from NotificationDT.lastChgTime
    */
   private Timestamp lastChgTime;

   private String addUserName;
   /**
    * from NotificationDT.addUserId
    */
   private Long addUserId;

   /**
    * from PublicHealthCase.jurisdictionCd
    */
   private String jurisdictionCd;

   /**
    * from publicHealthCaseDT.publicHealthCaseUid
    */
   private Long publicHealthCaseUid;

   /**
    * from Code_Value_General.code where Code_Value_General.code_set_nm = 'PHC_TYPE'
    */
   private String cdTxt;

   /**
    * from Code_Value_General.code_desc_txt where Code_Value_General.code_set_nm =
    * 'S_JURDIC_C'
    */
   private String jurisdictionCdTxt;

   /**
    * from publicHealthCaseDT.localId
    */
   private String publicHealthCaseLocalId;

   /**
    * from Code_Value_General.code where Code_Value_General.code_set_nm = 'PHC_CLASS'
    */
   private String caseClassCdTxt;

   /**
    * from Notification.record_status_cd
    */
   private String recordStatusCd;
   private String lastNm;
   private String firstNm;
   private String currSexCd;
   private Timestamp birthTimeCalc;
   private String autoResendInd;
   public String isHistory;
   //Needed for Auto Resend
   private String progAreaCd;
   private String sharedInd;
   private String currSexCdDesc;

   private Long MPRUid;
   
   //This is for cd in the Notification table
   private String cdNotif;
  // This is for approve notification checking variable for popup 
   private boolean nndAssociated;
   private boolean isCaseReport;
   private Long programJurisdictionOid;
   private boolean shareAssocaited;
   
   
   /**
 * Notification.cd
 */
   

   
   //Need for the new Notification Queue to add links for patient and condition
   private String patientFullName;
   private String patientFullNameLnk;
   private String conditionCodeTextLnk; 
   private String approveLink;
   private String rejectLink;
   private String notificationCd;
   private String notificationSrtDescCd;
   private String recipient;
   private String exportRecFacilityUid;
   //The following variable is required to control the Special Character's of Recipient which is coming from the manageSystems
   private String codeConverterTemp;
   private String codeConverterCommentTemp;
   private boolean isPendingNotification;
   private String nndInd;


/**
    * @roseuid 3C22A54702E6
    */
   public NotificationSummaryVO()
   {

   }

   /**
    * Access method for the notificationUid property.
    *
    * @return   the current value of the notificationUid property
    */
   public Long getNotificationUid()
   {
      return notificationUid;
   }

   /**
    * Sets the value of the notificationUid property.
    *
    * @param aNotificationUid the new value of the notificationUid property
    */
   public void setNotificationUid(Long aNotificationUid)
   {
      notificationUid = aNotificationUid;
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
    * Access method for the rptSentTime property.
    *
    * @return   the current value of the rptSentTime property
    */
   public Timestamp getRptSentTime()
   {
      return rptSentTime;
   }

   /**
    * Sets the value of the rptSentTime property.
    *
    * @param aRptSentTime the new value of the rptSentTime property
    */
   public void setRptSentTime(Timestamp aRptSentTime)
   {
      rptSentTime = aRptSentTime;
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
    * Access method for the caseClassCd property.
    *
    * @return   the current value of the caseClassCd property
    */
   public String getCaseClassCd()
   {
      return caseClassCd;
   }

   /**
    * Sets the value of the caseClassCd property.
    *
    * @param aCaseClassCd the new value of the caseClassCd property
    */
   public void setCaseClassCd(String aCaseClassCd)
   {
      caseClassCd = aCaseClassCd;
   }

   /**
    * Access method for the localId property.
    *
    * @return   the current value of the localId property
    */
   public String getLocalId()
   {
      return localId;
   }

   /**
    * Sets the value of the localId property.
    *
    * @param aLocalId the new value of the localId property
    */
   public void setLocalId(String aLocalId)
   {
      localId = aLocalId;
   }

   /**
    * Access method for the txt property.
    *
    * @return   the current value of the txt property
    */
   public String getTxt()
   {
      return txt;
   }

   /**
    * Sets the value of the txt property.
    *
    * @param aTxt the new value of the txt property
    */
   public void setTxt(String aTxt)
   {
      txt = aTxt;
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
    * Access method for the jurisdictionCd property.
    *
    * @return   the current value of the jurisdictionCd property
    */
   public String getJurisdictionCd()
   {
      return jurisdictionCd;
   }

   /**
    * Sets the value of the jurisdictionCd property.
    *
    * @param aJurisdictionCd the new value of the jurisdictionCd property
    */
   public void setJurisdictionCd(String aJurisdictionCd)
   {
      jurisdictionCd = aJurisdictionCd;
   }

   /**
    * Access method for the publicHealthCaseUid property.
    *
    * @return   the current value of the publicHealthCaseUid property
    */
   public Long getPublicHealthCaseUid()
   {
      return publicHealthCaseUid;
   }

   /**
    * Sets the value of the publicHealthCaseUid property.
    *
    * @param aPublicHealthCaseUid the new value of the publicHealthCaseUid property
    */
   public void setPublicHealthCaseUid(Long aPublicHealthCaseUid)
   {
      publicHealthCaseUid = aPublicHealthCaseUid;
   }

   /**
    * Access method for the cdTxt property.
    *
    * @return   the current value of the cdTxt property
    */
   public String getCdTxt()
   {
      return cdTxt;
   }

   /**
    * Sets the value of the cdTxt property.
    *
    * @param aCdTxt the new value of the cdTxt property
    */
   public void setCdTxt(String aCdTxt)
   {
      cdTxt = aCdTxt;
   }

   /**
    * Access method for the jurisdictionCdTxt property.
    *
    * @return   the current value of the jurisdictionCdTxt property
    */
   public String getJurisdictionCdTxt()
   {
      return jurisdictionCdTxt;
   }

   /**
    * Sets the value of the jurisdictionCdTxt property.
    *
    * @param aJurisdictionCdTxt the new value of the jurisdictionCdTxt property
    */
   public void setJurisdictionCdTxt(String aJurisdictionCdTxt)
   {
      jurisdictionCdTxt = aJurisdictionCdTxt;
   }

   /**
    * Access method for the lastNm property.
    *
    * @return   the current value of the lastNm property
    */
   public String getLastNm()
   {
      return lastNm;
   }

   /**
    * Sets the value of the lastNm property.
    *
    * @param aLastNm the new value of the lastNm property
    */
   public void setLastNm(String aLastNm)
   {
      lastNm = aLastNm;
   }

   /**
    * Access method for the firstNm property.
    *
    * @return   the current value of the firstNm property
    */
   public String getFirstNm()
   {
      return firstNm;
   }

   /**
    * Sets the value of the firstNm property.
    *
    * @param aFirstNm the new value of the firstNm property
    */
   public void setFirstNm(String aFirstNm)
   {
      firstNm = aFirstNm;
   }

   /**
    * Access method for the currSexCd property.
    *
    * @return   the current value of the currSexCd property
    */
   public String getCurrSexCd()
   {
      return currSexCd;
   }

   /**
    * Sets the value of the currSexCd property.
    *
    * @param aCurrSexCd the new value of the currSexCd property
    */
   public void setCurrSexCd(String aCurrSexCd)
   {
      currSexCd = aCurrSexCd;
   }

   /**
    * Access method for the birthTimeCalc property.
    *
    * @return   the current value of the birthTimeCalc property
    */
   public Timestamp getBirthTimeCalc()
   {
      return birthTimeCalc;
   }

   /**
    * Sets the value of the birthTimeCalc property.
    *
    * @param aBirthTimeCalc the new value of the birthTimeCalc property
    */
   public void setBirthTimeCalc(Timestamp aBirthTimeCalc)
   {
      birthTimeCalc = aBirthTimeCalc;
   }

   /**
    * Determines if the autoResendInd property is true.
    *
    * @return   <code>true<code> if the autoResendInd property is true
    */
   public String getAutoResendInd()
   {
      return autoResendInd;
   }

   /**
    * Sets the value of the autoResendInd property.
    *
    * @param aAutoResendInd the new value of the autoResendInd property
    */
   public void setAutoResendInd(String aAutoResendInd)
   {
      autoResendInd = aAutoResendInd;
   }

   /**
    * Access method for the isHistory property.
    *
    * @return   the current value of the isHistory property
    */
   public String getIsHistory()
   {
      return isHistory;
   }

   /**
    * Sets the value of the isHistory property.
    *
    * @param aIsHistory the new value of the isHistory property
    */
   public void setIsHistory(String aIsHistory)
   {
      isHistory = aIsHistory;
   }

   /**
    * @param objectname1
    * @param objectname2
    * @param voClass
    * @return boolean
    * @roseuid 3C22A54702FA
    */
   public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass)
   {
    return true;
   }

   /**
    * @param itDirty
    * @roseuid 3C22A547030E
    */
   public void setItDirty(boolean itDirty)
   {
        this.itDirty = itDirty;
   }

   /**
    * @return boolean
    * @roseuid 3C22A5470322
    */
   public boolean isItDirty()
   {
    return this.itDirty;
   }

   /**
    * @param itNew
    * @roseuid 3C22A547032C
    */
   public void setItNew(boolean itNew)
   {
        this.itNew = itNew;
   }

   /**
    * @return boolean
    * @roseuid 3C22A5470337
    */
   public boolean isItNew()
   {
    return itNew;
   }

   /**
    * @param itDelete
    * @roseuid 3C22A5470340
    */
   public void setItDelete(boolean itDelete)
   {

   }

   /**
    * @return boolean
    * @roseuid 3C22A5470354
    */
   public boolean isItDelete()
   {
    return true;
   }

   /**
    * Access method for the publicHealthCaseLocalID property.
    * @return   the current value of the publicHealthCaseLocalID property
    * @roseuid 3EBA74B602FD
    */
   public String getPublicHealthCaseLocalId()
   {
      return publicHealthCaseLocalId;
   }

   /**
    * Sets the value of the publicHealthCaseLocalId property.
    * @param aPublicHealthCaseLocalID the new value of the publicHealthCaseLocalID
    * property
    * @param aPublicHealthCaseLocalId
    * @roseuid 3EBA74B70005
    */
   public void setPublicHealthCaseLocalId(String aPublicHealthCaseLocalId)
   {
      publicHealthCaseLocalId = aPublicHealthCaseLocalId;
   }

   /**
    * Access method for the caseClassCdTxt property.
    * @return   the current value of the caseClassCdTxt property
    * @roseuid 3EBA74B901FD
    */
   public String getCaseClassCdTxt()
   {
      return caseClassCdTxt;
   }

   /**
    * Sets the value of the caseClassCdTxt property.
    * @param aCaseClassCdTxt the new value of the caseClassCdTxt property
    * @roseuid 3EBA74B90301
    */
   public void setCaseClassCdTxt(String aCaseClassCdTxt)
   {
      caseClassCdTxt = aCaseClassCdTxt;
   }

   /**
    * Access method for the jurisdictionCdTxt property.
    * @return   the current value of the jurisdictionCdTxt property
    * @roseuid 3EBA74BB008D
    */
   public String getRecordStatusCd()
   {
      return recordStatusCd;
   }

   /**
    * Sets the value of the recordStatusCd property.
    * @param aRecordStatusCd the new value of the recordStatusCd property
    * @roseuid 3EBA74BB025A
    */
   public void setRecordStatusCd(String aRecordStatusCd)
   {
      recordStatusCd = aRecordStatusCd;
   }
   //Needed for Auto Resend
   public void setProgAreaCd(String aProgAreaCd){
     progAreaCd = aProgAreaCd;
   }
   public String getProgAreaCd(){
     return progAreaCd;
   }
   public void setSharedInd(String aSharedInd){
  sharedInd = aSharedInd;
  }
   public String getSharedInd(){
   return sharedInd;
}


 /**
  * @return java.lang.Long
  */
 public Long getLastChgUserId()
 {
  return null;
 }

 /**
  * @param aLastChgUserId
  */
 public void setLastChgUserId(Long aLastChgUserId)
 {

 }



 /**
  * @return java.lang.String
  */
 public String getLastChgReasonCd()
 {
  return null;
 }

 /**
  * @param aLastChgReasonCd
  */
 public void setLastChgReasonCd(String aLastChgReasonCd)
 {

 }


 /**
  * @return java.lang.String
  */
 public String getStatusCd()
 {
  return null;
 }

 /**
  * @param aStatusCd
  */
 public void setStatusCd(String aStatusCd)
 {

 }

 /**
  * @return java.sql.Timestamp
  */
 public Timestamp getStatusTime()
 {
  return null;
 }

 /**
  * @param aStatusTime
  */
 public void setStatusTime(Timestamp aStatusTime)
 {

 }

 /**
  * @return java.lang.String
  */
 public String getSuperclass()
 {
  return null;
 }

 /**
  * @return java.lang.Long
  */
 public Long getUid()
 {
  return null;
 }
  public void setUid(Long aUid)
 {
 }
 /**
  * @return java.lang.Long
  */
 public Long getProgramJurisdictionOid()
 {
  return programJurisdictionOid;
 }

 /**
  * @param aProgramJurisdictionOid
  */
 public void setProgramJurisdictionOid(Long aProgramJurisdictionOid)
 {
	 programJurisdictionOid=aProgramJurisdictionOid;
 }

 /**
  */
 public Integer getVersionCtrlNbr()
 {
    return null;
 }
 /**
  * @return java.sql.Timestamp
  */
 public Timestamp getRecordStatusTime()
 {
  return recordStatusTime;
 }

 /**
  * @param aRecordStatusTime
  */
 public void setRecordStatusTime(Timestamp aRecordStatusTime)
 {
	 this.recordStatusTime = aRecordStatusTime;
 }


  public void setAddUserName(String addUserName){
	  this.addUserName = addUserName;
  }

  public String getAddUserName(){
	  return this.addUserName;
  }

public Long getMPRUid() {
	return MPRUid;
}

public void setMPRUid(Long uid) {
	MPRUid = uid;
}

public String getPatientFullName() {
	return patientFullName;
}

public void setPatientFullName(String patientFullName) {
	this.patientFullName = patientFullName;
}

public String getPatientFullNameLnk() {
	return patientFullNameLnk;
}

public void setPatientFullNameLnk(String patientFullNameLnk) {
	this.patientFullNameLnk = patientFullNameLnk;
}

public String getConditionCodeTextLnk() {
	return conditionCodeTextLnk;
}

public void setConditionCodeTextLnk(String conditionCodeTextLnk) {
	this.conditionCodeTextLnk = conditionCodeTextLnk;
}

public String getApproveLink() {
	return approveLink;
}

public void setApproveLink(String approveLink) {
	this.approveLink = approveLink;
}

public String getRejectLink() {
	return rejectLink;
}

public void setRejectLink(String rejectLink) {
	this.rejectLink = rejectLink;
}

public String getNotificationCd() {
	return notificationCd;
}

public void setNotificationCd(String notificationCd) {
	this.notificationCd = notificationCd;
}

public String getCdNotif() {
	return cdNotif;
}

public void setCdNotif(String cdNotif) {
	this.cdNotif = cdNotif;
}

public String getRecipient() {
	return recipient;
}

public void setRecipient(String recipient) {
	this.recipient = recipient;
}

public String getExportRecFacilityUid() {
	return exportRecFacilityUid;
}

public void setExportRecFacilityUid(String exportRecFacilityUid) {
	this.exportRecFacilityUid = exportRecFacilityUid;
}

public boolean isNndAssociated() {
	return nndAssociated;
}

public void setNndAssociated(boolean nndAssociated) {
	this.nndAssociated = nndAssociated;
}

public String getCodeConverterTemp() {
	return codeConverterTemp;
}

public void setCodeConverterTemp(String codeConverterTemp) {
	this.codeConverterTemp = codeConverterTemp;
}

public String getCodeConverterCommentTemp() {
	return codeConverterCommentTemp;
}

public void setCodeConverterCommentTemp(String codeConverterCommentTemp) {
	this.codeConverterCommentTemp = codeConverterCommentTemp;
}

public String getNotificationSrtDescCd() {
	return notificationSrtDescCd;
}

public void setNotificationSrtDescCd(String notificationSrtDescCd) {
	this.notificationSrtDescCd = notificationSrtDescCd;
}

public boolean isPendingNotification() {
	return isPendingNotification;
}

public void setPendingNotification(boolean isPendingNotification) {
	this.isPendingNotification = isPendingNotification;
}

public boolean isCaseReport() {
	return isCaseReport;
}

public void setCaseReport(boolean isCaseReport) {
	this.isCaseReport = isCaseReport;
}

public String getCurrSexCdDesc() {
	return currSexCdDesc;
}

public void setCurrSexCdDesc(String currSexCdDesc) {
	this.currSexCdDesc = currSexCdDesc;
}

public boolean isShareAssocaited() {
	return shareAssocaited;
}

public void setShareAssocaited(boolean shareAssocaited) {
	this.shareAssocaited = shareAssocaited;
}

public String getNndInd() {
	return nndInd;
}

public void setNndInd(String nndInd) {
	this.nndInd = nndInd;
}

}
