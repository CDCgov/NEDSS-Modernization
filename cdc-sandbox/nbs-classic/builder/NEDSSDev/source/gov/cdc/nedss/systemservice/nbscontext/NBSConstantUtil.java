/**
 * Title:NBSConstantUtil class Description:Constants for NBS Context Copyright:
 * Copyright (c) 2001 Company:CSC
 *
 * @author vlosik
 * @version 1.0
 */

package gov.cdc.nedss.systemservice.nbscontext;

public class NBSConstantUtil {

	//Constants use in NBSContext
	public static final String OBJECT_STORE = "NBSObjectStore";

	public static final String CONTEXT_INFO = "NBSContextInfo";

	//Constants Specific to initializeNBSConstant
	public static final String NBS_PAGE_ID = "id";

	public static final String PAGE_ELEMENT_TREEMAP = "PAGE_ELEMENT_TREEMAP";

	//public static final String PRESERVEOBJS = "PRESERVEOBJS";
	public static final String TASK_START_MAP = "TASK_START_MAP";

	public static final String TASK_END_MAP = "TASK_END_MAP";

	public static final String ACTION = "action";

	public static final String NAME = "name"; //This is a Attribute for
											  // PageElements/PreserveObjs

	public static final String NAME_FOR_NBSPAGE = "name"; //This is a Attribute
														  // for NBSPAGE

	public static final String PAGE_ELEMENTS = "PageElements";

	public static final String PRESERVEOBJS = "PreserveObjs";

	public static final String TASKSTARTS = "TaskStarts";

	public static final String TASKENDS = "TaskEnds";

	public static final String TASKNAME = "taskName";

	public static final String NBSPAGE = "NBSPage";

	public static final String NBS_PAGE_CONTEXT = "NBSPageContext";

	public static final String NBS_TASK_OVERRIDES = "TaskOverrides";

	public static final String NBS_TASK_OVERRIDE = "TaskOverride";

	public static final String NBSCONTEXTINFO = "NBSContextInfo";

	public static final String NONE = "NONE";

	//public static final String configFileName =
	// "C:\\ContextMaps\\PersonContextMap.xml";
	public static final String configFileName = System.getProperty("nbs.dir")
			+ System.getProperty("file.separator") + "Properties"
			+ System.getProperty("file.separator") + "NBSContextMap.xml";

	public static final String GLOBAL_CONTEXT = "GLOBAL_CONTEXT";

	//Constants for Investigation Context Management
	public static final String DSInvestigationUid = "DSInvestigationUID";
	//Second investigation in compare investigations
	public static final String DSInvestigationUid1 = "DSInvestigationUID1";
	

	public static final String DSInvestigationJurisdiction = "DSInvestigationJurisdiction";

	public static final String DSPersonSummary = "DSPatientPersonUID";

	public static final String DSPatientPersonUID = "DSPatientPersonUID";

	public static final String DSPatientPersonLocalID = "DSPatientPersonLocalID";

	public static final String DSPublicHealthCaseDT = "DSPublicHealthCaseDT";

	public static final String DSConditionCode = "DSConditionCode";

	public static final String DSWorkupProxyVO = "DSWorkupProxyVO";

	public static final String DSNotificationComments = "DSNotificationComments";

	public static final String SubmitNoViewAccess = "SubmitNoViewAccess";

	public static final String INV_FORM_GEN = "INV_FORM_GEN";

	public static final String INV_FORM_MEA = "INV_FORM_MEA";

	public static final String INV_FORM_RUB = "INV_FORM_RUB";

	public static final String INV_FORM_PER = "INV_FORM_PER";

	public static final String INV_FORM_CRS = "INV_FORM_CRS";

	public static final String INV_FORM_BMIRD = "INV_FORM_BMIRD";

	public static final String INV_FORM_BMDGEN = "INV_FORM_BMDGEN";

	public static final String INV_FORM_BMDGAS = "INV_FORM_BMDGAS";

	public static final String INV_FORM_BMDGBS = "INV_FORM_BMDGBS";

	public static final String INV_FORM_BMDHI = "INV_FORM_BMDHI";

	public static final String INV_FORM_BMDNM = "INV_FORM_BMDNM";

