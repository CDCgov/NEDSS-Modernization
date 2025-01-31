package gov.cdc.nedss.nnd.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import noNamespace.Type01;
import noNamespace.Type0199;
import noNamespace.Type12;
import noNamespace.Type123;
import noNamespace.Type1289;
import noNamespace.TypeBirthGender;
import noNamespace.TypeClientRisk;
import noNamespace.TypeCurrentGender;
import noNamespace.TypeEthnicity;
import noNamespace.TypeHIVTestResult;
import noNamespace.TypeHeader;
import noNamespace.TypeHivStatus;
import noNamespace.TypeNotifiability;
import noNamespace.TypeNotificationMethod;
import noNamespace.TypeOtherRisk;
import noNamespace.TypeRaces;
import noNamespace.TypeReferralAttend;
import noNamespace.TypeSiteType;
import noNamespace.TypeState;
import noNamespace.TypeYesNoOther;

/**
 * PartnerServicesConstants - Helps populate the values in Partner Services schema elements.
 *  See PSData_v2.1.xsd. Many of the types are enum. The maps in this class translate the
 *  values from our HIV pages to the NMH&E data variables.
 * @author Gregory Tucker
 * <p>Copyright: Copyright (c) 2016</p>
 * <p>Company: CSRA for CDC</p>
 * Dec 22nd, 2016
 * @version 0.9
 */

public class PartnerServicesConstantsOld {
	
	public static final String PS_SCHEMA_VERSION_NUMBEROLD = "2.1";
	public static final String PS_SCHEMA_VERSION_NUMBER = "3.0";
	public static final String PS_DATA_TYPE = "PS";
	public static final String ELICIT_PARTNER_TIME_PERIOD = "12";
	public static final String LEGACY_ID_TYPE = "LEGACY";
	public static final String PREV_POS = "1";
	public static final String PREV_NEG_NEW_POS = "2";
	public static final String NO_PREV_NEW_POS = "5";
 	
	
	public static final Map<String, TypeHeader.DataType.Enum> 
	  HEADER_DATA_TYPE = 
		    Collections.unmodifiableMap(new HashMap<String, TypeHeader.DataType.Enum>() {{ 
		        put("PS", TypeHeader.DataType.Enum.forString("PS"));
		    }});
	
	
	public static final Map<String, TypeCurrentGender.Enum> 
	  CURRENT_SEX_MAP = 
		    Collections.unmodifiableMap(new HashMap<String, TypeCurrentGender.Enum>() {{ 
		        put("M", TypeCurrentGender.Enum.forString("1"));
		        put("F", TypeCurrentGender.Enum.forString("2"));
		        put("MtF", TypeCurrentGender.Enum.forString("3"));
		        put("FtM", TypeCurrentGender.Enum.forString("4"));
		        put("T", TypeCurrentGender.Enum.forString("5"));
		        put("R", TypeCurrentGender.Enum.forString("77"));
		        put("89", TypeCurrentGender.Enum.forString("89")); //other specify
		    }});
	
	public static final Map<String, TypeBirthGender.Enum> 
	   BIRTH_GENDER_MAP = 
		    Collections.unmodifiableMap(new HashMap<String, TypeBirthGender.Enum>() {{ 
		        put("M", TypeBirthGender.Enum.forString("1"));
		        put("F", TypeBirthGender.Enum.forString("2"));
		        //put("NASK", "66"); //Default. Did not asked doesn't map.
		        put("U", TypeBirthGender.Enum.forString("77")); //declined to ans map to Unknown
		        
		    }});
	
