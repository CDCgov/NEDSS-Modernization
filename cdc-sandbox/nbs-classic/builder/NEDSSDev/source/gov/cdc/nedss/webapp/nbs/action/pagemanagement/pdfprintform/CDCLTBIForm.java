package gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform;

import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.webapp.nbs.form.page.PageForm;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

/**
 * PDF generating/populating class for "LTBI form"
 * @author Fatima Lopez Calzado
 *
 */
public class CDCLTBIForm extends CommonPDFPrintForm{
	/**
	 * Constants
	 */
	private static String delimiter1 = "__";

	
	/******************************************************Patient Demographics******************************************************/
		//Name
	private	static String DEM102 = "DEM102";
	protected static String DEM102__PatientLastNameDEM102= DEM102 + delimiter1+ "PatientLastNameDEM102";
	private	static String DEM104_FN = "DEM104_FN";
	protected static String DEM104_FN__PatientFirstNameDEM104= DEM104_FN + delimiter1+ "PatientFirstNameDEM104";
	private	static String DEM105 = "DEM105";
	protected static String DEM105__PatientMiddleNameDEM105= DEM105 + delimiter1+ "PatientMiddleNameDEM105";
	private	static String DEM250 = "DEM250";
	protected static String DEM250__PatientAliasNameDEM250= DEM250 + delimiter1+ "PatientAliasNameDEM250";	
	private	static String FullName = "Full_Name";
	protected static String FullName__Full_Name= FullName + delimiter1+ "FullNameFull_Name";	
	
		//Address
	private	static String DEM159_DEM160= "DEM159_DEM160";//street1+street2 - there is a space in form
	protected static String DEM159_160__PatientStreetDEM159= DEM159_DEM160 + delimiter1+ "PatientLastNameDEM159DEM160";
	private	static String DEM161 = "DEM161";
	protected static String DEM161__PatientCityDEM161= DEM161 + delimiter1+ "PatientCityDEM161";
	private	static String DEM162 = "DEM162";
	protected static String DEM162__PatientStateDEM162= DEM162 + delimiter1+ "PatientStateDEM162";
	private	static String DEM163 = "DEM163";
	protected static String DEM163__PatientZipDEM163= DEM163 + delimiter1+ "PatientZipDEM163";
	protected static String DEM165__PatientCountyDEM165= DEM165 + delimiter1+ "PatientCountyDEM165";
	protected static String DEM167__PatientCountryDEM167= DEM167 + delimiter1+ "PatientCountryDEM167";
	private	static String DEM168 = "DEM168";
	protected static String DEM168__CensusTracDEM168= DEM168 + delimiter1+ "PatientCensusTrackDEM168";
	private static String City_Limits_CD = "INV1112_CD";
	protected static String CityLimitsINV1112= City_Limits_CD + delimiter1+ "CityLimitsINV1112";
	
		//Phone number
	private	static String NBS006 = "NBS006";
	protected static String NBS006__PatientCellNBS006_UseCd = NBS006 + delimiter1+ "PatientCellNBS006";
	private	static String DEM177 = "DEM177";
	protected static String DEM177__PatientHmPhDEM177_UseCd = DEM177 + delimiter1+ "PatientHmPhDEM177";
	
		//Work number
	private	static String NBS002 = "NBS002";
	protected static String NBS002__PatientWorkPhNBS002_UseCd = NBS002 + delimiter1+ "PatientWorkPhNBS002";

		//Age
	private	static String INV2001 = "INV2001";
	protected static String INV2001__PatientReportedAge = INV2001 + delimiter1+ "PatientReportedAgeINV2001";
	private	static String DEM115 = "DEM115";
	protected static String DEM115__PatientDOB = DEM115 + delimiter1+ "PatientDOBDEM115";
		
		//Pregnant
	private	static String INV178_CD = "INV178_CD";
	protected static String INV178__PatientPregnantINV178 = INV178_CD + delimiter1+ "PatientPregnantINV178";
	private	static String NBS128 = "NBS128";
	protected static String NBS128__PatientPregnantWeeksNBS128 = NBS128 + delimiter1+ "PatientPregnantNBS128";
	
	
		//Race
	private	static String DEM152_AI_CDT = "DEM152_AI_CDT";
	protected static String DEM152_AI_CDT__PatientRaceDEM152 = DEM152_AI_CDT + delimiter1+ "PatientRaceDEM152";	
	private	static String DEM152_A_CDT = "DEM152_A_CDT";
	protected static String DEM152_A_CDT__PatientRaceDEM152 = DEM152_A_CDT + delimiter1+ "PatientRaceDEM152";	
	private	static String DEM152_B_CDT = "DEM152_B_CDT";
	protected static String DEM152_B_CDT__PatientRaceDEM152 = DEM152_B_CDT + delimiter1+ "PatientRaceDEM152";	
	private	static String DEM152_W_CDT = "DEM152_W_CDT";
	protected static String DEM152_W_CDT__PatientRaceDEM152 = DEM152_W_CDT + delimiter1+ "PatientRaceDEM152";	
	private	static String DEM152_O_CDT = "DEM152_O_CDT";
	protected static String DEM152_O_CDT__PatientRaceDEM152 = DEM152_O_CDT + delimiter1+ "PatientRaceDEM152";	
	private	static String DEM152_NH_CDT = "DEM152_NH_CDT";
	protected static String DEM152_NH_CDT__PatientRaceDEM152 = DEM152_NH_CDT + delimiter1+ "PatientRaceDEM152";	
	private	static String DEM152_U_CDT = "DEM152_U_CDT";
	protected static String DEM152_U_CDT__PatientRaceDEM152 = DEM152_U_CDT + delimiter1+ "PatientRaceDEM152";	
	private	static String DEM152_R_CDT = "DEM152_R_CDT";
	protected static String DEM152_R_CDT__PatientRaceDEM152 = DEM152_R_CDT + delimiter1+ "PatientRaceDEM152";	
	private	static String DEM152_D_CDT = "DEM152_D_CDT";
	protected static String DEM152_D_CDT__PatientRaceDEM152 = DEM152_D_CDT + delimiter1+ "PatientRaceDEM152";	
	private	static String DEM152_NA_CDT = "DEM152_NA_CDT";
	protected static String DEM152_NA_CDT__PatientRaceDEM152 = DEM152_NA_CDT + delimiter1+ "PatientRaceDEM152";	
	
	private static String DEM242 = "DEM242";
	private static String DEM242__AmericanIndianAlaskanNative = DEM242 + delimiter1 +"SpecifyAmericanIndianAlaskanNativeDEM242";
	private static String DEM243 = "DEM243";
	private static String DEM243_Asian= DEM243 + delimiter1 +"SpecifyAsianDEM243";
	private static String DEM244 = "DEM244";
	private static String DEM244_BlackAfricanAmerican= DEM244 + delimiter1 +"DEM244_BlackAfricanAmerican";
	private static String DEM245 = "DEM245";
	private static String DEM245_Hawaiian = DEM245 + delimiter1 +"DEM245_Hawaiian";
	private static String DEM246 = "DEM246";
	private static String DEM246_White = DEM246 + delimiter1 +"DEM246_White";
	
		//Ethnicity
	private	static String DEM155_CDT = "DEM155_CDT";
	protected static String DEM155_CDT__PatientEthnicityDEM155 = DEM155_CDT + delimiter1+ "EthnicityDEM155";
	private	static String NBS273_CDT = "NBS273_CDT";
	protected static String NBS273_CDT__PatientEthnicityUnkownReasonNBS273 = NBS273_CDT + delimiter1+ "PatientEthnicityUnkownReasonNBS273";
	
		//Gender
	private	static String DEM113_CD = "DEM113_CD";
	protected static String DEM113_CD__PatientCurrentSexDEM113 = DEM113_CD + delimiter1+ "PatientCurrentSexDEM113";	 
	private	static String NBS274_CD = "NBS274_CD";
	protected static String  NBS274_CD__PatientTransgenderNBS274 =  NBS274_CD + delimiter1+ "PatientTransgenderNBS274";	
	private	static String NBS272_CD = "NBS272_CD";
	protected static String NBS272_CD__PatientSexUnkownReasonNBS272 = NBS272_CD + delimiter1+ "PatientSexUnkownReasonNBS272";
	private static String CURRENT_SEX = "Current_Sex_CDT";
	private static String CURRENT_SEX_currentSex = CURRENT_SEX+delimiter1+"CurrentSexCDT";
	
		//Place of employment
	private static String Employment_Phone = "Employment_Phone";//Field Follow Up form constant
	protected static String EMPLOYMENT__PatientWorkPhoneNBS002_PatientWorkPhoneExtensionNBS003 = Employment_Phone+delimiter1+"PatientWorkPhoneNBS002_PatientWorkPhoneExtensionNBS003";
		
	
	/******************************************************Administrative Information******************************************************/
	
	private static String date_reported = "INV111";
	protected static  String INV111_date_reported = date_reported+delimiter1+"DateReportedINV111";
	
	private static String mmwr_week = "INV165";
	protected static  String INV165_mmwrweek = mmwr_week+delimiter1+"mmwrweekINV165";
	
	private static String mmwr_year = "INV166";
	protected static  String INV166_mmwryear = mmwr_year+delimiter1+"mmwryearINV166";
	
	private static String case_verification = "INV1115_CDT";
	protected static  String INV1115_caseverfication = case_verification+delimiter1+"caseVerificationINV1115";
	
	private static String INV163_CDT = "INV163_CDT";
	protected static  String INV163_CDT_casestatus = INV163_CDT+delimiter1+"casestatusINV163";
	
	private static String INV173 = "INV173";
	protected static  String INV173_casenumber= INV173+delimiter1+"tbStateCaseNumberINV173";	
	
	private static String INV1109_CD= "INV1109_CD";
	protected static  String INV1109_CD_casecounted = INV1109_CD+delimiter1+"caseCountedINV1109_CD";																																														
	
	private static String INV1111_CDT= "INV1111_CDT";
	protected static  String INV1111_CDT_countryofverifiedcase = INV1111_CDT+delimiter1+"countryofverifiedcaseINV1111";																																														
	
	private static String INV1108= "INV1108";
	protected static  String INV1108_localcasenumber = INV1108+delimiter1+"localcasenumberINV1108";																																														
	
	private static String INV1110= "INV1110";
	protected static  String INV1110_previouslyreportedstatecase = INV1110+delimiter1+"previouslyreportedstatecaseINV1110";																																														
	
	private static String INV1111_Oth= "INV1111_Oth";
	protected static  String INV1111_Oth_othercountryofverifiedcase = INV1111_Oth+delimiter1+"othercountryofverifiedcaseINV1111_Oth";																																														
	

