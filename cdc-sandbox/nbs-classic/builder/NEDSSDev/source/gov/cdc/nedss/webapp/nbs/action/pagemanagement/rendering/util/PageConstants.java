package gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util;

/**
 * PageConstants defines the generic MetaData common across PAMs
 * @author nmallela
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: Computer Sciences Corporation</p>
 * PageConstants.java
 * Aug 5, 2008
 * @version
 * @updateBy: Pradeep Sharma
 * Updated to include lab specific elements
 */
public class PageConstants {
	
	//public static final String PERSON_PARENT_UID="NBS470";
	
	public static final String RETAIN_PATIENT = "NBS_LAB223";
	public static final String RETAIN_REPORTING_FACILITY = "NBS_LAB224";
	//Person_Name Constants
	public static final String LAST_NM = "DEM102";
	public static final String FIRST_NM = "DEM104";
	public static final String MIDDLE_NM= "DEM105";
	public static final String SUFFIX= "DEM107";
	public static final String NAME_INFORMATION_AS_OF = "NBS095";

	//Person Constants
	public static final String DEM_DATA_AS_OF = "NBS104";
	public static final String DOB= "DEM115";
	public static final String REP_AGE= "INV2001";
	public static final String REP_AGE_UNITS= "INV2002";
	public static final String CURR_SEX= "DEM113";
	public static final String ADDITIONAL_GENDER="NBS213";
	public static final String SEX_UNKNOWN_REASON="NBS272";
	public static final String TRANSGENDER_INFORMATION="NBS274";

	public static final String IS_PAT_DECEASED= "DEM127";
	public static final String DECEASED_DATE= "DEM128";
	public static final String MAR_STAT= "DEM140";
	public static final String ETHNICITY= "DEM155";
	public static final String ETHNICITY_UNK_REASON= "NBS273";
	public static final String PATIENT_LOCAL_ID = "DEM197";
	public static final String BIRTH_SEX= "DEM114";
	public static final String SEX_AND_BIRTH_INFORMATION_AS_OF = "NBS096";
	public static final String MORTALITY_INFORMATION_AS_OF = "NBS097";
	public static final String MARITAL_STATUS_AS_OF = "NBS098";
	public static final String ETHNICITY_AS_OF = "NBS100";
	public static final String BIRTH_TIME_CALC = "DEM121";
	public static final String EHARS_ID = "NBS269";
	public static final String HEIGHT = "NBS155";
	public static final String SIZE_BUILD = "NBS156";
	public static final String HAIR = "NBS157";
	public static final String COMPLEXION = "NBS158";
	public static final String OTHER_IDENTIFYING_INFORMATION = "NBS159";

	//COntactTracing Specific
	public static final String ALIAS_NICK_NAME = "DEM250";
	public static final String PRIMARY_OCCUPATION = "DEM139";
	public static final String BIRTH_COUNTRY = "DEM126";
	public static final String PRIMARY_LANGUAGE = "DEM142";
	public static final String SPEAKS_ENGLISH = "NBS214";
	public static final String CELL_PHONE = "NBS006";
	public static final String EMAIL = "DEM182";
	//Entity_ID Constants
	public static final String SSN= "DEM133";
	public static final String SSN_AS_OF = "NBS451";

	//Entity_locator_participation Constants
	public static final String ADDRESS_INFORMATION_AS_OF = "NBS102";
	public static final String TELEPHONE_INFORMATION_AS_OF = "NBS103";

	//Postal_Locator Constants
	public static final String ADDRESS_1= "DEM159";
	public static final String ADDRESS_2= "DEM160";
	public static final String CITY= "DEM161";
	public static final String STATE= "DEM162";
	public static final String COUNTY= "DEM165";
	public static final String REPORTING_COUNTY= "AR109";
	public static final String CENSUS_TRACT= "DEM168";
	public static final String ZIP= "DEM163";
	public static final String COUNTRY= "DEM167";
	public static final String WITHIN_CITY_LIMITS= "DEM237";
	public static final String ADDRESS_COMMENTS = "DEM175";


	//Tele_Locator Constants
	public static final String H_PHONE= "DEM177";
	public static final String H_PHONE_EXT= "DEM239";
	public static final String W_PHONE= "NBS002";
	public static final String W_PHONE_EXT= "NBS003";



	//Person_race Constants
	public static final String RACE= "DEM152";
	public static final String DETAILED_RACE_ASIAN= "DEM243";
	public static final String DETAILED_RACE_HAWAII= "DEM245";
	public static final String DETAILED_RACE_WHITE= "DEM246";
	public static final String DETAILED_RACE_AFRICAN_AMERICAN= "DEM244";
	public static final String DETAILED_RACE_AMERICAN_INDIAN_AND_ALASKA_NATIVE= "DEM242";
	public static final String RACE_INFORMATION_AS_OF = "NBS101";

