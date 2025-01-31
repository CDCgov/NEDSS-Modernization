
package gov.cdc.nedss.systemservice.ejb.edxdocumentejb.labutil;

import gov.cdc.nedss.association.dt.ParticipationDT;
import gov.cdc.nedss.association.dt.RoleDT;
import gov.cdc.nedss.entity.entityid.dt.EntityIdDT;
import gov.cdc.nedss.entity.organization.dt.OrganizationDT;
import gov.cdc.nedss.entity.organization.dt.OrganizationNameDT;
import gov.cdc.nedss.entity.organization.vo.OrganizationVO;
import gov.cdc.nedss.phdc.HL7HDType;
import gov.cdc.nedss.phdc.HL7MSHType;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dt.EdxELRLabMapDT;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dt.EdxLabInformationDT;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.LabResultProxyVO;
import java.util.ArrayList;
import java.util.Collection;

/**
 * 
 * @author Pradeep Kumar Sharma
 *Utility class to process the Message Header section of NBS
 */
public class MessageHandler {


	public LabResultProxyVO getMessage(HL7MSHType messageHandlerType, EdxLabInformationDT edxLabInformationDT){

		LabResultProxyVO labResultProxyVO  = new LabResultProxyVO(); 
		if(labResultProxyVO.getTheOrganizationVOCollection()==null)
			labResultProxyVO.setTheOrganizationVOCollection(new ArrayList<Object>());
		HL7HDType sendingFacility= messageHandlerType.getSendingFacility();
		EdxELRLabMapDT edxELRSendingMapDT =processSendingFacility(sendingFacility, edxLabInformationDT);
		//edxLabInformationDT.setNextUid((edxLabInformationDT.getNextUid()-1));
		createOrganizationVO(labResultProxyVO, edxELRSendingMapDT,edxLabInformationDT);
		//sendingOrgVO.setRole(EdxELRConstants.ELR_SENDING_FACILITY_CD);
		//labResultProxyVO.getTheOrganizationVOCollection().add(sendingOrgVO);
		edxLabInformationDT.setMessageControlID(messageHandlerType.getMessageControlID());
		//HL7HDType receivingApplication=messageHandlerType.getReceivingApplication();
		//String receivingApplicationName=receivingApplication.getHL7NamespaceID();

		//HL7HDType receivingFacility=messageHandlerType.getReceivingFacility();
		//EdxELRLabMapDT edxELRReceivingMapDT =processReceivingFacility(receivingFacility, edxLabInformationDT);
		//edxLabInformationDT.setNextUid((edxLabInformationDT.getNextUid()-1));
		//createOrganizationVO(labResultProxyVO,edxELRReceivingMapDT,edxLabInformationDT);
		

		//HL7TSType DateTimeOfMessage=messageHandlerType.getDateTimeOfMessage();

		//NOTE: Not used by NBS String Security=messageHandlerType.getSecurity();
		//HL7MSGType MessageType=messageHandlerType.getMessageType();

		/*Collection<Object> actIdDTCollection =new ArrayList<Object>();
		String messageControlID=messageHandlerType.getMessageControlID();
		edxLabInformationDT.setMessageControlID(messageControlID);
		ActIdDT actIdDT = new ActIdDT();
		actIdDT.setActUid(new Long(rootObservationUid));
		actIdDT.setActIdSeq(new Integer(1));
		actIdDT.setRootExtensionTxt(messageControlID);
		actIdDT.setAssigningAuthorityCd(edxELRSendingMapDT.getEntityIdAssigningAuthorityCd());
		actIdDT.setAssigningAuthorityDescTxt(edxELRSendingMapDT.getEntityIdAssigningAuthorityDescTxt());
		actIdDT.setTypeCd(EdxELRConstant.ELR_MESSAGE_CTRL_CD);
		actIdDT.setRecordStatusCd(EdxELRConstant.ELR_ACTIVE);
		actIdDTCollection.add(actIdDT);
		labResultProxyVO.setTheActIdDTCollection(actIdDTCollection);
		*/
		
		//TODO LOG this HL7PTType  ProcessingID=messageHandlerType.getProcessingID();
		//TODO LOG this HL7VIDType VersionID=messageHandlerType.getVersionID();
		/**NOTE: Not used by NBS 
		Object FieldSeparator= messageHandlerType.getFieldSeparator();
		Object EncodingCharacters=messageHandlerType.getEncodingCharacters();
		HL7HDType  SendingApplication = messageHandlerType.getSendingApplication();
		HL7NMType  SequenceNumber=messageHandlerType.getSequenceNumber();
		String ContinuationPointer=messageHandlerType.getContinuationPointer();
		String AcceptAcknowledgmentType=messageHandlerType.getAcceptAcknowledgmentType();
		String ApplicationAcknowledgmentType=messageHandlerType.getApplicationAcknowledgmentType();
		Object CountryCode=messageHandlerType.getCountryCode();
		Object CharacterSet=messageHandlerType.getCharacterSetArray();
		HL7CEType PrincipalLanguageOfMessage=messageHandlerType.getPrincipalLanguageOfMessage();
		String AlternateCharacterSetHandlingScheme=messageHandlerType.getAlternateCharacterSetHandlingScheme();
		Object MessageProfileIdentifier=messageHandlerType.getMessageProfileIdentifierArray();
		 */
		return labResultProxyVO;
	}