	public static final Map<String, TypeRaces.Race.RaceValueCode.Enum> 
    RACE_MAP = 
	    Collections.unmodifiableMap(new HashMap<String, TypeRaces.Race.RaceValueCode.Enum>() {{ 
	        put("1002-5", TypeRaces.Race.RaceValueCode.Enum.forString("R1")); //amer ind
	        put("2028-9", TypeRaces.Race.RaceValueCode.Enum.forString("R2")); //asian
	        put("2054-5", TypeRaces.Race.RaceValueCode.Enum.forString("R3")); //black
	        put("2076-8", TypeRaces.Race.RaceValueCode.Enum.forString("R4")); //hawaiian
	        put("2106-3", TypeRaces.Race.RaceValueCode.Enum.forString("R5")); //white
	        put("U", TypeRaces.Race.RaceValueCode.Enum.forString("99")); //unknown
	        put("PHC1175", TypeRaces.Race.RaceValueCode.Enum.forString("77")); //refused
	    }});	
	
	public static final Map<String, TypeEthnicity.Enum> 
    RACE_CATAGORY_MAP = 
	    Collections.unmodifiableMap(new HashMap<String, TypeEthnicity.Enum>() {{ 
	        put("2135-2", TypeEthnicity.Enum.forString("E1")); //hisp
	        put("2186-5", TypeEthnicity.Enum.forString("E2")); //not
	        put("U", TypeEthnicity.Enum.forString("99")); //unknown
	    }});
	
