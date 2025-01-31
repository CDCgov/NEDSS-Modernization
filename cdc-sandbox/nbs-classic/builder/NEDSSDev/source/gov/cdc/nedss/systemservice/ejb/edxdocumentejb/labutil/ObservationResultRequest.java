package gov.cdc.nedss.systemservice.ejb.edxdocumentejb.labutil;
import gov.cdc.nedss.act.actid.dt.ActIdDT;
import gov.cdc.nedss.act.observation.dt.ObsValueCodedDT;
import gov.cdc.nedss.act.observation.dt.ObsValueNumericDT;
import gov.cdc.nedss.act.observation.dt.ObsValueTxtDT;
import gov.cdc.nedss.act.observation.dt.ObservationDT;
import gov.cdc.nedss.act.observation.dt.ObservationInterpDT;
import gov.cdc.nedss.act.observation.vo.ObservationVO;
import gov.cdc.nedss.association.dt.ActRelationshipDT;
import gov.cdc.nedss.association.dt.ParticipationDT;
import gov.cdc.nedss.association.dt.RoleDT;
import gov.cdc.nedss.entity.entityid.dt.EntityIdDT;
import gov.cdc.nedss.entity.organization.dt.OrganizationDT;
import gov.cdc.nedss.entity.organization.dt.OrganizationNameDT;
import gov.cdc.nedss.entity.organization.vo.OrganizationVO;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.locator.dt.EntityLocatorParticipationDT;
import gov.cdc.nedss.phdc.HL7CEType;
import gov.cdc.nedss.phdc.HL7CWEType;
import gov.cdc.nedss.phdc.HL7EIType;
import gov.cdc.nedss.phdc.HL7NTEType;
import gov.cdc.nedss.phdc.HL7OBSERVATIONType;
import gov.cdc.nedss.phdc.HL7OBXType;
import gov.cdc.nedss.phdc.HL7XADType;
import gov.cdc.nedss.phdc.HL7XONType;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.LabResultProxyVO;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dao.ELRXRefDAOImpl;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dt.EdxLabIdentiferDT;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dt.EdxLabInformationDT;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.StringTokenizer;



/**
 * @author Pradeep Kumar Sharma
 *Utility class to parse observation information
 */
public class ObservationResultRequest {
	static final LogUtils logger = new LogUtils(ObservationResultRequest.class.getName());

	public LabResultProxyVO getObservationResultRequest(HL7OBSERVATIONType[] observationRequestArray,  LabResultProxyVO labResultProxyVO, EdxLabInformationDT edxLabInformationDT) throws NEDSSAppException{
		
		try {
			for(int j=0; j<observationRequestArray.length; j++){
				try {
					HL7OBSERVATIONType observationRequest= observationRequestArray[j];
					ObservationVO observationVO =getObservationResult(observationRequest.getObservationResult(), labResultProxyVO, edxLabInformationDT);
					getObsReqNotes(observationRequest.getNotesAndCommentsArray(), observationVO);
					//edxLabInformationDT.setParentObservationUid(observationVO.getTheObservationDT().getObservationUid());
					labResultProxyVO.getTheObservationVOCollection().add(observationVO);
				} catch (Exception e) {
					logger.error("ObservationResultRequest.getObservationResultRequest Exception thrown while processing observationRequestArray. Please check!!!"+e.getMessage(), e);
					throw new NEDSSAppException("Exception thrown at ObservationResultRequest.getObservationResultRequest while oricessing observationRequestArray:"+ e.getMessage());
				}
			}
		} catch (NEDSSAppException e) {
			logger.error("ObservationResultRequest.getObservationResultRequest Exception thrown while parsing XML document. Please check!!!"+e.getMessage(), e);
			throw new NEDSSAppException("Exception thrown at ObservationResultRequest.getObservationResultRequest:"+ e.getMessage());
		}
		return labResultProxyVO;
	}
	
