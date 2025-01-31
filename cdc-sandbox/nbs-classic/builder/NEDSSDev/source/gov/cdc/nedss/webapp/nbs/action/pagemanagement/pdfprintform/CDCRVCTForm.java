package gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform;

import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.webapp.nbs.form.page.PageForm;

import java.io.File;
import java.io.IOException;
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
 * PDF generating/populating class for "RVCT form"
 * @author Fatima Lopez Calzado
 *
 */
public class CDCRVCTForm extends CommonPDFPrintForm{
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
	
	//R8
	private static String INV290_R8_CDT = "INV290_R8_CDT";
	protected static  String INV290_R8_CDT_TestType = INV290_R8_CDT+delimiter1+"TestType_INV290_R8_CDT";																																														
	
	private static String LAB165_R8_CDT = "LAB165_R8_CDT";
	protected static String LAB165_R8_CDT_SpecimenSource = LAB165_R8_CDT+delimiter1+"SpecimenSource_LAB165_R8_CDT";
	
	private static String LAB163_R8 = "LAB163_R8";
	protected static String LAB163_R8_SpecimenSource = LAB163_R8+delimiter1+"DateCollectedPlaced_LAB163_R8";
	
	private static String LAB167_R8 = "LAB167_R8";
	protected static String LAB167_R8_DateReported = LAB167_R8+delimiter1+"DateReported_LAB167_R8";
	
	private static String INV291_R8_CD = "INV291_R8_CD";
	protected static String INV291_R8_CD_testResultQualitative = INV291_R8_CD+delimiter1+"testResultQualitative_INV291_R8_CD";
	
	private static String LAB628_R8 = "LAB628_R8";
	protected static String LAB628_R8_QuantitativeResult = LAB628_R8+delimiter1+"QuantitativeResult_LAB628_R8";
	
	private static String LAB115_R8_CD = "LAB115_R8_CD";
	protected static String LAB115_R8_CD_resultsUnits = LAB115_R8_CD+delimiter1+"resultsUnits_LAB115_R8_CD";
	

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

	
	/******************************************************Clinical History and Findings******************************************************/

	private static String code161413004_CD = "161413004_CD";
	protected static String code161413004_CD_previouslyDiagnosed = code161413004_CD+delimiter1+"previouslyDiagnosed_161413004_CD";
	private static String INV1135_R1_CD = "INV1135_R1_CD";
	protected static String INV1135_R1_CD_diagnosisType = INV1135_R1_CD+delimiter1+"diagnosisType_INV1135_R1_CD";
	private static String code82758_4_R1 = "82758_4_R1";
	protected static String code82758_4_R1_dateOfDiagnosis = code82758_4_R1+delimiter1+"dateOfDiagnosis_82758_4_R1";
	private static String INV1136_R1 = "INV1136_R1";
	protected static String INV1136_R1_previousStateCaseNumber = INV1136_R1+delimiter1+"previousStateCaseNumber_INV1136_R1";
	private static String INV1137_R1_CD = "INV1137_R1_CD";
	protected static String INV1137_R1_CD_completedTreatment = INV1137_R1_CD+delimiter1+"completedTreatment_INV1137_R1_CD";
	private static String INV1135_R2_CD = "INV1135_R2_CD";
	protected static String INV1135_R2_CD_diagnosisType = INV1135_R2_CD+delimiter1+"diagnosisType_INV1135_R2_CD";
	private static String code82758_4_R2 = "82758_4_R2";
	protected static String code82758_4_R2_dateOfDiagnosis = code82758_4_R2+delimiter1+"dateOfDiagnosis_82758_4_R2";
	private static String INV1136_R2 = "INV1136_R2";
	protected static String INV1136_R2_previousStateCaseNumber = INV1136_R2+delimiter1+"previousStateCaseNumber_INV1136_R2";
	private static String INV1137_R2_CD = "INV1137_R2_CD";
	protected static String INV1137_R2_CD_completedTreatment = INV1137_R2_CD+delimiter1+"completedTreatment_INV1137_R2_CD";
	private static String INV1135_R3_CD = "INV1135_R3_CD";
	protected static String INV1135_R3_CD_diagnosisType = INV1135_R3_CD+delimiter1+"diagnosisType_INV1135_R3_CD";
	private static String code82758_4_R3 = "82758_4_R3";
	protected static String code82758_4_R3_dateOfDiagnosis = code82758_4_R3+delimiter1+"dateOfDiagnosis_82758_4_R3";
	private static String INV1136_R3 = "INV1136_R3";
	protected static String INV1136_R3_previousStateCaseNumber = INV1136_R3+delimiter1+"previousStateCaseNumber_INV1136_R3";
	private static String INV1137_R3_CD = "INV1137_R3_CD";
	protected static String INV1137_R3_CD_completedTreatment = INV1137_R3_CD+delimiter1+"completedTreatment_INV1137_R3_CD";
	
	private static String INV137 = "INV137";
	protected static String INV137_dateOfIllnessOnset = INV137+delimiter1+"dateOfIllnessOnset_INV137";
	
	
	private static String INV1133secondarySites = "INV1133_SecondarySites";
	private static String INV1133_secondarySitesOfTBDisease = INV1133secondarySites+delimiter1+"secondarySitesOfTBDisease_INV1133SecondarySites";
	
	private static String INV1133_CD = "INV1133_CD";
	private static String INV1133_CD_sitesOfTBDisease = INV1133_CD+delimiter1+"sitesOfTBDisease_INV1133_CD";
	private static String INV1133_OTH = "INV1133_OTH";
	private static String INV1133_OTH_sitesOfTBDisease = INV1133_OTH+delimiter1+"sitesOfTBDisease_INV1133_CD";
	
	private static String INV1133_PU_CD = "INV1133_PU_CD";
	protected static String INV1133_PU_CD_sitesOfTBDisease = INV1133_PU_CD+delimiter1+"sitesOfTBDisease_INV1133_PU_CD";
	private static String INV1133_PL_CD = "INV1133_PL_CD";
	protected static String INV1133_PL_CD_sitesOfTBDisease = INV1133_PL_CD+delimiter1+"sitesOfTBDisease_INV1133_PL_CD";
	private static String INV1133_SI_CD = "INV1133_SI_CD";
	protected static String INV1133_SI_CD_sitesOfTBDisease = INV1133_SI_CD+delimiter1+"sitesOfTBDisease_INV1133_SI_CD";
	private static String INV1133_LC_CD = "INV1133_LC_CD";
	protected static String INV1133_LC_CD_sitesOfTBDisease = INV1133_LC_CD+delimiter1+"sitesOfTBDisease_INV1133_LC_CD";
	private static String INV1133_LI_CD = "INV1133_LI_CD";
	protected static String INV1133_LI_CD_sitesOfTBDisease = INV1133_LI_CD+delimiter1+"sitesOfTBDisease_INV1133_LI_CD";
	private static String INV1133_LA_CD = "INV1133_LA_CD";
	protected static String INV1133_LA_CD_sitesOfTBDisease = INV1133_LA_CD+delimiter1+"sitesOfTBDisease_INV1133_LA_CD";
	private static String INV1133_LO_CD = "INV1133_LO_CD";
	protected static String INV1133_LO_CD_sitesOfTBDisease = INV1133_LO_CD+delimiter1+"sitesOfTBDisease_INV1133_LO_CD";
	private static String INV1133_LU_CD = "INV1133_LU_CD";
	protected static String INV1133_LU_CD_sitesOfTBDisease = INV1133_LU_CD+delimiter1+"sitesOfTBDisease_INV1133_LU_CD";
	private static String INV1133_L_CD = "INV1133_L_CD";
	protected static String INV1133_L_CD_sitesOfTBDisease = INV1133_L_CD+delimiter1+"sitesOfTBDisease_INV1133_L_CD";
	private static String INV1133_B_CD = "INV1133_B_CD";
	protected static String INV1133_B_CD_sitesOfTBDisease = INV1133_B_CD+delimiter1+"sitesOfTBDisease_INV1133_B_CD";
	private static String INV1133_G_CD = "INV1133_G_CD";
	protected static String INV1133_G_CD_sitesOfTBDisease = INV1133_G_CD+delimiter1+"sitesOfTBDisease_INV1133_G_CD";
	private static String INV1133_M_CD = "INV1133_M_CD";
	protected static String INV1133_M_CD_sitesOfTBDisease = INV1133_M_CD+delimiter1+"sitesOfTBDisease_INV1133_M_CD";
	private static String INV1133_P_CD = "INV1133_P_CD";
	protected static String INV1133_P_CD_sitesOfTBDisease = INV1133_P_CD+delimiter1+"sitesOfTBDisease_INV1133_P_CD";
	private static String INV1133_O_CD = "INV1133_O_CD";
	protected static String INV1133_O_CD_sitesOfTBDisease = INV1133_O_CD+delimiter1+"sitesOfTBDisease_INV1133_O_CD";

	
	/******************************************************Initial Drug Regimen******************************************************/
	
	private static String VAR141 = "VAR141";
	protected static String VAR141_DateTherapyStarted = VAR141+delimiter1+"DateTherapyStarted_VAR141";
	private static String INV1139_CD = "INV1139_CD";
	protected static String INV1139_CD_InitialDrugRegimenNOTRIPEHRZE = INV1139_CD+delimiter1+"InitialDrugRegimenNOTRIPEHRZE_INV1139_CD";
	private static String INV1139_OTH = "INV1139_OTH";
	protected static String INV1139_OTH_InitialDrugRegimenNOTRIPEHRZE = INV1139_OTH+delimiter1+"InitialDrugRegimenNOTRIPEHRZE_INV1139_OTH";
	private static String code6038_CD = "6038_CD";
	protected static String code6038_CD_Isoniazid = code6038_CD+delimiter1+"Isoniazid_6038_CD";
	private static String code9384_CD = "9384_CD";
	protected static String code9384_CD_Rifampin = code9384_CD+delimiter1+"Rifampin_9384_CD";
	private static String code8987_CD = "8987_CD";
	protected static String code8987_CD_Pyrazinamide = code8987_CD+delimiter1+"Pyrazinamide_8987_CD";
	private static String code4110_CD = "4110_CD";
	protected static String code4110_CD_Ethambutol = code4110_CD+delimiter1+"Ethambutol_4110_CD";
	private static String code10109_CD = "10109_CD";
	protected static String code10109_CD_Streptomycin = code10109_CD+delimiter1+"Streptomycin_10109_CD";
	private static String code55672_CD = "55672_CD";
	protected static String code55672_CD_Rifabutin = code55672_CD+delimiter1+"Rifabutin_55672_CD";
	private static String code35617_CD = "35617_CD";
	protected static String code35617_CD_Rifapentine = code35617_CD+delimiter1+"Rifapentine_35617_CD";
	private static String code4127_CD = "4127_CD";
	protected static String code4127_CD_Ethionamide = code4127_CD+delimiter1+"Ethionamide_4127_CD";
	private static String code641_CD = "641_CD";
	protected static String code641_CD_Amikacin = code641_CD+delimiter1+"Amikacin_641_CD";
	private static String code6099_CD = "6099_CD";
	protected static String code6099_CD_Kanamycin = code6099_CD+delimiter1+"Kanamycin_6099_CD";
	private static String code78903_CD = "78903_CD";
	protected static String code78903_CD_Capreomycin = code78903_CD+delimiter1+"Capreomycin_78903_CD";
	private static String code2551_CD = "2551_CD";
	protected static String code2551_CD_Ciprofloxacin = code2551_CD+delimiter1+"Ciprofloxacin_2551_CD";
	private static String code82122_CD = "82122_CD";
	protected static String code82122_CD_Levofloxacin = code82122_CD+delimiter1+"Levofloxacin_82122_CD";
	private static String code7623_CD = "7623_CD";
	protected static String code7623_CD_Ofloxacin = code7623_CD+delimiter1+"Ofloxacin_7623_CD";
	private static String code139462_CD = "139462_CD";
	protected static String code139462_CD_Moxifloxacin = code139462_CD+delimiter1+"Moxifloxacin_139462_CD";
	private static String PHC1888_CD = "PHC1888_CD";
	protected static String PHC1888_CD_OtherQuinolones = PHC1888_CD+delimiter1+"Other Quinolones_PHC1888_CD";
	private static String code3007_CD = "3007_CD";
	protected static String code3007_CD_Cycloserine = code3007_CD+delimiter1+"Cycloserine_3007_CD";
	private static String code7833_CD = "7833_CD";
	protected static String code7833_CD_ParaAminesalicylicacid = code7833_CD+delimiter1+"Para-Aminesalicylicacid_7833_CD";
	private static String code190376_CD = "190376_CD";
	protected static String code190376_CD_Linezolid = code190376_CD+delimiter1+"Linezolid_190376_CD";
	private static String code1364504_CD = "1364504_CD";
	protected static String code1364504_CD_Bedaquiline = code1364504_CD+delimiter1+"Bedaquiline_1364504_CD";
	private static String PHC1889_CD = "PHC1889_CD";
	protected static String PHC1889_CD_Delamanid = PHC1889_CD+delimiter1+"Delamanid_PHC1889_CD";
	private static String code2592_CD = "2592_CD";
	protected static String code2592_CD_Clofazimine = code2592_CD+delimiter1+"Clofazimine_2592_CD";
	private static String code2198359_CD = "2198359_CD";
	protected static String code2198359_CD_Pretomanid = code2198359_CD+delimiter1+"Pretomanid_2198359_CD";
	private static String NBS456_CD = "NBS456_CD";
	protected static String NBS456_CD_OtherDrugRegimen = NBS456_CD+delimiter1+"Other Drug Regimen_NBS456_CD";
	private static String NBS456_OTH = "NBS456_OTH";
	protected static String NBS456_OTH_Specify = NBS456_OTH+delimiter1+"Specify:_NBS456_OTH";

	/******************************************************Genotyping And Drug Susceptibility Testing (Phenotypic)******************************************************/
	
	private static String INV1145_CD = "INV1145_CD";
	protected static String INV1145_CD_IsolateSubmittedForGenotyping = INV1145_CD+delimiter1+"IsolateSubmittedForGenotyping_INV1145_CD";
	private static String LAB125 = "LAB125";
	protected static String LAB125_AccessionNumberGen = LAB125+delimiter1+"AccessionNumberGen_LAB125";
	private static String INV1147_CD = "INV1147_CD";
	protected static String INV1147_CD_WasPhenotypic = INV1147_CD+delimiter1+"WasPhenotypic_INV1147_CD";
	
	private static String LABAST6_R1_CDT = "LABAST6_R1_CDT";
	protected static String LABAST6_R1_CDT_DrugName = LABAST6_R1_CDT+delimiter1+"DrugName_LABAST6_R1_CDT";
	private static String LABAST5_R1 = "LABAST5_R1";
	protected static String LABAST5_R1_DateCollected = LABAST5_R1+delimiter1+"DateCollected_LABAST5_R1";
	private static String LABAST14_R1 = "LABAST14_R1";
	protected static String LABAST14_R1_DateReported = LABAST14_R1+delimiter1+"DateReported_LABAST14_R1";
	private static String LABAST3_R1_CDT = "LABAST3_R1_CDT";
	protected static String LABAST3_R1_CDT_SpecimenSource = LABAST3_R1_CDT+delimiter1+"SpecimenSource_LABAST3_R1_CDT";
	private static String LABAST8_R1_CD = "LABAST8_R1_CD";
	protected static String LABAST8_R1_CD_Result = LABAST8_R1_CD+delimiter1+"Result_LABAST8_R1_CD";
	private static String LABAST7_R1_CDT = "LABAST7_R1_CDT";
	protected static String LABAST7_R1_CDT_TestMethodOptional = LABAST7_R1_CDT+delimiter1+"TestMethodOptional_LABAST7_R1_CDT";
	
	private static String LABAST6_R2_CDT = "LABAST6_R2_CDT";
	protected static String LABAST6_R2_CDT_DrugName = LABAST6_R2_CDT+delimiter1+"DrugName_LABAST6_R2_CDT";
	private static String LABAST5_R2 = "LABAST5_R2";
	protected static String LABAST5_R2_DateCollected = LABAST5_R2+delimiter1+"DateCollected_LABAST5_R2";
	private static String LABAST14_R2 = "LABAST14_R2";
	protected static String LABAST14_R2_DateReported = LABAST14_R2+delimiter1+"DateReported_LABAST14_R2";
	private static String LABAST3_R2_CDT = "LABAST3_R2_CDT";
	protected static String LABAST3_R2_CDT_SpecimenSource = LABAST3_R2_CDT+delimiter1+"SpecimenSource_LABAST3_R2_CDT";
	private static String LABAST8_R2_CD = "LABAST8_R2_CD";
	protected static String LABAST8_R2_CD_Result = LABAST8_R2_CD+delimiter1+"Result_LABAST8_R2_CD";
	private static String LABAST7_R2_CDT = "LABAST7_R2_CDT";
	protected static String LABAST7_R2_CDT_TestMethodOptional = LABAST7_R2_CDT+delimiter1+"TestMethodOptional_LABAST7_R2_CDT";

	private static String LABAST6_R3_CDT = "LABAST6_R3_CDT";
	protected static String LABAST6_R3_CDT_DrugName = LABAST6_R3_CDT+delimiter1+"DrugName_LABAST6_R3_CDT";
	private static String LABAST5_R3 = "LABAST5_R3";
	protected static String LABAST5_R3_DateCollected = LABAST5_R3+delimiter1+"DateCollected_LABAST5_R3";
	private static String LABAST14_R3 = "LABAST14_R3";
	protected static String LABAST14_R3_DateReported = LABAST14_R3+delimiter1+"DateReported_LABAST14_R3";
	private static String LABAST3_R3_CDT = "LABAST3_R3_CDT";
	protected static String LABAST3_R3_CDT_SpecimenSource = LABAST3_R3_CDT+delimiter1+"SpecimenSource_LABAST3_R3_CDT";
	private static String LABAST8_R3_CD = "LABAST8_R3_CD";
	protected static String LABAST8_R3_CD_Result = LABAST8_R3_CD+delimiter1+"Result_LABAST8_R3_CD";
	private static String LABAST7_R3_CDT = "LABAST7_R3_CDT";
	protected static String LABAST7_R3_CDT_TestMethodOptional = LABAST7_R3_CDT+delimiter1+"TestMethodOptional_LABAST7_R3_CDT";

	private static String LABAST6_R4_CDT = "LABAST6_R4_CDT";
	protected static String LABAST6_R4_CDT_DrugName = LABAST6_R4_CDT+delimiter1+"DrugName_LABAST6_R4_CDT";
	private static String LABAST5_R4 = "LABAST5_R4";
	protected static String LABAST5_R4_DateCollected = LABAST5_R4+delimiter1+"DateCollected_LABAST5_R4";
	private static String LABAST14_R4 = "LABAST14_R4";
	protected static String LABAST14_R4_DateReported = LABAST14_R4+delimiter1+"DateReported_LABAST14_R4";
	private static String LABAST3_R4_CDT = "LABAST3_R4_CDT";
	protected static String LABAST3_R4_CDT_SpecimenSource = LABAST3_R4_CDT+delimiter1+"SpecimenSource_LABAST3_R4_CDT";
	private static String LABAST8_R4_CD = "LABAST8_R4_CD";
	protected static String LABAST8_R4_CD_Result = LABAST8_R4_CD+delimiter1+"Result_LABAST8_R4_CD";
	private static String LABAST7_R4_CDT = "LABAST7_R4_CDT";
	protected static String LABAST7_R4_CDT_TestMethodOptional = LABAST7_R4_CDT+delimiter1+"TestMethodOptional_LABAST7_R4_CDT";

	private static String LABAST6_R5_CDT = "LABAST6_R5_CDT";
	protected static String LABAST6_R5_CDT_DrugName = LABAST6_R5_CDT+delimiter1+"DrugName_LABAST6_R5_CDT";
	private static String LABAST5_R5 = "LABAST5_R5";
	protected static String LABAST5_R5_DateCollected = LABAST5_R5+delimiter1+"DateCollected_LABAST5_R5";
	private static String LABAST14_R5 = "LABAST14_R5";
	protected static String LABAST14_R5_DateReported = LABAST14_R5+delimiter1+"DateReported_LABAST14_R5";
	private static String LABAST3_R5_CDT = "LABAST3_R5_CDT";
	protected static String LABAST3_R5_CDT_SpecimenSource = LABAST3_R5_CDT+delimiter1+"SpecimenSource_LABAST3_R5_CDT";
	private static String LABAST8_R5_CD = "LABAST8_R5_CD";
	protected static String LABAST8_R5_CD_Result = LABAST8_R5_CD+delimiter1+"Result_LABAST8_R5_CD";
	private static String LABAST7_R5_CDT = "LABAST7_R5_CDT";
	protected static String LABAST7_R5_CDT_TestMethodOptional = LABAST7_R5_CDT+delimiter1+"TestMethodOptional_LABAST7_R5_CDT";

	private static String LABAST6_R6_CDT = "LABAST6_R6_CDT";
	protected static String LABAST6_R6_CDT_DrugName = LABAST6_R6_CDT+delimiter1+"DrugName_LABAST6_R6_CDT";
	private static String LABAST5_R6 = "LABAST5_R6";
	protected static String LABAST5_R6_DateCollected = LABAST5_R6+delimiter1+"DateCollected_LABAST5_R6";
	private static String LABAST14_R6 = "LABAST14_R6";
	protected static String LABAST14_R6_DateReported = LABAST14_R6+delimiter1+"DateReported_LABAST14_R6";
	private static String LABAST3_R6_CDT = "LABAST3_R6_CDT";
	protected static String LABAST3_R6_CDT_SpecimenSource = LABAST3_R6_CDT+delimiter1+"SpecimenSource_LABAST3_R6_CDT";
	private static String LABAST8_R6_CD = "LABAST8_R6_CD";
	protected static String LABAST8_R6_CD_Result = LABAST8_R6_CD+delimiter1+"Result_LABAST8_R6_CD";
	private static String LABAST7_R6_CDT = "LABAST7_R6_CDT";
	protected static String LABAST7_R6_CDT_TestMethodOptional = LABAST7_R6_CDT+delimiter1+"TestMethodOptional_LABAST7_R6_CDT";

	private static String LABAST6_R7_CDT = "LABAST6_R7_CDT";
	protected static String LABAST6_R7_CDT_DrugName = LABAST6_R7_CDT+delimiter1+"DrugName_LABAST6_R7_CDT";
	private static String LABAST5_R7 = "LABAST5_R7";
	protected static String LABAST5_R7_DateCollected = LABAST5_R7+delimiter1+"DateCollected_LABAST5_R7";
	private static String LABAST14_R7 = "LABAST14_R7";
	protected static String LABAST14_R7_DateReported = LABAST14_R7+delimiter1+"DateReported_LABAST14_R7";
	private static String LABAST3_R7_CDT = "LABAST3_R7_CDT";
	protected static String LABAST3_R7_CDT_SpecimenSource = LABAST3_R7_CDT+delimiter1+"SpecimenSource_LABAST3_R7_CDT";
	private static String LABAST8_R7_CD = "LABAST8_R7_CD";
	protected static String LABAST8_R7_CD_Result = LABAST8_R7_CD+delimiter1+"Result_LABAST8_R7_CD";
	private static String LABAST7_R7_CDT = "LABAST7_R7_CDT";
	protected static String LABAST7_R7_CDT_TestMethodOptional = LABAST7_R7_CDT+delimiter1+"TestMethodOptional_LABAST7_R7_CDT";
	
