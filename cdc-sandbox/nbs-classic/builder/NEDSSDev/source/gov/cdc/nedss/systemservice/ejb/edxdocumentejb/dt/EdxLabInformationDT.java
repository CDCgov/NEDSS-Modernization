package gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dt;

import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.LabResultProxyVO;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.labutil.EdxELRConstants;
import gov.cdc.nedss.act.observation.vo.ObservationVO;
import gov.cdc.nedss.entity.person.vo.PersonVO;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class EdxLabInformationDT extends EdxRuleAlgorothmManagerDT  implements Serializable{

	/**
	 * Pradeep Kumar Sharma
	 * Utility class to store the information that is being shared all across the ELR processing 
	 */
	private static final long serialVersionUID = 1L;
	private Timestamp addTime;
	private Timestamp OrderEffectiveDate;
	private String role;
	private long rootObserbationUid;
	private PersonVO orderingProviderVO;
	private String sendingFacilityClia;
	private String sendingFacilityName;
	private long patientUid;
	private long userId;
	private int nextUid;
	private String fillerNumber;
	private String messageControlID;
	private long parentObservationUid;
	private boolean isOrderingProvider;
	private LabResultProxyVO labResultProxyVO;
	private String localId;
	private boolean isParentObsInd;
	private Collection<Object> edxLabIdentiferDTColl;
	private String entityName;
	private String reportingSourceName;
	private String userName;
	private String universalIdType;
	
	private Long associatedPublicHealthCaseUid;
	private long publicHealthCaseUid;
	private long notificationUid;
	private long originalAssociatedPHCUid;
	private long nbsInterfaceUid;
	
	private Timestamp specimenCollectionTime;

	private String jurisdictionName;
	private String programAreaName;
	private boolean jurisdictionAndProgramAreaSuccessfullyDerived;

	private boolean algorithmHasInvestigation;
	private boolean investigationSuccessfullyCreated;
	private boolean investigationMissingFields;
	
	private boolean algorithmHasNotification;
	private boolean notificationSuccessfullyCreated;
	private boolean notificationMissingFields;
	
	private boolean labIsCreate;
	private boolean labIsCreateSuccess;
	private boolean labIsUpdateDRRQ;
	private boolean labIsUpdateDRSA;
	
	private boolean labIsUpdateSuccess;
	private boolean labIsMarkedAsReviewed;
	private Map<Object,Object> resultedTest;
	private String conditionCode;
	private Object proxyVO;
	private Map<Object, Object> edxSusLabDTMap = new HashMap<Object, Object>();
	private String addReasonCd;
	private ObservationVO rootObservationVO;
	//Informational Variables
	private boolean multipleSubjectMatch;
	private boolean multipleOrderingProvider;
	private boolean multipleCollector;
	private boolean multiplePrincipalInterpreter;
	private boolean multipleOrderingFacility;
	private boolean multipleSpecimen;
	private boolean ethnicityCodeTranslated;
	private boolean obsMethodTranslated;
	private boolean raceTranslated;
	private boolean sexTranslated;
	private boolean ssnInvalid;
	private boolean nullClia;
	//Error Variables
	private boolean fillerNumberPresent;
	private boolean finalPostCorrected;
	private boolean preliminaryPostFinal;
	
	private boolean preliminaryPostCorrected;
	private boolean activityTimeOutOfSequence;
	private boolean multiplePerformingLab;
	private boolean orderTestNameMissing;
	private boolean reflexOrderedTestCdMissing;
	private boolean reflexResultedTestCdMissing;
	private boolean resultedTestNameMissing;
	private boolean drugNameMissing;
	private boolean obsStatusTranslated;
	private  String dangerCode;
	private  String relationship;
	private  String relationshipDesc;
	private boolean activityToTimeMissing;
	private boolean systemException;
	private boolean universalServiceIdMissing;
	private boolean missingOrderingProvider;
	private boolean missingOrderingFacility;
	private boolean multipleReceivingFacility;
	private long personParentUid;
	private boolean patientMatch;
	private boolean multipleOBR;
	private boolean multipleSubject;
	private boolean noSubject;
	private boolean orderOBRWithParent;
	private boolean childOBRWithoutParent;
	private boolean invalidXML;
	private boolean missingOrderingProviderandFacility;
	private boolean createLabPermission;
	private boolean updateLabPermission;
	private boolean markAsReviewPermission;
	private boolean createInvestigationPermission;
	private boolean createNotificationPermission;
	private boolean matchingAlgorithm;
	private boolean unexpectedResultType;
	private boolean childSuscWithoutParentResult;
	private boolean fieldTruncationError;
	private boolean invalidDateError;
	private String algorithmAndOrLogic;
	private boolean labAssociatedToInv;
	
	private boolean reasonforStudyCdMissing;
	private Collection<Object> matchingPublicHealthCaseDTColl;
	private String investigationType;
	
	public boolean isLabAssociatedToInv() {
		return labAssociatedToInv;
	}
	public void setLabAssociatedToInv(boolean labAssociatedToInv) {
		this.labAssociatedToInv = labAssociatedToInv;
	}
	
	public String getInvestigationType() {
		return investigationType;
	}
	public void setInvestigationType(String investigationType) {
		this.investigationType = investigationType;
	}
	public Collection<Object> getMatchingPublicHealthCaseDTColl() {
		return matchingPublicHealthCaseDTColl;
	}
	public void setMatchingPublicHealthCaseDTColl(
			Collection<Object> matchingPublicHealthCaseDTColl) {
		this.matchingPublicHealthCaseDTColl = matchingPublicHealthCaseDTColl;
	}
	public Long getAssociatedPublicHealthCaseUid() {
		return associatedPublicHealthCaseUid;
	}
	public void setAssociatedPublicHealthCaseUid(Long associatedPublicHealthCaseUid) {
		this.associatedPublicHealthCaseUid = associatedPublicHealthCaseUid;
	}
	public boolean isChildSuscWithoutParentResult() {
		return childSuscWithoutParentResult;
	}
	public void setChildSuscWithoutParentResult(boolean childSuscWithoutParentResult) {
		this.childSuscWithoutParentResult = childSuscWithoutParentResult;
	}
	public boolean isUnexpectedResultType() {
		return unexpectedResultType;
	}
	public void setUnexpectedResultType(boolean unexpectedResultType) {
		this.unexpectedResultType = unexpectedResultType;
	}
	public EdxLabInformationDT() {
		// init variables
		unexpectedResultType = false;
		childSuscWithoutParentResult = false;
		jurisdictionName								= null;
		programAreaName									= null;
		jurisdictionAndProgramAreaSuccessfullyDerived	= false;

		algorithmHasInvestigation						= false;
		investigationSuccessfullyCreated				= false;
		investigationMissingFields						= false;
		
		algorithmHasNotification						= false;
		notificationSuccessfullyCreated					= false;
		notificationMissingFields						= false;
		
		labIsCreate										= false;
		labIsCreateSuccess								= false;
		labIsUpdateDRRQ										= false;
		labIsUpdateSuccess								= false;
		labIsMarkedAsReviewed							= false;
		
		multipleSubjectMatch = false;
		multipleOrderingProvider = false;
		multipleCollector = false;
		multiplePrincipalInterpreter = false;
		multipleOrderingFacility = false;
		multipleSpecimen = false;
		ethnicityCodeTranslated = true;
		obsMethodTranslated = true;
		raceTranslated = true;
		sexTranslated = true;
		ssnInvalid = false;
		nullClia = false;
		fillerNumberPresent = true;
		finalPostCorrected = false;
		preliminaryPostCorrected = false;
		preliminaryPostFinal = false;
		activityTimeOutOfSequence = false;
		multiplePerformingLab = false;
		orderTestNameMissing = false;
		reflexOrderedTestCdMissing = false;
		reflexResultedTestCdMissing = false;
		resultedTestNameMissing = false;
		drugNameMissing = false;
		obsStatusTranslated = true;
		activityToTimeMissing = false;
		systemException = false;
		universalServiceIdMissing = false;
		missingOrderingProvider = false;
		missingOrderingFacility=false;
		multipleReceivingFacility = false;
		patientMatch = false;
		multipleOBR = false;
		multipleSubject = false;
		noSubject = false;
		orderOBRWithParent = false;
		childOBRWithoutParent = false;
		invalidXML = false;
		missingOrderingProviderandFacility = false;
		createLabPermission = true;
		updateLabPermission = true;
		markAsReviewPermission = true;
		createInvestigationPermission = true;
		createNotificationPermission = true;
		matchingAlgorithm = true;
		fieldTruncationError = false;
		reasonforStudyCdMissing = false;
	}
	public long getOriginalAssociatedPHCUid() {
		return originalAssociatedPHCUid;
	}
	public void setOriginalAssociatedPHCUid(long originalAssociatedPHCUid) {
		this.originalAssociatedPHCUid = originalAssociatedPHCUid;
	}

	public String getRelationshipDesc() {
		return relationshipDesc;
	}
	public void setRelationshipDesc(String relationshipDesc) {
		this.relationshipDesc = relationshipDesc;
	}
	public String getRelationship() {
		return relationship;
	}
	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}
	public String getDangerCode() {
		return dangerCode;
	}
	public void setDangerCode(String dangerCode) {
		this.dangerCode = dangerCode;
	} 
	public boolean isMissingOrderingProvider() {
		return missingOrderingProvider;
	}
	public void setMissingOrderingProvider(boolean missingOrderingProvider) {
		this.missingOrderingProvider = missingOrderingProvider;
	}
	public ObservationVO getRootObservationVO() {
		return rootObservationVO;
	}

	public void setRootObservationVO(ObservationVO rootObservationVO) {
		this.rootObservationVO = rootObservationVO;
	}
	public String getAddReasonCd() {
		return addReasonCd;
	}

	public void setAddReasonCd(String addReasonCd) {
		this.addReasonCd = addReasonCd;
	}

	public Object getProxyVO() {
		return proxyVO;
	}

	public void setProxyVO(Object proxyVO) {
		this.proxyVO = proxyVO;
	}

	public String getConditionCode() {
		return conditionCode;
	}

	public void setConditionCode(String conditionCode) {
		this.conditionCode = conditionCode;
	}

	public Map<Object, Object> getResultedTest() {
		return resultedTest;
	}

	public void setResultedTest(Map<Object, Object> resultedTest) {
		this.resultedTest = resultedTest;
	}

	public String getEntityName() {
		return entityName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	public String getReportingSourceName() {
		return reportingSourceName;
	}
	public void setReportingSourceName(String reportingSourceName) {
		this.reportingSourceName = reportingSourceName;
	}
	
	
	public Map<Object, Object> getEdxSusLabDTMap() {
		return edxSusLabDTMap;
	}

	public void setEdxSusLabDTMap(Map<Object, Object> edxSusLabDTMap) {
		this.edxSusLabDTMap = edxSusLabDTMap;
	}

	public Collection<Object> getEdxLabIdentiferDTColl() {
		return edxLabIdentiferDTColl;
	}
	public void setEdxLabIdentiferDTColl(Collection<Object> edxLabIdentiferDTColl) {
		this.edxLabIdentiferDTColl = edxLabIdentiferDTColl;
	}
	public boolean isParentObsInd() {
		return isParentObsInd;
	}
	public void setParentObsInd(boolean isParentObsInd) {
		this.isParentObsInd = isParentObsInd;
	}
	public String getLocalId() {
		return localId;
	}
	public void setLocalId(String localId) {
		this.localId = localId;
	}
	public LabResultProxyVO getLabResultProxyVO() {
		return labResultProxyVO;
	}
	public void setLabResultProxyVO(LabResultProxyVO labResultProxyVO) {
		this.labResultProxyVO = labResultProxyVO;
	}
	public boolean isOrderingProvider() {
		return isOrderingProvider;
	}
	public void setOrderingProvider(boolean isOrderingProvider) {
		this.isOrderingProvider = isOrderingProvider;
	}
	public long getParentObservationUid() {
		return parentObservationUid;
	}
	public void setParentObservationUid(long parentObservationUid) {
		this.parentObservationUid = parentObservationUid;
	}
	public String getMessageControlID() {
		return messageControlID;
	}
	public void setMessageControlID(String messageControlID) {
		this.messageControlID = messageControlID;
	}
	public String getFillerNumber() {
		return fillerNumber;
	}
	public void setFillerNumber(String fillerNumber) {
		this.fillerNumber = fillerNumber;
	}
	public String getSendingFacilityName() {
		return sendingFacilityName;
	}
	public void setSendingFacilityName(String sendingFacilityName) {
		this.sendingFacilityName = sendingFacilityName;
	}
	public String getSendingFacilityClia() {
		return sendingFacilityClia;
	}
	public void setSendingFacilityClia(String sendingFacilityClia) {
		this.sendingFacilityClia = sendingFacilityClia;
	}
	public Timestamp getAddTime() {
		return addTime;
	}
	public void setAddTime(Timestamp addTime) {
		this.addTime = addTime;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public long getRootObserbationUid() {
		return rootObserbationUid;
	}
	public void setRootObserbationUid(long rootObserbationUid) {
		this.rootObserbationUid = rootObserbationUid;
	}
	public long getPatientUid() {
		return patientUid;
	}
	public void setPatientUid(long patientUid) {
		this.patientUid = patientUid;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public int getNextUid() {
		nextUid = nextUid-1;
		return nextUid;
	}
	public void setNextUid(int nextUid) {
		this.nextUid = nextUid;
	}
	public String getJurisdictionName() {
		return jurisdictionName;
	}
	public void setJurisdictionName(String jurisdictionName) {
		this.jurisdictionName = jurisdictionName;
	}
	public String getProgramAreaName() {
		return programAreaName;
	}
	public void setProgramAreaName(String programAreaName) {
		this.programAreaName = programAreaName;
	}
	public boolean isJurisdictionAndProgramAreaSuccessfullyDerived() {
		return jurisdictionAndProgramAreaSuccessfullyDerived;
	}
	public void setJurisdictionAndProgramAreaSuccessfullyDerived(boolean jurisdictionAndProgramAreaSuccessfullyDerived) {
		this.jurisdictionAndProgramAreaSuccessfullyDerived = jurisdictionAndProgramAreaSuccessfullyDerived;
	}
	public boolean isInvestigationSuccessfullyCreated() {
		return investigationSuccessfullyCreated;
	}
	public void setInvestigationSuccessfullyCreated(boolean investigationSuccessfullyCreated) {
		this.investigationSuccessfullyCreated = investigationSuccessfullyCreated;
	}
	public boolean isNotificationSuccessfullyCreated() {
		return notificationSuccessfullyCreated;
	}
	public void setNotificationSuccessfullyCreated(boolean notificationSuccessfullyCreated) {
		this.notificationSuccessfullyCreated = notificationSuccessfullyCreated;
	}
	public boolean isInvestigationMissingFields() {
		return investigationMissingFields;
	}
	public void setInvestigationMissingFields(boolean investigationMissingFields) {
		this.investigationMissingFields = investigationMissingFields;
	}
	public boolean isNotificationMissingFields() {
		return notificationMissingFields;
	}
	public void setNotificationMissingFields(boolean notificationMissingFields) {
		this.notificationMissingFields = notificationMissingFields;
	}
	public boolean isLabIsCreate() {
		return labIsCreate;
	}
	public void setLabIsCreate(boolean labIsCreate) {
		this.labIsCreate = labIsCreate;
	}
	public boolean isLabIsCreateSuccess() {
		return labIsCreateSuccess;
	}
	public void setLabIsCreateSuccess(boolean labIsCreateSuccess) {
		this.labIsCreateSuccess = labIsCreateSuccess;
	}
	public boolean isLabIsUpdateDRRQ() {
		return labIsUpdateDRRQ;
	}
	public void setLabIsUpdateDRRQ(boolean labIsUpdateDRRQ) {
		this.labIsUpdateDRRQ = labIsUpdateDRRQ;
	}
	public boolean isLabIsUpdateSuccess() {
		return labIsUpdateSuccess;
	}
	public void setLabIsUpdateSuccess(boolean labIsUpdateSuccess) {
		this.labIsUpdateSuccess = labIsUpdateSuccess;
	}
	public boolean isAlgorithmHasInvestigation() {
		return algorithmHasInvestigation;
	}
	public void setAlgorithmHasInvestigation(boolean algorithmHasInvestigation) {
		this.algorithmHasInvestigation = algorithmHasInvestigation;
	}
	public boolean isAlgorithmHasNotification() {
		return algorithmHasNotification;
	}
	public void setAlgorithmHasNotification(boolean algorithmHasNotification) {
		this.algorithmHasNotification = algorithmHasNotification;
	}
	public boolean isLabIsMarkedAsReviewed() {
		return labIsMarkedAsReviewed;
	}
	public void setLabIsMarkedAsReviewed(boolean labIsMarkedAsReviewed) {
		this.labIsMarkedAsReviewed = labIsMarkedAsReviewed;
	}
	public boolean isMultipleSubjectMatch() {
		return multipleSubjectMatch;
	}
	public void setMultipleSubjectMatch(boolean multipleSubjectMatch) {
		this.multipleSubjectMatch = multipleSubjectMatch;
	}
	public boolean isMultipleOrderingProvider() {
		return multipleOrderingProvider;
	}
	public void setMultipleOrderingProvider(boolean multipleOrderingProvider) {
		this.multipleOrderingProvider = multipleOrderingProvider;
	}
	public boolean isMultipleCollector() {
		return multipleCollector;
	}
	public void setMultipleCollector(boolean multipleCollector) {
		this.multipleCollector = multipleCollector;
	}
	public boolean isMultiplePrincipalInterpreter() {
		return multiplePrincipalInterpreter;
	}
	public void setMultiplePrincipalInterpreter(boolean multiplePrincipalInterpreter) {
		this.multiplePrincipalInterpreter = multiplePrincipalInterpreter;
	}
	public boolean isMultipleOrderingFacility() {
		return multipleOrderingFacility;
	}
	public void setMultipleOrderingFacility(boolean multipleOrderingFacility) {
		this.multipleOrderingFacility = multipleOrderingFacility;
	}
	public boolean isMultipleSpecimen() {
		return multipleSpecimen;
	}
	public void setMultipleSpecimen(boolean multipleSpecimen) {
		this.multipleSpecimen = multipleSpecimen;
	}
	public boolean isEthnicityCodeTranslated() {
		return ethnicityCodeTranslated;
	}
	public void setEthnicityCodeTranslated(boolean ethnicityCodeTranslated) {
		this.ethnicityCodeTranslated = ethnicityCodeTranslated;
	}
	public boolean isObsMethodTranslated() {
		return obsMethodTranslated;
	}
	public void setObsMethodTranslated(boolean obsMethodTranslated) {
		this.obsMethodTranslated = obsMethodTranslated;
	}
	public boolean isRaceTranslated() {
		return raceTranslated;
	}
	public void setRaceTranslated(boolean raceTranslated) {
		this.raceTranslated = raceTranslated;
	}
	public boolean isSexTranslated() {
		return sexTranslated;
	}
	public void setSexTranslated(boolean sexTranslated) {
		this.sexTranslated = sexTranslated;
	}
	public boolean isSsnInvalid() {
		return ssnInvalid;
	}
	public void setSsnInvalid(boolean ssnInvalid) {
		this.ssnInvalid = ssnInvalid;
	}
	public boolean isNullClia() {
		if(sendingFacilityClia==null || (sendingFacilityClia!=null && sendingFacilityClia.trim().equalsIgnoreCase(EdxELRConstants.ELR_DEFAULT_CLIA)))
			return true;
		else
			return false;
	}
	public void setNullClia(boolean nullClia) {
		this.nullClia = nullClia;
	}
	public boolean isFillerNumberPresent() {
		return fillerNumberPresent;
	}
	public void setFillerNumberPresent(boolean fillerNumberPresent) {
		this.fillerNumberPresent = fillerNumberPresent;
	}
	public boolean isFinalPostCorrected() {
		return finalPostCorrected;
	}
	public void setFinalPostCorrected(boolean finalPostCorrected) {
		this.finalPostCorrected = finalPostCorrected;
	}
	public boolean isActivityTimeOutOfSequence() {
		return activityTimeOutOfSequence;
	}
	public void setActivityTimeOutOfSequence(boolean activityTimeOutOfSequence) {
		this.activityTimeOutOfSequence = activityTimeOutOfSequence;
	}
	public boolean isMultiplePerformingLab() {
		return multiplePerformingLab;
	}
	public void setMultiplePerformingLab(boolean multiplePerformingLab) {
		this.multiplePerformingLab = multiplePerformingLab;
	}
	public boolean isOrderTestNameMissing() {
		return orderTestNameMissing;
	}
	public void setOrderTestNameMissing(boolean orderTestNameMissing) {
		this.orderTestNameMissing = orderTestNameMissing;
	}
	public boolean isReflexOrderedTestCdMissing() {
		return reflexOrderedTestCdMissing;
	}
	public void setReflexOrderedTestCdMissing(boolean reflexOrderedTestCdMissing) {
		this.reflexOrderedTestCdMissing = reflexOrderedTestCdMissing;
	}
	public boolean isReflexResultedTestCdMissing() {
		return reflexResultedTestCdMissing;
	}
	public void setReflexResultedTestCdMissing(boolean reflexResultedTestCdMissing) {
		this.reflexResultedTestCdMissing = reflexResultedTestCdMissing;
	}
	public boolean isResultedTestNameMissing() {
		return resultedTestNameMissing;
	}
	public void setResultedTestNameMissing(boolean resultedTestNameMissing) {
		this.resultedTestNameMissing = resultedTestNameMissing;
	}
	public boolean isDrugNameMissing() {
		return drugNameMissing;
	}
	public void setDrugNameMissing(boolean drugNameMissing) {
		this.drugNameMissing = drugNameMissing;
	}
	public boolean isObsStatusTranslated() {
		return obsStatusTranslated;
	}
	public void setObsStatusTranslated(boolean obsStatusTranslated) {
		this.obsStatusTranslated = obsStatusTranslated;
	}
	public boolean isActivityToTimeMissing() {
		return activityToTimeMissing;
	}
	public void setActivityToTimeMissing(boolean activityToTimeMissing) {
		this.activityToTimeMissing = activityToTimeMissing;
	}
	public boolean isSystemException() {
		return systemException;
	}
	public void setSystemException(boolean systemException) {
		this.systemException = systemException;
	}
	public boolean isUniversalServiceIdMissing() {
		return universalServiceIdMissing;
	}
	public void setUniversalServiceIdMissing(boolean universalServiceIdMissing) {
		this.universalServiceIdMissing = universalServiceIdMissing;
	}
	public boolean isMultipleReceivingFacility() {
		return multipleReceivingFacility;
	}
	public void setMultipleReceivingFacility(boolean multipleReceivingFacility) {
		this.multipleReceivingFacility = multipleReceivingFacility;
	}
	public long getPublicHealthCaseUid() {
		return publicHealthCaseUid;
	}
	public void setPublicHealthCaseUid(long publicHealthCaseUid) {
		this.publicHealthCaseUid = publicHealthCaseUid;
	}
	public long getNotificationUid() {
		return notificationUid;
	}
	public void setNotificationUid(long notificationUid) {
		this.notificationUid = notificationUid;
	}
	public long getPersonParentUid() {
		return personParentUid;
	}
	public void setPersonParentUid(long personParentUid) {
		this.personParentUid = personParentUid;
	}
	public boolean isPatientMatch() {
		return patientMatch;
	}
	public void setPatientMatch(boolean patientMatch) {
		this.patientMatch = patientMatch;
	}
	public boolean isMultipleOBR() {
		return multipleOBR;
	}
	public void setMultipleOBR(boolean multipleOBR) {
		this.multipleOBR = multipleOBR;
	}
	public PersonVO getOrderingProviderVO() {
		return orderingProviderVO;
	}
	public void setOrderingProviderVO(PersonVO orderingProviderVO) {
		this.orderingProviderVO = orderingProviderVO;
	}
	public boolean isMultipleSubject() {
		return multipleSubject;
	}
	public void setMultipleSubject(boolean multipleSubject) {
		this.multipleSubject = multipleSubject;
	}
	public boolean isNoSubject() {
		return noSubject;
	}
	public void setNoSubject(boolean noSubject) {
		this.noSubject = noSubject;
	}
	public boolean isOrderOBRWithParent() {
		return orderOBRWithParent;
	}
	public void setOrderOBRWithParent(boolean orderOBRWithParent) {
		this.orderOBRWithParent = orderOBRWithParent;
	}
	public boolean isChildOBRWithoutParent() {
		return childOBRWithoutParent;
	}
	public void setChildOBRWithoutParent(boolean childOBRWithoutParent) {
		this.childOBRWithoutParent = childOBRWithoutParent;
	}
	public boolean isMissingOrderingFacility() {
		return missingOrderingFacility;
	}
	public void setMissingOrderingFacility(boolean missingOrderingFacility) {
		this.missingOrderingFacility = missingOrderingFacility;
	}

	public boolean isInvalidXML() {
		return invalidXML;
	}
	public void setInvalidXML(boolean invalidXML) {
		this.invalidXML = invalidXML;
	}

	public boolean isMissingOrderingProviderandFacility() {
		return missingOrderingProviderandFacility;
	}
	public void setMissingOrderingProviderandFacility(
			boolean missingOrderingProviderandFacility) {
		this.missingOrderingProviderandFacility = missingOrderingProviderandFacility;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public boolean isCreateLabPermission() {
		return createLabPermission;
	}
	public void setCreateLabPermission(boolean createLabPermission) {
		this.createLabPermission = createLabPermission;
	}
	public boolean isMarkAsReviewPermission() {
		return markAsReviewPermission;
	}
	public void setMarkAsReviewPermission(boolean markAsReviewPermission) {
		this.markAsReviewPermission = markAsReviewPermission;
	}
	public boolean isCreateInvestigationPermission() {
		return createInvestigationPermission;
	}
	public void setCreateInvestigationPermission(
			boolean createInvestigationPermission) {
		this.createInvestigationPermission = createInvestigationPermission;
	}
	public boolean isCreateNotificationPermission() {
		return createNotificationPermission;
	}
	public void setCreateNotificationPermission(boolean createNotificationPermission) {
		this.createNotificationPermission = createNotificationPermission;
	}
	public boolean isMatchingAlgorithm() {
		return matchingAlgorithm;
	}
	public void setMatchingAlgorithm(boolean matchingAlgorithm) {
		this.matchingAlgorithm = matchingAlgorithm;
	}
	public String getUniversalIdType() {
		return universalIdType;
	}
	public void setUniversalIdType(String universalIdType) {
		this.universalIdType = universalIdType;
	}
	public boolean isUpdateLabPermission() {
		return updateLabPermission;
	}
	public void setUpdateLabPermission(boolean updateLabPermission) {
		this.updateLabPermission = updateLabPermission;
	}
	public boolean isPreliminaryPostFinal() {
		return preliminaryPostFinal;
	}
	public void setPreliminaryPostFinal(boolean preliminaryPostFinal) {
		this.preliminaryPostFinal = preliminaryPostFinal;
	}
	public boolean isPreliminaryPostCorrected() {
		return preliminaryPostCorrected;
	}
	public void setPreliminaryPostCorrected(boolean preliminaryPostCorrected) {
		this.preliminaryPostCorrected = preliminaryPostCorrected;
	}
	public String getAlgorithmAndOrLogic() {
		return algorithmAndOrLogic;
	}
	public void setAlgorithmAndOrLogic(String algorithmAndOrLogic) {
		this.algorithmAndOrLogic = algorithmAndOrLogic;
	}
	public boolean isFieldTruncationError() {
		return fieldTruncationError;
	}
	public void setFieldTruncationError(boolean fieldTruncationError) {
		this.fieldTruncationError = fieldTruncationError;
	}
	public boolean isReasonforStudyCdMissing() {
		return reasonforStudyCdMissing;
	}
	public void setReasonforStudyCdMissing(boolean reasonforStudyCdMissing) {
		this.reasonforStudyCdMissing = reasonforStudyCdMissing;
	}
	public boolean isInvalidDateError() {
		return invalidDateError;
	}
	public void setInvalidDateError(boolean invalidDateError) {
		this.invalidDateError = invalidDateError;
	}
	public boolean isLabIsUpdateDRSA() {
		return labIsUpdateDRSA;
	}
	public void setLabIsUpdateDRSA(boolean labIsUpdateDRSA) {
		this.labIsUpdateDRSA = labIsUpdateDRSA;
	}
	public long getNbsInterfaceUid() {
		return nbsInterfaceUid;
	}
	public void setNbsInterfaceUid(long nbsInterfaceUid) {
		this.nbsInterfaceUid = nbsInterfaceUid;
	}
	public Timestamp getSpecimenCollectionTime() {
		return specimenCollectionTime;
	}
	public void setSpecimenCollectionTime(Timestamp specimenCollectionTime) {
		this.specimenCollectionTime = specimenCollectionTime;
	}
	public Timestamp getOrderEffectiveDate() {
		return OrderEffectiveDate;
	}
	public void setOrderEffectiveDate(Timestamp orderEffectiveDate) {
		OrderEffectiveDate = orderEffectiveDate;
	}
}
