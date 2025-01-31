package gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dt;

import java.io.Serializable;

/**
 * Utility class to get and set lab specific values
 * @author Pradeep Kumar Sharma
 *
 */
public class EdxLabIdentiferDT  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String identifer;
	private String subMapID;
	private Long observationUid;
	private String[] observationValues;

	public String[] getObservationValues() {
		return observationValues;
	}
	public void setObservationValues(String[] observationValues) {
		this.observationValues = observationValues;
	}
	public String getSubMapID() {
		return subMapID;
	}
	public void setSubMapID(String subMapID) {
		this.subMapID = subMapID;
	}
	public String getIdentifer() {
		return identifer;
	}
	public void setIdentifer(String identifer) {
		this.identifer = identifer;
	}
	public Long getObservationUid() {
		return observationUid;
	}
	public void setObservationUid(Long observationUid) {
		this.observationUid = observationUid;
	}

}
