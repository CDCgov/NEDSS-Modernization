package gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

import gov.cdc.nedss.association.dt.ActRelationshipDT;
import gov.cdc.nedss.association.dt.ParticipationDT;
import gov.cdc.nedss.entity.organization.vo.OrganizationVO;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.PageProxyVO;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.act.PageActProxyVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.LabReportSummaryVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.MorbReportSummaryVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.ProviderDataForPrintVO;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.webapp.nbs.form.page.PageForm;
/**
 * PDF generating/populating class for "Provider Surveillance Follow-up Form"
 * Print CDC Forms is on the View Screen of STD Investigations. 
 * @author Greg Tucker
 *
 */
 
public class CDCProviderForm extends CommonPDFPrintForm{
	/**
	 * Constants
	 */
	
	public static final String FACILITY_PHONE	= "ORD121_ORD122";
	public static final String FACILITY_ADDRESS	= "ORD110_ORD111";
	public static final String EXTENTION_TXT = " ext: ";
	public static final String PROVIDER_FACILITY="ProviderFacility"; //provider order facility Name
	public static final String PROVIDER_NAME_FACILITY="ProviderNameTileFacility"; //Provider Name Title Facility
	public static final String PROVIDER_NAME="ProviderName"; //INV181 provider name 
	
	private static String delimiter1 = "__";
	//Form Fields - Starting from the top left of the form
	private	static String ORD110_ORD111 = "ORD110_ORD111";
	protected static String ORD110_ORD111__T1LabOrderingFacilityOrT2MorbReportingOrgAddress= ORD110_ORD111 + delimiter1+ "T1LabOrderingFacilityOrT2MorbReportingOrgAddress";
	private	static String ORD112 = "ORD112";
	protected static String ORD112__T1LabOrderingFacilityOrT2MorbReportingOrgCity= ORD112 + delimiter1+ "T1LabOrderingFacilityOrT2MorbReportingOrgCity";
	private	static String ORD113 = "ORD113";
	protected static String ORD113__T1LabOrderingFacilityOrT2MorbReportingOrgState= ORD113 + delimiter1+ "T1LabOrderingFacilityOrT2MorbReportingOrgState";
	private	static String ORD114 = "ORD114";
	protected static String ORD114__T1LabOrderingFacilityOrT2MorbReportingOrgZip= ORD114 + delimiter1+ "T1LabOrderingFacilityOrT2MorbReportingOrgZip";
	private	static String ORD121_ORD122 = "ORD121_ORD122";
	protected static String ORD121_ORD122__T1LabOrderingFacilityOrT2MorbReportingOrgPhone= ORD121_ORD122 + delimiter1+ "T1LabOrderingFacilityOrT2MorbReportingOrgPhone";
	private	static String NBS150_1_CD = "NBS150_1_CD";
	protected static String NBS150_1_CD__FirstDiseaseSurveillanceProviderDiagnosisNBS150= NBS150_1_CD + delimiter1+ "FirstDiseaseSurveillanceProviderDiagnosisNBS150";
	private	static String NBS150_2_CD = "NBS150_2_CD";
	protected static String NBS150_2_CD__SecondDiseaseSurveillanceProviderDiagnosisNBS150= NBS150_2_CD + delimiter1+ "SecondDiseaseSurveillanceProviderDiagnosisNBS150";
	private	static String NBS149_CDT = "NBS149_CDT";
	protected static String NBS149_CDT__SurveillanceExamReasonNBS149= NBS149_CDT + delimiter1+ "SurveillanceExamReasonNBS149";
	
	
	
	//Sign Symptom
	private	static String INV272_R1_CD = "INV272_R1_CD";
	protected static String INV272_R1_CD__FirstSignSymptomINV272= INV272_R1_CD + delimiter1+ "SignSymptomINV272";
	private	static String NBS247_R1 = "NBS247_R1";
	protected static String NBS247_R1__FirstSignSymptomOnsetDateNBS247= NBS247_R1 + delimiter1+ "FirstSignSymptomOnsetDateNBS247";
	private	static String STD121_R1_CDT = "STD121_R1_CDT";
	protected static String STD121_R1_CDT__FirstSignSymptomAnatomicSiteSTD121= STD121_R1_CDT + delimiter1+ "FirstSignSymptomAnatomicSiteSTD121";
	private	static String NBS249_R1 = "NBS249_R1";
	protected static String NBS249_R1__FirstSignSymptomDurationDaysNBS249= NBS249_R1 + delimiter1+ "FirstSignSymptomDurationDaysNBS249";
	private	static String INV272_R2_CD = "INV272_R2_CD";
	protected static String INV272_R2_CD__SecondSignSymptomINV272= INV272_R2_CD + delimiter1+ "SignSymptomINV272";
	private	static String NBS247_R2 = "NBS247_R2";
	protected static String NBS247_R2__SecondSignSymptomOnsetDateNBS247= NBS247_R2 + delimiter1+ "SecondSignSymptomOnsetDateNBS247";
	private	static String STD121_R2_CDT = "STD121_R2_CDT";
	protected static String STD121_R2_CDT__SecondSignSymptomAnatomicSiteSTD121= STD121_R2_CDT + delimiter1+ "SecondSignSymptomAnatomicSiteSTD121";
	private	static String NBS249_R2 = "NBS249_R2";
	protected static String NBS249_R2__SecondSignSymptomDurationDaysNBS249= NBS249_R2 + delimiter1+ "SecondSignSymptomDurationDaysNBS249";
	//Labs
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
	
