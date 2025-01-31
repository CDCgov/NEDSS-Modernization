package gov.cdc.nedss.nnd.util;

import gov.cdc.nedss.util.PropertyUtil;

import java.io.File;
import java.util.TreeMap;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author
 * @version 1.0
 */

public class NNDConstantUtil {

    public NNDConstantUtil() {

    }

   public static  TreeMap<Object,Object> encodeMap  = new TreeMap<Object,Object>();
   static {
         encodeMap.put(">", "&gt");
         encodeMap.put("<", "&lt");
         encodeMap.put("&","&amp");
         encodeMap.put("'","&apos");
         encodeMap.put("\"","&quot");
   }   public static final  String delimiters = "><&'\"";

   public static  TreeMap<Object,Object> decodeMap  = new TreeMap<Object,Object>();
   static {
         decodeMap.put("&gt",">");
         decodeMap.put("&lt","<");
         decodeMap.put("&amp","&");
         decodeMap.put("&apos","'");
         decodeMap.put("&quot","\"");
   }

   public static final String NOTIFICATION_CD_NOTF = "NOTF";
   public static final String NOTIFICATION_CD_NSUM = "NSUM";
   public static final String NOTIFICATION_CSR="NCSR";

   public static final String JDBC_TIME_STAMP_FORMAT = "yyyy-MM-dd HH:mm:ss.FFFFFFFFF";
//   public static final String GMT_TIME_ZONE = "GMT";
   public static final String NND_EVENT_TYPE_INVESTIGATION = "Investigation";
   public static final String NND_EVENT_TYPE_LABREPORT = "LabReport";
   public static final String NND_EVENT_TYPE_VACCINATION = "Vaccination";
   public static final String NND_EVENT_TYPE_SUMMARY_REPORT = "SummaryReport";
   public static final String NND_EVENT_TYPE_NOTIFICATION = "Notification";
   public static final String NND_EVENT_TYPE_CDF= "CDFData";
   public static final String NND_ROLE_KEY = "ROLE_KEY";

// ELR ROLE CONSTANTS
   public static final String ROLE_CD_CT ="CT";
   public static final String ROLE_CD_SPP ="SPP";
   public static final String ROLE_CD_CON ="CON";



   public static final String MSGOUT_MESSAGE ="MSGOUT_MESSAGE";
   public static final String MSGOUT_NM_USE_CD = "L";
   public static final String MSGOUT_INTERACTION_ID_CDCNND1 = "CDCNND1"; //Condition Notification
   public static final String MSGOUT_INTERACTION_ID_CDCNND2 = "CDCNND2"; //Change/Retraction or Prior Notification
   public static final String MSGOUT_INTERACTION_ID_CDCNND3 = "CDCNND3"; //Summary Notification
   public static final String MSGOUT_INTERACTION_ID_CDCNND4 = "CDCNND4"; //Case Notification Acceptance Report


   public static final String MSGOUT_RECEIVING_FACILITY = "MSGOUT_RECEIVING_FACILITY";
   public static final String MSGOUT_RECEIVING_ASSIGNING_AUTHORITY_CD = "NBS";
   public static final String MSGOUT_RECEIVING_ASSIGNING_AUTHORITY_DESC_TXT = "Centers for Disease Control and Prevention";
   public static final String MSGOUT_RECEIVING_FACILITY_TYPE_CD = "FI";
   public static final String MSGOUT_RECEIVING_FACILITY_ROOT_EXTENTION = "CDC";

   public static final String MSGOUT_SENDING_FACILITY = "MSGOUT_SENDING_FACILITY";
   public static final String MSGOUT_SENDING_ASSIGNING_AUTHORITY_CD = "NBS";
   public static final String MSGOUT_SENDING_ASSIGNING_AUTHORITY_DESC_TXT = "NEDSS Base System";
   public static final String MSGOUT_SENDING_FACILITY_TYPE_CD = "FI";

   public static final String MSG_ID_ASSIGN_AUTH_CD = "NBS";
   public static final String MSG_ID_ASSIGN_AUTH_DESC_TXT = "NEDSS Base System";
   public static final String MSG_ID_TYPE_CD = "LID";
   public static final String MSG_ID_TYPE_DESC_TXT = "Local NBS Identifier";



