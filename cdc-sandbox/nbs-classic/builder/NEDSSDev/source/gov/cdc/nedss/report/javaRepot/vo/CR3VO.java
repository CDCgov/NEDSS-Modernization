package gov.cdc.nedss.report.javaRepot.vo;

import java.util.Collection;

import gov.cdc.nedss.report.javaRepot.dt.ReportPatientDT;
import gov.cdc.nedss.util.AbstractVO;
/**
 * VO class for CR3VO for report
 * @author Pradeep Kumar Sharma
 *
 */
public class CR3VO extends AbstractVO implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 558228231826876870L;

	private ReportPatientDT patientDT;

	private Collection<Object> named;


	public ReportPatientDT getPatientDT() {
		return patientDT;
	}

	public void setPatientDT(ReportPatientDT patientDT) {
		this.patientDT = patientDT;
	}

	public Collection<Object> getNamed() {
		return named;
	}

	public void setNamed(Collection<Object> named) {
		this.named = named;
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