	//Treatments
	private	static String TR101_R1 = "TR101_R1";
	protected static String TR101_R1__FirstTreatmentTR101_TRT100_TR101_TRT104_OrderingProviderName= TR101_R1 + delimiter1+ "FirstTreatmentTR101+TRT100+TR101+TRT104/OrderingProviderName";	
	private	static String TR101_R2 = "TR101_T2";
	protected static String TR101_R2__SecondTreatmentTR101_TRT100_TR101_TRT104_OrderingProviderName= TR101_R2 + delimiter1+ "SecondTreatmentTR101+TRT100+TR101+TRT104/OrderingProviderName";	
	private	static String TR101_R3 = "TR101_T3";
	protected static String TR101_R3__ThirdTreatmentTR101_TRT100_TR101_TRT104_OrderingProviderName = TR101_R3 + delimiter1+ "ThirdTreatmentTR101+TRT100+TR101+TRT104/OrderingProviderName";	
	private	static String TR101_R4 = "TR101_T4";
	protected static String TR101_R4__FourthTreatmentTR101_TRT100_TR101_TRT104_OrderingProviderName= TR101_R4 + delimiter1+ "ThirdTreatmentTR101+TRT100+TR101+TRT104/OrderingProviderName";
	//Patient Demographics
	private	static String DEM102 = "DEM102";
	protected static String DEM102__PatientLastNameDEM102= DEM102 + delimiter1+ "PatientLastNameDEM102";
	private	static String DEM104 = "DEM104";
	protected static String DEM104__PatientFirstNameDEM104= DEM104 + delimiter1+ "PatientFirstNameDEM104";	
	private	static String DEM250 = "DEM250";
	protected static String DEM250__PatientAliasNameDEM250= DEM250 + delimiter1+ "PatientAliasNameDEM250";	
	private	static String DEM159_DEM160= "DEM159_DEM160";//street1+street2 - there is a space in form
	protected static String DEM159_160__PatientStreetDEM159= DEM159_DEM160 + delimiter1+ "PatientLastNameDEM159DEM160";
	private	static String DEM161 = "DEM161";
	protected static String DEM161__PatientCityDEM161= DEM161 + delimiter1+ "PatientCityDEM161";
	private	static String DEM162 = "DEM162";
	protected static String DEM162__PatientStateDEM162= DEM162 + delimiter1+ "PatientStateDEM162";
	private	static String DEM163 = "DEM163";
	protected static String DEM163__PatientZipDEM163= DEM163 + delimiter1+ "PatientZipDEM163";
	private	static String NBS006 = "NBS006";
	protected static String NBS006__PatientCellNBS006_UseCd = NBS006 + delimiter1+ "PatientCellNBS006";
	private	static String DEM177 = "DEM177";
	protected static String DEM177__PatientHmPhDEM177_UseCd = DEM177 + delimiter1+ "PatientHmPhDEM177";
	private	static String INV2001 = "INV2001";
	protected static String INV2001__PatientReportedAge = INV2001 + delimiter1+ "PatientReportedAgeINV2001";
	private	static String DEM115 = "DEM115";
	protected static String DEM115__PatientDOB = DEM115 + delimiter1+ "PatientDOBDEM115";
	private	static String DEM113_CD = "DEM113_CD";
	protected static String DEM113_CD__PatientCurrentSexDEM113 = DEM113_CD + delimiter1+ "PatientCurrentSexDEM113";	
	private	static String NBS274_CD = "NBS274_CD";
	protected static String  NBS274_CD__PatientTransgenderNBS274 =  NBS274_CD + delimiter1+ "PatientTransgenderNBS274";	
	private	static String NBS272_CD = "NBS272_CD";
	protected static String NBS272_CD__PatientSexUnkownReasonNBS272 = NBS272_CD + delimiter1+ "PatientSexUnkownReasonNBS272";	
	private	static String DEM152_AI_CDT = "DEM152_AI_CDT";
	protected static String DEM152_AI_CDT__PatientRaceAmerIndianDEM152 = DEM152_AI_CDT + delimiter1+ "PatientRaceAmerIndianDEM152";
	private	static String DEM152_A_CDT = "DEM152_A_CDT";
	protected static String DEM152_A_CDT__PatientRaceAsianDEM152 = DEM152_A_CDT + delimiter1+ "PatientRaceAsianDEM152";
	private	static String DEM152_B_CDT = "DEM152_B_CDT";
	protected static String DEM152_B_CDT__PatientRaceBlackDEM152 = DEM152_B_CDT + delimiter1+ "PatientRaceBlackDEM152";
	private	static String DEM152_W_CDT = "DEM152_W_CDT";
	protected static String DEM152_W_CDT__PatientRaceWhiteDEM152 = DEM152_W_CDT + delimiter1+ "PatientRaceWhiteDEM152";	
	private	static String DEM152_NH_CDT = "DEM152_NH_CDT";
	protected static String DEM152_NH_CDT__PatientRaceHawaiianDEM152 = DEM152_NH_CDT + delimiter1+ "PatientRaceHawaiianDEM152";	
	private	static String DEM152_U_CDT = "DEM152_U_CDT";
	protected static String DEM152_U_CDT__PatientRaceUnknownDEM152 = DEM152_U_CDT + delimiter1+ "PatientRaceUnknownDEM152";
	private	static String DEM152_R_CDT = "DEM152_R_CDT";
	protected static String DEM152_R_CDT__PatientRaceRefusedDEM152 = DEM152_R_CDT + delimiter1+ "PatientRaceRefusedDEM152";
	private	static String DEM152_D_CDT = "DEM152_D_CDT";
	protected static String DEM152_D_CDT__PatientRaceDidNotAskDEM152 = DEM152_D_CDT + delimiter1+ "PatientRaceDidNotAskDEM152";
	private	static String DEM152_O_CDT = "DEM152_O_CDT";
	protected static String DEM152_O_CDT__PatientRaceOtherDEM152 = DEM152_O_CDT + delimiter1+ "PatientRaceOtherDEM152";
	
	
	private	static String DEM155_CDT = "DEM155_CDT";
	protected static String DEM155_CDT__PatientEthnicityDEM155 = DEM155_CDT + delimiter1+ "EthnicityDEM155";	
	private	static String NBS273 = "NBS273";
	protected static String NBS273__PatientEthnicityUnkownReasonNBS273 = NBS273 + delimiter1+ "PatientEthnicityUnkownReasonNBS273";	
	private	static String INV178_CD = "INV178_CD";
	protected static String INV178_CD__PatientPregnantINV178 = INV178_CD + delimiter1+ "PatientPregnantINV178";
	private	static String NBS128 = "NBS128";
	protected static String NBS128__PatientPregnantWeeksNBS128 = NBS128 + delimiter1+ "PatientPregnantNBS128";
	private	static String DEM140_CDT = "DEM140_CDT";
	protected static String DEM140_CDT__PatientMaritalStatusDEM140 = DEM140_CDT + delimiter1+ "PatientMaritalStatusDEM140";
	//Disease Info
	private	static String NBS146_1 = "NBS146_1";
	protected static String NBS146_1__FirstDiseaseSurveillanceDateAssignedNBS146 = NBS146_1 + delimiter1+ "FirstDiseaseSurveillanceDateAssignedNBS146";
	private	static String NBS145_1 = "NBS145_1"; //SurvInvestgrOfPHC
	protected static String NBS145_1__FirstDiseaseSurveillanceInvestigatorAssignedNBS145 = NBS145_1 + delimiter1+ "FirstDiseaseSurveillanceInvestigatorAssignedNBS145";
	private	static String NBS147_1 = "NBS147_1";
	protected static String NBS147_1__FirstDiseaseSurveillanceCloseDateNBS147 = NBS147_1 + delimiter1+ "FirstDiseaseSurveillanceCloseDateNBS147";
	private	static String NBS148_1_CD = "NBS148_1_CD";
	protected static String NBS148_1_CD__FirstDiseaseSurveillanceProviderNBS148 = NBS148_1_CD + delimiter1+ "FirstDiseaseSurveillanceProviderNBS148";
	private	static String NBS151_1_CDT = "NBS151_1_CDT";
	protected static String NBS151_1_CDT__FirstDiseaseSurveillanceFollowupNBS151 = NBS151_1_CDT + delimiter1+ "FirstDiseaseSurveillanceFollowupNBS151";
	