	private static String LABAST6_R8_CDT = "LABAST6_R8_CDT";
	protected static String LABAST6_R8_CDT_DrugName = LABAST6_R8_CDT+delimiter1+"DrugName_LABAST6_R8_CDT";
	private static String LABAST5_R8 = "LABAST5_R8";
	protected static String LABAST5_R8_DateCollected = LABAST5_R8+delimiter1+"DateCollected_LABAST5_R8";
	private static String LABAST14_R8 = "LABAST14_R8";
	protected static String LABAST14_R8_DateReported = LABAST14_R8+delimiter1+"DateReported_LABAST14_R8";
	private static String LABAST3_R8_CDT = "LABAST3_R8_CDT";
	protected static String LABAST3_R8_CDT_SpecimenSource = LABAST3_R8_CDT+delimiter1+"SpecimenSource_LABAST3_R8_CDT";
	private static String LABAST8_R8_CD = "LABAST8_R8_CD";
	protected static String LABAST8_R8_CD_Result = LABAST8_R8_CD+delimiter1+"Result_LABAST8_R8_CD";
	private static String LABAST7_R8_CDT = "LABAST7_R8_CDT";
	protected static String LABAST7_R8_CDT_TestMethodOptional = LABAST7_R8_CDT+delimiter1+"TestMethodOptional_LABAST7_R8_CDT";

	private static String LABAST6_R9_CDT = "LABAST6_R9_CDT";
	protected static String LABAST6_R9_CDT_DrugName = LABAST6_R9_CDT+delimiter1+"DrugName_LABAST6_R9_CDT";
	private static String LABAST5_R9 = "LABAST5_R9";
	protected static String LABAST5_R9_DateCollected = LABAST5_R9+delimiter1+"DateCollected_LABAST5_R9";
	private static String LABAST14_R9 = "LABAST14_R9";
	protected static String LABAST14_R9_DateReported = LABAST14_R9+delimiter1+"DateReported_LABAST14_R9";
	private static String LABAST3_R9_CDT = "LABAST3_R9_CDT";
	protected static String LABAST3_R9_CDT_SpecimenSource = LABAST3_R9_CDT+delimiter1+"SpecimenSource_LABAST3_R9_CDT";
	private static String LABAST8_R9_CD = "LABAST8_R9_CD";
	protected static String LABAST8_R9_CD_Result = LABAST8_R9_CD+delimiter1+"Result_LABAST8_R9_CD";
	private static String LABAST7_R9_CDT = "LABAST7_R9_CDT";
	protected static String LABAST7_R9_CDT_TestMethodOptional = LABAST7_R9_CDT+delimiter1+"TestMethodOptional_LABAST7_R9_CDT";

	private static String LABAST6_R10_CDT = "LABAST6_R10_CDT";
	protected static String LABAST6_R10_CDT_DrugName = LABAST6_R10_CDT+delimiter1+"DrugName_LABAST6_R10_CDT";
	private static String LABAST5_R10 = "LABAST5_R10";
	protected static String LABAST5_R10_DateCollected = LABAST5_R10+delimiter1+"DateCollected_LABAST5_R10";
	private static String LABAST14_R10 = "LABAST14_R10";
	protected static String LABAST14_R10_DateReported = LABAST14_R10+delimiter1+"DateReported_LABAST14_R10";
	private static String LABAST3_R10_CDT = "LABAST3_R10_CDT";
	protected static String LABAST3_R10_CDT_SpecimenSource = LABAST3_R10_CDT+delimiter1+"SpecimenSource_LABAST3_R10_CDT";
	private static String LABAST8_R10_CD = "LABAST8_R10_CD";
	protected static String LABAST8_R10_CD_Result = LABAST8_R10_CD+delimiter1+"Result_LABAST8_R10_CD";
	private static String LABAST7_R10_CDT = "LABAST7_R10_CDT";
	protected static String LABAST7_R10_CDT_TestMethodOptional = LABAST7_R10_CDT+delimiter1+"TestMethodOptional_LABAST7_R10_CDT";

	private static String LABAST6_R11_CDT = "LABAST6_R11_CDT";
	protected static String LABAST6_R11_CDT_DrugName = LABAST6_R11_CDT+delimiter1+"DrugName_LABAST6_R11_CDT";
	private static String LABAST5_R11 = "LABAST5_R11";
	protected static String LABAST5_R11_DateCollected = LABAST5_R11+delimiter1+"DateCollected_LABAST5_R11";
	private static String LABAST14_R11 = "LABAST14_R11";
	protected static String LABAST14_R11_DateReported = LABAST14_R11+delimiter1+"DateReported_LABAST14_R11";
	private static String LABAST3_R11_CDT = "LABAST3_R11_CDT";
	protected static String LABAST3_R11_CDT_SpecimenSource = LABAST3_R11_CDT+delimiter1+"SpecimenSource_LABAST3_R11_CDT";
	private static String LABAST8_R11_CD = "LABAST8_R11_CD";
	protected static String LABAST8_R11_CD_Result = LABAST8_R11_CD+delimiter1+"Result_LABAST8_R11_CD";
	private static String LABAST7_R11_CDT = "LABAST7_R11_CDT";
	protected static String LABAST7_R11_CDT_TestMethodOptional = LABAST7_R11_CDT+delimiter1+"TestMethodOptional_LABAST7_R11_CDT";
	
	private static String LABAST6_R12_CDT = "LABAST6_R12_CDT";
	protected static String LABAST6_R12_CDT_DrugName = LABAST6_R12_CDT+delimiter1+"DrugName_LABAST6_R12_CDT";
	private static String LABAST5_R12 = "LABAST5_R12";
	protected static String LABAST5_R12_DateCollected = LABAST5_R12+delimiter1+"DateCollected_LABAST5_R12";
	private static String LABAST14_R12 = "LABAST14_R12";
	protected static String LABAST14_R12_DateReported = LABAST14_R12+delimiter1+"DateReported_LABAST14_R12";
	private static String LABAST3_R12_CDT = "LABAST3_R12_CDT";
	protected static String LABAST3_R12_CDT_SpecimenSource = LABAST3_R12_CDT+delimiter1+"SpecimenSource_LABAST3_R12_CDT";
	private static String LABAST8_R12_CD = "LABAST8_R12_CD";
	protected static String LABAST8_R12_CD_Result = LABAST8_R12_CD+delimiter1+"Result_LABAST8_R12_CD";
	private static String LABAST7_R12_CDT = "LABAST7_R12_CDT";
	protected static String LABAST7_R12_CDT_TestMethodOptional = LABAST7_R12_CDT+delimiter1+"TestMethodOptional_LABAST7_R12_CDT";

	private static String LABAST6_R13_CDT = "LABAST6_R13_CDT";
	protected static String LABAST6_R13_CDT_DrugName = LABAST6_R13_CDT+delimiter1+"DrugName_LABAST6_R13_CDT";
	private static String LABAST5_R13 = "LABAST5_R13";
	protected static String LABAST5_R13_DateCollected = LABAST5_R13+delimiter1+"DateCollected_LABAST5_R13";
	private static String LABAST14_R13 = "LABAST14_R13";
	protected static String LABAST14_R13_DateReported = LABAST14_R13+delimiter1+"DateReported_LABAST14_R13";
	private static String LABAST3_R13_CDT = "LABAST3_R13_CDT";
	protected static String LABAST3_R13_CDT_SpecimenSource = LABAST3_R13_CDT+delimiter1+"SpecimenSource_LABAST3_R13_CDT";
	private static String LABAST8_R13_CD = "LABAST8_R13_CD";
	protected static String LABAST8_R13_CD_Result = LABAST8_R13_CD+delimiter1+"Result_LABAST8_R13_CD";
	private static String LABAST7_R13_CDT = "LABAST7_R13_CDT";
	protected static String LABAST7_R13_CDT_TestMethodOptional = LABAST7_R13_CDT+delimiter1+"TestMethodOptional_LABAST7_R13_CDT";

	private static String LABAST6_R14_CDT = "LABAST6_R14_CDT";
	protected static String LABAST6_R14_CDT_DrugName = LABAST6_R14_CDT+delimiter1+"DrugName_LABAST6_R14_CDT";
	private static String LABAST5_R14 = "LABAST5_R14";
	protected static String LABAST5_R14_DateCollected = LABAST5_R14+delimiter1+"DateCollected_LABAST5_R14";
	private static String LABAST14_R14 = "LABAST14_R14";
	protected static String LABAST14_R14_DateReported = LABAST14_R14+delimiter1+"DateReported_LABAST14_R14";
	private static String LABAST3_R14_CDT = "LABAST3_R14_CDT";
	protected static String LABAST3_R14_CDT_SpecimenSource = LABAST3_R14_CDT+delimiter1+"SpecimenSource_LABAST3_R14_CDT";
	private static String LABAST8_R14_CD = "LABAST8_R14_CD";
	protected static String LABAST8_R14_CD_Result = LABAST8_R14_CD+delimiter1+"Result_LABAST8_R14_CD";
	private static String LABAST7_R14_CDT = "LABAST7_R14_CDT";
	protected static String LABAST7_R14_CDT_TestMethodOptional = LABAST7_R14_CDT+delimiter1+"TestMethodOptional_LABAST7_R14_CDT";

	private static String LABAST6_R15_CDT = "LABAST6_R15_CDT";
	protected static String LABAST6_R15_CDT_DrugName = LABAST6_R15_CDT+delimiter1+"DrugName_LABAST6_R15_CDT";
	private static String LABAST5_R15 = "LABAST5_R15";
	protected static String LABAST5_R15_DateCollected = LABAST5_R15+delimiter1+"DateCollected_LABAST5_R15";
	private static String LABAST14_R15 = "LABAST14_R15";
	protected static String LABAST14_R15_DateReported = LABAST14_R15+delimiter1+"DateReported_LABAST14_R15";
	private static String LABAST3_R15_CDT = "LABAST3_R15_CDT";
	protected static String LABAST3_R15_CDT_SpecimenSource = LABAST3_R15_CDT+delimiter1+"SpecimenSource_LABAST3_R15_CDT";
	private static String LABAST8_R15_CD = "LABAST8_R15_CD";
	protected static String LABAST8_R15_CD_Result = LABAST8_R15_CD+delimiter1+"Result_LABAST8_R15_CD";
	private static String LABAST7_R15_CDT = "LABAST7_R15_CDT";
	protected static String LABAST7_R15_CDT_TestMethodOptional = LABAST7_R15_CDT+delimiter1+"TestMethodOptional_LABAST7_R15_CDT";

	private static String LABAST6_R16_CDT = "LABAST6_R16_CDT";
	protected static String LABAST6_R16_CDT_DrugName = LABAST6_R16_CDT+delimiter1+"DrugName_LABAST6_R16_CDT";
	private static String LABAST5_R16 = "LABAST5_R16";
	protected static String LABAST5_R16_DateCollected = LABAST5_R16+delimiter1+"DateCollected_LABAST5_R16";
	private static String LABAST14_R16 = "LABAST14_R16";
	protected static String LABAST14_R16_DateReported = LABAST14_R16+delimiter1+"DateReported_LABAST14_R16";
	private static String LABAST3_R16_CDT = "LABAST3_R16_CDT";
	protected static String LABAST3_R16_CDT_SpecimenSource = LABAST3_R16_CDT+delimiter1+"SpecimenSource_LABAST3_R16_CDT";
	private static String LABAST8_R16_CD = "LABAST8_R16_CD";
	protected static String LABAST8_R16_CD_Result = LABAST8_R16_CD+delimiter1+"Result_LABAST8_R16_CD";
	private static String LABAST7_R16_CDT = "LABAST7_R16_CDT";
	protected static String LABAST7_R16_CDT_TestMethodOptional = LABAST7_R16_CDT+delimiter1+"TestMethodOptional_LABAST7_R16_CDT";

	private static String LABAST6_R17_CDT = "LABAST6_R17_CDT";
	protected static String LABAST6_R17_CDT_DrugName = LABAST6_R17_CDT+delimiter1+"DrugName_LABAST6_R17_CDT";
	private static String LABAST5_R17 = "LABAST5_R17";
	protected static String LABAST5_R17_DateCollected = LABAST5_R17+delimiter1+"DateCollected_LABAST5_R17";
	private static String LABAST14_R17 = "LABAST14_R17";
	protected static String LABAST14_R17_DateReported = LABAST14_R17+delimiter1+"DateReported_LABAST14_R17";
	private static String LABAST3_R17_CDT = "LABAST3_R17_CDT";
	protected static String LABAST3_R17_CDT_SpecimenSource = LABAST3_R17_CDT+delimiter1+"SpecimenSource_LABAST3_R17_CDT";
	private static String LABAST8_R17_CD = "LABAST8_R17_CD";
	protected static String LABAST8_R17_CD_Result = LABAST8_R17_CD+delimiter1+"Result_LABAST8_R17_CD";
	private static String LABAST7_R17_CDT = "LABAST7_R17_CDT";
	protected static String LABAST7_R17_CDT_TestMethodOptional = LABAST7_R17_CDT+delimiter1+"TestMethodOptional_LABAST7_R17_CDT";

	private static String LABAST6_R18_CDT = "LABAST6_R18_CDT";
	protected static String LABAST6_R18_CDT_DrugName = LABAST6_R18_CDT+delimiter1+"DrugName_LABAST6_R18_CDT";
	private static String LABAST5_R18 = "LABAST5_R18";
	protected static String LABAST5_R18_DateCollected = LABAST5_R18+delimiter1+"DateCollected_LABAST5_R18";
	private static String LABAST14_R18 = "LABAST14_R18";
	protected static String LABAST14_R18_DateReported = LABAST14_R18+delimiter1+"DateReported_LABAST14_R18";
	private static String LABAST3_R18_CDT = "LABAST3_R18_CDT";
	protected static String LABAST3_R18_CDT_SpecimenSource = LABAST3_R18_CDT+delimiter1+"SpecimenSource_LABAST3_R18_CDT";
	private static String LABAST8_R18_CD = "LABAST8_R18_CD";
	protected static String LABAST8_R18_CD_Result = LABAST8_R18_CD+delimiter1+"Result_LABAST8_R18_CD";
	private static String LABAST7_R18_CDT = "LABAST7_R18_CDT";
	protected static String LABAST7_R18_CDT_TestMethodOptional = LABAST7_R18_CDT+delimiter1+"TestMethodOptional_LABAST7_R18_CDT";

	private static String LABAST6_R19_CDT = "LABAST6_R19_CDT";
	protected static String LABAST6_R19_CDT_DrugName = LABAST6_R19_CDT+delimiter1+"DrugName_LABAST6_R19_CDT";
	private static String LABAST5_R19 = "LABAST5_R19";
	protected static String LABAST5_R19_DateCollected = LABAST5_R19+delimiter1+"DateCollected_LABAST5_R19";
	private static String LABAST14_R19 = "LABAST14_R19";
	protected static String LABAST14_R19_DateReported = LABAST14_R19+delimiter1+"DateReported_LABAST14_R19";
	private static String LABAST3_R19_CDT = "LABAST3_R19_CDT";
	protected static String LABAST3_R19_CDT_SpecimenSource = LABAST3_R19_CDT+delimiter1+"SpecimenSource_LABAST3_R19_CDT";
	private static String LABAST8_R19_CD = "LABAST8_R19_CD";
	protected static String LABAST8_R19_CD_Result = LABAST8_R19_CD+delimiter1+"Result_LABAST8_R19_CD";
	private static String LABAST7_R19_CDT = "LABAST7_R19_CDT";
	protected static String LABAST7_R19_CDT_TestMethodOptional = LABAST7_R19_CDT+delimiter1+"TestMethodOptional_LABAST7_R19_CDT";

	private static String LABAST6_R20_CDT = "LABAST6_R20_CDT";
	protected static String LABAST6_R20_CDT_DrugName = LABAST6_R20_CDT+delimiter1+"DrugName_LABAST6_R20_CDT";
	private static String LABAST5_R20 = "LABAST5_R20";
	protected static String LABAST5_R20_DateCollected = LABAST5_R20+delimiter1+"DateCollected_LABAST5_R20";
	private static String LABAST14_R20 = "LABAST14_R20";
	protected static String LABAST14_R20_DateReported = LABAST14_R20+delimiter1+"DateReported_LABAST14_R20";
	private static String LABAST3_R20_CDT = "LABAST3_R20_CDT";
	protected static String LABAST3_R20_CDT_SpecimenSource = LABAST3_R20_CDT+delimiter1+"SpecimenSource_LABAST3_R20_CDT";
	private static String LABAST8_R20_CD = "LABAST8_R20_CD";
	protected static String LABAST8_R20_CD_Result = LABAST8_R20_CD+delimiter1+"Result_LABAST8_R20_CD";
	private static String LABAST7_R20_CDT = "LABAST7_R20_CDT";
	protected static String LABAST7_R20_CDT_TestMethodOptional = LABAST7_R20_CDT+delimiter1+"TestMethodOptional_LABAST7_R20_CDT";

	private static String LABAST6_R21_CDT = "LABAST6_R21_CDT";
	protected static String LABAST6_R21_CDT_DrugName = LABAST6_R21_CDT+delimiter1+"DrugName_LABAST6_R21_CDT";
	private static String LABAST5_R21 = "LABAST5_R21";
	protected static String LABAST5_R21_DateCollected = LABAST5_R21+delimiter1+"DateCollected_LABAST5_R21";
	private static String LABAST14_R21 = "LABAST14_R21";
	protected static String LABAST14_R21_DateReported = LABAST14_R21+delimiter1+"DateReported_LABAST14_R21";
	private static String LABAST3_R21_CDT = "LABAST3_R21_CDT";
	protected static String LABAST3_R21_CDT_SpecimenSource = LABAST3_R21_CDT+delimiter1+"SpecimenSource_LABAST3_R21_CDT";
	private static String LABAST8_R21_CD = "LABAST8_R21_CD";
	protected static String LABAST8_R21_CD_Result = LABAST8_R21_CD+delimiter1+"Result_LABAST8_R21_CD";
	private static String LABAST7_R21_CDT = "LABAST7_R21_CDT";
	protected static String LABAST7_R21_CDT_TestMethodOptional = LABAST7_R21_CDT+delimiter1+"TestMethodOptional_LABAST7_R21_CDT";
	
	private static String LABAST6_R22_CDT = "LABAST6_R22_CDT";
	protected static String LABAST6_R22_CDT_DrugName = LABAST6_R22_CDT+delimiter1+"DrugName_LABAST6_R22_CDT";
	private static String LABAST5_R22 = "LABAST5_R22";
	protected static String LABAST5_R22_DateCollected = LABAST5_R22+delimiter1+"DateCollected_LABAST5_R22";
	private static String LABAST14_R22 = "LABAST14_R22";
	protected static String LABAST14_R22_DateReported = LABAST14_R22+delimiter1+"DateReported_LABAST14_R22";
	private static String LABAST3_R22_CDT = "LABAST3_R22_CDT";
	protected static String LABAST3_R22_CDT_SpecimenSource = LABAST3_R22_CDT+delimiter1+"SpecimenSource_LABAST3_R22_CDT";
	private static String LABAST8_R22_CD = "LABAST8_R22_CD";
	protected static String LABAST8_R22_CD_Result = LABAST8_R22_CD+delimiter1+"Result_LABAST8_R22_CD";
	private static String LABAST7_R22_CDT = "LABAST7_R22_CDT";
	protected static String LABAST7_R22_CDT_TestMethodOptional = LABAST7_R22_CDT+delimiter1+"TestMethodOptional_LABAST7_R22_CDT";

	private static String LABAST6_R23_CDT = "LABAST6_R23_CDT";
	protected static String LABAST6_R23_CDT_DrugName = LABAST6_R23_CDT+delimiter1+"DrugName_LABAST6_R23_CDT";
	private static String LABAST5_R23 = "LABAST5_R23";
	protected static String LABAST5_R23_DateCollected = LABAST5_R23+delimiter1+"DateCollected_LABAST5_R23";
	private static String LABAST14_R23 = "LABAST14_R23";
	protected static String LABAST14_R23_DateReported = LABAST14_R23+delimiter1+"DateReported_LABAST14_R23";
	private static String LABAST3_R23_CDT = "LABAST3_R23_CDT";
	protected static String LABAST3_R23_CDT_SpecimenSource = LABAST3_R23_CDT+delimiter1+"SpecimenSource_LABAST3_R23_CDT";
	private static String LABAST8_R23_CD = "LABAST8_R23_CD";
	protected static String LABAST8_R23_CD_Result = LABAST8_R23_CD+delimiter1+"Result_LABAST8_R23_CD";
	private static String LABAST7_R23_CDT = "LABAST7_R23_CDT";
	protected static String LABAST7_R23_CDT_TestMethodOptional = LABAST7_R23_CDT+delimiter1+"TestMethodOptional_LABAST7_R23_CDT";
	
	private static String LABAST6_R24_CDT = "LABAST6_R24_CDT";
	protected static String LABAST6_R24_CDT_DrugName = LABAST6_R24_CDT+delimiter1+"DrugName_LABAST6_R24_CDT";
	private static String LABAST5_R24 = "LABAST5_R24";
	protected static String LABAST5_R24_DateCollected = LABAST5_R24+delimiter1+"DateCollected_LABAST5_R24";
	private static String LABAST14_R24 = "LABAST14_R24";
	protected static String LABAST14_R24_DateReported = LABAST14_R24+delimiter1+"DateReported_LABAST14_R24";
	private static String LABAST3_R24_CDT = "LABAST3_R24_CDT";
	protected static String LABAST3_R24_CDT_SpecimenSource = LABAST3_R24_CDT+delimiter1+"SpecimenSource_LABAST3_R24_CDT";
	private static String LABAST8_R24_CD = "LABAST8_R24_CD";
	protected static String LABAST8_R24_CD_Result = LABAST8_R24_CD+delimiter1+"Result_LABAST8_R24_CD";
	private static String LABAST7_R24_CDT = "LABAST7_R24_CDT";
	protected static String LABAST7_R24_CDT_TestMethodOptional = LABAST7_R24_CDT+delimiter1+"TestMethodOptional_LABAST7_R24_CDT";
	
