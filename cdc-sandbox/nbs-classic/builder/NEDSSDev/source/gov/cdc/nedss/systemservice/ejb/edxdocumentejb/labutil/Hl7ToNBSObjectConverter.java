package gov.cdc.nedss.systemservice.ejb.edxdocumentejb.labutil;

import gov.cdc.nedss.association.dt.ParticipationDT;
import gov.cdc.nedss.entity.entityid.dt.EntityIdDT;
import gov.cdc.nedss.entity.organization.vo.OrganizationVO;
import gov.cdc.nedss.entity.person.dt.PersonEthnicGroupDT;
import gov.cdc.nedss.entity.person.dt.PersonNameDT;
import gov.cdc.nedss.entity.person.dt.PersonRaceDT;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.locator.dt.EntityLocatorParticipationDT;
import gov.cdc.nedss.locator.dt.PostalLocatorDT;
import gov.cdc.nedss.locator.dt.TeleLocatorDT;
import gov.cdc.nedss.phdc.HL7CNNType;
import gov.cdc.nedss.phdc.HL7CWEType;
import gov.cdc.nedss.phdc.HL7CXType;
import gov.cdc.nedss.phdc.HL7DTType;
import gov.cdc.nedss.phdc.HL7FNType;
import gov.cdc.nedss.phdc.HL7NMType;
import gov.cdc.nedss.phdc.HL7SADType;
import gov.cdc.nedss.phdc.HL7TSType;
import gov.cdc.nedss.phdc.HL7XADType;
import gov.cdc.nedss.phdc.HL7XCNType;
import gov.cdc.nedss.phdc.HL7XPNType;
import gov.cdc.nedss.phdc.HL7XTNType;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.LabResultProxyVO;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dao.ELRXRefDAOImpl;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dt.EdxLabInformationDT;
import gov.cdc.nedss.systemservice.ejb.srtmapejb.dao.SRTMapDAOImpl;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


/**
 *
 * @author Pradeep Kumar Sharma Utility class to convert the HL7 objects to NBS
 *         objects
 *
 */
public class Hl7ToNBSObjectConverter {
	static final LogUtils logger = new LogUtils(Hl7ToNBSObjectConverter.class.getName());

	public Timestamp processHL7TSType(HL7TSType time, String itemDescription) throws NEDSSAppException {
		String timeStr = "";
		try {
			Timestamp toTimestamp = null;
			java.util.Date date2=null;
			int year = -1;
			int month = -1;
			int day = -1;
			int hourOfDay = 0;
			int minute = 0;
			int second = 0;
			if (time != null) {
				if (time.getYear() != null)
					year = time.getYear().intValue();
				if (time.getMonth() != null)
					month = time.getMonth().intValue();
				if (time.getHours() != null)
					hourOfDay = time.getHours().intValue();
				if (time.getDay() != null)
					day = time.getDay().intValue();
				if (time.getMinutes() != null)
					minute = time.getMinutes().intValue();
				if (time.getSeconds() != null)
					second = time.getSeconds().intValue();


				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				timeStr = year+"-"+month+"-"+day+" "+hourOfDay+":"+minute+":"+second;
				logger.debug("  in processHL7TSType: Date string is: " +timeStr);
				date2 = sdf.parse(timeStr);
				toTimestamp = new java.sql.Timestamp(date2.getTime());
				if (isDateNotOkForDatabase(toTimestamp)) {
					throw new NEDSSAppException("Hl7ToNBSObjectConverter.processHL7TSType " +itemDescription +timeStr + EdxELRConstants.DATE_INVALID_FOR_DATABASE);
				}
			}
			

			return toTimestamp;
		} catch (ParseException e) {
			logger.error("Hl7ToNBSObjectConverter.processHL7TSType failed as the date format is not right. Please check.!"+ timeStr);
			throw new NEDSSAppException("Hl7ToNBSObjectConverter.processHL7TSType failed as the date format is not right. "+ itemDescription+timeStr);
		}
	}
	


	public Timestamp processHL7TSTypeWithMillis(HL7TSType time, String itemDescription) throws NEDSSAppException {
		String dateStr = "";
		try {
			Timestamp toTimestamp = null;
			
			int year = -1;
			int month = -1;
			int day = -1;
			int hourOfDay = 0;
			int minute = 0;
			int second = 0;
			int millis = 0;
			if (time != null) {
				if (time.getYear() != null)
					year = time.getYear().intValue();
				if (time.getMonth() != null)
					month = time.getMonth().intValue();
				if (time.getHours() != null)
					hourOfDay = time.getHours().intValue();
				if (time.getDay() != null)
					day = time.getDay().intValue();
				if (time.getMinutes() != null)
					minute = time.getMinutes().intValue();
				if (time.getSeconds() != null)
					second = time.getSeconds().intValue();
				if (time.getMillis() != null)
					millis = time.getMillis().intValue();

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				 java.util.Date date2=null;
				dateStr = year+"-"+month+"-"+day+" "+hourOfDay+":"+minute+":"+second+"."+millis;
				logger.debug("  in processHL7TSTypeWithMillis: Date string is: " +dateStr);
				date2 = sdf.parse(dateStr);
				toTimestamp = new java.sql.Timestamp(date2.getTime());
				if (isDateNotOkForDatabase(toTimestamp)) {
					throw new NEDSSAppException("Hl7ToNBSObjectConverter.processHL7TSTypeWithMillis " +itemDescription +date2.toString() + EdxELRConstants.DATE_INVALID_FOR_DATABASE);
				}
			}
			return toTimestamp;
		} catch (ParseException e) {
			logger.error("Hl7ToNBSObjectConverter.processHL7TSTypeWithMillis failed as the date format is not right. Please check.!"+ dateStr);
			throw new NEDSSAppException("Hl7ToNBSObjectConverter.processHL7TSTypeWithMillis failed as the date format is not right."+ itemDescription+dateStr);
		}
	}
	public Timestamp processHL7TSTypeForDOBWithoutTime(HL7TSType time) throws NEDSSAppException {
		Timestamp toTimestamp = null;
		String toTime = "";
	
		try {
			int year = -1;
			int month = -1;
			int date = -1;
			if (time != null) {
				if (time.getYear() != null)
					year = time.getYear().intValue();
				if (time.getMonth() != null)
					month = time.getMonth().intValue();
				if (time.getDay() != null)
					date = time.getDay().intValue();

				if (year >= 0 && month >= 0 && date >= 0) {
					toTime = month + "/" + date + "/" + year;
					logger.debug("  in processHL7TSTypeForDOBWithoutTime: Date string is: " +toTime);
					toTimestamp = this.stringToStrutsTimestamp(toTime); //if can't process returns null
				}
			}
		} catch (Exception e) {
			logger.error("Hl7ToNBSObjectConverter.processHL7TSTypeForDOBWithoutTime failed as the date format is not right. Please check.!"+ toTime);
			throw new NEDSSAppException("Hl7ToNBSObjectConverter.processHL7TSTypeForDOBWithoutTime failed as the date format is not right."+  
										EdxELRConstants.DATE_VALIDATION_PID_PATIENT_BIRTH_DATE_NO_TIME_MSG+toTime+"<--");
		}
		
		if (isDateNotOkForDatabase(toTimestamp)) {
			throw new NEDSSAppException("Hl7ToNBSObjectConverter.processHL7TSTypeForDOBWithoutTime " +EdxELRConstants.DATE_VALIDATION_PID_PATIENT_BIRTH_DATE_NO_TIME_MSG +toTime + EdxELRConstants.DATE_INVALID_FOR_DATABASE);
		}		
		return toTimestamp;
	}


