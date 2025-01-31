package gov.cdc.nedss.systemservice.ejb.edxdocumentejb.labutil;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.rmi.PortableRemoteObject;

import org.apache.xmlbeans.XmlObject;
import org.w3c.dom.Node;

import gov.cdc.nedss.act.actid.dt.ActIdDT;
import gov.cdc.nedss.act.observation.dt.EDXDocumentDT;
import gov.cdc.nedss.act.observation.dt.ObservationDT;
import gov.cdc.nedss.act.observation.ejb.dao.ObservationDAOImpl;
import gov.cdc.nedss.act.observation.helper.ObservationProcessor;
import gov.cdc.nedss.act.observation.vo.ObservationVO;
import gov.cdc.nedss.association.dao.RoleDAOImpl;
import gov.cdc.nedss.association.dt.ActRelationshipDT;
import gov.cdc.nedss.association.dt.ParticipationDT;
import gov.cdc.nedss.association.dt.RoleDT;
import gov.cdc.nedss.elr.ejb.msginprocessor.helper.ELRConstants;
import gov.cdc.nedss.entity.entityid.dt.EntityIdDT;
import gov.cdc.nedss.entity.material.vo.MaterialVO;
import gov.cdc.nedss.entity.organization.vo.OrganizationVO;
import gov.cdc.nedss.entity.person.dt.PersonDT;
import gov.cdc.nedss.entity.person.dt.PersonEthnicGroupDT;
import gov.cdc.nedss.entity.person.dt.PersonNameDT;
import gov.cdc.nedss.entity.person.dt.PersonRaceDT;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.exception.NEDSSAppConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.locator.dt.EntityLocatorParticipationDT;
import gov.cdc.nedss.phdc.ContainerDocument;
import gov.cdc.nedss.phdc.HL7LabReportType;
import gov.cdc.nedss.phdc.HL7MSHType;
import gov.cdc.nedss.phdc.HL7OrderObservationType;
import gov.cdc.nedss.phdc.HL7PATIENTRESULTType;
import gov.cdc.nedss.phdc.HL7PatientResultSPMType;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.bean.ObservationProxy;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.bean.ObservationProxyHome;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.LabResultProxyVO;
import gov.cdc.nedss.systemservice.dao.NbsInterfaceDAOImpl;
import gov.cdc.nedss.systemservice.dt.EDXActivityDetailLogDT;
import gov.cdc.nedss.systemservice.dt.NbsInterfaceDT;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dt.EdxLabInformationDT;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dt.EdxPatientMatchDT;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.util.EdxMatchingCriteriaUtil;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.util.EdxPatientMatchingCriteriaUtil;
import gov.cdc.nedss.systemservice.ejb.jurisdictionserviceejb.bean.JurisdictionService;
import gov.cdc.nedss.systemservice.ejb.jurisdictionserviceejb.bean.JurisdictionServiceHome;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.util.XMLTypeToNBSObject;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;



/**
 * 
 * @author Pradeep Kumar Sharma @SAIC
 * @version NBS Release 4.4 This is a utility class to process the ELR messages
 * 
 */
public class HL7CommonLabUtil {
	static final LogUtils logger = new LogUtils(
			HL7CommonLabUtil.class.getName());

	public ObservationDT sendLabResultToProxy(
			LabResultProxyVO labResultProxyVO, NBSSecurityObj securityObj)
			throws RemoteException, NEDSSSystemException {
		ObservationDT obsDT = null;

		if (!(securityObj.getTheUserProfile().getTheUser().getUserID()
				.equals("nedss_elr_load"))) {
			logger.error("HL7CommonLabUtil.sendLabResultToProxy \"nedss_elr_load\" user found thrown ");

			throw new NEDSSSystemException(
					"do not have the permission to retrieve reporting lab.");
		}

		if (labResultProxyVO == null) {
			logger.error("HL7CommonLabUtil.sendLabResultToProxy labResultProxyVO is null ");
			throw new NEDSSSystemException("LabResultProxyVO is null");
		} else {
			labResultProxyVO.setItNew(true);
			labResultProxyVO.setItDirty(false);
		}

		try {
			NedssUtils nedssUtils = new NedssUtils();
			String sBeanJndiName = JNDINames.OBSERVATION_PROXY_EJB;
			Object objref = nedssUtils.lookupBean(sBeanJndiName);
			logger.debug("objref = " + objref.toString());

			ObservationProxyHome home = (ObservationProxyHome) PortableRemoteObject
					.narrow(objref, ObservationProxyHome.class);
			ObservationProxy obsProxy = home.create();
			logger.debug("obsProxy = " + obsProxy.getClass());
			Map<Object, Object> returnMap = obsProxy.setLabResultProxy(
					labResultProxyVO, securityObj);
			
			obsDT = (ObservationDT)returnMap
					.get(NEDSSConstants.SETLAB_RETURN_OBSDT);
			logger.info("odsObsLocalId: " + obsDT.getLocalId());

		} catch (Exception e) {
			logger.fatal("HL7CommonLabUtil.sendLabResultToProxy Exception thrown "+ e.getMessage(), e);
			throw new NEDSSSystemException("HL7CommonLabUtil.sendLabResultToProxy The labResultProxyVO could not be saved"+ e.getMessage());
		}
		logger.info("Sent LabResultProxyVO to Observation Proxy - ODS Observation uid="
				+ obsDT.getObservationUid());
		return obsDT;
	}

	public LabResultProxyVO getLabResultToProxy(Long observationUid,
			NBSSecurityObj securityObj) throws RemoteException,
			NEDSSSystemException {
		LabResultProxyVO labResultProxyVO = null;
		if (!(securityObj.getTheUserProfile().getTheUser().getUserID()
				.equals("nedss_elr_load"))) {
			logger.error("HL7CommonLabUtil.getLabResultToProxy \"nedss_elr_load\" user not found.");

			throw new NEDSSSystemException(
					"do not have the permission to retrieve reporting lab.");
		}

		if (observationUid == null) {
			logger.error("HL7CommonLabUtil.getLabResultToProxy observationUid is null ");
			throw new NEDSSSystemException("LabResultProxyVO is null");
		}
		try {
			NedssUtils nedssUtils = new NedssUtils();
			String sBeanJndiName = JNDINames.OBSERVATION_PROXY_EJB;
			Object objref = nedssUtils.lookupBean(sBeanJndiName);
			logger.debug("objref = " + objref.toString());

			ObservationProxyHome home = (ObservationProxyHome) PortableRemoteObject
					.narrow(objref, ObservationProxyHome.class);
			ObservationProxy obsProxy = home.create();
			logger.debug("obsProxy = " + obsProxy.getClass());
			labResultProxyVO = obsProxy.getLabResultProxyByType(observationUid, true,
					securityObj);

		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal("HL7CommonLabUtil.getLabResultToProxy Exception thrown "
					+ e);
			throw new NEDSSSystemException(
					"HL7CommonLabUtil.getLabResultToProxy The labResultProxyVO could not be retrieved. Please check.:"
							+ e);
		}
		logger.info("HL7CommonLabUtil.getLabResultToProxy result returned.");
		return labResultProxyVO;
	}

