package gov.cdc.nedss.systemservice.ejb.edxdocumentejb.beanhelper;

import gov.cdc.nedss.act.notification.dt.NotificationDT;
import gov.cdc.nedss.act.notification.vo.NotificationVO;
import gov.cdc.nedss.act.publichealthcase.vo.PublicHealthCaseVO;
import gov.cdc.nedss.association.dt.ActRelationshipDT;
import gov.cdc.nedss.entity.entityid.dt.EntityIdDT;
import gov.cdc.nedss.entity.person.dt.PersonDT;
import gov.cdc.nedss.entity.person.dt.PersonEthnicGroupDT;
import gov.cdc.nedss.entity.person.dt.PersonNameDT;
import gov.cdc.nedss.entity.person.dt.PersonRaceDT;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.locator.dt.EntityLocatorParticipationDT;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.act.PageActProxyVO;
import gov.cdc.nedss.proxy.ejb.notificationproxyejb.vo.NotificationProxyVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.LabResultProxyVO;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.vo.PamProxyVO;
import gov.cdc.nedss.systemservice.dt.EDXActivityDetailLogDT;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dt.EdxRuleAlgorothmManagerDT;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.labutil.EdxELRConstants;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.util.EdxPHCRConstants;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.util.EdxPHCRDocumentUtil;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSOperationLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.NEDSSConstants;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.ejb.EJBException;

/**
 * This is a helper class where common methods for PHCR and ELR can be pooled.
 * @author Pradeep Kuamr Sharma
 *
 */
public class EdxCommonHelper {

