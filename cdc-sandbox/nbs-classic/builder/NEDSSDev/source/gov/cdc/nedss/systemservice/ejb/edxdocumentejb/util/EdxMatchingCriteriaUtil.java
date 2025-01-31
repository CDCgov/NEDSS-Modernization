package gov.cdc.nedss.systemservice.ejb.edxdocumentejb.util;

/**
 * EdxMatchingCriteriaUtil:  it defines a util Service within NBS framework to match the non-patient enties againist the PHCR Document that enters the system
 * via an electronic public health document. 
 * The intent of this util class is to avoid inserting duplicate records in DB by  creating hashcodes from the  PHCR Document and match with existing in edx_entity_table and if donot match create the new hashcode 
 * and insert the new records to the table.  
 * 
 * @author: Sathya L
 * @Company: Saic
 * @since:Release 4.3
 */

import gov.cdc.nedss.controller.ejb.entitycontrollerejb.bean.EntityController;
import gov.cdc.nedss.controller.ejb.entitycontrollerejb.bean.EntityControllerHome;
import gov.cdc.nedss.entity.entityid.dt.EntityIdDT;
import gov.cdc.nedss.entity.organization.dt.OrganizationDT;
import gov.cdc.nedss.entity.organization.dt.OrganizationNameDT;
import gov.cdc.nedss.entity.organization.vo.OrganizationVO;
import gov.cdc.nedss.entity.person.dt.PersonDT;
import gov.cdc.nedss.entity.person.dt.PersonNameDT;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.entity.place.vo.PlaceVO;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.locator.dt.EntityLocatorParticipationDT;
import gov.cdc.nedss.locator.dt.PostalLocatorDT;
import gov.cdc.nedss.locator.dt.TeleLocatorDT;
import gov.cdc.nedss.nnd.ejb.nndmessageprocessorejb.dao.NotificationSRTCodeLookupTranslationDAOImpl;
import gov.cdc.nedss.systemservice.dt.EDXActivityDetailLogDT;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dao.EdxEntityMatchDAO;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dt.EdxEntityMatchDT;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dt.EdxRuleAlgorothmManagerDT;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.labutil.EdxELRConstants;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.util.EdxPHCRConstants.MSG_TYPE;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.Coded;
import gov.cdc.nedss.util.DataTables;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.rmi.PortableRemoteObject;

public class EdxMatchingCriteriaUtil {

	private static final LogUtils logger = new LogUtils(
			EdxMatchingCriteriaUtil.class.getName());

