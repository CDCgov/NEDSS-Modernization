package gov.cdc.nedss.nnd.ejb.nndmessageprocessorejb.bean;

import gov.cdc.nedss.act.intervention.dt.InterventionDT;
import gov.cdc.nedss.act.notification.dt.NotificationDT;
import gov.cdc.nedss.act.notification.vo.NotificationVO;
import gov.cdc.nedss.act.observation.dt.ObsValueCodedDT;
import gov.cdc.nedss.act.observation.dt.ObsValueNumericDT;
import gov.cdc.nedss.act.observation.dt.ObservationDT;
import gov.cdc.nedss.act.observation.vo.ObservationVO;
import gov.cdc.nedss.act.publichealthcase.dt.PublicHealthCaseDT;
import gov.cdc.nedss.act.publichealthcase.vo.PublicHealthCaseVO;
import gov.cdc.nedss.act.summaryreport.vo.SummaryReportProxyVO;
import gov.cdc.nedss.association.dao.ActRelationshipDAOImpl;
import gov.cdc.nedss.association.dt.ActRelationshipDT;
import gov.cdc.nedss.association.dt.ParticipationDT;
import gov.cdc.nedss.controller.ejb.actcontrollerejb.bean.ActController;
import gov.cdc.nedss.controller.ejb.actcontrollerejb.bean.ActControllerHome;
import gov.cdc.nedss.entity.material.dt.ManufacturedMaterialDT;
import gov.cdc.nedss.entity.material.dt.MaterialDT;
import gov.cdc.nedss.entity.material.vo.MaterialVO;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.ldf.dt.StateDefinedFieldMetaDataDT;
import gov.cdc.nedss.ldf.ejb.ldfmetadataejb.bean.LDFMetaData;
import gov.cdc.nedss.ldf.ejb.ldfmetadataejb.bean.LDFMetaDataHome;
import gov.cdc.nedss.nnd.dt.TransportQOutDT;
import gov.cdc.nedss.nnd.ejb.nndmessageprocessorejb.dao.MsgOutMessageDAOImpl;
import gov.cdc.nedss.nnd.ejb.nndmessageprocessorejb.dao.MsgOutReceivingFacilityDAOImpl;
import gov.cdc.nedss.nnd.ejb.nndmessageprocessorejb.dao.MsgOutReceivingMessageDAOImpl;
import gov.cdc.nedss.nnd.ejb.nndmessageprocessorejb.dao.MsgOutSendingFacilityDAOImpl;
import gov.cdc.nedss.nnd.ejb.nndmessageprocessorejb.dao.NETSSTransportQOutDAO;
import gov.cdc.nedss.nnd.ejb.nndmessageprocessorejb.dao.NNDActivityLogDAOImpl;
import gov.cdc.nedss.nnd.ejb.nndmessageprocessorejb.dao.NNDMessageBatchDAO;
import gov.cdc.nedss.nnd.ejb.nndmessageprocessorejb.dao.NNDMsgOutErrorLogDAOImpl;
import gov.cdc.nedss.nnd.ejb.nndmessageprocessorejb.dao.NotificationConditionCodeDAOImpl;
import gov.cdc.nedss.nnd.ejb.nndmessageprocessorejb.dao.PSFDAOImpl;
import gov.cdc.nedss.nnd.ejb.nndmessageprocessorejb.dao.TransportQOutDAOImpl;
import gov.cdc.nedss.nnd.ejb.nndmessageprocessorejb.helper.AggregateSummaryMessageBuilder;
import gov.cdc.nedss.nnd.ejb.nndmessageprocessorejb.helper.NNDLDFMarshaller;
import gov.cdc.nedss.nnd.ejb.nndmessageprocessorejb.helper.NNDMessageBuilder;
import gov.cdc.nedss.nnd.ejb.nndmessageprocessorejb.helper.NNDObjectMarshaller;
import gov.cdc.nedss.nnd.ejb.nndmessageprocessorejb.helper.PHDCMessageBuilder;
import gov.cdc.nedss.nnd.exception.NNDException;
import gov.cdc.nedss.nnd.helper.MsgOutErrorLogDT;
import gov.cdc.nedss.nnd.helper.MsgOutMessageDT;
import gov.cdc.nedss.nnd.helper.MsgOutReceivingFacilityDT;
import gov.cdc.nedss.nnd.helper.MsgOutReceivingMessageDT;
import gov.cdc.nedss.nnd.helper.MsgOutSendingFacilityDT;
import gov.cdc.nedss.nnd.helper.NNDActivityLogDT;
import gov.cdc.nedss.nnd.ejb.nndmessageprocessorejb.helper.PageBuilderToMasterMessageUtil;
import gov.cdc.nedss.nnd.util.NNDConstantUtil;
import gov.cdc.nedss.nnd.vo.MessageProcessorVO;
import gov.cdc.nedss.proxy.ejb.interventionproxyejb.bean.InterventionProxy;
import gov.cdc.nedss.proxy.ejb.interventionproxyejb.bean.InterventionProxyHome;
import gov.cdc.nedss.proxy.ejb.interventionproxyejb.vo.VaccinationProxyVO;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.bean.InvestigationProxy;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.bean.InvestigationProxyHome;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.dao.RetrieveSummaryVO;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.InvestigationProxyVO;
import gov.cdc.nedss.proxy.ejb.notificationproxyejb.bean.NotificationProxy;
import gov.cdc.nedss.proxy.ejb.notificationproxyejb.bean.NotificationProxyHome;
import gov.cdc.nedss.proxy.ejb.notificationproxyejb.vo.NotificationSummaryVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.bean.ObservationProxy;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.bean.ObservationProxyHome;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.LabReportSummaryVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.LabResultProxyVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.MorbReportSummaryVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.MorbidityProxyVO;
import gov.cdc.nedss.nnd.util.PartnerServicesUtil;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.bean.NbsDocument;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.bean.NbsDocumentHome;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.uidgenerator.UidClassCodes;
import gov.cdc.nedss.systemservice.uidgenerator.UidGeneratorHelper;
import gov.cdc.nedss.systemservice.util.PrepareVOUtils;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;





import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.ejb.SessionContext;
import javax.rmi.PortableRemoteObject;

public class NNDMessageProcessorEJB implements javax.ejb.SessionBean 
{

	static final LogUtils logger = new LogUtils(NNDMessageProcessorEJB.class.
                                              getName());
	private NedssUtils nedssUtils = new NedssUtils();
	private String moduleName =  "";
	private TransportQOutDAOImpl transportQOutDao;
	private static PropertyUtil propertyUtil= PropertyUtil.getInstance();
	private static final String READY_FOR_TRANSFORM_STATUS = "RDY_FOR_TRNSFRM";

	/**
	 * @roseuid 3D590C690177
	 * @J2EE_METHOD  --  NNDMessageProcessorEJB
	 */
	public NNDMessageProcessorEJB() {
	}

	/**
	 * @roseuid 3C0648F10194
	 * @J2EE_METHOD  --  ejbRemove
	 * A container invokes this method before it ends the life of the session object. This
	 * happens as a result of a client's invoking a remove operation, or when a container
	 * decides to terminate the session object after a timeout. This method is called with
	 * no transaction context.
	 */
	public void ejbRemove() {
	}

	/**
	 * @roseuid 3C0648F1019E
	 * @J2EE_METHOD  --  ejbActivate
	 * The activate method is called when the instance is activated from its 'passive' state.
	 * The instance should acquire any resource that it has released earlier in the ejbPassivate()
	 * method. This method is called with no transaction context.
	 */
	public void ejbActivate() {
	}

	/**
	 * @roseuid 3C0648F101B2
	 * @J2EE_METHOD  --  ejbPassivate
	 * The passivate method is called before the instance enters the 'passive' state. The
	 * instance should release any resources that it can re-acquire later in the ejbActivate()
	 * method. After the passivate method completes, the instance must be in a state that
	 * allows the container to use the Java Serialization protocol to externalize and store
	 * away the instance's state. This method is called with no transaction context.
	 */
	public void ejbPassivate() {
	}

	/**
	 * @roseuid 3C1534910248
	 * @J2EE_METHOD  --  setSessionContext
	 * Set the associated session context. The container calls this method after the instance
	 * creation. The enterprise Bean instance should store the reference to the context
	 * object in an instance variable. This method is called with no transaction context.
	 */
	public void setSessionContext(SessionContext sessioncontext) throws EJBException, RemoteException {
	}

	/**
	 * @roseuid 3D406CCA01F4
	 * @J2EE_METHOD  --  ejbCreate
	 * Called by the container to create a session bean instance. Its parameters typically
	 * contain the information the client uses to customize the bean instance for its use.
	 * It requires a matching pair in the bean class and its home interface.
	 */
	public void ejbCreate() {
	}