	/******************************************************Initial Evaluation******************************************************/

	private static String DEM126= "DEM126";
	protected static  String DEM126_countryofbirth = DEM126+delimiter1+"countryofbirthDEM126";																																														
	
	private static String DEM2005= "DEM2005";
	protected static  String DEM2005_dateoffirstusarrival = DEM2005+delimiter1+"dateoffirstusarrivalDEM2005";		
	
	private static String DEM2003_CD= "DEM2003_CD";
	protected static  String DEM2003_CD_eligibleforuscitizenship = DEM2003_CD+delimiter1+"eligibleforuscitizenship_DEM2003_CD";																																														
	
	private static String INV1113_CDT= "INV1113_CDT";
	protected static  String INV1113_CDT_countriesofbirthforprimaryguardians = INV1113_CDT+delimiter1+"countriesofbirthforprimaryguardians_INV1113";																																														
	
	private static String INV501_CDT= "INV501_CDT";
	protected static  String INV501_CDT_countryofusualresidency = INV501_CDT+delimiter1+"countryofusualresdency_INV501_CDT";
	
	private static String INV1114_CD= "INV1114_CD";
	protected static  String INV1114_CD_outsideusfor90daysormore = INV1114_CD+delimiter1+"outsideusfor90daysormore_INV1114_CD";																																														
	
	private static String TB101_CD= "TB101_CD";
	protected static  String TB101_CD_statusattbdiagnosis = TB101_CD+delimiter1+"statusattbdiagnosis_TB101_CD";																																														
	
	private static String INV1116_CD= "INV1116_CD";
	protected static  String INV1116_CD_initialreasonevaluatedfortb = INV1116_CD+delimiter1+"initialreasonevaluatedfortb_INV1116_CD";																																														
	
	private static String INV1116_OTH= "INV1116_OTH";
	protected static  String INV1116_OTH_initialreasonevaluatedfortb = INV1116_OTH+delimiter1+"initialreasonevaluatedfortb_INV1116_OTH";																																														
	

	//Ever worked as
	
	private static String INV1276_CD= "INV1276_CD";
	protected static  String INV1276_CD_everworkedas = INV1276_CD+delimiter1+"everworkedas_INV1276_CD";																																														
	
	
	private static String INV1276_C_CD= "INV1276_C_CD";
	protected static  String INV1276_C_CD_everworkedas = INV1276_C_CD+delimiter1+"everworkedas_INV1276_C_CD";																																														
	
	private static String INV1276_H_CD= "INV1276_H_CD";
	protected static  String INV1276_H_CD_everworkedas = INV1276_H_CD+delimiter1+"everworkedas_INV1276_H_CD";																																														
	
	private static String INV1276_M_CD= "INV1276_M_CD";
	protected static  String INV1276_M_CD_everworkedas = INV1276_M_CD+delimiter1+"everworkedas_INV1276_M_CD";																																														
	
	private static String INV1276_U_CD= "INV1276_U_CD";
	protected static  String INV1276_U_CD_everworkedas = INV1276_U_CD+delimiter1+"everworkedas_INV1276_U_CD";																																														
	
	private static String INV1276_N_CD= "INV1276_N_CD";
	protected static  String INV1276_N_CD_everworkedas = INV1276_N_CD+delimiter1+"everworkedas_INV1276_N_CD";																																														
	
	//Repeating block: occupation:
	
	private static String code85659_1_R1= "85659_1_R1";
	protected static  String code85659_1_R1_currentOccupationStandarized = code85659_1_R1+delimiter1+"currentOccupationStandarized85659_1";																																														
	private static String code85659_1_R2= "85659_1_R2";
	protected static  String code85659_1_R2_currentOccupationStandarized = code85659_1_R2+delimiter1+"currentOccupationStandarized85659_1";																																														
	private static String code85659_1_R3= "85659_1_R3";
	protected static  String code85659_1_R3_currentOccupationStandarized = code85659_1_R3+delimiter1+"currentOccupationStandarized85659_1";																																														

	private static String code85658_3_R1= "85658_3_R1";
	protected static  String code85658_3_R1_currentOccupation = code85658_3_R1+delimiter1+"currentOccupation85658_3";																																														
	private static String code85658_3_R2= "85658_3_R2";
	protected static  String code85658_3_R2_currentOccupation = code85658_3_R2+delimiter1+"currentOccupation85658_3";																																														
	private static String code85658_3_R3= "85658_3_R3";
	protected static  String code85658_3_R3_currentOccupation = code85658_3_R3+delimiter1+"currentOccupation85658_3";																																														

	private static String code85657_5_R1= "85657_5_R1";
	protected static  String code85657_5_R1_currentIndustryStandarized = code85657_5_R1+delimiter1+"currentIndustryStandarized85657_5";																																														
	private static String code85657_5_R2= "85657_5_R2";
	protected static  String code85657_5_R2_currentIndustryStandarized = code85657_5_R2+delimiter1+"currentIndustryStandarized85657_5";																																														
	private static String code85657_5_R3= "85657_5_R3";
	protected static  String code85657_5_R3_currentIndustryStandarized = code85657_5_R3+delimiter1+"currentIndustryStandarized85657_5";																																														
	
	private static String code85078_4_R1= "85078_4_R1";
	protected static  String code85078_4_R1_currentIndustry = code85078_4_R1+delimiter1+"currentIndustry85078_4";																																														
	private static String code85078_4_R2= "85078_4_R2";
	protected static  String code85078_4_R2_currentIndustry = code85078_4_R2+delimiter1+"currentIndustry85078_4";																																														
	private static String code85078_4_R3= "85078_4_R3";
	protected static  String code85078_4_R3_currentIndustry = code85078_4_R3+delimiter1+"currentIndustry85078_4";																																														
	
	/******************************************************Risks******************************************************/
	

	private static String ARB016_CD= "ARB016_CD";
	protected static  String ARB016_CD_diabeticAtDiagnosisEvaluation = ARB016_CD+delimiter1+"diabeticAtDiagnosisEvaluation_ARB016";																																														
	
	private static String TB127_CD= "TB127_CD";
	protected static  String TB127_CD_homelessInThePast12Months = TB127_CD+delimiter1+"homelessInThePast12Months_TB127";																																														
	
	
	private static String Code32911000_CD= "32911000_CD";
	protected static  String Code32911000_CD_homelessever = Code32911000_CD+delimiter1+"homelessever_32911000";																																														
	
	private static String NBS689_CD= "NBS689_CD";
	protected static  String NBS689_CD_correctionalFacility = NBS689_CD+delimiter1+"correctionalFacility_NBS689";																																														
	
	private static String INV1119_CD= "INV1119_CD";
	protected static  String INV1119_CD_typeoffacility = INV1119_CD+delimiter1+"typeoffacility_INV1119_CD";																																														
	
	private static String INV1119_OTH= "INV1119_OTH";
	protected static  String INV1119_OTH_typeoffacility = INV1119_OTH+delimiter1+"typeoffacility_INV1119_CD";																																														
	
	private static String INV649_CD= "INV649_CD";
	protected static  String INV649_CD_correctionalfacilityever = INV649_CD+delimiter1+"correctionalfacilityever_INV649_CD";																																														
	
	private static String INV636_CD= "INV636_CD";
	protected static  String INV636_CD_residentoflongtermcarefacility = INV636_CD+delimiter1+"residentoflongtermcarefacility_INV636_CD";																																														
	
	private static String INV1120_CD= "INV1120_CD";
	protected static  String INV1120_CD_typeoffacility = INV1120_CD+delimiter1+"typeoffacility_INV1120_CD";																																														
	
	private static String INV1120_OTH= "INV1120_OTH";
	protected static  String INV1120_OTH_typeoffacility = INV1120_OTH+delimiter1+"typeoffacility_INV1120_OTH";																																														
	
	private static String INV607_CD= "INV607_CD";
	protected static  String INV607_CD_injectingdruguseinthepast12months = INV607_CD+delimiter1+"injectingdruguseinthepast12months_INV607_CD";																																														
	
	private static String INV608_CD= "INV608_CD";
	protected static  String INV608_CD_injectingdruguseinthepast12months = INV608_CD+delimiter1+"noninjectingdruguseinthepast12months_INV608_CD";																																														
	
	private static String ARB025_CD= "ARB025_CD";
	protected static  String ARB025_CD_HeavyAlcoholUseInThePast12Months = ARB025_CD+delimiter1+"HeavyAlcoholUseInThePast12Months_ARB025_CD";																																														
	
	private static String PHC690_CD= "PHC690_CD";
	protected static  String PHC690_CD_TNFAntagonistTherapy = PHC690_CD+delimiter1+"TNFAntagonistTherapy_PHC690_CD";																																														
	
	private static String Code161663000_CD= "161663000_CD";
	protected static  String Code161663000_CD_PostOrganTransplantation = Code161663000_CD+delimiter1+"PostOrganTransplantation_161663000_CD";																																														
	
	private static String ARB024_CD= "ARB024_CD";
	protected static  String ARB024_CD_EndStageRenalDisease = ARB024_CD+delimiter1+"EndStageRenalDisease_ARB024_CD";																																														
	
	private static String PHC2236_CD= "PHC2236_CD";
	protected static  String PHC2236_CD_ViralHepatitisBorCOnly = PHC2236_CD+delimiter1+"ViralHepatitisBorCOnly_PHC2236_CD";																																														
	
	private static String VAR126_CD= "VAR126_CD";
	protected static  String VAR126_CD_OtherImmunocompromise = VAR126_CD+delimiter1+"OtherImmunocompromise_VAR126_CD";																																														
	
	private static String Code72166_2_CD= "72166_2_CD";
	protected static  String Code72166_2_CD_CurrentSmokingStatusAtDiagnosticEvaluation = Code72166_2_CD+delimiter1+"CurrentSmokingStatusAtDiagnosticEvaluation_72166_2_CD";																																														
	
	private static String TRAVEL10_CD= "TRAVEL10_CD";
	protected static  String TRAVEL10_CD_LivedOutsideOfUSForMoreThan2Months = TRAVEL10_CD+delimiter1+"LivedOutsideOfUSForMoreThan2Months_TRAVEL10_CD";																																														
	
	private static String NBS561= "NBS561";
	protected static  String NBS561_SpecifyOtherRiskFactor = NBS561+delimiter1+"SpecifyOtherRiskFactor_NBS561";																																														
	
	private static String NBS560_CD= "NBS560_CD";
	protected static  String NBS560_CD_OtherRiskFactor = NBS560_CD+delimiter1+"OtherRiskFactor_NBS560_CD";																																														
	
