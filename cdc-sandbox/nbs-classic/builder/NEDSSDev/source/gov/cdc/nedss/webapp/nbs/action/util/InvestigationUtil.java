package gov.cdc.nedss.webapp.nbs.action.util;

import gov.cdc.nedss.act.notification.dt.NotificationDT;
import gov.cdc.nedss.act.notification.vo.NotificationVO;
import gov.cdc.nedss.act.observation.dt.ObsValueCodedDT;
import gov.cdc.nedss.act.observation.dt.ObsValueDateDT;
import gov.cdc.nedss.act.observation.dt.ObsValueNumericDT;
import gov.cdc.nedss.act.observation.dt.ObsValueTxtDT;
import gov.cdc.nedss.act.observation.vo.ObservationVO;
import gov.cdc.nedss.act.publichealthcase.dt.ConfirmationMethodDT;
import gov.cdc.nedss.act.publichealthcase.dt.PublicHealthCaseDT;
import gov.cdc.nedss.act.publichealthcase.vo.PublicHealthCaseVO;
import gov.cdc.nedss.association.dt.ActRelationshipDT;
import gov.cdc.nedss.association.dt.ParticipationDT;
import gov.cdc.nedss.entity.organization.vo.OrganizationVO;
import gov.cdc.nedss.entity.person.dt.PersonReportsSummaryDT;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.exception.NEDSSAppConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.nnd.ejb.nndmessageprocessorejb.helper.MessageBuilderHelper;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.PageProxyVO;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.act.PageActProxyVO;
import gov.cdc.nedss.phdc.CaseType;
import gov.cdc.nedss.phdc.ClinicalInformationType;
import gov.cdc.nedss.phdc.CodedType;
import gov.cdc.nedss.phdc.CommonQuestionsType;
import gov.cdc.nedss.phdc.ContainerDocument;
import gov.cdc.nedss.phdc.ContainerDocument.Container;
import gov.cdc.nedss.phdc.EpidemiologicInformationType;
import gov.cdc.nedss.phdc.InvestigationInformationType;
import gov.cdc.nedss.phdc.NumericType;
import gov.cdc.nedss.phdc.ReportingInformationType;
import gov.cdc.nedss.phdc.cda.ClinicalDocumentDocument1;
import gov.cdc.nedss.proxy.ejb.interventionproxyejb.vo.VaccinationSummaryVO;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.ManageSummaryVO;
import gov.cdc.nedss.proxy.ejb.notificationproxyejb.vo.NotificationProxyVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.LabReportSummaryVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.MorbReportSummaryVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.ReportSummaryInterface;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.vo.PamProxyVO;
import gov.cdc.nedss.proxy.ejb.treatmentproxyejb.vo.TreatmentSummaryVO;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.cdautil.CDAPHCProcessor;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dt.EdxCDAInformationDT;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dt.EdxRuleAlgorothmManagerDT;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.util.EdxPHCRDocumentUtil;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dao.NbsDocumentDAOImpl;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dt.NBSDocumentDT;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dt.SummaryDT;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.util.XMLTypeToNBSObject;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.vo.NBSDocumentVO;
import gov.cdc.nedss.systemservice.ejb.srtcachemanagerejb.dt.SRTGenericCodeDT;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbscontext.NBSContext;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.nbssecurity.ProgramAreaVO;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.util.StringUtils;
import gov.cdc.nedss.webapp.nbs.action.person.util.PersonUtil;
import gov.cdc.nedss.webapp.nbs.form.manage.ManageEventsForm;
import gov.cdc.nedss.webapp.nbs.form.pam.PamForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;

public class InvestigationUtil {

	static final LogUtils logger = new LogUtils(CommonAction.class.getName());
	CachedDropDownValues cdv = new CachedDropDownValues();

