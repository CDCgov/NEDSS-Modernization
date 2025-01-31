package gov.cdc.nedss.proxy.ejb.pamproxyejb.bean;


//Import Statements
import gov.cdc.nedss.act.ctcontact.dt.CTContactAttachmentDT;
import gov.cdc.nedss.act.ctcontact.dt.CTContactDT;
import gov.cdc.nedss.act.ctcontact.dt.CTContactNoteDT;
import gov.cdc.nedss.act.ctcontact.dt.CTContactSummaryDT;
import gov.cdc.nedss.act.ctcontact.ejb.dao.CTContactAttachmentDAO;
import gov.cdc.nedss.act.ctcontact.ejb.dao.CTContactNoteDAO;
import gov.cdc.nedss.act.ctcontact.vo.CTContactVO;
import gov.cdc.nedss.act.intervention.dt.InterventionDT;
import gov.cdc.nedss.act.intervention.vo.InterventionVO;
import gov.cdc.nedss.act.interview.ejb.dao.InterviewSummaryDAO;
import gov.cdc.nedss.act.notification.dt.NotificationDT;
import gov.cdc.nedss.act.notification.vo.NotificationVO;
import gov.cdc.nedss.act.observation.helper.ObservationProcessor;
import gov.cdc.nedss.act.publichealthcase.dt.PublicHealthCaseDT;
import gov.cdc.nedss.act.publichealthcase.ejb.dao.PublicHealthCaseDAOImpl;
import gov.cdc.nedss.act.publichealthcase.vo.PublicHealthCaseVO;
import gov.cdc.nedss.act.sqlscript.WumSqlQuery;
import gov.cdc.nedss.act.summaryreport.vo.SummaryReportProxyVO;
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
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.nbsactentity.dt.NbsActEntityDT;
import gov.cdc.nedss.nnd.helper.NNDActivityLogDT;
import gov.cdc.nedss.nnd.helper.NNDMessageSenderHelper;
import gov.cdc.nedss.page.ejb.pageproxyejb.bean.dao.NBSAttachmentNoteDAOImpl;
import gov.cdc.nedss.page.ejb.pageproxyejb.util.PageCaseUtil;
import gov.cdc.nedss.pam.dao.NbsHistoryDAO;
import gov.cdc.nedss.pam.dao.PamRootDAO;
import gov.cdc.nedss.pam.vo.PamVO;
import gov.cdc.nedss.proxy.ejb.interventionproxyejb.vo.VaccinationSummaryVO;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.dao.ObservationSummaryDAOImpl;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.dao.RetrieveSummaryVO;
import gov.cdc.nedss.proxy.ejb.notificationproxyejb.bean.NotificationProxy;
import gov.cdc.nedss.proxy.ejb.notificationproxyejb.bean.NotificationProxyHome;
import gov.cdc.nedss.proxy.ejb.notificationproxyejb.vo.NotificationProxyVO;
import gov.cdc.nedss.proxy.ejb.notificationproxyejb.vo.NotificationSummaryVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.bean.ObservationProxy;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.bean.ObservationProxyHome;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.LabReportSummaryVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.MorbReportSummaryVO;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.dao.AggregateSummaryDataDAOImpl;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.dao.CTContactSummaryDAO;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.vo.CTContactProxyVO;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.vo.PamProxyVO;
import gov.cdc.nedss.proxy.ejb.queue.dao.MessageLogDAOImpl;
import gov.cdc.nedss.proxy.ejb.queue.dt.MessageLogDT;
import gov.cdc.nedss.proxy.ejb.treatmentproxyejb.vo.TreatmentSummaryVO;
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
import gov.cdc.nedss.systemservice.util.AssocDTInterface;
import gov.cdc.nedss.systemservice.util.NEDSSDAOFactory;
import gov.cdc.nedss.systemservice.util.PrepareVOUtils;
import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.BMPBase;
import gov.cdc.nedss.util.DataTables;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.util.PropertyUtil;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;
import javax.rmi.PortableRemoteObject;

/**
 * PamProxyEJB is the new EJB to store and retrieve Pams. PamProxyEJB stores the
 * Public_health_case, Participations, act_relationship, Pam_answer,
 * Pam_answer_hist, Pam_case_entity, Pam_case_entity_hist and also retrieve
 * these objects while retrieving the historical VO Objects from database.
 *
 * @author Pradeep Sharma
 *
 */
public class PamProxyEJB extends BMPBase implements javax.ejb.SessionBean {
	// For logging
	static final LogUtils logger = new LogUtils(PamProxyEJB.class.getName());

	private static final long serialVersionUID = 1L;

	PamProxyVO pamProxyVO = null;

	public PamProxyEJB() {
	}