	public Timestamp processHL7DTType(HL7DTType time, String itemDescription) throws NEDSSAppException {
		Timestamp toTimestamp = null;
		
		int year = -1;
		int month = -1;
		int date = -1;
		String toTime = "";
		if (time != null) {
			try {
				if (time.getYear() != null)
					year = time.getYear().intValue();
				if (time.getMonth() != null)
					month = time.getMonth().intValue();
				if (time.getDay() != null)
					date = time.getDay().intValue();

				if (year >= 0 && month >= 0 && date >= 0) {
					toTime = month + "/" + date + "/" + year;
					logger.debug("  in processHL7DTType: Date string is: " +toTime);
					toTimestamp = this.stringToStrutsTimestamp(toTime);
				}
			} catch (Exception e) {
				logger.error("Hl7ToNBSObjectConverter.processHL7DTType failed as the date format is not right. Please check.!"+toTime);
				throw new NEDSSAppException("Hl7ToNBSObjectConverter.processHL7DTType failed as the date format is not right." +itemDescription +toTime);
			}
			if (isDateNotOkForDatabase(toTimestamp)) {
				throw new NEDSSAppException("Hl7ToNBSObjectConverter.processHL7DTType " +itemDescription +toTime + EdxELRConstants.DATE_INVALID_FOR_DATABASE);
			}

		}
		
		return toTimestamp;
	}
	
	public java.sql.Timestamp stringToStrutsTimestamp(String strTime) {
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("MM/dd/yyyy");
	      String input = strTime;
	      java.util.Date t;
	      try {
	         if (input != null && input.trim().length() > 0) {
	            t = formatter.parse(input);
	            logger.debug(t);
	            java.sql.Timestamp ts = new java.sql.Timestamp(t.getTime());
	            return ts;
	         }
	         else {
	            return null;
	         }
	      }
	      catch (Exception e) {
	         logger.info("string could not be parsed into time");
	         return null;
	      }
	   }

	public PersonVO getProviderVO(HL7XCNType collector,
			LabResultProxyVO labResultProxyVO,
			EdxLabInformationDT edxLabInformationDT) throws NEDSSAppException {
		PersonVO personVO;
		try {
			edxLabInformationDT.setRole(EdxELRConstants.ELR_PROVIDER_CD);

			EntityIdDT entityIdDT = new EntityIdDT();
			entityIdDT.setEntityUid(new Long(edxLabInformationDT.getNextUid()));
			entityIdDT.setAddTime(edxLabInformationDT.getAddTime());
			if (entityIdDT.getTypeCd() != null
					&& entityIdDT.getTypeCd().equalsIgnoreCase(
							EdxELRConstants.TYPE_CD_SSN)
					&& entityIdDT.getAssigningAuthorityCd() != null
					&& entityIdDT.getAssigningAuthorityCd().equalsIgnoreCase(
							EdxELRConstants.ASSIGNING_AUTH_CD_SSN)) {

				entityIdDT.setRootExtensionTxt(formatSSN(collector
						.getHL7IDNumber()));
			} else {

				entityIdDT.setRootExtensionTxt(collector.getHL7IDNumber());
			}
			entityIdDT.setTypeCd(EdxELRConstants.ELR_PROVIDER_REG_NUM_CD);
			entityIdDT
					.setTypeDescTxt(EdxELRConstants.ELR_PROVIDER_REG_NUM_DESC);
			entityIdDT.setAssigningAuthorityCd(edxLabInformationDT
					.getSendingFacilityClia());
			entityIdDT.setAssigningAuthorityDescTxt(edxLabInformationDT
					.getSendingFacilityName());
			entityIdDT.setAssigningAuthorityIdType(edxLabInformationDT.getUniversalIdType());
			entityIdDT.setRecordStatusCd(EdxELRConstants.ELR_ACTIVE);
			entityIdDT.setAsOfDate(edxLabInformationDT.getAddTime());
			entityIdDT.setEntityIdSeq(new Integer(1));
			entityIdDT.setStatusCd(EdxELRConstants.ELR_ACTIVE_CD);

			entityIdDT.setItNew(true);
			entityIdDT.setItDirty(false);

			edxLabInformationDT.setRole(EdxELRConstants.ELR_PROVIDER_CD);
			HL7PatientProcessor hL7PatientProcessor = new HL7PatientProcessor();
			personVO = hL7PatientProcessor.personVO(labResultProxyVO,
					edxLabInformationDT);
			if(personVO.getTheEntityIdDTCollection()==null){
				personVO.setTheEntityIdDTCollection(new ArrayList<Object>());
			}
			personVO.getTheEntityIdDTCollection().add(entityIdDT);
			PersonNameDT personNameDT = new PersonNameDT();
			if(collector.getHL7FamilyName()!=null && collector.getHL7FamilyName().getHL7Surname()!=null)
				personNameDT.setLastNm(collector.getHL7FamilyName().getHL7Surname());
			personNameDT.setFirstNm(collector.getHL7GivenName());
			personNameDT.setMiddleNm(collector
					.getHL7SecondAndFurtherGivenNamesOrInitialsThereof());
			personNameDT.setNmPrefix(collector.getHL7Prefix());
			personNameDT.setNmSuffix(collector.getHL7Suffix());
			personNameDT.setNmDegree(collector.getHL7Degree());
			personNameDT.setPersonNameSeq(new Integer(1));
			personNameDT.setNmUseCd(collector.getHL7NameTypeCode());

			personNameDT.setAddTime(edxLabInformationDT.getAddTime());
			personNameDT.setLastChgTime(edxLabInformationDT.getAddTime());
			personNameDT.setAddUserId(edxLabInformationDT.getUserId());
			personNameDT.setLastChgUserId(edxLabInformationDT.getUserId());
			personVO.setThePersonNameDTCollection(new ArrayList<Object>());
			personVO.getThePersonNameDTCollection().add(personNameDT);
		} catch (NEDSSAppException e) {
			logger.error("Exception thrown by Hl7ToNBSObjectConverter.getProviderVO "
					+ e);
			throw new NEDSSAppException(
					"Exception thrown at Hl7ToNBSObjectConverter.getProviderVO:"
							+ e);
		}

		return personVO;
	}