	//Act_Id Constants
	public static final String STATE_CASE = "INV173";
	public static final String COUNTY_CASE = "INV198";
	public static final String LEGACY_CASE_ID = "INV200";
	public static final String FILLER_ORDR_NBR_ACCESSION_NUMBER = "LAB125";
	public static final String MESSAGE_CTRL_ID_NUMBER = "NBS_LOG101";
	public static final String SPECIMEN_SOURCE="LAB165";
	public static final String SPECIMEN_DETAILS="NBS_LAB262";

	
	//Public_Health_Case Constants
	public static final String GEN_COMMENTS= "DEM196";
	public static final String JURISDICTION = "INV107";
	public static final String PROGRAM_AREA = "INV108";
	public static final String INV_STATUS_CD = "INV109";
	public static final String DATE_REPORTED = "INV111";
	public static final String INV_START_DATE = "INV147";
	public static final String MMWR_WEEK = "INV165";
	public static final String MMWR_YEAR = "INV166";
	public static final String CASE_LOCAL_ID = "INV168";
	public static final String DATE_REPORTED_TO_COUNTY = "INV120";
	public static final String DATE_REPORTED_TO_STATE = "INV121";
	public static final String DIAGNOSIS_DATE = "INV136";
	public static final String ILLNESS_ONSET_DATE = "INV137";
	public static final String PAT_AGE_AT_ONSET_UNIT_CODE = "INV144";
	public static final String PAT_AGE_AT_ONSET = "INV143";
	public static final String DATE_ASSIGNED_TO_INVESTIGATION = "INV110";
	public static final String WAS_THE_PATIENT_HOSPITALIZED = "INV128";
	public static final String ADMISSION_DATE = "INV132";
	public static final String DISCHARGE_DATE = "INV133";
	public static final String DURATION_OF_STAY = "INV134";
	public static final String PREGNANCY_STATUS = "INV178";
	public static final String PREGNANT_WEEKS = "NBS128";

	public static final String DID_THE_PATIENT_DIE = "INV145";
	public static final String IS_PERSON_ASSOCIATED_WITH_DAYCAREFACILITY = "INV148";
	public static final String IS_THIS_PERSON_FOOD_HANDLER = "INV149";
	public static final String IMPORTED_COUNTRY = "INV153";
	public static final String IMPORTED_STATE = "INV154";
	public static final String IMPORTED_CITY = "INV155";
	public static final String IMPORTED_COUNTY  = "INV156";
	public static final String INVESTIGATION_DEATH_DATE ="INV146";
	public static final String OUTBREAK_INDICATOR ="INV150";
	public static final String OUTBREAK_NAME ="INV151";

	// The following field should be converted to INV-
	public static final String CASE_ADD_TIME = "INV194";
	public static final String CASE_ADD_USERID = "INV195";
	//public static final String CASE_LC_USERTIME = "TUB251";
	//public static final String CASE_LC_USERID = "TUB252";
	public static final String SHARED_IND = "NBS012";
	//public static final String CASE_ADD_TIME = "TUB261";
	public static final String CONDITION_CD = "INV169";
	public static final String RECORD_STATUS_CD = "INV230";
	public static final String RECORD_STATUS_TIME = "INV234";
	public static final String STATUS_CD = "INV235";
	public static final String PROGRAM_JURISDICTION_OID = "INV213";
	public static final String VERSION_CTRL_NBR = "INV237";
	public static final String CASE_CLS_CD = "INV163";
	public static final String TUB_GEN_COMMENTS = "INV167";
	//Common Constants
	public static final String REQ_FOR_NOTIF = "REQ_FOR_NOTIF";
	public static final String NO_REQ_FOR_NOTIF_CHECK = "NO_REQ_FOR_NOTIF_CHECK";
	public static final String FIELD_LIST_TO_HILIGHT = "field_list_to_hilight";

	//R2.0.2 Additions
	public static final String REPORTING_SOURCE = "INV112";
	public static final String ILLNESS_END_DATE = "INV138";
	public static final String ILLNESS_DURATION = "INV139";
	public static final String ILLNESS_DURATION_UNITS = "INV140";
	public static final String DISEASE_IMPORT_CD = "INV152";
	public static final String TRANSMISN_MODE_CD = "INV157";
	public static final String DETECTION_METHOD_CD = "INV159";
	public static final String CONFIRM_METHOD_CD = "INV161";
	public static final String CONFIRM_DATE = "INV162";

