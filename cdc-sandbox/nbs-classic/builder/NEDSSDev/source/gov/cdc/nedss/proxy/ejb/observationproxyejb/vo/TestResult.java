package gov.cdc.nedss.proxy.ejb.observationproxyejb.vo;

import gov.cdc.nedss.entity.organization.vo.OrganizationVO;
import gov.cdc.nedss.util.Coded;
import gov.cdc.nedss.util.Numeric;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

public class TestResult {

	private OrganizationVO performingFacility;
	private Coded testResultCode;
	private Numeric numericValue;
	private Coded codedValue;
	private String textValue;
	private Calendar observationDateTime;
	private String referenceRangeLow;
	private String referenceRangeHigh;
	private Collection<Object>  comments;
	private Collection<Object>  susceptibilities;
	private Long testResultObservationUID;
	
	public TestResult() {
		comments = new ArrayList<Object> ();
		susceptibilities = new ArrayList<Object> ();
	}
	
	public OrganizationVO getPerformingFacility() {
		return performingFacility;
	}
	public void setPerformingFacility(OrganizationVO performingFacility) {
		this.performingFacility = performingFacility;
	}
	public Coded getTestResultCode() {
		return testResultCode;
	}
	public void setTestResultCode(Coded testResultCode) {
		this.testResultCode = testResultCode;
	}
	public Numeric getNumericValue() {
		return numericValue;
	}
	public void setNumericValue(Numeric numericValue) {
		this.numericValue = numericValue;
	}
	public Coded getCodedValue() {
		return codedValue;
	}
	public void setCodedValue(Coded codedValue) {
		this.codedValue = codedValue;
	}
	public String getTextValue() {
		return textValue;
	}
	public void setTextValue(String textValue) {
		this.textValue = textValue;
	}
	public Calendar getObservationDateTime() {
		return observationDateTime;
	}
	public void setObservationDateTime(Calendar observationDateTime) {
		this.observationDateTime = observationDateTime;
	}
	public String getReferenceRangeLow() {
		return referenceRangeLow;
	}
	public void setReferenceRangeLow(String referenceRangeLow) {
		this.referenceRangeLow = referenceRangeLow;
	}
	public String getReferenceRangeHigh() {
		return referenceRangeHigh;
	}
	public void setReferenceRangeHigh(String referenceRangeHigh) {
		this.referenceRangeHigh = referenceRangeHigh;
	}
	public Collection<Object>  getComments() {
		return comments;
	}
	public void setComments(Collection<Object> comments) {
		this.comments = comments;
	}
	public Collection<Object>  getSusceptibilities() {
		return susceptibilities;
	}
	public void setSusceptibilities(Collection<Object> susceptibilities) {
		this.susceptibilities = susceptibilities;
	}
	public Long getTestResultObservationUID() {
		return testResultObservationUID;
	}
	public void setTestResultObservationUID(Long testResultObservationUID) {
		this.testResultObservationUID = testResultObservationUID;
	}
}
