package gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

import gov.cdc.nedss.act.ctcontact.dt.CTContactDT;
import gov.cdc.nedss.act.ctcontact.dt.CTContactSummaryDT;
import gov.cdc.nedss.act.interview.dt.InterviewSummaryDT;
import gov.cdc.nedss.entity.person.dt.PersonDT;
import gov.cdc.nedss.entity.person.dt.PersonNameDT;
import gov.cdc.nedss.entity.person.dt.PersonRaceDT;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.entity.place.vo.PlaceVO;
import gov.cdc.nedss.exception.NEDSSAppConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.act.PageActProxyVO;
import gov.cdc.nedss.pam.act.NbsCaseAnswerDT;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.CoinfectionSummaryVO;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.vo.CTContactProxyVO;
import gov.cdc.nedss.systemservice.ejb.questionmapejb.dt.NbsQuestionMetadata;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.util.StringUtils;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageLoadUtil;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.common.PageManagementCommonActionUtil;
import gov.cdc.nedss.webapp.nbs.action.place.PlaceUtil;
import gov.cdc.nedss.webapp.nbs.form.page.PageForm;

/**
 * PDF generating/populating class for "Interview Record Form"
 * @author Pradeep Kumar Sharma
 *

 * @updatedby : Pradeep Sharma
 * Description: This code has been updated to fix data loss issue
 * Page Builder: Data Loss on Page Builder Investigations details in https://nbscentral.sramanaged.com//redmine/issues/12201
 * @version : 5.4.4 
*/
public class InterviewRecordForm extends CommonPDFPrintForm{
	/**
	 * Constants
	 */

	private	static String SEPERATOR_ONE = "_1";
	private	static String SEPERATOR_TWO = "_2";
	private	static String NBS135SourceOrSpread = "NBS135";
	private static String CON141RelationshipThisPatientorOtherInfected = "CON141";
	
	
	//Page Four of Interview Form 
	private	static String ContactLastNameDEM102_CT_R 	= "DEM102_CT_R";//DEM102_CT_R1_1
	private	static String ContactFirstNameDEM104_CT_R 	= "DEM104_CT_R"; //DEM104_CT_R1_1
	private	static String ContactAliasNameDEM250_CT_R 	= "DEM250_CT_R"; //DEM250_CT_R1_1
	private	static String INVContactJurisdiction107_CT_R = "INV107_CT_R"; //DEM102_CT_R1_1, DEM102_CT_R2_1
	private	static String ContactReferralBasisCON144_CR_R = "CON144_CR_R"; //CON144_CR_R1_1_CD add CD to end
	private	static String ContactFirstExposureDateFirstExp_CR_R = "FirstExp_CR_R"; //FirstExp_CR_R1_1
	private	static String ContactFirstExposureFrequencyFreqExp_CR_R = "FreqExp_CR_R"; //FreqExp_CR_R1_1
	private	static String ContactLastExposureDateLastExp_CR_R = "LastExp_CR_R"; //LastExp_CR_R1_1
	private	static String ContactGenderDEM113_CT_R = "DEM113_CT_R"; //DEM113_CT_R1_1_CD
	private	static String ContactPreferredGenderNBS274_CT_R = "NBS274_CT_R"; //NBS274_CT_R1_1_CDT  only T
	private	static String ContactGenderUnknownReasonNBS272_CT_R = "NBS272_CT_R"; //NBS272_CT_R1_1_CDT  only R
	private	static String ContactPregnantINV178_CT_R = "INV178_CT_R"; //INV178_CT_R1_1_CD
	private	static String ContactOpSpouseNBS125_CR_R = "NBS125_CR_R"; //NBS125_CR_R1_1_CDT
	
	private	static String ContactMarginalCTName_CR_R 	= "MarginalCTName_CR_R";
	private	static String ContactMarginalCTSex_CR_R  	= "MarginalCTSex_CR_R"; //MarginalCTSex_CR_R1_1_CD
	private	static String ContactMarginalCTAge_CR_R		= "MarginalCTAge_CR_R"; //MarginalCTAge_CR_R1_1
	private	static String ContactMarginalCTRace_CR_R 	= "MarginalCTRace_CR_R"; //MarginalCTRace_CR_R1_1_CDT
	private	static String ContactMarginalCTHeight_CR_R 	= "MarginalCTHeight_CR_R"; // NBS155 - MarginalCTHeight_CR_R1_1
	private	static String ContactMarginalCTWeight_CR_R 	= "MarginalCTWeight_CR_R"; //NBS156 - MarginalCTWeight_CR_R1_1
	private	static String ContactMarginalCTHair_CR_R 	= "MarginalCTHair_CR_R"; //NBS157 - MarginalCTHair_CR_R1_1
	private	static String ContactMarginalCTExp_CR_R 	= "MarginalCTExp_CR_R"; //MarginalCTExp_CR_R1_1
	private	static String ContactMarginalCTLocInfo_CR_R = "MarginalCTLocInfo_CR_R"; //MarginalCTLocInfo_CR_R1_1
	
	private	static String ContactInterviewDateIXS101_CR_IR_R 	= "IXS101_CR_IR_R"; //IXS101_CR_IR_R2_1	
	private	static String ContactInitialDateINV147_CT_R 		= "INV147_CT_R"; //INV147_CT_R2_1
	private	static String ContactInterviewerDISIXS102_CR_IR_R	= "IXS102_CR_IR_R"; //IXS102_CR_IR_R2_1_CD
	private	static String ContactInterviewTypeIXS105_CR_IR_R 	= "IXS105_CR_IR_R"; //IXS105_CR_IR_R2_1_CDT
	private	static String ContactNotificationMethodNBS177_CT_R  = "NBS177_CT_R"; //NBS177_CT_R2_1_CD
	private	static String ContactFieldRecordNbrNBS160_CT_R		= "NBS160_CT_R"; //NBS160_CT_R2_1
	private	static String ContactDispoNBS173_CT_R				= "NBS173_CT_R"; //NBS173_CT_R2_1_CD
	private	static String ContactDispoDateNBS174_CT_R 			= "NBS174_CT_R"; //NBS174_CT_R2_1
	private	static String ContactConditionNBS136_CT_R		 	= "NBS136_CT_R"; //NBS136_CT_R2_1_CD - use Inv condition
	private	static String ContactDispoByNBS175_CT_R				= "NBS175_CT_R"; //NBS175_CT_R2_1_CD
	private	static String ContactSourceSpreadNBS135_CT_R 		= "NBS135_CT_R"; //NBS135_CT_R2_1_CD
	
	/****************************************************PAGE 1**************************************************************/
	
	/*HEADER*/
	
	private static String delimiter1 = "__";
	private	static String DEM197_1 = "DEM197_1";
	protected static String DEM197_1__PatientIdDEM197= DEM197_1 + delimiter1+ "PatientIdDEM197";
	private	static String NBS136_1_CD = "NBS136_1_CD";
	protected static String NBS136_1_CD__ConditionNBS136= NBS136_1_CD + delimiter1+ "ConditionNBS136";
	private	static String NBS136_2_CD = "NBS136_2_CD";
	protected static String NBS136_2_CD__ConditionNBS136= NBS136_2_CD + delimiter1+ "ConditionNBS136";
	private	static String New_CaseNo_1 = "New_CaseNo_1";//Field Follow Up form constant
	protected static String New_CaseNo_1__InvestigationIdINV168 = New_CaseNo_1 + delimiter1+ "NewCaseNo1INV168";
	private	static String New_CaseNo_2 = "New_CaseNo_2";//Field Follow Up form constant
	protected static String New_CaseNo_2__InvestigationIdINV168 = New_CaseNo_2 + delimiter1+ "NewCaseNo2INV168";
	private	static String NBS191_1 = "NBS191_1";
	protected static String NBS191_1__LOTNBS191= NBS191_1 + delimiter1+ "LotNBS191";
	private	static String NBS191_2 = "NBS191_2";
	protected static String NBS191_2__LOTNBS191= NBS191_2 + delimiter1+ "LotNBS191";
	private	static String NBS160_1 = "NBS160_1";
	protected static String NBS160_1__InterviewRecordIdNBS160= NBS160_1 + delimiter1+ "InterviewRecordIdNBS160";
	private	static String STD102_CD = "STD102_CD";
	protected static String STD102_CD__NeurologicalInvolvementSTD102= STD102_CD + delimiter1+ "NeurologicalInvolvementSTD102";
	private	static String IXS109_1_CD = "IXS109_1_CD";
	protected static String IXS109_1_CD__900SiteTypeIXS109= IXS109_1_CD + delimiter1+ "900SiteTypeIXS109";
	private	static String IXS108_1 = "IXS108_1";
	protected static String IXS108_1__900SiteZipCodeIXS108= IXS108_1 + delimiter1+ "900SiteZipCodeIXS108";
	private	static String IXS107_1 = "IXS107_1";
	protected static String IXS107_1__900AgencyIdIXS107= IXS107_1 + delimiter1+ "900AgencyIdIXS107";
	
	/*NAME*/
	
	//Patient Name
	private	static String DEM102 = "DEM102";
	protected static String DEM102__PatientLastNameDEM102= DEM102 + delimiter1+ "PatientLastNameDEM102";
	private	static String DEM104 = "DEM104";
	protected static String DEM104__PatientFirstNameDEM104= DEM104 + delimiter1+ "PatientFirstNameDEM104";
	private	static String DEM250 = "DEM250";
	protected static String DEM250__PatientAliasNameDEM250= DEM250 + delimiter1+ "PatientAliasNameDEM250";	
	private	static String MAIDENNAME = "Maiden_Name";
	protected static String MAIDENNAME__MaidenName= MAIDENNAME + delimiter1+ "MaidenName";	
	
	/*ADDRESS*/
	
	private	static String DEM159_DEM160= "DEM159_DEM160";//street1+street2 - there is a space in form
	protected static String DEM159_160__PatientStreetDEM159= DEM159_DEM160 + delimiter1+ "PatientLastNameDEM159DEM160";
	private	static String DEM161 = "DEM161";
	protected static String DEM161__PatientCityDEM161= DEM161 + delimiter1+ "PatientCityDEM161";
	private	static String DEM162 = "DEM162";
	protected static String DEM162__PatientStateDEM162= DEM162 + delimiter1+ "PatientStateDEM162";
	private	static String DEM163 = "DEM163";
	protected static String DEM163__PatientZipDEM163= DEM163 + delimiter1+ "PatientZipDEM163";
	private	static String DEM165 = "DEM165";
	protected static String DEM165__CountyDEM165= DEM165 + delimiter1+ "CountyDEM165";
	//private	static String DISTRICT = "Patient_District"; //not captured by NBS
	//protected static String Patient_District__DISTRIC= DISTRICT + delimiter1+ "DistrictPatient_District";
	private	static String DEM167 = "DEM167";
	protected static String DEM167__CountryDEM167= DEM167 + delimiter1+ "CountryDEM167";
	private	static String NBS201_1 = "NBS201_1";
	protected static String NBS201_1__LivingWithNBS201= NBS201_1 + delimiter1+ "LivingWithNBS201";
	private	static String NBS202_1_CD = "NBS202_1_CD";
	protected static String NBS202_1_CD__ResidenceTypeNBS202= NBS202_1_CD + delimiter1+ "ResidenceTypeNBS202";
	
	//Time At Address
	private	static String NBS203_1 = "NBS203_1";
	protected static String NBS203_1__TimeAtAddressNBS203= NBS203_1 + delimiter1+ "TimeAtAddressNBS203";
	private	static String NBS204_1_CD = "NBS204_1_CD";
	protected static String NBS204_1_CD__TimeAtAddressNBS204= NBS204_1_CD + delimiter1+ "TimeAtAddressNBS204";
	
	//Time in State
	private	static String NBS205_1 = "NBS205_1";
	protected static String NBS205_1__TimeInStateNBS205= NBS205_1 + delimiter1+ "TimeInStateNBS205";
	private	static String NBS206_1_CD = "NBS206_1_CD";
	protected static String NBS206_1_CD__TimeInStateNBS206= NBS206_1_CD + delimiter1+ "TimeInStateNBS206";
	
	//Time in Country
	private	static String NBS207_1 = "NBS207_1";
	protected static String NBS207_1__TimeInCountryNBS207= NBS207_1 + delimiter1+ "TimeInCountryNBS207";
	private	static String NBS208_1_CD = "NBS208_1_CD";
	protected static String NBS208_1_CD__TimeInCountryNBS208= NBS208_1_CD + delimiter1+ "TimeInCountryNBS208";
	
	//Currently institutionalized
	private	static String NBS209_1_CDT = "NBS209_1_CDT";
	protected static String NBS209_1_CDT__CurrentlyInstitutionalizedNBS209= NBS209_1_CDT + delimiter1+ "CurrentlyInstitutionalizedNBS209";
	
	//Name of Institution
	private	static String NBS210_1 = "NBS210_1";
	protected static String NBS210_1__NameOfInstitutionNBS210= NBS210_1 + delimiter1+ "NameOfInstitutionNBS210";
	//Type of Institution
	private	static String NBS211_1_CD = "NBS211_1_CD";
	protected static String NBS211_1_CD__TypeOfInstitutionNBS211= NBS211_1_CD + delimiter1+ "TypeOfInstitutionNBS211";
	
	/*PHONE/CONTACT*/
	
	private	static String NBS006 = "NBS006";
	protected static String NBS006__PatientCellNBS006_UseCd = NBS006 + delimiter1+ "PatientCellNBS006";
	private	static String DEM177 = "DEM177";
	protected static String DEM177__PatientHmPhDEM177_UseCd = DEM177 + delimiter1+ "PatientHmPhDEM177";
	//Place of employment
	private static String Employment_Phone = "Employment_Phone";//Field Follow Up form constant
	protected	static String EMPLOYMENT__PatientWorkPhoneNBS002_PatientWorkPhoneExtensionNBS003 = Employment_Phone+delimiter1+"PatientWorkPhoneNBS002_PatientWorkPhoneExtensionNBS003";
	//PAGER
	private	static String DEM177_R2 = "DEM177_R2";
	protected static String DEM177_R2__PagerDEM177 = DEM177_R2 + delimiter1+ "PagerDEM177";
	
	//eMail
	private	static String DEM182 = "DEM182";
	protected static String DEM182__EmailDEM182 = DEM182 + delimiter1+ "EmailDEM182";
	private	static String DEM182_R2 = "DEM182_R2";
	protected static String DEM182_R2__Email2DEM182 = DEM182 + delimiter1+ "Email2DEM182";

	private	static String EMERCTNAME = "Emer_Ct_Name";
	protected static String Emer_Ct_Name__EmailAddressesEmerCtName = EMERCTNAME + delimiter1+ "EmailAddressesEmerCtName";
		private	static String EMERCTPHONE = "Emer_Ct_Phone";
	protected static String Emer_Ct_Phone__EmailAddressesEmerCtPhone = EMERCTPHONE + delimiter1+ "EmailAddressesEmerCtPhone";
		private	static String EMERCTRELATION = "Emer_Ct_Relation";
	protected static String Emer_Ct_Relation__EmailAddressesEmerCtRelation = EMERCTRELATION + delimiter1+ "EmailAddressesEmerCtRelation";
	
	
	/*DEMOGRAPHICS*/
	//Age
	private	static String INV2001 = "INV2001";
	protected static String INV2001__PatientReportedAge = INV2001 + delimiter1+ "PatientReportedAgeINV2001";
	private	static String DEM115 = "DEM115";
	protected static String DEM115__PatientDOB = DEM115 + delimiter1+ "PatientDOBDEM115";
	private	static String BIRTH_SEX_CDT = "Birth_Sex_CDT";//Field Follow Up form constant
	protected static String BIRTH_SEX_CDT__BirthSex = BIRTH_SEX_CDT + delimiter1+ "BirthSex";
	//Gender
	private	static String DEM113_CD = "DEM113_CD";
	protected static String DEM113_CD__PatientCurrentSexDEM113 = DEM113_CD + delimiter1+ "PatientCurrentSexDEM113";	 
	private	static String NBS274_CD = "NBS274_CD";
	protected static String  NBS274_CD__PatientTransgenderNBS274 =  NBS274_CD + delimiter1+ "PatientTransgenderNBS274";	
	private	static String NBS272_CD = "NBS272_CD";
	protected static String NBS272_CD__PatientSexUnkownReasonNBS272 = NBS272_CD + delimiter1+ "PatientSexUnkownReasonNBS272";
	private	static String ADDL_SEX = "Addl_Sex";//Field Follow Up form constant
	protected static String ADDL_SEX__AddlSex = ADDL_SEX + delimiter1+ "AddlSex";
	//Marital Status
	private	static String DEM140_CDT = "DEM140_CDT";
	protected static String DEM140_CDT__PatientMaritalStatusDEM140 = DEM140_CDT + delimiter1+ "PatientMaritalStatusDEM140";
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
	//Ethnicity
	private	static String DEM155_CDT = "DEM155_CDT";
	protected static String DEM155_CDT__PatientEthnicityDEM155 = DEM155_CDT + delimiter1+ "EthnicityDEM155";
	private	static String NBS273_CDT = "NBS273_CDT";
	protected static String NBS273_CDT__PatientEthnicityUnkownReasonNBS273 = NBS273_CDT + delimiter1+ "PatientEthnicityUnkownReasonNBS273";
	//English Speaking
	private	static String DEM214_1_CDT = "DEM214_1_CDT";
	protected static String DEM214_1_CDT__EnglishSpeakingDEM214= DEM214_1_CDT + delimiter1+ "EnglishSpeakingDEM214";
	private	static String DEM142_1_CDT = "DEM142_1_CDT";
	protected static String DEM142_1_CDT__PrimaryLanguageDEM142= DEM142_1_CDT + delimiter1+ "PrimaryLanguageDEM142";
	
	
	/*PREGNANCY*/

	private	static String NBS216_1_CD = "NBS216_1_CD";
	protected static String NBS216_1_CD__PregnantAtExamNBS216= NBS216_1_CD + delimiter1+ "PregnantAtExamNBS216";
	private	static String NBS217_1 = "NBS217_1";
	protected static String NBS217_1__PregnantAtExamWeeksNBS217= NBS217_1 + delimiter1+ "PregnantAtExamWeeksNBS217";
	private	static String NBS218_1_CD = "NBS218_1_CD";
	protected static String NBS218_1_CD__PregnantAtInterviewNBS218= NBS218_1_CD + delimiter1+ "PregnantAtInterviewNBS218";
	private	static String NBS219 = "NBS219";
	protected static String NBS219__PregnantAtInterviewWeeksNBS219= NBS219 + delimiter1+ "PregnantAtInterviewWeeksNBS219";
	private	static String NBS220_1_CD = "NBS220_1_CD";
	protected static String NBS220_1_CD__CurrentlyInPrenatalCareNBS220= NBS220_1_CD + delimiter1+ "CurrentlyInPrenatalCareNBS220";
	private	static String NBS221_1_CD = "NBS221_1_CD";
	protected static String NBS221_1_CD__PregnantInLast12MonthsNBS221= NBS221_1_CD + delimiter1+ "PregnantInLast12MonthsNBS221";
	private	static String NBS222_1_CD = "NBS222_1_CD";
	protected static String NBS222_1_CD__PregnantInLastMonthNBS222= NBS222_1_CD + delimiter1+ "PregnantInLastMonthNBS222";
	
	/*CONDITION 1 REPORTING INFORMATION*/
	
	private	static String INV159_1_CD = "INV159_1_CD";
	protected static String INV159_1_CD__MethodOfCaseDetectionINV159= INV159_1_CD + delimiter1+ "MethodOfCaseDetectionINV159";
	private	static String CASEDETECT_1 = "CaseDetect_1";
	protected static String CASEDETECT_1__OtherCaseDetect= CASEDETECT_1 + delimiter1+ "OtherCaseDetect";
	private	static String NBS136_OP_1_CD = "NBS136_OP_1_CD";
	protected static String NBS136_OP_1_CD__OPConditionNBS136_R2= NBS136_OP_1_CD + delimiter1+ "OPConditionNBS136";
	private	static String CaseID_OP_1 = "CaseID_OP_1";
	protected static String CaseID_OP_1__OPCaseIDCaseID_OP= CaseID_OP_1 + delimiter1+ "OPCaseIDCaseID_OP";
	private	static String TestFacility_1 = "TestFacility_1";
	protected static String TestFacility_1__FirstTestedTestFacility= TestFacility_1 + delimiter1+ "FirstTestedTestFacility";
	private	static String TestFacilityOth_1 = "TestFacilityOth_1";
	protected static String TestFacilityOth_1__IfOtherDescribeTestFacilityOth_1= TestFacilityOth_1 + delimiter1+ "IfOtherDescribeTestFacilityOth";
	private	static String LAB201_1 = "LAB201_1";
	protected static String LAB201_1__LaboratoryReportDateLAB201= LAB201_1 + delimiter1+ "LaboratoryReportDateLAB201";
	private	static String NBS192_1_CDT = "NBS192_1_CDT";
	protected static String NBS192_1_CDT__InterviewedNBS192= NBS192_1_CDT + delimiter1+ "InterviewedNBS192";
	private	static String NBS192_I2_1_CDT = "NBS192_I2_1_CDT";
	protected static String NBS192_I2_1_CDT__InterviewedNBS192= NBS192_I2_1_CDT + delimiter1+ "InterviewedNBS192";
	
	//private	static String NotIxdReason_1_CDT = "NotIxdReason_1_CDT";
	//protected static String NotIxdReason_1_CDT__ReasonNotInterviewedNBS192= NotIxdReason_1_CDT + delimiter1+ "ReasonNotInterviewedNBS192";
	private	static String NotIxdOth_1 = "NotIxdOth_1";
	protected static String NotIxdOth_1__InterviewedDescribeNotIxdOth= NotIxdOth_1 + delimiter1+ "InterviewedDescribenNotIxdOth";
	private	static String IxPeriod_1 = "IxPeriod_1";
	protected static String IxPeriod_1__InterviewPeriodIxPeriod= IxPeriod_1 + delimiter1+ "InterviewPeriodIxPeriod";
	
	private	static String IXS106_IR_1_CD = "IXS106_IR_1_CD";
	protected static String IXS106_IR_1_CD__PlaceOfInterviewIXS106= IXS106_IR_1_CD + delimiter1+ "PlaceOfInterviewIXS106";
	private	static String IxPlaceOth_1 = "IxPlaceOth_1";
	protected static String IxPlaceOth_1__PlaceOtherIxPlaceOth= IxPlaceOth_1 + delimiter1+ "PlaceOtherIxPlaceOth";
	private	static String IXS107_IR_1 = "IXS107_IR_1";
	protected static String IXS107_IR_1__PEMSSiteIdIXS107= IXS107_IR_1 + delimiter1+ "PEMSSiteIdIXS107";
	private	static String NBS189_1 = "NBS189_1";
	protected static String NBS189_1__DateFirstAssignedForInterviewNBS189= NBS189_1 + delimiter1+ "DateFirstAssignedForInterviewNBS189";
	private	static String NBS188_1_CD = "NBS188_1_CD";
	protected static String NBS188_1_CD__DISFirstInterviewNBS188= NBS188_1_CD + delimiter1+ "DISFirstInterviewNBS188";
	private	static String NBS187_1 = "NBS187_1";
	protected static String NBS187_1__DateReassignedForInterview1_NBS187= NBS187_1 + delimiter1+ "DateReassignedForInterview1_NBS187";
	private	static String NBS186_1_CD = "NBS186_1_CD";
	protected static String NBS186_1_CD__DISDateReassigned1_NBS186= NBS186_1_CD + delimiter1+ "DISDateReassigned1_NBS186";
	private	static String IXS101_IR_I1_1 = "IXS101_IR_I1_1";
	protected static String IXS101_IR_I1_1__DateOriginalInterviewIXS101= IXS101_IR_I1_1 + delimiter1+ "DateOriginalInterviewIXS101";
	private	static String IXS102_IR_I1_1_CD = "IXS102_IR_I1_1_CD";
	protected static String IXS102_IR_I1_1_CD__DISOriginalInterviewIXS102= IXS102_IR_I1_1_CD + delimiter1+ "DISOriginalInterviewIXS102";
	private	static String IXS101_IR_I2_1 = "IXS101_IR_I2_1";
	protected static String IXS101_IR_I2_1__DateFirstReinterviewIXS101= IXS101_IR_I2_1 + delimiter1+ "DateFirstReinterviewIXS101";
	private	static String IXS102_IR_I2_1_CD = "IXS102_IR_I2_1_CD";
	protected static String IXS102_IR_I2_1_CD__DISFirstReinterviewIXS102= IXS102_IR_I2_1_CD + delimiter1+ "DISFirstReinterviewIXS102";
	private	static String NBS196_1 = "NBS196_1";
	protected static String NBS196_1__DateCaseClosedNBS196= NBS196_1 + delimiter1+ "DateCaseClosedNBS196";
	private	static String NBS197_1_CD = "NBS197_1_CD";
	protected static String NBS197_1_CD__DISDateClosedNBS197= NBS197_1_CD + delimiter1+ "DISDateClosedNBS197";
	private	static String NBS190_1_CD = "NBS190_1_CD";
	protected static String NBS190_1_CD__SupervisorNBS190= NBS190_1_CD + delimiter1+ "SupervisorNBS190";
	private	static String INV152_1_CD = "INV152_1_CD";
	protected static String INV152_1_CD__ImportedCaseINV152= INV152_1_CD + delimiter1+ "ImportedCaseINV152";
	private	static String ImportLocation_1 = "ImportLocation_1";
	protected static String ImportLocation_1__ImportLocationImportLocation= ImportLocation_1 + delimiter1+ "ImportLocationImportLocation";
	
	/*CONDITION 2 REPORTING INFORMATION*/
	
	private	static String INV159_2_CD = "INV159_2_CD";
	protected static String INV159_2_CD__MethodOfCaseDetectionINV159= INV159_2_CD + delimiter1+ "MethodOfCaseDetectionINV159";
	private	static String CASEDETECT_2 = "CaseDetect_2";
	protected static String CASEDETECT_2__OtherCaseDetect= CASEDETECT_2 + delimiter1+ "OtherCaseDetect";
	private	static String NBS136_OP_2_CD = "NBS136_OP_2_CD";
	protected static String NBS136_OP_2_CD__OPConditionNBS136_R2= NBS136_OP_2_CD + delimiter1+ "OPConditionNBS136_R2";
	private	static String CaseID_OP_2 = "CaseID_OP_2";
	protected static String CaseID_OP_2__OPCaseIDCaseID_OP= CaseID_OP_2 + delimiter1+ "OPCaseIDCaseID_OP";
	private	static String TestFacility_2 = "TestFacility_2";
	protected static String TestFacility_2__FirstTestedTestFacility= TestFacility_2 + delimiter1+ "FirstTestedTestFacility";
	private	static String TestFacilityOth_2 = "TestFacilityOth_2";
	protected static String TestFacilityOth_2__IfOtherDescribeTestFacilityOth_2= TestFacilityOth_2 + delimiter1+ "IfOtherDescribeTestFacilityOth_2";
	private	static String LAB201_2 = "LAB201_2";
	protected static String LAB201_2__LaboratoryReportDateLAB201= LAB201_2 + delimiter1+ "LaboratoryReportDateLAB201";
	private	static String NBS192_2_CDT = "NBS192_2_CDT";
	protected static String NBS192_2_CDT__InterviewedNBS192= NBS192_2_CDT + delimiter1+ "InterviewedNBS192";
	private	static String NBS192_I2_2_CDT = "NBS192_I2_2_CDT";
	protected static String NBS192_I2_2_CDT__InterviewedNBS192= NBS192_I2_2_CDT + delimiter1+ "InterviewedNBS192";
	//private	static String NotIxdReason_2_CDT = "NotIxdReason_2_CDT";
	//protected static String NotIxdReason_2_CDT__InterviewedIfNotNBS192_R2_2_CDT= NotIxdReason_2_CDT + delimiter1+ "InterviewedIfNotNBS192";
	private	static String NotIxdOth_2 = "NotIxdOth_2";
	protected static String NotIxdOth_2__InterviewedDescribeNotIxdOth_2= NotIxdOth_2 + delimiter1+ "InterviewedDescribeNotIxdOth_2";
	private	static String IxPeriod_2 = "IxPeriod_2";
	protected static String IxPeriod_2__InterviewPeriodIxPeriod_2= IxPeriod_2 + delimiter1+ "InterviewPeriodIxPeriod_2";
	private	static String IXS106_IR_2_CD = "IXS106_IR_2_CD";
	protected static String IXS106_IR_2_CD__PlaceOfInterviewIXS106_2_CD= IXS106_IR_2_CD + delimiter1+ "PlaceOfInterviewIXS106";
	private	static String IxPlaceOth_2 = "IxPlaceOth_2";//TODO: Cambiar? Waiting Jenifer email
	protected static String IxPlaceOth_2__PlaceOtherIxPlaceOth_2= IxPlaceOth_2 + delimiter1+ "PlaceOtherIxPlaceOth_2";
	private	static String IXS107_IR_2 = "IXS107_IR_2";
	protected static String IXS107_IR_2__PEMSSiteIDIXS107_R2_2= IXS107_IR_2 + delimiter1+ "PEMSSiteIDIXS107_R2_2";
	private	static String NBS189_2 = "NBS189_2";
	protected static String NBS189_2__DateFirstAssignedForInterviewNBS189_2= NBS189_2 + delimiter1+ "DateFirstAssignedForInterviewNBS189_2";
	private	static String NBS188_2_CD = "NBS188_2_CD";
	protected static String NBS188_2_CD__DISFirstInterviewNBS188= NBS188_2_CD + delimiter1+ "DISFirstInterviewNBS188";
	private	static String NBS187_2 = "NBS187_2";
	protected static String NBS187_2__DateReassignedForInterview1_NBS187= NBS187_2 + delimiter1+ "DateReassignedForInterview1_NBS187";
	private	static String NBS186_2_CD = "NBS186_2_CD";
	protected static String NBS186_2_CD__DISDateReassigned1_NBS186= NBS186_2_CD + delimiter1+ "DISDateReassigned1_NBS186";
	private	static String IXS101_IR_I1_2 = "IXS101_IR_I1_2";
	protected static String IXS101_IR_I1_2__DateOriginalInterviewIXS101_R2_2= IXS101_IR_I1_2 + delimiter1+ "DateOriginalInterviewIXS101";
	private	static String IXS102_IR_I1_2_CD = "IXS102_IR_I1_2_CD";
	protected static String IXS102_IR_I1_2_CD__DISOriginalInterviewIXS102_R2_2_CD= IXS102_IR_I1_2_CD + delimiter1+ "DISOriginalInterviewIXS102_R2_2_CD";
	private	static String IXS101_IR_I2_2 = "IXS101_IR_I2_2";
	protected static String IXS101_IR_I2_2__DateFirstReinterviewIXS101_R3_2= IXS101_IR_I2_2 + delimiter1+ "DateFirstReinterviewIXS101_R3_2";
	private	static String IXS102_IR_I2_2_CD = "IXS102_IR_I2_2_CD";
	protected static String IXS102_IR_I2_2_CD__DISFirstReinterviewIXS102_R3_2_CD= IXS102_IR_I2_2_CD + delimiter1+ "DISFirstReinterviewIXS102_R3_2_CD";
	private	static String NBS196_2 = "NBS196_2";
	protected static String NBS196_2__DateCaseClosedNBS196_2= NBS196_2 + delimiter1+ "DateCaseClosedNBS196_2";
	private	static String NBS197_2_CD = "NBS197_2_CD";
	protected static String NBS197_2_CD__DISDateClosedNBS197= NBS197_2_CD + delimiter1+ "DISDateClosedNBS197";
	private	static String NBS190_2_CD = "NBS190_2_CD";
	protected static String NBS190_2_CD__SupervisorNBS190= NBS190_2_CD + delimiter1+ "SupervisorNBS190";
	private	static String INV152_2_CD = "INV152_2_CD";
	protected static String INV152_2_CD__ImportedCaseINV152_2_CD= INV152_2_CD + delimiter1+ "ImportedCaseINV152_2_CD";
	private	static String ImportLocation_2 = "ImportLocation_2";
	protected static String ImportLocation_2__ImportLocationImportLocation_2= ImportLocation_2 + delimiter1+ "ImportLocationImportLocation_2";
		
	
	