	public LabResultProxyVO processELR(NbsInterfaceDT nbsInterfaceDT,
			EdxLabInformationDT edxLabInformationDT,
			NBSSecurityObj nbsSecurityObj) throws NEDSSAppException {
		LabResultProxyVO labResultProxyVO = null;
		
		try {
			HL7PatientProcessor hL7PatientProcessor = new HL7PatientProcessor();
			HL7ORCProcessor hL7ORCProcessor = new HL7ORCProcessor();
			int rootObservationUid = 0;
			Long userId = Long.parseLong(nbsSecurityObj.getEntryID());

			edxLabInformationDT.setRootObserbationUid(new Integer(
					--rootObservationUid));
			edxLabInformationDT.setUserId(userId);
			edxLabInformationDT
					.setPatientUid(new Integer(--rootObservationUid));
			Timestamp time = new Timestamp(new Date().getTime());
			edxLabInformationDT.setAddTime(time);

			edxLabInformationDT.setNextUid(new Integer(--rootObservationUid));
			XMLTypeToNBSObject xMLTypeToNBSObject = new XMLTypeToNBSObject();
			String xmlPayLoadContent = nbsInterfaceDT.getXmlPayLoadContent();
			Collection<Object> collXMLDoc = new ArrayList<Object>();
			EDXDocumentDT eDXDocumentDT = new EDXDocumentDT();
			eDXDocumentDT.setAddTime(time);
			eDXDocumentDT.setDocTypeCd(EdxELRConstants.ELR_DOC_TYPE_CD);
			eDXDocumentDT.setPayload(xmlPayLoadContent);
			eDXDocumentDT.setRecordStatusCd(EdxELRConstants.ELR_ACTIVE);
			eDXDocumentDT.setRecordStatusTime(time);
			eDXDocumentDT
					.setNbsDocumentMetadataUid(EdxELRConstants.ELR_NBS_DOC_META_UID);
			eDXDocumentDT.setItDirty(false);
			eDXDocumentDT.setItNew(true);
			collXMLDoc.add(eDXDocumentDT);
			HL7PatientResultSPMType hl7PatientResultSPMType = null;
			ContainerDocument containerDoc = xMLTypeToNBSObject
					.parseCaseTypeXml(xmlPayLoadContent);
			HL7LabReportType hl7LabReportType = containerDoc.getContainer()
					.getHL7LabReport();
			HL7MSHType messageHandlerType = hl7LabReportType.getHL7MSH();// one
																			// Header
			MessageHandler messageHandler = new MessageHandler();
			labResultProxyVO = messageHandler.getMessage(messageHandlerType,
					edxLabInformationDT);
			HL7PATIENTRESULTType[] HL7PatientResultArray = hl7LabReportType
					.getHL7PATIENTRESULTArray();
			if(HL7PatientResultArray==null){
				edxLabInformationDT.setNoSubject(true);
				edxLabInformationDT.setErrorText(EdxELRConstants.ELR_MASTER_LOG_ID_13);
				logger.error("HL7CommonLabUtil.processELR error thrown as NO patient segment is found.Please check message with NBS_INTERFACE_UID:-"+nbsInterfaceDT.getNbsInterfaceUid());
				throw new NEDSSAppException(EdxELRConstants.NO_SUBJECT);
			}else if(HL7PatientResultArray!=null && HL7PatientResultArray.length>1){
				edxLabInformationDT.setMultipleSubject(true);
				edxLabInformationDT.setErrorText(EdxELRConstants.ELR_MASTER_LOG_ID_13);
				logger.error("HL7CommonLabUtil.processELR error thrown as multiple patient segment is found.Please check message with NBS_INTERFACE_UID:-"+nbsInterfaceDT.getNbsInterfaceUid());
				throw new NEDSSAppException(EdxELRConstants.MULTIPLE_SUBJECT);
			}
			for (int i = 0; i < HL7PatientResultArray.length; i++) {
				HL7PATIENTRESULTType hl7PatientResult = HL7PatientResultArray[i];
				
				labResultProxyVO = hL7PatientProcessor.getPatientAndNextOfKin(hl7PatientResult,
								labResultProxyVO, edxLabInformationDT);
				HL7OrderObservationType[] hl7OrderObservationArray = hl7PatientResult
						.getORDEROBSERVATIONArray();
				if(hl7OrderObservationArray==null){
					edxLabInformationDT.setOrderTestNameMissing(true);
					logger.error("HL7CommonLabUtil.processELR error thrown as NO OBR segment is found.Please check message with NBS_INTERFACE_UID:-"+nbsInterfaceDT.getNbsInterfaceUid());
					throw new NEDSSAppException(EdxELRConstants.NO_ORDTEST_NAME);
				}
				for (int j = 0; j < hl7OrderObservationArray.length; j++) {
					// edxLabInformationDT.setParentObservationUid(new Long(0));
					HL7OrderObservationType hl7OrderObservationType = hl7OrderObservationArray[j];
					if (hl7OrderObservationType.getCommonOrder() != null)
						hL7ORCProcessor.getORCProcessing(
								hl7OrderObservationType.getCommonOrder(),
								labResultProxyVO, edxLabInformationDT);
					hl7OrderObservationType.getPatientResultOrderObservation()
							.getOBSERVATIONArray();
					if (hl7OrderObservationType
							.getPatientResultOrderSPMObservation() != null) {
						hl7PatientResultSPMType = hl7OrderObservationType.getPatientResultOrderSPMObservation();
					}
					if(j==0 && (hl7OrderObservationType.getObservationRequest().getParent()!=null 
								||hl7OrderObservationType.getObservationRequest().getParentResult()!=null) ){
						edxLabInformationDT.setOrderOBRWithParent(true);
						edxLabInformationDT.setErrorText(EdxELRConstants.ELR_MASTER_LOG_ID_13);
						logger.error("HL7CommonLabUtil.processELR error thrown as either OBR26 is null OR OBR 29 is \"NOT NULL\" for the first OBR section.Please check message with NBS_INTERFACE_UID:-"+nbsInterfaceDT.getNbsInterfaceUid());
						throw new NEDSSAppException(EdxELRConstants.ORDER_OBR_WITH_PARENT);
				
					}else if(j>0 && (hl7OrderObservationType.getObservationRequest().getParent()==null 
								|| hl7OrderObservationType.getObservationRequest().getParentResult()==null 
								|| hl7OrderObservationType.getObservationRequest().getParentResult().getParentObservationValueDescriptor()== null
								|| hl7OrderObservationType.getObservationRequest().getParentResult().getParentObservationValueDescriptor().getHL7String() == null
								|| hl7OrderObservationType.getObservationRequest().getParentResult().getParentObservationValueDescriptor().getHL7String().trim().equals("")
								|| hl7OrderObservationType.getObservationRequest().getParent().getHL7FillerAssignedIdentifier()==null 
								|| hl7OrderObservationType.getObservationRequest().getParent().getHL7FillerAssignedIdentifier().getHL7EntityIdentifier()==null
								|| hl7OrderObservationType.getObservationRequest().getParent().getHL7FillerAssignedIdentifier().getHL7EntityIdentifier().trim().equals("")
								|| hl7OrderObservationType.getObservationRequest().getParentResult().getParentObservationIdentifier()==null
								|| (hl7OrderObservationType.getObservationRequest().getParentResult().getParentObservationIdentifier().getHL7Identifier()==null && hl7OrderObservationType.getObservationRequest().getParentResult().getParentObservationIdentifier().getHL7AlternateIdentifier()==null)
								|| (hl7OrderObservationType.getObservationRequest().getParentResult().getParentObservationIdentifier().getHL7Text()==null && hl7OrderObservationType.getObservationRequest().getParentResult().getParentObservationIdentifier().getHL7AlternateText()==null))){
						
						edxLabInformationDT.setMultipleOBR(true);	
						logger.error("HL7CommonLabUtil.processELR error thrown as either OBR26 is null OR OBR 29 is null for the OBR "+(j+1)+".Please check message with NBS_INTERFACE_UID:-"+nbsInterfaceDT.getNbsInterfaceUid());
						edxLabInformationDT.setErrorText(EdxELRConstants.ELR_MASTER_LOG_ID_13);
						throw new NEDSSAppException(EdxELRConstants.MULTIPLE_OBR);
					}

					ObservationRequest observationRequest = new ObservationRequest();
					observationRequest.getObservationRequest(hl7OrderObservationType.getObservationRequest(),
							hl7PatientResultSPMType, labResultProxyVO,
							edxLabInformationDT);
					
					if(edxLabInformationDT.getRootObservationVO()!=null 
							&& edxLabInformationDT.getRootObservationVO().getTheObservationDT()!=null
							&& edxLabInformationDT.getRootObservationVO().getTheObservationDT().getEffectiveFromTime()!=null) {
						NbsInterfaceDAOImpl nbsInterfaceDAOImpl = new NbsInterfaceDAOImpl();
						nbsInterfaceDAOImpl.updateNBSInterfaceRecord(edxLabInformationDT);
					}
					ObservationResultRequest observationResultRequest  = new ObservationResultRequest();
					observationResultRequest.getObservationResultRequest(
							hl7OrderObservationType
									.getPatientResultOrderObservation()
									.getOBSERVATIONArray(), labResultProxyVO,
							edxLabInformationDT);

				}
			}
			labResultProxyVO.seteDXDocumentCollection(collXMLDoc);
		} catch (Exception e) {
			logger.fatal("HL7CommonLabUtil.processELR Exception thrown while parsing XML document. Please checkPlease check message with NBS_INTERFACE_UID:-"+nbsInterfaceDT.getNbsInterfaceUid(), e);
			throw new NEDSSAppException("Exception thrown at HL7CommonLabUtil.processELR:" + e.getMessage());
		}
		return labResultProxyVO;
	}
	
