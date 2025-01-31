//Source file: C:\\CDC\\Code Frameworks\\gov\\cdc\\nedss\\helpers\\InvestigationSummaryVO.java

package gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;
import gov.cdc.nedss.util.NEDSSConstants;

import java.sql.Timestamp; 

public class InvestigationSummaryVO extends AbstractVO implements RootDTInterface
{

   /**
	 * 
	 */
	private static final long serialVersionUID = -6643542172298540986L;

/**
    * from PublicHealthCaseDT.investigationStatusCd
    */
   private String investigationStatusCd;

   /**
    * from codeValueGeneral.shortDescTxt
    */
   private String investigationStatusDescTxt;

   /**
    * from PublicHealthCaseDT.localId
    */
   private String localId;

   /**
    * from PublicHealthCaseDT.activityFromTime
    */
   private Timestamp activityFromTime;

   /**
    * from PublicHealthCaseDT.addTime
    */
   private Timestamp addTime;

   /**
    * from PublicHealthCaseDT.cd
    */
   private String cd;

   /**
    * from codedValueGeneral.conditionCodeText
    */
   private String conditionCodeText;

   /**
    * from PublicHealthCaseDT.caseClassCd
    */
   private String caseClassCd;

   /**
    * from CodedValueGeneral.caseClassCodeTxt
    */
   private String caseClassCodeTxt;

   /**
    * from PublicHealthCaseDT.jurisdictionCd
    */
   private String jurisdictionCd;

   /**
    * from CodedValueGeneral.jurisdictionDescTxt
    */
   private String jurisdictionDescTxt;

   /**
    * from PublicHealthCaseDT.publicHeathCaseUid
    * (Not for display purposes)
    */
   private Long publicHealthCaseUid;

   /**
    * from PublicHealthCaseDT.statusCd
    */
   private String statusCd;

   /**
    * from PublicHealthCaseDT.recordStatusCd
    */
   private String recordStatusCd;

   private String investigatorFirstName;
   private String investigatorLastName;
   private String investigatorLocalId;
   private String patientLastName;
   private String patientFirstName;
   private Long patientRevisionUid;
   private Long MPRUid;
   private String investigatorFullName;
   private String patientFullName;
   private String patientFullNameLnk;
   private String conditionCodeTextLnk;
   private String notifRecordStatusCd;
   private boolean isAssociated;
   private String[] selectedcheckboxIds;
   private String checkBoxId;
   private String dispositionCd;
   private String progAreaCd;
   private String disabled;
   private Timestamp actFromTime;
   private String currProcessStateCd = null;
   private String coinfectionId;
   private Integer versionCtrlNbr;
   private String disposition;
   private String patIntvStatusCd;
   private String epiLinkId;
   private Long investigatorMPRUid;
   private Long observationUid;
   private String uniqueMapKey;
   private String personLocalId;
   private String currSexCd;
   private java.sql.Timestamp birthTime;
   private String investigationFormCode;

   
   
   public String getInvestigationFormCode() {
	return investigationFormCode;
}

public void setInvestigationFormCode(String investigationFormCode) {
	this.investigationFormCode = investigationFormCode;
}

/**
 * @return the currentProcessStateCd
 */
public String getCurrProcessStateCd() {
	return currProcessStateCd;
}

/**
 * @param currentProcessStateCd the currentProcessStateCd to set
 */
public void setCurrProcessStateCd(String currProcessStateCd) {
	this.currProcessStateCd = currProcessStateCd;
}

/**
 * @return the actFromTime
 */
public Timestamp getActFromTime() {
	return actFromTime;
}

/**
 * @param actFromTime the actFromTime to set
 */
public void setActFromTime(Timestamp actFromTime) {
	this.actFromTime = actFromTime;
}

/**
 * @return the disabled
 */
public String getDisabled() {
	return disabled;
}

/**
 * @param disabled the disabled to set
 */
public void setDisabled(String disabled) {
	this.disabled = disabled;
}

/**
    * @roseuid 3C02DD440053
    */
   public InvestigationSummaryVO()
   {

   }

