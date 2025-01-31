package gov.cdc.nedss.systemservice.ejb.edxdocumentejb.labutil;

import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dt.EdxRuleAlgorothmManagerDT.STATUS_VAL;

/**
 * @author SHARMAPRA
 *
 */
/**
 * @author SHARMAPRA
 *
 */
public interface EdxELRConstants {


	//Entity ID specific mappings
	public static final String ELR_PERSON_CD="PSN";
	public static final String ELR_PATIENT_CD="PAT";
	public static final String ELR_PATIENT="PATIENT";
	public static final String ELR_NOK_DESC="Observation Participant";
	public static final String ELR_PERSON_TYPE="PN";
	public static final String ELR_PERSON_TYPE_DESC="Person Number";
	public static final String ELR_PATIENT_ALTERNATE_IND="ELR_PATIENT_ALTERNATE_IND";
	public static final String ELR_PATIENT_ALTERNATE_TYPE="APT";
	public static final String EI_TYPE="EI_TYPE";
	public static final String ELR_LAB_CD="LAB214";


	public static final String ELR_PATIENT_ALTERNATE_DESC="Alternate Person Number’";
	public static final String ELR_CLIA_DESC="Clinical Laboratory Improvement Amendment";
	public static final String ELR_CLIA_CD="CLIA";
	public static final String ELR_LAB_COMMENT = "LabComment";
	public static final String ELR_AR_LAB_COMMENT = "APND";
	//  Removed as duplicate of ELR_DOC_TYPE_CD below
//	public static final String ELR_DOC_TYPE="11648804";
	public static final String ADD_REASON_CD="Add";
	public static final String ELR_DEFAULT_CLIA="DEFAULT";

	public static final String ELR_OBS_STATUS_CD_COMPLETED = "D";
    public static final String ELR_OBS_STATUS_CD_SUPERCEDED = "T";
    public static final String ELR_OBS_STATUS_CD_NEW = "N";

	public static final String ELR_SENDING_FACILITY_CD="SF";
	public static final String ELR_SENDING_FACILITY_DESC="Sending Facility";
	public static final String ELR_SENDING_HCFAC="HCFAC";
	public static final String ELR_OBS="OBS";
	public static final String ELR_AUTHOR_CD="AUT";
	public static final String ELR_AUTHOR_DESC="Author";
	public static final String ELR_standard_industry_class_cd="621511";
	public static final String ELR_standard_industry_desc_txt ="Medical Laboratory";
	public static final String ELR_LABORATORY_DESC="Laboratory";
	public static final String ELR_SENDING_LAB_CD="LAB";
	public static final String ELR_ELECTRONIC_IND="Y";
	public static final String ELR_LEGAL_NAME="L";
	public static final String ELR_ALIAS_NAME="AL";
	public static final String ELR_MOTHER_IDENTIFIER="MO";
	public static final String ELR_ACCOUNT_IDENTIFIER="AN";
	public static final String ELR_ACCOUNT_DESC="Account number";
	public static final String ELR_ALTERNAT_EPERSON_IDENTIFIER="APT";
	public static final String ELR_SS_TYPE="SS";
	public static final String ELR_SS_AUTHO_TYPE="SSA";
	public static final String ELR_SS_DESC_TYPE="Social Security";
	public static final String ELR_ACTIVE="ACTIVE";
	public static final String ELR_ACTIVE_CD="A";
	public static final String ELR_FACILITY_CD="FI";
	public static final String ELR_FACILITY_DESC="Facility Identifier";
	public static final String ELR_DRIVER_LIC_CD="DL";
	public static final String ELR_DRIVER_LIC_DESC="Driver's license number";

