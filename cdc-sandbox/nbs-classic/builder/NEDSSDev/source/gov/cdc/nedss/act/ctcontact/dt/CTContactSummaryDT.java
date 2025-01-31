package gov.cdc.nedss.act.ctcontact.dt;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.Map;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;
/**
 * Title:CTContactDT
 * Description: Copyright: Copyright (c) 2009
 * Company:CSC
 * @author: Pradeep Sharma
 * @version 3.1Contract Tracing
 */
public class CTContactSummaryDT extends AbstractVO implements RootDTInterface {

	private static final long serialVersionUID = 1L;
	private Long ctContactUid;
	private Long contactMprUid;
	private Long subjectMprUid;
	private Timestamp namedOnDate;
	private String localId;
	private Long subjectEntityUid;
	private Long contactEntityUid;
	private String namedBy;
	private String name;
	private boolean contactNamedByPatient;
	private boolean patientNamedByContact;
	private boolean otherNamedByPatient;
	private String priorityCd;
	private String dispositionCd;
	private String priority;
	private String disposition;
	private String invDisposition;
	private String invDispositionCd;
	private Long subjectEntityPhcUid;
	private String subjectPhcLocalId;
	private Long contactEntityPhcUid;
	private String contactPhcLocalId;
	private String subjectPhcCd;
	
	private String ageReported;
	private String ageReportedUnitCd;
	private Timestamp birthTime;
	private String currSexCd;
	private String relationshipCd;
	private String conditionCd;
	
	private String ageDOBSex;
	private String description;
	private String associatedWith;
	private Timestamp createDate;


	private String contactReferralBasisCd;
	private Long namedDuringInterviewUid;
	private Long thirdPartyEntityPhcUid; //this is really Other Infected Investigation
	private Long thirdPartyEntityUid; //this is really Other Infected Patient
	private String contactProcessingDecisionCd;
	private String contactProcessingDecision;
	private String subjectName;
	private String contactName;
	private String otherInfectedPatientName;
	private String sourceDispositionCd;
	private String sourceCurrentSexCd;
	private String sourceInterviewStatusCd;
	private String sourceConditionCd; //this was needed because for Syphillis the code could be Congenital
	private String progAreaCd;
	private Timestamp interviewDate;
	
	private Map<Object, Object> associatedMap;
	
	public Map<Object, Object> getAssociatedMap() {
		return associatedMap;
	}


	public void setAssociatedMap(Map<Object, Object> associatedMap) {
		this.associatedMap = associatedMap;
	}


	public Timestamp getCreateDate() {
		return createDate;
	}


	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getAssociatedWith() {
		return associatedWith;
	}


	public void setAssociatedWith(String associatedWith) {
		this.associatedWith = associatedWith;
	}


	/**
	 * 
	 */
	private boolean isAssociatedToPHC;
	private String checkBoxId;
	
	public String getSubjectPhcCd() {
		return subjectPhcCd;
	}


	public void setSubjectPhcCd(String subjectPhcCd) {
		this.subjectPhcCd = subjectPhcCd;
	}


	private String contactPhcCd;;
	
	
	public String getContactPhcCd() {
		return contactPhcCd;
	}


	public void setContactPhcCd(String contactPhcCd) {
		this.contactPhcCd = contactPhcCd;
	}


	public String getContactPhcLocalId() {
		return contactPhcLocalId;
	}


	public void setContactPhcLocalId(String contactPhcLocalId) {
		this.contactPhcLocalId = contactPhcLocalId;
	}


	private Long LocalUid;
	
	public String getSubjectPhcLocalId() {
		return subjectPhcLocalId;
	}


	public void setSubjectPhcLocalId(String subjectPhcLocalId) {
		this.subjectPhcLocalId = subjectPhcLocalId;
	}


	public Long getContactEntityPhcUid() {
		return contactEntityPhcUid;
	}


	public void setContactEntityPhcUid(Long contactEntityPhcUid) {
		this.contactEntityPhcUid = contactEntityPhcUid;
	}


	public Long getLocalUid() {
		return LocalUid;
	}