	private ObservationVO getObsReqNotes(HL7NTEType[] noteArray, ObservationVO observationVO) throws NEDSSAppException{
		try {
			for(int i=0; i<noteArray.length; i++){
				HL7NTEType notes= noteArray[i];
				if(notes.getHL7CommentArray()!=null && notes.getHL7CommentArray().length >0){
					for(int j=0; j<notes.getHL7CommentArray().length; j++){
						String note =  notes.getHL7CommentArray()[j];
						ObsValueTxtDT obsValueTxtDT = new ObsValueTxtDT();
						obsValueTxtDT.setItNew(true);
						obsValueTxtDT.setItDirty(false);
						obsValueTxtDT.setObservationUid(observationVO.getTheObservationDT().getObservationUid());
						obsValueTxtDT.setTxtTypeCd(EdxELRConstants.ELR_OBX_COMMENT_TYPE);
						
						obsValueTxtDT.setValueTxt(note);
						if(observationVO.getTheObsValueTxtDTCollection()==null)
						observationVO.setTheObsValueTxtDTCollection(new ArrayList<Object>());
						int seq = observationVO.getTheObsValueTxtDTCollection().size();
						obsValueTxtDT.setObsValueTxtSeq(new Integer(++seq));
						observationVO.getTheObsValueTxtDTCollection().add(obsValueTxtDT);
					}
				}else{
					ObsValueTxtDT obsValueTxtDT = new ObsValueTxtDT();
					obsValueTxtDT.setItNew(true);
					obsValueTxtDT.setItDirty(false);
					obsValueTxtDT.setValueTxt("\r");
					obsValueTxtDT.setObservationUid(observationVO.getTheObservationDT().getObservationUid());
					obsValueTxtDT.setTxtTypeCd(EdxELRConstants.ELR_OBX_COMMENT_TYPE);
					
					if(observationVO.getTheObsValueTxtDTCollection()==null)
					observationVO.setTheObsValueTxtDTCollection(new ArrayList<Object>());
					int seq = observationVO.getTheObsValueTxtDTCollection().size();
					obsValueTxtDT.setObsValueTxtSeq(new Integer(++seq));
					observationVO.getTheObsValueTxtDTCollection().add(obsValueTxtDT);
			
				}
				
			}
		} catch (Exception e) {
			logger.error("ObservationResultRequest.getObsReqNotes Exception thrown while parsing XML document. Please check!!!"+e.getMessage(), e);
			throw new NEDSSAppException("Exception thrown at ObservationResultRequest.getObsReqNotes:"+ e.getMessage());
	
		}
		return observationVO;
		
		
	}
	private ObservationVO getObservationResult(HL7OBXType hl7OBXType, LabResultProxyVO labResultProxyVO, EdxLabInformationDT edxLabInformationDT) throws NEDSSAppException {
		ObservationVO observationVO;
		try {
			Hl7ToNBSObjectConverter hl7ToNBSObjectConverter = new Hl7ToNBSObjectConverter();
			 CachedDropDownValues cdv = new CachedDropDownValues();
			observationVO = new ObservationVO(); 
			
			ObservationDT observationDT= new ObservationDT(); 
			// observationDT.setObsDomainCd(EdxELRConstants.CTRL_CD_DISPLAY_FORM);
			 observationDT.setCtrlCdDisplayForm(EdxELRConstants.CTRL_CD_DISPLAY_FORM);
			 observationDT.setElectronicInd(EdxELRConstants.ELR_ELECTRONIC_IND);
			 observationDT.setObservationUid(new Long(edxLabInformationDT.getNextUid()));
			 observationDT.setStatusCd(EdxELRConstants.ELR_ACTIVE_CD);
			 //edxLabInformationDT.setNextUid((edxLabInformationDT.getNextUid()-1));
			 if(edxLabInformationDT.isParentObsInd()){
				 observationDT.setObsDomainCdSt1(EdxELRConstants.ELR_REF_RESULT_CD);
			 }else{
				 observationDT.setObsDomainCdSt1(EdxELRConstants.ELR_RESULT_CD);
					
			 }
				 
			 
			 observationDT.setItNew(true);
			 observationDT.setItDirty(false);
			 observationVO.setItNew(true);
			 observationVO.setItDirty(false); 
			 observationVO.setTheObservationDT(observationDT);
			 HL7CommonLabUtil hL7CommonLabUtil = new HL7CommonLabUtil();
			 EdxLabIdentiferDT edxLabIdentiferDT = new EdxLabIdentiferDT();
			 
			 	if(!edxLabInformationDT.isParentObsInd() && (hl7OBXType.getObservationIdentifier()== null 
			 			|| (hl7OBXType.getObservationIdentifier().getHL7Identifier()==null && hl7OBXType.getObservationIdentifier().getHL7AlternateIdentifier()==null))){
			 		edxLabInformationDT.setResultedTestNameMissing(true);
			 		edxLabInformationDT.setErrorText(EdxELRConstants.ELR_MASTER_LOG_ID_19);
			 		String xmlElementName = hL7CommonLabUtil.getXMLElementName(hl7OBXType)+".ObservationIdentifier";
			 		throw new NEDSSAppException(EdxELRConstants.NO_RESULT_NAME+" XMLElementName: "+xmlElementName);
			}
			 
			 if(hl7OBXType.getObservationIdentifier().getHL7Identifier()!=null){
				 	edxLabIdentiferDT.setIdentifer(hl7OBXType.getObservationIdentifier().getHL7Identifier());
			 }else if(hl7OBXType.getObservationIdentifier().getHL7AlternateIdentifier()!=null)
				 	edxLabIdentiferDT.setIdentifer(hl7OBXType.getObservationIdentifier().getHL7AlternateIdentifier());
				edxLabIdentiferDT.setSubMapID(hl7OBXType.getObservationSubID());
				edxLabIdentiferDT.setObservationValues(hl7OBXType.getObservationValueArray()) ;
				edxLabIdentiferDT.setObservationUid(observationDT.getObservationUid());
				edxLabInformationDT.getEdxSusLabDTMap().put(edxLabIdentiferDT.getObservationUid(),edxLabIdentiferDT);
				if(edxLabInformationDT.getEdxLabIdentiferDTColl()==null)
					edxLabInformationDT.setEdxLabIdentiferDTColl(new ArrayList<Object>());
				
				
				
			 edxLabInformationDT.getEdxLabIdentiferDTColl().add(edxLabIdentiferDT);
			 //HL7SIType  siType =hl7OBXType.getSetIDOBX();
			
			 //String id = siType.getHL7SequenceID();
			Collection<Object> actIdDTColl =  new ArrayList<Object>();
			 ActIdDT actIdDT= new ActIdDT();
			 actIdDT.setActIdSeq(new Integer(1));
			 actIdDT.setActUid(observationDT.getObservationUid());
			 actIdDT.setRootExtensionTxt(edxLabInformationDT.getMessageControlID()); 
			 actIdDT.setAssigningAuthorityCd(edxLabInformationDT.getSendingFacilityClia());
			 actIdDT.setAssigningAuthorityDescTxt(edxLabInformationDT.getSendingFacilityName());
			 actIdDT.setTypeCd(EdxELRConstants.ELR_MESSAGE_CTRL_CD);
			 actIdDT.setTypeDescTxt(EdxELRConstants.ELR_MESSAGE_CTRL_DESC);
			 actIdDT.setRecordStatusCd(EdxELRConstants.ELR_ACTIVE);
			 actIdDTColl.add(actIdDT);

			 ActIdDT act2IdDT = new ActIdDT();
			 act2IdDT.setActUid(observationDT.getObservationUid());
			 act2IdDT.setActIdSeq(new Integer(2));
			 act2IdDT.setRootExtensionTxt(edxLabInformationDT.getFillerNumber());
			 act2IdDT.setAssigningAuthorityCd(edxLabInformationDT.getSendingFacilityClia());
			 act2IdDT.setAssigningAuthorityDescTxt(edxLabInformationDT.getSendingFacilityName());
			 act2IdDT.setTypeCd(EdxELRConstants.ELR_FILLER_NUMBER_CD);
			 act2IdDT.setTypeDescTxt(EdxELRConstants.ELR_FILLER_NUMBER_DESC);
			 act2IdDT.setRecordStatusCd(EdxELRConstants.ELR_ACTIVE);
			 actIdDTColl.add(act2IdDT);
			/*
			 * ND-18349 HHS ELR Updates for COVID: Updates Required to Process OBX 18
			 */
			HL7EIType[] equipmentIdType = hl7OBXType.getEquipmentInstanceIdentifierArray();
			int seq = 3;
			for (int i = 0; i < equipmentIdType.length; i++) {
				HL7EIType equipmentId = equipmentIdType[i];
				if (equipmentIdType != null) {
					ActIdDT act3IdDT = new ActIdDT();
					act3IdDT.setActUid(observationDT.getObservationUid());
					act3IdDT.setActIdSeq(new Integer(seq));
					act3IdDT.setRootExtensionTxt(equipmentId.getHL7EntityIdentifier());
					act3IdDT.setAssigningAuthorityCd(equipmentId.getHL7UniversalID());
					act3IdDT.setAssigningAuthorityDescTxt(equipmentId.getHL7UniversalIDType());
					act3IdDT.setTypeCd(EdxELRConstants.ELR_EQUIPMENT_INSTANCE_CD);
					act3IdDT.setTypeDescTxt(EdxELRConstants.ELR_EQUIPMENT_INSTANCE_DESC);
					act3IdDT.setRecordStatusCd(EdxELRConstants.ELR_ACTIVE);
					actIdDTColl.add(act3IdDT);
					seq = seq + 1;
				}
			}
			
			/*
			if (equipmentIdType != null && equipmentIdType.length > 0) {
				HL7EIType equipmentId = equipmentIdType[0];
				if (equipmentIdType != null) {
					ActIdDT act3IdDT = new ActIdDT();
					act3IdDT.setActUid(observationDT.getObservationUid());
					act3IdDT.setActIdSeq(new Integer(3));
					act3IdDT.setRootExtensionTxt(equipmentId.getHL7EntityIdentifier());
					act3IdDT.setAssigningAuthorityCd(equipmentId.getHL7UniversalID());
					act3IdDT.setAssigningAuthorityDescTxt(equipmentId.getHL7UniversalIDType());
					act3IdDT.setTypeCd(EdxELRConstants.ELR_EQUIPMENT_INSTANCE_CD);
					act3IdDT.setTypeDescTxt(EdxELRConstants.ELR_EQUIPMENT_INSTANCE_DESC);
					act3IdDT.setRecordStatusCd(EdxELRConstants.ELR_ACTIVE);
					actIdDTColl.add(act3IdDT);
				}
			}*/
			 
			 observationVO.setTheActIdDTCollection(actIdDTColl);
			 
			 ActRelationshipDT actRelationshipDT = new ActRelationshipDT();
			 actRelationshipDT.setItNew(true);
			 actRelationshipDT.setItDirty(false);
			 actRelationshipDT.setAddTime(edxLabInformationDT.getAddTime());
			 actRelationshipDT.setLastChgTime(edxLabInformationDT.getAddTime());
			 actRelationshipDT.setRecordStatusTime(edxLabInformationDT.getAddTime());
			 actRelationshipDT.setTypeCd(EdxELRConstants.ELR_COMP_CD);
			 actRelationshipDT.setTypeDescTxt(EdxELRConstants.ELR_COMP_DESC);
			 actRelationshipDT.setSourceActUid(observationVO.getTheObservationDT().getObservationUid());
			 if(edxLabInformationDT.isParentObsInd()){
				actRelationshipDT.setTargetActUid(edxLabInformationDT.getParentObservationUid());
			 }
			 else
				 actRelationshipDT.setTargetActUid(edxLabInformationDT.getRootObserbationUid());
			 actRelationshipDT.setTargetClassCd(EdxELRConstants.ELR_OBS);
			 actRelationshipDT.setSourceClassCd(EdxELRConstants.ELR_OBS);
			 actRelationshipDT.setRecordStatusCd(EdxELRConstants.ELR_ACTIVE);
			 actRelationshipDT.setStatusCd(EdxELRConstants.ELR_ACTIVE_CD);
			 actRelationshipDT.setSequenceNbr(new Integer(1));
			 actRelationshipDT.setItNew(true);
			 actRelationshipDT.setItDirty(false);
			 if(labResultProxyVO.getTheActRelationshipDTCollection()==null)
				 labResultProxyVO.setTheActRelationshipDTCollection(new ArrayList<Object>());
			 labResultProxyVO.getTheActRelationshipDTCollection().add(actRelationshipDT);
			
			 
			 
			 HL7CWEType obsIdentifier=hl7OBXType.getObservationIdentifier();
			 if(obsIdentifier!=null){     
				 if(obsIdentifier.getHL7Identifier()!=null)
				 observationDT.setCd(obsIdentifier.getHL7Identifier());
				 if(obsIdentifier.getHL7Text()!=null)
					 observationDT.setCdDescTxt(obsIdentifier.getHL7Text());
			  
				 if(observationDT.getCd()==null && obsIdentifier.getHL7AlternateIdentifier()!=null)
					 observationDT.setCd(obsIdentifier.getHL7AlternateIdentifier());
				 else if(observationDT.getCd()!=null && obsIdentifier.getHL7AlternateIdentifier()!=null)
					 observationDT.setAltCd(obsIdentifier.getHL7AlternateIdentifier());
				 if(obsIdentifier.getHL7AlternateText()!=null && observationDT.getCdDescTxt()==null)
				 observationDT.setCdDescTxt(obsIdentifier.getHL7AlternateText());
				 else if(obsIdentifier.getHL7AlternateText()!=null && observationDT.getCdDescTxt()!=null)
				 observationDT.setAltCdDescTxt(obsIdentifier.getHL7AlternateText());
				

				if(observationDT.getCd()!=null || observationDT.getCdDescTxt()!=null){
					 observationDT.setCdSystemCd(obsIdentifier.getHL7NameofCodingSystem());
					 observationDT.setCdSystemDescTxt(obsIdentifier.getHL7NameofCodingSystem());
				}
				if(observationDT.getAltCd()!=null || observationDT.getAltCdDescTxt()!=null){
					 observationDT.setAltCdSystemCd(obsIdentifier.getHL7NameofAlternateCodingSystem());
					 observationDT.setAltCdSystemDescTxt(obsIdentifier.getHL7NameofAlternateCodingSystem());
				}else if(observationDT.getCdSystemCd()==null){
					 observationDT.setCdSystemCd(obsIdentifier.getHL7NameofAlternateCodingSystem());
					 observationDT.setCdSystemDescTxt(obsIdentifier.getHL7NameofAlternateCodingSystem());
				}
				if (observationDT.getCdSystemCd() != null
						&& observationDT.getCdSystemCd().equals(EdxELRConstants.ELR_LOINC_CD)) {
					observationDT.setCdSystemCd(EdxELRConstants.ELR_LOINC_CD);
					observationDT.setCdSystemDescTxt(EdxELRConstants.ELR_LOINC_DESC);
					Map<String, String> aOELOINCs = CachedDropDowns.getAOELOINCCodes();
					if (aOELOINCs != null && aOELOINCs.containsKey(observationDT.getCd())) {
						observationDT.setMethodCd(NEDSSConstants.AOE_OBS);
					}

				}else if(observationDT.getCdSystemCd()!=null && observationDT.getCdSystemCd().equals(EdxELRConstants.ELR_SNOMED_CD)){
					observationDT.setCdSystemCd(EdxELRConstants.ELR_SNOMED_CD);
				 	observationDT.setCdSystemDescTxt(EdxELRConstants.ELR_SNOMED_DESC);
				 }else if(observationDT.getCdSystemCd()!=null && observationDT.getCdSystemCd().equals(EdxELRConstants.ELR_LOCAL_CD)){
					 observationDT.setCdSystemCd(EdxELRConstants.ELR_LOCAL_CD);
					 	observationDT.setCdSystemDescTxt(EdxELRConstants.ELR_LOCAL_DESC);
				 }
				
				 if(observationDT.getAltCd()!=null && observationDT.getAltCdSystemCd()!=null && observationDT.getAltCdSystemCd().equals(EdxELRConstants.ELR_SNOMED_CD)){
						observationDT.setAltCdSystemCd(EdxELRConstants.ELR_SNOMED_CD);
					 	observationDT.setAltCdSystemDescTxt(EdxELRConstants.ELR_SNOMED_DESC);
				 }else if(observationDT.getAltCd()!=null){
						observationDT.setAltCdSystemCd(EdxELRConstants.ELR_LOCAL_CD);
						observationDT.setAltCdSystemDescTxt(EdxELRConstants.ELR_LOCAL_DESC);
				 }
				 
				  if(edxLabInformationDT.isParentObsInd()){
						 if(observationVO.getTheObservationDT().getCd()==null  || 
							 (observationVO.getTheObservationDT().getCd()!=null && 
							 observationVO.getTheObservationDT().getCd().trim().equals(""))) {
							 edxLabInformationDT.setDrugNameMissing(true);
							 edxLabInformationDT.setErrorText(EdxELRConstants.ELR_MASTER_LOG_ID_13);
							 throw new NEDSSAppException(EdxELRConstants.NO_DRUG_NAME);
						 }
				  }
			 }
			 else{
				 logger.error("ObservationResultRequest.getObservationResult The Resulted Test ObservationCd  can't be set to null. Please check." + observationDT.getCd());
				 throw new NEDSSAppException("ObservationResultRequest.getObservationResult The Resulted Test ObservationCd  can't be set to null. Please check." + observationDT.getCd());
			 }
			
			 
			 String[]  obsValueArray =hl7OBXType.getObservationValueArray();
			 //Collection<Object> observationValue = new ArrayList<Object>();
			 String elementName = "ObservationValue";
			 for(int i=0; i<obsValueArray.length;i++){
			String text = obsValueArray[i];
				formatValue( text,hl7OBXType, observationVO,edxLabInformationDT, elementName);
				if (!(hl7OBXType.getValueType().equals(EdxELRConstants.ELR_STRING_CD)
						|| hl7OBXType.getValueType().equals(EdxELRConstants.ELR_TEXT_CD)
						|| hl7OBXType.getValueType().equals(EdxELRConstants.ELR_TEXT_DT)
						|| hl7OBXType.getValueType().equals(EdxELRConstants.ELR_TEXT_TS))) {
					break;
				}
			} 
			 
			if(hl7OBXType.getReferencesRange()!=null){
				String range =hl7OBXType.getReferencesRange();
				ObsValueNumericDT obsValueNumericDT = null;
				if(observationVO.getTheObsValueNumericDTCollection()!=null){
					 obsValueNumericDT = observationVO.getObsValueNumericDT_s(0);
				}else{
				 obsValueNumericDT = new ObsValueNumericDT();
				 obsValueNumericDT.setItNew(true);
				 obsValueNumericDT.setItDirty(false);
				 obsValueNumericDT.setObsValueNumericSeq(new Integer(1));
				 obsValueNumericDT.setObservationUid(observationVO.getTheObservationDT().getObservationUid());
				}
				if(range.contains("^")){
					int i=0;
					if(range.indexOf("^")==0){
						i=1;
					}
					StringTokenizer st = new StringTokenizer(range, "^");
					while (st.hasMoreTokens()) {
						i++;
						String token = st.nextToken();
						if(i==1)
							obsValueNumericDT.setLowRange(token);
						else if(i==2)
							obsValueNumericDT.setHighRange(token);
					}
				} 
				else{
					obsValueNumericDT.setLowRange(range);
					if(observationVO.getTheObsValueNumericDTCollection()==null){
						observationVO.setTheObsValueNumericDTCollection(new ArrayList<Object>());
						observationVO.getTheObsValueNumericDTCollection().add(obsValueNumericDT);
					}
				}
			}
			if(hl7OBXType.getAbnormalFlagsArray()!=null && hl7OBXType.getAbnormalFlagsArray().length>0){
				ObservationInterpDT observationIntrepDT = new ObservationInterpDT();
				observationIntrepDT.setObservationUid(observationDT.getObservationUid()); 
				observationIntrepDT.setInterpretationCd(hl7OBXType.getAbnormalFlagsArray()[0].getHL7Identifier());
				 String str=cdv.getDescForCode("OBS_INTRP",observationIntrepDT.getInterpretationCd());
				 if(str==null || str.trim().length()==0)
					 observationIntrepDT.setInterpretationDescTxt(hl7OBXType.getAbnormalFlagsArray()[0].getHL7Text());
				else
				observationIntrepDT.setInterpretationDescTxt(str);
				observationIntrepDT.setObservationUid(observationVO.getTheObservationDT().getObservationUid());
				observationVO.setTheObservationInterpDTCollection(new ArrayList<Object>());
				observationVO.getTheObservationInterpDTCollection().add(observationIntrepDT);
				
			}
			if(hl7OBXType.getObservationResultStatus()!=null){
				String toCode = CachedDropDowns.findToCode("ELR_LCA_STATUS", hl7OBXType.getObservationResultStatus(), "ACT_OBJ_ST");
				if (toCode != null && !toCode.equals("") && !toCode.equals(" ")){
					observationDT.setStatusCd(toCode.trim());
					
				}else{
					observationDT.setStatusCd(hl7OBXType.getObservationResultStatus());
					//edxLabInformationDT.setObsMethodTranslated(false);
				}	
			}
			// It was decided to use only OBX19 for this field instead of OBX14(as in 2.3.1) - ER 1085 in Rel4.4
			if(hl7OBXType.getDateTimeOftheAnalysis()!=null){
				//String sequesce = hl7OBXType.getSetIDOBX()act2IdDT ;
				observationDT.setActivityToTime(hl7ToNBSObjectConverter.processHL7TSType(hl7OBXType.getDateTimeOftheAnalysis(),EdxELRConstants.DATE_VALIDATION_OBX_LAB_PERFORMED_DATE_MSG));
			}
			
			/*ND-18369 HHS ELR Updates for COVID: Update ELR Import to Not Require OBX-19 (Date/time of the Analysis) - Updated 09/03/2020 to make the fields optional*/
			/*else{
				edxLabInformationDT.setErrorText(EdxELRConstants.ELR_MASTER_LOG_ID_13);
				edxLabInformationDT.setActivityToTimeMissing(true);
				throw new NEDSSAppException(EdxELRConstants.ODSACTIVTOTIME_FAIL);
			}*/
			observationDT.setRptToStateTime(edxLabInformationDT.getLastChgTime());
			//2.3.1 to 2.5.1 translation copies this filed from OBX-15(CWE data type) to OBX-23(XON data type) which is required, so always reading it from OBX-23.
			HL7XONType hl7XONTypeName = hl7OBXType.getPerformingOrganizationName();
			if(hl7XONTypeName!=null){
				OrganizationVO producerOrg =getPerformingFacility(hl7OBXType,observationVO.getTheObservationDT().getObservationUid(),labResultProxyVO, edxLabInformationDT);
				//edxLabInformationDT.setNextUid((edxLabInformationDT.getNextUid()-1));
				labResultProxyVO.getTheOrganizationVOCollection().add(producerOrg);
			}
			HL7CEType[] methodArray = hl7OBXType.getObservationMethodArray();
			String methodCd = null;
			String methodDescTxt = null;
			final String delimiter = "**";
			for (int i = 0; i < methodArray.length; i++) {
				HL7CEType method = methodArray[i];
				if (method.getHL7Identifier() != null) {
					if (methodCd == null)
						methodCd = method.getHL7Identifier() + delimiter;
					else
						methodCd = methodCd + method.getHL7Identifier() + delimiter;

					String str = cdv.getDescForCode("OBS_METH", method.getHL7Identifier());
					if (str == null || str.trim().equals("")) {

						logger.warn(
								"ObservationResultRequest.getObservationResult warning: Method code could not be teranslated. Please check!!!");
						edxLabInformationDT.setObsMethodTranslated(false);
					}
					if (method.getHL7Text() != null) {
						if (methodDescTxt == null)
							methodDescTxt = method.getHL7Text() + delimiter;
						else
							methodDescTxt = methodDescTxt + method.getHL7Text() + delimiter;
					}
				}
			}

			if (methodCd != null && methodCd.lastIndexOf(delimiter) > 0)
				methodCd = methodCd.substring(0, methodCd.lastIndexOf(delimiter));
			if (methodDescTxt != null && methodDescTxt.lastIndexOf(delimiter) > 0)
				methodDescTxt = methodDescTxt.substring(0, methodDescTxt.lastIndexOf(delimiter));
			observationVO.getTheObservationDT().setMethodCd(methodCd);
			observationVO.getTheObservationDT().setMethodDescTxt(methodDescTxt);
		} catch (Exception e) {
			logger.error("ObservationResultRequest.getObservationResult Exception thrown while parsing XML document. Please check!!!"+e.getMessage(), e);
			throw new NEDSSAppException("Exception thrown at ObservationResultRequest.getObservationResult:"+ e.getMessage());
	
		}
			
		return observationVO;
	}
		