	public static final String ELR_RECEIVING_FACILITY_CD="RF";
	public static final String ELR_RECEIVING_FACILITY_DESC="Receiving Facility";
	public static final String ELR_RECEIVING_ROLE_CLASS_CD= "PHO";
	public static final String ELR_RECEIVING_ORG_CLASS_CD= "DOH";
	public static final String ELR_RECEIVING_ORG_CLASS_DESC= "Department of Health";
	public static final String ELR_RECEIVING_STANDARD_INDUSTRY_CLASS_CD = "621399";
	public static final String ELR_PERFORMING_STANDARD_INDUSTRY_CLASS_CD = "621511";
	public static final String ELR_RECEIVING_STANDARD_INDUSTRY_CLASS_DESC = "Offices of Misc. Health Providers";
	public static final String ELR_ORG = "ORG";
	public static final String ELR_MESSAGE_CTRL_CD="MCID";
	public static final String ELR_MESSAGE_CTRL_DESC="Message Control ID";
	public static final String ELR_NEXT_OF_KIN="NOK";
	public static final String ELR_NEXT_OF_KIN_RL_CLASS="RL_CLASS";
	public static final String ELR_CON = "CON";
	public static final String ELR_POSTAL_CD="PST";
	public static final String ELR_USE_EMERGENCY_CONTACT_CD="EC";
	public static final String ELR_HOUSE_DESC="House";
	public static final String ELR_HOUSE_CD="H";
	public static final String ELR_TELE_CD="TELE";
	public static final String ELR_PHONE_CD="PH";
	public static final String ELR_PHONE_DESC="PHONE";
	public static final String ELR_OP_CD="OP";
	public static final String ELR_OP_DESC="Order Provider";
	public static final String ELR_ROLE_REASON="because";
	public static final String ELR_OTHER_CD="OTH";
	public static final String ELR_OTHER_DESC="Other";
	public static final String ELR_ORDERER_CD="ORD";
	public static final String ELR_ORDERER_DESC="Orderer";
	public static final String ELR_OFFICE_DESC="Office";
	public static final String ELR_OFFICE_CD="O";
	public static final String ELR_WORKPLACE_CD="WP";
	public static final String ELR_WORKPLACE_DESC="Workplace";
	public static final String CTRL_CD_DISPLAY_FORM="LabReport";
	public static final String ELR_PATIENT_SUBJECT_CD="PATSBJ";
	public static final String ELR_PATIENT_SUBJECT_DESC="Patient Subject";
	public static final String ELR_FILLER_NUMBER_CD="FN";
	public static final String ELR_FILLER_NUMBER_DESC="Filler Number";
	public static final String ELR_EQUIPMENT_INSTANCE_CD="EII";
	public static final String ELR_EQUIPMENT_INSTANCE_DESC="Equipment Instance Identifier";
	public static final String ELR_LOINC_CD="LN";
	public static final String ELR_LOINC_DESC="LOINC";
	public static final String ELR_LOCAL_DESC="LOCAL";
	public static final String ELR_LOCAL_CD="L";
	public static final String ELR_SPECIMEN_PROCURER_CD="SPP";
	public static final String ELR_SPECIMEN_PROCURER_DESC="Specimen Procurer";

	public static final String ELR_PROV_CD="PROV";
	public static final String ELR_PROVIDER_CD="PRV";
	public static final String ELR_PROVIDER_DESC="Provider";
	public static final String ELR_EMP_IDENT_CD="EI";
	public static final String ELR_EMP_IDENT_DESC="Employee Identifier";
	public static final String ELR_SPECIMEN_CD="SPC";
	public static final String ELR_SPECIMEN_DESC="Specimen";
	public static final String ELR_NO_INFO_CD="NI";
	public static final String ELR_NO_INFO_DESC="No Information Given";
	public static final String ELR_MAT_CD="MAT";
	public static final String ELR_MAT_DESC="MATERIAL";
	public static final String ELR_PROVIDER_REG_NUM_CD="PRN";
	public static final String ELR_PROVIDER_REG_NUM_DESC="Provider Registration Number";
	public static final String ELR_LAB_PROVIDER_CD="LABP";
	public static final String ELR_LAB_PROVIDER_DESC="Laboratory Provider";
	public static final String ELR_LAB_VERIFIER_CD="VRF";
	public static final String ELR_LAB_VERIFIER_DESC="Verifier";
	public static final String ELR_LAB_ASSISTANT_CD="ASS";
	public static final String ELR_LAB_ASSISTANT_DESC="Assistant";
	public static final String ELR_LAB_PERFORMER_CD="PRF";
	public static final String ELR_LAB_PERFORMER_DESC="Performer";
	public static final String ELR_LAB_ENTERER_CD="ENT";
	public static final String ELR_LAB_ENTERER_DESC="Enterer";