   //these should not be used anymore, commenting for testing
   //public static final String NND_CASE_INTERACTION_ID = "CDCNND1";
   //public static final String NND_SUMMARY_INTERACTION_ID = "CDCNND3";

   public static final String NND_NEW_NOT_INTERACTION_ID = "CDCNND1";
   public static final String NND_AUTO_RESEND_INTERACTION_ID = "CDCNND2";
   public static final String NND_SUMMARY_INTERACTION_ID = "CDCNND3";
   public static final String SET_TRANSPORTQ_OUT = "setTransportQOut";
   public static final String NBS_TEST_PROCESSING_CODE = "T";
   public static final String NBS_TEST_PROCESSING_DESC_TXT = "Test";
   public static final String NBS_PRODUCTION_PROCESSING_CODE = "P";
   public static final String NBS_PRODUCTION_PROCESSING_DESC_TXT = "Production";
   public static final String LDF_PROCESSED = "LDF_PROCESSED";
   public static final String LDF_UPDATE = "LDF_UPDATE";
   public static final String LDF_CREATE = "LDF_CREATE";
   public static final String LDF_LOGGED = "LDF_LOGGED";
   public static final String TRANSPORT_Q_OUT_SUCCESSFUL_NNDACTIVITYLOG_CREATE_FAIL = "TRANSPORT Q OUT WAS SUCCESSFUL, INITIAL INSERT INTO NNDACTIVITYLOG FAILED";

   public static final String LDF_CDF_CLASSCD= "CDC";
   public static final String LDF_DM_CLASSCD= "DM";
   public static final String EXTRACTION_FAILURE = "EXTRACTION_FAIL";
   public static final String TRANSPORT_COMPLETE = "TRANSPORT_COMPLETE";
   public static final String TRANSPORT_FAIL = "TRANSPORT_FAIL";
   public static final String PHINMS_SUCCESS = "success";
   public static final String PHINMS_FAILURE = "failure";
   public static final String NND_ACTIVITY_LOG_LDF_ERROR_MESSAGE_TXT_PREFIX ="STATE_DEFINED_FIELD_METADATA.LDF_UID";
   public static final String OBTAIN_SUBFORM_DATA_METHOD = "getSubformMetaDataByUids";
   public static final String NND_ACTIVITY_RECORD_STATUS_CD_DEV_SUCCESS = "DEV_SUCCESS";
   public static final String NON_NND = "NON_NND";
   public static final String NND_ACTIVITY_RECORD_STATUS_CD_DEV_ERROR = "DEV_ERROR";
   public static final String NND_ACTIVITY_STATUS_CD_DEV_SUCCESS = "S";
   public static final String NND_ACTIVITY_STATUS_CD_DEV_ERROR = "E";

   public static final String FAILED_CASE_REPORTS_ROOT_DIR = PropertyUtil.nedssDir + "FailedCaseReports"  + File.separator ;
   public static final String CASE_REPORTS_ROOT_DIR = PropertyUtil.nedssDir + "phdc"  + File.separator ;
   public static final String CSR_ROOT_DIR = PropertyUtil.nedssDir + "csr"  + File.separator ;
   public static final String SUMMARY_ROOT_DIR = PropertyUtil.nedssDir + "summary" + File.separator ;
   public static final String NND_ROOT_DIR = PropertyUtil.nedssDir + "nnd" + File.separator ;
   public static final String NND_ARCHIVE_DIRECTORY = NND_ROOT_DIR + "MessageArchive" + File.separator;
   public static final String NND_ARCHIVE_DIRECTORY_COMPLETED = NND_ROOT_DIR + "MessageArchiveCompleted"+ File.separator;
   public static final String NND_ARCHIVE_DIRECTORY_FAILED = NND_ROOT_DIR + "MessageArchiveFailed"+ File.separator;
   public static final String NND_LDF_ARCHIVE_DIRECTORY = NND_ROOT_DIR + "ldf" + File.separator + "messageArchive"+ File.separator;
   //1.1 Constants to declare PHC's AR TypeCd
   public static final String PHC_LAB_AR_TYPECD = "LabReport";
   public static final String PHC_MRB_AR_TYPECD = "MorbReport";
   public static final String PHC_VAC_AR_TYPECD = "1180";
   public static final String PHC_TRT_AR_TYPECD = "TreatmentToPHC";
   public static final String PHC_NTF_AR_TYPECD = "Notification";

