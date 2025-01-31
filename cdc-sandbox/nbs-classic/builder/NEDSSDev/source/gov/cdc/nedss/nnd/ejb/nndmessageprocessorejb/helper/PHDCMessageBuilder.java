package gov.cdc.nedss.nnd.ejb.nndmessageprocessorejb.helper;

import gov.cdc.nedss.act.notification.dt.NotificationDT;
import gov.cdc.nedss.entity.entityid.dao.EntityIdDAOImpl;
import gov.cdc.nedss.entity.entityid.dt.EntityIdDT;
import gov.cdc.nedss.entity.organization.dt.OrganizationNameDT;
import gov.cdc.nedss.entity.organization.ejb.dao.OrganizationDAOImpl;
import gov.cdc.nedss.entity.organization.ejb.dao.OrganizationRootDAOImpl;
import gov.cdc.nedss.entity.organization.vo.OrganizationVO;
import gov.cdc.nedss.entity.person.dt.PersonDT;
import gov.cdc.nedss.entity.person.dt.PersonNameDT;
import gov.cdc.nedss.entity.person.ejb.dao.PersonDAOImpl;
import gov.cdc.nedss.entity.person.ejb.dao.PersonRootDAOImpl;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSDAOAppException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.locator.dt.EntityLocatorParticipationDT;
import gov.cdc.nedss.locator.dt.PostalLocatorDT;
import gov.cdc.nedss.locator.dt.TeleLocatorDT;
import gov.cdc.nedss.nnd.dt.CaseNotificationDataDT;
import gov.cdc.nedss.nnd.dt.NotificationParticipationDT;
import gov.cdc.nedss.nnd.ejb.nndmessageprocessorejb.dao.NotificationMessageDAOImpl;
import gov.cdc.nedss.nnd.util.NNDConstantUtil;
import gov.cdc.nedss.page.ejb.pageproxyejb.bean.dao.NBSAttachmentNoteDAOImpl;
import gov.cdc.nedss.page.ejb.pageproxyejb.dt.NBSNoteDT;
import gov.cdc.nedss.phdc.AnswerType;
import gov.cdc.nedss.phdc.CaseType;
import gov.cdc.nedss.phdc.CodedType;
import gov.cdc.nedss.phdc.ContainerDocument;
import gov.cdc.nedss.phdc.DiseaseSpecificQuestionsType;
import gov.cdc.nedss.phdc.HierarchicalDesignationType;
import gov.cdc.nedss.phdc.IdentifierType;
import gov.cdc.nedss.phdc.IdentifiersType;
import gov.cdc.nedss.phdc.NoteType;
import gov.cdc.nedss.phdc.NumericType;
import gov.cdc.nedss.phdc.ObservationType;
import gov.cdc.nedss.phdc.OrganizationParticipantType;
import gov.cdc.nedss.phdc.ParticipantsType;
import gov.cdc.nedss.phdc.PostalAddressType;
import gov.cdc.nedss.phdc.ProviderNameType;
import gov.cdc.nedss.phdc.ProviderParticipantType;
import gov.cdc.nedss.phdc.TelephoneType;
import gov.cdc.nedss.phdc.ContainerDocument.Container;
import gov.cdc.nedss.systemservice.dao.EDXActivityLogDAOImpl;
import gov.cdc.nedss.systemservice.dao.NbsInterfaceDAOImpl;
import gov.cdc.nedss.systemservice.dt.EDXActivityLogDT;
import gov.cdc.nedss.systemservice.dt.NbsInterfaceDT;
import gov.cdc.nedss.systemservice.ejb.casenotificationejb.dao.CaseNotificationDAOImpl;
import gov.cdc.nedss.systemservice.ejb.casenotificationejb.dt.ExportReceivingFacilityDT;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.Coded;
import gov.cdc.nedss.util.DataTables;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.namespace.QName;

import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlOptions;

/**
 * Name: PHDCMessageBuilder.java Description: Construct PHDC XML message using
 * XMLBeans derived from PHDC.xsd and populating with data from CaseNotificationDataDT's
 * Copyright: Copyright (c) 2008
 * Company:  Computer Sciences Corporation
 *
 * @author Beau Bannerman
 */
public class PHDCMessageBuilder {
	private static LogUtils logger = new LogUtils(PHDCMessageBuilder.class.getName());
	private static PropertyUtil propertyUtil = PropertyUtil.getInstance();
	private static MessageBuilderHelper messageBuilderHelper = new MessageBuilderHelper();

	public void createNotificationMessage(NotificationDT notificationDT,
			Long publicHealthCaseUid, String publicHealthCaseLocalId,
			String nndEntityIdentifier, NBSSecurityObj nbsSecurityObj)
			throws NEDSSSystemException, NEDSSAppException {

		// EDX Activity log
		NotificationMessageDAOImpl notificationMessageDAOImpl = new NotificationMessageDAOImpl();
		Long notificationUid = notificationDT.getNotificationUid();
		String notificationLocalId = notificationDT.getLocalId();
		Long interfaceUid = null;


		try {
			// Retrieve Participations associated with the current Investigation (via publicHealthCaseUid)
			TreeMap<Object,Object> phcEntityParticipationsMap = notificationMessageDAOImpl.getPublicHealthCaseEntityParticipations(publicHealthCaseUid);

			Collection<Object>  caseDataDTCollection  = null;
			Collection<Object>  diseaseSpecificCaseDataDTCollection  = null;

			caseDataDTCollection  = notificationMessageDAOImpl.getCaseNotificationDataDTCollection(publicHealthCaseUid, notificationDT);
			diseaseSpecificCaseDataDTCollection  = notificationMessageDAOImpl.getDiseaseSpecificCaseNotificationDataDTCollection(publicHealthCaseUid, notificationDT);

			messageBuilderHelper.retrieveNonNBSAnswerData(caseDataDTCollection, phcEntityParticipationsMap, publicHealthCaseUid, notificationUid,0);

			messageBuilderHelper.checkForRequiredMessageElements(caseDataDTCollection, phcEntityParticipationsMap);

			addPHDCHeaderAndCaseSectionHeaderInfo(caseDataDTCollection, notificationDT, nndEntityIdentifier, publicHealthCaseLocalId);

			messageBuilderHelper.codeLookupforMessageData(caseDataDTCollection);
			messageBuilderHelper.codeLookupforMessageData(diseaseSpecificCaseDataDTCollection);
			String phdcXML = buildMessage(caseDataDTCollection, diseaseSpecificCaseDataDTCollection, notificationDT, publicHealthCaseUid, nbsSecurityObj, phcEntityParticipationsMap);

			// Write PHDC message to file and/or Interface table
			NbsInterfaceDT nbsInterfaceDT = prepareNbsInterface(phdcXML, notificationDT);
			interfaceUid = writePHDCMessage(nbsInterfaceDT, notificationDT);
			notificationDT.setNbsInterfaceUid(interfaceUid);

			writeEDXActivityLogRecord(NEDSSConstants.EDX_ACTIVITY_SUCCESS, null, interfaceUid, notificationUid, notificationLocalId);
		} catch (NEDSSDAOAppException e) {
			// This exception will only be thrown from NbsInterfaceDAO if NBS_interface insert was successful
			String errString = "PHDCMessageBuilder.createNotificationMessage:  Exception caught: " + e.getMessage();
			logger.fatal(errString, e);
			writeEDXActivityLogRecord(NEDSSConstants.EDX_ACTIVITY_SUCCESS, e.getMessage(), interfaceUid, notificationUid, notificationLocalId);
		} catch (Exception e) {
			// All other exception - means that writing PHDC message to NBS_interface did not succeed.
			String errString = "PHDCMessageBuilder.createNotificationMessage:  Exception caught: " + e.getMessage();
			logger.fatal(errString, e);
			writeEDXActivityLogRecord(NEDSSConstants.EDX_ACTIVITY_FAIL, errString, interfaceUid, notificationUid, notificationLocalId);
			throw new NEDSSAppException(errString);
		}
	}

	private void setCodedType(CodedType codedType, Coded coded) {
		codedType.setCode(coded.getCode());
		codedType.setCodeDescTxt(coded.getCodeDescription());
		codedType.setCodeSystemCode(coded.getCodeSystemCd());
	}

