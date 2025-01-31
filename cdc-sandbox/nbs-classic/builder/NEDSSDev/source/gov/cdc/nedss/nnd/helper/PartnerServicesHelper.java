package gov.cdc.nedss.nnd.helper;

import gov.cdc.nedss.act.notification.dt.NotificationDT;
import gov.cdc.nedss.act.notification.ejb.dao.NotificationDAOImpl;
import gov.cdc.nedss.act.notification.vo.NotificationVO;
import gov.cdc.nedss.act.publichealthcase.dt.PublicHealthCaseDT;
import gov.cdc.nedss.act.publichealthcase.vo.PublicHealthCaseVO;
import gov.cdc.nedss.association.dt.ActRelationshipDT;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.nnd.psf.TypeAttemptToLocate;
import gov.cdc.nedss.nnd.psf.TypeAttemptToLocateOutcome;
import gov.cdc.nedss.nnd.psf.TypeCareStatus;
import gov.cdc.nedss.nnd.psf.TypeCase;
import gov.cdc.nedss.nnd.psf.TypeCurrentHIVMedicalCareStatus;
import gov.cdc.nedss.nnd.psf.TypeElicitPartner;
import gov.cdc.nedss.nnd.psf.TypeEnrollmentStatus;
import gov.cdc.nedss.nnd.psf.TypeHIVStage;
import gov.cdc.nedss.nnd.psf.TypeHIVTestPerformed;
import gov.cdc.nedss.nnd.psf.TypeHIVTestResult;
import gov.cdc.nedss.nnd.psf.TypeHeader;
import gov.cdc.nedss.nnd.psf.TypePSClientHIVStatus;
import gov.cdc.nedss.nnd.psf.TypePartnerNotification;
import gov.cdc.nedss.nnd.psf.TypeProgramAnnouncement;
import gov.cdc.nedss.nnd.psf.TypeProvisionOfResult;
import gov.cdc.nedss.nnd.psf.TypeRaces;
import gov.cdc.nedss.nnd.psf.TypeRaces.Race;
import gov.cdc.nedss.nnd.psf.TypeReasonForUnsuccessfulAttempt;
import gov.cdc.nedss.nnd.psf.TypeReferredToPrEP;
import gov.cdc.nedss.nnd.psf.TypeRiskProfile;
import gov.cdc.nedss.nnd.psf.TypeRiskProfile.VaginalOrAnalSexInLast12Mnths;
import gov.cdc.nedss.nnd.psf.TypeSession;
import gov.cdc.nedss.nnd.psf.TypeSyphilisTestResult;
import gov.cdc.nedss.nnd.psf.TypeTestingAndReferral;
import gov.cdc.nedss.nnd.psf.TypeYesNo;
import gov.cdc.nedss.nnd.psf.XpemsPSDataDocument;
import gov.cdc.nedss.nnd.psf.XpemsPSDataDocument.XpemsPSData;
import gov.cdc.nedss.nnd.psf.XpemsPSDataDocument.XpemsPSData.Clients;
import gov.cdc.nedss.nnd.psf.XpemsPSDataDocument.XpemsPSData.Clients.Client;
import gov.cdc.nedss.nnd.psf.XpemsPSDataDocument.XpemsPSData.Clients.Client.AsAPartner;
import gov.cdc.nedss.nnd.psf.XpemsPSDataDocument.XpemsPSData.Clients.Client.AsAPartner.Partner;
import gov.cdc.nedss.nnd.psf.XpemsPSDataDocument.XpemsPSData.Clients.Client.AsAnIndex;
import gov.cdc.nedss.nnd.psf.XpemsPSDataDocument.XpemsPSData.Clients.Client.ClientRiskProfiles;
import gov.cdc.nedss.nnd.psf.XpemsPSDataDocument.XpemsPSData.Clients.Client.AsAnIndex.AttemptsToLocate;
import gov.cdc.nedss.nnd.psf.XpemsPSDataDocument.XpemsPSData.Clients.Client.AsAnIndex.ElicitPartners;
import gov.cdc.nedss.nnd.psf.XpemsPSDataDocument.XpemsPSData.Clients.Client.AsAnIndex.PartnerServiceCases;
import gov.cdc.nedss.nnd.psf.XpemsPSDataDocument.XpemsPSData.Clients.Client.AsAnIndex.Sessions;
import gov.cdc.nedss.nnd.psf.XpemsPSDataDocument.XpemsPSData.Sites;
import gov.cdc.nedss.nnd.psf.XpemsPSDataDocument.XpemsPSData.Sites.Site;
import gov.cdc.nedss.nnd.util.PartnerServicesConstants;
import gov.cdc.nedss.nnd.util.PartnerServicesUtil;
import gov.cdc.nedss.proxy.ejb.notificationproxyejb.bean.NotificationProxy;
import gov.cdc.nedss.proxy.ejb.notificationproxyejb.bean.NotificationProxyHome;
import gov.cdc.nedss.proxy.ejb.notificationproxyejb.vo.NotificationProxyVO;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dt.PartnerServicesLookupDT;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.QuestionsCache;

import java.math.BigInteger;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.rmi.PortableRemoteObject;



/**
 * PartnerServicesHelper - Helps populate the Partner Services schema elements.
 *  See PSData_v3.xsd. For documentation on elements and valid values 
 *  see NHM&E Data Variables and Values pdf document.
 * @author Fatima Lopez Calzado
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: GDIT</p>
 * August 8th, 2018
 * @version 1.0
 */

