package gov.cdc.nedss.systemservice.ejb.edxdocumentejb.labutil;

import gov.cdc.nedss.association.dt.ParticipationDT;
import gov.cdc.nedss.association.dt.RoleDT;
import gov.cdc.nedss.entity.organization.dt.OrganizationDT;
import gov.cdc.nedss.entity.organization.dt.OrganizationNameDT;
import gov.cdc.nedss.entity.organization.vo.OrganizationVO;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.locator.dt.EntityLocatorParticipationDT;
import gov.cdc.nedss.phdc.HL7ORCType;
import gov.cdc.nedss.phdc.HL7XADType;
import gov.cdc.nedss.phdc.HL7XCNType;
import gov.cdc.nedss.phdc.HL7XONType;
import gov.cdc.nedss.phdc.HL7XTNType;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.LabResultProxyVO;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dt.EdxLabInformationDT;
import gov.cdc.nedss.util.LogUtils;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 
 * @author Pradeep Kumar Sharma
 * 
 */
public class HL7ORCProcessor {
	static final LogUtils logger = new LogUtils(HL7ORCProcessor.class.getName());

	private LabResultProxyVO getOrderingProvider(HL7ORCType hl7ORCType,
			LabResultProxyVO labResultProxyVO,
			EdxLabInformationDT edxLabInformationDT) throws NEDSSAppException {
		try {
			Hl7ToNBSObjectConverter hl7ToNBSObjectConverter = new Hl7ToNBSObjectConverter();
			HL7XADType address = null;
			HL7XADType[] addressArray = hl7ORCType
					.getOrderingProviderAddressArray();
			if(addressArray!=null && addressArray.length!=0){
				edxLabInformationDT.setRole(EdxELRConstants.ELR_OP_CD);
				edxLabInformationDT.setOrderingProvider(true);
				PersonVO personVO = new PersonVO();
				personVO.getThePersonDT().setAddUserId(EdxELRConstants.ELR_ADD_USER_ID);
				for (int i = 0; i < addressArray.length; ) {
					address = addressArray[i];
					if (address != null) {
						hl7ToNBSObjectConverter.personAddressType(address,
										EdxELRConstants.ELR_OP_CD, personVO);
						if (personVO.getTheEntityLocatorParticipationDTCollection() == null)
							personVO.setTheEntityLocatorParticipationDTCollection(new ArrayList<Object>());
						break;
					}
				}
				personVO.setRole(EdxELRConstants.ELR_OP_CD);
				edxLabInformationDT.setOrderingProviderVO(personVO);
				labResultProxyVO.getThePersonVOCollection().add(personVO);
				edxLabInformationDT.setMissingOrderingProvider(false);
			}else{
			edxLabInformationDT.setMissingOrderingProvider(true);
			}
		} catch (Exception e) {
			logger.error("Exception thrown by HL7ORCProcessor.getOrderingProvider "
					+ e.getMessage(), e);
			throw new NEDSSAppException("Exception thrown at HL7ORCProcessor.getOrderingProvider:"+ e);
		}

		return labResultProxyVO;

	}

