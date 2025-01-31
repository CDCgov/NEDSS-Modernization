package gov.cdc.nedss.systemservice.ejb.edxdocumentejb.util;

import gov.cdc.nedss.act.actid.dt.ActIdDT;
import gov.cdc.nedss.act.notification.dt.NotificationDT;
import gov.cdc.nedss.act.notification.vo.NotificationVO;
import gov.cdc.nedss.act.publichealthcase.dt.ConfirmationMethodDT;
import gov.cdc.nedss.act.publichealthcase.dt.PublicHealthCaseDT;
import gov.cdc.nedss.act.publichealthcase.vo.PublicHealthCaseVO;
import gov.cdc.nedss.association.dt.ActRelationshipDT;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.nnd.ejb.nndmessageprocessorejb.helper.MessageBuilderHelper;
import gov.cdc.nedss.page.ejb.pageproxyejb.bean.PageProxy;
import gov.cdc.nedss.page.ejb.pageproxyejb.bean.PageProxyHome;
import gov.cdc.nedss.page.ejb.pageproxyejb.dt.NBSNoteDT;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.PageProxyVO;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.act.PageActProxyVO;
import gov.cdc.nedss.pam.act.NbsCaseAnswerDT;
import gov.cdc.nedss.pam.vo.PamVO;
import gov.cdc.nedss.phdc.AnswerType;
import gov.cdc.nedss.phdc.CaseType;
import gov.cdc.nedss.phdc.ClinicalInformationType;
import gov.cdc.nedss.phdc.CodedType;
import gov.cdc.nedss.phdc.CommonQuestionsType;
import gov.cdc.nedss.phdc.ContainerDocument;
import gov.cdc.nedss.phdc.EpidemiologicInformationType;
import gov.cdc.nedss.phdc.HeaderType;
import gov.cdc.nedss.phdc.HierarchicalDesignationType;
import gov.cdc.nedss.phdc.InvestigationInformationType;
import gov.cdc.nedss.phdc.ObservationType;
import gov.cdc.nedss.phdc.OrganizationParticipantType;
import gov.cdc.nedss.phdc.ProviderParticipantType;
import gov.cdc.nedss.phdc.ReportingInformationType;
import gov.cdc.nedss.phdc.ContainerDocument.Container;
import gov.cdc.nedss.proxy.ejb.notificationproxyejb.bean.NotificationProxy;
import gov.cdc.nedss.proxy.ejb.notificationproxyejb.bean.NotificationProxyHome;
import gov.cdc.nedss.proxy.ejb.notificationproxyejb.vo.NotificationProxyVO;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.bean.PamProxy;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.bean.PamProxyHome;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.vo.CTContactProxyVO;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.vo.PamProxyVO;
import gov.cdc.nedss.systemservice.dt.EDXActivityDetailLogDT;
import gov.cdc.nedss.systemservice.ejb.casenotificationejb.dt.ExportReceivingFacilityDT;
import gov.cdc.nedss.systemservice.ejb.decisionsupportejb.dao.DSMAlgorithmDaoImpl;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dt.EdxRuleAlgorothmManagerDT;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.labutil.EdxELRConstants;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.util.EdxPHCRConstants.MSG_TYPE;
import gov.cdc.nedss.systemservice.ejb.jurisdictionejb.bean.Jurisdiction;
import gov.cdc.nedss.systemservice.ejb.jurisdictionejb.bean.JurisdictionHome;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dao.NbsDocumentDAOImpl;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dt.NBSDocumentDT;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.util.XMLTypeToNBSObject;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.vo.NBSDocumentVO;
import gov.cdc.nedss.systemservice.ejb.questionmapejb.dt.NbsQuestionMetadata;
import gov.cdc.nedss.systemservice.ejb.srtcachemanagerejb.dt.SRTGenericCodeDT;
import gov.cdc.nedss.systemservice.ejb.srtmapejb.dao.SRTMapDAOImpl;
import gov.cdc.nedss.systemservice.nbscontext.NBSConstantUtil;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.nbssecurity.ProgramAreaJurisdictionUtil;
import gov.cdc.nedss.systemservice.nbssecurity.ProgramAreaVO;
import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.action.decisionsupportmanagement.util.DecisionSupportConstants;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.QuestionsCache;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.UndeclaredThrowableException;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.rmi.PortableRemoteObject;

import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;

/**
 *
 * @author Pradeep Sharma
 * @since Release 4.1
 * @updated Release 4.1 NBS Inv. Autocreate
 * @updated Release 4.3
 */
public class EdxPHCRDocumentUtil {
	private static final long serialVersionUID = 1L;
	public static final String _REQUIRED = "_REQUIRED";
	static final LogUtils logger = new LogUtils(EdxPHCRDocumentUtil.class.getName());

	public Object createInvestigation(Long documentUid,NBSSecurityObj nbsSecurityObj) {
		NBSDocumentVO nbsDocumentVO = new NBSDocumentVO();
		Object obj = null;
		try {
			NbsDocumentDAOImpl nbsDocDAO = new NbsDocumentDAOImpl();
			nbsDocumentVO = nbsDocDAO.getNBSDocument(documentUid);
			obj = createPageActProxyVO(nbsDocumentVO, nbsSecurityObj);
			if (obj instanceof PageProxyVO) {
				PageActProxyVO pageProxyVO = (PageActProxyVO) obj;
				PamVO pageVO = getCreatePamVO(pageProxyVO.getPageVO());
				pageProxyVO.setPageVO(pageVO);
			} else {
			}
		} catch (NEDSSSystemException e) {
			logger.error("EdxPHCRDocumentUtil.createInvestigation NEDSSSystemException thrown "+ e);
			throw new EJBException("EdxPHCRDocumentUtil.createInvestigation NEDSSSystemException thrown "+ e, e);
		} catch (ClassCastException e) {
			logger.error("EdxPHCRDocumentUtil.createInvestigation ClassCastException thrown "+ e, e);
			throw new EJBException("EdxPHCRDocumentUtil.createInvestigation ClassCastException thrown "+ e, e);
		} catch (EJBException e) {
			logger.error("EdxPHCRDocumentUtil.createInv EJBException thrown "+ e, e);
			throw new EJBException("EdxPHCRDocumentUtil.createInv EJBException thrown " + e, e);
		} catch (UndeclaredThrowableException e) {
			logger.error("EdxPHCRDocumentUtil.createInv UndeclaredThrowableException thrown "+ e, e);
			throw new EJBException(
					"EdxPHCRDocumentUtil.createInv UndeclaredThrowableException thrown "+ e, e);
		} catch (Exception e) {
			logger.error("EdxPHCRDocumentUtil.createInv Exception thrown " + e);
			throw new EJBException("EdxPHCRDocumentUtil.createInv Exception thrown " + e, e);
		}
		return obj;
	}

	public static Long createPageProxyVO(Object obj,
			NBSSecurityObj nbsSecurityObj, String eventType) throws NEDSSAppException {
		Long actUid = null;
		try {
			NedssUtils nu = new NedssUtils();
			// Object obj= createPageActProxyVO(nbsDocumentVO,nbsSecurityObj);

			if (obj instanceof PageProxyVO) {
				PageActProxyVO pageActProxyVO = (PageActProxyVO) obj;
				Object object = nu.lookupBean(JNDINames.PAGE_PROXY_EJB);
				PageProxyHome pageProxyHome = (PageProxyHome) javax.rmi.PortableRemoteObject
						.narrow(object, PageProxyHome.class);
				PageProxy pageProxy = pageProxyHome.create();
				actUid = pageProxy.setPageProxyVO(eventType,
						pageActProxyVO, nbsSecurityObj);
				pageActProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().setPublicHealthCaseUid(actUid);
				}else if (obj instanceof CTContactProxyVO) {
					CTContactProxyVO contactProxyVO = (CTContactProxyVO) obj;
					Object object = nu.lookupBean(JNDINames.PAM_PROXY_EJB);
					PamProxyHome pamProxyHome = (PamProxyHome) javax.rmi.PortableRemoteObject
							.narrow(object, PamProxyHome.class);
					PamProxy pamProxy = pamProxyHome.create();
					actUid = pamProxy.setContactProxyVO( contactProxyVO, nbsSecurityObj);
					contactProxyVO.getcTContactVO().getcTContactDT().setCtContactUid(actUid);

			} else {
				Object object = nu.lookupBean(JNDINames.PAM_PROXY_EJB);
				PamProxyHome pamProxyHome = (PamProxyHome) javax.rmi.PortableRemoteObject
						.narrow(object, PamProxyHome.class);
				PamProxy pamProxy = pamProxyHome.create();
				PamProxyVO pamProxyVO = (PamProxyVO) obj;
				actUid = pamProxy.setPamProxy(pamProxyVO, nbsSecurityObj);
				pamProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().setPublicHealthCaseUid(actUid);
			}
		} catch (NEDSSSystemException e) {
			logger.error("EdxPHCRDocumentUtil.createInvestigation NEDSSSystemException thrown "+ e, e);
			throw new NEDSSAppException(
					"EdxPHCRDocumentUtil.createInvestigation NEDSSSystemException thrown "+ e, e);
		} catch (ClassCastException e) {
			logger.error("EdxPHCRDocumentUtil.createInvestigation ClassCastException thrown "+ e);
			throw new NEDSSAppException(
					"EdxPHCRDocumentUtil.createInvestigation ClassCastException thrown "+ e, e);
		} catch (RemoteException e) {
			logger.error("EdxPHCRDocumentUtil.createInvestigation RemoteException thrown "+ e);
			throw new NEDSSAppException(
					"EdxPHCRDocumentUtil.createInvestigation RemoteException thrown "+ e, e);
		} catch (EJBException e) {
			logger.error("EdxPHCRDocumentUtil.createInvestigation EJBException thrown "	+ e);
			throw new NEDSSAppException(
					"EdxPHCRDocumentUtil.createInvestigation EJBException thrown "+ e, e);
		} catch (CreateException e) {
			logger.error("EdxPHCRDocumentUtil.createInvestigation CreateException thrown " + e);
			throw new NEDSSAppException(
					"EdxPHCRDocumentUtil.createInvestigation CreateException thrown "+ e, e);
		} catch (FinderException e) {
			logger.error("EdxPHCRDocumentUtil.createInvestigation FinderException thrown "+ e);
			throw new NEDSSAppException("EdxPHCRDocumentUtil.createInvestigation FinderException thrown "+ e, e);
		} catch (NEDSSConcurrentDataException e) {
			logger.error("EdxPHCRDocumentUtil.createInvestigation NEDSSConcurrentDataException thrown "+ e);
			throw new NEDSSAppException("EdxPHCRDocumentUtil.createInvestigation NEDSSConcurrentDataException thrown "+ e, e);
		}catch (Exception e) {
			logger.error("EdxPHCRDocumentUtil.createInvestigation Exception thrown "+ e);
			throw new NEDSSAppException("EdxPHCRDocumentUtil.createInvestigation Exception thrown "+ e, e);
		}
		return actUid;
	}

