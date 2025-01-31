/**
* Name:		DataTables.java
* Description:	A class for storing the name of all the database tables.
*               The String constants in this class should be used by other
*               classes instead of hardcoding the name of a database table
*               into the source code.
* Copyright:	Copyright (c) 2001
* Company: 	Computer Sciences Corporation
* @author	Brent Chen & NEDSS Development Team
* @version	1.0
*/


package gov.cdc.nedss.util;



public interface DataTables
{
    public static final String UID_TABLE = "UID_Generator";
    public static final String ENTITY_TABLE = "Entity";
    public static final String PERSON_TABLE = "Person";
    public static final String PERSON_RACE_TABLE = "Person_Race";
    public static final String PERSON_RACE_MOD_TABLE = "Person_Race_Mod";
    public static final String PERSON_ETHNIC_GROUP_TABLE = "Person_Ethnic_Group";
    public static final String ENTITY_ID_TABLE = "Entity_ID";
    public static final String PERSON_NAME_TABLE = "Person_Name";
    public static final String ENTITY_LOCATOR_PARTICIPATION_TABLE = "Entity_Locator_Participation";
    public static final String TELE_LOCATOR_TABLE = "Tele_Locator";
    public static final String PHYSICAL_LOCATOR_TABLE = "Physical_Locator";
    public static final String POSTAL_LOCATOR_TABLE = "Postal_Locator";
    public static final String ENTITY_GROUP_TABLE = "Entity_Group";
    public static final String ENTITY_ROLE_TABLE = "Entity_Role";
    public static final String PLACE_TABLE = "Place";
    public static final String ORGANIZATION_NAME_TABLE = "Organization_Name";
    public static final String ORGANIZATION_TABLE = "Organization";
    public static final String MATERIAL_TABLE = "Material";
    public static final String MANUFACT_MATERIAL_TABLE = "Manufactured_material";
    public static final String NONPERSON_TABLE = "Non_Person_living_subject";
    public static final String INTERVIEW_TABLE = "Interview";
    public static final String NBS_ANSWER_TABLE = "nbs_answer";
    public static final String NBS_ANSWER_HIST_TABLE = "nbs_answer_hist";
    public static final String NBS_CONFIGURATION = "NBS_CONFIGURATION";
 //   public static final String NOTIFICATION_TABLE = "Notification";
//    public static final String PUBLIC_HEALTH_CASE = "PUBLIC_HEALTH_CASE";

    public static final String CN_TRANSPORTQ_OUT_TABLE = "CN_transportq_out";
    public static final String NETSS_TRANSPORTQ_OUT_TABLE = "NETSS_TransportQ_out";

    public static final String PERSON_TABLE_HIST = "Person_hist";
    public static final String PERSON_RACE_TABLE_HIST = "Person_Race_hist";
    public static final String PERSON_ETHNIC_GROUP_TABLE_HIST = "Person_Ethnic_Group_hist";
    public static final String ENTITY_ID_TABLE_HIST = "Entity_id_hist";
    public static final String PERSON_NAME_TABLE_HIST = "Person_name_hist";
    public static final String ENTITY_LOCATOR_PARTICIPATION_TABLE_HIST = "Entity_loc_participation_hist";