	public EdxELRLabMapDT processSendingFacility(HL7HDType SendingFacility, EdxLabInformationDT edxLabInformationDT){
		EdxELRLabMapDT edxLabMapDT  = new EdxELRLabMapDT();
		edxLabMapDT.setRoleCd(EdxELRConstants.ELR_SENDING_FACILITY_CD);
		edxLabMapDT.setRoleCdDescTxt(EdxELRConstants.ELR_SENDING_FACILITY_DESC);
		edxLabMapDT.setRoleSubjectClassCd(EdxELRConstants.ELR_SENDING_HCFAC);
		edxLabMapDT.setEntityCd(EdxELRConstants.ELR_SENDING_LAB_CD);
		edxLabMapDT.setEntityCdDescTxt(EdxELRConstants.ELR_LABORATORY_DESC);
		edxLabMapDT.setEntityStandardIndustryClassCd(EdxELRConstants.ELR_standard_industry_desc_txt);
		edxLabMapDT.setEntityStandardIndustryDescTxt(EdxELRConstants.ELR_standard_industry_desc_txt);
		edxLabMapDT.setEntityDisplayNm(SendingFacility.getHL7NamespaceID());
		edxLabInformationDT.setSendingFacilityName(SendingFacility.getHL7NamespaceID());
		edxLabInformationDT.setSendingFacilityClia(SendingFacility.getHL7UniversalID());
		edxLabMapDT.setEntityUid(new Long(edxLabInformationDT.getNextUid()));
		edxLabInformationDT.setUniversalIdType(SendingFacility.getHL7UniversalIDType());
		edxLabMapDT.setEntityIdAssigningAuthorityCd(SendingFacility.getHL7UniversalIDType());
		edxLabMapDT.setEntityIdAssigningAuthorityDescTxt(SendingFacility.getHL7NamespaceID());
		edxLabMapDT.setEntityIdRootExtensionTxt(SendingFacility.getHL7UniversalID());
		edxLabMapDT.setEntityIdTypeCd(EdxELRConstants.ELR_FACILITY_CD);
		edxLabMapDT.setEntityIdTypeDescTxt(EdxELRConstants.ELR_FACILITY_DESC);

		edxLabMapDT.setParticipationActClassCd(EdxELRConstants.ELR_OBS);
		edxLabMapDT.setParticipationCd(EdxELRConstants.ELR_SENDING_FACILITY_CD);
		edxLabMapDT.setParticipationSubjectClassCd(EdxELRConstants.ELR_ORG);
		edxLabMapDT.setParticipationTypeCd(EdxELRConstants.ELR_AUTHOR_CD);
		edxLabMapDT.setParticipationTypeDescTxt(EdxELRConstants.ELR_AUTHOR_DESC);
		edxLabMapDT.setParticipationActUid(new Long(edxLabInformationDT.getRootObserbationUid()));
		edxLabMapDT.setParticipationEntityUid(edxLabMapDT.getEntityUid());
		return edxLabMapDT;
	}

