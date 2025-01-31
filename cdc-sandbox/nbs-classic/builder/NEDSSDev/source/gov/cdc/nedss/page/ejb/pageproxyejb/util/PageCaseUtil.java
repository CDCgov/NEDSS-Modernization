package gov.cdc.nedss.page.ejb.pageproxyejb.util;

import gov.cdc.nedss.act.ctcontact.dt.CTContactDT;
import gov.cdc.nedss.act.ctcontact.dt.CTContactSummaryDT;
import gov.cdc.nedss.act.ctcontact.ejb.dao.CTContactDAOImpl;
import gov.cdc.nedss.act.ctcontact.ejb.dao.CTContactRootDAO;
import gov.cdc.nedss.act.intervention.dt.InterventionDT;
import gov.cdc.nedss.act.intervention.vo.InterventionVO;
import gov.cdc.nedss.act.interview.dt.InterviewSummaryDT;
import gov.cdc.nedss.act.interview.ejb.dao.InterviewSummaryDAO;
import gov.cdc.nedss.act.notification.dt.NotificationDT;
import gov.cdc.nedss.act.notification.vo.NotificationVO;
import gov.cdc.nedss.act.observation.helper.ObservationProcessor;
import gov.cdc.nedss.act.publichealthcase.dt.CaseManagementDT;
import gov.cdc.nedss.act.publichealthcase.dt.PublicHealthCaseDT;
import gov.cdc.nedss.act.publichealthcase.ejb.dao.CaseManagementDAOImpl;
import gov.cdc.nedss.act.publichealthcase.vo.PublicHealthCaseVO;
import gov.cdc.nedss.act.sqlscript.WumSqlQuery;
import gov.cdc.nedss.act.treatment.dt.TreatmentDT;
import gov.cdc.nedss.act.util.ManageAutoAssociations;
import gov.cdc.nedss.association.dao.ActRelationshipDAOImpl;
import gov.cdc.nedss.association.dao.ActRelationshipHistoryManager;
import gov.cdc.nedss.association.dao.ParticipationDAOImpl;
import gov.cdc.nedss.association.dao.ParticipationHistoryManager;
import gov.cdc.nedss.association.dt.ActRelationshipDT;
import gov.cdc.nedss.association.dt.ParticipationDT;
import gov.cdc.nedss.controller.ejb.actcontrollerejb.bean.ActController;
import gov.cdc.nedss.controller.ejb.actcontrollerejb.bean.ActControllerHome;
import gov.cdc.nedss.controller.ejb.entitycontrollerejb.bean.EntityController;
import gov.cdc.nedss.controller.ejb.entitycontrollerejb.bean.EntityControllerHome;
import gov.cdc.nedss.entity.material.dt.MaterialDT;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.nbsactentity.dt.NbsActEntityDT;
import gov.cdc.nedss.nnd.dt.CnTransportQOutDT;
import gov.cdc.nedss.nnd.ejb.nndmessageprocessorejb.dao.CnTransportQOutDAOImpl;
import gov.cdc.nedss.nnd.helper.NNDActivityLogDT;
import gov.cdc.nedss.nnd.helper.NNDMessageSenderHelper;
import gov.cdc.nedss.page.ejb.pageproxyejb.bean.dao.NBSAttachmentNoteDAOImpl;
import gov.cdc.nedss.page.ejb.pageproxyejb.dt.NBSNoteDT;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.PageProxyVO;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.act.PageActProxyVO;
import gov.cdc.nedss.pam.dao.NbsHistoryDAO;
import gov.cdc.nedss.pam.dao.PamRootDAO;
import gov.cdc.nedss.pam.vo.PamVO;
import gov.cdc.nedss.proxy.ejb.epilink.dao.EpilinkActivityLogDaoImpl;
import gov.cdc.nedss.proxy.ejb.epilink.dt.EpilinkDT;
import gov.cdc.nedss.proxy.ejb.interventionproxyejb.vo.VaccinationSummaryVO;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.dao.ObservationSummaryDAOImpl;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.dao.RetrieveSummaryVO;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.CoinfectionSummaryVO;
import gov.cdc.nedss.proxy.ejb.notificationproxyejb.bean.NotificationProxy;
import gov.cdc.nedss.proxy.ejb.notificationproxyejb.bean.NotificationProxyHome;
import gov.cdc.nedss.proxy.ejb.notificationproxyejb.vo.NotificationProxyVO;
import gov.cdc.nedss.proxy.ejb.notificationproxyejb.vo.NotificationSummaryVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.bean.ObservationProxy;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.bean.ObservationProxyHome;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.LabReportSummaryVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.MorbReportSummaryVO;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.bean.PamProxy;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.bean.PamProxyEJB;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.bean.PamProxyHome;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.dao.CTContactSummaryDAO;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.vo.CTContactProxyVO;
import gov.cdc.nedss.proxy.ejb.queue.dao.MessageLogDAOImpl;
import gov.cdc.nedss.proxy.ejb.queue.dt.MessageLogDT;
import gov.cdc.nedss.proxy.ejb.treatmentproxyejb.vo.TreatmentSummaryVO;
import gov.cdc.nedss.systemservice.ejb.casenotificationejb.dao.CaseNotificationDAOImpl;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.bean.NbsDocument;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.bean.NbsDocumentHome;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dao.NbsDocumentDAOImpl;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dt.EDXEventProcessDT;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.vo.NBSDocumentVO;
import gov.cdc.nedss.systemservice.ejb.nbssecurityejb.helper.NBSAuthHelper;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.nbssecurity.ProgramAreaJurisdictionUtil;
import gov.cdc.nedss.systemservice.util.NEDSSDAOFactory;
import gov.cdc.nedss.systemservice.util.PrepareVOUtils;
import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.BMPBase;
import gov.cdc.nedss.util.CDAEventSummaryParser;
import gov.cdc.nedss.util.DataTables;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.MessageConstants;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.util.UidSummaryVO;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.ejb.SessionContext;
import javax.rmi.PortableRemoteObject;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;

/**
 *Name: PageCaseUtil.java Description: Utility class for Page Object(for
 * Dynamic Pages) Copyright(c) 2010
 * Release 5.2/2017: added support for merge investigation cases
 * 
 * @Company: CSC
 * @since: NBS4.0
 * @author Pradeep Sharma
 * @Company: CSRA
 * @since: NBS5.2
 * @updatedByAuthor: Pradeep Sharma
 */
public class PageCaseUtil extends BMPBase {
	static final LogUtils logger = new LogUtils(PageCaseUtil.class.getName());