	public EDXActivityDetailLogDT getMatchingProvider(PersonVO personVO,
			NBSSecurityObj nbsSecurityObj) throws Exception {
		Long entityUid = personVO.getThePersonDT().getPersonUid();
	//	String providerRole = personVO.getRole();
		EdxEntityMatchDAO edxDao = new EdxEntityMatchDAO();
		Collection<EdxEntityMatchDT> coll = new ArrayList<EdxEntityMatchDT>();
		EDXActivityDetailLogDT edxActivityDetailLogDT = new EDXActivityDetailLogDT();
		String DET_MSG_ENTITY_EXISTS_SUCCESS = "Provider entity found with entity uid : ";
		String DET_MSG_ENTITY_EXISTS_FAIL_NEW = "Provider not found. New Provider created with person uid : ";
		// creating new localID DT for
		// local identifier
		EdxEntityMatchDT theEdxEntityMatchDT = null;
		String localId = null;
		int localIdhshCd = 0;
		localId = getLocalId(personVO);
		if (localId != null) {
			localId = localId.toUpperCase();
			localIdhshCd = localId.hashCode();
		}
		try {
			// Try to get the matching with the match string
			EdxEntityMatchDT edxEntityMatchingDT = edxDao
					.getEdxEntityMatch(NEDSSConstants.PRV, localId);
			if (edxEntityMatchingDT != null
					&& edxEntityMatchingDT.getEntityUid() != null) {
				edxActivityDetailLogDT.setRecordId(""
						+ edxEntityMatchingDT.getEntityUid());
				edxActivityDetailLogDT.setComment(DET_MSG_ENTITY_EXISTS_SUCCESS
						+ edxEntityMatchingDT.getEntityUid());
				edxActivityDetailLogDT.setRecordType("" + MSG_TYPE.Provider);
				edxActivityDetailLogDT.setRecordName("PHCR_IMPORT");
				edxActivityDetailLogDT.setLogType(""
						+ EdxRuleAlgorothmManagerDT.STATUS_VAL.Success);
				return edxActivityDetailLogDT;
			}
		} catch (Exception ex) {
			logger.error("Error in geting the  matching Provider");
			throw new NEDSSAppException(
					"Error in geting the  matching Provider" + ex.getMessage(),
					ex);
		}
		if (localId != null) {
			theEdxEntityMatchDT = new EdxEntityMatchDT();
			theEdxEntityMatchDT.setTypeCd(NEDSSConstants.PRV);
			theEdxEntityMatchDT.setMatchString(localId);
			theEdxEntityMatchDT.setMatchStringHashCode(new Long(localIdhshCd));
		}

		// Matching the Identifier (i.e. NPI)
		String identifier = null;
		int identifierHshCd = 0;
		List identifierList = null;
    	identifierList = getIdentifier(personVO);
		if (identifierList != null && !identifierList.isEmpty()) {
			for (int k = 0; k < identifierList.size(); k++) {
				identifier = (String) identifierList.get(k);
				if (identifier != null) {
					identifier = identifier.toUpperCase();
					identifierHshCd = identifier.hashCode();
				}
				try {
					// Try to get the matching with the match string
					EdxEntityMatchDT edxEntityMatchingDT = edxDao
							.getEdxEntityMatch(NEDSSConstants.PRV, identifier);
					if (edxEntityMatchingDT != null
							&& edxEntityMatchingDT.getEntityUid() != null) {
						if (theEdxEntityMatchDT != null) {
							theEdxEntityMatchDT.setEntityUid(edxEntityMatchingDT
									.getEntityUid());
							if (personVO.getRole() == null) {
								edxDao.setEdxEntityMatchDT(theEdxEntityMatchDT);
							}
						}
						edxActivityDetailLogDT.setRecordId(""
								+ edxEntityMatchingDT.getEntityUid());
						edxActivityDetailLogDT
								.setComment(DET_MSG_ENTITY_EXISTS_SUCCESS
										+ edxEntityMatchingDT.getEntityUid());
						edxActivityDetailLogDT.setRecordType(""
								+ MSG_TYPE.Provider);
						edxActivityDetailLogDT.setRecordName("PHCR_IMPORT");
						edxActivityDetailLogDT.setLogType(""
								+ EdxRuleAlgorothmManagerDT.STATUS_VAL.Success);
						return edxActivityDetailLogDT;
					}
				} catch (Exception ex) {
					logger.error("Error in geting the  matching Provider");
					throw new NEDSSAppException(
							"Error in geting the  matching Provider"
									+ ex.getMessage(), ex);
				}
				if (identifier != null) {
					EdxEntityMatchDT edxEntityMatchDT = new EdxEntityMatchDT();
					edxEntityMatchDT.setTypeCd(NEDSSConstants.PRV);
					edxEntityMatchDT.setMatchString(identifier);
					edxEntityMatchDT.setMatchStringHashCode(new Long(
							identifierHshCd));
					coll.add(edxEntityMatchDT);
				}

			}
		}

		// Matching with name and address with street address1 alone
		String nameAddStrSt1 = null;
		int nameAddStrSt1hshCd = 0;
		nameAddStrSt1 = nameAddressStreetOne(personVO);
		if (nameAddStrSt1 != null) {
			nameAddStrSt1 = nameAddStrSt1.toUpperCase();
			nameAddStrSt1hshCd = nameAddStrSt1.hashCode();
			if (nameAddStrSt1 != null) {

				try {
					// Try to get the matching with match string
					EdxEntityMatchDT edxEntityMatchingDT = edxDao
							.getEdxEntityMatch(NEDSSConstants.PRV, nameAddStrSt1);
					if (edxEntityMatchingDT != null
							&& edxEntityMatchingDT.getEntityUid() != null) {
						if (theEdxEntityMatchDT != null) {
							theEdxEntityMatchDT.setEntityUid(edxEntityMatchingDT
									.getEntityUid());
							if (personVO.getRole() == null) {
								edxDao.setEdxEntityMatchDT(theEdxEntityMatchDT);
							}
						}
						edxActivityDetailLogDT.setRecordId(""
								+ edxEntityMatchingDT.getEntityUid());
						edxActivityDetailLogDT
								.setComment(DET_MSG_ENTITY_EXISTS_SUCCESS
										+ edxEntityMatchingDT.getEntityUid());
						edxActivityDetailLogDT.setRecordType(""
								+ MSG_TYPE.Provider);
						edxActivityDetailLogDT.setRecordName("PHCR_IMPORT");
						edxActivityDetailLogDT.setLogType(""
								+ EdxRuleAlgorothmManagerDT.STATUS_VAL.Success);
						return edxActivityDetailLogDT;
					}
				} catch (Exception ex) {
					logger.error("Error in geting the  matching Provider");
					throw new NEDSSAppException(
							"Error in geting the  matching Provider"
									+ ex.getMessage(), ex);
				}
			}
		}
		// Continue for name Telephone with no extension
		String nameTelePhone = null;
		int nameTelePhonehshCd = 0;
		nameTelePhone = telePhoneTxt(personVO);
		if (nameTelePhone != null) {
			nameTelePhone = nameTelePhone.toUpperCase();
			nameTelePhonehshCd = nameTelePhone.hashCode();
			if (nameTelePhone != null) {
				try {
					// Try to get the matching with the match string
					EdxEntityMatchDT edxEntityMatchingDT = edxDao
							.getEdxEntityMatch(NEDSSConstants.PRV, nameTelePhone);
					if (edxEntityMatchingDT != null
							&& edxEntityMatchingDT.getEntityUid() != null) {
						if (theEdxEntityMatchDT != null) {
							theEdxEntityMatchDT.setEntityUid(edxEntityMatchingDT
									.getEntityUid());
							if (personVO.getRole() == null) {
								edxDao.setEdxEntityMatchDT(theEdxEntityMatchDT);
							}
						}
						edxActivityDetailLogDT.setRecordId(""
								+ edxEntityMatchingDT.getEntityUid());
						edxActivityDetailLogDT
								.setComment(DET_MSG_ENTITY_EXISTS_SUCCESS
										+ edxEntityMatchingDT.getEntityUid());
						edxActivityDetailLogDT.setRecordType(""
								+ MSG_TYPE.Provider);
						edxActivityDetailLogDT.setRecordName("PHCR_IMPORT");
						edxActivityDetailLogDT.setLogType(""
								+ EdxRuleAlgorothmManagerDT.STATUS_VAL.Success);
						return edxActivityDetailLogDT;
					}
				} catch (Exception ex) {
					logger.error("Error in geting the  matching Provider");
					throw new NEDSSAppException(
							"Error in geting the  matching Provider"
									+ ex.getMessage(), ex);
				}

			}
		}
		// Create the provider in case if the provider is not there in the DB
		try {
			EntityController entityController = getEntityController();

			if (personVO.getThePersonDT().getCd().equals(NEDSSConstants.PRV)) { // Provider
				String businessTriggerCd = NEDSSConstants.PRV_CR;
				entityUid = entityController.setProvider(personVO,
						businessTriggerCd, nbsSecurityObj);
			}
		} catch (Exception e) {
			logger.error("Error in getting the entity Controller or Setting the Organization"
					+ e.getMessage());
			throw new NEDSSAppException(
					"Error in getting the entity Controller or Setting the Organization"
							+ e.getMessage(), e);
		}
		/*
		 * if(personVO.getRole()!=null &&
		 * personVO.getRole().equalsIgnoreCase("PRV")){ return
		 * edxActivityDetailLogDT; }
		 */
		// Create the name and address with no street 2(only street1)
		if (nameAddStrSt1 != null) {
			EdxEntityMatchDT edxEntityMatchDT = new EdxEntityMatchDT();
			edxEntityMatchDT.setEntityUid(entityUid);
			edxEntityMatchDT.setTypeCd(NEDSSConstants.PRV);
			edxEntityMatchDT.setMatchString(nameAddStrSt1);
			edxEntityMatchDT
					.setMatchStringHashCode(new Long(nameAddStrSt1hshCd));
			try {
				if (personVO.getRole() == null) {
					edxDao.setEdxEntityMatchDT(edxEntityMatchDT);
				}
			} catch (Exception e) {
				logger.error("Error in creating the EdxEntityMatchDT with nameAddStrSt1:"
						+ nameAddStrSt1 + " " + e.getMessage());
				throw new NEDSSAppException(e.getMessage(), e);
			}

		}

		// Create the name and address with nameTelePhone
		if (nameTelePhone != null) {
			EdxEntityMatchDT edxEntityMatchDT = new EdxEntityMatchDT();
			edxEntityMatchDT.setEntityUid(entityUid);
			edxEntityMatchDT.setTypeCd(NEDSSConstants.PRV);
			edxEntityMatchDT.setMatchString(nameTelePhone);
			edxEntityMatchDT
					.setMatchStringHashCode(new Long(nameTelePhonehshCd));
			try {
				if (personVO.getRole() == null) {
					edxDao.setEdxEntityMatchDT(edxEntityMatchDT);
				}
			} catch (Exception e) {
				logger.error("Error in creating the EdxEntityMatchDT with nameTelePhone:"
						+ nameTelePhone + " " + e.getMessage());
				throw new NEDSSAppException(e.getMessage(), e);
			}
		}
		if (theEdxEntityMatchDT != null)
			coll.add(theEdxEntityMatchDT);
		if (coll != null) {
			Iterator<EdxEntityMatchDT> it = coll.iterator();
			while (it.hasNext()) {
				EdxEntityMatchDT edxEntityMatchDT = (EdxEntityMatchDT) it
						.next();
				edxEntityMatchDT.setEntityUid(entityUid);
				if (personVO.getRole() == null) {
					edxDao.setEdxEntityMatchDT(edxEntityMatchDT);
				}
			}
		}
		// returnung the entity Uid which is just created
		edxActivityDetailLogDT.setRecordId("" + entityUid);
		edxActivityDetailLogDT.setComment("" + DET_MSG_ENTITY_EXISTS_FAIL_NEW
				+ edxActivityDetailLogDT.getRecordId());
		edxActivityDetailLogDT.setRecordType("" + MSG_TYPE.Provider);
		edxActivityDetailLogDT.setRecordName("PHCR_IMPORT");
		edxActivityDetailLogDT.setLogType(""
				+ EdxRuleAlgorothmManagerDT.STATUS_VAL.Success);
		return edxActivityDetailLogDT;
	}

	// getting Local Id for Provider
	private String getLocalId(PersonVO personVO) {
		String localId = null;
		if (personVO.getLocalIdentifier() != null) {
			localId = personVO.getLocalIdentifier();
		}
		return localId;
	}

	// getting identifiers for providers PHCR
	private List<String> getIdentifier(PersonVO personVO) {
		String carrot = "^";
		List<String> identifierList = new ArrayList<String>();
		String identifier = null;
		Collection<Object> newEntityIdDTColl = new ArrayList<Object>();
		try{
		if (personVO.getTheEntityIdDTCollection() != null
				&& personVO.getTheEntityIdDTCollection().size() > 0) {
			Collection<Object> entityIdDTColl = personVO
					.getTheEntityIdDTCollection();
			Iterator<Object> entityIdIterator = entityIdDTColl.iterator();
			while (entityIdIterator.hasNext()) {
				EntityIdDT entityIdDT = (EntityIdDT) entityIdIterator.next();
				if ((entityIdDT.getStatusCd()
						.equalsIgnoreCase(NEDSSConstants.STATUS_ACTIVE))) {
					   if ((entityIdDT.getRootExtensionTxt() != null)
							&& (entityIdDT.getTypeCd() != null)
							&& (entityIdDT.getAssigningAuthorityCd() != null)
							&& (entityIdDT.getAssigningAuthorityDescTxt() !=null)
							&& (entityIdDT.getAssigningAuthorityIdType() != null)) {
							identifier = entityIdDT.getRootExtensionTxt()
									+ carrot + entityIdDT.getTypeCd() + carrot
									+ entityIdDT.getAssigningAuthorityCd()
									+ carrot
									+ entityIdDT.getAssigningAuthorityDescTxt()
									+ carrot + entityIdDT.getAssigningAuthorityIdType();
					}else {
						   try {
								Coded coded = new Coded();
								coded.setCode(entityIdDT.getAssigningAuthorityCd());
								coded.setCodesetName(NEDSSConstants.EI_AUTH_PRV);
								coded.setCodesetTableName(DataTables.CODE_VALUE_GENERAL);
								NotificationSRTCodeLookupTranslationDAOImpl lookupDAO = new NotificationSRTCodeLookupTranslationDAOImpl();
								lookupDAO.retrieveSRTCodeInfo(coded);
								if (entityIdDT.getRootExtensionTxt() != null
										&& entityIdDT.getTypeCd() != null
										&& coded.getCode()!=null 
										&& coded.getCodeDescription()!=null
										&& coded.getCodeSystemCd()!=null){
									identifier = entityIdDT.getRootExtensionTxt()
											+ carrot + entityIdDT.getTypeCd() + carrot
											+ coded.getCode() + carrot
											+ coded.getCodeDescription() + carrot
											+ coded.getCodeSystemCd();
										}
						   }catch (NEDSSSystemException ex) {
											String errorMessage = "The assigning authority "
													+ entityIdDT.getAssigningAuthorityCd()
													+ " does not exists in the system. ";
													logger.debug(ex.getMessage() + errorMessage);
										}
								}
						if (entityIdDT.getTypeCd()!=null && !entityIdDT.getTypeCd().equalsIgnoreCase("LR")) {
							newEntityIdDTColl.add(entityIdDT);
						}
						if (identifier != null) {
							identifierList.add(identifier);
						}

					}

				}

			}
		personVO.setTheEntityIdDTCollection(newEntityIdDTColl);
		
		}catch (NEDSSSystemException ex) {
			String errorMessage = "Exception while creating hashcode for Provider entity IDs . ";
					logger.debug(ex.getMessage() + errorMessage);
					throw new NEDSSSystemException(errorMessage, ex);
		}
		return identifierList;

	}