	private void addDiseaseSpecificParticipants(ParticipantsType participantsType, Long publicHealthCaseUid) {
		NotificationMessageDAOImpl notificationMessageDAOImpl = new NotificationMessageDAOImpl();
		Collection<Object> entityParticipationCollection = notificationMessageDAOImpl.getPHCREntityParticipations(publicHealthCaseUid);
		Iterator<Object> entityIter = entityParticipationCollection.iterator();

		while (entityIter.hasNext()) {
			NotificationParticipationDT entityParticipation = (NotificationParticipationDT)entityIter.next();
			// Skip the "standard" orgs/providers, they've already been handled by metadata
			if (!entityParticipation.getTypeCd().equalsIgnoreCase("HospOfADT") &&
				!entityParticipation.getTypeCd().equalsIgnoreCase("InvestgrOfPHC") &&
				!entityParticipation.getTypeCd().equalsIgnoreCase("OrgAsReporterOfPHC") &&
				!entityParticipation.getTypeCd().equalsIgnoreCase("PerAsReporterOfPHC") &&
				!entityParticipation.getTypeCd().equalsIgnoreCase("PhysicianOfPHC") &&
				!entityParticipation.getTypeCd().equalsIgnoreCase("SubjOfPHC")) {

				//Used for setting Identifiers
				IdentifiersType identifiers = null;
				String localId = "";
				Collection<Object> entityIdCollection = null;

				if (entityParticipation.getSubjectClassCd().equalsIgnoreCase(NEDSSConstants.PERSON)) {
					//Process Provider
					PersonRootDAOImpl personRootDAO = new PersonRootDAOImpl();
					PersonVO providerVO = (PersonVO)personRootDAO.loadObject(entityParticipation.getSubjectEntityUID());
					localId = providerVO.getThePersonDT().getLocalId();
					entityIdCollection = providerVO.getTheEntityIdDTCollection();

					ProviderParticipantType provider = participantsType.addNewProvider();
					identifiers = provider.addNewIdentifiers();

					//Lookup participation type code with code_set_group_id = 2080
					Coded provTypeCodeLookup = new Coded(entityParticipation.getTypeCd(),new Long(2080));
					CodedType providerTypeCd = provider.addNewTypeCd();
					setCodedType(providerTypeCd,provTypeCodeLookup);

					boolean found = false;

					//Find legal name, name_use_cd = "L"
					Iterator<Object> nameIter = providerVO.getThePersonNameDTCollection().iterator();
					while (nameIter.hasNext() && !found) {
						PersonNameDT personNameDT = (PersonNameDT)nameIter.next();
						if (personNameDT.getNmUseCd().equalsIgnoreCase(NEDSSConstants.LEGAL_NAME)) {
							found = true;
							ProviderNameType providerName = provider.addNewName();
							if (personNameDT.getFirstNm() != null)
								providerName.setFirst(personNameDT.getFirstNm());
							if (personNameDT.getLastNm() != null)
								providerName.setLast(personNameDT.getLastNm());

							if (personNameDT.getNmDegree() != null && personNameDT.getNmDegree().trim().length() > 0) {
								//Lookup degree code with code_set_group_id = 1980
								Coded degreeLookup = new Coded(personNameDT.getNmDegree(),new Long(1980));
								providerName.addNewDegree();
								setCodedType(providerTypeCd,degreeLookup);
							}
						}
					}

					found = false;
					//Find work place address, use_cd = "WP"
					Iterator<Object> addressIter = providerVO.getTheEntityLocatorParticipationDTCollection().iterator();
					while (addressIter.hasNext() && !found) {
						EntityLocatorParticipationDT entityLocatorParticipationDT = (EntityLocatorParticipationDT)addressIter.next();
						if (entityLocatorParticipationDT.getClassCd() != null &&
						    entityLocatorParticipationDT.getClassCd().equalsIgnoreCase(NEDSSConstants.POSTAL) &&
						    entityLocatorParticipationDT.getUseCd() != null &&
						    entityLocatorParticipationDT.getUseCd().equalsIgnoreCase(NEDSSConstants.WORK_PLACE)) {

							PostalLocatorDT postalLocatorDT = entityLocatorParticipationDT.getThePostalLocatorDT();
							found = true;

							PostalAddressType postalAddress = provider.addNewPostalAddress();
							if (postalLocatorDT.getStreetAddr1() != null)
								postalAddress.setStreetAddressOne(postalLocatorDT.getStreetAddr1());
							if (postalLocatorDT.getStreetAddr2() != null)
								postalAddress.setStreetAddressTwo(postalLocatorDT.getStreetAddr2());
							if (postalLocatorDT.getCityCd() != null)
								postalAddress.setCity(postalLocatorDT.getCityCd());
							if (postalLocatorDT.getZipCd() != null)
								postalAddress.setZipCode(postalLocatorDT.getZipCd());

							if (postalLocatorDT.getStateCd() != null) {
								//Lookup state code with code_set_group_id = 3920
								Coded stateLookup = new Coded(postalLocatorDT.getStateCd(),new Long(3920));
								CodedType state = postalAddress.addNewState();
								setCodedType(state,stateLookup);
							}

							if (postalLocatorDT.getCityCd() != null) {
								//Lookup city code with code_set_group_id = 560
								Coded countyLookup = new Coded(postalLocatorDT.getCityCd(),new Long(560));
								CodedType county = postalAddress.addNewCounty();
								setCodedType(county,countyLookup);
							}
						}
					}

					found = false;
					//Find work phone, use_cd = "WP"
					Iterator<Object> teleIter = providerVO.getTheEntityLocatorParticipationDTCollection().iterator();
					while (teleIter.hasNext() && !found) {
						EntityLocatorParticipationDT entityLocatorParticipationDT = (EntityLocatorParticipationDT)teleIter.next();
						if (entityLocatorParticipationDT.getClassCd() != null &&
						    entityLocatorParticipationDT.getClassCd().equalsIgnoreCase(NEDSSConstants.TELE) &&
						    entityLocatorParticipationDT.getUseCd() != null &&
						    entityLocatorParticipationDT.getUseCd().equalsIgnoreCase(NEDSSConstants.WORK_PHONE)) {

							TeleLocatorDT teleLocatorDT = entityLocatorParticipationDT.getTheTeleLocatorDT();

							TelephoneType telephone = provider.addNewTelephone();
							if (teleLocatorDT.getPhoneNbrTxt() != null)
								telephone.setNumber(teleLocatorDT.getPhoneNbrTxt());
							if (teleLocatorDT.getExtensionTxt() != null)
								telephone.setExtension(teleLocatorDT.getExtensionTxt());
							found = true;
						}
					}
				} else if (entityParticipation.getSubjectClassCd().equalsIgnoreCase(NEDSSConstants.ORGANIZATION)) {
					//Process Organization
					OrganizationRootDAOImpl organizationRootDAO = new OrganizationRootDAOImpl();
					OrganizationVO organizationVO = (OrganizationVO)organizationRootDAO.loadObject(entityParticipation.getSubjectEntityUID());
					localId = organizationVO.getTheOrganizationDT().getLocalId();
					entityIdCollection = organizationVO.getTheEntityIdDTCollection();

					OrganizationParticipantType organization = participantsType.addNewOrganization();
					identifiers = organization.addNewIdentifiers();

					//Lookup participation type code with code_set_group_id = 2080
					Coded organizationTypeLookup = new Coded(entityParticipation.getTypeCd(),new Long(2080));
					CodedType organizationTypeCd = organization.addNewTypeCd();
					setCodedType(organizationTypeCd,organizationTypeLookup);

					boolean found = false;

					//Find legal name, name_use_cd = "L"
					Iterator<Object> nameIter = organizationVO.getTheOrganizationNameDTCollection().iterator();
					while (nameIter.hasNext() && !found) {
						OrganizationNameDT organizationNameDT = (OrganizationNameDT)nameIter.next();
						if (organizationNameDT.getNmUseCd().equalsIgnoreCase(NEDSSConstants.LEGAL_NAME)) {
							found = true;
							if (organizationNameDT.getNmTxt() != null)
								organization.setName(organizationNameDT.getNmTxt());
						}
						found = true;
					}

					found = false;
					//Find work place address, use_cd = "WP"
					Iterator<Object> addressIter = organizationVO.getTheEntityLocatorParticipationDTCollection().iterator();
					while (addressIter.hasNext() && !found) {
						EntityLocatorParticipationDT entityLocatorParticipationDT = (EntityLocatorParticipationDT)addressIter.next();
						if (entityLocatorParticipationDT.getClassCd() != null &&
						    entityLocatorParticipationDT.getClassCd().equalsIgnoreCase(NEDSSConstants.POSTAL) &&
						    entityLocatorParticipationDT.getUseCd() != null &&
						    entityLocatorParticipationDT.getUseCd().equalsIgnoreCase(NEDSSConstants.WORK_PLACE)) {

							PostalLocatorDT postalLocatorDT = entityLocatorParticipationDT.getThePostalLocatorDT();
							found = true;

							PostalAddressType postalAddress = organization.addNewPostalAddress();
							if (postalLocatorDT.getStreetAddr1() != null)
								postalAddress.setStreetAddressOne(postalLocatorDT.getStreetAddr1());
							if (postalLocatorDT.getStreetAddr2() != null)
								postalAddress.setStreetAddressTwo(postalLocatorDT.getStreetAddr2());
							if (postalLocatorDT.getCityCd() != null)
								postalAddress.setCity(postalLocatorDT.getCityCd());
							if (postalLocatorDT.getZipCd() != null)
								postalAddress.setZipCode(postalLocatorDT.getZipCd());

							if (postalLocatorDT.getStateCd() != null) {
								//Lookup state code with code_set_group_id = 3920
								Coded stateLookup = new Coded(postalLocatorDT.getStateCd(),new Long(3920));
								CodedType state = postalAddress.addNewState();
								setCodedType(state,stateLookup);
							}

							if (postalLocatorDT.getCityCd() != null) {
								//Lookup city code with code_set_group_id = 560
								Coded countyLookup = new Coded(postalLocatorDT.getCityCd(),new Long(560));
								CodedType county = postalAddress.addNewCounty();
								setCodedType(county,countyLookup);
							}
						}
					}

					found = false;
					//Find work phone, use_cd = "WP"
					Iterator<Object> teleIter = organizationVO.getTheEntityLocatorParticipationDTCollection().iterator();
					while (teleIter.hasNext() && !found) {
						EntityLocatorParticipationDT entityLocatorParticipationDT = (EntityLocatorParticipationDT)teleIter.next();
						if (entityLocatorParticipationDT.getClassCd() != null &&
						    entityLocatorParticipationDT.getClassCd().equalsIgnoreCase(NEDSSConstants.TELE) &&
						    entityLocatorParticipationDT.getUseCd() != null &&
						    entityLocatorParticipationDT.getUseCd().equalsIgnoreCase(NEDSSConstants.WORK_PHONE)) {

							TeleLocatorDT teleLocatorDT = entityLocatorParticipationDT.getTheTeleLocatorDT();

							TelephoneType telephone = organization.addNewTelephone();
							if (teleLocatorDT.getPhoneNbrTxt() != null)
								telephone.setNumber(teleLocatorDT.getPhoneNbrTxt());
							if (teleLocatorDT.getExtensionTxt() != null)
								telephone.setExtension(teleLocatorDT.getExtensionTxt());
							found = true;
						}
					}
				}

				//add identifiers

				//NBS Local ID as a Local Registry Identifer ("LR")
				IdentifierType localIdentifier = identifiers.addNewIdentifier();
				localIdentifier.setIDNumber(localId);
				localIdentifier.setIDTypeCode("LR");

				HierarchicalDesignationType assigningFacility = localIdentifier.addNewAssigningFacility();
				assigningFacility.setNamespaceID(propertyUtil.getMsgSendingFacility());
				assigningFacility.setUniversalID(propertyUtil.getMsgSendingFacilityOID());
				assigningFacility.setUniversalIDType("ISO");

				HierarchicalDesignationType assigningAuthority = localIdentifier.addNewAssigningAuthority();
				assigningAuthority.setNamespaceID(propertyUtil.getMsgSendingApplication());
				assigningAuthority.setUniversalID(propertyUtil.getMsgSendingApplicationOID());
				assigningAuthority.setUniversalIDType("ISO");

				// Add Id's from Entity Collection
				if (entityIdCollection != null && entityIdCollection.size() > 0) {
					addEntityIds(identifiers, entityIdCollection);
				}
			}
		}


	}