	/****************************************************PAGE 2**************************************************************/
	
	private	static String NBS229_CD = "NBS229_CD";
	protected static String NBS229_CD__RiskAssesedNBS229= NBS229_CD + delimiter1+ "RiskAssesedNBS229";
	private	static String STD107_CD = "STD107_CD";
	protected static String STD107_CD__sexWithMaleSTD107= STD107_CD + delimiter1+ "sexWithMaleSTD107";
	private	static String STD108_CD = "STD108_CD";
	protected static String STD108_CD__sexWidthFemaleSTD108= STD108_CD + delimiter1+ "sexWidthFemaleSTD108";
	private	static String NBS230_CD = "NBS230_CD";
	protected static String NBS230_CD__sexWithTransgenderNBS230= NBS230_CD + delimiter1+ "sexWithTransgenderNBS230";
	private	static String STD109_CD = "STD109_CD";
	protected static String STD109_CD__sexWithAnonymousSTD109= STD109_CD + delimiter1+ "sexWithAnonymousSTD109";
	private	static String NBS231_CD = "NBS231_CD";
	protected static String NBS231_CD__sexWithoutCondomNBS231= NBS231_CD + delimiter1+ "sexWithoutCondomNBS231";
	private	static String STD111_CD = "STD111_CD";
	protected static String STD111_CD__sexWithIntoxicatedDrugsSTD111= STD111_CD + delimiter1+ "sexWithIntoxicatedDrugsSTD111";
	private	static String STD112_CD = "STD112_CD";
	protected static String STD112_CD__exchangedDrugsSTD112= STD112_CD + delimiter1+ "exchangedDrugsSTD112";
	private	static String STD113_CD = "STD113_CD";
	protected static String STD113_CD__sexMSMSTD113= STD113_CD + delimiter1+ "sexMSMSTD113";
	private	static String STD110_CD = "STD110_CD";
	protected static String STD110_CD__sexIDUSTD110= STD110_CD + delimiter1+ "sexIDUSTD110";
	private	static String STD118_CD = "STD118_CD";
	protected static String STD118_CD__beenIncarceratedSTD118= STD118_CD + delimiter1+ "beenIncarceratedSTD118";
	private	static String STD114_CD = "STD114_CD";
	protected static String STD114_CD__engagedInjectionDrugSTD114= STD114_CD + delimiter1+ "engagedInjectionDrugSTD114";
	private	static String NBS232_CD = "NBS232_CD";
	protected static String NBS232_CD__sharedInjectionNBS232= NBS232_CD + delimiter1+ "sharedInjectionNBS232";
	private	static String NBS233_CDT = "NBS233_CDT";
	protected static String NBS233_CDT__injectionDrugNoneNBS233= NBS233_CDT + delimiter1+ "injectionDrugNoneNBS233";
	private	static String NBS235_CDT = "NBS235_CDT";
	protected static String NBS235_CDT__injectionDrugCrackNBS235= NBS235_CDT + delimiter1+ "injectionDrugCrackNBS235";
	private	static String NBS237_CDT = "NBS237_CDT";
	protected static String NBS237_CDT__injectionDrugCocaineNBS237= NBS237_CDT + delimiter1+ "injectionDrugCocaineNBS237";
	private	static String NBS239_CDT = "NBS239_CDT";
	protected static String NBS239_CDT__injectionDrugHeroinNBS239= NBS239_CDT + delimiter1+ "injectionDrugHeroinNBS239";
	private	static String NBS234_CDT = "NBS234_CDT";
	protected static String NBS234_CDT__injectionDrugMetamphetaminesNBS234= NBS234_CDT + delimiter1+ "injectionDrugMetamphetaminesNBS234";
	private	static String NBS236_CDT = "NBS236_CDT";
	protected static String NBS236_CDT__injectionDrugNitratesNBS236= NBS236_CDT + delimiter1+ "injectionDrugNitratesNBS236";
	private	static String NBS238_CDT = "NBS238_CDT";
	protected static String NBS238_CDT__injectionDrugErectileNBS238= NBS238_CDT + delimiter1+ "injectionDrugErectileNBS238";
	private	static String NBS240_CDT = "NBS240_CDT";
	protected static String NBS240_CDT__injectionDrugOtherNBS240= NBS240_CDT + delimiter1+ "injectionDrugOtherNBS240";
	private	static String STD300_1 = "STD300_1";
	protected static String STD300_1__injectionDrugOtherSpecifySTD300= STD300_1 + delimiter1+ "injectionDrugOtherSpecifySTD300";
	private	static String NBS243T_R1_1_CD = "NBS243T_R1_1_CD";
	protected static String NBS243T_R1_1_CD__placesMetPartnersTypeNBS243T= NBS243T_R1_1_CD + delimiter1+ "placesMetPartnersTypeNBS243T";
	private	static String NBS243T_R2_1_CD = "NBS243T_R2_1_CD";
	protected static String NBS243T_R2_1_CD__placesMetPartnersTypeNBS243T= NBS243T_R2_1_CD + delimiter1+ "placesMetPartnersTypeNBS243T";
	private	static String NBS243T_R3_1_CD = "NBS243T_R3_1_CD";
	protected static String NBS243T_R3_1_CD__placesMetPartnersTypeNBS243T= NBS243T_R3_1_CD + delimiter1+ "placesMetPartnersTypeNBS243T";
	private	static String NBS243T_R4_1_CD = "NBS243T_R4_1_CD";
	protected static String NBS243T_R4_1_CD__placesMetPartnersTypeNBS243T= NBS243T_R4_1_CD + delimiter1+ "placesMetPartnersTypeNBS243T";
	private	static String NBS243T_R5_1_CD = "NBS243T_R5_1_CD";
	protected static String NBS243T_R5_1_CD__placesMetPartnersTypeNBS243T= NBS243T_R5_1_CD + delimiter1+ "placesMetPartnersTypeNBS243T";
	private	static String NBS242_1_CDT = "NBS242_1_CDT";
	protected static String NBS242_1_CDT__placesMetPartnersDidNotAskRefusedToAnswerNBS242= NBS242_1_CDT + delimiter1+ "placesMetPartnersDidNotAskRefusedToAnswerNBS242";
	private	static String NBS243N_R1_1 = "NBS243N_R1_1";
	protected static String NBS243N_R1_1__PlacesMetPartnersNameNBS243N= NBS243N_R1_1 + delimiter1+ "PlacesMetPartnersNameNBS243N";
	private	static String NBS243N_R2_1 = "NBS243N_R2_1";
	protected static String NBS243N_R2_1__PlacesMetPartnersNameNBS243N= NBS243N_R2_1 + delimiter1+ "PlacesMetPartnersNameNBS243N";
	private	static String NBS243N_R3_1 = "NBS243N_R3_1";
	protected static String NBS243N_R3_1__PlacesMetPartnersNameNBS243N= NBS243N_R3_1 + delimiter1+ "PlacesMetPartnersNameNBS243N";
	private	static String NBS243N_R4_1 = "NBS243N_R4_1";
	protected static String NBS243N_R4_1__PlacesMetPartnersNameNBS243N= NBS243N_R4_1 + delimiter1+ "PlacesMetPartnersNameNBS243N";
	private	static String NBS243N_R5_1 = "NBS243N_R5_1";
	protected static String NBS243N_R5_1__PlacesMetPartnersNameNBS243N= NBS243N_R5_1 + delimiter1+ "PlacesMetPartnersNameNBS243N";
	
	
	//Places had sex
	private	static String NBS290T_R1_1_CD = "NBS290T_R1_1_CD";
	protected static String NBS290T_R1_1_CD__PlacesHadSexTypeNBS290T= NBS290T_R1_1_CD + delimiter1+ "PlacesHadSexTypeNBS290T";
	private	static String NBS290T_R2_1_CD = "NBS290T_R2_1_CD";
	protected static String NBS290T_R2_1_CD__PlacesHadSexTypeNBS290T= NBS290T_R2_1_CD + delimiter1+ "PlacesHadSexTypeNBS290T";
	private	static String NBS290T_R3_1_CD = "NBS290T_R3_1_CD";
	protected static String NBS290T_R3_1_CD__PlacesHadSexTypeNBS290T= NBS290T_R3_1_CD + delimiter1+ "PlacesHadSexTypeNBS290T";
	private	static String NBS290T_R4_1_CD = "NBS290T_R4_1_CD";
	protected static String NBS290T_R4_1_CD__PlacesHadSexTypeNBS290T= NBS290T_R4_1_CD + delimiter1+ "PlacesHadSexTypeNBS290T";
	private	static String NBS290T_R5_1_CD = "NBS290T_R5_1_CD";
	protected static String NBS290T_R5_1_CD__PlacesHadSexTypeNBS290T= NBS290T_R5_1_CD + delimiter1+ "PlacesHadSexTypeNBS290T";
	private	static String NBS244_1_CDT = "NBS244_1_CDT";
	protected static String NBS244_1_CDT__PlacesHadSexDidNotAskNBS244= NBS244_1_CDT + delimiter1+ "PlacesHadSexDidNotAskNBS244";
	private	static String NBS290N_R1_1 = "NBS290N_R1_1";
	protected static String NBS290N_R1_1__PlacesHadSexNameNBS290N= NBS290N_R1_1 + delimiter1+ "PlacesHadSexNameNBS290N";
	private	static String NBS290N_R2_1 = "NBS290N_R2_1";
	protected static String NBS290N_R2_1__PlacesHadSexNameNBS290N= NBS290N_R2_1 + delimiter1+ "PlacesHadSexNameNBS290N";
	private	static String NBS290N_R3_1 = "NBS290N_R3_1";
	protected static String NBS290N_R3_1__PlacesHadSexNameNBS290N= NBS290N_R3_1 + delimiter1+ "PlacesHadSexNameNBS290N";
	private	static String NBS290N_R4_1 = "NBS290N_R4_1";
	protected static String NBS290N_R4_1__PlacesHadSexNameNBS290N= NBS290N_R4_1 + delimiter1+ "PlacesHadSexNameNBS290N";
	private	static String NBS290N_R5_1 = "NBS290N_R5_1";
	protected static String NBS290N_R5_1__PlacesHadSexNameNBS290N= NBS290N_R5_1 + delimiter1+ "PlacesHadSexNameNBS290N";
	
	
	//Partners in last 12 Months Female
	private	static String NBS224_1 = "NBS224_1";
	protected static String NBS224_1__PartnersLast12MonthFemaleNBS224= NBS224_1 + delimiter1+ "PartnersLast12MonthFemaleNBS224";
	private	static String NBS223_1_CDT = "NBS223_1_CDT";
	protected static String NBS223_1_CDT__PartnersLast12MonthFemaleUnknownRefusedNBS223= NBS223_1_CDT + delimiter1+ "PartnersLast12MonthFemaleUnknownRefusedNBS223";
		
	//Partners in last 12 Months Male
	
	private	static String NBS226_1 = "NBS226_1";
	protected static String NBS226_1__PartnersLast12MonthMaleNBS226= NBS226_1 + delimiter1+ "PartnersLast12MonthMaleNBS226";
	private	static String NBS225_1_CDT = "NBS225_1_CDT";
	protected static String NBS225_1_CDT__PartnersLast12MonthMaleUnknownRefusedNBS225= NBS225_1_CDT + delimiter1+ "PartnersLast12MonthMaleUnknownRefusedNBS225";
	
	//Partners in last 12 Months Transgender
	
	private	static String NBS228_1 = "NBS228_1";
	protected static String NBS228_1__PartnersLast12MonthTransgenderNBS228= NBS228_1 + delimiter1+ "PartnersLast12MonthTransgenderNBS228";
	private	static String NBS227_1_CDT = "NBS227_1_CDT";
	protected static String NBS227_1_CDT__PartnersLast12MonthTransgenderUnknownRefusedNBS227= NBS227_1_CDT + delimiter1+ "PartnersLast12MonthTransgenderUnknownRefusedNBS227";
	
	
	//Interview Period Partners Condition 1

	private	static String NBS130_1 = "NBS130_1";
	protected static String NBS130_1__InterviewPeriodPartnerFemaleNBS130= NBS130_1 + delimiter1+ "InterviewPeriodPartnerFemaleNBS130";
	private	static String NBS129_1_CDT = "NBS129_1_CDT";
	protected static String NBS129_1_CDT__InterviewPeriodPartnerFemaleUnknownRefusedNBS129= NBS129_1_CDT + delimiter1+ "InterviewPeriodPartnerFemaleUnknownRefusedNBS129";
	private	static String NBS132_1 = "NBS132_1";
	protected static String NBS132_1__InterviewPeriodPartnerMaleNBS132= NBS132_1 + delimiter1+ "InterviewPeriodPartnerMaleNBS132";
	private	static String NBS131_1_CDT = "NBS131_1_CDT";
	protected static String NBS131_1_CDT__InterviewPeriodPartnerMaleUnknownRefusedNBS131= NBS131_1_CDT + delimiter1+ "InterviewPeriodPartnerMaleUnknownRefusedNBS131";
	private	static String NBS134_1 = "NBS134_1";
	protected static String NBS134_1__InterviewPeriodPartnerTransgenderNBS134= NBS134_1 + delimiter1+ "InterviewPeriodPartnerTransgenderNBS134";
	private	static String NBS133_1_CDT = "NBS133_1_CDT";
	protected static String NBS133_1_CDT__InterviewPeriodPartnerUnknownRefusedNBS133= NBS133_1_CDT + delimiter1+ "InterviewPeriodPartnerUnknownRefusedNBS133";

	//Interview Period Partners Condition 2
	
	private	static String NBS130_2 = "NBS130_2";
	protected static String NBS130_2__InterviewPeriodPartnerFemaleNBS130= NBS130_2 + delimiter1+ "InterviewPeriodPartnerFemaleNBS130";
	private	static String NBS129_2_CDT = "NBS129_2_CDT";
	protected static String NBS129_2_CDT__InterviewPeriodPartnerFemaleUnknownRefusedNBS129= NBS129_2_CDT + delimiter1+ "InterviewPeriodPartnerFemaleUnknownRefusedNBS129";
	private	static String NBS132_2 = "NBS132_2";
	protected static String NBS132_2__InterviewPeriodPartnerMaleNBS132= NBS132_2 + delimiter1+ "InterviewPeriodPartnerMaleNBS132";
	private	static String NBS131_2_CDT = "NBS131_2_CDT";
	protected static String NBS131_2_CDT__InterviewPeriodPartnerMaleUnknownRefusedNBS131= NBS131_2_CDT + delimiter1+ "InterviewPeriodPartnerMaleUnknownRefusedNBS131";
	private	static String NBS134_2 = "NBS134_2";
	protected static String NBS134_2__InterviewPeriodPartnerTransgenderNBS134= NBS134_2 + delimiter1+ "InterviewPeriodPartnerTransgenderNBS134";
	private	static String NBS133_2_CDT = "NBS133_2_CDT";
	protected static String NBS133_2_CDT__InterviewPeriodPartnerUnknownRefusedNBS133= NBS133_2_CDT + delimiter1+ "InterviewPeriodPartnerUnknownRefusedNBS133";

				
	
		//Partner Internet Information
		
	private	static String STD119_1_CDT = "STD119_1_CDT";
	protected static String STD119_1_CDT__partnerInternetInformationSTD119= STD119_1_CDT + delimiter1+ "partnerInternetInformationSTD119";
		
		//Social History Comments
		
		private	static String SocHistComments = "SocHistComments";
		protected static String SocHistComments__SocialHistoryCommentsSocHistComments= SocHistComments + delimiter1+ "SocialHistoryCommentsSocHistComments";

		
//PAGE 3		
	//STD Testing - STD Lab Tests
	
		private	static String LAB163_RS1 = "LAB163_RS1";
		protected static String LAB163_RS1__StdFirstLabSpecimenDateLAB163= LAB163_RS1 + delimiter1+ "StdFirstLabSpecimenDateLAB163";
		private	static String LAB220_RS1 = "LAB220_RS1";
		protected static String LAB220_RS1__StdFirstLabTestNameLAB220= LAB220_RS1 + delimiter1+ "StdFirstLabTestNameLAB220";
		private	static String Lab_Result_RS1 = "Lab_Result_RS1";
		protected static String Lab_Result_RS1__StdFirstLabTestResultLAB220= Lab_Result_RS1 + delimiter1+ "StdFirstLabResultLAB220";
		private	static String ORD3_RS1 = "ORD3_RS1";
		protected static String ORD3_RS1__StdFirstLabReportingFacility= ORD3_RS1 + delimiter1+ "StdFirstLabReportingFacility";
		private	static String LAB165_RS1 = "LAB165_RS1";
		protected static String LAB165_RS1__StdFirstLabSpecimenSourceLAB165= LAB165_RS1 + delimiter1+ "StdFirstLabSpecimenSourceLAB165";
		
		private	static String LAB163_RS2 = "LAB163_RS2";
		protected static String LAB163_RS2__StdSecondLabSpecimenDateLAB163= LAB163_RS2 + delimiter1+ "StdSecondLabSpecimenDateLAB163";
		private	static String LAB220_RS2 = "LAB220_RS2";
		protected static String LAB220_RS2__StdSecondLabTestNameLAB220= LAB220_RS2 + delimiter1+ "StdSecondLabTestNameLAB220";
		private	static String Lab_Result_RS2 = "Lab_Result_RS2";
		protected static String Lab_Result_RS2__StdSecondLabTestResultLAB220= Lab_Result_RS2 + delimiter1+ "StdSecondLabResultLAB220";
		private	static String ORD3_RS2 = "ORD3_RS2";
		protected static String ORD3_RS2__StdSecondLabReportingFacility= ORD3_RS2 + delimiter1+ "StdSecondLabReportingFacility";
		private	static String LAB165_RS2 = "LAB165_RS2";
		protected static String LAB165_RS2__StdSecondLabSpecimenSourceLAB165= LAB165_RS2 + delimiter1+ "StdSecondLabSpecimenSourceLAB165";
		
		private	static String LAB163_RS3 = "LAB163_RS3";
		protected static String LAB163_RS3__StdThirdLabSpecimenDateLAB163= LAB163_RS3 + delimiter1+ "StdThirdLabSpecimenDateLAB163";
		private	static String LAB220_RS3 = "LAB220_RS3";
		protected static String LAB220_RS3__StdThirdLabTestNameLAB220= LAB220_RS3 + delimiter1+ "StdThirdLabTestNameLAB220";
		private	static String Lab_Result_RS3 = "Lab_Result_RS3";
		protected static String Lab_Result_RS3__StdThirdLabTestResultLAB220= Lab_Result_RS3 + delimiter1+ "StdThirdLabResultLAB220";
		private	static String ORD3_RS3 = "ORD3_RS3";
		protected static String ORD3_RS3__StdThirdLabReportingFacility= ORD3_RS3 + delimiter1+ "StdThirdLabReportingFacility";
		private	static String LAB165_RS3 = "LAB165_RS3";
		protected static String LAB165_RS3__StdThirdLabSpecimenSourceLAB165= LAB165_RS3 + delimiter1+ "StdThirdLabSpecimenSourceLAB165";
		
		private	static String LAB163_RS4 = "LAB163_RS4";
		protected static String LAB163_RS4__StdFourthLabSpecimenDateLAB164= LAB163_RS4 + delimiter1+ "StdFourthLabSpecimenDateLAB164";
		private	static String LAB220_RS4 = "LAB220_RS4";
		protected static String LAB220_RS4__StdFourthLabTestNameLAB220= LAB220_RS4 + delimiter1+ "StdFourthLabTestNameLAB220";
		private	static String Lab_Result_RS4 = "Lab_Result_RS4";
		protected static String Lab_Result_RS4__StdFourthLabTestResultLAB220= Lab_Result_RS4 + delimiter1+ "StdFourthLabResultLAB220";
		private	static String ORD3_RS4 = "ORD3_RS4";
		protected static String ORD3_RS4__StdFourthLabReportingFacility= ORD3_RS4 + delimiter1+ "StdFourthLabReportingFacility";
		private	static String LAB165_RS4 = "LAB165_RS4";
		protected static String LAB165_RS4__StdFourthLabSpecimenSourceLAB165= LAB165_RS3 + delimiter1+ "StdFourthLabSpecimenSourceLAB165";	
	//HIV Testing (Upper Fields)	
		private	static String NBS262_I2_1_CD = "NBS262_I2_1_CD";
		protected static String NBS262_I2_1_CD__TestedForHivNBS262= NBS262_I2_1_CD + delimiter1+ "TestedForHivNBS262";
		private	static String NBS254_1_CD = "NBS254_1_CD";
		protected static String NBS254_1_CD__PrevTestedForHivNBS254= NBS254_1_CD + delimiter1+ "PrevTestedForHivNBS254";
		private	static String STD106_CD = "STD106_CD";
		protected static String STD106_CD__SelfReportedHivTestResultSTD106= STD106_CD + delimiter1+ "SelfReportedHivTestResultSTD106";
		private	static String NBS259 = "NBS259";
		protected static String NBS259__DateOfSelfReportedHivTestNBS259= NBS259+ delimiter1+ "DateOfSelfReportedHivTestNBS259";
	//HIV Testing (Lab Fields)	
		private	static String LAB163_RH1 = "LAB163_RH1";
		protected static String LAB163_RH1__HivFirstLabSpecimenDateLAB163= LAB163_RH1 + delimiter1+ "HivFirstLabSpecimenDateLAB163";
		private	static String LAB220_RH1 = "LAB220_RH1";
		protected static String LAB220_RH1__HivFirstLabTestNameLAB220= LAB220_RH1 + delimiter1+ "HivFirstLabTestNameLAB220";
		private	static String Lab_Result_RH1 = "Lab_Result_RH1";
		protected static String Lab_Result_RH1__HivFirstLabTestResultLAB220= Lab_Result_RH1 + delimiter1+ "HivFirstLabResultLAB220";
		private	static String ORD3_RH1 = "ORD3_RH1";
		protected static String ORD3_RH1__HivFirstLabReportingFacility= ORD3_RH1 + delimiter1+ "HivFirstLabReportingFacility";
		private	static String LAB165_RH1 = "LAB165_RH1";
		protected static String LAB165_RH1__HivFirstLabSpecimenSourceLAB165= LAB165_RH1 + delimiter1+ "HivFirstLabSpecimenSourceLAB165";
		
		private	static String LAB163_RH2 = "LAB163_RH2";
		protected static String LAB163_RH2__HivSecondLabSpecimenDateLAB163= LAB163_RH2 + delimiter1+ "HivSecondLabSpecimenDateLAB163";
		private	static String LAB220_RH2 = "LAB220_RH2";
		protected static String LAB220_RH2__HivSecondLabTestNameLAB220= LAB220_RH2 + delimiter1+ "HivSecondLabTestNameLAB220";
		private	static String Lab_Result_RH2 = "Lab_Result_RH2";
		protected static String Lab_Result_RH2__HivSecondLabTestResultLAB220= Lab_Result_RH2 + delimiter1+ "HivSecondLabResultLAB220";
		private	static String ORD3_RH2 = "ORD3_RH2";
		protected static String ORD3_RH2__HivSecondLabReportingFacility= ORD3_RH2 + delimiter1+ "HivSecondLabReportingFacility";
		private	static String LAB165_RH2 = "LAB165_RH2";
		protected static String LAB165_RH2__HivSecondLabSpecimenSourceLAB165= LAB165_RH2 + delimiter1+ "HivSecondLabSpecimenSourceLAB165";
		
		private	static String LAB163_RH3 = "LAB163_RH3";
		protected static String LAB163_RH3__HivThirdLabSpecimenDateLAB163= LAB163_RH3 + delimiter1+ "HivThirdLabSpecimenDateLAB163";
		private	static String LAB220_RH3 = "LAB220_RH3";
		protected static String LAB220_RH3__HivThirdLabTestNameLAB220= LAB220_RH3 + delimiter1+ "HivThirdLabTestNameLAB220";
		private	static String Lab_Result_RH3 = "Lab_Result_RH3";
		protected static String Lab_Result_RH3__HivThirdLabTestResultLAB220= Lab_Result_RH3 + delimiter1+ "HivThirdLabResultLAB220";
		private	static String ORD3_RH3 = "ORD3_RH3";
		protected static String ORD3_RH3__HivThirdLabReportingFacility= ORD3_RH3 + delimiter1+ "HivThirdLabReportingFacility";
		private	static String LAB165_RH3 = "LAB165_RH3";
		protected static String LAB165_RH3__HivThirdLabSpecimenSourceLAB165= LAB165_RH3 + delimiter1+ "HivThirdLabSpecimenSourceLAB165";
				
		
		//Sign Symptom
		private	static String INV272_R1_CD = "INV272_R1_CD";
		protected static String INV272_R1_CD__FirstSignSymptomINV272= INV272_R1_CD + delimiter1+ "SignSymptomINV272";
		private	static String NBS247_R1 = "NBS247_R1";
		protected static String NBS247_R1__FirstSignSymptomOnsetDateNBS247= NBS247_R1 + delimiter1+ "FirstSignSymptomOnsetDateNBS247";
		private	static String STD121_R1_CDT = "STD121_R1_CDT";
		protected static String STD121_R1_CDT__FirstSignSymptomAnatomicSiteSTD121= STD121_R1_CDT + delimiter1+ "FirstSignSymptomAnatomicSiteSTD121";
		private	static String NBS246_R1_CD = "NBS246_R1_CD";
		protected static String NBS246_R1_CD__FirstSignSymptomClinicianObservedNBS246= NBS246_R1_CD + delimiter1+ "FirstSignSymptomFirstSignSymptomClinicianObservedNBS246";
		private	static String NBS249_R1 = "NBS249_R1";
		protected static String NBS249_R1__FirstSignSymptomDurationDaysNBS249= NBS249_R1 + delimiter1+ "FirstSignSymptomDurationDaysNBS249";
		
		private	static String INV272_R2_CD = "INV272_R2_CD";
		protected static String INV272_R2_CD__SecondSignSymptomINV272= INV272_R2_CD + delimiter1+ "SignSymptomINV272";
		private	static String NBS247_R2 = "NBS247_R2";
		protected static String NBS247_R2__SecondSignSymptomOnsetDateNBS247= NBS247_R2 + delimiter1+ "SecondSignSymptomOnsetDateNBS247";
		private	static String STD121_R2_CDT = "STD121_R2_CDT";
		protected static String STD121_R2_CDT__SecondSignSymptomAnatomicSiteSTD121= STD121_R2_CDT + delimiter1+ "SecondSignSymptomAnatomicSiteSTD121";
		private	static String NBS246_R2_CD = "NBS246_R2_CD";
		protected static String NBS246_R2_CD__SecondSignSymptomClinicianObservedNBS246= NBS246_R2_CD + delimiter1+ "SecondSignSymptomFirstSignSymptomClinicianObservedNBS246";
		private	static String NBS249_R2 = "NBS249_R2";
		protected static String NBS249_R2__SecondSignSymptomDurationDaysNBS249= NBS249_R2 + delimiter1+ "SecondSignSymptomDurationDaysNBS249";
		
		private	static String INV272_R3_CD = "INV272_R3_CD";
		protected static String INV272_R3_CD__ThirdSignSymptomINV272= INV272_R3_CD + delimiter1+ "SignSymptomINV272";
		private	static String NBS247_R3 = "NBS247_R3";
		protected static String NBS247_R3__ThirdSignSymptomOnsetDateNBS247= NBS247_R3 + delimiter1+ "ThirdSignSymptomOnsetDateNBS247";
		private	static String STD121_R3_CDT = "STD121_R3_CDT";
		protected static String STD121_R3_CDT__ThirdSignSymptomAnatomicSiteSTD121= STD121_R3_CDT + delimiter1+ "ThirdSignSymptomAnatomicSiteSTD121";
		private	static String NBS246_R3_CD = "NBS246_R3_CD";
		protected static String NBS246_R3_CD__ThirdSignSymptomClinicianObservedNBS246= NBS246_R3_CD + delimiter1+ "ThirdSignSymptomFirstSignSymptomClinicianObservedNBS246";
		private	static String NBS249_R3 = "NBS249_R3";
		protected static String NBS249_R3__ThirdSignSymptomDurationDaysNBS249= NBS249_R3 + delimiter1 + "ThirdSignSymptomDurationDaysNBS249";
		
		
		
		private	static String STD117_1_CD = "STD117_1_CD";
		protected	static String STD117_1_CD__PreviousStdHistorySTD117 = STD117_1_CD  + delimiter1 + "PreviousStdHistorySTD117";
		private	static String NBS250_R1_1_CD = "NBS250_R1_1_CD";
		protected	static String NBS250_R1_1_CD__FirstPreviousStdConditionNBS250 = NBS250_R1_1_CD  + delimiter1 + "FirstPreviousStdConditionNBS250";
		private	static String NBS251_R1_1 = "NBS251_R1_1";
		protected	static String NBS251_R1_1__FirstPreviousStdDiagnosisDateNBS251 = NBS251_R1_1 + delimiter1 + "FirstPreviousStdDiagnosisDateNBS251";
		private	static String NBS252_R1_1 = "NBS252_R1_1";
		protected	static String NBS252_R1_1__FirstPreviousStdTreatmentDateNBS252 = NBS252_R1_1 + delimiter1 + "FirstPreviousStdTreatmentDateNBS252";
		private	static String NBS253_R1_1_CD = "NBS253_R1_1_CD";
		protected	static String NBS253_R1_1_CD__FirstPreviousStdConfirmedNBS253 = NBS253_R1_1_CD + delimiter1 + "FirstPreviousStdConfirmedNBS253";