	private	static String NBS146_2 = "NBS146_2";
	protected static String NBS146_2__SecondDiseaseSurveillanceDateAssignedNBS146 = NBS146_2 + delimiter1+ "SecondDiseaseSurveillanceDateAssignedNBS146";
	private	static String NBS145_2 = "NBS145_2";
	protected static String NBS145_2__SecondDiseaseSurveillanceInvestigatorAssignedNBS145 = NBS145_2 + delimiter1+ "SecondDiseaseSurveillanceInvestigatorAssignedNBS145";
	private	static String NBS147_2 = "NBS147_2";
	protected static String NBS147_2__SecondDiseaseSurveillanceCloseDateNBS147 = NBS147_2 + delimiter1+ "SecondDiseaseSurveillanceCloseDateNBS147";
	private	static String NBS148_2_CD = "NBS148_2_CD";
	protected static String NBS148_2_CD__SecondDiseaseSurveillanceProviderNBS148 = NBS148_2_CD + delimiter1+ "SecondDiseaseSurveillanceProviderNBS148";
	private	static String NBS151_2_CDT = "NBS151_2_CDT";
	protected static String NBS151_2_CDT__SecondDiseaseSurveillanceFollowupNBS151 = NBS151_2_CDT + delimiter1+ "SecondDiseaseSurveillanceFollowupNBS151";
	