	private String buildMessage(Collection<Object> caseNotificationDataDTCollection, Collection<Object> diseaseSpecificCaseDataDTCollection, NotificationDT notificationDT,
								Long publicHealthCaseUid, NBSSecurityObj nbsSecurityObj, TreeMap<Object, Object> phcEntityParticipationsMap) {

		messageBuilderHelper.populateUnits(caseNotificationDataDTCollection);
		TreeMap<Object, Object> condAndFormCdTreeMap = CachedDropDowns
				.getConditionCdAndInvFormCd();
		
		String InvestigationFormCd = condAndFormCdTreeMap.get(notificationDT.getCaseConditionCd()).toString();
		
		//For TB and Varicella conditions, the question needs to be stored and retrieved separately, not a single observation as in page builder conditions
		if(InvestigationFormCd!=null & !(InvestigationFormCd.equals(NEDSSConstants.INV_FORM_RVCT)|| InvestigationFormCd.equals(NEDSSConstants.INV_FORM_VAR)))
			messageBuilderHelper.populateUnits(diseaseSpecificCaseDataDTCollection);

		return createPHDCCaseReportMessageXML(notificationDT, caseNotificationDataDTCollection, diseaseSpecificCaseDataDTCollection, publicHealthCaseUid, nbsSecurityObj, phcEntityParticipationsMap);
	}


	private Collection<Object>  addPHDCHeaderAndCaseSectionHeaderInfo(Collection<Object> caseNotificationDataDTCollection,
										 					 NotificationDT notificationDT,
										 					 String nndEntityIdentifier,
										 					 String publicHealthCaseLocalId) {

        CachedDropDownValues cdv = new CachedDropDownValues();

        CaseNotificationDAOImpl caseNotificationDAOImpl = new CaseNotificationDAOImpl();
        ExportReceivingFacilityDT exportReceivingFacilityDT = caseNotificationDAOImpl.getReceivingFacility(notificationDT.getExportReceivingFacilityUid());


		CaseNotificationDataDT phdcMessageType = new CaseNotificationDataDT();
		CaseNotificationDataDT phdcVersionID = new CaseNotificationDataDT();
		CaseNotificationDataDT phdcProcessingID = new CaseNotificationDataDT();
		CaseNotificationDataDT phdcAddTime = new CaseNotificationDataDT();
		CaseNotificationDataDT phdcResultStatus = new CaseNotificationDataDT();
		CaseNotificationDataDT phdcMsgControlId = new CaseNotificationDataDT();
		CaseNotificationDataDT phdcSendingApplicationNamespaceID = new CaseNotificationDataDT();
		CaseNotificationDataDT phdcSendingApplicationUniversalID = new CaseNotificationDataDT();
		CaseNotificationDataDT phdcSendingApplicationUniversalIDType = new CaseNotificationDataDT();
		CaseNotificationDataDT phdcSendingFacilityNamespaceID = new CaseNotificationDataDT();
		CaseNotificationDataDT phdcSendingFacilityUniversalID = new CaseNotificationDataDT();
		CaseNotificationDataDT phdcSendingFacilityUniversalIDType = new CaseNotificationDataDT();
		CaseNotificationDataDT phdcReceivingApplicationNamespaceID = new CaseNotificationDataDT();
		CaseNotificationDataDT phdcReceivingApplicationUniversalID = new CaseNotificationDataDT();
		CaseNotificationDataDT phdcReceivingApplicationUniversalIDType = new CaseNotificationDataDT();
		CaseNotificationDataDT phdcReceivingFacilityNamespaceID = new CaseNotificationDataDT();
		CaseNotificationDataDT phdcReceivingFacilityUniversalID = new CaseNotificationDataDT();
		CaseNotificationDataDT phdcReceivingFacilityUniversalIDType = new CaseNotificationDataDT();

		CaseNotificationDataDT phdcCaseHeaderDocumentType = new CaseNotificationDataDT();
		CaseNotificationDataDT phdcCaseHeaderPurpose = new CaseNotificationDataDT();
		CaseNotificationDataDT phdcCaseHeaderDescription = new CaseNotificationDataDT();
		CaseNotificationDataDT phdcCaseHeaderSendingApplicationEventIdentifier= new CaseNotificationDataDT();

		// Message Type
		phdcMessageType.setAnswerTxt("PHDC");
		phdcMessageType.setCodedValue("PHDC");
		phdcMessageType.setCodedValueDescription("Public Health Document Container");
		phdcMessageType.setCodedValueCodingSystem(propertyUtil.getMsgSendingApplicationOID());
		phdcMessageType.setDataType(NEDSSConstants.DATATYPE_CODED);
		phdcMessageType.setXmlDataType("nbs:CodedType");
		phdcMessageType.setXmlPath("Header");
		phdcMessageType.setXmlTag("MessageType");
		caseNotificationDataDTCollection.add(phdcMessageType);

		// Version ID - Current hard coded here for now until we come up with a document property table
		phdcVersionID.setAnswerTxt("1.0");
		phdcVersionID.setDataType(NEDSSConstants.DATATYPE_TEXT);
		phdcVersionID.setXmlDataType("xs:string");
		phdcVersionID.setXmlPath("Header");
		phdcVersionID.setXmlTag("VersionID");
		caseNotificationDataDTCollection.add(phdcVersionID);

		// Processing ID - Use the same processing id that NND uses
		phdcProcessingID.setAnswerTxt(propertyUtil.getMsgProcessingId());
		phdcProcessingID.setDataType(NEDSSConstants.DATATYPE_TEXT);
		phdcProcessingID.setXmlDataType("xs:string");
		phdcProcessingID.setXmlPath("Header");
		phdcProcessingID.setXmlTag("ProcessingID");
		caseNotificationDataDTCollection.add(phdcProcessingID);

		// Message Control ID - Notification local id
		phdcMsgControlId.setAnswerTxt(notificationDT.getLocalId());
		phdcMsgControlId.setDataType(NEDSSConstants.DATATYPE_TEXT);
		phdcMsgControlId.setXmlDataType("xs:string");
		phdcMsgControlId.setXmlPath("Header");
		phdcMsgControlId.setXmlTag("MessageControlID");
		caseNotificationDataDTCollection.add(phdcMsgControlId);

		// Sending Application - from NEDSS.properties - NamespaceID
		phdcSendingApplicationNamespaceID.setAnswerTxt(propertyUtil.getMsgSendingApplication());
		phdcSendingApplicationNamespaceID.setDataType(NEDSSConstants.DATATYPE_TEXT);
		phdcSendingApplicationNamespaceID.setXmlDataType("xs:string");
		phdcSendingApplicationNamespaceID.setXmlPath("Header.SendingApplication");
		phdcSendingApplicationNamespaceID.setXmlTag("NamespaceID");
		caseNotificationDataDTCollection.add(phdcSendingApplicationNamespaceID);

		// Sending Application - from NEDSS.properties - UniversalID
		phdcSendingApplicationUniversalID.setAnswerTxt(propertyUtil.getMsgSendingApplicationOID());
		phdcSendingApplicationUniversalID.setDataType(NEDSSConstants.DATATYPE_TEXT);
		phdcSendingApplicationUniversalID.setXmlDataType("xs:string");
		phdcSendingApplicationUniversalID.setXmlPath("Header.SendingApplication");
		phdcSendingApplicationUniversalID.setXmlTag("UniversalID");
		caseNotificationDataDTCollection.add(phdcSendingApplicationUniversalID);

		// Sending Application - from NEDSS.properties - UniversalIDType
		phdcSendingApplicationUniversalIDType.setAnswerTxt("ISO");
		phdcSendingApplicationUniversalIDType.setDataType(NEDSSConstants.DATATYPE_TEXT);
		phdcSendingApplicationUniversalIDType.setXmlDataType("xs:string");
		phdcSendingApplicationUniversalIDType.setXmlPath("Header.SendingApplication");
		phdcSendingApplicationUniversalIDType.setXmlTag("UniversalIDType");
		caseNotificationDataDTCollection.add(phdcSendingApplicationUniversalIDType);

		// Sending Facility - from NEDSS.properties - NamespaceID
		phdcSendingFacilityNamespaceID.setAnswerTxt(propertyUtil.getMsgSendingFacility());
		phdcSendingFacilityNamespaceID.setDataType(NEDSSConstants.DATATYPE_TEXT);
		phdcSendingFacilityNamespaceID.setXmlDataType("xs:string");
		phdcSendingFacilityNamespaceID.setXmlPath("Header.SendingFacility");
		phdcSendingFacilityNamespaceID.setXmlTag("NamespaceID");
		caseNotificationDataDTCollection.add(phdcSendingFacilityNamespaceID);

		// Sending Facility - from NEDSS.properties - UniversalID
		phdcSendingFacilityUniversalID.setAnswerTxt(propertyUtil.getMsgSendingFacilityOID());
		phdcSendingFacilityUniversalID.setDataType(NEDSSConstants.DATATYPE_TEXT);
		phdcSendingFacilityUniversalID.setXmlDataType("xs:string");
		phdcSendingFacilityUniversalID.setXmlPath("Header.SendingFacility");
		phdcSendingFacilityUniversalID.setXmlTag("UniversalID");
		caseNotificationDataDTCollection.add(phdcSendingFacilityUniversalID);

		// Sending Facility - from NEDSS.properties - UniversalIDType
		phdcSendingFacilityUniversalIDType.setAnswerTxt("ISO");
		phdcSendingFacilityUniversalIDType.setDataType(NEDSSConstants.DATATYPE_TEXT);
		phdcSendingFacilityUniversalIDType.setXmlDataType("xs:string");
		phdcSendingFacilityUniversalIDType.setXmlPath("Header.SendingFacility");
		phdcSendingFacilityUniversalIDType.setXmlTag("UniversalIDType");
		caseNotificationDataDTCollection.add(phdcSendingFacilityUniversalIDType);

		// Receiving Application - from Export_receiving_facility table - NamespaceID
		phdcReceivingApplicationNamespaceID.setAnswerTxt(exportReceivingFacilityDT.getReceivingSystemShortName());
		phdcReceivingApplicationNamespaceID.setDataType(NEDSSConstants.DATATYPE_TEXT);
		phdcReceivingApplicationNamespaceID.setXmlDataType("xs:string");
		phdcReceivingApplicationNamespaceID.setXmlPath("Header.ReceivingApplication");
		phdcReceivingApplicationNamespaceID.setXmlTag("NamespaceID");
		caseNotificationDataDTCollection.add(phdcReceivingApplicationNamespaceID);

		// Receiving Application - from Export_receiving_facility table - UniversalID
		phdcReceivingApplicationUniversalID.setAnswerTxt(exportReceivingFacilityDT.getReceivingSystemOid());
		phdcReceivingApplicationUniversalID.setDataType(NEDSSConstants.DATATYPE_TEXT);
		phdcReceivingApplicationUniversalID.setXmlDataType("xs:string");
		phdcReceivingApplicationUniversalID.setXmlPath("Header.ReceivingApplication");
		phdcReceivingApplicationUniversalID.setXmlTag("UniversalID");
		caseNotificationDataDTCollection.add(phdcReceivingApplicationUniversalID);

		// Receiving Application - from Export_receiving_facility table - UniversalIDType
		phdcReceivingApplicationUniversalIDType.setAnswerTxt("ISO");
		phdcReceivingApplicationUniversalIDType.setDataType(NEDSSConstants.DATATYPE_TEXT);
		phdcReceivingApplicationUniversalIDType.setXmlDataType("xs:string");
		phdcReceivingApplicationUniversalIDType.setXmlPath("Header.ReceivingApplication");
		phdcReceivingApplicationUniversalIDType.setXmlTag("UniversalIDType");
		caseNotificationDataDTCollection.add(phdcReceivingApplicationUniversalIDType);

		// Receiving Facility - from Export_receiving_facility table - NamespaceID
		phdcReceivingFacilityNamespaceID.setAnswerTxt(exportReceivingFacilityDT.getReceivingSystemOwner());
		phdcReceivingFacilityNamespaceID.setDataType(NEDSSConstants.DATATYPE_TEXT);
		phdcReceivingFacilityNamespaceID.setXmlDataType("xs:string");
		phdcReceivingFacilityNamespaceID.setXmlPath("Header.ReceivingFacility");
		phdcReceivingFacilityNamespaceID.setXmlTag("NamespaceID");
		caseNotificationDataDTCollection.add(phdcReceivingFacilityNamespaceID);

		// Receiving Facility - from Export_receiving_facility table - UniversalID
		phdcReceivingFacilityUniversalID.setAnswerTxt(exportReceivingFacilityDT.getReceivingSystemOwnerOid());
		phdcReceivingFacilityUniversalID.setDataType(NEDSSConstants.DATATYPE_TEXT);
		phdcReceivingFacilityUniversalID.setXmlDataType("xs:string");
		phdcReceivingFacilityUniversalID.setXmlPath("Header.ReceivingFacility");
		phdcReceivingFacilityUniversalID.setXmlTag("UniversalID");
		caseNotificationDataDTCollection.add(phdcReceivingFacilityUniversalID);

		// Receiving Facility - from Export_receiving_facility table - UniversalIDType
		phdcReceivingFacilityUniversalIDType.setAnswerTxt("ISO");
		phdcReceivingFacilityUniversalIDType.setDataType(NEDSSConstants.DATATYPE_TEXT);
		phdcReceivingFacilityUniversalIDType.setXmlDataType("xs:string");
		phdcReceivingFacilityUniversalIDType.setXmlPath("Header.ReceivingFacility");
		phdcReceivingFacilityUniversalIDType.setXmlTag("UniversalIDType");
		caseNotificationDataDTCollection.add(phdcReceivingFacilityUniversalIDType);

		// ResultStatus - derived from autoresend flag from Notification
		phdcResultStatus.setAnswerTxt(messageBuilderHelper.getResultStatus(notificationDT));
		phdcResultStatus.setCodesetGroupId(new Long(4370));
		phdcResultStatus.setDataType(NEDSSConstants.DATATYPE_CODED);
		phdcResultStatus.setXmlDataType("nbs:CodedType");
		phdcResultStatus.setXmlPath("Header");
		phdcResultStatus.setXmlTag("ResultStatus");
		caseNotificationDataDTCollection.add(phdcResultStatus);

		// CreationTime - current system time
		phdcAddTime.setAnswerTxt(notificationDT.getAddTime().toString());
		phdcAddTime.setDataType(NEDSSConstants.DATATYPE_DATE);
		phdcAddTime.setXmlDataType("xs:dateTime");
		phdcAddTime.setXmlPath("Header");
		phdcAddTime.setXmlTag("CreationTime");
		caseNotificationDataDTCollection.add(phdcAddTime);

		// phdcCaseHeaderDocumentType
		phdcCaseHeaderDocumentType.setAnswerTxt(NEDSSConstants.PHC_236); //Case Report
		phdcCaseHeaderDocumentType.setCodesetGroupId(new Long(3580));
		phdcCaseHeaderDocumentType.setDataType(NEDSSConstants.DATATYPE_CODED);
		phdcCaseHeaderDocumentType.setXmlDataType("nbs:CodedType");
		phdcCaseHeaderDocumentType.setXmlPath("Case.SectionHeader");
		phdcCaseHeaderDocumentType.setXmlTag("DocumentType");
		caseNotificationDataDTCollection.add(phdcCaseHeaderDocumentType);

		// phdcCaseHeaderPurpose
		phdcCaseHeaderPurpose.setAnswerTxt(notificationDT.getCd());
		phdcCaseHeaderPurpose.setCodesetGroupId(new Long(4360));
		phdcCaseHeaderPurpose.setDataType(NEDSSConstants.DATATYPE_CODED);
		phdcCaseHeaderPurpose.setXmlDataType("nbs:CodedType");
		phdcCaseHeaderPurpose.setXmlPath("Case.SectionHeader");
		phdcCaseHeaderPurpose.setXmlTag("Purpose");
		caseNotificationDataDTCollection.add(phdcCaseHeaderPurpose);

		// phdcCaseHeaderDescription
		phdcCaseHeaderDescription.setAnswerTxt("Case Report:"+cdv.getConditionDesc(notificationDT.getCaseConditionCd()));
		phdcCaseHeaderDescription.setDataType(NEDSSConstants.DATATYPE_TEXT);
		phdcCaseHeaderDescription.setXmlDataType("xs:string");
		phdcCaseHeaderDescription.setXmlPath("Case.SectionHeader");
		phdcCaseHeaderDescription.setXmlTag("Description");
		caseNotificationDataDTCollection.add(phdcCaseHeaderDescription);

		// phdcCaseHeaderSendingApplicationEventIdentifier
		phdcCaseHeaderSendingApplicationEventIdentifier.setAnswerTxt(publicHealthCaseLocalId);
		phdcCaseHeaderSendingApplicationEventIdentifier.setDataType(NEDSSConstants.DATATYPE_TEXT);
		phdcCaseHeaderSendingApplicationEventIdentifier.setXmlDataType("xs:string");
		phdcCaseHeaderSendingApplicationEventIdentifier.setXmlPath("Case.SectionHeader");
		phdcCaseHeaderSendingApplicationEventIdentifier.setXmlTag("SendingApplicationEventIdentifier");
		caseNotificationDataDTCollection.add(phdcCaseHeaderSendingApplicationEventIdentifier);

		return caseNotificationDataDTCollection;
	}