	public static PersonVO getPersonVO(HttpServletRequest request, HttpServletResponse response) throws ServletException{
		HttpSession session = request.getSession(false);
		// security stuff
		NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) session.getAttribute("NBSSecurityObject");
		Long mprUid = (Long) NBSContext.retrieve(session, NBSConstantUtil.DSPersonSummary);
		// why do we need to create instance
		PersonVO personVO = new PersonVO();
		if (mprUid != null) {
			personVO = findMasterPatientRecord(mprUid, session, nbsSecurityObj);
			ArrayList<Object> stateList = new ArrayList<Object> ();
			try {
			PersonUtil.convertPersonToRequestObj(personVO, request, "AddPatientFromEvent", stateList);
			}catch(Exception ex) {
				throw new ServletException(ex.getMessage());
			}
		}
		return personVO;
	}

	private static PersonVO findMasterPatientRecord(Long mprUId, HttpSession session, NBSSecurityObj secObj) {

		PersonVO personVO = null;
		MainSessionCommand msCommand = null;

		try {

			String sBeanJndiName = JNDINames.ENTITY_PROXY_EJB;
			String sMethod = "getMPR";
			Object[] oParams = new Object[] { mprUId };
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(session);

			ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
			personVO = (PersonVO) arr.get(0);
		} catch (Exception ex) {
			logger.error("Exception in FindMasterPatientRec: " + ex.getMessage());
			ex.printStackTrace();
			if (session == null) {
				logger.error("Error: no session, please login");
			}

			logger.fatal("Error in Find Master Patient Rec: " +ex.getMessage());
		}

		return personVO;
	}// findMasterPatientRecord

	/**
	 * Gets the investigation form code from the context...
	 * @param req
	 * @return
	 */
	public static String getInvFormCd(HttpServletRequest req, PamForm form) {
		HttpSession session = req.getSession();
		String investigationFormCd = null;
		try {
			String conditionCd = (String) NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationCondition);
			String programArea = (String) NBSContext.retrieve(session, NBSConstantUtil.DSInvestigationProgramArea);
			CachedDropDownValues cdv = new CachedDropDownValues();
			ProgramAreaVO programAreaVO = cdv.getProgramAreaCondition("('" + programArea + "')", conditionCd);
			investigationFormCd = programAreaVO.getInvestigationFormCd();

		} catch (Exception e) {

			try {
				investigationFormCd = (String) NBSContext.retrieve(session,NBSConstantUtil.DSInvestigationFormCd);				
			} catch (Exception e1) {
				logger.info("INV FORM CD is not present in Context: " + e.toString());
			}
		} 
		if(investigationFormCd == null) 
			investigationFormCd =  form.getPamFormCd();
		else
			form.setPamFormCd(investigationFormCd);
		//Log
		if(investigationFormCd == null || (investigationFormCd != null && investigationFormCd.equals(""))) {
			logger.error("Error while retrieving investigationFormCd from Context / PamForm: ");
		}
		return investigationFormCd;

	}


	public  static NotificationProxyVO setShareNotificationProxyVO(PublicHealthCaseVO publicHealthCaseVO, String exportFacility, String comment,
			String newJurisdictionCode, HttpServletRequest request)
	throws java.rmi.RemoteException, javax.ejb.EJBException,
	NEDSSAppConcurrentDataException, Exception {
		NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) request.getSession().getAttribute("NBSSecurityObject");
		Long exportReceivingFacilityUid =null;
		if(exportFacility!=null){
			exportReceivingFacilityUid =new Long(exportFacility);
		}
		NotificationDT notificationDT = new NotificationDT();
		java.util.Date startDate= new java.util.Date();
		notificationDT.setTxt(comment);
		Timestamp aAddTime = new Timestamp(startDate.getTime());
		notificationDT.setAddTime(aAddTime); 
		notificationDT.setLastChgTime(aAddTime);
		notificationDT.setLastChgUserId(Long.valueOf(nbsSecurityObj.getEntryID()));
		notificationDT.setExportReceivingFacilityUid(exportReceivingFacilityUid);
		notificationDT.setAddUserId(Long.valueOf(nbsSecurityObj.getEntryID()));
		notificationDT.setCaseClassCd(publicHealthCaseVO.getThePublicHealthCaseDT().getCaseClassCd());
		notificationDT.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
		notificationDT.setItNew(false);
		notificationDT.setItDirty(true);
		notificationDT.setRecordStatusCd(NEDSSConstants.PENDING_APPROVAL_STATUS);
		notificationDT.setAutoResendInd(NEDSSConstants.NOTIFICATION_AUTO_RESEND_OFF);
		notificationDT.setStatusTime(aAddTime);
		notificationDT.setProgramJurisdictionOid(publicHealthCaseVO.getThePublicHealthCaseDT().getProgramJurisdictionOid());
		notificationDT.setRecordStatusTime(aAddTime);
		if(newJurisdictionCode != null)
			notificationDT.setJurisdictionCd(newJurisdictionCode);
		notificationDT.setProgAreaCd(publicHealthCaseVO.getThePublicHealthCaseDT().getProgAreaCd());
		notificationDT.setVersionCtrlNbr(new Integer(1));
		notificationDT.setCaseConditionCd(publicHealthCaseVO.getThePublicHealthCaseDT().getCd());
		notificationDT.setNotificationUid(new Long(-1));
		NotificationVO notVO = new NotificationVO();
		notVO.setTheNotificationDT(notificationDT);
		notVO.setItNew(true);

		ActRelationshipDT actDT1 = new ActRelationshipDT();
		actDT1.setItNew(true);
		actDT1.setTargetActUid(publicHealthCaseVO.getThePublicHealthCaseDT().getPublicHealthCaseUid());
		actDT1.setSourceActUid(notificationDT.getNotificationUid());
		actDT1.setAddTime(aAddTime);
		actDT1.setRecordStatusCd(NEDSSConstants.NOTIFICATION_PENDING_CODE);
		actDT1.setSequenceNbr(new Integer(1));
		actDT1.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
		actDT1.setStatusTime(aAddTime);
		actDT1.setTypeCd(NEDSSConstants.ACT106_TYP_CD);
		actDT1.setSourceClassCd(NEDSSConstants.ACT106_SRC_CLASS_CD);
		actDT1.setTargetClassCd(NEDSSConstants.ACT106_TAR_CLASS_CD);
		NotificationProxyVO  notProxyVO = new NotificationProxyVO();
		notProxyVO.setItNew(true);
		notProxyVO.setThePublicHealthCaseVO(publicHealthCaseVO);
		notProxyVO.setTheNotificationVO(notVO);
		ArrayList<Object> actRelColl = new ArrayList<Object> ();
		actRelColl.add(0, actDT1);
		notProxyVO.setTheActRelationshipDTCollection(actRelColl);
		return notProxyVO;

	}

	public static Object createProxyObject(Long nbsDocUid,
			String conditionCode, NBSSecurityObj nbsSecurityObj)
			throws NEDSSAppException {
		NbsDocumentDAOImpl nbsDocumentDAOImpl = new NbsDocumentDAOImpl();
		NBSDocumentVO nBSDocumentVO = nbsDocumentDAOImpl
				.getNBSDocument(nbsDocUid);
		Object object = null;
		String docType = getDocumentType(nBSDocumentVO.getNbsDocumentDT());
		if (docType != null && docType.equals(NEDSSConstants.EDX_PHDC_DOC_TYPE))
			object = EdxPHCRDocumentUtil.createProxyVOUtil(nBSDocumentVO,
					conditionCode, nbsSecurityObj);
		else if (docType != null
				&& docType.equals(NEDSSConstants.CDA_PHDC_TYPE)) {
			nBSDocumentVO.getNbsDocumentDT().setCd(conditionCode); //while creating investigation setting condition_code to selected condition_code instead of using from document table
			EdxRuleAlgorothmManagerDT managerDT  = new EdxRuleAlgorothmManagerDT();
			managerDT.setDocumentDT(nBSDocumentVO.getNbsDocumentDT());
			EdxCDAInformationDT informationDT = new EdxCDAInformationDT(managerDT);
			informationDT.setPatientUid(-2);
			java.util.Date dateTime = new java.util.Date();
			Timestamp time = new Timestamp(dateTime.getTime());
			informationDT.setAddTime(time);
			CDAPHCProcessor.initializeProcessor();
			object = CDAPHCProcessor.createPageActProxyVO(null, nBSDocumentVO,
					informationDT, nbsSecurityObj);
		}
		return object;
	}
	
	public static String getDocumentType(NBSDocumentDT documentDT){
		if(documentDT.getPayLoadTxt()!=null){
			try {
				XmlObject xobj = XmlObject.Factory.parse(documentDT.getPayLoadTxt());
				if(xobj!=null && xobj instanceof  ContainerDocument)
					return NEDSSConstants.EDX_PHDC_DOC_TYPE;
				else if(xobj!=null && xobj instanceof  ClinicalDocumentDocument1){
					documentDT.setDocumentObject(xobj);
					return NEDSSConstants.CDA_PHDC_TYPE;
				}
			} catch (XmlException e) {
				logger.error("XML Exception in getDocumentType: " + e.getMessage());
				e.printStackTrace();
			}
		}
		return "";
		
	}

	public static  Map<Object,Object>  getPublicHealthCaseAndObsColl(Long nbsDocUid, HttpServletRequest request){
		MessageBuilderHelper messageBuilderHelper = new MessageBuilderHelper();
		HashMap map = new HashMap<Object,Object>();

		Collection<Object>  observationCollection   = new ArrayList<Object> ();
		PublicHealthCaseVO  publicHealthCaseVO= new PublicHealthCaseVO();
		try {

			XMLTypeToNBSObject xmlObject = new XMLTypeToNBSObject();
			NbsDocumentDAOImpl nbsDocumentDAOImpl = new NbsDocumentDAOImpl();
			NBSDocumentVO nBSDocumentVO  = nbsDocumentDAOImpl.getNBSDocument(nbsDocUid);
			nBSDocumentVO.getPatientVO().getThePersonDT().getPersonParentUid();

			//publicHealthCaseVO.getThePublicHealthCaseDT().setProgAreaCd(nBSDocumentVO.getNbsDocumentDT().getProgAreaCd());
			publicHealthCaseVO.getThePublicHealthCaseDT().setJurisdictionCd(nBSDocumentVO.getNbsDocumentDT().getJurisdictionCd());
			//publicHealthCaseVO.getThePublicHealthCaseDT().setCd(nBSDocumentVO.getNbsDocumentDT().getCd());
			ContainerDocument containerDoc=null;
            if(nBSDocumentVO.getNbsDocumentDT().getPayloadViewIndCd() != null && nBSDocumentVO.getNbsDocumentDT().getPayloadViewIndCd().equals(NEDSSConstants.PAYLOAD_VIEW_IND_CD_PHDC)){ 
            	 containerDoc=xmlObject.parseCaseTypeXml(nBSDocumentVO.getNbsDocumentDT().getPayLoadTxt());
            }else if(nBSDocumentVO.getNbsDocumentDT().getPayloadViewIndCd() != null && !nBSDocumentVO.getNbsDocumentDT().getPayloadViewIndCd().equals(NEDSSConstants.PAYLOAD_VIEW_IND_CD_PHDC)){
                 if(nBSDocumentVO.getNbsDocumentDT().getPhdcDocDerivedTxt() != null)	
                	 containerDoc=xmlObject.parseCaseTypeXml(nBSDocumentVO.getNbsDocumentDT().getPhdcDocDerivedTxt());
            	 
            }
            Container container=null; 
            if(containerDoc != null)                 
            	container = containerDoc.getContainer();
            CaseType caseType =null;
            if (container != null)
			  caseType = container.getCase();
            CommonQuestionsType commonQuestionsType =null;
            if(caseType != null)
            	commonQuestionsType =caseType.getCommonQuestions();
			if(commonQuestionsType!=null){
				if(commonQuestionsType.getReportingInformation()!=null){
					ReportingInformationType reportingInformationType =commonQuestionsType.getReportingInformation();
					if(reportingInformationType.getDateOfReport()!=null)
						publicHealthCaseVO.getThePublicHealthCaseDT().setRptFormCmpltTime(new Timestamp(reportingInformationType.getDateOfReport().getTimeInMillis()));
				}

				ClinicalInformationType clinicalInformationType =commonQuestionsType.getClinicalInformation();
				if(clinicalInformationType!=null){
					ObsValueCodedDT hospitalzedDT= getObsValueCodedDT(observationCollection,"INV128", clinicalInformationType.getWasThePatientHospitalized());
					if(hospitalzedDT!=null)
						publicHealthCaseVO.getThePublicHealthCaseDT().setHospitalizedIndCd(hospitalzedDT.getCode());
					ObsValueDateDT admissionDT= getObsDateDT(observationCollection, "INV132", clinicalInformationType.getAdmissionDate());
					if(admissionDT!=null)
						publicHealthCaseVO.getThePublicHealthCaseDT().setHospitalizedAdminTime(admissionDT.getFromTime());
					ObsValueDateDT dischargeDT= getObsDateDT(observationCollection,"INV133",clinicalInformationType.getDischargeDate());
					if(dischargeDT!=null)
						publicHealthCaseVO.getThePublicHealthCaseDT().setHospitalizedDischargeTime(dischargeDT.getFromTime());
					ObsValueNumericDT durationOfStayDT=getObsValueNumericForBigIntDT(observationCollection, "INV134",clinicalInformationType.getDurationOfStay());
					if(durationOfStayDT!=null)
						publicHealthCaseVO.getThePublicHealthCaseDT().setHospitalizedDurationAmt(durationOfStayDT.getNumericValue1());
					if(clinicalInformationType.getDiagnosisDate()!=null)
						publicHealthCaseVO.getThePublicHealthCaseDT().setDiagnosisTime(new Timestamp(clinicalInformationType.getDiagnosisDate().getTimeInMillis()));	
					if(clinicalInformationType.getIllnessOnsetDate()!=null)
						publicHealthCaseVO.getThePublicHealthCaseDT().setEffectiveFromTime(new Timestamp(clinicalInformationType.getIllnessOnsetDate().getTimeInMillis()));	
					if(clinicalInformationType.getIllnessEndDate()!=null)
						publicHealthCaseVO.getThePublicHealthCaseDT().setEffectiveToTime(new Timestamp(clinicalInformationType.getIllnessEndDate().getTimeInMillis()));	
					if(clinicalInformationType.getIllnessDuration()!=null && clinicalInformationType.getIllnessDuration().getValue1()>0){
						int duration =(int)clinicalInformationType.getIllnessDuration().getValue1();
						publicHealthCaseVO.getThePublicHealthCaseDT().setEffectiveDurationAmt(""+duration);
					}
					if(clinicalInformationType.getIllnessDuration()!=null && clinicalInformationType.getIllnessDuration().getUnit()!=null){
						try{
							SRTGenericCodeDT srtIllnessUnitDT = messageBuilderHelper.reversePHINToNBSCodeTranslation("PHVS_AGE_UNIT", clinicalInformationType.getIllnessDuration().getUnit().getCode(), "Standard_XREF");
							if(srtIllnessUnitDT!=null){
								publicHealthCaseVO.getThePublicHealthCaseDT().setEffectiveDurationUnitCd(srtIllnessUnitDT.getCode());
							}
						}
						catch(Exception e){
							logger.error("Code cannot be translated for country: please check  to_code_set_nm COUNTRY_ISO for the message(for Country_XREF table) ");
						}

					}
					if(clinicalInformationType.getDidThePatientDieFromIllness()!=null)
						publicHealthCaseVO.getThePublicHealthCaseDT().setOutcomeCd(clinicalInformationType.getDidThePatientDieFromIllness().getCode());	
					ObsValueDateDT dateOfDeathDT =getObsDateDT(observationCollection,"INV146",clinicalInformationType.getDateOfDeath());
					if(dateOfDeathDT!=null)
						publicHealthCaseVO.getThePublicHealthCaseDT().setDeceasedTime(dateOfDeathDT.getFromTime());	

					ObsValueCodedDT pregnantStatusDT = getObsValueCodedDT(observationCollection,"INV178", clinicalInformationType.getPregnancyStatus());
					if(pregnantStatusDT!=null)
						publicHealthCaseVO.getThePublicHealthCaseDT().setPregnantIndCd(pregnantStatusDT.getCode());
					if(clinicalInformationType.getAgeAtIllnessOnset()!=null && clinicalInformationType.getAgeAtIllnessOnset().getValue1()>-1){
						int age =(int)clinicalInformationType.getAgeAtIllnessOnset().getValue1();
						publicHealthCaseVO.getThePublicHealthCaseDT().setPatAgeAtOnset(""+age);
					}
					if(clinicalInformationType.getAgeAtIllnessOnset()!=null && clinicalInformationType.getAgeAtIllnessOnset().getUnit()!=null){
						try{
							SRTGenericCodeDT srtIllnessUnitDT = messageBuilderHelper.reversePHINToNBSCodeTranslation("PHVS_AGE_UNIT", clinicalInformationType.getAgeAtIllnessOnset().getUnit().getCode(), "Standard_XREF");
							if(srtIllnessUnitDT!=null){
								publicHealthCaseVO.getThePublicHealthCaseDT().setPatAgeAtOnsetUnitCd(srtIllnessUnitDT.getCode());
							}
						}
						catch(Exception e){
							logger.error("Code cannot be translated for Age at Onset Unit: please check  to_code_set_nm PHVS_AGE_UNIT for the message(for Standard_XREF table) ");
						}

					}

				}


				if(commonQuestionsType.getEpidemiologicInformation()!=null){
					EpidemiologicInformationType epidemiologicInformationType=commonQuestionsType.getEpidemiologicInformation();

					if(epidemiologicInformationType.getDetectionMethod()!=null &&epidemiologicInformationType.getDetectionMethod().getCode()!=null){
						publicHealthCaseVO.getThePublicHealthCaseDT().setDetectionMethodCd(epidemiologicInformationType.getDetectionMethod().getCode());
					}

					if(epidemiologicInformationType.getIsThisCasePartOfAnOutbreak()!=null)
						publicHealthCaseVO.getThePublicHealthCaseDT().setOutbreakInd(epidemiologicInformationType.getIsThisCasePartOfAnOutbreak().getCode());
					if(epidemiologicInformationType.getIsThisCasePartOfAnOutbreak()!=null && epidemiologicInformationType.getOutbreakName()!=null)
						publicHealthCaseVO.getThePublicHealthCaseDT().setOutbreakName(epidemiologicInformationType.getOutbreakName().getCode());

					if(epidemiologicInformationType.getWhereWasTheDiseaseAcquired()!=null)
						publicHealthCaseVO.getThePublicHealthCaseDT().setDiseaseImportedCd(epidemiologicInformationType.getWhereWasTheDiseaseAcquired().getCode());
					if(epidemiologicInformationType.getImportedCountry()!=null && epidemiologicInformationType.getImportedCountry().getCode()!=null){
						try{
							SRTGenericCodeDT srtImportedCountryCodeDT = messageBuilderHelper.reversePHINToNBSCodeTranslation("COUNTRY_ISO", epidemiologicInformationType.getImportedCountry().getCode(), "Country_XREF");
							if(srtImportedCountryCodeDT!=null){
								ObsValueCodedDT importCountryDT=getObsValueCodedFromStringDT(observationCollection,"INV153",srtImportedCountryCodeDT.getCode());
								if(importCountryDT!=null)
									publicHealthCaseVO.getThePublicHealthCaseDT().setImportedCountryCd(importCountryDT.getCode());
							}
						}
						catch(Exception e){
							logger.error("Code cannot be translated for country: please check  to_code_set_nm COUNTRY_ISO for the message(for Country_XREF table) ");
						}

					}
					ObsValueCodedDT importedStateDT=getObsValueCodedDT(observationCollection, "INV154",epidemiologicInformationType.getImportedState());
					if(importedStateDT!=null)
						publicHealthCaseVO.getThePublicHealthCaseDT().setImportedStateCd(importedStateDT.getCode());
					ObsValueTxtDT cityTxtDT=getObsValueTxtDT(observationCollection, "INV155", epidemiologicInformationType.getImportedCity());
					if(cityTxtDT!=null)
						publicHealthCaseVO.getThePublicHealthCaseDT().setImportedCityDescTxt(cityTxtDT.getValueTxt());
					ObsValueCodedDT importedCountyDT=getObsValueCodedDT(observationCollection,"INV156",epidemiologicInformationType.getImportedCounty());
					if(importedCountyDT!=null)
						publicHealthCaseVO.getThePublicHealthCaseDT().setImportedCountyCd((importedCountyDT.getCode()));
					if(epidemiologicInformationType.getTransmissionMode()!=null && epidemiologicInformationType.getTransmissionMode().getCode()!=null)
						publicHealthCaseVO.getThePublicHealthCaseDT().setTransmissionModeCd(epidemiologicInformationType.getTransmissionMode().getCode());
					//special handling required
					Calendar confirmationDate= null;
					if(epidemiologicInformationType.getConfirmationDate()!=null)
						confirmationDate= epidemiologicInformationType.getConfirmationDate();
					ArrayList<Object> confirmationlist = new ArrayList<Object> ();
					CodedType[]			confirmationMethod =epidemiologicInformationType.getConfirmationMethodArray();
					for (int i = 0; i <confirmationMethod.length; i++) {
						ConfirmationMethodDT confirmationMethodDT = new ConfirmationMethodDT();
						if (confirmationMethod[i] != null){
							CodedType confirmationMethodCoded=confirmationMethod[i];
							confirmationMethodDT.setConfirmationMethodCd(confirmationMethodCoded.getCode());
							if(confirmationDate!=null)
								confirmationMethodDT.setConfirmationMethodTime(new Timestamp(confirmationDate.getTimeInMillis()));
							confirmationlist.add(confirmationMethodDT);
						}
					}
					if(confirmationlist.size()==0 && confirmationDate!=null){
						ConfirmationMethodDT confirmationMethodDT = new ConfirmationMethodDT();
						confirmationMethodDT.setConfirmationMethodTime(new Timestamp(confirmationDate.getTimeInMillis()));
						confirmationlist.add(confirmationMethodDT);
					}
					publicHealthCaseVO.setTheConfirmationMethodDTCollection(confirmationlist);
					//getObsDateDT(observationCollection,epidemiologicInformationType.getConfirmationDate());

					if(epidemiologicInformationType.getCaseStatus()!=null && epidemiologicInformationType.getCaseStatus().getCode()!=null){
						try{
							SRTGenericCodeDT srtCaseStatusCode = messageBuilderHelper.reversePHINToNBSCodeTranslation("PHVS_PHC_CLASS",epidemiologicInformationType.getCaseStatus().getCode(), "Standard_XREF");
							if(srtCaseStatusCode!=null){
								publicHealthCaseVO.getThePublicHealthCaseDT().setCaseClassCd(srtCaseStatusCode.getCode());
							}
						}
						catch(Exception e){
							logger.error("Code cannot be translated for case status: check  to_code_set_nm PHVS_PHC_CLASS (for Standard_XREF table) ");
						}
					}
					if(epidemiologicInformationType.getMMWRWeek()!=null )
						publicHealthCaseVO.getThePublicHealthCaseDT().setMmwrWeek(""+epidemiologicInformationType.getMMWRWeek());
					if(epidemiologicInformationType.getMMWRYear()!=null )
						publicHealthCaseVO.getThePublicHealthCaseDT().setMmwrYear(""+epidemiologicInformationType.getMMWRYear());
					publicHealthCaseVO.getThePublicHealthCaseDT().setTxt(epidemiologicInformationType.getGeneralComments());
				}

				if(commonQuestionsType.getInvestigationInformation()!=null){

					InvestigationInformationType investigationInformationType =commonQuestionsType.getInvestigationInformation();
				}
				if( commonQuestionsType.getReportingInformation()!=null){

					ReportingInformationType reportingInformationType = commonQuestionsType.getReportingInformation();
					if(reportingInformationType.getReportingSourceType()!=null){
						publicHealthCaseVO.getThePublicHealthCaseDT().setRptSourceCd(reportingInformationType.getReportingSourceType().getCode());
					}

					if(reportingInformationType.getEarliestDateReportedToCounty()!=null)
						publicHealthCaseVO.getThePublicHealthCaseDT().setRptToCountyTime(new Timestamp(reportingInformationType.getEarliestDateReportedToCounty().getTimeInMillis()));
					if(reportingInformationType.getEarliestDateReportedToState()!=null)
						publicHealthCaseVO.getThePublicHealthCaseDT().setRptToStateTime(new Timestamp(reportingInformationType.getEarliestDateReportedToState().getTimeInMillis()));

				}
			}
			publicHealthCaseVO.getThePublicHealthCaseDT().setInvestigationStatusCd(NEDSSConstants.STATUS_OPEN);
			publicHealthCaseVO.getThePublicHealthCaseDT().setSharedInd(NEDSSConstants.TRUE);

		} catch (Exception e) {
			logger.error("InvestigationUtil.getPublicHealthCaseAndObsColl error:"+ e.getMessage());
			e.printStackTrace();
		}
		map.put("PHCVO",publicHealthCaseVO );
		map.put("OBSERVATIONCOLLECTION",observationCollection  );
		return map;
	}
	private static ObsValueTxtDT getObsValueTxtDT(Collection<Object> observationCollection, String code, String text){
		ObsValueTxtDT obsValueTxtDT=null;
		Collection<Object>  obsValueTextDTCollection= new ArrayList<Object> ();
		if(text!=null){
			obsValueTxtDT=new ObsValueTxtDT();
			obsValueTxtDT.setObsValueTxtSeq(new Integer(1));
			obsValueTxtDT.setValueTxt(text);
			logger.debug("XMLTypeToNBSObject.getObsValueTxtDT:The String:-\""+ text);
			if(obsValueTxtDT!=null)
				obsValueTextDTCollection.add(obsValueTxtDT);
			ObservationVO obsVO= new ObservationVO();
			obsVO.setTheObsValueTxtDTCollection(obsValueTextDTCollection);
			observationCollection.add(obsVO);
			obsVO.getTheObservationDT().setCd(code);
			return obsValueTxtDT;
		}
		else 
			return null;
	}
	private static ObsValueDateDT getObsDateDT(Collection<Object> observationCollection, String code,  Calendar calendar){
		ObsValueDateDT obsValueDateDT=null;
		Collection<Object>  obsValueDateDTCollection= new ArrayList<Object> ();
		if(calendar!=null){
			obsValueDateDT=new ObsValueDateDT();
			//obsValueDateDT.setToTime_s(calendar.toString());
			obsValueDateDT.setFromTime(new Timestamp(calendar.getTimeInMillis()));
			obsValueDateDT.setObsValueDateSeq(new Integer(1));
			logger.debug("XMLTypeToNBSObject.getObsDateDT:The calendar:-\""+ calendar);
			if(obsValueDateDT!=null)
				obsValueDateDTCollection.add(obsValueDateDT);
			ObservationVO obsVO= new ObservationVO();
			obsVO.setTheObsValueDateDTCollection(obsValueDateDTCollection);
			obsVO.getTheObservationDT().setCd(code);
			observationCollection.add(obsVO);
		}
		return obsValueDateDT;
	}
	private static ObsValueCodedDT getObsValueCodedDT(Collection<Object> observationCollection, String code,CodedType codedType){
		ObsValueCodedDT obsValueCodedDT=null;
		Collection<Object>  obsValueCodedDTCollection= new ArrayList<Object> ();
		if(codedType!=null){
			obsValueCodedDT=new ObsValueCodedDT();
			obsValueCodedDT.setCode(codedType.getCode());
			obsValueCodedDT.setCodeSystemCd(codedType.getCodeSystemCode());
			logger.debug("XMLTypeToNBSObject.getObsValueCodedDT:The xml token:-\""+ codedType.xmlText());
			if(obsValueCodedDT!=null)
				obsValueCodedDTCollection.add(obsValueCodedDT);
			ObservationVO obsVO= new ObservationVO();
			obsVO.getTheObservationDT().setCd(code);
			obsVO.setTheObsValueCodedDTCollection(obsValueCodedDTCollection);
			observationCollection.add(obsVO);
		}
		return obsValueCodedDT;
	}
	private static ObsValueCodedDT getObsValueCodedFromStringDT(Collection<Object> observationCollection, String ObsCode,String code){
		ObsValueCodedDT obsValueCodedDT=null;
		Collection<Object>  obsValueCodedDTCollection= new ArrayList<Object> ();
		if(code!=null){
			obsValueCodedDT=new ObsValueCodedDT();
			obsValueCodedDT.setCode(code);
			//obsValueCodedDT.setCodeSystemCd(codedType.getCodeSystemCode());
			if(obsValueCodedDT!=null)
				obsValueCodedDTCollection.add(obsValueCodedDT);
			ObservationVO obsVO= new ObservationVO();
			obsVO.getTheObservationDT().setCd(ObsCode);
			obsVO.setTheObsValueCodedDTCollection(obsValueCodedDTCollection);
			observationCollection.add(obsVO);
		}
		return obsValueCodedDT;
	}
	private static ObsValueNumericDT getObsValueNumericDT(Collection<Object> observationCollection,String code, NumericType numericType){
		ObsValueNumericDT obsValueNumericDT= null;
		Collection<Object>  obsValueNumericDTCollection= new ArrayList<Object> ();
		if(numericType!=null){
			obsValueNumericDT= new ObsValueNumericDT();

			if(numericType.getValue1()>0){
				long value1 =  (long)numericType.getValue1();
				obsValueNumericDT.setNumericValue1(new BigDecimal(value1+""));
				obsValueNumericDT.setObsValueNumericSeq(new Integer(1));
			}

			if(numericType.getUnit()!=null)
				obsValueNumericDT.setNumericUnitCd(numericType.getUnit().getCode());
			logger.debug("XMLTypeToNBSObject.getObsValueNumericDT:the xml token:-\""+ numericType.xmlText());
			if(obsValueNumericDT!=null)
				obsValueNumericDTCollection.add(obsValueNumericDT);
			ObservationVO obsVO= new ObservationVO();
			obsVO.getTheObservationDT().setCd(code);
			observationCollection.add(obsVO);
		}
		return obsValueNumericDT;
	}
	private static ObsValueNumericDT getObsValueNumericForBigIntDT(Collection<Object> observationCollection,String code,BigInteger number){
		ObsValueNumericDT obsValueNumericDT= null;
		Collection<Object>  obsValueNumericDTCollection= new ArrayList<Object> ();
		if(number!=null){
			obsValueNumericDT= new ObsValueNumericDT();

			if(number!=null){
				obsValueNumericDT.setNumericValue1_s(number.toString());
				obsValueNumericDT.setObsValueNumericSeq(new Integer(1));
			}

			logger.debug("XMLTypeToNBSObject.getObsValueNumericForBigIntDT:thenumber:-\""+ number);
			if(obsValueNumericDT!=null)
				obsValueNumericDTCollection.add(obsValueNumericDT);
			ObservationVO obsVO= new ObservationVO();
			obsVO.getTheObservationDT().setCd(code);
			obsVO.setTheObsValueNumericDTCollection(obsValueNumericDTCollection);
			observationCollection.add(obsVO);
		}
		return obsValueNumericDT;
	}

	
	public ManageSummaryVO getManageSummaryVOForManage(String strphcUID, String mprUid,
			HttpSession session)
	{
		ManageSummaryVO manageSummaryVO =null;
		logger.debug(
				"getMorbReportSummaryListForManage has been called, strPSN = " + mprUid + "strphcUID = " +
				strphcUID);
		try
		{
			Long personUID = new Long(mprUid);
			Long phcUID = new Long(strphcUID);
			String sBeanJndiName = JNDINames.INVESTIGATION_PROXY_EJB;
			String sMethod = "getManageSummaryVO";

			Object[] oParams = new Object[] { phcUID, personUID };
			MainSessionCommand msCommand = null;

			if (msCommand == null)
			{

				MainSessionHolder holder = new MainSessionHolder();
				msCommand = holder.getMainSessionCommand(session);
			}

			ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
			 manageSummaryVO = (ManageSummaryVO)arr.get(0);
		}
		catch (Exception ex)
		{
			logger.fatal("getManageSummaryVOForManage: ", ex);
		}

		return manageSummaryVO;
	}
	
	private static void setLabReportsDisplayList(
			Collection<Object> LabSummaryList, HttpServletRequest request, PublicHealthCaseDT phcDT, String sCurrTask) {
		NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) request.getSession().getAttribute("NBSSecurityObject");
		Collection<PersonReportsSummaryDT> summaryLabReportList = new ArrayList<PersonReportsSummaryDT>();
		if (LabSummaryList == null || LabSummaryList.size() == 0) {
			logger.debug("Observation summary collection arraylist is null");
		} else {
			Iterator<Object> obsIterator = LabSummaryList.iterator();

			while (obsIterator.hasNext()) {
				logger.debug("Inside iterator.hasNext()...");
				LabReportSummaryVO labReportSummaryVO = (LabReportSummaryVO) obsIterator
						.next();
				String processingDecision = "";
				if (labReportSummaryVO.getProcessingDecisionCd() != null)
					processingDecision = CachedDropDowns
							.getCodeDescTxtForCd(
									labReportSummaryVO
											.getProcessingDecisionCd(),
									NEDSSConstants.STD_PROCESSING_DECISION_LIST_SYPHILIS_AND_NONSYPHILIS);
				PersonReportsSummaryDT dt = new PersonReportsSummaryDT();
				dt.setProgArea(labReportSummaryVO.getProgramArea());
				String startDate = labReportSummaryVO.getDateReceived() == null ? "No Date"
						: StringUtils.formatDate(labReportSummaryVO
								.getDateReceived());
				if (labReportSummaryVO.isLabFromDoc()) {
					startDate = "<a href=\"/nbs/"
							+ sCurrTask
							+ ".do?ContextAction=ViewDocument"
							+ "&method=viewManageSubmit&nbsDocumentUid="
							+ labReportSummaryVO.getUid()
							+ "\">"+ startDate+ "</a>"+ "<br>"+ StringUtils
									.formatDatewithHrMin(labReportSummaryVO
											.getDateReceived());
				} else
					startDate = "<a href=\"/nbs/"
							+ sCurrTask
							+ ".do?ContextAction=ViewObservationLab"
							+ "&method=viewManageSubmit&observationUID="
							+ labReportSummaryVO.getObservationUid()
							+ "\">"+ startDate+ "</a>"+ "<br>"+ StringUtils
									.formatDatewithHrMin(labReportSummaryVO
											.getDateReceived());

				// Append Electronic Ind
				dt.setItAssociated(labReportSummaryVO.getIsAssociated());
				dt.setObservationUid(labReportSummaryVO.getObservationUid());
				if(processingDecision!=null && processingDecision.trim().length()>0)
					processingDecision = "<br>"+ processingDecision;
				if (labReportSummaryVO.getElectronicInd() != null
						&& labReportSummaryVO.getElectronicInd().equals("Y"))
					dt.setDateReceived(startDate
							+ processingDecision
							+ "<br><img src=\"ind_electronic.gif\" title=\"Electronic Indicator\" alt=\"Electronic Indicator\">");
				else
					dt.setDateReceived(startDate + processingDecision);
				String provider = getProviderFullName(
						labReportSummaryVO.getProviderPrefix(),
						labReportSummaryVO.getProviderFirstName(),
						labReportSummaryVO.getProviderLastName(),
						labReportSummaryVO.getProviderSuffix());
				provider = provider == null ? ""
						: "<b>Ordering Provider:</b><br>" + provider;
				String facility = labReportSummaryVO.getReportingFacility() == null ? ""
						: "<b>Reporting Facility:</b><br>"
								+ labReportSummaryVO.getReportingFacility();
				dt.setProviderFacility(facility + "<br>" + provider);
				String dateCollected = labReportSummaryVO.getDateCollected() == null ? "No Date"
							: StringUtils.formatDate(labReportSummaryVO
									.getDateCollected());
				dt.setDateCollected(dateCollected);
				if (labReportSummaryVO.isLabFromDoc())
					dt.setDescription(labReportSummaryVO
							.getResultedTestString());
				else
					dt.setDescription(DecoratorUtil
							.getResultedTestsStringForWorup(labReportSummaryVO
									.getTheResultedTestSummaryVOCollection()));

				dt.setEventId(labReportSummaryVO.getLocalId() == null ? ""
						: labReportSummaryVO.getLocalId());
				if(PropertyUtil.isStdOrHivProgramArea(phcDT.getProgAreaCd()) && phcDT.getAddTime()!=null && labReportSummaryVO.getActivityFromTime()!=null && phcDT.getAddTime().equals(labReportSummaryVO.getActivityFromTime())){
					dt.setReactor(true);
					//if Disassociate Initiating Event permission - don't disable assoc lab checkbox
					if (!nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,
							NBSOperationLookup.DISASSOCIATEINITIATINGEVENT))
						dt.setDisabled(NEDSSConstants.DISABLED);
				}
            	// don't allow to associate or disassociate the STD lab or morbs if its closed STD investigation
            	if(PropertyUtil.isStdOrHivProgramArea(labReportSummaryVO.getProgramArea()) && PropertyUtil.isStdOrHivProgramArea(phcDT.getProgAreaCd()) && phcDT.getInvestigationStatusCd()!=null && !phcDT.getInvestigationStatusCd().equals(NEDSSConstants.STATUS_OPEN))
            		dt.setDisabled(NEDSSConstants.DISABLED);
				summaryLabReportList.add(dt);
			} // while
			request.setAttribute("observationSummaryLabList", summaryLabReportList);
		}
	}
    private static String getProviderFullName(String prefix,String firstNm,String lastNm, String sufix){
        StringBuffer sb = new StringBuffer("");
        if(prefix!=null && !prefix.equals("")){
            sb.append(prefix).append(" ");
        }
        if(firstNm!=null && !firstNm.equals("")){
            sb.append(firstNm).append(" ");
        }
        if(lastNm!=null && !lastNm.equals("")){
            sb.append(lastNm).append(" ");
        }
        if(sufix!=null && !sufix.equals("")){
            sb.append(sufix).append(" ");
        }
        if(sb.toString().trim().equals(""))
            return null;
        else
        return sb.toString();
     }

	/**
	 * Get ObservationSummary values from request object and put it to appropriate object
	 * @param InvestigationProxyVO the investigationProxyVO
	 * @param HttpServletRequest the request
	 */
	public void populateAssociatedLabMorbs(ManageEventsForm form,Collection<Object>  labObsSummaryColl,Collection<Object>  morbObsSummaryColl, String sPublicHealthCaseUID, HttpServletRequest request, PublicHealthCaseDT phcDT){

		String sCurrentTask = NBSContext.getCurrentTask(request.getSession());
		NBSSecurityObj nbsSecurityObj = (NBSSecurityObj) request.getSession().getAttribute("NBSSecurityObject");
		setLabReportsDisplayList(labObsSummaryColl, request,  phcDT,  sCurrentTask);

		if (morbObsSummaryColl != null && morbObsSummaryColl.size() > 0) {
			if (morbObsSummaryColl != null) {
				HashMap parameterMap = new HashMap<Object,Object>();
				Iterator ite = morbObsSummaryColl.iterator();
				while (ite.hasNext()) {
					MorbReportSummaryVO mrsVO = (MorbReportSummaryVO) ite
					.next();
					parameterMap.put("ContextAction", "ViewObservationMorb");
					parameterMap.put("observationUID", (mrsVO.getObservationUid()).toString());
					parameterMap.put("method", "viewManageSubmit");
	            	StringBuffer desc = new StringBuffer("<b>"+mrsVO.getConditionDescTxt()+"</b>");
	            	// Lab reports created within morb report
	            	if(PropertyUtil.isStdOrHivProgramArea(phcDT.getProgAreaCd()) && phcDT.getAddTime()!=null && mrsVO.getActivityFromTime()!=null &&  phcDT.getAddTime().equals(mrsVO.getActivityFromTime())){
	            		mrsVO.setReactor(true);
						//if Disassociate Initiating Event permission - don't disable the associate morb checkbox
						if (!nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,
								NBSOperationLookup.DISASSOCIATEINITIATINGEVENT))	            		
							mrsVO.setDisabled(NEDSSConstants.DISABLED);
	            	}
	            	// don't allow to associate or disassociate the STD lab or morbs if its closed STD investigation
	            	if(PropertyUtil.isStdOrHivProgramArea(mrsVO.getProgramArea()) && PropertyUtil.isStdOrHivProgramArea(phcDT.getProgAreaCd()) && phcDT.getInvestigationStatusCd()!=null && !phcDT.getInvestigationStatusCd().equals(NEDSSConstants.STATUS_OPEN))
	            		mrsVO.setDisabled(NEDSSConstants.DISABLED);
	            	boolean flag = true;
	                if((mrsVO.getTheLabReportSummaryVOColl() != null && 
	                		mrsVO.getTheLabReportSummaryVOColl().size() > 0))
	                {
	 				   ArrayList<Object> labFromMorbList = (ArrayList<Object>)mrsVO.getTheLabReportSummaryVOColl();
	 					if (labFromMorbList == null || labFromMorbList.size() == 0) {
	 						logger.debug("Observation summary collection arraylist is null");
	 						}
	 						else {
	 							Iterator<Object>  labIterator = labFromMorbList.iterator();
	 							while (labIterator.hasNext()) 
	 							{
	 								flag = false;
	 								LabReportSummaryVO labReportSummaryVO = (LabReportSummaryVO)labIterator.next();
	 								desc.append(DecoratorUtil.getResultedTestsStringForWorup(labReportSummaryVO.getTheResultedTestSummaryVOCollection()));
	 							}//While
	 						}
	                }
	                if((mrsVO.getTheTreatmentSummaryVOColl() != null && 
	                		mrsVO.getTheTreatmentSummaryVOColl().size() > 0))
	                {
	 				   ArrayList<Object> treatmentFromMorbList = (ArrayList<Object>)mrsVO.getTheTreatmentSummaryVOColl();
	 					if (treatmentFromMorbList == null || treatmentFromMorbList.size() == 0) {
	 						logger.debug("Observation summary collection arraylist is null");
	 						}
	 						else {
	 							NedssUtils nUtil = new NedssUtils();
	 		 		        	nUtil.sortObjectByColumn("getCustomTreatmentNameCode", mrsVO.getTheTreatmentSummaryVOColl(), true);

	 							if(flag)
	 								desc.append("<br>");
	 							desc.append("<b>Treatment Info:</b><UL>");
	 							Iterator<Object>  treatmentIterator = treatmentFromMorbList.iterator();
	 						    while (treatmentIterator.hasNext()) 
	 							{
	 								logger.debug("Inside iterator.hasNext()...");		    	
	 								TreatmentSummaryVO treatment = (TreatmentSummaryVO)treatmentIterator.next();
	 								desc.append(treatment.getCustomTreatmentNameCode() == null ? "" :
	 					            	"<LI><b>"+treatment.getCustomTreatmentNameCode()+"</b></LI>");
	 							}//While
	 						   desc.append("<UL>");
	 						}

	                }
					mrsVO.setConditionDescTxt(desc.toString());
					
					if (mrsVO.isMorbFromDoc()) {
						String startDate = mrsVO.getDateReceived() == null ? "No Date"
								: StringUtils.formatDate(mrsVO
										.getDateReceived());
						String actionLink = "<a href=\"/nbs/"
								+ sCurrentTask
								+ ".do?ContextAction=ViewDocument"
								+ "&method=viewManageSubmit&nbsDocumentUid="
								+ mrsVO.getUid()
								+ "\">"+ startDate+ "</a>"+ "<br>"+ StringUtils
										.formatDatewithHrMin(mrsVO.getDateReceived());
						mrsVO.setActionLink(actionLink + "<br><img src=\"ind_electronic.gif\" title=\"Electronic Indicator\" alt=\"Electronic Indicator\">");
					}
					else
						mrsVO.setActionLink(getHiperlink(dateLink(mrsVO
								.getDateReceived()), sCurrentTask, parameterMap));
					if(mrsVO.getProcessingDecisionCd()!=null){
						if(mrsVO.getIsAssociated())
							mrsVO.setActionLink(mrsVO.getActionLink().concat("<br>").concat(cdv.getCodeShortDescTxt(mrsVO.getProcessingDecisionCd(),"CM_PROCESS_STAGE")));
						else
							mrsVO.setActionLink(mrsVO.getActionLink().concat("<br>").concat(cdv.getCodeShortDescTxt(mrsVO.getProcessingDecisionCd(),"STD_SYPHILIS_NONSYPHILIS_PROCESSING_DECISION")));
					}
					parameterMap = new HashMap<Object,Object>();
				}
			}
			request.setAttribute("observationSummaryMorbList",morbObsSummaryColl);
			request.setAttribute("CurrentTask", sCurrentTask);
		}
	}

	public void populateAssociatedTreatments(ManageEventsForm form,Collection<Object>  treatmentColl, String sPublicHealthCaseUID, HttpServletRequest request){

		String sCurrentTask = NBSContext.getCurrentTask(request.getSession());

		if (treatmentColl != null && treatmentColl.size() > 0) {
			if (treatmentColl != null) {
				HashMap<Object, Object> parameterMap = new HashMap<Object,Object>();
				Iterator<Object> ite = treatmentColl.iterator();
				while (ite.hasNext()) {
					TreatmentSummaryVO treatSumVO = (TreatmentSummaryVO) ite
					.next();
					
					parameterMap.put("ContextAction", "ViewTreatment");
					parameterMap.put("treatmentUID", (treatSumVO.getTreatmentUid()).toString());
					parameterMap.put("method", "viewManageSubmit");
					treatSumVO.setActionLink(getHiperlink(dateLink(treatSumVO
							.getActivityFromTime()), sCurrentTask, parameterMap));
					if(treatSumVO.getNbsDocumentUid()!=null){
						String startDate = dateLink(treatSumVO
								.getActivityFromTime()) == null ? "No Date"
								: dateLink(treatSumVO
										.getActivityFromTime());
						
						startDate = "<a href=\"/nbs/LoadViewDocument2.do?method=cdaDocumentView&docId="
								+ treatSumVO.getNbsDocumentUid()+"&eventId="+treatSumVO.getLocalId()
								+ "&eventType="+NEDSSConstants.TREATMENT_ACT_TYPE_CD
								+ "\" target=\"_blank\">" + startDate + "</a>";
						
						treatSumVO.setActionLink(startDate
								+ "<br>"
								+ "<img src=\"ind_electronic.gif\" title=\"Electronic Indicator\" alt=\"Electronic Indicator\">");
					}
					parameterMap = new HashMap<Object,Object>();
				
				}
			}
			request.setAttribute("treatmentList",treatmentColl);
			request.setAttribute("CurrentTask", sCurrentTask);
		}
	}

	public void populateAssociatedVaccinations(ManageEventsForm form,Collection<Object>  vaccinationColl, String sPublicHealthCaseUID, HttpServletRequest request){

		String sCurrentTask = NBSContext.getCurrentTask(request.getSession());

		if (vaccinationColl != null && vaccinationColl.size() > 0) {
			if (vaccinationColl != null) {
				Iterator<Object> ite = vaccinationColl.iterator();
				while (ite.hasNext()) {
					VaccinationSummaryVO vacSumVO = (VaccinationSummaryVO) ite.next();
					String actionLink = "<a href=\"javascript:contactRecordPopUp('/nbs/PageAction.do?method=viewGenericLoad&businessObjectType=VAC&actUid="+vacSumVO.getInterventionUid()+"&Action=InvPath')\">"+
					dateLink(vacSumVO.getActivityFromTime())+"</a>";
					
					if(NEDSSConstants.ELECTRONIC_IND_VACCINATION.equals(vacSumVO.getElectronicInd())){
						vacSumVO.setActionLink(actionLink+"<br><img src=\"ind_electronic.gif\" title=\"Electronic Indicator\" alt=\"Electronic Indicator\">");
					}else{
						vacSumVO.setActionLink(actionLink);
					}
				}
			}
			request.setAttribute("vaccinationList",vaccinationColl);
			request.setAttribute("CurrentTask", sCurrentTask);
		}
	}

	public void populateAssociatedDocument(ManageEventsForm form,Collection<Object>  documentColl, String sPublicHealthCaseUID, HttpServletRequest request){

		String sCurrentTask = NBSContext.getCurrentTask(request.getSession());

		if(documentColl != null) {
			HashMap<Object,Object> parameterMap = new HashMap<Object,Object>();
			Iterator<Object> ite = documentColl.iterator();
			while (ite.hasNext()) {
				SummaryDT doc = (SummaryDT) ite.next();
				if(doc.getDocPurposeCd() != null && doc.getDocPurposeCd().length() > 0)
					doc.setDocPurposeCdConditionDescTxt(cdv.getCodeShortDescTxt(doc.getDocPurposeCd(),"NBS_DOC_PURPOSE"));
				if(doc.getDocType() != null && doc.getDocType().length() > 0)
					doc.setDocTypeConditionDescTxt(cdv.getCodeShortDescTxt(doc.getDocType(),"PUBLIC_HEALTH_EVENT"));
				if (doc.getDocEventTypeCd() != null) {
					String docTypeCd = doc.getDocEventTypeCd();
					if (docTypeCd.equals(NEDSSConstants.LABRESULT_CODE))
						doc.setDocTypeConditionDescTxt("Lab Report");
					else if (docTypeCd.equals(NEDSSConstants.MORBIDITY_CODE))
						doc.setDocTypeConditionDescTxt("Morb Report");
					else if (docTypeCd.equals(NEDSSConstants.CLASS_CD_CONTACT))
						doc
								.setDocTypeConditionDescTxt("Contact Record");
				}
				if(doc.getDocStatusCd() != null && doc.getDocStatusCd().length() > 0)
					doc.setDocStatusCdConditionDescTxt(cdv.getCodeShortDescTxt(doc.getDocStatusCd(),"NBS_DOC_STATUS"));
				//doc.setIsAssociated(true);
				parameterMap.put("ContextAction", NBSConstantUtil.ViewDocument);
				parameterMap.put("nbsDocumentUid", doc.getNbsDocumentUid().toString());
				parameterMap.put("method", "viewManageSubmit");
				doc.setActionLink(getHiperlink(dateLink(doc.getLastChgTime()), sCurrentTask, parameterMap));
				parameterMap = new HashMap<Object,Object>();
			}
		}
		request.setAttribute("documentSummaryList",documentColl);
		request.setAttribute("CurrentTask", sCurrentTask);
	}
	
	public String getHiperlink(String displayname, String currentTask,
			HashMap<Object,Object> parameterMap) {
		String url = NavigatorUtil.getLink(currentTask+".do", displayname,
				parameterMap);
		return url;
	}
	private String dateLink(java.sql.Timestamp timestamp) {
		Date date = null;
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		if (timestamp != null) {
			date = new Date(timestamp.getTime());
		}
		if (date == null) {
			return "No Date";
		}
		else {
			return formatter.format(date);
		}
	}

	public Collection<Object>  setIsAssociationIsTouchedFlagAsPerFrontEnd(Collection<Object> labObsSummaryColl,Collection<Object>  newEventSummaryCollection, HttpServletRequest request)
	{
		Collection<Object>  newLabCollection  = new ArrayList<Object> ();
		if (labObsSummaryColl != null && labObsSummaryColl.size() > 0)
		{

			ReportSummaryInterface summaryVO = null;
			Iterator<Object> anItor = labObsSummaryColl.iterator();
			while(anItor.hasNext())
			{
				summaryVO = (ReportSummaryInterface)anItor.next();
				boolean isAssociated= false;
				String flag1 = summaryVO.getObservationUid().toString();
				if(newEventSummaryCollection!=null) {
					for (Iterator<Object> anIterator = newEventSummaryCollection.iterator(); anIterator.hasNext(); ){
						String selectedUid = (String)anIterator.next();
						if(flag1.equalsIgnoreCase(selectedUid) && (summaryVO.getIsAssociated() )){
							isAssociated = true;
							summaryVO.setItTouched(false);
							newLabCollection.add(summaryVO);
						}
						else if(flag1.equalsIgnoreCase(selectedUid)){
							isAssociated= true;
							summaryVO.setItAssociated(true);
							summaryVO.setItTouched(true);
							newLabCollection.add(summaryVO);
						}
					}
				}
				if(summaryVO.getIsAssociated() && !isAssociated){
					summaryVO.setItAssociated(false);
					summaryVO.setItTouched(true);
					newLabCollection.add(summaryVO);
				}
			}

		}
		return newLabCollection;

	}

	public Collection<Object>  setMorbIsAssociationIsTouchedFlagAsPerFrontEnd(Collection<Object> morbObsSummaryColl,Collection<Object>  newEventSummaryCollection, HttpServletRequest request)
	{
		Collection<Object>  newMorbLabCollection  = new ArrayList<Object> ();
		if (morbObsSummaryColl != null && morbObsSummaryColl.size() > 0)
		{
			MorbReportSummaryVO morbSummaryVO = null;
			Iterator<Object> anItor = morbObsSummaryColl.iterator();
			while(anItor.hasNext())
			{
				morbSummaryVO = (MorbReportSummaryVO)anItor.next();
				boolean isAssociated= false;
				String flag = morbSummaryVO.getLocalId().toString();
				if(newEventSummaryCollection!=null) {
					for (Iterator<Object> anIterator = newEventSummaryCollection.iterator(); anIterator.hasNext(); ){
						String selectedUid = (String)anIterator.next();
						if(flag.equalsIgnoreCase(selectedUid) && (morbSummaryVO.getIsAssociated() )){
							isAssociated = true;
							morbSummaryVO.setItTouched(false);
							newMorbLabCollection.add(morbSummaryVO);
						}
						else if(flag.equalsIgnoreCase(selectedUid)){
							isAssociated= true;
							morbSummaryVO.setItAssociated(true);
							morbSummaryVO.setItTouched(true);
							newMorbLabCollection.add(morbSummaryVO);
						}
					}
				}
				if(morbSummaryVO.getIsAssociated() && !isAssociated){
					morbSummaryVO.setItAssociated(false);
					morbSummaryVO.setItTouched(true);
					newMorbLabCollection.add(morbSummaryVO);
				}
			}

		}
		return newMorbLabCollection;
	}

	public Collection<Object>  setTreatIsAssociationIsTouchedFlagAsPerFrontEnd(Collection<Object> treatmentSummaryColl,Collection<Object>  newEventSummaryCollection, HttpServletRequest request)
	{
		Collection<Object>  newTreatmentCollection  = new ArrayList<Object> ();
		if (treatmentSummaryColl != null && treatmentSummaryColl.size() > 0)
		{
			TreatmentSummaryVO treatmentSummaryVO = null;
			Iterator<Object> anItor = treatmentSummaryColl.iterator();
			while(anItor.hasNext())
			{
				treatmentSummaryVO = (TreatmentSummaryVO)anItor.next();
				boolean isAssociated= false;
				String flag1 = treatmentSummaryVO.getLocalId().toString();
				if(newEventSummaryCollection!=null) {
					for (Iterator<Object> anIterator = newEventSummaryCollection.iterator(); 
							anIterator.hasNext(); ){
						String selectedUid = (String)anIterator.next();
						if(flag1.equalsIgnoreCase(selectedUid) && (treatmentSummaryVO.getIsAssociated() )){
							isAssociated = true;
							treatmentSummaryVO.setIsTouched(false);
							newTreatmentCollection.add(treatmentSummaryVO);
						}
						else if(flag1.equalsIgnoreCase(selectedUid)){
							isAssociated= true;
							treatmentSummaryVO.setIsAssociated(true);
							treatmentSummaryVO.setIsTouched(true);
							newTreatmentCollection.add(treatmentSummaryVO);
						}
					}
				}
				if(treatmentSummaryVO.getIsAssociated() 
						&& !isAssociated){
					treatmentSummaryVO.setIsAssociated(false);
					treatmentSummaryVO.setIsTouched(true);
					newTreatmentCollection.add(treatmentSummaryVO);
				}
			}
		}
		return newTreatmentCollection;
	}

	public Collection<Object>  setVacciIsAssociationIsTouchedFlagAsPerFrontEnd
		(Collection<Object> vaccinationSumColl,Collection<Object>  newEventSummaryCollection, HttpServletRequest request)
	{
		Collection<Object>  newVaccinationCollection  = new ArrayList<Object> ();
		if (vaccinationSumColl != null && vaccinationSumColl.size() > 0)
		{
			VaccinationSummaryVO vaccinationSummaryVO = null;
			Iterator<Object> anItor = vaccinationSumColl.iterator();
			while(anItor.hasNext())
			{
				vaccinationSummaryVO = (VaccinationSummaryVO)anItor.next();
				boolean isAssociated= false;
				String flag1 = vaccinationSummaryVO.getLocalId().toString();
				if(newEventSummaryCollection!=null) {
					for (Iterator<Object> anIterator = newEventSummaryCollection.iterator(); anIterator.hasNext(); ){
						String selectedUid = (String)anIterator.next();
						if(flag1.equalsIgnoreCase(selectedUid) && (vaccinationSummaryVO.getIsAssociated() )){
							isAssociated = true;
							vaccinationSummaryVO.setIsTouched(false);
							newVaccinationCollection.add(vaccinationSummaryVO);
						}
						else if(flag1.equalsIgnoreCase(selectedUid)){
							isAssociated= true;
							vaccinationSummaryVO.setIsAssociated(true);
							vaccinationSummaryVO.setIsTouched(true);
							newVaccinationCollection.add(vaccinationSummaryVO);
						}
					}
				}
				if(vaccinationSummaryVO.getIsAssociated() && !isAssociated){
					vaccinationSummaryVO.setIsAssociated(false);
					vaccinationSummaryVO.setIsTouched(true);
					newVaccinationCollection.add(vaccinationSummaryVO);
				}
			}
		}
		return newVaccinationCollection;
	}


	public boolean setAssociations(String strPHCUID, Collection<Object>  ObsVOColl, Collection<Object>  vaccinationSumColl, Collection<Object>  documentSumColl, 
			Collection<Object> treatmentSumColl, HttpSession session)
	{
		logger.debug("getProxyObject has been called, strPHCUID = " + strPHCUID);
		try
		{
			Long publicHealthCaseUID = new Long(strPHCUID);
			String sBeanJndiName = JNDINames.INVESTIGATION_PROXY_EJB;
			String sMethod = "setAssociations";
			Object[] oParams = new Object[]
                            {publicHealthCaseUID, ObsVOColl, vaccinationSumColl, documentSumColl, treatmentSumColl,
								new Boolean(true)};
			MainSessionCommand msCommand = null;
			logger.debug("checking if mscommand is null:");
			if (msCommand == null)
			{
				MainSessionHolder holder = new MainSessionHolder();
				logger.debug("about to call holder:");
				msCommand = holder.getMainSessionCommand(session);
			}
			logger.debug("about to call ejb:");	
			ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod,
					oParams);
		}
		catch (Exception ex)
		{
			logger.fatal("getProxyObject: ", ex);

			return false;
		}

		return true;
	}

	
	public Collection<Object>  setDocIsAssociationIsTouchedFlagAsPerFrontEnd(Collection<Object> documentSumColl,Collection<Object>  newEventSummaryCollection, HttpServletRequest request)
	{
		Collection<Object>  newDocumentCollection  = new ArrayList<Object> ();
		if (documentSumColl != null && documentSumColl.size() > 0)
		{
			SummaryDT summaryDT = null;
			Iterator<Object> anItor = documentSumColl.iterator();
			while(anItor.hasNext())
			{
				summaryDT = (SummaryDT)anItor.next();
				boolean isAssociated= false;
				String flag1 = summaryDT.getLocalId().toString();
				if(newEventSummaryCollection!=null) {
					for (Iterator<Object> anIterator = newEventSummaryCollection.iterator(); anIterator.hasNext(); ){
						String selectedUid = (String)anIterator.next();
						if(flag1.equalsIgnoreCase(selectedUid) && (summaryDT.getIsAssociated() )){
							isAssociated = true;
							summaryDT.setIsTouched(false);
							newDocumentCollection.add(summaryDT);
						}
						else if(flag1.equalsIgnoreCase(selectedUid)){
							isAssociated= true;
							summaryDT.setIsAssociated(true);
							summaryDT.setIsTouched(true);
							newDocumentCollection.add(summaryDT);
						}
					}
				}
				if(summaryDT.getIsAssociated() && !isAssociated){
					summaryDT.setIsAssociated(false);
					summaryDT.setIsTouched(true);
					newDocumentCollection.add(summaryDT);
				}
			}
		}
		return newDocumentCollection;
	}
	public boolean setDocumentAssociations(String strPhcUid, Collection<Object>  ObsVOColl,
			HttpSession session)
	{
		logger.debug(
				"getProxyObject has been called, strPHCUID = " + strPhcUid);

		try
		{

			Long publicHealthCaseUID = new Long(strPhcUid);
			String sBeanJndiName = JNDINames.INVESTIGATION_PROXY_EJB;
			String sMethod = "setObservationAssociations";
			Object[] oParams = new Object[]
			                              {publicHealthCaseUID, ObsVOColl};
			MainSessionCommand msCommand = null;
			logger.debug("checking if mscommand is null:");
			if (msCommand == null)
			{
				MainSessionHolder holder = new MainSessionHolder();
				logger.debug("about to call holder:");
				msCommand = holder.getMainSessionCommand(session);
			}
			logger.debug("about to call ejb:");
			ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod,oParams);
		}
		catch (Exception ex)
		{
			logger.fatal("getProxyObject: ", ex);
			return false;
		}
		return true;
	}
	
	
	public void setAssociationsFlagForDoc(Collection<Object> actRelationshipDtCollection, Collection<Object>  docSummaryColl)
	{
		if(actRelationshipDtCollection  != null) {
			Iterator<Object> ite = actRelationshipDtCollection.iterator();
			while (ite.hasNext()) {
				ActRelationshipDT actRelDt = (ActRelationshipDT) ite.next();
				if(actRelDt.getTypeCd().equalsIgnoreCase(NEDSSConstants.DocToPHC))
				{
					if(docSummaryColl != null)
					{
						Iterator<Object> itet = docSummaryColl.iterator();
						while(itet.hasNext())
						{
							SummaryDT summDt = (SummaryDT) itet.next();

							if(summDt.getNbsDocumentUid().longValue() == actRelDt.getSourceActUid().longValue())
								summDt.setIsAssociated(true);
						}
					}
				}
			}
		}
	}
	public static void storeContactContextInformation(HttpSession session, PublicHealthCaseVO publicHealthCaseVO, Collection<Object> personVoCollection){
		  NBSContext.store(session, NBSConstantUtil.DSInvestigationUid, publicHealthCaseVO.getThePublicHealthCaseDT().getPublicHealthCaseUid().toString());
		  NBSContext.store(session, NBSConstantUtil.DSInvestigationLocalID, publicHealthCaseVO.getThePublicHealthCaseDT().getLocalId());
		  NBSContext.store(session, NBSConstantUtil.DSInvestigationProgramArea, publicHealthCaseVO.getThePublicHealthCaseDT().getProgAreaCd());
		  NBSContext.store(session, NBSConstantUtil.DSInvestigationJurisdiction, publicHealthCaseVO.getThePublicHealthCaseDT().getJurisdictionCd());
		  NBSContext.store(session, NBSConstantUtil.DSSharedInd, publicHealthCaseVO.getThePublicHealthCaseDT().getSharedInd());
		  String sCurrTask = NBSContext.getCurrentTask(session);
		  NBSContext.store(session, NBSConstantUtil.DSInvestigationPath, sCurrTask);
     	  NBSContext.store(session, NBSConstantUtil.DSInvestigationCondition, publicHealthCaseVO.getThePublicHealthCaseDT().getCd());
     	  NBSContext.store(session, NBSConstantUtil.DSCaseStatus, (publicHealthCaseVO.getThePublicHealthCaseDT().getCaseClassCd()==null?"":publicHealthCaseVO.getThePublicHealthCaseDT().getCaseClassCd()));
     	  NBSContext.store(session, NBSConstantUtil.DSConditionCdDescTxt, publicHealthCaseVO.getThePublicHealthCaseDT().getCdDescTxt()==null?"":publicHealthCaseVO.getThePublicHealthCaseDT().getCdDescTxt());
     	  if (publicHealthCaseVO.getTheCaseManagementDT() != null) {
     		 NBSContext.store(session, NBSConstantUtil.DSStdDispositionCd, (publicHealthCaseVO.getTheCaseManagementDT().getFldFollUpDispo()==null?"":publicHealthCaseVO.getTheCaseManagementDT().getFldFollUpDispo()));
     		 NBSContext.store(session, NBSConstantUtil.DSStdInterviewStatusCd, (publicHealthCaseVO.getTheCaseManagementDT().getPatIntvStatusCd()==null?"":publicHealthCaseVO.getTheCaseManagementDT().getPatIntvStatusCd()));
     	  } else { 
     		 NBSContext.store(session, NBSConstantUtil.DSStdDispositionCd, "");
     		 NBSContext.store(session, NBSConstantUtil.DSStdInterviewStatusCd, "");
     	  }
     	 Iterator<Object>  anIterator = null;
		  PersonVO personVO = null;
		  if (personVoCollection != null)
		  {
			  for (anIterator = personVoCollection.iterator(); anIterator.hasNext();) {
			  personVO = (PersonVO) anIterator.next();
				  if (personVO.getThePersonDT().getCd().equals(NEDSSConstants.PAT))
				  {
					  NBSContext.store(session, NBSConstantUtil.DSPersonSummary, personVO.getThePersonDT().getPersonParentUid());
					  NBSContext.store(session, NBSConstantUtil.DSPatientPersonLocalID, personVO.getThePersonDT().getLocalId());
					  NBSContext.store(session, NBSConstantUtil.DSPatientRevisionUID, personVO.getThePersonDT().getPersonUid().toString());
					  if (personVO.getThePersonDT().getCurrSexCd() != null)
						  	NBSContext.store(session, NBSConstantUtil.DSPatientCurrentSexCd, personVO.getThePersonDT().getCurrSexCd());
				  }
			  }
		  }
		  Iterator<Object>  iterator = publicHealthCaseVO.getTheParticipationDTCollection().iterator();
		  boolean investigatorInContext = false;
		  while(iterator.hasNext())
		  {
			  ParticipationDT participationDT = (ParticipationDT)iterator.next();
			  if(participationDT.getTypeCd() != null && participationDT.getTypeCd().equals(NEDSSConstants.PHC_INVESTIGATOR) && participationDT.getSubjectEntityUid()!= null)
			  {
				  investigatorInContext=true;
			    NBSContext.store(session, NBSConstantUtil.DSInvestigatorUid,  participationDT.getSubjectEntityUid().toString());
			  }
		  }
		  if(!investigatorInContext)
			  NBSContext.store(session, NBSConstantUtil.DSInvestigatorUid, "");
	}
	
	public static PublicHealthCaseVO getPublicHealthCaseVO(Long publicHealthCaseUid, HttpSession session) {
		PublicHealthCaseVO publicHealthCaseVO = null;
		MainSessionCommand msCommand = null;
		try {
			String sBeanJndiName = JNDINames.PAM_PROXY_EJB;
			String sMethod = "getPublicHealthCaseVO";
			Object[] oParams = new Object[] { publicHealthCaseUid };
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(session);
			ArrayList<?> arr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
			publicHealthCaseVO = (PublicHealthCaseVO) arr.get(0);
		} catch (Exception ex) {
			ex.printStackTrace();
			if (session == null) {
				logger.error("Error: no session, please login");
			}
			logger.fatal("personVO: ", ex);
		}
		return publicHealthCaseVO;
	}// getPublicHealthCaseVO

	
	
	
	public static PamProxyVO setEntities(PamProxyVO pamProxyVO, NBSSecurityObj nbsSecurityObj, HttpServletRequest request){
		Collection<Object> participationCollection= pamProxyVO.getTheParticipationDTCollection();
		Iterator<Object> it = participationCollection.iterator();
		while(it.hasNext()){
			ParticipationDT participationDT= (ParticipationDT)it.next();
			if(participationDT.getSubjectClassCd().equalsIgnoreCase(NEDSSConstants.CLASS_CD_PSN) && !participationDT.getTypeCd().equalsIgnoreCase(NEDSSConstants.PHC_PATIENT)){
				Long providerEntityUid = participationDT.getSubjectEntityUid();
				PersonVO providerVO=getProvider(providerEntityUid,nbsSecurityObj,request);
				if(pamProxyVO.getThePersonVOCollection()==null){
					pamProxyVO.setThePersonVOCollection(new ArrayList<Object>());
				}
				pamProxyVO.getThePersonVOCollection().add(providerVO);
			}
			else if(participationDT.getSubjectClassCd().equalsIgnoreCase(NEDSSConstants.CLASS_CD_ORG)){
				Long organizationEntityUid = participationDT.getSubjectEntityUid();
				OrganizationVO organizationVO = getOrganization(organizationEntityUid,nbsSecurityObj,request);
				if(pamProxyVO.getTheOrganizationVOCollection()==null){
					pamProxyVO.setTheOrganizationVOCollection(new ArrayList<Object>());
				}
				pamProxyVO.getTheOrganizationVOCollection().add(organizationVO);
			}
		}
		return pamProxyVO;
	}
	public static PageProxyVO setEntities(PageActProxyVO pageProxyVO, NBSSecurityObj nbsSecurityObj, HttpServletRequest request){
		Collection<Object> participationCollection= pageProxyVO.getTheParticipationDTCollection();
		Iterator<Object> it = participationCollection.iterator();
		while(it.hasNext()){
			ParticipationDT participationDT= (ParticipationDT)it.next();
			if(participationDT.getSubjectClassCd().equalsIgnoreCase(NEDSSConstants.CLASS_CD_PSN)&& !participationDT.getTypeCd().equalsIgnoreCase(NEDSSConstants.PHC_PATIENT)){
				Long providerEntityUid = participationDT.getSubjectEntityUid();
				PersonVO providerVO=getProvider(providerEntityUid,nbsSecurityObj,request);
				if(pageProxyVO.getThePersonVOCollection()==null){
					pageProxyVO.setThePersonVOCollection(new ArrayList<Object>());
				}
				pageProxyVO.getThePersonVOCollection().add(providerVO);
			}
			else if(participationDT.getSubjectClassCd().equalsIgnoreCase(NEDSSConstants.CLASS_CD_ORG)){
				Long organizationEntityUid = participationDT.getSubjectEntityUid();
				OrganizationVO organizationVO = getOrganization(organizationEntityUid,nbsSecurityObj,request);
				if(pageProxyVO.getTheOrganizationVOCollection()==null){
					pageProxyVO.setTheOrganizationVOCollection(new ArrayList<Object>());
				}
				pageProxyVO.getTheOrganizationVOCollection().add(organizationVO);
			}
		}
		return pageProxyVO;
	}
	/**
	 * Utility method to get the provider information
	 * @param providerEntityUid
	 * @param nbsSecurityObj
	 * @param request
	 * @return
	 */
	private static PersonVO getProvider( Long providerEntityUid, NBSSecurityObj nbsSecurityObj, HttpServletRequest request){
		PersonVO providerVO = null;
		try {
			providerVO = getInvestigator((providerEntityUid+""), nbsSecurityObj, request);
		} catch (NEDSSAppConcurrentDataException e) {
			logger.error("InvestigationUtil.getProvider The provider information is not available"+e);
		} catch (Exception e) {
			logger.error("InvestigationUtil.getProvider The provider information is not available"+e);
		}
		return providerVO;
	}
	/**
	 * Utility method to get the Organization information
	 * @param organizationEntityUid
	 * @param nbsSecurityObj
	 * @param request
	 * @return
	 */
	public static OrganizationVO getOrganization( Long organizationEntityUid,NBSSecurityObj nbsSecurityObj, HttpServletRequest request){
		OrganizationVO organizationVO = null;
		try {
			organizationVO = getOrganization((organizationEntityUid+""), nbsSecurityObj, request);
		} catch (NEDSSAppConcurrentDataException e) {
			logger.error("InvestigationUtil.getOrganization The provider information is not available"+e);
		} catch (Exception e) {
			logger.error("InvestigationUtil.getOrganization The provider information is not available"+e);
		}
		return organizationVO;
	}
	
	/**
	 * utility method to get the PersonVO
	 * @param providerEntityUid
	 * @param nbsSecurityObj
	 * @param request
	 * @return PersonVO
	 * @throws NEDSSAppConcurrentDataException
	 * @throws Exception
	 */
	public static PersonVO getInvestigator(String providerEntityUid, NBSSecurityObj nbsSecurityObj, HttpServletRequest request)throws NEDSSAppConcurrentDataException, Exception 
	{
		HttpSession session = request.getSession();
		PersonVO personVO = null;
		try {
			MainSessionCommand msCommand = null;
			Long UID = new Long(providerEntityUid.trim());
			String sBeanJndiName = JNDINames.ENTITY_PROXY_EJB;
			String sMethod = "getProvider";
			Object[] oParams = new Object[] {UID};

			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(session);

			ArrayList<?> arr = msCommand.processRequest(sBeanJndiName,sMethod, oParams);
			personVO = (PersonVO) arr.get(0);
		} catch (Exception e) {
			logger.error("Error: "+e.getMessage());
			e.printStackTrace();
		}
		return personVO;
	}
	
	/**Utility method to get the OrganizationVO from the database
	 * @param organizationUid
	 * @param nbsSecurityObj
	 * @param request
	 * @return OrganizationVO
	 * @throws NEDSSAppConcurrentDataException
	 * @throws Exception
	 */
	private static OrganizationVO getOrganization(String organizationUid, NBSSecurityObj nbsSecurityObj, HttpServletRequest request)throws NEDSSAppConcurrentDataException, Exception 
	{
		HttpSession session = request.getSession();
		OrganizationVO organizationVO = null;
		try {
			MainSessionCommand msCommand = null;
			Long UID = new Long(organizationUid.trim());
			String sBeanJndiName = JNDINames.ENTITY_PROXY_EJB;
			String sMethod = "getOrganization";
			Object[] oParams = new Object[] {UID};
			MainSessionHolder holder = new MainSessionHolder();
			msCommand = holder.getMainSessionCommand(session);
			ArrayList<?> arr = msCommand.processRequest(sBeanJndiName,sMethod, oParams);
			organizationVO = (OrganizationVO) arr.get(0);
		} catch (Exception e) {
			logger.error("Error in call to getOrganization: "+e.getMessage());
			e.printStackTrace();
		}
		return organizationVO;
	}
}