	private static String LABAST6_R25_CDT = "LABAST6_R25_CDT";
	protected static String LABAST6_R25_CDT_DrugName = LABAST6_R25_CDT+delimiter1+"DrugName_LABAST6_R25_CDT";
	private static String LABAST5_R25 = "LABAST5_R25";
	protected static String LABAST5_R25_DateCollected = LABAST5_R25+delimiter1+"DateCollected_LABAST5_R25";
	private static String LABAST14_R25 = "LABAST14_R25";
	protected static String LABAST14_R25_DateReported = LABAST14_R25+delimiter1+"DateReported_LABAST14_R25";
	private static String LABAST3_R25_CDT = "LABAST3_R25_CDT";
	protected static String LABAST3_R25_CDT_SpecimenSource = LABAST3_R25_CDT+delimiter1+"SpecimenSource_LABAST3_R25_CDT";
	private static String LABAST8_R25_CD = "LABAST8_R25_CD";
	protected static String LABAST8_R25_CD_Result = LABAST8_R25_CD+delimiter1+"Result_LABAST8_R25_CD";
	private static String LABAST7_R25_CDT = "LABAST7_R25_CDT";
	protected static String LABAST7_R25_CDT_TestMethodOptional = LABAST7_R25_CDT+delimiter1+"TestMethodOptional_LABAST7_R25_CDT";
	
	private static String LABAST6_R26_CDT = "LABAST6_R26_CDT";
	protected static String LABAST6_R26_CDT_DrugName = LABAST6_R26_CDT+delimiter1+"DrugName_LABAST6_R26_CDT";
	private static String LABAST5_R26 = "LABAST5_R26";
	protected static String LABAST5_R26_DateCollected = LABAST5_R26+delimiter1+"DateCollected_LABAST5_R26";
	private static String LABAST14_R26 = "LABAST14_R26";
	protected static String LABAST14_R26_DateReported = LABAST14_R26+delimiter1+"DateReported_LABAST14_R26";
	private static String LABAST3_R26_CDT = "LABAST3_R26_CDT";
	protected static String LABAST3_R26_CDT_SpecimenSource = LABAST3_R26_CDT+delimiter1+"SpecimenSource_LABAST3_R26_CDT";
	private static String LABAST8_R26_CD = "LABAST8_R26_CD";
	protected static String LABAST8_R26_CD_Result = LABAST8_R26_CD+delimiter1+"Result_LABAST8_R26_CD";
	private static String LABAST7_R26_CDT = "LABAST7_R26_CDT";
	protected static String LABAST7_R26_CDT_TestMethodOptional = LABAST7_R26_CDT+delimiter1+"TestMethodOptional_LABAST7_R26_CDT";
	
	private static String LABAST6_R27_CDT = "LABAST6_R27_CDT";
	protected static String LABAST6_R27_CDT_DrugName = LABAST6_R27_CDT+delimiter1+"DrugName_LABAST6_R27_CDT";
	private static String LABAST5_R27 = "LABAST5_R27";
	protected static String LABAST5_R27_DateCollected = LABAST5_R27+delimiter1+"DateCollected_LABAST5_R27";
	private static String LABAST14_R27 = "LABAST14_R27";
	protected static String LABAST14_R27_DateReported = LABAST14_R27+delimiter1+"DateReported_LABAST14_R27";
	private static String LABAST3_R27_CDT = "LABAST3_R27_CDT";
	protected static String LABAST3_R27_CDT_SpecimenSource = LABAST3_R27_CDT+delimiter1+"SpecimenSource_LABAST3_R27_CDT";
	private static String LABAST8_R27_CD = "LABAST8_R27_CD";
	protected static String LABAST8_R27_CD_Result = LABAST8_R27_CD+delimiter1+"Result_LABAST8_R27_CD";
	private static String LABAST7_R27_CDT = "LABAST7_R27_CDT";
	protected static String LABAST7_R27_CDT_TestMethodOptional = LABAST7_R27_CDT+delimiter1+"TestMethodOptional_LABAST7_R27_CDT";
	
	private static String LABAST6_R28_CDT = "LABAST6_R28_CDT";
	protected static String LABAST6_R28_CDT_DrugName = LABAST6_R28_CDT+delimiter1+"DrugName_LABAST6_R28_CDT";
	private static String LABAST5_R28 = "LABAST5_R28";
	protected static String LABAST5_R28_DateCollected = LABAST5_R28+delimiter1+"DateCollected_LABAST5_R28";
	private static String LABAST14_R28 = "LABAST14_R28";
	protected static String LABAST14_R28_DateReported = LABAST14_R28+delimiter1+"DateReported_LABAST14_R28";
	private static String LABAST3_R28_CDT = "LABAST3_R28_CDT";
	protected static String LABAST3_R28_CDT_SpecimenSource = LABAST3_R28_CDT+delimiter1+"SpecimenSource_LABAST3_R28_CDT";
	private static String LABAST8_R28_CD = "LABAST8_R28_CD";
	protected static String LABAST8_R28_CD_Result = LABAST8_R28_CD+delimiter1+"Result_LABAST8_R28_CD";
	private static String LABAST7_R28_CDT = "LABAST7_R28_CDT";
	protected static String LABAST7_R28_CDT_TestMethodOptional = LABAST7_R28_CDT+delimiter1+"TestMethodOptional_LABAST7_R28_CDT";
	
	private static String LABAST6_R29_CDT = "LABAST6_R29_CDT";
	protected static String LABAST6_R29_CDT_DrugName = LABAST6_R29_CDT+delimiter1+"DrugName_LABAST6_R29_CDT";
	private static String LABAST5_R29 = "LABAST5_R29";
	protected static String LABAST5_R29_DateCollected = LABAST5_R29+delimiter1+"DateCollected_LABAST5_R29";
	private static String LABAST14_R29 = "LABAST14_R29";
	protected static String LABAST14_R29_DateReported = LABAST14_R29+delimiter1+"DateReported_LABAST14_R29";
	private static String LABAST3_R29_CDT = "LABAST3_R29_CDT";
	protected static String LABAST3_R29_CDT_SpecimenSource = LABAST3_R29_CDT+delimiter1+"SpecimenSource_LABAST3_R29_CDT";
	private static String LABAST8_R29_CD = "LABAST8_R29_CD";
	protected static String LABAST8_R29_CD_Result = LABAST8_R29_CD+delimiter1+"Result_LABAST8_R29_CD";
	private static String LABAST7_R29_CDT = "LABAST7_R29_CDT";
	protected static String LABAST7_R29_CDT_TestMethodOptional = LABAST7_R29_CDT+delimiter1+"TestMethodOptional_LABAST7_R29_CDT";
	
	private static String LABAST6_R30_CDT = "LABAST6_R30_CDT";
	protected static String LABAST6_R30_CDT_DrugName = LABAST6_R30_CDT+delimiter1+"DrugName_LABAST6_R30_CDT";
	private static String LABAST5_R30 = "LABAST5_R30";
	protected static String LABAST5_R30_DateCollected = LABAST5_R30+delimiter1+"DateCollected_LABAST5_R30";
	private static String LABAST14_R30 = "LABAST14_R30";
	protected static String LABAST14_R30_DateReported = LABAST14_R30+delimiter1+"DateReported_LABAST14_R30";
	private static String LABAST3_R30_CDT = "LABAST3_R30_CDT";
	protected static String LABAST3_R30_CDT_SpecimenSource = LABAST3_R30_CDT+delimiter1+"SpecimenSource_LABAST3_R30_CDT";
	private static String LABAST8_R30_CD = "LABAST8_R30_CD";
	protected static String LABAST8_R30_CD_Result = LABAST8_R30_CD+delimiter1+"Result_LABAST8_R30_CD";
	private static String LABAST7_R30_CDT = "LABAST7_R30_CDT";
	protected static String LABAST7_R30_CDT_TestMethodOptional = LABAST7_R30_CDT+delimiter1+"TestMethodOptional_LABAST7_R30_CDT";
	
	private static String LABAST6_R31_CDT = "LABAST6_R31_CDT";
	protected static String LABAST6_R31_CDT_DrugName = LABAST6_R31_CDT+delimiter1+"DrugName_LABAST6_R31_CDT";
	private static String LABAST5_R31 = "LABAST5_R31";
	protected static String LABAST5_R31_DateCollected = LABAST5_R31+delimiter1+"DateCollected_LABAST5_R31";
	private static String LABAST14_R31 = "LABAST14_R31";
	protected static String LABAST14_R31_DateReported = LABAST14_R31+delimiter1+"DateReported_LABAST14_R31";
	private static String LABAST3_R31_CDT = "LABAST3_R31_CDT";
	protected static String LABAST3_R31_CDT_SpecimenSource = LABAST3_R31_CDT+delimiter1+"SpecimenSource_LABAST3_R31_CDT";
	private static String LABAST8_R31_CD = "LABAST8_R31_CD";
	protected static String LABAST8_R31_CD_Result = LABAST8_R31_CD+delimiter1+"Result_LABAST8_R31_CD";
	private static String LABAST7_R31_CDT = "LABAST7_R31_CDT";
	protected static String LABAST7_R31_CDT_TestMethodOptional = LABAST7_R31_CDT+delimiter1+"TestMethodOptional_LABAST7_R31_CDT";
	
	/******************************************************Genotypic/Molecular Drug Susceptibility Testing (Molecular)******************************************************/
	
	private static String INV1148_CD = "INV1148_CD";
	protected static String INV1148_CD_WasGenOrMoleDrugSuscepTest = INV1148_CD+delimiter1+"WasGenOrMoleDrugSuscepTest_INV1148_CD";
	private static String code48018_6_R1_CDT = "48018_6_R1_CDT";
	protected static String code48018_6_R1_CDT_GeneName = code48018_6_R1_CDT+delimiter1+"GeneName_48018_6_R1_CDT";
	private static String LAB682_R1 = "LAB682_R1";
	protected static String LAB682_R1_DateCollected = LAB682_R1+delimiter1+"DateCollected_LAB682_R1";
	private static String LAB683_R1 = "LAB683_R1";
	protected static String LAB683_R1_DateReported = LAB683_R1+delimiter1+"DateReported_LAB683_R1";
	private static String LAB684_R1_CDT = "LAB684_R1_CDT";
	protected static String LAB684_R1_CDT_SpecimenSource = LAB684_R1_CDT+delimiter1+"SpecimenSource_LAB684_R1_CDT";
	private static String LAB685_R1_CD = "LAB685_R1_CD";
	protected static String LAB685_R1_CD_Result = LAB685_R1_CD+delimiter1+"Result_LAB685_R1_CD";
	private static String LAB686_R1 = "LAB686_R1";
	protected static String LAB686_R1_NucleicAcidChange = LAB686_R1+delimiter1+"NucleicAcidChange_LAB686_R1";
	private static String LAB687_R1 = "LAB687_R1";
	protected static String LAB687_R1_AminoAcidChange = LAB687_R1+delimiter1+"AminoAcidChange_LAB687_R1";
	private static String LAB688_R1_CD = "LAB688_R1_CD";
	protected static String LAB688_R1_CD_Indel = LAB688_R1_CD+delimiter1+"Indel_LAB688_R1_CD";
	private static String LAB689_R1_CD = "LAB689_R1_CD";
	protected static String LAB689_R1_CD_TestType = LAB689_R1_CD+delimiter1+"TestType_LAB689_R1_CD";
	private static String LAB689_R1_OTH = "LAB689_R1_OTH";
	protected static String LAB689_R1_OTH_TestType = LAB689_R1_OTH+delimiter1+"TestType_LAB689_R1_OTH";

	
	private static String code48018_6_R2_CDT = "48018_6_R2_CDT";
	protected static String code48018_6_R2_CDT_GeneName = code48018_6_R2_CDT+delimiter1+"GeneName_48018_6_R2_CDT";
	private static String LAB682_R2 = "LAB682_R2";
	protected static String LAB682_R2_DateCollected = LAB682_R2+delimiter1+"DateCollected_LAB682_R2";
	private static String LAB683_R2 = "LAB683_R2";
	protected static String LAB683_R2_DateReported = LAB683_R2+delimiter1+"DateReported_LAB683_R2";
	private static String LAB684_R2_CDT = "LAB684_R2_CDT";
	protected static String LAB684_R2_CDT_SpecimenSource = LAB684_R2_CDT+delimiter1+"SpecimenSource_LAB684_R2_CDT";
	private static String LAB685_R2_CD = "LAB685_R2_CD";
	protected static String LAB685_R2_CD_Result = LAB685_R2_CD+delimiter1+"Result_LAB685_R2_CD";
	private static String LAB686_R2 = "LAB686_R2";
	protected static String LAB686_R2_NucleicAcidChange = LAB686_R2+delimiter1+"NucleicAcidChange_LAB686_R2";
	private static String LAB687_R2 = "LAB687_R2";
	protected static String LAB687_R2_AminoAcidChange = LAB687_R2+delimiter1+"AminoAcidChange_LAB687_R2";
	private static String LAB688_R2_CD = "LAB688_R2_CD";
	protected static String LAB688_R2_CD_Indel = LAB688_R2_CD+delimiter1+"Indel_LAB688_R2_CD";
	private static String LAB689_R2_CD = "LAB689_R2_CD";
	protected static String LAB689_R2_CD_TestType = LAB689_R2_CD+delimiter1+"TestType_LAB689_R2_CD";
	private static String LAB689_R2_OTH = "LAB689_R2_OTH";
	protected static String LAB689_R2_OTH_TestType = LAB689_R2_OTH+delimiter1+"TestType_LAB689_R2_OTH";

	private static String code48018_6_R3_CDT = "48018_6_R3_CDT";
	protected static String code48018_6_R3_CDT_GeneName = code48018_6_R3_CDT+delimiter1+"GeneName_48018_6_R3_CDT";
	private static String LAB682_R3 = "LAB682_R3";
	protected static String LAB682_R3_DateCollected = LAB682_R3+delimiter1+"DateCollected_LAB682_R3";
	private static String LAB683_R3 = "LAB683_R3";
	protected static String LAB683_R3_DateReported = LAB683_R3+delimiter1+"DateReported_LAB683_R3";
	private static String LAB684_R3_CDT = "LAB684_R3_CDT";
	protected static String LAB684_R3_CDT_SpecimenSource = LAB684_R3_CDT+delimiter1+"SpecimenSource_LAB684_R3_CDT";
	private static String LAB685_R3_CD = "LAB685_R3_CD";
	protected static String LAB685_R3_CD_Result = LAB685_R3_CD+delimiter1+"Result_LAB685_R3_CD";
	private static String LAB686_R3 = "LAB686_R3";
	protected static String LAB686_R3_NucleicAcidChange = LAB686_R3+delimiter1+"NucleicAcidChange_LAB686_R3";
	private static String LAB687_R3 = "LAB687_R3";
	protected static String LAB687_R3_AminoAcidChange = LAB687_R3+delimiter1+"AminoAcidChange_LAB687_R3";
	private static String LAB688_R3_CD = "LAB688_R3_CD";
	protected static String LAB688_R3_CD_Indel = LAB688_R3_CD+delimiter1+"Indel_LAB688_R3_CD";
	private static String LAB689_R3_CD = "LAB689_R3_CD";
	protected static String LAB689_R3_CD_TestType = LAB689_R3_CD+delimiter1+"TestType_LAB689_R3_CD";
	private static String LAB689_R3_OTH = "LAB689_R3_OTH";
	protected static String LAB689_R3_OTH_TestType = LAB689_R3_OTH+delimiter1+"TestType_LAB689_R3_OTH";

	private static String code48018_6_R4_CDT = "48018_6_R4_CDT";
	protected static String code48018_6_R4_CDT_GeneName = code48018_6_R4_CDT+delimiter1+"GeneName_48018_6_R4_CDT";
	private static String LAB682_R4 = "LAB682_R4";
	protected static String LAB682_R4_DateCollected = LAB682_R4+delimiter1+"DateCollected_LAB682_R4";
	private static String LAB683_R4 = "LAB683_R4";
	protected static String LAB683_R4_DateReported = LAB683_R4+delimiter1+"DateReported_LAB683_R4";
	private static String LAB684_R4_CDT = "LAB684_R4_CDT";
	protected static String LAB684_R4_CDT_SpecimenSource = LAB684_R4_CDT+delimiter1+"SpecimenSource_LAB684_R4_CDT";
	private static String LAB685_R4_CD = "LAB685_R4_CD";
	protected static String LAB685_R4_CD_Result = LAB685_R4_CD+delimiter1+"Result_LAB685_R4_CD";
	private static String LAB686_R4 = "LAB686_R4";
	protected static String LAB686_R4_NucleicAcidChange = LAB686_R4+delimiter1+"NucleicAcidChange_LAB686_R4";
	private static String LAB687_R4 = "LAB687_R4";
	protected static String LAB687_R4_AminoAcidChange = LAB687_R4+delimiter1+"AminoAcidChange_LAB687_R4";
	private static String LAB688_R4_CD = "LAB688_R4_CD";
	protected static String LAB688_R4_CD_Indel = LAB688_R4_CD+delimiter1+"Indel_LAB688_R4_CD";
	private static String LAB689_R4_CD = "LAB689_R4_CD";
	protected static String LAB689_R4_CD_TestType = LAB689_R4_CD+delimiter1+"TestType_LAB689_R4_CD";
	private static String LAB689_R4_OTH = "LAB689_R4_OTH";
	protected static String LAB689_R4_OTH_TestType = LAB689_R4_OTH+delimiter1+"TestType_LAB689_R4_OTH";

	
	
	private static String code48018_6_R5_CDT = "48018_6_R5_CDT";
	protected static String code48018_6_R5_CDT_GeneName = code48018_6_R5_CDT+delimiter1+"GeneName_48018_6_R5_CDT";
	private static String LAB682_R5 = "LAB682_R5";
	protected static String LAB682_R5_DateCollected = LAB682_R5+delimiter1+"DateCollected_LAB682_R5";
	private static String LAB683_R5 = "LAB683_R5";
	protected static String LAB683_R5_DateReported = LAB683_R5+delimiter1+"DateReported_LAB683_R5";
	private static String LAB684_R5_CDT = "LAB684_R5_CDT";
	protected static String LAB684_R5_CDT_SpecimenSource = LAB684_R5_CDT+delimiter1+"SpecimenSource_LAB684_R5_CDT";
	private static String LAB685_R5_CD = "LAB685_R5_CD";
	protected static String LAB685_R5_CD_Result = LAB685_R5_CD+delimiter1+"Result_LAB685_R5_CD";
	private static String LAB686_R5 = "LAB686_R5";
	protected static String LAB686_R5_NucleicAcidChange = LAB686_R5+delimiter1+"NucleicAcidChange_LAB686_R5";
	private static String LAB687_R5 = "LAB687_R5";
	protected static String LAB687_R5_AminoAcidChange = LAB687_R5+delimiter1+"AminoAcidChange_LAB687_R5";
	private static String LAB688_R5_CD = "LAB688_R5_CD";
	protected static String LAB688_R5_CD_Indel = LAB688_R5_CD+delimiter1+"Indel_LAB688_R5_CD";
	private static String LAB689_R5_CD = "LAB689_R5_CD";
	protected static String LAB689_R5_CD_TestType = LAB689_R5_CD+delimiter1+"TestType_LAB689_R5_CD";
	private static String LAB689_R5_OTH = "LAB689_R5_OTH";
	protected static String LAB689_R5_OTH_TestType = LAB689_R5_OTH+delimiter1+"TestType_LAB689_R5_OTH";

	
	
	private static String code48018_6_R6_CDT = "48018_6_R6_CDT";
	protected static String code48018_6_R6_CDT_GeneName = code48018_6_R6_CDT+delimiter1+"GeneName_48018_6_R6_CDT";
	private static String LAB682_R6 = "LAB682_R6";
	protected static String LAB682_R6_DateCollected = LAB682_R6+delimiter1+"DateCollected_LAB682_R6";
	private static String LAB683_R6 = "LAB683_R6";
	protected static String LAB683_R6_DateReported = LAB683_R6+delimiter1+"DateReported_LAB683_R6";
	private static String LAB684_R6_CDT = "LAB684_R6_CDT";
	protected static String LAB684_R6_CDT_SpecimenSource = LAB684_R6_CDT+delimiter1+"SpecimenSource_LAB684_R6_CDT";
	private static String LAB685_R6_CD = "LAB685_R6_CD";
	protected static String LAB685_R6_CD_Result = LAB685_R6_CD+delimiter1+"Result_LAB685_R6_CD";
	private static String LAB686_R6 = "LAB686_R6";
	protected static String LAB686_R6_NucleicAcidChange = LAB686_R6+delimiter1+"NucleicAcidChange_LAB686_R6";
	private static String LAB687_R6 = "LAB687_R6";
	protected static String LAB687_R6_AminoAcidChange = LAB687_R6+delimiter1+"AminoAcidChange_LAB687_R6";
	private static String LAB688_R6_CD = "LAB688_R6_CD";
	protected static String LAB688_R6_CD_Indel = LAB688_R6_CD+delimiter1+"Indel_LAB688_R6_CD";
	private static String LAB689_R6_CD = "LAB689_R6_CD";
	protected static String LAB689_R6_CD_TestType = LAB689_R6_CD+delimiter1+"TestType_LAB689_R6_CD";
	private static String LAB689_R6_OTH = "LAB689_R6_OTH";
	protected static String LAB689_R6_OTH_TestType = LAB689_R6_OTH+delimiter1+"TestType_LAB689_R6_OTH";

	
	
	private static String code48018_6_R7_CDT = "48018_6_R7_CDT";
	protected static String code48018_6_R7_CDT_GeneName = code48018_6_R7_CDT+delimiter1+"GeneName_48018_6_R7_CDT";
	private static String LAB682_R7 = "LAB682_R7";
	protected static String LAB682_R7_DateCollected = LAB682_R7+delimiter1+"DateCollected_LAB682_R7";
	private static String LAB683_R7 = "LAB683_R7";
	protected static String LAB683_R7_DateReported = LAB683_R7+delimiter1+"DateReported_LAB683_R7";
	private static String LAB684_R7_CDT = "LAB684_R7_CDT";
	protected static String LAB684_R7_CDT_SpecimenSource = LAB684_R7_CDT+delimiter1+"SpecimenSource_LAB684_R7_CDT";
	private static String LAB685_R7_CD = "LAB685_R7_CD";
	protected static String LAB685_R7_CD_Result = LAB685_R7_CD+delimiter1+"Result_LAB685_R7_CD";
	private static String LAB686_R7 = "LAB686_R7";
	protected static String LAB686_R7_NucleicAcidChange = LAB686_R7+delimiter1+"NucleicAcidChange_LAB686_R7";
	private static String LAB687_R7 = "LAB687_R7";
	protected static String LAB687_R7_AminoAcidChange = LAB687_R7+delimiter1+"AminoAcidChange_LAB687_R7";
	private static String LAB688_R7_CD = "LAB688_R7_CD";
	protected static String LAB688_R7_CD_Indel = LAB688_R7_CD+delimiter1+"Indel_LAB688_R7_CD";
	private static String LAB689_R7_CD = "LAB689_R7_CD";
	protected static String LAB689_R7_CD_TestType = LAB689_R7_CD+delimiter1+"TestType_LAB689_R7_CD";
	private static String LAB689_R7_OTH = "LAB689_R7_OTH";
	protected static String LAB689_R7_OTH_TestType = LAB689_R7_OTH+delimiter1+"TestType_LAB689_R7_OTH";

	
	