	private	static String Other_Info = "Other_Info";
	protected static String  OTHER_INFO__PhysicianNameAndAddressINV182_OtherIdentifyingInfoNBS159 =  Other_Info + delimiter1+ "PhysicianNameAndAddressINV182+OtherIdentifyingInfoNBS159";	


	/**
	 * MAPS
	 */
	private static Map<String, String> ProviderFormCommonFieldsMap= new HashMap<String, String>();
	
	/**
	 * Methods
	 */
	private static void initializeProviderValues(){
		if(ProviderFormCommonFieldsMap.size()==0){
			//ProviderFormCommonFieldsMap.put("ProviderNameTitleLabFacilityOrMorbReportingNameINV182", "ProviderNameTitleFacility");
			//ProviderFormCommonFieldsMap.put(ORD110_ORD111__T1LabOrderingFacilityOrT2MorbReportingOrgAddress, ORD110_ORD111);
			//ProviderFormCommonFieldsMap.put(ORD112__T1LabOrderingFacilityOrT2MorbReportingOrgCity, ORD112);
			//ProviderFormCommonFieldsMap.put(ORD113__T1LabOrderingFacilityOrT2MorbReportingOrgState, ORD113);
			//ProviderFormCommonFieldsMap.put(ORD114__T1LabOrderingFacilityOrT2MorbReportingOrgZip, ORD114);
			//ProviderFormCommonFieldsMap.put(ORD121_ORD122__T1LabOrderingFacilityOrT2MorbReportingOrgPhone, ORD121_ORD122);
			ProviderFormCommonFieldsMap.put(NBS150_1_CD__FirstDiseaseSurveillanceProviderDiagnosisNBS150, NBS150_1_CD);
			ProviderFormCommonFieldsMap.put(NBS150_2_CD__SecondDiseaseSurveillanceProviderDiagnosisNBS150, NBS150_2_CD);
			
			ProviderFormCommonFieldsMap.put(NBS149_CDT__SurveillanceExamReasonNBS149, NBS149_CDT);
			
			ProviderFormCommonFieldsMap.put(INV272_R1_CD__FirstSignSymptomINV272, INV272_R1_CD);
			ProviderFormCommonFieldsMap.put(NBS247_R1__FirstSignSymptomOnsetDateNBS247, NBS247_R1);
			ProviderFormCommonFieldsMap.put(STD121_R1_CDT__FirstSignSymptomAnatomicSiteSTD121,STD121_R1_CDT);
			ProviderFormCommonFieldsMap.put(NBS249_R1__FirstSignSymptomDurationDaysNBS249, NBS249_R1);
			ProviderFormCommonFieldsMap.put(INV272_R2_CD__SecondSignSymptomINV272, INV272_R2_CD);
			ProviderFormCommonFieldsMap.put(NBS247_R2__SecondSignSymptomOnsetDateNBS247, NBS247_R2);
			ProviderFormCommonFieldsMap.put(STD121_R2_CDT__SecondSignSymptomAnatomicSiteSTD121,STD121_R2_CDT);
			ProviderFormCommonFieldsMap.put(NBS249_R2__SecondSignSymptomDurationDaysNBS249, NBS249_R2);
			
			ProviderFormCommonFieldsMap.put(LAB163_R1__FirstLabSpecimenDateLAB163, LAB163_R1);
			ProviderFormCommonFieldsMap.put(LAB220_R1__FirstLabTestNameLAB220, LAB220_R1);
			ProviderFormCommonFieldsMap.put(Lab_Result_R1__FirstLabTestResultLAB220, Lab_Result_R1);
			ProviderFormCommonFieldsMap.put(ORD3_R1__FirstLabReportingFacility, ORD3_R1);
			
			ProviderFormCommonFieldsMap.put(LAB163_R2__SecondLabSpecimenDateLAB163, LAB163_R2);
			ProviderFormCommonFieldsMap.put(LAB220_R2__SecondLabTestNameLAB220, LAB220_R2);
			ProviderFormCommonFieldsMap.put(Lab_Result_R2__SecondLabTestResultLAB220, Lab_Result_R2);
			ProviderFormCommonFieldsMap.put(ORD3_R2__SecondLabReportingFacility, ORD3_R2);
			
			
			ProviderFormCommonFieldsMap.put(LAB163_R3__ThirdLabSpecimenDateLAB163, LAB163_R3);
			ProviderFormCommonFieldsMap.put(LAB220_R3__ThirdLabTestNameLAB220, LAB220_R3);
			ProviderFormCommonFieldsMap.put(Lab_Result_R3__ThirdLabTestResultLAB220, Lab_Result_R3);
			ProviderFormCommonFieldsMap.put(ORD3_R3__ThirdLabReportingFacility, ORD3_R3);
			
			ProviderFormCommonFieldsMap.put(LAB163_R4__FourthLabSpecimenDateLAB163, LAB163_R4);
			ProviderFormCommonFieldsMap.put(LAB220_R4__FourthLabTestNameLAB220, LAB220_R4);
			ProviderFormCommonFieldsMap.put(Lab_Result_R4__FourthLabTestResultLAB220, Lab_Result_R4);
			ProviderFormCommonFieldsMap.put(ORD3_R4__FourthLabReportingFacility, ORD3_R4);
			
			ProviderFormCommonFieldsMap.put(TR101_R1__FirstTreatmentTR101_TRT100_TR101_TRT104_OrderingProviderName,TR101_R1);
			ProviderFormCommonFieldsMap.put(TR101_R2__SecondTreatmentTR101_TRT100_TR101_TRT104_OrderingProviderName,TR101_R2);
			ProviderFormCommonFieldsMap.put(TR101_R3__ThirdTreatmentTR101_TRT100_TR101_TRT104_OrderingProviderName,TR101_R3);
			ProviderFormCommonFieldsMap.put(TR101_R4__FourthTreatmentTR101_TRT100_TR101_TRT104_OrderingProviderName,TR101_R4);
			
			ProviderFormCommonFieldsMap.put(DEM102__PatientLastNameDEM102, DEM102);
			ProviderFormCommonFieldsMap.put(DEM104__PatientFirstNameDEM104, DEM104);
			ProviderFormCommonFieldsMap.put(DEM250__PatientAliasNameDEM250, DEM250);
			
			ProviderFormCommonFieldsMap.put(DEM159_160__PatientStreetDEM159, DEM159_DEM160); 
			ProviderFormCommonFieldsMap.put(DEM161__PatientCityDEM161, DEM161);
			ProviderFormCommonFieldsMap.put(DEM162__PatientStateDEM162, DEM162);
			ProviderFormCommonFieldsMap.put(DEM163__PatientZipDEM163, DEM163);
			
			ProviderFormCommonFieldsMap.put(NBS006__PatientCellNBS006_UseCd, NBS006);
			ProviderFormCommonFieldsMap.put(DEM177__PatientHmPhDEM177_UseCd, DEM177);
			ProviderFormCommonFieldsMap.put(INV2001__PatientReportedAge, INV2001);
			
			
			ProviderFormCommonFieldsMap.put(DEM115__PatientDOB, DEM115);
			ProviderFormCommonFieldsMap.put(DEM113_CD__PatientCurrentSexDEM113, DEM113_CD);
			ProviderFormCommonFieldsMap.put(NBS274_CD__PatientTransgenderNBS274, NBS274_CD);
			ProviderFormCommonFieldsMap.put(NBS272_CD__PatientSexUnkownReasonNBS272, NBS272_CD);

			ProviderFormCommonFieldsMap.put(DEM152_AI_CDT__PatientRaceAmerIndianDEM152, DEM152_AI_CDT);
			ProviderFormCommonFieldsMap.put(DEM152_A_CDT__PatientRaceAsianDEM152, DEM152_A_CDT);
			ProviderFormCommonFieldsMap.put(DEM152_B_CDT__PatientRaceBlackDEM152, DEM152_B_CDT);
			ProviderFormCommonFieldsMap.put(DEM152_W_CDT__PatientRaceWhiteDEM152, DEM152_W_CDT);
			ProviderFormCommonFieldsMap.put(DEM152_NH_CDT__PatientRaceHawaiianDEM152, DEM152_NH_CDT);
			ProviderFormCommonFieldsMap.put(DEM152_U_CDT__PatientRaceUnknownDEM152, DEM152_U_CDT);
			ProviderFormCommonFieldsMap.put(DEM152_R_CDT__PatientRaceRefusedDEM152, DEM152_R_CDT);
			ProviderFormCommonFieldsMap.put(DEM152_D_CDT__PatientRaceDidNotAskDEM152, DEM152_D_CDT);
			ProviderFormCommonFieldsMap.put(DEM152_O_CDT__PatientRaceOtherDEM152, DEM152_O_CDT);					
			
			ProviderFormCommonFieldsMap.put(DEM155_CDT__PatientEthnicityDEM155, DEM155_CDT);
			ProviderFormCommonFieldsMap.put(NBS273__PatientEthnicityUnkownReasonNBS273, NBS273);
			ProviderFormCommonFieldsMap.put(INV178_CD__PatientPregnantINV178, INV178_CD);
			ProviderFormCommonFieldsMap.put(NBS128__PatientPregnantWeeksNBS128, NBS128);
			ProviderFormCommonFieldsMap.put(DEM140_CDT__PatientMaritalStatusDEM140, DEM140_CDT);
			
			ProviderFormCommonFieldsMap.put(NBS146_1__FirstDiseaseSurveillanceDateAssignedNBS146, NBS146_1);
			ProviderFormCommonFieldsMap.put(NBS145_1__FirstDiseaseSurveillanceInvestigatorAssignedNBS145, NBS145_1);
			ProviderFormCommonFieldsMap.put(NBS147_1__FirstDiseaseSurveillanceCloseDateNBS147, NBS147_1);
			ProviderFormCommonFieldsMap.put(NBS148_1_CD__FirstDiseaseSurveillanceProviderNBS148, NBS148_1_CD);
			ProviderFormCommonFieldsMap.put(NBS151_1_CDT__FirstDiseaseSurveillanceFollowupNBS151, NBS151_1_CDT);
			
			
			ProviderFormCommonFieldsMap.put(NBS146_2__SecondDiseaseSurveillanceDateAssignedNBS146, NBS146_2);
			ProviderFormCommonFieldsMap.put(NBS145_2__SecondDiseaseSurveillanceInvestigatorAssignedNBS145, NBS145_2);
			ProviderFormCommonFieldsMap.put(NBS147_2__SecondDiseaseSurveillanceCloseDateNBS147, NBS147_2);
			ProviderFormCommonFieldsMap.put(NBS148_2_CD__SecondDiseaseSurveillanceProviderNBS148, NBS148_2_CD);
			ProviderFormCommonFieldsMap.put(NBS151_2_CDT__SecondDiseaseSurveillanceFollowupNBS151, NBS151_2_CDT);
			ProviderFormCommonFieldsMap.put("OtherInfoPhysicianNameAndAddressINV182+OtherIdentifyingInfoNBS159", "Other_Info");
		}
		
	}
	
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
				fillPDForm(acroForm,formCode,req);
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
		
