package gov.cdc.nedss.systemservice.ejb.edxdocumentejb.labutil;

import gov.cdc.nedss.act.actid.dt.ActIdDT;
import gov.cdc.nedss.act.observation.dt.ObsValueCodedDT;
import gov.cdc.nedss.act.observation.dt.ObservationDT;
import gov.cdc.nedss.act.observation.dt.ObservationReasonDT;
import gov.cdc.nedss.act.observation.vo.ObservationVO;
import gov.cdc.nedss.association.dt.ActRelationshipDT;
import gov.cdc.nedss.association.dt.ParticipationDT;
import gov.cdc.nedss.association.dt.RoleDT;
import gov.cdc.nedss.entity.entityid.dt.EntityIdDT;
import gov.cdc.nedss.entity.organization.vo.OrganizationVO;
import gov.cdc.nedss.entity.person.dt.PersonNameDT;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.locator.dt.EntityLocatorParticipationDT;
import gov.cdc.nedss.phdc.HL7CWEType;
import gov.cdc.nedss.phdc.HL7EIType;
import gov.cdc.nedss.phdc.HL7NDLType;
import gov.cdc.nedss.phdc.HL7OBRType;
import gov.cdc.nedss.phdc.HL7PatientResultSPMType;
import gov.cdc.nedss.phdc.HL7XCNType;
import gov.cdc.nedss.phdc.HL7XTNType;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.LabResultProxyVO;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dao.ELRXRefDAOImpl;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dt.EdxLabIdentiferDT;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dt.EdxLabInformationDT;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;



/**
 * @author Pradeep Kumar Sharma
 *Utility class to parse observation information
 */
public class ObservationRequest {
	static final LogUtils logger = new LogUtils(ObservationRequest.class.getName());