	public static final String ELR_COMP_CD="COMP";
	public static final String ELR_COMP_DESC="Has Component";
	public static final String ELR_SNOMED_CD="SNM";
	public static final String ELR_SNOMED_DESC="SNOMED";

	public static final String ELR_REPORTING_ENTITY_CD="RE";
	public static final String ELR_REPORTING_ENTITY_DESC="Reporting Entity";


	public static final String ELR_PATIENT_ROLE_CD="PAT";
	public static final String ELR_NEXT_F_KIN_ROLE_CD="NOK";
	public static final String ELR_NEXT_F_KIN_ROLE_DESC="Next of Kin";
	public static final String ELR_DOC_TYPE_CD = "11648804";
	public static final String ELR_REFER_CD = "REFR";
	public static final String ELR_REFER_DESC = "Refers";
	public static final String ELR_SUPPORT_CD = "SPRT";
	public static final String ELR_SUPPORT_DESC = "Has Support";
	public static final String ELR_LAB222_CD = "LAB222";
	public static final String ELR_RESULT_CD = "Result";
	public static final String ELR_ORDER_CD = "Order";
	public static final String ELR_REF_ORDER_CD = "R_Order";
	public static final String ELR_REF_RESULT_CD ="R_Result";
	public static final String ELR_YES_CD ="Y";
	public static final String ELR_PATIENT_DESC="Observation Subject";
	public static final Long ELR_ADD_USER_ID=new Long(99);
	public static final String ELR_ADD_REASON_CD="because";
	public static final String ELR_OBS_STATUS_CD="D";
	public static final String ELR_TRACER_CD="TRC";
	public static final String ELR_TRACER_DESC="Tracker";
	public static final String TYPE_CD_SSN ="SS";
	public static final String ASSIGNING_AUTH_CD_SSN ="SSN";
	public static final Long ELR_NBS_DOC_META_UID=new Long(1005);
	public static final String ELR_STUCTURED_NUMERIC_CD="SN";
	public static final String ELR_NUMERIC_CD="NM";
	public static final String ELR_CODED_EXEC_CD="CE";
	public static final String ELR_CODED_WITH_EXC_CD="CWE";
	public static final String ELR_STRING_CD="ST";
	public static final String ELR_TEXT_CD="TX";
	public static final String ELR_TEXT_TS="TS";
	public static final String ELR_TEXT_DT="DT";
	public static final String ELR_COPY_TO_DESC="Copy To";
	public static final String ELR_COPY_TO_CD="CT";
	public static final String ELR_OBX_COMMENT_TYPE="N";

	public static final String ELR_USA_DESC="USA";
	public static final String ELR_USA_CD="840";
	public static final String ELR_CODED_TYPE="C";
	public static final String ELR_STRING_TYPE="ST";
	public static final String ELR_NUMERIC_TYPE="N";
	public static final String ELR_STUCTURED_NUMERIC_TYPE="SN";
	public static final String ELR_CONFIRMED_CD="C";
	public static final String ELR_OPEN_CD="O";
	public static final String ELR_INDIVIDUAL = "I";
	// Activity Log
	public static final String ELR_RECORD_NM = "ELR_IMPORT";
	public static final String ELR_RECORD_TP = "Electronic Lab Report";

	// Activity Log Messages
	// ---------------------

