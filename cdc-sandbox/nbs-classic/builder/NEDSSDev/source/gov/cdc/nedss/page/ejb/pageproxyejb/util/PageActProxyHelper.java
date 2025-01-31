package gov.cdc.nedss.page.ejb.pageproxyejb.util;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.rmi.PortableRemoteObject;

import gov.cdc.nedss.association.dao.ActRelationshipDAOImpl;
import gov.cdc.nedss.association.dao.ActRelationshipHistDAOImpl;
import gov.cdc.nedss.association.dao.ParticipationDAOImpl;
import gov.cdc.nedss.association.dao.ParticipationHistDAOImpl;
import gov.cdc.nedss.association.dt.ActRelationshipDT;
import gov.cdc.nedss.association.dt.ParticipationDT;
import gov.cdc.nedss.controller.ejb.entitycontrollerejb.bean.EntityController;
import gov.cdc.nedss.controller.ejb.entitycontrollerejb.bean.EntityControllerHome;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.nbsactentity.dao.NbsActEntityDAO;
import gov.cdc.nedss.nbsactentity.dao.NbsActEntityHistoryDAO;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.act.PageActProxyVO;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;

/**
 * PageActProxyHelper contains helper methods for add, edit, view functionality for common PageActProxyVO objects
 * @author Pradeep Kumar Sharma
 *
 */
public class PageActProxyHelper {
	
	static final LogUtils logger = new LogUtils(PageActProxyHelper.class.getName());

	
	/**
	 * Utility method to add, edit participation, act relationship collection and also history objects for act relationship and participations.
	 * This method also adds organization and person collection to the PageActProxyVO Object
	 */
	public static void getPageActProxyVO(PageActProxyVO pageProxyVO, Long pageActUid, NBSSecurityObj nbsSecurityObj) {
		try {
			ParticipationDAOImpl participationDAOImpl = new ParticipationDAOImpl();
			Collection<Object> participationCollection =participationDAOImpl.loadAct(pageActUid);
			Collection<Object> theOrganizationVOCollection = new ArrayList<Object>();
			NedssUtils nedssUtils = new NedssUtils();
			Collection<Object> thePersonVOCollection = new ArrayList<Object>();
			ParticipationDT participationDT = null;
			Object entityLookedUpObject;
			// Reference an Entity controller to use later
			entityLookedUpObject = nedssUtils.lookupBean(JNDINames.EntityControllerEJB);
			EntityControllerHome ecHome = (EntityControllerHome) PortableRemoteObject.narrow(entityLookedUpObject, EntityControllerHome.class);
			EntityController entityController = ecHome.create();
						
			if(participationCollection!=null) {
				Iterator<Object> participationIterator = participationCollection.iterator();
				logger.debug("ParticipationDTCollection() = "+ participationCollection);
				// Populate the Entity collections with the results
				while (participationIterator.hasNext()) {
					participationDT = (ParticipationDT) participationIterator.next();
					Long nEntityID = participationDT.getSubjectEntityUid();
					String strClassCd = participationDT.getSubjectClassCd();
					String recordStatusCd = participationDT.getRecordStatusCd();
					if (strClassCd != null
							&& strClassCd.compareToIgnoreCase(NEDSSConstants.ORGANIZATION) == 0 && recordStatusCd != null && recordStatusCd.equals(NEDSSConstants.RECORD_STATUS_ACTIVE)) {
						theOrganizationVOCollection.add(entityController.getOrganization(nEntityID, nbsSecurityObj));
						continue;
					}else if (strClassCd != null && strClassCd.compareToIgnoreCase(NEDSSConstants.PERSON) == 0 && recordStatusCd != null && recordStatusCd.equals(NEDSSConstants.RECORD_STATUS_ACTIVE)) {
						thePersonVOCollection.add(entityController.getPerson(nEntityID, nbsSecurityObj));
						continue;
					}else if (strClassCd != null && strClassCd.compareToIgnoreCase(NEDSSConstants.CLASS_CD_PAT) == 0 && recordStatusCd != null && recordStatusCd.equals(NEDSSConstants.RECORD_STATUS_ACTIVE)) {
						thePersonVOCollection.add(entityController.getPerson(nEntityID, nbsSecurityObj));
						continue;
					}
				}
			}
			pageProxyVO.setTheParticipationDTCollection(participationCollection);
			pageProxyVO.setTheOrganizationVOCollection(theOrganizationVOCollection);
			pageProxyVO.setThePersonVOCollection(thePersonVOCollection);
			
			ActRelationshipDAOImpl actRelationshipDAOImpl = new ActRelationshipDAOImpl();
			Collection<Object> theActRelationshipDTCollection = actRelationshipDAOImpl.loadSource(pageActUid);
			pageProxyVO.setTheActRelationshipDTCollection(theActRelationshipDTCollection);
			
		} catch ( NEDSSSystemException e) {
			logger.error("PageActProxyHelper.setPageActProxyVO Exception raised with NEDSSSystemException", e);
			throw new EJBException();
		} catch (RemoteException e) {
			logger.error("PageActProxyHelper.setPageActProxyVO Exception raised with RemoteException", e);
			throw new EJBException();
		} catch (CreateException e) {
			logger.error("PageActProxyHelper.setPageActProxyVO Exception raised with CreateException", e);
			throw new EJBException();
		}
	}
	
