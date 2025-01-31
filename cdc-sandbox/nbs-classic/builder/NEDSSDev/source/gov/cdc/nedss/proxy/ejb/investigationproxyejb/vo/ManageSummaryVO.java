package gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo;

import gov.cdc.nedss.act.publichealthcase.vo.PublicHealthCaseVO;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.util.AbstractVO;

import java.util.Collection;

public class ManageSummaryVO extends AbstractVO  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Collection<Object>  labSummaryCollection;
	private Collection<Object>  mobReportSummaryVOCollection;
	private Collection<Object>  TreatmentSummaryVOCollection;
	private Collection<Object>  VaccinationSummaryVOCollection;
	private PublicHealthCaseVO publicHealthCaseVO;
	private Collection<Object>   documentCollection;
	private PersonVO patientVO;
	
	public ManageSummaryVO(){
		
	}
	public Collection<Object>  getLabSummaryCollection() {
		return labSummaryCollection;
	}
	public void setLabSummaryCollection(Collection<Object> labSummaryCollection) {
		this.labSummaryCollection  = labSummaryCollection;
	}
	public Collection<Object>  getMobReportSummaryVOCollection() {
		return mobReportSummaryVOCollection;
	}
	public void setMobReportSummaryVOCollection(
			Collection<Object>  mobReportSummaryVOCollection) {
		this.mobReportSummaryVOCollection  = mobReportSummaryVOCollection;
	}
	public Collection<Object>  getTreatmentSummaryVOCollection() {
		return TreatmentSummaryVOCollection;
	}
	public void setTreatmentSummaryVOCollection(
			Collection<Object>  treatmentSummaryVOCollection) {
		TreatmentSummaryVOCollection  = treatmentSummaryVOCollection;
	}
	public Collection<Object>  getVaccinationSummaryVOCollection() {
		return VaccinationSummaryVOCollection;
	}
	public void setVaccinationSummaryVOCollection(
			Collection<Object>  vaccinationSummaryVOCollection) {
		VaccinationSummaryVOCollection  = vaccinationSummaryVOCollection;
	}
	public PublicHealthCaseVO getPublicHealthCaseVO() {
		return publicHealthCaseVO;
	}
	public void setPublicHealthCaseVO(PublicHealthCaseVO publicHealthCaseVO) {
		this.publicHealthCaseVO = publicHealthCaseVO;
	}
	public boolean isEqual(Object objectname1, Object objectname2, Class<?> voClass) {
		// TODO Auto-generated method stub
		return false;
	}
	public boolean isItDelete() {
		// TODO Auto-generated method stub
		return false;
	}
	public boolean isItDirty() {
		// TODO Auto-generated method stub
		return false;
	}
	public boolean isItNew() {
		// TODO Auto-generated method stub
		return false;
	}
	public void setItDelete(boolean itDelete) {
		// TODO Auto-generated method stub
		
	}
	public void setItDirty(boolean itDirty) {
		// TODO Auto-generated method stub
		
	}
	public void setItNew(boolean itNew) {
		// TODO Auto-generated method stub
		
	}
	public Collection<Object>  getDocumentCollection() {
		return documentCollection;
	}
	public void setDocumentCollection(Collection<Object> documentCollection) {
		this.documentCollection  = documentCollection;
	}
	public PersonVO getPatientVO() {
		return patientVO;
	}
	public void setPatientVO(PersonVO patientVO) {
		this.patientVO = patientVO;
	}
	

}