	/******************************************************Diagnostic Testing******************************************************/
	
	//HIV Status
	private static String NBS859_CD= "NBS859_CD";
	protected static  String NBS859_CD_HivStatus = NBS859_CD+delimiter1+"HivStatus_NBS859_CD";																																														
	
	private static String NBS450= "NBS450";
	protected static  String NBS450_Collection_Date = NBS450+delimiter1+"HivStatusCollectionDate_NBS450";																																														
	
	private static String NBS870= "NBS870";
	protected static  String NBS870_Date_Reported = NBS870+delimiter1+"HivStatusDateReported_NBS870";																																														
	
	//Tuberculin
	
	private static String TUB147_CD= "TUB147_CD";
	protected static  String TUB147_CD_Tuberculin = TUB147_CD+delimiter1+"Tuberculin_TUB147_CD";																																														

	private static String TUB148= "TUB148";
	protected static  String TUB148_DatePlaced = TUB148+delimiter1+"TuberculinDatePlaced_TUB148";																																														
	
	private static String NBS866= "NBS866";
	protected static  String NBS866_DateRead = NBS866+delimiter1+"TuberculinDateRead_NBS866";																																														
	
	private static String TUB149= "TUB149";
	protected static  String TUB149_MMOfInduration = TUB149+delimiter1+"TuberculinMMOfInduration_TUB149";																																														
	
	
	
	//Interferon
	
	private static String TUB150_CD= "TUB150_CD";
	protected static  String TUB150_CD_Interferon= TUB150_CD+delimiter1+"Interferon_TUB150_CD";																																														
	
	private static String NBS868_CD= "NBS868_CD";
	protected static  String NBS868_CD_TestType = NBS868_CD+delimiter1+"TestType_NBS868_CD";																																														
	
	private static String TUB151= "TUB151";
	protected static  String TUB151_CollectionDate = TUB151+delimiter1+"InterferonCollectionDate_TUB151";																																														
			
	private static String NBS869= "NBS869";
	protected static  String NBS869_DateReported = NBS869+delimiter1+"InterferonDateReported_NBS869";																																														
			
	private static String NBS871= "NBS871";
	protected static  String NBS871_QuantitativeResult= NBS871+delimiter1+"InterferonQuantitativeResult_NBS871";	
	
	private static String NBS872_CDT= "NBS872_CDT";
	protected static  String NBS872_CDT_QuantitativeResultUnits= NBS872_CDT+delimiter1+"InterferonQuantitativeResultUnits_NBS872_CDT";																																														
					
			
	//Sputum Smear
	private static String TUB120_CD= "TUB120_CD";
	protected static  String TUB120_CD_SputumSmear = TUB120_CD+delimiter1+"SputumSmear_TUB120_CD";																																														
	
	private static String TUB121= "TUB121";
	protected static  String TUB121_CollectionDate = TUB121+delimiter1+"SputumSmearCollectionDate_TUB121";																																														
	
	private static String NBS863= "NBS863";
	protected static  String NBS863_DateReported = NBS863+delimiter1+"DateReported_NBS863";																																														
	
	
	//Sputum Culture
	private static String TUB122_CD= "TUB122_CD";
	protected static  String TUB122_CD_SputumCulture = TUB122_CD+delimiter1+"SputumCulture_TUB122_CD";																																														
	
	private static String TUB123= "TUB123";
	protected static  String TUB123_CollectionDate = TUB123+delimiter1+"SputumCultureCollectionDate_TUB123";			

	private static String TUB124= "TUB124";
	protected static  String TUB124_DateReported = TUB124+delimiter1+"SputumCultureDateReported_TUB124";																																														
	
	
	//Smear/Pathology/Cytology
	private static String TUB126_CD= "TUB126_CD";
	protected static  String TUB126_CD_SmearPathology = TUB126_CD+delimiter1+"SmearPathology_TUB126_CD";	
	
	private static String TUB129_CD= "TUB129_CD";
	protected static  String TUB129_CD_TestType = TUB129_CD+delimiter1+"TestType_TUB129_CD";																																														
	
	
	private static String TUB127= "TUB127";
	protected static  String TUB127_CollectionDate = TUB127+delimiter1+"SmearPathologyCollectionDate_TUB127";																																														
	
	
	private static String NBS864= "NBS864";
	protected static  String NBS864_DateReported = NBS864+delimiter1+"SmearPathologyDateReported_NBS864";																																														
	
	private static String TUB128_CDT= "TUB128_CDT";
	protected static  String TUB128_CDT_SpecimenSource = TUB128_CDT+delimiter1+"SmearPathologySpecimenSource_TUB128_CDT";																																														
	
	//Culture of tissue
	private static String TUB130_CD= "TUB130_CD";
	protected static  String TUB130_CD_CultureOfTissue = TUB130_CD+delimiter1+"CultureOfTissue_TUB130_CD";																																														
	
	private static String TUB131= "TUB131";
	protected static  String TUB131_CollectionDate = TUB131+delimiter1+"CultureOfTissueCollectionDate_TUB131";																																														
	
	private static String TUB132_CDT= "TUB132_CDT";
	protected static  String TUB132_CDT_SpecimenSource = TUB132_CDT+delimiter1+"CultureOfTissueSpecimenSource_TUB132_CDT";																																														
	
	private static String TUB133= "TUB133";
	protected static  String TUB133_DateReported = TUB133+delimiter1+"CultureOfTissueSpecimenSource_TUB133";																																														
	
	
	//Nucleic Acid Amplification
	private static String TUB135_CD= "TUB135_CD";
	protected static  String TUB135_CD_NucleicAcid = TUB135_CD+delimiter1+"NucleicAcid_TUB135_CD";																																														
	
	private static String TUB136= "TUB136";
	protected static  String TUB136_CollectionDate = TUB136+delimiter1+"NucleicAcidCollectionDate_TUB136";																																														
	
	private static String TUB139= "TUB139";
	protected static  String TUB139_DateReported = TUB139+delimiter1+"NucleicAcidDateReported_TUB139";																																														
	
	private static String NBS865_CDT= "NBS865_CDT";
	protected static  String NBS865_CDT_SpecimenSource = NBS865_CDT+delimiter1+"NucleicAcidSpecimenSource_NBS865_CDT";																																														
	
	//Diagnosis Testing Repeating
	private static String INV290_R1_CDT = "INV290_R1_CDT";
	protected static  String INV290_R1_CDT_TestType = INV290_R1_CDT+delimiter1+"TestType_INV290_R1_CDT";																																														
	
	private static String LAB165_R1_CDT = "LAB165_R1_CDT";
	protected static String LAB165_R1_CDT_SpecimenSource = LAB165_R1_CDT+delimiter1+"SpecimenSource_LAB165_R1_CDT";
	
	private static String LAB163_R1 = "LAB163_R1";
	protected static String LAB163_R1_SpecimenSource = LAB163_R1+delimiter1+"DateCollectedPlaced_LAB163_R1";
	
	private static String LAB167_R1 = "LAB167_R1";
	protected static String LAB167_R1_DateReported = LAB167_R1+delimiter1+"DateReported_LAB167_R1";
	
	private static String INV291_R1_CD = "INV291_R1_CD";
	protected static String INV291_R1_CD_testResultQualitative = INV291_R1_CD+delimiter1+"testResultQualitative_INV291_R1_CD";
	
	private static String LAB628_R1 = "LAB628_R1";
	protected static String LAB628_R1_QuantitativeResult = LAB628_R1+delimiter1+"QuantitativeResult_LAB628_R1";
	
	private static String LAB115_R1_CD = "LAB115_R1_CD";
	protected static String LAB115_R1_CD_resultsUnits = LAB115_R1_CD+delimiter1+"resultsUnits_LAB115_R1_CD";
	
	//R2
	private static String INV290_R2_CDT = "INV290_R2_CDT";
	protected static  String INV290_R2_CDT_TestType = INV290_R2_CDT+delimiter1+"TestType_INV290_R2_CDT";																																														
	
	private static String LAB165_R2_CDT = "LAB165_R2_CDT";
	protected static String LAB165_R2_CDT_SpecimenSource = LAB165_R2_CDT+delimiter1+"SpecimenSource_LAB165_R2_CDT";
	
	private static String LAB163_R2 = "LAB163_R2";
	protected static String LAB163_R2_SpecimenSource = LAB163_R2+delimiter1+"DateCollectedPlaced_LAB163_R2";
	
	private static String LAB167_R2 = "LAB167_R2";
	protected static String LAB167_R2_DateReported = LAB167_R2+delimiter1+"DateReported_LAB167_R2";
	
	private static String INV291_R2_CD = "INV291_R2_CD";
	protected static String INV291_R2_CD_testResultQualitative = INV291_R2_CD+delimiter1+"testResultQualitative_INV291_R2_CD";
	
	private static String LAB628_R2 = "LAB628_R2";
	protected static String LAB628_R2_QuantitativeResult = LAB628_R2+delimiter1+"QuantitativeResult_LAB628_R2";
	
	private static String LAB115_R2_CD = "LAB115_R2_CD";
	protected static String LAB115_R2_CD_resultsUnits = LAB115_R2_CD+delimiter1+"resultsUnits_LAB115_R2_CD";

	//R3
	private static String INV290_R3_CDT = "INV290_R3_CDT";
	protected static  String INV290_R3_CDT_TestType = INV290_R3_CDT+delimiter1+"TestType_INV290_R3_CDT";																																														
	
	private static String LAB165_R3_CDT = "LAB165_R3_CDT";
	protected static String LAB165_R3_CDT_SpecimenSource = LAB165_R3_CDT+delimiter1+"SpecimenSource_LAB165_R3_CDT";
	
	private static String LAB163_R3 = "LAB163_R3";
	protected static String LAB163_R3_SpecimenSource = LAB163_R3+delimiter1+"DateCollectedPlaced_LAB163_R3";
	
	private static String LAB167_R3 = "LAB167_R3";
	protected static String LAB167_R3_DateReported = LAB167_R3+delimiter1+"DateReported_LAB167_R3";
	
	private static String INV291_R3_CD = "INV291_R3_CD";
	protected static String INV291_R3_CD_testResultQualitative = INV291_R3_CD+delimiter1+"testResultQualitative_INV291_R3_CD";
	
	private static String LAB628_R3 = "LAB628_R3";
	protected static String LAB628_R3_QuantitativeResult = LAB628_R3+delimiter1+"QuantitativeResult_LAB628_R3";
	
