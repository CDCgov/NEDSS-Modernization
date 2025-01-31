package gov.cdc.nedss.util;

/**
 * Title:        NEDSSConstants
 * Description:  Constants for NEDSS project
 * Copyright:    Copyright (c) 2002
 * Company:      Computer Sciences Corporation
 * @author       NEDSS Development Team
 * @version      1.0.0
 */

import java.sql.Timestamp;
import java.util.Date;

public class NEDSSConstants {
    /**
     * Originally from NEDSSConstantUtil.java
     */

    //only for build C
    public static final String CDC = "1";

    public static final String TN = "2";

    public static final String NE = "3";

    public static final String STATE = CDC;

    //odbc names for db connection
    public static final String MSGIN = "msgIn";

    public static final String MSGOUT = "msgOut";

    public static final String ELRXREF = "elr";

    public static final String ODS = "nedss1";

    public static final String RDB = "rdb";

    public static final String SRT = "srt";

    /**
     * Begin to remove the following section ...
     * The section below is no longer valid for NEDSS project
     */

    /** OBJECT Constants start with this value and increments one by one */
    public static final int LOWER_BOUND_COMMAND = 0;

    /** Unknown Command */
    public static final int UNKNOWN_COMMAND = LOWER_BOUND_COMMAND;

    /** Report Command */
    public static final int REPORT = LOWER_BOUND_COMMAND + 7;

    /** TaskList Command */
    public static final int TASKLIST = LOWER_BOUND_COMMAND + 10;

    public static final String SEARCH = "search";

    public static final String SEARCH_RESULTS = "search_results";

    public static final String VIEW = "view";

    public static final String EDIT = "edit";

    public static final String CREATE = "create";

    public static final String DEL = "delete";

    public static final String SETUP = "setup";

    public static final String SORT_NOTIFICATION = "sortNotification";

    /** command processor classes */
    public static final String[] commandProcessor = {
            "gov.cdc.nedss.webapp.nbs.action.report.ReportWebProcessor", // UNKNOWN_COMMAND
            "gov.cdc.nedss.webapp.nbs.action.report.ReportWebProcessor", // LOGON_COMMAND
            "gov.cdc.nedss.webapp.nbs.action.report.ReportWebProcessor", // MAIN_PAGE_COMMAND
            "gov.cdc.nedss.cdm.commands.PersonCommandWebProcessor", // PERSON_COMMAND
            "gov.cdc.nedss.helpers.ObservationCommand", // OBSERVATION_COMMAND
            "gov.cdc.nedss.wum.commands.CoreDemographicCommandWebProcessor",
            "gov.cdc.nedss.wum.commands.InvestigationWebProcessor", //INVESTIGATION_VIEW
            "gov.cdc.nedss.webapp.nbs.action.report.ReportWebProcessor", //REPORT
            "gov.cdc.nedss.wum.commands.NotificationWebProcessor", //NOTIFICATION_WEB_PROCESSOR
            "gov.cdc.nedss.wum.commands.WorkupWebProcessor", // WORKUP_COMMAND
            "gov.cdc.nedss.webapp.nbs.action.report.ReportWebProcessor" };

    /** Operation Types (Actually, these are subcommands for each type of Command. */

    /** OBJECT Constants start with this value and increments one by one */
    public static final int LOWER_BOUND_OPERATION = 100;

    /** Unknown Operation on Person Command */
    public static final int UNKNOWN_OPERATION = LOWER_BOUND_OPERATION;

    /** Person Find Screen on Person Command */
    public static final int FIND_SCREEN = LOWER_BOUND_OPERATION + 1;

    /** Person Find Search on Person Command */
    public static final int FIND = LOWER_BOUND_OPERATION + 2;

    /** Person Add on Person Command */
    public static final int PERSON_ADD = LOWER_BOUND_OPERATION + 3;

    /** Person Edit on Person Command */
    public static final int PERSON_EDIT = LOWER_BOUND_OPERATION + 4;

    /** Person Compare on Person Command */
    public static final int PERSON_VIEW = LOWER_BOUND_OPERATION + 5;

    public static final int PERSON_SORT = LOWER_BOUND_OPERATION + 6;

    public static final int COMPARE = LOWER_BOUND_OPERATION + 7;

    public static final int PERSON_DELETE = LOWER_BOUND_OPERATION + 8;

    /** Core demographic add */
    public static final int CORE_DEMOGRAPHIC_ADD = LOWER_BOUND_OPERATION + 9;

    /** INVESTIGATION_CHOOSE */
    public static final int INVESTIGATION_CHOOSE = LOWER_BOUND_OPERATION + 11;

    /** INVESTIGATION_CREATE */
    public static final int INVESTIGATION_SORT = LOWER_BOUND_OPERATION + 12;

    /** INVESTIGATION_CREATE */
    public static final int INVESTIGATION_DELETE = LOWER_BOUND_OPERATION + 13;

    /** INVESTIGATION_VIEW */
    public static final int INVESTIGATION_VIEW = LOWER_BOUND_OPERATION + 14;

    /** INVESTIGATION_EDIT */
    public static final int INVESTIGATION_EDIT = LOWER_BOUND_OPERATION + 15;

    /** Reporting constants .... */
    /** LIST */
    public static final int REPORT_LIST = LOWER_BOUND_OPERATION + 16;

    /** REPORT_BASIC */
    public static final int REPORT_BASIC = LOWER_BOUND_OPERATION + 17;

    /** REPORT_ADVANCED */
    public static final int REPORT_ADVANCED = LOWER_BOUND_OPERATION + 18;

    /** REPORT_COLUMN */
    public static final int REPORT_COLUMN = LOWER_BOUND_OPERATION + 19;

    /** REPORT_RUN */
    public static final int REPORT_RUN = LOWER_BOUND_OPERATION + 20;

    /** REPORT_DELETE */
    public static final int REPORT_DELETE = LOWER_BOUND_OPERATION + 21;

    /** UPDATE_REPORT */
    public static final int UPDATE_REPORT = LOWER_BOUND_OPERATION + 22;

    /** SAVE_AS_NEW_REPORT */
    public static final int SAVE_AS_NEW_REPORT = LOWER_BOUND_OPERATION + 23;

    /** EXPORT_REPORT */
    public static final int EXPORT_REPORT = LOWER_BOUND_OPERATION + 24;

    /** NOTIFICATION_CREATE */
    public static final int NOTIFICATION_CREATE = LOWER_BOUND_OPERATION + 25;

    /** ADD */
    public static final int ADD = LOWER_BOUND_OPERATION + 26;

    //public static final int CREATE = LOWER_BOUND_OPERATION + 27;
    //public static final int VIEW = LOWER_BOUND_OPERATION + 28;
    //public static final int EDIT = LOWER_BOUND_OPERATION + 29;
    /** SORT */
    public static final int SORT = LOWER_BOUND_OPERATION + 30;

    /** DELETE */
    public static final int DELETE = LOWER_BOUND_OPERATION + 31;

    /** CHOOSE */
    public static final int CHOOSE = LOWER_BOUND_OPERATION + 32;

    /** MANAGE */
    public static final int MANAGE = LOWER_BOUND_OPERATION + 33;

    /** REVIEW */
    public static final int REVIEW = LOWER_BOUND_OPERATION + 34;

    public static final int SORT_INVESTIGATION_SUMMARY = LOWER_BOUND_OPERATION + 35;

    public static final int VIEW_WORKUP = LOWER_BOUND_OPERATION + 36;

    /** other REPORT constants */
    /** RUN_REPORT: Use this to run the report in a new window. */
    public static final int RUN_REPORT = LOWER_BOUND_OPERATION + 37;

    /** RUN_PAGE: Use this to take the user to the Run Page. */
    public static final int RUN_PAGE = LOWER_BOUND_OPERATION + 38;

    /** OWNER: true or false */
    public static final int OWNER = LOWER_BOUND_OPERATION + 39;

    /** GET_COUNTIES: Use this to get Counties for States selected */
    public static final int GET_COUNTIES = LOWER_BOUND_OPERATION + 40;

    /** GET_CODEDVALUES: Use this to load coded values used to populate Advance Filter Values**/
    public static final int GET_CODEDVALUES = LOWER_BOUND_OPERATION + 53;

    /** SAVE_PAGE */
    public static final int SAVE_PAGE = LOWER_BOUND_OPERATION + 41;

    /** dynamically generate list of counties based on state */
    public static final int PERSON_LOAD_COUNTY_CREATE = LOWER_BOUND_OPERATION + 42;

    public static final int PERSON_LOAD_COUNTY_EDIT = LOWER_BOUND_OPERATION + 43;

    public static final int PERSON_CANCEL = LOWER_BOUND_OPERATION + 44;

    public static final int OBSERVATION_CHOOSE = LOWER_BOUND_OPERATION + 45;

    public static final int GET_MY_INVESTIGATIONS = LOWER_BOUND_OPERATION + 46;

    public static final int GET_TASKLIST_ITEMS = LOWER_BOUND_OPERATION + 47;

    public static final int GET_NOTIFICATIONS_FOR_APPROVAL = LOWER_BOUND_OPERATION + 48;

    public static final String SORT_OBSERVATION_SUMMARY = "SORT_OBSERVATIONS";

    public static final int SORT_NOTIFICATION_LIST = LOWER_BOUND_OPERATION + 50;

    public static final int SELECT_PERMISSION_SET = LOWER_BOUND_OPERATION + 51;

    public static final int LOAD_PERMISSION_SETS = LOWER_BOUND_OPERATION + 52;

    /**
     * End of the section
     * The section above is no longer valid for NEDSS project
     */

    /* other constants go here */
    public static final String STATUS_ACTIVE = "A";

    public static final String STATUS_INACTIVE = "I";

    public static final String STATUS_OPEN = "O";

    public static final String RECORD_STATUS_ACTIVE = "ACTIVE";

    public static final String RECORD_STATUS_INACTIVE = "INACTIVE";


    public static final String RECORD_STATUS_Active = "Active";

    public static final String RECORD_STATUS_Inactive = "Inactive";


    public static final String RECORD_STATUS_SUPERCEDED = "SUPERCEDED";

    public static final String RECORD_STATUS_LOGICAL_DELETE = "LOG_DEL";

    public static final String ACT_UID_LIST_TYPE = "ACTUID";

    public static final String ENTITY_UID_LIST_TYPE = "ENTITYUID";

    public static final String SOURCE_ACT_UID_LIST_TYPE = "SOURCEACTUID";

    // public static final String RECORD_STATUS_PROC_W_ASSOC = "PROC_W_ASSOC";
    public static final String RECORD_STATUS_PROCESSED = "PROCESSED";

    public static final String RECORD_STATUS_UNPROCESSED = "UNPROCESSED";

    public static final String APPROVED_STATUS = "APPROVED";

    public static final String PENDING_APPROVAL_STATUS = "PEND_APPR";

    public static final String CASE_CLASS_CODE_SET_NM = "PHC_CLASS";

    public static final String CASE_CLASS_CODE_CONFIRMED = "C";

    public static final String CASE_CLASS_CODE_NOT_A_CASE = "N";

    public static final String INVESTIGATION_STATUS_CODE_CLOSED = "C";

    public static final String PHC_IN_STS="PHC_IN_STS";


    /*Participation type codes*/
    public static final String PHC_SUBJECT_TYPE_CODE_PART_DT = "PHCSUB";

    public static final String VACCINATION_SUBJECT_TYPE_CODE_PART_DT = "VACSUBJ";

    public static final String VACCINATION_ADMINISTERED_TYPE_CODE = "VACADMIN";

    public static final String OBSERVATION_LAB_GEN_SUBJECT_TYPE_CODE_PART_DT = "SUBJOFLABGEN";

    public static final String OBSERVATION_LAB_MICRO_SUBJECT_TYPE_CODE_PART_DT = "SUBJOFLABMICRO";

    public static final String OBSERVATION_GEN_OBS_SUBJECT_TYPE_CODE_PART_DT = "SUBJOFGENOBS";

    public static final String OBSERVATION_LAB_GEN_SUBJECT_PERSON_TYPE_CODE_PART_DT = "SUBJOFLABGEN";

    public static final String OBSERVATION_LAB_GEN_GROUP_LEVEL_CODE = "L_1";

    public static final String OBSERVATION_LAB_MICRO_SUBJECT_PERSON_TYPE_CODE_PART_DT = "SUBJOFLABMICRO";

    public static final String OBSERVATION_ORDER_PROVIDER_PERSON_TYPE_CODE_PART_DT = "ORDEROFLABTEST";

    public static final String OBSERVATION_ORDER_FACILITY_ORGANIZATION_TYPE_CODE_PART_DT = "ORDEROFLABTEST";

    public static final String OBSERVATION_SUBMIT_LAB_GEN_ORGANIZATION_TYPE_CODE_PART_DT = "SUBLABOFLABGEN";

    public static final String OBSERVATION_SUBMIT_LAB_MICRO_ORGANIZATION_TYPE_CODE_PART_DT = "SUBLABOFLABMICRO";

    public static final String OBSERVATION_SPECIMEN_MATERIAL_TYPE_CODE_PART_DT = "SPECMNOFLABTEST";

    public static final String OBSERVATION_LAB_TECHNICIAN_TEST_TYPE_CODE_PART_DT = "LABTECHOFTEST";

    public static final String OBSERVATION_LAB_TECHNICIAN_ISOLATE_TYPE_CODE_PART_DT = "LABTECHOFISO";

    public static final String OBSERVATION_LAB_TECHNICIAN_DRUG_SENSITIVITY_TYPE_CODE_PART_DT = "LABTECHOFABSENS";

    public static final String MATERIAL_SPECIMEN_INTERVENTION_TYPE_CODE_PART_DT = "SPECMNOFCOLLCTNINT";

    public static final String INTERVENTION_SPECIMEN_COLLECTOR_TYPE_CODE_PART_DT = "COLLCTROFSPECMN";

    public static final String INTERVENTION_SPECIMEN_SUBJECT_TYPE_CODE_PART_DT = "SUBJOFSPECMNCOLLCTN";

    public static final String NOINFORMATIONGIVEN = "No Information Given";

    public static final String NOINFORMATIONGIVEN_CODE = "NI";

    public static final String UNDEFINED = "UNDEFINED";

    /*ActTelationship type codes*/
    public static final String OBSERVATION_CHILD_COMPONENT_TYPE_CODE_ACT_RELTN_DT = "OBS_COMP";

    /* Constants added on 12/19/2001 for Investigation Status and types of Observations*/
    public static final String OPEN_INVESTIGATION = "OPEN";

    public static final String CLINICAL_OBSERVATION = "CLINICAL";

    public static final String LABORATORY_OBSERVATION = "LABORATORY";

    public static final String EPIDEMIOLIGIC_OBSERVATION = "EPIDEMIOLIGIC";

    public static final String VACCINE_OBSERVATION = "VACCINE";

    public static final String MEDICALHISTORY_OBSERVATION = "MEDICALHISTORY";

    /* Constants related to Investigation mainly used in InvestigationAction class */
    public static final String ROLE_INVESTIGATOR = "PHCINV";

	public static final String PHC_FIELD_FOLLO_UP_SUPVSR="FldFupSupervisorOfPHC";

	public static final String PHC_CASE_SUPRVSR="CASupervisorOfPHC";

    /* Constants related to Investigation mainly used in InvestigationAction class */
    public static final String PHC_INVESTIGATOR = "InvestgrOfPHC";

    public static final String STD_INITIAL_FOLLOWUP_INVESTIGATOR = "InitFupInvestgrOfPHC";

    public static final String STD_FIELD_FOLLOWUP_INVESTIGATOR = "InitFldFupInvestgrOfPHC";
    
    public static final String FIELD_FOLLOWUP_INVESTIGATOR = "FldFupInvestgrOfPHC";

    public static final String ABC_PHC_INVESTIGATOR = "ABCInvestgrOfPHC";

    public static final String PHC_REPORTER = "PerAsReporterOfPHC";

    public static final String PHC_PHYSICIAN = "PhysicianOfPHC";
    
    public static final String PHC_FACILITY = "OrgAsClinicOfPHC";

    public static final String PHC_REPORTING_SOURCE = "OrgAsReporterOfPHC";