		private	static String NBS250_R2_1_CD = "NBS250_R2_1_CD";
		protected	static String NBS250_R2_1_CD__SecondPreviousStdConditionNBS250 = NBS250_R2_1_CD  + delimiter1 + "SecondPreviousStdConditionNBS250";
		private	static String NBS251_R2_1 = "NBS251_R2_1";
		protected	static String NBS251_R2_1__SecondPreviousStdDiagnosisDateNBS251 = NBS251_R2_1 + delimiter1 + "SecondPreviousStdDiagnosisDateNBS251";
		private	static String NBS252_R2_1 = "NBS252_R2_1";
		protected	static String NBS252_R2_1__SecondPreviousStdTreatmentDateNBS252 = NBS252_R2_1 + delimiter1 + "SecondPreviousStdTreatmentDateNBS252";
		private	static String NBS253_R2_1_CD = "NBS253_R2_1_CD";
		protected	static String NBS253_R2_1_CD__SecondPreviousStdConfirmedNBS253 = NBS253_R2_1_CD + delimiter1 + "SecondPreviousStdConfirmedNBS253";		
		
		private	static String NBS250_R3_1_CD = "NBS250_R3_1_CD";
		protected	static String NBS250_R3_1_CD__ThirdPreviousStdConditionNBS250 = NBS250_R3_1_CD  + delimiter1 + "ThirdPreviousStdConditionNBS250";
		private	static String NBS251_R3_1 = "NBS251_R3_1";
		protected	static String NBS251_R3_1__ThirdPreviousStdDiagnosisDateNBS251 = NBS251_R3_1 + delimiter1 + "ThirdPreviousStdDiagnosisDateNBS251";
		private	static String NBS252_R3_1 = "NBS252_R3_1";
		protected	static String NBS252_R3_1__ThirdPreviousStdTreatmentDateNBS252 = NBS252_R3_1 + delimiter1 + "ThirdPreviousStdTreatmentDateNBS252";
		private	static String NBS253_R3_1_CD = "NBS253_R3_1_CD";
		protected	static String NBS253_R3_1_CD__ThirdPreviousStdConfirmedNBS253 = NBS253_R3_1_CD + delimiter1 + "ThirdPreviousStdConfirmedNBS253";	
		
	//STD/HIV Treatment/Counseling
		
		private	static String TR101_R1 = "TR101_R1";
		protected static String TR101_1__FirstTreatmentTRT101_TRT100_TRT101_TRT104_OrderingProviderName= TR101_R1 + delimiter1+ "FirstTreatmentTRT101+TRT100+TRT101+TRT104/OrderingProviderName";	
		private	static String TR101_R2 = "TR101_R2";
		protected static String TR101_2__SecondTreatmentTRT101_TRT100_TRT101_TRT104_OrderingProviderName= TR101_R2 + delimiter1+ "SecondTreatmentTRT101+TRT100+TRT101+TRT104/OrderingProviderName";	
		private	static String TR101_R3 = "TR101_R3";
		protected static String TR101_3__ThirdTreatmentTRT101_TRT100_TRT101_TRT104_OrderingProviderName = TR101_R3 + delimiter1+ "ThirdTreatmentTRT101+TRT100+TRT101+TRT104/OrderingProviderName";	
		private	static String TR101_R4 = "TR101_R4";
		protected static String TR101_4__FourthTreatmentTRT101_TRT100_TRT101_TRT104_OrderingProviderName= TR101_R4 + delimiter1+ "ThirdTreatmentTRT101+TRT100+TRT101+TRT104/OrderingProviderName";
	//Treatment Comments - not collected in NBS
	//Incidental Antibiotic Treatment - not collected in NBS
		
		private	static String NBS255_1_CD = "NBS255_1_CD";
		protected static String NBS255_1_CD__AntiViralTherapyLast12Months= NBS255_1_CD + delimiter1 + "AntiViralTherapyLast12MonthsNBS255";
		private	static String NBS256_1_CD = "NBS256_1_CD";
		protected static String NBS256_1_CD__AntiViralTherapyEver= NBS256_1_CD + delimiter1 + "AntiViralTherapyEverNBS256";
		
		//we'll let the common code retrieve and translate these next 2 and then translate them back from 0-1 to N/Y.
		private	static String NBS265_1_CD = "NBS265_1_CD";
		private	static String NBS265_I2_1_CD = "NBS265_I2_1_CD";
		protected static String NBS265_1_CD__ResultsProvidedToPartner = NBS265_1_CD + delimiter1 + "ResultsProvidedToPartnerNBS265"; //Y/N
		
		private	static String NBS266_1_CD = "NBS266_1_CD";
		private	static String NBS266_I2_1_CD = "NBS266_I2_1_CD";
		protected static String NBS266_1_CD__ReferForCare = NBS266_1_CD + delimiter1 + "ReferForCareNBS266";
		
		private	static String NBS267_CD = "NBS267_CD";
		protected static String NBS267_CD__KeepAppointment = NBS267_CD + delimiter1 + "KeepAppointmentNBS267";

//Page 4
		
	//Partner, Social Contact and Associate Information
	  //Contact One
		//Line 1
		private	static String DEM102_CT_R1_1 = "DEM102_CT_R1_1";
		protected static String DEM102_CT_R1_1__ContactLastName = DEM102_CT_R1_1 +  delimiter1 + "ContactLastNameDem102";
		private	static String DEM104_CT_R1_1 = "DEM104_CT_R1_1";
		protected static String DEM104_CT_R1_1__ContactFirstName = DEM104_CT_R1_1 +  delimiter1 + "ContactFirstNameDem104";		
		private	static String DEM250_CT_R1_1 = "DEM250_CT_R1_1";
		protected static String DEM250_CT_R1_1__ContactAliasName = DEM250_CT_R1_1 +  delimiter1 + "ContactAliasNameDem250";	
		
		private	static String INV107_CT_R1_1 = "INV107_CT_R1_1";
		protected static String INV107_CT_R1_1__ContactJurisdiction = INV107_CT_R1_1 +  delimiter1 + "ContactJurisdictionINV107";
		//Line 2
		private	static String CON144_CR_R1_1_CD = "CON144_CR_R1_1_CD";
		protected static String CON144_CR_R1_1_CD__ContactReferralBasis = CON144_CR_R1_1_CD +  delimiter1 + "ContactReferralBasisCON144";
		private	static String FirstExp_CR_R1_1 = "FirstExp_CR_R1_1";
		protected static String FirstExp_CR_R1_1__ContactFirstExposureDate = FirstExp_CR_R1_1 +  delimiter1 + "ContactFirstExposureDateNBS118_NBS121";
		private	static String FreqExp_CR_R1_1 = "FreqExp_CR_R1_1";
		protected static String FreqExp_CR_R1_1__ContactFirstExposureFrequency = FreqExp_CR_R1_1 +  delimiter1 + "ContactFirstExposureDateNBS119pipeNBS122";
		private	static String LastExp_CR_R1_1 = "LastExp_CR_R1_1";
		protected static String LastExp_CR_R1_1__ContactLastExposureDate = LastExp_CR_R1_1 +  delimiter1 + "ContactLastExposureDateNBS120_NBS123";
		//contact gender
		private	static String DEM113_CT_R1_1_CD = "DEM113_CT_R1_1_CD";
		protected static String DEM113_CT_R1_1_CD__ContactGender = DEM113_CT_R1_1_CD + delimiter1 + "ContactGenderDEM113";
		private	static String INV178_CT_R1_1_CD = "INV178_CT_R1_1_CD";
		protected static String INV178_CT_R1_1_CD__ContactPregnant = INV178_CT_R1_1_CD + delimiter1 + "ContactPregnantINV178";
		private	static String NBS125_CR_R1_1_CDT = "NBS125_CR_R1_1_CDT";
		protected static String NBS125_CR_R1_1_CDT__ContactOpSpouse = NBS125_CR_R1_1_CDT + delimiter1 + "ContactOpSpouseNBS125";
		//Contact Primary Investigation  - Condition 1 
		private	static String IXS101_CR_IR_R1_1 = "IXS101_CR_IR_R1_1";
		protected static String IXS101_CR_IR_R1_1__ContactPrimaryInvInterviewDate = IXS101_CR_IR_R1_1 + delimiter1 + "ContactPrimaryConditionInterviewDateIXS101";
		private	static String INV147_CT_R1_1 = "INV147_CT_R1_1";
		protected static String INV147_CR_IR_R1_1__ContactPrimaryInvStartDate = INV147_CT_R1_1 + delimiter1 + "ContactPrimaryInvStartDateINV147";
		private	static String IXS102_CR_IR_R1_1_CD = "IXS102_CR_IR_R1_1_CD";
		protected static String IXS102_CR_IR_R1_1_CD__ContactPrimaryInvNamedDuringInterviewer = IXS102_CR_IR_R1_1_CD + delimiter1 + "ContactPrimaryInvNamedDuringInterviewerIXS102";
		private	static String IXS105_CR_IR_R1_1_CDT = "IXS105_CR_IR_R1_1_CDT";
		protected static String IXS105_CR_IR_R1_1_CDT__ContactPrimaryInvNamedDuringInterviewType = IXS105_CR_IR_R1_1_CDT + delimiter1 + "ContactPrimaryInvNamedDuringInterviewTypeIXS105";
		private	static String NBS177_CT_R1_1_CD = "NBS177_CT_R1_1_CD";
		protected static String NBS177_CT_R1_1_CD__ContactPrimaryInvActualReferralType = NBS177_CT_R1_1_CD  + delimiter1 + "ContactPrimaryInvActualReferralTypeNBS177";
		private	static String NBS160_CT_R1_1= "NBS160_CT_R1_1";
		protected static String NBS160_CT_R1_1__ContactPrimaryInvFieldRecordNbr = NBS160_CT_R1_1  + delimiter1 + "ContactPrimaryInvFieldRecordNbrNBS160";
		private	static String NBS173_CT_R1_1_CD = "NBS173_CT_R1_1_CD";
		protected static String NBS173_CT_R1_1_CD__ContactPrimaryInvDisposition = NBS173_CT_R1_1_CD  + delimiter1 + "ContactPrimaryInvDispositionNBS173";
		private	static String NBS174_CT_R1_1 = "NBS174_CT_R1_1";
		protected static String NBS174_CT_R1_1__ContactPrimaryInvDispositionDate = NBS174_CT_R1_1  + delimiter1 + "ContactPrimaryInvDispositionDateNBS174";
		private	static String NBS136_CT_R1_1_CD = "NBS136_CT_R1_1_CD";
		protected static String NBS136_CT_R1_1_CD__ContactPrimaryInvCaseDiagnosis = NBS136_CT_R1_1_CD + delimiter1 + "ContactPrimaryInvCaseDiagnosisNBS136";
		private	static String NBS175_CT_R1_1_CD = "NBS175_CT_R1_1_CD";
		protected static String NBS175_CT_R1_1_CD__ContactPrimaryInvDispoBy = NBS175_CT_R1_1_CD + delimiter1 + "ContactPrimaryInvDispoByNBS175";
		private	static String NBS135_CT_R1_1_CD = "NBS135_CT_R1_1_CD";
		protected static String NBS135_CT_R1_1_CD__ContactPrimaryInvSourceOrSpread = NBS135_CT_R1_1_CD + delimiter1 + "ContactPrimaryInvSourceOrSpreadNBS135";
		//Contact Co-Infection Investigation  - Condition 2 
		private	static String IXS101_CR_IR_R1_2 = "IXS101_CR_IR_R1_2";
		protected static String IXS101_CR_IR_R1_2__ContactCoinfectionInvInterviewDate = IXS101_CR_IR_R1_2 + delimiter1 + "ContactCoinfectionConditionInterviewDateIXS101";
		private	static String INV147_CT_R1_2 = "INV147_CT_R1_2";
		protected static String IXS101_CR_IR_R1_2__ContactCoinfectionInvStartDate = INV147_CT_R1_2 + delimiter1 + "ContactCoinfectionInvStartDateINV147";
		private	static String IXS102_CR_IR_R1_2_CD = "IXS102_CR_IR_R1_2_CD";
		protected static String IXS102_CR_IR_R1_2_CD__ContactCoinfectionInvNamedDuringInterviewer = IXS102_CR_IR_R1_2_CD + delimiter1 + "ContactCoinfectionInvNamedDuringInterviewerIXS102";
		private	static String IXS105_CR_IR_R1_2_CDT = "IXS105_CR_IR_R1_2_CDT";
		protected static String IXS105_CR_IR_R1_2_CDT__ContactCoinfectionInvNamedDuringInterviewType = IXS105_CR_IR_R1_2_CDT + delimiter1 + "ContactCoinfectionInvNamedDuringInterviewTypeIXS105";
		private	static String NBS177_CT_R1_2_CD = "NBS177_CT_R1_2_CD";
		protected static String NBS177_CT_R1_2_CD__ContactCoinfectionInvActualReferralType = NBS177_CT_R1_2_CD  + delimiter1 + "ContactCoinfectionInvActualReferralTypeNBS177";
		private	static String NBS160_CT_R1_2= "NBS160_CT_R1_2";
		protected static String NBS160_CT_R1_2__ContactCoinfectionInvFieldRecordNbr = NBS160_CT_R1_2  + delimiter1 + "ContactCoinfectionInvFieldRecordNbrNBS160";
		private	static String NBS173_CT_R1_2_CD = "NBS173_CT_R1_2_CD";
		protected static String NBS173_CT_R1_2_CD__ContactCoinfectionInvDisposition = NBS173_CT_R1_2_CD  + delimiter1 + "ContactCoinfectionInvDispositionNBS173";
		private	static String NBS174_CT_R1_2 = "NBS174_CT_R1_2";
		protected static String NBS174_CT_R1_2__ContactCoinfectionInvDispositionDate = NBS174_CT_R1_2  + delimiter1 + "ContactCoinfectionInvDispositionDateNBS174";
		private	static String NBS136_CT_R1_2_CD = "NBS136_CT_R1_2_CD";
		protected static String NBS136_CT_R1_2_CD__ContactCoinfectionInvCaseDiagnosis = NBS136_CT_R1_2_CD + delimiter1 + "ContactCoinfectionInvCaseDiagnosisNBS136";
		private	static String NBS175_CT_R1_2_CD = "NBS175_CT_R1_2_CD";
		protected static String NBS175_CT_R1_2_CD__ContactCoinfectionInvDispoBy = NBS175_CT_R1_2_CD + delimiter1 + "ContactCoinfectionInvDispoByNBS175";
		private	static String NBS135_CT_R1_2_CD = "NBS135_CT_R1_2_CD";
		protected static String NBS135_CT_R1_2_CD__ContactCoinfectionInvSourceOrSpread = NBS135_CT_R1_2_CD + delimiter1 + "ContactCoinfectionInvSourceOrSpreadNBS135";
     //Contact Two
	   //Line 1		
		private	static String DEM102_CT_R2_1 = "DEM102_CT_R2_1";
		protected static String DEM102_CT_R2_1__ContactLastName = DEM102_CT_R2_1 +  delimiter1 + "ContactLastNameDem102";
		private	static String DEM104_CT_R2_1 = "DEM104_CT_R2_1";
		protected static String DEM104_CT_R2_1__ContactFirstName = DEM104_CT_R2_1 +  delimiter1 + "ContactFirstNameDem104";		
		private	static String DEM250_CT_R2_1 = "DEM250_CT_R2_1";
		protected static String DEM250_CT_R2_1__ContactAliasName = DEM250_CT_R2_1 +  delimiter1 + "ContactAliasNameDem250";	
		
		private	static String INV107_CT_R2_1 = "INV107_CT_R2_1";
		protected static String INV107_CT_R2_1__ContactJurisdiction = INV107_CT_R2_1 +  delimiter1 + "ContactJurisdictionINV107";
		//Line 2
		private	static String CON144_CR_R2_1_CD = "CON144_CR_R2_1_CD";
		protected static String CON144_CR_R2_1_CD__ContactReferralBasis = CON144_CR_R2_1_CD +  delimiter1 + "ContactReferralBasisCON144";
		private	static String FirstExp_CR_R2_1 = "FirstExp_CR_R2_1";
		protected static String FirstExp_CR_R2_1__ContactFirstExposureDate = FirstExp_CR_R2_1 +  delimiter1 + "ContactFirstExposureDateNBS118_NBS121";
		private	static String FreqExp_CR_R2_1 = "FreqExp_CR_R2_1";
		protected static String FreqExp_CR_R2_1__ContactFirstExposureFrequency = FreqExp_CR_R2_1 +  delimiter1 + "ContactFirstExposureDateNBS119pipeNBS122";
		private	static String LastExp_CR_R2_1 = "LastExp_CR_R2_1";
		protected static String LastExp_CR_R2_1__ContactLastExposureDate = LastExp_CR_R2_1 +  delimiter1 + "ContactLastExposureDateNBS120_NBS123";
		//contact gender
		private	static String DEM113_CT_R2_1_CD = "DEM113_CT_R2_1_CD";
		protected static String DEM113_CT_R2_1_CD__ContactGender = DEM113_CT_R2_1_CD + delimiter1 + "ContactGenderDEM113";
		private	static String INV178_CT_R2_1_CD = "INV178_CT_R2_1_CD";
		protected static String INV178_CT_R2_1_CD__ContactPregnant = INV178_CT_R2_1_CD + delimiter1 + "ContactPregnantINV178";
		private	static String NBS125_CR_R2_1_CDT = "NBS125_CR_R2_1_CDT";
		protected static String NBS125_CR_R2_1_CDT__ContactOpSpouse = NBS125_CR_R2_1_CDT + delimiter1 + "ContactOpSpouseNBS125";
		//Contact Primary Investigation  - Condition 1 
		private	static String IXS101_CR_IR_R2_1 = "IXS101_CR_IR_R2_1";
		protected static String IXS101_CR_IR_R2_1__ContactPrimaryInvInterviewDate = IXS101_CR_IR_R2_1 + delimiter1 + "ContactPrimaryConditionInterviewDateIXS101";
		private	static String INV147_CT_R2_1 = "INV147_CT_R2_1";
		protected static String INV147_CR_IR_R2_1__ContactPrimaryInvStartDate = INV147_CT_R2_1 + delimiter1 + "ContactPrimaryInvStartDateINV147";
		private	static String IXS102_CR_IR_R2_1_CD = "IXS102_CR_IR_R2_1_CD";
		protected static String IXS102_CR_IR_R2_1_CD__ContactPrimaryInvNamedDuringInterviewer = IXS102_CR_IR_R2_1_CD + delimiter1 + "ContactPrimaryInvNamedDuringInterviewerIXS102";
		private	static String IXS105_CR_IR_R2_1_CDT = "IXS105_CR_IR_R2_1_CDT";
		protected static String IXS105_CR_IR_R2_1_CDT__ContactPrimaryInvNamedDuringInterviewType = IXS105_CR_IR_R2_1_CDT + delimiter1 + "ContactPrimaryInvNamedDuringInterviewTypeIXS105";
		private	static String NBS177_CT_R2_1_CD = "NBS177_CT_R2_1_CD";
		protected static String NBS177_CT_R2_1_CD__ContactPrimaryInvActualReferralType = NBS177_CT_R2_1_CD  + delimiter1 + "ContactPrimaryInvActualReferralTypeNBS177";
		private	static String NBS160_CT_R2_1= "NBS160_CT_R2_1";
		protected static String NBS160_CT_R2_1__ContactPrimaryInvFieldRecordNbr = NBS160_CT_R2_1  + delimiter1 + "ContactPrimaryInvFieldRecordNbrNBS160";
		private	static String NBS173_CT_R2_1_CD = "NBS173_CT_R2_1_CD";
		protected static String NBS173_CT_R2_1_CD__ContactPrimaryInvDisposition = NBS173_CT_R2_1_CD  + delimiter1 + "ContactPrimaryInvDispositionNBS173";
		private	static String NBS174_CT_R2_1 = "NBS174_CT_R2_1";
		protected static String NBS174_CT_R2_1__ContactPrimaryInvDispositionDate = NBS174_CT_R2_1  + delimiter1 + "ContactPrimaryInvDispositionDateNBS174";
		private	static String NBS136_CT_R2_1_CD = "NBS136_CT_R2_1_CD";
		protected static String NBS136_CT_R2_1_CD__ContactPrimaryInvCaseDiagnosis = NBS136_CT_R2_1_CD + delimiter1 + "ContactPrimaryInvCaseDiagnosisNBS136";
		private	static String NBS175_CT_R2_1_CD = "NBS175_CT_R2_1_CD";
		protected static String NBS175_CT_R2_1_CD__ContactPrimaryInvDispoBy = NBS175_CT_R2_1_CD + delimiter1 + "ContactPrimaryInvDispoByNBS175";
		private	static String NBS135_CT_R2_1_CD = "NBS135_CT_R2_1_CD";
		protected static String NBS135_CT_R2_1_CD__ContactPrimaryInvSourceOrSpread = NBS135_CT_R2_1_CD + delimiter1 + "ContactPrimaryInvSourceOrSpreadNBS135";
		//Contact Co-Infection Investigation  - Condition 2 
		private	static String IXS101_CR_IR_R2_2 = "IXS101_CR_IR_R2_2";
		protected static String IXS101_CR_IR_R2_2__ContactCoinfectionInvInterviewDate = IXS101_CR_IR_R2_2 + delimiter1 + "ContactCoinfectionConditionInterviewDateIXS101";
		private	static String INV147_CT_R2_2 = "INV147_CT_R2_2";
		protected static String IXS101_CR_IR_R2_2__ContactCoinfectionInvStartDate = INV147_CT_R2_2 + delimiter1 + "ContactCoinfectionInvStartDateINV147";
		private	static String IXS102_CR_IR_R2_2_CD = "IXS102_CR_IR_R2_2_CD";
		protected static String IXS102_CR_IR_R2_2_CD__ContactCoinfectionInvNamedDuringInterviewer = IXS102_CR_IR_R2_2_CD + delimiter1 + "ContactCoinfectionInvNamedDuringInterviewerIXS102";
		private	static String IXS105_CR_IR_R2_2_CDT = "IXS105_CR_IR_R2_2_CDT";
		protected static String IXS105_CR_IR_R2_2_CDT__ContactCoinfectionInvNamedDuringInterviewType = IXS105_CR_IR_R2_2_CDT + delimiter1 + "ContactCoinfectionInvNamedDuringInterviewTypeIXS105";
		private	static String NBS177_CT_R2_2_CD = "NBS177_CT_R2_2_CD";
		protected static String NBS177_CT_R2_2_CD__ContactCoinfectionInvActualReferralType = NBS177_CT_R2_2_CD  + delimiter1 + "ContactCoinfectionInvActualReferralTypeNBS177";
		private	static String NBS160_CT_R2_2= "NBS160_CT_R2_2";
		protected static String NBS160_CT_R2_2__ContactCoinfectionInvFieldRecordNbr = NBS160_CT_R2_2  + delimiter1 + "ContactCoinfectionInvFieldRecordNbrNBS160";
		private	static String NBS173_CT_R2_2_CD = "NBS173_CT_R2_2_CD";
		protected static String NBS173_CT_R2_2_CD__ContactCoinfectionInvDisposition = NBS173_CT_R2_2_CD  + delimiter1 + "ContactCoinfectionInvDispositionNBS173";
		private	static String NBS174_CT_R2_2 = "NBS174_CT_R2_2";
		protected static String NBS174_CT_R2_2__ContactCoinfectionInvDispositionDate = NBS174_CT_R2_2  + delimiter1 + "ContactCoinfectionInvDispositionDateNBS174";
		private	static String NBS136_CT_R2_2_CD = "NBS136_CT_R2_2_CD";
		protected static String NBS136_CT_R2_2_CD__ContactCoinfectionInvCaseDiagnosis = NBS136_CT_R2_2_CD + delimiter1 + "ContactCoinfectionInvCaseDiagnosisNBS136";
		private	static String NBS175_CT_R2_2_CD = "NBS175_CT_R2_2_CD";
		protected static String NBS175_CT_R2_2_CD__ContactCoinfectionInvDispoBy = NBS175_CT_R2_2_CD + delimiter1 + "ContactCoinfectionInvDispoByNBS175";
		private	static String NBS135_CT_R2_2_CD = "NBS135_CT_R2_2_CD";
		protected static String NBS135_CT_R2_2_CD__ContactCoinfectionInvSourceOrSpread = NBS135_CT_R2_2_CD + delimiter1 + "ContactCoinfectionInvSourceOrSpreadNBS135";
	//Contact Three
		//Line 1
		private	static String DEM102_CT_R3_1 = "DEM102_CT_R3_1";
		protected static String DEM102_CT_R3_1__ContactLastName = DEM102_CT_R3_1 +  delimiter1 + "ContactLastNameDem102";
		private	static String DEM104_CT_R3_1 = "DEM104_CT_R3_1";
		protected static String DEM104_CT_R3_1__ContactFirstName = DEM104_CT_R3_1 +  delimiter1 + "ContactFirstNameDem104";		
		private	static String DEM250_CT_R3_1 = "DEM250_CT_R3_1";
		protected static String DEM250_CT_R3_1__ContactAliasName = DEM250_CT_R3_1 +  delimiter1 + "ContactAliasNameDem250";	
		
		private	static String INV107_CT_R3_1 = "INV107_CT_R3_1";
		protected static String INV107_CT_R3_1__ContactJurisdiction = INV107_CT_R3_1 +  delimiter1 + "ContactJurisdictionINV107";
		//Line 2
		private	static String CON144_CR_R3_1_CD = "CON144_CR_R3_1_CD";
		protected static String CON144_CR_R3_1_CD__ContactReferralBasis = CON144_CR_R3_1_CD +  delimiter1 + "ContactReferralBasisCON144";
		private	static String FirstExp_CR_R3_1 = "FirstExp_CR_R3_1";
		protected static String FirstExp_CR_R3_1__ContactFirstExposureDate = FirstExp_CR_R3_1 +  delimiter1 + "ContactFirstExposureDateNBS118_NBS121";
		private	static String FreqExp_CR_R3_1 = "FreqExp_CR_R3_1";
		protected static String FreqExp_CR_R3_1__ContactFirstExposureFrequency = FreqExp_CR_R3_1 +  delimiter1 + "ContactFirstExposureDateNBS119pipeNBS122";
		private	static String LastExp_CR_R3_1 = "LastExp_CR_R3_1";
		protected static String LastExp_CR_R3_1__ContactLastExposureDate = LastExp_CR_R3_1 +  delimiter1 + "ContactLastExposureDateNBS120_NBS123";
		//contact gender
		private	static String DEM113_CT_R3_1_CD = "DEM113_CT_R3_1_CD";
		protected static String DEM113_CT_R3_1_CD__ContactGender = DEM113_CT_R3_1_CD + delimiter1 + "ContactGenderDEM113";
		private	static String INV178_CT_R3_1_CD = "INV178_CT_R3_1_CD";
		protected static String INV178_CT_R3_1_CD__ContactPregnant = INV178_CT_R3_1_CD + delimiter1 + "ContactPregnantINV178";
		private	static String NBS125_CR_R3_1_CDT = "NBS125_CR_R3_1_CDT";
		protected static String NBS125_CR_R3_1_CDT__ContactOpSpouse = NBS125_CR_R3_1_CDT + delimiter1 + "ContactOpSpouseNBS125";
		//Contact Primary Investigation  - Condition 1 
		private	static String IXS101_CR_IR_R3_1 = "IXS101_CR_IR_R3_1";
		protected static String IXS101_CR_IR_R3_1__ContactPrimaryInvInterviewDate = IXS101_CR_IR_R3_1 + delimiter1 + "ContactPrimaryConditionInterviewDateIXS101";
		private	static String INV147_CT_R3_1 = "INV147_CT_R3_1";
		protected static String INV147_CR_IR_R3_1__ContactPrimaryInvStartDate = INV147_CT_R3_1 + delimiter1 + "ContactPrimaryInvStartDateINV147";
		private	static String IXS102_CR_IR_R3_1_CD = "IXS102_CR_IR_R3_1_CD";
		protected static String IXS102_CR_IR_R3_1_CD__ContactPrimaryInvNamedDuringInterviewer = IXS102_CR_IR_R3_1_CD + delimiter1 + "ContactPrimaryInvNamedDuringInterviewerIXS102";
		private	static String IXS105_CR_IR_R3_1_CDT = "IXS105_CR_IR_R3_1_CDT";
		protected static String IXS105_CR_IR_R3_1_CDT__ContactPrimaryInvNamedDuringInterviewType = IXS105_CR_IR_R3_1_CDT + delimiter1 + "ContactPrimaryInvNamedDuringInterviewTypeIXS105";
		private	static String NBS177_CT_R3_1_CD = "NBS177_CT_R3_1_CD";
		protected static String NBS177_CT_R3_1_CD__ContactPrimaryInvActualReferralType = NBS177_CT_R3_1_CD  + delimiter1 + "ContactPrimaryInvActualReferralTypeNBS177";
		private	static String NBS160_CT_R3_1= "NBS160_CT_R3_1";
		protected static String NBS160_CT_R3_1__ContactPrimaryInvFieldRecordNbr = NBS160_CT_R3_1  + delimiter1 + "ContactPrimaryInvFieldRecordNbrNBS160";
		private	static String NBS173_CT_R3_1_CD = "NBS173_CT_R3_1_CD";
		protected static String NBS173_CT_R3_1_CD__ContactPrimaryInvDisposition = NBS173_CT_R3_1_CD  + delimiter1 + "ContactPrimaryInvDispositionNBS173";
		private	static String NBS174_CT_R3_1 = "NBS174_CT_R3_1";
		protected static String NBS174_CT_R3_1__ContactPrimaryInvDispositionDate = NBS174_CT_R3_1  + delimiter1 + "ContactPrimaryInvDispositionDateNBS174";
		private	static String NBS136_CT_R3_1_CD = "NBS136_CT_R3_1_CD";
		protected static String NBS136_CT_R3_1_CD__ContactPrimaryInvCaseDiagnosis = NBS136_CT_R3_1_CD + delimiter1 + "ContactPrimaryInvCaseDiagnosisNBS136";
		private	static String NBS175_CT_R3_1_CD = "NBS175_CT_R3_1_CD";
		protected static String NBS175_CT_R3_1_CD__ContactPrimaryInvDispoBy = NBS175_CT_R3_1_CD + delimiter1 + "ContactPrimaryInvDispoByNBS175";
		private	static String NBS135_CT_R3_1_CD = "NBS135_CT_R3_1_CD";
		protected static String NBS135_CT_R3_1_CD__ContactPrimaryInvSourceOrSpread = NBS135_CT_R3_1_CD + delimiter1 + "ContactPrimaryInvSourceOrSpreadNBS135";
		//Contact Co-Infection Investigation  - Condition 2 
		private	static String IXS101_CR_IR_R3_2 = "IXS101_CR_IR_R3_2";
		protected static String IXS101_CR_IR_R3_2__ContactCoinfectionInvInterviewDate = IXS101_CR_IR_R3_2 + delimiter1 + "ContactCoinfectionConditionInterviewDateIXS101";
		private	static String INV147_CT_R3_2 = "INV147_CT_R3_2";
		protected static String IXS101_CR_IR_R3_2__ContactCoinfectionInvStartDate = INV147_CT_R3_2 + delimiter1 + "ContactCoinfectionInvStartDateINV147";
		private	static String IXS102_CR_IR_R3_2_CD = "IXS102_CR_IR_R3_2_CD";
		protected static String IXS102_CR_IR_R3_2_CD__ContactCoinfectionInvNamedDuringInterviewer = IXS102_CR_IR_R3_2_CD + delimiter1 + "ContactCoinfectionInvNamedDuringInterviewerIXS102";
		private	static String IXS105_CR_IR_R3_2_CDT = "IXS105_CR_IR_R3_2_CDT";
		protected static String IXS105_CR_IR_R3_2_CDT__ContactCoinfectionInvNamedDuringInterviewType = IXS105_CR_IR_R3_2_CDT + delimiter1 + "ContactCoinfectionInvNamedDuringInterviewTypeIXS105";
		private	static String NBS177_CT_R3_2_CD = "NBS177_CT_R3_2_CD";
		protected static String NBS177_CT_R3_2_CD__ContactCoinfectionInvActualReferralType = NBS177_CT_R3_2_CD  + delimiter1 + "ContactCoinfectionInvActualReferralTypeNBS177";
		private	static String NBS160_CT_R3_2= "NBS160_CT_R3_2";
		protected static String NBS160_CT_R3_2__ContactCoinfectionInvFieldRecordNbr = NBS160_CT_R3_2  + delimiter1 + "ContactCoinfectionInvFieldRecordNbrNBS160";
		private	static String NBS173_CT_R3_2_CD = "NBS173_CT_R3_2_CD";
		protected static String NBS173_CT_R3_2_CD__ContactCoinfectionInvDisposition = NBS173_CT_R3_2_CD  + delimiter1 + "ContactCoinfectionInvDispositionNBS173";
		private	static String NBS174_CT_R3_2 = "NBS174_CT_R3_2";
		protected static String NBS174_CT_R3_2__ContactCoinfectionInvDispositionDate = NBS174_CT_R3_2  + delimiter1 + "ContactCoinfectionInvDispositionDateNBS174";
		private	static String NBS136_CT_R3_2_CD = "NBS136_CT_R3_2_CD";
		protected static String NBS136_CT_R3_2_CD__ContactCoinfectionInvCaseDiagnosis = NBS136_CT_R3_2_CD + delimiter1 + "ContactCoinfectionInvCaseDiagnosisNBS136";
		private	static String NBS175_CT_R3_2_CD = "NBS175_CT_R3_2_CD";
		protected static String NBS175_CT_R3_2_CD__ContactCoinfectionInvDispoBy = NBS175_CT_R3_2_CD + delimiter1 + "ContactCoinfectionInvDispoByNBS175";
		private	static String NBS135_CT_R3_2_CD = "NBS135_CT_R3_2_CD";
		protected static String NBS135_CT_R3_2_CD__ContactCoinfectionInvSourceOrSpread = NBS135_CT_R3_2_CD + delimiter1 + "ContactCoinfectionInvSourceOrSpreadNBS135";
		
