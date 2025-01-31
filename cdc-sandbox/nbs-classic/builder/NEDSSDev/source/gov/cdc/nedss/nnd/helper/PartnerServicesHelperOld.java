package gov.cdc.nedss.nnd.helper;

import gov.cdc.nedss.act.actid.dt.ActIdDT;
import gov.cdc.nedss.act.ctcontact.dt.CTContactSummaryDT;
import gov.cdc.nedss.act.interview.dt.InterviewSummaryDT;
import gov.cdc.nedss.act.interview.ejb.dao.InterviewSummaryDAO;
import gov.cdc.nedss.act.notification.dt.NotificationDT;
import gov.cdc.nedss.act.notification.vo.NotificationVO;
import gov.cdc.nedss.act.publichealthcase.dt.PublicHealthCaseDT;
import gov.cdc.nedss.act.publichealthcase.vo.PublicHealthCaseVO;
import gov.cdc.nedss.association.dt.ActRelationshipDT;
import gov.cdc.nedss.controller.ejb.entitycontrollerejb.bean.EntityController;
import gov.cdc.nedss.controller.ejb.entitycontrollerejb.bean.EntityControllerHome;
import gov.cdc.nedss.entity.entityid.dt.EntityIdDT;
import gov.cdc.nedss.entity.person.dt.PersonDT;
import gov.cdc.nedss.entity.person.dt.PersonRaceDT;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.locator.dt.EntityLocatorParticipationDT;
import gov.cdc.nedss.locator.dt.PostalLocatorDT;
import gov.cdc.nedss.nnd.util.PartnerServicesConstantsOld;
import gov.cdc.nedss.page.ejb.pageproxyejb.bean.PageProxy;
import gov.cdc.nedss.page.ejb.pageproxyejb.bean.PageProxyHome;
import gov.cdc.nedss.page.ejb.pageproxyejb.dt.NbsAnswerDT;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.PageProxyVO;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.act.PageActProxyVO;
import gov.cdc.nedss.pam.vo.PamVO;
import gov.cdc.nedss.proxy.ejb.notificationproxyejb.bean.NotificationProxy;
import gov.cdc.nedss.proxy.ejb.notificationproxyejb.bean.NotificationProxyHome;
import gov.cdc.nedss.proxy.ejb.notificationproxyejb.vo.NotificationProxyVO;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.bean.PamProxy;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.bean.PamProxyHome;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.dao.CTContactSummaryDAO;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.vo.CTContactProxyVO;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dt.PartnerServicesLookupDT;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dt.PartnerServicesMigratedCaseDT;
import gov.cdc.nedss.systemservice.ejb.questionmapejb.dt.NbsQuestionMetadata;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.action.hivpartnerservices.HivPartnerServicesFileAction;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.rendering.util.PageLoadUtil;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.QuestionsCache;

import java.math.BigInteger;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.rmi.PortableRemoteObject;

import noNamespace.Type12.Enum;
import noNamespace.TypeAttemptToLocate;
import noNamespace.TypeCase;
import noNamespace.TypeClient;
import noNamespace.TypeClient.AsAPartner;
import noNamespace.TypeClient.AsAPartner.Partner;
import noNamespace.TypeClient.AsAPartner.Partner.ExposureNotifications;
import noNamespace.TypeClient.AsAPartner.Partner.Referrals;
import noNamespace.TypeClient.AsAPartner.Partner.AttemptsToLocate;
import noNamespace.TypeClient.AsAnIndex;
import noNamespace.TypeClient.AsAnIndex.ElicitPartners;
import noNamespace.TypeClient.AsAnIndex.PartnerServiceCases;
import noNamespace.TypeClient.ClientRiskProfiles;
import noNamespace.TypeElicitPartner;
import noNamespace.TypeHeader;
import noNamespace.TypeOtherRisk;
import noNamespace.TypePartnerNotification;
import noNamespace.TypeRaces;
import noNamespace.TypeReferral;
import noNamespace.TypeRiskProfile;
import noNamespace.TypeSession;
import noNamespace.TypeRaces.Race;
import noNamespace.XpemsPSDataDocument;
import noNamespace.XpemsPSDataDocument.XpemsPSData;
import noNamespace.XpemsPSDataDocument.XpemsPSData.Agency;
import noNamespace.XpemsPSDataDocument.XpemsPSData.Sites;

/**
 * PartnerServicesHelper - Helps populate the Partner Services schema elements.
 *  See PSData_v2.1.xsd. For documentation on elements and valid values 
 *  see NHM&E Data Variables and Values pdf document.
 * @author Gregory Tucker
 * <p>Copyright: Copyright (c) 2016</p>
 * <p>Company: CSRA for CDC</p>
 * May 4, 2017
 * @version 1.0
 */

public class PartnerServicesHelperOld {
	static final LogUtils logger = new LogUtils(PartnerServicesHelper.class.getName());
	private static PropertyUtil propertyUtil = PropertyUtil.getInstance();
	public static Map<Object, Object> caseQuestionMap;
	public static Map<Object, Object> ixsQuestionMap;
	public static Map<Object, Object> ctQuestionMap;
	//public static Map<String, String> nbsInvestigationLocalIdToStdMisCaseNo = new HashMap<String,String>();      //contains NBSCaseLocalId/STD*MIS Case No
	public static Map<String, String> patientLocalIdToStdMisPatientNum = new HashMap<String,String>();   //contains PatientLocalId/STD*MIS Patient Num
	//public static Map<String, String> patientLocalIdToFirstStdMisCaseNoMap = new HashMap<String,String>(); //contains Patient LocalId/STD*MIS First Case No
	public static Map<String, Long> localIdToUidCaseMap = new HashMap<String, Long>(); //phc local id, public health case uid
	public static Map<String, String> patientToFirstCaseLocalIdMap = new HashMap<String,String>();
	public static Map<String, String> patientToFirstLegacyCaseNoMap = new HashMap<String,String>();
	public static Map<String, ArrayList<PartnerServicesLookupDT>> patientToAllCasesMap = new HashMap<String, ArrayList<PartnerServicesLookupDT>>();
	public static Map<String, PartnerServicesClient> patientToClientMap = new HashMap<String, PartnerServicesClient>();
	public static Map<String, Long> patientInScopeHashMap = new HashMap<String, Long>();

	
	/**
	 * buildPartnerServicesDocument - root element
	 * @param partnerServicesColl
	 * @param xpemsdoc - PS doc
	 * @param dateRange - date range for header
	 * @param contactPerson - contact person for header
	 * @param invFormCd - investigation form code
	 * @param ixsFormCd - interview form code
	 * @param defaultStateCd 
	 * @param nbsSecurityObj
	 * @throws Exception
	 * @throws NEDSSAppException
	 * @throws RemoteException
	 */
	public static void buildPartnerServicesDocument 
	(
			ArrayList<Object> partnerServicesColl,
			XpemsPSDataDocument xpemsdoc,
			String startingDateRange,
			String endingDateRange,
			String contactPerson,
			String invFormCd, 
			String ixsFormCd,
			String defaultStateCd, 
			NBSSecurityObj nbsSecurityObj
		) throws Exception, NEDSSAppException, RemoteException {
		logger.debug("in buildPartnerServicesDocument();");
		helperSetup(invFormCd, ixsFormCd);
		XpemsPSData xpemsPSData = xpemsdoc.addNewXpemsPSData();
		TypeHeader headerInformation = xpemsPSData.addNewHeaderInformation();
		fillInHeaderInformation(headerInformation, startingDateRange, endingDateRange, contactPerson);
		Sites sites = xpemsPSData.addNewSites();
		Agency agency = xpemsPSData.addNewAgency();
		agency.setAgencyId(propertyUtil.getPartnerServicesDefaultAgency());

		//agency.setPopulatedAreaValueCode(propertyUtil.getNBS_STATE_CODE());
		
		//we'll be calling the PageProxy over and over
		PageProxy pageProxy = null;
		NedssUtils nu = new NedssUtils();
		Object object = nu.lookupBean(JNDINames.PAGE_PROXY_EJB);
		PageProxyHome pageProxyHome = (PageProxyHome) javax.rmi.PortableRemoteObject.narrow(object, PageProxyHome.class);
		try {
			 pageProxy = pageProxyHome.create();
			} catch (RemoteException e1) {
				logger.error("PartnerServicesHelper get PageProxy RemoteException thrown"
						+ e1);
				throw new RemoteException();
			} catch (CreateException e1) {
				logger.error("PartnerServicesHelper get PageProxy CreateException thrown"
						+ e1);
				throw new CreateException();
			}
		
		HashMap<String, String> siteIdHash = new HashMap();
		addDefaultSite(sites, siteIdHash); //per JW use default site for those with no zip and/or site type
		patientInScopeHashMap = getListOfPatientLocalIdsInTimeframe(partnerServicesColl);
		ArrayList<PartnerServicesClient> partnerServicesClientComprehensiveList = new ArrayList<PartnerServicesClient>();
		
		try {
			partnerServicesClientComprehensiveList = 
				populatePartnerServicesClientComprehensiveList(partnerServicesColl, pageProxy, sites, siteIdHash,nbsSecurityObj);
		} catch (Exception ex) {
			logger.error("PartnerServices error building Client Comprehensive List occurred " +ex.getMessage());
			throw new NEDSSAppException("PartnerServices Error Building Client Comprehensive List occurred " +ex.getMessage());
		}
			
		
		noNamespace.XpemsPSDataDocument.XpemsPSData.Agency.Clients clients = agency.addNewClients();
		ArrayList<PublicHealthCaseVO> phcVONotifColl=new ArrayList<PublicHealthCaseVO>();
		Iterator clientIter = partnerServicesClientComprehensiveList.iterator();
		int clientCounter = 0;
		while (clientIter.hasNext()) {
			++clientCounter;
			logger.info("----------**** Processing client "+clientCounter +" out of "+partnerServicesClientComprehensiveList.size() +"****------------ ");
			PartnerServicesClient partnerServicesClient = (PartnerServicesClient) clientIter.next();
			processNextClient(pageProxy, clients, partnerServicesClient, siteIdHash, sites, phcVONotifColl, defaultStateCd, nbsSecurityObj);
		}
		 if (siteIdHash.isEmpty()) { //must be at least 1 site
			logger.warn("PartnerServices: Warning- No sites specified in any Interview Record. Must be at least 1 site. Creating dummy site.");
			noNamespace.XpemsPSDataDocument.XpemsPSData.Sites.Site theSite = sites.addNewSite();
			theSite.setSiteId(PartnerServicesConstantsOld.PS_DEFAULT_SITE_ID);
			theSite.setSiteTypeValueCode(PartnerServicesConstantsOld.SITE_TYPES_MAP.get(PartnerServicesConstantsOld.PS_DEFAULT_SITE_ID));
			theSite.setZip(PartnerServicesConstantsOld.PS_DEFAULT_SITE_ZIP);
		 }
		//Create Notification for each Case
        String comments="PartnerServices File Notification Created";

        logger.info("Creating notifications for " + phcVONotifColl.size() + " HIV/AIDS investigations");
    	//Iterator phcCollIter = partnerServicesPHCCollection.iterator();
   		Object objectnotif = nu.lookupBean(JNDINames.NOTIFICATION_PROXY_EJB);
        NotificationProxyHome home = (NotificationProxyHome) PortableRemoteObject.narrow(objectnotif, NotificationProxyHome.class);
        NotificationProxy notificationproxy = home.create();
    	Iterator phcCollIter=phcVONotifColl.iterator();
    	while (phcCollIter.hasNext()) {
    		Object publicHealthCaseVO1=phcCollIter.next();
    		PublicHealthCaseVO publicHealthCaseVO=(PublicHealthCaseVO) publicHealthCaseVO1;
    		createPartnerServicesNotification(publicHealthCaseVO, notificationproxy,  comments, nbsSecurityObj, invFormCd);  
    	}
    	 logger.debug("Completed creating notifications");
    	return;
	}

	/**
	 * createPartnerServicesNotification - We are storing a new one each time.
	 * @param publicHealthCaseVO
	 * @param notificationProxy
	 * @param comments
	 * @param nbsSecurityObj
	 * @param invFormCd
	 * @throws javax.ejb.EJBException
	 * @throws Exception
	 * @throws Exception
	 */
	private static void createPartnerServicesNotification(PublicHealthCaseVO publicHealthCaseVO, NotificationProxy notificationProxy, String comments, NBSSecurityObj nbsSecurityObj, String invFormCd) throws javax.ejb.EJBException, Exception
    {

	PublicHealthCaseDT phcDT = publicHealthCaseVO.getThePublicHealthCaseDT();
	logger.info("PartnerServices creating notification for " + phcDT.getLocalId());
	Long phcUID = phcDT.getPublicHealthCaseUid();
	String programeAreaCode = phcDT.getProgAreaCd();
	String jurisdictionCode = phcDT.getJurisdictionCd();
	String sharedInd = phcDT.getSharedInd();
	
	Collection<Object> arColl = publicHealthCaseVO.getTheActRelationshipDTCollection();
	NotificationProxyVO notProxyVO = null;
	for (Iterator<Object> actRelIter = arColl.iterator(); actRelIter.hasNext();)
	{
		ActRelationshipDT actDT = (ActRelationshipDT) actRelIter.next();
		logger.debug("Checking act relation type= " + actDT.getTypeCd());
		if (  actDT.getTypeCd().equals(NEDSSConstants.ACT106_TYP_CD) && !actDT.isNNDInd()) {
			logger.debug("Partner Services Notification already exists for " + publicHealthCaseVO.getThePublicHealthCaseDT().getLocalId());
			comments = "Partner Services File Notification Re-Created";
		}
	}
    	/*
    	logger.debug("Calling Notification Proxy to find ", actDT.getSourceActUid().toString());
    	try {
    		notProxyVO = notificationProxy.getNotificationProxy(actDT.getSourceActUid(),nbsSecurityObj);
		} catch (EJBException e) {
			logger.error("PartnerServicesHelper Notification EJB  Exception thrown" + e);
			e.printStackTrace();
		} catch (NEDSSSystemException e) {
			logger.error("PartnerServicesHelper Notification System Exception thrown" + e);
			e.printStackTrace();
		}
   
        notProxyVO.setItDirty(true);
        notProxyVO.getTheNotificationVO().setItDirty(true);
       // NotificationDT notDTu=null;
        NotificationDT notDTu = notProxyVO.getTheNotificationVO().getTheNotificationDT();
        //notDTu.setAddTime(new java.sql.Timestamp(new Date().getTime()));
        notDTu.setTxt(Partner Services File Notification Created);
        //notDTu.setCaseClassCd(phcDT.getCaseClassCd());
        //notDTu.setStatusCd("A");
        notDTu.setRecordStatusCd(NEDSSConstants.NOTIFICATION_COMPLETED);
        //notDTu.setAutoResendInd("F");
        notDTu.setStatusTime(new java.sql.Timestamp(new Date().getTime()));
        notDTu.setSharedInd(sharedInd);
        notDTu.setItNew(false);
        notDTu.setItDirty(true);
        notDTu.setMmwrWeek(phcDT.getMmwrWeek() );
        notDTu.setMmwrYear(phcDT.getMmwrYear() );
        notificationProxy.setNotificationProxy(notProxyVO, nbsSecurityObj);
        return;
	    } 
	    */
	
	
	logger.debug("Creating Notification for " + publicHealthCaseVO.getThePublicHealthCaseDT().getLocalId());
    PublicHealthCaseVO phcVO = new PublicHealthCaseVO();
    phcVO.setThePublicHealthCaseDT(phcDT);
    phcVO.setItNew(false);
    phcVO.setItDirty(false);

    // Create the Notification object

    NotificationDT notDT = new NotificationDT();
    notDT.setItNew(true);
    notDT.setNotificationUid(new Long(-1));
    notDT.setAddTime(new java.sql.Timestamp(new Date().getTime()));
    notDT.setTxt(comments);
    notDT.setStatusCd("A");
    notDT.setCaseClassCd(phcDT.getCaseClassCd());
    notDT.setStatusTime(new java.sql.Timestamp(new Date().getTime()));
    notDT.setVersionCtrlNbr(new Integer(1));
    notDT.setSharedInd(sharedInd);
    notDT.setCaseConditionCd(phcDT.getCd());
    notDT.setRecordStatusCd(NEDSSConstants.NOTIFICATION_COMPLETED);
    notDT.setCd(NEDSSConstants.CLASS_CD_PSF_NOTF);
    notDT.setAutoResendInd("F");
    notDT.setMmwrWeek( phcDT.getMmwrWeek() );
    notDT.setMmwrYear(phcDT.getMmwrYear() );
   
    NotificationVO notVO = new NotificationVO();
    notVO.setTheNotificationDT(notDT);
    notVO.setItNew(true);

    // create the act relationship between the phc & notification
    ActRelationshipDT actDT1 = new ActRelationshipDT();
    actDT1.setItNew(true);
    actDT1.setTargetActUid(phcUID);
    actDT1.setSourceActUid(notDT.getNotificationUid());
    actDT1.setAddTime(new java.sql.Timestamp(new Date().getTime()));
    actDT1.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
    actDT1.setSequenceNbr(new Integer(1));
    actDT1.setStatusCd("A");
    actDT1.setNNDInd(false); //not really NND notification
    actDT1.setStatusTime(new java.sql.Timestamp(new Date().getTime()));
    actDT1.setTypeCd(NEDSSConstants.ACT106_TYP_CD);
    actDT1.setSourceClassCd(NEDSSConstants.ACT106_SRC_CLASS_CD);
    actDT1.setTargetClassCd(NEDSSConstants.ACT106_TAR_CLASS_CD);
   
    notProxyVO = new NotificationProxyVO();
    notProxyVO.setItNew(true);
    notProxyVO.setThePublicHealthCaseVO(phcVO);
    notProxyVO.setTheNotificationVO(notVO);

    ArrayList<Object> actRelColl = new ArrayList<Object>();
    actRelColl.add(0, actDT1);
    notProxyVO.setTheActRelationshipDTCollection(actRelColl);
	
	String programAreaCd = notProxyVO.getThePublicHealthCaseVO().getThePublicHealthCaseDT()
            .getProgAreaCd();
    notProxyVO.getTheNotificationVO().getTheNotificationDT().setProgAreaCd(programAreaCd);
    notificationProxy.setNotificationProxy(notProxyVO, nbsSecurityObj);
    logger.debug("Completed creating Notification for " + publicHealthCaseVO.getThePublicHealthCaseDT().getLocalId());
	return;
    }


