package gov.cdc.nedss.report.javaRepot.vo;

import java.util.Collection;

import gov.cdc.nedss.report.javaRepot.dt.ReportPatientDT;
import gov.cdc.nedss.util.AbstractVO;
/**
 * VO class for CR1 report
 * @author Pradeep Kumar Sharma
 *
 */
public class CR1VO extends AbstractVO implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 558228231826876870L;

	private ReportPatientDT patientDT;
	
	private Collection<Object> Treatments;
	private Collection<Object> signsAndSymptoms;
	private Collection<Object> named;
	private Collection<Object> namedBackedBy;
	private Collection<Object> namedByButNotBackedBy;
	
	
	public Collection<Object> getSignsAndSymptoms() {
		return signsAndSymptoms;
	}

	public void setSignsAndSymptoms(Collection<Object> signsAndSymptoms) {
		this.signsAndSymptoms = signsAndSymptoms;
	}

	public Collection<Object> getNamedByButNotBackedBy() {
		return namedByButNotBackedBy;
	}

	public void setNamedByButNotBackedBy(
			Collection<Object> namedByButNotBackedBy) {
		this.namedByButNotBackedBy = namedByButNotBackedBy;
	}

	public ReportPatientDT getPatientDT() {
		return patientDT;
	}

	public void setPatientDT(ReportPatientDT patientDT) {
		this.patientDT = patientDT;
	}

	public Collection<Object> getTreatments() {
		return Treatments;
	}

	public void setTreatments(Collection<Object> treatments) {
		Treatments = treatments;
	}

	public Collection<Object> getNamed() {
		return named;
	}

	public void setNamed(Collection<Object> named) {
		this.named = named;
	}

	public Collection<Object> getNamedBackedBy() {
		return namedBackedBy;
	}

	public void setNamedBackedBy(Collection<Object> namedBackedBy) {
		this.namedBackedBy = namedBackedBy;
	}


	
	
	
	
	@Override
	public void setItDirty(boolean itDirty) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isItDirty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setItNew(boolean itNew) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isItNew() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setItDelete(boolean itDelete) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isItDelete() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEqual(Object objectname1, Object objectname2,
			Class<?> voClass) {
		// TODO Auto-generated method stub
		return false;
	}

}