	public static void createNotification
	(
		EdxRuleAlgorothmManagerDT edxRuleAlgorithmManagerDT, 
		Object pageObj, 
		NBSSecurityObj nbsSecurityObj
	) throws Exception, NEDSSAppException, RemoteException {
		EDXActivityDetailLogDT edxActivityDetailLogDT;
		try{
		boolean authorized = nbsSecurityObj.getPermission(NBSBOLookup.NOTIFICATION, NBSOperationLookup.CREATE); // ADD CREATE AUTOCREATE
		if (authorized) {
			edxActivityDetailLogDT = sendNotification(pageObj, edxRuleAlgorithmManagerDT.getNndComment(), nbsSecurityObj);
			if (edxActivityDetailLogDT!=null && edxActivityDetailLogDT.getLogType().equals(EdxRuleAlgorothmManagerDT.STATUS_VAL.Failure.name())) {
				edxRuleAlgorithmManagerDT.setErrorText(EdxPHCRConstants.SUM_MSG_MISSING_FLDS+EdxPHCRConstants.NOT_STR);
				edxRuleAlgorithmManagerDT.setStatus(EdxRuleAlgorothmManagerDT.STATUS_VAL.Failure);
			}
		} else {									
			edxRuleAlgorithmManagerDT.setStatus(EdxRuleAlgorothmManagerDT.STATUS_VAL.Failure);
			edxRuleAlgorithmManagerDT.setErrorText(EdxPHCRConstants.SUM_MSG_NOTIFICATION_FAIL);
			edxActivityDetailLogDT = new EDXActivityDetailLogDT();
			String uid = nbsSecurityObj.getTheUserProfile().getTheUser().getUserID();
			edxActivityDetailLogDT.setRecordId(uid);
			edxActivityDetailLogDT.setRecordType(EdxPHCRConstants.MSG_TYPE.Notification.name());
			edxActivityDetailLogDT.setRecordName("PHCR_IMPORT");
			edxActivityDetailLogDT.setLogType(EdxRuleAlgorothmManagerDT.STATUS_VAL.Failure.name());
			edxActivityDetailLogDT.setComment(EdxPHCRConstants.DET_MSG_NOT_AUTH_1+uid+EdxPHCRConstants.DET_MSG_NOT_AUTH_2+EdxPHCRConstants.NOT_STR);
		}
		edxRuleAlgorithmManagerDT.getEdxActivityLogDT().getEDXActivityLogDTWithVocabDetails().add(edxActivityDetailLogDT);
		}
		catch(Exception ex){
			String errortext = "Exception while creating notification for PHCUid: "+edxRuleAlgorithmManagerDT.getPHCUid()+" Message: "+ex.getMessage();
			ex.printStackTrace();
			throw new NEDSSAppException(errortext,ex);
		}
	}
	public static EDXActivityDetailLogDT sendNotification(Object pageObj, String nndComment, NBSSecurityObj nbsSecurityObj) throws EJBException, RemoteException, NEDSSAppException {
		NotificationProxyVO notProxyVO = null;
		// Create the Notification object
		PublicHealthCaseVO publicHealthCaseVO;
		if (pageObj instanceof PageActProxyVO) {
			publicHealthCaseVO = ((PageActProxyVO) pageObj).getPublicHealthCaseVO();
		} else if (pageObj instanceof PamProxyVO) {
			publicHealthCaseVO = ((PamProxyVO) pageObj).getPublicHealthCaseVO();
		} else if (pageObj instanceof PublicHealthCaseVO) {
			publicHealthCaseVO = ((PublicHealthCaseVO) pageObj);
		} else {
			throw new NEDSSAppException("Cannot create Notification for unknown page type: " + pageObj.getClass().getCanonicalName());
		}
		NotificationDT notDT = new NotificationDT();
		notDT.setItNew(true);
		notDT.setNotificationUid(new Long(-1));
		notDT.setAddTime(new java.sql.Timestamp(new Date().getTime()));
		notDT.setTxt(nndComment);
		notDT.setStatusCd("A");
		notDT.setCaseClassCd(publicHealthCaseVO.getThePublicHealthCaseDT().getCaseClassCd());
		notDT.setStatusTime(new java.sql.Timestamp(new Date().getTime()));
		notDT.setVersionCtrlNbr(new Integer(1));
		notDT.setSharedInd("T");
		notDT.setCaseConditionCd(publicHealthCaseVO.getThePublicHealthCaseDT().getCd());
		notDT.setAutoResendInd("F");

		NotificationVO notVO = new NotificationVO();
		notVO.setTheNotificationDT(notDT);
		notVO.setItNew(true);

		// create the act relationship between the phc & notification
		ActRelationshipDT actDT1 = new ActRelationshipDT();
		actDT1.setItNew(true);
		actDT1.setTargetActUid(publicHealthCaseVO.getThePublicHealthCaseDT().getPublicHealthCaseUid());
		actDT1.setSourceActUid(notDT.getNotificationUid());
		actDT1.setAddTime(new java.sql.Timestamp(new Date().getTime()));
		actDT1.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
		actDT1.setSequenceNbr(new Integer(1));
		actDT1.setStatusCd("A");
		actDT1.setStatusTime(new java.sql.Timestamp(new Date().getTime()));
		actDT1.setTypeCd(NEDSSConstants.ACT106_TYP_CD);
		actDT1.setSourceClassCd(NEDSSConstants.ACT106_SRC_CLASS_CD);
		actDT1.setTargetClassCd(NEDSSConstants.ACT106_TAR_CLASS_CD);

		notProxyVO = new NotificationProxyVO();
		notProxyVO.setItNew(true);
		notProxyVO.setThePublicHealthCaseVO(publicHealthCaseVO);
		notProxyVO.setTheNotificationVO(notVO);

		ArrayList<Object> actRelColl = new ArrayList<Object>();
		actRelColl.add(0, actDT1);
		notProxyVO.setTheActRelationshipDTCollection(actRelColl);

		EDXActivityDetailLogDT eDXActivityDetailLogDT = EdxPHCRDocumentUtil.sendProxyToEJB(notProxyVO, pageObj, nbsSecurityObj);
		return eDXActivityDetailLogDT;
	}

	
	