	private static final long serialVersionUID = 1L;
	Long phcPatientRevisionUid=null;
	
	
	/**
	 * 
	 * @param publicHealthCaseUID
	 * @param nbsSecurityObj
	 * @return
	 * @throws RemoteException
	 * @throws java.rmi.RemoteException
	 * @throws CreateException
	 * @throws javax.ejb.EJBException
	 * @throws javax.ejb.CreateException
	 * @throws NEDSSConcurrentDataException
	 * @throws NEDSSSystemException
	 * @throws javax.ejb.FinderException
	 * @throws NEDSSConcurrentDataException
	 */
	@SuppressWarnings("unchecked")
	public Map<Object, Object> deletePageProxy(Long publicHealthCaseUID, boolean isMergeCase ,
			NBSSecurityObj nbsSecurityObj) throws RemoteException,
			CreateException, NEDSSConcurrentDataException {
		try {
			int labCount = 0;
			int morbCount = 0;
			int vaccineCount = 0;
			int documentCount = 0;
			Map<Object, Object> returnMap = new HashMap<Object, Object>();
			PrepareVOUtils prepareVOUtils = new PrepareVOUtils();
			NedssUtils nedssUtils = new NedssUtils();
			ActController actController = null;

			Object object = nedssUtils.lookupBean(JNDINames.ActControllerEJB);
			logger.debug("ActController lookup = " + object.toString());
			ActControllerHome acthome = (ActControllerHome) PortableRemoteObject
					.narrow(object, ActControllerHome.class);
			logger.debug("Found ActControllerHome: " + acthome);
			try {
				actController = acthome.create();
			} catch (RemoteException e1) {
				logger.fatal("PageCaseUtil.deletePageProxy RemoteException thrown"
						+ e1.getMessage(),e1);
				throw new RemoteException(e1.getMessage(),e1);
			} catch (CreateException e1) {
				logger.fatal("PageCaseUtil.deletePageProxy CreateException thrown"
						+ e1.getMessage(),e1);
				throw new CreateException(e1.getMessage());
			}

			PageActProxyVO pageProxyVO = null;
			try {
				pageProxyVO = getPageProxyVO(NEDSSConstants.CASE, publicHealthCaseUID, nbsSecurityObj);
			} catch (RemoteException e1) {
				logger.fatal("PageCaseUtil.deletePageProxy RemoteException thrown"
						+ e1.getMessage(),e1);
				throw new RemoteException(e1.getMessage(),e1);
			} catch (NEDSSSystemException e1) {
				logger
						.fatal("PageCaseUtil.deletePageProxy NEDSSSystemException thrown"
								+ e1.getMessage(),e1);
				throw new NEDSSSystemException(e1.getMessage(),e1);
			}
			pageProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT()
					.setPageCase(true);
			NbsHistoryDAO nbsHistoryDAO = new NbsHistoryDAO();
			nbsHistoryDAO.getPamHistory(pageProxyVO.getPublicHealthCaseVO());
			// nbsHistoryDAO.insertPageHistory(pageProxyVO.getPublicHealthCaseVO());
			PublicHealthCaseDT phcDT = pageProxyVO.getPublicHealthCaseVO()
					.getThePublicHealthCaseDT();
			phcDT.setItDelete(false);
			phcDT.setItDirty(true);

			if (nbsSecurityObj.getPermission(
					NBSBOLookup.INVESTIGATION,
					NBSOperationLookup.DELETE, phcDT.getProgAreaCd(), phcDT
							.getJurisdictionCd())) {
				ActRelationshipDAOImpl actRelationshipDAOImpl = new ActRelationshipDAOImpl();
				pageProxyVO.getPublicHealthCaseVO()
						.setTheActRelationshipDTCollection(
								actRelationshipDAOImpl.load(publicHealthCaseUID
										.longValue()));
				Iterator<Object> anIterator = null;
				for (anIterator = pageProxyVO.getPublicHealthCaseVO()
						.getTheActRelationshipDTCollection().iterator(); anIterator
						.hasNext();) {
					ActRelationshipDT actRelationshipDT = (ActRelationshipDT) anIterator
							.next();
					if (actRelationshipDT.getTypeCd().equalsIgnoreCase(
							NEDSSConstants.LAB_REPORT))
						labCount++;
					if (actRelationshipDT.getTypeCd().equalsIgnoreCase(
							NEDSSConstants.MORBIDITY_REPORT))
						morbCount++;
					if (actRelationshipDT.getTypeCd().equalsIgnoreCase(
							NEDSSConstants.AR_TYPE_CODE))
						vaccineCount++;
					if (actRelationshipDT.getTypeCd().equalsIgnoreCase(
							NEDSSConstants.DocToPHC))
						documentCount++;						
				}

				returnMap.put(NEDSSConstants.LAB_REPORT, new Integer(labCount));
				returnMap.put(NEDSSConstants.MORBIDITY_REPORT, new Integer(morbCount));
				// '1180' AR_TYPE_CODE is for Vaccines
				returnMap.put(NEDSSConstants.AR_TYPE_CODE,
						new Integer(vaccineCount));
				returnMap.put(NEDSSConstants.DocToPHC,
						new Integer(documentCount));
				//must unlink event Before deleting 
				if (labCount > 0 || morbCount > 0 || vaccineCount > 0 || documentCount > 0) {
					return returnMap;
				}

				// Logically delete the Investigation
				/**TODO PKS UPDATE FOR MERGE INVESTIGATIONS
				if(isMergeCase)
					phcDT.setReentrant(true);*/
				RootDTInterface rootDTInterface = phcDT;
				String businessObjLookupName = NBSBOLookup.INVESTIGATION;
				String businessTriggerCd = "INV_DEL";
				String tableName = "PUBLIC_HEALTH_CASE";
				String moduleCd = "BASE";
				try {
					phcDT = (PublicHealthCaseDT) prepareVOUtils.prepareVO(
							rootDTInterface, businessObjLookupName,
							businessTriggerCd, tableName, moduleCd, nbsSecurityObj);

					phcDT.setCaseClassCd(NEDSSConstants.CASE_CLASS_CODE_NOT_A_CASE);
					phcDT
							.setInvestigationStatusCd(NEDSSConstants.INVESTIGATION_STATUS_CODE_CLOSED);
					pageProxyVO.getPublicHealthCaseVO().setThePublicHealthCaseDT(
							phcDT);
					actController.setPublicHealthCase(pageProxyVO
							.getPublicHealthCaseVO(), nbsSecurityObj);
				} catch (NEDSSSystemException e1) {
					logger
							.fatal("PageCaseUtil.deletePageProxy NEDSSSystemException thrown"
									+ e1.getMessage(),e1);
					throw new NEDSSSystemException(e1.getMessage(),e1);
				} catch (NEDSSConcurrentDataException e1) {
					logger
							.fatal("PageCaseUtil.deletePageProxy NEDSSConcurrentDataException thrown"
									+ e1.getMessage(),e1);
					throw new NEDSSConcurrentDataException(e1.getMessage(),e1);
				} catch (EJBException e1) {
					logger.fatal("PageCaseUtil.deletePageProxy EJBException thrown"
							+ e1.getMessage(), e1);
					throw new EJBException(e1.getMessage(),e1);
				}
				// Persist the Investigation
				// actController.setPublicHealthCaseInfo(phcDT, nbsSecurityObj);
				// /////////////////////
				// Handle Notifications - update state if a notification exists

				NotificationSummaryVO notificationSummaryVO = null;
				if (pageProxyVO.getTheNotificationSummaryVOCollection() != null
						&& pageProxyVO.getTheNotificationSummaryVOCollection()
								.size() > 0) {
					Collection<Object> theNotificationSummaryVOCollection = pageProxyVO
							.getTheNotificationSummaryVOCollection();
					Iterator<Object> iter = theNotificationSummaryVOCollection
							.iterator();
					while (iter.hasNext()) {
						notificationSummaryVO = (NotificationSummaryVO) iter.next();
						if (notificationSummaryVO.getIsHistory().equalsIgnoreCase(
								"F")) {
							NotificationProxy notificationProxy = null;
							Object objNotification = nedssUtils
									.lookupBean(JNDINames.NOTIFICATION_PROXY_EJB);
							logger.debug("NotificationProxyEJB lookup = "
									+ objNotification.toString());
							NotificationProxyHome notificationProxyHome = (NotificationProxyHome) PortableRemoteObject
									.narrow(objNotification,
											NotificationProxyHome.class);
							logger.debug("Found NotificationProxyHome: "
									+ notificationProxyHome);
							notificationProxy = notificationProxyHome.create();
							NotificationProxyVO notificationProxyVO = null;
							if(isMergeCase){
								notificationProxyVO = notificationProxy
										.getNotificationProxyForMerge(notificationSummaryVO
												.getNotificationUid(), nbsSecurityObj);
							}else{
								notificationProxyVO = notificationProxy
										.getNotificationProxy(notificationSummaryVO
												.getNotificationUid(), nbsSecurityObj);
							}
							NotificationDT notificationDT = notificationProxyVO
									.getTheNotificationVO().getTheNotificationDT();

							// if auto resend is "T", set to PEND_DEL and resend the
							// notification
							// if auto resend is off, logically delete the
							// notification
							if (notificationDT.getAutoResendInd().equalsIgnoreCase(
									"T")) {
								try {
									notificationDT.setItDelete(false);
									notificationDT.setItDirty(true);

									String BUSINESS_OBJECT_LOOKUP_NAME = NBSBOLookup.NOTIFICATION;
									String BUSINESS_TRIGGER_CD = NEDSSConstants.NOT_DEL_NOTF;
									String TABLE_NAME = DataTables.NOTIFICATION_TABLE;
									String MODULE_CD = NEDSSConstants.BASE;

									NotificationDT newNotificationDT = (NotificationDT) prepareVOUtils
											.prepareVO(notificationDT,
													BUSINESS_OBJECT_LOOKUP_NAME,
													BUSINESS_TRIGGER_CD,
													TABLE_NAME, MODULE_CD,
													nbsSecurityObj);

									actController.setNotificationInfo(
											newNotificationDT, nbsSecurityObj);
									// nndMSHelper.updateAutoResendNotificationsAsync(investigationProxyVO,
									// nbsSecurityObj);*/
								} catch (Exception e) {
									NNDMessageSenderHelper nndMSHelper = null;
									nndMSHelper = NNDMessageSenderHelper
											.getInstance();
									NNDActivityLogDT nndActivityLogDT = new NNDActivityLogDT();
									String phcLocalId = pageProxyVO
											.getPublicHealthCaseVO()
											.getThePublicHealthCaseDT()
											.getLocalId();
									nndActivityLogDT.setErrorMessageTxt(e
											.toString());
									if (phcLocalId != null)
										nndActivityLogDT.setLocalId(phcLocalId);
									else
										nndActivityLogDT.setLocalId("N/A");

									// catch & store auto resend notifications
									// exceptions in NNDActivityLog table
									nndMSHelper.persistNNDActivityLog(
											nndActivityLogDT, nbsSecurityObj);
									logger
											.error("Exception occurred while calling nndMessageSenderHelper.updateAutoResendNotificationsAsync");
									e.printStackTrace();
								}

							} else {
								notificationDT.setItDelete(false);
								notificationDT.setItDirty(true);

								String BUSINESS_OBJECT_LOOKUP_NAME = NBSBOLookup.NOTIFICATION;
								String BUSINESS_TRIGGER_CD = NEDSSConstants.NOT_DEL;
								String TABLE_NAME = DataTables.NOTIFICATION_TABLE;
								String MODULE_CD = NEDSSConstants.BASE;

								try {
									NotificationDT newNotificationDT = (NotificationDT) prepareVOUtils
											.prepareVO(notificationDT,
													BUSINESS_OBJECT_LOOKUP_NAME,
													BUSINESS_TRIGGER_CD,
													TABLE_NAME, MODULE_CD,
													nbsSecurityObj);

									actController.setNotificationInfo(
											newNotificationDT, nbsSecurityObj);
								} catch (NEDSSSystemException e) {
									logger
											.fatal("PageCaseUtil.deletePageProxy NEDSSSystemException thrown"
													+ e.getMessage(),e);
									throw new NEDSSSystemException(e.getMessage(),e);
								} catch (NEDSSConcurrentDataException e) {
									logger
											.fatal("PageCaseUtil.deletePageProxy NEDSSConcurrentDataException thrown"
													+ e.getMessage(),e);
									throw new NEDSSConcurrentDataException(e.getMessage(),e);
								} catch (EJBException e) {
									logger
											.fatal("PageCaseUtil.deletePageProxy EJBException thrown"
													+ e.getMessage(),e);
									throw new EJBException(e.getMessage(),e);
								}
							}
						}
					}
				}
				// ///////////////////////////////////

				if(!isMergeCase){
					
				// Logically delete the associated Contacts
				CTContactSummaryDAO cTContactSummaryDAO = new CTContactSummaryDAO();
				Collection<Object> contactCollection = cTContactSummaryDAO
						.getContactListForInvestigation(publicHealthCaseUID,
								nbsSecurityObj);
					try {
						PamProxy pamProxy = null;
						pamProxy = getPamProxyEJBRemoteInterface();
						if (contactCollection != null && contactCollection.size() > 0) {
							Iterator itContact = contactCollection.iterator();
							while (itContact.hasNext()) {
								CTContactSummaryDT ctContactSummaryDT = (CTContactSummaryDT) (itContact
										.next());
	
								CTContactProxyVO ctContactProxyVO = pamProxy
										.getContactProxyVO(ctContactSummaryDT
												.getCtContactUid(), nbsSecurityObj);
								if (ctContactProxyVO.getcTContactVO().getcTContactDT()
										.getSubjectEntityPhcUid().compareTo(
												publicHealthCaseUID) == 0)
									ctContactProxyVO.setItDelete(true);
								else {
									ctContactProxyVO.getcTContactVO().getcTContactDT()
											.setContactEntityPhcUid(null);
									ctContactProxyVO.setItDirty(true);
									ctContactProxyVO.getcTContactVO().setItDirty(true);
									ctContactProxyVO.getcTContactVO().getcTContactDT()
											.setItDirty(true);
	
								}
								pamProxy.setContactProxyVO(ctContactProxyVO,
										nbsSecurityObj);
							}
						}
					} 
					catch (Exception e) {
						logger.fatal("Error message thrown in deletePageProxy" + e.getMessage(),e);
						throw new NEDSSSystemException(e.getMessage());
					}
				}

				// Logically delete the associated Patient revision
				Long personUid = this
						.getPersonUidOfInvestigation(publicHealthCaseUID);

				Object lookedUpObj = nedssUtils
						.lookupBean(JNDINames.EntityControllerEJB);
				logger.debug("!!!!!!!!!!EntityController lookup = "
						+ object.toString());
				EntityControllerHome ecHome = (EntityControllerHome) PortableRemoteObject
						.narrow(lookedUpObj, EntityControllerHome.class);
				logger.debug("!!!!!!!!!!Found EntityControllerHome: " + ecHome);
				EntityController entityController = ecHome.create();

				// retrieve PersonVO that represents the Patient Revision for this
				// Vaccination by passing in personUid
				PersonVO personVO = entityController.getPatientRevision(personUid,
						nbsSecurityObj);
				personVO.setItDirty(true);
				personVO.getThePersonDT().setItDirty(true);
				businessTriggerCd = NEDSSConstants.PAT_DEL;
				personUid = entityController.setPatientRevision(personVO,
						businessTriggerCd, nbsSecurityObj);
				logger
						.debug("PersonUid value returned by PageProxy.deletePageProxy(): "
								+ personUid);

				if (pageProxyVO.getTheTreatmentSummaryVOCollection() != null) {
					Iterator<Object> anTreatmentIterator = null;
					for (anTreatmentIterator = pageProxyVO
							.getTheTreatmentSummaryVOCollection().iterator(); anTreatmentIterator
							.hasNext();) {
						TreatmentSummaryVO treatmentSummaryVO = (TreatmentSummaryVO) anTreatmentIterator
								.next();

						TreatmentDT treatmentDT = actController.getTreatmentInfo(
								treatmentSummaryVO.getTreatmentUid(),
								nbsSecurityObj);
						treatmentDT.setItDelete(false);
						treatmentDT.setItDirty(true);

						String BUSINESS_OBJECT_LOOKUP_NAME = NBSBOLookup.TREATMENT;
						String BUSINESS_TRIGGER_CD = NEDSSConstants.TRT_DEL;
						String TABLE_NAME = DataTables.TREATMENT_TABLE;
						String MODULE_CD = NEDSSConstants.BASE;

						TreatmentDT newTreatmentDT = (TreatmentDT) prepareVOUtils
								.prepareVO(treatmentDT,
										BUSINESS_OBJECT_LOOKUP_NAME,
										BUSINESS_TRIGGER_CD, TABLE_NAME, MODULE_CD,
										nbsSecurityObj);

						actController.setTreatmentInfo(newTreatmentDT,
								nbsSecurityObj);
					}
				}
				PamRootDAO pamRootDAO = new PamRootDAO();
				pamRootDAO.UpdateFordeletePamVO(pageProxyVO.getPageVO(),
						pageProxyVO.getPublicHealthCaseVO());

				return returnMap;
			} else {
				returnMap.put(NEDSSConstants.SECURITY_FAIL,
						NEDSSConstants.SECURITY_FAIL);
				return returnMap;
			}
		} catch (NEDSSDAOSysException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal("NEDSSDAOSysException occured: " + e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		} catch (NEDSSSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal("NEDSSSystemException occured: " + e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		} catch (ClassCastException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal("ClassCastException occured: " + e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		} catch (EJBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal("EJBException occured: " + e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
	}

	/**
	 * Gets the PageActProxyVO Object from database
	 * 
	 * @param publicHealthCaseUID
	 * @param nbsSecurityObj
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws javax.ejb.EJBException
	 * @throws NEDSSSystemException
	 * @throws javax.ejb.FinderException
	 * @throws javax.ejb.CreateException
	 */
	public PageActProxyVO getPageProxyVO(String typeCd,Long publicHealthCaseUID,
			NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException,
			NEDSSSystemException {

		if (!nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,
				NBSOperationLookup.VIEW)) {
			logger
					.info("nbsSecurityObj.getPermission(NedssBOLookup.INVESTIGATION,NBSOperationLookup.VIEW) is false");
			throw new NEDSSSystemException("NO PERMISSIONS");
		}
		logger
				.info("nbsSecurityObj.getPermission(NedssBOLookup.INVESTIGATION,NBSOperationLookup.VIEW) is true");
		PageActProxyVO pageProxyVO = new PageActProxyVO();

		PublicHealthCaseVO thePublicHealthCaseVO = null;

		ArrayList<Object> thePersonVOCollection = new ArrayList<Object>();
		ArrayList<Object> theOrganizationVOCollection = new ArrayList<Object>();
		ArrayList<Object> theMaterialVOCollection = new ArrayList<Object>();
		ArrayList<Object> theInterventionVOCollection = new ArrayList<Object>();

		// Summary Collections
		ArrayList<Object> theVaccinationSummaryVOCollection = new ArrayList<Object>();
		ArrayList<Object> theTreatmentSummaryVOCollection = new ArrayList<Object>();
		ArrayList<Object> theInvestigationAuditLogSummaryVOCollection = new ArrayList<Object>(); // civil00014862

		ArrayList<Object> theDocumentSummaryVOCollection = new ArrayList<Object>();

		NedssUtils nedssUtils = new NedssUtils();
		Object theLookedUpObject;

		try {
			logger
					.debug("* before nedssUtils.lookupBean(JNDINames.ActControllerEJB)");
			// Reference an Act controller to use later
			theLookedUpObject = nedssUtils
					.lookupBean(JNDINames.ActControllerEJB);
			ActControllerHome actHome = (ActControllerHome) PortableRemoteObject
					.narrow(theLookedUpObject, ActControllerHome.class);
			ActController actController = actHome.create();

			logger
					.debug("* before nedssUtils.lookupBean(JNDINames.EntityControllerEJB");
			// Reference an Entity controller to use later
			theLookedUpObject = nedssUtils
					.lookupBean(JNDINames.EntityControllerEJB);
			EntityControllerHome ecHome = (EntityControllerHome) PortableRemoteObject
					.narrow(theLookedUpObject, EntityControllerHome.class);
			EntityController entityController = ecHome.create();

			// Step 1: Get the Public Health Case
			thePublicHealthCaseVO = actController.getPublicHealthCase(
					publicHealthCaseUID, nbsSecurityObj);

			// before returning PublicHealthCaseVO check security permissions -
			// if no permissions - terminate
			if (!nbsSecurityObj.checkDataAccess(thePublicHealthCaseVO
					.getThePublicHealthCaseDT(), NBSBOLookup.INVESTIGATION,
					NBSOperationLookup.VIEW)) {
				logger
						.info("nbsSecurityObj.checkDataAccess(thePublicHealthCaseVO.getThePublicHealthCaseDT(), NedssBOLookup.INVESTIGATION, NBSOperationLookup.VIEW) is false");
				throw new NEDSSSystemException("NO ACCESS PERMISSIONS");
			}
			logger
					.info("nbsSecurityObj.checkDataAccess(thePublicHealthCaseVO.getThePublicHealthCaseDT(), NedssBOLookup.INVESTIGATION, NBSOperationLookup.VIEW) is true");

			NBSAuthHelper helper = new NBSAuthHelper();
			thePublicHealthCaseVO.getThePublicHealthCaseDT().setAddUserName(
					helper.getUserName(thePublicHealthCaseVO
							.getThePublicHealthCaseDT().getAddUserId()));
			thePublicHealthCaseVO.getThePublicHealthCaseDT()
					.setLastChgUserName(
							helper.getUserName(thePublicHealthCaseVO
									.getThePublicHealthCaseDT()
									.getLastChgUserId()));

			PamRootDAO pamRootDAO = new PamRootDAO();
			PamVO pageVO = pamRootDAO.getPamVO(publicHealthCaseUID);
			pageProxyVO.setPageVO(pageVO);
			String strTypeCd;
			String strClassCd;
			String recordStatusCd = "";
			Long nEntityID;
			ParticipationDT participationDT = null;

			Iterator<Object> participationIterator = thePublicHealthCaseVO
					.getTheParticipationDTCollection().iterator();
			logger.debug("ParticipationDTCollection() = "
					+ thePublicHealthCaseVO.getTheParticipationDTCollection());

			// Populate the Entity collections with the results
			while (participationIterator.hasNext()) {
				participationDT = (ParticipationDT) participationIterator
						.next();
				nEntityID = participationDT.getSubjectEntityUid();
				strClassCd = participationDT.getSubjectClassCd();
				strTypeCd = participationDT.getTypeCd();
				recordStatusCd = participationDT.getRecordStatusCd();
				if (strClassCd != null
						&& strClassCd
								.compareToIgnoreCase(NEDSSConstants.ORGANIZATION) == 0
						&& recordStatusCd != null
						&& recordStatusCd
								.equals(NEDSSConstants.RECORD_STATUS_ACTIVE)) {
					theOrganizationVOCollection.add(entityController
							.getOrganization(nEntityID, nbsSecurityObj));
					continue;
				}
				if (strClassCd != null
						&& strClassCd
								.compareToIgnoreCase(NEDSSConstants.PERSON) == 0
						&& recordStatusCd != null
						&& recordStatusCd
								.equals(NEDSSConstants.RECORD_STATUS_ACTIVE)) {
					thePersonVOCollection.add(entityController.getPerson(
							nEntityID, nbsSecurityObj));
					continue;
				}
				if (strClassCd != null
						&& strClassCd
								.compareToIgnoreCase(NEDSSConstants.MATERIAL) == 0
						&& recordStatusCd != null
						&& recordStatusCd
								.equals(NEDSSConstants.RECORD_STATUS_ACTIVE)) {
					theMaterialVOCollection.add(entityController.getMaterial(
							nEntityID, nbsSecurityObj));
					continue;
				}
				if (nEntityID == null || strClassCd == null
						|| strClassCd.length() == 0) {
					continue;
				}
			}
			
			pageProxyVO.setTheOrganizationVOCollection(theOrganizationVOCollection);
			pageProxyVO.setPublicHealthCaseVO(thePublicHealthCaseVO);
			pageProxyVO.setThePersonVOCollection(thePersonVOCollection);
			
			pageProxyVO.setTheNotificationSummaryVOCollection(RetrieveSummaryVO
					.notificationSummaryOnInvestigation(thePublicHealthCaseVO,
							pageProxyVO, nbsSecurityObj));

			if (pageProxyVO.getTheNotificationSummaryVOCollection() != null) {
				Iterator<Object> it = pageProxyVO
						.getTheNotificationSummaryVOCollection().iterator();
				while (it.hasNext()) {
					NotificationSummaryVO notifVO = (NotificationSummaryVO) it
							.next();
					Iterator<Object> actIterator = pageProxyVO
							.getPublicHealthCaseVO()
							.getTheActRelationshipDTCollection().iterator();
					while (actIterator.hasNext()) {
						ActRelationshipDT actRelationDT = (ActRelationshipDT) actIterator
								.next();
						if ((notifVO.getCdNotif().equalsIgnoreCase(NEDSSConstants.CLASS_CD_SHARE_NOTF) ||
								notifVO.getCdNotif().equalsIgnoreCase(NEDSSConstants.CLASS_CD_SHARE_NOTF_PHDC))
								&& notifVO.getNotificationUid().compareTo(
										actRelationDT.getSourceActUid()) == 0) {
							actRelationDT.setShareInd(true);
						}
						if ( (notifVO.getCdNotif().equalsIgnoreCase(NEDSSConstants.CLASS_CD_EXP_NOTF) || 
								notifVO.getCdNotif().equalsIgnoreCase(NEDSSConstants.CLASS_CD_EXP_NOTF_PHDC))
								&& notifVO.getNotificationUid().compareTo(
										actRelationDT.getSourceActUid()) == 0) {
							actRelationDT.setExportInd(true);
						}
						if ((notifVO.getCdNotif().equalsIgnoreCase(
								NEDSSConstants.CLASS_CD_NOTF))
								&& notifVO.getNotificationUid().compareTo(
										actRelationDT.getSourceActUid()) == 0) {
							actRelationDT.setNNDInd(true);
						}
					}
				}
			}
			
			if(typeCd!=null && !typeCd.equals(NEDSSConstants.CASE_LITE)) {
			ActRelationshipDT actRelationshipDT = null;
			// Get the Vaccinations for a PublicHealthCase/Investigation
			Iterator<Object> actRelationshipIterator = thePublicHealthCaseVO
					.getTheActRelationshipDTCollection().iterator();

			// Populate the ACT collections in the results
			while (actRelationshipIterator.hasNext()) {
				actRelationshipDT = (ActRelationshipDT) actRelationshipIterator
						.next();
				logger.debug("inside while actUid: "
						+ actRelationshipDT.getTargetActUid()
						+ " observationUid: "
						+ actRelationshipDT.getSourceActUid());
				Long nSourceActID = actRelationshipDT.getSourceActUid();
				strClassCd = actRelationshipDT.getSourceClassCd();
				strTypeCd = actRelationshipDT.getTypeCd();
				recordStatusCd = actRelationshipDT.getRecordStatusCd();

				if (strClassCd != null
						&& strClassCd
								.compareToIgnoreCase(NEDSSConstants.INTERVENTION_CLASS_CODE) == 0
						&& recordStatusCd != null
						&& recordStatusCd
								.equals(NEDSSConstants.RECORD_STATUS_ACTIVE)
						&& strTypeCd != null && !strTypeCd.equals("1180")) {
					InterventionVO interventionVO = actController
							.getIntervention(nSourceActID, nbsSecurityObj);
					theInterventionVOCollection.add(interventionVO);
					InterventionDT intDT = interventionVO
							.getTheInterventionDT();

					if (intDT.getCd() != null
							&& intDT.getCd().compareToIgnoreCase(
									"VACCINES/ANTISERA") == 0) {
						Collection<Object> intPartDTs = interventionVO
								.getTheParticipationDTCollection();
						Iterator<Object> intPartIter = intPartDTs.iterator();
						while (intPartIter.hasNext()) {
							ParticipationDT dt = (ParticipationDT) intPartIter
									.next();

							if (dt.getTypeCd() != null
									&& dt.getTypeCd() == NEDSSConstants.VACCINATION_ADMINISTERED_TYPE_CODE) {
								VaccinationSummaryVO vaccinationSummaryVO = new VaccinationSummaryVO();
								vaccinationSummaryVO.setActivityFromTime(intDT
										.getActivityFromTime());
								vaccinationSummaryVO.setInterventionUid(intDT
										.getInterventionUid());
								vaccinationSummaryVO.setLocalId(intDT
										.getLocalId());
								MaterialDT materialDT = entityController
										.getMaterialInfo(dt
												.getSubjectEntityUid(),
												nbsSecurityObj);
								vaccinationSummaryVO
										.setVaccineAdministered(materialDT
												.getNm());
								// theVaccinationSummaryVOCollection.add(vaccinationSummaryVO);
							}
						}
					}
					continue;
				}

				if (nSourceActID == null || strClassCd == null) {
					logger
							.debug("PageProxyEJB.getInvestigation: check for nulls: SourceActUID"
									+ nSourceActID + " classCd: " + strClassCd);
					continue;
				}
			}

			

			Collection<Object> labSumVOCol = new ArrayList<Object>();
			HashMap<Object, Object> labSumVOMap = new HashMap<Object, Object>();

			if (nbsSecurityObj.getPermission(NBSBOLookup.OBSERVATIONLABREPORT,
					NBSOperationLookup.VIEW,
					ProgramAreaJurisdictionUtil.ANY_PROGRAM_AREA,
					ProgramAreaJurisdictionUtil.ANY_JURISDICTION)) {
				String labReportViewClause = nbsSecurityObj
						.getDataAccessWhereClause(
								NBSBOLookup.OBSERVATIONLABREPORT,
								NBSOperationLookup.VIEW, "obs");
				labReportViewClause = labReportViewClause != null ? " AND "
						+ labReportViewClause : "";

				Collection<Object> LabReportUidSummarVOs = new ObservationSummaryDAOImpl()
						.findAllActiveLabReportUidListForManage(
								publicHealthCaseUID, labReportViewClause);

				String uidType = "LABORATORY_UID";
				Collection<?> labReportSummaryVOCollection = new ArrayList<Object>();
				LabReportSummaryVO labReportSummaryVOs = new LabReportSummaryVO();
				if (LabReportUidSummarVOs != null
						&& LabReportUidSummarVOs.size() > 0) {
					// labSumVOCol = new
					// ObservationProcessor().retrieveLabReportSummary(LabReportUidSummarVOs,
					// nbsSecurityObj);
					boolean isCDCFormPrintCase= false;
					if(typeCd.equalsIgnoreCase(NEDSSConstants.PRINT_CDC_CASE)){
						isCDCFormPrintCase = true;
						if(LabReportUidSummarVOs!=null && LabReportUidSummarVOs.size()>0){
							Iterator it = LabReportUidSummarVOs.iterator();
							while(it.hasNext()){
							UidSummaryVO uidSummaryVO = (UidSummaryVO)it.next();
							uidSummaryVO.setStatusTime(thePublicHealthCaseVO.getThePublicHealthCaseDT().getAddTime());
							}
						}
						
					}else{
						isCDCFormPrintCase= false;
					}
					labSumVOMap = new ObservationProcessor()
							.retrieveLabReportSummaryRevisited(
									LabReportUidSummarVOs,isCDCFormPrintCase , nbsSecurityObj,
									uidType);
				     
					if (labSumVOMap != null) {
						if (labSumVOMap.containsKey("labEventList")) {
							labReportSummaryVOCollection = (ArrayList<?>) labSumVOMap
									.get("labEventList");
							Iterator<?> iterator = labReportSummaryVOCollection
									.iterator();
							while (iterator.hasNext()) {
								labReportSummaryVOs = (LabReportSummaryVO) iterator
										.next();
								labSumVOCol.add(labReportSummaryVOs);
							}
						}
					}

					logger.debug("Size of labreport Collection<Object>  :"
							+ labSumVOCol.size());

					logger.debug("Size of labreport Collection<Object>  :"
							+ labSumVOCol.size());
				}
				//Add the associated labs from PHDC document
				NbsDocumentDAOImpl nbsDAO = new NbsDocumentDAOImpl();
			      Map<String, EDXEventProcessDT> edxEventsMap = nbsDAO.getEDXEventProcessMapByCaseId(thePublicHealthCaseVO.getThePublicHealthCaseDT().getPublicHealthCaseUid());
			      CDAEventSummaryParser cdaParser = new CDAEventSummaryParser();
			      Map<Long, LabReportSummaryVO> labMapfromDOC = cdaParser.getLabReportMapByPHCUid(edxEventsMap, nbsSecurityObj);
			     if(labMapfromDOC!=null && labMapfromDOC.size()>0)
			    	 labSumVOCol.addAll(labMapfromDOC.values());
			} else {
				logger
						.debug("user has no permission to view ObservationSummaryVO collection");
			}

			if (labSumVOCol != null) {
				pageProxyVO.setTheLabReportSummaryVOCollection(labSumVOCol);

			}

			Collection<Object> morbSumVOCol = new ArrayList<Object>();
			if (nbsSecurityObj.getPermission(
					NBSBOLookup.OBSERVATIONMORBIDITYREPORT,
					NBSOperationLookup.VIEW,
					ProgramAreaJurisdictionUtil.ANY_PROGRAM_AREA,
					ProgramAreaJurisdictionUtil.ANY_JURISDICTION)) {
				String morbReportViewClause = nbsSecurityObj
						.getDataAccessWhereClause(
								NBSBOLookup.OBSERVATIONMORBIDITYREPORT,
								NBSOperationLookup.VIEW, "obs");
				morbReportViewClause = morbReportViewClause != null ? " AND "
						+ morbReportViewClause : "";
				Collection<Object> morbReportUidSummarVOs = new ObservationSummaryDAOImpl()
						.findAllActiveMorbReportUidListForManage(
								publicHealthCaseUID, morbReportViewClause);

				String uidType = "MORBIDITY_UID";
				Collection<?> mobReportSummaryVOCollection = new ArrayList<Object>();
				MorbReportSummaryVO mobReportSummaryVOs = new MorbReportSummaryVO();
				HashMap<Object, Object> morbSumVoMap = new HashMap<Object, Object>();
				if (morbReportUidSummarVOs != null
						&& morbReportUidSummarVOs.size() > 0) {
					// morbSumVOCol = new
					// ObservationProcessor().retrieveMorbReportSummary(morbReportUidSummarVOs,
					// nbsSecurityObj);
					boolean isCDCFormPrintCase= false;
					if(typeCd.equalsIgnoreCase(NEDSSConstants.PRINT_CDC_CASE)){
						if(morbReportUidSummarVOs!=null && morbReportUidSummarVOs.size()>0){
							Iterator it = morbReportUidSummarVOs.iterator();
							while(it.hasNext()){
							UidSummaryVO uidSummaryVO = (UidSummaryVO)it.next();
							uidSummaryVO.setStatusTime(thePublicHealthCaseVO.getThePublicHealthCaseDT().getAddTime());
							}
						}
						
						isCDCFormPrintCase = true;
					}else{
						isCDCFormPrintCase= false;
					}
					morbSumVoMap = new ObservationProcessor()
							.retrieveMorbReportSummaryRevisited(
									morbReportUidSummarVOs, isCDCFormPrintCase, nbsSecurityObj,
									uidType);
					if (morbSumVoMap != null) {

						if (morbSumVoMap.containsKey("MorbEventColl")) {
							mobReportSummaryVOCollection = (ArrayList<?>) morbSumVoMap
									.get("MorbEventColl");
							Iterator<?> iterator = mobReportSummaryVOCollection
									.iterator();
							while (iterator.hasNext()) {
								mobReportSummaryVOs = (MorbReportSummaryVO) iterator
										.next();
								morbSumVOCol.add(mobReportSummaryVOs);

							}
						}
					}
					logger.debug("Size of Morbidity Collection<Object>  :"
							+ morbSumVOCol.size());
				}
				//Add the associated morbs from PHDC document
				NbsDocumentDAOImpl nbsDAO = new NbsDocumentDAOImpl();
			     Map<String, EDXEventProcessDT> edxEventsMap = nbsDAO.getEDXEventProcessMapByCaseId(thePublicHealthCaseVO.getThePublicHealthCaseDT().getPublicHealthCaseUid());
			     CDAEventSummaryParser cdaParser = new CDAEventSummaryParser();
				 Map<Long, MorbReportSummaryVO> morbMapfromDOC = cdaParser.getMorbReportMapByPHCUid(edxEventsMap, nbsSecurityObj);
			     if(morbMapfromDOC!=null && morbMapfromDOC.size()>0)
			    	 morbSumVOCol.addAll(morbMapfromDOC.values());
			} else {
				logger
						.debug("user has no permission to view ObservationSummaryVO collection");
			}
			if (morbSumVOCol != null) {
				pageProxyVO.setTheMorbReportSummaryVOCollection(morbSumVOCol);

			}

			if (nbsSecurityObj.getPermission(
					NBSBOLookup.INTERVENTIONVACCINERECORD,
					NBSOperationLookup.VIEW)) {
				RetrieveSummaryVO retrievePhcVaccinations = new RetrieveSummaryVO();
				theVaccinationSummaryVOCollection = new ArrayList<Object>(
						retrievePhcVaccinations
								.retrieveVaccinationSummaryVOForInv(
										publicHealthCaseUID, nbsSecurityObj)
								.values());
				pageProxyVO
						.setTheVaccinationSummaryVOCollection(theVaccinationSummaryVOCollection);
			} else {
				logger
						.debug("user has no permission to view VaccinationSummaryVO collection");
			}

			// Begin support for TreatmentSummary
			if (nbsSecurityObj.getPermission(NBSBOLookup.TREATMENT,
					NBSOperationLookup.VIEW,
					ProgramAreaJurisdictionUtil.ANY_PROGRAM_AREA,
					ProgramAreaJurisdictionUtil.ANY_JURISDICTION)) {

				logger
						.debug("About to get TreatmentSummaryList for Investigation");
				RetrieveSummaryVO rsvo = new RetrieveSummaryVO();
				theTreatmentSummaryVOCollection = new ArrayList<Object>((rsvo
						.retrieveTreatmentSummaryVOForInv(publicHealthCaseUID,
								nbsSecurityObj)).values());
				logger.debug("Number of treatments found: "
						+ theTreatmentSummaryVOCollection.size());
				pageProxyVO
						.setTheTreatmentSummaryVOCollection(theTreatmentSummaryVOCollection);
			} else {
				logger
						.debug("user has no permission to view TreatmentSummaryVO collection");
			}

			// Added this for Investigation audit log summary on the RVCT
			// Page(civil00014862)
			if (nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,
					NBSOperationLookup.VIEW)) {

				logger.debug("About to get AuditLogSummary for Investigation");
				RetrieveSummaryVO summaryVO = new RetrieveSummaryVO();
				theInvestigationAuditLogSummaryVOCollection = new ArrayList<Object>(
						(summaryVO.retrieveInvestigationAuditLogSummaryVO(
								publicHealthCaseUID, nbsSecurityObj)));

				logger.debug("Number of Investigation Auditlog summary found: "
						+ theInvestigationAuditLogSummaryVOCollection.size());
				pageProxyVO
						.setTheInvestigationAuditLogSummaryVOCollection(theInvestigationAuditLogSummaryVOCollection);
			} else {
				logger
						.debug("user has no permission to view InvestigationAuditLogSummaryVO collection");
			}

			// End (civil00014862)

			// Begin support for Document Summary Section
			if (nbsSecurityObj.getPermission(NBSBOLookup.DOCUMENT,
					NBSOperationLookup.VIEW)) {
				RetrieveSummaryVO retrievePhcVaccinations = new RetrieveSummaryVO();
				theDocumentSummaryVOCollection = new ArrayList<Object>(
						retrievePhcVaccinations
								.retrieveDocumentSummaryVOForInv(
										publicHealthCaseUID, nbsSecurityObj)
								.values());
				pageProxyVO
						.setTheDocumentSummaryVOCollection(theDocumentSummaryVOCollection);
			} else {
				logger
						.debug("user has no permission to view DocumentSummaryVO collection");
			}
			if (nbsSecurityObj.getPermission(NBSBOLookup.INTERVIEW,
					NBSOperationLookup.VIEW)) {
				InterviewSummaryDAO interviewSummaryDAO = new InterviewSummaryDAO();
				Collection<Object> interviewCollection = interviewSummaryDAO
						.getInterviewListForInvestigation(publicHealthCaseUID,
								pageProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getProgAreaCd(),								
								nbsSecurityObj);

				pageProxyVO
						.setTheInterviewSummaryDTCollection(interviewCollection);
			} else {
				logger.debug("User has no permission to view Interview Summary collection");
			}			
			if (nbsSecurityObj.getPermission(NBSBOLookup.CT_CONTACT,
					NBSOperationLookup.VIEW)) {
				CTContactSummaryDAO cTContactSummaryDAO = new CTContactSummaryDAO();
				Collection<Object> contactCollection = cTContactSummaryDAO
						.getContactListForInvestigation(publicHealthCaseUID,
								nbsSecurityObj);

				pageProxyVO
						.setTheCTContactSummaryDTCollection(contactCollection);
			} else {
				logger
						.debug("user has no permission to view Contact Summary collection");
			}
			if (nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,
					NBSOperationLookup.VIEW)) {
				NBSAttachmentNoteDAOImpl nbsAttachmentDAO = new NBSAttachmentNoteDAOImpl();
				Collection<Object> nbsCaseAttachmentDTColl = nbsAttachmentDAO.getNbsAttachmentCollection(publicHealthCaseUID);   
				pageProxyVO.setNbsAttachmentDTColl(nbsCaseAttachmentDTColl);  
				Collection<Object>  nbsCaseNotesColl = nbsAttachmentDAO.getNbsNoteCollection(publicHealthCaseUID);
				pageProxyVO.setNbsNoteDTColl(nbsCaseNotesColl);
			} else {
				logger
				.debug("user has no permission to view Investigation : Attachments and Notes are secured by Investigation View Permission");
			}
		}

		} catch (Exception e) {
			logger.fatal(e.getMessage(), e);
			e.printStackTrace();
			throw new java.rmi.RemoteException(e.getMessage(), e);
		}
		return pageProxyVO;
	}

	/**
	 * 
	 * @param investigationUid
	 * @return
	 */
	private Long getPersonUidOfInvestigation(Long investigationUid) {

		Long personUid = null;
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		try {
			String aQuery = WumSqlQuery.SELECT_PATIENT_FOR_INVESTIGATION_DELETION;
			logger.info("Query = " + aQuery);
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(aQuery);
			preparedStmt.setLong(1, investigationUid.longValue());
			preparedStmt.setString(2, NEDSSConstants.PHC_PATIENT);
			preparedStmt.setString(3, NEDSSConstants.PERSON_CLASS_CODE);
			preparedStmt.setString(4, NEDSSConstants.ACTIVE);
			preparedStmt.setString(5, NEDSSConstants.CLASS_CD_CASE);
			resultSet = preparedStmt.executeQuery();

			if (resultSet != null && resultSet.next()) {
				personUid = new Long(resultSet.getLong(1));
				logger.debug("personUid: " + personUid);
			}
		} catch (Exception e) {
			logger
					.fatal(
							"Unable to retrieve PersonUid of Patient Revision for the Vaccination" + e.getMessage(), e);
			logger.debug("This record cannot be deleted");
			throw new NEDSSSystemException(e.getMessage(), e);
		} finally {
			try {
				resultSet.close();
				preparedStmt.close();
				dbConnection.close();
			} catch (SQLException sqlex) {
				sqlex.printStackTrace();
				logger.fatal("SQLExceptionOccured: " + sqlex.getMessage(), sqlex);
				throw new NEDSSSystemException(sqlex.getMessage(), sqlex);
			}
		}
		return personUid;
	}

	/**
	 * 
	 * @param dt
	 * @throws NEDSSSystemException
	 */
	private void insertActRelationshipHistory(ActRelationshipDT dt)
			throws NEDSSSystemException {
		try {
			if (dt != null) {
				ActRelationshipHistoryManager mn = new ActRelationshipHistoryManager(
						dt.getTargetActUid().longValue(), dt.getSourceActUid()
								.longValue(), dt.getTypeCd());
				mn.store(dt);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal(e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
	}

	/**
	 * 
	 * @param dt
	 * @throws NEDSSSystemException
	 */
	private void insertParticipationHistory(ParticipationDT dt)
			throws NEDSSSystemException {
		try {
			if (dt != null) {
				ParticipationHistoryManager man = new ParticipationHistoryManager(
						dt.getSubjectEntityUid().longValue(), dt.getActUid()
								.longValue(), dt.getTypeCd());
				man.store(dt);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal(e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
	}

	/**
	 * Converts negative UIDs to positive UIDs
	 * 
	 * @param pageProxyVO
	 * @param falseUid
	 * @param actualUid
	 */
	private void setFalseToNew(PageActProxyVO pageProxyVO, Long falseUid,
			Long actualUid) {
		try {
			Iterator<Object> anIterator = null;

			ParticipationDT participationDT = null;
			ActRelationshipDT actRelationshipDT = null;
			NbsActEntityDT pamCaseEntityDT = null;
			Collection<Object> participationColl = pageProxyVO
					.getTheParticipationDTCollection();
			Collection<Object> actRelationShipColl = pageProxyVO
					.getPublicHealthCaseVO().getTheActRelationshipDTCollection();
			Collection<Object> pamCaseEntityColl = pageProxyVO.getPageVO()
					.getActEntityDTCollection();
			Long eventUid = null;
			if (participationColl != null) {
				for (anIterator = participationColl.iterator(); anIterator
						.hasNext();) {
					participationDT = (ParticipationDT) anIterator.next();
					logger.debug("(participationDT.getAct() comparedTo falseUid)"
							+ (participationDT.getActUid().compareTo(falseUid)));
					if (participationDT.getActUid().compareTo(falseUid) == 0) {
						participationDT.setActUid(actualUid);
					}
					if (participationDT.getSubjectEntityUid().compareTo(falseUid) == 0) {
						participationDT.setSubjectEntityUid(actualUid);
						
					}
				}
				logger.debug("participationDT.getSubjectEntityUid()"
						+ participationDT.getSubjectEntityUid());
			}
			

			if (actRelationShipColl != null) {
				for (anIterator = actRelationShipColl.iterator(); anIterator
						.hasNext();) {
					actRelationshipDT = (ActRelationshipDT) anIterator.next();

					if (actRelationshipDT.getTargetActUid().compareTo(falseUid) == 0) {
						actRelationshipDT.setTargetActUid(actualUid);
						eventUid=actRelationshipDT.getTargetActUid();
					}
					if (actRelationshipDT.getSourceActUid().compareTo(falseUid) == 0) {
						actRelationshipDT.setSourceActUid(actualUid);
					}
					logger.debug("ActRelationShipDT: falseUid "
							+ falseUid.toString() + " actualUid: " + actualUid);
				}
			}
			if (pamCaseEntityColl != null) {
				for (anIterator = pamCaseEntityColl.iterator(); anIterator
						.hasNext();) {
					pamCaseEntityDT = (NbsActEntityDT) anIterator.next();
					if (pamCaseEntityDT.getEntityUid().compareTo(falseUid) == 0) {
						pamCaseEntityDT.setEntityUid(actualUid);
					}
				}
				logger.debug("pamCaseEntityDT.getSubjectEntityUid()"
						+ pamCaseEntityDT.getEntityUid());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal(e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
		
		
	}

	/**
	 * @param pageProxyVO
	 * @param nbsSecurityObj
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws NEDSSConcurrentDataException
	 * @throws CreateException
	 */
	public Long setPageActProxyVO(PageProxyVO pageProxyVO, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException,
			NEDSSConcurrentDataException, CreateException {
		try {
			return setPageActProxyVO(pageProxyVO, true, nbsSecurityObj);
		}  catch (NEDSSSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal("NEDSSSystemException Occured: " + e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		} catch (ClassCastException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal("ClassCastException Occured: " + e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		} catch (EJBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal("EJBException Occured: " + e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
	}
	
	/**
	 * Description: Stores the PageActProxyVO Object to database
	 * 
	 * @param pageProxyVO
	 * @param nbsSecurityObj
	 * @return public_health_case_uid
	 * @throws java.rmi.RemoteException
	 * @throws javax.ejb.EJBException
	 * @throws javax.ejb.CreateException
	 * @throws NEDSSSystemException
	 * @throws NEDSSConcurrentDataException
	 * @throws CreateException
	 */
	public Long setPageActProxyVO(PageProxyVO pageProxyVO, boolean triggerConfectionLogic, 
			NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException,
			NEDSSConcurrentDataException, CreateException {
		try {
			PageActProxyVO pageActProxyVO = (PageActProxyVO) pageProxyVO;
			Long actualUid = null;
			Long mprUid = null;
			if(!pageActProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().isStdOpenedFromClosed()) {
				PublicHealthCaseDT phcDT = pageActProxyVO.getPublicHealthCaseVO()
						.getThePublicHealthCaseDT();
				 boolean isCoInfectionCondition =pageActProxyVO.getPublicHealthCaseVO().isCoinfectionCondition();		
				 
				// if both are false throw exception
				if ((!pageActProxyVO.isItNew()) && (!pageActProxyVO.isItDirty())) {
					logger.info("pageProxyVO.isItNew() = " + pageActProxyVO.isItNew()
							+ " and pageProxyVO.isItDirty() = "
							+ pageActProxyVO.isItDirty());
					throw new NEDSSSystemException("pageProxyVO.isItNew() = "
							+ pageActProxyVO.isItNew()
							+ " and pageProxyVO.isItDirty() = "
							+ pageActProxyVO.isItDirty() + " for setPageProxy");
				}
				logger.info("pageProxyVO.isItNew() = " + pageActProxyVO.isItNew()
						+ " and pageProxyVO.isItDirty() = "
						+ pageActProxyVO.isItDirty());
	
				if (pageActProxyVO.isItNew()) {
					logger.info("pageProxyVO.isItNew() = " + pageActProxyVO.isItNew()
							+ " and pageProxyVO.isItDirty() = "
							+ pageActProxyVO.isItDirty());
	
					boolean checkInvestigationAutoCreatePermission = nbsSecurityObj
							.getPermission(NBSBOLookup.INVESTIGATION,
									NBSOperationLookup.AUTOCREATE, phcDT
											.getProgAreaCd(),
									ProgramAreaJurisdictionUtil.ANY_JURISDICTION, phcDT
											.getSharedInd());
	
					if (!nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,
							NBSOperationLookup.ADD, phcDT.getProgAreaCd(),
							ProgramAreaJurisdictionUtil.ANY_JURISDICTION, phcDT
									.getSharedInd())
							&& !(checkInvestigationAutoCreatePermission)) {
						logger.info("no add permissions for setPageProxy");
						throw new NEDSSSystemException(
								"NO ADD PERMISSIONS for setPageProxy");
					}
					logger.info("user has add permissions for setPageProxy");
				} else if (pageActProxyVO.isItDirty()) {
					if (!nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,
							NBSOperationLookup.EDIT, phcDT.getProgAreaCd(), phcDT
									.getJurisdictionCd(), phcDT.getSharedInd())) {
						logger.info("no edit permissions for setPageProxy");
						throw new NEDSSSystemException(
								"NO EDIT PERMISSIONS for setPageProxy");
					}
				}
	
				logger.info("pageProxyVO.isItDirty() = " + pageActProxyVO.isItDirty()
						+ " and user has edit permissions for setPageProxy");
				NNDMessageSenderHelper nndMessageSenderHelper = NNDMessageSenderHelper
						.getInstance();
				// uncomment to test autoresend notification
				if (pageActProxyVO.isItDirty() && !pageActProxyVO.isConversionHasModified()) { //If conversion has modified pageActProxyVO then no need to re-queue notifications.
					try {
						// update auto resend notifications
						nndMessageSenderHelper.updateAutoResendNotificationsAsync(
								pageActProxyVO, nbsSecurityObj);
					} catch (Exception e) {
						NNDActivityLogDT nndActivityLogDT = new NNDActivityLogDT();
						String phcLocalId = pageActProxyVO.getPublicHealthCaseVO()
								.getThePublicHealthCaseDT().getLocalId();
						nndActivityLogDT.setErrorMessageTxt(e.toString());
						if (phcLocalId != null)
							nndActivityLogDT.setLocalId(phcLocalId);
						else
							nndActivityLogDT.setLocalId("N/A");
						// catch & store auto resend notifications exceptions in
						// NNDActivityLog table
						nndMessageSenderHelper.persistNNDActivityLog(nndActivityLogDT,
								nbsSecurityObj);
						logger
								.error("Exception occurred while calling nndMessageSenderHelper.updateAutoResendNotificationsAsync");
						e.printStackTrace();
					}
				}
				if (pageActProxyVO.isItNew() && (!pageActProxyVO.isItDirty())) {
					// changes according to new Analysis
					String classCd;
					Long entityUID;
					String recordStatusCd;
					ParticipationDT partDT = null;
					Iterator<Object> partIter = pageActProxyVO
							.getTheParticipationDTCollection().iterator();
	
					while (partIter.hasNext()) {
						partDT = (ParticipationDT) partIter.next();
						entityUID = partDT.getSubjectEntityUid();
	
						if (entityUID != null && entityUID > 0) {
							classCd = partDT.getSubjectClassCd();
							if (classCd != null
									&& classCd
											.compareToIgnoreCase(NEDSSConstants.PERSON) == 0) {
								// Now, get PersonVO from Entity Controller and check if
								// Person is active, if not throw
								// DataConcurrenceException
								EntityController entityController = null;
								NedssUtils nedssUtils = new NedssUtils();
	
								Object obj = nedssUtils
										.lookupBean(JNDINames.EntityControllerEJB);
								logger.debug("EntityController lookup = "
										+ obj.toString());
	
								EntityControllerHome home = (EntityControllerHome) PortableRemoteObject
										.narrow(obj, EntityControllerHome.class);
								logger.debug("Found EntityControllerHome: " + home);
								entityController = home.create();
	
								PersonVO personVO = entityController.getPerson(
										entityUID, nbsSecurityObj);
								recordStatusCd = personVO.getThePersonDT()
										.getRecordStatusCd();
	
								if (recordStatusCd != null
										&& recordStatusCd
												.trim()
												.compareToIgnoreCase(
														NEDSSConstants.RECORD_STATUS_LOGICAL_DELETE) == 0) {
									throw new NEDSSConcurrentDataException(
											"The Person you are trying to create Investigation no Longer exists !!");
								}
							} // if
						} // entityUID > 0
					} // while
				} // if
	
				PersonVO personVO = null;
	
				Iterator<Object> anIterator = null;
				ParticipationDAOImpl participationDAOImpl = null;
				ActRelationshipDAOImpl actRelationshipDAOImpl = null;
				Long falsePublicHealthCaseUid = null;
	
				try {
					NedssUtils nedssUtils = new NedssUtils();
	
					EntityController entityController = null;
					Object obj = nedssUtils.lookupBean(JNDINames.EntityControllerEJB);
					logger.debug("EntityController lookup = " + obj.toString());
					EntityControllerHome home = (EntityControllerHome) PortableRemoteObject
							.narrow(obj, EntityControllerHome.class);
					logger.debug("Found EntityControllerHome: " + home);
					entityController = home.create();
	
					ActController actController = null;
					Object object = nedssUtils.lookupBean(JNDINames.ActControllerEJB);
					logger.debug("ActController lookup = " + object.toString());
					ActControllerHome acthome = (ActControllerHome) PortableRemoteObject
							.narrow(object, ActControllerHome.class);
					logger.debug("Found ActControllerHome: " + acthome);
					actController = acthome.create();
	
					Long falseUid = null;
					Long realUid = null;
					Long patientRevisionUid=null;
					Long phcUid=null;
					if (pageActProxyVO.getThePersonVOCollection() != null) {
						for (anIterator = pageActProxyVO.getThePersonVOCollection()
								.iterator(); anIterator.hasNext();) {
							personVO = (PersonVO) anIterator.next();
							if (personVO.getThePersonDT().getCd()!=null 
									&& personVO.getThePersonDT().getCd().equals(NEDSSConstants.PAT)) {
								mprUid=personVO.getThePersonDT().getPersonParentUid();
							}
							logger.debug("The Base personDT is :"
									+ personVO.getThePersonDT());
							logger.debug("The personUID is :"
									+ personVO.getThePersonDT().getPersonUid());
	
							if (personVO.isItNew()) {
								if (personVO.getThePersonDT().getCd().equals(
										NEDSSConstants.PAT)) { // Patient
									String businessTriggerCd = NEDSSConstants.PAT_CR;
									try {
										realUid = entityController.setPatientRevision(
												personVO, businessTriggerCd,
												nbsSecurityObj);
										patientRevisionUid= realUid;
										phcPatientRevisionUid=patientRevisionUid;
									} catch (NEDSSConcurrentDataException ex) {
										logger
												.fatal("The entity cannot be updated as concurrent access is not allowed!");
										// cntx.setRollbackOnly();
										throw new NEDSSConcurrentDataException(
												"Concurrent access occurred in PageProxyEJB : "
														+ ex.toString());
									} catch (Exception ex) {
										logger
												.fatal("Error in executing entityController.setPatientRevision when personVO.isNew is true");
										ex.printStackTrace();
										throw new javax.ejb.EJBException(
												"Error in entityController.setPatientRevision : "
														+ ex.toString());
									}
								} else if (personVO.getThePersonDT().getCd().equals(
										NEDSSConstants.PRV)) { // Provider
									String businessTriggerCd = NEDSSConstants.PRV_CR;
									try {
										realUid = entityController.setProvider(
												personVO, businessTriggerCd,
												nbsSecurityObj);
									} catch (NEDSSConcurrentDataException ex) {
										logger
												.fatal("The entity cannot be updated as concurrent access is not allowed!");
										// cntx.setRollbackOnly();
										throw new NEDSSConcurrentDataException(
												"Concurrent access occurred in PageProxyEJB : "
														+ ex.toString());
									} catch (Exception ex) {
										logger
												.fatal("Error in executing entityController.setProvider when personVO.isNew is true");
										ex.printStackTrace();
										throw new javax.ejb.EJBException(
												"Error in entityController.setProvider : "
														+ ex.toString());
									}
	
								} // end of else if
	
								falseUid = personVO.getThePersonDT().getPersonUid();
								logger.debug("the  falsePersonUid is " + falseUid);
								logger.debug("The realUid of Patient/Provider is: "
										+ realUid);
								// replace the falseId with the realId
								if (falseUid < 0) {
									logger
											.debug("the Value for false uid for Patient/Provider is being set here");
									setFalseToNew(pageActProxyVO, falseUid, realUid);
								}
							} else if (personVO.isItDirty()) {
								if (personVO.getThePersonDT().getCd().equals(
										NEDSSConstants.PAT)) {
									String businessTriggerCd = NEDSSConstants.PAT_EDIT;
									try {
										realUid = entityController.setPatientRevision(
												personVO, businessTriggerCd,
												nbsSecurityObj);
										patientRevisionUid= realUid;
									} catch (NEDSSConcurrentDataException ex) {
										logger
												.fatal("The entity cannot be updated as concurrent access is not allowed!");
										throw new NEDSSConcurrentDataException(
												"Concurrent access occurred in PageProxyEJB : "
														+ ex.toString());
									} catch (Exception ex) {
										logger
												.fatal("Error in executing entityController.setPatientRevision when personVO.isDirty is true");
										ex.printStackTrace();
										throw new javax.ejb.EJBException(
												"Error in entityController.setPatientRevision : "
														+ ex.toString());
									}
								} else if (personVO.getThePersonDT().getCd().equals(
										NEDSSConstants.PRV)) { // Provider
									String businessTriggerCd = NEDSSConstants.PRV_EDIT;
									try {
										realUid = entityController.setProvider(
												personVO, businessTriggerCd,
												nbsSecurityObj);
									} catch (NEDSSConcurrentDataException ex) {
										logger
												.fatal("The entity cannot be updated as concurrent access is not allowed!");
										throw new NEDSSConcurrentDataException(
												"Concurrent access occurred in PageProxyEJB : "
														+ ex.toString());
									} catch (Exception ex) {
										logger
												.fatal("Error in executing entityController.setProvider when personVO.isDirty is true");
										ex.printStackTrace();
										throw new javax.ejb.EJBException(
												"Error in entityController.setProvider : "
														+ ex.toString());
									}
	
								} // end of else
								logger
										.debug("The realUid for the Patient/Provider is: "
												+ realUid);
	
							}
						} // end of for
						phcDT.setCurrentPatientUid(patientRevisionUid);
					} // end of if(pageProxyVO.getThePersonVOCollection() != null)
	
					if (pageActProxyVO.getPublicHealthCaseVO() != null) {
						String businessTriggerCd = null;
						PublicHealthCaseVO publicHealthCaseVO = pageActProxyVO
								.getPublicHealthCaseVO();
						publicHealthCaseVO.getThePublicHealthCaseDT().setPageCase(true);
						NbsHistoryDAO nbsHistoryDAO = new NbsHistoryDAO();
					if(pageActProxyVO.isItDirty())
						nbsHistoryDAO.getPamHistory(pageActProxyVO
								.getPublicHealthCaseVO());
						PublicHealthCaseDT publicHealthCaseDT = publicHealthCaseVO
								.getThePublicHealthCaseDT();
						if(publicHealthCaseVO.getNbsAnswerCollection()!=null)
							logger.debug("********#publicHealthCaseVO.getNbsAnswerCollection() size from history table: "+publicHealthCaseVO.getNbsAnswerCollection().size());
						if(publicHealthCaseDT.getPublicHealthCaseUid()!=null && publicHealthCaseDT.getVersionCtrlNbr()!=null)
							logger.debug("********#Public Health Case Uid: "+publicHealthCaseDT.getPublicHealthCaseUid().longValue()+"" +" Version: "+publicHealthCaseDT.getVersionCtrlNbr().intValue()+"");
				
						RootDTInterface rootDTInterface = publicHealthCaseDT;
						String businessObjLookupName = NBSBOLookup.INVESTIGATION;
						if (pageActProxyVO.isItNew()) {
							businessTriggerCd = "INV_CR";
							if(isCoInfectionCondition && pageActProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCoinfectionId()==null) {
								logger.debug("AssociatedInvestigationUpdateUtil.updatForConInfectionId created an new coinfection id for the case");
								pageActProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().setCoinfectionId(NEDSSConstants.COINFCTION_GROUP_ID_NEW_CODE);
							}
						} else if (pageActProxyVO.isItDirty()) {
							businessTriggerCd = "INV_EDIT";
							
						}
						String tableName = "PUBLIC_HEALTH_CASE";
						String moduleCd = "BASE";
						PrepareVOUtils prepareVOUtils = new PrepareVOUtils();
						publicHealthCaseDT = (PublicHealthCaseDT) prepareVOUtils
								.prepareVO(rootDTInterface, businessObjLookupName,
										businessTriggerCd, tableName, moduleCd,
										nbsSecurityObj);
						publicHealthCaseVO.setThePublicHealthCaseDT(publicHealthCaseDT);
	
						falsePublicHealthCaseUid = publicHealthCaseVO
								.getThePublicHealthCaseDT().getPublicHealthCaseUid();
						actualUid = actController.setPublicHealthCase(
								publicHealthCaseVO, nbsSecurityObj);
						phcUid= actualUid;
						logger.debug("actualUid = " + actualUid);
						if (falsePublicHealthCaseUid < 0) {
							logger.debug("falsePublicHealthCaseUid = "
									+ falsePublicHealthCaseUid);
							setFalseToNew(pageActProxyVO, falsePublicHealthCaseUid,
									actualUid);
							publicHealthCaseVO.getThePublicHealthCaseDT()
									.setPublicHealthCaseUid(actualUid);
						}
	
						logger.debug("falsePublicHealthCaseUid = "
								+ falsePublicHealthCaseUid);
					}
	
					if (pageActProxyVO.getMessageLogDTMap() != null && !pageActProxyVO.getMessageLogDTMap().isEmpty()) {
						
						
							Set<String> set = pageActProxyVO.getMessageLogDTMap().keySet();
							for (Iterator<String> aIterator = set.iterator(); aIterator.hasNext();) {
								String key =(String)aIterator.next();
								if (key.contains(MessageConstants.DISPOSITION_SPECIFIED_KEY))
									//Investigator of Named by contact will get message for Named by contact and contact's investigation id.
									continue;
								MessageLogDT messageLogDT =(MessageLogDT)pageActProxyVO.getMessageLogDTMap().get(key);
								
								messageLogDT.setPersonUid(patientRevisionUid);
								 if(messageLogDT.getEventUid()!=null && messageLogDT.getEventUid().longValue()>0)
									continue;
								else
									messageLogDT.setEventUid(phcUid);
								
								
						
						}
						MessageLogDAOImpl messageLogDAOImpl =  new MessageLogDAOImpl();
						try {
							messageLogDAOImpl.storeMessageLogDTCollection(pageActProxyVO.getMessageLogDTMap().values());
						} catch (Exception e) {
							logger.error("Unable to store the Error message for = "
									+ falsePublicHealthCaseUid);
						}
					}
						
					// this collection should only be populated in edit scenario, xz
					// defect 11861 (10/01/04)
					if (pageActProxyVO.getTheNotificationSummaryVOCollection() != null) {
						Collection<Object> notSumVOColl = pageActProxyVO
								.getTheNotificationSummaryVOCollection();
						Iterator<Object> notSumIter = notSumVOColl.iterator();
						while (notSumIter.hasNext()) {
							NotificationSummaryVO notSummaryVO = (NotificationSummaryVO) notSumIter
									.next();
							// Only handles notifications that are not history and not
							// in auto-resend status.
							// for auto resend, it'll be handled separately. xz defect
							// 11861 (10/07/04)
							if (notSummaryVO.getIsHistory().equals("F")
									&& notSummaryVO.getAutoResendInd().equals("F")) {
								Long notificationUid = notSummaryVO
										.getNotificationUid();
								String phcCd = phcDT.getCd();
								String phcClassCd = phcDT.getCaseClassCd();
								String progAreaCd = phcDT.getProgAreaCd();
								String jurisdictionCd = phcDT.getJurisdictionCd();
								String sharedInd = phcDT.getSharedInd();
								String notificationRecordStatusCode = notSummaryVO
										.getRecordStatusCd();
								if (notificationRecordStatusCode != null) {
									String trigCd = null;
	
									/*
									 * The notification status remains same when the
									 * Investigation or Associated objects are changed
									 */
									if (notificationRecordStatusCode
											.equalsIgnoreCase(NEDSSConstants.APPROVED_STATUS)) {
										trigCd = NEDSSConstants.NOT_CR_APR;
									}
	
									// change from pending approval to approved
									if (notificationRecordStatusCode
											.equalsIgnoreCase(NEDSSConstants.PENDING_APPROVAL_STATUS)) {
										trigCd = NEDSSConstants.NOT_CR_PEND_APR;
									}
									if (trigCd != null) {
										// we only need to update notification when
										// trigCd is not null
										RetrieveSummaryVO.updateNotification(
												notificationUid, trigCd, phcCd,
												phcClassCd, progAreaCd, jurisdictionCd,
												sharedInd, nbsSecurityObj);
									}
	
								}
							}
						}
					}
					Long docUid = null;
					if (pageActProxyVO.getPublicHealthCaseVO()
							.getTheActRelationshipDTCollection() != null) {
						actRelationshipDAOImpl = (ActRelationshipDAOImpl) NEDSSDAOFactory
								.getDAO(JNDINames.ACT_RELATIONSHIP_DAO_CLASS);
						for (anIterator = pageActProxyVO.getPublicHealthCaseVO()
								.getTheActRelationshipDTCollection().iterator(); anIterator
								.hasNext();) {
							ActRelationshipDT actRelationshipDT = (ActRelationshipDT) anIterator
									.next();
							if (actRelationshipDT.getTypeCd() != null
									&& actRelationshipDT.getTypeCd().equals(
											NEDSSConstants.DocToPHC))
								docUid = actRelationshipDT.getSourceActUid();
							logger.debug("the actRelationshipDT statusTime is "
									+ actRelationshipDT.getStatusTime());
							logger.debug("the actRelationshipDT statusCode is "
									+ actRelationshipDT.getStatusCd());
							logger.debug("Got into The ActRelationship loop");
							try {
								if (actRelationshipDT.isItDelete()) {
									insertActRelationshipHistory(actRelationshipDT);
	
								}
								actRelationshipDAOImpl.store(actRelationshipDT);
								logger
										.debug("Got into The ActRelationship, The ActUid is "
												+ actRelationshipDT.getTargetActUid());
							} catch (Exception e) {
								logger.fatal(nbsSecurityObj.getFullName(), e
										.getMessage(), e);
								e.printStackTrace();
								throw new javax.ejb.EJBException(e.getMessage());
							}
						}
					}
					
					if (pageActProxyVO.getPublicHealthCaseVO()
							.getEdxEventProcessDTCollection() != null) {
						NbsDocumentDAOImpl documentDAO = new NbsDocumentDAOImpl();
						for (EDXEventProcessDT processDT : pageActProxyVO
								.getPublicHealthCaseVO()
								.getEdxEventProcessDTCollection()) {
							if(processDT.getDocEventTypeCd()!=null && processDT.getDocEventTypeCd().equals(NEDSSConstants.CASE))
								processDT.setNbsEventUid(phcUid);
							documentDAO.insertEventProcessDTs(processDT);
							logger.debug("Inserted the event Process for sourceId: "
									+ processDT.getSourceEventId());
						}
					}
					
					/*
					 * Updating the Document table
					 */
					// Getting the DocumentEJB reference
					if (docUid != null) {
						try {
							NbsDocument nbsDocument = null;
							Object docEJB = nedssUtils
									.lookupBean(JNDINames.NBS_DOCUMENT_EJB);
							logger.debug("DocumentEJB lookup = " + docEJB.toString());
							NbsDocumentHome dochome = (NbsDocumentHome) PortableRemoteObject
									.narrow(docEJB, NbsDocumentHome.class);
							logger.debug("Found NbsDocumentHome: " + dochome);
							nbsDocument = dochome.create();
							// get the
							NBSDocumentVO nbsDocVO = nbsDocument
									.getNBSDocumentWithoutActRelationship(docUid,
											nbsSecurityObj);
							if (nbsDocVO.getNbsDocumentDT().getJurisdictionCd() == null
									|| (nbsDocVO.getNbsDocumentDT().getJurisdictionCd() != null && nbsDocVO
											.getNbsDocumentDT().getJurisdictionCd()
											.equals("")))
								nbsDocVO.getNbsDocumentDT().setJurisdictionCd(
										pageActProxyVO.getPublicHealthCaseVO()
												.getThePublicHealthCaseDT()
												.getJurisdictionCd());
						 nbsDocument.updateDocumentWithOutthePatient(nbsDocVO, nbsSecurityObj);
					    } catch (Exception e) {
							logger.error("Error while updating the Document table", e
									.getMessage(), e);
							e.printStackTrace();
							throw new javax.ejb.EJBException(e.getMessage());
						}
					}
	
					if (pageActProxyVO.getTheParticipationDTCollection() != null) {
						logger.debug("got the participation Collection<Object>  Loop");
						participationDAOImpl = (ParticipationDAOImpl) NEDSSDAOFactory
								.getDAO(JNDINames.ACT_PARTICIPATION_DAO_CLASS);
						logger
								.debug("got the participation Collection<Object>  Loop got the DAO");
						for (anIterator = pageActProxyVO
								.getTheParticipationDTCollection().iterator(); anIterator
								.hasNext();) {
							logger
									.debug("got the participation Collection<Object>  FOR Loop");
							ParticipationDT participationDT = (ParticipationDT) anIterator
									.next();
							logger.debug("the participationDT statusTime is "
									+ participationDT.getStatusTime());
							logger.debug("the participationDT statusCode is "
									+ participationDT.getStatusCd());
							logger.debug(" got the participation Loop");
							try {
								if (participationDT.isItDelete()) {
									insertParticipationHistory(participationDT);
	
								}
								participationDAOImpl.store(participationDT);
								logger.debug("got the participationDT, the ACTUID is "
										+ participationDT.getActUid());
								logger
										.debug("got the participationDT, the subjectEntityUid is "
												+ participationDT.getSubjectEntityUid());
							} catch (Exception e) {
								logger.fatal(nbsSecurityObj.getFullName(), e
										.getMessage(), e);
								e.printStackTrace();
								throw new javax.ejb.EJBException(e.getMessage());
							}
						}
					}
					if( pageActProxyVO.isUnsavedNote() && pageActProxyVO.getNbsNoteDTColl()!=null && pageActProxyVO.getNbsNoteDTColl().size()>0){
						PageCaseUtil.storeNotes(actualUid, pageActProxyVO.getNbsNoteDTColl());
						
					}
					if (pageActProxyVO.getPageVO() != null && pageActProxyVO.isItNew()) {
						PamRootDAO pamRootDAO = new PamRootDAO();
						pamRootDAO.insertPamVO(pageActProxyVO.getPageVO(),
								pageActProxyVO.getPublicHealthCaseVO());
					} else if (pageActProxyVO.getPageVO() != null
							&& pageActProxyVO.isItDirty()) {
						PamRootDAO pamRootDAO = new PamRootDAO();
						pamRootDAO.editPamVO(pageActProxyVO.getPageVO(), pageActProxyVO
								.getPublicHealthCaseVO());
	
					} else
						logger
								.error("There is error in setPageActProxyVO as pageProxyVO.getPageVO() is null");
	
					logger.debug("the actual Uid for PageProxy Publichealthcase is "
							+ actualUid);
				} catch (NEDSSConcurrentDataException ex) {
					logger
							.fatal("PageProxyEJB.setInvestigation: The entity cannot be updated as concurrent access is not allowed!");
					throw new NEDSSConcurrentDataException(
							"Concurrent access occurred in PageProxyEJB : "
									+ ex.toString());
				} catch (Exception e) {
					logger.fatal(nbsSecurityObj.getFullName(), e.getMessage(), e);
					e.printStackTrace();
					throw new javax.ejb.EJBException("ActControllerEJB Create : "+e.getMessage()
							+ e.toString());
				}
				
				 if(triggerConfectionLogic && !pageActProxyVO.isRenterant() && pageActProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCoinfectionId()!=null 
						 && !pageActProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getCoinfectionId().
						 equalsIgnoreCase(NEDSSConstants.COINFCTION_GROUP_ID_NEW_CODE) && mprUid!=null 
						 && !pageActProxyVO.isMergeCase() && !NEDSSConstants.INVESTIGATION_STATUS_CODE_CLOSED.equals(pageActProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getInvestigationStatusCd())) { 
					 AssociatedInvestigationUpdateUtil coInfectionUtil = new AssociatedInvestigationUpdateUtil();
					 coInfectionUtil.updatForConInfectionId(pageActProxyVO, mprUid, actualUid, nbsSecurityObj);
				 }
				 
					if(pageActProxyVO.getPublicHealthCaseVO().getTheCaseManagementDT()!=null) {
						boolean isStdHivProgramAreaCode =PropertyUtil.isStdOrHivProgramArea(pageProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getProgAreaCd());
					    if(isStdHivProgramAreaCode)
					    	updateNamedAsContactDisposition(pageActProxyVO.getPublicHealthCaseVO().getTheCaseManagementDT(),nbsSecurityObj);
					 
					}
			} else {
				//First update nbs_case_answer, so that while reading pageActProxyVo to update co-infection questions it should read updated nbs_case_answer non coinfection answers.
				PamRootDAO pamRootDAO = new PamRootDAO();
				pamRootDAO.editPamVO(pageActProxyVO.getPageVO(), pageActProxyVO
						.getPublicHealthCaseVO());
				PersonVO personVO = null;
				Iterator<Object> anIterator = null;
				if (pageActProxyVO.getThePersonVOCollection() != null) {
					for (anIterator = pageActProxyVO.getThePersonVOCollection()
							.iterator(); anIterator.hasNext();) {
						personVO = (PersonVO) anIterator.next();
						if (personVO.getThePersonDT().getCd()!=null 
								&& personVO.getThePersonDT().getCd().equals(NEDSSConstants.PAT)) {
							mprUid=personVO.getThePersonDT().getPersonParentUid();
						}
					}
				}
				actualUid = pageActProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid();
				AssociatedInvestigationUpdateUtil coInfectionUtil = new AssociatedInvestigationUpdateUtil();
				coInfectionUtil.updatForConInfectionId(pageActProxyVO, mprUid, actualUid, nbsSecurityObj);
				
			}
			return actualUid;
		} catch (NEDSSSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal("NEDSSSystemException Occured: " + e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		} catch (ClassCastException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal("ClassCastException Occured: " + e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		} catch (EJBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal("EJBException Occured: " + e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
	}

	/**
	 * 
	 * @param pageProxyVO
	 * @param observationUid
	 * @param observationTypeCd
	 * @param nBSSecurityObj
	 * @return
	 * @throws RemoteException
	 * @throws javax.ejb.EJBException
	 * @throws javax.ejb.CreateException
	 * @throws java.rmi.RemoteException
	 * @throws NEDSSConcurrentDataException
	 * @throws NEDSSSystemException
	 * @throws NEDSSConcurrentDataException
	 * @throws CreateException
	 */
	public Long setPageProxyWithAutoAssoc(PageProxyVO pageProxyVO,
			Long observationUid, String observationTypeCd, String processingDecision,
			NBSSecurityObj nBSSecurityObj) throws RemoteException,
			NEDSSConcurrentDataException, CreateException {
		Long publicHealthCaseUID;
		try {
			publicHealthCaseUID = setPageActProxyVO(pageProxyVO, nBSSecurityObj);
			Collection<Object> observationColl = new ArrayList<Object>();
			if (observationTypeCd
					.equalsIgnoreCase(NEDSSConstants.LAB_DISPALY_FORM)) {
				LabReportSummaryVO labSumVO = new LabReportSummaryVO();
				labSumVO.setItTouched(true);
				labSumVO.setItAssociated(true);
				labSumVO.setObservationUid(observationUid);
				//set the add_reason_code(processing decision) for act_relationship  from initial follow-up(pre-populated from Lab report processing decision) field in case management
				if(pageProxyVO.getPublicHealthCaseVO().getTheCaseManagementDT()!=null && pageProxyVO.getPublicHealthCaseVO().getTheCaseManagementDT().getInitFollUp()!=null)
					labSumVO.setProcessingDecisionCd(pageProxyVO.getPublicHealthCaseVO().getTheCaseManagementDT().getInitFollUp());
				else
					labSumVO.setProcessingDecisionCd(processingDecision);
				observationColl.add(labSumVO);

			} else {
				MorbReportSummaryVO morbSumVO = new MorbReportSummaryVO();
				morbSumVO.setItTouched(true);
				morbSumVO.setItAssociated(true);
				morbSumVO.setObservationUid(observationUid);
				//set the add_reason_code(processing decision) for act_relationship  from initial follow-up(pre-populated from Morb report processing decision) field in case management 
				if(pageProxyVO.getPublicHealthCaseVO().getTheCaseManagementDT()!=null && pageProxyVO.getPublicHealthCaseVO().getTheCaseManagementDT().getInitFollUp()!=null)
					morbSumVO.setProcessingDecisionCd(pageProxyVO.getPublicHealthCaseVO().getTheCaseManagementDT().getInitFollUp());
				else
					morbSumVO.setProcessingDecisionCd(processingDecision);
				observationColl.add(morbSumVO);

			}
		    boolean isStdHivProgramAreaCode =PropertyUtil.isStdOrHivProgramArea(pageProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getProgAreaCd());
			/*StringTokenizer st2 = new StringTokenizer(
					properties.getSTDProgramAreas(), ",");
			if (st2 != null) {
			while (st2.hasMoreElements()) {
				if (st2.nextElement().equals(pageProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getProgAreaCd())) {
					isProgramAreaCode= true;
					break;
					}
				}
			}*/
			//if(isStdHivProgramAreaCode)
				//RetrieveSummaryVO.createAndStoreMesssageLogDTCollection( observationColl,pageProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT(), nBSSecurityObj);
			ManageAutoAssociations manageAutoAssoc = new ManageAutoAssociations();
			manageAutoAssoc.setObservationAssociationsImpl(publicHealthCaseUID,
					observationColl, nBSSecurityObj, true);
		} catch (RemoteException e) {
			logger
					.fatal("PageCaseUtil.setPageProxyWithAutoAssoc RemoteException thrown"
							+ e.getMessage(),e);
			throw new RemoteException(e.getMessage(),e);
		} catch (NEDSSConcurrentDataException e) {
			logger
					.fatal("PageCaseUtil.setPageProxyWithAutoAssoc NEDSSConcurrentDataException thrown"
							+ e.getMessage(),e);
			throw new NEDSSConcurrentDataException(e.getMessage(),e);
		} catch (EJBException e) {
			logger
					.fatal("PageCaseUtil.setPageProxyWithAutoAssoc EJBException thrown"
							+ e.getMessage(),e);
			throw new EJBException(e.getMessage(),e);
		} catch (CreateException e) {
			logger
					.fatal("PageCaseUtil.setPageProxyWithAutoAssoc CreateException thrown"
							+ e.getMessage(),e);
			throw new CreateException(e.getMessage());
		}
		return publicHealthCaseUID;
	}

	public void setSessionContext(SessionContext sc)
			throws java.rmi.RemoteException, javax.ejb.EJBException {
	}

	
	/**
	 * 
	 * @param publicHealthCaseUID
	 * @param newJurisdictionCode
	 * @param nbsSecurityObj
	 * @throws javax.ejb.EJBException
	 * @throws NEDSSSystemException
	 */
	public void changeCondition(Long publicHealthCaseUID,
			PageActProxyVO pageProxyVO, NBSSecurityObj nbsSecurityObj)
			throws javax.ejb.EJBException, NEDSSSystemException {
		String programAreaCode=pageProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getProgAreaCd();
		Long jurisdictionOid=pageProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getProgramJurisdictionOid();
		try{
			CTContactDAOImpl ctContactDAO =  new CTContactDAOImpl();
			ctContactDAO.updateContactsForChangeCondition(publicHealthCaseUID, programAreaCode, jurisdictionOid);
			updateCaseNotificationForChangeConditionToNonNNDCondition(pageProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT());
			
	       } catch (NEDSSSystemException e) {
				logger.fatal("NEDSSSystemException occured in PageCaseUtil.changeCondition"+e.getMessage(), e);
				throw new NEDSSSystemException(e.getMessage(),e);
	       }catch (Exception e) {
				logger.fatal("Exception occured: nbsSecurityObj.getFullName(): " + nbsSecurityObj.getFullName(), e.getMessage(), e);
				e.printStackTrace();
				throw new javax.ejb.EJBException(e.getMessage(),e);
			}
	}
	
	/**
	 * 
	 * @param publicHealthCaseUID
	 * @param newJurisdictionCode
	 * @param nbsSecurityObj
	 * @throws javax.ejb.EJBException
	 * @throws NEDSSSystemException
	 * @throws NEDSSConcurrentDataException
	 */
	public void transferOwnership(Long publicHealthCaseUID,
			PageActProxyVO pageProxyVO, String newJurisdictionCode,
			Boolean isExportCase, NBSSecurityObj nbsSecurityObj)
			throws javax.ejb.EJBException, NEDSSSystemException,
			NEDSSConcurrentDataException {
		try {
			ActController actController = null;
			ObservationProxy observationProxy = null;
			PublicHealthCaseDT publicHealthCaseDT = null;
			PublicHealthCaseDT newPublicHealthCaseDT = null;

			NedssUtils nedssUtils = new NedssUtils();
			PrepareVOUtils prepareVOUtils = new PrepareVOUtils();
			Object object = nedssUtils.lookupBean(JNDINames.ActControllerEJB);
			logger.debug("ActController lookup = " + object.toString());
			ActControllerHome acthome = (ActControllerHome) PortableRemoteObject
					.narrow(object, ActControllerHome.class);
			logger.debug("Found ActControllerHome: " + acthome);
			actController = acthome.create();
			publicHealthCaseDT = actController.getPublicHealthCaseInfo(
					publicHealthCaseUID, nbsSecurityObj);
			publicHealthCaseDT.setPageCase(true);
			NbsHistoryDAO nbsHistoryDAO = new NbsHistoryDAO();
			nbsHistoryDAO.getPamHistory(pageProxyVO.getPublicHealthCaseVO());

			if (!nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,
					NBSOperationLookup.TRANSFERPERMISSIONS, publicHealthCaseDT
							.getProgAreaCd(), publicHealthCaseDT
							.getJurisdictionCd(), publicHealthCaseDT
							.getSharedInd())) {
				logger.info("no add permissions for transferOwnership");
				throw new NEDSSSystemException(
						"NO ADD PERMISSIONS for transferOwnership");
			}
			logger.info("user has add permissions for setPageProxy");
			java.util.Date dateTime = new java.util.Date();
			Timestamp systemTime = new Timestamp(dateTime.getTime());
			publicHealthCaseDT.setItDirty(true);
			publicHealthCaseDT.setJurisdictionCd(newJurisdictionCode);

			publicHealthCaseDT.setLastChgTime(systemTime);
			newPublicHealthCaseDT = (PublicHealthCaseDT) prepareVOUtils
					.prepareVO(publicHealthCaseDT, NBSBOLookup.INVESTIGATION,
							NEDSSConstants.INV_EDIT,
							DataTables.PUBLIC_HEALTH_CASE_TABLE,
							NEDSSConstants.BASE, nbsSecurityObj);
			pageProxyVO.getPublicHealthCaseVO().setThePublicHealthCaseDT(
					newPublicHealthCaseDT);

			actController.setPublicHealthCase(pageProxyVO
					.getPublicHealthCaseVO(), nbsSecurityObj);
			// actController.setPublicHealthCaseInfo(newPublicHealthCaseDT,nbsSecurityObj);
			pageProxyVO.getPublicHealthCaseVO().setThePublicHealthCaseDT(
					publicHealthCaseDT);
			Collection<Object> actRelationShips = actController
					.getActRelationships(publicHealthCaseUID, nbsSecurityObj);

			if (actRelationShips != null) {
				Iterator<Object> it = actRelationShips.iterator();
				while (it.hasNext()) {
					ActRelationshipDT actRelationshipDT = (ActRelationshipDT) it
							.next();
					if ((actRelationshipDT.getTypeCd()
							.equalsIgnoreCase("LabReport"))
							|| (actRelationshipDT.getTypeCd()
									.equalsIgnoreCase("MorbReport"))) {
						if (eventIsDocument(actRelationshipDT, pageProxyVO))
							continue; //per Jit no need to transfer lab/morb doc
						Object objObject = nedssUtils
								.lookupBean(JNDINames.OBSERVATION_PROXY_EJB);
						ObservationProxyHome observationProxyhome = (ObservationProxyHome) PortableRemoteObject
								.narrow(objObject, ObservationProxyHome.class);
						logger.debug("Found observationProxyHome: " + acthome);
						observationProxy = observationProxyhome.create();
						observationProxy.transferOwnership(actRelationshipDT
								.getSourceActUid(), null, newJurisdictionCode,
								NEDSSConstants.CASCADING, nbsSecurityObj);
					} else if (actRelationshipDT.getTypeCd().equalsIgnoreCase(
							NEDSSConstants.DocToPHC)) {
						Long docUid = actRelationshipDT.getSourceActUid();
						if (docUid != null) {
							try {
								NbsDocument nbsDocument = null;
								Object docEJB = nedssUtils
										.lookupBean(JNDINames.NBS_DOCUMENT_EJB);
								logger.debug("DocumentEJB lookup = "
										+ docEJB.toString());
								NbsDocumentHome dochome = (NbsDocumentHome) PortableRemoteObject
										.narrow(docEJB, NbsDocumentHome.class);
								logger.debug("Found NbsDocumentHome: "
										+ dochome);
								nbsDocument = dochome.create();
								// get the
								nbsDocument.transferOwnership(docUid, null,
										newJurisdictionCode, nbsSecurityObj);
							} catch (Exception e) {
								logger
										.error(
												"Error while updating the Document table",
												e.getMessage(), e);
								e.printStackTrace();
								throw new javax.ejb.EJBException(e.getMessage());
							}
						}
					} else if (actRelationshipDT.getTypeCd().equalsIgnoreCase(
							NEDSSConstants.ACT106_TYP_CD)) {
						NotificationDT notificationDT = actController
								.getNotificationInfo(actRelationshipDT
										.getSourceActUid(), nbsSecurityObj);
						String trigCd = NEDSSConstants.NOT_EDIT;
						// notificationDT.setItNew(false);
						RetrieveSummaryVO.updateNotification(actRelationshipDT
								.getSourceActUid(), trigCd, notificationDT
								.getCaseConditionCd(), notificationDT
								.getCaseClassCd(), notificationDT
								.getProgAreaCd(), newJurisdictionCode,
								notificationDT.getSharedInd(), nbsSecurityObj);
					}
				}
			}
			PamRootDAO pamRootDAO = new PamRootDAO();
			pamRootDAO.UpdateFordeletePamVO(pageProxyVO.getPageVO(),
					pageProxyVO.getPublicHealthCaseVO());

		} catch (NEDSSConcurrentDataException ex) {
			logger
					.fatal("PageProxyEJB.transferOwnership: The entity cannot be updated as concurrent access is not allowed!");
			throw new NEDSSConcurrentDataException(ex.getMessage(),ex);
		} catch (Exception e) {
			logger.fatal(nbsSecurityObj.getFullName(), e.getMessage(), e);
			e.printStackTrace();
			throw new javax.ejb.EJBException(e.getMessage(),e);
		}

	}


	private Long transferOwnershipforExport(NotificationProxyVO notProxyVO,
			String newJurisdictionCode, NBSSecurityObj nbsSecurityObj)
			throws javax.ejb.EJBException, NEDSSSystemException,
			NEDSSConcurrentDataException {
		Long newNotficationUid = new Long(0);
		try {
			ActController actController = null;
			PublicHealthCaseDT publicHealthCaseDT = null;
			PublicHealthCaseDT newPublicHealthCaseDT = null;
			NedssUtils nedssUtils = new NedssUtils();
			PrepareVOUtils prepareVOUtils = new PrepareVOUtils();
			Object object = nedssUtils.lookupBean(JNDINames.ActControllerEJB);
			logger.debug("ActController lookup = " + object.toString());
			ActControllerHome acthome = (ActControllerHome) PortableRemoteObject
					.narrow(object, ActControllerHome.class);
			logger.debug("Found ActControllerHome: " + acthome);
			actController = acthome.create();

			publicHealthCaseDT = actController.getPublicHealthCaseInfo(
					notProxyVO.getThePublicHealthCaseVO()
							.getThePublicHealthCaseDT()
							.getPublicHealthCaseUid(), nbsSecurityObj);

			if (!nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,
					NBSOperationLookup.TRANSFERPERMISSIONS, publicHealthCaseDT
							.getProgAreaCd(), publicHealthCaseDT
							.getJurisdictionCd(), publicHealthCaseDT
							.getSharedInd())) {
				logger.info("no add permissions for setInvestigationProxy");
				throw new NEDSSSystemException(
						"NO ADD PERMISSIONS for transferOwnership");
			}

			logger.info("user has add permissions for setInvestigationProxy");

			publicHealthCaseDT.setItDirty(true);
			// Do not update the public health case jurisdiction when you do
			// export
			// publicHealthCaseDT.setJurisdictionCd(newJurisdictionCode);
			newPublicHealthCaseDT = (PublicHealthCaseDT) prepareVOUtils
					.prepareVO(publicHealthCaseDT, NBSBOLookup.INVESTIGATION,
							NEDSSConstants.INV_EDIT,
							DataTables.PUBLIC_HEALTH_CASE_TABLE,
							NEDSSConstants.BASE, nbsSecurityObj);

			actController.setPublicHealthCaseInfo(newPublicHealthCaseDT,
					nbsSecurityObj);
			Collection<Object> actRelationShips = actController
					.getActRelationships(notProxyVO.getThePublicHealthCaseVO()
							.getThePublicHealthCaseDT()
							.getPublicHealthCaseUid(), nbsSecurityObj);

			NotificationVO notificationVO = notProxyVO.getTheNotificationVO();
			if (actRelationShips != null) {
				Iterator<Object> it = actRelationShips.iterator();
				while (it.hasNext()) {
					ActRelationshipDT actRelationshipDT = (ActRelationshipDT) it
							.next();
					if (actRelationshipDT.getTypeCd().equalsIgnoreCase(
							NEDSSConstants.ACT106_TYP_CD)) {

						NotificationDT notificationDT = actController
								.getNotificationInfo(actRelationshipDT
										.getSourceActUid(), nbsSecurityObj);

						// Call the method here
						if (notificationDT.getCd().equalsIgnoreCase(NEDSSConstants.CLASS_CD_EXP_NOTF) ||
								notificationDT.getCd().equalsIgnoreCase(NEDSSConstants.CLASS_CD_EXP_NOTF_PHDC)	) {
							if (notificationDT.getRecordStatusCd().equals(
									NEDSSConstants.NOTIFICATION_REJECTED_CODE)) {
								String trigCd = NEDSSConstants.NOT_EDIT;
								notificationVO = updateNotificationforExport(
										actRelationshipDT.getSourceActUid(),
										trigCd,
										notificationDT.getCaseConditionCd(),
										notificationDT.getCaseClassCd(),
										notificationDT.getProgAreaCd(),
										notProxyVO.getTheNotificationVO()
												.getTheNotificationDT()
												.getJurisdictionCd(),
										notificationDT.getSharedInd(),
										notProxyVO
												.getTheNotificationVO()
												.getTheNotificationDT()
												.getExportReceivingFacilityUid(),
										notProxyVO.getTheNotificationVO()
												.getTheNotificationDT()
												.getTxt(), nbsSecurityObj);
								newNotficationUid = actController
										.setNotification(notificationVO,
												nbsSecurityObj);

							}

						}

					}
				}

			}
		} catch (NEDSSConcurrentDataException ex) {
			logger
					.fatal("InvestigationProxyEJB.transferOwnership: The entity cannot be updated as concurrent access is not allowed!");
			throw new NEDSSConcurrentDataException(ex.getMessage(),ex);
		} catch (Exception e) {
			logger.fatal(nbsSecurityObj.getFullName()+ e.getMessage(), e);
			e.printStackTrace();
			throw new javax.ejb.EJBException(e.getMessage(),e);
		}
		return newNotficationUid;

	}

	private static NotificationVO updateNotificationforExport(
			Long notificationUid, String businessTriggerCd, String phcCd,
			String phcClassCd, String progAreaCd, String jurisdictionCd,
			String sharedInd, Long recFacilithyUid, String comment,
			NBSSecurityObj nbsSecurityObj) {

		NedssUtils nedssUtils = new NedssUtils();
		Object theLookedUpObject = nedssUtils.lookupBean(JNDINames.ActControllerEJB);
		try {
			ActControllerHome ecHome = (ActControllerHome) PortableRemoteObject
					.narrow(theLookedUpObject, ActControllerHome.class);
			ActController actController = ecHome.create();
			NotificationVO notificationVO = actController.getNotification(
					notificationUid, nbsSecurityObj);

			PrepareVOUtils prepareVOUtils = new PrepareVOUtils();
			NotificationDT newNotificationDT = null;
			NotificationDT notificationDT = notificationVO
					.getTheNotificationDT();
			notificationDT.setProgAreaCd(progAreaCd);
			notificationDT.setJurisdictionCd(jurisdictionCd);
			notificationDT.setCaseConditionCd(phcCd);
			notificationDT.setSharedInd(sharedInd);
			notificationDT.setCaseClassCd(phcClassCd);
			notificationDT.setExportReceivingFacilityUid(recFacilithyUid);
			notificationDT
					.setRecordStatusCd(NEDSSConstants.NOTIFICATION_PENDING_CODE);
			notificationDT.setTxt(comment);
			notificationDT.setItDirty(true);
			notificationVO.setItDirty(true);

			// retreive the new NotificationDT generated by PrepareVOUtils
			newNotificationDT = (NotificationDT) prepareVOUtils.prepareVO(
					notificationDT, NBSBOLookup.NOTIFICATION,
					businessTriggerCd, DataTables.NOTIFICATION_TABLE,
					NEDSSConstants.BASE, nbsSecurityObj);
			newNotificationDT
					.setRecordStatusCd(NEDSSConstants.NOTIFICATION_PENDING_CODE);

			// replace old NotificationDT in NotificationVO with the new
			// NotificationDT
			notificationVO.setTheNotificationDT(newNotificationDT);
			return notificationVO;

		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal("Exception calling ActController.setNotification() "
					+ e.getMessage(),e);
			throw new NEDSSSystemException(e.getMessage(),e);
		}

	}

	public Long exportOwnership(NotificationProxyVO notProxyVO,
			String newJurisdictionCode, NBSSecurityObj nbsSecurityObj)
			throws javax.ejb.EJBException, NEDSSSystemException,
			NEDSSConcurrentDataException {
		try {
			Long sourceActUid = transferOwnershipforExport(notProxyVO,
					newJurisdictionCode, nbsSecurityObj);
			if (sourceActUid.compareTo(new Long(0)) == 0) {
				ActController actController = null;
				NedssUtils nedssUtils = new NedssUtils();
				Object object = nedssUtils.lookupBean(JNDINames.ActControllerEJB);
				logger.debug("ActController lookup = " + object.toString());
				ActControllerHome acthome = (ActControllerHome) PortableRemoteObject
						.narrow(object, ActControllerHome.class);
				logger.debug("Found ActControllerHome: " + acthome);

				try {
					actController = acthome.create();
					sourceActUid = actController.setNotification(notProxyVO
							.getTheNotificationVO(), nbsSecurityObj);
				} catch (Exception e) {
					logger.fatal(nbsSecurityObj.getFullName(), e.getMessage(), e);
					e.printStackTrace();
					throw new javax.ejb.EJBException(e.getMessage());
				}

				ActRelationshipDAOImpl actRelationshipDAOImpl = new ActRelationshipDAOImpl();
				if (notProxyVO.getTheActRelationshipDTCollection() != null) {
					actRelationshipDAOImpl = (ActRelationshipDAOImpl) NEDSSDAOFactory
							.getDAO(JNDINames.ACT_RELATIONSHIP_DAO_CLASS);
					Iterator<Object> anIterator = notProxyVO
							.getTheActRelationshipDTCollection().iterator();
					while (anIterator.hasNext()) {
						ActRelationshipDT actRelationshipDT = (ActRelationshipDT) anIterator
								.next();

						try {
							actRelationshipDT.setSourceActUid(sourceActUid);
							actRelationshipDAOImpl.store(actRelationshipDT);
							logger
									.debug("Got into The ActRelationship, The ActUid is "
											+ actRelationshipDT.getTargetActUid());
						} catch (Exception e) {
							logger.fatal(nbsSecurityObj.getFullName(), e
									.getMessage(), e);
							e.printStackTrace();
							throw new javax.ejb.EJBException(e.getMessage());
						}
					}
				}
			}// if it is an update notification
			return notProxyVO.getThePublicHealthCaseVO().getThePublicHealthCaseDT()
					.getPublicHealthCaseUid();
		} catch (ClassCastException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal("ClassCastException occured, newJurisdictionCode: " + newJurisdictionCode + ", "
					+ e.getMessage(),e);
			throw new NEDSSSystemException(e.getMessage(),e);
		}
	}

	private PamProxy getPamProxyEJBRemoteInterface() throws EJBException {

		PamProxy pamProxy = null;
		try {

			logger.debug("You are in the getRemoteInterface() method");
			NedssUtils nu = new NedssUtils();
			Object obj = nu.lookupBean(JNDINames.PAM_PROXY_EJB);
			PamProxyHome pamProxyHome = (PamProxyHome) javax.rmi.PortableRemoteObject
					.narrow(obj, PamProxyHome.class);
			pamProxy = pamProxyHome.create();
		} catch (Exception e) {
			logger
					.fatal(
							"Error while creating a getPamProxyEJBRemoteInterface  in PageCaseUtil.",
							e.getMessage(),e);
			throw new EJBException(e.getMessage(),e);
		}
		return pamProxy;
	} // getPamProxyEJBRemoteInterface
	
    /**
     * setAssociations - used by change condition to copy the associations from one case to another
     *  Note that if the program area changes, the change is not cascaded to the lab or morb report
     * @param investigationUID - new investigation to add associations to
     * @param pageProxyVO - contains collections that need to be added
     * @param securityObj
     */
	public void setAssociations(Long investigationUID, PageActProxyVO pageProxyVO,
			  NBSSecurityObj nbsSecurityObj) throws
			  java.rmi.RemoteException, javax.ejb.EJBException,
			  javax.ejb.CreateException, NEDSSConcurrentDataException{
		      ManageAutoAssociations manageAutoAssc = new ManageAutoAssociations();
		      Collection<Object> reportSumVOCollection = pageProxyVO.getTheLabReportSummaryVOCollection();
		      reportSumVOCollection.addAll(pageProxyVO.getTheMorbReportSummaryVOCollection());
		      
			 try {
			  if(reportSumVOCollection!=null){
				  manageAutoAssc.setObservationAssociationsImpl(investigationUID,
						  reportSumVOCollection, nbsSecurityObj);
			  }
			  if(pageProxyVO.getTheVaccinationSummaryVOCollection()!=null){
				  manageAutoAssc.setVaccinationAssociationsImpl(investigationUID,
						  pageProxyVO.getTheVaccinationSummaryVOCollection(), nbsSecurityObj);
			  }
			  if(pageProxyVO.getTheDocumentSummaryVOCollection()!=null){
				  manageAutoAssc.setDocumentAssociationsImpl(investigationUID,
						  pageProxyVO.getTheDocumentSummaryVOCollection(), nbsSecurityObj);
			  }
			  if(pageProxyVO.getTheTreatmentSummaryVOCollection()!=null){
				  manageAutoAssc.setTreatmentAssociationsImpl(investigationUID,NEDSSConstants.INVESTIGATION,
						  pageProxyVO.getTheTreatmentSummaryVOCollection(), nbsSecurityObj);
			  }
			  
		  }catch (Exception e) {
			  logger.error("setAssoications:Exception occurred while calling nndMessageSenderHelper.updateAutoResendNotificationsAsync" + e.getMessage(),e);
			  e.printStackTrace();
			  throw new EJBException(e.getMessage(),e);
		  }
	  }
	 
    /**
     * deleteExistingAssociations - physically delete existing associations, used by change condition
     *   Note that Notifications are not deleted.
     * @param publicHealthCase to delete associations for, deletes all the act relationships where target is this UID
     * @param pageProxyVO
     * @param securityObj
     */
	public void deleteExistingAssociations(Long investigationUID,  NBSSecurityObj nbsSecurityObj) throws
			  NEDSSDAOSysException, NEDSSSystemException{
		      ActRelationshipDAOImpl actRelationshipDAOImpl = new  ActRelationshipDAOImpl();
		      
				actRelationshipDAOImpl = (ActRelationshipDAOImpl) NEDSSDAOFactory
				.getDAO(JNDINames.ACT_RELATIONSHIP_DAO_CLASS);

			try {
				actRelationshipDAOImpl.removeAllActRelationshipsButNotifications(investigationUID);
				logger.debug("Removed act relationships (except Notifications) for case  " + investigationUID);
			} catch (Exception e) {
				logger.fatal("nbsSecurityObj.getFullName: " + nbsSecurityObj.getFullName() + e.getMessage(),e);
				e.printStackTrace();
				 throw new EJBException(e.getMessage(),e);
			}
		}
	
    /**
     * updateContactsForChangeCondition - update the patient contact to the new revision for change condition, 
     *   also update the CT investigation to the new investigation
     *   and the Program Area in case it changed
     * @param oldInvestigationUID - old investigation with old condition
     * @param newInvestigationUID - newly created investigation with new condition
     * @param participationCollection - contains the patient revision
     * @param programArea - this could change with the new condition
     * @param jurisdiction - needed to calculate the OID
     */
	public void updateContactsForChangeCondition(Long oldInvestigationUID, 
			Long newInvestigationUID,Collection<Object> participationColl,
			String programArea, String jurisdiction)throws
			  NEDSSDAOSysException, NEDSSSystemException{
		try {
			Long revisionUid = null;
			if(participationColl!=null && participationColl.size()>0){
				Iterator<Object> ite = participationColl.iterator();
				while(ite.hasNext()){
					ParticipationDT pDT = (ParticipationDT)ite.next();
				if(pDT!=null && pDT.getTypeCd().equals(NEDSSConstants.PHC_PATIENT)){
					revisionUid = pDT.getSubjectEntityUid();
					break;
				}
				
				}
			}
			long pajHash = ProgramAreaJurisdictionUtil.getPAJHash(programArea, jurisdiction);
			Long programJurisOid = new Long(pajHash);
			logger.debug("programJurisOid is : " + programJurisOid);

			CTContactRootDAO ctDAO = (CTContactRootDAO) NEDSSDAOFactory
			.getDAO(JNDINames.CTCONTACT_ANSWER_DAO);
			ctDAO.manageContactsForChangeCondition(oldInvestigationUID,newInvestigationUID,
					revisionUid, programArea, programJurisOid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("oldInvestigationUID: " + oldInvestigationUID + ", newInvestigationUID: " + newInvestigationUID + ", " + e.getMessage(),e);
			e.printStackTrace();
			 throw new EJBException(e.getMessage(),e);
		}
		
	}
	 /**
     * updateContactsForMergeInvestigation - update the patient contact to the new revision after Merge Investigations, 
     *   also update the CT investigation to the new investigation
     * @param oldInvestigationUID - old investigation with old condition
     * @param newInvestigationUID - newly created investigation with new condition
     * @param participationCollection - contains the patient revision
     */
	/**PKS : method not required
	public void updateContactsForMergeInvestigation(Long oldInvestigationUID, 
			Long newInvestigationUID,Collection<Object> participationColl)throws
			  NEDSSDAOSysException, NEDSSSystemException{
		try {
			Long revisionUid = null;
			if(participationColl!=null && participationColl.size()>0){
				Iterator<Object> ite = participationColl.iterator();
				while(ite.hasNext()){
					ParticipationDT pDT = (ParticipationDT)ite.next();
					if(pDT!=null && pDT.getTypeCd().equals(NEDSSConstants.PHC_PATIENT)){
						revisionUid = pDT.getSubjectEntityUid();
						break;
					}
				}
			}
			CTContactRootDAO ctDAO = (CTContactRootDAO) NEDSSDAOFactory.getDAO(JNDINames.CTCONTACT_ANSWER_DAO);
			ctDAO.manageContactsForMergeInvestigation(oldInvestigationUID,newInvestigationUID,
					revisionUid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("oldInvestigationUID: " + oldInvestigationUID + ", newInvestigationUID: " + newInvestigationUID + ", " + e.getMessage(),e);
			e.printStackTrace();
			 throw new EJBException(e.getMessage(),e);
		}
		
	}*/
	   /**
     * updateNotesForChangeCondition - update any notes in the NBS_Note table with the new UID
     * 
     * @param oldInvestigationUID - old investigation with old condition
     * @param newInvestigationUID - newly created investigation with new condition
     */
	public int updateNotesForChangeCondition(Long oldInvestigationUID, 
			Long newInvestigationUID)throws
			  NEDSSDAOSysException, NEDSSSystemException{
		
			int updateCount;
		try{
			NBSAttachmentNoteDAOImpl nbsAttachmentNoteDao  =  new NBSAttachmentNoteDAOImpl();
			updateCount =  nbsAttachmentNoteDao.updateNbsNoteCase(oldInvestigationUID, newInvestigationUID);
			if (updateCount > 0)
				logger.debug(updateCount + " Notes were migrated for Change Condition");
		}catch(Exception e){
			logger.fatal("Error in updating the record for NBS_Note : oldInvestigationUID: " + oldInvestigationUID + ", newInvestigationUID: " + newInvestigationUID + ", " + e.getMessage(),e);
			e.printStackTrace();
			throw new NEDSSSystemException(e.getMessage(),e);
		}
		return updateCount;
	}
	   /**
     * updateAttahmentsForChangeCondition - update any attachments in the NBS_attachment table with the new UID
     * 
     * @param oldInvestigationUID - old investigation with old condition
     * @param newInvestigationUID - newly created investigation with new condition
     */
	public int updateAttachmentsForChangeCondition(Long oldInvestigationUID, 
			Long newInvestigationUID)throws
			  NEDSSDAOSysException, NEDSSSystemException{
		
			int updateCount;
		try{
			NBSAttachmentNoteDAOImpl nbsAttachmentNoteDao  =  new NBSAttachmentNoteDAOImpl();
			updateCount =  nbsAttachmentNoteDao.updateNbsAttachmentCase(oldInvestigationUID, newInvestigationUID);
			if (updateCount > 0)
				logger.debug(updateCount + " Attachments were migrated for Change Condition");
		}catch(Exception e){
			logger.fatal("Error in updating the record for NBS_Attachment : oldInvestigationUID: " + oldInvestigationUID + ", newInvestigationUID: " + newInvestigationUID + ", " + e.getMessage(),e);
			e.printStackTrace();
			throw new NEDSSSystemException(e.getMessage(),e);
		}
		return updateCount;
	}

	public static void storeNotes(Long publicHealthjCaseUid, Collection<Object> nbsNoteDTColl) {
		Iterator<Object> it =nbsNoteDTColl.iterator();
		while(it.hasNext()){
			NBSNoteDT nbsNoteDT= (NBSNoteDT)it.next();
			nbsNoteDT.setNoteParentUid(publicHealthjCaseUid);
			NBSAttachmentNoteDAOImpl nBSAttachmentDAO = new NBSAttachmentNoteDAOImpl();
			try{
				nBSAttachmentDAO.insertNbsNote(nbsNoteDT);
			}catch(Exception e){
				logger.fatal("Error in inserting the record to NBSNote : publicHealthCaseUid: " + publicHealthjCaseUid + ", " + e.getMessage(),e);
				e.printStackTrace();
				throw new NEDSSSystemException(e.getMessage(),e);
			}
		}
	}
	
	
	public void updateEpilink (String currEpilink, String newEpilink, PublicHealthCaseVO supersededVO, PublicHealthCaseVO survivorVO, Map<Object, Object> coinfectionMapToIgnore,  NBSSecurityObj nbsSecurityObj)  throws java.rmi.RemoteException, javax.ejb.EJBException, NEDSSAppException, NEDSSSystemException
    {
		logger.debug("PageCaseUtil.updateEpilink:currEpilink is "+ currEpilink);
		if(currEpilink!=null) {
			currEpilink= currEpilink.trim();
		}
		logger.debug("PageCaseUtil.updateEpilink newEpilink is "+ newEpilink);
		if(newEpilink!=null) {
			newEpilink= newEpilink.trim();
		}
		Long survivorPersonUid=null;
		if(survivorVO!=null){
			Collection coll = survivorVO.getTheParticipationDTCollection();
			Iterator it = coll.iterator();
			while(it.hasNext()){
				ParticipationDT participationDT=(ParticipationDT)it.next();
				if(participationDT.getTypeCd().equals(NEDSSConstants.PHC_PATIENT)){
					survivorPersonUid=participationDT.getSubjectEntityUid();
				}
			}
		}

    	CaseManagementDAOImpl cmDAO = new CaseManagementDAOImpl();
    	EpilinkActivityLogDaoImpl epilinkDAO = new EpilinkActivityLogDaoImpl();
    	EpilinkDT epilinkDT = new EpilinkDT(); 
    	epilinkDT.setOldEpilinkId(currEpilink);
    	epilinkDT.setNewEpilinkId(newEpilink);
    	epilinkDT.setProcessedDate(new Date());
    	epilinkDT.setRecordStatusCd("Active");
    	epilinkDT.setTargetTypeCd("CASE");
    	epilinkDT.setSourceTypeCd("CASE");
    	epilinkDT.setDocType("CASE");
    	epilinkDT.setActionTxt("MERGE_EPILINKID");
    	java.util.Date dateTime = new java.util.Date();
		Timestamp lastChgTime = new Timestamp(dateTime.getTime());
		Long lastChgUserId= new Long(nbsSecurityObj.getEntryID());
    	epilinkDT.setAddUserId(Long.valueOf(nbsSecurityObj.getTheUserProfile().getTheUser().getEntryID()));
    	try
    	{
    	   List<Object> investigationslist = cmDAO.getEpilinkDTCollection(currEpilink);
    	   Long investigationUid=null;
    	   for(Object dt : investigationslist){
    		   if(dt instanceof CaseManagementDT){
    			   investigationUid = ((CaseManagementDT)dt).getPublicHealthCaseUid() ;
    			   if(supersededVO!=null){
	    			   if(supersededVO.getThePublicHealthCaseDT().getPublicHealthCaseUid()!=null && supersededVO.getThePublicHealthCaseDT().getPublicHealthCaseUid().longValue()==investigationUid.longValue()
	    				|| survivorVO.getThePublicHealthCaseDT().getPublicHealthCaseUid()!=null && survivorVO.getThePublicHealthCaseDT().getPublicHealthCaseUid().longValue()==investigationUid.longValue()
	    				|| coinfectionMapToIgnore.get(investigationUid)!=null)
	    				   continue;
    			   }
    			    PageCaseUtil pageCaseUtil =  new PageCaseUtil();
   					PageActProxyVO proxyVO = (PageActProxyVO) pageCaseUtil.getPageProxyVO(NEDSSConstants.CASE, investigationUid, nbsSecurityObj);

   					/**TODO PKS UPDATE FOR MERGE INVESTIGATIONS
   					if(publicHealthCaseUidToIgnore!=null && publicHealthCaseUidToIgnore.longValue()!=investigationUid.longValue())
    				   proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().setReentrant(true);
    				   */
     			   	epilinkDT.setInvestigationsString(proxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getLocalId());
   					proxyVO.getPublicHealthCaseVO().getTheCaseManagementDT().setEpiLinkId(newEpilink);
   					AssociatedInvestigationUpdateUtil util =  new AssociatedInvestigationUpdateUtil();
   					util.updatePageProxyVOInterface(proxyVO,lastChgTime,lastChgUserId, nbsSecurityObj);
   					Long phcUid = pageCaseUtil.setPageActProxyVO( proxyVO, nbsSecurityObj);
   			   	  	logger.debug("PageCaseUtil.Exception string is: " + phcUid);
   			   
    		   }
    	   }
		   CTContactDAOImpl contactDAO= new CTContactDAOImpl();
		   List<Object> contactRecordProxyVOList = new ArrayList<Object>();;
    	   List<Object> contactRecordlist = contactDAO.getEpilinkDTCollection(currEpilink);
    	   PamProxyEJB pamEJB =  new PamProxyEJB();
		   if(contactRecordlist!=null && contactRecordlist.size()>0) {
    		   Iterator<Object> iterator = contactRecordlist.iterator();
    		   while(iterator.hasNext()) {
    			   CTContactDT cTContactDT =  (CTContactDT)iterator.next();
    			   Long ctContactUid=cTContactDT.getCtContactUid();
    			   CTContactProxyVO contactProxyVO=pamEJB.getContactProxyVO(ctContactUid, nbsSecurityObj);
    			   contactRecordProxyVOList.add(contactProxyVO);
    		   }
		   }
		   Map<Object, Object> reEnterantCheckerMap = new HashMap<Object, Object>();
    	  if(contactRecordProxyVOList!=null && contactRecordProxyVOList.size()>0){
    		   Iterator<Object> contactIterator = contactRecordProxyVOList.iterator();
    		   while(contactIterator.hasNext()){
    			   CTContactProxyVO contactProxyVO =  (CTContactProxyVO)contactIterator.next();
    			   Long ctContactUid = contactProxyVO.getcTContactVO().getcTContactDT().getCtContactUid();
    			   AssociatedInvestigationUpdateUtil util = new AssociatedInvestigationUpdateUtil();
    			   String contactEpiLinkId = contactProxyVO.getcTContactVO().getcTContactDT().getConEntityEpilinkId();
    			   String subjectEpiLinkId = contactProxyVO.getcTContactVO().getcTContactDT().getSubEntityEpilinkId();
    			   
    			   if(contactEpiLinkId!=null && contactEpiLinkId.trim().equalsIgnoreCase(currEpilink)) {
    				   logger.debug("PageCaseUtil.updateEpilink contactEpiLinkId is matched for contactEpiLinkId:"+contactEpiLinkId+"\nFor contactUid:"+ctContactUid);
    				   logger.debug("PageCaseUtil.updateEpilink contactEpiLinkId :"+contactEpiLinkId +"\ncurrEpilink:"+currEpilink+"\nnewEpilink"+newEpilink);
           			   contactProxyVO.getcTContactVO().getcTContactDT().setConEntityEpilinkId(newEpilink);
    			   }
    			   
    			   if(subjectEpiLinkId!=null && subjectEpiLinkId.trim().equalsIgnoreCase(currEpilink)) {
    				   logger.debug("PageCaseUtil.updateEpilink subjectEpiLinkId is matched for subjectEpiLinkId:"+contactEpiLinkId+"\nFor contactUid:"+ctContactUid);
    				   logger.debug("PageCaseUtil.updateEpilink subjectEpiLinkId :"+contactEpiLinkId +"\ncurrEpilink:"+currEpilink+"\nnewEpilink"+newEpilink);
    				   contactProxyVO.getcTContactVO().getcTContactDT().setSubEntityEpilinkId(newEpilink);
    			   }
    			   
    			  Long personParentUid=null;
    			   int versionCtrlNbr=0;
    			   if(reEnterantCheckerMap.get(contactProxyVO.getcTContactVO().getContactPersonVO().getThePersonDT().getPersonParentUid())!=null){
    				   personParentUid=contactProxyVO.getContactPersonVO().getThePersonDT().getPersonParentUid();
      				  versionCtrlNbr=contactProxyVO.getContactPersonVO().getThePersonDT().getVersionCtrlNbr();
    				  contactProxyVO.getcTContactVO().getContactPersonVO().getThePersonDT().setReentrant(true);
    			   }else{
    				   personParentUid=contactProxyVO.getContactPersonVO().getThePersonDT().getPersonParentUid();
     				  versionCtrlNbr=contactProxyVO.getContactPersonVO().getThePersonDT().getVersionCtrlNbr();
     			   }
    			   reEnterantCheckerMap.put(personParentUid, versionCtrlNbr);
    				contactProxyVO.getcTContactVO().getContactPersonVO().getThePersonDT().setReentrant(true);
    				contactProxyVO.getContactPersonVO().getThePersonDT().setReentrant(true);
    				
    				reEnterantCheckerMap.put(contactProxyVO.getcTContactVO().getContactPersonVO().getThePersonDT().getPersonParentUid(), new Integer(0));
    				
    				
    			   CTContactProxyVO returnContactProxyVO=util.updateContactProxyVO(contactProxyVO, lastChgTime, lastChgUserId, nbsSecurityObj);   
    			   if(supersededVO!=null){
    				   if(supersededVO.getThePublicHealthCaseDT().getPublicHealthCaseUid()!=null && supersededVO.getThePublicHealthCaseDT().getPublicHealthCaseUid()!=null){
 	       				   if (returnContactProxyVO.getcTContactVO().getcTContactDT().getSubjectEntityPhcUid()!= null
    						   && returnContactProxyVO.getcTContactVO().getcTContactDT().getSubjectEntityPhcUid().compareTo(supersededVO.getThePublicHealthCaseDT().getPublicHealthCaseUid())==0) {
	    					   returnContactProxyVO.getcTContactVO().getcTContactDT().setIsMergeCaseInd(true);
	    					   returnContactProxyVO.getcTContactVO().getcTContactDT().setSubjectEntityUid(survivorPersonUid);
	    					   returnContactProxyVO.getcTContactVO().getcTContactDT().setSubjectEntityPhcUid(survivorVO.getThePublicHealthCaseDT().getPublicHealthCaseUid());
 	       				   }else{
	    					   returnContactProxyVO.getcTContactVO().getcTContactDT().setSubjectEntityUid(survivorPersonUid);
	    					   returnContactProxyVO.getcTContactVO().getcTContactDT().setSubjectEntityPhcUid(survivorVO.getThePublicHealthCaseDT().getPublicHealthCaseUid());
 	       				   }
    				   }

    			   }
    			   
    			   pamEJB.setContactProxyVO(returnContactProxyVO, nbsSecurityObj);
    			   returnContactProxyVO.setItDirty(false);
    		 }
    	   }
           epilinkDAO.insertEpilinkLog(epilinkDT);
    		
      }catch(NEDSSAppException nae){
    	  logger.fatal("PageCaseUtil.updateEpilink NEDSSAppException string is: " + nae.getErrorCd()+nae.getMessage(),nae);
          throw new  EJBException(nae.getMessage(),nae);
      }
      catch(Exception e)
      {
          logger.fatal("currEpilink: " + currEpilink + ", newEpilink: " + newEpilink + ", updateEpilink catch: " + e.getClass()+e.getMessage(),e);
          logger.fatal("PageCaseUtil.updateEpilink Exception string is: " + e.getMessage(),e);
          throw new  EJBException(e.getMessage(),e);
      }
    }

	
	public void mergeContactRecordForNonSTDCases(PageActProxyVO survivorActProxyVO, PageActProxyVO supersededActProxyVO, NBSSecurityObj nbsSecurityObj){
		try {
			Long survivorPersonUid= null;
			if(survivorActProxyVO!=null){
				Collection coll = survivorActProxyVO.getTheParticipationDTCollection();
				Iterator it = coll.iterator();
				while(it.hasNext()){
					ParticipationDT participationDT=(ParticipationDT)it.next();
					if(participationDT.getTypeCd().equals(NEDSSConstants.PHC_PATIENT)){
						survivorPersonUid=participationDT.getSubjectEntityUid();
						break;
					}
				}
			}
			java.util.Date dateTime = new java.util.Date();
			Timestamp lastChgTime = new Timestamp(dateTime.getTime());
			Long lastChgUserId= new Long(nbsSecurityObj.getEntryID());
		   Collection<Object> contactRecordProxyVOList = supersededActProxyVO.getTheCTContactSummaryDTCollection();
		   if(contactRecordProxyVOList!=null){
			   Iterator<Object> contactIterator = contactRecordProxyVOList.iterator();
			   while(contactIterator.hasNext()){
					CTContactSummaryDT contactSummaryDT = (CTContactSummaryDT) contactIterator.next();
					Long ctContactUid = contactSummaryDT.getCtContactUid();
					PamProxyEJB pamEJB =  new PamProxyEJB();
					CTContactProxyVO contactProxyVO=pamEJB.getContactProxyVO(ctContactUid, nbsSecurityObj);
					contactProxyVO.getcTContactVO().getContactPersonVO().getThePersonDT().setReentrant(true);
					contactProxyVO.getContactPersonVO().getThePersonDT().setReentrant(true);
					AssociatedInvestigationUpdateUtil util = new AssociatedInvestigationUpdateUtil();
					CTContactProxyVO returnContactProxyVO=util.updateContactProxyVO(contactProxyVO, lastChgTime, lastChgUserId, nbsSecurityObj);   
					returnContactProxyVO.getcTContactVO().getcTContactDT().setIsMergeCaseInd(true);
					returnContactProxyVO.getcTContactVO().getcTContactDT().setSubjectEntityUid(survivorPersonUid);
					returnContactProxyVO.getcTContactVO().getcTContactDT().setSubjectEntityPhcUid(survivorActProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid());
					pamEJB.setContactProxyVO(returnContactProxyVO, nbsSecurityObj);
			   }
		   }
		} catch (RemoteException e) {
			logger.error("mergeContactRecordForNonSTDCases RemoteException thrown : ", e);
		} catch (EJBException e) {
			logger.error("mergeContactRecordForNonSTDCases EJBException thrown : ", e);
		} catch (NEDSSSystemException e) {
			logger.error("mergeContactRecordForNonSTDCases NEDSSSystemException thrown : ", e);
		} catch (NEDSSConcurrentDataException e) {
			logger.error("mergeContactRecordForNonSTDCases NEDSSConcurrentDataException thrown : ", e);
		} catch (CreateException e) {
			logger.error("mergeContactRecordForNonSTDCases CreateException thrown : ", e);
		}
	   
		
	}
	
	public Long setContactAndPageActProxyVO(CTContactProxyVO contactProxyVO,
			PageProxyVO pageProxyVO, NBSSecurityObj nbsSecurityObj) {
		Long contactProxyUid=null;
		Long pageCaseUid =null;
		NedssUtils nedssUtils = new NedssUtils();
		try {
			PageActProxyVO pageActProxyVO = (PageActProxyVO)pageProxyVO;

			if (pageActProxyVO.getThePersonVOCollection() != null) {
				Iterator<Object> personIterator =pageActProxyVO.getThePersonVOCollection()
						.iterator();
				for (personIterator = pageActProxyVO.getThePersonVOCollection().iterator(); 
						personIterator.hasNext();) {
					PersonVO phcPersonVO = (PersonVO) personIterator.next();
					if (phcPersonVO.getThePersonDT().getCd()!=null && phcPersonVO.
							getThePersonDT().getCd().equals(NEDSSConstants.PAT)) {
						if(phcPersonVO.getThePersonDT().getPersonParentUid()!=null 
								&& phcPersonVO.getThePersonDT().getPersonParentUid().longValue()<1)
							phcPersonVO.getThePersonDT().setPersonParentUid(null);
					}
				}
			}
			pageCaseUid =setPageActProxyVO(pageProxyVO, nbsSecurityObj);

			Object lookedUpObj = nedssUtils.lookupBean(JNDINames.EntityControllerEJB);
			logger.debug("!!!!!!!!!!EntityController lookup = "+ lookedUpObj.toString());
			EntityControllerHome ecHome = (EntityControllerHome) PortableRemoteObject.
					narrow(lookedUpObj, EntityControllerHome.class);
			logger.debug("!!!!!!!!!!Found EntityControllerHome: " + ecHome);
			EntityController entityController = ecHome.create();
			PersonVO personVO = entityController.getPerson(phcPatientRevisionUid, 
					nbsSecurityObj);
			contactProxyVO.getcTContactVO().getcTContactDT().setContactEntityUid(phcPatientRevisionUid);
			contactProxyVO.getcTContactVO().getcTContactDT().setContactEntityPhcUid(pageCaseUid);
			contactProxyVO.getcTContactVO().getContactPersonVO().getThePersonDT().setPersonParentUid(personVO.getThePersonDT().getPersonParentUid());
			contactProxyVO.getcTContactVO().getContactPersonVO().getThePersonDT().setVersionCtrlNbr(personVO.getThePersonDT().getVersionCtrlNbr());
			contactProxyVO.getcTContactVO().getContactPersonVO().getThePersonDT().setReentrant(true);
			contactProxyVO.getContactPersonVO().getThePersonDT().setReentrant(true);
			contactProxyVO.getContactPersonVO().getThePersonDT().setPersonParentUid(personVO.getThePersonDT().getPersonParentUid());
			contactProxyVO.getContactPersonVO().getThePersonDT().setVersionCtrlNbr(personVO.getThePersonDT().getVersionCtrlNbr());
			
			
			
			PamProxyEJB pamProxy= new PamProxyEJB();
			contactProxyUid=pamProxy.setContactProxyVO(contactProxyVO, nbsSecurityObj);
			logger.debug("PageCaseUtil.setContactAndPageActProxyVO pageCaseUid created for"
					+ " contact record id:" + contactProxyUid +", for pageCaseUid:"+ pageCaseUid );

		} catch (EJBException e) {
			logger.fatal(e.toString()+" : setContactAndPageActProxyVO  catch: " + e.getClass());
			logger.fatal("PageCaseUtil.setContactAndPageActProxyVO EJBException string is: " 
					+ e.getMessage(),e);
			throw new  EJBException(e.getMessage(),e);
		} catch (RemoteException e) {
			logger.fatal(e.toString()+" : setContactAndPageActProxyVO  catch: " + e.getClass());
			logger.fatal("PageCaseUtil.setContactAndPageActProxyVO RemoteException string is: " 
					+ e.getMessage(),e);
			throw new  EJBException(e.getMessage(),e);
		} catch (NEDSSSystemException e) {
			logger.fatal(e.toString()+" : setContactAndPageActProxyVO  catch: " + e.getClass());
			logger.fatal("PageCaseUtil.setContactAndPageActProxyVO NEDSSSystemException string is: " 
					+ e.getMessage(),e);
			throw new  EJBException(e.getMessage(),e);
		} catch (NEDSSConcurrentDataException e) {
			logger.fatal(e.toString()+" : setContactAndPageActProxyVO  catch: " + e.getClass());
			logger.fatal("PageCaseUtil.setContactAndPageActProxyVO NEDSSConcurrentDataException string is: " 
					+ e.getMessage(),e);
			throw new  EJBException(e.getMessage(),e);
		} catch (CreateException e) {
			logger.fatal(e.toString()+" : setContactAndPageActProxyVO  catch: " + e.getClass());
			logger.fatal("PageCaseUtil.setContactAndPageActProxyVO CreateException string is: " +
					e.getMessage(),e);
			throw new  EJBException(e.getMessage(),e);
		}

		return contactProxyUid;
	}
	
	
	public void updateNamedAsContactDisposition(CaseManagementDT caseManagementDT, NBSSecurityObj nbsSecurityObj) throws NEDSSSystemException {
		if (caseManagementDT.getPublicHealthCaseUid() == null)  //auto field followup create in progress..
			return;
		try {
			logger.debug("PageCaseUtil.updateNamedAsContactDisposition while updateContactRecordDisposition with caseManagementDT:" + caseManagementDT.toString());

			String dispositionCd =caseManagementDT.getFldFollUpDispo();
			if(dispositionCd!=null && dispositionCd.equalsIgnoreCase(NEDSSConstants.FROM1_A_PREVENTATIVE_TREATMENT)) {
				dispositionCd = NEDSSConstants.TO1_Z_PREVIOUS_PREVENTATIVE_TREATMENT;
			}
			else if(dispositionCd!=null && dispositionCd.equalsIgnoreCase(NEDSSConstants.FROM2_C_INFECTED_BROUGHT_TO_TREATMENT)) {
				dispositionCd = NEDSSConstants.TO2_E_PREVIOUSLY_TREATED_FOR_THIS_INFECTION;
			}
			Timestamp fldFollowUpDispDate=caseManagementDT.getFldFollUpDispoDate();
			
			CTContactDAOImpl ctContactDAO =  new CTContactDAOImpl();
			
			int numbersOfAssociatedContactRecords= ctContactDAO.countNamedAsContactDispoInvestigations(caseManagementDT.getPublicHealthCaseUid());
			logger.debug("numbersOfAssociatedContactRecords is "+numbersOfAssociatedContactRecords);
			
			if(numbersOfAssociatedContactRecords>0) {
				ctContactDAO.updateNamedAsContactDispoInvestigation(dispositionCd,fldFollowUpDispDate, caseManagementDT.getPublicHealthCaseUid());
				logger.debug("updateNamedAsContactDisposition update was successful for "+numbersOfAssociatedContactRecords+" numbers of associated investigations.");
			}
		} catch (NEDSSSystemException e) {
			logger.fatal("NEDSSSystemException occured: PageCaseUtil.updateNamedAsContactDisposition while updateContactRecordDisposition with caseManagementDT:" + caseManagementDT.toString() + e.getMessage(),e);

			throw new NEDSSSystemException(e.getMessage(),e);
		}
	}

	/**
	 * Associates the Interview with the investigations in the associate list.
	 * Physically deletes associations in the disassociate list.
	* @param InterviewUid   the InterviewUid to associate to the list of Invs
	* @param associatedInvestigationList  list of Investigations to associate the Interview with
	* @param disassociatedInvestigationList   list of Investigations to remove association with the Interview with
	* @param nbsSecurityObj      the current value of the NBSSecurityObj object
	* @return  void
	* @throws java.rmi.RemoteException, javax.ejb.EJBException, CreateException, FinderException, NEDSSSystemException
	*/
	public void setInterviewCaseAssociations(Long InterviewUid,
	                                       String actType,
	                                       ArrayList<Long> associatedInvestigationList,
	                                       ArrayList<Long> disassociatedInvestigationList,
	                                       NBSSecurityObj nbsSecurityObj) throws
	java.rmi.RemoteException, javax.ejb.EJBException,
	CreateException, FinderException, NEDSSSystemException {

		try {
			logger.debug("Now in setInterviewCaseAssociations for InterviewUid: " + InterviewUid.toString() + "/n Number to associate= " +
				associatedInvestigationList.size() + " Number to DisAssociate=" + disassociatedInvestigationList.size());


			Long investigationUid = null;

			ManageAutoAssociations manageAutoAssc = new ManageAutoAssociations();
			InterviewSummaryDT InterviewSummaryDT = new InterviewSummaryDT();
			
			//process associated investigations
			InterviewSummaryDT.setAssociated(true);
			InterviewSummaryDT.setInterviewUid(InterviewUid);
			Collection<Object>  InterviewSummaryVOColl = new ArrayList<Object> ();
			InterviewSummaryVOColl.add(InterviewSummaryDT);
			
			try {
					Iterator addIter = associatedInvestigationList.iterator();
					while (addIter.hasNext()) {
						investigationUid = (Long) addIter.next();
						manageAutoAssc.setInterviewAssociationsImpl(investigationUid, actType,
			                                        InterviewSummaryVOColl,
			                                        nbsSecurityObj);
					}
			} catch (Exception e) {
					logger.error(
							"An error occurred while adding Case associations setInterviewCaseAssociations Investigation UID = " +
									investigationUid + " InterviewUID = " + InterviewUid.toString());
					e.printStackTrace();
			}
			
			//process disassociated investigations
			InterviewSummaryDT = new InterviewSummaryDT();
			InterviewSummaryDT.setAssociated(false);
			InterviewSummaryDT.setInterviewUid(InterviewUid);
			InterviewSummaryVOColl = new ArrayList<Object> ();
			InterviewSummaryVOColl.add(InterviewSummaryDT);
			
			try {
					Iterator removeIter = disassociatedInvestigationList.iterator();
					while (removeIter.hasNext()) {
						investigationUid = (Long) removeIter.next();
						manageAutoAssc.setInterviewAssociationsImpl(investigationUid, actType,
			                                        InterviewSummaryVOColl,
			                                        nbsSecurityObj);
					}
			} catch (Exception e) {
					logger.error(
							"An error occurred while removing Case associations setInterviewCaseAssociations InterviewUID = " +
									InterviewUid.toString() + " Investigation UID = " + investigationUid);
					e.printStackTrace();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal("InterviewUid:" + InterviewUid + ", " + e.getMessage(),e);
			throw new NEDSSSystemException(e.getMessage(),e);
		}
		
		return;
	}// end of setInterviewCaseAssociations()
	
	/**
	 * Check if the item (Lab or Morb) in the act relationship is from a Doc
	 * @param actRelationshipDT
	 * @param pageProxyVO
	 * @return true if from doc else false
	 */
	private boolean eventIsDocument(ActRelationshipDT actRelationshipDT,
			PageActProxyVO pageProxyVO) {
		if (actRelationshipDT.getTypeCd().equalsIgnoreCase(NEDSSConstants.LAB_REPORT)) {
			Collection<Object> labList = pageProxyVO.getTheLabReportSummaryVOCollection();
			if (labList != null && labList.size() > 0) {
				Iterator labIter = labList.iterator();
				while (labIter.hasNext()) {
					LabReportSummaryVO labSumVO = (LabReportSummaryVO) labIter.next();
					if (actRelationshipDT.getSourceActUid().longValue() == labSumVO.getObservationUid().longValue()) {
						if (labSumVO.isLabFromDoc())
							return true;
						else
							return false;
					}
				}
			}
		} else if (actRelationshipDT.getTypeCd().equalsIgnoreCase(NEDSSConstants.MORBIDITY_REPORT)) {
			Collection<Object> morbList = pageProxyVO.getTheMorbReportSummaryVOCollection();
			if (morbList != null && morbList.size() > 0) {
				Iterator morbIter = morbList.iterator();
				while (morbIter.hasNext()) {
					MorbReportSummaryVO morbSumVO = (MorbReportSummaryVO) morbIter.next();
					if (actRelationshipDT.getSourceActUid().longValue() == morbSumVO.getObservationUid().longValue()) {
						if (morbSumVO.isMorbFromDoc())
							return true;
						else
							return false;
					}
				}
			}
		}

		// is not from a document
		return false;
	}
	
	private void updateCaseNotificationForChangeConditionToNonNNDCondition(
			PublicHealthCaseDT caseDT) {
		try {
			CaseNotificationDAOImpl caseNotiDAO = new CaseNotificationDAOImpl();
			String nndInd = caseNotiDAO.getNNDInd(caseDT.getCd());
			if (nndInd != null && nndInd.equals(NEDSSConstants.YES))
				return;
			CnTransportQOutDAOImpl cnDao = new CnTransportQOutDAOImpl();
			CnTransportQOutDT cnDT = cnDao.selectItemByCaseLocalId(caseDT
					.getLocalId());
			if (cnDT != null && cnDT.getCnTransportQOutUID() != null) {
				TransformerFactory tFactory = TransformerFactory.newInstance();
				String nedssDir = new StringBuffer(
						System.getProperty("nbs.dir")).append(File.separator)
						.toString().intern();
				String propertiesDir = new StringBuffer(nedssDir)
						.append("Properties").append(File.separator).toString()
						.intern();
				byte[] encoded = Files.readAllBytes(Paths.get(propertiesDir
						+ "ChangeConditionToNonNND.xsl"));
				Transformer transformer = tFactory
						.newTransformer(new javax.xml.transform.stream.StreamSource(
								new ByteArrayInputStream(encoded)));
				Writer result = new StringWriter();
				PrintWriter out = new PrintWriter(result);
				InputStream is = new ByteArrayInputStream(cnDT
						.getMessagePayload().getBytes("UTF-8"));
				transformer.transform(
						new javax.xml.transform.stream.StreamSource(is),
						new javax.xml.transform.stream.StreamResult(out));
				// to get rid of '<?xml version="1.0" encoding="UTF-8"?>'
				cnDT.setMessagePayload(result.toString().substring(38));
				cnDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_UNPROCESSED);
				cnDT.setReportStatus(NEDSSConstants.NND_DELETED_MESSAGE);
				java.util.Date dateTime = new java.util.Date();
				Timestamp systemTime = new Timestamp(dateTime.getTime());
				cnDT.setLastChgTime(systemTime);
				cnDao.insertCnTransportQOutDT(cnDT);
			}
		} catch (IOException ex) {
			logger.error("Error in XML translation for case Notification"
					+ ex.getMessage());
			ex.printStackTrace();
			throw new NEDSSSystemException(ex.getMessage(), ex);
		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal(
					"updateCaseNotificationForChangeConditionToNonNNDCondition caseLocalid:"
							+ caseDT.getLocalId() + ", " + e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);

		}
	}
	  
	
	public Map<Object, Object> setCoInfectionsAndEpiLinkIdForMerge(PageActProxyVO survivorProxyVO, PageActProxyVO supersededProxyVO, NBSSecurityObj nbsSecurityObj) throws NEDSSAppException{
		Map<Object, Object> coInSupersededEpliLinkIdMap =  new HashMap<Object, Object>();
    	try{
    		PublicHealthCaseDT surviverPublicHealthCaseDT = null;
    		PublicHealthCaseDT supersededPublicHealthCaseDT = null;
    		String survivingEpiLinkId="";
    		String supersededEpiLinkId="";

    		
    		if(survivorProxyVO!=null && survivorProxyVO.getPublicHealthCaseVO()!=null){
    			supersededPublicHealthCaseDT = supersededProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT();
    			surviverPublicHealthCaseDT  = survivorProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT();
    			survivingEpiLinkId=survivorProxyVO.getPublicHealthCaseVO().getTheCaseManagementDT().getEpiLinkId();
    		    supersededEpiLinkId=survivorProxyVO.getPublicHealthCaseVO().getTheCaseManagementDT().getEpiLinkId();
    			Long mprUid = null;
				if (survivorProxyVO.getThePersonVOCollection() != null) {
					for (Iterator<Object> anIterator = survivorProxyVO.getThePersonVOCollection()
							.iterator(); anIterator.hasNext();) {
						PersonVO personVO = (PersonVO) anIterator.next();
						if (NEDSSConstants.PAT.equals(personVO.getThePersonDT().getCd())) {
							mprUid=personVO.getThePersonDT().getPersonParentUid();
							break;
						}
					}
				}
				Collection<Object> coinfectionSummaryVOsupersededCollection = null;
				Collection<Object> coinfectionSummaryVOSurvivingCollection = null;
				
				//Get List of coinfection associated with superseded investigation
				RetrieveSummaryVO retrieveSummaryVO =  new RetrieveSummaryVO();
				coinfectionSummaryVOsupersededCollection = retrieveSummaryVO.getInvListForMergeCoInfectionId(mprUid,supersededPublicHealthCaseDT.getCoinfectionId());
				
				//Get List of coinfection associated with surviving investigation
				coinfectionSummaryVOSurvivingCollection = retrieveSummaryVO.getInvListForMergeCoInfectionId(mprUid,surviverPublicHealthCaseDT.getCoinfectionId());
			
				/**Update based on requirement*/
				boolean isUpdateSurvivingWithSupersededCoinfectionID= false;
				boolean isUpdateSupersededWithSurvivingCoinfectionID = false;
				
				if(coinfectionSummaryVOsupersededCollection.size()>0 && coinfectionSummaryVOSurvivingCollection.size()>1){
					isUpdateSupersededWithSurvivingCoinfectionID =true;
					supersededPublicHealthCaseDT.setCoinfectionId(surviverPublicHealthCaseDT.getCoinfectionId());
    			}else if(coinfectionSummaryVOsupersededCollection.size()==0 && coinfectionSummaryVOSurvivingCollection.size()>1){
					supersededPublicHealthCaseDT.setCoinfectionId(surviverPublicHealthCaseDT.getCoinfectionId());
					
				}else if(coinfectionSummaryVOsupersededCollection.size()==0 && coinfectionSummaryVOSurvivingCollection.size()==1){
					supersededPublicHealthCaseDT.setCoinfectionId(surviverPublicHealthCaseDT.getCoinfectionId());
				}else if(coinfectionSummaryVOsupersededCollection.size()>0 && coinfectionSummaryVOSurvivingCollection.size()==1){
					isUpdateSurvivingWithSupersededCoinfectionID= true;
					surviverPublicHealthCaseDT.setCoinfectionId(supersededPublicHealthCaseDT.getCoinfectionId());
				}
				
				
				if(isUpdateSurvivingWithSupersededCoinfectionID){
		    		//If surviver's coinfectionId is null then set superseded's coinfectionId to surviver's coinfectionId.
					surviverPublicHealthCaseDT.setCoinfectionId(supersededPublicHealthCaseDT.getCoinfectionId());
				}
				
				if(isUpdateSupersededWithSurvivingCoinfectionID || supersededProxyVO!=null ){
					if(coinfectionSummaryVOsupersededCollection!=null){
						for (Iterator<Object> anIterator = coinfectionSummaryVOsupersededCollection.iterator(); anIterator.hasNext();) {
							CoinfectionSummaryVO coninfectionSummaryVO= (CoinfectionSummaryVO)anIterator.next();
							
							
							//Remove superseded records from coinfectionSummaryVOCollection list
							if(isUpdateSupersededWithSurvivingCoinfectionID 
									&& coninfectionSummaryVO!=null 
									&& coninfectionSummaryVO.getPublicHealthCaseUid()!=null 
									&& supersededPublicHealthCaseDT.getPublicHealthCaseUid().longValue()==coninfectionSummaryVO.getPublicHealthCaseUid().longValue()){
								anIterator.remove();
							}
							coInSupersededEpliLinkIdMap.put(coninfectionSummaryVO.getPublicHealthCaseUid(), supersededProxyVO.getPublicHealthCaseVO().getTheCaseManagementDT().getEpiLinkId());
						}
					}
					if(coinfectionSummaryVOsupersededCollection.size()>0)
						coinfectionSummaryVOSurvivingCollection.addAll(coinfectionSummaryVOsupersededCollection);
				} 
				
				//Update coinfections with surviving 
				AssociatedInvestigationUpdateUtil coInfectionUtil = new AssociatedInvestigationUpdateUtil();
				coInfectionUtil.updateForConInfectionId(survivorProxyVO, supersededProxyVO, mprUid, coInSupersededEpliLinkIdMap, surviverPublicHealthCaseDT.getPublicHealthCaseUid(), coinfectionSummaryVOsupersededCollection, surviverPublicHealthCaseDT.getCoinfectionId(), nbsSecurityObj);
				return coInSupersededEpliLinkIdMap;
			}
    	}catch(Exception ex){
    		logger.fatal("Exception occured in PageStoreUtil.setCoInfectionsAndEpiLinkIdForMerge: " +ex.getMessage(), ex);
    		logger.fatal("Exception occured in PageStoreUtil.setCoInfectionsAndEpiLinkIdForMerge: survivorProxyVO PublicHealthCaseUid: " + survivorProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid() + ", survivorProxyVO PublicHealthCaseUid" + survivorProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid() +", "+ ex.getMessage(), ex);
    		logger.fatal("Exception occured in PageStoreUtil.setCoInfectionsAndEpiLinkIdForMerge: supersededProxyVO PublicHealthCaseUid: " + supersededProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid() + ", superseded PublicHealthCaseUid" + supersededProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid() +", "+ ex.getMessage(), ex);
        	throw new NEDSSAppException(ex.getMessage(), ex);
    	}
    	return coInSupersededEpliLinkIdMap;
    }
	
	
	/**
	 * Currently used to associate notes, attachments, contact records/dis-associate events from the superseded investigation after Merge Investigations.
	 * @param oldPHCUid
	 * @param newPHCUid
	 * @param nbsSecurityObj
	 * @throws java.rmi.RemoteException
	 * @throws javax.ejb.EJBException
	 * @throws NEDSSSystemException
	 * @throws NEDSSConcurrentDataException
	 */
	public void setAssociationsAfterMergeInvestigation(Long oldPHCUid, Long newPHCUid, PageActProxyVO newPageProxyVO, NBSSecurityObj nbsSecurityObj) 
			throws java.rmi.RemoteException, javax.ejb.EJBException, javax.ejb.CreateException, NEDSSSystemException, NEDSSConcurrentDataException{
		
		PageCaseUtil pageCaseUtil= new PageCaseUtil();
		pageCaseUtil.updateNotesForChangeCondition(oldPHCUid,newPHCUid);
		pageCaseUtil.updateAttachmentsForChangeCondition(oldPHCUid,newPHCUid);
		/*commented to process contact after update!!!
		 * pageCaseUtil.updateContactsForMergeInvestigation(oldPHCUid,newPHCUid,newPageProxyVO.getTheParticipationDTCollection());
		 * */
		pageCaseUtil.deleteExistingAssociations(oldPHCUid, nbsSecurityObj);
	}
	
	/**
	 * 
	 * @param nbsSecurityObj
	 * @param publicHealthCaseUid
	 * @param survivorLocalId
	 * @param supersededLocalId
	 */
	public void addNewNoteForSurvivingInvestigation(NBSSecurityObj nbsSecurityObj, Long survivorPublicHealthCaseUid, Long supercededPublicHealthCaseUid, String survivorLocalId, String supersededLocalId) {		
        NBSNoteDT nbsNoteDT = new NBSNoteDT();
        nbsNoteDT.setItNew(true);
        try {
        	
			Long userUid = new Long(Long.parseLong(nbsSecurityObj.getEntryID()));
			RetrieveSummaryVO retrieveSummaryVO = new RetrieveSummaryVO();
			String userNm = retrieveSummaryVO.getUserName(userUid);
			
			
        	nbsNoteDT.setNbsNoteUid(new Long(-1));
        	String userId = nbsSecurityObj.getTheUserProfile().getTheUser().getEntryID();
        	nbsNoteDT.setNoteParentUid(survivorPublicHealthCaseUid);
        	
        	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        	String date  = dateFormat.format(new Date());
        	
        	String survivorMessage = "Investigation "+supersededLocalId+" was merged with "+survivorLocalId +" on "+date+" by "+userNm+". Investigation "+survivorLocalId+" was the surviving Investigation.";
        	
        	
        	nbsNoteDT.setNote(survivorMessage);
        	
        	nbsNoteDT.setAddTime(new Timestamp(new Date().getTime()));
        	nbsNoteDT.setAddUserId(Long.valueOf(userId));
        	nbsNoteDT.setRecordStatusTime(new Timestamp(new Date().getTime()));
        	nbsNoteDT.setRecordStatusCd(NEDSSConstants.ACTIVE);
        	nbsNoteDT.setLastChgUserId(Long.valueOf(userId));
        	nbsNoteDT.setLastChgTime(new Timestamp(new Date().getTime()));
        	nbsNoteDT.setTypeCd(NEDSSConstants.INVESTIGATION_CD);  //SNOMED code for Public Health Case Investigation
        	nbsNoteDT.setPrivateIndCd(NEDSSConstants.FALSE);
        	// store note in DB
        	// Long resultUid = saveNewNote(nbsNoteDT, session);
        	
        	Long survivorResultUid = setNBSNote(nbsNoteDT, nbsSecurityObj);
	        if(survivorResultUid != null && survivorResultUid.longValue() > 0)
	        	logger.debug("New note added for surviving investigation (merge)");
        	nbsNoteDT.setNoteParentUid(supercededPublicHealthCaseUid);
        	String supercededMessage = "Investigation "+supersededLocalId+" was superceded by "+survivorLocalId +" on "+date+" by "+userNm+". Investigation "+supersededLocalId+" is the superceded Investigation.";
        	nbsNoteDT.setNote(supercededMessage);	
           	
        	Long supercededResultUid = setNBSNote(nbsNoteDT, nbsSecurityObj);
 
	        if(supercededResultUid != null && supercededResultUid.longValue() > 0)
	        	logger.debug("New note added for surviving investigation (merge)");
	    } catch (Exception e) {
	        logger.error("Change Condition - Error storing note = " + e);
	    }
	}
		
	/**
	 * 	
	 * @param nbsNoteDT
	 * @param nbsSecurityObj
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws javax.ejb.EJBException
	 * @throws javax.ejb.CreateException
	 * @throws NEDSSSystemException
	 * @throws NEDSSConcurrentDataException
	 */
	public Long setNBSNote(NBSNoteDT nbsNoteDT, NBSSecurityObj nbsSecurityObj)throws java.rmi.RemoteException, javax.ejb.EJBException, javax.ejb.CreateException, 
		NEDSSSystemException, NEDSSConcurrentDataException 
	{
		if (!nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION, NBSOperationLookup.VIEW)) {
			logger.info("nbsSecurityObj.getPermission(NedssBOLookup.INVESTIGATION,NBSOperationLookup.EDIT) is false");
			throw new NEDSSSystemException("NO PERMISSIONS");
		}
		logger.info("nbsSecurityObj.getPermission(NedssBOLookup.INVESTIGATION,NBSOperationLookup.EDIT) is true");
		
		NBSAttachmentNoteDAOImpl nBSAttachmentDAO = new NBSAttachmentNoteDAOImpl();
		try{
			return nBSAttachmentDAO.insertNbsNote(nbsNoteDT);
		}catch(Exception e){
			logger.fatal("PageProxyEJB.setNBSNote:" + e.getMessage(),e);
			throw new NEDSSSystemException(e.getMessage(),e);
		}
	}
		
}
