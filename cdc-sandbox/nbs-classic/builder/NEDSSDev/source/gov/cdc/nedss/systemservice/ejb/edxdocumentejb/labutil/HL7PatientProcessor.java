package gov.cdc.nedss.systemservice.ejb.edxdocumentejb.labutil;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

import gov.cdc.nedss.association.dt.ParticipationDT;
import gov.cdc.nedss.association.dt.RoleDT;
import gov.cdc.nedss.elr.ejb.msginprocessor.helper.ELRConstants;
import gov.cdc.nedss.entity.entityid.dt.EntityIdDT;
import gov.cdc.nedss.entity.person.dt.PersonDT;
import gov.cdc.nedss.entity.person.dt.PersonEthnicGroupDT;
import gov.cdc.nedss.entity.person.dt.PersonRaceDT;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.locator.dt.EntityLocatorParticipationDT;
import gov.cdc.nedss.phdc.HL7CEType;
import gov.cdc.nedss.phdc.HL7CWEType;
import gov.cdc.nedss.phdc.HL7CXType;
import gov.cdc.nedss.phdc.HL7NK1Type;
import gov.cdc.nedss.phdc.HL7PATIENTRESULTType;
import gov.cdc.nedss.phdc.HL7PIDType;
import gov.cdc.nedss.phdc.HL7XADType;
import gov.cdc.nedss.phdc.HL7XPNType;
import gov.cdc.nedss.phdc.HL7XTNType;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.LabResultProxyVO;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dao.ELRXRefDAOImpl;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dt.EdxLabInformationDT;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

/**
 * 
 * @author Pradeep Kumar Sharma Utility class to parse patient information
 * 
 */
public class HL7PatientProcessor {
	static final LogUtils logger = new LogUtils(
			HL7PatientProcessor.class.getName());

	public LabResultProxyVO getPatientAndNextOfKin(
			HL7PATIENTRESULTType hl7PatientResult,
			LabResultProxyVO labResultProxyVO,
			EdxLabInformationDT edxLabInformationDT) throws NEDSSAppException {
		try {
			if (hl7PatientResult != null
					&& hl7PatientResult.getPATIENT() != null
					&& hl7PatientResult.getPATIENT().getPatientIdentification() != null) {
				HL7PIDType patientInfo = hl7PatientResult.getPATIENT()
						.getPatientIdentification();
				getPatientVO(patientInfo, labResultProxyVO, edxLabInformationDT);
				//edxLabInformationDT.setNextUid((edxLabInformationDT.getNextUid()));
			}
			if(hl7PatientResult!=null && hl7PatientResult.getPATIENT()!=null && hl7PatientResult.getPATIENT().getNextofKinAssociatedPartiesArray()!=null){
				HL7NK1Type[] hl7NK1Array = hl7PatientResult.getPATIENT()
						.getNextofKinAssociatedPartiesArray();
				for (int j = 0; j < hl7NK1Array.length; j++) {
					HL7NK1Type hl7NK1Type = hl7NK1Array[j];
					getNextOfKinVO(hl7NK1Type, labResultProxyVO,
							edxLabInformationDT);
					break;
					//edxLabInformationDT.setNextUid((edxLabInformationDT.getNextUid() - 1));
	
				}
			}
		} catch (Exception e) {
			logger.error("Exception thrown by HL7PatientProcessor.getPatientAndNextOfKin "
					+ e);
			throw new NEDSSAppException("Exception thrown at HL7PatientProcessor.getPatientAndNextOfKin:"+ e.getMessage() + e);
		}
		return labResultProxyVO;

	}