	public PersonVO processCNNPersonName(HL7CNNType hl7CNNType,
			PersonVO personVO) {
		PersonNameDT personNameDT = new PersonNameDT();
		//String HL7IDNumber = hl7CNNType.getHL7IDNumber();
		String lastName = hl7CNNType.getHL7FamilyName();
		personNameDT.setLastNm(lastName);
		String firstName = hl7CNNType.getHL7GivenName();
		personNameDT.setFirstNm(firstName);
		String middleName = hl7CNNType
				.getHL7SecondAndFurtherGivenNamesOrInitialsThereof();
		personNameDT.setMiddleNm(middleName);
		String suffix = hl7CNNType.getHL7Suffix();
		personNameDT.setNmSuffix(suffix);
		String prefix = hl7CNNType.getHL7Prefix();
		personNameDT.setNmPrefix(prefix);
		String degree = hl7CNNType.getHL7Degree();
		personNameDT.setNmDegree(degree);
		personNameDT.setNmUseCd(EdxELRConstants.ELR_LEGAL_NAME);
		personNameDT.setRecordStatusCd(EdxELRConstants.ELR_ACTIVE);
		personNameDT.setPersonNameSeq(new Integer(1));
		personNameDT.setAddTime(personVO.getThePersonDT().getAddTime());
		personNameDT.setAddReasonCd(EdxELRConstants.ADD_REASON_CD);
		personNameDT.setAsOfDate(personVO.getThePersonDT().getLastChgTime());
		personNameDT.setAddUserId(personVO.getThePersonDT().getAddUserId());
		personNameDT.setItNew(true);
		personNameDT.setItDirty(false);
		personNameDT.setLastChgTime(personVO.getThePersonDT().getLastChgTime());
		personNameDT.setLastChgUserId(personVO.getThePersonDT()
				.getLastChgUserId());
		int seq = 0;
		if (personVO.getThePersonNameDTCollection() == null) {
			personVO.setThePersonNameDTCollection(new ArrayList<Object>());
		} else {
			seq = personVO.getThePersonNameDTCollection().size();
		}
		personNameDT.setPersonNameSeq(seq + 1);

		personVO.getThePersonNameDTCollection().add(personNameDT);
		personDtToPersonVO(personNameDT, personVO);
		return personVO;
	}



	public EntityIdDT processEntityData(HL7CXType hl7CXType,
			PersonVO personVO, String indicator, int j) throws NEDSSAppException {
		EntityIdDT entityIdDT = new EntityIdDT();
		if (hl7CXType != null ) {
			entityIdDT.setEntityUid(personVO.getThePersonDT().getPersonUid());
			entityIdDT.setAddTime(personVO.getThePersonDT().getAddTime());
			entityIdDT.setEntityIdSeq(j + 1);
			entityIdDT.setRootExtensionTxt(hl7CXType.getHL7IDNumber());
			if(hl7CXType.getHL7AssigningAuthority()!=null){
				entityIdDT.setAssigningAuthorityCd(hl7CXType.getHL7AssigningAuthority().getHL7UniversalID());
				entityIdDT.setAssigningAuthorityDescTxt(hl7CXType.getHL7AssigningAuthority().getHL7NamespaceID());
				entityIdDT.setAssigningAuthorityIdType(hl7CXType.getHL7AssigningAuthority().getHL7UniversalIDType());
			}
			if (indicator != null
					&& indicator
							.equals(EdxELRConstants.ELR_PATIENT_ALTERNATE_IND)) {
				entityIdDT
						.setTypeCd(EdxELRConstants.ELR_PATIENT_ALTERNATE_TYPE);
				entityIdDT
						.setTypeDescTxt(EdxELRConstants.ELR_PATIENT_ALTERNATE_DESC);


			}else if (indicator != null
					&& indicator
					.equals(EdxELRConstants.ELR_MOTHER_IDENTIFIER)) {
				entityIdDT
				.setTypeCd(EdxELRConstants.ELR_MOTHER_IDENTIFIER);
				entityIdDT
					.setTypeDescTxt(EdxELRConstants.ELR_MOTHER_IDENTIFIER);

			} else if (indicator != null
					&& indicator
					.equals(EdxELRConstants.ELR_ACCOUNT_IDENTIFIER)) {
				entityIdDT
				.setTypeCd(EdxELRConstants.ELR_ACCOUNT_IDENTIFIER);
				entityIdDT
					.setTypeDescTxt(EdxELRConstants.ELR_ACCOUNT_DESC);

			}
			else if (hl7CXType.getHL7IdentifierTypeCode() == null
					|| hl7CXType.getHL7IdentifierTypeCode().trim().equals("")) {
				entityIdDT.setTypeCd(EdxELRConstants.ELR_PERSON_TYPE);
				entityIdDT.setTypeDescTxt(EdxELRConstants.ELR_PERSON_TYPE_DESC);
				// entityIdDT.setTypeCd(hl7CXType.getHL7IdentifierTypeCode());
				String typeCode = CachedDropDowns.getCodeDescTxtForCd(
						entityIdDT.getTypeCd(), EdxELRConstants.EI_TYPE);
 
				if (typeCode == null || typeCode.trim().equals("")) {
					entityIdDT.setTypeDescTxt(EdxELRConstants.ELR_CLIA_DESC);
				} else
					entityIdDT.setTypeDescTxt(typeCode);

			} else {
				entityIdDT.setTypeCd(hl7CXType.getHL7IdentifierTypeCode());
			}

			/*if(hl7CXType.getHL7AssigningAuthority()!=null)
				entityIdDT.setAssigningAuthorityDescTxt(hl7CXType
					.getHL7AssigningAuthority().getHL7UniversalID());
			*/
			entityIdDT.setAddUserId(personVO.getThePersonDT().getAddUserId());
			entityIdDT.setLastChgUserId(personVO.getThePersonDT()
					.getLastChgUserId());
			entityIdDT.setStatusCd(EdxELRConstants.ELR_ACTIVE_CD);
			entityIdDT.setStatusTime_s(personVO.getThePersonDT().getAddTime()
					.toString());
			entityIdDT.setRecordStatusTime_s(personVO.getThePersonDT()
					.getAddTime().toString());
			entityIdDT.setRecordStatusCd(NEDSSConstants.ACTIVE);
			entityIdDT.setAsOfDate(personVO.getThePersonDT().getAddTime());
			entityIdDT.setEffectiveFromTime(processHL7DTType(hl7CXType
					.getHL7EffectiveDate(), EdxELRConstants.DATE_VALIDATION_PID_PATIENT_IDENTIFIER_EFFECTIVE_DATE_TIME_MSG));
			entityIdDT.setValidFromTime(entityIdDT.getEffectiveFromTime());
			entityIdDT.setEffectiveToTime(processHL7DTType(hl7CXType
					.getHL7ExpirationDate(), EdxELRConstants.DATE_VALIDATION_PID_PATIENT_IDENTIFIER_EXPIRATION_DATE_TIME_MSG));
			entityIdDT.setValidToTime(entityIdDT.getEffectiveToTime());
			entityIdDT.setItNew(true);
			entityIdDT.setItDirty(false);
		}
		return entityIdDT;
	}