	public void setLocalUid(Long localUid) {
		LocalUid = localUid;
	}


	private Long phcLocalUid;
	
	
	
	public Long getCtContactUid() {
		return ctContactUid;
	}


	public void setCtContactUid(Long ctContactUid) {
		this.ctContactUid = ctContactUid;
	}


	public Long getContactMprUid() {
		return contactMprUid;
	}


	public void setContactMprUid(Long contactMprUid) {
		this.contactMprUid = contactMprUid;
	}


	
	public Long getPhcLocalUid() {
		return phcLocalUid;
	}


	public void setPhcLocalUid(Long phcLocalUid) {
		this.phcLocalUid = phcLocalUid;
	}


	public Long getSubjectEntityPhcUid() {
		return subjectEntityPhcUid;
	}


	public void setSubjectEntityPhcUid(Long subjectEntityPhcUid) {
		this.subjectEntityPhcUid = subjectEntityPhcUid;
	}


	public String getPriorityCd() {
		return priorityCd;
	}


	public void setPriorityCd(String priorityCd) {
		this.priorityCd = priorityCd;
	}


	public String getDispositionCd() {
		return dispositionCd;
	}


	public void setDispositionCd(String dispositionCd) {
		this.dispositionCd = dispositionCd;
	}
	public String getInvDispositionCd() {
		return invDispositionCd;
	}

	public void setInvDispositionCd(String invDispositionCd) {
		this.invDispositionCd = invDispositionCd;
	}
	
	public Timestamp getNamedOnDate() {
		return namedOnDate;
	}


	public void setNamedOnDate(Timestamp namedOnDate) {
		this.namedOnDate = namedOnDate;
	}


	public String getLocalId() {
		return localId;
	}


	public void setLocalId(String localId) {
		this.localId = localId;
	}


	public Long getSubjectEntityUid() {
		return subjectEntityUid;
	}


	public void setSubjectEntityUid(Long subjectEntityUid) {
		this.subjectEntityUid = subjectEntityUid;
	}


	public Long getContactEntityUid() {
		return contactEntityUid;
	}


	public void setContactEntityUid(Long contactEntityUid) {
		this.contactEntityUid = contactEntityUid;
	}


	public String getNamedBy() {
		return namedBy;
	}