	//Fourth Contact
		//Line 1
		private	static String DEM102_CT_R4_1 = "DEM102_CT_R4_1";
		protected static String DEM102_CT_R4_1__ContactLastName = DEM102_CT_R4_1 +  delimiter1 + "ContactLastNameDem102";
		private	static String DEM104_CT_R4_1 = "DEM104_CT_R4_1";
		protected static String DEM104_CT_R4_1__ContactFirstName = DEM104_CT_R4_1 +  delimiter1 + "ContactFirstNameDem104";		
		private	static String DEM250_CT_R4_1 = "DEM250_CT_R4_1";
		protected static String DEM250_CT_R4_1__ContactAliasName = DEM250_CT_R4_1 +  delimiter1 + "ContactAliasNameDem250";	
		
		private	static String INV107_CT_R4_1 = "INV107_CT_R4_1";
		protected static String INV107_CT_R4_1__ContactJurisdiction = INV107_CT_R4_1 +  delimiter1 + "ContactJurisdictionINV107";
		//Line 2
		private	static String CON144_CR_R4_1_CD = "CON144_CR_R4_1_CD";
		protected static String CON144_CR_R4_1_CD__ContactReferralBasis = CON144_CR_R4_1_CD +  delimiter1 + "ContactReferralBasisCON144";
		private	static String FirstExp_CR_R4_1 = "FirstExp_CR_R4_1";
		protected static String FirstExp_CR_R4_1__ContactFirstExposureDate = FirstExp_CR_R4_1 +  delimiter1 + "ContactFirstExposureDateNBS118_NBS121";
		private	static String FreqExp_CR_R4_1 = "FreqExp_CR_R4_1";
		protected static String FreqExp_CR_R4_1__ContactFirstExposureFrequency = FreqExp_CR_R4_1 +  delimiter1 + "ContactFirstExposureDateNBS119pipeNBS122";
		private	static String LastExp_CR_R4_1 = "LastExp_CR_R4_1";
		protected static String LastExp_CR_R4_1__ContactLastExposureDate = LastExp_CR_R4_1 +  delimiter1 + "ContactLastExposureDateNBS120_NBS123";
		//contact gender
		private	static String DEM113_CT_R4_1_CD = "DEM113_CT_R4_1_CD";
		protected static String DEM113_CT_R4_1_CD__ContactGender = DEM113_CT_R4_1_CD + delimiter1 + "ContactGenderDEM113";
		private	static String INV178_CT_R4_1_CD = "INV178_CT_R4_1_CD";
		protected static String INV178_CT_R4_1_CD__ContactPregnant = INV178_CT_R4_1_CD + delimiter1 + "ContactPregnantINV178";
		private	static String NBS125_CR_R4_1_CDT = "NBS125_CR_R4_1_CDT";
		protected static String NBS125_CR_R4_1_CDT__ContactOpSpouse = NBS125_CR_R4_1_CDT + delimiter1 + "ContactOpSpouseNBS125";
		//Contact Primary Investigation  - Condition 1 
		private	static String IXS101_CR_IR_R4_1 = "IXS101_CR_IR_R4_1";
		protected static String IXS101_CR_IR_R4_1__ContactPrimaryInvInterviewDate = IXS101_CR_IR_R4_1 + delimiter1 + "ContactPrimaryConditionInterviewDateIXS101";
		private	static String INV147_CT_R4_1 = "INV147_CT_R4_1";
		protected static String INV147_CR_IR_R4_1__ContactPrimaryInvStartDate = INV147_CT_R4_1 + delimiter1 + "ContactPrimaryInvStartDateINV147";
		private	static String IXS102_CR_IR_R4_1_CD = "IXS102_CR_IR_R4_1_CD";
		protected static String IXS102_CR_IR_R4_1_CD__ContactPrimaryInvNamedDuringInterviewer = IXS102_CR_IR_R4_1_CD + delimiter1 + "ContactPrimaryInvNamedDuringInterviewerIXS102";
		private	static String IXS105_CR_IR_R4_1_CDT = "IXS105_CR_IR_R4_1_CDT";
		protected static String IXS105_CR_IR_R4_1_CDT__ContactPrimaryInvNamedDuringInterviewType = IXS105_CR_IR_R4_1_CDT + delimiter1 + "ContactPrimaryInvNamedDuringInterviewTypeIXS105";
		private	static String NBS177_CT_R4_1_CD = "NBS177_CT_R4_1_CD";
		protected static String NBS177_CT_R4_1_CD__ContactPrimaryInvActualReferralType = NBS177_CT_R4_1_CD  + delimiter1 + "ContactPrimaryInvActualReferralTypeNBS177";
		private	static String NBS160_CT_R4_1= "NBS160_CT_R4_1";
		protected static String NBS160_CT_R4_1__ContactPrimaryInvFieldRecordNbr = NBS160_CT_R4_1  + delimiter1 + "ContactPrimaryInvFieldRecordNbrNBS160";
		private	static String NBS173_CT_R4_1_CD = "NBS173_CT_R4_1_CD";
		protected static String NBS173_CT_R4_1_CD__ContactPrimaryInvDisposition = NBS173_CT_R4_1_CD  + delimiter1 + "ContactPrimaryInvDispositionNBS173";
		private	static String NBS174_CT_R4_1 = "NBS174_CT_R4_1";
		protected static String NBS174_CT_R4_1__ContactPrimaryInvDispositionDate = NBS174_CT_R4_1  + delimiter1 + "ContactPrimaryInvDispositionDateNBS174";
		private	static String NBS136_CT_R4_1_CD = "NBS136_CT_R4_1_CD";
		protected static String NBS136_CT_R4_1_CD__ContactPrimaryInvCaseDiagnosis = NBS136_CT_R4_1_CD + delimiter1 + "ContactPrimaryInvCaseDiagnosisNBS136";
		private	static String NBS175_CT_R4_1_CD = "NBS175_CT_R4_1_CD";
		protected static String NBS175_CT_R4_1_CD__ContactPrimaryInvDispoBy = NBS175_CT_R4_1_CD + delimiter1 + "ContactPrimaryInvDispoByNBS175";
		private	static String NBS135_CT_R4_1_CD = "NBS135_CT_R4_1_CD";
		protected static String NBS135_CT_R4_1_CD__ContactPrimaryInvSourceOrSpread = NBS135_CT_R4_1_CD + delimiter1 + "ContactPrimaryInvSourceOrSpreadNBS135";
		//Contact Co-Infection Investigation  - Condition 2 
		private	static String IXS101_CR_IR_R4_2 = "IXS101_CR_IR_R4_2";
		protected static String IXS101_CR_IR_R4_2__ContactCoinfectionInvInterviewDate = IXS101_CR_IR_R4_2 + delimiter1 + "ContactCoinfectionConditionInterviewDateIXS101";
		private	static String INV147_CT_R4_2 = "INV147_CT_R4_2";
		protected static String IXS101_CR_IR_R4_2__ContactCoinfectionInvStartDate = INV147_CT_R4_2 + delimiter1 + "ContactCoinfectionInvStartDateINV147";
		private	static String IXS102_CR_IR_R4_2_CD = "IXS102_CR_IR_R4_2_CD";
		protected static String IXS102_CR_IR_R4_2_CD__ContactCoinfectionInvNamedDuringInterviewer = IXS102_CR_IR_R4_2_CD + delimiter1 + "ContactCoinfectionInvNamedDuringInterviewerIXS102";
		private	static String IXS105_CR_IR_R4_2_CDT = "IXS105_CR_IR_R4_2_CDT";
		protected static String IXS105_CR_IR_R4_2_CDT__ContactCoinfectionInvNamedDuringInterviewType = IXS105_CR_IR_R4_2_CDT + delimiter1 + "ContactCoinfectionInvNamedDuringInterviewTypeIXS105";
		private	static String NBS177_CT_R4_2_CD = "NBS177_CT_R4_2_CD";
		protected static String NBS177_CT_R4_2_CD__ContactCoinfectionInvActualReferralType = NBS177_CT_R4_2_CD  + delimiter1 + "ContactCoinfectionInvActualReferralTypeNBS177";
		private	static String NBS160_CT_R4_2= "NBS160_CT_R4_2";
		protected static String NBS160_CT_R4_2__ContactCoinfectionInvFieldRecordNbr = NBS160_CT_R4_2  + delimiter1 + "ContactCoinfectionInvFieldRecordNbrNBS160";
		private	static String NBS173_CT_R4_2_CD = "NBS173_CT_R4_2_CD";
		protected static String NBS173_CT_R4_2_CD__ContactCoinfectionInvDisposition = NBS173_CT_R4_2_CD  + delimiter1 + "ContactCoinfectionInvDispositionNBS173";
		private	static String NBS174_CT_R4_2 = "NBS174_CT_R4_2";
		protected static String NBS174_CT_R4_2__ContactCoinfectionInvDispositionDate = NBS174_CT_R4_2  + delimiter1 + "ContactCoinfectionInvDispositionDateNBS174";
		private	static String NBS136_CT_R4_2_CD = "NBS136_CT_R4_2_CD";
		protected static String NBS136_CT_R4_2_CD__ContactCoinfectionInvCaseDiagnosis = NBS136_CT_R4_2_CD + delimiter1 + "ContactCoinfectionInvCaseDiagnosisNBS136";
		private	static String NBS175_CT_R4_2_CD = "NBS175_CT_R4_2_CD";
		protected static String NBS175_CT_R4_2_CD__ContactCoinfectionInvDispoBy = NBS175_CT_R4_2_CD + delimiter1 + "ContactCoinfectionInvDispoByNBS175";
		private	static String NBS135_CT_R4_2_CD = "NBS135_CT_R4_2_CD";
		protected static String NBS135_CT_R4_2_CD__ContactCoinfectionInvSourceOrSpread = NBS135_CT_R4_2_CD + delimiter1 + "ContactCoinfectionInvSourceOrSpreadNBS135";
	//Fifth Contact
		//Line 1				
		private	static String DEM102_CT_R5_1 = "DEM102_CT_R5_1";
		protected static String DEM102_CT_R5_1__ContactLastName = DEM102_CT_R5_1 +  delimiter1 + "ContactLastNameDem102";
		private	static String DEM104_CT_R5_1 = "DEM104_CT_R5_1";
		protected static String DEM104_CT_R5_1__ContactFirstName = DEM104_CT_R5_1 +  delimiter1 + "ContactFirstNameDem104";		
		private	static String DEM250_CT_R5_1 = "DEM250_CT_R5_1";
		protected static String DEM250_CT_R5_1__ContactAliasName = DEM250_CT_R5_1 +  delimiter1 + "ContactAliasNameDem250";	
		
		private	static String INV107_CT_R5_1 = "INV107_CT_R5_1";
		protected static String INV107_CT_R5_1__ContactJurisdiction = INV107_CT_R5_1 +  delimiter1 + "ContactJurisdictionINV107";
		//Line 2
		private	static String CON144_CR_R5_1_CD = "CON144_CR_R5_1_CD";
		protected static String CON144_CR_R5_1_CD__ContactReferralBasis = CON144_CR_R5_1_CD +  delimiter1 + "ContactReferralBasisCON144";
		private	static String FirstExp_CR_R5_1 = "FirstExp_CR_R5_1";
		protected static String FirstExp_CR_R5_1__ContactFirstExposureDate = FirstExp_CR_R5_1 +  delimiter1 + "ContactFirstExposureDateNBS118_NBS121";
		private	static String FreqExp_CR_R5_1 = "FreqExp_CR_R5_1";
		protected static String FreqExp_CR_R5_1__ContactFirstExposureFrequency = FreqExp_CR_R5_1 +  delimiter1 + "ContactFirstExposureDateNBS119pipeNBS122";
		private	static String LastExp_CR_R5_1 = "LastExp_CR_R5_1";
		protected static String LastExp_CR_R5_1__ContactLastExposureDate = LastExp_CR_R5_1 +  delimiter1 + "ContactLastExposureDateNBS120_NBS123";
		//contact gender
		private	static String DEM113_CT_R5_1_CD = "DEM113_CT_R5_1_CD";
		protected static String DEM113_CT_R5_1_CD__ContactGender = DEM113_CT_R5_1_CD + delimiter1 + "ContactGenderDEM113";
		private	static String INV178_CT_R5_1_CD = "INV178_CT_R5_1_CD";
		protected static String INV178_CT_R5_1_CD__ContactPregnant = INV178_CT_R5_1_CD + delimiter1 + "ContactPregnantINV178";
		private	static String NBS125_CR_R5_1_CDT = "NBS125_CR_R5_1_CDT";
		protected static String NBS125_CR_R5_1_CDT__ContactOpSpouse = NBS125_CR_R5_1_CDT + delimiter1 + "ContactOpSpouseNBS125";
		//Contact Primary Investigation  - Condition 1 
		private	static String IXS101_CR_IR_R5_1 = "IXS101_CR_IR_R5_1";
		protected static String IXS101_CR_IR_R5_1__ContactPrimaryInvInterviewDate = IXS101_CR_IR_R5_1 + delimiter1 + "ContactPrimaryConditionInterviewDateIXS101";
		private	static String INV147_CT_R5_1 = "INV147_CT_R5_1";
		protected static String INV147_CR_IR_R5_1__ContactPrimaryInvStartDate = INV147_CT_R5_1 + delimiter1 + "ContactPrimaryInvStartDateINV147";
		private	static String IXS102_CR_IR_R5_1_CD = "IXS102_CR_IR_R5_1_CD";
		protected static String IXS102_CR_IR_R5_1_CD__ContactPrimaryInvNamedDuringInterviewer = IXS102_CR_IR_R5_1_CD + delimiter1 + "ContactPrimaryInvNamedDuringInterviewerIXS102";
		private	static String IXS105_CR_IR_R5_1_CDT = "IXS105_CR_IR_R5_1_CDT";
		protected static String IXS105_CR_IR_R5_1_CDT__ContactPrimaryInvNamedDuringInterviewType = IXS105_CR_IR_R5_1_CDT + delimiter1 + "ContactPrimaryInvNamedDuringInterviewTypeIXS105";
		private	static String NBS177_CT_R5_1_CD = "NBS177_CT_R5_1_CD";
		protected static String NBS177_CT_R5_1_CD__ContactPrimaryInvActualReferralType = NBS177_CT_R5_1_CD  + delimiter1 + "ContactPrimaryInvActualReferralTypeNBS177";
		private	static String NBS160_CT_R5_1= "NBS160_CT_R5_1";
		protected static String NBS160_CT_R5_1__ContactPrimaryInvFieldRecordNbr = NBS160_CT_R5_1  + delimiter1 + "ContactPrimaryInvFieldRecordNbrNBS160";
		private	static String NBS173_CT_R5_1_CD = "NBS173_CT_R5_1_CD";
		protected static String NBS173_CT_R5_1_CD__ContactPrimaryInvDisposition = NBS173_CT_R5_1_CD  + delimiter1 + "ContactPrimaryInvDispositionNBS173";
		private	static String NBS174_CT_R5_1 = "NBS174_CT_R5_1";
		protected static String NBS174_CT_R5_1__ContactPrimaryInvDispositionDate = NBS174_CT_R5_1  + delimiter1 + "ContactPrimaryInvDispositionDateNBS174";
		private	static String NBS136_CT_R5_1_CD = "NBS136_CT_R5_1_CD";
		protected static String NBS136_CT_R5_1_CD__ContactPrimaryInvCaseDiagnosis = NBS136_CT_R5_1_CD + delimiter1 + "ContactPrimaryInvCaseDiagnosisNBS136";
		private	static String NBS175_CT_R5_1_CD = "NBS175_CT_R5_1_CD";
		protected static String NBS175_CT_R5_1_CD__ContactPrimaryInvDispoBy = NBS175_CT_R5_1_CD + delimiter1 + "ContactPrimaryInvDispoByNBS175";
		private	static String NBS135_CT_R5_1_CD = "NBS135_CT_R5_1_CD";
		protected static String NBS135_CT_R5_1_CD__ContactPrimaryInvSourceOrSpread = NBS135_CT_R5_1_CD + delimiter1 + "ContactPrimaryInvSourceOrSpreadNBS135";
		//Contact Co-Infection Investigation  - Condition 2 
		private	static String IXS101_CR_IR_R5_2 = "IXS101_CR_IR_R5_2";
		protected static String IXS101_CR_IR_R5_2__ContactCoinfectionInvInterviewDate = IXS101_CR_IR_R5_2 + delimiter1 + "ContactCoinfectionConditionInterviewDateIXS101";
		private	static String INV147_CT_R5_2 = "INV147_CT_R5_2";
		protected static String IXS101_CR_IR_R5_2__ContactCoinfectionInvStartDate = INV147_CT_R5_2 + delimiter1 + "ContactCoinfectionInvStartDateINV147";
		private	static String IXS102_CR_IR_R5_2_CD = "IXS102_CR_IR_R5_2_CD";
		protected static String IXS102_CR_IR_R5_2_CD__ContactCoinfectionInvNamedDuringInterviewer = IXS102_CR_IR_R5_2_CD + delimiter1 + "ContactCoinfectionInvNamedDuringInterviewerIXS102";
		private	static String IXS105_CR_IR_R5_2_CDT = "IXS105_CR_IR_R5_2_CDT";
		protected static String IXS105_CR_IR_R5_2_CDT__ContactCoinfectionInvNamedDuringInterviewType = IXS105_CR_IR_R5_2_CDT + delimiter1 + "ContactCoinfectionInvNamedDuringInterviewTypeIXS105";
		private	static String NBS177_CT_R5_2_CD = "NBS177_CT_R5_2_CD";
		protected static String NBS177_CT_R5_2_CD__ContactCoinfectionInvActualReferralType = NBS177_CT_R5_2_CD  + delimiter1 + "ContactCoinfectionInvActualReferralTypeNBS177";
		private	static String NBS160_CT_R5_2= "NBS160_CT_R5_2";
		protected static String NBS160_CT_R5_2__ContactCoinfectionInvFieldRecordNbr = NBS160_CT_R5_2  + delimiter1 + "ContactCoinfectionInvFieldRecordNbrNBS160";
		private	static String NBS173_CT_R5_2_CD = "NBS173_CT_R5_2_CD";
		protected static String NBS173_CT_R5_2_CD__ContactCoinfectionInvDisposition = NBS173_CT_R5_2_CD  + delimiter1 + "ContactCoinfectionInvDispositionNBS173";
		private	static String NBS174_CT_R5_2 = "NBS174_CT_R5_2";
		protected static String NBS174_CT_R5_2__ContactCoinfectionInvDispositionDate = NBS174_CT_R5_2  + delimiter1 + "ContactCoinfectionInvDispositionDateNBS174";
		private	static String NBS136_CT_R5_2_CD = "NBS136_CT_R5_2_CD";
		protected static String NBS136_CT_R5_2_CD__ContactCoinfectionInvCaseDiagnosis = NBS136_CT_R5_2_CD + delimiter1 + "ContactCoinfectionInvCaseDiagnosisNBS136";
		private	static String NBS175_CT_R5_2_CD = "NBS175_CT_R5_2_CD";
		protected static String NBS175_CT_R5_2_CD__ContactCoinfectionInvDispoBy = NBS175_CT_R5_2_CD + delimiter1 + "ContactCoinfectionInvDispoByNBS175";
		private	static String NBS135_CT_R5_2_CD = "NBS135_CT_R5_2_CD";
		protected static String NBS135_CT_R5_2_CD__ContactCoinfectionInvSourceOrSpread = NBS135_CT_R5_2_CD + delimiter1 + "ContactCoinfectionInvSourceOrSpreadNBS135";
   //Marginal Partners
		//First Marginal Contact
		private	static String MarginalCTName_CR_R1_1 = "MarginalCTName_CR_R1_1";
		protected static String MarginalCTName_CR_R1_1__FirstMarginalContactFullName = MarginalCTName_CR_R1_1 + delimiter1 + "FirstMarginalContactFullName_DEM102_DEM104_DEM105";
		private	static String MarginalCTSex_CR_R1_1_CD = "MarginalCTSex_CR_R1_1_CD";
		protected static String MarginalCTSex_CR_R1_1_CD__FirstMarginalSexInfo = MarginalCTSex_CR_R1_1_CD + delimiter1 + "FirstMarginalSexInfo_DEM113_NBS272_NBS274";
		private	static String MarginalCTAge_CR_R1_1 = "MarginalCTAge_CR_R1_1";
		protected static String MarginalCTAge_CR_R1_1__FirstMarginalCTAge = MarginalCTAge_CR_R1_1+ delimiter1 +  delimiter1 + "FirstMarginalCTReportedAgeINV2001";
		private	static String MarginalCTRace_CR_R1_1_CDT = "MarginalCTRace_CR_R1_1_CDT";
		protected static String MarginalCTRace_CR_R1_1_CDT__FirstMarginalCTRace = MarginalCTRace_CR_R1_1_CDT + delimiter1 +  "FirstMarginalCTRaceDEM152";
		private	static String MarginalCTHeight_CR_R1_1 = "MarginalCTHeight_CR_R1_1";
		protected static String MarginalCTHeight_CR_R1_1__FirstMarginalCTHeight = MarginalCTHeight_CR_R1_1 + delimiter1 + "FirstMarginalCTHeightNBS155";
		private	static String MarginalCTWeight_CR_R1_1 = "MarginalCTWeight_CR_R1_1";
		protected static String MarginalCTWeight_CR_R1_1__FirstMarginalCTSizeBuild= MarginalCTWeight_CR_R1_1 + delimiter1 + "FirstMarginalCTSizeBuildNBS156";
		private	static String MarginalCTHair_CR_R1_1 = "MarginalCTHair_CR_R1_1";
		protected static String MarginalCTHair_CR_R1_1__FirstMarginalCTHair = MarginalCTHair_CR_R1_1 + delimiter1 + "FirstMarginalCTHairNBS157";
		private	static String MarginalCTExp_CR_R1_1 = "MarginalCTExp_CR_R1_1";
		protected static String MarginalCTExp_CR_R1_1__FirstMarginalCTExposureDates = MarginalCTExp_CR_R1_1 + delimiter1 + "FirstMarginalCTExposureDatesNBS188_NBS121_NBS120_NBS123";
		private	static String MarginalCTLocInfo_CR_R1_1 = "MarginalCTLocInfo_CR_R1_1";
		protected static String MarginalCTLocInfo_CR_R1_1__FirstMarginalCTOtherIdentifyingInfo = MarginalCTLocInfo_CR_R1_1 + "FirstMarginalCTOtherIdentifyingInfoNBS159";
		
		//Second Marginal Contact
		private	static String MarginalCTName_CR_R2_1 = "MarginalCTName_CR_R2_1";
		protected static String MarginalCTName_CR_R2_1__SecondMarginalContactFullName = MarginalCTName_CR_R2_1 + delimiter1 + "SecondMarginalContactFullName_DEM102_DEM104_DEM105";
		private	static String MarginalCTSex_CR_R2_1_CD = "MarginalCTSex_CR_R2_1_CD";
		protected static String MarginalCTSex_CR_R2_1_CD__SecondMarginalSexInfo = MarginalCTSex_CR_R2_1_CD + delimiter1 + "SecondMarginalSexInfo_DEM113_NBS272_NBS274";
		private	static String MarginalCTAge_CR_R2_1 = "MarginalCTAge_CR_R2_1";
		protected static String MarginalCTAge_CR_R2_1__SecondMarginalCTAge = MarginalCTAge_CR_R2_1+ delimiter1 +  delimiter1 + "SecondMarginalCTReportedAgeINV2001";
		private	static String MarginalCTRace_CR_R2_1_CDT = "MarginalCTRace_CR_R2_1_CDT";
		protected static String MarginalCTRace_CR_R2_1_CDT__SecondMarginalCTRace = MarginalCTRace_CR_R2_1_CDT + delimiter1 +  "SecondMarginalCTRaceDEM152";
		private	static String MarginalCTHeight_CR_R2_1 = "MarginalCTHeight_CR_R2_1";
		protected static String MarginalCTHeight_CR_R2_1__SecondMarginalCTHeight = MarginalCTHeight_CR_R2_1 + delimiter1 + "SecondMarginalCTHeightNBS155";
		private	static String MarginalCTWeight_CR_R2_1 = "MarginalCTWeight_CR_R2_1";
		protected static String MarginalCTWeight_CR_R2_1__SecondMarginalCTSizeBuild= MarginalCTWeight_CR_R2_1 + delimiter1 + "SecondMarginalCTSizeBuildNBS156";
		private	static String MarginalCTHair_CR_R2_1 = "MarginalCTHair_CR_R2_1";
		protected static String MarginalCTHair_CR_R2_1__SecondMarginalCTHair = MarginalCTHair_CR_R2_1 + delimiter1 + "SecondMarginalCTHairNBS157";
		private	static String MarginalCTExp_CR_R2_1 = "MarginalCTExp_CR_R2_1";
		protected static String MarginalCTExp_CR_R2_1__SecondMarginalCTExposureDates = MarginalCTExp_CR_R2_1 + delimiter1 + "SecondMarginalCTExposureDatesNBS188_NBS121_NBS120_NBS123";
		private	static String MarginalCTLocInfo_CR_R2_1 = "MarginalCTLocInfo_CR_R2_1";
		protected static String MarginalCTLocInfo_CR_R2_1__SecondMarginalCTOtherIdentifyingInfo = MarginalCTLocInfo_CR_R2_1 + "SecondMarginalCTOtherIdentifyingInfoNBS159";
		
		//Third Marginal Contact
		private	static String MarginalCTName_CR_R3_1 = "MarginalCTName_CR_R3_1";
		protected static String MarginalCTName_CR_R3_1__ThirdMarginalContactFullName = MarginalCTName_CR_R3_1 + delimiter1 + "ThirdMarginalContactFullName_DEM102_DEM104_DEM105";
		private	static String MarginalCTSex_CR_R3_1_CD = "MarginalCTSex_CR_R3_1_CD";
		protected static String MarginalCTSex_CR_R3_1_CD__ThirdMarginalSexInfo = MarginalCTSex_CR_R3_1_CD + delimiter1 + "ThirdMarginalSexInfo_DEM113_NBS272_NBS274";
		private	static String MarginalCTAge_CR_R3_1 = "MarginalCTAge_CR_R3_1";
		protected static String MarginalCTAge_CR_R3_1__ThirdMarginalCTAge = MarginalCTAge_CR_R3_1+ delimiter1 +  delimiter1 + "ThirdMarginalCTReportedAgeINV2001";
		private	static String MarginalCTRace_CR_R3_1_CDT = "MarginalCTRace_CR_R3_1_CDT";
		protected static String MarginalCTRace_CR_R3_1_CDT__ThirdMarginalCTRace = MarginalCTRace_CR_R3_1_CDT + delimiter1 +  "ThirdMarginalCTRaceDEM152";
		private	static String MarginalCTHeight_CR_R3_1 = "MarginalCTHeight_CR_R3_1";
		protected static String MarginalCTHeight_CR_R3_1__ThirdMarginalCTHeight = MarginalCTHeight_CR_R3_1 + delimiter1 + "ThirdMarginalCTHeightNBS155";
		private	static String MarginalCTWeight_CR_R3_1 = "MarginalCTWeight_CR_R3_1";
		protected static String MarginalCTWeight_CR_R3_1__ThirdMarginalCTSizeBuild= MarginalCTWeight_CR_R3_1 + delimiter1 + "ThirdMarginalCTSizeBuildNBS156";
		private	static String MarginalCTHair_CR_R3_1 = "MarginalCTHair_CR_R3_1";
		protected static String MarginalCTHair_CR_R3_1__ThirdMarginalCTHair = MarginalCTHair_CR_R3_1 + delimiter1 + "ThirdMarginalCTHairNBS157";
		private	static String MarginalCTExp_CR_R3_1 = "MarginalCTExp_CR_R3_1";
		protected static String MarginalCTExp_CR_R3_1__ThirdMarginalCTExposureDates = MarginalCTExp_CR_R3_1 + delimiter1 + "ThirdMarginalCTExposureDatesNBS188_NBS121_NBS120_NBS123";
		private	static String MarginalCTLocInfo_CR_R3_1 = "MarginalCTLocInfo_CR_R3_1";
		protected static String MarginalCTLocInfo_CR_R3_1__ThirdMarginalCTOtherIdentifyingInfo = MarginalCTLocInfo_CR_R3_1 + "ThirdMarginalCTOtherIdentifyingInfoNBS159";
				
