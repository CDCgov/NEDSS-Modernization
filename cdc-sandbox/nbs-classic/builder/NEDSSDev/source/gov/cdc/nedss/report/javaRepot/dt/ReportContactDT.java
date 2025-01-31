package gov.cdc.nedss.report.javaRepot.dt;

import java.sql.Timestamp;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;
/**
 * ReportContactDT form custom Report Generation
 * @author Pradeep Kumar Sharma
 *
 */
public class ReportContactDT extends AbstractVO implements RootDTInterface{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String contactReferralBasis;
	private String subjectEpiLinkId;
	private String contactEpiLinkId;
	private Timestamp contactFirstSexExpoDate;
	private String contactSexExpFreq;
	private Timestamp contactLastSexExpDate;
	private Timestamp dispositionDate;
	private String dispoDescription;
	private String subjectLastName;
	private String subjectFirstName;
	private String subjectMiddleName;
	private Long contactInvestigationKey;
	private Long subjectInvestigationKey;
	private Long contactMprUid;
	private String contactInvestigationCase;
	private String subjectInvestigationCase;
	private String contactLastName;
	private String contactFirstName;
	private String contactMiddleName;
		private Timestamp contactFirstNdlShareExpoDate;
	private String contactNdlShareExpFreq;
	private Timestamp contactLastNdlShareExpDate;
	private String diagnosis;
	private String diagnosisCode;
	private String gender;
	
	private String epiLinkId;
	private Timestamp interviewDate;
	private String transgenderIdentity;
	private String subjectCurrentSex;
	private String subjectSex;
	private String subjectCurrentSexUnknReason;
	private String patientCurrentSex;

	public Long getContactMprUid() {
		return contactMprUid;
	}

	public void setContactMprUid(Long contactMprUid) {
		this.contactMprUid = contactMprUid;
	}

	public String getSubjectCurrentSex() {
		return subjectCurrentSex;
	}

	public void setSubjectCurrentSex(String subjectCurrentSex) {
		this.subjectCurrentSex = subjectCurrentSex;
	}

	public String getSubjectSex() {
		return subjectSex;
	}

	public void setSubjectSex(String subjectSex) {
		this.subjectSex = subjectSex;
	}

	public String getSubjectCurrentSexUnknReason() {
		return subjectCurrentSexUnknReason;
	}

	public void setSubjectCurrentSexUnknReason(String subjectCurrentSexUnknReason) {
		this.subjectCurrentSexUnknReason = subjectCurrentSexUnknReason;
	}

	public String getTransgenderIdentity() {
		return transgenderIdentity;
	}

	public void setTransgenderIdentity(String transgenderIdentity) {
		this.transgenderIdentity = transgenderIdentity;
	}

	public String getPatientCurrentSex() {
		return patientCurrentSex;
	}

	public void setPatientCurrentSex(String patientCurrentSex) {
		this.patientCurrentSex = patientCurrentSex;
	}

	public String getContactLastName() {
		return contactLastName;
	}

	public void setContactLastName(String contactLastName) {
		this.contactLastName = contactLastName;
	}

	public String getContactFirstName() {
		return contactFirstName;
	}

	public void setContactFirstName(String contactFirstName) {
		this.contactFirstName = contactFirstName;
	}

	public String getContactMiddleName() {
		return contactMiddleName;
	}

	public void setContactMiddleName(String contactMiddleName) {
		this.contactMiddleName = contactMiddleName;
	}


	public Timestamp getInterviewDate() {
		return interviewDate;
	}

	public void setInterviewDate(Timestamp interviewDate) {
		this.interviewDate = interviewDate;
	}

	public String getContactName() {
		if (contactLastName != null && !contactLastName.trim().equals("")
				&& contactFirstName != null
				&& !contactFirstName.trim().equals(""))
			return contactLastName + "," + contactFirstName;
		else if (contactFirstName == null || contactFirstName.trim().equals(""))
			return contactLastName;
		else if (contactLastName == null || contactLastName.trim().equals(""))
			return contactFirstName;
		else
			return null;
	}