	public void setNamedBy(String namedBy) {
		this.namedBy = namedBy;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public boolean isContactNamedByPatient() {
		return contactNamedByPatient;
	}


	public void setContactNamedByPatient(boolean contactNamedByPatient) {
		this.contactNamedByPatient = contactNamedByPatient;
	}


	public boolean isPatientNamedByContact() {
		return patientNamedByContact;
	}


	public void setPatientNamedByContact(boolean patientNamedByContact) {
		this.patientNamedByContact = patientNamedByContact;
	}

	public boolean isOtherNamedByPatient() {
		return otherNamedByPatient;
	}

	public void setOtherNamedByPatient(boolean otherNamedByPatient) {
		this.otherNamedByPatient = otherNamedByPatient;
	}
	
	public String getPriority() {
		return priority;
	}


	public void setPriority(String priority) {
		this.priority = priority;
	}


	public String getDisposition() {
		return disposition;
	}

	public void setDisposition(String disposition) {
		this.disposition = disposition;
	}

	public String getInvDisposition() {
		return invDisposition;
	}

	public void setInvDisposition(String invDisposition) {
		this.invDisposition = invDisposition;
	}
	
	public String getAgeReported() {
		return ageReported;
	}


	public void setAgeReported(String ageReported) {
		this.ageReported = ageReported;
	}

	
	public String getAgeReportedUnitCd() {
		return ageReportedUnitCd;
	}


	public void setAgeReportedUnitCd(String ageReportedUnitCd) {
		this.ageReportedUnitCd = ageReportedUnitCd;
	}

	
	public Timestamp getBirthTime() {
		return birthTime;
	}


	public void setBirthTime(Timestamp birthTime) {
		this.birthTime = birthTime;
	}


	public String getCurrSexCd() {
		return currSexCd;
	}


	public void setCurrSexCd(String currSexCd) {
		this.currSexCd = currSexCd;
	}


	public String getRelationshipCd() {
		return relationshipCd;
	}


	public void setRelationshipCd(String relationshipCd) {
		this.relationshipCd = relationshipCd;
	}

	public String getConditionCd() {
		return conditionCd;
	}


	public void setConditionCd(String conditionCd) {
		this.conditionCd = conditionCd;
	}

	
	public boolean getIsAssociatedToPHC() {
		return isAssociatedToPHC;
	}


	public void setIsAssociatedToPHC(boolean isAssociatedToPHC) {
		this.isAssociatedToPHC = isAssociatedToPHC;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		try {
			sb.append(this.getClass().getName() + "\r\n");
			Field[] f = CTContactDT.class.getDeclaredFields();
			for (int i = 0; i < f.length; i++) {
				if (f[i] != null)
					sb.append(f[i].getName() + ":" + f[i].get(this) + "\r\n");
			}
		} catch (Exception ignore) {
		}
		return sb.toString();
	}
	

	public String getCheckBoxId() {
		if(isAssociatedToPHC) {
			checkBoxId = "checked=\"true\"";	
		}
		return checkBoxId;
	}

	public void setCheckBoxId(String checkBoxId) {
		this.checkBoxId = checkBoxId;
	}
	

	public String getAgeDOBSex() {
		return ageDOBSex;
	}


	public void setAgeDOBSex(String ageDOBSex) {
		this.ageDOBSex = ageDOBSex;
	}

	/**
	 * @return the referralBasisCd for STD
	 */
	public String getContactReferralBasisCd() {
		return contactReferralBasisCd;
	}

	public void setContactReferralBasisCd(String contactReferralBasisCd) {
		this.contactReferralBasisCd = contactReferralBasisCd;
	}


	/**
	 * @return the namedDuringInterviewUid
	 * Note: set to 999999 if Initiated w/o Interview
	 */
	public Long getNamedDuringInterviewUid() {
		return namedDuringInterviewUid;
	}

	/**
	 * @param namedDuringInterview 
	 * the Interview UID of the interview the contact was named in
	 */
	public void setNamedDuringInterviewUid(Long namedDuringInterviewUid) {
		this.namedDuringInterviewUid = namedDuringInterviewUid;
	}

	/**
	 * @return the Other Infected Patient Public Health Case Uid
	 */
	public Long getThirdPartyEntityPhcUid() {
		return thirdPartyEntityPhcUid;
	}

	public void setThirdPartyEntityPhcUid(Long thirdPartyEntityPhcUid) {
		this.thirdPartyEntityPhcUid = thirdPartyEntityPhcUid;
	}


	/**
	 * @return the Other Infected Patient Person Uid
	 */
	public Long getThirdPartyEntityUid() {
		return thirdPartyEntityUid;
	}

	public void setThirdPartyEntityUid(Long thirdPartyEntityUid) {
		this.thirdPartyEntityUid = thirdPartyEntityUid;
	}


	/**
	 * @return the otherInfectedPatientName
	 */
	public String getOtherInfectedPatientName() {
		return otherInfectedPatientName;
	}


	/**
	 * @param otherInfectedPatientName the otherInfectedPatientName to set
	 */
	public void setOtherInfectedPatientName(String otherInfectedPatientName) {
		this.otherInfectedPatientName = otherInfectedPatientName;
	}
	
	/**
	 * @return the contactProcessingDecisionCd
	 */
	public String getContactProcessingDecisionCd() {
		return contactProcessingDecisionCd;
	}


	/**
	 * @param contactProcessingDecisionCd the contactProcessingDecisionCd to set
	 */
	public void setContactProcessingDecisionCd(String contactProcessingDecisionCd) {
		this.contactProcessingDecisionCd = contactProcessingDecisionCd;
	}

	/**
	 * @return the code lookup text for the contactProcessingDecisionCd
	 */
	public String getContactProcessingDecision() {
		return contactProcessingDecision;
	}

	/**
	 * @param contactProcessingDecision set the text for the contactProcessingDecision
	 */
	public void setContactProcessingDecision(String contactProcessingDecision) {
		this.contactProcessingDecision = contactProcessingDecision;
	}


	/**
	 * @return the subjectName
	 */
	public String getSubjectName() {
		return subjectName;
	}


	/**
	 * @param subjectName the subjectName to set
	 */
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}


