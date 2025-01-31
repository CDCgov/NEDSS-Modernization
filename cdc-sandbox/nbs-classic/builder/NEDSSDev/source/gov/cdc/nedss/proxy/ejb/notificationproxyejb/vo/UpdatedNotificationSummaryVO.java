
/**
 * Title:        UpdatedNotificationSummaryVO
 * Description:  UpdatedNotificationSummaryVO is a value object that represents a updated notification 
 * 				 summary object . It extends AbstractVO. It has getting and setting methods, 
 * 				 and flags for its properties.
 * Copyright:    Copyright (c) 2002
 * Company:      Computer Sciences Corporation
 * @author:      NEDSS Development Team
 * @version      NBS1.1.3 SP2
 */

package gov.cdc.nedss.proxy.ejb.notificationproxyejb.vo;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

import java.sql.Timestamp;

public class UpdatedNotificationSummaryVO
	extends AbstractVO
	implements RootDTInterface {

	private String sharedInd = null;
	private boolean caseStatusChg = false;
	private Integer versionCtrlNbr = null;
	private String jurisdiction;
	private String submittedBy;
	private String caseStatus;
	private String conditionLink;
	private String recipient;
	private String notificationCd;
	
	private boolean removeFlag = false;
	/**
	 * from UpdatedNotificationDT.notificationtionUid
	 */
	private Long notificationUid;

	/**
	 * from UpdatedNotificationDT.addTime
	 */
	private Timestamp addTime;

	/**
	 * from PublicHealthCaseDT.cd
	 */
	private String cd;

	/**
	 * from PublicHealthCase.caseClassCd
	 */
	private String caseClassCd;

	/**
	 * from UpdaetdNotificationDT.lastChgTime
	 */
	private Timestamp lastChgTime;

	private String addUserName;
	/**
	 * from UpdatedNotificationDT.addUserId
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

	private String statusCd;
	private String lastNm;
	private String firstNm;
	private String progAreaCd;
	private String patientFullName;
	private String patientFullNameLnk;
	private String decoratedCaseClassCdTxt;
	private String nndInd;

	public String getNndInd() {
		return nndInd;
	}

	public void setNndInd(String nndInd) {
		this.nndInd = nndInd;
	}

	public UpdatedNotificationSummaryVO() {

	}

	/**
	 * Access method for the notificationUid property.
	 *
	 * @return   the current value of the notificationUid property
	 */
	public Long getNotificationUid() {
		return notificationUid;
	}

	/**
	 * Sets the value of the notificationUid property.
	 *
	 * @param aNotificationUid the new value of the notificationUid property
	 */
	public void setNotificationUid(Long aNotificationUid) {
		notificationUid = aNotificationUid;
	}

	/**
	 * Access method for the addTime property.
	 *
	 * @return   the current value of the addTime property
	 */
	public Timestamp getAddTime() {
		return addTime;
	}

	/**
	 * Sets the value of the addTime property.
	 *
	 * @param aAddTime the new value of the addTime property
	 */
	public void setAddTime(Timestamp aAddTime) {
		addTime = aAddTime;
	}

	/**
	 * Access method for the cd property.
	 *
	 * @return   the current value of the cd property
	 */
	public String getCd() {
		return cd;
	}

	/**
	 * Sets the value of the cd property.
	 *
	 * @param aCd the new value of the cd property
	 */
	public void setCd(String aCd) {
		cd = aCd;
	}

	/**
	 * Access method for the caseClassCd property.
	 *
	 * @return   the current value of the caseClassCd property
	 */
	public String getCaseClassCd() {
		return caseClassCd;
	}

	/**
	 * Sets the value of the caseClassCd property.
	 *
	 * @param aCaseClassCd the new value of the caseClassCd property
	 */
	public void setCaseClassCd(String aCaseClassCd) {
		caseClassCd = aCaseClassCd;
	}

	/**
	 * Access method for the localId property.
	 *
	 * @return   the current value of the localId property
	 */
	public String getLocalId() {
		throw new UnsupportedOperationException("getLocalId() not supported");
	}

	/**
	 * Sets the value of the localId property.
	 *
	 * @param aLocalId the new value of the localId property
	 */
	public void setLocalId(String aLocalId) {
		throw new UnsupportedOperationException("setLocalId() not supported");
	}

	/**
	 * Access method for the lastChgTime property.
	 *
	 * @return   the current value of the lastChgTime property
	 */
	public Timestamp getLastChgTime() {
		return lastChgTime;
	}

	/**
	 * Sets the value of the lastChgTime property.
	 *
	 * @param aLastChgTime the new value of the lastChgTime property
	 */
	public void setLastChgTime(Timestamp aLastChgTime) {
		lastChgTime = aLastChgTime;
	}

	/**
	 * Access method for the addUserId property.
	 *
	 * @return   the current value of the addUserId property
	 */
	public Long getAddUserId() {
		return addUserId;
	}

	/**
	 * Sets the value of the addUserId property.
	 *
	 * @param aAddUserId the new value of the addUserId property
	 */
	public void setAddUserId(Long aAddUserId) {
		addUserId = aAddUserId;
	}

	/**
	 * Access method for the jurisdictionCd property.
	 *
	 * @return   the current value of the jurisdictionCd property
	 */
	public String getJurisdictionCd() {
		return jurisdictionCd;
	}

	/**
	 * Sets the value of the jurisdictionCd property.
	 *
	 * @param aJurisdictionCd the new value of the jurisdictionCd property
	 */
	public void setJurisdictionCd(String aJurisdictionCd) {
		jurisdictionCd = aJurisdictionCd;
	}

	/**
	 * Access method for the publicHealthCaseUid property.
	 *
	 * @return   the current value of the publicHealthCaseUid property
	 */
	public Long getPublicHealthCaseUid() {
		return publicHealthCaseUid;
	}

	/**
	 * Sets the value of the publicHealthCaseUid property.
	 *
	 * @param aPublicHealthCaseUid the new value of the publicHealthCaseUid property
	 */
	public void setPublicHealthCaseUid(Long aPublicHealthCaseUid) {
		publicHealthCaseUid = aPublicHealthCaseUid;
	}

	/**
	 * Access method for the cdTxt property.
	 *
	 * @return   the current value of the cdTxt property
	 */
	public String getCdTxt() {
		return cdTxt;
	}

	/**
	 * Sets the value of the cdTxt property.
	 *
	 * @param aCdTxt the new value of the cdTxt property
	 */
	public void setCdTxt(String aCdTxt) {
		cdTxt = aCdTxt;
	}

	/**
	 * Access method for the jurisdictionCdTxt property.
	 *
	 * @return   the current value of the jurisdictionCdTxt property
	 */
	public String getJurisdictionCdTxt() {
		return jurisdictionCdTxt;
	}

	/**
	 * Sets the value of the jurisdictionCdTxt property.
	 *
	 * @param aJurisdictionCdTxt the new value of the jurisdictionCdTxt property
	 */
	public void setJurisdictionCdTxt(String aJurisdictionCdTxt) {
		jurisdictionCdTxt = aJurisdictionCdTxt;
	}

	/**
	 * Access method for the lastNm property.
	 *
	 * @return   the current value of the lastNm property
	 */
	public String getLastNm() {
		return lastNm;
	}

	/**
	 * Sets the value of the lastNm property.
	 *
	 * @param aLastNm the new value of the lastNm property
	 */
	public void setLastNm(String aLastNm) {
		lastNm = aLastNm;
	}

	/**
	 * Access method for the firstNm property.
	 *
	 * @return   the current value of the firstNm property
	 */
	public String getFirstNm() {
		return firstNm;
	}

	/**
	 * Sets the value of the firstNm property.
	 *
	 * @param aFirstNm the new value of the firstNm property
	 */
	public void setFirstNm(String aFirstNm) {
		firstNm = aFirstNm;
	}

	/**
	 * @param objectname1
	 * @param objectname2
	 * @param voClass
	 * @return boolean
	 * @roseuid 3C22A54702FA
	 */
	public boolean isEqual(
		java.lang.Object objectname1,
		java.lang.Object objectname2,
		Class<?> voClass) {
		return true;
	}

	/**
	 * @param itDirty
	 * @roseuid 3C22A547030E
	 */
	public void setItDirty(boolean itDirty) {
		this.itDirty = itDirty;
	}

	/**
	 * @return boolean
	 * @roseuid 3C22A5470322
	 */
	public boolean isItDirty() {
		return this.itDirty;
	}

	/**
	 * @param itNew
	 * @roseuid 3C22A547032C
	 */
	public void setItNew(boolean itNew) {
		this.itNew = itNew;
	}

	/**
	 * @return boolean
	 * @roseuid 3C22A5470337
	 */
	public boolean isItNew() {
		return itNew;
	}

	/**
	 * @param itDelete
	 * @roseuid 3C22A5470340
	 */
	public void setItDelete(boolean itDelete) {

	}

	/**
	 * @return boolean
	 * @roseuid 3C22A5470354
	 */
	public boolean isItDelete() {
		return true;
	}

	/**
	 * Access method for the publicHealthCaseLocalID property.
	 * @return   the current value of the publicHealthCaseLocalID property
	 * @roseuid 3EBA74B602FD
	 */
	public String getPublicHealthCaseLocalId() {
		return publicHealthCaseLocalId;
	}

	/**
	 * Sets the value of the publicHealthCaseLocalId property.
	 * @param aPublicHealthCaseLocalID the new value of the publicHealthCaseLocalID
	 * property
	 * @param aPublicHealthCaseLocalId
	 * @roseuid 3EBA74B70005
	 */
	public void setPublicHealthCaseLocalId(String aPublicHealthCaseLocalId) {
		publicHealthCaseLocalId = aPublicHealthCaseLocalId;
	}

	/**
	 * Access method for the caseClassCdTxt property.
	 * @return   the current value of the caseClassCdTxt property
	 * @roseuid 3EBA74B901FD
	 */
	public String getCaseClassCdTxt() {
		return caseClassCdTxt;
	}

	/**
	 * Sets the value of the caseClassCdTxt property.
	 * @param aCaseClassCdTxt the new value of the caseClassCdTxt property
	 * @roseuid 3EBA74B90301
	 */
	public void setCaseClassCdTxt(String aCaseClassCdTxt) {
		caseClassCdTxt = aCaseClassCdTxt;
	}

	/**
	 * Access method for the jurisdictionCdTxt property.
	 * @return   the current value of the jurisdictionCdTxt property
	 * @roseuid 3EBA74BB008D
	 */
	public String getRecordStatusCd() {
		return statusCd;
	}

	/**
	 * Sets the value of the recordStatusCd property.
	 * @param aRecordStatusCd the new value of the recordStatusCd property
	 * @roseuid 3EBA74BB025A
	 */
	public void setRecordStatusCd(String aRecordStatusCd) {
		statusCd = aRecordStatusCd;
	}
	//Needed for Auto Resend
	public void setProgAreaCd(String aProgAreaCd) {
		progAreaCd = aProgAreaCd;
	}
	public String getProgAreaCd() {
		return progAreaCd;
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

	public String getDecoratedCaseClassCdTxt() {
		return decoratedCaseClassCdTxt;
	}

	public void setDecoratedCaseClassCdTxt(String decoratedCaseClassCdTxt) {
		this.decoratedCaseClassCdTxt = decoratedCaseClassCdTxt;
	}

	public void setSharedInd(String aSharedInd) {
		this.sharedInd = aSharedInd;
	}
	public String getSharedInd() {
		return this.sharedInd;
	}
	/**
	 * @return java.lang.Long
	 */
	public Long getLastChgUserId() {
		return null;
	}

	/**
	 * @param aLastChgUserId
	 */
	public void setLastChgUserId(Long aLastChgUserId) {

	}

	/**
	 * @return java.lang.String
	 */
	public String getLastChgReasonCd() {
		return null;
	}

	/**
	 * @param aLastChgReasonCd
	 */
	public void setLastChgReasonCd(String aLastChgReasonCd) {

	}

	/**
	 * @return java.lang.String
	 */
	public String getStatusCd() {
		return null;
	}

	/**
	 * @param aStatusCd
	 */
	public void setStatusCd(String aStatusCd) {

	}

	/**
	 * @return java.sql.Timestamp
	 */
	public Timestamp getStatusTime() {
		return null;
	}

	/**
	 * @param aStatusTime
	 */
	public void setStatusTime(Timestamp aStatusTime) {

	}

	/**
	 * @return java.lang.String
	 */
	public String getSuperclass() {
		return null;
	}

	/**
	 * @return java.lang.Long
	 */
	public Long getUid() {
		return null;
	}
	public void setUid(Long aUid) {
	}
	/**
	 * @return java.lang.Long
	 */
	public Long getProgramJurisdictionOid() {
		return null;
	}

	/**
	 * @param aProgramJurisdictionOid
	 */
	public void setProgramJurisdictionOid(Long aProgramJurisdictionOid) {

	}

	/**
	 */
	public Integer getVersionCtrlNbr() {
		return versionCtrlNbr;
	}
	
	public String getJurisdiction() {
		return jurisdiction;
	}

	public void setJurisdiction(String jurisdiction) {
		this.jurisdiction = jurisdiction;
	}

	public String getSubmittedBy() {
		return submittedBy;
	}

	public void setSubmittedBy(String submittedBy) {
		this.submittedBy = submittedBy;
	}

	public String getCaseStatus() {
		return caseStatus;
	}

	public void setCaseStatus(String caseStatus) {
		this.caseStatus = caseStatus;
	}

	/**
	 * @return java.sql.Timestamp
	 */
	public Timestamp getRecordStatusTime() {
		return null;
	}

	/**
	 * @param aRecordStatusTime
	 */
	public void setRecordStatusTime(Timestamp aRecordStatusTime) {

	}

	public void setAddUserName(String addUserName) {
		this.addUserName = addUserName;
	}

	public String getAddUserName() {
		return this.addUserName;
	}

	/**
	 * @return
	 */
	public boolean isRemoveFlag() {
		return removeFlag;
	}

	/**
	 * @param b
	 */
	public void setRemoveFlag(boolean b) {
		removeFlag = b;
	}

	/**
	 * Return case status change flag
	 * @return caseStatusChg Flag
	 */
	public boolean isCaseStatusChg() {
		return caseStatusChg;
	}

	/**
	 * Set case status change flag
	 * @param b
	 */
	public void setCaseStatusChg(boolean b) {
		caseStatusChg = b;
	}

	/**
	 * @param integer
	 */
	public void setVersionCtrlNbr(Integer integer) {
		versionCtrlNbr = integer;
	}

	public String getConditionLink() {
		return conditionLink;
	}

	public void setConditionLink(String conditionLink) {
		this.conditionLink = conditionLink;
	}

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public String getNotificationCd() {
		return notificationCd;
	}

	public void setNotificationCd(String notificationCd) {
		this.notificationCd = notificationCd;
	}
}