    public static final String INERVIEWER_OF_PHC= "InterviewerOfPHC";
    public static final String INIT_INERVIEWER_OF_PHC="InitInterviewerOfPHC";
    public static final String LEGAL_NAME = "L";

    public static final String ALIAS_NAME = "AL";

    public static final String LEGAL_NAME_TXT = "Legal";

    public static final String HOSPITAL_NAME_ADMITTED = "HospOfADT";

    public static final String INVESTIGATION_FORM = "PHC_INV_FORM";

    public static final String PHC_INV_FORM = "PHCInvForm";

    public static final String PARENT_OBSERVATION = "Inv_Form_Meas";

    public static final String INV_FRM_Q = "InvFrmQ";

    public static final String ITEM_TO_ROW = "ItemToRow";

    public static final String PATIENT_HOSPITALIZED = "CORE001";

    public static final String DAY_CARE_FACILITY = "CORE190";

    public static final String FOOD_HANDLER = "CORE191";

    public static final String IMPORTED_COUNTRY = "CORE201";

    public static final String DISEASE_IMPORTED = "CORE200";

    public static final String YES = "Y";

    public static final String NO = "N";

    // public static final String UNKNOWN = "U";
    public static final String YNU = "YNU";

    public static final String PHC_PATIENT = "SubjOfPHC";

    public static final String CONTACT_ENTITY = "SubjOfContact";

    public static final String OTHER = "OTH";

    public static final String MEASLES = "10140";

    public static final String PERTUSSIS = "10190";

    public static final String RUBELLA = "10200";

    public static final String CRS = "10370";

    public static final String INV_EDIT = "INV_EDIT";

    public static final String supplemental = "supplemental";

    /**
     * Constants added for the InvestigationProxy for the values in case they are null and database
     * expect not null
     */
    public static final String PLACE__STATUS_CD = STATUS_ACTIVE;

    public static final Timestamp PLACE_TIME = new Timestamp(new Date()
            .getTime());

    public static final String CLINICAL_DOCUMENT_STATUS_CD = STATUS_ACTIVE;

    public static final Timestamp CLINICAL_DOCUMENT_TIME = new Timestamp(
            new Date().getTime());

    public static final String ENTITY_GROUP_STATUS_CD = STATUS_ACTIVE;

    public static final Timestamp ENTITY_GROUP_TIME = new Timestamp(new Date()
            .getTime());

    public static final String INTERVENTION_STATUS_CD = STATUS_ACTIVE;

    public static final Timestamp INTERVENTION_TIME = new Timestamp(new Date()
            .getTime());

    public static final String PATIENTENCOUNTER_STATUS_CD = STATUS_ACTIVE;

    public static final Timestamp PATIENTENCOUNTER_TIME = new Timestamp(
            new Date().getTime());

    public static final String REFERRAL_STATUS_CD = STATUS_ACTIVE;

    public static final Timestamp REFERRAL_TIME = new Timestamp(new Date()
            .getTime());

    public static final String ACTRELATIONSHIP_STATUS_CD = STATUS_ACTIVE;

    public static final Timestamp ACTRELATIONSHIP_TIME = new Timestamp(
            new Date().getTime());

    public static final String PARTICIPATION_STATUS_CD = STATUS_ACTIVE;

    public static final Timestamp PARTICIPATION_TIME = new Timestamp(new Date()
            .getTime());

    public static final String ROLE_STATUS_CD = STATUS_ACTIVE;

    public static final Timestamp ROLE_TIME = new Timestamp(new Date()
            .getTime());

    public static final int SUMMARY_TAB = 1;

    public static final int DEMOGRAPHICS_TAB = 2;

    public static final int INVESTIGATION_TAB = 3;

    public static final int VACCINE_RECORDS_TAB = 4;

    public static final int OBSERVATION_TAB = 5;

    /*
     * Observation related constants
     */
    public static final String GEN_OBS_SUBJECT = "SubjOfGenObs";

    /*
     * Class type constants for SRT Max Length of cached values
     */
    public static final int SRT_MAX_LENGTH = 5000;

    /*
     * Class type constants
     */
    public static final String CLASSTYPE_ENTITY = "Entity";

    public static final String CLASSTYPE_ACT = "Act";

    public static final String SYSTEM_REFERENCE_TABLE = PropertyUtil.getInstance().getSystemReferenceTable();

    public static final String SYSTEM_MESSAGEOUT_DB = "nbs_msgoute";

    /*
     * constants for TaskList
     */
    
    public static final String OPEN_INVESTIGATIONS = "Open Investigations";

    public static final String NND_NOTIFICATIONS_FOR_APPROVAL = "Approval Queue for Initial Notifications";

    public static final String NND_UPDATED_NOTIFICATIONS_FOR_APPROVAL = "Updated Notifications Queue";

    public static final String MY_PROGRAM_AREAS_INVESTIGATIONS = "My Program Area's Investigations";

    public static final String ELRS_NEEDING_PROGRAM_OR_JURISDICTION_ASSIGNMENT = "ELR Program Area or Jurisdiction Assignment";

    public static final String NEW_LAB_REPORTS_FOR_REVIEW = "New Lab Reports For Review";

    public static final String INVESTIGATIONS_JURISDIC_ASSIGNMENT = "Investigations Needing Jurisdiction Assignment";

    public static final String OBSERVATIONS_ASSIGNMENT = "Observations Needing Program or Jurisdiction Assignment";

    public static final String OBSERVATIONS_FOR_REVIEW = "Observations for Review";

    public static final String ADDED_RECORDS_FOR_REVIEW = "Added Records for Review";

    public static final String EDITED_RECORDS_FOR_REVIEW = "Edited Records for Review";

    public static final String DELETED_RECORDS_FOR_REVIEW = "Deleted Records for Review";

    public static final String ASSOCIATE_INVESTIGATIONS = "Associate Investigations to an Observation";

    public static final String ASSOCIATE = "Associate";

    /*
     * constants for BMIRD Validation
     */
    public static final String BMIRD_ABC_STATE_CASE = "State Assigned ABC Case ID";

    public static final String BMIRD_DATE_POSITIVE_CULTURE = "Date of First Positive Culture";

    public static final String BMIRD_BACTERIAL_SPECIES_ISOLATED = "Bacterial species isolated from normally site";

    public static final String BMIRD_ABC_CASE = "NEDSS100";

    public static final String BMIRD_ABC_STATE_CASE_ID = "NEDSS110";

    public static final String BMIRD_DATE_OF = "NEDSS120";

    public static final String BMIRD_BACTER = "NEDSS130";

    /*
     * constants for mask delimiters
     */
    public static final String PROGRAM_AREA_DELIMITER = "!";

    /*  DELIMITERS  */
    public static final String SRT_PART = "$";

    public static final String SRT_LINE = "|";

    public static final String BATCH_PART = "~";

    public static final String BATCH_SECT = "^";

    public static final String BATCH_SEP = SRT_PART;

    public static final String BATCH_LINE = "|";

    public static final String BATCH_HELP = "--";

    public static final String BATCH_STAR = "**";

    public static final String BATCH_START_CHILD = "[";

    public static final String BATCH_END_CHILD = "]";

    public static final String STR_TOKEN = "\'";

    public static final String STR_COMMA = ",";

    public static final String STR_000 = " 000,";

    public static final String STR_PARS = BATCH_SECT;

    /*
     *Constants for vaccine
     */
    public static final String SUBJECT_OF_VACCINE = "SubOfVacc";

    public static final String VACCINE_GIVEN = "VaccGiven";

    public static final String PERFORMER_OF_VACCINE = "PerformerOfVacc";

    public static final String PERFORMER_OF_VACCINEMER_OF_VACCINE = "PerformerOfVacc";

    public static final String MANUFACTURER_OF_VACCINE = "MfgrOfVaccine";

    public static final String MANUFACTURERD_VACCINE = "MfgdVaccine";

    public static final String VACCINE_ACT_TYPE_CD = "VAC105";

    public static final String VAC106 = "VAC106";

    public static final String VACADM_CD = "VACADM";
    
    //  public static final String VACCINE_ACT_DESC_TXT = "Vaccination Age";
    public static final String SORT_VACCINE = "sortVaccinations";

    /** Constants for treatment */
    public static final String SUBJECT_OF_TREATMENT = "SubjOfTrmt";

    public static final String PROVIDER_OF_TREATMENT = "ProviderOfTrmt";

    public static final String RPT_FACILITY_OF_TREATMENT = "ReporterOfTrmt";

    public static final String TREATMENT_ACT_TYPE_CD = "TRMT";

    public static final String TRT_TO_PHC = "TreatmentToPHC";

    public static final String TRT_TO_MORB = "TreatmentToMorb";

    /** Constants for ctrlCdDisplayForm */
    public static final String GENERIC_OBSERVATION_CD_DISPLAY = "GNRC";

    public static final String LAB_GENERAL_OBSERVATION_CD_DISPLAY = "GNRL";

    /** Act relationship type codes */
    public static final String GENERIC_OBSERVATION_TYPE = "GenericObs";

    public static final String LAB_GENERAL_OBSERVATION_TYPE = "LabGenObs";

    public static final String LAB_REPORT = "LabReport";
    public static final String TREATMENT = "TRMT";
    

    // State Transition
    public static final String NOT_APR = "NOT_APR";

    public static final String NOT_CR = "NOT_CR";

    public static final String NOT_CR_APR = "NOT_CR_APR";

    public static final String NOT_CR_PEND_APR = "NOT_CR_PEND_APR";

    public static final String NOT_REJ = "NOT_REJ";

    public static final String NOT_EDIT = "NOT_EDIT";

    public static final String NOT_DEL = "NOT_DEL";

    public static final String NOT_DEL_NOTF = "NOT_DEL_NOTF";

    public static final String BASE = "BASE";
    
    public static final String NOT_HIV = "NOT_HIV";
    public static final String NOT_HIV_EDIT = "NOT_HIV_EDIT";

    // business trigger codes
    //ACTS
    public static final String OBS_GEN_DEL = "OBS_GEN_DEL";

    public static final String OBS_LAB_DEL = "OBS_LAB_DEL";

    public static final String NOT_MSG_COM = "NOT_MSG_COM";

    public static final String NOT_MSG_FAIL = "NOT_MSG_FAIL";

    public static final String OBS_LAB_CR = "OBS_LAB_CR";
    public static final String OBS_LAB_CR_MR = "OBS_LAB_CR_MR";
    public static final String OBS_MORB_CR_MR = "OBS_MORB_CR_MR";
    public static final String OBS_LAB_EDIT = "OBS_LAB_EDIT";

    public static final String OBS_LAB_CORRECT = "OBS_LAB_CORRECT";

    public static final String OBS_MORB_CREATE = "OBS_MORB_CR";

    public static final String OBS_MORB_EDIT = "OBS_MORB_EDIT";

    public static final String OBS_MORB_DEL = "OBS_MORB_DEL";

    public static final String OBS_LAB_PROCESS = "OBS_LAB_PROCESS";

    public static final String OBS_MORB_PROCESS = "OBS_MORB_PROCESS";

    public static final String OBS_LAB_UNPROCESS = "OBS_LAB_UNPROCESS";

    public static final String OBS_MORB_UNPROCESS = "OBS_MORB_UNPROCESS";

    public static final String CREATE_NND_PAYLOAD = "CREATE_NND_PAYLOAD";

    //ENTITIES
    public static final String PER_EDIT = "PAT_EDIT";

    public static final String PER_CR = "PAT_CR";

    public static final String PAT = "PAT";

    public static final String NOK = "NOK";

    public static final String PAT_CR = "PAT_CR";

    public static final String PAT_DEL = "PAT_DEL";

    public static final String PAT_EDIT = "PAT_EDIT";

    public static final String PROV = "PROV";

    public static final String PRV = "PRV";

    public static final String PRV_CR = "PRV_CR";

    public static final String PRV_EDIT = "PRV_EDIT";

    public static final String PER_MERGE = "PAT_MERGE";

    public static final String ORG_EDIT = "ORG_EDIT";

    public static final String ORG_CR = "ORG_CR";

    public static final String ORG_DEL = "ORG_DEL";

    public static final String ORG_INA = "ORG_INA";

    public static final String PLC_EDIT = "PLC_EDIT";

    public static final String PLC_CR = "PLC_CR";

    public static final String PLC_INA = "PLC_INA";

    public static final String MAT_SPC_EDIT = "MAT_SPC_EDIT";

    public static final String MAT_SPC_CR = "MAT_SPC_CR";

    public static final String CD_DESC_TXT = "Observation participant";

    public static final String NBS_LAB_REW_PROC_DEC="NBS_LAB_REW_PROC_DEC";

    public static final String CM_PROCESS_STAGE="CM_PROCESS_STAGE";

    // Table Name constants used by SecurityObj getDataAccessWhereClause
    public static final String OBSERVATION_LAB_REPORT_TABLE = "obs";

    public static final String LAB_TEST_TABLE = "NBS_LAB_TEST";

    public static final String SUSP_TEST_TABLE = "NBS_SUSP_TEST";

    /*
     * constants for Security Administration
     */
    public static final String ALL = "All";

    //Business trigger codes
    public static final String ASSOC_OBS_TRIGGER_CD = "OBS_LAB_ASC";

    //public static final String W_ASSOC_OBS_TRIGGER_CD = "OBS_LAB_DIS_ASC";
    //public static final String WO_ASSOC_OBS_TRIGGER_CD = "OBS_LAB_DIS_ASC_LAST";
    //BUSINESS_TRIGGER_CD
    public static final String INT_VAC_CR = "INT_VAC_CR";

    public static final String INT_VAC_EDIT = "INT_VAC_EDIT";

    public static final String MAT_MFG_CR = "MAT_MFG_CR";

    public static final String MAT_MFG_EDIT = "MAT_MFG_EDIT";

    public static final String ACTIVE = "ACTIVE";

    public static final String INVESTIGATION_STATUS_CD = "O";

    public static final String OBSERVATION_RECORD_STATUS_CD = "UNPROCESSED";

    //Treatment
    public static final String TRT_CR = "TRT_CR";

    public static final String TRT_EDIT = "TRT_EDIT";

    public static final String TRT_DEL = "TRT_DEL";

    /*
     * nedss_version_number
     */
    public static final String NEDSS_VERSION_NUMBER = "NEDSS-2";

    /*
     * ldap props
     */
    public static final String LDAP_ID = PropertyUtil.getInstance()
            .getLDAPMangerDN();

    public static final String LDAP_PWD = PropertyUtil.getInstance()
            .getLDAPManagerPassword();

    public static final int LDAP_AttributeNumber = 16;

    public static final String SECURITY_LDAP = "LDAP";

    public static final String SECURITY_DB = "DB";

    //public static final String LDAP_PWD = "4u2use123";//This is for atldev_3 ldap server

    /*
     * ldap props
     */
    public static final String ORG_NM_USE = "L";

    public static final String ORG_ADDRESS_USE = "PB";

    public static final String ORG_ADDRESS_TYPE = "M";

    public static final String CLASS_CD_OBS = "OBS";

    public static final String CLASS_CD_PSN = "PSN";

    public static final String CLASS_CD_ORG = "ORG";
    
    public static final String CLASS_CD_PAT = "PAT";

    public static final String CLASS_CD_CASE = "CASE";

    public static final String CLASS_CD_CONTACT = "CT";

    public static final String CLASS_CD_INTERVIEW = "IXS";

    public static final String CLASS_CD_NOTF = "NOTF";
    public static final String CLASS_CD_EXP_NOTF = "EXP_NOTF";
    public static final String CLASS_CD_EXP_NOTF_PHDC = "EXP_NOTF_PHDC";
    public static final String CLASS_CD_SHARE_NOTF = "SHARE_NOTF";
    public static final String CLASS_CD_SHARE_NOTF_PHDC = "SHARE_NOTF_PHDC";
    public static final String CLASS_CD_PSF_NOTF = "PSF_NOTF";

    public static final String CLASS_CD_INTV = "INTV";

    public static final String CLASS_CD_TRT = "TRMT";

    public static final String ANTIBIOTIC_CONTAIN_ROW = "AntibioticContainRow";

    public static final String SOURCE_CONTAIN_ROW = "SourceContainRow";

    /* Add constant for Summary Report */
    public static final String SUMMARY_REPORT_ADD = "ADD";

    public static final String TOTAL_COUNT = "Total Count";