	private static String LAB115_R3_CD = "LAB115_R3_CD";
	protected static String LAB115_R3_CD_resultsUnits = LAB115_R3_CD+delimiter1+"resultsUnits_LAB115_R3_CD";
	
	//R4
	private static String INV290_R4_CDT = "INV290_R4_CDT";
	protected static  String INV290_R4_CDT_TestType = INV290_R4_CDT+delimiter1+"TestType_INV290_R4_CDT";																																														
	
	private static String LAB165_R4_CDT = "LAB165_R4_CDT";
	protected static String LAB165_R4_CDT_SpecimenSource = LAB165_R4_CDT+delimiter1+"SpecimenSource_LAB165_R4_CDT";
	
	private static String LAB163_R4 = "LAB163_R4";
	protected static String LAB163_R4_SpecimenSource = LAB163_R4+delimiter1+"DateCollectedPlaced_LAB163_R4";
	
	private static String LAB167_R4 = "LAB167_R4";
	protected static String LAB167_R4_DateReported = LAB167_R4+delimiter1+"DateReported_LAB167_R4";
	
	private static String INV291_R4_CD = "INV291_R4_CD";
	protected static String INV291_R4_CD_testResultQualitative = INV291_R4_CD+delimiter1+"testResultQualitative_INV291_R4_CD";
	
	private static String LAB628_R4 = "LAB628_R4";
	protected static String LAB628_R4_QuantitativeResult = LAB628_R4+delimiter1+"QuantitativeResult_LAB628_R4";
	
	private static String LAB115_R4_CD = "LAB115_R4_CD";
	protected static String LAB115_R4_CD_resultsUnits = LAB115_R4_CD+delimiter1+"resultsUnits_LAB115_R4_CD";
	
	//R5
	private static String INV290_R5_CDT = "INV290_R5_CDT";
	protected static  String INV290_R5_CDT_TestType = INV290_R5_CDT+delimiter1+"TestType_INV290_R5_CDT";																																														
	
	private static String LAB165_R5_CDT = "LAB165_R5_CDT";
	protected static String LAB165_R5_CDT_SpecimenSource = LAB165_R5_CDT+delimiter1+"SpecimenSource_LAB165_R5_CDT";
	
	private static String LAB163_R5 = "LAB163_R5";
	protected static String LAB163_R5_SpecimenSource = LAB163_R5+delimiter1+"DateCollectedPlaced_LAB163_R5";
	
	private static String LAB167_R5 = "LAB167_R5";
	protected static String LAB167_R5_DateReported = LAB167_R5+delimiter1+"DateReported_LAB167_R5";
	
	private static String INV291_R5_CD = "INV291_R5_CD";
	protected static String INV291_R5_CD_testResultQualitative = INV291_R5_CD+delimiter1+"testResultQualitative_INV291_R5_CD";
	
	private static String LAB628_R5 = "LAB628_R5";
	protected static String LAB628_R5_QuantitativeResult = LAB628_R5+delimiter1+"QuantitativeResult_LAB628_R5";
	
	private static String LAB115_R5_CD = "LAB115_R5_CD";
	protected static String LAB115_R5_CD_resultsUnits = LAB115_R5_CD+delimiter1+"resultsUnits_LAB115_R5_CD";

	//R6
	private static String INV290_R6_CDT = "INV290_R6_CDT";
	protected static  String INV290_R6_CDT_TestType = INV290_R6_CDT+delimiter1+"TestType_INV290_R6_CDT";																																														
	
	private static String LAB165_R6_CDT = "LAB165_R6_CDT";
	protected static String LAB165_R6_CDT_SpecimenSource = LAB165_R6_CDT+delimiter1+"SpecimenSource_LAB165_R6_CDT";
	
	private static String LAB163_R6 = "LAB163_R6";
	protected static String LAB163_R6_SpecimenSource = LAB163_R6+delimiter1+"DateCollectedPlaced_LAB163_R6";
	
	private static String LAB167_R6 = "LAB167_R6";
	protected static String LAB167_R6_DateReported = LAB167_R6+delimiter1+"DateReported_LAB167_R6";
	
	private static String INV291_R6_CD = "INV291_R6_CD";
	protected static String INV291_R6_CD_testResultQualitative = INV291_R6_CD+delimiter1+"testResultQualitative_INV291_R6_CD";
	
	private static String LAB628_R6 = "LAB628_R6";
	protected static String LAB628_R6_QuantitativeResult = LAB628_R6+delimiter1+"QuantitativeResult_LAB628_R6";
	
	private static String LAB115_R6_CD = "LAB115_R6_CD";
	protected static String LAB115_R6_CD_resultsUnits = LAB115_R6_CD+delimiter1+"resultsUnits_LAB115_R6_CD";

	//R7
	private static String INV290_R7_CDT = "INV290_R7_CDT";
	protected static  String INV290_R7_CDT_TestType = INV290_R7_CDT+delimiter1+"TestType_INV290_R7_CDT";																																														
	
	private static String LAB165_R7_CDT = "LAB165_R7_CDT";
	protected static String LAB165_R7_CDT_SpecimenSource = LAB165_R7_CDT+delimiter1+"SpecimenSource_LAB165_R7_CDT";
	
	private static String LAB163_R7 = "LAB163_R7";
	protected static String LAB163_R7_SpecimenSource = LAB163_R7+delimiter1+"DateCollectedPlaced_LAB163_R7";
	
	private static String LAB167_R7 = "LAB167_R7";
	protected static String LAB167_R7_DateReported = LAB167_R7+delimiter1+"DateReported_LAB167_R7";
	
	private static String INV291_R7_CD = "INV291_R7_CD";
	protected static String INV291_R7_CD_testResultQualitative = INV291_R7_CD+delimiter1+"testResultQualitative_INV291_R7_CD";
	
	private static String LAB628_R7 = "LAB628_R7";
	protected static String LAB628_R7_QuantitativeResult = LAB628_R7+delimiter1+"QuantitativeResult_LAB628_R7";
	
	private static String LAB115_R7_CD = "LAB115_R7_CD";
	protected static String LAB115_R7_CD_resultsUnits = LAB115_R7_CD+delimiter1+"resultsUnits_LAB115_R7_CD";
	

	/******************************************************Chest Radiograph and Other Chest Imaging Results******************************************************/
	
	private static String TUB141_CD = "TUB141_CD";
	protected static String TUB141_CD_InitialChestXRay = TUB141_CD+delimiter1+"initialChestXRay_TUB141_CD";
	
	private static String LAB681A = "LAB681A";
	protected static String LAB681A_XRayDate = LAB681A+delimiter1+"XRayDate_LAB681A";
	
	private static String TUB142_CD = "TUB142_CD";
	protected static String TUB142_CD_evidenceOfCavityXRay = TUB142_CD+delimiter1+"evidenceOfCavityXRay_TUB142_CD";
	
	private static String TUB143_CD = "TUB143_CD";
	protected static String TUB143_CD_evidenceOfMiliaryTBXRay = TUB143_CD+delimiter1+"evidenceOfMiliaryTBXRay_TUB143_CD";
	
	private static String TUB144_CD = "TUB144_CD";
	protected static String TUB144_CD_InitialChestCTScan = TUB144_CD+delimiter1+"initialChestCTScan_TUB144_CD";
	
	private static String LAB681B = "LAB681B";
	protected static String LAB681B_CTScanDate = LAB681B+delimiter1+"CTScanDate_LAB681B";
	
	private static String TUB145_CD = "TUB145_CD";
	protected static String TUB145_CD_evidenceOfCavityCTScan = TUB145_CD+delimiter1+"evidenceOfCavityCTScan_TUB145_CD";
	
	private static String TUB146_CD = "TUB146_CD";
	protected static String TUB146_CD_evidenceOfMiliaryTBCTScan = TUB146_CD+delimiter1+"evidenceOfMiliaryTBCTScan_TUB146_CD";
	
	
	//Chest repeating
	private static String LAB677_R1_CD = "LAB677_R1_CD";
	protected static String LAB677_R1_CD_ChestTestType = LAB677_R1_CD+delimiter1+"chestTestType_LAB677_R1_CD";
	
	private static String LAB677_R1_OTH = "LAB677_R1_OTH";
	protected static String LAB677_R1_OTH_ChestTestType = LAB677_R1_OTH+delimiter1+"chestTestType_LAB677_R1_OTH";
	
	private static String LAB681_R1 = "LAB681_R1";
	protected static String LAB681_R1_ChestDateOfStudy = LAB681_R1+delimiter1+"chestDateOfStudy_LAB681_R1";
	
	private static String LAB678_R1_CD = "LAB678_R1_CD";
	protected static String LAB678_R1_CD_ChestResultOfStudy = LAB678_R1_CD+delimiter1+"chestResultOfStudy_LAB678_R1_CD";
	
	private static String LAB679_R1_CD = "LAB679_R1_CD";
	protected static String LAB679_R1_CD_ChestEvidenceOfCavity = LAB679_R1_CD+delimiter1+"chestEvidenceOfCavity_LAB679_R1_CD";
	
	private static String LAB680_R1_CD = "LAB680_R1_CD";
	protected static String LAB680_R1_CD_chestEvidenceOfMiliary = LAB680_R1_CD+delimiter1+"chestEvidenceOfMiliary_LAB680_R1_CD";

	private static String LAB677_R2_CD = "LAB677_R2_CD";
	protected static String LAB677_R2_CD_ChestTestType = LAB677_R2_CD+delimiter1+"chestTestType_LAB677_R2_CD";
	
	private static String LAB677_R2_OTH = "LAB677_R2_OTH";
	protected static String LAB677_R2_OTH_ChestTestType = LAB677_R2_OTH+delimiter1+"chestTestType_LAB677_R2_OTH";
	
	private static String LAB681_R2 = "LAB681_R2";
	protected static String LAB681_R2_ChestDateOfStudy = LAB681_R2+delimiter1+"chestDateOfStudy_LAB681_R2";
	
	private static String LAB678_R2_CD = "LAB678_R2_CD";
	protected static String LAB678_R2_CD_ChestResultOfStudy = LAB678_R2_CD+delimiter1+"chestResultOfStudy_LAB678_R2_CD";
	
	private static String LAB679_R2_CD = "LAB679_R2_CD";
	protected static String LAB679_R2_CD_ChestEvidenceOfCavity = LAB679_R2_CD+delimiter1+"chestEvidenceOfCavity_LAB679_R2_CD";
	
	private static String LAB680_R2_CD = "LAB680_R2_CD";
	protected static String LAB680_R2_CD_chestEvidenceOfMiliary = LAB680_R2_CD+delimiter1+"chestEvidenceOfMiliary_LAB680_R2_CD";
	
