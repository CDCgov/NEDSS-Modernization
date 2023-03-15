package gov.cdc.nbs.message.util;

public class Constants {
	
	
	// Prefix Application ID associated with Kafka requestID
    public static final String APP_ID = "PATIENT-SEARCH";
		
	//Patient Update Response Statuses & Messages
	public static final String COMPLETE= "COMPLETE";
	public static final String FAILED= "FAILED";
	public static final String PENDING= "PENDING";
	public static final String UNKNOWN= "UNKNOWN";
	
	public static final String FAILED_NO_REQUESTID_MSG = "No RequestId provided with request. Could not updated patient.";
	public static final String FAILED_PERSON_NOT_FIND_MSG = "The patient was not found system with id:";
		
		
    //Patient Update Types
    public static final String UPDATE_GENERAL_INFO ="GeneralInformation";
    
    public static final String UPDATE_SEX_BIRTH="Sex&Birth";
    
    public static final String UPDATE_MORTALITY="Mortality";
    
    public static final String USE_CD= "DTH";


}