		//Fourth Marginal Contact
		private	static String MarginalCTName_CR_R4_1 = "MarginalCTName_CR_R4_1";
		protected static String MarginalCTName_CR_R4_1_FourthMarginalContactFullName = MarginalCTName_CR_R4_1 + delimiter1 + "FourthMarginalContactFullName_DEM102_DEM104_DEM105";
		private	static String MarginalCTSex_CR_R4_1_CD = "MarginalCTSex_CR_R4_1_CD";
		protected static String MarginalCTSex_CR_R4_1_CD__FourthMarginalSexInfo = MarginalCTSex_CR_R4_1_CD + delimiter1 + "FourthMarginalSexInfo_DEM113_NBS272_NBS274";
		private	static String MarginalCTAge_CR_R4_1 = "MarginalCTAge_CR_R4_1";
		protected static String MarginalCTAge_CR_R4_1__FourthMarginalCTAge = MarginalCTAge_CR_R4_1+ delimiter1 +  delimiter1 + "FourthMarginalCTReportedAgeINV2001";
		private	static String MarginalCTRace_CR_R4_1_CDT = "MarginalCTRace_CR_R4_1_CDT";
		protected static String MarginalCTRace_CR_R4_1_CDT__FourthMarginalCTRace = MarginalCTRace_CR_R4_1_CDT + delimiter1 +  "FourthMarginalCTRaceDEM152";
		private	static String MarginalCTHeight_CR_R4_1 = "MarginalCTHeight_CR_R4_1";
		protected static String MarginalCTHeight_CR_R4_1__FourthMarginalCTHeight = MarginalCTHeight_CR_R4_1 + delimiter1 + "FourthMarginalCTHeightNBS155";
		private	static String MarginalCTWeight_CR_R4_1 = "MarginalCTWeight_CR_R4_1";
		protected static String MarginalCTWeight_CR_R4_1__FourthMarginalCTSizeBuild= MarginalCTWeight_CR_R4_1 + delimiter1 + "FourthMarginalCTSizeBuildNBS156";
		private	static String MarginalCTHair_CR_R4_1 = "MarginalCTHair_CR_R4_1";
		protected static String MarginalCTHair_CR_R4_1__FourthMarginalCTHair = MarginalCTHair_CR_R4_1 + delimiter1 + "FourthMarginalCTHairNBS157";
		private	static String MarginalCTExp_CR_R4_1 = "MarginalCTExp_CR_R4_1";
		protected static String MarginalCTExp_CR_R4_1__FourthMarginalCTExposureDates = MarginalCTExp_CR_R4_1 + delimiter1 + "FourthMarginalCTExposureDatesNBS188_NBS121_NBS120_NBS123";
		private	static String MarginalCTLocInfo_CR_R4_1 = "MarginalCTLocInfo_CR_R4_1";
		protected static String MarginalCTLocInfo_CR_R4_1__FourthMarginalCTOtherIdentifyingInfo = MarginalCTLocInfo_CR_R4_1 + "FourthMarginalCTOtherIdentifyingInfoNBS159";
		//Fifth Marginal Contact
		private	static String MarginalCTName_CR_R5_1 = "MarginalCTName_CR_R5_1";
		protected static String MarginalCTName_CR_R5_1_FifthMarginalContactFullName = MarginalCTName_CR_R5_1 + delimiter1 + "FifthMarginalContactFullName_DEM102_DEM104_DEM105";
		private	static String MarginalCTSex_CR_R5_1_CD = "MarginalCTSex_CR_R5_1_CD";
		protected static String MarginalCTSex_CR_R5_1_CD__FifthMarginalSexInfo = MarginalCTSex_CR_R5_1_CD + delimiter1 + "FifthMarginalSexInfo_DEM113_NBS272_NBS274";
		private	static String MarginalCTAge_CR_R5_1 = "MarginalCTAge_CR_R5_1";
		protected static String MarginalCTAge_CR_R5_1__FifthMarginalCTAge = MarginalCTAge_CR_R5_1+ delimiter1 +  delimiter1 + "FifthMarginalCTReportedAgeINV2001";
		private	static String MarginalCTRace_CR_R5_1_CDT = "MarginalCTRace_CR_R5_1_CDT";
		protected static String MarginalCTRace_CR_R5_1_CDT__FifthMarginalCTRace = MarginalCTRace_CR_R5_1_CDT + delimiter1 +  "FifthMarginalCTRaceDEM152";
		private	static String MarginalCTHeight_CR_R5_1 = "MarginalCTHeight_CR_R5_1";
		protected static String MarginalCTHeight_CR_R5_1__FifthMarginalCTHeight = MarginalCTHeight_CR_R5_1 + delimiter1 + "FifthMarginalCTHeightNBS155";
		private	static String MarginalCTWeight_CR_R5_1 = "MarginalCTWeight_CR_R5_1";
		protected static String MarginalCTWeight_CR_R5_1__FifthMarginalCTSizeBuild= MarginalCTWeight_CR_R5_1 + delimiter1 + "FifthMarginalCTSizeBuildNBS156";
		private	static String MarginalCTHair_CR_R5_1 = "MarginalCTHair_CR_R5_1";
		protected static String MarginalCTHair_CR_R5_1__FifthMarginalCTHair = MarginalCTHair_CR_R5_1 + delimiter1 + "FifthMarginalCTHairNBS157";
		private	static String MarginalCTExp_CR_R5_1 = "MarginalCTExp_CR_R5_1";
		protected static String MarginalCTExp_CR_R5_1__FifthMarginalCTExposureDates = MarginalCTExp_CR_R5_1 + delimiter1 + "FifthMarginalCTExposureDatesNBS188_NBS121_NBS120_NBS123";
		private	static String MarginalCTLocInfo_CR_R5_1 = "MarginalCTLocInfo_CR_R5_1";
		protected static String MarginalCTLocInfo_CR_R5_1__FifthMarginalCTOtherIdentifyingInfo = MarginalCTLocInfo_CR_R5_1 + "FifthMarginalCTOtherIdentifyingInfoNBS159";
		
//Page Five

		private	static String NBS195 = "NBS195";
		protected static String NBS195__InterviewNotesMainAndCoinf = NBS195 + "InterviewNotesMainAndCoinfNBS195";
		//private static String Travel_History = "Travel_History" //not collected in NBS
		//private static String DIS_Investigation_Plans = "Investigation_Plans" //not collected in NBS
		private static String NBS200 = "NBS200";
		protected static String NBS200__SupervisorNotesFromMainandCoinf = NBS200 + delimiter1 + "SupervisorReviewNotesFromMainandCoinfNBS200";
		
	/**
	 * MAPS
	 */
	
	private static Map<String, String> InterviewFieldsMap= new HashMap<String, String>();
	private static Map<String, String> preferredSex = new HashMap<String, String>();
	
	/**
	 * Lab Variables
	 */
	
	/**
	 * Methods
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
				acroForm.setNeedAppearances(true);
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
				logger.error("Error while redirecting Print Error: " + e1.toString());
				throw new NEDSSAppException("Error while closing FileInputStream for Print", e1);
			}
		} finally {		
				try {
					if(pdfDocument!=null)
						pdfDocument.close();
				} catch (IOException e2) {
					logger.error("Error while closing FileInputStream for Print : " + e2.toString());
					throw new NEDSSAppException("Error while closing FileInputStream for Print", e2);
				}
			}
		}
		
	
	private static void initializeInterviewValues() throws NEDSSAppException{
		try{
			
		if(InterviewFieldsMap.size()==0){

			/******************************************************Patient Demographics******************************************************/
				//Name
			InterviewFieldsMap.put(DEM102__PatientLastNameDEM102, DEM102);
			InterviewFieldsMap.put(DEM104__PatientFirstNameDEM104, DEM104);
			
			/*HEADER*/
			InterviewFieldsMap.put(DEM197_1__PatientIdDEM197, DEM197_1);
			InterviewFieldsMap.put(NBS136_1_CD__ConditionNBS136, NBS136_1_CD);
			InterviewFieldsMap.put(NBS136_2_CD__ConditionNBS136, NBS136_2_CD);
			InterviewFieldsMap.put(New_CaseNo_1__InvestigationIdINV168, New_CaseNo_1);
			InterviewFieldsMap.put(New_CaseNo_2__InvestigationIdINV168, New_CaseNo_2);
			InterviewFieldsMap.put(NBS191_1__LOTNBS191, NBS191_1);
			InterviewFieldsMap.put(NBS191_2__LOTNBS191, NBS191_2);
			InterviewFieldsMap.put(NBS160_1__InterviewRecordIdNBS160, NBS160_1);
			InterviewFieldsMap.put(STD102_CD__NeurologicalInvolvementSTD102, STD102_CD);
			InterviewFieldsMap.put(IXS109_1_CD__900SiteTypeIXS109, IXS109_1_CD);
			InterviewFieldsMap.put(IXS108_1__900SiteZipCodeIXS108, IXS108_1);
			InterviewFieldsMap.put(IXS107_1__900AgencyIdIXS107, IXS107_1);
			
		
			
			/*NAME*/
			
			//Patient Name
			InterviewFieldsMap.put(DEM102__PatientLastNameDEM102, DEM102);
			InterviewFieldsMap.put(DEM104__PatientFirstNameDEM104, DEM104);
			InterviewFieldsMap.put(DEM250__PatientAliasNameDEM250, DEM250);
			InterviewFieldsMap.put(MAIDENNAME__MaidenName, MAIDENNAME);
			
			
			
			/*ADDRESS*/
			
			InterviewFieldsMap.put(DEM159_160__PatientStreetDEM159, DEM159_DEM160);
			InterviewFieldsMap.put(DEM161__PatientCityDEM161, DEM161);
			InterviewFieldsMap.put(DEM162__PatientStateDEM162, DEM162);
			InterviewFieldsMap.put(DEM163__PatientZipDEM163, DEM163);
			InterviewFieldsMap.put(DEM165__CountyDEM165, DEM165);
			//InterviewFieldsMap.put(Patient_District__DISTRIC, DISTRICT); //not in NBS
			InterviewFieldsMap.put(DEM167__CountryDEM167, DEM167);
			InterviewFieldsMap.put(NBS201_1__LivingWithNBS201, NBS201_1);
			InterviewFieldsMap.put(NBS202_1_CD__ResidenceTypeNBS202, NBS202_1_CD);			

			//Time At Address
			InterviewFieldsMap.put(NBS203_1__TimeAtAddressNBS203, NBS203_1);
			InterviewFieldsMap.put(NBS204_1_CD__TimeAtAddressNBS204, NBS204_1_CD);
			
			//Time in State
			
			InterviewFieldsMap.put(NBS205_1__TimeInStateNBS205, NBS205_1);
			InterviewFieldsMap.put(NBS206_1_CD__TimeInStateNBS206, NBS206_1_CD);

			//Time in Country
			InterviewFieldsMap.put(NBS207_1__TimeInCountryNBS207, NBS207_1);
			InterviewFieldsMap.put(NBS208_1_CD__TimeInCountryNBS208, NBS208_1_CD);
		
			//Currently institutionalized

			InterviewFieldsMap.put(NBS209_1_CDT__CurrentlyInstitutionalizedNBS209, NBS209_1_CDT);
			
			//Name of Institution
			
			InterviewFieldsMap.put(NBS210_1__NameOfInstitutionNBS210, NBS210_1);

			//Type of Institution
			
			InterviewFieldsMap.put(NBS211_1_CD__TypeOfInstitutionNBS211, NBS211_1_CD);
			
			/*PHONE/CONTACT*/
			InterviewFieldsMap.put(NBS006__PatientCellNBS006_UseCd, NBS006);
			InterviewFieldsMap.put(DEM177__PatientHmPhDEM177_UseCd, DEM177);
			

			//Place of employment
			
			InterviewFieldsMap.put(EMPLOYMENT__PatientWorkPhoneNBS002_PatientWorkPhoneExtensionNBS003, Employment_Phone);
			
			
		
			//Pager

			InterviewFieldsMap.put(DEM182__EmailDEM182, DEM182);
			InterviewFieldsMap.put(DEM182_R2__Email2DEM182, DEM182_R2);
			InterviewFieldsMap.put(Emer_Ct_Name__EmailAddressesEmerCtName, EMERCTNAME);
			InterviewFieldsMap.put(Emer_Ct_Phone__EmailAddressesEmerCtPhone, EMERCTPHONE);
			InterviewFieldsMap.put(Emer_Ct_Relation__EmailAddressesEmerCtRelation, EMERCTRELATION);
			
			
			/*DEMOGRAPHICS*/
			//Age
			
			InterviewFieldsMap.put(INV2001__PatientReportedAge, INV2001);
			InterviewFieldsMap.put(DEM115__PatientDOB, DEM115);
			InterviewFieldsMap.put(BIRTH_SEX_CDT__BirthSex, BIRTH_SEX_CDT);
			
			//Gender

			InterviewFieldsMap.put(DEM113_CD__PatientCurrentSexDEM113, DEM113_CD);
			InterviewFieldsMap.put(NBS274_CD__PatientTransgenderNBS274, NBS274_CD);
			InterviewFieldsMap.put(NBS272_CD__PatientSexUnkownReasonNBS272, NBS272_CD);
			InterviewFieldsMap.put(ADDL_SEX__AddlSex, ADDL_SEX);
		
			//Marital Status
			InterviewFieldsMap.put(DEM140_CDT__PatientMaritalStatusDEM140, DEM140_CDT);
			
			
			//Race
			InterviewFieldsMap.put(DEM152_AI_CDT__PatientRaceDEM152, DEM152_AI_CDT);
			InterviewFieldsMap.put(DEM152_A_CDT__PatientRaceDEM152, DEM152_A_CDT);
			InterviewFieldsMap.put(DEM152_B_CDT__PatientRaceDEM152, DEM152_B_CDT);
			InterviewFieldsMap.put(DEM152_W_CDT__PatientRaceDEM152, DEM152_W_CDT);
			InterviewFieldsMap.put(DEM152_O_CDT__PatientRaceDEM152, DEM152_O_CDT);
			InterviewFieldsMap.put(DEM152_NH_CDT__PatientRaceDEM152, DEM152_NH_CDT);
			InterviewFieldsMap.put(DEM152_U_CDT__PatientRaceDEM152, DEM152_U_CDT);
			InterviewFieldsMap.put(DEM152_R_CDT__PatientRaceDEM152, DEM152_R_CDT);
			InterviewFieldsMap.put(DEM152_D_CDT__PatientRaceDEM152, DEM152_D_CDT);
			InterviewFieldsMap.put(DEM140_CDT__PatientMaritalStatusDEM140, DEM140_CDT);
			InterviewFieldsMap.put(DEM140_CDT__PatientMaritalStatusDEM140, DEM140_CDT);
			InterviewFieldsMap.put(DEM140_CDT__PatientMaritalStatusDEM140, DEM140_CDT);
			InterviewFieldsMap.put(DEM140_CDT__PatientMaritalStatusDEM140, DEM140_CDT);
			InterviewFieldsMap.put(DEM140_CDT__PatientMaritalStatusDEM140, DEM140_CDT);
			InterviewFieldsMap.put(DEM140_CDT__PatientMaritalStatusDEM140, DEM140_CDT);
			InterviewFieldsMap.put(DEM140_CDT__PatientMaritalStatusDEM140, DEM140_CDT);
			
			//Ethnicity
			InterviewFieldsMap.put(DEM155_CDT__PatientEthnicityDEM155, DEM155_CDT);
			InterviewFieldsMap.put(NBS273_CDT__PatientEthnicityUnkownReasonNBS273, NBS273_CDT);
		
			//English Speaking
			InterviewFieldsMap.put(DEM214_1_CDT__EnglishSpeakingDEM214, DEM214_1_CDT);
			InterviewFieldsMap.put(DEM142_1_CDT__PrimaryLanguageDEM142, DEM142_1_CDT);
			
			/*PREGNANCY*/

			InterviewFieldsMap.put(NBS216_1_CD__PregnantAtExamNBS216, NBS216_1_CD);
			InterviewFieldsMap.put(NBS217_1__PregnantAtExamWeeksNBS217, NBS217_1);
			InterviewFieldsMap.put(NBS218_1_CD__PregnantAtInterviewNBS218, NBS218_1_CD);
			InterviewFieldsMap.put(NBS219__PregnantAtInterviewWeeksNBS219, NBS219);
			InterviewFieldsMap.put(NBS220_1_CD__CurrentlyInPrenatalCareNBS220, NBS220_1_CD);
			InterviewFieldsMap.put(NBS221_1_CD__PregnantInLast12MonthsNBS221, NBS221_1_CD);
			InterviewFieldsMap.put(NBS222_1_CD__PregnantInLastMonthNBS222, NBS222_1_CD);
			
			
			/*CONDITION 1 REPORTING INFORMATION*/
			

			InterviewFieldsMap.put(INV159_1_CD__MethodOfCaseDetectionINV159, INV159_1_CD);
			InterviewFieldsMap.put(CASEDETECT_1__OtherCaseDetect, CASEDETECT_1);
			InterviewFieldsMap.put(NBS136_OP_1_CD__OPConditionNBS136_R2, NBS136_OP_1_CD);
			InterviewFieldsMap.put(CaseID_OP_1__OPCaseIDCaseID_OP, CaseID_OP_1);
			InterviewFieldsMap.put(TestFacility_1__FirstTestedTestFacility, TestFacility_1);
			InterviewFieldsMap.put(TestFacilityOth_1__IfOtherDescribeTestFacilityOth_1, TestFacilityOth_1);
			
			InterviewFieldsMap.put(LAB201_1__LaboratoryReportDateLAB201, LAB201_1);
			InterviewFieldsMap.put(NBS192_1_CDT__InterviewedNBS192, NBS192_1_CDT);
			InterviewFieldsMap.put(NBS192_I2_1_CDT__InterviewedNBS192, NBS192_I2_1_CDT);
			//InterviewFieldsMap.put(NotIxdReason_1_CDT__ReasonNotInterviewedNBS192, NotIxdReason_1_CDT);
			InterviewFieldsMap.put(NotIxdOth_1__InterviewedDescribeNotIxdOth, NotIxdOth_1);
			
			InterviewFieldsMap.put(IxPeriod_1__InterviewPeriodIxPeriod, IxPeriod_1);
			InterviewFieldsMap.put(IXS106_IR_1_CD__PlaceOfInterviewIXS106, IXS106_IR_1_CD);
			InterviewFieldsMap.put(IxPlaceOth_1__PlaceOtherIxPlaceOth, IxPlaceOth_1);
			InterviewFieldsMap.put(IXS107_IR_1__PEMSSiteIdIXS107, IXS107_IR_1);

			InterviewFieldsMap.put(NBS189_1__DateFirstAssignedForInterviewNBS189, NBS189_1);
			InterviewFieldsMap.put(NBS188_1_CD__DISFirstInterviewNBS188, NBS188_1_CD);
			InterviewFieldsMap.put(NBS187_1__DateReassignedForInterview1_NBS187, NBS187_1);
			InterviewFieldsMap.put(NBS186_1_CD__DISDateReassigned1_NBS186, NBS186_1_CD);

			InterviewFieldsMap.put(IXS101_IR_I1_1__DateOriginalInterviewIXS101, IXS101_IR_I1_1);
			InterviewFieldsMap.put(IXS102_IR_I1_1_CD__DISOriginalInterviewIXS102, IXS102_IR_I1_1_CD);
			InterviewFieldsMap.put(IXS101_IR_I2_1__DateFirstReinterviewIXS101, IXS101_IR_I2_1);
			InterviewFieldsMap.put(IXS102_IR_I2_1_CD__DISFirstReinterviewIXS102, IXS102_IR_I2_1_CD);
			
			InterviewFieldsMap.put(NBS196_1__DateCaseClosedNBS196, NBS196_1);
			InterviewFieldsMap.put(NBS197_1_CD__DISDateClosedNBS197, NBS197_1_CD);
			InterviewFieldsMap.put(NBS190_1_CD__SupervisorNBS190, NBS190_1_CD);
			InterviewFieldsMap.put(INV152_1_CD__ImportedCaseINV152, INV152_1_CD);
			InterviewFieldsMap.put(ImportLocation_1__ImportLocationImportLocation, ImportLocation_1);
			
			/*CONDITION 2 REPORTING INFORMATION*/
						
			

			InterviewFieldsMap.put(INV159_2_CD__MethodOfCaseDetectionINV159, INV159_2_CD);
			InterviewFieldsMap.put(CASEDETECT_2__OtherCaseDetect, CASEDETECT_2);
			InterviewFieldsMap.put(NBS136_OP_2_CD__OPConditionNBS136_R2, NBS136_OP_2_CD);
			InterviewFieldsMap.put(CaseID_OP_2__OPCaseIDCaseID_OP, CaseID_OP_2);
			InterviewFieldsMap.put(TestFacility_2__FirstTestedTestFacility, TestFacility_2);
			InterviewFieldsMap.put(TestFacilityOth_2__IfOtherDescribeTestFacilityOth_2, TestFacilityOth_2);
			
			InterviewFieldsMap.put(LAB201_2__LaboratoryReportDateLAB201, LAB201_2);
			InterviewFieldsMap.put(NBS192_2_CDT__InterviewedNBS192, NBS192_2_CDT);
			InterviewFieldsMap.put(NBS192_I2_2_CDT__InterviewedNBS192, NBS192_I2_2_CDT);
			//InterviewFieldsMap.put(NotIxdReason_2_CDT__InterviewedIfNotNBS192_R2_2_CDT, NotIxdReason_2_CDT);
			InterviewFieldsMap.put(NotIxdOth_2__InterviewedDescribeNotIxdOth_2, NotIxdOth_2);
			
			InterviewFieldsMap.put(IxPeriod_2__InterviewPeriodIxPeriod_2, IxPeriod_2);
			InterviewFieldsMap.put(IXS106_IR_2_CD__PlaceOfInterviewIXS106_2_CD, IXS106_IR_2_CD);
			InterviewFieldsMap.put(IxPlaceOth_2__PlaceOtherIxPlaceOth_2, IxPlaceOth_2);
			InterviewFieldsMap.put(IXS107_IR_2__PEMSSiteIDIXS107_R2_2, IXS107_IR_2);

			InterviewFieldsMap.put(NBS189_2__DateFirstAssignedForInterviewNBS189_2, NBS189_2);
			InterviewFieldsMap.put(NBS188_2_CD__DISFirstInterviewNBS188, NBS188_2_CD);
			InterviewFieldsMap.put(NBS187_2__DateReassignedForInterview1_NBS187, NBS187_2);
			InterviewFieldsMap.put(NBS186_2_CD__DISDateReassigned1_NBS186, NBS186_2_CD);

			InterviewFieldsMap.put(IXS101_IR_I1_2__DateOriginalInterviewIXS101_R2_2, IXS101_IR_I1_2);
			InterviewFieldsMap.put(IXS102_IR_I1_2_CD__DISOriginalInterviewIXS102_R2_2_CD, IXS102_IR_I1_2_CD);
			InterviewFieldsMap.put(IXS101_IR_I2_2__DateFirstReinterviewIXS101_R3_2, IXS101_IR_I2_2);
			InterviewFieldsMap.put(IXS102_IR_I2_2_CD__DISFirstReinterviewIXS102_R3_2_CD, IXS102_IR_I2_2_CD);
			
			InterviewFieldsMap.put(NBS196_2__DateCaseClosedNBS196_2, NBS196_2);
			InterviewFieldsMap.put(NBS197_2_CD__DISDateClosedNBS197, NBS197_2_CD);
			InterviewFieldsMap.put(NBS190_2_CD__SupervisorNBS190, NBS190_2_CD);
			InterviewFieldsMap.put(INV152_2_CD__ImportedCaseINV152_2_CD, INV152_2_CD);
			InterviewFieldsMap.put(ImportLocation_2__ImportLocationImportLocation_2, ImportLocation_2);
						
//Page 2
			
			
			InterviewFieldsMap.put(NBS229_CD__RiskAssesedNBS229, NBS229_CD);
			InterviewFieldsMap.put(STD107_CD__sexWithMaleSTD107, STD107_CD);
			InterviewFieldsMap.put(STD108_CD__sexWidthFemaleSTD108, STD108_CD);
			InterviewFieldsMap.put(NBS230_CD__sexWithTransgenderNBS230, NBS230_CD);
			InterviewFieldsMap.put(STD109_CD__sexWithAnonymousSTD109, STD109_CD);
			InterviewFieldsMap.put(NBS231_CD__sexWithoutCondomNBS231, NBS231_CD);
			InterviewFieldsMap.put(STD111_CD__sexWithIntoxicatedDrugsSTD111, STD111_CD);
			InterviewFieldsMap.put(STD112_CD__exchangedDrugsSTD112, STD112_CD);
			InterviewFieldsMap.put(STD113_CD__sexMSMSTD113, STD109_CD);
			InterviewFieldsMap.put(STD110_CD__sexIDUSTD110, STD110_CD);
			
			InterviewFieldsMap.put(STD118_CD__beenIncarceratedSTD118, STD118_CD);
			InterviewFieldsMap.put(STD114_CD__engagedInjectionDrugSTD114, STD114_CD);
			InterviewFieldsMap.put(NBS232_CD__sharedInjectionNBS232, NBS232_CD);
			InterviewFieldsMap.put(NBS233_CDT__injectionDrugNoneNBS233, NBS233_CDT);
			InterviewFieldsMap.put(NBS235_CDT__injectionDrugCrackNBS235, NBS235_CDT);
			InterviewFieldsMap.put(NBS237_CDT__injectionDrugCocaineNBS237, NBS237_CDT);
			
			InterviewFieldsMap.put(NBS239_CDT__injectionDrugHeroinNBS239, NBS239_CDT);
			InterviewFieldsMap.put(NBS234_CDT__injectionDrugMetamphetaminesNBS234, NBS234_CDT);
			InterviewFieldsMap.put(NBS236_CDT__injectionDrugNitratesNBS236, NBS236_CDT);
			InterviewFieldsMap.put(NBS238_CDT__injectionDrugErectileNBS238, NBS238_CDT);
			InterviewFieldsMap.put(NBS240_CDT__injectionDrugOtherNBS240, NBS240_CDT);
			
			
			InterviewFieldsMap.put(STD300_1__injectionDrugOtherSpecifySTD300, STD300_1);
			InterviewFieldsMap.put(NBS243T_R1_1_CD__placesMetPartnersTypeNBS243T, NBS243T_R1_1_CD);
			InterviewFieldsMap.put(NBS243T_R2_1_CD__placesMetPartnersTypeNBS243T, NBS243T_R2_1_CD);
			InterviewFieldsMap.put(NBS243T_R3_1_CD__placesMetPartnersTypeNBS243T, NBS243T_R3_1_CD);
			InterviewFieldsMap.put(NBS243T_R4_1_CD__placesMetPartnersTypeNBS243T, NBS243T_R4_1_CD);
			InterviewFieldsMap.put(NBS243T_R5_1_CD__placesMetPartnersTypeNBS243T, NBS243T_R5_1_CD);
			
			InterviewFieldsMap.put(NBS242_1_CDT__placesMetPartnersDidNotAskRefusedToAnswerNBS242, NBS242_1_CDT);
			
			InterviewFieldsMap.put(NBS243N_R1_1__PlacesMetPartnersNameNBS243N, NBS243N_R1_1);
			InterviewFieldsMap.put(NBS243N_R2_1__PlacesMetPartnersNameNBS243N, NBS243N_R2_1);
			InterviewFieldsMap.put(NBS243N_R3_1__PlacesMetPartnersNameNBS243N, NBS243N_R3_1);
			InterviewFieldsMap.put(NBS243N_R4_1__PlacesMetPartnersNameNBS243N, NBS243N_R4_1);
			InterviewFieldsMap.put(NBS243N_R5_1__PlacesMetPartnersNameNBS243N, NBS243N_R5_1);

			//Places had sex
			
			InterviewFieldsMap.put(NBS290T_R1_1_CD__PlacesHadSexTypeNBS290T, NBS290T_R1_1_CD);
			InterviewFieldsMap.put(NBS290T_R2_1_CD__PlacesHadSexTypeNBS290T, NBS290T_R2_1_CD);
			InterviewFieldsMap.put(NBS290T_R3_1_CD__PlacesHadSexTypeNBS290T, NBS290T_R3_1_CD);
			InterviewFieldsMap.put(NBS290T_R4_1_CD__PlacesHadSexTypeNBS290T, NBS290T_R4_1_CD);
			InterviewFieldsMap.put(NBS290T_R5_1_CD__PlacesHadSexTypeNBS290T, NBS290T_R5_1_CD);
			
			InterviewFieldsMap.put(NBS244_1_CDT__PlacesHadSexDidNotAskNBS244, NBS244_1_CDT);
			InterviewFieldsMap.put(NBS290N_R1_1__PlacesHadSexNameNBS290N, NBS290N_R1_1);
			InterviewFieldsMap.put(NBS290N_R2_1__PlacesHadSexNameNBS290N, NBS290N_R2_1);
			InterviewFieldsMap.put(NBS290N_R3_1__PlacesHadSexNameNBS290N, NBS290N_R3_1);
			InterviewFieldsMap.put(NBS290N_R4_1__PlacesHadSexNameNBS290N, NBS290N_R4_1);

			//Partners in last 12 Months Female
			InterviewFieldsMap.put(NBS224_1__PartnersLast12MonthFemaleNBS224, NBS224_1);
			InterviewFieldsMap.put(NBS223_1_CDT__PartnersLast12MonthFemaleUnknownRefusedNBS223, NBS223_1_CDT);
			