	public static Object createPageActProxyVO(NBSDocumentVO nBSDocumentVO, NBSSecurityObj nbsSecurityObj) throws NEDSSAppException {
		Object object =createProxyVO(nBSDocumentVO, null, nbsSecurityObj);
		return object;
	}

	public static Object createProxyVOUtil(NBSDocumentVO nBSDocumentVO, String conditionCode, NBSSecurityObj nbsSecurityObj) throws NEDSSAppException {
		Object object =createProxyVO(nBSDocumentVO,conditionCode,nbsSecurityObj);
		return object;
	}

	public static Object createProxyVO(NBSDocumentVO nBSDocumentVO,String conditionCode, NBSSecurityObj nbsSecurityObj) throws NEDSSAppException {
		Object obj = null;
		PageActProxyVO pageActProxyVO = null;
		PamProxyVO pamProxyVO = null;
		Timestamp time = new Timestamp(new Date().getTime());

		MessageBuilderHelper messageBuilderHelper = new MessageBuilderHelper();
		int j = 0;
		PublicHealthCaseVO publicHealthCaseVO = new PublicHealthCaseVO();
		publicHealthCaseVO.getThePublicHealthCaseDT().setPublicHealthCaseUid(new Long(--j));
		try {
			ContainerDocument containerDoc = null;
			XMLTypeToNBSObject xmlObject = new XMLTypeToNBSObject();
			// publicHealthCaseVO.getThePublicHealthCaseDT().setProgAreaCd(nBSDocumentVO.getNbsDocumentDT().getProgAreaCd());
			publicHealthCaseVO.getThePublicHealthCaseDT().setJurisdictionCd(nBSDocumentVO.getNbsDocumentDT().getJurisdictionCd());

			if (nBSDocumentVO.getNbsDocumentDT().getPayloadViewIndCd() != null
					&& nBSDocumentVO.getNbsDocumentDT().getPayloadViewIndCd()
							.equals(NEDSSConstants.PAYLOAD_VIEW_IND_CD_PHDC)) {
				containerDoc = xmlObject.parseCaseTypeXml(nBSDocumentVO
						.getNbsDocumentDT().getPayLoadTxt());
			} else if (nBSDocumentVO.getNbsDocumentDT().getPayloadViewIndCd() != null
					&& !nBSDocumentVO.getNbsDocumentDT().getPayloadViewIndCd()
							.equals(NEDSSConstants.PAYLOAD_VIEW_IND_CD_PHDC)) {
				if (nBSDocumentVO.getNbsDocumentDT().getPhdcDocDerivedTxt() != null)
					containerDoc = xmlObject.parseCaseTypeXml(nBSDocumentVO
							.getNbsDocumentDT().getPhdcDocDerivedTxt());

			}

			publicHealthCaseVO.getThePublicHealthCaseDT().setAddTime(
					nBSDocumentVO.getNbsDocumentDT().getAddTime());
			publicHealthCaseVO.getThePublicHealthCaseDT().setLastChgTime(
					nBSDocumentVO.getNbsDocumentDT().getAddTime());
			publicHealthCaseVO.getThePublicHealthCaseDT().setAddUserId(
					nBSDocumentVO.getNbsDocumentDT().getAddUserId());
			publicHealthCaseVO.getThePublicHealthCaseDT().setLastChgUserId(
					nBSDocumentVO.getNbsDocumentDT().getAddUserId());
			publicHealthCaseVO.getThePublicHealthCaseDT().setRecordStatusTime(
					nBSDocumentVO.getNbsDocumentDT().getAddTime());
			publicHealthCaseVO.getThePublicHealthCaseDT().setSharedInd(
					NEDSSConstants.TRUE);
			publicHealthCaseVO.setItNew(true);
			publicHealthCaseVO.setItDirty(false);
			publicHealthCaseVO.getThePublicHealthCaseDT().setItNew(true);
			publicHealthCaseVO.getThePublicHealthCaseDT().setItDirty(false);
			ProgramAreaVO programAreaVO = CachedDropDowns
					.getProgramAreaForCondition(conditionCode);
			Container container = null;
			if (containerDoc != null)
				container = containerDoc.getContainer();
			HeaderType headerType = container.getHeader();
			CaseType caseType = null;
			if (container != null)
				caseType = container.getCase();
			if(conditionCode==null)
				conditionCode = caseType.getCondition().getCode();
			if (programAreaVO.getInvestigationFormCd()!=null &&  (programAreaVO.getInvestigationFormCd().equals(NEDSSConstants.INV_FORM_RVCT)
					|| programAreaVO.getInvestigationFormCd().equals(NEDSSConstants.INV_FORM_VAR))) {
				pamProxyVO = new PamProxyVO();
			} else {
				pageActProxyVO = new PageActProxyVO();
			}

			if(headerType.getSendingFacility()!=null){
			HierarchicalDesignationType hierarchicalDesignationType  = headerType.getSendingApplication();
			ExportReceivingFacilityDT exportReceivingFacilityDT  = new ExportReceivingFacilityDT();
			exportReceivingFacilityDT.setReceivingSystemDescTxt(hierarchicalDesignationType.getNamespaceID());
			exportReceivingFacilityDT.setReceivingSystemOid(hierarchicalDesignationType.getUniversalID());
			exportReceivingFacilityDT.setReceivingIndCd(hierarchicalDesignationType.getUniversalIDType());
			if(pageActProxyVO!=null)
				pageActProxyVO.setExportReceivingFacilityDT(exportReceivingFacilityDT);
			else
				pamProxyVO.setExportReceivingFacilityDT(exportReceivingFacilityDT);
			}
			TreeMap<Object, Object> condAndFormCdTreeMap = CachedDropDowns
					.getConditionCdAndInvFormCd();
			if (condAndFormCdTreeMap.get(conditionCode) == null) {
				throw new NEDSSAppException(
						"The Condition code mapping to Investigation does not exist: Please check the existence of "
								+ "condition code :"
								+ conditionCode
								+ ": in the nbs_srte..condition_code.");
			} else {
				publicHealthCaseVO.getThePublicHealthCaseDT().setCd(
						conditionCode);
				publicHealthCaseVO.getThePublicHealthCaseDT()
						.setCaseTypeCd("I");

				if (!(nBSDocumentVO.getNbsDocumentDT().getProgAreaCd() == null)
						&& !(nBSDocumentVO.getNbsDocumentDT()
								.getJurisdictionCd() == null)) {
					String progAreaCd = nBSDocumentVO.getNbsDocumentDT()
							.getProgAreaCd();
					publicHealthCaseVO.getThePublicHealthCaseDT()
							.setProgAreaCd(progAreaCd);
					String jurisdictionCd = nBSDocumentVO.getNbsDocumentDT()
							.getJurisdictionCd();
					long pajHash = ProgramAreaJurisdictionUtil.getPAJHash(
							progAreaCd, jurisdictionCd);
					Long aProgramJurisdictionOid = new Long(pajHash);
					publicHealthCaseVO.getThePublicHealthCaseDT()
							.setProgramJurisdictionOid(aProgramJurisdictionOid);
					publicHealthCaseVO.getThePublicHealthCaseDT()
							.setJurisdictionCd(jurisdictionCd);
				}
				publicHealthCaseVO.getThePublicHealthCaseDT().setCdDescTxt(
						programAreaVO.getConditionShortNm());

			}
			Map<Object, Object> questionIdentifierMap = loadQuestions(conditionCode);
			CommonQuestionsType commonQuestionsType = null;
			if (pamProxyVO != null) {
				pamProxyVO.setPublicHealthCaseVO(publicHealthCaseVO);
			} else
				pageActProxyVO.setPublicHealthCaseVO(publicHealthCaseVO);
			if (caseType.getParticipants() != null
					&& ((caseType.getParticipants().sizeOfOrganizationArray() > 0)
					|| (caseType.getParticipants().sizeOfProviderArray() > 0))) {
				EdxPersonEntityProcessor.setParticipant(
						caseType.getParticipants(), headerType, null,
						pageActProxyVO, pamProxyVO, nbsSecurityObj);
			}
			if (caseType != null)
				commonQuestionsType = caseType.getCommonQuestions();
			if (commonQuestionsType != null) {
				if (commonQuestionsType.getReportingInformation() != null) {
					ReportingInformationType reportingInformationType = commonQuestionsType
							.getReportingInformation();
					if (reportingInformationType.getDateOfReport() != null)
						publicHealthCaseVO.getThePublicHealthCaseDT()
								.setRptFormCmpltTime(
										new Timestamp(reportingInformationType
												.getDateOfReport()
												.getTimeInMillis()));
				}
				ClinicalInformationType clinicalInformationType = commonQuestionsType
						.getClinicalInformation();
				if (clinicalInformationType != null) {
					publicHealthCaseVO
							.getThePublicHealthCaseDT()
							.setHospitalizedIndCd(
									getNbsCode(
											"INV128",
											clinicalInformationType
													.getWasThePatientHospitalized(),
											questionIdentifierMap));
					if (clinicalInformationType.getAdmissionDate() != null) {
						publicHealthCaseVO.getThePublicHealthCaseDT()
								.setHospitalizedAdminTime(
										new Timestamp(clinicalInformationType
												.getAdmissionDate()
												.getTimeInMillis()));
					}
					if (clinicalInformationType.getDischargeDate() != null) {
						publicHealthCaseVO.getThePublicHealthCaseDT()
								.setHospitalizedDischargeTime(
										new Timestamp(clinicalInformationType
												.getDischargeDate()
												.getTimeInMillis()));
					}
					if (clinicalInformationType.getDurationOfStay() != null) {
						publicHealthCaseVO.getThePublicHealthCaseDT()
								.setHospitalizedDurationAmt(
										new BigDecimal(clinicalInformationType
												.getDurationOfStay()));
					}
					if (clinicalInformationType.getDiagnosisDate() != null)
						publicHealthCaseVO.getThePublicHealthCaseDT()
								.setDiagnosisTime(
										new Timestamp(clinicalInformationType
												.getDiagnosisDate()
												.getTimeInMillis()));
					if (clinicalInformationType.getIllnessOnsetDate() != null)
						publicHealthCaseVO.getThePublicHealthCaseDT()
								.setEffectiveFromTime(
										new Timestamp(clinicalInformationType
												.getIllnessOnsetDate()
												.getTimeInMillis()));
					if (clinicalInformationType.getIllnessEndDate() != null)
						publicHealthCaseVO.getThePublicHealthCaseDT()
								.setEffectiveToTime(
										new Timestamp(clinicalInformationType
												.getIllnessEndDate()
												.getTimeInMillis()));
					if (clinicalInformationType.getIllnessDuration() != null
							&& clinicalInformationType.getIllnessDuration()
									.getValue1() > 0) {
						int duration = (int) clinicalInformationType
								.getIllnessDuration().getValue1();
						publicHealthCaseVO.getThePublicHealthCaseDT()
								.setEffectiveDurationAmt("" + duration);
					}
					if (clinicalInformationType.getIllnessDuration() != null
							&& clinicalInformationType.getIllnessDuration()
									.getUnit() != null) {
						try {
							SRTGenericCodeDT srtIllnessUnitDT = messageBuilderHelper
									.reversePHINToNBSCodeTranslation(
											"PHVS_AGE_UNIT",
											clinicalInformationType
													.getIllnessDuration()
													.getUnit().getCode(),
											"Standard_XREF");
							if (srtIllnessUnitDT != null) {
								publicHealthCaseVO.getThePublicHealthCaseDT()
										.setEffectiveDurationUnitCd(
												srtIllnessUnitDT.getCode());
							}
						} catch (Exception e) {
							logger.info("Code cannot be translated for country: please check  to_code_set_nm COUNTRY_ISO for the message(for Country_XREF table) ");
						}

					}
					if (clinicalInformationType
							.getDidThePatientDieFromIllness() != null)
						publicHealthCaseVO
								.getThePublicHealthCaseDT()
								.setOutcomeCd(
										getNbsCode(
												"INV145",
												clinicalInformationType
														.getDidThePatientDieFromIllness(),
												questionIdentifierMap));
					if (clinicalInformationType.getDateOfDeath() != null) {
						publicHealthCaseVO.getThePublicHealthCaseDT()
								.setDeceasedTime(
										new Timestamp(clinicalInformationType
												.getDateOfDeath()
												.getTimeInMillis()));
					}
					if (clinicalInformationType.getPregnancyStatus() != null)
						publicHealthCaseVO.getThePublicHealthCaseDT()
								.setPregnantIndCd(
										getNbsCode("INV178",
												clinicalInformationType
														.getPregnancyStatus(),
												questionIdentifierMap));
					if (clinicalInformationType.getAgeAtIllnessOnset() != null
							&& clinicalInformationType.getAgeAtIllnessOnset()
									.getValue1() > -1) {
						int age = (int) clinicalInformationType
								.getAgeAtIllnessOnset().getValue1();
						publicHealthCaseVO.getThePublicHealthCaseDT()
								.setPatAgeAtOnset("" + age);
					}
					if (clinicalInformationType.getAgeAtIllnessOnset() != null
							&& clinicalInformationType.getAgeAtIllnessOnset()
									.getUnit() != null) {
						try {
							SRTGenericCodeDT srtIllnessUnitDT = messageBuilderHelper
									.reversePHINToNBSCodeTranslation(
											"PHVS_AGE_UNIT",
											clinicalInformationType
													.getAgeAtIllnessOnset()
													.getUnit().getCode(),
											"Standard_XREF");
							if (srtIllnessUnitDT != null) {
								publicHealthCaseVO.getThePublicHealthCaseDT()
										.setPatAgeAtOnsetUnitCd(
												srtIllnessUnitDT.getCode());
							}
						} catch (Exception e) {
							logger.error("Code cannot be translated for Age at Onset Unit: please check  to_code_set_nm PHVS_AGE_UNIT for the message(for Standard_XREF table) ");
						}
					}
					if (clinicalInformationType.getHospital() != null) {
						OrganizationParticipantType organizationParticipantType = clinicalInformationType
								.getHospital();
						EdxPersonEntityProcessor
								.setOrganizationParticipationType(
										organizationParticipantType,
										headerType, NEDSSConstants.HospOfADT,
										pageActProxyVO, pamProxyVO,
										nbsSecurityObj);
					}
					if (clinicalInformationType.getPhysician() != null) {
						ProviderParticipantType providerParticipantType = clinicalInformationType
								.getPhysician();
						EdxPersonEntityProcessor.setProviderParticipationType(
								providerParticipantType, headerType,
								NEDSSConstants.PHC_PHYSICIAN, pageActProxyVO,
								pamProxyVO, nbsSecurityObj);
					}

				}
				if (commonQuestionsType.getEpidemiologicInformation() != null) {
					EpidemiologicInformationType epidemiologicInformationType = commonQuestionsType
							.getEpidemiologicInformation();

					if (epidemiologicInformationType.getDetectionMethod() != null
							&& epidemiologicInformationType
									.getDetectionMethod().getCode() != null) {
						publicHealthCaseVO.getThePublicHealthCaseDT()
								.setDetectionMethodCd(
										getNbsCode("INV159",
												epidemiologicInformationType
														.getDetectionMethod(),
												questionIdentifierMap));
					}

					if (epidemiologicInformationType
							.getIsAssociatedWithDayCare() != null)
						publicHealthCaseVO
								.getThePublicHealthCaseDT()
								.setDayCareIndCd(
										getNbsCode(
												"INV148",
												epidemiologicInformationType
														.getIsAssociatedWithDayCare(),
												questionIdentifierMap));
					if (epidemiologicInformationType.getIsFoodHandler() != null)
						publicHealthCaseVO.getThePublicHealthCaseDT()
								.setFoodHandlerIndCd(
										getNbsCode("INV149",
												epidemiologicInformationType
														.getIsFoodHandler(),
												questionIdentifierMap));

					if (epidemiologicInformationType
							.getIsThisCasePartOfAnOutbreak() != null)
						publicHealthCaseVO
								.getThePublicHealthCaseDT()
								.setOutbreakInd(
										getNbsCode(
												"INV150",
												epidemiologicInformationType
														.getIsThisCasePartOfAnOutbreak(),
												questionIdentifierMap));
					if (epidemiologicInformationType
							.getIsThisCasePartOfAnOutbreak() != null
							&& epidemiologicInformationType.getOutbreakName() != null)
						publicHealthCaseVO.getThePublicHealthCaseDT()
								.setOutbreakName(
										getNbsCode("INV151",
												epidemiologicInformationType
														.getOutbreakName(),
												questionIdentifierMap));

					if (epidemiologicInformationType
							.getWhereWasTheDiseaseAcquired() != null)
						publicHealthCaseVO
								.getThePublicHealthCaseDT()
								.setDiseaseImportedCd(
										getNbsCode(
												"INV152",
												epidemiologicInformationType
														.getWhereWasTheDiseaseAcquired(),
												questionIdentifierMap));
					if (epidemiologicInformationType.getImportedCountry() != null
							&& epidemiologicInformationType
									.getImportedCountry().getCode() != null) {
						try {
							SRTGenericCodeDT srtImportedCountryCodeDT = messageBuilderHelper
									.reversePHINToNBSCodeTranslation(
											"COUNTRY_ISO",
											epidemiologicInformationType
													.getImportedCountry()
													.getCode(), "Country_XREF");
							if (srtImportedCountryCodeDT != null)
								publicHealthCaseVO.getThePublicHealthCaseDT()
										.setImportedCountryCd(
												srtImportedCountryCodeDT
														.getCode());
						} catch (Exception e) {
							logger.error("Code cannot be translated for country: please check  to_code_set_nm COUNTRY_ISO for the message(for Country_XREF table) ");
						}

					}
					if (epidemiologicInformationType.getImportedState() != null)
						publicHealthCaseVO.getThePublicHealthCaseDT()
								.setImportedStateCd(
										getNbsCode("INV154",
												epidemiologicInformationType
														.getImportedState(),
												questionIdentifierMap));
					if (epidemiologicInformationType.getImportedCity() != null)
						publicHealthCaseVO.getThePublicHealthCaseDT()
								.setImportedCityDescTxt(
										epidemiologicInformationType
												.getImportedCity());
					if (epidemiologicInformationType.getImportedCounty() != null)
						publicHealthCaseVO.getThePublicHealthCaseDT()
								.setImportedCountyCd(
										getNbsCode("INV156",
												epidemiologicInformationType
														.getImportedCounty(),
												questionIdentifierMap));
					if (epidemiologicInformationType.getTransmissionMode() != null
							&& epidemiologicInformationType
									.getTransmissionMode().getCode() != null)
						publicHealthCaseVO.getThePublicHealthCaseDT()
								.setTransmissionModeCd(
										getNbsCode("INV157",
												epidemiologicInformationType
														.getTransmissionMode(),
												questionIdentifierMap));
					// special handling required
					Calendar confirmationDate = null;
					if (epidemiologicInformationType.getConfirmationDate() != null)
						confirmationDate = epidemiologicInformationType
								.getConfirmationDate();
					ArrayList<Object> confirmationlist = new ArrayList<Object>();
					CodedType[] confirmationMethod = epidemiologicInformationType
							.getConfirmationMethodArray();
					for (int i = 0; i < confirmationMethod.length; i++) {
						ConfirmationMethodDT confirmationMethodDT = new ConfirmationMethodDT();
						if (confirmationMethod[i] != null) {
							CodedType confirmationMethodCoded = confirmationMethod[i];
							String confirmationMethodCode=getNbsCode(
									"INV161", confirmationMethodCoded,
									questionIdentifierMap);
							if(confirmationMethodCode == null ||  confirmationMethodCode.trim().equals("")){
								confirmationMethodCode="NA";
							}
							confirmationMethodDT
									.setConfirmationMethodCd(confirmationMethodCode);
							if (confirmationDate != null)
								confirmationMethodDT
										.setConfirmationMethodTime(new Timestamp(
												confirmationDate
														.getTimeInMillis()));
							confirmationlist.add(confirmationMethodDT);
						}
					}
					if (confirmationlist.size() == 0
							&& confirmationDate != null) {
						ConfirmationMethodDT confirmationMethodDT = new ConfirmationMethodDT();
						confirmationMethodDT.setConfirmationMethodCd("NA");
						confirmationMethodDT
								.setConfirmationMethodTime(new Timestamp(
										confirmationDate.getTimeInMillis()));
						confirmationlist.add(confirmationMethodDT);
					}
					publicHealthCaseVO
							.setTheConfirmationMethodDTCollection(confirmationlist);
					// getObsDateDT(observationCollection,epidemiologicInformationType.getConfirmationDate());

					if (epidemiologicInformationType.getCaseStatus() != null
							&& epidemiologicInformationType.getCaseStatus()
									.getCode() != null) {
						try {
							SRTGenericCodeDT srtCaseStatusCode = messageBuilderHelper
									.reversePHINToNBSCodeTranslation(
											"PHVS_PHC_CLASS",
											epidemiologicInformationType
													.getCaseStatus().getCode(),
											"Standard_XREF");
							if (srtCaseStatusCode != null) {
								publicHealthCaseVO.getThePublicHealthCaseDT()
										.setCaseClassCd(
												srtCaseStatusCode.getCode());
							}
						} catch (Exception e) {
							logger.error("Code cannot be translated for case status: check  to_code_set_nm PHVS_PHC_CLASS (for Standard_XREF table) ");
						}
					}
					Calendar now = Calendar.getInstance();
					if (epidemiologicInformationType.getMMWRWeek() != null)
						publicHealthCaseVO.getThePublicHealthCaseDT()
								.setMmwrWeek(
										""
												+ epidemiologicInformationType
														.getMMWRWeek());
					else
						publicHealthCaseVO.getThePublicHealthCaseDT()
								.setMmwrYear(
										"" + now.get(Calendar.WEEK_OF_YEAR));

					if (epidemiologicInformationType.getMMWRYear() != null)
						publicHealthCaseVO.getThePublicHealthCaseDT()
								.setMmwrYear(
										""
												+ epidemiologicInformationType
														.getMMWRYear());
					else
						publicHealthCaseVO.getThePublicHealthCaseDT()
								.setMmwrYear("" + now.get(Calendar.YEAR));

					publicHealthCaseVO.getThePublicHealthCaseDT().setTxt(
							epidemiologicInformationType.getGeneralComments());
				}

				if (commonQuestionsType.getInvestigationInformation() != null) {
					InvestigationInformationType investigationInformationType = commonQuestionsType
							.getInvestigationInformation();
					if (investigationInformationType != null) {
						if (investigationInformationType
								.getInvestigationStatus() != null){
							String code = getNbsCode(
                                    "INV109",investigationInformationType.getInvestigationStatus(),questionIdentifierMap);
                        if (code == null || code.trim().equals("")){
                              logger.warn("The Investigation status cd "+investigationInformationType.getInvestigationStatus().getCode()+" does not exists in SRTE; defaulting to 'O'");
                              publicHealthCaseVO.getThePublicHealthCaseDT().setInvestigationStatusCd(NEDSSConstants.STATUS_OPEN);
                        }
                        else
                        publicHealthCaseVO
                                    .getThePublicHealthCaseDT()
                                    .setInvestigationStatusCd(code);

						}else{
							publicHealthCaseVO.getThePublicHealthCaseDT()
							.setInvestigationStatusCd(NEDSSConstants.STATUS_OPEN);
						}
						if (investigationInformationType
								.getDateAssignedToInvestigation() != null)
							publicHealthCaseVO
									.getThePublicHealthCaseDT()
									.setInvestigatorAssignedTime(
											new Timestamp(
													investigationInformationType
															.getDateAssignedToInvestigation()
															.getTimeInMillis()));
						if (investigationInformationType
								.getInvestigationStartDate() != null){
							publicHealthCaseVO
									.getThePublicHealthCaseDT()
									.setActivityFromTime(
											new Timestamp(
													investigationInformationType
															.getInvestigationStartDate()
															.getTimeInMillis()));
						}else{
							publicHealthCaseVO
							.getThePublicHealthCaseDT().setActivityFromTime(time);
						}
						Collection<Object> theActIdDTCollection = new ArrayList<Object>();
						int k=1;
							ActIdDT actIDDT = new ActIdDT();
							actIDDT.setItNew(true);
							actIDDT.setActIdSeq(new Integer(1));
							actIDDT.setTypeCd(NEDSSConstants.ACT_ID_STATE_TYPE_CD);
							actIDDT.setTypeCd("STATE");
							if (investigationInformationType.getStateCaseID() != null) {
									actIDDT.setRootExtensionTxt(investigationInformationType
									.getStateCaseID());
							}
							theActIdDTCollection.add(actIDDT);
							publicHealthCaseVO.setTheActIdDTCollection(theActIdDTCollection);
						if(publicHealthCaseVO.getThePublicHealthCaseDT().getCd().equalsIgnoreCase("10220")){
							ActIdDT actID1DT = new ActIdDT();
							actID1DT.setItNew(true);
							actID1DT.setActIdSeq(new Integer(2));
							actID1DT.setTypeCd("CITY");
							if (investigationInformationType.getCityCountyCaseID()!= null) {
								actID1DT.setRootExtensionTxt(investigationInformationType.getCityCountyCaseID());
							}
							theActIdDTCollection.add(actID1DT);
							publicHealthCaseVO
									.setTheActIdDTCollection(theActIdDTCollection);
						}
						if (investigationInformationType.getInvestigator() != null) {
							ProviderParticipantType providerParticipantType = investigationInformationType
									.getInvestigator();
							EdxPersonEntityProcessor
									.setProviderParticipationType(
											providerParticipantType,
											headerType,
											NEDSSConstants.PHC_INVESTIGATOR,
											pageActProxyVO, pamProxyVO,
											nbsSecurityObj);
						}
					}
				}else{
					publicHealthCaseVO.getThePublicHealthCaseDT().setInvestigationStatusCd(NEDSSConstants.STATUS_OPEN);
					publicHealthCaseVO.getThePublicHealthCaseDT().setActivityFromTime(time);
					Collection<Object> theActIdDTCollection = new ArrayList<Object>();
					ActIdDT actIDDT = new ActIdDT();
					actIDDT.setItNew(true);
					actIDDT.setTypeCd("STATE");
					actIDDT.setActIdSeq(new Integer(1));
					actIDDT.setTypeCd(NEDSSConstants.ACT_ID_STATE_TYPE_CD);

					theActIdDTCollection.add(actIDDT);
					publicHealthCaseVO
							.setTheActIdDTCollection(theActIdDTCollection);
					if(publicHealthCaseVO.getThePublicHealthCaseDT().getCd().equalsIgnoreCase("10220")){
						ActIdDT actID1DT = new ActIdDT();
						actID1DT.setItNew(true);
						actID1DT.setActIdSeq(new Integer(2));
						actID1DT.setTypeCd("CITY");
						theActIdDTCollection.add(actID1DT);
						publicHealthCaseVO
								.setTheActIdDTCollection(theActIdDTCollection);
					}

				}
				if (commonQuestionsType.getReportingInformation() != null) {

					ReportingInformationType reportingInformationType = commonQuestionsType
							.getReportingInformation();
					if (reportingInformationType.getReportingSourceType() != null) {
						publicHealthCaseVO
								.getThePublicHealthCaseDT()
								.setRptSourceCd(
										getNbsCode(
												"INV112",
												reportingInformationType
														.getReportingSourceType(),
												questionIdentifierMap));
					}

					if (reportingInformationType
							.getEarliestDateReportedToCounty() != null)
						publicHealthCaseVO
								.getThePublicHealthCaseDT()
								.setRptToCountyTime(
										new Timestamp(
												reportingInformationType
														.getEarliestDateReportedToCounty()
														.getTimeInMillis()));
					if (reportingInformationType
							.getEarliestDateReportedToState() != null)
						publicHealthCaseVO
								.getThePublicHealthCaseDT()
								.setRptToStateTime(
										new Timestamp(
												reportingInformationType
														.getEarliestDateReportedToState()
														.getTimeInMillis()));

					if (reportingInformationType.getReportingOrganization() != null) {
						OrganizationParticipantType organizationParticipantType = reportingInformationType
								.getReportingOrganization();
						EdxPersonEntityProcessor
								.setOrganizationParticipationType(
										organizationParticipantType,
										headerType,
										NEDSSConstants.PHC_REPORTING_SOURCE,
										pageActProxyVO, pamProxyVO,
										nbsSecurityObj);
					}
					if (reportingInformationType.getReportingProvider() != null) {
						ProviderParticipantType providerParticipantType = reportingInformationType
								.getReportingProvider();
						EdxPersonEntityProcessor.setProviderParticipationType(
								providerParticipantType, headerType,
								NEDSSConstants.PHC_REPORTER, pageActProxyVO,
								pamProxyVO, nbsSecurityObj);
					}
				}

				publicHealthCaseVO.getThePublicHealthCaseDT().setSharedInd(
						NEDSSConstants.TRUE);
				publicHealthCaseVO.getThePublicHealthCaseDT().setGroupCaseCnt(
						new Integer(1));
				publicHealthCaseVO.getThePublicHealthCaseDT().setStatusCd(
						NEDSSConstants.STATUS_ACTIVE);
			}
			Map<Object, Object> nbsAnswerMap = null;
			if (caseType.getDiseaseSpecificQuestions() != null) {
				nbsAnswerMap = setCaseAnswerDTCollection(
						caseType, questionIdentifierMap, publicHealthCaseVO);
				if (pamProxyVO != null)
					pamProxyVO.getPamVO().setPamAnswerDTMap(nbsAnswerMap);
				if (pageActProxyVO != null)
					pageActProxyVO.getPageVO().setPamAnswerDTMap(nbsAnswerMap);
			}
			if(questionIdentifierMap.get(_REQUIRED)!=null){
				@SuppressWarnings("unchecked")
				Map<Object, Object> requireMap =(Map<Object, Object>)questionIdentifierMap.get(_REQUIRED);
				String errorText = requiredFieldCheck(requireMap, nbsAnswerMap);
				publicHealthCaseVO.setErrorText(errorText);
			}
			PersonVO patientVO = EdxPersonEntityProcessor.setPatientVO(pageActProxyVO, pamProxyVO, caseType.getPatient(), publicHealthCaseVO.getThePublicHealthCaseDT().getAddTime(),true);
			patientVO.getThePersonDT().setPersonParentUid(nBSDocumentVO.getPatientVO().getThePersonDT().getPersonParentUid());

			if (pamProxyVO != null) {
				if (pamProxyVO.getThePersonVOCollection() == null
						|| (pamProxyVO.getThePersonVOCollection() != null && pamProxyVO
								.getThePersonVOCollection().size() == 0)) {
					Collection<Object> personColl = new ArrayList<Object>();
					personColl.add(patientVO);
					pamProxyVO.setThePersonVOCollection(personColl);
				} else {
					pamProxyVO.getThePersonVOCollection().add(patientVO);
				}
				pamProxyVO.setPublicHealthCaseVO(publicHealthCaseVO);
				pamProxyVO.setItNew(true);
				pamProxyVO.setItDirty(false);
				obj = pamProxyVO;
			}
			if (pageActProxyVO != null) {
				if (pageActProxyVO.getThePersonVOCollection() == null
						|| (pageActProxyVO.getThePersonVOCollection() != null && pageActProxyVO
								.getThePersonVOCollection().size() == 0)) {
					Collection<Object> personColl = new ArrayList<Object>();
					personColl.add(patientVO);
					pageActProxyVO.setThePersonVOCollection(personColl);
				} else {
					pageActProxyVO.getThePersonVOCollection().add(patientVO);
				}
				pageActProxyVO.setPublicHealthCaseVO(publicHealthCaseVO);
				pageActProxyVO.setItNew(true);
				pageActProxyVO.setItDirty(false);
				obj = pageActProxyVO;
			}
			createActRelationshipForDoc(nBSDocumentVO.getNbsDocumentDT()
					.getNbsDocumentUid(), publicHealthCaseVO);

			if (caseType.getNotes() != null
					&& caseType.getNotes().sizeOfNoteArray() > 0) {
				setNotes(pageActProxyVO, pamProxyVO, caseType);
			}

		} catch (NumberFormatException e) {
			logger.error("EdxPHCRDocumentUtil.getPublicHealthCaseVO.NumberFormatException :"
					+ e);
			throw new EJBException(
					"EdxPHCRDocumentUtil.getPublicHealthCaseVO.NumberFormatException :"
							+ e, e);
		} catch (NEDSSAppException e) {
			logger.error("EdxPHCRDocumentUtil.getPublicHealthCaseVO.NEDSSAppException :"
					+ e);
			if(e.getErrorCd() != null && e.getErrorCd().equals(EdxRuleConstants.EDX_FAIL_INV_ON_REQ_FIELDS))
			{
				throw e;
			}else
			{
				throw new EJBException(
						"EdxPHCRDocumentUtil.getPublicHealthCaseVO.NEDSSAppException :"
								+ e, e);
			}

		} catch (Exception e) {
			logger.error("EdxPHCRDocumentUtil.getPublicHealthCaseVO.Exception :"
					+ e);
			throw new EJBException(
					"EdxPHCRDocumentUtil.getPublicHealthCaseVO.Exception :" + e, e);
		}

		return obj;
	}