	private static String code48018_6_R8_CDT = "48018_6_R8_CDT";
	protected static String code48018_6_R8_CDT_GeneName = code48018_6_R8_CDT+delimiter1+"GeneName_48018_6_R8_CDT";
	private static String LAB682_R8 = "LAB682_R8";
	protected static String LAB682_R8_DateCollected = LAB682_R8+delimiter1+"DateCollected_LAB682_R8";
	private static String LAB683_R8 = "LAB683_R8";
	protected static String LAB683_R8_DateReported = LAB683_R8+delimiter1+"DateReported_LAB683_R8";
	private static String LAB684_R8_CDT = "LAB684_R8_CDT";
	protected static String LAB684_R8_CDT_SpecimenSource = LAB684_R8_CDT+delimiter1+"SpecimenSource_LAB684_R8_CDT";
	private static String LAB685_R8_CD = "LAB685_R8_CD";
	protected static String LAB685_R8_CD_Result = LAB685_R8_CD+delimiter1+"Result_LAB685_R8_CD";
	private static String LAB686_R8 = "LAB686_R8";
	protected static String LAB686_R8_NucleicAcidChange = LAB686_R8+delimiter1+"NucleicAcidChange_LAB686_R8";
	private static String LAB687_R8 = "LAB687_R8";
	protected static String LAB687_R8_AminoAcidChange = LAB687_R8+delimiter1+"AminoAcidChange_LAB687_R8";
	private static String LAB688_R8_CD = "LAB688_R8_CD";
	protected static String LAB688_R8_CD_Indel = LAB688_R8_CD+delimiter1+"Indel_LAB688_R8_CD";
	private static String LAB689_R8_CD = "LAB689_R8_CD";
	protected static String LAB689_R8_CD_TestType = LAB689_R8_CD+delimiter1+"TestType_LAB689_R8_CD";
	private static String LAB689_R8_OTH = "LAB689_R8_OTH";
	protected static String LAB689_R8_OTH_TestType = LAB689_R8_OTH+delimiter1+"TestType_LAB689_R8_OTH";

	//MDR TB Case
	private static String INV1275_CD = "INV1275_CD";
	protected static String INV1275_CD_TreatedAsMDR = INV1275_CD+delimiter1+"TreatedAsMDR_INV1275_CD";

	/******************************************************Case Outcome******************************************************/
	
	private static String INV1149_CD = "INV1149_CD";
	protected static String INV1149_CD_SputumCultureConversionDocumented = INV1149_CD+delimiter1+"SputumCultureConversionDocumented_INV1149_CD";
	private static String INV1150 = "INV1150";
	protected static String INV1150_dateSpecimenCollected = INV1150+delimiter1+" dateSpecimenCollected_INV1150";
	private static String INV1151_CD = "INV1151_CD";
	protected static String INV1151_CD_reasonForNotDocumenting = INV1151_CD+delimiter1+"reasonForNotDocumenting_INV1151_CD";
	private static String INV1151Oth = "INV1151_OTH";
	protected static String INV1151Oth_OtherSpecify = INV1151Oth+delimiter1+"OtherSpecify_INV1151_OTH";
	private static String TB279_CD = "TB279_CD";
	protected static String TB279_CD_MovedDuringTherapy = TB279_CD+delimiter1+"MovedDuringTherapy_TB279_CD";
	private static String INV1152_CD = "INV1152_CD";
	protected static String INV1152_CD_IfYesMovedToWhere = INV1152_CD+delimiter1+"IfYesMovedToWhere_INV1152_CD";
	private static String INV1152_OS_CD = "INV1152_OS_CD";
	protected static String INV1152_OS_CD_IfYesMovedToWhere = INV1152_OS_CD+delimiter1+"IfYesMovedToWhere_INV1152_OS_CD";
	private static String INV1152_OUS_CD = "INV1152_OUS_CD";
	protected static String INV1152_OUS_CD_IfYesMovedToWhere = INV1152_OUS_CD+delimiter1+"IfYesMovedToWhere_INV1152_OUS_CD";
	
	private static String INV1153_CDT = "INV1153_CDT";
	protected static String INV1153_CDT_IfOutOfStateSpecifyDestination = INV1153_CDT+delimiter1+"IfOutOfStateSpecifyDestination_INV1153_CDT";
	private static String INV1155_CD = "INV1155_CD";
	protected static String INV1155_CD_TransnationalReferralMade = INV1155_CD+delimiter1+"TransnationalReferralMade_INV1155_CD";
	private static String INV1154_CDT = "INV1154_CDT";
	protected static String INV1154_CDT_IfOutOfCountrySpecifyDestination = INV1154_CDT+delimiter1+"IfOutOfCountrySpecifyDestination_INV1154_CDT";
	private static String code413947000 = "413947000";
	protected static String code413947000_DateTherapyStopped = 413947000+delimiter1+"DateTherapyStopped_413947000";
	private static String INV1140_CD = "INV1140_CD";
	protected static String INV1140_CD_ReasonTherapyStoppedOrNeverStarted = INV1140_CD+delimiter1+"ReasonTherapyStoppedOrNeverStarted_INV1140_CD";
	private static String INV1140_OTH = "INV1140_OTH";
	protected static String INV1140_OTH_ReasonTherapyStoppedOrNeverStarted = INV1140_OTH+delimiter1+"ReasonTherapyStoppedOrNeverStartedOtherSpecify_INV1140_OTH";
	
	private static String INV1141_CD = "INV1141_CD";
	protected static String INV1141_CD_ReasonTbTherapyExtended = INV1141_CD+delimiter1+"ReasonTbTherapyExtended_INV1141_CD";
	
	private static String INV1141_I_CD = "INV1141_I_CD";
	protected static String INV1141_I_CD_ReasonTbTherapyExtended = INV1141_I_CD+delimiter1+"ReasonTbTherapyExtended_INV1141_I_CD";
	
	private static String INV1141_A_CD = "INV1141_A_CD";
	protected static String INV1141_A_CD_ReasonTbTherapyExtended = INV1141_A_CD+delimiter1+"ReasonTbTherapyExtended_INV1141_A_CD";
	
	private static String INV1141_C_CD = "INV1141_C_CD";
	protected static String INV1141_C_CD_ReasonTbTherapyExtended = INV1141_C_CD+delimiter1+"ReasonTbTherapyExtended_INV1141_C_CD";
	
	private static String INV1141_F_CD = "INV1141_F_CD";
	protected static String INV1141_F_CD_ReasonTbTherapyExtended = INV1141_F_CD+delimiter1+"ReasonTbTherapyExtended_INV1141_F_CD";
	
	private static String INV1141_N_CD = "INV1141_N_CD";
	protected static String INV1141_N_CD_ReasonTbTherapyExtended = INV1141_N_CD+delimiter1+"ReasonTbTherapyExtended_INV1141_N_CD";
	
	private static String INV1141_U_CD = "INV1141_U_CD";
	protected static String INV1141_U_CD_ReasonTbTherapyExtended = INV1141_U_CD+delimiter1+"ReasonTbTherapyExtended_INV1141_U_CD";
	
	private static String INV1141_O_CD = "INV1141_O_CD";
	protected static String INV1141_O_CD_ReasonTbTherapyExtended = INV1141_O_CD+delimiter1+"ReasonTbTherapyExtended_INV1141_O_CD";
	
	private static String INV1141_OTH = "INV1141_OTH";
	protected static String INV1141_OTH_ReasonTbTherapyExtended = INV1141_OTH+delimiter1+"ReasonTbTherapyExtended_INV1141_OTH";
	
	private static String code55753_8_CD = "55753_8_CD";
	protected static String code55753_8_CD_TreatmentAdministration = code55753_8_CD+delimiter1+"TreatmentAdministration_55753_8_CD";
	private static String code55753_8_D_CD = "55753_8_D_CD";
	protected static String code55753_8_D_CD_TreatmentAdministration = code55753_8_D_CD+delimiter1+"TreatmentAdministration_55753_8_D_CD";
	private static String code55753_8_E_CD = "55753_8_E_CD";
	protected static String code55753_8_E_CD_TreatmentAdministration = code55753_8_E_CD+delimiter1+"TreatmentAdministration_55753_8_E_CD";
	private static String code55753_8_S_CD = "55753_8_S_CD";
	protected static String code55753_8_S_CD_TreatmentAdministration = code55753_8_S_CD+delimiter1+"TreatmentAdministration_55753_8_S_CD";

	private static String DEM127_CD = "DEM127_CD";
	protected static String DEM127_CD_DidThePatientDied = DEM127_CD+delimiter1+"DidThePatientDied_DEM127_CD";
	private static String DEM128 = "DEM128";
	protected static String DEM128_DateOfDeath = DEM128+delimiter1+"DateOfDeath_DEM128";
	private static String INV145_CD = "INV145_CD";
	protected static String INV145_CD_DidTBOrComplicationsOfTBTreatmentContributeToDeath = INV145_CD+delimiter1+"DidTBOrComplicationsOfTBTreatmentContributeToDeath_INV145_CD";
	private static String INV167 = "INV167";
	protected static String INV167_InvestigationComments = INV167+delimiter1+"InvestigationComments_INV167";
	
	/******************************************************MDR TB Supplemental Surveillance Form******************************************************/

	private static String INV1156_CD = "INV1156_CD";
	protected static String INV1156_CD_HistoryTreatmentBeforeCurrentEpisode = INV1156_CD+delimiter1+"HistoryTreatmentBeforeCurrentEpisode_INV1156_CD";
	private static String INV1157 = "INV1157";
	protected static String INV1157_DateMDRTBTherapyStartedForCurrentEpisode = INV1157+delimiter1+"DateMDRTBTherapyStartedForCurrentEpisode_INV1157";
	
	
	private static String INV1158_R1_CDT = "INV1158_R1_CDT";
	protected static String INV1158_R1_CDT_DrugName = INV1158_R1_CDT+delimiter1+"DrugName_INV1158_R1_CDT";
	private static String INV1159_R1_CD = "INV1159_R1_CD";
	protected static String INV1159_R1_CD_LengthTimAdministered = INV1159_R1_CD+delimiter1+"LengthTimAdministered_INV1159_R1_CD";
	
	private static String INV1158_R2_CDT = "INV1158_R2_CDT";
	protected static String INV1158_R2_CDT_DrugName = INV1158_R2_CDT+delimiter1+"DrugName_INV1158_R2_CDT";
	private static String INV1159_R2_CD = "INV1159_R2_CD";
	protected static String INV1159_R2_CD_LengthTimAdministered = INV1159_R2_CD+delimiter1+"LengthTimAdministered_INV1159_R2_CD";
	private static String INV1158_R3_CDT = "INV1158_R3_CDT";
	protected static String INV1158_R3_CDT_DrugName = INV1158_R3_CDT+delimiter1+"DrugName_INV1158_R3_CDT";
	private static String INV1159_R3_CD = "INV1159_R3_CD";
	protected static String INV1159_R3_CD_LengthTimAdministered = INV1159_R3_CD+delimiter1+"LengthTimAdministered_INV1159_R3_CD";
	private static String INV1158_R4_CDT = "INV1158_R4_CDT";
	protected static String INV1158_R4_CDT_DrugName = INV1158_R4_CDT+delimiter1+"DrugName_INV1158_R4_CDT";
	private static String INV1159_R4_CD = "INV1159_R4_CD";
	protected static String INV1159_R4_CD_LengthTimAdministered = INV1159_R4_CD+delimiter1+"LengthTimAdministered_INV1159_R4_CD";
	private static String INV1158_R5_CDT = "INV1158_R5_CDT";
	protected static String INV1158_R5_CDT_DrugName = INV1158_R5_CDT+delimiter1+"DrugName_INV1158_R5_CDT";
	private static String INV1159_R5_CD = "INV1159_R5_CD";
	protected static String INV1159_R5_CD_LengthTimAdministered = INV1159_R5_CD+delimiter1+"LengthTimAdministered_INV1159_R5_CD";
	private static String INV1158_R6_CDT = "INV1158_R6_CDT";
	protected static String INV1158_R6_CDT_DrugName = INV1158_R6_CDT+delimiter1+"DrugName_INV1158_R6_CDT";
	private static String INV1159_R6_CD = "INV1159_R6_CD";
	protected static String INV1159_R6_CD_LengthTimAdministered = INV1159_R6_CD+delimiter1+"LengthTimAdministered_INV1159_R6_CD";
	private static String INV1158_R7_CDT = "INV1158_R7_CDT";
	protected static String INV1158_R7_CDT_DrugName = INV1158_R7_CDT+delimiter1+"DrugName_INV1158_R7_CDT";
	private static String INV1159_R7_CD = "INV1159_R7_CD";
	protected static String INV1159_R7_CD_LengthTimAdministered = INV1159_R7_CD+delimiter1+"LengthTimAdministered_INV1159_R7_CD";
	private static String INV1158_R8_CDT = "INV1158_R8_CDT";
	protected static String INV1158_R8_CDT_DrugName = INV1158_R8_CDT+delimiter1+"DrugName_INV1158_R8_CDT";
	private static String INV1159_R8_CD = "INV1159_R8_CD";
	protected static String INV1159_R8_CD_LengthTimAdministered = INV1159_R8_CD+delimiter1+"LengthTimAdministered_INV1159_R8_CD";
	private static String INV1158_R9_CDT = "INV1158_R9_CDT";
	protected static String INV1158_R9_CDT_DrugName = INV1158_R9_CDT+delimiter1+"DrugName_INV1158_R9_CDT";
	private static String INV1159_R9_CD = "INV1159_R9_CD";
	protected static String INV1159_R9_CD_LengthTimAdministered = INV1159_R9_CD+delimiter1+"LengthTimAdministered_INV1159_R9_CD";
	private static String INV1158_R10_CDT = "INV1158_R10_CDT";
	protected static String INV1158_R10_CDT_DrugName = INV1158_R10_CDT+delimiter1+"DrugName_INV1158_R10_CDT";
	private static String INV1159_R10_CD = "INV1159_R10_CD";
	protected static String INV1159_R10_CD_LengthTimAdministered = INV1159_R10_CD+delimiter1+"LengthTimAdministered_INV1159_R10_CD";

	private static String INV1158_R11_CDT = "INV1158_R11_CDT";
	protected static String INV1158_R11_CDT_DrugName = INV1158_R11_CDT+delimiter1+"DrugName_INV1158_R11_CDT";
	private static String INV1159_R11_CD = "INV1159_R11_CD";
	protected static String INV1159_R11_CD_LengthTimAdministered = INV1159_R11_CD+delimiter1+"LengthTimAdministered_INV1159_R11_CD";

	private static String INV1158_R12_CDT = "INV1158_R12_CDT";
	protected static String INV1158_R12_CDT_DrugName = INV1158_R12_CDT+delimiter1+"DrugName_INV1158_R12_CDT";
	private static String INV1159_R12_CD = "INV1159_R12_CD";
	protected static String INV1159_R12_CD_LengthTimAdministered = INV1159_R12_CD+delimiter1+"LengthTimAdministered_INV1159_R12_CD";

	private static String INV1158_R13_CDT = "INV1158_R13_CDT";
	protected static String INV1158_R13_CDT_DrugName = INV1158_R13_CDT+delimiter1+"DrugName_INV1158_R13_CDT";
	private static String INV1159_R13_CD = "INV1159_R13_CD";
	protected static String INV1159_R13_CD_LengthTimAdministered = INV1159_R13_CD+delimiter1+"LengthTimAdministered_INV1159_R13_CD";

	private static String INV1158_R14_CDT = "INV1158_R14_CDT";
	protected static String INV1158_R14_CDT_DrugName = INV1158_R14_CDT+delimiter1+"DrugName_INV1158_R14_CDT";
	private static String INV1159_R14_CD = "INV1159_R14_CD";
	protected static String INV1159_R14_CD_LengthTimAdministered = INV1159_R14_CD+delimiter1+"LengthTimAdministered_INV1159_R14_CD";

	private static String INV1158_R15_CDT = "INV1158_R15_CDT";
	protected static String INV1158_R15_CDT_DrugName = INV1158_R15_CDT+delimiter1+"DrugName_INV1158_R15_CDT";
	private static String INV1159_R15_CD = "INV1159_R15_CD";
	protected static String INV1159_R15_CD_LengthTimAdministered = INV1159_R15_CD+delimiter1+"LengthTimAdministered_INV1159_R15_CD";

	private static String INV1158_R16_CDT = "INV1158_R16_CDT";
	protected static String INV1158_R16_CDT_DrugName = INV1158_R16_CDT+delimiter1+"DrugName_INV1158_R16_CDT";
	private static String INV1159_R16_CD = "INV1159_R16_CD";
	protected static String INV1159_R16_CD_LengthTimAdministered = INV1159_R16_CD+delimiter1+"LengthTimAdministered_INV1159_R16_CD";

	private static String INV1158_R17_CDT = "INV1158_R17_CDT";
	protected static String INV1158_R17_CDT_DrugName = INV1158_R17_CDT+delimiter1+"DrugName_INV1158_R17_CDT";
	private static String INV1159_R17_CD = "INV1159_R17_CD";
	protected static String INV1159_R17_CD_LengthTimAdministered = INV1159_R17_CD+delimiter1+"LengthTimAdministered_INV1159_R17_CD";

	private static String INV1158_R18_CDT = "INV1158_R18_CDT";
	protected static String INV1158_R18_CDT_DrugName = INV1158_R18_CDT+delimiter1+"DrugName_INV1158_R18_CDT";
	private static String INV1159_R18_CD = "INV1159_R18_CD";
	protected static String INV1159_R18_CD_LengthTimAdministered = INV1159_R18_CD+delimiter1+"LengthTimAdministered_INV1159_R18_CD";

	private static String INV1158_R19_CDT = "INV1158_R19_CDT";
	protected static String INV1158_R19_CDT_DrugName = INV1158_R19_CDT+delimiter1+"DrugName_INV1158_R19_CDT";
	private static String INV1159_R19_CD = "INV1159_R19_CD";
	protected static String INV1159_R19_CD_LengthTimAdministered = INV1159_R19_CD+delimiter1+"LengthTimAdministered_INV1159_R19_CD";

	private static String INV1158_R20_CDT = "INV1158_R20_CDT";
	protected static String INV1158_R20_CDT_DrugName = INV1158_R20_CDT+delimiter1+"DrugName_INV1158_R20_CDT";
	private static String INV1159_R20_CD = "INV1159_R20_CD";
	protected static String INV1159_R20_CD_LengthTimAdministered = INV1159_R20_CD+delimiter1+"LengthTimAdministered_INV1159_R20_CD";

	private static String INV1158_R21_CDT = "INV1158_R21_CDT";
	protected static String INV1158_R21_CDT_DrugName = INV1158_R21_CDT+delimiter1+"DrugName_INV1158_R21_CDT";
	private static String INV1159_R21_CD = "INV1159_R21_CD";
	protected static String INV1159_R21_CD_LengthTimAdministered = INV1159_R21_CD+delimiter1+"LengthTimAdministered_INV1159_R21_CD";

	private static String INV1158_R22_CDT = "INV1158_R22_CDT";
	protected static String INV1158_R22_CDT_DrugName = INV1158_R22_CDT+delimiter1+"DrugName_INV1158_R22_CDT";
	private static String INV1159_R22_CD = "INV1159_R22_CD";
	protected static String INV1159_R22_CD_LengthTimAdministered = INV1159_R22_CD+delimiter1+"LengthTimAdministered_INV1159_R22_CD";

	private static String INV1158_R23_CDT = "INV1158_R23_CDT";
	protected static String INV1158_R23_CDT_DrugName = INV1158_R23_CDT+delimiter1+"DrugName_INV1158_R23_CDT";
	private static String INV1159_R23_CD = "INV1159_R23_CD";
	protected static String INV1159_R23_CD_LengthTimAdministered = INV1159_R23_CD+delimiter1+"LengthTimAdministered_INV1159_R23_CD";

	private static String INV1158_R24_CDT = "INV1158_R24_CDT";
	protected static String INV1158_R24_CDT_DrugName = INV1158_R24_CDT+delimiter1+"DrugName_INV1158_R24_CDT";
	private static String INV1159_R24_CD = "INV1159_R24_CD";
	protected static String INV1159_R24_CD_LengthTimAdministered = INV1159_R24_CD+delimiter1+"LengthTimAdministered_INV1159_R24_CD";	
	
	private static String INV1158_R25_CDT = "INV1158_R25_CDT";
	protected static String INV1158_R25_CDT_DrugName = INV1158_R25_CDT+delimiter1+"DrugName_INV1158_R25_CDT";
	private static String INV1159_R25_CD = "INV1159_R25_CD";
	protected static String INV1159_R25_CD_LengthTimAdministered = INV1159_R25_CD+delimiter1+"LengthTimAdministered_INV1159_R25_CD";	
	
	private static String INV1158_R26_CDT = "INV1158_R26_CDT";
	protected static String INV1158_R26_CDT_DrugName = INV1158_R26_CDT+delimiter1+"DrugName_INV1158_R26_CDT";
	private static String INV1159_R26_CD = "INV1159_R26_CD";
	protected static String INV1159_R26_CD_LengthTimAdministered = INV1159_R26_CD+delimiter1+"LengthTimAdministered_INV1159_R26_CD";	
	
	private static String INV1158_R27_CDT = "INV1158_R27_CDT";
	protected static String INV1158_R27_CDT_DrugName = INV1158_R27_CDT+delimiter1+"DrugName_INV1158_R27_CDT";
	private static String INV1159_R27_CD = "INV1159_R27_CD";
	protected static String INV1159_R27_CD_LengthTimAdministered = INV1159_R27_CD+delimiter1+"LengthTimAdministered_INV1159_R27_CD";	
	
	private static String INV1158_R28_CDT = "INV1158_R28_CDT";
	protected static String INV1158_R28_CDT_DrugName = INV1158_R28_CDT+delimiter1+"DrugName_INV1158_R28_CDT";
	private static String INV1159_R28_CD = "INV1159_R28_CD";
	protected static String INV1159_R28_CD_LengthTimAdministered = INV1159_R28_CD+delimiter1+"LengthTimAdministered_INV1159_R28_CD";	
	
	private static String INV1158_R29_CDT = "INV1158_R29_CDT";
	protected static String INV1158_R29_CDT_DrugName = INV1158_R29_CDT+delimiter1+"DrugName_INV1158_R29_CDT";
	private static String INV1159_R29_CD = "INV1159_R29_CD";
	protected static String INV1159_R29_CD_LengthTimAdministered = INV1159_R29_CD+delimiter1+"LengthTimAdministered_INV1159_R29_CD";	
	