	/**
	 * @return the contactName
	 */
	public String getContactName() {
		return contactName;
	}


	/**
	 * @param contactName the contactName to set
	 */
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}


	/**
	 * @return the sourceDispositionCd
	 */
	public String getSourceDispositionCd() {
		return sourceDispositionCd;
	}


	/**
	 * @param sourceDispositionCd the sourceDispositionCd to set
	 */
	public void setSourceDispositionCd(String sourceDispositionCd) {
		this.sourceDispositionCd = sourceDispositionCd;
	}


	/**
	 * @return the sourceInterviewStatusCd
	 */
	public String getSourceInterviewStatusCd() {
		return sourceInterviewStatusCd;
	}


	/**
	 * @param sourceInterviewStatusCd the sourceInterviewStatusCd to set
	 */
	public void setSourceInterviewStatusCd(String sourceInterviewStatusCd) {
		this.sourceInterviewStatusCd = sourceInterviewStatusCd;
	}
	
	/**
	 * @return the sourceConditionCd
	 */
	public String getSourceConditionCd() {
		return sourceConditionCd;
	}


	/**
	 * @param sourceConditionCd the sourceConditionCd to set
	 */
	public void setSourceConditionCd(String sourceConditionCd) {
		this.sourceConditionCd = sourceConditionCd;
	}
	
	
	/**
	 * @return the sourceCurrentSexCd
	 */
	public String getSourceCurrentSexCd() {
		return sourceCurrentSexCd;
	}


	/**
	 * @param sourceCurrentSexCd the sourceCurrentSexCd to set
	 */
	public void setSourceCurrentSexCd(String sourceCurrentSexCd) {
		this.sourceCurrentSexCd = sourceCurrentSexCd;
	}
	
	
	/**
	 * @return the programAreaCd
	 */
	public String getProgAreaCd() {
		return progAreaCd;
	}


	/**
	 * @param progAreaCd the progAreaCd to set
	 */
	public void setProgAreaCd(String progAreaCd) {
		this.progAreaCd = progAreaCd;
	}


	@Override
	public boolean isEqual(Object objectname1, Object objectname2,
			Class<?> voClass) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public Timestamp getAddTime() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Long getAddUserId() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String getJurisdictionCd() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String getLastChgReasonCd() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Timestamp getLastChgTime() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Long getLastChgUserId() {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	public Long getProgramJurisdictionOid() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String getRecordStatusCd() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Timestamp getRecordStatusTime() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String getSharedInd() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String getStatusCd() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Timestamp getStatusTime() {
		// TODO Auto-generated method stub
		return null;
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
	public Integer getVersionCtrlNbr() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean isItDelete() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean isItDirty() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean isItNew() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void setAddTime(Timestamp aAddTime) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setAddUserId(Long aAddUserId) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setItDelete(boolean itDelete) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setItDirty(boolean itDirty) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setItNew(boolean itNew) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setJurisdictionCd(String aJurisdictionCd) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setLastChgReasonCd(String aLastChgReasonCd) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setLastChgTime(Timestamp aLastChgTime) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setLastChgUserId(Long aLastChgUserId) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public void setProgramJurisdictionOid(Long aProgramJurisdictionOid) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setRecordStatusCd(String aRecordStatusCd) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setRecordStatusTime(Timestamp aRecordStatusTime) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setSharedInd(String aSharedInd) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setStatusCd(String aStatusCd) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setStatusTime(Timestamp aStatusTime) {
		// TODO Auto-generated method stub
		
	}
	
	public Long getSubjectMprUid() {
		return subjectMprUid;
	}


	public void setSubjectMprUid(Long subjectMprUid) {
		this.subjectMprUid = subjectMprUid;
	}


	public Timestamp getInterviewDate() {
		return interviewDate;
	}


	public void setInterviewDate(Timestamp interviewDate) {
		this.interviewDate = interviewDate;
	}

}
