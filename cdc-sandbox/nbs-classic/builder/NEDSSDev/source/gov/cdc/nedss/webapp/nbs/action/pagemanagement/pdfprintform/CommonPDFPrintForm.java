package gov.cdc.nedss.webapp.nbs.action.pagemanagement.pdfprintform;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.rmi.PortableRemoteObject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationWidget;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

import gov.cdc.nedss.act.actid.dt.ActIdDT;
import gov.cdc.nedss.act.ctcontact.dt.CTContactAnswerDT;
import gov.cdc.nedss.act.ctcontact.dt.CTContactSummaryDT;
import gov.cdc.nedss.act.interview.dt.InterviewSummaryDT;
import gov.cdc.nedss.act.publichealthcase.dt.CaseManagementDT;
import gov.cdc.nedss.act.publichealthcase.dt.PublicHealthCaseDT;
import gov.cdc.nedss.act.treatment.dt.TreatmentAdministeredDT;
import gov.cdc.nedss.act.treatment.ejb.dao.TreatmentAdministeredDAOImpl;
import gov.cdc.nedss.association.dt.ActRelationshipDT;
import gov.cdc.nedss.association.dt.ParticipationDT;
import gov.cdc.nedss.entity.entityid.dt.EntityIdDT;
import gov.cdc.nedss.entity.organization.dt.OrganizationNameDT;
import gov.cdc.nedss.entity.organization.vo.OrganizationVO;
import gov.cdc.nedss.entity.person.dt.PersonDT;
import gov.cdc.nedss.entity.person.dt.PersonNameDT;
import gov.cdc.nedss.entity.person.dt.PersonRaceDT;
import gov.cdc.nedss.entity.person.ejb.bean.Person;
import gov.cdc.nedss.entity.person.ejb.bean.PersonHome;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.locator.dt.EntityLocatorParticipationDT;
import gov.cdc.nedss.locator.dt.PostalLocatorDT;
import gov.cdc.nedss.locator.dt.TeleLocatorDT;
import gov.cdc.nedss.page.ejb.pageproxyejb.dt.NbsAnswerDT;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.PageProxyVO;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.act.PageActProxyVO;
import gov.cdc.nedss.pam.act.NbsCaseAnswerDT;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.InvestigationSummaryVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.LabReportSummaryVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.MorbReportSummaryVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.ResultedTestSummaryVO;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.vo.CTContactProxyVO;
import gov.cdc.nedss.proxy.ejb.treatmentproxyejb.vo.TreatmentProxyVO;
import gov.cdc.nedss.proxy.ejb.treatmentproxyejb.vo.TreatmentSummaryVO;
import gov.cdc.nedss.proxy.ejb.workupproxyejb.vo.WorkupProxyVO;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.ejb.questionmapejb.dt.NbsQuestionMetadata;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.util.StringUtils;
import gov.cdc.nedss.webapp.nbs.action.contacttracing.util.CTConstants;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageConstants;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageLoadUtil;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.RenderConstants;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.util.common.PageManagementCommonActionUtil;
import gov.cdc.nedss.webapp.nbs.action.util.CallProxyEJB;
import gov.cdc.nedss.webapp.nbs.form.page.PageForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.QuestionsCache;

/**
 * CommonPDFPrintForm is a Common utility class to consolidate print functionality for PDF forms. 
 * The PDF forms supported by this class includes Field Record Form, Interview Record Form, and Provider Follow-up Form.
 * @author Pradeep Kumar Sharma
  * @updatedby pateldh
 *
 * @updatedby : Pradeep Sharma
 * Description: This code has been updated to fix data loss issue
 * Page Builder: Data Loss on Page Builder Investigations details in https://nbscentral.sramanaged.com//redmine/issues/12201
 * @version : 5.4.4 
 */
public abstract class CommonPDFPrintForm {
	static final LogUtils logger = new LogUtils(CommonPDFPrintForm.class.getName());
	/**DEM152MarginalRaceMap
	 * Constants
	 */
	
	/* Staging variables */
	protected static String[][] labStagingArray = new String[8][6];
	protected static final int DATE_INDEX = 0;
	protected static final int TEST_INDEX = 1;
	protected static final int RESULT_INDEX = 2;
	protected static final int LAB_INDEX = 3;
	protected static final int SPECIMEN_SOURCE_INDEX = 4;
	protected static final int FORMATTED_FOR_SORT_DATE_INDEX = 5;
	
	/*Field Form variables*/
	
	
	
	
	/*Field Form varaibles ends*/
	public static final String STATE_SHORT_NAME	= "ORD113";
	public static final String FACILITY_ADDRESS	= "ORD110,ORD111";
	public static final String FACILITY_CITY="ORD112";
	public static final String STATE_STATE_SHORT_NAME	= "ORD113";
	public static final String FACILITY_ZIP	="ORD114";

	public static final String INTERVIEWER="INTERVIEWER";
	public static final String PROVIDER="PROVIDER";
	public static final String PATIENT="PATIENT";
	public static final String PROVIDER_ADDRESS	= "ORD110_ORD111";
	public static final String PROVIDER_CITY="ORD112";
	public static final String PROVIDER_ZIP="ORD114";
	public static final String DEM118	= "DEM118";
	public static final String NBS195  = "NBS195"; //Interview Notes
	public static final String OTHER_INFO	= "OTHER_INFO";
	public static final String NBS145_	= "NBS145_";
	public static final String PROVIDER_PHONE	= "ORD121_ORD122";
	public static  int labIndex=1;
	public static  int treatmentIndex=1;
	public static  Collection<Object> treatmentCollection=new ArrayList<Object>();
	public static  Collection<Object> labCollection=new ArrayList<Object>();
	

	public static final int HalfLineLength = 58; //no longer used with new PDFBox
	public static final int FullLineLength = 140;//no longer used with new PDFBox
	public static final int NotesHalfWidthCharLimit = 1300;
	public static final int NotesFullWidthHalfPageCharLimit = 3000;
	public static final int NotesFullWidthFullPageCharLimit = 5600;

	public static final String NBS136_ORIGINAL_CONDITION = "NBS136_OP_"; //gst mod from NBS136
	public static final String ORIGINAL_CASE_ID = "CaseID_OP_"; //gst mod from NBS136
	public static final String NBS057_NBS056_ORIGINAL_INFECTIOUS_PERIOD = "NBS057_NBS056";
	public static final String NBS117_ORIGINAL_PATIENT_ID_NUMBER = "NBS117";  //local id of Original Inv of Contact started Inv
	public static final String PROVIDER_NAME_FACILITY="ProviderNameTileFacility"; //Provider Name Title Facility
	public static final String PROVIDER_FACILITY="ProviderFacility"; //provider order facility Name
	public static final String PROVIDER_NAME="ProviderName"; //INV181 provider name 

	public static final String NBS136 = "NBS136";
	public static final String EMPTY_STRING="";

	public static final String COND_CD = "COND_CD";
	public static final String UNABLE="UNABLE";

	public static final String NBS111	= "NBS111";
	public static final String INV107="INV107";
	public static final String NBS179="NBS179";
	public static final String NBS181="NBS181";
	public static final String NBS113	= "NBS113";
	public static final String NBS118	= "NBS118";
	public static final String NBS119	= "NBS119";
	public static final String NBS120	= "NBS120"; //lastSexualExposureDate
	public static final String NBS121	= "NBS121";
	public static final String NBS122	= "NBS122";
	public static final String NBS123	= "NBS123"; //lastNeedleExposureDate
	public static final String NBS125	= "NBS125";
	public static final String NBS243	= "NBS243";
	public static final String NBS290	= "NBS290";
	public static final String DEM165	= "DEM165"; //County
	public static final String DEM167	= "DEM167"; //Country
	public static final String DEM168	= "DEM168"; //Census Tract
	
	public static final String ADDL_SEX="Addl_Sex";
	public static final String CURRENT_SEX_CDT="Current_Sex_CDT";
	public static final String PREGNANCY_STATUS_CDT="Pregnancy_Status_CDT";
	
	
	public static final String EMPLOYMENT_PHONE="Employment_Phone";
	public static final String FF_OOJ	= "K";
	public static final String INIT_FOLL_INSUFF_INFO	= "II";

	public static final String STD116 = "STD116";
	public static final String STD106 = "STD106";
	public static final String NBS152 = "NBS152";
	public static final String BIRTH_SEX_CDT="Birth_Sex_CDT";
	public static final String BIRTH_SEX_INV_CDT="Birth_Sex_Inv_CDT";
	
	
	public static final String NBS250="NBS250";
	public static final String NBS251="NBS251";
	public static final String NBS252="NBS252";
	public static final String NBS253="NBS253";
	
	public static final String NBS217="NBS217";
	public static final String NBS219="NBS219";
	public static final String NBS130="NBS130";
	public static final String NBS132="NBS132";
	public static final String NBS134="NBS134";
	public static final String NBS257="NBS257";
	public static final String NBS267="NBS267";
	//Program Area - used for fields in Interview
	public static final String STD = "STD";
	public static final String HIV = "HIV";
	
	

	private	static String DEM182 = "DEM182"; //email address
	private	static String DEM182_R2 = "DEM182_R2"; //otherEmailAddress
	private	static String DEM142	= "DEM142"; //primary Lang
	private	static String Internet_Site = "Internet_Site";
	private static String NBS177_R2 = "NBS177_R2"; //pager

	//Case management Constants
	public static final String NBS111_CASE_MANAGEMENT_INITIATING_AGNCY	="NBS111";
	public static final String NBS112_CASE_MANAGEMENT_OOJ_INITG_AGNCY_RECD_DATE	="NBS112";
	public static final String NBS113_CASE_MANAGEMENT_OOJ_INITG_AGNCY_OUTC_DUE_DATE	="NBS113";
	public static final String NBS114_CASE_MANAGEMENT_OOJ_INITG_AGNCY_OUTC_SNT_DATE	="NBS114";
	public static final String NBS140_CASE_MANAGEMENT_INIT_FOLL_UP	="NBS140";
	public static final String NBS141_CASE_MANAGEMENT_INIT_FOLL_UP_CLOSED_DATE	="NBS141";
	public static final String NBS142_CASE_MANAGEMENT_INTERNET_FOLL_UP	="NBS142";
	public static final String NBS143_CASE_MANAGEMENT_INIT_FOLL_UP_NOTIFIABLE	="NBS143";
	public static final String NBS144_CASE_MANAGEMENT_INIT_FOLL_UP_CLINIC_CODE	="NBS144";
	public static final String NBS146_CASE_MANAGEMENT_SURV_ASSIGNED_DATE	="NBS146";
	public static final String NBS147_CASE_MANAGEMENT_SURV_CLOSED_DATE	="NBS147";
	public static final String NBS148_CASE_MANAGEMENT_SURV_PROVIDER_CONTACT	="NBS148";
	public static final String NBS149_CASE_MANAGEMENT_SURV_PROV_EXM_REASON	="NBS149";
	
	
	public static final String NBS233_DRUG_HISTORY	="NBS233";
	public static final String NBS235_DRUG_HISTORY	="NBS235";
	public static final String NBS237_DRUG_HISTORY	="NBS237";
	public static final String NBS239_DRUG_HISTORY	="NBS239";
	public static final String NBS234_DRUG_HISTORY	="NBS234";
	public static final String NBS236_DRUG_HISTORY	="NBS236";
	public static final String NBS238_DRUG_HISTORY	="NBS238";
	public static final String NBS240_DRUG_HISTORY	="NBS240";
	public static final String STD119_PARTNER_INTERNET	="STD119";
	
	public static final String NBS150_CASE_MANAGEMENT_SURV_PROV_DIAGNOSIS	="NBS150";
	public static final String NBS151_CASE_MANAGEMENT_SURV_PATIENT_FOLL_UP	="NBS151";
	public static final String NBS153_CASE_MANAGEMENT_STATUS_900	="NBS153";
	public static final String NBS155_CASE_MANAGEMENT_SUBJ_HEIGHT	="NBS155";
	public static final String NBS156_CASE_MANAGEMENT_SUBJ_SIZE_BUILD	="NBS156";
	public static final String NBS157_CASE_MANAGEMENT_SUBJ_HAIR	="NBS157";
	public static final String NBS158_CASE_MANAGEMENT_SUBJ_COMPLEXION	="NBS158";
	public static final String NBS159_CASE_MANAGEMENT_SUBJ_OTH_IDNTFYNG_INFO	="NBS159";
	public static final String NBS160_CASE_MANAGEMENT_FIELD_RECORD_NUMBER	="NBS160";
	public static final String NBS162_CASE_MANAGEMENT_FOLL_UP_ASSIGNED_DATE	="NBS162";
	public static final String NBS164_CASE_MANAGEMENT_INIT_FOLL_UP_ASSIGNED_DATE	="NBS164";
	public static final String NBS165_CASE_MANAGEMENT_FLD_FOLL_UP_PROV_EXM_REASON	="NBS165";
	public static final String NBS166_CASE_MANAGEMENT_FLD_FOLL_UP_PROV_DIAGNOSIS	="NBS166";
	public static final String NBS167_CASE_MANAGEMENT_FLD_FOLL_UP_NOTIFICATION_PLAN	="NBS167";
	public static final String NBS168_CASE_MANAGEMENT_FLD_FOLL_UP_EXPECTED_IN	="NBS168";
	public static final String NBS169_CASE_MANAGEMENT_FLD_FOLL_UP_EXPECTED_DATE	="NBS169";
	public static final String NBS170_CASE_MANAGEMENT_FLD_FOLL_UP_EXAM_DATE	="NBS170";
	public static final String NBS173_CASE_MANAGEMENT_FLD_FOLL_UP_DISPO	="NBS173";
	public static final String NBS174_CASE_MANAGEMENT_FLD_FOLL_UP_DISPO_DATE	="NBS174";
	public static final String NBS177_CASE_MANAGEMENT_ACT_REF_TYPE_CD	="NBS177";
	public static final String NBS178_CASE_MANAGEMENT_FLD_FOLL_UP_INTERNET_OUTCOME	="NBS178";
	public static final String NBS179_CASE_MANAGEMENT_OOJ_AGENCY	="NBS179";
	public static final String NBS180_CASE_MANAGEMENT_OOJ_NUMBER	="NBS180";
	public static final String NBS181_CASE_MANAGEMENT_OOJ_DUE_DATE	="NBS181";
	public static final String NBS182_CASE_MANAGEMENT_FIELD_FOLL_UP_OOJ_OUTCOME	="NBS182";
	public static final String NBS187_CASE_MANAGEMENT_INTERVIEW_ASSIGNED_DATE	="NBS187";
	public static final String NBS189_CASE_MANAGEMENT_INIT_INTERVIEW_ASSIGNED_DATE	="NBS189";
	public static final String NBS191_CASE_MANAGEMENT_EPI_LINK_ID	="NBS191";
	public static final String NBS192_CASE_MANAGEMENT_PAT_INTV_STATUS_CD	="NBS192";
	public static final String NBS196_CASE_MANAGEMENT_CASE_CLOSED_DATE	="NBS196";

	
	public static final String NBS173_ACT_ID	="INV173";
	
	public static final String 	INV107_Public_Health_Case_jurisdiction_cd	="INV107";
	public static final String 	INV108_Public_Health_Case_PROG_AREA_CD	="INV108";
	public static final String 	INV109_Public_Health_Case_investigation_status_cd	="INV109";
	public static final String 	INV110_Public_Health_Case_INVESTIGATOR_ASSIGNED_TIME	="INV110";
	public static final String 	INV111_Public_Health_Case_rpt_form_cmplt_time	="INV111";
	public static final String 	INV112_Public_Health_Case_RPT_SOURCE_CD	="INV112";
	public static final String 	INV120_Public_Health_Case_rpt_to_county_time	="INV120";
	public static final String 	INV121_Public_Health_Case_RPT_TO_STATE_TIME	="INV121";
	public static final String 	INV128_Public_Health_Case_hospitalized_ind_cd	="INV128";
	public static final String 	INV132_Public_Health_Case_HOSPITALIZED_ADMIN_TIME	="INV132";
	public static final String 	INV133_Public_Health_Case_hospitalized_discharge_time	="INV133";
	public static final String 	INV134_Public_Health_Case_HOSPITALIZED_DURATION_AMT	="INV134";
	public static final String 	INV136_Public_Health_Case_diagnosis_time	="INV136";
	public static final String 	INV137_Public_Health_Case_EFFECTIVE_FROM_TIME	="INV137";
	public static final String 	INV138_Public_Health_Case_effective_to_time	="INV138";
	public static final String 	INV139_Public_Health_Case_EFFECTIVE_DURATION_AMT	="INV139";
	public static final String 	INV140_Public_Health_Case_effective_duration_unit_cd	="INV140";
	public static final String 	INV143_Public_Health_Case_PAT_AGE_AT_ONSET	="INV143";
	public static final String 	INV144_Public_Health_Case_pat_age_at_onset_unit_cd	="INV144";
	public static final String 	INV145_Public_Health_Case_OUTCOME_CD	="INV145";
	public static final String 	INV146_Public_Health_Case_deceased_time	="INV146";
	public static final String 	INV147_Public_Health_Case_ACTIVITY_FROM_TIME	="INV147";
	public static final String 	INV148_Public_Health_Case_day_care_ind_cd	="INV148";
	public static final String 	INV149_Public_Health_Case_FOOD_HANDLER_IND_CD	="INV149";
	public static final String 	INV150_Public_Health_Case_outbreak_ind	="INV150";
	public static final String 	INV151_Public_Health_Case_OUTBREAK_NAME	="INV151";
	public static final String 	INV152_Public_Health_Case_disease_imported_cd	="INV152";
	public static final String 	INV153_Public_Health_Case_IMPORTED_COUNTRY_CD	="INV153";
	public static final String 	INV154_Public_Health_Case_imported_state_cd	="INV154";
	public static final String 	INV155_Public_Health_Case_IMPORTED_CITY_DESC_TXT	="INV155";
	public static final String 	INV156_Public_Health_Case_imported_county_cd	="INV156";
	public static final String 	INV157_Public_Health_Case_TRANSMISSION_MODE_CD	="INV157";
	public static final String 	INV159_Public_Health_Case_detection_method_cd	="INV159";
	public static final String 	INV163_Public_Health_Case_CASE_CLASS_CD	="INV163";
	public static final String 	INV165_Public_Health_Case_mmwr_week	="INV165";
	public static final String 	INV166_Public_Health_Case_MMWR_YEAR	="INV166";
	public static final String 	INV167_Public_Health_Case_txt	="INV167";
	public static final String 	INV168_Public_Health_Case_LOCAL_ID	="INV168";
	public static final String 	INV169_Public_Health_Case_CD	="INV169";
	public static final String 	INV174_Public_Health_Case_shared_ind	="INV174";
	public static final String 	INV178_Public_Health_Case_pregnant_ind_cd	="INV178";
	public static final String 	INV2006_Public_Health_Case_ACTIVITY_TO_TIME	="INV2006";
	public static final String 	INV257_Public_Health_Case_PRIORITY_CD	="INV257";
	public static final String 	INV258_Public_Health_Case_INFECTIOUS_FROM_DATE	="INV258";
	public static final String 	INV259_Public_Health_Case_INFECTIOUS_TO_DATE	="INV259";
	public static final String 	INV260_Public_Health_Case_CONTACT_INV_STATUS	="INV260";
	public static final String 	INV261_Public_Health_Case_CONTACT_INV_TXT	="INV261";
	public static final String 	NBS012_Public_Health_Case_SHARED_IND	="NBS012";
	public static final String 	NBS055_Public_Health_Case_PRIORITY_CD	="NBS055";
	public static final String 	NBS056_Public_Health_Case_INFECTIOUS_FROM_DATE	="NBS056";
	public static final String 	NBS057_Public_Health_Case_INFECTIOUS_TO_DATE	="NBS057";
	public static final String 	NBS058_Public_Health_Case_CONTACT_INV_STATUS	="NBS058";
	public static final String 	NBS059_Public_Health_Case_CONTACT_INV_TXT	="NBS059";
	public static final String 	NBS110_Public_Health_Case_REFERRAL_BASIS_CD	="NBS110";
	public static final String 	NBS115_Public_Health_Case_CURR_PROCESS_STATE_CD	="NBS115";
	public static final String 	SUM100_Public_Health_Case_rpt_cnty_cd	="SUM100";
	public static final String 	SUM101_Public_Health_Case_mmwr_year	="SUM101";
	public static final String 	SUM102_Public_Health_Case_mmwr_week	="SUM102";
	public static final String 	SUM105_Public_Health_Case_txt	="SUM105";
	public static final String 	SUM106_Public_Health_Case_cd	="SUM106";
	public static final String 	SUM113_Public_Health_Case_rpt_form_cmplt_time	="SUM113";
	public static final String 	SUM115_Public_Health_Case_count_interval_cd	="SUM115";
	public static final String 	SUM116_Public_Health_Case_case_class_cd	="SUM116";
	public static final String 	NBS260_Referred_For_900_Test = "NBS260";
	public static final String 	NBS262_900_Test_Preformed = "NBS262";
	public static final String 	NBS265_Partner_Informed_Of_900_Result = "NBS265";
	public static final String 	NBS263_HIV_Test_Result_at_this_Event = "NBS263";
	public static final String 	NBS266_Refer_For_Care = "NBS266";
	public static final String 	NBS242PlacesToMeetPartner = "NBS242";
	public static final String 	NBS244PlacesToHaveSex = "NBS244";
	public static final String 	NBS192PatientInterviewStatus = "NBS192";
	public static final String 	NBS223FemalePartnersPastYear = "NBS223";
	public static final String 	NBS225MalePartnersPastYear = "NBS225";
	public static final String 	NBS227TransgenderPartnersPastYear = "NBS227";
	public static final String 	NBS129FemalePartnersInterviewPeriod = "NBS129";
	public static final String 	NBS131MalePartnersInterviewPeriod  = "NBS131";
	public static final String 	NBS133TransgenderPartnersInterviewPeriod  = "NBS133";
	public static final String 	NBS214SpeaksEnglish = "NBS214";
	public static final String 	FirstLabDateLAB201 = "LAB201_";
	public static final String 	Pregnancy_Status_CDT	="INV178";
	
	public static final String 	INV1111	="INV1111";
	public static final String 	INV1113	="INV1113";
	public static final String 	INV163	="INV163";
	public static final String 	INV1115	="INV1115";
	public static final String 	INV1109	="INV1109";
	public static final String 	DEM126	="DEM126";
	public static final String 	INV501	="INV501";
	public static final String 	INV1116	="INV1116";
	public static final String 	INV1119	="INV1119";
	public static final String INV1120 = "INV1120";
	public static final String 	INV1276	="INV1276";
	
	public static final String code85659_1="85659_1";
	public static final String code85657_5="85657_5";
	public static final String NBS872="NBS872";
	
	//Specimen Source
	public static final String TUB128="TUB128";
	public static final String TUB132="TUB132";
	public static final String TUB865="NBS865";

	
	
	//Diagnosis testing repeatig
	
	public static final String INV290="INV290";
	public static final String LAB165="LAB165";
	public static final String LAB115="LAB115";
	

	public static final String LAB677="LAB677";
	public static final String LABAST6 = "LABAST6";
	public static final String LABAST3 = "LABAST3";
	public static final String LABAST7 = "LABAST7";
	public static final String LAB684 = "LAB684";
	public static final String code48018_6 = "48018_6";
	public static final String LAB689="LAB689";
	public static final String LAB689_OTH="LAB689_OTH";
	
	
	public static final String INV1153 = "INV1153";
	public static final String INV1154 = "INV1154";
	public static final String INV1158 = "INV1158";
	public static final String code42563_7_CD = "42563_7_CD";
	
	   
	
	public static final String CODED_VALUE	= "_CD";
	public static final String OTH_VALUE	= "_OTH";
	public static final String CODED_VALUE_TRANSLATED="_CDT";
	public static final String REPEAT_IND="_R";
	public static final String SEPERATOR="_";
	private static String delimiter1 = "__";
	private static String _1 = "_1";
	public static final String ORIGINAL_PATIENT_CONTACT_RECORD = "_OPCR";  //values from the Contact that started the Case

	//Provider Form Fields - Starting from the top left of the form
	private	static String ORD110_ORD111 = "ORD110_ORD111"; //T1LabOrderingFacilityOrT2MorbReportingOrgAddress
	private	static String ORD112 = "ORD112";//T1LabOrderingFacilityOrT2MorbReportingOrgCity
	private	static String ORD113 = "ORD113";//T1LabOrderingFacilityOrT2MorbReportingOrgState
	private	static String ORD114 = "ORD114";//T1LabOrderingFacilityOrT2MorbReportingOrgZip
	private	static String ORD121_ORD122 = "ORD121_ORD122";//T1LabOrderingFacilityOrT2MorbReportingOrgPhone with Ext

	private	static String STD121AnatomicSite = "STD121"; //hasClinicianObservedLesionsStdMap
	protected static String LAB220_R	= "LAB220_R";//LabTestName
	protected static String Lab_Result_ = "Lab_Result_R";
	protected static String ORD3_R = "ORD3_R";//LabReportingFacility
	protected static String LAB163_R = "LAB163_R";//Specimen date
	protected static String LAB165_R = "LAB165_R";//Specimen Source
	private	static String TR101 = "TR101"; //treatment
	private	static String DEM102 = "DEM102";//Last Nm
	private	static String DEM104 = "DEM104";//First_NM
	private	static String DEM104_FN = "DEM104_FN";//First_NM
	private	static String DEM105 = "DEM105";//Middle Nm
	private	static String DEM250 = "DEM250";//NickNm
	private	static String fullName = "Full_Name";//Full Name for the top of the form
	
	private	static String MaidenName = "Maiden_Name";
	private	static String DEM159_DEM160= "DEM159_DEM160";//street1+street2 - there is a space in form
	//private	static String DEM126= "DEM126";//Country of birth
	
	private	static String DEM161 = "DEM161";//City
	private	static String DEM162 = "DEM162";//State
	private	static String DEM163 = "DEM163";//Zip
	private	static String NBS006 = "NBS006";//Patient Cell
	private	static String DEM177 = "DEM177";//Hm Ph
	private	static String NBS002 = "NBS002";//Work Ph
	private	static String NBS003 = "NBS003";//Work Ph extension
	private	static String INV2001 = "INV2001";//Reported Age
	private	static String DEM115 = "DEM115";//DOB
	private	static String DEM113_CD = "DEM113_CD";//Cur Sex
	private	static String NBS274_CD = "NBS274_CD";//Transgender
	private	static String NBS272_CD = "NBS272_CD";
	private	static String DEM152 = "DEM152"; //Sex Unk Reason
	//Detailed race
	private static String DEM242 = "DEM242";
	private static String DEM243 = "DEM243";
	private static String DEM244 = "DEM244";
	private static String DEM245 = "DEM245";
	private static String DEM246 = "DEM246";
	
	
	
	private	static String DEM155 = "DEM155";//Ethnicity
	private	static String NBS273 = "NBS273";//Ethnicity Unknown Reason
	private	static String DEM140 = "DEM140";//Marital Status
	private	static String DEM197 = "DEM197";//Patient local id
	private	static String DEM127 = "DEM127";//Patient Deceased
	private	static String DEM128 = "DEM128";//Deceased Time
	
	
	
	//Interview Related form fields
	private	static String NBS186="NBS186_";
	
	//Ever worked as
	
	private static String INV1276_CD = "INV1276_CD";
	//What criteria met
	private static String INV515 = "INV515";
	private static String INV515_CD = "INV515_CD";
	
	//Sites
	private static String INV1133_CD = "INV1133_CD";
	private static String INV1133 = "INV1133";
	private static String INV1133_SecondarySites = "INV1133_SecondarySites";
	private static String INV1152_CD = "INV1152_CD";
	private static String INV1152 = "INV1152";
	private static String INV1141_CD = "INV1141_CD";
	private static String INV1141 = "INV1141";
	private static String code55753_8_CD = "55753_8_CD";
	private static String code55753_8 = "55753_8";
	private static String code55753_8B_CD = "55753_8B_CD";
	private static String code55753_8B = "55753_8B";
	private static String code64750_3 = "64750_3";
	private static String code64750_3_CD = "64750_3_CD";
	
	
	
	
	protected static PDDocument pdfDocument;
	public static final String nedssDirectory = new StringBuffer(System.getProperty("nbs.dir")).append(File.separator).toString().intern(); 
	public static final String propertiesDirectory = new StringBuffer(nedssDirectory).append("Properties").append(File.separator).toString().intern();
	public static final CachedDropDownValues cache = new CachedDropDownValues();
	public static Map<Object, Object> countryMap = cache.getCountyShortDescTxtCode();
	
	public static TreeMap<?, ?> countyCodes = null;

	protected static Map<Object,Object> questionMap = null;
	protected static Map<Object,Object> questionKeyMap = null;
	protected static Map<Object,Object> questionUidMap = null;
	protected static Map<Object,Object> contactQuestionMap = null;
	protected static Map<Object,Object> interviewQuestionMap = null;
	protected static Map<String, String> mappedInvRepeatValues = new HashMap<String, String>();
	protected static  Map<String, String> mappedDrugValues =new HashMap<String, String>();
	protected ActRelationshipDT actRelationshipDT = null;
	//protected static  Map<String, String> mappedLabValues = new HashMap<String, String>();
	//protected static Map<String, String> mappedTreatments =  new HashMap<String, String>();
	protected static Map<String, String> mappedDiagnosis = new HashMap<String, String>();
	protected static Map<String, String> mappedReferralBasis = new HashMap<String, String>();
	protected static Map<String, String>  mappedInterviewRecord =  new HashMap<String, String>();
	protected static Map<String, String> mappedOOJ =  new HashMap<String, String>();
	protected static Map<String, String> mappedMorbValues =new HashMap<String, String>();
	protected static 	Map<String, String> mappedEntityValues=null;
	protected static Map<String, String> mappedEtc=null;
	protected static Map<Object, Object>  answerMap =null;
	protected static PageForm pageForm = null;
	protected static PageActProxyVO proxyVO = null;
	protected static PageActProxyVO coProxyVO = null;
	protected static PageActProxyVO contactProxyVO = null;
	protected static PageActProxyVO contactcoProxyVO = null;
	private static Map<String, String> BirthSexMap= new HashMap<String, String>();
	private static Map<String, String> PregnancyMap= new HashMap<String, String>();
	
	private static Map<String, String> drugHistory = new HashMap<String, String>(); //NBS233_CDT, NBS235_CDT, NBS237_CDT, NBS239_CDT, NBS234_CDT, NBS236_CDT, NBS238_CDT, NBS240_CDT
	private static Map<String, String> CurrentSexMap= new HashMap<String, String>();
	private static Map<String, String> PreferredSexMap= new HashMap<String, String>();
	private static Map<String, String> SexUnknownReasonMap= new HashMap<String, String>();
	private static Map<String, String> DEM140PMaritalMap= new HashMap<String, String>();

	private static Map<String, String> DEM152RaceCodePRaceCatMap= new HashMap<String, String>();
	private static Map<String, String> DetailedRaceCodePRaceCatMapAmericanIndian= new HashMap<String, String>();
	private static Map<String, String> DetailedRaceCodePRaceCatMapAsian= new HashMap<String, String>();
	private static Map<String, String> DetailedRaceCodePRaceCatMapAfricanAmerican= new HashMap<String, String>();
	private static Map<String, String> DetailedRaceCodePRaceCatMapNativeHawaiian= new HashMap<String, String>();
	private static Map<String, String> DetailedRaceCodePRaceCatMapWhite= new HashMap<String, String>();
	
	
	

	private static Map<String, String> INV163caseStatusMap= new HashMap<String, String>();
	private static Map<String, String> INV1115CaseVerificationMap= new HashMap<String, String>();
	
	
	
	private static Map<String, String> INV1109CaseCountedMap= new HashMap<String, String>();
	private static Map<String, String> INV1111CountryOfVerifiedCase= new HashMap<String, String>();
	private static Map<String, String> INV501CountryOfUsualResidency= new HashMap<String, String>();
	private static Map<String, String> INV1276EverWorkedAs= new HashMap<String, String>();
	private static Map<String, String> LABAST6Drugs= new HashMap<String, String>();
	private static Map<String, String> code42563_7sideEffects= new HashMap<String, String>();
	
	
	
	
	
	private static Map<String, String> INV515Criteria= new HashMap<String, String>();
	
	private static Map<String, String> INV1133PrimarySites = new HashMap<String, String>();
	private static Map<String, String> INV1133SecondarySites = new HashMap<String, String>();
	
	private static Map<String, String> INV1152MovedToWhere = new HashMap<String, String>();
	private static Map<String, String> INV1141ReasonTherapyExtended = new HashMap<String, String>();
	private static Map<String, String> code55753TreatmentAdministration = new HashMap<String, String>();
	

	private static Map<String, String> code64750_3SevereAdverse = new HashMap<String, String>();
	
	
	
	
	private static Map<String, String> LABAST6DrugName = new HashMap<String, String>();
	private static Map<String, String> LABAST3SpecimenSource = new HashMap<String, String>();
	private static Map<String, String> LABAST7TestMethod = new HashMap<String, String>();
	
	private static Map<String, String> code48018_6Gene = new HashMap<String, String>();
	private static Map<String, String> LAB684SpecimenSource = new HashMap<String, String>();

	private static Map<String, String> INV1153OutOfState = new HashMap<String, String>();
	private static Map<String, String> INV1154OutOfCountry = new HashMap<String, String>();
	
	
	private static Map<String, String> INV1158Drug = new HashMap<String, String>();
	private static Map<String, String> code42563_7_CDSideEffect = new HashMap<String, String>();
	
	
	
	//85659_1 PHVS_OCCUPATION_CDC_CENSUS2010 and 85657_5 PHVS_INDUSTRY_CDC_CENSUS2010
	private static Map<String, String> code85659_1Occupation= new HashMap<String, String>();
	private static Map<String, String> code85657_5Industry= new HashMap<String, String>();
	
	
	
	private static Map<String, String> NBS872_QuantitativeResultsUnits= new HashMap<String, String>();
	private static Map<String, String> TUB128_TUB132_TUB865_SpecimenSource= new HashMap<String, String>();
	
	private static Map<String, String> INV290TestType= new HashMap<String, String>();
	private static Map<String, String> LAB165SpecimenSource= new HashMap<String, String>();
	

	
	private static Map<String, String> DEM155PhvsEthnicitygroupCdcUnkMap= new HashMap<String, String>();
	private static Map<String, String> INV178YnuMap= new HashMap<String, String>();
	protected static Map<String, String> IXS105NbsInterviewTypeMap= new HashMap<String, String>();
	private static Map<String, String> NBS151SurveillancePatientFollowupMap= new HashMap<String, String>();
	private static Map<String, String> NBS273PEthnUnkReasonMap= new HashMap<String, String>();
	private static Map<String, String> STD121PhvsClinicianObservedLesionsStdMap= new HashMap<String, String>();
	protected static Map<String, String> NBS150CaseDiagnosisMap= new HashMap<String, String>();
	private static Map<String, String> NBS149ExamReasonMap = new HashMap<String, String>();
	private static Map<String, String> STD119PartnerInternet = new HashMap<String, String>();
	private static Map<String, String> YNRUDStdMisMap = new  HashMap<String, String>();  //NBS262
	private static Map<String, String> YNStdMisMap = new   HashMap<String, String>();  //NBS260, NBS265, NBS266
	private static Map<String, String> NBS263_HIVTestResult = new   HashMap<String, String>();  //NBS263
	protected static Map<String, String> YNStdReverseMisMap = new   HashMap<String, String>();  //NBS260, NBS265, NBS266
	protected static Map<String, String> NBS192_PatientInterviewStatusMap =  new   HashMap<String, String>();  //NBS192
	protected static Map<String, String> NBS192_R_PatientInterviewStatusMap =  new   HashMap<String, String>();  //NBS192
	protected static Map<String, String> NBS129FemalePartnersInterviewPeriodMap =  new   HashMap<String, String>();  //NBS129
	protected static Map<String, String> NBS131MalePartnersInterviewPeriodMap =  new   HashMap<String, String>();  //NBS131
	protected static Map<String, String> NBS133TransgenderPartnersInterviewPeriodMap =  new   HashMap<String, String>();  //NBS133
	protected static Map<String, String> NBS125OPSpouseMap =  new   HashMap<String, String>();  //NBS125
	protected static Map<String, String> NBS223FemalePartnersPastYearMap =  new   HashMap<String, String>();  //NBS223
	protected static Map<String, String> NBS225MalePartnersPastYearMap =  new   HashMap<String, String>();  //NBS225
	protected static Map<String, String> NBS227TransgenderPartnersPastYearMap =  new   HashMap<String, String>();  //NBS225
	protected static Map<String, String> NBS242PlacesToMeetPartnerMap =  new   HashMap<String, String>();  //NBS242
	protected static Map<String, String> NBS244PlacesToHaveSexMap =  new   HashMap<String, String>();  //NBS244
	protected static Map<String, String> DEM152MarginalRaceMap =  new   HashMap<String, String>();  //NBS244
	protected static Map<String, String> formSpecificQuestionMap= new HashMap<String, String>();
	protected static Map<String, String> formSpecificQuestionAnswerMap= new HashMap<String, String>();
	
	//protected static final int LAB_COUNTER = 5;
	
	/**
	 * Initialize the translation maps
	 */
	public static void initializeValues(){
		
		formSpecificQuestionMap= new HashMap<String, String>();
		
		initializeDemographics();
		initializeCaseStatus();
		initializeCaseVerification();
		initializeCaseCounted();
		initializeCountryOfVerifiedCase();
		initializeCountryOfUsualresidency();
		initializeEverWorkedAs();
		initializeOccupation();
		initializeIndustry();
	//	initializeRestOfValues();
	
		
		//initializeEverWorkedAs2();
		initializeDEM152MarginalRace();
		initializeDEM155PhvsEthnicitygroupCdcUnkMap();
		initializeIXS105NbsInterviewTypeMap();
		initializeNBS151SurveillancePatientFollowupMap();
		initializeNBS273PEthnUnkReasonMap();
		initializeSTD121PhvsClinicianObservedLesionsStdMap();
		initializeNBS150CaseDiagnosisMap();
		initializeNBS149ExamReasonMap();
		initializeSTD119PartnerInternet();
		initializeYNRUDStdMisMap();
		initializeYNStdMisMap();
		initializeNBS263_HIVTestResult();
		initializeYNStdReverseMisMap();
		initializeNBS192_PatientInterviewStatusMap();
		initializeNBS192_R_PatientInterviewStatusMap();
		initializeNBS242PlacesToMeetPartnerMap();
		initializeNBS244PlacesToHaveSexMap();
		initializeNBS223FemalePartnersPastYearMap();
		initializeNBS225MalePartnersPastYearMap();
		initializeNBS227TransgenderPartnersPastYearMap();
		initializeNBS129FemalePartnersInterviewPeriodMap();
		initializeNBS131MalePartnersInterviewPeriodMap();
		initializeNBS133TransgenderPartnersInterviewPeriodMap();
		initializeNBS125OPSpouseMap();
		initializeNBS872_QuantitativeResultsUnits();
		initializeTUB128_TUB132_TUB865_SpecimenSource();
		initializeINV290TestType();
		initializeLAB165SpecimenSource();
		initializeINV515Criteria();
		initializeINV1133PrimarySites();
		initializeINV1152MovedToWhere();
		initializeINV1141ReasonTherapyExtended();
		initializeCode55753TreatmentAdministration();
		initializeCode64750_3SevereAdverse();
		initializeINV1133SecondarySites();
		initializeLABAST6DrugName();
		initializeLABAST6Drugs();
		initializeLABAST3SpecimenSource();
		initializeLABAST7TestMethod();
		initializeLAB684SpecimenSource();
		initializecode48018_6Gene();
		initializeINV1153OutOfState();
		initializeINV1154OutOfCountry();
		initializeINV1158Drug();
		initializeCode42563_7_CDSideEffect();
		initializeCode42563_7sideEffects();

		
			
	}

	/**
	 * SetField value (set the value in PDF form field)
	 * @param field
	 * @param name
	 * @param value
	 * @throws NEDSSAppException
	 */
	static void setField(PDField field, String name, String value)throws NEDSSAppException {
		setField(field,name,value, Boolean.TRUE);
	}
	
	/**
	 * SetField value to PDF form field to populate value in PDF printout
	 * @param field
	 * @param name
	 * @param value
	 * @param grid
	 * @throws NEDSSAppException
	 */
	static void setField(PDField field, String name, String value,Boolean grid) throws NEDSSAppException {
		try {
			if (field != null && value!=null
					&& field.getClass()
							.getName()
							.equals("org.apache.pdfbox.pdmodel.interactive.form.PDTextbox")) {
				if(grid){
				value = setValueForGridFields(name,value);
				}
				field.setValue(value);

			} else if (field != null
					&& field.getClass()
					.getName()
					.equalsIgnoreCase("org.apache.pdfbox.pdmodel.interactive.form.PDCheckbox")) {
					logger.debug("PDCheckbox field name:" + field.getPartialName());
						//((PDCheckbox) field).check();
					try{
						field.setValue(value);//Don't throw an error page and leave the field blank in case the checkbox doesn't contain the value
					}catch(IllegalArgumentException e){
						logger.error("setField: Error while setting field values for name:"+ name  +"and value="+value +" and IOException raises: " + e);
					}
						
		
			} else if (field != null && value!=null
					&& field.getClass()
							.getName()
							.equals("org.apache.pdfbox.pdmodel.interactive.form.PDRadioButton")) {
				logger.debug("PDRadioCollection field name:" + field.getPartialName());
				if( field.getPartialName().equals("STDLAB121_CD_1")){
					logger.debug("PDRadioCollection field name:" + field.getPartialName());
				}
				
				try{
				field.setValue(value);
				}catch(IllegalArgumentException e){
					logger.error("setField: Error while setting field values for name:"+ name  +"and value="+value +" and IOException raises: " + e);
				//	throw new NEDSSAppException("setField: IllegalArgumentException Error while setting field values for name:",e);
				}
				
				List<PDAnnotationWidget> widgets = field.getWidgets();
				if(widgets!=null)
				for(int i=0; i<widgets.size();i++){
					Set<COSName> COSNames=	 ((COSDictionary)widgets.get(i).getAppearance().getNormalAppearance().getCOSObject()).keySet();
					String COSNameKey = ((COSName)COSNames.toArray()[0]).getName();
					if(COSNameKey.trim().equalsIgnoreCase(value.trim()) ){
						field.setValue(COSNameKey);
					}
				}
			} else {
				logger.debug("No field found with name:" + name);
				if(value != null) field.setValue(value);
			}
		} catch (IOException e) {
			logger.error("setField: Error while setting field values for name:"+ name  +"and value="+value +" and IOException raises: " + e);
			throw new NEDSSAppException("setField:IOException Error while setting field values for name:",e);
		}
	}
	
	/**
	 * Load the questions contained in the specified form.
	 * @param formCd
	 */
	@SuppressWarnings("unchecked")
	public static void loadQuestionMap(String formCd){
		questionMap = (Map<Object, Object> )QuestionsCache.getDMBQuestionMap().get(formCd);
		questionUidMap = new HashMap<Object, Object>();
		questionKeyMap = new HashMap<Object, Object>();
		Set set = questionMap.keySet();
		if(set!=null){
			Iterator it = set.iterator();
			while(it.hasNext()){
				String key = (String)it.next();
				NbsQuestionMetadata nbsQuestionDT= (NbsQuestionMetadata)questionMap.get(key);
				if (nbsQuestionDT == null)
				{
					logger.debug("CommonPDFPrintForm: Key not found in question map ");
					continue;
				}
				Long questionUid = nbsQuestionDT.getNbsQuestionUid();
				String questionIdentifier = nbsQuestionDT.getQuestionIdentifier();
				if (questionUid != null && nbsQuestionDT != null) {
					questionUidMap.put(questionUid, nbsQuestionDT);
					if (questionIdentifier != null)
						questionKeyMap.put((Object)questionUid, (Object)questionIdentifier);
				}
			}
		}
		
		
		initializeValues();
	}
	
	/**
	 * Load the questions associated with the contact form
	 * @param formCd
	 */
	@SuppressWarnings("unchecked")
	public static void loadContactQuestionMap(String formCd){
		contactQuestionMap = (Map<Object, Object> )QuestionsCache.getDMBQuestionMap().get(formCd);
	}
	
	/**
	 * Load the questions associated with the generic interview form
	 * @param formCd
	 */
	@SuppressWarnings("unchecked")
	public static void loadInterviewQuestionMap(String formCd){
		interviewQuestionMap = (Map<Object, Object> )QuestionsCache.getDMBQuestionMap().get(formCd);	
	}
	
	/**
	 * Get the questions associated with the Investigation Form
	 * @param formCd
	 */
	public static void addQuestionMap(String formCd){
		if (formCd != null && !formCd.isEmpty()){
			 @SuppressWarnings("unchecked")
			Map<Object,Object> questionMapToAdd = (Map<Object, Object> )QuestionsCache.getDMBQuestionMap().get(formCd);
			 questionMap.putAll(questionMapToAdd);
		}
	}
	
	/**
	 * Populate treatment values from the Investigation and/or the Morb Report.
	 * @param actProxyVO
	 * @param session
	 * @throws NEDSSAppException
	 */
	public static void populateTreatmentValuesWithData(PageActProxyVO actProxyVO, HttpSession session) throws NEDSSAppException {
		try {
			Collection<Object> coll = actProxyVO.getTheTreatmentSummaryVOCollection();
			processTreatmentCollection(coll, null, session);
			Collection<Object> morbColl =actProxyVO.getTheMorbReportSummaryVOCollection();
			Iterator<Object> it = morbColl.iterator();
			while(it.hasNext()){
				//Per JW, use the Provider name from the Morb Report, don't have a performing provider
				MorbReportSummaryVO morbReportSummaryVO=(MorbReportSummaryVO)it.next();
				processTreatmentCollection(morbReportSummaryVO.getTheTreatmentSummaryVOColl(),
						morbReportSummaryVO.getProviderFullNameForPrint(), session);
			}

			
		}catch (Exception e) {
			logger.error("Error while retrieving populateTreatmentValues:  "+ e);
			throw new NEDSSAppException("Error while retrieving populateTreatmentValues:  "+ e);
		}
	}
	

	/**
	 * Process the TreatmentSummaryVO collection from the Inv or Morb Report.
	 * @param coll
	 * @param session
	 * @return
	 * @throws NEDSSAppException
	 */
	private static int processTreatmentCollection(Collection<Object> coll,  String morbProviderFullName, HttpSession session) throws NEDSSAppException{
		try {
			
			if(treatmentIndex >4)
				return treatmentIndex;
			if (coll != null) {
				Iterator<Object> it = coll.iterator();
				
				while (it.hasNext()) {
					TreatmentSummaryVO treamentSummVO = (TreatmentSummaryVO) it.next();
					// treamentSummVO.getNbsDocumentUid()==null added to eliminate treatments coming from PHDC documents.
					if(!treatmentCollection.contains(treamentSummVO.getTreatmentUid()) && treamentSummVO.getNbsDocumentUid() == null ){
						StringBuffer tratmentValues= new StringBuffer();
						String dateValue = StringUtils.formatDate(treamentSummVO.getActivityFromTime());
						if (dateValue == null || dateValue.isEmpty())
							dateValue = StringUtils.formatDate(treamentSummVO.getActivityToTime()); //Morb Report puts date in to time.
						String treatmentInfo = getTreatmentNameCode(treamentSummVO).toString();;
						tratmentValues.append(checkNull(dateValue));
						tratmentValues.append(checkNull(treatmentInfo));
						String sBeanJndiName = JNDINames.TREATMENT_PROXY_EJB;
				        String sMethod = "getTreatmentProxy";
						Object[] oParams ={treamentSummVO.getTreatmentUid()};
						TreatmentProxyVO treatmentProxyVO =(TreatmentProxyVO)CallProxyEJB.callProxyEJB(oParams , sBeanJndiName, sMethod, session);
						PersonVO provider =  PageLoadUtil.getPerson(NEDSSConstants.PROVIDER_OF_TREATMENT, treatmentProxyVO.getTheTreatmentVO().getTheParticipationDTCollection(), treatmentProxyVO.getThePersonVOCollection());
						String providerFullName = EMPTY_STRING;
						if(provider != null){
							providerFullName = provider.getThePersonDT().getFullName();
							if (providerFullName != null && !providerFullName.isEmpty()){
								if(providerFullName.contains(",")){
									String firstName = providerFullName.substring(providerFullName.indexOf(",")).replace(",", "");
									String lastName = providerFullName.substring(0,providerFullName.indexOf(","));
									providerFullName=firstName+" "+lastName;
								}
									
								tratmentValues.append(", ").append(providerFullName);
							}
						}
						if (morbProviderFullName != null && !morbProviderFullName.isEmpty() &&
								(providerFullName == null || providerFullName.isEmpty())) { //use provider from Morb Rpt
							tratmentValues.append(", ").append(morbProviderFullName);
						}
						treatmentCollection.add(treatmentProxyVO.getTheTreatmentVO().getTheTreatmentDT().getTreatmentUid());
						formSpecificQuestionAnswerMap.put(TR101+REPEAT_IND+treatmentIndex, tratmentValues.toString());
						treatmentIndex++;
					}
					
				}
			} 
			return treatmentIndex;
		} catch (NEDSSAppException e) {
			logger.error("Error while retrieving processTreatmentCollection:  "+ e);
			throw new NEDSSAppException("Error while processTreatmentCollection:  "+ e);
		}
	}
	
	
	/**
	 * Process the pdf field if we have an answer for it in the Form Specific Question Answer Map
	 * @param pdfField
	 * @param i - no longer used, used to indicate main proxy or coinf proxy
	 * @throws NEDSSAppException
	 */
	protected static void processPDFFIelds( PDField pdfField, int i) throws NEDSSAppException{
		
	//For making visible the fields with format date or number
	pdfDocument.getDocumentCatalog().getAcroForm().setNeedAppearances(true);
		
	String value="";
		try {
			if(pdfField != null){
				pdfField.setReadOnly(true);
				String key =pdfField.getPartialName();
				if(formSpecificQuestionAnswerMap!=null && formSpecificQuestionAnswerMap.get(key)!=null ){
					 value=formSpecificQuestionAnswerMap.get(key);
					setField(pdfField,key,value);
				}
			}
		} catch (Exception e) {
			 e.printStackTrace();
	            throw new NEDSSAppException("Error while loading printLoad Page Case: " + e.getMessage(), e);
		}

	}
	
	/**
	 * 
	 * @param pageForm
	 * @param req
	 * @return
	 * @throws NEDSSAppException
	 */
	public static PageActProxyVO viewPrintLoadUtil(PageForm pageForm, HttpServletRequest req)  throws NEDSSAppException{
		//PageActProxyVO proxyVO =null;
		try {
			labIndex=1;
			coProxyVO=null;
			treatmentIndex=1;
			treatmentCollection=new ArrayList<Object>();
			labStagingArray = new String[8][6];

			// Call Common framework
			HttpSession session = req.getSession();
			pageForm.setActionMode(NEDSSConstants.VIEW_LOAD_ACTION);
			pageForm.setFormFieldMap(new HashMap<Object, Object>());
			String invFormCd = pageForm.getPageFormCd();
			PageLoadUtil pageLoadUtil  = new PageLoadUtil();
			pageLoadUtil.loadQuestionKeys(invFormCd);
			if(proxyVO!=null){
				Long publicHealthCaseUID = new Long((String)NBSContext.retrieve(session,
						NBSConstantUtil.DSInvestigationUid));
				Object[] oParams = new Object[] { NEDSSConstants.PRINT_CDC_CASE, publicHealthCaseUID };
				proxyVO =(PageActProxyVO)CallProxyEJB.callProxyEJB(oParams , JNDINames.PAGE_PROXY_EJB, 
								"getPageProxyVO", session);
			}
			
			if(pageForm.getCoinfCondInvUid()!= null && !pageForm.getCoinfCondInvUid().equals("0")){
				Long coinfectionUid= new Long(pageForm.getCoinfCondInvUid());
				getCoinfectionVO( coinfectionUid, req);
			}
		
		
			// Load common PAT, INV answers and put it in answerMap for UI & Rules
			// to work
			pageLoadUtil.loadQuestionKeys(invFormCd);

			pageLoadUtil.setCommonAnswersForViewEdit(pageForm, proxyVO, req);
			 //Pam Specific Answers
			pageLoadUtil.setMSelectCBoxAnswersForViewEdit(pageForm, 
					updateMapWithQIds(((PageActProxyVO) proxyVO).getPageVO()
							.getPamAnswerDTMap()));
			//GST -NBSCentral 8855
			pageForm.getPageClientVO().setOldPageProxyVO(proxyVO);
			
			contactProxyVO=null;//for avoiding get the data form the previous investigation printed
			contactcoProxyVO=null;//for avoiding get the data form the previous investigation printed

		
			return proxyVO;
			}catch (Exception e) {
				 throw new NEDSSAppException("viewPrintLoadUtil Error while loading printLoad Page Case: " + e.getMessage(), e);
			}
	}
	
	public static void initializeIndustry(){
		

		if(code85657_5Industry.size()==0){	
			
			code85657_5Industry.put("170","Crop production [0170]");
			code85657_5Industry.put("180","Animal production [0180]");
			code85657_5Industry.put("190","Forestry except logging [0190]");
			code85657_5Industry.put("270","Logging [0270]");
			code85657_5Industry.put("280","Fishing, hunting, and trapping [0280]");
			code85657_5Industry.put("290","Support activities for agriculture and forestry [0290]");
			code85657_5Industry.put("370","Oil and gas extraction [0370]");
			code85657_5Industry.put("380","Coal mining [0380]");
			code85657_5Industry.put("390","Metal ore mining [0390]");
			code85657_5Industry.put("470","Nonmetallic mineral mining and quarrying [0470]");
			code85657_5Industry.put("480","Not specified type of mining [0480]");
			code85657_5Industry.put("490","Support activities for mining [0490]");
			code85657_5Industry.put("570","Electric power generation, transmission and distribution [0570]");
			code85657_5Industry.put("580","Natural gas distribution [0580]");
			code85657_5Industry.put("590","Electric and gas, and other combinations [0590]");
			code85657_5Industry.put("670","Water, steam, air-conditioning, and irrigation systems [0670]");
			code85657_5Industry.put("680","Sewage treatment facilities [0680]");
			code85657_5Industry.put("690","Not specified utilities [0690]");
			code85657_5Industry.put("770","Construction (the cleaning of buildings and dwellings is incidental during construction and immedia");
			code85657_5Industry.put("1070","Animal food, grain and oilseed milling [1070]");
			code85657_5Industry.put("1080","Sugar and confectionery products [1080]");
			code85657_5Industry.put("1090","Fruit and vegetable preserving and specialty food manufacturing [1090]");
			code85657_5Industry.put("1170","Dairy product manufacturing [1170]");
			code85657_5Industry.put("1180","Animal slaughtering and processing [1180]");
			code85657_5Industry.put("1190","Retail bakeries [1190]");
			code85657_5Industry.put("1270","Bakeries, except retail [1270]");
			code85657_5Industry.put("1280","Seafood and other miscellaneous foods, n.e.c. [1280]");
			code85657_5Industry.put("1290","Not specified food industries [1290]");
			code85657_5Industry.put("1370","Beverage manufacturing [1370]");
			code85657_5Industry.put("1390","Tobacco manufacturing [1390]");
			code85657_5Industry.put("1470","Fiber, yarn, and thread mills [1470]");
			code85657_5Industry.put("1480","Fabric mills, except knitting [1480]");
			code85657_5Industry.put("1490","Textile and fabric finishing and coating mills [1490]");
			code85657_5Industry.put("1570","Carpet and rug mills [1570]");
			code85657_5Industry.put("1590","Textile product mills, except carpets and rugs [1590]");
			code85657_5Industry.put("1670","Knitting fabric mills, and apparel knitting mills [1670]");
			code85657_5Industry.put("1680","Cut and sew apparel manufacturing [1680]");
			code85657_5Industry.put("1690","Apparel accessories and other apparel manufacturing [1690]");
			code85657_5Industry.put("1770","Footwear manufacturing [1770]");
			code85657_5Industry.put("1790","Leather tanning and finishing and other allied products manufacturing [1790]");
			code85657_5Industry.put("1870","Pulp, paper, and paperboard mills [1870]");
			code85657_5Industry.put("1880","Paperboard containers and boxes [1880]");
			code85657_5Industry.put("1890","Miscellaneous paper and pulp products [1890]");
			code85657_5Industry.put("1990","Printing and related support activities [1990]");
			code85657_5Industry.put("2070","Petroleum refining [2070]");
			code85657_5Industry.put("2090","Miscellaneous petroleum and coal products [2090]");
			code85657_5Industry.put("2170","Resin, synthetic rubber and fibers, and filaments manufacturing [2170]");
			code85657_5Industry.put("2180","Agricultural chemical manufacturing [2180]");
			code85657_5Industry.put("2190","Pharmaceutical and medicine manufacturing [2190]");
			code85657_5Industry.put("2270","Paint, coating, and adhesive manufacturing [2270]");
			code85657_5Industry.put("2280","Soap, cleaning compound, and cosmetics manufacturing [2280]");
			code85657_5Industry.put("2290","Industrial and miscellaneous chemicals [2290]");
			code85657_5Industry.put("2370","Plastics product manufacturing [2370]");
			code85657_5Industry.put("2380","Tire manufacturing [2380]");
			code85657_5Industry.put("2390","Rubber products, except tires, manufacturing [2390]");
			code85657_5Industry.put("2470","Pottery, ceramics, and plumbing fixture manufacturing [2470]");
			code85657_5Industry.put("2480","Structural clay product manufacturing [2480]");
			code85657_5Industry.put("2490","Glass and glass product manufacturing [2490]");
			code85657_5Industry.put("2570","Cement, concrete, lime, and gypsum product manufacturing [2570]");
			code85657_5Industry.put("2590","Miscellaneous nonmetallic mineral product manufacturing [2590]");
			code85657_5Industry.put("2670","Iron and steel mills and steel product manufacturing [2670]");
			code85657_5Industry.put("2680","Aluminum production and processing [2680]");
			code85657_5Industry.put("2690","Nonferrous metal, except aluminum, production and processing [2690]");
			code85657_5Industry.put("2770","Foundries [2770]");
			code85657_5Industry.put("2780","Metal forgings and stampings [2780]");
			code85657_5Industry.put("2790","Cutlery and hand tool manufacturing [2790]");
			code85657_5Industry.put("2870","Structural metals, and boiler, tank, and shipping container manufacturing [2870]");
			code85657_5Industry.put("2880","Machine shops; turned product; screw, nut and bolt manufacturing [2880]");
			code85657_5Industry.put("2890","Coating, engraving, heat treating and allied activities [2890]");
			code85657_5Industry.put("2970","Ordnance [2970]");
			code85657_5Industry.put("2980","Miscellaneous fabricated metal products manufacturing [2980]");
			code85657_5Industry.put("2990","Not specified metal industries [2990]");
			code85657_5Industry.put("3070","Agricultural implement manufacturing [3070]");
			code85657_5Industry.put("3080","Construction, mining and oil and gas field machinery manufacturing [3080]");
			code85657_5Industry.put("3090","Commercial and service industry machinery manufacturing [3090]");
			code85657_5Industry.put("3170","Metalworking machinery manufacturing [3170]");
			code85657_5Industry.put("3180","Engines, turbines, and power transmission equipment manufacturing [3180]");
			code85657_5Industry.put("3190","Machinery manufacturing, n.e.c. [3190]");
			code85657_5Industry.put("3290","Not specified machinery manufacturing [3290]");
			code85657_5Industry.put("3360","Computer and peripheral equipment manufacturing [3360]");
			code85657_5Industry.put("3370","Communications, audio, and video equipment manufacturing [3370]");
			code85657_5Industry.put("3380","Navigational, measuring, electromedical, and control instruments manufacturing [3380]");
			code85657_5Industry.put("3390","Electronic component and product manufacturing, n.e.c. [3390]");
			code85657_5Industry.put("3470","Household appliance manufacturing [3470]");
			code85657_5Industry.put("3490","Electrical lighting and electrical equipment manufacturing, and other electrical component manufact");
			code85657_5Industry.put("3570","Motor vehicles and motor vehicle equipment manufacturing [3570]");
			code85657_5Industry.put("3580","Aircraft and parts manufacturing [3580]");
			code85657_5Industry.put("3590","Aerospace products and parts manufacturing [3590]");
			code85657_5Industry.put("3670","Railroad rolling stock manufacturing [3670]");
			code85657_5Industry.put("3680","Ship and boat building [3680]");
			code85657_5Industry.put("3690","Other transportation equipment manufacturing [3690]");
			code85657_5Industry.put("3770","Sawmills and wood preservation [3770]");
			code85657_5Industry.put("3780","Veneer, plywood, and engineered wood products [3780]");
			code85657_5Industry.put("3790","Prefabricated wood buildings and mobile homes [3790]");
			code85657_5Industry.put("3870","Miscellaneous wood products [3870]");
			code85657_5Industry.put("3890","Furniture and related product manufacturing [3890]");
			code85657_5Industry.put("3960","Medical equipment and supplies manufacturing [3960]");
			code85657_5Industry.put("3970","Sporting and athletic goods, and doll, toy and game manufacturing [3970]");
			code85657_5Industry.put("3980","Miscellaneous manufacturing, n.e.c. [3980]");
			code85657_5Industry.put("3990","Not specified manufacturing industries [3990]");
			code85657_5Industry.put("4070","Motor vehicles, parts and supplies merchant wholesalers [4070]");
			code85657_5Industry.put("4080","Furniture and home furnishing merchant wholesalers [4080]");
			code85657_5Industry.put("4090","Lumber and other construction materials merchant wholesalers [4090]");
			code85657_5Industry.put("4170","Professional and commercial equipment and supplies merchant wholesalers [4170]");
			code85657_5Industry.put("4180","Metals and minerals, except petroleum, merchant wholesalers [4180]");
			code85657_5Industry.put("4190","Electrical and electronic goods merchant wholesalers [4190]");
			code85657_5Industry.put("4260","Hardware, plumbing and heating equipment, and supplies merchant wholesalers [4260]");
			code85657_5Industry.put("4270","Machinery, equipment, and supplies merchant wholesalers [4270]");
			code85657_5Industry.put("4280","Recyclable material merchant wholesalers [4280]");
			code85657_5Industry.put("4290","Miscellaneous durable goods merchant wholesalers [4290]");
			code85657_5Industry.put("4370","Paper and paper products merchant wholesalers [4370]");
			code85657_5Industry.put("4380","Drugs, sundries, and chemical and allied products merchant wholesalers [4380]");
			code85657_5Industry.put("4390","Apparel, fabrics, and notions merchant wholesalers [4390]");
			code85657_5Industry.put("4470","Groceries and related products merchant wholesalers [4470]");
			code85657_5Industry.put("4480","Farm product raw materials merchant wholesalers [4480]");
			code85657_5Industry.put("4490","Petroleum and petroleum products merchant wholesalers [4490]");
			code85657_5Industry.put("4560","Alcoholic beverages merchant wholesalers [4560]");
			code85657_5Industry.put("4570","Farm supplies merchant wholesalers [4570]");
			code85657_5Industry.put("4580","Miscellaneous nondurable goods merchant wholesalers [4580]");
			code85657_5Industry.put("4585","Wholesale electronic markets agents and brokers [4585]");
			code85657_5Industry.put("4590","Not specified wholesale trade [4590]");
			code85657_5Industry.put("4670","Automobile dealers [4670]");
			code85657_5Industry.put("4680","Other motor vehicle dealers [4680]");
			code85657_5Industry.put("4690","Auto parts, accessories, and tire stores [4690]");
			code85657_5Industry.put("4770","Furniture and home furnishings stores [4770]");
			code85657_5Industry.put("4780","Household appliance stores [4780]");
			code85657_5Industry.put("4790","Radio, TV, and computer stores [4790]");
			code85657_5Industry.put("4870","Building material and supplies dealers [4870]");
			code85657_5Industry.put("4880","Hardware stores [4880]");
			code85657_5Industry.put("4890","Lawn and garden equipment and supplies stores [4890]");
			code85657_5Industry.put("4970","Grocery stores [4970]");
			code85657_5Industry.put("4980","Specialty food stores [4980]");
			code85657_5Industry.put("4990","Beer, wine, and liquor stores [4990]");
			code85657_5Industry.put("5070","Pharmacies and drug stores [5070]");
			code85657_5Industry.put("5080","Health and personal care, except drug, stores [5080]");
			code85657_5Industry.put("5090","Gasoline stations [5090]");
			code85657_5Industry.put("5170","Clothing stores [5170]");
			code85657_5Industry.put("5180","Shoe stores [5180]");
			code85657_5Industry.put("5190","Jewelry, luggage, and leather goods stores [5190]");
			code85657_5Industry.put("5270","Sporting goods, camera, and hobby and toy stores [5270]");
			code85657_5Industry.put("5280","Sewing, needlework, and piece goods stores [5280]");
			code85657_5Industry.put("5290","Music stores [5290]");
			code85657_5Industry.put("5370","Book stores and news dealers [5370]");
			code85657_5Industry.put("5380","Department stores and discount stores [5380]");
			code85657_5Industry.put("5390","Miscellaneous general merchandise stores [5390]");
			code85657_5Industry.put("5470","Retail florists [5470]");
			code85657_5Industry.put("5480","Office supplies and stationery stores [5480]");
			code85657_5Industry.put("5490","Used merchandise stores [5490]");
			code85657_5Industry.put("5570","Gift, novelty, and souvenir shops [5570]");
			code85657_5Industry.put("5580","Miscellaneous retail stores [5580]");
			code85657_5Industry.put("5590","Electronic shopping [5590]");
			code85657_5Industry.put("5591","Electronic auctions [5591]");
			code85657_5Industry.put("5592","Mail order houses [5592]");
			code85657_5Industry.put("5670","Vending machine operators [5670]");
			code85657_5Industry.put("5680","Fuel dealers [5680]");
			code85657_5Industry.put("5690","Other direct selling establishments [5690]");
			code85657_5Industry.put("5790","Not specified retail trade [5790]");
			code85657_5Industry.put("6070","Air transportation [6070]");
			code85657_5Industry.put("6080","Rail transportation [6080]");
			code85657_5Industry.put("6090","Water transportation [6090]");
			code85657_5Industry.put("6170","Truck transportation [6170]");
			code85657_5Industry.put("6180","Bus service and urban transit [6180]");
			code85657_5Industry.put("6190","Taxi and limousine service [6190]");
			code85657_5Industry.put("6270","Pipeline transportation [6270]");
			code85657_5Industry.put("6280","Scenic and sightseeing transportation [6280]");
			code85657_5Industry.put("6290","Services incidental to transportation [6290]");
			code85657_5Industry.put("6370","Postal Service [6370]");
			code85657_5Industry.put("6380","Couriers and messengers [6380]");
			code85657_5Industry.put("6390","Warehousing and storage [6390]");
			code85657_5Industry.put("6470","Newspaper publishers [6470]");
			code85657_5Industry.put("6480","Periodical, book, and directory publishers [6480]");
			code85657_5Industry.put("6490","Software publishers [6490]");
			code85657_5Industry.put("6570","Motion pictures and video industries [6570]");
			code85657_5Industry.put("6590","Sound recording industries [6590]");
			code85657_5Industry.put("6670","Radio and television broadcasting and cable subscription programming [6670]");
			code85657_5Industry.put("6672","Internet publishing and broadcasting and web search portals [6672]");
			code85657_5Industry.put("6680","Wired telecommunications carriers [6680]");
			code85657_5Industry.put("6690","Other telecommunications services [6690]");
			code85657_5Industry.put("6695","Data processing, hosting, and related services [6695]");
			code85657_5Industry.put("6770","Libraries and archives [6770]");
			code85657_5Industry.put("6780","Other information services [6780]");
			code85657_5Industry.put("6870","Banking and related activities [6870]");
			code85657_5Industry.put("6880","Savings institutions, including credit unions [6880]");
			code85657_5Industry.put("6890","Non-depository credit and related activities [6890]");
			code85657_5Industry.put("6970","Securities, commodities, funds, trusts, and other financial investments [6970]");
			code85657_5Industry.put("6990","Insurance carriers and related activities [6990]");
			code85657_5Industry.put("7070","Real estate [7070]");
			code85657_5Industry.put("7080","Automotive equipment rental and leasing [7080]");
			code85657_5Industry.put("7170","Video tape and disk rental [7170]");
			code85657_5Industry.put("7180","Other consumer goods rental [7180]");
			code85657_5Industry.put("7190","Commercial, industrial, and other intangible assets rental and leasing [7190]");
			code85657_5Industry.put("7270","Legal services [7270]");
			code85657_5Industry.put("7280","Accounting, tax preparation, bookkeeping, and payroll services [7280]");
			code85657_5Industry.put("7290","Architectural, engineering, and related services [7290]");
			code85657_5Industry.put("7370","Specialized design services [7370]");
			code85657_5Industry.put("7380","Computer systems design and related services [7380]");
			code85657_5Industry.put("7390","Management, scientific, and technical consulting services [7390]");
			code85657_5Industry.put("7460","Scientific research and development services [7460]");
			code85657_5Industry.put("7470","Advertising and related services [7470]");
			code85657_5Industry.put("7480","Veterinary services [7480]");
			code85657_5Industry.put("7490","Other professional, scientific, and technical services [7490]");
			code85657_5Industry.put("7570","Management of companies and enterprises [7570]");
			code85657_5Industry.put("7580","Employment services [7580]");
			code85657_5Industry.put("7590","Business support services [7590]");
			code85657_5Industry.put("7670","Travel arrangements and reservation services [7670]");
			code85657_5Industry.put("7680","Investigation and security services [7680]");
			code85657_5Industry.put("7690","Services to buildings and dwellings (except cleaning during construction and immediately after cons");
			code85657_5Industry.put("7770","Landscaping services [7770]");
			code85657_5Industry.put("7780","Other administrative and other support services [7780]");
			code85657_5Industry.put("7790","Waste management and remediation services [7790]");
			code85657_5Industry.put("7860","Elementary and secondary schools [7860]");
			code85657_5Industry.put("7870","Colleges and universities, including junior colleges [7870]");
			code85657_5Industry.put("7880","Business, technical, and trade schools and training [7880]");
			code85657_5Industry.put("7890","Other schools and instruction, and educational support services [7890]");
			code85657_5Industry.put("7970","Offices of physicians [7970]");
			code85657_5Industry.put("7980","Offices of dentists [7980]");
			code85657_5Industry.put("7990","Offices of chiropractors [7990]");
			code85657_5Industry.put("8070","Offices of optometrists [8070]");
			code85657_5Industry.put("8080","Offices of other health practitioners [8080]");
			code85657_5Industry.put("8090","Outpatient care centers [8090]");
			code85657_5Industry.put("8170","Home health care services [8170]");
			code85657_5Industry.put("8180","Other health care services [8180]");
			code85657_5Industry.put("8190","Hospitals [8190]");
			code85657_5Industry.put("8270","Nursing care facilities [8270]");
			code85657_5Industry.put("8290","Residential care facilities, without nursing [8290]");
			code85657_5Industry.put("8370","Individual and family services [8370]");
			code85657_5Industry.put("8380","Community food and housing, and emergency services [8380]");
			code85657_5Industry.put("8390","Vocational rehabilitation services [8390]");
			code85657_5Industry.put("8470","Child day care services [8470]");
			code85657_5Industry.put("8560","Independent artists, performing arts, spectator sports, and related industries [8560]");
			code85657_5Industry.put("8570","Museums, art galleries, historical sites, and similar institutions [8570]");
			code85657_5Industry.put("8580","Bowling centers [8580]");
			code85657_5Industry.put("8590","Other amusement, gambling, and recreation industries [8590]");
			code85657_5Industry.put("8660","Traveler accommodation [8660]");
			code85657_5Industry.put("8670","Recreational vehicle parks and camps, and rooming and boarding houses [8670]");
			code85657_5Industry.put("8680","Restaurants and other food services [8680]");
			code85657_5Industry.put("8690","Drinking places, alcoholic beverages [8690]");
			code85657_5Industry.put("8770","Automotive repair and maintenance [8770]");
			code85657_5Industry.put("8780","Car washes [8780]");
			code85657_5Industry.put("8790","Electronic and precision equipment repair and maintenance [8790]");
			code85657_5Industry.put("8870","Commercial and industrial machinery and equipment repair and maintenance [8870]");
			code85657_5Industry.put("8880","Personal and household goods repair and maintenance [8880]");
			code85657_5Industry.put("8890","Footwear and leather goods repair [8890]");
			code85657_5Industry.put("8970","Barber shops [8970]");
			code85657_5Industry.put("8980","Beauty salons [8980]");
			code85657_5Industry.put("8990","Nail salons and other personal care services [8990]");
			code85657_5Industry.put("9070","Drycleaning and laundry services [9070]");
			code85657_5Industry.put("9080","Funeral homes, cemeteries, and crematories [9080]");
			code85657_5Industry.put("9090","Other personal services [9090]");
			code85657_5Industry.put("9160","Religious organizations [9160]");
			code85657_5Industry.put("9170","Civic, social, advocacy organizations, and grantmaking and giving services [9170]");
			code85657_5Industry.put("9180","Labor unions [9180]");
			code85657_5Industry.put("9190","Business, professional, political, and similar organizations [9190]");
			code85657_5Industry.put("9290","Private households [9290]");
			code85657_5Industry.put("9370","Executive offices and legislative bodies [9370]");
			code85657_5Industry.put("9380","Public finance activities [9380]");
			code85657_5Industry.put("9390","Other general government and support [9390]");
			code85657_5Industry.put("9470","Justice, public order, and safety activities [9470]");
			code85657_5Industry.put("9480","Administration of human resource programs [9480]");
			code85657_5Industry.put("9490","Administration of environmental quality and housing programs [9490]");
			code85657_5Industry.put("9570","Administration of economic programs and space research [9570]");
			code85657_5Industry.put("9590","National security and international affairs [9590]");
			code85657_5Industry.put("9670","U. S Army [9670]");
			code85657_5Industry.put("9680","U. S. Air Force [9680]");
			code85657_5Industry.put("9690","U. S. Navy [9690]");
			code85657_5Industry.put("9770","U. S. Marines [9770]");
			code85657_5Industry.put("9780","U. S. Coast Guard [9780]");
			code85657_5Industry.put("9790","U. S. Armed Forces, Branch not specified [9790]");
			code85657_5Industry.put("9870","Military Reserves or National Guard [9870]");
			code85657_5Industry.put("9880","Retired [9880]");
			code85657_5Industry.put("9890","Non-paid Worker [9890]");
			code85657_5Industry.put("9990","Insufficient Information [9990]");

			
			
			
		}
	
		
		
		
	}
	//Not needed as there is another similar method (initializeEverWorkedAs) with same values except which is a typo: 1002-5
//	private static void initializeEverWorkedAs2(){
//		
//
//		
//		
//
//			/*DetailedRaceCodePRaceCatMap.put("2054-5", "DEM244");//Black or African American  
//			DetailedRaceCodePRaceCatMap.put("2076-8", "DEM245");//Native Hawaiian or Other Pacific Islander  
//			DetailedRaceCodePRaceCatMap.put("2106-3", "DEM246");//White  */
//			
//			
//		
//		
//		if(INV1276EverWorkedAs.size()==0){
//			INV1276EverWorkedAs.put("1002-5", "C");
//			INV1276EverWorkedAs.put("223366009", "H");
//			INV1276EverWorkedAs.put("PHC2121", "M");
//			INV1276EverWorkedAs.put("UNK", "U");
//			INV1276EverWorkedAs.put("260413007", "N");
//			
//			
//			
//		}
//	}
	
	public static void initializeDEM152MarginalRace(){
		
		if(DEM152MarginalRaceMap.size()==0){
			DEM152MarginalRaceMap.put("C0682244", "AI/AN");//American Indian or Alaska Native  
			DEM152MarginalRaceMap.put("2028-9", "A");//Asian  
			DEM152MarginalRaceMap.put("2054-5", "B");//Black or African American  
			DEM152MarginalRaceMap.put("2076-8", "NH/PI");//Native Hawaiian or Other Pacific Islander  
			DEM152MarginalRaceMap.put("2106-3", "W");//White  
			DEM152MarginalRaceMap.put("2131-1", "O");//Other Race  
			DEM152MarginalRaceMap.put("NASK", "D");//not asked  
			DEM152MarginalRaceMap.put("PHC1175", "R");//Refused to answer  
			DEM152MarginalRaceMap.put("U", "U");//Unknown  
		}	
		
	}
	
	public static void initializeDEM155PhvsEthnicitygroupCdcUnkMap(){
		if(DEM155PhvsEthnicitygroupCdcUnkMap.size()==0){
			DEM155PhvsEthnicitygroupCdcUnkMap.put("2135-2", "2135-2");//Hispanic or Latino  
			DEM155PhvsEthnicitygroupCdcUnkMap.put("2186-5", "2186-5");//Not Hispanic or Latino  
			DEM155PhvsEthnicitygroupCdcUnkMap.put("UNK", "UNK");//unknown  
		}
	}
	public static void initializeIXS105NbsInterviewTypeMap(){
		if(IXS105NbsInterviewTypeMap.size()==0){
			IXS105NbsInterviewTypeMap.put("CLUSTER", "C");//Cluster  
			IXS105NbsInterviewTypeMap.put("INITIAL", "O");//Initial/Original  
			IXS105NbsInterviewTypeMap.put("REINTVW", "R");//Re-Interview  
		}
		
		}
		public static void initializeNBS151SurveillancePatientFollowupMap(){
		if(NBS151SurveillancePatientFollowupMap.size()==0){
			NBS151SurveillancePatientFollowupMap.put("PHC149", "A");//admin close  
			NBS151SurveillancePatientFollowupMap.put("RSC", "R");//rec srch clos 
			NBS151SurveillancePatientFollowupMap.put("PHC54", "S");//ooj 
			NBS151SurveillancePatientFollowupMap.put("II", "I");//insuf info
			NBS151SurveillancePatientFollowupMap.put("NPP", "N");//not prog priority
			NBS151SurveillancePatientFollowupMap.put("FF", "F");//field followup
			NBS151SurveillancePatientFollowupMap.put("PC", "P");//physician closure
		}
		}
		 
		/*if(NBS151SurveillancePatientFollowupMap.size()==0){
			NBS151SurveillancePatientFollowupMap.put("90213003", "?");//BFP - No Follow-up  
			NBS151SurveillancePatientFollowupMap.put("FF", "F");//Field Follow-up  
			NBS151SurveillancePatientFollowupMap.put("II", "I");//Insufficient Info  
			NBS151SurveillancePatientFollowupMap.put("PC", "P");//Physician Closure  
			NBS151SurveillancePatientFollowupMap.put("PHC149", "A");//Administrative Closure  
			NBS151SurveillancePatientFollowupMap.put("PHC54", "S");//Send OOJ  
			NBS151SurveillancePatientFollowupMap.put("RSC", "R");//Record Search Closure  
		}*/
		
		public static void initializeNBS273PEthnUnkReasonMap(){
		if(NBS273PEthnUnkReasonMap.size()==0){
			NBS273PEthnUnkReasonMap.put("0", "0");//Refused to answer  
			NBS273PEthnUnkReasonMap.put("6", "6");//Not asked  
		}
		}
		public static void initializeSTD121PhvsClinicianObservedLesionsStdMap(){
		if(STD121PhvsClinicianObservedLesionsStdMap.size()==0){
			STD121PhvsClinicianObservedLesionsStdMap.put("123851003", "G");//Mouth/Oral cavity  
			STD121PhvsClinicianObservedLesionsStdMap.put("18911002", "B");//Penis  
			STD121PhvsClinicianObservedLesionsStdMap.put("20233005", "C");//Scrotum  
			STD121PhvsClinicianObservedLesionsStdMap.put("22943007", "J");//Torso  
			STD121PhvsClinicianObservedLesionsStdMap.put("281088000", "A");//Anus/Rectum  
			STD121PhvsClinicianObservedLesionsStdMap.put("66019005", "K");//"Extremities (Arms, legs, feet, hands)"  
			STD121PhvsClinicianObservedLesionsStdMap.put("69536005", "I");//Head  
			STD121PhvsClinicianObservedLesionsStdMap.put("71252005", "E");//Cervix  
			STD121PhvsClinicianObservedLesionsStdMap.put("71836000", "F");//Nasopharynx  
			STD121PhvsClinicianObservedLesionsStdMap.put("76784001", "D");//Vagina  
			STD121PhvsClinicianObservedLesionsStdMap.put("PHC1161", "H");//Eye/conjunctiva  
			STD121PhvsClinicianObservedLesionsStdMap.put("PHC1168", "N");//No lesion noted  
			STD121PhvsClinicianObservedLesionsStdMap.put("PHC1170", "O");//Other anatomic site not represented in other defined anatomic sites  
			STD121PhvsClinicianObservedLesionsStdMap.put("UNK ", "U");//Unknown  
		}
		
		}
		public static void initializeNBS150CaseDiagnosisMap(){
			
		
		if(NBS150CaseDiagnosisMap.size()==0){
			NBS150CaseDiagnosisMap.put("10273", "100");//Chancroid  
			NBS150CaseDiagnosisMap.put("10274", "200");//Chlamydia trachomatis infection  
			NBS150CaseDiagnosisMap.put("10280", "300");//Gonorrhea  
			NBS150CaseDiagnosisMap.put("10280", "350");//Gonorrhea  
			NBS150CaseDiagnosisMap.put("10307", "400");//Nongonococcal urethritis (NGU)  
			NBS150CaseDiagnosisMap.put("71011005", "410");//Infestation by Phthirus pubis (disorder)  
			NBS150CaseDiagnosisMap.put("128870005", "420");//Crusted scabies (disorder)  
			NBS150CaseDiagnosisMap.put("10308", "450");//Mucopurulent cervicitis (MPC)  
			NBS150CaseDiagnosisMap.put("419760006", "460");//Bacterial vaginosis (disorder)  
			NBS150CaseDiagnosisMap.put("35089004", "470");//Urogenital infection by Trichomonas vaginalis (disorder)  
			NBS150CaseDiagnosisMap.put("78048006", "480");//Candidiasis (disorder)  
			NBS150CaseDiagnosisMap.put("10309", "490");//"Pelvic Inflammatory Disease (PID), Unknown Etiology"  
			NBS150CaseDiagnosisMap.put("10276", "500");//Granuloma inguinale (GI)  
			NBS150CaseDiagnosisMap.put("10306", "600");//Lymphogranuloma venereum (LGV)  
			NBS150CaseDiagnosisMap.put("10311", "710");//"Syphilis, primary"  
			NBS150CaseDiagnosisMap.put("10312", "720");//"Syphilis, secondary"  
			NBS150CaseDiagnosisMap.put("10313", "730");//"Syphilis, early latent"  
			NBS150CaseDiagnosisMap.put("10315", "740");//"Syphilis, unknown latent"  
			NBS150CaseDiagnosisMap.put("10314", "745");//"Syphilis, late latent"  
			NBS150CaseDiagnosisMap.put("10319", "750");//"Syphilis, late with clinical manifestations (including late benign syphilis and cardiovascular syphilis)"  
			NBS150CaseDiagnosisMap.put("266113007", "800");//Genital warts (disorder)  
			NBS150CaseDiagnosisMap.put("423391007", "850");//Genital herpes simplex type 2 (disorder)  
			NBS150CaseDiagnosisMap.put("900", "900");//900 - HIV infection  
			NBS150CaseDiagnosisMap.put("10560", "950");//AIDS  
		}
		
		}
		public static void initializeNBS149ExamReasonMap(){
		if(NBS149ExamReasonMap.size()==0){
			NBS149ExamReasonMap.put("S", "S");//Symptomatic
			NBS149ExamReasonMap.put("A", "A");//Asymptomatic
			NBS149ExamReasonMap.put("C", "C");//Contact to STD Exposure to Sexually Transmissible Disorder
			NBS149ExamReasonMap.put("P", "P");//Prenatal
			NBS149ExamReasonMap.put("D", "D");//Delivery
			NBS149ExamReasonMap.put("I", "I");//Institutional Screening
			NBS149ExamReasonMap.put("M", "M");//Community Screening
			NBS149ExamReasonMap.put("H", "H");//Health Dept Screening
			NBS149ExamReasonMap.put("U", "U");//Unk
			// NBS149ExamReasonMap.put("183688007", "Not In List"); //self referral 
		}
		}
		
		public static void initializeSTD119PartnerInternet(){
		if(STD119PartnerInternet.size()==0){
			STD119PartnerInternet.put("N", "N");//No
			STD119PartnerInternet.put("NASK", "NASK");//Did not asked
			STD119PartnerInternet.put("R", "R");//Refused to answer
			STD119PartnerInternet.put("U", "NASK");//Unknown
			STD119PartnerInternet.put("Y", "Y");//Yes
		}
		}
/*		if(NBS110ReferralBasisMap.size()==0){
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
			NBS110ReferralBasisMap.put("P", "O");//Check if NBS110 (Referral Basis) = P1, P2, P3, S1, S2, S3, A1, A2, A3 or C1 and the investigation was started from the Patient File. 
			NBS110ReferralBasisMap.put("A", "O");//Check if NBS110 (Referral Basis) = P1, P2, P3, S1, S2, S3, A1, A2, A3 or C1 and the investigation was started from the Patient File. 
		}
		if(NBS110ReferralBasisOriginMap.size()==0){
			NBS110ReferralBasisOriginMap.put("P", "NBS110_P_CD");//Print actual referral basis code if Referral Basis is P1, P2 or P3 and named in Contact Record. If co-infection is selected, separate the referral bases by a ","
			NBS110ReferralBasisOriginMap.put("A", "NBS110_A_CD");//Print actual referral basis code if Referral Basis is S1, S2, S3, A1, A2, A3 or C1 and named in Contact Record. If co-infection is selected, separate the referral bases by a ","
			NBS110ReferralBasisOriginMap.put("T", "NBS110_T_CD");//Print actual referral basis code if Referral Basis is  T1 or T2.  If co-infection is selected, separate the referral bases by a ","
			NBS110ReferralBasisOriginMap.put("O", "NBS179_O_CD");//Print actual referral basis code if Referral Basis is P1, P2, P3, S1, S2, S3, A1, A2, A3 or C1 and the investigation was started from the Patient File (not from contact record). If co-infection is selected, separate the referral bases by a ","
			
		}*/
		
		public static void initializeYNRUDStdMisMap(){
			
		
		if(YNRUDStdMisMap.size()==0){
			YNRUDStdMisMap.put("Y", "1");//Y=1 
			YNRUDStdMisMap.put("N", "0");//N=0 
			YNRUDStdMisMap.put("R", "9");//R=9 Refuse
			YNRUDStdMisMap.put("U", "9");//U=9 STDMIS default 
			YNRUDStdMisMap.put("NASK", "9");//Not ASK=9 
			YNRUDStdMisMap.put("NP", "0");//Not ASK=9 
			
		}
		}
		
		public static void initializeYNStdMisMap(){
		if(YNStdMisMap.size()==0){
			YNStdMisMap.put("Y", "1");//Y=1 
			YNStdMisMap.put("N", "0");//N=0 
			YNStdMisMap.put("2", "1");//2=1 
			
		}
		}
		
		public static void initializeNBS263_HIVTestResult(){
		if(NBS263_HIVTestResult.size()==0){
			NBS263_HIVTestResult.put("1", "01");//1=01
			NBS263_HIVTestResult.put("10", "04");//10=04
			NBS263_HIVTestResult.put("11", "03");//11=03
			NBS263_HIVTestResult.put("12", "03");//12=03 
			NBS263_HIVTestResult.put("13", "01");//13=01
			NBS263_HIVTestResult.put("14", "01");//14=01
			NBS263_HIVTestResult.put("15", "01");//15=01
			NBS263_HIVTestResult.put("16", "01");//16=01
			NBS263_HIVTestResult.put("2", "04");//2=04
			NBS263_HIVTestResult.put("3", "03");//3=03
			NBS263_HIVTestResult.put("4", "04");//4=04
			NBS263_HIVTestResult.put("5", "05");//5=05
			NBS263_HIVTestResult.put("6", "06");//6=06
			NBS263_HIVTestResult.put("7", "12");//7=12
			NBS263_HIVTestResult.put("9", "09");//9=09
		}
		}
		
		public static void initializeYNStdReverseMisMap(){
		if(YNStdReverseMisMap.size()==0){
			YNStdReverseMisMap.put("1", "Y");//Y=1 
			YNStdReverseMisMap.put("0", "N");//N=0 
		}	
		}
		
		public static void initializeNBS192_PatientInterviewStatusMap(){
		if(NBS192_PatientInterviewStatusMap.size()==0){
			NBS192_PatientInterviewStatusMap.put("A", "N");//Awaiting
			NBS192_PatientInterviewStatusMap.put("D", "N");//Died
			NBS192_PatientInterviewStatusMap.put("I", "Y");//Interviewed
			NBS192_PatientInterviewStatusMap.put("J", "N");//OOJ
			NBS192_PatientInterviewStatusMap.put("L", "N");//Language Barrier
			NBS192_PatientInterviewStatusMap.put("N", "N");//No CLuster Ix
			NBS192_PatientInterviewStatusMap.put("O", "N");//Other
			NBS192_PatientInterviewStatusMap.put("P", "N");//Physician Refusal
			NBS192_PatientInterviewStatusMap.put("R", "N");//Refused	
			NBS192_PatientInterviewStatusMap.put("U", "N");//Unable to Locate
		}
		}
		
		public static void initializeNBS192_R_PatientInterviewStatusMap(){
			
		
		if(NBS192_R_PatientInterviewStatusMap.size()==0){
			//NBS192R_PatientInterviewStatusMap.put("A", "");//Awaiting
			NBS192_R_PatientInterviewStatusMap.put("D", "D");//Died
			//NBS192R_PatientInterviewStatusMap.put("I", "");//Interviewed
			NBS192_R_PatientInterviewStatusMap.put("J", "O");//OOJ
			NBS192_R_PatientInterviewStatusMap.put("L", "L");//Language Barrier
			NBS192_R_PatientInterviewStatusMap.put("N", "N");//No CLuster Ix
			NBS192_R_PatientInterviewStatusMap.put("O", "O");//Other
			NBS192_R_PatientInterviewStatusMap.put("P", "P");//Physician Refusal
			NBS192_R_PatientInterviewStatusMap.put("R", "R");//Refused	
			NBS192_R_PatientInterviewStatusMap.put("U", "O");//Unable to Locate
		}
		}
		
		public static void initializeNBS242PlacesToMeetPartnerMap(){
		if(NBS242PlacesToMeetPartnerMap.size()==0){
			//NBS242PlacesToMeetPartner.put("Y", "");//Y=1 
			//NBS242PlacesToMeetPartner.put("N", "");//N=0 
			NBS242PlacesToMeetPartnerMap.put("R", "R");//R=9 Refuse
			NBS242PlacesToMeetPartnerMap.put("U", "U");//U=9 STDMIS default unknown Did not Ask
		}		
		}
		
		public static void initializeNBS244PlacesToHaveSexMap(){
		if(NBS244PlacesToHaveSexMap.size()==0){
			//NBS244PlacesToHaveSexMap.put("Y", "");//Y=1 
			//NBS244PlacesToHaveSexMap.put("N", "");//N=0 
			NBS244PlacesToHaveSexMap.put("R", "R");//R=9 Refuse
			NBS244PlacesToHaveSexMap.put("U", "U");//U=9 STDMIS default unknown Did not Ask
		}
		}
		public static void initializeNBS223FemalePartnersPastYearMap(){
		if (NBS223FemalePartnersPastYearMap.size()==0){
			//NBS223FemalePartnersPastYearMap.put("Y", "");//Y=1 
			//NBS223FemalePartnersPastYearMap.put("N", "");//N=0 
			NBS223FemalePartnersPastYearMap.put("R", "R");//Refuse
			NBS223FemalePartnersPastYearMap.put("U", "U");//STDMIS default unknown
		}
		}
		public static void initializeNBS225MalePartnersPastYearMap(){
		if (NBS225MalePartnersPastYearMap.size()==0){
			//NBS225MalePartnersPastYearMap.put("Y", "");//Y=1 
			//NBS225MalePartnersPastYearMap.put("N", "");//N=0 
			NBS225MalePartnersPastYearMap.put("R", "R");//Refuse
			NBS225MalePartnersPastYearMap.put("U", "U");//STDMIS default unknown
		}
		}
		public static void initializeNBS227TransgenderPartnersPastYearMap(){
		if (NBS227TransgenderPartnersPastYearMap.size()==0){
			//NBS227TransgenderPartnersPastYearMap.put("Y", "");//Y=1 
			//NBS227TransgenderPartnersPastYearMap.put("N", "");//N=0 
			NBS227TransgenderPartnersPastYearMap.put("R", "R");//Refuse
			NBS227TransgenderPartnersPastYearMap.put("U", "U");//STDMIS default unknown
		}
		}
		public static void initializeNBS129FemalePartnersInterviewPeriodMap(){
		if (NBS129FemalePartnersInterviewPeriodMap.size()==0){
			//NBS129FemalePartnersInterviewPeriodMap.put("Y", "");//Y=1 
			//NBS129FemalePartnersInterviewPeriodMap.put("N", "");//N=0 
			NBS129FemalePartnersInterviewPeriodMap.put("R", "R");//Refuse
			NBS129FemalePartnersInterviewPeriodMap.put("U", "U");//STDMIS default unknown
		}
		}
		public static void initializeNBS131MalePartnersInterviewPeriodMap(){
		if (NBS131MalePartnersInterviewPeriodMap.size()==0){
			//NBS131MalePartnersInterviewPeriodMap.put("Y", "");//Y=1 
			//NBS131MalePartnersInterviewPeriodMap.put("N", "");//N=0 
			NBS131MalePartnersInterviewPeriodMap.put("R", "R");//Refuse
			NBS131MalePartnersInterviewPeriodMap.put("U", "U");//STDMIS default unknown
		}
		}
		public static void initializeNBS133TransgenderPartnersInterviewPeriodMap(){
		if (NBS133TransgenderPartnersInterviewPeriodMap.size()==0){
			//NBS133TransgenderPartnersInterviewPeriodMap.put("Y", "");//Y=1 
			//NBS133TransgenderPartnersInterviewPeriodMap.put("N", "");//N=0 
			NBS133TransgenderPartnersInterviewPeriodMap.put("R", "R");//Refuse
			NBS133TransgenderPartnersInterviewPeriodMap.put("U", "U");//STDMIS default unknown
		}	
		}
		public static void initializeNBS125OPSpouseMap(){
		if(NBS125OPSpouseMap .size()==0){ //Contact Orig Patient Spouse?
			NBS125OPSpouseMap.put("C", "Y");//yes -current  
			NBS125OPSpouseMap.put("F", "Y");//yes - former  
			NBS125OPSpouseMap.put("N", "N");//no
			NBS125OPSpouseMap.put("U", "U");//unk
			//NBS125OPSpouseMap .put("R", "R");//not supported
		}
		}
		public static void initializeNBS872_QuantitativeResultsUnits(){
		if(NBS872_QuantitativeResultsUnits.size()==0){//Interferon Quantitative results
			NBS872_QuantitativeResultsUnits.put("{cells}/uL","cells per microliter");
			NBS872_QuantitativeResultsUnits.put("U/g","enzyme unit per gram");
			NBS872_QuantitativeResultsUnits.put("g","gram");
			NBS872_QuantitativeResultsUnits.put("g/dL","gram per deciliter");
			NBS872_QuantitativeResultsUnits.put("g/L","gram per liter");
			NBS872_QuantitativeResultsUnits.put("[IU]/L","international unit per liter");
			NBS872_QuantitativeResultsUnits.put("ug","microgram");
			NBS872_QuantitativeResultsUnits.put("ug/dL","microgram per deciliter");
			NBS872_QuantitativeResultsUnits.put("ug/L","microgram per liter");
			NBS872_QuantitativeResultsUnits.put("uL","microliter");
			NBS872_QuantitativeResultsUnits.put("umol/dL","micromole per deciliter");
			NBS872_QuantitativeResultsUnits.put("umol/L","micromole per liter");
			NBS872_QuantitativeResultsUnits.put("mU/g","millienzyme Unit per gram");
			NBS872_QuantitativeResultsUnits.put("mU/L","millienzyme Unit per liter");
			NBS872_QuantitativeResultsUnits.put("meq","milliequivalent");
			NBS872_QuantitativeResultsUnits.put("meq/L","milliequivalent per liter");
			NBS872_QuantitativeResultsUnits.put("mg","milligram");
			NBS872_QuantitativeResultsUnits.put("mg/dL","milligram per deciliter");
			NBS872_QuantitativeResultsUnits.put("mg/L","milligram per liter");
			NBS872_QuantitativeResultsUnits.put("mL","milliliter");
			NBS872_QuantitativeResultsUnits.put("mm","millimeter");
			NBS872_QuantitativeResultsUnits.put("mmol","millimole");
			NBS872_QuantitativeResultsUnits.put("mmol/L","millimole per liter");
			NBS872_QuantitativeResultsUnits.put("mosm/kg","milliosmole per kilogram");
			NBS872_QuantitativeResultsUnits.put("ng/dL","nanogram per deciliter");
			NBS872_QuantitativeResultsUnits.put("ng/L","nanogram per liter");
			NBS872_QuantitativeResultsUnits.put("ng/mL","nanogram per millliiter");
			NBS872_QuantitativeResultsUnits.put("nmol","nanomole");
			NBS872_QuantitativeResultsUnits.put("nmol/L","nanomole per liter");
			NBS872_QuantitativeResultsUnits.put("%","percent");
			NBS872_QuantitativeResultsUnits.put("{titer}","titer");

			
		
		}
		}
		
		public static void initializeTUB128_TUB132_TUB865_SpecimenSource(){
		
		if(TUB128_TUB132_TUB865_SpecimenSource.size()==0){
			TUB128_TUB132_TUB865_SpecimenSource.put("120228005","Accessory sinus");
			TUB128_TUB132_TUB865_SpecimenSource.put("23451007","Adrenal gland");
			TUB128_TUB132_TUB865_SpecimenSource.put("53505006","Anus");
			TUB128_TUB132_TUB865_SpecimenSource.put("66754008","Appendix");
			TUB128_TUB132_TUB865_SpecimenSource.put("C0541696","Bile and pancreatic fluid");
			TUB128_TUB132_TUB865_SpecimenSource.put("87612001","Blood");
			TUB128_TUB132_TUB865_SpecimenSource.put("59820001","Blood vessel");
			TUB128_TUB132_TUB865_SpecimenSource.put("272673000","Bone");
			TUB128_TUB132_TUB865_SpecimenSource.put("14016003","Bone marrow");
			TUB128_TUB132_TUB865_SpecimenSource.put("12738006","Brain");
			TUB128_TUB132_TUB865_SpecimenSource.put("76752008","Breast");
			TUB128_TUB132_TUB865_SpecimenSource.put("258446004","Bronchial fluid");
			TUB128_TUB132_TUB865_SpecimenSource.put("55214000","Bronchiole");
			TUB128_TUB132_TUB865_SpecimenSource.put("955009","Bronchus");
			TUB128_TUB132_TUB865_SpecimenSource.put("17401000","Cardiac valve");
			TUB128_TUB132_TUB865_SpecimenSource.put("71252005","Cervix");
			TUB128_TUB132_TUB865_SpecimenSource.put("71854001","Colon");
			TUB128_TUB132_TUB865_SpecimenSource.put("25087005","Cranial, spinal, and peripheral nerve");
			TUB128_TUB132_TUB865_SpecimenSource.put("65216001","CSF (cerebrospinal fluid)");
			TUB128_TUB132_TUB865_SpecimenSource.put("110708006","Ear and Mastoid Cells");
			TUB128_TUB132_TUB865_SpecimenSource.put("2739003","Endometrium");
			TUB128_TUB132_TUB865_SpecimenSource.put("110887005","Epididymis, vas deferens, spermatic cord, and scrotum");
			TUB128_TUB132_TUB865_SpecimenSource.put("110547006","Epiglottis and larynx");
			TUB128_TUB132_TUB865_SpecimenSource.put("32849002","Esophagus");
			TUB128_TUB132_TUB865_SpecimenSource.put("16014003","Extrahepatic bile duct");
			TUB128_TUB132_TUB865_SpecimenSource.put("PHC4","Eye and ear appendages");
			TUB128_TUB132_TUB865_SpecimenSource.put("110850002","Fallopian tube, broad ligament, parametrium, and paraovarian region");
			TUB128_TUB132_TUB865_SpecimenSource.put("50473004","Female genital fluids");
			TUB128_TUB132_TUB865_SpecimenSource.put("C0230999","Fetus and embryo");
			TUB128_TUB132_TUB865_SpecimenSource.put("28231008","Gallbladder");
			TUB128_TUB132_TUB865_SpecimenSource.put("168137004","Gastric aspirate");
			TUB128_TUB132_TUB865_SpecimenSource.put("39477002","Gastrointestinal contents (feces)");
			TUB128_TUB132_TUB865_SpecimenSource.put("80891009","Heart");
			TUB128_TUB132_TUB865_SpecimenSource.put("88928006","Joints (synovial tissue)");
			TUB128_TUB132_TUB865_SpecimenSource.put("64033007","Kidney");
			TUB128_TUB132_TUB865_SpecimenSource.put("91684004","Ligament and fascia");
			TUB128_TUB132_TUB865_SpecimenSource.put("48477009","Lip");
			TUB128_TUB132_TUB865_SpecimenSource.put("10200004","Liver");
			TUB128_TUB132_TUB865_SpecimenSource.put("39607008","Lung");
			TUB128_TUB132_TUB865_SpecimenSource.put("59441001","Lymph node");
			TUB128_TUB132_TUB865_SpecimenSource.put("23378005","Male genital fluids");
			TUB128_TUB132_TUB865_SpecimenSource.put("PHC8","Meninges, dural sinus, choroid plexus");
			TUB128_TUB132_TUB865_SpecimenSource.put("226789007","Milk");
			TUB128_TUB132_TUB865_SpecimenSource.put("123851003","Mouth");
			TUB128_TUB132_TUB865_SpecimenSource.put("PHC6","Multiple Sites");
			TUB128_TUB132_TUB865_SpecimenSource.put("27232003","Myometrium");
			TUB128_TUB132_TUB865_SpecimenSource.put("71836000","Nasopharynx");
			TUB128_TUB132_TUB865_SpecimenSource.put("45206002","Nose");
			TUB128_TUB132_TUB865_SpecimenSource.put("PHC7","Omentum and peritoneum");
			TUB128_TUB132_TUB865_SpecimenSource.put("OTH","Other");
			TUB128_TUB132_TUB865_SpecimenSource.put("15497006","Ovary");
			TUB128_TUB132_TUB865_SpecimenSource.put("15776009","Pancreas");
			TUB128_TUB132_TUB865_SpecimenSource.put("18911002","Penis");
			TUB128_TUB132_TUB865_SpecimenSource.put("34429004","Pericardial fluid");
			TUB128_TUB132_TUB865_SpecimenSource.put("76848001","Pericardium");
			TUB128_TUB132_TUB865_SpecimenSource.put("409614007","Peritoneal fluid");
			TUB128_TUB132_TUB865_SpecimenSource.put("54066008","Pharynx, oropharynx, and hypopharynx");
			TUB128_TUB132_TUB865_SpecimenSource.put("56329008","Pituitary gland");
			TUB128_TUB132_TUB865_SpecimenSource.put("110973009","Placenta, umbilical cord, and implantation site");
			TUB128_TUB132_TUB865_SpecimenSource.put("3120008","Pleural");
			TUB128_TUB132_TUB865_SpecimenSource.put("2778004","Pleural fluid");
			TUB128_TUB132_TUB865_SpecimenSource.put("110651000","Prostate and seminal vesicle");
			TUB128_TUB132_TUB865_SpecimenSource.put("11311000","Pus");
			TUB128_TUB132_TUB865_SpecimenSource.put("34402009","Rectum");
			TUB128_TUB132_TUB865_SpecimenSource.put("25990002","Renal pelvis");
			TUB128_TUB132_TUB865_SpecimenSource.put("256897009","Saliva");
			TUB128_TUB132_TUB865_SpecimenSource.put("385294005","Salivary gland");
			TUB128_TUB132_TUB865_SpecimenSource.put("PHC12","Skeletal system (bones of head, rib cage, and vertebral column)");
			TUB128_TUB132_TUB865_SpecimenSource.put("PHC11","Skeletal system (bones of shoulder, girdle, pelvis, and extremities)");
			TUB128_TUB132_TUB865_SpecimenSource.put("39937001","Skin and skin appendages");
			TUB128_TUB132_TUB865_SpecimenSource.put("38848004","Small intestine - duodenum");
			TUB128_TUB132_TUB865_SpecimenSource.put("110611003","Small intestine - jejunum and ileum");
			TUB128_TUB132_TUB865_SpecimenSource.put("PHC10","Soft tissue (muscles of head, neck, mouth, and upper extremity)");
			TUB128_TUB132_TUB865_SpecimenSource.put("PHC9","Soft tissue (muscles of trunk, perineum, and lower extremity)");
			TUB128_TUB132_TUB865_SpecimenSource.put("181607009","Soft tissue (not otherwise specified)");
			TUB128_TUB132_TUB865_SpecimenSource.put("2748008","Spinal cord");
			TUB128_TUB132_TUB865_SpecimenSource.put("78961009","Spleen");
			TUB128_TUB132_TUB865_SpecimenSource.put("69695003","Stomach");
			TUB128_TUB132_TUB865_SpecimenSource.put("71966008","Subcutaneous tissue");
			TUB128_TUB132_TUB865_SpecimenSource.put("6085005","Synovial fluid");
			TUB128_TUB132_TUB865_SpecimenSource.put("59863003","Tendon and tendon sheath");
			TUB128_TUB132_TUB865_SpecimenSource.put("40689003","Testis");
			TUB128_TUB132_TUB865_SpecimenSource.put("9875009","Thymus");
			TUB128_TUB132_TUB865_SpecimenSource.put("297261005","Thyroid or parathyroid gland(s)");
			TUB128_TUB132_TUB865_SpecimenSource.put("21974007","Tongue");
			TUB128_TUB132_TUB865_SpecimenSource.put("303337002","Tonsils and adenoids");
			TUB128_TUB132_TUB865_SpecimenSource.put("362102006","Tooth, gum, and supporting structures of the tooth");
			TUB128_TUB132_TUB865_SpecimenSource.put("44567001","Trachea");
			TUB128_TUB132_TUB865_SpecimenSource.put("UNK","Unknown");
			TUB128_TUB132_TUB865_SpecimenSource.put("72869002","Upper respiratory fluids");
			TUB128_TUB132_TUB865_SpecimenSource.put("87953007","Ureter");
			TUB128_TUB132_TUB865_SpecimenSource.put("13648007","Urethra");
			TUB128_TUB132_TUB865_SpecimenSource.put("89837001","Urinary bladder");
			TUB128_TUB132_TUB865_SpecimenSource.put("78014005","Urine");
			TUB128_TUB132_TUB865_SpecimenSource.put("35039007","Uterus");
			TUB128_TUB132_TUB865_SpecimenSource.put("76784001","Vagina");
			TUB128_TUB132_TUB865_SpecimenSource.put("110888000","Vulva, labia, clitoris, and Bartholin`s gland");
			
		}
		}
		
		public static void initializeINV290TestType(){
		if(INV290TestType.size()==0){
			
			INV290TestType.put("24467-3","CD4 Count");
			INV290TestType.put("50941-4","Culture");
			INV290TestType.put("10525-4","Cytology");
			INV290TestType.put("76629-5","Fasting blood glucose");
			INV290TestType.put("55454-3","Hemoglobin A1c");
			INV290TestType.put("55277-8","HIV Status");
			INV290TestType.put("LAB720","IGRA - Other");
			INV290TestType.put("LAB671","IGRA-QFT");
			INV290TestType.put("LAB672","IGRA-TSpot");
			INV290TestType.put("71773-6","IGRA-Unknown");
			INV290TestType.put("LAB673","NAA");
			INV290TestType.put("OTH","Other Test Type");
			INV290TestType.put("50595-8","Pathology");
			INV290TestType.put("LAB719","Pathology/Cytology");
			INV290TestType.put("20431-3","Smear");
			INV290TestType.put("TB119","Tuberculin Skin Test");

			
		}
		}
		
		public static void initializeLAB165SpecimenSource(){
		if(LAB165SpecimenSource.size()==0){
			LAB165SpecimenSource.put("120228005","Accessory sinus");
			LAB165SpecimenSource.put("23451007","Adrenal gland");
			LAB165SpecimenSource.put("53505006","Anus");
			LAB165SpecimenSource.put("66754008","Appendix");
			LAB165SpecimenSource.put("C0541696","Bile and pancreatic fluid");
			LAB165SpecimenSource.put("87612001","Blood");
			LAB165SpecimenSource.put("59820001","Blood vessel");
			LAB165SpecimenSource.put("272673000","Bone");
			LAB165SpecimenSource.put("14016003","Bone marrow");
			LAB165SpecimenSource.put("12738006","Brain");
			LAB165SpecimenSource.put("76752008","Breast");
			LAB165SpecimenSource.put("258446004","Bronchial fluid");
			LAB165SpecimenSource.put("55214000","Bronchiole");
			LAB165SpecimenSource.put("955009","Bronchus");
			LAB165SpecimenSource.put("17401000","Cardiac valve");
			LAB165SpecimenSource.put("71252005","Cervix");
			LAB165SpecimenSource.put("71854001","Colon");
			LAB165SpecimenSource.put("25087005","Cranial, spinal, and peripheral nerve");
			LAB165SpecimenSource.put("65216001","CSF (cerebrospinal fluid)");
			LAB165SpecimenSource.put("110708006","Ear and Mastoid Cells");
			LAB165SpecimenSource.put("2739003","Endometrium");
			LAB165SpecimenSource.put("110887005","Epididymis, vas deferens, spermatic cord, and scrotum");
			LAB165SpecimenSource.put("110547006","Epiglottis and larynx");
			LAB165SpecimenSource.put("32849002","Esophagus");
			LAB165SpecimenSource.put("16014003","Extrahepatic bile duct");
			LAB165SpecimenSource.put("PHC4","Eye and ear appendages");
			LAB165SpecimenSource.put("110850002","Fallopian tube, broad ligament, parametrium, and paraovarian region");
			LAB165SpecimenSource.put("50473004","Female genital fluids");
			LAB165SpecimenSource.put("C0230999","Fetus and embryo");
			LAB165SpecimenSource.put("28231008","Gallbladder");
			LAB165SpecimenSource.put("168137004","Gastric aspirate");
			LAB165SpecimenSource.put("39477002","Gastrointestinal contents (feces)");
			LAB165SpecimenSource.put("80891009","Heart");
			LAB165SpecimenSource.put("88928006","Joints (synovial tissue)");
			LAB165SpecimenSource.put("64033007","Kidney");
			LAB165SpecimenSource.put("91684004","Ligament and fascia");
			LAB165SpecimenSource.put("48477009","Lip");
			LAB165SpecimenSource.put("10200004","Liver");
			LAB165SpecimenSource.put("39607008","Lung");
			LAB165SpecimenSource.put("59441001","Lymph node");
			LAB165SpecimenSource.put("23378005","Male genital fluids");
			LAB165SpecimenSource.put("PHC8","Meninges, dural sinus, choroid plexus");
			LAB165SpecimenSource.put("226789007","Milk");
			LAB165SpecimenSource.put("123851003","Mouth");
			LAB165SpecimenSource.put("PHC6","Multiple Sites");
			LAB165SpecimenSource.put("27232003","Myometrium");
			LAB165SpecimenSource.put("71836000","Nasopharynx");
			LAB165SpecimenSource.put("45206002","Nose");
			LAB165SpecimenSource.put("PHC7","Omentum and peritoneum");
			LAB165SpecimenSource.put("OTH","Other");
			LAB165SpecimenSource.put("15497006","Ovary");
			LAB165SpecimenSource.put("15776009","Pancreas");
			LAB165SpecimenSource.put("18911002","Penis");
			LAB165SpecimenSource.put("34429004","Pericardial fluid");
			LAB165SpecimenSource.put("76848001","Pericardium");
			LAB165SpecimenSource.put("409614007","Peritoneal fluid");
			LAB165SpecimenSource.put("54066008","Pharynx, oropharynx, and hypopharynx");
			LAB165SpecimenSource.put("56329008","Pituitary gland");
			LAB165SpecimenSource.put("110973009","Placenta, umbilical cord, and implantation site");
			LAB165SpecimenSource.put("3120008","Pleural");
			LAB165SpecimenSource.put("2778004","Pleural fluid");
			LAB165SpecimenSource.put("110651000","Prostate and seminal vesicle");
			LAB165SpecimenSource.put("11311000","Pus");
			LAB165SpecimenSource.put("34402009","Rectum");
			LAB165SpecimenSource.put("25990002","Renal pelvis");
			LAB165SpecimenSource.put("256897009","Saliva");
			LAB165SpecimenSource.put("385294005","Salivary gland");
			LAB165SpecimenSource.put("PHC12","Skeletal system (bones of head, rib cage, and vertebral column)");
			LAB165SpecimenSource.put("PHC11","Skeletal system (bones of shoulder, girdle, pelvis, and extremities)");
			LAB165SpecimenSource.put("39937001","Skin and skin appendages");
			LAB165SpecimenSource.put("38848004","Small intestine - duodenum");
			LAB165SpecimenSource.put("110611003","Small intestine - jejunum and ileum");
			LAB165SpecimenSource.put("PHC10","Soft tissue (muscles of head, neck, mouth, and upper extremity)");
			LAB165SpecimenSource.put("PHC9","Soft tissue (muscles of trunk, perineum, and lower extremity)");
			LAB165SpecimenSource.put("181607009","Soft tissue (not otherwise specified)");
			LAB165SpecimenSource.put("2748008","Spinal cord");
			LAB165SpecimenSource.put("78961009","Spleen");
			LAB165SpecimenSource.put("119334006","Sputum");
			LAB165SpecimenSource.put("69695003","Stomach");
			LAB165SpecimenSource.put("71966008","Subcutaneous tissue");
			LAB165SpecimenSource.put("6085005","Synovial fluid");
			LAB165SpecimenSource.put("59863003","Tendon and tendon sheath");
			LAB165SpecimenSource.put("40689003","Testis");
			LAB165SpecimenSource.put("9875009","Thymus");
			LAB165SpecimenSource.put("297261005","Thyroid or parathyroid gland(s)");
			LAB165SpecimenSource.put("21974007","Tongue");
			LAB165SpecimenSource.put("303337002","Tonsils and adenoids");
			LAB165SpecimenSource.put("362102006","Tooth, gum, and supporting structures of the tooth");
			LAB165SpecimenSource.put("44567001","Trachea");
			LAB165SpecimenSource.put("UNK","Unknown");
			LAB165SpecimenSource.put("72869002","Upper respiratory fluids");
			LAB165SpecimenSource.put("87953007","Ureter");
			LAB165SpecimenSource.put("13648007","Urethra");
			LAB165SpecimenSource.put("89837001","Urinary bladder");
			LAB165SpecimenSource.put("78014005","Urine");
			LAB165SpecimenSource.put("35039007","Uterus");
			LAB165SpecimenSource.put("76784001","Vagina");
			LAB165SpecimenSource.put("110888000","Vulva, labia, clitoris, and Bartholin`s gland");

		}
		}
		
		public static void initializeINV515Criteria(){
	
			if(INV515Criteria.size()==0){
				
				INV515Criteria.put("PHC1140","S");
				INV515Criteria.put("PHC1139","C");
				INV515Criteria.put("PHC1138","R");
				INV515Criteria.put("PHC1137","W");
				INV515Criteria.put("PHC1215","B");
				INV515Criteria.put("PHC1141","O");
				
				
			}
			
		}
			
		public static void initializeINV1133PrimarySites(){
			if(INV1133PrimarySites.size()==0){
				INV1133PrimarySites.put("39607008","PU");
				INV1133PrimarySites.put("3120008","PL");
				INV1133PrimarySites.put("PHC5","SI");
				INV1133PrimarySites.put("69831007","LC");
				INV1133PrimarySites.put("281778006","LI");
				INV1133PrimarySites.put("281777001","LA");
				INV1133PrimarySites.put("PHC2","LO");
				INV1133PrimarySites.put("PHC3","LU");
				INV1133PrimarySites.put("110547006","L");
				INV1133PrimarySites.put("110522009","B");
				INV1133PrimarySites.put("21514008","G");
				INV1133PrimarySites.put("1231004","M");
				INV1133PrimarySites.put("83670000","P");
				INV1133PrimarySites.put("OTH","O");			
				
			}
			}
			public static void initializeINV1152MovedToWhere(){
			if(INV1152MovedToWhere.size()==0){
				INV1152MovedToWhere.put("PHC246","OS");
				INV1152MovedToWhere.put("PHC1911","OUS");
			}
			}
	
			
			public static void initializeINV1141ReasonTherapyExtended(){
			if(INV1141ReasonTherapyExtended.size()==0){
				INV1141ReasonTherapyExtended.put("62014003","A");
				INV1141ReasonTherapyExtended.put("PHC701","C");
				INV1141ReasonTherapyExtended.put("76797004","F");
				INV1141ReasonTherapyExtended.put("PHC700","I");
				INV1141ReasonTherapyExtended.put("258143003","N");
				INV1141ReasonTherapyExtended.put("OTH","O");
				INV1141ReasonTherapyExtended.put("UNK","U");
			}
			}
			
			public static void initializeCode55753TreatmentAdministration(){
			if(code55753TreatmentAdministration.size()==0){
			
			code55753TreatmentAdministration.put("435891000124101","D");
			code55753TreatmentAdministration.put("PHC1881","E");
			code55753TreatmentAdministration.put("225425006","S");			
			}
			}
			public static void initializeCode64750_3SevereAdverse(){
			if(code64750_3SevereAdverse.size()==0){
				
				code64750_3SevereAdverse.put("399166001","D");
				code64750_3SevereAdverse.put("434081000124108","H");
				
				
			}
			}

			
			public static void initializeINV1133SecondarySites(){

			if(INV1133SecondarySites.size()==0){
				INV1133SecondarySites.put("120228005","Accessory sinus");
				INV1133SecondarySites.put("23451007","Adrenal gland");
				INV1133SecondarySites.put("53505006","Anus");
				INV1133SecondarySites.put("66754008","Appendix");
				INV1133SecondarySites.put("87612001","Blood");
				INV1133SecondarySites.put("59820001","Blood vessel");
				INV1133SecondarySites.put("110522009","Bone and/or Joint");
				INV1133SecondarySites.put("14016003","Bone marrow");
				INV1133SecondarySites.put("12738006","Brain");
				INV1133SecondarySites.put("76752008","Breast");
				INV1133SecondarySites.put("17401000","Cardiac valve");
				INV1133SecondarySites.put("71854001","Colon");
				INV1133SecondarySites.put("25087005","Cranial, spinal, and peripheral nerve");
				INV1133SecondarySites.put("110708006","Ear and Mastoid Cells");
				INV1133SecondarySites.put("32849002","Esophagus");
				INV1133SecondarySites.put("16014003","Extrahepatic bile duct");
				INV1133SecondarySites.put("PHC4","Eye and ear appendages");
				INV1133SecondarySites.put("C0230999","Fetus and embryo");
				INV1133SecondarySites.put("28231008","Gallbladder");
				INV1133SecondarySites.put("21514008","Genitourinary");
				INV1133SecondarySites.put("80891009","Heart");
				INV1133SecondarySites.put("110547006","Laryngeal");
				INV1133SecondarySites.put("48477009","Lip");
				INV1133SecondarySites.put("10200004","Liver");
				INV1133SecondarySites.put("281777001","Lymphatic Axillary");
				INV1133SecondarySites.put("69831007","Lymphatic Cervical");
				INV1133SecondarySites.put("281778006","Lymphatic Intrathoracic");
				INV1133SecondarySites.put("PHC2","Lymphatic Other");
				INV1133SecondarySites.put("PHC3","Lymphatic Unknown");
				INV1133SecondarySites.put("1231004","Meningeal");
				INV1133SecondarySites.put("123851003","Mouth");
				INV1133SecondarySites.put("71836000","Nasopharynx");
				INV1133SecondarySites.put("45206002","Nose");
				INV1133SecondarySites.put("OTH","Other");
				INV1133SecondarySites.put("15776009","Pancreas");
				INV1133SecondarySites.put("76848001","Pericardium");
				INV1133SecondarySites.put("83670000","Peritoneal");
				INV1133SecondarySites.put("54066008","Pharynx, oropharynx, and hypopharynx");
				INV1133SecondarySites.put("56329008","Pituitary gland");
				INV1133SecondarySites.put("110973009","Placenta, umbilical cord, and implantation site");
				INV1133SecondarySites.put("3120008","Pleural");
				INV1133SecondarySites.put("39607008","Pulmonary");
				INV1133SecondarySites.put("34402009","Rectum");
				INV1133SecondarySites.put("385294005","Salivary gland");
				INV1133SecondarySites.put("PHC5","Site not Stated");
				INV1133SecondarySites.put("39937001","Skin and skin appendages");
				INV1133SecondarySites.put("38848004","Small intestine - duodenum");
				INV1133SecondarySites.put("110611003","Small intestine - jejunum and ileum");
				INV1133SecondarySites.put("2748008","Spinal cord");
				INV1133SecondarySites.put("78961009","Spleen");
				INV1133SecondarySites.put("69695003","Stomach");
				INV1133SecondarySites.put("71966008","Subcutaneous tissue");
				INV1133SecondarySites.put("9875009","Thymus");
				INV1133SecondarySites.put("297261005","Thyroid or parathyroid gland(s)");
				INV1133SecondarySites.put("21974007","Tongue");
				INV1133SecondarySites.put("303337002","Tonsils and adenoids");
				INV1133SecondarySites.put("362102006","Tooth, gum, and supporting structures of the tooth");
				INV1133SecondarySites.put("44567001","Trachea");

			
				
			}
			}
			
			public static void initializeLABAST6DrugName(){
			if(LABAST6DrugName.size()==0){
				LABAST6DrugName.put("18860-7","Amikacin");
				LABAST6DrugName.put("LAB675","Bedaquiline");
				LABAST6DrugName.put("18872-2","Capreomycin");
				LABAST6DrugName.put("18906-8","Ciprofloxacin");
				LABAST6DrugName.put("23627-3","Clofazimine");
				LABAST6DrugName.put("18914-2","Cycloserine");
				LABAST6DrugName.put("LAB676","Delamanid");
				LABAST6DrugName.put("18921-7","Ethambutol");
				LABAST6DrugName.put("18922-5","Ethionamide");
				LABAST6DrugName.put("18934-0","Isoniazid");
				LABAST6DrugName.put("18935-7","Kanamycin");
				LABAST6DrugName.put("20629-2","Levofloxacin");
				LABAST6DrugName.put("29258-1","Linezolid");
				LABAST6DrugName.put("31039-1","Moxifloxacin");
				LABAST6DrugName.put("18959-7","Ofloxacin");
				LABAST6DrugName.put("LAB674","Other Quinolones");
				LABAST6DrugName.put("OTH","Other Test Type");
				LABAST6DrugName.put("23629-9","Para-Aminesalicylicacid");
				LABAST6DrugName.put("93850-6","Pretomanid");
				LABAST6DrugName.put("18973-8","Pyrazinamide");
				LABAST6DrugName.put("19149-4","Rifabutin");
				LABAST6DrugName.put("18974-6","Rifampin");
				LABAST6DrugName.put("76627-9","Rifapentine");
				LABAST6DrugName.put("18982-9","Streptomycin");

			}
			}
			public static void initializeLABAST6Drugs(){
			if(LABAST6Drugs.size()==0){
				//reading from the map to make sure the names are consistent
				LABAST6Drugs.put(LABAST6DrugName.get("18934-0"), "1");//Isoniazid
				LABAST6Drugs.put(LABAST6DrugName.get("18974-6"), "2");//Rifampin
				LABAST6Drugs.put(LABAST6DrugName.get("18973-8"), "3");//Pyrazinamide
				LABAST6Drugs.put(LABAST6DrugName.get("18921-7"), "4");//Ethambutol
				LABAST6Drugs.put(LABAST6DrugName.get("18982-9"), "5");//Streptomycin		
				LABAST6Drugs.put(LABAST6DrugName.get("19149-4"), "6");//Rifabutin		
				LABAST6Drugs.put(LABAST6DrugName.get("76627-9"),"7");	//Rifapentine	
				LABAST6Drugs.put(LABAST6DrugName.get("18922-5"), "8");	//Ethionamide	
				LABAST6Drugs.put(LABAST6DrugName.get("18860-7"), "9");		//Amikacin
				LABAST6Drugs.put(LABAST6DrugName.get("18935-7"), "10");//Kanamycin		
				LABAST6Drugs.put(LABAST6DrugName.get("18872-2"), "11");//Capreomycin		
				LABAST6Drugs.put(LABAST6DrugName.get("18906-8"), "12");//Ciprofloxacin		
				LABAST6Drugs.put(LABAST6DrugName.get("20629-2"), "13");//Levofloxacin		
				LABAST6Drugs.put(LABAST6DrugName.get("18959-7"), "14");//Ofloxacin	
				LABAST6Drugs.put(LABAST6DrugName.get("31039-1"), "15");//Moxifloxacin	
				LABAST6Drugs.put(LABAST6DrugName.get("LAB674"), "16");//Other Quinolones	
				LABAST6Drugs.put(LABAST6DrugName.get("18914-2"), "17");//Cycloserine	
				LABAST6Drugs.put(LABAST6DrugName.get("23629-9"), "18");//Para-Aminesalicylicacid	
				LABAST6Drugs.put(LABAST6DrugName.get("29258-1"), "19");	//Linezolid
				LABAST6Drugs.put(LABAST6DrugName.get("LAB675"), "20");//Bedaquiline		
				LABAST6Drugs.put(LABAST6DrugName.get("LAB676"), "21");	//Delamanid
				LABAST6Drugs.put(LABAST6DrugName.get("23627-3"), "22");//Clofazimine	
				LABAST6Drugs.put(LABAST6DrugName.get("93850-6"), "23");		//Pretomanid
			//	LABAST6Drugs.put(LABAST6DrugName.get("OTH"), "24");		//Other Test Type	
				
			}
			
			}
			
			public static void initializeLABAST3SpecimenSource(){
			if(LABAST3SpecimenSource.size()==0){
				LABAST3SpecimenSource.put("120228005","Accessory sinus");
				LABAST3SpecimenSource.put("23451007","Adrenal gland");
				LABAST3SpecimenSource.put("53505006","Anus");
				LABAST3SpecimenSource.put("66754008","Appendix");
				LABAST3SpecimenSource.put("C0541696","Bile and pancreatic fluid");
				LABAST3SpecimenSource.put("87612001","Blood");
				LABAST3SpecimenSource.put("59820001","Blood vessel");
				LABAST3SpecimenSource.put("272673000","Bone");
				LABAST3SpecimenSource.put("14016003","Bone marrow");
				LABAST3SpecimenSource.put("12738006","Brain");
				LABAST3SpecimenSource.put("76752008","Breast");
				LABAST3SpecimenSource.put("258446004","Bronchial fluid");
				LABAST3SpecimenSource.put("55214000","Bronchiole");
				LABAST3SpecimenSource.put("955009","Bronchus");
				LABAST3SpecimenSource.put("17401000","Cardiac valve");
				LABAST3SpecimenSource.put("71252005","Cervix");
				LABAST3SpecimenSource.put("71854001","Colon");
				LABAST3SpecimenSource.put("25087005","Cranial, spinal, and peripheral nerve");
				LABAST3SpecimenSource.put("65216001","CSF (cerebrospinal fluid)");
				LABAST3SpecimenSource.put("110708006","Ear and Mastoid Cells");
				LABAST3SpecimenSource.put("2739003","Endometrium");
				LABAST3SpecimenSource.put("110887005","Epididymis, vas deferens, spermatic cord, and scrotum");
				LABAST3SpecimenSource.put("110547006","Epiglottis and larynx");
				LABAST3SpecimenSource.put("32849002","Esophagus");
				LABAST3SpecimenSource.put("16014003","Extrahepatic bile duct");
				LABAST3SpecimenSource.put("PHC4","Eye and ear appendages");
				LABAST3SpecimenSource.put("110850002","Fallopian tube, broad ligament, parametrium, and paraovarian region");
				LABAST3SpecimenSource.put("50473004","Female genital fluids");
				LABAST3SpecimenSource.put("C0230999","Fetus and embryo");
				LABAST3SpecimenSource.put("28231008","Gallbladder");
				LABAST3SpecimenSource.put("168137004","Gastric aspirate");
				LABAST3SpecimenSource.put("39477002","Gastrointestinal contents (feces)");
				LABAST3SpecimenSource.put("80891009","Heart");
				LABAST3SpecimenSource.put("88928006","Joints (synovial tissue)");
				LABAST3SpecimenSource.put("64033007","Kidney");
				LABAST3SpecimenSource.put("91684004","Ligament and fascia");
				LABAST3SpecimenSource.put("48477009","Lip");
				LABAST3SpecimenSource.put("10200004","Liver");
				LABAST3SpecimenSource.put("39607008","Lung");
				LABAST3SpecimenSource.put("59441001","Lymph node");
				LABAST3SpecimenSource.put("23378005","Male genital fluids");
				LABAST3SpecimenSource.put("PHC8","Meninges, dural sinus, choroid plexus");
				LABAST3SpecimenSource.put("226789007","Milk");
				LABAST3SpecimenSource.put("123851003","Mouth");
				LABAST3SpecimenSource.put("PHC6","Multiple Sites");
				LABAST3SpecimenSource.put("27232003","Myometrium");
				LABAST3SpecimenSource.put("71836000","Nasopharynx");
				LABAST3SpecimenSource.put("45206002","Nose");
				LABAST3SpecimenSource.put("PHC7","Omentum and peritoneum");
				LABAST3SpecimenSource.put("OTH","Other");
				LABAST3SpecimenSource.put("15497006","Ovary");
				LABAST3SpecimenSource.put("15776009","Pancreas");
				LABAST3SpecimenSource.put("18911002","Penis");
				LABAST3SpecimenSource.put("34429004","Pericardial fluid");
				LABAST3SpecimenSource.put("76848001","Pericardium");
				LABAST3SpecimenSource.put("409614007","Peritoneal fluid");
				LABAST3SpecimenSource.put("54066008","Pharynx, oropharynx, and hypopharynx");
				LABAST3SpecimenSource.put("56329008","Pituitary gland");
				LABAST3SpecimenSource.put("110973009","Placenta, umbilical cord, and implantation site");
				LABAST3SpecimenSource.put("3120008","Pleural");
				LABAST3SpecimenSource.put("2778004","Pleural fluid");
				LABAST3SpecimenSource.put("110651000","Prostate and seminal vesicle");
				LABAST3SpecimenSource.put("11311000","Pus");
				LABAST3SpecimenSource.put("34402009","Rectum");
				LABAST3SpecimenSource.put("25990002","Renal pelvis");
				LABAST3SpecimenSource.put("256897009","Saliva");
				LABAST3SpecimenSource.put("385294005","Salivary gland");
				LABAST3SpecimenSource.put("PHC12","Skeletal system (bones of head, rib cage, and vertebral column)");
				LABAST3SpecimenSource.put("PHC11","Skeletal system (bones of shoulder, girdle, pelvis, and extremities)");
				LABAST3SpecimenSource.put("39937001","Skin and skin appendages");
				LABAST3SpecimenSource.put("38848004","Small intestine - duodenum");
				LABAST3SpecimenSource.put("110611003","Small intestine - jejunum and ileum");
				LABAST3SpecimenSource.put("PHC10","Soft tissue (muscles of head, neck, mouth, and upper extremity)");
				LABAST3SpecimenSource.put("PHC9","Soft tissue (muscles of trunk, perineum, and lower extremity)");
				LABAST3SpecimenSource.put("181607009","Soft tissue (not otherwise specified)");
				LABAST3SpecimenSource.put("2748008","Spinal cord");
				LABAST3SpecimenSource.put("78961009","Spleen");
				LABAST3SpecimenSource.put("119334006","Sputum");
				LABAST3SpecimenSource.put("69695003","Stomach");
				LABAST3SpecimenSource.put("71966008","Subcutaneous tissue");
				LABAST3SpecimenSource.put("6085005","Synovial fluid");
				LABAST3SpecimenSource.put("59863003","Tendon and tendon sheath");
				LABAST3SpecimenSource.put("40689003","Testis");
				LABAST3SpecimenSource.put("9875009","Thymus");
				LABAST3SpecimenSource.put("297261005","Thyroid or parathyroid gland(s)");
				LABAST3SpecimenSource.put("21974007","Tongue");
				LABAST3SpecimenSource.put("303337002","Tonsils and adenoids");
				LABAST3SpecimenSource.put("362102006","Tooth, gum, and supporting structures of the tooth");
				LABAST3SpecimenSource.put("44567001","Trachea");
				LABAST3SpecimenSource.put("UNK","Unknown");
				LABAST3SpecimenSource.put("72869002","Upper respiratory fluids");
				LABAST3SpecimenSource.put("87953007","Ureter");
				LABAST3SpecimenSource.put("13648007","Urethra");
				LABAST3SpecimenSource.put("89837001","Urinary bladder");
				LABAST3SpecimenSource.put("78014005","Urine");
				LABAST3SpecimenSource.put("35039007","Uterus");
				LABAST3SpecimenSource.put("76784001","Vagina");
				LABAST3SpecimenSource.put("110888000","Vulva, labia, clitoris, and Bartholin`s gland");

			}
			}
			public static void initializeLABAST7TestMethod(){
			if(LABAST7TestMethod.size()==0){
				LABAST7TestMethod.put("88629000","Agar dilution/MIC");
				LABAST7TestMethod.put("39334004","Broth culture/MIC");
				LABAST7TestMethod.put("104234003","E-Test/MIC");
				LABAST7TestMethod.put("359872008","Kirby-Bauer/Disk Diffusion");
				LABAST7TestMethod.put("OTH","Other (specify)");

			}
			}
			public static void initializeLAB684SpecimenSource(){
			if(LAB684SpecimenSource.size()==0){
	
				LAB684SpecimenSource.put("120228005","Accessory sinus");
				LAB684SpecimenSource.put("23451007","Adrenal gland");
				LAB684SpecimenSource.put("53505006","Anus");
				LAB684SpecimenSource.put("66754008","Appendix");
				LAB684SpecimenSource.put("C0541696","Bile and pancreatic fluid");
				LAB684SpecimenSource.put("87612001","Blood");
				LAB684SpecimenSource.put("59820001","Blood vessel");
				LAB684SpecimenSource.put("272673000","Bone");
				LAB684SpecimenSource.put("14016003","Bone marrow");
				LAB684SpecimenSource.put("12738006","Brain");
				LAB684SpecimenSource.put("76752008","Breast");
				LAB684SpecimenSource.put("258446004","Bronchial fluid");
				LAB684SpecimenSource.put("55214000","Bronchiole");
				LAB684SpecimenSource.put("955009","Bronchus");
				LAB684SpecimenSource.put("17401000","Cardiac valve");
				LAB684SpecimenSource.put("71252005","Cervix");
				LAB684SpecimenSource.put("71854001","Colon");
				LAB684SpecimenSource.put("25087005","Cranial, spinal, and peripheral nerve");
				LAB684SpecimenSource.put("65216001","CSF (cerebrospinal fluid)");
				LAB684SpecimenSource.put("110708006","Ear and Mastoid Cells");
				LAB684SpecimenSource.put("2739003","Endometrium");
				LAB684SpecimenSource.put("110887005","Epididymis, vas deferens, spermatic cord, and scrotum");
				LAB684SpecimenSource.put("110547006","Epiglottis and larynx");
				LAB684SpecimenSource.put("32849002","Esophagus");
				LAB684SpecimenSource.put("16014003","Extrahepatic bile duct");
				LAB684SpecimenSource.put("PHC4","Eye and ear appendages");
				LAB684SpecimenSource.put("110850002","Fallopian tube, broad ligament, parametrium, and paraovarian region");
				LAB684SpecimenSource.put("50473004","Female genital fluids");
				LAB684SpecimenSource.put("C0230999","Fetus and embryo");
				LAB684SpecimenSource.put("28231008","Gallbladder");
				LAB684SpecimenSource.put("168137004","Gastric aspirate");
				LAB684SpecimenSource.put("39477002","Gastrointestinal contents (feces)");
				LAB684SpecimenSource.put("80891009","Heart");
				LAB684SpecimenSource.put("88928006","Joints (synovial tissue)");
				LAB684SpecimenSource.put("64033007","Kidney");
				LAB684SpecimenSource.put("91684004","Ligament and fascia");
				LAB684SpecimenSource.put("48477009","Lip");
				LAB684SpecimenSource.put("10200004","Liver");
				LAB684SpecimenSource.put("39607008","Lung");
				LAB684SpecimenSource.put("59441001","Lymph node");
				LAB684SpecimenSource.put("23378005","Male genital fluids");
				LAB684SpecimenSource.put("PHC8","Meninges, dural sinus, choroid plexus");
				LAB684SpecimenSource.put("226789007","Milk");
				LAB684SpecimenSource.put("123851003","Mouth");
				LAB684SpecimenSource.put("PHC6","Multiple Sites");
				LAB684SpecimenSource.put("27232003","Myometrium");
				LAB684SpecimenSource.put("71836000","Nasopharynx");
				LAB684SpecimenSource.put("45206002","Nose");
				LAB684SpecimenSource.put("PHC7","Omentum and peritoneum");
				LAB684SpecimenSource.put("OTH","Other");
				LAB684SpecimenSource.put("15497006","Ovary");
				LAB684SpecimenSource.put("15776009","Pancreas");
				LAB684SpecimenSource.put("18911002","Penis");
				LAB684SpecimenSource.put("34429004","Pericardial fluid");
				LAB684SpecimenSource.put("76848001","Pericardium");
				LAB684SpecimenSource.put("409614007","Peritoneal fluid");
				LAB684SpecimenSource.put("54066008","Pharynx, oropharynx, and hypopharynx");
				LAB684SpecimenSource.put("56329008","Pituitary gland");
				LAB684SpecimenSource.put("110973009","Placenta, umbilical cord, and implantation site");
				LAB684SpecimenSource.put("3120008","Pleural");
				LAB684SpecimenSource.put("2778004","Pleural fluid");
				LAB684SpecimenSource.put("110651000","Prostate and seminal vesicle");
				LAB684SpecimenSource.put("11311000","Pus");
				LAB684SpecimenSource.put("34402009","Rectum");
				LAB684SpecimenSource.put("25990002","Renal pelvis");
				LAB684SpecimenSource.put("256897009","Saliva");
				LAB684SpecimenSource.put("385294005","Salivary gland");
				LAB684SpecimenSource.put("PHC12","Skeletal system (bones of head, rib cage, and vertebral column)");
				LAB684SpecimenSource.put("PHC11","Skeletal system (bones of shoulder, girdle, pelvis, and extremities)");
				LAB684SpecimenSource.put("39937001","Skin and skin appendages");
				LAB684SpecimenSource.put("38848004","Small intestine - duodenum");
				LAB684SpecimenSource.put("110611003","Small intestine - jejunum and ileum");
				LAB684SpecimenSource.put("PHC10","Soft tissue (muscles of head, neck, mouth, and upper extremity)");
				LAB684SpecimenSource.put("PHC9","Soft tissue (muscles of trunk, perineum, and lower extremity)");
				LAB684SpecimenSource.put("181607009","Soft tissue (not otherwise specified)");
				LAB684SpecimenSource.put("2748008","Spinal cord");
				LAB684SpecimenSource.put("78961009","Spleen");
				LAB684SpecimenSource.put("119334006","Sputum");
				LAB684SpecimenSource.put("69695003","Stomach");
				LAB684SpecimenSource.put("71966008","Subcutaneous tissue");
				LAB684SpecimenSource.put("6085005","Synovial fluid");
				LAB684SpecimenSource.put("59863003","Tendon and tendon sheath");
				LAB684SpecimenSource.put("40689003","Testis");
				LAB684SpecimenSource.put("9875009","Thymus");
				LAB684SpecimenSource.put("297261005","Thyroid or parathyroid gland(s)");
				LAB684SpecimenSource.put("21974007","Tongue");
				LAB684SpecimenSource.put("303337002","Tonsils and adenoids");
				LAB684SpecimenSource.put("362102006","Tooth, gum, and supporting structures of the tooth");
				LAB684SpecimenSource.put("44567001","Trachea");
				LAB684SpecimenSource.put("UNK","Unknown");
				LAB684SpecimenSource.put("72869002","Upper respiratory fluids");
				LAB684SpecimenSource.put("87953007","Ureter");
				LAB684SpecimenSource.put("13648007","Urethra");
				LAB684SpecimenSource.put("89837001","Urinary bladder");
				LAB684SpecimenSource.put("78014005","Urine");
				LAB684SpecimenSource.put("35039007","Uterus");
				LAB684SpecimenSource.put("76784001","Vagina");
				LAB684SpecimenSource.put("110888000","Vulva, labia, clitoris, and Bartholin`s gland");

			}
			}
	
			public static void initializecode48018_6Gene(){
			if(code48018_6Gene.size()==0){
				code48018_6Gene.put("PHC1892","ahpC-oxyR");
				code48018_6Gene.put("PHC2330","atpE");
				code48018_6Gene.put("PHC1898","eis");
				code48018_6Gene.put("PHC1896","embB");
				code48018_6Gene.put("PHC1902","ethA");
				code48018_6Gene.put("PHC1893","fabG1");
				code48018_6Gene.put("PHC1900","gyrA");
				code48018_6Gene.put("PHC1901","gyrB");
				code48018_6Gene.put("PHC1891","inhA");
				code48018_6Gene.put("PHC1890","katG");
				code48018_6Gene.put("OTH","Other (specify)");
				code48018_6Gene.put("PHC2328","pepQ");
				code48018_6Gene.put("PHC1895","pncA");
				code48018_6Gene.put("PHC2327","rplC");
				code48018_6Gene.put("PHC1894","rpoB");
				code48018_6Gene.put("PHC1903","rpsL");
				code48018_6Gene.put("PHC2329","rrl");
				code48018_6Gene.put("PHC1897","rrs");
				code48018_6Gene.put("PHC2326","Rv0678");
				code48018_6Gene.put("PHC1899","tlyA");
				
			}
			}
			public static void initializeINV1153OutOfState(){
			if(INV1153OutOfState.size()==0){
				
				INV1153OutOfState.put("01","Alabama");
				INV1153OutOfState.put("02","Alaska");
				INV1153OutOfState.put("04","Arizona");
				INV1153OutOfState.put("05","Arkansas");
				INV1153OutOfState.put("06","California");
				INV1153OutOfState.put("08","Colorado");
				INV1153OutOfState.put("09","Connecticut");
				INV1153OutOfState.put("10","Delaware");
				INV1153OutOfState.put("11","District of Columbia");
				INV1153OutOfState.put("12","Florida");
				INV1153OutOfState.put("13","Georgia");
				INV1153OutOfState.put("15","Hawaii");
				INV1153OutOfState.put("16","Idaho");
				INV1153OutOfState.put("17","Illinois");
				INV1153OutOfState.put("18","Indiana");
				INV1153OutOfState.put("19","Iowa");
				INV1153OutOfState.put("20","Kansas");
				INV1153OutOfState.put("21","Kentucky");
				INV1153OutOfState.put("22","Louisiana");
				INV1153OutOfState.put("23","Maine");
				INV1153OutOfState.put("24","Maryland");
				INV1153OutOfState.put("25","Massachusetts");
				INV1153OutOfState.put("26","Michigan");
				INV1153OutOfState.put("27","Minnesota");
				INV1153OutOfState.put("28","Mississippi");
				INV1153OutOfState.put("29","Missouri");
				INV1153OutOfState.put("30","Montana");
				INV1153OutOfState.put("31","Nebraska");
				INV1153OutOfState.put("32","Nevada");
				INV1153OutOfState.put("33","New Hampshire");
				INV1153OutOfState.put("34","New Jersey");
				INV1153OutOfState.put("35","New Mexico");
				INV1153OutOfState.put("36","New York");
				INV1153OutOfState.put("37","North Carolina");
				INV1153OutOfState.put("38","North Dakota");
				INV1153OutOfState.put("39","Ohio");
				INV1153OutOfState.put("40","Oklahoma");
				INV1153OutOfState.put("41","Oregon");
				INV1153OutOfState.put("42","Pennsylvania");
				INV1153OutOfState.put("44","Rhode Island");
				INV1153OutOfState.put("45","South Carolina");
				INV1153OutOfState.put("46","South Dakota");
				INV1153OutOfState.put("47","Tennessee");
				INV1153OutOfState.put("48","Texas");
				INV1153OutOfState.put("49","Utah");
				INV1153OutOfState.put("50","Vermont");
				INV1153OutOfState.put("51","Virginia");
				INV1153OutOfState.put("53","Washington");
				INV1153OutOfState.put("54","West Virginia");
				INV1153OutOfState.put("55","Wisconsin");
				INV1153OutOfState.put("56","Wyoming");
				INV1153OutOfState.put("60","American Samoa");
				INV1153OutOfState.put("64","Federated States of Micro");
				INV1153OutOfState.put("66","Guam");
				INV1153OutOfState.put("68","Marshall Islands");
				INV1153OutOfState.put("69","Northern Mariana Islands");
				INV1153OutOfState.put("70","Palau");
				INV1153OutOfState.put("72","Puerto Rico");
				INV1153OutOfState.put("78","US Virgin Islands");

			
			}
			}
			public static void initializeINV1154OutOfCountry(){
			if(INV1154OutOfCountry.size()==0){
				
				INV1154OutOfCountry.put("4","AFGHANISTAN");
				INV1154OutOfCountry.put("ALA","ALAND ISLANDS");
				INV1154OutOfCountry.put("8","ALBANIA");
				INV1154OutOfCountry.put("12","ALGERIA");
				INV1154OutOfCountry.put("16","AMERICAN SAMOA");
				INV1154OutOfCountry.put("20","ANDORRA");
				INV1154OutOfCountry.put("24","ANGOLA");
				INV1154OutOfCountry.put("660","ANGUILLA");
				INV1154OutOfCountry.put("ATA","ANTARCTICA");
				INV1154OutOfCountry.put("28","ANTIGUA AND BARBUDA");
				INV1154OutOfCountry.put("32","ARGENTINA");
				INV1154OutOfCountry.put("51","ARMENIA");
				INV1154OutOfCountry.put("533","ARUBA");
				INV1154OutOfCountry.put("36","AUSTRALIA");
				INV1154OutOfCountry.put("40","AUSTRIA");
				INV1154OutOfCountry.put("31","AZERBAIJAN");
				INV1154OutOfCountry.put("44","BAHAMAS, THE");
				INV1154OutOfCountry.put("48","BAHRAIN");
				INV1154OutOfCountry.put("50","BANGLADESH");
				INV1154OutOfCountry.put("52","BARBADOS");
				INV1154OutOfCountry.put("112","BELARUS");
				INV1154OutOfCountry.put("56","BELGIUM");
				INV1154OutOfCountry.put("84","BELIZE");
				INV1154OutOfCountry.put("204","BENIN");
				INV1154OutOfCountry.put("60","BERMUDA");
				INV1154OutOfCountry.put("64","BHUTAN");
				INV1154OutOfCountry.put("68","BOLIVIA");
				INV1154OutOfCountry.put("70","BOSNIA AND HERZEGOVINA");
				INV1154OutOfCountry.put("72","BOTSWANA");
				INV1154OutOfCountry.put("BVT","BOUVET ISLAND");
				INV1154OutOfCountry.put("76","BRAZIL");
				INV1154OutOfCountry.put("IOT","BRITISH INDIAN OCEAN TERRITORY");
				INV1154OutOfCountry.put("92","BRITISH VIRGIN ISLANDS");
				INV1154OutOfCountry.put("96","BRUNEI");
				INV1154OutOfCountry.put("100","BULGARIA");
				INV1154OutOfCountry.put("854","BURKINA (UPPER VOLTA)");
				INV1154OutOfCountry.put("108","BURUNDI");
				INV1154OutOfCountry.put("116","CAMBODIA");
				INV1154OutOfCountry.put("120","CAMEROON");
				INV1154OutOfCountry.put("124","CANADA");
				INV1154OutOfCountry.put("132","CAPE VERDE");
				INV1154OutOfCountry.put("136","CAYMAN ISLANDS");
				INV1154OutOfCountry.put("140","CENTRAL AFRICAN REPUBLIC");
				INV1154OutOfCountry.put("148","CHAD");
				INV1154OutOfCountry.put("152","CHILE");
				INV1154OutOfCountry.put("156","CHINA");
				INV1154OutOfCountry.put("CXR","CHRISTMAS ISLAND");
				INV1154OutOfCountry.put("CCK","COCOS (KEELING) ISLANDS");
				INV1154OutOfCountry.put("170","COLOMBIA");
				INV1154OutOfCountry.put("174","COMOROS");
				INV1154OutOfCountry.put("178","CONGO");
				INV1154OutOfCountry.put("184","COOK ISLANDS");
				INV1154OutOfCountry.put("188","COSTA RICA");
				INV1154OutOfCountry.put("191","CROATIA");
				INV1154OutOfCountry.put("192","CUBA");
				INV1154OutOfCountry.put("196","CYPRUS");
				INV1154OutOfCountry.put("203","CZECH REPUBLIC");
				INV1154OutOfCountry.put("180","DEMOCRATIC REPUBLIC OF THE CONGO");
				INV1154OutOfCountry.put("208","DENMARK");
				INV1154OutOfCountry.put("262","DJIBOUTI");
				INV1154OutOfCountry.put("212","DOMINICA");
				INV1154OutOfCountry.put("214","DOMINICAN REPUBLIC");
				INV1154OutOfCountry.put("218","ECUADOR");
				INV1154OutOfCountry.put("818","EGYPT");
				INV1154OutOfCountry.put("222","EL SALVADOR");
				INV1154OutOfCountry.put("226","EQUATORIAL GUINEA");
				INV1154OutOfCountry.put("232","ERITREA");
				INV1154OutOfCountry.put("233","ESTONIA");
				INV1154OutOfCountry.put("231","ETHIOPIA");
				INV1154OutOfCountry.put("238","FALKLAND ISLANDS (MALVINAS)");
				INV1154OutOfCountry.put("234","FAROE ISLANDS");
				INV1154OutOfCountry.put("242","FIJI");
				INV1154OutOfCountry.put("246","FINLAND");
				INV1154OutOfCountry.put("250","FRANCE");
				INV1154OutOfCountry.put("254","FRENCH GUIANA");
				INV1154OutOfCountry.put("258","FRENCH POLYNESIA");
				INV1154OutOfCountry.put("ATF","FRENCH SOUTHERN TERRITORIES");
				INV1154OutOfCountry.put("266","GABON");
				INV1154OutOfCountry.put("270","GAMBIA, THE");
				INV1154OutOfCountry.put("268","GEORGIA");
				INV1154OutOfCountry.put("276","GERMANY");
				INV1154OutOfCountry.put("288","GHANA");
				INV1154OutOfCountry.put("292","GIBRALTAR");
				INV1154OutOfCountry.put("300","GREECE");
				INV1154OutOfCountry.put("304","GREENLAND");
				INV1154OutOfCountry.put("308","GRENADA");
				INV1154OutOfCountry.put("312","GUADELOUPE");
				INV1154OutOfCountry.put("316","GUAM");
				INV1154OutOfCountry.put("320","GUATEMALA");
				INV1154OutOfCountry.put("GGY","GUERNSEY");
				INV1154OutOfCountry.put("324","GUINEA");
				INV1154OutOfCountry.put("624","GUINEA-BISSAU");
				INV1154OutOfCountry.put("328","GUYANA");
				INV1154OutOfCountry.put("332","HAITI");
				INV1154OutOfCountry.put("HMD","HEARD ISLAND AND MCDONALD ISLANDS");
				INV1154OutOfCountry.put("336","HOLY SEE (VATICAN CITY STATE)");
				INV1154OutOfCountry.put("340","HONDURAS");
				INV1154OutOfCountry.put("344","HONG KONG");
				INV1154OutOfCountry.put("348","HUNGARY");
				INV1154OutOfCountry.put("352","ICELAND");
				INV1154OutOfCountry.put("356","INDIA");
				INV1154OutOfCountry.put("360","INDONESIA");
				INV1154OutOfCountry.put("364","IRAN");
				INV1154OutOfCountry.put("368","IRAQ");
				INV1154OutOfCountry.put("372","IRELAND");
				INV1154OutOfCountry.put("833","ISLE OF MAN");
				INV1154OutOfCountry.put("376","ISRAEL");
				INV1154OutOfCountry.put("380","ITALY");
				INV1154OutOfCountry.put("CIV","IVORY COAST");
				INV1154OutOfCountry.put("388","JAMAICA");
				INV1154OutOfCountry.put("392","JAPAN");
				INV1154OutOfCountry.put("JEY","JERSEY");
				INV1154OutOfCountry.put("400","JORDAN");
				INV1154OutOfCountry.put("398","KAZAKHSTAN");
				INV1154OutOfCountry.put("404","KENYA");
				INV1154OutOfCountry.put("296","KIRIBATI");
				INV1154OutOfCountry.put("PRK","KOREA, DEMOCRATIC PEOPLES REPUBLIC OF");
				INV1154OutOfCountry.put("410","KOREA, REPUBLIC OF");
				INV1154OutOfCountry.put("414","KUWAIT");
				INV1154OutOfCountry.put("417","KYRGYZSTAN");
				INV1154OutOfCountry.put("LAO","LAOS");
				INV1154OutOfCountry.put("428","LATVIA");
				INV1154OutOfCountry.put("422","LEBANON");
				INV1154OutOfCountry.put("426","LESOTHO");
				INV1154OutOfCountry.put("430","LIBERIA");
				INV1154OutOfCountry.put("434","LIBYA");
				INV1154OutOfCountry.put("438","LIECHTENSTEIN");
				INV1154OutOfCountry.put("440","LITHUANIA");
				INV1154OutOfCountry.put("442","LUXEMBOURG");
				INV1154OutOfCountry.put("446","MACAU");
				INV1154OutOfCountry.put("807","MACEDONIA");
				INV1154OutOfCountry.put("450","MADAGASCAR");
				INV1154OutOfCountry.put("454","MALAWI");
				INV1154OutOfCountry.put("458","MALAYSIA");
				INV1154OutOfCountry.put("462","MALDIVES");
				INV1154OutOfCountry.put("466","MALI");
				INV1154OutOfCountry.put("470","MALTA");
				INV1154OutOfCountry.put("584","MARSHALL ISLANDS");
				INV1154OutOfCountry.put("474","MARTINIQUE");
				INV1154OutOfCountry.put("478","MAURITANIA");
				INV1154OutOfCountry.put("480","MAURITIUS");
				INV1154OutOfCountry.put("MYT","MAYOTTE");
				INV1154OutOfCountry.put("484","MEXICO");
				INV1154OutOfCountry.put("583","MICRONESIA, FEDERATED STATES OF");
				INV1154OutOfCountry.put("498","MOLDOVA");
				INV1154OutOfCountry.put("492","MONACO");
				INV1154OutOfCountry.put("496","MONGOLIA");
				INV1154OutOfCountry.put("MNE","MONTENEGRO");
				INV1154OutOfCountry.put("500","MONTSERRAT");
				INV1154OutOfCountry.put("504","MOROCCO");
				INV1154OutOfCountry.put("508","MOZAMBIQUE");
				INV1154OutOfCountry.put("104","MYANMAR");
				INV1154OutOfCountry.put("516","NAMIBIA");
				INV1154OutOfCountry.put("520","NAURU");
				INV1154OutOfCountry.put("524","NEPAL");
				INV1154OutOfCountry.put("528","NETHERLANDS");
				INV1154OutOfCountry.put("530","NETHERLANDS ANTILLES");
				INV1154OutOfCountry.put("540","NEW CALEDONIA");
				INV1154OutOfCountry.put("554","NEW ZEALAND");
				INV1154OutOfCountry.put("558","NICARAGUA");
				INV1154OutOfCountry.put("562","NIGER");
				INV1154OutOfCountry.put("566","NIGERIA");
				INV1154OutOfCountry.put("570","NIUE");
				INV1154OutOfCountry.put("574","NORFOLK ISLAND");
				INV1154OutOfCountry.put("580","NORTHERN MARIANA ISLANDS");
				INV1154OutOfCountry.put("578","NORWAY");
				INV1154OutOfCountry.put("512","OMAN");
				INV1154OutOfCountry.put("586","PAKISTAN");
				INV1154OutOfCountry.put("585","PALAU");
				INV1154OutOfCountry.put("275","PALESTINIAN TERRITORY, OCCUPIED");
				INV1154OutOfCountry.put("591","PANAMA");
				INV1154OutOfCountry.put("598","PAPUA NEW GUINEA");
				INV1154OutOfCountry.put("600","PARAGUAY");
				INV1154OutOfCountry.put("604","PERU");
				INV1154OutOfCountry.put("608","PHILIPPINES");
				INV1154OutOfCountry.put("612","PITCAIRN ISLANDS");
				INV1154OutOfCountry.put("616","POLAND");
				INV1154OutOfCountry.put("620","PORTUGAL");
				INV1154OutOfCountry.put("630","PUERTO RICO");
				INV1154OutOfCountry.put("634","QATAR");
				INV1154OutOfCountry.put("REU","REUNION");
				INV1154OutOfCountry.put("642","ROMANIA");
				INV1154OutOfCountry.put("643","RUSSIA");
				INV1154OutOfCountry.put("646","RWANDA");
				INV1154OutOfCountry.put("SGS","S.GEORGIA/S.SANDWICH ISLANDS");
				INV1154OutOfCountry.put("674","SAN MARINO");
				INV1154OutOfCountry.put("678","SAO TOME AND PRINCIPE");
				INV1154OutOfCountry.put("682","SAUDI ARABIA");
				INV1154OutOfCountry.put("686","SENEGAL");
				INV1154OutOfCountry.put("SRB","SERBIA");
				INV1154OutOfCountry.put("690","SEYCHELLES");
				INV1154OutOfCountry.put("694","SIERRA LEONE");
				INV1154OutOfCountry.put("702","SINGAPORE");
				INV1154OutOfCountry.put("703","SLOVAKIA (SLOVAK REPUBLIC)");
				INV1154OutOfCountry.put("705","SLOVENIA");
				INV1154OutOfCountry.put("90","SOLOMON ISLANDS");
				INV1154OutOfCountry.put("706","SOMALIA");
				INV1154OutOfCountry.put("710","SOUTH AFRICA");
				INV1154OutOfCountry.put("724","SPAIN");
				INV1154OutOfCountry.put("144","SRI LANKA");
				INV1154OutOfCountry.put("BLM","ST. BARTHELEMY");
				INV1154OutOfCountry.put("654","ST. HELENA");
				INV1154OutOfCountry.put("659","ST. KITTS AND NEVIS");
				INV1154OutOfCountry.put("662","ST. LUCIA");
				INV1154OutOfCountry.put("MAF","ST. MARTIN (FRENCH PART)");
				INV1154OutOfCountry.put("666","ST. PIERRE AND MIQUELON");
				INV1154OutOfCountry.put("670","ST. VINCENT/GRENADINES");
				INV1154OutOfCountry.put("736","SUDAN");
				INV1154OutOfCountry.put("740","SURINAME");
				INV1154OutOfCountry.put("744","SVALBARD AND JAN MAYEN ISLANDS");
				INV1154OutOfCountry.put("748","SWAZILAND");
				INV1154OutOfCountry.put("752","SWEDEN");
				INV1154OutOfCountry.put("756","SWITZERLAND");
				INV1154OutOfCountry.put("760","SYRIA (SYRIAN ARAB REPUBLIC)");
				INV1154OutOfCountry.put("158","TAIWAN");
				INV1154OutOfCountry.put("762","TAJIKISTAN");
				INV1154OutOfCountry.put("834","TANZANIA, UNITED REPUBLIC OF");
				INV1154OutOfCountry.put("764","THAILAND");
				INV1154OutOfCountry.put("626","TIMOR-LESTE (EAST TIMOR)");
				INV1154OutOfCountry.put("768","TOGO");
				INV1154OutOfCountry.put("772","TOKELAU");
				INV1154OutOfCountry.put("776","TONGA");
				INV1154OutOfCountry.put("780","TRINIDAD AND TOBAGO");
				INV1154OutOfCountry.put("788","TUNISIA");
				INV1154OutOfCountry.put("792","TURKEY");
				INV1154OutOfCountry.put("795","TURKMENISTAN");
				INV1154OutOfCountry.put("796","TURKS AND CAICOS ISLANDS");
				INV1154OutOfCountry.put("798","TUVALU");
				INV1154OutOfCountry.put("UMI","U.S. MINOR OUTLYING ISLANDS");
				INV1154OutOfCountry.put("850","U.S. VIRGIN ISLANDS");
				INV1154OutOfCountry.put("800","UGANDA");
				INV1154OutOfCountry.put("804","UKRAINE");
				INV1154OutOfCountry.put("784","UNITED ARAB EMIRATES (UAE)");
				INV1154OutOfCountry.put("826","UNITED KINGDOM");
				INV1154OutOfCountry.put("840","UNITED STATES");
				INV1154OutOfCountry.put("858","URUGUAY");
				INV1154OutOfCountry.put("860","UZBEKISTAN");
				INV1154OutOfCountry.put("548","VANUATU (NEW HEBRIDES)");
				INV1154OutOfCountry.put("862","VENEZUELA");
				INV1154OutOfCountry.put("704","VIETNAM");
				INV1154OutOfCountry.put("876","WALLIS AND FUTUNA ISLANDS");
				INV1154OutOfCountry.put("732","WESTERN SAHARA");
				INV1154OutOfCountry.put("882","WESTERN SAMOA");
				INV1154OutOfCountry.put("887","YEMEN");
				INV1154OutOfCountry.put("894","ZAMBIA");
				INV1154OutOfCountry.put("716","ZIMBABWE");

				
			}
			}
			public static void initializeINV1158Drug(){
			if(INV1158Drug.size()==0){
				INV1158Drug.put("641","Amikacin");
				INV1158Drug.put("1364504","Bedaquiline");
				INV1158Drug.put("78903","Capreomycin");
				INV1158Drug.put("2551","Ciprofloxacin");
				INV1158Drug.put("2592","Clofazimine");
				INV1158Drug.put("3007","Cycloserine");
				INV1158Drug.put("PHC1889","Delamanid");
				INV1158Drug.put("4110","Ethambutol");
				INV1158Drug.put("4127","Ethionamide");
				INV1158Drug.put("6038","Isoniazid");
				INV1158Drug.put("6099","Kanamycin");
				INV1158Drug.put("82122","Levofloxacin");
				INV1158Drug.put("190376","Linezolid");
				INV1158Drug.put("139462","Moxifloxacin");
				INV1158Drug.put("7623","Ofloxacin");
				INV1158Drug.put("OTH","Other (specify)");
				INV1158Drug.put("PHC1888","Other Quinolones");
				INV1158Drug.put("7833","Para-Aminesalicylicacid");
				INV1158Drug.put("8987","Pyrazinamide");
				INV1158Drug.put("55672","Rifabutin");
				INV1158Drug.put("9384","Rifampin");
				INV1158Drug.put("35617","Rifapentine");
				INV1158Drug.put("10109","Streptomycin");
				INV1158Drug.put("2198359","Pretomanid");
				

				
			}
			}
			public static void initializeCode42563_7_CDSideEffect(){
			if(code42563_7_CDSideEffect.size()==0){
				
				code42563_7_CDSideEffect.put("57676002","Arthralgia");
				code42563_7_CDSideEffect.put("36358004","Cardiac Abnormalities");
				code42563_7_CDSideEffect.put("35489007","Depression");
				code42563_7_CDSideEffect.put("15188001","Hearing Loss");
				code42563_7_CDSideEffect.put("197354009","Liver Toxicity");
				code42563_7_CDSideEffect.put("68962001","Myalgia");
				code42563_7_CDSideEffect.put("OTH","Other (specify)");
				code42563_7_CDSideEffect.put("302226006","Peripheral Neuropathy");
				code42563_7_CDSideEffect.put("236423003","Renal Dysfunction");
				code42563_7_CDSideEffect.put("82313006","Suicide Attempt or Ideation");
				code42563_7_CDSideEffect.put("60862001","Tinnitus");
				code42563_7_CDSideEffect.put("445053006","Vestibular Dysfunction");
				code42563_7_CDSideEffect.put("PHC1920","Vision Change/Loss");


			}
			}
			
			public static void initializeCode42563_7sideEffects(){
			if(code42563_7sideEffects.size()==0){
				//reading from the map to make sure the names are consistent
				code42563_7sideEffects.put(code42563_7_CDSideEffect.get("15188001"), "1");//Hearing loss
				code42563_7sideEffects.put(code42563_7_CDSideEffect.get("197354009"), "2");//Liver Toxicity
				code42563_7sideEffects.put(code42563_7_CDSideEffect.get("236423003"), "3");//Renal Dysfunction
				code42563_7sideEffects.put(code42563_7_CDSideEffect.get("302226006"), "4");//Peripheral Neuropathy
				code42563_7sideEffects.put(code42563_7_CDSideEffect.get("35489007"), "5");//Depression		
				code42563_7sideEffects.put(code42563_7_CDSideEffect.get("36358004"), "6");//Cardiac Abnormalities		
				code42563_7sideEffects.put(code42563_7_CDSideEffect.get("445053006"),"7");	//Vestibular Dysfunction	
				code42563_7sideEffects.put(code42563_7_CDSideEffect.get("60862001"), "8");	//Tinnitus	
				code42563_7sideEffects.put(code42563_7_CDSideEffect.get("68962001"), "9");		//Myalgia
				code42563_7sideEffects.put(code42563_7_CDSideEffect.get("82313006"), "10");//Suicide Attempt or Ideation		
				code42563_7sideEffects.put(code42563_7_CDSideEffect.get("PHC1920"), "11");//Vision Change/Loss		
			//	code42563_7sideEffects.put(code42563_7_CDSideEffect.get("OTH"), "12");//Other (Specify)	
			}
			
			}
		
	
	
	public static void initializeDemographics(){
		
		if(PregnancyMap.size()==0){
			PregnancyMap.put("Y", "Y");//Female  
			PregnancyMap.put("N", "N");//Male  
			PregnancyMap.put("UNK", "UNK");//Unknown  
		}
		if(BirthSexMap.size()==0){
			BirthSexMap.put("F", "F");//Female  
			BirthSexMap.put("M", "M");//Male  
			BirthSexMap.put("U", "D");//Unknown  
		}
		if(INV178YnuMap.size()==0){
			INV178YnuMap.put("Y", "Y");//Female  
			INV178YnuMap.put("N", "N");//Male  
			INV178YnuMap.put("UNK", "UNK");//Unknown  
		}
		if(drugHistory.size()==0)
		{
			drugHistory.put("ASKU", "U");//Asked but unknown
			drugHistory.put("D", "D");//Did not asked
			drugHistory.put("N", "N");//No
			drugHistory.put("R", "R");//Refused to answer
			drugHistory.put("Y", "Y");//Yes
		}
		if(CurrentSexMap.size()==0){
			CurrentSexMap.put("F", "F");//Female  
			CurrentSexMap.put("M", "M");//Male  
			CurrentSexMap.put("U", "U");//Unknown  
		}
		if(PreferredSexMap.size()==0){
			PreferredSexMap.put("MtF", "MtF");//Male to Female  
			PreferredSexMap.put("T", "T");//Trans 
			PreferredSexMap.put("FtM", "FtM");//Female to Male  
		}
		if(SexUnknownReasonMap.size()==0){
			SexUnknownReasonMap.put("R", "R");//refused to ans
			SexUnknownReasonMap.put("D", "D");//did not ask
		}
		if(DEM140PMaritalMap.size()==0){
			//DEM140PMaritalMap.put("A", " ");//Annulled @TODO change back x to " " 
			DEM140PMaritalMap.put("D", "D");//Divorced  
			//DEM140PMaritalMap.put("I", " ");//Interlocutory  @TODO change back x to " " 
			DEM140PMaritalMap.put("L", "L");//Legally separated  
			DEM140PMaritalMap.put("M", "M");//Married  
			//DEM140PMaritalMap.put("P", " ");//Polygamous   @TODO change back x to " " 
			DEM140PMaritalMap.put("R", "R");//Refused to answer  
			DEM140PMaritalMap.put("S", "S");//"Single, never married"  
			DEM140PMaritalMap.put("T", "T");//Domestic partner  
			DEM140PMaritalMap.put("U", "U");//Unknown  
			DEM140PMaritalMap.put("W", "W");//Widowed  
		}
		if(DEM152RaceCodePRaceCatMap.size()==0){
			DEM152RaceCodePRaceCatMap.put("1002-5", "AI/AN");//American Indian or Alaska Native  
			DEM152RaceCodePRaceCatMap.put("2028-9", "A");//Asian  
			DEM152RaceCodePRaceCatMap.put("2054-5", "B");//Black or African American  
			DEM152RaceCodePRaceCatMap.put("2076-8", "NH/PI");//Native Hawaiian or Other Pacific Islander  
			DEM152RaceCodePRaceCatMap.put("2106-3", "W");//White  
			DEM152RaceCodePRaceCatMap.put("2131-1", "O");//Other Race  
			DEM152RaceCodePRaceCatMap.put("NASK", "NA");//not asked  
			DEM152RaceCodePRaceCatMap.put("PHC1175", "R");//Refused to answer  
			DEM152RaceCodePRaceCatMap.put("U", "U");//Unknown  
		}
		if(DetailedRaceCodePRaceCatMapAmericanIndian.size()==0){
					
			DetailedRaceCodePRaceCatMapAmericanIndian.put("1735-0", "Alaska Native");//Alaska Native 
			DetailedRaceCodePRaceCatMapAmericanIndian.put("1004-1", "American Indian");//American Indian
					
		}
		if(DetailedRaceCodePRaceCatMapAsian.size()==0){
			
			DetailedRaceCodePRaceCatMapAsian.put("2029-7", "Asian Indian");
			DetailedRaceCodePRaceCatMapAsian.put("2030-5", "Bangladeshi");
			DetailedRaceCodePRaceCatMapAsian.put("2031-3", "Bhutanese");
			DetailedRaceCodePRaceCatMapAsian.put("2032-1", "Burmese");
			DetailedRaceCodePRaceCatMapAsian.put("2033-9", "Cambodian");
			DetailedRaceCodePRaceCatMapAsian.put("2034-7", "Chinese");
			DetailedRaceCodePRaceCatMapAsian.put("2036-2", "Filipino");
			DetailedRaceCodePRaceCatMapAsian.put("2037-0", "Hmong");
			DetailedRaceCodePRaceCatMapAsian.put("2038-8", "Indonesian");
			DetailedRaceCodePRaceCatMapAsian.put("2048-7", "Iwo Jiman");
			DetailedRaceCodePRaceCatMapAsian.put("2039-6", "Japanese");
			DetailedRaceCodePRaceCatMapAsian.put("2040-4", "Korean");
			DetailedRaceCodePRaceCatMapAsian.put("2041-2", "Laotian");
			DetailedRaceCodePRaceCatMapAsian.put("2052-9", "Madagascar");
			DetailedRaceCodePRaceCatMapAsian.put("2042-0", "Malaysian");
			DetailedRaceCodePRaceCatMapAsian.put("2049-5", "Maldivian");
			DetailedRaceCodePRaceCatMapAsian.put("2050-3", "Nepalese");
			DetailedRaceCodePRaceCatMapAsian.put("2043-8", "Okinawan");
			DetailedRaceCodePRaceCatMapAsian.put("2044-6", "Pakistani");
			DetailedRaceCodePRaceCatMapAsian.put("2051-1", "Singaporean");
			DetailedRaceCodePRaceCatMapAsian.put("2045-3", "Sri Lankan");
			DetailedRaceCodePRaceCatMapAsian.put("2035-4", "Taiwanese");
			DetailedRaceCodePRaceCatMapAsian.put("2046-1", "Thai");
			DetailedRaceCodePRaceCatMapAsian.put("2047-9", "Vietnamese");
		
			
		}
		if(DetailedRaceCodePRaceCatMapAfricanAmerican.size()==0){
			
			DetailedRaceCodePRaceCatMapAfricanAmerican.put("2060-2", "African");
			DetailedRaceCodePRaceCatMapAfricanAmerican.put("2058-6", "African American");
			DetailedRaceCodePRaceCatMapAfricanAmerican.put("2067-7", "Bahamian");
			DetailedRaceCodePRaceCatMapAfricanAmerican.put("2068-5", "Barbadian");
			DetailedRaceCodePRaceCatMapAfricanAmerican.put("2056-0", "Black");
			DetailedRaceCodePRaceCatMapAfricanAmerican.put("2070-1", "Dominica Islander");
			DetailedRaceCodePRaceCatMapAfricanAmerican.put("2069-3", "Dominican");
			DetailedRaceCodePRaceCatMapAfricanAmerican.put("2071-9", "Haitian");
			DetailedRaceCodePRaceCatMapAfricanAmerican.put("2072-7", "Jamaican");
			DetailedRaceCodePRaceCatMapAfricanAmerican.put("2073-5", "Tobagoan");
			DetailedRaceCodePRaceCatMapAfricanAmerican.put("2074-3", "Trinidadian");
			DetailedRaceCodePRaceCatMapAfricanAmerican.put("2075-0", "West Indian");
			
		
		}
		
		
		//native Hawaiian
		
		if(DetailedRaceCodePRaceCatMapNativeHawaiian.size()==0){
			
			DetailedRaceCodePRaceCatMapNativeHawaiian.put("2100-6", "Melanesian");
			DetailedRaceCodePRaceCatMapNativeHawaiian.put("2085-9", "Micronesian");
			DetailedRaceCodePRaceCatMapNativeHawaiian.put("2500-7", "Other Pacific Islander");
			DetailedRaceCodePRaceCatMapNativeHawaiian.put("2078-4", "Polynesian");
			
					
		}
		
		//White
		
		if(DetailedRaceCodePRaceCatMapWhite.size()==0){
			
			DetailedRaceCodePRaceCatMapWhite.put("2129-5", "Arab");
			DetailedRaceCodePRaceCatMapWhite.put("2108-9", "European");
			DetailedRaceCodePRaceCatMapWhite.put("2118-8", "Middle Eastern or North African");
		}

		
	}
	
	
	public static void initializeCaseStatus(){
		if(INV163caseStatusMap.size()==0){
			INV163caseStatusMap.put("C", "Confirmed");
			INV163caseStatusMap.put("N", "Not a Case");
			INV163caseStatusMap.put("P", "Probable");
			INV163caseStatusMap.put("S", "Suspect");
			INV163caseStatusMap.put("U", "Unknown");
		}
	}
	
	public static void initializeCaseVerification(){
		if(INV1115CaseVerificationMap.size()==0){
			INV1115CaseVerificationMap.put("415684004", "5 - Suspected");
			INV1115CaseVerificationMap.put("PHC162", "0 - Not a Verified Case");
			INV1115CaseVerificationMap.put("PHC165", "4 - Verified by Provider Diagnosis");
			INV1115CaseVerificationMap.put("PHC653", "1A - Positive NAA");
			INV1115CaseVerificationMap.put("PHC654", "3 - Clinical Case Definition");
			INV1115CaseVerificationMap.put("PHC97", "1 - Positive Culture");
			INV1115CaseVerificationMap.put("PHC98", "2 - Positive Smear/Tissue"); 
		}
	}
	
	public static void initializeCaseCounted(){
		if(INV1109CaseCountedMap.size()==0){
			INV1109CaseCountedMap.put("N", "N");
			INV1109CaseCountedMap.put("PHC659", "PHC659");
			INV1109CaseCountedMap.put("PHC660", "PHC660");
		}
	
	}
	
	public static void initializeCountryOfVerifiedCase(){
		

		if(INV1111CountryOfVerifiedCase.size()==0){
			INV1111CountryOfVerifiedCase.put("4","AFGHANISTAN");
			INV1111CountryOfVerifiedCase.put("ALA","ALAND ISLANDS");
			INV1111CountryOfVerifiedCase.put("8","ALBANIA");
			INV1111CountryOfVerifiedCase.put("12","ALGERIA");
			INV1111CountryOfVerifiedCase.put("16","AMERICAN SAMOA");
			INV1111CountryOfVerifiedCase.put("20","ANDORRA");
			INV1111CountryOfVerifiedCase.put("24","ANGOLA");
			INV1111CountryOfVerifiedCase.put("660","ANGUILLA");
			INV1111CountryOfVerifiedCase.put("ATA","ANTARCTICA");
			INV1111CountryOfVerifiedCase.put("28","ANTIGUA AND BARBUDA");
			INV1111CountryOfVerifiedCase.put("32","ARGENTINA");
			INV1111CountryOfVerifiedCase.put("51","ARMENIA");
			INV1111CountryOfVerifiedCase.put("533","ARUBA");
			INV1111CountryOfVerifiedCase.put("36","AUSTRALIA");
			INV1111CountryOfVerifiedCase.put("40","AUSTRIA");
			INV1111CountryOfVerifiedCase.put("31","AZERBAIJAN");
			INV1111CountryOfVerifiedCase.put("44","BAHAMAS, THE");
			INV1111CountryOfVerifiedCase.put("48","BAHRAIN");
			INV1111CountryOfVerifiedCase.put("50","BANGLADESH");
			INV1111CountryOfVerifiedCase.put("52","BARBADOS");
			INV1111CountryOfVerifiedCase.put("112","BELARUS");
			INV1111CountryOfVerifiedCase.put("56","BELGIUM");
			INV1111CountryOfVerifiedCase.put("84","BELIZE");
			INV1111CountryOfVerifiedCase.put("204","BENIN");
			INV1111CountryOfVerifiedCase.put("60","BERMUDA");
			INV1111CountryOfVerifiedCase.put("64","BHUTAN");
			INV1111CountryOfVerifiedCase.put("68","BOLIVIA");
			INV1111CountryOfVerifiedCase.put("70","BOSNIA AND HERZEGOVINA");
			INV1111CountryOfVerifiedCase.put("72","BOTSWANA");
			INV1111CountryOfVerifiedCase.put("BVT","BOUVET ISLAND");
			INV1111CountryOfVerifiedCase.put("76","BRAZIL");
			INV1111CountryOfVerifiedCase.put("IOT","BRITISH INDIAN OCEAN TERRITORY");
			INV1111CountryOfVerifiedCase.put("VGB","BRITISH VIRGIN ISLANDS");
			INV1111CountryOfVerifiedCase.put("96","BRUNEI");
			INV1111CountryOfVerifiedCase.put("100","BULGARIA");
			INV1111CountryOfVerifiedCase.put("854","BURKINA (UPPER VOLTA)");
			INV1111CountryOfVerifiedCase.put("BUMM","BURMA");
			INV1111CountryOfVerifiedCase.put("108","BURUNDI");
			INV1111CountryOfVerifiedCase.put("116","CAMBODIA");
			INV1111CountryOfVerifiedCase.put("120","CAMEROON");
			INV1111CountryOfVerifiedCase.put("124","CANADA");
			INV1111CountryOfVerifiedCase.put("132","CAPE VERDE");
			INV1111CountryOfVerifiedCase.put("136","CAYMAN ISLANDS");
			INV1111CountryOfVerifiedCase.put("140","CENTRAL AFRICAN REPUBLIC");
			INV1111CountryOfVerifiedCase.put("148","CHAD");
			INV1111CountryOfVerifiedCase.put("152","CHILE");
			INV1111CountryOfVerifiedCase.put("156","CHINA");
			INV1111CountryOfVerifiedCase.put("CXR","CHRISTMAS ISLAND");
			INV1111CountryOfVerifiedCase.put("CCK","COCOS (KEELING) ISLANDS");
			INV1111CountryOfVerifiedCase.put("170","COLOMBIA");
			INV1111CountryOfVerifiedCase.put("174","COMOROS");
			INV1111CountryOfVerifiedCase.put("178","CONGO");
			INV1111CountryOfVerifiedCase.put("184","COOK ISLANDS");
			INV1111CountryOfVerifiedCase.put("188","COSTA RICA");
			INV1111CountryOfVerifiedCase.put("191","CROATIA");
			INV1111CountryOfVerifiedCase.put("192","CUBA");
			INV1111CountryOfVerifiedCase.put("196","CYPRUS");
			INV1111CountryOfVerifiedCase.put("203","CZECH REPUBLIC");
			INV1111CountryOfVerifiedCase.put("CSHH","CZECHOSLOVAKIA");
			INV1111CountryOfVerifiedCase.put("180","DEMOCRATIC REPUBLIC OF THE CONGO");
			INV1111CountryOfVerifiedCase.put("208","DENMARK");
			INV1111CountryOfVerifiedCase.put("262","DJIBOUTI");
			INV1111CountryOfVerifiedCase.put("212","DOMINICA");
			INV1111CountryOfVerifiedCase.put("214","DOMINICAN REPUBLIC");
			INV1111CountryOfVerifiedCase.put("218","ECUADOR");
			INV1111CountryOfVerifiedCase.put("818","EGYPT");
			INV1111CountryOfVerifiedCase.put("222","EL SALVADOR");
			INV1111CountryOfVerifiedCase.put("226","EQUATORIAL GUINEA");
			INV1111CountryOfVerifiedCase.put("232","ERITREA");
			INV1111CountryOfVerifiedCase.put("233","ESTONIA");
			INV1111CountryOfVerifiedCase.put("231","ETHIOPIA");
			INV1111CountryOfVerifiedCase.put("238","FALKLAND ISLANDS (MALVINAS)");
			INV1111CountryOfVerifiedCase.put("FRO","FAROE ISLANDS");
			INV1111CountryOfVerifiedCase.put("242","FIJI");
			INV1111CountryOfVerifiedCase.put("246","FINLAND");
			INV1111CountryOfVerifiedCase.put("250","FRANCE");
			INV1111CountryOfVerifiedCase.put("254","FRENCH GUIANA");
			INV1111CountryOfVerifiedCase.put("258","FRENCH POLYNESIA");
			INV1111CountryOfVerifiedCase.put("ATF","FRENCH SOUTHERN TERRITORIES");
			INV1111CountryOfVerifiedCase.put("266","GABON");
			INV1111CountryOfVerifiedCase.put("270","GAMBIA, THE");
			INV1111CountryOfVerifiedCase.put("268","GEORGIA");
			INV1111CountryOfVerifiedCase.put("276","GERMANY");
			INV1111CountryOfVerifiedCase.put("288","GHANA");
			INV1111CountryOfVerifiedCase.put("292","GIBRALTAR");
			INV1111CountryOfVerifiedCase.put("300","GREECE");
			INV1111CountryOfVerifiedCase.put("304","GREENLAND");
			INV1111CountryOfVerifiedCase.put("308","GRENADA");
			INV1111CountryOfVerifiedCase.put("312","GUADELOUPE");
			INV1111CountryOfVerifiedCase.put("316","GUAM");
			INV1111CountryOfVerifiedCase.put("320","GUATEMALA");
			INV1111CountryOfVerifiedCase.put("GGY","GUERNSEY");
			INV1111CountryOfVerifiedCase.put("324","GUINEA");
			INV1111CountryOfVerifiedCase.put("624","GUINEA-BISSAU");
			INV1111CountryOfVerifiedCase.put("328","GUYANA");
			INV1111CountryOfVerifiedCase.put("332","HAITI");
			INV1111CountryOfVerifiedCase.put("HMD","HEARD ISLAND AND MCDONALD ISLANDS");
			INV1111CountryOfVerifiedCase.put("VAT","HOLY SEE (VATICAN CITY STATE)");
			INV1111CountryOfVerifiedCase.put("340","HONDURAS");
			INV1111CountryOfVerifiedCase.put("HKG","HONG KONG");
			INV1111CountryOfVerifiedCase.put("348","HUNGARY");
			INV1111CountryOfVerifiedCase.put("352","ICELAND");
			INV1111CountryOfVerifiedCase.put("356","India India India India India India India India India India India India");
			INV1111CountryOfVerifiedCase.put("360","INDONESIA");
			INV1111CountryOfVerifiedCase.put("IRN","IRAN");
			INV1111CountryOfVerifiedCase.put("368","IRAQ");
			INV1111CountryOfVerifiedCase.put("NTHH","IRAQ-S ARABIA NEUTRAL ZONE");
			INV1111CountryOfVerifiedCase.put("372","IRELAND");
			INV1111CountryOfVerifiedCase.put("833","ISLE OF MAN");
			INV1111CountryOfVerifiedCase.put("376","ISRAEL");
			INV1111CountryOfVerifiedCase.put("380","ITALY");
			INV1111CountryOfVerifiedCase.put("CIV","IVORY COAST");
			INV1111CountryOfVerifiedCase.put("388","JAMAICA");
			INV1111CountryOfVerifiedCase.put("392","JAPAN");
			INV1111CountryOfVerifiedCase.put("JEY","JERSEY");
			INV1111CountryOfVerifiedCase.put("400","JORDAN");
			INV1111CountryOfVerifiedCase.put("398","KAZAKHSTAN");
			INV1111CountryOfVerifiedCase.put("404","KENYA");
			INV1111CountryOfVerifiedCase.put("296","KIRIBATI");
			INV1111CountryOfVerifiedCase.put("PRK","KOREA, DEMOCRATIC PEOPLES REPUBLIC OF");
			INV1111CountryOfVerifiedCase.put("KOR","KOREA, REPUBLIC OF");
			INV1111CountryOfVerifiedCase.put("414","KUWAIT");
			INV1111CountryOfVerifiedCase.put("417","KYRGYZSTAN");
			INV1111CountryOfVerifiedCase.put("LAO","LAOS");
			INV1111CountryOfVerifiedCase.put("428","LATVIA");
			INV1111CountryOfVerifiedCase.put("422","LEBANON");
			INV1111CountryOfVerifiedCase.put("426","LESOTHO");
			INV1111CountryOfVerifiedCase.put("430","LIBERIA");
			INV1111CountryOfVerifiedCase.put("434","LIBYA");
			INV1111CountryOfVerifiedCase.put("438","LIECHTENSTEIN");
			INV1111CountryOfVerifiedCase.put("440","LITHUANIA");
			INV1111CountryOfVerifiedCase.put("442","LUXEMBOURG");
			INV1111CountryOfVerifiedCase.put("MAC","MACAU");
			INV1111CountryOfVerifiedCase.put("MKD","MACEDONIA");
			INV1111CountryOfVerifiedCase.put("450","MADAGASCAR");
			INV1111CountryOfVerifiedCase.put("454","MALAWI");
			INV1111CountryOfVerifiedCase.put("458","MALAYSIA");
			INV1111CountryOfVerifiedCase.put("462","MALDIVES");
			INV1111CountryOfVerifiedCase.put("466","MALI");
			INV1111CountryOfVerifiedCase.put("470","MALTA");
			INV1111CountryOfVerifiedCase.put("584","MARSHALL ISLANDS");
			INV1111CountryOfVerifiedCase.put("474","MARTINIQUE");
			INV1111CountryOfVerifiedCase.put("478","MAURITANIA");
			INV1111CountryOfVerifiedCase.put("480","MAURITIUS");
			INV1111CountryOfVerifiedCase.put("MYT","MAYOTTE");
			INV1111CountryOfVerifiedCase.put("484","MEXICO");
			INV1111CountryOfVerifiedCase.put("583","MICRONESIA, FEDERATED STATES OF");
			INV1111CountryOfVerifiedCase.put("MIUM","MIDWAY ISLAND");
			INV1111CountryOfVerifiedCase.put("MDA","MOLDOVA");
			INV1111CountryOfVerifiedCase.put("492","MONACO");
			INV1111CountryOfVerifiedCase.put("496","MONGOLIA");
			INV1111CountryOfVerifiedCase.put("MNE","MONTENEGRO");
			INV1111CountryOfVerifiedCase.put("500","MONTSERRAT");
			INV1111CountryOfVerifiedCase.put("504","MOROCCO");
			INV1111CountryOfVerifiedCase.put("508","MOZAMBIQUE");
			INV1111CountryOfVerifiedCase.put("104","MYANMAR");
			INV1111CountryOfVerifiedCase.put("516","NAMIBIA");
			INV1111CountryOfVerifiedCase.put("520","NAURU");
			INV1111CountryOfVerifiedCase.put("524","NEPAL");
			INV1111CountryOfVerifiedCase.put("528","NETHERLANDS");
			INV1111CountryOfVerifiedCase.put("530","NETHERLANDS ANTILLES");
			INV1111CountryOfVerifiedCase.put("540","NEW CALEDONIA");
			INV1111CountryOfVerifiedCase.put("554","NEW ZEALAND");
			INV1111CountryOfVerifiedCase.put("558","NICARAGUA");
			INV1111CountryOfVerifiedCase.put("562","NIGER");
			INV1111CountryOfVerifiedCase.put("566","NIGERIA");
			INV1111CountryOfVerifiedCase.put("570","NIUE");
			INV1111CountryOfVerifiedCase.put("574","NORFOLK ISLAND");
			INV1111CountryOfVerifiedCase.put("580","NORTHERN MARIANA ISLANDS");
			INV1111CountryOfVerifiedCase.put("578","NORWAY");
			INV1111CountryOfVerifiedCase.put("512","OMAN");
			INV1111CountryOfVerifiedCase.put("586","PAKISTAN");
			INV1111CountryOfVerifiedCase.put("585","PALAU");
			INV1111CountryOfVerifiedCase.put("275","PALESTINIAN TERRITORY, OCCUPIED");
			INV1111CountryOfVerifiedCase.put("591","PANAMA");
			INV1111CountryOfVerifiedCase.put("598","PAPUA NEW GUINEA");
			INV1111CountryOfVerifiedCase.put("600","PARAGUAY");
			INV1111CountryOfVerifiedCase.put("604","PERU");
			INV1111CountryOfVerifiedCase.put("608","PHILIPPINES");
			INV1111CountryOfVerifiedCase.put("612","PITCAIRN ISLANDS");
			INV1111CountryOfVerifiedCase.put("616","POLAND");
			INV1111CountryOfVerifiedCase.put("620","PORTUGAL");
			INV1111CountryOfVerifiedCase.put("626","PORTUGUESE TIMOR");
			INV1111CountryOfVerifiedCase.put("630","PUERTO RICO");
			INV1111CountryOfVerifiedCase.put("634","QATAR");
			INV1111CountryOfVerifiedCase.put("REU","REUNION");
			INV1111CountryOfVerifiedCase.put("642","ROMANIA");
			INV1111CountryOfVerifiedCase.put("643","RUSSIA");
			INV1111CountryOfVerifiedCase.put("646","RWANDA");
			INV1111CountryOfVerifiedCase.put("SGS","S.GEORGIA/S.SANDWICH ISLANDS");
			INV1111CountryOfVerifiedCase.put("674","SAN MARINO");
			INV1111CountryOfVerifiedCase.put("678","SAO TOME AND PRINCIPE");
			INV1111CountryOfVerifiedCase.put("682","SAUDI ARABIA");
			INV1111CountryOfVerifiedCase.put("686","SENEGAL");
			INV1111CountryOfVerifiedCase.put("SRB","SERBIA");
			INV1111CountryOfVerifiedCase.put("690","SEYCHELLES");
			INV1111CountryOfVerifiedCase.put("694","SIERRA LEONE");
			INV1111CountryOfVerifiedCase.put("702","SINGAPORE");
			INV1111CountryOfVerifiedCase.put("703","SLOVAKIA (SLOVAK REPUBLIC)");
			INV1111CountryOfVerifiedCase.put("705","SLOVENIA");
			INV1111CountryOfVerifiedCase.put("90","SOLOMON ISLANDS");
			INV1111CountryOfVerifiedCase.put("706","SOMALIA");
			INV1111CountryOfVerifiedCase.put("710","SOUTH AFRICA");
			INV1111CountryOfVerifiedCase.put("SUHH","SOVIET UNION");
			INV1111CountryOfVerifiedCase.put("724","SPAIN");
			INV1111CountryOfVerifiedCase.put("144","SRI LANKA");
			INV1111CountryOfVerifiedCase.put("BLM","ST. BARTHELEMY");
			INV1111CountryOfVerifiedCase.put("654","ST. HELENA");
			INV1111CountryOfVerifiedCase.put("659","ST. KITTS AND NEVIS");
			INV1111CountryOfVerifiedCase.put("662","ST. LUCIA");
			INV1111CountryOfVerifiedCase.put("MAF","ST. MARTIN (FRENCH PART)");
			INV1111CountryOfVerifiedCase.put("666","ST. PIERRE AND MIQUELON");
			INV1111CountryOfVerifiedCase.put("670","ST. VINCENT/GRENADINES");
			INV1111CountryOfVerifiedCase.put("736","SUDAN");
			INV1111CountryOfVerifiedCase.put("740","SURINAME");
			INV1111CountryOfVerifiedCase.put("SJM","SVALBARD AND JAN MAYEN ISLANDS");
			INV1111CountryOfVerifiedCase.put("748","SWAZILAND");
			INV1111CountryOfVerifiedCase.put("752","SWEDEN");
			INV1111CountryOfVerifiedCase.put("756","SWITZERLAND");
			INV1111CountryOfVerifiedCase.put("760","SYRIA (SYRIAN ARAB REPUBLIC)");
			INV1111CountryOfVerifiedCase.put("TWN","TAIWAN");
			INV1111CountryOfVerifiedCase.put("762","TAJIKISTAN");
			INV1111CountryOfVerifiedCase.put("TZA","TANZANIA, UNITED REPUBLIC OF");
			INV1111CountryOfVerifiedCase.put("764","THAILAND");
			INV1111CountryOfVerifiedCase.put("TLS","TIMOR-LESTE (EAST TIMOR)");
			INV1111CountryOfVerifiedCase.put("768","TOGO");
			INV1111CountryOfVerifiedCase.put("772","TOKELAU");
			INV1111CountryOfVerifiedCase.put("776","TONGA");
			INV1111CountryOfVerifiedCase.put("780","TRINIDAD AND TOBAGO");
			INV1111CountryOfVerifiedCase.put("788","TUNISIA");
			INV1111CountryOfVerifiedCase.put("792","TURKEY");
			INV1111CountryOfVerifiedCase.put("795","TURKMENISTAN");
			INV1111CountryOfVerifiedCase.put("796","TURKS AND CAICOS ISLANDS");
			INV1111CountryOfVerifiedCase.put("798","TUVALU");
			INV1111CountryOfVerifiedCase.put("UMI","U.S. MINOR OUTLYING ISLANDS");
			INV1111CountryOfVerifiedCase.put("VIR","U.S. VIRGIN ISLANDS");
			INV1111CountryOfVerifiedCase.put("800","UGANDA");
			INV1111CountryOfVerifiedCase.put("804","UKRAINE");
			INV1111CountryOfVerifiedCase.put("784","UNITED ARAB EMIRATES (UAE)");
			INV1111CountryOfVerifiedCase.put("826","UNITED KINGDOM");
			INV1111CountryOfVerifiedCase.put("840","UNITED STATES");
			INV1111CountryOfVerifiedCase.put("UNK","UNKNOWN");
			INV1111CountryOfVerifiedCase.put("858","URUGUAY");
			INV1111CountryOfVerifiedCase.put("PUUM","US MISC PACIFIC ISLANDS");
			INV1111CountryOfVerifiedCase.put("860","UZBEKISTAN");
			INV1111CountryOfVerifiedCase.put("548","VANUATU (NEW HEBRIDES)");
			INV1111CountryOfVerifiedCase.put("862","VENEZUELA");
			INV1111CountryOfVerifiedCase.put("704","VIETNAM");
			INV1111CountryOfVerifiedCase.put("WKUM","WAKE ISLAND");
			INV1111CountryOfVerifiedCase.put("WLF","WALLIS AND FUTUNA ISLANDS");
			INV1111CountryOfVerifiedCase.put("WE","WEST BANK");
			INV1111CountryOfVerifiedCase.put("732","WESTERN SAHARA");
			INV1111CountryOfVerifiedCase.put("882","WESTERN SAMOA");
			INV1111CountryOfVerifiedCase.put("887","YEMEN");
			INV1111CountryOfVerifiedCase.put("YUCS","YUGOSLAVIA");
			INV1111CountryOfVerifiedCase.put("ZRCD","ZAIRE");
			INV1111CountryOfVerifiedCase.put("894","ZAMBIA");
			INV1111CountryOfVerifiedCase.put("716","ZIMBABWE");

			
		}
		
	}
	public static void initializeCountryOfUsualresidency(){
	if(INV501CountryOfUsualResidency.size()==0){
		INV501CountryOfUsualResidency.put("100","BULGARIA");
		INV501CountryOfUsualResidency.put("104","MYANMAR");
		INV501CountryOfUsualResidency.put("108","BURUNDI");
		INV501CountryOfUsualResidency.put("112","BELARUS");
		INV501CountryOfUsualResidency.put("116","CAMBODIA");
		INV501CountryOfUsualResidency.put("12","ALGERIA");
		INV501CountryOfUsualResidency.put("120","CAMEROON");
		INV501CountryOfUsualResidency.put("124","CANADA");
		INV501CountryOfUsualResidency.put("132","CAPE VERDE");
		INV501CountryOfUsualResidency.put("136","CAYMAN ISLANDS");
		INV501CountryOfUsualResidency.put("140","CENTRAL AFRICAN REPUBLIC");
		INV501CountryOfUsualResidency.put("144","SRI LANKA");
		INV501CountryOfUsualResidency.put("148","CHAD");
		INV501CountryOfUsualResidency.put("152","CHILE");
		INV501CountryOfUsualResidency.put("156","CHINA");
		INV501CountryOfUsualResidency.put("158","TAIWAN, PROVINCE OF CHINA");
		INV501CountryOfUsualResidency.put("16","AMERICAN SAMOA");
		INV501CountryOfUsualResidency.put("170","COLOMBIA");
		INV501CountryOfUsualResidency.put("174","COMOROS");
		INV501CountryOfUsualResidency.put("178","CONGO");
		INV501CountryOfUsualResidency.put("180","DEMOCRATIC REPUBLIC OF THE CONGO");
		INV501CountryOfUsualResidency.put("184","COOK ISLANDS");
		INV501CountryOfUsualResidency.put("188","COSTA RICA");
		INV501CountryOfUsualResidency.put("191","CROATIA");
		INV501CountryOfUsualResidency.put("192","CUBA");
		INV501CountryOfUsualResidency.put("196","CYPRUS");
		INV501CountryOfUsualResidency.put("20","ANDORRA");
		INV501CountryOfUsualResidency.put("203","CZECH REPUBLIC");
		INV501CountryOfUsualResidency.put("204","BENIN");
		INV501CountryOfUsualResidency.put("208","DENMARK");
		INV501CountryOfUsualResidency.put("212","DOMINICA");
		INV501CountryOfUsualResidency.put("214","DOMINICAN REPUBLIC");
		INV501CountryOfUsualResidency.put("218","ECUADOR");
		INV501CountryOfUsualResidency.put("222","EL SALVADOR");
		INV501CountryOfUsualResidency.put("226","EQUATORIAL GUINEA");
		INV501CountryOfUsualResidency.put("231","ETHIOPIA");
		INV501CountryOfUsualResidency.put("232","ERITREA");
		INV501CountryOfUsualResidency.put("233","ESTONIA");
		INV501CountryOfUsualResidency.put("234","FAROE ISLANDS");
		INV501CountryOfUsualResidency.put("238","FALKLAND ISLANDS (MALVINAS)");
		INV501CountryOfUsualResidency.put("24","ANGOLA");
		INV501CountryOfUsualResidency.put("242","FIJI");
		INV501CountryOfUsualResidency.put("246","FINLAND");
		INV501CountryOfUsualResidency.put("250","FRANCE");
		INV501CountryOfUsualResidency.put("254","FRENCH GUIANA");
		INV501CountryOfUsualResidency.put("258","FRENCH POLYNESIA");
		INV501CountryOfUsualResidency.put("262","DJIBOUTI");
		INV501CountryOfUsualResidency.put("266","GABON");
		INV501CountryOfUsualResidency.put("268","GEORGIA");
		INV501CountryOfUsualResidency.put("270","GAMBIA");
		INV501CountryOfUsualResidency.put("275","PALESTINIAN TERRITORY, OCCUPIED");
		INV501CountryOfUsualResidency.put("276","GERMANY");
		INV501CountryOfUsualResidency.put("28","ANTIGUA AND BARBUDA");
		INV501CountryOfUsualResidency.put("288","GHANA");
		INV501CountryOfUsualResidency.put("292","GIBRALTAR");
		INV501CountryOfUsualResidency.put("296","KIRIBATI");
		INV501CountryOfUsualResidency.put("300","GREECE");
		INV501CountryOfUsualResidency.put("304","GREENLAND");
		INV501CountryOfUsualResidency.put("308","GRENADA");
		INV501CountryOfUsualResidency.put("31","AZERBAIJAN");
		INV501CountryOfUsualResidency.put("312","GUADELOUPE");
		INV501CountryOfUsualResidency.put("316","GUAM");
		INV501CountryOfUsualResidency.put("32","ARGENTINA");
		INV501CountryOfUsualResidency.put("320","GUATEMALA");
		INV501CountryOfUsualResidency.put("324","GUINEA");
		INV501CountryOfUsualResidency.put("328","GUYANA");
		INV501CountryOfUsualResidency.put("332","HAITI");
		INV501CountryOfUsualResidency.put("336","HOLY SEE (VATICAN CITY STATE)");
		INV501CountryOfUsualResidency.put("340","HONDURAS");
		INV501CountryOfUsualResidency.put("344","HONG KONG");
		INV501CountryOfUsualResidency.put("348","HUNGARY");
		INV501CountryOfUsualResidency.put("352","ICELAND");
		INV501CountryOfUsualResidency.put("356","INDIA");
		INV501CountryOfUsualResidency.put("36","AUSTRALIA");
		INV501CountryOfUsualResidency.put("360","INDONESIA");
		INV501CountryOfUsualResidency.put("364","IRAN, ISLAMIC REPUBLIC OF");
		INV501CountryOfUsualResidency.put("368","IRAQ");
		INV501CountryOfUsualResidency.put("372","IRELAND");
		INV501CountryOfUsualResidency.put("376","ISRAEL");
		INV501CountryOfUsualResidency.put("380","ITALY");
		INV501CountryOfUsualResidency.put("388","JAMAICA");
		INV501CountryOfUsualResidency.put("392","JAPAN");
		INV501CountryOfUsualResidency.put("398","KAZAKHSTAN");
		INV501CountryOfUsualResidency.put("4","AFGHANISTAN");
		INV501CountryOfUsualResidency.put("40","AUSTRIA");
		INV501CountryOfUsualResidency.put("400","JORDAN");
		INV501CountryOfUsualResidency.put("404","KENYA");
		INV501CountryOfUsualResidency.put("410","KOREA, REPUBLIC OF");
		INV501CountryOfUsualResidency.put("414","KUWAIT");
		INV501CountryOfUsualResidency.put("417","KYRGYZSTAN");
		INV501CountryOfUsualResidency.put("422","LEBANON");
		INV501CountryOfUsualResidency.put("426","LESOTHO");
		INV501CountryOfUsualResidency.put("428","LATVIA");
		INV501CountryOfUsualResidency.put("430","LIBERIA");
		INV501CountryOfUsualResidency.put("434","LIBYAN ARAB JAMAHIRIYA");
		INV501CountryOfUsualResidency.put("438","LIECHTENSTEIN");
		INV501CountryOfUsualResidency.put("44","BAHAMAS");
		INV501CountryOfUsualResidency.put("440","LITHUANIA");
		INV501CountryOfUsualResidency.put("442","LUXEMBOURG");
		INV501CountryOfUsualResidency.put("446","MACAO");
		INV501CountryOfUsualResidency.put("450","MADAGASCAR");
		INV501CountryOfUsualResidency.put("454","MALAWI");
		INV501CountryOfUsualResidency.put("458","MALAYSIA");
		INV501CountryOfUsualResidency.put("462","MALDIVES");
		INV501CountryOfUsualResidency.put("466","MALI");
		INV501CountryOfUsualResidency.put("470","MALTA");
		INV501CountryOfUsualResidency.put("474","MARTINIQUE");
		INV501CountryOfUsualResidency.put("478","MAURITANIA");
		INV501CountryOfUsualResidency.put("48","BAHRAIN");
		INV501CountryOfUsualResidency.put("480","MAURITIUS");
		INV501CountryOfUsualResidency.put("484","MEXICO");
		INV501CountryOfUsualResidency.put("492","MONACO");
		INV501CountryOfUsualResidency.put("496","MONGOLIA");
		INV501CountryOfUsualResidency.put("498","MOLDOVA, REPUBLIC OF");
		INV501CountryOfUsualResidency.put("50","BANGLADESH");
		INV501CountryOfUsualResidency.put("500","MONTSERRAT");
		INV501CountryOfUsualResidency.put("504","MOROCCO");
		INV501CountryOfUsualResidency.put("508","MOZAMBIQUE");
		INV501CountryOfUsualResidency.put("51","ARMENIA");
		INV501CountryOfUsualResidency.put("512","OMAN");
		INV501CountryOfUsualResidency.put("516","NAMIBIA");
		INV501CountryOfUsualResidency.put("52","BARBADOS");
		INV501CountryOfUsualResidency.put("520","NAURU");
		INV501CountryOfUsualResidency.put("524","NEPAL");
		INV501CountryOfUsualResidency.put("528","NETHERLANDS");
		INV501CountryOfUsualResidency.put("530","NETHERLANDS ANTILLES");
		INV501CountryOfUsualResidency.put("533","ARUBA");
		INV501CountryOfUsualResidency.put("540","NEW CALEDONIA");
		INV501CountryOfUsualResidency.put("548","VANUATU");
		INV501CountryOfUsualResidency.put("554","NEW ZEALAND");
		INV501CountryOfUsualResidency.put("558","NICARAGUA");
		INV501CountryOfUsualResidency.put("56","BELGIUM");
		INV501CountryOfUsualResidency.put("562","NIGER");
		INV501CountryOfUsualResidency.put("566","NIGERIA");
		INV501CountryOfUsualResidency.put("570","NIUE");
		INV501CountryOfUsualResidency.put("574","NORFOLK ISLAND");
		INV501CountryOfUsualResidency.put("578","NORWAY");
		INV501CountryOfUsualResidency.put("580","NORTHERN MARIANA ISLANDS");
		INV501CountryOfUsualResidency.put("583","MICRONESIA, FEDERATED STATES OF");
		INV501CountryOfUsualResidency.put("584","MARSHALL ISLANDS");
		INV501CountryOfUsualResidency.put("585","PALAU");
		INV501CountryOfUsualResidency.put("586","PAKISTAN");
		INV501CountryOfUsualResidency.put("591","PANAMA");
		INV501CountryOfUsualResidency.put("598","PAPUA NEW GUINEA");
		INV501CountryOfUsualResidency.put("60","BERMUDA");
		INV501CountryOfUsualResidency.put("600","PARAGUAY");
		INV501CountryOfUsualResidency.put("604","PERU");
		INV501CountryOfUsualResidency.put("608","PHILIPPINES");
		INV501CountryOfUsualResidency.put("612","PITCAIRN");
		INV501CountryOfUsualResidency.put("616","POLAND");
		INV501CountryOfUsualResidency.put("620","PORTUGAL");
		INV501CountryOfUsualResidency.put("624","GUINEA-BISSAU");
		INV501CountryOfUsualResidency.put("626","TIMOR-LESTE");
		INV501CountryOfUsualResidency.put("630","PUERTO RICO");
		INV501CountryOfUsualResidency.put("634","QATAR");
		INV501CountryOfUsualResidency.put("64","BHUTAN");
		INV501CountryOfUsualResidency.put("642","ROMANIA");
		INV501CountryOfUsualResidency.put("643","RUSSIAN FEDERATION");
		INV501CountryOfUsualResidency.put("646","RWANDA");
		INV501CountryOfUsualResidency.put("654","SAINT HELENA");
		INV501CountryOfUsualResidency.put("659","SAINT KITTS AND NEVIS");
		INV501CountryOfUsualResidency.put("660","ANGUILLA");
		INV501CountryOfUsualResidency.put("662","SAINT LUCIA");
		INV501CountryOfUsualResidency.put("666","SAINT PIERRE AND MIQUELON");
		INV501CountryOfUsualResidency.put("670","SAINT VINCENT AND THE GRENADINES");
		INV501CountryOfUsualResidency.put("674","SAN MARINO");
		INV501CountryOfUsualResidency.put("678","SAO TOME AND PRINCIPE");
		INV501CountryOfUsualResidency.put("68","BOLIVIA");
		INV501CountryOfUsualResidency.put("682","SAUDI ARABIA");
		INV501CountryOfUsualResidency.put("686","SENEGAL");
		INV501CountryOfUsualResidency.put("690","SEYCHELLES");
		INV501CountryOfUsualResidency.put("694","SIERRA LEONE");
		INV501CountryOfUsualResidency.put("70","BOSNIA AND HERZEGOVINA");
		INV501CountryOfUsualResidency.put("702","SINGAPORE");
		INV501CountryOfUsualResidency.put("703","SLOVAKIA");
		INV501CountryOfUsualResidency.put("704","VIET NAM");
		INV501CountryOfUsualResidency.put("705","SLOVENIA");
		INV501CountryOfUsualResidency.put("706","SOMALIA");
		INV501CountryOfUsualResidency.put("710","SOUTH AFRICA");
		INV501CountryOfUsualResidency.put("716","ZIMBABWE");
		INV501CountryOfUsualResidency.put("72","BOTSWANA");
		INV501CountryOfUsualResidency.put("724","SPAIN");
		INV501CountryOfUsualResidency.put("732","WESTERN SAHARA");
		INV501CountryOfUsualResidency.put("736","SUDAN");
		INV501CountryOfUsualResidency.put("740","SURINAME");
		INV501CountryOfUsualResidency.put("744","SVALBARD AND JAN MAYEN");
		INV501CountryOfUsualResidency.put("748","SWAZILAND");
		INV501CountryOfUsualResidency.put("752","SWEDEN");
		INV501CountryOfUsualResidency.put("756","SWITZERLAND");
		INV501CountryOfUsualResidency.put("76","BRAZIL");
		INV501CountryOfUsualResidency.put("760","SYRIAN ARAB REPUBLIC");
		INV501CountryOfUsualResidency.put("762","TAJIKISTAN");
		INV501CountryOfUsualResidency.put("764","THAILAND");
		INV501CountryOfUsualResidency.put("768","TOGO");
		INV501CountryOfUsualResidency.put("772","TOKELAU");
		INV501CountryOfUsualResidency.put("776","TONGA");
		INV501CountryOfUsualResidency.put("780","TRINIDAD AND TOBAGO");
		INV501CountryOfUsualResidency.put("784","UNITED ARAB EMIRATES");
		INV501CountryOfUsualResidency.put("788","TUNISIA");
		INV501CountryOfUsualResidency.put("792","TURKEY");
		INV501CountryOfUsualResidency.put("795","TURKMENISTAN");
		INV501CountryOfUsualResidency.put("796","TURKS AND CAICOS ISLANDS");
		INV501CountryOfUsualResidency.put("798","TUVALU");
		INV501CountryOfUsualResidency.put("8","ALBANIA");
		INV501CountryOfUsualResidency.put("800","UGANDA");
		INV501CountryOfUsualResidency.put("804","UKRAINE");
		INV501CountryOfUsualResidency.put("807","MACEDONIA, THE FORMER YUGOSLAV REPUBLIC OF");
		INV501CountryOfUsualResidency.put("818","EGYPT");
		INV501CountryOfUsualResidency.put("826","UNITED KINGDOM");
		INV501CountryOfUsualResidency.put("833","ISLE OF MAN");
		INV501CountryOfUsualResidency.put("834","TANZANIA, UNITED REPUBLIC OF");
		INV501CountryOfUsualResidency.put("84","BELIZE");
		INV501CountryOfUsualResidency.put("840","UNITED STATES");
		INV501CountryOfUsualResidency.put("850","VIRGIN ISLANDS, U.S.");
		INV501CountryOfUsualResidency.put("854","BURKINA FASO");
		INV501CountryOfUsualResidency.put("858","URUGUAY");
		INV501CountryOfUsualResidency.put("860","UZBEKISTAN");
		INV501CountryOfUsualResidency.put("862","VENEZUELA");
		INV501CountryOfUsualResidency.put("876","WALLIS AND FUTUNA");
		INV501CountryOfUsualResidency.put("882","SAMOA");
		INV501CountryOfUsualResidency.put("887","YEMEN");
		INV501CountryOfUsualResidency.put("894","ZAMBIA");
		INV501CountryOfUsualResidency.put("90","SOLOMON ISLANDS");
		INV501CountryOfUsualResidency.put("92","VIRGIN ISLANDS, BRITISH");
		INV501CountryOfUsualResidency.put("96","BRUNEI DARUSSALAM");
		INV501CountryOfUsualResidency.put("ALA","ALAND ISLANDS");
		INV501CountryOfUsualResidency.put("AT","ASHMORE AND CARTIER ISLANDS");
		INV501CountryOfUsualResidency.put("ATA","ANTARCTICA");
		INV501CountryOfUsualResidency.put("ATF","FRENCH SOUTHERN TERRITORIES");
		INV501CountryOfUsualResidency.put("BLM","SAINT BARTHELEMY");
		INV501CountryOfUsualResidency.put("BQ","NAVASSA ISLAND");
		INV501CountryOfUsualResidency.put("BS","BASSAS DA INDIA [FRANCE]");
		INV501CountryOfUsualResidency.put("BVT","BOUVET ISLAND");
		INV501CountryOfUsualResidency.put("CCK","COCOS (KEELING) ISLANDS");
		INV501CountryOfUsualResidency.put("CIV","IVORY COAST");
		INV501CountryOfUsualResidency.put("CR","CORAL SEA ISLANDS [AUSTRALIA]");
		INV501CountryOfUsualResidency.put("CSHH","CZECHOSLOVAKIA");
		INV501CountryOfUsualResidency.put("CXR","CHRISTMAS ISLAND");
		INV501CountryOfUsualResidency.put("DQ","JARVIS ISLAND [UNITED STATES]");
		INV501CountryOfUsualResidency.put("EU","Europa Island [France]");
		INV501CountryOfUsualResidency.put("FQ","BAKER ISLAND");
		INV501CountryOfUsualResidency.put("GGY","GUERNSEY");
		INV501CountryOfUsualResidency.put("GO","GLORIOSO ISLANDS [FRANCE]");
		INV501CountryOfUsualResidency.put("GZ","GAZA STRIP");
		INV501CountryOfUsualResidency.put("HMD","HEARD ISLAND AND MCDONALD ISLANDS");
		INV501CountryOfUsualResidency.put("HQ","HOWLAND ISLAND [UNITED STATES]");
		INV501CountryOfUsualResidency.put("IOT","BRITISH INDIAN OCEAN TERRITORY");
		INV501CountryOfUsualResidency.put("IP","CLIPPERTON ISLAND [FRANCE]");
		INV501CountryOfUsualResidency.put("JEY","JERSEY");
		INV501CountryOfUsualResidency.put("JN","JAN MAYEN [NORWAY]");
		INV501CountryOfUsualResidency.put("JQ","JOHNSTON ATOLL [UNITED STATES]");
		INV501CountryOfUsualResidency.put("JU","JUAN DE NOVA ISLAND [FRANCE]");
		INV501CountryOfUsualResidency.put("KQ","KINGMAN REEF [UNITED STATES]");
		INV501CountryOfUsualResidency.put("LAO","LAO PEOPLES DEMOCRATIC REPUBLIC");
		INV501CountryOfUsualResidency.put("LQ","PALMYRA ATOLL [UNITED STATES]");
		INV501CountryOfUsualResidency.put("MAF","SAINT MARTIN (FRENCH PART)");
		INV501CountryOfUsualResidency.put("MIUM","MIDWAY ISLANDS");
		INV501CountryOfUsualResidency.put("MNE","MONTENEGRO");
		INV501CountryOfUsualResidency.put("MYT","MAYOTTE");
		INV501CountryOfUsualResidency.put("NI","NO INFORMATION");
		INV501CountryOfUsualResidency.put("NTHH","NEUTRAL ZONE");
		INV501CountryOfUsualResidency.put("PF","PARACEL ISLANDS");
		INV501CountryOfUsualResidency.put("PG","SPRATLY ISLANDS");
		INV501CountryOfUsualResidency.put("PRK","KOREA, DEMOCRATIC PEOPLES REPUBLIC OF");
		INV501CountryOfUsualResidency.put("PUUM","US MISC PACIFIC ISLANDS");
		INV501CountryOfUsualResidency.put("REU","REUNION");
		INV501CountryOfUsualResidency.put("SGS","SOUTH GEORGIA AND THE SOUTH SANDWICH ISLANDS");
		INV501CountryOfUsualResidency.put("SRB","SERBIA");
		INV501CountryOfUsualResidency.put("SUHH","U.S.S.R.");
		INV501CountryOfUsualResidency.put("TE","TROMELIN ISLAND [FRANCE]");
		INV501CountryOfUsualResidency.put("TPTL","EAST TIMOR");
		INV501CountryOfUsualResidency.put("UMI","UNITED STATES MINOR OUTLYING ISLANDS");
		INV501CountryOfUsualResidency.put("UNK","UNKNOWN");
		INV501CountryOfUsualResidency.put("WKUM","WAKE ISLAND");
		INV501CountryOfUsualResidency.put("YUCS","YUGOSLAVIA");
		INV501CountryOfUsualResidency.put("ZRCD","ZAIRE");

		
	}
}
	public static void initializeEverWorkedAs(){
	
	if(INV1276EverWorkedAs.size()==0){
		INV1276EverWorkedAs.put("C0682244", "C");
		INV1276EverWorkedAs.put("223366009", "H");
		INV1276EverWorkedAs.put("PHC2121", "M");
		INV1276EverWorkedAs.put("UNK", "U");
		INV1276EverWorkedAs.put("260413007", "N");		
		
		
	}
	
	}
	
	
	public static void initializeOccupation(){
		
		
		if(code85659_1Occupation.size()==0){	
				
				
			code85659_1Occupation.put("0010","Chief executives [0010]");
			code85659_1Occupation.put("0020","General and operations managers [0020]");
			code85659_1Occupation.put("0030","Legislators [0030]");
			code85659_1Occupation.put("0040","Advertising and promotions managers [0040]");
			code85659_1Occupation.put("0050","Marketing and sales managers [0050]");
			code85659_1Occupation.put("0060","Public relations and fundraising managers [0060]");
			code85659_1Occupation.put("0100","Administrative services managers [0100]");
			code85659_1Occupation.put("0110","Computer and information systems managers [0110]");
			code85659_1Occupation.put("0120","Financial managers [0120]");
			code85659_1Occupation.put("0135","Compensation and benefits managers [0135]");
			code85659_1Occupation.put("0136","Human resources managers [0136]");
			code85659_1Occupation.put("0137","Training and development managers [0137]");
			code85659_1Occupation.put("0140","Industrial production managers [0140]");
			code85659_1Occupation.put("0150","Purchasing managers [0150]");
			code85659_1Occupation.put("0160","Transportation, storage, and distribution managers [0160]");
			code85659_1Occupation.put("0205","Farmers, ranchers, and other agricultural managers [0205]");
			code85659_1Occupation.put("0220","Construction managers [0220]");
			code85659_1Occupation.put("0230","Education administrators [0230]");
			code85659_1Occupation.put("0300","Architectural and engineering managers [0300]");
			code85659_1Occupation.put("0310","Food service managers [0310]");
			code85659_1Occupation.put("0325","Funeral service managers [0325]");
			code85659_1Occupation.put("0330","Gaming managers [0330]");
			code85659_1Occupation.put("0340","Lodging managers [0340]");
			code85659_1Occupation.put("0350","Medical and health services managers [0350]");
			code85659_1Occupation.put("0360","Natural sciences managers [0360]");
			code85659_1Occupation.put("0400","Postmasters and mail superintendents [0400]");
			code85659_1Occupation.put("0410","Property, real estate, and community association managers [0410]");
			code85659_1Occupation.put("0420","Social and community service managers [0420]");
			code85659_1Occupation.put("0425","Emergency management directors [0425]");
			code85659_1Occupation.put("0430","Managers, all other [0430]");
			code85659_1Occupation.put("0500","Agents and business managers of artists, performers, and athletes [0500]");
			code85659_1Occupation.put("0510","Buyers and purchasing agents, farm products [0510]");
			code85659_1Occupation.put("0520","Wholesale and retail buyers, except farm products [0520]");
			code85659_1Occupation.put("0530","Purchasing agents, except wholesale, retail, and farm products [0530]");
			code85659_1Occupation.put("0540","Claims adjusters, appraisers, examiners, and investigators [0540]");
			code85659_1Occupation.put("0565","Compliance officers [0565]");
			code85659_1Occupation.put("0600","Cost estimators [0600]");
			code85659_1Occupation.put("0630","Human resources workers [0630]");
			code85659_1Occupation.put("0640","Compensation, benefits, and job analysis specialists [0640]");
			code85659_1Occupation.put("0650","Training and development specialists [0650]");
			code85659_1Occupation.put("0700","Logisticians [0700]");
			code85659_1Occupation.put("0710","Management analysts [0710]");
			code85659_1Occupation.put("0725","Meeting, convention, and event planners [0725]");
			code85659_1Occupation.put("0726","Fundraisers [0726]");
			code85659_1Occupation.put("0735","Market research analysts and marketing specialists [0735]");
			code85659_1Occupation.put("0740","Business operations specialists, all other [0740]");
			code85659_1Occupation.put("0800","Accountants and auditors [0800]");
			code85659_1Occupation.put("0810","Appraisers and assessors of real estate [0810]");
			code85659_1Occupation.put("0820","Budget analysts [0820]");
			code85659_1Occupation.put("0830","Credit analysts [0830]");
			code85659_1Occupation.put("0840","Financial analysts [0840]");
			code85659_1Occupation.put("0850","Personal financial advisors [0850]");
			code85659_1Occupation.put("0860","Insurance underwriters [0860]");
			code85659_1Occupation.put("0900","Financial examiners [0900]");
			code85659_1Occupation.put("0910","Credit counselors and loan officers [0910]");
			code85659_1Occupation.put("0930","Tax examiners and collectors, and revenue agents [0930]");
			code85659_1Occupation.put("0940","Tax preparers [0940]");
			code85659_1Occupation.put("0950","Financial specialists, all other [0950]");
			code85659_1Occupation.put("1005","Computer and information research scientists [1005]");
			code85659_1Occupation.put("1006","Computer systems analysts [1006]");
			code85659_1Occupation.put("1007","Information security analysts [1007]");
			code85659_1Occupation.put("1010","Computer programmers [1010]");
			code85659_1Occupation.put("1020","Software developers, applications and systems software [1020]");
			code85659_1Occupation.put("1030","Web developers [1030]");
			code85659_1Occupation.put("1050","Computer support specialists [1050]");
			code85659_1Occupation.put("1060","Database administrators [1060]");
			code85659_1Occupation.put("1105","Network and computer systems administrators [1105]");
			code85659_1Occupation.put("1106","Computer network architects [1106]");
			code85659_1Occupation.put("1107","Computer occupations, all other [1107]");
			code85659_1Occupation.put("1200","Actuaries [1200]");
			code85659_1Occupation.put("1210","Mathematicians [1210]");
			code85659_1Occupation.put("1220","Operations research analysts [1220]");
			code85659_1Occupation.put("1230","Statisticians [1230]");
			code85659_1Occupation.put("1240","Miscellaneous mathematical science occupations [1240]");
			code85659_1Occupation.put("1300","Architects, except naval [1300]");
			code85659_1Occupation.put("1310","Surveyors, cartographers, and photogrammetrists [1310]");
			code85659_1Occupation.put("1320","Aerospace engineers [1320]");
			code85659_1Occupation.put("1330","Agricultural engineers [1330]");
			code85659_1Occupation.put("1340","Biomedical engineers [1340]");
			code85659_1Occupation.put("1350","Chemical engineers [1350]");
			code85659_1Occupation.put("1360","Civil engineers [1360]");
			code85659_1Occupation.put("1400","Computer hardware engineers [1400]");
			code85659_1Occupation.put("1410","Electrical and electronics engineers [1410]");
			code85659_1Occupation.put("1420","Environmental engineers [1420]");
			code85659_1Occupation.put("1430","Industrial engineers, including health and safety [1430]");
			code85659_1Occupation.put("1440","Marine engineers and naval architects [1440]");
			code85659_1Occupation.put("1450","Materials engineers [1450]");
			code85659_1Occupation.put("1460","Mechanical engineers [1460]");
			code85659_1Occupation.put("1500","Mining and geological engineers, including mining safety engineers [1500]");
			code85659_1Occupation.put("1510","Nuclear engineers [1510]");
			code85659_1Occupation.put("1520","Petroleum engineers [1520]");
			code85659_1Occupation.put("1530","Engineers, all other [1530]");
			code85659_1Occupation.put("1540","Drafters [1540]");
			code85659_1Occupation.put("1550","Engineering technicians, except drafters [1550]");
			code85659_1Occupation.put("1560","Surveying and mapping technicians [1560]");
			code85659_1Occupation.put("1600","Agricultural and food scientists [1600]");
			code85659_1Occupation.put("1610","Biological scientists [1610]");
			code85659_1Occupation.put("1640","Conservation scientists and foresters [1640]");
			code85659_1Occupation.put("1650","Medical scientists [1650]");
			code85659_1Occupation.put("1660","Life scientists, all other [1660]");
			code85659_1Occupation.put("1700","Astronomers and physicists [1700]");
			code85659_1Occupation.put("1710","Atmospheric and space scientists [1710]");
			code85659_1Occupation.put("1720","Chemists and materials scientists [1720]");
			code85659_1Occupation.put("1740","Environmental scientists and geoscientists [1740]");
			code85659_1Occupation.put("1760","Physical scientists, all other [1760]");
			code85659_1Occupation.put("1800","Economists [1800]");
			code85659_1Occupation.put("1815","Survey researchers [1815]");
			code85659_1Occupation.put("1820","Psychologists [1820]");
			code85659_1Occupation.put("1830","Sociologists [1830]");
			code85659_1Occupation.put("1840","Urban and regional planners [1840]");
			code85659_1Occupation.put("1860","Miscellaneous social scientists and related workers [1860]");
			code85659_1Occupation.put("1900","Agricultural and food science technicians [1900]");
			code85659_1Occupation.put("1910","Biological technicians [1910]");
			code85659_1Occupation.put("1920","Chemical technicians [1920]");
			code85659_1Occupation.put("1930","Geological and petroleum technicians [1930]");
			code85659_1Occupation.put("1940","Nuclear technicians [1940]");
			code85659_1Occupation.put("1950","Social science research assistants [1950]");
			code85659_1Occupation.put("1965","Miscellaneous life, physical, and social science technicians [1965]");
			code85659_1Occupation.put("2000","Counselors [2000]");
			code85659_1Occupation.put("2010","Social workers [2010]");
			code85659_1Occupation.put("2015","Probation officers and correctional treatment specialists [2015]");
			code85659_1Occupation.put("2016","Social and human service assistants [2016]");
			code85659_1Occupation.put("2025","Miscellaneous community and social service specialists, including health educators and community he");
			code85659_1Occupation.put("2040","Clergy [2040]");
			code85659_1Occupation.put("2050","Directors, religious activities and education [2050]");
			code85659_1Occupation.put("2060","Religious workers, all other [2060]");
			code85659_1Occupation.put("2100","Lawyers [2100]");
			code85659_1Occupation.put("2105","Judicial law clerks [2105]");
			code85659_1Occupation.put("2110","Judges, magistrates, and other judicial workers [2110]");
			code85659_1Occupation.put("2145","Paralegals and legal assistants [2145]");
			code85659_1Occupation.put("2160","Miscellaneous legal support workers [2160]");
			code85659_1Occupation.put("2200","Postsecondary teachers [2200]");
			code85659_1Occupation.put("2300","Preschool and kindergarten teachers [2300]");
			code85659_1Occupation.put("2310","Elementary and middle school teachers [2310]");
			code85659_1Occupation.put("2320","Secondary school teachers [2320]");
			code85659_1Occupation.put("2330","Special education teachers [2330]");
			code85659_1Occupation.put("2340","Other teachers and instructors [2340]");
			code85659_1Occupation.put("2400","Archivists, curators, and museum technicians [2400]");
			code85659_1Occupation.put("2430","Librarians [2430]");
			code85659_1Occupation.put("2440","Library technicians [2440]");
			code85659_1Occupation.put("2540","Teacher assistants [2540]");
			code85659_1Occupation.put("2550","Other education, training, and library workers [2550]");
			code85659_1Occupation.put("2600","Artists and related workers [2600]");
			code85659_1Occupation.put("2630","Designers [2630]");
			code85659_1Occupation.put("2700","Actors [2700]");
			code85659_1Occupation.put("2710","Producers and directors [2710]");
			code85659_1Occupation.put("2720","Athletes, coaches, umpires, and related workers [2720]");
			code85659_1Occupation.put("2740","Dancers and choreographers [2740]");
			code85659_1Occupation.put("2750","Musicians, singers, and related workers [2750]");
			code85659_1Occupation.put("2760","Entertainers and performers, sports and related workers, all other [2760]");
			code85659_1Occupation.put("2800","Announcers [2800]");
			code85659_1Occupation.put("2810","News analysts, reporters and correspondents [2810]");
			code85659_1Occupation.put("2825","Public relations specialists [2825]");
			code85659_1Occupation.put("2830","Editors [2830]");
			code85659_1Occupation.put("2840","Technical writers [2840]");
			code85659_1Occupation.put("2850","Writers and authors [2850]");
			code85659_1Occupation.put("2860","Miscellaneous media and communication workers [2860]");
			code85659_1Occupation.put("2900","Broadcast and sound engineering technicians and radio operators [2900]");
			code85659_1Occupation.put("2910","Photographers [2910]");
			code85659_1Occupation.put("2920","Television, video, and motion picture camera operators and editors [2920]");
			code85659_1Occupation.put("2960","Media and communication equipment workers, all other [2960]");
			code85659_1Occupation.put("3000","Chiropractors [3000]");
			code85659_1Occupation.put("3010","Dentists [3010]");
			code85659_1Occupation.put("3030","Dietitians and nutritionists [3030]");
			code85659_1Occupation.put("3040","Optometrists [3040]");
			code85659_1Occupation.put("3050","Pharmacists [3050]");
			code85659_1Occupation.put("3060","Physicians and surgeons [3060]");
			code85659_1Occupation.put("3110","Physician assistants [3110]");
			code85659_1Occupation.put("3120","Podiatrists [3120]");
			code85659_1Occupation.put("3140","Audiologists [3140]");
			code85659_1Occupation.put("3150","Occupational therapists [3150]");
			code85659_1Occupation.put("3160","Physical therapists [3160]");
			code85659_1Occupation.put("3200","Radiation therapists [3200]");
			code85659_1Occupation.put("3210","Recreational therapists [3210]");
			code85659_1Occupation.put("3220","Respiratory therapists [3220]");
			code85659_1Occupation.put("3230","Speech-language pathologists [3230]");
			code85659_1Occupation.put("3235","Exercise physiologists [3235]");
			code85659_1Occupation.put("3245","Therapists, all other [3245]");
			code85659_1Occupation.put("3250","Veterinarians [3250]");
			code85659_1Occupation.put("3255","Registered nurses [3255]");
			code85659_1Occupation.put("3256","Nurse anesthetists [3256]");
			code85659_1Occupation.put("3257","Nurse midwives [3257]");
			code85659_1Occupation.put("3258","Nurse practitioners [3258]");
			code85659_1Occupation.put("3260","Health diagnosing and treating practitioners, all other [3260]");
			code85659_1Occupation.put("3300","Clinical laboratory technologists and technicians [3300]");
			code85659_1Occupation.put("3310","Dental hygienists [3310]");
			code85659_1Occupation.put("3320","Diagnostic related technologists and technicians [3320]");
			code85659_1Occupation.put("3400","Emergency medical technicians and paramedics [3400]");
			code85659_1Occupation.put("3420","Health practitioner support technologists and technicians [3420]");
			code85659_1Occupation.put("3500","Licensed practical and licensed vocational nurses [3500]");
			code85659_1Occupation.put("3510","Medical records and health information technicians [3510]");
			code85659_1Occupation.put("3520","Opticians, dispensing [3520]");
			code85659_1Occupation.put("3535","Miscellaneous health technologists and technicians [3535]");
			code85659_1Occupation.put("3540","Other healthcare practitioners and technical occupations [3540]");
			code85659_1Occupation.put("3600","Nursing, psychiatric, and home health aides [3600]");
			code85659_1Occupation.put("3610","Occupational therapy assistants and aides [3610]");
			code85659_1Occupation.put("3620","Physical therapist assistants and aides [3620]");
			code85659_1Occupation.put("3630","Massage therapists [3630]");
			code85659_1Occupation.put("3640","Dental assistants [3640]");
			code85659_1Occupation.put("3645","Medical assistants [3645]");
			code85659_1Occupation.put("3646","Medical transcriptionists [3646]");
			code85659_1Occupation.put("3647","Pharmacy aides [3647]");
			code85659_1Occupation.put("3648","Veterinary assistants and laboratory animal caretakers [3648]");
			code85659_1Occupation.put("3649","Phlebotomists [3649]");
			code85659_1Occupation.put("3655","Healthcare support workers, all other, including medical equipment preparers [3655]");
			code85659_1Occupation.put("3700","First-line supervisors of correctional officers [3700]");
			code85659_1Occupation.put("3710","First-line supervisors of police and detectives [3710]");
			code85659_1Occupation.put("3720","First-line supervisors of fire fighting and prevention workers [3720]");
			code85659_1Occupation.put("3730","First-line supervisors of protective service workers, all other [3730]");
			code85659_1Occupation.put("3740","Firefighters [3740]");
			code85659_1Occupation.put("3750","Fire inspectors [3750]");
			code85659_1Occupation.put("3800","Bailiffs, correctional officers, and jailers [3800]");
			code85659_1Occupation.put("3820","Detectives and criminal investigators [3820]");
			code85659_1Occupation.put("3830","Fish and game wardens [3830]");
			code85659_1Occupation.put("3840","Parking enforcement workers [3840]");
			code85659_1Occupation.put("3850","Police and sheriff's patrol officers [3850]");
			code85659_1Occupation.put("3860","Transit and railroad police [3860]");
			code85659_1Occupation.put("3900","Animal control workers [3900]");
			code85659_1Occupation.put("3910","Private detectives and investigators [3910]");
			code85659_1Occupation.put("3930","Security guards and gaming surveillance officers [3930]");
			code85659_1Occupation.put("3940","Crossing guards [3940]");
			code85659_1Occupation.put("3945","Transportation security screeners [3945]");
			code85659_1Occupation.put("3955","Lifeguards and other recreational, and all other protective service workers [3955]");
			code85659_1Occupation.put("4000","Chefs and head cooks [4000]");
			code85659_1Occupation.put("4010","First-line supervisors of food preparation and serving workers [4010]");
			code85659_1Occupation.put("4020","Cooks [4020]");
			code85659_1Occupation.put("4030","Food preparation workers [4030]");
			code85659_1Occupation.put("4040","Bartenders [4040]");
			code85659_1Occupation.put("4050","Combined food preparation and serving workers, including fast food [4050]");
			code85659_1Occupation.put("4060","Counter attendants, cafeteria, food concession, and coffee shop [4060]");
			code85659_1Occupation.put("4110","Waiters and waitresses [4110]");
			code85659_1Occupation.put("4120","Food servers, nonrestaurant [4120]");
			code85659_1Occupation.put("4130","Dining room and cafeteria attendants and bartender helpers [4130]");
			code85659_1Occupation.put("4140","Dishwashers [4140]");
			code85659_1Occupation.put("4150","Hosts and hostesses, restaurant, lounge, and coffee shop [4150]");
			code85659_1Occupation.put("4160","Food preparation and serving related workers, all other [4160]");
			code85659_1Occupation.put("4200","First-line supervisors of housekeeping and janitorial workers [4200]");
			code85659_1Occupation.put("4210","First-line supervisors of landscaping, lawn service, and groundskeeping workers [4210]");
			code85659_1Occupation.put("4220","Janitors and building cleaners [4220]");
			code85659_1Occupation.put("4230","Maids and housekeeping cleaners [4230]");
			code85659_1Occupation.put("4240","Pest control workers [4240]");
			code85659_1Occupation.put("4250","Grounds maintenance workers [4250]");
			code85659_1Occupation.put("4300","First-line supervisors of gaming workers [4300]");
			code85659_1Occupation.put("4320","First-line supervisors of personal service workers [4320]");
			code85659_1Occupation.put("4340","Animal trainers [4340]");
			code85659_1Occupation.put("4350","Nonfarm animal caretakers [4350]");
			code85659_1Occupation.put("4400","Gaming services workers [4400]");
			code85659_1Occupation.put("4410","Motion picture projectionists [4410]");
			code85659_1Occupation.put("4420","Ushers, lobby attendants, and ticket takers [4420]");
			code85659_1Occupation.put("4430","Miscellaneous entertainment attendants and related workers [4430]");
			code85659_1Occupation.put("4460","Embalmers and funeral attendants [4460]");
			code85659_1Occupation.put("4465","Morticians, undertakers, and funeral directors [4465]");
			code85659_1Occupation.put("4500","Barbers [4500]");
			code85659_1Occupation.put("4510","Hairdressers, hairstylists, and cosmetologists [4510]");
			code85659_1Occupation.put("4520","Miscellaneous personal appearance workers [4520]");
			code85659_1Occupation.put("4530","Baggage porters, bellhops, and concierges [4530]");
			code85659_1Occupation.put("4540","Tour and travel guides [4540]");
			code85659_1Occupation.put("4600","Childcare workers [4600]");
			code85659_1Occupation.put("4610","Personal care aides [4610]");
			code85659_1Occupation.put("4620","Recreation and fitness workers [4620]");
			code85659_1Occupation.put("4640","Residential advisors [4640]");
			code85659_1Occupation.put("4650","Personal care and service workers, all other [4650]");
			code85659_1Occupation.put("4700","First-line supervisors of retail sales workers [4700]");
			code85659_1Occupation.put("4710","First-line supervisors of non-retail sales workers [4710]");
			code85659_1Occupation.put("4720","Cashiers [4720]");
			code85659_1Occupation.put("4740","Counter and rental clerks [4740]");
			code85659_1Occupation.put("4750","Parts salespersons [4750]");
			code85659_1Occupation.put("4760","Retail salespersons [4760]");
			code85659_1Occupation.put("4800","Advertising sales agents [4800]");
			code85659_1Occupation.put("4810","Insurance sales agents [4810]");
			code85659_1Occupation.put("4820","Securities, commodities, and financial services sales agents [4820]");
			code85659_1Occupation.put("4830","Travel agents [4830]");
			code85659_1Occupation.put("4840","Sales representatives, services, all other [4840]");
			code85659_1Occupation.put("4850","Sales representatives, wholesale and manufacturing [4850]");
			code85659_1Occupation.put("4900","Models, demonstrators, and product promoters [4900]");
			code85659_1Occupation.put("4920","Real estate brokers and sales agents [4920]");
			code85659_1Occupation.put("4930","Sales engineers [4930]");
			code85659_1Occupation.put("4940","Telemarketers [4940]");
			code85659_1Occupation.put("4950","Door-to-door sales workers, news and street vendors, and related workers [4950]");
			code85659_1Occupation.put("4965","Sales and related workers, all other [4965]");
			code85659_1Occupation.put("5000","First-line supervisors of office and administrative support workers [5000]");
			code85659_1Occupation.put("5010","Switchboard operators, including answering service [5010]");
			code85659_1Occupation.put("5020","Telephone operators [5020]");
			code85659_1Occupation.put("5030","Communications equipment operators, all other [5030]");
			code85659_1Occupation.put("5100","Bill and account collectors [5100]");
			code85659_1Occupation.put("5110","Billing and posting clerks [5110]");
			code85659_1Occupation.put("5120","Bookkeeping, accounting, and auditing clerks [5120]");
			code85659_1Occupation.put("5130","Gaming cage workers [5130]");
			code85659_1Occupation.put("5140","Payroll and timekeeping clerks [5140]");
			code85659_1Occupation.put("5150","Procurement clerks [5150]");
			code85659_1Occupation.put("5160","Tellers [5160]");
			code85659_1Occupation.put("5165","Financial clerks, all other [5165]");
			code85659_1Occupation.put("5200","Brokerage clerks [5200]");
			code85659_1Occupation.put("5210","Correspondence clerks [5210]");
			code85659_1Occupation.put("5220","Court, municipal, and license clerks [5220]");
			code85659_1Occupation.put("5230","Credit authorizers, checkers, and clerks [5230]");
			code85659_1Occupation.put("5240","Customer service representatives [5240]");
			code85659_1Occupation.put("5250","Eligibility interviewers, government programs [5250]");
			code85659_1Occupation.put("5260","File clerks [5260]");
			code85659_1Occupation.put("5300","Hotel, motel, and resort desk clerks [5300]");
			code85659_1Occupation.put("5310","Interviewers, except eligibility and loan [5310]");
			code85659_1Occupation.put("5320","Library assistants, clerical [5320]");
			code85659_1Occupation.put("5330","Loan interviewers and clerks [5330]");
			code85659_1Occupation.put("5340","New accounts clerks [5340]");
			code85659_1Occupation.put("5350","Order clerks [5350]");
			code85659_1Occupation.put("5360","Human resources assistants, except payroll and timekeeping [5360]");
			code85659_1Occupation.put("5400","Receptionists and information clerks [5400]");
			code85659_1Occupation.put("5410","Reservation and transportation ticket agents and travel clerks [5410]");
			code85659_1Occupation.put("5420","Information and record clerks, all other [5420]");
			code85659_1Occupation.put("5500","Cargo and freight agents [5500]");
			code85659_1Occupation.put("5510","Couriers and messengers [5510]");
			code85659_1Occupation.put("5520","Dispatchers [5520]");
			code85659_1Occupation.put("5530","Meter readers, utilities [5530]");
			code85659_1Occupation.put("5540","Postal service clerks [5540]");
			code85659_1Occupation.put("5550","Postal service mail carriers [5550]");
			code85659_1Occupation.put("5560","Postal service mail sorters, processors, and processing machine operators [5560]");
			code85659_1Occupation.put("5600","Production, planning, and expediting clerks [5600]");
			code85659_1Occupation.put("5610","Shipping, receiving, and traffic clerks [5610]");
			code85659_1Occupation.put("5620","Stock clerks and order fillers [5620]");
			code85659_1Occupation.put("5630","Weighers, measurers, checkers, and samplers, recordkeeping [5630]");
			code85659_1Occupation.put("5700","Secretaries and administrative assistants [5700]");
			code85659_1Occupation.put("5800","Computer operators [5800]");
			code85659_1Occupation.put("5810","Data entry keyers [5810]");
			code85659_1Occupation.put("5820","Word processors and typists [5820]");
			code85659_1Occupation.put("5830","Desktop publishers [5830]");
			code85659_1Occupation.put("5840","Insurance claims and policy processing clerks [5840]");
			code85659_1Occupation.put("5850","Mail clerks and mail machine operators, except postal service [5850]");
			code85659_1Occupation.put("5860","Office clerks, general [5860]");
			code85659_1Occupation.put("5900","Office machine operators, except computer [5900]");
			code85659_1Occupation.put("5910","Proofreaders and copy markers [5910]");
			code85659_1Occupation.put("5920","Statistical assistants [5920]");
			code85659_1Occupation.put("5940","Office and administrative support workers, all other [5940]");
			code85659_1Occupation.put("6005","First-line supervisors of farming, fishing, and forestry workers [6005]");
			code85659_1Occupation.put("6010","Agricultural inspectors [6010]");
			code85659_1Occupation.put("6020","Animal breeders [6020]");
			code85659_1Occupation.put("6040","Graders and sorters, agricultural products [6040]");
			code85659_1Occupation.put("6050","Miscellaneous agricultural workers [6050]");
			code85659_1Occupation.put("6100","Fishers and related fishing workers [6100]");
			code85659_1Occupation.put("6110","Hunters and trappers [6110]");
			code85659_1Occupation.put("6120","Forest and conservation workers [6120]");
			code85659_1Occupation.put("6130","Logging workers [6130]");
			code85659_1Occupation.put("6200","First-line supervisors of construction trades and extraction workers [6200]");
			code85659_1Occupation.put("6210","Boilermakers [6210]");
			code85659_1Occupation.put("6220","Brickmasons, blockmasons, and stonemasons [6220]");
			code85659_1Occupation.put("6230","Carpenters [6230]");
			code85659_1Occupation.put("6240","Carpet, floor, and tile installers and finishers [6240]");
			code85659_1Occupation.put("6250","Cement masons, concrete finishers, and terrazzo workers [6250]");
			code85659_1Occupation.put("6260","Construction laborers [6260]");
			code85659_1Occupation.put("6300","Paving, surfacing, and tamping equipment operators [6300]");
			code85659_1Occupation.put("6310","Pile-driver operators [6310]");
			code85659_1Occupation.put("6320","Operating engineers and other construction equipment operators [6320]");
			code85659_1Occupation.put("6330","Drywall installers, ceiling tile installers, and tapers [6330]");
			code85659_1Occupation.put("6355","Electricians [6355]");
			code85659_1Occupation.put("6360","Glaziers [6360]");
			code85659_1Occupation.put("6400","Insulation workers [6400]");
			code85659_1Occupation.put("6420","Painters, construction and maintenance [6420]");
			code85659_1Occupation.put("6430","Paperhangers [6430]");
			code85659_1Occupation.put("6440","Pipelayers, plumbers, pipefitters, and steamfitters [6440]");
			code85659_1Occupation.put("6460","Plasterers and stucco masons [6460]");
			code85659_1Occupation.put("6500","Reinforcing iron and rebar workers [6500]");
			code85659_1Occupation.put("6515","Roofers [6515]");
			code85659_1Occupation.put("6520","Sheet metal workers [6520]");
			code85659_1Occupation.put("6530","Structural iron and steel workers [6530]");
			code85659_1Occupation.put("6540","Solar photovoltaic installers [6540]");
			code85659_1Occupation.put("6600","Helpers, construction trades [6600]");
			code85659_1Occupation.put("6660","Construction and building inspectors [6660]");
			code85659_1Occupation.put("6700","Elevator installers and repairers [6700]");
			code85659_1Occupation.put("6710","Fence erectors [6710]");
			code85659_1Occupation.put("6720","Hazardous materials removal workers [6720]");
			code85659_1Occupation.put("6730","Highway maintenance workers [6730]");
			code85659_1Occupation.put("6740","Rail-track laying and maintenance equipment operators [6740]");
			code85659_1Occupation.put("6750","Septic tank servicers and sewer pipe cleaners [6750]");
			code85659_1Occupation.put("6765","Miscellaneous construction and related workers [6765]");
			code85659_1Occupation.put("6800","Derrick, rotary drill, and service unit operators, oil, gas, and mining [6800]");
			code85659_1Occupation.put("6820","Earth drillers, except oil and gas [6820]");
			code85659_1Occupation.put("6830","Explosives workers, ordnance handling experts, and blasters [6830]");
			code85659_1Occupation.put("6840","Mining machine operators [6840]");
			code85659_1Occupation.put("6910","Roof bolters, mining [6910]");
			code85659_1Occupation.put("6920","Roustabouts, oil and gas [6920]");
			code85659_1Occupation.put("6930","Helpers--extraction workers [6930]");
			code85659_1Occupation.put("6940","Other extraction workers [6940]");
			initializeOccupation2();

		}
		
		
	}
	
	/**
	 * initializeOccupation2: continuation of initializeOccupation but divided in 2 because of unit testing.
	 */
	private static void initializeOccupation2(){
	
	code85659_1Occupation.put("7000","First-line supervisors of mechanics, installers, and repairers [7000]");
	code85659_1Occupation.put("7010","Computer, automated teller, and office machine repairers [7010]");
	code85659_1Occupation.put("7020","Radio and telecommunications equipment installers and repairers [7020]");
	code85659_1Occupation.put("7030","Avionics technicians [7030]");
	code85659_1Occupation.put("7040","Electric motor, power tool, and related repairers [7040]");
	code85659_1Occupation.put("7050","Electrical and electronics installers and repairers, transportation equipment [7050]");
	code85659_1Occupation.put("7100","Electrical and electronics repairers, industrial and utility [7100]");
	code85659_1Occupation.put("7110","Electronic equipment installers and repairers, motor vehicles [7110]");
	code85659_1Occupation.put("7120","Electronic home entertainment equipment installers and repairers [7120]");
	code85659_1Occupation.put("7130","Security and fire alarm systems installers [7130]");
	code85659_1Occupation.put("7140","Aircraft mechanics and service technicians [7140]");
	code85659_1Occupation.put("7150","Automotive body and related repairers [7150]");
	code85659_1Occupation.put("7160","Automotive glass installers and repairers [7160]");
	code85659_1Occupation.put("7200","Automotive service technicians and mechanics [7200]");
	code85659_1Occupation.put("7210","Bus and truck mechanics and diesel engine specialists [7210]");
	code85659_1Occupation.put("7220","Heavy vehicle and mobile equipment service technicians and mechanics [7220]");
	code85659_1Occupation.put("7240","Small engine mechanics [7240]");
	code85659_1Occupation.put("7260","Miscellaneous vehicle and mobile equipment mechanics, installers, and repairers [7260]");
	code85659_1Occupation.put("7300","Control and valve installers and repairers [7300]");
	code85659_1Occupation.put("7315","Heating, air conditioning, and refrigeration mechanics and installers [7315]");
	code85659_1Occupation.put("7320","Home appliance repairers [7320]");
	code85659_1Occupation.put("7330","Industrial and refractory machinery mechanics [7330]");
	code85659_1Occupation.put("7340","Maintenance and repair workers, general [7340]");
	code85659_1Occupation.put("7350","Maintenance workers, machinery [7350]");
	code85659_1Occupation.put("7360","Millwrights [7360]");
	code85659_1Occupation.put("7410","Electrical power-line installers and repairers [7410]");
	code85659_1Occupation.put("7420","Telecommunications line installers and repairers [7420]");
	code85659_1Occupation.put("7430","Precision instrument and equipment repairers [7430]");
	code85659_1Occupation.put("7440","Wind turbine service technicians [7440]");
	code85659_1Occupation.put("7510","Coin, vending, and amusement machine servicers and repairers [7510]");
	code85659_1Occupation.put("7520","Commercial divers [7520]");
	code85659_1Occupation.put("7540","Locksmiths and safe repairers [7540]");
	code85659_1Occupation.put("7550","Manufactured building and mobile home installers [7550]");
	code85659_1Occupation.put("7560","Riggers [7560]");
	code85659_1Occupation.put("7600","Signal and track switch repairers [7600]");
	code85659_1Occupation.put("7610","Helpers--installation, maintenance, and repair workers [7610]");
	code85659_1Occupation.put("7630","Other installation, maintenance, and repair workers [7630]");
	code85659_1Occupation.put("7700","First-line supervisors of production and operating workers [7700]");
	code85659_1Occupation.put("7710","Aircraft structure, surfaces, rigging, and systems assemblers [7710]");
	code85659_1Occupation.put("7720","Electrical, electronics, and electromechanical assemblers [7720]");
	code85659_1Occupation.put("7730","Engine and other machine assemblers [7730]");
	code85659_1Occupation.put("7740","Structural metal fabricators and fitters [7740]");
	code85659_1Occupation.put("7750","Miscellaneous assemblers and fabricators [7750]");
	code85659_1Occupation.put("7800","Bakers [7800]");
	code85659_1Occupation.put("7810","Butchers and other meat, poultry, and fish processing workers [7810]");
	code85659_1Occupation.put("7830","Food and tobacco roasting, baking, and drying machine operators and tenders [7830]");
	code85659_1Occupation.put("7840","Food batchmakers [7840]");
	code85659_1Occupation.put("7850","Food cooking machine operators and tenders [7850]");
	code85659_1Occupation.put("7855","Food processing workers, all other [7855]");
	code85659_1Occupation.put("7900","Computer control programmers and operators [7900]");
	code85659_1Occupation.put("7920","Extruding and drawing machine setters, operators, and tenders, metal and plastic [7920]");
	code85659_1Occupation.put("7930","Forging machine setters, operators, and tenders, metal and plastic [7930]");
	code85659_1Occupation.put("7940","Rolling machine setters, operators, and tenders, metal and plastic [7940]");
	code85659_1Occupation.put("7950","Cutting, punching, and press machine setters, operators, and tenders, metal and plastic [7950]");
	code85659_1Occupation.put("7960","Drilling and boring machine tool setters, operators, and tenders, metal and plastic [7960]");
	code85659_1Occupation.put("8000","Grinding, lapping, polishing, and buffing machine tool setters, operators, and tenders, metal and p");
	code85659_1Occupation.put("8010","Lathe and turning machine tool setters, operators, and tenders, metal and plastic [8010]");
	code85659_1Occupation.put("8020","Milling and planing machine setters, operators, and tenders, metal and plastic [8020]");
	code85659_1Occupation.put("8030","Machinists [8030]");
	code85659_1Occupation.put("8040","Metal furnace operators, tenders, pourers, and casters [8040]");
	code85659_1Occupation.put("8060","Model makers and patternmakers, metal and plastic [8060]");
	code85659_1Occupation.put("8100","Molders and molding machine setters, operators, and tenders, metal and plastic [8100]");
	code85659_1Occupation.put("8120","Multiple machine tool setters, operators, and tenders, metal and plastic [8120]");
	code85659_1Occupation.put("8130","Tool and die makers [8130]");
	code85659_1Occupation.put("8140","Welding, soldering, and brazing workers [8140]");
	code85659_1Occupation.put("8150","Heat treating equipment setters, operators, and tenders, metal and plastic [8150]");
	code85659_1Occupation.put("8160","Layout workers, metal and plastic [8160]");
	code85659_1Occupation.put("8200","Plating and coating machine setters, operators, and tenders, metal and plastic [8200]");
	code85659_1Occupation.put("8210","Tool grinders, filers, and sharpeners [8210]");
	code85659_1Occupation.put("8220","Metal workers and plastic workers, all other [8220]");
	code85659_1Occupation.put("8250","Prepress technicians and workers [8250]");
	code85659_1Occupation.put("8255","Printing press operators [8255]");
	code85659_1Occupation.put("8256","Print binding and finishing workers [8256]");
	code85659_1Occupation.put("8300","Laundry and dry-cleaning workers [8300]");
	code85659_1Occupation.put("8310","Pressers, textile, garment, and related materials [8310]");
	code85659_1Occupation.put("8320","Sewing machine operators [8320]");
	code85659_1Occupation.put("8330","Shoe and leather workers and repairers [8330]");
	code85659_1Occupation.put("8340","Shoe machine operators and tenders [8340]");
	code85659_1Occupation.put("8350","Tailors, dressmakers, and sewers [8350]");
	code85659_1Occupation.put("8360","Textile bleaching and dyeing machine operators and tenders [8360]");
	code85659_1Occupation.put("8400","Textile cutting machine setters, operators, and tenders [8400]");
	code85659_1Occupation.put("8410","Textile knitting and weaving machine setters, operators, and tenders [8410]");
	code85659_1Occupation.put("8420","Textile winding, twisting, and drawing out machine setters, operators, and tenders [8420]");
	code85659_1Occupation.put("8430","Extruding and forming machine setters, operators, and tenders, synthetic and glass fibers [8430]");
	code85659_1Occupation.put("8440","Fabric and apparel patternmakers [8440]");
	code85659_1Occupation.put("8450","Upholsterers [8450]");
	code85659_1Occupation.put("8460","Textile, apparel, and furnishings workers, all other [8460]");
	code85659_1Occupation.put("8500","Cabinetmakers and bench carpenters [8500]");
	code85659_1Occupation.put("8510","Furniture finishers [8510]");
	code85659_1Occupation.put("8520","Model makers and patternmakers, wood [8520]");
	code85659_1Occupation.put("8530","Sawing machine setters, operators, and tenders, wood [8530]");
	code85659_1Occupation.put("8540","Woodworking machine setters, operators, and tenders, except sawing [8540]");
	code85659_1Occupation.put("8550","Woodworkers, all other [8550]");
	code85659_1Occupation.put("8600","Power plant operators, distributors, and dispatchers [8600]");
	code85659_1Occupation.put("8610","Stationary engineers and boiler operators [8610]");
	code85659_1Occupation.put("8620","Water and wastewater treatment plant and system operators [8620]");
	code85659_1Occupation.put("8630","Miscellaneous plant and system operators [8630]");
	code85659_1Occupation.put("8640","Chemical processing machine setters, operators, and tenders [8640]");
	code85659_1Occupation.put("8650","Crushing, grinding, polishing, mixing, and blending workers [8650]");
	code85659_1Occupation.put("8710","Cutting workers [8710]");
	code85659_1Occupation.put("8720","Extruding, forming, pressing, and compacting machine setters, operators, and tenders [8720]");
	code85659_1Occupation.put("8730","Furnace, kiln, oven, drier, and kettle operators and tenders [8730]");
	code85659_1Occupation.put("8740","Inspectors, testers, sorters, samplers, and weighers [8740]");
	code85659_1Occupation.put("8750","Jewelers and precious stone and metal workers [8750]");
	code85659_1Occupation.put("8760","Medical, dental, and ophthalmic laboratory technicians [8760]");
	code85659_1Occupation.put("8800","Packaging and filling machine operators and tenders [8800]");
	code85659_1Occupation.put("8810","Painting workers [8810]");
	code85659_1Occupation.put("8830","Photographic process workers and processing machine operators [8830]");
	code85659_1Occupation.put("8840","Semiconductor processors [8840]");
	code85659_1Occupation.put("8850","Adhesive bonding machine operators and tenders [8850]");
	code85659_1Occupation.put("8860","Cleaning, washing, and metal pickling equipment operators and tenders [8860]");
	code85659_1Occupation.put("8900","Cooling and freezing equipment operators and tenders [8900]");
	code85659_1Occupation.put("8910","Etchers and engravers [8910]");
	code85659_1Occupation.put("8920","Molders, shapers, and casters, except metal and plastic [8920]");
	code85659_1Occupation.put("8930","Paper goods machine setters, operators, and tenders [8930]");
	code85659_1Occupation.put("8940","Tire builders [8940]");
	code85659_1Occupation.put("8950","Helpers--production workers [8950]");
	code85659_1Occupation.put("8965","Production workers, all other [8965]");
	code85659_1Occupation.put("9000","Supervisors of transportation and material moving workers [9000]");
	code85659_1Occupation.put("9010","Homemaker, Housewife - NIOSH/NCHS [9010]");
	code85659_1Occupation.put("9020","Volunteer - NIOSH/NCHS [9020]");
	code85659_1Occupation.put("9030","Aircraft pilots and flight engineers [9030]");
	code85659_1Occupation.put("9040","Air traffic controllers and airfield operations specialists [9040]");
	code85659_1Occupation.put("9050","Flight attendants [9050]");
	code85659_1Occupation.put("9060","Retired - NIOSH/NCHS [9060]");
	code85659_1Occupation.put("9070","Student - NIOSH/NCHS [9070]");
	code85659_1Occupation.put("9100","Never worked, disabled, child/infant - NIOSH/NCHS [9100]");
	code85659_1Occupation.put("9110","Ambulance drivers and attendants, except emergency medical technicians [9110]");
	code85659_1Occupation.put("9120","Bus drivers [9120]");
	code85659_1Occupation.put("9130","Driver/sales workers and truck drivers [9130]");
	code85659_1Occupation.put("9140","Taxi drivers and chauffeurs [9140]");
	code85659_1Occupation.put("9150","Motor vehicle operators, all other [9150]");
	code85659_1Occupation.put("9200","Locomotive engineers and operators [9200]");
	code85659_1Occupation.put("9230","Railroad brake, signal, and switch operators [9230]");
	code85659_1Occupation.put("9240","Railroad conductors and yardmasters [9240]");
	code85659_1Occupation.put("9260","Subway, streetcar, and other rail transportation workers [9260]");
	code85659_1Occupation.put("9300","Sailors and marine oilers [9300]");
	code85659_1Occupation.put("9310","Ship and boat captains and operators [9310]");
	code85659_1Occupation.put("9330","Ship engineers [9330]");
	code85659_1Occupation.put("9340","Bridge and lock tenders [9340]");
	code85659_1Occupation.put("9350","Parking lot attendants [9350]");
	code85659_1Occupation.put("9360","Automotive and watercraft service attendants [9360]");
	code85659_1Occupation.put("9410","Transportation inspectors [9410]");
	code85659_1Occupation.put("9415","Transportation attendants, except flight attendants [9415]");
	code85659_1Occupation.put("9420","Other transportation workers [9420]");
	code85659_1Occupation.put("9500","Conveyor operators and tenders [9500]");
	code85659_1Occupation.put("9510","Crane and tower operators [9510]");
	code85659_1Occupation.put("9520","Dredge, excavating, and loading machine operators [9520]");
	code85659_1Occupation.put("9560","Hoist and winch operators [9560]");
	code85659_1Occupation.put("9600","Industrial truck and tractor operators [9600]");
	code85659_1Occupation.put("9610","Cleaners of vehicles and equipment [9610]");
	code85659_1Occupation.put("9620","Laborers and freight, stock, and material movers, hand [9620]");
	code85659_1Occupation.put("9630","Machine feeders and offbearers [9630]");
	code85659_1Occupation.put("9640","Packers and packagers, hand [9640]");
	code85659_1Occupation.put("9650","Pumping station operators [9650]");
	code85659_1Occupation.put("9720","Refuse and recyclable material collectors [9720]");
	code85659_1Occupation.put("9730","Mine shuttle car operators [9730]");
	code85659_1Occupation.put("9740","Tank car, truck, and ship loaders [9740]");
	code85659_1Occupation.put("9750","Material moving workers, all other [9750]");
	code85659_1Occupation.put("9830","Military, rank not specified [9830]");
	code85659_1Occupation.put("9840","Military, Commissioned Officer - NIOSH/NCHS [9840]");
	code85659_1Occupation.put("9850","Military, Non-commissioned Officer/Enlisted - NIOSH/NCHS [9850]");
	code85659_1Occupation.put("9900","Unemployed or Insufficient Information - NIOSH/NCHS [9900]");
}
	/**TODO REVIEW PENDING**/	
	private static String  setValueForGridFields(String fieldName, String value) throws NEDSSAppException {
		try {
			if (fieldName.contains(NBS217) 
					||fieldName.contains(NBS219)
					||fieldName.contains(NBS130)
					||fieldName.contains(NBS132)
					||fieldName.contains(NBS134)
					||fieldName.contains(NBS136)
					||fieldName.contains(NBS250)
					){
				value = addEmptySpaces(value,"  ");
			}
			
			if(fieldName.contains(DEM118)){
				value =value.length()<2? "0" + value : value;
				value =value.length()<3? "0" + value : value;
				value =addEmptySpaces(value," ");
			}
			return value;
		} catch (Exception e) {
			throw new NEDSSAppException("CommonPDFPrintForm.setValueForGridFields, Exception thrown",e);
		}
	}
	
	/**
	 * processRepeatingQuestions method populates the repeating questions that are part of a batch(repeating questions) entry
	 * @param repeatingAnswerMap
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> processRepeatingQuestions(Map<Object, Object> repeatingAnswerMap, String formCode, int investigationRepeatNumber) throws NEDSSAppException {
		Map<String, String> returnMap = new HashMap<String, String>();
		NbsQuestionMetadata metadata =null;
		try {
			PageLoadUtil pageLoadUtil  =  new PageLoadUtil();
			pageLoadUtil.loadQuestionKeys(formCode);
			Map<Object, Object> map = pageLoadUtil.getQuestionMap();//map.get("LABAST6")
			if (map != null) {
				Collection<Object> mappedQuestions = map.values();
				Iterator<Object> iter = mappedQuestions.iterator();
				while (iter.hasNext()) {
					metadata = (NbsQuestionMetadata) iter
							.next();
					logger.error(metadata.toString());
					if(metadata.getQuestionGroupSeqNbr()!=null && metadata.getDataLocation()!=null && metadata.getQuestionIdentifier()!=null 
							&& metadata.getDataLocation().equals(NEDSSConstants.DATA_LOCATION_CASE_ANSWER_TEXT)){
						if(questionMap.containsKey(metadata.getQuestionIdentifier())){
							
						//	if(metadata.getQuestionIdentifier()!=null && metadata.getQuestionIdentifier().equalsIgnoreCase("LABAST3"))
						//		System.out.println("AQUI");
								
							populateRepeatData(repeatingAnswerMap, metadata);
						}
					}
					
				}
			}
		} catch (Exception e) {
			logger.error(metadata);
			logger.error(e);
			logger.error("CommonPDFPrintForm.processRepeatingQuestions Exception thrown, " + e);
			throw new NEDSSAppException("CommonPDFPrintForm.processRepeatingQuestions, Exception thrown",e);
		}
		return returnMap;
	}


	/**
	 * Get Entity Values from the Participant collection.
	 * Populates entity values in formSpecificQuestionAnswerMap
	 * @param theProxyVO
	 * @param invCnt - 1 for inv, 2 for coinfection
	 * @throws NEDSSAppException
	 */
	public static void populateEntities(PageActProxyVO theProxyVO, int invCnt) throws NEDSSAppException {
		try {
			PageActProxyVO actProxyVO = (PageActProxyVO) theProxyVO;
			Long patientUid = null;
			Long assignedToUid = null;
			Long physicianUid = null;
			Long interviewerOfPHC=null;
			Long initInterviewerOfPHC=null;
			Collection<Object> partColl = actProxyVO.getPublicHealthCaseVO().getTheParticipationDTCollection();
			if (partColl != null) {
				Iterator<Object> it = partColl.iterator();
				while (it.hasNext()) {
					ParticipationDT partDT = (ParticipationDT) it.next();
					if (partDT.getTypeCd().equals(NEDSSConstants.PHC_PATIENT)) {
						patientUid = partDT.getSubjectEntityUid();
					} else if (partDT.getTypeCd().equals(
							NEDSSConstants.Surveillance_Investigator_OF_PHC)) {
						assignedToUid = partDT.getSubjectEntityUid();
					} else if (partDT.getTypeCd().equals(
							NEDSSConstants.PHC_PHYSICIAN)) {
						physicianUid = partDT.getSubjectEntityUid();
					}else if (partDT.getTypeCd().equals(
							NEDSSConstants.INERVIEWER_OF_PHC)) {
						interviewerOfPHC = partDT.getSubjectEntityUid();
					}else if (partDT.getTypeCd().equals(
							NEDSSConstants.INIT_INERVIEWER_OF_PHC)) {
						initInterviewerOfPHC = partDT.getSubjectEntityUid();
					}
				}
			}
			//mapInterviewerData
			PersonVO interviewerVO= null;
			PersonVO initInterviewerVO= null;
			Collection<Object> coll = actProxyVO.getThePersonVOCollection();
			if (coll != null) {
				boolean isAssignedToDataPopulated= false;
				boolean isPhysicianDataPopulated= false;
				
				Iterator<Object> it = coll.iterator();
				while (it.hasNext()) {
					PersonVO personVO = (PersonVO) it.next();
					if (assignedToUid != null && personVO.getThePersonDT().getPersonUid().compareTo(assignedToUid) == 0 && !isAssignedToDataPopulated) {
						Collection<Object> entityColl = personVO
								.getTheEntityIdDTCollection();
						if (entityColl != null) {
							Iterator<Object> iter = entityColl.iterator();
							while (iter.hasNext()) {
								EntityIdDT entityDT = (EntityIdDT) iter.next();
								if (entityDT.getTypeCd().equals(NEDSSConstants.ENTITY_TYPECD_QEC)) {
									if(formSpecificQuestionMap.containsKey(NBS145_+ invCnt) && entityDT.getRootExtensionTxt() != null && !entityDT.getRootExtensionTxt().isEmpty()){
										//formSpecificQuestionAnswerMap.put(NBS145_+ invCnt, entityDT.getRootExtensionTxt());
										formSpecificQuestionAnswerMap.put(NBS145_ + invCnt,
												entityDT.getRootExtensionTxt());
										isAssignedToDataPopulated= true;
									}
								}
							}
						}
						continue;
					} else if (invCnt == 1 && personVO.getThePersonDT().getPersonUid().compareTo(patientUid) == 0) {
						PersonDT patientDT = personVO.getThePersonDT();
						if (patientDT.getAgeReported() != null  && formSpecificQuestionMap.containsKey(INV2001)){
							formSpecificQuestionAnswerMap.put(INV2001, patientDT.getAgeReported().toString());
						}
						if (patientDT.getAgeReported() != null && formSpecificQuestionMap.containsKey(DEM115)){
							formSpecificQuestionAnswerMap.put(DEM115,patientDT.getBirthTime_s());
						}
						if (formSpecificQuestionMap.containsKey(DEM197+SEPERATOR+invCnt))
							formSpecificQuestionAnswerMap.put(DEM197+SEPERATOR+invCnt, patientDT.getLocalId()); 
						

						if (formSpecificQuestionMap.containsKey(DEM127+SEPERATOR+"CD"))
							formSpecificQuestionAnswerMap.put(DEM127+SEPERATOR+"CD", patientDT.getDeceasedIndCd());
						
						if (formSpecificQuestionMap.containsKey(DEM128))
							formSpecificQuestionAnswerMap.put(DEM128, patientDT.getDeceasedTime_s());
						
						
						
						
						ArrayList<Object> al = (ArrayList<Object>) personVO.getThePersonRaceDTCollection();
						if(al != null){
							String africanAmerican = "";
							String americanIndian = "";
							String asian = "";
							String nativeHawaiian = "";
							String white = "";
							for(int i=0;i<al.size();i++){
								PersonRaceDT dt = ((PersonRaceDT)al.get(i));
								String race =dt.getRaceCd();
								if(DEM152RaceCodePRaceCatMap.get(race)!=null){
									String raceCode=DEM152RaceCodePRaceCatMap.get(race);
									if(raceCode.indexOf("/")>0){
										raceCode = raceCode.substring(0, raceCode.indexOf("/"));
									}
									if(formSpecificQuestionMap.containsKey(DEM152+SEPERATOR+raceCode+CODED_VALUE_TRANSLATED)){
										formSpecificQuestionAnswerMap.put(DEM152+SEPERATOR+raceCode+CODED_VALUE_TRANSLATED,race);
									}
								}
								else
									if(DetailedRaceCodePRaceCatMapAfricanAmerican.get(race)!=null){
										africanAmerican+= DetailedRaceCodePRaceCatMapAfricanAmerican.get(race)+", ";
									}else
										if(DetailedRaceCodePRaceCatMapAmericanIndian.get(race)!=null){
											americanIndian+= DetailedRaceCodePRaceCatMapAmericanIndian.get(race)+", ";
										}else
											if(DetailedRaceCodePRaceCatMapAsian.get(race)!=null){
												asian+= DetailedRaceCodePRaceCatMapAsian.get(race)+", ";
											}else
												if(DetailedRaceCodePRaceCatMapNativeHawaiian.get(race)!=null){
													nativeHawaiian+= DetailedRaceCodePRaceCatMapNativeHawaiian.get(race)+", ";
												}else
													if(DetailedRaceCodePRaceCatMapWhite.get(race)!=null){
														white+= DetailedRaceCodePRaceCatMapWhite.get(race)+", ";
													}
							}
							
							
							if(formSpecificQuestionMap.containsKey(DEM244)){
								if(africanAmerican!= null && africanAmerican.endsWith(" ,"))
									africanAmerican = africanAmerican.substring(0,africanAmerican.length()-2);
								formSpecificQuestionAnswerMap.put(DEM244,africanAmerican);
							}
							if(formSpecificQuestionMap.containsKey(DEM242)){
								if(americanIndian!= null && americanIndian.endsWith(" ,"))
									americanIndian = americanIndian.substring(0,africanAmerican.length()-2);
								formSpecificQuestionAnswerMap.put(DEM242,americanIndian);
							}
							if(formSpecificQuestionMap.containsKey(DEM243)){
								if(asian!= null && asian.endsWith(" ,"))
									asian = asian.substring(0,asian.length()-2);
								formSpecificQuestionAnswerMap.put(DEM243,asian);
							}
							if(formSpecificQuestionMap.containsKey(DEM245)){
								if(nativeHawaiian!= null && nativeHawaiian.endsWith(" ,"))
									nativeHawaiian = nativeHawaiian.substring(0,nativeHawaiian.length()-2);
								formSpecificQuestionAnswerMap.put(DEM245,nativeHawaiian);
							}
							if(formSpecificQuestionMap.containsKey(DEM246)){
								if(white!= null && white.endsWith(" ,"))
									white = white.substring(0,white.length()-2);
								formSpecificQuestionAnswerMap.put(DEM246,white);
							}
							
							
							
						}

						if (patientDT.getEthnicGroupInd() != null) {
							String ethnicity = DEM155PhvsEthnicitygroupCdcUnkMap.get(patientDT.getEthnicGroupInd());
							 if(formSpecificQuestionMap.containsKey(DEM155+CODED_VALUE_TRANSLATED)){
								formSpecificQuestionAnswerMap.put(DEM155+CODED_VALUE_TRANSLATED,patientDT.getEthnicGroupInd());
							}else if(formSpecificQuestionMap.containsKey(DEM155+CODED_VALUE_TRANSLATED)){
								formSpecificQuestionAnswerMap.put(DEM155+CODED_VALUE_TRANSLATED,ethnicity);
							}
						}
						if (patientDT.getEthnicUnkReasonCd() != null) {
							String ethnicityUnkReason = NBS273PEthnUnkReasonMap.get(patientDT.getEthnicUnkReasonCd());
							if(formSpecificQuestionMap.containsKey(NBS273+CODED_VALUE_TRANSLATED) && ethnicityUnkReason != null && !ethnicityUnkReason.isEmpty()){
								formSpecificQuestionAnswerMap.put(NBS273+CODED_VALUE_TRANSLATED,ethnicityUnkReason);
							}
						}
						
						
						if (patientDT.getMaritalStatusCd() != null) {
							String maritalStatus = DEM140PMaritalMap.get(patientDT.getMaritalStatusCd());
							 if(formSpecificQuestionMap.containsKey(DEM140+CODED_VALUE_TRANSLATED) &&  maritalStatus!=null  && !maritalStatus.equals("")){
								formSpecificQuestionAnswerMap.put(DEM140+CODED_VALUE_TRANSLATED,maritalStatus);
							}						
						}

						if (personVO.getThePersonDT().getCurrSexCd() != null) {
							String curSex = personVO.getThePersonDT().getCurrSexCd();	
							/*TODO map this */
							if(formSpecificQuestionMap.containsKey(DEM113_CD)) {
								if (curSex != null && !curSex.isEmpty())
									if (CurrentSexMap.containsKey(curSex)) //M, F, U supported on form
										formSpecificQuestionAnswerMap.put(DEM113_CD, CurrentSexMap.get(curSex));
							}
							String preferredSex = personVO.getThePersonDT().getPreferredGenderCd();	
							if(formSpecificQuestionMap.containsKey(NBS274_CD)) {
								if (preferredSex != null && !preferredSex.isEmpty())
									if (PreferredSexMap.containsKey(preferredSex)) //M, F, U supported on form
										formSpecificQuestionAnswerMap.put(NBS274_CD, PreferredSexMap.get(preferredSex));
							}
							String sexUnknownReason = personVO.getThePersonDT().getSexUnkReasonCd();	
							if(formSpecificQuestionMap.containsKey(NBS272_CD)) { 
								if (sexUnknownReason != null && !sexUnknownReason.isEmpty())
									if (SexUnknownReasonMap.containsKey(sexUnknownReason))//R for refused or D for did not know
										formSpecificQuestionAnswerMap.put(NBS272_CD, SexUnknownReasonMap.get(sexUnknownReason));
							}
							
						}
						//Get the Master Patient Record (MPR) for values not in the Patient Revision
						Long PersonParentUid=patientDT.getPersonParentUid();
						NedssUtils nedssUtils = new NedssUtils();
						Object obj = nedssUtils.lookupBean(JNDINames.PERSONEJB);
						logger.debug("PersonEJB lookup = " + obj.toString());
						PersonHome home = (PersonHome) PortableRemoteObject.narrow(obj,PersonHome.class);
						Person person = null;
						person = home.findByPrimaryKey(PersonParentUid);
						
						String url = null;
						String email = null;
						if(person.getPersonVO()!=null && invCnt==1){
							PersonVO mprVO = person.getPersonVO();
							if (mprVO.getThePersonDT().getBirthGenderCd() != null)
								formSpecificQuestionAnswerMap.put(BIRTH_SEX_CDT, mprVO.getThePersonDT().getBirthGenderCd());
							if (mprVO.getThePersonDT().getPrimLangCd() != null) {
								String primaryLangCd = mprVO.getThePersonDT().getPrimLangCd();
								CachedDropDownValues cddV = new CachedDropDownValues();
								TreeMap<Object,Object> langTreeMap = cddV.getLanguageCodeAsTreeMap();
						    	String primaryLangShortDesc = (String) langTreeMap.get(primaryLangCd);
						    	if (primaryLangShortDesc != null)
						    		formSpecificQuestionAnswerMap.put(DEM142+SEPERATOR+invCnt,primaryLangShortDesc);
							}
							if(formSpecificQuestionMap.containsKey(MaidenName)){
								if(mprVO.getThePersonDT().getMothersMaidenNm()!=null) {
									formSpecificQuestionAnswerMap.put(MaidenName, mprVO.getThePersonDT().getMothersMaidenNm());
								}
							}
							if (mprVO.getThePersonDT().getSpeaksEnglishCd() != null) {
								String speaksEnglishCd = mprVO.getThePersonDT().getSpeaksEnglishCd();
						    	if (speaksEnglishCd != null)
						    		formSpecificQuestionAnswerMap.put(NBS214SpeaksEnglish+SEPERATOR+invCnt+CODED_VALUE, speaksEnglishCd);
							}
							if(mprVO.getTheEntityLocatorParticipationDTCollection()!=null){
								Collection<Object> entityColl= mprVO.getTheEntityLocatorParticipationDTCollection();
								Iterator<Object> iterator  = entityColl.iterator();
								while(iterator.hasNext()){
									EntityLocatorParticipationDT elpDT= (EntityLocatorParticipationDT)iterator.next();
									if(elpDT.getClassCd().equals(NEDSSConstants.TELE) && elpDT.getCd().equals(NEDSSConstants.NET) && elpDT.getUseCd().equals(NEDSSConstants.HOME)){
										TeleLocatorDT teleDT = elpDT.getTheTeleLocatorDT();
										if((email==null || !email.isEmpty()) && (teleDT.getEmailAddress()!=null && !teleDT.getEmailAddress().isEmpty()))
											email =teleDT.getEmailAddress();
										if (formSpecificQuestionAnswerMap.get(DEM182) == null)
											formSpecificQuestionAnswerMap.put(DEM182, email);
										else
											formSpecificQuestionAnswerMap.put(DEM182_R2, email);
										if((url==null || !url.isEmpty()) && (teleDT.getUrlAddress()!=null && !teleDT.getUrlAddress().isEmpty()))
											url = teleDT.getUrlAddress();
										formSpecificQuestionAnswerMap.put(Internet_Site, url);
										if(url!=null  && !url.isEmpty() && email!=null && !email.isEmpty())
											break;
									}
									
									if(elpDT.getClassCd().equals(NEDSSConstants.TELE) && elpDT.getCd().equals(NEDSSConstants.BP)){
										String pager=elpDT.getTheTeleLocatorDT_s().getPhoneNbrTxt();
										formSpecificQuestionAnswerMap.put(NBS177_R2, pager);
									}
								}
							}
						}
						putEntityNameAndAddressIntoMap(personVO, PATIENT);

						//Physician INV182
						} else if (physicianUid != null && personVO.getThePersonDT().getPersonUid()
								.compareTo(physicianUid) == 0  && !isPhysicianDataPopulated) {
							
							//putEntityNameAndAddressIntoMap(personVO, PROVIDER);
							Map<String, String> tmpMap = putEntityNameAndAddressIntoMap(personVO, PROVIDER_NAME, true, 
									PROVIDER_ADDRESS, PROVIDER_CITY, STATE_STATE_SHORT_NAME, 
									PROVIDER_ZIP, PROVIDER_PHONE, null);
							String otherInfoStr = "";
							otherInfoStr = getProviderOtherInfo(tmpMap, pageForm.getPageClientVO().getAnswerMap());
							if (otherInfoStr != null)
								formSpecificQuestionAnswerMap.put(OTHER_INFO, otherInfoStr);
							isPhysicianDataPopulated= true;
						} //physician
						else if(interviewerOfPHC != null && personVO.getThePersonDT().getPersonUid()
								.compareTo(interviewerOfPHC) == 0  ) {
							interviewerVO=personVO;
						} //interviewer
						else if(initInterviewerOfPHC != null && personVO.getThePersonDT().getPersonUid()
								.compareTo(initInterviewerOfPHC) == 0  ) {
							initInterviewerVO =personVO;
						} //Initial Interviewer
					//If there's no physician, add the other info

				}
				//If there's no physician, the other info section is still displaying
				String otherInfoStr=getOtherInfo();
				if (otherInfoStr != null){
					String otherInfo = formSpecificQuestionAnswerMap.get(OTHER_INFO);
					if(otherInfo==null || otherInfo.isEmpty())
						formSpecificQuestionAnswerMap.put(OTHER_INFO, otherInfoStr);
				}
				
				mapInterviewerData(theProxyVO, invCnt,interviewerVO,initInterviewerVO);
			}
		} catch (Exception e) {
			logger.error("Error while retrieving populateEntityValues:  "
					+ e.toString());
			throw new NEDSSAppException("populateEntityValues error thrown:", e);
		}
	}


	public static void putEntityNameAndAddressIntoMap(PersonVO personVO, String typeCd) {
		PostalLocatorDT postalDT = null;
		TeleLocatorDT teleDT = null;

		if (personVO.getThePersonNameDTCollection() != null) {
			Iterator<Object> personNameIt = personVO.getThePersonNameDTCollection()
					.iterator();
			while (personNameIt.hasNext()) {
				PersonNameDT personNameDT = (PersonNameDT) personNameIt.next();
				if (personNameDT.getNmUseCd().equalsIgnoreCase("L")) {  //Legal Name
					
					if(formSpecificQuestionMap.containsKey(DEM104_FN) && typeCd.equals(PATIENT)){
						formSpecificQuestionAnswerMap.put(DEM104_FN, personNameDT.getFirstNm());
					}else
						if(formSpecificQuestionMap.containsKey(DEM104) && typeCd.equals(PATIENT)){
							if(personNameDT.getFirstNm()!=null && personNameDT.getMiddleNm()!=null) {
								formSpecificQuestionAnswerMap.put(DEM104, personNameDT.getFirstNm()+", "+personNameDT.getMiddleNm());
							}else if(personNameDT.getFirstNm()!=null && personNameDT.getMiddleNm()==null) {
								formSpecificQuestionAnswerMap.put(DEM104, personNameDT.getFirstNm());
							}else if(personNameDT.getFirstNm()==null && personNameDT.getMiddleNm()!=null) {
								formSpecificQuestionAnswerMap.put(DEM104, personNameDT.getMiddleNm());
							} else
								formSpecificQuestionAnswerMap.put(DEM104, "");
					
					
					}
					
				
					
					if(formSpecificQuestionMap.containsKey(DEM102) && typeCd.equals(PATIENT)){
						if(personNameDT.getLastNm()!=null) {
							//add the last name to the maiden name
							String maiden = formSpecificQuestionAnswerMap.get(MaidenName);
							if(maiden!=null && !maiden.isEmpty())
								formSpecificQuestionAnswerMap.put(MaidenName, personNameDT.getLastNm()+", "+maiden);
								
							formSpecificQuestionAnswerMap.put(DEM102, personNameDT.getLastNm());
							
						} else
							formSpecificQuestionAnswerMap.put(DEM102, "");
					}
					if(formSpecificQuestionMap.containsKey(DEM105) && typeCd.equals(PATIENT)){
						if(personNameDT.getMiddleNm()!=null) {
							formSpecificQuestionAnswerMap.put(DEM105, personNameDT.getMiddleNm());
						} else
							formSpecificQuestionAnswerMap.put(DEM105, "");
					}
					

				}else if (personNameDT.getNmUseCd().equalsIgnoreCase("AL") && typeCd.equals(PATIENT)) {
					//alias name goes in DEM250
					if(formSpecificQuestionMap.containsKey(DEM250)){
						if(personNameDT.getFirstNm()!=null && !personNameDT.getFirstNm().isEmpty())
							formSpecificQuestionAnswerMap.put(DEM250, personNameDT.getFirstNm());
					}
				}

			}
			
			
			
			String firstName = "";
			String lastName = "";
			String middleName = "";
			
			if(formSpecificQuestionAnswerMap.get(DEM104_FN)!=null)
				firstName = formSpecificQuestionAnswerMap.get(DEM104_FN)+" ";
			if(formSpecificQuestionAnswerMap.get(DEM102)!=null)
				lastName = formSpecificQuestionAnswerMap.get(DEM102)+", ";
			if(formSpecificQuestionAnswerMap.get(DEM105)!=null)
				middleName = formSpecificQuestionAnswerMap.get(DEM105);
			
			
			String fullNameString = lastName+firstName + middleName ;
			formSpecificQuestionAnswerMap.put(fullName, fullNameString);
			
		}
		formSpecificQuestionAnswerMap.put(ADDL_SEX, personVO.getThePersonDT().getAdditionalGenderCd());
		formSpecificQuestionAnswerMap.put(CURRENT_SEX_CDT, personVO.getThePersonDT().getCurrSexCd());
		if( personVO.getThePersonDT().getBirthGenderCd()!=null)//For RVCT, we read the information from the investigation instead of from the MPR, according to Jennifer's feedback (ND-28284)
			formSpecificQuestionAnswerMap.put(BIRTH_SEX_INV_CDT, personVO.getThePersonDT().getBirthGenderCd());
		
	//	formSpecificQuestionAnswerMap.put(PREGNANCY_STATUS_CDT, personVO.getThePersonDT().getP());
		
		if (personVO.getTheEntityLocatorParticipationDTCollection() != null) {
			Iterator<Object> entIt = personVO
					.getTheEntityLocatorParticipationDTCollection().iterator();
			while (entIt.hasNext()) {
				StringBuffer stBuff = new StringBuffer("");
				StringBuffer stBuff2 = new StringBuffer("");
				
				EntityLocatorParticipationDT entityDT = (EntityLocatorParticipationDT) entIt
						.next();
				if (entityDT != null) {
					if (entityDT.getCd() != null
							&& entityDT.getCd()!=null
							&& entityDT.getClassCd() != null
							&& entityDT.getClassCd().equalsIgnoreCase("PST")
							&& entityDT.getRecordStatusCd() != null
							&& entityDT.getRecordStatusCd().equalsIgnoreCase("ACTIVE")
							&& entityDT.getUseCd()!=null) {
						postalDT = entityDT.getThePostalLocatorDT();
						stBuff = new StringBuffer("");
						stBuff.append((postalDT.getStreetAddr1() == null) ? ""
								: (postalDT.getStreetAddr1()));
						stBuff.append((postalDT.getStreetAddr2() == null) ? ""
								: (", " + postalDT.getStreetAddr2()));
						if(formSpecificQuestionMap.containsKey(ORD110_ORD111) && typeCd.equals(PROVIDER) && entityDT.getUseCd().equalsIgnoreCase("WP") && entityDT.getCd().equalsIgnoreCase("O")){
							//String key = ORD110_ORD111__T1LabOrderingFacilityOrT2MorbReportingOrgAddress.substring(0, ORD110_ORD111__T1LabOrderingFacilityOrT2MorbReportingOrgAddress.indexOf(delimiter1));
							formSpecificQuestionAnswerMap.put(ORD110_ORD111, stBuff.toString());

						}else if(formSpecificQuestionMap.containsKey(DEM159_DEM160) && typeCd.equals(PATIENT) && entityDT.getUseCd().equalsIgnoreCase("H") && entityDT.getCd().equalsIgnoreCase("H")){
							//String key = DEM159_160__PatientStreetDEM159.substring(0, DEM159_160__PatientStreetDEM159.indexOf(delimiter1));
							formSpecificQuestionAnswerMap.put(DEM159_DEM160 , stBuff.toString());
						}else if(formSpecificQuestionMap.containsKey(DEM126) && typeCd.equals(PATIENT) && entityDT.getUseCd().equalsIgnoreCase("BIR") && entityDT.getCd().equalsIgnoreCase("F")){
							//String key = DEM159_160__PatientStreetDEM159.substring(0, DEM159_160__PatientStreetDEM159.indexOf(delimiter1));
							formSpecificQuestionAnswerMap.put(DEM126 , INV1111CountryOfVerifiedCase.get(postalDT.getCntryCd()));
						}

						if(postalDT.getCityDescTxt()!=null &&  formSpecificQuestionMap.containsKey(ORD112) && typeCd.equals(PROVIDER) && entityDT.getUseCd().equalsIgnoreCase("WP") && entityDT.getCd().equalsIgnoreCase("O")){
							//String key = ORD112;__T1LabOrderingFacilityOrT2MorbReportingOrgCity.substring(0, ORD112__T1LabOrderingFacilityOrT2MorbReportingOrgCity.indexOf(delimiter1));
							formSpecificQuestionAnswerMap.put(ORD112, postalDT.getCityDescTxt());				
						}else if(formSpecificQuestionMap.containsKey(DEM161) && typeCd.equals(PATIENT) && entityDT.getUseCd().equalsIgnoreCase("H") && entityDT.getCd().equalsIgnoreCase("H")){
							//String key = DEM161__PatientCityDEM161.substring(0, DEM161__PatientCityDEM161.indexOf(delimiter1));
							formSpecificQuestionAnswerMap.put(DEM161 , postalDT.getCityDescTxt());
						}

						String stateStr = postalDT.getStateCd() == null ? "" 
								: getStateDescTxt(postalDT.getStateCd());
						//returnMap.put(stateAdrKey, stateStr);
						if(formSpecificQuestionMap.containsKey( ORD113) && typeCd.equals(PROVIDER) && entityDT.getUseCd().equalsIgnoreCase("WP") && entityDT.getCd().equalsIgnoreCase("O")){
							//String key = ORD113__T1LabOrderingFacilityOrT2MorbReportingOrgState.substring(0, ORD113__T1LabOrderingFacilityOrT2MorbReportingOrgState.indexOf(delimiter1));
							formSpecificQuestionAnswerMap.put(ORD113, stateStr);
						}else if(formSpecificQuestionMap.containsKey(DEM162) && typeCd.equals(PATIENT) && entityDT.getUseCd().equalsIgnoreCase("H") && entityDT.getCd().equalsIgnoreCase("H")){
							//String key = DEM162__PatientStateDEM162.substring(0, DEM162__PatientStateDEM162.indexOf(delimiter1));
							formSpecificQuestionAnswerMap.put(DEM162 , stateStr);
							if (formSpecificQuestionMap.containsKey(DEM165))
								if (postalDT.getCntyCd() != null) {
									countyCodes = cache.getCountyCodes(postalDT.getStateCd());
									if (countyCodes != null && countyCodes.containsKey(postalDT.getCntyCd()))
										formSpecificQuestionAnswerMap.put(DEM165, (String)countyCodes.get(postalDT.getCntyCd()));
								}
							if (formSpecificQuestionMap.containsKey(DEM167))
								if (postalDT.getCntryCd() != null) {
									if (countryMap != null && countryMap.containsKey(postalDT.getCntryCd()))
										formSpecificQuestionAnswerMap.put(DEM167, (String)countryMap.get(postalDT.getCntryCd()));
							}
							
							if (formSpecificQuestionMap.containsKey(DEM168))
								if (postalDT.getCensusTract() != null) {
										formSpecificQuestionAnswerMap.put(DEM168, postalDT.getCensusTract());
							}
							//City_Limits_CDT
							
						/*	if (patientDT.getMaritalStatusCd() != null) {
								String maritalStatus = DEM140PMaritalMap.get(patientDT.getMaritalStatusCd());
								 if(formSpecificQuestionMap.containsKey(DEM140+CODED_VALUE_TRANSLATED) &&  maritalStatus!=null  && !maritalStatus.equals("")){
									formSpecificQuestionAnswerMap.put(DEM140+CODED_VALUE_TRANSLATED,maritalStatus);
								}						
							}*/
							
							
						}

						if(postalDT.getZipCd()!=null && formSpecificQuestionMap.containsKey( ORD114) && typeCd.equals(PROVIDER) && entityDT.getUseCd().equalsIgnoreCase("WP") && entityDT.getCd().equalsIgnoreCase("O")){
							//String key = ORD114__T1LabOrderingFacilityOrT2MorbReportingOrgZip.substring(0, ORD114__T1LabOrderingFacilityOrT2MorbReportingOrgZip.indexOf(delimiter1));
							formSpecificQuestionAnswerMap.put(ORD114, postalDT.getZipCd());
						}else if(postalDT.getZipCd()!=null && formSpecificQuestionMap.containsKey( DEM163) && typeCd.equals(PATIENT) && entityDT.getUseCd().equalsIgnoreCase("H") && entityDT.getCd().equalsIgnoreCase("H")){
							//String key = ORD114__T1LabOrderingFacilityOrT2MorbReportingOrgZip.substring(0, ORD114__T1LabOrderingFacilityOrT2MorbReportingOrgZip.indexOf(delimiter1));
							formSpecificQuestionAnswerMap.put(DEM163, postalDT.getZipCd());
						}

					} //address fields
					else if (entityDT.getClassCd().equalsIgnoreCase(
							"TELE")
							&& entityDT.getRecordStatusCd() != null
							&& entityDT.getRecordStatusCd()
							.equalsIgnoreCase("ACTIVE")
							&& entityDT.getCd() != null
							&& entityDT.getCd().equalsIgnoreCase("PH")
							&& entityDT.getUseCd() != null
							&& entityDT.getUseCd().equalsIgnoreCase("WP")) {
						teleDT = entityDT.getTheTeleLocatorDT();

						stBuff = new StringBuffer("");
						stBuff.append((teleDT.getPhoneNbrTxt() == null) ? ""
								: (teleDT.getPhoneNbrTxt() + " "));
						
						stBuff2 = new StringBuffer("");
						stBuff2.append(stBuff);
						String extension = "";
						
						String ext = "";
						if(teleDT.getExtensionTxt()!=null && !teleDT.getExtensionTxt().equals("0.0")) {
							ext = teleDT.getExtensionTxt().replace(".0", "");
							stBuff.append("Ext." + ext);
							extension="Ext." + ext;//This is used from work phone extension from RVCT,where work phone and extension are separated into two differen fields.
						}
						if(stBuff.toString().length()>0 && formSpecificQuestionMap.containsKey( ORD121_ORD122) && typeCd.equals(PROVIDER)){
								//String key = ORD121ExtORD122__T1LabOrderingFacilityOrT2MorbReportingOrgPhone.substring(0, ORD121ExtORD122__T1LabOrderingFacilityOrT2MorbReportingOrgPhone.indexOf(delimiter1));
							formSpecificQuestionAnswerMap.put(ORD121_ORD122, stBuff.toString());
						}else if(stBuff.toString().length()>0 && formSpecificQuestionMap.containsKey( ORD121_ORD122) && typeCd.equals(PROVIDER)){
							//String key = ORD121ExtORD122__T1LabOrderingFacilityOrT2MorbReportingOrgPhone.substring(0, ORD121ExtORD122__T1LabOrderingFacilityOrT2MorbReportingOrgPhone.indexOf(delimiter1));
							formSpecificQuestionAnswerMap.put(ORD121_ORD122, stBuff.toString());
						}else if( entityDT.getCd().equalsIgnoreCase("PH") && entityDT.getUseCd().equalsIgnoreCase("WP") && stBuff.toString().length()>0 && formSpecificQuestionMap.containsKey( EMPLOYMENT_PHONE)&& typeCd.equals(PATIENT)){
							formSpecificQuestionAnswerMap.put(EMPLOYMENT_PHONE, stBuff.toString());
							//Work phone
							formSpecificQuestionAnswerMap.put(NBS002, stBuff2.toString()+(" (WP)"));
							formSpecificQuestionAnswerMap.put(NBS003, extension);
							
							}
							
						
						
					}//phone get class cd
					else if (entityDT.getClassCd().equalsIgnoreCase(
							"TELE")
							&& entityDT.getRecordStatusCd() != null
							&& entityDT.getRecordStatusCd()
							.equalsIgnoreCase("ACTIVE")
							&& entityDT.getCd() != null
							&& entityDT.getUseCd() != null ) {
						teleDT = entityDT.getTheTeleLocatorDT();
						stBuff = new StringBuffer("");
						stBuff.append((teleDT.getPhoneNbrTxt() == null) ? ""
								: (teleDT.getPhoneNbrTxt() + " "));
						String ext = "";
						if(teleDT.getExtensionTxt()!=null && !teleDT.getExtensionTxt().equals("0.0")){
							ext = teleDT.getExtensionTxt().replace(".0", "");
							stBuff.append("Ext." + ext);
						}
						if( entityDT.getCd() != null
								&& entityDT.getCd().equalsIgnoreCase("CP") && stBuff.toString().length()>0 
								&& entityDT.getUseCd().equalsIgnoreCase("MC") && formSpecificQuestionMap.containsKey( NBS006) && typeCd.equals(PATIENT)){
							//String key = NBS006__PatientCellNBS006_UseCd.substring(0, NBS006__PatientCellNBS006_UseCd.indexOf(delimiter1));
							formSpecificQuestionAnswerMap.put(NBS006, stBuff.toString()+(" (C)"));
						}else if( entityDT.getCd().equalsIgnoreCase("PH") && entityDT.getUseCd().equalsIgnoreCase("H") && stBuff.toString().length()>0 && formSpecificQuestionMap.containsKey( DEM177)&& typeCd.equals(PATIENT)){
							//String key = DEM177__PatientHmPhDEM177_UseCd.substring(0, DEM177__PatientHmPhDEM177_UseCd.indexOf(delimiter1));
							formSpecificQuestionAnswerMap.put(DEM177, stBuff.toString()+(" (H)"));
						}
						else if( entityDT.getCd().equalsIgnoreCase("NET") && entityDT.getUseCd().equalsIgnoreCase("H") && stBuff.toString().length()>0 && formSpecificQuestionMap.containsKey( DEM182)&& typeCd.equals(PATIENT)){
							formSpecificQuestionAnswerMap.put(DEM182, stBuff.toString());
						}

					}//phone get class cd	
				} //entityDT != null

			} //iterator has next
		} //EntityLocatorParticipation not null
	}

	
	
	/**
	 * getMappedValues: Put the values that are needed by the form into an answerMap.
	 * @param commonFieldsMap
	 * @param req
	 * @throws NEDSSAppException
	 */
	protected static void getMappedValues(Map<String, String> commonFieldsMap, String formCd, HttpServletRequest req) throws NEDSSAppException{
		
		treatmentIndex = 1; //initialize treatment index
		labIndex = 1;//initialize lab index
		
		try {
			Set<String> mapSet=commonFieldsMap.keySet();
			Iterator<String> iterator = mapSet.iterator();
			while(iterator.hasNext()){
				String keyVal=(String)iterator.next();
				if(keyVal.contains(delimiter1)){
					String newKey = keyVal.substring(0, keyVal.indexOf(delimiter1));
					formSpecificQuestionMap.put(newKey, keyVal);
				}
			}
			
			formSpecificQuestionAnswerMap = new HashMap<String,String>();
			processMappedValues(1, formCd,proxyVO);
			if(coProxyVO!=null)
				processMappedValues(2, formCd,coProxyVO);
			
			
			processDrugsPhenotypic(formSpecificQuestionAnswerMap);
			processDrugsMDR(formSpecificQuestionAnswerMap);
			processSideEffects(formSpecificQuestionAnswerMap);
			processEverWorkedAs(formSpecificQuestionMap,formSpecificQuestionAnswerMap);
			processWhatCriteriaMet(formSpecificQuestionMap,formSpecificQuestionAnswerMap);
			processPrimarySites(formSpecificQuestionMap,formSpecificQuestionAnswerMap);
			processMovedWhere(formSpecificQuestionMap,formSpecificQuestionAnswerMap);
			processReasonTbTherapyExtended(formSpecificQuestionMap,formSpecificQuestionAnswerMap);
			processTreatmentAdministration(formSpecificQuestionMap,formSpecificQuestionAnswerMap);
			processTreatmentAdministrationLTBI(formSpecificQuestionMap,formSpecificQuestionAnswerMap);
			
			processSevereAdverseEvent(formSpecificQuestionMap,formSpecificQuestionAnswerMap);
	
			               
			getTreatmentCollection(req);
			
			
			//For avoiding process the same lab report twice when 2 conditions have the same lab report
			ArrayList<String> labsProcessed = new ArrayList<String>();
			ActRelationshipDT actRelationshipDT = getSourceAct(proxyVO);
			Long sourceUid=null;
			if(actRelationshipDT!=null){
				sourceUid=actRelationshipDT.getSourceActUid();
			}
			populateLabsWithSourceLabResultsFirst(sourceUid, proxyVO,  labsProcessed, 1);
			
			if(coProxyVO!=null){
			//For avoiding process the same lab report twice when 2 conditions have the same lab report
				ActRelationshipDT coActRelationshipDT = getSourceAct(coProxyVO);
				if(coActRelationshipDT!=null){
					sourceUid=coActRelationshipDT.getSourceActUid();
				}
				populateLabsWithSourceLabResultsFirst(sourceUid, coProxyVO,  labsProcessed, 2);
			}
			

			//Process Interviews into Map?
			if(proxyVO!=null)
				formSpecificQuestionAnswerMap.putAll(populateInterviewIntoMap(1, proxyVO, req));
			if(coProxyVO!=null)
				formSpecificQuestionAnswerMap.putAll(populateInterviewIntoMap(2, coProxyVO, req));

			
		} catch (Exception e) {
			throw new NEDSSAppException("Error while getMappedValues:  "+ e);
		}
		
	}
	

	/**
	 * Lab Results are populated from Morb and Lab Reports int the LabStagingArray.
	 * Put the lab fields into the form answer map.
	 * @throws NEDSSAppException
	 */
	protected static void populateLabFieldsFromStaging() throws NEDSSAppException {
		try {
        Arrays.sort(labStagingArray, new Comparator<String[]>() {
            public int compare(final String[] entry1, final String[] entry2) {
                final String time1 = entry1[FORMATTED_FOR_SORT_DATE_INDEX];
                final String time2 = entry2[FORMATTED_FOR_SORT_DATE_INDEX];
                if ( (time1 == null || time1.length() == 0) && (time2 == null || time2.length() == 0) )
                	return 0;
                if ( (time1 == null || time1.length() == 0) && (time2 != null && time2.length() != 0) )
                	return 1;
                if ( (time1 != null && time1.length() != 0) && (time2 == null || time2.length() == 0) )
                	return -1;
                return time1.compareTo(time2);
            }
        });
		} catch (Exception e) {
			logger.error("SOrt Exception in populateLabFieldsFromStaging "+e.getMessage(), e);
		}
		
		try {
			for (int curIndex=0; curIndex < labIndex-1; ++curIndex) {
				String theLabDateKey =LAB163_R + (curIndex+1);
				formSpecificQuestionAnswerMap.put(theLabDateKey, labStagingArray[curIndex][DATE_INDEX]);
				String theLabTestKey =  LAB220_R + (curIndex+1);
				formSpecificQuestionAnswerMap.put(theLabTestKey, labStagingArray[curIndex][TEST_INDEX]);
				String theLabResultKey =  Lab_Result_+ (curIndex+1);
				formSpecificQuestionAnswerMap.put(theLabResultKey, labStagingArray[curIndex][RESULT_INDEX]);
				String theLabLabKey = ORD3_R+(curIndex+1);
				formSpecificQuestionAnswerMap.put(theLabLabKey, labStagingArray[curIndex][LAB_INDEX]);
				String theLabSpecimenSourceKey = LAB165_R +(curIndex+1);
				formSpecificQuestionAnswerMap.put(theLabSpecimenSourceKey, labStagingArray[curIndex][SPECIMEN_SOURCE_INDEX]);
				
				if (curIndex == 7)
					break;
			}
		} catch (Exception e) {
			logger.error("Exception populateLabFieldsFromStaging "+e.getMessage(), e);
			throw new NEDSSAppException("Error while populateLabsFromStaging:  "+ e);
		}
		return;
	}

	/**
	 * process the mapped values for the specified investigation
	 * @param investigationCounter
	 * @param actProxyVO
	 * @throws NEDSSAppException
	 */
	private static void processMappedValues(int investigationCounter, String formCode,PageActProxyVO actProxyVO) throws NEDSSAppException{
		Long key = null; 
		String value="";
		Object resultObject=null;
		try {
			Map<Object, Object>  answerMap = actProxyVO.getPageVO().getPamAnswerDTMap();
			Set<Object> set= questionUidMap.keySet();
			Iterator<Object> it = set.iterator();
			while(it.hasNext()){
				key = (Long)it.next();
				NbsQuestionMetadata nbsQuestionDT= (NbsQuestionMetadata)questionUidMap.get(key);
				if(nbsQuestionDT.getDataLocation()==null || key==null)
					continue;
				if(nbsQuestionDT.getDataLocation().equalsIgnoreCase(NEDSSConstants.NBS_CASE_ANSWER_ANSWER_TXT)){
					resultObject =answerMap.get(key);
					if(resultObject instanceof NbsAnswerDT && answerMap!=null 
							&& answerMap.get(key)!=null)	{
						NbsAnswerDT caseDT = (NbsAnswerDT)answerMap.get(key);
						if(nbsQuestionDT!=null){
							populateNonRepeatData(caseDT.getAnswerTxt(),investigationCounter, nbsQuestionDT, false);
						}
						
				}else if(resultObject instanceof ArrayList){//Multiple answers, like INV1276
					
							ArrayList<NbsAnswerDT> results = (ArrayList<NbsAnswerDT>)answerMap.get(key);
						
							for(int i=0; results!=null && i< results.size(); i++){
							NbsAnswerDT answer =(NbsAnswerDT)results.get(i);
							
							if(answer!=null){
								
								if(nbsQuestionDT!=null){
									
										populateNonRepeatData(answer.getAnswerTxt(),investigationCounter, nbsQuestionDT, true);
								}
								
								
							}
							
					}
					
					
				}
				}else if(nbsQuestionDT.getDataLocation().startsWith(RenderConstants.CASE_MANAGEMENT)){
					mapValueForCaseManagementDT(nbsQuestionDT,investigationCounter, actProxyVO.getPublicHealthCaseVO().getTheCaseManagementDT());
				}else if(nbsQuestionDT.getDataLocation().startsWith(RenderConstants.PUBLIC_HEALTH_CASE)){
					mapValueForPublicHealthCaseDT(nbsQuestionDT,investigationCounter, actProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT());
				}//ACT_ID.ROOT_EXTENSION_TXT
				else if(nbsQuestionDT.getDataLocation().startsWith(RenderConstants.ACT_ID)){
					mapValueForActIdDT(actProxyVO.getPublicHealthCaseVO().getTheActIdDTCollection(),nbsQuestionDT,investigationCounter);
				}else if(nbsQuestionDT.getDataLocation().startsWith(RenderConstants.POSTAL_LOCATOR)){
					resultObject =answerMap.get(key);
					if(resultObject instanceof NbsAnswerDT && answerMap!=null 
							&& answerMap.get(key)!=null)	{
						NbsAnswerDT caseDT = (NbsAnswerDT)answerMap.get(key);
						if(nbsQuestionDT!=null){
							populateNonRepeatData(caseDT.getAnswerTxt(),investigationCounter, nbsQuestionDT, false);
						}
						
					}}
			}
			 
			
			if(actProxyVO.getPageVO().getPageRepeatingAnswerDTMap()!=null  && actProxyVO.getPageVO().getPageRepeatingAnswerDTMap().size()>0) {
				processRepeatingQuestions(actProxyVO.getPageVO().getPageRepeatingAnswerDTMap(), formCode, investigationCounter);
			}
			populateEntities(actProxyVO, investigationCounter);
		}
		catch (Exception e) {
			logger.error("key"+ key);
			logger.error("value"+ value);
			throw new NEDSSAppException("Error while getMappedValues:  "+ e);
		}
	}
	/**
	 * Get values that map to the PublicHealthCase record if present.
	 * @param nbsQuestionDT
	 * @param investigationCounterNumber
	 * @param publicHealthCaseDT
	 * @throws Exception
	 */
	private static void mapValueForPublicHealthCaseDT(NbsQuestionMetadata nbsQuestionDT, int investigationCounterNumber, PublicHealthCaseDT publicHealthCaseDT) throws Exception {
	 try{
		 


		 if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(	INV107_Public_Health_Case_jurisdiction_cd	)){
		          populateNonRepeatData(publicHealthCaseDT.getJurisdictionCd()	, investigationCounterNumber, nbsQuestionDT, false);
		 }else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(	INV108_Public_Health_Case_PROG_AREA_CD	)){
		          populateNonRepeatData(publicHealthCaseDT.getProgAreaCd()	, investigationCounterNumber, nbsQuestionDT, false);
		 }else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(	INV109_Public_Health_Case_investigation_status_cd	)){
			 populateNonRepeatData(publicHealthCaseDT.getInvestigationStatusCd()	, investigationCounterNumber, nbsQuestionDT, false);
		}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(	INV110_Public_Health_Case_INVESTIGATOR_ASSIGNED_TIME	)){
		          populateNonRepeatData(StringUtils.formatDate(publicHealthCaseDT.getInvestigatorAssignedTime())	, investigationCounterNumber, nbsQuestionDT, false);
		}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(	INV111_Public_Health_Case_rpt_form_cmplt_time	)){
		          populateNonRepeatData(StringUtils.formatDate(publicHealthCaseDT.getRptFormCmpltTime())	, investigationCounterNumber, nbsQuestionDT, false);
		}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(	INV112_Public_Health_Case_RPT_SOURCE_CD	)){
		          populateNonRepeatData(publicHealthCaseDT.getRptSourceCd()	, investigationCounterNumber, nbsQuestionDT, false);
		}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(	INV120_Public_Health_Case_rpt_to_county_time	)){
		          populateNonRepeatData(StringUtils.formatDate(publicHealthCaseDT.getRptToCountyTime())	, investigationCounterNumber, nbsQuestionDT, false);
		}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(	INV121_Public_Health_Case_RPT_TO_STATE_TIME	)){
		          populateNonRepeatData(StringUtils.formatDate(publicHealthCaseDT.getRptToStateTime())	, investigationCounterNumber, nbsQuestionDT, false);
		}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(	INV128_Public_Health_Case_hospitalized_ind_cd	)){
		          populateNonRepeatData(publicHealthCaseDT.getHospitalizedIndCd()	, investigationCounterNumber, nbsQuestionDT, false);
		}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(	INV132_Public_Health_Case_HOSPITALIZED_ADMIN_TIME	)){
		          populateNonRepeatData(StringUtils.formatDate(publicHealthCaseDT.getHospitalizedAdminTime())	, investigationCounterNumber, nbsQuestionDT, false);
		}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(	INV133_Public_Health_Case_hospitalized_discharge_time	)){
		          populateNonRepeatData(StringUtils.formatDate(publicHealthCaseDT.getHospitalizedDischargeTime())	, investigationCounterNumber, nbsQuestionDT, false);
		}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(	INV134_Public_Health_Case_HOSPITALIZED_DURATION_AMT	)){
			if(publicHealthCaseDT.getHospitalizedDurationAmt()!=null)
		          populateNonRepeatData(publicHealthCaseDT.getHospitalizedDurationAmt().toString()	, investigationCounterNumber, nbsQuestionDT, false);
		}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(	INV136_Public_Health_Case_diagnosis_time)){
		          populateNonRepeatData(StringUtils.formatDate(publicHealthCaseDT.getDiagnosisTime())	, investigationCounterNumber, nbsQuestionDT, false);
		}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(	INV137_Public_Health_Case_EFFECTIVE_FROM_TIME	)){
		          populateNonRepeatData(StringUtils.formatDate(publicHealthCaseDT.getEffectiveFromTime())	, investigationCounterNumber, nbsQuestionDT, false);
		}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(	INV138_Public_Health_Case_effective_to_time	)){
		          populateNonRepeatData(StringUtils.formatDate(publicHealthCaseDT.getEffectiveToTime())	, investigationCounterNumber, nbsQuestionDT, false);
		}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(	INV139_Public_Health_Case_EFFECTIVE_DURATION_AMT	)){
		          populateNonRepeatData(publicHealthCaseDT.getEffectiveDurationAmt()	, investigationCounterNumber, nbsQuestionDT, false);
		}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(	INV140_Public_Health_Case_effective_duration_unit_cd	)){
		          populateNonRepeatData(publicHealthCaseDT.getEffectiveDurationUnitCd()	, investigationCounterNumber, nbsQuestionDT, false);
		}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(	INV143_Public_Health_Case_PAT_AGE_AT_ONSET	)){
		          populateNonRepeatData(publicHealthCaseDT.getPatAgeAtOnset()	, investigationCounterNumber, nbsQuestionDT, false);
		}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(	INV144_Public_Health_Case_pat_age_at_onset_unit_cd	)){
		          populateNonRepeatData(publicHealthCaseDT.getPatAgeAtOnsetUnitCd()	, investigationCounterNumber, nbsQuestionDT, false);
		}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(	INV145_Public_Health_Case_OUTCOME_CD	)){
		          populateNonRepeatData(publicHealthCaseDT.getOutcomeCd()	, investigationCounterNumber, nbsQuestionDT, false);
		}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(	INV146_Public_Health_Case_deceased_time	)){
		          populateNonRepeatData(StringUtils.formatDate(publicHealthCaseDT.getDeceasedTime())	, investigationCounterNumber, nbsQuestionDT, false);
		}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(	INV147_Public_Health_Case_ACTIVITY_FROM_TIME	)){
		          populateNonRepeatData(StringUtils.formatDate(publicHealthCaseDT.getActivityFromTime())	, investigationCounterNumber, nbsQuestionDT, false);
		}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(	INV148_Public_Health_Case_day_care_ind_cd	)){
		          populateNonRepeatData(publicHealthCaseDT.getDayCareIndCd()	, investigationCounterNumber, nbsQuestionDT, false);
		}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(	INV149_Public_Health_Case_FOOD_HANDLER_IND_CD	)){
		          populateNonRepeatData(publicHealthCaseDT.getFoodHandlerIndCd()	, investigationCounterNumber, nbsQuestionDT, false);
		}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(	INV150_Public_Health_Case_outbreak_ind	)){
		          populateNonRepeatData(publicHealthCaseDT.getOutbreakInd()	, investigationCounterNumber, nbsQuestionDT, false);
		}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(	INV151_Public_Health_Case_OUTBREAK_NAME	)){
		          populateNonRepeatData(publicHealthCaseDT.getOutbreakName()	, investigationCounterNumber, nbsQuestionDT, false);
		}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(	INV152_Public_Health_Case_disease_imported_cd	)){
		          populateNonRepeatData(publicHealthCaseDT.getDiseaseImportedCd()	, investigationCounterNumber, nbsQuestionDT, false);
		}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(	INV153_Public_Health_Case_IMPORTED_COUNTRY_CD	)){
		          populateNonRepeatData(publicHealthCaseDT.getImportedCountryCd()	, investigationCounterNumber, nbsQuestionDT, false);
		}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(	INV154_Public_Health_Case_imported_state_cd	)){
		          populateNonRepeatData(publicHealthCaseDT.getImportedStateCd()	, investigationCounterNumber, nbsQuestionDT, false);
		}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(	INV155_Public_Health_Case_IMPORTED_CITY_DESC_TXT	)){
		          populateNonRepeatData(publicHealthCaseDT.getImportedCityDescTxt()	, investigationCounterNumber, nbsQuestionDT, false);
		}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(	INV156_Public_Health_Case_imported_county_cd	)){
		          populateNonRepeatData(publicHealthCaseDT.getImportedCountyCd()	, investigationCounterNumber, nbsQuestionDT, false);
		}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(	INV157_Public_Health_Case_TRANSMISSION_MODE_CD	)){
		          populateNonRepeatData(publicHealthCaseDT.getTransmissionModeCd()	, investigationCounterNumber, nbsQuestionDT, false);
		}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(	INV159_Public_Health_Case_detection_method_cd	)){
		          populateNonRepeatData(publicHealthCaseDT.getDetectionMethodCd()	, investigationCounterNumber, nbsQuestionDT, false);
		}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(	INV163_Public_Health_Case_CASE_CLASS_CD	)){
		          populateNonRepeatData(publicHealthCaseDT.getCaseClassCd()	, investigationCounterNumber, nbsQuestionDT, false);
		}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(	INV165_Public_Health_Case_mmwr_week	)){
		          populateNonRepeatData(publicHealthCaseDT.getMmwrWeek()	, investigationCounterNumber, nbsQuestionDT, false);
		}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(	INV166_Public_Health_Case_MMWR_YEAR	)){
		          populateNonRepeatData(publicHealthCaseDT.getMmwrYear()	, investigationCounterNumber, nbsQuestionDT, false);
		}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(	INV167_Public_Health_Case_txt	)){
		          populateNonRepeatData(publicHealthCaseDT.getTxt()	, investigationCounterNumber, nbsQuestionDT, false);
		}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(	INV168_Public_Health_Case_LOCAL_ID	)){
		          populateNonRepeatData(publicHealthCaseDT.getLocalId()	, investigationCounterNumber, nbsQuestionDT, false);
		}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(	INV169_Public_Health_Case_CD	)){
		          populateNonRepeatData(publicHealthCaseDT.getCd()	, investigationCounterNumber, nbsQuestionDT, false);
		}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(	INV174_Public_Health_Case_shared_ind	)){
		          populateNonRepeatData(publicHealthCaseDT.getSharedInd()	, investigationCounterNumber, nbsQuestionDT, false);
		}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(	INV178_Public_Health_Case_pregnant_ind_cd	)){
		          populateNonRepeatData(publicHealthCaseDT.getPregnantIndCd()	, investigationCounterNumber, nbsQuestionDT, false);
		}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(	INV2006_Public_Health_Case_ACTIVITY_TO_TIME	)){
		          populateNonRepeatData(StringUtils.formatDate(publicHealthCaseDT.getActivityToTime())	, investigationCounterNumber, nbsQuestionDT, false);
		}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(	INV257_Public_Health_Case_PRIORITY_CD	)){
		          populateNonRepeatData(publicHealthCaseDT.getPriorityCd()	, investigationCounterNumber, nbsQuestionDT, false);
		}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(	INV258_Public_Health_Case_INFECTIOUS_FROM_DATE	)){
		          populateNonRepeatData(StringUtils.formatDate(publicHealthCaseDT.getInfectiousFromDate())	, investigationCounterNumber, nbsQuestionDT, false);
		}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(	INV259_Public_Health_Case_INFECTIOUS_TO_DATE	)){
		          populateNonRepeatData(StringUtils.formatDate(publicHealthCaseDT.getInfectiousToDate())	, investigationCounterNumber, nbsQuestionDT, false);
		}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(	INV260_Public_Health_Case_CONTACT_INV_STATUS	)){
		          populateNonRepeatData(publicHealthCaseDT.getContactInvStatus()	, investigationCounterNumber, nbsQuestionDT, false);
		}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(	INV261_Public_Health_Case_CONTACT_INV_TXT	)){
		          populateNonRepeatData(publicHealthCaseDT.getContactInvTxt()	, investigationCounterNumber, nbsQuestionDT, false);
		}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(	NBS012_Public_Health_Case_SHARED_IND	)){
		          populateNonRepeatData(publicHealthCaseDT.getSharedInd()	, investigationCounterNumber, nbsQuestionDT, false);
		}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(	NBS055_Public_Health_Case_PRIORITY_CD	)){
		          populateNonRepeatData(publicHealthCaseDT.getPriorityCd()	, investigationCounterNumber, nbsQuestionDT, false);
		}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(	NBS056_Public_Health_Case_INFECTIOUS_FROM_DATE	)){
		          populateNonRepeatData(StringUtils.formatDate(publicHealthCaseDT.getInfectiousFromDate())	, investigationCounterNumber, nbsQuestionDT, false);
		}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(	NBS057_Public_Health_Case_INFECTIOUS_TO_DATE	)){
		          populateNonRepeatData(StringUtils.formatDate(publicHealthCaseDT.getInfectiousToDate())	, investigationCounterNumber, nbsQuestionDT, false);
		}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(	NBS058_Public_Health_Case_CONTACT_INV_STATUS	)){
		          populateNonRepeatData(publicHealthCaseDT.getContactInvStatus()	, investigationCounterNumber, nbsQuestionDT, false);
		}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(	NBS059_Public_Health_Case_CONTACT_INV_TXT	)){
		          populateNonRepeatData(publicHealthCaseDT.getContactInvTxt()	, investigationCounterNumber, nbsQuestionDT, false);
		}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(	NBS110_Public_Health_Case_REFERRAL_BASIS_CD	)){
				populateNonRepeatData(publicHealthCaseDT.getReferralBasisCd()	, investigationCounterNumber, nbsQuestionDT, false);
		}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(	NBS115_Public_Health_Case_CURR_PROCESS_STATE_CD	)){
		          populateNonRepeatData(publicHealthCaseDT.getCurrProcessStateCd()	, investigationCounterNumber, nbsQuestionDT, false);
		}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(	SUM100_Public_Health_Case_rpt_cnty_cd	)){
		          populateNonRepeatData(publicHealthCaseDT.getRptCntyCd()	, investigationCounterNumber, nbsQuestionDT, false);
		}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(	SUM101_Public_Health_Case_mmwr_year	)){
		          populateNonRepeatData(publicHealthCaseDT.getMmwrYear()	, investigationCounterNumber, nbsQuestionDT, false);
		}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(	SUM102_Public_Health_Case_mmwr_week	)){
		          populateNonRepeatData(publicHealthCaseDT.getMmwrWeek()	, investigationCounterNumber, nbsQuestionDT, false);
		}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(	SUM105_Public_Health_Case_txt	)){
		          populateNonRepeatData(publicHealthCaseDT.getTxt()	, investigationCounterNumber, nbsQuestionDT, false);
		}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(	SUM106_Public_Health_Case_cd	)){
		          populateNonRepeatData(publicHealthCaseDT.getCd()	, investigationCounterNumber, nbsQuestionDT, false);
		}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(	SUM113_Public_Health_Case_rpt_form_cmplt_time	)){
		          populateNonRepeatData(StringUtils.formatDate(publicHealthCaseDT.getRptFormCmpltTime())	, investigationCounterNumber, nbsQuestionDT, false);
		}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(	SUM115_Public_Health_Case_count_interval_cd	)){
		          populateNonRepeatData(publicHealthCaseDT.getCountIntervalCd()	, investigationCounterNumber, nbsQuestionDT, false);
		}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(	SUM116_Public_Health_Case_case_class_cd	)){
	          populateNonRepeatData(publicHealthCaseDT.getCaseClassCd()	, investigationCounterNumber, nbsQuestionDT, false);
		} 
	}catch (Exception e) {
		throw new NEDSSAppException("The nbsQuestionDT is not valid as the data location is set null here", e);
	}

		
	}
	/**
	 * Initially Assigned Interview is filled in when the Interviewer is saved.
	 * If interviwer is there, initial interviewer is there.
	 * Some specific logic about reassignment. If not reassigned, we don't see Date Reassigned.
	 * @param theProxyVO 
	 * @param investigationCounter
	 * @param interviewer
	 * @param initInterviewerOfPHC
	 * @throws NEDSSAppException
	 */
	private static void mapInterviewerData(PageActProxyVO theProxyVO, int investigationCounter, PersonVO interviewer, PersonVO initInterviewerOfPHC) throws NEDSSAppException{
		

		//This doesn't depend on interviewer reassigned or not
		
		 PersonVO supPersonVO =PageLoadUtil.getPersonVO("CASupervisorOfPHC", theProxyVO);
		 if(supPersonVO != null){
			 formSpecificQuestionAnswerMap.put("NBS190_"+investigationCounter+CODED_VALUE,supPersonVO.getEntityIdDT_s(0).getRootExtensionTxt());
		 }
		 
		 PersonVO closureInvestgrOfPHC =PageLoadUtil.getPersonVO("ClosureInvestgrOfPHC", theProxyVO);
		 if(closureInvestgrOfPHC != null){
			 formSpecificQuestionAnswerMap.put("NBS197_"+investigationCounter+CODED_VALUE,closureInvestgrOfPHC.getEntityIdDT_s(0).getRootExtensionTxt());
		 }
		 
		 
		if (interviewer == null || initInterviewerOfPHC == null)
			return;
		
		try {
			
			//if Interview has been reassigned since initial assignment to another interviewer
			if(theProxyVO.getPublicHealthCaseVO().getTheCaseManagementDT().getInitInterviewAssignedDate() != null
					&& theProxyVO.getPublicHealthCaseVO().getTheCaseManagementDT().getInterviewAssignedDate() != null
					&& ( theProxyVO.getPublicHealthCaseVO().getTheCaseManagementDT().getInitInterviewAssignedDate()
										  .compareTo(theProxyVO.getPublicHealthCaseVO().getTheCaseManagementDT().getInterviewAssignedDate()) != 0
						|| initInterviewerOfPHC.getThePersonDT().getPersonParentUid().compareTo(interviewer.getThePersonDT().getPersonParentUid())!=0 )){

				 String initInterviewerId = initInterviewerOfPHC.getEntityIdDT_s(0).getRootExtensionTxt();
				 if(formSpecificQuestionMap.containsKey( "NBS188"+SEPERATOR+investigationCounter+CODED_VALUE )){
						formSpecificQuestionAnswerMap.put("NBS188"+SEPERATOR+investigationCounter+CODED_VALUE , checkNull(initInterviewerId));
				 }
				 if(formSpecificQuestionMap.containsKey( NBS189_CASE_MANAGEMENT_INIT_INTERVIEW_ASSIGNED_DATE+SEPERATOR+investigationCounter )){
						formSpecificQuestionAnswerMap.put(NBS189_CASE_MANAGEMENT_INIT_INTERVIEW_ASSIGNED_DATE+SEPERATOR+investigationCounter, StringUtils.formatDate(theProxyVO.getPublicHealthCaseVO().getTheCaseManagementDT().getInitInterviewAssignedDate()));
				 }


				 String interviewerId = interviewer.getEntityIdDT_s(0).getRootExtensionTxt();
				 if(interviewerId != null){
					 if(formSpecificQuestionMap.containsKey( "NBS186"+SEPERATOR+investigationCounter+CODED_VALUE)){
							formSpecificQuestionAnswerMap.put("NBS186"+SEPERATOR+investigationCounter+CODED_VALUE , checkNull(interviewerId));
					 }
					 if(formSpecificQuestionMap.containsKey( NBS187_CASE_MANAGEMENT_INTERVIEW_ASSIGNED_DATE+SEPERATOR+investigationCounter)){
							formSpecificQuestionAnswerMap.put(NBS187_CASE_MANAGEMENT_INTERVIEW_ASSIGNED_DATE+SEPERATOR+investigationCounter, StringUtils.formatDate(theProxyVO.getPublicHealthCaseVO().getTheCaseManagementDT().getInterviewAssignedDate()));
					 }
					 
				 }


					
			} else { //not been reassigned

				String interviewerId = interviewer.getEntityIdDT_s(0).getRootExtensionTxt();
				mappedDiagnosis.put("NBS188"+SEPERATOR+investigationCounter+CODED_VALUE, checkNull(interviewerId));
					 
			  if(formSpecificQuestionMap.containsKey( "NBS188"+SEPERATOR+investigationCounter+CODED_VALUE)){
							formSpecificQuestionAnswerMap.put("NBS188"+SEPERATOR+investigationCounter+CODED_VALUE,  checkNull(interviewerId));
			  } 
				
			  if(theProxyVO.getPublicHealthCaseVO().getTheCaseManagementDT().getInterviewAssignedDate() != null){
					 if(formSpecificQuestionMap.containsKey( NBS189_CASE_MANAGEMENT_INIT_INTERVIEW_ASSIGNED_DATE+SEPERATOR+investigationCounter )){
							formSpecificQuestionAnswerMap.put(NBS189_CASE_MANAGEMENT_INIT_INTERVIEW_ASSIGNED_DATE+SEPERATOR+investigationCounter ,  StringUtils.formatDate(theProxyVO.getPublicHealthCaseVO().getTheCaseManagementDT().getInterviewAssignedDate()));
					 }
				}
		   } //else not reassigned			 
			 
		} catch (Exception e) {
			logger.error("mapCommonEntities NedssAppException thrown"+e);
			throw new NEDSSAppException("mapCommonEntities Exception thrown", e);
		}
		return;
	}
	/**
	 * Get values that map to the CaseManagement record if present.
	 * @param nbsQuestionDT
	 * @param investigationCounterNumber
	 * @param caseManagementDT
	 * @throws Exception
	 */
	private static void mapValueForCaseManagementDT(NbsQuestionMetadata nbsQuestionDT, int investigationCounterNumber, CaseManagementDT caseManagementDT) throws Exception {
        try {
        	if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(NBS155_CASE_MANAGEMENT_SUBJ_HEIGHT)){
        		populateNonRepeatData(caseManagementDT.getSubjHeight(), investigationCounterNumber, nbsQuestionDT, false);
        	}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(NBS156_CASE_MANAGEMENT_SUBJ_SIZE_BUILD)){
        		populateNonRepeatData(caseManagementDT.getSubjSizeBuild(), investigationCounterNumber, nbsQuestionDT, false);
        	}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(NBS157_CASE_MANAGEMENT_SUBJ_HAIR)){
        		populateNonRepeatData(caseManagementDT.getSubjHair(), investigationCounterNumber, nbsQuestionDT, false);
        	}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(NBS158_CASE_MANAGEMENT_SUBJ_COMPLEXION)){
        		populateNonRepeatData(caseManagementDT.getSubjComplexion(), investigationCounterNumber, nbsQuestionDT, false);
        	}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(NBS159_CASE_MANAGEMENT_SUBJ_OTH_IDNTFYNG_INFO)){
        		populateNonRepeatData(caseManagementDT.getSubjOthIdntfyngInfo(), investigationCounterNumber, nbsQuestionDT, false);
        	}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(NBS111_CASE_MANAGEMENT_INITIATING_AGNCY)){
        		populateNonRepeatData(caseManagementDT.getInitiatingAgncy(), investigationCounterNumber, nbsQuestionDT, false);
        	}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(NBS112_CASE_MANAGEMENT_OOJ_INITG_AGNCY_RECD_DATE)){
        		populateNonRepeatData(StringUtils.formatDate(caseManagementDT.getOojInitgAgncyRecdDate()), investigationCounterNumber, nbsQuestionDT, false);
        	}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(NBS113_CASE_MANAGEMENT_OOJ_INITG_AGNCY_OUTC_DUE_DATE)){
        		populateNonRepeatData(StringUtils.formatDate(caseManagementDT.getOojInitgAgncyOutcDueDate()), investigationCounterNumber, nbsQuestionDT, false);
        	}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(NBS114_CASE_MANAGEMENT_OOJ_INITG_AGNCY_OUTC_SNT_DATE)){
        		populateNonRepeatData(StringUtils.formatDate(caseManagementDT.getOojInitgAgncyOutcSntDate()), investigationCounterNumber, nbsQuestionDT, false);
        	}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(NBS160_CASE_MANAGEMENT_FIELD_RECORD_NUMBER)){
        		populateNonRepeatData(caseManagementDT.getFieldRecordNumber(), investigationCounterNumber, nbsQuestionDT, false);
        	}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(NBS191_CASE_MANAGEMENT_EPI_LINK_ID)){
        		populateNonRepeatData(caseManagementDT.getEpiLinkId(), investigationCounterNumber, nbsQuestionDT, false);
        	}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(NBS140_CASE_MANAGEMENT_INIT_FOLL_UP)){
        		populateNonRepeatData(caseManagementDT.getInitFollUp(), investigationCounterNumber, nbsQuestionDT, false);
        	}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(NBS141_CASE_MANAGEMENT_INIT_FOLL_UP_CLOSED_DATE)){
        		populateNonRepeatData(StringUtils.formatDate(caseManagementDT.getInitFollUpClosedDate()), investigationCounterNumber, nbsQuestionDT, false);
        	}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(NBS142_CASE_MANAGEMENT_INTERNET_FOLL_UP)){
        		populateNonRepeatData(caseManagementDT.getInternetFollUp(), investigationCounterNumber, nbsQuestionDT, false);
        	}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(NBS144_CASE_MANAGEMENT_INIT_FOLL_UP_CLINIC_CODE)){
        		populateNonRepeatData(caseManagementDT.getInitFollUpClinicCode(), investigationCounterNumber, nbsQuestionDT, false);
        	}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(NBS146_CASE_MANAGEMENT_SURV_ASSIGNED_DATE)){
        		populateNonRepeatData(StringUtils.formatDate(caseManagementDT.getSurvAssignedDate()), investigationCounterNumber, nbsQuestionDT, false);
        	}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(NBS147_CASE_MANAGEMENT_SURV_CLOSED_DATE)){
        		populateNonRepeatData(StringUtils.formatDate(caseManagementDT.getSurvClosedDate()), investigationCounterNumber, nbsQuestionDT, false);
        	}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(NBS148_CASE_MANAGEMENT_SURV_PROVIDER_CONTACT)){
        		populateNonRepeatData(caseManagementDT.getSurvProviderContact(), investigationCounterNumber, nbsQuestionDT, false);
        	}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(NBS149_CASE_MANAGEMENT_SURV_PROV_EXM_REASON)){
        		if(investigationCounterNumber==1)
        			populateNonRepeatData(caseManagementDT.getSurvProvExmReason(), 1, nbsQuestionDT, false);
        	}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(NBS150_CASE_MANAGEMENT_SURV_PROV_DIAGNOSIS)){
        		populateNonRepeatData(caseManagementDT.getSurvProvDiagnosis(), investigationCounterNumber, nbsQuestionDT, false);
        	}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(NBS151_CASE_MANAGEMENT_SURV_PATIENT_FOLL_UP)){
        		populateNonRepeatData(caseManagementDT.getSurvPatientFollUp(), investigationCounterNumber, nbsQuestionDT, false);
        	}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(NBS143_CASE_MANAGEMENT_INIT_FOLL_UP_NOTIFIABLE)){
        		populateNonRepeatData(caseManagementDT.getInitFollUpNotifiable(), investigationCounterNumber, nbsQuestionDT, false);
        	}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(NBS167_CASE_MANAGEMENT_FLD_FOLL_UP_NOTIFICATION_PLAN)){
        		populateNonRepeatData(caseManagementDT.getFldFollUpNotificationPlan(), investigationCounterNumber, nbsQuestionDT, false);
        	}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(NBS177_CASE_MANAGEMENT_ACT_REF_TYPE_CD)){
        		populateNonRepeatData(caseManagementDT.getActRefTypeCd(), investigationCounterNumber, nbsQuestionDT, false);
        	}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(NBS162_CASE_MANAGEMENT_FOLL_UP_ASSIGNED_DATE)){
        		populateNonRepeatData(StringUtils.formatDate(caseManagementDT.getFollUpAssignedDate()), investigationCounterNumber, nbsQuestionDT, false);
        	}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(NBS164_CASE_MANAGEMENT_INIT_FOLL_UP_ASSIGNED_DATE)){
        		populateNonRepeatData(StringUtils.formatDate(caseManagementDT.getInitFollUpAssignedDate()), investigationCounterNumber, nbsQuestionDT, false);
        	}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(NBS165_CASE_MANAGEMENT_FLD_FOLL_UP_PROV_EXM_REASON)){
        		populateNonRepeatData(caseManagementDT.getFldFollUpProvExmReason(), investigationCounterNumber, nbsQuestionDT, false);
        	}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(NBS166_CASE_MANAGEMENT_FLD_FOLL_UP_PROV_DIAGNOSIS)){
        		populateNonRepeatData(caseManagementDT.getFldFollUpProvDiagnosis(), investigationCounterNumber, nbsQuestionDT, false);
        	}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(NBS168_CASE_MANAGEMENT_FLD_FOLL_UP_EXPECTED_IN)){
        		populateNonRepeatData(caseManagementDT.getFldFollUpExpectedIn(), investigationCounterNumber, nbsQuestionDT, false);
        	}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(NBS169_CASE_MANAGEMENT_FLD_FOLL_UP_EXPECTED_DATE)){
        		populateNonRepeatData(StringUtils.formatDate(caseManagementDT.getFldFollUpExpectedDate()), investigationCounterNumber, nbsQuestionDT, false);
        	}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(NBS170_CASE_MANAGEMENT_FLD_FOLL_UP_EXAM_DATE)){
        		populateNonRepeatData(StringUtils.formatDate(caseManagementDT.getFldFollUpExamDate()), investigationCounterNumber, nbsQuestionDT, false);
        	}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(NBS173_CASE_MANAGEMENT_FLD_FOLL_UP_DISPO)){
        		populateNonRepeatData(caseManagementDT.getFldFollUpDispo(), investigationCounterNumber, nbsQuestionDT, false);
        	}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(NBS174_CASE_MANAGEMENT_FLD_FOLL_UP_DISPO_DATE)){
        		populateNonRepeatData(StringUtils.formatDate(caseManagementDT.getFldFollUpDispoDate()), investigationCounterNumber, nbsQuestionDT, false);
        	}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(NBS178_CASE_MANAGEMENT_FLD_FOLL_UP_INTERNET_OUTCOME)){
        		populateNonRepeatData(caseManagementDT.getFldFollUpInternetOutcome(), investigationCounterNumber, nbsQuestionDT, false);
        	}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(NBS179_CASE_MANAGEMENT_OOJ_AGENCY)){
        		populateNonRepeatData(caseManagementDT.getOojAgency(), investigationCounterNumber, nbsQuestionDT, false);
        	}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(NBS180_CASE_MANAGEMENT_OOJ_NUMBER)){
        		populateNonRepeatData(caseManagementDT.getOojNumber(), investigationCounterNumber, nbsQuestionDT, false);
        	}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(NBS181_CASE_MANAGEMENT_OOJ_DUE_DATE)){
        		populateNonRepeatData(StringUtils.formatDate(caseManagementDT.getOojDueDate()), investigationCounterNumber, nbsQuestionDT, false);
        	}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(NBS182_CASE_MANAGEMENT_FIELD_FOLL_UP_OOJ_OUTCOME)){
        		populateNonRepeatData(caseManagementDT.getFieldFollUpOojOutcome(), investigationCounterNumber, nbsQuestionDT, false);
        	}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(NBS187_CASE_MANAGEMENT_INTERVIEW_ASSIGNED_DATE)){
        		populateNonRepeatData(StringUtils.formatDate(caseManagementDT.getInterviewAssignedDate()), investigationCounterNumber, nbsQuestionDT, false);
        	}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(NBS189_CASE_MANAGEMENT_INIT_INTERVIEW_ASSIGNED_DATE)){
        		populateNonRepeatData(StringUtils.formatDate(caseManagementDT.getInitInterviewAssignedDate()), investigationCounterNumber, nbsQuestionDT, false);
        	}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(NBS192_CASE_MANAGEMENT_PAT_INTV_STATUS_CD)){
        		populateNonRepeatData(caseManagementDT.getPatIntvStatusCd(), investigationCounterNumber, nbsQuestionDT, false);
        	}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(NBS196_CASE_MANAGEMENT_CASE_CLOSED_DATE)){
        		populateNonRepeatData(StringUtils.formatDate(caseManagementDT.getCaseClosedDate()), investigationCounterNumber, nbsQuestionDT, false);
        	}else if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(NBS153_CASE_MANAGEMENT_STATUS_900)){
        		populateNonRepeatData(caseManagementDT.getStatus900(), investigationCounterNumber, nbsQuestionDT, false);
        	}


		}catch (Exception e) {
			throw new NEDSSAppException("The nbsQuestionDT is not valid as the data location is set null here", e);
		}
	}
	
	
	private static void mapValueForActIdDT( Collection<Object> actIdColl, NbsQuestionMetadata nbsQuestionDT, int investigationCounterNumber) throws Exception {
        try {		
			if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(NBS173_ACT_ID)){
				Iterator<Object> itr = actIdColl.iterator();

				while (itr.hasNext()) {

					ActIdDT dt = (ActIdDT) itr.next();
					String type = dt.getTypeCd();
					if(type!=null && type.equalsIgnoreCase("STATE")){

						String answerTxt = dt.getRootExtensionTxt();
					
						populateNonRepeatData(answerTxt, investigationCounterNumber, nbsQuestionDT, false);
					}
				}
			}

			
			if(nbsQuestionDT.getQuestionIdentifier().equalsIgnoreCase(DEM126)){
				Iterator<Object> itr = actIdColl.iterator();

				while (itr.hasNext()) {

					ActIdDT dt = (ActIdDT) itr.next();
					String type = dt.getTypeCd();
					if(type!=null && type.equalsIgnoreCase("STATE")){

						String answerTxt = dt.getRootExtensionTxt();
					
						populateNonRepeatData(answerTxt, investigationCounterNumber, nbsQuestionDT, false);
					}
				}
			}

			
			

		}catch (Exception e) {
			throw new NEDSSAppException("The nbsQuestionDT is not valid as the data location is set null here", e);
		}
	}
	
	/**
	 * Return the Getter Name for a field.
	 * @param nbsQuestionDT
	 * @param prefix
	 * @return
	 * @throws Exception
	 */
	public static String findDynamicFieldName(NbsQuestionMetadata nbsQuestionDT, String prefix )
			throws Exception {
		String dataLoc="";
		String getterName="";
		try {
			Iterator<Object> iter = questionMap.keySet().iterator();
			while (iter.hasNext()) {
				Object key = iter.next();
				if (answerMap.get(key) != null) {

					NbsQuestionMetadata dt = (NbsQuestionMetadata) questionMap
							.get(key);
					dataLoc = dt.getDataLocation() == null ? "" : dt.getDataLocation();
					if (dataLoc.toUpperCase().startsWith(prefix + ".")) {
						int pos = dataLoc.indexOf(".");
						if (pos == -1) {
							throw new Exception("Data Location for: "+ dt.getQuestionIdentifier()+ " cannot be without '.' between Table Name and Table Column");
						}
						String colNm = dataLoc.substring(pos + 1);
						
						StringBuffer sb = new StringBuffer("get");
						StringTokenizer st = new StringTokenizer(colNm, "_");
						while (st.hasMoreTokens()) {
							String s = st.nextToken();
							s = s.substring(0, 1).toUpperCase()
									+ s.substring(1).toLowerCase();
							sb.append(s);
							getterName= sb.toString();
						}
					} else {
						continue;
					}

				}
			}
		} catch (Exception e) {
			logger.error("findDynamicFieldName: Error while finding gettname and exception is raised: "+ e);
			throw new Exception(e);
		}
		return getterName;
	}
	
	
	/**
	 * getTreatmentCollection: Process treatments.
	 * @param req
	 */
	private static void getTreatmentCollection( HttpServletRequest req) {
		try {
			populateTreatmentValuesWithData(proxyVO,req.getSession());
			if(treatmentIndex<5 && coProxyVO != null) 
				populateTreatmentValuesWithData(coProxyVO,req.getSession());
		} catch (Exception e) {
			logger.debug("Exception in getTreatmentCollection- CIR - getTreatments " + e.getMessage());
		}
	}

	/**
	 * processDrugsPhenotypic: this method will reorganize the drugs displayed in the PDF form to match the prepopulated drug names.
	 * @param formSpecificQuestionMap
	 * @param formSpecificQuestionAnswerMap
	 */
	private static void processDrugsPhenotypic(Map<String,String> formSpecificQuestionAnswerMap){
		
	
		Map<String, String> drugsNewOrder = new HashMap<String, String>();
		int index=24;//the number of row where we start populating values in any order
		int totalNumberOfRows = 37;
		
		for (int i = 1; i<totalNumberOfRows;i++){
			
			if(formSpecificQuestionAnswerMap.get("LABAST6_R"+i+"_CDT")!=null){
				String valueDrug = formSpecificQuestionAnswerMap.get("LABAST6_R"+i+"_CDT");
				String dateCollected = formSpecificQuestionAnswerMap.get("LABAST5_R"+i);
				String dateReported = formSpecificQuestionAnswerMap.get("LABAST14_R"+i);
				String specimenSource = formSpecificQuestionAnswerMap.get("LABAST3_R"+i+"_CDT");
				String result = formSpecificQuestionAnswerMap.get("LABAST8_R"+i+"_CD");
				String testMethod = formSpecificQuestionAnswerMap.get("LABAST7_R"+i+"_CDT");
				
				String position = LABAST6Drugs.get(valueDrug);
				
				
				if(position!=null && !position.isEmpty())//it has a value
					if(drugsNewOrder.get("LABAST6_R"+position+"_CDT")!=null)//lets checks it hasn't been added already in the map, and it is a duplicate that needs to go down
						position=null;
							
				if(position==null || position.isEmpty()){
					position = index+"";
					
					index++;
				}
				
				
				drugsNewOrder.put("LABAST6_R"+position+"_CDT",valueDrug);
				drugsNewOrder.put("LABAST5_R"+position,dateCollected);
				drugsNewOrder.put("LABAST14_R"+position,dateReported);
				drugsNewOrder.put("LABAST3_R"+position+"_CDT",specimenSource);
				drugsNewOrder.put("LABAST8_R"+position+"_CD",result);
				drugsNewOrder.put("LABAST7_R"+position+"_CDT",testMethod);
			
			}
			
			
			
			
			

		}
		
		
		//remove all the existing answers:
		
		for(int j = 1; j<=totalNumberOfRows; j++){
			
			String valueDrug = "LABAST6_R"+j+"_CDT";
			String dateCollected = "LABAST5_R"+j;
			String dateReported = "LABAST14_R"+j;
			String specimenSource = "LABAST3_R"+j+"_CDT";
			String result = "LABAST8_R"+j+"_CD";
			String testMethod = "LABAST7_R"+j+"_CDT";
			
			
			
			formSpecificQuestionAnswerMap.remove(valueDrug);
			formSpecificQuestionAnswerMap.remove(dateCollected);
			formSpecificQuestionAnswerMap.remove(dateReported);
			formSpecificQuestionAnswerMap.remove(specimenSource);
			formSpecificQuestionAnswerMap.remove(result);
			formSpecificQuestionAnswerMap.remove(testMethod);
			
			
		}
			
		
			
			
			
		for (Map.Entry<String,String> entry : drugsNewOrder.entrySet()) {
			formSpecificQuestionAnswerMap.put(entry.getKey(), entry.getValue());
				
		}
			
		
	}

	
	
	/**
	 * processDrugsMDR: this method will reorganize the drugs displayed in the PDF form to match the prepopulated drug names.
	 * @param formSpecificQuestionMap
	 * @param formSpecificQuestionAnswerMap
	 */
	private static void processDrugsMDR(Map<String,String> formSpecificQuestionAnswerMap){
		
	
		Map<String, String> drugsNewOrder = new HashMap<String, String>();
		int index=24;//the number of row where we start populating values in any order
		int totalNumberOfRows = 30;
		
		for (int i = 1; i<=totalNumberOfRows;i++){
			
			if(formSpecificQuestionAnswerMap.get("INV1158_R"+i+"_CDT")!=null){
				String valueDrug = formSpecificQuestionAnswerMap.get("INV1158_R"+i+"_CDT");
				String lengthAdministered = formSpecificQuestionAnswerMap.get("INV1159_R"+i+"_CD");
			
				
				String position = LABAST6Drugs.get(valueDrug);
				
				
				if(position!=null && !position.isEmpty())//it has a value
					if(drugsNewOrder.get("INV1158_R"+position+"_CDT")!=null)//lets checks it hasn't been added already in the map, and it is a duplicate that needs to go down
						position=null;
							
				if(position==null || position.isEmpty()){
					position = index+"";
					
					index++;
				}
				
				
				drugsNewOrder.put("INV1158_R"+position+"_CDT",valueDrug);
				drugsNewOrder.put("INV1159_R"+position+"_CD",lengthAdministered);
			
			}
			
			
			
			
			

		}
		
		
		//remove all the existing answers:
		
		for(int j = 1; j<=totalNumberOfRows; j++){
			
			String valueDrug = "INV1158_R"+j+"_CDT";
			String lengthAdministered = "INV1159_R"+j+"_CD";
			
			formSpecificQuestionAnswerMap.remove(valueDrug);
			formSpecificQuestionAnswerMap.remove(lengthAdministered);
			
		}
				
			
			
			
		for (Map.Entry<String,String> entry : drugsNewOrder.entrySet()) {
			formSpecificQuestionAnswerMap.put(entry.getKey(), entry.getValue());
				
		}
			
	
		
	}
	
	
	
	
	/**
	 * processSideEffects: this method will reorganize the side effects displayed in the PDF form to match the prepopulated side effect names.
	 * @param formSpecificQuestionMap
	 * @param formSpecificQuestionAnswerMap
	 */
	private static void processSideEffects(Map<String,String> formSpecificQuestionAnswerMap){
		
	
		Map<String, String> sideEffectsNewOrder = new HashMap<String, String>();
		int index=12;//the number of row where we start populating values in any order
		int totalNumberOfRows = 14;
		
		for (int i = 1; i<=totalNumberOfRows;i++){
			//42563_7_CD_R1_CDT name
			
			//INV1164_R1_CD experienced
			
			//INV1163_R1_CD when
			
			
			if(formSpecificQuestionAnswerMap.get("42563_7_CD_R"+i+"_CDT")!=null){
				String valueSideEffect = formSpecificQuestionAnswerMap.get("42563_7_CD_R"+i+"_CDT");
				String experienced = formSpecificQuestionAnswerMap.get("INV1164_R"+i+"_CD");
				String when = formSpecificQuestionAnswerMap.get("INV1163_R"+i+"_CD");
				
				String position = code42563_7sideEffects.get(valueSideEffect);
				
				
				if(position!=null && !position.isEmpty())//it has a value
					if(sideEffectsNewOrder.get("42563_7_CD_R"+position+"_CDT")!=null)//lets checks it hasn't been added already in the map, and it is a duplicate that needs to go down
						position=null;
							
				if(position==null || position.isEmpty()){
					position = index+"";
					
					index++;
				}
				
				
				sideEffectsNewOrder.put("42563_7_CD_R"+position+"_CDT",valueSideEffect);
				sideEffectsNewOrder.put("INV1164_R"+position+"_CD",experienced);
				sideEffectsNewOrder.put("INV1163_R"+position+"_CD",when);
			}
				

		}
		
			
		//remove all the existing answers:
		
		for(int j = 1; j<=totalNumberOfRows; j++){
			
			String valueSideEffect = "42563_7_CD_R"+j+"_CDT";
			String experienced = "INV1164_R"+j+"_CD";
			String when = "INV1163_R"+j+"_CD";
			
			formSpecificQuestionAnswerMap.remove(valueSideEffect);
			formSpecificQuestionAnswerMap.remove(experienced);
			formSpecificQuestionAnswerMap.remove(when);
			
		}
			
		for (Map.Entry<String,String> entry : sideEffectsNewOrder.entrySet()) {
			formSpecificQuestionAnswerMap.put(entry.getKey(), entry.getValue());
				
		}
			
		
	}
	
	
	
	/**
	 * processEverWorkedAs: processes Ever Worked as to being able to multi select
	 * @param formSpecificQuestionMap
	 * @param formSpecificQuestionAnswerMap
	 */
	private static void processEverWorkedAs( Map<String,String> formSpecificQuestionMap, Map<String,String> formSpecificQuestionAnswerMap) {
		try {
			String answer = formSpecificQuestionAnswerMap.get(INV1276_CD);
			if(answer!=null){
				String[] answers = answer.split(", ");
				
				for(int i=0; i<answers.length; i++){
					String ans = answers[i];
					String code = "";
					if(ans!=null)
						code = INV1276EverWorkedAs.get(ans);
						String key = INV1276+"_"+code+"_CD";
						if(formSpecificQuestionMap.get(key)!=null)
							formSpecificQuestionAnswerMap.put(key,ans);
				}
			}
			
		} catch (Exception e) {
			logger.debug("Exception in processEverWorkedAs: " + e.getMessage());
		}
	}

	
	/**
	 * processWhatCriteriaMet: processes 26. Case Meets Binational Reporting Criteria, Which criteria were met section to being able to multi select 
	 * @param formSpecificQuestionMap
	 * @param formSpecificQuestionAnswerMap
	 */
	
	private static void processWhatCriteriaMet( Map<String,String> formSpecificQuestionMap, Map<String,String> formSpecificQuestionAnswerMap) {
		try {
			String answer = formSpecificQuestionAnswerMap.get(INV515_CD);
			if(answer!=null){
				String[] answers = answer.split(", ");
				
				for(int i=0; i<answers.length; i++){
					String ans = answers[i];
					String code = "";
					if(ans!=null)
						code = INV515Criteria.get(ans);
						String key = INV515+"_"+code+"_CD";
						if(formSpecificQuestionMap.get(key)!=null)
							formSpecificQuestionAnswerMap.put(key,ans);
				}
	
			}

		} catch (Exception e) {
			logger.debug("Exception in processWhatCriteriaMet: " + e.getMessage());
		}
	}
	
	
	/**
	 * processPrimarySites: read information for primary sites check boxes, primary sites Other Specify and secondaty sites (any Site not showing as part of the check boxes)
	 * @param formSpecificQuestionMap
	 * @param formSpecificQuestionAnswerMap
	 */
	private static void processPrimarySites( Map<String,String> formSpecificQuestionMap, Map<String,String> formSpecificQuestionAnswerMap) {
		try {
			String answer = formSpecificQuestionAnswerMap.get(INV1133_CD);
			String answersSecondarySites = "";
			
			if(answer!=null){
				String[] answers = answer.split(", ");
				
				for(int i=0; i<answers.length; i++){
					String ans = answers[i];
					String code = "";
					if(ans!=null)
						code = INV1133PrimarySites.get(ans);
						String key = INV1133+"_"+code+"_CD";
						if(formSpecificQuestionMap.get(key)!=null)
							formSpecificQuestionAnswerMap.put(key,ans);
						else{ //Secondary site
						
							String ansTranslated = INV1133SecondarySites.get(ans);//If we translate this from mapcode, we are unable to select checkboxes because we need the codes.
							answersSecondarySites+=ansTranslated+", ";
						}
				}
				
				
				if(formSpecificQuestionMap.get(INV1133_SecondarySites)!=null)
					formSpecificQuestionAnswerMap.put(INV1133_SecondarySites,answersSecondarySites);
			}

		} catch (Exception e) {
			logger.debug("Exception in processPrimarySites: " + e.getMessage());
		}
	}
	
	/**
	 * processMovedWhere: processes 38. Moved During Therapy (If Yes, moved to where(select all that apply)) section to being able to multi select 
	 * @param formSpecificQuestionMap
	 * @param formSpecificQuestionAnswerMap
	 */
	
	private static void processMovedWhere( Map<String,String> formSpecificQuestionMap, Map<String,String> formSpecificQuestionAnswerMap) {
		try {
			String answer = formSpecificQuestionAnswerMap.get(INV1152_CD);
		//	String answersSecondarySites = "";
			
			if(answer!=null){
				String[] answers = answer.split(", ");
				
				for(int i=0; i<answers.length; i++){
					String ans = answers[i];
					String code = "";
					if(ans!=null)
						code = INV1152MovedToWhere.get(ans);
						String key = INV1152+"_"+code+"_CD";
						if(formSpecificQuestionMap.get(key)!=null)
							formSpecificQuestionAnswerMap.put(key,ans);
						
				}
				
			}

		} catch (Exception e) {
			logger.debug("Exception in processMovedWhere: " + e.getMessage());
		}
	}
	
	/**
	 * processReasonTbTherapyExtended: processes 41. Reason TB Disease Therapy Extended >12 Months, if applicable section from RVCT form to being able to multi select
	 * @param formSpecificQuestionMap
	 * @param formSpecificQuestionAnswerMap
	 */
	
	private static void processReasonTbTherapyExtended( Map<String,String> formSpecificQuestionMap, Map<String,String> formSpecificQuestionAnswerMap) {
		try {
			String answer = formSpecificQuestionAnswerMap.get(INV1141_CD);
			
			if(answer!=null){
				String[] answers = answer.split(", ");
				
				for(int i=0; i<answers.length; i++){
					String ans = answers[i];
					String code = "";
					if(ans!=null)
						code = INV1141ReasonTherapyExtended.get(ans);
						String key = INV1141+"_"+code+"_CD";
						if(formSpecificQuestionMap.get(key)!=null)
							formSpecificQuestionAnswerMap.put(key,ans);			
				}
			}

		} catch (Exception e) {
			logger.debug("Exception in processReasonTbTherapyExtended: " + e.getMessage());
		}
	}
	
	
	/**
	 * processTreatmentAdministration: processes 42. treatment adminitration section from RVCT form to being able to multi select
	 * @param formSpecificQuestionMap
	 * @param formSpecificQuestionAnswerMap
	 */
	private static void processTreatmentAdministration( Map<String,String> formSpecificQuestionMap, Map<String,String> formSpecificQuestionAnswerMap) {
		try {
			String answer = formSpecificQuestionAnswerMap.get(code55753_8_CD);
			//String answersSecondarySites = "";
			
			if(answer!=null){
				String[] answers = answer.split(", ");
				
				for(int i=0; i<answers.length; i++){
					String ans = answers[i];
					String code = "";
					if(ans!=null)
						code = code55753TreatmentAdministration.get(ans);
						String key = code55753_8+"_"+code+"_CD";
						if(formSpecificQuestionMap.get(key)!=null)
							formSpecificQuestionAnswerMap.put(key,ans);
				}
			}

		} catch (Exception e) {
			logger.debug("Exception in processTreatmentAdministration: " + e.getMessage());
		}
	}
	
	
	private static void processTreatmentAdministrationLTBI( Map<String,String> formSpecificQuestionMap, Map<String,String> formSpecificQuestionAnswerMap) {
		try {
			String answer = formSpecificQuestionAnswerMap.get(code55753_8B_CD);
			//String answersSecondarySites = "";
			
			if(answer!=null){
				String[] answers = answer.split(", ");
				
				for(int i=0; i<answers.length; i++){
					String ans = answers[i];
					String code = "";
					if(ans!=null)
						code = code55753TreatmentAdministration.get(ans);
						String key = code55753_8B+"_"+code+"_CD";
						if(formSpecificQuestionMap.get(key)!=null)
							formSpecificQuestionAnswerMap.put(key,ans);
				}
			}

		} catch (Exception e) {
			logger.debug("Exception in processTreatmentAdministrationLTBI: " + e.getMessage());
		}
	}
	
	
	private static void processSevereAdverseEvent( Map<String,String> formSpecificQuestionMap, Map<String,String> formSpecificQuestionAnswerMap) {
		try {
			String answer = formSpecificQuestionAnswerMap.get(code64750_3_CD);
			//String answersSecondarySites = "";
			
			if(answer!=null){
				String[] answers = answer.split(", ");
				
				for(int i=0; i<answers.length; i++){
					String ans = answers[i];
					String code = "";
					if(ans!=null)
						code = code64750_3SevereAdverse.get(ans);
						String key = code64750_3+"_"+code+"_CD";
						if(formSpecificQuestionMap.get(key)!=null)
							formSpecificQuestionAnswerMap.put(key,ans);
				}
			}

		} catch (Exception e) {
			logger.debug("Exception in processSevereAdverseEvent: " + e.getMessage());
		}
	}
	/**
	 * If null, return empty string else return value
	 * @param s
	 * @return
	 */
	protected static String checkNull(String s){
		return  s != null? s: "";
	}
	
	/**
	 * Get the Treatment into one string.
	 * @param treamentSummVO
	 * @return
	 * @throws NEDSSAppException
	 */
	private static StringBuffer getTreatmentNameCode(TreatmentSummaryVO treamentSummVO) throws NEDSSAppException{
		try {
			StringBuffer treatmentNameCode =new StringBuffer();
				if(treamentSummVO.getTreatmentNameCode() != null && treamentSummVO.getTreatmentNameCode().equals("OTH")){
					TreatmentAdministeredDAOImpl dao = new TreatmentAdministeredDAOImpl();
					Collection<Object> coll =dao.load(treamentSummVO.getTreatmentUid());
					Object[] array =coll.toArray();
					if(array.length >0) {

						TreatmentAdministeredDT dt = (TreatmentAdministeredDT) array[0];
						if(dt.getCd() != null){
						String desc =CachedDropDowns.getCodeDescTxtForCd(dt.getCd(),"TREAT_DRUG");
						String text = desc != null? desc : dt.getCd();
						treatmentNameCode.append(text).append(", ");
						}
						if(dt.getDoseQty() != null){
							treatmentNameCode.append(dt.getDoseQty()).append(" ");
						}
						if(dt.getDoseQtyUnitCd()  != null){
							String desc =CachedDropDowns.getCodeDescTxtForCd(dt.getDoseQtyUnitCd() ,"TREAT_DOSE_UNIT");
							String text = desc != null? desc : dt.getDoseQtyUnitCd();
							treatmentNameCode.append(text).append(", ");
							}
						if(dt.getRouteCd() != null){
							String desc =CachedDropDowns.getCodeDescTxtForCd(dt.getRouteCd(),"TREAT_ROUTE");
							String text = desc != null? desc : dt.getRouteCd();
							treatmentNameCode.append(text).append(", ");
							}
						if(dt.getIntervalCd() != null){
							String desc =CachedDropDowns.getCodeDescTxtForCd(dt.getIntervalCd(),"TREAT_FREQ_UNIT");
							String text = desc != null? desc : dt.getIntervalCd();
							treatmentNameCode.append(text).append(" X ");
							}
						if(dt.getEffectiveDurationAmt() != null){
							treatmentNameCode.append(dt.getEffectiveDurationAmt()).append(" ");
						}

						if(dt.getEffectiveDurationUnitCd() != null){
							String desc =CachedDropDowns.getCodeDescTxtForCd(dt.getEffectiveDurationUnitCd(),"TREAT_DUR_UNIT");
							String text = desc != null? desc : dt.getEffectiveDurationUnitCd();
							treatmentNameCode.append(text);
							}
					}


				}else{
					treatmentNameCode.append(treamentSummVO.getCustomTreatmentNameCode());
				}
				return treatmentNameCode;
		} catch (NEDSSSystemException e) {
			throw new NEDSSAppException("getTreatmentNameCode excdeption thrown", e);
		}
	}


	
	
	/**
	 * checkInvStartedFromConRec - if Investigation started from Contact and get values from Original Investigation that created the contact
	 * @param invProxyVO
	 * @param invCnt - 1 for Inv, 2 for CoInv
	 * @param session
	 * @return null if not started from contact else map of values needed from source investigation if present:
	 * 		 NBS136_ORIGINAL_CONDITION - condition of originating Inv. Usually same. Could be different.
	 * 		 NBS117_ORIGINAL_PATIENT_ID_NUMBER - local is i.e.CAS10048000GA01 of originating inv.
	 * 		 NBS057_NBS056_ORIGINAL_INFECTIOUS_PERIOD - infectious from - infectious to in months on the Contact Tracing tab
	 * @throws NEDSSAppException
	 */
	public static Map<String, String> checkInvStartedFromConRec(PageProxyVO invProxyVO, int invCnt, HttpSession session) throws NEDSSAppException {

		PageActProxyVO contactAux;
		HashMap<String, String> returnMap = new HashMap<String,String>();

		try {
			Collection<Object> contactColl = ((PageActProxyVO) invProxyVO)
					.getTheCTContactSummaryDTCollection();

			Iterator<Object> itr = contactColl.iterator();
			Boolean isFrnCon = Boolean.FALSE;
			String contactCond = null;
			while (itr.hasNext()) {

				CTContactSummaryDT dt = (CTContactSummaryDT) itr.next();

				if(dt.isPatientNamedByContact()&&dt.getSubjectEntityPhcUid() != null){
					Long conPhcUid = dt.getSubjectEntityPhcUid();
					if(conPhcUid != null){
						contactAux = (PageActProxyVO) PageLoadUtil
								.getProxyObject(
										String.valueOf(conPhcUid),
										session);

						if (contactAux.getTheCTContactSummaryDTCollection() != null) {
							Iterator<Object> it = contactAux
									.getTheCTContactSummaryDTCollection().iterator();
							while (it.hasNext()) {
								CTContactSummaryDT condt = (CTContactSummaryDT) it.next();
								if (condt.isContactNamedByPatient()
										&& condt.getContactEntityPhcUid() != null
										&& condt.getContactEntityPhcUid().equals(
												invProxyVO.getPublicHealthCaseVO()
												.getThePublicHealthCaseDT()
												.getPublicHealthCaseUid())) {
									PageLoadUtil pageLoadUtil  = new PageLoadUtil();
									String formCd = pageForm.getPageFormCd();
									pageLoadUtil.loadQuestions(formCd);
									pageLoadUtil.loadQuestionKeys(formCd);
									//we're assuming question key map is already populated with the correct page data
									Map<Object, Object> thisAnswerMap = pageLoadUtil.updateMapWithQIds(((PageActProxyVO) contactAux)
											.getPageVO().getPamAnswerDTMap());
									isFrnCon = Boolean.TRUE;
									if (thisAnswerMap.containsKey(NBS136))
										contactCond = getInvAnsFromMap(thisAnswerMap, NBS136);
								
					
									if (contactCond == null || contactCond.isEmpty()) {
										contactCond = contactAux.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd();
										if(contactCond != null){
											CachedDropDownValues cddv = new CachedDropDownValues();
											String shortcd = cddv.getDiagnosisCodeForConceptCode(contactCond);
											contactCond =shortcd != null ? shortcd :contactCond;
										}
									}
									
									//OP Condition and OP Case ID should only print if investigation was started from contact record
									if(!"T1".equals(invProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getReferralBasisCd()) && 
											!"T2".equals(invProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getReferralBasisCd()) ) {
										returnMap.put(NBS136_ORIGINAL_CONDITION+invCnt+CODED_VALUE, contactCond);
										//ToDo: Should NBS117 be the Original Patient Local Id not the Case ID. i.e. PSN10065016GA01
										returnMap.put(ORIGINAL_CASE_ID+invCnt, contactAux.getPublicHealthCaseVO().getThePublicHealthCaseDT().getLocalId());
									}
									returnMap.put(NBS117_ORIGINAL_PATIENT_ID_NUMBER, contactAux.getPublicHealthCaseVO().getThePublicHealthCaseDT().getLocalId());
									//get originating inv infectious period in months
									//Interview Period (mos) 1 and Interview Period (mos) 2 fpor coinf
									Timestamp infectiousFromDate =  contactAux.getPublicHealthCaseVO().getThePublicHealthCaseDT().getInfectiousFromDate();
									Timestamp infectiousToDate = contactAux.getPublicHealthCaseVO().getThePublicHealthCaseDT().getInfectiousToDate();
									if (infectiousFromDate != null && infectiousToDate!= null) {
										try {
											int m1 = infectiousFromDate.getYear() * 12 + infectiousFromDate.getMonth();
											int m2 = infectiousToDate .getYear() * 12 + infectiousToDate .getMonth();
											Integer infectiousNumberOfMonths = (Integer) m2 - m1 + 1;
											if (infectiousNumberOfMonths < 1)
												infectiousNumberOfMonths = 1;
											returnMap.put(NBS057_NBS056_ORIGINAL_INFECTIOUS_PERIOD, infectiousNumberOfMonths.toString());
										} catch (Exception e) {
											logger.error("Error in CommonPDFPrintForm computing infectious period of source investigation", e.getMessage());
										}	
									}

									//per ND-1007 put from original contact record if present
									CTContactProxyVO originalContactRecordProxy = PageLoadUtil.getCTContactProxyObject(condt.getCtContactUid(), session);
									Map<Object, Object>  ctAnswerMap = 	pageLoadUtil.updateMapWithQIds(originalContactRecordProxy.getcTContactVO().getCtContactAnswerDTMap());
									if (ctAnswerMap.containsKey(NBS118))
										returnMap.put(NBS118+ORIGINAL_PATIENT_CONTACT_RECORD, getContactAnsFromMap(ctAnswerMap,NBS118));
									if (ctAnswerMap.containsKey(NBS119))
										returnMap.put(NBS119+ORIGINAL_PATIENT_CONTACT_RECORD, getContactAnsFromMap(ctAnswerMap,NBS119));
									if (ctAnswerMap.containsKey(NBS120))
										returnMap.put(NBS120+ORIGINAL_PATIENT_CONTACT_RECORD, getContactAnsFromMap(ctAnswerMap,NBS120));
									if (ctAnswerMap.containsKey(NBS121))
										returnMap.put(NBS121+ORIGINAL_PATIENT_CONTACT_RECORD, getContactAnsFromMap(ctAnswerMap,NBS121));	
									if (ctAnswerMap.containsKey(NBS122))
										returnMap.put(NBS122+ORIGINAL_PATIENT_CONTACT_RECORD, getContactAnsFromMap(ctAnswerMap,NBS122));
									if (ctAnswerMap.containsKey(NBS123))
										returnMap.put(NBS123+ORIGINAL_PATIENT_CONTACT_RECORD, getContactAnsFromMap(ctAnswerMap,NBS123));										
									logger.debug("\n --> put Original Contact Record Sexual and Needle Sharing Exposure Dates into returnMap <-- \n");
									
									Long originalInterview = condt.getNamedDuringInterviewUid();
									if (originalInterview != null && 
											originalInterview.longValue() != CTConstants.StdInitiatedWithoutInterviewLong.longValue()) {
										//retrieve the original interview for values IXS102_FR1_1, IXS102_FR1_2 and IXS105_1_CDT, IXS105_2_CDT
										if (contactAux.getTheInterviewSummaryDTCollection()!=null && contactAux.getTheInterviewSummaryDTCollection().size()>0){
											Iterator iterator= contactAux.getTheInterviewSummaryDTCollection().iterator();
											boolean interviewNotFound = true;
											while(iterator.hasNext() && interviewNotFound){
												InterviewSummaryDT interviewSummaryDT = (InterviewSummaryDT)iterator.next();
												if(interviewSummaryDT != null && 
														interviewSummaryDT.getInterviewUid().longValue() == originalInterview.longValue() ){
													interviewNotFound = false;
													if (interviewSummaryDT.getInterviewTypeCd() != null)
														returnMap.put("IXS105_"+invCnt+CODED_VALUE_TRANSLATED, IXS105NbsInterviewTypeMap.get(interviewSummaryDT.getInterviewTypeCd()));
													if (interviewSummaryDT.getInterviewerQuickCd() != null)
														returnMap.put("IXS102_FR1_"+invCnt, interviewSummaryDT.getInterviewerQuickCd());
												} //interviewSummaryDT != null
											}//while 
										}//interviewSumary not null
									}//original interview not null
									
								} //this contact started Inv
								if(isFrnCon) break;
							} //contact summary iter
							if(isFrnCon) break;
						} //contact summary not null
						if(isFrnCon) break;
					}//contactColl has next 
				} //dt.isPatientNamedByContact()&&dt.getSubjectEntityPhcUid() != null
				
			} //contact collection iter
			if (returnMap.isEmpty())
				return null;


			return returnMap;

		} catch (Exception e) {
			logger.error("checkInvStartedFromConRec exception thrown " +e);
			throw new NEDSSAppException("checkInvStartedFromConRec exception thrown", e);
		}
	}
	
	/**
	 * For CDT (translated code) form fields, translate the NBS code into the code needed for the form
	 * @param questionIdentifier
	 * @param answerTxt
	 * @return
	 */
	protected static String mapCode(String questionIdentifier, String answerTxt) {
		if(questionIdentifier.equalsIgnoreCase(STD121AnatomicSite)){
			answerTxt=STD121PhvsClinicianObservedLesionsStdMap.get(answerTxt);
		}else if(questionIdentifier.equalsIgnoreCase(NBS151_CASE_MANAGEMENT_SURV_PATIENT_FOLL_UP)){
			answerTxt=NBS151SurveillancePatientFollowupMap.get(answerTxt);
		}else if(questionIdentifier.equalsIgnoreCase(INV178_Public_Health_Case_pregnant_ind_cd)){
			answerTxt=INV178YnuMap.get(answerTxt);
			answerTxt=INV178YnuMap.get(answerTxt);
		}else if(questionIdentifier.equalsIgnoreCase(NBS149_CASE_MANAGEMENT_SURV_PROV_EXM_REASON)){
			answerTxt=NBS149ExamReasonMap.get(answerTxt);
		}else if(questionIdentifier.equalsIgnoreCase(NBS233_DRUG_HISTORY)||questionIdentifier.equalsIgnoreCase(NBS235_DRUG_HISTORY)||questionIdentifier.equalsIgnoreCase(NBS237_DRUG_HISTORY)||questionIdentifier.equalsIgnoreCase(NBS239_DRUG_HISTORY)|| questionIdentifier.equalsIgnoreCase(NBS234_DRUG_HISTORY)||questionIdentifier.equalsIgnoreCase(NBS236_DRUG_HISTORY)||questionIdentifier.equalsIgnoreCase(NBS238_DRUG_HISTORY)||questionIdentifier.equalsIgnoreCase(NBS240_DRUG_HISTORY)){
				answerTxt=drugHistory.get(answerTxt);
		}else if(questionIdentifier.equalsIgnoreCase(STD119_PARTNER_INTERNET)){
			answerTxt=STD119PartnerInternet.get(answerTxt);
		}else if(questionIdentifier.equalsIgnoreCase(NBS167_CASE_MANAGEMENT_FLD_FOLL_UP_NOTIFICATION_PLAN)){
			if(answerTxt!=null && answerTxt.length()==1) 
				answerTxt="0"+answerTxt;
		}else if(questionIdentifier.equalsIgnoreCase(NBS143_CASE_MANAGEMENT_INIT_FOLL_UP_NOTIFIABLE)){
			if(answerTxt!=null && answerTxt.length()==1) 
				answerTxt="0"+answerTxt;
		}else if(questionIdentifier.equalsIgnoreCase(NBS177_CASE_MANAGEMENT_ACT_REF_TYPE_CD)){
			if(answerTxt!=null && answerTxt.length()==1) 
				answerTxt="0"+answerTxt;
		}else if(questionIdentifier.equalsIgnoreCase(NBS252)){
			if(answerTxt!=null && answerTxt.length()==1) 
				answerTxt="0"+answerTxt;
		}else if(questionIdentifier.equalsIgnoreCase(NBS257)){
			if(answerTxt!=null && answerTxt.length()==1) 
				answerTxt="0"+answerTxt;
		}else if(questionIdentifier.equalsIgnoreCase(NBS267)){
			if(answerTxt!=null && answerTxt.length()==1) 
				answerTxt="0"+answerTxt;
		}else if(questionIdentifier.equalsIgnoreCase(STD106)){ //gst revisit differ on IX than FR?
			if(answerTxt!=null && answerTxt.length()==1) 
				answerTxt="0"+answerTxt;
		}else if(questionIdentifier.equalsIgnoreCase(NBS260_Referred_For_900_Test)){
			answerTxt=YNStdMisMap.get(answerTxt);
		}else if(questionIdentifier.equalsIgnoreCase(NBS262_900_Test_Preformed)){
			answerTxt=YNRUDStdMisMap.get(answerTxt);
		}else if(questionIdentifier.equalsIgnoreCase(NBS265_Partner_Informed_Of_900_Result)){
			answerTxt=YNStdMisMap.get(answerTxt);		
		}else if(questionIdentifier.equalsIgnoreCase(NBS263_HIV_Test_Result_at_this_Event)){
			answerTxt=NBS263_HIVTestResult.get(answerTxt);	
		}else if(questionIdentifier.equalsIgnoreCase(NBS266_Refer_For_Care)){
			answerTxt=YNStdMisMap.get(answerTxt);	
		}else if (questionIdentifier.equalsIgnoreCase(NBS242PlacesToMeetPartner)) {
			answerTxt=NBS242PlacesToMeetPartnerMap.get(answerTxt); 
		}else if (questionIdentifier.equalsIgnoreCase(NBS244PlacesToHaveSex)) {
			answerTxt=NBS244PlacesToHaveSexMap.get(answerTxt); 
		}else if (questionIdentifier.equalsIgnoreCase(NBS223FemalePartnersPastYear)) {
			answerTxt=NBS223FemalePartnersPastYearMap.get(answerTxt); 
		}else if (questionIdentifier.equalsIgnoreCase(NBS225MalePartnersPastYear)) {
			answerTxt=NBS225MalePartnersPastYearMap.get(answerTxt); 
		}else if (questionIdentifier.equalsIgnoreCase(NBS227TransgenderPartnersPastYear)) {
			answerTxt=NBS227TransgenderPartnersPastYearMap.get(answerTxt); 
		}else if (questionIdentifier.equalsIgnoreCase(NBS129FemalePartnersInterviewPeriod)) {
			answerTxt=NBS129FemalePartnersInterviewPeriodMap.get(answerTxt); 
		}else if (questionIdentifier.equalsIgnoreCase(NBS131MalePartnersInterviewPeriod)) {
			answerTxt=NBS131MalePartnersInterviewPeriodMap.get(answerTxt); 
		}else if (questionIdentifier.equalsIgnoreCase(NBS133TransgenderPartnersInterviewPeriod)) {
			answerTxt=NBS133TransgenderPartnersInterviewPeriodMap.get(answerTxt); 
		}else if (questionIdentifier.equalsIgnoreCase(NBS192PatientInterviewStatus)) {
			answerTxt = NBS192_PatientInterviewStatusMap.get(answerTxt);
		}else if (questionIdentifier.equalsIgnoreCase(Pregnancy_Status_CDT)) {
			answerTxt = PregnancyMap.get(answerTxt);
		}else if (questionIdentifier.equalsIgnoreCase(INV163)) {
			answerTxt = INV163caseStatusMap.get(answerTxt);
		}else if (questionIdentifier.equalsIgnoreCase(INV1115)) {
			answerTxt = INV1115CaseVerificationMap.get(answerTxt);
		}else if (questionIdentifier.equalsIgnoreCase(INV1109)) {
			answerTxt = INV1109CaseCountedMap.get(answerTxt);
		}else if (questionIdentifier.equalsIgnoreCase(INV1111)) {
			answerTxt = INV1111CountryOfVerifiedCase.get(answerTxt);
		}else if (questionIdentifier.equalsIgnoreCase(INV1113)) {
			answerTxt = INV1111CountryOfVerifiedCase.get(answerTxt);
		}
		else if (questionIdentifier.equalsIgnoreCase(INV501)) {
			answerTxt = (String)INV501CountryOfUsualResidency.get(answerTxt);
		}/*else if (questionIdentifier.equalsIgnoreCase(INV1116) || questionIdentifier.equalsIgnoreCase(INV1119) || questionIdentifier.equalsIgnoreCase(INV1120)) {
			//if(answerTxt!=null && answerTxt.indexOf("^")!=-1)
			//	answerTxt =answerTxt.substring(0,answerTxt.indexOf("^"));
		}*/
		else if (questionIdentifier.equalsIgnoreCase(code85659_1)) {
			answerTxt = (String)code85659_1Occupation.get(answerTxt);
		}
		else if (questionIdentifier.equalsIgnoreCase(code85657_5)) {
			answerTxt = (String)code85657_5Industry.get(answerTxt);
		}
		else if (questionIdentifier.equalsIgnoreCase(NBS872)){
			answerTxt = (String)NBS872_QuantitativeResultsUnits.get(answerTxt);
		}
		else if(questionIdentifier.equalsIgnoreCase(TUB128) || questionIdentifier.equalsIgnoreCase(TUB132) || questionIdentifier.equalsIgnoreCase(TUB865)){
			if(answerTxt!=null && answerTxt.indexOf("^")!=-1)
				answerTxt=answerTxt.substring(answerTxt.indexOf("^")+1);
			else
				answerTxt = (String)TUB128_TUB132_TUB865_SpecimenSource.get(answerTxt);
		}
		
		else if(questionIdentifier.equalsIgnoreCase(INV290)){
			
			if(answerTxt!=null && answerTxt.indexOf("OTH^")!=-1)
				answerTxt = "OTH";
			answerTxt = (String)INV290TestType.get(answerTxt);
		}
		else if(questionIdentifier.equalsIgnoreCase(LAB165)){
		
			if(answerTxt!=null && answerTxt.indexOf("OTH^")!=-1 && answerTxt.length()>4)
				answerTxt = answerTxt.substring(answerTxt.indexOf("^")+1);
			else
				if(answerTxt!=null && answerTxt.indexOf("OTH^")!=-1)
					answerTxt = (String)LAB165SpecimenSource.get("OTH");
				else
					answerTxt = (String)LAB165SpecimenSource.get(answerTxt);
			
		}else if(questionIdentifier.equalsIgnoreCase(LAB677)){
			
			if(answerTxt!=null && answerTxt.indexOf("OTH^")!=-1)
				answerTxt = "OTH";
			
		}else if(questionIdentifier.equalsIgnoreCase(LAB677+"_OTH")){
			
			if(answerTxt!=null && answerTxt.indexOf("OTH^")!=-1)
				answerTxt = answerTxt.substring(answerTxt.indexOf("^")+1);
			
		}else if (questionIdentifier.equalsIgnoreCase(LABAST6)) {
			if(answerTxt!=null && answerTxt.indexOf("OTH^")!=-1 && answerTxt.length()>4)
				answerTxt = answerTxt.substring(answerTxt.indexOf("^")+1);
			else
				if(answerTxt!=null && answerTxt.indexOf("OTH^")!=-1)
					answerTxt = (String)LABAST6DrugName.get("OTH");
				else
					answerTxt = LABAST6DrugName.get(answerTxt);
		}else if (questionIdentifier.equalsIgnoreCase(LABAST3)) {
			if(answerTxt!=null && answerTxt.indexOf("OTH^")!=-1 && answerTxt.length()>4)
				answerTxt = answerTxt.substring(answerTxt.indexOf("^")+1);
			else
				if(answerTxt!=null && answerTxt.indexOf("OTH^")!=-1)
					answerTxt = (String)LABAST3SpecimenSource.get("OTH");
				else
					answerTxt = LABAST3SpecimenSource.get(answerTxt);
		}else if (questionIdentifier.equalsIgnoreCase(LABAST7)) {
			if(answerTxt!=null && answerTxt.indexOf("OTH^")!=-1 && answerTxt.length()>4)
				answerTxt = answerTxt.substring(answerTxt.indexOf("^")+1);
			else
				if(answerTxt!=null && answerTxt.indexOf("OTH^")!=-1)
					answerTxt = (String)LABAST7TestMethod.get("OTH");
				else
					answerTxt = LABAST7TestMethod.get(answerTxt);
		}
		else if (questionIdentifier.equalsIgnoreCase(code48018_6)) {
			if(answerTxt!=null && answerTxt.indexOf("OTH^")!=-1 && answerTxt.length()>4)
				answerTxt = answerTxt.substring(answerTxt.indexOf("^")+1);
			else
				if(answerTxt!=null && answerTxt.indexOf("OTH^")!=-1)
					answerTxt = (String)code48018_6Gene.get("OTH");
				else
					answerTxt = code48018_6Gene.get(answerTxt);
		}
		
		else if (questionIdentifier.equalsIgnoreCase(LAB684)) {
			if(answerTxt!=null && answerTxt.indexOf("OTH^")!=-1 && answerTxt.length()>4)
				answerTxt = answerTxt.substring(answerTxt.indexOf("^")+1);
			else
				if(answerTxt!=null && answerTxt.indexOf("OTH^")!=-1)
					answerTxt = (String)LAB684SpecimenSource.get("OTH");
				else
					answerTxt = LAB684SpecimenSource.get(answerTxt);
		}
		else if(questionIdentifier.equalsIgnoreCase(LAB689)){
			
			if(answerTxt!=null && answerTxt.indexOf("OTH^")!=-1)
				answerTxt = "OTH";
		
		}
		else if (questionIdentifier.equalsIgnoreCase(INV1153)) {
			answerTxt = (String)INV1153OutOfState.get(answerTxt);
		}
		else if (questionIdentifier.equalsIgnoreCase(INV1154)) {
			answerTxt = (String)INV1154OutOfCountry.get(answerTxt);
		}
		else if (questionIdentifier.equalsIgnoreCase(INV1158)) {
			if(answerTxt!=null && answerTxt.indexOf("OTH^")!=-1 && answerTxt.length()>4)
				answerTxt = answerTxt.substring(answerTxt.indexOf("^")+1);
			else
				if(answerTxt!=null && answerTxt.indexOf("OTH^")!=-1)
					answerTxt = (String)INV1158Drug.get("OTH");
				else
					answerTxt = (String)INV1158Drug.get(answerTxt);
		}
		else if (questionIdentifier.equalsIgnoreCase(code42563_7_CD)) {
			if(answerTxt!=null && answerTxt.indexOf("OTH^")!=-1 && answerTxt.length()>4)
				answerTxt = answerTxt.substring(answerTxt.indexOf("^")+1);
			else
				if(answerTxt!=null && answerTxt.indexOf("OTH^")!=-1)
					answerTxt = (String)code42563_7_CDSideEffect.get("OTH");
				else
					if(answerTxt!=null && answerTxt.indexOf("OTH^")!=-1)
						answerTxt = (String)code42563_7_CDSideEffect.get("OTH");

			else
				answerTxt = (String)code42563_7_CDSideEffect.get(answerTxt);
		}else if(questionIdentifier.equalsIgnoreCase(LAB689_OTH)){
			
			if(answerTxt!=null && answerTxt.indexOf("OTH^")!=-1)
				answerTxt = answerTxt.substring(answerTxt.indexOf("^")+1);
		
		}
		

		return answerTxt;
	}
	
	
	/**
	 * Convert the race code into the one the form requires.
	 * @param race
	 * @param i
	 */
	protected static void convertRaceCD(String race, int i) {
		if(formSpecificQuestionMap.containsKey(DEM152)){
			formSpecificQuestionAnswerMap.put(DEM152,DEM152RaceCodePRaceCatMap.get(race));
		}else if(formSpecificQuestionMap.containsKey(DEM152+SEPERATOR+race)){
			formSpecificQuestionAnswerMap.put(DEM152+SEPERATOR+race,DEM152RaceCodePRaceCatMap.get(race));
		}
	}
	

	/**
	 * Get the coinfection investigation from the workup proxy in PageLoadUtil to show the conditions in the list.
	 * Note: This takes some time to process. Is there a better way?
	 * @param request
	 * @return
	 * @throws NEDSSAppException
	 * @throws Exception
	 */
	public static Map<String, String> getCoinfectionInvestigations(HttpServletRequest request) throws NEDSSAppException,Exception{
		Map<String, String> returnMap = new HashMap<String, String>();

		Long publicHealthCaseUID = new Long((String)NBSContext.retrieve(request.getSession(),
				NBSConstantUtil.DSInvestigationUid));
		Object[] oParam1 = new Object[] { NEDSSConstants.PRINT_CDC_CASE, publicHealthCaseUID };
		proxyVO =(PageActProxyVO)CallProxyEJB.callProxyEJB(oParam1 , JNDINames.PAGE_PROXY_EJB, 
						"getPageProxyVO", request.getSession());
		String coinfCd = proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCoinfectionId();
		Long currPHCUid = proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid();
		PersonVO personVO;
		try {
			personVO = PageLoadUtil.getPersonVO(NEDSSConstants.PHC_PATIENT, proxyVO);

			Long personUID = personVO.getThePersonDT().getPersonParentUid();

			 String sBeanJndiName = JNDINames.WORKUP_PROXY_EJB;// "WorkupProxyEJBRef";
	         String sMethod = "getWorkupProxy";
	         Object[] oParams = new Object[] {personUID};
	         MainSessionHolder holder = new MainSessionHolder();
	         MainSessionCommand msCommand;

				msCommand = holder.getMainSessionCommand(request.getSession());

	         ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod,
	                                                  oParams);
	         WorkupProxyVO workupProxyVO = (WorkupProxyVO) arr.get(0);

			Collection<Object> coll = workupProxyVO.getTheInvestigationSummaryVOCollection();

			for(Object o : coll){
				if (o instanceof InvestigationSummaryVO){
					if (((InvestigationSummaryVO)o).getCoinfectionId() != null &&((InvestigationSummaryVO)o).getCoinfectionId().equals(coinfCd) && ((InvestigationSummaryVO)o).getPublicHealthCaseUid().longValue() != currPHCUid.longValue()){
						returnMap.put(((InvestigationSummaryVO)o).getPublicHealthCaseUid().toString(), ((InvestigationSummaryVO)o).getConditionCodeText());
					}
				}
			}
		} catch (NEDSSAppException e) {
			logger.error("Error while retrieving getCoInfectionInvestigation:  "
					+ e.toString());
			throw new NEDSSAppException(e.toString());
		} catch (Exception e) {
			logger.error("Error while retrieving getCoInfectionInvestigation:  "
					+ e.toString());
			throw new Exception(e.toString());
		}

		return returnMap;
	}
	
	/**
	 * If a coinfection was specified get the VO and put it into the variable coProxyVO.
	 * @param coinfectionUid
	 * @param request
	 * @throws NEDSSAppException
	 * @throws Exception
	 */
	private static void getCoinfectionVO(Long coinfectionUid, HttpServletRequest request) throws NEDSSAppException,Exception{
		try{
			Object[] oParams = new Object[] { NEDSSConstants.PRINT_CDC_CASE, new Long(coinfectionUid)};
			coProxyVO =(PageActProxyVO)CallProxyEJB.callProxyEJB(oParams , JNDINames.PAGE_PROXY_EJB, "getPageProxyVO", request.getSession());
		} catch (Exception e) {
			logger.error("Error while retrieving getCoInfectionInvestigation:  "
					+ e.toString());
			throw new NEDSSAppException("Error while retrieving getCoInfectionInvestigation:  "+ e.toString());
		}
	}

	/**
	 * Populate from the repeat answer map if present.
	 * @param repeatingAnswerMap
	 * @param metadata
	 * @throws NEDSSAppException
	 */
	@SuppressWarnings("unchecked")
	private static void populateRepeatData(Map<Object, Object> repeatingAnswerMap, NbsQuestionMetadata metadata )
			throws NEDSSAppException {
		try {
			if (repeatingAnswerMap.containsKey(metadata.getNbsQuestionUid())) {
				ArrayList<Object> list = (ArrayList<Object>) repeatingAnswerMap.get(metadata.getNbsQuestionUid());

				if(metadata.getNbsUiComponentUid().compareTo((long) 1019)==0){
					populateNotes(list, metadata);
				}else{
					if (list != null && list.size() > 0) {
						Iterator<Object> it = list.iterator();
						while (it.hasNext()) {
							NbsAnswerDT caseDT = (NbsAnswerDT) it.next();

							if(metadata.getQuestionIdentifier()!=null && metadata.getQuestionIdentifier().equalsIgnoreCase("63939_3"))
								System.out.println("");

							if(formSpecificQuestionMap.containsKey(metadata.getQuestionIdentifier()+ REPEAT_IND)){
								formSpecificQuestionAnswerMap.put(metadata.getQuestionIdentifier()+ REPEAT_IND,  mapCode(metadata.getQuestionIdentifier(), caseDT.getAnswerTxt()));
							}else if(formSpecificQuestionMap.containsKey(metadata.getQuestionIdentifier()+ REPEAT_IND+ caseDT.getAnswerGroupSeqNbr())){
								formSpecificQuestionAnswerMap.put(metadata.getQuestionIdentifier()+ REPEAT_IND+ caseDT.getAnswerGroupSeqNbr(),  mapCode(metadata.getQuestionIdentifier(), caseDT.getAnswerTxt()));
							}else if(formSpecificQuestionMap.containsKey(metadata.getQuestionIdentifier()+ REPEAT_IND+ caseDT.getAnswerGroupSeqNbr()+ CODED_VALUE_TRANSLATED)){
								formSpecificQuestionAnswerMap.put(metadata.getQuestionIdentifier()+ REPEAT_IND+ caseDT.getAnswerGroupSeqNbr()+ CODED_VALUE_TRANSLATED, mapCode(metadata.getQuestionIdentifier(), caseDT.getAnswerTxt()));
							}else if(formSpecificQuestionMap.containsKey(metadata.getQuestionIdentifier()+ REPEAT_IND+ caseDT.getAnswerGroupSeqNbr()+ CODED_VALUE)){
								String answerRepeating = caseDT.getAnswerTxt();
								formSpecificQuestionAnswerMap.put(metadata.getQuestionIdentifier()+ REPEAT_IND+ caseDT.getAnswerGroupSeqNbr()+ CODED_VALUE, mapCode(metadata.getQuestionIdentifier(), answerRepeating));
								//Handling Other value answer from repeating codequestion
								if(answerRepeating!=null && answerRepeating.indexOf("OTH^")!=-1 && formSpecificQuestionMap.containsKey(metadata.getQuestionIdentifier()+ REPEAT_IND+ caseDT.getAnswerGroupSeqNbr()+ OTH_VALUE))
									formSpecificQuestionAnswerMap.put(metadata.getQuestionIdentifier()+ REPEAT_IND+ caseDT.getAnswerGroupSeqNbr()+ OTH_VALUE, mapCode(metadata.getQuestionIdentifier()+"_OTH", caseDT.getAnswerTxt()));
							}else if(formSpecificQuestionMap.containsKey(metadata.getQuestionIdentifier()+ REPEAT_IND+ caseDT.getAnswerGroupSeqNbr()+_1+ CODED_VALUE)){ // handles NBS250_R1_1_CD type of questions
								formSpecificQuestionAnswerMap.put(metadata.getQuestionIdentifier()+ REPEAT_IND+ caseDT.getAnswerGroupSeqNbr()+_1+ CODED_VALUE, mapCode(metadata.getQuestionIdentifier(), caseDT.getAnswerTxt()));
							}else if(formSpecificQuestionMap.containsKey(metadata.getQuestionIdentifier()+ REPEAT_IND+ caseDT.getAnswerGroupSeqNbr()+_1)){ // handles NBS251_R1_1 type of questions
								formSpecificQuestionAnswerMap.put(metadata.getQuestionIdentifier()+ REPEAT_IND+ caseDT.getAnswerGroupSeqNbr()+_1, mapCode(metadata.getQuestionIdentifier(), caseDT.getAnswerTxt()));
							}

						}

					}
				}
			}
		} catch (Exception e) {
			logger.error("CommonPDFPrintForm.populateRepeatData Exception thrown, "+ e.getMessage(), e);
			throw new NEDSSAppException("CommonPDFPrintForm.populateRepeatData Exception thrown, ", e);
		}
	}
	
	/**
	 * Populate note field. Only called from repeat.
	 * Note: Fatima says this is unnecessary with the new PDFBox lib.
	 * @param coll
	 * @param metadata
	 */
	private static void populateNotes(Collection coll, NbsQuestionMetadata metadata){
		String notes="";
		Iterator<NbsCaseAnswerDT> it = coll.iterator();
		while (it.hasNext()) {
			NbsCaseAnswerDT caseDT =  it.next();
			logger.debug("caseDT is:" + caseDT.toString());
			if(caseDT.getAnswerTxt() != null ){
				logger.debug("caseDT uid is :" + caseDT.getNbsCaseAnswerUid());
				notes = notes +  caseDT.getAnswerTxt()/*addNewlinesToNotes(caseDT.getAnswerTxt(),metadata.getQuestionIdentifier())*/ + "\n" ;
			}//notes.length();
		}
		//truncating the notes and appending '...' to fit in the pdf notes box
		
			//For notes textbox stretching full page width and Half Page Length
			if (metadata.getQuestionIdentifier().equals(NBS152)){
				if( notes.length()>NotesFullWidthHalfPageCharLimit ){
				notes=notes.substring(0, NotesFullWidthHalfPageCharLimit)+"...";
				}
			}
			//For notes textbox stretching full page width and Full Page Length
			else if (metadata.getQuestionIdentifier().equals(NBS195)){
				if( notes.length()>NotesFullWidthFullPageCharLimit ){
				notes=notes.substring(0, NotesFullWidthFullPageCharLimit)+"...";
				}
			}
			//For notes textbox stretching half page width
			else{ 
				if( notes.length()>NotesHalfWidthCharLimit ){
				notes=notes.substring(0, NotesHalfWidthCharLimit)+"...";
				}
			}
		
		String previousNotes="";//For not losing the notes from the initiating investigation with the notes from the co-infection investigation
		if(formSpecificQuestionAnswerMap.get(metadata.getQuestionIdentifier())!=null){
			
			previousNotes=formSpecificQuestionAnswerMap.get(metadata.getQuestionIdentifier());
			notes=previousNotes+notes;
		}
		
		formSpecificQuestionAnswerMap.put(metadata.getQuestionIdentifier() , notes);

	
	}

	/**
	 * getAnswer: in case the field can contain the answer to multiple answers, like countries, we will get the new answer and append it to the previous one before setting the answer.
	 * @param multiAnswer
	 * @param previousAnswers
	 * @param metadata
	 * @param answerTxt
	 * @return
	 */
	private static String getAnswer(boolean multiAnswer, String previousAnswers, NbsQuestionMetadata metadata, String answerTxt){
		
		String answer = mapCode(metadata.getQuestionIdentifier(),answerTxt);
		if(metadata.getQuestionIdentifier()!=null && ( metadata.getQuestionIdentifier().equalsIgnoreCase("DEM127") || metadata.getQuestionIdentifier().equalsIgnoreCase("DEM128")))
			System.out.println("aqui");
			if(multiAnswer && previousAnswers!=null && !previousAnswers.isEmpty()){
			answer = previousAnswers+", "+answer;
		}
		
		return answer;
		
		
	}
	/**
	 * populateNonRepeatData - translate if necessary
	 * @param answertext
	 * @param investigationCounterNumber
	 * @param metadata
	 * @throws NEDSSAppException
	 */
	@SuppressWarnings("unchecked")
	private static void populateNonRepeatData(
			String answertext, int investigationCounterNumber, NbsQuestionMetadata metadata, boolean multiAnswer)
					throws NEDSSAppException {
		try {
	
				if(metadata.getQuestionIdentifier()!=null && metadata.getQuestionIdentifier().equalsIgnoreCase("63939_3"))
					System.out.println("Aqui");
			if(formSpecificQuestionMap.containsKey(metadata.getQuestionIdentifier()) && investigationCounterNumber == 1){
				
				String previousAnswers = formSpecificQuestionAnswerMap.get(metadata.getQuestionIdentifier());
				String answer = getAnswer(multiAnswer, previousAnswers, metadata, answertext);
				
				formSpecificQuestionAnswerMap.put(metadata.getQuestionIdentifier(), answer);
			
			}else if(formSpecificQuestionMap.containsKey(metadata.getQuestionIdentifier()+ SEPERATOR+investigationCounterNumber)){
				
				String previousAnswers = formSpecificQuestionAnswerMap.get(metadata.getQuestionIdentifier()+ SEPERATOR+investigationCounterNumber);
				String answer = getAnswer(multiAnswer, previousAnswers, metadata, answertext);

				formSpecificQuestionAnswerMap.put(metadata.getQuestionIdentifier()+ SEPERATOR+investigationCounterNumber, answer);
			
			}else if(formSpecificQuestionMap.containsKey(metadata.getQuestionIdentifier()+CODED_VALUE) && investigationCounterNumber == 1){
				
				
				String previousAnswers = formSpecificQuestionAnswerMap.get(metadata.getQuestionIdentifier()+ CODED_VALUE);
				String answer = getAnswer(multiAnswer, previousAnswers, metadata, answertext);
				//In case of answers like OTH^.... and we just need OTH
				if(answer!=null && answer.indexOf("^")!=-1){
					String answerTxt= answer;
				
					answer =answer.substring(0,answer.indexOf("^"));
				
					if(formSpecificQuestionMap.containsKey(metadata.getQuestionIdentifier()+OTH_VALUE)){
						
						answerTxt =answerTxt.substring(answerTxt.indexOf("^")+1);
						formSpecificQuestionAnswerMap.put(metadata.getQuestionIdentifier()+ OTH_VALUE, answerTxt);
					}
				}
				formSpecificQuestionAnswerMap.put(metadata.getQuestionIdentifier()+ CODED_VALUE, answer);
			
				}else if(formSpecificQuestionMap.containsKey(metadata.getQuestionIdentifier()+ SEPERATOR+investigationCounterNumber+CODED_VALUE)){
				
				String previousAnswers = formSpecificQuestionAnswerMap.get(metadata.getQuestionIdentifier()+ SEPERATOR+investigationCounterNumber+CODED_VALUE);
				String answer = getAnswer(multiAnswer, previousAnswers, metadata, answertext);
				//In case of answers like OTH^.... and we just need OTH
				if(answer!=null && answer.indexOf("^")!=-1){
					String answerTxt=answer;
					answer =answer.substring(0,answer.indexOf("^"));
				
				
					if(formSpecificQuestionMap.containsKey(metadata.getQuestionIdentifier()+SEPERATOR+investigationCounterNumber+OTH_VALUE)){
						
						answerTxt =answerTxt.substring(answerTxt.indexOf("^")+1);
						formSpecificQuestionAnswerMap.put(metadata.getQuestionIdentifier()+SEPERATOR+investigationCounterNumber+ OTH_VALUE, answerTxt);
					}


					
				}
				formSpecificQuestionAnswerMap.put(metadata.getQuestionIdentifier()+ SEPERATOR+investigationCounterNumber+CODED_VALUE, answer);
			
			}
			else if(formSpecificQuestionMap.containsKey(metadata.getQuestionIdentifier()+ SEPERATOR+investigationCounterNumber+OTH_VALUE)){
				
				String previousAnswers = formSpecificQuestionAnswerMap.get(metadata.getQuestionIdentifier()+ SEPERATOR+investigationCounterNumber+OTH_VALUE);
				String answer = getAnswer(multiAnswer, previousAnswers, metadata, answertext);
				//In case of answers like OTH^.... and we just need OTH
				if(answer!=null && answer.indexOf("^")!=-1)
					answer =answer.substring(answer.indexOf("^")+1);
				
				formSpecificQuestionAnswerMap.put(metadata.getQuestionIdentifier()+ SEPERATOR+investigationCounterNumber+OTH_VALUE, answer);
			
			}
			
			else if(formSpecificQuestionMap.containsKey(metadata.getQuestionIdentifier()+ SEPERATOR+investigationCounterNumber+CODED_VALUE_TRANSLATED)){
				String previousAnswers = formSpecificQuestionAnswerMap.get(metadata.getQuestionIdentifier()+ SEPERATOR+investigationCounterNumber+CODED_VALUE_TRANSLATED);
				String answer = getAnswer(multiAnswer, previousAnswers, metadata, answertext);

				
				formSpecificQuestionAnswerMap.put(metadata.getQuestionIdentifier()+ SEPERATOR+investigationCounterNumber+CODED_VALUE_TRANSLATED, answer);
		
			} else if(formSpecificQuestionMap.containsKey(metadata.getQuestionIdentifier()+CODED_VALUE_TRANSLATED) && investigationCounterNumber == 1){
			
				String previousAnswers = formSpecificQuestionAnswerMap.get(metadata.getQuestionIdentifier()+CODED_VALUE_TRANSLATED);
				String answer = getAnswer(multiAnswer, previousAnswers, metadata, answertext);

				
				formSpecificQuestionAnswerMap.put(metadata.getQuestionIdentifier()+CODED_VALUE_TRANSLATED, answer);
			}


		}
		catch (Exception e) {
			logger.error(e);
			logger.error("CommonPDFPrintForm.populateNonRepeatData Exception thrown, "+ e);
			throw new NEDSSAppException("CommonPDFPrintForm.populateNonRepeatData Exception thrown, ", e);
		}
	}

	/**
	 * Put a space between chars to show correctly on the form.
	 * @param st
	 * @return
	 */
	public static String addEmptySpaces(String st) {
		return addEmptySpaces(st," ");
	}
	
	/**
	 * Put a space between chars to show correctly on the form.
	 * @param st
	 * @param space
	 * @return
	 */
	public static String addEmptySpaces(String st, String space) {
		StringBuffer sb = new StringBuffer();
		
		sb.append(space);
		for (int i = 0; i < st.length(); i++) {
			sb.append(st.charAt(i)).append(space);
		}
		return sb.toString();
	}
	
	/**
	 * Get the STD-MIS condition code to display.
	 * @param cd
	 * @return
	 */
	protected static String getAltCond(String cd) {
		String text;
		text = cd;
		if(text != null){
			CachedDropDownValues cddv = new CachedDropDownValues();
			String shortcd = cddv.getDiagnosisCodeForConceptCode(text);
			if((shortcd!=null && shortcd.equalsIgnoreCase("350")) || (text!=null && text.equalsIgnoreCase("10280")) )//Gonorrhea and Gonorrhea resistant have the same concept_code=10280
				shortcd="300";
			
			text = shortcd != null ? shortcd : text;
			
		}
		return text;
	}
	
	/**
	 * Get the juris desc for the Field Record from.
	 * @param cd
	 * @return
	 */
	protected static String getJurisdictionDesc(String cd) {
		String text;
		text = cd;
		if(text != null){
			CachedDropDownValues cddv = new CachedDropDownValues();
			String description = cddv.getJurisdictionDesc(text);

			text = description != null ? description : text;
		}
		return text;
	}
	
	/**
	 * getStateByCode() it returns the name of the state from the code
	 * 
	 * @param cd
	 * @return
	 */
	protected static String getStateByCode(String cd){
		
		String text;
		text = cd;
		if(text != null){
			CachedDropDownValues cddv = new CachedDropDownValues();
			String description=null;
			
			if(cddv.getStateTreeMap().get(text)!=null)
				description = cddv.getStateTreeMap().get(text).toString();

			text = description != null ? description : text;
		}
		return text;
	}

	/**
	 * Get the AR (Lab or Morb) that is the source of the investigation , if any
	 * Uses the time to determine the source.
	 * @param proxyVO
	 * @return
	 * @throws NEDSSAppException
	 */
	public static ActRelationshipDT getSourceAct(PageProxyVO proxyVO) throws NEDSSAppException {
		try {
			Timestamp addTime = proxyVO.getPublicHealthCaseVO()
					.getThePublicHealthCaseDT().getAddTime();
			Collection<Object> coll = proxyVO.getPublicHealthCaseVO()
					.getTheActRelationshipDTCollection();
			Iterator<Object> iterator = coll.iterator();
			while (iterator.hasNext()) {
				ActRelationshipDT ar = (ActRelationshipDT) iterator.next();
				//			DateFormat format =  DateFormat.getDateInstance(DateFormat.MEDIUM);
				//
				//			if (ar.getFromTime() != null
				//					&& format.format(addTime).equals(format.format(ar.getFromTime())) )
				if (ar.getFromTime() != null
						&& addTime.compareTo(ar.getFromTime())==0 )
					return ar;
			}
		} catch (Exception e) {
			logger.error("Error while retrieving getSourceAct:  "+ e);
			throw new NEDSSAppException("Error while retrieving getSourceAct:  "+e);
		}

		return null;

	}

	/**
	 * populateLabsWithSOurceLabresultsFirst - if a lab result is the source of an Investigation, process it first. 
	 * If a morb started the investigation, process all the labs in the morb first.
	 * @param sourceObsUid
	 * @param actProxyVO
	 * @param labsProcessed
	 * @param invCnt 
	 * @throws Exception
	 */
	private static void populateLabsWithSourceLabResultsFirst(Long sourceObsUid,  PageActProxyVO actProxyVO , ArrayList<String> labsProcessed, int invCnt) throws Exception {
		Timestamp firstLabDate = null;
		try {

			//if the sourceObsUid is not null, see if the source is a lab or morb
			//and get the lab collection and process it first.
			if (sourceObsUid != null) {
				Collection<Object> sourceLabSummaryCollection = null;
				if (actProxyVO.getTheMorbReportSummaryVOCollection() != null && !actProxyVO.getTheMorbReportSummaryVOCollection().isEmpty()) {
					//only one morb report
					Iterator<Object> morbIt  = 	actProxyVO.getTheMorbReportSummaryVOCollection().iterator();
					MorbReportSummaryVO morbSummaryVO = (MorbReportSummaryVO)morbIt.next();
					//Is this a T2?
					if (morbSummaryVO.getUid() != null && morbSummaryVO.getUid().longValue() == sourceObsUid.longValue() && !morbSummaryVO.isMorbFromDoc()) {
						sourceLabSummaryCollection = morbSummaryVO.getTheLabReportSummaryVOColl();
						if (sourceLabSummaryCollection != null && !sourceLabSummaryCollection.isEmpty()) {
							Iterator<Object> labCollIter = sourceLabSummaryCollection.iterator();
							//process all labs in the morb into the staging area
							while (labCollIter.hasNext()) {
								LabReportSummaryVO labRptSumVO = (LabReportSummaryVO) labCollIter.next();
								if(!labsProcessed.contains(labRptSumVO.getLocalId())){
									processLabIntoLabStagingList(labRptSumVO, labStagingArray, morbSummaryVO.getProviderDataForPrintVO().getFacility());
									labsProcessed.add(labRptSumVO.getLocalId());
								}
							}
						}
					}
				}
				if (sourceLabSummaryCollection == null) { //must be a T2
					Collection<Object> labColl = actProxyVO.getTheLabReportSummaryVOCollection();
					if (labColl != null && !labColl.isEmpty()) {
						//find the lab that was the source observation 
						Iterator<Object> labCollIter = labColl.iterator();
						while (labCollIter.hasNext()) {
							LabReportSummaryVO labRptSumVO = (LabReportSummaryVO) labCollIter.next();
							Timestamp labDate = labRptSumVO.getDateCollected();
							if (labDate == null)
								labDate = labRptSumVO.getDateReceived();
							if (firstLabDate == null && labDate!=null)
								firstLabDate = labDate;
							else if (firstLabDate != null && labDate != null) {
								if (firstLabDate.after(labDate))
									firstLabDate = labDate;
							}
							if (labRptSumVO.getObservationUid().longValue() == sourceObsUid.longValue() && !labRptSumVO.isLabFromDoc())
								if(!labsProcessed.contains(labRptSumVO.getLocalId())){
									processLabIntoLabStagingList(labRptSumVO, labStagingArray, null);
									labsProcessed.add(labRptSumVO.getLocalId());
								}
						}
						//now put the rest into the staging area if there is room
						labCollIter = labColl.iterator();
						while (labCollIter.hasNext()) {
							LabReportSummaryVO labRptSumVO = (LabReportSummaryVO) labCollIter.next();
							if (labRptSumVO.getObservationUid().longValue() != sourceObsUid.longValue() && !labRptSumVO.isLabFromDoc()) {
								if(!labsProcessed.contains(labRptSumVO.getLocalId())){
									processLabIntoLabStagingList(labRptSumVO, labStagingArray, null);
									labsProcessed.add(labRptSumVO.getLocalId());
								}
							}
						}

					}
				}
			}

			//Now go through the lab collection and see if we can add to our list
			Collection<Object> coll = actProxyVO.getTheLabReportSummaryVOCollection();
			//if there is a source observation (investigation started from a lab) - use that one
			if (coll != null && !coll.isEmpty() && labIndex< 5){
				Iterator<Object> collIter = coll.iterator();
				while (collIter.hasNext()) {
					LabReportSummaryVO labReportSummaryVO = (LabReportSummaryVO) collIter.next();
					Timestamp labDate = labReportSummaryVO.getDateCollected();
					if (labDate == null)
						labDate = labReportSummaryVO.getDateReceived();
					if (firstLabDate == null && labDate!=null)
						firstLabDate = labDate;
					else if (firstLabDate != null && labDate != null) {
						if (firstLabDate.after(labDate))
							firstLabDate = labDate;
					}
					if(!labsProcessed.contains(labReportSummaryVO.getLocalId()) && !labReportSummaryVO.isLabFromDoc()){
						processLabIntoLabStagingList(labReportSummaryVO, labStagingArray, null);
						labsProcessed.add(labReportSummaryVO.getLocalId());
					}
				} //has next
			} //coll != null


			if(actProxyVO.getTheMorbReportSummaryVOCollection() != null 
					&& !actProxyVO.getTheMorbReportSummaryVOCollection().isEmpty() && labIndex< 5){
				Iterator<Object> it  = 	actProxyVO.getTheMorbReportSummaryVOCollection().iterator();
				MorbReportSummaryVO summaryVO = (MorbReportSummaryVO)it.next();
				if(summaryVO.getTheLabReportSummaryVOColl()!=null && !summaryVO.isMorbFromDoc()){
					Iterator<Object> collIter = summaryVO.getTheLabReportSummaryVOColl().iterator();
					while (collIter.hasNext()) {
						LabReportSummaryVO labReportSummaryVO = (LabReportSummaryVO) collIter.next();
						if(!labsProcessed.contains(labReportSummaryVO.getLocalId())){
							processLabIntoLabStagingList(labReportSummaryVO, labStagingArray, summaryVO.getProviderDataForPrintVO().getFacility());
							labsProcessed.add(labReportSummaryVO.getLocalId());
							if (labIndex==4)
								break;
						}
					}
				}
			}
			//This field is the earliest lab date
			if (firstLabDate != null) {
				String firstLabDateStr = StringUtils.formatDate(firstLabDate);
				if (firstLabDateStr != null)
					formSpecificQuestionAnswerMap.put(FirstLabDateLAB201+invCnt, firstLabDateStr);
			}
		} catch (Exception e) {
			logger.debug("Error while processing populateLabsWithSourceLabResultsFirst:  "+ e);
			logger.error("Error while processing populateLabsWithSourceLabResultsFirst:  " + e);
			throw new NEDSSAppException("Application error while processing populateLabsWithSourceLabResultsFirst:  " + e);
		}

	}

	/**
	 * processLabIntoLabStagingList - Process the lab into the two dimentional string array staging area
	 * @param labReportSummaryVO
	 * @param theLabStagingArray
	 * @throws NEDSSAppException
	 */
	protected static void processLabIntoLabStagingList(LabReportSummaryVO labReportSummaryVO,
			String[][] theLabStagingArray, String morbRptFacilityName) throws NEDSSAppException {

		boolean organismNamePresent = false;
		if(labIndex > 8)
			return;
		try {
		if (labReportSummaryVO
				.getTheResultedTestSummaryVOCollection() != null && !labReportSummaryVO.isLabFromDoc()) {
			Iterator<Object> iter = labReportSummaryVO.getTheResultedTestSummaryVOCollection().iterator();
			while (iter.hasNext()) {
				ResultedTestSummaryVO resultedSummaryVO = (ResultedTestSummaryVO) iter.next();
				//build the Result string
				StringBuffer labResultSB = new StringBuffer("");
				labResultSB
				.append(resultedSummaryVO
						.getNumericResultCompare() == null ? EMPTY_STRING
								: " "+resultedSummaryVO
								.getNumericResultCompare());
				labResultSB
				.append(resultedSummaryVO
						.getNumericResultValue1() == null ? EMPTY_STRING
								: " "+resultedSummaryVO
								.getNumericResultValue1());
				labResultSB
				.append(resultedSummaryVO
						.getNumericResultSeperator() == null ? EMPTY_STRING
								: " "+resultedSummaryVO
								.getNumericResultSeperator());
				labResultSB
				.append(resultedSummaryVO
						.getNumericResultValue2() == null ? EMPTY_STRING
								: " "+resultedSummaryVO
								.getNumericResultValue2());
				labResultSB
				.append(resultedSummaryVO
						.getNumericResultUnits() == null ? EMPTY_STRING
								: " "+resultedSummaryVO
								.getNumericResultUnits());
				
				if (resultedSummaryVO.getOrganismName() != null && !resultedSummaryVO.getOrganismName().isEmpty()) {
					if (!(resultedSummaryVO.getCodedResultValue() != null && resultedSummaryVO.getCodedResultValue().contains(resultedSummaryVO.getOrganismName()))) {
						if (labResultSB.length() > 0)
							labResultSB.append("/");
						labResultSB.append(resultedSummaryVO.getOrganismName()).append(" ");
						organismNamePresent = true;
					}
				}
					
				if (resultedSummaryVO.getTextResultValue() != null && !resultedSummaryVO.getTextResultValue().isEmpty()) {
					if (labResultSB.length() > 0)
						labResultSB.append("/");
					labResultSB.append(resultedSummaryVO.getTextResultValue()).append(" ");
				}
				String labResultStr =  null;
				if(resultedSummaryVO.getCodedResultValue()!=null && !(labResultSB.length() == 0) && !labResultSB.toString().trim().equals("")){
					if (organismNamePresent)
						labResultStr =  labResultSB.toString();
					else
						labResultStr =  resultedSummaryVO.getCodedResultValue()+"/"+labResultSB.toString();
					
				}else if(!(labResultSB.length() == 0) && !labResultSB.toString().trim().equals("") && resultedSummaryVO.getCodedResultValue()==null){
					labResultStr = labResultSB.toString();
					
				}else if( ((labResultSB.length() == 0) || labResultSB.toString().trim().equals("")) && resultedSummaryVO.getCodedResultValue()!=null){
					labResultStr = resultedSummaryVO.getCodedResultValue();
				}
				
				labStagingArray[labIndex-1][TEST_INDEX] =  resultedSummaryVO.getResultedTest();
				if (labResultStr != null)
					labStagingArray[labIndex-1][RESULT_INDEX] =  labResultStr;
				else
					labStagingArray[labIndex-1][RESULT_INDEX] = " ";
				//Per JW, for Morb use the Morb Facility for the Lab facility
				String theReportingFacility = labReportSummaryVO.getReportingFacility();
				if (theReportingFacility == null || theReportingFacility.isEmpty()) {
					if (morbRptFacilityName != null && !morbRptFacilityName.isEmpty())
						theReportingFacility = morbRptFacilityName;
				}
				labStagingArray[labIndex-1][LAB_INDEX] = 
							checkNull(theReportingFacility);
				
				//Date for every resulted test of a lab report
				Timestamp  labDate = null;
				if (labReportSummaryVO.getDateCollected() != null)
					labDate = labReportSummaryVO.getDateCollected();
				else
					labDate = labReportSummaryVO.getDateReceived();
				String labDateStr = formatDate(labDate);
				labStagingArray[labIndex-1][DATE_INDEX] = labDateStr;
				labStagingArray[labIndex-1][FORMATTED_FOR_SORT_DATE_INDEX] = formatDateForSort(labDate);
				
				String specimenSource = labReportSummaryVO.getSpecimenSource();
				labStagingArray[labIndex-1][SPECIMEN_SOURCE_INDEX] = specimenSource;
				
				labIndex++;
				
				if(labIndex > 8)
					break;
			} //hasNext lab result
		} //resulted test coll not null
		
		} catch (Exception e) {
			logger.error("Error while processing processLabIntoLabStagingList:  " + e.getMessage(), e);
			throw new NEDSSAppException("Application error occurred while processing processLabIntoLabStagingList:  " + e);
		}
		return; 
	}
	
	/**
	 * Format timestamp into date string
	 * @param timestamp
	 * @return
	 */
	protected static String formatDate(Timestamp timestamp) {
		java.util.Date date = null;
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		if(timestamp != null) date = new java.util.Date(timestamp.getTime());
	      if (date == null) {
	          return new String("");
	       }
	       else {
	          return formatter.format(date);
	       }
	}
	/**
	 * Format timestamp into date string
	 * @param timestamp
	 * @return
	 */
	protected static String formatDateForSort(Timestamp timestamp) {
		java.util.Date date = null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		if(timestamp != null) date = new java.util.Date(timestamp.getTime());
	      if (date == null) {
	          return new String("");
	       }
	       else {
	          return formatter.format(date);
	       }
	}

/**
 * Put name and address information into a return map.
 * @param proVO
 * @param nameKey
 * @param includePrefix
 * @param streetAdrKey
 * @param cityAdrKey
 * @param stateAdrKey
 * @param zipAdrKey
 * @param wkPhoneKey
 * @param emailKey
 * @return <String, String> map of values
 */
	public static Map<String, String> putEntityNameAndAddressIntoMap(PersonVO proVO, String nameKey, boolean includePrefix, String streetAdrKey, String cityAdrKey, String stateAdrKey, String zipAdrKey, String wkPhoneKey, String emailKey) {

		Map<String, String> returnMap = new HashMap<String, String>();

		PersonDT personDT = null;
		PostalLocatorDT postalDT = null;
		TeleLocatorDT teleDT = null;

		StringBuffer stBuff = new StringBuffer("");
		personDT = proVO.getThePersonDT();

		if (proVO.getThePersonNameDTCollection() != null) {

			Iterator personNameIt = proVO.getThePersonNameDTCollection()
					.iterator();
			while (personNameIt.hasNext()) {
				PersonNameDT personNameDT = (PersonNameDT) personNameIt.next();
				if (personNameDT.getNmUseCd().equalsIgnoreCase("L")) {
					if (includePrefix)
						stBuff.append((personNameDT.getNmPrefix() == null) ? ""
								: (personNameDT.getNmPrefix() + " "));
					stBuff.append((personNameDT.getFirstNm() == null) ? ""
							: (personNameDT.getFirstNm() + " "));
					stBuff.append((personNameDT.getLastNm() == null) ? ""
							: (personNameDT.getLastNm()));
					stBuff.append((personNameDT.getNmSuffix() == null) ? ""
							: (", " + personNameDT.getNmSuffix()));
					stBuff.append(
							(personNameDT.getNmDegree() == null) ? ""
									: (", " + personNameDT.getNmDegree()));
					if (nameKey != null)
						returnMap.put(nameKey, stBuff.toString());
				}
			}
		}

		if (proVO.getTheEntityLocatorParticipationDTCollection() != null) {
			Iterator entIt = proVO
					.getTheEntityLocatorParticipationDTCollection().iterator();
			while (entIt.hasNext()) {
				EntityLocatorParticipationDT entityDT = (EntityLocatorParticipationDT) entIt
						.next();
				if (entityDT != null) {
					if (entityDT.getCd() != null
							&& entityDT.getCd().equalsIgnoreCase("O")
							&& entityDT.getClassCd() != null
							&& entityDT.getClassCd().equalsIgnoreCase("PST")
							&& entityDT.getRecordStatusCd() != null
							&& entityDT.getRecordStatusCd().equalsIgnoreCase(
									"ACTIVE")
									&& entityDT.getUseCd().equalsIgnoreCase("WP")) {
						postalDT = entityDT.getThePostalLocatorDT();
						stBuff = new StringBuffer("");
						stBuff.append((postalDT.getStreetAddr1() == null) ? ""
								: (postalDT.getStreetAddr1()));
						stBuff.append((postalDT.getStreetAddr2() == null) ? ""
								: (", " + postalDT.getStreetAddr2()));
						if (streetAdrKey != null)
							returnMap.put(streetAdrKey, stBuff.toString());
						if (cityAdrKey != null) {
							String cityStr = postalDT.getCityDescTxt() == null ? ""
									: postalDT.getCityDescTxt();
							returnMap.put(cityAdrKey, cityStr);

						}
						if (stateAdrKey != null) {
							String stateStr = postalDT.getStateCd() == null ? "" 
									: getStateDescTxt(postalDT.getStateCd());
							returnMap.put(stateAdrKey, stateStr);
						}
						if (zipAdrKey != null)
							returnMap.put(zipAdrKey, postalDT.getZipCd() == null ? ""
									: postalDT.getZipCd());

					} //address fields

					if (entityDT.getClassCd() != null) {
						if (entityDT.getClassCd().equalsIgnoreCase(
								"TELE")
								&& entityDT.getRecordStatusCd() != null
								&& entityDT.getRecordStatusCd()
								.equalsIgnoreCase("ACTIVE")
								&& entityDT.getCd() != null
								&& (entityDT.getCd().equalsIgnoreCase("O") || entityDT.getCd().equalsIgnoreCase("PH")  || entityDT.getCd().equalsIgnoreCase("FAX"))
								&& entityDT.getUseCd() != null
								&& entityDT.getUseCd().equalsIgnoreCase("WP")) {
							teleDT = entityDT.getTheTeleLocatorDT();

							if (emailKey != null)
								returnMap.put(emailKey, teleDT.getEmailAddress() == null ? ""
										: (teleDT.getEmailAddress()));
							if (wkPhoneKey != null) {
								stBuff = new StringBuffer("");
								stBuff.append((teleDT.getPhoneNbrTxt() == null) ? ""
										: (teleDT.getPhoneNbrTxt() + " "));
								String ext = "";
								//ELRs store an .0 after the extension
								if(teleDT.getExtensionTxt()!=null && !teleDT.getExtensionTxt().equals("0.0")){
									ext = teleDT.getExtensionTxt().replace(".0", "");
									stBuff.append("Ext." + ext);
								}
								returnMap.put(wkPhoneKey, stBuff.toString());

							}
						}
					}//phone get class cd
				}
			}
		}
		return returnMap;
	}
	
	/**
	 * If the PHC has a value in NBS291 (ND-3847) which is a participation OrgAsClinicOfPHC then this
	 * address should show. In particular, they want the Fax number from this.
	 * @param proVO
	 * @param facilityVO
	 * @param nameKey
	 * @param includePrefix
	 * @param streetAdrKey
	 * @param cityAdrKey
	 * @param stateAdrKey
	 * @param zipAdrKey
	 * @param wkPhoneKey
	 * @param emailKey
	 * @return
	 */
	public static Map<String, String> putEntityNameAndAddressIntoMap(PersonVO proVO, OrganizationVO orgVO, String nameKey, boolean includePrefix, String streetAdrKey, String cityAdrKey, String stateAdrKey, String zipAdrKey, String wkPhoneKey, String emailKey) {

		Map<String, String> returnMap = new HashMap<String, String>();

		PersonDT personDT = null;
		PostalLocatorDT postalDT = null;
		TeleLocatorDT teleDT = null;

		StringBuffer stBuff = new StringBuffer("");
		personDT = proVO.getThePersonDT();

		if (proVO.getThePersonNameDTCollection() != null) {

			Iterator personNameIt = proVO.getThePersonNameDTCollection()
					.iterator();
			while (personNameIt.hasNext()) {
				PersonNameDT personNameDT = (PersonNameDT) personNameIt.next();
				if (personNameDT.getNmUseCd().equalsIgnoreCase("L")) {
					if (includePrefix)
						stBuff.append((personNameDT.getNmPrefix() == null) ? ""
								: (personNameDT.getNmPrefix() + " "));
					stBuff.append((personNameDT.getFirstNm() == null) ? ""
							: (personNameDT.getFirstNm() + " "));
					stBuff.append((personNameDT.getLastNm() == null) ? ""
							: (personNameDT.getLastNm()));
					stBuff.append((personNameDT.getNmSuffix() == null) ? ""
							: (", " + personNameDT.getNmSuffix()));
					stBuff.append(
							(personNameDT.getNmDegree() == null) ? ""
									: (", " + personNameDT.getNmDegree()));
					if (nameKey != null)
						returnMap.put(nameKey, stBuff.toString());
				}
			}
		}
		if (orgVO.getTheOrganizationNameDTCollection() != null) {
		      Collection<Object>  names = orgVO.getTheOrganizationNameDTCollection();
		      if (names != null) {
		    	  Iterator<Object>  iter = names.iterator();
		    	  while (iter.hasNext()) {
		    		  OrganizationNameDT name = (OrganizationNameDT) iter.next();
		    		  if (name != null) {
		            // for organizationInfo
		    			  if (name.getNmTxt() != null) {
		    				  String facilityName = name.getNmTxt();
		    				  stBuff.append("/" + facilityName);
		    				  if (nameKey != null)
		  						returnMap.put(nameKey, stBuff.toString());
		    			  }
		          } //not null
		        } //hasNext
		      } //org names not null
		} //name collection not null
		if (orgVO.getTheEntityLocatorParticipationDTCollection() != null) {
			Iterator entIt = orgVO
					.getTheEntityLocatorParticipationDTCollection().iterator();
			while (entIt.hasNext()) {
				EntityLocatorParticipationDT entityDT = (EntityLocatorParticipationDT) entIt
						.next();
				if (entityDT != null) {
					if (entityDT.getCd() != null
							&& entityDT.getCd().equalsIgnoreCase("O")
							&& entityDT.getClassCd() != null
							&& entityDT.getClassCd().equalsIgnoreCase("PST")
							&& entityDT.getRecordStatusCd() != null
							&& entityDT.getRecordStatusCd().equalsIgnoreCase(
									"ACTIVE")
									&& entityDT.getUseCd().equalsIgnoreCase("WP")) {
						postalDT = entityDT.getThePostalLocatorDT();
						stBuff = new StringBuffer("");
						stBuff.append((postalDT.getStreetAddr1() == null) ? ""
								: (postalDT.getStreetAddr1()));
						stBuff.append((postalDT.getStreetAddr2() == null) ? ""
								: (", " + postalDT.getStreetAddr2()));
						if (streetAdrKey != null)
							returnMap.put(streetAdrKey, stBuff.toString());
						if (cityAdrKey != null) {
							String cityStr = postalDT.getCityDescTxt() == null ? ""
									: postalDT.getCityDescTxt();
							returnMap.put(cityAdrKey, cityStr);

						}
						if (stateAdrKey != null) {
							String stateStr = postalDT.getStateCd() == null ? "" 
									: getStateDescTxt(postalDT.getStateCd());
							returnMap.put(stateAdrKey, stateStr);
						}
						if (zipAdrKey != null)
							returnMap.put(zipAdrKey, postalDT.getZipCd() == null ? ""
									: postalDT.getZipCd());

					} //address fields

					if (entityDT.getClassCd() != null) {
						if (entityDT.getClassCd().equalsIgnoreCase(
								"TELE")
								&& entityDT.getRecordStatusCd() != null
								&& entityDT.getRecordStatusCd()
								.equalsIgnoreCase("ACTIVE")
								&& entityDT.getCd() != null
								&& (entityDT.getCd().equalsIgnoreCase("O") || entityDT.getCd().equalsIgnoreCase("PH") || entityDT.getCd().equalsIgnoreCase("FAX"))
								&& entityDT.getUseCd() != null
								&& entityDT.getUseCd().equalsIgnoreCase("WP")) {
							
							teleDT = entityDT.getTheTeleLocatorDT();

							if (emailKey != null)
								returnMap.put(emailKey, teleDT.getEmailAddress() == null ? ""
										: (teleDT.getEmailAddress()));
							if (wkPhoneKey != null) {
								if (returnMap.get(wkPhoneKey) != null)
									stBuff = new StringBuffer(returnMap.get(wkPhoneKey) + "\n");
								else 
									stBuff = new StringBuffer("");
								stBuff.append((teleDT.getPhoneNbrTxt() == null) ? ""
										: (teleDT.getPhoneNbrTxt() + " "));
								String ext = "";
								if(teleDT.getExtensionTxt()!=null && !teleDT.getExtensionTxt().equals("0.0")){
									ext = teleDT.getExtensionTxt().replace(".0", "");
									stBuff.append("Ext." + ext);
								}
								stBuff.append("(" + entityDT.getCd() + ") ");
								returnMap.put(wkPhoneKey, stBuff.toString());
							}
						} //if tele
					}//phone get class cd
				} //entityDT not null
			}
		}
		returnMap.put("ClinicPresent", "NBS291"); //flag - this takes precidence over the Lab
		return returnMap;
	}
	
	
	private static String getOtherInfo(){
		
		if (answerMap == null)
			return null;
		String otherInfo = null;
		if (answerMap.containsKey(PageConstants.OTHER_IDENTIFYING_INFORMATION)) {
			otherInfo = (String) answerMap.get(PageConstants.OTHER_IDENTIFYING_INFORMATION);
		}
		
		
		if (otherInfo != null && !otherInfo.isEmpty()) {
			otherInfo+="\n";
		}
		
		
		return otherInfo;
		
	}
	
	
	/**
	 * OTHER_INFO containsINV182 (physician and associated address); NBS159 (other identifying info)
	 * @param mappedEntityValues
	 * @param answerMap
	 * @return
	 */
	private static String getProviderOtherInfo(
			Map<String, String> mappedEntityValues,
			Map<Object, Object> answerMap) {
		StringBuffer strBuff = new StringBuffer("");


		if (mappedEntityValues == null || answerMap == null)
			return null;
		String otherInfo = null;
		if (answerMap.containsKey(PageConstants.OTHER_IDENTIFYING_INFORMATION)) {
			otherInfo = (String) answerMap.get(PageConstants.OTHER_IDENTIFYING_INFORMATION);
		}

		try{

			if (mappedEntityValues.containsKey(PROVIDER_NAME)) {
				strBuff.append(mappedEntityValues.get(PROVIDER_NAME));
				if (otherInfo == null || otherInfo.isEmpty())
					strBuff.append("\n");
				else 
					strBuff.append(", ");
				if (mappedEntityValues.containsKey(PROVIDER_ADDRESS)) {
					strBuff.append(mappedEntityValues.get(PROVIDER_ADDRESS));
					if (otherInfo == null || otherInfo.isEmpty())
						strBuff.append("\n");
					else 
						strBuff.append(", ");
				}
				if (mappedEntityValues.containsKey(PROVIDER_CITY)) {
					strBuff.append(mappedEntityValues.get(PROVIDER_CITY));
					if (mappedEntityValues.containsKey(STATE_STATE_SHORT_NAME)) {
						strBuff.append(", ");
						strBuff.append(mappedEntityValues.get(STATE_STATE_SHORT_NAME));
					}
					if (mappedEntityValues.containsKey(PROVIDER_ZIP)) {
						strBuff.append(" ");
						strBuff.append(mappedEntityValues.get(PROVIDER_ZIP));
					}
					strBuff.append("\n");
				}
				if (mappedEntityValues.containsKey(PROVIDER_PHONE)) {
					strBuff.append(mappedEntityValues.get(PROVIDER_PHONE));
					strBuff.append(" (W)");
				}				

			}

			if (otherInfo != null && !otherInfo.isEmpty()) {
				strBuff.append("\n");
				strBuff.append(otherInfo);
			}

		}catch(Exception e){
			logger.error("getProviderOtherInfo: Error while getting Other Info value:" + e.getMessage());
			return "";
		}
		return strBuff.toString();
	}

	/**
	 * Get the 2 digit state code from the cache i.e. 13=GA
	 * @param sStateCd
	 * @return
	 */

	protected static String getStateDescTxt(String sStateCd) {

		String desc = "";
		if (sStateCd != null && !sStateCd.trim().equals("")) {
			CachedDropDownValues srtValues = new CachedDropDownValues();
			//TreeMap treemap = srtValues.getStateCodes2("USA");
			TreeMap treemap = srtValues.getStateAbbreviationsByCode();
			if (treemap != null) {
				if (sStateCd != null && treemap.get(sStateCd) != null) {
					desc = (String) treemap.get(sStateCd);
				}
			}
		}

		return desc;
	}
	/**
	 * Put interview values into form Ans Map if present.
	 * @param investigationCounter
	 * @param actProxyVO
	 * @param req
	 * @return map with Interview Values
	 * @throws NEDSSAppException
	 */
	private static Map<String, String>  populateInterviewIntoMap(int investigationCounter, PageActProxyVO actProxyVO, HttpServletRequest req) throws NEDSSAppException {

		Map<String, String> returnMap = new HashMap<String,String>();
		//Map<Object, Object>  thisAnswerMap = updateMapWithQIds(((PageActProxyVO) proxyVO).getPageVO()
		//		.getPamAnswerDTMap());


		if(actProxyVO.getPublicHealthCaseVO().getTheCaseManagementDT().getPatIntvStatusCd()!= null){
			String patientInterviewStatus = actProxyVO.getPublicHealthCaseVO().getTheCaseManagementDT().getPatIntvStatusCd();
			String status = NBS192_R_PatientInterviewStatusMap.get(patientInterviewStatus);
			returnMap.put("NBS192_I2_"+investigationCounter+CODED_VALUE_TRANSLATED, status);
		}
		
		if(actProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getLocalId() != null){
			returnMap.put("New_CaseNo_"+investigationCounter, actProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getLocalId());
		}
		//per Anthony Meriweather, only use Diagnosis Reported to CDC
		//sooo comment this out..shouldn't be in this method anyway!
		//String condCd = actProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd() ;
		//if(condCd != null){
		//	String shortcd = getAltCond(condCd);
		//	condCd = shortcd != null ? shortcd : condCd;
		//	returnMap.put(NBS136+SEPERATOR+investigationCounter+CODED_VALUE, condCd);		
		//}

		if(actProxyVO.getTheInterviewSummaryDTCollection()!=null && actProxyVO.getTheInterviewSummaryDTCollection().size()>0){
			Iterator iterator= actProxyVO.getTheInterviewSummaryDTCollection().iterator();
			while(iterator.hasNext()){
				InterviewSummaryDT interviewSummaryDT = (InterviewSummaryDT)iterator.next();
				if(interviewSummaryDT != null){
					if(interviewSummaryDT.getInterviewTypeCd()!= null){
						returnMap.put("IXS105_" + investigationCounter ,mapCode("IXS105",interviewSummaryDT.getInterviewTypeCd()));
						if(interviewSummaryDT.getInterviewTypeCd().equalsIgnoreCase("INITIAL")) {
							if	(proxyVO.getPublicHealthCaseVO().getTheCaseManagementDT().getFldFollUpDispoDate() != null ){
								String dispodate = DateFormat.getDateInstance(DateFormat.MEDIUM).format(proxyVO.getPublicHealthCaseVO().getTheCaseManagementDT().getFldFollUpDispoDate());
								returnMap.put("NBS174_" + investigationCounter, dispodate);
							}

							if(interviewSummaryDT.getInterviewerQuickCd()!=null){
								returnMap.put("IXS102_IR_I1_"+investigationCounter+CODED_VALUE, interviewSummaryDT.getInterviewerQuickCd());
							}
							if(interviewSummaryDT.getInterviewDate() != null){
								returnMap.put("IXS101_" + investigationCounter, StringUtils.formatDate(interviewSummaryDT.getInterviewDate()));
								returnMap.put("IXS101_IR_I1_" + investigationCounter, StringUtils.formatDate(interviewSummaryDT.getInterviewDate()));
							}
						}
						if(interviewSummaryDT.getInterviewStatusCd() != null){
							returnMap.put("IXS100_" + investigationCounter ,mapCode("IXS100",interviewSummaryDT.getInterviewStatusCd()));
						}



						if(interviewSummaryDT.getInterviewLocCd()!=null){
							returnMap.put("IXS106_IR_"+investigationCounter+CODED_VALUE , interviewSummaryDT.getInterviewLocCd());
						}

						if(interviewSummaryDT.getInterviewTypeCd().equalsIgnoreCase("REINTVW")){
							returnMap.put("IXS101_IR_I2_" + investigationCounter ,StringUtils.formatDate(interviewSummaryDT.getInterviewDate()));
							returnMap.put("IXS102_IR_I2_"+investigationCounter+CODED_VALUE  ,interviewSummaryDT.getInterviewerQuickCd());
						}

						if(interviewSummaryDT.getThe900SiteId()!=null){
							returnMap.put("IXS107_IR_" + investigationCounter, interviewSummaryDT.getThe900SiteId());
						}
						PageLoadUtil pageLoadUtil = new PageLoadUtil();
						String interviewPhcInvFormCd =PageManagementCommonActionUtil.checkIfPublishedPageExists(req, NEDSSConstants.INTERVIEW_BUSINESS_OBJECT_TYPE, actProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCd());
						try {
						
							pageLoadUtil.loadQuestions(interviewPhcInvFormCd);
						} catch (Exception e1) {
							logger.error("populateInterviewIntoMap: Exception loading questions -> " +e1.getMessage(), e1);
						}
						PageActProxyVO interviewProxyVO = (PageActProxyVO) PageLoadUtil.getProxyObject(
								interviewSummaryDT.getInterviewUid().toString(),
								NEDSSConstants.INTERVIEW_BUSINESS_OBJECT_TYPE, req.getSession());
						pageLoadUtil.loadQuestionKeys(interviewPhcInvFormCd);
						Map<Object, Object> interviewAnsMap = pageLoadUtil.updateMapWithQIds(interviewProxyVO.getPageVO().getAnswerDTMap());
						if(interviewAnsMap.containsKey("IXS107") || interviewAnsMap.containsKey("IXS108") ||interviewAnsMap.containsKey("IXS109")){
							String ixs107Val = getAnsFromMap(interviewAnsMap, "IXS107");
							String ixs108Val = getAnsFromMap(interviewAnsMap, "IXS108");
							String ixs109Val = getAnsFromMap(interviewAnsMap, "IXS109");

							if(ixs107Val!=null)
								returnMap.put("IXS107_1", ixs107Val);
							if(ixs108Val!=null)
								returnMap.put("IXS108_1", ixs108Val);
							if(ixs109Val!=null)
								returnMap.put("IXS109_1_CD", ixs109Val);

							returnMap.put("IXS107_IR_" + investigationCounter, ixs107Val);
						}

					}
				}
			}
		}

		try {
			PersonVO fldFupInvestgrOfPHC = PageLoadUtil.getPersonVO("FldFupInvestgrOfPHC", actProxyVO);

			if(fldFupInvestgrOfPHC != null){
				String investigatorId = fldFupInvestgrOfPHC.getEntityIdDT_s(0).getRootExtensionTxt();
				returnMap.put("NBS175_" + investigationCounter, checkNull(investigatorId));
			}
			
			PersonVO initInterviewer = PageLoadUtil.getPersonVO("InitInterviewerOfPHC", actProxyVO);
			if(initInterviewer!=null){
				String initInvestigatorId = initInterviewer.getEntityIdDT_s(0).getRootExtensionTxt();
				returnMap.put("NBS188_" + investigationCounter+CODED_VALUE, checkNull(initInvestigatorId));
				
				PersonVO interviewer = PageLoadUtil.getPersonVO("InterviewerOfPHC", actProxyVO);	
				
				if(interviewer!=null){
					String interviewerId = interviewer.getEntityIdDT_s(0).getRootExtensionTxt();

					if(interviewerId != null && initInvestigatorId != null && !initInvestigatorId.equalsIgnoreCase(interviewerId)){
						returnMap.put(NBS186 + investigationCounter + CODED_VALUE, checkNull(interviewerId));
					}else{
						returnMap.put("NBS187_" + investigationCounter , "");
					}
				}
			}



		} catch (NEDSSAppException e) {
			logger.debug(e.getMessage());
			logger.error("populateInterview: Error occurred during processing ", e.getMessage());
			throw new NEDSSAppException("Application error occurred while processing populateInterview:  " + e);
		} catch (Exception e) {
			logger.debug(e.getMessage());
			logger.error("populateInterview: Error occurred during processing ", e.getMessage());
			throw new NEDSSAppException("Application error occurred while processing populateInterview:  " + e);
		}
		return returnMap;
	}
	

		/**
		 * The answerMap is keyed with the long value of the nbs_question_uid.
		 * The returns the map with the question_identifier (i.e DEM110) as the key
		 * @param answerMap
		 * @return
		 */
		public static Map<Object, Object> updateMapWithQIds(
				Map<Object, Object> answerMap) {
			Map<Object, Object> returnMap = new HashMap<Object, Object>();
			if (answerMap != null && answerMap.size() > 0) {
				Iterator<Object> iter = answerMap.keySet().iterator();
				while (iter.hasNext()) {
					Long key = (Long) iter.next();
					returnMap.put(questionKeyMap.get(key), answerMap.get(key));
				}
			}

			return returnMap;
		}
		/**
		 * Return the specified answer from the Contact Answer Map
		 * @param conAnsMap
		 * @param questionId
		 * @return answerTxt
		 */
		protected static String getContactAnsFromMap(Map<Object, Object> conAnsMap,
				String questionId) {
			CTContactAnswerDT contactAnswerDT = (CTContactAnswerDT) conAnsMap.get(questionId);
			if (contactAnswerDT != null)
				return contactAnswerDT.getAnswerTxt();
			return null;
		}
		/**
		 * Return the answer from the Case ans map
		 * @param invAnsMap
		 * @param questionId
		 * @return
		 */
		protected static String getInvAnsFromMap(Map<Object, Object> invAnsMap,
				String questionId) {
			NbsCaseAnswerDT invAnswerDT = (NbsCaseAnswerDT) invAnsMap.get(questionId);
			if (invAnswerDT != null)
				return invAnswerDT.getAnswerTxt();
			return null;
		}	

		/**
		 * Return the answer from the Case ans map
		 * @param invAnsMap
		 * @param questionId
		 * @return
		 */
		protected static String getAnsFromMap(Map<Object, Object> invAnsMap,
				String questionId) {
			NbsAnswerDT invAnswerDT = (NbsAnswerDT) invAnsMap.get(questionId);
			if (invAnswerDT != null)
				return invAnswerDT.getAnswerTxt();
			return null;
		}	

}