	private static void fillPDForm(PDAcroForm acroForm, String invFormCd, HttpServletRequest req) throws NEDSSAppException {
		initializeProviderValues();	
		String curKey ="";
		String currentFormValue = "";
		Map<String, String> mappedFacilityDataForPrintVO =new HashMap<String, String>();
		Map<String, String> mappedProviderEntityValues   =new HashMap<String, String>();

			try {
				
			
				ActRelationshipDT actRelationshipDT = getSourceAct(proxyVO);
				Long sourceUid=null;
				if(actRelationshipDT!=null)
					sourceUid=actRelationshipDT.getSourceActUid();
				mappedProviderEntityValues= populateProviderEntityValues(proxyVO, 1);
				getMappedValues(ProviderFormCommonFieldsMap, invFormCd, req);
				populateLabFieldsFromStaging();
				answerMap = pageForm.getPageClientVO().getAnswerMap(); 
				
				String referralBasis = "";
				if (answerMap.containsKey(NEDSSConstants.REFERRAL_BASIS))
					referralBasis = (String) answerMap.get(NEDSSConstants.REFERRAL_BASIS);
				if (referralBasis.equals(NEDSSConstants.REFERRAL_BASIS_LAB) || referralBasis.equals(NEDSSConstants.REFERRAL_BASIS_MORB)) {
					mappedFacilityDataForPrintVO = getFacilityDataForPrintVO(proxyVO, referralBasis);
				}
					

				//Traverse through the list of PDF Fields

				List<PDField> pdfFormFields = acroForm.getFields();
				for(PDField pdfField: pdfFormFields){
					curKey =pdfField.getPartialName();
					currentFormValue = pdfField.getValueAsString();
					
					//If Referral Basis != Lab or Morb we don't show Provider name, address, phone fields
					if (!referralBasis.equals(NEDSSConstants.REFERRAL_BASIS_LAB) && !referralBasis.equals(NEDSSConstants.REFERRAL_BASIS_MORB) && !mappedProviderEntityValues.containsKey("ClinicPresent")) {
						if (curKey.equals(FACILITY_ADDRESS)  || curKey.equals(FACILITY_CITY)  
								|| curKey.equals(STATE_STATE_SHORT_NAME) ||curKey.equals(FACILITY_ZIP) || curKey.equals(FACILITY_PHONE)) {
							pdfField.setReadOnly(true);
							continue; //skip if not a T1 or T2
						}
					}
					if (mappedProviderEntityValues.containsKey("ClinicPresent") && mappedProviderEntityValues.containsKey(curKey)) {
						setField(pdfField, curKey, mappedProviderEntityValues.get(curKey));
						pdfField.setReadOnly(true);
					} else 	if (curKey.equals(PROVIDER_NAME_FACILITY)) {
						StringBuffer providerNameFacilityBuf = new StringBuffer("");
						if (mappedProviderEntityValues != null && mappedProviderEntityValues.containsKey(PROVIDER_NAME))
							providerNameFacilityBuf.append(mappedProviderEntityValues.get(PROVIDER_NAME));
						else if (mappedFacilityDataForPrintVO != null && mappedFacilityDataForPrintVO.containsKey(PROVIDER_FACILITY)) 
									providerNameFacilityBuf.append("/" + (String) mappedFacilityDataForPrintVO.get(PROVIDER_FACILITY));
						if (providerNameFacilityBuf.length() > 0)
							setField(pdfField, curKey, providerNameFacilityBuf.toString());
						pdfField.setReadOnly(true);
					//}else if (curKey.equals(OTHER_INFO)) {
					//	setField(pdfField, curKey, getProviderOtherInfo(mappedProviderEntityValues, answerMap));
					//	pdfField.setReadOnly(true);
					} else if (mappedFacilityDataForPrintVO.containsKey(curKey)) {
						setField(pdfField, curKey, mappedFacilityDataForPrintVO.get(curKey));
						pdfField.setReadOnly(true);
					}
					//call common
					else processPDFFIelds(pdfField, 1);
				}
			} catch (NEDSSAppException e) {
				logger.error("CDCProviderForm.fillPDForm: Error while filling up the form and IOException raises: " + e);
				logger.error("Current PDF Form FieldKey = " + curKey);
				throw new NEDSSAppException("CDCProviderForm.fillPDForm: Error while filling up the form and IOException raises: " + e);
			}
		}//end
	

