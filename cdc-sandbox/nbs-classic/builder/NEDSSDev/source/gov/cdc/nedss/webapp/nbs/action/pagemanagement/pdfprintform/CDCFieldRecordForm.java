package gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

import gov.cdc.nedss.act.ctcontact.dt.CTContactSummaryDT;
import gov.cdc.nedss.act.interview.dt.InterviewSummaryDT;
import gov.cdc.nedss.association.dt.ParticipationDT;
import gov.cdc.nedss.entity.entityid.dt.EntityIdDT;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.page.ejb.pageproxyejb.dt.NbsAnswerDT;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.PageProxyVO;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.act.PageActProxyVO;
import gov.cdc.nedss.systemservice.ejb.questionmapejb.dt.NbsQuestionMetadata;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.webapp.nbs.form.page.PageForm;

/**
 * PDF generating/populating class for "Field Record Form"
 * @author Fatima Lopez Calzado
 *
 */
public class CDCFieldRecordForm extends CommonPDFPrintForm{
	/**
	 * Constants
	 */
	private static String delimiter1 = "__";
	private static String DISPOSITION_K = "k";
	private static String PARTICIPATION_INVESTIGATOR_FIELD_FOLLOW_UP ="FldFupInvestgrOfPHC";
	private static String LAB_REFERRAL_BASIS="T1";
	private static String MORB_REFERRAL_BASIS="T2";
	private static String POSTCOUNSELED_UNKNOWN = "Unk";
	private static String REFERRAL_BASIS_P1="P1";
	private static String REFERRAL_BASIS_P2="P2";
	private static String REFERRAL_BASIS_P3="P3";
	private static String PATIENT_NOTIFICATION_ELIGIBILITY_YES = "06";
	private static String PATIENT_NOTIFICATION_ELIGIBILITY_OTHER = "88";
	
	/******************************************************Patient Demographics******************************************************/
		//Name
	private	static String DEM102 = "DEM102";
	protected static String DEM102__PatientLastNameDEM102= DEM102 + delimiter1+ "PatientLastNameDEM102";
	private	static String DEM104 = "DEM104";
	protected static String DEM104__PatientFirstNameDEM104= DEM104 + delimiter1+ "PatientFirstNameDEM104";
	private	static String DEM105 = "DEM105";
	protected static String DEM105__PatientMiddleNameDEM105= DEM105 + delimiter1+ "PatientMiddleNameDEM105";
	private	static String DEM250 = "DEM250";
	protected static String DEM250__PatientAliasNameDEM250= DEM250 + delimiter1+ "PatientAliasNameDEM250";	
		//Address
	private	static String DEM159_DEM160= "DEM159_DEM160";//street1+street2 - there is a space in form
	protected static String DEM159_160__PatientStreetDEM159= DEM159_DEM160 + delimiter1+ "PatientLastNameDEM159DEM160";
	private	static String DEM161 = "DEM161";
	protected static String DEM161__PatientCityDEM161= DEM161 + delimiter1+ "PatientCityDEM161";
	private	static String DEM162 = "DEM162";
	protected static String DEM162__PatientStateDEM162= DEM162 + delimiter1+ "PatientStateDEM162";
	private	static String DEM163 = "DEM163";
	protected static String DEM163__PatientZipDEM163= DEM163 + delimiter1+ "PatientZipDEM163";
		//Phone number
	private	static String NBS006 = "NBS006";
	protected static String NBS006__PatientCellNBS006_UseCd = NBS006 + delimiter1+ "PatientCellNBS006";
	private	static String DEM177 = "DEM177";
	protected static String DEM177__PatientHmPhDEM177_UseCd = DEM177 + delimiter1+ "PatientHmPhDEM177";
		//Age
	private	static String INV2001 = "INV2001";
	protected static String INV2001__PatientReportedAge = INV2001 + delimiter1+ "PatientReportedAgeINV2001";
	private	static String DEM115 = "DEM115";
	protected static String DEM115__PatientDOB = DEM115 + delimiter1+ "PatientDOBDEM115";
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
		//Gender
	private	static String DEM113_CD = "DEM113_CD";
	protected static String DEM113_CD__PatientCurrentSexDEM113 = DEM113_CD + delimiter1+ "PatientCurrentSexDEM113";	 
	private	static String NBS274_CD = "NBS274_CD";
	protected static String  NBS274_CD__PatientTransgenderNBS274 =  NBS274_CD + delimiter1+ "PatientTransgenderNBS274";	
	private	static String NBS272_CD = "NBS272_CD";
	protected static String NBS272_CD__PatientSexUnkownReasonNBS272 = NBS272_CD + delimiter1+ "PatientSexUnkownReasonNBS272";
		//Marital Status
	private	static String DEM140_CDT = "DEM140_CDT";
	protected static String DEM140_CDT__PatientMaritalStatusDEM140 = DEM140_CDT + delimiter1+ "PatientMaritalStatusDEM140";
		//Email
	private	static String DEM182 = "DEM182";//Field Follow Up form constant
	protected static String DEM182__EmailAddressDEM182 = DEM182 + delimiter1+ "EmailAddressDEM182";
	private	static String INTERNET_SITE = "Internet_Site";//Field Follow Up form constant
	protected static String INTERNET_SITE__InternetSiteDEM183 = INTERNET_SITE + delimiter1+ "InternetSiteDEM183";
		//Appearance
	private	static String NBS155 = "NBS155";//Field Follow Up form constant. Common with different value.
	protected static String NBS155__HeightNBS155= NBS155 + delimiter1+ "HeightNBS155";
	private	static String NBS156 = "NBS156";//Field Follow Up form constant. Common with different value.
	protected static String NBS156__SizeNBS156= NBS156 + delimiter1+ "SizeNBS156";
	private	static String NBS157 = "NBS157";//Field Follow Up form constant. Common with different value.
	protected static String NBS157__HairNBS157= NBS157 + delimiter1+ "HairNBS157";
	private	static String NBS158 = "NBS158";//Field Follow Up form constant. Common with different value.
	protected static String NBS158__ComplexionNBS158= NBS158 + delimiter1+ "ComplexionNBS158";
		//Place of employment
	private static String Employment_Phone = "Employment_Phone";//Field Follow Up form constant
	protected	static String EMPLOYMENT__PatientWorkPhoneNBS002_PatientWorkPhoneExtensionNBS003 = Employment_Phone+delimiter1+"PatientWorkPhoneNBS002_PatientWorkPhoneExtensionNBS003";
		//Other_info
	private	static String Other_Info = "Other_Info";
	protected static String  OTHER_INFO__PhysicianNameAndAddressINV182_OtherIdentifyingInfoNBS159 =  Other_Info + delimiter1+ "PhysicianNameAndAddressINV182+OtherIdentifyingInfoNBS159";	

	
	/******************************************************Lab******************************************************/
		
	private	static String LAB163_R1 = "LAB163_R1";
	protected static String LAB163_R1__FirstLabSpecimenDateLAB163= LAB163_R1 + delimiter1+ "FirstLabSpecimenDateLAB163";
	private	static String LAB220_R1 = "LAB220_R1";
	protected static String LAB220_R1__FirstLabTestNameLAB220= LAB220_R1 + delimiter1+ "FirstLabTestNameLAB220";
	private	static String Lab_Result_R1 = "Lab_Result_R1";
	protected static String Lab_Result_R1__FirstLabTestResultLAB220= Lab_Result_R1 + delimiter1+ "FirstLabTestNameLAB220";
	private	static String ORD3_R1 = "ORD3_R1";
	protected static String ORD3_R1__FirstLabReportingFacility= ORD3_R1 + delimiter1+ "FirstLabReportingFacility";
	
	private	static String LAB163_R2 = "LAB163_R2";
	protected static String LAB163_R2__SecondLabSpecimenDateLAB163= LAB163_R2 + delimiter1+ "SecondLabSpecimenDateLAB163";
	private	static String LAB220_R2 = "LAB220_R2";
	protected static String LAB220_R2__SecondLabTestNameLAB220= LAB220_R2 + delimiter1+ "SecondLabTestNameLAB220";
	private	static String Lab_Result_R2 = "Lab_Result_R2";
	protected static String Lab_Result_R2__SecondLabTestResultLAB220= Lab_Result_R2 + delimiter1+ "SecondLabTestNameLAB220";
	private	static String ORD3_R2 = "ORD3_R2";
	protected static String ORD3_R2__SecondLabReportingFacility= ORD3_R2 + delimiter1+ "SecondLabReportingFacility";
	
	private	static String LAB163_R3 = "LAB163_R3";
	protected static String LAB163_R3__ThirdLabSpecimenDateLAB163= LAB163_R3 + delimiter1+ "ThirdLabSpecimenDateLAB163";
	private	static String LAB220_R3 = "LAB220_R3";
	protected static String LAB220_R3__ThirdLabTestNameLAB220= LAB220_R3 + delimiter1+ "ThirdLabTestNameLAB220";
	private	static String Lab_Result_R3 = "Lab_Result_R3";
	protected static String Lab_Result_R3__ThirdLabTestResultLAB220= Lab_Result_R3 + delimiter1+ "ThirdLabTestNameLAB220";
	private	static String ORD3_R3 = "ORD3_R3";
	protected static String ORD3_R3__ThirdLabReportingFacility= ORD3_R3 + delimiter1+ "ThirdLabReportingFacility";
	 
	private	static String LAB163_R4 = "LAB163_R4";
	protected static String LAB163_R4__FourthLabSpecimenDateLAB163= LAB163_R4 + delimiter1+ "FourthLabSpecimenDateLAB163";
	private	static String LAB220_R4 = "LAB220_R4";
	protected static String LAB220_R4__FourthLabTestNameLAB220= LAB220_R4 + delimiter1+ "FourthLabTestNameLAB220";
	private	static String Lab_Result_R4 = "Lab_Result_R4";
	protected static String Lab_Result_R4__FourthLabTestResultLAB220= Lab_Result_R4 + delimiter1+ "FourthLabTestNameLAB220";
	private	static String ORD3_R4 = "ORD3_R4";
	protected static String ORD3_R4__FourthLabReportingFacility= ORD3_R4 + delimiter1+ "FourthLabReportingFacility";	
	
	/******************************************************Treatments******************************************************/
	private	static String TR101_R1 = "TR101_R1";
	protected static String TR101_1__FirstTreatmentTRT101_TRT100_TRT101_TRT104_OrderingProviderName= TR101_R1 + delimiter1+ "FirstTreatmentTRT101+TRT100+TRT101+TRT104/OrderingProviderName";	
	private	static String TR101_R2 = "TR101_R2";
	protected static String TR101_2__SecondTreatmentTRT101_TRT100_TRT101_TRT104_OrderingProviderName= TR101_R2 + delimiter1+ "SecondTreatmentTRT101+TRT100+TRT101+TRT104/OrderingProviderName";	
	private	static String TR101_R3 = "TR101_R3";
	protected static String TR101_3__ThirdTreatmentTRT101_TRT100_TRT101_TRT104_OrderingProviderName = TR101_R3 + delimiter1+ "ThirdTreatmentTRT101+TRT100+TRT101+TRT104/OrderingProviderName";	
	private	static String TR101_R4 = "TR101_R4";
	protected static String TR101_4__FourthTreatmentTRT101_TRT100_TRT101_TRT104_OrderingProviderName= TR101_R4 + delimiter1+ "ThirdTreatmentTRT101+TRT100+TRT101+TRT104/OrderingProviderName";
	
	/******************************************************Exposure******************************************************/
	