	private String createPHDCCaseReportMessageXML(NotificationDT notificationDT, Collection<Object> caseNotificationDataDTCollection, Collection<Object> diseaseSpecificCaseDataDTCollection,
			          							  Long publicHealthCaseUid, NBSSecurityObj nbsSecurityObj, TreeMap<Object, Object> phcEntityParticipationsMap) throws NEDSSSystemException {
		// Create new Public Health Document Container Message for Case data
		ContainerDocument phdcMessageDoc = ContainerDocument.Factory.newInstance();

		// Create Container and the Header and Case sections
		Container container = phdcMessageDoc.addNewContainer();

		container.addNewHeader();
		CaseType caseElement = container.addNewCase();
		caseElement.addNewPatient();

		Long patientUid = null;
		
		if (phcEntityParticipationsMap.containsKey(NEDSSConstants.PHC_PATIENT)) {
			patientUid = (Long)phcEntityParticipationsMap.get(NEDSSConstants.PHC_PATIENT);
		} else {
			String errString = "PHDCMessageBuilder.createPHDCCaseReportMessageXML:  Missing Patient uid in phcEntityParticipationsMap.";
			logger.error(errString);
			throw new NEDSSSystemException(errString);
		}
		
		if (caseNotificationDataDTCollection  != null) {
			// Sort by part_type_cd and xml_path so we can group complex types in one shot
			List<Object> list = (ArrayList<Object> )caseNotificationDataDTCollection;
			Collections.sort(list, CaseNotificationDataDT.PHDCSort);

			Iterator<Object> it = caseNotificationDataDTCollection.iterator();

			CaseNotificationDataDT caseNotificationDataDT = null;

			if ((it.hasNext()))
				caseNotificationDataDT = (CaseNotificationDataDT)it.next();

			while (caseNotificationDataDT != null) {
				Collection<Object>  groupedElements = new ArrayList<Object> ();

				String xmlGroupKey = messageBuilderHelper.getXMLGroupingKey(caseNotificationDataDT);

				// See if there are a group of CaseNotificationDataDT's that we should group together
				// to make up the same complex type - so we can set them all at once.
				// Items not in such groups will be set one by one by the same routine.
				while (caseNotificationDataDT != null && xmlGroupKey.equalsIgnoreCase(messageBuilderHelper.getXMLGroupingKey(caseNotificationDataDT))) {
					// Need to make sure this is not a "unit" question, we don't want to create a new
					// element because it will get added in the "parent's" element. The parent is the
					// question that contains the value that the unit qualifies.
					if (caseNotificationDataDT.getUnitParentIdentifier() == null || caseNotificationDataDT.getUnitParentIdentifier().trim().length() == 0)
						groupedElements.add(caseNotificationDataDT);

					// Get next item in caseNotificationDataDTCollection
					if ((it.hasNext()))
						caseNotificationDataDT = (CaseNotificationDataDT)it.next();
					else
						caseNotificationDataDT = null;
				}

				// Populated XML Structure using grouped element collection
				if (groupedElements != null && groupedElements.size() > 0) {
					container = populatePHDCMessageDocWithElement(container, groupedElements, patientUid);
				}

				// Now return to top level while and process the next item from
				// caseNotificationDataDTCollection  (if there is one)
			}

			// Add Disease Specific Questions
			if (diseaseSpecificCaseDataDTCollection  != null && diseaseSpecificCaseDataDTCollection.size() > 0) {
				// Sort by questionGroupSeqNbr and answerGroupSeqNbr which define groups of repeating questions/answers
				List<Object> diseaseSpecificList = (ArrayList<Object> )diseaseSpecificCaseDataDTCollection;
				Collections.sort(diseaseSpecificList, CaseNotificationDataDT.PHDCDiseaseSpecificSort);

				DiseaseSpecificQuestionsType diseaseSpecificQuestions = container.getCase().addNewDiseaseSpecificQuestions();

				Iterator<Object> it2 = diseaseSpecificCaseDataDTCollection.iterator();

				CaseNotificationDataDT diseaseSpecificCaseNotificationDataDT = null;

				if ((it2.hasNext()))
					diseaseSpecificCaseNotificationDataDT = (CaseNotificationDataDT)it2.next();

				CaseNotificationDataDT previousDiseaseSpecificCaseNotificationDataDT = null;
				ObservationType observation = null;

				while (diseaseSpecificCaseNotificationDataDT != null) {
					boolean multiSelect = false;

					if (previousDiseaseSpecificCaseNotificationDataDT != null) {
						//Check if we are in a multiselect set of answers
						//First see if they if they have same Question Identifier
						if (previousDiseaseSpecificCaseNotificationDataDT.getQuestionIdentifier().equalsIgnoreCase(diseaseSpecificCaseNotificationDataDT.getQuestionIdentifier())) {
							//Check if both are in Question groups
							if ( previousDiseaseSpecificCaseNotificationDataDT.getQuestionGroupSeqNbr() != null &&
								 diseaseSpecificCaseNotificationDataDT.getQuestionGroupSeqNbr() != null ) {
								//If so, are then in the same, and if so, also in the same AnswerGroupSeqNbr?
								if (previousDiseaseSpecificCaseNotificationDataDT.getQuestionGroupSeqNbr().compareTo(diseaseSpecificCaseNotificationDataDT.getQuestionGroupSeqNbr()) == 0 &&
									previousDiseaseSpecificCaseNotificationDataDT.getAnswerGroupSeqNbr().compareTo(diseaseSpecificCaseNotificationDataDT.getAnswerGroupSeqNbr()) == 0 ) {
									//If so, common multiselections
									multiSelect = true;
								} else {
									//otherwise, not multiselects
									multiSelect = false;
								}
							} else {
								//Both not in repeating groups, but same identifier, so common multiselections
								multiSelect = true;
							}
						} else {
							//Not same QuestionIdentifiers, can't be common multiselections
							multiSelect = false;
						}
					}

					//No multiSelect (or first multiselect found), create new Observation
					if (multiSelect == false) {
						observation = diseaseSpecificQuestions.addNewObservation();
					}

					observation = populatePHDCMessageDocWithDiseaseSpecificElement(observation, diseaseSpecificCaseNotificationDataDT, multiSelect);
					previousDiseaseSpecificCaseNotificationDataDT = diseaseSpecificCaseNotificationDataDT;

					// Add Batch Sequence (for repeating questions)
					if (diseaseSpecificCaseNotificationDataDT.getQuestionGroupSeqNbr() != null && diseaseSpecificCaseNotificationDataDT.getQuestionGroupSeqNbr() >= 0 ) {
						observation.setBatchSequence(BigInteger.valueOf(diseaseSpecificCaseNotificationDataDT.getQuestionGroupSeqNbr()));
						if (diseaseSpecificCaseNotificationDataDT.getBatchTableHeader() != null)
							observation.setBatchTableHeader(diseaseSpecificCaseNotificationDataDT.getBatchTableHeader());
						if (diseaseSpecificCaseNotificationDataDT.getBatchTableColumnWidth() != null)
							observation.setBatchTableColumnWidth(diseaseSpecificCaseNotificationDataDT.getBatchTableColumnWidth());
					}

					// Get next item in diseaseSpecificCaseDataDTCollection
					if ((it2.hasNext()))
						diseaseSpecificCaseNotificationDataDT = (CaseNotificationDataDT)it2.next();
					else
						diseaseSpecificCaseNotificationDataDT = null;
				}
			}
		}

		//Add disease specific participants
		ParticipantsType participants = container.getCase().addNewParticipants();
		addDiseaseSpecificParticipants(participants, publicHealthCaseUid);

		//Add Notes (rolling notes) associated to Investigation
		addNotes(caseElement, publicHealthCaseUid);

		// Add Case's Lab Reports
		PHDCLabReportHelper phdcLabReportHelper = new PHDCLabReportHelper();
		phdcLabReportHelper.populatePHDCCaseWithLabReports(caseElement, publicHealthCaseUid, notificationDT, nbsSecurityObj);

		//Add schmemaLocation to XML
		XmlCursor cursor = phdcMessageDoc.newCursor();
		if (cursor.toFirstChild())
		{
		  cursor.setAttributeText(new QName("http://www.w3.org/2001/XMLSchema-instance","schemaLocation"), "http://www.cdc.gov/NEDSS PHDC.xsd");
		}
		cursor.dispose();


		// Set up the validation error listener.
		ArrayList<Object> validationErrors = new ArrayList<Object> ();
		XmlOptions validationOptions = new XmlOptions();
		validationOptions.setErrorListener(validationErrors);

		//Ready to create xml for PHDC Message - first need to validate it
		if (phdcMessageDoc.validate(validationOptions))
			return phdcMessageDoc.toString(); // Return XML for PHDC Message
		else {
			String errString = "PHDCMessageBuilder.createPHDCCaseReportMessageXML - Created message has failed validation against PHDC.xsd:";
		   Iterator<Object>  iter = validationErrors.iterator();
		    while (iter.hasNext()) {
		    	errString += "\n>> " + iter.next();
		    }
			logger.error(errString);
			//If message does not validate write to the failed Directory
			if(phdcMessageDoc!=null) {
				String patientIdentifier = "";
				if (caseElement.getPatient().getIdentifiers() != null && caseElement.getPatient().getIdentifiers().getIdentifierArray(0) != null)
					patientIdentifier = caseElement.getPatient().getIdentifiers().getIdentifierArray(0).getIDNumber();
				messageBuilderHelper.writeToFile(phdcMessageDoc.toString(), patientIdentifier, NNDConstantUtil.FAILED_CASE_REPORTS_ROOT_DIR);
			}
			throw new NEDSSSystemException(errString);
		}
	}


