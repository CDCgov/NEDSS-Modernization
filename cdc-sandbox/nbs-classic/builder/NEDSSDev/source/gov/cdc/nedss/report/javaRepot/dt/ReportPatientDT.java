package gov.cdc.nedss.report.javaRepot.dt;

import java.sql.Timestamp;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;


/**
 * PatientDT form custom Report Generation
 * @author Pradeep Kumar Sharma
 *
 */
public class ReportPatientDT  extends AbstractVO implements RootDTInterface {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String reportName;
	private String patientName;
	private String reportDate;
	private String lotNumber;
	private String caseName;
	private String dx;  //diagnosis
	private String caseID;
	private String contacts;
	private String socialANAssociateContacts;
	private String originalInterviewDate;
	private String pregnantIndicator;
	private String nineHundredStatus;
	private String maritalStatus;
	private Timestamp dateClosed;
	private Timestamp dispositionDate;
	private String dispositionCode;
	private String subjectPreferredGender;
	private String currSexUnknReason;
	private Long investigationKey;
	private Double interviewKey;
	private String epiLinkId;
	private String interviewLocation;
	private String interviewType;
	private Long interviewProviderKey;
	private String transgenderIdentity;
	private String patientCurrentSex;
	private String patientSex;
	private String referralBasis;
	private Timestamp interviewDate;
	private String interviewerQEC;
	private String interviewerName;
	private String investigatorQEC;
	private String investigatorName;
	private String hivNineHundredTestInd;
	private String invCaseStatus;
	private String hivNineHundredResult;
	private String referralOOJ;
	private String initialFollowUpCd;
	private String hivPostTestNineHundredCounselling;
	private Timestamp initialInterviewerAssignmentDate;
	private Timestamp intervewerAssignmentDate;
	private String patientInterviewStatus;
	private String sourceSpread;
	private String ffupDispoDesc;
	private Timestamp confirmationDate;
	private String femalePartners;
	private String malePartners;
	private String transgenderPartners;
	private String contactProcessingDecisionDesc;
	private String contactDispositionDesc;
	private Timestamp contactDispositionDate;
	private String contactReferralBasis;
	private String contactSourceSpread;
	private Timestamp contactInvestigationStartDate;
	private Double contactInterviewKey;
	private Double contactRecordKey;




	public Timestamp getInterviewDate() {
		return interviewDate;
	}

	public void setInterviewDate(Timestamp interviewDate) {
		this.interviewDate = interviewDate;
	}

	public Timestamp getInitialInterviewerAssignmentDate() {
		return initialInterviewerAssignmentDate;
	}

	public void setInitialInterviewerAssignmentDate(Timestamp initialAssignmentDate) {
		this.initialInterviewerAssignmentDate = initialInterviewerAssignmentDate;
	}

	public String getInterviewerQEC() {
		return interviewerQEC;
	}

	public void setInterviewerQEC(String interviewerQEC) {
		this.interviewerQEC = interviewerQEC;
	}

	public String getInterviewerName() {
		return interviewerName;
	}

	public void setInterviewerName(String interviewerName) {
		this.interviewerName = interviewerName;
	}

	public String getInvestigatorQEC() {
		return investigatorQEC;
	}

	public void setInvestigatorQEC(String investigatorQEC) {
		this.investigatorQEC = investigatorQEC;
	}

	public String getInvestigatorName() {
		return investigatorName;
	}

	public void setInvestigatorName(String investigatorName) {
		this.investigatorName = investigatorName;
	}

	public String getHivNineHundredTestInd() {
		return hivNineHundredTestInd;
	}

	public void setHivNineHundredTestInd(String hivNineHundredTestInd) {
		this.hivNineHundredTestInd = hivNineHundredTestInd;
	}

	public String getInvCaseStatus() {
		return invCaseStatus;
	}

	public void setInvCaseStatus(String invCaseStatus) {
		this.invCaseStatus = invCaseStatus;
	}