	public LabResultProxyVO getObservationRequest(HL7OBRType hl7OBRType, HL7PatientResultSPMType hl7PatientResultSPMType, LabResultProxyVO labResultProxyVO, EdxLabInformationDT edxLabInformationDT) throws NEDSSAppException {
		try {
			ObservationVO observationVO = new ObservationVO(); 
			ObservationDT observationDT= new ObservationDT(); 
			HL7CommonLabUtil hL7CommonLabUtil = new HL7CommonLabUtil();
			observationDT.setObsDomainCd(EdxELRConstants.CTRL_CD_DISPLAY_FORM);
			observationDT.setCtrlCdDisplayForm(EdxELRConstants.CTRL_CD_DISPLAY_FORM);
			
			if(hl7OBRType.getResultStatus()!=null){
				String toCode = CachedDropDowns.findToCode("ELR_LCA_STATUS", hl7OBRType.getResultStatus(), "ACT_OBJ_ST");
				if (toCode != null && !toCode.equals("") && !toCode.equals(" ")){
					observationDT.setStatusCd(toCode.trim());
					
				}else{
					edxLabInformationDT.setObsStatusTranslated(false);
					edxLabInformationDT.setErrorText(EdxELRConstants.ELR_MASTER_LOG_ID_13);
					throw new NEDSSAppException(EdxELRConstants.TRANSLATE_OBS_STATUS);
				}	
			}else{
				edxLabInformationDT.setObsStatusTranslated(false);
				edxLabInformationDT.setErrorText(EdxELRConstants.ELR_MASTER_LOG_ID_13);
				throw new NEDSSAppException(EdxELRConstants.TRANSLATE_OBS_STATUS);
			}
			//observationDT.setStatusCd(EdxELRConstants.ELR_OBS_STATUS_CD);
			observationDT.setElectronicInd(EdxELRConstants.ELR_ELECTRONIC_IND);
			
			if(hl7OBRType.getSetIDOBR()!=null && hl7OBRType.getSetIDOBR().getHL7SequenceID()!=null && hl7OBRType.getSetIDOBR().getHL7SequenceID().equalsIgnoreCase("1"))
				observationDT.setObservationUid(new Long(edxLabInformationDT.getRootObserbationUid()));
			else if(!hl7OBRType.getSetIDOBR().getHL7SequenceID().equalsIgnoreCase("1")){
				observationDT.setObservationUid(new Long(edxLabInformationDT.getNextUid()));
				//edxLabInformationDT.setNextUid((edxLabInformationDT.getNextUid()-1));
			}else{
				observationDT.setObservationUid(new Long(edxLabInformationDT.getRootObserbationUid()));
			}
			observationDT.setItNew(true);
			observationDT.setItDirty(false);
			observationVO.setItNew(true);
			observationVO.setItDirty(false);
			observationDT.setObsDomainCdSt1(EdxELRConstants.ELR_ORDER_CD);

			hl7OBRType.getAssistantResultInterpreterArray();
			hl7OBRType.getCollectorsCommentArray();
			if(hl7OBRType.getDangerCode()!=null)
				edxLabInformationDT.setDangerCode(hl7OBRType.getDangerCode().getHL7Identifier());
			hl7OBRType.getDiagnosticServSectID();  
			hl7OBRType.getEscortRequired();
			hl7OBRType.getFillerField1();
			hl7OBRType.getFillerField2();
			hl7OBRType.getFillerSupplementalServiceInformationArray();

			OrganizationVO sendingOrgVO = null;
			EntityIdDT sendingFacilityId = null;
			Collection<Object> orgCollection = labResultProxyVO.getTheOrganizationVOCollection();
			Iterator<Object> it = orgCollection.iterator();
			while(it.hasNext()){
				OrganizationVO organizationVO= (OrganizationVO)it.next();
				if(organizationVO.getRole()!=null && organizationVO.getRole().equalsIgnoreCase(EdxELRConstants.ELR_SENDING_FACILITY_CD))
					sendingOrgVO= organizationVO;
				Collection<Object> entityCollection = sendingOrgVO.getTheEntityIdDTCollection();
				Iterator<Object> entityIterator =  entityCollection.iterator();
				while(entityIterator.hasNext()){
					EntityIdDT entityIdDT =  (EntityIdDT)entityIterator.next();
					if(entityIdDT.getTypeCd().equalsIgnoreCase(EdxELRConstants.ELR_FACILITY_CD))
						sendingFacilityId = entityIdDT;
				}
			}


			Collection<Object> actIdDTColl =  new ArrayList<Object>();
			ActIdDT actIdDT= new ActIdDT();
			actIdDT.setActIdSeq(new Integer(1));
			actIdDT.setActUid(new Long(edxLabInformationDT.getRootObserbationUid()));
			actIdDT.setRootExtensionTxt(edxLabInformationDT.getMessageControlID()); 
			actIdDT.setAssigningAuthorityCd(sendingFacilityId.getAssigningAuthorityCd());
			actIdDT.setAssigningAuthorityDescTxt(sendingFacilityId.getAssigningAuthorityDescTxt());
			actIdDT.setTypeCd(EdxELRConstants.ELR_MESSAGE_CTRL_CD);
			actIdDT.setTypeDescTxt(EdxELRConstants.ELR_MESSAGE_CTRL_DESC);
			actIdDT.setRecordStatusCd(EdxELRConstants.ELR_ACTIVE);
			actIdDTColl.add(actIdDT);

			HL7EIType  fillerType =hl7OBRType.getFillerOrderNumber();
			if(hl7OBRType.getParent()==null ){
				if(fillerType==null || (fillerType!= null && fillerType.getHL7EntityIdentifier()==null)){
					edxLabInformationDT.setFillerNumberPresent(false);
					edxLabInformationDT.setErrorText(EdxELRConstants.ELR_MASTER_LOG_ID_13);
					throw new NEDSSAppException(EdxELRConstants.FILLER_FAIL);
				}
				else{
					edxLabInformationDT.setFillerNumber(fillerType.getHL7EntityIdentifier());
				}
			}
			ActIdDT act2IdDT = new ActIdDT();
			act2IdDT.setActUid(new Long(edxLabInformationDT.getRootObserbationUid()));
			act2IdDT.setActIdSeq(new Integer(2));
			act2IdDT.setAssigningAuthorityCd(sendingFacilityId.getAssigningAuthorityCd());
			act2IdDT.setAssigningAuthorityDescTxt(sendingFacilityId.getAssigningAuthorityDescTxt());
			act2IdDT.setRootExtensionTxt(fillerType.getHL7EntityIdentifier());
			act2IdDT.setTypeCd(EdxELRConstants.ELR_FILLER_NUMBER_CD);
			act2IdDT.setTypeDescTxt(EdxELRConstants.ELR_FILLER_NUMBER_DESC);
			act2IdDT.setRecordStatusCd(EdxELRConstants.ELR_ACTIVE);
			actIdDTColl.add(act2IdDT);

			observationVO.setTheActIdDTCollection(actIdDTColl);
			if(hl7OBRType.getUniversalServiceIdentifier()==null){
				edxLabInformationDT.setUniversalServiceIdMissing(true);
				edxLabInformationDT.setErrorText(EdxELRConstants.ELR_MASTER_LOG_ID_13);
				throw new NEDSSAppException(EdxELRConstants.UNIVSRVCID);
			}
			else{
		
				if(hl7OBRType.getUniversalServiceIdentifier()!= null && hl7OBRType.getUniversalServiceIdentifier().getHL7NameofCodingSystem()!=null && hl7OBRType.getUniversalServiceIdentifier().getHL7NameofCodingSystem().equals(EdxELRConstants.ELR_LOINC_CD)){
					observationDT.setCdSystemCd(EdxELRConstants.ELR_LOINC_CD);
					observationDT.setCdSystemDescTxt(EdxELRConstants.ELR_LOINC_DESC);
				}

				if(hl7OBRType.getUniversalServiceIdentifier().getHL7Identifier()!=null)
					observationDT.setCd(hl7OBRType.getUniversalServiceIdentifier().getHL7Identifier());


				if(hl7OBRType.getUniversalServiceIdentifier().getHL7Text()!=null)
					observationDT.setCdDescTxt(hl7OBRType.getUniversalServiceIdentifier().getHL7Text());


				if(observationDT.getCd()!=null)
					observationDT.setAltCd(hl7OBRType.getUniversalServiceIdentifier().getHL7AlternateIdentifier());
				else
					observationDT.setCd(hl7OBRType.getUniversalServiceIdentifier().getHL7AlternateIdentifier());

				if(observationDT.getCdDescTxt()!=null)
					observationDT.setAltCdDescTxt(hl7OBRType.getUniversalServiceIdentifier().getHL7AlternateText());
				else
					observationDT.setCdDescTxt(hl7OBRType.getUniversalServiceIdentifier().getHL7AlternateText());

				if(observationDT.getCdSystemCd()!=null && observationDT.getCdSystemCd().equalsIgnoreCase(EdxELRConstants.ELR_LOINC_CD) && (observationDT.getAltCd()!=null) ){
					observationDT.setAltCdSystemCd(EdxELRConstants.ELR_LOCAL_CD);
					observationDT.setAltCdSystemDescTxt(EdxELRConstants.ELR_LOCAL_DESC);
				}else if((observationDT.getCd()!=null || observationDT.getCdDescTxt()!=null) && observationDT.getCdSystemCd()==null){
					observationDT.setCdSystemCd(EdxELRConstants.ELR_LOCAL_CD);
					observationDT.setCdSystemDescTxt(EdxELRConstants.ELR_LOCAL_DESC);
				}
				if((observationDT.getCd()==null || 
						observationDT.getCd().trim().equalsIgnoreCase("")) &&
				(observationDT.getAltCd()==null || 
						observationDT.getAltCd().trim().equalsIgnoreCase(""))	){
					edxLabInformationDT.setOrderTestNameMissing(true);
					edxLabInformationDT.setErrorText(EdxELRConstants.ELR_MASTER_LOG_ID_19);
					String xmlElementName = hL7CommonLabUtil.getXMLElementName(hl7OBRType)+".UniversalServiceIdentifier";
					throw new NEDSSAppException(EdxELRConstants.NO_ORDTEST_NAME+" XMLElementName: "+xmlElementName);
				}

			}
			observationDT.setPriorityCd(hl7OBRType.getPriorityOBR());
			Hl7ToNBSObjectConverter hl7ToNBSObjectConverter = new Hl7ToNBSObjectConverter();
			//observationDT.setActivityFromTime(hl7ToNBSObjectConverter.processHL7TSType(hl7OBRType.getSpecimenReceivedDateTime(), EdxELRConstants.DATE_VALIDATION_OBR_SPECIMEN_RECEIVED_TIME_MSG));
			// the field is deprecated according to a note in 2.5.1 ORU Mapping to ODS.docs, using this field to store 15-Order Effective Date/Time
			observationDT.setActivityFromTime(edxLabInformationDT.getOrderEffectiveDate());
			observationDT.setActivityToTime(hl7ToNBSObjectConverter.processHL7TSType(hl7OBRType.getResultsRptStatusChngDateTime(), EdxELRConstants.DATE_VALIDATION_OBR_RESULTS_RPT_STATUS_CHNG_TO_TIME_MSG));
			observationDT.setEffectiveFromTime(hl7ToNBSObjectConverter.processHL7TSType(hl7OBRType.getObservationDateTime(),EdxELRConstants.DATE_VALIDATION_OBR_OBSERVATION_DATE_MSG));
			observationDT.setEffectiveToTime(hl7ToNBSObjectConverter.processHL7TSType(hl7OBRType.getObservationEndDateTime(),EdxELRConstants.DATE_VALIDATION_OBR_OBSERVATION_END_DATE_MSG));
			HL7CWEType[] reasonArray =hl7OBRType.getReasonforStudyArray();
			Collection<Object> obsReasonDTColl = new ArrayList<Object>();
			for(int i=0; i<reasonArray.length; i++){
				ObservationReasonDT obsReasonDT= new ObservationReasonDT();
				HL7CWEType reason = reasonArray[i];
				if(reason.getHL7Identifier()!=null){
					obsReasonDT.setReasonCd(reason.getHL7Identifier());
					obsReasonDT.setReasonDescTxt(reason.getHL7Text());
				}else if(reason.getHL7AlternateIdentifier()!=null){
					obsReasonDT.setReasonCd(reason.getHL7AlternateIdentifier());
					obsReasonDT.setReasonDescTxt(reason.getHL7AlternateText());
				}
				
				if((reason.getHL7Identifier()==null || reason.getHL7Identifier().trim().equalsIgnoreCase("")) &&
				(reason.getHL7AlternateIdentifier()==null || reason.getHL7AlternateIdentifier().trim().equalsIgnoreCase(""))){
					edxLabInformationDT.setReasonforStudyCdMissing(true);
					edxLabInformationDT.setErrorText(EdxELRConstants.ELR_MASTER_LOG_ID_19);
					String xmlElementName = hL7CommonLabUtil.getXMLElementName(hl7OBRType)+".ReasonforStudy";
					throw new NEDSSAppException(EdxELRConstants.NO_REASON_FOR_STUDY+" XMLElementName: "+xmlElementName);
				}
				
				obsReasonDTColl.add(obsReasonDT);
			}
			if(edxLabInformationDT.getLastChgTime()==null)
				observationDT.setRptToStateTime(edxLabInformationDT.getAddTime());
			else
				observationDT.setRptToStateTime(edxLabInformationDT.getLastChgTime());
			observationVO.setTheObservationDT(observationDT);
			observationVO.setTheObservationReasonDTCollection(obsReasonDTColl);
			if(labResultProxyVO.getTheObservationVOCollection()==null)
				labResultProxyVO.setTheObservationVOCollection(new ArrayList<ObservationVO>());
			labResultProxyVO.getTheObservationVOCollection().add(observationVO);
			if(edxLabInformationDT.getRootObservationVO()==null)
				edxLabInformationDT.setRootObservationVO(observationVO);
				
				
			if(hl7OBRType.getParent()==null){
				processRootOBR(hl7OBRType, observationDT, labResultProxyVO, hl7PatientResultSPMType, edxLabInformationDT);
			}
			
			if(hl7OBRType.getParent()!=null){
				processSusOBR(hl7OBRType, observationDT, labResultProxyVO, edxLabInformationDT);
			}
			if(hl7OBRType.getParentResult()==null){
				//HL7PRLType  parentObsType = hl7OBRType.getParentResult();

				edxLabInformationDT.setParentObservationUid(new Long(0));
				edxLabInformationDT.setParentObsInd(false); 
			}

			if(hl7OBRType.getResultCopiesToArray()!=null){
				for(int i=0; i<hl7OBRType.getResultCopiesToArray().length; i++){
					HL7XCNType providerType =hl7OBRType.getResultCopiesToArray()[i];
					edxLabInformationDT.setRole(EdxELRConstants.ELR_COPY_TO_CD);
					PersonVO personVO = getProviderVO(providerType,null, labResultProxyVO,edxLabInformationDT);
					labResultProxyVO.getThePersonVOCollection().add(personVO);
					
				}

			}

		} catch (Exception e) {
			logger.fatal("Exception thrown at ObservationRequest.getObservationRequest:"+e.getMessage(), e);
			throw new NEDSSAppException("Exception thrown at ObservationRequest.getObservationRequest:"+ e.getMessage());
		}     



		return labResultProxyVO;

	}