	private static String INV1158_R30_CDT = "INV1158_R30_CDT";
	protected static String INV1158_R30_CDT_DrugName = INV1158_R30_CDT+delimiter1+"DrugName_INV1158_R30_CDT";
	private static String INV1159_R30_CD = "INV1159_R30_CD";
	protected static String INV1159_R30_CD_LengthTimAdministered = INV1159_R30_CD+delimiter1+"LengthTimAdministered_INV1159_R30_CD";	
	
	
	
	private static String INV1160 = "INV1160";
	protected static String INV1160_DateInjectableMedicationWasStopped = INV1160+delimiter1+"DateInjectableMedicationWasStopped_INV1160";
	private static String INV1161_CD = "INV1161_CD";
	protected static String INV1161_CD_WasSurgeryPerformedToTreatMDRTB = INV1161_CD+delimiter1+"WasSurgeryPerformedToTreatMDRTB_INV1161_CD";
	private static String INV1162 = "INV1162";
	protected static String INV1162_DateOfSurgery = INV1162+delimiter1+"DateOfSurgery_INV1162";
	private static String code42563_7_CD_R1_CDT = "42563_7_CD_R1_CDT";
	protected static String code42563_7_CD_R1_CDT_SideEffect = code42563_7_CD_R1_CDT+delimiter1+"SideEffect_42563_7_CD_R1_CDT";
	private static String INV1164_R1_CD = "INV1164_R1_CD";
	protected static String INV1164_R1_CD_SideEffectExperienced = INV1164_R1_CD+delimiter1+"SideEffectExperienced_INV1164_R1_CD";
	private static String INV1163_R1_CD = "INV1163_R1_CD";
	protected static String INV1163_R1_CD_When = INV1163_R1_CD+delimiter1+"When_INV1163_R1_CD";
	private static String code42563_7_CD_R2_CDT = "42563_7_CD_R2_CDT";
	protected static String code42563_7_CD_R2_CDT_SideEffect = code42563_7_CD_R2_CDT+delimiter1+"SideEffect_42563_7_CD_R2_CDT";
	private static String INV1164_R2_CD = "INV1164_R2_CD";
	protected static String INV1164_R2_CD_SideEffectExperienced = INV1164_R2_CD+delimiter1+"SideEffectExperienced_INV1164_R2_CD";
	private static String INV1163_R2_CD = "INV1163_R2_CD";
	protected static String INV1163_R2_CD_When = INV1163_R2_CD+delimiter1+"When_INV1163_R2_CD";
	private static String code42563_7_CD_R3_CDT = "42563_7_CD_R3_CDT";
	protected static String code42563_7_CD_R3_CDT_SideEffect = code42563_7_CD_R3_CDT+delimiter1+"SideEffect_42563_7_CD_R3_CDT";
	private static String INV1164_R3_CD = "INV1164_R3_CD";
	protected static String INV1164_R3_CD_SideEffectExperienced = INV1164_R3_CD+delimiter1+"SideEffectExperienced_INV1164_R3_CD";
	private static String INV1163_R3_CD = "INV1163_R3_CD";
	protected static String INV1163_R3_CD_When = INV1163_R3_CD+delimiter1+"When_INV1163_R3_CD";
	private static String code42563_7_CD_R4_CDT = "42563_7_CD_R4_CDT";
	protected static String code42563_7_CD_R4_CDT_SideEffect = code42563_7_CD_R4_CDT+delimiter1+"SideEffect_42563_7_CD_R4_CDT";
	private static String INV1164_R4_CD = "INV1164_R4_CD";
	protected static String INV1164_R4_CD_SideEffectExperienced = INV1164_R4_CD+delimiter1+"SideEffectExperienced_INV1164_R4_CD";
	private static String INV1163_R4_CD = "INV1163_R4_CD";
	protected static String INV1163_R4_CD_When = INV1163_R4_CD+delimiter1+"When_INV1163_R4_CD";
	private static String code42563_7_CD_R5_CDT = "42563_7_CD_R5_CDT";
	protected static String code42563_7_CD_R5_CDT_SideEffect = code42563_7_CD_R5_CDT+delimiter1+"SideEffect_42563_7_CD_R5_CDT";
	private static String INV1164_R5_CD = "INV1164_R5_CD";
	protected static String INV1164_R5_CD_SideEffectExperienced = INV1164_R5_CD+delimiter1+"SideEffectExperienced_INV1164_R5_CD";
	private static String INV1163_R5_CD = "INV1163_R5_CD";
	protected static String INV1163_R5_CD_When = INV1163_R5_CD+delimiter1+"When_INV1163_R5_CD";
	private static String code42563_7_CD_R6_CDT = "42563_7_CD_R6_CDT";
	protected static String code42563_7_CD_R6_CDT_SideEffect = code42563_7_CD_R6_CDT+delimiter1+"SideEffect_42563_7_CD_R6_CDT";
	private static String INV1164_R6_CD = "INV1164_R6_CD";
	protected static String INV1164_R6_CD_SideEffectExperienced = INV1164_R6_CD+delimiter1+"SideEffectExperienced_INV1164_R6_CD";
	private static String INV1163_R6_CD = "INV1163_R6_CD";
	protected static String INV1163_R6_CD_When = INV1163_R6_CD+delimiter1+"When_INV1163_R6_CD";
	private static String code42563_7_CD_R7_CDT = "42563_7_CD_R7_CDT";
	protected static String code42563_7_CD_R7_CDT_SideEffect = code42563_7_CD_R7_CDT+delimiter1+"SideEffect_42563_7_CD_R7_CDT";
	private static String INV1164_R7_CD = "INV1164_R7_CD";
	protected static String INV1164_R7_CD_SideEffectExperienced = INV1164_R7_CD+delimiter1+"SideEffectExperienced_INV1164_R7_CD";
	private static String INV1163_R7_CD = "INV1163_R7_CD";
	protected static String INV1163_R7_CD_When = INV1163_R7_CD+delimiter1+"When_INV1163_R7_CD";
	private static String code42563_7_CD_R8_CDT = "42563_7_CD_R8_CDT";
	protected static String code42563_7_CD_R8_CDT_SideEffect = code42563_7_CD_R8_CDT+delimiter1+"SideEffect_42563_7_CD_R8_CDT";
	private static String INV1164_R8_CD = "INV1164_R8_CD";
	protected static String INV1164_R8_CD_SideEffectExperienced = INV1164_R8_CD+delimiter1+"SideEffectExperienced_INV1164_R8_CD";
	private static String INV1163_R8_CD = "INV1163_R8_CD";
	protected static String INV1163_R8_CD_When = INV1163_R8_CD+delimiter1+"When_INV1163_R8_CD";
	private static String code42563_7_CD_R9_CDT = "42563_7_CD_R9_CDT";
	protected static String code42563_7_CD_R9_CDT_SideEffect = code42563_7_CD_R9_CDT+delimiter1+"SideEffect_42563_7_CD_R9_CDT";
	private static String INV1164_R9_CD = "INV1164_R9_CD";
	protected static String INV1164_R9_CD_SideEffectExperienced = INV1164_R9_CD+delimiter1+"SideEffectExperienced_INV1164_R9_CD";
	private static String INV1163_R9_CD = "INV1163_R9_CD";
	protected static String INV1163_R9_CD_When = INV1163_R9_CD+delimiter1+"When_INV1163_R9_CD";
	private static String code42563_7_CD_R10_CDT = "42563_7_CD_R10_CDT";
	protected static String code42563_7_CD_R10_CDT_SideEffect = code42563_7_CD_R10_CDT+delimiter1+"SideEffect_42563_7_CD_R10_CDT";
	private static String INV1164_R10_CD = "INV1164_R10_CD";
	protected static String INV1164_R10_CD_SideEffectExperienced = INV1164_R10_CD+delimiter1+"SideEffectExperienced_INV1164_R10_CD";
	private static String INV1163_R10_CD = "INV1163_R10_CD";
	protected static String INV1163_R10_CD_When = INV1163_R10_CD+delimiter1+"When_INV1163_R10_CD";
	
	private static String code42563_7_CD_R11_CDT = "42563_7_CD_R11_CDT";
	protected static String code42563_7_CD_R11_CDT_SideEffect = code42563_7_CD_R11_CDT+delimiter1+"SideEffect_42563_7_CD_R11_CDT";
	private static String INV1164_R11_CD = "INV1164_R11_CD";
	protected static String INV1164_R11_CD_SideEffectExperienced = INV1164_R11_CD+delimiter1+"SideEffectExperienced_INV1164_R11_CD";
	private static String INV1163_R11_CD = "INV1163_R11_CD";
	protected static String INV1163_R11_CD_When = INV1163_R11_CD+delimiter1+"When_INV1163_R11_CD";
	
	
	private static String code42563_7_CD_R12_CDT = "42563_7_CD_R12_CDT";
	protected static String code42563_7_CD_R12_CDT_SideEffect = code42563_7_CD_R12_CDT+delimiter1+"SideEffect_42563_7_CD_R12_CDT";
	private static String INV1164_R12_CD = "INV1164_R12_CD";
	protected static String INV1164_R12_CD_SideEffectExperienced = INV1164_R12_CD+delimiter1+"SideEffectExperienced_INV1164_R12_CD";
	private static String INV1163_R12_CD = "INV1163_R12_CD";
	protected static String INV1163_R12_CD_When = INV1163_R12_CD+delimiter1+"When_INV1163_R12_CD";
	
	

	private static String code42563_7_CD_R13_CDT = "42563_7_CD_R13_CDT";
	protected static String code42563_7_CD_R13_CDT_SideEffect = code42563_7_CD_R13_CDT+delimiter1+"SideEffect_42563_7_CD_R13_CDT";
	private static String INV1164_R13_CD = "INV1164_R13_CD";
	protected static String INV1164_R13_CD_SideEffectExperienced = INV1164_R13_CD+delimiter1+"SideEffectExperienced_INV1164_R13_CD";
	private static String INV1163_R13_CD = "INV1163_R13_CD";
	protected static String INV1163_R13_CD_When = INV1163_R13_CD+delimiter1+"When_INV1163_R13_CD";
	
	private static String code42563_7_CD_R14_CDT = "42563_7_CD_R14_CDT";
	protected static String code42563_7_CD_R14_CDT_SideEffect = code42563_7_CD_R14_CDT+delimiter1+"SideEffect_42563_7_CD_R14_CDT";
	private static String INV1164_R14_CD = "INV1164_R14_CD";
	protected static String INV1164_R14_CD_SideEffectExperienced = INV1164_R14_CD+delimiter1+"SideEffectExperienced_INV1164_R14_CD";
	private static String INV1163_R14_CD = "INV1163_R14_CD";
	protected static String INV1163_R14_CD_When = INV1163_R14_CD+delimiter1+"When_INV1163_R14_CD";
	
	
	
	public static Map<String, String> mappedRVCT =new HashMap<String, String>();
	
	/**
	 * MAPS
	 */
	
	private static Map<String, String> RVCTFieldsMap= new HashMap<String, String>();

	/**
	 * Methods
	 */
	
	/**
	 * initializeRVCTFormValues: this method initializes the RVCTFieldsMap map that will be send to the common form
	 * for populating the data
	 * @throws NEDSSAppException
	 */
	
	private static void initializeRVCTFormValues() throws NEDSSAppException{
		try{
		
		if(RVCTFieldsMap.size()==0){

			initializePatientDemographics();
			initializeAdministrative();
			initializeInitialEvaluation();
			initializeRisks();
			initializeDiagnosisTesting();
			initializeChest();
			initializeEpidemiologic();
			initializeClinicalHistory();
			initializeDrugRegimen();
			initializeGenotypicPhenotypic_part1();
			initializeGenotypicPhenotypic_part2();
			initializeGenotypicMolecular();
			initializeCaseOutcome();
			initializeMDRTBSupplemental();
			

		}
		}catch(Exception e){
			logger.error(e);
			logger.error("CDCRVCTForm.initializeRVCTFormValues Exception thrown, "+ e);
			throw new NEDSSAppException("CDCRVCTForm.initializeRVCTFormValues Exception thrown, ", e);
			
		}
	}
	
					public static void initializePatientDemographics(){
						/******************************************************Patient Demographics******************************************************/
						//Name
					RVCTFieldsMap.put(DEM102__PatientLastNameDEM102, DEM102);
					RVCTFieldsMap.put(DEM104_FN__PatientFirstNameDEM104, DEM104_FN);
					RVCTFieldsMap.put(DEM105__PatientMiddleNameDEM105, DEM105);
					RVCTFieldsMap.put(DEM250__PatientAliasNameDEM250, DEM250);
					RVCTFieldsMap.put(FullName__Full_Name, FullName);
					
						//Address
					RVCTFieldsMap.put(DEM159_160__PatientStreetDEM159, DEM159_DEM160); 
					RVCTFieldsMap.put(DEM161__PatientCityDEM161, DEM161);
					RVCTFieldsMap.put(DEM162__PatientStateDEM162, DEM162);
					RVCTFieldsMap.put(DEM163__PatientZipDEM163, DEM163);
					RVCTFieldsMap.put(DEM165__PatientCountyDEM165, DEM165);
					RVCTFieldsMap.put(DEM167__PatientCountryDEM167, DEM167);
					RVCTFieldsMap.put(DEM168__CensusTracDEM168, DEM168);
					RVCTFieldsMap.put(CityLimitsINV1112,City_Limits_CD);
					
						//Phone number
					RVCTFieldsMap.put(NBS006__PatientCellNBS006_UseCd, NBS006);
					RVCTFieldsMap.put(DEM177__PatientHmPhDEM177_UseCd, DEM177);
					
						//Work Phone number
					RVCTFieldsMap.put(NBS002__PatientWorkPhNBS002_UseCd, NBS002);
					
						//Reported age
					RVCTFieldsMap.put(INV2001__PatientReportedAge, INV2001);
					RVCTFieldsMap.put(DEM115__PatientDOB, DEM115);
					
						//Pregnant
					RVCTFieldsMap.put(INV178__PatientPregnantINV178, INV178_CD);
					RVCTFieldsMap.put(NBS128__PatientPregnantWeeksNBS128, NBS128);
					
						//Race
					RVCTFieldsMap.put(DEM152_AI_CDT__PatientRaceDEM152, DEM152_AI_CDT);
					RVCTFieldsMap.put(DEM152_A_CDT__PatientRaceDEM152, DEM152_A_CDT);
					RVCTFieldsMap.put(DEM152_B_CDT__PatientRaceDEM152, DEM152_B_CDT);
					RVCTFieldsMap.put(DEM152_W_CDT__PatientRaceDEM152, DEM152_W_CDT);
					RVCTFieldsMap.put(DEM152_O_CDT__PatientRaceDEM152, DEM152_O_CDT);
					RVCTFieldsMap.put(DEM152_NH_CDT__PatientRaceDEM152, DEM152_NH_CDT);
					RVCTFieldsMap.put(DEM152_U_CDT__PatientRaceDEM152, DEM152_U_CDT);
					RVCTFieldsMap.put(DEM152_R_CDT__PatientRaceDEM152, DEM152_R_CDT);
					RVCTFieldsMap.put(DEM152_D_CDT__PatientRaceDEM152, DEM152_D_CDT);
					RVCTFieldsMap.put(DEM152_NA_CDT__PatientRaceDEM152, DEM152_NA_CDT);
					
						//Detailed Race
					RVCTFieldsMap.put(DEM242__AmericanIndianAlaskanNative, DEM242);
					RVCTFieldsMap.put(DEM243_Asian, DEM243);
					RVCTFieldsMap.put(DEM244_BlackAfricanAmerican, DEM244);
					RVCTFieldsMap.put(DEM245_Hawaiian, DEM245);
					RVCTFieldsMap.put(DEM246_White, DEM246);
					
						//Ethnicity
					RVCTFieldsMap.put(DEM155_CDT__PatientEthnicityDEM155, DEM155_CDT);
					RVCTFieldsMap.put(NBS273_CDT__PatientEthnicityUnkownReasonNBS273, NBS273_CDT);
					
						//Gender
					RVCTFieldsMap.put(DEM113_CD__PatientCurrentSexDEM113, DEM113_CD);
					RVCTFieldsMap.put(NBS274_CD__PatientTransgenderNBS274, NBS274_CD);
					RVCTFieldsMap.put(NBS272_CD__PatientSexUnkownReasonNBS272, NBS272_CD);
					RVCTFieldsMap.put(CURRENT_SEX_currentSex, CURRENT_SEX);

					
						//Employment
					RVCTFieldsMap.put(EMPLOYMENT__PatientWorkPhoneNBS002_PatientWorkPhoneExtensionNBS003, Employment_Phone);
					
					}
	
