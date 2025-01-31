package gov.cdc.nedss.nnd.ejb.nndmessageprocessorejb.helper;

import gov.cdc.nedss.act.notification.dt.NotificationDT;
import gov.cdc.nedss.association.dao.ActRelationshipDAOImpl;
import gov.cdc.nedss.association.dt.ActRelationshipDT;
import gov.cdc.nedss.entity.organization.dt.OrganizationDT;
import gov.cdc.nedss.entity.organization.dt.OrganizationNameDT;
import gov.cdc.nedss.entity.organization.vo.OrganizationVO;
import gov.cdc.nedss.entity.person.dt.PersonDT;
import gov.cdc.nedss.entity.person.dt.PersonNameDT;
import gov.cdc.nedss.entity.person.dt.PersonRaceDT;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.locator.dt.EntityLocatorParticipationDT;
import gov.cdc.nedss.locator.dt.PostalLocatorDT;
import gov.cdc.nedss.locator.dt.TeleLocatorDT;
import gov.cdc.nedss.nnd.ejb.nndmessageprocessorejb.dao.NotificationSRTCodeLookupTranslationDAOImpl;
import gov.cdc.nedss.phdc.CaseType;
import gov.cdc.nedss.phdc.CodedType;
import gov.cdc.nedss.phdc.CommentsType;
import gov.cdc.nedss.phdc.IdentifierType;
import gov.cdc.nedss.phdc.IdentifiersType;
import gov.cdc.nedss.phdc.LabReportCommentsType;
import gov.cdc.nedss.phdc.LabReportType;
import gov.cdc.nedss.phdc.NameType;
import gov.cdc.nedss.phdc.NumericType;
import gov.cdc.nedss.phdc.OrganizationParticipantType;
import gov.cdc.nedss.phdc.PatientType;
import gov.cdc.nedss.phdc.PostalAddressType;
import gov.cdc.nedss.phdc.ProviderNameType;
import gov.cdc.nedss.phdc.ProviderParticipantType;
import gov.cdc.nedss.phdc.ReferenceRangeType;
import gov.cdc.nedss.phdc.SectionHeaderType;
import gov.cdc.nedss.phdc.SpecimenType;
import gov.cdc.nedss.phdc.SusceptibilityType;
import gov.cdc.nedss.phdc.TelephoneType;
import gov.cdc.nedss.phdc.TestResultType;
import gov.cdc.nedss.phdc.TestsType;
import gov.cdc.nedss.phdc.ValuesType;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.LabReport;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.LabResultProxyVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.Susceptibility;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.TestResult;
import gov.cdc.nedss.proxy.util.LabResultProxyManager;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.Coded;
import gov.cdc.nedss.util.DataTables;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.Numeric;
import gov.cdc.nedss.util.PropertyUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;


/**
 * Name: PHDCLabReportHelper.java Description: Retrieve and populate Lab Report data
 * from LabResultProxyVO into PHDC XML message by populating XMLBeans classes derived
 * from PHDC.xsd.
 * Copyright: Copyright (c) 2009 Company: Computer Sciences Corporation
 * 
 * @author Beau Bannerman
 */
public class PHDCLabReportHelper {
	private static LogUtils logger = new LogUtils(PHDCLabReportHelper.class.getName());
	private static PropertyUtil propertyUtil = PropertyUtil.getInstance();
	private static MessageBuilderHelper messageBuilderHelper = new MessageBuilderHelper();

	/**
	 * @param publicHealthCaseUid
	 * @return
	 */
	private Collection<Object>  retrieveAssociatedLabReports(Long publicHealthCaseUid, NBSSecurityObj nbsSecurityObj) throws NEDSSSystemException {
		Collection<Object>  labResultProxyVoCollection  = new ArrayList<Object> ();
		LabResultProxyManager labResultProxyManager = new LabResultProxyManager();
		ActRelationshipDAOImpl actRelationshipDAOImpl = new ActRelationshipDAOImpl();

		try {
			Collection<Object>  labReportUids = actRelationshipDAOImpl.load(publicHealthCaseUid.longValue());
			
			Iterator<Object> labReportIter = labReportUids.iterator();
			
			while (labReportIter.hasNext()) {
				ActRelationshipDT actRelationship = (ActRelationshipDT)labReportIter.next(); 
				Long observationUid = actRelationship.getSourceActUid();
				String type_cd = actRelationship.getTypeCd();
				if (type_cd.equalsIgnoreCase(NEDSSConstants.LAB_REPORT))
					labResultProxyVoCollection.add(labResultProxyManager.getLabResultProxyVO(observationUid, true, nbsSecurityObj));
			}
		} catch (Exception e) {
			String errString = "PHDCLabReportHelper.retrieveAssociatedLabReports:  Exception caught: " + e.getMessage();
			logger.fatal(errString, e);
			throw new NEDSSSystemException(errString);
		}
		
		return labResultProxyVoCollection;

	}

