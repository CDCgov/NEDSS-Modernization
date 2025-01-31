package gov.cdc.nedss.proxy.ejb.notificationproxyejb.vo;


import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

import java.sql.Timestamp;

public class RejectedNotificationSummaryVO extends AbstractVO
implements RootDTInterface {

	
	private static final long serialVersionUID = 1L; 
	private String sharedInd = null;
	private Integer versionCtrlNbr = null;
	private Long notificationUid;
	private Timestamp addTime;
	private String condition;
	private String caseClassCd;
	private Timestamp lastChgTime;
	private String addUserName;
	private Long addUserId;
	private String RejectedByUserName;
	private Long RejectedByUserId;
	private String jurisdictionCd;
	private String jurisdictionDescription;
	private Long publicHealthCaseUid;
	private String cdTxt;
	private String jurisdictionCdTxt;
	private String publicHealthCaseLocalId;
	private String caseStatus;
	private String caseStatusCd;
	private String PatientName;
	private String firstName;
	private String lastName;
	private Long patientUid;
	private String patientFullName;
	private String patientFullNameLnk;
	private String conditionCodeTextLnk; 
	private String recipient;
	private String notificationCd;
	private String exportRecFacilityUid;
	private String nndInd;

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
	 * @param integer
	 */
	public void setVersionCtrlNbr(Integer integer) {
		versionCtrlNbr = integer;
	}

	public Long getRejectedByUserId() {
		return RejectedByUserId;
	}

	public void setRejectedByUserId(Long rejectedByUserId) {
		RejectedByUserId = rejectedByUserId;
	}

	public String getRejectedByUserName() {
		return RejectedByUserName;
	}

	public void setRejectedByUserName(String rejectedByUserName) {
		RejectedByUserName = rejectedByUserName;
	}

	public String getProgAreaCd() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setProgAreaCd(String aProgAreaCd) {
		// TODO Auto-generated method stub
		
	}

	public String getCaseStatus() {
		return caseStatus;
	}

	public void setCaseStatus(String caseStatus) {
		this.caseStatus = caseStatus;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getJurisdictionDescription() {
		return jurisdictionDescription;
	}

	public void setJurisdictionDescription(String jurisdictionDescription) {
		this.jurisdictionDescription = jurisdictionDescription;
	}

	public String getPatientName() {
		return PatientName;
	}

	public void setPatientName(String patientName) {
		PatientName = patientName;
	}

	public Long getPatientUid() {
		return patientUid;
	}

	public void setPatientUid(Long patientUid) {
		this.patientUid = patientUid;
	}

	public String getRecordStatusCd() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setRecordStatusCd(String aRecordStatusCd) {
		// TODO Auto-generated method stub
		
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCaseStatusCd() {
		return caseStatusCd;
	}

	public void setCaseStatusCd(String caseStatusCd) {
		this.caseStatusCd = caseStatusCd;
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

	public String getExportRecFacilityUid() {
		return exportRecFacilityUid;
	}

	public void setExportRecFacilityUid(String exportRecFacilityUid) {
		this.exportRecFacilityUid = exportRecFacilityUid;
	}

	public String getNndInd() {
		return nndInd;
	}

	public void setNndInd(String nndInd) {
		this.nndInd = nndInd;
	}

}