	//R4.5 Additions

	public static final String REFERRAL_BASIS_CD="NBS110";
	public static final String CURR_PROCESS_STAGE_CD="NBS115";
	public static final String INV_CLOSED_DATE="INV2006";
	//public static final String INV_PRIORITY_CD="";

	//contact tracing
	public static final String CONTACT_PRIORITY = "NBS055";
	public static final String INFECTIOUS_PERIOD_FROM = "NBS056";
	public static final String INFECTIOUS_PERIOD_TO = "NBS057";
	public static final String CONTACT_STATUS = "NBS058";
	public static final String CONTACT_COMMENTS = "NBS059";

	//interview
	public static final String INTERVIEW_STATUS = "IXS100";
	public static final String INTERVIEW_DATE = "IXS101";
	public static final String INTERVIEWER = "IXS102";
	public static final String INTERVIEWEE_ROLE = "IXS103";
	public static final String INTERVIEWEE = "IXS104";
	public static final String INTERVIEW_TYPE = "IXS105";
	public static final String INTERVIEW_LOCATION = "IXS106";
	public static final String INTERVIEW_NOTES = "IXS111";
	public static final String HIV900_SiteId = "IXS107";
	public static final String HIV900_StateZipCd = "IXS108";
	public static final String HIV900_SiteType = "IXS109";
	public static final String HIV_INTERVENTIONS = "IXS110";
	
	
	//Lab
	public static final String 	LAB_JURISDICTION_CD	="INV107";
	public static final String 	LAB_PROG_AREA_CD	="INV108";
	public static final String 	LAB_PREGNANT_IND_CD	="INV178";
	public static final String 	LAB_NUMERIC_UNIT_CD	="LAB115";
	public static final String 	LAB_EFFECTIVE_FROM_TIME	="LAB163";
	public static final String 	LAB_CD_DESC_TXT	="NBS_LAB112";
	public static final String 	LAB_REASON_CD	="NBS_LAB124";
	public static final String 	LAB_TARGET_SITE_CD	="NBS_LAB166";
	public static final String 	LAB_STATUS_CD	="NBS_LAB196";
	public static final String 	LAB_ACTIVITY_TO_TIME	="NBS_LAB197";
	public static final String 	LAB_RPT_TO_STATE_TIME	="NBS_LAB201";
	public static final String 	LAB_VALUE_TXT	="NBS_LAB214";
	public static final String 	LAB_COMMENT_214	="LAB214";
    public static final String NBS460 ="NBS460";
    public static final String ELR_ROOT_OBS_COMMENT="NBS_LAB261";
    public static final String ELR_DANGER_DESC= "NBS_LAB316";
    public static final String 	LAB_TXT	="NBS_LAB261";
	public static final String 	LAB_PRIORITY_CD	="NBS_LAB264";
	public static final String 	LAB_ORDERED_TEST_CD	="NBS_LAB269";
	public static final String 	LAB_LOCAL_ID	="NBS_LAB317";
	public static final String 	PATIENT_STATUS_AT_SPECIMEN_COLLECTION	="NBS_LAB330";
	public static final String 	LAB_COMMENT	="LAB_COMMENT";
	public static final String 	LAB_RESULT_COMMENT	="LAB_RESULT_COMMENT";
	
	
	public static final String 	LAB_SHARED_IND	="NBS012";
	public static final String 	LAB_PREGNANT_WEEK	="NBS128";
	public static final String 	LAB_PATIENT="NBS432";
	public static final String 	LAB_ELR_IND="NBS455";
	public static final String 	LAB_RSLT_ELR_IND="NBS457";
	public static final String LAB_REPORTING_FACILITY="NBS_LAB365";
	public static final String 	RESULTED_TEST_BATCH_CONTAINER="RESULTED_TEST_CONTAINER";
	
//	public static final String 	LAB_ISOLATE_SENT_TO_CDC	="NBS_LAB363";
    public static final String LAB_ROOT = "LAB_ROOT";
    //LAB RESULTED TEST ELEMENTS
    public static final String LAB_RESULTED_TEST = "LAB_RESULTED_TEST";
    public static final String LAB_NBS_LAB217="NBS_LAB217";
    public static final String LAB_NBS_LAB293="NBS_LAB293";
    public static final String NBS_LAB293_1="NBS_LAB293_1";
    public static final String NBS_LAB121="NBS_LAB121";
   // public static final String NBS_LAB278="NBS_LAB278";
    public static final String NBS_LAB280="NBS_LAB280";
    public static final String NBS_LAB120="NBS_LAB120";
    public static final String NBS_LAB119="NBS_LAB119";
    public static final String LAB115="LAB115";
    public static final String NBS_LAB364="NBS_LAB364";
    public static final String NBS_LAB104="NBS_LAB104";
    public static final String NBS_LAB208="NBS_LAB208";
    public static final String NBS_LAB217="NBS_LAB217";
    public static final String NBS_LAB293="NBS_LAB293";
    public static final String NBS_LAB220="NBS_LAB220";
    public static final String NBS_LAB279="NBS_LAB279";
    public static final String RT_LEGACY_LAB279="LAB279";
    public static final String NBS_LAB207="NBS_LAB207";
    public static final String NBS_LAB118="NBS_LAB118";
    public static final String NBS458="NBS458";
    public static final String NBS457="NBS457";
    public static final String NBS373="NBS373";
    public static final String NBS374="NBS374";
    public static final String CODEID="CodeId";
    public static final String DESCRIPTIONID="DescriptionId";
    public static final String DESCRIPTION="Description";
    
    

//
    public static final String legalName_NBS462="NBS462";
    public static final String AliaslegalName_NBS463="NBS463";
    