		private static void setNotes(PageActProxyVO pageActProxyVO,
			PamProxyVO pamProxyVO, CaseType caseType) {
		String[] notes = caseType.getNotes().getNoteArray();
		Collection<Object> noteList = new ArrayList<Object>();
		for (int num = 0; num < notes.length; num++) {
			// notes
			NBSNoteDT nbsNoteDT = new NBSNoteDT();
			nbsNoteDT.setItNew(true);
			nbsNoteDT.setNbsNoteUid(new Long(-1));
			nbsNoteDT.setNote(notes[num]);

			if (pamProxyVO != null) {
				nbsNoteDT.setNoteParentUid(pamProxyVO.getPublicHealthCaseVO()
						.getThePublicHealthCaseDT().getPublicHealthCaseUid());
				nbsNoteDT.setAddTime(pamProxyVO.getPublicHealthCaseVO()
						.getThePublicHealthCaseDT().getAddTime());
				nbsNoteDT.setAddUserId(Long.valueOf(pamProxyVO
						.getPublicHealthCaseVO().getThePublicHealthCaseDT()
						.getAddUserId()));
				nbsNoteDT.setRecordStatusTime(pamProxyVO
						.getPublicHealthCaseVO().getThePublicHealthCaseDT()
						.getRecordStatusTime());
				nbsNoteDT.setLastChgUserId(Long.valueOf(pamProxyVO
						.getPublicHealthCaseVO().getThePublicHealthCaseDT()
						.getLastChgUserId()));
				nbsNoteDT.setLastChgTime(pamProxyVO.getPublicHealthCaseVO()
						.getThePublicHealthCaseDT().getLastChgTime());
			}
			if (pageActProxyVO != null) {
				nbsNoteDT.setNoteParentUid(pageActProxyVO
						.getPublicHealthCaseVO().getThePublicHealthCaseDT()
						.getPublicHealthCaseUid());
				nbsNoteDT.setAddTime(pageActProxyVO.getPublicHealthCaseVO()
						.getThePublicHealthCaseDT().getAddTime());
				nbsNoteDT.setAddUserId(Long.valueOf(pageActProxyVO
						.getPublicHealthCaseVO().getThePublicHealthCaseDT()
						.getAddUserId()));
				nbsNoteDT.setRecordStatusTime(pageActProxyVO
						.getPublicHealthCaseVO().getThePublicHealthCaseDT()
						.getRecordStatusTime());
				nbsNoteDT.setLastChgUserId(Long.valueOf(pageActProxyVO
						.getPublicHealthCaseVO().getThePublicHealthCaseDT()
						.getLastChgUserId()));
				nbsNoteDT.setLastChgTime(pageActProxyVO.getPublicHealthCaseVO()
						.getThePublicHealthCaseDT().getLastChgTime());
			}
			nbsNoteDT.setRecordStatusCd(NEDSSConstants.ACTIVE);
			nbsNoteDT.setTypeCd(NEDSSConstants.CASE);
			nbsNoteDT.setPrivateIndCd(NEDSSConstants.FALSE);

			noteList.add(nbsNoteDT);
		}
		if (pamProxyVO != null) {
			pamProxyVO.setUnsavedNote(true);
			pamProxyVO.setNbsNoteDTColl(noteList);
		}
		if (pageActProxyVO != null) {
			pageActProxyVO.setUnsavedNote(true);
			pageActProxyVO.setNbsNoteDTColl(noteList);
		}
	}