		/**
		 * @param labResultProxyVO
		 * @param edxLabInformationDT
		 * @param nbsSecurityObj
		 * @return
		 * @throws NEDSSAppException
		 */
		/**
		 * @param labResultProxyVO
		 * @param edxLabInformationDT
		 * @param nbsSecurityObj
		 * @return
		 * @throws NEDSSAppException
		 */
		public LabResultProxyVO processELREntities(LabResultProxyVO labResultProxyVO, EdxLabInformationDT edxLabInformationDT,NBSSecurityObj nbsSecurityObj) throws NEDSSAppException{
		RoleDAOImpl roleDao =  new RoleDAOImpl();
		PersonVO subjectVO = null;
		PersonVO providerVO = null;
		OrganizationVO orderingFacilityVO = null;

		try {
			Collection<ObservationVO> obsColl = labResultProxyVO.getTheObservationVOCollection();
			if (obsColl != null) { 
				Iterator<ObservationVO> it = obsColl.iterator();
				while (it.hasNext()) {
					ObservationVO obsVO= (ObservationVO)it.next();
					if((obsVO.getTheObservationDT().getObservationUid().longValue()==edxLabInformationDT.getRootObserbationUid()) && edxLabInformationDT.getRootObserbationUid() >0 ){
						long falseUid = -1;
						setFalseToNew(labResultProxyVO, falseUid, obsVO.getTheObservationDT().getObservationUid());
						if(obsVO.getTheActIdDTCollection()!=null){
							Iterator<Object> iter = obsVO.getTheActIdDTCollection().iterator();
							while(iter.hasNext()){
								ActIdDT actIdDT = (ActIdDT)iter.next();
								actIdDT.setItNew(false);
								actIdDT.setItDirty(true);
								actIdDT.setActUid(obsVO.getTheObservationDT().getObservationUid().longValue());
							}
						}
							
						break;
					}
				}
			}
			Collection<Object> personColl = labResultProxyVO
					.getThePersonVOCollection();
			if (personColl != null) {
				Iterator<Object> it = personColl.iterator();
				boolean orderingProviderIndicator = false;
				
				while (it.hasNext()) {
					PersonVO personVO = (PersonVO) it.next();
					if (personVO.getRole() != null
							&& personVO.getRole().equalsIgnoreCase(
									EdxELRConstants.ELR_NEXT_OF_KIN)) {
						long falseUid = personVO.thePersonDT.getPersonUid();
						EdxPatientMatchingCriteriaUtil edxPatientMatchingCriteriaUtil = new EdxPatientMatchingCriteriaUtil();
						edxPatientMatchingCriteriaUtil.getMatchingPatient(
								personVO, nbsSecurityObj);
						if (personVO.getThePersonDT().getPersonUid() != null) {
							// Long
							// personUid=personVO.getThePersonDT().getPersonUid();
							setFalseToNew(labResultProxyVO, falseUid, personVO
									.getThePersonDT().getPersonUid());
							// personVO =getPerson(personUid,nbsSecurityObj);
							personVO.setItNew(false);
							personVO.setItDirty(false);
							personVO.getThePersonDT().setItNew(false);
							personVO.getThePersonDT().setItDirty(false);

						}
					} else {
						long falseUid = personVO.thePersonDT.getPersonUid();
						Long personUid;
						EdxPatientMatchDT edxPatientMatchFoundDT = null;
						if (personVO.thePersonDT.getCd().equalsIgnoreCase(
								EdxELRConstants.ELR_PATIENT_CD)) {
							personVO.setRole(EdxELRConstants.ELR_PATIENT_CD);
							if(edxLabInformationDT.getPatientUid()>0){
								personUid=edxLabInformationDT.getPatientUid();
							}else{
								EdxPatientMatchingCriteriaUtil edxPatientMatchingCriteriaUtil = new EdxPatientMatchingCriteriaUtil();
								edxPatientMatchFoundDT = edxPatientMatchingCriteriaUtil
										.getMatchingPatient(personVO,nbsSecurityObj);
								edxLabInformationDT.setMultipleSubjectMatch(edxPatientMatchingCriteriaUtil.multipleMatchFound);
								personUid = personVO.getThePersonDT().getPersonUid();
							}
							if (personUid != null) {
								setFalseToNew(labResultProxyVO, falseUid,
										personUid);
								// personVO
								// =getPerson(personUId,nbsSecurityObj);
								personVO.setItNew(false);
								personVO.setItDirty(false);
								personVO.getThePersonDT().setItNew(false);
								personVO.getThePersonDT().setItDirty(false);			
								PersonNameDT personName = getPersonNameUseCdL(personVO);
								String lastName = personName.getLastNm();
								String firstName = personName.getFirstNm();
								edxLabInformationDT.setEntityName(firstName
										+ " " + lastName);
							}
//							addActivityDetailMsg(edxLabInformationDT, personVO,
//									edxPatientMatchFoundDT);
							if(edxPatientMatchFoundDT!=null && !edxPatientMatchFoundDT.isMultipleMatch() && personVO.getIsExistingPatient())
								edxLabInformationDT.setPatientMatch(true);
							if(personVO.getThePersonDT().getPersonParentUid()!=null){
								edxLabInformationDT.setPersonParentUid(personVO.getThePersonDT().getPersonParentUid().longValue());
							}
							subjectVO = personVO;
						} else if (personVO.thePersonDT.getCd()
								.equalsIgnoreCase(
										EdxELRConstants.ELR_PROVIDER_CD)) {
							
							if (personVO.getRole() != null
									&& personVO.getRole().equalsIgnoreCase(
											EdxELRConstants.ELR_OP_CD))
								orderingProviderIndicator = true;
							personVO.setRole(EdxELRConstants.ELR_PROV_CD);
							EdxMatchingCriteriaUtil util = new EdxMatchingCriteriaUtil();
							EDXActivityDetailLogDT eDXActivityDetailLogDT = new EDXActivityDetailLogDT();
							eDXActivityDetailLogDT = util.getMatchingProvider(
									personVO, nbsSecurityObj);
							String personUId;
							personUId = eDXActivityDetailLogDT.getRecordId();
							if (personUId != null) {
								long uid = Long.parseLong(personUId);
								setFalseToNew(labResultProxyVO, falseUid,
										uid);
								// personVO
								// =getPerson(personUid,nbsSecurityObj);
								personVO.setItNew(false);
								personVO.setItDirty(false);
								personVO.getThePersonDT().setItNew(false);
								personVO.getThePersonDT().setItDirty(false);
							}
							if (orderingProviderIndicator)
								providerVO = personVO;
							orderingProviderIndicator= false;
						}
					}
				}
			}

			Collection<Object> orgColl = labResultProxyVO
					.getTheOrganizationVOCollection();
			if (orgColl != null) {
				Iterator<Object> it = orgColl.iterator();
				while (it.hasNext()) {
					OrganizationVO organizationVO = (OrganizationVO) it.next();
					Long orgUid;
					if(organizationVO.getRole()!=null && organizationVO.getRole().equalsIgnoreCase(EdxELRConstants.ELR_SENDING_FACILITY_CD) && labResultProxyVO.getSendingFacilityUid()!=null)
						orgUid= labResultProxyVO.getSendingFacilityUid();
					else{
						EdxMatchingCriteriaUtil util = new EdxMatchingCriteriaUtil();
						EDXActivityDetailLogDT eDXActivityDetailLogDT = new EDXActivityDetailLogDT();
							eDXActivityDetailLogDT = util.getMatchingOrganization(
								organizationVO, nbsSecurityObj);
						
						orgUid = Long.parseLong(eDXActivityDetailLogDT
								.getRecordId());
							
					}
					Long falseUid = organizationVO.getTheOrganizationDT()
							.getOrganizationUid();
					//match found!!!!
					if (orgUid > 0) {
						setFalseToNew(labResultProxyVO, falseUid, orgUid);
						// /organizationVO
						// =getOrganization(orgUid,nbsSecurityObj);
						organizationVO.setItNew(false);
						organizationVO.setItDirty(false);
						organizationVO.getTheOrganizationDT().setItNew(false);
						organizationVO.getTheOrganizationDT().setItDirty(false);
					}
					if (organizationVO.getRole() != null
							&& organizationVO.getRole().equalsIgnoreCase(
									EdxELRConstants.ELR_OP_CD))
						orderingFacilityVO = organizationVO;
				}
			}
			/**
			*Roles must be checked for NEW, UPDATED, MARK FOR DELETE buckets.
			*/
			
			Map<Object, Object> mappedExistingRoleCollection  = new HashMap<Object, Object>();
			Map<Object, Object> mappedNewRoleCollection  = new HashMap<Object, Object>();
			if(labResultProxyVO.getTheRoleDTCollection()!=null){
			
				Collection<Object> coll=labResultProxyVO.getTheRoleDTCollection();
				if(coll!=null){
					Iterator<Object> it = coll.iterator();
					while(it.hasNext()){ 
						
						RoleDT roleDT = (RoleDT)it.next();
						if(roleDT.isItDelete()){
							mappedExistingRoleCollection.put(roleDT.getSubjectEntityUid()+roleDT.getCd()+roleDT.getScopingEntityUid(), roleDT);
						}else{
							mappedNewRoleCollection.put(roleDT.getSubjectEntityUid()+roleDT.getCd()+roleDT.getScopingEntityUid(), roleDT);
							
						}
						
					}
				}
			}
			ArrayList<Object> list = new ArrayList<Object>();
			
			//update scenario
			if(mappedNewRoleCollection!=null){
				Set<Object> set = mappedNewRoleCollection.keySet();
				Iterator<Object> iterator = set.iterator();
				while(iterator.hasNext()){
					String key = (String) iterator.next();
					if(mappedExistingRoleCollection.containsKey(key)){
						//Do not delete/modify the role as it exists in both updated and old ELR
						// UPDATE Role bucket
						mappedExistingRoleCollection.remove(key);
						
						
					}else{
						//insert Role as it is new in new/updated ELR
						//NEW role Bucket
						RoleDT roleDT = (RoleDT)mappedNewRoleCollection.get(key);
						list.add(roleDT);
					}
					
				}
				
				//will add all roles that are part of the old collection but are not contained in the new collection
				// MARK FOR DELETE Role bucket
				list.addAll(mappedExistingRoleCollection.values());
			}
			
			
			Map<Object, Object> modifiedRoleMap = new HashMap<Object,Object>();
			ArrayList<Object> listFinal = new ArrayList<Object>();
				if(list!=null){
					Iterator<Object> it = list.iterator();
					while(it.hasNext()){ 
						RoleDT roleDT = (RoleDT)it.next();
						if(roleDT.isItDelete()){
							listFinal.add(roleDT);
							//We have already taken care of the deduplication of role in the above code
							continue;
						}
						//We will write the role if there are no existing role relationships. 
						if(roleDT.getScopingEntityUid()==null){
							Long count =new Long(0);
							count = roleDao.loadCountBySubjectCdComb(roleDT);
							if(count==0){
								roleDT.setRoleSeq(count+1);
								modifiedRoleMap.put(roleDT.getSubjectEntityUid()+roleDT.getRoleSeq()+roleDT.getCd(), roleDT);
							}
							
						}else{
							
							int checkIfExisits =0;
							checkIfExisits = roleDao.loadCountBySubjectScpingCdComb(roleDT);
							
							if(checkIfExisits>0){
								//do nothing as this role relationship exists in database
							}else{
								long countForPKValues =0;
								countForPKValues = roleDao.loadCountBySubjectCdComb(roleDT);
								//We will write the role relationship for follwoing provider in scope of ELR patient
								if(countForPKValues==0 
										|| (roleDT.getCd()!=null 
											&& (roleDT.getCd().equals(EdxELRConstants.ELR_SPECIMEN_PROCURER_CD) 
													||roleDT.getCd().equals(EdxELRConstants.ELR_COPY_TO_CD)
													|| roleDT.getSubjectClassCd().equals(EdxELRConstants.ELR_CON)))){
									
									if(roleDT.getRoleSeq()!=null &&  roleDT.getRoleSeq().intValue()==2 
											&& roleDT.getSubjectClassCd().equals(EdxELRConstants.ELR_MAT_CD)
											&& roleDT.getScopingRoleCd().equals(EdxELRConstants.ELR_SPECIMEN_PROCURER_CD)){
										//Material is a special as provider to material is created with role sequence 2
									}else{
										roleDT.setRoleSeq(countForPKValues+1);
									}
									modifiedRoleMap.put(roleDT.getSubjectEntityUid()+roleDT.getRoleSeq()+roleDT.getCd()+roleDT.getScopingEntityUid(), roleDT);
	
								}
							}
							
						}
					}
				
				if(modifiedRoleMap!=null){
					Collection<Object> roleCollection = modifiedRoleMap.values();
					listFinal.addAll(roleCollection);
					/*Iterator<Object> itr = roleCollection.iterator();
					while(itr.hasNext()){
						RoleDT roleDT = (RoleDT)itr.next();
						listFinal.add(roleDT);
					}*/
				}
				
				labResultProxyVO.setTheRoleDTCollection(listFinal);
				
			}
		} catch (Exception e) {
			logger.error("HL7CommonLabUtil.processELR Exception thrown while processing entity Matching logic. Please check!!!"
					+ e.getMessage(), e);
			throw new NEDSSAppException(
					"Exception thrown at HL7CommonLabUtil.processELR entity Matching logic:"
							+ e);
		}

		try {
			ObservationVO orderTestVO = null;
			Collection<Object> resultTests = new ArrayList<Object>();
			for (Iterator<ObservationVO> it = labResultProxyVO
					.getTheObservationVOCollection().iterator(); it.hasNext();) {
				ObservationVO obsVO = (ObservationVO) it.next();

				String obsDomainCdSt1 = obsVO.getTheObservationDT()
						.getObsDomainCdSt1();
				if (obsDomainCdSt1 != null
						&& obsDomainCdSt1
								.equalsIgnoreCase(EdxELRConstants.ELR_RESULT_CD)) {
					resultTests.add(obsVO);
				} else if (obsDomainCdSt1 != null
						&& obsDomainCdSt1
								.equalsIgnoreCase(EdxELRConstants.ELR_ORDER_CD)) {
					orderTestVO = obsVO;
				}
			}

			if(orderTestVO.getTheObservationDT().getProgAreaCd()==null)
				getProgramArea(resultTests, orderTestVO,
					edxLabInformationDT.getSendingFacilityClia(),
					nbsSecurityObj);
			if(orderTestVO.getTheObservationDT().getJurisdictionCd()==null)
				assignJurisdiction(subjectVO, providerVO, orderingFacilityVO,
					orderTestVO);

		} catch (Exception e) {
			logger.error("HL7CommonLabUtil.processELR Exception thrown while finding programArea. Please check!!!"
					+ e.getMessage(), e);
			throw new NEDSSAppException(
					"Exception thrown at HL7CommonLabUtil.processELR Exception thrown while finding programArea:"
							+ e);
		}

		return labResultProxyVO;
	}