	// getting Last name,First name for the providers
	private String getNameString(PersonVO personVO) {
		String nameStr = null;
		if (personVO.getThePersonNameDTCollection() != null
				&& personVO.getThePersonNameDTCollection().size() > 0) {
			Collection<Object> PersonNameDTColl = personVO
					.getThePersonNameDTCollection();
			Iterator<Object> nameCollIter = PersonNameDTColl.iterator();
			while (nameCollIter.hasNext()) {
				PersonNameDT personNameDT = (PersonNameDT) nameCollIter.next();
				if (personNameDT.getNmUseCd() == null)
				{
					String Message = "personNameDT.getNmUseCd() is null";
					logger.debug(Message);
				}
				if (personNameDT.getNmUseCd() != null
						&& personNameDT.getNmUseCd().equals(
								NEDSSConstants.LEGAL)) {
					if (personNameDT.getLastNm() != null
							|| personNameDT.getFirstNm() != null)
						nameStr = personNameDT.getLastNm()
								+ personNameDT.getFirstNm();
				}
			}
		}
		return nameStr;
	}

	// Creating string for name and address for providers
	public String nameAddressStreetOne(PersonVO personVO) {
		String nameAddStr = null;
		String carrot = "^";
		if (personVO.getTheEntityLocatorParticipationDTCollection() != null
				&& personVO.getTheEntityLocatorParticipationDTCollection()
						.size() > 0) {
			Iterator<Object> addIter = personVO
					.getTheEntityLocatorParticipationDTCollection().iterator();
			while (addIter.hasNext()) {
				EntityLocatorParticipationDT entLocPartDT = (EntityLocatorParticipationDT) addIter
						.next();
				if (entLocPartDT.getClassCd() != null
						&& entLocPartDT.getClassCd().equals(
								NEDSSConstants.POSTAL)) {
					if (entLocPartDT.getCd() != null
							&& entLocPartDT.getCd().equals(
									NEDSSConstants.OFFICE_CD)
							&& entLocPartDT.getUseCd() != null
							&& entLocPartDT.getUseCd().equals(
									NEDSSConstants.WORK_PLACE)) {
						PostalLocatorDT postLocDT = entLocPartDT
								.getThePostalLocatorDT();
						if (postLocDT != null) {
							if ((postLocDT.getStreetAddr1() != null && !postLocDT
									.getStreetAddr1().equals(""))
									&& (postLocDT.getCityDescTxt() != null && !postLocDT
											.getCityDescTxt().equals(""))
									&& (postLocDT.getStateCd() != null && !postLocDT
											.getStateCd().equals(""))
									&& (postLocDT.getZipCd() != null && !postLocDT
											.getZipCd().equals(""))) {

								nameAddStr = carrot
										+ postLocDT.getStreetAddr1() + carrot
										+ postLocDT.getCityDescTxt() + carrot
										+ postLocDT.getStateCd() + carrot
										+ postLocDT.getZipCd();
							}
						}
					}
				}
			}

		}
		if (nameAddStr != null)
			nameAddStr = getNameString(personVO) + nameAddStr;
		return nameAddStr;
	}

	// Creating String for name + telephone for Providers
	public String telePhoneTxt(PersonVO personVO) {
		String nameTeleStr = null;
		String carrot = "^";

		if (personVO.getTheEntityLocatorParticipationDTCollection() != null
				&& personVO.getTheEntityLocatorParticipationDTCollection()
						.size() > 0) {
			Iterator<Object> addIter = personVO
					.getTheEntityLocatorParticipationDTCollection().iterator();
			while (addIter.hasNext()) {
				EntityLocatorParticipationDT entLocPartDT = (EntityLocatorParticipationDT) addIter
						.next();
				if (entLocPartDT.getClassCd() != null
						&& entLocPartDT.getClassCd()
								.equals(NEDSSConstants.TELE)) {
					if (entLocPartDT.getCd() != null
							&& entLocPartDT.getCd()
									.equals(NEDSSConstants.PHONE)) {
						TeleLocatorDT teleLocDT = entLocPartDT
								.getTheTeleLocatorDT();
						if (teleLocDT != null
								&& teleLocDT.getPhoneNbrTxt() != null
								&& !teleLocDT.getPhoneNbrTxt().equals(""))
							nameTeleStr = carrot + teleLocDT.getPhoneNbrTxt();

					}
				}
			}
		}
		if (nameTeleStr != null)
			nameTeleStr = getNameString(personVO) + nameTeleStr;
		return nameTeleStr;
	}