	public static final String INV_FORM_BMDSP = "INV_FORM_BMDSP";

	public static final String INV_FORM_HEPB = "INV_FORM_HEPB";

	public static final String INV_FORM_HEPBV = "INV_FORM_HEPBV";

	public static final String INV_FORM_HEPCV = "INV_FORM_HEPCV";

	public static final String INV_FORM_HEPA = "INV_FORM_HEPA";

	public static final String INV_FORM_HEPC = "INV_FORM_HEPC";

	public static final String INV_FORM_HEPGEN = "INV_FORM_HEPGEN";

	//NBS PAMs
	public static final String INV_FORM_RVCT = "INV_FORM_RVCT";
	public static final String INV_FORM_MALR = "INV_FORM_MALR";
	public static final String INV_FORM_VAR = "INV_FORM_VAR";	
	public static final String INV_FORM_CONTACT = "CONTACT_REC";
	//Flu FormCode
	public static final String INV_FORM_FLU = "SUM_FORM_FLU";

	public static final String SUBMIT = "Submit";

	public static final String ADD = "Add";

	public static final String CANCEL = "Cancel";

	public static final String EDIT = "Edit";

	public static final String XSP = "XSP";

	public static final String VIEW = "View";

	public static final String SORT = "Sort";

	public static final String DELETE = "Delete";

	public static final String InvestigationIDOnInv = "InvestigationIDOnInv";

	public static final String InvestigationIDOnSummary = "InvestigationIDOnSummary";

	public static final String InvestigationID = "InvestigationID";

	public static final String ReturnToFileInvestigations = "ReturnToFileInvestigations";

	public static final String ReturnToFileSummary = "ReturnToFileSummary";

	public static final String AddInvestigation = "AddInvestigation";

	public static final String CreateInvestigation = "CreateInvestigation";
	
	public static final String AssociateToInvestigations = "AssociateToInvestigations";

	public static final String ViewInvestigation = "ViewInvestigation";

	public static final String EditInvestigation = "EditInvestigation";

	public static final String SelectCondition = "SelectCondition";

	public static final String DSInvestigationCondition = "DSInvestigationCondition";
	
	public static final String DSProcessingDecision = "DSProcessingDecision";
	
	public static final String DSInvestigationType = "DSInvestigationType";

	public static final String DSInvestigationProgramArea = "DSInvestigationProgramArea";
	//Program area second investigation in Compare investigations
	public static final String DSInvestigationProgramArea1 = "DSInvestigationProgramArea1";
	

	public static final String DSInvestigationCode = "DSInvestigationCode";
	
	public static final String DSReferralBasis = "DSReferralBasis";

	public static final String DSObservationUID = "DSObservationUID";

	public static final String DSObservationTypeCd = "DSObservationTypeCd";

	public static final String ObservationLabID = "ObservationLabID";

	public static final String CreateNotification = "CreateNotification";

	public static final String ObservationMorbID = "ObservationMorbID";

	public static final String ViewVaccination = "ViewVaccination";

	public static final String DSVaccinationUID = "DSVaccinationUID";

	public static final String FileSummary = "FileSummary";

	public static final String DSFileTab = "DSFileTab";

	public static final String ManageObservations = "ManageObservations";
	
	public static final String ManageEvents = "ManageEvents";

	public static final String ManageVaccinations = "ManageVaccinations";

	public static final String DSJurisdiction = "DSJurisdiction";

	public static final String DSProgramArea = "DSProgramArea";

	public static final String TransferOwnership = "TransferOwnership";

	public static final String MarkAsReviewed = "MarkAsReviewed";
	
	public static final String ClearMarkAsReviewed = "ClearMarkAsReviewed";

	public static final String AddCommentLab = "AddCommentLab";

	public static final String DSObservationLocalID = "DSObservationLocalID";

	public static final String DSInvestigationLocalID = "DSInvestigationLocalID";

	public static final String ViewVaccinationFromInv = "ViewVaccinationFromInv";

	public static final String ReturnToViewVaccination = "ReturnToViewVaccination";

	public static final String AddVaccination = "AddVaccination";

	public static final String CREATE = "Create";

	public static final String ReturnToFileVaccinations = "ReturnToFileVaccinations";

