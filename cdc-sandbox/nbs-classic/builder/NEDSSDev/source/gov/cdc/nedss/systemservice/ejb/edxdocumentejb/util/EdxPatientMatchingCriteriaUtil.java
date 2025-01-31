package gov.cdc.nedss.systemservice.ejb.edxdocumentejb.util;

/**
 * EdxPatientMatchingCriteriaUtil:  it defines a util Service within NBS framework to match the patient entities against the PHCR Document. 
 * The intent of this util class is to create match strings from the  PHCR Document and match with existing in edx_Patient_match table 
 * and if do not match create the new match string(s) and insert the new records to the table.  
 * @author: Sathya L
 * @Company: Saic
 * @since:Release 4.3
 */

import gov.cdc.nedss.controller.ejb.entitycontrollerejb.bean.EntityController;
import gov.cdc.nedss.controller.ejb.entitycontrollerejb.bean.EntityControllerHome;
import gov.cdc.nedss.entity.entityid.dt.EntityIdDT;
import gov.cdc.nedss.entity.person.dt.PersonDT;
import gov.cdc.nedss.entity.person.dt.PersonNameDT;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.locator.dt.EntityLocatorParticipationDT;
import gov.cdc.nedss.locator.dt.PostalLocatorDT;
import gov.cdc.nedss.locator.dt.TeleLocatorDT;
import gov.cdc.nedss.nnd.ejb.nndmessageprocessorejb.dao.NotificationSRTCodeLookupTranslationDAOImpl;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dao.EdxPatientMatchDAO;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dt.EdxPatientMatchDT;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.labutil.EdxELRConstants;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.Coded;
import gov.cdc.nedss.util.DataTables;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.rmi.PortableRemoteObject;

public class EdxPatientMatchingCriteriaUtil {
	public boolean multipleMatchFound = false;

	private static final LogUtils logger = new LogUtils(
			EdxPatientMatchingCriteriaUtil.class.getName());