    public static final String SUMMARY_REPORT_VIEW = "VIEW";

    public static final String INV_SUMMARY_EDIT = "INV_SUMMARY_EDIT";
    public static final String    INV_SUMMARY_DEL ="INV_SUMMARY_DEL";

    public static final String TABLE_NAME = "PUBLIC_HEALTH_CASE";

    public static final String MODULE_CD = "BA";

    public static final String INV_SUMMARY_CR = "INV_SUMMARY_CR";

    public static final String SUMMARY_FORM = "SummaryForm";

    public static final String SUMMARY_CLASS_CD = "CASE";

    public static final String SUMMARY_FORM_Q = "SummaryFrmQ";

    public static final String SummaryRowItem = "SummaryRowItem";

    public static final String SUMMARY_OBS = "OBS";

    public static final String SUMMARY_NOTIFICATION = "SummaryNotification";

    public static final String CLASS_CD_NOTIFICATION = "NOTF";

    public static final String CREATE_SUMMARY_NOTIFICATION = "CREATESUMMARYNOTIFICATION";

    public static final String CREATE_NEEDS_APPROVAL = "CREATENEEDSAPPROVAL";

    public static final String NOTIFICATION_CD = "NSUM";

    public static final String AGGREGATE_NOTIFICATION_CD = "NAGG";

    public static final String INDIVIDUAL = "INDIVIDUAL";

    public static final String SUMMARY = "SUMMARY";

    public static final String ELR_LOAD_USER_ACCOUNT = "nedss_elr_load";
    public static final int NUMBER_OF_ELR_PER_BATCH = 100;

    public static final String CASETYPECD = "S";
    public static final String CASETYPECD_AGGREGATE_SUMMARY = "A";

    public static final String SUMMARY_REPORT_FORM = "Summary_Report_Form";

    public static final String NBS = "NBS";
    
    public static final String PSF = "Partner Services";
    
    public static final String NEDSS_BASE_SYSTEM = "NEDSS Base System";

    public static final String VERSION = "1.0";

    public static final String GROUPLEVELCD = "L1";

    public static final String SUM107 = "SUM107";

    public static final double COUNT = 0;

    public static final String SumCount = "SumCount";

    public static final String HospOfCulture = "HospOfCulture";

    public static final String TransferHosp = "TransferHosp";

    public static final String TreatmentHosp = "TreatmentHosp";

    public static final String DaycareFac = "DaycareFac";

    public static final String ChronicCareFac = "ChronicCareFac";

    public static final String HospOfBirth = "HospOfBirth";

    public static final String ReAdmHosp = "ReAdmHosp";

    public static final String HospOfADT = "HospOfADT";

    public static final String MaternalPSN = "MaternalPSN";

    public static final String CollegeUniversity = "CollegeUniversity";

    public static final int SECURITY_LOG_EVENT_TYPE_LOGIN_SUCCESS = 1;

    public static final int SECURITY_LOG_EVENT_TYPE_LOGIN_FAILED = 2;

    public static final int SECURITY_LOG_EVENT_TYPE_LOGOUT = 3;

    public static final String SECURITY_LOG = "SECURITY_LOG";

    public static final String SECURITY_LOG_EVENT_TYPE_LOGIN_SUCCESS_STRING = "LOGIN_SUCCESS";

    public static final String SECURITY_LOG_EVENT_TYPE_LOGIN_FAILED_STRING = "LOGIN_FAILED";

    public static final String SECURITY_LOG_EVENT_TYPE_LOGOUT_STRING = "LOGOUT";

    public static final String REMOTE_ADDRESS = "RemoteAddress";

    public static final String REMOTE_HOST = "RemoteHost";

    public static final String NBS_APP_EXCEPTION_ROLLBACK = "::[Rollback]::";

    public static final String NBS_APP_EXCEPTION_NO_ROLLBACK = "::[No Rollback]::";

    public static final String UPDATE = "UPDATE";

    public static final String SELECT = "SELECT";

    public static final String SELECTONE = "SELECTONE";

    public static final String ENTITY_TYPECD_QEC = "QEC";

    public static final String ENTITY_TYPE_DESC_TXT_QEC = "Quick Entry Code";

    public static final String SELECT_COUNT = "COUNT";

    public static final String ELECTRONIC_IND = "N";

    public static final String ELECTRONIC_IND_ELR = "Y";
    // Added for Electronic indicator flag  reading from NEDSS.Preperties file-Sathya
    public static final String ELECTRONIC_IND_ENTITY_MATCH = "Y";

    public static final String businessObjLookupNamePROVIDER = "PROVIDER";

    public static final String businessObjLookupNamePLACE = "PLACE";

    public static final String DSPatientPersonUID = "DSPatientPersonUID";

    public static final String EDX_IND = "Y";

    public static final String EHARS_ID="EHARS ID";

    public static final String ELECTRONIC_IND_VACCINATION = "Y";
    
    /**
     * Originally from EntityCodes.java
     * Description: CMD Entity Codes
     */

    /**
     * Material Entity Code
     */
    public static String MATERIAL = "MAT";

    /**
     * Organization Entity Code
     */
    public static String ORGANIZATION = "ORG";

    /**
     * Person Entity Code
     */
    public static String PERSON = "PSN";

    /**
     * Provider Entity Code
     */
    public static String PROVIDER = "PROV";

    /**
     * Place Entity Code
     */
    public static String PLACE = "PLC";

    public static String NOTIFICATION = "NOT";//This has been defined in WumConstants.java

    /**
     * EntityGroup code
     */
    public static String ENTITYGROUP = "GRP";

    /**
     * Non_Person_Living_Subject entity code
     */
    public static String NONPERSONLIVINGSUBJECT = "NLIV";

    /**
     * DATASOURCECOLUMN code
     */
    public static String DATASOURCECOLUMN = "DSC";

    /**
     * DATASOURCE code
     */
    public static String DATASOURCE = "DSO";

    /**
     * DISPLAYCOLUMN code
     */
    public static String DISPLAYCOLUMN = "DIC";

    /**
     * SORTCOLUMN code
     */
    public static String SORTCOLUMN = "SOC";

    /**
     * FILTERCODE code
     */
    public static String FILTERCODE = "FIC";

    /**
     * FILTERVALUE code
     */
    public static String FILTERVALUE = "RFV";

    /* Business object type codes  for LDF*/
    public static String INVESTIGATION_LDF = "PHC";

    public static String INVESTIGATION_HEP_LDF = "HEP";

    public static String INVESTIGATION_BMD_LDF = "BMD";

    public static String INVESTIGATION_NIP_LDF = "NIP";

    public static String ORGANIZATION_LDF = "ORG";

    public static String PROVIDER_LDF = "PRV";

    public static String LABREPORT_LDF = "LAB";

    public static String MORBREPORT_LDF = "MORB";

    public static String PATIENT_LDF = "PAT";

    public static String PATIENT_EXTENDED_LDF = "PAT-EXT";

    public static String VACCINATION_LDF = "INT_VAC";

    public static String TREATMENT_LDF = "TRT";

    public static String HOME_PAGE_LDF = "HOME";

    public static String ACTIVE_TRUE_LDF = "Y";

    public static String ACTIVE_FALSE_LDF = "N";

    public static String BLANK_XSPTAG_LDF = "<group></group>";

    public static String BLANK_SPECIALCHARACTER_LDF = "$$$$";

    /**
     * REPORT code
     */
    public static String SPECIAL_CHARACTER_OPENING = "<![CDATA[";

    public static String SPECIAL_CHARACTER_CLOSING = "]]>";

    //    public static String REPORT = "REP";

    /**
     * REPORTFILTER code
     */
    public static String REPORTFILTER = "RPF";
    public static String REPORTFILTER_VALIDATION = "RPFV";

    /**
     * Originally from CdmConstantUtil.java
     * Description: Constants for CDM module
     */

    public static final String NEDSS_FRONT_CONTROLLER_SESSION_BIND = "NFC";

    //Constants use by locator DAOs
    public static final String LOCATOR_UID = "LOCATOR_UID";

    public static final String POSTAL = "PST";

    public static final String TELE = "TELE";

    public static final String PHYSICAL = "PHYS";

    public static final String CURRENTADDRESS = "C";

    public static final String HOME = "H";

    public static final String BIRTH = "BIR";

    public static final String DEATH = "DTH";

    public static final String BIRTHCD = "F";

    public static final String WORK_PHONE = "WP";

    public static final String WORK_PLACE = "WP";

    public static final String PHONE = "PH";

    public static final String MOBILE = "MC";

    public static final String CELL = "CP";

    public static final String NET = "NET";
    
    public static final String BP = "BP";

    public static final String FAX = "FX";

    public static final String LEGAL = "L";

    public static final String CURRENT = "C";

    public static final String PATIENT = "PERSON";

    public static final String INTERNET = "Internet";

    public static final String OFFICE_CD = "O";

    public static final String PRIMARY_BUSINESS = "PB";

    //added by venu
    //Constants from PersonListServlet.java file
    public static final String GET_PERSON_ID = "getPersonId";

    public static final String CSORT_METHOD = "csortmethod";

    public static final String FIRST_NAME = "firstName";

    //   public static final String PERSON = "Person";
    public static final String APIBEAN = "apibean";

    public static final String PERSON_LIST = "PersonList";

    public static final String ORG_PRIV = "org_access_priv";

    public static final String PROG_AREA_PRIV = "prog_area_access_priv";

    //public static final String ORACLE_ID = "ORACLE";
    public static final String SQL_SERVER_ID = "SQL SERVER";

    //Constants from NedssAuthenticateServlet.java file
    public static final String UNAME = "uname";

    public static final String USERNAME = "Username";

    public static final String PWORD = "Password";

    public static final String ROLE2_ID = "ROLE2";

    public static final String ROLE1_ID = "ROLE1";

    public static final String ROLE4_ID = "ROLE4";

    public static final String ROLENAME = "rolename";

    public static final String ROLE = "role";

    //Constants from person.xsp.xsl and PersonRecordSearch.xsp.xsl files

    public final static String RESULT_SET = "ResultSet";

    public final static String GET_PERSON_SESSION = "GetPersonSession";

    public final static String PERSON_SEARCHER = "EJBPersonSearcher";

    // Morbidity
    public static final String DISPLAY_FORM = "MorbReport";

    public static final String LAB_DISPALY_FORM = "LabReport";

    public static final String LAB_REPORT_MORB = "LabReportMorb";

    public static final String CONTACT_AND_CASE = "CONTACT_AND_CASE";
    public static final String CASE = "CASE";
    public static final String PRINT_CDC_CASE = "PRINT_CDC_CASE";
    
    public static final String CASE_LITE = "CASE_LITE";
    
    public static final String PRINT = "print";

    public static final String OBS = "OBS";

    // public static final String ACTIVE="ACTIVE"; - a duplicate constant found in this class
    public static final String A = "A";

    public static final String OBSERVATION = "OBSERVATION";

    public static final String OBS_ADD_REASON_CD = "ADD LAB REPORT";
    
    public static final String MORB_ADD_REASON_CD = "ADD MORB REPORT";

    // public static final String BASE="BASE"; - a duplicate constant found in this class
    public static final String OBSERVATIONLABREPORT = "OBSERVATIONLABREPORT";

    // public static final String OBS_MORB_DIS_ASC_LAST="OBS_MORB_DIS_ASC_LAST";
    public static final String OBS_LAB_ASC = "OBS_LAB_ASC";

    public static final String OBSERVATIONMORBIDITYREPORT = "OBSERVATIONMORBIDITYREPORT";

    public static final String OBS_MORB_ASC = "OBS_MORB_ASC";

    public static final String INACTIVE = "INACTIVE";

    public static final String I = "I";

    public static final String CA = "CA";

    public static final String OBS_LAB_DIS_ASC = "OBS_LAB_DIS_ASC";

    public static final String OBS_LAB_DISASSOCIATE = "OBS_LAB_DISASSOCIATE";

    //public static final String OBS_LAB_DIS_ASC_LAST ="OBS_LAB_DIS_ASC_LAST";
    public static final String OBS_MORB_DIS_ASC = "OBS_MORB_DIS_ASC";

    //public static final String OBS_MORB_DIS_ASC_LAS="OBS_MORB_DIS_ASC_LAS";
    public static final String TYPE_CD = "1180";

    //public static final String OBS="OBS";

    // Constant are set for race
    public static final String RACE_CODESET = "P_RACE_CAT";

    public static final String UNKNOWN = "U";

    public static final String AMERICAN_INDIAN_OR_ALASKAN_NATIVE = "1002-5";

    public static final String ASIAN = "2028-9";

    public static final String AFRICAN_AMERICAN = "2054-5";

    public static final String NATIVE_HAWAIIAN_OR_PACIFIC_ISLANDER = "2076-8";

    public static final String WHITE = "2106-3";

    public static final String OTHER_RACE = "2131-1";

    public static final String REFUSED_TO_ANSWER="PHC1175";

	public static final String NOT_ASKED = "NASK";

    // Constants for Merge Person
    public static final String MERGE_PERSON_1_SELECTED = "MergePerson1Selected";

    public static final String MERGE_PERSON_2_SELECTED = "MergePerson2Selected";

    public static final String ZIPFORJURISDICTION = "Z";

    public static final String CITYFORJURISDICTION = "C";

    public static final String BIRTHPLACE = "BIR";

    public static final String BIRTHDELIVERYADDRESS = "BDL";
    
    public static final String MERGE_CANDIDATE_DEFAULT_SURVIVOR_OLDEST = "OLDEST";
    public static final String MERGE_CANDIDATE_DEFAULT_SURVIVOR_NEWEST = "NEWEST";
    public static final String SAS_VERSION = "SAS_VERSION";
    public static final String SAS_VERSION9_3 = "9.3";
    public static final String SAS_VERSION9_4 ="9.4";
    

    /**
     * Originally from WumConstants.java
     * Description: Constants for WUM module
     */

    /**
     * Activity class codes, classCd
     */
    public static final String OBSERVATION_CLASS_CODE = "OBS";

    public static final String WORKUP_CLASS_CODE = "WKUP";

    public static final String PUBLIC_HEALTH_CASE_CLASS_CODE = "CASE";

    public static final String NOTIFICATION_CLASS_CODE = "NOTF";

    public static final String INTERVENTION_CLASS_CODE = "INTV";

    public static final String REFERRAL_CLASS_CODE = "REFR";

    public static final String PATIENT_ENCOUNTER_CLASS_CODE = "ENC";

    public static final String CLINICAL_DOCUMENT_CLASS_CODE = "DOCCLIN";

    public final static String MATERIAL_CLASS_CODE = "MAT";

    public final static String PERSON_CLASS_CODE = "PSN";

    public final static String ORGANIZATION_CLASS_CODE = "ORG";

    public final static String TREATMENT_CLASS_CODE = "TRMT";

    public static final String PLACE_CLASS_CODE = "PLC";

    /**
     * Activity mood code, moodCd
     */
    public static final String EVENT_MOOD_CODE = "EVN";//Represents most what NEDSS captures

    public static final String DEFINITION_MOOD_CODE = "DEF";

    public static final String INTENT_MOOD_CODE = "INT";

    public static final String ORDER_MOOD_CODE = "ORD";

    public static final String APPOINTMENT_MOOD_CODE = "APT";

    public static final String APPOINTMENT_REQUEST_MOOD_CODE = "ARQ";

    public static final String PROPOSED_MOOD_CODE = "PRP";

    public static final String RECOMMENDED_MOOD_CODE = "RMD";

    /**
     * Notification approved codes
     */
    public static final String NOTIFICATION_REJECTED_CODE = "REJECTED";//rejected

    public static final String NOTIFICATION_APPROVED_CODE = "APPROVED";//approved code

    public static final String NOTIFICATION_PENDING_CODE = "PEND_APPR";//pending

    public static final String NOTIFICATION_COMPLETED = "COMPLETED";//completed

    public static final String NOTIFICATION_MESSAGE_FAILED = "MSG_FAIL";//message failed

    public static final String NOTIFICATION_PEND_DEL = "PEND_DEL";

    public static final String NOTIFICATION_PEND_DEL_IN_BATCH_PROCESS = "PEND_DEL_IN_BATCH_PR";

    public static final String NOTIFICATION_MSG_FAIL_PEND_DEL = "MSG_FAIL_PEND_DEL";