	private void populateLabReportTypeWithLabResultProxyVO(LabReportType labReportElement, LabResultProxyVO labResultProxyVO, NotificationDT notificationDT) {
		LabReport labReport = new LabReport(labResultProxyVO);
		createSectionHeader(labReportElement, labReport, notificationDT);
		createPatient(labReportElement, labReport);
		createTests(labReportElement, labReport);
	}
	
	private void createReportingFacility(TestsType testsElement, LabReport labReport) {
		if (labReport.getReportingFacility() == null ) {
			String errString = "PHDCLabReportHelper.createReportingFacility:  Missing Reporting Facility.";
			logger.error(errString);
			throw new NEDSSSystemException(errString);
		}
		
		if (labReport.getReportingFacility() != null ) {
			OrganizationParticipantType reportingFacilityElement = testsElement.addNewReportingFacility();
			CodedType typeCd = reportingFacilityElement.addNewTypeCd();
			setCodedType(typeCd, Coded.createCoded(NEDSSConstants.PAR111_TYP_CD, DataTables.CODE_VALUE_GENERAL, NEDSSConstants.PARTICIPATION_TYPE));
			OrganizationVO reportingFacility = labReport.getReportingFacility();
			addOrganizationParticipant(reportingFacilityElement, reportingFacility);
		}
	}

	private void createOrderingFacility(TestsType testsElement, LabReport labReport) {
		if (labReport.getOrderingFacility() != null ) {
			OrganizationParticipantType orderingFacilityElement = testsElement.addNewOrderingFacility();
			CodedType typeCd = orderingFacilityElement.addNewTypeCd();
			setCodedType(typeCd, Coded.createCoded(NEDSSConstants.PAR102_TYP_CD, DataTables.CODE_VALUE_GENERAL, NEDSSConstants.PARTICIPATION_TYPE));
			OrganizationVO orderingFacility = labReport.getOrderingFacility();
			addOrganizationParticipant(orderingFacilityElement, orderingFacility);
		}
	}

	private void createOrderingProvider(TestsType testsElement, LabReport labReport) {
 		if (labReport.getOrderingProvider() != null ) {
			ProviderParticipantType orderingProviderElement = testsElement.addNewOrderingProvider();
			CodedType typeCd = orderingProviderElement.addNewTypeCd();
			setCodedType(typeCd, Coded.createCoded(NEDSSConstants.PAR102_TYP_CD, DataTables.CODE_VALUE_GENERAL, NEDSSConstants.PARTICIPATION_TYPE));
			PersonVO orderingProvider = labReport.getOrderingProvider();
			addProviderParticipant(orderingProviderElement, orderingProvider);
		}
	}
	
