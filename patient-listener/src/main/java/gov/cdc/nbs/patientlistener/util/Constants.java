package gov.cdc.nbs.patientlistener.util;

public class Constants {
	
	private Constants() {
		
	}
	//Application ID
	public static final String APP_ID = "PATIENT-SEARCH";
	
	//Statuses
	public static final String COMPLETE= "COMPLETE";
	public static final String FAILED= "FAILED";
	public static final String PENDING= "PENDING";
	public static final String UNKNOWN= "UNKNOWN";
	
	public static final String FAILED_NO_REQUESTID_MSG = "No RequestId provided with request. Could not updated patient.";

}