	private PersonVO  getProviderVO(HL7XCNType orderingProvider, Collection<Object> entitylocatorColl,LabResultProxyVO labResultProxyVO,EdxLabInformationDT edxLabInformationDT) throws NEDSSAppException{
		PersonVO personVO =null;
		HL7PatientProcessor hL7PatientProcessor = new HL7PatientProcessor();
		Hl7ToNBSObjectConverter hl7ToNBSObjectConverter = new Hl7ToNBSObjectConverter();
		
		try {
			EntityIdDT entityIdDT = new EntityIdDT();
			entityIdDT.setEntityUid(new Long(edxLabInformationDT.getNextUid()));
			entityIdDT.setAddTime(edxLabInformationDT.getAddTime());
			entityIdDT.setEntityIdSeq(new Integer(1));
			entityIdDT.setRootExtensionTxt(orderingProvider.getHL7IDNumber());
			entityIdDT.setTypeCd(EdxELRConstants.ELR_PROVIDER_REG_NUM_CD);
			entityIdDT.setTypeDescTxt(EdxELRConstants.ELR_PROVIDER_REG_NUM_DESC);
			entityIdDT.setAssigningAuthorityCd(edxLabInformationDT.getSendingFacilityClia());
			entityIdDT.setAssigningAuthorityDescTxt(edxLabInformationDT.getSendingFacilityName());
			entityIdDT.setAssigningAuthorityIdType(edxLabInformationDT.getUniversalIdType());
			entityIdDT.setRecordStatusCd(EdxELRConstants.ELR_ACTIVE);
			entityIdDT.setStatusCd(EdxELRConstants.ELR_ACTIVE_CD);
			entityIdDT.setAsOfDate(edxLabInformationDT.getAddTime());
			entityIdDT.setItNew(true);
			entityIdDT.setItDirty(false);
			personVO =hL7PatientProcessor.personVO(labResultProxyVO,edxLabInformationDT);
			if(entitylocatorColl!=null)
				personVO.setTheEntityLocatorParticipationDTCollection(entitylocatorColl);
			if(personVO.getTheEntityIdDTCollection()==null)
				personVO.setTheEntityIdDTCollection(new ArrayList<Object>());
			if(entityIdDT.getEntityUid()!=null)
				personVO.getTheEntityIdDTCollection().add(entityIdDT);
			
			if(edxLabInformationDT.getRole().equalsIgnoreCase(EdxELRConstants.ELR_OP_CD)){
				ParticipationDT participationDT = new ParticipationDT();
				participationDT.setActClassCd(EdxELRConstants.ELR_OBS);
				participationDT.setCd(EdxELRConstants.ELR_OP_CD);
				participationDT.setActUid(edxLabInformationDT.getRootObserbationUid());
				participationDT.setTypeCd(EdxELRConstants.ELR_ORDERER_CD);
				participationDT.setTypeDescTxt(EdxELRConstants.ELR_ORDERER_DESC);
				participationDT =hl7ToNBSObjectConverter.defaultParticipationDT(participationDT,edxLabInformationDT);
				participationDT.setSubjectClassCd(EdxELRConstants.ELR_PERSON_CD);
				participationDT.setSubjectEntityUid(personVO.getThePersonDT().getPersonUid());
				if(labResultProxyVO.getTheParticipationDTCollection()==null)
					labResultProxyVO.setTheParticipationDTCollection(new ArrayList<Object>());
				labResultProxyVO.getTheParticipationDTCollection().add(participationDT);
				personVO.setRole(EdxELRConstants.ELR_OP_CD);

			}
			PersonNameDT personNameDT = new PersonNameDT();
			if(orderingProvider.getHL7FamilyName()!=null && orderingProvider.getHL7FamilyName().getHL7Surname()!=null )
				personNameDT.setLastNm(orderingProvider.getHL7FamilyName().getHL7Surname());
			personNameDT.setFirstNm(orderingProvider.getHL7GivenName());
			personNameDT.setMiddleNm(orderingProvider.getHL7SecondAndFurtherGivenNamesOrInitialsThereof());
			personNameDT.setNmPrefix(orderingProvider.getHL7Prefix());
			personNameDT.setNmSuffix(orderingProvider.getHL7Suffix());
			personNameDT.setNmDegree(orderingProvider.getHL7Degree());
			personNameDT.setNmUseCd(orderingProvider.getHL7NameTypeCode());

			personNameDT.setAddTime(edxLabInformationDT.getAddTime());
			personNameDT.setLastChgTime(edxLabInformationDT.getAddTime());
			personNameDT.setAddUserId(edxLabInformationDT.getUserId());
			personNameDT.setPersonNameSeq(new Integer(1));
			personNameDT.setLastChgUserId(edxLabInformationDT.getUserId());
			personVO.setThePersonNameDTCollection(new ArrayList<Object>());
			personNameDT.setNmUseCd(EdxELRConstants.ELR_LEGAL_NAME); 
			personVO.getThePersonNameDTCollection().add(personNameDT);
			
		} catch (Exception e) {
			logger.error("Exception thrown at ObservationRequest.getOrderingProviderVO:"+e);
			throw new NEDSSAppException("Exception thrown at ObservationRequest.getOrderingProviderVO:"+ e);

		}

		return personVO;
	}