   //Lab OrderedTest's CtrlCdDisplay and ObsDomainCdSt1
   public static final String LAB_OT_CTRLCD_DSPLYFRM = "LabReport";
   public static final String LAB_OT_OBSDCD_ST1 = "Order";
   public static final String LAB_RT_OBSDCD_ST1 = "Result";

   //THE FOLLOWING ARE USED IN NNDLDFExtraction.java to set values for TransportQOutDT.
   public static final String PROCESSING_STATUS = "queued";

   //the following are used in nndldfextraction.java to set values in the nndActivityLogDt object
   public static final String NND_ACTIVITY_LOG_STATUS_CD_SUCCESS = "S";
   public static final String NND_ACTIVITY_LOG_STATUS_CD_FAILURE = "F";
   public static final String DEFAULT_LOCAL_ID = "1";

   public static final String NND_LDF_MESSAGE_FILE_NAME_PREFIX = "NND_LDF_Message_";
   public static final String NND_BATCH_TO_CDC = "nnd_batch_to_cdc";
   public static final String LDF_METADATA_EJB_METHOD_SET_NND_LDF_PROCESS = "setNNDLdfProcess";
   public static final String LDF_METADATA_EJB_METHOD_GET_ALL_LDF_META_DATA = "getAllLDFMetaData";
   public static final String CHECK_TRANSPORT_Q_OUT_DONE = "checkTransportQOutDone";
   public static final String PERSIST_NND_ACTIVITY = "persistNNDActivity";
   public static final String STATE_SITE_CODE_NOT_SET = "STATE_SITE_CODE not set in Nedss.properties.\nNo message will be migrated on ";
   public static final String STATE_SITE_CODE_NOT_SET_PRINT_OUT = "STATE_SITE_CODE not set in Nedss.properties.\nNo message will be migrated on ";
   public static final String YES = "Y";
   public static final String NO = "N";

   public static final String LOCAL = "L";
   
   // The following constants used in NNDPamMessageHelper to identify the table's that need specific where clauses for query
   public static final String PUBLIC_HEALTH_CASE_TABLE = "Public_health_case";
   public static final String ENTITY_LOCATOR_PART_TABLE = "Entity_locator_participation";
   public static final String POSTAL_LOCATOR_TABLE = "Postal_locator";
   public static final String TELE_LOCATOR_TABLE = "Tele_locator";
   public static final String ACT_ID_TABLE = "Act_id";
   public static final String PERSON_RACE_TABLE = "Person_race";
   public static final String PERSON_RACE_TABLE_RACE_CATEGORY_CD_COLUMN = "race_category_cd";
   public static final String PERSON_RACE_TABLE_RACE_CD_COLUMN = "race_cd";
   public static final String NOTIFICATION_TABLE = "Notification";
   
   
   public static final String ABCSTATE_ABCQUESTION = "ABCSTATE_ABCQUESTION";

   public static final String QUESTION_TO_BATCH = "QuestionToBatch";
   public static final String MULTI_TO_BATCH = "MultiToBatch";
   public static final String SELECT_TO_NUMERIC = "SelectToNumeric";
   public static final String DISCRETE_TO_REPEATING = "DiscreteToRepeating";
   public static final String REPEATING_TO_REPEATING = "RepeatingToRepeating";
   public static final String HEP255 = "HEP255"; //Birth Place
   public static final String HEP255_DESC = "Birth Country Cd";
   public static final String BIRTH_CNTRY_CD_DESC = "Birth Country Cd";
   public static final String DEM126 = "DEM126";

   public static  boolean notNull(Object obj)
   {
    if (obj == null  || obj.equals(null))
       return false;
    else
       return true;

   }




}