	public EDXActivityDetailLogDT getMatchingOrganization(
			OrganizationVO organizationVO, NBSSecurityObj nbsSecurityObj)
			throws Exception {
		Long entityUid = organizationVO.getTheOrganizationDT()
				.getOrganizationUid();
	//	String orgRole = organizationVO.getRole();
		Collection<EdxEntityMatchDT> coll = new ArrayList<EdxEntityMatchDT>();
		EdxEntityMatchDAO edxDao = new EdxEntityMatchDAO();
		EDXActivityDetailLogDT edxActivityDetailLogDT = new EDXActivityDetailLogDT();
		String DET_MSG_ENTITY_EXISTS_SUCCESS = "Organization entity found with entity uid : ";
		String DET_MSG_ENTITY_EXISTS_FAIL_NEW = "Organization not found. New Organization created with organization uid: ";
		/*
		 * Creating new DT for localID for // local identifier
		 */
		EdxEntityMatchDT localEdxEntityMatchDT = null;
		String localId = null;
		int localIdhshCd = 0;
		localId = getLocalId(organizationVO);
		if (localId != null) {
			localId = localId.toUpperCase();
			localIdhshCd = localId.hashCode();
		}
		if (localId != null) {
			try {
				// Try to get the matching with the match string and type (was hash code)
				EdxEntityMatchDT edxEntityMatchingDT = edxDao
					.getEdxEntityMatch(NEDSSConstants.ORGANIZATION_CLASS_CODE, localId);
				if (edxEntityMatchingDT != null
					&& edxEntityMatchingDT.getEntityUid() != null) {
				edxActivityDetailLogDT.setRecordId(""
						+ edxEntityMatchingDT.getEntityUid());
				edxActivityDetailLogDT.setComment(DET_MSG_ENTITY_EXISTS_SUCCESS
						+ edxEntityMatchingDT.getEntityUid());
				edxActivityDetailLogDT
						.setRecordType("" + MSG_TYPE.Organization);
				edxActivityDetailLogDT.setRecordName("PHCR_IMPORT");
				edxActivityDetailLogDT.setLogType(""
						+ EdxRuleAlgorothmManagerDT.STATUS_VAL.Success);
				return edxActivityDetailLogDT;
				}
			} catch (Exception ex) {
				logger.error("Error in geting the  matching Organization");
				throw new NEDSSAppException(
					"Error in geting the  matching Provider" + ex.getMessage(),
					ex);
			}
		}  //localId != null
		if (localId != null) {
			localEdxEntityMatchDT = new EdxEntityMatchDT();
			localEdxEntityMatchDT.setTypeCd(NEDSSConstants.ORGANIZATION);
			localEdxEntityMatchDT.setMatchString(localId);
			localEdxEntityMatchDT.setMatchStringHashCode(new Long(localIdhshCd));
		}

		// Matching the Identifier (CLIA)
		String identifier = null;
		int identifierHshCd = 0;
		List identifierList = null;
		identifierList = getIdentifier(organizationVO);

		if (identifierList != null && !identifierList.isEmpty()) {
			for (int k = 0; k < identifierList.size(); k++) {
				identifier = (String) identifierList.get(k);
				if (identifier != null) {
					identifier = identifier.toUpperCase();
					identifierHshCd = identifier.hashCode();
				}
				try {
					// Try to get the matching with the type and match string
					EdxEntityMatchDT edxEntityMatchingDT = edxDao
							.getEdxEntityMatch(NEDSSConstants.ORGANIZATION_CLASS_CODE, identifier);
					if (edxEntityMatchingDT != null
							&& edxEntityMatchingDT.getEntityUid() != null) {
						if (localEdxEntityMatchDT != null) {
							localEdxEntityMatchDT.setEntityUid(edxEntityMatchingDT
									.getEntityUid());
							edxDao.setEdxEntityMatchDT(localEdxEntityMatchDT);
						}
						edxActivityDetailLogDT.setRecordId(""
								+ edxEntityMatchingDT.getEntityUid());
						edxActivityDetailLogDT
								.setComment(DET_MSG_ENTITY_EXISTS_SUCCESS
										+ edxEntityMatchingDT.getEntityUid());
						edxActivityDetailLogDT.setRecordType(""
								+ MSG_TYPE.Organization);
						edxActivityDetailLogDT.setRecordName("PHCR_IMPORT");
						edxActivityDetailLogDT.setLogType(""
								+ EdxRuleAlgorothmManagerDT.STATUS_VAL.Success);
						return edxActivityDetailLogDT;
					}
				} catch (Exception ex) {
					logger.error("Error in geting the  matching Organization");
					throw new NEDSSAppException(
							"Error in geting the  matching Organization"
									+ ex.getMessage(), ex);
				}
				if (identifier != null) {
					EdxEntityMatchDT edxEntityMatchDT = new EdxEntityMatchDT();
					edxEntityMatchDT.setTypeCd(NEDSSConstants.ORGANIZATION);
					edxEntityMatchDT.setMatchString(identifier);
					edxEntityMatchDT.setMatchStringHashCode(new Long(
							identifierHshCd));
					coll.add(edxEntityMatchDT);
				}

			}
		}
		// Matching with name and address with street address1 alone
		String nameAddStrSt1 = null;
		int nameAddStrSt1hshCd = 0;
		nameAddStrSt1 = nameAddressStreetOne(organizationVO);
		if (nameAddStrSt1 != null) {
			nameAddStrSt1 = nameAddStrSt1.toUpperCase();
			nameAddStrSt1hshCd = nameAddStrSt1.hashCode();
		}
		if (nameAddStrSt1 != null) {

			try {
				// Try to get the matching with the type and match string
				EdxEntityMatchDT edxEntityMatchingDT = edxDao
						.getEdxEntityMatch(NEDSSConstants.ORGANIZATION_CLASS_CODE, nameAddStrSt1);
				if (edxEntityMatchingDT != null
						&& edxEntityMatchingDT.getEntityUid() != null) {
					if (localEdxEntityMatchDT != null) {
						localEdxEntityMatchDT.setEntityUid(edxEntityMatchingDT
								.getEntityUid());
						edxDao.setEdxEntityMatchDT(localEdxEntityMatchDT);
					}
					edxActivityDetailLogDT.setRecordId(""
							+ edxEntityMatchingDT.getEntityUid());
					edxActivityDetailLogDT
							.setComment(DET_MSG_ENTITY_EXISTS_SUCCESS
									+ edxEntityMatchingDT.getEntityUid());
					edxActivityDetailLogDT.setRecordType(""
							+ MSG_TYPE.Organization);
					edxActivityDetailLogDT.setRecordName("PHCR_IMPORT");
					edxActivityDetailLogDT.setLogType(""
							+ EdxRuleAlgorothmManagerDT.STATUS_VAL.Success);
					return edxActivityDetailLogDT;
				}
			} catch (Exception ex) {
				logger.error("Error in geting the  matching Organization");
				throw new NEDSSAppException(
						"Error in geting the  matching Organization"
								+ ex.getMessage(), ex);
			}

		}
		// Continue for name Telephone with no extension
		String nameTelePhone = null;
		int nameTelePhonehshCd = 0;
		nameTelePhone = telePhoneTxt(organizationVO);
		if (nameTelePhone != null) {
			nameTelePhone = nameTelePhone.toUpperCase();
			nameTelePhonehshCd = nameTelePhone.hashCode();
		}
		if (nameTelePhone != null) {
			try {
				EdxEntityMatchDT edxEntityMatchingDT = edxDao
						.getEdxEntityMatch(NEDSSConstants.ORGANIZATION_CLASS_CODE, nameTelePhone);
				if (edxEntityMatchingDT != null
						&& edxEntityMatchingDT.getEntityUid() != null) {
					if (localEdxEntityMatchDT != null) {
						localEdxEntityMatchDT.setEntityUid(edxEntityMatchingDT
								.getEntityUid());
						edxDao.setEdxEntityMatchDT(localEdxEntityMatchDT);
					}
					edxActivityDetailLogDT.setRecordId(""
							+ edxEntityMatchingDT.getEntityUid());
					edxActivityDetailLogDT
							.setComment(DET_MSG_ENTITY_EXISTS_SUCCESS
									+ edxEntityMatchingDT.getEntityUid());
					edxActivityDetailLogDT.setRecordType(""
							+ MSG_TYPE.Organization);
					edxActivityDetailLogDT.setRecordName("PHCR_IMPORT");
					edxActivityDetailLogDT.setLogType(""
							+ EdxRuleAlgorothmManagerDT.STATUS_VAL.Success);
					return edxActivityDetailLogDT;
				}
			} catch (Exception ex) {
				logger.error("Error in geting the  matching Organization");
				throw new NEDSSAppException(
						"Error in geting the  matching Organization"
								+ ex.getMessage(), ex);
			}

		}
		// Create the provider in case if the provider is not there in the DB
		try {
			EntityController entityController = getEntityController();
			String businessTriggerCd = NEDSSConstants.ORG_CR;
			entityUid = entityController.setOrganization(organizationVO,
					businessTriggerCd, nbsSecurityObj);
		} catch (Exception e) {
			logger.error("Error in getting the entity Controller or setting the Organization");
			throw new NEDSSAppException(e.getMessage(), e);
		}
		// Create the name and address with no street 2(only street1)
		if (nameAddStrSt1 != null) {
			EdxEntityMatchDT edxEntityMatchDT = new EdxEntityMatchDT();
			edxEntityMatchDT.setEntityUid(entityUid);
			edxEntityMatchDT.setTypeCd(NEDSSConstants.ORGANIZATION);
			edxEntityMatchDT.setMatchString(nameAddStrSt1);
			edxEntityMatchDT
					.setMatchStringHashCode(new Long(nameAddStrSt1hshCd));
			try {
				edxDao.setEdxEntityMatchDT(edxEntityMatchDT);
			} catch (Exception e) {
				logger.error("Error in creating the EdxEntityMatchDT with nameAddStrSt1:"
						+ nameAddStrSt1 + " " + e.getMessage());
				throw new NEDSSAppException(e.getMessage(), e);
			}

		}

		// Create the name and address with nameTelePhone
		if (nameTelePhone != null) {
			EdxEntityMatchDT edxEntityMatchDT = new EdxEntityMatchDT();
			edxEntityMatchDT.setEntityUid(entityUid);
			edxEntityMatchDT.setTypeCd(NEDSSConstants.ORGANIZATION);
			edxEntityMatchDT.setMatchString(nameTelePhone);
			edxEntityMatchDT
					.setMatchStringHashCode(new Long(nameTelePhonehshCd));
			try {
				edxDao.setEdxEntityMatchDT(edxEntityMatchDT);
			} catch (Exception e) {
				logger.error("Error in creating the EdxEntityMatchDT with nameTelePhone:"
						+ nameTelePhone + " " + e.getMessage());
				throw new NEDSSAppException(e.getMessage(), e);
			}
		}
		if (localEdxEntityMatchDT != null)
			coll.add(localEdxEntityMatchDT);
		if (coll != null) {
			Iterator<EdxEntityMatchDT> it = coll.iterator();
			while (it.hasNext()) {
				EdxEntityMatchDT edxEntityMatchDT = (EdxEntityMatchDT) it
						.next();
				edxEntityMatchDT.setEntityUid(entityUid);
				edxDao.setEdxEntityMatchDT(edxEntityMatchDT);

			}

		}

		edxActivityDetailLogDT.setRecordId("" + entityUid);
		edxActivityDetailLogDT.setComment("" + DET_MSG_ENTITY_EXISTS_FAIL_NEW
				+ edxActivityDetailLogDT.getRecordId());
		edxActivityDetailLogDT.setRecordType("" + MSG_TYPE.Organization);
		edxActivityDetailLogDT.setRecordName("PHCR_IMPORT");
		edxActivityDetailLogDT.setLogType(""
				+ EdxRuleAlgorothmManagerDT.STATUS_VAL.Success);
		return edxActivityDetailLogDT;
	}
	
	
	public EDXActivityDetailLogDT getMatchingPlace(
			PlaceVO placeVO, NBSSecurityObj nbsSecurityObj)
			throws Exception {
		Long entityUid = placeVO.getThePlaceDT().getPlaceUid();

		Collection<EdxEntityMatchDT> coll = new ArrayList<EdxEntityMatchDT>();
		EdxEntityMatchDAO edxDao = new EdxEntityMatchDAO();
		EDXActivityDetailLogDT edxActivityDetailLogDT = new EDXActivityDetailLogDT();
		String DET_MSG_ENTITY_EXISTS_SUCCESS = "Place entity found with entity uid : ";
		String DET_MSG_ENTITY_EXISTS_FAIL_NEW = "Place not found. New Place created with place uid: ";
		/*
		 * Creating new DT for localID for // local identifier
		 */
		EdxEntityMatchDT localEdxEntityMatchDT = null;
		String localId = null;
		int localIdhshCd = 0;
		localId = placeVO.getLocalIdentifier();
		if (localId != null) {
			localId = localId.toUpperCase();
			localIdhshCd = localId.hashCode();
		}
		if (localId != null) {
			try {
				// Try to get the matching with the match string and type (was hash code)
				EdxEntityMatchDT edxEntityMatchingDT = edxDao
					.getEdxEntityMatch(NEDSSConstants.PLACE_CLASS_CODE, localId);
				if (edxEntityMatchingDT != null
					&& edxEntityMatchingDT.getEntityUid() != null) {
				edxActivityDetailLogDT.setRecordId(""
						+ edxEntityMatchingDT.getEntityUid());
				edxActivityDetailLogDT.setComment(DET_MSG_ENTITY_EXISTS_SUCCESS
						+ edxEntityMatchingDT.getEntityUid());
				edxActivityDetailLogDT
						.setRecordType("" + MSG_TYPE.Place);
				edxActivityDetailLogDT.setRecordName("PHCR_IMPORT");
				edxActivityDetailLogDT.setLogType(""
						+ EdxRuleAlgorothmManagerDT.STATUS_VAL.Success);
				return edxActivityDetailLogDT;
				}
			} catch (Exception ex) {
				logger.error("Error in geting the  matching Place");
				throw new NEDSSAppException(
					"Error in geting the  matching Provider" + ex.getMessage(),
					ex);
			}
		}  //localId != null
		if (localId != null) {
			localEdxEntityMatchDT = new EdxEntityMatchDT();
			localEdxEntityMatchDT.setTypeCd(NEDSSConstants.PLACE);
			localEdxEntityMatchDT.setMatchString(localId);
			localEdxEntityMatchDT.setMatchStringHashCode(new Long(localIdhshCd));
		}

		// Matching with name and address with street address1 alone
		String nameAddStrSt1 = null;
		int nameAddStrSt1hshCd = 0;
		nameAddStrSt1 = nameAddressStreetOne(placeVO);
		if (nameAddStrSt1 != null) {
			nameAddStrSt1 = nameAddStrSt1.toUpperCase();
			nameAddStrSt1hshCd = nameAddStrSt1.hashCode();
		}
		if (nameAddStrSt1 != null) {

			try {
				// Try to get the matching with the type and match string
				EdxEntityMatchDT edxEntityMatchingDT = edxDao
						.getEdxEntityMatch(NEDSSConstants.PLACE_CLASS_CODE, nameAddStrSt1);
				if (edxEntityMatchingDT != null
						&& edxEntityMatchingDT.getEntityUid() != null) {
					if (localEdxEntityMatchDT != null) {
						localEdxEntityMatchDT.setEntityUid(edxEntityMatchingDT
								.getEntityUid());
						edxDao.setEdxEntityMatchDT(localEdxEntityMatchDT);
					}
					edxActivityDetailLogDT.setRecordId(""
							+ edxEntityMatchingDT.getEntityUid());
					edxActivityDetailLogDT
							.setComment(DET_MSG_ENTITY_EXISTS_SUCCESS
									+ edxEntityMatchingDT.getEntityUid());
					edxActivityDetailLogDT.setRecordType(""
							+ MSG_TYPE.Place);
					edxActivityDetailLogDT.setRecordName("PHCR_IMPORT");
					edxActivityDetailLogDT.setLogType(""
							+ EdxRuleAlgorothmManagerDT.STATUS_VAL.Success);
					return edxActivityDetailLogDT;
				}
			} catch (Exception ex) {
				logger.error("Error in geting the  matching Place");
				throw new NEDSSAppException(
						"Error in geting the  matching Place"
								+ ex.getMessage(), ex);
			}

		}
		// Continue for name Telephone with no extension
		String nameTelePhone = null;
		int nameTelePhonehshCd = 0;
		nameTelePhone = telePhoneTxt(placeVO);
		if (nameTelePhone != null) {
			nameTelePhone = nameTelePhone.toUpperCase();
			nameTelePhonehshCd = nameTelePhone.hashCode();
		}
		if (nameTelePhone != null) {
			try {
				EdxEntityMatchDT edxEntityMatchingDT = edxDao
						.getEdxEntityMatch(NEDSSConstants.PLACE_CLASS_CODE, nameTelePhone);
				if (edxEntityMatchingDT != null
						&& edxEntityMatchingDT.getEntityUid() != null) {
					if (localEdxEntityMatchDT != null) {
						localEdxEntityMatchDT.setEntityUid(edxEntityMatchingDT
								.getEntityUid());
						edxDao.setEdxEntityMatchDT(localEdxEntityMatchDT);
					}
					edxActivityDetailLogDT.setRecordId(""
							+ edxEntityMatchingDT.getEntityUid());
					edxActivityDetailLogDT
							.setComment(DET_MSG_ENTITY_EXISTS_SUCCESS
									+ edxEntityMatchingDT.getEntityUid());
					edxActivityDetailLogDT.setRecordType(""
							+ MSG_TYPE.Place);
					edxActivityDetailLogDT.setRecordName("PHCR_IMPORT");
					edxActivityDetailLogDT.setLogType(""
							+ EdxRuleAlgorothmManagerDT.STATUS_VAL.Success);
					return edxActivityDetailLogDT;
				}
			} catch (Exception ex) {
				logger.error("Error in geting the  matching Place");
				throw new NEDSSAppException(
						"Error in geting the  matching Place"
								+ ex.getMessage(), ex);
			}

		}
		// Create the provider in case if the provider is not there in the DB
		try {
			EntityController entityController = getEntityController();
			String businessTriggerCd = NEDSSConstants.PLC_CR;
			entityUid = entityController.setPlace(placeVO, businessTriggerCd, nbsSecurityObj);
		} catch (Exception e) {
			logger.error("Error in getting the entity Controller or setting the Place");
			throw new NEDSSAppException(e.getMessage(), e);
		}
		// Create the name and address with no street 2(only street1)
		if (nameAddStrSt1 != null) {
			EdxEntityMatchDT edxEntityMatchDT = new EdxEntityMatchDT();
			edxEntityMatchDT.setEntityUid(entityUid);
			edxEntityMatchDT.setTypeCd(NEDSSConstants.PLACE);
			edxEntityMatchDT.setMatchString(nameAddStrSt1);
			edxEntityMatchDT
					.setMatchStringHashCode(new Long(nameAddStrSt1hshCd));
			try {
				edxDao.setEdxEntityMatchDT(edxEntityMatchDT);
			} catch (Exception e) {
				logger.error("Error in creating the EdxEntityMatchDT with nameAddStrSt1:"
						+ nameAddStrSt1 + " " + e.getMessage());
				throw new NEDSSAppException(e.getMessage(), e);
			}

		}

		// Create the name and address with nameTelePhone
		if (nameTelePhone != null) {
			EdxEntityMatchDT edxEntityMatchDT = new EdxEntityMatchDT();
			edxEntityMatchDT.setEntityUid(entityUid);
			edxEntityMatchDT.setTypeCd(NEDSSConstants.PLACE);
			edxEntityMatchDT.setMatchString(nameTelePhone);
			edxEntityMatchDT
					.setMatchStringHashCode(new Long(nameTelePhonehshCd));
			try {
				edxDao.setEdxEntityMatchDT(edxEntityMatchDT);
			} catch (Exception e) {
				logger.error("Error in creating the EdxEntityMatchDT with nameTelePhone:"
						+ nameTelePhone + " " + e.getMessage());
				throw new NEDSSAppException(e.getMessage(), e);
			}
		}
		if (localEdxEntityMatchDT != null)
			coll.add(localEdxEntityMatchDT);
		if (coll != null) {
			Iterator<EdxEntityMatchDT> it = coll.iterator();
			while (it.hasNext()) {
				EdxEntityMatchDT edxEntityMatchDT = (EdxEntityMatchDT) it
						.next();
				edxEntityMatchDT.setEntityUid(entityUid);
				edxDao.setEdxEntityMatchDT(edxEntityMatchDT);

			}

		}

		edxActivityDetailLogDT.setRecordId("" + entityUid);
		edxActivityDetailLogDT.setComment("" + DET_MSG_ENTITY_EXISTS_FAIL_NEW
				+ edxActivityDetailLogDT.getRecordId());
		edxActivityDetailLogDT.setRecordType("" + MSG_TYPE.Place);
		edxActivityDetailLogDT.setRecordName("PHCR_IMPORT");
		edxActivityDetailLogDT.setLogType(""
				+ EdxRuleAlgorothmManagerDT.STATUS_VAL.Success);
		return edxActivityDetailLogDT;
	}