	public NBSDocumentVO createNBSDocumentVO(String xmlPayLoadContent,
			Timestamp time, NBSSecurityObj nbsSecurityObj)
			throws NEDSSException {
		NBSDocumentVO nbsDocumentVO = new NBSDocumentVO();
		try {

			NBSDocumentDT nbsDocumentDT = new NBSDocumentDT();
			XMLTypeToNBSObject xmlObject = new XMLTypeToNBSObject();
			ContainerDocument containerDoc = xmlObject
					.parseCaseTypeXml(xmlPayLoadContent);
			Container container = containerDoc.getContainer();
			HeaderType headerType = container.getHeader();
			HierarchicalDesignationType sendingFacilityType = headerType
					.getSendingFacility();

			CaseType caseType = container.getCase();
			PersonVO patientVO = EdxPersonEntityProcessor.setPatientVO(null,
					null, caseType.getPatient(), time, true);
			String conditionCode = caseType.getCondition().getCode();
			ProgramAreaVO programAreaVO = CachedDropDowns
					.getProgramAreaForCondition(conditionCode);
			NedssUtils nedssUtils = new NedssUtils();
			Object lookUpJurisdiction = nedssUtils
					.lookupBean(JNDINames.JURISDICTION_EJB);

			String jurCd;

			JurisdictionHome jurHome = (JurisdictionHome) PortableRemoteObject
					.narrow(lookUpJurisdiction, JurisdictionHome.class);
			Jurisdiction jurisdiction = jurHome.create();
			Collection<Object> jurColl = jurisdiction
					.findJurisdictionForPatient(patientVO);
			jurCd = null;

			if (jurColl != null && jurColl.size() == 1) {

				for (Iterator<Object> i = jurColl.iterator(); i.hasNext();) {
					jurCd = (String) i.next();
				}
			}
			if (jurCd == null)
				throw new EJBException(
						"createNBSDocumentVO: The derived Jurisdiction code is null. Please check document");

			nbsDocumentDT.setPayLoadTxt(xmlPayLoadContent);
			nbsDocumentDT.setDocTypeCd(caseType.getSectionHeader()
					.getDocumentType().getCode());
			nbsDocumentDT
					.setRecordStatusCd(NEDSSConstants.NND_UNPROCESSED_MESSAGE);
			nbsDocumentDT.setStatusCd(headerType.getResultStatus().getCode());
			nbsDocumentDT.setRecordStatusTime(time);
			nbsDocumentDT.setAddUserId(new Long(nbsSecurityObj.getEntryID()));
			nbsDocumentDT.setAddTime(time);
			nbsDocumentDT.setProgAreaCd(programAreaVO.getStateProgAreaCode());
			nbsDocumentDT.setJurisdictionCd(jurCd);
			patientVO.getThePersonDT().setJurisdictionCd(jurCd);
			nbsDocumentDT.setTxt(caseType.getSectionHeader().getDescription());
			nbsDocumentDT.setSharedInd("T");
			if (nbsDocumentDT.getNbsDocumentMetadataUid() == null)
				nbsDocumentDT.setNbsDocumentMetadataUid(nbsDocumentDT
						.getNbsDocumentMetadataUid());
			nbsDocumentDT
					.setPayloadViewIndCd(NEDSSConstants.PAYLOAD_VIEW_IND_CD_PHDC);

			if (!(nbsDocumentDT.getProgAreaCd() == null)
					&& !(nbsDocumentDT.getJurisdictionCd() == null)) {
				String progAreaCd = nbsDocumentDT.getProgAreaCd();
				String jurisdictionCd = nbsDocumentDT.getJurisdictionCd();
				long pajHash = ProgramAreaJurisdictionUtil.getPAJHash(
						progAreaCd, jurisdictionCd);
				Long aProgramJurisdictionOid = new Long(pajHash);
				nbsDocumentDT
						.setProgramJurisdictionOid(aProgramJurisdictionOid);
			}

			nbsDocumentDT.setVersionCtrlNbr(new Integer(1));
			nbsDocumentDT.setCd(conditionCode);
			nbsDocumentDT.setLastChgTime(time);
			nbsDocumentDT
					.setLastChgUserId(new Long(nbsSecurityObj.getEntryID()));
			nbsDocumentDT.setDocPurposeCd(caseType.getSectionHeader()
					.getPurpose().getCode());

			if (caseType.getSectionHeader()
					.getSendingApplicationEventIdentifier() != null)
				nbsDocumentDT.setSendingAppEventId(caseType.getSectionHeader()
						.getSendingApplicationEventIdentifier());

			nbsDocumentDT
					.setDocStatusCd(headerType.getResultStatus().getCode());
			nbsDocumentDT
					.setCdDescTxt(caseType.getCondition().getCodeDescTxt());
			nbsDocumentDT.setSendingFacilityNm(sendingFacilityType
					.getNamespaceID());
			nbsDocumentVO.setNbsDocumentDT(nbsDocumentDT);
			nbsDocumentVO.setPatientVO(patientVO);
		} catch (ClassCastException e) {
			logger.error("NbsPHCRDocumentUtil createNBSDocumentVO ClassCastException:"
					+ e);
			throw new EJBException(
					"NbsPHCRDocumentUtil createNBSDocumentVO ClassCastException:"
							+ e, e);
		} catch (Exception e) {
			logger.error("NbsPHCRDocumentUtil createNBSDocumentVO Exception:"
					+ e);
			throw new EJBException(
					"NbsPHCRDocumentUtil createNBSDocumentVO Exception:" + e, e);
		}

		return nbsDocumentVO;
	}