	public EdxPatientMatchDT getMatchingPatient(PersonVO personVO,
			NBSSecurityObj nbsSecurityObj) throws Exception {
		Long patientUid = personVO.getThePersonDT().getPersonUid();
		String cd = personVO.getThePersonDT().getCd();
		String patientRole = personVO.getRole();
		EdxPatientMatchDAO edxPatientMatchDAO = new EdxPatientMatchDAO();
		EdxPatientMatchDT edxPatientFoundDT = null;
		EdxPatientMatchDT edxPatientMatchFoundDT = null;
		Long patientPersonUid = null;
		EdxPatientMatchDAO edxPtDao = new EdxPatientMatchDAO();
		boolean matchFound = false;
		boolean lrIDExists = true;

		if (patientRole == null
				|| patientRole.equalsIgnoreCase("")|| patientRole
						.equalsIgnoreCase(EdxELRConstants.ELR_PATIENT_ROLE_CD)
				) {
			EdxPatientMatchDT localIdHashCode = null;
			String localId = null;
			int localIdhshCd = 0;
			localId = getLocalId(personVO);
			if (localId != null) {
				localId = localId.toUpperCase();
				localIdhshCd = localId.hashCode();
			}
			try {
				// Try to get the matching with the match string 
				//	(was hash code but hash code had dups on rare occasions)
				edxPatientMatchFoundDT = edxPatientMatchDAO
						.getEdxPatientMatchOnMatchString(cd, localId);
				if (edxPatientMatchFoundDT.isMultipleMatch()){
			//			&& (patientRole !=null && !patientRole.equals("") && patientRole
			//					.equalsIgnoreCase(EdxELRConstants.ELR_PATIENT_ROLE_CD))) {
					multipleMatchFound = true;
					matchFound = false;
				} else if (edxPatientMatchFoundDT != null
						&& edxPatientMatchFoundDT.getPatientUid() != null) {
					matchFound = true;

				} else {
					lrIDExists = false;
				}

			} catch (Exception ex) {
				logger.error("Error in geting the  matching Patient");
				throw new NEDSSAppException(
						"Error in geting the  matching Patient"
								+ ex.getMessage(), ex);
			}
			if (localId != null) {
				localIdHashCode = new EdxPatientMatchDT();
				localIdHashCode.setTypeCd(NEDSSConstants.PAT);
				localIdHashCode.setMatchString(localId);
				localIdHashCode.setMatchStringHashCode(new Long(localIdhshCd));
			}

			// get Identifier
			if (!matchFound) {
				String IdentifierStr = null;
				int identifierStrhshCd = 0;

				List identifierStrList = getIdentifier(personVO);
				if (identifierStrList != null && !identifierStrList.isEmpty()) {
					for (int k = 0; k < identifierStrList.size(); k++) {
						edxPatientFoundDT = new EdxPatientMatchDT();
						IdentifierStr = (String) identifierStrList.get(k);
						if (IdentifierStr != null) {
							IdentifierStr = IdentifierStr.toUpperCase();
							identifierStrhshCd = IdentifierStr.hashCode();
						}

						if (IdentifierStr != null) {
							edxPatientFoundDT = new EdxPatientMatchDT();
							edxPatientFoundDT.setTypeCd(NEDSSConstants.PAT);
							edxPatientFoundDT.setMatchString(IdentifierStr);
							edxPatientFoundDT.setMatchStringHashCode(new Long(
									identifierStrhshCd));
							// Try to get the matching with the hash code
							edxPatientMatchFoundDT = edxPatientMatchDAO
									.getEdxPatientMatchOnMatchString(
											cd, IdentifierStr);
							if (edxPatientMatchFoundDT.isMultipleMatch()){
								//	&& patientRole !=null && !patientRole.equals("") && patientRole
								//			.equalsIgnoreCase(EdxELRConstants.ELR_PATIENT_ROLE_CD)) {
								matchFound = false;
								multipleMatchFound = true;
							} else if (edxPatientMatchFoundDT.getPatientUid() == null
									|| (edxPatientMatchFoundDT.getPatientUid() != null && edxPatientMatchFoundDT
											.getPatientUid() <= 0)) {
								matchFound = false;
							} else {
								matchFound = true;
								break;
							}
						}
					}
				}
			}
			// Matching with last name ,first name ,date of birth and current
			// sex
			if (!matchFound) {
				String namesdobcursexStr = null;
				int namesdobcursexStrhshCd = 0;
				namesdobcursexStr = getLNmFnmDobCurSexStr(personVO);
				if (namesdobcursexStr != null) {
					namesdobcursexStr = namesdobcursexStr.toUpperCase();
					namesdobcursexStrhshCd = namesdobcursexStr.hashCode();
					try {
						if (namesdobcursexStr != null) {
							edxPatientFoundDT = new EdxPatientMatchDT();
							edxPatientFoundDT.setPatientUid(patientUid);
							edxPatientFoundDT.setTypeCd(NEDSSConstants.PAT);
							edxPatientFoundDT.setMatchString(namesdobcursexStr);
							edxPatientFoundDT.setMatchStringHashCode(new Long(
									namesdobcursexStrhshCd));
						}
						// Try to get the matching with the match string 
						// (was hash code but hash code had dups)
						edxPatientMatchFoundDT = edxPatientMatchDAO
								.getEdxPatientMatchOnMatchString(
										cd, namesdobcursexStr);
						if (edxPatientMatchFoundDT.isMultipleMatch()){
							//	&& patientRole !=null && !patientRole.equals("") && patientRole
							//			.equalsIgnoreCase(EdxELRConstants.ELR_PATIENT_ROLE_CD)) {
							multipleMatchFound = true;
							matchFound = false;
						} else if (edxPatientMatchFoundDT.getPatientUid() == null
								|| (edxPatientMatchFoundDT.getPatientUid() != null && edxPatientMatchFoundDT
										.getPatientUid() <= 0)) {
							matchFound = false;
						} else {
							matchFound = true;
						}
					} catch (Exception ex) {
						logger.error("Error in geting the  matching Patient");
						throw new NEDSSAppException(
								"Error in geting the  matching Patient"
										+ ex.getMessage(), ex);
					}
				}
			}

			// Create the patient in case if the patient is not there in the DB
			if (!matchFound) {
				if (personVO.getTheEntityIdDTCollection() != null) {
					Collection<Object> newEntityIdDTColl = new ArrayList<Object>();
					Iterator<Object> iter = personVO
							.getTheEntityIdDTCollection().iterator();
					while (iter.hasNext()) {
						EntityIdDT entityIdDT = (EntityIdDT) iter.next();
						if (entityIdDT.getTypeCd() != null
								&& !entityIdDT.getTypeCd().equalsIgnoreCase(
										"LR")) {
							newEntityIdDTColl.add(entityIdDT);
						}
					}
					personVO.setTheEntityIdDTCollection(newEntityIdDTColl);
				}
				try {
					EntityController entityController = getEntityController();
					if (personVO.getThePersonDT().getCd()
							.equals(NEDSSConstants.PAT)) { // Patient
						patientPersonUid = entityController.setPerson(personVO,
								nbsSecurityObj);
						personVO.getThePersonDT().setPersonParentUid(
								patientPersonUid);
					}
				} catch (Exception e) {
					logger.error("Error in getting the entity Controller or Setting the Patient"
							+ e.getMessage(), e);
					throw new NEDSSAppException(
							"Error in getting the entity Controller or Setting the Patient"
									+ e.getMessage(), e);
				}
				personVO.setIsExistingPatient(false);
			}

			else {
				personVO.setIsExistingPatient(true);
			}

			try {
				if (patientPersonUid == null)
					personVO.getThePersonDT().setPersonParentUid(
							edxPatientMatchFoundDT.getPatientUid());
				else {
					personVO.getThePersonDT().setPersonParentUid(
							patientPersonUid);
				}
				EntityController entityController = getEntityController();
				patientUid = entityController.setPatientRevision(personVO,
						NEDSSConstants.PAT_CR, nbsSecurityObj);
				personVO.getThePersonDT().setPersonUid(patientUid);
			} catch (Exception e) {
				logger.error("Error in getting the entity Controller or Setting the Patient"
						+ e.getMessage());
				throw new NEDSSSystemException(
						"Error in getting the entity Controller or Setting the Patient"
								+ e.getMessage(), e);
			}

			// if LocalId not exists/match inserting the new record.
			if (!lrIDExists && localIdHashCode != null) {
				localIdHashCode.setPatientUid(personVO.getThePersonDT()
						.getPersonParentUid());
				edxPtDao.setEdxPatientMatchDT(localIdHashCode);
			}

		}

		else if (patientRole
				.equalsIgnoreCase(EdxELRConstants.ELR_NEXT_F_KIN_ROLE_CD)) {

			String nameAddStrSt1 = null;
			int nameAddStrSt1hshCd = 0;
			List nameAddressStreetOneStrList = nameAddressStreetOneNOK(personVO);
			if (nameAddressStreetOneStrList != null
					&& !nameAddressStreetOneStrList.isEmpty()) {
				for (int k = 0; k < nameAddressStreetOneStrList.size(); k++) {

					nameAddStrSt1 = (String) nameAddressStreetOneStrList.get(k);
					if (nameAddStrSt1 != null) {
						nameAddStrSt1 = nameAddStrSt1.toUpperCase();
						nameAddStrSt1hshCd = nameAddStrSt1.hashCode();
						try {
							if (nameAddStrSt1 != null) {
								edxPatientFoundDT = new EdxPatientMatchDT();
								edxPatientFoundDT.setPatientUid(patientUid);
								edxPatientFoundDT.setTypeCd(NEDSSConstants.NOK);
								edxPatientFoundDT.setMatchString(nameAddStrSt1);
								edxPatientFoundDT
										.setMatchStringHashCode(new Long(
												nameAddStrSt1hshCd));
							}
							// Try to get the Next of Kin matching with the match string
							edxPatientMatchFoundDT = edxPatientMatchDAO
									.getEdxPatientMatchOnMatchString(
											edxPatientFoundDT.getTypeCd(),
											nameAddStrSt1);
							if (edxPatientMatchFoundDT.getPatientUid() == null
									|| (edxPatientMatchFoundDT.getPatientUid() != null 
									&& edxPatientMatchFoundDT.getPatientUid() <= 0)) {
								matchFound = false;
							} else {
								matchFound = true;
							}
						} catch (Exception ex) {
							logger.error("Error in geting the  matching Next of Kin");
							throw new NEDSSAppException(
									"Error in geting the  matching Next of Kin"
											+ ex.getMessage(), ex);
						}
					}
				}
			}

			if (!matchFound) {
				String nameTelePhone = null;
				int nameTelePhonehshCd = 0;
				// nameTelePhone = telePhoneTxt(personVO);
				List nameTelePhoneStrList = telePhoneTxtNOK(personVO);
				if (nameTelePhoneStrList != null
						&& !nameTelePhoneStrList.isEmpty()) {
					for (int k = 0; k < nameTelePhoneStrList.size(); k++) {
						nameTelePhone = (String) nameTelePhoneStrList.get(k);
						if (nameTelePhone != null) {
							nameTelePhone = nameTelePhone.toUpperCase();
							nameTelePhonehshCd = nameTelePhone.hashCode();
							try {
								if (nameTelePhone != null) {
									edxPatientFoundDT = new EdxPatientMatchDT();
									edxPatientFoundDT.setPatientUid(patientUid);
									edxPatientFoundDT
											.setTypeCd(NEDSSConstants.NOK);
									edxPatientFoundDT
											.setMatchString(nameTelePhone);
									edxPatientFoundDT
											.setMatchStringHashCode(new Long(
													nameTelePhonehshCd));
								}
								// Try to get the matching with the match string
								edxPatientMatchFoundDT = edxPatientMatchDAO
										.getEdxPatientMatchOnMatchString(
												edxPatientFoundDT.getTypeCd(),
												nameTelePhone);
								if (edxPatientMatchFoundDT.getPatientUid() == null
										|| (edxPatientMatchFoundDT
												.getPatientUid() != null && edxPatientMatchFoundDT
												.getPatientUid() <= 0)) {
									matchFound = false;
								} else {
									matchFound = true;
								}
							} catch (Exception ex) {
								logger.error("Error in geting the  matching Patient");
								throw new NEDSSAppException(
										"Error in geting the  matching Patient"
												+ ex.getMessage(), ex);
							}
						}
					}
				}
			}
			if (!matchFound) {
				if (personVO.getTheEntityIdDTCollection() != null) {
					Collection<Object> newEntityIdDTColl = new ArrayList<Object>();
					Iterator<Object> iter = personVO
							.getTheEntityIdDTCollection().iterator();
					while (iter.hasNext()) {
						EntityIdDT entityIdDT = (EntityIdDT) iter.next();
						if (entityIdDT.getTypeCd() != null
								&& !entityIdDT.getTypeCd().equalsIgnoreCase(
										"LR")) {
							newEntityIdDTColl.add(entityIdDT);
						}
					}
					personVO.setTheEntityIdDTCollection(newEntityIdDTColl);
				}
				try {
					EntityController entityController = getEntityController();
					if (personVO.getThePersonDT().getCd()
							.equals(NEDSSConstants.PAT)) { // Patient
						patientPersonUid = entityController.setPerson(personVO,
								nbsSecurityObj);
						personVO.getThePersonDT().setPersonParentUid(
								patientPersonUid);
					}
				} catch (Exception e) {
					logger.error("Error in getting the entity Controller or Setting the Patient"
							+ e.getMessage());
					throw new NEDSSAppException(
							"Error in getting the entity Controller or Setting the Patient"
									+ e.getMessage(), e);
				}
				personVO.setIsExistingPatient(false);
			}

			else {
				personVO.setIsExistingPatient(true);
			}

			try {
				if (patientPersonUid == null)
					personVO.getThePersonDT().setPersonParentUid(
							edxPatientMatchFoundDT.getPatientUid());
				else {
					personVO.getThePersonDT().setPersonParentUid(
							patientPersonUid);
				}
				EntityController entityController = getEntityController();
				patientUid = entityController.setPatientRevision(personVO,
						NEDSSConstants.PAT_CR, nbsSecurityObj);
				personVO.getThePersonDT().setPersonUid(patientUid);
			} catch (Exception e) {
				logger.error("Error in getting the entity Controller or Setting the Patient"
						+ e.getMessage());
				throw new NEDSSSystemException(
						"Error in getting the entity Controller or Setting the Patient"
								+ e.getMessage(), e);
			}
		}
		return edxPatientMatchFoundDT;
	}

