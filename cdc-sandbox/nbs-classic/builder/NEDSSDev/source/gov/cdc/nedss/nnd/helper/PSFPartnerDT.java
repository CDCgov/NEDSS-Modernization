package gov.cdc.nedss.nnd.helper;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

import java.sql.Timestamp;

/**
 * PSFIndexDT - Used to store data from PSFPartnerDT table.
 * @author Fatima Lopez Calzado
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: GDIT</p>
 * September 11 th, 2018
 * @version 1
 */

public class PSFPartnerDT extends AbstractVO implements RootDTInterface{

	private static final long serialVersionUID = 1L;
	
	private String agencyId;
	private String localClientId;
	private String clientIdSTDMIS;
	private String clientIdPSID;
	private String clientIdLocalId;
	private String clientFirstName;
	private String clientLastName;
	private Timestamp clientDOB;
	private String caseNumberPS;
	private String caseNumberEarliestPS;
	private String caseNumberSTDMIS;
	private String caseNumberLegacyId;
	private String caseNumberLocalId;
	private String partnerType;
	private String attemptToLocateOutcome;
	private String reasonForUnsuccessfulAttempt;
	private String enrollmentStatus;
	private String partnerNotifiability;
	private String actualNotificationMethod;
	private String previousHivTestValueCode;
	private String previousHIVTestResult;
	private String hivTestPerformed;
	private Timestamp sampleDate;
	private String hivTestResult;
	private String provisionOfResultValueCode;
	private String syphilisTest;
	private String syphilisTestResult;
	private String currentHIVMedicalCareStatus;
	private Timestamp firstMedicalCareAppointmentDate;
	private String currentlyOnPrEP;
	private String referredToPrEP;
	private Timestamp patientAddTime;
	private Timestamp patientLastChgTime;
	private String patientStatusCd;
	private Timestamp invAddtime;
	private Timestamp invLastChgTime;
	private String invStatusCd;
	private String invLocalId;
	private Timestamp crAddTime;
	private Timestamp crLastChgTime;
	private String crStatusCd;
	private String crLocalId;
	private Timestamp partnerDateDemographicsCollected;
	private Timestamp partnerLastChgDt;
	

	

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
	public String getAgencyId() {
		if(agencyId==null)
			agencyId="";
		return agencyId;
	}
	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
	}
	public String getLocalClientId() {
		if(localClientId==null)
			localClientId="";
		return localClientId;
	}
	public void setLocalClientId(String localClientId) {
		this.localClientId = localClientId;
	}
	public String getClientIdSTDMIS() {
		if(clientIdSTDMIS==null)
			clientIdSTDMIS="";
		return clientIdSTDMIS;
	}
	public void setClientIdSTDMIS(String clientIdSTDMIS) {
		this.clientIdSTDMIS = clientIdSTDMIS;
	}
	public String getClientIdPSID() {
		if(clientIdPSID==null)
			clientIdPSID="";
		return clientIdPSID;
	}
	public void setClientIdPSID(String clientIdPSID) {
		this.clientIdPSID = clientIdPSID;
	}
	public String getClientIdLocalId() {
		if(clientIdLocalId==null)
			clientIdLocalId="";
		return clientIdLocalId;
	}
	public void setClientIdLocalId(String clientIdLocalId) {
		this.clientIdLocalId = clientIdLocalId;
	}
	public String getClientFirstName() {
		if(clientFirstName==null)
			clientFirstName="";
		return clientFirstName;
	}
	public void setClientFirstName(String clientFirstName) {
		this.clientFirstName = clientFirstName;
	}
	public String getClientLastName() {
		if(clientLastName==null)
			clientLastName="";
		return clientLastName;
	}
	public void setClientLastName(String clientLastName) {
		this.clientLastName = clientLastName;
	}
	public Timestamp getClientDOB() {
		return clientDOB;
	}
	public void setClientDOB(Timestamp clientDOB) {
		this.clientDOB = clientDOB;
	}
	public String getCaseNumberPS() {
		if(caseNumberPS==null)
			caseNumberPS="";
		return caseNumberPS;
	}
	public void setCaseNumberPS(String caseNumberPS) {
		this.caseNumberPS = caseNumberPS;
	}
	public String getCaseNumberSTDMIS() {
		if(caseNumberSTDMIS==null)
			caseNumberSTDMIS="";
		return caseNumberSTDMIS;
	}
	public void setCaseNumberSTDMIS(String caseNumberSTDMIS) {
		this.caseNumberSTDMIS = caseNumberSTDMIS;
	}
	public String getCaseNumberLegacyId() {
		if(caseNumberLegacyId==null)
			caseNumberLegacyId="";
		return caseNumberLegacyId;
	}
	public void setCaseNumberLegacyId(String caseNumberLegacyId) {
		this.caseNumberLegacyId = caseNumberLegacyId;
	}
	public String getCaseNumberLocalId() {
		if(caseNumberLocalId==null)
			caseNumberLocalId="";
		return caseNumberLocalId;
	}
	public void setCaseNumberLocalId(String caseNumberLocalId) {
		this.caseNumberLocalId = caseNumberLocalId;
	}
	public String getPartnerType() {
		return partnerType;
	}
	public void setPartnerType(String partnerType) {
		this.partnerType = partnerType;
	}
	public String getAttemptToLocateOutcome() {
		if(attemptToLocateOutcome==null)
			attemptToLocateOutcome="";
		return attemptToLocateOutcome;
	}
	public void setAttemptToLocateOutcome(String attemptToLocateOutcome) {
		this.attemptToLocateOutcome = attemptToLocateOutcome;
	}
	public String getReasonForUnsuccessfulAttempt() {
		if(reasonForUnsuccessfulAttempt==null)
			reasonForUnsuccessfulAttempt="";
		return reasonForUnsuccessfulAttempt;
	}
	public void setReasonForUnsuccessfulAttempt(String reasonForUnsuccessfulAttempt) {
		this.reasonForUnsuccessfulAttempt = reasonForUnsuccessfulAttempt;
	}
	public String getEnrollmentStatus() {
		if(enrollmentStatus==null)
			enrollmentStatus="";
		return enrollmentStatus;
	}
	public void setEnrollmentStatus(String enrollmentStatus) {
		this.enrollmentStatus = enrollmentStatus;
	}
	public String getPartnerNotifiability() {
		if(partnerNotifiability==null)
			partnerNotifiability="";
		return partnerNotifiability;
	}
	public void setPartnerNotifiability(String partnerNotifiability) {
		this.partnerNotifiability = partnerNotifiability;
	}
	public String getActualNotificationMethod() {
		if(actualNotificationMethod==null)
			actualNotificationMethod="";
		return actualNotificationMethod;
	}
	public void setActualNotificationMethod(String actualNotificationMethod) {
		this.actualNotificationMethod = actualNotificationMethod;
	}
	public String getPreviousHivTestValueCode() {
		if(previousHivTestValueCode==null)
			previousHivTestValueCode="";
		return previousHivTestValueCode;
	}
	public void setPreviousHivTestValueCode(String previousHivTestValueCode) {
		this.previousHivTestValueCode = previousHivTestValueCode;
	}


	public Timestamp getSampleDate() {
		return sampleDate;
	}
	public void setSampleDate(Timestamp sampleDate) {
		this.sampleDate = sampleDate;
	}
	
	public String getProvisionOfResultValueCode() {
		if(provisionOfResultValueCode==null)
			provisionOfResultValueCode="";
		return provisionOfResultValueCode;
	}
	public void setProvisionOfResultValueCode(String provisionOfResultValueCode) {
		this.provisionOfResultValueCode = provisionOfResultValueCode;
	}
	public String getSyphilisTest() {
		if(syphilisTest==null)
			syphilisTest="";
		return syphilisTest;
	}
	public void setSyphilisTest(String syphilisTest) {
		this.syphilisTest = syphilisTest;
	}
	public String getSyphilisTestResult() {
		if(syphilisTestResult==null)
			syphilisTestResult="";
		return syphilisTestResult;
	}
	public void setSyphilisTestResult(String syphilisTestResult) {
		this.syphilisTestResult = syphilisTestResult;
	}
	public String getCurrentHIVMedicalCareStatus() {
		if(currentHIVMedicalCareStatus==null)
			currentHIVMedicalCareStatus="";
		return currentHIVMedicalCareStatus;
	}
	public void setCurrentHIVMedicalCareStatus(String currentHIVMedicalCareStatus) {
		this.currentHIVMedicalCareStatus = currentHIVMedicalCareStatus;
	}
	public Timestamp getFirstMedicalCareAppointmentDate() {
		return firstMedicalCareAppointmentDate;
	}
	public void setFirstMedicalCareAppointmentDate(
			Timestamp firstMedicalCareAppointmentDate) {
		this.firstMedicalCareAppointmentDate = firstMedicalCareAppointmentDate;
	}
	public String getCurrentlyOnPrEP() {
		if(currentlyOnPrEP==null)
			currentlyOnPrEP="";
		return currentlyOnPrEP;
	}
	public void setCurrentlyOnPrEP(String currentlyOnPrEP) {
		this.currentlyOnPrEP = currentlyOnPrEP;
	}
	public String getReferredToPrEP() {
		if(referredToPrEP==null)
			referredToPrEP="";
		return referredToPrEP;
	}
	public void setReferredToPrEP(String referredToPrEP) {
		this.referredToPrEP = referredToPrEP;
	}
	public Timestamp getPatientAddTime() {
		return patientAddTime;
	}
	public void setPatientAddTime(Timestamp patientAddTime) {
		this.patientAddTime = patientAddTime;
	}
	public Timestamp getPatientLastChgTime() {
		return patientLastChgTime;
	}
	public void setPatientLastChgTime(Timestamp patientLastChgTime) {
		this.patientLastChgTime = patientLastChgTime;
	}
	public String getPatientStatusCd() {
		if(patientStatusCd==null)
			patientStatusCd="";
		return patientStatusCd;
	}
	public void setPatientStatusCd(String patientStatusCd) {
		this.patientStatusCd = patientStatusCd;
	}
	public Timestamp getInvAddtime() {
		return invAddtime;
	}
	public void setInvAddtime(Timestamp invAddtime) {
		this.invAddtime = invAddtime;
	}
	public Timestamp getInvLastChgTime() {
		return invLastChgTime;
	}
	public void setInvLastChgTime(Timestamp invLastChgTime) {
		this.invLastChgTime = invLastChgTime;
	}
	public String getInvStatusCd() {
		return invStatusCd;
	}
	public void setInvStatusCd(String invStatusCd) {
		this.invStatusCd = invStatusCd;
	}
	public Timestamp getCrAddTime() {
		return crAddTime;
	}
	public void setCrAddTime(Timestamp crAddTime) {
		this.crAddTime = crAddTime;
	}
	public Timestamp getCrLastChgTime() {
		return crLastChgTime;
	}
	public void setCrLastChgTime(Timestamp crLastChgTime) {
		this.crLastChgTime = crLastChgTime;
	}
	public String getCrStatusCd() {
		if(crStatusCd==null)
			crStatusCd="";
		return crStatusCd;
	}
	public void setCrStatusCd(String crStatusCd) {
		this.crStatusCd = crStatusCd;
	}
	
	public String getInvLocalId() {
		if(invLocalId==null)
			invLocalId="";
		return invLocalId;
	}
	public void setInvLocalId(String invLocalId) {
		this.invLocalId = invLocalId;
	}
	public String getCrLocalId() {
		if(crLocalId==null)
			crLocalId="";
		return crLocalId;
	}
	public void setCrLocalId(String crLocalId) {
		this.crLocalId = crLocalId;
	}
	public String getPreviousHIVTestResult() {
		if(previousHIVTestResult==null)
			previousHIVTestResult="";
		return previousHIVTestResult;
	}
	public void setPreviousHIVTestResult(String previousHIVTestResult) {
		this.previousHIVTestResult = previousHIVTestResult;
	}
	public Timestamp getPartnerDateDemographicsCollected() {
		return partnerDateDemographicsCollected;
	}
	public void setPartnerDateDemographicsCollected(
			Timestamp partnerDateDemographicsCollected) {
		this.partnerDateDemographicsCollected = partnerDateDemographicsCollected;
	}
	public String getHivTestPerformed() {
		if(hivTestPerformed==null)
			hivTestPerformed="";
		return hivTestPerformed;
	}
	public void setHivTestPerformed(String hivTestPerformed) {
		this.hivTestPerformed = hivTestPerformed;
	}
	public String getHivTestResult() {
		if(hivTestResult==null)
			hivTestResult="";
		return hivTestResult;
	}
	public void setHivTestResult(String hivTestResult) {
		this.hivTestResult = hivTestResult;
	}
	public String getCaseNumberEarliestPS() {
		return caseNumberEarliestPS;
	}
	public void setCaseNumberEarliestPS(String caseNumberEarliestPS) {
		this.caseNumberEarliestPS = caseNumberEarliestPS;
	}
	public Timestamp getPartnerLastChgDt() {
		return partnerLastChgDt;
	}
	public void setPartnerLastChgDt(Timestamp partnerLastChgDt) {
		this.partnerLastChgDt = partnerLastChgDt;
	}



}