	// getting Local Id string from person VO for Organization
	private String getLocalId(OrganizationVO organizationVO) {
		String localId = null;
		if (organizationVO.getLocalIdentifier() != null) {
			localId = organizationVO.getLocalIdentifier();
		}
		return localId;
	}

	// getting identifiers for PHCR Organizations
	private List<String> getIdentifier(OrganizationVO organizationVO) {
		String carrot = "^";
		List<String> identifierList = new ArrayList<String>();
		String identifier = null;
		Collection<Object> newEntityIdDTColl = new ArrayList<Object>();
		try {
		if (organizationVO.getTheEntityIdDTCollection() != null
				&& organizationVO.getTheEntityIdDTCollection().size() > 0) {
			Collection<Object> entityIdDTColl = organizationVO
					.getTheEntityIdDTCollection();
			Iterator<Object> entityIdIterator = entityIdDTColl.iterator();
			while (entityIdIterator.hasNext()) {
				EntityIdDT entityIdDT = (EntityIdDT) entityIdIterator.next();
				if ((entityIdDT.getStatusCd()
						.equalsIgnoreCase(NEDSSConstants.STATUS_ACTIVE))) {
					
					   if ((entityIdDT.getRootExtensionTxt() != null)
							&& (entityIdDT.getTypeCd() != null)
							&& (entityIdDT.getAssigningAuthorityCd() != null)
							&& (entityIdDT.getAssigningAuthorityDescTxt() !=null)
							&& (entityIdDT.getAssigningAuthorityIdType() != null)) {
							identifier = entityIdDT.getRootExtensionTxt()
									+ carrot + entityIdDT.getTypeCd() + carrot
									+ entityIdDT.getAssigningAuthorityCd()
									+ carrot
									+ entityIdDT.getAssigningAuthorityDescTxt()
									+ carrot + entityIdDT.getAssigningAuthorityIdType();
					}else {
						   try {
								Coded coded = new Coded();
								coded.setCode(entityIdDT.getAssigningAuthorityCd());
								coded.setCodesetName(NEDSSConstants.EI_AUTH_ORG);
								coded.setCodesetTableName(DataTables.CODE_VALUE_GENERAL);
								NotificationSRTCodeLookupTranslationDAOImpl lookupDAO = new NotificationSRTCodeLookupTranslationDAOImpl();
								lookupDAO.retrieveSRTCodeInfo(coded);
								if (entityIdDT.getRootExtensionTxt() != null
										&& entityIdDT.getTypeCd() != null
										&& coded.getCode()!=null 
										&& coded.getCodeDescription()!=null
										&& coded.getCodeSystemCd()!=null){
									identifier = entityIdDT.getRootExtensionTxt()
											+ carrot + entityIdDT.getTypeCd() + carrot
											+ coded.getCode() + carrot
											+ coded.getCodeDescription() + carrot
											+ coded.getCodeSystemCd();
										}
						   }catch (NEDSSSystemException ex) {
											String errorMessage = "The assigning authority "
													+ entityIdDT.getAssigningAuthorityCd()
													+ " does not exists in the system. ";
													logger.debug(ex.getMessage() + errorMessage);
										}
								}
						if (entityIdDT.getTypeCd()!=null && !entityIdDT.getTypeCd().equalsIgnoreCase("LR")) {
							newEntityIdDTColl.add(entityIdDT);
						}
						if (identifier != null) {
							identifierList.add(identifier);
						}

			}
		}
		}
		organizationVO.setTheEntityIdDTCollection(newEntityIdDTColl);
		} catch (NEDSSSystemException ex) {
			String errorMessage = "Exception while creating hashcode for organization entity IDs . ";
					logger.debug(ex.getMessage() + errorMessage);
			throw new NEDSSSystemException(errorMessage, ex);
		}
		return identifierList;
	}