	public Map<Object,Object> getApprovedNotification(Integer maxRow, NBSSecurityObj nbsSecurityObj) throws NNDException {

		try {
			logger.debug("calling getApprovedNotification");
			//return max rows of NotificationUids sequenced by notification_uid
			NNDMessageBatchDAO batchDAO = new NNDMessageBatchDAO();
			Map<Object,Object>  uidMap= null;
			uidMap = batchDAO.getApprovedAndPendDelNotificationUid(maxRow.intValue());

			//security checking before returning notification uidList
			String userId = nbsSecurityObj.getTheUserProfile().getTheUser().getUserID();

			if (userId.equals("nnd_batch_to_cdc")) {
				if (uidMap != null) {
					logger.debug("returning approve Notification list, size = " + uidMap.size());
					}
				return uidMap;
			}
			else {
				NNDException nnde = new NNDException("NNDException in NNDMessageProcessor Failed at security checking!");
				nnde.setModuleName("NNDMessageProcessorEJB.getApprovedNotifications");
				throw nnde;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.fatal("NNDMessageProcessorEJB.getCSRNotifications: maxRow: " + maxRow + ", userId: " + nbsSecurityObj.getTheUserProfile().getTheUser().getUserID() + ", " +  e.getMessage(),e);
			NNDException nndOther = new NNDException("Exception in NNDMessageProcessor " + e.getMessage());
			nndOther.setModuleName("NNDMessageProcessorEJB.getApprovedNotifications");
			throw nndOther;
		}
	}
	

	
	/**
	 * @roseuid 3D6BF63C01A5
	 * @J2EE_METHOD  --  buildAndWriteInvestigationMessage
	 */
	public void buildAndWriteInvestigationMessage(NotificationDT notificationDT,
												  NBSSecurityObj nbsSecurityObj) 
												  throws NNDException, java.rmi.RemoteException {
		try {
			moduleName = "NNDMessageProcessorEJB.buildAndWriteInvestigationMessage";
			
			logger.debug("Before calling buildAndWriteInvestigationMessage, notificationUid = " +
					notificationDT.getNotificationUid());
			
			logger.debug("Before calling buildAndWriteInvestigationMessage, notification record_status_cd = " +
					notificationDT.getRecordStatusCd());
			
			logger.debug("Before calling buildAndWriteInvestigationMessage, notification getVersionCtrlNbr = " +
					notificationDT.getVersionCtrlNbr());

			//    String nndMessage = null; not used

			//StepA, buildInvestigationMessage
			ActRelationshipDT actRelationshipDT = null;
			InvestigationProxyVO investigationProxyVO = null;
			LabResultProxyVO labResultProxyVO = null;
			VaccinationProxyVO vaccinationProxyVO = null;
			String notificationMessage = null;
			MessageProcessorVO messageProcessorVO = new MessageProcessorVO(); 
			PublicHealthCaseDT publicHealthCaseDT = null;
			PublicHealthCaseVO phcVO = new PublicHealthCaseVO();
			ActController actController = null;

			try {

				// Get ActRelationship to PublicHealthCase, pass sourceActUid = NotificationUid, typeCd = "Notification"
				ActRelationshipDAOImpl actRelationshipDAOImpl = new ActRelationshipDAOImpl();
				Collection<Object>  actRelationshipDTColl = actRelationshipDAOImpl.loadSource(
															notificationDT.getNotificationUid().longValue(),
															NEDSSConstants.ACT106_TYP_CD);

				// Business rules state One notification relates to ONLY ONE PHC investigation.
				if (actRelationshipDTColl.size() != 1) {
					NNDException nnde = new NNDException("ERROR: found 0 or more than one actRelationship between notification and investigation! ");
					nnde.setModuleName(moduleName);
					throw nnde;
				}

				for (Iterator<Object> anIterator = actRelationshipDTColl.iterator(); anIterator.hasNext(); ) {
					actRelationshipDT = (ActRelationshipDT) anIterator.next();

					if (actRelationshipDT != null) {
						break;
					}
				}

				if (actRelationshipDT == null) {
					NNDException nnde2 = new NNDException("actRelationshipDT is null! notificationUid = " +
												notificationDT.getNotificationUid());
					nnde2.setModuleName(moduleName);

					throw nnde2;
				}

				//Get related Public Health Case DT so we can get local id for cn_transportq_out
				NedssUtils nedssUtils = new NedssUtils();
				Object object = nedssUtils.lookupBean(JNDINames.ActControllerEJB);
				logger.debug("ActController lookup = " + object.toString());

				ActControllerHome actHome = (ActControllerHome) PortableRemoteObject.narrow(object,ActControllerHome.class);
				logger.debug("Found ActControllerHome: " + actHome);
				actController = actHome.create();
				publicHealthCaseDT = actController.getPublicHealthCaseInfo(actRelationshipDT.getTargetActUid(),  nbsSecurityObj);
			}
			catch (Exception e) {
				e.printStackTrace();
				logger.fatal("TargetActUid: " + actRelationshipDT.getTargetActUid() + ", " + e.getMessage(), e);
				NNDException nndOther = new NNDException("NNDMessageProcessorEJB.buildAndWriteInvestigationMessage:  can't find related public health case" +
														 notificationDT.getNotificationUid() + 
														 e.getMessage());
				nndOther.setModuleName(moduleName);

				throw nndOther;
			}

			Long publicHealthCaseUid = actRelationshipDT.getTargetActUid();
			String publicHealthCaseLocalId = publicHealthCaseDT.getLocalId();

			
			// Get NND Entity Identifiers from Condition_code table - needed for MSH-21 and the presence
			// of this identifier signals that this condition should be processed with the NND message
			// processing via Rhapsody instead of the legacy Master Message processing
			NotificationConditionCodeDAOImpl notificationConditionCodeDAOImpl = new NotificationConditionCodeDAOImpl();
			TreeMap conditionMap = notificationConditionCodeDAOImpl.getEntityIdentiferForNNDConditions(NEDSSConstants.INDIVIDUAL, notificationDT.getCaseConditionCd());
			String nndEntityIdentifier = (String)conditionMap.get(notificationDT.getCaseConditionCd());

			// If we have an NND Entity Identifier and this is a NND (NOTF), then we will create an NBSNNDIntermediaryMessage xml message to 
			// and populate the file system and/or the CN_transportq_out table.  Rhapsody route is then used to transform this message
			// into an HL7 2.5, pipe delimited, NND case notification message for transport via PHINMS to CDC. 
			if ( nndEntityIdentifier != null &&  !nndEntityIdentifier.equals(NEDSSConstants.LEGACY_MASTER_MSG) && (notificationDT.getCd() != null && notificationDT.getCd().equals(NEDSSConstants.CLASS_CD_NOTF)) ) {
			       NNDMessageBuilder nndMessageBuilder= new NNDMessageBuilder();
			       // Create the NND message only if nnd indicator is set to Y - task#4609 - REl4.5
			       if(notificationDT.getNndInd()!=null && notificationDT.getNndInd().equals(NEDSSConstants.YES)){
			              try {
			                     nndMessageBuilder.createNotificationMessage(notificationDT, publicHealthCaseUid, publicHealthCaseLocalId, nndEntityIdentifier, nbsSecurityObj);
			              } catch (NEDSSConcurrentDataException ex) { 
			                    logger.fatal("While trying to create NND for Notification uid:  " + notificationDT.getNotificationUid() + ", the Cn_transportq_out update failed due to data concurrent exception." + ex.getMessage(),ex);
			                    throw new NNDException("Concurrent access occurred in NNDMessageProcessorEJB : " + ex.getMessage());
			              }
			       }

			       // Mark Notification as complete and return
			       notificationDT.setCaseClassCd(publicHealthCaseDT.getCaseClassCd());
			       updateNotificationComplete(notificationDT, nbsSecurityObj);
			       return;
			}

			// If we have an Export or Share notification, then we need to create a Public Health Document Container 
			// (PHDC) XML message to facilitate the sharing or exporting of NBS case data to an external system.  
			// This message will be written to the file system and/or NBS_interface table.
			if ( notificationDT.getCd() != null && 
				(notificationDT.getCd().equals(NEDSSConstants.CLASS_CD_EXP_NOTF) || 
				 notificationDT.getCd().equals(NEDSSConstants.CLASS_CD_SHARE_NOTF) || 
				 notificationDT.getCd().equals(NEDSSConstants.CLASS_CD_SHARE_NOTF_PHDC )) ) {
				PHDCMessageBuilder phdcMessageBuilder = new PHDCMessageBuilder();

				try {
					// Build PHDC XML Message and write to NBS_interface table 
					phdcMessageBuilder.createNotificationMessage(notificationDT, publicHealthCaseUid, publicHealthCaseLocalId, null, nbsSecurityObj);
				} catch (NEDSSAppException e) {
					// If a NEDSSAppException was thrown, then the message creation process failed and it was logged to
					// the EDX_activity_log - so now update the Notification table with MSG_FAIL and return.
					logger.fatal(e.getMessage(), e);
					updateNotificationFailure(notificationDT, nbsSecurityObj);
					throw new NNDException(e.getMessage());
					
				}
				// Message creation was successful and logged to the EDX_activity log
				// Mark Notification as COMPLETE and return
				if(notificationDT.getCd().equalsIgnoreCase(NEDSSConstants.CLASS_CD_EXP_NOTF)){
					phcVO.setThePublicHealthCaseDT(publicHealthCaseDT);
					updatePublicHealthCase( notificationDT,  actController,  phcVO,  nbsSecurityObj);
				}
				else
				notificationDT.setCaseClassCd(publicHealthCaseDT.getCaseClassCd());
				
				updateNotificationComplete(notificationDT, nbsSecurityObj);

				return;
			}
long msgOutMessageUid = 0;
if(notificationDT.getNndInd()!=null && notificationDT.getNndInd().equals(NEDSSConstants.YES)){
   
   // Call notificationProxyEJB to validate required fields, the return will be
   // an array of the missing fields. If there is any missing fields, throw exception
   try {
     NotificationProxy notificationProxy = null;
     Object object = nedssUtils.lookupBean(JNDINames.NOTIFICATION_PROXY_EJB);
     NotificationProxyHome notificationProxyHome = (NotificationProxyHome)
			  PortableRemoteObject.narrow(
			  object,
			  NotificationProxyHome.class);
     logger.debug(
			  "Found NotificationProxyHome: " + notificationProxyHome);
     notificationProxy = notificationProxyHome.create();


   }
   catch (CreateException ce) {
	   logger.fatal(
				  "Error calling validateNNDIndividualRequiredFields() in NotificationProxyEJB, where publicHealthCaseUid = " +
				  publicHealthCaseUid, ce.getMessage(),ce);
     ce.printStackTrace();
     NNDException nnde = new NNDException(ce.getMessage() +
			  " error to create notificationProxyEJB.");
     nnde.setModuleName(moduleName);
     throw nnde;
   }
   catch (RemoteException re) {

	   logger.fatal(
				  "Error calling validateNNDIndividualRequiredFields() in NotificationProxyEJB, where publicHealthCaseUid = " +
				  publicHealthCaseUid, re.getMessage(),re);
     NNDException nnde2 = new NNDException("RemoteException was thrown from notificationProxyEJB when calling validateNNDIndividualRequiredFields");
     nnde2.setModuleName(moduleName);
     throw nnde2;
   }
   catch (Exception e) {
     e.printStackTrace();
     logger.fatal(
			  "Error calling validateNNDIndividualRequiredFields() in NotificationProxyEJB, where publicHealthCaseUid = " +
			  publicHealthCaseUid, e.getMessage(),e);
     NNDException nndOther = new NNDException(e.getMessage() + "Error calling validateNNDIndividualRequiredFields() in NotificationProxyEJB, where publicHealthCaseUid = " +
			                                       publicHealthCaseUid);
     nndOther.setModuleName(moduleName);
     throw nndOther;
   }

   //get InvestigationProxyVO by calling InvestigationProxyEJB, and add to messageProcessorVO
   try {

     InvestigationProxy investigationProxy = null;
     String sBeanJndiName = JNDINames.INVESTIGATION_PROXY_EJB;
     Object object = nedssUtils.lookupBean(sBeanJndiName);
     InvestigationProxyHome investigationProxyHome =
			  (InvestigationProxyHome) PortableRemoteObject.narrow(object,
			  InvestigationProxyHome.class);
     logger.debug(
			  "Found InvestigationProxyHome: " +
			  investigationProxyHome);
     investigationProxy = investigationProxyHome.create();
     investigationProxyVO = investigationProxy.getInvestigationProxyLite(
			  publicHealthCaseUid, nbsSecurityObj, true);

     if (nndEntityIdentifier != null && nndEntityIdentifier.equals(NEDSSConstants.LEGACY_MASTER_MSG)) {
			  PageBuilderToMasterMessageUtil.migratePageProxyToLegacyInvestigationProxy(
					publicHealthCaseUid,
					notificationDT,
					investigationProxyVO,
					nbsSecurityObj) ;
     }      
     
     
     if (investigationProxyVO != null) {
			messageProcessorVO.setTheInvestigationProxyVO(
			    investigationProxyVO);
     }
   }
   catch (CreateException ce) {
	   logger.fatal(ce.getMessage(), ce);
     ce.printStackTrace();
     NNDException nnde = new NNDException(ce.getMessage() +
			  " error to create investigationProxyEJB.");
     nnde.setModuleName(moduleName);
     throw nnde;
   }
   catch (FinderException fe) {
	   logger.fatal(fe.getMessage(), fe);
     fe.printStackTrace();
     NNDException nnde2 = new NNDException(fe.getMessage() +
			  " error to call investigationProxy.getInvestigationProxy, targetActUid = " +
			  actRelationshipDT.getTargetActUid());
     nnde2.setModuleName(moduleName);
     throw nnde2;
   }
   catch (NEDSSAppException ae) {
	   logger.fatal(ae.getMessage(), ae);
			ae.printStackTrace();
			NNDException nnde3 = new NNDException(ae.getMessage() +
			    " An error occurred while converting PageProxy to InvestigationProxy for MstMsg ");
			nnde3.setModuleName(moduleName);
			throw nnde3;
    }

   catch (Exception e) {
     e.printStackTrace();
     logger.fatal(
			  " Exception while getting InvestigationProxyEJB");
     NNDException nndOther = new NNDException(e.getMessage() +
			  " Exception while getting InvestigationProxyEJB ");
     nndOther.setModuleName(moduleName);
     throw nndOther;
   }

   /*iterate through investigationVO.ObservationSummaryVO collection, for each ObservationSummaryVO
			     get labResultProxyVO by calling ObservationProxyEJB
    */

   try {
     Collection<Object>  labReportSumVOColl = investigationProxyVO.
			  getTheLabReportSummaryVOCollection();
     ArrayList<Object> labResultProxyVOList = new ArrayList<Object> ();
     if (labReportSumVOColl != null) {
			ObservationProxy observationProxy = null;
			for (Iterator<Object> anIterator = labReportSumVOColl.iterator();
			     anIterator.hasNext(); ) {

			  LabReportSummaryVO labReportSumVO = (LabReportSummaryVO) anIterator.
			      next();
			  if(observationProxy == null) {
				String sBeanJndiName = JNDINames.OBSERVATION_PROXY_EJB;  
			    Object object = nedssUtils.lookupBean(sBeanJndiName);
			    ObservationProxyHome observationProxyHome = (ObservationProxyHome)
			        PortableRemoteObject.narrow(object, ObservationProxyHome.class);
			    logger.debug("Found ObservationProxyHome: " + observationProxyHome);
			    observationProxy = observationProxyHome.create();
			  }//end of if
			  labResultProxyVO = observationProxy.getLabResultProxy(labReportSumVO.
			      getObservationUid(), nbsSecurityObj);
			  labResultProxyVOList.add(labResultProxyVO);
			}
     }
     if (labResultProxyVOList != null) {
			messageProcessorVO.setTheLabResultProxyVOCollection(
			    labResultProxyVOList);

     }
     Collection<Object>  morbReportSumVOColl = investigationProxyVO.
			  getTheMorbReportSummaryVOCollection();
     ArrayList<Object> morbidityProxyVOList = new ArrayList<Object> ();
     if (morbReportSumVOColl != null) {
			ObservationProxy observationProxy = null;
			for (Iterator<Object> anIterator = morbReportSumVOColl.iterator();
			     anIterator.hasNext(); ) {
			  MorbReportSummaryVO morbReportSumVO = (MorbReportSummaryVO)
			      anIterator.next();
			  //if a lab report exists, process the morb
			  if (morbReportSumVO.getTheLabReportSummaryVOColl() != null &&
			      morbReportSumVO.getTheLabReportSummaryVOColl().size() > 0) {


			    if(observationProxy == null) {
			      String sBeanJndiName = JNDINames.OBSERVATION_PROXY_EJB;
			      Object object = nedssUtils.lookupBean(sBeanJndiName);
			      ObservationProxyHome observationProxyHome = (ObservationProxyHome)
			          PortableRemoteObject.narrow(object, ObservationProxyHome.class);
			      observationProxy = observationProxyHome.create();
			    }//end of if
			    MorbidityProxyVO morbidityProxyVO = observationProxy.
			        getMorbidityProxy(morbReportSumVO.getObservationUid(),
			                          nbsSecurityObj);
			    morbidityProxyVOList.add(morbidityProxyVO);
			  }
			}
     }
     if (morbidityProxyVOList != null) {
			messageProcessorVO.setTheMorbidityProxyVOCollection(
			    morbidityProxyVOList);

     }
   }
   catch (CreateException ce) {
	   logger.fatal(
				  " Exception while getting ObservattionProxyEJB "+ce.getMessage());
     ce.printStackTrace();
     NNDException nnde = new NNDException(ce.getMessage() +
			  " error to create ObservationProxyEJB.");
     nnde.setModuleName(moduleName);
     throw nnde;
   }
   catch (Exception e) {
     e.printStackTrace();
     logger.fatal(
			  " Exception while getting ObservattionProxyEJB "+e.getMessage());
     NNDException nndOther = new NNDException(e.getMessage() +
			  " Exception while getting ObservationProxyEJB ");
     nndOther.setModuleName(moduleName);
     throw nndOther;
   }

   /* iterate through Investigation.VaccinationSummaryVO collection
			     for each instance of vaccinationSummaryVO, get VaccinationProxyVO by calling IntervationProxyEJB
    */
   Long interventionUid = null;
   
	Collection<Object> actRelationshipColl = investigationProxyVO.getPublicHealthCaseVO()
			.getTheActRelationshipDTCollection();

	try {
		if (actRelationshipColl != null) {
			ArrayList<Object> vaccinationProxyVOList = new ArrayList<Object>();
			InterventionProxy interventionProxy = null;
			for (Iterator<Object> anIterator = actRelationshipColl.iterator(); anIterator.hasNext();) {

				ActRelationshipDT actreleationshipDT = (ActRelationshipDT) anIterator.next();
				if (actreleationshipDT.getSourceClassCd() != null && actreleationshipDT.getSourceClassCd()
						.equals(NEDSSConstants.INTERVENTION_CLASS_CODE)) {
					interventionUid = actreleationshipDT.getSourceActUid();

					if (interventionProxy == null) {
						// System.out.println("just before calling InterventionProxyEJBRef.............
						// ");
						String sBeanJndiName = JNDINames.INTERVENTION_PROXY_EJB;
						Object object = nedssUtils.lookupBean(sBeanJndiName);
						InterventionProxyHome interventionProxyHome = (InterventionProxyHome) PortableRemoteObject
								.narrow(object, InterventionProxyHome.class);
						logger.debug("Found InterventionProxyHome: " + interventionProxyHome);
						interventionProxy = interventionProxyHome.create();
					} // end of
					vaccinationProxyVO = interventionProxy.getVaccinationProxy(interventionUid,
							nbsSecurityObj);
					if (vaccinationProxyVO.getTheMaterialVO() == null
							|| vaccinationProxyVO.getTheObservationVOCollection() == null) {

						setVaccinationValuesFromPageBuilder(vaccinationProxyVO);
					}

					vaccinationProxyVOList.add(vaccinationProxyVO);
				}
			}

			if (vaccinationProxyVOList != null) {
				messageProcessorVO.setTheVaccinationProxyVOCollection(vaccinationProxyVOList);
			}
		}
	}
    
   catch (CreateException ce) {
	   logger.fatal(" IntervationProxy.getVaccinationProxy, interventionUid = " +
	           interventionUid.toString() + ce.getMessage(),ce);
     ce.printStackTrace();
     NNDException nnde = new NNDException(ce.getMessage() +
			  " error to create InterventionProxyEJB.");
     nnde.setModuleName(moduleName);
     throw nnde;
   }
   catch (FinderException fe) {
     fe.printStackTrace();
     logger.fatal(" IntervationProxy.getVaccinationProxy, interventionUid = " +
	           interventionUid.toString() + fe.getMessage(),fe);
     NNDException nnde2 = new NNDException(fe.getMessage() +
			  " error to call IntervationProxy.getVaccinationProxy, interventionUid = " +
			  interventionUid);
     nnde2.setModuleName(moduleName);
     throw nnde2;
   }
   catch (Exception e) {
     e.printStackTrace();
     logger.fatal(" IntervationProxy.getVaccinationProxy, interventionUid = " +
			           interventionUid.toString() + e.getMessage(),e);
     NNDException nndOther = new NNDException(e.getMessage() +
			  " call IntervationProxy.getVaccinationProxy, interventionUid = " +
			                                       interventionUid);
     nndOther.setModuleName(moduleName);
     throw nndOther;
   }

   //commented as per seq diagram for rel 1.1
   //messageProcessorVO.setNotificationType(notificationDT.getCd());
   //messageProcessorVO.setNotificationLocalID(notificationDT.getLocalId());
   messageProcessorVO.setTheNotificationDT(notificationDT);

   //Get the state defined meta data and store it in the NNDLDFMarshaller
   if (nndEntityIdentifier == null)
	   getStateDefinedMetaData(nbsSecurityObj);

   //Step B, call the marchaller to build the XMLString message
   //notificationMessage = "testing message, nndObjectMarshaller is under construction!";
   try {

     NNDObjectMarshaller nndObjectMarshaller = new NNDObjectMarshaller();
     notificationMessage = nndObjectMarshaller.marshallNNDObjects(
			  messageProcessorVO, nbsSecurityObj);
   }
   catch (NNDException nnde) {
	   logger.fatal(nnde.getMessage(), nnde);
     nnde.printStackTrace();
     throw nnde;
   }
   catch (Exception se) {
	   logger.fatal(se.getMessage(), se);
     se.printStackTrace();
     NNDException nndOther = new NNDException(se.getMessage() +
			  " Exception in marshallNNDObjects, notification localId = " +
			  notificationDT.getLocalId());
     nndOther.setModuleName(moduleName);
     throw nndOther;
   }

   //step C: write to msgOut database
   msgOutMessageUid = writeToMsgOut(notificationMessage, notificationDT,
			                                  nbsSecurityObj);
}
   //step D: update notification table in ods, recordStatusCd changes from IN_BATCH_PROCESS to COMPLETE
   try {
     logger.debug(
			  "Before calling updateNotificationComplete, notification record_status_cd = " +
			  notificationDT.getRecordStatusCd());
     notificationDT.setItNew(false);
     notificationDT.setItDirty(true);
     logger.debug(
			  "before updateNotificationComplete, notification versionControlNbr= " +
			  notificationDT.getVersionCtrlNbr());
     notificationDT.setCaseClassCd(publicHealthCaseDT.getCaseClassCd());
     updateNotificationComplete(notificationDT, nbsSecurityObj);
     logger.debug(
			  "after calling updateNotificationComplete, notification record_status_cd = " +
			  notificationDT.getRecordStatusCd());
   }
   catch (NNDException nnde) {
	   logger.fatal("RecordStatusCd: " + notificationDT.getRecordStatusCd() + ", VersionCtrlNbr: " + notificationDT.getVersionCtrlNbr() + ", " + nnde.getMessage(), nnde);
     nnde.printStackTrace();
     throw nnde;
   }
   catch (Exception se) {
     se.printStackTrace();
     logger.fatal("RecordStatusCd: " + notificationDT.getRecordStatusCd() + ", VersionCtrlNbr: " + notificationDT.getVersionCtrlNbr() + ", " + se.getMessage(), se);
     NNDException nndOther = new NNDException(se.getMessage() +
			  " Exception in marshallNNDObjects, notification localId = " +
			  notificationDT.getLocalId());
     nndOther.setModuleName(moduleName);
     throw nndOther;
   }

   //Step E: add record in NNDActivityLog table in ods, where record_status_cd = "DEV_SUCCESS",
   // and status_cd = "S".
   try {
			String recordStatusCd="";
			if(notificationDT!=null && notificationDT.getNndInd() != null &&  notificationDT.getNndInd().equals(NEDSSConstants.NO))
			  recordStatusCd = NNDConstantUtil.NON_NND;
			else
			  recordStatusCd = NNDConstantUtil.NND_ACTIVITY_RECORD_STATUS_CD_DEV_SUCCESS;

     persistNNDActivityLog(notificationDT,
				  				recordStatusCd,
			                    NNDConstantUtil.NND_ACTIVITY_STATUS_CD_DEV_SUCCESS,
			                    new Long(msgOutMessageUid), null, nbsSecurityObj);
   }
   catch (NNDException nnde) {
	   logger.fatal("NndInd: " + notificationDT.getNndInd() + ", " + nnde.getMessage(), nnde);
     throw nnde;
   }

   catch (Exception e) {
     e.printStackTrace();
     logger.fatal("NndInd: " + notificationDT.getNndInd() + ", " + e.getMessage(), e);
     NNDException nndOther = new NNDException(
			  "Exception while calling persistNNDActivityLog " + e.getMessage());
     nndOther.setModuleName(moduleName);
     throw nndOther;

   }
		} catch (NEDSSSystemException e) {
			// TODO Auto-generated catch block
			logger.fatal(e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}

  }

//done till here

/**
   * @roseuid 3D6BF63D008C
   * @J2EE_METHOD  --  buildAndWriteSummaryNotificationMessage
   */
  public void buildAndWriteSummaryNotificationMessage(NotificationDT
      notificationDT,
      NBSSecurityObj nbsSecurityObj) throws NNDException,
      java.rmi.RemoteException {
    try {
		logger.debug(
		    "Before calling buildAndWriteSummaryNotificationMessage, notificationUid = " +
		    notificationDT.getNotificationUid());
		logger.debug(
		    "Before calling buildAndWriteSummaryNotificationMessage, notification record_status_cd = " +
		    notificationDT.getRecordStatusCd());
		logger.debug(
		    "Before calling buildAndWriteSummaryNotificationMessage, notification getVersionCtrlNbr = " +
		    notificationDT.getVersionCtrlNbr());
		long msgOutMessageUid = 0;
		String nndMessage = null;
		String moduleName =
		    "NNDMessageProcessorEJB.buildAndWriteSummaryNotificationMessage";
		//StepA, buildInvestigationMessage
		ActRelationshipDT actRelationshipDT = null;
		SummaryReportProxyVO summaryReportProxyVO = null;
		InvestigationProxyVO investigationProxyVO = null;
		LabResultProxyVO labResultProxyVO = null;
		VaccinationProxyVO vaccinationProxyVO = null;
		String notificationMessage = null;
		MessageProcessorVO messageProcessorVO = new MessageProcessorVO();

		try {

		  /*get ActRelationship to PublicHealthCase,
		       pass sourceActUid = NotificationUid, typeCd = "SummaryNotification"
		   */
		  ActRelationshipDAOImpl actRelationshipDAOImpl =
		      new ActRelationshipDAOImpl();
		  Collection<Object>  actRelationshipDTColl = actRelationshipDAOImpl.loadSource(
		      notificationDT.getNotificationUid().longValue(),
		      NEDSSConstants.ACT128_TYP_CD);

		  /* Business rules state One notification relates to ONLY ONE
		      phc investigation.
		   */
		  if (actRelationshipDTColl.size() != 1) {
		    NNDException nnde = new NNDException("ERROR: found 0 or more than one actRelationship between notification and investigation!");
		    nnde.setModuleName(moduleName);
		    throw nnde;
		  }
		  for (Iterator<Object> anIterator = actRelationshipDTColl.iterator();
		       anIterator.hasNext(); ) {
		    actRelationshipDT = (ActRelationshipDT) anIterator.next();

		    if (actRelationshipDT != null) {

		      break;
		    }
		  }

		  if (actRelationshipDT == null) {
		    NNDException nnde2 = new NNDException(
		        "actRelationshipDT is null! notificationUid = " +
		        notificationDT.getNotificationUid());

		    nnde2.setModuleName(moduleName);
		    throw nnde2;

		  }

		}
		catch (Exception e) {
			logger.fatal("NotificationUid: " + notificationDT.getNotificationUid() + ", " + e.getMessage(), e);
		  e.printStackTrace();
		  NNDException nndOther = new NNDException(e.getMessage() +
		      " can't find related public health case" +
		                                           notificationDT.
		                                           getNotificationUid());
		  nndOther.setModuleName(moduleName);
		  throw nndOther;
		}

		NedssUtils nedssUtils = new NedssUtils();

		/*call notificationProxyEJB to validate required fields, the return will be
		     a array of the missing fields. If there is any missing fields, throw exception
		 */
		Long publicHealthCaseUid = actRelationshipDT.getTargetActUid();
		// Create the NND message only if nnd indicator is set to Y - task#4609 - REl4.5
		if(notificationDT.getNndInd()!=null && notificationDT.getNndInd().equals(NEDSSConstants.YES)){
		// If we this is a Aggregate Summary Notification (NAGG), then we will create an NBSNNDIntermediaryMessage xml message to 
		// and populate the file system and/or the CN_transportq_out table.  Rhapsody route is then used to transform this message
		// into an HL7 2.5, pipe delimited, Summary Case notification message for transport via PHINMS to CDC. 
		if ( notificationDT.getCd() != null && notificationDT.getCd().equals(NEDSSConstants.AGGREGATE_NOTIFICATION_CD) ) { 
			String publicHealthCaseLocalId = "";
			try {
				//Get related Public Health Case DT so we can get local id for cn_transportq_out
				ActController actController = null;
				Object object = nedssUtils.lookupBean(JNDINames.ActControllerEJB);
				logger.debug("ActController lookup = " + object.toString());
				ActControllerHome actHome = (ActControllerHome) PortableRemoteObject.narrow(object,ActControllerHome.class);
				logger.debug("Found ActControllerHome: " + actHome);
				actController = actHome.create();
				PublicHealthCaseDT publicHealthCaseDT = actController.getPublicHealthCaseInfo(actRelationshipDT.getTargetActUid(), nbsSecurityObj);
				publicHealthCaseLocalId = publicHealthCaseDT.getLocalId();
			} catch (Exception e) {
				logger.fatal("Cd: " + notificationDT.getCd() + ", " + e.getMessage(), e);
				e.printStackTrace();
				NNDException nndOther = new NNDException("NNDMessageProcessorEJB.buildAndWriteSummaryNotificationMessage:  Can't find related public health case" +
														 notificationDT.getNotificationUid() + 
														 e.getMessage());
				nndOther.setModuleName(moduleName);

				throw nndOther;
			}

			
			AggregateSummaryMessageBuilder aggregateSummaryMessageBuilder= new AggregateSummaryMessageBuilder();

			NotificationConditionCodeDAOImpl notificationConditionCodeDAOImpl = new NotificationConditionCodeDAOImpl();
			TreeMap summaryConditionMap = notificationConditionCodeDAOImpl.getEntityIdentiferForNNDConditions(NEDSSConstants.SUMMARY);
			String nndSummaryEntityIdentifier = (String)summaryConditionMap.get(notificationDT.getCaseConditionCd());

			if (nndSummaryEntityIdentifier == null || nndSummaryEntityIdentifier.trim().length() < 1) {
				String errString = "NNDMessageProcessorEJB.buildAndWriteInvestigationMessage - Missing Summary Entity Identifer for Aggregate Summary Notification, notificationUid =  " + notificationDT.getNotificationUid();
				logger.error(errString);
				throw new NEDSSSystemException(errString);
			}
			
			try {
				aggregateSummaryMessageBuilder.createNotificationMessage(notificationDT, publicHealthCaseUid, publicHealthCaseLocalId, nndSummaryEntityIdentifier, nbsSecurityObj);
			} catch (NEDSSConcurrentDataException ex) { 
				logger.fatal("While trying to create Aggregate Summary Notification Message for Notification uid:  " + notificationDT.getNotificationUid() + ", the Cn_transportq_out update failed due to data concurrent exception."+ex.getMessage(), ex);
				throw new NNDException("Concurrent access occurred in NNDMessageProcessorEJB : " + ex.toString());
			}

			// Mark Notification as complete and return
			updateNotificationComplete(notificationDT, nbsSecurityObj);
			return;
		}
		
		try {

		  NotificationProxy notificationProxy = null;
		  Object object = nedssUtils.lookupBean(JNDINames.NOTIFICATION_PROXY_EJB);
		  NotificationProxyHome notificationProxyHome = (NotificationProxyHome)
		      PortableRemoteObject.narrow(
		      object,
		      NotificationProxyHome.class);
		  logger.debug(
		      "Found NotificationProxyHome: " + notificationProxyHome);
		  notificationProxy = notificationProxyHome.create();

		  Collection<Object>  missingFields = notificationProxy.
		      validateNNDSummaryRequiredFields(
		      publicHealthCaseUid,
		      nbsSecurityObj);

		  if (missingFields != null) {
		    logger.error(
		        "validateNNDSummaryRequiredFields, there are some required fields missing!");

		    for (Iterator<Object> anIterator = missingFields.iterator();
		         anIterator.hasNext(); ) {

		      String missingRequied = (String) anIterator.next();
		      logger.error("missingRequiedfield = " + missingRequied);
		    }
		    NNDException nnde = new NNDException(
		        "There are some required fields missing when validateNNDSummaryRequiredFields");
		    nnde.setModuleName(moduleName);
		    throw nnde;

		  }
		  else {
		    logger.debug(
		        "Pass validateNNDSummaryRequiredFields, no required fields missing.");
		  }

		}
		catch (CreateException ce) {
			logger.fatal("CreateException occurred, publicHealthCaseUid: " + publicHealthCaseUid + ", " + ce.getMessage(), ce);
		  ce.printStackTrace();
		  NNDException nnde2 = new NNDException(ce.getMessage() +
		      " error to create notificationProxyEJB.");
		  nnde2.setModuleName(moduleName);
		  throw nnde2;
		}
		catch (RemoteException re) {
		  logger.fatal(
		      "RemoteException was thrown from notificationProxyEJB when calling validateNNDSummaryRequiredFields, publicHealthCaseUid: " + publicHealthCaseUid + ", " + re.getMessage(), re);
		  NNDException nnde3 = new NNDException("RemoteException was thrown from notificationProxyEJB when calling validateNNDSummaryRequiredFields. " +
		                                        re.getMessage());
		  nnde3.setModuleName(moduleName);
		  throw nnde3;
		}
		catch (Exception e) {
			logger.fatal("CreateException occurred, publicHealthCaseUid: " + publicHealthCaseUid + ", " + e.getMessage(), e);
		  e.printStackTrace();
		  logger.fatal(
		      "Error calling validateNNDSummaryRequiredFields() in NotificationProxyEJB, where publicHealthCaseUid = " +
		      publicHealthCaseUid);
		  NNDException nndeOther = new NNDException(e.getMessage() + " Error calling validateNNDSummaryRequiredFields() in NotificationProxyEJB, where publicHealthCaseUid = " +
		                                            publicHealthCaseUid);

		  nndeOther.setModuleName(moduleName);
		  throw nndeOther;
		}

		//get SummaryReportProxyVO by calling InvestigationProxyEJB, and add to messageProcessorVO
		try {

		  InvestigationProxy investigationProxy = null;
		  String sBeanJndiName = JNDINames.INVESTIGATION_PROXY_EJB;
		  Object object = nedssUtils.lookupBean(sBeanJndiName);
		  InvestigationProxyHome investigationProxyHome =
		      (InvestigationProxyHome) PortableRemoteObject.narrow(object,
		      InvestigationProxyHome.class);
		  logger.debug(
		      "Found InvestigationProxyHome: " +
		      investigationProxyHome);
		  investigationProxy = investigationProxyHome.create();
		  summaryReportProxyVO = investigationProxy.getSummaryReportProxy(
		      publicHealthCaseUid, nbsSecurityObj);

		  if (summaryReportProxyVO != null) {
		    messageProcessorVO.setTheSummaryReportProxyVO(
		        summaryReportProxyVO);
		  }
		}

		catch (CreateException ce) {
		  ce.printStackTrace();
		  logger.fatal("CreateException occurred, publicHealthCaseUid: " + publicHealthCaseUid + ", " + ce.getMessage(), ce);
		  NNDException nnde = new NNDException(ce.getMessage() +
		      " error to create investigationProxyEJB.");
		  nnde.setModuleName(moduleName);

		  throw nnde;
		}

		catch (Exception e) {
		  e.printStackTrace();
		  NNDException nndeOther = new NNDException(e.getMessage() +
		      " error to create investigationProxyEJB.");
		  nndeOther.setModuleName(moduleName);
		  logger.fatal("publicHealthCaseUid: " + publicHealthCaseUid + ", " + e.getMessage(), e);
		  throw nndeOther;
		}
		//set the notificationDT before building XMLString
		messageProcessorVO.setTheNotificationDT(notificationDT);

		//Step B, call the marchaller to build the XMLString message

		try {

		  NNDObjectMarshaller nndObjectMarshaller = new NNDObjectMarshaller();
		  notificationMessage = nndObjectMarshaller.marshallNNDObjects(
		      messageProcessorVO, nbsSecurityObj);
		}
		catch (NNDException nnde) {
			logger.fatal(nnde.getMessage(), nnde);
		  throw nnde;
		}
		catch (Exception se) {
		  se.printStackTrace();
		  logger.fatal("LocalId: " + notificationDT.getLocalId() + ", " + se.getMessage(), se);
		  NNDException nndeOther = new NNDException(se.getMessage() +
		      " Exception in marshallNNDObjects, notification localId = " +
		      notificationDT.getLocalId());
		  nndeOther.setModuleName(moduleName);
		  throw nndeOther;
		}

		//step C: write to msgOut database
		msgOutMessageUid = writeToMsgOut(notificationMessage, notificationDT,
		                                      nbsSecurityObj);
		}
		//step D: update notification table in ods, recordStatusCd changes from IN_BATCH_PROCESS to COMPLETE
		try {
		  logger.debug(
		      "Before calling updateNotificationComplete, notification record_status_cd = " +
		      notificationDT.getRecordStatusCd());
		  notificationDT.setItNew(false);
		  notificationDT.setItDirty(true);
		  logger.debug(
		      "before updateNotificationComplete, notification versionControlNbr= " +
		      notificationDT.getVersionCtrlNbr());
		  updateNotificationComplete(notificationDT, nbsSecurityObj);
		  logger.debug(
		      "after calling updateNotificationComplete, notification record_status_cd = " +
		      notificationDT.getRecordStatusCd());
		}
		catch (NNDException nnde) {
			logger.fatal("RecordStatusCd: " + notificationDT.getRecordStatusCd() + ", " + nnde.getMessage(), nnde);
		  nnde.printStackTrace();
		  throw nnde;
		}

		try {
		  String recordStatusCd = "";
		  if(notificationDT!=null && notificationDT.getNndInd() != null &&  notificationDT.getNndInd().equals(NEDSSConstants.NO))
			  recordStatusCd = NNDConstantUtil.NON_NND;
		  else
			  recordStatusCd = NNDConstantUtil.NND_ACTIVITY_RECORD_STATUS_CD_DEV_SUCCESS;
		  persistNNDActivityLog(notificationDT,
				  				recordStatusCd,
		                        NNDConstantUtil.NND_ACTIVITY_STATUS_CD_DEV_SUCCESS,
		                        new Long(msgOutMessageUid), null, nbsSecurityObj);
		}
		catch (NNDException nnde) {
			logger.fatal("NndInd: " + notificationDT.getNndInd() + ", " + nnde.getMessage(), nnde);
		  throw nnde;
		}

		catch (Exception e) {
		  e.printStackTrace();
		  logger.fatal(e.getMessage(), e);
		  NNDException nndOther = new NNDException(
		      "Exception while calling persistNNDActivityLog " + e.getMessage());
		  nndOther.setModuleName(moduleName);
		  throw nndOther;

		}
	} catch (NEDSSSystemException e) {
		// TODO Auto-generated catch block
		logger.fatal(e.getMessage(), e);
		throw new NEDSSSystemException(e.getMessage(), e);
	}
  }

  private long writeToMsgOut(String nedssMessage,
                             NotificationDT notificationDT,
                             NBSSecurityObj nbsSecurityObj) throws NNDException {
    String moduleName = "NNDMessageProcessorEJB.writeToMsgOut";
    MsgOutMessageDT msgOutMessageDT = new MsgOutMessageDT();
    MsgOutReceivingFacilityDT msgOutReceivingFacilityDT =
        new MsgOutReceivingFacilityDT();
    MsgOutReceivingMessageDT msgOutReceivingMessageDT =
        new MsgOutReceivingMessageDT();
    MsgOutSendingFacilityDT msgOutSendingFacilityDT = new
        MsgOutSendingFacilityDT();

    /*because of constrains in database, data have to be populated in the tables by following sequence
             1. MsgOut_Receiving_facility
             2. MsgOut_Sending_facility
             3. MsgOut_Message
             4. MsgOut_Receiving_Message
         please refer to the design document for the required fields in each msgOut table
     */
    try {

      // step 1, populate the required fields in MsgOutReceivingFacilityDT
      msgOutReceivingFacilityDT.setNmUseCd(
          NNDConstantUtil.MSGOUT_NM_USE_CD);
      msgOutReceivingFacilityDT.setAssigningAuthorityCd(
          NNDConstantUtil.MSGOUT_RECEIVING_ASSIGNING_AUTHORITY_CD);
      msgOutReceivingFacilityDT.setAssigningAuthorityDescTxt(
          NNDConstantUtil.MSGOUT_RECEIVING_ASSIGNING_AUTHORITY_DESC_TXT);
      msgOutReceivingFacilityDT.setReceivingFacilityNm(
          NNDConstantUtil.MSGOUT_RECEIVING_ASSIGNING_AUTHORITY_DESC_TXT);
      msgOutReceivingFacilityDT.setRecordStatusCd(
          NEDSSConstants.RECORD_STATUS_ACTIVE);
      msgOutReceivingFacilityDT.setRecordStatusTime(new Timestamp(new Date().
          getTime()));
      msgOutReceivingFacilityDT.setRootExtensionTxt(
          NNDConstantUtil.MSGOUT_RECEIVING_FACILITY_ROOT_EXTENTION);
      msgOutReceivingFacilityDT.setStatusCd(
          NEDSSConstants.STATUS_ACTIVE);
      msgOutReceivingFacilityDT.setStatusTime(new Timestamp(new Date().getTime()));
      msgOutReceivingFacilityDT.setTypeCd(
          NNDConstantUtil.MSGOUT_RECEIVING_FACILITY_TYPE_CD);
      msgOutReceivingFacilityDT.setItNew(true);

      MsgOutReceivingFacilityDAOImpl msgOutReceivingFacilityDAOImpl =
          new MsgOutReceivingFacilityDAOImpl();
      long msgOutReceivingFacilityUid = msgOutReceivingFacilityDAOImpl.create(
          msgOutReceivingFacilityDT);

      // step 2, populate the required fields in MsgOutSendingFacilityDT
      String rootExtentionTxt = propertyUtil.getNNDRootExtensiontTxt();
      String sendingFacilityNm = propertyUtil.getNNDSendingFacilityNm();
      msgOutSendingFacilityDT.setRootExtensionTxt(rootExtentionTxt);
      msgOutSendingFacilityDT.setSendingFacilityNm(sendingFacilityNm);
      msgOutSendingFacilityDT.setNmUseCd(
          NNDConstantUtil.MSGOUT_NM_USE_CD);
      msgOutSendingFacilityDT.setAssigningAuthorityCd(
          NNDConstantUtil.MSG_ID_ASSIGN_AUTH_CD);
      msgOutSendingFacilityDT.setAssigningAuthorityDescTxt(
          NNDConstantUtil.MSG_ID_ASSIGN_AUTH_DESC_TXT);
      msgOutSendingFacilityDT.setRecordStatusCd(
          NEDSSConstants.RECORD_STATUS_ACTIVE);
      msgOutSendingFacilityDT.setRecordStatusTime(new Timestamp(new Date().
          getTime()));
      msgOutSendingFacilityDT.setStatusCd(
          NEDSSConstants.STATUS_ACTIVE);
      msgOutSendingFacilityDT.setTypeCd(
          NNDConstantUtil.MSGOUT_SENDING_FACILITY_TYPE_CD);
      msgOutSendingFacilityDT.setStatusTime(new Timestamp(new Date().getTime()));
      msgOutSendingFacilityDT.setItNew(true);

      MsgOutSendingFacilityDAOImpl msgOutSendingFacilityDAOImpl =
          new MsgOutSendingFacilityDAOImpl();
      long msgOutSendingFacilityUid = msgOutSendingFacilityDAOImpl.create(
          msgOutSendingFacilityDT);

      // step 3, populate the required fields in MsgOutMessageDT
      msgOutMessageDT.setVersionId(propertyUtil.getNND_MESSAGE_VERSION());
      msgOutMessageDT.setAttachmentTxt(nedssMessage);
      msgOutMessageDT.setSendingFacilityEntityUid(new Long(
          msgOutSendingFacilityUid));
      msgOutMessageDT.setAddTime(new Timestamp(new Date().getTime()));
      msgOutMessageDT.setMsgIdRootTxt(notificationDT.getLocalId());
      /* set the Interaction ID depending on autoResendInd */
      if (notificationDT.getCd().equals("NOTF")) {
        if (notificationDT.getAutoResendInd().equals(NEDSSConstants.NOTIFICATION_AUTO_RESEND_ON)) {
          msgOutMessageDT.setInteractionId(NNDConstantUtil.
                                           NND_AUTO_RESEND_INTERACTION_ID);
        }
        else if (notificationDT.getAutoResendInd().equals(NEDSSConstants.NOTIFICATION_AUTO_RESEND_OFF)) {
          msgOutMessageDT.setInteractionId(NNDConstantUtil.
                                           NND_NEW_NOT_INTERACTION_ID);
        }
        else {
          logger.error("notificationDT.getAutoResendInd() not T or F");
        }
      }
      else if (notificationDT.getCd().equals("NSUM")) {

        //set the interaction id to summary
        msgOutMessageDT.setInteractionId(NNDConstantUtil.
                                         NND_SUMMARY_INTERACTION_ID);
      }
      else {
        logger.error("notificationDT.getCd() <> NOTF or NSUM");

      }

      msgOutMessageDT.setMsgIdAssignAuthCd(
          NNDConstantUtil.MSG_ID_ASSIGN_AUTH_CD);
      msgOutMessageDT.setProcessingCd(NNDConstantUtil.
                                      NBS_PRODUCTION_PROCESSING_CODE);
      msgOutMessageDT.setProcessingDescTxt(NNDConstantUtil.
                                           NBS_PRODUCTION_PROCESSING_DESC_TXT);
      msgOutMessageDT.setMsgIdAssignAuthDescTxt(
          NNDConstantUtil.MSG_ID_ASSIGN_AUTH_DESC_TXT);
      msgOutMessageDT.setMsgIdTypeCd(NNDConstantUtil.MSG_ID_TYPE_CD);
      msgOutMessageDT.setMsgIdTypeDescTxt(
          NNDConstantUtil.MSG_ID_TYPE_DESC_TXT);
      msgOutMessageDT.setStatusCd(READY_FOR_TRANSFORM_STATUS);
      msgOutMessageDT.setStatusTime(new Timestamp(new Date().getTime()));
      msgOutMessageDT.setRecordStatusCd(
          NEDSSConstants.RECORD_STATUS_ACTIVE);
      msgOutMessageDT.setRecordStatusTime(new Timestamp(new Date().getTime()));
      msgOutMessageDT.setItNew(true);

      MsgOutMessageDAOImpl msgOutMessageDAOImpl = new MsgOutMessageDAOImpl();
      long msgOutMessageUid = msgOutMessageDAOImpl.create(
          msgOutMessageDT);

      // step4, populate the required fields in MsgOutReceivingMessageDT
      msgOutReceivingMessageDT.setReceivingFacilityEntityUid(new Long(
          msgOutReceivingFacilityUid));
      msgOutReceivingMessageDT.setMessageUid(new Long(msgOutMessageUid));
      msgOutReceivingMessageDT.setStatusCd(
          NEDSSConstants.STATUS_ACTIVE);
      msgOutReceivingMessageDT.setStatusTime(new Timestamp(new Date().getTime()));
      msgOutReceivingMessageDT.setItNew(true);

      MsgOutReceivingMessageDAOImpl msgOutReceivingMessageDAOImpl =
          new MsgOutReceivingMessageDAOImpl();
      msgOutReceivingMessageDAOImpl.create(msgOutReceivingMessageDT);
      return msgOutMessageUid;
    }
    catch (Exception daoAppException) {
    	logger.fatal("Nedss Message" + nedssMessage + "RecordStatusCd" + notificationDT.getRecordStatusCd() + ", " + daoAppException.getMessage(), daoAppException);
      daoAppException.printStackTrace();
      NNDException nnde = new NNDException("failed to write msgOut: " +
                                           daoAppException.getMessage());
      nnde.setModuleName(moduleName);
      throw nnde;
    }
  }

  public NotificationDT updateNotificationRecordToBatch(Long notificationUid,
      NBSSecurityObj nbsSecurityObj) throws NNDException,
      RemoteException {
    try {
		String moduleName =
		    "NNDMessageProcessorEJB.updateNotificationRecordToBatch";
		NotificationDT notifDT = null;
		NotificationVO notifVO = null;

		try {

		  ActController actController = null;
		  NedssUtils nedssUtils = new NedssUtils();
		  Object object = nedssUtils.lookupBean(
		      JNDINames.ActControllerEJB);
		  logger.debug("ActController lookup = " + object.toString());

		  ActControllerHome actHome = (ActControllerHome) PortableRemoteObject.
		      narrow(
		      object,
		      ActControllerHome.class);
		  logger.debug("Found ActControllerHome: " + actHome);
		  actController = actHome.create();
		  notifVO = actController.getNotification(notificationUid,
		                                              nbsSecurityObj);
		  notifDT=notifVO.getTheNotificationDT();
		  Long programJurisdictionOidFormExport =null;
		  String exportJurisdictionCd=null;
		  if(notifDT.getCd().equalsIgnoreCase(NEDSSConstants.CLASS_CD_EXP_NOTF))
		  {
		    	  programJurisdictionOidFormExport=notifDT.getProgramJurisdictionOid();
				  exportJurisdictionCd=notifDT.getJurisdictionCd();
		      if(notifVO!=null && notifVO.getTheActRelationshipDTCollection()!=null){
		    	 Iterator<Object>  it =  notifVO.getTheActRelationshipDTCollection().iterator();
		    	  while(it.hasNext()){
		    		  RetrieveSummaryVO retSummary= new RetrieveSummaryVO();
		    		  ActRelationshipDT actRelationshipDT =(ActRelationshipDT)it.next();
		    		  Long phcUid = actRelationshipDT.getTargetActUid();
		    		  Collection<Object>  NotificationSummaryVOColl =retSummary.retrieveNotificationSummaryListForInvestigation(phcUid,nbsSecurityObj);
		    		 Iterator<Object>  anIterator = 	NotificationSummaryVOColl.iterator();
		    		  while(anIterator.hasNext()){
		    			  NotificationSummaryVO newVO = (NotificationSummaryVO) anIterator.next();
		    			  if (!newVO.getCdNotif().equals(NEDSSConstants.CLASS_CD_EXP_NOTF) && !newVO.getIsHistory().equalsIgnoreCase("T")
		    					  && (newVO.getRecordStatusCd().equalsIgnoreCase(NEDSSConstants.NOTIFICATION_APPROVED_CODE)
		    							  ||newVO.getRecordStatusCd().equalsIgnoreCase(NEDSSConstants.NOTIFICATION_COMPLETED))){
		    				  if(notificationUid.compareTo(newVO.getNotificationUid())!=0){
		    					  NotificationDT updatedNotificationDT =actController.getNotificationInfo(newVO.getNotificationUid(), nbsSecurityObj);
		    					  updatedNotificationDT.setJurisdictionCd(exportJurisdictionCd);
		    					  updatedNotificationDT.setProgramJurisdictionOid(programJurisdictionOidFormExport);
		    					  updatedNotificationDT.setItDirty(true);
		    					  updatedNotificationDT.setItNew(false);
		    					  updatedNotificationDT.setRecordStatusCd(NEDSSConstants.NOTIFICATION_APPROVED_CODE);
		    					  if(newVO.getCdNotif().equals(NEDSSConstants.CLASS_CD_NOTF) || 
		    							  newVO.getCdNotif().equals(NEDSSConstants.CLASS_CD_SHARE_NOTF) ||
		    							  newVO.getCdNotif().equals(NEDSSConstants.CLASS_CD_SHARE_NOTF_PHDC))
		    						  updatedNotificationDT.setCaseClassCd(NEDSSConstants.CASE_CLASS_CODE_NOT_A_CASE);
		    					  actController.setNotificationInfo(updatedNotificationDT, nbsSecurityObj);
		    					  
		    				  }
		
		    			  }
		    		  }
		    	  }
		      }
		    }
		  //before call prepareVOUtils to update notification, should set dirty mark
		  notifDT.setItDirty(true);
		  notifDT.setItNew(false);

		  try {

		    PrepareVOUtils prepareVOUtils = new PrepareVOUtils();
		    notifDT = (NotificationDT) prepareVOUtils.prepareVO(notifDT,
		        "NOTIFICATION",
		        "NOT_BATCH",
		        "NOTIFICATION",
		        "BASE",
		        nbsSecurityObj);
		    notifDT.setItDirty(true);
		    notifDT.setItNew(false);
		  }
		  catch (Exception ae) {
		    ae.printStackTrace();
		    logger.fatal(
		        ae.getMessage() +
		        "fail to update notification to IN_BATCH_PROCESS.");
		    NNDException nnde = new NNDException(ae.getMessage() +
		        " fail to in calling PrepareVOUtil to update notification to IN_BATCH_PROCESS.");
		    nnde.setModuleName(moduleName);
		    throw nnde;
		  }

		  actController.setNotificationInfo(notifDT, nbsSecurityObj);
 
		}
		catch (CreateException ce) {
			logger.fatal(ce.getMessage(), ce);
		  ce.printStackTrace();
		  NNDException nnde = new NNDException(ce.getMessage() +
		                                       " error to create ActControllerEJB.");
		  nnde.setModuleName(moduleName);
		  throw nnde;
		}
		catch (NEDSSConcurrentDataException concurentException) {
			logger.fatal(concurentException.getMessage(), concurentException);
		  concurentException.printStackTrace();
		  NNDException nnde2 = new NNDException(concurentException.getMessage() +
		      " error to call setNotificationInfo.");

		  nnde2.setModuleName(moduleName);
		  throw nnde2;
		}
		catch (Exception e) {
		  e.printStackTrace();
		  logger.fatal(
		      e.getMessage() +
		      "fail to update notification to IN_BATCH_PROCESS.");
		  NNDException nndeOther = new NNDException(e.getMessage() +
		      " fail to in calling PrepareVOUtil to update notification to IN_BATCH_PROCESS.");
		  nndeOther.setModuleName(moduleName);
		  throw nndeOther;
		}

		logger.debug(
		    "After called ActController to set IN_BATCH versionCtlNBR = " +
		    notifDT.getVersionCtrlNbr());

		//temporary fix for data concurrency problem
		int tempVersonNbr = notifDT.getVersionCtrlNbr().intValue() + 1;
		notifDT.setVersionCtrlNbr(new Integer(tempVersonNbr));

		return notifDT;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal(e.getMessage(), e);
		throw new NEDSSSystemException(e.getMessage(), e);
	}
  }

  public void updateNotificationFailure(NotificationDT notificationDT,
                                        NBSSecurityObj nbsSecurityObj) throws
      NNDException,
      java.rmi.RemoteException {
    String moduleName = "NNDMessageProcessorEJB.updateNotificationFailure";
    logger.debug("updateNotificationFailure has been called.");

    PrepareVOUtils prepareVOUtils = new PrepareVOUtils();

    try {

      ActController actController = null;
      NedssUtils nedssUtils = new NedssUtils();
      Object object = nedssUtils.lookupBean(
          JNDINames.ActControllerEJB);
      logger.debug("ActController lookup = " + object.toString());

      ActControllerHome actHome = (ActControllerHome) PortableRemoteObject.
          narrow(
          object,
          ActControllerHome.class);
      logger.debug("Found ActControllerHome: " + actHome);
      actController = actHome.create();

      NotificationDT updateNotifDT = (NotificationDT) actController.
          getNotificationInfo(notificationDT.getNotificationUid(),
                              nbsSecurityObj);
      updateNotifDT.setItDirty(true);
      updateNotifDT.setItNew(false);

      NotificationDT notifDT = (NotificationDT) prepareVOUtils.prepareVO(
          updateNotifDT, "NOTIFICATION",
          "NOT_MSG_FAIL", "NOTIFICATION",
          "BASE", nbsSecurityObj);
      notifDT.setItDirty(true);
      notifDT.setItNew(false);
      actController.setNotificationInfo(notifDT, nbsSecurityObj);
      logger.debug(
          "after call actController to updateNotificationFailure, versionCtrlNbr = " +
          notifDT.getVersionCtrlNbr());
    }
    catch (Exception e) {
      e.printStackTrace();
      logger.fatal(e.getMessage(), e);
      NNDException nnde = new NNDException(e.getMessage() +
                                           " error to create ActControllerEJB.");
      nnde.setModuleName(moduleName);
      throw nnde;
    }
  }

  private void updateNotificationComplete(NotificationDT notificationDT,
                                          NBSSecurityObj nbsSecurityObj) throws
      NNDException {

    try {
      PrepareVOUtils prepareVOUtils = new PrepareVOUtils();
      NotificationDT notifDT = (NotificationDT) prepareVOUtils.prepareVO(
          notificationDT, "NOTIFICATION",
          "NOT_MSG_COM", "NOTIFICATION", "BASE",
          nbsSecurityObj);
      notifDT.setRptSentTime(new Timestamp(new java.util.Date().getTime()));
      notifDT.setItDirty(true);
      notifDT.setItNew(false);
      notifDT.setRptSentTime(new Timestamp(new java.util.Date().getTime()));

      //set autoresend based on Notification Cd 'NOTF' not 'NSUM'
      if(notifDT.getCd().equalsIgnoreCase(NEDSSConstants.CLASS_CD_NOTIFICATION)||
    		  notifDT.getCd().equalsIgnoreCase(NEDSSConstants.AGGREGATE_NOTIFICATION_CD)|| 
    		  notifDT.getCd().equalsIgnoreCase(NEDSSConstants.CLASS_CD_SHARE_NOTF) ||
    		  notifDT.getCd().equalsIgnoreCase(NEDSSConstants.CLASS_CD_SHARE_NOTF_PHDC) ) {
          notifDT.setAutoResendInd(NEDSSConstants.NOTIFICATION_AUTO_RESEND_ON);
      }

      ActController actController = null;
      NedssUtils nedssUtils = new NedssUtils();
      Object object = nedssUtils.lookupBean(
          JNDINames.ActControllerEJB);
      logger.debug("ActController lookup = " + object.toString());

      ActControllerHome actHome = (ActControllerHome) PortableRemoteObject.
          narrow(
          object,
          ActControllerHome.class);
      logger.debug("Found ActControllerHome: " + actHome);
      actController = actHome.create();
      actController.setNotificationInfo(notifDT, nbsSecurityObj);
    }
    catch (NEDSSConcurrentDataException ex) {
      logger.fatal("concurrent access is not allowed!"+ex.getMessage(), ex);
      NNDException nnde = new NNDException(
          "NEDSSConcurrentDataException : Concurrent access occurred  : " +
          ex.toString());
      nnde.setModuleName("NNDMessageProcessorEJB.updateNotificaitonComplete");
      throw nnde;
    }
    catch (Exception e) {
      e.printStackTrace();
      logger.fatal(e.getMessage(), e);
      NNDException nndOther = new NNDException(e.getMessage() +
          " Exception while creating ActControllerEJB ");
      nndOther.setModuleName(
          "NNDMessageProcessorEJB.updateNotificationComplete");
      throw nndOther;
    }
  }


  /**
   * This method will return a collection of TranportQOutDT objects
   * where the APPLICATONSTATUS IS NOT 'LDF_LOGGED'
   * AND the PROCESSIGNSTATUS = 'done' AND the TRANSPORTSTATUS = 'success' or TRANSPORTSTATUS = 'failure'
   * @param service String
   * @param nbsSecurityObj NBSSecurityObj
   * @throws NEDSSSystemException
   * @return Collection
   */
  public Collection<Object>  checkTransportQOutDone(String service, NBSSecurityObj nbsSecurityObj) throws NEDSSSystemException{
    try {
		if(transportQOutDao == null)
		  transportQOutDao = new TransportQOutDAOImpl();
		return transportQOutDao.selectTransportQOutDone(service);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal(e.getMessage(), e);
		throw new NEDSSSystemException(e.getMessage(), e);
	}
  }

  /**
   * This method is currently used for creating nndActivityLogs for nndLDFExtraction.
   * The code here is specific for this activity due to the usage of "newNNDActivityLogUid".
   * @param dtColl Collection
   * @param nbsSecurityObj NBSSecurityObj
   * @throws NEDSSSystemException
   */
  public void persistNNDActivity(Collection<Object> dtColl, NBSSecurityObj nbsSecurityObj) throws NEDSSSystemException {
    try {
		NNDActivityLogDAOImpl dao = new NNDActivityLogDAOImpl();
		Long newNNDActivityLogUid = null;
   Iterator<Object>  it = dtColl.iterator();
		while(it.hasNext()) {
		  NNDActivityLogDT dt = (NNDActivityLogDT)it.next();
		  if(dt.isItNew()) {
		    if(newNNDActivityLogUid == null) {
		      newNNDActivityLogUid = dao.create(dt);
		    } else {
		      dt.setNndActivityLogUid(newNNDActivityLogUid);
		      dao.create(dt);
		    }

		  } else {
		    //if the original creation failed at the "if" of this if/else, we did not place
		    //a record in the nnd activity log.  As a result, PHINMS may have
		    //picked up the representative record in transportQ_out and processed it.
		    //Therefore, we notice that the transportQ_Out record was processed on subsequent execusions
		    //of NNDLDFExtraction.bat so we attempt to udpate the record, however it is not there
		    //so we must attempt to creat it again.
		    if(!dao.updateRecordStatusCd(dt))
		      dao.create(dt);

		  }
		}//end of while
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal(e.getMessage(), e);
		throw new NEDSSSystemException(e.getMessage(), e);
	}
  }



  public void persistNNDActivityLog(NotificationDT notificationDT,
                                    String recordStatusCd, String statusCd,
                                    Long msgOutMessageUid, String errorMessage,
                                    NBSSecurityObj nbsSecurityObj) throws
      NNDException, java.rmi.RemoteException {
    try {
      System.out.println(
          "persistNNDActivityLog has been called with recordStatusCd = " +
          recordStatusCd);
      NNDActivityLogDAOImpl nndActivityLogDAOImpl = new NNDActivityLogDAOImpl();

      NNDActivityLogDT nndActivityLogDT = new NNDActivityLogDT();
      //nndActivityLogDT.setNndActivityLogUid(notificationDT.getNotificationUid());

      nndActivityLogDT.setNndActivityLogSeq(new Integer(1)); // default to 1
      nndActivityLogDT.setRecordStatusCd(recordStatusCd);
      nndActivityLogDT.setRecordStatusTime(new Timestamp(new Date().getTime()));
      nndActivityLogDT.setStatusCd(statusCd);
      nndActivityLogDT.setStatusTime(new Timestamp(new Date().getTime()));
      nndActivityLogDT.setService(propertyUtil.getNBSNNDVersion());
      if (notificationDT != null) {
        nndActivityLogDT.setLocalId(notificationDT.getLocalId());
      }
      else {
        nndActivityLogDT.setLocalId("N/A");

      }
      if (statusCd.equals(NNDConstantUtil.NND_ACTIVITY_STATUS_CD_DEV_SUCCESS)) {
    	  if(recordStatusCd!=null && recordStatusCd.equals("NON_NND"))
    		  nndActivityLogDT.setErrorMessageTxt("This notification was generated for a non-NND condition and was not sent to the CDC.");
    	  else
    		  nndActivityLogDT.setErrorMessageTxt("msgOutMessageUid = " +
                                            msgOutMessageUid.longValue());
      }
      else {
        nndActivityLogDT.setErrorMessageTxt(errorMessage);
      }
      long uid = 0; //dummy
      nndActivityLogDAOImpl.create(uid, nndActivityLogDT);
    }
    catch (Exception e) {
    	logger.fatal(e.getMessage(), e);
      e.printStackTrace();
      NNDException nndOther = new NNDException();
      nndOther.setModuleName("NNDMessageProcessorEJB.persistNNDActivityLog");
      throw nndOther;

    }

  }

    /**
     * @roseuid
     * @J2EE_METHOD -- propagateMsgOutError @ param nbsSecurityObj
     *              NBSSecurityObj
     * @return void
     * @throws NNDException
     * @throws RemoteException
     */
    public void propagateMsgOutError(NBSSecurityObj nbsSecurityObj) throws NNDException
    {
        try {
			NNDMsgOutErrorLogDAOImpl nndMsgErrLog = new NNDMsgOutErrorLogDAOImpl();
			List<Object> msgOutErrorLogDTColl = (ArrayList<Object>) nndMsgErrLog.getMsgOutStatus();

			NNDActivityLogDT nndActivityLogDT = new NNDActivityLogDT(); 
			/*
			 * iterate through MsgOutErrorLogDTColl and copy values for each
			 * MsdOutErrorLogDT to NNDActivityLogDT
			 */
			if (msgOutErrorLogDTColl != null)
			{ 
			    for (Iterator<Object> iter = msgOutErrorLogDTColl.iterator(); iter.hasNext();)
			    {
			        MsgOutErrorLogDT msgOutErrorLogDT = (MsgOutErrorLogDT) iter.next();
			        nndActivityLogDT.setLocalId(msgOutErrorLogDT.getNotificationLocalId());
			        nndActivityLogDT.setErrorMessageTxt(msgOutErrorLogDT.getErrorMessageTxt());
			        nndActivityLogDT.setRecordStatusCd(msgOutErrorLogDT.getRecordStatusCd());
			        nndActivityLogDT.setStatusCd(NNDConstantUtil.NND_ACTIVITY_STATUS_CD_DEV_ERROR);
			        nndActivityLogDT.setRecordStatusTime(msgOutErrorLogDT.getRecordStatusTime());
			        nndActivityLogDT.setStatusTime(msgOutErrorLogDT.getStatusTime());
			        nndActivityLogDT.setNndActivityLogSeq(new Integer("1"));

			        msgOutErrorLogDT.setProcessedLog("Y");
			        this.persistMsgOutErrorStatusInternal(nndActivityLogDT, msgOutErrorLogDT, nbsSecurityObj); 
			    }
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal("NumberFormatException occured, " + e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		} catch (NEDSSSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal("NEDSSSystemException occured, " + e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
    }

    private void persistMsgOutErrorStatusInternal(NNDActivityLogDT nndActivityLogDT, MsgOutErrorLogDT msgOutErrorLogDT,
            NBSSecurityObj nbsSecurityObj) throws NNDException
    {
        NNDActivityLogDAOImpl nndActivityLogDAOImpl = new NNDActivityLogDAOImpl();
        NNDMsgOutErrorLogDAOImpl nndMsgOutErrorLogDAOImpl = new NNDMsgOutErrorLogDAOImpl();

        try
        {
            long uid = 0; // dummy value
            nndActivityLogDAOImpl.create(uid, nndActivityLogDT);
            nndMsgOutErrorLogDAOImpl.updateProcessedLog(msgOutErrorLogDT); 
        }
        catch (Exception e)
        {
        	logger.fatal("nndActivityLogDT.getLocalId: " + nndActivityLogDT.getLocalId() + ", " + e.getMessage(), e);
            e.printStackTrace();
            NNDException nndException = new NNDException();
            nndException.setModuleName("persistMsgOutErrorStatusInternal");
            throw nndException;
        }
    }

    public Long setTransportQOut(TransportQOutDT dt, NBSSecurityObj nbsSecurityObj) throws NNDException
    {
        try {
			Long recordId = dt.getRecordId() != null ? dt.getRecordId() : new Long(0);
			boolean check1 = nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM, NBSOperationLookup.LDFADMINISTRATION);

			if (check1 == false)
			{
			    logger.fatal("No permission for LDF Administration");

			    throw new NEDSSSystemException("don't have permission for LDF Administration");
			}

			if (transportQOutDao == null)
			    transportQOutDao = new TransportQOutDAOImpl();

			if (dt.isItNew())
			    recordId = transportQOutDao.create(dt);
			else if (dt.isItDirty())
			    transportQOutDao.updateApplicationStatus(dt);

			return recordId;
		} catch (NEDSSSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.fatal("MessageId: " + dt.getMessageId() + ", " + e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage(), e);
		}
    }



  private void getStateDefinedMetaData (NBSSecurityObj nbsSecurityObj) throws NNDException {
    moduleName = "buildAndWriteInvestigationMessage.getStateDefinedMetaData" ;
    
    //get CDFMetaData by calling LDFSessionBeanEJB, and set it on the NNDLDFMarhaller
    try {

          boolean check1 = nbsSecurityObj.getPermission(NBSBOLookup.SYSTEM,
             NBSOperationLookup.LDFADMINISTRATION);

         if (check1 == false)
         {
            logger.fatal("No permission for LDF Administration");
            throw new NNDException("don't have permission for LDF Administration");
         }

        String jndiName = JNDINames.LDFMetaData_EJB;
        Object object = nedssUtils.lookupBean(jndiName);
        LDFMetaDataHome ldfMetaDataHome = (LDFMetaDataHome) PortableRemoteObject.narrow(object, LDFMetaDataHome.class);
        
        logger.debug("Found LDFMetaDataHome: " + ldfMetaDataHome);
        
	    LDFMetaData ldfMetaDataRemote = ldfMetaDataHome.create();
	    Collection<Object>  stateDefinedFieldMetaDataCollCDF =  ldfMetaDataRemote.getLDFMetaDataByClassCd(NNDConstantUtil.LDF_CDF_CLASSCD, nbsSecurityObj);
	    Collection<Object>  stateDefinedFieldMetaDataCollDM =  ldfMetaDataRemote.getLDFMetaDataByClassCd(NNDConstantUtil.LDF_DM_CLASSCD, nbsSecurityObj);
	    Collection<Object>  stateDefinedFieldMetaDataColl = new ArrayList<Object> ();
	    stateDefinedFieldMetaDataColl.addAll(stateDefinedFieldMetaDataCollCDF);
	    stateDefinedFieldMetaDataColl.addAll(stateDefinedFieldMetaDataCollDM);
	    stateDefinedFieldMetaDataColl = this.filterStateDefinedFieldMetaData(stateDefinedFieldMetaDataColl);
	    if (!(  stateDefinedFieldMetaDataColl == null))
	      NNDLDFMarshaller.stateDefinedFieldMetaDataDTColl = stateDefinedFieldMetaDataColl;
	    else {
	
	       NNDException nnde = new NNDException(" There is no state defined meta data  ");
	       nnde.setModuleName(moduleName);
	        throw nnde;
	    }
    }
    catch (NNDException nnde)
    {
    	logger.fatal(nnde.getMessage(), nnde);
      throw nnde;
    }
    catch (CreateException ce) {
    	logger.fatal(ce.getMessage(), ce);
      ce.printStackTrace();
      NNDException nnde = new NNDException(ce.getMessage() +
          " error while ldfMetaDataEJB.");
      nnde.setModuleName(moduleName);
      throw nnde;
    }
    catch (Exception e) {
      e.printStackTrace();
      logger.fatal(
          " Exception while getting LDFMetaData" + e.getMessage(), e);
      NNDException nndOther = new NNDException(e.getMessage() +
          " Exception while getting LDFMetaData");
      nndOther.setModuleName(moduleName);
      throw nndOther;
    }
  }

  /**
   * Filters the state defined field meta data collection on NND_IND = "Y"
   * @param metaDataColl
   * @return Collection
   * @throws NNDException
   */
  private Collection<Object>  filterStateDefinedFieldMetaData(Collection<Object> metaDataColl)
  throws NNDException
  {

    try {
		Collection<Object>  filteredStateDefinedFieldMetaDataColl = null;

		  if (metaDataColl != null)
		  {

		      filteredStateDefinedFieldMetaDataColl = new ArrayList<Object> ();
		     Iterator<Object>  itr = metaDataColl.iterator();
		      while(itr.hasNext())
		      {

		        StateDefinedFieldMetaDataDT metaDataDT =( StateDefinedFieldMetaDataDT)itr.next();
		        /*if(metaDataDT.getNndInd() == null)
		        {
		          NNDException nnde = new NNDException("StateDefinedFieldMetaDataDT.NND_IND is null " + " CLASS_CD =  "  + metaDataDT.getClassCd() );
		          nnde.setModuleName(moduleName);
		          throw nnde;

		        }*/
		        //else
		        //{
		          if (metaDataDT.getNndInd() != null && metaDataDT.getNndInd().equals(NNDConstantUtil.YES))
		            filteredStateDefinedFieldMetaDataColl.add(metaDataDT);
		        //}

		      }
		  }
		  return filteredStateDefinedFieldMetaDataColl;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.fatal(e.getMessage(), e);
		throw new NEDSSSystemException(e.getMessage(), e);
	}
  }
  
  private void updatePublicHealthCase(NotificationDT notificationDT, ActController actController, PublicHealthCaseVO phcVO, NBSSecurityObj nbsSecurityObj){
	  try {
		  phcVO.setItDirty(true);
		  phcVO.getThePublicHealthCaseDT().setJurisdictionCd(notificationDT.getJurisdictionCd());
		  phcVO.getThePublicHealthCaseDT().setProgramJurisdictionOid(notificationDT.getProgramJurisdictionOid());
		  phcVO.getThePublicHealthCaseDT().setCaseClassCd(NEDSSConstants.CASE_CLASS_CODE_NOT_A_CASE);
		  phcVO.getThePublicHealthCaseDT().setInvestigationStatusCd(NEDSSConstants.INVESTIGATION_STATUS_CODE_CLOSED);
		  actController.setPublicHealthCase(phcVO, nbsSecurityObj);
		  if (phcVO.getTheActRelationshipDTCollection() != null) {
			  Collection<Object>  actRelationShips=phcVO.getTheActRelationshipDTCollection();
			 Iterator<Object>  iterForActRelationshipDT = actRelationShips.iterator();
			  while (iterForActRelationshipDT.hasNext()) {
				  ActRelationshipDT actRelationDT = (ActRelationshipDT) iterForActRelationshipDT.next();
				  if ((actRelationDT.getTypeCd()
						  .equalsIgnoreCase("LabReport"))	|| (actRelationDT.getTypeCd()	.equalsIgnoreCase("MorbReport"))) {
					  Object objObject = nedssUtils.lookupBean(JNDINames.OBSERVATION_PROXY_EJB);
					  ObservationProxy observationProxy = null;
					  ObservationProxyHome observationProxyhome = (ObservationProxyHome) PortableRemoteObject
					  .narrow(objObject, ObservationProxyHome.class);
					  logger.debug("Found observationProxyHome: " + observationProxyhome);
					  observationProxy = observationProxyhome.create();
					  observationProxy.transferOwnership(actRelationDT.getSourceActUid(), null, notificationDT.getJurisdictionCd(),
							  NEDSSConstants.CASCADING, nbsSecurityObj);
				  }
				  else if(actRelationDT.getTypeCd().equalsIgnoreCase(NEDSSConstants.DocToPHC)){
					  Long docUid = actRelationDT.getSourceActUid();
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
							  nbsDocument.transferOwnership(docUid, null,notificationDT.getJurisdictionCd(), nbsSecurityObj);
						  }catch(Exception e){
							  logger.fatal("Error while updating the Document table" , e.getMessage(), e);
							  e.printStackTrace();
							  throw new EJBException(e.getMessage(),e);
						  }
					  }
				  }

			  }
		  }  

	  } catch (EJBException e) {
		  logger.fatal("Error:EJBException thrown:"+e.getMessage(),e);
		  throw new EJBException(e.getMessage(),e);
	  } catch (NEDSSConcurrentDataException e) {
		  logger.fatal("Error:NEDSSConcurrentDataException thrown:"+e.getMessage(),e);
		  throw new EJBException(e.getMessage(),e);
	  } catch (CreateException e) {
		  logger.fatal("Error:CreateException thrown:"+e.getMessage(),e);
		  throw new EJBException(e.getMessage(),e);
	  } catch (NEDSSSystemException e) {
		  logger.fatal("Error:NEDSSSystemException thrown:"+e.getMessage(),e);
		  throw new EJBException(e.getMessage(),e);
	  } catch (FinderException e) {
		  logger.fatal("Error:FinderException thrown:"+e.getMessage(),e);
		  throw new EJBException(e.getMessage(),e);
	  } catch (RemoteException e) {
		  logger.fatal("Error:RemoteException thrown:"+e.getMessage(),e);
		  throw new EJBException(e.getMessage(),e);
	  }
  }
  
  /** createHivPartnerServicesFile - 
   * Create the XML file of HIV cases for the specified date range for Luther Consulting Evaluation Web
   * Called from System Management/Messaging Management/Manage HIV Partner Services File HivPartnerServicesFileAction.java
   * See PSData_v2.1.xsd, PartnerServicesHelper.java, PartnerServicesUtil.java
   * @param reportingMonth = either 3 or 9 for March or Sept
   * @param reportingYear ex 2016
   * @param contactPerson - i.e. email
   * @returns byteArray (of XML document)
   */
	public byte[]  createHivPartnerServicesFile(String reportingMonth, String reportingYear, String contactPerson, String invFormCd, String ixsFormCd, NBSSecurityObj nbsSecurityObj)throws EJBException,RemoteException,NEDSSAppException {
			
			logger.info  ("NNDMessageProcessor.createHivPartnerServicesFile called with parameters: "+reportingMonth+" "+reportingYear+ " " +contactPerson + " " +invFormCd + " " +ixsFormCd);
			try {
				String defaultStateCd = propertyUtil.getNBS_STATE_CODE(); //for default state
				byte[] psXML = null;
				PartnerServicesUtil partnerServicesUtil = new PartnerServicesUtil();
				psXML = partnerServicesUtil.processPartnerServicesFileRequest(reportingMonth, reportingYear, contactPerson, invFormCd, ixsFormCd, defaultStateCd, nbsSecurityObj);
				if (psXML != null)
					logger.info("NNDMessageProcessorEJB: Returning PartnerServices XML Document. Size is:" +psXML.length);
				return psXML;
			} catch (NEDSSAppException ae) {
				logger.fatal(ae.getMessage(), ae);
				throw new NEDSSAppException(ae.getMessage(), ae);
			} catch (Exception ex) {
				logger.fatal(ex.getMessage(), ex);
				throw new NEDSSSystemException(ex.getMessage(), ex);
			}
	}
	
	public byte[]  createHivPartnerServicesFileOld(String reportingMonth, String reportingYear, String contactPerson, String invFormCd, String ixsFormCd, NBSSecurityObj nbsSecurityObj)throws EJBException,RemoteException,NEDSSAppException {
		
		logger.info  ("NNDMessageProcessor.createHivPartnerServicesFile called with parameters: "+reportingMonth+" "+reportingYear+ " " +contactPerson + " " +invFormCd + " " +ixsFormCd);
		try {
			String defaultStateCd = propertyUtil.getNBS_STATE_CODE(); //for default state
			byte[] psXML = null;
			PartnerServicesUtil partnerServicesUtil = new PartnerServicesUtil();
			psXML = partnerServicesUtil.processPartnerServicesFileRequest(reportingMonth, reportingYear, contactPerson, invFormCd, ixsFormCd, defaultStateCd, nbsSecurityObj);
			if (psXML != null)
				logger.info("NNDMessageProcessorEJB: Returning PartnerServices XML Document. Size is:" +psXML.length);
			return psXML;
		} catch (NEDSSAppException ae) {
			logger.fatal(ae.getMessage(), ae);
			throw new NEDSSAppException(ae.getMessage(), ae);
		} catch (Exception ex) {
			logger.fatal(ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.getMessage(), ex);
		}
}
	

	/**
	 * getNETSSTransportQOutDTCollection - get the NETSS case collection. Called by NETSSMessageProcessor batch program.
	 * @param mmwrYear
	 * @param mmwrWeek
	 * @param includePriorYear
	 * @param nbsSecurityObj
	 * @return NETSSTransportQOutDT collection
	 * @throws NNDException 
	 */
	public Collection<Object> getNETSSTransportQOutDTCollection(Short mmwrYear, Short mmwrWeek, Boolean includePriorYear, NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException, NEDSSSystemException {
		Collection<Object> coll = new ArrayList<Object>();

		NETSSTransportQOutDAO netssTransportQOutDAO = new NETSSTransportQOutDAO();

		try
		{
			if (mmwrYear > 2000)
				mmwrYear = (short) (mmwrYear - 2000); //Netss uses 2 digit mmwr year
			coll = netssTransportQOutDAO.getNETSSTransportQOutDTCollectionForYear(mmwrYear, mmwrWeek, includePriorYear);
}
		catch (Exception e)
		{
			logger.fatal("Exception retrieving NETSS records for " + mmwrYear + " " + mmwrWeek + " " +e.getMessage());
			e.printStackTrace();
			throw new NEDSSSystemException("Error retrieving NETSS Collection: " + e.getMessage(), e);
		}		

		return coll;
	}
	
	
	/**
	 * The way vaccination is stored in PageBuilder has changed from Legacy. For master message, we need to restructure 
	 * the data into the old format.
	 * @param vaccinationProxyVO
	 * @return
	 */
	private VaccinationProxyVO setVaccinationValuesFromPageBuilder(VaccinationProxyVO vaccinationProxyVO){
		logger.debug("in setVaccinationValuesFromPageBuilder()");

		try{
			UidGeneratorHelper uidGen = new UidGeneratorHelper();

			InterventionDT interventionDT = vaccinationProxyVO.getTheInterventionVO().getTheInterventionDT();

			// Material objects							

			// Manufactured related objects
			if (vaccinationProxyVO.getTheMaterialVO() == null) {
				vaccinationProxyVO.setTheMaterialVO(new MaterialVO());
				vaccinationProxyVO.theMaterialVO.setTheManufacturedMaterialDTCollection(new ArrayList<Object>());
				vaccinationProxyVO.theMaterialVO.setTheEntityIdDTCollection(new ArrayList<Object>());
				vaccinationProxyVO.theMaterialVO.setTheEntityLocatorParticipationDTCollection(new ArrayList<Object>());
				vaccinationProxyVO.theMaterialVO.setTheRoleDTCollection(new ArrayList<Object>());
			} 

			if (vaccinationProxyVO.theMaterialVO.getTheManufacturedMaterialDTCollection() == null) {
				vaccinationProxyVO.theMaterialVO.setTheManufacturedMaterialDTCollection(new ArrayList<Object>());
			} 

			//Add manufactured material
			ManufacturedMaterialDT manufacturedMaterialDT = new ManufacturedMaterialDT();						
			manufacturedMaterialDT.setLotNm(interventionDT.getMaterialLotNm());
			manufacturedMaterialDT.setManufacturedMaterialSeq(0);
			manufacturedMaterialDT.setExpirationTime(interventionDT.getMaterialExpirationTime());
			long materialUid = uidGen.getNbsIDLong(UidClassCodes.NBS_CLASS_CODE).longValue();
			manufacturedMaterialDT.setMaterialUid(materialUid);
			//Add associated material
			vaccinationProxyVO.getTheMaterialVO().getTheManufacturedMaterialDTCollection().add(manufacturedMaterialDT);

			String materialLocId = uidGen.getLocalID(UidClassCodes.MATERIAL_CLASS_CODE);
			MaterialDT materialDT = new MaterialDT();
			CachedDropDownValues cddv = new CachedDropDownValues();
			String vacName = "unknown";
			if (interventionDT.getMaterialCd() != null && !interventionDT.getMaterialCd().isEmpty())
				vacName = cddv.getDescForCode("VAC_NM", interventionDT.getMaterialCd());
			materialDT.setCdDescTxt(vacName);
			materialDT.setLocalId(materialLocId);
			materialDT.setMaterialUid(materialUid); //assuming same as manuf material
			materialDT.setAddReasonCd(interventionDT.getAddReasonCd());
			materialDT.setAddTime(interventionDT.getAddTime());
			materialDT.setAddUserId(interventionDT.getAddUserId());
			materialDT.setLastChgTime(interventionDT.getLastChgTime());
			materialDT.setLastChgUserId(interventionDT.getLastChgUserId());
			materialDT.setRecordStatusCd(interventionDT.getRecordStatusCd());
			materialDT.setRecordStatusTime(interventionDT.getRecordStatusTime());
			materialDT.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
			materialDT.setStatusTime(interventionDT.getRecordStatusTime());
			materialDT.setVersionCtrlNbr(interventionDT.getVersionCtrlNbr());
			materialDT.setNm(interventionDT.getMaterialCd());	
			vaccinationProxyVO.getTheMaterialVO().setTheMaterialDT(materialDT);

			//Add the participation to the Material VO
			ParticipationDT participationDT = new ParticipationDT();
			participationDT.setTypeCd("VaccGiven");
			//participationDT.setAddReasonCd("Add");
			//participationDT.setAddTime(interventionDT.getAddTime());
			//participationDT.setAddUserId(interventionDT.getAddUserId());
			//participationDT.setLastChgTime(interventionDT.getLastChgTime());
			//participationDT.setLastChgUserId(interventionDT.getLastChgUserId());
			participationDT.setSubjectClassCd(NEDSSConstants.MATERIAL_CLASS_CODE);
			participationDT.setTypeDescTxt("Vaccination (Material) Administered");
			participationDT.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
			participationDT.setStatusTime(interventionDT.getRecordStatusTime());
			participationDT.setRecordStatusCd("ACTIVE");
			participationDT.setFromTime(interventionDT.getActivityFromTime());
			participationDT.setActClassCd("INTV");
			participationDT.setActUid(interventionDT.getInterventionUid());
			participationDT.setSubjectEntityUid(vaccinationProxyVO.getTheMaterialVO().getTheMaterialDT().getMaterialUid());
			if (vaccinationProxyVO.getTheMaterialVO().getTheParticipationDTCollection() == null) {
				vaccinationProxyVO.getTheMaterialVO().setTheParticipationDTCollection(new ArrayList<Object>());
			}
			vaccinationProxyVO.getTheMaterialVO().getTheParticipationDTCollection().add(participationDT);	

			//This same participation goes at the Intervention level as well
			vaccinationProxyVO.getTheInterventionVO().getTheParticipationDTCollection().add(participationDT);



			// Set obervation values from intervention	
			//If age is null, these observations are STILL needed


			ObservationVO observationVOAge = new ObservationVO();
			vaccinationProxyVO.getTheObservationVOCollection().add(observationVOAge);
			ObservationDT theObservationDTAge = observationVOAge.getTheObservationDT();
			String ageObsLocId = uidGen.getLocalID(UidClassCodes.OBSERVATION_CLASS_CODE);
			long ageAtVacUid = uidGen.getNbsIDLong(UidClassCodes.NBS_CLASS_CODE).longValue();
			theObservationDTAge.setCd("VAC105");
			theObservationDTAge.setCdDescTxt("Age at Vaccination");
			theObservationDTAge.setCdSystemCd(interventionDT.getCdSystemCd());
			theObservationDTAge.setCdSystemDescTxt(interventionDT.getCdSystemDescTxt());
			theObservationDTAge.setLocalId(ageObsLocId);
			theObservationDTAge.setJurisdictionCd(interventionDT.getJurisdictionCd());
			theObservationDTAge.setProgramJurisdictionOid(4L); //means unknown
			theObservationDTAge.setObservationUid(ageAtVacUid);
			theObservationDTAge.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
			theObservationDTAge.setStatusTime(interventionDT.getRecordStatusTime());
			theObservationDTAge.setProgramJurisdictionOid(interventionDT.getProgramJurisdictionOid());
			theObservationDTAge.setSharedInd(interventionDT.getSharedInd());
			theObservationDTAge.setVersionCtrlNbr(interventionDT.getVersionCtrlNbr());
			theObservationDTAge.setCdVersion("1.0");
			ObsValueNumericDT obsValueNumericDT = new ObsValueNumericDT();
			obsValueNumericDT.setObservationUid(theObservationDTAge.getObservationUid());
			obsValueNumericDT.setObsValueNumericSeq(1);
			if (interventionDT.getAgeAtVacc() != null) { //could be null
				obsValueNumericDT.setNumericScale1((int)0); //no decimals
				Integer ageAtVac = interventionDT.getAgeAtVacc();
				BigDecimal ageAtVacBD = BigDecimal.valueOf(ageAtVac).stripTrailingZeros();
				obsValueNumericDT.setNumericValue1(ageAtVacBD);
			}
			if (observationVOAge.getTheObsValueNumericDTCollection() == null)
				observationVOAge.setTheObsValueNumericDTCollection(new ArrayList<Object>());
			observationVOAge.getTheObsValueNumericDTCollection().add(obsValueNumericDT);
			//add the act relationship to the Intervention 
			ActRelationshipDT ageActRelationshipDT = new ActRelationshipDT();
			ageActRelationshipDT.setTypeCd("VAC105");
			ageActRelationshipDT.setAddTime(interventionDT.getAddTime());
			ageActRelationshipDT.setLastChgTime(interventionDT.getLastChgTime());
			ageActRelationshipDT.setRecordStatusCd(interventionDT.getRecordStatusCd());
			ageActRelationshipDT.setRecordStatusTime(interventionDT.getRecordStatusTime());
			ageActRelationshipDT.setSourceClassCd("OBS");
			ageActRelationshipDT.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
			ageActRelationshipDT.setStatusTime(interventionDT.getRecordStatusTime());
			ageActRelationshipDT.setTargetClassCd("INTV");
			ageActRelationshipDT.setTypeDescTxt("Vaccination Age");
			//String localUIDi = uidGen.getLocalID(UidClassCodes.INTTERVENTION_CLASS_CODE);			
			ageActRelationshipDT.setTargetActUid(interventionDT.getInterventionUid());			
			//String localUIDo = uidGen.getLocalID(UidClassCodes.OBSERVATION_CLASS_CODE);
			ageActRelationshipDT.setSourceActUid(observationVOAge.getTheObservationDT().getObservationUid());
			if (vaccinationProxyVO.getTheInterventionVO().getTheActRelationshipDTCollection() == null)
				vaccinationProxyVO.getTheInterventionVO().setTheActRelationshipDTCollection(new ArrayList<Object>());
			vaccinationProxyVO.getTheInterventionVO().getTheActRelationshipDTCollection().add(ageActRelationshipDT);
			logger.debug("Adding OBS for VAC106");
			//add the Age Unit observation
			ObservationVO observationVOAgeUnit = new ObservationVO();
			ObservationDT theObservationDTAgeUnit = observationVOAgeUnit.getTheObservationDT();	
			vaccinationProxyVO.getTheObservationVOCollection().add(observationVOAgeUnit);
			String auObsLocalId = uidGen.getLocalID(UidClassCodes.OBSERVATION_CLASS_CODE);	
			long ageUnitAtVacUid = uidGen.getNbsIDLong(UidClassCodes.NBS_CLASS_CODE).longValue();
			theObservationDTAgeUnit.setCd("VAC106");  
			theObservationDTAgeUnit.setCdDescTxt("Age at Vaccination Units");
			theObservationDTAgeUnit.setCdSystemCd(interventionDT.getCdSystemCd());
			theObservationDTAgeUnit.setCdSystemDescTxt(interventionDT.getCdSystemDescTxt());
			theObservationDTAgeUnit.setLocalId(auObsLocalId);
			theObservationDTAgeUnit.setJurisdictionCd(interventionDT.getJurisdictionCd());
			theObservationDTAgeUnit.setProgAreaCd(interventionDT.getProgAreaCd());
			theObservationDTAge.setProgramJurisdictionOid(4L); //means unknown
			theObservationDTAgeUnit.setObservationUid(ageUnitAtVacUid);
			theObservationDTAgeUnit.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
			theObservationDTAgeUnit.setStatusTime(interventionDT.getRecordStatusTime());
			theObservationDTAgeUnit.setProgramJurisdictionOid(interventionDT.getProgramJurisdictionOid());
			theObservationDTAgeUnit.setSharedInd(interventionDT.getSharedInd());
			theObservationDTAgeUnit.setVersionCtrlNbr(interventionDT.getVersionCtrlNbr());
			theObservationDTAgeUnit.setCdVersion("1.0");
			ObsValueCodedDT auObsValueCodedDT = new ObsValueCodedDT();
			if (interventionDT.getAgeAtVacc() == null)
				auObsValueCodedDT.setCode("NI");
			else if (interventionDT.getAgeAtVaccUnitCd() == null)
				auObsValueCodedDT.setCode("Y"); //default to year if null
			else
				auObsValueCodedDT.setCode(interventionDT.getAgeAtVaccUnitCd());
			auObsValueCodedDT.setObservationUid(ageUnitAtVacUid);
			if (observationVOAgeUnit.getTheObsValueCodedDTCollection() == null)
				observationVOAgeUnit.setTheObsValueCodedDTCollection(new ArrayList<Object>());
			observationVOAgeUnit.getTheObsValueCodedDTCollection().add(auObsValueCodedDT);	
			logger.debug("Adding AR for VAC106");
			//Add the act relationship for the Age Unit
			ActRelationshipDT unitActRelationshipDT = new ActRelationshipDT();
			unitActRelationshipDT.setTypeCd("VAC105"); //This is really VAC106 but this was how it was in legacy so keeping it that way..
			unitActRelationshipDT.setAddTime(interventionDT.getAddTime());
			unitActRelationshipDT.setLastChgTime(interventionDT.getLastChgTime());
			unitActRelationshipDT.setRecordStatusCd(interventionDT.getRecordStatusCd());
			unitActRelationshipDT.setRecordStatusTime(interventionDT.getRecordStatusTime());
			unitActRelationshipDT.setSourceClassCd("OBS");
			unitActRelationshipDT.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
			unitActRelationshipDT.setStatusTime(interventionDT.getRecordStatusTime());
			unitActRelationshipDT.setTargetClassCd("INTV");
			unitActRelationshipDT.setTypeDescTxt("Vaccination Age");
			unitActRelationshipDT.setTargetActUid(interventionDT.getInterventionUid());			
			unitActRelationshipDT.setSourceActUid(observationVOAgeUnit.getTheObservationDT().getObservationUid());
			vaccinationProxyVO.getTheInterventionVO().getTheActRelationshipDTCollection().add(unitActRelationshipDT);

			if (vaccinationProxyVO.getTheInterventionVO().getTheInterventionDT().getVaccMfgrCd() != null && !vaccinationProxyVO.getTheInterventionVO().getTheInterventionDT().getVaccMfgrCd().isEmpty()) {
				logger.debug("Adding Obs for Vaccine Mfgr");
				//add the Manufacturer observation - this is a new approach
				ObservationVO observationVOVacMfgr = new ObservationVO();
				ObservationDT obsDTVacManufer = observationVOVacMfgr.getTheObservationDT();	
				vaccinationProxyVO.getTheObservationVOCollection().add(observationVOVacMfgr);
				String mfgrObsLocId = uidGen.getLocalID(UidClassCodes.OBSERVATION_CLASS_CODE);
				long mfgrUid = uidGen.getNbsIDLong(UidClassCodes.NBS_CLASS_CODE).longValue();
				obsDTVacManufer.setCd("VAC107");
				obsDTVacManufer.setCdSystemCd(interventionDT.getCdSystemCd());
				obsDTVacManufer.setCdSystemDescTxt(interventionDT.getCdSystemDescTxt());
				obsDTVacManufer.setLocalId(mfgrObsLocId);
				obsDTVacManufer.setObservationUid(mfgrUid);
				obsDTVacManufer.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
				obsDTVacManufer.setCdDescTxt("Vaccine Manufacturer");
				obsDTVacManufer.setStatusTime(interventionDT.getRecordStatusTime());
				obsDTVacManufer.setLastChgTime(interventionDT.getLastChgTime());
				obsDTVacManufer.setLastChgUserId(interventionDT.getLastChgUserId());
				obsDTVacManufer.setProgramJurisdictionOid(4L); //means unknown
				obsDTVacManufer.setSharedInd(interventionDT.getSharedInd());
				obsDTVacManufer.setVersionCtrlNbr(interventionDT.getVersionCtrlNbr());
				obsDTVacManufer.setCdVersion("1.0");
				ObsValueCodedDT obsValueCodedDT = new ObsValueCodedDT();
				if (vaccinationProxyVO.getTheInterventionVO().getTheInterventionDT().getVaccMfgrCd() != null) {
					obsValueCodedDT.setCode(vaccinationProxyVO.getTheInterventionVO().getTheInterventionDT().getVaccMfgrCd());
					String vacMfgr = cddv.getDescForCode("VAC_MFGR", obsValueCodedDT.getCode());
					if (vacMfgr != null && !vacMfgr.isEmpty())
						obsValueCodedDT.setDisplayName(vacMfgr);
				}
				if (observationVOVacMfgr.getTheObsValueCodedDTCollection() == null)
					observationVOVacMfgr.setTheObsValueCodedDTCollection(new ArrayList<Object>());
				observationVOVacMfgr.getTheObsValueCodedDTCollection().add(obsValueCodedDT);	

				//Add the act relationship for the Vaccine Manufacturer
				ActRelationshipDT vacMfgrActRelationshipDT = new ActRelationshipDT();
				vacMfgrActRelationshipDT.setTypeCd("VAC107");
				vacMfgrActRelationshipDT.setAddTime(interventionDT.getAddTime());
				vacMfgrActRelationshipDT.setLastChgTime(interventionDT.getLastChgTime());
				vacMfgrActRelationshipDT.setRecordStatusCd(interventionDT.getRecordStatusCd());
				vacMfgrActRelationshipDT.setRecordStatusTime(interventionDT.getRecordStatusTime());
				vacMfgrActRelationshipDT.setSourceClassCd("OBS");
				vacMfgrActRelationshipDT.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
				vacMfgrActRelationshipDT.setStatusTime(interventionDT.getRecordStatusTime());
				vacMfgrActRelationshipDT.setTargetClassCd("INTV");
				vacMfgrActRelationshipDT.setTypeDescTxt("Vaccine Manufacturer");

				vacMfgrActRelationshipDT.setTargetActUid(interventionDT.getInterventionUid());			

				vacMfgrActRelationshipDT.setSourceActUid(observationVOVacMfgr.getTheObservationDT().getObservationUid());
				vaccinationProxyVO.getTheInterventionVO().getTheActRelationshipDTCollection().add(vacMfgrActRelationshipDT);
			}
			/*
			 * Check the participation for SubOfVacc. In 5.2 the SubjectClassCd was changed from PSN to PAT. 
			 * We need to change it back for the Subject to show up.
			 * Also the vaccine time should be set in the from time for Subject and Performer.
			 */
			Iterator<Object>  parIterator = vaccinationProxyVO.getTheInterventionVO().getTheParticipationDTCollection().iterator();
			while (parIterator.hasNext()) {
				ParticipationDT parDT = (ParticipationDT)parIterator.next();
				if(parDT.getTypeCd().equals(NEDSSConstants.SUBJECT_OF_VACCINE) &&
						parDT.getSubjectClassCd().equals(NEDSSConstants.CLASS_CD_PAT) &&
						parDT.getRecordStatusCd().equals(NEDSSConstants.ACTIVE) &&
						parDT.getActClassCd().equals(NEDSSConstants.INTERVENTION_CLASS_CODE))
				{
					logger.debug("Updating Subject Class Code and activity from time for Subject of Vacc in participationDT");
					parDT.setSubjectClassCd(NEDSSConstants.PERSON_CLASS_CODE); //change it back from PAT to PSN
					if (parDT.getFromTime() == null && interventionDT.getActivityFromTime() != null)
						parDT.setFromTime(interventionDT.getActivityFromTime());
				}
				
				if(parDT.getTypeCd().equals(NEDSSConstants.PERFORMER_OF_VACCINE) &&
						parDT.getSubjectClassCd().equals(NEDSSConstants.PERSON_CLASS_CODE) ||
								parDT.getSubjectClassCd().equals(NEDSSConstants.ORGANIZATION_CLASS_CODE)  &&
						parDT.getRecordStatusCd().equals(NEDSSConstants.ACTIVE) &&
						parDT.getActClassCd().equals(NEDSSConstants.INTERVENTION_CLASS_CODE))
				{
					logger.debug("Updating Activity From Time for Performer of Vac (PSN or ORG)  in participationDT");
					if (parDT.getFromTime() == null && interventionDT.getActivityFromTime() != null)
						parDT.setFromTime(interventionDT.getActivityFromTime());
				}
				
				
			}
			/*
			Iterator<Object>  personIterator = vaccinationProxyVO.getThePersonVOCollection().iterator();
			while (personIterator.hasNext()) {
				PersonVO personVO = (PersonVO)personIterator.next();
			}
			*/
		}catch(Exception ex){
			logger.error("Exception in setVaccinationValuesFromPageBuilder() :"+ex.getMessage(),ex);
		}		
		logger.debug("leaving setVaccinationValuesFromPageBuilder()");
		return vaccinationProxyVO;
	}
	
	 

	/**
	 * populatePSFTables: populates the PSF tables and writes into the NBS_MSGOUTE.Activity_log table
	 * @param incrementalOrFull
	 * @param nbsSecurityObj
	 * @throws NNDException
	 * @throws java.rmi.RemoteException
	 */
	

	public String populatePSFTables(String incrementalOrFull,
												  NBSSecurityObj nbsSecurityObj) throws NNDException, java.rmi.RemoteException
			
	{
		String result="";
		
		try {
			String messageStatus ="Success";
			String messageException ="";
			
			
			PSFDAOImpl psfDaoImpl = new PSFDAOImpl();
		
			try{
				result = psfDaoImpl.populateTablesForPartnerServicesFile(incrementalOrFull);
				
				
				
			}catch(NEDSSSystemException e){
				messageStatus="Failure";
				messageException = e.getMessage();	
			}
			catch(Exception e){
				logger.error("Exception thrown at populatePSFTables:" + e.getCause()+e.getMessage(), e);
		    	e.printStackTrace();
			//	throw new NNDException	("Exception thrown at populatePSFTables:" + e.getCause()+e.getMessage());
			}
	
		}catch(Exception e){
			logger.error("Exception thrown at populatePSFTables:" + e.getCause()+e.getMessage(), e);
	    	e.printStackTrace();
		//	throw new NNDException	("Exception thrown at populatePSFTables:" + e.getCause()+e.getMessage());
		}
		
		return result;
	}
	
	
	
}