	public EdxELRLabMapDT processReceivingFacility(HL7HDType receivingFacility, EdxLabInformationDT edxLabInformationDT){
		EdxELRLabMapDT edxLabMapDT  = new EdxELRLabMapDT();
		edxLabMapDT.setRoleCd(EdxELRConstants.ELR_RECEIVING_FACILITY_CD);
		edxLabMapDT.setRoleCdDescTxt(EdxELRConstants.ELR_RECEIVING_FACILITY_DESC);
		edxLabMapDT.setRoleSubjectClassCd(EdxELRConstants.ELR_RECEIVING_ROLE_CLASS_CD);
		edxLabMapDT.setEntityCd(EdxELRConstants.ELR_RECEIVING_ORG_CLASS_CD);
		edxLabMapDT.setEntityCdDescTxt(EdxELRConstants.ELR_RECEIVING_ORG_CLASS_DESC);
		edxLabMapDT.setEntityStandardIndustryClassCd(EdxELRConstants.ELR_RECEIVING_STANDARD_INDUSTRY_CLASS_CD);
		edxLabMapDT.setEntityStandardIndustryDescTxt(EdxELRConstants.ELR_RECEIVING_STANDARD_INDUSTRY_CLASS_DESC);
		edxLabMapDT.setEntityDisplayNm(receivingFacility.getHL7NamespaceID());

		edxLabMapDT.setEntityIdAssigningAuthorityCd(receivingFacility.getHL7UniversalIDType());
		edxLabMapDT.setEntityIdAssigningAuthorityDescTxt(receivingFacility.getHL7NamespaceID());
		edxLabMapDT.setEntityIdRootExtensionTxt(receivingFacility.getHL7UniversalID());

		edxLabMapDT.setEntityUid(new Long(edxLabInformationDT.getNextUid()));

		edxLabMapDT.setEntityIdTypeCd(null);
		edxLabMapDT.setEntityIdTypeDescTxt(null);

		edxLabMapDT.setParticipationActClassCd(EdxELRConstants.ELR_OBS);
		edxLabMapDT.setParticipationCd(EdxELRConstants.ELR_RECEIVING_FACILITY_CD);
		edxLabMapDT.setParticipationSubjectClassCd(EdxELRConstants.ELR_ORG);
		edxLabMapDT.setParticipationSubjectEntityCd(EdxELRConstants.ELR_ORG);  
		edxLabMapDT.setParticipationTypeCd(EdxELRConstants.ELR_TRACER_CD);
		edxLabMapDT.setParticipationTypeDescTxt(EdxELRConstants.ELR_TRACER_DESC);
		edxLabMapDT.setParticipationActUid(new Long(edxLabInformationDT.getRootObserbationUid()));
		edxLabMapDT.setParticipationActClassCd(EdxELRConstants.ELR_OBS);
		edxLabMapDT.setParticipationEntityUid(new Long(edxLabInformationDT.getNextUid()));
		return edxLabMapDT;
	}