	/*private static void addActivityDetailMsg(
			EdxLabInformationDT edxLabInformationDT, PersonVO personVO,
			EdxPatientMatchDT edxPatientMatchDT) throws NEDSSAppException {
		EDXActivityLogDT edxActivityLogDT = edxLabInformationDT
				.getEdxActivityLogDT();
		Collection<Object> details = edxActivityLogDT
				.getEDXActivityLogDTWithVocabDetails();
		PersonNameDT personName = getPersonNameUseCdL(personVO);
		String lastName = personName.getLastNm();
		String firstName = personName.getFirstNm();
		String msg;

		if (edxPatientMatchDT.isMultipleMatch()) {
			msg = EdxELRConstants.DET_MSG_PATIENT_MULTIPLE_FOUND;
		} else {
			if (personVO.getIsExistingPatient()) {
				msg = EdxELRConstants.DET_MSG_PATIENT_FOUND;
			} else {
				msg = EdxELRConstants.DET_MSG_PATIENT_NOT_FOUND;
			}
		}
		msg = msg.replace("%1", lastName);
		msg = msg.replace("%2", firstName);
		msg = msg.replace("%3", ""
				+ personVO.getThePersonDT().getPersonParentUid());

		EDXActivityDetailLogDT edxActivityDetailLogDT = new EDXActivityDetailLogDT();
		edxActivityDetailLogDT.setRecordId(""
				+ personVO.getThePersonDT().getPersonParentUid());
		edxActivityDetailLogDT.setRecordType(EdxELRConstants.ELR_RECORD_TP);
		edxActivityDetailLogDT.setRecordName(EdxELRConstants.ELR_RECORD_NM);
		edxActivityDetailLogDT
				.setLogType(EdxRuleAlgorothmManagerDT.STATUS_VAL.Success.name());

		if (msg.length() > EdxPHCRConstants.MAX_DETAIL_COMMENT_LEN) {
			msg = msg.substring(0, EdxPHCRConstants.MAX_DETAIL_COMMENT_LEN)
					+ "...";
		}

		edxActivityDetailLogDT.setComment(msg);

		if (details == null) {
			ArrayList<Object> al = new ArrayList<Object>();
			edxActivityLogDT.setEDXActivityLogDTWithVocabDetails(al);
			details = edxActivityLogDT.getEDXActivityLogDTWithVocabDetails();
		}
		details.add(edxActivityDetailLogDT);

		edxActivityLogDT.setEDXActivityLogDTWithVocabDetails(details);
	}
*/
	private PersonNameDT getPersonNameUseCdL(PersonVO personVO)
			throws NEDSSAppException {
		Collection<Object> personNames = personVO
				.getThePersonNameDTCollection();
		Iterator<Object> pnIter = personNames.iterator();
		while (pnIter.hasNext()) {
			PersonNameDT personName = (PersonNameDT) pnIter.next();
			if (personName.getNmUseCd().equals("L")) {
				return personName;
			}
		}
		throw new NEDSSAppException("No name use code \"L\" in PersonVO");
	}

