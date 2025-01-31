package gov.cdc.nedss.report.javaRepot.dt;

import java.sql.Timestamp;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;
/**
 * TreatmentDT form custom Report Generation
 * @author Pradeep Kumar Sharma
 *
 */
public class TreatmentDT  extends AbstractVO implements RootDTInterface{
	
	
	private String treatment;
	private String rxDate;
	private String provider;
	private String signsSymptoms;
	private String onsetDate;
	private String duration;
	private String clinicalObservation;
	private String providerFirstName;
	private String providerLastName;
	private String providerSuffixName;
	private String treatmentProviderKey;
	private String treatmentName;
	private String treatmentInvestigationKey;
	private String tretmentDrug;
	private String treatmentDosageStrength;
	private String treatmentDosageUnit;
	private String treatmentFrequency;
	private String treatmentDuration;
	private String treatmentDurationUnit;
	private String customTreatment;
	

	public String getProviderFirstName() {
		return providerFirstName;
	}
	public void setProviderFirstName(String providerFirstName) {
		this.providerFirstName = providerFirstName;
	}
	public String getProviderLastName() {
		return providerLastName;
	}
	public void setProviderLastName(String providerLastName) {
		this.providerLastName = providerLastName;
	}
	public String getProviderSuffixName() {
		return providerSuffixName;
	}
	public void setProviderSuffixName(String providerSuffixName) {
		this.providerSuffixName = providerSuffixName;
	}
	public String getTreatmentProviderKey() {
		return treatmentProviderKey;
	}
	public void setTreatmentProviderKey(String treatmentProviderKey) {
		this.treatmentProviderKey = treatmentProviderKey;
	}
	public String getTreatmentName() {
		return treatmentName;
	}
	public void setTreatmentName(String treatmentName) {
		this.treatmentName = treatmentName;
	}
	public String getTreatmentInvestigationKey() {
		return treatmentInvestigationKey;
	}
	public void setTreatmentInvestigationKey(String treatmentInvestigationKey) {
		this.treatmentInvestigationKey = treatmentInvestigationKey;
	}
	public String getTretmentDrug() {
		return tretmentDrug;
	}
	public void setTretmentDrug(String tretmentDrug) {
		this.tretmentDrug = tretmentDrug;
	}
	public String getTreatmentDosageStrength() {
		return treatmentDosageStrength;
	}
	public void setTreatmentDosageStrength(String treatmentDosageStrength) {
		this.treatmentDosageStrength = treatmentDosageStrength;
	}
	public String getTreatmentDosageUnit() {
		return treatmentDosageUnit;
	}
	public void setTreatmentDosageUnit(String treatmentDosageUnit) {
		this.treatmentDosageUnit = treatmentDosageUnit;
	}
	public String getTreatmentFrequency() {
		return treatmentFrequency;
	}
	public void setTreatmentFrequency(String treatmentFrequency) {
		this.treatmentFrequency = treatmentFrequency;
	}
	public String getTreatmentDuration() {
		return treatmentDuration;
	}
	public void setTreatmentDuration(String treatmentDuration) {
		this.treatmentDuration = treatmentDuration;
	}
	public String getTreatmentDurationUnit() {
		return treatmentDurationUnit;
	}
	public void setTreatmentDurationUnit(String treatmentDurationUnit) {
		this.treatmentDurationUnit = treatmentDurationUnit;
	}
	public String getCustomTreatment() {
		return customTreatment;
	}
	public void setCustomTreatment(String customTreatment) {
		this.customTreatment = customTreatment;
	}
	public String getTreatment() {
		return treatmentName;
	}
	public void setTreatment(String treatment) {
		this.treatment = treatment;
	}
	public String getRxDate() {
		return rxDate;
	}
	public void setRxDate(String rxDate) {
		this.rxDate = rxDate;
	}
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	public String getSignsSymptoms() {
		return signsSymptoms;
	}
	public void setSignsSymptoms(String signsSymptoms) {
		this.signsSymptoms = signsSymptoms;
	}
	public String getOnsetDate() {
		return onsetDate;
	}
	public void setOnsetDate(String onsetDate) {
		this.onsetDate = onsetDate;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getClinicalObservation() {
		return clinicalObservation;
	}
	public void setClinicalObservation(String clinicalObservation) {
		this.clinicalObservation = clinicalObservation;
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