    public static final String NOTIFICATION_IN_BATCH_PROCESS = "IN_BATCH_PROCESS";

    /**
     * EDX_activity_log
     */
    public static final String EDX_ACTIVITY_SUCCESS = "SUCCESS";
    public static final String EDX_ACTIVITY_FAIL = "Failure";
    public static final String EDX_NOTIFICATION_SOURCE_CODE = "NOT";
    public static final String EDX_IMPORT_CODE = "I";
    public static final String EDX_EXPORT_CODE = "E";
    public static final String EDX_PHDC_DOC_TYPE = "PHDC";
    public static final String EDX_PHCR_DOC_TYPE = "PHCR";
    public static final String EDX_INTERFACE_TABLE_CODE = "INT";
    public static final String PAYLOAD_VIEW_IND_CD_PHDC = "P";
    public static final String EDX_TYPE_ENTITY = "Y";


    /**
     * Stuff for getVaccinationProxy
     */
    public static final String SRT_VACCINE_SUBJECT = "SubOfVacc";

    public static final String SRT_VACCINE_SUBJECT_ADMIN = "PerformerOfVacc";

    public static final String SRT_VACCINE_ORGANIZATION_ADMIN = "PerformerOfVacc";

    public static final String SRT_VACCINE_MATERIAL_ADMINISTERED = "VaccGiven";

    public static final String SRT_VACCINE_ORGANIZATION_MANUFACTURER = "MfgdVaccine";

    public static final String SRT_VACCINE_OBSERVATION_CORE220 = "CORE220";

    public static final String SRT_VACCINE_OBSERVATION_CORE231 = "CORE231";

    public static final String SRT_VACCINE_OBSERVATION_VAC105 = "VAC105";

    /**
     * Request Attribute for observations in View Workup
     */
    public static final String OBSERVATION_LIST = "observationList";

    /**
     * Stuff for deleteObservationProxy
     */
    public static final String GENERIC_OBS = "GenericObs";

    public static final String LAB_RESULT_OBS = "LabGenObs";

    public static final String LAB_MICRO_OBS = "LabMicroObs";

    //  public static final String STATUS_ACTIVE  = "ACTIVE";

    /**
     * AR type code for Manage Vaccination
     */
    public static final String AR_TYPE_CODE = "1180";

    /**
     * Originally from WumConstantUtil.java
     * Description: Constants for WUM module
     */

    //Constants use by locator DAOs
    /**
     * Laboratory Results new Participation codes as of 04/13/02
     */
    public static final String PARTICIPATION_TYPE = "PAR_TYPE";

    public static final String PART_ACT_CLASS_CD = "OBS";

    public static final String ACTRELATION_CLASS_CD = "OBS";

    public static final String PAR101_SUB_CD = "PSN";

    public static final String PAR101_TYP_CD = "ORD";

    public static final String PAR102_SUB_CD = "ORG";

    public static final String PAR102_TYP_CD = "ORD";

    public static final String PAR104_SUB_CD = "MAT";

    public static final String PAR104_TYP_CD = "SPC";

    public static final String PAR110_SUB_CD = "PSN";

    public static final String PAR110_TYP_CD = "PATSBJ";

    public static final String PAR111_SUB_CD = "ORG";

    public static final String PAR111_TYP_CD = "AUT";

    public static final String PAR122_SUB_CD = "ORG";

    public static final String PAR122_TYP_CD = "PRF";
    public static final String Surveillance_Investigator_OF_PHC = "SurvInvestgrOfPHC";

    public static final String AUT = "AUT";

    public static final String ORD = "ORD";

    // public static final String STATUS_ACTIVE = "A"; - a duplicate constant found in this class
    // public static final String RECORD_STATUS_ACTIVE = "ACTIVE"; - a duplicate constant found in this class

    /**
     * Laboratory Results new ActRelationship codes as of 04/13/02
     */
    public static final String ACT106_SRC_CLASS_CD = "NOTF";

    public static final String ACT106_TAR_CLASS_CD = "CASE";

    public static final String ACT106_TYP_CD = "Notification";

    public static final String ACT108_TYP_CD = "COMP";

    public static final String ACT114_TYP_CD = "LAB180";

    public static final String ACT109_TYP_CD = "REFR";

    public static final String ACT110_TYP_CD = "COMP";

    public static final String ACT128_TYP_CD = "SummaryNotification";

    public static final String ORDERED_TEST_OBS_DOMAIN_CD = "Order";

    public static final String RESULTED_TEST_OBS_DOMAIN_CD = "Result";
    public static final String PATIENT_STATUS_OBS_DOMAIN_CD = "Order_rslt";

    public static final String C_ORDER = "C_Order";

    public static final String C_RESULT = "C_Result";

    public static final String LABCOMMENT = "LabComment";

    public static final String R_ORDER = "R_Order";

    public static final String R_RESULT = "R_Result";

    public static final String ISOLATE_OBS_DOMAIN_CD = "Isolate";

    public static final String HAS_COMPONENT = "Has Component";
    
    public static final String LAB_REPORT_DESC_TXT = "Laboratory Report";
    
    public static final String TRMT_TO_MORB_RPT_TXT = "Treatment To Morbidity Report";

    public static final String CAUS = "CAUS";

    public static final String COMP = "COMP";

    public static final String APND = "APND";

    public static final String APPENDS = "Appends";

    public static final String IS_CAUSE_FOR = "Is Cause For";

    public static final String ACT_TYPE_PROCESSING_DECISION = "TBD";

    /* note: since obs_domain_cd_st_1 column in Observation table has only length 10,
     declared SUSCEPTIBILITY_OBS_DOMAIN_CD as "Susceptibi", if length is increased
     then modify SUSCEPTIBILITY_OBS_DOMAIN_CD to "Susceptibility"
     */
    public static final String SUSCEPTIBILITY_OBS_DOMAIN_CD = "Susceptibi";

    public static final String OBS_PROCESSED = "PROCESSED";

    public static final String OBS_UNPROCESSED = "UNPROCESSED";

    public static final String PAR_RECORD_STATUS_CD = "ACTIVE";

    public static final String PAR_SUB_CLASS_CD = "PSN";

    //General constants
    public final static String OBSERVATION_CODE = "ocd";

    public final static String LABRESULT_CODE = "LabReport";

    public final static String MORBIDITY_CODE = "MorbReport";

    public final static String MORBIDITY_REPORT = "MorbReport";

    public final static String CREATE_GEN_OBS = "general";

    public final static String MORBIDITY_FORM_Q = "MorbFrmQ";
    
    public static final String ACT_ID_CITY_TYPE_CD = "CITY";

    public static final String ACT_ID_STATE_TYPE_CD = "STATE";
    
    public static final String ACT_ID_LEGACY_TYPE_CD = "LEGACY";

    public static final String ABCS = "ABCS";

    public static final String INIT_LOAD = "initLoad";

    public static final String CONTEXT_ACTION = "ContextAction";

    public static final String QUEUE_SIZE = "queueSize";

    public static final String QUEUE_COUNT = "queueCount";

    //created on 04-30-2002
    public final static String INIT_PARAMETER = "reports";

    public final static String SECURITY_OBJECT = "SecurityObject";

    public final static String NBS_SECURITY_OBJECT = "NBSSecurityObject";

    public final static String OBJECT_TYPE = "ObjectType";

    public final static String OPERATION_TYPE = "OperationType";

    public final static String OBSERVATION_UID = "observationUID";

    public final static String PERSON_FORM = "personForm";

    public final static String PERSON_UID = "personUID";

    public final static String PERSON_LOCALUID = "personLocalId";

    public final static String PERSON_DOB = "patientDateOfBirth";

    public final static String PERSON_SEX = "patientCurrentSex";

    public final static String VIEW_LABRESULT = "view";

    public final static String CREATE_LABRESULT = "create";

    public final static String EDIT_LABRESULT = "edit";

    public final static String ERROR_PAGE = "error";

    public final static String LOGIN_PAGE = "login";

    public final static String XSP_PAGE = "XSP";

    public final static String DELETE_PAGE = "viewDelete";

    public final static String DELETE_DENIED_PAGE = "deleteDenied";

    public final static String NEXT_PAGE = "next";

    public final static String POST_EDIT_VIEW = "viewLabResult";

    public final static String DS_PERSON_INFO = "DSPersonInfo";

    public final static String DS_PERSON_SUMMARY = "DSPersonSummary";

    public final static String DS_PERSON_MERGE_VO1 = "DSPersonMergeVO1";

    public final static String DS_PERSON_MERGE_VO2 = "DSPersonMergeVO2";

    public final static String DS_PERSON_MERGE_SURVIVING_VO = "DSPersonMergeSurvivingVO";

    public final static String DS_PERSON_MERGE_SUPERCEDED_VO = "DSPersonMergeSupercededVO";

    //ObservationProxy.setLabResultProxy Return constants
    public final static String SETLAB_RETURN_OBS_UID = "ObservationUid";

    public final static String SETLAB_RETURN_MPR_UID = "MPRUid";

    public final static String SETLAB_RETURN_OBS_LOCAL = "ObservationLocalId";
    public final static String SETLAB_RETURN_OBSDT = "ObservationDT";

    public final static String SETLAB_RETURN_MPR_LOCAL = "MPRLocalId";

    public final static String SETLAB_RETURN_JURISDICTION_ERRORS = "JurisdictionErrorCollection";

    public final static String SETLAB_RETURN_PROGRAM_AREA_ERRORS = "ProgramAreaErrorCollection";

    //program Area Code
    public final static String PROGRAM_AREA_CD = "S_PROGRA_C";

    public final static String ALLOWED_PROGRAM_AREAS = "AllowedProgramAreas";

    public final static String ALLOWED_JURISDICTIONS = "AllowedJurisdictions";

    public final static String ALLOWED_OBSERVATIONS = "AllowedObservations";

    public final static String LAB_TEST = "LAB_TEST";

    public final static String OBS_CTRL_FORM = "OBS_CTRL_FORM";

    //Obs ctrlCdDisplayForm
    public final static String LAB_CTRLCD_DISPLAY = "LabReport";

    public final static String MOB_CTRLCD_DISPLAY = "MorbReport";

    public final static String MOB_VACCINE_CONTAIN_ROW = "VaccinationContainRo";

    public final static String MOB_ITEM_TO_ROW = "ItemToRow";

    public final static String TREATMENT_TO_MORB_TYPE = "TreatmentToMorb";

    // morb participation constants
    public final static String MOB_SUBJECT_OF_MORB_REPORT = "SubjOfMorbReport";

    public final static String MOB_REPORTER_OF_MORB_REPORT = "ReporterOfMorbReport";

    public final static String MOB_PHYSICIAN_OF_MORB_REPORT = "PhysicianOfMorb";

    public final static String MOB_HOSP_OF_MORB_REPORT = "HospOfMorbObs";

    //LabReports Interpretation values
    public final static String QUALITATIVERESULTS = "QualitativeResults";

    public final static String INTERPRETATION = "Interpretation";

    //constants as part of ErrorLog
    public static final String NBS_ERRORLOG = PropertyUtil.propertiesDir +"NBSErrorMap.xml";

    public static final String ERROR_MESSAGES = "ErrorMessages";

    /* code added to support LDFs */
    public static final String LDF_DROP_DOWN = "Dropdown";

    public static final String LDF_VALIDATION_TYPE = "LDF_VALIDATION_TYPE";

    public static final String LDF_DATA_TYPE = "LDF_DATA_TYPE";

    public static final String LDF_SOURCE = "LDF_SOURCE";

    //NBS_Question Data Types
    public static final String NBS_QUESTION_DATATYPE_CODED_VALUE = "Coded";
    public static final String NBS_QUESTION_DATATYPE_DATE = "Date";
    public static final String NBS_QUESTION_DATATYPE_DATETIME = "Date/Time";
    public static final String NBS_QUESTION_DATATYPE_NUMERIC = "Numeric";
    public static final String NBS_QUESTION_DATATYPE_MASK_STRUCTURE_NUMERIC = "NUM_SN";
    public static final String NBS_QUESTION_DATATYPE_MASK_NUM_DD = "NUM_DD";
    public static final String NBS_QUESTION_DATATYPE_MASK_NUM_EXT = "NUM_EXT";
    public static final String NBS_QUESTION_DATATYPE_MASK_NUM_MM = "NUM_MM";
    public static final String NBS_QUESTION_DATATYPE_MASK_NUM_TEMP = "NUM_TEMP";
    public static final String NBS_QUESTION_DATATYPE_MASK_NUM_YYYY = "NUM_YYYY";
    public static final String NBS_QUESTION_DATATYPE_TEXT = "Text";
    public static final String NBS_QUESTION_DATATYPE_TEXTAREA = "TextArea";
    public static final String NBS_QUESTION_DATATYPE_SUBHEADING = "Subheading";
    public static final String NBS_QUESTION_DATATYPE_PARTICIPATION = "PART";
    public static final String NBS_QUESTION_DATATYPE_PARTICIPATION_ORGANIZATION = "PartOrg";
    public static final String NBS_QUESTION_DATATYPE_PARTICIPATION_PERSON = "PartPer";
    public static final String NBS_QUESTION_DATATYPE_PARTICIPATION_PLACE = "PartPlc";    
    public static final String NBS_QUESTION_MASK_NUMERIC = "NUM";
    public static final String NBS_QUESTION_MASK_NUMERIC_YEAR = "NUM_YYYY";

    //LDF Legacy Data Types
    public static final String LDF_DATATYPE_CODED_VALUE = "CV";
    public static final String LDF_DATATYPE_STRING = "ST";
    public static final String LDF_DATATYPE_LIST_STRING = "LIST_ST";
    public static final String LDF_DATATYPE_SUBHEADING = "SUB";

    // Flag indicating whether filed is added by state or CDC
    public static final String ADMINFLAGCDC = "CDC";

    public static final String ADMINFLAGSTATE = "STATE";

    public static final String ERROR = "ERROR";

    public static final String REPORTING_LAB_CLIA_NULL = "reportingLabCLIA is null";

    public static final String PROGRAM_AREA_ASSIGNMENT_ERROR = "Error in assigning program area";

    //constants for MPRUpdateEngine
    public static final String NEDSSVO_CONFIG = PropertyUtil.propertiesDir + "NEDSSVO_Config.xml";

    //constants for DeDuplication...
    public static final String OVERRIDE = "override";

    //constants for NND
    public static final String NND_FINAL_MESSAGE = "F";
    public static final String NND_CORRECTED_MESSAGE = "C";
    public static final String NND_DELETED_MESSAGE = "X";
    public static final String NND_UNPROCESSED_MESSAGE = "UNPROCESSED";
    public static final String NND_REQUIRED_FIELD = "R";
    public static final String NND_OPTIONAL_FIELD = "O";
    public static final String NND_PREFERRED_FIELD = "P";
    public static final String NND_LOCALLY_CODED = "L";
    public static final String MESSAGE_OUTPUT_FILE  = "FILE";
    public static final String MESSAGE_OUTPUT_TABLE  = "TABLE";
    public static final String MESSAGE_OUTPUT_BOTH  = "BOTH";

    public static final String NOTIFICATION_AUTO_RESEND_ON = "T";
    public static final String NOTIFICATION_AUTO_RESEND_OFF = "F";

    public static final String TRUE = "T";
    public static final String FALSE = "F";

    public static final String OPTIONAL = "O";
    public static final String REQUIRED = "R";


    //constants for NND HL7 Data types
    public static final String NND_HL7_DATATYPE_CWE = "CWE";
    public static final String NND_HL7_DATATYPE_CE = "CE";
    public static final String NND_HL7_DATATYPE_ST = "ST";
    public static final String NND_HL7_DATATYPE_TS = "TS";
    public static final String NND_HL7_DATATYPE_IS = "IS";
    public static final String NND_HL7_DATATYPE_ID = "ID";
    public static final String NND_HL7_DATATYPE_SN = "SN";
    public static final String NND_HL7_DATATYPE_TX = "TX";
    public static final String NND_HL7_DATATYPE_CX = "CX";
    public static final String NND_HL7_DATATYPE_HD = "HD";
    public static final String NND_HL7_DATATYPE_NM = "NM";
    public static final String NND_HL7_DATATYPE_EI = "EI";
    public static final String NND_HL7_DATATYPE_EIP = "EIP";
    public static final String NND_HL7_DATATYPE_XPN = "XPN";
    public static final String NND_HL7_DATATYPE_XTN = "XTN";
    public static final String NND_HL7_DATATYPE_DT = "DT";

