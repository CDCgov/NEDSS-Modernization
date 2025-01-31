package gov.cdc.nedss.systemservice.ejb.edxdocumentejb.cdautil;

import gov.cdc.nedss.controller.ejb.entitycontrollerejb.bean.EntityController;
import gov.cdc.nedss.entity.entityid.dt.EntityIdDT;
import gov.cdc.nedss.entity.organization.dt.OrganizationDT;
import gov.cdc.nedss.entity.organization.dt.OrganizationNameDT;
import gov.cdc.nedss.entity.organization.vo.OrganizationVO;
import gov.cdc.nedss.entity.person.dt.PersonDT;
import gov.cdc.nedss.entity.person.dt.PersonEthnicGroupDT;
import gov.cdc.nedss.entity.person.dt.PersonNameDT;
import gov.cdc.nedss.entity.person.dt.PersonRaceDT;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.entity.place.dt.PlaceDT;
import gov.cdc.nedss.entity.place.vo.PlaceVO;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.locator.dt.EntityLocatorParticipationDT;
import gov.cdc.nedss.locator.dt.PostalLocatorDT;
import gov.cdc.nedss.locator.dt.TeleLocatorDT;
import gov.cdc.nedss.page.ejb.pageproxyejb.vo.act.PageActProxyVO;
import gov.cdc.nedss.phdc.cda.AD;
import gov.cdc.nedss.phdc.cda.AdxpAdditionalLocator;
import gov.cdc.nedss.phdc.cda.AdxpCensusTract;
import gov.cdc.nedss.phdc.cda.AdxpCity;
import gov.cdc.nedss.phdc.cda.AdxpCountry;
import gov.cdc.nedss.phdc.cda.AdxpCounty;
import gov.cdc.nedss.phdc.cda.AdxpPostalCode;
import gov.cdc.nedss.phdc.cda.AdxpState;
import gov.cdc.nedss.phdc.cda.AdxpStreetAddressLine;
import gov.cdc.nedss.phdc.cda.CE;
import gov.cdc.nedss.phdc.cda.II;
import gov.cdc.nedss.phdc.cda.IVLTS;
import gov.cdc.nedss.phdc.cda.PN;
import gov.cdc.nedss.phdc.cda.POCDMT000040Entry;
import gov.cdc.nedss.phdc.cda.POCDMT000040Participant2;
import gov.cdc.nedss.phdc.cda.POCDMT000040ParticipantRole;
import gov.cdc.nedss.phdc.cda.POCDMT000040Patient;
import gov.cdc.nedss.phdc.cda.POCDMT000040PatientRole;
import gov.cdc.nedss.phdc.cda.POCDMT000040PlayingEntity;
import gov.cdc.nedss.phdc.cda.POCDMT000040RecordTarget;
import gov.cdc.nedss.phdc.cda.POCDMT000040Section;
import gov.cdc.nedss.phdc.cda.TEL;
import gov.cdc.nedss.phdc.cda.TS;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.vo.CTContactProxyVO;
import gov.cdc.nedss.systemservice.dt.EDXActivityDetailLogDT;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dt.EdxCDAInformationDT;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.labutil.EdxELRConstants;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.labutil.Hl7ToNBSObjectConverter;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.util.EdxMatchingCriteriaUtil;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.util.EdxPersonEntityProcessor;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.util.CDAXMLTypeToNBSObject;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.util.XMLTypeToNBSObject;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.systemservice.vo.ParticipationTypeVO;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.StringUtils;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CDAEntityProcessor {
	static final LogUtils logger = new LogUtils(
			CDAEntityProcessor.class.getName());

	@SuppressWarnings("unchecked")
	public static PersonVO getPatient(
			POCDMT000040RecordTarget[] recordTargetArray,
			EdxCDAInformationDT informationDT, POCDMT000040Section shSection) throws NEDSSAppException {
		if (recordTargetArray == null || recordTargetArray.length == 0) {
			logger.error("CDAEntityProcessor.getPatient No patient information inthe CDA message, message cannot be processed");
			return null;
		}
		PersonVO person = new PersonVO();
		try {
			POCDMT000040PatientRole patientRole = recordTargetArray[0]
					.getPatientRole();
			POCDMT000040Patient patientInfo = patientRole.getPatient();
			AD[] addresses = patientRole.getAddrArray();
			AD birthAddress = null;
			if(patientRole.getPatient()!=null && patientRole.getPatient().getBirthplace()!=null && patientRole.getPatient().getBirthplace().getPlace()!=null )
				birthAddress = 	patientRole.getPatient().getBirthplace().getPlace().getAddr();
			II[] ids = patientRole.getIdArray();
			TEL[] telephones = patientRole.getTelecomArray();
			PN[] names = patientInfo.getNameArray();
			person.setThePersonNameDTCollection(parsePersonNames(names,
					informationDT));
			parseStandardPatientInfo(patientInfo, person, informationDT);
			if(shSection!=null)
				parseParientInfoFromSocialHistorySection(person,informationDT, shSection);
			person.setTheEntityLocatorParticipationDTCollection(parseAddresses(
					addresses, informationDT));
			if(person.getTheEntityLocatorParticipationDTCollection()==null)
				person.setTheEntityLocatorParticipationDTCollection(new ArrayList<Object>());
			person.getTheEntityLocatorParticipationDTCollection().addAll(parseBirthAddress(
						birthAddress, informationDT));
			if (person.getTheEntityLocatorParticipationDTCollection() == null)
				person.setTheEntityLocatorParticipationDTCollection(new ArrayList<Object>());
			person.getTheEntityLocatorParticipationDTCollection().addAll(
					parseTelephones(telephones, informationDT));
			Map<Object, Object> EntityIdMap = parseEntityIds(ids, informationDT);
			if (EntityIdMap != null) {
				person.setTheEntityIdDTCollection((Collection<Object>) EntityIdMap
						.get(NEDSSConstants.NON_LR_ENTITY_IDS));
				person.setLocalIdentifier((String) EntityIdMap
						.get(NEDSSConstants.PHCR_LOCAL_REGISTRY_ID));
			}
		} catch (Exception ex) {
			String errString = "CDAEntityProcessor.getPatient Exception while parsing patient Info: "
					+ ex.getMessage();
			logger.error(errString);
			ex.printStackTrace();
			throw new NEDSSAppException(errString, ex);
		}
		return person;
	}

	@SuppressWarnings("unchecked")
	public static PersonVO getProviderAssignedEntity(
			POCDMT000040ParticipantRole participantRole,
			EdxCDAInformationDT informationDT) throws NEDSSAppException {

		if (participantRole == null) {
			logger.error("CDAEntityProcessor.getProviderAssignedEntity No provider information in the participantRole");
			return null;
		}
		PersonVO person = new PersonVO();
		try {
			AD[] addresses = participantRole.getAddrArray();
			II[] ids = participantRole.getIdArray();
			TEL[] telephones = participantRole.getTelecomArray();
			 POCDMT000040PlayingEntity playingEntity = participantRole.getPlayingEntity();
			 PN[] names = null;
			 if(playingEntity!=null)
				 names = participantRole.getPlayingEntity().getNameArray();
			 person.setThePersonNameDTCollection(parsePersonNames(names,
						informationDT));
			parseStandardProviderInfo(participantRole, person, informationDT);
			person.setTheEntityLocatorParticipationDTCollection(parseAddresses(
					addresses, informationDT));
			if (person.getTheEntityLocatorParticipationDTCollection() == null)
				person.setTheEntityLocatorParticipationDTCollection(new ArrayList<Object>());
			person.getTheEntityLocatorParticipationDTCollection().addAll(
					parseTelephones(telephones, informationDT));
			Map<Object, Object> EntityIdMap = parseEntityIds(ids, informationDT);
			if (EntityIdMap != null) {
				person.setTheEntityIdDTCollection((Collection<Object>) EntityIdMap
						.get(NEDSSConstants.NON_LR_ENTITY_IDS));
				person.setLocalIdentifier((String) EntityIdMap
						.get(NEDSSConstants.PHCR_LOCAL_REGISTRY_ID));
			}
			
		} catch (Exception ex) {
			String errorString = "Error while parsing out provider entity for PHDC :"+ex.getMessage();
			logger.error(errorString);
			ex.printStackTrace();
			throw new NEDSSAppException(errorString, ex);
		}
		return person;
	}

	@SuppressWarnings("unchecked")
	public static OrganizationVO getOrganizationAssignedEntity(
			POCDMT000040ParticipantRole participantRole,
			EdxCDAInformationDT informationDT) throws NEDSSAppException {
		if (participantRole == null) {
			logger.error("CDAEntityProcessor.getOrganizationAssignedEntity No organization information in the assigned entity");
			return null;
		}
		OrganizationVO org = new OrganizationVO();
		try {
			AD[] addresses = participantRole.getAddrArray();
			II[] ids = participantRole.getIdArray();
			TEL[] telephones = participantRole.getTelecomArray();
			POCDMT000040PlayingEntity playingEntity = participantRole.getPlayingEntity();
			PN[] names = null;
			 if(playingEntity!=null)
				 names = participantRole.getPlayingEntity().getNameArray();
			parseStandardOrganizationInfo(participantRole, org, informationDT);
			org.setTheEntityLocatorParticipationDTCollection(parseAddresses(
					addresses, informationDT));
			if (org.getTheEntityLocatorParticipationDTCollection() == null)
				org.setTheEntityLocatorParticipationDTCollection(new ArrayList<Object>());
			org.getTheEntityLocatorParticipationDTCollection().addAll(
					parseTelephones(telephones, informationDT));
			Map<Object, Object> EntityIdMap = parseEntityIds(ids, informationDT);
			if (EntityIdMap != null) {
				org.setTheEntityIdDTCollection((Collection<Object>) EntityIdMap
						.get(NEDSSConstants.NON_LR_ENTITY_IDS));
				org.setLocalIdentifier((String) EntityIdMap
						.get(NEDSSConstants.PHCR_LOCAL_REGISTRY_ID));
			}
			org.setTheOrganizationNameDTCollection(parseOrganizationNames(names,
					informationDT));
		} catch (Exception e) {
			String errorString = "Error while parsing out organization entity for PHDC :"+e.getMessage();
			e.printStackTrace();
			throw new NEDSSAppException(errorString);
		}
		return org;
	}
	
	@SuppressWarnings("unchecked")
	public static PlaceVO getPlaceAssignedEntity(
			POCDMT000040ParticipantRole participantRole,
			EdxCDAInformationDT informationDT) throws NEDSSAppException {
		if (participantRole == null) {
			logger.error("CDAEntityProcessor.getPlaceAssignedEntity No place information in the assigned entity");
			return null;
		}
		PlaceVO place = new PlaceVO();
		try {
			AD[] addresses = participantRole.getAddrArray();
			II[] ids = participantRole.getIdArray();
			TEL[] telephones = participantRole.getTelecomArray();
			parseStandardPlaceInfo(participantRole, place, informationDT);
			place.setTheEntityLocatorParticipationDTCollection(parseAddresses(
					addresses, informationDT));
			if (place.getTheEntityLocatorParticipationDTCollection() == null)
				place.setTheEntityLocatorParticipationDTCollection(new ArrayList<Object>());
			place.getTheEntityLocatorParticipationDTCollection().addAll(
					parseTelephones(telephones, informationDT));
			Map<Object, Object> EntityIdMap = parseEntityIds(ids, informationDT);
			if (EntityIdMap != null) {
				place.setTheEntityIdDTCollection((Collection<Object>) EntityIdMap
						.get(NEDSSConstants.NON_LR_ENTITY_IDS));
				place.setLocalIdentifier((String) EntityIdMap
						.get(NEDSSConstants.PHCR_LOCAL_REGISTRY_ID));
			}
		} catch (Exception e) {
			String errorString = "Error while parsing out place entity for PHDC :"+e.getMessage();
			e.printStackTrace();
			throw new NEDSSAppException(errorString);
		}
		return place;
	}

	public static void parseStandardPatientInfo(
			POCDMT000040Patient patientInfo, PersonVO personVO,
			EdxCDAInformationDT informationDT) throws NEDSSAppException {
		PersonDT personDT = personVO.getThePersonDT();
		try {
			personDT.setElectronicInd(NEDSSConstants.ELECTRONIC_IND);
			personDT.setEdxInd(NEDSSConstants.EDX_TYPE_ENTITY);
			Timestamp ts = informationDT.getAddTime();
			personDT.setCd(NEDSSConstants.PAT);
			personDT.setCdDescTxt(EdxCDAConstants.CDA_PATIENT_DESC);
			personDT.setPersonUid(informationDT.getPatientUid());
			personVO.setItDirty(false);
			personDT.setItNew(true);
			personDT.setItDirty(false);
			personVO.setItNew(true);
			// PersonDT
			personDT.setVersionCtrlNbr(new Integer(1));
			personDT.setItNew(true);
			personDT.setLastChgTime(ts);
			personDT.setAddTime(ts);
			personDT.setLastChgUserId(informationDT.getUserId());
			personDT.setAddUserId(informationDT.getUserId());
			personDT.setStatusCd(NEDSSConstants.A);
			personDT.setRecordStatusCd(NEDSSConstants.ACTIVE);
			personDT.setRecordStatusTime(ts);
			personDT.setStatusTime(ts);
			Timestamp asOfDate = informationDT.getAsOfDate()==null ? ts : informationDT.getAsOfDate();
			personDT.setAsOfDateAdmin(asOfDate);
			
			personDT.setAsOfDateGeneral(asOfDate);
			CE sex = patientInfo.getAdministrativeGenderCode();
			if (sex != null){
				personDT.setCurrSexCd(sex.getCode());
				personDT.setAsOfDateSex(asOfDate);
			}
			TS birthTime = patientInfo.getBirthTime();
			if (birthTime != null){
				personDT.setBirthTime(parseDateAsTimestamp(birthTime));
				personDT.setAsOfDateSex(asOfDate);
			}
			CE maritalStatus = patientInfo.getMaritalStatusCode();
			if (maritalStatus != null){
				personDT.setMaritalStatusCd(maritalStatus.getCode());
				personDT.setAsOfDateMorbidity(asOfDate);
			}
			// Ethnic Group Code
			Collection<Object> ethnicColl = new ArrayList<Object>();
			CE ethnicCode = patientInfo.getEthnicGroupCode();
			if (ethnicCode != null && ethnicCode.getCode() != null) {
				PersonEthnicGroupDT ethnicGroupDT = new PersonEthnicGroupDT();
				ethnicGroupDT.setItNew(true);
				ethnicGroupDT.setItDirty(false);
				personDT.setAsOfDateEthnicity(asOfDate);
				ethnicGroupDT.setRecordStatusCd(NEDSSConstants.ACTIVE);
				ethnicGroupDT.setPersonUid(personVO.getThePersonDT()
						.getPersonUid());
				ethnicGroupDT
						.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
				ethnicGroupDT.setRecordStatusTime(personVO.getThePersonDT()
						.getAddTime());
				ethnicGroupDT.setEthnicGroupCd(ethnicCode.getCode());
				personVO.getThePersonDT().setEthnicGroupInd(
						ethnicGroupDT.getEthnicGroupCd());
				ethnicGroupDT.setPersonUid(personVO.getThePersonDT()
						.getPersonUid());
				ethnicColl.add(ethnicGroupDT);
				personVO.setThePersonEthnicGroupDTCollection(ethnicColl);
			}
			// Race Code
			// CE raceCode = patientInfo.getRaceCode();
			CE[] raceCodes = patientInfo.getRaceCode2Array();
			if (raceCodes != null && raceCodes.length > 0) {
				Collection<Object> raceColl = new ArrayList<Object>();
				for (CE raceCode : raceCodes) {
					if (raceCode != null && raceCode.getCode() != null) {
						String translatedRaceCd = XMLTypeToNBSObject.getNBSCodeFromPHINCodes("PHVS_RACECATEGORY_CDC_NULLFLAVOR", raceCode.getCode(), NEDSSConstants.CODE_VALUE_GENERAL);
						if(translatedRaceCd==null || translatedRaceCd.trim().length()==0){
							logger.info("The race code "+raceCode.getCode() +" could not be translated to NBS race code, It won't be written to ODSE");
							continue;
						}
						PersonRaceDT raceDT = new PersonRaceDT();
						raceDT.setItNew(true);
						raceDT.setItDelete(false);
						raceDT.setItDirty(false);
						raceDT.setAddTime(new Timestamp(new Date().getTime()));
						raceDT.setAddUserId(personVO.getThePersonDT()
								.getAddUserId());
						raceDT.setRaceCd(XMLTypeToNBSObject.getNBSCodeFromPHINCodes("PHVS_RACECATEGORY_CDC_NULLFLAVOR", raceCode.getCode(), NEDSSConstants.CODE_VALUE_GENERAL));
						raceDT.setRaceCategoryCd(XMLTypeToNBSObject.getNBSCodeFromPHINCodes("PHVS_RACECATEGORY_CDC_NULLFLAVOR", raceCode.getCode(), NEDSSConstants.CODE_VALUE_GENERAL));
						raceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
						raceDT.setRecordStatusTime(personVO.getThePersonDT()
								.getAddTime());
						raceDT.setAsOfDate(asOfDate);
						raceDT.setPersonUid(personVO.getThePersonDT()
								.getPersonUid());
						raceColl.add(raceDT);
					}
				}
				personVO.setThePersonRaceDTCollection(raceColl);
			}
		} catch (Exception ex) {
			String errString = "CDAEntityProcessor.parseStandardPatientInfo Exception while parsing core patient Info: "
					+ ex.getMessage();
			logger.error(errString);
			ex.printStackTrace();
			throw new NEDSSAppException(errString, ex);
		}
	}
	
	public static void parseParientInfoFromSocialHistorySection(
			PersonVO person, EdxCDAInformationDT informationDT,
			POCDMT000040Section shSection) throws NEDSSAppException {
		try {
			if (shSection != null && shSection.getEntryArray() != null
					&& shSection.getEntryArray().length > 0) {
				for (POCDMT000040Entry entry : shSection.getEntryArray()) {
					if (entry.getObservation() != null
							&& entry.getObservation().getCode() != null
							&& entry.getObservation().getCode().getCode() != null
							&& entry.getObservation().getValueArray() != null
							&& entry.getObservation().getValueArray().length > 0) {
						if (entry.getObservation().getCode().getCode().equals("DEM127")) {// Is this person deceased?
							String value = ((CE) entry.getObservation()
									.getValueArray(0)).getCode();
							person.getThePersonDT().setDeceasedIndCd(value);
						} else if (entry.getObservation().getCode().getCode().equals("DEM128")) {//deceased Date?
							String value =  CDAXMLTypeToNBSObject
									.parseStringDate(((TS) entry.getObservation().getValueArray(0)).getValue());
							person.getThePersonDT().setDeceasedTime(StringUtils.stringToStrutsTimestamp(value));
						}else if (entry.getObservation().getCode().getCode().equals("DEM142")) {// Primary language code
							String value = ((CE) entry.getObservation()
									.getValueArray(0)).getCode();
							person.getThePersonDT().setPrimLangCd(value);
						} else if (entry.getObservation().getCode()
								.getCode().equals("DEM196")) {// Patient Comments
							String value = entry.getObservation()
									.getValueArray(0).newCursor()
									.getTextValue();
							person.getThePersonDT().setDescription(value);
						} else if (entry.getObservation().getCode().getCode()
								.equals("NBS213")) {// Patient Gender (Transgender Information)
							String value = entry.getObservation()
									.getValueArray(0).newCursor()
									.getTextValue();
							person.getThePersonDT()
									.setAdditionalGenderCd(value);
						} else if (entry.getObservation().getCode()
								.getCode().equals("NBS214")) {// Patient Speaks English
							String value = ((CE) entry.getObservation()
									.getValueArray(0)).getCode();
							value = XMLTypeToNBSObject.getNBSCodeFromPHINCodes(
									"YNU", value,
									NEDSSConstants.CODE_VALUE_GENERAL);
							person.getThePersonDT().setSpeaksEnglishCd(value);
						}else if (entry.getObservation().getCode()
								.getCode().equals("DEM114")) {// Birth Sex
							String value = ((CE) entry.getObservation()
									.getValueArray(0)).getCode();
							value = XMLTypeToNBSObject.getNBSCodeFromPHINCodes(
									"SEX", value,
									NEDSSConstants.CODE_VALUE_GENERAL);
							person.getThePersonDT().setBirthGenderCd(value);
						} else if (entry.getObservation().getCode()
								.getCode().equals("NBS269")) {// Patient State HIV Case Number
							String value = entry.getObservation()
									.getValueArray(0).newCursor()
									.getTextValue();
							person.getThePersonDT().setEharsId(value);
						} else if (entry.getObservation().getCode()
								.getCode().equals("NBS272")) {// Patient Sex Unknown Reason
							String value = ((CE) entry.getObservation()
									.getValueArray(0)).getCode();
							value = XMLTypeToNBSObject.getNBSCodeFromPHINCodes(
									"SEX_UNK_REASON", value,
									NEDSSConstants.CODE_VALUE_GENERAL);
							person.getThePersonDT().setSexUnkReasonCd(value);
						} else if (entry.getObservation().getCode()
								.getCode().equals("NBS273")) {// Patient Ethnicity Unknown Reason
							String value = ((CE) entry.getObservation()
									.getValueArray(0)).getCode();
							value = XMLTypeToNBSObject.getNBSCodeFromPHINCodes(
									"P_ETHN_UNK_REASON", value,
									NEDSSConstants.CODE_VALUE_GENERAL);
							person.getThePersonDT().setEthnicUnkReasonCd(value);
						} else if (entry.getObservation().getCode()
								.getCode().equals("NBS274")) {// Patient Other (Additional) Gender
							String value = ((CE) entry.getObservation()
									.getValueArray(0)).getCode();
							value = XMLTypeToNBSObject.getNBSCodeFromPHINCodes(
									"NBS_STD_GENDER_PARPT", value,
									NEDSSConstants.CODE_VALUE_GENERAL);
							person.getThePersonDT().setPreferredGenderCd(value);
						/*Fix for Defect #13328 PHDC Import: Prevent Writing Reported Age to MPR
						}else if (entry.getObservation().getCode()
								.getCode().equals("INV2001")) {// Patient Age Reported
							String value = entry.getObservation()
									.getValueArray(0).newCursor()
									.getTextValue();
							person.getThePersonDT().setAgeReported(value);
						}else if (entry.getObservation().getCode()
								.getCode().equals("INV2002")) {// Patient Age Reported Units AGE_UNIT
							String value = ((CE) entry.getObservation()
									.getValueArray(0)).getCode();
							value = XMLTypeToNBSObject.getNBSCodeFromPHINCodes(
									"AGE_UNIT", value,
									NEDSSConstants.CODE_VALUE_GENERAL);
							person.getThePersonDT().setAgeReportedUnitCd(value);*/
						}
					}
				}
			}

		} catch (Exception ex) {
			String errString = " CDAEntityProcessor.parseParientInfoFromSocialHistorySection Exception while parsing social history Info: "
					+ ex.getMessage();
			logger.error(errString);
			ex.printStackTrace();
			throw new NEDSSAppException(errString, ex);
		}
	}
	public static void parseStandardPlaceInfo(
			POCDMT000040ParticipantRole participantRole, PlaceVO placeVO,
			EdxCDAInformationDT informationDT) throws NEDSSAppException {
		PlaceDT placeDT = placeVO.getThePlaceDT();
		try {
			Timestamp ts = informationDT.getAddTime();
			placeDT.setPlaceUid(informationDT.getEntityUid());
			placeVO.setItDirty(false);
			placeVO.setItNew(true);
			placeVO.setItDirty(false);
			placeVO.setItNew(true);
			// PlaceDT
			placeDT.setVersionCtrlNbr(new Integer(1));
			placeDT.setItNew(true);
			placeDT.setLastChgTime(ts);
			placeDT.setAddTime(ts);
			placeDT.setLastChgUserId(informationDT.getUserId());
			placeDT.setAddUserId(informationDT.getUserId());
			placeDT.setStatusCd(NEDSSConstants.A);
			placeDT.setRecordStatusCd(NEDSSConstants.ACTIVE);
			placeDT.setRecordStatusTime(informationDT.getAddTime());
			placeDT.setStatusTime(informationDT.getAddTime());
			POCDMT000040PlayingEntity playingEntity = participantRole.getPlayingEntity();
			PN[] name = null;
			if (playingEntity != null) {
				name = participantRole.getPlayingEntity().getNameArray();
				if (playingEntity.getDesc() != null)
					placeDT.setDescription(playingEntity.getDesc().newCursor()
							.getTextValue());
			}
			if(name!=null && name.length>0)
				placeDT.setNm(name[0].newCursor().getTextValue());
			if(participantRole.getCode()!=null)
				placeDT.setCd(participantRole.getCode().getCode());
		} catch (Exception ex) {
			String errString = " CDAEntityProcessor.parseStandardPlaceInfo Exception while parsing core place Info: "
					+ ex.getMessage();
			logger.error(errString);
			ex.printStackTrace();
			throw new NEDSSAppException(errString, ex);
		}
	}
	public static void parseStandardOrganizationInfo(
			POCDMT000040ParticipantRole participantRole, OrganizationVO orgVO,
			EdxCDAInformationDT informationDT) throws NEDSSAppException {
		OrganizationDT orgDT = orgVO.getTheOrganizationDT();
		try {
			orgDT.setElectronicInd(NEDSSConstants.ELECTRONIC_IND);
			orgDT.setEdxInd(NEDSSConstants.EDX_TYPE_ENTITY);
			Timestamp ts = informationDT.getAddTime();
			orgDT.setOrganizationUid(informationDT.getEntityUid());
			orgVO.setItDirty(false);
			orgVO.setItNew(true);
			orgVO.setItDirty(false);
			orgVO.setItNew(true);
			// OrganizationDT
			orgDT.setVersionCtrlNbr(new Integer(1));
			orgDT.setItNew(true);
			orgDT.setLastChgTime(ts);
			orgDT.setAddTime(ts);
			orgDT.setLastChgUserId(informationDT.getUserId());
			orgDT.setAddUserId(informationDT.getUserId());
			orgDT.setStatusCd(NEDSSConstants.A);
			orgDT.setRecordStatusCd(NEDSSConstants.ACTIVE);
			orgDT.setRecordStatusTime(informationDT.getAddTime());
			orgDT.setStatusTime(informationDT.getAddTime());
		} catch (Exception ex) {
			String errString = " CDAEntityProcessor.parseStandardOrganizationInfo Exception while parsing core Organization Info: "
					+ ex.getMessage();
			logger.error(errString);
			ex.printStackTrace();
			throw new NEDSSAppException(errString, ex);
		}
	}

	public static void parseStandardProviderInfo(
			POCDMT000040ParticipantRole participantRole, PersonVO personVO,
			EdxCDAInformationDT informationDT) throws NEDSSAppException {
		PersonDT personDT = personVO.getThePersonDT();
		try {
			personDT.setElectronicInd(NEDSSConstants.ELECTRONIC_IND);
			personDT.setEdxInd(NEDSSConstants.EDX_TYPE_ENTITY);
			Timestamp ts = informationDT.getAddTime();
			personDT.setCd(NEDSSConstants.PRV);
			personDT.setPersonUid(informationDT.getEntityUid());
			personVO.setItDirty(false);
			personDT.setItNew(true);
			personDT.setItDirty(false);
			personVO.setItNew(true);
			// PersonDT
			personDT.setVersionCtrlNbr(new Integer(1));
			personDT.setItNew(true);
			personDT.setLastChgTime(ts);
			personDT.setAddTime(ts);
			personDT.setLastChgUserId(informationDT.getUserId());
			personDT.setAddUserId(informationDT.getUserId());
			personDT.setStatusCd(NEDSSConstants.A);
			personDT.setRecordStatusCd(NEDSSConstants.ACTIVE);
			personDT.setRecordStatusTime(ts);
			personDT.setStatusTime(ts);
		} catch (Exception ex) {
			String errString = "parseStandardProviderInfo Exception while parsing core provider Info: "
					+ ex.getMessage();
			logger.error(errString);
			ex.printStackTrace();
			throw new NEDSSAppException(errString, ex);
		}
	}

	@SuppressWarnings("unchecked")
	public static Collection<Object> parseAddresses(AD[] addresses,
			EdxCDAInformationDT informationDT) throws NEDSSAppException {
		if (addresses == null || addresses.length == 0) {
			logger.debug("No Address information available for entity");
			return new ArrayList<Object>();
		}
		String processedAddress = null;
		Collection<Object> elpCollection = new ArrayList<Object>();
		try {
			Hl7ToNBSObjectConverter hl7ToNBSObjectConverter = new Hl7ToNBSObjectConverter();
			for (AD address : addresses) {
				EntityLocatorParticipationDT elp = new EntityLocatorParticipationDT();
				elp.setItNew(true);
				elp.setItDirty(false);
				Timestamp asOfDate = null;
				if(address.getUseablePeriodArray()!=null && address.getUseablePeriodArray().length>0)
					asOfDate = CDAXMLTypeToNBSObject
							.parseDateAsTimestamp(((IVLTS) address.getUseablePeriodArray(0)).getLow());
				if(asOfDate==null)
					elp.setAsOfDate(informationDT.getAddTime());
				else
					elp.setAsOfDate(asOfDate);
				elp.setAddTime(new Timestamp(new Date().getTime()));
				elp.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
				elp.setStatusTime(informationDT.getAddTime());
				elp.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
				elp.setRecordStatusTime(informationDT.getAddTime());
				String addressUse = null;
				List<Object> addressUseList = address.getUse();
				if (addressUseList != null && addressUseList.size() > 0)
					addressUse = (String) addressUseList.get(0);
				elp.setCd(EdxCDAConstants.HOUSE_CD);
				elp.setUseCd(addressUse);
				if(addressUse==null)
					elp.setUseCd(NEDSSConstants.HOME);
				if (informationDT.isProviderEntity()
						|| informationDT.isOrganizationEntity()
						|| informationDT.isPlaceEntity()){
					elp.setCd(NEDSSConstants.OFFICE_CD);
					elp.setUseCd(NEDSSConstants.WORK_PLACE);
				}
				if (informationDT.isPlaceEntity()){
					elp.setCd(NEDSSConstants.PLACE_CLASS_CODE);
				}
				elp.setClassCd(NEDSSConstants.POSTAL);
				PostalLocatorDT pl = new PostalLocatorDT();
				pl.setItNew(true);
				pl.setItDirty(false);
				pl.setAddTime(new Timestamp(new Date().getTime()));
				pl.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
				pl.setRecordStatusTime(informationDT.getAddTime());
				AdxpStreetAddressLine[] streetAddressLineArray = address
						.getStreetAddressLineArray();
				if (streetAddressLineArray != null
						&& streetAddressLineArray.length > 0)
					pl.setStreetAddr1(streetAddressLineArray[0].newCursor()
							.getTextValue());
				processedAddress = pl.getStreetAddr1();
				if(streetAddressLineArray != null
						&& streetAddressLineArray.length > 1){
					pl.setStreetAddr2(streetAddressLineArray[1].newCursor()
							.getTextValue());
				}
				AdxpCity[] cithArray = address.getCityArray();
				if (cithArray != null && cithArray.length > 0) {
					if(cithArray[0].newCursor().getTextValue().length()<20)
						pl.setCityCd(cithArray[0].newCursor().getTextValue());
					pl.setCityDescTxt(cithArray[0].newCursor().getTextValue());
				}
				AdxpCounty[] countyArray = address.getCountyArray();
				if (countyArray != null && countyArray.length > 0) {
					String countyCode = CDAXMLTypeToNBSObject.parseCodedString(countyArray[0].newCursor().getTextValue());
					if(countyCode!=null && countyCode.length()<20)
						pl.setCntyCd(countyCode);
				}
				AdxpState[] stateArray = address.getStateArray();
				if (stateArray != null && stateArray.length > 0) {
					pl.setStateCd(CDAXMLTypeToNBSObject.parseCodedString(stateArray[0].newCursor().getTextValue()));
				}
				AdxpCountry[] countryArray = address.getCountryArray();
				if (countryArray != null && countryArray.length > 0) {
					pl.setCntryCd(CDAXMLTypeToNBSObject
							.parseCodedString(countryArray[0].newCursor()
									.getTextValue()));
					pl.setCntryCd(XMLTypeToNBSObject.getNBSCodeFromPHINCodes(
							"PSL_CNTRY", pl.getCntryCd(),
							NEDSSConstants.CODE_VALUE_GENERAL) == null ? pl
							.getCntryCd() : XMLTypeToNBSObject
							.getNBSCodeFromPHINCodes("PSL_CNTRY",
									pl.getCntryCd(),
									NEDSSConstants.CODE_VALUE_GENERAL));
				}
				AdxpPostalCode[] postalCodeArray = address.getPostalCodeArray();
				if (postalCodeArray != null && postalCodeArray.length > 0) {
					pl.setZipCd(hl7ToNBSObjectConverter
							.formatZip(postalCodeArray[0].newCursor()
									.getTextValue()));
				}
				AdxpCensusTract[] censusTractArray = address
						.getCensusTractArray();
				if (censusTractArray != null && censusTractArray.length > 0) {
					pl.setCensusTract(censusTractArray[0].newCursor()
							.getTextValue());
				}
				AdxpAdditionalLocator[] adxpAdditionalLocatorArray = address
						.getAdditionalLocatorArray();
				if (adxpAdditionalLocatorArray != null
						&& adxpAdditionalLocatorArray.length > 0) {
					pl.setUserAffiliationTxt(adxpAdditionalLocatorArray[0]
							.newCursor().getTextValue());
					elp.setUserAffiliationTxt(adxpAdditionalLocatorArray[0]
							.newCursor().getTextValue());
				}
				elp.setThePostalLocatorDT(pl);
				elp.setEntityUid(informationDT.getEntityUid());
				elpCollection.add(elp);
			}
		} catch (Exception ex) {
			String errString = "CDAEntityProcessor.parseAddresses Exception while parsing Addresses: "
					+ processedAddress + " " + ex.getMessage();
			logger.error(errString);
			ex.printStackTrace();
			throw new NEDSSAppException(errString, ex);
		}
		return elpCollection;

	}
	
	public static Collection<Object> parseBirthAddress(AD address,
			EdxCDAInformationDT informationDT) throws NEDSSAppException {
		if (address == null) {
			logger.debug("No Birth Address information available for entity");
			return new ArrayList<Object>();
		}
		String processedAddress = null;
		Collection<Object> elpCollection = new ArrayList<Object>();
		try {
				Hl7ToNBSObjectConverter hl7ToNBSObjectConverter = new Hl7ToNBSObjectConverter();
				EntityLocatorParticipationDT elp = new EntityLocatorParticipationDT();
				elp.setItNew(true);
				elp.setItDirty(false);
				Timestamp asOfDate = null;
				if(address.getUseablePeriodArray()!=null && address.getUseablePeriodArray().length>0)
					asOfDate = CDAXMLTypeToNBSObject
							.parseDateAsTimestamp(((IVLTS) address.getUseablePeriodArray(0)).getLow());
				if(asOfDate==null)
					elp.setAsOfDate(informationDT.getAddTime());
				else
					elp.setAsOfDate(asOfDate);
				elp.setAddTime(new Timestamp(new Date().getTime()));
				elp.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
				elp.setStatusTime(informationDT.getAddTime());
				elp.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
				elp.setRecordStatusTime(informationDT.getAddTime());
				elp.setUseCd(NEDSSConstants.BIRTH);
				elp.setCd(NEDSSConstants.BIRTHCD);
				elp.setClassCd(NEDSSConstants.POSTAL);
				PostalLocatorDT pl = new PostalLocatorDT();
				pl.setItNew(true);
				pl.setItDirty(false);
				pl.setAddTime(new Timestamp(new Date().getTime()));
				pl.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
				pl.setRecordStatusTime(informationDT.getAddTime());
				AdxpStreetAddressLine[] streetAddressLineArray = address
						.getStreetAddressLineArray();
				if (streetAddressLineArray != null
						&& streetAddressLineArray.length > 0)
					pl.setStreetAddr1(streetAddressLineArray[0].newCursor()
							.getTextValue());
				processedAddress = pl.getStreetAddr1();
				if(streetAddressLineArray != null
						&& streetAddressLineArray.length > 1){
					pl.setStreetAddr2(streetAddressLineArray[1].newCursor()
							.getTextValue());
				}
				AdxpCity[] cithArray = address.getCityArray();
				if (cithArray != null && cithArray.length > 0) {
					if(cithArray[0].newCursor().getTextValue().length()<20)
						pl.setCityCd(cithArray[0].newCursor().getTextValue());
					pl.setCityDescTxt(cithArray[0].newCursor().getTextValue());
				}
				AdxpCounty[] countyArray = address.getCountyArray();
				if (countyArray != null && countyArray.length > 0) {
					String countyCode = CDAXMLTypeToNBSObject.parseCodedString(countyArray[0].newCursor().getTextValue());
					if(countyCode!=null && countyCode.length()<20)
						pl.setCntyCd(countyCode);
				}
				AdxpState[] stateArray = address.getStateArray();
				if (stateArray != null && stateArray.length > 0) {
					pl.setStateCd(CDAXMLTypeToNBSObject.parseCodedString(stateArray[0].newCursor().getTextValue()));
				}
				AdxpCountry[] countryArray = address.getCountryArray();
				if (countryArray != null && countryArray.length > 0) {
					pl.setCntryCd(CDAXMLTypeToNBSObject
							.parseCodedString(countryArray[0].newCursor()
									.getTextValue()));
					pl.setCntryCd(XMLTypeToNBSObject.getNBSCodeFromPHINCodes(
							"PSL_CNTRY", pl.getCntryCd(),
							NEDSSConstants.CODE_VALUE_GENERAL) == null ? pl
							.getCntryCd() : XMLTypeToNBSObject
							.getNBSCodeFromPHINCodes("PSL_CNTRY",
									pl.getCntryCd(),
									NEDSSConstants.CODE_VALUE_GENERAL));
				}
				AdxpPostalCode[] postalCodeArray = address.getPostalCodeArray();
				if (postalCodeArray != null && postalCodeArray.length > 0) {
					pl.setZipCd(hl7ToNBSObjectConverter
							.formatZip(postalCodeArray[0].newCursor()
									.getTextValue()));
				}
				AdxpCensusTract[] censusTractArray = address
						.getCensusTractArray();
				if (censusTractArray != null && censusTractArray.length > 0) {
					pl.setCensusTract(censusTractArray[0].newCursor()
							.getTextValue());
				}
				elp.setThePostalLocatorDT(pl);
				elp.setEntityUid(informationDT.getEntityUid());
				elpCollection.add(elp);
		} catch (Exception ex) {
			String errString = "CDAEntityProcessor.parseAddresses Exception while parsing Addresses: "
					+ processedAddress + " " + ex.getMessage();
			logger.error(errString);
			ex.printStackTrace();
			throw new NEDSSAppException(errString, ex);
		}
		return elpCollection;

	}


	public static Collection<Object> parsePersonNames(PN[] names,
			EdxCDAInformationDT informationDT) throws NEDSSAppException {

		if (names == null || names.length == 0)
			return new ArrayList<Object>();
		String processedName = null;
		Collection<Object> nameCollection = new ArrayList<Object>();
		try {
			for (PN name : names) {
				PersonNameDT personNameDT = new PersonNameDT();
				if (name.getUse() != null && name.getUse().size()> 0){
					String nameType =name.getUse().get(0).toString();
					if(nameType.equals("P")){
					personNameDT.setNmUseCd(NEDSSConstants.ALIAS_NAME);
					}else if(nameType.equals("L")){
						personNameDT.setNmUseCd(NEDSSConstants.LEGAL_NAME);
					}
				}
				//if (informationDT.isProviderEntity())
				//	personNameDT.setNmUseCd(NEDSSConstants.LEGAL);
				if (name.getPrefixArray() != null
						&& name.getPrefixArray().length > 0)
					personNameDT.setNmPrefix(name.getPrefixArray(0).newCursor()
							.getTextValue());
				if (name.getFamilyArray() != null
						&& name.getFamilyArray().length > 0)
					personNameDT.setLastNm(name.getFamilyArray(0).newCursor()
							.getTextValue());
				processedName = personNameDT.getLastNm();
				if (name.getGivenArray() != null
						&& name.getGivenArray().length > 0)
					personNameDT.setFirstNm(name.getGivenArray(0).newCursor()
							.getTextValue());
				if (name.getGivenArray() != null
						&& name.getGivenArray().length > 1)
					personNameDT.setMiddleNm(name.getGivenArray(1).newCursor()
							.getTextValue());
				processedName = processedName + ", "
						+ personNameDT.getFirstNm();
				if (name.getSuffixArray() != null
						&& name.getSuffixArray().length > 0) {
					personNameDT.setNmSuffixCd(name.getSuffixArray(0)
							.newCursor().getTextValue());
					personNameDT.setNmSuffix(name.getSuffixArray(0).newCursor()
							.getTextValue());
				}
				
				if(name.getValidTime()!=null && name.getValidTime().getLow()!=null && name.getValidTime().getLow().getValue()!=null){
					Timestamp asOfDate = CDAXMLTypeToNBSObject
							.parseDateAsTimestamp(name.getValidTime().getLow());
					personNameDT.setAsOfDate(asOfDate);
					informationDT.setAsOfDate(asOfDate);
				}
				personNameDT.setAddTime(informationDT.getAddTime());
				personNameDT.setAddReasonCd(EdxELRConstants.ADD_REASON_CD);
				personNameDT.setRecordStatusCd(NEDSSConstants.ACTIVE);
				personNameDT.setRecordStatusTime(informationDT.getAddTime());
				personNameDT.setStatusCd(NEDSSConstants.A);
				personNameDT.setStatusTime(informationDT.getAddTime());
				if(personNameDT.getAsOfDate()==null)
					personNameDT.setAsOfDate(informationDT.getAddTime());
				personNameDT.setAddUserId(informationDT.getUserId());
				personNameDT.setItNew(true);
				personNameDT.setItDirty(false);
				personNameDT.setLastChgTime(informationDT.getAddTime());
				personNameDT.setLastChgUserId(informationDT.getUserId());
				personNameDT.setPersonUid(informationDT.getEntityUid());
				int seq = 0;
				if (nameCollection != null)
					seq = nameCollection.size();
				personNameDT.setPersonNameSeq(seq + 1);
				nameCollection.add(personNameDT);
			}
		} catch (Exception ex) {
			String errString = "CDAEntityProcessor.parsePersonNames Exception while parsing Names: "
					+ processedName + " " + ex.getMessage();
			logger.error(errString);
			ex.printStackTrace();
			throw new NEDSSAppException(errString, ex);
		}
		return nameCollection;
	}

	public static Collection<Object> parseOrganizationNames(PN[] names,
			EdxCDAInformationDT informationDT) throws NEDSSAppException {

		if (names == null || names.length == 0)
			return new ArrayList<Object>();
		String processedName = null;
		Collection<Object> nameCollection = new ArrayList<Object>();
		try {
			PN name = names[0];
			OrganizationNameDT orgNameDT = new OrganizationNameDT();
			orgNameDT.setNmUseCd(NEDSSConstants.LEGAL);
			orgNameDT.setNmTxt(name.newCursor().getTextValue());
			processedName = orgNameDT.getNmTxt();
			orgNameDT.setAddTime(informationDT.getAddTime());
			orgNameDT.setRecordStatusCd(NEDSSConstants.ACTIVE);
			orgNameDT.setRecordStatusTime(informationDT.getAddTime());
			orgNameDT.setStatusCd(NEDSSConstants.A);
			orgNameDT.setStatusTime(informationDT.getAddTime());
			orgNameDT.setAddUserId(informationDT.getUserId());
			orgNameDT.setItNew(true);
			orgNameDT.setItDirty(false);
			orgNameDT.setLastChgTime(informationDT.getAddTime());
			orgNameDT.setLastChgUserId(informationDT.getUserId());
			orgNameDT.setOrganizationUid(informationDT.getEntityUid());
			orgNameDT.setOrganizationNameSeq(1);
			nameCollection.add(orgNameDT);
		} catch (Exception ex) {
			String errString = "CDAEntityProcessor.parseOrganizationNames Exception while parsing Names: "
					+ processedName + " " + ex.getMessage();
			logger.error(errString);
			ex.printStackTrace();
			throw new NEDSSAppException(errString, ex);
		}
		return nameCollection;
	}

	public static Collection<Object> parseTelephones(TEL[] telephones,
			EdxCDAInformationDT informationDT) throws NEDSSAppException {
		if (telephones == null || telephones.length == 0)
			return new ArrayList<Object>();
		String processedTele = null;
		Collection<Object> elpCollection = new ArrayList<Object>();
		try {
			for (TEL telephone : telephones) {
				if(telephone.getValue()!=null){
				EntityLocatorParticipationDT elp = new EntityLocatorParticipationDT();
				TeleLocatorDT teleDT = new TeleLocatorDT();
				Timestamp asOfDate = null;
				if(telephone.getUseablePeriodArray()!=null && telephone.getUseablePeriodArray().length>0)
					asOfDate = CDAXMLTypeToNBSObject
							.parseDateAsTimestamp(((IVLTS) telephone.getUseablePeriodArray(0)).getLow());
				if(asOfDate==null)
					elp.setAsOfDate(informationDT.getAddTime());
				else
					elp.setAsOfDate(asOfDate);
				elp.setItNew(true);
				elp.setItDirty(false);
				elp.setAddTime(informationDT.getAddTime());
				elp.setCd(NEDSSConstants.PHONE);
				elp.setClassCd(NEDSSConstants.TELE);
				if (telephone.getUse() != null && telephone.getUse().size() > 0){
					String useCd = telephone.getUse().get(0).toString();
					if(useCd.equals("HP"))
						elp.setUseCd(NEDSSConstants.HOME);
					else if(useCd.equals(NEDSSConstants.MOBILE)){
						elp.setCd(NEDSSConstants.CELL);
						elp.setUseCd(useCd);
					}
					else
						elp.setUseCd(useCd);
				}
				else if (informationDT.isOrganizationEntity()
						|| informationDT.isProviderEntity()
						|| informationDT.isPlaceEntity())
					elp.setUseCd(NEDSSConstants.WORK_PLACE);
				else
					elp.setUseCd(NEDSSConstants.HOME);
				elp.setStatusTime(informationDT.getAddTime());
				elp.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
				elp.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
				elp.setRecordStatusTime(informationDT.getAddTime());
				teleDT.setItNew(true);
				teleDT.setItDirty(false);
				teleDT.setAddTime(informationDT.getAddTime());
				teleDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
				teleDT.setRecordStatusTime(informationDT.getAddTime());
				elp.setTheTeleLocatorDT(teleDT);
				processedTele = telephone.getValue();
				parseTelephone(processedTele, teleDT);
				if(processedTele!=null && (processedTele.indexOf("@")>-1 || processedTele.toLowerCase().indexOf(".")>-1)){
					if (informationDT.isOrganizationEntity()
							|| informationDT.isProviderEntity()){
					elp.setUseCd(NEDSSConstants.WORK_PLACE);
					elp.setCd(NEDSSConstants.PHONE);
					}
					else{
						elp.setUseCd(NEDSSConstants.HOME);
						elp.setCd(NEDSSConstants.NET);
					}
				}
				elpCollection.add(elp);
				
			}
			}
		} catch (Exception ex) {
			String errString = "CDAEntityProcessor.parseTelephones Exception while parsing telephone information: "
					+ processedTele + " " + ex.getMessage();
			logger.error(errString);
			ex.printStackTrace();
			throw new NEDSSAppException(errString, ex);
		}
		return elpCollection;
	}

	public static Map<Object, Object> parseEntityIds(II[] ids,
			EdxCDAInformationDT informationDT) throws NEDSSAppException {
		if (ids == null || ids.length == 0)
			return null;
		Map<Object, Object> returnMap = new HashMap<Object, Object>();
		Collection<Object> idCollection = new ArrayList<Object>();
		String processedId = null;
		try {
			for (II id : ids) {
				if (id.getAssigningAuthorityName() != null
						&& id.getAssigningAuthorityName().equals(
								NEDSSConstants.PHCR_LOCAL_REGISTRY_ID)) {
					StringBuffer buffer = new StringBuffer();
					buffer.append(id.getAssigningAuthorityName());// LR
					buffer.append("^").append(id.getExtension());
					buffer.append("^").append(id.getRoot());
					buffer.append("^").append(EdxCDAConstants.LOCAL_DESC);
					buffer.append("^").append(EdxCDAConstants.LOCAL_CD);
					// TODO:add assigning facility
					returnMap.put(NEDSSConstants.PHCR_LOCAL_REGISTRY_ID,
							buffer.toString());
					informationDT.setSourcePatientId(id.getExtension());
				} else if (id.getExtension() != null) {
					int size = 0;
					if (idCollection != null)
						size = idCollection.size();
					EntityIdDT entityIdDT = new EntityIdDT();
					entityIdDT.setEntityUid(informationDT.getEntityUid());
					entityIdDT.setAddTime(informationDT.getAddTime());
					entityIdDT.setEntityIdSeq(size + 1);
					entityIdDT.setRootExtensionTxt(id.getExtension());
					processedId = id.getExtension();
					// TODO: update the assigning authority and type with right
					// values through lookup tables
					entityIdDT.setAssigningAuthorityIdType(id.getRoot());
					entityIdDT.setAssigningAuthorityCd(EdxCDAConstants.ANY);
					entityIdDT
							.setAssigningAuthorityDescTxt(EdxCDAConstants.ANY);
					if(id.getAssigningAuthorityName()!=null && id.getAssigningAuthorityName().contains(NEDSSConstants.ENTITY_TYPECD_QEC))
						entityIdDT.setTypeCd(NEDSSConstants.ENTITY_TYPECD_QEC);
					else if(id.getAssigningAuthorityName()!=null)
						entityIdDT.setTypeCd(id.getAssigningAuthorityName());
					else
					entityIdDT.setTypeCd(EdxCDAConstants.ANY);
					// TODO:add assigning facility
					entityIdDT.setAddUserId(informationDT.getUserId());
					entityIdDT.setLastChgUserId(informationDT.getUserId());
					entityIdDT.setStatusCd(NEDSSConstants.A);
					entityIdDT.setStatusTime(informationDT.getAddTime());
					entityIdDT.setRecordStatusTime(informationDT.getAddTime());
					entityIdDT.setRecordStatusCd(NEDSSConstants.ACTIVE);
					entityIdDT.setAsOfDate(informationDT.getAddTime());
					entityIdDT.setItNew(true);
					entityIdDT.setItDirty(false);
					idCollection.add(entityIdDT);
				}
				returnMap.put(NEDSSConstants.NON_LR_ENTITY_IDS, idCollection);
			}
		} catch (Exception ex) {
			String errString = "CDAEntityProcessor.parseEntityIds Exception while parsing entity Ids: "
					+ processedId + " " + ex.getMessage();
			logger.error(errString);
			ex.printStackTrace();
			throw new NEDSSAppException(errString, ex);
		}
		return returnMap;
	}

	public static HashMap<Object, Object> createActEntityandParticipations(Object proxyVO,
			HashMap<Object, Object> participantMap,
			HashMap<Object, Object> participantRoleMap,
			EdxCDAInformationDT informationDT, String objectType,
			NBSSecurityObj nbsSecurityObj) throws NEDSSAppException {
		HashMap<Object, Object> legacyToNBSEntityUidMap = new HashMap<Object, Object>();
		try {
			Set<Object> participants = participantMap.keySet();
			EdxMatchingCriteriaUtil util = new EdxMatchingCriteriaUtil();
			EDXActivityDetailLogDT eDXActivityDetailLogDT = null;
			for (Object participant : participants) {
				String participantId = (String) participantMap.get(participant);
				POCDMT000040ParticipantRole participantRole = (POCDMT000040ParticipantRole) participantRoleMap
						.get(participantId);
				if(participantRole==null){
					String errorTxt = "Participant role does not exist for legacy entity id: "+participantId;
					throw new NEDSSAppException(errorTxt);
				}
				if(participant!=null && participant.toString().indexOf("^")>0)
					participant = participant.toString().substring(0, participant.toString().indexOf("^"));
				ParticipationTypeVO participationType = getParticipationTypeByQidAndEventType(
						(String) participant, objectType);
				if (participationType.getSubjectClassCd().equals(
						NEDSSConstants.PERSON)) {
					informationDT.setProviderEntity(-1);
					PersonVO personVO = getProviderAssignedEntity(
							participantRole, informationDT);
					if(personVO!=null)
					eDXActivityDetailLogDT = util.getMatchingProvider(personVO,
							nbsSecurityObj);
				
					// Participations are also created in the
					// setActEntityForCreate
					// method
					if (proxyVO instanceof PageActProxyVO && personVO!=null)
						EdxPersonEntityProcessor.setActEntityForCreate(
								(PageActProxyVO) proxyVO, null,
								participationType.getTypeCd(), new Long(
										eDXActivityDetailLogDT.getRecordId()),
								NEDSSConstants.PERSON);
					else if(proxyVO instanceof CTContactProxyVO && personVO!=null)
						EdxPersonEntityProcessor.setActEntityForContactRecord((CTContactProxyVO) proxyVO,
								participationType.getTypeCd(), new Long(eDXActivityDetailLogDT.getRecordId()));
						
				} else if (participationType.getSubjectClassCd().equals(
						NEDSSConstants.ORGANIZATION)) {
					informationDT.setOrganizationEntity(-1);
					OrganizationVO orgVO = getOrganizationAssignedEntity(
							participantRole, informationDT);
					if(orgVO!=null){
					eDXActivityDetailLogDT = new EDXActivityDetailLogDT();
					eDXActivityDetailLogDT = util.getMatchingOrganization(
							orgVO, nbsSecurityObj);
					}
					if (proxyVO instanceof PageActProxyVO && orgVO!=null)
					EdxPersonEntityProcessor.setActEntityForCreate(
							(PageActProxyVO) proxyVO, null,
							participationType.getTypeCd(), new Long(
									eDXActivityDetailLogDT.getRecordId()),
							NEDSSConstants.ORGANIZATION);

					else if(proxyVO instanceof CTContactProxyVO && orgVO!=null)
						EdxPersonEntityProcessor.setActEntityForContactRecord((CTContactProxyVO) proxyVO,
								participationType.getTypeCd(), new Long(eDXActivityDetailLogDT.getRecordId()));
				}
				else if (participationType.getSubjectClassCd().equals(
						NEDSSConstants.PLACE)) {
					//only go through the process again if participant is not in the map
					if(legacyToNBSEntityUidMap.get(participantId)==null)
					informationDT.setPlaceEntity(-1);
					PlaceVO placeVO = getPlaceAssignedEntity(
							participantRole, informationDT);
					if(placeVO!=null){
					eDXActivityDetailLogDT = new EDXActivityDetailLogDT();
					eDXActivityDetailLogDT = util.getMatchingPlace(placeVO, nbsSecurityObj);
					EntityController entityController = util.getEntityController();
					PlaceVO newPlaceVO = entityController.getPlace(new Long(eDXActivityDetailLogDT.getRecordId()), nbsSecurityObj);
					// To append postal and tele  locator Uids
					if(newPlaceVO.getTheEntityLocatorParticipationDTCollection()!=null && newPlaceVO.getTheEntityLocatorParticipationDTCollection().size()>0){
						Long postalLocatorUid = new Long(-1);
						Long teleLocatorUid = new Long(-1);
						for(Object entityLocator : newPlaceVO.getTheEntityLocatorParticipationDTCollection()){
							PostalLocatorDT plDT= ((EntityLocatorParticipationDT)entityLocator).getThePostalLocatorDT();
							TeleLocatorDT teleDT= ((EntityLocatorParticipationDT)entityLocator).getTheTeleLocatorDT();
							if(plDT!=null)
								postalLocatorUid = plDT.getPostalLocatorUid();
							if(teleDT!=null)
								teleLocatorUid  = teleDT.getTeleLocatorUid();
						}
						eDXActivityDetailLogDT.setRecordId(eDXActivityDetailLogDT.getRecordId()+"^"+postalLocatorUid.longValue()+"^"+teleLocatorUid.longValue()+"");
					}
					else
						eDXActivityDetailLogDT.setRecordId(eDXActivityDetailLogDT.getRecordId()+"^^");
					}
//					if (proxyVO instanceof PageActProxyVO && placeVO!=null)
//					EdxPersonEntityProcessor.setActEntityForCreate(
//							(PageActProxyVO) proxyVO, null,
//							participationType.getTypeCd(), new Long(
//									eDXActivityDetailLogDT.getRecordId()),
//							NEDSSConstants.PLACE);
//					else if(proxyVO instanceof CTContactProxyVO && placeVO!=null)
//						EdxPersonEntityProcessor.setActEntityForContactRecord((CTContactProxyVO) proxyVO,
//								participationType.getTypeCd(), new Long(eDXActivityDetailLogDT.getRecordId()));
				}
				if(legacyToNBSEntityUidMap.get(participantId)==null)
					legacyToNBSEntityUidMap.put(participantId, eDXActivityDetailLogDT.getRecordId());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new NEDSSAppException(
					"Exception while creating act entity relationship in CDAEntityProcessor.createActEntityandParticipations"
							+ System.getProperty("line.separator")
							+ "Message: " + ex.getMessage(), ex);
		}
		return legacyToNBSEntityUidMap;
	}

	public static ParticipationTypeVO getParticipationTypeByQidAndEventType(
			String questionId, String actType) throws NEDSSAppException {
		ArrayList<Object> participationTypes = CachedDropDowns
				.getParticipationTypes();
		try {
			for (Object participation : participationTypes) {
				if (questionId.equals(((ParticipationTypeVO) participation)
						.getQuestionIdentifier())
						&& actType.equals(((ParticipationTypeVO) participation)
								.getActClassCd()))
					return (ParticipationTypeVO) participation;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new NEDSSAppException(
					"Error while getting the participation Type object for questionId:  "
							+ questionId
							+ " and actType: "
							+ actType
							+ " Please make sure question identifer has participation type defined in nbs_srte.participation_type table.",
					e);
		}
		return null;
	}

	@SuppressWarnings("unused")
	public static Timestamp parseDateAsTimestamp(TS ts)
			throws NEDSSAppException {
		Date date = null;
		Timestamp  time = null;
		try {
			if (ts == null)
				return null;
			String stringDate = null;
			if (ts != null) {
				String inStringDate = ts.getValue();
				Calendar caldate = Calendar.getInstance();
				try {
					if (inStringDate != null) {
						DateFormat df = null;
						if (inStringDate.length() > 8)
							df = new SimpleDateFormat("yyyyMMddhhmmss");
						else
							df = new SimpleDateFormat("yyyyMMdd");
						date = df.parse(inStringDate);
					}
				} catch (Exception e) {
					String errString = "CdaMessageParser.parseDate:  Invalid date:  '" + stringDate + "':  " + e.getMessage();
					logger.error(errString);
					throw new NEDSSAppException(errString, e);
				}
			}
			if(date!=null)
				time= new Timestamp(date.getTime());
		} catch (Exception e) {
			String error="CDAEntityProcessor.parseDateAsTimestamp error thrown for input timestamp:="+ ts;
			logger.error(error);
			e.printStackTrace();
			throw new NEDSSAppException(error, e);
		}
		return time;
	}

	public static void parseTelephone(String telecomString,
			TeleLocatorDT telephone) throws NEDSSAppException {
		// Following will parse:
		// tel:(999)555-1212 -> 999-555-1212
		// tel:(999)555-1212;ext=9999 -> 999-555-1212 and 9999
		// tel:+1-999-555-1212 -> 999-555-1212
		// tel:+1-999-555-1212;ext=9999 -> 999-555-1212 and 9999
		// tel:+1(999)555-1212;ext=9999 -> 999-555-1212 and 9999
		// tel:+1-(999)555-1212;ext=9999 -> 999-555-1212 and 9999
		// 4044444444 -> 404-444-4444

		// Telephone Number parsing
		String parsetelephoneNumber;
		try {
			// tel:+1(999)555-1212;ext=9999 
			if (telecomString.indexOf("+") > -1
					&& telecomString.indexOf("-") > telecomString.indexOf("+") && telecomString.indexOf("(")>-1 && telecomString.indexOf("-") > telecomString.indexOf("(")){
				String countryCode = telecomString.substring(telecomString.indexOf("+")+1, telecomString
						.indexOf("("));
				telephone.setCntryCd(countryCode);
				parsetelephoneNumber = telecomString.substring(telecomString
						.indexOf("("));
			}
			// tel:+1-(999)555-1212; tel:+1-999-555-1212; tel:+1-(999)555-1212;ext=9999;
			else if (telecomString.indexOf("+") > -1
					&& telecomString.indexOf("-") > telecomString.indexOf("+")){
				String countryCode = telecomString.substring(telecomString.indexOf("+")+1, telecomString
						.indexOf("-"));
				telephone.setCntryCd(countryCode);
				parsetelephoneNumber = telecomString.substring(telecomString
						.indexOf("-") + 1);
			}
			else
				parsetelephoneNumber = telecomString.substring(telecomString
						.indexOf(":") + 1);

			// If we have a ';' it is at end of telephone number and before
			// extension, so drop thereafter
			if (parsetelephoneNumber.indexOf(";") > 0)
				parsetelephoneNumber = parsetelephoneNumber.substring(0,
						parsetelephoneNumber.indexOf(";"));

			// Replace (areacode) with areacode-
			if (parsetelephoneNumber.indexOf("(") != -1)
				parsetelephoneNumber = parsetelephoneNumber
						.substring(parsetelephoneNumber.indexOf("(") + 1);

			if (parsetelephoneNumber.indexOf(")") > 0)
				parsetelephoneNumber = parsetelephoneNumber.substring(0,
						parsetelephoneNumber.indexOf(")"))
						+ "-"
						+ parsetelephoneNumber.substring(parsetelephoneNumber
								.indexOf(")") + 1);
			if (parsetelephoneNumber.indexOf("-") < 0
					&& parsetelephoneNumber.length() == 10 
					&& parsetelephoneNumber.indexOf("-") < 0)
					parsetelephoneNumber = parsetelephoneNumber.substring(0, 3)
							+ "-" + parsetelephoneNumber.substring(3, 6) + "-"
							+ parsetelephoneNumber.substring(6);
 
			if(parsetelephoneNumber!=null && parsetelephoneNumber.indexOf("@")>-1){
				if((telecomString.startsWith("mailto:")))
					telecomString = telecomString.substring(7);
				telephone.setEmailAddress(telecomString);
			}
			else if(parsetelephoneNumber!=null && (parsetelephoneNumber.toLowerCase().indexOf("www.")>-1 || parsetelephoneNumber.toLowerCase().indexOf(".")>-1))
				telephone.setUrlAddress(telecomString);
			else
					telephone.setPhoneNbrTxt(parsetelephoneNumber);

			// Telephone Extension parsing
			if (telecomString.indexOf("ext=") > 0) {
				telephone.setExtensionTxt(telecomString.substring(telecomString
						.indexOf("ext=") + 4));
			}
			// Telephone Extension parsing
			if (telecomString.indexOf("extn=") > 0) {
				telephone.setExtensionTxt(telecomString.substring(telecomString
						.indexOf("extn=") + 5));
			}

		} catch (Exception ex) {
			String errString = "CDAEntityProcessor.parseTelephone Invalid telecom infermation :  '" + telecomString + "':  " + ex.getMessage();
			logger.error(errString);
			ex.printStackTrace();
			throw new NEDSSAppException(errString, ex);
		}
	}


	public static void populateEntitiesFromEntries(POCDMT000040Entry[] entries,
			HashMap<Object, Object> entitiesMap) throws NEDSSAppException {
		try {
			for (POCDMT000040Entry entry : entries) {
				if (entry.getAct() != null) {
				    String code =  entry.getAct().getCode()!=null?entry.getAct().getCode().getCode():"";
					POCDMT000040Participant2[] participantArray = entry.getAct()
							.getParticipantArray();
					if (participantArray != null) {
						for (POCDMT000040Participant2 participant : participantArray) {
							// create a Map with participant ID and participantRole
							II[] idArrays = participant.getParticipantRole().getIdArray();
							for (II id : idArrays) {
								if (id != null
										&& id.getAssigningAuthorityName() != null
										&& id.getAssigningAuthorityName()
												.equals(NEDSSConstants.PHCR_LOCAL_REGISTRY_ID))
									entitiesMap.put(id.getExtension()+code,
											participant.getParticipantRole());
							}
						}
					}
				}
			}
		} catch (Exception e) {
			String errorString="PopulateEntitiesFromEntries: Error while traferring values to bean and exception is raised: "+e.getMessage();
			e.printStackTrace();
			throw new NEDSSAppException(errorString, e);
		}
	}
/*TEST code commented out!
	public static void main(String args[]) throws NEDSSAppException{
	CDAEntityProcessor processor = new CDAEntityProcessor();
	TeleLocatorDT telephone = new TeleLocatorDT();
	processor.parseTelephone("tel:+1-677-555-2323", telephone);
	}
	*/

}