	// Summary messages and related Status values
	public static final STATUS_VAL SUM_MSG_JURISDICTION_FAIL_STS = STATUS_VAL.Success;
	public static final String SUM_MSG_JURISDICTION_FAIL =
			"Jurisdiction and/or Program Area could not be derived. " +
			"The Lab Report is logged in Documents Requiring Security Assignment queue.";

	public static final STATUS_VAL SUM_MSG_ALGORITHM_FAIL_STS = STATUS_VAL.Success;
	public static final String SUM_MSG_ALGORITHM_FAIL =
			"A matching algorithm was not found. The Lab Report is logged in Documents Requiring Review queue.";

	public static final STATUS_VAL SUM_MSG_INVESTIGATION_FAIL_STS = STATUS_VAL.Failure;
	public static final String SUM_MSG_INVESTIGATION_FAIL =
			"Error creating investigation.  See Activity Details.";

	public static final STATUS_VAL SUM_MSG_MISSING_INVESTIGATION_FLDS_STS = STATUS_VAL.Failure;
	public static final String SUM_MSG_MISSING_INVESTIGATION_FLDS =
			"Missing fields required to create investigation";

	public static final STATUS_VAL SUM_MSG_NOTIFICATION_FAIL_STS = STATUS_VAL.Failure;
	public static final String SUM_MSG_NOTIFICATION_FAIL =
			"Error creating notification.  See Activity Details.";

	public static final STATUS_VAL SUM_MSG_MISSING_NOTIFICATION_FLDS_STS = STATUS_VAL.Failure;
	public static final String SUM_MSG_MISSING_NOTIFICATION_FLDS =
			"Missing fields required to create notification";

	public static final STATUS_VAL SUM_MSG_ERROR_LAB_REVIEWED_STS = STATUS_VAL.Failure;
	public static final String SUM_MSG_ERROR_LAB_REVIEWED =
			"Error marking lab as reviewed.  See Activity Details.";

	public static final STATUS_VAL SUM_MSG_CREATING_LAB_STS = STATUS_VAL.Failure;
	public static final String SUM_MSG_CREATING_LAB =
			"Error creating lab.  See Activity Details.";

	public static final STATUS_VAL SUM_MSG_UPDATING_LAB_STS = STATUS_VAL.Failure;
	public static final String SUM_MSG_UPDATING_LAB =
			"Error updating Lab.  See Activity Details.";