	/**
	 * The ProviderrDataForPrintVO is placed in Lab or Morb summary by Observation Processor.
	 * Populate the Facility Info from the Lab or Morb Summary ProviderDataForPrintVO
	 * @param proxyVO
	 * @param referralBasis
	 * @return facility data map
	 */
	private static Map<String, String> getFacilityDataForPrintVO(PageActProxyVO proxyVO, String referralBasis) {
		
		Collection<Object> coll = null;
		
		try {
		 
		if (referralBasis.equalsIgnoreCase(NEDSSConstants.REFERRAL_BASIS_LAB)) {  //T1
			 coll = proxyVO.getTheLabReportSummaryVOCollection();
			 if (coll != null) {
			 Iterator<Object> collIter = coll.iterator();
				while (collIter.hasNext()) {
					LabReportSummaryVO labReportSummaryVO = (LabReportSummaryVO) collIter.next();
					if (labReportSummaryVO.getProviderDataForPrintVO() != null)
						return (populateFacilityFromPrintVO(labReportSummaryVO.getProviderDataForPrintVO()));
						
				}
			 }//coll not null
			
		} else if (referralBasis.equalsIgnoreCase(NEDSSConstants.REFERRAL_BASIS_MORB)) {
			 coll = proxyVO.getTheMorbReportSummaryVOCollection();
			 if (coll != null) {
				 Iterator<Object> collIter = coll.iterator();
				 while (collIter.hasNext()) {
					 MorbReportSummaryVO morbReportSummaryVO = (MorbReportSummaryVO) collIter.next();
					 if (morbReportSummaryVO.getProviderDataForPrintVO() != null)
						 return (populateFacilityFromPrintVO(morbReportSummaryVO.getProviderDataForPrintVO()));
				 }
			} //coll not null
		} //if morb	 
		}catch(Exception e){
			logger.error("getFacilityDataForPrintVO: Error while getting Ordering or Reporting Facility Info:" + e.getMessage());
		}
		return(new HashMap<String, String>());
		
	}
	/**
	 * ObservationProcessor puts data into ProviderDataForPrintVO for the Lab or the Morb.
	 * @param providerDataForPrintVO
	 * @return Map containing facility info
	 */
	private static Map<String, String> populateFacilityFromPrintVO(ProviderDataForPrintVO providerDataForPrintVO) {
		Map<String, String> returnMap = new HashMap<String, String>();
		StringBuffer facilityAddressBuf = new StringBuffer();
		StringBuffer facilityPhoneBuf = new StringBuffer();
		//get name from facility or facilityName
		if (providerDataForPrintVO.getFacility() != null && !providerDataForPrintVO.getFacility().isEmpty())
			returnMap.put(PROVIDER_FACILITY, providerDataForPrintVO.getFacility());
		else if (providerDataForPrintVO.getFacilityName() != null && !providerDataForPrintVO.getFacilityName().isEmpty())
			returnMap.put(PROVIDER_FACILITY, providerDataForPrintVO.getFacilityName());
		//append address1 and address2
		facilityAddressBuf
				.append(providerDataForPrintVO
						.getFacilityAddress1() == null ? EMPTY_STRING
						: providerDataForPrintVO.getFacilityAddress1());
		if (providerDataForPrintVO
				.getFacilityAddress2() != null)
			facilityAddressBuf
				.append(" ").append(providerDataForPrintVO.getFacilityAddress2());
		returnMap.put(FACILITY_ADDRESS,
				facilityAddressBuf.toString());

		if (providerDataForPrintVO
				.getFacilityCity() != null)
			returnMap.put(FACILITY_CITY,
					providerDataForPrintVO.getFacilityCity());
		
		String facilityStateCode = providerDataForPrintVO.getFacilityState();
		String facilityStateDesc = cache
					.getStateAbbreviationByCode(facilityStateCode);
		if (facilityStateDesc != null
					&& !facilityStateDesc.trim().equalsIgnoreCase(EMPTY_STRING)) {
				returnMap.put(STATE_SHORT_NAME,	facilityStateDesc.toString());
		}
		//Zip
		if (providerDataForPrintVO
				.getFacilityZip() != null) 
			returnMap.put(FACILITY_ZIP,
					providerDataForPrintVO.getFacilityZip());
		
		//Phone - append ext
		if (providerDataForPrintVO
				.getFacilityPhone() != null) {
			facilityPhoneBuf.append(providerDataForPrintVO.getFacilityPhone());
			if (providerDataForPrintVO.getFacilityPhoneExtension() != null 
					&& !providerDataForPrintVO.getFacilityPhoneExtension().isEmpty()) 
				facilityPhoneBuf.append(EXTENTION_TXT).append(providerDataForPrintVO.getFacilityPhoneExtension());

			returnMap.put(FACILITY_PHONE, facilityPhoneBuf.toString());
		}

		return returnMap;
	}	
	
