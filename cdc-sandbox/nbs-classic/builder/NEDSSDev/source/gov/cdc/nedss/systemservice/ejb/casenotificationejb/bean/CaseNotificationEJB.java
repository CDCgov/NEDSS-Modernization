package gov.cdc.nedss.systemservice.ejb.casenotificationejb.bean;


import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;

import gov.cdc.nedss.act.notification.dt.NotificationDT;
import gov.cdc.nedss.act.notification.vo.NotificationVO;
import gov.cdc.nedss.act.publichealthcase.dt.PublicHealthCaseDT;
import gov.cdc.nedss.act.publichealthcase.vo.PublicHealthCaseVO;
import gov.cdc.nedss.association.dao.ActRelationshipDAOImpl;
import gov.cdc.nedss.association.dt.ActRelationshipDT;
import gov.cdc.nedss.controller.ejb.actcontrollerejb.bean.ActController;
import gov.cdc.nedss.controller.ejb.actcontrollerejb.bean.ActControllerHome;
import gov.cdc.nedss.exception.NEDSSAppConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.localfields.dt.NbsQuestionDT;
import gov.cdc.nedss.localfields.ejb.dao.NBSQuestionDAOImpl;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.dao.RetrieveSummaryVO;
import gov.cdc.nedss.proxy.ejb.notificationproxyejb.vo.NotificationProxyVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.bean.ObservationProxy;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.bean.ObservationProxyHome;
import gov.cdc.nedss.systemservice.ejb.casenotificationejb.dao.CaseNotificationDAOImpl;
import gov.cdc.nedss.systemservice.ejb.casenotificationejb.dt.ExportAlgorithmDT;
import gov.cdc.nedss.systemservice.ejb.casenotificationejb.dt.ExportReceivingFacilityDT;
import gov.cdc.nedss.systemservice.ejb.casenotificationejb.dt.ExportTriggerDT;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.systemservice.util.NEDSSDAOFactory;
import gov.cdc.nedss.systemservice.util.PrepareVOUtils;
import gov.cdc.nedss.util.DataTables;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.rmi.PortableRemoteObject;
import javax.servlet.http.HttpServletRequest;

public class CaseNotificationEJB implements SessionBean{
	
	static final LogUtils logger = new LogUtils(CaseNotificationEJB.class.getName());
	CaseNotificationDAOImpl caseNotifDAO = new CaseNotificationDAOImpl();
	