	public PersonVO MapPersonNameType(HL7XPNType hl7XPNType,
			PersonVO personVO) throws NEDSSAppException {
		PersonNameDT personNameDT = new PersonNameDT();
		HL7FNType hl7FamilyName = hl7XPNType.getHL7FamilyName();
		/** Optional maxOccurs="1 */
		if(hl7FamilyName!=null){
			personNameDT.setLastNm(hl7FamilyName.getHL7Surname());
			personNameDT.setLastNm2(hl7FamilyName.getHL7OwnSurname());
		}
		/** length"194 */
		personNameDT.setFirstNm(hl7XPNType.getHL7GivenName());
		/** Optional maxOccurs="1 */
		/** length"30 */
		String hl7SecondAndFurtherGivenNamesOrInitialsThereof = hl7XPNType
				.getHL7SecondAndFurtherGivenNamesOrInitialsThereof();
		/** Optional maxOccurs="1 */
		/** length"30 */
		personNameDT
				.setMiddleNm(hl7SecondAndFurtherGivenNamesOrInitialsThereof);
		String hl7Suffix = hl7XPNType.getHL7Suffix();
		/** Optional maxOccurs="1 */
		/** length"20 */
		personNameDT.setNmSuffix(hl7Suffix);

		String hl7Prefix = hl7XPNType.getHL7Prefix();
		/** Optional maxOccurs="1 */
		/** length"20 */
		personNameDT.setNmPrefix(hl7Prefix);

		/** Optional maxOccurs="1 */
		/** length"6 */
		String hl7NameTypeCode = hl7XPNType.getHL7NameTypeCode();
		if(hl7NameTypeCode==null)
			personNameDT.setNmUseCd(EdxELRConstants.ELR_LEGAL_NAME);
		else if(hl7NameTypeCode!=null)
				personNameDT.setNmUseCd(hl7NameTypeCode);
		String toCode = CachedDropDowns.findToCode("ELR_LCA_NM_USE", personNameDT.getNmUseCd(), "P_NM_USE");
		if(toCode!=null){
			personNameDT.setNmUseCd(toCode);
		}
		/** length"1 */
		HL7TSType hl7EffectiveDate = hl7XPNType.getHL7EffectiveDate();
		/** Optional maxOccurs="1 */
		/** length"26 */
		Hl7ToNBSObjectConverter hl7ToNBSObjectConverter = new Hl7ToNBSObjectConverter();
		Timestamp timestamp = hl7ToNBSObjectConverter
				.processHL7TSType(hl7EffectiveDate, EdxELRConstants.DATE_VALIDATION_PERSON_NAME_FROM_TIME_MSG);
		personNameDT.setFromTime(timestamp);
		HL7TSType hl7ExpirationDate = hl7XPNType.getHL7ExpirationDate();
		/** Optional maxOccurs="1 */
		/** length"26 */
		Timestamp toTimestamp = hl7ToNBSObjectConverter
				.processHL7TSType(hl7ExpirationDate, EdxELRConstants.DATE_VALIDATION_PERSON_NAME_TO_TIME_MSG);
		personNameDT.setToTime(toTimestamp);
		/** Optional maxOccurs="1 */
		/** length"199 */
		personNameDT.setAddTime(personVO.getThePersonDT().getAddTime());
		personNameDT.setAddReasonCd(EdxELRConstants.ADD_REASON_CD);
		personNameDT.setRecordStatusCd(EdxELRConstants.ELR_ACTIVE);
		personNameDT.setAsOfDate(personVO.getThePersonDT().getLastChgTime());
		personNameDT.setAddUserId(personVO.getThePersonDT().getAddUserId());
		personNameDT.setItNew(true);
		personNameDT.setItDirty(false);
		personNameDT.setLastChgTime(personVO.getThePersonDT().getLastChgTime());
		personNameDT.setLastChgUserId(personVO.getThePersonDT()
				.getLastChgUserId());
		int seq = 0;
		if (personVO.getThePersonNameDTCollection() == null) {
			personVO.setThePersonNameDTCollection(new ArrayList<Object>());
			seq = 0;
		} else {
			seq = personVO.getThePersonNameDTCollection().size();
		}
		personNameDT.setPersonNameSeq(seq + 1);

		personVO.getThePersonNameDTCollection().add(personNameDT);

		if (personNameDT.getNmUseCd()!=null && personNameDT.getNmUseCd().equals(EdxELRConstants.ELR_LEGAL_NAME)) {
			personVO.getThePersonDT().setLastNm(personNameDT.getLastNm());
			personVO.getThePersonDT().setFirstNm(personNameDT.getFirstNm());
			personVO.getThePersonDT().setNmPrefix(personNameDT.getNmPrefix());
			personVO.getThePersonDT().setNmSuffix(personNameDT.getNmSuffix());
		}
		return personVO;
	}