	private void setFalseToNew(LabResultProxyVO labResultProxyVO,
			Long falseUid, Long actualUid) throws NEDSSAppException {
		
		try {
			Iterator<Object> anIterator = null;

			ParticipationDT participationDT = null;
			ActRelationshipDT actRelationshipDT = null;
			RoleDT roleDT = null;

			Collection<Object> participationColl = (ArrayList<Object>) labResultProxyVO
					.getTheParticipationDTCollection();
			Collection<Object> actRelationShipColl = (ArrayList<Object>) labResultProxyVO
					.getTheActRelationshipDTCollection();
			Collection<Object> roleColl = (ArrayList<Object>) labResultProxyVO
					.getTheRoleDTCollection();
			
			if (participationColl != null) {
				for (anIterator = participationColl.iterator(); anIterator
						.hasNext();) {
					participationDT = (ParticipationDT) anIterator.next();
					logger.debug("(participationDT.getAct() comparedTo falseUid)"
							+ (participationDT.getActUid().compareTo(falseUid)));
					if (participationDT.getActUid().compareTo(falseUid) == 0) {
						participationDT.setActUid(actualUid);
					}
					
					if (participationDT.getSubjectEntityUid().compareTo(
							falseUid) == 0) {
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
					}
					if (actRelationshipDT.getSourceActUid().compareTo(falseUid) == 0) {
						actRelationshipDT.setSourceActUid(actualUid);
					}
					logger.debug("ActRelationShipDT: falseUid "
							+ falseUid.toString() + " actualUid: " + actualUid);
				}
			}
			if (roleColl != null) {
				for (anIterator = roleColl.iterator(); anIterator.hasNext();) {
					roleDT = (RoleDT) anIterator.next();
					if (roleDT.getSubjectEntityUid().compareTo(falseUid) == 0) {
						roleDT.setSubjectEntityUid(actualUid);
					}
					if (roleDT.getScopingEntityUid() != null) {
						if (roleDT.getScopingEntityUid().compareTo(falseUid) == 0) {
							roleDT.setScopingEntityUid(actualUid);
							//roleDT.setItNew(false);
							//roleDT.setItDirty(true);
						}
						logger.debug("\n\n\n(roleDT.getSubjectEntityUid() compared to falseUid)  "
								+ roleDT.getSubjectEntityUid().compareTo(
										falseUid));
						logger.debug("\n\n\n(roleDT.getScopingEntityUid() compared to falseUid)  "
								+ roleDT.getScopingEntityUid().compareTo(
										falseUid));
					}
					
				}
			}
			
		} catch (Exception e) {
			logger.error("HL7CommonLabUtil.setFalseToNew thrown for falseUid:"
					+ falseUid + "For actualUid :" + actualUid);
			throw new NEDSSAppException(
					"HL7CommonLabUtil.setFalseToNew thrown for falseUid:"
							+ falseUid + "For actualUid :" + actualUid);
		}
	}

	/*
	 * private static OrganizationVO getOrganization(Long organizationUID,
	 * NBSSecurityObj nbsSecurityObj) throws NEDSSAppException {
	 * 
	 * EdxMatchingCriteriaUtil util = new EdxMatchingCriteriaUtil();
	 * EntityController entityController = util.getEntityController();
	 * OrganizationVO orgVO; try { orgVO =
	 * entityController.getOrganization(organizationUID, nbsSecurityObj);
	 * Collection entityLocatorColl =
	 * orgVO.getTheEntityLocatorParticipationDTCollection();
	 * setEntityLocatorParticipation(organizationUID, entityLocatorColl); }
	 * catch (RemoteException e) { throw new
	 * NEDSSAppException("Exception thrown at HL7CommonLabUtil.getOrganization:"
	 * + e); } catch (EJBException e) { throw new
	 * NEDSSAppException("Exception thrown at HL7CommonLabUtil.getOrganization:"
	 * + e); } return orgVO;
	 * 
	 * }
	 * 
	 * private static void setEntityLocatorParticipation(Long entityUid,
	 * Collection<Object> entityLocatorColl) throws RemoteException,
	 * NEDSSAppException { Iterator<Object> anIterator = null;
	 * EntityLocatorParticipationDT elpDT = null; try { if (entityLocatorColl !=
	 * null) {
	 * 
	 * for (anIterator = entityLocatorColl.iterator(); anIterator.hasNext();) {
	 * elpDT = (EntityLocatorParticipationDT) anIterator.next();
	 * elpDT.setEntityUid(entityUid); elpDT.setItNew(false);
	 * elpDT.setItDirty(true); } logger.debug(
	 * "\n\n\n(HL7CommonLabUtil.setEntityLocatorParticipation :EntityLocatorParticipationDT.set entityUid )  "
	 * + entityUid); logger.debug(
	 * "\n\n\n(HL7CommonLabUtil.setEntityLocatorParticipation :EntityLocatorParticipationDT. set entityUid )  "
	 * + entityUid); } } catch (EJBException e) { throw new NEDSSAppException(
	 * "Exception thrown at HL7CommonLabUtil.setEntityLocatorParticipation:" +
	 * e); } }
	 * 
	 * /*private static PersonVO getPerson(Long personUID, NBSSecurityObj
	 * nbsSecurityObj) throws NEDSSAppException {
	 * 
	 * EdxMatchingCriteriaUtil util = new EdxMatchingCriteriaUtil();
	 * EntityController entityController = util.getEntityController(); PersonVO
	 * personVO; try { personVO = entityController.getPerson(personUID,
	 * nbsSecurityObj); Collection entityLocatorColl =
	 * personVO.getTheEntityLocatorParticipationDTCollection();
	 * setEntityLocatorParticipation(personUID, entityLocatorColl); } catch
	 * (RemoteException e) { throw new
	 * NEDSSAppException("Exception thrown at HL7CommonLabUtil.getPerson:" + e);
	 * } catch (EJBException e) { throw new
	 * NEDSSAppException("Exception thrown at HL7CommonLabUtil.getPerson:" + e);
	 * } return personVO;
	 * 
	 * }
	 */
	public void getProgramArea(Collection<Object> resultTests,
			ObservationVO orderTest, String clia, NBSSecurityObj securityObj)
			throws NEDSSAppException {

		String programAreaCode = null;
		{
			try {

				if (clia == null || clia.trim().equals(""))
					clia = NEDSSConstants.DEFAULT;

				Map<Object, Object> paResults = null;
				if (orderTest.getTheObservationDT().getElectronicInd()
						.equals(NEDSSConstants.ELECTRONIC_IND_ELR)) {
					if (resultTests.size() > 0) {

						ObservationProcessor obsProcessor = new ObservationProcessor();
						paResults = obsProcessor.getProgramArea(clia,
								resultTests, orderTest.getTheObservationDT()
										.getElectronicInd());
					}

					if (paResults != null
							&& paResults
									.containsKey(ELRConstants.PROGRAM_AREA_HASHMAP_KEY)) {
						programAreaCode = (String) paResults
								.get(ELRConstants.PROGRAM_AREA_HASHMAP_KEY);
						orderTest.getTheObservationDT().setProgAreaCd(
								programAreaCode);
					} else {
						orderTest.getTheObservationDT().setProgAreaCd(null);
					}
				}
				if (paResults != null && paResults.containsKey("ERROR")) {
					orderTest.getTheObservationDT().setProgAreaCd(null);
				} else {
					orderTest.getTheObservationDT().setProgAreaCd(
							programAreaCode);
				}
			} catch (Exception e) {
				logger.error("HL7CommonLabUtil.getProgramArea: exception caught. "
						+ e);
				throw new NEDSSAppException(
						"HL7CommonLabUtil.getProgramArea: exception caught. "
								+ e);
			}
		}
	}