					public static void initializeAdministrative(){


						/******************************************************Administrative Information******************************************************/
					
					RVCTFieldsMap.put(INV111_date_reported,  date_reported);
					RVCTFieldsMap.put(INV165_mmwrweek,  mmwr_week);
					RVCTFieldsMap.put(INV166_mmwryear,  mmwr_year);
					RVCTFieldsMap.put(INV1115_caseverfication,  case_verification);
					RVCTFieldsMap.put(INV163_CDT_casestatus,  INV163_CDT);
					RVCTFieldsMap.put(INV173_casenumber,  INV173);
					RVCTFieldsMap.put(INV1109_CD_casecounted,  INV1109_CD);
					RVCTFieldsMap.put(INV1111_CDT_countryofverifiedcase,  INV1111_CDT);
					RVCTFieldsMap.put(INV1108_localcasenumber,  INV1108);
					RVCTFieldsMap.put(INV1110_previouslyreportedstatecase,  INV1110);
					RVCTFieldsMap.put(INV1111_Oth_othercountryofverifiedcase,  INV1111_Oth);
					
					}
					
					
					public static void initializeInitialEvaluation(){
					
					/******************************************************Initial Evaluation******************************************************/
					
					
					RVCTFieldsMap.put(DEM126_countryofbirth,  DEM126);
					RVCTFieldsMap.put(DEM2005_dateoffirstusarrival,  DEM2005);
					RVCTFieldsMap.put(DEM2003_CD_eligibleforuscitizenship,  DEM2003_CD);
					//RVCTFieldsMap.put(DEM2003_CD_eligibleforuscitizenship,  DEM2003_CD);
					RVCTFieldsMap.put(INV1113_CDT_countriesofbirthforprimaryguardians,  INV1113_CDT);
					RVCTFieldsMap.put(INV501_CDT_countryofusualresidency,  INV501_CDT);
					RVCTFieldsMap.put(INV1114_CD_outsideusfor90daysormore,  INV1114_CD);
					RVCTFieldsMap.put(TB101_CD_statusattbdiagnosis,  TB101_CD);
					RVCTFieldsMap.put(INV1116_CD_initialreasonevaluatedfortb,  INV1116_CD);
					RVCTFieldsMap.put(INV1116_OTH_initialreasonevaluatedfortb,  INV1116_OTH);
					
					RVCTFieldsMap.put(INV1276_CD_everworkedas,  INV1276_CD);
					RVCTFieldsMap.put(INV1276_C_CD_everworkedas,  INV1276_C_CD);
					RVCTFieldsMap.put(INV1276_H_CD_everworkedas,  INV1276_H_CD);
					RVCTFieldsMap.put(INV1276_M_CD_everworkedas,  INV1276_M_CD);
					RVCTFieldsMap.put(INV1276_U_CD_everworkedas,  INV1276_U_CD);
					RVCTFieldsMap.put(INV1276_N_CD_everworkedas,  INV1276_N_CD);
					
					//Repeating block occupation
					
					RVCTFieldsMap.put(code85659_1_R1_currentOccupationStandarized,  code85659_1_R1);
					RVCTFieldsMap.put(code85659_1_R2_currentOccupationStandarized,  code85659_1_R2);
					RVCTFieldsMap.put(code85659_1_R3_currentOccupationStandarized,  code85659_1_R3);
					
					RVCTFieldsMap.put(code85658_3_R1_currentOccupation, code85658_3_R1);
					RVCTFieldsMap.put(code85658_3_R2_currentOccupation, code85658_3_R2);
					RVCTFieldsMap.put(code85658_3_R3_currentOccupation, code85658_3_R3);
							
					RVCTFieldsMap.put(code85657_5_R1_currentIndustryStandarized,  code85657_5_R1);
					RVCTFieldsMap.put(code85657_5_R2_currentIndustryStandarized,  code85657_5_R2);
					RVCTFieldsMap.put(code85657_5_R3_currentIndustryStandarized,  code85657_5_R3);
				
					RVCTFieldsMap.put(code85078_4_R1_currentIndustry,  code85078_4_R1);
					RVCTFieldsMap.put(code85078_4_R2_currentIndustry,  code85078_4_R2);
					RVCTFieldsMap.put(code85078_4_R3_currentIndustry,  code85078_4_R3);
				
					
					
					}
					
					
					public static void initializeRisks(){
					
					/****************************************************** Risks ******************************************************/
					
					RVCTFieldsMap.put(ARB016_CD_diabeticAtDiagnosisEvaluation,  ARB016_CD);
					RVCTFieldsMap.put(TB127_CD_homelessInThePast12Months,  TB127_CD);
					RVCTFieldsMap.put(Code32911000_CD_homelessever,  Code32911000_CD);
					RVCTFieldsMap.put(NBS689_CD_correctionalFacility,  NBS689_CD);																																											
					RVCTFieldsMap.put(INV1119_CD_typeoffacility,  INV1119_CD);	
					RVCTFieldsMap.put(INV1119_OTH_typeoffacility,  INV1119_OTH);	
					
					RVCTFieldsMap.put(INV649_CD_correctionalfacilityever,  INV649_CD);																																												
					RVCTFieldsMap.put(INV636_CD_residentoflongtermcarefacility,  INV636_CD);
					RVCTFieldsMap.put(INV1120_CD_typeoffacility,  INV1120_CD);		
					RVCTFieldsMap.put(INV1120_OTH_typeoffacility,  INV1120_OTH);					
					RVCTFieldsMap.put(INV607_CD_injectingdruguseinthepast12months,  INV607_CD);
					RVCTFieldsMap.put(INV608_CD_injectingdruguseinthepast12months,  INV608_CD);
					
					
					RVCTFieldsMap.put(ARB025_CD_HeavyAlcoholUseInThePast12Months,  ARB025_CD);
					RVCTFieldsMap.put(PHC690_CD_TNFAntagonistTherapy,  PHC690_CD);
					RVCTFieldsMap.put(Code161663000_CD_PostOrganTransplantation,  Code161663000_CD);
					RVCTFieldsMap.put(ARB024_CD_EndStageRenalDisease,  ARB024_CD);
					RVCTFieldsMap.put(PHC2236_CD_ViralHepatitisBorCOnly,  PHC2236_CD);
					RVCTFieldsMap.put(VAR126_CD_OtherImmunocompromise,  VAR126_CD);
					RVCTFieldsMap.put(Code72166_2_CD_CurrentSmokingStatusAtDiagnosticEvaluation,  Code72166_2_CD);
					RVCTFieldsMap.put(TRAVEL10_CD_LivedOutsideOfUSForMoreThan2Months,  TRAVEL10_CD);		
					RVCTFieldsMap.put(NBS560_CD_OtherRiskFactor, NBS560_CD);		
					RVCTFieldsMap.put(NBS561_SpecifyOtherRiskFactor,  NBS561);		
					
					
					}
					
					
					public static void initializeDiagnosisTesting(){
					
					/****************************************************** Diagnostic Testing ******************************************************/

					
					//HIV Status
					RVCTFieldsMap.put(NBS859_CD_HivStatus,  NBS859_CD);	
					RVCTFieldsMap.put(NBS450_Collection_Date,  NBS450);	
					RVCTFieldsMap.put(NBS870_Date_Reported,  NBS870);	
					
					//Tuberculin
					RVCTFieldsMap.put(TUB147_CD_Tuberculin,  TUB147_CD);
					RVCTFieldsMap.put(TUB148_DatePlaced,  TUB148);
					RVCTFieldsMap.put(NBS866_DateRead,  NBS866);
					RVCTFieldsMap.put(TUB149_MMOfInduration,  TUB149);
			
					//Interferon
					RVCTFieldsMap.put(TUB150_CD_Interferon,  TUB150_CD);		
					RVCTFieldsMap.put(NBS868_CD_TestType,  NBS868_CD);		
					
					RVCTFieldsMap.put(TUB151_CollectionDate,  TUB151);	
					RVCTFieldsMap.put(NBS869_DateReported,  NBS869);	
					RVCTFieldsMap.put(NBS871_QuantitativeResult,  NBS871);	
					RVCTFieldsMap.put(NBS872_CDT_QuantitativeResultUnits,  NBS872_CDT);	
				
					//Sputum Smear
					RVCTFieldsMap.put(TUB120_CD_SputumSmear,  TUB120_CD);
					RVCTFieldsMap.put(TUB121_CollectionDate,  TUB121);
					RVCTFieldsMap.put(NBS863_DateReported,  NBS863);
					
					//Sputum Culture
					RVCTFieldsMap.put(TUB122_CD_SputumCulture,  TUB122_CD);
					RVCTFieldsMap.put(TUB123_CollectionDate,  TUB123);
					RVCTFieldsMap.put(TUB124_DateReported,  TUB124);
					
					//Smear Pathology
					RVCTFieldsMap.put(TUB126_CD_SmearPathology,  TUB126_CD);
					RVCTFieldsMap.put(TUB129_CD_TestType,  TUB129_CD);
					RVCTFieldsMap.put(TUB127_CollectionDate,  TUB127);
					RVCTFieldsMap.put(NBS864_DateReported,  NBS864);
					RVCTFieldsMap.put(TUB128_CDT_SpecimenSource,  TUB128_CDT);
							
					//Culture of Tissue
					RVCTFieldsMap.put(TUB130_CD_CultureOfTissue,  TUB130_CD);
					RVCTFieldsMap.put(TUB131_CollectionDate,  TUB131);
					RVCTFieldsMap.put(TUB132_CDT_SpecimenSource,  TUB132_CDT);
					RVCTFieldsMap.put(TUB133_DateReported,  TUB133);
				
					//Nucleid Acid	
					RVCTFieldsMap.put(TUB135_CD_NucleicAcid,  TUB135_CD);
					RVCTFieldsMap.put(TUB136_CollectionDate,  TUB136);
					RVCTFieldsMap.put(TUB139_DateReported,  TUB139);
					RVCTFieldsMap.put(NBS865_CDT_SpecimenSource,  NBS865_CDT);
					
					//Diagnostic testing repeating
					RVCTFieldsMap.put(INV290_R1_CDT_TestType,  INV290_R1_CDT);
					RVCTFieldsMap.put(LAB165_R1_CDT_SpecimenSource,  LAB165_R1_CDT);
					RVCTFieldsMap.put(LAB163_R1_SpecimenSource,  LAB163_R1);
					RVCTFieldsMap.put(LAB167_R1_DateReported,  LAB167_R1);
					RVCTFieldsMap.put(INV291_R1_CD_testResultQualitative,  INV291_R1_CD);
					RVCTFieldsMap.put(LAB628_R1_QuantitativeResult,  LAB628_R1);
					RVCTFieldsMap.put(LAB115_R1_CD_resultsUnits,  LAB115_R1_CD);
					
					RVCTFieldsMap.put(INV290_R2_CDT_TestType,  INV290_R2_CDT);
					RVCTFieldsMap.put(LAB165_R2_CDT_SpecimenSource,  LAB165_R2_CDT);
					RVCTFieldsMap.put(LAB163_R2_SpecimenSource,  LAB163_R2);
					RVCTFieldsMap.put(LAB167_R2_DateReported,  LAB167_R2);
					RVCTFieldsMap.put(INV291_R2_CD_testResultQualitative,  INV291_R2_CD);
					RVCTFieldsMap.put(LAB628_R2_QuantitativeResult,  LAB628_R2);
					RVCTFieldsMap.put(LAB115_R2_CD_resultsUnits,  LAB115_R2_CD);
					
					RVCTFieldsMap.put(INV290_R3_CDT_TestType,  INV290_R3_CDT);
					RVCTFieldsMap.put(LAB165_R3_CDT_SpecimenSource,  LAB165_R3_CDT);
					RVCTFieldsMap.put(LAB163_R3_SpecimenSource,  LAB163_R3);
					RVCTFieldsMap.put(LAB167_R3_DateReported,  LAB167_R3);
					RVCTFieldsMap.put(INV291_R3_CD_testResultQualitative,  INV291_R3_CD);
					RVCTFieldsMap.put(LAB628_R3_QuantitativeResult,  LAB628_R3);
					RVCTFieldsMap.put(LAB115_R3_CD_resultsUnits,  LAB115_R3_CD);
					
					RVCTFieldsMap.put(INV290_R4_CDT_TestType,  INV290_R4_CDT);
					RVCTFieldsMap.put(LAB165_R4_CDT_SpecimenSource,  LAB165_R4_CDT);
					RVCTFieldsMap.put(LAB163_R4_SpecimenSource,  LAB163_R4);
					RVCTFieldsMap.put(LAB167_R4_DateReported,  LAB167_R4);
					RVCTFieldsMap.put(INV291_R4_CD_testResultQualitative,  INV291_R4_CD);
					RVCTFieldsMap.put(LAB628_R4_QuantitativeResult,  LAB628_R4);
					RVCTFieldsMap.put(LAB115_R4_CD_resultsUnits,  LAB115_R4_CD);
					
					RVCTFieldsMap.put(INV290_R5_CDT_TestType,  INV290_R5_CDT);
					RVCTFieldsMap.put(LAB165_R5_CDT_SpecimenSource,  LAB165_R5_CDT);
					RVCTFieldsMap.put(LAB163_R5_SpecimenSource,  LAB163_R5);
					RVCTFieldsMap.put(LAB167_R5_DateReported,  LAB167_R5);
					RVCTFieldsMap.put(INV291_R5_CD_testResultQualitative,  INV291_R5_CD);
					RVCTFieldsMap.put(LAB628_R5_QuantitativeResult,  LAB628_R5);
					RVCTFieldsMap.put(LAB115_R5_CD_resultsUnits,  LAB115_R5_CD);
					
					RVCTFieldsMap.put(INV290_R6_CDT_TestType,  INV290_R6_CDT);
					RVCTFieldsMap.put(LAB165_R6_CDT_SpecimenSource,  LAB165_R6_CDT);
					RVCTFieldsMap.put(LAB163_R6_SpecimenSource,  LAB163_R6);
					RVCTFieldsMap.put(LAB167_R6_DateReported,  LAB167_R6);
					RVCTFieldsMap.put(INV291_R6_CD_testResultQualitative,  INV291_R6_CD);
					RVCTFieldsMap.put(LAB628_R6_QuantitativeResult,  LAB628_R6);
					RVCTFieldsMap.put(LAB115_R6_CD_resultsUnits,  LAB115_R6_CD);
					
					RVCTFieldsMap.put(INV290_R7_CDT_TestType,  INV290_R7_CDT);
					RVCTFieldsMap.put(LAB165_R7_CDT_SpecimenSource,  LAB165_R7_CDT);
					RVCTFieldsMap.put(LAB163_R7_SpecimenSource,  LAB163_R7);
					RVCTFieldsMap.put(LAB167_R7_DateReported,  LAB167_R7);
					RVCTFieldsMap.put(INV291_R7_CD_testResultQualitative,  INV291_R7_CD);
					RVCTFieldsMap.put(LAB628_R7_QuantitativeResult,  LAB628_R7);
					RVCTFieldsMap.put(LAB115_R7_CD_resultsUnits,  LAB115_R7_CD);
					
					RVCTFieldsMap.put(INV290_R8_CDT_TestType,  INV290_R8_CDT);
					RVCTFieldsMap.put(LAB165_R8_CDT_SpecimenSource,  LAB165_R8_CDT);
					RVCTFieldsMap.put(LAB163_R8_SpecimenSource,  LAB163_R8);
					RVCTFieldsMap.put(LAB167_R8_DateReported,  LAB167_R8);
					RVCTFieldsMap.put(INV291_R8_CD_testResultQualitative,  INV291_R8_CD);
					RVCTFieldsMap.put(LAB628_R8_QuantitativeResult,  LAB628_R8);
					RVCTFieldsMap.put(LAB115_R8_CD_resultsUnits,  LAB115_R8_CD);
					
					
					}
					
					
					public static void initializeChest(){
					
					/****************************************************** Chest Radiograph and Other Chest Imaging Results ******************************************************/

					RVCTFieldsMap.put(TUB141_CD_InitialChestXRay,  TUB141_CD);
					RVCTFieldsMap.put(LAB681A_XRayDate,  LAB681A);
					RVCTFieldsMap.put(TUB142_CD_evidenceOfCavityXRay,  TUB142_CD);
					RVCTFieldsMap.put(TUB143_CD_evidenceOfMiliaryTBXRay,  TUB143_CD);
					RVCTFieldsMap.put(TUB144_CD_InitialChestCTScan,  TUB144_CD);
					RVCTFieldsMap.put(LAB681B_CTScanDate,  LAB681B);
					RVCTFieldsMap.put(TUB145_CD_evidenceOfCavityCTScan,  TUB145_CD);
					RVCTFieldsMap.put(TUB146_CD_evidenceOfMiliaryTBCTScan,  TUB146_CD);
					
					//Chest repeating
					RVCTFieldsMap.put(LAB677_R1_CD_ChestTestType,  LAB677_R1_CD);
					RVCTFieldsMap.put(LAB677_R1_OTH_ChestTestType,  LAB677_R1_OTH);
					RVCTFieldsMap.put(LAB681_R1_ChestDateOfStudy,  LAB681_R1);
					RVCTFieldsMap.put(LAB678_R1_CD_ChestResultOfStudy,  LAB678_R1_CD);
					RVCTFieldsMap.put(LAB679_R1_CD_ChestEvidenceOfCavity,  LAB679_R1_CD);
					RVCTFieldsMap.put(LAB680_R1_CD_chestEvidenceOfMiliary,  LAB680_R1_CD);
					
					RVCTFieldsMap.put(LAB677_R2_CD_ChestTestType,  LAB677_R2_CD);
					RVCTFieldsMap.put(LAB677_R2_OTH_ChestTestType,  LAB677_R2_OTH);
					RVCTFieldsMap.put(LAB681_R2_ChestDateOfStudy,  LAB681_R2);
					RVCTFieldsMap.put(LAB678_R2_CD_ChestResultOfStudy,  LAB678_R2_CD);
					RVCTFieldsMap.put(LAB679_R2_CD_ChestEvidenceOfCavity,  LAB679_R2_CD);
					RVCTFieldsMap.put(LAB680_R2_CD_chestEvidenceOfMiliary,  LAB680_R2_CD);
					
					RVCTFieldsMap.put(LAB677_R3_CD_ChestTestType,  LAB677_R3_CD);
					RVCTFieldsMap.put(LAB677_R3_OTH_ChestTestType,  LAB677_R3_OTH);
					RVCTFieldsMap.put(LAB681_R3_ChestDateOfStudy,  LAB681_R3);
					RVCTFieldsMap.put(LAB678_R3_CD_ChestResultOfStudy,  LAB678_R3_CD);
					RVCTFieldsMap.put(LAB679_R3_CD_ChestEvidenceOfCavity,  LAB679_R3_CD);
					RVCTFieldsMap.put(LAB680_R3_CD_chestEvidenceOfMiliary,  LAB680_R3_CD);
					
					}
					
					public static void initializeDrugRegimen(){
					
					
					/****************************************************** Initial Drug Regimen ******************************************************/

				
					RVCTFieldsMap.put(VAR141_DateTherapyStarted,  VAR141);
					RVCTFieldsMap.put(INV1139_CD_InitialDrugRegimenNOTRIPEHRZE,  INV1139_CD);
					/*RVCTFieldsMap.put(INV1139_CD_InitialDrugRegimenNOTRIPEHRZE,  INV1139_CD);
					RVCTFieldsMap.put(INV1139_CD_InitialDrugRegimenNOTRIPEHRZE,  INV1139_CD);
					RVCTFieldsMap.put(INV1139_CD_InitialDrugRegimenNOTRIPEHRZE,  INV1139_CD);
					RVCTFieldsMap.put(INV1139_CD_InitialDrugRegimenNOTRIPEHRZE,  INV1139_CD);
					RVCTFieldsMap.put(INV1139_CD_InitialDrugRegimenNOTRIPEHRZE,  INV1139_CD);*/
					RVCTFieldsMap.put(INV1139_OTH_InitialDrugRegimenNOTRIPEHRZE, INV1139_OTH);
					RVCTFieldsMap.put(code6038_CD_Isoniazid,  code6038_CD);
					RVCTFieldsMap.put(code9384_CD_Rifampin,  code9384_CD);
					RVCTFieldsMap.put(code8987_CD_Pyrazinamide,  code8987_CD);
					RVCTFieldsMap.put(code4110_CD_Ethambutol,  code4110_CD);
					RVCTFieldsMap.put(code10109_CD_Streptomycin,  code10109_CD);
					RVCTFieldsMap.put(code55672_CD_Rifabutin,  code55672_CD);
					RVCTFieldsMap.put(code35617_CD_Rifapentine,  code35617_CD);
					RVCTFieldsMap.put(code4127_CD_Ethionamide,  code4127_CD);
					RVCTFieldsMap.put(code641_CD_Amikacin,  code641_CD);
					RVCTFieldsMap.put(code6099_CD_Kanamycin,  code6099_CD);
					RVCTFieldsMap.put(code78903_CD_Capreomycin,  code78903_CD);
					RVCTFieldsMap.put(code2551_CD_Ciprofloxacin,  code2551_CD);
					RVCTFieldsMap.put(code82122_CD_Levofloxacin,  code82122_CD);
					RVCTFieldsMap.put(code7623_CD_Ofloxacin,  code7623_CD);
					RVCTFieldsMap.put(code139462_CD_Moxifloxacin,  code139462_CD);
					RVCTFieldsMap.put(PHC1888_CD_OtherQuinolones,  PHC1888_CD);
					RVCTFieldsMap.put(code3007_CD_Cycloserine,  code3007_CD);
					RVCTFieldsMap.put(code7833_CD_ParaAminesalicylicacid,  code7833_CD);
					RVCTFieldsMap.put(code190376_CD_Linezolid,  code190376_CD);
					RVCTFieldsMap.put(code1364504_CD_Bedaquiline,  code1364504_CD);
					RVCTFieldsMap.put(PHC1889_CD_Delamanid,  PHC1889_CD);
					RVCTFieldsMap.put(code2592_CD_Clofazimine,  code2592_CD);
					RVCTFieldsMap.put(code2198359_CD_Pretomanid,  code2198359_CD);
					RVCTFieldsMap.put(NBS456_CD_OtherDrugRegimen,  NBS456_CD);
					RVCTFieldsMap.put(NBS456_OTH_Specify,  NBS456_OTH);


		}


					public static void initializeEpidemiologic(){


					/****************************************************** Epidemiologic Investigation ******************************************************/

					RVCTFieldsMap.put(INV1274_CD_caseMeetBinational,  INV1274_CD);
					RVCTFieldsMap.put(INV515_CD_whichCriteriaWereMet,  INV515_CD);
					RVCTFieldsMap.put(INV515_S_CD_whichCriteriaWereMet,  INV515_S_CD);
					RVCTFieldsMap.put(INV515_C_CD_whichCriteriaWereMet,  INV515_C_CD);
					RVCTFieldsMap.put(INV515_R_CD_whichCriteriaWereMet,  INV515_R_CD);
					RVCTFieldsMap.put(INV515_W_CD_whichCriteriaWereMet,  INV515_W_CD);
					RVCTFieldsMap.put(INV515_B_CD_whichCriteriaWereMet,  INV515_B_CD);
					RVCTFieldsMap.put(INV515_O_CD_whichCriteriaWereMet,  INV515_O_CD);
					RVCTFieldsMap.put(INV1122_CD_caseIdentifiedDuringTheContactInvestigation,  INV1122_CD);
					RVCTFieldsMap.put(INV1123_CD_evaluatedForTB,  INV1123_CD);
					RVCTFieldsMap.put(INV1134_CD_contactInvestigationConducted,  INV1134_CD);
					RVCTFieldsMap.put(INV1124_R1_linkedStateCaseNumber,  INV1124_R1);
					RVCTFieldsMap.put(INV1124_R2_linkedStateCaseNumber,  INV1124_R2);
					RVCTFieldsMap.put(INV1124_R3_linkedStateCaseNumber,  INV1124_R3);
					RVCTFieldsMap.put(INV1124_R4_linkedStateCaseNumber,  INV1124_R4);
					RVCTFieldsMap.put(INV1124_R5_linkedStateCaseNumber,  INV1124_R5);
					RVCTFieldsMap.put(INV1124_R6_linkedStateCaseNumber,  INV1124_R6);
					RVCTFieldsMap.put(INV1124_R7_linkedStateCaseNumber,  INV1124_R7);
					RVCTFieldsMap.put(INV1124_R8_linkedStateCaseNumber,  INV1124_R8);
					}
					
					public static void initializeClinicalHistory(){
					
					
					/****************************************************** Clinical History and Findings ******************************************************/

					RVCTFieldsMap.put(code161413004_CD_previouslyDiagnosed,  code161413004_CD);
					RVCTFieldsMap.put(INV1135_R1_CD_diagnosisType,  INV1135_R1_CD);
					RVCTFieldsMap.put(code82758_4_R1_dateOfDiagnosis,  code82758_4_R1);
					RVCTFieldsMap.put(INV1136_R1_previousStateCaseNumber,  INV1136_R1);
					RVCTFieldsMap.put(INV1137_R1_CD_completedTreatment,  INV1137_R1_CD);
					RVCTFieldsMap.put(INV1135_R2_CD_diagnosisType,  INV1135_R2_CD);
					RVCTFieldsMap.put(code82758_4_R2_dateOfDiagnosis,  code82758_4_R2);
					RVCTFieldsMap.put(INV1136_R2_previousStateCaseNumber,  INV1136_R2);
					RVCTFieldsMap.put(INV1137_R2_CD_completedTreatment,  INV1137_R2_CD);
					RVCTFieldsMap.put(INV1135_R3_CD_diagnosisType,  INV1135_R3_CD);
					RVCTFieldsMap.put(code82758_4_R3_dateOfDiagnosis,  code82758_4_R3);
					RVCTFieldsMap.put(INV1136_R3_previousStateCaseNumber,  INV1136_R3);
					RVCTFieldsMap.put(INV1137_R3_CD_completedTreatment,  INV1137_R3_CD);
					RVCTFieldsMap.put(INV137_dateOfIllnessOnset,  INV137);
					
					RVCTFieldsMap.put(INV1133_secondarySitesOfTBDisease, INV1133secondarySites);
					RVCTFieldsMap.put(INV1133_CD_sitesOfTBDisease, INV1133_CD);
					RVCTFieldsMap.put(INV1133_OTH_sitesOfTBDisease, INV1133_OTH);
					
					RVCTFieldsMap.put(INV1133_PU_CD_sitesOfTBDisease,  INV1133_PU_CD);
					RVCTFieldsMap.put(INV1133_PL_CD_sitesOfTBDisease,  INV1133_PL_CD);
					RVCTFieldsMap.put(INV1133_SI_CD_sitesOfTBDisease,  INV1133_SI_CD);
					RVCTFieldsMap.put(INV1133_LC_CD_sitesOfTBDisease,  INV1133_LC_CD);
					RVCTFieldsMap.put(INV1133_LI_CD_sitesOfTBDisease,  INV1133_LI_CD);
					RVCTFieldsMap.put(INV1133_LA_CD_sitesOfTBDisease,  INV1133_LA_CD);
					RVCTFieldsMap.put(INV1133_LO_CD_sitesOfTBDisease,  INV1133_LO_CD);
					RVCTFieldsMap.put(INV1133_LU_CD_sitesOfTBDisease,  INV1133_LU_CD);
					RVCTFieldsMap.put(INV1133_L_CD_sitesOfTBDisease,  INV1133_L_CD);
					RVCTFieldsMap.put(INV1133_B_CD_sitesOfTBDisease,  INV1133_B_CD);
					RVCTFieldsMap.put(INV1133_G_CD_sitesOfTBDisease,  INV1133_G_CD);
					RVCTFieldsMap.put(INV1133_M_CD_sitesOfTBDisease,  INV1133_M_CD);
					RVCTFieldsMap.put(INV1133_P_CD_sitesOfTBDisease,  INV1133_P_CD);
					RVCTFieldsMap.put(INV1133_O_CD_sitesOfTBDisease,  INV1133_O_CD);
					}
					