	private void addNotes(CaseType caseElement, Long actUid) {
		NBSAttachmentNoteDAOImpl nbsAttachmentNoteDAOImpl = new NBSAttachmentNoteDAOImpl();
		Collection<Object> notesCollection = nbsAttachmentNoteDAOImpl.getNbsNoteCollection(actUid);
		if (notesCollection != null && notesCollection.size() > 0) {
			NoteType notes = caseElement.addNewNotes();
			Iterator<Object> noteIter = notesCollection.iterator();
			while (noteIter.hasNext()) {
				String noteString = ((NBSNoteDT)noteIter.next()).getNote();
				notes.addNote(noteString);
			}
		}
	}

	private ObservationType populatePHDCMessageDocWithDiseaseSpecificElement(ObservationType observation, CaseNotificationDataDT diseaseSpecificCaseNotificationDataDT, boolean multiSelect) {
		//Create new observation to store this Disease Specific Case Answer

		try {
			String dataType = diseaseSpecificCaseNotificationDataDT.getDataType();

			if (dataType == null) {
				String errString = "PHDCMessageBuilder.populatePHDCMessageDocWithDiseaseSpecificElement - Missing data type for question identifer: " +
				diseaseSpecificCaseNotificationDataDT.getQuestionIdentifier();
				logger.error(errString);
				throw new NEDSSSystemException(errString);
			}

			//Populate Observation Question, if multiSelect, skip new question and will add answer to existing question to group multiselections
			if (!multiSelect) {
				CodedType observationQuestion = observation.addNewQuestion();
				observationQuestion.setCode(diseaseSpecificCaseNotificationDataDT.getQuestionIdentifier());
				observationQuestion.setCodeDescTxt(diseaseSpecificCaseNotificationDataDT.getNbsQuestionLabel());
				observationQuestion.setCodeSystemCode(diseaseSpecificCaseNotificationDataDT.getQuestionOID());
			}

			AnswerType observationAnswer = null;
			//If multiselect, reuse previous Answer to add another answer code
			if (multiSelect) {
				observationAnswer = observation.getAnswer();
			} else {
				observationAnswer = observation.addNewAnswer();
			}

			if (diseaseSpecificCaseNotificationDataDT.getAnswerGroupSeqNbr() != null) {
				observationAnswer.setAnswerSequence(BigInteger.valueOf(diseaseSpecificCaseNotificationDataDT.getAnswerGroupSeqNbr()));
			}

			//Populate Observation Answer (based on data type)
			if (dataType.equalsIgnoreCase(NEDSSConstants.DATATYPE_TEXT)) {
				observationAnswer.setAnswerText(diseaseSpecificCaseNotificationDataDT.getAnswerTxt());
			}
			else if (dataType.equalsIgnoreCase(NEDSSConstants.DATATYPE_NUMERIC)) {
				String structuredNumeric = diseaseSpecificCaseNotificationDataDT.getAnswerTxt();

				if (structuredNumeric == null) {
					String errString = "PHDCMessageBuilder.populatePHDCMessageDocWithDiseaseSpecificElement:  Structured numeric is missing, caseNotificationDataDT=" + diseaseSpecificCaseNotificationDataDT.toString();
					logger.error(errString);
					throw new NEDSSSystemException(errString);
				}

				NumericType observationNumericAnswer = observationAnswer.addNewAnswerNumeric();
				parseStructuredNumericUnit(diseaseSpecificCaseNotificationDataDT, observationNumericAnswer);

				parseStructuredNumeric(structuredNumeric, observationNumericAnswer);
			}
			else if (dataType.equalsIgnoreCase(NEDSSConstants.DATATYPE_DATE)) {
				observationAnswer.setAnswerDateTime(messageBuilderHelper.convertNBSDateToCalendar(diseaseSpecificCaseNotificationDataDT.getAnswerTxt()));
			}
			else if (dataType.equalsIgnoreCase(NEDSSConstants.DATATYPE_CODED)) {
				CodedType observationAnswerCoded = observationAnswer.addNewAnswerCode();
				if (diseaseSpecificCaseNotificationDataDT.getCodedValue() != null && diseaseSpecificCaseNotificationDataDT.getCodedValue().trim().length() > 0) {
					observationAnswerCoded.setCode(diseaseSpecificCaseNotificationDataDT.getCodedValue());
					observationAnswerCoded.setCodeDescTxt(diseaseSpecificCaseNotificationDataDT.getCodedValueDescription());
					observationAnswerCoded.setCodeSystemCode(diseaseSpecificCaseNotificationDataDT.getCodedValueCodingSystem());
				}
				if (diseaseSpecificCaseNotificationDataDT.getOriginalText() != null && diseaseSpecificCaseNotificationDataDT.getOriginalText().trim().length() > 0) {
					observationAnswerCoded.setText(diseaseSpecificCaseNotificationDataDT.getOriginalText());
				}

				if (diseaseSpecificCaseNotificationDataDT.getLocalCodedValue() != null && diseaseSpecificCaseNotificationDataDT.getLocalCodedValue().trim().length() > 0) {
					
					observationAnswerCoded.setCode(diseaseSpecificCaseNotificationDataDT.getLocalCodedValue());
					observationAnswerCoded.setCodeDescTxt(diseaseSpecificCaseNotificationDataDT.getLocalCodedValueDescription());
					observationAnswerCoded.setCodeSystemCode(diseaseSpecificCaseNotificationDataDT.getLocalCodedValueCodingSystem());
					
					observationAnswerCoded.setAlternateCode(diseaseSpecificCaseNotificationDataDT.getLocalCodedValue());
					observationAnswerCoded.setAlternateCodeDescTxt(diseaseSpecificCaseNotificationDataDT.getLocalCodedValueDescription());
					observationAnswerCoded.setAlternateCodeSystemCode(diseaseSpecificCaseNotificationDataDT.getLocalCodedValueCodingSystem());
				}
			}
			else {
				String errString = "PHDCMessageBuilder.populatePHDCMessageDocWithDiseaseSpecificElement - Invalid data type (" + dataType + ") for question identifer: " +
				diseaseSpecificCaseNotificationDataDT.getQuestionIdentifier();
				logger.error(errString);
				throw new NEDSSSystemException(errString);
			}
		} catch (Exception e) {
			String errString = "PHDCMessageBuilder.populatePHDCMessageDocWithDiseaseSpecificElement - For question identifier: " +
			diseaseSpecificCaseNotificationDataDT.getQuestionIdentifier() + ":  " + e.getMessage();
			logger.error(errString);
			throw new NEDSSSystemException(errString);
		}

		return observation;
	}