	/**
	 * processNextClient
	 * @param pageProxy
	 * @param noNamespace.XpemsPSDataDocument.XpemsPSData.Agency.Clients
	 * @param partnerServicesClient
	 * @param siteIdHash
	 * @param sites
	 * @param defaultStateCd 
	 * @param nbsSecurityObj
	 * @return
	 * @throws RemoteException
	 * @throws CreateException
	 * @throws NEDSSAppException
	 */
	private static void processNextClient(
			PageProxy pageProxy,
			noNamespace.XpemsPSDataDocument.XpemsPSData.Agency.Clients clients,
			PartnerServicesClient partnerServicesClient, 
			HashMap<String, String> siteIdHash,
			Sites sites, 
			ArrayList<PublicHealthCaseVO> phcNotificationVOColl,
			String defaultStateCd, 
			NBSSecurityObj nbsSecurityObj) throws RemoteException, CreateException, NEDSSAppException {
		
		logger.info("PartnerServicesHelper processing client "+partnerServicesClient.getPatientLocalId());
		PartnerServicesLookupDT drivingClientLookupDT = findDrivingClientLookupDT(partnerServicesClient);
		PageActProxyVO drivingCasePhcProxyVO = null;
		if (!drivingClientLookupDT.getIsContactRcd()) {
			//Get the driving Investigation Page Act Proxy VO
			try {
				Long phcUid = drivingClientLookupDT.getPublicHealthCaseUid();
				drivingCasePhcProxyVO = (PageActProxyVO) pageProxy.getPageProxyVO(NEDSSConstants.CASE, phcUid, nbsSecurityObj);
				phcNotificationVOColl.add(drivingCasePhcProxyVO.getPublicHealthCaseVO());
			} catch (EJBException e) {
				logger.error("PartnerServicesHelper Investigation Exception thrown " + e);
				e.printStackTrace();
			} catch (NEDSSSystemException e) {
				logger.error("PartnerServicesHelper Investigation System Exception thrown " + e);
				e.printStackTrace();
			} catch (FinderException e) {
				logger.error("PartnerServicesHelper Investigation Finder Exception thrown " + e);
				e.printStackTrace();
			}
			if (drivingCasePhcProxyVO == null) {
				logger.error("PartnerServicesHelper  Error: Investigation could not be retrieved?? " + drivingClientLookupDT.getPublicHealthCaseUid().toString() );
				return; //deleted?
			}
		} //not contact only record..
		//walk through the data and populate the client
		TypeClient client = clients.addNewClient();
		Boolean processedWithError = false;
		try {
				populateClient (
					partnerServicesClient,
					drivingCasePhcProxyVO,
					client,
					drivingClientLookupDT,
					pageProxy,
					phcNotificationVOColl,
					siteIdHash,
					sites,
					defaultStateCd,
					nbsSecurityObj);
				} catch (Exception e) {
					logger.error("PartnerServicesHelper populateClient Exception thrown " + e);
					e.printStackTrace();
					processedWithError = true;
				}
		/* failsafe check for empty client - must be at least one index or partner */
		if (processedWithError || (client.getAsAnIndex() == null && (client.getAsAPartnerList() == null || client.getAsAPartnerList().isEmpty())) ) {
			int currentClient = clients.sizeOfClientArray();
			clients.removeClient(--currentClient);
			if (processedWithError)
				logger.error("Error processing client - PartnerServicesHelper removing client "+partnerServicesClient.getPatientLocalId());
			else
				logger.error("Error no index or partner in client?? - PartnerServicesHelper removing this client "+partnerServicesClient.getPatientLocalId());
			return;
		}
		logger.info("PartnerServicesHelper completed processing client "+partnerServicesClient.getPatientLocalId());
		return;
	}
	/**
	 * populateSiteIfNotInList
	 * @param siteIdHash
	 * @param sites
	 * @param interviewSummaryCollection
	 */
	private static void populateSiteIfNotInList(
			HashMap<String, String> siteIdHash,
			Sites sites, 
			Collection interviewSummaryCollection) {

		logger.debug("in populateSiteIfNotInList()");
		String siteId = null;
		String siteZip = null;
		String siteType = null;
		try {
			Iterator interviewSummaryListIter = interviewSummaryCollection.iterator();
			while (interviewSummaryListIter.hasNext()) {
				InterviewSummaryDT interviewSummaryDT = (InterviewSummaryDT) interviewSummaryListIter.next();
				if (interviewSummaryDT.getThe900SiteId() != null)
					siteId = interviewSummaryDT.getThe900SiteId();
				if (interviewSummaryDT.getThe900SiteTypeCd() != null)
					siteType = interviewSummaryDT.getThe900SiteTypeCd();
				if (interviewSummaryDT.getThe900SiteZipCd() != null)
					siteZip = interviewSummaryDT.getThe900SiteZipCd();
				if (siteZip == null || siteZip.isEmpty() || siteZip.length() > 10) 
					continue;
				if (siteId == null || siteId.isEmpty() || siteType == null || siteType.isEmpty() )
					continue;
				if (siteIdHash.containsKey(siteId))
					continue; //already in list
				if (siteId.length() > 32)
					continue; // would invalidate xml
				if (PartnerServicesConstantsOld.SITE_TYPES_MAP.containsKey(siteType))
					logger.debug("Adding Site SiteType=" +siteType +" SiteId=" +siteId + " siteZip="+siteZip);
				else {
						logger.error("PartnerServices: Missing ?? site type of "+siteType);
						continue;
				}
				noNamespace.XpemsPSDataDocument.XpemsPSData.Sites.Site theSite = sites.addNewSite();
				theSite.setSiteId(siteId);
				siteIdHash.put(siteId, siteType);
				theSite.setSiteTypeValueCode(PartnerServicesConstantsOld.SITE_TYPES_MAP.get(siteType));
				if (siteZip != null) // you get a warning if no zip, dan says should always have a zip
					theSite.setZip(siteZip);
			} // while next

		} catch (Exception ex) {
			logger.error("PartnerServices populateSiteIfNotInList encountered exception adding site " +ex.getMessage());
		}
		logger.debug("leaving populateSiteIfNotInList()");
		return;

	}

	/**
	 * Populate a comprehensive Client record.
	 * @param partnerServicesClient
	 * @param drivingPhcProxyVO
	 * @param client
	 * @param drivingPartnerServicesDT
	 * @param pageProxy
	 * @param phcNotificationVOColl
	 * @param siteIdHash
	 * @param sites
	 * @param defaultStateCd 
	 * @param nbsSecurityObj
	 * @throws Exception
	 */
	private static void populateClient(
			PartnerServicesClient partnerServicesClient,
			PageActProxyVO drivingPhcProxyVO, 
			TypeClient client,
			PartnerServicesLookupDT drivingPartnerServicesDT, 
			PageProxy pageProxy, 
			ArrayList<PublicHealthCaseVO> phcNotificationVOColl, 
			HashMap<String, String> siteIdHash, Sites sites, 
			String defaultStateCd, 
			NBSSecurityObj nbsSecurityObj) throws Exception {
	
		logger.debug("in populateClient()");
		
		PersonVO patientVO = null;
		AsAPartner partnerInformation = null;
		if (drivingPhcProxyVO != null) {
			patientVO = PageLoadUtil.getPersonVO("SubjOfPHC",
				(PageProxyVO) drivingPhcProxyVO);
				logger.debug("PartnerServices processing client " + patientVO.getThePersonDT().getLocalId());
		} else if (drivingPartnerServicesDT.getCtContactProxyVO() != null) {
			patientVO = drivingPartnerServicesDT.getCtContactProxyVO().getContactPersonVO();
			
		}

		//some fields we need are only in the MPR
		if (patientVO != null && patientVO.getThePersonDT().getLocalId()!= null)
			logger.info("populating client for " + patientVO.getThePersonDT().getLocalId());
		
		PersonVO patientMprVO = getPatientMPR(patientVO, nbsSecurityObj);
		client.setLocalClientId(getClientIdToUse(patientMprVO, nbsSecurityObj));
		PostalLocatorDT patientAdr = getTheHomeAddress(patientVO);
		Timestamp birthDate = patientVO.thePersonDT.getBirthTime();
		Timestamp lastModifiedDate = patientVO.thePersonDT.getLastChgTime();
		String lastModifiedDateStr = "";
		if (lastModifiedDate != null)
			lastModifiedDateStr = new SimpleDateFormat("MM/dd/yyyy").format(lastModifiedDate);
		client.setLastModifiedDate(lastModifiedDateStr);
		String birthYear = PartnerServicesConstantsOld.PS_DEFAULT_YEAR; //1800
		if (birthDate != null)
			 birthYear= new SimpleDateFormat("yyyy").format(birthDate);
		client.setBirthYear(birthYear);
		String ethnicityCd = patientVO.getThePersonDT().getEthnicGroupInd();
		if (ethnicityCd != null) {
			if (PartnerServicesConstantsOld.RACE_CATAGORY_MAP.containsKey(ethnicityCd))  {
				client.setEthnicity(PartnerServicesConstantsOld.RACE_CATAGORY_MAP.get(ethnicityCd));
			} else {
				client.setEthnicity(PartnerServicesConstantsOld.RACE_CATAGORY_MAP.get("U")); //unknown
			}
		} else client.setEthnicity(PartnerServicesConstantsOld.RACE_CATAGORY_MAP.get("U")); //unknown
			
		//Races is mandatory
		TypeRaces races = client.addNewRaces();
		getTheRaces(patientVO, races); //populate the race
		//Get the Patient state
		if (patientAdr != null) {
			if (patientAdr.getStateCd() != null && PartnerServicesConstantsOld.STATE_MAP.containsKey(patientAdr.getStateCd())) {
						client.setStateOfResidence(PartnerServicesConstantsOld.STATE_MAP.get(patientAdr.getStateCd()));
			} else {
				//per Jit, use NBS_STATE_CD for default if missing
				if (PartnerServicesConstantsOld.STATE_MAP.containsKey(defaultStateCd))
					client.setStateOfResidence(PartnerServicesConstantsOld.STATE_MAP.get(defaultStateCd)); 
			}
		} else client.setStateOfResidence(PartnerServicesConstantsOld.STATE_MAP.get(defaultStateCd));
		
		String currentSexCd = patientVO.thePersonDT.getCurrSexCd();
		//some fields we need are only in the MPR
		String birthSexCd = patientMprVO.thePersonDT.getBirthGenderCd();
		if (birthSexCd != null) {
			if (PartnerServicesConstantsOld.BIRTH_GENDER_MAP.containsKey(birthSexCd)) {
				client.setBirthGenderValueCode(PartnerServicesConstantsOld.BIRTH_GENDER_MAP.get(birthSexCd));
			}
		} else if (currentSexCd != null && (currentSexCd.equals("M") || currentSexCd.equals("F"))){
			client.setBirthGenderValueCode(PartnerServicesConstantsOld.BIRTH_GENDER_MAP.get(currentSexCd));
		//required field so if not present, set to Unknown
		} else {
			client.setBirthGenderValueCode(PartnerServicesConstantsOld.BIRTH_GENDER_MAP.get("U"));
		}
		//set current gender 
		String prefferredGenderCd = patientVO.thePersonDT.getPreferredGenderCd();
		String unknownReasonCd = patientVO.thePersonDT.getSexUnkReasonCd();
		String additionalGender = patientVO.thePersonDT.getAdditionalGenderCd();
		
		if (prefferredGenderCd != null && PartnerServicesConstantsOld.CURRENT_SEX_MAP.containsKey(prefferredGenderCd))
			client.setCurrentGenderValueCode(PartnerServicesConstantsOld.CURRENT_SEX_MAP.get(prefferredGenderCd));
		else if (unknownReasonCd != null && PartnerServicesConstantsOld.CURRENT_SEX_MAP.containsKey(unknownReasonCd))
			client.setCurrentGenderValueCode(PartnerServicesConstantsOld.CURRENT_SEX_MAP.get(unknownReasonCd));
		else if (additionalGender != null && additionalGender.trim().isEmpty()) {
			client.setCurrentGenderValueCode(PartnerServicesConstantsOld.CURRENT_SEX_MAP.get("89"));
			client.setOtherCurrentGender(additionalGender);
		} else if (currentSexCd != null && PartnerServicesConstantsOld.CURRENT_SEX_MAP.containsKey(currentSexCd)) {
			client.setCurrentGenderValueCode(PartnerServicesConstantsOld.CURRENT_SEX_MAP.get(currentSexCd));
		} else client.setCurrentGenderValueCode(PartnerServicesConstantsOld.CURRENT_SEX_MAP.get("R")); //default to refused to ans
		if (drivingPhcProxyVO != null) {
			//Fill in the Client Risk Profile if any information present on Investigation
			String completedRiskProfile = getQuestionAnswer("NBS229", drivingPhcProxyVO.getPageVO(), NEDSSConstants.CASE);
			if (completedRiskProfile != null) {
				ClientRiskProfiles clientRiskProfiles = client.addNewClientRiskProfiles();
				TypeRiskProfile clientRiskProfile = clientRiskProfiles.addNewClientRiskProfile();
				populateClientRiskProfile(clientRiskProfile, drivingPhcProxyVO, drivingPartnerServicesDT.getInterviewSummaryColl());
			}
		}
		//Index Information Needed?
		if (partnerServicesClient.getIndexCase() != null) {
			logger.debug("in populateClient Index Information Needed");
			if (partnerServicesClient.getIndexCase().getPublicHealthCaseUid().longValue() == 
					drivingPhcProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid().longValue()) {
				populateIndexPatientInformation(client, drivingPhcProxyVO, drivingPartnerServicesDT.getInterviewSummaryColl(), siteIdHash, nbsSecurityObj);
			} else {
				PageActProxyVO indexCaseInterviewProxyVO = null;
				PartnerServicesLookupDT indexCaseLookupDT = partnerServicesClient.getIndexCase();
			    //get the driving Investigation Page Act Proxy VO
				PageActProxyVO indexCasePhcProxyVO = null;
				try {
					Long phcUid = partnerServicesClient.getIndexCase().getPublicHealthCaseUid();
					indexCasePhcProxyVO = (PageActProxyVO) pageProxy.getPageProxyVO(NEDSSConstants.CASE, phcUid, nbsSecurityObj);
					if (partnerServicesClient.getIndexCase().getDrivingCase() != null && partnerServicesClient.getIndexCase().getDrivingCase() == true)
						phcNotificationVOColl.add(indexCasePhcProxyVO.getPublicHealthCaseVO());
						
					if (indexCaseLookupDT.getInterviewUid() != null) {
						Collection interviewSummaryCollection = getInterviewSummaryListForCase(pageProxy,
							indexCasePhcProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid(),
							indexCasePhcProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getProgAreaCd(),
							nbsSecurityObj);
						if (interviewSummaryCollection != null) {
							indexCaseLookupDT.setInterviewSummaryColl(interviewSummaryCollection);
							//Add the Site if it is not already in the list
							populateSiteIfNotInList(siteIdHash, sites, interviewSummaryCollection);  
							logger.debug("Retrieved " +interviewSummaryCollection.size() + " interviews for " +indexCasePhcProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getLocalId());
						}
					}
				} catch (EJBException e) {
					logger.error("PartnerServicesHelper Investigation Exception thrown " + e);
					e.printStackTrace();
				} catch (NEDSSSystemException e) {
					logger.error("PartnerServicesHelper Investigation System Exception thrown " + e);
					e.printStackTrace();
				} catch (FinderException e) {
					logger.error("PartnerServicesHelper Investigation Finder Exception thrown " + e);
					e.printStackTrace();
				}
				if (indexCasePhcProxyVO == null)
					logger.error("PartnerServicesHelper  Error: Investigation could not be retrieved?? " + partnerServicesClient.getIndexCase().getPublicHealthCaseUid().toString() );		
				else
					populateIndexPatientInformation(client, indexCasePhcProxyVO, indexCaseLookupDT.getInterviewSummaryColl(), siteIdHash, nbsSecurityObj);
			}
		} //if Index case present - tbd - could consider adding to client Risk Profiles and Sessions.
		
		HashMap <String,PartnerServicesLookupDT> processedPartners = new HashMap <String,PartnerServicesLookupDT>();
		Iterator partnerIter = partnerServicesClient.getPartnerCases().iterator();
		while (partnerIter.hasNext()) {
			logger.debug("in populateClient Partner Information Needed");
			PartnerServicesLookupDT partnerCaseLookupDT = (PartnerServicesLookupDT) partnerIter.next();
			String thisPartner = partnerCaseLookupDT.getPersonLocalId();
			if (processedPartners.containsKey(thisPartner)) //make sure we don't have same partner twice
				continue;
			else processedPartners.put(thisPartner, partnerCaseLookupDT);
			
			if (partnerCaseLookupDT.getIsContactRcd() != null && partnerCaseLookupDT.getIsContactRcd()) {
				//not much information in a contact record that did not become a case
				if (partnerInformation == null)
					partnerInformation = client.addNewAsAPartner();
				noNamespace.TypeClient.AsAPartner.Partner partner = partnerInformation.addNewPartner();
				populatePartnerInformationForContactOnly(partner, partnerCaseLookupDT, nbsSecurityObj);
			} else if (partnerCaseLookupDT.getPublicHealthCaseUid().longValue() ==
					drivingPhcProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid().longValue()) {
				if (partnerInformation == null)
					partnerInformation = client.addNewAsAPartner();
				noNamespace.TypeClient.AsAPartner.Partner partner = partnerInformation.addNewPartner();
				populatePartnerInformation(partner, drivingPhcProxyVO, partnerCaseLookupDT.getInterviewSummaryColl(), pageProxy, partnerCaseLookupDT.getIndexCaseLocalId(), siteIdHash, nbsSecurityObj);
			} else {
			    //Get the partner Investigation Page Act Proxy VO
				PageActProxyVO partnerCasePhcProxyVO = null;
				Collection<Object> interviewSummaryCollection = new ArrayList<Object>();
				try {
					Long phcUid = partnerCaseLookupDT.getPublicHealthCaseUid();
					partnerCasePhcProxyVO = (PageActProxyVO) pageProxy.getPageProxyVO(NEDSSConstants.CASE, phcUid, nbsSecurityObj);
					if (partnerCaseLookupDT.getDrivingCase() != null && partnerCaseLookupDT.getDrivingCase() == true)
						phcNotificationVOColl.add(partnerCasePhcProxyVO.getPublicHealthCaseVO());
					interviewSummaryCollection = getInterviewSummaryListForCase(pageProxy,
							partnerCasePhcProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid(),
							partnerCasePhcProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getProgAreaCd(),
							nbsSecurityObj);
					if (interviewSummaryCollection != null) {
						partnerCaseLookupDT.setInterviewSummaryColl(interviewSummaryCollection);
						populateSiteIfNotInList(siteIdHash, sites, interviewSummaryCollection);  
						logger.debug("Retrieved " +interviewSummaryCollection.size() + " interviews for " +partnerCasePhcProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getLocalId());
					}
				} catch (EJBException e) {
					logger.error("PartnerServicesHelper Investigation Exception thrown " + e);
					e.printStackTrace();
				} catch (NEDSSSystemException e) {
					logger.error("PartnerServicesHelper Investigation System Exception thrown " + e);
					e.printStackTrace();
				} catch (FinderException e) {
					logger.error("PartnerServicesHelper Investigation Finder Exception thrown " + e);
					e.printStackTrace();
				}
				if (partnerCasePhcProxyVO == null)
					logger.error("PartnerServicesHelper  Error: Investigation could not be retrieved?? " + partnerServicesClient.getIndexCase().getPublicHealthCaseUid().toString() );
				else {
					if (partnerInformation == null)
						partnerInformation = client.addNewAsAPartner();
					noNamespace.TypeClient.AsAPartner.Partner partner = partnerInformation.addNewPartner();
					populatePartnerInformation(partner, partnerCasePhcProxyVO, partnerCaseLookupDT.getInterviewSummaryColl(), pageProxy, partnerCaseLookupDT.getIndexCaseLocalId(), siteIdHash, nbsSecurityObj);
				}	
			}
		}
		logger.debug("PartnerServices done processing client " + patientVO.getThePersonDT().getLocalId());
		return;
	}