   /**
    * Access method for the investigationStatusCd property.
    *
    * @return   the current value of the investigationStatusCd property
    */
   public String getInvestigationStatusCd()
   {
      return investigationStatusCd;
   }

   /**
    * Sets the value of the investigationStatusCd property.
    *
    * @param aInvestigationStatusCd the new value of the investigationStatusCd property
    */
   public void setInvestigationStatusCd(String aInvestigationStatusCd)
   {
      investigationStatusCd = aInvestigationStatusCd;
   }

   /**
    * Access method for the investigationStatusDescTxt property.
    *
    * @return   the current value of the investigationStatusDescTxt property
    */
   public String getInvestigationStatusDescTxt()
   {
      return investigationStatusDescTxt;
   }

   /**
    * Sets the value of the investigationStatusDescTxt property.
    *
    * @param aInvestigationStatusDescTxt the new value of the investigationStatusDescTxt property
    */
   public void setInvestigationStatusDescTxt(String aInvestigationStatusDescTxt)
   {
      investigationStatusDescTxt = aInvestigationStatusDescTxt;
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
    * Access method for the activityFromTime property.
    *
    * @return   the current value of the activityFromTime property
    */
   public Timestamp getActivityFromTime()
   {

	  return activityFromTime;
   }

   public Timestamp getActivityFromTime_s()
   {
	  return activityFromTime;
   }
   /**
    * Sets the value of the activityFromTime property.
    *
    * @param aActivityFromTime the new value of the activityFromTime property
    */
   public void setActivityFromTime(Timestamp aActivityFromTime)
   {
      activityFromTime = aActivityFromTime;
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
    * Sets the value of the conditionCodeText property.
    *
    * @param aConditionCodeText the new value of the cd property
    */
   public void setConditionCodeText(String aConditionCodeText)
   {
      conditionCodeText = aConditionCodeText;
   }

   /**
    * Access method for the conditionCodeText property.
    *
    * @return   the current value of the conditionCodeText property
    */
   public String getConditionCodeText()
   {
      return conditionCodeText;
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
    * Access method for the caseClassCodeTxt property.
    *
    * @return   the current value of the caseClassCodeTxt property
    */
   public String getCaseClassCodeTxt()
   {
      return caseClassCodeTxt;
   }

   /**
    * Sets the value of the caseClassCodeTxt property.
    *
    * @param aCaseClassCodeTxt the new value of the caseClassCodeTxt property
    */
   public void setCaseClassCodeTxt(String aCaseClassCodeTxt)
   {
      caseClassCodeTxt = aCaseClassCodeTxt;
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
    * Access method for the jurisdictionDescTxt property.
    *
    * @return   the current value of the jurisdictionDescTxt property
    */
   public String getJurisdictionDescTxt()
   {
      return jurisdictionDescTxt;
   }

   /**
    * Sets the value of the jurisdictionDescTxt property.
    *
    * @param aJurisdictionDescTxt the new value of the jurisdictionDescTxt property
    */
   public void setJurisdictionDescTxt(String aJurisdictionDescTxt)
   {
      jurisdictionDescTxt = aJurisdictionDescTxt;
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
    * Access method for the statusCd property.
    *
    * @return   the current value of the statusCd property
    */
   public String getStatusCd()
   {
      return statusCd;
   }

   /**
    * Sets the value of the statusCd property.
    *
    * @param aStatusCd the new value of the statusCd property
    */
   public void setStatusCd(String aStatusCd)
   {
      statusCd = aStatusCd;
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
    * @param objectname1
    * @param objectname2
    * @param voClass
    * @return boolean
    * @roseuid 3C1684EF028C
    */
   public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass)
   {
    return true;
   }

   /**
    * @param itDirty
    * @roseuid 3C1684EF039A
    */
   public void setItDirty(boolean itDirty)
   {
        this.itDirty = itDirty;
   }

   /**
    * @return boolean
    * @roseuid 3C1684F0003E
    */
   public boolean isItDirty()
   {
    return this.itDirty;
   }

   /**
    * @param itNew
    * @roseuid 3C1684F00070
    */
   public void setItNew(boolean itNew)
   {
        this.itNew = itNew;
   }

   /**
    * @return boolean
    * @roseuid 3C1684F000FD
    */
   public boolean isItNew()
   {
    return this.itNew;
   }

   /**
    * @param itDelete
    * @roseuid 3C1684F0012F
    */
   public void setItDelete(boolean itDelete)
   {
        this.itDelete = itDelete;
   }

   /**
    * @return boolean
    * @roseuid 3C1684F001A7
    */
   public boolean isItDelete()
   {
    return this.itDelete;
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
public String getProgAreaCd()
{
   return progAreaCd;
}

/**
 * @param aProgAreaCd
 */
public void setProgAreaCd(String progAreaCd)
{
	this.progAreaCd = progAreaCd;
}

/**
 * @return java.lang.String
 */
/**
 * @return java.lang.Long
 */
public Long getAddUserId()
{
 return null;
}

/**
 * @param aAddUserId
 */
public void setAddUserId(Long aAddUserId)
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
 * @return java.sql.Timestamp
 */
public Timestamp getRecordStatusTime()
{
 return null;
}

/**
 * @param aRecordStatusTime
 */
public void setRecordStatusTime(Timestamp aRecordStatusTime)
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
 return null;
}

/**
 * @param aProgramJurisdictionOid
 */
public void setProgramJurisdictionOid(Long aProgramJurisdictionOid)
{

}

/**
 * @return java.lang.String
 */
public String getSharedInd()
{
 return null;
}

/**
 * @param aSharedInd
 */
public void setSharedInd(String aSharedInd)
{

}

/**
 */
public Integer getVersionCtrlNbr()
{
   return versionCtrlNbr;
}

	/**
	 * A setter for add time
	 */
	public void setAddTime(java.sql.Timestamp aAddTime) {
		this.addTime = aAddTime;
	}

	/**
	 * A getter for add time
	 */
	public Timestamp getAddTime() {
		return addTime;
	}
    /**
 * Access method for the lastChgTime property.
 *
 * @return   the current value of the lastChgTime property
 */
public Timestamp getLastChgTime()
{
   return null;
}

/**
 * Sets the value of the lastChgTime property.
 *
 * @param aLastChgTime the new value of the lastChgTime property
 */
public void setLastChgTime(Timestamp aLastChgTime)
{

}
public String getInvestigatorFirstName() {
	return investigatorFirstName;
}

public void setInvestigatorFirstName(String investigatorFirstName) {
	this.investigatorFirstName = investigatorFirstName;
}

public String getInvestigatorLastName() {
	return investigatorLastName;
}

public void setInvestigatorLastName(String investigatorLastName) {
	this.investigatorLastName = investigatorLastName;
}

public String getInvestigatorLocalId() {
	return investigatorLocalId;
}

public void setInvestigatorLocalId(String investigatorLocalId) {
	this.investigatorLocalId = investigatorLocalId;
}

public String getPatientFirstName() {
	return patientFirstName;
}

public void setPatientFirstName(String patientFirstName) {
	this.patientFirstName = patientFirstName;
}

public String getPatientLastName() {
	return patientLastName;
}

public void setPatientLastName(String patientLastName) {
	this.patientLastName = patientLastName;
}

public Long getMPRUid() {
	return MPRUid;
}

public void setMPRUid(Long uid) {
	MPRUid = uid;
}
public String getInvestigatorFullName() {
	return investigatorFullName;
}

public void setInvestigatorFullName(String investigatorFullName) {
this.investigatorFullName = investigatorFullName;
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

public String getNotifRecordStatusCd() {
	return notifRecordStatusCd;
}

public String getNotifStatusTransCd() {
	if(notifRecordStatusCd == null)
		return notifRecordStatusCd;
	if(notifRecordStatusCd.equals(NEDSSConstants.APPROVED_STATUS))
			return "Approved";
	else if(notifRecordStatusCd.equals(NEDSSConstants.NOTIFICATION_COMPLETED))
			return "Completed";
	else if(notifRecordStatusCd.equals(NEDSSConstants.NOTIFICATION_MESSAGE_FAILED))
			return "Failed";
	else if(notifRecordStatusCd.equals(NEDSSConstants.NOTIFICATION_PENDING_CODE))
			return "Pending";
	else if(notifRecordStatusCd.equals(NEDSSConstants.NOTIFICATION_REJECTED_CODE))
			return "Rejected";

	return notifRecordStatusCd;
}

public void setNotifRecordStatusCd(String notifRecordStatusCd) {
	this.notifRecordStatusCd = notifRecordStatusCd;
}

  /**
    * Checks whether the Investigation is associated to an Observation by taking a boolean param
    * @param boolean
    */
   public void setIsAssociated(boolean flag)
   {
       isAssociated=flag;
   }

   /**
    * Gets  the is observation associated flag
    * @return boolean
    */
  public boolean getIsAssociated()
   {
      return isAssociated;
   }

  public String[] getSelectedcheckboxIds() {
		return selectedcheckboxIds;
	}

	public void setSelectedcheckboxIds(String[] selectedcheckboxIds) {
		this.selectedcheckboxIds = selectedcheckboxIds;
	}

	public String getCheckBoxId() {
		if(isAssociated) {
			checkBoxId = "checked=\"true\"";
		}
		return checkBoxId;
	}

	public void setCheckBoxId(String checkBoxId) {
		this.checkBoxId = checkBoxId;
	}
	/**
	 * Access method for the dispositionCd property.
	 * From the act_relationship add_reason_cd. Used for STD.
	 *
	 * @return   the current value of the dispositionCd property
	 */
	public String getDispositionCd()
	{
	   return dispositionCd;
	}

	/**
	 * Sets the value of the dispositionCd property.
	 *
	 * @param dispositionCd the new value of the dispositionCd property
	 */
	public void setDispositionCd(String dispositionCd)
	{
	   this.dispositionCd = dispositionCd;
	}

	public String getCoinfectionId() {
		return coinfectionId;
	}

	public void setCoinfectionId(String coinfectionId) {
		this.coinfectionId = coinfectionId;
	}

	public String getDisposition() {
		return disposition;
	}

	public void setDisposition(String disposition) {
		this.disposition = disposition;
	}
	/*
	 * Aka Lot Number
	 */
	public String getEpiLinkId() {
		return epiLinkId;
	}

	public void setEpiLinkId(String epiLinkId) {
		this.epiLinkId = epiLinkId;
	}

	public String getPatIntvStatusCd() {
		return patIntvStatusCd;
	}

	public void setPatIntvStatusCd(String patIntvStatusCd) {
		this.patIntvStatusCd = patIntvStatusCd;
	}

	/**
	 * @return the patientRevisionUid
	 */
	public Long getPatientRevisionUid() {
		return patientRevisionUid;
	}

	/**
	 * @param patientRevisionUid the patientRevisionUid to set
	 */
	public void setPatientRevisionUid(Long patientRevisionUid) {
		this.patientRevisionUid = patientRevisionUid;
	}

	public Long getInvestigatorMPRUid() {
		return investigatorMPRUid;
	}

	public void setInvestigatorMPRUid(Long investigatorMPRUid) {
		this.investigatorMPRUid = investigatorMPRUid;
	}

	public Long getObservationUid() {
		return observationUid;
	}

	public void setObservationUid(Long observationUid) {
		this.observationUid = observationUid;
	}

	public String getUniqueMapKey() {
		return uniqueMapKey;
	}

	public void setUniqueMapKey(String uniqueMapKey) {
		this.uniqueMapKey = uniqueMapKey;
	}

	public String getPersonLocalId() {
		return personLocalId;
	}

	public void setPersonLocalId(String personLocalId) {
		this.personLocalId = personLocalId;
	}

	 

	public String getCurrSexCd() {
		return currSexCd;
	}

	public void setCurrSexCd(String currSexCd) {
		this.currSexCd = currSexCd;
	}

	public java.sql.Timestamp getBirthTime() {
		return birthTime;
	}

	public void setBirthTime(java.sql.Timestamp birthTime) {
		this.birthTime = birthTime;
	}

	



}