	private void assignJurisdiction(PersonVO subjectVO,
			PersonVO providerVO, OrganizationVO orderingFacilityVO,
			ObservationVO orderTestVO) throws NEDSSAppException {
		try {
			NedssUtils nedssUtils = new NedssUtils();
			String sBeanJndiName = JNDINames.JURISDICTIONSERVICE_EJB;
			Object objref = nedssUtils.lookupBean(sBeanJndiName);
			JurisdictionServiceHome home = (JurisdictionServiceHome) PortableRemoteObject
					.narrow(objref, JurisdictionServiceHome.class);
			JurisdictionService jurisdictionService = home.create();
			HashMap<Object, Object> jurisdictionMap = null;
			if (subjectVO != null) {
				jurisdictionMap = jurisdictionService
						.resolveLabReportJurisdiction(subjectVO, providerVO,
								orderingFacilityVO);
			}

			if (jurisdictionMap!=null && jurisdictionMap.get(ELRConstants.JURISDICTION_HASHMAP_KEY) != null) {
				String jurisdiction = (String) jurisdictionMap
						.get(ELRConstants.JURISDICTION_HASHMAP_KEY);
				orderTestVO.getTheObservationDT().setJurisdictionCd(
						jurisdiction);
			} else
				orderTestVO.getTheObservationDT().setJurisdictionCd(null);
		} catch (Exception e) {
			logger.error("HL7CommonLabUtil.assignJurisdiction: exception caught. "
					+ e);
			throw new NEDSSAppException(
					"HL7CommonLabUtil.assignJurisdiction: exception caught. "
							+ e);
		}

	} // end of assignJursidiction()

	public void processMatchedProxyVO(LabResultProxyVO labResultProxyVO,
			LabResultProxyVO matchedlabResultProxyVO, EdxLabInformationDT edxLabInformationDT) {
		Long matchedObservationUid =null;
		ObservationDT matchedObservationDT = null;
		Collection<ObservationVO> observationCollection = matchedlabResultProxyVO
				.getTheObservationVOCollection();
		Iterator<ObservationVO> it = observationCollection.iterator();
		//Collection<Object> deleteObCollection = new ArrayList<Object>();
		Collection<Object> updatedARCollection = new ArrayList<Object>();
		Collection<Object> updatedPartCollection = new ArrayList<Object>();
		Collection<Object> updatedRoleCollection=new ArrayList<Object>();
		while (it.hasNext()) {
			ObservationVO observationVO = (ObservationVO) it.next();
			String obsDomainCdSt1 = observationVO.getTheObservationDT()
					.getObsDomainCdSt1();
			if (obsDomainCdSt1 != null
					&& obsDomainCdSt1
							.equalsIgnoreCase(EdxELRConstants.ELR_ORDER_CD)) {
				matchedObservationDT =observationVO.getTheObservationDT();

		        //prepareVOutils required the following fields to be set
		        observationVO.getTheObservationDT().getRecordStatusCd();
		        observationVO.getTheObservationDT().getRecordStatusTime();
		        //update the order status
		        if(edxLabInformationDT.getRootObservationVO()!=null && edxLabInformationDT.getRootObservationVO().getTheObservationDT()!=null)
		        	observationVO.getTheObservationDT().setStatusCd(edxLabInformationDT.getRootObservationVO().getTheObservationDT().getStatusCd());
		        observationVO.setItDirty(true);
		        observationVO.setItNew(false);
		        matchedObservationUid=observationVO.getTheObservationDT().getObservationUid();
			}else{
				if(observationVO.getTheObservationDT().getCtrlCdDisplayForm()!=null && 
						(observationVO.getTheObservationDT().getCd().equalsIgnoreCase(EdxELRConstants.ELR_LAB_CD) ||
						observationVO.getTheObservationDT().getCtrlCdDisplayForm().equalsIgnoreCase(EdxELRConstants.ELR_LAB_COMMENT))){
					observationVO.setItDirty(true);
					continue;
				}
				else
					observationVO.setItDelete(true);
				
				if(labResultProxyVO.getTheObservationVOCollection()==null){
					labResultProxyVO.setTheObservationVOCollection(new ArrayList<ObservationVO>());
				}
				labResultProxyVO.getTheObservationVOCollection().add(observationVO);
			} 
				for (Iterator<Object> iterator = observationVO
						.getTheActRelationshipDTCollection().iterator(); iterator.hasNext();)
		        {
					ActRelationshipDT actRelationshipDT = (ActRelationshipDT)iterator.next();
				if (actRelationshipDT.getTypeCd() != null
						&& actRelationshipDT.getTypeCd().equals(
								NEDSSConstants.LAB_REPORT)
						&& actRelationshipDT.getTargetClassCd() != null
						&& actRelationshipDT.getTargetClassCd().equals(
								NEDSSConstants.CASE))
					edxLabInformationDT
							.setOriginalAssociatedPHCUid(actRelationshipDT
									.getTargetActUid());
		            if(	actRelationshipDT.getTypeCd().equals(EdxELRConstants.ELR_AR_LAB_COMMENT)){
						//actRelationshipDT.setTargetActUid(matchedObservationUid);
						updatedARCollection.add(actRelationshipDT);
					}
					else{
						actRelationshipDT.setItDelete(true);
						updatedARCollection.add(actRelationshipDT);
		            }
		            
		            /*actRelationshipDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
		            actRelationshipDT.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
		            actRelationshipDT.setSequenceNbr(null);
		            actRelationshipDT.setItDirty(false);
		            actRelationshipDT.setItNew(true);*/
		        }
				
			
			for (Iterator<Object> itr1 = observationVO.getTheParticipationDTCollection().iterator(); itr1.hasNext();)
            {
                ParticipationDT participationDT = (ParticipationDT)itr1.next();
                participationDT.setItDelete(true);
                participationDT.setItDirty(false);
                participationDT.setItNew(false);
                if(participationDT.getTypeCd().equalsIgnoreCase(EdxELRConstants.ELR_AUTHOR_CD) && participationDT.getCd().equalsIgnoreCase(EdxELRConstants.ELR_SENDING_FACILITY_CD))
                	labResultProxyVO.setSendingFacilityUid(participationDT.getSubjectEntityUid());
                updatedPartCollection.add(participationDT);
    		}
		}
		updatedARCollection.addAll(labResultProxyVO.getTheActRelationshipDTCollection());
		labResultProxyVO.setTheActRelationshipDTCollection(updatedARCollection);
		updatedPartCollection.addAll(labResultProxyVO.getTheParticipationDTCollection());
		labResultProxyVO.setTheParticipationDTCollection(updatedPartCollection);

	
		Collection<Object> rolecoll = new ArrayList<Object>();
		
		Long patientUid = null;
		Collection<Object> coll = matchedlabResultProxyVO.getThePersonVOCollection();
		if(coll!=null){
			Iterator<Object> iterator = coll.iterator();
			while(iterator.hasNext()){
				PersonVO personVO =(PersonVO)iterator.next();
				if (personVO.getThePersonDT() != null && personVO.getThePersonDT().getCdDescTxt() != null && personVO
						.getThePersonDT().getCdDescTxt().equalsIgnoreCase(EdxELRConstants.ELR_PATIENT_DESC)) {
					patientUid = personVO.getThePersonDT().getPersonUid();
					edxLabInformationDT.setPatientUid(patientUid);
				}
				rolecoll.addAll(personVO.getTheRoleDTCollection());
			}
		}
		

		Collection<Object> orgColl = matchedlabResultProxyVO.getTheOrganizationVOCollection();
		if(orgColl!=null){
			Iterator<Object> iterator = orgColl.iterator();
			while(iterator.hasNext()){
				OrganizationVO organizationVO =(OrganizationVO)iterator.next();
				rolecoll.addAll(organizationVO.getTheRoleDTCollection());
			}
		}
		Collection<Object> matColl = matchedlabResultProxyVO.getTheMaterialVOCollection();
		if(matColl!=null){
			Iterator<Object> iterator = matColl.iterator();
			while(iterator.hasNext()){
				MaterialVO materialVO =(MaterialVO)iterator.next();
				rolecoll.addAll(materialVO.getTheRoleDTCollection());
			}
		}
		if(rolecoll!=null){
			for (Iterator<Object> itr1 = rolecoll.iterator(); itr1.hasNext();)
	        {
	            RoleDT roleDT = (RoleDT)itr1.next();
	            roleDT.setItDelete(true);
	            roleDT.setItDirty(false);
	            roleDT.setItNew(false);
	            //rolecoll.add(roleDT);
	            updatedRoleCollection.add(roleDT);
	        }
		}
		updatedRoleCollection.addAll(labResultProxyVO.getTheRoleDTCollection());
		labResultProxyVO.setTheRoleDTCollection(updatedRoleCollection);
		
		if(labResultProxyVO.getTheObservationVOCollection()!=null){
			Iterator<ObservationVO> iter = labResultProxyVO.getTheObservationVOCollection().iterator();
			while(iter.hasNext()){
				ObservationVO obsVO = (ObservationVO)iter.next();
				String obsDomainCdSt1 = obsVO.getTheObservationDT()
						.getObsDomainCdSt1();
				if (obsDomainCdSt1 != null
						&& obsDomainCdSt1
								.equalsIgnoreCase(EdxELRConstants.ELR_ORDER_CD)) {
					obsVO.getTheObservationDT().setObservationUid(matchedObservationUid);
					obsVO.getTheObservationDT().setVersionCtrlNbr(matchedObservationDT.getVersionCtrlNbr());
					obsVO.getTheObservationDT().setProgAreaCd(matchedObservationDT.getProgAreaCd());
					obsVO.getTheObservationDT().setJurisdictionCd(matchedObservationDT.getJurisdictionCd());
					obsVO.getTheObservationDT().setSharedInd(matchedObservationDT.getSharedInd());
					obsVO.getTheObservationDT().setLocalId(matchedObservationDT.getLocalId());
					obsVO.getTheObservationDT().setItDirty(true);
					obsVO.getTheObservationDT().setItNew(false);
					
					obsVO.setItNew(false);
					obsVO.setItDirty(true);
					labResultProxyVO.setItDirty(true);
					labResultProxyVO.setItNew(false);
					break;
				}
				
			}
		}
		if(labResultProxyVO.getTheActRelationshipDTCollection()!=null){
			Iterator<Object> iter = labResultProxyVO.getTheActRelationshipDTCollection().iterator();
			while(iter.hasNext()){
				ActRelationshipDT actRelationshipDT = (ActRelationshipDT)iter.next();
				if(actRelationshipDT.getTargetActUid().compareTo(edxLabInformationDT.getRootObserbationUid())==0 &&
						(! actRelationshipDT.getTypeCd().equals(EdxELRConstants.ELR_SUPPORT_CD)||
						!actRelationshipDT.getTypeCd().equals(EdxELRConstants.ELR_REFER_CD)||
						!actRelationshipDT.getTypeCd().equals(EdxELRConstants.ELR_COMP_CD))){
					actRelationshipDT.setTargetActUid(matchedObservationUid);
					break;
				}
				//else
				//	actRelationshipDT.setItDelete(true);
				
			}
		}
	
	}
	