	/**
	 * populateClientRiskProfile
	 * @param clientRiskProfile
	 * @param phcProxyVO
	 * @param interviewSummaryDTColl
	 * @throws Exception
	 */
	private static void populateClientRiskProfile(
			TypeRiskProfile clientRiskProfile, 
			PageActProxyVO phcProxyVO,
			Collection<Object> interviewSummaryDTColl) throws Exception {
		logger.debug("in populateClientRiskProfile()");
    	/*********Client Risk Profile**********/
		String completedRiskProfile = getQuestionAnswer("NBS229", phcProxyVO.getPageVO(), NEDSSConstants.CASE);
		if (completedRiskProfile == null)
			completedRiskProfile = "66"; //not asked
		//per Traci - CDC no longer accepts Blank in NoClientRiskFactors. Can't be empty
		if (completedRiskProfile != null && PartnerServicesConstantsOld.NO_CLIENT_RISK_FACTORS.containsKey(completedRiskProfile)) {
			clientRiskProfile.setNoClientRiskFactors(PartnerServicesConstantsOld.NO_CLIENT_RISK_FACTORS.get(completedRiskProfile));
		}
		
		String injDrugUse = getQuestionAnswer("STD114", phcProxyVO.getPageVO(), NEDSSConstants.CASE);
		if (injDrugUse == null || !PartnerServicesConstantsOld.INJECTION_DRUG_USE_MAP.containsKey(injDrugUse))
			injDrugUse = "UNK";
		if (completedRiskProfile != null && completedRiskProfile.equals("1") && injDrugUse.equals("blank"))
			injDrugUse = "UNK"; //If no client risk factors is 1 (Risks collected) injection drug use should not be blank.
		if (injDrugUse != null && PartnerServicesConstantsOld.INJECTION_DRUG_USE_MAP.containsKey(injDrugUse)) 
			clientRiskProfile.setInjectionDrugUse(PartnerServicesConstantsOld.INJECTION_DRUG_USE_MAP.get(injDrugUse));
		
			
		String shareInjDrugUse = getQuestionAnswer("NBS232", phcProxyVO.getPageVO(), NEDSSConstants.CASE);
		if (shareInjDrugUse == null)
			shareInjDrugUse = "UNK";
		if (shareInjDrugUse != null && PartnerServicesConstantsOld.SHARED_INJECTION_DRUG_USE_MAP.containsKey(shareInjDrugUse)) 
			clientRiskProfile.setShareDrugInjectionEquipment(PartnerServicesConstantsOld.SHARED_INJECTION_DRUG_USE_MAP.get(shareInjDrugUse));
		if (interviewSummaryDTColl != null && !interviewSummaryDTColl.isEmpty()) {
			Iterator interviewSummaryIterator = interviewSummaryDTColl.iterator();
			while (interviewSummaryIterator.hasNext()) {
				InterviewSummaryDT thisInterview = (InterviewSummaryDT) interviewSummaryIterator.next();
				if (thisInterview.getInterviewTypeCd() != null && thisInterview.getInterviewTypeCd().equals("INITIAL") && thisInterview.getInterviewDate() != null) {
					Timestamp interviewDate = thisInterview.getInterviewDate();
					if (interviewDate != null) {
						String interviewDateStr = new SimpleDateFormat("MM/dd/yyyy").format(interviewDate);
						clientRiskProfile.setDateCollectedForRiskProfile(interviewDateStr);
					}
				}
			} //hasNext
		} else {//has no Interview collection - default to dummy date if set to Interviewed
			String patientInterviewStatus = phcProxyVO.getPublicHealthCaseVO().getTheCaseManagementDT().getPatIntvStatusCd();
			if (patientInterviewStatus != null && patientInterviewStatus.equals("I")) //interviewed but no interviews
				clientRiskProfile.setDateCollectedForRiskProfile(PartnerServicesConstantsOld.PS_DEFAULT_DATE); // set to dummy date 01/01/1900
		}
		/**********VaginalOrAnalSexInLast12Mnths**********/
		noNamespace.TypeRiskProfile.VaginalOrAnalSexInLast12Mnths vaginalOrAnalSexInLast12Months = clientRiskProfile.addNewVaginalOrAnalSexInLast12Mnths();
		
		String sexWithMaleLast12 =  getQuestionAnswer("STD107", phcProxyVO.getPageVO(), NEDSSConstants.CASE);
		if (sexWithMaleLast12 == null)
			sexWithMaleLast12 = "UNK";
		/* If No Client Risk Factors is either 66 or 77, sex with male should not be 0. (blank or 99 are allowed) */
		if (completedRiskProfile != null && (completedRiskProfile.equals("66") || completedRiskProfile.equals("77")) && (sexWithMaleLast12.equals("N") || sexWithMaleLast12.equals("O") || sexWithMaleLast12.equals("R") || sexWithMaleLast12.equals("U") || sexWithMaleLast12.equals("D")))
			sexWithMaleLast12 = "UNK";;
		if (sexWithMaleLast12 != null && PartnerServicesConstantsOld.WithMaleLast12.containsKey(sexWithMaleLast12)) 
			vaginalOrAnalSexInLast12Months.setWithMale(PartnerServicesConstantsOld.WithMaleLast12.get(sexWithMaleLast12));
		
		String sexWithFemaleLast12 =  getQuestionAnswer("STD108", phcProxyVO.getPageVO(), NEDSSConstants.CASE);
		if (sexWithFemaleLast12 == null)
			sexWithFemaleLast12 = "UNK";
		/* If No Client Risk Factors is either 66 or 77, sex with female should not be 0. (blank or 99 are allowed) */
		if (completedRiskProfile != null && (completedRiskProfile.equals("66") || completedRiskProfile.equals("77")) && (sexWithFemaleLast12.equals("N") || sexWithFemaleLast12.equals("O") || sexWithFemaleLast12.equals("R") || sexWithFemaleLast12.equals("U") || sexWithFemaleLast12.equals("D")))
			sexWithFemaleLast12 = "UNK";;
		if (sexWithFemaleLast12 != null && PartnerServicesConstantsOld.WithFemaleLast12.containsKey(sexWithFemaleLast12)) 
			vaginalOrAnalSexInLast12Months.setWithFemale(PartnerServicesConstantsOld.WithFemaleLast12.get(sexWithFemaleLast12));
		
		String sexWithTransLast12 =  getQuestionAnswer("NBS230", phcProxyVO.getPageVO(), NEDSSConstants.CASE);
		if (sexWithTransLast12  == null)
			sexWithTransLast12 = "UNK";
		if (sexWithMaleLast12 != null && PartnerServicesConstantsOld.WithTransLast12.containsKey(sexWithTransLast12)) 
			vaginalOrAnalSexInLast12Months.setWithTransgender(PartnerServicesConstantsOld.WithTransLast12.get(sexWithTransLast12 ));
		
		String sexNoCondomLast12 =  getQuestionAnswer("NBS231", phcProxyVO.getPageVO(), NEDSSConstants.CASE);
		if (sexNoCondomLast12 == null)
			sexNoCondomLast12 = "UNK";
		if (sexWithMaleLast12 != null && PartnerServicesConstantsOld.NoCondom.containsKey(sexNoCondomLast12)) 
			vaginalOrAnalSexInLast12Months.setVaginalOrAnalSexWithoutCondomPS(PartnerServicesConstantsOld.NoCondom.get(sexNoCondomLast12));
		
		String sexWithIDULast12 =  getQuestionAnswer("STD110", phcProxyVO.getPageVO(), NEDSSConstants.CASE);
		if (sexWithIDULast12 == null)
			sexWithIDULast12 = "UNK";
		if (sexWithIDULast12 != null && PartnerServicesConstantsOld.WithIDULast12.containsKey(sexWithIDULast12)) 
			vaginalOrAnalSexInLast12Months.setVaginalOrAnalSexWithIDUPS(PartnerServicesConstantsOld.WithIDULast12.get(sexWithIDULast12));
		
		//Other Additional Risks
		try {
			String hadSexWithAnonymousPartner = getQuestionAnswer("STD109", phcProxyVO.getPageVO(), NEDSSConstants.CASE);
			String hadSexWithKnownIDU = getQuestionAnswer("STD110", phcProxyVO.getPageVO(), NEDSSConstants.CASE);
			String hadSexWhileIntoxicatedHigh = getQuestionAnswer("STD111", phcProxyVO.getPageVO(), NEDSSConstants.CASE);
			String exchangedMoneyForSex = getQuestionAnswer("STD112", phcProxyVO.getPageVO(), NEDSSConstants.CASE);
			//see if we need to populate Client Risk Profile.otherRiskFactors
			if ((hadSexWithAnonymousPartner != null && !hadSexWithAnonymousPartner.isEmpty() && PartnerServicesConstantsOld.OUY_MAP.containsKey(hadSexWithAnonymousPartner)) ||
				(hadSexWithKnownIDU != null && !hadSexWithKnownIDU.isEmpty() && PartnerServicesConstantsOld.OUY_MAP.containsKey(hadSexWithKnownIDU)) ||
				(hadSexWhileIntoxicatedHigh != null && !hadSexWhileIntoxicatedHigh.isEmpty() && PartnerServicesConstantsOld.OUY_MAP.containsKey(hadSexWhileIntoxicatedHigh)) ||
				(exchangedMoneyForSex != null && !exchangedMoneyForSex.isEmpty() && PartnerServicesConstantsOld.OUY_MAP.containsKey(exchangedMoneyForSex))) {
				
				TypeRiskProfile.OtherRiskFactors otherRisks = clientRiskProfile.addNewOtherRiskFactors();
				//TypeOtherRisk otherRisk = otherRisks.addNewOtherRiskFactor();

				if ((hadSexWithAnonymousPartner != null && !hadSexWithAnonymousPartner.isEmpty()))
					if (PartnerServicesConstantsOld.OUY_MAP.containsKey(hadSexWithAnonymousPartner)) {
						otherRisks.addOtherRiskFactor(PartnerServicesConstantsOld.OTHER_RISK_MAP.get("8")); //with stranger
					}
				if ((hadSexWithKnownIDU != null && !hadSexWithKnownIDU.isEmpty())) {
					if (PartnerServicesConstantsOld.OUY_MAP.containsKey(hadSexWithKnownIDU)) {
					 otherRisks.addOtherRiskFactor(PartnerServicesConstantsOld.OTHER_RISK_MAP.get("15")); //Unprotected vaginal/anal sex with a person who is an IDU
					}
				}
				if ((hadSexWhileIntoxicatedHigh != null && !hadSexWhileIntoxicatedHigh.isEmpty())) {
					if (PartnerServicesConstantsOld.OUY_MAP.containsKey(hadSexWhileIntoxicatedHigh)) {
					 otherRisks.addOtherRiskFactor(PartnerServicesConstantsOld.OTHER_RISK_MAP.get("2")); //intoxicated
					}
				}
				if ((exchangedMoneyForSex != null && !exchangedMoneyForSex.isEmpty())) {
					if (PartnerServicesConstantsOld.OUY_MAP.containsKey(exchangedMoneyForSex)) {
					 otherRisks.addOtherRiskFactor(PartnerServicesConstantsOld.OTHER_RISK_MAP.get("1"));
					}//for money
				}
			}
		} catch (Exception ex) {
			logger.error("Error in Other Risks " + ex);
		}
		logger.debug("leaving populateClientRiskProfile()");
	}

