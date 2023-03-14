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
	
	 //Patient Update Types
    public static final String UPDATE_GENERAL_INFO ="GeneralInformation";
    
    public static final String UPDATE_SEX_BIRTH="Sex&Birth";
    
    public static final String UPDATE_MORTALITY="Mortality";
    
    public static final String USE_CD= "DTH";


}