	/**
	 *
	 * @param publicHealthCaseUID
	 * @param nbsSecurityObj
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws javax.ejb.EJBException
	 * @throws javax.ejb.CreateException
	 * @throws NEDSSSystemException
	 * @throws javax.ejb.FinderException
	 * @throws NEDSSConcurrentDataException
	 */
	@SuppressWarnings("unchecked")
	public Map<Object, Object> deletePamProxy(Long publicHealthCaseUID,
			NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException,
			javax.ejb.EJBException, javax.ejb.CreateException,
			NEDSSSystemException, javax.ejb.FinderException,
			NEDSSConcurrentDataException {
		try {
			int labCount = 0;
			int morbCount = 0;
			int vaccineCount = 0;
			int documentCount = 0;
			
			Map<Object, Object> returnMap = new HashMap<Object, Object>();
			RetrieveSummaryVO rsvo = new RetrieveSummaryVO();
			PrepareVOUtils prepareVOUtils = new PrepareVOUtils();
			NedssUtils nedssUtils = new NedssUtils();
			ActController actController = null;

			Object object = nedssUtils.lookupBean(JNDINames.ActControllerEJB);
			logger.debug("ActController lookup = " + object.toString());
			ActControllerHome acthome = (ActControllerHome) PortableRemoteObject
			.narrow(object, ActControllerHome.class);
			logger.debug("Found ActControllerHome: " + acthome);
			actController = acthome.create();

			PamProxyVO pamProxyVO = getPamProxy(publicHealthCaseUID, nbsSecurityObj);
			pamProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().setPamCase(true);
			NbsHistoryDAO nbsHistoryDAO= new NbsHistoryDAO();
			nbsHistoryDAO.getPamHistory(pamProxyVO.getPublicHealthCaseVO());
			PublicHealthCaseDT phcDT = pamProxyVO.getPublicHealthCaseVO()
			.getThePublicHealthCaseDT();
			phcDT.setItDelete(false);
			phcDT.setItDirty(true);

			if (nbsSecurityObj.getPermission(
					NBSBOLookup.INVESTIGATION,
					NBSOperationLookup.DELETE, phcDT.getProgAreaCd(), phcDT
					.getJurisdictionCd())) {
				// Loop through Act Relationships for this Investigation and count #
				// of labs, morbs, vac
				// If we have any count of these, we return the counts and fail the
				// delete
				ActRelationshipDAOImpl actRelationshipDAOImpl = new ActRelationshipDAOImpl();
				pamProxyVO.getPublicHealthCaseVO()
				.setTheActRelationshipDTCollection(
						actRelationshipDAOImpl.load(publicHealthCaseUID
								.longValue()));
				Iterator<Object> anIterator = null;
				for (anIterator = pamProxyVO.getPublicHealthCaseVO()
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
				returnMap.put(NEDSSConstants.MORBIDITY_REPORT, new Integer(
						morbCount));
				// '1180' AR_TYPE_CODE is for Vaccines
				returnMap.put(NEDSSConstants.AR_TYPE_CODE,
						new Integer(vaccineCount));
				returnMap.put(NEDSSConstants.DocToPHC,
						new Integer(documentCount));
				//must unlink event/document before deleting
				if (labCount > 0 || morbCount > 0 || vaccineCount > 0 || documentCount > 0) {
					return returnMap;
				}


				// Logically delete the Investigation
				RootDTInterface rootDTInterface = phcDT;
				String businessObjLookupName = NBSBOLookup.INVESTIGATION;
				String businessTriggerCd = "INV_DEL";
				String tableName = "PUBLIC_HEALTH_CASE";
				String moduleCd = "BASE";
				phcDT = (PublicHealthCaseDT) prepareVOUtils.prepareVO(
						rootDTInterface, businessObjLookupName, businessTriggerCd,
						tableName, moduleCd, nbsSecurityObj);

				phcDT.setCaseClassCd(NEDSSConstants.CASE_CLASS_CODE_NOT_A_CASE);
				phcDT
				.setInvestigationStatusCd(NEDSSConstants.INVESTIGATION_STATUS_CODE_CLOSED);
				pamProxyVO.getPublicHealthCaseVO().setThePublicHealthCaseDT(phcDT);
				actController.setPublicHealthCase(pamProxyVO.getPublicHealthCaseVO(), nbsSecurityObj);
				// Persist the Investigation
				//actController.setPublicHealthCaseInfo(phcDT, nbsSecurityObj);

			      //Handle Notifications - update state if a notification exists
			     
			      NotificationSummaryVO notificationSummaryVO=null;
				  if(pamProxyVO.getTheNotificationSummaryVOCollection()!=null && pamProxyVO.getTheNotificationSummaryVOCollection().size() > 0) {
					  Collection<Object>   theNotificationSummaryVOCollection=pamProxyVO.getTheNotificationSummaryVOCollection();
					 Iterator<Object>  iter = theNotificationSummaryVOCollection.iterator();
					  while(iter.hasNext()){
						  notificationSummaryVO = (NotificationSummaryVO)iter.next();
						  if(notificationSummaryVO.getIsHistory().equalsIgnoreCase("F")){
							  NotificationProxy notificationProxy=null;
							  Object objNotification = nedssUtils.lookupBean(JNDINames.NOTIFICATION_PROXY_EJB);
							  logger.debug("NotificationProxyEJB lookup = " + objNotification.toString());
							  NotificationProxyHome notificationProxyHome =(NotificationProxyHome)PortableRemoteObject.narrow(objNotification, NotificationProxyHome.class);
							  logger.debug("Found NotificationProxyHome: " + notificationProxyHome);
							  notificationProxy = notificationProxyHome.create();

							  NotificationProxyVO notificationProxyVO = notificationProxy.getNotificationProxy(
									  notificationSummaryVO.getNotificationUid(),nbsSecurityObj);
							  NotificationDT notificationDT = notificationProxyVO.getTheNotificationVO().getTheNotificationDT();

							  //if	auto resend is "T", set to PEND_DEL and resend the notification
							  //if auto resend is off, logically delete the notification
							  if ( notificationDT.getAutoResendInd().equalsIgnoreCase("T") ){
								  try {
									  notificationDT.setItDelete(false);
									  notificationDT.setItDirty(true);

									  String BUSINESS_OBJECT_LOOKUP_NAME = NBSBOLookup.NOTIFICATION;
									  String BUSINESS_TRIGGER_CD = NEDSSConstants.NOT_DEL_NOTF;
									  String TABLE_NAME = DataTables.NOTIFICATION_TABLE;
									  String MODULE_CD = NEDSSConstants.BASE;

									  NotificationDT newNotificationDT = (NotificationDT) prepareVOUtils.prepareVO(
											  notificationDT, BUSINESS_OBJECT_LOOKUP_NAME, BUSINESS_TRIGGER_CD,
											  TABLE_NAME, MODULE_CD, nbsSecurityObj);

									  actController.setNotificationInfo(newNotificationDT, nbsSecurityObj);
								  }
								  catch(Exception e) {
									  NNDMessageSenderHelper nndMSHelper = null;
									  nndMSHelper = NNDMessageSenderHelper.getInstance();
									  NNDActivityLogDT nndActivityLogDT = new  NNDActivityLogDT();
									  String phcLocalId = pamProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getLocalId();
									  nndActivityLogDT.setErrorMessageTxt(e.toString());
									  if (phcLocalId!=null)
										  nndActivityLogDT.setLocalId(phcLocalId);
									  else
										  nndActivityLogDT.setLocalId("N/A");

									  //catch & store auto resend notifications exceptions in NNDActivityLog table
									  nndMSHelper.persistNNDActivityLog(nndActivityLogDT,nbsSecurityObj);
									  logger.error("Exception occurred while calling nndMessageSenderHelper.updateAutoResendNotificationsAsync, notificationUid: " + notificationDT.getNotificationUid() + ", " + e.getMessage(), e);
									  e.printStackTrace();
									  throw new NEDSSSystemException(e.getMessage(), e);
								  }

							  }
							  else
							  {
								  notificationDT.setItDelete(false);
								  notificationDT.setItDirty(true);

								  String BUSINESS_OBJECT_LOOKUP_NAME = NBSBOLookup.NOTIFICATION;
								  String BUSINESS_TRIGGER_CD = NEDSSConstants.NOT_DEL;
								  String TABLE_NAME = DataTables.NOTIFICATION_TABLE;
								  String MODULE_CD = NEDSSConstants.BASE;

								  NotificationDT newNotificationDT = (NotificationDT) prepareVOUtils.prepareVO(
										  notificationDT, BUSINESS_OBJECT_LOOKUP_NAME, BUSINESS_TRIGGER_CD,
										  TABLE_NAME, MODULE_CD, nbsSecurityObj);

								  actController.setNotificationInfo(newNotificationDT, nbsSecurityObj);
							  }
						  }
					  }
				  }

				// Logically delete the associated Contacts
			    CTContactSummaryDAO cTContactSummaryDAO = new CTContactSummaryDAO();
			    Collection<Object> contactCollection = cTContactSummaryDAO.getContactListForInvestigation(publicHealthCaseUID, nbsSecurityObj);
			    try {
			        if (contactCollection != null && contactCollection.size() > 0) {
			            Iterator itContact = contactCollection.iterator();
			            while (itContact.hasNext()) {
			                CTContactSummaryDT ctContactSummaryDT = (CTContactSummaryDT)(itContact.next());
			                CTContactProxyVO ctContactProxyVO = getContactProxyVO(ctContactSummaryDT.getCtContactUid(), nbsSecurityObj);
			                if(ctContactProxyVO.getcTContactVO().getcTContactDT().getSubjectEntityPhcUid().compareTo(publicHealthCaseUID)==0)
			                	ctContactProxyVO.setItDelete(true);
			                else{
			                	ctContactProxyVO.getcTContactVO().getcTContactDT().setContactEntityPhcUid(null);
			                  	ctContactProxyVO.setItDirty(true);
			                  	ctContactProxyVO.getcTContactVO().setItDirty(true);
			                  	ctContactProxyVO.getcTContactVO().getcTContactDT().setItDirty(true);

			                }
			                setContactProxyVO(ctContactProxyVO, nbsSecurityObj);
			            }
			        }
			    } catch(Exception e) {
			    	logger.error("Exception occurred while Logically deleting the associated Contacts, publicHealthCaseUID: " + publicHealthCaseUID + ", " + e.getMessage(), e);
					throw new NEDSSSystemException(e.getMessage(), e);
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
				.debug("PersonUid value returned by PamProxy.deletePamProxy(): "
						+ personUid);
				if(pamProxyVO.getTheTreatmentSummaryVOCollection()!=null){
					Iterator<Object>  anTreatmentIterator = null;
				     for (anTreatmentIterator = pamProxyVO.getTheTreatmentSummaryVOCollection().iterator(); anTreatmentIterator.hasNext(); ) {
					      TreatmentSummaryVO treatmentSummaryVO = (TreatmentSummaryVO) anTreatmentIterator.next();

					      TreatmentDT treatmentDT = actController.getTreatmentInfo(
					    		                                    treatmentSummaryVO.getTreatmentUid(),
					                                                nbsSecurityObj);
					      treatmentDT.setItDelete(false);
					      treatmentDT.setItDirty(true);

					      String BUSINESS_OBJECT_LOOKUP_NAME = NBSBOLookup.TREATMENT;
					      String BUSINESS_TRIGGER_CD = NEDSSConstants.TRT_DEL;
					      String TABLE_NAME = DataTables.TREATMENT_TABLE;
					      String MODULE_CD = NEDSSConstants.BASE;

					      TreatmentDT newTreatmentDT = (TreatmentDT) prepareVOUtils.prepareVO(
					          treatmentDT, BUSINESS_OBJECT_LOOKUP_NAME, BUSINESS_TRIGGER_CD,
					          TABLE_NAME, MODULE_CD, nbsSecurityObj);

				          actController.setTreatmentInfo(newTreatmentDT, nbsSecurityObj);
				     }
					}
				PamRootDAO pamRootDAO = new PamRootDAO();
				pamRootDAO.UpdateFordeletePamVO(pamProxyVO.getPamVO(), pamProxyVO
							.getPublicHealthCaseVO());

				return returnMap;
			} else {
				returnMap.put(NEDSSConstants.SECURITY_FAIL,
						NEDSSConstants.SECURITY_FAIL);
				return returnMap;
			}
		} catch (ClassCastException e) {
			// TODO Auto-generated catch block
			logger.error("ClassCastException occurred  in PamProxyEJB.deletePamProxy: " + e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
	}

	public void ejbActivate() {

	}

	/**
	 * Container generated method
	 *
	 * @throws java.rmi.RemoteException
	 * @throws javax.ejb.CreateException
	 */
	public void ejbCreate() throws java.rmi.RemoteException,
	javax.ejb.CreateException {
	}

	public void ejbPassivate() {

	}

	public void ejbRemove() {

	}

	/**
	 * Gets the PamProxyVO Object from database
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
	public PamProxyVO getPamProxy(Long publicHealthCaseUID,
			NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException,
			javax.ejb.EJBException, NEDSSSystemException,
			javax.ejb.FinderException, javax.ejb.CreateException {

		if (!nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,
				NBSOperationLookup.VIEW)) {
			logger.info("nbsSecurityObj.getPermission(NedssBOLookup.INVESTIGATION,NBSOperationLookup.VIEW) is false");
			throw new NEDSSSystemException("NO PERMISSIONS");
		}
		logger.info("nbsSecurityObj.getPermission(NedssBOLookup.INVESTIGATION,NBSOperationLookup.VIEW) is true");
		pamProxyVO = new PamProxyVO();

		PublicHealthCaseVO thePublicHealthCaseVO = null;

		ArrayList<Object>  thePersonVOCollection  = new ArrayList<Object> ();
		ArrayList<Object>  theOrganizationVOCollection  = new ArrayList<Object> ();
		ArrayList<Object>  theMaterialVOCollection  = new ArrayList<Object> ();
		ArrayList<Object>  theInterventionVOCollection  = new ArrayList<Object> ();

		// Summary Collections
		ArrayList<Object>  theVaccinationSummaryVOCollection  = new ArrayList<Object> ();
		ArrayList<Object>  theTreatmentSummaryVOCollection  = new ArrayList<Object> ();
		ArrayList<Object>  theInvestigationAuditLogSummaryVOCollection  = new ArrayList<Object> (); //civil00014862

		 ArrayList<Object>  theDocumentSummaryVOCollection  = new ArrayList<Object> (); 
		 

		NedssUtils nedssUtils = new NedssUtils();
		Object theLookedUpObject;

		try {
			logger.debug("* before nedssUtils.lookupBean(JNDINames.ActControllerEJB)");
			// Reference an Act controller to use later
			theLookedUpObject = nedssUtils
			.lookupBean(JNDINames.ActControllerEJB);
			ActControllerHome actHome = (ActControllerHome) PortableRemoteObject
			.narrow(theLookedUpObject, ActControllerHome.class);
			ActController actController = actHome.create();

			logger.debug("* before nedssUtils.lookupBean(JNDINames.EntityControllerEJB");
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
				logger.info("nbsSecurityObj.checkDataAccess(thePublicHealthCaseVO.getThePublicHealthCaseDT(), NedssBOLookup.INVESTIGATION, NBSOperationLookup.VIEW) is false");
				throw new NEDSSSystemException("NO ACCESS PERMISSIONS");
			}
			logger.info("nbsSecurityObj.checkDataAccess(thePublicHealthCaseVO.getThePublicHealthCaseDT(), NedssBOLookup.INVESTIGATION, NBSOperationLookup.VIEW) is true");

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
			PamVO pamVO = pamRootDAO.getPamVO(publicHealthCaseUID);
			pamProxyVO.setPamVO(pamVO);
			String strTypeCd;
			String strClassCd;
			String recordStatusCd = "";
			Long nEntityID;
			ParticipationDT participationDT = null;

			Iterator<Object>  participationIterator = thePublicHealthCaseVO
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

			ActRelationshipDT actRelationshipDT = null;
			// Get the Vaccinations for a PublicHealthCase/Investigation
			Iterator<Object>  actRelationshipIterator = thePublicHealthCaseVO
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
						Collection<Object>  intPartDTs = interventionVO
						.getTheParticipationDTCollection();
						Iterator<Object>  intPartIter = intPartDTs.iterator();
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
					.debug("PamProxyEJB.getInvestigation: check for nulls: SourceActUID"
							+ nSourceActID + " classCd: " + strClassCd);
					continue;
				}
			}

			pamProxyVO.setTheOrganizationVOCollection(theOrganizationVOCollection);
			pamProxyVO.setPublicHealthCaseVO(thePublicHealthCaseVO);
			pamProxyVO.setThePersonVOCollection(thePersonVOCollection);

			Collection<Object>  labSumVOCol = new ArrayList<Object> ();
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

				Collection<Object>  LabReportUidSummarVOs = new ObservationSummaryDAOImpl()
				.findAllActiveLabReportUidListForManage(publicHealthCaseUID, labReportViewClause);
				
				String uidType = "LABORATORY_UID";
		        Collection<Object>  newLabReportSummaryVOCollection  = new ArrayList<Object> ();
		        Collection<?>  labReportSummaryVOCollection  = new ArrayList<Object> ();
		        LabReportSummaryVO labReportSummaryVOs = new LabReportSummaryVO();
		        Collection<Object>  labColl = new ArrayList<Object> ();
				if (LabReportUidSummarVOs != null && LabReportUidSummarVOs.size() > 0) {
					//labSumVOCol = new ObservationProcessor().retrieveLabReportSummary(LabReportUidSummarVOs,
							//nbsSecurityObj);

		         
			        	labSumVOMap = new ObservationProcessor().retrieveLabReportSummaryRevisited(
			        												LabReportUidSummarVOs, false, nbsSecurityObj, uidType);
			        	if(labSumVOMap !=null)
			        	{
			        		
			        		if(labSumVOMap.containsKey("labEventList"))
			        		{
			        		  labReportSummaryVOCollection  = (ArrayList<?> )labSumVOMap.get("labEventList");
			        		 Iterator<?>  iterator = labReportSummaryVOCollection.iterator();
			          		  while( iterator.hasNext())
			          		  {
			          			 labReportSummaryVOs = (LabReportSummaryVO) iterator. next();
			          			 labSumVOCol.add(labReportSummaryVOs);
			          			 
			          		  }
			        		}
			        	}

			          logger.debug("Size of labreport Collection<Object>  :" + labSumVOCol.size());
			        

					logger.debug("Size of labreport Collection<Object>  :"
							+ labSumVOCol.size());
				}
			} else {
				logger
				.debug("user has no permission to view ObservationSummaryVO collection");

			}

			if (labSumVOCol != null) {
				pamProxyVO.setTheLabReportSummaryVOCollection(labSumVOCol);

			}

			Collection<Object>  morbSumVOCol = new ArrayList<Object> ();
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
				Collection<Object>  morbReportUidSummarVOs = new ObservationSummaryDAOImpl()
				.findAllActiveMorbReportUidListForManage(
						publicHealthCaseUID, morbReportViewClause);
				
				String uidType = "MORBIDITY_UID";
		        Collection<Object>  newMobReportSummaryVOCollection  = new ArrayList<Object> ();
		        Collection<?>  mobReportSummaryVOCollection  = new ArrayList<Object> ();
		        MorbReportSummaryVO mobReportSummaryVOs = new MorbReportSummaryVO();
		        HashMap<Object, Object> morbSumVoMap = new HashMap<Object, Object>();
				if (morbReportUidSummarVOs != null && morbReportUidSummarVOs.size() > 0) {
					//morbSumVOCol = new ObservationProcessor().retrieveMorbReportSummary(morbReportUidSummarVOs,
							//nbsSecurityObj);
					
					morbSumVoMap = new ObservationProcessor().retrieveMorbReportSummaryRevisited(morbReportUidSummarVOs,false, nbsSecurityObj, uidType);
		          if(morbSumVoMap !=null)
		      	{
		      		
		      		if(morbSumVoMap.containsKey("MorbEventColl"))
		      		{
		      			mobReportSummaryVOCollection  = (ArrayList<?> )morbSumVoMap.get("MorbEventColl");
		      		  Iterator<?>  iterator = mobReportSummaryVOCollection.iterator();
		        		  while( iterator.hasNext())
		        		  {
		        			  mobReportSummaryVOs = (MorbReportSummaryVO) iterator. next();
		        			  morbSumVOCol.add(mobReportSummaryVOs);
		        			 
		        		  }
		      		}
		      	}
					logger.debug("Size of Morbidity Collection<Object>  :"
							+ morbSumVOCol.size());
				}
			} else {
				logger
				.debug("user has no permission to view ObservationSummaryVO collection");
			}
			if (morbSumVOCol != null) {
				pamProxyVO.setTheMorbReportSummaryVOCollection(morbSumVOCol);

			}

			if (nbsSecurityObj.getPermission(
					NBSBOLookup.INTERVENTIONVACCINERECORD,
					NBSOperationLookup.VIEW)) {
				RetrieveSummaryVO retrievePhcVaccinations = new RetrieveSummaryVO();
				theVaccinationSummaryVOCollection  = new ArrayList<Object> (
						retrievePhcVaccinations
						.retrieveVaccinationSummaryVOForInv(
								publicHealthCaseUID, nbsSecurityObj)
								.values());
				pamProxyVO
				.setTheVaccinationSummaryVOCollection(theVaccinationSummaryVOCollection);
			} else {
				logger
				.debug("user has no permission to view VaccinationSummaryVO collection");
			}
			pamProxyVO.setTheNotificationSummaryVOCollection(RetrieveSummaryVO.
			          notificationSummaryOnInvestigation(thePublicHealthCaseVO, pamProxyVO,
			                                             nbsSecurityObj));

			if(pamProxyVO.getTheNotificationSummaryVOCollection()!=null){
				Iterator<Object>  it = pamProxyVO.getTheNotificationSummaryVOCollection().iterator();
				while(it.hasNext()){
					NotificationSummaryVO notifVO = (NotificationSummaryVO)it.next();
					Iterator<Object>  actIterator = pamProxyVO.getPublicHealthCaseVO().getTheActRelationshipDTCollection().iterator();
					while(actIterator.hasNext()){
						ActRelationshipDT actRelationDT = (ActRelationshipDT)actIterator.next();
						if((notifVO.getCdNotif().equalsIgnoreCase(NEDSSConstants.CLASS_CD_SHARE_NOTF) || notifVO.getCdNotif().equalsIgnoreCase(NEDSSConstants.CLASS_CD_SHARE_NOTF_PHDC)) && 
								notifVO.getNotificationUid().compareTo(actRelationDT.getSourceActUid())==0){
							actRelationDT.setShareInd(true);
						}
						if((notifVO.getCdNotif().equalsIgnoreCase(NEDSSConstants.CLASS_CD_EXP_NOTF) || notifVO.getCdNotif().equalsIgnoreCase(NEDSSConstants.CLASS_CD_EXP_NOTF_PHDC)) && 
								notifVO.getNotificationUid().compareTo(actRelationDT.getSourceActUid())==0){
							actRelationDT.setExportInd(true);
						}
						if((notifVO.getCdNotif().equalsIgnoreCase(NEDSSConstants.CLASS_CD_NOTF) || notifVO.getCdNotif().equalsIgnoreCase(NEDSSConstants.CLASS_CD_EXP_NOTF_PHDC)) && 
								notifVO.getNotificationUid().compareTo(actRelationDT.getSourceActUid())==0){
							actRelationDT.setNNDInd(true);
						}
					}
				}
			}
			// Begin support for TreatmentSummary
			if (nbsSecurityObj.getPermission(NBSBOLookup.TREATMENT,
					NBSOperationLookup.VIEW,
					ProgramAreaJurisdictionUtil.ANY_PROGRAM_AREA,
					ProgramAreaJurisdictionUtil.ANY_JURISDICTION)) {

				logger
				.debug("About to get TreatmentSummaryList for Investigation");
				RetrieveSummaryVO rsvo = new RetrieveSummaryVO();
				theTreatmentSummaryVOCollection  = new ArrayList<Object> ((rsvo
						.retrieveTreatmentSummaryVOForInv(publicHealthCaseUID,
								nbsSecurityObj)).values());
				logger.debug("Number of treatments found: "
						+ theTreatmentSummaryVOCollection.size());
				pamProxyVO
				.setTheTreatmentSummaryVOCollection(theTreatmentSummaryVOCollection);
			} else {
				logger
				.debug("user has no permission to view TreatmentSummaryVO collection");
			}

			// Added this for Investigation audit log summary on the RVCT Page(civil00014862)
			if (nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,
					NBSOperationLookup.VIEW)) {

				logger.debug("About to get AuditLogSummary for Investigation");
				RetrieveSummaryVO summaryVO = new RetrieveSummaryVO();
				theInvestigationAuditLogSummaryVOCollection  = new ArrayList<Object> ((summaryVO.
						retrieveInvestigationAuditLogSummaryVO
						(publicHealthCaseUID,nbsSecurityObj)));



				logger.debug("Number of Investigation Auditlog summary found: "
						+ theInvestigationAuditLogSummaryVOCollection.size());
				pamProxyVO.setTheInvestigationAuditLogSummaryVOCollection(theInvestigationAuditLogSummaryVOCollection);
			} else {
				logger.debug("user has no permission to view InvestigationAuditLogSummaryVO collection");
			}

			// End (civil00014862)

			// Begin support for Document Summary Section
			if (nbsSecurityObj.getPermission(NBSBOLookup.DOCUMENT,
		              NBSOperationLookup.VIEW)) {
				RetrieveSummaryVO retrievePhcVaccinations = new RetrieveSummaryVO();
				theDocumentSummaryVOCollection  = new ArrayList<Object> (
				retrievePhcVaccinations.retrieveDocumentSummaryVOForInv(
				publicHealthCaseUID, nbsSecurityObj).values());
				pamProxyVO.setTheDocumentSummaryVOCollection(theDocumentSummaryVOCollection);
			}
			else {
			logger.debug(
			"user has no permission to view DocumentSummaryVO collection");
			}
			if (nbsSecurityObj.getPermission(NBSBOLookup.CT_CONTACT,
		              NBSOperationLookup.VIEW)) {
				CTContactSummaryDAO cTContactSummaryDAO = new CTContactSummaryDAO();
				Collection<Object> contactCollection= cTContactSummaryDAO.getContactListForInvestigation(publicHealthCaseUID, nbsSecurityObj);
	             
				pamProxyVO.setTheCTContactSummaryDTCollection(contactCollection);
			}
			else {
			logger.debug(
			"user has no permission to view Contact Summary collection");
			}
			if (nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,
					NBSOperationLookup.VIEW)) {
				NBSAttachmentNoteDAOImpl nbsAttachmentDAO = new NBSAttachmentNoteDAOImpl();
				Collection<Object> nbsCaseAttachmentDTColl = nbsAttachmentDAO.getNbsAttachmentCollection(publicHealthCaseUID);   
				pamProxyVO.setNbsAttachmentDTColl(nbsCaseAttachmentDTColl);  
				Collection<Object>  nbsCaseNotesColl = nbsAttachmentDAO.getNbsNoteCollection(publicHealthCaseUID);
				pamProxyVO.setNbsNoteDTColl(nbsCaseNotesColl);
			} else {
				logger
				.debug("user has no permission to view Investigation : Attachments and Notes are secured by Investigation View Permission");
			}


		} catch (Exception e) {
			logger.fatal("Exception in PamProxyEJB.getPamProxy, publicHealthCaseUID: " + publicHealthCaseUID + ", " + e.getMessage(), e);
			throw new java.rmi.RemoteException(e.getMessage(), e);
		}
		return pamProxyVO;
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
			logger.fatal("Unable to retrieve PersonUid of Patient Revision for the Vaccination",
					e);
			logger.debug("This record cannot be deleted");
			logger.fatal("Exception in PamProxyEJB.getPersonUidOfInvestigation, investigationUid: " + investigationUid + ", " + e.getMessage(), e);
			
		} finally {
			try {
				resultSet.close();
				preparedStmt.close();
				dbConnection.close();
			} catch (SQLException sqlex) {
				sqlex.printStackTrace();
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
			logger.fatal("Exception in PamProxyEJB.insertActRelationshipHistory, SourceActUid: " + dt.getSourceActUid() + ", TargetActUid: " + dt.getTargetActUid() + ", " + e.getMessage(), e);
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
			logger.fatal("Exception in PamProxyEJB.insertParticipationHistory, TypeCd: " + dt.getTypeCd() + ", TargetActUid: " + dt.getActUid() + ", SubjectEntityUid: " + dt.getSubjectEntityUid() + ", " + e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
	}

	/**
	 *
	 * @param publicHealthUID
	 * @param nbsSecurityObj
	 * @return
	 * @throws NEDSSSystemException
	 */
	public Collection<Object>  retrieveNotificationSummaryListForInvestigation(
			Long publicHealthUID, NBSSecurityObj nbsSecurityObj)
	throws NEDSSSystemException {
		try {
			RetrieveSummaryVO retrieveSummaryVO = new RetrieveSummaryVO();
			ArrayList<Object>  theNotificationSummaryVOCollection  = (ArrayList<Object> ) retrieveSummaryVO
			.retrieveNotificationSummaryListForInvestigation(
					publicHealthUID, nbsSecurityObj);
			return theNotificationSummaryVOCollection;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("Exception in PamProxyEJB.retrieveNotificationSummaryListForInvestigation, publicHealthUID: " + publicHealthUID + ", " + e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
	}

	/**
	 * Converts negative UIDs to positive UIDs
	 *
	 * @param pamProxyVO
	 * @param falseUid
	 * @param actualUid
	 */
	private void setFalseToNew(PamProxyVO pamProxyVO, Long falseUid, Long actualUid) {
		try {
			Iterator<Object>  anIterator = null;

			ParticipationDT participationDT = null;
			ActRelationshipDT actRelationshipDT = null;
			NbsActEntityDT pamCaseEntityDT = null;
			Collection<Object>  participationColl = pamProxyVO.getTheParticipationDTCollection();
			Collection<Object>  actRelationShipColl = pamProxyVO
			.getPublicHealthCaseVO().getTheActRelationshipDTCollection();
			Collection<Object>  pamCaseEntityColl = pamProxyVO.getPamVO().getActEntityDTCollection();
			if (participationColl != null) {
				for (anIterator = participationColl.iterator(); anIterator.hasNext();) {
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
				for (anIterator = actRelationShipColl.iterator(); anIterator.hasNext();) {
					actRelationshipDT = (ActRelationshipDT) anIterator.next();

					if (actRelationshipDT.getTargetActUid().compareTo(falseUid) == 0) {
						actRelationshipDT.setTargetActUid(actualUid);
					}
					if (actRelationshipDT.getSourceActUid().compareTo(falseUid) == 0) {
						actRelationshipDT.setSourceActUid(actualUid);
					}
					logger.debug("ActRelationShipDT: falseUid "
							+ falseUid.toString() + " actualUid: " + actualUid);
				}
			}
			if (pamCaseEntityColl != null) {
				for (anIterator = pamCaseEntityColl.iterator(); anIterator.hasNext();) {
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
			logger.fatal("Exception occured in PamProxyEJB.setFalseToNew, falseUid: " + falseUid + ", actualUid: " + actualUid + ", " + e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);

		}
	}

	/**
	 * Description: Stores the PamProxyVO Object to database
	 *
	 * @param pamProxyVO
	 * @param nbsSecurityObj
	 * @return public_health_case_uid
	 * @throws java.rmi.RemoteException
	 * @throws javax.ejb.EJBException
	 * @throws javax.ejb.CreateException
	 * @throws NEDSSSystemException
	 * @throws NEDSSConcurrentDataException
	 */
	public Long setPamProxy(PamProxyVO pamProxyVO, NBSSecurityObj nbsSecurityObj)
	throws java.rmi.RemoteException, javax.ejb.EJBException,
	javax.ejb.CreateException, NEDSSSystemException,
	NEDSSConcurrentDataException {

		PublicHealthCaseDT phcDT = pamProxyVO.getPublicHealthCaseVO()
		.getThePublicHealthCaseDT();

		// if both are false throw exception
		if ((!pamProxyVO.isItNew()) && (!pamProxyVO.isItDirty())) {
			logger.info("pamProxyVO.isItNew() = " + pamProxyVO.isItNew()
					+ " and pamProxyVO.isItDirty() = "
					+ pamProxyVO.isItDirty());
			throw new NEDSSSystemException("pamProxyVO.isItNew() = "
					+ pamProxyVO.isItNew() + " and pamProxyVO.isItDirty() = "
					+ pamProxyVO.isItDirty() + " for setPamProxy");
		}
		logger.info("pamProxyVO.isItNew() = " + pamProxyVO.isItNew()
				+ " and pamProxyVO.isItDirty() = " + pamProxyVO.isItDirty());

		if (pamProxyVO.isItNew()) {
			logger
			.info("pamProxyVO.isItNew() = " + pamProxyVO.isItNew()
					+ " and pamProxyVO.isItDirty() = "
					+ pamProxyVO.isItDirty());

			boolean checkInvestigationAutoCreatePermission = nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,
					NBSOperationLookup.AUTOCREATE, phcDT.getProgAreaCd(),	ProgramAreaJurisdictionUtil.ANY_JURISDICTION, phcDT.getSharedInd());

			if (!nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,	NBSOperationLookup.ADD, phcDT.getProgAreaCd(), ProgramAreaJurisdictionUtil.ANY_JURISDICTION, phcDT	.getSharedInd())
					&& !(checkInvestigationAutoCreatePermission)) {
				logger.info("no add permissions for setPamProxy");
				throw new NEDSSSystemException("NO ADD PERMISSIONS for setPamProxy");
			}
			logger.info("user has add permissions for setPamProxy");
		} else if (pamProxyVO.isItDirty()) {
			if (!nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION, NBSOperationLookup.EDIT, phcDT.getProgAreaCd(), phcDT.getJurisdictionCd(), phcDT.getSharedInd())) {
				logger.info("no edit permissions for setPamProxy");
				throw new NEDSSSystemException("NO EDIT PERMISSIONS for setPamProxy");
			}
		}

		logger.info("pamProxyVO.isItDirty() = " + pamProxyVO.isItDirty()	+ " and user has edit permissions for setPamProxy");
	    NNDMessageSenderHelper nndMessageSenderHelper = NNDMessageSenderHelper.
        getInstance();
	    //uncomment to test autoresend notification
	    if(pamProxyVO.isItDirty()){
	      try {
	      	  //update auto resend notifications
	        nndMessageSenderHelper.updateAutoResendNotificationsAsync(pamProxyVO, nbsSecurityObj);
	      }
	      catch (Exception e) {
	        NNDActivityLogDT nndActivityLogDT = new NNDActivityLogDT();
	        String phcLocalId = pamProxyVO.getPublicHealthCaseVO().
	            getThePublicHealthCaseDT().getLocalId();
	        nndActivityLogDT.setErrorMessageTxt(e.toString());
	        if (phcLocalId != null)
	          nndActivityLogDT.setLocalId(phcLocalId);
	        else
	          nndActivityLogDT.setLocalId("N/A");
	          //catch & store auto resend notifications exceptions in NNDActivityLog table
	        nndMessageSenderHelper.persistNNDActivityLog(nndActivityLogDT,
	            nbsSecurityObj);
	        logger.error("Exception occurred while calling nndMessageSenderHelper.updateAutoResendNotificationsAsync, phcLocalId: " + phcLocalId + ", " + e.getMessage(), e);
	        e.printStackTrace();
	        throw new NEDSSSystemException(e.getMessage(), e);
	      }
	    }
		if (pamProxyVO.isItNew() && (!pamProxyVO.isItDirty())) {
			// changes according to new Analysis
			String classCd;
			Long entityUID;
			String recordStatusCd;
			ParticipationDT partDT = null;
			Iterator<Object>  partIter = pamProxyVO	.getTheParticipationDTCollection().iterator();

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

						EntityControllerHome home = (EntityControllerHome) PortableRemoteObject.narrow(obj, EntityControllerHome.class);
						logger.debug("Found EntityControllerHome: " + home);
						entityController = home.create();

						PersonVO personVO = entityController
						.getPerson(entityUID, nbsSecurityObj);
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

		Long actualUid = null;
		PersonVO personVO = null;

		Iterator<Object>  anIterator = null;
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

			if (pamProxyVO.getThePersonVOCollection() != null) {
				for (anIterator = pamProxyVO.getThePersonVOCollection()
						.iterator(); anIterator.hasNext();) {

					personVO = (PersonVO) anIterator.next();
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
							} catch (NEDSSConcurrentDataException ex) {
								logger.fatal("NEDSSConcurrentDataException occurred in PamProxyEJB.setPamProxy, realUid: " + realUid + ", " + ex.getMessage(),ex);
								// cntx.setRollbackOnly();
								throw new NEDSSConcurrentDataException(ex.getMessage(),ex);
							} catch (Exception ex) {
								logger.fatal("Error in executing entityController.setPatientRevision when personVO.isNew is true, realUid: " + realUid + ", " + ex.getMessage(),ex);
								ex.printStackTrace();
								throw new EJBException(ex.getMessage(),ex);
							}
						} else if (personVO.getThePersonDT().getCd().equals(
								NEDSSConstants.PRV)) { // Provider
							String businessTriggerCd = NEDSSConstants.PRV_CR;
							try {
								realUid = entityController.setProvider(
										personVO, businessTriggerCd,
										nbsSecurityObj);
							} catch (NEDSSConcurrentDataException ex) {
								logger.fatal("NEDSSConcurrentDataException occurred in PamProxyEJB.setPamProxy, realUid: " + realUid + ", " + ex.getMessage(),ex);
								// cntx.setRollbackOnly();
								throw new NEDSSConcurrentDataException(ex.getMessage(),ex);
							} catch (Exception ex) {
								logger.fatal("Error in executing entityController.setProvider when personVO.isNew is true, realUid: " + realUid + ", "  + ex.getMessage(),ex);
								ex.printStackTrace();
								throw new EJBException(ex.getMessage(),ex);
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
							setFalseToNew(pamProxyVO, falseUid, realUid);
						}
					} else if (personVO.isItDirty()) {
						if (personVO.getThePersonDT().getCd().equals(
								NEDSSConstants.PAT)) {
							String businessTriggerCd = NEDSSConstants.PAT_EDIT;
							try {
								realUid = entityController.setPatientRevision(
										personVO, businessTriggerCd,
										nbsSecurityObj);
							} catch (NEDSSConcurrentDataException ex) {
								logger.fatal("NEDSSConcurrentDataException occurred in PamProxyEJB.setPamProxy, realUid: " + realUid + ", " + ex.getMessage(),ex);
								throw new EJBException(ex.getMessage(),ex);
							} catch (Exception ex) {
								logger.fatal("Error in executing entityController.setPatientRevision when personVO.isDirty is true, realUid: " + realUid + ", " + ex.getMessage(),ex);
								ex.printStackTrace();
								throw new EJBException(ex.getMessage(),ex);
							}
						} else if (personVO.getThePersonDT().getCd().equals(
								NEDSSConstants.PRV)) { // Provider
							String businessTriggerCd = NEDSSConstants.PRV_EDIT;
							try {
								realUid = entityController.setProvider(
										personVO, businessTriggerCd,
										nbsSecurityObj);
							} catch (NEDSSConcurrentDataException ex) {
								logger.fatal("NEDSSConcurrentDataException occurred in PamProxyEJB.setPamProxy, realUid: " + realUid + ", " + ex.getMessage(),ex);
								throw new EJBException(ex.getMessage(),ex);
							} catch (Exception ex) {
								logger.fatal("Error in executing entityController.setPatientRevision when personVO.isDirty is true, realUid: " + realUid + ", " + ex.getMessage(),ex);
								ex.printStackTrace();
								throw new EJBException(ex.getMessage(),ex);
							}

						} // end of else
						logger.debug("The realUid for the Patient/Provider is: "
								+ realUid);

					}
				} // end of for
			} // end of if(pamProxyVO.getThePersonVOCollection() != null)

			if (pamProxyVO.getPublicHealthCaseVO() != null) {
				String businessTriggerCd = null;
				PublicHealthCaseVO publicHealthCaseVO = pamProxyVO
				.getPublicHealthCaseVO();
				publicHealthCaseVO.getThePublicHealthCaseDT().setPamCase(true);
				NbsHistoryDAO nbsHistoryDAO= new NbsHistoryDAO();
				nbsHistoryDAO.getPamHistory(pamProxyVO.getPublicHealthCaseVO());
				PublicHealthCaseDT publicHealthCaseDT = publicHealthCaseVO
				.getThePublicHealthCaseDT();
				RootDTInterface rootDTInterface = publicHealthCaseDT;
				String businessObjLookupName = NBSBOLookup.INVESTIGATION;
				if (pamProxyVO.isItNew()) {
					businessTriggerCd = "INV_CR";
				} else if (pamProxyVO.isItDirty()) {
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
				logger.debug("actualUid = " + actualUid);
				if (falsePublicHealthCaseUid < 0) {
					logger.debug("falsePublicHealthCaseUid = "
							+ falsePublicHealthCaseUid);
					setFalseToNew(pamProxyVO, falsePublicHealthCaseUid,
							actualUid);
					publicHealthCaseVO.getThePublicHealthCaseDT()
					.setPublicHealthCaseUid(actualUid);
				}
				Long publicHealthCaseUid =publicHealthCaseVO.getThePublicHealthCaseDT().getPublicHealthCaseUid();
				
				logger.debug("falsePublicHealthCaseUid = "
						+ falsePublicHealthCaseUid);
			}
			if( pamProxyVO.isUnsavedNote() && pamProxyVO.getNbsNoteDTColl()!=null && pamProxyVO.getNbsNoteDTColl().size()>0){
				PageCaseUtil.storeNotes(actualUid, pamProxyVO.getNbsNoteDTColl());
				
			}
		      // this collection should only be populated in edit scenario, xz defect 11861 (10/01/04)
		      if (pamProxyVO.getTheNotificationSummaryVOCollection() != null) {
		      	Collection<Object>  notSumVOColl = pamProxyVO.getTheNotificationSummaryVOCollection();
		       Iterator<Object>  notSumIter =  notSumVOColl.iterator();
		        while(notSumIter.hasNext()){
		          NotificationSummaryVO notSummaryVO = (NotificationSummaryVO)notSumIter.next();
		          // Only handles notifications that are not history and not in auto-resend status.
		          // for auto resend, it'll be handled separately.  xz defect 11861 (10/07/04)
		          if(notSummaryVO.getIsHistory().equals("F") && notSummaryVO.getAutoResendInd().equals("F")) {
		            Long notificationUid = notSummaryVO.getNotificationUid();
		            String phcCd = phcDT.getCd();
		            String phcClassCd = phcDT.getCaseClassCd();
		            String progAreaCd = phcDT.getProgAreaCd();
		            String jurisdictionCd = phcDT.getJurisdictionCd();
		            String sharedInd = phcDT.getSharedInd();
		            String notificationRecordStatusCode = notSummaryVO.getRecordStatusCd();
		            if(notificationRecordStatusCode != null){
		            	String trigCd = null;

		            	/* The notification status remains same when the
		            	 * Investigation or Associated objects are changed
		            	 */
		        		if (notificationRecordStatusCode
		        				.equalsIgnoreCase(NEDSSConstants.APPROVED_STATUS)){
		        				trigCd = NEDSSConstants.NOT_CR_APR;
		        		}

		            	// change from pending approval to approved
		    			if (notificationRecordStatusCode
		    				.equalsIgnoreCase(NEDSSConstants.PENDING_APPROVAL_STATUS)){
		    				trigCd = NEDSSConstants.NOT_CR_PEND_APR;
		    			}
		              	if(trigCd != null){
		              		// we only need to update notification when trigCd is not null
		              		RetrieveSummaryVO. updateNotification(notificationUid,trigCd,phcCd,phcClassCd,progAreaCd,jurisdictionCd,sharedInd,nbsSecurityObj);
		                }

		            }
		          }
		        }
		      }
		    Long docUid = null;
			if (pamProxyVO.getPublicHealthCaseVO()
					.getTheActRelationshipDTCollection() != null) {
				actRelationshipDAOImpl = (ActRelationshipDAOImpl) NEDSSDAOFactory
				.getDAO(JNDINames.ACT_RELATIONSHIP_DAO_CLASS);
				for (anIterator = pamProxyVO.getPublicHealthCaseVO()
						.getTheActRelationshipDTCollection().iterator(); anIterator
						.hasNext();) {
					ActRelationshipDT actRelationshipDT = (ActRelationshipDT) anIterator
					.next();
					 if(actRelationshipDT.getTypeCd() != null && actRelationshipDT.getTypeCd().equals(NEDSSConstants.DocToPHC))
			        	  docUid  = actRelationshipDT.getSourceActUid();
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
						logger.fatal("ActUid: "	+ actRelationshipDT.getTargetActUid() + ", nbsSecurityObj.getFullName: " + nbsSecurityObj.getFullName() + ", " + e.getMessage(), e);
						e.printStackTrace();
						throw new EJBException(e.getMessage(),e);
					}
				}
			}
		    /* 
		       * Updating the Document table
		       */ 
		      //Getting the DocumentEJB reference
		      if(docUid != null){
			      try{
				      NbsDocument nbsDocument = null;
				      Object docEJB = nedssUtils.lookupBean(JNDINames.NBS_DOCUMENT_EJB);
				      logger.debug("DocumentEJB lookup = " + docEJB.toString());
				      NbsDocumentHome dochome = (NbsDocumentHome) PortableRemoteObject.
				          narrow(docEJB, NbsDocumentHome.class);
				      logger.debug("Found NbsDocumentHome: " + dochome);
				      nbsDocument = dochome.create(); 
				      //get the 
				      NBSDocumentVO nbsDocVO = nbsDocument.getNBSDocumentWithoutActRelationship(docUid, nbsSecurityObj);
				      if(nbsDocVO.getNbsDocumentDT().getJurisdictionCd()==null || (nbsDocVO.getNbsDocumentDT().getJurisdictionCd()!=null && nbsDocVO.getNbsDocumentDT().getJurisdictionCd().equals("")))
				    	  nbsDocVO.getNbsDocumentDT().setJurisdictionCd(pamProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getJurisdictionCd());
				      Long nbsDocumentUid = nbsDocument.updateDocumentWithOutthePatient(nbsDocVO, nbsSecurityObj);
			      }catch(Exception e){
			    	  logger.error("Error while updating the Document table, docUid: " + docUid + ", " + e.getMessage(), e.getMessage(),e);
			          e.printStackTrace();
			          throw new javax.ejb.EJBException(e.getMessage(),e);
			      }
		      }
			
			if (pamProxyVO.getTheParticipationDTCollection() != null) {
				logger.debug("got the participation Collection<Object>  Loop");
				participationDAOImpl = (ParticipationDAOImpl) NEDSSDAOFactory
				.getDAO(JNDINames.ACT_PARTICIPATION_DAO_CLASS);
				logger.debug("got the participation Collection<Object>  Loop got the DAO");
				for (anIterator = pamProxyVO.getTheParticipationDTCollection().iterator(); anIterator
						.hasNext();) {
					logger.debug("got the participation Collection<Object>  FOR Loop");
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
						logger.fatal("Exception occured in PamProxy.setPamProxy, subjectEntityUid: " + participationDT.getSubjectEntityUid() + ", nbsSecurityObj.getFullName: " + nbsSecurityObj.getFullName() + ", " + e.getMessage(), e);
						e.printStackTrace();
						throw new EJBException(e.getMessage(),e);
					}
				}
			}
			if (pamProxyVO.getPamVO() != null && pamProxyVO.isItNew()) {
				PamRootDAO pamRootDAO = new PamRootDAO();
				pamRootDAO.insertPamVO(pamProxyVO.getPamVO(), pamProxyVO
						.getPublicHealthCaseVO());
			} else if (pamProxyVO.getPamVO() != null && pamProxyVO.isItDirty()) {
				PamRootDAO pamRootDAO = new PamRootDAO();
				pamRootDAO.editPamVO(pamProxyVO.getPamVO(), pamProxyVO
						.getPublicHealthCaseVO());

			}
			else
				logger.error("There is error in setPamProxyVO as pamProxyVO.getPamVO() is null");

			logger.debug("the actual Uid for PamProxy Publichealthcase is "
					+ actualUid);
		} catch (NEDSSConcurrentDataException ex) {
			logger.fatal("NEDSSConcurrentDataException occurred in PamProxyEJB.setPamProxy" + ex.getMessage(),ex);
			throw new NEDSSConcurrentDataException(ex.getMessage(),ex);
		} catch (Exception e) {
			logger.fatal("Exception occured in PamProxy.setPamProxy, nbsSecurityObj: " + nbsSecurityObj.getFullName() + ", " + e.getMessage(), e);
			e.printStackTrace();
			throw new EJBException(e.getMessage(),e);
		}
		return actualUid;
	}

	/**
	 *
	 * @param pamProxyVO
	 * @param observationUid
	 * @param observationTypeCd
	 * @param nBSSecurityObj
	 * @return
	 * @throws javax.ejb.EJBException
	 * @throws javax.ejb.CreateException
	 * @throws java.rmi.RemoteException
	 * @throws NEDSSSystemException
	 * @throws NEDSSConcurrentDataException
	 */
	public Long setPamProxyWithAutoAssoc(PamProxyVO pamProxyVO,
			Long observationUid, String observationTypeCd,
			NBSSecurityObj nBSSecurityObj) throws javax.ejb.EJBException,
			javax.ejb.CreateException, java.rmi.RemoteException,
			NEDSSSystemException, NEDSSConcurrentDataException

			{
		try {
			Long investigationUID = setPamProxy(pamProxyVO, nBSSecurityObj);

			Collection<Object>  observationColl = new ArrayList<Object> ();
			if (observationTypeCd.equalsIgnoreCase(NEDSSConstants.LAB_DISPALY_FORM)) {
				LabReportSummaryVO labSumVO = new LabReportSummaryVO();
				labSumVO.setItTouched(true);
				labSumVO.setItAssociated(true);
				labSumVO.setObservationUid(observationUid);
				observationColl.add(labSumVO);

			} else {
				MorbReportSummaryVO morbSumVO = new MorbReportSummaryVO();
				morbSumVO.setItTouched(true);
				morbSumVO.setItAssociated(true);
				morbSumVO.setObservationUid(observationUid);
				observationColl.add(morbSumVO);

			}
			ManageAutoAssociations manageAutoAssoc = new ManageAutoAssociations();
			manageAutoAssoc.setObservationAssociationsImpl(investigationUID, observationColl, nBSSecurityObj, true);
			return investigationUID;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("Exception occured in PamProxy.setPamProxy, observationUid: " + observationUid + ", observationTypeCd: " + observationTypeCd + ", " + e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
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
	 * @throws NEDSSConcurrentDataException
	 */
	public void transferOwnership(Long publicHealthCaseUID,	PamProxyVO pamProxyVO,
			String newJurisdictionCode,Boolean isExportCase, NBSSecurityObj nbsSecurityObj)
			throws javax.ejb.EJBException, NEDSSSystemException,	NEDSSConcurrentDataException {
		try {
			ActController actController = null;
			ObservationProxy observationProxy = null;
			PublicHealthCaseDT publicHealthCaseDT = null;
			PublicHealthCaseDT newPublicHealthCaseDT = null;

			NedssUtils nedssUtils = new NedssUtils();
			PrepareVOUtils prepareVOUtils = new PrepareVOUtils();
			Object object = nedssUtils.lookupBean(JNDINames.ActControllerEJB);
			logger.debug("ActController lookup = " + object.toString());
			ActControllerHome acthome = (ActControllerHome) PortableRemoteObject.narrow(object, ActControllerHome.class);
			logger.debug("Found ActControllerHome: " + acthome);
			actController = acthome.create();
			publicHealthCaseDT = actController.getPublicHealthCaseInfo(publicHealthCaseUID, nbsSecurityObj);
			publicHealthCaseDT.setPamCase(true);
			NbsHistoryDAO nbsHistoryDAO= new NbsHistoryDAO();
			nbsHistoryDAO.getPamHistory(pamProxyVO.getPublicHealthCaseVO());
			
			if (!nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION, NBSOperationLookup.TRANSFERPERMISSIONS,
					publicHealthCaseDT.getProgAreaCd(), publicHealthCaseDT.getJurisdictionCd(), publicHealthCaseDT.getSharedInd())) {
				logger.info("no add permissions for transferOwnership");
				throw new NEDSSSystemException("NO ADD PERMISSIONS for transferOwnership");
			}
			logger.info("user has add permissions for setPamProxy");
			java.util.Date dateTime = new java.util.Date();
			Timestamp systemTime = new Timestamp(dateTime.getTime());
			publicHealthCaseDT.setItDirty(true);
			publicHealthCaseDT.setJurisdictionCd(newJurisdictionCode);
			
			publicHealthCaseDT.setLastChgTime(systemTime);
			newPublicHealthCaseDT = (PublicHealthCaseDT) prepareVOUtils.prepareVO(publicHealthCaseDT, NBSBOLookup.INVESTIGATION,
					NEDSSConstants.INV_EDIT, DataTables.PUBLIC_HEALTH_CASE_TABLE, 	NEDSSConstants.BASE, nbsSecurityObj);
			pamProxyVO.getPublicHealthCaseVO().setThePublicHealthCaseDT(newPublicHealthCaseDT);
			
			actController.setPublicHealthCase(pamProxyVO.getPublicHealthCaseVO(), nbsSecurityObj);
			//actController.setPublicHealthCaseInfo(newPublicHealthCaseDT,nbsSecurityObj);
			pamProxyVO.getPublicHealthCaseVO().setThePublicHealthCaseDT(publicHealthCaseDT);
			Collection<Object>  actRelationShips = actController.getActRelationships(publicHealthCaseUID, nbsSecurityObj);

			if (actRelationShips != null) {
				Iterator<Object>  it = actRelationShips.iterator();
				while (it.hasNext()) {
					ActRelationshipDT actRelationshipDT = (ActRelationshipDT) it.next();
					if ((actRelationshipDT.getTypeCd()
							.equalsIgnoreCase("LabReport"))	|| (actRelationshipDT.getTypeCd()	.equalsIgnoreCase("MorbReport"))) {
						Object objObject = nedssUtils.lookupBean(JNDINames.OBSERVATION_PROXY_EJB);
						ObservationProxyHome observationProxyhome = (ObservationProxyHome) PortableRemoteObject
							.narrow(objObject, ObservationProxyHome.class);
						logger.debug("Found observationProxyHome: " + acthome);
						observationProxy = observationProxyhome.create();
						observationProxy.transferOwnership(actRelationshipDT.getSourceActUid(), null, newJurisdictionCode,
								NEDSSConstants.CASCADING, nbsSecurityObj);
					}
					  else if(actRelationshipDT.getTypeCd().equalsIgnoreCase(NEDSSConstants.DocToPHC)){
			    			 Long docUid = actRelationshipDT.getSourceActUid();
			    			  if(docUid != null){
			    			      try{
			    				      NbsDocument nbsDocument = null;
			    				      Object docEJB = nedssUtils.lookupBean(JNDINames.NBS_DOCUMENT_EJB);
			    				      logger.debug("DocumentEJB lookup = " + docEJB.toString());
			    				      NbsDocumentHome dochome = (NbsDocumentHome) PortableRemoteObject.
			    				          narrow(docEJB, NbsDocumentHome.class);
			    				      logger.debug("Found NbsDocumentHome: " + dochome);
			    				      nbsDocument = dochome.create(); 
			    				      //get the 
			    				      nbsDocument.transferOwnership(docUid, null, newJurisdictionCode, nbsSecurityObj);
			    			      }catch(Exception e){
			    			    	  logger.error("Error while updating the Document table, docUid: " + docUid + ", " + e.getMessage(), e);
			    			          e.printStackTrace();
			    			          throw new EJBException(e.getMessage(), e);
			    		     }
			    		  }
					  }
					else if(actRelationshipDT.getTypeCd().equalsIgnoreCase(NEDSSConstants.ACT106_TYP_CD)){
						NotificationDT notificationDT = actController.getNotificationInfo(
								actRelationshipDT.getSourceActUid(), nbsSecurityObj);
						String trigCd = NEDSSConstants.NOT_EDIT;
						//notificationDT.setItNew(false);
						RetrieveSummaryVO.updateNotification(actRelationshipDT.getSourceActUid(), trigCd, notificationDT.getCaseConditionCd(),notificationDT.getCaseClassCd(),
								notificationDT.getProgAreaCd(),newJurisdictionCode, notificationDT.getSharedInd(),nbsSecurityObj);
					}
				}
			}
			PamRootDAO pamRootDAO = new PamRootDAO();
			pamRootDAO.UpdateFordeletePamVO(pamProxyVO.getPamVO(), pamProxyVO
					.getPublicHealthCaseVO());

		} catch (NEDSSConcurrentDataException ex) {
			logger.fatal("NEDSSConcurrentDataException occured in PamProxyEJB.transferOwnership" + ex.getMessage(), ex);
			throw new NEDSSConcurrentDataException(ex.getMessage(), ex);
		} catch (Exception e) {
			logger.fatal("Exception occured in PamProxyEJB.transferOwnership, nbsSecurityObj.getFullName: " + nbsSecurityObj.getFullName() + ", " + e.getMessage(), e);
			e.printStackTrace();
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}

	}
	
	 private Long transferOwnershipforExport(NotificationProxyVO notProxyVO, String newJurisdictionCode, NBSSecurityObj nbsSecurityObj) throws javax.ejb.
     EJBException,NEDSSSystemException, NEDSSConcurrentDataException {
 Long newNotficationUid = new Long(0);
 try {
	  ActController actController = null;
	  ObservationProxy observationProxy = null;
	  PublicHealthCaseDT publicHealthCaseDT = null;
	  PublicHealthCaseDT newPublicHealthCaseDT = null;
	  NedssUtils nedssUtils = new NedssUtils();
	  PrepareVOUtils prepareVOUtils = new PrepareVOUtils();
	  Object object = nedssUtils.lookupBean(JNDINames.ActControllerEJB);
	  logger.debug("ActController lookup = " + object.toString());
	  ActControllerHome acthome = (ActControllerHome) PortableRemoteObject.
	  narrow(object, ActControllerHome.class);
	  logger.debug("Found ActControllerHome: " + acthome);
	  actController = acthome.create();

	  publicHealthCaseDT = actController.getPublicHealthCaseInfo(
			  notProxyVO.getThePublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid(), nbsSecurityObj);

	  if (!nbsSecurityObj.getPermission(NBSBOLookup.INVESTIGATION,
             NBSOperationLookup.TRANSFERPERMISSIONS,
             publicHealthCaseDT.getProgAreaCd(),
             publicHealthCaseDT.getJurisdictionCd(),
             publicHealthCaseDT.getSharedInd())) {
		  logger.info("no add permissions for setInvestigationProxy");
		  throw new NEDSSSystemException(
		  "NO ADD PERMISSIONS for transferOwnership");
	  }

	  logger.info("user has add permissions for setInvestigationProxy");

	  publicHealthCaseDT.setItDirty(true);
	  // Do not update the public health case jurisdiction when you do export 
	  //publicHealthCaseDT.setJurisdictionCd(newJurisdictionCode);
	 		  newPublicHealthCaseDT = (PublicHealthCaseDT) prepareVOUtils.prepareVO(
			  publicHealthCaseDT, NBSBOLookup.INVESTIGATION,
			  NEDSSConstants.INV_EDIT, DataTables.PUBLIC_HEALTH_CASE_TABLE,
			  NEDSSConstants.BASE, nbsSecurityObj);

	  actController.setPublicHealthCaseInfo(newPublicHealthCaseDT,
			  nbsSecurityObj);
	  Collection<Object>  actRelationShips = actController.getActRelationships(notProxyVO.getThePublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid(),nbsSecurityObj);
	  
	  NotificationVO notificationVO = notProxyVO.getTheNotificationVO();
	  if(actRelationShips!=null){
		 Iterator<Object>  it = actRelationShips.iterator();
		  while(it.hasNext()){
			  ActRelationshipDT actRelationshipDT = (ActRelationshipDT)it.next();
		      if(actRelationshipDT.getTypeCd().equalsIgnoreCase(NEDSSConstants.ACT106_TYP_CD)){

				  NotificationDT notificationDT = actController.getNotificationInfo(
						  actRelationshipDT.getSourceActUid(), nbsSecurityObj);
				  

				  // Call the method here 
				  if(notificationDT.getCd().equalsIgnoreCase(NEDSSConstants.CLASS_CD_EXP_NOTF) ||
						  notificationDT.getCd().equalsIgnoreCase(NEDSSConstants.CLASS_CD_EXP_NOTF_PHDC) ){
			    	   if(notificationDT.getRecordStatusCd().equals(NEDSSConstants.NOTIFICATION_REJECTED_CODE)){
			    		   String trigCd = NEDSSConstants.NOT_EDIT;  				    		   
			    		   notificationVO = updateNotificationforExport(actRelationshipDT.getSourceActUid(), trigCd,
									  notificationDT.getCaseConditionCd(),notificationDT.getCaseClassCd(),
									  notificationDT.getProgAreaCd(),notProxyVO.getTheNotificationVO().getTheNotificationDT().getJurisdictionCd(),
									  notificationDT.getSharedInd(), notProxyVO.getTheNotificationVO().getTheNotificationDT().getExportReceivingFacilityUid(),notProxyVO.getTheNotificationVO().getTheNotificationDT().getTxt(), nbsSecurityObj);
			    		   newNotficationUid = actController.setNotification(notificationVO,nbsSecurityObj);
			    		   		   
			    	   }
			    	  
			    	   
			       }	
				  
			  }
		  }
		  
	  }
 }
 catch (NEDSSConcurrentDataException ex) {
	  logger.fatal("InvestigationProxyEJB.transferOwnershipforExport: Concurrent access is not allowed!");
	  throw new NEDSSConcurrentDataException(ex.getMessage(),ex);
 }
 catch (Exception e) {
	  logger.fatal("Exception occured in PamProxyEJB.transferOwnsershipforExport, newJurisdictionCode: " + newJurisdictionCode + ", NotificationUid: " + notProxyVO.getTheNotificationVO().getTheNotificationDT().getNotificationUid() + ", nbsSecurityObj.getFullName: " + nbsSecurityObj.getFullName() + ", " + e.getMessage(), e);
	  e.printStackTrace();
	  throw new EJBException(e.getMessage(),e);
 }
 return newNotficationUid;

}
	 
	 
	//TODO This method needs to clean up as similar kind of method is existing in caseNotificationEJB.  
	 private static  NotificationVO updateNotificationforExport(Long notificationUid,
	 	 		String businessTriggerCd,
	 	 		String phcCd,
	 	 		String phcClassCd,
	 	 		String progAreaCd,
	 	 		String jurisdictionCd,
	 	 		String sharedInd,
	 	 		Long recFacilithyUid,
	 	 		String comment,
	 	 		NBSSecurityObj nbsSecurityObj) {

	 	     NedssUtils nedssUtils = new NedssUtils();
	 	     Collection<Object>  notificationVOCollection  = null;
	 	     Object theLookedUpObject = nedssUtils.lookupBean(JNDINames.ActControllerEJB);
	 	     try
	 	     {
	 	       ActControllerHome ecHome = (ActControllerHome) PortableRemoteObject.narrow(theLookedUpObject,ActControllerHome.class);
	 	       ActController actController = ecHome.create();
	 	       NotificationVO notificationVO = actController.getNotification(notificationUid,nbsSecurityObj);
	 	       
	 	       PrepareVOUtils prepareVOUtils = new PrepareVOUtils();
	 	       NotificationDT newNotificationDT = null;
	 	       NotificationDT notificationDT = notificationVO.getTheNotificationDT();
	 	       notificationDT.setProgAreaCd(progAreaCd);
	 	       notificationDT.setJurisdictionCd(jurisdictionCd);
	 	       notificationDT.setCaseConditionCd(phcCd);
	 	       notificationDT.setSharedInd(sharedInd);
	 	       notificationDT.setCaseClassCd(phcClassCd);
	 	       notificationDT.setExportReceivingFacilityUid(recFacilithyUid);
	 	       notificationDT.setRecordStatusCd(NEDSSConstants.NOTIFICATION_PENDING_CODE);
	 	       notificationDT.setTxt(comment);
	 	       notificationDT.setItDirty(true);
	 	       notificationVO.setItDirty(true);   
	 	       
	 	       
	 	         
	 		
	 			       //retreive the new NotificationDT generated by PrepareVOUtils
	 			       newNotificationDT = (NotificationDT) prepareVOUtils.prepareVO(
	 			       notificationDT, NBSBOLookup.NOTIFICATION, businessTriggerCd,
	 			       DataTables.NOTIFICATION_TABLE, NEDSSConstants.BASE, nbsSecurityObj); 
	 			       newNotificationDT.setRecordStatusCd(NEDSSConstants.NOTIFICATION_PENDING_CODE);
	 		
	 			       //replace old NotificationDT in NotificationVO with the new NotificationDT
	 			         notificationVO.setTheNotificationDT(newNotificationDT);
	 			         return notificationVO;
	 			         
	 	     
	 	     }
	 	        catch (Exception e)
	 	        {
	 	               e.printStackTrace();
	 	               logger.fatal("Error calling ActController.setNotification(), notificationUid: " + notificationUid
	 	            		   + ", businessTriggerCd: " + businessTriggerCd + ", phcCd: " + phcCd + ", progAreaCd: " + progAreaCd 
	 	            		   + ", recFacilityUid: " + recFacilithyUid + ", " + e.getMessage(), e);
	 	               throw new NEDSSSystemException(e.getMessage(),e);
	 	        }

	 	 }

	 
	public PublicHealthCaseDT getPublicHealthCaseDT(Long publicHealthCaseUid)
		throws  javax.ejb.EJBException,java.rmi.RemoteException, NEDSSSystemException
    {
		try {
			PublicHealthCaseDAOImpl publicHealthCaseDAOImpl = new PublicHealthCaseDAOImpl();
			PublicHealthCaseDT pdt = (PublicHealthCaseDT) publicHealthCaseDAOImpl.loadObject(publicHealthCaseUid.longValue());
			return pdt;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("Exception in getPublicHealthCaseDT, publicHealthCaseUid: " + publicHealthCaseUid + ", " + e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
	}
	
	public String getRootExtensionTxt(String formCode, int seqNum,  String caseNumber, String caseLocalId) throws  javax.ejb.EJBException,java.rmi.RemoteException, NEDSSSystemException{
		String rootExtensionTxt="";
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;
		PropertyUtil propertyUtil= PropertyUtil.getInstance();
		String assAuthQuery=null;
		try {
		if(caseLocalId != null)
		{
		
			assAuthQuery = WumSqlQuery.GET_ASSIGNING_AUTH_STATE_CASE_SQL;
		}else
		{
			assAuthQuery = WumSqlQuery.CREATE_ASSIGNING_AUTH_STATE_CASE_SQL;
		}
		
			 
			logger.info("Query = " + assAuthQuery);
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(assAuthQuery);
			preparedStmt.setString(1, formCode);
			preparedStmt.setInt(2, seqNum);
			preparedStmt.setString(3, caseNumber);
			if(caseLocalId != null)
				preparedStmt.setString(4, caseLocalId);

			
			resultSet = preparedStmt.executeQuery();

			if (resultSet != null && resultSet.next()) {
				rootExtensionTxt = resultSet.getString(1);
				logger.debug("assigningAuthorityCd : " + rootExtensionTxt);
			}
		} catch (Exception e) {
			logger.fatal("Unable to retrieve rootExtensionTxt, formCode: " + formCode + ", caseNumber: " + caseNumber + ", caseLocalId: " + ", " + e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		} finally {
			try {
				if(resultSet!=null)
					resultSet.close();
				if(preparedStmt!=null)
					preparedStmt.close();
				if(dbConnection!=null)
					dbConnection.close();
			} catch (SQLException sqlex) {
				sqlex.printStackTrace();
				logger.fatal("SQLException occured in PamProxyEJB.getRootExtensionTxt: " + sqlex.getMessage(), sqlex);
				throw new EJBException(sqlex.getMessage(), sqlex);
			}
		}
		return rootExtensionTxt; 
	}
	public Long exportOwnership( NotificationProxyVO notProxyVO, String newJurisdictionCode, NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException, NEDSSSystemException,
    NEDSSConcurrentDataException{
	    try {
			Long sourceActUid = transferOwnershipforExport(notProxyVO,newJurisdictionCode,nbsSecurityObj);
			if(sourceActUid.compareTo(new Long(0))==0){
			   	 ActController actController = null;
				 NedssUtils nedssUtils = new NedssUtils();
				 Object object = nedssUtils.lookupBean(JNDINames.ActControllerEJB);
				 logger.debug("ActController lookup = " + object.toString());
				 ActControllerHome acthome = (ActControllerHome) PortableRemoteObject.
				 narrow(object, ActControllerHome.class);
				 logger.debug("Found ActControllerHome: " + acthome);
				 
					   try{
							actController = acthome.create();					
						    sourceActUid= actController.setNotification(notProxyVO.getTheNotificationVO(), nbsSecurityObj);
						  }catch(Exception e){
					            logger.fatal(nbsSecurityObj.getFullName(), e.getMessage(), e);
					            e.printStackTrace();
					            throw new javax.ejb.EJBException(e.getMessage());
					     }
			
			ActRelationshipDAOImpl actRelationshipDAOImpl = new ActRelationshipDAOImpl();
			  if (notProxyVO.getTheActRelationshipDTCollection() != null) {
			        actRelationshipDAOImpl = (ActRelationshipDAOImpl) NEDSSDAOFactory.
			            getDAO(JNDINames.ACT_RELATIONSHIP_DAO_CLASS);
			       Iterator<Object>  anIterator = notProxyVO. getTheActRelationshipDTCollection().iterator();
			       while(anIterator.hasNext()){
			          ActRelationshipDT actRelationshipDT = (ActRelationshipDT) anIterator.next();
			
			          try {
			        	  actRelationshipDT.setSourceActUid(sourceActUid);
			            actRelationshipDAOImpl.store(actRelationshipDT);
			            logger.debug("Got into The ActRelationship, The ActUid is " +
			                         actRelationshipDT.getTargetActUid());
			          }
			          catch (Exception e) {
			            logger.fatal(nbsSecurityObj.getFullName(), e.getMessage(), e);
			            e.printStackTrace();
			            throw new javax.ejb.EJBException(e.getMessage());
			          }
			        }
			      }
			}//if it is an update notification 
			  return notProxyVO.getThePublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid();
		} catch (ClassCastException e) {
			// TODO Auto-generated catch block
			logger.fatal("ClassCastException occured in PamProxyEJB.exportOwnership, NotificationUid: " + notProxyVO.getTheNotificationVO().getTheNotificationDT().getNotificationUid() +  ", newJurisdictionCode: " + newJurisdictionCode + ", " + e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
	  }
	 /**
	    * @roseuid 3D1B937801F4
	    * @J2EE_METHOD  --  getAggregateSummaryData
	    * This method has been added to retrieve Aggregate Summary Data.
	    */

	  /* public Object getAggregateSummaryData(AggregateSummaryResultsDT dt,
	                                               NBSSecurityObj nbsSecurityObj) throws
	                                               javax.ejb.EJBException,java.rmi.RemoteException, NEDSSSystemException {
		   Object obj = null;
	       AggregateSummaryDataDAOImpl aggregateSummaryDAO = new AggregateSummaryDataDAOImpl();
	       ArrayList<Object> aggregateSummaryDTCollection  = aggregateSummaryDAO.retrieveAggregateSummaryReportList(whereclause, nbsSecurityObj);
	       if(aggregateSummaryDTCollection  != null && aggregateSummaryDTCollection.size() > 0) {
	    	   obj = aggregateSummaryDTCollection.get(0);
	       }
	    	   
	     return obj;
	   } // End of getAggregateSummaryData  method*/
	   
	   /**
	    * @roseuid 3D1B937801F4
	    * @J2EE_METHOD  --  editAggregateSummaryData
	    * This method has been added to update Aggregate Summary Data.
	    */

	 /*  public Object editAggregateSummaryData(AggregateSummaryResultsDT dt, NBSSecurityObj nbsSecurityObj) throws
	                                               javax.ejb.EJBException,java.rmi.RemoteException, NEDSSSystemException {
		    AggregateSummaryDataDAOImpl aggregateSummaryDAO = new AggregateSummaryDataDAOImpl();      
	        aggregateSummaryDAO.updateAggregateSummaryReportList(dt); 
	        Object obj =null;
	        return obj;
	    
	   } // End of editAggregateSummaryData  method  */
	   
	   /**
	    * @throws NEDSSSystemException 
	 * @throws CreateException 
	 * @throws RemoteException 
	 * @throws NEDSSAppException 
	 * @roseuid 3D1B937801F4
	    * @J2EE_METHOD  --  editAggregateSummaryData
	    * This method has been added to update Aggregate Summary Data.
	    */

	  /* public Object addAggregateSummaryData(AggregateSummaryDT dt, NBSSecurityObj nbsSecurityObj) throws
	                                               javax.ejb.EJBException,java.rmi.RemoteException, NEDSSSystemException {
		    AggregateSummaryDataDAOImpl aggregateSummaryDAO = new AggregateSummaryDataDAOImpl();      
	        aggregateSummaryDAO.addAggregateSummaryReportList(dt);  
	        Object obj =null;
	        return obj;
	    
	   } // End of editAggregateSummaryData  method */

	/*
	 * 
	 */
	public  Long setAggregateSummary(SummaryReportProxyVO summaryReportProxyVO, NBSSecurityObj nbsSecurityObj) throws NEDSSSystemException, RemoteException, CreateException, NEDSSAppException{
		try {
			Long publicHealthCaseUID = null;
			Collection<Object>  nbsCaseAnswerCollection  = null;
			if (!summaryReportProxyVO.isItNew() && !summaryReportProxyVO.isItDirty() && !summaryReportProxyVO.isItDelete()) {
				logger.error(
						"SummaryReportProxyVO is not new, or dirty. Hence exiting without changes!");
				return null;
			}
			PrepareVOUtils preVOUtils = new PrepareVOUtils();
			RootDTInterface rootDT = null;
			if (nbsSecurityObj != null && nbsSecurityObj.getPermission( NBSBOLookup.SUMMARYREPORT, NEDSSConstants.SUMMARY_REPORT_ADD, summaryReportProxyVO.getThePublicHealthCaseVO().
					getThePublicHealthCaseDT().getProgAreaCd(),ProgramAreaJurisdictionUtil.ANY_JURISDICTION)) {
				NedssUtils nedssUtils = new NedssUtils();
				Object object = nedssUtils.lookupBean(JNDINames.ActControllerEJB);
				logger.info("ActController lookup = " + object.toString());
				
				ActControllerHome actHome = (ActControllerHome) PortableRemoteObject.narrow(object, ActControllerHome.class);
				ActController act = actHome.create();
				if(summaryReportProxyVO.getThePublicHealthCaseVO().getNbsAnswerCollection()!=null){
					nbsCaseAnswerCollection  = new ArrayList<Object> (summaryReportProxyVO.getThePublicHealthCaseVO().getNbsAnswerCollection());
				}
				else
					nbsCaseAnswerCollection  = new ArrayList<Object> ();
					
				summaryReportProxyVO.getThePublicHealthCaseVO().getThePublicHealthCaseDT().setSummaryCase(true);
				
				try {
					PublicHealthCaseVO phcVO=null;
					if (summaryReportProxyVO.getThePublicHealthCaseVO().getThePublicHealthCaseDT().isItNew()) {
						phcVO =summaryReportProxyVO.getThePublicHealthCaseVO();
						rootDT = preVOUtils.prepareVO(summaryReportProxyVO.getThePublicHealthCaseVO().getThePublicHealthCaseDT(),NBSBOLookup.SUMMARYREPORT,
								NEDSSConstants.INV_SUMMARY_CR, NEDSSConstants.TABLE_NAME, NEDSSConstants.BASE, nbsSecurityObj);
						logger.debug(" PamProxyEJB.setAggregateSummary called to get the root DT for new SummaryReportVO");
						PublicHealthCaseDT phcDT =summaryReportProxyVO.getThePublicHealthCaseVO().getThePublicHealthCaseDT();
						PublicHealthCaseDAOImpl publicHealthCaseDAOImpl = new PublicHealthCaseDAOImpl();
						boolean publicHealthCaseExist=publicHealthCaseDAOImpl.publicHealthCaseExist(summaryReportProxyVO.getThePublicHealthCaseVO());
						if(publicHealthCaseExist){
							throw new NEDSSAppException("The system contains SummaryReport exists with the  criteria "
									+" cd:"+phcDT.getCd() + " Reported county:"+phcDT.getRptCntyCd() +" class code "+phcDT.getCaseClassCd() +
									"Date of Report "+ phcDT.getRptFormCmpltTime()+" and contIntervalCode :" +phcDT.getCountIntervalCd());
						}
					}
					else if (summaryReportProxyVO.getThePublicHealthCaseVO().getThePublicHealthCaseDT().isItDirty()) {
						NbsHistoryDAO nbsHistoryDAO= new NbsHistoryDAO();
						phcVO = nbsHistoryDAO.getPamHistory(summaryReportProxyVO.getPublicHealthCaseVO());
						rootDT = preVOUtils.prepareVO(summaryReportProxyVO.getThePublicHealthCaseVO().
								getThePublicHealthCaseDT(), NBSBOLookup.SUMMARYREPORT,
								NEDSSConstants.INV_SUMMARY_EDIT, NEDSSConstants.TABLE_NAME, NEDSSConstants.BASE, nbsSecurityObj);
						logger.debug("PamProxyEJB.setAggregateSummary called to get the root DT for edited SummaryReportVO");
					}
					else if (summaryReportProxyVO.getThePublicHealthCaseVO().getThePublicHealthCaseDT().isItDelete()) {
						NbsHistoryDAO nbsHistoryDAO= new NbsHistoryDAO();
						phcVO = nbsHistoryDAO.getPamHistory(summaryReportProxyVO.getPublicHealthCaseVO());
						PublicHealthCaseDT phcDT = summaryReportProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT();
						phcDT.setItDelete(false);
						phcDT.setItDirty(true);
						rootDT = preVOUtils.prepareVO(phcDT, NBSBOLookup.SUMMARYREPORT,
								NEDSSConstants.INV_SUMMARY_DEL, NEDSSConstants.TABLE_NAME, NEDSSConstants.BASE, nbsSecurityObj);
						logger.debug("PamProxyEJB.setAggregateSummary called to get the root DT for Delete SummaryReportVO");
					}
					if(summaryReportProxyVO.getThePublicHealthCaseVO().getThePublicHealthCaseDT().isItDirty() ||
							summaryReportProxyVO.getThePublicHealthCaseVO().getThePublicHealthCaseDT().isItDelete() ||
							summaryReportProxyVO.getThePublicHealthCaseVO().getThePublicHealthCaseDT().isItNew()){
						
						PublicHealthCaseDT publicHealthCaseDT = (PublicHealthCaseDT) rootDT;
						phcVO.setThePublicHealthCaseDT(publicHealthCaseDT);
						publicHealthCaseUID = act.setPublicHealthCase(phcVO, nbsSecurityObj);
						logger.info("New SummaryReport is created with PublicHealthCaseUID:" + publicHealthCaseUID);
					}else{
						
						publicHealthCaseUID=summaryReportProxyVO.getThePublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid();
					}
						
					boolean isnotificationCreated = false;
					NotificationVO notificationVO=null;
					if(summaryReportProxyVO.getTheNotificationVOCollection()!=null){
						Iterator<Object>  iter = summaryReportProxyVO.getTheNotificationVOCollection().iterator();
						while(iter.hasNext()){
							  notificationVO=(NotificationVO) iter.next();
							  Long notificationUid=null;
							  if(notificationVO.isItNew()==true){
								   		   try{
												 notificationUid= act.setNotification(notificationVO, nbsSecurityObj);
												 isnotificationCreated = true;
											  }catch(Exception e){
										            logger.fatal(nbsSecurityObj.getFullName(), e.getMessage(), e);
										            e.printStackTrace();
										            throw new javax.ejb.EJBException(e.getMessage());
										     }
							    
							    ActRelationshipDAOImpl actRelationshipDAOImpl = new ActRelationshipDAOImpl();
								  if (summaryReportProxyVO.getTheActRelationshipDTCollection() != null) {
								        actRelationshipDAOImpl = (ActRelationshipDAOImpl) NEDSSDAOFactory.
								            getDAO(JNDINames.ACT_RELATIONSHIP_DAO_CLASS);
								       Iterator<Object>  anIterator = summaryReportProxyVO. getTheActRelationshipDTCollection().iterator();
								       while(anIterator.hasNext()){
								          ActRelationshipDT actRelationshipDT = (ActRelationshipDT) anIterator.next();
								
								          try {
								        	  actRelationshipDT.setSourceActUid(notificationUid);
								        	  actRelationshipDT.setTargetActUid(publicHealthCaseUID);
								        	  AssocDTInterface aDTInterface = preVOUtils.prepareAssocDT(actRelationshipDT, nbsSecurityObj);
								            actRelationshipDAOImpl.store(aDTInterface);
								            logger.debug("Got into The ActRelationship, The ActUid is " +
								                         actRelationshipDT.getTargetActUid());
								          }
								          catch (Exception e) {
								            logger.fatal(nbsSecurityObj.getFullName(), e.getMessage(), e);
								            e.printStackTrace();
								            throw new javax.ejb.EJBException(e.getMessage());
								          }
								        }
								      }
							    } else if(notificationVO.isItDelete()==true) {
							    	//Mark it appropriately for DELETE
							    	try {
										deleteSummaryNotification(phcVO, nbsSecurityObj);
									} catch (Exception e) {
										e.printStackTrace();
										logger.error("Error while deleting Notification for AggregateSummary: "+ e.toString());
										throw e;
									}
							    	
							    }
							
						}
					}
					if(summaryReportProxyVO.getThePublicHealthCaseVO().getThePublicHealthCaseDT().isItDirty() ||
							summaryReportProxyVO.getThePublicHealthCaseVO().getThePublicHealthCaseDT().isItDelete() ||
							summaryReportProxyVO.getThePublicHealthCaseVO().getThePublicHealthCaseDT().isItNew()){
						
						
						  if(summaryReportProxyVO.isItDirty() && summaryReportProxyVO.getTheNotificationVOCollection()!=null && !isnotificationCreated){
							   NNDMessageSenderHelper nndMessageSenderHelper = null;
						          nndMessageSenderHelper = NNDMessageSenderHelper.getInstance();
						      try {
						      	//update auto resend notifications
						        nndMessageSenderHelper.updateAutoResendNotificationsAsync(summaryReportProxyVO, nbsSecurityObj);
						      }
						      catch (Exception e) {
						        NNDActivityLogDT nndActivityLogDT = new NNDActivityLogDT();
						        String phcLocalId = summaryReportProxyVO.getPublicHealthCaseVO().
						            getThePublicHealthCaseDT().getLocalId();
						        nndActivityLogDT.setErrorMessageTxt(e.toString());
						        if (phcLocalId != null)
						          nndActivityLogDT.setLocalId(phcLocalId);
						        else
						          nndActivityLogDT.setLocalId("N/A");
						        nndMessageSenderHelper.persistNNDActivityLog(nndActivityLogDT,nbsSecurityObj);
						        logger.error("PamProxyEJB.setAggregateSummary:-Exception occurred while calling nndMessageSenderHelper.updateAutoResendNotificationsAsync"+e);
						        e.printStackTrace();
						      }
						    }
						PamRootDAO pamRootDAO = new PamRootDAO();
						summaryReportProxyVO.getThePublicHealthCaseVO().getThePublicHealthCaseDT().setPublicHealthCaseUid(publicHealthCaseUID);
						pamRootDAO.setPamCaseAnswerCollection(nbsCaseAnswerCollection, summaryReportProxyVO.getThePublicHealthCaseVO());
					}

				} catch (ClassCastException e) {
					throw new java.rmi.RemoteException(e.toString());
				}catch (NEDSSAppException ex) {
					throw new NEDSSAppException("The system contains SummaryReport exists with the  criteria "
							+" cd:"+summaryReportProxyVO.getThePublicHealthCaseVO().getThePublicHealthCaseDT().getCd() + " Reported county:"+summaryReportProxyVO.getThePublicHealthCaseVO().getThePublicHealthCaseDT().getRptCntyCd() +" class code "+summaryReportProxyVO.getThePublicHealthCaseVO().getThePublicHealthCaseDT().getCaseClassCd() +
							"Date of Report "+ summaryReportProxyVO.getThePublicHealthCaseVO().getThePublicHealthCaseDT().getRptFormCmpltTime()+" and contIntervalCode :" +summaryReportProxyVO.getThePublicHealthCaseVO().getThePublicHealthCaseDT().getCountIntervalCd());
				}  catch (Exception e) {
					throw new java.rmi.RemoteException(e.toString());
				}
				return publicHealthCaseUID;
			} 
			else {
				logger.fatal(nbsSecurityObj.getEntryID(),  " user does not have the rights to Create/Edit/Delete this summary Case");
				throw new EJBException(  " user does not have the rights to Create/Edit this summary Case");
			}
		} catch (ClassCastException e) {
			// TODO Auto-generated catch block
			logger.fatal("ClassCastException occured in PamProxyEJB.setAggregateSummary, PublicHealthCaseUid: " + summaryReportProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid() + ", "+ e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
	}

	public  SummaryReportProxyVO getAggregateSummary(Long publicHealthCaseUid, NBSSecurityObj nbsSecurityObj)  throws javax.ejb.EJBException,
    javax.ejb.CreateException,
    java.rmi.RemoteException,
    NEDSSSystemException{
		Long publicHealthCaseUID = null;
		try {
			SummaryReportProxyVO summaryReportProxyVO =  new SummaryReportProxyVO();
			NedssUtils nedssUtils = new NedssUtils();
			Object object = nedssUtils.lookupBean(JNDINames.ActControllerEJB);
			logger.info("ActController lookup = " + object.toString());
			ActControllerHome actHome = (ActControllerHome) PortableRemoteObject.narrow(object, ActControllerHome.class);
			ActController act = actHome.create();
			PublicHealthCaseVO publicHealthCaseVO = act.getPublicHealthCase(publicHealthCaseUid, nbsSecurityObj);
			logger.info("Getting  SummaryReportProxyVO with PublicHealthCaseUID:" + publicHealthCaseUID);
			PamRootDAO pamRootDAO = new PamRootDAO();
			Collection<Object>  pamAnswerColl =pamRootDAO.getSummaryPamCaseAnswerCollection(publicHealthCaseUid);
			summaryReportProxyVO.setThePublicHealthCaseVO(publicHealthCaseVO);
			summaryReportProxyVO.getThePublicHealthCaseVO().setNbsAnswerCollection(pamAnswerColl);
			Long notificationUID = null;
			NotificationVO notificationVO = new NotificationVO();
			 Collection<Object>  notificationColl = new ArrayList<Object> ();
			if(publicHealthCaseVO.getTheActRelationshipDTCollection()!=null){
				 Iterator<Object>  actIterator = publicHealthCaseVO.getTheActRelationshipDTCollection().
		          iterator();
		      while (actIterator.hasNext()) {
		    	  ActRelationshipDT actDT = (ActRelationshipDT) actIterator.next();
			if (actDT.getTypeCd().equalsIgnoreCase(NEDSSConstants.
					ACT128_TYP_CD) &&
		                 actDT.getSourceClassCd().equalsIgnoreCase(NEDSSConstants.
		            CLASS_CD_NOTIFICATION) &&
		                 actDT.getTargetClassCd().equalsIgnoreCase(NEDSSConstants.
		            CLASS_CD_CASE) &&
		                 actDT.getRecordStatusCd().equalsIgnoreCase(NEDSSConstants.
		            ACTIVE)) {
		          notificationUID = actDT.getSourceActUid();

		          notificationVO = act.getNotification(notificationUID, nbsSecurityObj);
		          notificationColl.add(notificationVO);
		          summaryReportProxyVO.setTheNotificationVOCollection(notificationColl);
		        }
		      }
			}
		    

			return summaryReportProxyVO;
		}  catch (EJBException e) {
			logger.fatal("ClassCastException occured in PamProxyEJB.getAggregateSummary, PublicHealthCaseUid: " + publicHealthCaseUid + ", "+ e.getMessage(), e);
			throw new EJBException(e.getMessage(), e);
		}
	}
	public Collection<Object>  getAggregateSummaryColl(Map<Object, Object> map, NBSSecurityObj nbsSecurityObj) throws NEDSSSystemException, NEDSSConcurrentDataException, RemoteException, CreateException{
		Collection<Object>  coll = new ArrayList<Object> ();
		try {
			if(map!=null && map.values().size()>0){ 
				Iterator<Object>  it = map.keySet().iterator();
				StringBuffer  sqlQueryBuff = new StringBuffer();
				sqlQueryBuff.append(" where  ");
				while(it.hasNext()){
					String dataLocation = it.next().toString();
					System.out.println("data is :"+dataLocation);
					String value= map.get(dataLocation).toString();
						if(it.hasNext()){
							sqlQueryBuff.append(dataLocation).append("=\'"+value+"\' and ");
						}else{
							sqlQueryBuff.append(dataLocation).append("=\'"+value+"\'");
						}
				}
				String whereClause=sqlQueryBuff.toString()+ " AND  Public_Health_Case.record_status_cd='"+ NEDSSConstants.RECORD_STATUS_ACTIVE +"' AND CASE_TYPE_CD='"+ NEDSSConstants.STATUS_ACTIVE+"'";
				AggregateSummaryDataDAOImpl aggregateSummaryDataDAOImpl = new AggregateSummaryDataDAOImpl();
				coll = aggregateSummaryDataDAOImpl.getAggregateSummaryDataCollection(whereClause);
				}
			else{
				if(map==null)
					logger.error("PamProxyEJB.getAggregateSummaryColl: the select criteria map is null");
				else if(map.values().size()==0)
					logger.error("PamProxyEJB.getAggregateSummaryColl: the select criteria map is empty");
				
			} 
		}catch (Exception e) {
			 logger.fatal("Exception occured in PamProxyEJB.getAggregateSummary, nbsSecurityObj.getFullName: " + nbsSecurityObj.getFullName() + ", " + e.getMessage(), e);
	         e.printStackTrace();
	         throw new EJBException(e.getMessage(),e);
		}
		
		return coll;
	}

	
	/**
	 * deleteSummaryNotification handles a NOTIFICATION associated to AggregateSummary Report as follows:
	 *  (a) If auto_resend_ind= "T", then set record_status_cd=PEND_DEL and resend the notification
	 *  (b) If auto_resend_ind= "F", then set record_status_cd=LOG_DEL
	 * @param phcVO
	 * @param nbsSecurityObj
	 */
	private void deleteSummaryNotification(PublicHealthCaseVO phcVO,
			NBSSecurityObj nbsSecurityObj) throws  Exception {
		
		try {
			NedssUtils nedssUtils = new NedssUtils();
			RetrieveSummaryVO rsvo = new RetrieveSummaryVO();
			ArrayList<Object>  theNotificationSummaryVOCollection  = new ArrayList<Object> ((rsvo
					.retrieveNotificationSummaryListForManage(phcVO
							.getThePublicHealthCaseDT(), nbsSecurityObj)));

			NotificationSummaryVO notificationSummaryVO = null;
			if (theNotificationSummaryVOCollection.size() > 0) {
				notificationSummaryVO = (NotificationSummaryVO) theNotificationSummaryVOCollection
						.get(0);

				ActController actController = null;
				Object object = nedssUtils.lookupBean(JNDINames.ActControllerEJB);
				logger.debug("ActController lookup = " + object.toString());
				ActControllerHome acthome = (ActControllerHome) PortableRemoteObject
				.narrow(object, ActControllerHome.class);
				logger.debug("Found ActControllerHome: " + acthome);
				actController = acthome.create();
				

				PrepareVOUtils prepareVOUtils = new PrepareVOUtils();
				NotificationProxy notificationProxy = null;
				Object objNotification = nedssUtils
						.lookupBean(JNDINames.NOTIFICATION_PROXY_EJB);
				logger.debug("NotificationProxyEJB lookup = "
						+ objNotification.toString());
				NotificationProxyHome notificationProxyHome = (NotificationProxyHome) PortableRemoteObject
						.narrow(objNotification, NotificationProxyHome.class);
				logger.debug("Found NotificationProxyHome: "
						+ notificationProxyHome);
				notificationProxy = notificationProxyHome.create();

				NotificationProxyVO notificationProxyVO = notificationProxy
						.getNotificationProxy(notificationSummaryVO
								.getNotificationUid(), nbsSecurityObj);
				NotificationDT notificationDT = notificationProxyVO
						.getTheNotificationVO().getTheNotificationDT();

				//
				if (notificationDT.getAutoResendInd().equalsIgnoreCase("T")) {
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
										BUSINESS_TRIGGER_CD, TABLE_NAME, MODULE_CD,
										nbsSecurityObj);

						actController.setNotificationInfo(newNotificationDT,
								nbsSecurityObj);
						// nndMSHelper.updateAutoResendNotificationsAsync(investigationProxyVO,
						// nbsSecurityObj);*/
					} catch (Exception e) {
						NNDMessageSenderHelper nndMSHelper = null;
						nndMSHelper = NNDMessageSenderHelper.getInstance();
						NNDActivityLogDT nndActivityLogDT = new NNDActivityLogDT();
						String phcLocalId = phcVO.getThePublicHealthCaseDT()
								.getLocalId();
						nndActivityLogDT.setErrorMessageTxt(e.toString());
						if (phcLocalId != null)
							nndActivityLogDT.setLocalId(phcLocalId);
						else
							nndActivityLogDT.setLocalId("N/A");

						// catch & store auto resend notifications exceptions in
						// NNDActivityLog table
						nndMSHelper.persistNNDActivityLog(nndActivityLogDT,
								nbsSecurityObj);
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

					NotificationDT newNotificationDT = (NotificationDT) prepareVOUtils
							.prepareVO(notificationDT, BUSINESS_OBJECT_LOOKUP_NAME,
									BUSINESS_TRIGGER_CD, TABLE_NAME, MODULE_CD,
									nbsSecurityObj);

					actController.setNotificationInfo(newNotificationDT,
							nbsSecurityObj);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("Exception occured in PamProxyEJB.deleteSummaryNotification, PublicHealthCaseUid: " + phcVO.getThePublicHealthCaseDT().getPublicHealthCaseUid() + ", " + e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}

	}
	public Long setContactProxyVO(CTContactProxyVO cTContactProxyVO, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException,
	javax.ejb.EJBException, javax.ejb.CreateException, NEDSSSystemException, NEDSSConcurrentDataException{
		try {
			NedssUtils nedssUtils = new NedssUtils();
			CTContactVO updatedCTContactVO = null;
			
			Object lookedUpObj = nedssUtils
			.lookupBean(JNDINames.EntityControllerEJB);
			logger.debug("!!!!!!!!!!EntityController lookup = "+ lookedUpObj.toString());
			EntityControllerHome ecHome = (EntityControllerHome) PortableRemoteObject
			.narrow(lookedUpObj, EntityControllerHome.class);
			logger.debug("!!!!!!!!!!Found EntityControllerHome: " + ecHome);
			EntityController entityController = ecHome.create();
			String businessTriggerCd=null;
			if (cTContactProxyVO.isItNew()) {
				businessTriggerCd = "CON_CR";
				if(cTContactProxyVO.getContactPersonVO().getThePersonDT().getPersonParentUid()<0)
					cTContactProxyVO.getContactPersonVO().getThePersonDT().setPersonParentUid(null);
				cTContactProxyVO.getContactPersonVO().getThePersonDT().setItDirty(true);
				cTContactProxyVO.getContactPersonVO().getThePersonDT().setItNew(true);			
				cTContactProxyVO.getContactPersonVO().setItDirty(true);
				cTContactProxyVO.getContactPersonVO().setItNew(true);
			}else if (cTContactProxyVO.isItDelete()) {
				cTContactProxyVO = getContactProxyVO(cTContactProxyVO.getcTContactVO().getcTContactDT().getCtContactUid(), nbsSecurityObj);
				cTContactProxyVO.setItDirty(true);
				businessTriggerCd = "CON_DEL";
				cTContactProxyVO.getcTContactVO().setItDelete(true);
				cTContactProxyVO.getcTContactVO().setItDirty(false);
				cTContactProxyVO.getcTContactVO().getcTContactDT().setItDirty(false);
				cTContactProxyVO.getcTContactVO().getcTContactDT().setItDelete(true);
				cTContactProxyVO.getContactPersonVO().getThePersonDT().setItDirty(false);
				cTContactProxyVO.getContactPersonVO().getThePersonDT().setItDelete(true);			
				cTContactProxyVO.getContactPersonVO().setItDirty(false);
				cTContactProxyVO.getContactPersonVO().setItDelete(true);
				
			}else if (cTContactProxyVO.isItDirty()) {
				businessTriggerCd = "CON_EDIT";
			}
			
			String businessObjLookupName = NBSBOLookup.CT_CONTACT;
			String tableName = "CT_CONTACT";
			String moduleCd = "BASE";
			CTContactDT contactDT = cTContactProxyVO.getcTContactVO().getcTContactDT();
			RootDTInterface rootDTInterface = contactDT;
			PrepareVOUtils prepareVOUtils = new PrepareVOUtils();
			contactDT = (CTContactDT) prepareVOUtils
			.prepareVO(rootDTInterface, businessObjLookupName,
					businessTriggerCd, tableName, moduleCd,
					nbsSecurityObj);
			cTContactProxyVO.getcTContactVO().setcTContactDT(contactDT);

			ActController actController = null;
			Object object = nedssUtils.lookupBean(JNDINames.ActControllerEJB);
			logger.debug("ActController lookup = " + object.toString());
			ActControllerHome acthome = (ActControllerHome) PortableRemoteObject
			.narrow(object, ActControllerHome.class);
			logger.debug("Found ActControllerHome: " + acthome);
			actController = acthome.create();
				Long patientUid = null;
				Long falsePatientUid=cTContactProxyVO.getContactPersonVO().getThePersonDT().getPersonUid();
				PersonVO personVO=  cTContactProxyVO.getContactPersonVO();
				if (personVO.getThePersonDT().getCd().equals(
						NEDSSConstants.PAT)) { // Patient
					if(businessTriggerCd.equalsIgnoreCase("CON_CR"))
						businessTriggerCd = NEDSSConstants.PAT_CR;
					else if(businessTriggerCd.equalsIgnoreCase("CON_DEL")){
						businessTriggerCd = NEDSSConstants.PAT_DEL;
						personVO.setItDirty(true);
					}else if(businessTriggerCd.equalsIgnoreCase("CON_EDIT"))
						businessTriggerCd = NEDSSConstants.PAT_EDIT;
					
					try {
						
						patientUid = entityController.setPatientRevision(personVO, businessTriggerCd,nbsSecurityObj);
					} catch (NEDSSConcurrentDataException ex) {
						logger.fatal("PamProxyEJB.setContactProxyVO: Concurrent access is not allowed!" + ex.getMessage(),ex);
						// cntx.setRollbackOnly();
						throw new NEDSSConcurrentDataException(ex.getMessage(),ex);
					} catch (Exception ex) {
						logger.fatal("PamProxyEJB.setContactProxyVO:Error in executing entityController.setPatientRevision when personVO.isNew is true" + ex.getMessage(),ex);
						ex.printStackTrace();
						throw new javax.ejb.EJBException(ex.getMessage(),ex);
					}
				}
				
				cTContactProxyVO.getcTContactVO().getcTContactDT().setContactEntityUid(patientUid);
				updatedCTContactVO = actController.setCTContact(cTContactProxyVO.getcTContactVO(), nbsSecurityObj);
				
				try{
					if(cTContactProxyVO.getMessageLogDtMap() != null){
						MessageLogDAOImpl messageLogDAOImpl =  new MessageLogDAOImpl();
						if(cTContactProxyVO.getMessageLogDtMap().values().size()>0){
							messageLogDAOImpl.storeMessageLogDTCollection(cTContactProxyVO.getMessageLogDtMap().values());
						}
					}
					
				}catch (Exception ex) {
					logger.fatal("PamProxyEJB.setContactProxyVO:Error in executing entityController.setPatientRevision setting message log" + ex.getMessage(),ex);
					ex.printStackTrace();
				throw new javax.ejb.EJBException(ex.getMessage(),ex);
				}
				
				Long docUid = null;
				ActRelationshipDAOImpl actRelationshipDAOImpl = null;
				if (cTContactProxyVO.getcTContactVO()
						.getTheActRelationshipDTCollection() != null) {
					actRelationshipDAOImpl = (ActRelationshipDAOImpl) NEDSSDAOFactory
							.getDAO(JNDINames.ACT_RELATIONSHIP_DAO_CLASS);
					for (ActRelationshipDT actRelationshipDT : cTContactProxyVO.getcTContactVO()
							.getTheActRelationshipDTCollection()) {
						if (actRelationshipDT.getTypeCd() != null
								&& actRelationshipDT.getTypeCd().equals(
										NEDSSConstants.DocToCON))
							docUid = actRelationshipDT.getSourceActUid();
						actRelationshipDT.setTargetActUid(updatedCTContactVO.getcTContactDT().getCtContactUid());
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
							logger.fatal("nbsSecurityObj.getFullName: " + nbsSecurityObj.getFullName() + ", " + e.getMessage(), e);
							e.printStackTrace();
							throw new javax.ejb.EJBException(e.getMessage(),e);
						}
					}
				}
				
				if (cTContactProxyVO.getcTContactVO()
						.getEdxEventProcessDTCollection() != null) {
					NbsDocumentDAOImpl documentDAO = new NbsDocumentDAOImpl();
					for (EDXEventProcessDT processDT : cTContactProxyVO.getcTContactVO()
							.getEdxEventProcessDTCollection()) {
						if(processDT.getDocEventTypeCd()!=null && processDT.getDocEventTypeCd().equals(NEDSSConstants.CLASS_CD_CONTACT))
							processDT.setNbsEventUid(updatedCTContactVO.getcTContactDT().getCtContactUid());
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
					 nbsDocument.updateDocumentWithOutthePatient(nbsDocVO, nbsSecurityObj);
				    } catch (Exception e) {
						logger.error("Error while updating the Document table, docUid: " + docUid + e
								.getMessage(), e);
						e.printStackTrace();
						throw new javax.ejb.EJBException(e.getMessage(),e);
					}
				}
				
			return updatedCTContactVO.getcTContactDT().getCtContactUid();
		} catch (ClassCastException e) {
			// TODO Auto-generated catch block
			logger.fatal("ClassCastException occured in PamProxyEJB.setContactProxyVO, LocalID: " + cTContactProxyVO.getcTContactVO().getLocalId() + ", " + e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
	}
	public CTContactProxyVO getContactProxyVO(Long ctContactUid, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException,
	javax.ejb.EJBException, javax.ejb.CreateException, NEDSSSystemException, NEDSSConcurrentDataException{
		try {
			NedssUtils nedssUtils = new NedssUtils();
			CTContactVO cTContactVO = null;
			CTContactProxyVO cTContactProxyVO = new CTContactProxyVO();
			Object lookedUpObj = nedssUtils
			.lookupBean(JNDINames.EntityControllerEJB);
			logger.debug("!!!!!!!!!!EntityController lookup = "+ lookedUpObj.toString());
			EntityControllerHome ecHome = (EntityControllerHome) PortableRemoteObject
			.narrow(lookedUpObj, EntityControllerHome.class);
			logger.debug("!!!!!!!!!!Found EntityControllerHome: " + ecHome);
			EntityController entityController = ecHome.create();
			
			
			ActController actController = null;
			Object object = nedssUtils.lookupBean(JNDINames.ActControllerEJB);
			logger.debug("ActController lookup = " + object.toString());
			ActControllerHome acthome = (ActControllerHome) PortableRemoteObject
			.narrow(object, ActControllerHome.class);
			logger.debug("Found ActControllerHome: " + acthome);
			actController = acthome.create();
			
			cTContactVO = actController.getCTContact(ctContactUid, nbsSecurityObj);
			cTContactProxyVO.setcTContactVO(cTContactVO);
			Long contactPersonUid = cTContactVO.getcTContactDT().getContactEntityUid();
			
			PersonVO contactPersonVO =entityController.getPatientRevision(contactPersonUid, nbsSecurityObj);
			cTContactProxyVO.setContactPersonVO(contactPersonVO);
			//TODO: extend the code for site and provider
			String typeCd;
			String recordStatusCd = "";
			Long nEntityID;
			NbsActEntityDT nbsActEntityDT = null;

			Iterator<Object>  actEntityCollection = cTContactVO.getActEntityDTCollection().iterator();
			logger.debug("actEntityDTCollection() = "
					+ cTContactVO.getActEntityDTCollection());
			Collection<Object> thePersonVOCollection = new ArrayList<Object>();
			Collection<Object> theOrganizationVOCollection = new ArrayList<Object>();
			
			// Populate the Entity collections with the results
			while (actEntityCollection.hasNext()) {
				nbsActEntityDT = (NbsActEntityDT) actEntityCollection
				.next();
				nEntityID = nbsActEntityDT.getEntityUid();
				typeCd = nbsActEntityDT.getTypeCd();
				recordStatusCd = nbsActEntityDT.getRecordStatusCd();
				if (typeCd != null
						&& (typeCd.equalsIgnoreCase(NEDSSConstants.CONTACT_PROVIDER))) {
					thePersonVOCollection.add(entityController
							.getPerson(nEntityID, nbsSecurityObj));
					continue;
				}
				if (typeCd != null
						&& (typeCd.equalsIgnoreCase(NEDSSConstants.CONTACT_DISPOSITIONED_BY))) {
					thePersonVOCollection.add(entityController
							.getPerson(nEntityID, nbsSecurityObj));
					continue;
				}
				if (typeCd != null
						&& (typeCd.equalsIgnoreCase(NEDSSConstants.CONTACT_OTHER_INFECTED_PATIENT))) {
					thePersonVOCollection.add(entityController
							.getPerson(nEntityID, nbsSecurityObj));
					continue;
				}				
				if (typeCd != null
						&& typeCd.equalsIgnoreCase(NEDSSConstants.CONTACT_ORGANIZATION)) {
					theOrganizationVOCollection.add(entityController.getOrganization(nEntityID, nbsSecurityObj));
					continue;
				}
				
				if (typeCd == null || typeCd.length() == 0) {
					continue;
				}
			}
			cTContactProxyVO.setOrganizationVOCollection(theOrganizationVOCollection);
			cTContactProxyVO.setPersonVOCollection(thePersonVOCollection);
			
			return cTContactProxyVO;
		} catch (ClassCastException e) {
			// TODO Auto-generated catch block
			logger.fatal("ClassCastException occured, ctContactUid: " + ctContactUid + ", " + e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
	}
	

	public Long setContactAttachment(CTContactAttachmentDT ctContactAttachmentDT, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException,
	javax.ejb.EJBException, javax.ejb.CreateException, NEDSSSystemException, NEDSSConcurrentDataException {
		try {
			if (!nbsSecurityObj.getPermission(NBSBOLookup.CT_CONTACT, NBSOperationLookup.EDIT)) {
				logger.info("nbsSecurityObj.getPermission(NedssBOLookup.CT_CONTACT,NBSOperationLookup.EDIT) is false");
				throw new NEDSSSystemException("NO PERMISSIONS");
			}
			logger.info("nbsSecurityObj.getPermission(NedssBOLookup.CT_CONTACT,NBSOperationLookup.EDIT) is true");

			CTContactAttachmentDAO ctContactAttachmentDAO = new CTContactAttachmentDAO();
			return ctContactAttachmentDAO.insertCTContactAttachment(ctContactAttachmentDT);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("CtContactUid: " + ctContactAttachmentDT.getCtContactUid() + ", " + e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
	}
	
	public byte[] getContactAttachment(Long ctContactAttachmentUid, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException,
	javax.ejb.EJBException, javax.ejb.CreateException, NEDSSSystemException, NEDSSConcurrentDataException {
		try {
			if (!nbsSecurityObj.getPermission(NBSBOLookup.CT_CONTACT, NBSOperationLookup.VIEW)) {
				logger.info("nbsSecurityObj.getPermission(NedssBOLookup.CT_CONTACT,NBSOperationLookup.VIEW) is false");
				throw new NEDSSSystemException("NO PERMISSIONS");
			}
			logger.info("nbsSecurityObj.getPermission(NedssBOLookup.CT_CONTACT,NBSOperationLookup.VIEW) is true");

			CTContactAttachmentDAO ctContactAttachmentDAO = new CTContactAttachmentDAO();
			return ctContactAttachmentDAO.getContactAttachment(ctContactAttachmentUid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("ctContactAttachmentUid: " + ctContactAttachmentUid + ", " + e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
	}
	
	public Collection<Object> getContactAttachmentSummaryCollection(Long ctContactUid, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException,
	javax.ejb.EJBException, javax.ejb.CreateException, NEDSSSystemException, NEDSSConcurrentDataException {
		try {
			if (!nbsSecurityObj.getPermission(NBSBOLookup.CT_CONTACT, NBSOperationLookup.VIEW)) {
				logger.info("nbsSecurityObj.getPermission(NedssBOLookup.CT_CONTACT,NBSOperationLookup.VIEW) is false");
				throw new NEDSSSystemException("NO PERMISSIONS");
			}
			logger.info("nbsSecurityObj.getPermission(NedssBOLookup.CT_CONTACT,NBSOperationLookup.VIEW) is true");

			CTContactAttachmentDAO ctContactAttachmentDAO = new CTContactAttachmentDAO();
			return ctContactAttachmentDAO.getSummaryCTContactAttachmentDTCollection(ctContactUid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("ctContactUid: " + ctContactUid + ", " + e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
	}
	
	public void deleteContactAttachment(Long ctContactAttachmentUid, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, 
	javax.ejb.EJBException, javax.ejb.CreateException, NEDSSSystemException, NEDSSConcurrentDataException {
		try {
			if (!nbsSecurityObj.getPermission(NBSBOLookup.CT_CONTACT, NBSOperationLookup.EDIT)) {
				logger.info("nbsSecurityObj.getPermission(NedssBOLookup.CT_CONTACT,NBSOperationLookup.EDIT) is false");
				throw new NEDSSSystemException("NO PERMISSIONS");
			}
			logger.info("nbsSecurityObj.getPermission(NedssBOLookup.CT_CONTACT,NBSOperationLookup.EDIT) is true");

			CTContactAttachmentDAO ctContactAttachmentDAO = new CTContactAttachmentDAO();
			ctContactAttachmentDAO.removeCTContactAttachment(ctContactAttachmentUid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("ctContactAttachmentUid: " + ctContactAttachmentUid + ", " + e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
	}
	

	public Long setContactNote(CTContactNoteDT ctContactNoteDT, NBSSecurityObj nbsSecurityObj) 
			throws java.rmi.RemoteException, javax.ejb.EJBException, javax.ejb.CreateException, 
			NEDSSSystemException, NEDSSConcurrentDataException 
	{
		try {
			if (!nbsSecurityObj.getPermission(NBSBOLookup.CT_CONTACT, NBSOperationLookup.EDIT)) {
				logger.info("nbsSecurityObj.getPermission(NedssBOLookup.CT_CONTACT,NBSOperationLookup.EDIT) is false");
				throw new NEDSSSystemException("NO PERMISSIONS");
			}
			logger.info("nbsSecurityObj.getPermission(NedssBOLookup.CT_CONTACT,NBSOperationLookup.EDIT) is true");

			CTContactNoteDAO ctContactNoteDAO = new CTContactNoteDAO();
			return ctContactNoteDAO.insertCTContactNote(ctContactNoteDT);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("CtContactUid: " + ctContactNoteDT.getCtContactUid() + ", JurisdictionCd: " + ctContactNoteDT.getJurisdictionCd() + ", " + e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
	}

	
	public Collection<Object> getNamedAsContactSummaryByCondition(Long publicHealthCaseUID, Long mprUid, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, 
	javax.ejb.EJBException, javax.ejb.CreateException, NEDSSSystemException, NEDSSConcurrentDataException {
		try {
			if (!nbsSecurityObj.getPermission(NBSBOLookup.CT_CONTACT, NBSOperationLookup.VIEW)) {
				logger.info("nbsSecurityObj.getPermission(NedssBOLookup.CT_CONTACT,NBSOperationLookup.VIEW) is false");
				throw new NEDSSSystemException("NO PERMISSIONS");
			}
			logger.info("nbsSecurityObj.getPermission(NedssBOLookup.CT_CONTACT,NBSOperationLookup.VIEW) is true");
			
			CTContactSummaryDAO ctContactSummaryDAO = new CTContactSummaryDAO();
			return ctContactSummaryDAO.getNamedAsContactSummaryByCondition(publicHealthCaseUID,mprUid,nbsSecurityObj);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("publicHealthCaseUID: " + publicHealthCaseUID + ", mprUid: " + mprUid + ", " + e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}		
	}
	/**
	 * getInterviewSummaryForInvestigation - get the list of interviews associated with the PHC
	 *  Used by STD Contact Named field.
	 * @param phcUid
	 * @param programArea
	 * @param nbsSecurityObj
	 * @return InterviewSummaryDT collection
	 */
	public Collection<Object> getInterviewSummaryforInvestigation(Long publicHealthCaseUid, String programArea,  NBSSecurityObj nbsSecurityObj)
			throws java.rmi.RemoteException, javax.ejb.EJBException, NEDSSSystemException {
	
		Collection<Object> interviewCollection = new ArrayList<Object>();
		if (nbsSecurityObj.getPermission(NBSBOLookup.INTERVIEW,
					NBSOperationLookup.VIEW)) {
			try{
				InterviewSummaryDAO interviewSummaryDAO = new InterviewSummaryDAO();
				interviewCollection = interviewSummaryDAO
						.getInterviewListForInvestigation(publicHealthCaseUid,
								programArea,								
								nbsSecurityObj);
	        }catch(Exception e){
	        	logger.fatal("publicHealthCaseUid: " + publicHealthCaseUid.toString() + ", programArea: " + programArea + ", " + e.getMessage(), e);
	        	throw new NEDSSSystemException(e.getMessage(), e);
	        }
		}
		return interviewCollection;
	}
				
	public void updateContactAssociations(Long publicHealthCaseUID, Collection<Object> addContactAssociationUIDs, Collection<Object> removeContactAssociationsUIDs, NBSSecurityObj nbsSecurityObj)throws java.rmi.RemoteException, 
	javax.ejb.EJBException, javax.ejb.CreateException, NEDSSSystemException, NEDSSConcurrentDataException {
		NedssUtils nedssUtils = new NedssUtils();
		ActController actController = null;
		Object object = nedssUtils.lookupBean(JNDINames.ActControllerEJB);
		logger.debug("ActController lookup = " + object.toString());
		ActControllerHome acthome = (ActControllerHome) PortableRemoteObject
		.narrow(object, ActControllerHome.class);
		logger.debug("Found ActControllerHome: " + acthome);
		actController = acthome.create();

		CTContactVO ctContactVO;
        try{
        
			if (addContactAssociationUIDs != null && addContactAssociationUIDs.size() > 0) {
				Iterator itAssociate = addContactAssociationUIDs.iterator();
				while (itAssociate.hasNext()) {
					Long associateUid = new Long((String)(itAssociate.next()));
					ctContactVO = actController.getCTContact(associateUid, nbsSecurityObj);
					ctContactVO.setAssociateDissasociate(true);
					CTContactDT contactDT = ctContactVO.getcTContactDT();
					if (contactDT.getContactEntityPhcUid() == null ) {
						contactDT.setItDirty(true);
						String businessObjLookupName = NBSBOLookup.CT_CONTACT;
						String tableName = "CT_CONTACT";
						String moduleCd = "BASE";
						String businessTriggerCd = "CON_PHC_ASC";
						RootDTInterface rootDTInterface = contactDT;
						PrepareVOUtils prepareVOUtils = new PrepareVOUtils();
						contactDT = (CTContactDT) prepareVOUtils.prepareVO(rootDTInterface, 
								businessObjLookupName,
								businessTriggerCd, 
								tableName, 
								moduleCd,
								nbsSecurityObj);
						ctContactVO.setItDirty(true);
						Collection<Object> actEntityColl = ctContactVO.getActEntityDTCollection();
						//setting the iDirty/ItNew for Act
						ctContactVO.setActEntityDTCollection(setActDirtyNew(actEntityColl));		
						contactDT.setContactEntityPhcUid(publicHealthCaseUID);
						ctContactVO.setcTContactDT(contactDT);							
						actController.setCTContact(ctContactVO, nbsSecurityObj);
					}
				}
			}
	      
	        
			if (removeContactAssociationsUIDs != null && removeContactAssociationsUIDs.size() > 0) {
				Iterator itDisassociate = removeContactAssociationsUIDs.iterator();
				while (itDisassociate.hasNext()) {
					Long disassociateUid = new Long((String)(itDisassociate.next()));
					ctContactVO = actController.getCTContact(disassociateUid, nbsSecurityObj);
					ctContactVO.setAssociateDissasociate(true);
					CTContactDT contactDT = ctContactVO.getcTContactDT();
					if (contactDT.getContactEntityPhcUid() != null ) {
						contactDT.setItDirty(true);
						String businessObjLookupName = NBSBOLookup.CT_CONTACT;
						String tableName = "CT_CONTACT";
						String moduleCd = "BASE";
						String businessTriggerCd = "CON_PHC_DIS_ASC";
						RootDTInterface rootDTInterface = contactDT;
						PrepareVOUtils prepareVOUtils = new PrepareVOUtils();
						contactDT = (CTContactDT) prepareVOUtils.prepareVO(rootDTInterface, 
								businessObjLookupName,
								businessTriggerCd, 
								tableName, 
								moduleCd,
								nbsSecurityObj);
						ctContactVO.setItDirty(true);						
						Collection<Object> actEntityColl = ctContactVO.getActEntityDTCollection();
						//setting the iDirty/ItNew for Act
						ctContactVO.setActEntityDTCollection(setActDirtyNew(actEntityColl));						
						ctContactVO.getcTContactDT().setContactEntityPhcUid(null);
						ctContactVO.setcTContactDT(contactDT);
						actController.setCTContact(ctContactVO, nbsSecurityObj);
					}
				}
			}
        }catch(Exception e){
        	logger.fatal("publicHealthCaseUid: " + publicHealthCaseUID.toString() + ", " + e.getMessage(), e);
        	throw new NEDSSSystemException(e.getMessage(), e);
        }
	}
	
	private Collection<Object> setActDirtyNew(Collection<Object> coll){
		try {
			Collection<Object> actColl = new ArrayList<Object>();
			
			if(coll != null){
				Iterator iter = coll.iterator();
				while(iter.hasNext()){
					NbsActEntityDT nbsActentityDT = (NbsActEntityDT)iter.next();
					nbsActentityDT.setItDirty(true);
					nbsActentityDT.setItNew(false);
					actColl.add(nbsActentityDT);
				}
			}
			return actColl;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("Excepion occured in PamProxyEJB.setActDirtyNew: " + e.getMessage(), e);
        	throw new NEDSSSystemException(e.getMessage(), e);
		}
	}
   
	public PublicHealthCaseVO getPublicHealthCaseVO(Long publicHealthCaseUid,NBSSecurityObj nbsSecurityObj)throws  javax.ejb.EJBException,java.rmi.RemoteException, NEDSSSystemException
	{
		try {
			NedssUtils nedssUtils = new NedssUtils();
			Object theLookedUpObject;
			PublicHealthCaseVO thePublicHealthCaseVO = null;
			try {
				theLookedUpObject = nedssUtils.lookupBean(JNDINames.ActControllerEJB);
					ActControllerHome actHome = (ActControllerHome) PortableRemoteObject.narrow(theLookedUpObject, ActControllerHome.class);
					ActController actController = actHome.create();
					thePublicHealthCaseVO = actController.getPublicHealthCase(publicHealthCaseUid, nbsSecurityObj);
			} catch (ClassCastException e) {
				logger.debug("PamProxy.getPublicHealthCaseVO = " + e.toString());
				e.printStackTrace();
			} catch (CreateException e) {
				logger.debug("PamProxy.getPublicHealthCaseVO = " + e.toString());
				e.printStackTrace();
			}
			return thePublicHealthCaseVO;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal("publicHealthCaseUid: " + publicHealthCaseUid.toString() + ", " + e.getMessage(), e);
        	throw new NEDSSSystemException(e.getMessage(), e);
		}
	}
} // PamProxyEJB