	/**
	 * See if there is a named patient investigation which is also in our investigation list
	 * @param contactSummaryCollection
	 * @param 
	 * @return
	 */
	private static boolean isIndexPatient (Collection<Object> contactSummaryCollection) {
		logger.debug("in isIndexPatient()");
		Iterator itr = contactSummaryCollection.iterator();
		while (itr.hasNext()) {
			CTContactSummaryDT contactSummaryDT = (CTContactSummaryDT) itr
					.next();
			if (contactSummaryDT.isContactNamedByPatient())
				if (contactSummaryDT.getContactEntityPhcUid() != null &&
				       contactSummaryDT.getContactReferralBasisCd() != null &&
				       contactSummaryDT.getContactReferralBasisCd().startsWith("P"))
					return true;
		}
		return false;
	}
	/**
	 * Is this case a partner to an Index case?
	 * @param contactSummaryCollection
	 * @return
	 */
	private static boolean isPartnerCase(Collection<Object> contactSummaryCollection) {
		logger.debug("in isPartnerCase()");
		//is this a partner or an Index case? 
		//If Partner we may want to include the index case in the collection just to be sure it is there
		Iterator itr = contactSummaryCollection.iterator();
		while (itr.hasNext()) {
			CTContactSummaryDT contactSummaryDT = (CTContactSummaryDT) itr
					.next();
			if (contactSummaryDT.isPatientNamedByContact() && 
					contactSummaryDT.getSourceDispositionCd() != null &&
					(contactSummaryDT.getSourceDispositionCd().equalsIgnoreCase(PartnerServicesConstantsOld.PREV_POS) ||
							contactSummaryDT.getSourceDispositionCd().equalsIgnoreCase(PartnerServicesConstantsOld.PREV_NEG_NEW_POS) ||
							contactSummaryDT.getSourceDispositionCd().equalsIgnoreCase(PartnerServicesConstantsOld.NO_PREV_NEW_POS)) &&
							contactSummaryDT.getContactReferralBasisCd() != null &&
							contactSummaryDT.getContactReferralBasisCd().startsWith("P"))
				return true;
		}
		return false;
	}
	/**
	 * populate indexPatientInformation elements
	 * @param client
	 * @param phcProxyVO
	 * @param interviewSummaryDTColl
	 * @param siteIdHash 
	 * @param nbsSecurityObj
	 */
    private static void populateIndexPatientInformation(
    		TypeClient client,
			PageActProxyVO phcProxyVO, 
			Collection<Object> interviewSummaryDTColl, 
			HashMap<String, String> siteIdHash,
			NBSSecurityObj nbsSecurityObj) {
    	logger.debug("in populateIndexPatientInformation()");
    	try {
		String localId = phcProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getLocalId();
		String caseNumber = findCaseNumberForPHC(phcProxyVO);
		if (localId == null || caseNumber == null)
			return; //unexpected err
		
		AsAnIndex indexPatientInformation = client.addNewAsAnIndex();
		indexPatientInformation.setPartnerServiceCaseNumber(caseNumber);
		
		
		//collectedDateForClient is required
		if (interviewSummaryDTColl != null && !interviewSummaryDTColl.isEmpty()) {
			Iterator interviewSummaryIterator = interviewSummaryDTColl.iterator();
			while (interviewSummaryIterator.hasNext()) {
				InterviewSummaryDT thisInterview = (InterviewSummaryDT) interviewSummaryIterator.next();
				if (thisInterview.getInterviewTypeCd() != null && thisInterview.getInterviewTypeCd().equals("INITIAL") && thisInterview.getInterviewDate() != null) {
					Timestamp interviewDate = thisInterview.getInterviewDate();
					if (interviewDate != null) {
						String interviewDateStr = new SimpleDateFormat("MM/dd/yyyy").format(interviewDate);
						indexPatientInformation.setCollectedDateForClient(interviewDateStr);
					}
				}
			} //hasNext 
		} else if (phcProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getLastChgTime() != null) {
			Timestamp partnerDateChanged = phcProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getLastChgTime();
			String changedDateStr = new SimpleDateFormat("MM/dd/yyyy").format(partnerDateChanged);
			indexPatientInformation.setCollectedDateForClient(changedDateStr);
		} else if (phcProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getActivityFromTime() != null) {
			Timestamp invStartDate = phcProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getActivityFromTime();
			String startDateStr = new SimpleDateFormat("MM/dd/yyyy").format(invStartDate);
			indexPatientInformation.setCollectedDateForClient(startDateStr);
		}		
		
		//*** Partner Service Case ****//
		PartnerServiceCases psCases = indexPatientInformation.addNewPartnerServiceCases();
		TypeCase partnerServiceCase = psCases.addNewPartnerServiceCase();
		Timestamp initiallyAssignedDate = phcProxyVO.getPublicHealthCaseVO().getTheCaseManagementDT().getInitInterviewAssignedDate();
		if (initiallyAssignedDate != null) {
			String initiallyAssignedDateStr = new SimpleDateFormat("MM/dd/yyyy").format(initiallyAssignedDate);
			partnerServiceCase.setCaseOpenDate(initiallyAssignedDateStr);
			
			//we should be using Investigation Open Date but BA map like STD*MIS 
		} else { //per Traci Bridget - this date must be populated..
			Timestamp activityFromTime = phcProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getActivityFromTime();
			if (activityFromTime != null) {
				String activityFromTimeStr = new SimpleDateFormat("MM/dd/yyyy").format(activityFromTime);
				partnerServiceCase.setCaseOpenDate(activityFromTimeStr);
			} else {
				Timestamp lastChgTime = phcProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getLastChgTime();
				if (lastChgTime != null) {
					String lastChgTimeStr = new SimpleDateFormat("MM/dd/yyyy").format(lastChgTime);
					partnerServiceCase.setCaseOpenDate(lastChgTimeStr);
				}
			}
		}
		// Timestamp caseClosedDate = phcProxyVO.getPublicHealthCaseVO().getTheCaseManagementDT().getCaseClosedDate();
		//Note - They don't want case closed date filled in except under certain conditions. Case
		//  was sent then reopened. We're not handling this yet.
		Timestamp dateOfReport = phcProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getRptFormCmpltTime();
		if (dateOfReport != null) {
			String dateOfReportStr = new SimpleDateFormat("MM/dd/yyyy").format(dateOfReport);
			partnerServiceCase.setDateOfReport(dateOfReportStr);
		} 
		//reportedToSurveillance
		String initialFollowup = phcProxyVO.getPublicHealthCaseVO().getTheCaseManagementDT().getInitFollUp();
		if (initialFollowup != null && initialFollowup.equalsIgnoreCase("SF"))
			partnerServiceCase.setReportedToSurveillance(PartnerServicesConstantsOld.REPORTED_TO_SURVEILLANCE_MAP.get("Y"));
		else 
			partnerServiceCase.setReportedToSurveillance(PartnerServicesConstantsOld.REPORTED_TO_SURVEILLANCE_MAP.get("N"));
		
		//**********Attempt to Locate********//
		noNamespace.TypeClient.AsAnIndex.AttemptsToLocate attemptsToLocate = indexPatientInformation.addNewAttemptsToLocate();
		TypeAttemptToLocate attemptToLocate = attemptsToLocate.addNewAttemptToLocate();
		String patientInterviewStatus = phcProxyVO.getPublicHealthCaseVO().getTheCaseManagementDT().getPatIntvStatusCd();
		if (patientInterviewStatus != null && PartnerServicesConstantsOld.ATTEMPT_TO_LOCATE_OUTCOME_MAP.containsKey(patientInterviewStatus)) {
			attemptToLocate.setAttemptToLocateOutcome(PartnerServicesConstantsOld.ATTEMPT_TO_LOCATE_OUTCOME_MAP.get(patientInterviewStatus));
    	} else {
    		attemptToLocate.setAttemptToLocateOutcome(PartnerServicesConstantsOld.ATTEMPT_TO_LOCATE_OUTCOME_MAP.get("U")); //Unable
    	}
		// Defect #10946 - Release 5.4 
		String enrollmentStatus =  getQuestionAnswer("NBS257", phcProxyVO.getPageVO(), NEDSSConstants.CASE);
		if(enrollmentStatus!=null && enrollmentStatus.trim().length()>0)
			attemptToLocate.setEnrollmentStatus(PartnerServicesConstantsOld.ENROLLMENT_STATUS.get(enrollmentStatus));
		
		Enum attemptToLocateResult = attemptToLocate.getAttemptToLocateOutcome();
		if (attemptToLocateResult != null && attemptToLocateResult.toString().equals("1")) {  //1 = unable to locate
			String reasonUnableToLocate =  getQuestionAnswer("NBS276", phcProxyVO.getPageVO(), NEDSSConstants.CASE);
			if (reasonUnableToLocate != null && PartnerServicesConstantsOld.REASON_UNABLE_TO_LOCATE_MAP.containsKey(reasonUnableToLocate)) {
				attemptToLocate.setReasonForUnsuccessfulAttempt(PartnerServicesConstantsOld.REASON_UNABLE_TO_LOCATE_MAP.get(reasonUnableToLocate));
			} else {
				if (patientInterviewStatus != null && patientInterviewStatus.equalsIgnoreCase("D")) //deceased
					attemptToLocate.setReasonForUnsuccessfulAttempt(PartnerServicesConstantsOld.REASON_UNABLE_TO_LOCATE_MAP.get("01")); //Deceased
				else if (patientInterviewStatus != null && patientInterviewStatus.equalsIgnoreCase("J"))
					attemptToLocate.setReasonForUnsuccessfulAttempt(PartnerServicesConstantsOld.REASON_UNABLE_TO_LOCATE_MAP.get("02")); //OOJ
				else if (patientInterviewStatus == null || (patientInterviewStatus != null && !patientInterviewStatus.equalsIgnoreCase("I"))) 
					attemptToLocate.setReasonForUnsuccessfulAttempt(PartnerServicesConstantsOld.REASON_UNABLE_TO_LOCATE_MAP.get("89")); //Other Specify - but no place to specify in 2.1 schema
			}
		}		
	
		populateElicitPartnerInformationIfPresent(indexPatientInformation, phcProxyVO, nbsSecurityObj);
		
		populateIndexSessionInformation(indexPatientInformation, interviewSummaryDTColl, siteIdHash);
			
		} catch (Exception ex) {
			logger.warn("populateIndexPatientInformation got an exception " +ex);
			ex.printStackTrace();
		}
		//specify reason unsuccessful attempt not in system - change req
    	
    	logger.debug("leaving populateIndexPatientInformation()");
	}