	private void parseStructuredNumericUnit(CaseNotificationDataDT caseNotificationDataDT, NumericType observationAnswerNumeric) {
		CodedType snUnit = null;
		String structuredNumeric = caseNotificationDataDT.getAnswerTxt();

		if (caseNotificationDataDT.getUnitTypeCd() != null && caseNotificationDataDT.getUnitTypeCd().equalsIgnoreCase(NEDSSConstants.CODED) ) {
			if (structuredNumeric != null && structuredNumeric.indexOf("^") > 0) {
				//This means we have a coded unit, need to do code lookup
				String code = structuredNumeric.split("\\^")[1];
				Long codeSetGroupId = Long.parseLong(caseNotificationDataDT.getUnitValue());
				Coded codeLookup = new Coded(code, codeSetGroupId);
				if (codeLookup == null || codeLookup.getCodeSystemCd() == null || codeLookup.getCodeSystemCd().trim().length() < 1) {
					String errString = "PHDCMessageBuilder.parseStructuredNumericUnit:  Unit code lookup failed.  CaseNotificationDT = " + caseNotificationDataDT.toString();
					logger.error(errString);
					throw new NEDSSSystemException(errString);
				}

				snUnit = observationAnswerNumeric.addNewUnit();
				if (codeLookup.getCodeSystemCd().equalsIgnoreCase(NEDSSConstants.NND_LOCALLY_CODED)) {
					snUnit.setCode(codeLookup.getCode());
					snUnit.setCodeDescTxt(codeLookup.getCodeDescription());
					snUnit.setCodeSystemCode(codeLookup.getCodeSystemCd());
					snUnit.setAlternateCode(codeLookup.getCode());
					snUnit.setAlternateCodeDescTxt(codeLookup.getCodeDescription());
					snUnit.setAlternateCodeSystemCode(codeLookup.getCodeSystemCd());
				} else {
					snUnit.setCode(codeLookup.getCode());
					snUnit.setCodeDescTxt(codeLookup.getCodeDescription());
					snUnit.setCodeSystemCode(codeLookup.getCodeSystemCd());
				}
			} else {
				String errString = "PHDCMessageBuilder.parseStructuredNumericUnit:  Unit code is missing.  CaseNotificationDT = " + caseNotificationDataDT.toString();
				logger.error(errString);
				throw new NEDSSSystemException(errString);
			}
		} else if (caseNotificationDataDT.getUnitTypeCd() != null && caseNotificationDataDT.getUnitTypeCd().equalsIgnoreCase(NEDSSConstants.LITERAL) ){
			//Literal, just parse into three parts - coded, value, codeSystem
			String literalCoding = caseNotificationDataDT.getUnitValue();
			boolean error = false;
			String code="";
			String codeDescription="";
			String codeSystemCode="";

			if (literalCoding != null && literalCoding.indexOf("^") > 0) {
				try {
					code = literalCoding.split("\\^")[0];
					codeDescription = literalCoding.split("\\^")[1];
					codeSystemCode = literalCoding.split("\\^")[2];
				} catch (Exception e) {
					// Problem parsing the three components
					error = true;
				}

				snUnit = observationAnswerNumeric.addNewUnit();
				if (codeSystemCode != null && codeSystemCode.equalsIgnoreCase(NEDSSConstants.NND_LOCALLY_CODED)) {
					snUnit.setCode(code);
					snUnit.setCodeDescTxt(codeDescription);
					snUnit.setCodeSystemCode(codeSystemCode);
					snUnit.setAlternateCode(code);
					snUnit.setAlternateCodeDescTxt(codeDescription);
					snUnit.setAlternateCodeSystemCode(codeSystemCode);
				} else {
					snUnit.setCode(code);
					snUnit.setCodeDescTxt(codeDescription);
					snUnit.setCodeSystemCode(codeSystemCode);
				}
			} else {
				error = true;
			}

			if (error) {
				String errString = "PHDCMessageBuilder.parseStructuredNumericUnit:  Unit code literal is invalid:  " + caseNotificationDataDT.getUnitValue() + " - CaseNotificationDT = " + caseNotificationDataDT.toString();
				logger.error(errString);
				throw new NEDSSSystemException(errString);
			}
		}
	}


	private void parseStructuredNumeric(String structuredNumeric, NumericType observationAnswerNumeric) {

		if (structuredNumeric.contains("^")) {
			structuredNumeric = structuredNumeric.split("\\^")[0];
		}

		// numeric = \\d*
		// comparator = [<>=][=>]?
		// separator = [/-:+]

		String num1 = null;
		String comparator = null;
		String num2 = null;
		String separatorSuffix = null;

		// [A number]
		if (structuredNumeric.matches("^[0-9]*\\.?[0-9]+$")) {
			num1 = structuredNumeric;
		} else
		// [Comparator][A number]
		if (structuredNumeric.matches("^[<>=]{1}[=>]?[0-9]*\\.?[0-9]+$")) {
			Pattern pattern = Pattern.compile("([<>=]{1}[=>]?)([0-9]*\\.?[0-9]+)");
		    Matcher matcher = pattern.matcher(structuredNumeric);

		    if(matcher.matches()) {
		    	comparator = matcher.group(1);
				num1 = matcher.group(2);
		    }
		} else
		// [A number][Separator/-:][A number]
		if (structuredNumeric.matches("^[0-9]*\\.?[0-9]+[/\\-:]{1}[0-9]*\\.?[0-9]+$")) {
			Pattern pattern = Pattern.compile("([0-9]*\\.?[0-9]+)([/\\-:]{1})([0-9]*\\.?[0-9]+)");
		    Matcher matcher = pattern.matcher(structuredNumeric);

		    if(matcher.matches()) {
				num1 = matcher.group(1);
				separatorSuffix = matcher.group(2);
				num2 = matcher.group(3);
		    }
		} else
		// [A number][Separator+]
		if (structuredNumeric.matches("^[0-9]*\\.?[0-9]+[+]{1}$")) {
			Pattern pattern = Pattern.compile("([0-9]*\\.?[0-9]+)([+]{1})");
		    Matcher matcher = pattern.matcher(structuredNumeric);

		    if(matcher.matches()) {
				num1 = matcher.group(1);
				separatorSuffix = matcher.group(2);
		    }
		} else
		// [Comparator][A number][Separator/-][A number]
		if (structuredNumeric.matches("^[<>=]{1}[=>]?[0-9]*\\.?[0-9]+[/\\-:][0-9]*\\.?[0-9]+$")) {
			Pattern pattern = Pattern.compile("([<>=]{1}[=>]?)([0-9]*\\.?[0-9]+)([/\\-:])([0-9]*\\.?[0-9]+)");
		    Matcher matcher = pattern.matcher(structuredNumeric);

		    if(matcher.matches()) {
				comparator = matcher.group(1);
				num1 = matcher.group(2);
				separatorSuffix = matcher.group(3);
				num2 = matcher.group(4);
		    }
		} else {
			String errString = "PHDCMessageBuilder.parseStructuredNumeric:  Unsupported SN format: " + structuredNumeric;
			logger.error(errString);
			throw new NEDSSSystemException(errString);
		}

		if (num1 != null) {
			observationAnswerNumeric.setValue1(Float.valueOf(num1.trim()).floatValue());
		} else {
			String errString = "PHDCMessageBuilder.parseStructuredNumeric:  Unsupported SN format: Does not contain Value1 value.";
			logger.error(errString);
			throw new NEDSSSystemException(errString);
		}
		if (num2 != null) {
			observationAnswerNumeric.setValue2(Float.valueOf(num2.trim()).floatValue());
		}
		if (comparator != null) {
			observationAnswerNumeric.setComparatorCode(comparator);
		}
		if (separatorSuffix != null) {
			observationAnswerNumeric.setSeperatorCode(separatorSuffix);
		}


	}