	private static Map<Object, Object> setCaseAnswerDTCollection(
			CaseType caseType, Map<Object, Object> questionIdentifierMap,
			PublicHealthCaseVO publicHealthCaseVO) throws NEDSSAppException {
		ObservationType[] observationTypeArray = caseType
				.getDiseaseSpecificQuestions().getObservationArray();
		TreeMap<Object, Object> condAndFormCdTreeMap = CachedDropDowns
				.getConditionCdAndInvFormCd();
		
		String InvestigationFormCd = condAndFormCdTreeMap.get(publicHealthCaseVO.getThePublicHealthCaseDT().getCd()).toString();
		
		Map<Object, Object> nbsCaseAnswerMap = new HashMap<Object, Object>();
		for (int i = 0; i < observationTypeArray.length; i++) {
			if (observationTypeArray[i] != null) {
				ObservationType observationType = observationTypeArray[i];
				NbsCaseAnswerDT nbsCaseAnswerDT = new NbsCaseAnswerDT();
				AnswerType answerType = observationType.getAnswer();
				CodedType codedQuestion = observationType.getQuestion();

				CodedType[] answerTypeArray = observationType.getAnswer()
						.getAnswerCodeArray();
				if (answerTypeArray.length == 0 || answerTypeArray.length == 1) {
					nbsCaseAnswerDT.setSeqNbr(0);
					String code = codedQuestion.getCode();
					if (questionIdentifierMap.get(code) == null) {
						logger.warn("The question indentifier Map for code:"
								+ code
								+ ": is null. Plesase check as the imported XML document does contain form specific question!");
					} else {
						NbsQuestionMetadata metaData = (NbsQuestionMetadata) questionIdentifierMap
								.get(code);

						setStandardNBSCaseAnswerVals(publicHealthCaseVO,
								nbsCaseAnswerDT);
						if(metaData.getNbsUiComponentUid()!=null && metaData.getNbsUiComponentUid().toString().equalsIgnoreCase(NEDSSConstants.MULTISELECT_COMPONENT)){
							nbsCaseAnswerDT.setSeqNbr(1);
						}
						if (answerType.getAnswerDate() != null) {
							DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
							String formattedDate = formatter.format(answerType.getAnswerDate().getTime());
							nbsCaseAnswerDT.setAnswerTxt(formattedDate);
						} else if (answerType.getAnswerDateTime() != null) {

							 SimpleDateFormat formatter, FORMATTER;
							if(answerType.getAnswerDateTime().toString().length()>10){
								String newDateStr = answerType.getAnswerDateTime().toString().substring(0, 10);
								try {
									  Date formattedDate ;
									  formatter = new SimpleDateFormat("yyyy-MM-dd");
									  FORMATTER= new SimpleDateFormat("MM/dd/yyyy");
									  formattedDate = (Date)formatter.parse(newDateStr);
									  nbsCaseAnswerDT.setAnswerTxt(FORMATTER.format(formattedDate).toString()+"");
								}
								catch (ParseException e)
								{
									logger.error("ParsingException raised :"+e);
								}
							}else if(answerType.getAnswerDateTime().toString().length()==10){
								String newDateStr = answerType.getAnswerDateTime().toString();
								try {
									  Date formattedDate ;
									  formatter = new SimpleDateFormat("yyyy-MM-dd");
									  FORMATTER= new SimpleDateFormat("MM/dd/yyyy");
									  formattedDate = (Date)formatter.parse(newDateStr);
									  nbsCaseAnswerDT.setAnswerTxt(FORMATTER.format(formattedDate).toString()+"");
								}
								catch (ParseException e)
								{
									logger.error("ParsingException raised :"+e);
								}
							}else{
								logger.error("ParsingException raised The datetime format is not right for the question:"+codedQuestion.getCode());
							}

						} else if (answerType.getAnswerText() != null) {
							nbsCaseAnswerDT.setAnswerTxt(answerType
									.getAnswerText());
						} else if (answerType.getAnswerNumeric() != null) {
							Double answerDouble = new Double(answerType.getAnswerNumeric().getValue1()+"");
							Double intAnswer = Math.floor(answerDouble);
							if(answerDouble>intAnswer)
								nbsCaseAnswerDT.setAnswerTxt(answerDouble + "");
							else{
								nbsCaseAnswerDT.setAnswerTxt(intAnswer.intValue() + "");
							}
						} else if (answerType.getAnswerCodeArray() != null) {
							for (int j = 0; j < answerTypeArray.length; j++) {
								if (answerTypeArray.length > 1)
									nbsCaseAnswerDT.setSeqNbr(new Integer(++j));
								else
									if(metaData.getNbsUiComponentUid()!=null && metaData.getNbsUiComponentUid().toString().equalsIgnoreCase(NEDSSConstants.MULTISELECT_COMPONENT)){
										nbsCaseAnswerDT.setSeqNbr(1);
									}else
										nbsCaseAnswerDT.setSeqNbr(0);
								CodedType codedAnswer = answerTypeArray[j];
								if (codedAnswer.getCode() != null
										&& codedAnswer.getCode().equals("OTH")) {
									if (InvestigationFormCd!=null && (InvestigationFormCd.equalsIgnoreCase(NEDSSConstants.INV_FORM_VAR)
											|| InvestigationFormCd.equalsIgnoreCase(NEDSSConstants.INV_FORM_RVCT))) {
										if(codedAnswer.getText()==null)
											nbsCaseAnswerDT.setAnswerTxt(codedAnswer.getCode());
										else
											nbsCaseAnswerDT.setAnswerTxt(codedAnswer.getText());
									}else{
									nbsCaseAnswerDT.setAnswerTxt(codedAnswer
											.getCode()
											+ "^"
											+ codedAnswer.getText());
									}
								} else
									nbsCaseAnswerDT
											.setAnswerTxt(getNbsCode(
													codedQuestion.getCode(),
													codedAnswer,
													questionIdentifierMap));
							}
						}
						if (answerType.getAnswerSequence() != null) {
							nbsCaseAnswerDT.setAnswerGroupSeqNbr(answerType
									.getAnswerSequence().intValue());
						}
						if (metaData.getUnitTypeCd() != null
								&& metaData.getUnitTypeCd().equalsIgnoreCase(
										"CODED")) {
							nbsCaseAnswerDT.setAnswerTxt(answerType
									.getAnswerNumeric().getValue1()
									+ "^"
									+ getNbsCode(codedQuestion.getCode(),
											answerType.getAnswerNumeric()
													.getUnit(),
											questionIdentifierMap));
						}
						if (questionIdentifierMap.get(code) == null) {
							logger.warn("The question indentifier Map for code:"
									+ code
									+ ": is null. Plesase check as the imported XML document does contain form specific question!");
						} else {
							setStandardNBSCaseAnswerVals(publicHealthCaseVO,
									nbsCaseAnswerDT);
							nbsCaseAnswerDT.setNbsQuestionUid(metaData
									.getNbsQuestionUid());
							nbsCaseAnswerDT
									.setNbsQuestionVersionCtrlNbr(metaData
											.getQuestionVersionNbr());
							if (metaData.getNbsUiComponentUid()
									.compareTo(1013L) == 0) {
								if (nbsCaseAnswerMap.get(metaData
										.getQuestionIdentifier()) == null) {
									Collection<Object> coll = new ArrayList<Object>();
									coll.add(nbsCaseAnswerDT);
									nbsCaseAnswerMap.put(
											metaData.getQuestionIdentifier(),
											coll);
								}
							} else {
								nbsCaseAnswerMap.put(
										metaData.getQuestionIdentifier(),
										nbsCaseAnswerDT);
							}
						}
					}
				} else {
					for (int j = 0; j < (answerTypeArray.length); j++) {
						nbsCaseAnswerDT = new NbsCaseAnswerDT();
						nbsCaseAnswerDT.setSeqNbr(new Integer(j + 1));
						CodedType codedAnswer = answerTypeArray[j];

						if (codedAnswer.getCode() != null
								&& codedAnswer.getCode().equals("OTH")) {
							if (InvestigationFormCd!=null && (InvestigationFormCd.equalsIgnoreCase(NEDSSConstants.INV_FORM_VAR)
									|| InvestigationFormCd.equalsIgnoreCase(NEDSSConstants.INV_FORM_RVCT))) {
								if(codedAnswer.getText()==null)
									nbsCaseAnswerDT.setAnswerTxt(codedAnswer.getCode());
								else
									nbsCaseAnswerDT.setAnswerTxt(codedAnswer.getText());

							}else{
								nbsCaseAnswerDT.setAnswerTxt(getNbsCode(
									codedQuestion.getCode(), codedAnswer,
									questionIdentifierMap)
									+ "^" + codedAnswer.getText());
							}
						} else
							nbsCaseAnswerDT.setAnswerTxt(getNbsCode(
									codedQuestion.getCode(), codedAnswer,
									questionIdentifierMap));

						String code = codedQuestion.getCode();
						if (questionIdentifierMap.get(code) == null) {
							logger.warn("The question indentifier Map for code:"
									+ code
									+ ": is null. Plesase check as the imported XML document does contain form specific question!");
						} else {
							setStandardNBSCaseAnswerVals(publicHealthCaseVO,
									nbsCaseAnswerDT);
							NbsQuestionMetadata metaData = (NbsQuestionMetadata) questionIdentifierMap
									.get(code);
							nbsCaseAnswerDT.setNbsQuestionUid(metaData
									.getNbsQuestionUid());
							nbsCaseAnswerDT
									.setNbsQuestionVersionCtrlNbr(metaData
											.getQuestionVersionNbr());

							if (nbsCaseAnswerMap.get(metaData
									.getQuestionIdentifier()) == null
									&& metaData.getNbsUiComponentUid()
											.compareTo(1013L) == 0) {
								Collection<Object> coll = new ArrayList<Object>();
								coll.add(nbsCaseAnswerDT);
								nbsCaseAnswerMap.put(
										metaData.getQuestionIdentifier(), coll);
							} else if (nbsCaseAnswerMap.get(metaData
									.getQuestionIdentifier()) != null
									&& metaData.getNbsUiComponentUid()
											.compareTo(1013L) == 0) {
								Collection<Object> coll = (ArrayList<Object>) nbsCaseAnswerMap
										.get(metaData.getQuestionIdentifier());
								coll.add(nbsCaseAnswerDT);
								nbsCaseAnswerMap.put(
										metaData.getQuestionIdentifier(), coll);
							} else {
								nbsCaseAnswerMap.put(
										metaData.getQuestionIdentifier(),
										nbsCaseAnswerDT);
							}
						}
					}
				}
			}
		}


		return nbsCaseAnswerMap;
	}

