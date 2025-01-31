package gov.cdc.nedss.act.ctcontact.dt;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.StringUtils;

import java.lang.reflect.Field;
import java.sql.Timestamp;

/**
 * Title:CTContactDT
 * Description: Copyright: Copyright (c) 2009
 * Company:CSC
 * @author: NBS project team
 * @version 3.1 ContactTracing
 */
public class CTContactDT extends AbstractVO implements RootDTInterface {
	private static final long serialVersionUID = 1L;

	  private Long ctContactUid;
	  private String localId ;
	  private Long subjectEntityUid;
	  private Long contactEntityUid;
	  private Long subjectEntityPhcUid;
	  private Long contactEntityPhcUid;
	  private Long thirdPartyEntityUid;
	  private Long thirdPartyEntityPhcUid;
	  private String recordStatusCd;
	  private Timestamp recordStatusTime;
	  private Long addUserId;
	  private Timestamp addTime;
	  private Timestamp lastChgTime;
	  private Long lastChgUserId;
 	  private String progAreaCd;
	  private String jurisdictionCd;
	  private Long programJurisdictionOid;
	  private String sharedIndCd;
	  private String contactStatus;
	  private String priorityCd;
	  private String groupNameCd;
	  private Timestamp investigatorAssignedDate;
	  private String dispositionCd;
	  private Timestamp dispositionDate;
	  private Timestamp namedOnDate;
	  private String relationshipCd;
	  private String healthStatusCd;
	  private String exposureSiteTypeCd;
	  private Timestamp firstExposedDate;
	  private Timestamp lastExposedDate;
	  private String txt;
	  private String symptomCd;
	  private Timestamp symptomOnsetDate;
	  private String symptomTxt;
	  private String riskFactorCd;
	  private String riskFactorTxt;
	  private String evaluationCompletedCd;
	  private Timestamp evaluationDate;
	  private String evaluationTxt;
	  private String treatmentInitiatedCd;
	  private Timestamp treatmentStartDate;
	  private String treatmentNotStartRsnCd;
	  private String treatmentEndCd;
	  private Timestamp treatmentEndDate;
	  private String treatmentNotEndRsnCd;
	  private String treatmentTxt;
	  private Integer versionCtrlNbr;
	  private String addUserName;
	  private String lastChgUserName;
	  private String processingDecisionCd;
	  private String subEntityEpilinkId;
	  private String conEntityEpilinkId;
	  private Long namedDuringInterviewUid;
	  private String contactReferralBasisCd;
	  private boolean isMergeCaseInd;