	private void processSusOBR(HL7OBRType hl7OBRType,ObservationDT observationDT,  LabResultProxyVO labResultProxyVO, EdxLabInformationDT edxLabInformationDT) throws NEDSSAppException{
		try {
			EdxLabIdentiferDT edxLabIdentiferDT= null;
			if(hl7OBRType.getParentResult()== null ||
					hl7OBRType.getParentResult().getParentObservationIdentifier()==null||
					(hl7OBRType.getParentResult().getParentObservationIdentifier().getHL7Identifier()==null &&
					hl7OBRType.getParentResult().getParentObservationIdentifier().getHL7AlternateIdentifier()==null)){
				edxLabInformationDT.setReflexOrderedTestCdMissing(true);
				edxLabInformationDT.setErrorText(EdxELRConstants.ELR_MASTER_LOG_ID_13);
				throw new NEDSSAppException(EdxELRConstants.ELR_MASTER_LOG_ID_13);
			}
			Long parentObservation = null;
			boolean fillerMatch = false;
			if(edxLabInformationDT.getEdxLabIdentiferDTColl()!=null){
				Iterator<Object> iter = edxLabInformationDT.getEdxLabIdentiferDTColl().iterator();
				while(iter.hasNext()){
					edxLabIdentiferDT= (EdxLabIdentiferDT)iter.next();
					if(edxLabIdentiferDT.getIdentifer()!=null 
							&& (edxLabIdentiferDT.getIdentifer().equals(hl7OBRType.getParentResult().getParentObservationIdentifier().getHL7Identifier())|| edxLabIdentiferDT.getIdentifer().equals(hl7OBRType.getParentResult().getParentObservationIdentifier().getHL7AlternateIdentifier()))
							&& edxLabIdentiferDT.getSubMapID()!=null 
							&& edxLabIdentiferDT.getSubMapID().equals(hl7OBRType.getParentResult().getParentObservationSubidentifier())
							&& edxLabIdentiferDT.getObservationValues()!=null 
							&& hl7OBRType.getParentResult().getParentObservationValueDescriptor()!=null 
							&& Arrays.toString(edxLabIdentiferDT.getObservationValues()).indexOf(hl7OBRType.getParentResult().getParentObservationValueDescriptor().getHL7String())>0){
						parentObservation= edxLabIdentiferDT.getObservationUid();
					}
					if(edxLabInformationDT.getFillerNumber()!=null 
							&& hl7OBRType.getParent().getHL7FillerAssignedIdentifier()!=null 
							&& edxLabInformationDT.getFillerNumber().equals(hl7OBRType.getParent().getHL7FillerAssignedIdentifier().getHL7EntityIdentifier())){
						fillerMatch = true;
					}
				}
				if(parentObservation == null || !fillerMatch){
					edxLabInformationDT.setChildSuscWithoutParentResult(true);
					edxLabInformationDT.setErrorText(EdxELRConstants.ELR_MASTER_LOG_ID_13);
					throw new NEDSSAppException(EdxELRConstants.CHILD_SUSC_WITH_NO_PARENT_RESULT);
				}
			}
			if(edxLabInformationDT.getEdxSusLabDTMap()== null || edxLabInformationDT.getEdxSusLabDTMap().get(parentObservation)!=null){

				ObservationVO obsVO= new ObservationVO();
				ObservationDT obsDT= new ObservationDT(); 
				obsDT.setCd(EdxELRConstants.ELR_LAB222_CD);
				obsDT.setCdDescTxt(EdxELRConstants.ELR_NO_INFO_DESC);
				//obsDT.setObsDomainCd(EdxELRConstants.CTRL_CD_DISPLAY_FORM);
				obsDT.setObsDomainCdSt1(EdxELRConstants.ELR_REF_ORDER_CD);
				obsDT.setCtrlCdDisplayForm(EdxELRConstants.CTRL_CD_DISPLAY_FORM);
				obsDT.setElectronicInd(EdxELRConstants.ELR_ELECTRONIC_IND);
				obsDT.setStatusTime(edxLabInformationDT.getAddTime());
				obsDT.setObservationUid(new Long(edxLabInformationDT.getNextUid()));
				obsDT.setStatusCd(EdxELRConstants.ELR_OBS_STATUS_CD);
				obsDT.setItNew(true);
				obsDT.setItDirty(false);
				obsVO.setItNew(true);
				obsVO.setItDirty(false);
				ObsValueCodedDT obsValueCodedDT = new ObsValueCodedDT();
				obsValueCodedDT.setObservationUid(observationDT.getObservationUid()); 
				obsValueCodedDT.setCode(EdxELRConstants.ELR_YES_CD);

				if(obsVO.getTheObsValueCodedDTCollection()==null)
					obsVO.setTheObsValueCodedDTCollection(new ArrayList<Object>());
				obsVO.getTheObsValueCodedDTCollection().add(obsValueCodedDT);
				obsVO.setTheObservationDT(obsDT); 
				ActRelationshipDT actRelationshipDT = new ActRelationshipDT();
				actRelationshipDT.setRecordStatusCd(EdxELRConstants.ELR_ACTIVE);
				actRelationshipDT.setStatusCd(EdxELRConstants.ELR_ACTIVE_CD);
				actRelationshipDT.setTypeCd(EdxELRConstants.ELR_SUPPORT_CD);
				actRelationshipDT.setItNew(true);
				actRelationshipDT.setItDirty(false);
				actRelationshipDT.setTypeDescTxt(EdxELRConstants.ELR_SUPPORT_DESC);
				actRelationshipDT.setSourceActUid(obsVO.getTheObservationDT().getObservationUid());
				actRelationshipDT.setTargetActUid(edxLabInformationDT.getRootObserbationUid());
				actRelationshipDT.setTargetClassCd(EdxELRConstants.ELR_OBS);
				actRelationshipDT.setSourceClassCd(EdxELRConstants.ELR_OBS);
				actRelationshipDT.setAddTime(edxLabInformationDT.getAddTime());
				actRelationshipDT.setLastChgTime(edxLabInformationDT.getAddTime());
				actRelationshipDT.setRecordStatusTime(edxLabInformationDT.getAddTime());
				if(labResultProxyVO.getTheActRelationshipDTCollection()==null)
					labResultProxyVO.setTheActRelationshipDTCollection(new ArrayList<Object>());
				labResultProxyVO.getTheActRelationshipDTCollection().add(actRelationshipDT);

				observationDT.setObsDomainCdSt1(EdxELRConstants.ELR_REF_RESULT_CD);
				ActRelationshipDT arDT = new ActRelationshipDT();
				arDT.setTypeCd(EdxELRConstants.ELR_REFER_CD);
				arDT.setTypeDescTxt(EdxELRConstants.ELR_REFER_DESC);
				arDT.setSourceActUid(obsVO.getTheObservationDT().getObservationUid());
				arDT.setTargetActUid(parentObservation);
				arDT.setRecordStatusCd(EdxELRConstants.ELR_ACTIVE);
				arDT.setRecordStatusTime(edxLabInformationDT.getAddTime());
				arDT.setTargetClassCd(EdxELRConstants.ELR_OBS);
				arDT.setSourceClassCd(EdxELRConstants.ELR_OBS);
				arDT.setItNew(true);
				arDT.setItDirty(false);
				labResultProxyVO.getTheActRelationshipDTCollection().add(arDT);
				if(hl7OBRType.getParent()!=null){
					if(labResultProxyVO.getTheObservationVOCollection()==null)
						labResultProxyVO.setTheObservationVOCollection(new ArrayList<ObservationVO>());
					labResultProxyVO.getTheObservationVOCollection().add(obsVO);
				}
				edxLabInformationDT.getEdxSusLabDTMap().put(parentObservation, obsVO.getTheObservationDT().getObservationUid());
			}

			if(parentObservation!=null){
				Long uid = (Long)edxLabInformationDT.getEdxSusLabDTMap().get(parentObservation);
				if(uid!=null){
					edxLabInformationDT.setParentObservationUid(uid);
					edxLabInformationDT.setParentObsInd(true);
				}
			}
		} catch (Exception e) {
			logger.error("Exception thrown at ObservationRequest.processSusOBR:"+e.getMessage(), e);
			throw new NEDSSAppException("Exception thrown at ObservationRequest.processSusOBR:"+ e);
		}

	}
	private PersonVO  getCollectorVO(HL7XCNType collector,LabResultProxyVO labResultProxyVO, EdxLabInformationDT edxLabInformationDT) throws NEDSSAppException{
		PersonVO personVO;
		Hl7ToNBSObjectConverter hl7ToNBSObjectConverter = new Hl7ToNBSObjectConverter();
		HL7PatientProcessor hL7PatientProcessor = new HL7PatientProcessor();
		try {
			edxLabInformationDT.setRole(EdxELRConstants.ELR_PROVIDER_CD);
			EntityIdDT entityIdDT = new EntityIdDT();

			entityIdDT.setEntityUid(new Long(edxLabInformationDT.getNextUid()));
			entityIdDT.setAddTime(edxLabInformationDT.getAddTime());
			entityIdDT.setEntityIdSeq(new Integer(1));
			entityIdDT.setRootExtensionTxt(collector.getHL7IDNumber());
			entityIdDT.setTypeCd(EdxELRConstants.ELR_EMP_IDENT_CD);
			entityIdDT.setTypeDescTxt(EdxELRConstants.ELR_EMP_IDENT_DESC);
			entityIdDT.setAssigningAuthorityCd(edxLabInformationDT.getSendingFacilityClia());
			entityIdDT.setAssigningAuthorityDescTxt(edxLabInformationDT.getSendingFacilityName());
			entityIdDT.setAssigningAuthorityIdType(edxLabInformationDT.getUniversalIdType());
			entityIdDT.setRecordStatusCd(EdxELRConstants.ELR_ACTIVE);
			entityIdDT.setStatusCd(EdxELRConstants.ELR_ACTIVE_CD);
			entityIdDT.setAsOfDate(edxLabInformationDT.getAddTime());
			entityIdDT.setItNew(true);
			entityIdDT.setItDirty(false);
			edxLabInformationDT.setRole(EdxELRConstants.ELR_PROVIDER_CD);

			personVO = hL7PatientProcessor.personVO(labResultProxyVO,edxLabInformationDT);
			
			
			if(personVO.getTheEntityIdDTCollection()==null){
				personVO.setTheEntityIdDTCollection(new ArrayList<Object>());
			}
			personVO.getTheEntityIdDTCollection().add(entityIdDT);
			
			PersonNameDT personNameDT = new PersonNameDT();
			if(collector.getHL7FamilyName()!=null && collector.getHL7FamilyName().getHL7Surname()!=null)
				personNameDT.setLastNm(collector.getHL7FamilyName().getHL7Surname());
			personNameDT.setFirstNm(collector.getHL7GivenName());
			personNameDT.setMiddleNm(collector.getHL7SecondAndFurtherGivenNamesOrInitialsThereof());
			personNameDT.setNmPrefix(collector.getHL7Prefix());
			personNameDT.setNmSuffix(collector.getHL7Suffix());
			personNameDT.setNmDegree(collector.getHL7Degree());
			personNameDT.setPersonNameSeq(new Integer(1));
			personNameDT.setNmUseCd(collector.getHL7NameTypeCode());
			//Defect 5542 Transcriptionist causing issue
			if (personNameDT.getNmUseCd() == null)
				personNameDT.setNmUseCd(EdxELRConstants.ELR_LEGAL_NAME);

			personNameDT.setAddTime(edxLabInformationDT.getAddTime());
			personNameDT.setLastChgTime(edxLabInformationDT.getAddTime());
			personNameDT.setAddUserId(edxLabInformationDT.getUserId());
			personNameDT.setLastChgUserId(edxLabInformationDT.getUserId());
			personVO.setThePersonNameDTCollection(new ArrayList<Object>());
			personVO.getThePersonNameDTCollection().add(personNameDT);

			RoleDT roleDT = new RoleDT();
			roleDT.setSubjectEntityUid(personVO.getThePersonDT().getPersonUid());
			roleDT.setCd(EdxELRConstants.ELR_SPECIMEN_PROCURER_CD);
			roleDT.setCdDescTxt(EdxELRConstants.ELR_SPECIMEN_PROCURER_DESC);
			roleDT.setSubjectClassCd(EdxELRConstants.ELR_PROV_CD);
			roleDT.setRoleSeq(new Long(1));
			roleDT.setScopingEntityUid(edxLabInformationDT.getPatientUid());
			roleDT.setScopingClassCd(EdxELRConstants.ELR_PATIENT_CD);
			roleDT.setScopingRoleCd(EdxELRConstants.ELR_PATIENT_CD);
			roleDT.setScopingRoleSeq(new Integer(1));
			roleDT.setRecordStatusCd(EdxELRConstants.ELR_ACTIVE);
			roleDT.setStatusCd(EdxELRConstants.ELR_ACTIVE_CD);
			roleDT.setItNew(true);
			roleDT.setItDirty(false);
			if(labResultProxyVO.getTheRoleDTCollection()==null)
				labResultProxyVO.setTheRoleDTCollection(new ArrayList<Object>());
			labResultProxyVO.getTheRoleDTCollection().add(roleDT);


		} catch (Exception e) {
			logger.error("Exception thrown at ObservationRequest.getCollectorVO:"+e.getMessage(), e);
			throw new NEDSSAppException("Exception thrown at ObservationRequest.getCollectorVO:"+ e);
		}

		return personVO;
	}