	public static String requiredFieldCheck(Map<Object, Object> requiredQuestionIdentifierMap, Map<Object, Object> nbsCaseAnswerMap) throws NEDSSAppException{
		//
		String requireFieldError = null;
		Iterator<Object> iter = (requiredQuestionIdentifierMap.keySet()).iterator();
		Collection<Object> errorTextColl = new ArrayList<Object>();
		try {
			while(iter.hasNext()){
				String reqdKey = (String) iter.next();
				logger.error(reqdKey);
				if (reqdKey!=null) {
					if (nbsCaseAnswerMap==null || nbsCaseAnswerMap.get(reqdKey) == null) {
						NbsQuestionMetadata metaData = (NbsQuestionMetadata) requiredQuestionIdentifierMap
								.get(reqdKey);
						if(metaData.getQuestionGroupSeqNbr()==null){
							errorTextColl.add("["+metaData.getQuestionLabel()+ "]");
							
						}

					}
				}
			}
		} catch (Exception e) {
				logger.error(e.getCause());
				logger.error("Exception raised"+ e);
				e.printStackTrace();
			}
		if(errorTextColl!=null && errorTextColl.size()>0){
			Iterator<Object> iterator = errorTextColl.iterator();
			String errorTextString ="";
			while(iterator.hasNext()){
				String errorText = (String)iterator.next();
				if(errorTextColl.size()==1){
					errorTextString =errorText;
				}else{
					if(iterator.hasNext()){
						errorTextString =errorTextString+ errorText+"; " ;
					}else{
						errorTextString =errorTextString+" and "+errorText+". ";
					}
				}
			}
			if(errorTextColl.size()==1){
				requireFieldError = "The following required field is missing: "+ errorTextString;
			}else if(errorTextColl.size()>1){
				requireFieldError = "The following required field(s) are missing: "+ errorTextString;
			}else
				requireFieldError =null;

		}
		return requireFieldError;
	}
	public static NbsCaseAnswerDT setStandardNBSCaseAnswerVals(
			PublicHealthCaseVO publicHealthCaseVO,
			NbsCaseAnswerDT nbsCaseAnswerDT) {

		nbsCaseAnswerDT.setActUid(publicHealthCaseVO.getThePublicHealthCaseDT()
				.getPublicHealthCaseUid());
		nbsCaseAnswerDT.setAddTime(publicHealthCaseVO
				.getThePublicHealthCaseDT().getAddTime());
		nbsCaseAnswerDT.setLastChgTime(publicHealthCaseVO
				.getThePublicHealthCaseDT().getLastChgTime());
		nbsCaseAnswerDT.setAddUserId(publicHealthCaseVO
				.getThePublicHealthCaseDT().getAddUserId());
		nbsCaseAnswerDT.setLastChgUserId(publicHealthCaseVO
				.getThePublicHealthCaseDT().getLastChgUserId());
		nbsCaseAnswerDT.setRecordStatusCd("OPEN");
		if (nbsCaseAnswerDT.getSeqNbr() != null
				&& nbsCaseAnswerDT.getSeqNbr().intValue() < 0)
			nbsCaseAnswerDT.setSeqNbr(new Integer(0));
		nbsCaseAnswerDT.setRecordStatusTime(publicHealthCaseVO
				.getThePublicHealthCaseDT().getRecordStatusTime());
		nbsCaseAnswerDT.setItNew(true);
		// if (nbsCaseAnswerDT.getNbsQuestionUid() == null) {
		// logger.error("There is no question identifier");
		// }
		return nbsCaseAnswerDT;
	}
	public static Map<Object, Object> loadQuestions(String conditionCode){
		TreeMap<Object, Object> condAndFormCdTreeMap = CachedDropDowns
				.getConditionCdAndInvFormCd();
		Map<Object, Object> questionMap;
		String invFormCd = condAndFormCdTreeMap.get(conditionCode)
				.toString();
		if(invFormCd==null || invFormCd.startsWith("INV_FORM"))
			invFormCd=DecisionSupportConstants.CORE_INV_FORM;
		PropertyUtil propertyUtil = PropertyUtil.getInstance();
		ArrayList<Object> questionList = new ArrayList<Object> ();
    	DSMAlgorithmDaoImpl dsmAlgoDao = new DSMAlgorithmDaoImpl();
    	Map<Object,Object> tempMap = new HashMap<Object,Object>();
    	Map<Object,Object> generalMap = new HashMap<Object,Object>();

    	//Check to see if it is single condition or multiple conditions
    	if(invFormCd != null){
    		
			if(invFormCd.equals(NBSConstantUtil.INV_FORM_RVCT)|| invFormCd.equals(NBSConstantUtil.INV_FORM_VAR)){
				if(QuestionsCache.getQuestionMap()!=null && QuestionsCache.getQuestionMap().containsKey(invFormCd))
    			{
    				tempMap = (Map<Object, Object> )QuestionsCache.getQuestionMap().get(invFormCd);
    			}
			}
    		else 
    		{
    			if(QuestionsCache.dmbMap.containsKey(invFormCd))
    				tempMap.putAll((Map<Object, Object> )QuestionsCache.dmbMap.get(invFormCd));
    			else if(!QuestionsCache.dmbMap.containsKey(invFormCd) && propertyUtil.getServerRestart()!=null && propertyUtil.getServerRestart().equals("F"))
    			{
    				Map<Object, Object> questions = (Map<Object, Object> )QuestionsCache.getDMBQuestionMapAfterPublish().get(invFormCd);
    				if(questions != null)
    					tempMap.putAll(questions);
    			}
    			else
    				tempMap = new HashMap<Object,Object>();
    		}
    		
	    	if(tempMap != null){
	    		Iterator<Object> tempIter = tempMap.keySet().iterator();    	
		    	while(tempIter.hasNext()) {
		    		String key = (String) tempIter.next();
		    		NbsQuestionMetadata metaData = (NbsQuestionMetadata) tempMap.get(key);
		    		if(generalMap.containsKey(key)) { //overwrite it 
		    			generalMap.remove(key);
		    			generalMap.put(key, metaData);
		    			continue;
		    		}
		    		else{
		    			generalMap.put(key, metaData);
		    		}
		    	}
		   }
    	}    	
    	
    	
    	return generalMap;

    }
	@SuppressWarnings("unchecked")
	public static Map<Object, Object> loadQuestionsLegacy(String conditionCode)
			throws Exception {
		TreeMap<Object, Object> condAndFormCdTreeMap = CachedDropDowns
				.getConditionCdAndInvFormCd();
		Map<Object, Object> questionMap;
		String InvestigationFormCd = condAndFormCdTreeMap.get(conditionCode)
				.toString();
		PropertyUtil propertyUtil = PropertyUtil.getInstance();

		if ((InvestigationFormCd!=null && (InvestigationFormCd.equalsIgnoreCase(NEDSSConstants.INV_FORM_VAR)
				|| InvestigationFormCd.equalsIgnoreCase(NEDSSConstants.INV_FORM_RVCT)))) {
			if (QuestionsCache.map.containsKey(InvestigationFormCd))
				questionMap = (Map<Object, Object>) QuestionsCache.map
						.get(InvestigationFormCd);
			else if (!QuestionsCache.dmbMap.containsKey(InvestigationFormCd)
					&& propertyUtil.getServerRestart() != null
					&& propertyUtil.getServerRestart().equals("F"))
				questionMap = (Map<Object, Object>) QuestionsCache
						.getQuestionMap().get(InvestigationFormCd);
			else
				questionMap = new HashMap<Object, Object>();
		} else {
			if (QuestionsCache.dmbMap.containsKey(InvestigationFormCd))
				questionMap = (Map<Object, Object>) QuestionsCache.dmbMap
						.get(InvestigationFormCd);
			else if (!QuestionsCache.dmbMap.containsKey(InvestigationFormCd)
					&& propertyUtil.getServerRestart() != null
					&& propertyUtil.getServerRestart().equals("F"))
				questionMap = (Map<Object, Object>) QuestionsCache
						.getDMBQuestionMap().get(InvestigationFormCd);
			else
				questionMap = new HashMap<Object, Object>();
		}

		Map<Object,Object> questionIdentifiers = loadQuestionKeys(questionMap,
				InvestigationFormCd);
		if (questionMap == null)
			throw new Exception("\n *************** Question Cache for "
					+ InvestigationFormCd
					+ " is empty!!! *************** \n");
		return questionIdentifiers;

	}