	// Detail messages
	public static final String DET_MSG_PATIENT_FOUND =
			"Patient match found: %1, %2 (UID: %3).";
	public static final String DET_MSG_PATIENT_NOT_FOUND =
			"Patient match not found; New Patient created: %1, %2 (UID: %3).";
	public static final String DET_MSG_PATIENT_MULTIPLE_FOUND =
			"Multiple patient matches found; New Patient created: %1, %2 (UID: %3).";
	public static final String FINAL_POST_CORRECTED =
			"Lab report %1 was not updated.  Final report with Accession # %2 was sent after a corrected report was received.";
	public static final String PRELIMINARY_POST_FINAL =
			"Lab report %1 was not updated.  Preliminary report with Accession # %2 was sent after a final report was received.";
	public static final String PRELIMINARY_POST_CORRECTED =
			"Lab report %1 was not updated.  Preliminary report with Accession # %2 was sent after a corrected report was received.";
	public static final String LABTEST_SEQUENCE =
			"An Observation Lab test match was found for Accession # %1, but the activity time is out of sequence.";
	public static final String MULTIPLE_PERFORMLAB =
			"Multiple Performing Labs were associated to a single result Test.";
	public static final String NO_ORDERINGPROVIDER =
			"The Ordering Provider and the Ordering Facility are missing.";
	public static final String NO_ORDTEST_NAME =
			"The code for Ordered Test name is missing.";
	public static final String NO_REFLEX_ORDERED_NM =
			"The code for Reflex Ordered Test name is missing.";
	public static final String NO_REFLEX_RESULT_NM =
			"The code for Reflex Resulted Test name is missing.";
	public static final String NO_RESULT_NAME =
			"The code for Result Test name is missing.";
	public static final String NO_DRUG_NAME =
			"The code for Drug name for Susceptibility test is missing.";
	public static final String SUBJECTMATCH_MULT =
			"Multiple patient matches found. New Patient created: %1 (UID: %2).";
	public static final String SUJBECTMATCH_NO =
			"Patient match not found; New Patient created: %1 (UID: %2).";
	public static final String MULTIPLE_PROVIDER =
			"Multiple Ordering Providers were associated to a single Ordered Test. The message was processed in NBS using the first Ordering Provider.";
	public static final String MULTIPLE_COLLECTOR =
			"Multiple Specimen Collectors were associated to a single Ordered Test. The message was processed in NBS using the first Specimen Collector.";
	public static final String MULTIPLE_INTERP =
			"Multiple Principal Interpreters were associated to a single Ordered Test. The message was processed in NBS using the first Principal Interpreter.";
	public static final String MULTIPLE_ORDERFAC =
			"Multiple Ordering Facilities were associated to a single Ordered Test. The message was processed in NBS using the first Ordering Facility.";
	public static final String MULTIPLE_RECEIVEFAC =
			"Multiple Receiving Facilities were associated to a single Ordered Test. The message was processed in NBS using the first Receiving Facility.";
	public static final String MULTIPLE_SPECIMEN =
			"Multiple Specimens were associated to a single Ordered Test. The message was processed in NBS using the first Specimen.";
	public static final String TRANSLATE_ETHN_GRP =
			"The Ethnicity code provided in the message is not found in the SRT database.  The code is saved to the NBS.";
	public static final String TRANSLATE_OBS_METH =
			"The Observation Method code provided in the message is not found in the SRT database.  The code is saved to the NBS.";
	public static final String TRANSLATE_RACE =
			"The Race code provided in the message is not found in the SRT database.  The code is saved to the NBS.";
	public static final String TRANSLATE_SEX =
			"The Sex code provided in the message is not found in the SRT database.  The code is saved to the NBS.";
	public static final String INFO_SSN_INVALID =
			"An invalid SSN is in the message.";
	public static final String NULL_CLIA =
			"The Reporting Facility did not have a CLIA number.";
	public static final String FILLER_FAIL =
			"The Filler Order Number is missing.";
	public static final String NO_MATCHING_ALGORITHM =
			"Matching algorithm for lab report not found.";
	public static final String ODSACTIVTOTIME_FAIL =
			"The Date/Time of the Analysis is missing.  It is required for to create a lab in NBS.";
	public static final String UNIVSRVCID =
			"The Universal Service Identifier is missing.";
	public static final String SYSTEM_EXCEPTION =
			"Unknown exception.  Please contact Support.";
	public static final String NO_INV_PERMISSION =
			"User %1 not authorized to autocreate investigation";
	public static final String NO_LAB_MARK_REVIEW_PERMISSION =
			"User %1 not authorized to mark the lab as reviewed";
	public static final String NO_NOT_PERMISSION =
			"User %1 not authorized to autocreate notification.";
	public static final String NO_LAB_CREATE_PERMISSION =
			"User %1 not authorized to autocreate lab report.";
	public static final String NO_LAB_UPDATE_PERMISSION =
			"User %1 not authorized to update a Lab Report.";
	public static final String JURISDICTION_DERIVED =
			"Jurisdiction derived.";
	public static final String PROG_AREA_DERIVED =
			"Program Area derived.";
	public static final String NO_JURISDICTION_DERIVED =
			"Jurisdiction not derived.";
	public static final String NO_PROG_AREA_DERIVED =
			"Program Area not derived.";
	public static final String LAB_CREATE_SUCCESS =
			"Lab %1 created and associated to Patient.";
	public static final String DOC_CREATE_SUCCESS =
			"Document created and associated to Lab.";
	public static final String MISSING_ORD_PROV =
			"The Ordering Provider is missing.";
	public static final String MISSING_ORD_FAC =
			"The Ordering Facility is missing.";
	public static final String TRANSLATE_OBS_STATUS =
			"Observation Status not found in SRT.";
	public static final String LAB_UPDATE_SUCCESS_DRRQ  =
			"Lab %1 updated and logged in Documents Requiring Review queue.";
	public static final String LAB_UPDATE_SUCCESS_DRSA  =
			"Lab %1 updated and logged in Documents Requiring Security Assignment queue.";
	public static final String SUBJECT_MATCH_FOUND =
			"Patient match found: %1 (UID: %2).";
	public static final String NO_REASON_FOR_STUDY =
			"The code for Reason For Study is missing.";
	public static final String MULTIPLE_OBR = "The ELR includes Multiple Ordered Tests (OBRs).  Child OBR(s) are missing Parent Result (OBR-26) and/or Parent (OBR-29) values.";

