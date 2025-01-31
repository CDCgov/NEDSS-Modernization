package gov.cdc.nedss.report.javaRepot.vo;

import java.util.Collection;

import gov.cdc.nedss.report.javaRepot.dt.ReportPlaceDT;
import gov.cdc.nedss.util.AbstractVO;

public class CR4VO extends AbstractVO implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3740333885366660487L;

	private ReportPlaceDT reportPlaceDT;
	
	private Collection<Object> associatedCases;
	private String count200;
	private String count300;
	private String count710;
	private String count720;
	private String count730;
	private String count740;
	private String count745;
	private String count900;
	private String count950;
	private String cohortCount;
	
	public String getCohortCount() {
		return cohortCount;
	}

	public void setCohortCount(String cohortCount) {
		this.cohortCount = cohortCount;
	}

	public Collection<Object> getAssociatedCohortCases() {
		return associatedCohortCases;
	}

	public void setAssociatedCohortCases(Collection<Object> associatedCohortCases) {
		this.associatedCohortCases = associatedCohortCases;
	}
	private Collection<Object> associatedCohortCases;
	
	public ReportPlaceDT getReportPlaceDT() {
		return reportPlaceDT;
	}

	public void setReportPlaceDT(ReportPlaceDT reportPlaceDT) {
		this.reportPlaceDT = reportPlaceDT;
	}

	public Collection<Object> getAssociatedCases() {
		return associatedCases;
	}

	public void setAssociatedCases(Collection<Object> associatedCases) {
		this.associatedCases = associatedCases;
	}
	public String getCount200() {
		return count200;
	}
	public void setCount200(String count200) {
		this.count200 = count200;
	}
	public String getCount300() {
		return count300;
	}
	public void setCount300(String count300) {
		this.count300 = count300;
	}
	public String getCount710() {
		return count710;
	}
	public void setCount710(String count710) {
		this.count710 = count710;
	}
	public String getCount720() {
		return count720;
	}
	public void setCount720(String count720) {
		this.count720 = count720;
	}
	public String getCount730() {
		return count730;
	}
	public void setCount730(String count730) {
		this.count730 = count730;
	}
	public String getCount740() {
		return count740;
	}
	public void setCount740(String count740) {
		this.count740 = count740;
	}
	public String getCount745() {
		return count745;
	}
	public void setCount745(String count745) {
		this.count745 = count745;
	}
	public String getCount900() {
		return count900;
	}
	public void setCount900(String count900) {
		this.count900 = count900;
	}
	public String getCount950() {
		return count950;
	}
	public void setCount950(String count950) {
		this.count950 = count950;
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
