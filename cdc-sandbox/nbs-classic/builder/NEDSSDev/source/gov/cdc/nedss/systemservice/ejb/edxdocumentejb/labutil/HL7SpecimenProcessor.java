package gov.cdc.nedss.systemservice.ejb.edxdocumentejb.labutil;

import gov.cdc.nedss.association.dt.ParticipationDT;
import gov.cdc.nedss.association.dt.RoleDT;
import gov.cdc.nedss.entity.entityid.dt.EntityIdDT;
import gov.cdc.nedss.entity.material.dt.MaterialDT;
import gov.cdc.nedss.entity.material.vo.MaterialVO;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.act.observation.dt.ObservationDT;
import gov.cdc.nedss.phdc.HL7EIPType;
import gov.cdc.nedss.phdc.HL7OBRType;
import gov.cdc.nedss.phdc.HL7PatientResultSPMType;
import gov.cdc.nedss.phdc.HL7SPECIMENType;
import gov.cdc.nedss.phdc.HL7SPMType;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.LabResultProxyVO;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dt.EdxLabInformationDT;
import gov.cdc.nedss.util.LogUtils;

import java.util.ArrayList;
/**
 * Utility class for Material processing
 * @author Pradeep Kuamr Sharma
 *
 */
public class  HL7SpecimenProcessor{
	static final LogUtils logger = new LogUtils(HL7SpecimenProcessor.class.getName());