	private static String LAB677_R3_CD = "LAB677_R3_CD";
	protected static String LAB677_R3_CD_ChestTestType = LAB677_R3_CD+delimiter1+"chestTestType_LAB677_R3_CD";
	
	private static String LAB677_R3_OTH = "LAB677_R3_OTH";
	protected static String LAB677_R3_OTH_ChestTestType = LAB677_R3_OTH+delimiter1+"chestTestType_LAB677_R3_OTH";
	
	private static String LAB681_R3 = "LAB681_R3";
	protected static String LAB681_R3_ChestDateOfStudy = LAB681_R3+delimiter1+"chestDateOfStudy_LAB681_R3";
	
	private static String LAB678_R3_CD = "LAB678_R3_CD";
	protected static String LAB678_R3_CD_ChestResultOfStudy = LAB678_R3_CD+delimiter1+"chestResultOfStudy_LAB678_R3_CD";
	
	private static String LAB679_R3_CD = "LAB679_R3_CD";
	protected static String LAB679_R3_CD_ChestEvidenceOfCavity = LAB679_R3_CD+delimiter1+"chestEvidenceOfCavity_LAB679_R3_CD";
	
	private static String LAB680_R3_CD = "LAB680_R3_CD";
	protected static String LAB680_R3_CD_chestEvidenceOfMiliary = LAB680_R3_CD+delimiter1+"chestEvidenceOfMiliary_LAB680_R3_CD";

	
	/******************************************************Epidemiologic Investigation******************************************************/
	
	
	private static String INV1274_CD = "INV1274_CD";
	protected static String INV1274_CD_caseMeetBinational = INV1274_CD+delimiter1+"caseMeetBinational_INV1274_CD";
	
	private static String INV515_CD = "INV515_CD";
	protected static String INV515_CD_whichCriteriaWereMet = INV515_CD+delimiter1+"whichCriteriaWereMet_INV515_CD";
	private static String INV515_S_CD = "INV515_S_CD";
	protected static String INV515_S_CD_whichCriteriaWereMet = INV515_S_CD+delimiter1+"whichCriteriaWereMet_INV515_S_CD";
	private static String INV515_C_CD = "INV515_C_CD";
	protected static String INV515_C_CD_whichCriteriaWereMet = INV515_C_CD+delimiter1+"whichCriteriaWereMet_INV515_C_CD";
	private static String INV515_R_CD = "INV515_R_CD";
	protected static String INV515_R_CD_whichCriteriaWereMet = INV515_R_CD+delimiter1+"whichCriteriaWereMet_INV515_R_CD";
	private static String INV515_W_CD = "INV515_W_CD";
	protected static String INV515_W_CD_whichCriteriaWereMet = INV515_W_CD+delimiter1+"whichCriteriaWereMet_INV515_W_CD";
	private static String INV515_B_CD = "INV515_B_CD";
	protected static String INV515_B_CD_whichCriteriaWereMet = INV515_B_CD+delimiter1+"whichCriteriaWereMet_INV515_B_CD";
	private static String INV515_O_CD = "INV515_O_CD";
	protected static String INV515_O_CD_whichCriteriaWereMet = INV515_O_CD+delimiter1+"whichCriteriaWereMet_INV515_O_CD";

	private static String INV1122_CD = "INV1122_CD";
	protected static String INV1122_CD_caseIdentifiedDuringTheContactInvestigation = INV1122_CD+delimiter1+"caseIdentifiedDuringTheContactInvestigation_INV1122_CD";

	private static String INV1123_CD = "INV1123_CD";
	protected static String INV1123_CD_evaluatedForTB = INV1123_CD+delimiter1+"evaluatedForTB_INV1123_CD";

	private static String INV1134_CD = "INV1134_CD";
	protected static String INV1134_CD_contactInvestigationConducted = INV1134_CD+delimiter1+"contactInvestigationConducted_INV1134_CD";

	private static String INV1124_R1 = "INV1124_R1";
	protected static String INV1124_R1_linkedStateCaseNumber = INV1124_R1+delimiter1+"linkedStateCaseNumber_INV1124_R1";

	private static String INV1124_R2 = "INV1124_R2";
	protected static String INV1124_R2_linkedStateCaseNumber = INV1124_R2+delimiter1+"linkedStateCaseNumber_INV1124_R2";

	private static String INV1124_R3 = "INV1124_R3";
	protected static String INV1124_R3_linkedStateCaseNumber = INV1124_R3+delimiter1+"linkedStateCaseNumber_INV1124_R3";

	private static String INV1124_R4 = "INV1124_R4";
	protected static String INV1124_R4_linkedStateCaseNumber = INV1124_R4+delimiter1+"linkedStateCaseNumber_INV1124_R4";

	private static String INV1124_R5 = "INV1124_R5";
	protected static String INV1124_R5_linkedStateCaseNumber = INV1124_R5+delimiter1+"linkedStateCaseNumber_INV1124_R5";

	private static String INV1124_R6 = "INV1124_R6";
	protected static String INV1124_R6_linkedStateCaseNumber = INV1124_R6+delimiter1+"linkedStateCaseNumber_INV1124_R6";

	private static String INV1124_R7 = "INV1124_R7";
	protected static String INV1124_R7_linkedStateCaseNumber = INV1124_R7+delimiter1+"linkedStateCaseNumber_INV1124_R7";

	private static String INV1124_R8 = "INV1124_R8";
	protected static String INV1124_R8_linkedStateCaseNumber = INV1124_R8+delimiter1+"linkedStateCaseNumber_INV1124_R8";

	
	/******************************************************Treatment and Outcome Information******************************************************/

	private static String INV1128 = "INV1128";
	protected static String INV1128_LTBITherapyStarted = INV1128+delimiter1+"LTBITherapyStarted_INV1128";
	private static String INV924 = "INV924";
	protected static String INV924_TreatmentStartDate = INV924+delimiter1+"TreatmentStartDate_INV924";
	private static String code63939_3 = "63939_3";
	protected static String code63939_3_DateTherapyStopped = code63939_3+delimiter1+"DateTherapyStopped_63939_3";
	private static String INV1129_CD = "INV1129_CD";
	protected static String INV1129_CD_SpecifyInitialRegimen= INV1129_CD+delimiter1+"SpecifyInitialRegimen_INV1129_CD";
	private static String INV1129_OTH = "INV1129_OTH";
	protected static String INV1129_OTH_SpecifyInitialRegimenOther= INV1129_OTH+delimiter1+"SpecifyInitialRegimenOther_INV1129_OTH";
	
	private static String INV1130_CD = "INV1130_CD";
	protected static String INV1130_CD_WhyLTBITreatmentWasNotStarted= INV1130_CD+delimiter1+"WhyLTBITreatmentWasNotStarted_INV1130_CD";
	private static String INV1130_OTH = "INV1130_OTH";
	protected static String INV1130_OTH_WhyLTBITreatmentWasNotStartedOther= INV1130_OTH+delimiter1+"WhyLTBITreatmentWasNotStartedOther_INV1130_OTH";
	private static String code55753_8B_CD = "55753_8B_CD";
	protected static String code55753_8B_CD_treatmentAdministration= code55753_8B_CD+delimiter1+"treatmentAdministration_55753_8B_CD";
	
	private static String code55753_8B_D_CD = "55753_8B_D_CD";
	protected static String code55753_8B_D_CD_treatmentAdministration= code55753_8B_D_CD+delimiter1+"treatmentAdministration_55753_8B_D_CD";
	
	private static String code55753_8B_E_CD = "55753_8B_E_CD";
	protected static String code55753_8B_E_CD_treatmentAdministration= code55753_8B_E_CD+delimiter1+"treatmentAdministration_55753_8B_E_CD";
	
	private static String code55753_8B_S_CD = "55753_8B_S_CD";
	protected static String code55753_8B_S_CD_treatmentAdministration= code55753_8B_S_CD+delimiter1+"treatmentAdministration_55753_8B_S_CD";
	
	
	private static String INV1131_CD = "INV1131_CD";
	protected static String INV1131_CD_ReasonTherapyStopped= INV1131_CD+delimiter1+"ReasonTherapyStopped_INV1131_CD";
	private static String INV1131_OTH = "INV1131_OTH";
	protected static String INV1131_OTH_ReasonTherapyStoppedOther= INV1131_OTH+delimiter1+"ReasonTherapyStoppedOther_INV1131_OTH";
	
	private static String INV1132 = "INV1132";
	protected static String INV1132_NTSSCaseNumber= INV1132+delimiter1+"NTSSCaseNumber_INV1132";
	private static String code64750_3_CD = "64750_3_CD";
	protected static String code64750_3_CD_SevereAdverseEvent= code64750_3_CD+delimiter1+"SevereAdverseEvent_64750_3_CD";
	private static String code64750_3_D_CD = "64750_3_D_CD";
	protected static String code64750_3_D_CD_SevereAdverseEvent= code64750_3_D_CD+delimiter1+"SevereAdverseEvent_64750_3_D_CD";
	private static String code64750_3_H_CD = "64750_3_H_CD";
	protected static String code64750_3_H_CD_SevereAdverseEvent= code64750_3_H_CD+delimiter1+"SevereAdverseEvent_64750_3_H_CD";
	
	private static String INV167 = "INV167";
	protected static String INV167_InvestigationComments = INV167+delimiter1+"InvestigationComments_INV167";
	
	
	

	

	
	
	

	
	 
	
	

	

	
	public static Map<String, String> mappedLTBI =new HashMap<String, String>();
	
	/**
	 * MAPS
	 */
	
	private static Map<String, String> LTBIFieldsMap= new HashMap<String, String>();

	/**
	 * Methods
	 */
	
	/**
	 * initializeLTBIFormValues: this method initializes the LTBIFieldsMap map that will be send to the common form
	 * for populating the data
	 * @throws NEDSSAppException
	 */
	
	private static void initializeLTBIFormValues() throws NEDSSAppException{
		try{
		
		if(LTBIFieldsMap.size()==0){
			initializePatientDemographics();
			initializeAdministrative();
			initializeInitialEvaluation();
			initializeRisks();
			initializeDiagnosisTesting();
			initializeChest();
			initializeEpidemiologic();
			initializeTreatmentAndOutcome();
		
		}
		}catch(Exception e){
			logger.error(e);
			logger.error("CDCLTBIForm.initializeLTBIFormValues Exception thrown, "+ e);
			throw new NEDSSAppException("CDCLTBIForm.initializeLTBIFormValues Exception thrown, ", e);
			
		}
	}
	

	
			