			//Partners in last 12 Months Male
			InterviewFieldsMap.put(NBS226_1__PartnersLast12MonthMaleNBS226, NBS226_1);
			InterviewFieldsMap.put(NBS225_1_CDT__PartnersLast12MonthMaleUnknownRefusedNBS225, NBS225_1_CDT);
			
			//Partners in last 12 Months Transgender
			InterviewFieldsMap.put(NBS228_1__PartnersLast12MonthTransgenderNBS228, NBS228_1);
			InterviewFieldsMap.put(NBS227_1_CDT__PartnersLast12MonthTransgenderUnknownRefusedNBS227, NBS227_1_CDT);
			
			//Interview Period Partners Condition 1
			InterviewFieldsMap.put(NBS130_1__InterviewPeriodPartnerFemaleNBS130, NBS130_1);
			InterviewFieldsMap.put(NBS129_1_CDT__InterviewPeriodPartnerFemaleUnknownRefusedNBS129, NBS129_1_CDT);
			InterviewFieldsMap.put(NBS132_1__InterviewPeriodPartnerMaleNBS132, NBS132_1);
			InterviewFieldsMap.put(NBS131_1_CDT__InterviewPeriodPartnerMaleUnknownRefusedNBS131, NBS131_1_CDT);
			InterviewFieldsMap.put(NBS134_1__InterviewPeriodPartnerTransgenderNBS134, NBS134_1);
			InterviewFieldsMap.put(NBS133_1_CDT__InterviewPeriodPartnerUnknownRefusedNBS133, NBS133_1_CDT);
			
			//Interview Period Partners Condition 2
			
			InterviewFieldsMap.put(NBS130_2__InterviewPeriodPartnerFemaleNBS130, NBS130_2);
			InterviewFieldsMap.put(NBS129_2_CDT__InterviewPeriodPartnerFemaleUnknownRefusedNBS129, NBS129_2_CDT);
			InterviewFieldsMap.put(NBS132_2__InterviewPeriodPartnerMaleNBS132, NBS132_2);
			InterviewFieldsMap.put(NBS131_2_CDT__InterviewPeriodPartnerMaleUnknownRefusedNBS131, NBS131_2_CDT);
			InterviewFieldsMap.put(NBS134_2__InterviewPeriodPartnerTransgenderNBS134, NBS134_2);
			InterviewFieldsMap.put(NBS133_2_CDT__InterviewPeriodPartnerUnknownRefusedNBS133, NBS133_2_CDT);
			
			//Partner Internet Information
			InterviewFieldsMap.put(STD119_1_CDT__partnerInternetInformationSTD119, STD119_1_CDT);
			
			//Social History Comments
			InterviewFieldsMap.put(SocHistComments__SocialHistoryCommentsSocHistComments, SocHistComments);

			
//Page 3
	//STD Testing (Labs)
		//Row 1
			InterviewFieldsMap.put(LAB163_RS1__StdFirstLabSpecimenDateLAB163, LAB163_RS1);
			InterviewFieldsMap.put(LAB220_RS1__StdFirstLabTestNameLAB220, LAB220_RS1);
			InterviewFieldsMap.put(Lab_Result_RS1__StdFirstLabTestResultLAB220, Lab_Result_RS1);
			InterviewFieldsMap.put(ORD3_RS1__StdFirstLabReportingFacility, ORD3_RS1);
			InterviewFieldsMap.put(LAB165_RS1__StdFirstLabSpecimenSourceLAB165, LAB165_RS1);
		//Row 2
			InterviewFieldsMap.put(LAB163_RS2__StdSecondLabSpecimenDateLAB163, LAB163_RS2);
			InterviewFieldsMap.put(LAB220_RS2__StdSecondLabTestNameLAB220, LAB220_RS2);
			InterviewFieldsMap.put(Lab_Result_RS2__StdSecondLabTestResultLAB220, Lab_Result_RS2);
			InterviewFieldsMap.put(ORD3_RS2__StdSecondLabReportingFacility, ORD3_RS2);
			InterviewFieldsMap.put(LAB165_RS2__StdSecondLabSpecimenSourceLAB165, LAB165_RS2);
		//Row 3
			InterviewFieldsMap.put(LAB163_RS3__StdThirdLabSpecimenDateLAB163, LAB163_RS3);
			InterviewFieldsMap.put(LAB220_RS3__StdThirdLabTestNameLAB220, LAB220_RS3);
			InterviewFieldsMap.put(Lab_Result_RS3__StdThirdLabTestResultLAB220, Lab_Result_RS3);
			InterviewFieldsMap.put(ORD3_RS3__StdThirdLabReportingFacility, ORD3_RS3);
			InterviewFieldsMap.put(LAB165_RS3__StdThirdLabSpecimenSourceLAB165, LAB165_RS3);
		//Row 4
			InterviewFieldsMap.put(LAB163_RS4__StdFourthLabSpecimenDateLAB164, LAB163_RS4);
			InterviewFieldsMap.put(LAB220_RS4__StdFourthLabTestNameLAB220, LAB220_RS4);
			InterviewFieldsMap.put(Lab_Result_RS4__StdFourthLabTestResultLAB220, Lab_Result_RS4);
			InterviewFieldsMap.put(ORD3_RS4__StdFourthLabReportingFacility, ORD3_RS4);
			InterviewFieldsMap.put(LAB165_RS4__StdFourthLabSpecimenSourceLAB165, LAB165_RS4);
			
		//HIV Testing (Upper Section)
			InterviewFieldsMap.put(NBS262_I2_1_CD__TestedForHivNBS262, NBS262_I2_1_CD);
			InterviewFieldsMap.put(NBS254_1_CD__PrevTestedForHivNBS254, NBS254_1_CD);
			InterviewFieldsMap.put(STD106_CD__SelfReportedHivTestResultSTD106, STD106_CD);
			InterviewFieldsMap.put(NBS259__DateOfSelfReportedHivTestNBS259,	NBS259);
		//HIV (Lab Fields)	
			//Row 1
			InterviewFieldsMap.put(LAB163_RH1__HivFirstLabSpecimenDateLAB163, LAB163_RH1);
			InterviewFieldsMap.put(LAB220_RH1__HivFirstLabTestNameLAB220, LAB220_RH1);
			InterviewFieldsMap.put(Lab_Result_RH1__HivFirstLabTestResultLAB220, Lab_Result_RH1);
			InterviewFieldsMap.put(ORD3_RH1__HivFirstLabReportingFacility, ORD3_RH1);
			InterviewFieldsMap.put(LAB165_RH1__HivFirstLabSpecimenSourceLAB165, LAB165_RH1);
		//Row 2
			InterviewFieldsMap.put(LAB163_RH2__HivSecondLabSpecimenDateLAB163, LAB163_RH2);
			InterviewFieldsMap.put(LAB220_RH2__HivSecondLabTestNameLAB220, LAB220_RH2);
			InterviewFieldsMap.put(Lab_Result_RH2__HivSecondLabTestResultLAB220, Lab_Result_RH2);
			InterviewFieldsMap.put(ORD3_RH2__HivSecondLabReportingFacility, ORD3_RH2);
			InterviewFieldsMap.put(LAB165_RH2__HivSecondLabSpecimenSourceLAB165, LAB165_RH2);
		//Row 3
			InterviewFieldsMap.put(LAB163_RH3__HivThirdLabSpecimenDateLAB163, LAB163_RH3);
			InterviewFieldsMap.put(LAB220_RH3__HivThirdLabTestNameLAB220, LAB220_RH3);
			InterviewFieldsMap.put(Lab_Result_RH3__HivThirdLabTestResultLAB220, Lab_Result_RH3);
			InterviewFieldsMap.put(ORD3_RH3__HivThirdLabReportingFacility, ORD3_RH3);
			InterviewFieldsMap.put(LAB165_RH3__HivThirdLabSpecimenSourceLAB165, LAB165_RH3);

	//Signs and Symptoms
			InterviewFieldsMap.put(INV272_R1_CD__FirstSignSymptomINV272, INV272_R1_CD);
			InterviewFieldsMap.put(NBS247_R1__FirstSignSymptomOnsetDateNBS247, NBS247_R1);
			InterviewFieldsMap.put(STD121_R1_CDT__FirstSignSymptomAnatomicSiteSTD121,STD121_R1_CDT);
			InterviewFieldsMap.put(NBS246_R1_CD__FirstSignSymptomClinicianObservedNBS246,NBS246_R1_CD);
			InterviewFieldsMap.put(NBS249_R1__FirstSignSymptomDurationDaysNBS249, NBS249_R1);
			InterviewFieldsMap.put(INV272_R2_CD__SecondSignSymptomINV272, INV272_R2_CD);
			InterviewFieldsMap.put(NBS247_R2__SecondSignSymptomOnsetDateNBS247, NBS247_R2);
			InterviewFieldsMap.put(STD121_R2_CDT__SecondSignSymptomAnatomicSiteSTD121,STD121_R2_CDT);
			InterviewFieldsMap.put(NBS246_R2_CD__SecondSignSymptomClinicianObservedNBS246,NBS246_R2_CD);
			InterviewFieldsMap.put(NBS249_R2__SecondSignSymptomDurationDaysNBS249, NBS249_R2);
			InterviewFieldsMap.put(INV272_R3_CD__ThirdSignSymptomINV272, INV272_R3_CD);
			InterviewFieldsMap.put(NBS247_R3__ThirdSignSymptomOnsetDateNBS247, NBS247_R3);
			InterviewFieldsMap.put(STD121_R3_CDT__ThirdSignSymptomAnatomicSiteSTD121,STD121_R3_CDT);
			InterviewFieldsMap.put(NBS246_R3_CD__ThirdSignSymptomClinicianObservedNBS246,NBS246_R3_CD);
			InterviewFieldsMap.put(NBS249_R3__ThirdSignSymptomDurationDaysNBS249, NBS249_R3);
	//STD History	
			InterviewFieldsMap.put(STD117_1_CD__PreviousStdHistorySTD117, STD117_1_CD);
			
			InterviewFieldsMap.put(NBS250_R1_1_CD__FirstPreviousStdConditionNBS250,NBS250_R1_1_CD);
			InterviewFieldsMap.put(NBS251_R1_1__FirstPreviousStdDiagnosisDateNBS251,NBS251_R1_1);
			InterviewFieldsMap.put(NBS252_R1_1__FirstPreviousStdTreatmentDateNBS252,NBS252_R1_1);
			InterviewFieldsMap.put(NBS253_R1_1_CD__FirstPreviousStdConfirmedNBS253,NBS253_R1_1_CD);
			
			InterviewFieldsMap.put(NBS250_R2_1_CD__SecondPreviousStdConditionNBS250,NBS250_R2_1_CD);
			InterviewFieldsMap.put(NBS251_R2_1__SecondPreviousStdDiagnosisDateNBS251,NBS251_R2_1);
			InterviewFieldsMap.put(NBS252_R2_1__SecondPreviousStdTreatmentDateNBS252,NBS252_R2_1);
			InterviewFieldsMap.put(NBS253_R2_1_CD__SecondPreviousStdConfirmedNBS253,NBS253_R2_1_CD);			
			
			InterviewFieldsMap.put(NBS250_R3_1_CD__ThirdPreviousStdConditionNBS250,NBS250_R3_1_CD);
			InterviewFieldsMap.put(NBS251_R3_1__ThirdPreviousStdDiagnosisDateNBS251,NBS251_R3_1);
			InterviewFieldsMap.put(NBS252_R3_1__ThirdPreviousStdTreatmentDateNBS252,NBS252_R3_1);
			InterviewFieldsMap.put(NBS253_R3_1_CD__ThirdPreviousStdConfirmedNBS253,NBS253_R3_1_CD);
	//Treatment
			InterviewFieldsMap.put(TR101_1__FirstTreatmentTRT101_TRT100_TRT101_TRT104_OrderingProviderName,TR101_R1);
			InterviewFieldsMap.put(TR101_2__SecondTreatmentTRT101_TRT100_TRT101_TRT104_OrderingProviderName,TR101_R2);
			InterviewFieldsMap.put(TR101_3__ThirdTreatmentTRT101_TRT100_TRT101_TRT104_OrderingProviderName,TR101_R3);
			InterviewFieldsMap.put(TR101_4__FourthTreatmentTRT101_TRT100_TRT101_TRT104_OrderingProviderName,TR101_R4);	
	//Anti-Retroviral Therapy for Diagnosed HIV Infection		
			InterviewFieldsMap.put(NBS255_1_CD__AntiViralTherapyLast12Months, NBS255_1_CD);
			InterviewFieldsMap.put(NBS256_1_CD__AntiViralTherapyEver, NBS256_1_CD);
			InterviewFieldsMap.put(NBS265_1_CD__ResultsProvidedToPartner, NBS265_1_CD);
	//900+ Only	
			InterviewFieldsMap.put(NBS266_1_CD__ReferForCare, NBS266_1_CD);
			InterviewFieldsMap.put(NBS267_CD__KeepAppointment, NBS267_CD);
			
//Page 4			
	//First Contact
			//InterviewFieldsMap.put(DEM102_CT_R1_1__ContactLastName, DEM102_CT_R1_1);
			//InterviewFieldsMap.put(DEM104_CT_R1_1__ContactFirstName, DEM104_CT_R1_1);
			//InterviewFieldsMap.put(DEM250_CT_R1_1__ContactAliasName, DEM250_CT_R1_1);
			//InterviewFieldsMap.put(INV107_CT_R1_1__ContactJurisdiction, INV107_CT_R1_1);
			//InterviewFieldsMap.put(CON144_CR_R1_1_CD__ContactReferralBasis, CON144_CR_R1_1_CD);
			//InterviewFieldsMap.put(FirstExp_CR_R1_1__ContactFirstExposureDate, FirstExp_CR_R1_1);
			//InterviewFieldsMap.put(FreqExp_CR_R1_1__ContactFirstExposureFrequency, FreqExp_CR_R1_1);
			//InterviewFieldsMap.put(LastExp_CR_R1_1__ContactLastExposureDate, FreqExp_CR_R1_1);
			//InterviewFieldsMap.put(DEM113_CT_R1_1_CD__ContactGender, DEM113_CT_R1_1_CD);
			//InterviewFieldsMap.put(INV178_CT_R1_1_CD__ContactPregnant, INV178_CT_R1_1_CD);
			//InterviewFieldsMap.put(NBS125_CR_R1_1_CDT__ContactOpSpouse, NBS125_CR_R1_1_CDT);
		//Contact Primary Inv - Condition 1
			//InterviewFieldsMap.put(IXS101_CR_IR_R1_1__ContactPrimaryInvInterviewDate, IXS101_CR_IR_R1_1);
			//InterviewFieldsMap.put(INV147_CR_IR_R1_1__ContactPrimaryInvStartDate, INV147_CT_R1_1);
			//InterviewFieldsMap.put(IXS101_CR_IR_R1_1__ContactPrimaryInvInterviewDate, IXS101_CR_IR_R1_1);
			//InterviewFieldsMap.put(IXS102_CR_IR_R1_1_CD__ContactPrimaryInvNamedDuringInterviewer, IXS102_CR_IR_R1_1_CD);
			//InterviewFieldsMap.put(IXS105_CR_IR_R1_1_CDT__ContactPrimaryInvNamedDuringInterviewType, IXS105_CR_IR_R1_1_CDT);
			//InterviewFieldsMap.put(NBS177_CT_R1_1_CD__ContactPrimaryInvActualReferralType, NBS177_CT_R1_1_CD);
			//InterviewFieldsMap.put(NBS160_CT_R1_1__ContactPrimaryInvFieldRecordNbr, NBS160_CT_R1_1);
			//InterviewFieldsMap.put(NBS173_CT_R1_1_CD__ContactPrimaryInvDisposition, NBS173_CT_R1_1_CD);
			//InterviewFieldsMap.put(NBS174_CT_R1_1__ContactPrimaryInvDispositionDate, NBS174_CT_R1_1);
			//InterviewFieldsMap.put(NBS136_CT_R1_1_CD__ContactPrimaryInvCaseDiagnosis, NBS136_CT_R1_1_CD);
			//InterviewFieldsMap.put(NBS175_CT_R1_1_CD__ContactPrimaryInvDispoBy, NBS175_CT_R1_1_CD);	
			//InterviewFieldsMap.put(NBS135_CT_R1_1_CD__ContactPrimaryInvSourceOrSpread, NBS135_CT_R1_1_CD);
		//Contact CoInfection Inv - Condition 2
			//InterviewFieldsMap.put(IXS101_CR_IR_R1_2__ContactCoinfectionInvInterviewDate, IXS101_CR_IR_R1_2);
			//InterviewFieldsMap.put(IXS101_CR_IR_R1_2__ContactCoinfectionInvStartDate, INV147_CT_R1_2);
			//InterviewFieldsMap.put(IXS101_CR_IR_R1_2__ContactCoinfectionInvInterviewDate, IXS101_CR_IR_R1_2);
			//InterviewFieldsMap.put(IXS102_CR_IR_R1_2_CD__ContactCoinfectionInvNamedDuringInterviewer, IXS102_CR_IR_R1_2_CD);
			//InterviewFieldsMap.put(IXS105_CR_IR_R1_2_CDT__ContactCoinfectionInvNamedDuringInterviewType, IXS105_CR_IR_R1_2_CDT);
			//InterviewFieldsMap.put(NBS177_CT_R1_2_CD__ContactCoinfectionInvActualReferralType, NBS177_CT_R1_2_CD);
			//InterviewFieldsMap.put(NBS160_CT_R1_2__ContactCoinfectionInvFieldRecordNbr, NBS160_CT_R1_2);
			//InterviewFieldsMap.put(NBS173_CT_R1_2_CD__ContactCoinfectionInvDisposition, NBS173_CT_R1_2_CD);
			//InterviewFieldsMap.put(NBS174_CT_R1_2__ContactCoinfectionInvDispositionDate, NBS174_CT_R1_2);
			//InterviewFieldsMap.put(NBS136_CT_R1_2_CD__ContactCoinfectionInvCaseDiagnosis, NBS136_CT_R1_2_CD);
			//InterviewFieldsMap.put(NBS175_CT_R1_2_CD__ContactCoinfectionInvDispoBy, NBS175_CT_R1_2_CD);	
			//InterviewFieldsMap.put(NBS135_CT_R1_2_CD__ContactCoinfectionInvSourceOrSpread, NBS135_CT_R1_2_CD);
     //Second Contact
			//InterviewFieldsMap.put(DEM102_CT_R2_1__ContactLastName, DEM102_CT_R2_1);
			//InterviewFieldsMap.put(DEM104_CT_R2_1__ContactFirstName, DEM104_CT_R2_1);
			//InterviewFieldsMap.put(DEM250_CT_R2_1__ContactAliasName, DEM250_CT_R2_1);
			//InterviewFieldsMap.put(INV107_CT_R2_1__ContactJurisdiction, INV107_CT_R2_1);
			//InterviewFieldsMap.put(CON144_CR_R2_1_CD__ContactReferralBasis, CON144_CR_R2_1_CD);
			//InterviewFieldsMap.put(FirstExp_CR_R2_1__ContactFirstExposureDate, FirstExp_CR_R2_1);
			//InterviewFieldsMap.put(FreqExp_CR_R2_1__ContactFirstExposureFrequency, FreqExp_CR_R2_1);
			//InterviewFieldsMap.put(LastExp_CR_R2_1__ContactLastExposureDate, FreqExp_CR_R2_1);
			//InterviewFieldsMap.put(DEM113_CT_R2_1_CD__ContactGender, DEM113_CT_R2_1_CD);
			//InterviewFieldsMap.put(INV178_CT_R2_1_CD__ContactPregnant, INV178_CT_R2_1_CD);
			//InterviewFieldsMap.put(NBS125_CR_R2_1_CDT__ContactOpSpouse, NBS125_CR_R2_1_CDT);
		//Contact Primary Inv - Condition 1
			//InterviewFieldsMap.put(IXS101_CR_IR_R2_1__ContactPrimaryInvInterviewDate, IXS101_CR_IR_R2_1);
			//InterviewFieldsMap.put(INV147_CR_IR_R2_1__ContactPrimaryInvStartDate, INV147_CT_R2_1);
			//InterviewFieldsMap.put(IXS101_CR_IR_R2_1__ContactPrimaryInvInterviewDate, IXS101_CR_IR_R2_1);
			//InterviewFieldsMap.put(IXS102_CR_IR_R2_1_CD__ContactPrimaryInvNamedDuringInterviewer, IXS102_CR_IR_R2_1_CD);
			//InterviewFieldsMap.put(IXS105_CR_IR_R2_1_CDT__ContactPrimaryInvNamedDuringInterviewType, IXS105_CR_IR_R2_1_CDT);
			//InterviewFieldsMap.put(NBS177_CT_R2_1_CD__ContactPrimaryInvActualReferralType, NBS177_CT_R2_1_CD);
			//InterviewFieldsMap.put(NBS160_CT_R2_1__ContactPrimaryInvFieldRecordNbr, NBS160_CT_R2_1);
			//InterviewFieldsMap.put(NBS173_CT_R2_1_CD__ContactPrimaryInvDisposition, NBS173_CT_R2_1_CD);
			//InterviewFieldsMap.put(NBS174_CT_R2_1__ContactPrimaryInvDispositionDate, NBS174_CT_R2_1);
			//InterviewFieldsMap.put(NBS136_CT_R2_1_CD__ContactPrimaryInvCaseDiagnosis, NBS136_CT_R2_1_CD);
			//InterviewFieldsMap.put(NBS175_CT_R2_1_CD__ContactPrimaryInvDispoBy, NBS175_CT_R2_1_CD);	
			//InterviewFieldsMap.put(NBS135_CT_R2_1_CD__ContactPrimaryInvSourceOrSpread, NBS135_CT_R2_1_CD);
		//Contact CoInfection Inv - Condition 2
			//InterviewFieldsMap.put(IXS101_CR_IR_R2_2__ContactCoinfectionInvInterviewDate, IXS101_CR_IR_R2_2);
			//InterviewFieldsMap.put(IXS101_CR_IR_R2_2__ContactCoinfectionInvStartDate, INV147_CT_R2_2);
			//InterviewFieldsMap.put(IXS101_CR_IR_R2_2__ContactCoinfectionInvInterviewDate, IXS101_CR_IR_R2_2);
			//InterviewFieldsMap.put(IXS102_CR_IR_R2_2_CD__ContactCoinfectionInvNamedDuringInterviewer, IXS102_CR_IR_R2_2_CD);
			//InterviewFieldsMap.put(IXS105_CR_IR_R2_2_CDT__ContactCoinfectionInvNamedDuringInterviewType, IXS105_CR_IR_R2_2_CDT);
			//InterviewFieldsMap.put(NBS177_CT_R2_2_CD__ContactCoinfectionInvActualReferralType, NBS177_CT_R2_2_CD);
			//InterviewFieldsMap.put(NBS160_CT_R2_2__ContactCoinfectionInvFieldRecordNbr, NBS160_CT_R2_2);
			//InterviewFieldsMap.put(NBS173_CT_R2_2_CD__ContactCoinfectionInvDisposition, NBS173_CT_R2_2_CD);
			//InterviewFieldsMap.put(NBS174_CT_R2_2__ContactCoinfectionInvDispositionDate, NBS174_CT_R2_2);
			//InterviewFieldsMap.put(NBS136_CT_R2_2_CD__ContactCoinfectionInvCaseDiagnosis, NBS136_CT_R2_2_CD);
			//InterviewFieldsMap.put(NBS175_CT_R2_2_CD__ContactCoinfectionInvDispoBy, NBS175_CT_R2_2_CD);	
			//InterviewFieldsMap.put(NBS135_CT_R2_2_CD__ContactCoinfectionInvSourceOrSpread, NBS135_CT_R2_2_CD);
	