	private OrganizationVO getPerformingFacility(HL7OBXType hl7OBXType, long observationUid, LabResultProxyVO labResultProxyVO,EdxLabInformationDT edxLabInformationDT) throws NEDSSAppException{
		
		HL7XONType hl7XONTypeName = hl7OBXType.getPerformingOrganizationName(); 
		
		OrganizationVO organizationVO;
		try {
			organizationVO = new OrganizationVO();
			OrganizationDT organizationDT= new OrganizationDT();
			organizationVO.setItNew(true);
			organizationVO.setItDirty(false);

			
			organizationDT.setCd(EdxELRConstants.ELR_SENDING_LAB_CD);
			organizationDT.setCdDescTxt(EdxELRConstants.ELR_LABORATORY_DESC);
			organizationDT.setStandardIndustryClassCd(EdxELRConstants.ELR_standard_industry_class_cd);
			organizationDT.setStandardIndustryDescTxt(EdxELRConstants.ELR_standard_industry_desc_txt);
			organizationDT.setElectronicInd(EdxELRConstants.ELR_ELECTRONIC_IND);
			organizationDT.setOrganizationUid(new Long(edxLabInformationDT.getNextUid()));
			organizationDT.setAddUserId(edxLabInformationDT.getUserId());
			organizationDT.setItNew(true);
			organizationDT.setItDirty(false);
			organizationVO.setTheOrganizationDT(organizationDT);
			
			
			EntityIdDT entityIdDT = new EntityIdDT(); 
			entityIdDT.setEntityUid(organizationDT.getOrganizationUid());
			entityIdDT.setRootExtensionTxt(hl7XONTypeName.getHL7OrganizationIdentifier());
			entityIdDT.setTypeCd(EdxELRConstants.ELR_FACILITY_CD);
			entityIdDT.setTypeDescTxt(EdxELRConstants.ELR_FACILITY_DESC);
			entityIdDT.setRecordStatusCd(EdxELRConstants.ELR_ACTIVE);
			entityIdDT.setStatusCd(EdxELRConstants.ELR_ACTIVE_CD);
			entityIdDT.setAsOfDate(edxLabInformationDT.getAddTime());
			
			entityIdDT.setEntityIdSeq(new Integer(1));
			if(hl7XONTypeName.getHL7AssigningAuthority()!=null){
				entityIdDT.setAssigningAuthorityCd(hl7XONTypeName.getHL7AssigningAuthority().getHL7UniversalID());
				entityIdDT.setAssigningAuthorityIdType(hl7XONTypeName.getHL7AssigningAuthority().getHL7UniversalIDType());
			}
			if(hl7XONTypeName.getHL7AssigningAuthority()!=null && hl7XONTypeName.getHL7AssigningAuthority().getHL7NamespaceID()!=null && hl7XONTypeName.getHL7AssigningAuthority().getHL7NamespaceID().equals(EdxELRConstants.ELR_CLIA_CD))
				entityIdDT.setAssigningAuthorityDescTxt(EdxELRConstants.ELR_CLIA_DESC);
			
			if(organizationVO.getTheEntityIdDTCollection()==null)
				organizationVO.setTheEntityIdDTCollection(new ArrayList<Object>());
			organizationVO.getTheEntityIdDTCollection().add(entityIdDT);
			
			ParticipationDT participationDT = new ParticipationDT();
			participationDT.setActClassCd(EdxELRConstants.ELR_OBS);
			participationDT.setCd(EdxELRConstants.ELR_REPORTING_ENTITY_CD);
			participationDT.setTypeCd(EdxELRConstants.ELR_LAB_PERFORMER_CD);
			participationDT.setAddUserId(EdxELRConstants.ELR_ADD_USER_ID);

			//participationDT.setRoleSeq(new Integer(1));
			participationDT.setItNew(true);
			participationDT.setItDirty(false);
			participationDT.setTypeDescTxt(EdxELRConstants.ELR_LAB_PERFORMER_DESC);
			//participationDT.setSubjectClassCd(EdxELRConstants.ELR_SENDING_HCFAC);
			participationDT.setSubjectClassCd(EdxELRConstants.ELR_ORG);
			participationDT.setSubjectEntityUid(organizationDT.getOrganizationUid());
			participationDT.setStatusCd(EdxELRConstants.ELR_ACTIVE_CD);
			participationDT.setRecordStatusCd(EdxELRConstants.ELR_ACTIVE);
			participationDT.setAddReasonCd(EdxELRConstants.ELR_ROLE_REASON);
			participationDT.setActUid(observationUid);
			
			labResultProxyVO.getTheParticipationDTCollection().add(participationDT);
			
			RoleDT roleDT = new RoleDT();
			roleDT.setCd(EdxELRConstants.ELR_REPORTING_ENTITY_CD);
			roleDT.setCdDescTxt(EdxELRConstants.ELR_REPORTING_ENTITY_DESC);
			roleDT.setScopingClassCd(EdxELRConstants.ELR_PATIENT_CD);
			roleDT.setScopingRoleCd(EdxELRConstants.ELR_PATIENT_CD);
			roleDT.setRoleSeq(new Long(1));
			roleDT.setItNew(true);
			roleDT.setItDirty(false);
			roleDT.setAddReasonCd("");
			roleDT.setAddTime(organizationVO.getTheOrganizationDT().getAddTime());
			roleDT.setAddUserId(organizationVO.getTheOrganizationDT().getAddUserId());
			//roleDT.setEffectiveDurationAmt(aEffectiveDurationAmt);
			//roleDT.setEffectiveDurationUnitCd(aEffectiveDurationUnitCd);
			//roleDT.setEffectiveFromTime(aEffectiveFromTime);
			//roleDT.setEffectiveToTime(aEffectiveToTime);
			roleDT.setItNew(true);
			roleDT.setItDirty(false);
			roleDT.setAddReasonCd(EdxELRConstants.ELR_ROLE_REASON);
			roleDT.setRecordStatusCd(EdxELRConstants.ELR_ACTIVE);
			roleDT.setStatusCd(EdxELRConstants.ELR_ACTIVE_CD);
			roleDT.setLastChgTime(organizationVO.getTheOrganizationDT().getAddTime());
			roleDT.setSubjectClassCd(EdxELRConstants.ELR_SENDING_HCFAC);
			roleDT.setSubjectEntityUid(organizationVO.getTheOrganizationDT().getOrganizationUid());
			roleDT.setScopingEntityUid(edxLabInformationDT.getPatientUid());
			labResultProxyVO.getTheRoleDTCollection().add(roleDT);
			
			Collection<Object> orgNameColl = new ArrayList<Object>(); 
			OrganizationNameDT organizationNameDT = new OrganizationNameDT();
			organizationNameDT.setNmTxt(hl7XONTypeName.getHL7OrganizationName());
			organizationNameDT.setNmUseCd(EdxELRConstants.ELR_LEGAL_NAME);
			organizationNameDT.setOrganizationNameSeq(new Integer(1));
			organizationDT.setDisplayNm(organizationNameDT.getNmTxt());
			orgNameColl.add(organizationNameDT);
			
			organizationVO.setTheOrganizationNameDTCollection(orgNameColl);
			Hl7ToNBSObjectConverter hl7ToNBSObjectConverter = new Hl7ToNBSObjectConverter();
			HL7XADType addressType = hl7OBXType.getPerformingOrganizationAddress();
			Collection<Object> addressCollection = new ArrayList<Object>();
			
			if (addressType != null) {
				
					EntityLocatorParticipationDT elpDT = hl7ToNBSObjectConverter
							.organizationAddressType(addressType,
									EdxELRConstants.ELR_OP_CD, organizationVO);
					addressCollection.add(elpDT);
			}
			
		} catch (Exception e) {
			logger.error("ObservationResultRequest.getPerformingFacility Exception thrown while parsing XML document. Please check!!!"+e.getMessage(), e);
			throw new NEDSSAppException("Exception thrown at ObservationResultRequest.getPerformingFacility:"+ e.getMessage());

		}
		 return organizationVO;
	}
	