	public static final Map<String, TypeState.Enum> 
    STATE_MAP = 
	    Collections.unmodifiableMap(new HashMap<String, TypeState.Enum>() {{ 
	        put("1", TypeState.Enum.forString("1")); //AL
	        put("01", TypeState.Enum.forString("01")); //AL
	        put("2", TypeState.Enum.forString("2")); //AK
	        put("02", TypeState.Enum.forString("02")); //AK
	        put("4", TypeState.Enum.forString("4")); //AZ
	        put("04", TypeState.Enum.forString("04")); //AZ
	        put("5", TypeState.Enum.forString("5")); //AR
	        put("05", TypeState.Enum.forString("05")); //AR
	        put("6", TypeState.Enum.forString("6")); //CA
	        put("06", TypeState.Enum.forString("06")); //CA
	        put("8", TypeState.Enum.forString("8")); //CO
	        put("08", TypeState.Enum.forString("08")); //CO
	        put("9", TypeState.Enum.forString("9")); //CT
	        put("09", TypeState.Enum.forString("09")); //CT
	        put("10", TypeState.Enum.forString("10")); //DE
	        put("12", TypeState.Enum.forString("12")); //FL
	        put("13", TypeState.Enum.forString("13")); //GA
	        put("15", TypeState.Enum.forString("15")); //HI
	        put("16", TypeState.Enum.forString("16")); //ID
	        put("17", TypeState.Enum.forString("17")); //IL
	        put("18", TypeState.Enum.forString("18")); //IN
	        put("19", TypeState.Enum.forString("19")); //IA
	        put("20", TypeState.Enum.forString("20")); //KS
	        put("21", TypeState.Enum.forString("21")); //KY
	        put("22", TypeState.Enum.forString("22")); //LA
	        put("23", TypeState.Enum.forString("23")); //MD
	        put("24", TypeState.Enum.forString("24")); //ME
	        put("25", TypeState.Enum.forString("25")); //MA
	        put("26", TypeState.Enum.forString("26")); //MI
	        put("27", TypeState.Enum.forString("27")); //MN
	        put("28", TypeState.Enum.forString("28")); //MS
	        put("29", TypeState.Enum.forString("29")); //MO
	        put("30", TypeState.Enum.forString("30")); //MT
	        put("31", TypeState.Enum.forString("31")); //NE
	        put("32", TypeState.Enum.forString("32")); //NV
	        put("33", TypeState.Enum.forString("33")); //NH
	        put("34", TypeState.Enum.forString("34")); //NJ
	        put("35", TypeState.Enum.forString("35")); //NM
	        put("36", TypeState.Enum.forString("36")); //NY
	        put("37", TypeState.Enum.forString("37")); //NC
	        put("38", TypeState.Enum.forString("38")); //ND
	        put("39", TypeState.Enum.forString("39")); //OH
	        put("40", TypeState.Enum.forString("40")); //OK
	        put("41", TypeState.Enum.forString("41")); //OR
	        put("42", TypeState.Enum.forString("42")); //PA
	        put("44", TypeState.Enum.forString("44")); //RI
	        put("45", TypeState.Enum.forString("45")); //SC
	        put("46", TypeState.Enum.forString("46")); //SD
	        put("47", TypeState.Enum.forString("47")); //TN
	        put("48", TypeState.Enum.forString("48")); //TX
	        put("49", TypeState.Enum.forString("49")); //UT
	        put("50", TypeState.Enum.forString("50")); //VT
	        put("51", TypeState.Enum.forString("51")); //VA
	        put("53", TypeState.Enum.forString("53")); //WA
	        put("55", TypeState.Enum.forString("55")); //WI
	        put("56", TypeState.Enum.forString("56")); //WY
	        put("11", TypeState.Enum.forString("11")); //DC
	        put("72", TypeState.Enum.forString("72")); //PR
	        put("60", TypeState.Enum.forString("60")); //AS
	        put("64", TypeState.Enum.forString("64")); //FM
	        put("66", TypeState.Enum.forString("66")); //GU
	        put("69", TypeState.Enum.forString("69")); //MH
	        put("70", TypeState.Enum.forString("70")); //PW
	        put("78", TypeState.Enum.forString("78")); //VI
	        put("88", TypeState.Enum.forString("88")); //Other
	    }});
	
	
	//per CDC blank is no longer accepted
	public static final Map<String, TypeClientRisk.Enum> 
    NO_CLIENT_RISK_FACTORS = 
	    Collections.unmodifiableMap(new HashMap<String, TypeClientRisk.Enum>() {{ 
	        put("blank", TypeClientRisk.Enum.forString("66")); //blank=not asked
	        put("1", TypeClientRisk.Enum.forString("1")); //completed
	        put("5", TypeClientRisk.Enum.forString("5")); //asked no risk
	        put("66", TypeClientRisk.Enum.forString("66")); // not asked
	        put("77", TypeClientRisk.Enum.forString("77")); // declined to ans
	    }});
	
	
	//see codeset NBS_SITE_TYPE_HIV
	public static final Map<String, TypeSiteType.Enum> 
    SITE_TYPES_MAP = 
	    Collections.unmodifiableMap(new HashMap<String, TypeSiteType.Enum>() {{ 
	        put("blank", TypeSiteType.Enum.forString("")); //blank
	        put("F01.01", TypeSiteType.Enum.forString("F01.01")); //Inpatient Hosp
	        put("F02.12", TypeSiteType.Enum.forString("F02.12")); //TB Clinic
	        put("F02.19", TypeSiteType.Enum.forString("F02.19")); //Drug Treatment Facility
	        put("F02.51", TypeSiteType.Enum.forString("F02.51")); //Community Health
	        put("F03", TypeSiteType.Enum.forString("F03")); // Emergency Treatment
	        put("F04.05", TypeSiteType.Enum.forString("F04.05")); //etc..
	        put("F06.02", TypeSiteType.Enum.forString("F06.02")); 
	        put("F06.03", TypeSiteType.Enum.forString("F06.03")); 
	        put("F06.04", TypeSiteType.Enum.forString("F06.04")); 
	        put("F06.05", TypeSiteType.Enum.forString("F06.05")); 
	        put("F06.07", TypeSiteType.Enum.forString("F06.07")); 
	        put("F06.08", TypeSiteType.Enum.forString("F06.08")); 
	        put("F06.12", TypeSiteType.Enum.forString("F06.12")); 	        
	        put("F06.88", TypeSiteType.Enum.forString("F06.88")); 
	        put("F07", TypeSiteType.Enum.forString("F07")); 
	        put("F08", TypeSiteType.Enum.forString("F08")); 
	        put("F09", TypeSiteType.Enum.forString("F09")); 
	        put("F11", TypeSiteType.Enum.forString("F11")); 
	        put("F12", TypeSiteType.Enum.forString("F12")); 
	        put("F13", TypeSiteType.Enum.forString("F13")); 
	        put("F14", TypeSiteType.Enum.forString("F14")); 
	        put("F15", TypeSiteType.Enum.forString("F15")); 
	        put("F88", TypeSiteType.Enum.forString("F88"));   
	    }});
	