	/**
	 * populateProviderEntityValues
	 * @param proxyVO
	 * @param i - set to 1 for main proxy, 2 for coinfection proxy
	 * @return
	 * @throws NEDSSAppException
	 */
	public static Map<String, String> populateProviderEntityValues(PageProxyVO proxyVO, int i) throws NEDSSAppException {
		try {
			PageActProxyVO actProxyVO = (PageActProxyVO) proxyVO;
			Map<String, String> entityMap = new HashMap<String, String>();

			Long physicianUid = null;
			Long facilityUid = null;
			PersonVO physicianVO = null;
			OrganizationVO clinicVO = null;

			Collection<Object> partColl = actProxyVO.getPublicHealthCaseVO()
					.getTheParticipationDTCollection();
			if (partColl != null) {
				Iterator<Object> it = partColl.iterator();
				while (it.hasNext()) {
					ParticipationDT partDT = (ParticipationDT) it.next();
					if (partDT.getTypeCd().equals(NEDSSConstants.PHC_PHYSICIAN)) {
						physicianUid = partDT.getSubjectEntityUid();
					} else if (partDT.getTypeCd().equals(NEDSSConstants.PHC_FACILITY)) {
						facilityUid = partDT.getSubjectEntityUid();
					}
				}
			}
			Collection<Object> coll = actProxyVO.getThePersonVOCollection();
			if (coll != null) {
				Iterator<Object> it = coll.iterator();
				while (it.hasNext()) {
					PersonVO personVO = (PersonVO) it.next();
						if (physicianUid != null && personVO.getThePersonDT().getPersonUid()
								.compareTo(physicianUid) == 0) {
							physicianVO = personVO;
							break;
						} //physician
				}
			}
			Collection<Object> orgColl = actProxyVO.getTheOrganizationVOCollection();
			if (orgColl != null) {
				Iterator<Object> it = orgColl.iterator();
				while (it.hasNext()) {
					OrganizationVO orgVO = (OrganizationVO) it.next();
						if (facilityUid != null && orgVO.getTheOrganizationDT().getOrganizationUid()
								.compareTo(facilityUid) == 0) {
							clinicVO = orgVO;
							break;
						} //Physician Clinic
				}
			}				
			if (physicianVO != null && clinicVO != null) {
				entityMap.putAll(putEntityNameAndAddressIntoMap(physicianVO, clinicVO, PROVIDER_NAME, true, 
						PROVIDER_ADDRESS, PROVIDER_CITY, STATE_STATE_SHORT_NAME, 
						PROVIDER_ZIP, PROVIDER_PHONE, null));
			} else if (physicianVO != null) {
				entityMap.putAll(putEntityNameAndAddressIntoMap(physicianVO, PROVIDER_NAME, true, 
						PROVIDER_ADDRESS, PROVIDER_CITY, STATE_STATE_SHORT_NAME, 
						PROVIDER_ZIP, PROVIDER_PHONE, null));
			}
			return entityMap;
		} catch (Exception e) {
			logger.error("Error while retrieving populateProviderEntityValues:  "
					+ e.toString());
			throw new NEDSSAppException("populateProviderEntityValues error thrown:", e);
		}
	}


	
}
			