	public String getHivNineHundredResult() {
		return hivNineHundredResult;
	}

	public void setHivNineHundredResult(String hivNineHundredResult) {
		this.hivNineHundredResult = hivNineHundredResult;
	}

	public String getReferralOOJ() {
		return referralOOJ;
	}

	public void setReferralOOJ(String referralOOJ) {
		this.referralOOJ = referralOOJ;
	}

	public String getInitialFollowUpCd() {
		return initialFollowUpCd;
	}

	public void setInitialFollowUpCd(String initialFollowUpCd) {
		this.initialFollowUpCd = initialFollowUpCd;
	}

	public String getHivPostTestNineHundredCounselling() {
		return hivPostTestNineHundredCounselling;
	}

	public void setHivPostTestNineHundredCounselling(
			String hivPostTestNineHundredCounselling) {
		this.hivPostTestNineHundredCounselling = hivPostTestNineHundredCounselling;
	}

	public Timestamp getIntervewerAssignmentDate() {
		return intervewerAssignmentDate;
	}

	public void setIntervewerAssignmentDate(Timestamp intervewerAssignmentDate) {
		this.intervewerAssignmentDate = intervewerAssignmentDate;
	}

	public String getPatientInterviewStatus() {
		return patientInterviewStatus;
	}

	public void setPatientInterviewStatus(String patientInterviewStatus) {
		this.patientInterviewStatus = patientInterviewStatus;
	}

	public String getSourceSpread() {
		return sourceSpread;
	}

	public void setSourceSpread(String sourceSpread) {
		this.sourceSpread = sourceSpread;
	}

	public String getFfupDispoDesc() {
		return ffupDispoDesc;
	}

	public void setFfupDispoDesc(String ffupDispoDesc) {
		this.ffupDispoDesc = ffupDispoDesc;
	}

	public Timestamp getConfirmationDate() {
		return confirmationDate;
	}

	public void setConfirmationDate(Timestamp confirmationDate) {
		this.confirmationDate = confirmationDate;
	}

	public String getFemalePartners() {
		return femalePartners;
	}

	public void setFemalePartners(String femalePartners) {
		this.femalePartners = femalePartners;
	}

	public String getMalePartners() {
		return malePartners;
	}

	public void setMalePartners(String malePartners) {
		this.malePartners = malePartners;
	}

	public String getTransgenderPartners() {
		return transgenderPartners;
	}

	public void setTransgenderPartners(String transgenderPartners) {
		this.transgenderPartners = transgenderPartners;
	}

	public String getDispositionCode() {
		return dispositionCode;
	}

	public void setDispositionCode(String dispositionCode) {
		this.dispositionCode = dispositionCode;
	}

	public String getReferralBasis() {
		return referralBasis;
	}

	public void setReferralBasis(String referralBasis) {
		this.referralBasis = referralBasis;
	}

	public String getPatientSex() {
		return patientSex;
	}

	public void setPatientSex(String patientSex) {
		this.patientSex = patientSex;
	}

	public String getGender() {
		if(transgenderIdentity!=null)
		return transgenderIdentity;
		else if(currSexUnknReason!=null)
			return currSexUnknReason;
		else if(patientCurrentSex!=null)
			return patientCurrentSex;
		else
			return null;

	}