	private String getNameString(OrganizationVO organizationVO) {
		String nameStr = null;
		if (organizationVO.getTheOrganizationNameDTCollection() != null
				&& organizationVO.getTheOrganizationNameDTCollection().size() > 0) {
			Collection<Object> organizationNameDTColl = organizationVO
					.getTheOrganizationNameDTCollection();
			Iterator<Object> nameCollIter = organizationNameDTColl.iterator();
			while (nameCollIter.hasNext()) {
				OrganizationNameDT organizationNameDT = (OrganizationNameDT) nameCollIter
						.next();
				if (organizationNameDT.getNmUseCd() != null
						&& organizationNameDT.getNmUseCd().equals(
								NEDSSConstants.LEGAL)) {
					if (organizationNameDT.getNmTxt() != null
							|| organizationNameDT.getNmTxt() != null)
						nameStr = organizationNameDT.getNmTxt();
				}
			}
		}
		return nameStr;
	}

	public String nameAddressStreetOne(OrganizationVO organizationVO) {
		String nameAddStr = null;
		String carrot = "^";
		if (organizationVO.getTheEntityLocatorParticipationDTCollection() != null
				&& organizationVO
						.getTheEntityLocatorParticipationDTCollection().size() > 0) {
			Iterator<Object> addIter = organizationVO
					.getTheEntityLocatorParticipationDTCollection().iterator();
			while (addIter.hasNext()) {
				EntityLocatorParticipationDT entLocPartDT = (EntityLocatorParticipationDT) addIter
						.next();
				if (entLocPartDT.getClassCd() != null
						&& entLocPartDT.getClassCd().equals(
								NEDSSConstants.POSTAL)) {
					if (entLocPartDT.getCd() != null
							&& entLocPartDT.getCd().equals(
									NEDSSConstants.OFFICE_CD)
							&& entLocPartDT.getUseCd() != null
							&& entLocPartDT.getUseCd().equals(
									NEDSSConstants.WORK_PLACE)) {
						if (entLocPartDT.getThePostalLocatorDT() != null) {
							PostalLocatorDT postLocDT = entLocPartDT
									.getThePostalLocatorDT();
							if ((postLocDT.getStreetAddr1() != null && !postLocDT
									.getStreetAddr1().equals(""))
									&& (postLocDT.getCityDescTxt() != null && !postLocDT
											.getCityDescTxt().equals(""))
									&& (postLocDT.getStateCd() != null && !postLocDT
											.getStateCd().equals(""))
									&& (postLocDT.getZipCd() != null && !postLocDT
											.getZipCd().equals(""))) {
								nameAddStr = carrot
										+ postLocDT.getStreetAddr1() + carrot
										+ postLocDT.getCityDescTxt() + carrot
										+ postLocDT.getStateCd() + carrot
										+ postLocDT.getZipCd();
							}
						}
					}
				}
			}
		}
		if (nameAddStr != null)
			nameAddStr = getNameString(organizationVO) + nameAddStr;
		return nameAddStr;
	}