	public static final Map<String, String> YNR_MAP = 
		    Collections.unmodifiableMap(new HashMap<String, String>() {{ 
		        put("Y", "1"); //yes
		        put("N", "0"); //no
		        put("R", "77"); //declined to ans
		        put("U", "99"); //unk
		        put("D", "66"); //not ask
		        put("NASK", "66"); //not ask
		    }});
	
	public static final Map<String, Type0199.Enum>
	     INJECTION_DRUG_USE_MAP = 
		    Collections.unmodifiableMap(new HashMap<String, Type0199.Enum>() {{ 
		    	put("blank", Type0199.Enum.forString(""));
		        put("N", Type0199.Enum.forString("0"));
		        put("Y", Type0199.Enum.forString("1"));
		        put("UNK", Type0199.Enum.forString("99"));
		        put("R", Type0199.Enum.forString("99"));
	}});
	
	public static final Map<String, Type0199.Enum> SHARED_INJECTION_DRUG_USE_MAP = 
		    Collections.unmodifiableMap(new HashMap<String, Type0199.Enum>() {{ 
		    	put("blank", Type0199.Enum.forString(""));
		        put("N", Type0199.Enum.forString("0"));
		        put("Y", Type0199.Enum.forString("1"));
		        put("UNK", Type0199.Enum.forString("99"));
		        put("R", Type0199.Enum.forString("99"));
	}});
	
	public static final Map<String, Type0199.Enum> WithMaleLast12 = 
		    Collections.unmodifiableMap(new HashMap<String, Type0199.Enum>() {{ 
		    	put("blank", Type0199.Enum.forString(""));
		        put("N", Type0199.Enum.forString("0"));
		        put("O", Type0199.Enum.forString("0"));
		        put("R", Type0199.Enum.forString("0"));
		        put("U", Type0199.Enum.forString("0"));
		        put("D", Type0199.Enum.forString("0"));
		        put("Y", Type0199.Enum.forString("1"));
		        put("UNK", Type0199.Enum.forString("99"));
	}});
	
	public static final Map<String, Type0199.Enum> WithFemaleLast12 = 
		    Collections.unmodifiableMap(new HashMap<String, Type0199.Enum>() {{ 
		    	put("blank", Type0199.Enum.forString(""));
		        put("N", Type0199.Enum.forString("0"));
		        put("O", Type0199.Enum.forString("0"));
		        put("R", Type0199.Enum.forString("0"));
		        put("U", Type0199.Enum.forString("0"));
		        put("D", Type0199.Enum.forString("0"));
		        put("Y", Type0199.Enum.forString("1"));
		        put("UNK", Type0199.Enum.forString("99"));
	}});
	
	public static final Map<String, Type0199.Enum> WithTransLast12 = 
		    Collections.unmodifiableMap(new HashMap<String, Type0199.Enum>() {{ 
		    	put("blank", Type0199.Enum.forString(""));
		        put("N", Type0199.Enum.forString("0"));
		        put("O", Type0199.Enum.forString("0"));
		        put("R", Type0199.Enum.forString("0"));
		        put("U", Type0199.Enum.forString("0"));
		        put("D", Type0199.Enum.forString("0"));
		        put("Y", Type0199.Enum.forString("1"));
		        put("UNK", Type0199.Enum.forString("99"));
	}});
	
	public static final Map<String, Type0199.Enum> NoCondom = 
		    Collections.unmodifiableMap(new HashMap<String, Type0199.Enum>() {{ 
		    	put("blank", Type0199.Enum.forString(""));
		        put("N", Type0199.Enum.forString("0"));
		        put("O", Type0199.Enum.forString("0"));
		        put("R", Type0199.Enum.forString("0"));
		        put("U", Type0199.Enum.forString("0"));
		        put("D", Type0199.Enum.forString("0"));		        
		        put("Y", Type0199.Enum.forString("1"));
		        put("UNK", Type0199.Enum.forString("99"));
	}});
	