	private void addOrganizationParticipant(OrganizationParticipantType organizationParticipantElement, OrganizationVO organizationVO) {
		OrganizationDT organizationDT = organizationVO.getTheOrganizationDT();
		
		if (organizationDT.getLocalId() == null || organizationDT.getLocalId().trim().length() < 1 ){
			String errString = "PHDCLabReportHelper.addOrganizationParticipant:  Missing SendingApplicationOrganizationIdentifier (Local Id of Organizaiton Participant).";
			logger.error(errString);
			throw new NEDSSSystemException(errString);
		}

		IdentifiersType sendingApplicationOrganizationIdentifiers = organizationParticipantElement.addNewIdentifiers();
		IdentifierType sendingApplicationOrganizationIdentifier = sendingApplicationOrganizationIdentifiers.addNewIdentifier();
		sendingApplicationOrganizationIdentifier.setIDNumber(organizationDT.getLocalId());

		if (organizationVO.getTheOrganizationNameDTCollection() != null) {
			Iterator<Object> organizationNameItr = organizationVO.getTheOrganizationNameDTCollection().iterator();
			
			if (organizationNameItr.hasNext()) {
				String organizationName = ((OrganizationNameDT)organizationNameItr.next()).getNmTxt();
				organizationParticipantElement.setName(organizationName);
			} else {// no name
				String errString = "PHDCLabReportHelper.addOrganizationParticipant:  No Organization name found.";
				logger.error(errString);
				throw new NEDSSSystemException(errString);
			}
		}
		
		if (organizationVO.getTheEntityLocatorParticipationDTCollection() != null) {
			
			Iterator<Object> elpItr = organizationVO.getTheEntityLocatorParticipationDTCollection().iterator();
			
			while (elpItr.hasNext()) {
				EntityLocatorParticipationDT elpDT = (EntityLocatorParticipationDT)elpItr.next();
				
				if (elpDT.getRecordStatusCd()!=null && elpDT.getRecordStatusCd().equals(NEDSSConstants.ACTIVE) && elpDT.getClassCd().equalsIgnoreCase(NEDSSConstants.POSTAL) && elpDT.getUseCd().equalsIgnoreCase(NEDSSConstants.WORK_PLACE) && elpDT.getCd().equals(NEDSSConstants.OFFICE_CD) && organizationParticipantElement.getPostalAddress()==null) {
					PostalAddressType postalAddress = organizationParticipantElement.addNewPostalAddress();
					PostalLocatorDT workPlaceAddress = elpDT.getThePostalLocatorDT();
					setPostalAddress(postalAddress, workPlaceAddress);
				} else if (elpDT.getRecordStatusCd()!=null && elpDT.getRecordStatusCd().equals(NEDSSConstants.ACTIVE) && elpDT.getClassCd().equalsIgnoreCase(NEDSSConstants.TELE) && elpDT.getUseCd().equalsIgnoreCase(NEDSSConstants.WORK_PLACE)&& elpDT.getCd().equals(NEDSSConstants.PHONE) && organizationParticipantElement.getTelephone()==null) {
					TelephoneType telephone = organizationParticipantElement.addNewTelephone();
					TeleLocatorDT workPlacePhone = elpDT.getTheTeleLocatorDT();
					setTelephone(telephone, workPlacePhone);
				}
			}
		}
		
	}

	private void addProviderParticipant(ProviderParticipantType providerParticipantElement, PersonVO providerVO) {
		PersonDT providerDT = providerVO.getThePersonDT();

		if (providerDT.getLocalId() == null || providerDT.getLocalId().trim().length() < 1 ){
			String errString = "PHDCLabReportHelper.addProviderParticipant:  Missing SendingApplicationPersonIdentifier (Local Id of Provider).";
			logger.error(errString);
			throw new NEDSSSystemException(errString);
		}

		
		IdentifiersType sendingApplicationPersonIdentifiers = providerParticipantElement.addNewIdentifiers();
		IdentifierType sendingApplicationPersonIdentifier = sendingApplicationPersonIdentifiers.addNewIdentifier();
		sendingApplicationPersonIdentifier.setIDNumber(providerDT.getLocalId());

		if (providerVO.getThePersonNameDTCollection() != null) {
			Iterator<Object> providerNameItr = providerVO.getThePersonNameDTCollection().iterator();
			
			if (providerNameItr.hasNext()) {
				PersonNameDT personNameDT = (PersonNameDT)providerNameItr.next();
				String providerFirstName = personNameDT.getFirstNm();
				String providerLastName = personNameDT.getLastNm();
				String providerDegree = personNameDT.getNmDegree();
				ProviderNameType providerName = providerParticipantElement.addNewName();
				providerName.setFirst(providerFirstName);
				providerName.setLast(providerLastName);
				if(providerDegree!=null){
				CodedType degree = providerName.addNewDegree();
				setCodedType(degree, Coded.createCoded(providerDegree, DataTables.CODE_VALUE_GENERAL, "P_NM_DEG"));
				}
			} else {// no name
				String errString = "PHDCLabReportHelper.addProviderParticipant:  No Provider name found.";
				logger.error(errString);
				throw new NEDSSSystemException(errString);
			}
				
		}
		
		if (providerVO.getTheEntityLocatorParticipationDTCollection() != null) {
			
			Iterator<Object> elpItr = providerVO.getTheEntityLocatorParticipationDTCollection().iterator();
			
			while (elpItr.hasNext()) {
				EntityLocatorParticipationDT elpDT = (EntityLocatorParticipationDT)elpItr.next();
				
				if (elpDT.getRecordStatusCd()!=null && elpDT.getRecordStatusCd().equals(NEDSSConstants.ACTIVE) && elpDT.getClassCd().equalsIgnoreCase(NEDSSConstants.POSTAL) && elpDT.getUseCd().equalsIgnoreCase(NEDSSConstants.WORK_PLACE) && elpDT.getCd().equals(NEDSSConstants.OFFICE_CD) && providerParticipantElement.getPostalAddress()==null) {
					PostalAddressType postalAddress = providerParticipantElement.addNewPostalAddress();
					PostalLocatorDT workPlaceAddress = elpDT.getThePostalLocatorDT();
					setPostalAddress(postalAddress, workPlaceAddress);
				} else if (elpDT.getRecordStatusCd()!=null && elpDT.getRecordStatusCd().equals(NEDSSConstants.ACTIVE) && elpDT.getClassCd().equalsIgnoreCase(NEDSSConstants.TELE) && elpDT.getUseCd().equalsIgnoreCase(NEDSSConstants.WORK_PLACE) && elpDT.getCd().equals(NEDSSConstants.PHONE) && providerParticipantElement.getTelephone()==null) {
					TelephoneType telephone = providerParticipantElement.addNewTelephone();
					TeleLocatorDT workPlacePhone = elpDT.getTheTeleLocatorDT();
					setTelephone(telephone, workPlacePhone);
				}
			}
		}
	}
	