					public static void initializeGenotypicPhenotypic_part1(){
					
					/****************************************************** Genotyping And Drug Suscetibility Testing (Phenotypic) ******************************************************/
					
					RVCTFieldsMap.put(INV1145_CD_IsolateSubmittedForGenotyping,  INV1145_CD);
					RVCTFieldsMap.put(LAB125_AccessionNumberGen,  LAB125);
					RVCTFieldsMap.put(INV1147_CD_WasPhenotypic,  INV1147_CD);
					
					RVCTFieldsMap.put(LABAST6_R1_CDT_DrugName,  LABAST6_R1_CDT);
					RVCTFieldsMap.put(LABAST5_R1_DateCollected,  LABAST5_R1);
					RVCTFieldsMap.put(LABAST14_R1_DateReported,  LABAST14_R1);
					RVCTFieldsMap.put(LABAST3_R1_CDT_SpecimenSource,  LABAST3_R1_CDT);
					RVCTFieldsMap.put(LABAST8_R1_CD_Result,  LABAST8_R1_CD);
					RVCTFieldsMap.put(LABAST7_R1_CDT_TestMethodOptional,  LABAST7_R1_CDT);

					RVCTFieldsMap.put(LABAST6_R2_CDT_DrugName,  LABAST6_R2_CDT);
					RVCTFieldsMap.put(LABAST5_R2_DateCollected,  LABAST5_R2);
					RVCTFieldsMap.put(LABAST14_R2_DateReported,  LABAST14_R2);
					RVCTFieldsMap.put(LABAST3_R2_CDT_SpecimenSource,  LABAST3_R2_CDT);
					RVCTFieldsMap.put(LABAST8_R2_CD_Result,  LABAST8_R2_CD);
					RVCTFieldsMap.put(LABAST7_R2_CDT_TestMethodOptional,  LABAST7_R2_CDT);

					RVCTFieldsMap.put(LABAST6_R3_CDT_DrugName,  LABAST6_R3_CDT);
					RVCTFieldsMap.put(LABAST5_R3_DateCollected,  LABAST5_R3);
					RVCTFieldsMap.put(LABAST14_R3_DateReported,  LABAST14_R3);
					RVCTFieldsMap.put(LABAST3_R3_CDT_SpecimenSource,  LABAST3_R3_CDT);
					RVCTFieldsMap.put(LABAST8_R3_CD_Result,  LABAST8_R3_CD);
					RVCTFieldsMap.put(LABAST7_R3_CDT_TestMethodOptional,  LABAST7_R3_CDT);

					RVCTFieldsMap.put(LABAST6_R4_CDT_DrugName,  LABAST6_R4_CDT);
					RVCTFieldsMap.put(LABAST5_R4_DateCollected,  LABAST5_R4);
					RVCTFieldsMap.put(LABAST14_R4_DateReported,  LABAST14_R4);
					RVCTFieldsMap.put(LABAST3_R4_CDT_SpecimenSource,  LABAST3_R4_CDT);
					RVCTFieldsMap.put(LABAST8_R4_CD_Result,  LABAST8_R4_CD);
					RVCTFieldsMap.put(LABAST7_R4_CDT_TestMethodOptional,  LABAST7_R4_CDT);

					RVCTFieldsMap.put(LABAST6_R5_CDT_DrugName,  LABAST6_R5_CDT);
					RVCTFieldsMap.put(LABAST5_R5_DateCollected,  LABAST5_R5);
					RVCTFieldsMap.put(LABAST14_R5_DateReported,  LABAST14_R5);
					RVCTFieldsMap.put(LABAST3_R5_CDT_SpecimenSource,  LABAST3_R5_CDT);
					RVCTFieldsMap.put(LABAST8_R5_CD_Result,  LABAST8_R5_CD);
					RVCTFieldsMap.put(LABAST7_R5_CDT_TestMethodOptional,  LABAST7_R5_CDT);

					RVCTFieldsMap.put(LABAST6_R6_CDT_DrugName,  LABAST6_R6_CDT);
					RVCTFieldsMap.put(LABAST5_R6_DateCollected,  LABAST5_R6);
					RVCTFieldsMap.put(LABAST14_R6_DateReported,  LABAST14_R6);
					RVCTFieldsMap.put(LABAST3_R6_CDT_SpecimenSource,  LABAST3_R6_CDT);
					RVCTFieldsMap.put(LABAST8_R6_CD_Result,  LABAST8_R6_CD);
					RVCTFieldsMap.put(LABAST7_R6_CDT_TestMethodOptional,  LABAST7_R6_CDT);

					RVCTFieldsMap.put(LABAST6_R7_CDT_DrugName,  LABAST6_R7_CDT);
					RVCTFieldsMap.put(LABAST5_R7_DateCollected,  LABAST5_R7);
					RVCTFieldsMap.put(LABAST14_R7_DateReported,  LABAST14_R7);
					RVCTFieldsMap.put(LABAST3_R7_CDT_SpecimenSource,  LABAST3_R7_CDT);
					RVCTFieldsMap.put(LABAST8_R7_CD_Result,  LABAST8_R7_CD);
					RVCTFieldsMap.put(LABAST7_R7_CDT_TestMethodOptional,  LABAST7_R7_CDT);

					RVCTFieldsMap.put(LABAST6_R8_CDT_DrugName,  LABAST6_R8_CDT);
					RVCTFieldsMap.put(LABAST5_R8_DateCollected,  LABAST5_R8);
					RVCTFieldsMap.put(LABAST14_R8_DateReported,  LABAST14_R8);
					RVCTFieldsMap.put(LABAST3_R8_CDT_SpecimenSource,  LABAST3_R8_CDT);
					RVCTFieldsMap.put(LABAST8_R8_CD_Result,  LABAST8_R8_CD);
					RVCTFieldsMap.put(LABAST7_R8_CDT_TestMethodOptional,  LABAST7_R8_CDT);

					RVCTFieldsMap.put(LABAST6_R9_CDT_DrugName,  LABAST6_R9_CDT);
					RVCTFieldsMap.put(LABAST5_R9_DateCollected,  LABAST5_R9);
					RVCTFieldsMap.put(LABAST14_R9_DateReported,  LABAST14_R9);
					RVCTFieldsMap.put(LABAST3_R9_CDT_SpecimenSource,  LABAST3_R9_CDT);
					RVCTFieldsMap.put(LABAST8_R9_CD_Result,  LABAST8_R9_CD);
					RVCTFieldsMap.put(LABAST7_R9_CDT_TestMethodOptional,  LABAST7_R9_CDT);

					RVCTFieldsMap.put(LABAST6_R10_CDT_DrugName,  LABAST6_R10_CDT);
					RVCTFieldsMap.put(LABAST5_R10_DateCollected,  LABAST5_R10);
					RVCTFieldsMap.put(LABAST14_R10_DateReported,  LABAST14_R10);
					RVCTFieldsMap.put(LABAST3_R10_CDT_SpecimenSource,  LABAST3_R10_CDT);
					RVCTFieldsMap.put(LABAST8_R10_CD_Result,  LABAST8_R10_CD);
					RVCTFieldsMap.put(LABAST7_R10_CDT_TestMethodOptional,  LABAST7_R10_CDT);

					RVCTFieldsMap.put(LABAST6_R11_CDT_DrugName,  LABAST6_R11_CDT);
					RVCTFieldsMap.put(LABAST5_R11_DateCollected,  LABAST5_R11);
					RVCTFieldsMap.put(LABAST14_R11_DateReported,  LABAST14_R11);
					RVCTFieldsMap.put(LABAST3_R11_CDT_SpecimenSource,  LABAST3_R11_CDT);
					RVCTFieldsMap.put(LABAST8_R11_CD_Result,  LABAST8_R11_CD);
					RVCTFieldsMap.put(LABAST7_R11_CDT_TestMethodOptional,  LABAST7_R11_CDT);

					RVCTFieldsMap.put(LABAST6_R12_CDT_DrugName,  LABAST6_R12_CDT);
					RVCTFieldsMap.put(LABAST5_R12_DateCollected,  LABAST5_R12);
					RVCTFieldsMap.put(LABAST14_R12_DateReported,  LABAST14_R12);
					RVCTFieldsMap.put(LABAST3_R12_CDT_SpecimenSource,  LABAST3_R12_CDT);
					RVCTFieldsMap.put(LABAST8_R12_CD_Result,  LABAST8_R12_CD);
					RVCTFieldsMap.put(LABAST7_R12_CDT_TestMethodOptional,  LABAST7_R12_CDT);

					RVCTFieldsMap.put(LABAST6_R13_CDT_DrugName,  LABAST6_R13_CDT);
					RVCTFieldsMap.put(LABAST5_R13_DateCollected,  LABAST5_R13);
					RVCTFieldsMap.put(LABAST14_R13_DateReported,  LABAST14_R13);
					RVCTFieldsMap.put(LABAST3_R13_CDT_SpecimenSource,  LABAST3_R13_CDT);
					RVCTFieldsMap.put(LABAST8_R13_CD_Result,  LABAST8_R13_CD);
					RVCTFieldsMap.put(LABAST7_R13_CDT_TestMethodOptional,  LABAST7_R13_CDT);

					RVCTFieldsMap.put(LABAST6_R14_CDT_DrugName,  LABAST6_R14_CDT);
					RVCTFieldsMap.put(LABAST5_R14_DateCollected,  LABAST5_R14);
					RVCTFieldsMap.put(LABAST14_R14_DateReported,  LABAST14_R14);
					RVCTFieldsMap.put(LABAST3_R14_CDT_SpecimenSource,  LABAST3_R14_CDT);
					RVCTFieldsMap.put(LABAST8_R14_CD_Result,  LABAST8_R14_CD);
					RVCTFieldsMap.put(LABAST7_R14_CDT_TestMethodOptional,  LABAST7_R14_CDT);


					}
					
					
					
					
					public static void initializeGenotypicPhenotypic_part2(){
						
						/****************************************************** Genotyping And Drug Suscetibility Testing (Phenotypic) ******************************************************/
						
						RVCTFieldsMap.put(LABAST6_R15_CDT_DrugName,  LABAST6_R15_CDT);
						RVCTFieldsMap.put(LABAST5_R15_DateCollected,  LABAST5_R15);
						RVCTFieldsMap.put(LABAST14_R15_DateReported,  LABAST14_R15);
						RVCTFieldsMap.put(LABAST3_R15_CDT_SpecimenSource,  LABAST3_R15_CDT);
						RVCTFieldsMap.put(LABAST8_R15_CD_Result,  LABAST8_R15_CD);
						RVCTFieldsMap.put(LABAST7_R15_CDT_TestMethodOptional,  LABAST7_R15_CDT);

						RVCTFieldsMap.put(LABAST6_R16_CDT_DrugName,  LABAST6_R16_CDT);
						RVCTFieldsMap.put(LABAST5_R16_DateCollected,  LABAST5_R16);
						RVCTFieldsMap.put(LABAST14_R16_DateReported,  LABAST14_R16);
						RVCTFieldsMap.put(LABAST3_R16_CDT_SpecimenSource,  LABAST3_R16_CDT);
						RVCTFieldsMap.put(LABAST8_R16_CD_Result,  LABAST8_R16_CD);
						RVCTFieldsMap.put(LABAST7_R16_CDT_TestMethodOptional,  LABAST7_R16_CDT);

						RVCTFieldsMap.put(LABAST6_R17_CDT_DrugName,  LABAST6_R17_CDT);
						RVCTFieldsMap.put(LABAST5_R17_DateCollected,  LABAST5_R17);
						RVCTFieldsMap.put(LABAST14_R17_DateReported,  LABAST14_R17);
						RVCTFieldsMap.put(LABAST3_R17_CDT_SpecimenSource,  LABAST3_R17_CDT);
						RVCTFieldsMap.put(LABAST8_R17_CD_Result,  LABAST8_R17_CD);
						RVCTFieldsMap.put(LABAST7_R17_CDT_TestMethodOptional,  LABAST7_R17_CDT);

						RVCTFieldsMap.put(LABAST6_R18_CDT_DrugName,  LABAST6_R18_CDT);
						RVCTFieldsMap.put(LABAST5_R18_DateCollected,  LABAST5_R18);
						RVCTFieldsMap.put(LABAST14_R18_DateReported,  LABAST14_R18);
						RVCTFieldsMap.put(LABAST3_R18_CDT_SpecimenSource,  LABAST3_R18_CDT);
						RVCTFieldsMap.put(LABAST8_R18_CD_Result,  LABAST8_R18_CD);
						RVCTFieldsMap.put(LABAST7_R18_CDT_TestMethodOptional,  LABAST7_R18_CDT);

						RVCTFieldsMap.put(LABAST6_R19_CDT_DrugName,  LABAST6_R19_CDT);
						RVCTFieldsMap.put(LABAST5_R19_DateCollected,  LABAST5_R19);
						RVCTFieldsMap.put(LABAST14_R19_DateReported,  LABAST14_R19);
						RVCTFieldsMap.put(LABAST3_R19_CDT_SpecimenSource,  LABAST3_R19_CDT);
						RVCTFieldsMap.put(LABAST8_R19_CD_Result,  LABAST8_R19_CD);
						RVCTFieldsMap.put(LABAST7_R19_CDT_TestMethodOptional,  LABAST7_R19_CDT);

						RVCTFieldsMap.put(LABAST6_R20_CDT_DrugName,  LABAST6_R20_CDT);
						RVCTFieldsMap.put(LABAST5_R20_DateCollected,  LABAST5_R20);
						RVCTFieldsMap.put(LABAST14_R20_DateReported,  LABAST14_R20);
						RVCTFieldsMap.put(LABAST3_R20_CDT_SpecimenSource,  LABAST3_R20_CDT);
						RVCTFieldsMap.put(LABAST8_R20_CD_Result,  LABAST8_R20_CD);
						RVCTFieldsMap.put(LABAST7_R20_CDT_TestMethodOptional,  LABAST7_R20_CDT);

						RVCTFieldsMap.put(LABAST6_R21_CDT_DrugName,  LABAST6_R21_CDT);
						RVCTFieldsMap.put(LABAST5_R21_DateCollected,  LABAST5_R21);
						RVCTFieldsMap.put(LABAST14_R21_DateReported,  LABAST14_R21);
						RVCTFieldsMap.put(LABAST3_R21_CDT_SpecimenSource,  LABAST3_R21_CDT);
						RVCTFieldsMap.put(LABAST8_R21_CD_Result,  LABAST8_R21_CD);
						RVCTFieldsMap.put(LABAST7_R21_CDT_TestMethodOptional,  LABAST7_R21_CDT);

						RVCTFieldsMap.put(LABAST6_R22_CDT_DrugName,  LABAST6_R22_CDT);
						RVCTFieldsMap.put(LABAST5_R22_DateCollected,  LABAST5_R22);
						RVCTFieldsMap.put(LABAST14_R22_DateReported,  LABAST14_R22);
						RVCTFieldsMap.put(LABAST3_R22_CDT_SpecimenSource,  LABAST3_R22_CDT);
						RVCTFieldsMap.put(LABAST8_R22_CD_Result,  LABAST8_R22_CD);
						RVCTFieldsMap.put(LABAST7_R22_CDT_TestMethodOptional,  LABAST7_R22_CDT);

						RVCTFieldsMap.put(LABAST6_R23_CDT_DrugName,  LABAST6_R23_CDT);
						RVCTFieldsMap.put(LABAST5_R23_DateCollected,  LABAST5_R23);
						RVCTFieldsMap.put(LABAST14_R23_DateReported,  LABAST14_R23);
						RVCTFieldsMap.put(LABAST3_R23_CDT_SpecimenSource,  LABAST3_R23_CDT);
						RVCTFieldsMap.put(LABAST8_R23_CD_Result,  LABAST8_R23_CD);
						RVCTFieldsMap.put(LABAST7_R23_CDT_TestMethodOptional,  LABAST7_R23_CDT);

						RVCTFieldsMap.put(LABAST6_R24_CDT_DrugName,  LABAST6_R24_CDT);
						RVCTFieldsMap.put(LABAST5_R24_DateCollected,  LABAST5_R24);
						RVCTFieldsMap.put(LABAST14_R24_DateReported,  LABAST14_R24);
						RVCTFieldsMap.put(LABAST3_R24_CDT_SpecimenSource,  LABAST3_R24_CDT);
						RVCTFieldsMap.put(LABAST8_R24_CD_Result,  LABAST8_R24_CD);
						RVCTFieldsMap.put(LABAST7_R24_CDT_TestMethodOptional,  LABAST7_R24_CDT);

						RVCTFieldsMap.put(LABAST6_R25_CDT_DrugName,  LABAST6_R25_CDT);
						RVCTFieldsMap.put(LABAST5_R25_DateCollected,  LABAST5_R25);
						RVCTFieldsMap.put(LABAST14_R25_DateReported,  LABAST14_R25);
						RVCTFieldsMap.put(LABAST3_R25_CDT_SpecimenSource,  LABAST3_R25_CDT);
						RVCTFieldsMap.put(LABAST8_R25_CD_Result,  LABAST8_R25_CD);
						RVCTFieldsMap.put(LABAST7_R25_CDT_TestMethodOptional,  LABAST7_R25_CDT);

						RVCTFieldsMap.put(LABAST6_R26_CDT_DrugName,  LABAST6_R26_CDT);
						RVCTFieldsMap.put(LABAST5_R26_DateCollected,  LABAST5_R26);
						RVCTFieldsMap.put(LABAST14_R26_DateReported,  LABAST14_R26);
						RVCTFieldsMap.put(LABAST3_R26_CDT_SpecimenSource,  LABAST3_R26_CDT);
						RVCTFieldsMap.put(LABAST8_R26_CD_Result,  LABAST8_R26_CD);
						RVCTFieldsMap.put(LABAST7_R26_CDT_TestMethodOptional,  LABAST7_R26_CDT);

						RVCTFieldsMap.put(LABAST6_R27_CDT_DrugName,  LABAST6_R27_CDT);
						RVCTFieldsMap.put(LABAST5_R27_DateCollected,  LABAST5_R27);
						RVCTFieldsMap.put(LABAST14_R27_DateReported,  LABAST14_R27);
						RVCTFieldsMap.put(LABAST3_R27_CDT_SpecimenSource,  LABAST3_R27_CDT);
						RVCTFieldsMap.put(LABAST8_R27_CD_Result,  LABAST8_R27_CD);
						RVCTFieldsMap.put(LABAST7_R27_CDT_TestMethodOptional,  LABAST7_R27_CDT);

						RVCTFieldsMap.put(LABAST6_R28_CDT_DrugName,  LABAST6_R28_CDT);
						RVCTFieldsMap.put(LABAST5_R28_DateCollected,  LABAST5_R28);
						RVCTFieldsMap.put(LABAST14_R28_DateReported,  LABAST14_R28);
						RVCTFieldsMap.put(LABAST3_R28_CDT_SpecimenSource,  LABAST3_R28_CDT);
						RVCTFieldsMap.put(LABAST8_R28_CD_Result,  LABAST8_R28_CD);
						RVCTFieldsMap.put(LABAST7_R28_CDT_TestMethodOptional,  LABAST7_R28_CDT);

						RVCTFieldsMap.put(LABAST6_R29_CDT_DrugName,  LABAST6_R29_CDT);
						RVCTFieldsMap.put(LABAST5_R29_DateCollected,  LABAST5_R29);
						RVCTFieldsMap.put(LABAST14_R29_DateReported,  LABAST14_R29);
						RVCTFieldsMap.put(LABAST3_R29_CDT_SpecimenSource,  LABAST3_R29_CDT);
						RVCTFieldsMap.put(LABAST8_R29_CD_Result,  LABAST8_R29_CD);
						RVCTFieldsMap.put(LABAST7_R29_CDT_TestMethodOptional,  LABAST7_R29_CDT);

						RVCTFieldsMap.put(LABAST6_R30_CDT_DrugName,  LABAST6_R30_CDT);
						RVCTFieldsMap.put(LABAST5_R30_DateCollected,  LABAST5_R30);
						RVCTFieldsMap.put(LABAST14_R30_DateReported,  LABAST14_R30);
						RVCTFieldsMap.put(LABAST3_R30_CDT_SpecimenSource,  LABAST3_R30_CDT);
						RVCTFieldsMap.put(LABAST8_R30_CD_Result,  LABAST8_R30_CD);
						RVCTFieldsMap.put(LABAST7_R30_CDT_TestMethodOptional,  LABAST7_R30_CDT);

						RVCTFieldsMap.put(LABAST6_R31_CDT_DrugName,  LABAST6_R31_CDT);
						RVCTFieldsMap.put(LABAST5_R31_DateCollected,  LABAST5_R31);
						RVCTFieldsMap.put(LABAST14_R31_DateReported,  LABAST14_R31);
						RVCTFieldsMap.put(LABAST3_R31_CDT_SpecimenSource,  LABAST3_R31_CDT);
						RVCTFieldsMap.put(LABAST8_R31_CD_Result,  LABAST8_R31_CD);
						RVCTFieldsMap.put(LABAST7_R31_CDT_TestMethodOptional,  LABAST7_R31_CDT);

						}
						
					
					