	private	static String NBS118 = "NBS118";//Field Follow Up form constant
	protected static String NBS118__ExposureFirstNBS118= NBS118 + delimiter1+ "ExposureFirstNBS118";
	private	static String NBS119 = "NBS119";//Field Follow Up form constant
	protected static String NBS119__ExposureFreqNBS119= NBS119 + delimiter1+ "ExposureFreqNBS119";
	private	static String NBS120 = "NBS120";//Field Follow Up form constant
	protected static String NBS120__ExposureLastNBS120= NBS120 + delimiter1+ "ExposureLastNBS120";
	
	private	static String NBS121 = "NBS121";//Field Follow Up form constant
	protected static String NBS121__ExposureFirstNBS121= NBS121 + delimiter1+ "ExposureFirstNBS121";
	private	static String NBS122 = "NBS122";//Field Follow Up form constant
	protected static String NBS122__ExposureFreqNBS122= NBS122 + delimiter1+ "ExposureFreqNBS119";
	private	static String NBS123 = "NBS123";//Field Follow Up form constant
	protected static String NBS123__ExposureLastNBS123= NBS123 + delimiter1+ "ExposureLastNBS120";
	
	
	/******************************************************Original Patient ID Number******************************************************/
	
	private	static String NBS117 = "NBS117";
	protected static String NBS117__OriginalPatientIdNumberNBS117= NBS117 + delimiter1+ "NBS117__OriginalPatientIdNumberNBS117";
	
	/******************************************************Pregnant******************************************************/
	
	private	static String INV178_CD = "INV178_CD";
	protected static String INV178__PatientPregnantINV178 = INV178_CD + delimiter1+ "PatientPregnantINV178";
	private	static String NBS128 = "NBS128";
	protected static String NBS128__PatientPregnantWeeksNBS128 = NBS128 + delimiter1+ "PatientPregnantNBS128";
	
	
	/******************************************************900 Case Status******************************************************/
	
	private	static String NBS153_CD = "NBS153_CD";//Field Follow Up form constant. Common with different value.
	protected static String NBS153_CD__900CasesStatusNBS153 = NBS153_CD + delimiter1+ "900CasesStatusNBS153";
	
	/******************************************************Referral Basis******************************************************/

	private	static String REFBASIS_P_CD = "RefBasis_P_CD";//Field Follow Up form constant
	protected static String REFBASIS_P_CD__Partner = REFBASIS_P_CD + delimiter1+ "Partner";
	private	static String NBS110_P_CD = "NBS110_P_CD";//Field Follow Up form constant
	protected static String NBS110_P_CD__PartnerNBS110 = NBS110_P_CD + delimiter1+ "PartnerNBS110";
	private	static String NBS150_P_1_CDT = "NBS150_P_1_CDT";//Field Follow Up form constant
	protected static String NBS150_P_1_CDT__PartnerNBS150 = NBS150_P_1_CDT + delimiter1+ "PartnerNBS150";
	private	static String NBS150_P_2_CDT = "NBS150_P_2_CDT";//Field Follow Up form constant
	protected static String NBS150_P_2_CDT__PartnerNBS150 = NBS150_P_2_CDT + delimiter1+ "PartnerNBS150";

	private	static String REFBASIS_A_CD = "RefBasis_A_CD";//Field Follow Up form constant
	protected static String REFBASIS_A_CD__SA = REFBASIS_A_CD + delimiter1+ "SA";
	private	static String NBS110_A_CD = "NBS110_A_CD";//Field Follow Up form constant
	protected static String NBS110_A_CD__SANBS110 = NBS110_A_CD + delimiter1+ "SANBS110";
	private	static String NBS150_A_1_CDT = "NBS150_A_1_CDT";//Field Follow Up form constant
	protected static String NBS150_A_1_CDT__SANBS110 = NBS150_A_1_CDT + delimiter1+ "SANBS150";
	private	static String NBS150_A_2_CDT = "NBS150_A_2_CDT";//Field Follow Up form constant
	protected static String NBS150_A_2_CDT__SANBS110 = NBS150_A_2_CDT + delimiter1+ "SANBS150";

	private	static String REFBASIS_T_CD = "RefBasis_T_CD";//Field Follow Up form constant
	protected static String REFBASIS_T_CD__PositiveLabTest = REFBASIS_T_CD + delimiter1+ "PositiveLabTest";
	private	static String NBS110_T_CD = "NBS110_T_CD";//Field Follow Up form constant
	protected static String NBS110_T_CD__PositiveLabTestNBS110= NBS110_T_CD + delimiter1+ "PositiveLabTestNBS110";
	private	static String NBS150_T_1_CDT = "NBS150_T_1_CDT";//Field Follow Up form constant
	protected static String NBS150_T_1_CDT__PositiveLabTestNBS110 = NBS150_T_1_CDT + delimiter1+ "PositiveLabTestNBS150";
	private	static String NBS150_T_2_CDT = "NBS150_T_2_CDT";//Field Follow Up form constant
	protected static String NBS150_T_2_CDT__PositiveLabTestNBS110 = NBS150_T_2_CDT + delimiter1+ "PositiveLabTestNBS150";

	private	static String REFBASIS_O_CD = "RefBasis_O_CD";//Field Follow Up form constant
	protected static String REFBASIS_O_CD__OOJ = REFBASIS_O_CD + delimiter1+ "OOJICCR";
	private	static String NBS179_O_CD = "NBS179_O_CD";//Field Follow Up form constant
	protected static String NBS179_O_CD__OOJNBS110 = NBS179_O_CD + delimiter1+ "OOJICCRNBS179";
	private	static String NBS150_O_1_CDT = "NBS150_O_1_CDT";//Field Follow Up form constant
	protected static String NBS150_O_1_CDT__OOJNBS110 = NBS150_O_1_CDT + delimiter1+ "OOJICCRNBS150";
	private	static String NBS150_O_2_CDT = "NBS150_O_2_CDT";//Field Follow Up form constant
	protected static String NBS150_O_2_CDT__OOJNBS110 = NBS150_O_2_CDT + delimiter1+ "OOJICCRNBS150";
	
	/******************************************************Diseases******************************************************/

	private	static String IXS102_FR1_1 = "IXS102_FR1_1";//Field Follow Up form constant
	protected static String IXS102_FR_1__InterviewerNumberIXS102 = IXS102_FR1_1 + delimiter1+ "InterviewerNumberIXS102";
	private	static String INV147_1 = "INV147_1";//Field Follow Up form constant. Common with different value.
	protected static String INV147_1__DateInitiatedINV147 = INV147_1 + delimiter1+ "DateInitiatedINV147";
	private	static String IXS105_1_CDT = "IXS105_1_CDT";//Field Follow Up form constant
	protected static String IXS105_1_CDT__TypeInterviewIXS105 = IXS105_1_CDT + delimiter1+ "TypeInterviewIXS105";
	private	static String NBS177_1_CD = "NBS177_1_CD";//Field Follow Up form constant. Common with different value.
	protected static String NBS177_1_CD__TypeReferralNBS177 = NBS177_1_CD + delimiter1+ "TypeReferralNBS177";
	private	static String NBS173_1_CD = "NBS173_1_CD";//Field Follow Up form constant. Common with different value.
	protected static String NBS173_1_CD__DispositionNBS173 = NBS173_1_CD + delimiter1+ "DispositionNBS173";
	private	static String NBS174_1 = "NBS174_1";//Field Follow Up form constant. Common with different value.
	protected static String NBS174_1__DateNBS174 = NBS174_1 + delimiter1+ "DateNBS174";
	private	static String New_CaseNo_1 = "New_CaseNo_1";//Field Follow Up form constant
	protected static String New_CaseNo_1__DateNBS174 = New_CaseNo_1 + delimiter1+ "NewCaseNo1";
	private static String NBS136="NBS136";
	private	static String NBS136_1_CD = "NBS136_1_CD";//Field Follow Up form constant. Common with different value.
	protected static String NBS136_1_CD__DxNBS136 = NBS136_1_CD + delimiter1+ "DxNBS136";
	private	static String NBS175_1 = "NBS175_1";//Field Follow Up form constant
	protected static String NBS175_1__WorkerNBS175 = NBS175_1 + delimiter1+ "WorkerNBS175";
	
	private	static String IXS102_FR1_2 = "IXS102_FR1_2";//Field Follow Up form constant
	protected static String IXS102_FR_2__InterviewerNumberIXS102 = IXS102_FR1_2 + delimiter1+ "InterviewerNumberIXS102";
	private	static String INV147_2 = "INV147_2";
	//Field Follow Up form constant. Common with different value.
	protected static String INV147_2__DateInitiatedINV147 = INV147_2 + delimiter1+ "DateInitiatedINV147";
	private	static String IXS105_2_CDT = "IXS105_2_CDT";//Field Follow Up form constant
	protected static String IXS105_2_CDT__TypeInterviewIXS105 = IXS105_2_CDT + delimiter1+ "TypeInterviewIXS105";
	private	static String NBS177_2_CD = "NBS177_2_CD";//Field Follow Up form constant. Common with different value.
	protected static String NBS177_2_CD__TypeReferralNBS177 = NBS177_2_CD + delimiter1+ "TypeReferralNBS177";
	private	static String NBS173_2_CD = "NBS173_2_CD";
	//Field Follow Up form constant. Common with different value.
	protected static String NBS173_2_CD__DispositionNBS173 = NBS173_2_CD + delimiter1+ "DispositionNBS173";
	private	static String NBS174_2 = "NBS174_2";
	//Field Follow Up form constant. Common with different value.
	protected static String NBS174_2__DateNBS174 = NBS174_2 + delimiter1+ "DateNBS174";
	private	static String New_CaseNo_2 = "New_CaseNo_2";//Field Follow Up form constant
	protected static String New_CaseNo_2__DateNBS174 = New_CaseNo_2 + delimiter1+ "NewCaseNo1";
	private	static String NBS136_2_CD = "NBS136_2_CD";//Field Follow Up form constant. Common with different value.
	protected static String NBS136_2_CD__DxNBS136 = NBS136_2_CD + delimiter1+ "DxNBS136";
	private	static String NBS175_2 = "NBS175_2";//Field Follow Up form constant
	protected static String NBS175_2__WorkerNBS175 = NBS175_2 + delimiter1+ "WorkerNBS175";
	
	/******************************************************OOJ******************************************************/
	
	private	static String NBS160 = "NBS160";//Field Follow Up form constant. Common with different value.
	protected static String NBS160__FRNumberNBS160 = NBS160 + delimiter1+ "FRNumberNBS160";

	private	static String NBS180 = "NBS180";//Field Follow Up form constant. Common with different value.
	protected static String NBS180__OOJNoNBS180 = NBS180 + delimiter1+ "OOJNoNBS180";

	private	static String NBS179 = "NBS179";//Field Follow Up form constant. Common with different value.
	protected static String NBS179__OOJAreaNBS179 = NBS179 + delimiter1+ "OOJAreaNBS179";
	private	static String NBS181 = "NBS181";//Field Follow Up form constant. Common with different value.
	protected static String NBS181__DueDateNBS181 = NBS181 + delimiter1+ "DueDateNBS181";
	private	static String NBS111 = "NBS111";//Field Follow Up form constant. Common with different value.
	protected static String NBS111__InitiatingAgencyNBS111 = NBS111 + delimiter1+ "InitiatingAgencyNBS111";
	private	static String INV107 = "INV107";//Field Follow Up form constant. Common with different value.
	protected static String INV107__InvestAgencyNBS107 = INV107 + delimiter1+ "InitiatingAgencyNBS111";
	private	static String NBS144 = "NBS144";//Field Follow Up form constant. Common with different value.
	protected static String NBS144__ClinicCodeNBS144 = NBS144 + delimiter1+ "ClinicCodeNBS144";
	private	static String NBS178_CD = "NBS178_CD";//Field Follow Up form constant. Common with different value.
	protected static String NBS178_CD__InternetOutcomeNBS178 = NBS178_CD + delimiter1+ "InternetOutcomeNBS144";
	private	static String NBS264_CD = "NBS264_CD";//Field Follow Up form constant
	protected static String NBS264_CD__PostTestCounseledNBS264 = NBS264_CD + delimiter1+ "PostTestCounseledNBS264";
	
	
	/******************************************************Notes******************************************************/
	private	static String NBS152 = "NBS152";//Field Follow Up form constant. Common with different value.
	protected static String NBS152__SurveillanceNotesNBS152 = NBS152 + delimiter1+ "SurveillanceNotesNBS152";
	private	static String NBS185 = "NBS185";//Field Follow Up form constant. Common with different value.
	protected static String NBS185__FieldFollowUpNotesNBS185= NBS185 + delimiter1+ "FieldFollowUpNotesNBS185";
	private	static String NBS268 = "NBS268";//Field Follow Up form constant. Common with different value.
	protected static String NBS268__FieldSupervisoryReviewCommentsNBS268 = NBS268 + delimiter1+ "NBS268__FieldSupervisoryReviewCommentsNBS268";
	
	
	/******************************************************900 Information******************************************************/
	