	public static final String INV_SUCCESS_CREATED="Investigation created (UID: %1).";
	
	public static final String LAB_ASSOCIATED_TO_INV = "Lab associated to Investigation (UID: %1).";

	public static final String NOT_SUCCESS_CREATED="Notification created (UID: %1).";

	public static final String INVALID_XML="The XML does not include information required to process the ELR into NBS.";

	public static final String MISSING_ORD_PROV_AND_ORD_FAC="The Ordering Provider and the Ordering Facility are missing.";

	public static final String MULTIPLE_SUBJECT="Multiple Patients were included in the ELR.";
	public static final String NO_SUBJECT="The Patient segment is missing from the ELR.";
	public static final String ORDER_OBR_WITH_PARENT="The Parent Ordered Test (OBR) includes a Parent Result (OBR 26) and/or Parent (OBR 29) values.";
	public static final String CHILD_OBR_WITHOUT_PARENT="The ELR includes Multiple Ordered Tests (OBRs).  Child OBR(s) are missing Parent Result (OBR 26) and/or Parent (OBR 29) values.";

	public static final String OFCI="Successfully retain event record.";
	public static final String OFCN="Successfully retain investigation and event record.";

	public static final String UNEXPECTED_RESULT_TYPE = "The ELR includes an unexpected result Data Type (OBX-2). NBS expects ST, TX, CE, DT, TS or SN.";
	public static final String CHILD_SUSC_WITH_NO_PARENT_RESULT = "The ELR includes Multiple Ordered Tests (OBRs).  Child OBR(s) do not have the same Parent Result (OBR-26) and/or Parent (OBR-29) values as the Parent OBR.";

	public static final String ELR_MASTER_LOG_ID_1="1";//"Jurisdiction and/or Program Area could not be derived.  The Lab Report is logged in Documents Requiring Security Assignment queue.";
	public static final String ELR_MASTER_LOG_ID_2="2";//	"A matching algorithm was not found.  The Lab Report is logged in Documents Requiring Review queue.";
	public static final String ELR_MASTER_LOG_ID_3="3";//	"Successfully Create Investigation";
	public static final String ELR_MASTER_LOG_ID_4="4";//"Error creating investigation.  See Activity Details.";
	public static final String ELR_MASTER_LOG_ID_5="5";//"Missing fields required to create investigation.";
	public static final String ELR_MASTER_LOG_ID_6="6";//"Successfully Create Notification";
	public static final String ELR_MASTER_LOG_ID_7="7";//"Error creating notification.  See Activity Details.";
	public static final String ELR_MASTER_LOG_ID_8="8";//"Missing fields required to create notification.";
	public static final String ELR_MASTER_LOG_ID_9="9";//"Error creating investigation.  See Activity Details.";
	public static final String ELR_MASTER_LOG_ID_10="10";//"Error creating notification.  See Activity Details.";
	public static final String ELR_MASTER_LOG_ID_11="11";//"Successfully Mark Lab as Reviewed";
	public static final String ELR_MASTER_LOG_ID_12="12";//"Error marking lab as reviewed.  See Activity Details.";
	public static final String ELR_MASTER_LOG_ID_13="13";//"Error creating lab.  See Activity Details.";
	public static final String ELR_MASTER_LOG_ID_14="14";//"Error updating Lab.  See Activity Details.";
	public static final String ELR_MASTER_LOG_ID_15="15";//"Lab updated successfully and logged in Documents Requiring Review queue";
	public static final String ELR_MASTER_LOG_ID_16="16";//"Unexpected exception.";
	public static final String ELR_MASTER_LOG_ID_17="17";//"Error creating lab.  See Activity Details.";
	public static final String ELR_MASTER_LOG_ID_18="18";//"String or binary data would be truncated.";
	public static final String ELR_MASTER_LOG_ID_19="19";//"Create Lab Blank Identifier";
	public static final String ELR_MASTER_LOG_ID_20="20";//"Invalid date. See Activity Details.";
	public static final String ELR_MASTER_LOG_ID_21="21";//"Successfully Mark Lab as Reviewed and associated to existing investigation";
	public static final String ELR_MASTER_LOG_ID_22="22";//"Lab updated successfully and logged in Documents Requiring Security Assignment queue";