    //Not an actual HL7 datatype:
    public static final String NND_HL7_DATATYPE_SN_WITH_UNIT = "SN_WITH_UNIT";

    //constants for NND HL7 Segments
    public static final String NND_HL7_SEGMENT_MSH_10 = "MSH-10.0";
    public static final String NND_HL7_SEGMENT_MSH_21 = "MSH-21.0";
    public static final String NND_HL7_SEGMENT_PID_3_1 = "PID-3.1";
    public static final String NND_HL7_SEGMENT_OBR_7 = "OBR-7.0";
    public static final String NND_HL7_SEGMENT_OBR_22 = "OBR-22.0";
    public static final String NND_HL7_SEGMENT_OBR_25 = "OBR-25.0";

    //CDA (Clinical Document Architecture) Constants
    public static final String CDA_PHDC_CODE = "55751-2";
    public static final String CDA_PHDC_TYPE = "CDA-PHDC";
    public static final String CDA_TYPE = "CDA";


    public static final String PHCR_LOCAL_REGISTRY_ID = "LR";

    //constants for Notification forms
    public static final String NOTF_CR_FORM = "CR_FORM";
    public static final String FLU_SUM_FORM = "SUM_FORM_FLU";

    //constants for Security User types
    public static final String SEC_USERTYPE_EXTERNAL = "externalUser";

    public static final String SEC_USERTYPE_INTERNAL = "internalUser";

    public static final String EXTERNAL_USER_IND = "E";
    public static final String INTERNAL_USER_IND = "N";
    

    public static final String SEC_MSA = "msa";

    public static final String SEC_PAA = "paa";

    //constant to be used as a dummy value for ReportingFacilityUID in LDAP when
    //one isn't selected in security admin.
    public static final String SEC_NO_REPORTING_FACILITY = "No Reporting Facility";

    //constants for Test types
    public static final String TEST_TYPE_LOINC = "LOINC";

    public static final String RESULT_TYPE_SNOMED= "SNOMEDRESULT";

    public static final String TEST_TYPE_LOCAL = "LOCAL";

    public static final String RESULT_TYPE_LOCAL = "LOCALRESULT";

    //Reporting lab
    public static final String REPORTING_LAB_CLIA = "CLIA";

    public static final String REPORTING_LAB_FI_TYPE = "FI";

    //treatment
    
    public static final String RESULT_TREATMENT= "TREATMENT";
    
    
    // constants for transport
    public static final String NEDSS_TRANSPORT_CONFIG_FILENAME = PropertyUtil.propertiesDir + "NedssTransportConfig.xml";

    public static final String NEDSS_JMS_QUEUE_TRANSPORT_MANAGER = "nbs-jms-queue-transport-manager";

    public static final String NEDSS_JMS_TOPIC_TRANSPORT_MANAGER = "nbs-jms-topic-transport-manager";

    public static final String NND_AUTO_RESEND_QUEUE = "queue/NNDAutoResendJMSQueue";

    public static final String PDP_NOTIFICATION_QUEUE = "PDPNotificationQueue";

    public static final String PROGRAM_AREA_PROCESS_CD = "ProcessCd";

    /* added to make notification_manage.xsp compile. If not required should be removed */
    public static final String INVESTIGATION = "Investigation";

    /*added for lab*/
    public static final String LOAD_ON_STARTUP = "YES";

    /*Constants to be used for lab caching mechanism*/
    public static final String ORDERED_TEST_DBVALUE = "O";

    public static final String RESULTED_TEST_DBVALUE = "R";

    public static final String ORDERED_TEST = "OT";

    public static final String RESULTED_TEST = "RT";

    public static final String DEFAULT = "DEFAULT";

    public static final String PROGRAM_AREA_NONE = "NONE";
    
    public static final String JURISDICTION_NONE = "NONE";

    public static final String ORDERED_TEST_LOOKUP = "OrderedTest";

    public static final String RESULTED_TEST_LOOKUP = "ResultedTest";

    public static final String SPECIMEN_SOURCE_LOOKUP = "SpecimentSourceLookup";

    public static final String SPECIMEN_SITE_LOOKUP = "SpecmentSiteLookup";

    public static final String NUMERIC_RESULT_LOOKUP = "NumericResultLookup";

    public static final String CODED_RESULT_LOOKUP = "CodedResultLookup";

    public static final String ORGANISM_LOOKUP = "OrganismLookup";

    public static final String DRUG_LOOKUP = "DrugLookup";

    public static final String TREATMENTS_LOOKUP = "TreatmentsLookup";

    public static final String TREATMENT_DRUGS_LOOKUP = "TreatmentDrugsLookup";

    public static final String NBS_ELEMENTSUID_LOOKUP = "NbsElementsUidLookup";

    public static final String UNITS_LOOKUP = "UnitsLookup";

    public static final String INVESTIGATION_REPORTING_LOOKUP = "InvReportingSourceLookup";

    public static final String HAS_HIV_PERMISSIONS = "hasHIVPermissions";

    public static final String LAB_TESTCODE_NI = "NI";

    public static final String LAB_TYPE_CLIA = "CLIA";

    public static final String LAB_TYPE_FI = "FI";

    public static final String CACHED_TESTNAME_DELIMITER = "--";

    public static final int CACHED_DROPDOWN_BEGINING_SIZE = 0;

    public static final int CACHED_DROPDOWN_END_SIZE500 = 501;

    public static final String LAB_REPORT_DESC = "Lab Report";

    public static final String MORB_REPORT_DESC = "Morbidity Report";

    public static final String KEY_CODINGSYSTEMCD = "KEY_CODING_SYSTEM_CD";

    public static final String KEY_CODESYSTEMDESCTXT = "KEY_CODE_SYSTEM_DESC_TXT";

    //Data migration status
    public static final String DM_RECORD_PASS = "Passed";

    public static final String DM_RECORD_FAIL = "Failed";

    public static final String LDF_METADATA_RECORD_STATUS_CD = "LDF_PROCESSED";

    public static final String LDF_CREATE_RECORD_STATUS_CD = "LDF_CREATE";

    public static final String LDF_UPDATE_RECORD_STATUS_CD = "LDF_UPDATE";

    public static final String SUBFORM_CREATE_RECORD_STATUS_CD = "SUBFORM_CREATE";

    public static final String SUBFORM_UPDATE_RECORD_STATUS_CD = "SUBFORM_UPDATE";

    public static final String DF_DATA_TYPE = "CustomDefinedField";

    public static final String SF_DATA_TYPE = "CustomSubform";

    //SRTFiltering 1.1.3 Mapping Flags. Set to Y or N is NEDSS.properties
    public static final String LABTEST_SITE_MAPPING = "LABTEST_SITE_MAPPING";

    public static final String LABTEST_SPECIMEN_MAPPING = "LABTEST_SPECIMEN_MAPPING";

    public static final String LABTEST_UNIT_MAPPING = "LABTEST_UNIT_MAPPING";

    public static final String LABTEST_DISPLAY_MAPPING = "LABTEST_DISPLAY_MAPPING";

    public static final String CONDITION_REPORTING_SOURCE = "CONDITION_REPORTING_SOURCE";

    public static final String LABTEST_PROGAREA_MAPPING = "LABTEST_PROGAREA_MAPPING";

    public static final String LABTEST_LABRESULT_MAPPING = "LABTEST_LABRESULT_MAPPING";

    public static final String LABTEST_RELATIONSHIP = "LABTEST_RELATIONSHIP";

    public static final String TREATMENT_CONDITION = "TREATMENT_CONDITION";

    public static final String LOINC_SITE_MAPPING = "LOINC_SITE_MAPPING";

    public static final String LOINC_SPECIMEN_MAPPING = "LOINC_SPECIMEN_MAPPING";

    public static final String LOINC_UNIT_MAPPING = "LOINC_UNIT_MAPPING";

    public static final String LOINC_DISPLAY_MAPPING = "LOINC_DISPLAY_MAPPING";

    //SRTFiltering for ReportingSource
    public static final String REPORTING_SOURCE_CREATE = "CREATE";

    public static final String REPORTING_SOURCE_EDIT = "EDIT";

    public static final String REPORTING_SOURCE_VIEW = "VIEW";

    //SRTFiltering for Treatment
    public static final String TREATMENT_SRT_CREATE = "CREATE";

    public static final String TREATMENT_SRT_EDIT = "EDIT";

    public static final String TREATMENT_SRT_VIEW = "VIEW";

    public static final String TREATMENT_SRT_DRUGS = "TREATMENT_DRUGS";

    public static final String TREATMENT_SRT_VALUES = "TREATMENTS";

    //display Elements for Lab
    
    public static final String LAB121 = "LAB121";

    public static final String LAB278 = "LAB278";

    public static final String LAB113To115_UNITS = "LAB113To115_UNITS";

    public static final String LAB113To115_VALUE = "LAB113To115_VALUE";

    public static final String LAB208 = "LAB208";

    public static final String LAB119_120 = "LAB119_120";

    public static final String LAB222 = "LAB222";

    public static final String LAB105 = "LAB105";

    public static final String LAB110 = "LAB110";

    public static final String LAB118 = "LAB118";

    public static final String LAB113 = "LAB113";

    public static final String LAB114 = "LAB114";

    public static final String LAB115 = "LAB115";

    public static final String LAB116 = "LAB116";

    public static final String LAB117 = "LAB117";

    public static final String LAB119 = "LAB119";

    public static final String LAB120 = "LAB120";

    public static final String OFFICE = "Office";
    
    public static final String HOUSE = "House";

    public static final String CODE_O = "O";

    /* event tab order */
    public static final String EVENT_TAB_PATIENT_NAME = "PATIENT";

    public static final int EVENT_TAB_PATIENT_ORDER = 1;

    public static final String EVENT_TAB_EVENT_NAME = "EVENT";

    public static final int EVENT_TAB_EVENT_ORDER = 2;


    // Constants needed by JMSManager
    public static final int JMS_BROADCAST_SUBJECT = 1;

    public static final int JMS_SEND_ONCE_SUBJECT = 2;

    public static final String QUEUE_CONN_FACT_TAG = "JMS Queue Connection Factory";

    public static final String TOPIC_CONN_FACT_TAG = "JMS Topic Connection Factory";

    public static final String PROV_URL_TAG = "JMS URL";

    public static final String INIT_CON_FACT_TAG = "JMS Initial Context Factory";
    public static final String I_ORDER = "I_Order";
    public static final String I_RESULT = "I_Result";
    public static final String LAB_329 = "LAB329";
    public static final String LAB_329A = "LAB329a";
    public static final String LAB_330 = "LAB330";
    public static final String NBS_LAB330 = "NBS_LAB330";
    public static final String NBS_LAB266 = "NBS_LAB266";
    public static final String LAB_331 = "LAB331";
    public static final String LAB_332 = "LAB332";
    public static final String LAB_333 = "LAB333";
    public static final String LAB_334 = "LAB334";
    public static final String LAB_335 = "LAB335";
    public static final String LAB_336 = "LAB336";
    public static final String LAB_337 = "LAB337";
    public static final String LAB_338 = "LAB338";
    public static final String LAB_339 = "LAB339";
    public static final String LAB_340 = "LAB340";
    public static final String LAB_341 = "LAB341";
    public static final String LAB_342 = "LAB342";
    public static final String LAB_343 = "LAB343";
    public static final String LAB_344 = "LAB344";
    public static final String LAB_345 = "LAB345";
    public static final String LAB_346 = "LAB346";
    public static final String LAB_347 = "LAB347";
    public static final String LAB_348 = "LAB348";
    public static final String LAB_349 = "LAB349";
    public static final String LAB_350 = "LAB350";
    public static final String LAB_351 = "LAB351";
    public static final String LAB_352 = "LAB352";
    public static final String LAB_353 = "LAB353";
    public static final String LAB_354 = "LAB354";
    public static final String LAB_355 = "LAB355";
    public static final String LAB_356 = "LAB356";
    public static final String LAB_357 = "LAB357";
    public static final String LAB_358 = "LAB358";
    public static final String LAB_359 = "LAB359";
    public static final String LAB_360 = "LAB360";
    public static final String LAB_361 = "LAB361";
    public static final String LAB_362 = "LAB362";
    public static final String LAB_330_CD_DESC_TXT = "Patient Status at Specimen Collection";
    public static final String LAB_363 = "LAB363";

    public static final String LAB_235 = "LAB235";

    public static final String MORB_235 = "MRB235";

    public static final String LAB329CD_DESC_TXT="Isolate Tracking";
    public static final String STATUS_CD_D="D";

    /*
     * ACT RELATIONSHIP specific constants
     */
    public static final String TYPE_CD_115 = "SPRT";
    public static final String TYPE_DESC_TXT_115= "Has Support";
    public static final String TYPE_CD_110= "COMP";
    public static final String  TYPE_DESC_TXT_110="Has Component";
    public static final String TYPE_DESC_TXT_109= "Refers to";


    public static final String CD_SYSTEM_CD_NBS                  = "2.16.840.1.114222.4.5.1";
    public static final String CODED_CD_SYSTEM_CD_HL7            = "2.16.840.1.113883.12.136";
    public static final String CODED_CD_SYSTEM_CD                = "2.16.840.1.114222.4.5.131";
    public static final String CODED_CD_SYSTEM_CD_LAB347         = "2.16.840.1.114222.4.5.190";
    public static final String CODED_CD_SYSTEM_CD_LAB352_YN      = "2.16.840.1.113833.12.136";
    public static final String CODED_CD_SYSTEM_CD_LAB352_ELSE    = "2.16.840.1.114222.5.192";
    public static final String CODED_CD_SYSTEM_CD_LAB359_LAB353  = "2.16.840.1.114222.5.131";
    public static final String CODED_CD_SYSTEM_CD_LAB355         = "2.16.840.1.114222.4.5.182";

    public static final String CODED_SYSTEM_DESC_TXT="HL7 Table 0136";
    public static final String FILLER_NUMBER_FOR_ACCESSION_NUMBER="FN";
    public static final String MESSAGE_CTRL_ID_CODE="MCID";
    public static final String HIV_SRT_CODE ="19030005";
    public static final String AIDS_SRT_CODE ="62479008";
    public static final String NO_FIRST_NAME_INVESTIGATOR ="No First";
    public static final String NO_LAST_NAME_INVESTIGATOR ="No Last";
    public static final String NO_INVESTIGATOR_ASSIGNED ="No Investigator Assigned";

    public static final String NND_REJECTED_NOTIFICATIONS_FOR_APPROVAL = "Rejected Notifications Queue";
    public static final String NON_CASCADING = "NON_CASCADING";
    public static final String CASCADING = "CASCADING";

    public static final String SECURITY_FAIL = "FAIL";

    public static final String CONDITION_CD= "CONDITION_CD";
    public static final String CONDITION= "Condition";
    public static final String PROG_AREA_CD = "PROG_AREA_CD";
    public static final String JURIS_CD = "JURIS_CD";
    /*
     * Queue Sizes properties.
     */