	public String nameAddressStreetOne(PlaceVO placeVO) {
		String nameAddStr = null;
		String carrot = "^";
		if (placeVO.getTheEntityLocatorParticipationDTCollection() != null
				&& placeVO
						.getTheEntityLocatorParticipationDTCollection().size() > 0) {
			Iterator<Object> addIter = placeVO
					.getTheEntityLocatorParticipationDTCollection().iterator();
			while (addIter.hasNext()) {
				EntityLocatorParticipationDT entLocPartDT = (EntityLocatorParticipationDT) addIter
						.next();
				if (entLocPartDT.getClassCd() != null
						&& entLocPartDT.getClassCd().equals(
								NEDSSConstants.POSTAL)) {
					if (entLocPartDT.getCd() != null
							&& entLocPartDT.getCd().equals(
									NEDSSConstants.OFFICE_CD)
							&& entLocPartDT.getUseCd() != null
							&& entLocPartDT.getUseCd().equals(
									NEDSSConstants.WORK_PLACE)) {
						if (entLocPartDT.getThePostalLocatorDT() != null) {
							PostalLocatorDT postLocDT = entLocPartDT
									.getThePostalLocatorDT();
							if ((postLocDT.getStreetAddr1() != null && !postLocDT
									.getStreetAddr1().equals(""))
									&& (postLocDT.getCityDescTxt() != null && !postLocDT
											.getCityDescTxt().equals(""))
									&& (postLocDT.getStateCd() != null && !postLocDT
											.getStateCd().equals(""))
									&& (postLocDT.getZipCd() != null && !postLocDT
											.getZipCd().equals(""))) {
								nameAddStr = carrot
										+ postLocDT.getStreetAddr1() + carrot
										+ postLocDT.getCityDescTxt() + carrot
										+ postLocDT.getStateCd() + carrot
										+ postLocDT.getZipCd();
							}
						}
					}
				}
			}
		}
		if (nameAddStr != null)
			nameAddStr = placeVO.getThePlaceDT().getNm() + nameAddStr;
		return nameAddStr;
	}

	
	public String telePhoneTxt(OrganizationVO organizationVO) {
		String nameTeleStr = null;
		String carrot = "^";

		if (organizationVO.getTheEntityLocatorParticipationDTCollection() != null
				&& organizationVO
						.getTheEntityLocatorParticipationDTCollection().size() > 0) {
			Iterator<Object> addIter = organizationVO
					.getTheEntityLocatorParticipationDTCollection().iterator();
			while (addIter.hasNext()) {
				EntityLocatorParticipationDT entLocPartDT = (EntityLocatorParticipationDT) addIter
						.next();
				if (entLocPartDT.getClassCd() != null
						&& entLocPartDT.getClassCd()
								.equals(NEDSSConstants.TELE)) {
					if (entLocPartDT.getCd() != null
							&& entLocPartDT.getCd()
									.equals(NEDSSConstants.PHONE)) {
						if (entLocPartDT.getTheTeleLocatorDT() != null) {
							TeleLocatorDT teleLocDT = entLocPartDT
									.getTheTeleLocatorDT();
							if (teleLocDT.getPhoneNbrTxt() != null
									&& !teleLocDT.getPhoneNbrTxt().equals(""))
								nameTeleStr = carrot
										+ teleLocDT.getPhoneNbrTxt();

						}
					}
				}
			}
		}
		if (nameTeleStr != null) {
			nameTeleStr = getNameString(organizationVO) + nameTeleStr;
		}
		return nameTeleStr;
	}
	
	public String telePhoneTxt(PlaceVO placeVO) {
		String nameTeleStr = null;
		String carrot = "^";

		if (placeVO.getTheEntityLocatorParticipationDTCollection() != null
				&& placeVO
						.getTheEntityLocatorParticipationDTCollection().size() > 0) {
			Iterator<Object> addIter = placeVO
					.getTheEntityLocatorParticipationDTCollection().iterator();
			while (addIter.hasNext()) {
				EntityLocatorParticipationDT entLocPartDT = (EntityLocatorParticipationDT) addIter
						.next();
				if (entLocPartDT.getClassCd() != null
						&& entLocPartDT.getClassCd()
								.equals(NEDSSConstants.TELE)) {
					if (entLocPartDT.getCd() != null
							&& entLocPartDT.getCd()
									.equals(NEDSSConstants.PHONE)) {
						if (entLocPartDT.getTheTeleLocatorDT() != null) {
							TeleLocatorDT teleLocDT = entLocPartDT
									.getTheTeleLocatorDT();
							if (teleLocDT.getPhoneNbrTxt() != null
									&& !teleLocDT.getPhoneNbrTxt().equals(""))
								nameTeleStr = carrot
										+ teleLocDT.getPhoneNbrTxt();

						}
					}
				}
			}
		}
		if (nameTeleStr != null) {
			nameTeleStr = placeVO.getThePlaceDT().getNm() + nameTeleStr;
		}
		return nameTeleStr;
	}

	public void matchOrganization(ArrayList<Object> orgList,
			NBSSecurityObj nbsSecurityObj) throws Exception {
		if (orgList != null && orgList.size() > 0) {
			Iterator iter = orgList.iterator();
			while (iter.hasNext()) {
				OrganizationDT orgDT = (OrganizationDT) iter.next();
				OrganizationVO organizationVO = new OrganizationVO();
				try {
					EntityController entityController = getEntityController();
					organizationVO = entityController.getOrganization(
							orgDT.getOrganizationUid(), nbsSecurityObj);
					setOrganizationtoEntityMatch(organizationVO, nbsSecurityObj);
				} catch (Exception e) {
					logger.error("Error while getting the entityController.organization  or while inserting to entityController match "
							+ e.getMessage());
					throw new NEDSSAppException(e.getMessage(), e);
				}
			}
		}

	}

	public EntityController getEntityController() {
		NedssUtils nedssUtils = new NedssUtils();
		EntityController entityController = null;
		try {
			// Get the EntityController
			Object lookedUpObj = nedssUtils
					.lookupBean(JNDINames.EntityControllerEJB);
			logger.debug("!!!!!!!!!!EntityController lookup = "
					+ lookedUpObj.toString());
			EntityControllerHome ecHome = (EntityControllerHome) PortableRemoteObject
					.narrow(lookedUpObj, EntityControllerHome.class);
			logger.debug("!!!!!!!!!!Found EntityControllerHome: " + ecHome);
			entityController = ecHome.create();

		} catch (Exception et) {
			String errorString = "entityController.  Error while  trying the EDX Edx Match for testing  - "
					+ et.getMessage();
			logger.error(errorString + et.getMessage());
			throw new NEDSSSystemException(errorString + et.getMessage(), et);
		}
		return entityController;
	}