	public static final String ELR_MASTER_MSG_ID_1="Jurisdiction and/or Program Area could not be derived.  The Lab Report is logged in Documents Requiring Security Assignment queue.";
	public static final String ELR_MASTER_MSG_ID_2=	"A matching algorithm was not found.  The Lab Report is logged in Documents Requiring Review queue.";
	public static final String ELR_MASTER_MSG_ID_3=	"Successfully Create Investigation";
	public static final String ELR_MASTER_MSG_ID_4="Error creating investigation.  See Activity Details.";
	public static final String ELR_MASTER_MSG_ID_5="Missing fields required to create investigation.";
	public static final String ELR_MASTER_MSG_ID_6="Successfully Create Notification";
	public static final String ELR_MASTER_MSG_ID_7="Error creating notification.  See Activity Details.";
	public static final String ELR_MASTER_MSG_ID_8="Missing fields required to create notification.";
	public static final String ELR_MASTER_MSG_ID_9="Error creating investigation.  See Activity Details.";
	public static final String ELR_MASTER_MSG_ID_10="Error creating notification.  See Activity Details.";
	public static final String ELR_MASTER_MSG_ID_11="Successfully Mark Lab as Reviewed";
	public static final String ELR_MASTER_MSG_ID_12="Error marking lab as reviewed.  See Activity Details.";
	public static final String ELR_MASTER_MSG_ID_13="Error creating lab.  See Activity Details.";
	public static final String ELR_MASTER_MSG_ID_14="Error updating Lab.  See Activity Details.";
	public static final String ELR_MASTER_MSG_ID_15="Lab updated successfully and logged in Documents Requiring Review queue.";
	public static final String ELR_MASTER_MSG_ID_16="Error creating Lab.  See Activity Details."; //in case of "Unexpected exception." it displays this message;
	public static final String ELR_MASTER_MSG_ID_17="Error creating lab.  See Activity Details.";
	public static final String ELR_MASTER_MSG_ID_18="Error creating lab. Input message length exceeds the field length in NBS, resulting in a truncation error. See Activity Details.";
	public static final String ELR_MASTER_MSG_ID_19="Error creating lab. Blank identifiers in CE or CWE segments 1 or 4. See Activity Details.";
	public static final String ELR_MASTER_MSG_ID_20="Error creating lab. Invalid Date encountered. See Activity Details.";
	public static final String ELR_MASTER_MSG_ID_21="Successfully Mark Lab as Reviewed and associated to existing investigation.";
	public static final String ELR_MASTER_MSG_ID_22="Lab updated successfully and logged in Documents Requiring Security Assignment queue.";


	public static final String SQL_FIELD_TRUNCATION_ERROR_MSG="String or binary data would be truncated";
	public static final String ORACLE_FIELD_TRUNCATION_ERROR_MSG="value too large for column";