	private OrganizationVO getOrderingFacility(HL7ORCType hl7ORCType,
			LabResultProxyVO labResultProxyVO,
			EdxLabInformationDT edxLabInformationDT) throws NEDSSAppException {
		OrganizationVO organizationVO = new OrganizationVO();
		try {
			Hl7ToNBSObjectConverter hl7ToNBSObjectConverter = new Hl7ToNBSObjectConverter();
			HL7XADType[] addressArray = hl7ORCType
					.getOrderingFacilityAddressArray();
			if(addressArray!=null && addressArray.length!=0){
				OrganizationDT organizationDT = new OrganizationDT();
				organizationVO.setItNew(true);
				organizationVO.setItDirty(false);
				organizationVO.setRole(EdxELRConstants.ELR_OP_CD);
				organizationDT.setOrganizationUid(new Long(edxLabInformationDT
						.getNextUid()));
				//edxLabInformationDT.setNextUid(edxLabInformationDT.getNextUid());
				organizationDT.setCd(EdxELRConstants.ELR_OTHER_CD);
				organizationDT.setCdDescTxt(EdxELRConstants.ELR_OTHER_DESC);
				organizationDT
						.setStandardIndustryClassCd(EdxELRConstants.ELR_RECEIVING_STANDARD_INDUSTRY_CLASS_CD);
				organizationDT
						.setStandardIndustryDescTxt(EdxELRConstants.ELR_RECEIVING_STANDARD_INDUSTRY_CLASS_DESC);
				organizationDT.setElectronicInd(EdxELRConstants.ELR_ELECTRONIC_IND);
				organizationDT.setItNew(true);
				organizationDT.setItDirty(false);
				organizationVO.setTheOrganizationDT(organizationDT);
				organizationDT.setAddUserId(edxLabInformationDT.getUserId());
	
				ParticipationDT participationDT = new ParticipationDT();
				participationDT.setActClassCd(EdxELRConstants.ELR_OBS);
				participationDT.setCd(EdxELRConstants.ELR_OP_CD);
				 participationDT.setAddUserId(EdxELRConstants.ELR_ADD_USER_ID);
				 participationDT.setActUid(edxLabInformationDT.getRootObserbationUid());
				participationDT.setTypeCd(EdxELRConstants.ELR_ORDERER_CD);
				participationDT =hl7ToNBSObjectConverter.defaultParticipationDT(participationDT,edxLabInformationDT);
				participationDT.setTypeDescTxt(EdxELRConstants.ELR_ORDERER_DESC);
				participationDT
						.setSubjectClassCd(EdxELRConstants.ELR_ORG);
				participationDT.setSubjectEntityUid(organizationDT
						.getOrganizationUid());
				if(labResultProxyVO.getTheParticipationDTCollection()==null)
					labResultProxyVO.setTheParticipationDTCollection(new ArrayList<Object>());
				labResultProxyVO.getTheParticipationDTCollection().add(participationDT);
	
				if(labResultProxyVO.getTheOrganizationVOCollection()==null)
					labResultProxyVO.setTheOrganizationVOCollection(new ArrayList<Object>());
				labResultProxyVO.getTheOrganizationVOCollection().add(organizationVO);
				
				Collection<Object> roleDTColl = new ArrayList<Object>();
				RoleDT roleDT = new RoleDT();
				roleDT.setCd(EdxELRConstants.ELR_OP_CD);
				roleDT.setCdDescTxt(EdxELRConstants.ELR_OP_DESC);
				roleDT.setScopingClassCd(EdxELRConstants.ELR_SENDING_HCFAC);
				roleDT.setRoleSeq(new Long(1));
				roleDT.setAddReasonCd("");
				roleDT.setAddTime(organizationVO.getTheOrganizationDT()
						.getAddTime());
				roleDT.setAddUserId(edxLabInformationDT.getUserId());
				roleDT.setItNew(true);
				roleDT.setItDirty(false);
				roleDT.setAddReasonCd(EdxELRConstants.ELR_ROLE_REASON);
				roleDT.setRecordStatusCd(EdxELRConstants.ELR_ACTIVE);
				roleDT.setLastChgTime(organizationVO.getTheOrganizationDT()
						.getAddTime());
				roleDT.setStatusCd(EdxELRConstants.ELR_ACTIVE_CD);
				roleDT.setSubjectEntityUid(organizationVO.getTheOrganizationDT()
						.getOrganizationUid());
				roleDTColl.add(roleDT);
				if(labResultProxyVO.getTheRoleDTCollection()==null)
					labResultProxyVO.setTheRoleDTCollection(new ArrayList<Object>());
				labResultProxyVO.getTheRoleDTCollection().add(roleDT);
	
				Collection<Object> addressCollection = new ArrayList<Object>();
				
				if (addressArray != null) {
					for (int i = 0; i < addressArray.length; ) {
						HL7XADType addressType = addressArray[i];
						EntityLocatorParticipationDT elpDT = hl7ToNBSObjectConverter
								.organizationAddressType(addressType,
										EdxELRConstants.ELR_OP_CD, organizationVO);
						addressCollection.add(elpDT);
						break;
					}
				}
				
				
				HL7XTNType[] phoneArray = hl7ORCType.getOrderingFacilityPhoneNumberArray();
				for (int i = 0; i < phoneArray.length; ) {
					HL7XTNType phone = phoneArray[i];
					if (phone != null) {
						EntityLocatorParticipationDT elpdt = hl7ToNBSObjectConverter.orgTelePhoneType(phone,
										EdxELRConstants.ELR_OP_CD, organizationVO);
						if(organizationVO.getTheEntityLocatorParticipationDTCollection()==null)
							organizationVO.setTheEntityLocatorParticipationDTCollection(new ArrayList<Object>());
						elpdt.setUseCd(EdxELRConstants.ELR_WORKPLACE_CD);
						organizationVO.getTheEntityLocatorParticipationDTCollection().add(elpdt);					
						break;
					}
				}
				Collection<Object> orgNameColl = new ArrayList<Object>();
				HL7XONType[] nameArray = hl7ORCType.getOrderingFacilityNameArray();
				if (nameArray != null) {
					for (int i = 0; i < nameArray.length;) {
						HL7XONType orgName = nameArray[i];
						OrganizationNameDT organizationNameDT = new OrganizationNameDT();
						organizationNameDT.setNmTxt(orgName
								.getHL7OrganizationName());
						organizationNameDT
								.setNmUseCd(EdxELRConstants.ELR_LEGAL_NAME);
						organizationNameDT.setOrganizationNameSeq(new Integer(i));
						organizationDT.setDisplayNm(organizationNameDT.getNmTxt());
						orgNameColl.add(organizationNameDT);
						break;
					}
				}
				organizationVO.setTheOrganizationNameDTCollection(orgNameColl);
				edxLabInformationDT.setMissingOrderingFacility(false);
			}else{
				edxLabInformationDT.setMissingOrderingFacility(true);
			}
			
		} catch (Exception e) {
			logger.fatal("Exception thrown by HL7ORCProcessorget.getOrderingFacility "
					+ e.getMessage(), e);

			throw new NEDSSAppException("Exception thrown at HL7ORCProcessorget.getOrderingFacility:"+ e);
			
		}
		if(organizationVO!=null)
			edxLabInformationDT.setMultipleOrderingFacility(false);

		return organizationVO;
	}

	public void getORCProcessing(HL7ORCType hl7ORCType,
			LabResultProxyVO labResultProxyVO,
			EdxLabInformationDT edxLabInformationDT) throws NEDSSAppException {
		try {
			getOrderingProvider(hl7ORCType, labResultProxyVO, edxLabInformationDT);
			this.getOrderingFacility(hl7ORCType, labResultProxyVO, edxLabInformationDT);
			if (hl7ORCType.getOrderEffectiveDateTime() != null) {
				Hl7ToNBSObjectConverter hl7ToNBSObjectConverter = new Hl7ToNBSObjectConverter();
				edxLabInformationDT.setOrderEffectiveDate(
						hl7ToNBSObjectConverter.processHL7TSType(hl7ORCType.getOrderEffectiveDateTime(),
								EdxELRConstants.DATE_VALIDATION_ORC_ORDER_EFFECTIVE_TIME_MSG));
			}
		} catch (NEDSSAppException e) {
			logger.fatal("Exception thrown at HL7ORCProcessorget.getORCProcessing:"+ e.getMessage() ,e);
			throw new NEDSSAppException("Exception thrown at HL7ORCProcessorget.getORCProcessing:"+ e);
			
		}
	}

}
