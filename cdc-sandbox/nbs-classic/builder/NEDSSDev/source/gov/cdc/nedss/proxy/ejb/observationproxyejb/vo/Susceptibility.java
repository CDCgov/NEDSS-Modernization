package gov.cdc.nedss.proxy.ejb.observationproxyejb.vo;

import gov.cdc.nedss.util.Coded;
import gov.cdc.nedss.util.Numeric;

public class Susceptibility {

	private Coded drug;
	private Numeric numericResult;
	private Coded codedResult;
	private Coded interpretation;

	public Coded getDrug() {
		return drug;
	}
	public void setDrug(Coded drug) {
		this.drug = drug;
	}
	public Numeric getNumericResult() {
		return numericResult;
	}
	public void setNumericResult(Numeric numericResult) {
		this.numericResult = numericResult;
	}
	public Coded getCodedResult() {
		return codedResult;
	}
	public void setCodedResult(Coded codedResult) {
		this.codedResult = codedResult;
	}
	public Coded getInterpretation() {
		return interpretation;
	}
	public void setInterpretation(Coded interpretation) {
		this.interpretation = interpretation;
	}


}
