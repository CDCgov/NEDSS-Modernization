package gov.cdc.nedss.proxy.ejb.observationproxyejb.vo;

import gov.cdc.nedss.entity.organization.vo.OrganizationVO;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.proxy.util.LabResultProxyVOParser;
import gov.cdc.nedss.util.Coded;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

public class LabReport {

	private PersonVO patient;
	private OrganizationVO reportingFacility;
	private PersonVO orderingProvider;
	private OrganizationVO orderingFacility;

	
	private Long labReportUID;
	private String labReportLocalId;
	private String fillerOrderNumber;
	private Calendar observationDateTime;
	private Calendar laboratoryReportDate;
	private Coded requestedObservation;
	private String clinicalInformation;
	private Coded resultStatus;

	private Coded specimenCode;
	private String specimenDescription;
	
	private Collection<Object>  testResults;

	public LabReport() {
		testResults = new ArrayList<Object> ();
	}
	
	//Use helper to construct an instance of LabReport from LabResultProxyVO object
	public LabReport(LabResultProxyVO labResultProxyVO) {
		testResults = new ArrayList<Object> ();

		LabResultProxyVOParser labResultProxyVOParser = new LabResultProxyVOParser();
		labResultProxyVOParser.parseLabReportProxyVO(this, labResultProxyVO);
	}
	
	public PersonVO getPatient() {
		return patient;
	}

	public void setPatient(PersonVO patient) {
		this.patient = patient;
	}

	public OrganizationVO getReportingFacility() {
		return reportingFacility;
	}

	public void setReportingFacility(OrganizationVO reportingFacility) {
		this.reportingFacility = reportingFacility;
	}

	public PersonVO getOrderingProvider() {
		return orderingProvider;
	}

	public void setOrderingProvider(PersonVO orderingProvider) {
		this.orderingProvider = orderingProvider;
	}

	public OrganizationVO getOrderingFacility() {
		return orderingFacility;
	}

	public void setOrderingFacility(OrganizationVO orderingFacility) {
		this.orderingFacility = orderingFacility;
	}

	public Long getLabReportUID() {
		return labReportUID;
	}

	public void setLabReportUID(Long labReportUID) {
		this.labReportUID = labReportUID;
	}

	public String getLabReportLocalId() {
		return labReportLocalId;
	}

	public void setLabReportLocalId(String labReportLocalId) {
		this.labReportLocalId = labReportLocalId;
	}

	public String getFillerOrderNumber() {
		return fillerOrderNumber;
	}

	public void setFillerOrderNumber(String fillerOrderNumber) {
		this.fillerOrderNumber = fillerOrderNumber;
	}

	public Calendar getObservationDateTime() {
		return observationDateTime;
	}

	public void setObservationDateTime(Calendar observationDateTime) {
		this.observationDateTime = observationDateTime;
	}

	public Calendar getLaboratoryReportDate() {
		return laboratoryReportDate;
	}

	public void setLaboratoryReportDate(Calendar laboratoryReportDate) {
		this.laboratoryReportDate = laboratoryReportDate;
	}

	public Coded getRequestedObservation() {
		return requestedObservation;
	}

	public void setRequestedObservation(Coded requestedObservation) {
		this.requestedObservation = requestedObservation;
	}

	public String getClinicalInformation() {
		return clinicalInformation;
	}

	public void setClinicalInformation(String clinicalInformation) {
		this.clinicalInformation = clinicalInformation;
	}

	public Coded getResultStatus() {
		return resultStatus;
	}

	public void setResultStatus(Coded resultStatus) {
		this.resultStatus = resultStatus;
	}

	public Coded getSpecimenCode() {
		return specimenCode;
	}

	public void setSpecimenCode(Coded specimenCode) {
		this.specimenCode = specimenCode;
	}

	public String getSpecimenDescription() {
		return specimenDescription;
	}

	public void setSpecimenDescription(String specimenDescription) {
		this.specimenDescription = specimenDescription;
	}

	public Collection<Object>  getTestResults() {
		return testResults;
	}

	public void setTestResults(Collection<Object> testResults) {
		this.testResults = testResults;
	}
	
	
}