	public LabResultProxyVO getNextOfKinVO(HL7NK1Type hl7NK1Type,
			LabResultProxyVO labResultProxyVO,
			EdxLabInformationDT edxLabInformationDT) throws NEDSSAppException {
		try {
			Hl7ToNBSObjectConverter hl7ToNBSObjectConverter = new Hl7ToNBSObjectConverter();
			edxLabInformationDT.setRole(EdxELRConstants.ELR_NEXT_OF_KIN);
			if(hl7NK1Type.getRelationship()!=null){
				edxLabInformationDT.setRelationship(hl7NK1Type.getRelationship().getHL7Identifier()); 
				String desc=CachedDropDowns.getCodeDescTxtForCd(edxLabInformationDT.getRelationship(), EdxELRConstants.ELR_NEXT_OF_KIN_RL_CLASS);
				if(desc!=null && desc.trim().length()>0 && hl7NK1Type.getRelationship().getHL7Text()==null)
					edxLabInformationDT.setRelationshipDesc(desc);
				else if(hl7NK1Type.getRelationship().getHL7Text()!=null)
					edxLabInformationDT.setRelationshipDesc(hl7NK1Type.getRelationship().getHL7Text());
			}
			PersonVO personVO = personVO(labResultProxyVO,edxLabInformationDT);
			
			HL7XADType[] addressArray = hl7NK1Type.getAddressArray();
			Collection<Object> addressCollection = new ArrayList<Object>();
			for (int j = 0; j < addressArray.length;) {
				HL7XADType addressType = addressArray[j];
				hl7ToNBSObjectConverter.personAddressType(addressType,
								EdxELRConstants.ELR_NEXT_OF_KIN, personVO);
				break;
			}

			HL7XPNType[] nameArray = hl7NK1Type.getNameArray();

			for (int j = 0; j < nameArray.length; j++) {
				HL7XPNType hl7XPNType = nameArray[j];
				hl7ToNBSObjectConverter.MapPersonNameType(hl7XPNType, personVO);
				break;
			}

			HL7XTNType[] phoneHomeArray = hl7NK1Type.getPhoneNumberArray();
			for (int j = 0; j < phoneHomeArray.length; j++) {
				HL7XTNType phoneType = phoneHomeArray[j];
				EntityLocatorParticipationDT elpDT = hl7ToNBSObjectConverter
						.personTelePhoneType(phoneType,
								EdxELRConstants.ELR_NEXT_OF_KIN, personVO);
				addressCollection.add(elpDT);
				break;
			}
			labResultProxyVO.getThePersonVOCollection().add(personVO);
		} catch (Exception e) {
			logger.error("Exception thrown by HL7PatientProcessor.getNextOfKinVO "
					+ e);
			throw new NEDSSAppException("Exception thrown at HL7PatientProcessor.getNextOfKinVO:"+ e);
		}
		return labResultProxyVO;
	}