    public static final String AR_MAP_FOR_LAB = "AR_MAP_FOR_LAB";
    public static final String ISOLATE_AND_SUSCEPTABILITY_TEST_VO = "ISOLATE_AND_SUSCEPTABILITY_TEST_VO";
      public static final String ISOLATE_AND_SUSCEPTABILITY_TEST_AR = "ISOLATE_AND_SUSCEPTABILITY_TEST_AR";
    public static final String SUSCEPTABILITY_TEST = "SUSCEPTABILITY_TEST";
    public static final String NBS_LAB110="NBS_LAB110";
    public static final String NBS_LAB110CodeId="NBS_LAB110CodeId";
    public static final String NBS367="NBS367";
    public static final String NBS375="NBS375";
    public static final String NBS365="NBS365";
    public static final String NBS369="NBS369";
    public static final String NBS372="NBS372";
    public static final String NBS377="NBS377";
    public static final String SUS_LEGACY_LAB279="LAB279";
    public static final String NBS405="NBS405";
    public static final String NEW_NBS405="NBS405";
    public static final String LAB110="LAB110";
    public static final String NEW_LAB110="NBS_LAB110";
    public static final String LEGACY_NBS376 ="NBS376";
    public static final String TRACK_ISOLATE_TEST = "TRACK_ISOLATE_TEST";
    public static final String TRACK_329_VO_TEST = "TRACK_329_VO_TEST";
    public static final String NBS376="NBS376";
    public static final String NBS378 ="NBS378";
    public static final String LAB_COLL_VOL = "NBS_LAB265";
    public static final String LAB_COLL_VOL_UNITS = "NBS_LAB313";
	//Case Management

	//STD 4.5
	public static final String CASE_DIAGNOSIS = "NBS136";
	public static final String CASE_DIAGNOSIS_CODESET = "CASE_DIAGNOSIS";
	public static final String DIAGNOSIS_CAN_CHANGE_CONDITION[] =  {"710","720","730","740","745","750","755","790","900","950"};


	public static final String INTERNET_FOLLOWUP = "NBS142";
	public static final String NOTIFIABLE = "NBS143";

	public static final String INVESTIGATOR_UID = "CONINV180Uid";
	public static final String INVESTIGATOR_SEARCH_RESULT = "CONINV180SearchResult";
	
	public static final String VACC_INFO_SOURCE_CD="VAC147";
	public static final String MATERIAL_CD="VAC101";
	public static final String INTERVENTION_LOCAL_ID="VAC102";
	public static final String ACTIVITY_FROM_TIME="VAC103";
	public static final String TARGET_SITE_CD="VAC104";
	public static final String AGE_AT_VACC="VAC105";
	public static final String AGE_AT_VACC_UNIT_CD="VAC106";
	public static final String VACC_MFGR_CD="VAC107";
	public static final String MATERIAL_LOT_NM="VAC108";
	public static final String MATERIAL_EXPIRATION_TIME="VAC109";
	public static final String VACC_DOSE_NBR="VAC120";
	
	public static final String EVENT_SUMMARY_LOCAL_ID="LocalId";
	public static final String EVENT_SUMMARY_CREATED_ON="CreatedOn";
	public static final String EVENT_SUMMARY_CREATED_BY="CreatedBy";
	public static final String EVENT_SUMMARY_UPDATED_ON="UpdatedOn";
	public static final String EVENT_SUMMARY_UPDATED_BY="UpdatedBy";
	
	public static final String EVENT_SUMMARY_PROCESSING_DECISION="ProcessingDecision";
	public static final String EVENT_SUMMARY_PROCESSING_DECISION_NOTES="ProcessingDecisionNotes";
	