	public PersonRaceDT raceType(HL7CWEType hl7CEType, PersonVO personVO) {
		PersonRaceDT raceDT = new PersonRaceDT();
		raceDT.setItNew(true);
		raceDT.setItDelete(false);
		raceDT.setItDirty(false);
		raceDT.setAddTime(new Timestamp(new Date().getTime()));
		raceDT.setAddUserId(personVO.getThePersonDT().getAddUserId());

		if (hl7CEType.getHL7Identifier() != null) {
			raceDT.setRaceCd(hl7CEType.getHL7Identifier());
			raceDT.setRaceDescTxt(hl7CEType.getHL7Text());
			raceDT.setRaceCategoryCd(hl7CEType.getHL7Identifier());
		} else {
			raceDT.setRaceCd(hl7CEType.getHL7AlternateIdentifier());
			raceDT.setRaceDescTxt(hl7CEType.getHL7AlternateText());
			raceDT.setRaceCategoryCd(hl7CEType.getHL7AlternateIdentifier());
		}



		raceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
		raceDT.setAsOfDate(personVO.getThePersonDT().getAddTime());
		return raceDT;
	}

	public PersonEthnicGroupDT ethnicGroupType(HL7CWEType hl7CWEType,
			PersonVO personVO) {
		PersonEthnicGroupDT ethnicGroupDT = new PersonEthnicGroupDT();

		ethnicGroupDT.setItNew(true);
		ethnicGroupDT.setItDirty(false);
		ethnicGroupDT.setRecordStatusCd(NEDSSConstants.ACTIVE);
		ethnicGroupDT.setPersonUid(personVO.getThePersonDT().getPersonUid());
		ethnicGroupDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
		ethnicGroupDT.setEthnicGroupCd(hl7CWEType.getHL7Identifier());
		ethnicGroupDT.setEthnicGroupDescTxt(hl7CWEType.getHL7Text());
		personVO.getThePersonDT().setEthnicGroupInd(ethnicGroupDT.getEthnicGroupCd());

		return ethnicGroupDT;
	}

	public PostalLocatorDT nbsStreetAddressType(HL7SADType hl7SADType,
			PostalLocatorDT pl) {

		String streetOrMailingAddress = hl7SADType
				.getHL7StreetOrMailingAddress();
		/** Optional maxOccurs="1 */
		/** length"120 */
		pl.setStreetAddr1(streetOrMailingAddress);
		String streetName = hl7SADType.getHL7StreetName();
		/** Optional maxOccurs="1 */
		/** length"50 */
		String dwellingNumber = hl7SADType.getHL7DwellingNumber();
		/** Optional maxOccurs="1 */
		/** length"12 */

		if(dwellingNumber==null)
			dwellingNumber="";
		if(streetName==null)
			streetName="";
			pl.setStreetAddr2(dwellingNumber + " " + streetName);
		return pl;
	}

	public EntityLocatorParticipationDT personAddressType(
			HL7XADType hl7XADType, String role, PersonVO personVO) throws NEDSSAppException {
		EntityLocatorParticipationDT elp = addressType(hl7XADType, role);
		elp.setEntityUid(personVO.getThePersonDT().getPersonUid());
		elp.setAddUserId(personVO.getThePersonDT().getAddUserId());
		elp.setAsOfDate(personVO.getThePersonDT().getLastChgTime());
		elp.getThePostalLocatorDT().setAddUserId(
				Long.valueOf(personVO.getThePersonDT().getAddUserId()));

		if (personVO.getTheEntityLocatorParticipationDTCollection() == null) {
			personVO.setTheEntityLocatorParticipationDTCollection(new ArrayList<Object>());
		}
		personVO.getTheEntityLocatorParticipationDTCollection().add(elp);
		return elp;
	}

	public EntityLocatorParticipationDT organizationAddressType(
			HL7XADType hl7XADType, String role, OrganizationVO organizationVO) throws NEDSSAppException {
		EntityLocatorParticipationDT elp = addressType(hl7XADType, role);
		elp.setEntityUid(organizationVO.getTheOrganizationDT()
				.getOrganizationUid());
		elp.setAddUserId(organizationVO.getTheOrganizationDT().getAddUserId());
		elp.setAsOfDate(organizationVO.getTheOrganizationDT().getLastChgTime());
		elp.getThePostalLocatorDT().setAddUserId(
				Long.valueOf(organizationVO.getTheOrganizationDT()
						.getAddUserId()));
		if (organizationVO.getTheEntityLocatorParticipationDTCollection() == null) {
			organizationVO
					.setTheEntityLocatorParticipationDTCollection(new ArrayList<Object>());
		}
		organizationVO.getTheEntityLocatorParticipationDTCollection().add(elp);
		return elp;
	}