	/**
	 * updatePersonELRPHDCUpdate: this is a common method used from ELR and PHDC to prepare the update to the patient revision.
	 * It is expecting to receive the personVO.
	 * 
	 * @param labResultProxyVO
	 * @param matchedLabResultProxyVO
	 */
	
	public void updatePersonELRPHDCUpdate(PersonVO currentPersonPatientVO, PersonVO personPatientMatchedVO){
		PersonDT matchedPersonDT = null;
		Long matchedPersonUid = null;
		Long matchedPersonParentUid = null;
		String matchedLocalId = null;
		Integer matchedVersionCtNo = null;
		Collection<Object> updatedPersonNameCollection = new ArrayList<Object>();
		Collection<Object> updatedPersonRaceCollection = new ArrayList<Object>();
		Collection<Object> updatedPersonEthnicGroupCollection = new ArrayList<Object>();
		Collection<Object> updatedtheEntityLocatorParticipationDTCollection  = new ArrayList<Object>();
		Collection<Object> updatedtheEntityIdDTCollection = new ArrayList<Object>();
		HashMap<Object,Object> hm = new HashMap<Object,Object>();
		HashMap<Object,Object> ethnicGroupHm = new HashMap<Object,Object>();
		int nameSeq=0;
		int entityIdSeq=0;
		
		
	
	
				if(personPatientMatchedVO!=null){
					matchedPersonDT = personPatientMatchedVO.getThePersonDT();
				
					matchedPersonUid=matchedPersonDT.getPersonUid();
					matchedPersonParentUid = matchedPersonDT.getPersonParentUid();
					matchedLocalId = matchedPersonDT.getLocalId();
					matchedVersionCtNo = matchedPersonDT.getVersionCtrlNbr();
					
				}
				
				if(personPatientMatchedVO!=null){
				if(personPatientMatchedVO.getThePersonNameDTCollection()!=null && personPatientMatchedVO.getThePersonNameDTCollection().size()>0){
					for (Iterator<Object> it = personPatientMatchedVO.getThePersonNameDTCollection().iterator(); it.hasNext();)
			        {
						PersonNameDT personNameDT = (PersonNameDT)it.next();
						personNameDT.setItDelete(true);
						personNameDT.setItDirty(false);
						personNameDT.setItNew(false);
						if(personNameDT.getPersonNameSeq()>nameSeq)
							nameSeq = personNameDT.getPersonNameSeq();
						updatedPersonNameCollection.add(personNameDT);
			        }
				}
				if(personPatientMatchedVO.getThePersonRaceDTCollection()!=null && personPatientMatchedVO.getThePersonRaceDTCollection().size()>0){
					for (Iterator<Object> it = personPatientMatchedVO.getThePersonRaceDTCollection().iterator(); it.hasNext();)
			        {
						PersonRaceDT personRaceDT = (PersonRaceDT)it.next();
						personRaceDT.setItDelete(true);
						personRaceDT.setItDirty(false);
						personRaceDT.setItNew(false);
						hm.put(personRaceDT.getRaceCd(), personRaceDT);
						updatedPersonRaceCollection.add(personRaceDT);
			        }
				}
				if(personPatientMatchedVO.getThePersonEthnicGroupDTCollection()!=null && personPatientMatchedVO.getThePersonEthnicGroupDTCollection().size()>0){
					for (Iterator<Object> it = personPatientMatchedVO.getThePersonEthnicGroupDTCollection().iterator(); it
							.hasNext();) {
						PersonEthnicGroupDT personEthnicGroupDT = (PersonEthnicGroupDT) it.next();
						personEthnicGroupDT.setItDelete(true);
						personEthnicGroupDT.setItDirty(false);
						personEthnicGroupDT.setItNew(false);
						ethnicGroupHm.put(personEthnicGroupDT.getEthnicGroupCd(), personEthnicGroupDT);
						updatedPersonEthnicGroupCollection.add(personEthnicGroupDT);

					}
				}
				if(personPatientMatchedVO.getTheEntityIdDTCollection()!=null && personPatientMatchedVO.getTheEntityIdDTCollection().size()>0){
					for (Iterator<Object> it = personPatientMatchedVO.getTheEntityIdDTCollection().iterator(); it.hasNext();)
			        {
						EntityIdDT entityIDDT = (EntityIdDT)it.next();
					
						entityIDDT.setItDelete(true);
						entityIDDT.setItDirty(false);
						entityIDDT.setItNew(false);
						if(entityIDDT.getEntityIdSeq()>entityIdSeq)
							entityIdSeq = entityIDDT.getEntityIdSeq();
						updatedtheEntityIdDTCollection.add(entityIDDT);
						
			        }
				}
				if(personPatientMatchedVO.getTheEntityLocatorParticipationDTCollection()!=null && personPatientMatchedVO.getTheEntityLocatorParticipationDTCollection().size()>0){
					for (Iterator<Object> it = personPatientMatchedVO.getTheEntityLocatorParticipationDTCollection().iterator(); it.hasNext();)
			        {
						EntityLocatorParticipationDT entityLocPartDT = (EntityLocatorParticipationDT)it.next();
					
						entityLocPartDT.setItDelete(true);
						entityLocPartDT.setItDirty(false);
						entityLocPartDT.setItNew(false);
						
					if(entityLocPartDT.getThePostalLocatorDT()!= null){
						entityLocPartDT.getThePostalLocatorDT().setItDelete(true);
						entityLocPartDT.getThePostalLocatorDT().setItDirty(false);
						entityLocPartDT.getThePostalLocatorDT().setItNew(false);
					}if(entityLocPartDT.getTheTeleLocatorDT()!= null){
						entityLocPartDT.getTheTeleLocatorDT().setItDelete(true);
						entityLocPartDT.getTheTeleLocatorDT().setItDirty(false);
						entityLocPartDT.getTheTeleLocatorDT().setItNew(false);
					}if(entityLocPartDT.getThePhysicalLocatorDT()!= null){	
						entityLocPartDT.getThePhysicalLocatorDT().setItDelete(true);
						entityLocPartDT.getThePhysicalLocatorDT().setItDirty(false);
						entityLocPartDT.getThePhysicalLocatorDT().setItNew(false);
					}
						updatedtheEntityLocatorParticipationDTCollection.add(entityLocPartDT);
						
			        }
				}
			 }
			
		
				
		
		if(currentPersonPatientVO!=null){
	
		
		
				//	personVO.getThePersonDT().setPersonUid(matchedPersonUid);
				currentPersonPatientVO.getThePersonDT().setPersonParentUid(matchedPersonParentUid);
				currentPersonPatientVO.getThePersonDT().setLocalId(matchedLocalId);
				currentPersonPatientVO.getThePersonDT().setVersionCtrlNbr(matchedVersionCtNo);
				currentPersonPatientVO.getThePersonDT().setItDirty(true);
				currentPersonPatientVO.getThePersonDT().setItNew(false);
					//personVO.getThePersonDT().setFirstNm(updatedFirstNm);
					//personVO.getThePersonDT().setLastNm(updatedLastNm);
				//	personVO.setIsExistingPatient(true);
				currentPersonPatientVO.setItNew(false);
				currentPersonPatientVO.setItDirty(true);
					//labResultProxyVO.setItDirty(true);
				//	labResultProxyVO.setItNew(false);
					
					
						if(currentPersonPatientVO.getThePersonNameDTCollection()!=null && currentPersonPatientVO.getThePersonNameDTCollection().size()>0){
							for (Iterator<Object> it = currentPersonPatientVO.getThePersonNameDTCollection().iterator(); it.hasNext();)
					        {
								PersonNameDT personNameDT = (PersonNameDT)it.next();
								personNameDT.setItNew(true);
								personNameDT.setItDirty(false);
								personNameDT.setItDelete(false);
								personNameDT.setPersonUid(matchedPersonUid);
								personNameDT.setPersonNameSeq(++nameSeq);
					        }
						}
						if(	currentPersonPatientVO.getThePersonNameDTCollection() == null)
							currentPersonPatientVO.setThePersonNameDTCollection(new ArrayList<Object>());
						currentPersonPatientVO.getThePersonNameDTCollection().addAll(updatedPersonNameCollection);
						
						if(currentPersonPatientVO.getThePersonRaceDTCollection()!=null && currentPersonPatientVO.getThePersonRaceDTCollection().size()>0){
							for (Iterator<Object> it = currentPersonPatientVO.getThePersonRaceDTCollection().iterator(); it.hasNext();) {
								PersonRaceDT personRaceDT = (PersonRaceDT) it.next();
								if (hm.get(personRaceDT.getRaceCd()) != null) {
									personRaceDT.setItDirty(true);
									personRaceDT.setItNew(false);
									personRaceDT.setItDelete(false);
									personRaceDT.setPersonUid(matchedPersonUid);
								} else {
									personRaceDT.setItNew(true);
									personRaceDT.setItDirty(false);
									personRaceDT.setItDelete(false);
									personRaceDT.setPersonUid(matchedPersonUid);
	
								}
							}
						}
						Collection<Object> personRaceCollection = currentPersonPatientVO.getThePersonRaceDTCollection();
						    Iterator it2 = hm.entrySet().iterator();
						    boolean found = false;
						    while (it2.hasNext()) {
						        Map.Entry pair = (Map.Entry)it2.next();
						        PersonRaceDT personRaceDT2 = (PersonRaceDT) pair.getValue();
						        if(personRaceCollection!=null && personRaceCollection.size()>0){
							        for (Iterator<Object> it = personRaceCollection.iterator(); it.hasNext();) {
										PersonRaceDT personRaceDT = (PersonRaceDT) it.next();
										if(personRaceDT2.getRaceCd().equals(personRaceDT.getRaceCd())){
											found = true;
											break;
										}
							        }
						        }
						        if(!found){
						        	personRaceDT2.setItDelete(true);
									personRaceDT2.setItDirty(false);
									personRaceDT2.setItNew(false);
									//Updated to cover the scenario that getThePersonRaceDTCollection is null
									if(currentPersonPatientVO.getThePersonRaceDTCollection()!=null)
										currentPersonPatientVO.getThePersonRaceDTCollection().add(personRaceDT2);
						        }
						    }
						
						
						if(	currentPersonPatientVO.getThePersonRaceDTCollection() == null || (	currentPersonPatientVO.getThePersonRaceDTCollection() != null && 	currentPersonPatientVO.getThePersonRaceDTCollection().size() == 0)){
							currentPersonPatientVO.setThePersonRaceDTCollection(new ArrayList<Object>());
							currentPersonPatientVO.getThePersonRaceDTCollection().addAll(updatedPersonRaceCollection);
						}
						if(currentPersonPatientVO.getThePersonEthnicGroupDTCollection()!=null && currentPersonPatientVO.getThePersonEthnicGroupDTCollection().size()>0){
							for (Iterator<Object> it = currentPersonPatientVO.getThePersonEthnicGroupDTCollection().iterator(); it
									.hasNext();) {
								PersonEthnicGroupDT personEthnicGroupDT = (PersonEthnicGroupDT) it.next();
	
								if (ethnicGroupHm.get(personEthnicGroupDT.getEthnicGroupCd()) != null) {
									personEthnicGroupDT.setItDirty(true);
									personEthnicGroupDT.setItNew(false);
									personEthnicGroupDT.setItDelete(false);
									personEthnicGroupDT.setPersonUid(matchedPersonUid);
								} else {
									personEthnicGroupDT.setItNew(true);
									personEthnicGroupDT.setItDirty(false);
									personEthnicGroupDT.setItDelete(false);
									personEthnicGroupDT.setPersonUid(matchedPersonUid);
	
								}
							}
						}
						if(	currentPersonPatientVO.getThePersonEthnicGroupDTCollection() == null || (	currentPersonPatientVO.getThePersonEthnicGroupDTCollection() != null && 	currentPersonPatientVO.getThePersonEthnicGroupDTCollection().size() == 0)){
							currentPersonPatientVO.setThePersonEthnicGroupDTCollection(new ArrayList<Object>());
							currentPersonPatientVO.getThePersonEthnicGroupDTCollection().addAll(updatedPersonEthnicGroupCollection);
						}
						if(currentPersonPatientVO.getTheEntityIdDTCollection()!=null && currentPersonPatientVO.getTheEntityIdDTCollection().size()>0){
							for (Iterator<Object> it = currentPersonPatientVO.getTheEntityIdDTCollection().iterator(); it.hasNext();)
					        {
								EntityIdDT entityIDDT = (EntityIdDT)it.next();
							
								entityIDDT.setItNew(true);
								entityIDDT.setItDirty(false);
								entityIDDT.setItDelete(false);
								entityIDDT.setEntityUid(matchedPersonUid);
								entityIDDT.setEntityIdSeq(++entityIdSeq);
								
					        }
						}
						if(	currentPersonPatientVO.getTheEntityIdDTCollection() == null)
							currentPersonPatientVO.setTheEntityIdDTCollection(new ArrayList<Object>());
						currentPersonPatientVO.getTheEntityIdDTCollection().addAll(updatedtheEntityIdDTCollection);
						
						
						if(currentPersonPatientVO.getTheEntityLocatorParticipationDTCollection()!=null  && currentPersonPatientVO.getTheEntityLocatorParticipationDTCollection().size()>0){
							for (Iterator<Object> it = currentPersonPatientVO.getTheEntityLocatorParticipationDTCollection().iterator(); it.hasNext();)
					        {
								EntityLocatorParticipationDT entityLocPartDT = (EntityLocatorParticipationDT)it.next();
							
								entityLocPartDT.setItNew(true);
								entityLocPartDT.setItDirty(false);
								entityLocPartDT.setItDelete(false);
								entityLocPartDT.setEntityUid(matchedPersonUid);
								
								if(entityLocPartDT.getThePostalLocatorDT()!= null){
									entityLocPartDT.getThePostalLocatorDT().setItNew(true);
									entityLocPartDT.getThePostalLocatorDT().setItDirty(false);
									entityLocPartDT.getThePostalLocatorDT().setItDelete(false);
								}if(entityLocPartDT.getTheTeleLocatorDT()!= null){
									entityLocPartDT.getTheTeleLocatorDT().setItNew(true);
									entityLocPartDT.getTheTeleLocatorDT().setItDirty(false);
									entityLocPartDT.getTheTeleLocatorDT().setItDelete(false);
								}if(entityLocPartDT.getThePhysicalLocatorDT()!= null){	
									entityLocPartDT.getThePhysicalLocatorDT().setItNew(true);
									entityLocPartDT.getThePhysicalLocatorDT().setItDirty(false);
									entityLocPartDT.getThePhysicalLocatorDT().setItDelete(false);
								}
								
					        }
						}
						if(	currentPersonPatientVO.getTheEntityLocatorParticipationDTCollection() == null)
							currentPersonPatientVO.setTheEntityLocatorParticipationDTCollection(new ArrayList<Object>());
						currentPersonPatientVO.getTheEntityLocatorParticipationDTCollection().addAll(updatedtheEntityLocatorParticipationDTCollection);
						
					
						
						currentPersonPatientVO.setRole(null);
				
			
				
			}
			
			
		}

	
				
				

	
	public void setPersonUIDOnUpdate(Long aPersonUid, PersonVO personVO) {

		if(personVO!=null){
					personVO.setItDirty(true);
					personVO.setItNew(false);
					personVO.getThePersonDT().setPersonUid(aPersonUid);
					personVO.getThePersonDT().setItDirty(true);
					personVO.getThePersonDT().setItNew(false);
					personVO.setRole(null);
		}
		
	}
	
	
	
	
	
}