	public static final Map<String, Type0199.Enum> WithIDULast12 = 
		    Collections.unmodifiableMap(new HashMap<String, Type0199.Enum>() {{ 
		    	put("blank", Type0199.Enum.forString(""));
		        put("N", Type0199.Enum.forString("0"));
		        put("O", Type0199.Enum.forString("0"));
		        put("R", Type0199.Enum.forString("0"));
		        put("U", Type0199.Enum.forString("0"));
		        put("D", Type0199.Enum.forString("0"));
		        put("Y", Type0199.Enum.forString("1"));
		        put("UNK", Type0199.Enum.forString("99"));
	}});
	
	
	public static final Map<String, Type12.Enum> ATTEMPT_TO_LOCATE_OUTCOME_MAP = 
		    Collections.unmodifiableMap(new HashMap<String, Type12.Enum>() {{ 
		        put("U", Type12.Enum.forString("1"));
		        put("I", Type12.Enum.forString("2"));
		        put("A", Type12.Enum.forString("1"));
		        put("D", Type12.Enum.forString("1"));
		        put("J", Type12.Enum.forString("1"));
		        put("L", Type12.Enum.forString("1"));
		        put("N", Type12.Enum.forString("1"));
		        put("O", Type12.Enum.forString("1"));
		        put("P", Type12.Enum.forString("1"));
		        put("R", Type12.Enum.forString("1"));
	}});
	
	public static final Map<String, Type12.Enum> ENROLLMENT_STATUS = 
		    Collections.unmodifiableMap(new HashMap<String, Type12.Enum>() {{ 
		        put("1", Type12.Enum.forString("1"));
		        put("2", Type12.Enum.forString("2"));
		        
	}});	
	
	
	public static final Map<String, Type1289.Enum> REASON_UNABLE_TO_LOCATE_MAP = 
		    Collections.unmodifiableMap(new HashMap<String, Type1289.Enum>() {{ 
		        put("1", Type1289.Enum.forString("1")); //deceased
		        put("2", Type1289.Enum.forString("2")); //OOJ
		        put("01", Type1289.Enum.forString("01")); //deceased
		        put("02", Type1289.Enum.forString("02")); //OOJ
		        put("89", Type1289.Enum.forString("89"));//Other
		        put("88", Type1289.Enum.forString("89"));//Other
	}});
	
	public static final Map<String, Type123.Enum> PARTNER_TYPE_MAP = 
		    Collections.unmodifiableMap(new HashMap<String, Type123.Enum>() {{ 
		        put("P1", Type123.Enum.forString("1"));
		        put("P2", Type123.Enum.forString("2"));
		        put("P3", Type123.Enum.forString("3"));
	}});	
	public static final Map<String, String> PARTNER_NOTIFICATION_PLAN_MAP = 
		    Collections.unmodifiableMap(new HashMap<String, String>() {{ 
		        put("1", "1");
		        put("2", "2");
		        put("3", "3");
		        put("4", "4");
		        put("5", "5");		        
	}});
	
	public static final Map<String, TypeYesNoOther.Enum> PARTNER_PREVIOUS_HIV_TEST_MAP = 
		    Collections.unmodifiableMap(new HashMap<String, TypeYesNoOther.Enum>() {{ 
		        put("N", TypeYesNoOther.Enum.forString("0"));
		        put("Y", TypeYesNoOther.Enum.forString("1"));
		        put("NASK", TypeYesNoOther.Enum.forString("66"));
		        put("R", TypeYesNoOther.Enum.forString("77"));
		        put("U", TypeYesNoOther.Enum.forString("99"));
	}});	
	
	
	public static final Map<String, TypeNotifiability.Enum> PARTNER_NOTIFIABILITY_MAP = 
		    Collections.unmodifiableMap(new HashMap<String, TypeNotifiability.Enum>() {{
		    	put("", TypeNotifiability.Enum.forString("")); 
		        put("1", TypeNotifiability.Enum.forString("1"));
		        put("2", TypeNotifiability.Enum.forString("2"));
		        put("3", TypeNotifiability.Enum.forString("3"));
		        put("4", TypeNotifiability.Enum.forString("4"));
		        put("5", TypeNotifiability.Enum.forString("5"));
		        put("6", TypeNotifiability.Enum.forString("6"));
		        put("88", TypeNotifiability.Enum.forString("88")); //other
	}});
	