public class PartnerServicesHelper {
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
			ArrayList<Object> sessionColl,
			HashMap<String, ArrayList<Object>> indexMap,
			HashMap<String, ArrayList<Object>> indexSessionMap,
			HashMap<String, ArrayList<Object>> partnerMap,
			HashMap<String, ArrayList<Object>> partnerSessionMap,
			ArrayList<Object> clientColl,
			HashMap<String, ArrayList<Object>> riskMap,
			HashMap<String, Object> sessionMap,			
			XpemsPSDataDocument xpemsdoc,
			String startingDateRange,
			String endingDateRange,
			String contactPerson,
			String invFormCd, 
			String ixsFormCd,
			String defaultStateCd, 
			NBSSecurityObj nbsSecurityObj
		) throws Exception, NEDSSAppException, RemoteException {
		
		
		String actualDate = "";
		 
		 DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
		 Date date = new Date();
		 actualDate=dateFormat.format(date);
			
		 String successFailure ="Success";
		 String errorMessage = "";
		 
		 ArrayList<String> indexInformation = new ArrayList<String>();
		 ArrayList<String> clientInformation = new ArrayList<String>();
		 ArrayList<String> sessionInformation = new ArrayList<String>();
		 ArrayList<String> partnerInformation = new ArrayList<String>();
		 
		 
		 
		try{
		
		 
			
		logger.debug("in buildPartnerServicesDocument();");
		helperSetup(invFormCd, ixsFormCd);
		XpemsPSData xpemsPSData = xpemsdoc.addNewXpemsPSData();
		
		TypeHeader headerInformation = xpemsPSData.addNewHeaderInformation();
		fillInHeaderInformation(headerInformation, startingDateRange, endingDateRange, contactPerson);
		Sites sites = xpemsPSData.addNewSites();
		Clients clients = xpemsPSData.addNewClients();
		
		fillSitesInformation(sites,sessionColl);
		ArrayList<String> allPublicHealthCases = new ArrayList<String>();
		fillClientsAndRiskInformation(clients,clientColl,riskMap,indexMap, partnerMap, indexSessionMap, partnerSessionMap, allPublicHealthCases,
		indexInformation, clientInformation, sessionInformation, partnerInformation);
		
		//Create Notification for each Case
        String comments="Partner Services File Notification Created";
    	NedssUtils nu = new NedssUtils();
        logger.info("Creating notifications for " + allPublicHealthCases.size() + " HIV/AIDS investigations");
    	//Iterator phcCollIter = partnerServicesPHCCollection.iterator();
   		Object objectnotif = nu.lookupBean(JNDINames.NOTIFICATION_PROXY_EJB);
        NotificationProxyHome home = (NotificationProxyHome) PortableRemoteObject.narrow(objectnotif, NotificationProxyHome.class);
        NotificationProxy notificationproxy = home.create();
        
        //get the collection of PublicHealthCaseDT based on the local ids from PSF_Index and PSF_Partner tables
        String listLocalId = getListLocalId (allPublicHealthCases);
        
        Collection<Object> phcDTColl = getPublicHealthCaseDTCollection(listLocalId);
        //Get the public health case local id which has a row in the act_relationship table for PSF notification
        ArrayList<String> phcArrayFromAct = getPhcLocalIdInActRelationShip(listLocalId);
        
        HashMap<String, String>  phcNotificationUidHashMap = createHashMapPhcNotification(phcArrayFromAct);
    	Iterator phcCollIter=phcDTColl.iterator();

    	while (phcCollIter.hasNext()) {
    		Object publicHealthCaseVO1=phcCollIter.next();
    		PublicHealthCaseDT publicHealthCaseDT=(PublicHealthCaseDT) publicHealthCaseVO1;
    		createPartnerServicesNotification(publicHealthCaseDT,phcNotificationUidHashMap, notificationproxy,  comments, nbsSecurityObj, invFormCd);  
    	}
    	 logger.debug("Completed creating notifications");
    	 
    	 
		}catch (Exception e){
			
			errorMessage = "Exception: "+e.getMessage();
			successFailure ="Failure";
			
			
		}
    	 
    	 
    	
    	 String mode ="";
		
		 Date finishDate = new Date();
		 String endDate=dateFormat.format(finishDate);
    	 
    	 PartnerServicesUtil partnerServicesUtil = new PartnerServicesUtil();
    	 //Writing into activity_log table
		 partnerServicesUtil.writeXMLLog(actualDate, successFailure, errorMessage, mode, actualDate, endDate);
		
		 //Writing into activity_log_detail table
		 partnerServicesUtil.writeXMLLogDetail(indexInformation);
		 partnerServicesUtil.writeXMLLogDetail(clientInformation);
		 partnerServicesUtil.writeXMLLogDetail(sessionInformation);
		 partnerServicesUtil.writeXMLLogDetail(partnerInformation);
		 
		 
		 
			
		 
		 
			
    	 
    	return;
	}

	/**
	 * createHashMapPhcNotification: it cretes a hashmap with key the public health case local id and value the notification uid linked to that investigation uid for PSF
	 * @param phcArrayFromAct
	 * @return
	 */
	public static HashMap<String, String> createHashMapPhcNotification(ArrayList<String> phcArrayFromAct){
		
		HashMap<String, String> phcNotifHashMap = new HashMap<String, String>();
		
		for (int i=0; i<phcArrayFromAct.size(); i++){
			
			String phcNotification = phcArrayFromAct.get(i);
			String phc = phcNotification.substring(0,phcNotification.indexOf("-"));
			String notification = phcNotification.substring(phcNotification.indexOf("-")+1);

			phcNotifHashMap.put(phc,  notification);
		}
		
		return phcNotifHashMap;
	}
	
	/**
	 * getPublicHealthCaseDTCollection: returns a collection of PublicHealthCaseDT which local_id is in the list
	 * received as a parameter called listLocalId.
	 * @param listLocalId
	 * @return
	 */
	public static Collection<Object> getPublicHealthCaseDTCollection(String listLocalId){
		
		Collection<Object> phcLocal=null;
		
		PartnerServicesUtil partnerServicesUtil = new PartnerServicesUtil();
		phcLocal=partnerServicesUtil.getCollectionOfPublicHealthCaseDTFromPHCLocalIdList(listLocalId);
		
		return phcLocal;
	}


	/**
	 * getPhcLocalIdInActRelationShip: returns an array of public health case local ids where the act_relationship.type_cd = 'Notification' and notification.cd ='PSF_NOTF',
	 * so we'll know if there's an existing PSF investigation already created.
	 * @param listLocalId
	 * @return
	 */
	public static ArrayList<String> getPhcLocalIdInActRelationShip(String listLocalId){
		
		ArrayList<Object> phcs=null;
		ArrayList<String> phcLocalIds=new ArrayList<String>();
		PartnerServicesUtil partnerServicesUtil = new PartnerServicesUtil();
		phcs=partnerServicesUtil.getCollectionOfActRelationShipFromPHCLocalId(listLocalId);
		
		
		for(int i=0; i<phcs.size(); i++){
			phcLocalIds.add(((PublicHealthCaseDT)phcs.get(i)).getLocalId());
		}
		
		return phcLocalIds;
	}
	
	/**
	 * getListLocalId: returns the list of localIds separated by , and between single quotes in order to use it in the where clause
	 * of other sql queries
	 * @param allPublicHealthCases
	 * @return
	 */
	public static String getListLocalId (ArrayList<String> allPublicHealthCases){
		
		String listLocalIds = "";
		
		for(int i=0; i<allPublicHealthCases.size(); i++){
			listLocalIds += "'"+allPublicHealthCases.get(i)+"',";
		}
		
		if(listLocalIds!="")
			listLocalIds = listLocalIds.substring(0,listLocalIds.length()-1);//Removed the , at the end
		
		return listLocalIds;	
		
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
	private static void createPartnerServicesNotification(PublicHealthCaseDT phcDT, HashMap<String,String> phcNotUidHashMap, NotificationProxy notificationProxy, String comments, NBSSecurityObj nbsSecurityObj, String invFormCd) throws javax.ejb.EJBException, Exception
    {

	String phcLocalId =  phcDT.getLocalId();
	//PublicHealthCaseDT phcDT = publicHealthCaseVO.getThePublicHealthCaseDT();
	logger.info("PartnerServices creating notification for " +phcLocalId);
	Long phcUID = phcDT.getPublicHealthCaseUid();
	//String programeAreaCode = phcDT.getProgAreaCd();
	//String jurisdictionCode = phcDT.getJurisdictionCd();
	String sharedInd = phcDT.getSharedInd();
	String notifUid = null;
	//	Collection<Object> arColl = publicHealthCaseVO.getTheActRelationshipDTCollection();
	NotificationProxyVO notProxyVO = null;
	NotificationDT notDT=null;
	
	if (phcNotUidHashMap.get(phcLocalId)!=null)//already exists a notification for that case
	{
		//get the notificationUid
		 notifUid = phcNotUidHashMap.get(phcLocalId);
		 //get the notificationDT
		 NotificationDAOImpl notif = new NotificationDAOImpl();
		 
		 notDT = notif.selectItem(Long.parseLong(notifUid));
		 notDT.setItNew(false);
		 notDT.setItDirty(true);
		logger.debug("Partner Services Notification already exists for " + phcLocalId);
		//comments = "Partner Services File Notification Re-Created";
		
	}

	logger.debug("Creating Notification for " + phcLocalId);
    PublicHealthCaseVO phcVO = new PublicHealthCaseVO();
    phcVO.setThePublicHealthCaseDT(phcDT);
    phcVO.setItNew(false);
    phcVO.setItDirty(false);

    // Create the Notification object

    if(notDT==null){
    notDT = new NotificationDT();
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
    }
    else{

        NotificationVO notVO = new NotificationVO();
        notVO.setTheNotificationDT(notDT);
        notVO.setItNew(false);
        notVO.setItDirty(true);
        

        notProxyVO = new NotificationProxyVO();
        notProxyVO.setItNew(false);
        notProxyVO.setItDirty(true);
        
        notProxyVO.setThePublicHealthCaseVO(phcVO);
        notProxyVO.setTheNotificationVO(notVO);

    }
    
    
    notificationProxy.setNotificationProxy(notProxyVO, nbsSecurityObj);
    logger.debug("Completed creating Notification for " + phcLocalId);
	return;
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
		headerInformation.setDataType(PartnerServicesConstants.HEADER_DATA_TYPE.get(PartnerServicesConstants.PS_DATA_TYPE));
		headerInformation.setSchemaVersionNumber(PartnerServicesConstants.PS_SCHEMA_VERSION_NUMBER);
		headerInformation.setSenderAgencyId(propertyUtil.getPartnerServicesSendingAgency());
		headerInformation.setFirstDate(startingDateRange);
		headerInformation.setLastDate(endingDateRange);
		headerInformation.setContactPersonInformation(contactPerson);
		headerInformation.setAgencyIDs(propertyUtil.getPartnerServicesDefaultAgency());
		String today = new SimpleDateFormat("MM/dd/yyyy").format(new Date());
		headerInformation.setDateCreated(today);
		headerInformation.setFileLastModifiedDate(today);
		String releaseNumber = propertyUtil.getRelNum();
		releaseNumber=releaseNumber.substring("Release".length()+1, releaseNumber.length());
		headerInformation.setSpecialInstructions(NEDSSConstants.PSF + " NBS " + releaseNumber);
		headerInformation.setDataOwnerAgencyName(propertyUtil.getNNDSendingFacilityNm());
		} catch (Exception e) {
			logger.error(" Exception in fillInHeaderInformation)()??", e.getMessage());
		}
		logger.debug("leaving fillInHeaderInformation()");
		return;
	}
	
	/**
	 * fillSitesInformation: fill the Java Objects with the Sites information
	 * @param sites
	 * @param sessionColl
	 */
	private static void fillSitesInformation (
			Sites sites,
			ArrayList<Object> sessionColl
			) {
		logger.debug("in fillSitesInformation()");
		ArrayList<String> listSites = new ArrayList<String>();
		try {
		
			if(sessionColl!=null)
				for(int i=0; i<sessionColl.size();i++){
					PSFSessionDT session = (PSFSessionDT)sessionColl.get(i);
					String SiteIdType = session.getSiteId()+session.getSiteTypeValueCode();
					
					if(!listSites.contains(SiteIdType)){
						Site site = sites.addNewSite();
						site.setSiteId(session.getSiteId());
						site.setSiteTypeValueCode(gov.cdc.nedss.nnd.psf.TypeSiteType.Enum.forString(session.getSiteTypeValueCode()));	
						listSites.add(SiteIdType);
					
					}
				}
		} catch (Exception e) {
			logger.error(" Exception in fillSitesInformation():", e.getMessage());
		}
		logger.debug("leaving fillSitesInformation()");
		return;
	}
	
	/**
	 * formatDateMMDDYYYY: formats the date from timestamp (from the DB) to MM/DD/YYYY required format by the PSF
	 * @param dateDB
	 * @return
	 */
	private static String formatDateMMDDYYYY(Timestamp dateDB){
		
		String date="";
		
		if(dateDB!=null)
			date = new SimpleDateFormat("MM/dd/yyyy").format(dateDB);
		
		return date;
	}
	
	
	
	/**
	 * fillClientsInformation: fill the Java Objects with the Clients information
	 * @param sites
	 * @param sessionColl
	 */
	private static void fillClientsAndRiskInformation (
			Clients clients,
			ArrayList<Object> clientColl,
			HashMap<String, ArrayList<Object>> riskMap,
			HashMap<String, ArrayList<Object>> indexMap,
			HashMap<String, ArrayList<Object>> partnerMap,
			HashMap<String, ArrayList<Object>> indexSessionMap,
			HashMap<String, ArrayList<Object>> partnerSessionMap, ArrayList<String> allPublicHealthCases,
			ArrayList<String> indexInformation,
			ArrayList<String> clientInformation,
			ArrayList<String> sessionInformation,
			ArrayList<String> partnerInformation) {
		logger.debug("in fillClientsInformation()");
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
		
		
		//Index detail log:
		boolean firstIndex = true;
		
		String successFailureIndex = "Success";
		String errorMessageIndex ="";
		String startDateIndex = "";
		String endDateIndex = "";
		 
		Date dateIndex = new Date();
		
		
		int indexCount = 0;
		 
		//Client detail log:
		
		
		String successFailureClient = "Success";
		String errorMessageClient ="";
		String startDateClient = "";
		String endDateClient = "";
		 
		
		Date dateClient = new Date();
		startDateClient=dateFormat.format(dateClient);
		int clientCount = 0;
		
		
		
		//Session detail log:
		
		boolean firstSession = true;
		
		String successFailureSession = "Success";
		String errorMessageSession ="";
		String startDateSession = "";
		String endDateSession = "";
		 
		
		
		int sessionCount = 0;
		ArrayList<String> sessionDeduplicated = new ArrayList<String>();
		
		
		
		//Partner detail log:
		
		boolean firstPartner = true;
		
		String successFailurePartner = "Success";
		String errorMessagePartner ="";
		String startDatePartner = "";
		String endDatePartner = "";
		 
		
		
		int partnerCount = 0;
		ArrayList<String> partnerDeduplicated = new ArrayList<String>();
		
		
		
		
		
		try {
			try{
			if(clientColl!=null)
				for(int i=0; i<clientColl.size();i++){
					Client client = clients.addNewClient();
					clientCount++;
					PSFClientDT clientDT = (PSFClientDT)clientColl.get(i);
					client.setBirthYear(clientDT.getBirthYear()+"");
					client.setLocalClientId(clientDT.getLocalClientId());
					client.setLastModifiedDate(formatDateMMDDYYYY(clientDT.getLastModifiedDate()));//lastChgTime, personLastChangeTime or investiation last change time??
					client.setEthnicity(gov.cdc.nedss.nnd.psf.TypeEthnicity.Enum.forString(clientDT.getEthnicity()));
					client.setEHarsStateNumber(clientDT.getEHarsStateNumber());
					client.setEHarsCityCountyNumber("");

					TypeRaces races = client.addNewRaces();
					
					Race race1 = (Race)races.addNewRace();
					race1.setRaceValueCode(gov.cdc.nedss.nnd.psf.TypeRaces.Race.RaceValueCode.Enum.forString(clientDT.getRaceValueCode1()));
					
					if(clientDT.getRaceValueCode2()!=null){
						Race race2 = (Race)races.addNewRace();
						race2.setRaceValueCode(gov.cdc.nedss.nnd.psf.TypeRaces.Race.RaceValueCode.Enum.forString(clientDT.getRaceValueCode2()));
					}
					
					if(clientDT.getRaceValueCode3()!=null){
						Race race3 = (Race)races.addNewRace();
						race3.setRaceValueCode(gov.cdc.nedss.nnd.psf.TypeRaces.Race.RaceValueCode.Enum.forString(clientDT.getRaceValueCode3()));
					}
					
					if(clientDT.getRaceValueCode4()!=null){
						Race race4 = (Race)races.addNewRace();
						race4.setRaceValueCode(gov.cdc.nedss.nnd.psf.TypeRaces.Race.RaceValueCode.Enum.forString(clientDT.getRaceValueCode4()));
					}
					
					if(clientDT.getRaceValueCode5()!=null){
						Race race5 = (Race)races.addNewRace();
						race5.setRaceValueCode(gov.cdc.nedss.nnd.psf.TypeRaces.Race.RaceValueCode.Enum.forString(clientDT.getRaceValueCode5()));
					}
					
					
					client.setBirthGenderValueCode(gov.cdc.nedss.nnd.psf.TypeBirthGender.Enum.forString(clientDT.getBirthGenderValueCode()+""));
					client.setCurrentGenderValueCode(gov.cdc.nedss.nnd.psf.TypeCurrentGender.Enum.forString(clientDT.getCurrentGenderValueCode()+""));
				
					//Fill risk information get ClientLocalID:
					
					String localClientId = clientDT.getLocalClientId();
					ArrayList<Object> riskDTList = (ArrayList<Object>)riskMap.get(localClientId);
				
					if(riskDTList!=null){
						ClientRiskProfiles risks = client.addNewClientRiskProfiles();
						for(int j=0; j<riskDTList.size(); j++){
							PSFRiskDT riskDT = (PSFRiskDT)riskDTList.get(j);
							TypeRiskProfile riskProfile = risks.addNewClientRiskProfile();
							VaginalOrAnalSexInLast12Mnths vaginal = riskProfile.addNewVaginalOrAnalSexInLast12Mnths();
							vaginal.setWithMale(gov.cdc.nedss.nnd.psf.TypeRiskResponse.Enum.forString(riskDT.getWithMale()+""));
							vaginal.setWithFemale(gov.cdc.nedss.nnd.psf.TypeRiskResponse.Enum.forString(riskDT.getWithFemale()+""));
							vaginal.setWithTransgender(gov.cdc.nedss.nnd.psf.TypeRiskResponse.Enum.forString(riskDT.getWithTransgender()+""));
							vaginal.setVaginalOrAnalSexWithoutCondomPS(gov.cdc.nedss.nnd.psf.TypeRiskResponse.Enum.forString(riskDT.getVaginalOrAnalSexWithoutCondomPS()+""));
							riskProfile.setInjectionDrugUse(gov.cdc.nedss.nnd.psf.TypeRiskResponse.Enum.forString(riskDT.getInjectionDrugUse()+""));
							riskProfile.setDateCollectedForRiskProfile(formatDateMMDDYYYY(riskDT.getDateCollectedForRiskProfile()));
						}
					}
					
					
					//Fill asAnIndex information get ClientLocalID:
					
					 if(firstIndex){
						
						Date date = new Date();
						startDateIndex=dateFormat.format(date);
						firstIndex=false;	
					 }
					try{
						ArrayList<Object> indexDTList = (ArrayList<Object>)indexMap.get(localClientId);
						
					
						if(indexDTList!=null){
							//get the newest investigation for that patient, in order to use that from data that does not repeat.
							
							PSFIndexDT indexDTLatest=null;
							Timestamp maxTime=null;
							for(int j=0; j<indexDTList.size(); j++){
								PSFIndexDT indexDT = (PSFIndexDT)indexDTList.get(j);
								Timestamp time = indexDT.getInvAddtime();
								if(maxTime==null || maxTime.before(time)){
									maxTime=time;
									indexDTLatest=indexDT;
								}
							}
							
							AsAnIndex asAnIndex = client.addNewAsAnIndex();
							String caseNumberPS = indexDTLatest.getCaseNumberPS();
							String caseNumberEarliestPS = indexDTLatest.getCaseNumberEarliestPS();
							asAnIndex.setAgencyId(indexDTLatest.getAgencyId());
							asAnIndex.setProgAnnouncementProgStrategy(TypeProgramAnnouncement.Enum.forString(""));
							asAnIndex.setPartnerServiceCaseNumber(caseNumberEarliestPS);
							asAnIndex.setIndexDateDemographicsCollected(formatDateMMDDYYYY(indexDTLatest.getIndexDateDemographicsCollected()));
							asAnIndex.setClientHIVStatus(TypePSClientHIVStatus.Enum.forString(indexDTLatest.getClientHIVStatus()));
							asAnIndex.setHivStage(TypeHIVStage.Enum.forString(""));
							asAnIndex.setFormId("");
							
							boolean firstPartnerServiceCase = true;
							boolean firstAttempToLocate = true;
							boolean firstElicitPartner = true;
							boolean firstSessions = true;
							
							
							PartnerServiceCases partnerServiceCases = null;
							AttemptsToLocate attemps = null;
							ElicitPartners elicits = null;
							Sessions sessions = null;
							for(int j=0; j<indexDTList.size(); j++){
								
								
								PSFIndexDT indexDT = (PSFIndexDT)indexDTList.get(j);
								String currentCaseNumberPS = indexDT.getCaseNumberPS();
								indexCount++;
								
								
								if(!allPublicHealthCases.contains(caseNumberPS))
									allPublicHealthCases.add(caseNumberPS);
								
								
								
								//Only include 1 partnerServiceCases and inside the partnerServiceCase sections
								if(firstPartnerServiceCase){
								 partnerServiceCases = asAnIndex.addNewPartnerServiceCases();
								 firstPartnerServiceCase=false;
								}
								
								TypeCase psCase = partnerServiceCases.addNewPartnerServiceCase();
								psCase.setCaseOpenDate(formatDateMMDDYYYY(indexDT.getCaseOpenDate()));
								psCase.setCaseCloseDate(formatDateMMDDYYYY(indexDT.getCaseCloseDate()));
								psCase.setCareStatusAtCaseClose(TypeCareStatus.Enum.forString(indexDT.getCareStatusAtCaseClose()));
								
								if(firstAttempToLocate){
									attemps = asAnIndex.addNewAttemptsToLocate();
									firstAttempToLocate=false;
									}
								
								
								TypeAttemptToLocate attempt = attemps.addNewAttemptToLocate();
								attempt.setAttemptToLocateOutcome(TypeAttemptToLocateOutcome.Enum.forString(indexDT.getAttemptToLocateOutcome()));
								attempt.setReasonForUnsuccessfulAttempt(TypeReasonForUnsuccessfulAttempt.Enum.forString(indexDT.getReasonForUnsuccessfulAttempt()));
								attempt.setEnrollmentStatus(TypeEnrollmentStatus.Enum.forString(indexDT.getEnrollmentStatus()));
								
								
								if(firstElicitPartner){
									elicits = asAnIndex.addNewElicitPartners();
									firstElicitPartner=false;
									}
								
								
								TypeElicitPartner elicit = elicits.addNewElicitPartner();
								if(indexDT.getTotalNumberofNamedPartners()!=null)
									elicit.setTotalNumberOfNamedPartners(new BigInteger(indexDT.getTotalNumberofNamedPartners()));
								else
									elicit.setTotalNumberOfNamedPartners(new BigInteger("0"));
								
								if(indexDT.getTotalNumberOfPartnersClaimed()!=null)
									elicit.setTotalNumberOfPartnersClaimed(new BigInteger(indexDT.getTotalNumberOfPartnersClaimed()));
								else
									elicit.setTotalNumberOfPartnersClaimed(new BigInteger("0"));
								
								
								//sessions
								try{
									
									if(firstSession){
										
										Date dateSession = new Date();
										startDateSession=dateFormat.format(dateSession);
										firstSession=false;
										
									}
								String keys = localClientId+"_"+currentCaseNumberPS;
								ArrayList<Object> asAnIndexSessions = indexSessionMap.get(keys);
								
							
								if(asAnIndexSessions!=null && asAnIndexSessions.size()>0){
									
									if(firstSessions){
										sessions = asAnIndex.addNewSessions();
										firstSessions=false;
										}
								}
								
								for(int k=0; asAnIndexSessions!=null && k<asAnIndexSessions.size(); k++){
									
									PSFSessionDT sessionDT = (PSFSessionDT) asAnIndexSessions.get(k);
									
									TypeSession session = sessions.addNewSession();
									sessionCount++;
									session.setSiteId(sessionDT.getSiteId());
									session.setSessionDate(formatDateMMDDYYYY(sessionDT.getSessionDate()));
									session.setCareStatusAtInterview(TypeCareStatus.Enum.forString(sessionDT.getCareStatusAtInterview()));
								
									if(!sessionDeduplicated.contains(sessionDT.getIrLocalId()))
										sessionDeduplicated.add(sessionDT.getIrLocalId());
								}
								
								}catch (Exception eSession){
									
									successFailureSession="Failure";
									errorMessageSession = "Exception:"+eSession.getMessage();
									
									
									
								}
								
								Date dateSession = new Date();
								endDateSession=dateFormat.format(dateSession);
								
									
							}
						}
						
					}catch(Exception eIndex){
						
						successFailureIndex="Failure";
						errorMessageIndex = "Exception:"+eIndex.getMessage();
					}
					
					dateIndex = new Date();
					endDateIndex=dateFormat.format(dateIndex);
					 

					
			
					
					//Fill partner information get ClientLocalID:
					ArrayList<Object> partnerDTList = (ArrayList<Object>)partnerMap.get(localClientId);
			
					try{
						
					if(partnerDTList!=null){
						//Deduplicate based on partner and caseId. Jira ND-13984
						partnerDTList = deduplicatePartners(partnerDTList);
						AsAPartner partners = null;
						
						if(partnerDTList.size()>0)
							partners = client.addNewAsAPartner();
						if(firstPartner){
							Date datePartner = new Date();
							startDatePartner=dateFormat.format(datePartner);
							firstPartner=false;
						}
						
						
					
						
						partnerDTList=removePartnersMultipleHIV(partnerDTList);
						
						for(int j=0; j<partnerDTList.size(); j++){
							
							
							PSFPartnerDT partnerDT = (PSFPartnerDT)partnerDTList.get(j);
							
							
								String caseNumberPS = partnerDT.getCaseNumberPS();
								String caseNumberEarliestPS = partnerDT.getCaseNumberEarliestPS();
								String invLocalId = partnerDT.getInvLocalId();
								Partner partner = partners.addNewPartner();
								partnerCount++;
								
								if(!partnerDeduplicated.contains(partnerDT.getInvLocalId()))
									partnerDeduplicated.add(partnerDT.getInvLocalId());
								
								partner.setAgencyId(partnerDT.getAgencyId());
								partner.addPartnerServiceCaseNumber(caseNumberEarliestPS);
								partner.setPartnerDateDemographicsCollected(formatDateMMDDYYYY(partnerDT.getPartnerDateDemographicsCollected()));
								partner.setPartnerType(gov.cdc.nedss.nnd.psf.TypePartnerType.Enum.forString(partnerDT.getPartnerType()));
								
								//atemp
								TypeAttemptToLocate attemp = partner.addNewAttemptsToLocate().addNewAttemptToLocate();
								attemp.setAttemptToLocateOutcome(gov.cdc.nedss.nnd.psf.TypeAttemptToLocateOutcome.Enum.forString(partnerDT.getAttemptToLocateOutcome()));
								attemp.setReasonForUnsuccessfulAttempt(gov.cdc.nedss.nnd.psf.TypeReasonForUnsuccessfulAttempt.Enum.forString(partnerDT.getReasonForUnsuccessfulAttempt()));
								attemp.setEnrollmentStatus(gov.cdc.nedss.nnd.psf.TypeEnrollmentStatus.Enum.forString(partnerDT.getEnrollmentStatus()));
								
								//exposure
								TypePartnerNotification exposure = partner.addNewExposureNotifications().addNewExposureNotification();
								exposure.setPartnerNotifiability(gov.cdc.nedss.nnd.psf.TypeNotifiability.Enum.forString(partnerDT.getPartnerNotifiability()));
								exposure.setActualNotificationMethod(gov.cdc.nedss.nnd.psf.TypeNotificationMethod.Enum.forString(partnerDT.getActualNotificationMethod()));
								exposure.setPreviousHivTestValueCode(gov.cdc.nedss.nnd.psf.TypePreviousHIVTest.Enum.forString(partnerDT.getPreviousHivTestValueCode()));
								exposure.setPreviousHIVTestResult(gov.cdc.nedss.nnd.psf.TypePreviousHIVTestResult.Enum.forString(partnerDT.getPreviousHIVTestResult()));
								
								//as a partner sessions
								
								if(caseNumberPS==null || caseNumberPS.equalsIgnoreCase(""))
									caseNumberPS = invLocalId;
								
								if(!allPublicHealthCases.contains(caseNumberPS))
									allPublicHealthCases.add(caseNumberPS);
								
								try{
									
									if(firstSession){
										
										Date dateSession = new Date();
										startDateSession=dateFormat.format(dateSession);
										firstSession=false;
										
									}
									
								String keys = localClientId+"_"+caseNumberPS;
								ArrayList<Object> asAPartnerSessions = partnerSessionMap.get(keys);
								
								gov.cdc.nedss.nnd.psf.XpemsPSDataDocument.XpemsPSData.Clients.Client.AsAPartner.Partner.Sessions sessions = null;
								if(asAPartnerSessions!=null && asAPartnerSessions.size()>0)
									sessions = partner.addNewSessions();
								
								for(int k=0; asAPartnerSessions!=null && k<asAPartnerSessions.size(); k++){
									
									PSFSessionDT sessionDT = (PSFSessionDT) asAPartnerSessions.get(k);
									//sessions = partner.addNewSessions();
									TypeSession session = sessions.addNewSession();
									sessionCount++;
									session.setSiteId(sessionDT.getSiteId());
									session.setSessionDate(formatDateMMDDYYYY(sessionDT.getSessionDate()));
									session.setCareStatusAtInterview(TypeCareStatus.Enum.forString(sessionDT.getCareStatusAtInterview()));
									if(!sessionDeduplicated.contains(sessionDT.getIrLocalId()))
										sessionDeduplicated.add(sessionDT.getIrLocalId());
								}
								
								}catch (Exception eSession){
									
									successFailureSession="Failure";
									errorMessageSession = "Exception:"+eSession.getMessage();
									
									
									
								}
								
								Date dateSession = new Date();
								endDateSession=dateFormat.format(dateSession);
								
								//as a partner OOJ sessions
	/*
								keys = localClientId+"_"+invLocalId;
								asAPartnerSessions = partnerSessionMap.get(keys);
								for(int k=0; asAPartnerSessions!=null && k<asAPartnerSessions.size(); k++){
									
									PSFSessionDT sessionDT = (PSFSessionDT) asAPartnerSessions.get(k);
									gov.cdc.nedss.nnd.psf.XpemsPSDataDocument.XpemsPSData.Clients.Client.AsAPartner.Partner.Sessions sessions = partner.addNewSessions();
									TypeSession session = sessions.addNewSession();
									
									session.setSiteId(sessionDT.getSiteId());
									session.setSessionDate(formatDateMMDDYYYY(sessionDT.getSessionDate()));
									session.setCareStatusAtInterview(TypeCareStatus.Enum.forString(sessionDT.getCareStatusAtInterview()));
								}
								*/
								
								//partnerTestingAndReferral
								
								TypeTestingAndReferral testingAndReferral = partner.addNewPartnerTestingAndReferrals().addNewPartnerTestingAndReferral();
								testingAndReferral.setHivTestPerformed(TypeHIVTestPerformed.Enum.forString(partnerDT.getHivTestPerformed()));
								testingAndReferral.setSampleDate(formatDateMMDDYYYY(partnerDT.getSampleDate()));
								testingAndReferral.setFormId("");//According to Jennifer Ward: States do not want to collect form ID. It can be a fixed blank in the xml. 
								testingAndReferral.setHivTestResult(TypeHIVTestResult.Enum.forString(partnerDT.getHivTestResult()));
								testingAndReferral.setProvisionOfResultValueCode(TypeProvisionOfResult.Enum.forString(partnerDT.getProvisionOfResultValueCode()));
								testingAndReferral.setSyphilisTest(TypeYesNo.Enum.forString(partnerDT.getSyphilisTest()));
								testingAndReferral.setSyphilisTestResult(TypeSyphilisTestResult.Enum.forString(partnerDT.getSyphilisTestResult()));
								testingAndReferral.setCurrentHIVMedicalCareStatus(TypeCurrentHIVMedicalCareStatus.Enum.forString(partnerDT.getCurrentHIVMedicalCareStatus()));
								testingAndReferral.setFirstMedicalCareAppointmentDate(formatDateMMDDYYYY(partnerDT.getFirstMedicalCareAppointmentDate()));
								testingAndReferral.setCurrentlyOnPrEP(TypeYesNo.Enum.forString(partnerDT.getCurrentlyOnPrEP()));
								testingAndReferral.setReferredToPrEP(TypeReferredToPrEP.Enum.forString(partnerDT.getReferredToPrEP()));
								
							}
							
						}
					
					
					}catch (Exception ePartner){
						
						successFailurePartner="Failure";
						errorMessagePartner = "Exception:"+ePartner.getMessage();
						
						
						
					}
					
					Date datePartner = new Date();
					endDatePartner=dateFormat.format(datePartner);
					
				
				}
		}catch(Exception eClient){
			
			successFailureClient="Failure";
			errorMessageClient=eClient.getMessage();
			
		}
		
		
		//Client
		if(successFailureClient!=null && successFailureClient.equalsIgnoreCase("Success"))
			errorMessageClient = clientCount+" records inserted in Client section";
		 dateClient = new Date();
		 endDateClient=dateFormat.format(dateClient);
		 
		
		clientInformation.add("Client");
		clientInformation.add(successFailureClient);
		clientInformation.add(errorMessageClient);
		clientInformation.add(startDateClient);
		clientInformation.add(endDateClient);
		 
		 
			 
		 //Index
			
		if(successFailureIndex!=null && successFailureIndex.equalsIgnoreCase("Success"))
			errorMessageIndex = indexCount+" records inserted in AsAnIndex section";
			
		indexInformation.add("AsAnIndex");
		indexInformation.add(successFailureIndex);
		indexInformation.add(errorMessageIndex);
		indexInformation.add(startDateIndex);
		indexInformation.add(endDateIndex);
			 
		//Session
		
		if(successFailureSession!=null && successFailureSession.equalsIgnoreCase("Success"))
			errorMessageSession = sessionCount+" records inserted in Session section, "+sessionDeduplicated.size()+" of them are unique session records";
		
		
		sessionInformation.add("Session");
		sessionInformation.add(successFailureSession);
		sessionInformation.add(errorMessageSession);
		sessionInformation.add(startDateSession);
		sessionInformation.add(endDateSession);
		
		//Partner
		
		if(successFailurePartner!=null && successFailurePartner.equalsIgnoreCase("Success"))
			errorMessagePartner =partnerCount+" records inserted in Partner section, "+partnerDeduplicated.size()+" of them are unique partner records";
		
		
		partnerInformation.add("Partner");
		partnerInformation.add(successFailurePartner);
		partnerInformation.add(errorMessagePartner);
		partnerInformation.add(startDatePartner);
		partnerInformation.add(endDatePartner);

			
			
		} catch (Exception e) {
			logger.error(" Exception in fillClientsInformation():", e.getMessage());
		}
		logger.debug("leaving fillClientsInformation()");
		return;
	}
	
	/**
	 * removePartnersMultipleHIV: if there's non OOJ partners which caseNumberEarliestPS are the same, remove the ones that are not the earliest (invAddTime)
	 * @param partnerDTList
	 * @return
	 */
	private static ArrayList<Object> removePartnersMultipleHIV(ArrayList<Object> partnerDTList){
		HashMap<String, Object> newestCase = new HashMap <String, Object>();
		ArrayList<Object> finalPartnerList = new ArrayList<Object>();
		//get the newest investigation for that patient, in order to use that from data that does not repeat.
		//filter also based on caseNubmerEarliestPS 
		PSFPartnerDT partnerDTLatest=null;
		Timestamp maxTime=null;
		//go through each of them, and the ones that are not OOJ, store the latest one
		for(int j=0; j<partnerDTList.size(); j++){
			PSFPartnerDT partnerDT = (PSFPartnerDT)partnerDTList.get(j);
			Timestamp time = partnerDT.getInvAddtime();
			String earliestCase = partnerDT.getCaseNumberEarliestPS();
			
			if(!earliestCase.startsWith("OOJ")){
				if(newestCase.containsKey(earliestCase)){
					PSFPartnerDT ps = (PSFPartnerDT)newestCase.get(earliestCase);

					
					if(time!=null && time.after(ps.getInvAddtime()))
							newestCase.put(earliestCase,partnerDT);
						
					
				}else
					newestCase.put(earliestCase,partnerDT);
			}else{//if OOJ
				finalPartnerList.add(partnerDT);
				
			}
		}
		
		//If all the cases were marginal (invAddtime == null), therefore the list will be empty, then use the crAddTime (as per requirements on 07302020)
		if(newestCase.size()==0)
			for(int j=0; j<partnerDTList.size(); j++){
				PSFPartnerDT partnerDT = (PSFPartnerDT)partnerDTList.get(j);
				Timestamp time = partnerDT.getCrAddTime();
				String earliestCase = partnerDT.getCaseNumberEarliestPS();
				
				if(!earliestCase.startsWith("OOJ")){
					if(newestCase.containsKey(earliestCase)){
						PSFPartnerDT ps = (PSFPartnerDT)newestCase.get(earliestCase);
						if(time!=null && time.after(ps.getCrAddTime()))
								newestCase.put(earliestCase,partnerDT);
							
						
					}else
						newestCase.put(earliestCase,partnerDT);
				}
			}
		
		
		
		for (Map.Entry<String,Object> entry : newestCase.entrySet()){
			finalPartnerList.add(entry.getValue());
		}  
		
		return finalPartnerList;
	}
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
	 * deduplicatePartners: if there are 2 partners with same ClientIdLocalId and same ClientIdLocalId, it will leave in the list
	 * the most recent one.
	 * @param partnerDTList
	 * @return
	 */
	public static ArrayList<Object> deduplicatePartners(ArrayList<Object> partnerDTList){
		
		ArrayList<Object> partnerDTList2 = new ArrayList<Object>();
		
		
		for(int i = 0 ; i< partnerDTList.size(); i++){
			
			PSFPartnerDT partner = (PSFPartnerDT)partnerDTList.get(i);
			
			partner = alreadyInList(partnerDTList2, partner);
			
			if(partner!=null)
				partnerDTList2.add(partner);
			
		}
		
	//	if(partnerDTList2.size()==0)//No records have been inserted (all of them are marginal, do the process again, but this time using the crLastChgTime.
			for(int i = 0 ; i< partnerDTList.size(); i++){
				
				PSFPartnerDT partner = (PSFPartnerDT)partnerDTList.get(i);
				
				partner = alreadyInListMarginal(partnerDTList2, partner);
				
				if(partner!=null)
					partnerDTList2.add(partner);
				
			}
		
		return partnerDTList2;
		
	}
	
	/**
	 * alreadyInList: returns the same partnerDT if it is not duplicated or if it is duplicated it leaves in the list the most recent one.
	 * 
	 * This method has been updated according to ND-18227 to compare using invAddTime and if all of them are marginal, then use crLastChgTime.
	 * If some of them are marging and some are not marginal, use the ones that are not marginal to compare using invAddTime.
	 * @param partnerDTList2
	 * @param partner
	 * @return
	 */
	
	public static PSFPartnerDT alreadyInList(ArrayList<Object>partnerDTList2, PSFPartnerDT partner){
		
		
		for (int j=0; j< partnerDTList2.size(); j++){
			
			
			PSFPartnerDT partner2 = (PSFPartnerDT)partnerDTList2.get(j);
			
			if(partner2.getCaseNumberEarliestPS()!= null && !partner2.getCaseNumberEarliestPS().equalsIgnoreCase("") && partner.getCaseNumberEarliestPS()!=null &&
			partner2.getCaseNumberEarliestPS().equalsIgnoreCase(partner.getCaseNumberEarliestPS()))
				if(partner2.getClientIdLocalId()!= null && partner.getClientIdLocalId()!=null &&
				partner2.getClientIdLocalId().equalsIgnoreCase(partner.getClientIdLocalId()))
				{
					
					Timestamp invAddTime = partner.getInvAddtime();
					Timestamp invAddTime2 = partner2.getInvAddtime();
					
					
					if(invAddTime!=null && invAddTime.after(invAddTime2)){//if the new one is more recent, then remove the old one and return the one to be added
						partnerDTList2.remove(j);
					}//otherwise, return null, meaning nothing needs to be updated because the other record is prior to the existing in the list or the new one is marginal
					else
						partner=null;
					
						
					
				}
			
			
		}
		if(partner!=null && partner.getInvAddtime()==null)
			partner=null;//Do not include a partner with invAddTime null just because it is the first one and doesn't have any other partner to compare
		
		
		return partner;
		
	}
	
	/**
	 * alreadyInListMarginal: this is the same method than alreadyInList but in case all cases are marginal, instead of looking at invAddTime, we will look into crLastChgTime
	 * @param partnerDTList2
	 * @param partner
	 * @return
	 */
	
