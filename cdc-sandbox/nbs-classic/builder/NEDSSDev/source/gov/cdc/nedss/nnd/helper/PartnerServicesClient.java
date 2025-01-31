/**
 * 
 */
package gov.cdc.nedss.nnd.helper;
import java.util.ArrayList;

import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dt.PartnerServicesLookupDT;
/**
 * Luther overlays all the data for each client each time we upload.
 * So we have to include all the cases, not just those that match the timeframe.
 * 
 * @author TuckerG
 *
 */
public class PartnerServicesClient {
	
	private String patientLocalId;    
	private PartnerServicesLookupDT indexCase;
	private ArrayList<PartnerServicesLookupDT> partnerCases;
	
	
	public PartnerServicesClient() {
		partnerCases = new ArrayList<PartnerServicesLookupDT>();
	}
	
	public PartnerServicesClient(String personLocalId) {
		partnerCases = new ArrayList<PartnerServicesLookupDT>();
		this.patientLocalId = personLocalId;
	}

	public String getPatientLocalId() {
		return patientLocalId;
	}
	public void setPatientLocalId(String patientLocalId) {
		this.patientLocalId = patientLocalId;
	}
	public PartnerServicesLookupDT getIndexCase() {
		return indexCase;
	}
	public void setIndexCase(PartnerServicesLookupDT indexCase) {
		this.indexCase = indexCase;
	}
	public ArrayList<PartnerServicesLookupDT> getPartnerCases() {
		return partnerCases;
	}
	public void setPartnerCases(ArrayList<PartnerServicesLookupDT> partnerCase) {
		this.partnerCases = partnerCase;
	}


}