	public static Map<Object,Object> loadQuestionKeys(Map<Object,Object> questionMap, String invFormCd) {
		if (questionMap == null)
			return null;
		Map<Object, Object> questionIdentifierMap = new HashMap<Object, Object>();
		Map<Object, Object> requiredQuestionIdentifierMap = new HashMap<Object, Object>();

		try {
			Iterator<Object> iter = (questionMap.keySet()).iterator();
			while (iter.hasNext()) {
				String key = (String) iter.next();
				NbsQuestionMetadata metaData = (NbsQuestionMetadata) questionMap
						.get(key);
				try {
					questionIdentifierMap.put(metaData.getQuestionIdentifier(),
							metaData);
					if (metaData.getRequiredInd() != null
							&& metaData.getRequiredInd().equalsIgnoreCase("T")
							&& metaData.getDataLocation() != null
							&& metaData.getDataLocation().equalsIgnoreCase(
									"NBS_Case_Answer.answer_txt")) {
						requiredQuestionIdentifierMap.put(metaData.getQuestionIdentifier(), metaData);
					}
				} catch (Exception e) {
					logger.error("NbsQuestionMetadata error thrown "
							+ metaData.toString(), e);
				}
			}

			if(requiredQuestionIdentifierMap!=null && requiredQuestionIdentifierMap.size()>0)
				questionIdentifierMap.put(_REQUIRED,requiredQuestionIdentifierMap);
		} catch (Exception e) {
			logger.error("NbsQuestionMetadata error thrown " + e, e);
		}
		return questionIdentifierMap;
	}

	public static String getNbsCode(String questionIdentifier,
			CodedType codeType, Map<Object, Object> questionIdentifierMap) {
		String nbsCode = "";
		if (codeType != null) {
			try {
				boolean isConceptCode = true;
				String conceptCode = codeType.getCode();
				String codeSystemCode = codeType.getCodeSystemCode();
				if (conceptCode == null || conceptCode.equalsIgnoreCase("")) {
					isConceptCode = false;
					conceptCode = codeType.getAlternateCode();
					codeSystemCode = codeType.getAlternateCodeSystemCode();
				}
				if (questionIdentifierMap.get(questionIdentifier) != null) {
					NbsQuestionMetadata metaData = (NbsQuestionMetadata) questionIdentifierMap
							.get(questionIdentifier);
					Long codeSetGroupId = metaData.getCodeSetGroupId();
					if (codeSetGroupId == null) {
						try {
							codeSetGroupId = new Long(metaData.getUnitValue());
						} catch (NumberFormatException e) {
							logger.debug("The number could not be converted to Int for conceptCode:"
									+ conceptCode
									+ " for questionIdentifier:"
									+ questionIdentifier);
						}
					}
					if (codeSetGroupId != null) {
						SRTMapDAOImpl map = new SRTMapDAOImpl();
						String returnedCode = map.getConceptToCode(conceptCode,
								codeSetGroupId, codeSystemCode, isConceptCode);
						if (returnedCode
								.equalsIgnoreCase(NEDSSConstants.TABLE_NOT_MAPPED)) {
							logger.warn("The conceptCode " + conceptCode
									+ " not mapped.");
							nbsCode = conceptCode;
						} else {
							nbsCode = returnedCode;
						}
					} else
						nbsCode = conceptCode;

				}
			} catch (Exception e) {
				logger.error("EdxPHCRDocumentUtil.getNbsCode error meesage for questionIdentifier="
						+ questionIdentifier + "CodedType=" + codeType);
				logger.error("EdxPHCRDocumentUtil.getNbsCode error meesage "
						+ e);
				throw new EJBException(
						"EdxPHCRDocumentUtil.getNbsCode error meesage for questionIdentifier="
								+ questionIdentifier + "CodedType=" + codeType
								+ e, e);
			}
		}
		return nbsCode;
	}

	public static void createActRelationshipForDoc(Long documentUid,
			PublicHealthCaseVO publicHealthCaseVO) {
		ActRelationshipDT actDoc = new ActRelationshipDT();
		actDoc.setItNew(true);
		actDoc.setSourceActUid(documentUid);
		actDoc.setSourceClassCd(NEDSSConstants.ACT_CLASS_CD_FOR_DOC);
		actDoc.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
		actDoc.setTargetActUid(publicHealthCaseVO.getThePublicHealthCaseDT()
				.getPublicHealthCaseUid());
		actDoc.setTargetClassCd(NEDSSConstants.CASE);
		actDoc.setRecordStatusCd(NEDSSConstants.ACTIVE);
		actDoc.setTypeCd(NEDSSConstants.DocToPHC);
		actDoc.setItNew(true);
		actDoc.setItDirty(false);

		if (publicHealthCaseVO.getTheActRelationshipDTCollection() == null) {
			Collection<Object> coll = new ArrayList<Object>();
			coll.add(actDoc);
			publicHealthCaseVO.setTheActRelationshipDTCollection(coll);
		} else
			publicHealthCaseVO.getTheActRelationshipDTCollection().add(actDoc);
	}