	public static final Map<String, TypeNotificationMethod.Enum> PARTNER_ACTUAL_NOTIFICATION_METHOD_MAP = 
		    Collections.unmodifiableMap(new HashMap<String, TypeNotificationMethod.Enum>() {{ 
		    	put("", TypeNotificationMethod.Enum.forString(""));
		        put("1", TypeNotificationMethod.Enum.forString("1"));
		        put("2", TypeNotificationMethod.Enum.forString("2"));
		        put("3", TypeNotificationMethod.Enum.forString("3"));
		        put("4", TypeNotificationMethod.Enum.forString("4"));
		        put("5", TypeNotificationMethod.Enum.forString("5"));
		        put("6", TypeNotificationMethod.Enum.forString("6"));
	}});
	
	public static final Map<String, TypeHivStatus.Enum> PARTNER_SELF_REPORTED_HIV_TEST_MAP = 
		    Collections.unmodifiableMap(new HashMap<String, TypeHivStatus.Enum>() {{
		    	put("", TypeHivStatus.Enum.forString(""));
		        put("1", TypeHivStatus.Enum.forString("1"));
		        put("2", TypeHivStatus.Enum.forString("2"));
		        put("3", TypeHivStatus.Enum.forString("3"));
		        put("4", TypeHivStatus.Enum.forString("4"));
		        put("66", TypeHivStatus.Enum.forString("66"));
		        put("77", TypeHivStatus.Enum.forString("77"));
		        put("99", TypeHivStatus.Enum.forString("99"));
	}});	
	//referred for 900 Test
	public static final Map<String, Type0199.Enum> REFERRED_TO_HIV_TESTING_MAP = 
		    Collections.unmodifiableMap(new HashMap<String, Type0199.Enum>() {{ 
		    	put("blank", Type0199.Enum.forString(""));
		        put("N", Type0199.Enum.forString("0"));
		        put("Y", Type0199.Enum.forString("1"));
	}});
	//referredToMedicalCare
	public static final Map<String, Type0199.Enum> REFERRED_TO_MEDICAL_CARE_MAP = 
		    Collections.unmodifiableMap(new HashMap<String, Type0199.Enum>() {{ 
		    	put("blank", Type0199.Enum.forString(""));
		        put("N", Type0199.Enum.forString("0"));
		        put("Y", Type0199.Enum.forString("1"));
	}});	
	
	//firstMedicalAppointment
	public static final Map<String, TypeReferralAttend.Enum> FIRST_MEDICAL_APPOINTMENT_MAP = 
		    Collections.unmodifiableMap(new HashMap<String, TypeReferralAttend.Enum>() {{ 
		    	put("1", TypeReferralAttend.Enum.forString("1"));
		        put("2", TypeReferralAttend.Enum.forString("2"));
		        put("3", TypeReferralAttend.Enum.forString("3"));
		        put("4", TypeReferralAttend.Enum.forString("4"));
		        put("5", TypeReferralAttend.Enum.forString("5"));
		        put("99", TypeReferralAttend.Enum.forString("99")); //Don't know
		        
	}});
	//HIV Test Performmed
	public static final Map<String, Type01.Enum> HIV_TEST_PERFORMED_MAP = 
		    Collections.unmodifiableMap(new HashMap<String, Type01.Enum>() {{ 
		    	put("blank", Type01.Enum.forString(""));
		        put("N", Type01.Enum.forString("0"));
		        put("Y", Type01.Enum.forString("1"));
	}});		
	