	private PersonVO  getOtherProviderVO(HL7NDLType  providerType, LabResultProxyVO labResultProxyVO, EdxLabInformationDT edxLabInformationDT) throws NEDSSAppException{

		PersonVO personVO;
		try {
			if(providerType==null)
				return null;
			HL7PatientProcessor hL7PatientProcessor = new HL7PatientProcessor();
			Hl7ToNBSObjectConverter hl7ToNBSObjectConverter =new Hl7ToNBSObjectConverter();
			personVO = hL7PatientProcessor.personVO(labResultProxyVO,edxLabInformationDT);

			if(providerType.getHL7Name()!=null && providerType.getHL7Name().getHL7IDNumber()!=null){

				Collection<Object> entityColl = new ArrayList<Object>();
				EntityIdDT entityIdDT = new EntityIdDT();
				entityIdDT.setEntityUid(new Long(edxLabInformationDT.getNextUid()));
				entityIdDT.setEntityIdSeq(new Integer(1));
				entityIdDT.setAddTime(edxLabInformationDT.getAddTime());
				entityIdDT.setRootExtensionTxt(providerType.getHL7Name().getHL7IDNumber());
				entityIdDT.setTypeCd(EdxELRConstants.ELR_EMP_IDENT_CD);
				entityIdDT.setTypeDescTxt(EdxELRConstants.ELR_EMP_IDENT_DESC);
				entityIdDT.setAssigningAuthorityCd(edxLabInformationDT.getSendingFacilityClia());
				entityIdDT.setAssigningAuthorityDescTxt(edxLabInformationDT.getSendingFacilityName());
				entityIdDT.setAssigningAuthorityIdType(edxLabInformationDT.getUniversalIdType());
				entityIdDT.setRecordStatusCd(EdxELRConstants.ELR_ACTIVE);
				entityIdDT.setStatusCd(EdxELRConstants.ELR_ACTIVE_CD);

				entityIdDT.setAsOfDate(edxLabInformationDT.getAddTime());
				entityIdDT.setItNew(true);
				entityIdDT.setItDirty(false);
				entityColl.add(entityIdDT);
				if(personVO.getTheEntityIdDTCollection()==null)
					personVO.setTheEntityIdDTCollection(new ArrayList<Object>());
				if(entityIdDT.getEntityUid()!=null)
					personVO.getTheEntityIdDTCollection().add(entityIdDT);
			}

			ParticipationDT participationDT= new ParticipationDT();
			participationDT.setSubjectEntityUid(personVO.getThePersonDT().getPersonUid());

			if(edxLabInformationDT.getRole().equals(EdxELRConstants.ELR_LAB_PROVIDER_CD)){
				participationDT.setCd(EdxELRConstants.ELR_LAB_PROVIDER_CD);
				participationDT.setTypeCd(EdxELRConstants.ELR_LAB_VERIFIER_CD);
				participationDT.setTypeDescTxt(EdxELRConstants.ELR_LAB_VERIFIER_DESC);

			}else if(edxLabInformationDT.getRole().equals(EdxELRConstants.ELR_LAB_VERIFIER_CD)){
				participationDT.setCd(EdxELRConstants.ELR_LAB_VERIFIER_CD);
				participationDT.setTypeCd(EdxELRConstants.ELR_LAB_VERIFIER_CD);
				participationDT.setTypeDescTxt(EdxELRConstants.ELR_LAB_VERIFIER_DESC);

			}else if(edxLabInformationDT.getRole().equals(EdxELRConstants.ELR_LAB_PERFORMER_CD)){
				participationDT.setCd(EdxELRConstants.ELR_LAB_PROVIDER_CD);
				participationDT.setTypeCd(EdxELRConstants.ELR_LAB_PERFORMER_CD);
				participationDT.setTypeDescTxt(EdxELRConstants.ELR_LAB_PERFORMER_DESC);

			}else if(edxLabInformationDT.getRole().equals(EdxELRConstants.ELR_LAB_ENTERER_CD)){
				participationDT.setCd(EdxELRConstants.ELR_LAB_ENTERER_CD);
				participationDT.setTypeCd(EdxELRConstants.ELR_LAB_ENTERER_CD);
				participationDT.setTypeDescTxt(EdxELRConstants.ELR_LAB_ENTERER_DESC);

				//participationDT.setRoleSeq(new Integer(1));
			}else if(edxLabInformationDT.getRole().equals(EdxELRConstants.ELR_LAB_ASSISTANT_CD)){
				participationDT.setCd(EdxELRConstants.ELR_LAB_ASSISTANT_CD);
				participationDT.setTypeCd(EdxELRConstants.ELR_LAB_ASSISTANT_CD);
				participationDT.setTypeDescTxt(EdxELRConstants.ELR_LAB_ASSISTANT_DESC);

			}
			participationDT =hl7ToNBSObjectConverter.defaultParticipationDT(participationDT,edxLabInformationDT);


			participationDT.setSubjectClassCd(EdxELRConstants.ELR_PERSON_CD);
			participationDT.setActClassCd(EdxELRConstants.ELR_OBS);
			participationDT.setActUid(edxLabInformationDT.getRootObserbationUid());
			labResultProxyVO.getTheParticipationDTCollection().add(participationDT);
			edxLabInformationDT.setRole(EdxELRConstants.ELR_PROVIDER_CD);
			personVO = hl7ToNBSObjectConverter.processCNNPersonName(providerType.getHL7Name(),personVO);
			if(labResultProxyVO.getThePersonVOCollection()==null)
				labResultProxyVO.setThePersonVOCollection(new ArrayList<Object>());
			labResultProxyVO.getThePersonVOCollection().add(personVO);
		} catch (Exception e) {
			logger.error("Exception thrown at ObservationRequest.getCollectorVO:"+e.getMessage(), e);
			throw new NEDSSAppException("Exception thrown at ObservationRequest.getCollectorVO:"+ e);
		}
		return personVO;
	}