	public String markAsReviewedHandler(Long observationUid,EdxLabInformationDT edxLabInformationDT,
			NBSSecurityObj nbsSecurityObj) throws
			NEDSSAppConcurrentDataException, java.rmi.RemoteException,
			javax.ejb.EJBException, Exception {
		String markAsReviewedFlag = "";
		NedssUtils nedssUtils = new NedssUtils();
		String sBeanJndiName = JNDINames.OBSERVATION_PROXY_EJB;
		Object objref = nedssUtils.lookupBean(sBeanJndiName);
		logger.debug("objref = " + objref.toString());
		ObservationProxyHome home = (ObservationProxyHome) PortableRemoteObject.narrow(objref, ObservationProxyHome.class);
		ObservationProxy obsProxy = home.create();
		logger.debug("obsProxy = " + obsProxy.getClass());
		try {
			
			if(edxLabInformationDT.getAssociatedPublicHealthCaseUid()==null || edxLabInformationDT.getAssociatedPublicHealthCaseUid().longValue()<0){
				boolean returnValue = obsProxy.processObservation(observationUid, nbsSecurityObj);
				if (returnValue) {
					markAsReviewedFlag = "PROCESSED";
				}
				else {
					markAsReviewedFlag = "UNPROCESSED";
				}
			}else {
				obsProxy.setLabInvAssociation(observationUid, edxLabInformationDT.getAssociatedPublicHealthCaseUid(), nbsSecurityObj);
			}
		}catch(Exception ex){
			logger.error("HL7CommonLabUtil.markAsReviewedHandler Exception thrown. Please check ObservationUid:-"+observationUid);	
			edxLabInformationDT.setLabIsMarkedAsReviewed(false);
			edxLabInformationDT.setErrorText(EdxELRConstants.ELR_MASTER_LOG_ID_12);
			throw new NEDSSAppException(EdxELRConstants.ELR_MASTER_MSG_ID_12);
		}
		return markAsReviewedFlag;

	}