	//Third Contact						
			//InterviewFieldsMap.put(DEM102_CT_R3_1__ContactLastName, DEM102_CT_R3_1);
			//InterviewFieldsMap.put(DEM104_CT_R3_1__ContactFirstName, DEM104_CT_R3_1);
			//InterviewFieldsMap.put(DEM250_CT_R3_1__ContactAliasName, DEM250_CT_R3_1);
			//InterviewFieldsMap.put(INV107_CT_R3_1__ContactJurisdiction, INV107_CT_R3_1);
			//InterviewFieldsMap.put(CON144_CR_R3_1_CD__ContactReferralBasis, CON144_CR_R3_1_CD);
			//InterviewFieldsMap.put(FirstExp_CR_R3_1__ContactFirstExposureDate, FirstExp_CR_R3_1);
			//InterviewFieldsMap.put(FreqExp_CR_R3_1__ContactFirstExposureFrequency, FreqExp_CR_R3_1);
			//InterviewFieldsMap.put(LastExp_CR_R3_1__ContactLastExposureDate, FreqExp_CR_R3_1);
			//InterviewFieldsMap.put(DEM113_CT_R3_1_CD__ContactGender, DEM113_CT_R3_1_CD);
			//InterviewFieldsMap.put(INV178_CT_R3_1_CD__ContactPregnant, INV178_CT_R3_1_CD);
			//InterviewFieldsMap.put(NBS125_CR_R3_1_CDT__ContactOpSpouse, NBS125_CR_R3_1_CDT);
		//Contact Primary Inv - Condition 1
			//InterviewFieldsMap.put(IXS101_CR_IR_R3_1__ContactPrimaryInvInterviewDate, IXS101_CR_IR_R3_1);
			//InterviewFieldsMap.put(INV147_CR_IR_R3_1__ContactPrimaryInvStartDate, INV147_CT_R3_1);
			//InterviewFieldsMap.put(IXS101_CR_IR_R3_1__ContactPrimaryInvInterviewDate, IXS101_CR_IR_R3_1);
			//InterviewFieldsMap.put(IXS102_CR_IR_R3_1_CD__ContactPrimaryInvNamedDuringInterviewer, IXS102_CR_IR_R3_1_CD);
			//InterviewFieldsMap.put(IXS105_CR_IR_R3_1_CDT__ContactPrimaryInvNamedDuringInterviewType, IXS105_CR_IR_R3_1_CDT);
			//InterviewFieldsMap.put(NBS177_CT_R3_1_CD__ContactPrimaryInvActualReferralType, NBS177_CT_R3_1_CD);
			//InterviewFieldsMap.put(NBS160_CT_R3_1__ContactPrimaryInvFieldRecordNbr, NBS160_CT_R3_1);
			//InterviewFieldsMap.put(NBS173_CT_R3_1_CD__ContactPrimaryInvDisposition, NBS173_CT_R3_1_CD);
			//InterviewFieldsMap.put(NBS174_CT_R3_1__ContactPrimaryInvDispositionDate, NBS174_CT_R3_1);
			//InterviewFieldsMap.put(NBS136_CT_R3_1_CD__ContactPrimaryInvCaseDiagnosis, NBS136_CT_R3_1_CD);
			//InterviewFieldsMap.put(NBS175_CT_R3_1_CD__ContactPrimaryInvDispoBy, NBS175_CT_R3_1_CD);	
			//InterviewFieldsMap.put(NBS135_CT_R3_1_CD__ContactPrimaryInvSourceOrSpread, NBS135_CT_R3_1_CD);
		//Contact CoInfection Inv - Condition 2
			//InterviewFieldsMap.put(IXS101_CR_IR_R3_2__ContactCoinfectionInvInterviewDate, IXS101_CR_IR_R3_2);
			//InterviewFieldsMap.put(IXS101_CR_IR_R3_2__ContactCoinfectionInvStartDate, INV147_CT_R3_2);
			//InterviewFieldsMap.put(IXS101_CR_IR_R3_2__ContactCoinfectionInvInterviewDate, IXS101_CR_IR_R3_2);
			//InterviewFieldsMap.put(IXS102_CR_IR_R3_2_CD__ContactCoinfectionInvNamedDuringInterviewer, IXS102_CR_IR_R3_2_CD);
			//InterviewFieldsMap.put(IXS105_CR_IR_R3_2_CDT__ContactCoinfectionInvNamedDuringInterviewType, IXS105_CR_IR_R3_2_CDT);
			//InterviewFieldsMap.put(NBS177_CT_R3_2_CD__ContactCoinfectionInvActualReferralType, NBS177_CT_R3_2_CD);
			//InterviewFieldsMap.put(NBS160_CT_R3_2__ContactCoinfectionInvFieldRecordNbr, NBS160_CT_R3_2);
			//InterviewFieldsMap.put(NBS173_CT_R3_2_CD__ContactCoinfectionInvDisposition, NBS173_CT_R3_2_CD);
			//InterviewFieldsMap.put(NBS174_CT_R3_2__ContactCoinfectionInvDispositionDate, NBS174_CT_R3_2);
			//InterviewFieldsMap.put(NBS136_CT_R3_2_CD__ContactCoinfectionInvCaseDiagnosis, NBS136_CT_R3_2_CD);
			//InterviewFieldsMap.put(NBS175_CT_R3_2_CD__ContactCoinfectionInvDispoBy, NBS175_CT_R3_2_CD);	
			//InterviewFieldsMap.put(NBS135_CT_R3_2_CD__ContactCoinfectionInvSourceOrSpread, NBS135_CT_R3_2_CD);			
     //Fourth Contact
			//InterviewFieldsMap.put(DEM102_CT_R4_1__ContactLastName, DEM102_CT_R4_1);
			//InterviewFieldsMap.put(DEM104_CT_R4_1__ContactFirstName, DEM104_CT_R4_1);
			//InterviewFieldsMap.put(DEM250_CT_R4_1__ContactAliasName, DEM250_CT_R4_1);
			//InterviewFieldsMap.put(INV107_CT_R4_1__ContactJurisdiction, INV107_CT_R4_1);
			//InterviewFieldsMap.put(CON144_CR_R4_1_CD__ContactReferralBasis, CON144_CR_R4_1_CD);
			//InterviewFieldsMap.put(FirstExp_CR_R4_1__ContactFirstExposureDate, FirstExp_CR_R4_1);
			//InterviewFieldsMap.put(FreqExp_CR_R4_1__ContactFirstExposureFrequency, FreqExp_CR_R4_1);
			//InterviewFieldsMap.put(LastExp_CR_R4_1__ContactLastExposureDate, FreqExp_CR_R4_1);
			//InterviewFieldsMap.put(DEM113_CT_R4_1_CD__ContactGender, DEM113_CT_R4_1_CD);
			//InterviewFieldsMap.put(INV178_CT_R4_1_CD__ContactPregnant, INV178_CT_R4_1_CD);
			//InterviewFieldsMap.put(NBS125_CR_R4_1_CDT__ContactOpSpouse, NBS125_CR_R4_1_CDT);
		//Contact Primary Inv - Condition 1
			//InterviewFieldsMap.put(IXS101_CR_IR_R4_1__ContactPrimaryInvInterviewDate, IXS101_CR_IR_R4_1);
			//InterviewFieldsMap.put(INV147_CR_IR_R4_1__ContactPrimaryInvStartDate, INV147_CT_R4_1);
			//InterviewFieldsMap.put(IXS101_CR_IR_R4_1__ContactPrimaryInvInterviewDate, IXS101_CR_IR_R4_1);
			//InterviewFieldsMap.put(IXS102_CR_IR_R4_1_CD__ContactPrimaryInvNamedDuringInterviewer, IXS102_CR_IR_R4_1_CD);
			//InterviewFieldsMap.put(IXS105_CR_IR_R4_1_CDT__ContactPrimaryInvNamedDuringInterviewType, IXS105_CR_IR_R4_1_CDT);
			//InterviewFieldsMap.put(NBS177_CT_R4_1_CD__ContactPrimaryInvActualReferralType, NBS177_CT_R4_1_CD);
			//InterviewFieldsMap.put(NBS160_CT_R4_1__ContactPrimaryInvFieldRecordNbr, NBS160_CT_R4_1);
			//InterviewFieldsMap.put(NBS173_CT_R4_1_CD__ContactPrimaryInvDisposition, NBS173_CT_R4_1_CD);
			//InterviewFieldsMap.put(NBS174_CT_R4_1__ContactPrimaryInvDispositionDate, NBS174_CT_R4_1);
			//InterviewFieldsMap.put(NBS136_CT_R4_1_CD__ContactPrimaryInvCaseDiagnosis, NBS136_CT_R4_1_CD);
			//InterviewFieldsMap.put(NBS175_CT_R4_1_CD__ContactPrimaryInvDispoBy, NBS175_CT_R4_1_CD);	
			//InterviewFieldsMap.put(NBS135_CT_R4_1_CD__ContactPrimaryInvSourceOrSpread, NBS135_CT_R4_1_CD);
		//Contact CoInfection Inv - Condition 2
			//InterviewFieldsMap.put(IXS101_CR_IR_R4_2__ContactCoinfectionInvInterviewDate, IXS101_CR_IR_R4_2);
			//InterviewFieldsMap.put(IXS101_CR_IR_R4_2__ContactCoinfectionInvStartDate, INV147_CT_R4_2);
			//InterviewFieldsMap.put(IXS101_CR_IR_R4_2__ContactCoinfectionInvInterviewDate, IXS101_CR_IR_R4_2);
			//InterviewFieldsMap.put(IXS102_CR_IR_R4_2_CD__ContactCoinfectionInvNamedDuringInterviewer, IXS102_CR_IR_R4_2_CD);
			//InterviewFieldsMap.put(IXS105_CR_IR_R4_2_CDT__ContactCoinfectionInvNamedDuringInterviewType, IXS105_CR_IR_R4_2_CDT);
			//InterviewFieldsMap.put(NBS177_CT_R4_2_CD__ContactCoinfectionInvActualReferralType, NBS177_CT_R4_2_CD);
			//InterviewFieldsMap.put(NBS160_CT_R4_2__ContactCoinfectionInvFieldRecordNbr, NBS160_CT_R4_2);
			//InterviewFieldsMap.put(NBS173_CT_R4_2_CD__ContactCoinfectionInvDisposition, NBS173_CT_R4_2_CD);
			//InterviewFieldsMap.put(NBS174_CT_R4_2__ContactCoinfectionInvDispositionDate, NBS174_CT_R4_2);
			//InterviewFieldsMap.put(NBS136_CT_R4_2_CD__ContactCoinfectionInvCaseDiagnosis, NBS136_CT_R4_2_CD);
			//InterviewFieldsMap.put(NBS175_CT_R4_2_CD__ContactCoinfectionInvDispoBy, NBS175_CT_R4_2_CD);	
			//InterviewFieldsMap.put(NBS135_CT_R4_2_CD__ContactCoinfectionInvSourceOrSpread, NBS135_CT_R4_2_CD);
	//Fifth Contact
			//InterviewFieldsMap.put(DEM102_CT_R5_1__ContactLastName, DEM102_CT_R5_1);
			//InterviewFieldsMap.put(DEM104_CT_R5_1__ContactFirstName, DEM104_CT_R5_1);
			//InterviewFieldsMap.put(DEM250_CT_R5_1__ContactAliasName, DEM250_CT_R5_1);
			//InterviewFieldsMap.put(INV107_CT_R5_1__ContactJurisdiction, INV107_CT_R5_1);
			//InterviewFieldsMap.put(CON144_CR_R5_1_CD__ContactReferralBasis, CON144_CR_R5_1_CD);
			//InterviewFieldsMap.put(FirstExp_CR_R5_1__ContactFirstExposureDate, FirstExp_CR_R5_1);
			//InterviewFieldsMap.put(FreqExp_CR_R5_1__ContactFirstExposureFrequency, FreqExp_CR_R5_1);
			//InterviewFieldsMap.put(LastExp_CR_R5_1__ContactLastExposureDate, FreqExp_CR_R5_1);
			//InterviewFieldsMap.put(DEM113_CT_R5_1_CD__ContactGender, DEM113_CT_R5_1_CD);
			//InterviewFieldsMap.put(INV178_CT_R5_1_CD__ContactPregnant, INV178_CT_R5_1_CD);
			//InterviewFieldsMap.put(NBS125_CR_R5_1_CDT__ContactOpSpouse, NBS125_CR_R5_1_CDT);
		//Contact Primary Inv - Condition 1
			//InterviewFieldsMap.put(IXS101_CR_IR_R5_1__ContactPrimaryInvInterviewDate, IXS101_CR_IR_R5_1);
			//InterviewFieldsMap.put(INV147_CR_IR_R5_1__ContactPrimaryInvStartDate, INV147_CT_R5_1);
			//InterviewFieldsMap.put(IXS101_CR_IR_R5_1__ContactPrimaryInvInterviewDate, IXS101_CR_IR_R5_1);
			//InterviewFieldsMap.put(IXS102_CR_IR_R5_1_CD__ContactPrimaryInvNamedDuringInterviewer, IXS102_CR_IR_R5_1_CD);
			//InterviewFieldsMap.put(IXS105_CR_IR_R5_1_CDT__ContactPrimaryInvNamedDuringInterviewType, IXS105_CR_IR_R5_1_CDT);
			//InterviewFieldsMap.put(NBS177_CT_R5_1_CD__ContactPrimaryInvActualReferralType, NBS177_CT_R5_1_CD);
			//InterviewFieldsMap.put(NBS160_CT_R5_1__ContactPrimaryInvFieldRecordNbr, NBS160_CT_R5_1);
			//InterviewFieldsMap.put(NBS173_CT_R5_1_CD__ContactPrimaryInvDisposition, NBS173_CT_R5_1_CD);
			//InterviewFieldsMap.put(NBS174_CT_R5_1__ContactPrimaryInvDispositionDate, NBS174_CT_R5_1);
			//InterviewFieldsMap.put(NBS136_CT_R5_1_CD__ContactPrimaryInvCaseDiagnosis, NBS136_CT_R5_1_CD);
			//InterviewFieldsMap.put(NBS175_CT_R5_1_CD__ContactPrimaryInvDispoBy, NBS175_CT_R5_1_CD);	
			//InterviewFieldsMap.put(NBS135_CT_R5_1_CD__ContactPrimaryInvSourceOrSpread, NBS135_CT_R5_1_CD);
		//Contact CoInfection Inv - Condition 2
			//InterviewFieldsMap.put(IXS101_CR_IR_R5_2__ContactCoinfectionInvInterviewDate, IXS101_CR_IR_R5_2);
			//InterviewFieldsMap.put(IXS101_CR_IR_R5_2__ContactCoinfectionInvStartDate, INV147_CT_R5_2);
			//InterviewFieldsMap.put(IXS101_CR_IR_R5_2__ContactCoinfectionInvInterviewDate, IXS101_CR_IR_R5_2);
			//InterviewFieldsMap.put(IXS102_CR_IR_R5_2_CD__ContactCoinfectionInvNamedDuringInterviewer, IXS102_CR_IR_R5_2_CD);
			//InterviewFieldsMap.put(IXS105_CR_IR_R5_2_CDT__ContactCoinfectionInvNamedDuringInterviewType, IXS105_CR_IR_R5_2_CDT);
			//InterviewFieldsMap.put(NBS177_CT_R5_2_CD__ContactCoinfectionInvActualReferralType, NBS177_CT_R5_2_CD);
			//InterviewFieldsMap.put(NBS160_CT_R5_2__ContactCoinfectionInvFieldRecordNbr, NBS160_CT_R5_2);
			//InterviewFieldsMap.put(NBS173_CT_R5_2_CD__ContactCoinfectionInvDisposition, NBS173_CT_R5_2_CD);
			//InterviewFieldsMap.put(NBS174_CT_R5_2__ContactCoinfectionInvDispositionDate, NBS174_CT_R5_2);
			//InterviewFieldsMap.put(NBS136_CT_R5_2_CD__ContactCoinfectionInvCaseDiagnosis, NBS136_CT_R5_2_CD);
			//InterviewFieldsMap.put(NBS175_CT_R5_2_CD__ContactCoinfectionInvDispoBy, NBS175_CT_R5_2_CD);	
			//InterviewFieldsMap.put(NBS135_CT_R5_2_CD__ContactCoinfectionInvSourceOrSpread, NBS135_CT_R5_2_CD);			
    //Marginal Partners, Social Contacts and Associates	
			//First Marginal Contact
			//InterviewFieldsMap.put(MarginalCTName_CR_R1_1__FirstMarginalContactFullName, MarginalCTName_CR_R1_1);
			//InterviewFieldsMap.put(MarginalCTSex_CR_R1_1_CD__FirstMarginalSexInfo, MarginalCTSex_CR_R1_1_CD);
			//InterviewFieldsMap.put(MarginalCTAge_CR_R1_1__FirstMarginalCTAge, MarginalCTAge_CR_R1_1);
			//InterviewFieldsMap.put(MarginalCTRace_CR_R1_1_CDT__FirstMarginalCTRace, MarginalCTRace_CR_R1_1_CDT);
			//InterviewFieldsMap.put(MarginalCTHeight_CR_R1_1__FirstMarginalCTHeight, MarginalCTHeight_CR_R1_1);
			//InterviewFieldsMap.put(MarginalCTWeight_CR_R1_1__FirstMarginalCTSizeBuild, MarginalCTWeight_CR_R1_1);
			//InterviewFieldsMap.put(MarginalCTHair_CR_R1_1__FirstMarginalCTHair, MarginalCTHair_CR_R1_1);
			//InterviewFieldsMap.put(MarginalCTExp_CR_R1_1__FirstMarginalCTExposureDates, MarginalCTExp_CR_R1_1);
			//InterviewFieldsMap.put(MarginalCTLocInfo_CR_R1_1__FirstMarginalCTOtherIdentifyingInfo, MarginalCTLocInfo_CR_R1_1);
		//Second Marginal
			//InterviewFieldsMap.put(MarginalCTName_CR_R2_1__SecondMarginalContactFullName, MarginalCTName_CR_R2_1);
			//InterviewFieldsMap.put(MarginalCTSex_CR_R2_1_CD__SecondMarginalSexInfo, MarginalCTSex_CR_R2_1_CD);
			//InterviewFieldsMap.put(MarginalCTAge_CR_R2_1__SecondMarginalCTAge, MarginalCTAge_CR_R2_1);
			//InterviewFieldsMap.put(MarginalCTRace_CR_R2_1_CDT__SecondMarginalCTRace, MarginalCTRace_CR_R2_1_CDT);
			//InterviewFieldsMap.put(MarginalCTHeight_CR_R2_1__SecondMarginalCTHeight, MarginalCTHeight_CR_R2_1);
			//InterviewFieldsMap.put(MarginalCTWeight_CR_R2_1__SecondMarginalCTSizeBuild, MarginalCTWeight_CR_R2_1);
			//InterviewFieldsMap.put(MarginalCTHair_CR_R2_1__SecondMarginalCTHair, MarginalCTHair_CR_R2_1);
			//InterviewFieldsMap.put(MarginalCTExp_CR_R2_1__SecondMarginalCTExposureDates, MarginalCTExp_CR_R2_1);
			//InterviewFieldsMap.put(MarginalCTLocInfo_CR_R2_1__SecondMarginalCTOtherIdentifyingInfo, MarginalCTLocInfo_CR_R2_1);
		//Third Marginal
			//InterviewFieldsMap.put(MarginalCTName_CR_R3_1__ThirdMarginalContactFullName, MarginalCTName_CR_R3_1);
			//InterviewFieldsMap.put(MarginalCTSex_CR_R3_1_CD__ThirdMarginalSexInfo, MarginalCTSex_CR_R3_1_CD);
			//InterviewFieldsMap.put(MarginalCTAge_CR_R3_1__ThirdMarginalCTAge, MarginalCTAge_CR_R3_1);
			//InterviewFieldsMap.put(MarginalCTRace_CR_R3_1_CDT__ThirdMarginalCTRace, MarginalCTRace_CR_R3_1_CDT);
			//InterviewFieldsMap.put(MarginalCTHeight_CR_R3_1__ThirdMarginalCTHeight, MarginalCTHeight_CR_R3_1);
			//InterviewFieldsMap.put(MarginalCTWeight_CR_R3_1__ThirdMarginalCTSizeBuild, MarginalCTWeight_CR_R3_1);
			//InterviewFieldsMap.put(MarginalCTHair_CR_R3_1__ThirdMarginalCTHair, MarginalCTHair_CR_R3_1);
			//InterviewFieldsMap.put(MarginalCTExp_CR_R3_1__ThirdMarginalCTExposureDates, MarginalCTExp_CR_R3_1);
			//InterviewFieldsMap.put(MarginalCTLocInfo_CR_R3_1__ThirdMarginalCTOtherIdentifyingInfo, MarginalCTLocInfo_CR_R3_1);			
		//Fourth Marginal
			//InterviewFieldsMap.put(MarginalCTName_CR_R4_1_FourthMarginalContactFullName, MarginalCTName_CR_R4_1);
			//InterviewFieldsMap.put(MarginalCTSex_CR_R4_1_CD__FourthMarginalSexInfo, MarginalCTSex_CR_R4_1_CD);
			//InterviewFieldsMap.put(MarginalCTAge_CR_R4_1__FourthMarginalCTAge, MarginalCTAge_CR_R4_1);
			//InterviewFieldsMap.put(MarginalCTRace_CR_R4_1_CDT__FourthMarginalCTRace, MarginalCTRace_CR_R4_1_CDT);
			//InterviewFieldsMap.put(MarginalCTHeight_CR_R4_1__FourthMarginalCTHeight, MarginalCTHeight_CR_R4_1);
			//InterviewFieldsMap.put(MarginalCTWeight_CR_R4_1__FourthMarginalCTSizeBuild, MarginalCTWeight_CR_R4_1);
			//InterviewFieldsMap.put(MarginalCTHair_CR_R4_1__FourthMarginalCTHair, MarginalCTHair_CR_R4_1);
			//InterviewFieldsMap.put(MarginalCTExp_CR_R4_1__FourthMarginalCTExposureDates, MarginalCTExp_CR_R4_1);
			//InterviewFieldsMap.put(MarginalCTLocInfo_CR_R4_1__FourthMarginalCTOtherIdentifyingInfo, MarginalCTLocInfo_CR_R4_1);
		//Fifth Marginal
			//InterviewFieldsMap.put(MarginalCTName_CR_R5_1_FifthMarginalContactFullName, MarginalCTName_CR_R5_1);
			//InterviewFieldsMap.put(MarginalCTSex_CR_R5_1_CD__FifthMarginalSexInfo, MarginalCTSex_CR_R5_1_CD);
			//InterviewFieldsMap.put(MarginalCTAge_CR_R5_1__FifthMarginalCTAge, MarginalCTAge_CR_R5_1);
			//InterviewFieldsMap.put(MarginalCTRace_CR_R5_1_CDT__FifthMarginalCTRace, MarginalCTRace_CR_R5_1_CDT);
			//InterviewFieldsMap.put(MarginalCTHeight_CR_R5_1__FifthMarginalCTHeight, MarginalCTHeight_CR_R5_1);
			//InterviewFieldsMap.put(MarginalCTWeight_CR_R5_1__FifthMarginalCTSizeBuild, MarginalCTWeight_CR_R5_1);
			//InterviewFieldsMap.put(MarginalCTHair_CR_R5_1__FifthMarginalCTHair, MarginalCTHair_CR_R5_1);
			//InterviewFieldsMap.put(MarginalCTExp_CR_R5_1__FifthMarginalCTExposureDates, MarginalCTExp_CR_R5_1);
			//InterviewFieldsMap.put(MarginalCTLocInfo_CR_R5_1__FifthMarginalCTOtherIdentifyingInfo, MarginalCTLocInfo_CR_R5_1);			
			
			}
		//For transgender information (NBS274_CT_R)
		if(preferredSex.size()==0){
			preferredSex.put("T","T");
			preferredSex.put("FtM","T");
			preferredSex.put("MtF","T");
		}
		}catch(Exception e){
			logger.error(e);
			logger.error("CDCFieldRecordForm.initializeFieldFollowUpValues Exception thrown, "+ e);
			throw new NEDSSAppException("CDCFieldRecordForm.initializeFieldFollowUpValues Exception thrown, ", e);
			
		}
	}
	
	private static void changeNBS192(){
		
		String value =formSpecificQuestionAnswerMap.get(NBS192_1_CDT);
		
		String valueNBS192_1 = NBS192_PatientInterviewStatusMap.get(value);
		String valueNBS192_R2_1 = NBS192_R_PatientInterviewStatusMap.get(value);
		
		formSpecificQuestionAnswerMap.put(NBS192_1_CDT, valueNBS192_1);
	//	formSpecificQuestionAnswerMap.put(NotIxdReason_1_CDT, valueNBS192_R2_1);
		
		
		
	}
	private static void changeTranslationValues(){
		String resultsProvided = formSpecificQuestionAnswerMap.get(NBS265_1_CD);
		if (resultsProvided != null)
			formSpecificQuestionAnswerMap.put(NBS265_I2_1_CD, YNStdReverseMisMap.get(resultsProvided));
		String referredToMedicalCare = formSpecificQuestionAnswerMap.get(NBS266_1_CD);
		if (resultsProvided != null)
			formSpecificQuestionAnswerMap.put(NBS266_I2_1_CD, YNStdReverseMisMap.get(referredToMedicalCare));
		
	}
	
	
	private static void changeValues(){
		
		String interviewValue = formSpecificQuestionAnswerMap.get(NBS192_1_CDT);
		String placesToMetPartners = formSpecificQuestionAnswerMap.get(NBS242_1_CDT);
		String placesToHaveSex = formSpecificQuestionAnswerMap.get(NBS244_1_CDT);
		
		if(interviewValue.equalsIgnoreCase("I")){
			formSpecificQuestionAnswerMap.put(NBS192_1_CDT, "Y");			
		}
		
		if(placesToMetPartners.equalsIgnoreCase("Y")){//TODO: Error?? Ask Jennifer. it should be U or R not Y
			formSpecificQuestionAnswerMap.put(NBS242_1_CDT, null);			
		}
		
		if(placesToHaveSex.equalsIgnoreCase("Y"))
			formSpecificQuestionAnswerMap.put(NBS244_1_CDT, null);			
	}
	
	
	private static void fillPDForm(PDAcroForm acroForm, String invFormCd, HttpServletRequest req) throws NEDSSAppException {
		String curKey = ""; String curFormValue = "";	
		Map<String, String> mappedContactandInterviewValues   =new HashMap<String, String>();
			
		try {

				
				int i = 1; //what is this used for??
				answerMap = pageForm.getPageClientVO().getAnswerMap(); 

				
				initializeInterviewValues();
				
				getMappedValues(InterviewFieldsMap, invFormCd, req);
				
				populateNewCaseValues(proxyVO, coProxyVO);
				
				populateOneOffFields(proxyVO, coProxyVO, req); //fields where translation different on other forms
				
				populateLabFieldsFromStaging();
				
				populatePlaceRecord(proxyVO, req);
				
				populateImportLocation();
				
				populateOriginalCaseVaues(proxyVO, coProxyVO, req.getSession());
				
				mappedContactandInterviewValues = getContactInformationFormFields(proxyVO, req);
				
				//changeNBS192();
				changeTranslationValues();

				//presently set to 1 so that we can make this code flexible for 2 nd Investigation for print
				@SuppressWarnings("unchecked")
				List<PDField> pdfFormFields = acroForm.getFields();
				for(PDField pdfField: pdfFormFields){
					curKey =pdfField.getPartialName();
					curFormValue = pdfField.getValueAsString(); //for debugging only
					logger.debug("PRINT PDF - Currently Processing Field :"+curKey+ " with value :"+curFormValue);
					if (mappedContactandInterviewValues.containsKey(curKey)) {
						pdfField.setReadOnly(true);
						setField(pdfField, curKey, mappedContactandInterviewValues.get(curKey));
					} else	
						processPDFFIelds(pdfField, i); //Common
				}

			} catch (NEDSSAppException e) {
				logger.error("InterviewRecordForm.fillPDForm: Error while filling up the form and IOException raises: " + e);
				throw new NEDSSAppException("InterviewRecordForm.filledForm Error", e);
			}
		}//end	
	






	private static void populateOriginalCaseVaues(PageActProxyVO proxyVO,
			PageActProxyVO coProxyVO, HttpSession session) {
		
		Map<String, String> originalInvestigationMap = new HashMap<String,String>();
		Map<String, String> originalCoInvestigationMap= new HashMap<String,String>();
		try {
			originalInvestigationMap = checkInvStartedFromConRec(proxyVO, 1, session);
			if (originalInvestigationMap != null && !originalInvestigationMap.isEmpty())
				formSpecificQuestionAnswerMap.putAll(originalInvestigationMap);
			
			if (coProxyVO != null) {
				originalCoInvestigationMap = checkInvStartedFromConRec(coProxyVO, 2, session);
				if (originalCoInvestigationMap != null && !originalCoInvestigationMap.isEmpty())
					formSpecificQuestionAnswerMap.putAll(originalCoInvestigationMap);
			} //coProxyVO != null

	
		} catch (NEDSSAppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * NBS262 is translated on FieldRec differently.
	 * @param proxyVO
	 * @param coProxyVO
	 */
	private static void populateOneOffFields(PageActProxyVO theProxyVO, PageActProxyVO theCoproxyVO, HttpServletRequest request) {
		if (theProxyVO != null) {
			String invFormCd = null;
			Map<Object, Object> proxyAnswerMap = null;
			invFormCd =PageManagementCommonActionUtil.checkIfPublishedPageExists(request, 
					NEDSSConstants.INVESTIGATION_BUSINESS_OBJECT_TYPE, theProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd());
			try {
				PageLoadUtil pageLoadUtil  = new PageLoadUtil();
				pageLoadUtil.loadQuestionKeys(invFormCd);
				proxyAnswerMap = pageLoadUtil.updateMapWithQIds(((PageActProxyVO) theProxyVO)
						.getPageVO().getPamAnswerDTMap());
			} catch (Exception e1) {
				logger.error("getContactInformationFormFields: Exception loading questions -> " +e1.getMessage());
			}
			String testedForHivAtThisEvent = getInvAnsFromMap(proxyAnswerMap, "NBS262");
			if (testedForHivAtThisEvent != null)
				formSpecificQuestionAnswerMap.put(NBS262_I2_1_CD, testedForHivAtThisEvent);


			Timestamp infectiousFromDate =  theProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getInfectiousFromDate();
			Timestamp infectiousToDate = theProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getInfectiousToDate();
			if (infectiousFromDate != null && infectiousToDate!= null) {
				try {
					int m1 = infectiousFromDate.getYear() * 12 + infectiousFromDate.getMonth();
					int m2 = infectiousToDate .getYear() * 12 + infectiousToDate .getMonth();
					Integer infectiousNumberOfMonths = (Integer) m2 - m1 + 1;
					if (infectiousNumberOfMonths < 1)
						infectiousNumberOfMonths = 1;
					formSpecificQuestionAnswerMap.put(IxPeriod_1, infectiousNumberOfMonths.toString());
				} catch (Exception e) {
					logger.error("Error in CommonPDFPrintForm computing infectious period of source investigation", e.getMessage());
				}	
			}//theProxy != null
		}
		if (theCoproxyVO != null) {
			Timestamp coinfectiousFromDate =  theCoproxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getInfectiousFromDate();
			Timestamp coinfectiousToDate = theCoproxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getInfectiousToDate();
			if (coinfectiousFromDate != null && coinfectiousToDate!= null) {
				try {
					int m1 = coinfectiousFromDate.getYear() * 12 + coinfectiousFromDate.getMonth();
					int m2 = coinfectiousToDate .getYear() * 12 + coinfectiousToDate .getMonth();
					Integer infectiousNumberOfMonths = (Integer) m2 - m1 + 1;
					if (infectiousNumberOfMonths < 1)
						infectiousNumberOfMonths = 1;
					formSpecificQuestionAnswerMap.put(IxPeriod_2, infectiousNumberOfMonths.toString());
				} catch (Exception e) {
					logger.error("Error in CommonPDFPrintForm computing infectious period of source investigation", e.getMessage());
				}	
			}
		}

	}
	
	
	/**
	 * Pull information for the Contacts. Per the naming convention, field names with _CT_ come from the contacts investigation.
	 * Fields with _CR_ come from the contact record itself.
	 * @param theProxyVO
	 * @param request
	 * @return
	 * @throws NEDSSAppException
	 */
	public static Map<String, String>  getContactInformationFormFields(PageActProxyVO theProxyVO, HttpServletRequest request) throws NEDSSAppException {

		HashMap<String, String> returnMap = new HashMap<String,String>();

		String conditionCd = theProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd();
		String contactFormCd = null;
		PageActProxyVO contactsPHCProxyVO = null;
		PageActProxyVO contactsCoinfPHCProxyVO = null;
		Map<Object, Object> conPHCAnsMap = null;
		Map<Object, Object> conCoinfPHCAnsMap = null;
		ArrayList<Object> coinfectionInvList = null;

		PageLoadUtil pageLoadUtil =new PageLoadUtil();
		
		Collection<Object> contactColl = theProxyVO
				.getTheCTContactSummaryDTCollection();
		if(contactColl==null || contactColl.isEmpty()) {
			logger.debug("No contacts found to process..");
			return returnMap; //nothing to do
		}

		int curMarginalCnt 	= 1;
		int curContactCnt 	= 1;
		Iterator conSumIter = contactColl.iterator();
		while (conSumIter.hasNext()) {

			CTContactSummaryDT contactSummaryDT = (CTContactSummaryDT) conSumIter
					.next();
			if (!contactSummaryDT.isContactNamedByPatient())
				continue; //we're only interested in contacts the patient named

			try {
				Boolean processContactDetails = Boolean.FALSE;
				if(contactSummaryDT.getContactProcessingDecisionCd() != null 
						&& contactSummaryDT.getContactProcessingDecisionCd().equals("FF")	 //Field Followup
						|| contactSummaryDT.getContactProcessingDecisionCd().equals("RSC")   //Record Search Closure
						|| contactSummaryDT.getContactProcessingDecisionCd().equals("SR")) { //Secondary Referral
					processContactDetails = Boolean.TRUE;
				}

				//get the contact proxyVO
				CTContactProxyVO contactProxyVO = PageLoadUtil.getCTContactProxyObject(
						contactSummaryDT.getCtContactUid(), request.getSession());
				//if there is an investigation started from the contact, get it
				Long contactsInvestigationUid = contactProxyVO.getcTContactVO().getcTContactDT().getContactEntityPhcUid();
				if (contactsInvestigationUid != null) {
					contactsPHCProxyVO = (PageActProxyVO) PageLoadUtil.getProxyObject(contactsInvestigationUid.toString(),
							request.getSession());

					String contactPhcInvFormCd =PageManagementCommonActionUtil.checkIfPublishedPageExists(request, 
							NEDSSConstants.INVESTIGATION_BUSINESS_OBJECT_TYPE, contactsPHCProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd());
					try {
						pageLoadUtil.loadQuestions(contactPhcInvFormCd);
					} catch (Exception e1) {
						logger.error("getContactInformationFormFields: Exception loading questions -> " +e1.getMessage());
						return returnMap;
					}
					pageLoadUtil.loadQuestionKeys(contactPhcInvFormCd);
					conPHCAnsMap = pageLoadUtil.updateMapWithQIds(contactsPHCProxyVO.getPageVO().getPamAnswerDTMap());
				}


				//need to retrieve the question map associated with the Contact Page to get to the answers..
				//Are we dealing with the HIV or STD contact form?
				if (conditionCd != null)
					contactFormCd =PageManagementCommonActionUtil.checkIfPublishedPageExists(request, NEDSSConstants.CONTACT_BUSINESS_OBJECT_TYPE, conditionCd);
				try {
					pageLoadUtil.loadQuestions(contactFormCd);
				} catch (Exception e1) {
					logger.error("getContactInformationFormFields: Exception loading questions -> " +e1.getMessage());
					return returnMap;
				}
				pageLoadUtil.loadQuestionKeys(contactFormCd);
				Map<Object, Object> conAnsMap = pageLoadUtil.updateMapWithQIds(contactProxyVO.getcTContactVO().getCtContactAnswerDTMap());
				//Relationship with This Patient/Other infected Patient?
				if (conAnsMap.containsKey(CON141RelationshipThisPatientorOtherInfected)) {
					String thisOrOther = getContactAnsFromMap(conAnsMap,CON141RelationshipThisPatientorOtherInfected);
					if (thisOrOther != null && !thisOrOther.isEmpty() && thisOrOther.equals("OTHPAT"))
						continue; //per JW, we are skipping this kind of Other, Other relationship, relationship needs to be with this patient
				}
				//Marginal patients don't have an follow-up Investigation
				if (!processContactDetails) {
					returnMap.putAll(getMarginalContactFormFields(contactProxyVO, conAnsMap, returnMap, curMarginalCnt));
					curMarginalCnt++;
					continue;
				}
				//----------------------------------------------------------------------------------------------------------------------------
				//get fields that come from Contact Record
				//Contact Referral Basis
				String contactReferralBasis = contactSummaryDT.getContactReferralBasisCd(); //CON144_CR_R1_1_CD
				if (contactReferralBasis != null)
					returnMap.put(ContactReferralBasisCON144_CR_R+curContactCnt+SEPERATOR_ONE+CODED_VALUE, contactReferralBasis);
				//First Exposure
				String firstSexualExposureDate = getContactAnsFromMap(conAnsMap, NBS118);
				String firstNeedleExposureDate = getContactAnsFromMap(conAnsMap, NBS121);
				String firstExposureDate = returnEarliestOfTwoDates(firstSexualExposureDate, firstNeedleExposureDate);
				//Earliest of NBS118 (First Sexual Exposure Date), NBS121 (First Needle-Sharing Exposure Date)
				if (firstExposureDate != null)
					returnMap.put(ContactFirstExposureDateFirstExp_CR_R+curContactCnt+SEPERATOR_ONE,
							firstExposureDate);
				//Frequency
				String sexualFrequency = getContactAnsFromMap(conAnsMap, NBS119);
				String needleSharingFrequency = getContactAnsFromMap(conAnsMap, NBS122);
				StringBuffer frequencySB = new StringBuffer();
				if (sexualFrequency != null) {
					frequencySB.append(sexualFrequency);
					if (needleSharingFrequency != null)
						frequencySB.append("|"); //pipe
				}
				if (needleSharingFrequency != null)
					frequencySB.append(needleSharingFrequency);
				//Last Exposure
				String lastSexualExposureDate = getContactAnsFromMap(conAnsMap, NBS120);
				String lastNeedleExposureDate = getContactAnsFromMap(conAnsMap, NBS123);
				String lastExposureDate = returnLatestOfTwoDates(lastSexualExposureDate, lastNeedleExposureDate);
				//Earliest of NBS118 (First Sexual Exposure Date), NBS121 (First Needle-Sharing Exposure Date)
				if (lastExposureDate != null)
					returnMap.put(ContactLastExposureDateLastExp_CR_R+curContactCnt+SEPERATOR_ONE,
							lastExposureDate);

				String origPatientSpouse = getContactAnsFromMap(conAnsMap, NBS125);
				if (origPatientSpouse != null && NBS125OPSpouseMap.get(origPatientSpouse) != null)
					returnMap.put(ContactOpSpouseNBS125_CR_R+curContactCnt+SEPERATOR_ONE+CODED_VALUE_TRANSLATED, NBS125OPSpouseMap.get(origPatientSpouse));


				//NBS119 (Sexual Frequency) <pipe> NBS122 (Needle-Sharing Frequency)
				if (frequencySB != null && frequencySB.length() != 0)
					returnMap.put(ContactFirstExposureFrequencyFreqExp_CR_R+curContactCnt+SEPERATOR_ONE,
							frequencySB.toString());

				//get Fields that come from the started Investigation
				if (contactsPHCProxyVO != null) {
					//S_JURDIC_C - jurisdiction codeset
					String jurisDesc = cache.getDescForCode("S_JURDIC_C", contactsPHCProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getJurisdictionCd());
					returnMap.put(INVContactJurisdiction107_CT_R+curContactCnt+SEPERATOR_ONE, jurisDesc);
					PersonVO personVO = PageLoadUtil.getPersonVO(NEDSSConstants.PHC_PATIENT, contactsPHCProxyVO);
					if (personVO != null)
						returnMap.putAll(putContactEntityFieldsIntoMap(personVO, curContactCnt));
					if 	(contactsPHCProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getPregnantIndCd() != null)
						returnMap.put(ContactPregnantINV178_CT_R+curContactCnt+SEPERATOR_ONE+CODED_VALUE, 
								contactsPHCProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getPregnantIndCd());
				}

				//failsafe check
				if (contactsPHCProxyVO == null) {
					logger.error("Error in getContactInformationFormFields: Contact Processing Decision is FF. RSC or SR but Contact Entity Phc Uid is null? Deleted? for contact " + contactProxyVO.getcTContactVO().getcTContactDT().getLocalId());
					continue; 
				}

				//Condition 1 fields come from the Investigation started from the Contact - theContactFFPageProxyVO
				//Condition 2 fields are only shown if the user selected a coinfection to print on the form
				//    For condition 2, we look to see if there is a coinfection selected and we look to see if the contact has a coinfection with that same condition
				//retreive the followup case	
				getContactConditionDetails(theProxyVO, contactsPHCProxyVO, conPHCAnsMap, contactProxyVO, curContactCnt, SEPERATOR_ONE, contactSummaryDT.getNamedDuringInterviewUid(),  returnMap, request.getSession());

				//Condition 2 is the coinfection, see if contact has same condition as coinfection and use that for Condition 2.
				if (coProxyVO == null) {
					logger.debug("getContactInformationFormFields: no coinfection specified to process for Condition 2");
					++curContactCnt;
					continue;
				}
				if(coProxyVO!=null)
					try {
						coinfectionInvList = PageLoadUtil.getSpecificCoinfectionInvListPHC(contactsPHCProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid(), request);
						logger.debug("getContactInformationFormFields: coinfectionInvList contains " + coinfectionInvList.size() + " coinfections");
					} catch (NEDSSAppConcurrentDataException e) {
						logger.error("getContactInformationFormFields: Concurrent Data Exception retreiving coinfection list -> " +e.getMessage());
						throw new NEDSSAppException("Concurrent Data Exception retreiving coinfection list:  "+ e);
					} catch (Exception e) {
						logger.error("getContactInformationFormFields: General Exception retreiving coinfection list -> " +e.getMessage());
						throw new NEDSSAppException("General Exception retreiving coinfection list:  "+ e);
					}
				if (coinfectionInvList == null || coinfectionInvList.isEmpty()) {
					logger.debug("getContactInformationFormFields: no coinfections to process for Condition 2");
					++curContactCnt; //go to next contact
					continue;
				}
				
				//find the coinfection with the same condition as the User Selected Coinfection
				Iterator<Object> iter = coinfectionInvList.iterator();
				String coinfCondCd = coProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd(); //get the condition code
				Long contactCoinfectionPHCUid = null;
				while(iter.hasNext()){
					CoinfectionSummaryVO coinfectionSummaryDT = (CoinfectionSummaryVO)iter.next();
					if (coinfectionSummaryDT.getConditionCd().equalsIgnoreCase(coinfCondCd)) {
						contactCoinfectionPHCUid = coinfectionSummaryDT.getPublicHealthCaseUid();
					}
				}

				if (contactCoinfectionPHCUid == null) {
					logger.debug("getContactInformationFormFields: no coinfections with same condition found to process for Condition 2");
					++curContactCnt;
					continue; 
				}
				//retrieve the PageActProxyVO for the contact's CoInfection
				contactsCoinfPHCProxyVO = (PageActProxyVO) PageLoadUtil.getProxyObject(contactCoinfectionPHCUid.toString(),
						request.getSession());

				String contactCoinfPhcInvFormCd =PageManagementCommonActionUtil.checkIfPublishedPageExists(request, 
						NEDSSConstants.INVESTIGATION_BUSINESS_OBJECT_TYPE, contactsCoinfPHCProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd());
				try {
					pageLoadUtil.loadQuestions(contactCoinfPhcInvFormCd);
				} catch (Exception e1) {
					logger.error("getContactInformationFormFields: Exception loading questions -> " +e1.getMessage());
					return returnMap;
				}
				pageLoadUtil.loadQuestionKeys(contactCoinfPhcInvFormCd);
				conCoinfPHCAnsMap = pageLoadUtil.updateMapWithQIds(contactsPHCProxyVO.getPageVO().getPamAnswerDTMap());
				
				//put the details on the form for Condition 2
				getContactConditionDetails(theProxyVO, contactsCoinfPHCProxyVO, conCoinfPHCAnsMap, contactProxyVO, curContactCnt, SEPERATOR_TWO, null, returnMap, request.getSession());

			} catch (Exception e) {
				logger.error("Error while retrieving getContactInformationFormFields"+ e);
				throw new NEDSSAppException("Error while retrieving getContactInformationFormFields:  "+ e);

			}
			++curContactCnt;
		} //has next

		return returnMap;
	}
	
	/**
	 * Return the earliest of two dates 
	 * Note one or both may be null.
	 * @param firstDate
	 * @param secondDate
	 * @return earliest date or null
	 */
	private static String returnEarliestOfTwoDates(
			String firstDate, String secondDate) {
		if (firstDate == null && secondDate == null)
			return null;
		if (firstDate == null && secondDate != null)
			return secondDate;
		if (firstDate != null && secondDate == null)
			return firstDate;
        try{

            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            java.util.Date date1 = sdf.parse(firstDate);
            java.util.Date date2 = sdf.parse(secondDate);

            if(date1.after(date2)){
                return secondDate;
            }

            if(date1.before(date2)){
                return firstDate;
            }

            if(date1.equals(date2)){
                return firstDate;
            }

        }catch(java.text.ParseException ex){
            logger.error("Invalid Date -> " + ex.getMessage());
        }
    
		return null;
	}
	/**
	 * Return the last of two dates 
	 * Note one or both may be null.
	 * @param firstDate
	 * @param secondDate
	 * @return earliest date or null
	 */
	private static String returnLatestOfTwoDates(
			String firstDate, String secondDate) {
		if (firstDate == null && secondDate == null)
			return null;
		if (firstDate == null && secondDate != null)
			return secondDate;
		if (firstDate != null && secondDate == null)
			return firstDate;
        try{

            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            java.util.Date date1 = sdf.parse(firstDate);
            java.util.Date date2 = sdf.parse(secondDate);

            if(date1.after(date2)){
                return firstDate;
            }

            if(date1.before(date2)){
                return secondDate;
            }

            if(date1.equals(date2)){
                return firstDate;
            }

        }catch(java.text.ParseException ex){
            logger.error("Invalid Date -> " + ex.getMessage());
        }
    
		return null;
	}



	/**
	 * Fill in the Marginal Contacts - Insufficient Info, etc. those with no investigation attached.
	 * @param contactProxyVO
	 * @param conAnsMap
	 * @param returnMap
	 * @param curMarginalCnt
	 * @return return map of fields
	 */
	private static Map<String, String>   getMarginalContactFormFields(CTContactProxyVO contactProxyVO, Map<Object, Object> conAnsMap, Map<String, String> returnMap, int curMarginalCnt) {

		try {
			//put information from the contactProxyVO into the return map
			PersonDT personDt = contactProxyVO.getContactPersonVO()
					.getThePersonDT();
			CTContactDT cTContactDT = contactProxyVO.getcTContactVO()
					.getcTContactDT();
			StringBuffer contactMarginalNameSB = new StringBuffer("");
			if (personDt.getFirstNm() != null)
				contactMarginalNameSB.append(personDt.getFirstNm()).append(" ");
			if (personDt.getMiddleNm() != null)
				contactMarginalNameSB.append(personDt.getMiddleNm()).append(" ");
			if (personDt.getLastNm() != null)
				contactMarginalNameSB.append(personDt.getLastNm());
			if (contactMarginalNameSB.length() > 0)
				returnMap.put(ContactMarginalCTName_CR_R+curMarginalCnt+SEPERATOR_ONE, 
						contactMarginalNameSB.toString());
			
			//current sex
			if (personDt.getCurrSexCd() != null) //MarginalCTSex_CR_R1_1_CD
				returnMap.put(ContactMarginalCTSex_CR_R+curMarginalCnt+SEPERATOR_ONE+CODED_VALUE, 
						personDt.getCurrSexCd());
			//Age
			if (personDt.getAgeReported() != null && !personDt.getAgeReported().isEmpty())
				returnMap.put(ContactMarginalCTAge_CR_R+curMarginalCnt+SEPERATOR_ONE,
						personDt.getAgeReported());
			//Race - TBD - this is empty? race is null?
			ArrayList<Object> al = (ArrayList<Object>) contactProxyVO.getContactPersonVO().getThePersonRaceDTCollection();
			if(al != null){
				String raceDesc= "";
				for(int i=0;i<al.size();i++){
					PersonRaceDT dt = ((PersonRaceDT)al.get(i));
					String raceCd =dt.getRaceCd();
					if(DEM152MarginalRaceMap.get(raceCd)!=null){
						raceDesc=DEM152MarginalRaceMap.get(raceCd);
						if(raceDesc.indexOf("/")>0){
							raceDesc = raceDesc.substring(0, raceDesc.indexOf("/"));
						}
					}
				}
				formSpecificQuestionAnswerMap.put(ContactMarginalCTRace_CR_R+curMarginalCnt+SEPERATOR_ONE+CODED_VALUE_TRANSLATED,raceDesc);
			}


			//Height	
			if (conAnsMap.containsKey("NBS155")) {
				String height = getContactAnsFromMap(conAnsMap, "NBS155");
				if (height != null && !height.isEmpty()) {
					returnMap.put(ContactMarginalCTHeight_CR_R+curMarginalCnt+SEPERATOR_ONE, height);
				}
			}
			//weight - really Size/Build	
			if (conAnsMap.containsKey("NBS156")) {
				String weight = getContactAnsFromMap(conAnsMap, "NBS156");
				if (weight != null && !weight.isEmpty()) {
					returnMap.put(ContactMarginalCTWeight_CR_R+curMarginalCnt+SEPERATOR_ONE, weight);
				}
			}
			//Hair	
			if (conAnsMap.containsKey("NBS157")) {
				String hair = getContactAnsFromMap(conAnsMap, "NBS157");
				if (hair  != null && !hair .isEmpty()) {
					returnMap.put(ContactMarginalCTHair_CR_R+curMarginalCnt+SEPERATOR_ONE, hair);
				}
			}

			//First Exposure
			String firstSexualExposureDate = getContactAnsFromMap(conAnsMap, NBS118);
			String firstNeedleExposureDate = getContactAnsFromMap(conAnsMap, NBS121);
			String firstExposureDate = returnEarliestOfTwoDates(firstSexualExposureDate, firstNeedleExposureDate);
			//Last Exposure
			String lastSexualExposureDate = getContactAnsFromMap(conAnsMap, NBS120);
			String lastNeedleExposureDate = getContactAnsFromMap(conAnsMap, NBS123);
			String lastExposureDate = returnLatestOfTwoDates(lastSexualExposureDate, lastNeedleExposureDate);		
			//Exposure
			StringBuffer exposureSB = new StringBuffer("");
			if (firstExposureDate != null)
				exposureSB.append(firstExposureDate);
			if (lastExposureDate != null) {
				exposureSB.append("-");
			}
			if (lastExposureDate != null) {
				exposureSB.append(lastExposureDate);
			}
			if (exposureSB.length() > 0)
				returnMap.put(ContactMarginalCTExp_CR_R+curMarginalCnt+SEPERATOR_ONE, exposureSB.toString());

			//Other Identifying Info from Contact Rec
			if (conAnsMap.containsKey(NBS159_CASE_MANAGEMENT_SUBJ_OTH_IDNTFYNG_INFO)) {
				String locInfo = getContactAnsFromMap(conAnsMap, NBS159_CASE_MANAGEMENT_SUBJ_OTH_IDNTFYNG_INFO);
				if (locInfo  != null && !locInfo .isEmpty()) {
					returnMap.put(ContactMarginalCTLocInfo_CR_R+curMarginalCnt+SEPERATOR_ONE, locInfo);
				}
			}
			

		} catch (Exception e) {
			logger.error("getMarginalContactFormFields Exception thrown, "+ e);// 
			e.printStackTrace();
		}

		return returnMap;

	}
	/**
	 * getContactConditionDetails - pull from contacts investigation
	 * @param contactInvProxyVO
	 * @param contactsPHCProxyVO 
	 * @param contactInvAnsMap //for Source/Spread
	 * @param contactProxyVO
	 * @param curContactCnt
	 * @param namedDuringInterview 
	 * @param returnMap
	 * @param session
	 * @throws NEDSSAppException
	 */
	
	private static void getContactConditionDetails(PageActProxyVO originalInvProxyVO, 
			PageActProxyVO contactInvProxyVO, Map<Object, Object> contactInvAnsMap, 
			CTContactProxyVO contactProxyVO, int curContactCnt, String curConditionInd, 
			Long namedDuringInterview, Map<String,String> returnMap, HttpSession session)
			throws NEDSSAppException {

		try {
			if (originalInvProxyVO.getTheInterviewSummaryDTCollection() != null) {
				Iterator<Object> iterator = originalInvProxyVO
						.getTheInterviewSummaryDTCollection().iterator();
				while (iterator.hasNext()) {
					InterviewSummaryDT interviewSummaryDT = (InterviewSummaryDT) iterator
							.next();

					//only show the Interview the contact was named during
					if (namedDuringInterview != null && interviewSummaryDT.getInterviewUid().longValue() == namedDuringInterview.longValue()) {
						//Ix Date
						if (interviewSummaryDT
								.getInterviewDate() != null) {
							returnMap.put(ContactInterviewDateIXS101_CR_IR_R+curContactCnt+curConditionInd,
									StringUtils.formatDate(interviewSummaryDT.getInterviewDate()));
						}

						if (interviewSummaryDT.getInterviewerQuickCd() != null) {
							returnMap.put(ContactInterviewerDISIXS102_CR_IR_R+curContactCnt+curConditionInd+CODED_VALUE,
									interviewSummaryDT.getInterviewerQuickCd());
						}
						//Ix Type
						if (interviewSummaryDT
								.getInterviewTypeCd() != null) {
							returnMap.put(ContactInterviewTypeIXS105_CR_IR_R+curContactCnt+curConditionInd+CODED_VALUE_TRANSLATED,
									IXS105NbsInterviewTypeMap.get(interviewSummaryDT.getInterviewTypeCd()));
						}
					} //if NamedDuringInterview
				} //hasNext
			} //Interview Summary Not Null		
			
			//Investigation Start Date
			if (contactInvProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getActivityFromTime() != null)
				returnMap.put(ContactInitialDateINV147_CT_R+curContactCnt+curConditionInd, 
						StringUtils.formatDate(contactInvProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getActivityFromTime()));

			
			//Notification Method - Actual Referral Type
			if (contactInvProxyVO.getPublicHealthCaseVO().getTheCaseManagementDT()
					.getActRefTypeCd() != null) {
				returnMap.put(
						ContactNotificationMethodNBS177_CT_R+curContactCnt+curConditionInd+CODED_VALUE,
						contactInvProxyVO.getPublicHealthCaseVO()
						.getTheCaseManagementDT().getActRefTypeCd());
			}
			//Field Record Number
			if (contactInvProxyVO.getPublicHealthCaseVO().getTheCaseManagementDT()
					.getFieldRecordNumber() != null) {
				returnMap.put(
						ContactFieldRecordNbrNBS160_CT_R+curContactCnt+curConditionInd,
						contactInvProxyVO.getPublicHealthCaseVO()
						.getTheCaseManagementDT().getFieldRecordNumber());
			}
			//Dispo
			if (contactInvProxyVO.getPublicHealthCaseVO()
					.getTheCaseManagementDT().getFldFollUpDispo() != null) {
				returnMap.put(
						ContactDispoNBS173_CT_R+curContactCnt+curConditionInd+CODED_VALUE,
						contactInvProxyVO.getPublicHealthCaseVO()
						.getTheCaseManagementDT()
						.getFldFollUpDispo());
			}
			//Dispo Date
			if (contactInvProxyVO.getPublicHealthCaseVO()
					.getTheCaseManagementDT().getFldFollUpDispoDate() != null) {
				returnMap.put(
						ContactDispoDateNBS174_CT_R+curContactCnt+curConditionInd ,
						StringUtils.formatDate(contactInvProxyVO.getPublicHealthCaseVO()
								.getTheCaseManagementDT()
								.getFldFollUpDispoDate()));
			}
			//Case Diagnosis - Contact Condition - per JW use INV condition
			//if (contactInvProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd() != null) {
			//	String condCd = contactInvProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd();
			//	if (NBS150CaseDiagnosisMap.get(condCd) != null) 
			//		returnMap.put(ContactConditionNBS136_CT_R+curContactCnt+curConditionInd+CODED_VALUE,
			//				NBS150CaseDiagnosisMap.get(condCd));
			//
			//}
			//Case Diagnosis - Contact Condition - per Anthony - only use NBS136
			if (contactInvAnsMap.containsKey(NBS136)) {
					String contactCond = getInvAnsFromMap(contactInvAnsMap, NBS136);
					if (contactCond != null && !contactCond.isEmpty())
						returnMap.put(ContactConditionNBS136_CT_R+curContactCnt+curConditionInd+CODED_VALUE, contactCond);
			}			
			PersonVO dispoFldFupInvestgrOfPHC = PageLoadUtil.getPersonVO(
					"DispoFldFupInvestgrOfPHC", contactInvProxyVO);
			if (dispoFldFupInvestgrOfPHC != null) {
				if (dispoFldFupInvestgrOfPHC.getEntityIdDT_s(0) != null) {
					returnMap.put(
							ContactDispoByNBS175_CT_R+curContactCnt+curConditionInd+CODED_VALUE,
							dispoFldFupInvestgrOfPHC.getEntityIdDT_s(0)
							.getRootExtensionTxt());
				}
			}
			if (contactInvAnsMap.containsKey(NBS135SourceOrSpread)) {
				String sourceSpread = getInvAnsFromMap(contactInvAnsMap, NBS135SourceOrSpread);
				if (sourceSpread != null && !sourceSpread.isEmpty())
					returnMap.put(ContactSourceSpreadNBS135_CT_R+curContactCnt+curConditionInd+CODED_VALUE, sourceSpread);

			}


		} catch (NumberFormatException e) {
			logger.debug("Exception in getContactConditionDetails - getPersonVO" , e);			
		} catch (NEDSSSystemException e) {
			logger.debug("Exception in getContactConditionDetails - getPersonVOCollection" , e);
		}

		return; //for debugging

	}
	
	
	/**
	 * putContactEntityFieldsIntoMap
	 * @param personVO
	 * @param curContactCnt
	 * @return map of contact entity form field values
	 */
	private static Map<String, String>   putContactEntityFieldsIntoMap(PersonVO personVO, int curContactCnt) {
		HashMap<String, String> returnMap = new HashMap<String,String>();

		if (personVO.getThePersonNameDTCollection() != null) {
			Iterator<Object> personNameIt = personVO.getThePersonNameDTCollection()
					.iterator();
			while (personNameIt.hasNext()) {
				PersonNameDT personNameDT = (PersonNameDT) personNameIt.next();
				if (personNameDT.getNmUseCd().equalsIgnoreCase("L")) {  //Legal Name
					if(personNameDT.getFirstNm()!=null && personNameDT.getMiddleNm()!=null) {  //DEM104_CT_R1_1
						returnMap.put(ContactFirstNameDEM104_CT_R +curContactCnt+SEPERATOR_ONE, personNameDT.getFirstNm()+", "+personNameDT.getMiddleNm());
					}else if(personNameDT.getFirstNm()!=null && personNameDT.getMiddleNm()==null) {
						returnMap.put(ContactFirstNameDEM104_CT_R +curContactCnt+SEPERATOR_ONE, personNameDT.getFirstNm());
					}else if(personNameDT.getFirstNm()==null && personNameDT.getMiddleNm()!=null) {
						returnMap.put(ContactFirstNameDEM104_CT_R +curContactCnt+SEPERATOR_ONE, personNameDT.getMiddleNm());
					}
					if(personNameDT.getLastNm()!=null) {
						returnMap.put(ContactLastNameDEM102_CT_R +curContactCnt+SEPERATOR_ONE, personNameDT.getLastNm());
					} 
				}else if (personNameDT.getNmUseCd().equalsIgnoreCase("AL")) {
					//alias name goes in DEM250
						if(personNameDT.getFirstNm()!=null && !personNameDT.getFirstNm().isEmpty())
							returnMap.put(ContactAliasNameDEM250_CT_R +curContactCnt+SEPERATOR_ONE, personNameDT.getFirstNm());
				}

			}
		}
		//Gender boxes 1,2 and 4
		if (personVO.getThePersonDT().getCurrSexCd() != null)
			returnMap.put(ContactGenderDEM113_CT_R+curContactCnt+SEPERATOR_ONE+CODED_VALUE, personVO.getThePersonDT().getCurrSexCd());
		//Gender box 3 T option only
		if (personVO.getThePersonDT().getPreferredGenderCd() != null && preferredSex.containsKey(personVO.getThePersonDT().getPreferredGenderCd()))
			returnMap.put(ContactPreferredGenderNBS274_CT_R+curContactCnt+SEPERATOR_ONE+CODED_VALUE_TRANSLATED,preferredSex.get( personVO.getThePersonDT().getPreferredGenderCd()));
		//Gender Box 5 Reason Unknown - R for Refused only
		if (personVO.getThePersonDT().getSexUnkReasonCd() != null && personVO.getThePersonDT().getSexUnkReasonCd().equalsIgnoreCase("R"))
			returnMap.put(ContactGenderUnknownReasonNBS272_CT_R+curContactCnt+SEPERATOR_ONE+CODED_VALUE_TRANSLATED, personVO.getThePersonDT().getSexUnkReasonCd());
		
		return returnMap;
	

	}

	

	

	/**
	 *  populates Places met partner / had sex's Type and Name columns
	 * @param proxyVO
	 * @param session
	 */
	private static void populatePlaceRecord(PageActProxyVO proxyVO, HttpServletRequest request) {
		
		try {
			 NbsQuestionMetadata metadata =null;
			 Map<String, String> placeQueMap = new HashMap<String, String>();
			 PageLoadUtil pageLoadUtil  = new PageLoadUtil();
			 String invFormCd =PageManagementCommonActionUtil.checkIfPublishedPageExists(request, 
						NEDSSConstants.INVESTIGATION_BUSINESS_OBJECT_TYPE, proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd());
				
			 pageLoadUtil.loadQuestionKeys(invFormCd);
			 Map<Object, Object> repeatingAnswerMap = ((PageActProxyVO)proxyVO).getPageVO().getPageRepeatingAnswerDTMap();
			 Map<Object, Object> map = pageLoadUtil.getQuestionMap();
			 if (map != null) {
				 Collection<Object> mappedQuestions = map.values();
				 Iterator<Object> iter = mappedQuestions.iterator();
				 while (iter.hasNext()) {
					 metadata = (NbsQuestionMetadata) iter.next();
						if(NBS243.equals(metadata.getQuestionIdentifier()) || NBS290.equals(metadata.getQuestionIdentifier())){
							checkifExists(placeQueMap, repeatingAnswerMap, metadata);
						}
				 }
			 }
				
			 List<String> hPlaceUids= new ArrayList<String>();
			 List<String> sPlaceUids= new ArrayList<String>();

			 for(String key : placeQueMap.keySet()){
				 if(key.contains(NBS243)){
					String[] uids = placeQueMap.get(key).split("\\^");
					if(uids.length>0)
					 hPlaceUids.add(uids[0]);
				 }
				 if(key.contains(NBS290)){
					 String[] uids = placeQueMap.get(key).split("\\^");
						if(uids.length>0)

					 sPlaceUids.add(uids[0]);
				 }
			 }

			 for(int i=0; i<hPlaceUids.size();i++){
				 PlaceVO placeVO = PlaceUtil.getThePlaceVO(Long.valueOf(hPlaceUids.get(i)), request.getSession());
				 formSpecificQuestionAnswerMap.put(NBS243+"T_R" +(i+1)+"_1_CD", checkNull(placeVO.getThePlaceDT().getCd()));
				 formSpecificQuestionAnswerMap.put(NBS243+"N_R" +(i+1)+"_1", checkNull(placeVO.getNm()));
			 }

			 for(int i=0; i<sPlaceUids.size();i++){
				 PlaceVO placeVO = PlaceUtil.getThePlaceVO(Long.valueOf(sPlaceUids.get(i)), request.getSession());
				 formSpecificQuestionAnswerMap.put(NBS290+"T_R" +(i+1)+"_1_CD", checkNull(placeVO.getThePlaceDT().getCd()));
				 formSpecificQuestionAnswerMap.put(NBS290+"N_R" +(i+1)+"_1", checkNull(placeVO.getNm()));
			 }
		} catch (Exception e) {
			logger.error("Exception while populating place record "+e.getMessage(), e);
		}

	}
	
	private static Map<String, String> checkifExists(Map<String, String> returnMap,
			Map<Object, Object> repeatingAnswerMap, NbsQuestionMetadata metadata)
			throws Exception {
		try {
			if (repeatingAnswerMap.containsKey(metadata.getNbsQuestionUid())) {
				ArrayList<Object> list = (ArrayList<Object>) repeatingAnswerMap.get(metadata.getNbsQuestionUid());
				if (list != null && list.size() > 0) {
					Iterator<Object> it = list.iterator();
					while (it.hasNext()) {
						NbsCaseAnswerDT caseDT = (NbsCaseAnswerDT) it.next();
						if(caseDT != null &&caseDT.getAnswerTxt() != null ){

						returnMap.put(metadata.getQuestionIdentifier() + "_" + caseDT.getAnswerGroupSeqNbr(),
								mapCode(metadata.getQuestionIdentifier(),caseDT.getAnswerTxt()));
						}

					}
				}
			}
		} catch (Exception e) {
			logger.error("PagePrintProxyToPrintableForm.checkifExists Exception thrown, "+ e.getMessage(), e);
			throw new Exception(e);
		}
		return returnMap;
	}
	/**
	 * Populate Case ID 1 and Case ID 2 if present
	 * @param proxyVO
	 * @param coProxyVO
	 */
	private static void populateNewCaseValues(PageActProxyVO proxyVO,
			PageActProxyVO coProxyVO) {
		if (proxyVO != null) {
			String invLocalId= proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getLocalId();
			formSpecificQuestionAnswerMap.put(New_CaseNo_1, invLocalId);
		}

		if(coProxyVO!=null) {
			String	invLocalIdCoInfection= coProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getLocalId();
			formSpecificQuestionAnswerMap.put(New_CaseNo_2, invLocalIdCoInfection);
		}
		
	}
	
	private static void populateImportLocation(){

		try{
			String diseaseImportedCd = "";
			String formField="";
			if(proxyVO!=null){
				 diseaseImportedCd = proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getDiseaseImportedCd();
				 formField = "ImportLocation_1";
				 if("OOC".equalsIgnoreCase(diseaseImportedCd)){
						formSpecificQuestionAnswerMap.put(formField, proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getImportedCountryCd());
					}else if("OOS".equalsIgnoreCase(diseaseImportedCd)){
						formSpecificQuestionAnswerMap.put(formField, proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getImportedStateCd());
					}else if("OOJ".equalsIgnoreCase(diseaseImportedCd)){
						formSpecificQuestionAnswerMap.put(formField, proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getImportedCityDescTxt()+" "+proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getImportedCountyCd());
					}
			}
			if(coProxyVO!=null){
				 diseaseImportedCd = coProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getDiseaseImportedCd();
				 formField = "ImportLocation_2";
				 if("OOC".equalsIgnoreCase(diseaseImportedCd)){
						formSpecificQuestionAnswerMap.put(formField, coProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getImportedCountryCd());
					}else if("OOS".equalsIgnoreCase(diseaseImportedCd)){
						formSpecificQuestionAnswerMap.put(formField, coProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getImportedStateCd());
					}else if("OOJ".equalsIgnoreCase(diseaseImportedCd)){
						formSpecificQuestionAnswerMap.put(formField, coProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getImportedCityDescTxt()+" "+coProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getImportedCountyCd());
					}
			}
		}catch(Exception ex){
			logger.error("Exception while populating Import Location "+ex.getMessage(), ex);
		}

	}
}
