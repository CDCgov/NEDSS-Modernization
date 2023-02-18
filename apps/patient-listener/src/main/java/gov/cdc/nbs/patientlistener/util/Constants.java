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
	public static final String FAILED_PERSON_NOT_FIND_MSG = "The patient was not found system with id:";
	
	public static final String DATE_PATTERN = "uuuu-MM-dd'T'HH:mm:ss||uuuu-MM-dd'T'HH:mm:ss.SSS||uuuu-MM-dd'T'HH:mm:ss.SS||uuuu-MM-dd HH:mm:ss.SSS||uuuu-MM-dd HH:mm:ss.S||uuuu-MM-dd HH:mm:ss.SS";


}