	public void process251Specimen(HL7PatientResultSPMType hL7PatientResultSPMType, LabResultProxyVO labResultProxyVO, ObservationDT observationDT, PersonVO collectorVO, EdxLabInformationDT edxLabInformationDT) throws NEDSSAppException{
		try {
			Hl7ToNBSObjectConverter hl7ToNBSObjectConverter = new Hl7ToNBSObjectConverter();
			HL7SPECIMENType[] hl7SPECIMENTypeArray =hL7PatientResultSPMType.getSPECIMENArray();
			if(hl7SPECIMENTypeArray!=null && hl7SPECIMENTypeArray.length>1)
				edxLabInformationDT.setMultipleSpecimen(true);
			if(hl7SPECIMENTypeArray!=null){
			for(int i=0; i<hl7SPECIMENTypeArray.length;){
				if(hl7SPECIMENTypeArray.length>1)
					edxLabInformationDT.setMultipleSpecimen(true);
					HL7SPECIMENType hl7SPECIMENType = hl7SPECIMENTypeArray[i];
					if(hl7SPECIMENType!=null && hl7SPECIMENType.getSPECIMEN()!=null){
						HL7SPMType  hl7SPMType  =hl7SPECIMENType.getSPECIMEN();
						MaterialVO materialVO = new MaterialVO();
						MaterialDT materialDT = new MaterialDT();
						materialVO.setTheMaterialDT(materialDT);
						materialDT.setMaterialUid(new Long(edxLabInformationDT.getNextUid()));
						materialDT.setRiskCd(edxLabInformationDT.getDangerCode());
						
						//edxLabInformationDT.setNextUid((edxLabInformationDT.getNextUid()));
			
						

						
						if(hl7SPMType.getSpecimenCollectionAmount()!=null && hl7SPMType.getSpecimenCollectionAmount().getHL7Quantity()!=null){
							materialDT.setQty(hl7SPMType.getSpecimenCollectionAmount().getHL7Quantity().getHL7Numeric()+"");
							if(hl7SPMType.getSpecimenCollectionAmount().getHL7Units()!=null)
								materialDT.setQtyUnitCd(hl7SPMType.getSpecimenCollectionAmount().getHL7Units().getHL7Identifier());
						}
						if(hl7SPMType.getSpecimenType()!=null){
							materialDT.setCd(hl7SPMType.getSpecimenType().getHL7Identifier());
							materialDT.setCdDescTxt(hl7SPMType.getSpecimenType().getHL7Text());
						}
						String [] specimenDec = hl7SPMType.getSpecimenDescriptionArray();
						if (specimenDec!=null && specimenDec.length>0)
							materialDT.setDescription(specimenDec[0]);
						if(hl7SPMType.getSpecimenSourceSite()!=null){
							observationDT.setTargetSiteCd(hl7SPMType.getSpecimenSourceSite().getHL7Identifier());
							observationDT.setTargetSiteDescTxt(hl7SPMType.getSpecimenSourceSite().getHL7Text());
						}
						//observationDT.setActivityFromTime(Hl7ToNBSObjectConverter.processHL7TSType(hl7SPMType.getSpecimenCollectionDateTime().getHL7RangeStartDateTime()));
						if(hl7SPMType.getSpecimenCollectionDateTime()!=null)
							observationDT.setEffectiveFromTime(hl7ToNBSObjectConverter.processHL7TSTypeWithMillis(hl7SPMType.getSpecimenCollectionDateTime().getHL7RangeStartDateTime(), EdxELRConstants.DATE_VALIDATION_SPM_SPECIMEN_COLLECTION_DATE_MSG));
						processMaterialVO(labResultProxyVO,collectorVO, materialVO, edxLabInformationDT);
						//use  Filler Specimen ID (SPM.2.2.1) is present for specimen ID - Defect #14343 Jira
						if (hl7SPMType.getSpecimenID() != null
								&& hl7SPMType.getSpecimenID().getHL7FillerAssignedIdentifier() != null
								&& hl7SPMType.getSpecimenID().getHL7FillerAssignedIdentifier()
										.getHL7EntityIdentifier() != null) {
							String specimenID = hl7SPMType.getSpecimenID().getHL7FillerAssignedIdentifier()
									.getHL7EntityIdentifier();
							materialVO.getEntityIdDT_s(0).setRootExtensionTxt(specimenID);
							if (hl7SPMType.getSpecimenID().getHL7FillerAssignedIdentifier().getHL7UniversalID() != null)
								materialVO.getEntityIdDT_s(0).setAssigningAuthorityCd(hl7SPMType.getSpecimenID()
										.getHL7FillerAssignedIdentifier().getHL7UniversalID());
							if (hl7SPMType.getSpecimenID().getHL7FillerAssignedIdentifier().getHL7NamespaceID() != null)
								materialVO.getEntityIdDT_s(0).setAssigningAuthorityDescTxt(hl7SPMType.getSpecimenID()
										.getHL7FillerAssignedIdentifier().getHL7NamespaceID());
							if (hl7SPMType.getSpecimenID().getHL7FillerAssignedIdentifier()
									.getHL7UniversalIDType() != null)
								materialVO.getEntityIdDT_s(0).setAssigningAuthorityIdType(hl7SPMType.getSpecimenID()
										.getHL7FillerAssignedIdentifier().getHL7UniversalIDType());
						}
						
						break;
					}
					break;
			}
		}
		} catch (NEDSSAppException e) {
			logger.fatal("HL7SpecimenProcessor.process251Specimen error thrown "+ e.getMessage(), e);
			throw new NEDSSAppException("HL7SpecimenProcessor.process251Specimen error thrown "+ e.getMessage() + e);
		}
	}
	
	
	public void processSpecimen(HL7OBRType hl7OBRType, LabResultProxyVO labResultProxyVO, ObservationDT observationDT, PersonVO collectorVO, EdxLabInformationDT edxLabInformationDT) throws NEDSSAppException{

		try {
			MaterialVO materialVO = new MaterialVO();
			MaterialDT materialDT = new MaterialDT();
			materialVO.setTheMaterialDT(materialDT);
			edxLabInformationDT.setMultipleSpecimen(false);
			materialDT.setMaterialUid(new Long(edxLabInformationDT.getNextUid()));
			//edxLabInformationDT.setNextUid((edxLabInformationDT.getNextUid()-1));
			if(hl7OBRType.getCollectionVolume()!=null && hl7OBRType.getCollectionVolume().getHL7Quantity()!=null ){
				if(hl7OBRType.getCollectionVolume().getHL7Quantity()!=null && hl7OBRType.getCollectionVolume().getHL7Quantity().getHL7Numeric()>0)
					materialDT.setQty(hl7OBRType.getCollectionVolume().getHL7Quantity().getHL7Numeric()+"");
				if(hl7OBRType.getCollectionVolume().getHL7Units()!=null && hl7OBRType.getCollectionVolume().getHL7Units().getHL7Identifier()!=null)
					materialDT.setQtyUnitCd(hl7OBRType.getCollectionVolume().getHL7Units().getHL7Identifier());

			}
			
			
				
			materialDT.setHandlingCd(hl7OBRType.getSpecimenActionCode());
			if(hl7OBRType.getSpecimenSource().getHL7SpecimenSourceNameOrCode()!=null){
					materialDT.setCd(hl7OBRType.getSpecimenSource().getHL7SpecimenSourceNameOrCode().getHL7Identifier());
					materialDT.setCdDescTxt(hl7OBRType.getSpecimenSource().getHL7SpecimenSourceNameOrCode().getHL7Text());
					materialDT.setDescription(hl7OBRType.getSpecimenSource().getHL7SpecimenCollectionMethod().getHL7String());
			}
			materialDT.setRiskCd(edxLabInformationDT.getDangerCode());
			processMaterialVO(labResultProxyVO, collectorVO, materialVO, edxLabInformationDT);
			
			
			if(hl7OBRType.getSpecimenSource().getHL7BodySite()!=null)
				observationDT.setTargetSiteCd(hl7OBRType.getSpecimenSource().getHL7BodySite().getHL7Identifier());
		} catch (NEDSSAppException e) {
			logger.fatal("HL7SpecimenProcessor.processSpecimen error thrown "+ e.getMessage(), e);
			throw new NEDSSAppException("HL7SpecimenProcessor.processSpecimen error thrown "+ e);
		}
	
	}
	