	/**
	 * @param labReport
	 * @param publicHealthCaseUid
	 */
	public void populatePHDCCaseWithLabReports(CaseType caseElement, Long publicHealthCaseUid, NotificationDT notificationDT, NBSSecurityObj nbsSecurityObj) throws NEDSSSystemException {
		//Retrieve lab reports associated with current 
		Collection<Object>  associatedLabReportProxyVOs = retrieveAssociatedLabReports(publicHealthCaseUid, nbsSecurityObj);
		
		Iterator<Object> labResultProxyVOItr = associatedLabReportProxyVOs.iterator();
		
		//Iterate through Collection<Object>  of associated Lab Reports and populate PHDC LabReport element
		while (labResultProxyVOItr.hasNext()) {
			LabResultProxyVO labReportProxyVO = (LabResultProxyVO)labResultProxyVOItr.next();
			LabReportType labReport = caseElement.addNewLabReport();
			populateLabReportTypeWithLabResultProxyVO(labReport, labReportProxyVO, notificationDT);
		}
	}
	
	private void createSectionHeader(LabReportType labReportElement, LabReport labReport, NotificationDT notificationDT) {
		SectionHeaderType sectionHeaderElement = labReportElement.addNewSectionHeader();
		
		CodedType documentType = sectionHeaderElement.addNewDocumentType();
		String phdcDocumentCode = "11648804";
		setCodedType(documentType, Coded.createCoded(phdcDocumentCode, DataTables.CODE_VALUE_GENERAL, "PUBLIC_HEALTH_EVENT"));
		
		CodedType purpose = sectionHeaderElement.addNewPurpose();
		setCodedType(purpose, Coded.createCoded(notificationDT.getCd(), DataTables.CODE_VALUE_GENERAL, "NBS_DOC_PURPOSE"));
		
		sectionHeaderElement.setDescription("Laboratory Reporting");
		
		if (labReport.getLabReportLocalId() == null || labReport.getLabReportLocalId().length() < 1 ){
			String errString = "PHDCLabReportHelper.createSectionHeader:  Missing SendingApplicationEventIdentifier (Local Id of LabReport).";
			logger.error(errString);
			throw new NEDSSSystemException(errString);
		}
		
		sectionHeaderElement.setSendingApplicationEventIdentifier(labReport.getLabReportLocalId());
	}
	