	/**
	 * printForm(): this method prints the form
	 * @param pageFormVar
	 * @param req
	 * @param res
	 * @param formType
	 * @throws Exception
	 */
	
	public static void printForm(PageForm pageFormVar, HttpServletRequest req, HttpServletResponse res, String formType) throws Exception {
		try {
			pageForm = pageFormVar;
			//String fileName=NedssUtils.CleanStringPath(FilenameUtils.normalize(propertiesDirectory + pageForm.getFormName() +".pdf"));
			pdfDocument = PDDocument.load(new File(NedssUtils.CleanStringPath(FilenameUtils.normalize(propertiesDirectory + pageForm.getFormName() +".pdf"))));
			String formCode = pageForm.getPageFormCd();
			if(formType.equals(NEDSSConstants.CDC_FILLED)){
				PDDocumentCatalog docCatalog = pdfDocument.getDocumentCatalog();
				PDAcroForm acroForm = docCatalog.getAcroForm();
				proxyVO = viewPrintLoadUtil(pageForm, req);	
				fillPDForm(acroForm, formCode, req);

			}
			res.setContentType("application/pdf");
			pdfDocument.save(res.getOutputStream());
			
		
		} catch (Exception e) {
			logger.error(e.toString());
			req.getSession().setAttribute("error", "<b>There is an error while printing the Form: </b> <br/><br/>");				
			try {
				res.sendRedirect("/nbs/error");
			} catch (IOException e1) {
				logger.error(e1);
				logger.error("CDCLTBIForm.printForm Exception thrown, "+ e1);
				throw new NEDSSAppException("CDCLTBIForm.printForm Exception thrown, ", e1);
				
			}
		} finally {		
				try {
					if(pdfDocument!=null)
						pdfDocument.close();
				} catch (IOException e2) {
					logger.error(e2);
					logger.error("CDCLTBIForm.printForm Exception thrown, Error while closing FileInputStream for Print, "+ e2);
					throw new NEDSSAppException("CDCLTBIForm.printForm Exception thrown, Error while closing FileInputStream for Print, ", e2);
				}
			}
		}
		

	
	
	/**
	 * changeFormat(): converts a date from yyyy-MM-dd format to MM-dd-yyyy format
	 * @param date: date ini format yyyy-MM-dd HH:mm:ss.S
	 * @return it returns the date in format MM-dd-yyyy
	 * @throws NEDSSAppException
	 */
	
/*	private static String changeFormat(String date) throws NEDSSAppException{
		
			try{
			
				SimpleDateFormat formatter, FORMATTER;
				formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
				Date dateObject = formatter.parse(date);
				FORMATTER = new SimpleDateFormat("MM-dd-yyyy");
				return FORMATTER.format(dateObject);
			}catch(Exception e){	
				logger.error(e);
				logger.error("CDCLTBIForm.changeFormat Exception thrown, "+ e);
				throw new NEDSSAppException("CDCLTBIForm.changeFormat Exception thrown, ", e);
			}
	}*/
	
	
	
	
	private static void fillPDForm(PDAcroForm acroForm, String invFormCd,HttpServletRequest req) throws NEDSSAppException {
		//    Map<String, String> originalContactRecordValues = new  HashMap<String,String>();
			String currentKey = "";
			//String currentFormValue = "";
			try {
				answerMap = pageForm.getPageClientVO().getAnswerMap(); 
		
				int i = 1; //what is this used for??
				
				initializeLTBIFormValues();
				
				getMappedValues(LTBIFieldsMap, invFormCd, req);

				//presently set to 1 so that we can make this code flexible for 2 nd Investigation for print
				@SuppressWarnings("unchecked")
				List<PDField> pdfFormFields = acroForm.getFields();
				for(PDField pdfField: pdfFormFields){
					currentKey =pdfField.getPartialName();
					//currentFormValue = pdfField.getValueAsString();
					
					processPDFFIelds(pdfField, i);//Common
				}

			} catch (NEDSSAppException e) {
				logger.error("Error processing PDF Fields - current field = " + currentKey);
				logger.error("CDCLTBIForm.fillPDForm Exception thrown, "+ e);
				throw new NEDSSAppException("CDCLTBIForm.fillPDForm Exception thrown, ", e);
			}
		}//end	
	
	public static void initializePatientDemographics(){
		
		
		/******************************************************Patient Demographics******************************************************/
			//Name
		LTBIFieldsMap.put(DEM102__PatientLastNameDEM102, DEM102);
		LTBIFieldsMap.put(DEM104_FN__PatientFirstNameDEM104, DEM104_FN);
		LTBIFieldsMap.put(DEM105__PatientMiddleNameDEM105, DEM105);
		LTBIFieldsMap.put(DEM250__PatientAliasNameDEM250, DEM250);
	//	LTBIFieldsMap.put(DEM250__PatientAliasNameDEM250, DEM250);
		LTBIFieldsMap.put(FullName__Full_Name, FullName);
		
			//Address
		LTBIFieldsMap.put(DEM159_160__PatientStreetDEM159, DEM159_DEM160); 
		LTBIFieldsMap.put(DEM161__PatientCityDEM161, DEM161);
		LTBIFieldsMap.put(DEM162__PatientStateDEM162, DEM162);
		LTBIFieldsMap.put(DEM163__PatientZipDEM163, DEM163);
		LTBIFieldsMap.put(DEM165__PatientCountyDEM165, DEM165);
		LTBIFieldsMap.put(DEM167__PatientCountryDEM167, DEM167);
		LTBIFieldsMap.put(DEM168__CensusTracDEM168, DEM168);
		LTBIFieldsMap.put(CityLimitsINV1112,City_Limits_CD);
		
			//Phone number
		LTBIFieldsMap.put(NBS006__PatientCellNBS006_UseCd, NBS006);
		LTBIFieldsMap.put(DEM177__PatientHmPhDEM177_UseCd, DEM177);
		
			//Work Phone number
		LTBIFieldsMap.put(NBS002__PatientWorkPhNBS002_UseCd, NBS002);
		
			//Reported age
		LTBIFieldsMap.put(INV2001__PatientReportedAge, INV2001);
		LTBIFieldsMap.put(DEM115__PatientDOB, DEM115);
		
			//Pregnant
		LTBIFieldsMap.put(INV178__PatientPregnantINV178, INV178_CD);
		LTBIFieldsMap.put(NBS128__PatientPregnantWeeksNBS128, NBS128);
		
			//Race
		LTBIFieldsMap.put(DEM152_AI_CDT__PatientRaceDEM152, DEM152_AI_CDT);
		LTBIFieldsMap.put(DEM152_A_CDT__PatientRaceDEM152, DEM152_A_CDT);
		LTBIFieldsMap.put(DEM152_B_CDT__PatientRaceDEM152, DEM152_B_CDT);
		LTBIFieldsMap.put(DEM152_W_CDT__PatientRaceDEM152, DEM152_W_CDT);
		LTBIFieldsMap.put(DEM152_O_CDT__PatientRaceDEM152, DEM152_O_CDT);
		LTBIFieldsMap.put(DEM152_NH_CDT__PatientRaceDEM152, DEM152_NH_CDT);
		LTBIFieldsMap.put(DEM152_U_CDT__PatientRaceDEM152, DEM152_U_CDT);
		LTBIFieldsMap.put(DEM152_R_CDT__PatientRaceDEM152, DEM152_R_CDT);
		LTBIFieldsMap.put(DEM152_D_CDT__PatientRaceDEM152, DEM152_D_CDT);
		LTBIFieldsMap.put(DEM152_NA_CDT__PatientRaceDEM152, DEM152_NA_CDT);
		
			//Detailed Race
		LTBIFieldsMap.put(DEM242__AmericanIndianAlaskanNative, DEM242);
		LTBIFieldsMap.put(DEM243_Asian, DEM243);
		LTBIFieldsMap.put(DEM244_BlackAfricanAmerican, DEM244);
		LTBIFieldsMap.put(DEM245_Hawaiian, DEM245);
		LTBIFieldsMap.put(DEM246_White, DEM246);
		
			//Ethnicity
		LTBIFieldsMap.put(DEM155_CDT__PatientEthnicityDEM155, DEM155_CDT);
		LTBIFieldsMap.put(NBS273_CDT__PatientEthnicityUnkownReasonNBS273, NBS273_CDT);
		
			//Gender
		LTBIFieldsMap.put(DEM113_CD__PatientCurrentSexDEM113, DEM113_CD);
		LTBIFieldsMap.put(NBS274_CD__PatientTransgenderNBS274, NBS274_CD);
		LTBIFieldsMap.put(NBS272_CD__PatientSexUnkownReasonNBS272, NBS272_CD);
		LTBIFieldsMap.put(CURRENT_SEX_currentSex, CURRENT_SEX);

		
			//Employment
		LTBIFieldsMap.put(EMPLOYMENT__PatientWorkPhoneNBS002_PatientWorkPhoneExtensionNBS003, Employment_Phone);
		
		
	}
	
	
	public static void initializeAdministrative(){
		
		
		/******************************************************Administrative Information******************************************************/
		
		LTBIFieldsMap.put(INV111_date_reported,  date_reported);
		LTBIFieldsMap.put(INV165_mmwrweek,  mmwr_week);
		LTBIFieldsMap.put(INV166_mmwryear,  mmwr_year);
		LTBIFieldsMap.put(INV1115_caseverfication,  case_verification);
		LTBIFieldsMap.put(INV163_CDT_casestatus,  INV163_CDT);
		LTBIFieldsMap.put(INV173_casenumber,  INV173);
		LTBIFieldsMap.put(INV1109_CD_casecounted,  INV1109_CD);
		LTBIFieldsMap.put(INV1111_CDT_countryofverifiedcase,  INV1111_CDT);
		LTBIFieldsMap.put(INV1108_localcasenumber,  INV1108);
		LTBIFieldsMap.put(INV1110_previouslyreportedstatecase,  INV1110);
		LTBIFieldsMap.put(INV1111_Oth_othercountryofverifiedcase,  INV1111_Oth);
		
		
		
		
	}
	