    public static final String PHYSICAL_LOCATOR_TABLE_HIST = "Physical_locator_hist";
    public static final String POSTAL_LOCATOR_TABLE_HIST = "Postal_locator_hist";
    public static final String TELE_LOCATOR_TABLE_HIST = "Tele_locator_hist";
   /*  Act related tables */
   // public static final String UID_TABLE = "UID_Generator";
    public static final String ACTIVITY_TABLE = "Act";
    public static final String PUBLIC_HEALTH_CASE_TABLE  = "Public_Health_Case";
    public static final String PARTICIPATION_TABLE = "Participation";
    public static final String LAB_EVENT = "Lab_event";
    public static final String CONFIRMATION_METHOD_TABLE = "Confirmation_Method";
    public static final String OBS_VALUE_TXT_TABLE = "Obs_Value_Txt";
    public static final String OBS_VALUE_DATE_TABLE = "Obs_Value_Date";
    public static final String OBS_VALUE_CODED_TABLE = "Obs_Value_Coded";
    public static final String OBS_VALUE_CODED_MOD_TABLE = "Obs_Value_Coded_Mod";
    public static final String OBS_VALUE_NUMERIC_TABLE = "Obs_Value_Numeric";
    public static final String ACTIVITY_ID_TABLE = "Act_id";
    public static final String OBSERVATION_REASON_TABLE = "Observation_reason";
    public static final String OBSERVATION_INTERP_TABLE = "Observation_interp";
    public static final String OBSERVATION_TABLE = "Observation";
    public static final String INTERVENTION_TABLE = "Intervention";
    public static final String PROCEDURE1_TABLE = "Procedure1";
    public static final String SUBSTANCE_ADMINISTRATION_TABLE = "Substance_administration";
    public static final String NOTIFICATION_TABLE = "Notification";
    public static final String NOTIFICATION_HIST_TABLE = "Notification_hist";
    public static final String ACTIVITY_LOCATOR_PARTICIPATION_TABLE = "Act_Locator_Participation";
    public static final String ACT_RELATIONSHIP = "Act_Relationship";
    public static final String TREATMENT_TABLE = "treatment";
    public static final String TREATMENT_PROCEDURE_TABLE = "treatment_procedure";
    public static final String TREATMENT_ADMINISTERED_TABLE = "treatment_administered";
    public static final String TREATMENT_HIST_TABLE = "treatment";
    public static final String TREATMENT_PROCEDURE_HIST_TABLE = "treatment_procedure";
    public static final String TREATMENT_ADMINISTERED_HIST_TABLE = "treatment_administered";
    public static final String NBS_CASE_ANSWER_TABLE = "NBS_CASE_ANSWER";
    public static final String NBS_CASE_ANSWER_TABLE_HIST = "NBS_CASE_ANSWER_HIST";
    public static final String NBS_ACT_ENTITY_TABLE = "NBS_ACT_ENTITY";
    public static final String NBS_CASE_ENTITY_TABLE_HIST = "NBS_ACT_ENTITY_HIST";
    public static final String ALERT = "ALERT";
    public static final String ALERT_USER = "ALERT_USER";
    public static final String USER_EMAIL = "USER_EMAIL";
    public static final String ALERT_EMAIL_MESSAGE = "ALERT_EMAIL_MESSAGE";
    public static final String EXPORT_RECEIVING_FACILITY = "EXPORT_RECEIVING_FACILITY";
    public static final String NBS_DOCUMENT_TABLE = "NBS_document";
    public static final String NBS_DOCUMENT_METADATA_TABLE = "NBS_document_metadata";
    public static final String CASE_MANAGEMENT_TABLE = "CASE_MANAGEMENT";
    public static final String XSS_FILTER_PATTERN = "XSS_FILTER_PATTERN";
    public static final String CASE_MANAGEMENT_HIST_TABLE = "CASE_MANAGEMENT_HIST";
    
    public static final String ACTIVITY_LOG_TABLE = "Activity_log";
    public static final String USER_PROFILE_TABLE = "USER_PROFILE";

    //SRT Tables
    public static final String CODE_VALUE_GENERAL = "Code_value_general";
    public static final String CONDITION_CODE_VIEW = "V_Condition_code";
    public static final String COUNTRY_CROSS_REFERENCE = "Country_XREF";
    public static final String LAB_RESULT_VIEW = "V_Lab_result";
    public static final String RACE_CODE = "Race_code";
    public static final String STATE_CODE_VIEW = "V_State_code";
    public static final String STATE_COUNTY_CODE_VALUE = "State_county_code_value";
    public static final String STANDARD_CROSS_REFERENCE = "Standard_XREF";
    
    public static final String CT_CONTACT = "CT_CONTACT";
    public static final String CT_CONTACT_HIST = "CT_CONTACT_HIST";
    public static final String CT_CONTACT_ANSWER = "CT_CONTACT_ANSWER";
    public static final String CT_CONTACT_ANSWER_HIST = "CT_CONTACT_ANSWER_HIST";
    public static final String CT_CONTACT_ATTACHMENT = "CT_CONTACT_ATTACHMENT";
    public static final String WA_RULE_METADATA= "WA_RULE_METADATA";
    public static final String NBS_ATTACHMENT = "NBS_ATTACHMENT";
    public static final String NBS_NOTE = "NBS_NOTE";
    public static final String NBS_EDX_PATIENT_MATCH_TABLE = "EDX_PATIENT_MATCH";
    //Database Security Tables
    public static final String SECURE_BUSINESS_OBJECT_RIGHT = "AUTH_BUS_OBJ_RT";
    public static final String SECURE_BUSINESS_OBJECT_TYPE = "AUTH_BUS_OBJ_TYPE";
    public static final String SECURE_BUSINESS_OPERATION_RIGHT = "AUTH_BUS_OP_RT";
    public static final String SECURE_BUSINESS_OPERATION_TYPE = "AUTH_BUS_OP_TYPE";
    public static final String SECURE_PERMISSION_SET = "Auth_perm_set";
    public static final String SECURE_PROGRAM_AREA_ADMIN = "AUTH_PROG_AREA_ADMIN";
    public static final String SECURE_USER = "Auth_user";
    public static final String SECURE_USER_ROLE = "AUTH_USER_ROLE";
    public static final String SECURE_USER_ROLE_HISTORY = "Secure_user_role_history";
    public static final String SECURE_Security_log="Security_log";
    public static final String MESSAGE_LOG = "Message_Log";
}//end of DataTables interface
