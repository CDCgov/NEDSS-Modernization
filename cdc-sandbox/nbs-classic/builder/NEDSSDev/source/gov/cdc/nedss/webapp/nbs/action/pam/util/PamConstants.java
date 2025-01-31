package gov.cdc.nedss.webapp.nbs.action.pam.util;

/**
 * PamConstants defines the generic MetaData common across PAMs
 * @author nmallela
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: Computer Sciences Corporation</p>
 * PamConstants.java
 * Aug 5, 2008
 * @updatedByAuthor Pradeep Sharma
 * @company: SAIC
 * @version 4.5
 */
public class PamConstants {

	//Person_Name Constants
	public static final String LAST_NM = "DEM102";
	public static final String FIRST_NM = "DEM104";
	public static final String MIDDLE_NM= "DEM105";
	public static final String SUFFIX= "DEM107";
	public static final String NAME_INFORMATION_AS_OF = "DEM206";

	//Person Constants
	public static final String DEM_DATA_AS_OF = "DEM215";
	public static final String DOB= "DEM115";
	public static final String REP_AGE= "DEM216";
	public static final String REP_AGE_UNITS= "DEM218";
	public static final String CURR_SEX= "DEM113";
	public static final String ADDITIONAL_GENDER="NBS213";
	public static final String IS_PAT_DECEASED= "DEM127";
	public static final String DECEASED_DATE= "DEM128";
	public static final String MAR_STAT= "DEM140";
	public static final String ETHNICITY= "DEM155";
	public static final String PATIENT_LOCAL_ID = "DEM197";
	public static final String BIRTH_SEX= "DEM114";
	public static final String SEX_AND_BIRTH_INFORMATION_AS_OF = "DEM207";
	public static final String MORTALITY_INFORMATION_AS_OF = "DEM208";
	public static final String MARITAL_STATUS_AS_OF = "DEM209";
	public static final String ETHNICITY_AS_OF = "DEM211";
	public static final String BIRTH_TIME_CALC = "DEM121";
	
	
	//ContactTracing Specific 
	public static final String ALIAS_NICK_NAME = "DEM250";
	public static final String PRIMARY_OCCUPATION = "DEM139";
	public static final String BIRTH_COUNTRY = "DEM126";
	public static final String PRIMARY_LANGUAGE = "DEM142";
	public static final String CELL_PHONE = "DEM251";
	public static final String EMAIL = "DEM252";

	
	//Entity_ID Constants
	public static final String SSN= "DEM133";
	public static final String SSN_AS_OF = "DEM210";

	//Entity_locator_participation Constants
	public static final String ADDRESS_INFORMATION_AS_OF = "DEM213";
	public static final String TELEPHONE_INFORMATION_AS_OF = "DEM214";

	//Postal_Locator Constants
	public static final String ADDRESS_1= "DEM159";
	public static final String ADDRESS_2= "DEM160";
	public static final String CITY= "DEM161";
	public static final String STATE= "DEM162";
	public static final String COUNTY= "DEM165";
	public static final String CENSUS_TRACT= "DEM168";
	public static final String ZIP= "DEM163";
	public static final String COUNTRY= "DEM167";
	public static final String WITHIN_CITY_LIMITS= "DEM237";

	//Tele_Locator Constants
	public static final String H_PHONE= "DEM238";
	public static final String H_PHONE_EXT= "DEM239";
	public static final String W_PHONE= "DEM240";
	public static final String W_PHONE_EXT= "DEM241";

	//Person_race Constants
	public static final String RACE= "DEM152";
	public static final String DETAILED_RACE_ASIAN= "DEM243";
	public static final String DETAILED_RACE_HAWAII= "DEM245";
	public static final String RACE_INFORMATION_AS_OF = "DEM212";

	//Act_Id Constants
	public static final String STATE_CASE = "INV173";
	public static final String COUNTY_CASE = "INV198";

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
	public static final String SHARED_IND = "INV174";
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
	
	//contact tracing
	public static final String CONTACT_PRIORITY = "INV257";
	public static final String INFECTIOUS_PERIOD_FROM = "INV258";
	public static final String INFECTIOUS_PERIOD_TO = "INV259";
	public static final String CONTACT_STATUS = "INV260";
	public static final String CONTACT_COMMENTS = "INV261";	
	

}