	private void createPatient(LabReportType labReportElement, LabReport labReport) {
		PatientType patientElement = labReportElement.addNewPatient();
		PersonVO patientVO = labReport.getPatient();
		PersonDT patientDT = patientVO.getThePersonDT();
		
		if (patientDT.getLocalId() == null || patientDT.getLocalId().trim().length() < 1 ){
			String errString = "PHDCLabReportHelper.createPatient:  Missing SendingApplicationPatientIdentifier (Local Id of Patient)";
			logger.error(errString);
			throw new NEDSSSystemException(errString);
		}

		IdentifiersType sendingApplicationPatientIdentifiers = patientElement.addNewIdentifiers();
		IdentifierType sendingApplicationPatientIdentifier = sendingApplicationPatientIdentifiers.addNewIdentifier();
		sendingApplicationPatientIdentifier.setIDNumber(patientDT.getLocalId());

		if (patientDT.getFirstNm() == null || patientDT.getLastNm() == null ||
			patientDT.getFirstNm().trim().length() < 1 || patientDT.getLastNm().trim().length() < 1	){
			String errString = "PHDCLabReportHelper.createPatient:  Incomplete or missing patient first name";
			logger.error(errString);
			throw new NEDSSSystemException(errString);
		}
		
		NameType name = patientElement.addNewName();
		name.setFirst(patientDT.getFirstNm() == null ? "" : patientDT.getFirstNm());
		name.setLast(patientDT.getLastNm() == null ? "" : patientDT.getLastNm());

		if (patientDT.getBirthTime() != null) {
			Calendar birthDateTime = messageBuilderHelper.convertNBSDateToCalendar(patientDT.getBirthTime().toString());
			patientElement.setDateOfBirth(birthDateTime);
		}
				
		if (patientDT.getAgeCalc() != null && patientDT.getAgeCalcUnitCd() != null ) {
			NumericType reportedAge = patientElement.addNewReportedAge();
			reportedAge.setValue1(patientDT.getAgeCalc().floatValue());
			CodedType reportedAgeUnits = reportedAge.addNewUnit();

			// Retrieve NBS Age Units Code Description/Code System Code and then translate to PHIN Standard and set XML Message
			NotificationSRTCodeLookupTranslationDAOImpl notificationSRTCodeLookupTranslationDAOImpl = new NotificationSRTCodeLookupTranslationDAOImpl();
			Coded reportedAgeUnitsCoded = Coded.createCoded(patientDT.getAgeCalcUnitCd(), DataTables.CODE_VALUE_GENERAL, "AGE_UNIT");
			setCodedType(reportedAgeUnits, notificationSRTCodeLookupTranslationDAOImpl.translateNBSCodetoPHINCode(reportedAgeUnitsCoded, DataTables.COUNTRY_CROSS_REFERENCE)); 
		}
		
		if (patientDT.getCurrSexCd() != null) {
			CodedType sex = patientElement.addNewSex();
			setCodedType(sex, Coded.createCoded(patientDT.getCurrSexCd(), DataTables.CODE_VALUE_GENERAL, "SEX"));
		}
		
		//Iterate through races
		if (patientVO.getThePersonRaceDTCollection() != null && patientVO.getThePersonRaceDTCollection().size() > 0) {
			Iterator<Object> personRaceItr = patientVO.getThePersonRaceDTCollection().iterator();
			
			while (personRaceItr.hasNext()) {
				PersonRaceDT personRaceDT = (PersonRaceDT)personRaceItr.next();
				CodedType race = patientElement.addNewRace();
				setRace(race, personRaceDT);
			}
		}
		
		if ( patientDT.getEthnicGroupInd() != null ) {
			CodedType ethnicity = patientElement.addNewEthnicity();
			setCodedType(ethnicity, Coded.createCoded(patientDT.getEthnicGroupInd(), DataTables.CODE_VALUE_GENERAL, "P_ETHN_GRP"));
		}

		if (patientVO.getTheEntityLocatorParticipationDTCollection() != null) {
			
			Iterator<Object> elpItr = patientVO.getTheEntityLocatorParticipationDTCollection().iterator();
			
			while (elpItr.hasNext()) {
				EntityLocatorParticipationDT elpDT = (EntityLocatorParticipationDT)elpItr.next();
				
				if ( elpDT.getRecordStatusCd()!=null && elpDT.getRecordStatusCd().equals(NEDSSConstants.ACTIVE) && elpDT.getClassCd().equalsIgnoreCase(NEDSSConstants.POSTAL) && elpDT.getUseCd().equalsIgnoreCase(NEDSSConstants.HOME) && patientElement.getPostalAddress()==null) {
					PostalAddressType postalAddress = patientElement.addNewPostalAddress();
					PostalLocatorDT homeAddress = elpDT.getThePostalLocatorDT();
					setPostalAddress(postalAddress, homeAddress);
				} else if (elpDT.getRecordStatusCd()!=null && elpDT.getRecordStatusCd().equals(NEDSSConstants.ACTIVE) && elpDT.getClassCd().equalsIgnoreCase(NEDSSConstants.TELE) && elpDT.getUseCd().equalsIgnoreCase(NEDSSConstants.HOME) && patientElement.getTelephone()==null) {
					TelephoneType telephone = patientElement.addNewTelephone();
					TeleLocatorDT homePhone = elpDT.getTheTeleLocatorDT();
					setTelephone(telephone, homePhone);
				}
			}
		}
	}
	