	public boolean getIsMergeCaseInd() {
		return isMergeCaseInd;
	}
	public void setIsMergeCaseInd(boolean isMergeCaseInd) {
		this.isMergeCaseInd = isMergeCaseInd;
	}
	public Long getCtContactUid() {
		return ctContactUid;
	}
	public void setCtContactUid(Long ctContactUid) {
		this.ctContactUid = ctContactUid;
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
	public Long getSubjectEntityPhcUid() {
		return subjectEntityPhcUid;
	}
	public void setSubjectEntityPhcUid(Long subjectEntityPhcUid) {
		this.subjectEntityPhcUid = subjectEntityPhcUid;
	}
	public Long getContactEntityPhcUid() {
		return contactEntityPhcUid;
	}
	public void setContactEntityPhcUid(Long contactEntityPhcUid) {
		this.contactEntityPhcUid = contactEntityPhcUid;
	}
	public Long getThirdPartyEntityUid() {
		return thirdPartyEntityUid;
	}
	public void setThirdPartyEntityUid(Long thirdPartyEntityUid) {
		this.thirdPartyEntityUid = thirdPartyEntityUid;
	}
	public Long getThirdPartyEntityPhcUid() {
		return thirdPartyEntityPhcUid;
	}
	public void setThirdPartyEntityPhcUid(Long thirdPartyEntityPhcUid) {
		this.thirdPartyEntityPhcUid = thirdPartyEntityPhcUid;
	}
	public String getRecordStatusCd() {
		return recordStatusCd;
	}
	public void setRecordStatusCd(String recordStatusCd) {
		this.recordStatusCd = recordStatusCd;
	}
	public Timestamp getRecordStatusTime() {
		return recordStatusTime;
	}
	public void setRecordStatusTime(Timestamp recordStatusTime) {
		this.recordStatusTime = recordStatusTime;
	}
	public Long getAddUserId() {
		return addUserId;
	}
	public void setAddUserId(Long addUserId) {
		this.addUserId = addUserId;
	}
	public Timestamp getAddTime() {
		return addTime;
	}
	public void setAddTime(Timestamp addTime) {
		this.addTime = addTime;
	}
	public Timestamp getLastChgTime() {
		return lastChgTime;
	}
	public void setLastChgTime(Timestamp lastChgTime) {
		this.lastChgTime = lastChgTime;
	}
	public Long getLastChgUserId() {
		return lastChgUserId;
	}
	public void setLastChgUserId(Long lastChgUserId) {
		this.lastChgUserId = lastChgUserId;
	}
	public String getProgAreaCd() {
		return progAreaCd;
	}
	public void setProgAreaCd(String progAreaCd) {
		this.progAreaCd = progAreaCd;
	}
	public String getJurisdictionCd() {
		return jurisdictionCd;
	}
	public void setJurisdictionCd(String jurisdictionCd) {
		this.jurisdictionCd = jurisdictionCd;
	}
	public Long getProgramJurisdictionOid() {
		return programJurisdictionOid;
	}
	public void setProgramJurisdictionOid(Long programJurisdictionOid) {
		this.programJurisdictionOid = programJurisdictionOid;
	}
	public String getSharedIndCd() {
		return sharedIndCd;
	}
	public void setSharedIndCd(String sharedIndCd) {
		this.sharedIndCd = sharedIndCd;
	}
	public String getContactStatus() {
		return contactStatus;
	}
	public void setContactStatus(String contactStatus) {
		this.contactStatus = contactStatus;
	}
	public String getPriorityCd() {
		return priorityCd;
	}
	public void setPriorityCd(String priorityCd) {
		this.priorityCd = priorityCd;
	}
	public String getGroupNameCd() {
		return groupNameCd;
	}
	public void setGroupNameCd(String groupNameCd) {
		this.groupNameCd = groupNameCd;
	}
	public Timestamp getInvestigatorAssignedDate() {
		return investigatorAssignedDate;
	}
	public void setInvestigatorAssignedDate(Timestamp investigatorAssignedDate) {
		this.investigatorAssignedDate = investigatorAssignedDate;
	}
	public String getDispositionCd() {
		return dispositionCd;
	}
	public void setDispositionCd(String dispositionCd) {
		this.dispositionCd = dispositionCd;
	}
	public Timestamp getDispositionDate() {
		return dispositionDate;
	}
	public void setDispositionDate(Timestamp dispositionDate) {
		this.dispositionDate = dispositionDate;
	}
	public Timestamp getNamedOnDate() {
		return namedOnDate;
	}
	public void setNamedOnDate(Timestamp namedOnDate) {
		this.namedOnDate = namedOnDate;
	}
	public String getRelationshipCd() {
		return relationshipCd;
	}
	public void setRelationshipCd(String relationshipCd) {
		this.relationshipCd = relationshipCd;
	}
	public String getHealthStatusCd() {
		return healthStatusCd;
	}
	public void setHealthStatusCd(String healthStatusCd) {
		this.healthStatusCd = healthStatusCd;
	}
	public String getExposureSiteTypeCd() {
		return exposureSiteTypeCd;
	}
	public void setExposureSiteTypeCd(String exposureSiteTypeCd) {
		this.exposureSiteTypeCd = exposureSiteTypeCd;
	}
	public Timestamp getFirstExposedDate() {
		return firstExposedDate;
	}
	public void setFirstExposedDate(Timestamp firstExposedDate) {
		this.firstExposedDate = firstExposedDate;
	}
	public Timestamp getLastExposedDate() {
		return lastExposedDate;
	}
	public void setLastExposedDate(Timestamp lastExposedDate) {
		this.lastExposedDate = lastExposedDate;
	}
	public String getTxt() {
		return txt;
	}
	public void setTxt(String txt) {
		this.txt = txt;
	}
	public String getSymptomCd() {
		return symptomCd;
	}
	public void setSymptomCd(String symptomCd) {
		this.symptomCd = symptomCd;
	}
	public Timestamp getSymptomOnsetDate() {
		return symptomOnsetDate;
	}
	public void setSymptomOnsetDate(Timestamp symptomOnsetDate) {
		this.symptomOnsetDate = symptomOnsetDate;
	}
	public String getSymptomTxt() {
		return symptomTxt;
	}
	public void setSymptomTxt(String symptomTxt) {
		this.symptomTxt = symptomTxt;
	}
	public String getRiskFactorCd() {
		return riskFactorCd;
	}
	public void setRiskFactorCd(String riskFactorCd) {
		this.riskFactorCd = riskFactorCd;
	}
	public String getRiskFactorTxt() {
		return riskFactorTxt;
	}
	public void setRiskFactorTxt(String riskFactorTxt) {
		this.riskFactorTxt = riskFactorTxt;
	}
	public String getEvaluationCompletedCd() {
		return evaluationCompletedCd;
	}
	public void setEvaluationCompletedCd(String evaluationCompletedCd) {
		this.evaluationCompletedCd = evaluationCompletedCd;
	}
	public Timestamp getEvaluationDate() {
		return evaluationDate;
	}
	public void setEvaluationDate(Timestamp evaluationDate) {
		this.evaluationDate = evaluationDate;
	}
	public String getEvaluationTxt() {
		return evaluationTxt;
	}
	public void setEvaluationTxt(String evaluationTxt) {
		this.evaluationTxt = evaluationTxt;
	}
	public String getTreatmentInitiatedCd() {
		return treatmentInitiatedCd;
	}
	public void setTreatmentInitiatedCd(String treatmentInitiatedCd) {
		this.treatmentInitiatedCd = treatmentInitiatedCd;
	}
	public Timestamp getTreatmentStartDate() {
		return treatmentStartDate;
	}
	public void setTreatmentStartDate(Timestamp treatmentStartDate) {
		this.treatmentStartDate = treatmentStartDate;
	}
	public String getTreatmentNotStartRsnCd() {
		return treatmentNotStartRsnCd;
	}
	public void setTreatmentNotStartRsnCd(String treatmentNotStartRsnCd) {
		this.treatmentNotStartRsnCd = treatmentNotStartRsnCd;
	}
	public String getTreatmentEndCd() {
		return treatmentEndCd;
	}
	public void setTreatmentEndCd(String treatmentEndCd) {
		this.treatmentEndCd = treatmentEndCd;
	}
	public Timestamp getTreatmentEndDate() {
		return treatmentEndDate;
	}
	public void setTreatmentEndDate(Timestamp treatmentEndDate) {
		this.treatmentEndDate = treatmentEndDate;
	}
	public String getTreatmentNotEndRsnCd() {
		return treatmentNotEndRsnCd;
	}
	public void setTreatmentNotEndRsnCd(String treatmentNotEndRsnCd) {
		this.treatmentNotEndRsnCd = treatmentNotEndRsnCd;
	}
	public String getTreatmentTxt() {
		return treatmentTxt;
	}
	public void setTreatmentTxt(String treatmentTxt) {
		this.treatmentTxt = treatmentTxt;
	}
	public Integer getVersionCtrlNbr() {
		return versionCtrlNbr;
	}
	public void setVersionCtrlNbr(Integer versionCtrlNbr) {
		this.versionCtrlNbr = versionCtrlNbr;
	}
	public boolean isItDirty() {
		return itDirty;
	}
	public void setItDirty(boolean itDirty) {
		this.itDirty = itDirty;
	}
	public boolean isItNew() {
		return itNew;
	}
	public void setItNew(boolean itNew) {
		this.itNew = itNew;
	}
	public boolean isItDelete() {
		return itDelete;
	}
	public void setItDelete(boolean itDelete) {
		this.itDelete = itDelete;
	}
	@Override
	public boolean isEqual(Object objectname1, Object objectname2,
			Class<?> voClass) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public String getLastChgReasonCd() {
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
		return NEDSSConstants.CLASSTYPE_ACT;
	}
	@Override
	public Long getUid() {
		// TODO Auto-generated method stub
		return ctContactUid;
	}
	@Override
	public void setLastChgReasonCd(String aLastChgReasonCd) {
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

	public String toString() {
		StringBuffer sb = new StringBuffer();
		try {
			sb.append(this.getClass().getName() + "\r\n");
			Field[] f = CTContactHistDT.class.getDeclaredFields();
			for (int i = 0; i < f.length; i++) {
				if (f[i] != null)
					sb.append(f[i].getName() + ":" + f[i].get(this) + "\r\n");
			}
		} catch (Exception ignore) {
		}
		return sb.toString();
	}
	public String getAddUserName() {
		return addUserName;
	}
	public void setAddUserName(String addUserName) {
		this.addUserName = addUserName;
	}
	public String getLastChgUserName() {
		return lastChgUserName;
	}
	public void setLastChgUserName(String lastChgUserName) {
		this.lastChgUserName = lastChgUserName;
	}
	
	/**
	 * @return Timestamp getInvestigatorAssignedTime
	 */
	public String getInvestigatorAssignedDate_s() {

		if (getInvestigatorAssignedDate() == null)
			return null;
		return StringUtils.formatDate(getInvestigatorAssignedDate());
	}

	/**
	 * 
	 * @param strTime
	 */
	public void setInvestigatorAssignedDate_s(String strTime) {

		if (strTime == null)

			return;

		this.setInvestigatorAssignedDate(StringUtils
				.stringToStrutsTimestamp(strTime));
	}
	/*
	 * Processing Decision only used for STD Contact
	 */
	public String getProcessingDecisionCd() {
		return processingDecisionCd;
	}
	public void setProcessingDecisionCd(String processingDecisionCd) {
		this.processingDecisionCd = processingDecisionCd;
	}
	public String getSubEntityEpilinkId() {
		return subEntityEpilinkId;
	}
	public void setSubEntityEpilinkId(String subEntityEpilinkId) {
		this.subEntityEpilinkId = subEntityEpilinkId;
	}
	public String getConEntityEpilinkId() {
		return conEntityEpilinkId;
	}
	public void setConEntityEpilinkId(String conEntityEpilinkId) {
		this.conEntityEpilinkId = conEntityEpilinkId;
	}
	
	/**
	 * @return the namedDuringInterview (CON143) for STD
	 * Note: set to 999999 if Initiated w/o Interview
	 */
	public Long getNamedDuringInterviewUid() {
		return namedDuringInterviewUid;
	}
	public void setNamedDuringInterviewUid(Long namedDuringInterviewUid) {
		this.namedDuringInterviewUid = namedDuringInterviewUid;
	}
	/**
	 * @return the contactReferralBasisCd (CON144) for STD
	 */
	public String getContactReferralBasisCd() {
		return contactReferralBasisCd;
	}
	public void setContactReferralBasisCd(String contactReferralBasisCd) {
		this.contactReferralBasisCd = contactReferralBasisCd;
	}	
	
}