	/**
     *  We have to walk through the named contacts and see if they have the sex information
     * present.
     * @param indexPatientInformation
     * @param phcProxyVO
     * @param nbsSecurityObj
     * @throws Exception
     */
	private static void populateElicitPartnerInformationIfPresent(
			AsAnIndex indexPatientInformation,
			PageActProxyVO phcProxyVO, 
			NBSSecurityObj nbsSecurityObj) throws Exception {
	
		logger.debug("in populateElicitPartnerInformationIfPresent()");
		String totalNumberOfPartners = getQuestionAnswer("STD120", phcProxyVO.getPageVO(), NEDSSConstants.CASE);
		
		int totalNumberOfNamed = 0;
		int totalNumberOfNamedMale = 0;
		int totalNumberOfNamedFemale = 0;
		int totalNumberOfNamedTransgender = 0;
		
		Collection<Object> contactColl = ( phcProxyVO)
				.getTheCTContactSummaryDTCollection();
		Iterator itr = contactColl.iterator();
		while (itr.hasNext()) {
			CTContactSummaryDT contactSummaryDT = (CTContactSummaryDT) itr
					.next();
			if (contactSummaryDT.isContactNamedByPatient())
				if (contactSummaryDT.getContactEntityPhcUid() != null &&
				       contactSummaryDT.getContactReferralBasisCd() != null &&
				       contactSummaryDT.getContactReferralBasisCd().startsWith("P")) {
					CTContactProxyVO ctProxyVO = getContactVO(contactSummaryDT.getCtContactUid(), nbsSecurityObj);
					PersonDT thePersonDT = ctProxyVO.getContactPersonVO().getThePersonDT();
					//check current sex and preferred gender 
					String preferredGenderCd = thePersonDT.getPreferredGenderCd();
					String currentSexCd = thePersonDT.getCurrSexCd();
					if (preferredGenderCd != null && !preferredGenderCd.equals("R") && !preferredGenderCd.equals("89") &&
						(preferredGenderCd.equals("FtM") || preferredGenderCd.equals("MtF")  || preferredGenderCd.equals("T")) )
							++totalNumberOfNamedTransgender;
					 else if (currentSexCd != null && currentSexCd.equals("M"))
							++totalNumberOfNamedMale;
					 else if (currentSexCd != null && currentSexCd.equals("F"))
							++totalNumberOfNamedFemale;	
						//this is an issue
					else logger.warn("PartnerServices.populateElicitPartnerInformation() unable to determine sex of named partner..");
				}
		}
		totalNumberOfNamed = totalNumberOfNamedMale + totalNumberOfNamedFemale + totalNumberOfNamedTransgender;
		if (totalNumberOfNamed != 0 ) {
			ElicitPartners elicitPartners = indexPatientInformation.addNewElicitPartners();
			TypeElicitPartner elicitPartner = elicitPartners.addNewElicitPartner();
			try {
			elicitPartner.setTimePeriodForRecallInMonths(new BigInteger(PartnerServicesConstantsOld.ELICIT_PARTNER_TIME_PERIOD)); //12
			elicitPartner.setTotalNumberOfNamedPartners(new BigInteger(String.valueOf(totalNumberOfNamed)));
			elicitPartner.setTotalNumberOfNamedMalePartners(new BigInteger(String.valueOf(totalNumberOfNamedMale)));
			elicitPartner.setTotalNumberOfNamedFemalePartners(new BigInteger(String.valueOf(totalNumberOfNamedFemale)));
			elicitPartner.setTotalNumberOfTransgenderPartners(new BigInteger(String.valueOf(totalNumberOfNamedTransgender)));
			} catch (NumberFormatException e) {
				logger.error("populateElicitPartnerInformation() is unable to convert Named Partners count to BigInt");
			}
		}
		logger.debug("leaving populateElicitPartnerInformationIfPresent()");
		return;
		
	}
	/**
	 * Add a session for each interview
	 * populateIndexSessionInformation 
	 * @param indexPatientInformation
	 * @param interviewSummaryDTColl
	 * @param siteIdHash 
	 */
    private static void populateIndexSessionInformation(
    		AsAnIndex indexPatientInformation,
			Collection<Object> interviewSummaryDTColl,
			HashMap<String, String> siteIdHash) {
    	
    	
    	if (interviewSummaryDTColl == null || interviewSummaryDTColl.isEmpty())
    		return;
    	
    	logger.debug("in populateIndexSessionInformation()");
		//there are two sessions so don't get the wrong one....
		noNamespace.TypeClient.AsAnIndex.Sessions sessions = null;
    	Iterator interviewSummaryIterator = interviewSummaryDTColl.iterator();
    	while (interviewSummaryIterator.hasNext()) {
    		InterviewSummaryDT thisInterview = (InterviewSummaryDT) interviewSummaryIterator.next();
    		Timestamp interviewDate = thisInterview.getInterviewDate();
    		String interviewDateStr = null;
    		String siteId = thisInterview.getThe900SiteId();
    		String interviewLocalId = thisInterview.getLocalId();
    		String siteType = thisInterview.getThe900SiteTypeCd();
    		String siteZip = thisInterview.getThe900SiteZipCd();
    		if (siteZip != null && siteZip.length() > 10) {  
    			logger.warn("Site Zip length issue with " + siteZip);
    			continue;
    		}
    		try {
    			if (interviewDate != null) 
    				interviewDateStr = new SimpleDateFormat("MM/dd/yyyy").format(interviewDate);
    		} catch (Exception ex) {
    			logger.error("PartnerServices populateIndexSessionInformation had interview date exception: " + ex);
    			continue;
    		}
		
    		
    		if (siteId == null || siteId.isEmpty() || siteType == null || siteType.isEmpty()) {
    			logger.debug("Setting dummy site id for interview due to missing data");
    			siteId = PartnerServicesConstantsOld.PS_DEFAULT_SITE_ID;
    		}
    		
    		if (!siteIdHash.containsKey(siteId)) {
    			logger.debug("Index SiteId not in hash map - using default dummy site id" + siteId);
    			siteId = PartnerServicesConstantsOld.PS_DEFAULT_SITE_ID;
    		}
    		
    		if (interviewDateStr != null && interviewLocalId != null && siteId != null && !siteId.isEmpty()) {
    			if (sessions == null)
    				sessions = indexPatientInformation.addNewSessions();
    			TypeSession session = sessions.addNewSession();
    			session.setInterventionId(interviewLocalId);
    			session.setSessionDate(interviewDateStr);
    			session.setSiteId(siteId);
    		}
    	} //while another interview
    	
    	logger.debug("leaving populateIndexSessionInformation()");
		return;
	}
	/**
	 * Add a session for each interview
	 * populateIndexSessionInformation 
	 * @param indexPatientInformation
	 * @param interviewSummaryDTColl
	 * @param siteIdHash 
	 */
    private static void populatePartnerSessionInformation(
    		noNamespace.TypeClient.AsAPartner.Partner partner,
			Collection<Object> interviewSummaryDTColl, 
			HashMap<String, String> siteIdHash) {
    	
    	if (interviewSummaryDTColl == null || interviewSummaryDTColl.isEmpty())
    		return;
    	logger.debug("in populatePartnerSessionInformation()");
    	noNamespace.TypeClient.AsAPartner.Partner.Sessions sessions = null;
    	Iterator interviewSummaryIterator = interviewSummaryDTColl.iterator();
    	while (interviewSummaryIterator.hasNext()) {
    		InterviewSummaryDT thisInterview = (InterviewSummaryDT) interviewSummaryIterator.next();
    		Timestamp interviewDate = thisInterview.getInterviewDate();
    		String interviewDateStr = null;
    		String siteId = thisInterview.getThe900SiteId();
    		String interviewLocalId = thisInterview.getLocalId();
    		String siteType = thisInterview.getThe900SiteTypeCd();
    		String siteZip = thisInterview.getThe900SiteZipCd();
    		if (siteZip != null && siteZip.length() > 10) {
    			logger.warn("Site Zip length issue with " + siteZip);
    			continue;
    		}
    		try {
    			if (interviewDate != null) 
    				interviewDateStr = new SimpleDateFormat("MM/dd/yyyy").format(interviewDate);
    		} catch (Exception ex) {
    			logger.error("PartnerServices populatePartnerSessionInformation had interview date exception: " + ex);
    			continue;
    		}
    		
    		if (siteId == null || siteId.isEmpty() || siteType == null || siteType.isEmpty()) {
    			logger.debug("setting dummy site id for interview due to missing data");
    			siteId = PartnerServicesConstantsOld.PS_DEFAULT_SITE_ID;
    		}
    		
    		if (!siteIdHash.containsKey(siteId)) { //Site Id is present but one of the key fields, zip or site type is missing
    			logger.info("Partner SiteId not in hash map - using default dummy site id" + siteId);
    			siteId = PartnerServicesConstantsOld.PS_DEFAULT_SITE_ID;
    		}
    		
    		if (interviewDateStr != null && interviewLocalId != null) {
    			//there are two sessions so don't get the wrong one....
    			if (sessions == null)
    				sessions = partner.addNewSessions();
    			TypeSession session = sessions.addNewSession();
    			session.setInterventionId(interviewLocalId);
    			session.setSessionDate(interviewDateStr);
    			session.setSiteId(siteId);
    		}
    	} //while another interview
    	logger.debug("leaving populatePartnerSessionInformation()");
		return;
	}    
    /**
     * Return postal locator for home address
     * @param personVO
     * @return PostalLocatorDT
     */
	private static PostalLocatorDT getTheHomeAddress(PersonVO personVO) {
		if(personVO.getTheEntityLocatorParticipationDTCollection()!=null){
			Collection<Object>  entityCOllection = personVO.getTheEntityLocatorParticipationDTCollection();
			if(entityCOllection.size()>0){
					 NedssUtils nUtil = new NedssUtils();
		        	 nUtil.sortObjectByColumn("getAsOfDate", entityCOllection, true);
			}
			Iterator<Object> it = entityCOllection.iterator();
			while(it.hasNext()){
					EntityLocatorParticipationDT edt = (EntityLocatorParticipationDT)it.next();
		            if (edt.getClassCd() != null
		    						&& edt.getClassCd().equals(NEDSSConstants.POSTAL)){
		                        PostalLocatorDT postal = edt.getThePostalLocatorDT();
		                        //this is for file summary tab
		                        if (edt.getCd() != null && edt.getUseCd() != null &&
		                        		edt.getCd().equals(NEDSSConstants.HOME) &&
		                        		edt.getUseCd().equals(NEDSSConstants.HOME) &&
		                        		edt.getStatusCd() != null &&
		                        		edt.getStatusCd().equals(NEDSSConstants.STATUS_ACTIVE) &&
		                        		edt.getRecordStatusCd() != null &&
		                        		edt.getRecordStatusCd().equals(NEDSSConstants.RECORD_STATUS_ACTIVE)) {
		                        	return(postal);
		                       } //home address  
		                } //postal
			} //has next
		}//coll not null
		return null;
	}

	/**
	 * Add races to Races element
	 * @param personVO
	 * @param races
	 * @return a count of races
	 */
		private static void getTheRaces(PersonVO personVO, TypeRaces races) {
			boolean raceFound = false;
			boolean refusedToAnswer = false;
			if(personVO.getThePersonRaceDTCollection()!=null){
				ArrayList<Object> raceColl = (ArrayList<Object> )personVO.getThePersonRaceDTCollection();
				Iterator<Object> ite = raceColl.iterator();
				while(ite.hasNext()){
					PersonRaceDT  raceDT= (PersonRaceDT)ite.next();
					String raceCd = raceDT.getRaceCd();
					if (raceCd.equals(NEDSSConstants.REFUSED_TO_ANSWER)) {
						refusedToAnswer = true;
						continue;
					} else if (raceCd.equals(NEDSSConstants.AMERICAN_INDIAN_OR_ALASKAN_NATIVE) || 
						raceCd.equals(NEDSSConstants.ASIAN) ||
						raceCd.equals(NEDSSConstants.AFRICAN_AMERICAN) ||
						raceCd.equals(NEDSSConstants.NATIVE_HAWAIIAN_OR_PACIFIC_ISLANDER) ||
						raceCd.equals(NEDSSConstants.WHITE))
							raceFound = true;
					else
							continue;
					String recordStatusCd = raceDT.getRecordStatusCd();
					if( recordStatusCd != null  && recordStatusCd.equalsIgnoreCase(NEDSSConstants.RECORD_STATUS_ACTIVE)) {
						if (PartnerServicesConstantsOld.RACE_MAP.containsKey(raceDT.getRaceCd())) {
							Race theRace = races.addNewRace();
							theRace.setRaceValueCode(PartnerServicesConstantsOld.RACE_MAP.get(raceDT.getRaceCd()));
						}
					}
				} //has next
			} //collection not null
			
			if (!raceFound) { //race is required, if no races present add an 'Unknown Race'
				Race theRace = races.addNewRace();
				if (refusedToAnswer)
					theRace.setRaceValueCode(PartnerServicesConstantsOld.RACE_MAP.get(NEDSSConstants.REFUSED_TO_ANSWER));
				else 
					theRace.setRaceValueCode(PartnerServicesConstantsOld.RACE_MAP.get(NEDSSConstants.UNKNOWN));
			}
			return;
	   }

	/**
	 * Is this a Partner?
	 * @param phcProxyVO
	 * @param caseInScopeHash
	 * @return
	 */
	private static Long isPartner (PageActProxyVO phcProxyVO, HashMap caseInScopeHash) {
				Collection<Object> contactColl = ( phcProxyVO)
						.getTheCTContactSummaryDTCollection();
				Iterator itr = contactColl.iterator();
				while (itr.hasNext()) {
					CTContactSummaryDT contactSummaryDT = (CTContactSummaryDT) itr
							.next();
					if (!contactSummaryDT.isContactNamedByPatient())
						if (contactSummaryDT.getContactReferralBasisCd() != null &&
						       contactSummaryDT.getContactReferralBasisCd().startsWith("P") &&
						       contactSummaryDT.getContactEntityPhcUid() == null &&
							   caseInScopeHash.containsKey(contactSummaryDT.getSubjectEntityPhcUid()))
						return contactSummaryDT.getSubjectEntityPhcUid(); //partnerServiceCaseNumber localId for this is the Index case
				}
				return null;
	}		

	/**
	 * populatePartnerInformation - populate elements in partnerInformation
	 * @param partner
	 * @param indexCase
	 * @param phcProxyVO
	 * @param pageProxy 
	 * @param indexCaseLocalId 
	 * @param siteIdHash 
	 * @param interviewSummaryColl
	 * @param nbsSecurityObj
	 */
	private static void populatePartnerInformation(
			noNamespace.TypeClient.AsAPartner.Partner partner, 
			PageActProxyVO phcProxyVO,
			Collection<Object> interviewSummaryDTColl, 
			PageProxy pageProxy, 
			String indexCaseLocalId, 
			HashMap<String, String> siteIdHash,
			NBSSecurityObj nbsSecurityObj) {
		
			Enum attemptToLocateResult = null;
		try {
			if (indexCaseLocalId != null)
				logger.debug("in populatePartnerInformation for index case "+indexCaseLocalId);
			if (interviewSummaryDTColl != null && !interviewSummaryDTColl.isEmpty()) {
				Iterator interviewSummaryIterator = interviewSummaryDTColl.iterator();
				while (interviewSummaryIterator.hasNext()) {
					InterviewSummaryDT thisInterview = (InterviewSummaryDT) interviewSummaryIterator.next();
					if (thisInterview.getInterviewTypeCd() != null && thisInterview.getInterviewTypeCd().equals("INITIAL") && thisInterview.getInterviewDate() != null) {
						Timestamp interviewDate = thisInterview.getInterviewDate();
						if (interviewDate != null) {
							String interviewDateStr = new SimpleDateFormat("MM/dd/yyyy").format(interviewDate);
							partner.setPartnerDateCollected(interviewDateStr);
						}
					}
				} //hasNext 
			} else if (phcProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getLastChgTime() != null) {
				Timestamp partnerDateChanged = phcProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getLastChgTime();
				String changedDateStr = new SimpleDateFormat("MM/dd/yyyy").format(partnerDateChanged);
				partner.setPartnerDateCollected(changedDateStr);
			} else if (phcProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getActivityFromTime() != null) {
				Timestamp invStartDate = phcProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getActivityFromTime();
				String startDateStr = new SimpleDateFormat("MM/dd/yyyy").format(invStartDate);
				partner.setPartnerDateCollected(startDateStr);
			}
			String theCaseNumber = getIndexCaseForPartner(phcProxyVO, pageProxy, indexCaseLocalId, nbsSecurityObj);
			partner.addPartnerServiceCaseNumber(theCaseNumber);
			//Following no longer in 2.1
			//partner.setLocalPartnerServiceId(phcProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getUid().toString());
			String referralBasisCd = phcProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getReferralBasisCd();
			if (referralBasisCd != null && PartnerServicesConstantsOld.PARTNER_TYPE_MAP.containsKey(referralBasisCd))
				partner.setPartnerType(PartnerServicesConstantsOld.PARTNER_TYPE_MAP.get(referralBasisCd));
			//**********Attempt to Locate********//
			noNamespace.TypeClient.AsAPartner.Partner.AttemptsToLocate attemptsToLocate = partner.addNewAttemptsToLocate();
			TypeAttemptToLocate attemptToLocate = attemptsToLocate.addNewAttemptToLocate();

			String patientInterviewStatus = null;
			if (phcProxyVO.getPublicHealthCaseVO().getTheCaseManagementDT() != null && phcProxyVO.getPublicHealthCaseVO().getTheCaseManagementDT().getPatIntvStatusCd() != null)
				patientInterviewStatus = phcProxyVO.getPublicHealthCaseVO().getTheCaseManagementDT().getPatIntvStatusCd();
			if (patientInterviewStatus != null && PartnerServicesConstantsOld.ATTEMPT_TO_LOCATE_OUTCOME_MAP.containsKey(patientInterviewStatus)) {
				attemptToLocate.setAttemptToLocateOutcome(PartnerServicesConstantsOld.ATTEMPT_TO_LOCATE_OUTCOME_MAP.get(patientInterviewStatus));
			} else {
				attemptToLocate.setAttemptToLocateOutcome(PartnerServicesConstantsOld.ATTEMPT_TO_LOCATE_OUTCOME_MAP.get("U")); //Unable
			}
			
			attemptToLocateResult = attemptToLocate.getAttemptToLocateOutcome();
			if (attemptToLocateResult != null && attemptToLocateResult.toString().equals("1")) {  //1 = unable to locate
				String reasonUnableToLocate = null;
				reasonUnableToLocate = getQuestionAnswer("NBS276", phcProxyVO.getPageVO(), NEDSSConstants.CASE);

				if (reasonUnableToLocate != null && PartnerServicesConstantsOld.REASON_UNABLE_TO_LOCATE_MAP.containsKey(reasonUnableToLocate)) {
					attemptToLocate.setReasonForUnsuccessfulAttempt(PartnerServicesConstantsOld.REASON_UNABLE_TO_LOCATE_MAP.get(reasonUnableToLocate));
				} else {
					if (patientInterviewStatus != null && patientInterviewStatus.equalsIgnoreCase("D")) //deceased
						attemptToLocate.setReasonForUnsuccessfulAttempt(PartnerServicesConstantsOld.REASON_UNABLE_TO_LOCATE_MAP.get("01")); //Deceased
					else if (patientInterviewStatus != null && patientInterviewStatus.equalsIgnoreCase("J"))
						attemptToLocate.setReasonForUnsuccessfulAttempt(PartnerServicesConstantsOld.REASON_UNABLE_TO_LOCATE_MAP.get("02")); //OOJ
					else attemptToLocate.setReasonForUnsuccessfulAttempt(PartnerServicesConstantsOld.REASON_UNABLE_TO_LOCATE_MAP.get("89")); //Other Specify - but no place to specify in 2.1 schema
				}
			} //if attempt to locate unsuccessful
				
		} catch (Exception e) {
			logger.error("Unexpected Exception in PopulatePartnerInformation(1) " + e.getMessage());
		}
			
		//Not in 2.1 schema
		//String notificationPlan = phcProxyVO.getPublicHealthCaseVO().getTheCaseManagementDT().getFldFollUpNotificationPlan();

		//Exposure Notification
		String partnerNotifiability = phcProxyVO.getPublicHealthCaseVO().getTheCaseManagementDT().getInitFollUpNotifiable();
		String actualNotificationMethod = phcProxyVO.getPublicHealthCaseVO().getTheCaseManagementDT().getActRefTypeCd();
		try {
			String previousHivTest = getQuestionAnswer("NBS254", phcProxyVO.getPageVO(), NEDSSConstants.CASE);
			String dateOfLastHivTest = getQuestionAnswer("NBS259", phcProxyVO.getPageVO(), NEDSSConstants.CASE);
			/* If previous HIV Test is ‘Yes’, then date of last test should be provided. Per Dougan, put 01/01/1900 */
			if (previousHivTest != null && previousHivTest.equals("Y") && dateOfLastHivTest == null)
				dateOfLastHivTest = PartnerServicesConstantsOld.PS_DEFAULT_DATE;
			String selfReportedHivTest = getQuestionAnswer("STD106", phcProxyVO.getPageVO(), NEDSSConstants.CASE);
			if ((partnerNotifiability != null && PartnerServicesConstantsOld.PARTNER_NOTIFIABILITY_MAP.containsKey(partnerNotifiability)) 
					|| (actualNotificationMethod != null && PartnerServicesConstantsOld.PARTNER_NOTIFIABILITY_MAP.containsKey(partnerNotifiability))
					|| (previousHivTest != null && PartnerServicesConstantsOld.PARTNER_PREVIOUS_HIV_TEST_MAP.containsKey(previousHivTest)) 
					|| dateOfLastHivTest != null || (selfReportedHivTest != null && PartnerServicesConstantsOld.PARTNER_SELF_REPORTED_HIV_TEST_MAP.containsKey(selfReportedHivTest))) {
				ExposureNotifications exposureNotifications = partner.addNewExposureNotifications();
				TypePartnerNotification exposureNotification = exposureNotifications.addNewExposureNotification();
				if (previousHivTest != null && PartnerServicesConstantsOld.PARTNER_PREVIOUS_HIV_TEST_MAP.containsKey(previousHivTest)) 
					exposureNotification.setPreviousHIVTest(PartnerServicesConstantsOld.PARTNER_PREVIOUS_HIV_TEST_MAP.get(previousHivTest));
				if (dateOfLastHivTest != null)
					exposureNotification.setDateOfLastHIVTest(dateOfLastHivTest);
				if (selfReportedHivTest != null && PartnerServicesConstantsOld.PARTNER_SELF_REPORTED_HIV_TEST_MAP.containsKey(selfReportedHivTest))
					exposureNotification.setSelfReportedHIVTestResult(PartnerServicesConstantsOld.PARTNER_SELF_REPORTED_HIV_TEST_MAP.get(selfReportedHivTest));
				if (partnerNotifiability != null && PartnerServicesConstantsOld.PARTNER_NOTIFIABILITY_MAP.containsKey(partnerNotifiability))
					exposureNotification.setPartnerNotifiability(PartnerServicesConstantsOld.PARTNER_NOTIFIABILITY_MAP.get(partnerNotifiability));
				else 
					exposureNotification.setPartnerNotifiability(PartnerServicesConstantsOld.PARTNER_NOTIFIABILITY_MAP.get("88")); //required field, put Other.
				if (actualNotificationMethod != null && partnerNotifiability != null &&
						partnerNotifiability.equals("6") &&  PartnerServicesConstantsOld.PARTNER_ACTUAL_NOTIFICATION_METHOD_MAP.containsKey(actualNotificationMethod))
					exposureNotification.setActualNotificationMethod(PartnerServicesConstantsOld.PARTNER_ACTUAL_NOTIFICATION_METHOD_MAP.get(actualNotificationMethod));
			}
		} catch (Exception ex) {
			logger.error("Error in populatePartnerInformation Exposure Notification section " + ex);
		}
		//referrals
		try {
	
			String referralDate = getQuestionAnswer("NBS261", phcProxyVO.getPageVO(), NEDSSConstants.CASE);
			String referredToHivTesting = getQuestionAnswer("NBS260", phcProxyVO.getPageVO(), NEDSSConstants.CASE);
			String referredToMedicalCare = getQuestionAnswer("NBS266", phcProxyVO.getPageVO(), NEDSSConstants.CASE);
			String firstMedicalCareAppointment = getQuestionAnswer("NBS267", phcProxyVO.getPageVO(), NEDSSConstants.CASE);
			String hivTestPerformed  = getQuestionAnswer("NBS262", phcProxyVO.getPageVO(), NEDSSConstants.CASE);
			String hivTestResult  = getQuestionAnswer("NBS263", phcProxyVO.getPageVO(), NEDSSConstants.CASE);
			String hivTestResultProvided = getQuestionAnswer("NBS265", phcProxyVO.getPageVO(), NEDSSConstants.CASE);
			
			/* Referral Date should be present if there was a successful attempt to locate the partner. */
			if (referralDate == null && attemptToLocateResult != null && attemptToLocateResult.toString().equals("2")) { //located but no default, per Dougan default to 01/01/1900
				referralDate = PartnerServicesConstantsOld.PS_DEFAULT_DATE;
			}
			if (referralDate != null || 
					(referredToHivTesting != null && PartnerServicesConstantsOld.REFERRED_TO_HIV_TESTING_MAP.containsKey(referredToHivTesting)) ||
					(referredToMedicalCare != null && PartnerServicesConstantsOld.REFERRED_TO_MEDICAL_CARE_MAP.containsKey(referredToMedicalCare)) ||
					(firstMedicalCareAppointment != null && PartnerServicesConstantsOld.FIRST_MEDICAL_APPOINTMENT_MAP.containsKey(firstMedicalCareAppointment)) ||
					(hivTestPerformed != null && PartnerServicesConstantsOld.HIV_TEST_PERFORMED_MAP.containsKey(hivTestPerformed)) ||
					(hivTestResult != null && PartnerServicesConstantsOld.HIV_TEST_RESULT_MAP.containsKey(hivTestResult)) ||
					(hivTestResultProvided != null && PartnerServicesConstantsOld.HIV_TEST_RESULTS_PROVIDED_MAP.containsKey(hivTestResultProvided)) ) {
				Referrals referrals = partner.addNewReferrals();
				TypeReferral referral = referrals.addNewReferral();
				if (referralDate != null) 
					referral.setReferralDate(referralDate);
				if (referredToHivTesting != null && PartnerServicesConstantsOld.REFERRED_TO_HIV_TESTING_MAP.containsKey(referredToHivTesting))
					referral.setReferredToHIVTesting(PartnerServicesConstantsOld.REFERRED_TO_HIV_TESTING_MAP.get(referredToHivTesting));
				if (referredToMedicalCare != null && PartnerServicesConstantsOld.REFERRED_TO_MEDICAL_CARE_MAP.containsKey(referredToMedicalCare))
					referral.setReferredToMedicalCare(PartnerServicesConstantsOld.REFERRED_TO_MEDICAL_CARE_MAP.get(referredToMedicalCare));
				if (firstMedicalCareAppointment != null && PartnerServicesConstantsOld.FIRST_MEDICAL_APPOINTMENT_MAP.containsKey(firstMedicalCareAppointment))
					referral.setFirstMedicalCareAppointment(PartnerServicesConstantsOld.FIRST_MEDICAL_APPOINTMENT_MAP.get(firstMedicalCareAppointment));
				if (hivTestPerformed != null && PartnerServicesConstantsOld.HIV_TEST_PERFORMED_MAP.containsKey(hivTestPerformed))
					referral.setHIVTestPerformed(PartnerServicesConstantsOld.HIV_TEST_PERFORMED_MAP.get(hivTestPerformed));
				if (hivTestResult != null && PartnerServicesConstantsOld.HIV_TEST_RESULT_MAP.containsKey(hivTestResult))
					referral.setHIVTestResult(PartnerServicesConstantsOld.HIV_TEST_RESULT_MAP.get(hivTestResult));
				if (hivTestResultProvided != null && PartnerServicesConstantsOld.HIV_TEST_RESULTS_PROVIDED_MAP.containsKey(hivTestResultProvided))
					referral.setHIVTestResultsProvided(PartnerServicesConstantsOld.HIV_TEST_RESULTS_PROVIDED_MAP.get(hivTestResultProvided));
			}
			
		} catch (Exception ex) {
			logger.error("Error in populatePartnerInformation Referral " + ex);
		}		
		
		//Sessions
		if (interviewSummaryDTColl!= null) {
			populatePartnerSessionInformation(
		    		 partner,
					interviewSummaryDTColl,
					siteIdHash);
		}
		logger.debug("leaving populatePartnerInformation");
		return;	
	}
	