	public Collection<Object> getExportCaseNotifAlgorithm(NBSSecurityObj nbsSecurityObj) throws RemoteException, Exception{
		try {
			Collection<Object> exCaseAlgorithm = caseNotifDAO.getExportCaseNotifAlgorithm();
			Collection<Object> algwithTriggers = new ArrayList<Object> ();
			if(exCaseAlgorithm != null && exCaseAlgorithm.size()>0){
				Iterator<Object> iter =  exCaseAlgorithm.iterator();
				while(iter.hasNext()){
					ExportAlgorithmDT exAldt = (ExportAlgorithmDT)iter.next();
					   Long algUid = exAldt.getExportAlgorithmUid();
					   ArrayList<Object>  triggerList  = getAlgTriggers(nbsSecurityObj,algUid);
					   exAldt.setTriggerDTList(triggerList);				   
					   algwithTriggers.add(exAldt);
				}
				
			}
			return algwithTriggers;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal(e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
	}
	
	public ArrayList<Object>  getAlgTriggers(NBSSecurityObj nbsSecurityObj, Long algUid) throws RemoteException, Exception{
		try {
			ArrayList<Object>  triggers = caseNotifDAO.getAlgorithmTriggersforAlgrthm(algUid);
			return triggers;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal(e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
	}
	
	public Collection<Object> getReceivingFacility(NBSSecurityObj nbsSecurityObj) throws RemoteException, Exception{
		try {
			Collection<Object> recFacColl = caseNotifDAO.getReceivingFacility();
			return recFacColl;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal(e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
	}

	
	public void insertExportAlgorithm( ExportAlgorithmDT dt,NBSSecurityObj nbsSecurityObj) throws RemoteException,javax.ejb.EJBException, Exception{
		Long algorithmUid =new Long(0);
		// The security check is supposed to be done in the UI level . Not repeating in the back end
		try{	
			caseNotifDAO.insertExportAlgorithm(dt);
			algorithmUid = this.getUIDFromAlgName(dt.getAlgorithmName(), nbsSecurityObj);
			if(algorithmUid.compareTo(new Long(0)) ==0 ){
				logger.error("Export Algorith is not inserted in the Export_Algorithm table");
				throw new EJBException("The Algorithm can not be inserted ");
			}			
			if((algorithmUid.compareTo(new Long(0)) != 0)){
				// get the TriggerField Values from the  AlgorithmDT
				ArrayList<Object>  triggerList = dt.getTriggerDTList();
				if(triggerList != null){
					Iterator<Object> iter = triggerList.iterator();
					while(iter.hasNext()){
						ExportTriggerDT trDT = (ExportTriggerDT)iter.next();
						trDT.setExportAlgorithmUid(algorithmUid);
						insertExportAlgthmTrigger(trDT,nbsSecurityObj);
					}
				}
			}
			
			
		}catch(Exception e){
			logger.fatal("Error in insertExportAlgorithm method" + e.getMessage(),e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
			
		}
		
	}
	/**
	 * As per the business rule the Algorithm name has to be unique in the System
	 * @param name
	 * @param nbsSecurityObj
	 * @return
	 * @throws RemoteException
	 * @throws javax.ejb.EJBException
	 * @throws Exception
	 */
	public Long getUIDFromAlgName(String name,NBSSecurityObj nbsSecurityObj)throws RemoteException,javax.ejb.EJBException, Exception{
		try {
			ArrayList<Object>  algList = caseNotifDAO.getAlgorithmUid(name);
			Long algorithmUid =new Long(0);
			if(algList != null && algList.size() >0){
				Iterator<Object> algIter = algList.iterator();
				while(algIter.hasNext()){
					ExportAlgorithmDT exAlDT = (ExportAlgorithmDT)algIter.next();
					algorithmUid = exAlDT.getExportAlgorithmUid();
				}
			}
			
			return algorithmUid;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal(e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
	}
	public void insertExportAlgthmTrigger( ExportTriggerDT dt,NBSSecurityObj nbsSecurityObj) throws RemoteException,javax.ejb.EJBException, Exception{
		try{	
			caseNotifDAO.insertExportAlgthmTrigger(dt);
		}catch(Exception e){
			logger.fatal("Error in insertExportAlgthmTrigger method in Export_Trigger Table" + e.getMessage(),e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
		
	}
	
	public ArrayList<Object>  getTriggerFields(NBSSecurityObj nbsSecurityObj)throws RemoteException,javax.ejb.EJBException, Exception{
		
		ArrayList<Object>  TriggerFieldsColl = new ArrayList<Object> ();
	try{
		 TriggerFieldsColl = (ArrayList<Object> ) caseNotifDAO.getExportTriggerFields(); 
	}catch(Exception e){
		logger.fatal("Error in getTriggerFields method" + e.getMessage(),e);
		throw new javax.ejb.EJBException(e.getMessage(), e);		
	}
	   return TriggerFieldsColl;	
	}
	
	
	public ArrayList<Object>  getTriggerFieldSRTs(NBSSecurityObj nbsSecurityObj)throws RemoteException,javax.ejb.EJBException, Exception{
		
		ArrayList<Object>  TriggerFieldsSRTs = new ArrayList<Object> ();
	try{
		 TriggerFieldsSRTs = (ArrayList<Object> ) caseNotifDAO.getSRTCodeSets(); 
	}catch(Exception e){
		logger.fatal("Error in getTriggerFields method" + e.getMessage(),e);
		throw new javax.ejb.EJBException(e.getMessage(), e);		
	}
	   return TriggerFieldsSRTs;	
	}

	public Long shareCase( NotificationProxyVO notProxyVO,  NBSSecurityObj nbsSecurityObj) throws javax.ejb.EJBException, NEDSSSystemException,
	NEDSSConcurrentDataException{
			 
		ActController actController = null;
		NedssUtils nedssUtils = new NedssUtils();
		Object object = nedssUtils.lookupBean(JNDINames.ActControllerEJB);
		logger.debug("ActController lookup = " + object.toString());
		ActControllerHome acthome = (ActControllerHome) PortableRemoteObject.
		narrow(object, ActControllerHome.class);
		logger.debug("Found ActControllerHome: " + acthome);
		try {
			actController = acthome.create();
			Long newNotficationUid =  updateShareNotification(notProxyVO,nbsSecurityObj);
			
			if(newNotficationUid.compareTo(new Long(1))==0)
				  return newNotficationUid;
			// If it is not an update for the notification, Create a new notification and also the act relationship
			if(newNotficationUid.compareTo(new Long(0))==0){
			    	  
					    		   // Create a new notification along with the act relationship
					    		   Long sourceActUid= actController.setNotification(notProxyVO.getTheNotificationVO(), nbsSecurityObj);
					   			ActRelationshipDAOImpl actRelationshipDAOImpl = new ActRelationshipDAOImpl();
								if (notProxyVO.getTheActRelationshipDTCollection() != null) {
									actRelationshipDAOImpl = (ActRelationshipDAOImpl) NEDSSDAOFactory.
									getDAO(JNDINames.ACT_RELATIONSHIP_DAO_CLASS);
									Iterator<Object> anIterator = notProxyVO. getTheActRelationshipDTCollection().iterator();
									while(anIterator.hasNext()){
										ActRelationshipDT actRelationshipDTa = (ActRelationshipDT) anIterator.next();
										try {
											actRelationshipDTa.setSourceActUid(sourceActUid);
											actRelationshipDAOImpl.store(actRelationshipDTa);
											logger.debug("Got into The ActRelationship, The ActUid is " +
													actRelationshipDTa.getTargetActUid());
											System.out.println("Notification Created PHC ="+notProxyVO.getThePublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid()+"notificationuid = "+sourceActUid);
											
										}
										catch (Exception e) {
											logger.fatal(nbsSecurityObj.getFullName(), e.getMessage(), e);
											e.printStackTrace();
											throw new javax.ejb.EJBException(e.getMessage(), e);
										}
									}
					}
			}		

		} catch (RemoteException e) {
			logger.error("RemoteException thrown at exportOwnership:" + e.getCause()+e.getMessage(), e);
			e.printStackTrace();
			throw new EJBException(e.getMessage(), e);	
		} catch (CreateException e) {
			logger.error("CreateException thrown at exportOwnership:" + e.getCause()+e.getMessage(), e);
			e.printStackTrace();
			throw new EJBException(e.getMessage(), e);	
		}
		
		return notProxyVO.getThePublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid();
	}
	
	private Long updateShareNotification(NotificationProxyVO notProxyVO, NBSSecurityObj nbsSecurityObj) throws javax.ejb.
    EJBException,NEDSSSystemException, NEDSSConcurrentDataException {
	
			 	Long newNotficationUid = new Long(0);
				ActController actController = null;
				NedssUtils nedssUtils = new NedssUtils();
				Object object = nedssUtils.lookupBean(JNDINames.ActControllerEJB);
				logger.debug("ActController lookup = " + object.toString());
				ActControllerHome acthome = (ActControllerHome) PortableRemoteObject.
				narrow(object, ActControllerHome.class);
				logger.debug("Found ActControllerHome: " + acthome);
				try {
					actController = acthome.create();
					//checking for the rec facility and the version Control 
			
			  Collection<Object> actRelationShips = actController.getActRelationships(notProxyVO.getThePublicHealthCaseVO().getThePublicHealthCaseDT().getPublicHealthCaseUid(),nbsSecurityObj);
			  
			  NotificationVO notificationVO = notProxyVO.getTheNotificationVO();
			  // Set the status of the notification according to user permission.
			  if(nbsSecurityObj.getPermission(NBSBOLookup.CASEREPORTING, NBSOperationLookup.CREATENEEDSAPPROVAL)){
				  notificationVO.getTheNotificationDT().setRecordStatusCd(NEDSSConstants.NOTIFICATION_PENDING_CODE);
		       }else{
		    	   notificationVO.getTheNotificationDT().setRecordStatusCd(NEDSSConstants.NOTIFICATION_APPROVED_CODE);
		       }

			  if(actRelationShips!=null){
				  Iterator<Object> it = actRelationShips.iterator();
				  while(it.hasNext()){
					  ActRelationshipDT actRelationshipDT = (ActRelationshipDT)it.next();
				      if(actRelationshipDT.getTypeCd().equalsIgnoreCase(NEDSSConstants.ACT106_TYP_CD)){

						  NotificationDT notificationDT = actController.getNotificationInfo(
								  actRelationshipDT.getSourceActUid(), nbsSecurityObj);
						  

						  // Call the method here 
						  if(notificationDT.getCd().equalsIgnoreCase(NEDSSConstants.CLASS_CD_SHARE_NOTF) || notificationDT.getCd().equalsIgnoreCase(NEDSSConstants.CLASS_CD_SHARE_NOTF_PHDC)){
					    	   if(notificationDT.getExportReceivingFacilityUid()!= null && notificationDT.getExportReceivingFacilityUid().compareTo(notProxyVO.getTheNotificationVO().getTheNotificationDT().getExportReceivingFacilityUid()) == 0){
					    		   if(notificationDT.getRecordStatusCd().equals(NEDSSConstants.NOTIFICATION_REJECTED_CODE)|| notificationDT.getRecordStatusCd().equals(NEDSSConstants.NOTIFICATION_MESSAGE_FAILED) || notificationDT.getRecordStatusCd().equals(NEDSSConstants.COMPLETED)){
									   String trigCd = NEDSSConstants.NOT_APR;  				    		   
									   notificationVO = updateNotificationforExport(actRelationshipDT.getSourceActUid(), trigCd,
									   notificationDT.getCaseConditionCd(),notificationDT.getCaseClassCd(),
						     		   notificationDT.getProgAreaCd(),notProxyVO.getTheNotificationVO().getTheNotificationDT().getJurisdictionCd(),
									   notificationDT.getSharedInd(), notProxyVO.getTheNotificationVO().getTheNotificationDT().getExportReceivingFacilityUid(),notProxyVO.getTheNotificationVO().getTheNotificationDT().getTxt(), nbsSecurityObj);
									   newNotficationUid = actController.setNotification(notificationVO,nbsSecurityObj);
					    		   }else {
					    			 //If the record status code is not REJECTED OR MSG_FAIL OR COMPLETED do not do anything.
					    			   return new Long(1);
					    		   }
					    		   		   
					    	   }
						  }
				      }
				  }
			  }
			} catch (NEDSSConcurrentDataException ex) {
				  logger.fatal("InvestigationProxyEJB.transferOwnership: The entity cannot be updated as concurrent access is not allowed!" + ex.getMessage(), ex);
				  throw new NEDSSConcurrentDataException( ex.getMessage(), ex);
			  }
			  catch (Exception e) {
				  logger.fatal("nbsSecurityObj.getFullName: " + nbsSecurityObj.getFullName() + ", " + e.getMessage(), e);
				  e.printStackTrace();
				  throw new javax.ejb.EJBException(e.getMessage(), e);
			  }
			  return newNotficationUid;
	}
	
	
	  
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
		     Collection<Object> notificationVOCollection = null;
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
		       notificationDT.setTxt(comment);
		       notificationDT.setExportReceivingFacilityUid(recFacilithyUid);
		      
		       notificationDT.setItDirty(true);
		       notificationVO.setItDirty(true);   
		      
		       
		         
			
				       //retreive the new NotificationDT generated by PrepareVOUtils
				       newNotificationDT = (NotificationDT) prepareVOUtils.prepareVO(
				       notificationDT, NBSBOLookup.NOTIFICATION, businessTriggerCd,
				       DataTables.NOTIFICATION_TABLE, NEDSSConstants.BASE, nbsSecurityObj); 
				       
				       /*check the security here for approve or pend_Appr - security check .
				       If the user has approval permission approve it else change the status to PEND_APR 
				       */ 
				       if(nbsSecurityObj.getPermission(NBSBOLookup.CASEREPORTING, NBSOperationLookup.CREATENEEDSAPPROVAL)){
				    	   newNotificationDT.setRecordStatusCd(NEDSSConstants.NOTIFICATION_PENDING_CODE);
				       }else{
				    	   newNotificationDT.setRecordStatusCd(NEDSSConstants.NOTIFICATION_APPROVED_CODE);
				       }			
				       //replace old NotificationDT in NotificationVO with the new NotificationDT
				         notificationVO.setTheNotificationDT(newNotificationDT);
				         return notificationVO;				         
		     
		     }
		        catch (Exception e)
		        {
		               e.printStackTrace();
		               logger.fatal("Error calling ActController.setNotification() " + e.getMessage(),e);
		               throw new NEDSSSystemException(e.getMessage(), e);
		        }

		 }


	public void ejbCreate() throws java.rmi.RemoteException,
	javax.ejb.CreateException {
	}

	public void ejbActivate() throws EJBException, RemoteException {
		// TODO Auto-generated method stub
		
	}

	public void ejbPassivate() throws EJBException, RemoteException {
		// TODO Auto-generated method stub
		
	}

	public void ejbRemove() throws EJBException, RemoteException {
		// TODO Auto-generated method stub
		
	}

	public void setSessionContext(SessionContext arg0) throws EJBException,
			RemoteException {
		// TODO Auto-generated method stub
		
	}


	public void getActiveExportAlgorithm( NBSSecurityObj nbsSecurityObj) throws EJBException, NEDSSAppConcurrentDataException, Exception{
		try {
			CaseNotificationDAOImpl caseNotificationDAOImpl= new CaseNotificationDAOImpl();
			Collection<Object> activeAlgorithmColl=caseNotificationDAOImpl.getExportAlgorithmrCollection();
			NbsQuestionDT nbsQuestionDT =null;
			TreeSet<Object> set  = new TreeSet<Object>();
			NBSQuestionDAOImpl nBSQuestionDAOImpl = new NBSQuestionDAOImpl();
			if(activeAlgorithmColl!=null && activeAlgorithmColl.size()>0){
				java.util.Iterator<Object> it = activeAlgorithmColl.iterator();
				StringBuffer generateSelectClause= new StringBuffer();
				StringBuffer generateWhereClause= new StringBuffer();
				generateSelectClause.append("Select Public_Health_Case.public_health_case_uid ");
				while(it.hasNext()){
					ExportAlgorithmDT exportAlgorithmDT =(ExportAlgorithmDT)it.next();
					Collection<Object> exportTriggerColl = caseNotificationDAOImpl.getExportTrigggerCollection(exportAlgorithmDT.getExportAlgorithmUid());
					if (exportTriggerColl!=null){
						java.util.Iterator<Object> iter = exportTriggerColl.iterator();
						while(iter.hasNext()){
							ExportTriggerDT exportTriggerDT=(ExportTriggerDT)iter.next();

							nbsQuestionDT = nBSQuestionDAOImpl.findNBSQuestion(exportTriggerDT.getNbsQuestionUid());
							set.add(nbsQuestionDT.getDataLocation());
							if(iter.hasNext()){
								String logicValue="";
								
								//generateSelectClause.append(nbsQuestionDT.getDataLocation()+",");
								if((nbsQuestionDT.getDataType().equals("Coded")) || (nbsQuestionDT.getDataType().equals("Text"))){
									if(exportTriggerDT.getTriggerLogic() != null){
										if((exportTriggerDT.getTriggerLogic().equals("=")) || (exportTriggerDT.getTriggerLogic().equals("!=")) ){
											logicValue = exportTriggerDT.getTriggerLogic()
														 +" '"+exportTriggerDT.getTriggerFilter()+"' ";									
										}
										else if(exportTriggerDT.getTriggerLogic().equals("CT")){
											logicValue = " LIKE '"+ exportTriggerDT.getTriggerFilter()+"'";
											
										}
										
									}
								}
								if((nbsQuestionDT.getDataType().equals(NEDSSConstants.NBS_QUESTION_DATATYPE_NUMERIC))){								
										logicValue = exportTriggerDT.getTriggerLogic()+exportTriggerDT.getTriggerFilter();
																	
								}else if((nbsQuestionDT.getDataType().equals(NEDSSConstants.NBS_QUESTION_DATATYPE_DATE))){								
										logicValue = exportTriggerDT.getTriggerLogic()+ " '"+exportTriggerDT.getTriggerFilter()+"'";
																	
								}
								generateWhereClause.append(nbsQuestionDT.getDataLocation() + logicValue +" and ");

							}
							else{
								//generateSelectClause.append(nbsQuestionDT.getDataLocation());
								generateWhereClause.append(nbsQuestionDT.getDataLocation() + exportTriggerDT.getTriggerLogic()+  " '"+exportTriggerDT.getTriggerFilter()+ "' ");

							}

						}
					}
					String dynamicWhenClauseBuilder= dynamicWhereCluaseBuilder(set);
					String fullSQLQuery = "";
					if(dynamicWhenClauseBuilder.indexOf("WHERE")!= -1)
						fullSQLQuery= generateSelectClause+dynamicWhenClauseBuilder+ " AND "+generateWhereClause;
					else
						fullSQLQuery= generateSelectClause+dynamicWhenClauseBuilder+ " WHERE "+generateWhereClause;
					//Reset the values each time 
					Collection<Object> phcColl= caseNotificationDAOImpl.getExportTrigggerPHCCollection(fullSQLQuery);
					
					if(phcColl != null)				
						System.out.println("Number of PHCs for the algorithm " + exportAlgorithmDT.getAlgorithmName()+" UID="+ exportAlgorithmDT.getExportAlgorithmUid()+ phcColl.size());
					if(phcColl==null && phcColl.size()==0){
						System.out.println("There exists no phc case for export trigger"+exportAlgorithmDT.getAlgorithmName()+" UID="+ exportAlgorithmDT.getExportAlgorithmUid());
						logger.debug("There exists no phc case for export trigger"+exportAlgorithmDT.getAlgorithmName()+" UID="+ exportAlgorithmDT.getExportAlgorithmUid()+ "query ="+fullSQLQuery);
						
					}
					if(phcColl!=null && phcColl.size()>0){

						logger.debug("There exists phc cases  for export trigger"+fullSQLQuery );
						java.util.Iterator<Object> iterator = phcColl.iterator();
						while(iterator.hasNext()){
							Long publicHealthCaseUID =(Long) iterator.next();
							NedssUtils nedssUtils = new NedssUtils();
							Object theLookedUpObject;
							theLookedUpObject = nedssUtils.lookupBean(JNDINames.ActControllerEJB);
							ActControllerHome actHome = (ActControllerHome) PortableRemoteObject.
							narrow(theLookedUpObject, ActControllerHome.class);
							ActController actController = actHome.create();
							PublicHealthCaseVO  thePublicHealthCaseVO = actController.getPublicHealthCase(
									publicHealthCaseUID, nbsSecurityObj);

							NotificationProxyVO notificationProxyVO =setShareNotificationProxyVO( thePublicHealthCaseVO,  exportAlgorithmDT.getExportReceivingFacilityUid(),  nbsSecurityObj);
							submitNotification(notificationProxyVO,  nbsSecurityObj);

						}

					}
					//Reset the values of Strings 
			
					  generateWhereClause= new StringBuffer();
					  dynamicWhenClauseBuilder="";

				}

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal(e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}

	}
	
	public Long submitNotification( NotificationProxyVO notProxyVO,  NBSSecurityObj nbsSecurityObj)throws RemoteException, javax.ejb.EJBException, NEDSSSystemException,
	NEDSSConcurrentDataException{
		return shareCase(notProxyVO,  nbsSecurityObj);
		
	}
	
	private String dynamicWhereCluaseBuilder(TreeSet<Object> set){
		try {
			HashMap<Object, Object> map= new HashMap<Object, Object>();
			java.util.Iterator<Object> it = set.iterator();
			StringBuffer buff = new StringBuffer();
			StringBuffer whereClause= new StringBuffer();
			buff.append("FROM " +"PUBLIC_HEALTH_CASE " );
			while(it.hasNext()){
				String dataLocation = (String)it.next();
				String Table_Name = dataLocation.substring(0,dataLocation.indexOf(".") );
				String Column_Name = dataLocation.substring(dataLocation.lastIndexOf((".") , dataLocation.length()));
				logger.debug("Table_Name is "+ Table_Name);
				logger.debug("Column_Name is "+ Column_Name);
				if(dataLocation.indexOf("Public_Health_Case") != -1){
					//ignore the this where clause
				}
				else if(dataLocation.indexOf("Person") != -1){
					buff.append(", " +Table_Name );
					buff.append(", PARTICIPATION WHERE PUBLIC_HEALTH_CASE.PUBLIC_HEALTH_CASE_UID=PARTICIPATION.ACT_UID AND PARTICIPATION.SUBJECT_ENTITY_UID=PERSON.PERSON_UID AND " +
					"PUBLIC_HEALTH_CASE.PUBLIC_HEALTH_CASE_UID NOT IN (SELECT ACT_UID FROM NBS_Master_Log)");
				}
				

			}
			return buff.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal(e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
	}

	public  static NotificationProxyVO setShareNotificationProxyVO(PublicHealthCaseVO publicHealthCaseVO, Long exportReceivingFacilityUid,
			NBSSecurityObj nbsSecurityObj)
	throws java.rmi.RemoteException, javax.ejb.EJBException,
	NEDSSAppConcurrentDataException, Exception {
		try {
			NotificationDT notificationDT = new NotificationDT();
			java.util.Date startDate= new java.util.Date();
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
			notificationDT.setJurisdictionCd(publicHealthCaseVO.getThePublicHealthCaseDT().getJurisdictionCd());
			notificationDT.setProgAreaCd(publicHealthCaseVO.getThePublicHealthCaseDT().getProgAreaCd());
			notificationDT.setVersionCtrlNbr(new Integer(1));
			notificationDT.setCaseConditionCd(publicHealthCaseVO.getThePublicHealthCaseDT().getCd());
			notificationDT.setNotificationUid(new Long(-1));
			notificationDT.setCd("SHARE_NOTF_BATCH");
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
			ArrayList<Object>  actRelColl = new ArrayList<Object> ();
			actRelColl.add(0, actDT1);
			notProxyVO.setTheActRelationshipDTCollection(actRelColl);
			return notProxyVO;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal("exportReceivingFacilityUid: " + exportReceivingFacilityUid + ", " + e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}

	}
	public Map<Object,Object> insertReceivingSystems( ExportReceivingFacilityDT dt,NBSSecurityObj nbsSecurityObj) throws RemoteException,javax.ejb.EJBException, Exception{
		try{	
			Map<Object, Object> errorMap = caseNotifDAO.insertReceivingSystems(dt);
			return errorMap;
		}catch(Exception e){
			logger.fatal("Error in insertExportAlgthmTrigger method in Export_Trigger Table" + e.getMessage(),e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
		
	}
	
	public String selectReceivingSystem( ExportReceivingFacilityDT dt,NBSSecurityObj nbsSecurityObj) throws RemoteException,javax.ejb.EJBException, Exception{
		try{	
			Long receivingFacilityUid = caseNotifDAO.getReceivingSystem(dt);
			return receivingFacilityUid.toString();
		}catch(Exception e){
			logger.fatal("Error in insertExportAlgthmTrigger method in Export_Trigger Table" + e.getMessage(),e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
		
	}
	
	public Map<Object,Object> updateReceivingSystems( ExportReceivingFacilityDT dt,NBSSecurityObj nbsSecurityObj) throws RemoteException,javax.ejb.EJBException, Exception{
		try{	
			Map<Object, Object> errorMap = caseNotifDAO.updateReceivingSystems(dt);
			return errorMap;
		}catch(Exception e){
			logger.fatal("Error in insertExportAlgthmTrigger method in Export_Trigger Table" + e.getMessage(),e);
			throw new javax.ejb.EJBException(e.getMessage(), e);
		}
	}
	
}
