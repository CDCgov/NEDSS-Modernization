package gov.cdc.nedss.page.ejb.pageproxyejb.util;

import gov.cdc.nedss.act.intervention.dt.InterventionDT;
import gov.cdc.nedss.act.intervention.vo.InterventionVO;
import gov.cdc.nedss.association.dao.ParticipationDAOImpl;
import gov.cdc.nedss.association.dao.ParticipationHistoryManager;
import gov.cdc.nedss.association.dt.ParticipationDT;
import gov.cdc.nedss.controller.ejb.actcontrollerejb.bean.ActController;
import gov.cdc.nedss.controller.ejb.actcontrollerejb.bean.ActControllerHome;
import gov.cdc.nedss.controller.ejb.entitycontrollerejb.bean.EntityController;
import gov.cdc.nedss.controller.ejb.entitycontrollerejb.bean.EntityControllerHome;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.nbsactentity.dt.NbsActEntityDT;
import gov.cdc.nedss.nnd.helper.NNDMessageSenderHelper;
import gov.cdc.nedss.page.ejb.pageproxyejb.dao.AnswerRootDAOImpl;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.PageProxyVO;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.act.PageActProxyVO;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.page.PageVO;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dao.NbsDocumentDAOImpl;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dt.EDXEventProcessDT;
import gov.cdc.nedss.systemservice.ejb.nbssecurityejb.helper.NBSAuthHelper;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.util.NEDSSDAOFactory;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.rmi.PortableRemoteObject;

public class InterventionUtil {
	static final LogUtils logger = new LogUtils(InterventionUtil.class.getName());