	// getting local Id string from person VO
	private String getLocalId(PersonVO personVO) {
		String localId = null;
		if (personVO.getLocalIdentifier() != null) {
			localId = personVO.getLocalIdentifier();
		}
		return localId;
	}

	public void setPatientToEntityMatch(PersonVO personVO,
			NBSSecurityObj nbsSecurityObj) throws Exception {

		Long patientUid = personVO.getThePersonDT().getPersonUid();
		EdxPatientMatchDT edxPatientMatchDT = new EdxPatientMatchDT();
		String patientRole = personVO.getRole();
		String cdDescTxt = personVO.thePersonDT.getCdDescTxt();
		// Matching with IDValue,IDType Code,Assigning authority,Legal Last name
		// ,Legal First name-Identifier
		// if (patientRole == null ||
		// patientRole.equalsIgnoreCase(EdxELRConstants.ELR_PATIENT_ROLE_CD)||
		// patientRole.equalsIgnoreCase("")) {
		if (cdDescTxt == null || cdDescTxt.equalsIgnoreCase("") || !cdDescTxt.equalsIgnoreCase(EdxELRConstants.ELR_NOK_DESC)) {
			String identifierStr = null;
			int identifierStrhshCd = 0;
			EdxPatientMatchDAO edxPtDao = new EdxPatientMatchDAO();
			List identifierStrList = getIdentifier(personVO);
			if (identifierStrList != null && !identifierStrList.isEmpty()) {
				for (int k = 0; k < identifierStrList.size(); k++) {
					identifierStr = (String) identifierStrList.get(k);
					if (identifierStr != null) {
						identifierStr = identifierStr.toUpperCase();
						identifierStrhshCd = identifierStr.hashCode();
					}

					if (identifierStr != null) {
						edxPatientMatchDT = new EdxPatientMatchDT();
						edxPatientMatchDT.setPatientUid(patientUid);
						edxPatientMatchDT.setTypeCd(NEDSSConstants.PAT);
						edxPatientMatchDT.setMatchString(identifierStr);
						edxPatientMatchDT.setMatchStringHashCode(new Long(
								identifierStrhshCd));
						try {
							edxPtDao.setEdxPatientMatchDT(edxPatientMatchDT);
						} catch (Exception e) {
							logger.error("Error in creating the setEdxPatientMatchDT with identifierStr:"
									+ identifierStr + " " + e.getMessage());
							throw new NEDSSAppException(e.getMessage(), e);
						}

					}
				}
			}

			// Matching with last name ,first name ,date of birth and current
			// sex

			String namesdobcursexStr = null;
			int namesdobcursexStrhshCd = 0;
			namesdobcursexStr = getLNmFnmDobCurSexStr(personVO);
			if (namesdobcursexStr != null) {
				namesdobcursexStr = namesdobcursexStr.toUpperCase();
				namesdobcursexStrhshCd = namesdobcursexStr.hashCode();
			}

			if (namesdobcursexStr != null) {
				edxPatientMatchDT = new EdxPatientMatchDT();
				edxPatientMatchDT.setPatientUid(patientUid);
				edxPatientMatchDT.setTypeCd(NEDSSConstants.PAT);
				edxPatientMatchDT.setMatchString(namesdobcursexStr);
				edxPatientMatchDT.setMatchStringHashCode(new Long(
						namesdobcursexStrhshCd));
				try {
					edxPtDao.setEdxPatientMatchDT(edxPatientMatchDT);
				} catch (Exception e) {
					logger.error("Error in creating the setEdxPatientMatchDT with namesdobcursexStr:"
							+ namesdobcursexStr + " " + e.getMessage());
					throw new NEDSSAppException(e.getMessage(), e);
				}

			}
		}
		// else if
		// (patientRole.equalsIgnoreCase(EdxELRConstants.ELR_NEXT_F_KIN_ROLE_CD))
		// {
		if (cdDescTxt != null && cdDescTxt.equalsIgnoreCase(EdxELRConstants.ELR_NOK_DESC)) {
			EdxPatientMatchDAO edxPtDao = new EdxPatientMatchDAO();
			String nameAddStrSt1 = null;
			int nameAddStrSt1hshCd = 0;
			List nameAddressStreetOneStrList = nameAddressStreetOneNOK(personVO);
			if (nameAddressStreetOneStrList != null
					&& !nameAddressStreetOneStrList.isEmpty()) {
				for (int k = 0; k < nameAddressStreetOneStrList.size(); k++) {
					nameAddStrSt1 = (String) nameAddressStreetOneStrList.get(k);
					if (nameAddStrSt1 != null) {
						nameAddStrSt1 = nameAddStrSt1.toUpperCase();
						nameAddStrSt1hshCd = nameAddStrSt1.hashCode();
						if (nameAddStrSt1 != null) {
							edxPatientMatchDT = new EdxPatientMatchDT();
							edxPatientMatchDT.setPatientUid(patientUid);
							edxPatientMatchDT.setTypeCd(NEDSSConstants.NOK);
							edxPatientMatchDT.setMatchString(nameAddStrSt1);
							edxPatientMatchDT.setMatchStringHashCode(new Long(
									nameAddStrSt1hshCd));
							try {
								edxPtDao.setEdxPatientMatchDT(edxPatientMatchDT);
							} catch (Exception e) {
								logger.error("Error in creating the setEdxPatientMatchDT with nameAddString:"
										+ nameAddStrSt1 + " " + e.getMessage());
								throw new NEDSSAppException(e.getMessage(), e);
							}
						}

					}
				}
			}
			List nameTelePhoneStrList = telePhoneTxtNOK(personVO);
			String nameTelePhone = null;
			int nameTelePhonehshCd = 0;
			if (nameTelePhoneStrList != null && !nameTelePhoneStrList.isEmpty()) {
				for (int k = 0; k < nameTelePhoneStrList.size(); k++) {
					nameTelePhone = (String) nameTelePhoneStrList.get(k);
					if (nameTelePhone != null) {
						nameTelePhone = nameTelePhone.toUpperCase();
						nameTelePhonehshCd = nameTelePhone.hashCode();
						if (nameTelePhone != null) {
							edxPatientMatchDT = new EdxPatientMatchDT();
							edxPatientMatchDT.setPatientUid(patientUid);
							edxPatientMatchDT.setTypeCd(NEDSSConstants.NOK);
							edxPatientMatchDT.setMatchString(nameTelePhone);
							edxPatientMatchDT.setMatchStringHashCode(new Long(
									nameTelePhonehshCd));
							try {
								edxPtDao.setEdxPatientMatchDT(edxPatientMatchDT);

							} catch (Exception e) {
								logger.error("Error in creating the EdxEntityMatchDT with nameTelePhone:"
										+ nameTelePhone + " " + e.getMessage());
								throw new NEDSSAppException(e.getMessage(), e);
							}
						}

					}
				}// for loop
			}

		}// end of method
	}