	private void setTelephone(TelephoneType telephone, TeleLocatorDT teleLocatorDT) {
		if (teleLocatorDT.getPhoneNbrTxt() != null && teleLocatorDT.getPhoneNbrTxt().trim().length() > 0) 
			telephone.setNumber(teleLocatorDT.getPhoneNbrTxt());
		
		if (teleLocatorDT.getExtensionTxt() != null && teleLocatorDT.getExtensionTxt().trim().length() > 0) 
			telephone.setExtension(teleLocatorDT.getExtensionTxt());
	}
	
	private void setRace(CodedType race, PersonRaceDT personRaceDT) {
		setCodedType(race, Coded.createCoded(personRaceDT.getRaceCd(), DataTables.RACE_CODE, NEDSSConstants.RACE_CODESET));
	}

	private void setPostalAddress(PostalAddressType postalAddress, PostalLocatorDT postalLocatorDT) {

		if (postalLocatorDT.getStreetAddr1() != null && postalLocatorDT.getStreetAddr1().trim().length() > 0) 
			postalAddress.setStreetAddressOne(postalLocatorDT.getStreetAddr1());
		
		if (postalLocatorDT.getStreetAddr2() != null && postalLocatorDT.getStreetAddr2().trim().length() > 0) 
			postalAddress.setStreetAddressTwo(postalLocatorDT.getStreetAddr2());

		if (postalLocatorDT.getCityDescTxt() != null && postalLocatorDT.getCityDescTxt().trim().length() > 0) 
			postalAddress.setCity(postalLocatorDT.getCityDescTxt());

		if (postalLocatorDT.getStateCd() != null && postalLocatorDT.getStateCd().trim().length() > 0) {
			CodedType state = postalAddress.addNewState();
			setCodedType(state, Coded.createCoded(postalLocatorDT.getStateCd(), DataTables.STATE_CODE_VIEW, "STATE_CCD"));
		}
		
		if (postalLocatorDT.getCntyCd() != null && postalLocatorDT.getCntyCd().trim().length() > 0) {
			CodedType county = postalAddress.addNewCounty();
			setCodedType(county, Coded.createCoded(postalLocatorDT.getCntyCd(), DataTables.STATE_COUNTY_CODE_VALUE, "COUNTY_CCD"));
		}

		if (postalLocatorDT.getZipCd() != null && postalLocatorDT.getZipCd().trim().length() > 0) {
			postalAddress.setZipCode(postalLocatorDT.getZipCd());
		}

	}
	