	private void processRootOBR(HL7OBRType hl7OBRType,ObservationDT observationDT,  LabResultProxyVO labResultProxyVO,  HL7PatientResultSPMType hl7PatientResultSPMType,EdxLabInformationDT edxLabInformationDT) throws NEDSSAppException{
		
		
		try {
			HL7SpecimenProcessor hL7SpecimenProcessor = new HL7SpecimenProcessor();
			Hl7ToNBSObjectConverter hl7ToNBSObjectConverter  = new Hl7ToNBSObjectConverter();
			hl7OBRType.getMedicallyNecessaryDuplicateProcedureReason();
			PersonVO collectorVO = null;
			HL7XCNType[] collectorArray = hl7OBRType.getCollectorIdentifierArray();
			if(collectorArray!=null && collectorArray.length>1)
				edxLabInformationDT.setMultipleCollector(true);
			if(collectorArray!=null){
				for(int i=0; i<collectorArray.length;){
					HL7XCNType collector= collectorArray[i];
					collectorVO =getCollectorVO(collector, labResultProxyVO,edxLabInformationDT);
					labResultProxyVO.getThePersonVOCollection().add(collectorVO);
					break;
				}
			}
			if(hl7OBRType.getRelevantClinicalInformation()!=null)
				observationDT.setTxt(hl7OBRType.getRelevantClinicalInformation());
//			if(hl7OBRType.getSpecimenSource()!=null){
//				logger.debug("ObservationRequest.getObservationRequest specimen is being processes");
//				HL7SpecimenProcessor.processSpecimen(hl7OBRType, labResultProxyVO, observationDT, collectorVO, edxLabInformationDT);
//			}else 
			if(hl7PatientResultSPMType!=null){
				logger.debug("ObservationRequest.getObservationRequest specimen is being processes for 2.5.1 message type");
				hL7SpecimenProcessor.process251Specimen( hl7PatientResultSPMType,  labResultProxyVO,  observationDT,  collectorVO,  edxLabInformationDT);
			}
			HL7XCNType[] orderingProviderArray = hl7OBRType.getOrderingProviderArray();
			if(orderingProviderArray!=null && orderingProviderArray.length>1){
				edxLabInformationDT.setMultipleOrderingProvider(true);
			}
			PersonVO orderingProviderVO= null;
			if(orderingProviderArray!=null && orderingProviderArray.length>0){
				for(int i=0; i<orderingProviderArray.length;){   
					HL7XCNType orderingProvider=orderingProviderArray[i];
					Collection<Object> entitylocatorColl =null; 
					
					PersonVO providerVO =null;
					if(edxLabInformationDT.getOrderingProviderVO()!=null){
						providerVO =edxLabInformationDT.getOrderingProviderVO();
						entitylocatorColl=providerVO.getTheEntityLocatorParticipationDTCollection();
						if(labResultProxyVO.getThePersonVOCollection().contains(providerVO))
							labResultProxyVO.getThePersonVOCollection().remove(providerVO);
					}
					edxLabInformationDT.setRole(EdxELRConstants.ELR_OP_CD);
					orderingProviderVO=getProviderVO(orderingProvider,entitylocatorColl,labResultProxyVO, edxLabInformationDT);
					edxLabInformationDT.setOrderingProvider(true);
					break;
				}
				if(hl7OBRType.getOrderCallbackPhoneNumberArray()!=null && orderingProviderVO!=null){
				//HL7XTNType[]  orderingProvPhoneArray = hl7OBRType.getOrderCallbackPhoneNumberArray();
					for(int i=0; i<hl7OBRType.getOrderCallbackPhoneNumberArray().length; ){
						HL7XTNType orderingProvPhone  =hl7OBRType.getOrderCallbackPhoneNumberArray()[i];
						EntityLocatorParticipationDT elpt =hl7ToNBSObjectConverter.personTelePhoneType(orderingProvPhone, EdxELRConstants.ELR_PROVIDER_CD, orderingProviderVO);
						elpt.setUseCd(EdxELRConstants.ELR_WORKPLACE_CD);
						break;
					}
				}
				//edxLabInformationDT.setNextUid((edxLabInformationDT.getNextUid()-1));
				if(labResultProxyVO.getThePersonVOCollection()==null)
					labResultProxyVO.setThePersonVOCollection(new ArrayList<Object>());
				if(orderingProviderVO!=null)
					labResultProxyVO.getThePersonVOCollection().add(orderingProviderVO);
				/*if(orderingProviderVO==null){
					edxLabInformationDT.setMissingOrderingProvider(true);	
					edxLabInformationDT.setErrorText(EdxELRConstants.ELR_MASTER_LOG_ID_13);
					new NEDSSAppException(EdxELRConstants.MISSING_ORD_PROV);
				}*/

			}else{
				if(edxLabInformationDT.getOrderingProviderVO()!=null){
					edxLabInformationDT.setOrderingProvider(false);
					PersonVO providerVO =edxLabInformationDT.getOrderingProviderVO();
					if(labResultProxyVO.getThePersonVOCollection().contains(providerVO))
						labResultProxyVO.getThePersonVOCollection().remove(providerVO);
				}
			}
			if(edxLabInformationDT.isMissingOrderingProvider() && edxLabInformationDT.isMissingOrderingFacility()){
				edxLabInformationDT.setMissingOrderingProviderandFacility(true);
				edxLabInformationDT.setErrorText(EdxELRConstants.ELR_MASTER_LOG_ID_13);
				//throe new NEDSSAppException(EdxELRConstants.MISSING_ORD_PROV);
				throw new NEDSSAppException("HL7ORCProcessorget.getORCProcessing: Both Ordering Provider and Ordering facility are null. Please check!!!");
			}
			
			HL7NDLType  princResultInterpretor = hl7OBRType.getPrincipalResultInterpreter();
			edxLabInformationDT.setMultiplePrincipalInterpreter(false);
			edxLabInformationDT.setRole(EdxELRConstants.ELR_LAB_PROVIDER_CD);
			getOtherProviderVO( princResultInterpretor,labResultProxyVO,  edxLabInformationDT);
		


			if( hl7OBRType.getAssistantResultInterpreterArray()!=null){
				for(int i = 0; i<hl7OBRType.getAssistantResultInterpreterArray().length; i++){
					HL7NDLType  assPrincResultInterpretor =hl7OBRType.getAssistantResultInterpreterArray()[i];
					edxLabInformationDT.setRole(EdxELRConstants.ELR_LAB_ASSISTANT_CD);
					getOtherProviderVO( assPrincResultInterpretor,labResultProxyVO,  edxLabInformationDT);
				}
			}


			if( hl7OBRType.getTechnicianArray()!=null){
				for(int i = 0; i<hl7OBRType.getTechnicianArray().length; i++){
					HL7NDLType  technician =hl7OBRType.getTechnicianArray()[i];
					edxLabInformationDT.setRole(EdxELRConstants.ELR_LAB_PERFORMER_CD);
					getOtherProviderVO( technician,labResultProxyVO,  edxLabInformationDT);
				}
			}
			if( hl7OBRType.getTranscriptionistArray()!=null){
				for(int i = 0; i<hl7OBRType.getTranscriptionistArray().length; i++){
					HL7NDLType  technician =hl7OBRType.getTranscriptionistArray()[i];
					edxLabInformationDT.setRole(EdxELRConstants.ELR_LAB_ENTERER_CD);
					getOtherProviderVO( technician,labResultProxyVO,  edxLabInformationDT);
				}
			}
		} catch (NEDSSAppException e) {
			logger.error(" Exception thrown at ObservationRequest.processRootOBR:"+e.getMessage(), e);
			throw new NEDSSAppException("Exception thrown at ObservationRequest.processRootOBR:"+ e.getMessage() +e);

		}
	
	}

}