	/**
	 * Retrieve list of Patients from the collection into a map
	 * @param partnerServicesColl
	 * @return hashmap <Patient Local Id, PHC Uid>
	 */
	private static HashMap<String, Long> getListOfPatientLocalIdsInTimeframe(ArrayList<Object> partnerServicesColl) {
		logger.debug("in getListOfPatientLocalIdsInTimeframe()");
		Iterator partnerServicesIter = partnerServicesColl.iterator();
		HashMap<String,Long> patientHash = new HashMap<String,Long>();
		PartnerServicesLookupDT partnerServicesDT = null;
		while (partnerServicesIter.hasNext()) {
			try {
			partnerServicesDT = (PartnerServicesLookupDT) partnerServicesIter.next();
			if (partnerServicesDT.getIsContactRcd())
				patientHash.put(partnerServicesDT.getPersonLocalId(),partnerServicesDT.getCtContactUid());
			else
				patientHash.put(partnerServicesDT.getPersonLocalId(),partnerServicesDT.getPublicHealthCaseUid());
			} catch (Exception e) {
				logger.error(" Exception in getListOfPatientLocalIdsInTimeframe()??", e.getMessage());
			}
		}
		logger.debug("patientHash.size="+patientHash.size());
		logger.debug("leaving getListOfPatientLocalIdsInTimeframe()");
		
		return patientHash;
	}
	

	/**
	 * helperSetup
	 * @param invFormCode
	 * @param ixsFormCode
	 * @throws Exception
	 */
	private static void helperSetup(String invFormCode, String ixsFormCode) throws Exception {
		logger.debug("in helperSetup");
		////Investigation
		if (invFormCode == null) {
			logger.error("PartnerServices Helper - investigation form code is null for HIV?");
			throw new Exception("\n ***************Check setting for HIV_PROGRAM_AREAS=HIV in NEDSS.properties. *************** \n");
		}
		if (QuestionsCache.dmbMap.containsKey(invFormCode))
			caseQuestionMap = (Map<Object, Object>) QuestionsCache.dmbMap
					.get(invFormCode);
		else if (!QuestionsCache.dmbMap.containsKey(invFormCode)
				&& propertyUtil.getServerRestart() != null
				&& propertyUtil.getServerRestart().equals("F"))
			caseQuestionMap = (Map<Object, Object>) QuestionsCache
					.getDMBQuestionMapAfterPublish().get(invFormCode);
		else
			caseQuestionMap = new HashMap<Object, Object>();
		if (caseQuestionMap == null)
			throw new Exception("\n *************** Question Cache for "
					+ invFormCode + " is empty!!! *************** \n");
		
		////Interview
		if (ixsFormCode  == null) {
			logger.error("PartnerServices Helper - Interview form code is null for HIV?");
			throw new Exception("\n ***************Check setting for HIV_PROGRAM_AREAS=HIV in NEDSS.properties. *************** \n");
		}
		if (QuestionsCache.dmbMap.containsKey(ixsFormCode))
			ixsQuestionMap = (Map<Object, Object>) QuestionsCache.dmbMap
					.get(ixsFormCode);
		else if (!QuestionsCache.dmbMap.containsKey(ixsFormCode)
				&& propertyUtil.getServerRestart() != null
				&& propertyUtil.getServerRestart().equals("F"))
			ixsQuestionMap = (Map<Object, Object>) QuestionsCache
					.getDMBQuestionMapAfterPublish().get(ixsFormCode);
		else
			ixsQuestionMap = new HashMap<Object, Object>();
		if (ixsQuestionMap == null)
			throw new Exception("\n *************** Question Cache for "
					+ ixsFormCode + " is empty!!! *************** \n");
		logger.debug("leaving helperSetup");		
	}
	/**
	 * 	getQuestionAnswer
	 * @param questionId
	 * @param pageVO
	 * @param busObj
	 * @return
	 * @throws Exception
	 */
	private static String getQuestionAnswer(String questionId, PamVO pageVO, String busObj) throws Exception {
		logger.debug("in getQuestionAnswer");
		Map<Object, Object> questionMap = null;
		if (busObj.equals(NEDSSConstants.INTERVIEW_BUSINESS_OBJECT_TYPE))
			questionMap = ixsQuestionMap;
		else
			questionMap = caseQuestionMap;

		NbsQuestionMetadata metaData = (NbsQuestionMetadata)questionMap.get(questionId);
		if (metaData == null)
			return null;
		Long uiComponentType = metaData.getNbsUiComponentUid();
		Long nbsQuestionUid = metaData.getNbsQuestionUid();
		NbsAnswerDT quesAns = null;
		//Case
		if (pageVO.getPamAnswerDTMap() != null && busObj.equals(NEDSSConstants.CASE))
			quesAns = (NbsAnswerDT) pageVO.getPamAnswerDTMap().get(nbsQuestionUid);
		//Interview
		else if (pageVO.getAnswerDTMap() != null && pageVO.getAnswerDTMap().size() > 0)
			quesAns = (NbsAnswerDT) pageVO.getAnswerDTMap().get(nbsQuestionUid);
		logger.debug("leaving getQuestionAnswer");
		if (quesAns != null)
			return quesAns.getAnswerTxt();
		return null;	
	}
	/**
	 * Fill in the header.
	 * @param headerInformation
	 * @param dateRange
	 * @param contactPerson
	 */
	private static void fillInHeaderInformation (
			TypeHeader headerInformation,
			String startingDateRange,
			String endingDateRange,
			String contactPerson
			) {
		logger.debug("in fillInHeaderInformation()");
		try {
		
		headerInformation.setSchemaVersionNumber(PartnerServicesConstantsOld.PS_SCHEMA_VERSION_NUMBER);
		headerInformation.setSenderAgencyId(propertyUtil.getPartnerServicesSendingAgency());
		headerInformation.setFirstDate(startingDateRange);
		headerInformation.setLastDate(endingDateRange);
		
		headerInformation.setDataType(PartnerServicesConstantsOld.HEADER_DATA_TYPE.get(PartnerServicesConstantsOld.PS_DATA_TYPE));
		headerInformation.setContactPersonInformation(contactPerson);
		headerInformation.setAgencyIDs(propertyUtil.getPartnerServicesDefaultAgency());
		String today = new SimpleDateFormat("MM/dd/yyyy").format(new Date());
		headerInformation.setDateCreated(today);
		headerInformation.setFileLastModifiedDate(today);
		headerInformation.setSpecialInstructions(NEDSSConstants.NBS + " rel " + propertyUtil.getRelNum());
		headerInformation.setDataOwnerAgencyName(propertyUtil.getNNDSendingFacilityNm());
		} catch (Exception e) {
			logger.error(" Exception in fillInHeaderInformation)()??", e.getMessage());
		}
		logger.debug("leaving fillInHeaderInformation()");
		return;
	}
	
	/**
	 * Lookup the contact.
	 * @param contactUid
	 * @param nbsSecurityObj
	 * @return
	 */
    public static CTContactProxyVO getContactVO (Long contactUid, NBSSecurityObj nbsSecurityObj)  {
    			logger.debug("Retrieving get Contact Proxy for: " + contactUid.toString() );
				PamProxy pamProxy = null;
				CTContactProxyVO ctContactProxyVO = null;
				NedssUtils nu = new NedssUtils();
				Object object = nu.lookupBean(JNDINames.PAM_PROXY_EJB);
				PamProxyHome pamProxyHome = (PamProxyHome) javax.rmi.PortableRemoteObject.narrow(object, PamProxyHome.class);
				try {
					pamProxy = pamProxyHome.create();
					} catch (RemoteException e1) {
						logger.error("PartnerServicesUtil.getContactVO RemoteException thrown"
								+ e1);
					} catch (CreateException e1) {
						logger.error("PartnerServicesUtil.getContactVO CreateException thrown"
								+ e1);
					}
				//return the contact vo for the uid
				try {
					ctContactProxyVO = (CTContactProxyVO) pamProxy.getContactProxyVO(contactUid, nbsSecurityObj);
				} catch (EJBException e) {
						logger.error("PartnerServicesHelper .getContactVO Exception thrown" + e);
						e.printStackTrace();
				} catch (NEDSSSystemException e) {
						logger.error("PartnerServicesHelper .getContactVO System Exception thrown" + e);
						e.printStackTrace();
				} catch (RemoteException e) {
					logger.error("PartnerServicesHelper .getContactVO Remote Exception thrown" + e);
					e.printStackTrace();
				} catch (NEDSSConcurrentDataException e) {
					logger.error("PartnerServicesHelper .getContactVO Concurrent Data Exception thrown" + e);
					e.printStackTrace();
				} catch (CreateException e) {
					logger.error("PartnerServicesHelper .getContactVO Create Exception thrown" + e);
					e.printStackTrace();
				} 
				logger.debug("Successfully retrieved Contact Proxy for: " + contactUid.toString() );
				logger.debug("Leaving  get Contact Proxy..");
				return ctContactProxyVO; 
	}
    