					public static void initializeGenotypicMolecular(){
						/****************************************************** Genotypic/Molecular Drug Susceptibility Testing (Molecular) ******************************************************/
					
					RVCTFieldsMap.put(INV1148_CD_WasGenOrMoleDrugSuscepTest,  INV1148_CD);
					RVCTFieldsMap.put(code48018_6_R1_CDT_GeneName,  code48018_6_R1_CDT);
					RVCTFieldsMap.put(LAB682_R1_DateCollected,  LAB682_R1);
					RVCTFieldsMap.put(LAB683_R1_DateReported,  LAB683_R1);
					RVCTFieldsMap.put(LAB684_R1_CDT_SpecimenSource,  LAB684_R1_CDT);
					RVCTFieldsMap.put(LAB685_R1_CD_Result,  LAB685_R1_CD);
					RVCTFieldsMap.put(LAB686_R1_NucleicAcidChange,  LAB686_R1);
					RVCTFieldsMap.put(LAB687_R1_AminoAcidChange,  LAB687_R1);
					RVCTFieldsMap.put(LAB688_R1_CD_Indel,  LAB688_R1_CD);
					RVCTFieldsMap.put(LAB689_R1_CD_TestType,  LAB689_R1_CD);
					RVCTFieldsMap.put(LAB689_R1_OTH_TestType,  LAB689_R1_OTH);
					
					RVCTFieldsMap.put(code48018_6_R2_CDT_GeneName,  code48018_6_R2_CDT);
					RVCTFieldsMap.put(LAB682_R2_DateCollected,  LAB682_R2);
					RVCTFieldsMap.put(LAB683_R2_DateReported,  LAB683_R2);
					RVCTFieldsMap.put(LAB684_R2_CDT_SpecimenSource,  LAB684_R2_CDT);
					RVCTFieldsMap.put(LAB685_R2_CD_Result,  LAB685_R2_CD);
					RVCTFieldsMap.put(LAB686_R2_NucleicAcidChange,  LAB686_R2);
					RVCTFieldsMap.put(LAB687_R2_AminoAcidChange,  LAB687_R2);
					RVCTFieldsMap.put(LAB688_R2_CD_Indel,  LAB688_R2_CD);
					RVCTFieldsMap.put(LAB689_R2_CD_TestType,  LAB689_R2_CD);
					RVCTFieldsMap.put(LAB689_R2_OTH_TestType,  LAB689_R2_OTH);
					
					RVCTFieldsMap.put(code48018_6_R3_CDT_GeneName,  code48018_6_R3_CDT);
					RVCTFieldsMap.put(LAB682_R3_DateCollected,  LAB682_R3);
					RVCTFieldsMap.put(LAB683_R3_DateReported,  LAB683_R3);
					RVCTFieldsMap.put(LAB684_R3_CDT_SpecimenSource,  LAB684_R3_CDT);
					RVCTFieldsMap.put(LAB685_R3_CD_Result,  LAB685_R3_CD);
					RVCTFieldsMap.put(LAB686_R3_NucleicAcidChange,  LAB686_R3);
					RVCTFieldsMap.put(LAB687_R3_AminoAcidChange,  LAB687_R3);
					RVCTFieldsMap.put(LAB688_R3_CD_Indel,  LAB688_R3_CD);
					RVCTFieldsMap.put(LAB689_R3_CD_TestType,  LAB689_R3_CD);
					RVCTFieldsMap.put(LAB689_R3_OTH_TestType,  LAB689_R3_OTH);
					
					RVCTFieldsMap.put(code48018_6_R4_CDT_GeneName,  code48018_6_R4_CDT);
					RVCTFieldsMap.put(LAB682_R4_DateCollected,  LAB682_R4);
					RVCTFieldsMap.put(LAB683_R4_DateReported,  LAB683_R4);
					RVCTFieldsMap.put(LAB684_R4_CDT_SpecimenSource,  LAB684_R4_CDT);
					RVCTFieldsMap.put(LAB685_R4_CD_Result,  LAB685_R4_CD);
					RVCTFieldsMap.put(LAB686_R4_NucleicAcidChange,  LAB686_R4);
					RVCTFieldsMap.put(LAB687_R4_AminoAcidChange,  LAB687_R4);
					RVCTFieldsMap.put(LAB688_R4_CD_Indel,  LAB688_R4_CD);
					RVCTFieldsMap.put(LAB689_R4_CD_TestType,  LAB689_R4_CD);
					RVCTFieldsMap.put(LAB689_R4_OTH_TestType,  LAB689_R4_OTH);

					RVCTFieldsMap.put(code48018_6_R5_CDT_GeneName,  code48018_6_R5_CDT);
					RVCTFieldsMap.put(LAB682_R5_DateCollected,  LAB682_R5);
					RVCTFieldsMap.put(LAB683_R5_DateReported,  LAB683_R5);
					RVCTFieldsMap.put(LAB684_R5_CDT_SpecimenSource,  LAB684_R5_CDT);
					RVCTFieldsMap.put(LAB685_R5_CD_Result,  LAB685_R5_CD);
					RVCTFieldsMap.put(LAB686_R5_NucleicAcidChange,  LAB686_R5);
					RVCTFieldsMap.put(LAB687_R5_AminoAcidChange,  LAB687_R5);
					RVCTFieldsMap.put(LAB688_R5_CD_Indel,  LAB688_R5_CD);
					RVCTFieldsMap.put(LAB689_R5_CD_TestType,  LAB689_R5_CD);
					RVCTFieldsMap.put(LAB689_R5_OTH_TestType,  LAB689_R5_OTH);

					RVCTFieldsMap.put(code48018_6_R6_CDT_GeneName,  code48018_6_R6_CDT);
					RVCTFieldsMap.put(LAB682_R6_DateCollected,  LAB682_R6);
					RVCTFieldsMap.put(LAB683_R6_DateReported,  LAB683_R6);
					RVCTFieldsMap.put(LAB684_R6_CDT_SpecimenSource,  LAB684_R6_CDT);
					RVCTFieldsMap.put(LAB685_R6_CD_Result,  LAB685_R6_CD);
					RVCTFieldsMap.put(LAB686_R6_NucleicAcidChange,  LAB686_R6);
					RVCTFieldsMap.put(LAB687_R6_AminoAcidChange,  LAB687_R6);
					RVCTFieldsMap.put(LAB688_R6_CD_Indel,  LAB688_R6_CD);
					RVCTFieldsMap.put(LAB689_R6_CD_TestType,  LAB689_R6_CD);
					RVCTFieldsMap.put(LAB689_R6_OTH_TestType,  LAB689_R6_OTH);

					RVCTFieldsMap.put(code48018_6_R7_CDT_GeneName,  code48018_6_R7_CDT);
					RVCTFieldsMap.put(LAB682_R7_DateCollected,  LAB682_R7);
					RVCTFieldsMap.put(LAB683_R7_DateReported,  LAB683_R7);
					RVCTFieldsMap.put(LAB684_R7_CDT_SpecimenSource,  LAB684_R7_CDT);
					RVCTFieldsMap.put(LAB685_R7_CD_Result,  LAB685_R7_CD);
					RVCTFieldsMap.put(LAB686_R7_NucleicAcidChange,  LAB686_R7);
					RVCTFieldsMap.put(LAB687_R7_AminoAcidChange,  LAB687_R7);
					RVCTFieldsMap.put(LAB688_R7_CD_Indel,  LAB688_R7_CD);
					RVCTFieldsMap.put(LAB689_R7_CD_TestType,  LAB689_R7_CD);
					RVCTFieldsMap.put(LAB689_R7_OTH_TestType,  LAB689_R7_OTH);

					RVCTFieldsMap.put(code48018_6_R8_CDT_GeneName,  code48018_6_R8_CDT);
					RVCTFieldsMap.put(LAB682_R8_DateCollected,  LAB682_R8);
					RVCTFieldsMap.put(LAB683_R8_DateReported,  LAB683_R8);
					RVCTFieldsMap.put(LAB684_R8_CDT_SpecimenSource,  LAB684_R8_CDT);
					RVCTFieldsMap.put(LAB685_R8_CD_Result,  LAB685_R8_CD);
					RVCTFieldsMap.put(LAB686_R8_NucleicAcidChange,  LAB686_R8);
					RVCTFieldsMap.put(LAB687_R8_AminoAcidChange,  LAB687_R8);
					RVCTFieldsMap.put(LAB688_R8_CD_Indel,  LAB688_R8_CD);
					RVCTFieldsMap.put(LAB689_R8_CD_TestType,  LAB689_R8_CD);
					RVCTFieldsMap.put(LAB689_R8_OTH_TestType,  LAB689_R8_OTH);
					
					
					
					RVCTFieldsMap.put(INV1275_CD_TreatedAsMDR,  INV1275_CD);
					}
					
					public static void initializeCaseOutcome(){
					
					/****************************************************** Case Outcome ******************************************************/
					
					RVCTFieldsMap.put(INV1149_CD_SputumCultureConversionDocumented,  INV1149_CD);
					RVCTFieldsMap.put(INV1150_dateSpecimenCollected,  INV1150);
					RVCTFieldsMap.put(INV1151_CD_reasonForNotDocumenting,  INV1151_CD);
					RVCTFieldsMap.put(INV1151Oth_OtherSpecify,  INV1151Oth);

					RVCTFieldsMap.put(TB279_CD_MovedDuringTherapy,  TB279_CD);
					RVCTFieldsMap.put(INV1152_CD_IfYesMovedToWhere,  INV1152_CD);
					RVCTFieldsMap.put(INV1152_OS_CD_IfYesMovedToWhere,  INV1152_OS_CD);
					RVCTFieldsMap.put(INV1152_OUS_CD_IfYesMovedToWhere,  INV1152_OUS_CD);

					RVCTFieldsMap.put(INV1153_CDT_IfOutOfStateSpecifyDestination,  INV1153_CDT);
					RVCTFieldsMap.put(INV1155_CD_TransnationalReferralMade,  INV1155_CD);
					RVCTFieldsMap.put(INV1154_CDT_IfOutOfCountrySpecifyDestination,  INV1154_CDT);
					RVCTFieldsMap.put(code413947000_DateTherapyStopped,  code413947000);
					RVCTFieldsMap.put(INV1140_CD_ReasonTherapyStoppedOrNeverStarted,  INV1140_CD);
					RVCTFieldsMap.put(INV1140_OTH_ReasonTherapyStoppedOrNeverStarted,  INV1140_OTH);	

					RVCTFieldsMap.put(INV1141_CD_ReasonTbTherapyExtended,  INV1141_CD);
					RVCTFieldsMap.put(INV1141_I_CD_ReasonTbTherapyExtended,  INV1141_I_CD);
					RVCTFieldsMap.put(INV1141_A_CD_ReasonTbTherapyExtended,  INV1141_A_CD);
					RVCTFieldsMap.put(INV1141_C_CD_ReasonTbTherapyExtended,  INV1141_C_CD);
					RVCTFieldsMap.put(INV1141_F_CD_ReasonTbTherapyExtended,  INV1141_F_CD);
					RVCTFieldsMap.put(INV1141_N_CD_ReasonTbTherapyExtended,  INV1141_N_CD);
					RVCTFieldsMap.put(INV1141_U_CD_ReasonTbTherapyExtended,  INV1141_U_CD);
					RVCTFieldsMap.put(INV1141_O_CD_ReasonTbTherapyExtended,  INV1141_O_CD);
					RVCTFieldsMap.put(INV1141_OTH_ReasonTbTherapyExtended,  INV1141_OTH);
				
					RVCTFieldsMap.put(code55753_8_CD_TreatmentAdministration,  code55753_8_CD);
					RVCTFieldsMap.put(code55753_8_D_CD_TreatmentAdministration,  code55753_8_D_CD);
					RVCTFieldsMap.put(code55753_8_E_CD_TreatmentAdministration,  code55753_8_E_CD);
					RVCTFieldsMap.put(code55753_8_S_CD_TreatmentAdministration,  code55753_8_S_CD);
					
					RVCTFieldsMap.put(DEM127_CD_DidThePatientDied,  DEM127_CD);
					RVCTFieldsMap.put(DEM128_DateOfDeath,  DEM128);
					RVCTFieldsMap.put(INV145_CD_DidTBOrComplicationsOfTBTreatmentContributeToDeath,  INV145_CD);
					RVCTFieldsMap.put(INV167_InvestigationComments,  INV167);
					}
					
					public static void initializeMDRTBSupplemental(){
					/****************************************************** MDR TB Supplemental Surveillance Form ******************************************************/
					
					RVCTFieldsMap.put(INV1156_CD_HistoryTreatmentBeforeCurrentEpisode,  INV1156_CD);
					RVCTFieldsMap.put(INV1157_DateMDRTBTherapyStartedForCurrentEpisode,  INV1157);
					RVCTFieldsMap.put(INV1158_R1_CDT_DrugName,  INV1158_R1_CDT);
					RVCTFieldsMap.put(INV1159_R1_CD_LengthTimAdministered,  INV1159_R1_CD);
					RVCTFieldsMap.put(INV1158_R2_CDT_DrugName,  INV1158_R2_CDT);
					RVCTFieldsMap.put(INV1159_R2_CD_LengthTimAdministered,  INV1159_R2_CD);
					RVCTFieldsMap.put(INV1158_R3_CDT_DrugName,  INV1158_R3_CDT);
					RVCTFieldsMap.put(INV1159_R3_CD_LengthTimAdministered,  INV1159_R3_CD);
					RVCTFieldsMap.put(INV1158_R4_CDT_DrugName,  INV1158_R4_CDT);
					RVCTFieldsMap.put(INV1159_R4_CD_LengthTimAdministered,  INV1159_R4_CD);
					RVCTFieldsMap.put(INV1158_R5_CDT_DrugName,  INV1158_R5_CDT);
					RVCTFieldsMap.put(INV1159_R5_CD_LengthTimAdministered,  INV1159_R5_CD);
					RVCTFieldsMap.put(INV1158_R6_CDT_DrugName,  INV1158_R6_CDT);
					RVCTFieldsMap.put(INV1159_R6_CD_LengthTimAdministered,  INV1159_R6_CD);
					RVCTFieldsMap.put(INV1158_R7_CDT_DrugName,  INV1158_R7_CDT);
					RVCTFieldsMap.put(INV1159_R7_CD_LengthTimAdministered,  INV1159_R7_CD);
					RVCTFieldsMap.put(INV1158_R8_CDT_DrugName,  INV1158_R8_CDT);
					RVCTFieldsMap.put(INV1159_R8_CD_LengthTimAdministered,  INV1159_R8_CD);
					RVCTFieldsMap.put(INV1158_R9_CDT_DrugName,  INV1158_R9_CDT);
					RVCTFieldsMap.put(INV1159_R9_CD_LengthTimAdministered,  INV1159_R9_CD);
					RVCTFieldsMap.put(INV1158_R10_CDT_DrugName,  INV1158_R10_CDT);
					RVCTFieldsMap.put(INV1159_R10_CD_LengthTimAdministered,  INV1159_R10_CD);
					
					RVCTFieldsMap.put(INV1158_R11_CDT_DrugName,  INV1158_R11_CDT);
					RVCTFieldsMap.put(INV1159_R11_CD_LengthTimAdministered,  INV1159_R11_CD);
					RVCTFieldsMap.put(INV1158_R12_CDT_DrugName,  INV1158_R12_CDT);
					RVCTFieldsMap.put(INV1159_R12_CD_LengthTimAdministered,  INV1159_R12_CD);
					RVCTFieldsMap.put(INV1158_R13_CDT_DrugName,  INV1158_R13_CDT);
					RVCTFieldsMap.put(INV1159_R13_CD_LengthTimAdministered,  INV1159_R13_CD);
					RVCTFieldsMap.put(INV1158_R14_CDT_DrugName,  INV1158_R14_CDT);
					RVCTFieldsMap.put(INV1159_R14_CD_LengthTimAdministered,  INV1159_R14_CD);
					RVCTFieldsMap.put(INV1158_R15_CDT_DrugName,  INV1158_R15_CDT);
					RVCTFieldsMap.put(INV1159_R15_CD_LengthTimAdministered,  INV1159_R15_CD);
					RVCTFieldsMap.put(INV1158_R16_CDT_DrugName,  INV1158_R16_CDT);
					RVCTFieldsMap.put(INV1159_R16_CD_LengthTimAdministered,  INV1159_R16_CD);
					RVCTFieldsMap.put(INV1158_R17_CDT_DrugName,  INV1158_R17_CDT);
					RVCTFieldsMap.put(INV1159_R17_CD_LengthTimAdministered,  INV1159_R17_CD);
					RVCTFieldsMap.put(INV1158_R18_CDT_DrugName,  INV1158_R18_CDT);
					RVCTFieldsMap.put(INV1159_R18_CD_LengthTimAdministered,  INV1159_R18_CD);
					RVCTFieldsMap.put(INV1158_R19_CDT_DrugName,  INV1158_R19_CDT);
					RVCTFieldsMap.put(INV1159_R19_CD_LengthTimAdministered,  INV1159_R19_CD);
					RVCTFieldsMap.put(INV1158_R20_CDT_DrugName,  INV1158_R20_CDT);
					RVCTFieldsMap.put(INV1159_R20_CD_LengthTimAdministered,  INV1159_R20_CD);
					RVCTFieldsMap.put(INV1158_R21_CDT_DrugName,  INV1158_R21_CDT);
					RVCTFieldsMap.put(INV1159_R21_CD_LengthTimAdministered,  INV1159_R21_CD);
					RVCTFieldsMap.put(INV1158_R22_CDT_DrugName,  INV1158_R22_CDT);
					RVCTFieldsMap.put(INV1159_R22_CD_LengthTimAdministered,  INV1159_R22_CD);
					RVCTFieldsMap.put(INV1158_R23_CDT_DrugName,  INV1158_R23_CDT);
					RVCTFieldsMap.put(INV1159_R23_CD_LengthTimAdministered,  INV1159_R23_CD);
					RVCTFieldsMap.put(INV1158_R24_CDT_DrugName,  INV1158_R24_CDT);
					RVCTFieldsMap.put(INV1159_R24_CD_LengthTimAdministered,  INV1159_R24_CD);
					RVCTFieldsMap.put(INV1158_R25_CDT_DrugName,  INV1158_R25_CDT);
					RVCTFieldsMap.put(INV1159_R25_CD_LengthTimAdministered,  INV1159_R25_CD);
					RVCTFieldsMap.put(INV1158_R26_CDT_DrugName,  INV1158_R26_CDT);
					RVCTFieldsMap.put(INV1159_R26_CD_LengthTimAdministered,  INV1159_R26_CD);
					RVCTFieldsMap.put(INV1158_R27_CDT_DrugName,  INV1158_R27_CDT);
					RVCTFieldsMap.put(INV1159_R27_CD_LengthTimAdministered,  INV1159_R27_CD);
					RVCTFieldsMap.put(INV1158_R28_CDT_DrugName,  INV1158_R28_CDT);
					RVCTFieldsMap.put(INV1159_R28_CD_LengthTimAdministered,  INV1159_R28_CD);
					RVCTFieldsMap.put(INV1158_R29_CDT_DrugName,  INV1158_R29_CDT);
					RVCTFieldsMap.put(INV1159_R29_CD_LengthTimAdministered,  INV1159_R29_CD);
					RVCTFieldsMap.put(INV1158_R30_CDT_DrugName,  INV1158_R30_CDT);
					RVCTFieldsMap.put(INV1159_R30_CD_LengthTimAdministered,  INV1159_R30_CD);
					
					
					RVCTFieldsMap.put(INV1160_DateInjectableMedicationWasStopped,  INV1160);
					RVCTFieldsMap.put(INV1161_CD_WasSurgeryPerformedToTreatMDRTB,  INV1161_CD);
					RVCTFieldsMap.put(INV1162_DateOfSurgery,  INV1162);
					RVCTFieldsMap.put(code42563_7_CD_R1_CDT_SideEffect,  code42563_7_CD_R1_CDT);
					RVCTFieldsMap.put(INV1164_R1_CD_SideEffectExperienced,  INV1164_R1_CD);
					RVCTFieldsMap.put(INV1163_R1_CD_When,  INV1163_R1_CD);
					RVCTFieldsMap.put(code42563_7_CD_R2_CDT_SideEffect,  code42563_7_CD_R2_CDT);
					RVCTFieldsMap.put(INV1164_R2_CD_SideEffectExperienced,  INV1164_R2_CD);
					RVCTFieldsMap.put(INV1163_R2_CD_When,  INV1163_R2_CD);
					RVCTFieldsMap.put(code42563_7_CD_R3_CDT_SideEffect,  code42563_7_CD_R3_CDT);
					RVCTFieldsMap.put(INV1164_R3_CD_SideEffectExperienced,  INV1164_R3_CD);
					RVCTFieldsMap.put(INV1163_R3_CD_When,  INV1163_R3_CD);
					RVCTFieldsMap.put(code42563_7_CD_R4_CDT_SideEffect,  code42563_7_CD_R4_CDT);
					RVCTFieldsMap.put(INV1164_R4_CD_SideEffectExperienced,  INV1164_R4_CD);
					RVCTFieldsMap.put(INV1163_R4_CD_When,  INV1163_R4_CD);
					RVCTFieldsMap.put(code42563_7_CD_R5_CDT_SideEffect,  code42563_7_CD_R5_CDT);
					RVCTFieldsMap.put(INV1164_R5_CD_SideEffectExperienced,  INV1164_R5_CD);
					RVCTFieldsMap.put(INV1163_R5_CD_When,  INV1163_R5_CD);
					RVCTFieldsMap.put(code42563_7_CD_R6_CDT_SideEffect,  code42563_7_CD_R6_CDT);
					RVCTFieldsMap.put(INV1164_R6_CD_SideEffectExperienced,  INV1164_R6_CD);
					RVCTFieldsMap.put(INV1163_R6_CD_When,  INV1163_R6_CD);
					RVCTFieldsMap.put(code42563_7_CD_R7_CDT_SideEffect,  code42563_7_CD_R7_CDT);
					RVCTFieldsMap.put(INV1164_R7_CD_SideEffectExperienced,  INV1164_R7_CD);
					RVCTFieldsMap.put(INV1163_R7_CD_When,  INV1163_R7_CD);
					RVCTFieldsMap.put(code42563_7_CD_R8_CDT_SideEffect,  code42563_7_CD_R8_CDT);
					RVCTFieldsMap.put(INV1164_R8_CD_SideEffectExperienced,  INV1164_R8_CD);
					RVCTFieldsMap.put(INV1163_R8_CD_When,  INV1163_R8_CD);
					
					RVCTFieldsMap.put(code42563_7_CD_R9_CDT_SideEffect,  code42563_7_CD_R9_CDT);
					RVCTFieldsMap.put(INV1164_R9_CD_SideEffectExperienced,  INV1164_R9_CD);
					RVCTFieldsMap.put(INV1163_R9_CD_When,  INV1163_R9_CD);
					
					RVCTFieldsMap.put(code42563_7_CD_R10_CDT_SideEffect,  code42563_7_CD_R10_CDT);
					RVCTFieldsMap.put(INV1164_R10_CD_SideEffectExperienced,  INV1164_R10_CD);
					RVCTFieldsMap.put(INV1163_R10_CD_When,  INV1163_R10_CD);
					
					RVCTFieldsMap.put(code42563_7_CD_R11_CDT_SideEffect,  code42563_7_CD_R11_CDT);
					RVCTFieldsMap.put(INV1164_R11_CD_SideEffectExperienced,  INV1164_R11_CD);
					RVCTFieldsMap.put(INV1163_R11_CD_When,  INV1163_R11_CD);
					
					RVCTFieldsMap.put(code42563_7_CD_R12_CDT_SideEffect,  code42563_7_CD_R12_CDT);
					RVCTFieldsMap.put(INV1164_R12_CD_SideEffectExperienced,  INV1164_R12_CD);
					RVCTFieldsMap.put(INV1163_R12_CD_When,  INV1163_R12_CD);
					
					RVCTFieldsMap.put(code42563_7_CD_R13_CDT_SideEffect,  code42563_7_CD_R13_CDT);
					RVCTFieldsMap.put(INV1164_R13_CD_SideEffectExperienced,  INV1164_R13_CD);
					RVCTFieldsMap.put(INV1163_R13_CD_When,  INV1163_R13_CD);
					
					RVCTFieldsMap.put(code42563_7_CD_R14_CDT_SideEffect,  code42563_7_CD_R14_CDT);
					RVCTFieldsMap.put(INV1164_R14_CD_SideEffectExperienced,  INV1164_R14_CD);
					RVCTFieldsMap.put(INV1163_R14_CD_When,  INV1163_R14_CD);
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
				logger.error("CDCRVCTForm.printForm Exception thrown, "+ e1);
				throw new NEDSSAppException("CDCRVCTForm.printForm Exception thrown, ", e1);
				
			}
		} finally {		
				try {
					if(pdfDocument!=null)
						pdfDocument.close();
				} catch (IOException e2) {
					logger.error(e2);
					logger.error("CDCRVCTForm.printForm Exception thrown, Error while closing FileInputStream for Print, "+ e2);
					throw new NEDSSAppException("CDCRVCTForm.printForm Exception thrown, Error while closing FileInputStream for Print, ", e2);
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
				logger.error("CDCRVCTForm.changeFormat Exception thrown, "+ e);
				throw new NEDSSAppException("CDCRVCTForm.changeFormat Exception thrown, ", e);
			}
	}*/
	
	
	
	
	private static void fillPDForm(PDAcroForm acroForm, String invFormCd,HttpServletRequest req) throws NEDSSAppException {
		//    Map<String, String> originalContactRecordValues = new  HashMap<String,String>();
			String currentKey = "";
			//String currentFormValue = "";
			try {
				answerMap = pageForm.getPageClientVO().getAnswerMap(); 
		
				int i = 1; //what is this used for??
				
				initializeRVCTFormValues();
				
				getMappedValues(RVCTFieldsMap, invFormCd, req);

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
				logger.error("CDCRVCTForm.fillPDForm Exception thrown, "+ e);
				throw new NEDSSAppException("CDCRVCTForm.fillPDForm Exception thrown, ", e);
			}
		}//end	

}