	public EntityLocatorParticipationDT addressType(
			HL7XADType hl7XADType, String role) throws NEDSSAppException {

		EntityLocatorParticipationDT elp =new EntityLocatorParticipationDT();;
		try {
			elp.setItNew(true);
			elp.setItDirty(false);
			elp.setAddTime(new Timestamp(new Date().getTime()));
			elp.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
			elp.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
			CachedDropDownValues cddv = new CachedDropDownValues();

			String addressType = hl7XADType.getHL7AddressType();
			/** Optional maxOccurs="1 */
			/** length"3 */

			if (role.equalsIgnoreCase(EdxELRConstants.ELR_OP_CD)) {
				elp.setClassCd(EdxELRConstants.ELR_POSTAL_CD);
				elp.setUseCd(EdxELRConstants.ELR_WORKPLACE_CD);
				elp.setCd(EdxELRConstants.ELR_OFFICE_CD);
				elp.setCdDescTxt(EdxELRConstants.ELR_OFFICE_DESC);
			} else if (role.equalsIgnoreCase(EdxELRConstants.ELR_NEXT_OF_KIN)) {
				elp.setClassCd(EdxELRConstants.ELR_POSTAL_CD);
				elp.setUseCd(EdxELRConstants.ELR_USE_EMERGENCY_CONTACT_CD);
				elp.setCd(EdxELRConstants.ELR_HOUSE_CD);
				elp.setCdDescTxt(EdxELRConstants.ELR_HOUSE_DESC);
			} else {
				if (addressType == null)
					elp.setCd(EdxELRConstants.ELR_HOUSE_CD);
				else
					elp.setCd(addressType);
				elp.setClassCd(NEDSSConstants.POSTAL);
				elp.setUseCd(NEDSSConstants.HOME);
			}

			PostalLocatorDT pl = new PostalLocatorDT();
			pl.setItNew(true);
			pl.setItDirty(false);
			pl.setAddTime(new Timestamp(new Date().getTime()));
			pl.setRecordStatusTime(new Timestamp(new Date().getTime()));

			pl.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
			HL7SADType HL7StreetAddress = hl7XADType.getHL7StreetAddress();
			/** Optional maxOccurs="1 */
			/** length"184 */
			if(HL7StreetAddress!=null){
				pl = nbsStreetAddressType(HL7StreetAddress, pl);
			}
			if(hl7XADType.getHL7OtherDesignation()!=null && (pl.getStreetAddr2()==null || pl.getStreetAddr2().trim().equalsIgnoreCase("")))
				pl.setStreetAddr2(hl7XADType.getHL7OtherDesignation());

			String city = hl7XADType.getHL7City();
			/** Optional maxOccurs="1 */
			/** length"50 */
			pl.setCityDescTxt(city);
			String stateOrProvince = hl7XADType.getHL7StateOrProvince();
			/** Optional maxOccurs="1 */
			/** length"50 */

			String state="";
			if(stateOrProvince!=null)
				state=translateStateCd(stateOrProvince);
			pl.setStateCd(state);
			String zip = hl7XADType.getHL7ZipOrPostalCode();
			/** Optional maxOccurs="1 */
			/** length"12 */

			pl.setZipCd(formatZip(zip));
			String country = hl7XADType.getHL7Country();
			if(country!=null && country.equalsIgnoreCase(EdxELRConstants.ELR_USA_DESC))
				pl.setCntryCd(EdxELRConstants.ELR_USA_CD);
			else
				pl.setCntryCd(country);
			String countyParishCode = hl7XADType.getHL7CountyParishCode();

			/** Optional maxOccurs="1 */
			/** length"20 */
			String cnty = cddv.getCountyCdByDesc(countyParishCode,pl.getStateCd());
			if(cnty==null)
				pl.setCntyCd(countyParishCode);
			else
				pl.setCntyCd(cnty);
			String HL7CensusTract = hl7XADType.getHL7CensusTract();
			/** Optional maxOccurs="1 */
			/** length"20 */
			pl.setCensusTrackCd(HL7CensusTract);

			elp.setThePostalLocatorDT(pl);
		} catch (Exception e) {
			logger.fatal("Hl7ToNBSObjectConverter. Error thrown: "+e);
		}
		return elp;
	}

	public EntityLocatorParticipationDT setPersonBirthType(String countryOfBirth, PersonVO personVO) {
		EntityLocatorParticipationDT elp = new EntityLocatorParticipationDT();

		elp.setItNew(true);
		elp.setItDirty(false);
		elp.setAddTime(new Timestamp(new Date().getTime()));
		elp.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
		elp.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
		elp.setClassCd(EdxELRConstants.ELR_TELE_CD);
		elp.setUseCd(NEDSSConstants.HOME);
		elp.setCd(EdxELRConstants.ELR_PHONE_CD);
		elp.setCdDescTxt(EdxELRConstants.ELR_PHONE_DESC);
		elp.setClassCd("PST") ;
		elp.setUseCd("BIR");
		elp.setCd("F");
        elp.setAddUserId(personVO.getThePersonDT().getAddUserId());
		elp.setEntityUid(personVO.getThePersonDT().getPersonUid());
		elp.setAsOfDate(personVO.getThePersonDT().getLastChgTime());

		PostalLocatorDT pl = new PostalLocatorDT();
		pl.setItNew(true);
		pl.setItDirty(false);
		pl.setAddTime(new Timestamp(new Date().getTime()));
		pl.setRecordStatusTime(new Timestamp(new Date().getTime()));
		pl.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
		pl.setCntryCd(countryOfBirth);
		pl.setAddUserId(personVO.getThePersonDT().getAddUserId());
		elp.setThePostalLocatorDT(pl);
		if (personVO.getTheEntityLocatorParticipationDTCollection() == null) {
			personVO.setTheEntityLocatorParticipationDTCollection(new ArrayList<Object>());
		}
		personVO.getTheEntityLocatorParticipationDTCollection().add(elp);
		return elp;
	}
	public EntityLocatorParticipationDT orgTelePhoneType(
			HL7XTNType hl7XTNType, String role, OrganizationVO organizationVO) {
		EntityLocatorParticipationDT elp = telePhoneType(hl7XTNType, role);
		elp.setAddUserId(organizationVO.getTheOrganizationDT().getAddUserId());
		elp.setEntityUid(organizationVO.getTheOrganizationDT()
				.getOrganizationUid());
		elp.setAsOfDate(organizationVO.getTheOrganizationDT().getLastChgTime());
		elp.getTheTeleLocatorDT().setAddUserId(
				organizationVO.getTheOrganizationDT().getAddUserId());
		return elp;

	}

	public EntityLocatorParticipationDT personTelePhoneType(
			HL7XTNType hl7XTNType, String role, PersonVO personVO) {
		EntityLocatorParticipationDT elp = telePhoneType(hl7XTNType, role);
		elp.setAddUserId(personVO.getThePersonDT().getAddUserId());
		elp.setEntityUid(personVO.getThePersonDT().getPersonUid());
		elp.setAsOfDate(personVO.getThePersonDT().getLastChgTime());
		elp.getTheTeleLocatorDT().setAddUserId(
				personVO.getThePersonDT().getAddUserId());
		if (personVO.getTheEntityLocatorParticipationDTCollection() == null) {
			personVO.setTheEntityLocatorParticipationDTCollection(new ArrayList<Object>());
		}
		personVO.getTheEntityLocatorParticipationDTCollection().add(elp);
		return elp;
	}
	