    /**
     * Get the Master Patient Record. Some fields like birth gender are only in the MPR.
     * @param revisionVO
     * @param nbsSecurityObj
     * @return PersonVO or null
     */
    private static PersonVO getPatientMPR(PersonVO revisionVO, NBSSecurityObj nbsSecurityObj) {
    	logger.debug("in getPatientMPR..");
    	NedssUtils nedssUtils = new NedssUtils();
    	Object object = nedssUtils.lookupBean(JNDINames.ActControllerEJB);
    	object = nedssUtils.lookupBean(JNDINames.EntityControllerEJB);
    	
    	PersonVO patientMPR = null;
    	if( revisionVO.getThePersonDT().getPersonParentUid() != null )
			try {
				EntityControllerHome home = (EntityControllerHome) PortableRemoteObject.narrow(object, EntityControllerHome.class); 
				EntityController entityController = home.create();
				patientMPR = entityController.getPerson(revisionVO.getThePersonDT().getPersonParentUid(), nbsSecurityObj);
			} catch (RemoteException e) {
				logger.error("getPatientMPR.getPatientMPR returned remote exception." +e.getMessage());
				e.printStackTrace();
			} catch (EJBException e) {
				logger.error("getPatientMPR.getPatientMPR returned EJB exception." +e.getMessage());
				e.printStackTrace();
			} catch (CreateException e) {
				logger.error("getPatientMPR.getPatientMPR returned Create exception." +e.getMessage());
				e.printStackTrace();
			} catch (Exception e) {
				logger.error("getPatientMPR.getPatientMPR returned exception." +e.getMessage());
				e.printStackTrace();
			}
    	if (patientMPR != null)
    		logger.debug("Retrieved MPR for revision " + revisionVO.thePersonDT.getPersonUid().toString());
    	else 
    		logger.debug("Unable to retrieve MPR for revision " + revisionVO.thePersonDT.getPersonUid().toString());
    	logger.debug("leaving getPatientMPR..");
    	return patientMPR;
    	
    }
    /**
     * Maps are produced from the all900CaseColl
     * uidToLocalIdCaseMap - class global of Uid to Local Id
     * firstCaseLocalId - class global of person LocalId to first 900 case localId - used because luther expects the first case id to be sent
     * @param all900CaseColl
     */
	public static void populateTheCaseMaps(ArrayList<Object> all900CaseColl) {
		logger.debug("in updateAllCaseMaps..");
		PartnerServicesLookupDT caseLookupDT = null;
		patientToAllCasesMap = new HashMap<String, ArrayList<PartnerServicesLookupDT>>();
		localIdToUidCaseMap = new HashMap<String,Long>();
		patientToFirstCaseLocalIdMap = new HashMap<String, String>();
		patientToFirstLegacyCaseNoMap = new HashMap<String, String>();
	
		try {
		Iterator allCaseIter = all900CaseColl.iterator();
		while (allCaseIter.hasNext()) {
				caseLookupDT = (PartnerServicesLookupDT) allCaseIter.next();
				//populate the ArrayList of cases for a patient
				if (patientToAllCasesMap.containsKey(caseLookupDT.getPersonLocalId()))
						patientToAllCasesMap.get(caseLookupDT.getPersonLocalId()).add(caseLookupDT);
				else {
					ArrayList<PartnerServicesLookupDT> lookupArrayList = new ArrayList<PartnerServicesLookupDT>();
					lookupArrayList.add(caseLookupDT);
					patientToAllCasesMap.put(caseLookupDT.getPersonLocalId(), lookupArrayList);
				}
				
				//populate Uid to Local Id case map	
				if (caseLookupDT.getPublicHealthCaseUid() != null &&
					caseLookupDT.getLocalId() != null) {
					localIdToUidCaseMap.put(caseLookupDT.getLocalId(), caseLookupDT.getPublicHealthCaseUid());
				}
				//setup the first local id case map
				if (caseLookupDT.getPersonLocalId() != null && caseLookupDT.getLocalId() != null) {
					if (!patientToFirstCaseLocalIdMap.containsKey(caseLookupDT.getPersonLocalId())) //don't add if already there
						patientToFirstCaseLocalIdMap.put(caseLookupDT.getPersonLocalId(), caseLookupDT.getLocalId());
				}
				//setup the first legacy id case map - note some are empty strings
				if (caseLookupDT.getLegacyCaseNo() != null && !caseLookupDT.getLegacyCaseNo().isEmpty() && caseLookupDT.getPersonLocalId() != null) {
					if (!patientToFirstLegacyCaseNoMap.containsKey(caseLookupDT.getPersonLocalId())) //don't add if already there
						patientToFirstLegacyCaseNoMap.put(caseLookupDT.getPersonLocalId(), caseLookupDT.getLegacyCaseNo());
				}				
		}

		} catch (Exception e) {
			logger.error("PartnerServicesHelper.updateAllCaseMaps had exception:" +e.getMessage());
			e.printStackTrace();
		}
		logger.info("PartnerServices localIdToUidCase contains " + localIdToUidCaseMap.size() + " cases");
		logger.info("PartnerServices firstCaseLocalId contains " + patientToFirstCaseLocalIdMap.size() + " cases");
		logger.info("PartnerServices firstLegacyCaseNo contains " + patientToFirstLegacyCaseNoMap.size() + " cases");
		logger.info("leaving updateAllCaseMaps..");
		return;
	}
	/**
	 * Get all interviews associated with a case, fill in 900 info.
	 * @param pageProxy
	 * @param publicHealthCaseUid
	 * @param programAreaCd
	 * @param nbsSecurityObj
	 * @return
	 */
	public static Collection getInterviewSummaryListForCase(PageProxy pageProxy, Long publicHealthCaseUid, String programAreaCd, NBSSecurityObj nbsSecurityObj) {
		logger.debug("Retrieving Interview Summary for PHC " +publicHealthCaseUid + " for Program Area " + programAreaCd);
		Collection<Object>  interviewSummaryDTCollection  = new ArrayList<Object> ();
		InterviewSummaryDAO interviewSummaryDAO = new InterviewSummaryDAO();
		interviewSummaryDTCollection = interviewSummaryDAO.getInterviewListForInvestigation(publicHealthCaseUid, programAreaCd, nbsSecurityObj);
		logger.debug("Retrieved " +interviewSummaryDTCollection.size() + " interviews");
		Iterator interviewSummaryIter = interviewSummaryDTCollection.iterator();
		//retrieve each Interview and populate the Site ID, Site Zip, Site Type info
		while (interviewSummaryIter.hasNext()) {
			InterviewSummaryDT interviewSummaryDT = (InterviewSummaryDT) interviewSummaryIter.next();
			try {
				Long interviewUid = interviewSummaryDT.getInterviewUid();
				PageActProxyVO interviewProxyVO = (PageActProxyVO) pageProxy.getPageProxyVO(NEDSSConstants.INTERVIEW_CLASS_CODE, interviewUid, nbsSecurityObj);
				String siteId = getQuestionAnswer("IXS107", interviewProxyVO.getPageVO(), NEDSSConstants.INTERVIEW_BUSINESS_OBJECT_TYPE);
				if (siteId == null || siteId.isEmpty()) //Alabama changed to dropdown
					siteId = getQuestionAnswer("AL31100", interviewProxyVO.getPageVO(), NEDSSConstants.INTERVIEW_BUSINESS_OBJECT_TYPE);
				String siteZip = getQuestionAnswer("IXS108", interviewProxyVO.getPageVO(), NEDSSConstants.INTERVIEW_BUSINESS_OBJECT_TYPE);
				String siteType = getQuestionAnswer("IXS109", interviewProxyVO.getPageVO(), NEDSSConstants.INTERVIEW_BUSINESS_OBJECT_TYPE);
				if (siteId != null) 
					interviewSummaryDT.setThe900SiteId(siteId);
				if (siteZip != null)
					interviewSummaryDT.setThe900SiteZipCd(siteZip);
				if (siteType != null)
					interviewSummaryDT.setThe900SiteTypeCd(siteType);
				
			} catch (EJBException e) {
				logger.error("PartnerServicesHelper getInterviews Exception thrown" + e);
				e.printStackTrace();
			} catch (NEDSSSystemException e) {
				logger.error("PartnerServicesHelper Interviews System Exception thrown" + e);
				e.printStackTrace();
			} catch (FinderException e) {
				logger.error("PartnerServicesHelper Interviews Finder Exception thrown" + e);
				e.printStackTrace();
			} catch (RemoteException e) {
				logger.error("PartnerServicesHelper Remote Exception getting Interview proxy thrown" + e);
				e.printStackTrace();
			} catch (CreateException e) {
				logger.error("PartnerServicesHelper Create Exception getting Interview proxy thrown" + e);
				e.printStackTrace();
			} catch (Exception e) {
				logger.error("PartnerServicesHelper Exception getting Interview answer" + e);
				e.printStackTrace();
			}	
		} //has next
	
		return interviewSummaryDTCollection;	
	}
	/**
	 * populateTheMigratedMaps - some of these maps were never used so they are commented out
	 * We need to use the same Client ID that STD*MIS used or the cases could be counted twice, etc.
	 * @param migratedCaseColl
	 */
	public static void populateTheMigratedMaps(
			ArrayList<Object> migratedCaseColl) {
		//nbsInvestigationLocalIdToStdMisCaseNo = new HashMap<String,String>(); //contains NBSCaseLocalId/STD*MIS Case No
		//patientLocalIdToFirstStdMisCaseNoMap = new  HashMap<String,String>(); //contains PatientLocalId/First STD*MIS Case No
		patientLocalIdToStdMisPatientNum = new HashMap<String,String>(); //contains PatientLocalId/STD*MIS Patient Num
		Iterator migratedCaseIter = migratedCaseColl.iterator();
		while (migratedCaseIter.hasNext()) {
			PartnerServicesMigratedCaseDT migratedCaseDT = (PartnerServicesMigratedCaseDT) migratedCaseIter.next();
			//if (migratedCaseDT.getNbsInvestigationLocalId() != null && migratedCaseDT.getLegacyCaseNo() != null) 
				//nbsInvestigationLocalIdToStdMisCaseNo.put(migratedCaseDT.getNbsInvestigationLocalId(), migratedCaseDT.getLegacyCaseNo());
			//if (migratedCaseDT.getNbsPatientLocalId() != null && migratedCaseDT.getLegacyCaseNo() != null) {
			//	if (!patientLocalIdToFirstStdMisCaseNoMap.containsKey(migratedCaseDT.getNbsPatientLocalId()))
			//		patientLocalIdToFirstStdMisCaseNoMap.put(migratedCaseDT.getNbsPatientLocalId(), migratedCaseDT.getLegacyCaseNo());
			//}
			if (migratedCaseDT.getNbsPatientLocalId() != null && migratedCaseDT.getLegacyPatientId() != null) {
				patientLocalIdToStdMisPatientNum.put(migratedCaseDT.getNbsPatientLocalId(), migratedCaseDT.getLegacyPatientId());
			}
		}
	
		return;
	}
	/**
	 * Because Luther overlays the client each time we send in a client record, we need to include any
	 * cases the client has even if outside of date range.
	 * @param pageProxy 
	 * @param siteIdHash 
	 * @param sites 
	 */
	private static ArrayList<PartnerServicesClient> populatePartnerServicesClientComprehensiveList(
			ArrayList<Object> partnerServicesColl, 
			PageProxy pageProxy, 
			Sites sites, HashMap<String, String> siteIdHash, 
			NBSSecurityObj nbsSecurityObj) {
		
		logger.debug("Beginning populatePartnerServicesClientComprehensiveList()");
		PartnerServicesLookupDT drivingCase = null;
		ArrayList<PartnerServicesClient> clientComprehensiveList = new ArrayList<PartnerServicesClient>();
		patientToClientMap = new HashMap<String, PartnerServicesClient>();
		Iterator lookupIter = partnerServicesColl.iterator();
		while (lookupIter.hasNext()) {
			PartnerServicesClient partnerServicesClient = null;
			drivingCase = (PartnerServicesLookupDT) lookupIter.next();
			if (drivingCase.getPersonLocalId() != null)
				logger.info("Building Comprehensive List: Driving case Person LocalId = " + drivingCase.getPersonLocalId());
			drivingCase.setDrivingCase(true); //should be set in select
			if (patientToClientMap.containsKey(drivingCase.getPersonLocalId()))
				partnerServicesClient = patientToClientMap.get(drivingCase.getPersonLocalId());
			else {
				partnerServicesClient = new PartnerServicesClient(drivingCase.getPersonLocalId());
				patientToClientMap.put(drivingCase.getPersonLocalId(), partnerServicesClient);
				clientComprehensiveList.add(partnerServicesClient);
			}
			logger.debug("populatePartnerServicesClientComprehensiveList(2)");
			Boolean thisCasePartner = drivingCase.getIsPartner();
			if (thisCasePartner == null && drivingCase.getContactSummaryColl() != null)
				thisCasePartner = isPartnerCase(drivingCase.getContactSummaryColl());
			if (thisCasePartner != null && thisCasePartner) {
				logger.debug("populatePartnerServicesClientComprehensiveList(3)");
				//associated partner that named this client
				drivingCase.setIsPartner(true);
				if (notAlreadyInList(partnerServicesClient.getPartnerCases(), drivingCase))
					partnerServicesClient.getPartnerCases().add(drivingCase);
				//could be also an index case
				if ( (drivingCase.getIsIndex() != null && drivingCase.getIsIndex()) || isIndexPatient(drivingCase.getContactSummaryColl())
						|| (drivingCase.getFldFollUpDispo() != null && (drivingCase.getFldFollUpDispo().equals("1") || drivingCase.getFldFollUpDispo().equals("2") || drivingCase.getFldFollUpDispo().equals("5"))
								&& (drivingCase.getReferralBasisCd() != null && drivingCase.getReferralBasisCd().startsWith("P"))))
						{
						//index case also
						drivingCase.setIsIndex(true);
						if (partnerServicesClient.getIndexCase() != null)
							logger.info("Overlaying existing index case " +partnerServicesClient.getIndexCase().getLocalId()+ "with same or newer case " + drivingCase.getLocalId());
						partnerServicesClient.setIndexCase(drivingCase); //could already be something there, but this one later in time
				}
			} else {
				logger.debug("populatePartnerServicesClientComprehensiveList(4)");
				//index case even if no contacts if HIV positive
				drivingCase.setIsIndex(true);
				if (partnerServicesClient.getIndexCase() != null)
					logger.debug("Overlaying existing index case " +partnerServicesClient.getIndexCase().getLocalId()+ "with same or newer case " + drivingCase.getLocalId());
				partnerServicesClient.setIndexCase(drivingCase);
			}
			if (drivingCase.getInterviewUid() != null) {
				try {
					Collection interviewSummaryColl = getInterviewSummaryListForCase(pageProxy, drivingCase.getPublicHealthCaseUid(), drivingCase.getProgAreaCd(), nbsSecurityObj);
					if (interviewSummaryColl != null & !interviewSummaryColl.isEmpty()) {
						drivingCase.setInterviewSummaryColl(interviewSummaryColl);
						//Add the Site if it is not already in the list
						populateSiteIfNotInList(siteIdHash, sites, interviewSummaryColl); 
					}
				} catch (Exception ex) {
					logger.error("PartnerServices: In populatePartnerServicesClientComprehensiveList error getting interviews for driving case is "+ex.getMessage());
				}
			}
			//Look and see if there are other 900 cases for this client. On our system there could be
			//an HIV infection case followed by an AIDS case some time later.
			//We need as comprehensive a client record as we can include since Luther wipes the client before adding the data..
			if (patientToAllCasesMap.containsKey(drivingCase.getPersonLocalId()) &&
					patientToAllCasesMap.get(drivingCase.getPersonLocalId()).size() > 1) {
				logger.debug("populatePartnerServicesClientComprehensiveList(5)");
				//Only in rare cases will there be another 900 case..
				ArrayList<PartnerServicesLookupDT> clientCases = patientToAllCasesMap.get(drivingCase.getPersonLocalId());
				Iterator allClientCasesIter = clientCases.iterator();
				CTContactSummaryDAO cTContactSummaryDAO = new CTContactSummaryDAO();
				while (allClientCasesIter.hasNext()) {
					logger.debug("populatePartnerServicesClientComprehensiveList(6)");
					PartnerServicesLookupDT theLookupDT = (PartnerServicesLookupDT) allClientCasesIter.next();
					if (theLookupDT.getPublicHealthCaseUid() != null)
						logger.info("Looking up client case phc uid = "+theLookupDT.getPublicHealthCaseUid().toString());
					logger.debug("populatePartnerServicesClientComprehensiveList(6a)");
					if (theLookupDT.getPublicHealthCaseUid() != null && drivingCase.getPublicHealthCaseUid() != null &&
							theLookupDT.getPublicHealthCaseUid().longValue() == drivingCase.getPublicHealthCaseUid().longValue())
						continue; //already in list
					
					Collection<Object> contactCollection = new ArrayList<Object>();
					try{
						contactCollection.addAll(cTContactSummaryDAO.getPHCContactNamedByPatientSummDTColl(theLookupDT.getPublicHealthCaseUid(), nbsSecurityObj));
						contactCollection.addAll(cTContactSummaryDAO.getPHCPatientNamedAsContactSummDTColl(theLookupDT.getPublicHealthCaseUid(), nbsSecurityObj));
					}catch(Exception ex){
						logger.error("PartnerServices.populatePartnerServicesClientComprehensiveList had exception retreiving contacts = "+ex.getMessage(), ex);
					}
					logger.debug("populatePartnerServicesClientComprehensiveList(6b)");
					theLookupDT.setContactSummaryColl(contactCollection);
					if (theLookupDT.getInterviewUid() != null) {
						try {
							Collection interviewSummaryList = getInterviewSummaryListForCase(pageProxy, theLookupDT.getPublicHealthCaseUid(),
																		theLookupDT.getProgAreaCd(), nbsSecurityObj);
							if (interviewSummaryList != null & !interviewSummaryList.isEmpty())
								theLookupDT.setInterviewSummaryColl(interviewSummaryList);
						} catch (Exception ex) {
							logger.error("PartnerServices: In populatePartnerServicesClientComprehensiveList error getting interviews for contact case is "+ex.getMessage());
						}
					}		
					logger.debug("populatePartnerServicesClientComprehensiveList(6c)");
					if (theLookupDT.getContactSummaryColl() != null && isPartnerCase(theLookupDT.getContactSummaryColl())) {
						logger.debug("populatePartnerServicesClientComprehensiveList(7)");
						theLookupDT.setIsPartner(true);
						if (notAlreadyInList(partnerServicesClient.getPartnerCases(), theLookupDT))
							partnerServicesClient.getPartnerCases().add(theLookupDT);
					} else if (partnerServicesClient.getIndexCase() == null) { //only one index, don't overlay with this
						logger.debug("populatePartnerServicesClientComprehensiveList(8)");
						theLookupDT.setIsIndex(true);
						partnerServicesClient.setIndexCase(theLookupDT);
					}
				} //all client cases iter
			} //more than one
		} //lookup Iner has next
		logger.debug("Completed populatePartnerServicesClientComprehensiveList()");
		logger.info("PartnerServices: Client Comprehensive List contains " + clientComprehensiveList.size() + " entries");
		return clientComprehensiveList;
	}
	/**
	 * See if the passed Lookup is already in the List
	 * @param partnerCases
	 * @param theLookupDT
	 * @return false if in list
	 */
	private static boolean notAlreadyInList(
			ArrayList<PartnerServicesLookupDT> partnerCases,
			PartnerServicesLookupDT theLookupDT) {
		if (partnerCases == null || partnerCases.size() == 0)
			return true;
		logger.debug("in notAlreadyInList()");
		Iterator psCaseIter = partnerCases.iterator();
		while (psCaseIter.hasNext()) {
			PartnerServicesLookupDT caseInList = (PartnerServicesLookupDT) psCaseIter.next();
			if (caseInList.getPublicHealthCaseUid() != null && theLookupDT.getPublicHealthCaseUid() != null 
					&& caseInList.getPublicHealthCaseUid().longValue() == theLookupDT.getPublicHealthCaseUid().longValue())
				return(false); //already in list
		}
		return true;
	}