	private void createTests(LabReportType labReportElement, LabReport labReport) {
		TestsType testsElement = labReportElement.addNewTests();
		
		
		//Participants
		createReportingFacility(testsElement, labReport);
		createOrderingProvider(testsElement, labReport);
		createOrderingFacility(testsElement, labReport);
		
		// populate discrete items 
		
		if (labReport.getFillerOrderNumber() != null && labReport.getFillerOrderNumber().trim().length() > 0)  
			testsElement.setFillerOrderNumber(labReport.getFillerOrderNumber());

		if (labReport.getObservationDateTime() != null)
			testsElement.setObservationDateTime(labReport.getObservationDateTime());
		
		if (labReport.getLaboratoryReportDate() != null)
			testsElement.setLaboratoryReportDate(labReport.getLaboratoryReportDate());

		if (labReport.getRequestedObservation().getCode() != null && labReport.getRequestedObservation().getCode().trim().length() > 0) { 
			CodedType requestedObservation = testsElement.addNewRequestedObservation();
			setCodedType(requestedObservation, labReport.getRequestedObservation());
		}
				

		if (labReport.getClinicalInformation() != null && labReport.getClinicalInformation().trim().length() > 0)  
			testsElement.setClinicalInformation(labReport.getClinicalInformation());
		
		if (labReport.getResultStatus().getCode() != null) {
			NotificationSRTCodeLookupTranslationDAOImpl notificationSRTCodeLookupTranslationDAOImpl = new NotificationSRTCodeLookupTranslationDAOImpl();
			CodedType resultStatus = testsElement.addNewResultStatus();
			setCodedType(resultStatus, notificationSRTCodeLookupTranslationDAOImpl.translateNBSCodetoPHINCode(labReport.getResultStatus(), DataTables.STANDARD_CROSS_REFERENCE)); 
		}
		
		//Add specimen if there is one
		if (labReport.getSpecimenCode() != null && labReport.getSpecimenCode().getCode() != null && labReport.getSpecimenCode().getCode().trim().length() > 0) {
			SpecimenType specimenElement = testsElement.addNewSpecimen();
			CodedType specimenCode = specimenElement.addNewSpecimenCode();
			setCodedType(specimenCode, labReport.getSpecimenCode());
		} else if (labReport.getSpecimenDescription() != null && labReport.getSpecimenDescription().trim().length() > 0) {
			SpecimenType specimenElement = testsElement.addNewSpecimen();
			specimenElement.setDescription(labReport.getSpecimenDescription());
		}
		
		// Add TestResults
		if ( labReport.getTestResults() == null || labReport.getTestResults() .size() < 1 ) {
			String errString = "PHDCLabReportHelper.createTests:  No Test Results found.";
			logger.error(errString);
			throw new NEDSSSystemException(errString);
		}
		
		if ( labReport.getTestResults() != null && labReport.getTestResults() .size() > 0) {
			Iterator<Object> testResultsIter = labReport.getTestResults().iterator();
			while (testResultsIter.hasNext()) {
				TestResult testResult = (TestResult)testResultsIter.next();
				addNewTestResult(testsElement, testResult);
			}
		}
	}
	
	private void setCodedType(CodedType codedType, Coded coded) {
		codedType.setCode(coded.getCode());
		codedType.setCodeDescTxt(coded.getCodeDescription());
		codedType.setCodeSystemCode(coded.getCodeSystemCd());
	}
	
	private void addNewTestResult(TestsType testsElement, TestResult testResult) {
		TestResultType testResultElement = testsElement.addNewTestResult();

		if (testResult.getPerformingFacility() != null) {
			OrganizationParticipantType performingFacilityElement = testResultElement.addNewPerformingFacility();
			CodedType typeCd = performingFacilityElement.addNewTypeCd();
			setCodedType(typeCd, Coded.createCoded(NEDSSConstants.PAR122_TYP_CD, DataTables.CODE_VALUE_GENERAL, NEDSSConstants.PARTICIPATION_TYPE));
			OrganizationVO performingFacilityVO = testResult.getPerformingFacility();
			addOrganizationParticipant(performingFacilityElement, performingFacilityVO);
		}
		
		if (testResult.getTestResultCode().getCode() != null) {
			CodedType testResultCode = testResultElement.addNewTestResultCode();
			setCodedType(testResultCode, testResult.getTestResultCode());
		}

		// Must have at least one of these values present for a Test Result
		if ( testResult.getNumericValue() == null  && testResult.getCodedValue() == null  && testResult.getTextValue() == null ) {
			String errString = "PHDCLabReportHelper.addNewTestResult:  No Test Result Values (Numeric, Coded nor Text) found for Test Result.";
			logger.error(errString);
			throw new NEDSSSystemException(errString);
		}
		
		//Add values
		ValuesType valuesElement = testResultElement.addNewValues();

		if (testResult.getNumericValue() != null) {
			NumericType numericValue = valuesElement.addNewNumeric();
			setNumericType(numericValue, testResult.getNumericValue());
		}

		if (testResult.getCodedValue() != null) {
			CodedType codedValue = valuesElement.addNewCoded();
			setCodedType(codedValue, testResult.getCodedValue());
		}

		if (testResult.getTextValue() != null && testResult.getTextValue().trim().length() > 0) 
			valuesElement.setText(testResult.getTextValue());

		if (testResult.getObservationDateTime() != null)
			testResultElement.setObservationDateTime(testResult.getObservationDateTime() );
		
		//Add reference range - High
		ReferenceRangeType referenceRange = null;
		if (testResult.getReferenceRangeHigh() != null && testResult.getReferenceRangeHigh().trim().length() > 0) {
			referenceRange = testResultElement.addNewReferenceRange();
			referenceRange.setHigh(testResult.getReferenceRangeHigh());
		}			
		
		//Add reference range - Low
		if (testResult.getReferenceRangeLow() != null && testResult.getReferenceRangeLow().trim().length() > 0) {
			if (referenceRange == null)
				referenceRange = testResultElement.addNewReferenceRange();
			referenceRange.setLow(testResult.getReferenceRangeLow());
		}
		
		// If there are comments, add them
		if ( testResult.getComments() != null && testResult.getComments().size() > 0) {
			LabReportCommentsType comments = testResultElement.addNewComments();
			
			//Loop through and add comment lines
			Iterator<Object> commentsIter = testResult.getComments().iterator();
			while (commentsIter.hasNext()) {
				CommentsType comment = comments.addNewComment();
				comment.setStringValue((String)(commentsIter.next()));
			}
		}
		
		// If there are susceptibilities, add them
		if ( testResult.getSusceptibilities() != null && testResult.getSusceptibilities().size() > 0) {
			Iterator<Object> susceptibilitiesIter = testResult.getSusceptibilities().iterator();
			while (susceptibilitiesIter.hasNext()) 
				addNewSusceptibility(testResultElement, (Susceptibility)(susceptibilitiesIter.next()));
		}
	}
	