public static PSFPartnerDT alreadyInListMarginal(ArrayList<Object>partnerDTList2, PSFPartnerDT partner){
		
		
	
		for (int j=0; j< partnerDTList2.size() && partner!=null; j++){
			
			
			PSFPartnerDT partner2 = (PSFPartnerDT)partnerDTList2.get(j);
			
			if(partner2.getCaseNumberEarliestPS()!= null && !partner2.getCaseNumberEarliestPS().equalsIgnoreCase("") && partner.getCaseNumberEarliestPS()!=null &&
			partner2.getCaseNumberEarliestPS().equalsIgnoreCase(partner.getCaseNumberEarliestPS()))
				if(partner2.getClientIdLocalId()!= null && partner.getClientIdLocalId()!=null &&
				partner2.getClientIdLocalId().equalsIgnoreCase(partner.getClientIdLocalId()))
				{

					Timestamp crLastChangeTime = partner.getCrLastChgTime();
					Timestamp crLastChangeTime2 = partner2.getCrLastChgTime();

					Timestamp invAddTime = partner.getAddTime();
					
					if(crLastChangeTime!=null && crLastChangeTime2!=null && crLastChangeTime.after(crLastChangeTime2) && invAddTime==null){//if the new one is more recent, then remove the old one and return the one to be added
						partnerDTList2.remove(j);
					}//otherwise, return null, meaning nothing needs to be updated because the other record is prior to the existing in the list or the new one is marginal
					else
						partner=null;
				}
		}
		if(partner!=null && partner.getAddTime()!=null)//Only marginals
			partner=null;
		
		
		return partner;
	}

}