	public String getSubjectName() {
		if (subjectLastName != null && !subjectLastName.trim().equals("")
				&& subjectFirstName != null
				&& !subjectFirstName.trim().equals(""))
			return subjectLastName + "," + subjectFirstName;
		else if (subjectFirstName == null || subjectFirstName.trim().equals(""))
			return subjectLastName;
		else if (subjectLastName == null || subjectLastName.trim().equals(""))
			return subjectFirstName;
		else
			return null;
	}
	public String getGender() {
		if(patientCurrentSex!=null)
				return patientCurrentSex;
			else 
				return null;
	}
	public String getDiagnosisCd() {
		return null;
	}
	public Timestamp getContactLastSexExpDate() {
		return contactLastSexExpDate;
	}
	public void setContactLastSexExpDate(Timestamp contactLastSexExpDate) {
		this.contactLastSexExpDate = contactLastSexExpDate;
	}
	public Timestamp getContactFirstNdlShareExpoDate() {
		return contactFirstNdlShareExpoDate;
	}
	public void setContactFirstNdlShareExpoDate(
			Timestamp contactFirstNdlShareExpoDate) {
		this.contactFirstNdlShareExpoDate = contactFirstNdlShareExpoDate;
	}
	public String getContactNdlShareExpFreq() {
		return contactNdlShareExpFreq;
	}
	public void setContactNdlShareExpFreq(String contactNdlShareExpFreq) {
		this.contactNdlShareExpFreq = contactNdlShareExpFreq;
	}
	public Timestamp getContactLastNdlShareExpDate() {
		return contactLastNdlShareExpDate;
	}
	public void setContactLastNdlShareExpDate(Timestamp contactLastNdlShareExpDate) {
		this.contactLastNdlShareExpDate = contactLastNdlShareExpDate;
	}
	public String getContactInvestigationCase() {
		return contactInvestigationCase;
	}
	public void setContactInvestigationCase(String contactInvestigationCase) {
		this.contactInvestigationCase = contactInvestigationCase;
	}
	public String getSubjectInvestigationCase() {
		return subjectInvestigationCase;
	}
	public void setSubjectInvestigationCase(String subjectInvestigationCase) {
		this.subjectInvestigationCase = subjectInvestigationCase;
	}
	public Timestamp getDispositionDate() {
		return dispositionDate;
	}
	public void setDispositionDate(Timestamp dispositionDate) {
		this.dispositionDate = dispositionDate;
	}
	public String getDispCd() {
		if(dispoDescription!=null && dispoDescription.length()>2)
			return dispoDescription.trim().substring(0, 1);
		else
			return null;
	}
	public String getContactReferraCd() {
		if(contactReferralBasis!=null && contactReferralBasis.length()>2)
			return contactReferralBasis.trim().substring(0, 2);
		else
			return null;
	}

	public String getContactReferralBasis() {
		return contactReferralBasis;
	}
	public void setContactReferralBasis(String contactReferralBasis) {
		this.contactReferralBasis = contactReferralBasis;
	}
	public String getSubjectEpiLinkId() {
		return subjectEpiLinkId;
	}
	public void setSubjectEpiLinkId(String subjectEpiLinkId) {
		this.subjectEpiLinkId = subjectEpiLinkId;
	}
	public String getContactEpiLinkId() {
		return contactEpiLinkId;
	}
	public void setContactEpiLinkId(String contactEpiLinkId) {
		this.contactEpiLinkId = contactEpiLinkId;
	}
	
	public Timestamp getContactFirstSexExpoDate() {
		return contactFirstSexExpoDate;
	}
	public void setContactFirstSexExpoDate(Timestamp contactFirstSexExpoDate) {
		this.contactFirstSexExpoDate = contactFirstSexExpoDate;
	}
	public String getContactSexExpFreq() {
		return contactSexExpFreq;
	}
	public void setContactSexExpFreq(String contactSexExpFreq) {
		this.contactSexExpFreq = contactSexExpFreq;
	}
	public String getDispoDescription() {
		return dispoDescription;
	}
	public void setDispoDescription(String dispoDescription) {
		this.dispoDescription = dispoDescription;
	}
	public String getSubjectLastName() {
		return subjectLastName;
	}
	public void setSubjectLastName(String subjectLastName) {
		this.subjectLastName = subjectLastName;
	}
	public String getSubjectFirstName() {
		return subjectFirstName;
	}
	public void setSubjectFirstName(String subjectFirstName) {
		this.subjectFirstName = subjectFirstName;
	}
	public String getSubjectMiddleName() {
		return subjectMiddleName;
	}
	public void setSubjectMiddleName(String subjectMiddleName) {
		this.subjectMiddleName = subjectMiddleName;
	}
	public Long getContactInvestigationKey() {
		return contactInvestigationKey;
	}
	public void setContactInvestigationKey(Long contactInvestigationKey) {
		this.contactInvestigationKey = contactInvestigationKey;
	}
	public Long getSubjectInvestigationKey() {
		return subjectInvestigationKey;
	}
	public void setSubjectInvestigationKey(Long subjectInvestigationKey) {
		this.subjectInvestigationKey = subjectInvestigationKey;
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

	public String getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}

	public String getDiagnosisCode() {
		return diagnosisCode;
	}

	public void setDiagnosisCode(String diagnosisCode) {
		this.diagnosisCode = diagnosisCode;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEpiLinkId() {
		return epiLinkId;
	}

	public void setEpiLinkId(String epiLinkId) {
		this.epiLinkId = epiLinkId;
	}


}