	public String getPatientCurrentSex() {
		return patientCurrentSex;
	}
	public void setPatientCurrentSex(String patientCurrentSex) {
		this.patientCurrentSex = patientCurrentSex;
	}
	public Long getInterviewKey() {
		if (interviewKey == null)
			return null;
		return Math.round(interviewKey);
	}
	public void setInterviewKey(Double interviewKey) {
		this.interviewKey = interviewKey;
	}
	public String getEpiLinkId() {
		return epiLinkId;
	}
	public void setEpiLinkId(String epiLinkId) {
		this.epiLinkId = epiLinkId;
	}
	public String getInterviewLocation() {
		return interviewLocation;
	}
	public void setInterviewLocation(String interviewLocation) {
		this.interviewLocation = interviewLocation;
	}
	public String getInterviewType() {
		return interviewType;
	}
	public void setInterviewType(String interviewType) {
		this.interviewType = interviewType;
	}
	public Long getInterviewProviderKey() {
		return interviewProviderKey;
	}
	public void setInterviewProviderKey(Long interviewProviderKey) {
		this.interviewProviderKey = interviewProviderKey;
	}
	public String getTransgenderIdentity() {
		return transgenderIdentity;
	}
	public void setTransgenderIdentity(String transgenderIdentity) {
		this.transgenderIdentity = transgenderIdentity;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Long getInvestigationKey() {
		return investigationKey;
	}
	public void setInvestigationKey(Long investigationKey) {
		this.investigationKey = investigationKey;
	}
	public String getSubjectPreferredGender() {
		return subjectPreferredGender;
	}
	public void setSubjectPreferredGender(String subjectPreferredGender) {
		this.subjectPreferredGender = subjectPreferredGender;
	}
	public String getCurrSexUnknReason() {
		return currSexUnknReason;
	}
	public void setCurrSexUnknReason(String currSexUnknReason) {
		this.currSexUnknReason = currSexUnknReason;
	}
	public Timestamp getDispositionDate() {
		return dispositionDate;
	}
	public void setDispositionDate(Timestamp dispositionDate) {
		this.dispositionDate = dispositionDate;
	}
	public String getPregnantIndicator() {
		return pregnantIndicator;
	}
	public void setPregnantIndicator(String pregnantIndicator) {
		this.pregnantIndicator = pregnantIndicator;
	}
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public String getReportName() {
		return reportName;
	}
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	public String getReportDate() {
		return reportDate;
	}
	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}
	public String getLotNumber() {
		return lotNumber;
	}
	public void setLotNumber(String lotNumber) {
		this.lotNumber = lotNumber;
	}
	public String getCaseName() {
		return caseName;
	}
	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}
	public String getDx() {
		return dx;
	}
	public void setDx(String dx) {
		this.dx = dx;
	}
	public String getCaseID() {
		return caseID;
	}
	public void setCaseID(String caseID) {
		this.caseID = caseID;
	}
	public String getContacts() {
		return contacts;
	}
	public void setContacts(String contacts) {
		this.contacts = contacts;
	}
	public String getSocialANAssociateContacts() {
		return socialANAssociateContacts;
	}
	public void setSocialANAssociateContacts(String socialANAssociateContacts) {
		this.socialANAssociateContacts = socialANAssociateContacts;
	}
	public String getOriginalInterviewDate() {
		return originalInterviewDate;
	}
	public void setOriginalInterviewDate(String originalInterviewDate) {
		this.originalInterviewDate = originalInterviewDate;
	}
	public String getNineHundredStatus() {
		return nineHundredStatus;
	}
	public void setNineHundredStatus(String nineHundredStatus) {
		this.nineHundredStatus = nineHundredStatus;
	}
	public String getMaritalStatus() {
		return maritalStatus;
	}
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	public Timestamp getDateClosed() {
		return dateClosed;
	}
	public void setDateClosed(Timestamp dateClosed) {
		this.dateClosed = dateClosed;
	}
	public String getContactProcessingDecisionDesc() {
		return contactProcessingDecisionDesc;
	}

	public void setContactProcessingDecisionDesc(
			String contactProcessingDecisionDesc) {
		this.contactProcessingDecisionDesc = contactProcessingDecisionDesc;
	}
	public String getContactDispositionDesc() {
		return contactDispositionDesc;
	}
	public void setContactDispositionDesc(
			String contactDispositionDesc) {
		this.contactDispositionDesc = contactDispositionDesc;
	}
	public Timestamp getContactDispositionDate() {
		return contactDispositionDate;
	}

	public void setContactDispositionDate(Timestamp contactDispositionDate) {
		this.contactDispositionDate = contactDispositionDate;
	}
	
	public String getContactReferralBasis() {
		return contactReferralBasis;
	}

	public void setContactReferralBasis(String contactReferralBasis) {
		this.contactReferralBasis = contactReferralBasis;
	}
	public String getContactSourceSpread() {
		return contactSourceSpread;
	}

	public void setContactSourceSpread(String contactSourceSpread) {
		this.contactSourceSpread = contactSourceSpread;
	}
	public Timestamp getContactInvestigationStartDate() {
		return contactInvestigationStartDate;
	}

	public void setContactInvestigationStartDate(
			Timestamp contactInvestigationStartDate) {
		this.contactInvestigationStartDate = contactInvestigationStartDate;
	}	
	
	public Long getContactInterviewKey() {
		if (contactInterviewKey == null)
			return null;
		return Math.round(contactInterviewKey);
		
	}

	public void setContactInterviewKey(Double contactInterviewKey) {
		this.contactInterviewKey = contactInterviewKey;
	}

	public Long getContactRecordKey() {
		if (contactRecordKey == null)
			return null;
		return Math.round(contactRecordKey);
	}
	public void setContactRecordKey(Double contactRecordKey) {
		this.contactRecordKey = contactRecordKey;
	}

	@Override
	public Long getLastChgUserId() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setLastChgUserId(Long aLastChgUserId) {
		// TODO Auto-generated method stub

	}
	@Override
	public String getJurisdictionCd() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setJurisdictionCd(String aJurisdictionCd) {
		// TODO Auto-generated method stub

	}
	@Override
	public String getProgAreaCd() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setProgAreaCd(String aProgAreaCd) {
		// TODO Auto-generated method stub

	}
	@Override
	public Timestamp getLastChgTime() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setLastChgTime(Timestamp aLastChgTime) {
		// TODO Auto-generated method stub

	}
	@Override
	public String getLocalId() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setLocalId(String aLocalId) {
		// TODO Auto-generated method stub

	}
	@Override
	public Long getAddUserId() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setAddUserId(Long aAddUserId) {
		// TODO Auto-generated method stub

	}
	@Override
	public String getLastChgReasonCd() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setLastChgReasonCd(String aLastChgReasonCd) {
		// TODO Auto-generated method stub

	}
	@Override
	public String getRecordStatusCd() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setRecordStatusCd(String aRecordStatusCd) {
		// TODO Auto-generated method stub

	}
	@Override
	public Timestamp getRecordStatusTime() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setRecordStatusTime(Timestamp aRecordStatusTime) {
		// TODO Auto-generated method stub

	}
	@Override
	public String getStatusCd() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setStatusCd(String aStatusCd) {
		// TODO Auto-generated method stub

	}
	@Override
	public Timestamp getStatusTime() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setStatusTime(Timestamp aStatusTime) {
		// TODO Auto-generated method stub

	}
	@Override
	public String getSuperclass() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Long getUid() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setAddTime(Timestamp aAddTime) {
		// TODO Auto-generated method stub

	}
	@Override
	public Timestamp getAddTime() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean isItNew() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void setItNew(boolean itNew) {
		// TODO Auto-generated method stub

	}
	@Override
	public boolean isItDirty() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void setItDirty(boolean itDirty) {
		// TODO Auto-generated method stub

	}
	@Override
	public boolean isItDelete() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void setItDelete(boolean itDelete) {
		// TODO Auto-generated method stub

	}
	@Override
	public Long getProgramJurisdictionOid() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setProgramJurisdictionOid(Long aProgramJurisdictionOid) {
		// TODO Auto-generated method stub

	}
	@Override
	public String getSharedInd() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setSharedInd(String aSharedInd) {
		// TODO Auto-generated method stub

	}
	@Override
	public Integer getVersionCtrlNbr() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean isEqual(Object objectname1, Object objectname2,
			Class<?> voClass) {
		// TODO Auto-generated method stub
		return false;
	}




}