	private void setNumericType(NumericType numericType, Numeric numeric) {
		numericType.setValue1(numeric.getValue1().floatValue());
		
		if (numeric.getComparator() != null)
			numericType.setComparatorCode(numeric.getComparator());
		
		if (numeric.getSeparator() != null)
			numericType.setSeperatorCode(numeric.getSeparator());
		
		if (numeric.getValue2() != null)
			numericType.setValue2(numeric.getValue2().floatValue());

		if (numeric.getUnit() != null) {
			CodedType unitType = numericType.addNewUnit();
			setCodedType(unitType, numeric.getUnit());
		}
	}

	private void addNewSusceptibility(TestResultType testResultElement, Susceptibility susceptibility) {
		SusceptibilityType susceptibilityType = testResultElement.addNewSusceptibility();
		
		if (susceptibility.getDrug() == null){
			String errString = "PHDCLabReportHelper.addNewSusceptibility:  Missing drug for Susceptibility.";
			logger.error(errString);
			throw new NEDSSSystemException(errString);
		}

		
		//Add drug 
		if (susceptibility.getDrug() != null) {
			CodedType drug = susceptibilityType.addNewDrug();
			setCodedType(drug, susceptibility.getDrug());
		}

		if (susceptibility.getCodedResult() == null && susceptibility.getNumericResult() == null) {
			String errString = "PHDCLabReportHelper.addNewSusceptibility:  Neither coded nor numeric result for Susceptibility found.";
			logger.error(errString);
			throw new NEDSSSystemException(errString);
		}
		
		
		//Add Numeric or Coded 
		if (susceptibility.getCodedResult() != null) {
			CodedType coded = susceptibilityType.addNewCoded();
			if (susceptibility.getCodedResult().getCodeDescription().equalsIgnoreCase(NEDSSConstants.UNDEFINED)) {
				setCodedType(coded, Coded.createCoded(susceptibility.getCodedResult().getCode(), DataTables.LAB_RESULT_VIEW, "LAB_RSLT"));
			} else {			
				setCodedType(coded, susceptibility.getCodedResult());
			}
		}
		
		if (susceptibility.getNumericResult() != null) {
			NumericType numericResult = susceptibilityType.addNewNumeric();
			setNumericType(numericResult, susceptibility.getNumericResult());
		}
		
		if (susceptibility.getInterpretation() != null) {
			CodedType interpretation = susceptibilityType.addNewInterpretation();
			setCodedType(interpretation, susceptibility.getInterpretation());
		}
	}
	
}