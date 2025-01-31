package gov.cdc.nedss.systemservice.ejb.edxdocumentejb.util;

public class EdxPHCRConstants {

	public static final String SUM_MSG_INVESTIGATION_FAIL = "Error creating investigation.  See Activity Details.";
	public static final String SUM_MSG_NOTIFICATION_FAIL = "Error creating notification.  See Activity Details.";
	public static final String SUM_MSG_JURISDICTION_FAIL = "Jurisdiction not derived. Document logged in Documents Requiring Security Assignment queue.";
	public static final String SUM_MSG_ALGORITHM_FAIL1 = "A matching algorithm for condition \"";
	public static final String SUM_MSG_ALGORITHM_FAIL2 = "\" was not found. Document logged in Documents Requiring Review queue.";
	public static final String SUM_MSG_ALGORITHM_FAIL3 = "\" was not found. Document is linked to patient's file. ";
	public static final String SUM_MSG_NO_CONDITION = "The condition does not exist in NBS so the Program Area could not be derived.  Document logged in Documents Requiring Security Assignment queue.";
	public static final String SUM_MSG_MISSING_FLDS = "Missing fields required to create ";
	public static final String SUM_MSG_INVALID_XML = "Failed to create document because of invalid XML. See Activity Details.";
	public static final String SUM_MSG_UNEXPECTED_CHARACTER = "Failed to create document because of unexpected character in XML. See Activity Details.";
	public static final String SUM_MSG_NO_CONDITION_ERROR = "Failed to create document  as disease condition does not exist in NBS. See Activity Details.";
	public static final String SUM_MSG_UPDATE_ERROR = "Error updating Case Document.  See Activity Details.";
	
	public static final String SUM_MSG_TRUNCATED_DATA = "Failed to create document as data length for one of the element exceeds the allowable limit. See Activity Details.";
	
	public static final String DET_MSG_JURISDICTION_SUCCESS = "Jurisdiction derived";
	public static final String DET_MSG_PATIENT_NEW_SUCCESS = "Patient match not found; New Patient created: ";
	public static final String DET_MSG_PATIENT_OLD_SUCCESS = "Patient match found: ";
	public static final String DET_MSG_DOCUMENT_SUCCESS1 = "Document ";
	public static final String DET_MSG_DOCUMENT_SUCCESS2 = " created and associated to Patient.";
	
	public static final String DET_MSG_INVESTIGATION_SUCCESS = "Investigation created ";
	public static final String DET_MSG_INVESTIGATION_FAIL = "Failed to create investigation";
	public static final String DET_MSG_NOTIFICATION_FAIL = "Failed to create Notification";
	public static final String DET_MSG_JURISDICTION_FAIL = "Jurisdiction not derived";
	public static final String DET_MSG_ALGORITHM_FAIL1 = "Matching algorithm for condition \"";
	public static final String DET_MSG_ALGORITHM_FAIL2 = "\" not found.";
	public static final String DET_MSG_MULTIPLE_PATIENTS = "Multiple patient matches found. New Patient created: ";
	public static final String DET_MSG_NO_CONDITION = " is not a condition in NBS.  Program Area cannot be derived.";
	public static final String DET_MSG_NOT_AUTH_1 = "User ";
	public static final String DET_MSG_NOT_AUTH_2 = " is not authorized to autocreate ";
	public static final String DET_MSG_UPDATE_DOCUMENT= " marked as an Update to ";
	public static final String SUM_MSG_INVESTIGATION_CREATE_FAIL_1 = "At least one existing investigation for ";
	public static final String SUM_MSG_INVESTIGATION_OPEN = "At least one existing open investigation for ";
	public static final String SUM_MSG_INVESTIGATION_CLOSED = "At least one existing closed investigation for ";
	public static final String SUM_MSG_INVESTIGATION_CLOSED_CREATE_FAIL_1 = "At least one existing closed investigation for ";
	public static final String SUM_MSG_INVESTIGATION_MULTI_CLOSED_CREATE_FAIL_1 = "Multiple existing closed investigation for ";
	public static final String SUM_MSG_INVESTIGATION_MULTI_OPEN_CREATE_FAIL_1 = "Multiple existing open investigation for ";
	public static final String SUM_MSG_INVESTIGATION_CREATE_FAIL_2 = " was found.  Document logged in Documents Requiring Review queue.";
	public static final String SUM_MSG_INVESTIGATION_UPDATE_2 = " was found. Updated the most recent investigation ";
	public static final String SUM_MSG_INVESTIGATION_UPDATE_3 = " was found. Document marked as reviewed ";
	public static final String EXISTING_INV_FOUND="At least one existing Investigation(s) found for ";
	public static final String DET_MSG_INTERVIEW_SUCCESS = "Interview created ";
	public static final String DET_MSG_INTERVIEW_FAIL = "Failed to create interview";
	public static final String DET_MSG_CONTACT_RECORD_SUCCESS = "Contact Record created ";
	public static final String DET_MSG_CONTACT_RECORD_FAIL = "Failed to create contact record";
	 

	
	public static final String INV_STR = "Investigation";
	public static final String NOT_STR = "Notification";
	
	public static final int MAX_DETAIL_COMMENT_LEN = 1997;

	public enum MSG_TYPE {
		Investigation, Document, Patient, Notification, Jurisdiction, Algorithm, NBS_System, Provider, Organization, Interview, ContactRecord,Place,
	};
}