	private void processMaterialVO(LabResultProxyVO labResultProxyVO,PersonVO collectorVO,MaterialVO materialVO,  EdxLabInformationDT edxLabInformationDT) throws NEDSSAppException{
		try {
			Hl7ToNBSObjectConverter hl7ToNBSObjectConverter = new Hl7ToNBSObjectConverter();
			EntityIdDT matEntityIdDT = new EntityIdDT();
			matEntityIdDT.setAssigningAuthorityIdType(edxLabInformationDT.getUniversalIdType());
			matEntityIdDT.setEntityUid(new Long(edxLabInformationDT.getNextUid()));
			matEntityIdDT.setAddTime(edxLabInformationDT.getAddTime());
			matEntityIdDT.setRootExtensionTxt(edxLabInformationDT.getFillerNumber());
			matEntityIdDT.setTypeCd(EdxELRConstants.ELR_SPECIMEN_CD);
			matEntityIdDT.setTypeDescTxt(EdxELRConstants.ELR_SPECIMEN_DESC);
			matEntityIdDT.setEntityIdSeq(new Integer(1));
			matEntityIdDT.setAssigningAuthorityCd(edxLabInformationDT.getSendingFacilityClia());
			matEntityIdDT.setAssigningAuthorityDescTxt(edxLabInformationDT.getSendingFacilityName());
			matEntityIdDT.setRecordStatusCd(EdxELRConstants.ELR_ACTIVE);
			matEntityIdDT.setAsOfDate(edxLabInformationDT.getAddTime());
			matEntityIdDT.setItNew(true);
			matEntityIdDT.setItDirty(false);

			if(materialVO.getTheEntityIdDTCollection()==null)
				materialVO.setTheEntityIdDTCollection(new ArrayList<Object>());
			materialVO.getTheEntityIdDTCollection().add(matEntityIdDT);

			edxLabInformationDT.setRole(EdxELRConstants.ELR_PROVIDER_CD);



			RoleDT roleDT = new RoleDT();
			roleDT.setSubjectEntityUid(materialVO.getTheMaterialDT().getMaterialUid());
			roleDT.setCd(EdxELRConstants.ELR_NO_INFO_CD);
			roleDT.setCdDescTxt(EdxELRConstants.ELR_NO_INFO_DESC);
			roleDT.setSubjectClassCd(EdxELRConstants.ELR_MAT_CD);
			roleDT.setRoleSeq(new Long(1));
			roleDT.setScopingEntityUid(edxLabInformationDT.getPatientUid());
			roleDT.setScopingClassCd(EdxELRConstants.ELR_PATIENT);
			roleDT.setScopingRoleCd(EdxELRConstants.ELR_PATIENT);
			roleDT.setScopingRoleSeq(new Integer(1));
			roleDT.setRecordStatusCd(EdxELRConstants.ELR_ACTIVE);
			roleDT.setStatusCd(EdxELRConstants.ELR_ACTIVE_CD);
			roleDT.setItNew(true);
			roleDT.setItDirty(false);
			if(labResultProxyVO.getTheRoleDTCollection()==null)
				labResultProxyVO.setTheRoleDTCollection(new ArrayList<Object>());
			labResultProxyVO.getTheRoleDTCollection().add(roleDT);

			if(collectorVO!=null){
				RoleDT role2DT = new RoleDT();
				role2DT.setSubjectEntityUid(materialVO.getTheMaterialDT().getMaterialUid());
				role2DT.setItNew(true);
				role2DT.setItDirty(false);
				role2DT.setCd(EdxELRConstants.ELR_NO_INFO_CD);
				role2DT.setCdDescTxt(EdxELRConstants.ELR_NO_INFO_DESC);
				role2DT.setSubjectClassCd(EdxELRConstants.ELR_MAT_CD);
				role2DT.setRoleSeq(new Long(2));
				role2DT.setRecordStatusCd(EdxELRConstants.ELR_ACTIVE);
				role2DT.setStatusCd(EdxELRConstants.ELR_ACTIVE_CD);
				role2DT.setScopingEntityUid(collectorVO.getThePersonDT().getPersonUid());
				role2DT.setScopingClassCd(EdxELRConstants.ELR_PROVIDER_CD);
				role2DT.setScopingRoleCd(EdxELRConstants.ELR_SPECIMEN_PROCURER_CD);
				role2DT.setScopingRoleSeq(new Integer(1));
				if(labResultProxyVO.getTheRoleDTCollection()==null)
					labResultProxyVO.setTheRoleDTCollection(new ArrayList<Object>());
				labResultProxyVO.getTheRoleDTCollection().add(role2DT);
			}


			ParticipationDT participationDT = new ParticipationDT(); 

			participationDT.setSubjectEntityUid(materialVO.getTheMaterialDT().getMaterialUid());
			participationDT.setCd(EdxELRConstants.ELR_NO_INFO_CD);
			//participationDT.setRoleSeq(new Integer(1));
			participationDT.setTypeCd(EdxELRConstants.ELR_SPECIMEN_CD);
			participationDT.setTypeDescTxt(EdxELRConstants.ELR_SPECIMEN_DESC);
			participationDT.setActClassCd(EdxELRConstants.ELR_OBS);
			participationDT.setSubjectClassCd(EdxELRConstants.ELR_MAT_CD);
			participationDT.setActUid(edxLabInformationDT.getRootObserbationUid());
			participationDT =hl7ToNBSObjectConverter.defaultParticipationDT(participationDT,edxLabInformationDT);

			labResultProxyVO.getTheParticipationDTCollection().add(participationDT);
			if(labResultProxyVO.getTheMaterialVOCollection()==null)
				labResultProxyVO.setTheMaterialVOCollection(new ArrayList<Object>());
			labResultProxyVO.getTheMaterialVOCollection().add(materialVO);
		} catch (Exception e) {
			logger.fatal("HL7SpecimenProcessor.processSpecimen error thrown "+ e.getMessage(), e);
			throw new NEDSSAppException("HL7SpecimenProcessor.processSpecimen error thrown "+ e);
		}

	}
}