	/**
	 * 
	 * @param interventionUid
	 * @param nbsSecurityObj
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws NEDSSSystemException
	 */
	public PageProxyVO getPageProxyVO(long interventionUid,NBSSecurityObj nbsSecurityObj) throws java.rmi.RemoteException,
	NEDSSSystemException {

		if (!nbsSecurityObj.getPermission(NBSBOLookup.INTERVENTIONVACCINERECORD, NBSOperationLookup.VIEW)) {
			logger.fatal("InterventionUtil.getPageProxyVO =nbsSecurityObj.getPermission(NedssBOLookup.intervention ,NBSOperationLookup.VIEW) is false");
			throw new NEDSSSystemException("View intervention access denied. NO PERMISSIONS to view intervention Object.");
		}
		logger.info("nbsSecurityObj.getPermission(NedssBOLookup.intervention,NBSOperationLookup.VIEW) is true");
		PageActProxyVO pageProxyVO = new PageActProxyVO();

		InterventionVO theInterventionVO=null;
		NedssUtils nedssUtils = new NedssUtils();
		Object actLookedUpObject;

		try {
			logger.debug("InterventionUtil.getPageProxyVO: before nedssUtils.lookupBean(JNDINames.ActControllerEJB)");
			// Reference an Act controller to use later
			actLookedUpObject = nedssUtils.lookupBean(JNDINames.ActControllerEJB);
			ActControllerHome actHome = (ActControllerHome) PortableRemoteObject.narrow(actLookedUpObject, ActControllerHome.class);
			ActController actController = actHome.create();

			theInterventionVO = actController.getIntervention(interventionUid, nbsSecurityObj);
			NBSAuthHelper helper = new NBSAuthHelper();
			//theInterventionVO.getTheInterventionDT().setAddUserName(helper.getUserName(theInterventionVO.getTheInterventionDT().getAddUserId()));
			//theInterventionVO.getTheInterventionDT().setLastChgUserName(helper.getUserName(theInterventionVO.getTheInterventionDT().getLastChgUserId()));

			logger.debug("InterventionUtil.getPageProxyVO: successful lookup of intervention DT Object.");

			AnswerRootDAOImpl answerRootDAO = new AnswerRootDAOImpl();
			PageVO pageVO = answerRootDAO.get(interventionUid);
			
			pageProxyVO.setPageVO(pageVO);
			pageProxyVO.setInterventionVO(theInterventionVO);
			
			PageActProxyHelper.getPageActProxyVO(pageProxyVO, interventionUid, nbsSecurityObj);
		} catch (Exception e) {
			logger.fatal("Exception is "+ e);
			String errorMessage =e.getCause()+ e.getMessage();
			logger.error("InterventionUtil.getPageProxyVO:-Exception There is an error while getting pageActProxyVO:", errorMessage);
			throw new java.rmi.RemoteException(e.toString());
		}
		return pageProxyVO;
	}

/**
 * 
 * @param interventionCaseUid
 * @param nbsSecurityObj
 * @return
 * @throws RemoteException
 * @throws CreateException
 * @throws NEDSSConcurrentDataException
 */
	public Map<Object, Object> deleteIntervention(Long interventionUid, NBSSecurityObj nbsSecurityObj) throws RemoteException, CreateException, NEDSSConcurrentDataException {
		Map<Object, Object> returnMap = new HashMap<Object, Object>();
		NedssUtils nedssUtils = new NedssUtils();
		ActController actController = null;
		if (!nbsSecurityObj.getPermission(NBSBOLookup.INTERVENTIONVACCINERECORD, NBSOperationLookup.DELETE)) {
			logger.fatal("InterventionUtil.deleteintervention =nbsSecurityObj.getPermission(NedssBOLookup.intervention ,NBSOperationLookup.DELETE) is false");
			throw new NEDSSSystemException("DELETE intervention access denied. NO PERMISSIONS to DELETE intervention Object.");
		}
		try {
			Object object = nedssUtils.lookupBean(JNDINames.ActControllerEJB);
			logger.debug("InterventionUtil.deleteintervention ActController lookup = " + object.toString());
			ActControllerHome acthome = (ActControllerHome) PortableRemoteObject.narrow(object, ActControllerHome.class);
			logger.debug("InterventionUtil.deleteinterventionFound ActControllerHome: " + acthome);
			try {
				actController = acthome.create();
			} catch (RemoteException e1) {
				logger.fatal("InterventionUtil.deleteinterventionProxy RemoteException thrown"+ e1);
				throw new RemoteException();
			} catch (CreateException e1) {
				logger.fatal("InterventionUtil.deleteinterventionProxy CreateException thrown"+ e1);
				throw new CreateException();
			}

			PageActProxyVO pageActProxyVO  = null;
			try {
				pageActProxyVO = (PageActProxyVO)getPageProxyVO(interventionUid, nbsSecurityObj);
			} catch (RemoteException e1) {
				logger.fatal("InterventionUtil.deleteintervention RemoteException thrown"+ e1);
				throw new RemoteException();
			} catch (NEDSSSystemException e1) {
				logger.fatal("InterventionUtil.deleteinterventionProxy NEDSSSystemException thrown"+ e1);
				throw new NEDSSSystemException();
			}
			java.util.Date dateTime = new java.util.Date();
			Timestamp lastChgTime = new Timestamp(dateTime.getTime());
			
			try {
				InterventionDT interventionDT = pageActProxyVO.getInterventionVO().getTheInterventionDT();
				AnswerRootDAOImpl answerRootDAOImpl = new AnswerRootDAOImpl();
				interventionDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_LOGICAL_DELETE);
				interventionDT.setRecordStatusTime(lastChgTime);
				interventionDT.setLastChgTime(lastChgTime);
				answerRootDAOImpl.logDelete(interventionDT);
				interventionDT.setItDelete(false);
				interventionDT.setItDirty(true);
				pageActProxyVO.setItDirty(true);
				pageActProxyVO.getInterventionVO().setItDirty(true);
				actController.setIntervention(pageActProxyVO.getInterventionVO(), nbsSecurityObj);
				
				// Delete Patient
				Object lookedUpObj = nedssUtils.lookupBean(JNDINames.EntityControllerEJB);
				EntityControllerHome ecHome = (EntityControllerHome) PortableRemoteObject.narrow(lookedUpObj, EntityControllerHome.class);
				EntityController entityController = ecHome.create();
				Long personUid = getPersionUidToDelete(pageActProxyVO);
				PersonVO personVO = entityController.getPatientRevision(personUid,nbsSecurityObj);
				personVO.setItDirty(true);
				personVO.getThePersonDT().setItDirty(true);
				String businessTriggerCd = NEDSSConstants.PAT_DEL;
				personUid = entityController.setPatientRevision(personVO,businessTriggerCd, nbsSecurityObj);
				
				PageActProxyHelper.setPageActProxyVO(pageActProxyVO,pageActProxyVO.getInterventionVO().getTheInterventionDT(), nbsSecurityObj);
				
			} catch (NEDSSSystemException e1) {logger.fatal("InterventionUtil.deleteinterventionProxy NEDSSSystemException thrown"+ e1);
			throw new NEDSSSystemException();
			} catch (NEDSSConcurrentDataException e1) {logger.fatal("InterventionUtil.deleteinterventionProxy NEDSSConcurrentDataException thrown"+ e1);
			throw new NEDSSConcurrentDataException();
			} catch (NEDSSAppException e1) {logger.fatal("InterventionUtil.deleteinterventionProxy NedssApp thrown"+ e1);
			throw new NEDSSSystemException();
			} catch (EJBException e1) {
				logger.fatal("InterventionUtil.deleteinterventionProxy EJBException thrown"+ e1);
				throw new RemoteException();
			}
		} catch (NEDSSSystemException e) {
			logger.fatal("InterventionUtil.deleteintervention NEDSSSystemException thrown ", e);
			throw new RemoteException();
		} catch (ClassCastException e) {
			logger.fatal("InterventionUtil.deleteintervention ClassCastException thrown ", e);
			throw new RemoteException();
		}
		return returnMap;
	}

	/**
	 * 
	 * @param pageProxyVO
	 * @param nbsSecurityObj
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws NEDSSConcurrentDataException
	 * @throws CreateException
	 */
	public Long setPageActProxyVO(PageProxyVO pageProxyVO, NBSSecurityObj nbsSecurityObj) throws RemoteException,NEDSSConcurrentDataException, CreateException {
		PageActProxyVO pageActProxyVO = (PageActProxyVO) pageProxyVO;
		Long actualUid =null;
		InterventionDT interventionDT = pageActProxyVO.getInterventionVO().getTheInterventionDT();
		// if both are false throw exception
		if ((!pageActProxyVO.isItNew()) && (!pageActProxyVO.isItDirty())) {
			logger.fatal("InterventionUtil.setPageActProxyVO:-pageProxyVO.isItNew() = " + pageActProxyVO.isItNew()+" and pageProxyVO.isItDirty() = "+ pageActProxyVO.isItDirty());
			throw new NEDSSSystemException("InterventionUtil.setPageActProxyVO:- pageProxyVO.isItNew() = "+ pageActProxyVO.isItNew()+ " and pageProxyVO.isItDirty() = "+ pageActProxyVO.isItDirty() + " for setinterventionProxy");
		}
		logger.info("InterventionUtil.setPageActProxyVO:-pageProxyVO.isItNew() = " + pageActProxyVO.isItNew()+ " and pageProxyVO.isItDirty() = "+ pageActProxyVO.isItDirty());

		if (pageActProxyVO.isItNew()) {
			logger.info("InterventionUtil.setPageActProxyVO:-pageProxyVO.isItNew() = " + pageActProxyVO.isItNew()+ " and pageProxyVO.isItDirty() = "+ pageActProxyVO.isItDirty());
		}
		boolean addPermission=nbsSecurityObj.getPermission(NBSBOLookup.INTERVENTIONVACCINERECORD, NBSOperationLookup.ADD);
		boolean editPermission=nbsSecurityObj.getPermission(NBSBOLookup.INTERVENTIONVACCINERECORD, NBSOperationLookup.EDIT);
		if(pageActProxyVO.isItNew() && !addPermission) {
			String errString ="InterventionUtil.setPageActProxyVO:-user does not have permission to add intervention record!!! Please check addPermission:-"+addPermission;
			logger.fatal(errString);
			throw new NEDSSSystemException(errString +"\nInterventionUtil.setPageActProxyVO:-pageProxyVO.isItNew() = "+ pageActProxyVO.isItNew()+ " and intervention add permission= "+ addPermission);
		}else if(pageActProxyVO.isItDirty() && !editPermission) {
			String errString ="InterventionUtil.setPageActProxyVO:-user does not have permission to update intervention record!!! Please check editPermission:-"+editPermission;
			logger.fatal(errString);
			throw new NEDSSSystemException(errString+"\nInterventionUtil.setPageActProxyVO:-pageProxyVO.isItDirty() = "+ pageActProxyVO.isItDirty()+ " and intervention add permission= "+ editPermission);
		}
		try {
			NedssUtils nedssUtils = new NedssUtils();
			ActController actController = null;
			Object object = nedssUtils.lookupBean(JNDINames.ActControllerEJB);
			logger.debug("InterventionUtil.setPageActProxyVO:-ActController lookup = " + object.toString());
			ActControllerHome acthome = (ActControllerHome) PortableRemoteObject.narrow(object, ActControllerHome.class);
			logger.debug("InterventionUtil.setPageActProxyVO:-Found ActControllerHome: " + acthome);
			actController = acthome.create();
			InterventionVO interventionVO = pageActProxyVO.getInterventionVO();
			Long falseUid=interventionVO.getTheInterventionDT().getInterventionUid();
			
			actualUid = actController.setIntervention(interventionVO, nbsSecurityObj);
			if(pageProxyVO.getInterventionVO().getTheInterventionDT().getInterventionUid()==null || (pageProxyVO.getInterventionVO().getTheInterventionDT().getInterventionUid().compareTo(new Long(1))<0)) {
				pageProxyVO.getInterventionVO().getTheInterventionDT().setInterventionUid(actualUid);
				interventionDT.setInterventionUid(actualUid);
			}
			
			Long patientRevisionID = createPatient(pageActProxyVO, nbsSecurityObj);
			setNbsActEntityIds(pageActProxyVO, patientRevisionID, interventionDT.getInterventionUid());
			
			//Set interventionId for create
			if (falseUid.intValue() < 0) {
				setParticipationUids(pageActProxyVO, actualUid, falseUid);
			}
			
			AnswerRootDAOImpl answerRootDAOImpl = new AnswerRootDAOImpl();
			if(pageActProxyVO.isItDirty()) {
				PageVO pageVO=(PageVO)pageActProxyVO.getPageVO();
				answerRootDAOImpl.store(pageVO, interventionDT);
			}else {
				PageVO pageVO=(PageVO)pageActProxyVO.getPageVO();
				answerRootDAOImpl.insertPageVO(pageVO, interventionDT);
			}
			
			//createParticipations(pageActProxyVO, nbsSecurityObj);
			
			PageActProxyHelper.setPageActProxyVO(pageActProxyVO, pageProxyVO.getInterventionVO().getTheInterventionDT(), nbsSecurityObj);

			if(!pageActProxyVO.isConversionHasModified()){
				NNDMessageSenderHelper nndMessageSenderHelper= NNDMessageSenderHelper.getInstance();
			    if(nndMessageSenderHelper.checkForExistingNotificationsByCdsAndUid(NEDSSConstants.CLASS_CD_INTV,NEDSSConstants.TYPE_CD,interventionDT.getInterventionUid()));
			    	try{
			    		nndMessageSenderHelper.updateAutoResendNotificationsByCdsAndUid(NEDSSConstants.CLASS_CD_INTV,NEDSSConstants.TYPE_CD,interventionDT.getInterventionUid(), nbsSecurityObj);
			    	}catch(Exception ex){
			    		logger.error("Exception while calling updateAutoResendNotificationsByCdsAndUid :"+ex.getMessage(),ex);
			    	}
			}
		} catch (NEDSSConcurrentDataException ex) {
			logger.fatal("Exception is "+ ex);
			logger.fatal("InterventionUtil.setPageActProxyVO:-The entity cannot be updated as concurrent access is not allowed!");
			logger.fatal(nbsSecurityObj.getFullName(), ex.getMessage(), ex);
			throw new NEDSSConcurrentDataException("Concurrent access occurred in interventionProxyEJB : "+ ex.toString());
		} catch (Exception e) {
			logger.fatal("Exception is "+ e);
			logger.fatal("InterventionUtil.setPageActProxyVO:-The entity cannot be updated. Please check Exception!");
			logger.fatal(nbsSecurityObj.getFullName(), e.getMessage(), e);
			throw new javax.ejb.EJBException("InterventionUtil.setPageActProxyVO:- Exception thrown: "+ e.toString());
		}
	return actualUid;
	}

	private Long createPatient(PageActProxyVO pageActProxyVO, NBSSecurityObj nbsSecurityObj){
		Long patientRevisionUid=null;
		try{
			EntityController entityController = null;
			NedssUtils nedssUtils = new NedssUtils();
			Object obj = nedssUtils.lookupBean(JNDINames.EntityControllerEJB);
			logger.debug("EntityController lookup = " + obj.toString());
			EntityControllerHome home = (EntityControllerHome) PortableRemoteObject
					.narrow(obj, EntityControllerHome.class);
			logger.debug("Found EntityControllerHome: " + home);
			entityController = home.create();
			
			if (pageActProxyVO.getThePersonVOCollection() != null) {
				//Iterator<Object> anIterator = null;
				for (Iterator<Object> anIterator = pageActProxyVO.getThePersonVOCollection()
						.iterator(); anIterator.hasNext();) {
					PersonVO personVO = (PersonVO) anIterator.next();
					if (personVO.getThePersonDT().getCd()!=null 
							&& personVO.getThePersonDT().getCd().equals(NEDSSConstants.PAT)) {
						Long mprUid=personVO.getThePersonDT().getPersonParentUid();
					}
					logger.debug("The Base personDT is :"+ personVO.getThePersonDT());
					logger.debug("The personUID is :"+ personVO.getThePersonDT().getPersonUid());

					if (personVO.isItNew()) {
						if (personVO.getThePersonDT().getCd().equals(NEDSSConstants.PAT)){
							patientRevisionUid = entityController.setPatientRevision(personVO, NEDSSConstants.PAT_CR,nbsSecurityObj);
							
							Long falseUid = personVO.getThePersonDT().getPersonUid();
							logger.debug("the  falsePersonUid is " + falseUid);
							logger.debug("The realUid of Patientis: "+ patientRevisionUid);
							// replace the falseId with the realId
							if (falseUid.intValue() < 0) {
								logger.debug("the Value for false uid for Patient is being set here");
								setParticipationUids(pageActProxyVO, patientRevisionUid, falseUid);
							}
						}
					}else if(personVO.isItDirty()){
						if (personVO.getThePersonDT().getCd().equals(
								NEDSSConstants.PAT)) {
							String businessTriggerCd = NEDSSConstants.PAT_EDIT;
							try {
								patientRevisionUid = entityController.setPatientRevision(personVO, businessTriggerCd,nbsSecurityObj);
							} catch (NEDSSConcurrentDataException ex) {
								logger.fatal("The entity cannot be updated as concurrent access is not allowed!");
								throw new NEDSSConcurrentDataException("Concurrent access occurred in PageProxyEJB : "+ ex.getMessage(), ex);
							} catch (Exception ex) {
								logger.fatal("Error in executing entityController.setPatientRevision when personVO.isDirty is true"+ex.getMessage(), ex);
								throw new javax.ejb.EJBException("Error in entityController.setPatientRevision : "+ ex.toString());
							}
						}
					}
				}
			}
			
		}catch(Exception ex){
			logger.fatal("createPatient Exception is "+ ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.getMessage(), ex);
		}
		return patientRevisionUid;
	}


	private void setParticipationUids(PageActProxyVO pageActProxyVO, Long actualUid, Long falseUid){
		try{
			Collection<Object> participationColl = pageActProxyVO.getTheParticipationDTCollection();
			ParticipationDT participationDT = null;
			if (participationColl != null) {
				for (Iterator<Object> anIterator = participationColl.iterator(); anIterator.hasNext();) {
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
		}catch(Exception ex){
			logger.fatal("setNbsActEntityIds Exception is "+ ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.getMessage(), ex);
		}
	}
	
	/*private void createParticipations(PageActProxyVO pageActProxyVO, NBSSecurityObj nbsSecurityObj){
		try{
			if (pageActProxyVO.getTheParticipationDTCollection() != null) {
				ParticipationDAOImpl participationDAOImpl = null;
				participationDAOImpl = (ParticipationDAOImpl) NEDSSDAOFactory
						.getDAO(JNDINames.ACT_PARTICIPATION_DAO_CLASS);
				logger
						.debug("got the participation Collection<Object>  Loop got the DAO");
				for (Iterator<Object>  anIterator = pageActProxyVO
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
							ParticipationHistoryManager phm = new ParticipationHistoryManager(
									participationDT.getSubjectEntityUid().longValue(), participationDT.getActUid()
											.longValue(), participationDT.getTypeCd());
							phm.store(participationDT);

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
		}catch(Exception ex){
			
		}
	}*/
	
	private void setNbsActEntityIds(PageActProxyVO pageActProxyVO, Long patientRevisionId, Long interventionId){
		try{
			Collection<Object> nbsActEntityColl = pageActProxyVO.getPageVO().getActEntityDTCollection();
			if (nbsActEntityColl != null) {
				for (Iterator<Object> anIterator = nbsActEntityColl.iterator(); anIterator.hasNext();) {
						NbsActEntityDT nbsActEntityDT = (NbsActEntityDT) anIterator.next();
						if(NEDSSConstants.SUBJECT_OF_VACCINE.equals(nbsActEntityDT.getTypeCd()) && patientRevisionId!=null){
							nbsActEntityDT.setEntityUid(patientRevisionId);
						}
						nbsActEntityDT.setActUid(interventionId);
				}
			}
		}catch(Exception ex){
			logger.fatal("setNbsActEntityIds Exception is "+ ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.getMessage(), ex);
		}
	}
	
	private Long getPersionUidToDelete(PageActProxyVO pageActProxyVO){
		Long personUid = null;
		try{
			if (pageActProxyVO.getPageVO().getActEntityDTCollection() != null) {
				for (Iterator<Object> anIterator = pageActProxyVO.getPageVO().getActEntityDTCollection().iterator(); anIterator.hasNext();) {
					NbsActEntityDT nbsActEntityDT = (NbsActEntityDT) anIterator.next();
					if (nbsActEntityDT!=null && NEDSSConstants.SUBJECT_OF_VACCINE.equals(nbsActEntityDT.getTypeCd())) {
						personUid = nbsActEntityDT.getEntityUid();
						return personUid;
					}
				}
			}
		}catch(Exception ex){
			logger.fatal("getPersionUidToDelete Exception is "+ ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.getMessage(), ex);
		}
		return personUid;
	}

}