	public static void initializeInitialEvaluation(){
		
		/******************************************************Initial Evaluation******************************************************/
		
		
		LTBIFieldsMap.put(DEM126_countryofbirth,  DEM126);
		LTBIFieldsMap.put(DEM2005_dateoffirstusarrival,  DEM2005);
		LTBIFieldsMap.put(DEM2003_CD_eligibleforuscitizenship,  DEM2003_CD);
		LTBIFieldsMap.put(DEM2003_CD_eligibleforuscitizenship,  DEM2003_CD);
		LTBIFieldsMap.put(INV1113_CDT_countriesofbirthforprimaryguardians,  INV1113_CDT);
		LTBIFieldsMap.put(INV501_CDT_countryofusualresidency,  INV501_CDT);
		LTBIFieldsMap.put(INV1114_CD_outsideusfor90daysormore,  INV1114_CD);
		LTBIFieldsMap.put(TB101_CD_statusattbdiagnosis,  TB101_CD);
		LTBIFieldsMap.put(INV1116_CD_initialreasonevaluatedfortb,  INV1116_CD);
		LTBIFieldsMap.put(INV1116_OTH_initialreasonevaluatedfortb,  INV1116_OTH);
		
		LTBIFieldsMap.put(INV1276_CD_everworkedas,  INV1276_CD);
		LTBIFieldsMap.put(INV1276_C_CD_everworkedas,  INV1276_C_CD);
		LTBIFieldsMap.put(INV1276_H_CD_everworkedas,  INV1276_H_CD);
		LTBIFieldsMap.put(INV1276_M_CD_everworkedas,  INV1276_M_CD);
		LTBIFieldsMap.put(INV1276_U_CD_everworkedas,  INV1276_U_CD);
		LTBIFieldsMap.put(INV1276_N_CD_everworkedas,  INV1276_N_CD);
		
		//Repeating block occupation
		
		LTBIFieldsMap.put(code85659_1_R1_currentOccupationStandarized,  code85659_1_R1);
		LTBIFieldsMap.put(code85659_1_R2_currentOccupationStandarized,  code85659_1_R2);
		LTBIFieldsMap.put(code85659_1_R3_currentOccupationStandarized,  code85659_1_R3);
		
		LTBIFieldsMap.put(code85658_3_R1_currentOccupation, code85658_3_R1);
		LTBIFieldsMap.put(code85658_3_R2_currentOccupation, code85658_3_R2);
		LTBIFieldsMap.put(code85658_3_R3_currentOccupation, code85658_3_R3);
				
		LTBIFieldsMap.put(code85657_5_R1_currentIndustryStandarized,  code85657_5_R1);
		LTBIFieldsMap.put(code85657_5_R2_currentIndustryStandarized,  code85657_5_R2);
		LTBIFieldsMap.put(code85657_5_R3_currentIndustryStandarized,  code85657_5_R3);
	
		LTBIFieldsMap.put(code85078_4_R1_currentIndustry,  code85078_4_R1);
		LTBIFieldsMap.put(code85078_4_R2_currentIndustry,  code85078_4_R2);
		LTBIFieldsMap.put(code85078_4_R3_currentIndustry,  code85078_4_R3);
	
		
		
	}
	
	public static void initializeRisks(){
		
		/****************************************************** Risks ******************************************************/
		
		LTBIFieldsMap.put(ARB016_CD_diabeticAtDiagnosisEvaluation,  ARB016_CD);
		LTBIFieldsMap.put(TB127_CD_homelessInThePast12Months,  TB127_CD);
		LTBIFieldsMap.put(Code32911000_CD_homelessever,  Code32911000_CD);
		LTBIFieldsMap.put(NBS689_CD_correctionalFacility,  NBS689_CD);																																											
		LTBIFieldsMap.put(INV1119_CD_typeoffacility,  INV1119_CD);	
		LTBIFieldsMap.put(INV1119_OTH_typeoffacility,  INV1119_OTH);	
		
		LTBIFieldsMap.put(INV649_CD_correctionalfacilityever,  INV649_CD);																																												
		LTBIFieldsMap.put(INV636_CD_residentoflongtermcarefacility,  INV636_CD);
		LTBIFieldsMap.put(INV1120_CD_typeoffacility,  INV1120_CD);		
		LTBIFieldsMap.put(INV1120_OTH_typeoffacility,  INV1120_OTH);					
		LTBIFieldsMap.put(INV607_CD_injectingdruguseinthepast12months,  INV607_CD);
		LTBIFieldsMap.put(INV608_CD_injectingdruguseinthepast12months,  INV608_CD);
		
		
		LTBIFieldsMap.put(ARB025_CD_HeavyAlcoholUseInThePast12Months,  ARB025_CD);
		LTBIFieldsMap.put(PHC690_CD_TNFAntagonistTherapy,  PHC690_CD);
		LTBIFieldsMap.put(Code161663000_CD_PostOrganTransplantation,  Code161663000_CD);
		LTBIFieldsMap.put(ARB024_CD_EndStageRenalDisease,  ARB024_CD);
		LTBIFieldsMap.put(PHC2236_CD_ViralHepatitisBorCOnly,  PHC2236_CD);
		LTBIFieldsMap.put(VAR126_CD_OtherImmunocompromise,  VAR126_CD);
		LTBIFieldsMap.put(Code72166_2_CD_CurrentSmokingStatusAtDiagnosticEvaluation,  Code72166_2_CD);
		LTBIFieldsMap.put(TRAVEL10_CD_LivedOutsideOfUSForMoreThan2Months,  TRAVEL10_CD);		
		LTBIFieldsMap.put(NBS560_CD_OtherRiskFactor, NBS560_CD);		
		LTBIFieldsMap.put(NBS561_SpecifyOtherRiskFactor,  NBS561);		
		
		
	}
	