	public void matchPatient(Long personUid, NBSSecurityObj nbsSecurityObj)
			throws Exception {

		if (personUid != null) {
			PersonVO personVO = new PersonVO();
			try {
				EntityController entityController = getEntityController();
				personVO = entityController
						.getPerson(personUid, nbsSecurityObj);
				setPatientToEntityMatch(personVO, nbsSecurityObj);
				// getMatchingPatient(personVO, nbsSecurityObj);
			} catch (Exception e) {
				logger.error("Error while getting the entityController.patient  or while inserting to setPatienttoEntityMatch  "
						+ e.getMessage());
				throw new NEDSSAppException(e.getMessage(), e);
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

	public String getLNmFnmDobCurSexStr(PersonVO personVO) {
		String namedobcursexStr = null;
		String carrot = "^";
		if (personVO.getThePersonDT() != null) {
			PersonDT personDT = personVO.getThePersonDT();
			if (personDT.getCd() != null
					&& personDT.getCd().equals(NEDSSConstants.PAT)) {
				if (personVO.getThePersonNameDTCollection() != null
						&& personVO.getThePersonNameDTCollection().size() > 0) {
					Collection<Object> personNameDTColl = personVO
							.getThePersonNameDTCollection();
					Iterator personNameIterator = personNameDTColl.iterator();
					Timestamp asofDate = null;
					while (personNameIterator.hasNext()) {
						PersonNameDT personNameDT = (PersonNameDT) personNameIterator
								.next();
						if (personNameDT.getNmUseCd() == null)
						{
							String Message = "personNameDT.getNmUseCd() is null";
							logger.debug(Message);
						}
						if (personNameDT.getNmUseCd() != null 
								&& personNameDT.getNmUseCd().equalsIgnoreCase("L")
								&& personNameDT.getRecordStatusCd() != null
								&& personNameDT.getRecordStatusCd().equals(
										NEDSSConstants.RECORD_STATUS_ACTIVE)) {
							if (asofDate == null
									|| (asofDate.getTime() < personNameDT
											.getAsOfDate().getTime())) {
								if ((personNameDT.getLastNm() != null)
										&& (!personNameDT.getLastNm().trim()
												.equals(""))
										&& (personNameDT.getFirstNm() != null)
										&& (!personNameDT.getFirstNm().trim()
												.equals(""))
										&& (personDT.getBirthTime() != null)
										&& (personDT.getCurrSexCd() != null)
										&& (!personDT.getCurrSexCd().trim()
												.equals(""))) {
									namedobcursexStr = personNameDT.getLastNm()
											+ carrot
											+ personNameDT.getFirstNm()
											+ carrot + personDT.getBirthTime()
											+ carrot + personDT.getCurrSexCd();
									asofDate = personNameDT.getAsOfDate();
								}
							} else if (asofDate.before(personNameDT
									.getAsOfDate())) {
								if ((personNameDT.getLastNm() != null)
										&& (!personNameDT.getLastNm().trim()
												.equals(""))
										&& (personNameDT.getFirstNm() != null)
										&& (!personNameDT.getFirstNm().trim()
												.equals(""))
										&& (personDT.getBirthTime() != null)
										&& (personDT.getCurrSexCd() != null)
										&& (!personDT.getCurrSexCd().trim()
												.equals(""))) {
									namedobcursexStr = personNameDT.getLastNm()
											+ carrot
											+ personNameDT.getFirstNm()
											+ carrot + personDT.getBirthTime()
											+ carrot + personDT.getCurrSexCd();
									asofDate = personNameDT.getAsOfDate();

								}

							}

						}
					}
				}
			}
		}
		return namedobcursexStr;
	}
	
	private List<String> getIdentifier(PersonVO personVO) {
		String carrot = "^";
		List<String> returnList =new ArrayList<String>();
		List<String> identifierList = new ArrayList<String>();
		String identifier = null;
		try{
		if (personVO.getTheEntityIdDTCollection() != null
				&& personVO.getTheEntityIdDTCollection().size() > 0) {
			Collection<Object> entityIdDTColl = personVO
					.getTheEntityIdDTCollection();
			Iterator<Object> entityIdIterator = entityIdDTColl.iterator();
			while (entityIdIterator.hasNext()) {
				identifier= null;
				EntityIdDT entityIdDT = (EntityIdDT) entityIdIterator.next();
				if (((entityIdDT.getStatusCd() != null && entityIdDT
						.getStatusCd().equalsIgnoreCase(NEDSSConstants.STATUS_ACTIVE))
						&& entityIdDT.getRecordStatusCd() != null
						&& (entityIdDT.getRecordStatusCd().equalsIgnoreCase(NEDSSConstants.RECORD_STATUS_ACTIVE))) 
						|| (entityIdDT.getRecordStatusCd() != null
								&& entityIdDT.getTypeCd()!=null  
								&& entityIdDT.getTypeCd().equalsIgnoreCase(EdxELRConstants.ELR_SS_TYPE)
						&& (entityIdDT.getRecordStatusCd().equalsIgnoreCase(NEDSSConstants.RECORD_STATUS_ACTIVE)))
								) {
					

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
								coded.setCodesetName(NEDSSConstants.EI_AUTH);
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

						if (identifier != null) {
							if (getNamesStr(personVO) != null) {
								identifier = identifier + carrot
										+ getNamesStr(personVO);
							identifierList.add(identifier);
						}
					}

				}
			}
		}
		HashSet<String> hashSet = new HashSet<String>(identifierList);
		returnList = new ArrayList<String>(hashSet) ;
		}
		catch (Exception ex) {
			String errorMessage = "Exception while creating hashcode for patient entity IDs . ";
					logger.debug(ex.getMessage() + errorMessage);
					throw new NEDSSSystemException(errorMessage, ex);
		}
		return returnList;
	}

	public String getNamesStr(PersonVO personVO) {
		String namesStr = null;
		String carrot = "^";
		if (personVO.getThePersonDT() != null) {
			PersonDT personDT = personVO.getThePersonDT();
			if (personDT.getCd() != null
					&& personDT.getCd().equals(NEDSSConstants.PAT)) {
				if (personVO.getThePersonNameDTCollection() != null
						&& personVO.getThePersonNameDTCollection().size() > 0) {
					Collection<Object> personNameDTColl = personVO
							.getThePersonNameDTCollection();
					Iterator<Object> personNameIterator = personNameDTColl
							.iterator();
					Timestamp asofDate = null;
					while (personNameIterator.hasNext()) {
						PersonNameDT personNameDT = (PersonNameDT) personNameIterator
								.next();
						if (personNameDT.getNmUseCd() != null
								&& personNameDT.getNmUseCd().equalsIgnoreCase(
										"L")
								&& personNameDT.getRecordStatusCd() != null
								&& personNameDT.getRecordStatusCd().equals(
										NEDSSConstants.RECORD_STATUS_ACTIVE)) {
							if (asofDate == null
									|| (asofDate.getTime() < personNameDT
											.getAsOfDate().getTime())) {
								if ((personNameDT.getLastNm() != null)
										&& (!personNameDT.getLastNm().trim()
												.equals(""))
										&& (personNameDT.getFirstNm() != null)
										&& (!personNameDT.getFirstNm().trim()
												.equals(""))) {
									namesStr = personNameDT.getLastNm()
											+ carrot
											+ personNameDT.getFirstNm();
									asofDate = personNameDT.getAsOfDate();

								}
							} else if (asofDate.before(personNameDT
									.getAsOfDate())) {
								if ((personNameDT.getLastNm() != null)
										&& (!personNameDT.getLastNm().trim()
												.equals(""))
										&& (personNameDT.getFirstNm() != null)
										&& (!personNameDT.getFirstNm().trim()
												.equals(""))) {
									namesStr = personNameDT.getLastNm()
											+ carrot
											+ personNameDT.getFirstNm();
									asofDate = personNameDT.getAsOfDate();
								}
							}
						}
					}
				}
			}
		}

		return namesStr;
	}

	// Creating string for name and address for patient
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
									NEDSSConstants.OFFICE_CD)) {
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
			nameAddStr = getNamesStr(personVO) + nameAddStr;
		return nameAddStr;
	}

	public List<String> nameAddressStreetOneNOK(PersonVO personVO) {
		String nameAddStr = null;
		String carrot = "^";
		List<String> nameAddressStreetOnelNOKist = new ArrayList();
		if (personVO.getTheEntityLocatorParticipationDTCollection() != null
				&& personVO.getTheEntityLocatorParticipationDTCollection()
						.size() > 0) {
			Iterator<Object> addIter = personVO
					.getTheEntityLocatorParticipationDTCollection().iterator();
			while (addIter.hasNext()) {
				EntityLocatorParticipationDT entLocPartDT = (EntityLocatorParticipationDT) addIter
						.next();
				if (entLocPartDT.getClassCd() != null
						&& entLocPartDT.getRecordStatusCd() !=null
						&& entLocPartDT.getRecordStatusCd().equalsIgnoreCase(NEDSSConstants.RECORD_STATUS_ACTIVE)
						&& entLocPartDT.getClassCd().equals(
								NEDSSConstants.POSTAL)) {
					if (entLocPartDT.getCd() != null) {
						PostalLocatorDT postLocDT = entLocPartDT
								.getThePostalLocatorDT();
						if (postLocDT != null) {
							if ((postLocDT.getStreetAddr1() != null 
									&& !postLocDT.getStreetAddr1().equals(""))
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
			if (nameAddStr != null)
				nameAddStr = getNamesStr(personVO) + nameAddStr;
			nameAddressStreetOnelNOKist.add(nameAddStr);

		}

		return nameAddressStreetOnelNOKist;
	}

public List<String> telePhoneTxtNOK(PersonVO personVO) {
		String nameTeleStr = null;
		String carrot = "^";
		List<String> telePhoneTxtList = new ArrayList();
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
								.equals(NEDSSConstants.TELE)
								&& entLocPartDT.getRecordStatusCd()!=null
								&& entLocPartDT.getRecordStatusCd().equalsIgnoreCase(NEDSSConstants.RECORD_STATUS_ACTIVE)) {
					if (entLocPartDT.getCd() != null) {
						TeleLocatorDT teleLocDT = entLocPartDT
								.getTheTeleLocatorDT();
						if (teleLocDT != null
								&& teleLocDT.getPhoneNbrTxt() != null
								&& !teleLocDT.getPhoneNbrTxt().equals(""))
							nameTeleStr = carrot + teleLocDT.getPhoneNbrTxt();

					}
					if (nameTeleStr != null) {

						if (getNamesStr(personVO) != null) {
							nameTeleStr = getNamesStr(personVO) + nameTeleStr;
							telePhoneTxtList.add(nameTeleStr);
						} else {
							return null;
						}
					}
				}
				
			}
		}

		return telePhoneTxtList;
	}
}