	public void setOrganizationtoEntityMatch(OrganizationVO organizationVO,
			NBSSecurityObj nbsSecurityObj) throws Exception {
		Long entityUid = organizationVO.getTheOrganizationDT()
				.getOrganizationUid();
	//	String orgRole = organizationVO.getRole();
		EdxEntityMatchDAO edxDao = new EdxEntityMatchDAO();

		// the Identifier (CLIA)
		String identifier = null;
		int identifierHshCd = 0;
		List identifierList = null;
		identifierList = getIdentifier(organizationVO);
		if (identifierList != null && !identifierList.isEmpty()) {
			for (int k = 0; k < identifierList.size(); k++) {
				identifier = (String) identifierList.get(k);
				if (identifier != null)
					identifier = identifier.toUpperCase();
				identifierHshCd = identifier.hashCode();
				if (identifier != null) {
					EdxEntityMatchDT edxEntityMatchDT = new EdxEntityMatchDT();
					edxEntityMatchDT.setEntityUid(entityUid);
					edxEntityMatchDT.setTypeCd(NEDSSConstants.ORGANIZATION);
					edxEntityMatchDT.setMatchString(identifier);
					edxEntityMatchDT.setMatchStringHashCode(new Long(
							identifierHshCd));
					try {
						edxDao.setEdxEntityMatchDT(edxEntityMatchDT);
					} catch (Exception e) {
						logger.error("Error in creating the EdxEntityMatchDT with identifier:"
								+ identifier + " " + e.getMessage());
						throw new NEDSSAppException(e.getMessage(), e);
					}
				}

			}
		}
		// with name and address with street address1 alone
		String nameAddStrSt1 = null;
		int nameAddStrSt1hshCd = 0;
		nameAddStrSt1 = nameAddressStreetOne(organizationVO);
		if (nameAddStrSt1 != null) {
			nameAddStrSt1 = nameAddStrSt1.toUpperCase();
			nameAddStrSt1hshCd = nameAddStrSt1.hashCode();
		}
		// Continue for name Telephone with no extension
		String nameTelePhone = null;
		int nameTelePhonehshCd = 0;
		nameTelePhone = telePhoneTxt(organizationVO);
		if (nameTelePhone != null) {
			nameTelePhone = nameTelePhone.toUpperCase();
			nameTelePhonehshCd = nameTelePhone.hashCode();
		}

		// Create the name and address with street 2
		EdxEntityMatchDT edxEntityMatchDT = null;
		if (nameAddStrSt1 != null) {
			edxEntityMatchDT = new EdxEntityMatchDT();
			edxEntityMatchDT.setEntityUid(entityUid);
			edxEntityMatchDT.setTypeCd(NEDSSConstants.ORGANIZATION);
			edxEntityMatchDT.setMatchString(nameAddStrSt1);
			edxEntityMatchDT
					.setMatchStringHashCode(new Long(nameAddStrSt1hshCd));
			try {
				edxDao.setEdxEntityMatchDT(edxEntityMatchDT);

			} catch (Exception e) {
				logger.error("Error in creating the EdxEntityMatchDT with nameAddStrSt1:"
						+ nameAddStrSt1 + " " + e.getMessage());
				throw new NEDSSAppException(e.getMessage(), e);
			}

		}

		// Create the name and address with nameTelePhone
		if (nameTelePhone != null) {
			edxEntityMatchDT = new EdxEntityMatchDT();
			edxEntityMatchDT.setEntityUid(entityUid);
			edxEntityMatchDT.setTypeCd(NEDSSConstants.ORGANIZATION);
			edxEntityMatchDT.setMatchString(nameTelePhone);
			edxEntityMatchDT
					.setMatchStringHashCode(new Long(nameTelePhonehshCd));
			try {
				edxDao.setEdxEntityMatchDT(edxEntityMatchDT);

			} catch (Exception e) {
				logger.error("Error in creating the EdxEntityMatchDT with nameTelePhone:"
						+ nameTelePhone + " " + e.getMessage());
				throw new NEDSSAppException(e.getMessage(), e);
			}
		}

		if (edxEntityMatchDT != null) {

			edxDao.updateMPR(edxEntityMatchDT);
		}

	}

	public void matchProvider(ArrayList<Object> provList,
			NBSSecurityObj nbsSecurityObj) throws Exception {

		if (provList != null && provList.size() > 0) {
			Iterator iter = provList.iterator();
			while (iter.hasNext()) {
				PersonDT personDT = (PersonDT) iter.next();
				PersonVO personVO = new PersonVO();
				try {
					EntityController entityController = getEntityController();
					personVO = entityController.getProvider(
							personDT.getPersonUid(), nbsSecurityObj);
					setProvidertoEntityMatch(personVO, nbsSecurityObj);
				} catch (Exception e) {
					logger.error("Error while getting the entityController.provider  or while inserting to setProvidertoEntityMatch  "
							+ e.getMessage());
					throw new NEDSSAppException(e.getMessage(), e);
				}
			}
		}

	}

	public String formatPhoneNumber(String phoneNumber) {
		String formattedPhoneNumber = "";
		if (phoneNumber.indexOf("-") == 3 && phoneNumber.lastIndexOf("-") == 4
				&& phoneNumber.length() == 5) {
			formattedPhoneNumber = phoneNumber.substring(0, 4);
			System.out.println("\n The  formattedPhoneNumber =  "
					+ formattedPhoneNumber);
		} else if (phoneNumber.indexOf("-") == 0
				&& phoneNumber.lastIndexOf("-") == 1) {
			formattedPhoneNumber = phoneNumber.substring(1, 6);
			System.out.println("\n The  formattedPhoneNumber =  "
					+ formattedPhoneNumber);
		} else
			formattedPhoneNumber = (phoneNumber.substring(0, 7) + "-" + phoneNumber
					.substring(7, 11));

		// formattedPhoneNumber = phoneNumber.format("%s-%s-%s",
		// phoneNumber.substring(0, 3), phoneNumber.substring(4, 7),
		// phoneNumber.substring(7, 11));

		return formattedPhoneNumber;

	}

	public void setProvidertoEntityMatch(PersonVO personVO,
			NBSSecurityObj nbsSecurityObj) throws Exception {

		Long entityUid = personVO.getThePersonDT().getPersonUid();
	//	String providerRole = personVO.getRole();

		// For identifier
		String identifier = null;
		int identifierHshCd = 0;
		List identifierList = null;
	//	if (providerRole == null
	//			|| !providerRole.equalsIgnoreCase(EdxELRConstants.ELR_PROV_CD)) {
			identifierList = getIdentifier(personVO);
		//} else {
		//	identifierList = getIdentifierForELR(personVO);
	//	}
		// List identifierList = getIdentifier(personVO);
		EdxEntityMatchDAO edxDao = new EdxEntityMatchDAO();
		if (identifierList != null && !identifierList.isEmpty()) {
			for (int k = 0; k < identifierList.size(); k++) {
				identifier = (String) identifierList.get(k);
				if (identifier != null)
					identifier = identifier.toUpperCase();
				identifierHshCd = identifier.hashCode();
				if (identifier != null) {
					EdxEntityMatchDT edxEntityMatchDT = new EdxEntityMatchDT();
					edxEntityMatchDT.setEntityUid(entityUid);
					edxEntityMatchDT.setTypeCd(NEDSSConstants.PRV);
					edxEntityMatchDT.setMatchString(identifier);
					edxEntityMatchDT.setMatchStringHashCode(new Long(
							identifierHshCd));
					try {
						edxDao.setEdxEntityMatchDT(edxEntityMatchDT);
					} catch (Exception e) {
						logger.error("Error in creating the EdxEntityMatchDT with identifier:"
								+ identifier + " " + e.getMessage());
						throw new NEDSSAppException(e.getMessage(), e);
					}
				}

			}

		}

		// Matching with name and address with street address1 alone
		String nameAddStrSt1 = null;
		int nameAddStrSt1hshCd = 0;
		nameAddStrSt1 = nameAddressStreetOne(personVO);
		if (nameAddStrSt1 != null) {
			nameAddStrSt1 = nameAddStrSt1.toUpperCase();
			nameAddStrSt1hshCd = nameAddStrSt1.hashCode();
		}

		// Continue for name Telephone with no extension
		String nameTelePhone = null;
		int nameTelePhonehshCd = 0;
		nameTelePhone = telePhoneTxt(personVO);
		if (nameTelePhone != null) {
			nameTelePhone = nameTelePhone.toUpperCase();
			nameTelePhonehshCd = nameTelePhone.hashCode();
		}

		EdxEntityMatchDT edxEntityMatchDT = null;
		// Create the name and address with no street 2(only street1)
		if (nameAddStrSt1 != null) {
			edxEntityMatchDT = new EdxEntityMatchDT();
			edxEntityMatchDT.setEntityUid(entityUid);
			edxEntityMatchDT.setTypeCd(NEDSSConstants.PRV);
			edxEntityMatchDT.setMatchString(nameAddStrSt1);
			edxEntityMatchDT
					.setMatchStringHashCode(new Long(nameAddStrSt1hshCd));
			try {
				edxDao.setEdxEntityMatchDT(edxEntityMatchDT);
			} catch (Exception e) {
				logger.error("Error in creating the EdxEntityMatchDT with nameAddStrSt1:"
						+ nameAddStrSt1 + " " + e.getMessage());
				throw new NEDSSAppException(e.getMessage(), e);
			}

		}
		// Create the name and address with nameTelePhone
		if (nameTelePhone != null) {
			edxEntityMatchDT = new EdxEntityMatchDT();
			edxEntityMatchDT.setEntityUid(entityUid);
			edxEntityMatchDT.setTypeCd(NEDSSConstants.PRV);
			edxEntityMatchDT.setMatchString(nameTelePhone);
			edxEntityMatchDT
					.setMatchStringHashCode(new Long(nameTelePhonehshCd));
			try {
				edxDao.setEdxEntityMatchDT(edxEntityMatchDT);
			} catch (Exception e) {
				logger.error("Error in creating the EdxEntityMatchDT with nameTelePhone:"
						+ nameTelePhone + " " + e.getMessage());
				throw new NEDSSAppException(e.getMessage(), e);
			}
		}
		if (edxEntityMatchDT != null) {
			edxDao.updateMPR(edxEntityMatchDT);
		}

	}

}