	public static final String VACCINE_TYPE="VaccineType";
	
	public static final String ELECTRONIC_IND="ElectronicInd";
	
	public static final String EVENT_SUMMARY_ACCESSION_NUMBER="AccessionNumber";
	public static final String EVENT_SUMMARY_COLLECTION_DATE="CollectionDate";
	public static final String EVENT_SUMMARY_LAB_REPORT_DATE="LabReportDate";
	public static final String EVENT_SUMMARY_DATE_RECEIVED_BY_PUBLIC_HEATLH="DateReceivedByPublicHealth";
	public static final String EVENT_SUMMARY_SSN = "SSN";
	public static final String EVENT_SUMMARY_ADDRESS = "Address";
	public static final String EVENT_SUMMARY_PATIENT_ID = "PatientId";
	public static final String EVENT_SUMMARY_PERSON_AGE = "PersonAge";
	public static final String EVENT_SUMMARY_FULL_NAME = "FullName";
	public static final String EVENT_SUMMARY_SEX = "Sex";
	public static final String BIRTH_ORDER_NBR_DEM117_B="DEM117_B";
	public static final String MULTIPLE_BIRTH_IND_DEM116_B="DEM116_B";
	//ENTITY ID Repeating Block
	
	public static final String ENTITY_ID_SUBSECTION="ENTITYID100";
	public static final String PATIENT_ENTITY_ID_TYPE="DEM144";
	public static final String PATIENT_ENTITY_ID_ASSIGNING_AUTHORITY="DEM146";
	public static final String PATIENT_ENTITY_ID_VALUE="DEM147";
	public static final String PATIENT_ENTITY_ID_AS_OF="NBS452";
	
	//Participant related title
	public static final String COPY_TO_PROVIDER_TITLE="Copy to Provider";
	public static final String SPECIMEN_PROCURER_TITLE="Specimen Procurer";
	public static final String ALTERNATE_CONTACT_TITLE="Alternate Contact";
	
	//TrackIsolate questions list
	public static final String CODED_TR_LAB336 = "NBS_LAB336";
	public static final String CODED_TR_LAB351 = "NBS_LAB351";
	public static final String CODED_TR_LAB355 = "NBS_LAB355";
	public static final String CODED_TR_LAB338 = "NBS_LAB338";
	public static final String CODED_TR_LAB331 = "NBS_LAB331";
	public static final String CODED_TR_LAB363 = "NBS_LAB363";
	public static final String CODED_TR_LAB346 = "NBS_LAB346";
	public static final String CODED_TR_LAB345 = "NBS_LAB345";
	public static final String CODED_TR_LAB337 = "NBS_LAB337";
	public static final String CODED_TR_LAB332 = "NBS_LAB332";
	public static final String CODED_TR_LAB347 = "NBS_LAB347";
	public static final String CODED_TR_LAB353 = "NBS_LAB353";
	public static final String CODED_TR_LAB359 = "NBS_LAB359";
	public static final String CODED_TR_LAB352 = "NBS_LAB352";
	public static final String CODED_TR_LAB358 = "NBS_LAB358";
	public static final String DATE_TR_LAB334 = "NBS_LAB334";
	public static final String DATE_TR_LAB362 = "NBS_LAB362";
	public static final String DATE_TR_LAB357 = "NBS_LAB357";
	public static final String DATE_TR_LAB361 = "NBS_LAB361";
	public static final String DATE_TR_LAB356 = "NBS_LAB356";
	public static final String DATE_TR_LAB350 = "NBS_LAB350";
	public static final String DATE_TR_LAB349 = "NBS_LAB349";
	public static final String TEXT_TR_LAB360 = "NBS_LAB360";
	public static final String TEXT_TR_LAB354 = "NBS_LAB354";
	public static final String TEXT_TR_LAB339 = "NBS_LAB339";
	public static final String TEXT_TR_LAB341 = "NBS_LAB341";
	public static final String TEXT_TR_LAB343 = "NBS_LAB343";
	public static final String TEXT_TR_LAB333 = "NBS_LAB333";
	public static final String TEXT_TR_LAB340 = "NBS_LAB340";
	public static final String TEXT_TR_LAB342 = "NBS_LAB342";
	public static final String TEXT_TR_LAB344 = "NBS_LAB344";
	public static final String TEXT_TR_LAB335 = "NBS_LAB335";
	public static final String TEXT_TR_LAB348 = "NBS_LAB348";

	
	public static final String RESULTED_TEST_BATCH_CONTAINER_MORB="NBS_UI_29";
	
}