    public static final String MY_PROGRAM_AREAS_INVESTIGATIONS_QUEUE_SIZE="MY_PROGRAM_AREAS_INVESTIGATIONS_QUEUE_SIZE";
    public static final String PAGE_MANAGEMENT_CONDITION_LIBRARY_QUEUE_SIZE="PAGE_MANAGEMENT_CONDITION_LIBRARY_QUEUE_SIZE";
    public static final String PAGE_MANAGEMENT_VALUE_SET_LIBRARY_QUEUE_SIZE="PAGE_MANAGEMENT_VALUE_SET_LIBRARY_QUEUE_SIZE";
    public static final String PAGE_MANAGEMENT_QUESTION_LIBRARY_QUEUE_SIZE="PAGE_MANAGEMENT_QUESTION_LIBRARY_QUEUE_SIZE";
    public static final String PAGE_MANAGEMENT_PAGE_LIBRARY_QUEUE_SIZE="PAGE_MANAGEMENT_PAGE_LIBRARY_QUEUE_SIZE";
    public static final String PAGE_MANAGEMENT_TEMPLATE_LIBRARY_QUEUE_SIZE="PAGE_MANAGEMENT_TEMPLATE_LIBRARY_QUEUE_SIZE";
    public static final String MY_PROGRAM_AREAS_QUEUE_SORTBY_NEWEST_INV_STARTDATE="MY_PROGRAM_AREAS_QUEUE_SORTBY_NEWEST_INV_STARTDATE";
    public static final String APPR_QUEUE_FOR_NOTIF_DISP_SIZE="APPR_QUEUE_FOR_NOTIF_DISP_SIZE";
    public static final String OBSERVATIONS_NEEDING_ASSIGNMENT_QUEUE_SIZE="OBSERVATIONS_NEEDING_ASSIGNMENT_QUEUE_SIZE";
    public static final String OBSERVATIONS_NEEDING_REVIEW_QUEUE_SIZE="OBSERVATIONS_NEEDING_REVIEW_QUEUE_SIZE";
    public static final String REJECTED_NOTIF_QUEUE_DISP_SIZE="REJECTED_NOTIF_QUEUE_DISP_SIZE";
    public static final String UPD_QUEUE_FOR_NOTIF_DISP_SIZE="UPD_QUEUE_FOR_NOTIF_DISP_SIZE";
    public static final String APPROVAL_NOTIFICATION_QUEUE_SIZE="APPROVAL_NOTIFICATION_QUEUE_SIZE";
    public static final String REJECTED_NOTIFICATION_QUEUE_SIZE="REJECTED_NOTIFICATION_QUEUE_SIZE";
    public static final String MANAGE_CONTACT_ASSOCIATIONS_QUEUE_SIZE="MANAGE_CONTACT_ASSOCIATIONS_QUEUE_SIZE";
    public static final String MANAGE_COINFECTIONS_ASSOCIATIONS_QUEUE_SIZE="MANAGE_COINFECTIONS_ASSOCIATIONS_QUEUE_SIZE";
    public static final String PAGE_MANAGEMENT_IMPORTEXPORT_LIBRARY_QUEUE_SIZE="PAGE_MANAGEMENT_IMPORTEXPORT_LIBRARY_QUEUE_SIZE";
    public static final String MESSAGING_MANAGEMENT_ACTIVITYLOG_LIBRARY_QUEUE_SIZE="MESSAGING_MANAGEMENT_ACTIVITYLOG_LIBRARY_QUEUE_SIZE";
    public static final String EPILINK_MANAGEMENT_ACTIVITYLOG_LIBRARY_QUEUE_SIZE ="EPILINK_MANAGEMENT_ACTIVITYLOG_LIBRARY_QUEUE_SIZE";
    public static final String ASSOCIATE_INVESTIGATIONS_QUEUE_SIZE="ASSOCIATE_INVESTIGATIONS_QUEUE_SIZE";
    public static final String MESSAGE_QUEUE_SIZE="MESSAGE_QUEUE_SIZE";
    public static final String PAGE_MANAGEMENT_PORT_PAGE_LIBRARY_QUEUE_SIZE="PAGE_MANAGEMENT_PORT_PAGE_LIBRARY_QUEUE_SIZE";
    public static final String MANAGE_TRIGGERCODES_QUEUE_SIZE="MANAGE_TRIGGERCODES_QUEUE_SIZE";
    public static final String IIS_PATIENT_SEARCH_RESULT_QUEUE_SIZE="IIS_PATIENT_SEARCH_RESULT_QUEUE_SIZE";
    public static final String IIS_VACCINATION_SEARCH_RESULT_QUEUE_SIZE="IIS_VACCINATION_SEARCH_RESULT_QUEUE_SIZE";
    
    public static String COUNTRY_LIST = "COUNTRY_LIST";
    public static String ISO_COUNTRY_LIST = "ISO_COUNTRY_LIST";
    public static String STATE_LIST = "STATE_LIST";
    public static String JURIS_LIST = "JURIS_LIST";
    public static String EXPORT_JURIS_LIST = "EXPORT_JURIS_LIST";
    public static String CONDITION_FAMILY_LIST = "CONDITION_FAMILY_LIST";
    public static String EXPORT_RECEIVING_LIST = "EXPORT_RECEIVING_LIST";
    public static String JURIS_ALERT_LIST = "JURIS_ALERT_LIST";
    
    public static String TREAT_COMPOSITE = "TREAT_COMPOSITE";
    public static String PROG_AREA= "PROG_AREA";
    public static String Nedss_Entry_Id="Users_Email_EntryId";
    public static String Users_With_Valid_Email="Users_With_Valid_Email";
    public static String Users_With_Active_Alert="Users_With_Active_Alert";
    public static String PSL_CNTRY = "PSL_CNTRY_FOR_JSP";
    public static String P_LANG = "P_LANG_FOR_JSP";
    public static String O_NAICS = "O_NAICS_JSP";
    public static String PARTICIPATION_TYPE_LIST = "PARTICIPATION_TYPE_LIST";
    public static String AOE_LOINC_LIST = "AOE_LOINC_LIST";

    public static String CASE_VERIFICATION= "CASE_VERIFICATION";
    /**
     * Constants for the Rules Engine
     */
    public static String  ENABLE = "enable";
    public static String  FILTER = "filter";
    public static String  DATE_RANGE = "dateRange";
    public static String  FILTER_REMOVE = "R";
    public static String  FILTER_ADD = "A";
    public static String  DATE = "Date";
    public static String MMWR = "MMWR";
    public static String DATE_COMPARE="dateCompare";
    public static String ON_SUBMIT = "S";
    public static String ON_CHANGE = "C";
    public static String ON_CREATE_SUBMIT = "D";
    public static String MATCH_VALUE = "matchValue";
    public static String REQUIRED_IF = "requiredIf";
    public static String DISABLE = "disable";
    public static String YEAR_RANGE = "yearRange";
    public static String VALID_LENGTH = "validLength";
    public static String VALID_VALUE = "validValue";
    public static String DATE_COMPARE_LIMIT = "dateCompareLimit";
    public static String YEAR_RANGE_EQUAL = "yearRangeEqual";
    public static String UNIQUE_CASE_NUMBER = "uniqueCaseNumber";
    public static String SINGLE_SRC_SAME_TARGET_FILTER = "singleSrcSameTargetFilter";
    public static String CASCADING_ENABLE_ON_FILTER_TARGET = "cascadingEnableOnFilterTarget";

    public static String EIGHTEEN_SEVENTYFIVE = "1875";



    /*
     * dataTypes
     */
    public static String DATATYPE_CODED = "Coded";
    public static String DATATYPE_TEXT = "Text";
    public static String DATATYPE_DATE = "Date";
    public static String DATATYPE_NUMERIC = "Numeric";

    /*
     * Operators for Rules Engine.
     */
    public static String LESS_THAN = "<";
    public static String LESS_THAN_EQUAL = "<=";
    public static String Equal = "=";
    public static String Not_Equal = "!=";

    public static String INV_FORM_RVCT = "INV_FORM_RVCT";
    public static String INV_FORM_VAR = "INV_FORM_VAR";

    public static String EDIT_SUBMIT_ACTION = "EDIT_SUBMIT";
    public static String CREATE_SUBMIT_ACTION = "CREATE_SUBMIT";
    public static String VIEW_LOAD_ACTION = "View";
    //public static String VIEW_SUBFORM_LOAD_ACTION = "ViewSubForm";
    //public static String EDIT_SUBFORM_SUBMIT_ACTION = "EditSubForm";
    
    
    public static String CREATE_LOAD_ACTION = "Create";
    public static String EDIT_LOAD_ACTION = "Edit";
    public static String PREVIEW_ACTION = "Preview";
    public static String RESULT_LOAD_ACTION = "ResultLoad";
    //Added for Export case Notification
    public static String ADD_TRIGGER_ACTION = "AddTrigger";
    public static String CLONE_LOAD_ACTION = "Clone";
    public static String CLONE_SUBMIT_ACTION = "ClONE_SUBMIT";
    public static String CREATE_EXTEND_ACTION = "CREATE_EXTEND";
    public static String PORT_LOAD_ACTION = "Port";

    //SRT Admin
    public static String LABORATORY_IDS = "LABORATORY_IDS";
    public static String LAB_TEST_TYPES = "LAB_TEST_TYPES";
    public static String CODE_SET_NMS = "CODE_SET_NMS";
    public static String CODE_SYSTEM_CD_DESCS = "CODE_SYSTEM_CD_DESCS";
    public static String NBS_UNITS_TYPE = "NBS_UNITS_TYPE";

    /*
     * Unique keys for SRT Admin
     */
     public static String CODE = "Code";
     public static String LABID = "Lab ID";
     public static String LABTESTCD = "Lab Test Code";
     public static String LABTESTCD_LOINCCD = "Lab Test Code or Loinc Code";
     public static String LABRESULTCD = "Lab Result Code";
     public static String LABRESULTCD_SNOMEDCD = "Lab Result Code or Snomed Code";
     public static String LOINCCD = "Loinc Code";
     public static String LOINCCD_CONDITIONCD = "Loinc Code or Condition Code";
     public static String SNOMEDCD = "Snomed Code";
     public static String SNOMEDCD_CONDITIONCD = "Snomed Code or Condition Code";
     public static String LOINC_CD = "LOINC Code";
     public static String SNOMED_CD = "SNOMED Code";
     public static String CODE_SET = "Value Set Code";
     public static String CONDITIONCD = "Condition Code";
     public static String LOINC_CODE = "LN";
     public static String SNOMED_CODE = "SNM";

     //Unique keys for Import and Export Admin
     public static String REC_SYS_NM = "System Name";
     public static String REC_SYS_SHORT_NM = "System short Name";
     public static String REC_SYS_OID = "System Oid";


    // Initial Value for the Version Control Number
    public static final int INITIAL_VERSION_CONTROL_NUMBER = 1;


    //Investigation Transfer OwnerShip contextAction constants

    public static String FileSummary = "FileSummary";
    public static String ReturnToFileEvents = "ReturnToFileEvents";
    public static String ReturnToFileSummary = "ReturnToFileSummary";

    //LocalFields
    public static String LDF_HTML_TYPES = "LDF_HTML_TYPES";
    public static String NBS_EVENT_SEARCH_DATES = "NBS_EVENT_SEARCH_DATES";
    public static String PHVS_EVN_SEARCH_ABC = "PHVS_EVN_SEARCH_ABC";
    public static String INVESTIGATOR = "INVESTIGATOR";
    public static String FILTERBYINVESTIGATOR = "Investigator equal to: ";
    public static String FILTERBYJURISDICTION = "Jurisdiction equal to: ";
    public static String FILTERBYCONDITION = "Condition equal to: ";
    public static String FILTERBYSUPERVISOR = "Supervisor equal to: ";
    public static String FILTERBYREFERRALBASIS = "Referral Basis equal to: ";
    public static String FILTERBYDESCRIPTION = "Description equal to: ";
    public static String FILTERBYEVENTID = "Event ID equal to: ";
    public static String FILTERBYSTATUS = "Case Status equal to: ";
    public static String FILTERBYDATE = "Start Date equal to: ";
    public static String FILTERBYREPORTTYPE = "Report Type equal to: ";
    public static String FILTERBYNOTIF = "Notification equal to: ";
    public static String FILTERBYOBSERVATIONTYPE = "ObservationType equal to: ";
    public static String FILTERBYEVENTTYPE = "Event Type equal to: ";
    public static String FILTERBYDSMSTATUS = "Status equal to: ";
    public static String FILTERBYLASTUPDATED = "Last Updated equal to: ";
    public static String FILTERBYDSMACTION = "Action equal to: ";
    public static String DATE_NON_BLANK_KEY = "!0";
    public static String DATE_BLANK_KEY = "0";
    public static String BLANK_KEY = "BLNK";
    public static String BLANK_VALUE = "(Blanks)";
    public static String NON_BLANK_VALUE = "(Non-Blank)";
    public static String BLANK_INVESTIGATOR_VALUE = "(Blanks)";
    public static String FILTERBYMESSAGEID = "Message ID equal to: ";
    public static String FILTERBYSOURCENAME = "Reporting Facility equal to: ";
    public static String FILTERBYPATIENTNAME = "Patient Name equal to: ";
    public static String FILTERBYACCESSIONNO = "Accession# equal to: ";
    public static String FILTERBYOBSERVATIONID = "Observation ID equal to: ";

    public static String GETINVESTIGATORFULLNAME ="getInvestigatorFullName";
    public static String RETURN_TO_OPEN_INVESTIGATIONS = "Return to Open Investigations";
    public static String RETURN_TO_MESSAGE_QUEUE= "Return to Messages Queue";
    public static String RETURN_TO_SUPERVISOR_REVIEW_QUEUE = "Return to Supervisor Review Queue";

    //Manage Code to Condition Library
    
    public static String FILTERBYCODE = "Code equal to: ";
    public static String FILTERBYDISPLAYNAME = "Display Name equal to: ";
    public static String FILTERBYPROGRAMAREA = "Program Area equal to: ";
    public static String FILTERBYCONDITION2 = "Condition equal to: ";
    public static String FILTERBYCODINGSYSTEM= "Coding System equal to: ";
    public static String FILTERBYSTATUS2= "Status equal to: ";
    
    
    
    
    // Observation Needing Review Queue
    public static final String OBSERVATION_TYPE_COUNT = "ObservationTypeCount";
    public static final String JURISDICTIONS_COUNT = "JurisdictionsCount";
    public static final String RESULTEDTEST_COUNT = "ResultedTestCount";
    public static final String RESULTEDDES_COUNT = "ResultedDesCount";
    public static final String DATE_FILTER_COUNT = "dateFilterListCount";
    public static final String DATE_PRGAREA_COUNT = "dateProgramAreaCount";
   

    // Updated Notification Queue Filter counts
    public static final String CASE_STATUS_FILTER_ITEMS_COUNT = "CaseStatusFilterItemsCount";
    public static final String SUBMITTED_BY_FILTER_ITEMS_COUNT = "SubmittedByFilterItemsCount";
    public static final String NOTIFICATION_CODE_FILTER_ITEMS_COUNT = "NotificationCodeFilterItemsCount";
    public static final String RECIPIENT_FILTER_ITEMS_COUNT = "RecipientFilterItemsCount";
    public static final String UPDATE_DATE_FILTER_ITEMS_COUNT = "UpdateDateFilterItemsCount";

    // Approval Notification Queue
    public static String FILTERBYSUBMITTEDBY = "Submitted By equal to: ";
    public static String FILTERBYRECIPIENT = "Recipient equal to: ";
    public static String FILTERBYTYPE = "Type equal to: ";
    public static String FILTERBYUPDATEDATE = "Update Date equal to: ";

    // Manage Template Queue
    public static String FILTERBYRECORDSTATUS = "Status equal to: ";
    public static String FILTERBYSOURCE = "Source equal to: ";
    public static String FILTERBYLASTUPDATEBY = "Last Updated By equal to: ";
    public static String FILTERBYLASTUPDATEDATE = "Last Update Date equal to: ";
    public static String FILTERBYTEMPLATEDESCRIPTION = "Template Description equal to: ";
    
    //Rejected Notification Queue
    public static String FILTERBYREJECTEDBY = "Rejected By equal to: ";
    public static String SUBJECT_OF_DOC="SubjOfDoc";
    public static String SUBJECT_OF_DOC_DESC="Patient Subject of Public Health Document";
    public static String ACT_CLASS_CD_FOR_DOC="DOC";
    public static String DocToPHC= "DocToPHC";
    public static String DocToCON= "DocToCON";
    public static final String LOCAl_DESC = "Local";

    // Manage ImpExpActivityLog Queue -  defined the additional ones only

    public static String FILTERBYPROCESSEDTIME = "Processed Time equal to: ";
    public static String FILTERBYTEMPLATENAME = "Template Name equal to: ";

    // Manage Associate Obs to one or more Investigations Queue
    public static final String RETURN_TO_ASSOCIATE_TO_INVESTIGATIONS = "Return to Associate to Investigations";