	private void formatValue( String text, HL7OBXType hl7OBXType,ObservationVO observationVO,EdxLabInformationDT edxLabInformationDT, String elementName) throws NEDSSAppException{
		String type = "";
		try {
			HL7CommonLabUtil hL7CommonLabUtil = new HL7CommonLabUtil();
			type = hl7OBXType.getValueType();
			HL7CEType cEUnit = hl7OBXType.getUnits();
			if(type!=null){
				if(type.equals(EdxELRConstants.ELR_CODED_WITH_EXC_CD) ||type.equals(EdxELRConstants.ELR_CODED_EXEC_CD)){
					if(text!=null){
						ObsValueCodedDT obsvalueDT= new ObsValueCodedDT();
						obsvalueDT.setItNew(true);
						obsvalueDT.setItDirty(false);
						String[] textValue = text.split("\\^");
						
						if (textValue != null && textValue.length>0) {
							obsvalueDT.setCode(textValue[0]);
							obsvalueDT.setDisplayName(textValue[1]);
							if(textValue.length==2){
								obsvalueDT.setCodeSystemCd(EdxELRConstants.ELR_SNOMED_CD);
							}else if(textValue.length==3 || textValue.length>3){
								obsvalueDT.setCodeSystemCd(textValue[2]);
							}
							if (textValue.length == 6) {
								obsvalueDT.setAltCd(textValue[3]);
								obsvalueDT.setAltCdDescTxt(textValue[4]);
								if(textValue.length==4){
									obsvalueDT.setAltCdSystemCd(EdxELRConstants.ELR_LOCAL_CD);
								}else if(textValue.length==5 || textValue.length>5){
									obsvalueDT.setAltCdSystemCd(textValue[5]);
								}
							}
						}
						if((obsvalueDT.getCode()==null || obsvalueDT.getCode().trim().equals(""))
								&& (obsvalueDT.getAltCd()==null || obsvalueDT.getAltCd().trim().equals(""))){
							edxLabInformationDT.setReflexResultedTestCdMissing(true);
							edxLabInformationDT.setErrorText(EdxELRConstants.ELR_MASTER_LOG_ID_19);
							String xmlElementName = hL7CommonLabUtil.getXMLElementName(hl7OBXType)+"."+elementName;
							throw new NEDSSAppException(EdxELRConstants.NO_REFLEX_RESULT_NM+" XMLElementName: "+xmlElementName);
						}
						
						if((obsvalueDT.getCode()==null || obsvalueDT.getCode().trim().equals("")) && obsvalueDT.getAltCd()!=null){
							obsvalueDT.setCode(obsvalueDT.getAltCd());
							obsvalueDT.setDisplayName(obsvalueDT.getAltCdDescTxt());
							obsvalueDT.setCodeSystemCd(obsvalueDT.getAltCdSystemCd());
							obsvalueDT.setAltCd(null);
							obsvalueDT.setAltCdDescTxt(null);
							obsvalueDT.setAltCdSystemCd(null);
						}
							
						if(obsvalueDT.getCodeSystemCd()!=null && obsvalueDT.getCodeSystemCd().equalsIgnoreCase(EdxELRConstants.ELR_SNOMED_CD))
							obsvalueDT.setCodeSystemDescTxt(EdxELRConstants.ELR_SNOMED_DESC);
						else if(obsvalueDT.getCodeSystemCd()!=null && obsvalueDT.getCodeSystemCd().equalsIgnoreCase(EdxELRConstants.ELR_LOCAL_CD))
							obsvalueDT.setCodeSystemDescTxt(EdxELRConstants.ELR_LOCAL_DESC);
						if(obsvalueDT.getAltCdSystemCd()!=null && obsvalueDT.getAltCdSystemCd().equalsIgnoreCase(EdxELRConstants.ELR_SNOMED_CD))
							obsvalueDT.setAltCdSystemDescTxt(EdxELRConstants.ELR_SNOMED_DESC);
						else if(obsvalueDT.getAltCdSystemCd()!=null && obsvalueDT.getAltCdSystemCd().equalsIgnoreCase(EdxELRConstants.ELR_LOCAL_CD))
							obsvalueDT.setAltCdSystemDescTxt(EdxELRConstants.ELR_LOCAL_DESC);
						
						
						
						obsvalueDT.setObservationUid(observationVO.getTheObservationDT().getObservationUid()); 

						if(observationVO.getTheObsValueCodedDTCollection()==null)
											observationVO.setTheObsValueCodedDTCollection(new ArrayList<Object>());
						observationVO.getTheObsValueCodedDTCollection().add(obsvalueDT);
					}
					

				
				} else if (type.equals(EdxELRConstants.ELR_STUCTURED_NUMERIC_CD)) {
					ObsValueNumericDT obsValueNumericDT = new ObsValueNumericDT();
					obsValueNumericDT.setObsValueNumericSeq(new Integer(1));
					StringTokenizer st = new StringTokenizer(text, "^");
					obsValueNumericDT.setItNew(true);
					obsValueNumericDT.setItDirty(false);
					int i = 0;
					if (text.indexOf("^") == 0) {
						i = 1;
					}
					while (st.hasMoreTokens()) {
						i++;
						String token = st.nextToken();
						if (i == 1) {
							if (token != null && token.equals("&lt;"))
								obsValueNumericDT.setComparatorCd1("<");
							else if (token != null && token.equals("&gt;"))
								obsValueNumericDT.setComparatorCd1(">");
							else
								obsValueNumericDT.setComparatorCd1(token);
						} else if (i == 2)
							obsValueNumericDT.setNumericValue1_s(token);
						else if (i == 3)
							obsValueNumericDT.setSeparatorCd(token);
						else if (i == 4)
							obsValueNumericDT.setNumericValue2(new BigDecimal(token));
					}
					if (cEUnit != null)
						obsValueNumericDT.setNumericUnitCd(cEUnit.getHL7Identifier());
					obsValueNumericDT.setObservationUid(observationVO.getTheObservationDT().getObservationUid());
					if (observationVO.getTheObsValueNumericDTCollection() == null)
						observationVO.setTheObsValueNumericDTCollection(new ArrayList<Object>());
					observationVO.getTheObsValueNumericDTCollection().add(obsValueNumericDT);

				}

				else if (type.equals(EdxELRConstants.ELR_NUMERIC_CD)) {
					ObsValueNumericDT obsValueNumericDT = new ObsValueNumericDT();
					obsValueNumericDT.setObsValueNumericSeq(new Integer(1));
					obsValueNumericDT.setItNew(true);
					obsValueNumericDT.setItDirty(false);

					obsValueNumericDT.setNumericValue1_s(text);

					if (cEUnit != null)
						obsValueNumericDT.setNumericUnitCd(cEUnit.getHL7Identifier());
					obsValueNumericDT.setObservationUid(observationVO.getTheObservationDT().getObservationUid());
					if (observationVO.getTheObsValueNumericDTCollection() == null)
						observationVO.setTheObsValueNumericDTCollection(new ArrayList<Object>());
					observationVO.getTheObsValueNumericDTCollection().add(obsValueNumericDT);

				} else if (type.equals(EdxELRConstants.ELR_STRING_CD) || type.equals(EdxELRConstants.ELR_TEXT_CD)
						|| type.equals(EdxELRConstants.ELR_TEXT_DT) || type.equals(EdxELRConstants.ELR_TEXT_TS)) {
					ObsValueTxtDT obsValueTxtDT = new ObsValueTxtDT();
					StringTokenizer st = new StringTokenizer(text, "^");
					int i = 0;
					if (observationVO.getTheObsValueTxtDTCollection() == null)
						observationVO.setTheObsValueTxtDTCollection(new ArrayList<Object>());

					while (st.hasMoreTokens()) {
						String token = st.nextToken();
						i = observationVO.getTheObsValueTxtDTCollection().size() + 1;
						obsValueTxtDT.setValueTxt(token);
						obsValueTxtDT.setObsValueTxtSeq(i++);
						obsValueTxtDT.setTxtTypeCd(EdxELRConstants.ELR_OFFICE_CD);
						obsValueTxtDT.setObservationUid(observationVO.getTheObservationDT().getObservationUid());
						obsValueTxtDT.setItNew(true);
						obsValueTxtDT.setItDirty(false);

					}
					observationVO.getTheObsValueTxtDTCollection().add(obsValueTxtDT);

				} else {
					edxLabInformationDT.setUnexpectedResultType(true);
					edxLabInformationDT.setErrorText(EdxELRConstants.ELR_MASTER_LOG_ID_13);
					throw new NEDSSAppException(EdxELRConstants.UNEXPECTED_RESULT_TYPE);
				}
			}
		} catch (Exception e) {
			logger.error("ObservationResultRequest.formatValue Exception thrown while observation value. Please check!!!"+e.getMessage(), e);
			throw new NEDSSAppException("Exception thrown at ObservationResultRequest.formatValue for text:\""+text+"\" and for type:\""+ type+"\"."+e.getMessage());
		}
		
	}

	
}