	public static final String DATE_VALIDATION="Invalid date in message at: ";
	public static final String DATE_VALIDATION_END_DELIMITER1="--> ";
	public static final String DATE_VALIDATION_PID_PATIENT_BIRTH_DATE_AND_TIME_MSG=DATE_VALIDATION+"PID-7 Patient Birth Date/Time "+DATE_VALIDATION_END_DELIMITER1;
	public static final String DATE_VALIDATION_PID_PATIENT_BIRTH_DATE_NO_TIME_MSG=DATE_VALIDATION+"PID-7 Patient Birth Date "+DATE_VALIDATION_END_DELIMITER1;
	public static final String DATE_VALIDATION_PERSON_NAME_FROM_TIME_MSG=DATE_VALIDATION+"Person Name From Date "+DATE_VALIDATION_END_DELIMITER1;
	public static final String DATE_VALIDATION_PERSON_NAME_TO_TIME_MSG=DATE_VALIDATION+"Person Name To Date "+DATE_VALIDATION_END_DELIMITER1;
	public static final String DATE_VALIDATION_OBR_SPECIMEN_RECEIVED_TIME_MSG=DATE_VALIDATION+"OBR-14 Specimen Received Date/Time "+DATE_VALIDATION_END_DELIMITER1;
	public static final String DATE_VALIDATION_ORC_ORDER_EFFECTIVE_TIME_MSG=DATE_VALIDATION+"ORC-15 Order Effective Date/Time "+DATE_VALIDATION_END_DELIMITER1;
	public static final String DATE_VALIDATION_OBR_RESULTS_RPT_STATUS_CHNG_TO_TIME_MSG=DATE_VALIDATION+"OBR-22 Results Rpt/Status Chng - Date/Time  "+DATE_VALIDATION_END_DELIMITER1;
	public static final String DATE_VALIDATION_OBR_OBSERVATION_DATE_MSG = DATE_VALIDATION+"OBR-7.0 or SPM-17.1 Observation Starting Date/Time "+DATE_VALIDATION_END_DELIMITER1;
	public static final String DATE_VALIDATION_OBR_OBSERVATION_END_DATE_MSG = DATE_VALIDATION+"OBR-8.0 or SPM-17.2 Observation Ending Date/Time "+DATE_VALIDATION_END_DELIMITER1;
	public static final String DATE_VALIDATION_OBX_LAB_PERFORMED_DATE_MSG = DATE_VALIDATION+"OBX-14 Analysis Date/Time "+DATE_VALIDATION_END_DELIMITER1;
	public static final String DATE_VALIDATION_SPM_SPECIMEN_COLLECTION_DATE_MSG = DATE_VALIDATION+"SPM-17 Specimen Collection Date/Time "+DATE_VALIDATION_END_DELIMITER1;
	public static final String DATE_VALIDATION_PATIENT_DEATH_DATE_AND_TIME_MSG= DATE_VALIDATION+"PID-29 Patient Death Date "+DATE_VALIDATION_END_DELIMITER1;
	public static final String DATE_VALIDATION_PID_PATIENT_IDENTIFIER_EFFECTIVE_DATE_TIME_MSG=DATE_VALIDATION+"PID-3 Patient Identifier Effective Date "+DATE_VALIDATION_END_DELIMITER1;
	public static final String DATE_VALIDATION_PID_PATIENT_IDENTIFIER_EXPIRATION_DATE_TIME_MSG=DATE_VALIDATION+"PID-3 Patient Identifier Expiration Date "+DATE_VALIDATION_END_DELIMITER1;

	public static final String DATE_INVALID_FOR_DATABASE=" is before or after the date allowed by the database.";
	
	public static final String MISSING_NOTF_REQ_FIELDS = "The following required Notification field(s) is(are) missing: ";
	
	public static final int UPDATED_LAT_LIST=100; 
	public static final String DEBUG="debug"; 
}