	private	static String NBS257_CD = "NBS257_CD";//Field Follow Up form constant
	protected static String NBS257_CD__Interviewed = NBS257_CD + delimiter1+ "Interviewed";
	private	static String IXS101_1 = "IXS101_1";//Field Follow Up form constant
	protected static String IXS101_1__900PSInterviewDateIXS101 = IXS101_1 + delimiter1+ "900PSInterviewDateIXS101";
	private	static String BIRTH_SEX_CDT = "Birth_Sex_CDT";//Field Follow Up form constant
	protected static String BIRTH_SEX_CDT__BirthSex = BIRTH_SEX_CDT + delimiter1+ "BirthSex";
	private	static String ADDL_SEX = "Addl_Sex";//Field Follow Up form constant
	protected static String ADDL_SEX__AddlSex = ADDL_SEX + delimiter1+ "AddlSex";
	private	static String NBS143_CD = "NBS143_CD";
	//Field Follow Up form constant. Common with different value.
	protected static String NBS143_CD__NotifiableNBS143 = NBS143_CD + delimiter1+ "NotifiableNBS143";
	private	static String NBS167_CD = "NBS167_CD";//Field Follow Up form constant. Common with different value.
	protected static String NBS167_CD__PlanNBS167 = NBS167_CD + delimiter1+ "PlanNBS167";
	private	static String NBS177_CD = "NBS177_CD";//Field Follow Up form constant. Common with different value.
	protected static String NBS177_CD__ActualMethodNBS177 = NBS177_CD + delimiter1+ "ActualMethodNBS177";
	
	
	private	static String STD106_CD = "STD106_CD";//Field Follow Up form constant
	protected static String STD106_CD__SelfReportedResultsSTD106 = STD106_CD + delimiter1+ "SelfReportedResultsSTD106";
	private	static String NBS259 = "NBS259";//Field Follow Up form constant
	protected static String NBS259__DateOfLast900NBS259 = NBS259 + delimiter1+ "DateOfLast900NBS259";
	private	static String NBS260_CDT = "NBS260_CDT";//Field Follow Up form constant
	protected static String NBS260_CDT__Referral1NBS260 = NBS260_CDT + delimiter1+ "Referral1NBS260";
	private	static String NBS261 = "NBS261";//Field Follow Up form constant
	protected static String NBS261__ReferralDate1NBS261 = NBS261 + delimiter1+ "ReferralDate1NBS261";
	private	static String NBS262_CDT = "NBS262_CDT";//Field Follow Up form constant
	protected static String NBS262_CDT__TestNBS262 = NBS262_CDT + delimiter1+ "TestNBS262";
	private	static String NBS263_CDT = "NBS263_CDT";//Field Follow Up form constant
	protected static String NBS263_CDT__ResultNBS263 = NBS263_CDT + delimiter1+ "ResultNBS263";
	private	static String NBS265_CDT = "NBS265_CDT";//Field Follow Up form constant
	protected static String NBS265_CDT__PostNBS265 = NBS265_CDT + delimiter1+ "PostNBS265";
	private	static String NBS266_CDT = "NBS266_CDT";//Field Follow Up form constant
	protected static String NBS266_CDT__Referral2NBS266 = NBS266_CDT + delimiter1+ "Referral2NBS266";
	private	static String NBS267_CD = "NBS267_CD";//Field Follow Up form constant
	protected static String NBS267_CD__FirstApptNBS267 = NBS267_CD + delimiter1+ "FirstApptNBS267";
	private	static String NBS229_CD = "NBS229_CD";//Field Follow Up form constant
	protected static String NBS229_CD__WasBehavioralRiskAssesedNBS229 = NBS229_CD + delimiter1+ "WasBehavioralRiskAssesedNBS229";
	private	static String STD107_CD = "STD107_CD";//Field Follow Up form constant
	protected static String STD107_CD__MaleSTD107 = STD107_CD + delimiter1+ "MaleSTD107";
	private	static String STD108_CD = "STD108_CD";//Field Follow Up form constant
	protected static String STD108_CD__FemaleSTD108 = STD108_CD + delimiter1+ "FemaleSTD108";
	private	static String NBS230_CD = "NBS230_CD";//Field Follow Up form constant
	protected static String NBS230_CD__TransgenderNBS230 = NBS230_CD + delimiter1+ "TransgenderNBS230";
	private	static String NBS231_CD = "NBS231_CD";//Field Follow Up form constant
	protected static String NBS231_CD__CondomNBS231 = NBS231_CD + delimiter1+ "CondomNBS231";
	private	static String STD114_CD = "STD114_CD";//Field Follow Up form constant
	protected static String STD114_CD__IDUSTD114 = STD114_CD + delimiter1+ "IDUSTD114";
	private	static String NBS232_CD = "NBS232_CD";//Field Follow Up form constant
	protected static String NBS232_CD__NIRNBS232 = NBS232_CD + delimiter1+ "NIRNBS232";
	private	static String BR_OTHER = "BR_Other";//Field Follow Up form constant
	protected static String BR_OTHER__OtherBROTHER = BR_OTHER + delimiter1+ "OtherBROTHER";
	

	public static Map<String, String> mappedFieldFollowUp =new HashMap<String, String>();
	
	/**
	 * MAPS
	 */
	
	private static Map<String, String> fieldFollowUpFieldsMap= new HashMap<String, String>();
	private static Map<String, String> referralBasisMap = new HashMap <String, String>();
	private static Map<String, String> interviewMap = new HashMap <String, String>();
	private static Map<String, String> IXS105NbsInterviewTypeMap = new HashMap <String, String>();
	private static Map<String, String> NBS110ReferralBasisMap= new HashMap<String, String>();
	private static Map<String, String> NBS110ReferralBasisMapOOJ= new HashMap<String, String>();
	private static Map<String, String> NBS110ReferralBasisOriginMap= new HashMap<String, String>();
	private static Map<String, String> NBS110ReferralBasisCdMap= new HashMap<String, String>();
	private static Map<String, String> NBS110ReferralBasisDisease1Map= new HashMap<String, String>();
	private static Map<String, String> NBS110ReferralBasisDisease2Map= new HashMap<String, String>();
	
	
	/**
	 * Methods
	 */
	
	/**
	 * initializeFieldFollowUpValues: this method initializes the fieldFollowUpFieldsMap map that will be send to the common form
	 * for populating the data
	 * @throws NEDSSAppException
	 */
	