	public LabResultProxyVO createOrganizationVO(LabResultProxyVO labResultProxyVO, EdxELRLabMapDT edxLabMapDT, EdxLabInformationDT edxLabInformationDT){
		Hl7ToNBSObjectConverter hl7ToNBSObjectConverter = new Hl7ToNBSObjectConverter();
		OrganizationVO organizationVO = new OrganizationVO();
		organizationVO.setRole(edxLabMapDT.getRoleCd());
		RoleDT role = new RoleDT();
		role.setSubjectEntityUid(edxLabMapDT.getEntityUid());
		role.setRoleSeq(new Long(1));
		role.setCd(edxLabMapDT.getRoleCd());
		role.setAddTime(edxLabMapDT.getAddTime());
		role.setLastChgTime(edxLabMapDT.getAddTime());
		role.setCdDescTxt(edxLabMapDT.getRoleCdDescTxt());
		role.setSubjectClassCd(edxLabMapDT.getRoleSubjectClassCd());
		role.setSubjectEntityUid(edxLabMapDT.getEntityUid());
		role.setRecordStatusCd(EdxELRConstants.ELR_ACTIVE); 
		role.setStatusCd(EdxELRConstants.ELR_ACTIVE_CD);
		role.setItNew(true);
		role.setItDirty(false);
		if(labResultProxyVO.getTheRoleDTCollection()==null)
			labResultProxyVO.setTheRoleDTCollection(new ArrayList<Object>());
		labResultProxyVO.getTheRoleDTCollection().add(role);
		

		Collection<Object> participationDtColl = new ArrayList<Object>();
		ParticipationDT participationDT = new ParticipationDT();
		//participationDT.setRoleSeq(new Integer(1));
		participationDT.setActClassCd( edxLabMapDT.getParticipationActClassCd());
		participationDT.setCd(edxLabMapDT.getParticipationCd());
		participationDT.setSubjectClassCd(edxLabMapDT.getParticipationSubjectClassCd());
		participationDT.setTypeCd(edxLabMapDT.getParticipationTypeCd());
		participationDT.setTypeDescTxt(edxLabMapDT.getParticipationTypeDescTxt());
		participationDT.setActUid(edxLabMapDT.getParticipationActUid());
		participationDT.setSubjectEntityUid(edxLabMapDT.getParticipationEntityUid());
		hl7ToNBSObjectConverter.defaultParticipationDT(participationDT,edxLabInformationDT);
		
		edxLabInformationDT.setAddReasonCd(participationDT.getAddReasonCd());
		
		participationDtColl.add(participationDT);
		//organizationVO.setTheParticipationDTCollection(participationDtColl);
		if(labResultProxyVO.getTheParticipationDTCollection()==null)
			labResultProxyVO.setTheParticipationDTCollection(new ArrayList<Object>());
		labResultProxyVO.getTheParticipationDTCollection().add(participationDT);
		OrganizationDT organizationDT = new OrganizationDT();
		organizationDT.setOrganizationUid(edxLabMapDT.getEntityUid());
		organizationDT.setCd(edxLabMapDT.getEntityCd());
		organizationDT.setAddTime(edxLabMapDT.getAddTime());
		organizationDT.setCdDescTxt(edxLabMapDT.getEntityCdDescTxt());
		organizationDT.setStandardIndustryClassCd(edxLabMapDT.getEntityIdAssigningAuthorityCd());
		organizationDT.setStandardIndustryDescTxt(edxLabMapDT.getEntityIdAssigningAuthorityDescTxt());
		organizationDT.setElectronicInd(EdxELRConstants.ELR_ELECTRONIC_IND);
		organizationDT.setDisplayNm(edxLabMapDT.getEntityDisplayNm());
		organizationDT.setOrganizationUid(edxLabMapDT.getEntityUid());
		organizationVO.setTheOrganizationDT(organizationDT);

		Collection<Object> organizationNameDTColl = new ArrayList<Object>();
		OrganizationNameDT organizationNameDT = new OrganizationNameDT();
		organizationNameDT.setOrganizationNameSeq(new Integer(1));
		organizationNameDT.setNmUseCd(EdxELRConstants.ELR_LEGAL_NAME);
		organizationNameDT.setNmTxt(edxLabMapDT.getEntityDisplayNm());
		organizationNameDT.setOrganizationUid(organizationDT.getOrganizationUid());
		organizationNameDTColl.add(organizationNameDT);
		organizationVO.setTheOrganizationNameDTCollection(organizationNameDTColl);

		if(edxLabMapDT.getEntityIdTypeCd()!=null &&edxLabMapDT.getEntityIdTypeDescTxt()!=null){
			Collection<Object> entityIdDTColl = new ArrayList<Object>();
			EntityIdDT entityIdDT = new EntityIdDT();
			entityIdDT.setEntityIdSeq(new Integer(1));
			if(edxLabMapDT.getEntityIdRootExtensionTxt()!=null && edxLabMapDT.getEntityIdRootExtensionTxt().trim().length()>0){
				entityIdDT.setRootExtensionTxt(edxLabMapDT.getEntityIdRootExtensionTxt());
				edxLabInformationDT.setSendingFacilityClia(edxLabMapDT.getEntityIdRootExtensionTxt());
				
			}else{
				entityIdDT.setRootExtensionTxt(EdxELRConstants.ELR_DEFAULT_CLIA);
				edxLabInformationDT.setSendingFacilityClia(EdxELRConstants.ELR_DEFAULT_CLIA);
			}
			entityIdDT.setAssigningAuthorityCd(edxLabMapDT.getEntityIdAssigningAuthorityCd());
			if(entityIdDT.getAssigningAuthorityCd().equalsIgnoreCase(EdxELRConstants.ELR_CLIA_CD))
				entityIdDT.setAssigningAuthorityDescTxt(EdxELRConstants.ELR_CLIA_DESC);
			entityIdDT.setTypeCd(edxLabMapDT.getEntityIdTypeCd());
			entityIdDT.setTypeDescTxt(edxLabMapDT.getEntityIdTypeDescTxt());
			entityIdDT.setRecordStatusCd(EdxELRConstants.ELR_ACTIVE);
			entityIdDT.setStatusCd(EdxELRConstants.ELR_ACTIVE_CD);
			entityIdDT.setAddTime(edxLabMapDT.getAddTime());
			entityIdDT.setAsOfDate(edxLabMapDT.getAsOfDate());
			entityIdDTColl.add(entityIdDT);
			organizationVO.setTheEntityIdDTCollection(entityIdDTColl);
		}
		if(labResultProxyVO.getTheOrganizationVOCollection()==null)
			labResultProxyVO.setTheOrganizationVOCollection(new ArrayList<Object>());
		labResultProxyVO.getTheOrganizationVOCollection().add(organizationVO);
		
		return labResultProxyVO;
	}
}