    // Manage ImpExpActivityLog Queue
    public static String DSMLOGACTIVITY_FILTERBYPROCESSEDTIME = "Processed Time equal to: ";
    public static String DSMLOGACTIVITY_FILTERBYEVENTID = "Event ID equal to: ";
    public static String DSMLOGACTIVITY_FILTERBYACTION = "Action equal to: ";
    public static String DSMLOGACTIVITY_FILTERBYALGORITHMNAME = "Algorithm Name equal to: ";
    public static String DSMLOGACTIVITY_FILTERBYSTATUS = "Status equal to: ";
    public static String DSMLOGACTIVITY_FILTERBYEXCEPTIONTEXT = "Exception Text equal to: ";


 // Manage EpilinkActivityLog Queue
    public static String EPILINKLOGACTIVITY_FILTERBYPROCESSEDDATE = "Processed DATE equal to: ";
    public static String EPILINKLOGACTIVITY_FILTERBYOLDEPILINK = "Old Epilink ID equal to: ";
    public static String EPILINKLOGACTIVITY_FILTERBYNEWEPILINK = "New Epilink ID equal to: ";
    public static String EPILINKLOGACTIVITY_FILTERBYUSERNAME = "User Name equal to: ";


    public static String FILTERBYUSERNAME = "User Name equal to: ";

    //Document related Constants
    public static final String DOC_PROCESS = "DOC_PROCESS";
    public static final String PHC_236 = "PHC236";
    public static final String LAB_11648804 = "11648804";
    public static final String JURIS_NO_EXPORT_LIST = "JURIS_NO_EXPORT_LIST";
    public static final String SEX = "SEX";
    public static final String APPROVED_SHARED_NOTIFICATION_UID_LIST= "APPROVED_SHARED_NOTIFICATION_UID_LIST";
    public static final String APPROVED_TRANFERRED_NOTIFICATION_UID_LIST= "APPROVED_TRANFERRED_NOTIFICATION_UID_LIST";
    public static final String APPROVED_NND_NOTIFICATION_UID_LIST= "APPROVED_NND_NOTIFICATION_UID_LIST";

    //Aggregate Summary Constants
    public static final String AGG_SUM_CONDITION_CD= "AGG_SUM_CONDITION_CD";
    public static final String NOVEL_INFLUENZA_FLU = "11063";
    public static final String AGG_SUM_COUNT_INTERVAL_CD = "14497002"; //(Weekly)

    //Redesign manage obs/ vaccination/ treatment
    public static final String INVESTIGATION_CASE_STATUS = "Null";

    public static final String ManageEvents="ManageEvents";

    /* Constants related to Contact*/
    public static final String CONTACT_PROVIDER = "InvestgrOfContact";
    public static final String CONTACT_DISPOSITIONED_BY = "DispoInvestgrOfConRec";
    public static final String CONTACT_OTHER_INFECTED_PATIENT = "OthInfPatOfConRec";

    public static final String CONTACT_ORGANIZATION = "SiteOfExposure";
    public static final String CONTACT_FORMCODE = "CONTACT_REC";
    public static final String CONTACT_PRIORITY = "NBS_PRIORITY";
    public static final String CONTACT_STATUS = "PHC_IN_STS";
    public static final String CONTACT_DISPOSITION = "NBS_DISPO";

    /* access modifiers*/
    public static final String PUBLIC = "public";
    public static final String PRIVATE = "private";
    public static final String CONTACT_TRACING_ENABLE_IND = "Y";

    /* Constants related to PAGE MANAGEMENT*/
    public static final String[] PAGE_GROUP_EXC={"GROUP_DEM", "GROUP_MSG", "GROUP_NOT"};
    public static final String PAGE_GROUP = "NBS_QUES_GROUP";
    public static final String PAGE_SUBGROUP = "NBS_QUES_SUBGROUP";
    public static final String PAGE_SUBGROUP_IXS = "NBS_QUES_SUBGROUP_IXS";
    public static final String PAGE_DATATYPE = "NBS_DATA_TYPE";
    public static final String PAGE_VALSET = "NBS_DATA_TYPE";
    public static final String PAGE_MASK = "NBS_MASK_TYPE";
    public static final String PAGE_HL7_DATATYPE= "NBS_HL7_DATA_TYPE";
    public static final String PAGE_NBS_STATUS_CD= "NBS_STATUS_CD";
    public static final String PAGE_NBS_REC_STATUS_CD= "NBS_REC_STATUS_CD";
    public static final String PAGE_HL7_SEGMENT= "NBS_HL7_SEGMENT";

    public static final String DELETECONTEXT = "deleteContext";
    public static final String PUBWITHDRAFT = "PWD";
    public static final String PUBLISHED = "Published";
    public static final String CREATEDRAFTIND = "createDraftInd";
    public static final String CREATEDRAFTINDPUB = "PUB";
    public static final String CREATEDRAFTINDDRT = "DRT";
    public static final String NONE = "none";


    /**
     * Question Groups Classifications
     */
    public static enum QuestionGroupCodes {
        GROUP_NOT, GROUP_MSG, GROUP_INV, GROUP_DEM
    }

    /**
     * Question Entry Method - Denotes whether a question
     * in the question library is created by the user using the
     * 'Add Question' user interface or predefined in the
     * system.
     */
    public static enum QuestionEntryMethod {
        USER, // entered by the user
        SYSTEM // predefined in the system
    }

    // Data Location
    public static final String DATA_LOCATION_CASE_ANSWER_TEXT = "NBS_CASE_ANSWER.ANSWER_TXT";

    public static final String DATE_DATATYPE = "DATE";
    public static final String DATETIME_DATATYPE = "DATETIME";
    public static final String NUMERIC_DATATYPE = "NUMERIC";
    public static final String NUMERIC_CODE = "NUM";
    public static final String TEXT_DATATYPE = "TEXT";
    public static final String PART_DATATYPE = "PART";

    public static final String CODED = "CODED";
    public static final String LITERAL = "LITERAL";
    public static final String CODED_DATA = "coded_data";
    public static final String TEXT_DATA = "text_data";
    public static final String PARTICIPATION_DATA = "participation_data";
    //
    public static final String PHIN_DEFINED_QUESTIONTYPE = "PHIN Standard Question";
    public static final String STATE_DEFINED_QUESTIONTYPE = "Locally Defined Question";

    // Page element types (Used in the Page Builder module)
    public static final String PAGE_ELEMENT_TYPE_PAGE = "Page";
    public static final String PAGE_ELEMENT_TYPE_TAB = "Tab";
    public static final String PAGE_ELEMENT_TYPE_SECTION = "Section";
    public static final String PAGE_ELEMENT_TYPE_SUBSECTION = "Subsection";
    public static final String PAGE_ELEMENT_TYPE_QUESTION = "Question";
    public static final String PAGE_ELEMENT_TYPE_HYPERLINK = "Hyperlink";
    public static final String PAGE_ELEMENT_TYPE_COMMENT = "Comment";
    public static final String PAGE_ELEMENT_TYPE_LINE_SEPARATOR = "Line Separator";
    public static final String PAGE_ELEMENT_TYPE_PARTICIPANT_LIST = "Participant List";
    public static final String PAGE_ELEMENT_TYPE_PATIENT_SEARCH = "Patient Search";
    public static final String PAGE_ELEMENT_TYPE_ACTION_BUTTON = "Action Button";
    public static final String PAGE_ELEMENT_TYPE_SET_VALUES_BUTTON = "Set Values Button";
    public static final String PAGE_ELEMENT_TYPE_ORIG_DOC_LIST = "Original Electronic Document List";

    public static final String PHIN_QUESTIONTYPE_CODE = "PHIN";
    public static final String STATE_QUESTIONTYPE_CODE = "LOCAL";
    public static final String NBS_QUESTION_TYPE = "NBS_QUESTION_TYPE";
    public static final String ORDER_GRP_ID = "2";

    public static final String VALUE_SET_TYPE_NO_SYSTEM_STAD = "VALUE_SET_TYPE_NO_SYSTEM_STAD";

    public static final String NBS_PH_DOMAINS = "NBS_PH_DOMAINS";

    public static final String DRAFT = "Draft";
    public static final String TEMPLATE = "TEMPLATE";
    public static final String MULTISELECT_COMPONENT = "1013";
    public static final String MULTISELECT_COMPONENT_READONLY_SAVE = "1025";

    public static final String NBS_CASE_ANSWER_ANSWER_TXT = "NBS_CASE_ANSWER.ANSWER_TXT";
    public static final String NBS_ANSWER_ANSWER_TXT = "NBS_ANSWER.ANSWER_TXT";
    public static final String ANSWER_TXT = "ANSWER_TXT";
    public static final String CT_CONTACT_ANSWER_ANSWER_TXT = "CT_CONTACT_ANSWER.ANSWER_TXT";

    public static final String INV = "INV";
    public static final String INVESTIGATION_CAP = "INVESTIGATION";
    public static final String FROM_CONTEXT = "fromWhere";
    public static final String PREVIEW = "preview";
    public static final String PUBLISH = "publish";
    public static final String IMPORT_CD = "I";
    public static final String EXPORT_CD = "E";
    public static final String IMPORT_DESC = "Import";
    public static final String EXPORT_DESC = "Export";

    public static final String  INVESTIGATION_CD = "424352007";
    public static final String  MORBIDITY_REPORT_CD = "OBS";
    

    public static final String REPEATING_QUESTION="REPEATING_QUESTION";
    public static final String NON_REPEATING_QUESTION="NON_REPEATING_QUESTION";

    public static final String TEMPLATE_SUMMARIES = "TEMPLATE_SUMMARIES";
    public static final String PAGE_COND_MAPPING_SUMMARIES = "PAGE_COND_MAPPING_SUMMARIES";

    public static final String TEMPLATE_NM = "TEMPLATE_NM";
    public static final String RELATED_CONDITIONS = "RELATED_CONDITIONS";
    public static final String TEMPLATE_DESCR = "TEMPLATE_DESCR";
    // Question Filter
    public static final String UNIQUE_ID = "UNIQUE_ID";
    public static final String UNIQUE_NAME = "UNIQUE_NAME";
    public static final String LABEL = "LABEL";
    public static final String DATA_TYPE="DATA_TYPE";
    public static final String CODE_SET_NM="CODE_SET_NM";
    //To Question Filter for Mapping Page and Answer Page
    public static final String TO_UNIQUE_ID="TO_ID";
    public static final String TO_LABEL="TO_LABEL";
    public static final String TO_DATA_TYPE="TO_DATA_TYPE";
    public static final String TO_CODE_SET_NM="TO_CODE_SET_NM";
    
    public static final String FROM_CODE="FROM_CODE";
    public static final String FROM_CODE_DESC="FROM_CODE_DESC";
    public static final String TO_CODE="TO_CODE";
    public static final String TO_CODE_DESC="TO_CODE_DESC";
    //Filter for Port Page Management(Port Page Library)
    public static final String MAP_NAME="MAP_NAME";
    public static final String FROM_PAGE_NM="FROM_PAGE_NM";
    public static final String TO_PAGE_NM="TO_PAGE_NM";
    
    public static final String INV_PATIENT = "PATIENT";
    public static final String INV_COMMENT = "COMMENT";
    public static final String INV_LOCAL_ID = "LOCALID";
    public static final String INV_PROVIDER = "PROVIDER";
  

   public static String PEND_APPR="PEND_APPR";
   public static String COMPLETED = "COMPLETED";
   public static String MSG_FAIL = "MSG_FAIL";
   public static String REJECTED = "REJECTED";
   public static String APPROVED = "APPROVED";
   public static String EVENT = "EVENT";
   public static String ADDMORB = "addMorb";
   public static String ADDLAB = "addObs";
   public static String ADDVACCINE = "addVaccine";
   public static String ADDINVS = "addInves";
   public static String EDITBUTTON = "editButton";
   public static String DELETEBUTTON = "deleteButton";
   public static String RECORDSTATUSCD = "recordStatusCd";
   public static String EVENTCOUNT = "EventCount";
   public static String MERGEINVS = "mergeInves";
   
public static String PROPERTY_FILE ="NEDSS.properties";
   public static final String DELETED = "DELETED";
    public static final String TABLE_NOT_MAPPED = "TABLE NOT MAPPED";

    //DSM constants
    public static final String CONDITIONS_PORT_REQ_IND_FALSE ="CONDITIONS_PORT_REQ_IND_FALSE";
    public static final String SENDING_SYSTEM_LIST ="SENDING_SYSTEM_LIST";
    public static final String INVESTIGATION_TYPE_RELATED_PAGE ="INVESTIGATION_TYPE_RELATED_PAGE";
    public static final String CONDITION_LIST ="CONDITION_LIST";
    public static final String TEMPLATE_CONDITION_LIST ="TEMPLATE_CONDITION_LIST";
    public static final String PUBLISHED_CONDITION_LIST ="PUBLISHED_CONDITION_LIST";
    public static final String USE_CURRENT_DATE="Current Date";

    // code set names
    public static final String CODE_SET_JURISDICTION = "S_JURDIC_C";
    public static final String CODE_SET_COUNTY_CCD = "COUNTY_CCD";
    public static final String LEGACY_MASTER_MSG = "LGCY_MSTR_MSG";

	public static enum CANADA {CAN, CA, PHVS_STATEPROVINCEOFEXPOSURE_CDC_CAN}
	public static String CANADA_124 = "124";
	public static enum USA {USA, US, PHVS_STATEPROVINCEOFEXPOSURE_CDC_US}
	public static String USA_840 = "840";
	public static enum MEXICO {MEX, MX, PHVS_STATEPROVINCEOFEXPOSURE_CDC_MEX}
	public static String MX_484 = "484";
	public static String PHVS_STATEPROVINCEOFEXPOSURE_CDC = "PHVS_STATEPROVINCEOFEXPOSURE_CDC";

    public static final String VARICELLA_VALUE = "Varicella";
    public static final String VARICELLA_KEY = "10030";
    public static final String TUBERCULOSIS_VALUE = "Tuberculosis";
    public static final String TUBERCULOSIS_KEY = "10220";

    public static final String EI_AUTH_ORG ="EI_AUTH_ORG";

    public static final String EI_AUTH_PRV ="EI_AUTH_PRV";

    public static final String EI_AUTH = "EI_AUTH";
    public static final String ADMIN_ALERT_LAB="11648804";
    public static final String LAB_TYPE_CD = "11648804";
    public static final String ISDBSECURITY= "ISDBSECURITY";
    public static final String SECURE_KEY_NOT_DEFINED= "SECURE_KEY_NOT_DEFINED";
    public static final String SECURE_VAL_NOT_DEFINED= "SECURE_VAL_NOT_DEFINED";
    public static final String SECURE_PROP_DEFINED= "SECURE_PROP_DEFINED";

    //Coinfection

    public static final String COINFECTION_LOGIC = "COINFECTION_LOGIC";
    public static final String INVESTIGATION_EXISTS = "INVESTIGATION_EXISTS";
    public static final String COINFECTION_INV = "COINFECTION_INV";
    public static final String COINFECTION_INV_EXISTS = "COINFECTION_INV_EXISTS";
    public static final String INV_EDIT_PERM = "INV_EDIT_PERM";
    public static final String LAB_REPORT_ASSOC_PERM = "LAB_REPORT_ASSOC_PERM";
    public static final String MORB_REPORT_ASSOC_PERM = "MORB_REPORT_ASSOC_PERM";
    public static final String INTERVIEW_CLASS_CODE = "IXS";

    //processing decision code set for mark as reviewed for lab and morb
    public static final String STD_PROCESSING_DECISION_LIST_LAB_SYPHILIS= "STD_LAB_SYPHILIS_PROC_DECISION";
    public static final String STD_PROCESSING_DECISION_LIST_MORB_SYPHILIS= "STD_MORB_SYPHILIS_PROC_DECISION";
    public static final String STD_PROCESSING_DECISION_LIST_NON_SYPHILIS= "STD_NONSYPHILIS_PROC_DECISION";
    public static final String STD_PROCESSING_DECISION_LIST_SYPHILIS_AND_NONSYPHILIS= "STD_UNKCOND_PROC_DECISION";
    public static final String NBS_NO_ACTION_RSN= "NBS_NO_ACTION_RSN";