	public String getXMLElementName( XmlObject hl7OBXType){
		String element="";
		try{
			Node node = hl7OBXType.getDomNode();
			while(!"Container".equalsIgnoreCase(node.getNodeName())){
				if(element.length()==0)
					element = node.getNodeName();
				else
					element = node.getNodeName()+"."+element;
				node = node.getParentNode();
}
			logger.debug("element-------------------------------------"+element);
		}catch(Exception ex){
			logger.error("Exception while getting element names from xml:"+ex.getMessage(), ex);
		}
		return element;
	}
	public void updatePersonELRUpdate(LabResultProxyVO labResultProxyVO, LabResultProxyVO matchedLabResultProxyVO){
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
		
		
		Collection<Object> personCollection = matchedLabResultProxyVO.getThePersonVOCollection();
		if(personCollection!=null){
			Iterator<Object> iterator = personCollection.iterator();
			
			while(iterator.hasNext()){
				PersonVO personVO =(PersonVO)iterator.next();
				String perDomainCdStr = personVO.getThePersonDT().getCdDescTxt();
				if(perDomainCdStr!= null && perDomainCdStr.equalsIgnoreCase(EdxELRConstants.ELR_PATIENT_DESC)){
					matchedPersonDT = personVO.getThePersonDT();
				
					matchedPersonUid=matchedPersonDT.getPersonUid();
					matchedPersonParentUid = matchedPersonDT.getPersonParentUid();
					matchedLocalId = matchedPersonDT.getLocalId();
					matchedVersionCtNo = matchedPersonDT.getVersionCtrlNbr();
					
		
				}
				if(perDomainCdStr!= null && perDomainCdStr.equalsIgnoreCase(EdxELRConstants.ELR_PATIENT_DESC)){
				if(personVO.getThePersonNameDTCollection()!=null && personVO.getThePersonNameDTCollection().size()>0){
					for (Iterator<Object> it = personVO.getThePersonNameDTCollection().iterator(); it.hasNext();)
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
				if(personVO.getThePersonRaceDTCollection()!=null && personVO.getThePersonRaceDTCollection().size()>0){
					for (Iterator<Object> it = personVO.getThePersonRaceDTCollection().iterator(); it.hasNext();)
			        {
						PersonRaceDT personRaceDT = (PersonRaceDT)it.next();
						personRaceDT.setItDelete(true);
						personRaceDT.setItDirty(false);
						personRaceDT.setItNew(false);
						hm.put(personRaceDT.getRaceCd(), personRaceDT);
						updatedPersonRaceCollection.add(personRaceDT);
			        }
				}
				if(personVO.getThePersonEthnicGroupDTCollection()!=null && personVO.getThePersonEthnicGroupDTCollection().size()>0){
					for (Iterator<Object> it = personVO.getThePersonEthnicGroupDTCollection().iterator(); it
							.hasNext();) {
						PersonEthnicGroupDT personEthnicGroupDT = (PersonEthnicGroupDT) it.next();
						personEthnicGroupDT.setItDelete(true);
						personEthnicGroupDT.setItDirty(false);
						personEthnicGroupDT.setItNew(false);
						ethnicGroupHm.put(personEthnicGroupDT.getEthnicGroupCd(), personEthnicGroupDT);
						updatedPersonEthnicGroupCollection.add(personEthnicGroupDT);

					}
				}
				if(personVO.getTheEntityIdDTCollection()!=null && personVO.getTheEntityIdDTCollection().size()>0){
					for (Iterator<Object> it = personVO.getTheEntityIdDTCollection().iterator(); it.hasNext();)
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
				if(personVO.getTheEntityLocatorParticipationDTCollection()!=null && personVO.getTheEntityLocatorParticipationDTCollection().size()>0){
					for (Iterator<Object> it = personVO.getTheEntityLocatorParticipationDTCollection().iterator(); it.hasNext();)
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
			}
		}
		
		
		if(labResultProxyVO.getThePersonVOCollection()!=null){
			Iterator<Object> iter = labResultProxyVO.getThePersonVOCollection().iterator();
			while(iter.hasNext()){
				PersonVO personVO =(PersonVO)iter.next();
				String perDomainCdStr = personVO.getThePersonDT().getCdDescTxt();
				if(perDomainCdStr!= null && perDomainCdStr.equalsIgnoreCase(EdxELRConstants.ELR_PATIENT_DESC)){
					
				//	personVO.getThePersonDT().setPersonUid(matchedPersonUid);
					personVO.getThePersonDT().setPersonParentUid(matchedPersonParentUid);
					personVO.getThePersonDT().setLocalId(matchedLocalId);
					personVO.getThePersonDT().setVersionCtrlNbr(matchedVersionCtNo);
					personVO.getThePersonDT().setItDirty(true);
					personVO.getThePersonDT().setItNew(false);
					//personVO.getThePersonDT().setFirstNm(updatedFirstNm);
					//personVO.getThePersonDT().setLastNm(updatedLastNm);
				//	personVO.setIsExistingPatient(true);
					personVO.setItNew(false);
					personVO.setItDirty(true);
					//labResultProxyVO.setItDirty(true);
				//	labResultProxyVO.setItNew(false);
					
					if(perDomainCdStr!= null && perDomainCdStr.equalsIgnoreCase(EdxELRConstants.ELR_PATIENT_DESC)){
						if(personVO.getThePersonNameDTCollection()!=null && personVO.getThePersonNameDTCollection().size()>0){
							for (Iterator<Object> it = personVO.getThePersonNameDTCollection().iterator(); it.hasNext();)
					        {
								PersonNameDT personNameDT = (PersonNameDT)it.next();
								personNameDT.setItNew(true);
								personNameDT.setItDirty(false);
								personNameDT.setItDelete(false);
								personNameDT.setPersonUid(matchedPersonUid);
								personNameDT.setPersonNameSeq(++nameSeq);
					        }
						}
						if(	personVO.getThePersonNameDTCollection() == null)
							personVO.setThePersonNameDTCollection(new ArrayList<Object>());
						personVO.getThePersonNameDTCollection().addAll(updatedPersonNameCollection);
						
						if(personVO.getThePersonRaceDTCollection()!=null && personVO.getThePersonRaceDTCollection().size()>0){
							for (Iterator<Object> it = personVO.getThePersonRaceDTCollection().iterator(); it.hasNext();) {
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
						Collection<Object> personRaceCollection = personVO.getThePersonRaceDTCollection();
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
									personVO.getThePersonRaceDTCollection().add(personRaceDT2);
						        }
						    }
						
						
						if(	personVO.getThePersonRaceDTCollection() == null || (	personVO.getThePersonRaceDTCollection() != null && 	personVO.getThePersonRaceDTCollection().size() == 0)){
							personVO.setThePersonRaceDTCollection(new ArrayList<Object>());
							personVO.getThePersonRaceDTCollection().addAll(updatedPersonRaceCollection);
						}
						if(personVO.getThePersonEthnicGroupDTCollection()!=null && personVO.getThePersonEthnicGroupDTCollection().size()>0){
							for (Iterator<Object> it = personVO.getThePersonEthnicGroupDTCollection().iterator(); it
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
						if(	personVO.getThePersonEthnicGroupDTCollection() == null || (	personVO.getThePersonEthnicGroupDTCollection() != null && 	personVO.getThePersonEthnicGroupDTCollection().size() == 0)){
							personVO.setThePersonEthnicGroupDTCollection(new ArrayList<Object>());
							personVO.getThePersonEthnicGroupDTCollection().addAll(updatedPersonEthnicGroupCollection);
						}
						if(personVO.getTheEntityIdDTCollection()!=null && personVO.getTheEntityIdDTCollection().size()>0){
							for (Iterator<Object> it = personVO.getTheEntityIdDTCollection().iterator(); it.hasNext();)
					        {
								EntityIdDT entityIDDT = (EntityIdDT)it.next();
							
								entityIDDT.setItNew(true);
								entityIDDT.setItDirty(false);
								entityIDDT.setItDelete(false);
								entityIDDT.setEntityUid(matchedPersonUid);
								entityIDDT.setEntityIdSeq(++entityIdSeq);
								
					        }
						}
						if(	personVO.getTheEntityIdDTCollection() == null)
							personVO.setTheEntityIdDTCollection(new ArrayList<Object>());
						personVO.getTheEntityIdDTCollection().addAll(updatedtheEntityIdDTCollection);
						
						
						if(personVO.getTheEntityLocatorParticipationDTCollection()!=null  && personVO.getTheEntityLocatorParticipationDTCollection().size()>0){
							for (Iterator<Object> it = personVO.getTheEntityLocatorParticipationDTCollection().iterator(); it.hasNext();)
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
						if(	personVO.getTheEntityLocatorParticipationDTCollection() == null)
							personVO.setTheEntityLocatorParticipationDTCollection(new ArrayList<Object>());
						personVO.getTheEntityLocatorParticipationDTCollection().addAll(updatedtheEntityLocatorParticipationDTCollection);
						
					
						}
					personVO.setRole(null);
				}
			
				
			}
			
			
		}

	}

	public Long getMatchedPersonUID(LabResultProxyVO matchedlabResultProxyVO) {
		
		// TODO Auto-generated method stub
		Long matchedPersonUid = null;
		Collection<Object> personCollection = matchedlabResultProxyVO.getThePersonVOCollection();
		if(personCollection!=null){
			Iterator<Object> iterator = personCollection.iterator();

			while(iterator.hasNext()){
				PersonVO personVO =(PersonVO)iterator.next();
				String perDomainCdStr = personVO.getThePersonDT().getCdDescTxt();
				if(perDomainCdStr!= null && perDomainCdStr.equalsIgnoreCase(EdxELRConstants.ELR_PATIENT_DESC)){
					matchedPersonUid = personVO.getThePersonDT().getPersonUid();
				}
			}
		}
		return matchedPersonUid;
	}


}
