package gov.cdc.nedss.proxy.ejb.epilink.vo;

import java.io.Serializable;
import java.util.Collection;

public class EpilinkSummaryDisplayVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	 private Collection<Object>  testCollection;

	public Collection<Object> getTestCollection() {
		return testCollection;
	}

	public void setTestCollection(Collection<Object> testCollection) {
		this.testCollection = testCollection;
	}
	 
	 

}