	private static void initializeFieldFollowUpValues() throws NEDSSAppException{
		try{
			
		if(fieldFollowUpFieldsMap.size()==0){

			/******************************************************Patient Demographics******************************************************/
				//Name
			fieldFollowUpFieldsMap.put(DEM102__PatientLastNameDEM102, DEM102);
			fieldFollowUpFieldsMap.put(DEM104__PatientFirstNameDEM104, DEM104);
			fieldFollowUpFieldsMap.put(DEM105__PatientMiddleNameDEM105, DEM105);
			fieldFollowUpFieldsMap.put(DEM250__PatientAliasNameDEM250, DEM250);
			
				//Address
			fieldFollowUpFieldsMap.put(DEM159_160__PatientStreetDEM159, DEM159_DEM160); 
			fieldFollowUpFieldsMap.put(DEM161__PatientCityDEM161, DEM161);
			fieldFollowUpFieldsMap.put(DEM162__PatientStateDEM162, DEM162);
			fieldFollowUpFieldsMap.put(DEM163__PatientZipDEM163, DEM163);
			
				//Phone number
			fieldFollowUpFieldsMap.put(NBS006__PatientCellNBS006_UseCd, NBS006);
			fieldFollowUpFieldsMap.put(DEM177__PatientHmPhDEM177_UseCd, DEM177);
			
				//Reported age
			fieldFollowUpFieldsMap.put(INV2001__PatientReportedAge, INV2001);
			fieldFollowUpFieldsMap.put(DEM115__PatientDOB, DEM115);
			
				//Race
			fieldFollowUpFieldsMap.put(DEM152_AI_CDT__PatientRaceDEM152, DEM152_AI_CDT);
			fieldFollowUpFieldsMap.put(DEM152_A_CDT__PatientRaceDEM152, DEM152_A_CDT);
			fieldFollowUpFieldsMap.put(DEM152_B_CDT__PatientRaceDEM152, DEM152_B_CDT);
			fieldFollowUpFieldsMap.put(DEM152_W_CDT__PatientRaceDEM152, DEM152_W_CDT);
			fieldFollowUpFieldsMap.put(DEM152_O_CDT__PatientRaceDEM152, DEM152_O_CDT);
			fieldFollowUpFieldsMap.put(DEM152_NH_CDT__PatientRaceDEM152, DEM152_NH_CDT);
			fieldFollowUpFieldsMap.put(DEM152_U_CDT__PatientRaceDEM152, DEM152_U_CDT);
			fieldFollowUpFieldsMap.put(DEM152_R_CDT__PatientRaceDEM152, DEM152_R_CDT);
			fieldFollowUpFieldsMap.put(DEM152_D_CDT__PatientRaceDEM152, DEM152_D_CDT);
			
				//Ethnicity
			fieldFollowUpFieldsMap.put(DEM155_CDT__PatientEthnicityDEM155, DEM155_CDT);
			fieldFollowUpFieldsMap.put(NBS273_CDT__PatientEthnicityUnkownReasonNBS273, NBS273_CDT);
			
				//Gender
			fieldFollowUpFieldsMap.put(DEM113_CD__PatientCurrentSexDEM113, DEM113_CD);
			fieldFollowUpFieldsMap.put(NBS274_CD__PatientTransgenderNBS274, NBS274_CD);
			fieldFollowUpFieldsMap.put(NBS272_CD__PatientSexUnkownReasonNBS272, NBS272_CD);
			
				//Marital Status
			fieldFollowUpFieldsMap.put(DEM140_CDT__PatientMaritalStatusDEM140, DEM140_CDT);
			
				//Email
			fieldFollowUpFieldsMap.put(DEM182__EmailAddressDEM182, DEM182);
			fieldFollowUpFieldsMap.put(INTERNET_SITE, INTERNET_SITE__InternetSiteDEM183);
			
				//Appearance
			fieldFollowUpFieldsMap.put(NBS155__HeightNBS155, NBS155);
			fieldFollowUpFieldsMap.put(NBS156__SizeNBS156, NBS156);
			fieldFollowUpFieldsMap.put(NBS157__HairNBS157, NBS157);
			fieldFollowUpFieldsMap.put(NBS158__ComplexionNBS158, NBS158);
			
				//Employment
			fieldFollowUpFieldsMap.put(EMPLOYMENT__PatientWorkPhoneNBS002_PatientWorkPhoneExtensionNBS003, Employment_Phone);
			
				//Other_Info
			fieldFollowUpFieldsMap.put(OTHER_INFO__PhysicianNameAndAddressINV182_OtherIdentifyingInfoNBS159, Other_Info);

			/******************************************************Lab******************************************************/
			fieldFollowUpFieldsMap.put(LAB163_R1__FirstLabSpecimenDateLAB163, LAB163_R1);
			fieldFollowUpFieldsMap.put(LAB220_R1__FirstLabTestNameLAB220, LAB220_R1);
			fieldFollowUpFieldsMap.put(Lab_Result_R1__FirstLabTestResultLAB220, Lab_Result_R1);
			fieldFollowUpFieldsMap.put(ORD3_R1__FirstLabReportingFacility, ORD3_R1);
			
			fieldFollowUpFieldsMap.put(LAB163_R2__SecondLabSpecimenDateLAB163, LAB163_R2);
			fieldFollowUpFieldsMap.put(LAB220_R2__SecondLabTestNameLAB220, LAB220_R2);
			fieldFollowUpFieldsMap.put(Lab_Result_R2__SecondLabTestResultLAB220, Lab_Result_R2);
			fieldFollowUpFieldsMap.put(ORD3_R2__SecondLabReportingFacility, ORD3_R2);
			
			
			fieldFollowUpFieldsMap.put(LAB163_R3__ThirdLabSpecimenDateLAB163, LAB163_R3);
			fieldFollowUpFieldsMap.put(LAB220_R3__ThirdLabTestNameLAB220, LAB220_R3);
			fieldFollowUpFieldsMap.put(Lab_Result_R3__ThirdLabTestResultLAB220, Lab_Result_R3);
			fieldFollowUpFieldsMap.put(ORD3_R3__ThirdLabReportingFacility, ORD3_R3);
			
			fieldFollowUpFieldsMap.put(LAB163_R4__FourthLabSpecimenDateLAB163, LAB163_R4);
			fieldFollowUpFieldsMap.put(LAB220_R4__FourthLabTestNameLAB220, LAB220_R4);
			fieldFollowUpFieldsMap.put(Lab_Result_R4__FourthLabTestResultLAB220, Lab_Result_R4);
			fieldFollowUpFieldsMap.put(ORD3_R4__FourthLabReportingFacility, ORD3_R4);
			
			/******************************************************Treatment******************************************************/

			fieldFollowUpFieldsMap.put(TR101_1__FirstTreatmentTRT101_TRT100_TRT101_TRT104_OrderingProviderName,TR101_R1);
			fieldFollowUpFieldsMap.put(TR101_2__SecondTreatmentTRT101_TRT100_TRT101_TRT104_OrderingProviderName,TR101_R2);
			fieldFollowUpFieldsMap.put(TR101_3__ThirdTreatmentTRT101_TRT100_TRT101_TRT104_OrderingProviderName,TR101_R3);
			fieldFollowUpFieldsMap.put(TR101_4__FourthTreatmentTRT101_TRT100_TRT101_TRT104_OrderingProviderName,TR101_R4);
			
			/******************************************************Exposure******************************************************/
			
			fieldFollowUpFieldsMap.put(NBS118__ExposureFirstNBS118, NBS118);
			fieldFollowUpFieldsMap.put(NBS119__ExposureFreqNBS119, NBS119);
			fieldFollowUpFieldsMap.put(NBS120__ExposureLastNBS120, NBS120);
			fieldFollowUpFieldsMap.put(NBS121__ExposureFirstNBS121, NBS121);
			fieldFollowUpFieldsMap.put(NBS122__ExposureFreqNBS122, NBS122);
			fieldFollowUpFieldsMap.put(NBS123__ExposureLastNBS123, NBS123);
			
			
			/******************************************************Original Patient ID Number******************************************************/
			
			fieldFollowUpFieldsMap.put(NBS117__OriginalPatientIdNumberNBS117, NBS117);
			
			/******************************************************Pregnant******************************************************/
			
			fieldFollowUpFieldsMap.put(INV178__PatientPregnantINV178, INV178_CD);
			fieldFollowUpFieldsMap.put(NBS128__PatientPregnantWeeksNBS128, NBS128);
			
			/******************************************************900 Case Status******************************************************/
			
			fieldFollowUpFieldsMap.put(NBS153_CD__900CasesStatusNBS153, NBS153_CD);

			/******************************************************Referral Basis******************************************************/
			
			fieldFollowUpFieldsMap.put(REFBASIS_P_CD__Partner, REFBASIS_P_CD);
			fieldFollowUpFieldsMap.put(NBS110_P_CD__PartnerNBS110, NBS110_P_CD);
			fieldFollowUpFieldsMap.put(NBS150_P_1_CDT__PartnerNBS150, NBS150_P_1_CDT);
			fieldFollowUpFieldsMap.put(NBS150_P_2_CDT__PartnerNBS150, NBS150_P_2_CDT);
			
			fieldFollowUpFieldsMap.put(REFBASIS_A_CD__SA, REFBASIS_A_CD);
			fieldFollowUpFieldsMap.put(NBS110_A_CD__SANBS110, NBS110_A_CD);
			fieldFollowUpFieldsMap.put(NBS150_A_1_CDT__SANBS110, NBS150_A_1_CDT);
			fieldFollowUpFieldsMap.put(NBS150_A_2_CDT__SANBS110, NBS150_A_2_CDT);

			fieldFollowUpFieldsMap.put(REFBASIS_T_CD__PositiveLabTest, REFBASIS_T_CD);
			fieldFollowUpFieldsMap.put(NBS110_T_CD__PositiveLabTestNBS110, NBS110_T_CD);
			fieldFollowUpFieldsMap.put(NBS150_T_1_CDT__PositiveLabTestNBS110, NBS150_T_1_CDT);
			fieldFollowUpFieldsMap.put(NBS150_T_2_CDT__PositiveLabTestNBS110, NBS150_T_2_CDT);
			
			fieldFollowUpFieldsMap.put(REFBASIS_O_CD__OOJ, REFBASIS_O_CD);
			fieldFollowUpFieldsMap.put(NBS179_O_CD__OOJNBS110, NBS179_O_CD);
			fieldFollowUpFieldsMap.put(NBS150_O_1_CDT__OOJNBS110, NBS150_O_1_CDT);
			fieldFollowUpFieldsMap.put(NBS150_O_2_CDT__OOJNBS110, NBS150_O_2_CDT);
			
			/******************************************************Diseases******************************************************/
			
			fieldFollowUpFieldsMap.put(IXS102_FR_1__InterviewerNumberIXS102, IXS102_FR1_1);
			fieldFollowUpFieldsMap.put(INV147_1__DateInitiatedINV147, INV147_1);
			fieldFollowUpFieldsMap.put(IXS105_1_CDT__TypeInterviewIXS105, IXS105_1_CDT);
			fieldFollowUpFieldsMap.put(NBS177_1_CD__TypeReferralNBS177, NBS177_1_CD);
			fieldFollowUpFieldsMap.put(NBS173_1_CD__DispositionNBS173, NBS173_1_CD);
			fieldFollowUpFieldsMap.put(NBS174_1__DateNBS174, NBS174_1);
			fieldFollowUpFieldsMap.put(New_CaseNo_1__DateNBS174, New_CaseNo_1);
			fieldFollowUpFieldsMap.put(NBS136_1_CD__DxNBS136, NBS136_1_CD);
			fieldFollowUpFieldsMap.put(NBS175_1__WorkerNBS175, NBS175_1);
			
			fieldFollowUpFieldsMap.put(IXS102_FR_2__InterviewerNumberIXS102, IXS102_FR1_2);
			fieldFollowUpFieldsMap.put(INV147_2__DateInitiatedINV147, INV147_2);
			fieldFollowUpFieldsMap.put(IXS105_2_CDT__TypeInterviewIXS105, IXS105_2_CDT);
			fieldFollowUpFieldsMap.put(NBS177_2_CD__TypeReferralNBS177, NBS177_2_CD);
			fieldFollowUpFieldsMap.put(NBS173_2_CD__DispositionNBS173, NBS173_2_CD);
			fieldFollowUpFieldsMap.put(NBS174_2__DateNBS174, NBS174_2);
			fieldFollowUpFieldsMap.put(New_CaseNo_2__DateNBS174, New_CaseNo_2);
			fieldFollowUpFieldsMap.put(NBS136_2_CD__DxNBS136, NBS136_2_CD);
			fieldFollowUpFieldsMap.put(NBS175_2__WorkerNBS175, NBS175_2);
			
			/******************************************************OOJ******************************************************/
			
			fieldFollowUpFieldsMap.put(NBS160__FRNumberNBS160, NBS160);
			fieldFollowUpFieldsMap.put(NBS180__OOJNoNBS180, NBS180);
			fieldFollowUpFieldsMap.put(NBS179__OOJAreaNBS179, NBS179);
			fieldFollowUpFieldsMap.put(NBS181__DueDateNBS181, NBS181);
			fieldFollowUpFieldsMap.put(NBS111__InitiatingAgencyNBS111, NBS111);
			fieldFollowUpFieldsMap.put(INV107__InvestAgencyNBS107, INV107);
			fieldFollowUpFieldsMap.put(NBS144__ClinicCodeNBS144, NBS144);
			fieldFollowUpFieldsMap.put(NBS178_CD__InternetOutcomeNBS178, NBS178_CD);
			fieldFollowUpFieldsMap.put(NBS264_CD__PostTestCounseledNBS264, NBS264_CD);
			fieldFollowUpFieldsMap.put(NBS175_2__WorkerNBS175, NBS175_2);
			fieldFollowUpFieldsMap.put(NBS175_2__WorkerNBS175, NBS175_2);
			fieldFollowUpFieldsMap.put(NBS175_2__WorkerNBS175, NBS175_2);
			fieldFollowUpFieldsMap.put(NBS175_2__WorkerNBS175, NBS175_2);
			fieldFollowUpFieldsMap.put(NBS175_2__WorkerNBS175, NBS175_2);
			fieldFollowUpFieldsMap.put(NBS175_2__WorkerNBS175, NBS175_2);
			fieldFollowUpFieldsMap.put(NBS175_2__WorkerNBS175, NBS175_2);
			fieldFollowUpFieldsMap.put(NBS175_2__WorkerNBS175, NBS175_2);
			
			/******************************************************Notes******************************************************/
			
			fieldFollowUpFieldsMap.put(NBS152__SurveillanceNotesNBS152, NBS152);
			fieldFollowUpFieldsMap.put(NBS185__FieldFollowUpNotesNBS185, NBS185);
			fieldFollowUpFieldsMap.put(NBS268__FieldSupervisoryReviewCommentsNBS268, NBS268);
						
			/******************************************************900******************************************************/
			
			fieldFollowUpFieldsMap.put(NBS257_CD__Interviewed, NBS257_CD);
			fieldFollowUpFieldsMap.put(IXS101_1__900PSInterviewDateIXS101, IXS101_1);
			fieldFollowUpFieldsMap.put(BIRTH_SEX_CDT__BirthSex, BIRTH_SEX_CDT);
			fieldFollowUpFieldsMap.put(ADDL_SEX__AddlSex, ADDL_SEX);
			fieldFollowUpFieldsMap.put(NBS143_CD__NotifiableNBS143, NBS143_CD);
			fieldFollowUpFieldsMap.put(NBS167_CD__PlanNBS167, NBS167_CD);
			fieldFollowUpFieldsMap.put(NBS177_CD__ActualMethodNBS177, NBS177_CD);
			fieldFollowUpFieldsMap.put(STD106_CD__SelfReportedResultsSTD106, STD106_CD);
			fieldFollowUpFieldsMap.put(NBS259__DateOfLast900NBS259, NBS259);
			fieldFollowUpFieldsMap.put(NBS260_CDT__Referral1NBS260, NBS260_CDT);
			fieldFollowUpFieldsMap.put(NBS261__ReferralDate1NBS261, NBS261);
			fieldFollowUpFieldsMap.put(NBS262_CDT__TestNBS262, NBS262_CDT);
			fieldFollowUpFieldsMap.put(NBS263_CDT__ResultNBS263, NBS263_CDT);
			fieldFollowUpFieldsMap.put(NBS265_CDT__PostNBS265, NBS265_CDT);
			fieldFollowUpFieldsMap.put(NBS266_CDT__Referral2NBS266, NBS266_CDT);
			fieldFollowUpFieldsMap.put(NBS267_CD__FirstApptNBS267, NBS267_CD);
			fieldFollowUpFieldsMap.put(NBS229_CD__WasBehavioralRiskAssesedNBS229, NBS229_CD);
			fieldFollowUpFieldsMap.put(STD107_CD__MaleSTD107, STD107_CD);
			fieldFollowUpFieldsMap.put(STD108_CD__FemaleSTD108, STD108_CD);
			fieldFollowUpFieldsMap.put(NBS230_CD__TransgenderNBS230, NBS230_CD);
			fieldFollowUpFieldsMap.put(NBS231_CD__CondomNBS231, NBS231_CD);
			fieldFollowUpFieldsMap.put(STD114_CD__IDUSTD114, STD114_CD);
			fieldFollowUpFieldsMap.put(NBS232_CD__NIRNBS232, NBS232_CD);
			fieldFollowUpFieldsMap.put(BR_OTHER__OtherBROTHER, BR_OTHER);
		}
		}catch(Exception e){
			logger.error(e);
			logger.error("CDCFieldRecordForm.initializeFieldFollowUpValues Exception thrown, "+ e);
			throw new NEDSSAppException("CDCFieldRecordForm.initializeFieldFollowUpValues Exception thrown, ", e);
			
		}
	}
	