	/**
	 * Find the case that was in scope for the time period
	 * @param partnerServicesClient
	 * @return
	 */
	private static PartnerServicesLookupDT findDrivingClientLookupDT(PartnerServicesClient partnerServicesClient) {
		logger.debug("in findDrivingClientLookupDT()");
		if (partnerServicesClient.getIndexCase() != null && partnerServicesClient.getIndexCase().getDrivingCase() != null)
			return partnerServicesClient.getIndexCase();
		Iterator partnerIterator = partnerServicesClient.getPartnerCases().iterator();
		while (partnerIterator.hasNext()) {
			PartnerServicesLookupDT partnerLookupDT = (PartnerServicesLookupDT) partnerIterator.next();
			if (partnerLookupDT.getDrivingCase()) {
				logger.debug("leaving findDrivingClientLookupDT()");
				return partnerLookupDT;
			}
		}
		logger.debug("leaving findDrivingClientLookupDT() --->>> Returning NULL");
		return null;
	}
	/**
	 * Use legacy id or id of first case.
	 * @param patientMprVO
	 * @param nbsSecurityObj
	 * @return
	 */
	private static String getClientIdToUse(PersonVO patientMprVO, NBSSecurityObj nbsSecurityObj) {
		logger.debug("in getClientIdToUse()");
		//see if PartnerServices Patient Number (code PSID) is on the MPP - if it is -- use that
		if (patientMprVO.getTheEntityIdDTCollection() != null && !patientMprVO.getTheEntityIdDTCollection().isEmpty()) {
			Iterator entityIdIter = patientMprVO.getTheEntityIdDTCollection().iterator();
			while (entityIdIter.hasNext()) {
				EntityIdDT idDT = (EntityIdDT) entityIdIter.next();
				if (idDT.getTypeCd() != null && idDT.getTypeCd().equals("PSID")) //Person Services Patient Number
					if (idDT.getRootExtensionTxt() != null && idDT.getRootExtensionTxt().length() > 0)
						return idDT.getRootExtensionTxt();
			}
		}
		//next see if we have migrated data for this patient and use that migrated id if present
		if (patientLocalIdToStdMisPatientNum.containsKey(patientMprVO.getThePersonDT().getLocalId())) {
			logger.debug("leaving getClientIdToUse(1)");
			return(patientLocalIdToStdMisPatientNum.get(patientMprVO.getThePersonDT().getLocalId()));
		}
		
		//use our local id
		logger.debug("leaving getClientIdToUse(2)");
		return (patientMprVO.getThePersonDT().getLocalId());
	}
	
	
	/**
	 * Retrieve the index case so that we can determine the right case number to use.
	 * @param phcProxyVO
	 * @param pageProxy
	 * @param indexCaseLocalIdParam 
	 * @param nbsSecurityObj
	 * @return
	 */
	private static String getIndexCaseForPartner(PageActProxyVO phcProxyVO, PageProxy pageProxy, String indexCaseLocalIdParam, NBSSecurityObj nbsSecurityObj) {
		logger.debug("in getIndexCaseForPartner()");
		Collection<Object> contactColl = ( phcProxyVO)
				.getTheCTContactSummaryDTCollection();
		Iterator itr = contactColl.iterator();
		String indexCaseLocalId = null;
		if (indexCaseLocalIdParam != null)
			indexCaseLocalId = indexCaseLocalIdParam;
		else {
			while (itr.hasNext()) {
				CTContactSummaryDT contactSummaryDT = (CTContactSummaryDT) itr
					.next();
				if (contactSummaryDT.isPatientNamedByContact() && 
					contactSummaryDT.getSourceDispositionCd() != null &&
					(contactSummaryDT.getSourceDispositionCd().equalsIgnoreCase(PartnerServicesConstantsOld.PREV_POS) ||
							contactSummaryDT.getSourceDispositionCd().equalsIgnoreCase(PartnerServicesConstantsOld.PREV_NEG_NEW_POS) ||
							contactSummaryDT.getSourceDispositionCd().equalsIgnoreCase(PartnerServicesConstantsOld.NO_PREV_NEW_POS)) &&
							contactSummaryDT.getContactReferralBasisCd() != null &&
							contactSummaryDT.getContactReferralBasisCd().startsWith("P")) {
					indexCaseLocalId = contactSummaryDT.getSubjectPhcLocalId();
				break;
				}
			}
		} //else not present as a parm
		logger.debug("in getIndexCaseForPartner(2)");
		PageActProxyVO indexCasePhcProxyVO = null;
		if (indexCaseLocalId != null) {
				if (localIdToUidCaseMap.containsKey(indexCaseLocalId)) {
						Long phcUid = localIdToUidCaseMap.get(indexCaseLocalId);
					    //Get the Index Case Investigation Page Act Proxy VO
						try {
							indexCasePhcProxyVO = (PageActProxyVO) pageProxy.getPageProxyVO(NEDSSConstants.CASE, phcUid, nbsSecurityObj);
						} catch (EJBException e) {
							logger.error("PartnerServicesHelper get Index Investigation Exception thrown " + e);
							e.printStackTrace();
						} catch (NEDSSSystemException e) {
							logger.error("PartnerServicesHelper get Index Investigation System Exception thrown " + e);
							e.printStackTrace();
						} catch (FinderException e) {
							logger.error("PartnerServicesHelper get Index Investigation Finder Exception thrown " + e);
							e.printStackTrace();
						}catch (CreateException e) {
							logger.error("PartnerServicesHelper get Index Investigation Create Exception thrown " + e);
							e.printStackTrace();
						}catch (RemoteException e) {
							logger.error("PartnerServicesHelper get Index Investigation Remote Exception thrown " + e);
							e.printStackTrace();
						}
					}
					if (indexCasePhcProxyVO != null) {
						logger.debug("leaving getIndexCaseForPartner(1)");
						return findCaseNumberForPHC(indexCasePhcProxyVO);
					}
		}
		logger.debug("leaving getIndexCaseForPartner(2)");
		return indexCaseLocalId; //case could have been deleted, must return something
	}
	
	/**
	 * Per christi and Jennifer - use the oldest case number
	 * @param phcProxyVO
	 * @return
	 */
    private static String findCaseNumberForPHC(PageActProxyVO phcProxyVO) {
    	logger.debug("in findCaseNumberForPHC()");
		String caseNumber = phcProxyVO.getPublicHealthCaseVO().getThePublicHealthCaseDT().getLocalId();
		String legacyCaseNumber = null;
		String patientLocalId = null;
		PersonVO patientVO = null;
		try {
			patientVO = PageLoadUtil.getPersonVO("SubjOfPHC",
					(PageProxyVO) phcProxyVO);
		} catch (NEDSSAppException e) {
			logger.error("PartnerServices: No SubjOfPHC found for investigation??");
			e.printStackTrace();
		}
		if (patientVO != null && patientVO.getThePersonDT() != null)
			patientLocalId = patientVO.getThePersonDT().getLocalId();
		
		logger.debug("PartnerServices patientToFirstCaseLocalIdMap contains " + patientToFirstCaseLocalIdMap.get(patientLocalId));//i.e. CAS10002589GA01
		//logger.debug("Partner Services nbsLocalIdToStdMisCaseNo contains " + nbsInvestigationLocalIdToStdMisCaseNo.get(caseNumber)); // i.e. 0610132824
		logger.debug("PartnerServices patientToFirstLegacyCaseNoMap contains " + patientToFirstLegacyCaseNoMap.get(patientLocalId)); //i.e. 0610145662
		
		if (phcProxyVO.getPublicHealthCaseVO().getTheActIdDTCollection() != null && 
				!phcProxyVO.getPublicHealthCaseVO().getTheActIdDTCollection().isEmpty()) {
			Iterator actIdIter = phcProxyVO.getPublicHealthCaseVO().getTheActIdDTCollection().iterator();
			while (actIdIter.hasNext()) {
				ActIdDT actIdDt = (ActIdDT) actIdIter.next();
				if ((actIdDt.getTypeCd().equalsIgnoreCase(PartnerServicesConstantsOld.LEGACY_ID_TYPE))
						&& (actIdDt.getRootExtensionTxt() != null)) {
					if (!actIdDt.getRootExtensionTxt().isEmpty())
						legacyCaseNumber = actIdDt.getRootExtensionTxt(); // i.e. 0610145662
				}	
			}
		}
		if (patientToFirstLegacyCaseNoMap != null && patientToFirstLegacyCaseNoMap.containsKey(patientLocalId)) {
			return patientToFirstLegacyCaseNoMap.get(patientLocalId);
		}
		if (patientToFirstCaseLocalIdMap != null && patientToFirstCaseLocalIdMap.containsKey(patientLocalId)) {
			return patientToFirstCaseLocalIdMap.get(patientLocalId);
		}
		if (legacyCaseNumber != null && !legacyCaseNumber.isEmpty())
			return legacyCaseNumber;
		logger.debug("leaving findCaseNumberForPHC()");
		return caseNumber;
	}
    /**
     * 
     * @param indexCaseLocalId
     * @return
     */
    private static String findCaseNumberForContactIndexCase(String indexCaseLocalId) {
    	logger.debug("in findCaseNumberForContactIndexCase()");
		if (patientToFirstLegacyCaseNoMap != null && patientToFirstLegacyCaseNoMap.containsKey(indexCaseLocalId)) {
			return patientToFirstLegacyCaseNoMap.get(indexCaseLocalId);
		}
		if (patientToFirstCaseLocalIdMap != null && patientToFirstCaseLocalIdMap.containsKey(indexCaseLocalId)) {
			return patientToFirstCaseLocalIdMap.get(indexCaseLocalId);
		}
		logger.debug("leaving findCaseNumberForContactIndexCase()");
		return indexCaseLocalId;
    }
	/**
	 * This is for a partner that is from a contact record that did not turn into a case.
	 * @param partner
	 * @param partnerCaseLookupDT
	 * @param nbsSecurityObj
	 */
	private static void populatePartnerInformationForContactOnly(
			Partner partner, PartnerServicesLookupDT partnerCaseLookupDT,
			NBSSecurityObj nbsSecurityObj) {
		logger.debug("in populatePartnerInformationForContactOnly()");
		//Index case number - will be in this file since we have the contact
		partner.addPartnerServiceCaseNumber(findCaseNumberForContactIndexCase(partnerCaseLookupDT.getIndexCaseLocalId()));
		
		//date information collected
		CTContactProxyVO ctContactProxyVO = partnerCaseLookupDT.getCtContactProxyVO();
		if (partnerCaseLookupDT.getContactInterviewDate() != null) {
			String ixDateStr = new SimpleDateFormat("MM/dd/yyyy").format(partnerCaseLookupDT.getContactInterviewDate());
			partner.setPartnerDateCollected(ixDateStr);
		} else if (ctContactProxyVO.getcTContactVO().getcTContactDT().getLastChgTime() != null) {
			Timestamp contactDateChanged = ctContactProxyVO.getcTContactVO().getcTContactDT().getLastChgTime();
			String changedDateStr = new SimpleDateFormat("MM/dd/yyyy").format(contactDateChanged);
			partner.setPartnerDateCollected(changedDateStr);
		}
		
		//referral basis
		String referralBasisCd = partnerCaseLookupDT.getReferralBasisCd();
		if (referralBasisCd != null && PartnerServicesConstantsOld.PARTNER_TYPE_MAP.containsKey(referralBasisCd))
			partner.setPartnerType(PartnerServicesConstantsOld.PARTNER_TYPE_MAP.get(referralBasisCd));
		
		//Don't really have Attempt to Locate information in Contact Only
		noNamespace.TypeClient.AsAPartner.Partner.AttemptsToLocate attemptsToLocate = partner.addNewAttemptsToLocate();
		TypeAttemptToLocate attemptToLocate = attemptsToLocate.addNewAttemptToLocate();
		attemptToLocate.setAttemptToLocateOutcome(PartnerServicesConstantsOld.ATTEMPT_TO_LOCATE_OUTCOME_MAP.get("U")); //Unable
		attemptToLocate.setReasonForUnsuccessfulAttempt(PartnerServicesConstantsOld.REASON_UNABLE_TO_LOCATE_MAP.get("89")); //Other Specify - but no place to specify in 2.1 schema
		
		logger.debug("leaving populatePartnerInformationForContactOnly()");
		return;
	}
	/**
	 * Per JW add a default site and use it for interviews with no site type and/or zip
	 * @param sites
	 * @param siteIdHash 
	 */
	private static void addDefaultSite(Sites sites, HashMap<String, String> siteIdHash) {
		//Dummy site added for Interviews that don't specify Site Type or Zip
			logger.debug("PartnerServices: Creating default site.");
			noNamespace.XpemsPSDataDocument.XpemsPSData.Sites.Site theSite = sites.addNewSite();
			theSite.setSiteId(PartnerServicesConstantsOld.PS_DEFAULT_SITE_ID);
			theSite.setSiteTypeValueCode(PartnerServicesConstantsOld.SITE_TYPES_MAP.get(PartnerServicesConstantsOld.PS_DEFAULT_SITE_TYPE));
			theSite.setZip(PartnerServicesConstantsOld.PS_DEFAULT_SITE_ZIP);
			siteIdHash.put(PartnerServicesConstantsOld.PS_DEFAULT_SITE_ID, PartnerServicesConstantsOld.PS_DEFAULT_SITE_TYPE);
			return;
	}
}