	//HIV Test Result
	public static final Map<String, TypeHIVTestResult.Enum> HIV_TEST_RESULT_MAP = 
		    Collections.unmodifiableMap(new HashMap<String, TypeHIVTestResult.Enum>() {{ 
		    	put("1", TypeHIVTestResult.Enum.forString("1"));
		        put("3", TypeHIVTestResult.Enum.forString("3"));
		        put("4", TypeHIVTestResult.Enum.forString("4"));
		        put("5", TypeHIVTestResult.Enum.forString("5"));
		        put("6", TypeHIVTestResult.Enum.forString("6"));
	}});	
	
	//HIV Test Results Provided
	public static final Map<String, Type01.Enum> HIV_TEST_RESULTS_PROVIDED_MAP = 
		    Collections.unmodifiableMap(new HashMap<String, Type01.Enum>() {{ 
		    	put("blank",Type01.Enum.forString(""));
		        put("N", Type01.Enum.forString("0"));
		        put("Y", Type01.Enum.forString("1"));
	}});
	
	//Additional Client Risk Factors
	public static final Map<String, TypeOtherRisk.Enum> OTHER_RISK_MAP = 
		    Collections.unmodifiableMap(new HashMap<String, TypeOtherRisk.Enum>() {{ 
		    	put("blank",TypeOtherRisk.Enum.forString(""));
		        put("1", TypeOtherRisk.Enum.forString("1"));  //STD112(O,U,Y)Exchange sex for drugs/money
		        put("2", TypeOtherRisk.Enum.forString("2"));  //STD111(O,U,Y) While intoxicated
		        put("5", TypeOtherRisk.Enum.forString("5"));  //with person with unk HIV status
		        put("6", TypeOtherRisk.Enum.forString("6"));  //with prostitute
		        put("8", TypeOtherRisk.Enum.forString("8"));  //STD109(O,U,Y) - with stranger
		        put("12", TypeOtherRisk.Enum.forString("12")); //was diagnosed with STD in last yr
		        put("13", TypeOtherRisk.Enum.forString("13")); //sex with numerous partners
		        put("15", TypeOtherRisk.Enum.forString("15")); //STD110(O,U,Y) Unprotected vaginal/anal sex with a person who is an IDU
		        put("16", TypeOtherRisk.Enum.forString("16")); //Unprotected vaginal/anal sex with a person who is HIV Pos
		        put("18", TypeOtherRisk.Enum.forString("18")); //unprotected with person who has sex for drugs/money
		        put("19", TypeOtherRisk.Enum.forString("19")); //Unprotected multiple partners in last year
	}});
	
	//NBS risk factor ans stuff on core page
	public static final Map<String, String> OUY_MAP = 
			Collections.unmodifiableMap(new HashMap<String, String>() {{ 
				put("O","O");  //Yes, Oral
				put("U","U");  //Yes, Vaginal or Anal
				put("Y","Y");  //Yes, unspecified
	}});
	
	
	//referred to surveillance
	public static final Map<String, Type0199.Enum> REPORTED_TO_SURVEILLANCE_MAP = 
		    Collections.unmodifiableMap(new HashMap<String, Type0199.Enum>() {{ 
		    	put("blank", Type0199.Enum.forString(""));
		        put("N", Type0199.Enum.forString("0"));
		        put("Y", Type0199.Enum.forString("1"));
	}});
	
	//used with error messages
	public static final String  NO_CASES_MSG = "No cases found to process between ";
	//public static final String  NO_SESSIONS_MSG = "No sessions found to process between ";
	//public static final String  NO_INDEXES_MSG = "No indexes found to process between ";
	public static final String  PS_MESSAGE_RESOURSES_PROPERTY = "hivPartnerServices.notFoundMsg";
	public static final String  PS_DEFAULT_YEAR = "1800";
	public static final String  PS_DEFAULT_DATE = "01/01/1900";
	public static final String  PS_DEFAULT_SITE_ID = "000";
	public static final String  PS_DEFAULT_SITE_TYPE = "F88";
	public static final String  PS_DEFAULT_SITE_ZIP = "12345";
}