	/**
	 * populateReferralBasisValues(): get the answers for Referral Basis. Also, it sets Original Patient ID Number since the
	 * information comes from contactOriginalInvestigationMap
	 * @param proxyVO
	 * @param referralBasisMap
	 * @param req: request for getting the session and then get the disease code
	 * @return values from contact record and original patient investigation if started by a contact record
	 * @throws NEDSSAppException
	 */
	
	public static Map<String, String> populateReferralBasisValues(PageProxyVO proxyVO, HttpServletRequest req) throws NEDSSAppException {
		Map<String,String> contactOriginalInvestigationMap = new HashMap<String,String>();
		
		try{

			referralBasisMap=new HashMap<String,String>();
			PageActProxyVO actProxyVO = (PageActProxyVO) proxyVO;
			contactOriginalInvestigationMap = 
					checkInvStartedFromConRec(proxyVO, 1, req.getSession());	

			String conCond = null;
			if (contactOriginalInvestigationMap != null)
				conCond = contactOriginalInvestigationMap.get(NBS136_ORIGINAL_CONDITION+1+CODED_VALUE);

			if(NBS110ReferralBasisMap.size()==0){
				NBS110ReferralBasisMap.put("P1", "P");//Check if NBS110 (Referral Basis) = P1, P2 or P3 and named in Contact Record
				NBS110ReferralBasisMap.put("P2", "P");//Check if NBS110 (Referral Basis) = P1, P2 or P3 and named in Contact Record
				NBS110ReferralBasisMap.put("P3", "P");//Check if NBS110 (Referral Basis) = P1, P2 or P3 and named in Contact Record
				NBS110ReferralBasisMap.put("S1", "A");//Check if NBS110 (Referral Basis) = S1, S2, S3, A1, A2, A3 or C1 and named in Contact Record
				NBS110ReferralBasisMap.put("S2", "A");//Check if NBS110 (Referral Basis) = S1, S2, S3, A1, A2, A3 or C1 and named in Contact Record
				NBS110ReferralBasisMap.put("S3", "A");//Check if NBS110 (Referral Basis) = S1, S2, S3, A1, A2, A3 or C1 and named in Contact Record
				NBS110ReferralBasisMap.put("A1", "A");//Check if NBS110 (Referral Basis) = S1, S2, S3, A1, A2, A3 or C1 and named in Contact Record
				NBS110ReferralBasisMap.put("A2", "A");//Check if NBS110 (Referral Basis) = S1, S2, S3, A1, A2, A3 or C1 and named in Contact Record
				NBS110ReferralBasisMap.put("A3", "A");//Check if NBS110 (Referral Basis) = S1, S2, S3, A1, A2, A3 or C1 and named in Contact Record
				NBS110ReferralBasisMap.put("C1", "A");//Check if NBS110 (Referral Basis) = S1, S2, S3, A1, A2, A3 or C1 and named in Contact Record
				NBS110ReferralBasisMap.put("T1", "T");//Check if NBS110 (Referral Basis) = T1 or T2
				NBS110ReferralBasisMap.put("T2", "T");//Check if NBS110 (Referral Basis) = T1 or T2
			}
			if(NBS110ReferralBasisMapOOJ.size()==0){
				NBS110ReferralBasisMapOOJ.put("P1", "OOJ");//Check if NBS110 (Referral Basis) =  P1, P2, P3, S1, S2, S3, A1, A2, A3 or C1 and the investigation was started from the Patient File.
				NBS110ReferralBasisMapOOJ.put("P2", "OOJ");//Check if NBS110 (Referral Basis) =  P1, P2, P3, S1, S2, S3, A1, A2, A3 or C1 and the investigation was started from the Patient File.
				NBS110ReferralBasisMapOOJ.put("P3", "OOJ");//Check if NBS110 (Referral Basis) =  P1, P2, P3, S1, S2, S3, A1, A2, A3 or C1 and the investigation was started from the Patient File.
				NBS110ReferralBasisMapOOJ.put("S1", "OOJ");//Check if NBS110 (Referral Basis) =  P1, P2, P3, S1, S2, S3, A1, A2, A3 or C1 and the investigation was started from the Patient File.
				NBS110ReferralBasisMapOOJ.put("S2", "OOJ");//Check if NBS110 (Referral Basis) =  P1, P2, P3, S1, S2, S3, A1, A2, A3 or C1 and the investigation was started from the Patient File.
				NBS110ReferralBasisMapOOJ.put("S3", "OOJ");//Check if NBS110 (Referral Basis) =  P1, P2, P3, S1, S2, S3, A1, A2, A3 or C1 and the investigation was started from the Patient File.
				NBS110ReferralBasisMapOOJ.put("A1", "OOJ");//Check if NBS110 (Referral Basis) =  P1, P2, P3, S1, S2, S3, A1, A2, A3 or C1 and the investigation was started from the Patient File.
				NBS110ReferralBasisMapOOJ.put("A2", "OOJ");//Check if NBS110 (Referral Basis) =  P1, P2, P3, S1, S2, S3, A1, A2, A3 or C1 and the investigation was started from the Patient File.
				NBS110ReferralBasisMapOOJ.put("A3", "OOJ");//Check if NBS110 (Referral Basis) =  P1, P2, P3, S1, S2, S3, A1, A2, A3 or C1 and the investigation was started from the Patient File.
				NBS110ReferralBasisMapOOJ.put("C1", "OOJ");//Check if NBS110 (Referral Basis) =  P1, P2, P3, S1, S2, S3, A1, A2, A3 or C1 and the investigation was started from the Patient File.

			}
			if(NBS110ReferralBasisOriginMap.size()==0){
				NBS110ReferralBasisOriginMap.put("P", REFBASIS_P_CD);//Print actual referral basis code if Referral Basis is P1, P2 or P3 and named in Contact Record. If co-infection is selected, separate the referral bases by a ","
				NBS110ReferralBasisOriginMap.put("A", REFBASIS_A_CD);//Print actual referral basis code if Referral Basis is S1, S2, S3, A1, A2, A3 or C1 and named in Contact Record. If co-infection is selected, separate the referral bases by a ","
				NBS110ReferralBasisOriginMap.put("T", REFBASIS_T_CD);//Print actual referral basis code if Referral Basis is  T1 or T2.  If co-infection is selected, separate the referral bases by a ","
				NBS110ReferralBasisOriginMap.put("OOJ", REFBASIS_O_CD);//Print actual referral basis code if Referral Basis is P1, P2, P3, S1, S2, S3, A1, A2, A3 or C1 and the investigation was started from the Patient File (not from contact record). If co-infection is selected, separate the referral bases by a ","

			}
			if(NBS110ReferralBasisCdMap.size()==0){
				NBS110ReferralBasisCdMap.put("P", NBS110_P_CD);//Print actual referral basis code if Referral Basis is P1, P2 or P3 and named in Contact Record. If co-infection is selected, separate the referral bases by a ","
				NBS110ReferralBasisCdMap.put("A", NBS110_A_CD);//Print actual referral basis code if Referral Basis is S1, S2, S3, A1, A2, A3 or C1 and named in Contact Record. If co-infection is selected, separate the referral bases by a ","
				NBS110ReferralBasisCdMap.put("T", NBS110_T_CD);//Print actual referral basis code if Referral Basis is  T1 or T2.  If co-infection is selected, separate the referral bases by a ","
				NBS110ReferralBasisCdMap.put("OOJ", NBS179_O_CD);//Print actual referral basis code if Referral Basis is P1, P2, P3, S1, S2, S3, A1, A2, A3 or C1 and the investigation was started from the Patient File (not from contact record). If co-infection is selected, separate the referral bases by a ","
			}
			if(NBS110ReferralBasisDisease1Map.size()==0){
				NBS110ReferralBasisDisease1Map.put("P", NBS150_P_1_CDT);//Print actual referral basis code if Referral Basis is P1, P2 or P3 and named in Contact Record. If co-infection is selected, separate the referral bases by a ","
				NBS110ReferralBasisDisease1Map.put("A", NBS150_A_1_CDT);//Print actual referral basis code if Referral Basis is S1, S2, S3, A1, A2, A3 or C1 and named in Contact Record. If co-infection is selected, separate the referral bases by a ","
				NBS110ReferralBasisDisease1Map.put("T", NBS150_T_1_CDT);//Print actual referral basis code if Referral Basis is  T1 or T2.  If co-infection is selected, separate the referral bases by a ","
				NBS110ReferralBasisDisease1Map.put("OOJ", NBS150_O_1_CDT);//Print actual referral basis code if Referral Basis is P1, P2, P3, S1, S2, S3, A1, A2, A3 or C1 and the investigation was started from the Patient File (not from contact record). If co-infection is selected, separate the referral bases by a ","
			}
			if(NBS110ReferralBasisDisease2Map.size()==0){
				NBS110ReferralBasisDisease2Map.put("P", NBS150_P_2_CDT);//Print actual referral basis code if Referral Basis is P1, P2 or P3 and named in Contact Record. If co-infection is selected, separate the referral bases by a ","
				NBS110ReferralBasisDisease2Map.put("A", NBS150_A_2_CDT);//Print actual referral basis code if Referral Basis is S1, S2, S3, A1, A2, A3 or C1 and named in Contact Record. If co-infection is selected, separate the referral bases by a ","
				NBS110ReferralBasisDisease2Map.put("T", NBS150_T_2_CDT);//Print actual referral basis code if Referral Basis is  T1 or T2.  If co-infection is selected, separate the referral bases by a ","
				NBS110ReferralBasisDisease2Map.put("OOJ", NBS150_O_2_CDT);//Print actual referral basis code if Referral Basis is P1, P2, P3, S1, S2, S3, A1, A2, A3 or C1 and the investigation was started from the Patient File (not from contact record). If co-infection is selected, separate the referral bases by a ","
			}
			if(NBS110ReferralBasisDisease2Map.size()==0){
				NBS110ReferralBasisDisease2Map.put("P", NBS150_P_2_CDT);//Print actual referral basis code if Referral Basis is P1, P2 or P3 and named in Contact Record. If co-infection is selected, separate the referral bases by a ","
				NBS110ReferralBasisDisease2Map.put("A", NBS150_A_2_CDT);//Print actual referral basis code if Referral Basis is S1, S2, S3, A1, A2, A3 or C1 and named in Contact Record. If co-infection is selected, separate the referral bases by a ","
				NBS110ReferralBasisDisease2Map.put("T", NBS150_T_2_CDT);//Print actual referral basis code if Referral Basis is  T1 or T2.  If co-infection is selected, separate the referral bases by a ","
				NBS110ReferralBasisDisease2Map.put("OOJ", NBS150_O_2_CDT);//Print actual referral basis code if Referral Basis is P1, P2, P3, S1, S2, S3, A1, A2, A3 or C1 and the investigation was started from the Patient File (not from contact record). If co-infection is selected, separate the referral bases by a ","
			}			

			if(actProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getReferralBasisCd() != null){
				String referral = actProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getReferralBasisCd();
				String value = NBS110ReferralBasisMap.get(referral);
				String valueOOJ = NBS110ReferralBasisMapOOJ.get(referral);
				if(conCond==null && valueOOJ!=null)//T or O
					value=valueOOJ;
				String key = NBS110ReferralBasisOriginMap.get(value);
				referralBasisMap.put(key, value);

				//Separate coinfection by ,
				String keyCode = NBS110ReferralBasisCdMap.get(value);
				if(coProxyVO!=null){
					String coreferral = coProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getReferralBasisCd();
					referral+=", "+coreferral;
				}
				referralBasisMap.put(keyCode,referral);

				String keyDisease1 = NBS110ReferralBasisDisease1Map.get(value);
				String code = proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd();
				String diagnosisCode=getAltCond(code);
				referralBasisMap.put(keyDisease1,diagnosisCode);

				if(coProxyVO!=null){
					String keyDisease2 = NBS110ReferralBasisDisease2Map.get(value);
					String condition2 = coProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd();
					String conCond2=getAltCond(condition2);
					referralBasisMap.put(keyDisease2,conCond2);
				}
			}

			//Original Patient ID Number
			String originalPatientIDNumber="";
			if(contactOriginalInvestigationMap!=null && contactOriginalInvestigationMap.containsKey(NBS117_ORIGINAL_PATIENT_ID_NUMBER)){
				originalPatientIDNumber=contactOriginalInvestigationMap.get(NBS117_ORIGINAL_PATIENT_ID_NUMBER);
				formSpecificQuestionAnswerMap.put(NBS117, originalPatientIDNumber);
				if (contactOriginalInvestigationMap.containsKey(IXS102_FR1_1))
					formSpecificQuestionAnswerMap.put(IXS102_FR1_1, contactOriginalInvestigationMap.get(IXS102_FR1_1));
				if (contactOriginalInvestigationMap.containsKey(IXS105_1_CDT))
					formSpecificQuestionAnswerMap.put(IXS105_1_CDT, contactOriginalInvestigationMap.get(IXS105_1_CDT));
					
			} else {
				//per ND-964 The following should be blank if NOT started from a Contact Record
				if (formSpecificQuestionAnswerMap.containsKey(NBS117_ORIGINAL_PATIENT_ID_NUMBER))
					formSpecificQuestionAnswerMap.remove(NBS117_ORIGINAL_PATIENT_ID_NUMBER);
				if (formSpecificQuestionAnswerMap.containsKey(IXS102_FR1_1))
					formSpecificQuestionAnswerMap.remove(IXS102_FR1_1);
				if (formSpecificQuestionAnswerMap.containsKey(IXS102_FR1_2))
					formSpecificQuestionAnswerMap.remove(IXS102_FR1_2);
				if (formSpecificQuestionAnswerMap.containsKey(IXS105_1_CDT))
					formSpecificQuestionAnswerMap.remove(IXS105_1_CDT);
				if (formSpecificQuestionAnswerMap.containsKey(IXS105_2_CDT))
					formSpecificQuestionAnswerMap.remove(IXS105_2_CDT);
			}

			//Add the referralBasisMap to formSpecificQuestionAnswerMap that contains the final Question - Answer map to populate the form
			formSpecificQuestionAnswerMap.putAll(referralBasisMap);

		}catch(Exception e){
			logger.error(e);
			logger.error("CDCFieldRecordForm.populateReferralBasisValues Exception thrown, "+ e);
			throw new NEDSSAppException("CDCFieldRecordForm.populateReferralBasisValues Exception thrown, ", e);
		}
		return contactOriginalInvestigationMap; //Some date values come from Original Investigation if present
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
				logger.error("CDCFieldRecordForm.printForm Exception thrown, "+ e1);
				throw new NEDSSAppException("CDCFieldRecordForm.printForm Exception thrown, ", e1);
				
			}
		} finally {		
				try {
					if(pdfDocument!=null)
						pdfDocument.close();
				} catch (IOException e2) {
					logger.error(e2);
					logger.error("CDCFieldRecordForm.printForm Exception thrown, Error while closing FileInputStream for Print, "+ e2);
					throw new NEDSSAppException("CDCFieldRecordForm.printForm Exception thrown, Error while closing FileInputStream for Print, ", e2);
				}
			}
		}
		

	/**
	 * findQuickCode(): this method converts the provider code to the corresponding quick code
	 * @param question: based on the question, the data is read from a different proxy
	 * @param uid: the provider uid
	 * @return
	 */
	
	private static String findQuickCode(String question, String uid){
		Iterator<Object> itPerson=null;

		String quickCode="";
		boolean found=false;
		
		if(question.equalsIgnoreCase(NBS175_1))
			itPerson=proxyVO.getThePersonVOCollection().iterator();
		if(question.equalsIgnoreCase(NBS175_2))
			itPerson=coProxyVO.getThePersonVOCollection().iterator();
		if(question.equalsIgnoreCase(IXS102_FR1_1))
			itPerson=contactProxyVO.getThePersonVOCollection().iterator();
		if(question.equalsIgnoreCase(IXS102_FR1_2))
			itPerson=contactcoProxyVO.getThePersonVOCollection().iterator();
		
				while (itPerson.hasNext() && !found) {
					PersonVO person = (PersonVO) itPerson.next();
					Iterator<Object> itEntity=person.getTheEntityIdDTCollection().iterator();
					while (itEntity.hasNext() && !found) {
						
						EntityIdDT entityDT =  (EntityIdDT) itEntity.next();
						//uncomment when 10740 approved by Change Control Board
						//String entityUid = "null";
						//if (entityDT.getEntityUid() != null)  //Fix for #10740 for KY when no QC
						//	entityUid = entityDT.getEntityUid().toString();						
						String entityUid = entityDT.getEntityUid().toString();
						if(entityUid.equalsIgnoreCase(uid)){
						
							if(entityDT.getTypeCd().equalsIgnoreCase(NEDSSConstants.ENTITY_TYPECD_QEC)){
			
										quickCode=entityDT.getRootExtensionTxt();
										found=true;
								
							}
						}
					}
				}
		return quickCode;
	}
	
	/**
	 * changeFormat(): converts a date from yyyy-MM-dd format to MM-dd-yyyy format
	 * @param date: date ini format yyyy-MM-dd HH:mm:ss.S
	 * @return it returns the date in format MM-dd-yyyy
	 * @throws NEDSSAppException
	 */
	
	private static String changeFormat(String date) throws NEDSSAppException{
		
			try{
			
				SimpleDateFormat formatter, FORMATTER;
				formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
				Date dateObject = formatter.parse(date);
				FORMATTER = new SimpleDateFormat("MM-dd-yyyy");
				return FORMATTER.format(dateObject);
			}catch(Exception e){	
				logger.error(e);
				logger.error("CDCFieldRecordForm.changeFormat Exception thrown, "+ e);
				throw new NEDSSAppException("CDCFieldRecordForm.changeFormat Exception thrown, ", e);
			}
	}
	
	/**
	 * setDisposition(): it sets the disposition for the initiating investigation and the coinfection investigation
	 * @param initiatingInvestigation
	 */
	
	private static void setDisposition(PageProxyVO proxy, String questionId){
		
		String dispositionFieldFollowUp=null;
		String OOJOutCome="";
		
		if(proxy!=null){
			dispositionFieldFollowUp= proxy.getPublicHealthCaseVO().getTheCaseManagementDT().getFldFollUpDispo();
			OOJOutCome = proxy.getPublicHealthCaseVO().getTheCaseManagementDT().getFieldFollUpOojOutcome();
		}

		if(dispositionFieldFollowUp!=null){
			if(!dispositionFieldFollowUp.equalsIgnoreCase(DISPOSITION_K))
				interviewMap.put(questionId, dispositionFieldFollowUp);
			else{
				if(OOJOutCome!=null)
					dispositionFieldFollowUp+=", "+OOJOutCome;
				interviewMap.put(questionId, dispositionFieldFollowUp);
			}
		}else{
			dispositionFieldFollowUp="";//This is here since the information is set from somewhere else on the commonForm, so we make sure to put the right value even when it is empty
			interviewMap.put(questionId, dispositionFieldFollowUp);
		}
		
	}
	
	/**
	 * setWorker(): it sets the Worker for the initiating investigation and for the co-infection investigation depending on the proxyVO and the questionId sent
	 * @param proxyVO
	 * @param questionId
	 */
	private static void setWorker(PageProxyVO proxy, String questionId){

		String quickCodeInvestigator="";
		boolean found=false;
		String investigatorUid="";
		Iterator<Object> itParticipation=null;

		if(proxy!=null)
			itParticipation=proxy.getPublicHealthCaseVO().getTheParticipationDTCollection().iterator();
			
		
		if(itParticipation!=null){
			while (itParticipation.hasNext() && !found) {
				ParticipationDT participation = (ParticipationDT) itParticipation.next();
				if(participation!=null && participation.getTypeCd().equalsIgnoreCase(PARTICIPATION_INVESTIGATOR_FIELD_FOLLOW_UP)){
					investigatorUid=participation.getSubjectEntityUid()+"";
					found=true;
				}
		}
				//Look for the quickcode
				quickCodeInvestigator=findQuickCode(questionId, investigatorUid);
				
				
	}
		interviewMap.put(questionId, quickCodeInvestigator);		
	}
	
	/**
	 * setDispositionDate(): it sets the disposition date for the initiating investigation (true) and the coinfection investigation (false) based on the parameter
	 * @param initiatingInvestigation
	 */
	
	private static void setDispositionDate(PageProxyVO proxy, String questionId) throws NEDSSAppException{
		
		Timestamp disposition=null;
		String dispositionFieldFollowUDate="";
		try{
		if(proxy!=null)
			disposition=proxy.getPublicHealthCaseVO().getTheCaseManagementDT().getFldFollUpDispoDate();

		if(disposition!=null){
			dispositionFieldFollowUDate = disposition.toString();
			
			if(dispositionFieldFollowUDate!=null){
				dispositionFieldFollowUDate=changeFormat(dispositionFieldFollowUDate);
				
			}
		}
		
		interviewMap.put(questionId, dispositionFieldFollowUDate);
		}catch(NEDSSAppException e){
			logger.error(e);
			logger.error("CDCFieldRecordForm.setDispositionDate Exception thrown, "+ e);
			throw new NEDSSAppException("CDCFieldRecordForm.setDispositionDate Exception thrown, ", e);
		}
		
	}
	
	/**
	 * getInterviewer(): this method returns the interviewer and the interviewType for the initiating investigation
	 * and the co-infection investigation depending on the contact and questionId indicated on the parameters.
	 * @param contact: Contact record from the main investigation (contactProxyVO) or Contact record from the co-infection
	 * investigation (contactCoProxyVO)
	 * @param questionId: the interviewer question identifier for the contact record of the initiating investigation or the co-infection investigation
	 * @return
	 */
	
	private static String getInterviewer(PageActProxyVO contact, String questionId)
	
	{
		String interviewType ="";
		//Interviewer contact record main investigation
				if (contact!=null && contact.getTheCTContactSummaryDTCollection() != null) {
					
					Iterator<Object> it = contact.getTheCTContactSummaryDTCollection().iterator();
					while (it.hasNext()) {
						CTContactSummaryDT condt = (CTContactSummaryDT) it.next();
						if (condt.isContactNamedByPatient()){// && condt.getContactEntityPhcUid().equals(proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid())) {
				
							Iterator<Object> itInterview=contact.getTheInterviewSummaryDTCollection().iterator();
							String interviewerUid="";
							
							while (itInterview.hasNext()) {
								InterviewSummaryDT interview = (InterviewSummaryDT) itInterview.next();
								interviewerUid=interview.getInterviewerUid()+"";
								interviewType = interview.getInterviewTypeCd();
							
							}
							String quickCode="";
							quickCode=findQuickCode(questionId, interviewerUid);
							interviewMap.put(questionId, quickCode);

					}
				}
				}
					
				return interviewType;
				
				
	}
	
	/**
	 * processInterviews(): it processes all the variables related to interviews on the field follow up form (right column, first page)
	 * 
	 * @throws NEDSSAppException
	 */
	
	private static void processInterviews(HttpServletRequest req) throws NEDSSAppException{
		try{
			
		interviewMap=new HashMap<String,String>();
		Map<String, String> originalCoInvMap = new HashMap<String,String>();
		if(coProxyVO!=null) {
			originalCoInvMap = checkInvStartedFromConRec(coProxyVO, 2, req.getSession()); //is this needed? ToDo - not referenced?
			if (originalCoInvMap != null && originalCoInvMap.containsKey(IXS102_FR1_2))
				interviewMap.put(IXS102_FR1_2,originalCoInvMap.get(IXS102_FR1_2));
			if (originalCoInvMap != null && originalCoInvMap.containsKey(IXS105_2_CDT))
				interviewMap.put(IXS105_2_CDT, originalCoInvMap.get(IXS105_2_CDT));
		}
					
		//************************************Investigation Start date	
		//initiating inv
		String InvestigationStartDate=proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getActivityFromTime_s();
				interviewMap.put(INV147_1, InvestigationStartDate);
		//coinfection
		String InvestigationStartDateCoinfection="";
		if(coProxyVO!=null)
				InvestigationStartDateCoinfection=coProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getActivityFromTime_s();
		interviewMap.put(INV147_2, InvestigationStartDateCoinfection);
		//************************************Type of Interview		
		
		if(IXS105NbsInterviewTypeMap.size()==0){
			IXS105NbsInterviewTypeMap.put("CLUSTER", "C");//Cluster  
			IXS105NbsInterviewTypeMap.put("INITIAL", "O");//Initial/Original  
			IXS105NbsInterviewTypeMap.put("REINTVW", "R");//Re-Interview  
		}
		//initiating inv
		//interviewMap.put(IXS105_1_CDT, IXS105NbsInterviewTypeMap.get(interviewType));
		//interviewMap.put(IXS105_2_CDT, IXS105NbsInterviewTypeMap.get(interviewTypeCoInfection));
		
		//***********************Actual referral type
		//initiating inv
		String actualReferralType = proxyVO.getPublicHealthCaseVO().getTheCaseManagementDT().getActRefTypeCd();
		interviewMap.put(NBS177_1_CD, actualReferralType);	
		
		//coinfection
		if(coProxyVO!=null){
			String actualReferralTypeCoInfection = coProxyVO.getPublicHealthCaseVO().getTheCaseManagementDT().getActRefTypeCd();
			interviewMap.put(NBS177_2_CD, actualReferralTypeCoInfection);	
		}
		
		//***********************Disposition
		
		setDisposition(proxyVO, NBS173_1_CD);
		setDisposition(coProxyVO, NBS173_2_CD);
		
		//********************Disposition date

		setDispositionDate(proxyVO, NBS174_1);
		setDispositionDate(coProxyVO, NBS174_2);
		
		//********************New_CaseNo_1
		
		String invLocalId= proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getLocalId();
		interviewMap.put(New_CaseNo_1, invLocalId);
		String invLocalIdCoInfection="";
		if(coProxyVO!=null) {
			invLocalIdCoInfection= coProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getLocalId();
			interviewMap.put(New_CaseNo_2, invLocalIdCoInfection);
		}
		
		//********************DX
		
		if(pageForm.getPageClientVO().getAnswerMap().get(NBS136)!=null){
			String diagnosisReportedToCDC=pageForm.getPageClientVO().getAnswerMap().get(NBS136).toString();
			interviewMap.put(NBS136_1_CD, diagnosisReportedToCDC);
		}
		
		String diagnosisReportedToCDCCoInfection="";
		if(coProxyVO!=null){
			
			Long key;
			Object resultObject;
			Map<Object, Object>  answerMap = coProxyVO.getPageVO().getPamAnswerDTMap();
			Set<Object> set= questionUidMap.keySet();
			Iterator<Object> itQuestion = set.iterator();
			while(itQuestion.hasNext()){
				key = (Long)itQuestion.next();
				NbsQuestionMetadata nbsQuestionDT= (NbsQuestionMetadata)questionUidMap.get(key);
				if(nbsQuestionDT.getDataLocation()!=null && key !=null && nbsQuestionDT.getDataLocation().equalsIgnoreCase(NEDSSConstants.NBS_CASE_ANSWER_ANSWER_TXT)){
					resultObject =answerMap.get(key);
					if(resultObject instanceof NbsAnswerDT && answerMap!=null 
							&& answerMap.get(key)!=null)	{
						NbsAnswerDT caseDT = (NbsAnswerDT)answerMap.get(key);
						if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(NBS136)){
							diagnosisReportedToCDCCoInfection=caseDT.getAnswerTxt();
							
							}
						}
							
					}		
			}
		}
		
		interviewMap.put(NBS136_2_CD, diagnosisReportedToCDCCoInfection);

		//********************Worker
		
		setWorker(proxyVO, NBS175_1);
		setWorker(coProxyVO, NBS175_2);

		//Add the interviewMap to formSpecificQuestionAnswerMap that contains the final Question - Answer map to populate the form
		formSpecificQuestionAnswerMap.putAll(interviewMap);
					
		}catch(Exception e){
			logger.error(e);
			logger.error("CDCFieldRecordForm.processInterviews Exception thrown, "+ e);
			throw new NEDSSAppException("CDCFieldRecordForm.processInterviews Exception thrown, ", e);
			}
		
		
	}
	
	/**
	 * changeExposureVariables(): P1 and P3 use NBS118, NBS119 and NBS120 but P2 uses NBS121, NBS121, NBS123. For making automatic the behavior, we send all the variables and this method get the corresponding values
	 * and put them on the variables showing on the form (NBS118, NBS119, NBS120) independently if it's P1, P2 or P3.
	 * First Sexual Exposure			NBS118
	 * Sexual Frequency					NBS119
	 * Last Sexual Exposure 			NBS120
	 * First Needle-Sharing Exposure	NBS121
	 * Needle-Sharing Frequency			NBS122
	 * Last Needle-Sharing Exposure		NBS123
	 * @param originalContactRecordValues - per ND-1007 use the original patient contact record if the INV started from CR
	 * @throws NEDSSAppException 
	 */
	private static void changeExposureVariables(Map<String, String> originalContactRecordValues) throws NEDSSAppException{

		String referral = proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getReferralBasisCd();
		String valueNBS118 = formSpecificQuestionAnswerMap.get(NBS118);
		String valueNBS119 = formSpecificQuestionAnswerMap.get(NBS119);
		String valueNBS120 = formSpecificQuestionAnswerMap.get(NBS120);
		String valueNBS121 = formSpecificQuestionAnswerMap.get(NBS121);
		String valueNBS122 = formSpecificQuestionAnswerMap.get(NBS122);
		String valueNBS123 = formSpecificQuestionAnswerMap.get(NBS123);
		
		if(referral!=null && referral.equalsIgnoreCase(REFERRAL_BASIS_P2)){
			formSpecificQuestionAnswerMap.put(NBS118, valueNBS121);
			formSpecificQuestionAnswerMap.put(NBS119, valueNBS122);
			formSpecificQuestionAnswerMap.put(NBS120, valueNBS123);
		} else if(referral!=null && referral.equalsIgnoreCase(REFERRAL_BASIS_P3)){
			StringBuffer exposureFirstSB = new StringBuffer("");
			if (valueNBS118 != null)
				exposureFirstSB.append(valueNBS118);
			exposureFirstSB.append(", ");
			if (valueNBS121 != null)
				exposureFirstSB.append(valueNBS121);
			formSpecificQuestionAnswerMap.put(NBS118, exposureFirstSB.toString());
			StringBuffer exposureLastSB = new StringBuffer("");
			if (valueNBS120 != null)
				exposureLastSB.append(valueNBS120);
			exposureLastSB.append(", ");
			if (valueNBS123 != null)
				exposureLastSB.append(valueNBS123);
			formSpecificQuestionAnswerMap.put(NBS120, exposureLastSB.toString());
			StringBuffer exposureFrequencySB = new StringBuffer("");
			if (valueNBS119 != null)
				exposureFrequencySB.append(valueNBS119);
			exposureFrequencySB.append(", ");
			if (valueNBS122 != null)
				exposureFrequencySB.append(valueNBS122);
			formSpecificQuestionAnswerMap.put(NBS119, exposureFrequencySB.toString());
		} 
		
		if (originalContactRecordValues == null)
			return;
		//If Investigation started from Contact Record...
		try {
			//check if values should come from the original investigation
			//These values are only present if the Investigation was started via a Contact Record with Processing Decision set to FF
			//per ND-1007 put from original contact record if present
			if (originalContactRecordValues.containsKey(NBS118+ORIGINAL_PATIENT_CONTACT_RECORD) ||
					originalContactRecordValues.containsKey(NBS121+ORIGINAL_PATIENT_CONTACT_RECORD)) {
				if (referral.equalsIgnoreCase(REFERRAL_BASIS_P1) && originalContactRecordValues.containsKey(NBS118+ORIGINAL_PATIENT_CONTACT_RECORD))
					formSpecificQuestionAnswerMap.put(NBS118, originalContactRecordValues.get(NBS118+ORIGINAL_PATIENT_CONTACT_RECORD));
				else if (referral.equalsIgnoreCase(REFERRAL_BASIS_P2) && originalContactRecordValues.containsKey(NBS121+ORIGINAL_PATIENT_CONTACT_RECORD))
					formSpecificQuestionAnswerMap.put(NBS118, originalContactRecordValues.get(NBS121+ORIGINAL_PATIENT_CONTACT_RECORD));
				else if (referral.equalsIgnoreCase(REFERRAL_BASIS_P3) && 
						(originalContactRecordValues.containsKey(NBS118+ORIGINAL_PATIENT_CONTACT_RECORD) ||
						originalContactRecordValues.containsKey(NBS121+ORIGINAL_PATIENT_CONTACT_RECORD))) {
					StringBuffer exposureFirstSB = new StringBuffer("");
					if (originalContactRecordValues.containsKey(NBS118+ORIGINAL_PATIENT_CONTACT_RECORD))
						exposureFirstSB.append(originalContactRecordValues.get(NBS118+ORIGINAL_PATIENT_CONTACT_RECORD));
					exposureFirstSB.append(", ");
					if (originalContactRecordValues.containsKey(NBS121+ORIGINAL_PATIENT_CONTACT_RECORD))
						exposureFirstSB.append(originalContactRecordValues.get(NBS121+ORIGINAL_PATIENT_CONTACT_RECORD));
					formSpecificQuestionAnswerMap.put(NBS118, exposureFirstSB.toString());
				}
			}
			if (originalContactRecordValues.containsKey(NBS119+ORIGINAL_PATIENT_CONTACT_RECORD) ||
					originalContactRecordValues.containsKey(NBS122+ORIGINAL_PATIENT_CONTACT_RECORD)) {
				if (referral.equalsIgnoreCase(REFERRAL_BASIS_P1) && originalContactRecordValues.containsKey(NBS119+ORIGINAL_PATIENT_CONTACT_RECORD))
					formSpecificQuestionAnswerMap.put(NBS119, originalContactRecordValues.get(NBS119+ORIGINAL_PATIENT_CONTACT_RECORD));
				else if (referral.equalsIgnoreCase(REFERRAL_BASIS_P2) && originalContactRecordValues.containsKey(NBS122+ORIGINAL_PATIENT_CONTACT_RECORD))
					formSpecificQuestionAnswerMap.put(NBS119, originalContactRecordValues.get(NBS122+ORIGINAL_PATIENT_CONTACT_RECORD));
				else if (referral.equalsIgnoreCase(REFERRAL_BASIS_P3) && 
						(originalContactRecordValues.containsKey(NBS119+ORIGINAL_PATIENT_CONTACT_RECORD) ||
						originalContactRecordValues.containsKey(NBS122+ORIGINAL_PATIENT_CONTACT_RECORD))) {
					StringBuffer exposureFirstSB = new StringBuffer("");
					if (originalContactRecordValues.containsKey(NBS119+ORIGINAL_PATIENT_CONTACT_RECORD))
						exposureFirstSB.append(originalContactRecordValues.get(NBS119+ORIGINAL_PATIENT_CONTACT_RECORD));
					exposureFirstSB.append(", ");
					if (originalContactRecordValues.containsKey(NBS122+ORIGINAL_PATIENT_CONTACT_RECORD))
						exposureFirstSB.append(originalContactRecordValues.get(NBS122+ORIGINAL_PATIENT_CONTACT_RECORD));
					formSpecificQuestionAnswerMap.put(NBS119, exposureFirstSB.toString());
				}
			}
			if (originalContactRecordValues.containsKey(NBS120+ORIGINAL_PATIENT_CONTACT_RECORD) ||
					originalContactRecordValues.containsKey(NBS123+ORIGINAL_PATIENT_CONTACT_RECORD)) {
				if (referral.equalsIgnoreCase(REFERRAL_BASIS_P1) && originalContactRecordValues.containsKey(NBS120+ORIGINAL_PATIENT_CONTACT_RECORD))
					formSpecificQuestionAnswerMap.put(NBS120, originalContactRecordValues.get(NBS120+ORIGINAL_PATIENT_CONTACT_RECORD));
				else if (referral.equalsIgnoreCase(REFERRAL_BASIS_P2) && originalContactRecordValues.containsKey(NBS122+ORIGINAL_PATIENT_CONTACT_RECORD))
					formSpecificQuestionAnswerMap.put(NBS120, originalContactRecordValues.get(NBS123+ORIGINAL_PATIENT_CONTACT_RECORD));
				else if (referral.equalsIgnoreCase(REFERRAL_BASIS_P3) && 
						(originalContactRecordValues.containsKey(NBS120+ORIGINAL_PATIENT_CONTACT_RECORD) ||
						originalContactRecordValues.containsKey(NBS123+ORIGINAL_PATIENT_CONTACT_RECORD))) {
					StringBuffer exposureLastSB = new StringBuffer("");
					if (originalContactRecordValues.containsKey(NBS120+ORIGINAL_PATIENT_CONTACT_RECORD))
						exposureLastSB.append(originalContactRecordValues.get(NBS120+ORIGINAL_PATIENT_CONTACT_RECORD));
					exposureLastSB.append(", ");
					if (originalContactRecordValues.containsKey(NBS123+ORIGINAL_PATIENT_CONTACT_RECORD))
						exposureLastSB.append(originalContactRecordValues.get(NBS123+ORIGINAL_PATIENT_CONTACT_RECORD));
					formSpecificQuestionAnswerMap.put(NBS120, exposureLastSB.toString());
				}
			}
		}catch(Exception e){
			logger.error(e);
			logger.error("CDCFieldRecordForm.changeExposureVariables Exception thrown, "+ e);
			throw new NEDSSAppException("CDCFieldRecordForm.changeExposureVariables Exception thrown, ", e);
		}
		
	}
	
	/**
	 * changeOOJ(): if disposition != K we remove the value from formSpecificQuestionAnswerMap before the value is set on the form
	 * 
	 */
	
	private static void changeOOJ(){
		
		String dispositionFieldFollowUp= proxyVO.getPublicHealthCaseVO().getTheCaseManagementDT().getFldFollUpDispo();
		if(dispositionFieldFollowUp==null || (dispositionFieldFollowUp!=null && !dispositionFieldFollowUp.equalsIgnoreCase(DISPOSITION_K))){
			formSpecificQuestionAnswerMap.put(NBS180, null);
			formSpecificQuestionAnswerMap.put(NBS179, null);
			formSpecificQuestionAnswerMap.put(NBS181, null);
		} else {
			//NBS179 is on the page twice as NBS179 and NBS179_O_CD
			if (proxyVO != null && proxyVO.getPublicHealthCaseVO().getTheCaseManagementDT() != null &&  
						proxyVO.getPublicHealthCaseVO().getTheCaseManagementDT().getOojAgency() != null)
				putNBS179DescriptionInOOJArea(proxyVO.getPublicHealthCaseVO().getTheCaseManagementDT().getOojAgency());
		}
	}
	
	/**
	 * changePostCounseled(): the investigation contains 3 values for post-counseled: Yes/No/Unknown. The form just contains Yes/No. In case the option selected is "unknown"
	 * this method removes it from the map.
	 */
	private static void changePostCounseled() throws NEDSSAppException{
		try{
		String postCounseled = formSpecificQuestionAnswerMap.get(NBS264_CD);
		
		if(postCounseled!=null && postCounseled.equalsIgnoreCase(POSTCOUNSELED_UNKNOWN))
			formSpecificQuestionAnswerMap.put(NBS264_CD, null);
		}catch(Exception e){
			logger.error(e);
			logger.error("CDCFieldRecordForm.changePostCounseled Exception thrown, "+ e);
			throw new NEDSSAppException("CDCFieldRecordForm.changePostCounseled Exception thrown, ", e);
		}
		
	}
	
	/**
	 * changeInvestigationAgencyFromCodeToDescription(): change jurisdiction from code to description. This needs to be called before changeInitiatingAgency since the Agency could be the investigation Agency
	 * depending from where was created the investigation
	 */
	private static void changeInvestigationAgencyFromCodeToDescription(){
		
		String INV107value = formSpecificQuestionAnswerMap.get(INV107);
		String jurisdictionDescription=getJurisdictionDesc(INV107value);	
		formSpecificQuestionAnswerMap.put(INV107, jurisdictionDescription);
	}		
	
	/**
	 * changeInitiatingAgency():
	 * If investigation is started from patient file, use NBS11. If investigation is started from any other method (lab, morb, contact record), use INV107.
	 **/

	private static void changeInitiatingAgency(){
		
		String NBS111value = formSpecificQuestionAnswerMap.get(NBS111);
		String INV107value = formSpecificQuestionAnswerMap.get(INV107);
		
		//Investigation created from Lab Report or Morbidity Report
		if(proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getReferralBasisCd()!=null && (proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getReferralBasisCd().equalsIgnoreCase(LAB_REFERRAL_BASIS)
		||proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getReferralBasisCd().equalsIgnoreCase(MORB_REFERRAL_BASIS)))
			formSpecificQuestionAnswerMap.put(NBS111, INV107value);
		else{
			String NBS111description = getStateByCode(NBS111value);
			formSpecificQuestionAnswerMap.put(NBS111, NBS111description);
		}
	}
	
	
	/**
	 * changeNotifibilityPlanActualMethod: If Patient Notifiable Eligibility Not Yes, Plan and Actual Method should be blank
	 * ND-948
	 */
	
	private static void changeNotifibilityPlanActualMethod(){
		
		String patientEligibleForNotificationOfExposure=null;
		if (proxyVO.getPublicHealthCaseVO().getTheCaseManagementDT() != null)
			patientEligibleForNotificationOfExposure = proxyVO.getPublicHealthCaseVO().getTheCaseManagementDT().getInitFollUpNotifiable();
		if(patientEligibleForNotificationOfExposure!=null && 
				(!patientEligibleForNotificationOfExposure.equalsIgnoreCase(PATIENT_NOTIFICATION_ELIGIBILITY_YES) && 
				!patientEligibleForNotificationOfExposure.equalsIgnoreCase(PATIENT_NOTIFICATION_ELIGIBILITY_OTHER))){
			if (formSpecificQuestionAnswerMap.containsKey(NBS167_CD))
				formSpecificQuestionAnswerMap.remove(NBS167_CD);
			if (formSpecificQuestionAnswerMap.containsKey(NBS177_CD))
				formSpecificQuestionAnswerMap.remove(NBS177_CD);
		} 
	}	
	
	private static void fillPDForm(PDAcroForm acroForm, String invFormCd,HttpServletRequest req) throws NEDSSAppException {
		    Map<String, String> originalContactRecordValues = new  HashMap<String,String>();
			String currentKey = "";
			//String currentFormValue = "";
			try {
				answerMap = pageForm.getPageClientVO().getAnswerMap(); 

				int i = 1; //what is this used for??
				
				initializeFieldFollowUpValues();
				
				getMappedValues(fieldFollowUpFieldsMap, invFormCd, req);
				
				clearLabValuesForFieldRec(labStagingArray);
				
				populateLabFieldsFromStaging();
				
				
				
				originalContactRecordValues = populateReferralBasisValues(proxyVO, req);
				
				processInterviews(req);

				changeExposureVariables(originalContactRecordValues);
				
				changeOOJ();
				
				changeInvestigationAgencyFromCodeToDescription();
				changeInitiatingAgency();
				
				changePostCounseled();
				
				changeNotifibilityPlanActualMethod();
				
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
				logger.error("CDCFieldRecordForm.fillPDForm Exception thrown, "+ e);
				throw new NEDSSAppException("CDCFieldRecordForm.fillPDForm Exception thrown, ", e);
			}
		}//end	




	/**
	 * NBS179 is in the form twice as NBS179_O_CD and NBS179 which breaks our model
	 * @param agencyCd
	 * Places NBS179 into formSpecificQuestionAnswerMap
	 */
	private static void putNBS179DescriptionInOOJArea(String agencyCd) {
		String agencyName = cache.getDescForCode("OOJ_AGENCY_LOCAL", agencyCd); 
		if (agencyName != null)
			formSpecificQuestionAnswerMap.put(NBS179, agencyName);
	}
	
	//Interview has 8 entries in lab, Field Rec has only 4
	private static void clearLabValuesForFieldRec(String[][] labStagingArray) {
		for (int i = 4; i < 8; ++i) {
			for (int j = 1; j < 6; ++ j) 
				labStagingArray[i][j] = "";
		}
	}
			
	
	
}