	public static boolean checkIfAreaCodeMoreThan3Digits(ArrayList<String> areaAndNumber, HL7NMType HL7Type){
		
		boolean incorrectLength = false;
		String areaCode="", number=""; 
		if (HL7Type != null && !HL7Type.toString().equalsIgnoreCase("<xml-fragment/>")) {
					
					String hl7segment =  HL7Type.toString();
					hl7segment = hl7segment.substring(hl7segment.indexOf("\">")+2);
					String areaCodeString = hl7segment.substring(0,hl7segment.indexOf("<"));
		
					if(areaCodeString.length()>3){//Area code more than 3 digits
						incorrectLength= true;
						areaCode = areaCodeString.substring(0,3);
						number = areaCodeString.substring(3);
						
						areaAndNumber.add(areaCode);
						areaAndNumber.add(number);
						
					
					}
					
			//if the phone number contains the area code, it seems to work fine because everything goes to the number and no math.round function is used, so the issue were the wrong number is created does not happen
		}
		
			return incorrectLength;
	}
	public static boolean  checkIfNumberMoreThan10Digits(ArrayList<String> areaAndNumber,  HL7NMType HL7Type){
		

		boolean incorrectLength = false;
		String areaCode="", number=""; 
		
		if (HL7Type != null && !HL7Type.toString().equalsIgnoreCase("<xml-fragment/>")) {
			
			String hl7segment =  HL7Type.toString();
			hl7segment = hl7segment.substring(hl7segment.indexOf("\">")+2);
			String areaCodeString = hl7segment.substring(0,hl7segment.indexOf("<"));
	
			if(areaCodeString.length()>10){//Phone number more than 10 digits
				int length = areaCodeString.length();
				incorrectLength= true;
			
				areaCode = areaCodeString.substring(0,length-10);
				number = areaCodeString.substring(length-10);
			

				areaAndNumber.add(areaCode);
				areaAndNumber.add(number);
				
			}
			
			//if the phone number contains the area code, it seems to work fine because everything goes to the number and no math.round function is used, so the issue were the wrong number is created does not happen
	}

		
		return incorrectLength;
		
	}

	public EntityLocatorParticipationDT telePhoneType(
			HL7XTNType hl7XTNType, String role) {
		EntityLocatorParticipationDT elp = new EntityLocatorParticipationDT();
		TeleLocatorDT teleDT = new TeleLocatorDT();

		elp.setItNew(true);
		elp.setItDirty(false);
		elp.setAddTime(new Timestamp(new Date().getTime()));
		elp.setCd(NEDSSConstants.PHONE);

		if (role.equalsIgnoreCase(EdxELRConstants.ELR_NEXT_OF_KIN)) {
			elp.setClassCd(EdxELRConstants.ELR_TELE_CD);
			elp.setUseCd(NEDSSConstants.HOME);
			elp.setCd(EdxELRConstants.ELR_PHONE_CD);
			elp.setCdDescTxt(EdxELRConstants.ELR_PHONE_DESC);
		} else {
			elp.setClassCd(EdxELRConstants.ELR_TELE_CD);
			elp.setUseCd(NEDSSConstants.HOME);
			elp.setCd(EdxELRConstants.ELR_PHONE_CD);
			elp.setCdDescTxt(EdxELRConstants.ELR_PHONE_DESC);
		}

		elp.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
		elp.setStatusCd(NEDSSConstants.STATUS_ACTIVE);
		teleDT.setItNew(true);
		teleDT.setItDirty(false);
		teleDT.setAddTime(new Timestamp(new Date().getTime()));
		teleDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
		elp.setTheTeleLocatorDT(teleDT);

		elp.setClassCd(NEDSSConstants.TELE);

		String emailAddress = hl7XTNType.getHL7EmailAddress();
		/** Optional maxOccurs="1 */
		/** length"199 */
		teleDT.setEmailAddress(emailAddress);
		String areaCode = "";
		String number = "";
		HL7NMType hl7AreaCityCode = hl7XTNType.getHL7AreaCityCode();
		HL7NMType hl7LocalNumber = hl7XTNType.getHL7LocalNumber();
		
		/** Optional maxOccurs="1 */
		/** length"5 */
		boolean incorrectLength = false;
		
		ArrayList<String> areaAndNumber = new ArrayList<String>();
		incorrectLength = checkIfAreaCodeMoreThan3Digits(areaAndNumber, hl7AreaCityCode);
		if(!incorrectLength)
			incorrectLength = checkIfNumberMoreThan10Digits(areaAndNumber, hl7LocalNumber);
		
		if(!incorrectLength){
		
			if (hl7AreaCityCode != null) {
				areaCode = Math.round(hl7AreaCityCode.getHL7Numeric()) + "";
			}
			hl7LocalNumber = hl7XTNType.getHL7LocalNumber();
			/** Optional maxOccurs="1 */
			/** length"9 */
			/*If the float is too long, like 10 digits, the float format would be somethign line 11.1F, and trying to convert it to String, in some cases, the precision
			 * is not great, and the number changes. That is the reason I am treating the local number as String. NBSCentral defect related is #2758*/
			if (hl7LocalNumber != null) {
				
				String localNumberString = hl7LocalNumber.toString();
				int begin = localNumberString.indexOf(">");
				if(begin!=-1){
					String subString1 = localNumberString.substring(begin+1);
					int end = subString1.indexOf("<");
					if(end!=-1)
						number = subString1.substring(0,end);
				}
				//number = (String.format ("%.0f", hl7LocalNumber.getHL7Numeric()));
				  
			}
		}
		else{
			areaCode = areaAndNumber.get(0);
			number = areaAndNumber.get(1);
		}	
		
		if(areaCode!=null && areaCode.equalsIgnoreCase("0"))
				areaCode = "";
			
		String phoneNbrTxt = areaCode + number;

		String formattedPhoneNumber = formatPhoneNbr(phoneNbrTxt);
		teleDT.setPhoneNbrTxt(formattedPhoneNumber);

		HL7NMType extension = hl7XTNType.getHL7Extension();
		teleDT.setExtensionTxt(extension.getHL7Numeric()+"");
		/** Optional maxOccurs="1 */
		/** length"5 */

		// teleDT.setExtensionTxt(extension.getHL7Numeric().getValue1()+"");
		String anyText = hl7XTNType.getHL7AnyText();
		/** Optional maxOccurs="1 */
		/** length"199 */
		teleDT.setUserAffiliationTxt(anyText);
		return elp;
	}

	public Timestamp convertCalToTimeStamp(Calendar calenderObject) {
		if (calenderObject != null && calenderObject.getTime() != null
				&& calenderObject.getTime().getTime() > 0) {
			Timestamp timestamp = new Timestamp(calenderObject.getTime()
					.getTime());
			return timestamp;
		} else
			return null;
	}

	public PersonVO personDtToPersonVO(PersonNameDT personNameDT,
			PersonVO personVO) {
		personVO.getThePersonDT().setLastNm(personNameDT.getLastNm());
		personVO.getThePersonDT().setFirstNm(personNameDT.getFirstNm());
		personVO.getThePersonDT().setNmPrefix(personNameDT.getNmPrefix());
		personVO.getThePersonDT().setNmSuffix(personNameDT.getNmSuffix());

		return personVO;
	}