	public static void initializeDiagnosisTesting(){
		
		/****************************************************** Diagnostic Testing ******************************************************/

		
		//HIV Status
		LTBIFieldsMap.put(NBS859_CD_HivStatus,  NBS859_CD);	
		LTBIFieldsMap.put(NBS450_Collection_Date,  NBS450);	
		LTBIFieldsMap.put(NBS870_Date_Reported,  NBS870);	
		
		//Tuberculin
		LTBIFieldsMap.put(TUB147_CD_Tuberculin,  TUB147_CD);
		LTBIFieldsMap.put(TUB148_DatePlaced,  TUB148);
		LTBIFieldsMap.put(NBS866_DateRead,  NBS866);
		LTBIFieldsMap.put(TUB149_MMOfInduration,  TUB149);

		//Interferon
		LTBIFieldsMap.put(TUB150_CD_Interferon,  TUB150_CD);		
		LTBIFieldsMap.put(NBS868_CD_TestType,  NBS868_CD);		
		
		LTBIFieldsMap.put(TUB151_CollectionDate,  TUB151);	
		LTBIFieldsMap.put(NBS869_DateReported,  NBS869);	
		LTBIFieldsMap.put(NBS871_QuantitativeResult,  NBS871);	
		LTBIFieldsMap.put(NBS872_CDT_QuantitativeResultUnits,  NBS872_CDT);	
	
		//Sputum Smear
		LTBIFieldsMap.put(TUB120_CD_SputumSmear,  TUB120_CD);
		LTBIFieldsMap.put(TUB121_CollectionDate,  TUB121);
		LTBIFieldsMap.put(NBS863_DateReported,  NBS863);
		
		//Sputum Culture
		LTBIFieldsMap.put(TUB122_CD_SputumCulture,  TUB122_CD);
		LTBIFieldsMap.put(TUB123_CollectionDate,  TUB123);
		LTBIFieldsMap.put(TUB124_DateReported,  TUB124);
		
		//Smear Pathology
		LTBIFieldsMap.put(TUB126_CD_SmearPathology,  TUB126_CD);
		LTBIFieldsMap.put(TUB129_CD_TestType,  TUB129_CD);
		LTBIFieldsMap.put(TUB127_CollectionDate,  TUB127);
		LTBIFieldsMap.put(NBS864_DateReported,  NBS864);
		LTBIFieldsMap.put(TUB128_CDT_SpecimenSource,  TUB128_CDT);
				
		//Culture of Tissue
		LTBIFieldsMap.put(TUB130_CD_CultureOfTissue,  TUB130_CD);
		LTBIFieldsMap.put(TUB131_CollectionDate,  TUB131);
		LTBIFieldsMap.put(TUB132_CDT_SpecimenSource,  TUB132_CDT);
		LTBIFieldsMap.put(TUB133_DateReported,  TUB133);
	
		//Nucleid Acid	
		LTBIFieldsMap.put(TUB135_CD_NucleicAcid,  TUB135_CD);
		LTBIFieldsMap.put(TUB136_CollectionDate,  TUB136);
		LTBIFieldsMap.put(TUB139_DateReported,  TUB139);
		LTBIFieldsMap.put(NBS865_CDT_SpecimenSource,  NBS865_CDT);
		
		//Diagnostic testing repeating
		LTBIFieldsMap.put(INV290_R1_CDT_TestType,  INV290_R1_CDT);
		LTBIFieldsMap.put(LAB165_R1_CDT_SpecimenSource,  LAB165_R1_CDT);
		LTBIFieldsMap.put(LAB163_R1_SpecimenSource,  LAB163_R1);
		LTBIFieldsMap.put(LAB167_R1_DateReported,  LAB167_R1);
		LTBIFieldsMap.put(INV291_R1_CD_testResultQualitative,  INV291_R1_CD);
		LTBIFieldsMap.put(LAB628_R1_QuantitativeResult,  LAB628_R1);
		LTBIFieldsMap.put(LAB115_R1_CD_resultsUnits,  LAB115_R1_CD);
		
		LTBIFieldsMap.put(INV290_R2_CDT_TestType,  INV290_R2_CDT);
		LTBIFieldsMap.put(LAB165_R2_CDT_SpecimenSource,  LAB165_R2_CDT);
		LTBIFieldsMap.put(LAB163_R2_SpecimenSource,  LAB163_R2);
		LTBIFieldsMap.put(LAB167_R2_DateReported,  LAB167_R2);
		LTBIFieldsMap.put(INV291_R2_CD_testResultQualitative,  INV291_R2_CD);
		LTBIFieldsMap.put(LAB628_R2_QuantitativeResult,  LAB628_R2);
		LTBIFieldsMap.put(LAB115_R2_CD_resultsUnits,  LAB115_R2_CD);
		
		LTBIFieldsMap.put(INV290_R3_CDT_TestType,  INV290_R3_CDT);
		LTBIFieldsMap.put(LAB165_R3_CDT_SpecimenSource,  LAB165_R3_CDT);
		LTBIFieldsMap.put(LAB163_R3_SpecimenSource,  LAB163_R3);
		LTBIFieldsMap.put(LAB167_R3_DateReported,  LAB167_R3);
		LTBIFieldsMap.put(INV291_R3_CD_testResultQualitative,  INV291_R3_CD);
		LTBIFieldsMap.put(LAB628_R3_QuantitativeResult,  LAB628_R3);
		LTBIFieldsMap.put(LAB115_R3_CD_resultsUnits,  LAB115_R3_CD);
		
		LTBIFieldsMap.put(INV290_R4_CDT_TestType,  INV290_R4_CDT);
		LTBIFieldsMap.put(LAB165_R4_CDT_SpecimenSource,  LAB165_R4_CDT);
		LTBIFieldsMap.put(LAB163_R4_SpecimenSource,  LAB163_R4);
		LTBIFieldsMap.put(LAB167_R4_DateReported,  LAB167_R4);
		LTBIFieldsMap.put(INV291_R4_CD_testResultQualitative,  INV291_R4_CD);
		LTBIFieldsMap.put(LAB628_R4_QuantitativeResult,  LAB628_R4);
		LTBIFieldsMap.put(LAB115_R4_CD_resultsUnits,  LAB115_R4_CD);
		
		LTBIFieldsMap.put(INV290_R5_CDT_TestType,  INV290_R5_CDT);
		LTBIFieldsMap.put(LAB165_R5_CDT_SpecimenSource,  LAB165_R5_CDT);
		LTBIFieldsMap.put(LAB163_R5_SpecimenSource,  LAB163_R5);
		LTBIFieldsMap.put(LAB167_R5_DateReported,  LAB167_R5);
		LTBIFieldsMap.put(INV291_R5_CD_testResultQualitative,  INV291_R5_CD);
		LTBIFieldsMap.put(LAB628_R5_QuantitativeResult,  LAB628_R5);
		LTBIFieldsMap.put(LAB115_R5_CD_resultsUnits,  LAB115_R5_CD);
		
		LTBIFieldsMap.put(INV290_R6_CDT_TestType,  INV290_R6_CDT);
		LTBIFieldsMap.put(LAB165_R6_CDT_SpecimenSource,  LAB165_R6_CDT);
		LTBIFieldsMap.put(LAB163_R6_SpecimenSource,  LAB163_R6);
		LTBIFieldsMap.put(LAB167_R6_DateReported,  LAB167_R6);
		LTBIFieldsMap.put(INV291_R6_CD_testResultQualitative,  INV291_R6_CD);
		LTBIFieldsMap.put(LAB628_R6_QuantitativeResult,  LAB628_R6);
		LTBIFieldsMap.put(LAB115_R6_CD_resultsUnits,  LAB115_R6_CD);
		
		LTBIFieldsMap.put(INV290_R7_CDT_TestType,  INV290_R7_CDT);
		LTBIFieldsMap.put(LAB165_R7_CDT_SpecimenSource,  LAB165_R7_CDT);
		LTBIFieldsMap.put(LAB163_R7_SpecimenSource,  LAB163_R7);
		LTBIFieldsMap.put(LAB167_R7_DateReported,  LAB167_R7);
		LTBIFieldsMap.put(INV291_R7_CD_testResultQualitative,  INV291_R7_CD);
		LTBIFieldsMap.put(LAB628_R7_QuantitativeResult,  LAB628_R7);
		LTBIFieldsMap.put(LAB115_R7_CD_resultsUnits,  LAB115_R7_CD);

		
		
		
	}
	public static void initializeChest(){
		
		
		/****************************************************** Chest Radiograph and Other Chest Imaging Results ******************************************************/

		LTBIFieldsMap.put(TUB141_CD_InitialChestXRay,  TUB141_CD);
		LTBIFieldsMap.put(LAB681A_XRayDate,  LAB681A);
		LTBIFieldsMap.put(TUB142_CD_evidenceOfCavityXRay,  TUB142_CD);
		LTBIFieldsMap.put(TUB143_CD_evidenceOfMiliaryTBXRay,  TUB143_CD);
		LTBIFieldsMap.put(TUB144_CD_InitialChestCTScan,  TUB144_CD);
		LTBIFieldsMap.put(LAB681B_CTScanDate,  LAB681B);
		LTBIFieldsMap.put(TUB145_CD_evidenceOfCavityCTScan,  TUB145_CD);
		LTBIFieldsMap.put(TUB146_CD_evidenceOfMiliaryTBCTScan,  TUB146_CD);
		
		//Chest repeating
		LTBIFieldsMap.put(LAB677_R1_CD_ChestTestType,  LAB677_R1_CD);
		LTBIFieldsMap.put(LAB677_R1_OTH_ChestTestType,  LAB677_R1_OTH);
		LTBIFieldsMap.put(LAB681_R1_ChestDateOfStudy,  LAB681_R1);
		LTBIFieldsMap.put(LAB678_R1_CD_ChestResultOfStudy,  LAB678_R1_CD);
		LTBIFieldsMap.put(LAB679_R1_CD_ChestEvidenceOfCavity,  LAB679_R1_CD);
		LTBIFieldsMap.put(LAB680_R1_CD_chestEvidenceOfMiliary,  LAB680_R1_CD);
		
		LTBIFieldsMap.put(LAB677_R2_CD_ChestTestType,  LAB677_R2_CD);
		LTBIFieldsMap.put(LAB677_R2_OTH_ChestTestType,  LAB677_R2_OTH);
		LTBIFieldsMap.put(LAB681_R2_ChestDateOfStudy,  LAB681_R2);
		LTBIFieldsMap.put(LAB678_R2_CD_ChestResultOfStudy,  LAB678_R2_CD);
		LTBIFieldsMap.put(LAB679_R2_CD_ChestEvidenceOfCavity,  LAB679_R2_CD);
		LTBIFieldsMap.put(LAB680_R2_CD_chestEvidenceOfMiliary,  LAB680_R2_CD);
		
		LTBIFieldsMap.put(LAB677_R3_CD_ChestTestType,  LAB677_R3_CD);
		LTBIFieldsMap.put(LAB677_R3_OTH_ChestTestType,  LAB677_R3_OTH);
		LTBIFieldsMap.put(LAB681_R3_ChestDateOfStudy,  LAB681_R3);
		LTBIFieldsMap.put(LAB678_R3_CD_ChestResultOfStudy,  LAB678_R3_CD);
		LTBIFieldsMap.put(LAB679_R3_CD_ChestEvidenceOfCavity,  LAB679_R3_CD);
		LTBIFieldsMap.put(LAB680_R3_CD_chestEvidenceOfMiliary,  LAB680_R3_CD);
		
	}
	
	public static void initializeEpidemiologic(){
		
		/****************************************************** Epidemiologic Investigation ******************************************************/

		LTBIFieldsMap.put(INV1274_CD_caseMeetBinational,  INV1274_CD);
		LTBIFieldsMap.put(INV515_CD_whichCriteriaWereMet,  INV515_CD);
		LTBIFieldsMap.put(INV515_S_CD_whichCriteriaWereMet,  INV515_S_CD);
		LTBIFieldsMap.put(INV515_C_CD_whichCriteriaWereMet,  INV515_C_CD);
		LTBIFieldsMap.put(INV515_R_CD_whichCriteriaWereMet,  INV515_R_CD);
		LTBIFieldsMap.put(INV515_W_CD_whichCriteriaWereMet,  INV515_W_CD);
		LTBIFieldsMap.put(INV515_B_CD_whichCriteriaWereMet,  INV515_B_CD);
		LTBIFieldsMap.put(INV515_O_CD_whichCriteriaWereMet,  INV515_O_CD);
		LTBIFieldsMap.put(INV1122_CD_caseIdentifiedDuringTheContactInvestigation,  INV1122_CD);
		LTBIFieldsMap.put(INV1123_CD_evaluatedForTB,  INV1123_CD);
		LTBIFieldsMap.put(INV1134_CD_contactInvestigationConducted,  INV1134_CD);
		LTBIFieldsMap.put(INV1124_R1_linkedStateCaseNumber,  INV1124_R1);
		LTBIFieldsMap.put(INV1124_R2_linkedStateCaseNumber,  INV1124_R2);
		LTBIFieldsMap.put(INV1124_R3_linkedStateCaseNumber,  INV1124_R3);
		LTBIFieldsMap.put(INV1124_R4_linkedStateCaseNumber,  INV1124_R4);
		LTBIFieldsMap.put(INV1124_R5_linkedStateCaseNumber,  INV1124_R5);
		LTBIFieldsMap.put(INV1124_R6_linkedStateCaseNumber,  INV1124_R6);
		LTBIFieldsMap.put(INV1124_R7_linkedStateCaseNumber,  INV1124_R7);
		LTBIFieldsMap.put(INV1124_R8_linkedStateCaseNumber,  INV1124_R8);
		
	}
	
	public static void initializeTreatmentAndOutcome(){
		
		/****************************************************** Treatment and Outcome Information ******************************************************/

		LTBIFieldsMap.put(INV1128_LTBITherapyStarted,  INV1128);
		LTBIFieldsMap.put(INV924_TreatmentStartDate,  INV924);
		LTBIFieldsMap.put(code63939_3_DateTherapyStopped,  code63939_3);
		LTBIFieldsMap.put(INV1129_CD_SpecifyInitialRegimen,  INV1129_CD);
		LTBIFieldsMap.put(INV1129_OTH_SpecifyInitialRegimenOther,  INV1129_OTH);
		LTBIFieldsMap.put(INV1130_CD_WhyLTBITreatmentWasNotStarted,  INV1130_CD);
		LTBIFieldsMap.put(INV1130_OTH_WhyLTBITreatmentWasNotStartedOther,  INV1130_OTH);
		
		
		
		LTBIFieldsMap.put(code55753_8B_CD_treatmentAdministration,  code55753_8B_CD);
		LTBIFieldsMap.put(code55753_8B_D_CD_treatmentAdministration,  code55753_8B_D_CD);
		LTBIFieldsMap.put(code55753_8B_E_CD_treatmentAdministration,  code55753_8B_E_CD);
		LTBIFieldsMap.put(code55753_8B_S_CD_treatmentAdministration,  code55753_8B_S_CD);
		
		
		LTBIFieldsMap.put(INV1131_CD_ReasonTherapyStopped,  INV1131_CD);
		LTBIFieldsMap.put(INV1131_OTH_ReasonTherapyStoppedOther, INV1131_OTH);
		LTBIFieldsMap.put(INV1132_NTSSCaseNumber,  INV1132);
		LTBIFieldsMap.put(code64750_3_CD_SevereAdverseEvent,  code64750_3_CD);
		LTBIFieldsMap.put(code64750_3_D_CD_SevereAdverseEvent,  code64750_3_D_CD);
		LTBIFieldsMap.put(code64750_3_H_CD_SevereAdverseEvent,  code64750_3_H_CD);

		LTBIFieldsMap.put(INV167_InvestigationComments,  INV167);
	
		

		
	}
}
