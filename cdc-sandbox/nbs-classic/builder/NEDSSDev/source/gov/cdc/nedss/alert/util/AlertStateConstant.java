package gov.cdc.nedss.alert.util;

public interface AlertStateConstant{

	public static final String ALERT_MESSAAGE_PREP="ALERT_MESSAGE_PREP:INFO: ";
	public static final String ALERT_MESSAAGE_PREP_ERROR="ALERT_MESSAGE_PREP:ERROR: ";
	public static final String ALERT_MESSAAGE_INVALID_EMAIL="Email id is null. Please check User_email table for User_email_uid: ";
	//public static final String ALERT_MESSAAGE_PREP="STEP4 :ALERT_MESSAAGE_PREP";
	public static final String ALERT_MAIL_COMPOSE="ALERT_MAIL_COMPOSE:INFO: ";
	public static final String ALERT_MAIL_COMPOSE_ERROR="ALERT_MAIL_COMPOSE:ERROR: ";
	public static final String ALERT_MAIL_TRANSMIT="ALERT_MAIL_TRANSMIT:INFO: ";
	public static final String ALERT_MAIL_TRANSMIT_ERROR="ALERT_MAIL_TRANSMIT:ERROR: ";
	public static final String ALERT_MAIL_COMPLETE="ALERT_MAIL_COMPLETE:INFO: ";
	public static final String ALERT_MAIL_COMPLETE_ERROR="ALERT_MAIL_TRANSMIT:ERROR: ";
	public static final String ALERT_MAIL_SUCCESSFUL="ALERT_MAIL_SUCCESSFUL";
	public static final String ALERT_EMAIL_MESSAGE_SUCCESSFUL="ALERT_EMAIL_COMPLETE:INFO: Email message recorded in ALERT_EMAIL_MESSAGE table";
	public static final String ALERT_EMAIL_MESSAGE_UNSUCCESSFUL="ALERT_EMAIL_COMPLETE:ERROR: Alert message not transmitted.";
	public static final String SIMULATED_PART1="TEST: Priority ";
	public static final String SIMULATED_PART2=": Alert from NBS indicating ";
	public static final String SIMULATED_PART3=" :TEST ";
	public static final String SIMULATED_TEXT_PART1="This is a TEST alert indicating a ";
	public static final String SIMULATED_TEXT_PART2=" indicating ";
	public static final String SIMULATED_TEXT_PART3=" was received.";
	public static final String SIMULATE = "simulate";
	
	public static final String PART1="Priority ";
	public static final String PART2=": Alert from NBS indicating ";
	public static final String PART3=" :TEST ";
	
	public static final String TEXT_PART1="A ";
	public static final String TEXT_PART1b=" from ";	
	public static final String TEXT_PART2=" indicating ";
	public static final String TEXT_PART3=" was received by the NBS on ";
	public static final String TEXT_PART4=" Jurisdiction with an identifier of ";
	
	
	public static final String BODY_TEXT_PART1="A ";	
	public static final String BODY_TEXT_PART1b=" from ";	
	public static final String BODY_TEXT_PART2=" mapped to ";
	public static final String BODY_TEXT_PART3=" was received by the NBS on ";	
	public static final String BODY_TEXT_PART4a=" for ";
	public static final String BODY_TEXT_PART4b=" Jurisdiction with an identifier of ";	
	public static final String BODY_TEXT_PART5=" with the following result(s):"; //"\r\n"?
	public static final String UNKNOWN_REPORTING_FACILITY ="an unspecified Reporting Facility"; //"\r\n"?
	
	public static final String MESSAGE_SENT="SENT";
	public static final String MESSAGE_NOT_SENT="NOT SENT";
	
	public static final String JURISDICTION_ALL="ALL";
	public static final String JURISDICTION_NOT_ASSIGNED="Not Assigned";
	public static final String PROGRAM_AREA_ALL="ALL";
	public static final String PROGRAM_AREA_NOT_ASSIGNED="Not Assigned";
	public static final String LAB_CODE="11648804";
	public static final String LAB_REPORT = "Laboratory Report";
	public static final String PHCR_OR_LAB_CODE="11648804PHC236";
	public static final String TRANSMISSION_ERROR1="ERROR1";
	public static final String TRANSMISSION_ERROR2="ERROR2";
	public static final String TRANSMISSION_ERROR="ERROR";
	public static final String TRANSMISSION_QUEUED="QUEUED";
	
}