    //processing decision code set for create investigation from lab and morb
    public static final String STD_CREATE_INV_LAB_UNKCOND_PROC_DECISION= "STD_CREATE_INV_LAB_UNKCOND_PROC_DECISION";
    public static final String STD_CREATE_INV_LABMORB_NONSYPHILIS_PROC_DECISION= "STD_CREATE_INV_LABMORB_NONSYPHILIS_PROC_DECISION";
    public static final String STD_CREATE_INV_LABMORB_SYPHILIS_PROC_DECISION= "STD_CREATE_INV_LABMORB_SYPHILIS_PROC_DECISION";
    public static final String STD_CREATE_INV_LAB_SYPHILIS_PROC_DECISION= "STD_CREATE_INV_LAB_SYPHILIS_PROC_DECISION";
    public static final String STD_CREATE_INV_MORB_SYPHILIS_PROC_DECISION= "STD_CREATE_INV_MORB_SYPHILIS_PROC_DECISION";
    public static final String STD_NONSYPHILIS_PROC_DECISION = "STD_NONSYPHILIS_PROC_DECISION";
    public static final String STD_CONTACT_RCD_PROCESSING_DECISION = "STD_CONTACT_RCD_PROCESSING_DECISION";
    public static final String STD_CR_PROC_DECISION_NOINV = "STD_CR_PROC_DECISION_NOINV";

    //Constants related to STD Disposition
    public static final String STD_PROCESSING_DECISION_NOT_APPLICABLE = "NA";
    public static final String STD_PROGRAM_AREA = "STD";
    public static final String GROUP_INV = "GROUP_INV";
    public static final String GROUP_CON = "GROUP_CON";
    public static final String GROUP_IXS = "GROUP_IXS";
    public static final String VACCINATION_CLASS_CODE = "VAC";
    
    public static final String STD_PA_LIST = "STD_PA_LIST";
    public static final String PROCESSING_DECISION = "ProcessingDecision";
    public static final String INVESTIGATION_TYPE = "investigationType";
    public static final String INVESTIGATION_TYPE_COINF = "Co-infection";
    public static final String INVESTIGATION_TYPE_NEW = "New";
    public static final String PREGNANT_IND_CD = "pregnantIndCd";
    public static final String PREGNANT_WEEKS = "pregnantWeek";

    public static final String REFERRAL_BASIS = "NBS110";
    public static final String REFERRAL_BASIS_OOJ = "NBS270";
    public static final String REFERRAL_BASIS_LAB = "T1";
    public static final String REFERRAL_BASIS_MORB = "T2";

    public static final String CURRENT_INVESTIGATOR = "INV180";
    public static final String PHYSICIAN_OF_PHC = "INV182";
    public static final String INITIAL_INVESTIGATOR = "NBS139";
    public static final String SURVEILLANCE_INVESTIGATOR = "NBS145";
    public static final String FOLLOWUP_INVESTIGATOR = "NBS161";
    public static final String INITIAL_FOLLOWUP_INVESTIGATOR = "NBS163";
    public static final String INITIAL_FOLLOWUP_INVESTIGATOR_ASSIGN_DATE = "NBS164";
    public static final String INTERVIEW_INVESTIGATOR = "NBS186";
    public static final String SBJ_INTERVIEW = "IntrvweeOfInterview";
    public static final String INITIAL_INTERVIEW_INVESTIGATOR = "NBS188";
    public static final String INITIAL_INTERVIEW_INVESTIGATOR_ASSIGN_DATE = "NBS189";
    public static final String MESSAGE_QUEUE = "Messages Queue";

    public static final String DISABLED = "DISABLED";
    //Constants used in Interview
    public static final String PRESUMPTIVE_INTERVIEW_TYPE = "PRESMPTV";
    public static final String INITIAL_ORIGINAL_INTERVIEW_TYPE = "INITIAL";
    public static final String REINTERVIEW_INTERVIEW_TYPE = "REINTVW";
	public static final String COINFECTION_FOR_INTERVIEW_EXISTS = "IXS190";
	public static final String INTERVIEW_COINFECTION_LIST = "IXS191";
    
    //Constants used in Page Management Rules
    public static final String ANY_SOURCE_VALUE = "Any Source Value";
    public static final String ANY_SOURCE_VALUE_COMPARATER = "=";

    public static final String SUPERVISOR_REVIEW_QUEUE_SIZE = "SUPERVISOR_REVIEW_QUEUE_SIZE";
    public static final String SUPERVISOR_REVIEW_QUEUE = "Supervisor Review Queue";

    public static final String PAGE_TITLE = "PageTitle";


    public static final String ACCEPT = "Accept";
    public static final String REJECT = "Reject";
    public static final String CASE_CLOSURE = "Case Closure";
    public static final String FR_CLOSURE = "FR Closure";
    public static final String OOJ_XFER = "OOJ Xfer";
    public static final String FLD_DISPO_OOJ = "K";

    public static final String HIV_SUB_GROUP = "HIV";

    public static final String XSS_FILTER_PATTERN = "XSS_FILTER_PATTERN";

    public static enum CLOSURE_INVESTGR {ClosureInvestgrOfPHC, NBS197};

    public static enum CURRENT_INVESTGR {InvestgrOfPHC, INV180};
    
    //Page Business Object Types
    public static final String CONTACT_BUSINESS_OBJECT_TYPE = "CON";
    public static final String INTERVIEW_BUSINESS_OBJECT_TYPE = "IXS";
    public static final String ISOLATE_BUSINESS_OBJECT_TYPE = "ISO";
    public static final String ISO_BUSINESS_OBJECT_TYPE_DESC = "Isolate Tracking";
    public static final String SUSCEPTIBILITY_BUSINESS_OBJECT_TYPE = "SUS";
    public static final String SUSCEPTIBILITY_BUSINESS_OBJECT_TYPE_DESC = "Susceptibility";
    public static final String INTERVIEW_BUSINESS_OBJECT_DESC = "Interview";
    public static final String INVESTIGATION_BUSINESS_OBJECT_TYPE = "INV";
    public static final String VACCINATION_BUSINESS_OBJECT_TYPE = "VAC";
    public static final String VACCINATION_BUSINESS_OBJECT_DESC = "Vaccination";
    public static final String LAB_BUSINESS_OBJECT_TYPE = "LAB";
    public static final String MORB_BUSINESS_OBJECT_TYPE = "MORB";
    public static final String TRMT_BUSINESS_OBJECT_TYPE = "TRMT";
    public static final String TRMT_BUSINESS_OBJECT_TYPE_DESC = "Treatment";
    public static final String LAB_BUSINESS_OBJECT_TYPE_DESC = "Lab Report";
    public static final String MORB_BUSINESS_OBJECT_TYPE_DESC = "Morbidity Report";
    public static final String GENERIC_NO_POPUP_BUSINESS_OBJECT_TYPE = "GENERIC_NO_POPUP_BUSINESS_OBJECT_TYPE";
    

    //Constants used in SubForms
    
    public static final String CURRENT_KEY = "NBS459";
    public static final String CURRENT_BATCH_ENTRY_MODE = "modeBatchEntry";
    public static final String SUS_LAB_SUSCEPTIBILITIES = "SUS_LAB_SUSCEPTIBILITIES";
    public static final String ISO_LAB_TRACK_ISOLATES = "ISO_LAB_TRACK_ISOLATES";
    
    public static final String SUBFORM_HASHMAP = "subFormHashMap";
    
    		
    //    public static final String CDC_PROVIDER_FORM ="CDCProviderForm";
//    public static final String CDC_FIELD_RECORD_FORM ="CDCFieldRecordForm";

    public static final String CDC_PROVIDER_FORM ="CDCProviderForm";
    public static final String CDC_FIELD_RECORD_FORM="CDCFieldRecordForm";
    public static final String CDC_INTERVIEW_RECORD_FORM ="InterviewRecordForm";
    public static final String CDC_RVCT_FORM ="RVCTForm";
    public static final String CDC_TBLISS_FORM ="TBLISSForm";
    
//    public static final String CDC_PROVIDER_BLANK_FORM ="CDCProviderBlankForm";
//    public static final String CDC_FIELD_RECORD_BLANK_FORM="CDCFieldRecordBlankForm";
//    public static final String CDC_INTERVIEW_RECORD_BLANK_FORM ="InterviewRecordBlankForm";
    public static final String CDC_PROVIDER_FORM_PDF ="CDCProviderFormV10_PSF_6_0.pdf";
    public static final String CDC_FIELD_RECORD_FORM_PDF ="CDCFieldRecordForm.pdf";
    public static final String CDC_INTERVIEW_RECORD_PDF ="InterviewRecordForm.pdf";

    //For fixing error when printing field follow-up form. If the param is "Filled" is shows Error page. If we change the value to "NoBlank" we need to show on the frontend the value Filled for making this change transparently to the user
    public static final String CDC_FILLED ="NoBlank";
    public static final String CDC_FILLED_JSP ="Filled";
    public static final String CDC_BLANK = "Blank";

    //For Act_relationship(Between Case and Interview):
    //Source_class_cd = �IXS�
    //Target_class_cd = �CASE�
    //Type_cd=�Interview�

	public static final String IXS_SOURCE_CD = "IXS";
	public static final String IXS_TARGET_CD = "CASE";
	public static final String IXS_TYPE_CD ="Interview";
	public static final String IXS_ADD_REASON_CD = "because";

    	//For Act_relationship(Between Contact Record and Interview):
    	//Source_class_cd = �IXS�
    	//Target_class_cd = �CON�
    	//Type_cd=�Interview�
	public static final String Target_class_cd = "CON";


	public static final String COINFCTION_GROUP_ID_NEW_CODE = "GENERATE_NEW_ID";
	public static final String NO_BATCH_ENTRY = "NO_BATCH_ENTRY";
	public static final String BATCH_ENTRY = "BATCH_ENTRY";
	public static final String USER_VIEW = "USER_VIEW";


	public static final String FF = "FF";

	//Disposition code for special processing
	public static final String FROM1_A_PREVENTATIVE_TREATMENT="A";
	public static final String TO1_Z_PREVIOUS_PREVENTATIVE_TREATMENT="Z";
	public static final String FROM2_C_INFECTED_BROUGHT_TO_TREATMENT="C";
	public static final String TO2_E_PREVIOUSLY_TREATED_FOR_THIS_INFECTION="E";
	
	//Comparators and Separators
	public static final String LESS_THAN_LOGIC="<";
	public static final String LESS_THAN_OR_EQUAL_LOGIC="<=";
	public static final String GREATER_THAN_LOGIC=">";
	public static final String GREATER_THAN_OR_EQUAL_LOGIC=">=";
	public static final String EQUAL_LOGIC="=";
	public static final String NOT_EQUAL_LOGIC="!=";
	public static final String NOT_EQUAL_LOGIC2="<>";
	public static final String BETWEEN_LOGIC="BET";
	public static final String CONTAINS_LOGIC="CT";
	public static final String STARTS_WITH_LOGIC="SW";
	public static final String ISNULL_LOGIC="ISNULL";
	public static final String NOTNULL_LOGIC="NOTNULL";
	public static final String COLON = ":";

	
	public  static final String NON_LR_ENTITY_IDS = "NON_LR_ENTITY_IDS";
	
	public static final Long MULTI_LINE_NOTES_W_USER_DATE_STAMP_CD = 1019L;
	public static final Long MULTI_SELECT_CD = 1013L;
	public static final Long PARTICIPANT_CD = 1017L;
	
	public static final String NOT_APPLICABLE = "NA";
	
    public static enum ContainerType {
        Case, LabReport, Contac, GROUP_DEM
    } 
    
    public static final String XML_SCHEMA = "XML_SCHEMA";
    public static final String XML_PAYLOAD = "XML_PAYLOAD";
    
    public static final String ONGOING_CASE_FALSE="false";
    
    public static final String CODE_VALUE_GENERAL="CODE_VALUE_GENERAL";

    public static final String PART_CACHED_MAP_KEY_SEPARATOR="|";
    
    public static final String ORIG_XML_QUEUED = "ORIG_QUEUED";
    
    public static final String QUEUED = "QUEUED";
    
    public static final String MSG_RHAP_PROCESSING = "MSG_RHAP_PROCESSING";	
    public static final String ADD_TIME_FOR_SRT_FILTERING="addTime";
    public static final String DATA_SOURCE_NOT_AVAILABLE = "The report or data source you are trying to access is not valid, because the data mart that feeds this report or data source is not currently available in the reporting database. Please contact your system administrator.";
    public static final String NULL_FLAVOUR_OID = "2.16.840.1.113883.5.1008";
    public static final String PARTNER_SERVICES_ID = "PSID";
    public static final String RESULTED_TEST_LAB220 = "NBS_LAB220";
    public static final String ORGANISM_LAB278 = "NBS_LAB280";
    public static final String ORDERED_TEST_LAB112 = "NBS_LAB112";
    public static final String DRUGD_TEST_LAB110 = "NBS_LAB110";
    
    
    public static final String TREATMENT_NBS481 = "NBS481";
    
    
    public static final String ANY = "ANY";
    
    public static final String SUBFORM_PUBLISH = "SUBFORM_PUBLISH";
    public static final String ALREADY_PUBLISHED = "ALREADY_PUBLISHED";
    
    public static final String  ERR109_PART1="The record you are editing has been edited by ";
    public static final String  ERR109_PART2=" since you have opened it. The system is unable to save your changes at this time. Please Cancel from this Edit and try again.";
    
    public static final String  ANSWER_COUNT_DIFF_FOR_ROLLBACK = "ANSWER_COUNT_DIFF_FOR_ROLLBACK";
    
	public static final String DISABLE_SUBMIT_BEFORE_PAGE_LOAD = "DISABLE_SUBMIT_BEFORE_PAGE_LOAD";
	public static final String FILE_COMPONENTS_HASHMAP = "FILE_COMPONENTS_HASHMAP";
	
	public static final String ANSWER_MAP = "AnswerMap";
	public static final String ANSWER_ARRAY_MAP = "AnswerArrayMap";
	public static final String BATCH_MAP = "BatchMap";
	public static final String MORB_FORM_CD = "MorbFormCd";
	public static final String OLD_CLIENT_VO = "OldClientVO";

	/*COVID Related Constants*/
	public static final String COVID_CR_UPDATE = "COVID_CR_UPDATE";
	public static final String COVID_CR_UPDATE_SENDING_SYSTEM = "COVID_CR_UPDATE_SENDING_SYSTEM";
	public static final String COVID_CR_UPDATE_IGNORE = "COVID_CR_UPDATE_IGNORE";
	public static final String COVID_CR_UPDATE_TIMEFRAME = "COVID_CR_UPDATE_TIMEFRAME";
	public static final String COVID_CR_UPDATE_CLOSED = "COVID_CR_UPDATE_CLOSED";
	public static final String COVID_CR_UPDATE_MULTI_CLOSED = "COVID_CR_UPDATE_MULTI_CLOSED";
	public static final String COVID_CR_UPDATE_MULTI_OPEN = "COVID_CR_UPDATE_MULTI_OPEN";
	public static final String SINGLE_CLOSED = "SINGLE_CLOSED";
	public static final String SINGLE_OPEN = "SINGLE_OPEN";
	public static final String MULTI_CLOSED = "MULTI_CLOSED";
	public static final String MULTI_OPEN = "MULTI_OPEN";
	public static final String GOBACK_YEARS_ELR_MATCHING = "GOBACK_YEARS_ELR_MATCHING";
	

	public static final String CARET = "^";
	public static final String SOCIAL_SECURITY = "SS";
	public static final String SOCIAL_SECURITY_ADMINISTRATION = "SSA";
	
	
	public static final String X_FORWARDED_FOR = "X-FORWARDED-FOR";
	public static final String AOE_OBS = "AOE";
	public static final String LAB_FORM_CD = "Lab_Report";
	public static final String LAB_REPORT_EVENT_ID = "LR";
	public static final String INVESTIGATION_EVENT_ID = "I";
	public static final String LAB_MOR_CASE_REPORT = "LMC";
	
	public static final String NAICS_INDUSTRY_CODES_LIST="NAICS_INDUSTRY_CODES_LIST";
	
	public static final String ADV_SEARCH_EVENT_TIME_LAST_SIX_MONTHS="6M";
	public static final String ADV_SEARCH_EVENT_TIME_LAST_THIRTY_DAYS="30D";
	public static final String ADV_SEARCH_EVENT_TIME_LAST_SEVEN_DAYS="7D";
	
	public static final String SNUMBER_LABEL = "SSN";
	
	public static final String PODS = "ods";
	public static final String UODS = "nbs_ods";
	
}