	/**
	 * Utility method for add, store, delete subview component Objects
	 * @param pageActProxyVO
	 * @param interfaceDT
	 * @param nbsSecurityObj
	 */
	public static void setPageActProxyVO(PageActProxyVO pageActProxyVO, RootDTInterface rootInterfaceDT, NBSSecurityObj nbsSecurityObj) {
	
		try {
			if(pageActProxyVO.isItDirty() || pageActProxyVO.isItDelete()) {
				try {
					if(pageActProxyVO.getTheParticipationDTCollection()!=null && pageActProxyVO.getTheParticipationDTCollection().size()>0) {
						ParticipationDAOImpl participationDAOImpl= new ParticipationDAOImpl();
						Collection<Object> oldParticipationCollection =participationDAOImpl.loadAct(rootInterfaceDT.getUid());
						if(oldParticipationCollection!=null) {
							ParticipationHistDAOImpl participationHistDAOImpl= new ParticipationHistDAOImpl();
							 Iterator<Object>  iterator = null;
						        if(oldParticipationCollection != null)
						        {
						        	iterator = oldParticipationCollection.iterator();
							        while(iterator.hasNext())
							        {
							        	ParticipationDT participationDT= (ParticipationDT)iterator.next();
							        	if(pageActProxyVO.isItDelete()) {
							        		ParticipationDT participationDT2 = (ParticipationDT)participationDT.deepCopy();
							        		if (pageActProxyVO.getInterviewVO() != null) {
							        			participationDT2.setRecordStatusCd(pageActProxyVO.getInterviewVO().getTheInterviewDT().getRecordStatusCd());
							        			participationDT2.setRecordStatusTime(pageActProxyVO.getInterviewVO().getTheInterviewDT().getRecordStatusTime());
							        			participationDT2.setLastChgUserId(pageActProxyVO.getInterviewVO().getTheInterviewDT().getLastChgUserId());
							        			participationDT2.setLastChgTime(pageActProxyVO.getInterviewVO().getTheInterviewDT().getRecordStatusTime());
							        			participationDAOImpl.store(participationDT2);
							        		} else if (pageActProxyVO.getInterventionVO() != null) {
							        			participationDT2.setRecordStatusCd(pageActProxyVO.getInterventionVO().getTheInterventionDT().getRecordStatusCd());
							        			participationDT2.setRecordStatusTime(pageActProxyVO.getInterventionVO().getTheInterventionDT().getRecordStatusTime());
							        			participationDT2.setLastChgUserId(pageActProxyVO.getInterventionVO().getTheInterventionDT().getLastChgUserId());
							        			participationDT2.setLastChgTime(pageActProxyVO.getInterventionVO().getTheInterventionDT().getRecordStatusTime());
							        			participationDAOImpl.store(participationDT2);
							        		}
							        	}else {
								        	participationDAOImpl.remove(participationDT);						        		
							        	}
										participationHistDAOImpl.insertParticipationHist(rootInterfaceDT.getVersionCtrlNbr(), participationDT);
							        }//end of while
						        }//end of if
							//participationHistDAOImpl.store(oldParticipationCollection);
						}
					}
				} catch (NEDSSSystemException e) {
					logger.error("PageActProxyHelper.setPageActProxyVO, pageActProxyVO.isItDirty() || pageActProxyVO.isItDelete() is true");
					logger.error("PageActProxyHelper.setPageActProxyVO, storing old participation objects");
					logger.error("PageActProxyHelper.setPageActProxyVO Exception raised with exceptions for pageActProxyVO.isItDirty() || pageActProxyVO.isItDelete()" , e);
					throw new EJBException();
				}
				if(pageActProxyVO.getTheActRelationshipDTCollection()!=null && pageActProxyVO.getTheActRelationshipDTCollection().size()>0) {
					try {
						ActRelationshipDAOImpl arDaoImpl= new ActRelationshipDAOImpl();
						Collection<Object> oldArDTCollection = arDaoImpl.load(rootInterfaceDT.getUid());
						if(oldArDTCollection!=null) {
							if(pageActProxyVO.isItDelete()) {
								Iterator<Object> iterator = pageActProxyVO.getTheActRelationshipDTCollection().iterator();
								while(iterator.hasNext()) {
									ActRelationshipDT arDT = (ActRelationshipDT)iterator.next();
									arDT.setRecordStatusCd(pageActProxyVO.getInterviewVO().getTheInterviewDT().getRecordStatusCd());
					        		arDT.setRecordStatusTime(pageActProxyVO.getInterviewVO().getTheInterviewDT().getRecordStatusTime());
					        		arDT.setLastChgUserId(pageActProxyVO.getInterviewVO().getTheInterviewDT().getLastChgUserId());
					        		arDT.setLastChgTime(pageActProxyVO.getInterviewVO().getTheInterviewDT().getRecordStatusTime());

					        		arDaoImpl.store(arDT);
								}
					    	}else {
								arDaoImpl.removeAllActRelationships(rootInterfaceDT.getUid());
							}

							ActRelationshipHistDAOImpl arHistDaoImpl= new ActRelationshipHistDAOImpl();
							arHistDaoImpl.store(oldArDTCollection);
						}
					} catch (NEDSSSystemException e) {
						logger.error("PageActProxyHelper.setPageActProxyVO, pageActProxyVO.isItDirty() || pageActProxyVO.isItDelete() is true");
						logger.error("PageActProxyHelper.setPageActProxyVO, storing old actRelationship objects");
							logger.error("PageActProxyHelper.setPageActProxyVO Exception raised with exceptions", e);
						throw new EJBException();
					}
				}
			}
			if (pageActProxyVO.isItDirty() || pageActProxyVO.isItNew()) {
				if (pageActProxyVO.getTheParticipationDTCollection() != null
						&& pageActProxyVO.getTheParticipationDTCollection()
								.size() > 0) {
					try {
						ParticipationDAOImpl participationDAOImpl = new ParticipationDAOImpl();
						participationDAOImpl.store(pageActProxyVO
								.getTheParticipationDTCollection(),
								rootInterfaceDT);

					} catch (NEDSSSystemException e) {
						logger.error("PageActProxyHelper.setPageActProxyVO, pageActProxyVO.getTheParticipationDTCollection()!=null && pageActProxyVO.getTheParticipationDTCollection().size()>0 is true");
						logger.error(
								"PageActProxyHelper.setPageActProxyVO Exception raised while storing participationDT Objects",
								e);
						throw new EJBException();
					}
				}
				if (pageActProxyVO.getTheActRelationshipDTCollection() != null
						&& pageActProxyVO.getTheActRelationshipDTCollection()
								.size() > 0) {
					try {
						ActRelationshipDAOImpl arDaoImpl = new ActRelationshipDAOImpl();
						Iterator<Object> iterator = pageActProxyVO
								.getTheActRelationshipDTCollection().iterator();
						while (iterator.hasNext()) {
							ActRelationshipDT arDT = (ActRelationshipDT) iterator
									.next();
							if (arDT.getSourceActUid().compareTo(new Long(1)) < 0) {
								arDT.setSourceActUid(rootInterfaceDT.getUid());
							}
							if (pageActProxyVO.isItDelete())
								arDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_LOGICAL_DELETE);
							arDaoImpl.store(arDT);
						}
					} catch (NEDSSSystemException e) {
						logger.error("PageActProxyHelper.setPageActProxyVO, pageActProxyVO.getTheActRelationshipDTCollection()!=null && pageActProxyVO.getTheActRelationshipDTCollection().size()>0 is true");
						logger.error(
								"PageActProxyHelper.setPageActProxyVO Exception raised while storing ActRelationshipDT Objects",
								e);
						throw new EJBException();
					}
				}
				/*if(pageActProxyVO.getPageVO()!=null && pageActProxyVO.getPageVO().getActEntityDTCollection()!=null) {
					try {
						NbsActEntityHistoryDAO nbsActEntityHistoryDAO =  new NbsActEntityHistoryDAO();
						Collection<Object> actEntityCollection  = pageActProxyVO.getPageVO().getActEntityDTCollection();
						NbsActEntityDAO nbsActEntityDAO =   new NbsActEntityDAO();
						Collection<Object> oldActEntityCollection = nbsActEntityDAO.getActEntityDTCollection(rootInterfaceDT.getUid());
						if(oldActEntityCollection!=null) {
							nbsActEntityHistoryDAO.insertPamEntityHistoryDTCollection(oldActEntityCollection,rootInterfaceDT );
						}
						nbsActEntityDAO.storeActEntityDTCollection(actEntityCollection, rootInterfaceDT);
					} catch (NEDSSSystemException e) {
						logger.error("PageActProxyHelper.setPageActProxyVO, pageActProxyVO.getPageVO()!=null && pageActProxyVO.getPageVO().getActEntityDTCollection()!=null is true");
						logger.error("PageActProxyHelper.setPageActProxyVO Exception raised while storing actEntityHistory OR ActEntity Objects ", e);
						throw new EJBException();
					}
				}*/
			}
		} catch (ClassNotFoundException e) {
			logger.error("PageActProxyHelper.setPageActProxyVO, setPageActProxyVO ClassNotFoundException thrown");
			logger.error("PageActProxyHelper.setPageActProxyVO ClassNotFoundException raised ", e);
			throw new EJBException();
		} catch (CloneNotSupportedException e) {
			logger.error("PageActProxyHelper.setPageActProxyVO, setPageActProxyVO CloneNotSupportedException thrown");
			logger.error("PageActProxyHelper.setPageActProxyVO CloneNotSupportedException raised ", e);
			throw new EJBException();
		} catch (IOException e) {
			logger.error("PageActProxyHelper.setPageActProxyVO, setPageActProxyVO IOException thrown");
			logger.error("PageActProxyHelper.setPageActProxyVO IOException raised ", e);
			throw new EJBException();
		}
	}

	public static Map<Object, Object> countInvestigationAssociationWithEvent(long actUid, String typeCd, NBSSecurityObj nbsSecurityObj) throws NEDSSSystemException{
		Map<Object, Object> returnMap = new HashMap<Object, Object>();
		logger.debug("actUid : "+actUid+ ", typeCd: "+typeCd);
		if(NEDSSConstants.VACCINATION_BUSINESS_OBJECT_TYPE.equals(typeCd)){
			if (!nbsSecurityObj.getPermission(NBSBOLookup.INTERVENTIONVACCINERECORD, NBSOperationLookup.DELETE)) {
				logger.fatal("InterventionUtil.deleteintervention =nbsSecurityObj.getPermission(NedssBOLookup.intervention ,NBSOperationLookup.DELETE) is false");
				throw new NEDSSSystemException("DELETE intervention access denied. NO PERMISSIONS to DELETE intervention Object.");
			}
		}
		try{
			ActRelationshipDAOImpl actRelationshipDAOImpl = new ActRelationshipDAOImpl();
			Collection<Object> arDTCollection = actRelationshipDAOImpl.loadSource(actUid);
			if(arDTCollection!=null && arDTCollection.size()>0){
				logger.debug("arDTCollection size: "+arDTCollection.size());
				returnMap.put(NEDSSConstants.CASE, new Integer(arDTCollection.size()));
			}
		}catch(Exception ex){
			logger.error("PageActProxyHelper.countInvestigationAssociationWithEvent "+ex.getMessage(), ex);
			throw new NEDSSSystemException();
		}
		return returnMap;
	}
}