	public ParticipationDT defaultParticipationDT(
			ParticipationDT participationDT,
			EdxLabInformationDT edxLabInformationDT) {
		participationDT.setAddTime(edxLabInformationDT.getAddTime());
		participationDT.setLastChgTime(edxLabInformationDT.getAddTime());
		participationDT.setAddUserId(edxLabInformationDT.getUserId());
		participationDT.setAddReasonCd(EdxELRConstants.ELR_ROLE_REASON);
		participationDT.setAddTime(edxLabInformationDT.getAddTime());
		participationDT.setStatusCd(EdxELRConstants.ELR_ACTIVE_CD);
		participationDT.setRecordStatusCd(EdxELRConstants.ELR_ACTIVE);
		participationDT.setItDirty(false);
		participationDT.setItNew(true);

		return participationDT;
	}

	public String formatZip(String zip) {
		if (zip != null) {
			zip =zip.trim();
			// for zip code like: 12,123,1234,12345
			if (zip.length() <= 5) {
				return zip;
			}
			// for zip code like: 123456789
			else if (zip.length() == 9 && zip.indexOf("-") == -1) {
				zip = zip.substring(0, 5) + "-" + zip.substring(5, 9);
				// for zip code like: 123456,1234567890: Will ignore 12345-6789
			} else if (zip.length() > 5 && zip.indexOf("-") == -1)
				zip = zip.substring(0, 5);
		}// end of if
		return zip;
	}

	public String formatPhoneNbr(String phoneNbrTxt) {
		// Format numeric number into telephone format
		// eg, 1234567 -> 123-4567, 1234567890 -> 123-456-7890
		String newFormatedNbr = "";
		if (phoneNbrTxt != null) {
			// String phoneNbr = dt.getPhoneNbrTxt();
			phoneNbrTxt =phoneNbrTxt.trim();
			int nbrSize = phoneNbrTxt.length();

			if (nbrSize > 4) { // Add first dash
				newFormatedNbr = "-"
						+ phoneNbrTxt.substring(nbrSize - 4, nbrSize);
				if (nbrSize > 7) { // Add a second dash
					newFormatedNbr = phoneNbrTxt.substring(0, nbrSize - 7)
							+ "-"
							+ phoneNbrTxt.substring(nbrSize - 7, nbrSize - 4)
							+ newFormatedNbr;
				} else {
					String remainder = phoneNbrTxt.substring(0, nbrSize - 4);
					newFormatedNbr = remainder + newFormatedNbr;
				}

			} else {
				newFormatedNbr = phoneNbrTxt;
			}
		}// end of if
		return newFormatedNbr;
	}// End of formatPhoneNbr

	public String formatSSN(String entityId) {
		String newEntityId = "";
		if (entityId != null && !entityId.equals("") && !entityId.equals(" ")) {
			entityId=entityId.trim();
			if (entityId.length() > 3) {
				newEntityId = entityId.substring(0, 3);
				newEntityId = newEntityId + "-";
				if (entityId.length() > 5) {
					newEntityId = newEntityId + entityId.substring(3, 5) + "-";
					newEntityId = newEntityId + entityId.substring(5, (entityId.length()));
					entityId = newEntityId;
				} else {
					newEntityId = newEntityId + entityId.substring(3, entityId.length()) + "- ";
					entityId = newEntityId;
				}
			} else {
				entityId = entityId + "- - ";
			}
		}// end of if

		return newEntityId;
	}// END OF IF

	public String translateStateCd(String msgInStateCd)
	            throws NEDSSAppException {
		if(msgInStateCd!=null && msgInStateCd.trim().length()>0)
		{
			 String SRT_SELECT_STATE_CD_ORACLE = "select state_cd \"key\" "+
					" FROM " + NEDSSConstants.SYSTEM_REFERENCE_TABLE+".State_code where state_nm = ?";
			String SRT_SELECT_STATE_CD_SQL = "select state_cd \"key\" "+
					" FROM " +  NEDSSConstants.SYSTEM_REFERENCE_TABLE+"..State_code where state_nm = ?";

		      SRTMapDAOImpl dao = new SRTMapDAOImpl();
		      String codeSql = null;
		      codeSql = SRT_SELECT_STATE_CD_SQL;
		      String stateCd = dao.getStateCd(codeSql, msgInStateCd);
		      return stateCd;
		}
		else
		{
		      return null;
		}
	}


	public EntityIdDT validateSSN(EntityIdDT entityIdDt) {
		String ssn = entityIdDt.getRootExtensionTxt();
		if(ssn != null && !ssn.equals("") && !ssn.equals(" ")) {
			ssn =ssn.trim();
			if (ssn.length() > 3) {
				String newSSN = ssn.substring(0, 3);
				newSSN = newSSN + "-";
				if (ssn.length() > 5) {
					newSSN = newSSN + ssn.replace("-", "").substring(3, 5) + "-";
					newSSN = newSSN + ssn.replace("-", "").substring(5, (ssn.replace("-", "").length()));
					ssn = newSSN;
					entityIdDt.setRootExtensionTxt(ssn);
				}
				else {
					newSSN = newSSN + ssn.replace("-", "").substring(3, ssn.length()) + "- ";
					ssn = newSSN;
					entityIdDt.setRootExtensionTxt(ssn);
				}
			}
			else {
				ssn = ssn + "- - ";
				entityIdDt.setRootExtensionTxt(ssn);
			}
		}//end of if
		return entityIdDt;
	}//end of while
	
	/**
	 * The earliest date that can be stored in SQL is Jan 1st, 1753 and the latest is Dec 31st, 9999
	 * Check the date so we don't get a SQL error.
	 * @param dateVal
	 * @return true if invalid date
	 */
	public boolean isDateNotOkForDatabase (Timestamp dateVal) {
		if (dateVal == null)
			return false;
		String earliestDate = "1753-01-01"; 
		String latestDate = "9999-12-31"; 
		try{
		    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		    Date earliestDateAcceptable = dateFormat.parse(earliestDate);
		    if (dateVal.before(earliestDateAcceptable))
		    	return true; 
		    Date lastAcceptableDate = dateFormat.parse(latestDate);
		    if (dateVal.after(lastAcceptableDate))
		    	return true; 
		}catch(Exception ex){//this generic but you can control another types of exception
		 logger.error("Unexpected exception in checkDateForDatabase() " + ex.getMessage());
		}
		return false;
	}		
	
}