	public LabResultProxyVO getPatientVO(HL7PIDType hl7PIDType,
			LabResultProxyVO labResultProxyVO,
			EdxLabInformationDT edxLabInformationDT) throws NEDSSAppException {

		try {
			Hl7ToNBSObjectConverter hl7ToNBSObjectConverter = new Hl7ToNBSObjectConverter();
			CachedDropDownValues cddv = new CachedDropDownValues();
			edxLabInformationDT.setRole(EdxELRConstants.ELR_PATIENT_CD);
			PersonVO personVO = personVO(labResultProxyVO,edxLabInformationDT);

			for (int j = 0; j < hl7PIDType.getPatientIdentifierListArray().length; j++) {
				HL7CXType hl7CXType = hl7PIDType
						.getPatientIdentifierListArray(j);

				EntityIdDT entityIdDT = hl7ToNBSObjectConverter
						.processEntityData(hl7CXType, personVO, null, j);
				if(entityIdDT!=null && entityIdDT.getAssigningAuthorityIdType() == null)
					entityIdDT.setAssigningAuthorityIdType(edxLabInformationDT.getUniversalIdType());
				if(entityIdDT != null && entityIdDT.getTypeCd()!=null &&  entityIdDT.getTypeCd().equals(EdxELRConstants.ELR_SS_TYPE)){
					String SSNNumberinit =entityIdDT.getRootExtensionTxt().replace("-", "");
	 				String SSNNumber =SSNNumberinit.replace(" ", "");
	 				try {
	 					if(SSNNumber.length()!=9)
	 						edxLabInformationDT.setSsnInvalid(true);
						Integer.parseInt(SSNNumber);
	 				}
	 				catch (NumberFormatException e) {
						edxLabInformationDT.setSsnInvalid(true);
					}
	 				entityIdDT =hl7ToNBSObjectConverter.validateSSN(entityIdDT);
	 				personVO.getThePersonDT().setSSN(entityIdDT.getRootExtensionTxt());
				}
				if(personVO.getTheEntityIdDTCollection()==null)
					personVO.setTheEntityIdDTCollection(new ArrayList<Object>());
				if(entityIdDT.getEntityUid()!=null)
					personVO.getTheEntityIdDTCollection().add(entityIdDT);
			}
			if (labResultProxyVO.getTheParticipationDTCollection() == null)
				labResultProxyVO
						.setTheParticipationDTCollection(new ArrayList<Object>());
			ParticipationDT participationDT = new ParticipationDT();
			participationDT.setSubjectEntityUid(personVO.getThePersonDT()
					.getPersonUid());
			
			participationDT.setItNew(true);
			participationDT.setItDirty(false);
			participationDT.setCd(EdxELRConstants.ELR_PATIENT_CD);
			 participationDT.setAddUserId(EdxELRConstants.ELR_ADD_USER_ID);

			participationDT.setSubjectClassCd(EdxELRConstants.ELR_PERSON_CD);
			participationDT.setTypeCd(EdxELRConstants.ELR_PATIENT_SUBJECT_CD);
			participationDT.setStatusCd(EdxELRConstants.ELR_ACTIVE_CD);
			participationDT.setRecordStatusCd(EdxELRConstants.ELR_ACTIVE);
			
			participationDT
					.setTypeDescTxt(EdxELRConstants.ELR_PATIENT_SUBJECT_DESC);
			participationDT.setActUid(edxLabInformationDT.getRootObserbationUid());
			participationDT.setActClassCd(EdxELRConstants.ELR_OBS);
			//participationDT.setRoleSeq(new Integer(1));
			labResultProxyVO.getTheParticipationDTCollection().add(
					participationDT);

			personVO.getThePersonDT().setAddReasonCd(EdxELRConstants.ELR_ADD_REASON_CD);
			personVO.getThePersonDT().setCurrSexCd(
					hl7PIDType.getAdministrativeSex());
			personVO.getThePersonDT().setElectronicInd(
					ELRConstants.ELECTRONIC_IND);
			String toCode = CachedDropDowns.findToCode("ELR_LCA_SEX", personVO
					.getThePersonDT().getCurrSexCd(), "P_SEX");
			if (toCode != null && !toCode.equals("") && !toCode.equals(" ")){
				personVO.getThePersonDT().setCurrSexCd(toCode.trim());
				edxLabInformationDT.setSexTranslated(true);
			}else{
				edxLabInformationDT.setSexTranslated(false);
			}
//			String Indicator = EdxELRConstants.ELR_ALTERNAT_EPERSON_IDENTIFIER;
//			for (int j = 0; j < hl7PIDType.getAlternatePatientIDPIDArray().length; j++) {
//				HL7CXType hl7CXType = hl7PIDType
//						.getAlternatePatientIDPIDArray(j);
//				int k= 1;
//				if(personVO.getTheEntityIdDTCollection()!=null )
//					k= personVO.getTheEntityIdDTCollection().size();
//				EntityIdDT entityIdDT = Hl7ToNBSObjectConverter
//						.processEntityData(hl7CXType, personVO, Indicator, k);
//				if(personVO.getTheEntityIdDTCollection()==null)
//					personVO.setTheEntityIdDTCollection(new ArrayList<Object>());
//				personVO.getTheEntityIdDTCollection().add(entityIdDT);
//			}
	
			
			if (hl7PIDType.getDateTimeOfBirth() != null) {
				Timestamp timestamp = hl7ToNBSObjectConverter
						.processHL7TSTypeForDOBWithoutTime(hl7PIDType.getDateTimeOfBirth());
				personVO.getThePersonDT().setBirthTime(timestamp);
				personVO.getThePersonDT().setBirthTimeCalc(timestamp);
			}
			
			
			if(hl7PIDType.getBirthPlace()!=null && !hl7PIDType.getBirthPlace().trim().equals("")){
				hl7ToNBSObjectConverter.setPersonBirthType(hl7PIDType.getBirthPlace(), personVO);
			}
//			if(hl7PIDType.getDriversLicenseNumberPatient()!=null){
//					HL7DLNType hl7DLNType = hl7PIDType.getDriversLicenseNumberPatient();
//					int j = 0;
//					if(personVO.getTheEntityIdDTCollection()!=null )
//						j= personVO.getTheEntityIdDTCollection().size();
//					EntityIdDT entityIdDT= new EntityIdDT();
//					entityIdDT.setEntityUid(personVO.getThePersonDT().getPersonUid());
//					entityIdDT.setAddTime(personVO.getThePersonDT().getAddTime());
//					entityIdDT.setEntityIdSeq(j + 1);
//					entityIdDT.setTypeCd(EdxELRConstants.ELR_DRIVER_LIC_CD);
//					entityIdDT.setTypeDescTxt(EdxELRConstants.ELR_DRIVER_LIC_DESC);
//					entityIdDT.setRootExtensionTxt(hl7DLNType.getHL7LicenseNumber());
//					entityIdDT.setAssigningAuthorityCd(hl7DLNType.getHL7IssuingStateProvinceCountry());
//					entityIdDT.setAddTime(personVO.getThePersonDT().getAddTime());
//					if(hl7DLNType.getHL7ExpirationDate()!=null){
//						Timestamp aValidToTime =Hl7ToNBSObjectConverter.processHL7DTType(hl7DLNType.getHL7ExpirationDate());
//						entityIdDT.setValidToTime(aValidToTime);
//					}
//					entityIdDT.setStatusCd(EdxELRConstants.ELR_ACTIVE_CD);
//					entityIdDT.setAsOfDate(personVO.getThePersonDT().getAddTime());
//					entityIdDT.setStatusTime_s(personVO.getThePersonDT().getAddTime()
//							.toString());
//					entityIdDT.setRecordStatusTime_s(personVO.getThePersonDT()
//							.getAddTime().toString());
//					entityIdDT.setRecordStatusCd(NEDSSConstants.ACTIVE);
//					entityIdDT.setItNew(true);
//					entityIdDT.setItDirty(false);
//
//					if(personVO.getTheEntityIdDTCollection()==null)
//						personVO.setTheEntityIdDTCollection(new ArrayList<Object>());
//					if(entityIdDT.getEntityUid()!=null)
//						personVO.getTheEntityIdDTCollection().add(entityIdDT);
//			}
			//hl7PIDType.getEthnicGroupArray();
			Collection<Object> ethnicColl = new ArrayList<Object>();
			HL7CWEType[] ethnicArray = hl7PIDType.getEthnicGroupArray();
			for (int j = 0; j < ethnicArray.length; j++) {
				HL7CWEType ethnicType = ethnicArray[j];
				
				PersonEthnicGroupDT personEthnicGroupDT = hl7ToNBSObjectConverter
						.ethnicGroupType(ethnicType, personVO);
				String ethnicGroupCd = CachedDropDowns.findToCode("ELR_LCA_ETHN_GRP",
						personEthnicGroupDT.getEthnicGroupCd(), "P_ETHN_GRP");
				if(ethnicGroupCd!=null && !ethnicGroupCd.trim().equals("")){
					personEthnicGroupDT.setEthnicGroupCd(ethnicGroupCd);
				}
				if (personEthnicGroupDT.getEthnicGroupCd() != null
						&& !personEthnicGroupDT.getEthnicGroupCd().trim().equals("")
						&& !cddv.reverseMap(cddv.getCodedValuesAsTreeMap("P_ETHN_GRP"))
								.containsKey(personEthnicGroupDT.getEthnicGroupCd()))
					edxLabInformationDT.setEthnicityCodeTranslated(false);
				if (personEthnicGroupDT.getEthnicGroupCd() != null
						&& !personEthnicGroupDT.getEthnicGroupCd().trim().equals("")) {
					ethnicColl.add(personEthnicGroupDT);
					personVO.getThePersonDT().setEthnicGroupInd(personEthnicGroupDT.getEthnicGroupCd());
				}
				else
					logger.info("Blank value recived for PID-22, Ethinicity");
				personVO.setThePersonEthnicGroupDTCollection(ethnicColl);
			}
			

			//hl7PIDType.getIdentityReliabilityCodeArray();
			//hl7PIDType.getIdentityUnknownIndicator();
			//hl7PIDType.getLastUpdateDateTime();
			//hl7PIDType.getLastUpdateFacility();

			HL7CEType maritalStatusType = hl7PIDType.getMaritalStatus();
			
			if(maritalStatusType!= null && maritalStatusType.getHL7Identifier()!=null){
				personVO.getThePersonDT().setMaritalStatusCd(maritalStatusType.getHL7Identifier().toUpperCase());
						personVO.getThePersonDT().setMaritalStatusDescTxt(maritalStatusType.getHL7Text());
			}

			if(hl7PIDType.getMothersIdentifierArray()!=null){
				for(int i=0; i <hl7PIDType.getMothersIdentifierArray().length; i++){
					HL7CXType hl7CXType = (HL7CXType)hl7PIDType.getMothersIdentifierArray()[i];
					int j = i;
					if(personVO.getTheEntityIdDTCollection()!=null )
						j= personVO.getTheEntityIdDTCollection().size();
					EntityIdDT entityIdDT = hl7ToNBSObjectConverter
							.processEntityData(hl7CXType, personVO, EdxELRConstants.ELR_MOTHER_IDENTIFIER, j);
					if(personVO.getTheEntityIdDTCollection()==null)
						personVO.setTheEntityIdDTCollection(new ArrayList<Object>());
					if(entityIdDT.getEntityUid()!=null)
						personVO.getTheEntityIdDTCollection().add(entityIdDT);
				}
			}
			
			//hl7PIDType.getMothersMaidenNameArray();  
			if(hl7PIDType.getMothersMaidenNameArray()!=null && (hl7PIDType.getMothersMaidenNameArray().length>0)){
				String surname = "";
				if(hl7PIDType.getMothersMaidenNameArray(0).getHL7FamilyName()!=null)
					surname = hl7PIDType.getMothersMaidenNameArray(0).getHL7FamilyName().getHL7Surname();
				String givenName = hl7PIDType.getMothersMaidenNameArray(0).getHL7GivenName();
				String motherMaidenNm = "";
				if(surname!= null)	
					motherMaidenNm = surname;
				if(givenName!= null)	
					motherMaidenNm =  motherMaidenNm + " " + givenName;
				personVO.getThePersonDT().setMothersMaidenNm(motherMaidenNm.trim());
			} 
			 
			//hl7PIDType.getMultipleBirthIndicator();
			if(hl7PIDType.getBirthOrder()!=null && hl7PIDType.getBirthOrder().isSetHL7Numeric()) 
				personVO.getThePersonDT().setBirthOrderNbr(Math.round(hl7PIDType.getBirthOrder().getHL7Numeric()));
			if(hl7PIDType.getMultipleBirthIndicator()!=null){  
				personVO.getThePersonDT().setMultipleBirthInd(hl7PIDType.getMultipleBirthIndicator());
			}
			hl7PIDType.getNationality();  
			if(hl7PIDType.getPatientAccountNumber()!=null){
				int j = 1;
				if(personVO.getTheEntityIdDTCollection()!=null )
					j= personVO.getTheEntityIdDTCollection().size();
				EntityIdDT entityIdDT = hl7ToNBSObjectConverter
						.processEntityData(hl7PIDType.getPatientAccountNumber(), personVO, EdxELRConstants.ELR_ACCOUNT_IDENTIFIER, j);
				if(personVO.getTheEntityIdDTCollection()==null)
					personVO.setTheEntityIdDTCollection(new ArrayList<Object>());
				if(entityIdDT.getEntityUid()!=null)
					personVO.getTheEntityIdDTCollection().add(entityIdDT);
				
			}
			HL7XADType[] addressArray = hl7PIDType.getPatientAddressArray();
			Collection<Object> addressCollection = new ArrayList<Object>();
			for (int j = 0; j < addressArray.length; ) {
				HL7XADType addressType = addressArray[j];
				hl7ToNBSObjectConverter.personAddressType(addressType,
						EdxELRConstants.ELR_PATIENT_CD, personVO);
				break;
			}

			// personVO.setTheEntityLocatorParticipationDTCollection(addressCollection);

			personVO.getThePersonDT().setDeceasedIndCd(
					hl7PIDType.getPatientDeathIndicator());
			hl7PIDType.getPatientID();
			if (hl7PIDType.getPatientDeathDateAndTime() != null) {
				personVO.getThePersonDT().setDeceasedTime(
						hl7ToNBSObjectConverter.processHL7TSType(hl7PIDType
								.getPatientDeathDateAndTime(), EdxELRConstants.DATE_VALIDATION_PATIENT_DEATH_DATE_AND_TIME_MSG));
			}

			HL7XPNType[] nameArray = hl7PIDType.getPatientNameArray();
			for (int j = 0; j < nameArray.length; j++) {
				HL7XPNType hl7XPNType = nameArray[j];
				hl7ToNBSObjectConverter.MapPersonNameType(hl7XPNType,personVO);
			}
//			if(hl7PIDType.getPatientAlias()!=null)
//				Hl7ToNBSObjectConverter.MapPersonNameType(hl7PIDType.getPatientAlias(), true,personVO);
			if(hl7PIDType.getPhoneNumberBusinessArray()!=null){
			HL7XTNType[] phoneBusinessArray = hl7PIDType.getPhoneNumberBusinessArray();
				for (int j = 0; j < phoneBusinessArray.length; j++) {
					HL7XTNType phoneType = phoneBusinessArray[j];
					EntityLocatorParticipationDT elpDT = hl7ToNBSObjectConverter
							.personTelePhoneType(phoneType,
									EdxELRConstants.ELR_PATIENT_CD, personVO);
					elpDT.setUseCd(NEDSSConstants.WORK_PHONE);
					addressCollection.add(elpDT);
					break;
				}
			}
			
			if(hl7PIDType.getPhoneNumberHomeArray()!=null){

				HL7XTNType[] phoneHomeArray = hl7PIDType.getPhoneNumberHomeArray();
				for (int j = 0; j < phoneHomeArray.length; j++) {
					HL7XTNType phoneType = phoneHomeArray[j];
					EntityLocatorParticipationDT elpDT = hl7ToNBSObjectConverter
							.personTelePhoneType(phoneType,
									EdxELRConstants.ELR_PATIENT_CD, personVO);
					elpDT.setUseCd(NEDSSConstants.HOME);
					addressCollection.add(elpDT);
					break;
				}
			}
			hl7PIDType.getPrimaryLanguageArray();
			hl7PIDType.getProductionClassCodeArray();
			if(hl7PIDType.getRaceArray()!=null){
				Collection<Object> raceColl = new ArrayList<Object>();
				HL7CWEType[] raceArray = hl7PIDType.getRaceArray();
				PersonRaceDT raceDT = null;
				for (int j = 0; j < raceArray.length; j++) {
					try {
						HL7CWEType raceType = raceArray[j];
						raceDT = hl7ToNBSObjectConverter.raceType(
								raceType, personVO);
						raceDT.setPersonUid(personVO.getThePersonDT()
								.getPersonUid());
						String newRaceCat = CachedDropDowns.findToCode("ELR_LCA_RACE",
								raceDT.getRaceCategoryCd(), "P_RACE_CAT");
						if (newRaceCat != null && !newRaceCat.trim().equals("")) {
							raceDT.setRaceCd(newRaceCat);
							raceDT.setRaceCategoryCd(newRaceCat);
						}
						if (!cddv.getRaceCodes()
								.containsKey(raceDT.getRaceCd()))
							edxLabInformationDT.setRaceTranslated(false);
						raceColl.add(raceDT);
					} catch (NEDSSSystemException e) {
						logger.error("Exception thrown by HL7PatientProcessor.getPatientVO  getting race information"
								+ e);
						throw new NEDSSAppException(
								"Exception thrown at HL7PatientProcessor.getPatientVO getting race information:"
										+ e);
						// TODO Logging
						// createElrActivityLogDt(ELRConstants.ELR_STATUS_CD_ERROR,
						// ELRConstants.ELR_PROCESS_CD_TRANSLATE_RACE);
					}// end of catch
				}
			personVO.setThePersonRaceDTCollection(raceColl);
			}
			hl7PIDType.getReligion();
			hl7PIDType.getSetIDPIDArray();
// 			if(hl7PIDType.getSSNNumberPatient()!=null){
// 				String SSNNumberinit =hl7PIDType.getSSNNumberPatient().replace("-", "");
// 				String SSNNumber =SSNNumberinit.replace(" ", "");
// 				try {
// 					if(SSNNumber.length()!=9)
// 						edxLabInformationDT.setSsnInvalid(true);
//					Integer.parseInt(SSNNumber);
//					EntityIdDT entityIdDT = new EntityIdDT();
//					entityIdDT.setEntityUid(personVO.getThePersonDT().getPersonUid());
//					entityIdDT.setAddTime(personVO.getThePersonDT().getAddTime());
//					if(personVO.getTheEntityIdDTCollection()!=null)
//						entityIdDT.setEntityIdSeq(personVO.getTheEntityIdDTCollection().size()+1);
//					else
//						entityIdDT.setEntityIdSeq(1);
//					entityIdDT.setRootExtensionTxt(hl7PIDType.getSSNNumberPatient());
//					entityIdDT.setTypeCd(EdxELRConstants.ELR_SS_TYPE);
//					entityIdDT.setTypeDescTxt(EdxELRConstants.ELR_SS_DESC_TYPE);
//					entityIdDT.setAssigningAuthorityCd(EdxELRConstants.ELR_SS_AUTHO_TYPE);
//					entityIdDT.setAddUserId(personVO.getThePersonDT().getAddUserId());
//					entityIdDT.setLastChgUserId(personVO.getThePersonDT().getLastChgUserId());
//					entityIdDT.setStatusCd(EdxELRConstants.ELR_ACTIVE_CD);
//					entityIdDT.setStatusTime_s(personVO.getThePersonDT().getAddTime().toString());
//					entityIdDT.setRecordStatusTime_s(personVO.getThePersonDT().getAddTime().toString());
//					entityIdDT.setRecordStatusCd(NEDSSConstants.ACTIVE);
//					entityIdDT.setAsOfDate(personVO.getThePersonDT().getAddTime());
//					entityIdDT.setItNew(true);
//					entityIdDT.setItDirty(false);
//					entityIdDT =Hl7ToNBSObjectConverter.validateSSN(entityIdDT);
//					personVO.getThePersonDT().setSSN(entityIdDT.getRootExtensionTxt());
//					if(personVO.getTheEntityIdDTCollection()==null)
//						personVO.setTheEntityIdDTCollection(new ArrayList<Object>());
//					personVO.getTheEntityIdDTCollection().add(entityIdDT);
// 				} catch (NumberFormatException e) {
//					edxLabInformationDT.setSsnInvalid(true);
//				}
//			}
			if(labResultProxyVO.getThePersonVOCollection()==null){
				labResultProxyVO.setThePersonVOCollection(new ArrayList<Object>());
			}
			labResultProxyVO.getThePersonVOCollection().add(personVO);
		} catch (NEDSSSystemException e) {
			logger.error("Exception thrown by HL7ORCProcessor.getPatientVO "+ e);
			throw new NEDSSAppException("Exception thrown at HL7PatientProcessor.getPatientVO:"+ e.getMessage() + e);
		}
		return labResultProxyVO;
	}