	public static final String ReturnToManageVaccinations = "ReturnToManageVaccinations";

	public static final String ReturnToViewInvestigation = "ReturnToViewInvestigation";

	public static final String DELETEDENIED = "DeleteDenied";

	public static final String DSMorbMap = "DSMorbMap";

	public static final String DSLabMap = "DSLabMap";

	public static final String DSCaseStatus = "DSCaseStatus";
	
	public static final String DocumentIDFromInv = "DocumentIDFromInv";
	
	public static final String ConfirmationMsg = "confMsg";

	//Constants for summary Data
	public static final String DSSummaryReportUID = "DSSummaryReportUID";

	public static final String DSSummaryReportInfo = "DSSummaryReportInfo";

	//public static final String DSPublicHealthCaseVO = "DSPublicHealthCaseVO";
	public static final String DSRejectedDeleteString  = "DSRejectedDeleteString";
	public static final String DSInvestigationFormCd  = "DSInvestigationFormCd";
	public static final String DSDocumentUID ="DSDocumentUID";
	public static final String DSDocConditionCD = "DSDocConditionCD";
	public static final String VaccinationIDFromInv = "VaccinationIDFromInv";
	public static final String ViewObservationLab = "ViewObservationLab";
	public static final String ViewObservationMorb = "ViewObservationMorb";
	public static final String ViewTreatment = "ViewTreatment";
	public static final String AddLab = "AddLab";
	public static final String AddMorb = "AddMorb";
	public static final String AddTreatment = "AddTreatment";
	public static final String DSTreatmentUID = "DSTreatmentUID";
	public static final String ViewDocument = "ViewDocument";
	public static final String DSPatientRevisionUID = "DSPatientRevisionUID";
	public static final String DSContactPersonUID = "DSContactPersonUID";
	public static final String DSContactUID = "DSContactUID";
	public static final String DSInvestigationPath = "DSInvestigationPath";
	public static final String DSInvestigationList = "DSInvestigationList";
	public static final String DSPersonList = "DSPersonList";
	public static final String DSPersonListFull = "DSPersonListFull";
	public static final String DSAttributeMap = "DSAttributeMap";
	public static final String DSInvestigationListFull = "DSInvestigationListFull";
	public static final String DSConditionList = "DSConditionList";
	public static final String DSManageList = "DSManageList";
	public static final String DSManageListFull = "DSManageListFull";
	public static final String DSCodeSetList = "DSCodeSetList";
	public static final String DSObservationList = "DSObservationList";
	public static final String DSFilePath = "DSFilePath";
	public static final String CONTACT_REC = "CONTACT_REC";
	public static final String DSContextVO="DSContextVO";
	public static final String DSPatientMPRUID = "DSPatientPersonUID";
	public static final String ContactTracing="ContactTracing";
	public static final String DSPatientMap = "DSPatientMap";
	public static final String DSInvestigatorUid = "DSInvestigatorUid";
	public static final String DSContactColl="DSContactColl";
	public static final String DSInterviewList="DSInterviewList";
	public static final String DSSharedInd="DSSharedInd";
	public static final String DSConditionCdDescTxt="DSConditionCdDescTxt";
	public static final String ContactRecordFormPrefix="CT";
	public static final String InvestigationFormPrefix="PG";
	public static final String DSCoinfectionInvSummVO = "DSCoinfectionInvSummVO";
	public static final String DSStdDispositionCd = "DSStdDispositionCd";
	public static final String DSSourceConditionCd = "DSSourceConditionCd";
	public static final String DSStdInterviewStatusCd = "DSStdInterviewStatusCd";
	public static final String DSPatientCurrentSexCd = "DSPatientCurrentSexCd";
	public static final String DSLotNbr = "DSLotNbr";
	public static final String DSRecordSearchClosureInvSummVO = "DSRecordSearchClosureInvSummVO";
	public static final String DSSecondaryReferralInvSummVO = "DSSecondaryReferralInvSummVO";
	public static final String DSSearchCriteriaMap = "DSSearchCriteriaMap";
	public static final String ViewElectronicDoc="viewELRDoc";
	public static final String DSisCreateInvFromViewDocument = "DSisCreateInvFromViewDocument";

}