	private Container populatePHDCMessageDocWithElement(Container container, Collection<Object>  groupedElements, Long patientUid) {
		// Get xml path from first element (all elements in group assumed to have same path)
		CaseNotificationDataDT caseNotificationDataDT = (CaseNotificationDataDT)(groupedElements.iterator().next());
		String xmlPath = caseNotificationDataDT.getXmlPath();

		// Build list from XML path to navigate with reflection to element "level" to populate
		List<Object> list = new ArrayList<Object> ();
		while (xmlPath.indexOf(".") > -1) {
			list.add(xmlPath.split("\\.")[0]);
			xmlPath = xmlPath.split("\\.", 2)[1];
		}
		list.add(xmlPath);

		// Initialize result to Container class to start the xml path chain navigation
		Object result = container;


		try {
			for (int x = 0; x < list.size(); x++) {
				// Setup to get next level
				Class<?> cls = result.getClass();

				// Call get method on current bean instance to retrieve next level in xml path
				Method xmlBeanMethod = null;
				try {
					xmlBeanMethod = cls.getMethod("get" + (String)list.get(x),  (Class<?>[])null);
				} catch (Exception e) {
					//If unknown method, try addNew (may be dealing with an Array)
					xmlBeanMethod = cls.getMethod("addNew" + (String)list.get(x),  (Class<?>[])null);
				}

				Object previousInstance = result;
				result = xmlBeanMethod.invoke(result,  (Object[])null);

				if (result == null) {
					// The getter method didn't return an instance, so try the addNew method
					xmlBeanMethod = cls.getMethod("addNew" + (String)list.get(x),  (Class<?>[])null);
					result = xmlBeanMethod.invoke(previousInstance,  (Object[])null);

					if (result == null) {
						// Both the get and the addNew for this segment of the XMLPath failed to work
						// Probably bad meta data
						String errString = "PHDCMessageBuilder.populatePHDCMessageDocWithElement - reflection call in attempting to navigate XMLPath failed " +
								           " for question identifier: " + caseNotificationDataDT.getQuestionIdentifier();
						logger.error(errString);
						throw new NEDSSSystemException(errString);
					}
				}
			}

			// Now have a Class instance representing the element level to now populate
			// Iterate through groupedElements collection and populate XML with each element
			// Need to determine data type to see if we can directly set this attribute, or
			// if it is a complex type such as CodedType. Also, data type will tell us how
			// to properly format the setter(s)' argument(s)
			Iterator<Object> iter = groupedElements.iterator();

			while (iter.hasNext()) {
				caseNotificationDataDT = (CaseNotificationDataDT)iter.next();
				String dataType = caseNotificationDataDT.getDataType();
				String xmlDataType = caseNotificationDataDT.getXmlDataType();
				String xmlElementTag = caseNotificationDataDT.getXmlTag();
				Class<?> cls = result.getClass();

				//If this is an Identifier and we're populating an NBS local id, we need to set type code, assigning authority and assigning facility
				if (xmlElementTag.equalsIgnoreCase("IDNUMBER") && caseNotificationDataDT.getDataLocation().split("\\.")[1].equalsIgnoreCase("LOCAL_ID")) {
					Class<?>[] argtypes = { String.class };
					Object[] parameters = { "LR" };
					Method xmlBeanMethod = cls.getMethod("setIDTypeCode", argtypes);
					xmlBeanMethod.invoke(result, parameters);

					xmlBeanMethod = cls.getMethod("addNewAssigningFacility",  (Class<?>[])null);
					HierarchicalDesignationType assigningFacility = (HierarchicalDesignationType)xmlBeanMethod.invoke(result,  (Object[])null);
					assigningFacility.setNamespaceID(propertyUtil.getMsgSendingFacility());
					assigningFacility.setUniversalID(propertyUtil.getMsgSendingFacilityOID());
					assigningFacility.setUniversalIDType("ISO");

					//Get handle to Identifiers element to add other identifiers (if they exist) below
					XmlCursor cursor = assigningFacility.newCursor();
					cursor.toParent();
					cursor.toParent();
					IdentifiersType identifiers = (IdentifiersType)cursor.getObject();
					cursor.dispose();

					xmlBeanMethod = cls.getMethod("addNewAssigningAuthority",  (Class<?>[])null);
					HierarchicalDesignationType assigningAuthority = (HierarchicalDesignationType)xmlBeanMethod.invoke(result,  (Object[])null);
					assigningAuthority.setNamespaceID(propertyUtil.getMsgSendingApplication());
					assigningAuthority.setUniversalID(propertyUtil.getMsgSendingApplicationOID());
					assigningAuthority.setUniversalIDType("ISO");

					//Also, retrieve and add in any other identifiers this entity may have
					Collection<Object> entityIdCollection = null;
					if (caseNotificationDataDT.getAnswerTxt().startsWith("PSN")) {
						PersonDAOImpl personDAOImpl = new PersonDAOImpl();
						PersonDT personDT = personDAOImpl.retrievePersonDTByLocalId(caseNotificationDataDT.getAnswerTxt());
						
						if (personDT.getCd().equalsIgnoreCase(NEDSSConstants.PAT)) {
							//Get EntityId's for this Patient
							EntityIdDAOImpl entityIdDAOImpl = new EntityIdDAOImpl();
							entityIdCollection = entityIdDAOImpl.load(patientUid);
						} else {
							//Get EntityId's for this Provider
							EntityIdDAOImpl entityIdDAOImpl = new EntityIdDAOImpl();
							entityIdCollection = entityIdDAOImpl.load(personDT.getPersonUid());
						}
							

					} else if (caseNotificationDataDT.getAnswerTxt().startsWith("ORG")) {
						OrganizationDAOImpl organizationDAOImpl = new OrganizationDAOImpl();
						Long organizationUid = organizationDAOImpl.retrieveOrganizationUIDByLocalId(caseNotificationDataDT.getAnswerTxt());
						//Get EntityId's for this Organization
						EntityIdDAOImpl entityIdDAOImpl = new EntityIdDAOImpl();
						entityIdCollection = entityIdDAOImpl.load(organizationUid);

					}

					//Add Id's from EntityId Collection
					if (entityIdCollection != null && entityIdCollection.size() > 0) {
						addEntityIds(identifiers, entityIdCollection);
					}
				}


				if (dataType == null) {
					String errString = "PHDCMessageBuilder.populatePHDCMessageDocWithElement - Missing data type for question identifer: " +
					caseNotificationDataDT.getQuestionIdentifier();
					logger.error(errString);
					throw new NEDSSSystemException(errString);
				}

				if (dataType.equalsIgnoreCase(NEDSSConstants.DATATYPE_TEXT)) {
					Class<?>[] argtypes = { String.class };
					Object[] parameters = { caseNotificationDataDT.getAnswerTxt() };
					Method xmlBeanMethod = cls.getMethod("set" + xmlElementTag, argtypes);
					xmlBeanMethod.invoke(result, parameters);
				}
				else if (dataType.equalsIgnoreCase(NEDSSConstants.DATATYPE_NUMERIC)) {
					Method xmlBeanMethod = null;

				    //if non-negative integer, just call set
					if (xmlDataType.equalsIgnoreCase("nonNegativeInteger") || xmlDataType.equalsIgnoreCase("xs:nonNegativeInteger")) {
						// Drop decimal in case it came from obs_value_numeric
						String nonNegativeIntegerString = caseNotificationDataDT.getAnswerTxt().split("\\.")[0];
						BigInteger bigInt = BigInteger.valueOf(new Long(nonNegativeIntegerString).longValue());
						Class<?>[] argtypes = { BigInteger.class };
						Object[] parameters = { bigInt };
						xmlBeanMethod = cls.getMethod("set" + xmlElementTag, argtypes);
						xmlBeanMethod.invoke(result, parameters);
					} else {
						xmlBeanMethod = cls.getMethod("addNew" + xmlElementTag,  (Class[])null);
						NumericType numericType = (NumericType)xmlBeanMethod.invoke(result,  (Object[])null);
						numericType.setValue1(Float.parseFloat(caseNotificationDataDT.getAnswerTxt()));
						// Check if question has a unit associated to it
						if (caseNotificationDataDT.getQuestionUnitIdentifier() != null) {
							if (caseNotificationDataDT.getCodedValue() == null ||
							    caseNotificationDataDT.getCodedValue().trim().length() == 0) {
								// No unit for field indicating it has a unit, need to fail the message
								String errString = "PHDCMessageBuilder.populatePHDCMessageDocWithElement - Missing unit for structured numeric.  Attempting to find unit question identifer: " +
										caseNotificationDataDT.getQuestionUnitIdentifier() + " for question identifier: " +
										caseNotificationDataDT.getQuestionIdentifier();
								logger.error(errString);
								throw new NEDSSSystemException(errString);
							}
							// Populate unit with code from caseNotificationDT
							CodedType unit = numericType.addNewUnit();
							unit.setCode(caseNotificationDataDT.getCodedValue());
							unit.setCodeDescTxt(caseNotificationDataDT.getCodedValueDescription());
							unit.setCodeSystemCode(caseNotificationDataDT.getCodedValueCodingSystem());
						}
					}
				}
				else if (dataType.equalsIgnoreCase(NEDSSConstants.DATATYPE_DATE)) {
					Class<?>[] argtypes = { Calendar.class };
					Object[] parameters = { messageBuilderHelper.convertNBSDateToCalendar(caseNotificationDataDT.getAnswerTxt()) };
					Method xmlBeanMethod = cls.getMethod("set" + xmlElementTag, argtypes);
					xmlBeanMethod.invoke(result, parameters);
				}
				else if (dataType.equalsIgnoreCase(NEDSSConstants.DATATYPE_CODED)) {
					Method xmlBeanMethod = cls.getMethod("addNew" + xmlElementTag,  (Class[])null);
					CodedType codedType = (CodedType)xmlBeanMethod.invoke(result,  (Object[])null);
					if (caseNotificationDataDT.getCodedValue() != null && caseNotificationDataDT.getCodedValue().trim().length() > 0) {
						codedType.setCode(caseNotificationDataDT.getCodedValue());
						codedType.setCodeDescTxt(caseNotificationDataDT.getCodedValueDescription());
						codedType.setCodeSystemCode(caseNotificationDataDT.getCodedValueCodingSystem());
					}
					if (caseNotificationDataDT.getOriginalText() != null && caseNotificationDataDT.getOriginalText().trim().length() > 0) {
						codedType.setText(caseNotificationDataDT.getOriginalText());
					}

					if (caseNotificationDataDT.getLocalCodedValue() != null && caseNotificationDataDT.getLocalCodedValue().trim().length() > 0) {
						
						codedType.setCode(caseNotificationDataDT.getLocalCodedValue());
						codedType.setCodeDescTxt(caseNotificationDataDT.getLocalCodedValueDescription());
						codedType.setCodeSystemCode(caseNotificationDataDT.getLocalCodedValueCodingSystem());
						
						codedType.setAlternateCode(caseNotificationDataDT.getLocalCodedValue());
						codedType.setAlternateCodeDescTxt(caseNotificationDataDT.getLocalCodedValueDescription());
						codedType.setAlternateCodeSystemCode(caseNotificationDataDT.getLocalCodedValueCodingSystem());
					}
				}
				else {
					String errString = "PHDCMessageBuilder.populatePHDCMessageDocWithElement - Invalid data type (" + dataType + ") for question identifer: " +
					caseNotificationDataDT.getQuestionIdentifier();
					logger.error(errString);
					throw new NEDSSSystemException(errString);
				}
			}
		} catch (Exception e) {
			String errString = "PHDCMessageBuilder.populatePHDCMessageDocWithElement - For question identifier: " +
					caseNotificationDataDT.getQuestionIdentifier() + ":  " + e.getMessage();
			logger.error(errString);
			throw new NEDSSSystemException(errString);
		}

		return container;
	}