	public PersonVO personVO(LabResultProxyVO labResultProxyVO, EdxLabInformationDT edxLabInformationDT) throws NEDSSAppException {
		PersonVO personVO = new PersonVO();

		try {

			// PersonVO
			PersonDT personDT = personVO.getThePersonDT();
			personVO.getThePersonDT().setElectronicInd(
					ELRConstants.ELECTRONIC_IND);
			if (edxLabInformationDT.getRole().equalsIgnoreCase(NEDSSConstants.PAT) ) {
				personVO.getThePersonDT().setCd(NEDSSConstants.PAT);
				personDT.setCd(EdxELRConstants.ELR_PATIENT_CD);
				personDT.setCdDescTxt(EdxELRConstants.ELR_PATIENT_DESC);
				personDT.setPersonUid(new Long(edxLabInformationDT.getPatientUid()));
			}else if(edxLabInformationDT.getRole().equalsIgnoreCase(EdxELRConstants.ELR_NEXT_OF_KIN)){	
				personVO.setRole(EdxELRConstants.ELR_NEXT_OF_KIN);
				personDT.setCd(EdxELRConstants.ELR_PATIENT_CD);
				personDT.setCdDescTxt(EdxELRConstants.ELR_NOK_DESC);
				personDT.setPersonUid(new Long(edxLabInformationDT.getNextUid()));
			} else {
				personVO.getThePersonDT()
						.setCd(EdxELRConstants.ELR_PROVIDER_CD);
				personDT.setCd(EdxELRConstants.ELR_PROVIDER_CD);
				personDT.setCdDescTxt(EdxELRConstants.ELR_PROVIDER_DESC);
				personDT.setPersonUid(new Long(edxLabInformationDT.getNextUid()));
			}
			personVO.setItDirty(false);
			personVO.getThePersonDT().setItNew(true);
			personVO.getThePersonDT().setItDirty(false);
			personVO.setItNew(true);

			// PersonDT
			personVO.getThePersonDT().setVersionCtrlNbr(new Integer(1));
			personVO.getThePersonDT().setItNew(true);
			personVO.getThePersonDT().setLastChgTime(
					edxLabInformationDT.getAddTime());
			personVO.getThePersonDT().setAddTime(
					edxLabInformationDT.getAddTime());
			personVO.getThePersonDT().setLastChgUserId(
					edxLabInformationDT.getUserId());
			personVO.getThePersonDT().setAddUserId(
					edxLabInformationDT.getUserId());
			personVO.getThePersonDT().setStatusCd(EdxELRConstants.ELR_ACTIVE_CD);
			personVO.getThePersonDT().setRecordStatusCd(EdxELRConstants.ELR_ACTIVE);
			personVO.getThePersonDT().setStatusTime(personVO.getThePersonDT().getLastChgTime());
			
			personDT.setLastChgTime(edxLabInformationDT.getAddTime());
			personDT.setLastChgUserId(edxLabInformationDT.getUserId());

			personDT.setAsOfDateAdmin(edxLabInformationDT.getAddTime());
			personDT.setAsOfDateEthnicity(edxLabInformationDT.getAddTime());
			personDT.setAsOfDateGeneral(edxLabInformationDT.getAddTime());
			personDT.setAsOfDateMorbidity(edxLabInformationDT.getAddTime());
			personDT.setAsOfDateSex(edxLabInformationDT.getAddTime());
			personDT.setAddTime(edxLabInformationDT.getAddTime());
			personDT.setAddUserId(edxLabInformationDT.getUserId());
			
   
			//personDT.setPersonParentUid(personDT.getPersonUid());
			if (personVO.getTheRoleDTCollection() == null)
				personVO.setTheRoleDTCollection(new ArrayList<Object>());
			RoleDT roleDT = new RoleDT();
			roleDT.setSubjectEntityUid(personDT.getPersonUid());
			roleDT.setRecordStatusCd(EdxELRConstants.ELR_ACTIVE);
			boolean addRole= false;
			if (edxLabInformationDT.getRole().equalsIgnoreCase(
					NEDSSConstants.PAT)) {
				roleDT.setCd(NEDSSConstants.PAT);
				roleDT.setCdDescTxt(EdxELRConstants.ELR_PATIENT);
				roleDT.setSubjectClassCd(EdxELRConstants.ELR_PATIENT);
				addRole= true;
			} else if (edxLabInformationDT.getRole().equalsIgnoreCase(
					EdxELRConstants.ELR_NEXT_OF_KIN)) {
				roleDT.setSubjectClassCd(EdxELRConstants.ELR_CON);
				if(edxLabInformationDT.getRelationship()!=null)
					roleDT.setCd(edxLabInformationDT.getRelationship());
				else
					roleDT.setCd(EdxELRConstants.ELR_NEXT_F_KIN_ROLE_CD);
				if(edxLabInformationDT.getRelationshipDesc()!=null)
					roleDT.setCdDescTxt(edxLabInformationDT.getRelationshipDesc());
				else	
					roleDT.setCdDescTxt(EdxELRConstants.ELR_NEXT_F_KIN_ROLE_DESC);
				roleDT.setScopingRoleSeq(new Integer(1));
				roleDT.setScopingEntityUid(edxLabInformationDT.getPatientUid());
				roleDT.setScopingClassCd(EdxELRConstants.ELR_PATIENT_CD);
				roleDT.setScopingRoleCd(EdxELRConstants.ELR_PATIENT_CD);
				addRole= true;
			} else if (edxLabInformationDT.getRole().equalsIgnoreCase(
					EdxELRConstants.ELR_SPECIMEN_PROCURER_CD)) {
				roleDT.setCd(EdxELRConstants.ELR_SPECIMEN_PROCURER_CD);
				roleDT.setSubjectClassCd(EdxELRConstants.ELR_PROVIDER_CD);
				roleDT.setCdDescTxt(EdxELRConstants.ELR_SPECIMEN_PROCURER_DESC);
				roleDT.setScopingClassCd(EdxELRConstants.ELR_PATIENT_CD);
				roleDT.setScopingRoleCd(EdxELRConstants.ELR_PATIENT_CD);
				roleDT.setScopingEntityUid(edxLabInformationDT.getPatientUid());
				roleDT.setScopingRoleSeq(new Integer(1));
				addRole= true;
			} else if (edxLabInformationDT.getRole().equalsIgnoreCase(
					EdxELRConstants.ELR_LAB_PROVIDER_CD) ||
					edxLabInformationDT.getRole().equalsIgnoreCase(EdxELRConstants.ELR_LAB_VERIFIER_CD)||
					edxLabInformationDT.getRole().equalsIgnoreCase(EdxELRConstants.ELR_LAB_ASSISTANT_CD) ||
					edxLabInformationDT.getRole().equalsIgnoreCase(EdxELRConstants.ELR_LAB_PERFORMER_CD) ||
					edxLabInformationDT.getRole().equalsIgnoreCase(EdxELRConstants.ELR_LAB_ENTERER_CD)) 
					{ 
				roleDT.setCd(EdxELRConstants.ELR_LAB_PROVIDER_CD);
				roleDT.setSubjectClassCd(EdxELRConstants.ELR_PROVIDER_CD);
				roleDT.setCdDescTxt(EdxELRConstants.ELR_LAB_PROVIDER_DESC);
				roleDT.setScopingClassCd(EdxELRConstants.ELR_PATIENT_CD);
				roleDT.setScopingRoleCd(EdxELRConstants.ELR_PATIENT_CD);
				roleDT.setScopingEntityUid(edxLabInformationDT.getPatientUid());
				roleDT.setScopingRoleSeq(new Integer(1));
				addRole= true;
			}else if (edxLabInformationDT.getRole().equalsIgnoreCase(
					EdxELRConstants.ELR_OP_CD)) { 
				roleDT.setCd(EdxELRConstants.ELR_OP_CD);
				roleDT.setSubjectClassCd(EdxELRConstants.ELR_PROVIDER_CD);
				roleDT.setCdDescTxt(EdxELRConstants.ELR_OP_DESC);
				roleDT.setScopingClassCd(EdxELRConstants.ELR_PATIENT_CD);
				roleDT.setScopingRoleCd(EdxELRConstants.ELR_PATIENT_CD);
				roleDT.setScopingRoleSeq(new Integer(1));
				roleDT.setScopingEntityUid(edxLabInformationDT.getPatientUid());
				roleDT.setScopingRoleSeq(new Integer(1));
				addRole= true;
			}else if (edxLabInformationDT.getRole().equalsIgnoreCase(
					EdxELRConstants.ELR_COPY_TO_CD)) { 
				roleDT.setCd(EdxELRConstants.ELR_COPY_TO_CD);
				roleDT.setSubjectClassCd(EdxELRConstants.ELR_PROV_CD);
				roleDT.setCdDescTxt(EdxELRConstants.ELR_COPY_TO_DESC);
				roleDT.setScopingClassCd(EdxELRConstants.ELR_PATIENT_CD);
				roleDT.setScopingRoleCd(EdxELRConstants.ELR_PATIENT_CD);
				roleDT.setScopingRoleSeq(new Integer(1));
				roleDT.setScopingEntityUid(edxLabInformationDT.getPatientUid());
				roleDT.setScopingRoleSeq(new Integer(1));
				addRole= true;
			}
			
			roleDT.setAddUserId(EdxELRConstants.ELR_ADD_USER_ID);
			roleDT.setAddReasonCd(EdxELRConstants.ELR_ADD_REASON_CD);
			roleDT.setRoleSeq(new Long(1));
			roleDT.setStatusCd(EdxELRConstants.ELR_ACTIVE_CD);
			roleDT.setSubjectEntityUid(personDT.getPersonUid());
			roleDT.setItNew(true);
			roleDT.setItDirty(false);
			
			if(addRole){
				if(labResultProxyVO.getTheRoleDTCollection()==null)
					labResultProxyVO.setTheRoleDTCollection(new ArrayList<Object>());
				labResultProxyVO.getTheRoleDTCollection().add(roleDT);
			}
		} catch (Exception e) {
			logger.error("Exception thrown by HL7ORCProcessor.personVO "
					+ e);
			throw new NEDSSAppException("Exception thrown at HL7PatientProcessor.personVO:"+ e);
		}

		return personVO;
	}
	
	
}