	public static PamVO getCreatePamVO(PamVO pamVO) {
		if (pamVO.getPamAnswerDTMap() != null) {
			Collection<Object> PamAnswerDTCollection = pamVO
					.getPamAnswerDTMap().values();
			Map<Object, Object> nbsReturnAnswerMap = new HashMap<Object, Object>();
			Map<Object, Object> nbsAnswerMap = new HashMap<Object, Object>();
			Map<Object, Object> nbsRepeatingAnswerMap = new HashMap<Object, Object>();
			Long nbsQuestionUid = new Long(0);
			Collection<Object> coll = new ArrayList<Object>();
			Iterator<Object> it = PamAnswerDTCollection.iterator();
			while (it.hasNext()) {
				NbsCaseAnswerDT pamAnsDT = (NbsCaseAnswerDT) it.next();

				if (pamAnsDT.getAnswerGroupSeqNbr() != null
						&& pamAnsDT.getAnswerGroupSeqNbr() > -1) {
					if (nbsRepeatingAnswerMap.get(pamAnsDT.getNbsQuestionUid()) == null) {
						Collection<Object> collection = new ArrayList<Object>();
						collection.add(pamAnsDT);
						nbsRepeatingAnswerMap.put(pamAnsDT.getNbsQuestionUid(),
								collection);
					} else {
						@SuppressWarnings("unchecked")
						Collection<Object> collection = (Collection<Object>) nbsRepeatingAnswerMap
								.get(pamAnsDT.getNbsQuestionUid());
						collection.add(pamAnsDT);
						nbsRepeatingAnswerMap.put(pamAnsDT.getNbsQuestionUid(),
								collection);
					}
				} else if ((pamAnsDT.getNbsQuestionUid().compareTo(
						nbsQuestionUid) == 0)
						&& pamAnsDT.getSeqNbr() != null
						&& pamAnsDT.getSeqNbr().intValue() > 0) {
					coll.add(pamAnsDT);
				} else if (pamAnsDT.getSeqNbr() != null
						&& pamAnsDT.getSeqNbr().intValue() > 0) {
					if (coll.size() > 0) {
						nbsAnswerMap.put(nbsQuestionUid, coll);
						coll = new ArrayList<Object>();
					}
					coll.add(pamAnsDT);
				} else {
					if (coll.size() > 0) {
						nbsAnswerMap.put(nbsQuestionUid, coll);
					}
					nbsAnswerMap.put(pamAnsDT.getNbsQuestionUid(), pamAnsDT);
					coll = new ArrayList<Object>();
				}
				nbsQuestionUid = pamAnsDT.getNbsQuestionUid();
				if (!it.hasNext() && coll.size() > 0) {
					nbsAnswerMap.put(pamAnsDT.getNbsQuestionUid(), coll);
				}
			}
			nbsReturnAnswerMap.put(NEDSSConstants.NON_REPEATING_QUESTION,
					nbsAnswerMap);
			nbsReturnAnswerMap.put(NEDSSConstants.REPEATING_QUESTION,
					nbsRepeatingAnswerMap);

			pamVO.setPamAnswerDTMap(nbsAnswerMap);
			pamVO.setPageRepeatingAnswerDTMap(nbsRepeatingAnswerMap);
		}
		return pamVO;
	}


	public static EDXActivityDetailLogDT  sendProxyToEJB(NotificationProxyVO notificationProxyVO, Object pageObj, NBSSecurityObj nbsSecurityObj)throws EJBException,RemoteException,NEDSSAppException {
		HashMap<Object,Object> nndRequiredMap = new HashMap<Object,Object>();
		EDXActivityDetailLogDT eDXActivityDetailLogDT = new EDXActivityDetailLogDT();

		eDXActivityDetailLogDT.setRecordType(MSG_TYPE.Notification.name());
		eDXActivityDetailLogDT.setRecordName("PHCR_IMPORT");
		eDXActivityDetailLogDT.setLogType(EdxRuleAlgorothmManagerDT.STATUS_VAL.Success.name());

		try {
			NedssUtils nu = new NedssUtils();
			Object object = nu.lookupBean(JNDINames.NOTIFICATION_PROXY_EJB);
			boolean formatErr = false;

			NotificationProxyHome notificationProxyHome = (NotificationProxyHome) javax.rmi.
	        PortableRemoteObject.narrow(object, NotificationProxyHome.class);
			NotificationProxy notificationProxy = notificationProxyHome.create();
			 PublicHealthCaseDT phcDT = notificationProxyVO.getThePublicHealthCaseVO().getThePublicHealthCaseDT();
			    Long publicHealthCaseUid = phcDT.getPublicHealthCaseUid();
			    try
			    {  Map<Object,Object> subMap = new HashMap<Object,Object>();
			    	TreeMap<Object, Object> condAndFormCdTreeMap = CachedDropDowns.getConditionCdAndInvFormCd();
					String investigationFormCd = condAndFormCdTreeMap.get(phcDT.getCd()).toString();
			    	Collection<Object>  notifReqColl = new ArrayList<Object>();
			    	if (QuestionsCache.getQuestionMapEJBRef() != null){
			    		notifReqColl = QuestionsCache.getQuestionMapEJBRef().retrieveQuestionRequiredNnd(investigationFormCd);

			  		  	if(nndRequiredMap.get(investigationFormCd) == null || ((HashMap<?,?>)nndRequiredMap.get(investigationFormCd)).size()==0) {

			  		  		if(notifReqColl != null && notifReqColl.size() > 0) {
			  		  			Iterator<Object>  iter = notifReqColl.iterator();
			  		  			while(iter.hasNext()) {
			  		  				NbsQuestionMetadata metaData = (NbsQuestionMetadata) iter.next();
			  		  				subMap.put(metaData.getNbsQuestionUid(), metaData);
			  		  			}
			  		  		}
			  		  	}
			  		  	Map<?,?> result = null;
			  		  	try {
			  		  		result=notificationProxy.validatePAMNotficationRequiredFieldsGivenPageProxy(pageObj, publicHealthCaseUid, subMap,investigationFormCd, nbsSecurityObj);
			  		  		StringBuffer errorText =new StringBuffer(20);
			  		  		if(result!=null && result.size()>0){
			  		  			int i =  result.size();
			  		  			Collection<?> coll =result.values();
			  		  			Iterator<?> it= coll.iterator();
			  		  			while(it.hasNext()){
			  		  				String label = (String)it.next();
			  		  				--i;
			  		  				errorText.append("["+label+"]");
			  		  				if(it.hasNext()){
			  		  					errorText.append("; and ");
			  		  				}
		  		  					if(i==0)
		  		  						errorText.append(".");

			  					}
			  		  			formatErr = true;
			  		  			eDXActivityDetailLogDT.setLogType(EdxRuleAlgorothmManagerDT.STATUS_VAL.Failure.name());
			  		  			eDXActivityDetailLogDT.setComment(EdxELRConstants.MISSING_NOTF_REQ_FIELDS+ errorText.toString());
			  		  			return eDXActivityDetailLogDT;
			  		  		}
			  		  	}catch (Exception e) {
			  		  		logger.error("Error in sendProxyToEJB  for Notifications: " + e.toString());
			  		  		throw new Exception(e.toString(), e);
			  		  	}
			    	}
			    }catch (Exception ex) {
			    	logger.error("Error while sendProxyToEJB Notif Required Fields: " + ex.toString());
			    	throw new Exception(ex.toString(), ex);
			    }
			    String programAreaCd = notificationProxyVO.getThePublicHealthCaseVO().getThePublicHealthCaseDT().getProgAreaCd();
			    NotificationVO notifVO = notificationProxyVO.getTheNotificationVO();
			    NotificationDT notifDT = notifVO.getTheNotificationDT();
			    notifDT.setProgAreaCd(programAreaCd);
			    notifVO.setTheNotificationDT(notifDT);
			    notificationProxyVO.setTheNotificationVO(notifVO);
			    Long realNotificationUid=notificationProxy.setNotificationProxy(notificationProxyVO, nbsSecurityObj);
				eDXActivityDetailLogDT.setRecordId(""+realNotificationUid);
			    if (!formatErr) {
			    	eDXActivityDetailLogDT.setComment("Notification created (UID: "+realNotificationUid+")");
			    }

			} catch (Exception e) {
				e.printStackTrace();
				eDXActivityDetailLogDT.setLogType(EdxRuleAlgorothmManagerDT.STATUS_VAL.Failure.name());
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				String exceptionMessage = errors.toString();
				exceptionMessage = exceptionMessage.substring(0,Math.min(exceptionMessage.length(), 2000));
		  		eDXActivityDetailLogDT.setComment(exceptionMessage);
		}
		return eDXActivityDetailLogDT;
	}//sendProxyToEJB
	
	public static PageActProxyVO getPageProxyVOWithoutSummary(Long publicHealthCaseUid, NBSSecurityObj nbsSecurityObj)
			throws NEDSSAppException {
		PageActProxyVO proxyVO = null;
		try {
			NedssUtils nu = new NedssUtils();
			// Object obj= createPageActProxyVO(nbsDocumentVO,nbsSecurityObj);

			Object object = nu.lookupBean(JNDINames.PAGE_PROXY_EJB);
			PageProxyHome pageProxyHome = (PageProxyHome) javax.rmi.PortableRemoteObject.narrow(object,
					PageProxyHome.class);
			PageProxy pageProxy = pageProxyHome.create();

			proxyVO = (PageActProxyVO) pageProxy.getPageProxyVO(NEDSSConstants.CASE_LITE, publicHealthCaseUid, nbsSecurityObj);

		} catch (NEDSSSystemException e) {
			logger.error("EdxPHCRDocumentUtil.createInvestigation NEDSSSystemException thrown " + e, e);
			throw new NEDSSAppException("EdxPHCRDocumentUtil.createInvestigation NEDSSSystemException thrown " + e, e);
		} catch (ClassCastException e) {
			logger.error("EdxPHCRDocumentUtil.createInvestigation ClassCastException thrown " + e);
			throw new NEDSSAppException("EdxPHCRDocumentUtil.createInvestigation ClassCastException thrown " + e, e);
		} catch (RemoteException e) {
			logger.error("EdxPHCRDocumentUtil.createInvestigation RemoteException thrown " + e);
			throw new NEDSSAppException("EdxPHCRDocumentUtil.createInvestigation RemoteException thrown " + e, e);
		} catch (EJBException e) {
			logger.error("EdxPHCRDocumentUtil.createInvestigation EJBException thrown " + e);
			throw new NEDSSAppException("EdxPHCRDocumentUtil.createInvestigation EJBException thrown " + e, e);
		} catch (CreateException e) {
			logger.error("EdxPHCRDocumentUtil.createInvestigation CreateException thrown " + e);
			throw new NEDSSAppException("EdxPHCRDocumentUtil.createInvestigation CreateException thrown " + e, e);
		} catch (FinderException e) {
			logger.error("EdxPHCRDocumentUtil.createInvestigation FinderException thrown " + e);
			throw new NEDSSAppException("EdxPHCRDocumentUtil.createInvestigation FinderException thrown " + e, e);
		} catch (Exception e) {
			logger.error("EdxPHCRDocumentUtil.createInvestigation Exception thrown " + e);
			throw new NEDSSAppException("EdxPHCRDocumentUtil.createInvestigation Exception thrown " + e, e);
		}
		return proxyVO;
	}


}