	private void addEntityIds(IdentifiersType identifiers, Collection<Object> entityIdCollection) {
		if (entityIdCollection != null) {
			Iterator<Object> idCollectionIter = entityIdCollection.iterator();
	
			while (idCollectionIter.hasNext()) {
				EntityIdDT entityIdDT = (EntityIdDT)idCollectionIter.next();
				if (!entityIdDT.getTypeCd().equalsIgnoreCase(NEDSSConstants.ENTITY_TYPECD_QEC)) {
					IdentifierType addId = identifiers.addNewIdentifier();
					addId.setIDNumber(entityIdDT.getRootExtensionTxt());
					addId.setIDTypeCode(entityIdDT.getTypeCd());
	
					HierarchicalDesignationType addIdAssigningAuthority = addId.addNewAssigningAuthority();
					addIdAssigningAuthority.setNamespaceID(entityIdDT.getAssigningAuthorityCd());
					try {
						Coded assigningAuthorityCodeLookup = new Coded(entityIdDT.getAssigningAuthorityCd(), DataTables.CODE_VALUE_GENERAL, "EI_AUTH");
						if (assigningAuthorityCodeLookup.getCodeDescription() != null) {
							if (assigningAuthorityCodeLookup.getCodeDescription().length() > 20)
								addIdAssigningAuthority.setUniversalID(assigningAuthorityCodeLookup.getCodeDescription().substring(0,19));
							else
								addIdAssigningAuthority.setUniversalID(assigningAuthorityCodeLookup.getCodeDescription());
						} else {
							addIdAssigningAuthority.setUniversalID(entityIdDT.getAssigningAuthorityDescTxt());
						}
	
						if (assigningAuthorityCodeLookup.getCodeSystemCd() != null) {
							if (assigningAuthorityCodeLookup.getCodeSystemCd().length() > 6)
								addIdAssigningAuthority.setUniversalIDType(assigningAuthorityCodeLookup.getCodeSystemCd().substring(0, 5));
							else
								addIdAssigningAuthority.setUniversalIDType(assigningAuthorityCodeLookup.getCodeSystemCd());
						} else {
							addIdAssigningAuthority.setUniversalIDType(NEDSSConstants.NOINFORMATIONGIVEN_CODE);
						}
					} catch (Exception e) {
						//Failed code lookup, set to local
						addIdAssigningAuthority.setUniversalID(entityIdDT.getAssigningAuthorityDescTxt());
						addIdAssigningAuthority.setUniversalIDType(NEDSSConstants.NOINFORMATIONGIVEN_CODE);
					}
				}
			}
		}
	}
	
	private NbsInterfaceDT prepareNbsInterface(String phdcXML, NotificationDT notificationDT) throws NEDSSConcurrentDataException {
		NbsInterfaceDT nbsInterfaceDT = new NbsInterfaceDT();

        CaseNotificationDAOImpl caseNotificationDAOImpl = new CaseNotificationDAOImpl();
        ExportReceivingFacilityDT exportReceivingFacilityDT = caseNotificationDAOImpl.getReceivingFacility(notificationDT.getExportReceivingFacilityUid());

		Date dateTime = new Date();
		Timestamp systemTime = new Timestamp(dateTime.getTime());

		nbsInterfaceDT.setXmlPayLoadContent(phdcXML); // Persisted to payload text/blob
		nbsInterfaceDT.setImpExpIndCd(NEDSSConstants.EDX_EXPORT_CODE);
		nbsInterfaceDT.setRecordStatusTime(systemTime);
		nbsInterfaceDT.setRecordStatusCd(NEDSSConstants.NND_UNPROCESSED_MESSAGE);
		nbsInterfaceDT.setAddTime(systemTime);
		nbsInterfaceDT.setDocTypeCd(NEDSSConstants.PHC_236);
		nbsInterfaceDT.setSystemNm(exportReceivingFacilityDT.getReceivingSystemShortName());
		return nbsInterfaceDT;
	}

	private Long writePHDCMessage(NbsInterfaceDT nbsInterfaceDT, NotificationDT notificationDT) throws NEDSSDAOAppException {
		NbsInterfaceDAOImpl nbsInterfaceDAOImpl = new NbsInterfaceDAOImpl();

		// Write payload to NBS_Interface table in ODSE
		if (propertyUtil.getPHDCMessageOutput() != null &&
			propertyUtil.getPHDCMessageOutput().equalsIgnoreCase(NEDSSConstants.MESSAGE_OUTPUT_TABLE)) {
			//sendRequest(nbsInterfaceDT.getXmlPayLoadContent());
			return nbsInterfaceDAOImpl.insertNBSInterface(nbsInterfaceDT);
		}

		// Write payload (XML) to file
		if (propertyUtil.getPHDCMessageOutput() != null &&
			propertyUtil.getPHDCMessageOutput().equalsIgnoreCase(NEDSSConstants.MESSAGE_OUTPUT_FILE)) {
			messageBuilderHelper.writeToFile(nbsInterfaceDT.getXmlPayLoadContent(), notificationDT.getLocalId(), NNDConstantUtil.CASE_REPORTS_ROOT_DIR);
			return null;
		}

		// Write payload (XML) to file and to NBS_Interface table in ODSE
		if (propertyUtil.getPHDCMessageOutput() != null &&
			propertyUtil.getPHDCMessageOutput().equalsIgnoreCase(NEDSSConstants.MESSAGE_OUTPUT_BOTH)) {
			messageBuilderHelper.writeToFile(nbsInterfaceDT.getXmlPayLoadContent(), notificationDT.getLocalId(), NNDConstantUtil.CASE_REPORTS_ROOT_DIR);
			//sendRequest(nbsInterfaceDT.getXmlPayLoadContent());
			return nbsInterfaceDAOImpl.insertNBSInterface(nbsInterfaceDT);
		}

		// No property found - just write to table
		return nbsInterfaceDAOImpl.insertNBSInterface(nbsInterfaceDT);
	}


	private void writeEDXActivityLogRecord(String result, String exceptionMsg, Long interfaceUID, Long notificationUID, String notificationLocalID) {
		EDXActivityLogDAOImpl edxActivityLogDAOImpl  = new EDXActivityLogDAOImpl();
		EDXActivityLogDT edxActivityLogDT = new EDXActivityLogDT();

		java.util.Date dateTime = new java.util.Date();
		Timestamp time = new Timestamp(dateTime.getTime());

		edxActivityLogDT.setSourceUid(notificationUID);
		edxActivityLogDT.setDocType(NEDSSConstants.PHC_236); // Public Health Document Container
		edxActivityLogDT.setImpExpIndCd(NEDSSConstants.EDX_EXPORT_CODE); // Export
		edxActivityLogDT.setRecordStatusTime(time);
		edxActivityLogDT.setSourceTypeCd(NEDSSConstants.EDX_NOTIFICATION_SOURCE_CODE); // Notification
		edxActivityLogDT.setTargetTypeCd(NEDSSConstants.EDX_INTERFACE_TABLE_CODE); // Interface Table
		edxActivityLogDT.setTargetUid(interfaceUID);
		edxActivityLogDT.setBusinessObjLocalId(notificationLocalID);

		edxActivityLogDT.setException(exceptionMsg); // Write exception if there is one
		edxActivityLogDT.setRecordStatusCd(result); // SUCCESS or FAIL
		edxActivityLogDAOImpl.insertEDXActivityLogDT(edxActivityLogDT);
	}
	
	
	static void sendRequest(String phdcMessage)throws NEDSSDAOAppException {   
		logger.debug("Start sending  request");   
		HttpURLConnection rc;
		try {
			URL url = new URL( " http://localhost:"+propertyUtil.getPHCRToCdaPort()+"/PhcrToCdaOutService/message" );   
			rc = (HttpURLConnection)url.openConnection();
			rc.setRequestMethod("POST");   
			rc.setDoOutput( true );   
			rc.setDoInput( true );    
			rc.setRequestProperty( "Content-Type", "text/xml; charset=utf-8" );   
			int len = phdcMessage.length();   
			rc.setRequestProperty( "Content-Length", Integer.toString( len ) );   
			rc.connect();       
			OutputStreamWriter out = new OutputStreamWriter( rc.getOutputStream() );    
			out.write( phdcMessage, 0, len );   
			out.flush();   
			logger.debug("Request sent, reading response ");   
			InputStreamReader read = new InputStreamReader( rc.getInputStream() );   
			StringBuilder sb = new StringBuilder();      
			int ch = read.read();   
			while( ch != -1 ){   
				sb.append((char)ch);   
				ch = read.read();   
			}   
			String response = sb.toString(); // entire response ends up in String   
			System.out.println("Got response\n\n\n" + response);
			read.close();   
			rc.disconnect();   
		} catch (MalformedURLException e) {
			logger.error("MalformedURLException caught:+"+e);
			throw new NEDSSDAOAppException(e.getMessage()+e.getCause());
		} catch (IOException e) {
			logger.error("IOException caught:+"+e);
			throw new NEDSSDAOAppException(e.getMessage()+e.getCause());
		}
	}  

}