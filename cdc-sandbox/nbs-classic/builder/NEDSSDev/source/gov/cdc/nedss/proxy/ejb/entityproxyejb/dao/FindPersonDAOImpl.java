/**
 * Name:		FindPersonDAOImpl.java
 * Description:	This is a class determine the DAO implementation based on
 *               the information provided in the deployment descriptor.
 * Copyright:	Copyright (c) 2001In
 * Company: 	Computer Sciences Corporation
 * @author	NEDSS Development Team
 * @version	1.0
 */

package gov.cdc.nedss.proxy.ejb.entityproxyejb.dao;

import gov.cdc.nedss.entity.entityid.dt.EntityIdDT;
import gov.cdc.nedss.entity.person.dt.PersonConditionDT;
import gov.cdc.nedss.entity.person.dt.PersonDT;
import gov.cdc.nedss.entity.person.dt.PersonNameDT;
import gov.cdc.nedss.entity.person.dt.PersonRaceDT;
import gov.cdc.nedss.entity.person.util.DisplayPersonList;
import gov.cdc.nedss.entity.person.vo.PatientRevisionSrchResultVO;
import gov.cdc.nedss.entity.person.vo.PatientSearchVO;
import gov.cdc.nedss.entity.person.vo.PatientSrchResultVO;
import gov.cdc.nedss.entity.person.vo.PersonSearchResultTmp;
import gov.cdc.nedss.entity.person.vo.PersonSearchVO;
import gov.cdc.nedss.entity.person.vo.PersonSrchResultVO;
import gov.cdc.nedss.entity.person.vo.ProviderSearchVO;
import gov.cdc.nedss.entity.person.vo.ProviderSrchResultVO;
import gov.cdc.nedss.entity.sqlscript.NEDSSSqlQuery;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.locator.dt.EntityLocatorParticipationDT;
import gov.cdc.nedss.locator.dt.PostalLocatorDT;
import gov.cdc.nedss.locator.dt.TeleLocatorDT;
import gov.cdc.nedss.proxy.ejb.tasklistproxyejb.dao.TaskListProxyDAOImpl;
import gov.cdc.nedss.proxy.util.PatientHashCodeModel;
import gov.cdc.nedss.proxy.util.PatientNOKHashCodeModel;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dt.EdxPatientMatchDT;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.DataTables;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

public class FindPersonDAOImpl extends SearchResultDAOImpl

{

	// For logging
	static final LogUtils logger = new LogUtils(
			FindPersonDAOImpl.class.getName());
	static final int MAX_CACHE_COUNT = 105;
	PropertyUtil propertyUtil = PropertyUtil.getInstance();
	
	  private static final String SELECT_PERSON = "SELECT distinct p.person_uid \"personUid\", p.person_parent_uid \"personParentUid\", p.administrative_gender_cd \"administrativeGenderCd\", p.add_reason_cd \"addReasonCd\", p.add_time \"addTime\", p.add_user_id \"addUserId\", p.age_calc \"ageCalc\", p.age_calc_time \"ageCalcTime\", p.age_calc_unit_cd \"ageCalcUnitCd\", p.age_category_cd \"ageCategoryCd\", p.age_reported \"ageReported\", p.age_reported_time \"ageReportedTime\", p.age_reported_unit_cd \"ageReportedUnitCd\", p.as_of_date_admin \"asOfDateAdmin\", p.as_of_date_ethnicity \"asOfDateEthnicity\", p.as_of_date_general \"asOfDateGeneral\", p.as_of_date_morbidity \"asOfDateMorbidity\", p.as_of_date_sex \"asOfDateSex\", p.birth_gender_cd \"birthGenderCd\", p.birth_order_nbr \"birthOrderNbr\", p.birth_time \"birthTime\", p.birth_time_calc \"birthTimeCalc\", p.cd \"cd\", p.cd_desc_txt \"cdDescTxt\", p.curr_sex_cd \"currSexCd\", p.deceased_ind_cd \"deceasedIndCd\", p.deceased_time \"deceasedTime\", p.description \"description\", p.education_level_cd \"educationLevelCd\", p.education_level_desc_txt \"educationLevelDescTxt\", p.electronic_ind \"electronicInd\", p.ethnic_group_ind \"ethnicGroupInd\", p.last_chg_reason_cd \"lastChgReasonCd\", p.last_chg_time \"lastChgTime\", p.last_chg_user_id \"lastChgUserId\", p.marital_status_cd \"maritalStatusCd\", " +
			    " p.marital_status_desc_txt \"maritalStatusDescTxt\", p.mothers_maiden_nm \"mothersMaidenNm\", p.multiple_birth_ind \"multipleBirthInd\", " +
			    "p.occupation_cd \"occupationCd\", p.preferred_gender_cd \"preferredGenderCd\", p.prim_lang_cd \"primLangCd\", p.prim_lang_desc_txt \"primLangDescTxt\", p.record_status_cd \"recordStatusCd\", p.record_status_time \"recordStatusTime\", p.status_cd \"statusCd\", p.status_time \"statusTime\", p.survived_ind_cd \"survivedIndCd\", p.user_affiliation_txt \"userAffiliationTxt\", " +
			    "p.first_nm \"firstNm\", p.last_nm \"lastNm\", p.middle_nm \"middleNm\", p.nm_prefix \"nmPrefix\", p.nm_suffix \"nmSuffix\", p.preferred_nm \"preferredNm\", p.hm_street_addr1 \"hmStreetAddr1\", p.hm_street_addr2 \"hmStreetAddr2\", p.hm_city_cd \"hmCityCd\", p.hm_city_desc_txt \"hmCityDescTxt\", p.hm_state_cd \"hmStateCd\", p.hm_zip_cd \"hmZipCd\", p.hm_cnty_cd \"hmCntyCd\", p.hm_cntry_cd \"hmCntryCd\", p.hm_phone_nbr \"hmPhoneNbr\", p.hm_phone_cntry_cd \"hmPhoneCntryCd\", p.hm_email_addr \"hmEmailAddr\", p.cell_phone_nbr \"cellPhoneNbr\", p.wk_street_addr1 \"wkStreetAddr1\", p.wk_street_addr2 \"wkStreetAddr2\", p.wk_city_cd \"wkCityCd\", p.wk_city_desc_txt \"wkCityDescTxt\", p.wk_state_cd \"wkStateCd\", p.wk_zip_cd \"wkZipCd\", p.wk_cnty_cd \"wkCntyCd\", p.wk_cntry_cd \"wkCntryCd\", p.wk_phone_nbr \"wkPhoneNbr\", p.wk_phone_cntry_cd \"wkPhoneCntryCd\", p.wk_email_addr \"wkEmailAddr\", p.SSN \"SSN\", p.medicaid_num \"medicaidNum\", p.dl_num \"dlNum\", p.dl_state_cd \"dlStateCd\", p.race_cd \"raceCd\", p.race_seq_nbr \"raceSeqNbr\"," +
			    " p.race_category_cd \"raceCategoryCd\", p.ethnicity_group_cd \"ethnicityGroupCd\", p.ethnic_group_seq_nbr \"ethnicGroupSeqNbr\", p.ethnic_group_desc_txt \"ethnicGroupDescTxt\", p.adults_in_house_nbr \"adultsInHouseNbr\", " +
			    "p.children_in_house_nbr \"childrenInHouseNbr\", p.birth_city_cd \"birthCityCd\", p.birth_city_desc_txt \"birthCityDescTxt\", p.birth_cntry_cd \"birthCntryCd\", p.birth_state_cd \"birthStateCd\", p.race_desc_txt \"raceDescTxt\", p.local_id \"localId\", p.version_ctrl_nbr \"versionCtrlNbr\" , p.group_nbr \"groupNbr\" , p.group_time \"groupTime\", p.edx_ind \"edxInd\", p.speaks_english_cd \"speaksEnglishCd\", p.additional_gender_cd \"additionalGenderCd\", p.ehars_id \"eharsId\", p.dedup_match_ind \"dedupMatchInd\" FROM ";
			    
	private static final String SELECT_PERSON_BY_EPILINK_COLLECTION = SELECT_PERSON + "  Act a  with (nolock), Entity e with (nolock) , NBS_act_entity ae  with (nolock), Public_health_case phc  with (nolock), Person p  with (nolock) "
			+ "where   phc.public_health_case_uid = a.act_uid and "
			+ "ae.act_uid = a.act_uid and "
			+ "ae.entity_uid = p.person_uid and "
			+ "phc.public_health_case_uid in "
			+ "(select phc.public_health_case_uid "
				+ "from case_management cm with (nolock), Public_health_case  phc with (nolock) "
				+ "where cm.epi_link_id in "
					+ "(select  cm.epi_link_id  "
					+ "from case_management cm  with (nolock) "
					+ "where cm.public_health_case_uid = ?) "
				+ "and cm.public_health_case_uid = phc.public_health_case_uid )";
	  

	
	public FindPersonDAOImpl() throws NEDSSSystemException {
	}

	/**
	 * Formats a String by removing specified characters with another set of
	 * specified characters
	 *
	 * @param toBeRepaced
	 *            is the characters to be replaced
	 * @param specialCharacter
	 * @param replacement
	 *            are the characters to replace the characters being removed
	 * @return String with characters replaced.
	 */
	private String replaceCharacters(String toBeRepaced,
			String specialCharacter, String replacement) {
		int s = 0;
		int e = 0;
			StringBuffer result = new StringBuffer();
	
			try{
			while ((e = toBeRepaced.indexOf(specialCharacter, s)) >= 0) {
				result.append(toBeRepaced.substring(s, e));
				result.append(replacement);
				s = e + specialCharacter.length();
			}
			result.append(toBeRepaced.substring(s));
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return result.toString();
	}

	public ArrayList<Object> findProvidersByKeyWords(ProviderSearchVO find,
			int cacheNumber, int fromIndex) throws NEDSSSystemException {
		ArrayList<Object> personNameColl = null;
		ArrayList<Object> personAddressColl = null;
		ArrayList<Object> personTeleColl = null;
		ArrayList<Object> personEntityIdColl = null;
		ArrayList<Object> personEthnicityColl = null;
		ArrayList<Object> personRaceColl = null;
		ArrayList<Object> personRoleColl = null;
		String whereClause = null;
		DisplayPersonList displayPersonList = null;
		int totalCount = 0;
		int listCount = 0;
		
		try{
			/* Has user entered any person and name field for search? */
			if ((find.getBirthTime() != null && find.getBirthTime().trim().length() != 0)
					|| (find.getCurrentSex() != null && find.getCurrentSex().trim()
							.length() != 0)
					|| (find.getLocalID() != null && find.getLocalID().trim()
							.length() != 0)
					|| (find.getLastName() != null && find.getLastName().trim()
							.length() != 0)
					|| (find.getFirstName() != null && find.getFirstName().trim()
							.length() != 0)) {
				whereClause = buildNameWhereClause(find);
				whereClause = whereClause + buildStatusWhereClause(find);
				// Add condition to sort search result by lastName and FirstName
				whereClause = whereClause + "order by p.last_nm, p.first_nm";
	
				// add person code filter to where clause
				// addPersonCodeFilterToWhereClause(whereClause, find);
				// end
				personNameColl = runNameQuery(whereClause);
				if (personNameColl != null)
					logger.debug("Size of name collection is: "
							+ personNameColl.size());
	
			}
	
			/* Has user entered any address information */
			if ((find.getStreetAddr1() != null && find.getStreetAddr1().trim()
					.length() != 0)
					|| (find.getCityDescTxt() != null && find.getCityDescTxt()
							.trim().length() != 0)
					|| (find.getState() != null && find.getState().trim().length() != 0)
					|| (find.getZipCd() != null && find.getZipCd().trim().length() != 0)) {
				whereClause = buildAddressWhereClause(find);
				whereClause = whereClause + buildStatusWhereClause(find);
				// Add condition to sort search result by lastName and FirstName
				whereClause = whereClause + "order by p.last_nm, p.first_nm";
	
				// add person code filter to where clause
				// addPersonCodeFilterToWhereClause(whereClause, find);
				// end
	
				personAddressColl = runAddressQuery(whereClause);
				if (personAddressColl != null)
					logger.debug("Size of address collection is: "
							+ personAddressColl.size());
			}
			/* Has user entered telephone number for search */
			if (find.getPhoneNbrTxt() != null
					&& find.getPhoneNbrTxt().trim().length() != 0) {
				whereClause = buildTeleWhereClause(find);
				whereClause = whereClause + buildStatusWhereClause(find);
				whereClause = whereClause + "order by p.last_nm, p.first_nm";
	
				// add person code filter to where clause
				// addPersonCodeFilterToWhereClause(whereClause, find);
				// end
				personTeleColl = runTeleQuery(whereClause);
				if (personTeleColl != null)
					logger.debug("Size of phone collection is: "
							+ personTeleColl.size());
	
			}
			/* Has user entered any EntityId information */
			if (find.getRootExtensionTxt() != null
					&& find.getRootExtensionTxt().trim().length() != 0) {
				whereClause = buildEntityIdWhereClause(find);
				// whereClause = whereClause + buildStatusWhereClause(find);
				// add person code filter to where clause
				// addPersonCodeFilterToWhereClause(whereClause, find);
				// end
				whereClause = whereClause + "order by p.last_nm, p.first_nm";
	
				personEntityIdColl = runEntityIdQuery(whereClause);
				if (personEntityIdColl != null)
					logger.debug("Size of entity ID collection is: "
							+ personEntityIdColl.size());
			}
			/* Has user entered Ethnicity */
			if (find.getEthnicGroupInd() != null
					&& find.getEthnicGroupInd().trim().length() != 0) {
				whereClause = buildEthnicityWhereClause(find);
				whereClause = whereClause + buildStatusWhereClause(find);
				logger.debug("Ethnicity where clauese is: " + whereClause);
	
				// add person code filter to where clause
				// addPersonCodeFilterToWhereClause(whereClause, find);
				// end
				whereClause = whereClause + "order by p.last_nm, p.first_nm";
	
				personEthnicityColl = runEthnicityQuery(whereClause);
				if (personEthnicityColl != null)
					logger.debug("Size of ethnicity collection is: "
							+ personEthnicityColl.size());
			}
			/* Has user entered Race */
			if (find.getRaceCd() != null && find.getRaceCd().trim().length() != 0) {
				whereClause = buildRaceWhereClause(find);
				whereClause = whereClause + buildStatusWhereClause(find);
				whereClause = whereClause + "order by p.last_nm, p.first_nm";
				// add person code filter to where clause
				// addPersonCodeFilterToWhereClause(whereClause, find);
				// end
				personRaceColl = runRaceQuery(whereClause);
				if (personRaceColl != null)
					logger.debug("Size of race collection is: "
							+ personRaceColl.size());
			}
			/* Has user entered Role */
			if (find.getRole() != null && find.getRole().trim().length() != 0) {
				whereClause = buildRoleWhereClause(find);
				whereClause = whereClause + buildStatusWhereClause(find);
				whereClause = whereClause + "order by p.last_nm, p.first_nm";
				// add person code filter to where clause
				// addPersonCodeFilterToWhereClause(whereClause, find);
				// end
				personRoleColl = runRoleQuery(whereClause);
				if (personRoleColl != null)
					logger.debug("Size of role collection is: "
							+ personRoleColl.size());
			}
			/* Find common PersonUIDs from all the queries */
			ArrayList<Object> idList = filterIDs(personNameColl, personAddressColl,
					personTeleColl, personEntityIdColl, personEthnicityColl,
					personRaceColl, personRoleColl);
			// logger.debug("Size of idList is: " + idList.size());
			logger.debug("Ids are: " + idList);
	
			/* generate where clause with those common personUIDs */
			whereClause = generateWhereClause(idList);
			if (whereClause != null) {
				personNameColl = runNameQuery(whereClause);
				personAddressColl = runAddressQuery(whereClause);
				personTeleColl = runTeleQuery(whereClause);
				personEntityIdColl = runEntityIdQuery(whereClause);
			}
			/*
			 * Iterate through IDList and find out attribute for each collection put
			 * them as personsearchresultVo and put it in ArrayList<Object>
			 */
			ArrayList<Object> searchResult = new ArrayList<Object>();
			CachedDropDownValues cache = new CachedDropDownValues();
			Iterator<Object> itr = idList.iterator();
			while (itr.hasNext()) {
				ProviderSrchResultVO srchResultVO = new ProviderSrchResultVO();
				ArrayList<Object> nameList = new ArrayList<Object>();
				Long personUid = (Long) itr.next();
				if (personNameColl != null) {
					Iterator<Object> nameItr = personNameColl.iterator();
					while (nameItr.hasNext()) {
						PersonSearchResultTmp tmp = (PersonSearchResultTmp) nameItr
								.next();
						if (tmp.getPersonUid().equals(personUid)) {
							if (!(tmp.getNameUseCd() == null)) {
								PersonNameDT nameDT = new PersonNameDT();
								nameDT.setPersonUid(personUid);
								nameDT.setFirstNm(tmp.getFirstName());
								nameDT.setLastNm(tmp.getLastName());
								if (tmp.getNmSuffix() != null
										&& tmp.getNmSuffix().trim().length() != 0) {
									TreeMap<?, ?> map = cache
											.getCodedValuesAsTreeMap("P_NM_SFX");
									map = cache.reverseMap(map); // we can add
																	// another
																	// method that
																	// do not do
																	// reverse
									nameDT.setNmSuffix((String) map.get(tmp
											.getNmSuffix()));
								}
								if (tmp.getNmDegree() != null
										&& tmp.getNmDegree().trim().length() != 0) {
									TreeMap<?, ?> map = cache
											.getCodedValuesAsTreeMap("P_NM_DEG");
									map = cache.reverseMap(map); // we can add
																	// another
																	// method that
																	// do not do
																	// reverse
									nameDT.setNmDegree((String) map.get(tmp
											.getNmDegree()));
								}
	
								// nameDT.setNmDegree(tmp.getNmDegree());
								if (tmp.getNameUseCd() != null
										&& tmp.getNameUseCd().trim().length() != 0) {
									TreeMap<?, ?> map = cache
											.getCodedValuesAsTreeMap("P_NM_USE");
									map = cache.reverseMap(map); // we can add
																	// another
																	// method that
																	// do not do
																	// reverse
									nameDT.setNmUseCd((String) map.get(tmp
											.getNameUseCd()));
								}
	
								// nameDT.setNmUseCd(tmp.getNameDesc());
								nameList.add(nameDT);
								srchResultVO.setPersonDOB(tmp.getDob());
								if (tmp.getCurrentSex() != null
										&& tmp.getCurrentSex().trim().length() != 0) {
									TreeMap<?, ?> map = cache
											.getCodedValuesAsTreeMap("SEX");
									map = cache.reverseMap(map); // we can add
																	// another
																	// method that
																	// do not do
																	// reverse
									srchResultVO.setCurrentSex((String) map.get(tmp
											.getCurrentSex()));
								}
								srchResultVO.setPersonUID(personUid);
								srchResultVO.setPersonLocalID(tmp.getLocalId());
								srchResultVO.setVersionCtrlNbr(tmp
										.getVersionCtrlNbr());
							}// if ( ! (tmp.getNameUseCd() == null))
						}// if (tmp.getPersonUid() == personUid)
					}// while (nameItr.hasNext())
				}
				srchResultVO.setPersonNameColl(nameList);
				// logger.debug("Number of names added : " + nameList.size());
				// for address
				ArrayList<Object> locatorList = new ArrayList<Object>();
	
				if (personAddressColl != null) {
					Iterator<Object> addressItr = personAddressColl.iterator();
					while (addressItr != null && addressItr.hasNext()) {
						EntityLocatorParticipationDT entityLocatorDT = new EntityLocatorParticipationDT();
						PersonSearchResultTmp tmp = (PersonSearchResultTmp) addressItr
								.next();
						if (tmp.getPersonUid().equals(personUid)) {
							if (tmp.getClassCd() != null) {
								srchResultVO.setPersonUID(personUid);
								srchResultVO.setVersionCtrlNbr(tmp
										.getVersionCtrlNbr());
								entityLocatorDT.setCd(tmp.getLocatorCd());
								// entityLocatorDT.setCdDescTxt(tmp.getLocatorTypeCdDesc());
								if (tmp.getLocatorCd() != null
										&& tmp.getLocatorCd().trim().length() != 0) {
									TreeMap<?, ?> map = cache
											.getCodedValuesAsTreeMap("EL_TYPE_PST_PRV");
									map = cache.reverseMap(map); // we can add
																	// another
																	// method that
																	// do not do
																	// reverse
									entityLocatorDT.setCdDescTxt((String) map
											.get(tmp.getLocatorCd()));
								}
								entityLocatorDT.setClassCd(tmp.getClassCd());
								entityLocatorDT.setEntityUid(personUid);
								entityLocatorDT.setLocatorUid(tmp.getLocatorUid());
								// entityLocatorDT.setUseCd(tmp.getLocatorUseCdDesc());
								if (tmp.getLocatorUseCd() != null
										&& tmp.getLocatorUseCd().trim().length() != 0) {
									TreeMap<?, ?> map = cache
											.getCodedValuesAsTreeMap("EL_USE_PST_PRV");
									map = cache.reverseMap(map); // we can add
																	// another
																	// method that
																	// do not do
																	// reverse
									entityLocatorDT.setUseCd((String) map.get(tmp
											.getLocatorUseCd()));
								}
	
								entityLocatorDT.setCd(tmp.getLocatorCd());
								PostalLocatorDT postalLocatorDT = new PostalLocatorDT();
								postalLocatorDT.setStateCd(tmp.getState());
								postalLocatorDT.setCityCd(tmp.getCity());
								postalLocatorDT.setCityDescTxt(tmp.getCity());
								postalLocatorDT
										.setStreetAddr1(tmp.getStreetAddr1());
								postalLocatorDT
										.setStreetAddr2(tmp.getStreetAddr2());
								postalLocatorDT.setPostalLocatorUid(tmp
										.getLocatorUid());
								postalLocatorDT.setZipCd(tmp.getZip());
								entityLocatorDT
										.setThePostalLocatorDT(postalLocatorDT);
								locatorList.add(entityLocatorDT);
								logger.debug("Added address locator: ");
							}
						}
					}// while (addressItr.hasNext())
						// logger.debug("Number of address added : " +
						// locatorList.size());
				}
	
				// tele locator
				if (personTeleColl != null) {
					Iterator<Object> teleItr = personTeleColl.iterator();
					while (teleItr != null && teleItr.hasNext()) {
						EntityLocatorParticipationDT entityLocatorDT = new EntityLocatorParticipationDT();
						PersonSearchResultTmp tmp = (PersonSearchResultTmp) teleItr
								.next();
						if (tmp.getPersonUid().equals(personUid)) {
							if (tmp.getClassCd() != null) {
								srchResultVO.setPersonUID(personUid);
								srchResultVO.setVersionCtrlNbr(tmp
										.getVersionCtrlNbr());
								entityLocatorDT.setCd(tmp.getLocatorCd());
								// entityLocatorDT.setCdDescTxt(tmp.getLocatorTypeCdDesc());
								if (tmp.getLocatorCd() != null
										&& tmp.getLocatorCd().trim().length() != 0) {
									TreeMap<?, ?> map = cache
											.getCodedValuesAsTreeMap("EL_TYPE_TELE_PRV");
									map = cache.reverseMap(map); // we can add
																	// another
																	// method that
																	// do not do
																	// reverse
									entityLocatorDT.setCdDescTxt((String) map
											.get(tmp.getLocatorCd()));
								}
								entityLocatorDT.setClassCd(tmp.getClassCd());
								entityLocatorDT.setEntityUid(personUid);
								entityLocatorDT.setLocatorUid(tmp.getLocatorUid());
								// entityLocatorDT.setUseCd(tmp.getLocatorUseCdDesc());
								if (tmp.getLocatorUseCd() != null
										&& tmp.getLocatorUseCd().trim().length() != 0) {
									TreeMap<?, ?> map = cache
											.getCodedValuesAsTreeMap("EL_USE_TELE_PRV");
									map = cache.reverseMap(map); // we can add
																	// another
																	// method that
																	// do not do
																	// reverse
									entityLocatorDT.setUseCd((String) map.get(tmp
											.getLocatorUseCd()));
								}
								entityLocatorDT.setCd(tmp.getLocatorCd());
	
								TeleLocatorDT teleLocatorDT = new TeleLocatorDT();
								teleLocatorDT.setPhoneNbrTxt(tmp.getTelephoneNbr());
								teleLocatorDT
										.setExtensionTxt(tmp.getExtensionTxt());
								teleLocatorDT
										.setEmailAddress(tmp.getEmailAddress());
								entityLocatorDT.setTheTeleLocatorDT(teleLocatorDT);
								locatorList.add(entityLocatorDT);
								logger.debug("Added tele locator: ");
							}
						}
					} // while (teleItr.hasNext())
				}
				srchResultVO.setPersonLocatorsColl(locatorList);
				// For ID
				ArrayList<Object> entityIdList = new ArrayList<Object>();
				if (personEntityIdColl != null) {
					Iterator<Object> idItr = personEntityIdColl.iterator();
					while (idItr.hasNext()) {
						EntityIdDT entityIdDT = new EntityIdDT();
						PersonSearchResultTmp tmp = (PersonSearchResultTmp) idItr
								.next();
						if (tmp.getPersonUid().equals(personUid)) {
							if (!(tmp.getEiTypeDesc() == null)) {
								// logger.debug("inside typeCd, what is it? "+
								// typeCd);
								srchResultVO.setPersonUID(personUid);
								entityIdDT.setEntityUid(personUid);
								srchResultVO.setVersionCtrlNbr(tmp
										.getVersionCtrlNbr());
	
								if (tmp.getEiTypeDesc() != null
										&& tmp.getEiTypeDesc().trim().length() != 0) {
									TreeMap<?, ?> map = cache
											.getCodedValuesAsTreeMap("EI_TYPE_PRV");
									map = cache.reverseMap(map); // we can add
																	// another
																	// method that
																	// do not do
																	// reverse
									entityIdDT.setTypeCd((String) map.get(tmp
											.getEiTypeDesc()));
								}
								entityIdDT.setTypeDescTxt(tmp.getEiTypeCd());
								entityIdDT.setAssigningAuthorityCd(tmp.getEiAssigningAuthorityCd());
								entityIdDT.setAssigningAuthorityDescTxt(tmp.getEiAssigningAuthorityDescTxt());
								// entityIdDT.setTypeCd(tmp.getEiTypeDesc());
								entityIdDT.setRootExtensionTxt(tmp
										.getEiRootExtensioTxt());
								entityIdList.add(entityIdDT);
							}
						}
					} // while (idItr.hasNext())
				}
				srchResultVO.setPersonIdColl(entityIdList);
				searchResult.add(srchResultVO);
				totalCount++;
			}// while (itr.hasNext())
			ArrayList<Object> cacheList = new ArrayList<Object>();
	
			for (int j = 0; j < searchResult.size(); j++) {
				ProviderSrchResultVO psvo = new ProviderSrchResultVO();
				psvo = (ProviderSrchResultVO) searchResult.get(j);
				if (fromIndex > searchResult.size())
					break;
				if (cacheNumber == listCount)
					break;
				cacheList.add(searchResult.get(j));
				listCount++;
				logger.debug("List Counts = " + listCount);
			}
	
			ArrayList<Object> displayList = new ArrayList<Object>();
			displayPersonList = new DisplayPersonList(totalCount, cacheList,
					fromIndex, listCount);
			displayList.add(displayPersonList);
			return displayList;
		}catch(Exception ex){
			logger.fatal("Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	/**
	 * Performs a search for person record according to the search criteria in
	 * the PersonSearchVO object
	 *
	 * @param find
	 *            which is a PersonSearchVO
	 * @param cacheNumber
	 *            which is an int
	 * @param fromIndex
	 *            which is an int
	 * @return ArrayList<Object> of person
	 * @throws NEDSSSystemException
	 */
	/* This method will run 5 or 7 queries to get person serach result */
	public ArrayList<Object> findPersonsByKeyWords(PersonSearchVO find,
			int cacheNumber, int fromIndex) throws NEDSSSystemException {
		ArrayList<Object> personNameColl = null;
		ArrayList<Object> personAddressColl = null;
		ArrayList<Object> personTeleColl = null;
		ArrayList<Object> personEntityIdColl = null;
		ArrayList<Object> personEthnicityColl = null;
		ArrayList<Object> personRaceColl = null;
		ArrayList<Object> personRoleColl = null;
		String whereClause = null;
		DisplayPersonList displayPersonList = null;
		int totalCount = 0;
		int listCount = 0;
		try{
			/* Has user entered any person and name field for search? */
			if ((find.getBirthTime() != null && find.getBirthTime().trim().length() != 0)
					|| (find.getCurrentSex() != null && find.getCurrentSex().trim()
							.length() != 0)
					|| (find.getLocalID() != null && find.getLocalID().trim()
							.length() != 0)
					|| (find.getLastName() != null && find.getLastName().trim()
							.length() != 0)
					|| (find.getFirstName() != null && find.getFirstName().trim()
							.length() != 0)) {
				whereClause = buildNameWhereClause(find);
				whereClause = whereClause + buildStatusWhereClause(find);
				personNameColl = runNameQuery(whereClause);
				if (personNameColl != null)
					logger.debug("Size of name collection is: "
							+ personNameColl.size());
	
			}
	
			/* Has user entered any address information */
			if ((find.getStreetAddr1() != null && find.getStreetAddr1().trim()
					.length() != 0)
					|| (find.getCityDescTxt() != null && find.getCityDescTxt()
							.trim().length() != 0)
					|| (find.getState() != null && find.getState().trim().length() != 0)
					|| (find.getZipCd() != null && find.getZipCd().trim().length() != 0)) {
				whereClause = buildAddressWhereClause(find);
				whereClause = whereClause + buildStatusWhereClause(find);
				personAddressColl = runAddressQuery(whereClause);
				if (personAddressColl != null)
					logger.debug("Size of address collection is: "
							+ personAddressColl.size());
			}
			/* Has user entered telephone number for search */
			if ((find.getPhoneNbrTxt() != null
					&& find.getPhoneNbrTxt().trim().length() != 0) 
				|| (find.getEmailAddress() != null && find.getEmailAddress()
				.trim().length() != 0)) {
				whereClause = buildTeleWhereClause(find);
				whereClause = whereClause + buildStatusWhereClause(find);
				personTeleColl = runTeleQuery(whereClause);
				if (personTeleColl != null)
					logger.debug("Size of phone collection is: "
							+ personTeleColl.size());
	
			}
			/* Has user entered any EntityId information */
			if (find.getRootExtensionTxt() != null
					&& find.getRootExtensionTxt().trim().length() != 0) {
				whereClause = buildEntityIdWhereClause(find);
				whereClause = whereClause + buildStatusWhereClause(find);
				personEntityIdColl = runEntityIdQuery(whereClause);
				if (personEntityIdColl != null)
					logger.debug("Size of entity ID collection is: "
							+ personEntityIdColl.size());
			}
			/* Has user entered Ethnicity */
			if (find.getEthnicGroupInd() != null
					&& find.getEthnicGroupInd().trim().length() != 0) {
				whereClause = buildEthnicityWhereClause(find);
				whereClause = whereClause + buildStatusWhereClause(find);
				logger.debug("Ethnicity where clauese is: " + whereClause);
				personEthnicityColl = runEthnicityQuery(whereClause);
				if (personEthnicityColl != null)
					logger.debug("Size of ethnicity collection is: "
							+ personEthnicityColl.size());
			}
			/* Has user entered Race */
			if (find.getRaceCd() != null && find.getRaceCd().trim().length() != 0) {
				whereClause = buildRaceWhereClause(find);
				whereClause = whereClause + buildStatusWhereClause(find);
				personRaceColl = runRaceQuery(whereClause);
				if (personRaceColl != null)
					logger.debug("Size of race collection is: "
							+ personRaceColl.size());
			}
			/* Has user entered Role */
			if (find.getRole() != null && find.getRole().trim().length() != 0) {
				whereClause = buildRoleWhereClause(find);
				whereClause = whereClause + buildStatusWhereClause(find);
				personRoleColl = runRoleQuery(whereClause);
				if (personRoleColl != null)
					logger.debug("Size of role collection is: "
							+ personRoleColl.size());
			}
			
	
			/* Find common PersonUIDs from all the queries */
			ArrayList<Object> idList = filterIDs(personNameColl, personAddressColl,
					personTeleColl, personEntityIdColl, personEthnicityColl,
					personRaceColl, personRoleColl);
			// logger.debug("Size of idList is: " + idList.size());
			logger.debug("Ids are: " + idList);
	
			/* generate where clause with those common personUIDs */
			whereClause = generateWhereClause(idList);
			if (whereClause != null) {
				personNameColl = runNameQuery(whereClause);
				personAddressColl = runAddressQuery(whereClause);
				personTeleColl = runTeleQuery(whereClause);
				personEntityIdColl = runEntityIdQuery(whereClause);
			}
			/*
			 * Iterate through IDList and find out attribute for each collection put
			 * them as personsearchresultVo and put it in ArrayList<Object>
			 */
			ArrayList<Object> searchResult = new ArrayList<Object>();
			Iterator<Object> itr = idList.iterator();
			while (itr.hasNext()) {
				PatientSrchResultVO srchResultVO = new PatientSrchResultVO();
				ArrayList<Object> nameList = new ArrayList<Object>();
				Long personUid = (Long) itr.next();
				Iterator<Object> nameItr = personNameColl.iterator();
				CachedDropDownValues cache = new CachedDropDownValues();
				while (nameItr.hasNext()) {
					PersonSearchResultTmp tmp = (PersonSearchResultTmp) nameItr
							.next();
					if (tmp.getPersonUid().equals(personUid)) {
						if (!(tmp.getNameUseCd() == null)) {
							PersonNameDT nameDT = new PersonNameDT();
							nameDT.setPersonUid(personUid);
							logger.debug("Full.. Name " + tmp.getFirstName() + " "
									+ tmp.getLastName());
							nameDT.setFirstNm(tmp.getFirstName());
							nameDT.setLastNm(tmp.getLastName());
							if (tmp.getNameUseCd() != null
									&& tmp.getNameUseCd().trim().length() != 0) {
								TreeMap<?, ?> map = cache
										.getCodedValuesAsTreeMap("P_NM_USE");
								map = cache.reverseMap(map); // we can add another
																// method that do
																// not do reverse
								nameDT.setNmUseCd((String) map.get(tmp
										.getNameUseCd()));
							}
	
							// nameDT.setNmUseCd(tmp.getNameDesc());
							nameList.add(nameDT);
							srchResultVO.setPersonDOB(tmp.getDob());
							if (tmp.getCurrentSex() != null
									&& tmp.getCurrentSex().trim().length() != 0) {
								TreeMap<?, ?> map = cache
										.getCodedValuesAsTreeMap("SEX");
								map = cache.reverseMap(map); // we can add another
																// method that do
																// not do reverse
								srchResultVO.setCurrentSex((String) map.get(tmp
										.getCurrentSex()));
							}
							srchResultVO.setPersonUID(personUid);
							srchResultVO.setPersonLocalID(tmp.getLocalId());
	
						}// if ( ! (tmp.getNameUseCd() == null))
					}// if (tmp.getPersonUid() == personUid)
				}// while (nameItr.hasNext())
				srchResultVO.setPersonNameColl(nameList);
				// logger.debug("Number of names added : " + nameList.size());
				// for address
				ArrayList<Object> locatorList = new ArrayList<Object>();
				Iterator<Object> addressItr = personAddressColl.iterator();
				while (addressItr.hasNext()) {
					EntityLocatorParticipationDT entityLocatorDT = new EntityLocatorParticipationDT();
					PersonSearchResultTmp tmp = (PersonSearchResultTmp) addressItr
							.next();
					if (tmp.getPersonUid().equals(personUid)) {
						if (tmp.getClassCd() != null) {
							srchResultVO.setPersonUID(personUid);
							entityLocatorDT.setCd(tmp.getLocatorCd());
							// entityLocatorDT.setCdDescTxt(tmp.getLocatorTypeCdDesc());
							if (tmp.getLocatorCd() != null
									&& tmp.getLocatorCd().trim().length() != 0) {
								TreeMap<?, ?> map = cache
										.getCodedValuesAsTreeMap("EL_TYPE");
								map = cache.reverseMap(map); // we can add another
																// method that do
																// not do reverse
								entityLocatorDT.setCdDescTxt((String) map.get(tmp
										.getLocatorCd()));
							}
							entityLocatorDT.setClassCd(tmp.getClassCd());
							entityLocatorDT.setEntityUid(personUid);
							entityLocatorDT.setLocatorUid(tmp.getLocatorUid());
							// entityLocatorDT.setUseCd(tmp.getLocatorUseCdDesc());
							if (tmp.getLocatorUseCd() != null
									&& tmp.getLocatorUseCd().trim().length() != 0) {
								TreeMap<?, ?> map = cache
										.getCodedValuesAsTreeMap("EL_USE");
								map = cache.reverseMap(map); // we can add another
																// method that do
																// not do reverse
								entityLocatorDT.setUseCd((String) map.get(tmp
										.getLocatorUseCd()));
							}
	
							entityLocatorDT.setCd(tmp.getLocatorCd());
							PostalLocatorDT postalLocatorDT = new PostalLocatorDT();
							postalLocatorDT.setStateCd(tmp.getState());
							postalLocatorDT.setCityCd(tmp.getCity());
							postalLocatorDT.setCityDescTxt(tmp.getCity());
							postalLocatorDT.setStreetAddr1(tmp.getStreetAddr1());
							postalLocatorDT.setStreetAddr2(tmp.getStreetAddr2());
							postalLocatorDT
									.setPostalLocatorUid(tmp.getLocatorUid());
							postalLocatorDT.setZipCd(tmp.getZip());
							entityLocatorDT.setThePostalLocatorDT(postalLocatorDT);
							locatorList.add(entityLocatorDT);
							logger.debug("Added address locator: ");
						}
					}
				}// while (addressItr.hasNext())
					// logger.debug("Number of address added : " +
					// locatorList.size());
	
				// tele locator
				Iterator<Object> teleItr = personTeleColl.iterator();
				while (teleItr.hasNext()) {
					EntityLocatorParticipationDT entityLocatorDT = new EntityLocatorParticipationDT();
					PersonSearchResultTmp tmp = (PersonSearchResultTmp) teleItr
							.next();
					if (tmp.getPersonUid().equals(personUid)) {
						if (tmp.getClassCd() != null) {
							srchResultVO.setPersonUID(personUid);
							entityLocatorDT.setCd(tmp.getLocatorCd());
							// entityLocatorDT.setCdDescTxt(tmp.getLocatorTypeCdDesc());
							if (tmp.getLocatorCd() != null
									&& tmp.getLocatorCd().trim().length() != 0) {
								TreeMap<?, ?> map = cache
										.getCodedValuesAsTreeMap("EL_TYPE");
								map = cache.reverseMap(map); // we can add another
																// method that do
																// not do reverse
								entityLocatorDT.setCdDescTxt((String) map.get(tmp
										.getLocatorCd()));
							}
							entityLocatorDT.setClassCd(tmp.getClassCd());
							entityLocatorDT.setEntityUid(personUid);
							entityLocatorDT.setLocatorUid(tmp.getLocatorUid());
							// entityLocatorDT.setUseCd(tmp.getLocatorUseCdDesc());
							if (tmp.getLocatorUseCd() != null
									&& tmp.getLocatorUseCd().trim().length() != 0) {
								TreeMap<?, ?> map = cache
										.getCodedValuesAsTreeMap("EL_USE");
								map = cache.reverseMap(map); // we can add another
																// method that do
																// not do reverse
								entityLocatorDT.setUseCd((String) map.get(tmp
										.getLocatorUseCd()));
							}
							entityLocatorDT.setCd(tmp.getLocatorCd());
	
							TeleLocatorDT teleLocatorDT = new TeleLocatorDT();
							teleLocatorDT.setPhoneNbrTxt(tmp.getTelephoneNbr());
							teleLocatorDT.setExtensionTxt(tmp.getExtensionTxt());
							teleLocatorDT.setEmailAddress(tmp.getEmailAddress());
							entityLocatorDT.setTheTeleLocatorDT(teleLocatorDT);
							locatorList.add(entityLocatorDT);
							logger.debug("Added tele locator: ");
						}
					}
				} // while (teleItr.hasNext())
				srchResultVO.setPersonLocatorsColl(locatorList);
				// For ID
				ArrayList<Object> entityIdList = new ArrayList<Object>();
				Iterator<Object> idItr = personEntityIdColl.iterator();
				while (idItr.hasNext()) {
					EntityIdDT entityIdDT = new EntityIdDT();
					PersonSearchResultTmp tmp = (PersonSearchResultTmp) idItr
							.next();
					if (tmp.getPersonUid().equals(personUid)) {
						if (!(tmp.getEiTypeDesc() == null)) {
							// logger.debug("inside typeCd, what is it? "+ typeCd);
							srchResultVO.setPersonUID(personUid);
							entityIdDT.setEntityUid(personUid);
							if (tmp.getEiTypeDesc() != null
									&& tmp.getEiTypeDesc().trim().length() != 0) {
								TreeMap<?, ?> map = cache
										.getCodedValuesAsTreeMap("EI_TYPE_PAT");
								map = cache.reverseMap(map); // we can add another
																// method that do
																// not do reverse
								entityIdDT.setTypeCd((String) map.get(tmp
										.getEiTypeDesc()));
							}
							entityIdDT.setRootExtensionTxt(tmp
									.getEiRootExtensioTxt());
							entityIdList.add(entityIdDT);
						}
					}
				} // while (idItr.hasNext())
				srchResultVO.setPersonIdColl(entityIdList);
				searchResult.add(srchResultVO);
				totalCount++;
			}// while (itr.hasNext())
			ArrayList<Object> displayList = new ArrayList<Object>();
			displayPersonList = new DisplayPersonList(totalCount+1, searchResult,
					fromIndex, totalCount);
			displayList.add(displayPersonList);
			return displayList;
		}catch(Exception ex){
			logger.fatal("Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	/* find common personUids from all collections */
	private ArrayList<Object> filterIDs(ArrayList<Object> personNameColl,
			ArrayList<Object> personAddressColl,
			ArrayList<Object> personTeleColl,
			ArrayList<Object> personEntityIdColl,
			ArrayList<Object> personEthnicityColl,
			ArrayList<Object> personRaceColl, ArrayList<Object> personRoleColl) {
		ArrayList<Object> list = new ArrayList<Object>();
		list = (ArrayList<Object>) getIDs(personNameColl, personAddressColl);
		list = (ArrayList<Object>) getIDs(personTeleColl, list);
		list = (ArrayList<Object>) getIDs(personEntityIdColl, list);
		list = (ArrayList<Object>) getIDs(personEthnicityColl, list);
		list = (ArrayList<Object>) getIDs(personRaceColl, list);
		list = (ArrayList<Object>) getIDs(personRoleColl, list);
		ArrayList<Object> idList = new ArrayList<Object>();
		int cacheCount = 0;
		try{
			/*
			 * get default cache count from property file. If not specified there
			 * take one from this file
			 */
			if (propertyUtil.getNumberOfRows() != 0)
				cacheCount = propertyUtil.getNumberOfRows();
			else
				cacheCount = MAX_CACHE_COUNT;
	
			if (list != null) {
				if (list.size() > cacheCount)
					list.subList(cacheCount, list.size()).clear();
				Iterator<Object> itr = list.iterator();
				int size = list.size();
				for (int i = 0; i < size; i++) {
					PersonSearchResultTmp tmp = (PersonSearchResultTmp) itr.next();
					idList.add(tmp.getPersonUid());
				}
			}
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return idList;
	}

	/* find common personUids from two lists. */
	private List<Object> getIDs(ArrayList<Object> list1, ArrayList<Object> list2) {

		if (list1 == null && list2 == null)
			return null;
		ArrayList<Object> list = new ArrayList<Object>();
		try{
			if (list2 == null) {
	
				ArrayList<Object> map = new ArrayList<Object>();
				Iterator<Object> itr = list1.iterator();
				int size = list1.size();
	
				for (int i = 0; i < size; i++) {
					PersonSearchResultTmp tmp = (PersonSearchResultTmp) itr.next();
					map.add(i, tmp.getPersonUid());
				}
	
				for (int i = 0; i < size; i++) {
					list.add(i, list1.get(i));
				}
	
				return list;
			}
			if (list1 == null) {
				ArrayList<Object> map = new ArrayList<Object>();
				Iterator<Object> itr = list2.iterator();
				int size = list2.size();
	
				for (int i = 0; i < size; i++) {
					PersonSearchResultTmp tmp = (PersonSearchResultTmp) itr.next();
					map.add(i, tmp);
				}
	
				for (int i = 0; i < size; i++) {
					list.add(i, list2.get(i));
				}
				return list;
			}
			/* if both list1 and list2 are there */
			// load hashmap for first list
			if (list1 != null && list2 != null) {
				ArrayList<Object> map1 = new ArrayList<Object>();
				ArrayList<Object> map2 = new ArrayList<Object>();
				int count = 0;
				Iterator<Object> itr = list1.iterator();
				int size1 = list1.size();
				for (int i = 0; i < size1; i++) {
					PersonSearchResultTmp tmp = (PersonSearchResultTmp) itr.next();
					map1.add(i, tmp.getPersonUid());
				}
				// load hashmap for second list
				itr = list2.iterator();
				int size2 = list2.size();
				for (int i = 0; i < size2; i++) {
					PersonSearchResultTmp tmp = (PersonSearchResultTmp) itr.next();
					map2.add(i, tmp.getPersonUid());
				}
				// compare and create list of common
				for (int i = 0; i < map1.size(); i++) {
					Long uid = (Long) map1.get(i);
					if (map2.contains(uid)) {
						list.add(list1.get(i));
	
					}
				}
			}
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return list;
	}

	/* get name query and run it */
	@SuppressWarnings("unchecked")
	private ArrayList<Object> runNameQuery(String whereClause) {
		ArrayList<Object> reSetList = new ArrayList<Object>();
		try{
			StringBuffer query = new StringBuffer();
			PersonSearchResultTmp searchResultName = new PersonSearchResultTmp();
			logger.debug("Name where clause is: " + whereClause);
			query.append(NEDSSSqlQuery.BASENAMEQUERYSQL);
			query.append(whereClause);
	
			logger.info(query.toString());
			reSetList =  (ArrayList<Object>) preparedStmtMethod(searchResultName, null,
					query.toString(), NEDSSConstants.SELECT);
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return reSetList;
	}

	@SuppressWarnings("unchecked")
	private ArrayList<Object> runPersonBasicQuery(String whereClause) {
		StringBuffer query = new StringBuffer();
		ArrayList<Object> reSetList = new ArrayList<Object>();
		try{
			PersonSearchResultTmp searchResultName = new PersonSearchResultTmp();
			logger.debug("Name where clause is: " + whereClause);
			query.append(NEDSSSqlQuery.BASELOCALIDQUERYSQL);
			query.append(whereClause);
	
			logger.info(query.toString());
			reSetList =  (ArrayList<Object>) preparedStmtMethod(searchResultName, null,
					query.toString(), NEDSSConstants.SELECT);
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return reSetList;
	}

	/* get address query and run it */

	@SuppressWarnings("unchecked")
	private ArrayList<Object> runAddressQuery(String whereClause) {
		ArrayList<Object> reSetList = new ArrayList<Object>();
		StringBuffer query = new StringBuffer();
		try{
			PersonSearchResultTmp searchResultAddress = new PersonSearchResultTmp();
			logger.debug("Address where clause is: " + whereClause);
			query.append(NEDSSSqlQuery.BASEADDRESSQUERYSQL);
			query.append(whereClause);
			logger.info(query.toString());
			reSetList =  (ArrayList<Object>) preparedStmtMethod(searchResultAddress,
					null, query.toString(), NEDSSConstants.SELECT);
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return reSetList;
	}

	/* get telephone query and run it */

	@SuppressWarnings("unchecked")
	private ArrayList<Object> runTeleQuery(String whereClause) {
		ArrayList<Object> reSetList = new ArrayList<Object>();
		StringBuffer query = new StringBuffer();
		try{
			PersonSearchResultTmp searchResultTele = new PersonSearchResultTmp();
			logger.debug("Tele where clause is: " + whereClause);
			query.append(NEDSSSqlQuery.BASETELEQUERYSQL);
			query.append(whereClause);
			reSetList =  (ArrayList<Object>) preparedStmtMethod(searchResultTele, null,
					query.toString(), NEDSSConstants.SELECT);
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return reSetList;
	}

	/* get Entity ID query and run it */

	@SuppressWarnings("unchecked")
	private ArrayList<Object> runEntityIdQuery(String whereClause) {
		ArrayList<Object> reSetList = new ArrayList<Object>();
		StringBuffer query = new StringBuffer();
		try{
			PersonSearchResultTmp searchResultEntityId = new PersonSearchResultTmp();
			query.append(NEDSSSqlQuery.BASEIDQUERYSQL);
			query.append(whereClause);
			reSetList =  (ArrayList<Object>) preparedStmtMethod(searchResultEntityId,
					null, query.toString(), NEDSSConstants.SELECT);
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return reSetList;
	}

	/* get Ethnicity query and run it */
	@SuppressWarnings("unchecked")
	private ArrayList<Object> runEthnicityQuery(String whereClause) {
		ArrayList<Object> reSetList = new ArrayList<Object>();
		StringBuffer query = new StringBuffer();
		try{
			PersonSearchResultTmp searchResultEthnicity = new PersonSearchResultTmp();
			logger.debug("Ethnicity where clause is: " + whereClause);
			query.append(NEDSSSqlQuery.BASEETHNICITYQUERYSQL);
			query.append(whereClause);
			reSetList =  (ArrayList<Object>) preparedStmtMethod(searchResultEthnicity,
					null, query.toString(), NEDSSConstants.SELECT);
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return reSetList;
	}

	/* get Race query and run it */
	@SuppressWarnings("unchecked")
	private ArrayList<Object> runRaceQuery(String whereClause) {
		ArrayList<Object> reSetList = new ArrayList<Object>();
		StringBuffer query = new StringBuffer();
		try{
			PersonSearchResultTmp searchResultRace = new PersonSearchResultTmp();
			logger.debug("Race where clause is: " + whereClause);
			query.append(NEDSSSqlQuery.BASERACEQUERYSQL);
			query.append(whereClause);
			reSetList =  (ArrayList<Object>) preparedStmtMethod(searchResultRace, null,
					query.toString(), NEDSSConstants.SELECT);
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return reSetList;
	}

	/* get role query and run it */
	@SuppressWarnings("unchecked")
	private ArrayList<Object> runRoleQuery(String whereClause) {
		ArrayList<Object> reSetList = new ArrayList<Object>();
		StringBuffer query = new StringBuffer();
		try{
			PersonSearchResultTmp searchResultRace = new PersonSearchResultTmp();
			logger.debug("Role where clause is: " + whereClause);
			query.append(NEDSSSqlQuery.BASEROLEQUERYSQL);
			query.append(whereClause);
			reSetList =  (ArrayList<Object>) preparedStmtMethod(searchResultRace, null,
					query.toString(), NEDSSConstants.SELECT);
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return reSetList;
	}

	/* Depending on user input build where clause for person and name query */

	private String buildPersonBasicWhereClause(PersonSearchVO find) {
		StringBuffer sbuf = new StringBuffer();
		String oper = null;
		String whereAnd = " where ";
		boolean firstWhere = true;
		try{
			if ((find.getBeforeBirthTime() != null)
					|| (find.getAfterBirthTime() != null)
					|| (find.getBirthTimeDay() != null)
					|| (find.getBirthTimeMonth() != null)
					|| (find.getBirthTimeYear() != null)) {

				if (find.getBirthTimeOperator().equals("=")) {
					if (find.getBirthTimeMonth() != null && find.getBirthTimeMonth().length() != 0
							&& find.getBirthTimeDay() != null && find.getBirthTimeDay().length() != 0
							&& find.getBirthTimeYear() != null && find.getBirthTimeYear().length() != 0) {
						String datevalue = find.getBirthTimeMonth() + "/" + find.getBirthTimeDay() + "/"
								+ find.getBirthTimeYear();
						if (firstWhere)
							firstWhere = false;
						else
							whereAnd = " AND ";
						sbuf.append(whereAnd + "(CONVERT(DATE, birth_time)) = '" + datevalue + "'");
					}

					else {
						if (find.getBirthTimeMonth() != null && find.getBirthTimeMonth().length() != 0) {
							if (firstWhere)
								firstWhere = false;
							else
								whereAnd = " AND ";
							sbuf.append(whereAnd + "(DATEPART(mm, birth_time)) = " + find.getBirthTimeMonth());
						}
						if (find.getBirthTimeDay() != null && find.getBirthTimeDay().length() != 0) {
							if (firstWhere)
								firstWhere = false;
							else
								whereAnd = " AND ";
							sbuf.append(whereAnd + "(DATEPART(dd, birth_time)) = " + find.getBirthTimeDay());
						}
						if (find.getBirthTimeYear() != null && find.getBirthTimeYear().length() != 0) {
							if (firstWhere)
								firstWhere = false;
							else
								whereAnd = " AND ";
							sbuf.append(whereAnd + "(DATEPART(yyyy, birth_time)) = " + find.getBirthTimeYear());
						}
					}
					// sbuf.append(whereAnd + " p.birth_time " +
					// find.getBirthTimeOperator() +
					// " TO_DATE('" + find.getBirthTime() +
					// "' , 'mm/dd/yyyy')");
				} else {
						if ((find.getAfterBirthTime() != null && find
								.getAfterBirthTime().length() != 0)
								&& (find.getBeforeBirthTime() != null && find
										.getBeforeBirthTime().length() != 0)) {
							if (firstWhere)
								firstWhere = false;
							else
								whereAnd = " AND ";
							sbuf.append(whereAnd
									+ "BIRTH_TIME >=convert(datetime,'"
									+ find.getBeforeBirthTime()
									+ "') AND BIRTH_TIME <=convert(datetime,'"
									+ find.getAfterBirthTime() + "')");
						}
					}
					// sbuf.append(whereAnd + " p.birth_time  " +
					// find.getBirthTimeOperator() + " CONVERT(datetime,'" +
					// find.getBirthTime() + "')") ;
			}
			if (find.getCurrentSex() != null) {
				if (find.getCurrentSex().trim().length() != 0) {
					if (firstWhere)
						firstWhere = false;
					else
						whereAnd = " AND ";
					sbuf.append(whereAnd + " p.curr_sex_cd = " + "'"
							+ find.getCurrentSex().trim() + "' ");
				}
			}
			if (find.getLocalID() != null) {
				if (find.getLocalID().trim().length() != 0) {
					if (firstWhere)
						firstWhere = false;
					else
						whereAnd = " AND ";
					if (find.getPatientIDOperator() != null
							&& find.getPatientIDOperator().equalsIgnoreCase("CT")) { // contains
						sbuf.append(whereAnd + "( p.local_id  like '%"
								+ find.getLocalID().trim() + "%')");
					} else {
						sbuf.append(whereAnd + " p.local_id = " + "'"
								+ find.getLocalID().trim() + "' ");
					}
				}
			}
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return sbuf.toString();
	}

	private String buildNameWhereClause(PersonSearchVO find) {
		StringBuffer sbuf = new StringBuffer();
		String oper = null;
		String whereAnd = " where ";
		boolean firstWhere = false;
		try{
			if (find.getLastName() != null) {
				if (find.getLastName().trim().length() != 0) {
					oper = find.getLastNameOperator().trim();
					if (oper.length() != 0)
						if (firstWhere)
							firstWhere = false;
						else
							whereAnd = " AND ";
					String personLastName = find.getLastName().trim();
					String specialCharacter;
					logger.debug("\n\n\ntoBeEscaped :" + personLastName);
					if (personLastName.indexOf("'") > 0) {
						logger.debug("inside if and index of \"'\" is :"
								+ personLastName.indexOf("'"));
						specialCharacter = "'";
						personLastName = replaceCharacters(personLastName,
								specialCharacter, "''");
						logger.debug("query is :" + personLastName);
	
					}
					if (oper.equalsIgnoreCase("SL")) // sounds like
						sbuf.append(whereAnd
								+ " (soundex(PN.last_nm)  =  soundex('"
								+ personLastName + "'))");
					else if (oper.equalsIgnoreCase("CT")) { // contains
						sbuf.append(whereAnd + "( PN.last_nm  like '%"
								+ personLastName + "%')");
						// sbuf.append(" and PN.nm_use_cd = '0')");
					} else if (oper.equalsIgnoreCase("SW")) { // Starts With
						sbuf.append(whereAnd + "( PN.last_nm  like '"
								+ personLastName + "%')");
						// sbuf.append(" and PN.nm_use_cd = '0')");
					} else {
						sbuf.append(whereAnd + " ( PN.last_nm " + oper
								+ "   '" + personLastName + "')");
						// sbuf.append(" and PN.nm_use_cd = '0')");
					}
				}
			}
			if (find.getFirstName() != null) {
				if (find.getFirstName().trim().length() != 0) {
					oper = find.getFirstNameOperator().trim();
					if (oper.length() != 0)
						if (firstWhere)
							firstWhere = false;
						else
							whereAnd = " AND ";
					String personFirstName = find.getFirstName().trim();
					String specialCharacter;
					logger.debug("personFirstName is :" + personFirstName + "test");
					logger.debug("\n\n\ntoBeEscaped :" + personFirstName);
					if (personFirstName.indexOf("'") > 0) {
						logger.debug("inside if and index of \"'\" is :"
								+ personFirstName.indexOf("'"));
						specialCharacter = "'";
						personFirstName = replaceCharacters(personFirstName,
								specialCharacter, "''");
						logger.debug("query is :" + personFirstName);
	
					}
					if (oper.equalsIgnoreCase("SL")) // sounds like
						sbuf.append(whereAnd
								+ " (soundex( PN.first_nm)  =  soundex('"
								+ personFirstName + "'))");
					else if (oper.equalsIgnoreCase("CT")) // contains
						sbuf.append(whereAnd + "( PN.first_nm  like '%"
								+ personFirstName + "%')");
					else if (oper.equalsIgnoreCase("SW")) // Starts With
						sbuf.append(whereAnd + "( PN.first_nm  like '"
								+ personFirstName + "%')");
					else
						sbuf.append(whereAnd + " ( PN.first_nm " + oper
								+ "   '" + personFirstName + "')");
	
				}
			}
			// New Code for Point in Time
			if (find instanceof ProviderSearchVO) {
				whereAnd = " AND ";
				sbuf.append(whereAnd + " pn.record_status_cd = 'ACTIVE' ");
				sbuf.append(whereAnd + " p.electronic_ind = 'N' ");
				sbuf.append(whereAnd + " (p.edx_ind is null or p.edx_ind != 'Y') ");
				sbuf.append(whereAnd + " p.cd = 'PRV' ");
			}
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return sbuf.toString();
	}


	/* Depending on user input build where clause for address query */
	private String buildAddressWhereClause(PersonSearchVO find) {
		StringBuffer sbuf = new StringBuffer();
		String oper = null;
		String whereAnd = " where ";
		boolean firstWhere = false;
		try{
			if (find.getStreetAddr1() != null) {
				if (find.getStreetAddr1().trim().length() != 0) {
					oper = find.getStreetAddr1Operator().trim();
					if (oper.length() != 0)
						if (firstWhere)
							firstWhere = false;
						else
							whereAnd = " AND ";
					String streetAddress = find.getStreetAddr1().trim();
					String specialCharacter;
					logger.debug("street is :" + streetAddress + "test");
					logger.debug("\n\n\ntoBeEscaped :" + streetAddress);
					if (streetAddress.indexOf("'") > 0) {
						logger.debug("inside if and index of \"'\" is :"
								+ streetAddress.indexOf("'"));
						specialCharacter = "'";
						streetAddress = replaceCharacters(streetAddress,
								specialCharacter, "''");
						logger.debug("query is :" + streetAddress);
	
					}
					if (oper.equalsIgnoreCase("SL"))
						sbuf.append(whereAnd
								+ " soundex (PL.street_addr1) = soundex('"
								+ streetAddress + "')");
					else if (oper.equalsIgnoreCase("CT")) // contains
						sbuf.append(whereAnd + "  PL.street_addr1  like '%"
								+ streetAddress + "%'");
					else
						sbuf.append(whereAnd + " PL.street_addr1) "
								+ oper + " '"
								+ streetAddress + "'");
				}
			}
			if (find.getCityDescTxt() != null) {
				if (find.getCityDescTxt().trim().length() != 0) {
					oper = find.getCityDescTxtOperator().trim();
					if (oper.length() != 0)
						if (firstWhere)
							firstWhere = false;
						else
							whereAnd = " AND ";
					if (oper.equalsIgnoreCase("SL"))
						sbuf.append(whereAnd
								+ " soundex (PL.city_desc_txt) = soundex('"
								+ find.getCityDescTxt().trim() + "')");
					else if (oper.equalsIgnoreCase("CT")) // contains
						sbuf.append(whereAnd + "  PL.city_desc_txt  like '%"
								+ find.getCityDescTxt().trim() + "%'");
					else
						sbuf.append(whereAnd + " PL.city_desc_txt "
								+ oper.toUpperCase() + " '"
								+ find.getCityDescTxt().trim() + "'");
	
				}
			}
			if (find.getState() != null) {
				if (find.getState().trim().length() != 0) {
					if (firstWhere)
						firstWhere = false;
					else
						whereAnd = " AND ";
					sbuf.append(whereAnd + " PL.state_cd =  '"
							+ find.getState().trim() + "'");
	
				}
			}
			if (find.getZipCd() != null) {
				if (find.getZipCd().trim().length() != 0) {
					if (firstWhere)
						firstWhere = false;
					else
						whereAnd = " AND ";
					sbuf.append(whereAnd + " PL.zip_cd = '"
							+ find.getZipCd().trim() + "'");
				}
			}
			// New Code for Point in Time
			if (find instanceof ProviderSearchVO) {
				whereAnd = " AND ";
				sbuf.append(whereAnd + " elp1.record_status_cd = 'ACTIVE' ");
				sbuf.append(whereAnd + " p.electronic_ind = 'N' ");
				sbuf.append(whereAnd + " (p.edx_ind is null or p.edx_ind != 'Y') ");
				sbuf.append(whereAnd + " p.cd = 'PRV' ");
			}
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return sbuf.toString();
	}

	/* Depending on user input build where clause for tele query */
	private String buildTeleWhereClause(PersonSearchVO find) {
		StringBuffer sbuf = new StringBuffer();
		String oper = " = ";
		String whereAnd = " where ";
		boolean firstWhere = false;
		try{
			if (find.getPhoneNbrTxt() != null) {
				if (find.getPhoneNbrTxt().trim().length() != 0) {
					// oper = find.getPhoneNbrTxtOperator().trim();
					if (oper.length() != 0)
						if (firstWhere)
							firstWhere = false;
						else
							whereAnd = " AND ";
					String phoneNumber = find.getPhoneNbrTxt().trim();
					String formattedPhone = formatPhoneNumber(phoneNumber);
					if (formattedPhone.indexOf("-") == 3
							&& formattedPhone.lastIndexOf("-") == 4
							&& formattedPhone.length() == 9) {
						logger.debug("formattedPhone.substring()= "
								+ formattedPhone.substring(0, 4) + " "
								+ formattedPhone.substring(5, 9));
						sbuf.append(whereAnd + " TL.phone_nbr_txt  like '"
								+ formattedPhone.substring(0, 4)
								+ "%'" + " and TL.phone_nbr_txt  like '%"
								+ formattedPhone.substring(5, 9)
								+ "%'");
					} else if (formattedPhone.indexOf("-") == 3
							&& formattedPhone.lastIndexOf("-") == 3
							&& formattedPhone.length() == 4) {
						sbuf.append(whereAnd + " TL.phone_nbr_txt  like '"
								+ formattedPhone.trim() + "%'");
					} else
						sbuf.append(whereAnd + " TL.phone_nbr_txt  like '%"
								+ formattedPhone.trim() + "%'");
				}
	
			}
			// New Code for Point in Time
			if (find instanceof ProviderSearchVO) {
				whereAnd = " AND ";
				sbuf.append(whereAnd + " elp2.record_status_cd = 'ACTIVE' ");
				sbuf.append(whereAnd + " p.electronic_ind = 'N' ");
				sbuf.append(whereAnd + " (p.edx_ind is null or p.edx_ind != 'Y') ");
				sbuf.append(whereAnd + " p.cd = 'PRV' ");
			}
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return sbuf.toString();
	}

	/* Depending on user input build where clause for entity id query */
	private String buildEntityIdWhereClause(PersonSearchVO find) {
		StringBuffer sbuf = new StringBuffer();
		try{
			if (find.getRootExtensionTxt() != null && find.getTypeCd() != null) {
				if (find.getTypeCd().trim().length() != 0) {
					if (find.getRootExtensionTxt().trim().length() != 0) {
						// There is no operator for ID and value; take default
						String oper = "=";
						String specialCharacter;
						String rootExtension = find.getRootExtensionTxt().trim();
						// logger.debug("rootExtension is :" + oper+"test");
						// logger.debug("\n\n\ntoBeEscaped :" + oper);
						if (rootExtension.indexOf("'") > 0) {
							logger.debug("inside if and index of \"'\" is :"
									+ rootExtension.indexOf("'"));
							specialCharacter = "'";
							rootExtension = replaceCharacters(rootExtension,
									specialCharacter, "''");
							logger.debug("oper query is :" + rootExtension);
						}
						if (oper.length() != 0)
							sbuf.append("AND (EI.TYPE_CD = '"
									+ find.getTypeCd().trim() + "')");
						sbuf.append("AND  EI.root_extension_txt = '"
								+ rootExtension + "'  ");
	
					} // end of 2nd if
				}
			}
	
			if (find.getSsn() != null) {
	
				if (find.getSsn().trim().length() != 0) {
					// There is no operator for ID and value; take default
					String oper = "=";
					String specialCharacter;
					String entityId = find.getSsn().trim();
	
					if (entityId.indexOf("'") > 0) {
						logger.debug("inside if and index of \"'\" is :"
								+ entityId.indexOf("'"));
						specialCharacter = "'";
						entityId = replaceCharacters(entityId, specialCharacter, "''");
					}
					if (oper.length() != 0)
						sbuf.append("AND (EI.TYPE_CD = 'SS')");
					sbuf.append("AND  EI.root_extension_txt = '"
							+ entityId + "'  ");
	
				} // end of 2nd if
	
			}
	
			// New Code for Point in Time
			if (find instanceof ProviderSearchVO) {
				String whereAnd = " AND ";
				sbuf.append(whereAnd + " ei.record_status_cd = 'ACTIVE' ");
				sbuf.append(whereAnd + " p.electronic_ind = 'N' ");
				sbuf.append(whereAnd + " (p.edx_ind is null or p.edx_ind != 'Y') ");
				sbuf.append(whereAnd + " p.cd = 'PRV' ");
			}
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return sbuf.toString();
	}

	/* Depending on user input build where clause for ethnicity query */
	private String buildEthnicityWhereClause(PersonSearchVO find) {
		StringBuffer sbuf = new StringBuffer();
		try{
			if (find.getEthnicGroupInd() != null) {
				if (find.getEthnicGroupInd().trim().length() != 0) {
					sbuf.append(" where p.ethnic_group_ind = " + "'"
							+ find.getEthnicGroupInd().trim() + "' ");
					// New Code for Point in Time
					if (find instanceof ProviderSearchVO) {
						String and = " AND ";
						sbuf.append(and + " p.electronic_ind = 'N' ");
						sbuf.append(and
								+ " (p.edx_ind is null or p.edx_ind != 'Y') ");
						sbuf.append(and + " p.person_cd = 'PRV' ");
					}
	
				}
			}
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return sbuf.toString();
	}

	/* Depending on user input build where clause for race query */
	private String buildRaceWhereClause(PersonSearchVO find) {
		StringBuffer sbuf = new StringBuffer();
		try{
			if (find.getRaceCd() != null) {
				if (find.getRaceCd().trim().length() != 0) {
					sbuf.append(" and pr.race_category_cd = '" + find.getRaceCd()
							+ "'");
					// New Code for Point in Time
					if (find instanceof ProviderSearchVO) {
						String and = " AND ";
						sbuf.append(and + " pr.record_status_cd = 'ACTIVE' ");
						sbuf.append(and + " p.electronic_ind = 'N' ");
						sbuf.append(and
								+ " (p.edx_ind is null or p.edx_ind != 'Y') ");
						sbuf.append(and + " p.person_cd = 'PRV' ");
					}
	
				}
			}
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return sbuf.toString();
	}

	/* Depending on user input build where clause for role query */
	private String buildRoleWhereClause(PersonSearchVO find) {
		StringBuffer sbuf = new StringBuffer();
		try{
			if (find.getRole() != null) {
				if (find.getRole().trim().length() != 0) {
					sbuf.append(" and r.cd = '" + find.getRole() + "'");
				}
			}
	
			// New Code for Point in Time
			if (find instanceof ProviderSearchVO) {
				String and = " AND ";
				sbuf.append(and + " r.record_status_cd = 'ACTIVE' ");
				sbuf.append(and + " p.electronic_ind = 'N' ");
				sbuf.append(and + " (p.edx_ind is null or p.edx_ind != 'Y') ");
				sbuf.append(and + " p.person_cd = 'PRV' ");
			}
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return sbuf.toString();
	}

	/*
	 * Depending on user input build where clause for status. This query should
	 * be appended to all other queries.
	 */
	private String buildStatusWhereClause(PersonSearchVO find) {
		StringBuffer sbuf = new StringBuffer();
		String oper = null;
		String whereAnd = " where ";
		boolean firstWhere = false;
		int noCodes = 0;
		try{
			if (find.isActive() || find.isInActive() || find.isSuperceded()) {
	
				if (find.isActive()) {
					if (find.getStatusCodeActive().trim().length() != 0) {
						if (firstWhere)
							firstWhere = false;
						else
							whereAnd = " AND ( ";
	
						sbuf.append(whereAnd + " (p.record_status_cd =  '"
								+ find.getStatusCodeActive().trim() + "')");
						noCodes += 1;
					}
				}
	
				if (find.isInActive()) {
					if (find.getStatusCodeInActive().trim().length() != 0) {
						if (firstWhere)
							firstWhere = false;
						else if (noCodes > 0)
							whereAnd = " OR ";
						else
							whereAnd = " AND ( ";
						sbuf.append(whereAnd + " ( p.record_status_cd = '"
								+ find.getStatusCodeInActive().trim() + "')");
						noCodes += 1;
	
					}
				}
				if (find.isSuperceded()) {
					if (find.getStatusCodeSuperCeded().trim().length() != 0) {
						if (firstWhere)
							firstWhere = false;
						else if (noCodes > 0)
							whereAnd = " OR ";
						else
							whereAnd = " AND ( ";
						sbuf.append(whereAnd + " ( p.record_status_cd = '"
								+ find.getStatusCodeSuperCeded().trim() + "')");
						// sbuf.append(whereAnd + " ( p.status_cd = '" + "S" +
						// "')");
						noCodes += 1;
	
					}
				}
			}
			if (noCodes > 0)
				sbuf.append(" )  ");
		// sbuf.append(" AND (p.edx_ind != 'Y' )");
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return sbuf.toString();

	}

	/* Generate where clause based on common personUIDs in the list */
	private String generateWhereClause(ArrayList<Object> list) {
		String result = null;
		try{
			if (list != null && list.size() > 0) {
				ArrayList<Object> csvChunkArray = createUidsCsvStringArray(list);
	
				boolean firstComma = true;
				StringBuffer sbuf = new StringBuffer();
				sbuf.append(" and (");
				sbuf.append(constructUidWhereInClause("p.person_parent_uid",
						csvChunkArray));
				sbuf.append(") ");
				result = sbuf.toString();
			}
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return result;
	}

	private String constructUidWhereInClause(String fieldName,
			ArrayList<Object> uidsStringArray) {
		// EXAMPLE:
		// "p.person_uid in (uid1, uid2, ...., uid1000) or p.person_uid(uid1001, uid1002)"
		// there should be exceptions thrown when fieldName is empty or null and
		// when uidsStringArray is empty or null
		// nclogger.info("constructUidWhereInClause(): START");
		// nclogger.info("input fieldName=" + fieldName);
		// nclogger.info("input uidsStringArray size =" +
		// uidsStringArray.size());
		StringBuffer strbResult = new StringBuffer();
		try{
			if (uidsStringArray != null && !uidsStringArray.isEmpty()) {
				int size = uidsStringArray.size();
				boolean first = true;
	
				for (int i = 0; i < size; i++) {
					if (first) {
						first = false;
					} else {
						strbResult.append(" OR ");
					}
	
					strbResult.append(fieldName);
					strbResult.append(" in (");
					strbResult.append(uidsStringArray.get(i));
					strbResult.append(") ");
				}
			}
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return strbResult.toString();
	}

	private ArrayList<Object> createUidsCsvStringArray(
			ArrayList<Object> personParentUidList) {
		// nclogger.info("createUidsCsvStringArray(): START");
		ArrayList<Object> result = null;
		try{
			if (personParentUidList != null && !personParentUidList.isEmpty()) {
				result = new ArrayList<Object>();
				int maxNum = 255;
				Long curUid = null;
				int size = personParentUidList.size();
				int numOfIterations = ((int) size / maxNum) + 1;
				int maxForThisIteration = size;
	
				for (int k = 0; k < numOfIterations; k++) {
					ArrayList<Object> uidCollector = new ArrayList<Object>();
					int counterStartingPoint = k * maxNum;
	
					if ((k + 1) * maxNum > size) {
						maxForThisIteration = size;
					} else {
						maxForThisIteration = (k + 1) * maxNum;
					}
					for (int counter = counterStartingPoint; counter < maxForThisIteration; counter++) {
						curUid = (Long) personParentUidList.get(counter);
						uidCollector.add(curUid);
					}
	
					result.add(createCsvString(uidCollector));
				}
			}
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return result;
	}

	private String createCsvString(ArrayList<Object> uids) {

		StringBuffer strbResult = new StringBuffer("");
		try{
			if (uids != null && !uids.isEmpty()) {
				boolean firstId = true;
	
				if (uids != null && !uids.isEmpty()) {
					Iterator<Object> it = uids.iterator();
	
					if (it != null) {
						Long cur = null;
	
						while (it.hasNext()) {
							cur = (Long) it.next();
	
							if (firstId) {
								firstId = false;
							} else {
								strbResult.append(",");
							}
	
							strbResult.append(cur.longValue());
						}
					}
				}
			}

		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return strbResult.toString();
	}

	/* Generate where clause based on personParentUIDs in the list */
	private String generateWhereClauseFor(
			ArrayList<Object> personParentUidList, boolean firstTime) {
		String result = null;
		try{
			if (personParentUidList != null && personParentUidList.size() > 0) {
				boolean firstComma = true;
	
				StringBuffer sbuf = new StringBuffer();
	
				ArrayList<Object> csvChunkArray = createUidsCsvStringArray(personParentUidList);
	
				if (firstTime) {
					// sbuf.append(" where p.person_parent_uid in ( ");
					sbuf.append(" where (");
					sbuf.append(constructUidWhereInClause("p.person_parent_uid",
							csvChunkArray));
					sbuf.append(") ");
				} else {
					// sbuf.append(" and p.person_parent_uid in ( ") ;
					sbuf.append(" and (");
					sbuf.append(constructUidWhereInClause("p.person_parent_uid",
							csvChunkArray));
					sbuf.append(") ");
				}
	
				result = sbuf.toString();
			}
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return result;
	}

	private String generateWhereClauseForPersonBasic(
			ArrayList<Object> personParentUidList) {
		String result = null;
		try{
			if (personParentUidList != null && personParentUidList.size() > 0) {
				ArrayList<Object> csvChunkArray = createUidsCsvStringArray(personParentUidList);
	
				boolean firstComma = true;
				StringBuffer sbuf = new StringBuffer();
				sbuf.append(" where (");
				sbuf.append(constructUidWhereInClause("p.person_parent_uid",
						csvChunkArray));
				sbuf.append(") ");
				result = sbuf.toString();
			}
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return result;
	}


	
	private String buildEpiLinkWhereClause(PersonSearchVO find){
		String subQuery = "";
	 
		subQuery = "(select local_id from Public_health_case  with (nolock) "
				+ " where public_health_case_uid in"
				+ " (select subject_entity_phc_uid from CT_contact"
				+ " where (subject_entity_epi_link_id='"+find.getEpiLinkId()+"' or contact_entity_epi_link_id='"+find.getEpiLinkId()+"')"
				+ " union "
				+ " select contact_entity_phc_uid from CT_contact"
				+ " where (subject_entity_epi_link_id='"+find.getEpiLinkId()+"' or contact_entity_epi_link_id='"+find.getEpiLinkId()+"')))";
		return subQuery;
	}
	
	// }

	// *******************************************************************************************/
	// BEGIN: findPatientsByKeyWords(PersonSearchVO find, int cacheNumber, int
	// fromIndex)
	// *******************************************************************************************/
	/**
	 * Performs a search for patient record according to the search criteria in
	 * the PatientSearchVO object
	 *
	 * @param find
	 *            which is a PatientSearchVO
	 * @param cacheNumber
	 *            which is an int
	 * @param fromIndex
	 *            which is an int
	 * @return ArrayList<Object> of person
	 */
	/* This method will run 5 or 7 queries to get person serach result */
	public ArrayList<Object> findPatientsByKeyWordsOld(PatientSearchVO find,
			int cacheNumber, int fromIndex) {
		ArrayList<Object> personNameColl = null;
		ArrayList<Object> personAddressColl = null;
		ArrayList<Object> personTeleColl = null;
		ArrayList<Object> personEntityIdColl = null;
		ArrayList<Object> personEthnicityColl = null;
		ArrayList<Object> personRaceColl = null;
		ArrayList<Object> personRoleColl = null;
		ArrayList<Object> personBasicColl = null;
		Collection<Object> patientRevisonColl = null;
		String whereClause = null;
		DisplayPersonList displayPersonList = null;
		ArrayList<Object> displayList = new ArrayList<Object>();
		try{
			String orderByRule = " ORDER BY pn.last_nm ASC, pn.first_nm ASC, p.birth_time_calc DESC";
			String orderByRuleForBasic = " ORDER BY p.birth_time_calc DESC";
			int totalCount = 0;
			int listCount = 0;
			/* Has user entered any person and name field for search? */
			if ((find.getLastName() != null && find.getLastName().trim().length() != 0)
					|| (find.getFirstName() != null && find.getFirstName().trim()
							.length() != 0)) {
				logger.debug("local id is: " + find.getLocalID());
				whereClause = buildNameWhereClause(find);
				// whereClause = whereClause + buildStatusWhereClause(find);
				// whereClause += addPersonCd(PATIENT_CD);
				whereClause += find.getDataAccessWhereClause();
				whereClause += orderByRule;
				logger.debug("whereClause for name collection is: " + whereClause);
				personNameColl = runNameQuery(whereClause);
				if (personNameColl != null)
					logger.debug("Size of name collection is: "
							+ personNameColl.size());
	
			}
			if ((find.getBeforeBirthTime() != null && find.getBeforeBirthTime()
					.trim().length() != 0)
					|| (find.getAfterBirthTime() != null && find
							.getAfterBirthTime().trim().length() != 0)
					|| (find.getBirthTimeDay() != null && find.getBirthTimeDay()
							.trim().length() != 0)
					|| (find.getBirthTimeMonth() != null && find
							.getBirthTimeMonth().trim().length() != 0)
					|| (find.getBirthTimeYear() != null && find.getBirthTimeYear()
							.trim().length() != 0)
					|| (find.getCurrentSex() != null && find.getCurrentSex().trim()
							.length() != 0)
					|| (find.getLocalID() != null && find.getLocalID().trim()
							.length() != 0)) {
				logger.debug("local id is: " + find.getLocalID());
				whereClause = buildPersonBasicWhereClause(find);
				whereClause += find.getDataAccessWhereClause();
				whereClause += orderByRuleForBasic;
				logger.debug("whereClause for name collection is: " + whereClause);
				personBasicColl = runPersonBasicQuery(whereClause);
				if (personNameColl != null)
					logger.debug("Size of name collection is: "
							+ personNameColl.size());
	
			}
			if ((find.getBirthTime() != null && find.getBirthTime().trim().length() != 0)
					|| (find.getCurrentSex() != null && find.getCurrentSex().trim()
							.length() != 0)
					|| (find.getLocalID() != null && find.getLocalID().trim()
							.length() != 0)) {
				logger.debug("local id is: " + find.getLocalID());
				whereClause = buildPersonBasicWhereClause(find);
				whereClause += find.getDataAccessWhereClause();
				whereClause += orderByRuleForBasic;
				logger.debug("whereClause for name collection is: " + whereClause);
				personBasicColl = runPersonBasicQuery(whereClause);
				if (personNameColl != null)
					logger.debug("Size of name collection is: "
							+ personNameColl.size());
	
			}
	
			// Has user entered any address information
			if ((find.getStreetAddr1() != null && find.getStreetAddr1().trim()
					.length() != 0)
					|| (find.getCityDescTxt() != null && find.getCityDescTxt()
							.trim().length() != 0)
					|| (find.getState() != null && find.getState().trim().length() != 0)
					|| (find.getZipCd() != null && find.getZipCd().trim().length() != 0)) {
				whereClause = buildAddressWhereClause(find);
				// whereClause = whereClause + buildStatusWhereClause(find);
				whereClause += find.getDataAccessWhereClause();
				personAddressColl = runAddressQuery(whereClause);
				if (personAddressColl != null)
					logger.debug("Size of address collection is: "
							+ personAddressColl.size());
			}
			// Has user entered telephone number for search
			if (find.getPhoneNbrTxt() != null
					&& find.getPhoneNbrTxt().trim().length() != 0) {
				whereClause = buildTeleWhereClause(find);
				// whereClause = whereClause + buildStatusWhereClause(find);
				whereClause += find.getDataAccessWhereClause();
				personTeleColl = runTeleQuery(whereClause);
				if (personTeleColl != null)
					logger.debug("Size of phone collection is: "
							+ personTeleColl.size());
	
			}
	
			// Has user entered any EntityId information
			if ((find.getRootExtensionTxt() != null && find.getRootExtensionTxt()
					.trim().length() != 0)
					|| (find.getSsn() != null && find.getSsn().trim().length() != 0)) {
				whereClause = buildEntityIdWhereClause(find);
				// whereClause = whereClause + buildStatusWhereClause(find);
				whereClause += find.getDataAccessWhereClause();
				personEntityIdColl = runEntityIdQuery(whereClause);
				if (personEntityIdColl != null)
					logger.debug("Size of entity ID collection is: "
							+ personEntityIdColl.size());
			}
			/* Has user entered Ethnicity */
			if (find.getEthnicGroupInd() != null
					&& find.getEthnicGroupInd().trim().length() != 0) {
				whereClause = buildEthnicityWhereClause(find);
				// whereClause = whereClause + buildStatusWhereClause(find);
				whereClause += find.getDataAccessWhereClause();
				logger.debug("Ethnicity where clauese is: " + whereClause);
				personEthnicityColl = runEthnicityQuery(whereClause);
				if (personEthnicityColl != null)
					logger.debug("Size of ethnicity collection is: "
							+ personEthnicityColl.size());
			}
			/* Has user entered Race */
			if (find.getRaceCd() != null && find.getRaceCd().trim().length() != 0) {
				whereClause = buildRaceWhereClause(find);
				// whereClause = whereClause + buildStatusWhereClause(find);
				whereClause += find.getDataAccessWhereClause();
				personRaceColl = runRaceQuery(whereClause);
				if (personRaceColl != null)
					logger.debug("Size of race collection is: "
							+ personRaceColl.size());
			}
			/* Has user entered Role */
			if (find.getRole() != null && find.getRole().trim().length() != 0) {
				whereClause = buildRoleWhereClause(find);
				// whereClause = whereClause + buildStatusWhereClause(find);
				whereClause += find.getDataAccessWhereClause();
				personRoleColl = runRoleQuery(whereClause);
				if (personRoleColl != null)
					logger.debug("Size of role collection is: "
							+ personRoleColl.size());
			}
			/* Find a list of unique PersonParentUIDs from all the queries */
			ArrayList<Object> parentUidList = filterForUniquePersonParentUIDs(
					personBasicColl, personNameColl, personAddressColl,
					personTeleColl, personEntityIdColl, personEthnicityColl,
					personRaceColl, personRoleColl);
			logger.debug("Size of parent idList is: " + parentUidList.size());
			logger.debug("Parent Ids are: " + parentUidList);
	
			if (parentUidList == null || parentUidList.size() <= 0)
				return new ArrayList<Object>();
	
			/* generate where clause with those common personUIDs */
	
			whereClause = generateWhereClauseFor(parentUidList, false);
			whereClause += this.buildStatusWhereClause(find);
			if (whereClause != null) {
				personBasicColl = runPersonBasicQuery(generateWhereClauseFor(
						parentUidList, true));
				personNameColl = runNameQuery(whereClause);
				personAddressColl = runAddressQuery(whereClause);
				personTeleColl = runTeleQuery(whereClause);
				personEntityIdColl = runEntityIdQuery(whereClause);
				personRaceColl = runRaceQuery(whereClause);
			}
	
			/* Find a list of all common PersonUIDs from all the queries */
			ArrayList<Object> personUidList = filterForCommonPersonBasicUIDs(
					personBasicColl, personNameColl, personAddressColl,
					personTeleColl, personEntityIdColl, personEthnicityColl,
					personRaceColl, personRoleColl, parentUidList);
			logger.debug("Person Ids size is : " + personUidList.size());
			logger.debug("Person Ids are: " + personUidList);
	
			/*
			 * Iterate through person parentUIDList and find out attribute for each
			 * collection put them as patientsearchresultVo and put it in
			 * ArrayList<Object>
			 */
			ArrayList<Object> searchResult = new ArrayList<Object>();
			Iterator<Object> itr = parentUidList.iterator();
			while (itr.hasNext()) {
				Long personParentUid = (Long) itr.next();
	
				boolean isActiveMpr = false;
	
				// Compose a PatientSrchResultVO object for this person parent uid
				PatientSrchResultVO srchResultVO = new PatientSrchResultVO();
	
				// 1..... Look into the name collection to extract all matching
				// records
				ArrayList<Object> nameList = new ArrayList<Object>();
				Iterator<Object> nameItr = personNameColl.iterator();
				boolean srchResultVOPopulates = false;
				String recordStatusCd = "";
	
				while (nameItr.hasNext()) {
					PersonSearchResultTmp tmp = (PersonSearchResultTmp) nameItr
							.next();
					if (tmp.getPersonUid().equals(personParentUid)) // a mpr
					{
						addNameDTObjectTo(nameList, tmp);
	
						// Find out whether there is a need to reset the active mpr
						// flag
						recordStatusCd = tmp.getRecordStatusCd();
						if (!isActiveMpr
								&& recordStatusCd != null
								&& recordStatusCd
										.equalsIgnoreCase(NEDSSConstants.RECORD_STATUS_ACTIVE)) {
							isActiveMpr = true;
						}
	
						// Populate attributes of patient search result vo if not
						// yet done
						if (!srchResultVOPopulates) {
							populateSrchResultVO(srchResultVO, tmp);
							srchResultVOPopulates = true;
						}
					}// if (tmp.getPersonUid() == personUid)
				}// while (nameItr.hasNext())
				srchResultVO.setRecordStatusCd(recordStatusCd);
				srchResultVO.setPersonNameColl(nameList);
				// logger.debug("Number of names added : " + nameList.size());
	
				ArrayList<Object> basicList = new ArrayList<Object>();
				Iterator<Object> basicItr = personBasicColl.iterator();
				while (basicItr.hasNext()) {
					PersonSearchResultTmp tmp = (PersonSearchResultTmp) basicItr
							.next();
					if (tmp.getPersonUid().equals(personParentUid)) // a mpr
					{
						addNameDTObjectTo(basicList, tmp);
	
						// Find out whether there is a need to reset the active mpr
						// flag
						recordStatusCd = tmp.getRecordStatusCd();
						if (!isActiveMpr
								&& recordStatusCd != null
								&& recordStatusCd
										.equalsIgnoreCase(NEDSSConstants.RECORD_STATUS_ACTIVE)) {
							isActiveMpr = true;
						}
	
						// Populate attributes of patient search result vo if not
						// yet done
						if (!srchResultVOPopulates) {
							populateSrchResultVO(srchResultVO, tmp);
							srchResultVOPopulates = true;
						}
					}// if (tmp.getPersonUid() == personUid)
				}// while (nameItr.hasNext())
				srchResultVO.setRecordStatusCd(recordStatusCd);
				if (srchResultVO.getPersonNameColl() != null
						&& srchResultVO.getPersonNameColl().size() > 0)
					srchResultVO.getPersonNameColl().addAll(basicList);
				else
					srchResultVO.setPersonNameColl(basicList);
				// logger.debug("Number of names added : " + nameList.size());
	
				// Adding Race collections
				ArrayList<Object> raceList = new ArrayList<Object>();
				Iterator<Object> raceIterator = personRaceColl.iterator();
				while (raceIterator.hasNext()) {
					PersonSearchResultTmp tmp = (PersonSearchResultTmp) raceIterator
							.next();
					if (tmp.getPersonUid().equals(personParentUid)) {
						// if ( tmp.getClassCd() != null)
						{
							addRaceDTObjectTo(raceList, tmp);
						}
	
						// Find out whether there is a need to reset the active mpr
						// flag
						recordStatusCd = tmp.getRecordStatusCd();
						if (!isActiveMpr
								&& recordStatusCd != null
								&& recordStatusCd
										.equalsIgnoreCase(NEDSSConstants.RECORD_STATUS_ACTIVE)) {
							isActiveMpr = true;
						}
	
						// Populate attributes of patient search result vo if not
						// yet done
						if (!srchResultVOPopulates) {
							populateSrchResultVO(srchResultVO, tmp);
							srchResultVOPopulates = true;
						}
	
					}
				}
				srchResultVO.setRaceCdColl(raceList);
	
				// 2..... Look into the address collection to extract all matching
				// records
				ArrayList<Object> locatorList = new ArrayList<Object>();
				Iterator<Object> addressItr = personAddressColl.iterator();
				while (addressItr.hasNext()) {
					PersonSearchResultTmp tmp = (PersonSearchResultTmp) addressItr
							.next();
					if (tmp.getPersonUid().equals(personParentUid)) {
						if (tmp.getClassCd() != null) {
							addAddressLocatorDTObjectTo(locatorList, tmp);
							logger.debug("Added address locator: ");
						}
	
						// Find out whether there is a need to reset the active mpr
						// flag
						recordStatusCd = tmp.getRecordStatusCd();
						if (!isActiveMpr
								&& recordStatusCd != null
								&& recordStatusCd
										.equalsIgnoreCase(NEDSSConstants.RECORD_STATUS_ACTIVE)) {
							isActiveMpr = true;
						}
	
						// Populate attributes of patient search result vo if not
						// yet done
						if (!srchResultVOPopulates) {
							populateSrchResultVO(srchResultVO, tmp);
							srchResultVOPopulates = true;
						}
	
					}
				}// while (addressItr.hasNext())
					// logger.debug("Number of address added : " +
					// locatorList.size());
	
				// 3..... Look into the tele locator collection to extract all
				// matching records
				Iterator<Object> teleItr = personTeleColl.iterator();
				while (teleItr.hasNext()) {
					PersonSearchResultTmp tmp = (PersonSearchResultTmp) teleItr
							.next();
					if (tmp.getPersonUid().equals(personParentUid)) {
						if (tmp.getClassCd() != null) {
							addTeleLocatorDTObjectTo(locatorList, tmp);
	
							logger.debug("Added tele locator: ");
						}
	
						// Find out whether there is a need to reset the active mpr
						// flag
						recordStatusCd = tmp.getRecordStatusCd();
						if (!isActiveMpr
								&& recordStatusCd != null
								&& recordStatusCd
										.equalsIgnoreCase(NEDSSConstants.RECORD_STATUS_ACTIVE)) {
							isActiveMpr = true;
						}
	
						// Populate attributes of patient search result vo if not
						// yet done
						if (!srchResultVOPopulates) {
							populateSrchResultVO(srchResultVO, tmp);
							srchResultVOPopulates = true;
						}
	
					}
				} // while (teleItr.hasNext())
				srchResultVO.setPersonLocatorsColl(locatorList);
	
				// 4..... Look into the entity id collection to extract all matching
				// records
				ArrayList<Object> entityIdList = new ArrayList<Object>();
				Iterator<Object> idItr = personEntityIdColl.iterator();
				while (idItr.hasNext()) {
	
					PersonSearchResultTmp tmp = (PersonSearchResultTmp) idItr
							.next();
					if (tmp.getPersonUid().equals(personParentUid)) {
						if (!(tmp.getEiTypeDesc() == null)) {
							// logger.debug("inside typeCd, what is it? "+ typeCd);
							addEntityIdDTObjectTo(entityIdList, tmp);
						}
	
						// Find out whether there is a need to reset the active mpr
						// flag
						recordStatusCd = tmp.getRecordStatusCd();
						if (!isActiveMpr
								&& recordStatusCd != null
								&& recordStatusCd
										.equalsIgnoreCase(NEDSSConstants.RECORD_STATUS_ACTIVE)) {
							isActiveMpr = true;
						}
	
						// Populate attributes of patient search result vo if not
						// yet done
						if (!srchResultVOPopulates) {
							populateSrchResultVO(srchResultVO, tmp);
							srchResultVOPopulates = true;
						}
	
					}
				} // while (idItr.hasNext())
				srchResultVO.setPersonIdColl(entityIdList);
	
				// 5..... If active mpr, look into all of the collections to extract
				// all matching revision records
				// for this particular personParentUid
				if (isActiveMpr) {
					Collection<Object> allRevisions = extractAllRevisionsFor(
							personParentUid, personUidList, personBasicColl,
							personNameColl, personAddressColl, personTeleColl,
							personEntityIdColl);
					srchResultVO.setRevisionColl(allRevisions);
				}
	
				// It's done for this individual patient, add it to the search
				// result container if it is valid
				if (srchResultVO.getPersonUID() != null) {
					searchResult.add(srchResultVO);
					totalCount++;
				}
			}// while (itr.hasNext())
	
			Collections.sort(searchResult,
					PatientSrchResultVO.PatientSrchRsltVOComparator);
			ArrayList<Object> cacheList = new ArrayList<Object>();
			if (cacheNumber == 0)
				cacheNumber = this.getReturnedPatientNumber();
			for (int j = 0; j < searchResult.size(); j++) {
				PatientSrchResultVO psvo = new PatientSrchResultVO();
				psvo = (PatientSrchResultVO) searchResult.get(j);
				if (fromIndex > searchResult.size())
					break;
	
				if (cacheNumber == listCount)
					break;
				cacheList.add(searchResult.get(j));
				Collections.sort(cacheList,
						PatientSrchResultVO.PatientSrchRsltVOComparator);
				listCount++;
				logger.debug("List Counts = " + listCount);
			}
	
			
			displayPersonList = new DisplayPersonList(totalCount, cacheList,
					fromIndex, listCount);
			displayList.add(displayPersonList);
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return displayList;
	}

	private String addPersonCd(String personCd) {
		return " AND p.cd = '" + personCd + "'";
	}

	/* Extracts a list of unique person parent Uids from all collections */
	private ArrayList<Object> filterForUniquePersonParentUIDs(
			ArrayList<Object> personNameColl,
			ArrayList<Object> personAddressColl,
			ArrayList<Object> personTeleColl,
			ArrayList<Object> personEntityIdColl,
			ArrayList<Object> personEthnicityColl,
			ArrayList<Object> personRaceColl, ArrayList<Object> personRoleColl) {
		ArrayList<Object> list = new ArrayList<Object>();
		logger.debug("Start filterForUniquePersonParentUIDs()");

		list = (ArrayList<Object>) getDistinctIDs(personNameColl,
				personAddressColl);
		list = (ArrayList<Object>) getDistinctIDs(personTeleColl, list);
		list = (ArrayList<Object>) getDistinctIDs(personEntityIdColl, list);
		list = (ArrayList<Object>) getDistinctIDs(personEthnicityColl, list);
		list = (ArrayList<Object>) getDistinctIDs(personRaceColl, list);
		list = (ArrayList<Object>) getDistinctIDs(personRoleColl, list);

		// logger.debug("Number of parent uid after filtering: " +list.size());
		ArrayList<Object> idList = new ArrayList<Object>();
		// int cacheCount =0;
		/*
		 * get default cache count from property file. If not specified there
		 * take one from this file
		 */
		// if (propertyUtil.getNumberOfRows() != 0 )
		// cacheCount= propertyUtil.getNumberOfRows() ;
		// else
		// cacheCount = MAX_CACHE_COUNT;
		try{
			if (list != null) {
	
				// if (list.size() > cacheCount)
				// list.subList(cacheCount, list.size() ).clear();
				Iterator<Object> itr = list.iterator();
				while (itr.hasNext()) {
					PersonSearchResultTmp tmp = (PersonSearchResultTmp) itr.next();
					idList.add(tmp.getPersonParentUid());
				}
			}
			logger.debug("Number of parent uid returned: " + idList.size());
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return idList;
	}

	private ArrayList<Object> filterForUniquePersonParentUIDs(
			ArrayList<Object> personBasicColl,
			ArrayList<Object> personNameColl,
			ArrayList<Object> personAddressColl,
			ArrayList<Object> personTeleColl,
			ArrayList<Object> personEntityIdColl,
			ArrayList<Object> personEthnicityColl,
			ArrayList<Object> personRaceColl, ArrayList<Object> personRoleColl) {
		ArrayList<Object> list = new ArrayList<Object>();
		logger.debug("Start filterForUniquePersonParentUIDs()");
		list = (ArrayList<Object>) getDistinctIDs(personBasicColl,
				personAddressColl);
		list = (ArrayList<Object>) getDistinctIDs(personNameColl, list);
		list = (ArrayList<Object>) getDistinctIDs(personTeleColl, list);
		list = (ArrayList<Object>) getDistinctIDs(personEntityIdColl, list);
		list = (ArrayList<Object>) getDistinctIDs(personEthnicityColl, list);
		list = (ArrayList<Object>) getDistinctIDs(personRaceColl, list);
		list = (ArrayList<Object>) getDistinctIDs(personRoleColl, list);

		// logger.debug("Number of parent uid after filtering: " +list.size());
		ArrayList<Object> idList = new ArrayList<Object>();
		// int cacheCount =0;
		/*
		 * get default cache count from property file. If not specified there
		 * take one from this file
		 */
		// if (propertyUtil.getNumberOfRows() != 0 )
		// cacheCount= propertyUtil.getNumberOfRows() ;
		// else
		// cacheCount = MAX_CACHE_COUNT;
		try{
			if (list != null) {
	
				// if (list.size() > cacheCount)
				// list.subList(cacheCount, list.size() ).clear();
				Iterator<Object> itr = list.iterator();
				while (itr.hasNext()) {
					PersonSearchResultTmp tmp = (PersonSearchResultTmp) itr.next();
					idList.add(tmp.getPersonParentUid());
				}
			}
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		logger.debug("Number of parent uid returned: " + idList.size());
		return idList;
	}

	/* find all personUids from two lists. */
	private List<Object> getPersonUIDs(ArrayList<Object> list1,
			ArrayList<Object> list2) {
		// int cacheCount =0;
		if (list1 == null && list2 == null)
			return null;
		ArrayList<Object> list = new ArrayList<Object>();
		try{
			if (list2 == null) {
				HashMap<Object, Object> map = new HashMap<Object, Object>();
				Iterator<Object> itr = list1.iterator();
				while (itr.hasNext()) {
					PersonSearchResultTmp tmp = (PersonSearchResultTmp) itr.next();
					map.put(tmp.getPersonUid(), tmp);
				}
				Set<Object> set = map.keySet();
				itr = set.iterator();
				while (itr.hasNext()) {
					Long uid = (Long) itr.next();
					list.add(map.get(uid));
				}
				return list;
			}
			if (list1 == null) {
				HashMap<Object, Object> map = new HashMap<Object, Object>();
				Iterator<Object> itr = list2.iterator();
				while (itr.hasNext()) {
					PersonSearchResultTmp tmp = (PersonSearchResultTmp) itr.next();
					map.put(tmp.getPersonUid(), tmp);
				}
				Set<Object> set = map.keySet();
				itr = set.iterator();
				while (itr.hasNext()) {
					Long uid = (Long) itr.next();
					list.add(map.get(uid));
				}
				return list;
			}
			/* if both list1 and list2 are there */
			// load hashmap for first list
			if (list1 != null && list2 != null) {
				HashMap<Object, Object> map1 = new HashMap<Object, Object>();
				HashMap<Object, Object> map2 = new HashMap<Object, Object>();
				int count = 0;
				Iterator<Object> itr = list1.iterator();
				while (itr.hasNext()) {
					PersonSearchResultTmp tmp = (PersonSearchResultTmp) itr.next();
					map1.put(tmp.getPersonUid(), tmp);
				}
				// load hashmap for second list
				itr = list2.iterator();
				while (itr.hasNext()) {
					PersonSearchResultTmp tmp = (PersonSearchResultTmp) itr.next();
					map2.put(tmp.getPersonUid(), tmp);
				}
				map1.putAll(map2);
				Set<Object> set = map1.keySet();
				itr = set.iterator();
				while (itr.hasNext()) {
					Long uid = (Long) itr.next();
					list.add(map1.get(uid));
				}
	
			}
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return list;
	}

	/* find all distinct person parent Uids from two lists. */
	private List<Object> getDistinctIDs(ArrayList<Object> list1,
			ArrayList<Object> list2) {
		// int cacheCount =0;
		if (list1 == null && list2 == null)
			return null;
		ArrayList<Object> list = new ArrayList<Object>();
		try{
			if (list2 == null) {
				HashMap<Object, Object> map = new HashMap<Object, Object>();
				Iterator<Object> itr = list1.iterator();
				while (itr.hasNext()) {
					PersonSearchResultTmp tmp = (PersonSearchResultTmp) itr.next();
					Long personParentUid = tmp.getPersonParentUid();
					if (personParentUid != null
							&& personParentUid.compareTo(tmp.getPersonUid()) == 0)
						map.put(personParentUid, tmp);
					else if (personParentUid != null
							&& !map.containsKey(personParentUid))
						map.put(personParentUid, tmp);
				}
				Set<Object> set = map.keySet();
				itr = set.iterator();
				while (itr.hasNext()) {
					Long uid = (Long) itr.next();
					list.add(map.get(uid));
				}
				Collections.sort(list,
						PersonSearchResultTmp.PatientSearchComparator);
				return list;
			}
			if (list1 == null) {
				HashMap<Object, Object> map = new HashMap<Object, Object>();
				Iterator<Object> itr = list2.iterator();
				while (itr.hasNext()) {
					PersonSearchResultTmp tmp = (PersonSearchResultTmp) itr.next();
					Long personParentUid = tmp.getPersonParentUid();
					if (personParentUid != null
							&& personParentUid.compareTo(tmp.getPersonUid()) == 0)
						map.put(personParentUid, tmp);
					else if (personParentUid != null
							&& !map.containsKey(personParentUid))
						map.put(personParentUid, tmp);
				}
				Set<Object> set = map.keySet();
				itr = set.iterator();
				while (itr.hasNext()) {
					Long uid = (Long) itr.next();
					list.add(map.get(uid));
				}
				Collections.sort(list,
						PersonSearchResultTmp.PatientSearchComparator);
				return list;
			}
			/* if both list1 and list2 are there */
			// load hashmap for first list
			if (list1 != null && list2 != null) {
				HashMap<Object, Object> map1 = new HashMap<Object, Object>();
				HashMap<Object, Object> map2 = new HashMap<Object, Object>();
				int count = 0;
				Iterator<Object> itr = list1.iterator();
				while (itr.hasNext()) {
					PersonSearchResultTmp tmp = (PersonSearchResultTmp) itr.next();
					Long personParentUid = tmp.getPersonParentUid();
					if (personParentUid != null
							&& personParentUid.compareTo(tmp.getPersonUid()) == 0)
						map1.put(personParentUid, tmp);
					else if (personParentUid != null
							&& !map1.containsKey(personParentUid))
						map1.put(personParentUid, tmp);
				}
				// load hashmap for second list
				itr = list2.iterator();
				while (itr.hasNext()) {
					PersonSearchResultTmp tmp = (PersonSearchResultTmp) itr.next();
					Long personParentUid = tmp.getPersonParentUid();
					if (personParentUid != null
							&& personParentUid.compareTo(tmp.getPersonUid()) == 0)
						map2.put(personParentUid, tmp);
					else if (personParentUid != null
							&& !map2.containsKey(personParentUid))
						map2.put(personParentUid, tmp);
	
				}
				// compare and create list of common
				Set<Object> set = map1.keySet();
				itr = set.iterator();
				while (itr.hasNext()) {
					Long uid = (Long) itr.next();
					if (map2.containsKey(uid)) {
						list.add(map1.get(uid));
					}
				}
				Collections.sort(list,
						PersonSearchResultTmp.PatientSearchComparator);
				return list;
			}
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return list;
	}

	/*
	 * find ALL common personUids from all collections for a list of selected
	 * person parent uids
	 */
	private ArrayList<Object> filterForCommonPersonUIDs(
			ArrayList<Object> personNameColl,
			ArrayList<Object> personAddressColl,
			ArrayList<Object> personTeleColl,
			ArrayList<Object> personEntityIdColl,
			ArrayList<Object> personEthnicityColl,
			ArrayList<Object> personRaceColl, ArrayList<Object> personRoleColl,
			ArrayList<Object> parentUIDs) {
		ArrayList<Object> list = new ArrayList<Object>();
		ArrayList<Object> idList = new ArrayList<Object>();
		try{
			list = (ArrayList<Object>) getIDs(personNameColl, personAddressColl);
			list = (ArrayList<Object>) getIDs(personTeleColl, list);
			list = (ArrayList<Object>) getIDs(personEntityIdColl, list);
			list = (ArrayList<Object>) getIDs(personEthnicityColl, list);
			list = (ArrayList<Object>) getIDs(personRaceColl, list);
			list = (ArrayList<Object>) getIDs(personRoleColl, list);
		
			if (list != null) {
				logger.debug("All person uids before filtering by parent ids: "
						+ list.size());
	
				Iterator<Object> itr = list.iterator();
				while (itr.hasNext()) {
					PersonSearchResultTmp tmp = (PersonSearchResultTmp) itr.next();
					if (parentUIDs != null && !parentUIDs.isEmpty()
							&& tmp.getPersonParentUid() != null
							&& parentUIDs.contains(tmp.getPersonParentUid()))
						idList.add(tmp.getPersonUid());
				}
			}
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return idList;
	}

	private ArrayList<Object> filterForCommonPersonBasicUIDs(
			ArrayList<Object> personBasicColl,
			ArrayList<Object> personNameColl,
			ArrayList<Object> personAddressColl,
			ArrayList<Object> personTeleColl,
			ArrayList<Object> personEntityIdColl,
			ArrayList<Object> personEthnicityColl,
			ArrayList<Object> personRaceColl, ArrayList<Object> personRoleColl,
			ArrayList<Object> parentUIDs) {
		ArrayList<Object> list = new ArrayList<Object>();
		ArrayList<Object> idList = new ArrayList<Object>();
		try{
			list = (ArrayList<Object>) getIDs(personBasicColl, personNameColl);
			list = (ArrayList<Object>) getIDs(personAddressColl, list);
			list = (ArrayList<Object>) getIDs(personTeleColl, list);
			list = (ArrayList<Object>) getIDs(personEntityIdColl, list);
			list = (ArrayList<Object>) getIDs(personEthnicityColl, list);
			list = (ArrayList<Object>) getIDs(personRaceColl, list);
			list = (ArrayList<Object>) getIDs(personRoleColl, list);
			if (list != null) {
				logger.debug("All person uids before filtering by parent ids: "
						+ list.size());
	
				Iterator<Object> itr = list.iterator();
				while (itr.hasNext()) {
					PersonSearchResultTmp tmp = (PersonSearchResultTmp) itr.next();
					if (parentUIDs != null && !parentUIDs.isEmpty()
							&& tmp.getPersonParentUid() != null
							&& parentUIDs.contains(tmp.getPersonParentUid()))
						idList.add(tmp.getPersonUid());
				}
			}
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return idList;
	}

	private void addNameDTObjectTo(ArrayList<Object> nameList,
			PersonSearchResultTmp tmp) {
		try{
			if (!(tmp.getNameUseCd() == null)) {
				PersonNameDT nameDT = new PersonNameDT();
				nameDT.setPersonUid(tmp.getPersonUid());
				logger.debug("Full.. Name " + tmp.getFirstName() + " "
						+ tmp.getLastName());
				logger.debug("Full.. Name " + tmp.getFirstName() + " "
						+ tmp.getLastName() + " Middle " + tmp.getNmMiddle()
						+ " Suffix  " + tmp.getNmSuffix());
				nameDT.setFirstNm(tmp.getFirstName());
				nameDT.setLastNm(tmp.getLastName());
				nameDT.setMiddleNm(tmp.getNmMiddle());
				nameDT.setNmSuffix(tmp.getNmSuffix());
	
				if (tmp.getNameUseCd() != null
						&& tmp.getNameUseCd().trim().length() != 0) {
					CachedDropDownValues cache = new CachedDropDownValues();
					TreeMap<?, ?> map = cache.getCodedValuesAsTreeMap("P_NM_USE");
					map = cache.reverseMap(map); // we can add another method that
													// do not do reverse
					nameDT.setNmUseCd((String) map.get(tmp.getNameUseCd()));
				}
	
				// nameDT.setNmUseCd(tmp.getNameDesc());
				logger.debug("Full.. Name in nameDT: " + nameDT.getFirstNm() + " "
						+ nameDT.getLastNm() + " " + nameDT.getNmUseCd() + " "
						+ nameDT.getPersonUid());
				nameList.add(nameDT);
			}
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	private void addRaceDTObjectTo(ArrayList<Object> raceList,
			PersonSearchResultTmp tmp) {
		try{
			logger.debug("\n\n\n ****** this is add race collection in to object  "
					+ tmp.getNameUseCd());
			// if (! (tmp.getNameUseCd() == null)) {
			PersonRaceDT raceDT = new PersonRaceDT();
			raceDT.setPersonUid(tmp.getPersonUid());
			logger.debug("Full.. Race " + tmp.getRaceCd());
			raceDT.setRaceCd(tmp.getRaceCd());
			raceDT.setRaceDescTxt(tmp.getRaceDescTxt());
			raceList.add(raceDT);
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		// }
	}

	private void addAddressLocatorDTObjectTo(ArrayList<Object> locatorList,
			PersonSearchResultTmp tmp) {
		try{
			CachedDropDownValues cache = new CachedDropDownValues();
			EntityLocatorParticipationDT entityLocatorDT = new EntityLocatorParticipationDT();
			entityLocatorDT.setCd(tmp.getLocatorCd());
			// entityLocatorDT.setCdDescTxt(tmp.getLocatorTypeCdDesc());
			if (tmp.getLocatorCd() != null
					&& tmp.getLocatorCd().trim().length() != 0) {
				TreeMap<?, ?> map = cache
						.getCodedValuesAsTreeMap("EL_TYPE_PST_PAT");
				map = cache.reverseMap(map); // we can add another method that do
												// not do reverse
				entityLocatorDT.setCdDescTxt((String) map.get(tmp.getLocatorCd()));
			}
			entityLocatorDT.setClassCd(tmp.getClassCd());
			entityLocatorDT.setEntityUid(tmp.getPersonUid());
			entityLocatorDT.setLocatorUid(tmp.getLocatorUid());
			// entityLocatorDT.setUseCd(tmp.getLocatorUseCdDesc());
			if (tmp.getLocatorUseCd() != null
					&& tmp.getLocatorUseCd().trim().length() != 0) {
				TreeMap<?, ?> map = cache.getCodedValuesAsTreeMap("EL_USE_PST_PAT");
				map = cache.reverseMap(map); // we can add another method that do
												// not do reverse
				entityLocatorDT.setUseCd((String) map.get(tmp.getLocatorUseCd()));
				if (tmp.getState() != null && tmp.getState().length() > 0)
					entityLocatorDT.setLocatorDescTxt(cache
							.getCountiesByStateString(tmp.getState()));
				else
					entityLocatorDT.setLocatorDescTxt("");
			}
	
			entityLocatorDT.setCd(tmp.getLocatorCd());
			PostalLocatorDT postalLocatorDT = new PostalLocatorDT();
			postalLocatorDT.setStateCd(tmp.getState());
			postalLocatorDT.setCityCd(tmp.getCity());
			postalLocatorDT.setCntyCd(tmp.getCntyCd());
			postalLocatorDT.setCntryCd(tmp.getCntryCd());
			postalLocatorDT.setCityDescTxt(tmp.getCity());
			postalLocatorDT.setStreetAddr1(tmp.getStreetAddr1());
			postalLocatorDT.setStreetAddr2(tmp.getStreetAddr2());
			postalLocatorDT.setPostalLocatorUid(tmp.getLocatorUid());
			postalLocatorDT.setZipCd(tmp.getZip());
			entityLocatorDT.setThePostalLocatorDT(postalLocatorDT);
			locatorList.add(entityLocatorDT);
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	private void addTeleLocatorDTObjectTo(ArrayList<Object> locatorList,
			PersonSearchResultTmp tmp) {
		try{
			CachedDropDownValues cache = new CachedDropDownValues();
			EntityLocatorParticipationDT entityLocatorDT = new EntityLocatorParticipationDT();
			entityLocatorDT.setCd(tmp.getLocatorCd());
			// entityLocatorDT.setCdDescTxt(tmp.getLocatorTypeCdDesc());
			if (tmp.getLocatorCd() != null
					&& tmp.getLocatorCd().trim().length() != 0) {
				TreeMap<?, ?> map = cache.getCodedValuesAsTreeMap("EL_TYPE");
				map = cache.reverseMap(map); // we can add another method that do
												// not do reverse
				entityLocatorDT.setCdDescTxt((String) map.get(tmp.getLocatorCd()));
			}
			entityLocatorDT.setClassCd(tmp.getClassCd());
			entityLocatorDT.setEntityUid(tmp.getPersonUid());
			entityLocatorDT.setLocatorUid(tmp.getLocatorUid());
			// entityLocatorDT.setUseCd(tmp.getLocatorUseCdDesc());
			if (tmp.getLocatorUseCd() != null
					&& tmp.getLocatorUseCd().trim().length() != 0) {
				TreeMap<?, ?> map = cache.getCodedValuesAsTreeMap("EL_USE");
				map = cache.reverseMap(map); // we can add another method that do
												// not do reverse
				entityLocatorDT.setUseCd((String) map.get(tmp.getLocatorUseCd()));
			}
			entityLocatorDT.setCd(tmp.getLocatorCd());
	
			TeleLocatorDT teleLocatorDT = new TeleLocatorDT();
			teleLocatorDT.setPhoneNbrTxt(tmp.getTelephoneNbr());
			teleLocatorDT.setExtensionTxt(tmp.getExtensionTxt());
			teleLocatorDT.setEmailAddress(tmp.getEmailAddress());
			entityLocatorDT.setTheTeleLocatorDT(teleLocatorDT);
			locatorList.add(entityLocatorDT);
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	private void addEntityIdDTObjectTo(ArrayList<Object> entityIdList,
			PersonSearchResultTmp tmp) {
		try{
			CachedDropDownValues cache = new CachedDropDownValues();
			EntityIdDT entityIdDT = new EntityIdDT();
			entityIdDT.setEntityUid(tmp.getPersonUid());
			if (tmp.getEiTypeDesc() != null
					&& tmp.getEiTypeDesc().trim().length() != 0) {
				TreeMap<?, ?> map = cache.getCodedValuesAsTreeMap("EI_TYPE_PAT");
				map = cache.reverseMap(map); // we can add another method that do
												// not do reverse
				entityIdDT.setTypeCd((String) map.get(tmp.getEiTypeDesc()));
			}
			entityIdDT.setRootExtensionTxt(tmp.getEiRootExtensioTxt());
			entityIdList.add(entityIdDT);
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	private void populateSrchResultVO(PatientSrchResultVO srchResultVO,
			PersonSearchResultTmp tmp) {
		try{
			srchResultVO.setPersonDOB(tmp.getDob());
			srchResultVO.setEthnicGroupInd(tmp.getEthnicGroupInd());
			srchResultVO.setAsOfDateAdmin(tmp.getAsOfDateAdmin());
			srchResultVO.setAgeReported(tmp.getAgeReported());
			srchResultVO.setAgeUnit(tmp.getAgeUnit());
			srchResultVO.setSsn(tmp.getSsn());
			srchResultVO.setMaritalStatusCd(tmp.getMaritalStatusCd());
			srchResultVO.setSex(tmp.getCurrentSex());
			srchResultVO.setRecordStatusCd(tmp.getRecordStatusCd());
			srchResultVO.setDeceasedTime(tmp.getDeceasedTime());
			srchResultVO.setVersionCtrlNbr(tmp.getVersionCtrlNbr());
	
			if (tmp.getCurrentSex() != null
					&& tmp.getCurrentSex().trim().length() != 0) {
				CachedDropDownValues cache = new CachedDropDownValues();
				TreeMap<?, ?> map = cache.getCodedValuesAsTreeMap("SEX");
				map = cache.reverseMap(map); // we can add another method that do
												// not do reverse
				srchResultVO.setCurrentSex((String) map.get(tmp.getCurrentSex()));
			}
			srchResultVO.setPersonUID(tmp.getPersonUid());
			srchResultVO.setPersonParentUid(tmp.getPersonParentUid());
			srchResultVO.setPersonLocalID(tmp.getLocalId());
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	private Collection<Object> extractAllRevisionsFor(Long personParentUid,
			ArrayList<Object> personUidList, ArrayList<Object> personBasicColl,
			ArrayList<Object> personNameColl,
			ArrayList<Object> personAddressColl,
			ArrayList<Object> personTeleColl,
			ArrayList<Object> personEntityIdColl) {
		List<Object> revisionList = new ArrayList<Object>();
		try{
			List<Object> revisionUidList = findAllRevisionUidsFor(personParentUid,
					personBasicColl, personNameColl, personAddressColl,
					personTeleColl, personEntityIdColl);
	
			Iterator<Object> it = revisionUidList.iterator();
			while (it.hasNext()) {
				Long patientRevisionUid = (Long) it.next();
				PatientRevisionSrchResultVO srchResultVO = new PatientRevisionSrchResultVO();
	
				// 1..... Look into the name collection to extract all matching
				// records
				ArrayList<Object> nameList = new ArrayList<Object>();
				Iterator<Object> nameItr = personNameColl.iterator();
				CachedDropDownValues cache = new CachedDropDownValues();
				while (nameItr.hasNext()) {
					PersonSearchResultTmp tmp = (PersonSearchResultTmp) nameItr
							.next();
					if (tmp.getPersonUid().equals(patientRevisionUid)) {
						addNameDTObjectTo(nameList, tmp);
						if (!(tmp.getNameUseCd() == null)) {
	
							srchResultVO.setPersonDOB(tmp.getDob());
							if (tmp.getCurrentSex() != null
									&& tmp.getCurrentSex().trim().length() != 0) {
								TreeMap<?, ?> map = cache
										.getCodedValuesAsTreeMap("SEX");
								map = cache.reverseMap(map); // we can add another
																// method that do
																// not do reverse
								srchResultVO.setCurrentSex((String) map.get(tmp
										.getCurrentSex()));
							}
							srchResultVO.setPersonUID(tmp.getPersonUid());
							srchResultVO.setPersonLocalID(tmp.getLocalId());
	
						} // if ( ! (tmp.getNameUseCd() == null))
					} // if (tmp.getPersonUid() == personUid)
				} // while (nameItr.hasNext())
				srchResultVO.setPersonNameColl(nameList);
				// logger.debug("Number of names added : " + nameList.size());
				ArrayList<Object> basicList = new ArrayList<Object>();
				Iterator<Object> basicItr = personBasicColl.iterator();
				while (nameItr.hasNext()) {
					PersonSearchResultTmp tmp = (PersonSearchResultTmp) basicItr
							.next();
					if (tmp.getPersonUid().equals(patientRevisionUid)) {
						addNameDTObjectTo(basicList, tmp);
						srchResultVO.setPersonUID(tmp.getPersonUid());
						srchResultVO.setPersonLocalID(tmp.getLocalId());
					} // if (tmp.getPersonUid() == personUid)
				} // while (nameItr.hasNext())
				if (srchResultVO.getPersonNameColl() != null
						&& srchResultVO.getPersonNameColl().size() > 0)
					srchResultVO.getPersonNameColl().addAll(basicList);
				else
					srchResultVO.setPersonNameColl(basicList);
				// logger.debug("Number of names added : " + nameList.size());
	
				// 2..... Look into the address locator collection to extract all
				// matching records
				ArrayList<Object> locatorList = new ArrayList<Object>();
				Iterator<Object> addressItr = personAddressColl.iterator();
				while (addressItr.hasNext()) {
					EntityLocatorParticipationDT entityLocatorDT = new EntityLocatorParticipationDT();
					PersonSearchResultTmp tmp = (PersonSearchResultTmp) addressItr
							.next();
					if (tmp.getPersonUid().equals(patientRevisionUid)) {
						if (tmp.getClassCd() != null) {
							addAddressLocatorDTObjectTo(locatorList, tmp);
							logger.debug("Added address locator: ");
						}
					}
				} // while (addressItr.hasNext())
					// logger.debug("Number of address added : " +
					// locatorList.size());
	
				// 3..... Look into the tele locator collection to extract all
				// matching records
				Iterator<Object> teleItr = personTeleColl.iterator();
				while (teleItr.hasNext()) {
					EntityLocatorParticipationDT entityLocatorDT = new EntityLocatorParticipationDT();
					PersonSearchResultTmp tmp = (PersonSearchResultTmp) teleItr
							.next();
					if (tmp.getPersonUid().equals(patientRevisionUid)) {
						if (tmp.getClassCd() != null) {
							addTeleLocatorDTObjectTo(locatorList, tmp);
							logger.debug("Added tele locator: ");
						}
					}
				} // while (teleItr.hasNext())
				srchResultVO.setPersonLocatorsColl(locatorList);
	
				// 4..... Look into the entity id collection to extract all matching
				// records
				ArrayList<Object> entityIdList = new ArrayList<Object>();
				Iterator<Object> idItr = personEntityIdColl.iterator();
				while (idItr.hasNext()) {
					EntityIdDT entityIdDT = new EntityIdDT();
					PersonSearchResultTmp tmp = (PersonSearchResultTmp) idItr
							.next();
					if (tmp.getPersonUid().equals(patientRevisionUid)) {
						if (!(tmp.getEiTypeDesc() == null)) {
							// logger.debug("inside typeCd, what is it? "+ typeCd);
							addEntityIdDTObjectTo(entityIdList, tmp);
						}
					}
				} // while (idItr.hasNext())
				srchResultVO.setPersonIdColl(entityIdList);
	
				revisionList.add(srchResultVO);
			}
	
			Collections
					.sort(revisionList,
							PatientRevisionSrchResultVO.PatientRevisionSrchRsltVOComparator);
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return revisionList;
	}

	private List<Object> findAllRevisionUidsFor(Long personParentUid,
			ArrayList<Object> personBasicColl,
			ArrayList<Object> personNameColl,
			ArrayList<Object> personAddressColl,
			ArrayList<Object> personTeleColl,
			ArrayList<Object> personEntityIdColl) {
		ArrayList<Object> list = new ArrayList<Object>();
		ArrayList<Object> revisionIdList = new ArrayList<Object>();
		logger.debug("Start findAllRevisionUidsFor this parent uid - "
				+ personParentUid);
		logger.debug("personNameColl.size() - " + personNameColl.size());
		logger.debug("personAddressColl.size() - " + personAddressColl.size());
		logger.debug("personTeleColl.size() - " + personTeleColl.size());
		logger.debug("personEntityIdColl.size() - " + personEntityIdColl.size());
		try{
			list = (ArrayList<Object>) getPersonUIDs(personBasicColl,
					personNameColl);
			list = (ArrayList<Object>) getPersonUIDs(personAddressColl, list);
			list = (ArrayList<Object>) getPersonUIDs(personTeleColl, list);
			list = (ArrayList<Object>) getPersonUIDs(personEntityIdColl, list);
	
			
			logger.debug("Number of revision for this parent uid - "
					+ personParentUid + " is: " + list.size());
	
			if (list != null) {
				Iterator<Object> itr = list.iterator();
				while (itr.hasNext()) {
					PersonSearchResultTmp tmp = (PersonSearchResultTmp) itr.next();
					if (tmp != null) {
						logger.debug("personParentUid: " + personParentUid);
						logger.debug("tmp.getPersonParentUid();: "
								+ tmp.getPersonParentUid());
						logger.debug("tmp.getPersonUid();: " + tmp.getPersonUid());
						Long currentParentUid = tmp.getPersonParentUid();
						Long currentPersonUid = tmp.getPersonUid();
	
						if (currentParentUid.compareTo(personParentUid) == 0
								&& currentParentUid.compareTo(currentPersonUid) != 0) {
							revisionIdList.add(currentPersonUid);
							logger.debug("revisionIdList.add(currentPersonUid): "
									+ currentPersonUid + " for personParentUid: "
									+ personParentUid
									+ " and for currentParentUid: "
									+ currentParentUid);
						}
					}
				}
			}
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return revisionIdList;
	}

	private String formatPhoneNumber(String phoneNumber) {
		String formattedPhoneNumber = "";
		try{
			if (phoneNumber.indexOf("-") == 3 && phoneNumber.lastIndexOf("-") == 4
					&& phoneNumber.length() == 5) {
				formattedPhoneNumber = phoneNumber.substring(0, 4);
			} else if (phoneNumber.indexOf("-") == 0
					&& phoneNumber.lastIndexOf("-") == 1) {
				formattedPhoneNumber = phoneNumber.substring(1, 6);
				logger.debug("\n The  formattedPhoneNumber =  "
						+ formattedPhoneNumber);
			} else
				formattedPhoneNumber = phoneNumber;
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return formattedPhoneNumber;

	}

	private int getReturnedPatientNumber() {
		if (propertyUtil.getNumberOfRows() != 0) {
			return propertyUtil.getNumberOfRows();
		} else
			throw new NEDSSSystemException(
					"Expected the number of patient search "
							+ "results returned to the caller defined in the property file.");
	}

	// ###################################################################/
	// END: findPatientsByKeyWords(PersonSearchVO find, int cacheNumber, int
	// fromIndex)
	// ###################################################################/

	public static void main(String[] args) {
		try {

			FindPersonDAOImpl search = new FindPersonDAOImpl();
			/*
			 * PersonSearchVO searchVO = new PersonSearchVO();
			 * searchVO.setLastName("LastName0");
			 * searchVO.setLastNameOperator("=");
			 * searchVO.setFirstName("Williams");
			 * searchVO.setFirstNameOperator("=");
			 * searchVO.setStreetAddr1("StreetAddr1");
			 * searchVO.setStreetAddr1Operator("=");
			 * searchVO.setBirthTime("10/13/1988");
			 * searchVO.setBirthTimeOperator("=");
			 * searchVO.setRootExtensionTxt("Root ext text");
			 * searchVO.setRootExtensionTxtOperator("=");
			 * searchVO.setPhoneNbrTxt("404-417-3151");
			 * searchVO.setPhoneNbrTxtOperator("="); searchVO.setActive(true);
			 * ArrayList<Object> arr =
			 * search.findPersonsByKeyWords(searchVO,0,0); StringBuffer
			 * testQuery = new StringBuffer(" ");
			 * testQuery.append(" ORDER BY 1");
			 * logger.debug("Complete Query is: " + testQuery.toString());
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ************************************************************************
	// ************************************************************************
	// *********************** NEW FIND PATIENT: START ************************
	// ************************************************************************

	// constant variables to represent various type of data used in patient
	// search

	public ArrayList<Object> findPatientsByKeyWords(PatientSearchVO find,
			int cacheNumber, int fromIndex, NBSSecurityObj nbsSecurityObj) {
		// nclogger.info("--" + new java.util.Date().getTime() +
		// "--DAO:findPatientsByKeyWords4 : START");

		ArrayList<Object> result = new ArrayList<Object>();
		try{
			HashMap<Object, Object> revisionsWithMprs = new HashMap<Object, Object>(); // key
																						// is
																						// person_uid,
																						// value
																						// is
																						// person_parent_uid
	
			if (cacheNumber <= 0) {
				// if cacheNumber if not specified by the client, set it to the max
				// number of records to be
				// returned by the SQL select statement
				cacheNumber = this.getReturnedPatientNumber();
			}
	
			ArrayList<Object> queries;
			//if(find.getReportType()!=null && find.getReportType().equalsIgnoreCase("I"))//Investigation
		//		queries= buildSearchQueryInvestigation(find, cacheNumber, nbsSecurityObj);
		//	else if(find.getReportType()!=null && find.getReportType().equalsIgnoreCase("LR"))//Laboratory Report
		//		queries= buildSearchQueryLaboratoryReport(find, cacheNumber, nbsSecurityObj);
			//else
				queries= buildSearchQuery(find, cacheNumber);
			String searchQuery = "";
	
			if (queries != null && queries.size() > 1) 
				searchQuery = (String) queries.get(1);
			
	
			// nclogger.info("SEARCH QUERY: " + searchQuery);
			// nclogger.info("COUNT SEARCH QUERY: " + countSearchQuery);
			PersonSearchResultTmp searchResultName = new PersonSearchResultTmp();
			PersonConditionDT perconDT = new PersonConditionDT();
			TaskListProxyDAOImpl taskListProxyDAOImpl = new TaskListProxyDAOImpl();
			Integer intTotalCount = 0;
			//Integer intTotalCount = taskListProxyDAOImpl.getCountbySP(countSearchQuery);
			// nclogger.info("COUNT :" + intTotalCount);
			
			String personParentUids = null;
			if(searchQuery!=null && searchQuery.trim().length()>0)		
				personParentUids = this.getPersonParentUids(searchQuery);
			if(personParentUids==null)
				personParentUids = "-1";
	
			ArrayList<Object> personVOs = null;
	
			/*if (intTotalCount == null || intTotalCount.intValue() <= 0) {
				// if count query returned less than one item, there is
				// no need to waiste time performing the full search/retrieval
				personVOs = new ArrayList<Object>();
				intTotalCount = new Integer(0);
			} else {*/
				String finalQuery="";
				if(find.getReportType()!=null && find.getReportType().equalsIgnoreCase("I"))
						finalQuery=searchQuery;//buildRetrievalQueryInvestigation(searchQuery);
					else	
						finalQuery = buildRetrievalQuery(personParentUids);
	
				// logger.debug("FINAL QUERY: " + finalQuery);

				ArrayList<?> searchResult = (ArrayList<?>) preparedStmtMethod(
						searchResultName, null, finalQuery, NEDSSConstants.SELECT);
	
				// assemble VOs into the form expected by the client (mprs, include
				// revisions, etc)

				if(find.getReportType()!=null && find.getReportType().equalsIgnoreCase("I")){
					personVOs= assembleInvestigationData(searchResult);
				}
				else{
					
					personVOs = assemblePersonData(searchResult);
	
					// Specific for Contact Tracing.
					getConditionDts(find, personParentUids, perconDT, personVOs);
		
					// sort assembled VOs by First Name, Last Name, and DOB
					// Note: all revisions are sorted within assemblePersonData() method
					if (personVOs != null) {
						Collections.sort(personVOs,
								PatientSrchResultVO.PatientSrchRsltVOComparator);
					}
				
				}
			//}
	
			DisplayPersonList displayPersonList = new DisplayPersonList(
					personVOs.size(), personVOs, fromIndex,
					personVOs.size());
			result.add(displayPersonList);
			result.add(finalQuery);//This is for having access to the query used from outside of the EJB and being able to store it in the DB while saving queues from Event Search as Custom queues to be shown from Home Page.
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return result;
	}

	private void getConditionDts(PatientSearchVO find, String searchQuery,
			PersonConditionDT perconDT, ArrayList<Object> personVOs) {
		try{
			ArrayList<?> personConditionDTs;
			if (find.isContactTracing()) {
				String conditionQuery = assemblePersonCondtion(searchQuery);
	
				// logger.debug("Condition QUERY: " + conditionQuery);
				personConditionDTs = (ArrayList<?>) preparedStmtMethod(
						perconDT, null, conditionQuery, NEDSSConstants.SELECT);
	
				Iterator<?> personVOsIt = personVOs.iterator();
				Collection<Object> col = new ArrayList<Object>();
				while (personVOsIt.hasNext()) {
					boolean exitPersonConditionloop = false;
					PersonSrchResultVO dt1 = (PersonSrchResultVO) personVOsIt
							.next();
					if (personConditionDTs != null) {
						Iterator<?> iteratorPersonCondIt = personConditionDTs
								.iterator();
						while (iteratorPersonCondIt.hasNext()) {
							PersonConditionDT dt = (PersonConditionDT) iteratorPersonCondIt
									.next();
							if (dt.getPersonUid().equals(dt1.getPersonUID())) {
								col.add(dt.getCondition());
							}
						}
					}
					dt1.setConditionCdColl(col);
					col = new ArrayList<Object>();
				}
			}
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	/**
	 * This method builds two queries: one is a COUNT statement, another one is
	 * search SELECT statement. Each query covers all tables corresponding to
	 * any combination of search criteria specified by the client. The search
	 * SQL select is built using nested SQL (subqueries) with derived table
	 * joins. The following ideas are used: 1) the first subquery is a SELECT
	 * against PERSON table or a join with PERSON table of one of the following
	 * tables: PERSON_NAME, PERSON_RACE, POSTAL_LOCATOR, TELE_LOCATOR,
	 * PERSON_ETHNICK_GROUP 2) every subquery returns person_parent_uid to use
	 * for a join with the following wrapping query 3) the final resulting query
	 * returns all person_uid with the corresponding person_parent_uid
	 * representing MPRs and revisions that satisfy all search criteria
	 *
	 * @author ykulikova
	 * @param find
	 *            Represents all search criteria specified by the client.
	 * @param cacheNumber
	 *            Represents the max number of MPRs satisfying search criteria
	 *            to be retrieved.
	 * @return Two queries, one is a COUNT query, which is executed would return
	 *         the total number of MPRs in the database satisfying search
	 *         criteria; another one is the SELECT query, which returns all
	 *         person uids representing MPRs and revisions that satisfy all
	 *         search criteria.
	 *
	 */
	private ArrayList<Object> buildSearchQuery(PatientSearchVO find,
			int cacheNumber) {
		ArrayList<Object> result = new ArrayList<Object>();
		try{
			ArrayList<Object> specifiedSearchCriteriaTypes = computeSearchCriteriaTypes(find);
	
			if (specifiedSearchCriteriaTypes != null
					&& !specifiedSearchCriteriaTypes.isEmpty()) {
				int sizeSC = specifiedSearchCriteriaTypes.size();
	
				String nestedQuery = "";
				String countQuery = "";
	
				for (int i = 0; i < sizeSC; i++) {
					int curSearchDataType = ((Integer) specifiedSearchCriteriaTypes
							.get(i)).intValue();
	
					if (i == 0)// first set of search criteria
					{
						nestedQuery = buildFirstNestedQuery(curSearchDataType, find);
					} else// other than first set of search criteria
					{
						nestedQuery = buildNonFirstNestedQuery(curSearchDataType,
								find, nestedQuery, i);
					}
	
					if (i == sizeSC - 1)// last set of search criteria
					{
						String temp = nestedQuery;
						nestedQuery = finishBuildingSearchQuery(nestedQuery,
								cacheNumber);
						countQuery = finishBuildingSearchCountQuery(temp);
					}
				}
	
				result.add(countQuery);
				result.add(nestedQuery);
			}
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return result;
	}
	
	
	
	

	/**
	 * This method adds final wrapping queries to the search SELECT statement.
	 * These final queries are added to make sure that all MPRs are sorted
	 * appropriately, and only cacheNumber of distinct MPRs is selected. The
	 * final query had only personUid and person_parentUid in SELECT statment.
	 * These final queries are not responsible for makeing sure that the final
	 * set of UIDs satisfies search criteria. That is expected to be taken care
	 * of by nestedQuery input parameter.
	 *
	 * @author ykulikova
	 * @param nestedQuery
	 *            Query, which takes care of selecting all person_parent_uids
	 *            that satisfy search criteria. It has to contain the following
	 *            column names in its select statement: "personParentUid",
	 *            "parentFirstName", "parentLastName" and "parentBirthTimeCalc".
	 * @param cacheNumber
	 *            Represents the max number of distinct MPR uids to retrieve.
	 * @return SQL SELECT statement to retrieve first cacheNumber MPR and
	 *         revision person_uid, which satisfy specified search criteria.
	 *
	 */
	private String finishBuildingSearchQuery(String nestedQuery, int cacheNumber) {
		StringBuffer query = new StringBuffer();
		try {
			query.append("select STUFF ((select ',' + CAST(personParentUid as varchar) from ( select distinct top ")
					.append(cacheNumber)
					.append(" p.person_parent_uid \"personParentUid\" from person p with (nolock) , ");

			// SQL Server
			{
				query.append(" (select distinct top ");
				query.append(cacheNumber); 
				query.append(" \"personParentUid\" from person p with (nolock) , ");
				query.append(" ( ");
				query.append(nestedQuery);
				query.append(" ) derivedTable ");
				query.append(" where p.person_uid=p.person_parent_uid and p.person_parent_uid=\"personParentUid\" ");
				query.append(" order by \"personParentUid\" ");
				query.append(") qq ");
			}
	
			query.append(" where qq.\"personParentUid\" = p.person_parent_uid ) as a  for XML PATH('')), 1, 1, '')");
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return query.toString();
	}

	/**
	 * This method adds final wrapping queries to the search COUNT statement.
	 * These final queries are added to make sure that only distinct MPRs are
	 * counted. The query does not trancate the number of results. The idea is
	 * to be able to count all MPRs that satisfy search criteria.
	 *
	 * @author ykulikova
	 * @param nestedQuery
	 *            Query, which takes care of selecting all person_parent_uids
	 *            that satisfy search criteria. It has to contain the following
	 *            column names in its select statement: "personParentUid",
	 *            "parentFirstName", "parentLastName" and "parentBirthTimeCalc".
	 * @return SQL SELECT COUNT(*) statement to count all distinct MPR and
	 *         revision person_uid, which satisfy specified search criteria.
	 */
	private String finishBuildingSearchCountQuery(String nestedQuery) {
		StringBuffer query = new StringBuffer();
		try{
			// SQL server
			{
				query.append(" select count(*) \"t_count\" from ");
				query.append("(select distinct \"personParentUid\" from ");
				query.append(" ( ");
				query.append(nestedQuery);
				query.append(") derivedTable ) t ");
			}
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return query.toString();
	}

	

	/**
	 * This method buils second, third, etc. subqueries for patient search. All
	 * subqueries except the first one are joins between the table indicated byt
	 * the search criteria of a specific type and a derived table defined by the
	 * nesedQuery.
	 *
	 * @param searchDataType
	 *            Indicates type of search criteris, which dictates which tables
	 *            have to queried.
	 * @param find
	 *            Represents all search criteria specified by the client.
	 * @param nestedQuery
	 *            Represents a nested query to be used to defined a derived
	 *            table. The nestedQuery has to have "personParentUid" column in
	 *            its SELECT statement.
	 * @param seqNumber
	 *            This value is used to create unique name for the derived
	 *            table.
	 * @return Returns input nestedQuery wrapped into the query, which
	 *         corresponds to the set of search criteria represented by
	 *         searchDataType.
	 */
	private String buildNonFirstNestedQuery(int searchDataType,
			PatientSearchVO find, String nestedQuery, int seqNumber) {
		String result = "";
		try{
			String nqDerivedTableName = "nq" + seqNumber;
			String wqDerivedTableName = "wq" + seqNumber;
			String wrappedNestedQuery = wrapNestedQuery(nestedQuery,
					nqDerivedTableName);
	
			switch (searchDataType) {
			case DT_NAME:
				result = buildNameNestedQuery(find, wrappedNestedQuery,
						wqDerivedTableName);
				break;
			case DT_ADDRESS:
				result = buildAddressNestedQuery(find, wrappedNestedQuery,
						wqDerivedTableName);
				break;
			case DT_TELE:
				result = buildTeleNestedQuery(find, wrappedNestedQuery,
						wqDerivedTableName);
				break;
			case DT_ETHNICITY:
				result = buildEthnicityNestedQuery(find, wrappedNestedQuery,
						wqDerivedTableName);
				break;
			case DT_RACE:
				result = buildRaceNestedQuery(find, wrappedNestedQuery,
						wqDerivedTableName);
				break;
			case DT_ID:
				result = buildIdsNestedQuery(find, wrappedNestedQuery,
						wqDerivedTableName);
				break;
			case DT_PERSON:
				result = buildPersonNestedQuery(find, wrappedNestedQuery,
						wqDerivedTableName);
				break;
			case DT_INVESTIGATION:
				result = buildInvestigationNestedQuery(find, wrappedNestedQuery,
						wqDerivedTableName);
				break;
			case DT_ABC_CASE_ID:
				result = buildAbcCaseNestedQuery(find, wrappedNestedQuery,
						wqDerivedTableName);
				break;
			case DT_STATE_CASE_ID:
				result = buildStateCaseNestedQuery(find, wrappedNestedQuery,
						wqDerivedTableName);
				break;
			case DT_VACCINATION:
				result = buildVaccinationNestedQuery(find, wrappedNestedQuery,
						wqDerivedTableName);
				break;
			case DT_NOTIFICATION:
				result = buildNotificationNestedQuery(find, wrappedNestedQuery,
						wqDerivedTableName);
				break;
			case DT_TREATMENT:
				result = buildTreatmentNestedQuery(find, wrappedNestedQuery,
						wqDerivedTableName);
				break;
			case DT_LAB_REPORT:
				result = buildLabReportNestedQuery(find, wrappedNestedQuery,
						wqDerivedTableName);
				break;
			case DT_MORBIDITY_REPORT:
				result = buildMorbReportNestedQuery(find, wrappedNestedQuery,
						wqDerivedTableName);
				break;
			// case DT_RVCT_STATE_CASE_NUMBER:
			// result = buildRVCTStateNestedQuery(find, wrappedNestedQuery,
			// wqDerivedTableName);
			// break;
			case DT_CITY_COUNTY_CASE_ID:
				result = buildCityCountyNestedQuery(find, wrappedNestedQuery,
						wqDerivedTableName);
				break;
			case DT_ACCESSION_NUMBER:// changes made for event search based on
										// accession number
				result = buildAccessionNumberNestedQuery(find, wrappedNestedQuery,
						wqDerivedTableName);
				break;
			case DT_DOC_NUMBER:// changes made for event search based on doc number
				result = buildDocNumberNestedQuery(find, wrappedNestedQuery,
						wqDerivedTableName);
				break;
			default:
				// raise exception
			}
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return result;
	}

	/**
	 * Builds SELECT portion of the SQL statement for all nested search queries.
	 *
	 * @param wqDerivedTableName
	 *            Represents the name of the derived table, that will be used
	 *            when constracting the quiry, which uses the SELECT statement
	 *            built by this method.
	 * @return SELECT clause with the following column names: "personParentUid",
	 *         "parentLastName", "parentFirstName", "parentBirthTimeCalc".
	 */
	private String buildNestedQuerySelect(String wqDerivedTableName) {
		StringBuffer select = new StringBuffer("");

		select.append(" select distinct ");
		select.append(wqDerivedTableName);
		select.append(".\"personParentUid\" ");
		return select.toString();
	}
	
	private String buildPersonNestedQuery(PatientSearchVO find,
			String nestedQuery, String wqDerivedTableName) {
		StringBuffer query = new StringBuffer("");

		query.append(buildNestedQuerySelect(wqDerivedTableName));

		query.append(" from person p with (nolock) , ( ");
		query.append(nestedQuery);
		query.append(" ) ");
		query.append(wqDerivedTableName);

		query.append(" where ");
		query.append(wqDerivedTableName);
		query.append(".\"personUid\" = ");
		query.append("p.person_uid and p.person_uid = p.person_parent_uid and ");
		query.append(buildPersonSearchWhereClause(find));

		return query.toString();
	}

	private String buildPersonSearchWhereClause(PersonSearchVO find) {
		StringBuffer sbuf = new StringBuffer();
		String oper = null;
		String whereAnd = " ";
		boolean firstWhere = true;
		try{
			if ((find.getBeforeBirthTime() != null)
					|| (find.getAfterBirthTime() != null)
					|| (find.getBirthTimeDay() != null)
					|| (find.getBirthTimeMonth() != null)
					|| (find.getBirthTimeYear() != null)) {

					if (find.getBirthTimeOperator().equals("=")) {
						
						if (find.getBirthTimeMonth() != null && find.getBirthTimeMonth().length() != 0
								&& find.getBirthTimeDay() != null && find.getBirthTimeDay().length() != 0
								&& find.getBirthTimeYear() != null && find.getBirthTimeYear().length() != 0) {
							String datevalue = find.getBirthTimeMonth() + "/" + find.getBirthTimeDay() + "/"
									+ find.getBirthTimeYear();
							if (firstWhere)
								firstWhere = false;
							else
								whereAnd = " AND ";
							sbuf.append(whereAnd + "(CONVERT(DATE, birth_time)) = '" + datevalue + "'");
						}

						else { 
						if (find.getBirthTimeMonth() != null
								&& find.getBirthTimeMonth().length() != 0) {
							if (firstWhere)
								firstWhere = false;
							else
								whereAnd = " AND ";
							sbuf.append(whereAnd + "(DATEPART(mm, birth_time)) = "
									+ find.getBirthTimeMonth());
						}
						if (find.getBirthTimeDay() != null
								&& find.getBirthTimeDay().length() != 0) {
							if (firstWhere)
								firstWhere = false;
							else
								whereAnd = " AND ";
							sbuf.append(whereAnd + "(DATEPART(dd, birth_time)) = "
									+ find.getBirthTimeDay());
						}
						if (find.getBirthTimeYear() != null
								&& find.getBirthTimeYear().length() != 0) {
							if (firstWhere)
								firstWhere = false;
							else
								whereAnd = " AND ";
							sbuf.append(whereAnd
									+ "(DATEPART(yyyy, birth_time)) = "
									+ find.getBirthTimeYear());
						}
						}
						// sbuf.append(whereAnd + " p.birth_time  " +
						// find.getBirthTimeOperator() +
						// " TO_DATE('" + find.getBirthTime() +
						// "' , 'mm/dd/yyyy')");
					} else {
						if ((find.getAfterBirthTime() != null && find
								.getAfterBirthTime().length() != 0)
								&& (find.getBeforeBirthTime() != null && find
										.getBeforeBirthTime().length() != 0)) {
							if (firstWhere)
								firstWhere = false;
							else
								whereAnd = " AND ";
							sbuf.append(whereAnd
									+ "BIRTH_TIME >=convert(datetime,'"
									+ find.getBeforeBirthTime()
									+ "') AND BIRTH_TIME <=convert(datetime,'"
									+ find.getAfterBirthTime() + "')");
						}
					}
					// sbuf.append(whereAnd + " p.birth_time  " +
					// find.getBirthTimeOperator() + " CONVERT(datetime,'" +
					// find.getBirthTime() + "')") ;
				}// End DOB where clause for Sql Server
			if (find.getCurrentSex() != null) {
				if (find.getCurrentSex().trim().length() != 0) {
					if (firstWhere)
						firstWhere = false;
					else
						whereAnd = " AND ";
					sbuf.append(whereAnd + " p.curr_sex_cd = " + "'"
							+ find.getCurrentSex().trim() + "' ");
				}
			}
			if (find.getLocalID() != null) {
				if (find.getLocalID().trim().length() != 0) {
					if (firstWhere)
						firstWhere = false;
					else
						whereAnd = " AND ";
					if (find.getPatientIDOperator() != null && find.getPatientIDOperator().equalsIgnoreCase("CT")) { // contains
						sbuf.append(whereAnd + "( p.local_id  like '%" + find.getLocalID().trim() + "%')");
					} else if (find.getPatientIDOperator() != null && find.getPatientIDOperator().equalsIgnoreCase("IN")) { // in
						sbuf.append(whereAnd + "( p.local_id  in (" + find.getLocalID().trim() + "))");
					} else {
						sbuf.append(whereAnd + " p.local_id = " + "'"
								+ find.getLocalID().trim() + "' ");
					}
				}
			}
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return sbuf.toString();
	}

	/*
	 * private String buildPersonSearchWhereClause(PatientSearchVO find) { //
	 * nclogger.info("--" + new java.util.Date().getTime() +
	 * "--DAO:buildPersonSearchWhereClause : START"); StringBuffer strbResult =
	 * new StringBuffer("");
	 *
	 * String and = " "; boolean firstWhere = true;
	 *
	 * if ((find.getBirthTime() != null) && (find.getBirthTime().trim().length()
	 * != 0)) { if (firstWhere) firstWhere = false; else and = " AND ";
	 *
	 * if (propertyUtil.getDatabaseServerType() != null &&
	 * propertyUtil.getDatabaseServerType
	 * ().equalsIgnoreCase(NEDSSConstants.ORACLE_ID)) { strbResult.append(and +
	 * " p.birth_time  " + find.getBirthTimeOperator() + " TO_DATE('" +
	 * find.getBirthTime() + "' , 'mm/dd/yyyy')"); } else {
	 * strbResult.append(and + " p.birth_time  " + find.getBirthTimeOperator() +
	 * " CONVERT(datetime,'" + find.getBirthTime() + "')"); } }
	 *
	 * if ((find.getCurrentSex() != null) &&
	 * (find.getCurrentSex().trim().length() != 0)) { if (firstWhere) firstWhere
	 * = false; else and = " AND "; strbResult.append(and + " p.curr_sex_cd = "
	 * + "'" + find.getCurrentSex().trim() + "' "); }
	 *
	 * if ((find.getLocalID() != null) && (find.getLocalID().trim().length() !=
	 * 0)) { if (firstWhere) firstWhere = false; else and = " AND ";
	 * strbResult.append(and + " p.local_id = " + "'" + find.getLocalID().trim()
	 * + "' "); } // nclogger.info("--" + new java.util.Date().getTime() +
	 * "--DAO:buildPersonSearchWhereClause : END");
	 *
	 * return strbResult.toString(); }
	 */

	private String buildIdsNestedQuery(PatientSearchVO find,
			String nestedQuery, String wqDerivedTableName) {
		StringBuffer query = new StringBuffer("");

		query.append(buildNestedQuerySelect(wqDerivedTableName));

		query.append(" from entity_id ei, ( ");
		query.append(nestedQuery);
		query.append(" ) ");
		query.append(wqDerivedTableName);

		query.append(" where ");
		query.append(wqDerivedTableName);
		query.append(".\"personUid\" = ");
		query.append("ei.entity_uid and ");
		query.append(buildEntityIdSearchWhereClause(find));
		query.append(" and ei.status_cd='A' ");

		return query.toString();
	}

	private String buildEntityIdSearchWhereClause(PatientSearchVO find) {
		// nclogger.info("--" + new java.util.Date().getTime() +
		// "--DAO:buildEntityIdSearchWhereClause : START");
		StringBuffer strbResult = new StringBuffer("");

		try{
			String and = " ";
			boolean firstWhere = true;
			String oper = "=";
			String specialCharacter = "'";
	
			if ((find.getRootExtensionTxt() != null && find.getTypeCd() != null)
					&& (find.getTypeCd().trim().length() != 0)) {
				// There is no operator for ID and value; take default
				String rootExtension = find.getRootExtensionTxt().trim();
	
				if (rootExtension.indexOf("'") > 0) {
					rootExtension = replaceCharacters(rootExtension,
							specialCharacter, "''");
				}
	
				strbResult.append("AND (EI.TYPE_CD = '" + find.getTypeCd().trim()
						+ "') ");
				strbResult.append("AND  EI.root_extension_txt like '%"
						+ rootExtension + "%' ");
				
			}
			
			if ((find.getRootExtensionTxt()) != null && (find.getTypeCd() == null)) {
				// There is no operator for ID and value; take default
				String idValueNumber = find.getRootExtensionTxt().trim();
	
				if (idValueNumber.indexOf("'") > 0) {
					idValueNumber = replaceCharacters(idValueNumber,
							specialCharacter, "''");
				}
	
				strbResult.append("AND  (EI.root_extension_txt like '%"
						+ idValueNumber + "%' ");
				strbResult.append("OR  p.ehars_id like '%"
						+ idValueNumber + "%') ");
			}
	
			if ((find.getSsn() != null) && (find.getSsn().trim().length() != 0)) {
				// There is no operator for ID and value; take default
				String entityId = find.getSsn().trim();
	
				if (entityId.indexOf("'") > 0) {
					entityId = replaceCharacters(entityId, specialCharacter, "''");
				}
	
				strbResult.append("AND (E2.TYPE_CD = 'SS')");
				strbResult.append("AND  E2.root_extension_txt like '%"
						+ entityId.toUpperCase() + "%' ");
			}
			// nclogger.info("--" + new java.util.Date().getTime() +
			// "--DAO:buildEntityIdSearchWhereClause : END");
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return strbResult.toString();
	}

	private String buildRaceNestedQuery(PatientSearchVO find,
			String nestedQuery, String wqDerivedTableName) {
		StringBuffer query = new StringBuffer("");

		query.append(buildNestedQuerySelect(wqDerivedTableName));

		query.append(" from person_race pr with (nolock), ( ");
		query.append(nestedQuery);
		query.append(" ) ");
		query.append(wqDerivedTableName);

		query.append(" where ");
		query.append(wqDerivedTableName);
		query.append(".\"personUid\" = ");
		query.append("pr.person_uid and ");
		query.append("pr.race_category_cd = '" + find.getRaceCd() + "'");
		query.append(" and pr.record_status_cd='ACTIVE' ");

		return query.toString();
	}

	
	private String buildEthnicityNestedQuery(PatientSearchVO find,
			String nestedQuery, String wqDerivedTableName) {
		StringBuffer query = new StringBuffer("");

		query.append(buildNestedQuerySelect(wqDerivedTableName));

		query.append(" from person p with (nolock) , ( ");
		query.append(nestedQuery);
		query.append(" ) ");
		query.append(wqDerivedTableName);

		query.append(" where ");
		query.append(wqDerivedTableName);
		query.append(".\"personUid\" = ");
		query.append("p.person_uid and p.person_uid = p.person_parent_uid and ");
		query.append("p.ethnic_group_ind = " + "'"
				+ find.getEthnicGroupInd().trim() + "' ");

		return query.toString();
	}

	private String buildNameNestedQuery(PatientSearchVO find,
			String nestedQuery, String wqDerivedTableName) {
		StringBuffer query = new StringBuffer("");

		query.append(buildNestedQuerySelect(wqDerivedTableName));

		query.append(" from person_name pn with (nolock), ( ");
		query.append(nestedQuery);
		query.append(" ) ");
		query.append(wqDerivedTableName);

		query.append(" where ");
		query.append(wqDerivedTableName);
		query.append(".\"personUid\" = ");
		query.append("pn.person_uid and ");
		query.append(buildNameSearchWhereClause(find));
		query.append(" and pn.record_status_cd='ACTIVE' ");

		return query.toString();
	}

	private String buildInvestigationNestedQuery(PatientSearchVO find,
			String nestedQuery, String wqDerivedTableName) {
		StringBuffer query = new StringBuffer("");

		query.append(buildNestedQuerySelect(wqDerivedTableName));

		query.append(" from public_health_case phc with (nolock), participation par with (nolock) , ( ");
		query.append(nestedQuery);
		query.append(" ) ");
		query.append(wqDerivedTableName);

		query.append(" where ");
		query.append(wqDerivedTableName);
		query.append(".\"personUid\" = ");
		query.append("par.subject_entity_uid and ");
		query.append(" phc.local_id='");
		query.append(find.getActId());
		query.append("' and	par.act_uid = phc.public_health_case_uid and ");
		query.append("par.type_cd = 'SubjOfPHC' and phc.record_status_cd = 'OPEN' ");

		return query.toString();
	}

	private String buildTreatmentNestedQuery(PatientSearchVO find,
			String nestedQuery, String wqDerivedTableName) {
		StringBuffer query = new StringBuffer("");

		query.append(buildNestedQuerySelect(wqDerivedTableName));

		query.append(" from treatment t with (nolock) , participation par with (nolock), ( ");
		query.append(nestedQuery);
		query.append(" ) ");
		query.append(wqDerivedTableName);

		query.append(" where ");
		query.append(wqDerivedTableName);
		query.append(".\"personUid\" = ");
		query.append("par.subject_entity_uid and ");
		query.append(" t.local_id='");
		query.append(find.getActId());
		query.append("' and	par.act_uid = t.treatment_uid and ");
		query.append("par.type_cd = 'SubjOfTrmt'");
		query.append(" and t.record_status_cd = 'ACTIVE' ");

		return query.toString();
	}

	private String buildMorbReportNestedQuery(PatientSearchVO find,
			String nestedQuery, String wqDerivedTableName) {
		StringBuffer query = new StringBuffer("");

		query.append(buildNestedQuerySelect(wqDerivedTableName));

		query.append(" from observation obs with (nolock), participation par with (nolock), ( ");
		query.append(nestedQuery);
		query.append(" ) ");
		query.append(wqDerivedTableName);

		query.append(" where ");
		query.append(wqDerivedTableName);
		query.append(".\"personUid\" = ");
		query.append("par.subject_entity_uid and ");
		query.append(" obs.local_id='");
		query.append(find.getActId());
		query.append("' and	par.act_uid = obs.observation_uid and ");
		query.append("par.type_cd = 'SubjOfMorbReport' and obs.record_status_cd in ('PROCESSED', 'UNPROCESSED') ");

		return query.toString();
	}

	private String buildLabReportNestedQuery(PatientSearchVO find,
			String nestedQuery, String wqDerivedTableName) {
		StringBuffer query = new StringBuffer("");

		query.append(buildNestedQuerySelect(wqDerivedTableName));

		query.append(" from observation obs with (nolock) , participation par with (nolock), ( ");
		query.append(nestedQuery);
		query.append(" ) ");
		query.append(wqDerivedTableName);

		query.append(" where ");
		query.append(wqDerivedTableName);
		query.append(".\"personUid\" = ");
		query.append("par.subject_entity_uid and ");
		query.append(" obs.local_id='");
		query.append(find.getActId());
		query.append("' and	par.act_uid = obs.observation_uid and ");
		query.append("par.type_cd = 'PATSBJ' and obs.record_status_cd in ('PROCESSED', 'UNPROCESSED') ");

		return query.toString();
	}

	private String buildVaccinationNestedQuery(PatientSearchVO find,
			String nestedQuery, String wqDerivedTableName) {
		StringBuffer query = new StringBuffer("");

		query.append(buildNestedQuerySelect(wqDerivedTableName));

		query.append(" from intervention inter with (nolock), participation par with (nolock), ( ");
		query.append(nestedQuery);
		query.append(" ) ");
		query.append(wqDerivedTableName);

		query.append(" where ");
		query.append(wqDerivedTableName);
		query.append(".\"personUid\" = ");
		query.append("par.subject_entity_uid and ");
		query.append(" inter.local_id='");
		query.append(find.getActId());
		query.append("' and	par.act_uid = inter.intervention_uid and ");
		query.append("par.type_cd = 'SubOfVacc'");
		query.append(" and inter.record_status_cd='ACTIVE' ");

		return query.toString();
	}
	
	private String buildNotificationNestedQuery(PatientSearchVO find,
			String nestedQuery, String wqDerivedTableName) {
		StringBuffer query = new StringBuffer("");
		
		return query.toString();
	}

	private String buildAbcCaseNestedQuery(PatientSearchVO find,
			String nestedQuery, String wqDerivedTableName) {
		StringBuffer query = new StringBuffer("");

		query.append(buildNestedQuerySelect(wqDerivedTableName));

		query.append(" from public_health_case phc with (nolock) , participation par with (nolock) , act_id actid with (nolock) , ( ");
		query.append(nestedQuery);
		query.append(" ) ");
		query.append(wqDerivedTableName);

		query.append(" where ");
		query.append(wqDerivedTableName);
		query.append(".\"personUid\" = ");
		query.append("par.subject_entity_uid and ");
		query.append("actid.root_extension_txt = '");
		query.append(find.getActId());
		query.append("' and	actid.act_id_seq = '2' and ");
		query.append(" actid.act_uid = phc.public_health_case_uid ");
		query.append(" and	par.act_uid = phc.public_health_case_uid and ");
		query.append("par.type_cd = 'SubjOfPHC' and phc.record_status_cd = 'OPEN' ");

		return query.toString();
	}

	private String buildStateCaseNestedQuery(PatientSearchVO find,
			String nestedQuery, String wqDerivedTableName) {
		StringBuffer query = new StringBuffer("");

		query.append(buildNestedQuerySelect(wqDerivedTableName));

		query.append(" from public_health_case phc with (nolock), participation par with (nolock), act_id actid with (nolock), ( ");
		query.append(nestedQuery);
		query.append(" ) ");
		query.append(wqDerivedTableName);

		query.append(" where ");
		query.append(wqDerivedTableName);
		query.append(".\"personUid\" = ");
		query.append("par.subject_entity_uid and ");
		query.append("actid.root_extension_txt = '");
		query.append(find.getActId());
		query.append("' and	actid.act_id_seq = '1' and ");
		query.append(" actid.act_uid = phc.public_health_case_uid ");
		query.append(" and	par.act_uid = phc.public_health_case_uid and ");
		query.append("par.type_cd = 'SubjOfPHC' and phc.record_status_cd = 'OPEN' ");

		return query.toString();
	}

	private String buildRVCTStateNestedQuery(PatientSearchVO find,
			String nestedQuery, String wqDerivedTableName) {
		StringBuffer query = new StringBuffer("");

		query.append(buildNestedQuerySelect(wqDerivedTableName));

		query.append(" from public_health_case phc with (nolock) , participation par with (nolock) , act_id actid with (nolock), ( ");
		query.append(nestedQuery);
		query.append(" ) ");
		query.append(wqDerivedTableName);

		query.append(" where ");
		query.append(wqDerivedTableName);
		query.append(".\"personUid\" = ");
		query.append("par.subject_entity_uid and ");
		query.append("actid.root_extension_txt = '");
		query.append(find.getActId());
		query.append("' and	actid.act_id_seq = '1' and ");
		query.append(" actid.act_uid = phc.public_health_case_uid ");
		query.append(" and	par.act_uid = phc.public_health_case_uid and ");
		query.append("par.type_cd = 'SubjOfPHC' and phc.record_status_cd = 'OPEN' and phc.cd='10220'");

		return query.toString();
	}

	private String buildCityCountyNestedQuery(PatientSearchVO find,
			String nestedQuery, String wqDerivedTableName) {
		StringBuffer query = new StringBuffer("");

		query.append(buildNestedQuerySelect(wqDerivedTableName));

		query.append(" from public_health_case phc with (nolock) , participation par with (nolock) , act_id actid with (nolock), ( ");
		query.append(nestedQuery);
		query.append(" ) ");
		query.append(wqDerivedTableName);

		query.append(" where ");
		query.append(wqDerivedTableName);
		query.append(".\"personUid\" = ");
		query.append("par.subject_entity_uid and ");
		query.append("actid.root_extension_txt = '");
		query.append(find.getActId());
		query.append("' and	actid.act_id_seq = '2' and ");
		query.append(" actid.act_uid = phc.public_health_case_uid ");
		query.append(" and	par.act_uid = phc.public_health_case_uid and ");
		// query.append("par.type_cd = 'SubjOfPHC' and phc.record_status_cd = 'OPEN' and phc.cd='10220'");
		query.append("par.type_cd = 'SubjOfPHC' and phc.record_status_cd = 'OPEN' ");

		return query.toString();
	}

	// changes made for event search based on accession number
	private String buildAccessionNumberNestedQuery(PatientSearchVO find,
			String nestedQuery, String wqDerivedTableName) {
		StringBuffer query = new StringBuffer("");

		query.append(buildNestedQuerySelect(wqDerivedTableName));

		query.append(" from observation obs, participation par with (nolock), act_id actid with (nolock), ( ");
		query.append(nestedQuery);
		query.append(" ) ");
		query.append(wqDerivedTableName);

		query.append(" where ");
		query.append(wqDerivedTableName);
		query.append(".\"personUid\" = ");
		query.append("par.subject_entity_uid and ");
		query.append("where actid.root_extension_txt ='");
		query.append(find.getActId());
		query.append("' and actid.type_cd = 'FN' and actid.type_desc_txt ='Filler Number' and actid.record_status_cd ='ACTIVE' and ");
		query.append("actid.act_uid = obs.observation_uid and ");
		query.append("par.act_uid = obs.observation_uid and ");
		query.append(" p.cd='PAT' ");

		return query.toString();

	}

	// changes made for event search based on doc number
	private String buildDocNumberNestedQuery(PatientSearchVO find,
			String nestedQuery, String wqDerivedTableName) {
		StringBuffer query = new StringBuffer("");

		query.append(buildNestedQuerySelect(wqDerivedTableName));

		query.append(" from nbs_document doc with (nolock) , participation par with (nolock), ( ");
		query.append(nestedQuery);
		query.append(" ) ");
		query.append(wqDerivedTableName);

		query.append(" where ");
		query.append(wqDerivedTableName);
		query.append(".\"personUid\" = ");
		query.append("par.subject_entity_uid and ");
		query.append(" phc.local_id='");
		query.append(find.getActId());
		query.append("' and	par.act_uid = doc.nbs_document_uid and ");
		query.append("par.type_cd = 'SubjOfDoc' and phc.record_status_cd = 'OPEN' ");

		return query.toString();
	}

	private String buildNameSearchWhereClause(PatientSearchVO find) {
		// nclogger.info("--" + new java.util.Date().getTime() +
		// "--DAO:buildNameSearchWhereClause : START");
		StringBuffer strbResult = new StringBuffer("");
		try{
			String and = " ";
			boolean firstWhere = true;
			String oper = null;
	
			if ((find.getLastName() != null)
					&& (find.getLastName().trim().length() != 0)) {
				oper = find.getLastNameOperator().trim();
				if (oper.length() != 0)
					if (firstWhere)
						firstWhere = false;
					else
						and = " AND ";
	
				String personLastName = find.getLastName().trim();
				String specialCharacter;
	
				if (personLastName.indexOf("'") > 0) {
					specialCharacter = "'";
					personLastName = replaceCharacters(personLastName,
							specialCharacter, "''");
				}
	
				if (oper.equalsIgnoreCase("SL")) // sounds like
				{
					strbResult.append(and
							+ " (soundex( PN.last_nm)  =  soundex('"
							+ personLastName + "'))");
				} else if (oper.equalsIgnoreCase("CT"))// contains
				{
					strbResult.append(and + "(  PN.last_nm  like '%"
							+ personLastName + "%')");
				} else if (oper.equalsIgnoreCase("SW"))// Starts With
				{
					strbResult.append(and + "(  PN.last_nm  like '"
							+ personLastName + "%')");
				} else {
					strbResult.append(and + " ( PN.last_nm " + oper + "   '"
							+ personLastName + "')");
				}
			}
	
			if ((find.getFirstName() != null)
					&& (find.getFirstName().trim().length() != 0)) {
				oper = find.getFirstNameOperator().trim();
				if (oper.length() != 0)
					if (firstWhere)
						firstWhere = false;
					else
						and = " AND ";
	
				String personFirstName = find.getFirstName().trim();
				String specialCharacter;
	
				if (personFirstName.indexOf("'") > 0) {
					specialCharacter = "'";
					personFirstName = replaceCharacters(personFirstName,
							specialCharacter, "''");
				}
	
				if (oper.equalsIgnoreCase("SL")) // sounds like
				{
					strbResult.append(and
							+ " (soundex(PN.first_nm)  =  soundex('"
							+ personFirstName + "'))");
				} else if (oper.equalsIgnoreCase("CT")) // contains
				{
					strbResult.append(and + "( PN.first_nm  like '%"
							+ personFirstName + "%')");
				} else if (oper.equalsIgnoreCase("SW")) // Starts With
				{
					strbResult.append(and + "(  PN.first_nm  like '"
							+ personFirstName + "%')");
				} else {
					strbResult.append(and + " ( PN.first_nm " + oper
							+ "   '" + personFirstName + "')");
				}
			}
			// nclogger.info("--" + new java.util.Date().getTime() +
			// "--DAO:buildNameSearchWhereClause : END");
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return strbResult.toString();
	}

	/**
	 * buildInvestigationCriteriaSearchWhereClause():
	 * Condition, program area, jurisdiction
	 * @param find
	 * @return
	 */
	private String buildInvestigationCriteriaSearchWhereClause(PatientSearchVO find) {
		// nclogger.info("--" + new java.util.Date().getTime() +
		// "--DAO:buildNameSearchWhereClause : START");
		StringBuffer strbResult = new StringBuffer("");
		try{
			String and = " ";
			boolean firstWhere = true;
			String oper = null;

			//Condition
			if ((find.getConditionSelected() != null) && (find.getConditionSelected().length != 0)) {
				oper = "=";
				if (oper.length() != 0)
					if (firstWhere)
						firstWhere = false;
					else
						and = " AND ";

				String[] condition = find.getConditionSelected();

				strbResult.append(and);

				String result = ("" + Arrays.asList(condition)).replaceAll("(^.|.$)", "\'").replace(", ", "\',\'");

				strbResult.append(" Public_health_case.cd  in (" + result + ")");

			}
			//Program Area
			if ((find.getProgramArea() != null) && (find.getProgramArea().length != 0)) {
				oper = "=";
				if (oper.length() != 0)
					if (firstWhere)
						firstWhere = false;
					else
						and = " AND ";

				String[] programArea = find.getProgramArea();
				strbResult.append(and);

				String result = ("" + Arrays.asList(programArea)).replaceAll("(^.|.$)", "\'").replace(", ", "\',\'");

				strbResult.append(" Public_health_case.prog_area_cd  in (" + result + ")");
			}
			
			
		
			
			//Jurisdiction
			if ((find.getJurisdictionSelected() != null)
					&& (find.getJurisdictionSelected().length != 0)) {
				oper = "=";
				if (oper.length() != 0)
					if (firstWhere)
						firstWhere = false;
					else
						and = " AND ";
		
				String[] jurisdiction = find.getJurisdictionSelected();
				strbResult.append(and);

				String result = ("" + Arrays.asList(jurisdiction)).replaceAll("(^.|.$)", "\'").replace(", ", "\',\'");

				strbResult.append(" Public_health_case.jurisdiction_cd  in (" + result + ")");
			
			}
			
			//Investigation Status
			if ((find.getInvestigationStatusSelected() != null)
					&& (find.getInvestigationStatusSelected().trim().length() != 0)) {
				oper = "=";
				if (oper.length() != 0)
					if (firstWhere)
						firstWhere = false;
					else
						and = " AND ";
		
				String investigationStatus = find.getInvestigationStatusSelected().trim();
				String specialCharacter;
	
				if (investigationStatus.indexOf("'") > 0) {
					specialCharacter = "'";
					investigationStatus = replaceCharacters(investigationStatus,
							specialCharacter, "''");
				}
					strbResult.append(and + " ( Public_health_case.investigation_status_cd " + oper
							+ "   '" + investigationStatus + "')");
				
			}
			
			
			//Pregnancy Status
			if ((find.getPregnantSelected() != null)
					&& (find.getPregnantSelected().trim().length() != 0)) {
				oper = "=";
				if (oper.length() != 0)
					if (firstWhere)
						firstWhere = false;
					else
						and = " AND ";
		
				String pregnant = find.getPregnantSelected().trim();
				String specialCharacter;
	
				if (pregnant.indexOf("'") > 0) {
					specialCharacter = "'";
					pregnant = replaceCharacters(pregnant,
							specialCharacter, "''");
				}
					strbResult.append(and + " ( Public_health_case.pregnant_ind_cd " + oper
							+ "   '" + pregnant + "')");
				
			}
			//Investigator
			
			if(find.getInvestigatorSelected()!=null && !find.getInvestigatorSelected().isEmpty()){
				
				oper = "=";
				if (oper.length() != 0)
					if (firstWhere)
						firstWhere = false;
					else
						and = " AND ";
		
				String investigator = find.getInvestigatorSelected().trim();
				String specialCharacter;
	
				if (investigator.indexOf("'") > 0) {
					specialCharacter = "'";
					investigator = replaceCharacters(investigator,
							specialCharacter, "''");
				}
					strbResult.append(and + " (person.person_uid " + oper
							+ "   '" + investigator + "')");
					
				
			}
					
					
			//Notification status
			if (((find.getNotificationCodedValuesSelected() != null)
					&& (find.getNotificationCodedValuesSelected().trim().length() != 0))
					|| find.getNotificationValuesSelected().contains("UNASSIGNED")) {
				oper = "=";
				if (oper.length() != 0)
					if (firstWhere)
						firstWhere = false;
					else
						and = " AND ";
	
				String notification = find.getNotificationCodedValuesSelected().trim();
				String specialCharacter;
	
				if(!((find.getNotificationCodedValuesSelected() != null)
						&& (find.getNotificationCodedValuesSelected().trim().length() != 0))
						&& find.getNotificationValuesSelected().contains("UNASSIGNED"))
					notification+="UNASSIGNED";
				else
					if(find.getNotificationValuesSelected().contains("UNASSIGNED"))
						notification+=",UNASSIGNED";
				
				String[] notificationArray = notification.split(",");
				
				if(notificationArray.length>0)
					strbResult.append(and);
				
		
				//CachedDropDownValues cdv = new CachedDropDownValues();
				
				//TreeMap<?, ?> notificationCodes = cdv.getCodedValuesAsTreeMap("REC_STAT_NOT_UI");
				
				for(int i=0; i<notificationArray.length; i++){
					
					String notif = notificationArray[i];
					
					//if(!notif.equalsIgnoreCase("UNASSIGNED"))
					//	notif=(String)notificationCodes.get(notif);
					
					if (notif.indexOf("'") > 0) {
						specialCharacter = "'";
						notif = replaceCharacters(notif,
								specialCharacter, "''");
					}
		
					if(i==0){
						if(notif.equalsIgnoreCase("UNASSIGNED"))
							strbResult.append( "( ( Notification.record_status_cd  is NULL) or (Notification.record_status_cd = '')");
						
						else
							strbResult.append( "( (Notification.record_status_cd " + oper + "   '"
								+ notif + "')");
					}
					else{
						if(notif.equalsIgnoreCase("UNASSIGNED"))
							strbResult.append( "OR (Notification.record_status_cd  is NULL or (Notification.record_status_cd = ''))");
						else
						strbResult.append( "OR  ( Notification.record_status_cd " + oper + "   '"
								+ notif + "')");
					}
						
						
						
				}
				
				if(notificationArray.length>0)
					strbResult.append(")");
				
			}
			//Notification status
			
			/*if(find.getNotificationStatusSelected()!=null && !find.getNotificationStatusSelected().equals("")){
				
				oper = "=";
				if (oper.length() != 0)
					if (firstWhere)
						firstWhere = false;
					else
						and = " AND ";
		
				String investigationStatus = find.getInvestigationStatusSelected().trim();
				String specialCharacter;
	
				if (investigationStatus.indexOf("'") > 0) {
					specialCharacter = "'";
					investigationStatus = replaceCharacters(investigationStatus,
							specialCharacter, "''");
				}
					strbResult.append(and + " (upper( Notification.record_status_cd ) " + oper
							+ "   '" + investigationStatus.toUpperCase() + "')");
					
			}
			
			*/
			//Event Status
			if ((find.getEventStatusInitialSelected() != null)
					|| (find.getEventStatusUpdateSelected() != null)) {
				oper = "=";
				if (oper.length() != 0)
					if (firstWhere)
						firstWhere = false;
					else
						and = " AND ";
		
				String newInitial = find.getEventStatusInitialSelected().trim();
				String specialCharacter;
	
				if (newInitial.indexOf("'") > 0) {
					specialCharacter = "'";
					newInitial = replaceCharacters(newInitial,
							specialCharacter, "''");
				}
				
				String update = find.getEventStatusUpdateSelected().trim();

				if (update.indexOf("'") > 0) {
					specialCharacter = "'";
					update = replaceCharacters(update,
							specialCharacter, "''");
				}
				
				
				if (newInitial.equalsIgnoreCase("true") && update.equalsIgnoreCase("true"))
					strbResult.append(and + " public_health_case.version_ctrl_nb) >='1'");
				else
					if (newInitial.equalsIgnoreCase("true"))
						strbResult.append(and + " public_health_case.version_ctrl_nb) = '1'");
					else
						if (update.equalsIgnoreCase("true"))
							strbResult.append(and + " public_health_case.version_ctrl_nbr >'1'");
			}
			
			
			//Outbreak Name
			if ((find.getOutbreakNameSelected() != null) && (find.getOutbreakNameSelected().length != 0)) {
				oper = "=";
				if (oper.length() != 0)
					if (firstWhere)
						firstWhere = false;
					else
						and = " AND ";

				String[] outbreakName = find.getOutbreakNameSelected();

				strbResult.append(and);

				String result = ("" + Arrays.asList(outbreakName)).replaceAll("(^.|.$)", "\'").replace(", ", "\',\'");

				strbResult.append(" Public_health_case.OUTBREAK_NAME  in (" + result + ")");

			}
			//Case status
			if (((find.getCaseStatusCodedValuesSelected() != null)
					&& (find.getCaseStatusCodedValuesSelected().trim().length() != 0)
					) || find.getCaseStatusListValuesSelected().contains("UNASSIGNED")
					) {
				oper = "=";
				if (oper.length() != 0)
					if (firstWhere)
						firstWhere = false;
					else
						and = " AND ";
		
				String caseStatus = find.getCaseStatusCodedValuesSelected().trim();
				
				if(find.getCaseStatusListValuesSelected().contains("UNASSIGNED") && 
						!((find.getCaseStatusCodedValuesSelected() != null)
								&& (find.getCaseStatusCodedValuesSelected().trim().length() != 0)
								))
					caseStatus+="UNASSIGNED";
				else
					if(find.getCaseStatusListValuesSelected().contains("UNASSIGNED"))
						caseStatus+=",UNASSIGNED";
				String[] caseStatusArray = caseStatus.split(",");
				
				//CachedDropDownValues cdv = new CachedDropDownValues();
				//TreeMap<?, ?> caseStatusCodes = cdv.getCodedValuesAsTreeMap("PHC_CLASS");
				if(caseStatusArray.length>0)
					strbResult.append(and);
				
				
				for(int i=0; i<caseStatusArray.length; i++){
					
					String caseStatusValue = caseStatusArray[i];
					
					//if(!caseStatusValue.equalsIgnoreCase("UNASSIGNED"))
					//	caseStatusValue=(String)caseStatusCodes.get(caseStatusValue);
					String specialCharacter;
					if (caseStatusValue.indexOf("'") > 0) {
						specialCharacter = "'";
						caseStatusValue = replaceCharacters(caseStatusValue,
								specialCharacter, "''");
					}
		
					if(i==0){
						if(caseStatusValue.equalsIgnoreCase("UNASSIGNED"))
							strbResult.append( "( (Public_health_case.CASE_CLASS_CD  is NULL) or (Notification.record_status_cd = '')");
						
						else
							strbResult.append( "( ( Public_health_case.CASE_CLASS_CD " + oper + "   '"
								+ caseStatusValue + "')");
					}
					else{
						if(caseStatusValue.equalsIgnoreCase("UNASSIGNED"))
							strbResult.append( "OR (Public_health_case.CASE_CLASS_CD  is NULL or (Notification.record_status_cd = ''))");
						else
						strbResult.append( "OR  ( Public_health_case.CASE_CLASS_CD " + oper + "   '"
								+ caseStatusValue + "')");
					}
					
				}
				
				if(caseStatusArray.length>0)
					strbResult.append(")");
				
			}
			//Current Processing Status
			if (((find.getCurrentProcessCodedValuesSelected() != null)
					&& (find.getCurrentProcessCodedValuesSelected().trim().length() != 0))
					|| find.getCurrentProcessStateValuesSelected().contains("UNASSIGNED")) {
				oper = "=";
				if (oper.length() != 0)
					if (firstWhere)
						firstWhere = false;
					else
						and = " AND ";
				
				
				String currentProcessStatus = find.getCurrentProcessCodedValuesSelected().trim();
				
				if ((!(find.getCurrentProcessCodedValuesSelected() != null)
						&& (find.getCurrentProcessCodedValuesSelected().trim().length() != 0))
						&& find.getCurrentProcessStateValuesSelected().contains("UNASSIGNED"))
					currentProcessStatus+=",UNASSIGNED";
				else
				if(find.getCurrentProcessStateValuesSelected().contains("UNASSIGNED"))
					currentProcessStatus+=",UNASSIGNED";
				String[] currentProcessStatusArray = currentProcessStatus.split(",");
				
				//CachedDropDownValues cdv = new CachedDropDownValues();
				//TreeMap<?, ?> currentProcessStatusCodes = cdv.getCodedValuesAsTreeMap("CM_PROCESS_STAGE");
				if(currentProcessStatusArray.length>0)
					strbResult.append(and);
				
				
				for(int i=0; i<currentProcessStatusArray.length; i++){
					
					String currentProcessStatusValue = currentProcessStatusArray[i];
					
					//if(!currentProcessStatusValue.equalsIgnoreCase("UNASSIGNED"))
					//	currentProcessStatusValue=(String)currentProcessStatusCodes.get(currentProcessStatusValue);
					String specialCharacter;
					if (currentProcessStatusValue.indexOf("'") > 0) {
						specialCharacter = "'";
						currentProcessStatusValue = replaceCharacters(currentProcessStatusValue,
								specialCharacter, "''");
					}
		
					if(i==0){
						if(currentProcessStatusValue.equalsIgnoreCase("UNASSIGNED"))
							strbResult.append( "( ( Public_health_case.CURR_PROCESS_STATE_CD  is NULL) or (Notification.record_status_cd = '')");
						
						else
							strbResult.append( "( ( Public_health_case.CURR_PROCESS_STATE_CD " + oper + "   '"
								+ currentProcessStatusValue + "')");
					}
					else{
						if(currentProcessStatusValue.equalsIgnoreCase("UNASSIGNED"))
							strbResult.append( "OR ( Public_health_case.CURR_PROCESS_STATE_CD  is NULL or (Notification.record_status_cd = ''))");
						else
						strbResult.append( "OR  ( Public_health_case.CURR_PROCESS_STATE_CD " + oper + "   '"
								+ currentProcessStatusValue + "')");
					}
					
				}
				
				if(currentProcessStatusArray.length>0)
					strbResult.append(")");
				
			}
			
			
			
			// nclogger.info("--" + new java.util.Date().getTime() +
			// "--DAO:buildNameSearchWhereClause : END");
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return strbResult.toString();
	}

	
		
	/**
	 * This method wraps nestedQuery into a query, which will retrieve all MPR
	 * and revision person_uid, which correpond to the collection of
	 * person_parent_uid from the nestedQuery. This is needed so that on every
	 * step of the search query, we have an updated list of all person_uid
	 * necessary for the next query (this includes MPRs and revisions).
	 *
	 * @param nestedQuery
	 *            Represents the query defining derived table. Has to contains
	 *            the following column names in SELECT clause:
	 *            "personParentUid", "parentLastName", "parentFirstName",
	 *            "parentBrithTimeCalc"
	 * @param nqDerivedTableName
	 *            Represents a table name for the derived table defined by the
	 *            nestedQuery.
	 * @return SQL select query, which retrieves all MPR and revision person_uid
	 *         that correspond to the person_parent_uid defined by the
	 *         nestedQuery.
	 */
	private String wrapNestedQuery(String nestedQuery, String nqDerivedTableName) {
		StringBuffer query = new StringBuffer("");
		query.append(" select p.person_uid \"personUid\", ");
		query.append(nqDerivedTableName);
		query.append(".\"personParentUid\" ");
		query.append(" from person p with (nolock) , ");
		query.append(" ( ");
		query.append(nestedQuery);
		query.append(" ) ");
		query.append(nqDerivedTableName);
		query.append(" where ");
		query.append(nqDerivedTableName);
		query.append(".\"personParentUid\" = p.person_parent_uid ");
		return query.toString();
	}
	
	
//	private String wrapNestedQueryInvestigation(String nestedQuery, String nqDerivedTableName) {
//		StringBuffer query = new StringBuffer("");
//		query.append(" select distinct ");
//		query.append(nqDerivedTableName);
//		query.append(".\"investigatorLastName\", ");
//		query.append(nqDerivedTableName);
//		query.append(".\"investigatorFirstName\", ");
//		query.append(nqDerivedTableName);
//		query.append(".\"investigatorUid\", ");
//		query.append(nqDerivedTableName);
//		query.append(".\"investigatorLocalId\", ");
//		query.append(nqDerivedTableName);
//		query.append(".\"investigationStatusCd\", ");
//		query.append(nqDerivedTableName);
//		query.append(".\"investigationStatusDescTxt\", ");
//		query.append(nqDerivedTableName);
//		query.append(".\"localId\", ");
//		query.append(nqDerivedTableName);
//		query.append(".\"activityFromTime\", ");
//		query.append(nqDerivedTableName);
//		query.append(".\"cd\", ");
//		query.append(nqDerivedTableName);
//		query.append(".\"conditionCodeText\", ");
//		query.append(nqDerivedTableName);
//		query.append(".\"caseClassCd\", ");
//		query.append(nqDerivedTableName);
//		query.append(".\"addUserId\", ");
//		query.append(nqDerivedTableName);
//		query.append(".\"lastChgUserId\", ");
//		query.append(nqDerivedTableName);
//		query.append(".\"caseClassCodeTxt\", ");
//		query.append(nqDerivedTableName);
//		query.append(".\"jurisdictionCd\", ");
//		query.append(nqDerivedTableName);
//		query.append(".\"patientLastName\", ");
//		query.append(nqDerivedTableName);
//		query.append(".\"patientFirstName\", ");
//		query.append(nqDerivedTableName);
//		query.append(".\"MPRUid\", ");
//		query.append(nqDerivedTableName);
//		query.append(".\"jurisdictionDescTxt\", ");
//		query.append(nqDerivedTableName);
//		query.append(".\"publicHealthCaseUid\", ");
//		query.append(nqDerivedTableName);
//		query.append(".\"statusCd\", ");
//		query.append(nqDerivedTableName);
//		query.append(".\"recordStatusCd\", ");
//		query.append(nqDerivedTableName);
//		query.append(".\"notifRecordStatusCd\", ");
//		query.append(nqDerivedTableName);
//		query.append(".\"notifLocalId\" ");
//		
//	
//	query.append(" from public_health_case phc, ");
//	query.append(" ( ");
//	query.append(nestedQuery);
//	query.append(" ) ");
//	query.append(nqDerivedTableName);
//	query.append(" where ");
//	query.append(nqDerivedTableName);
//	query.append(".\"publicHealthCaseUid\" = phc.public_health_case_uid ");
//	
//	
//	return query.toString();
//}
	private String buildAddressNestedQuery(PatientSearchVO find,
			String nestedQuery, String wqDerivedTableName) {
		StringBuffer query = new StringBuffer("");

		query.append(buildNestedQuerySelect(wqDerivedTableName));

		query.append(" from postal_locator pl with (nolock), entity_locator_participation elp with (nolock), ( ");
		query.append(nestedQuery);
		query.append(" ) ");
		query.append(wqDerivedTableName);

		query.append(" where ");
		query.append(wqDerivedTableName);
		query.append(".\"personUid\" = ");
		query.append("elp.entity_uid and ");
		query.append("elp.locator_uid = pl.postal_locator_uid and ");
		query.append("elp.class_cd = 'PST' and ");
		query.append(buildAddressSearchWhereClause(find));
		query.append(" and elp.status_cd='A' ");

		return query.toString();
	}

	
	private String buildAddressSearchWhereClause(PersonSearchVO find) {
		// nclogger.info("--" + new java.util.Date().getTime() +
		// "--DAO:buildAddressSearchWhereClause : START");
		StringBuffer sbuf = new StringBuffer();
		String oper = null;
		String and = " ";
		boolean firstWhere = true;
		try{
			if ((find.getStreetAddr1() != null)
					&& (find.getStreetAddr1().trim().length() != 0)) {
				oper = find.getStreetAddr1Operator().trim();
				if (oper.length() != 0)
					if (firstWhere)
						firstWhere = false;
					else
						and = " AND ";
	
				String streetAddress = find.getStreetAddr1().trim();
				String specialCharacter;
	
				if (streetAddress.indexOf("'") > 0) {
					specialCharacter = "'";
					streetAddress = replaceCharacters(streetAddress,
							specialCharacter, "''");
				}
	
				if (oper.equalsIgnoreCase("SL"))
					sbuf.append(and
							+ " soundex (PL.street_addr1) = soundex('"
							+ streetAddress + "')");
				else if (oper.equalsIgnoreCase("CT")) // contains
					sbuf.append(and + " PL.street_addr1  like '%"
							+ streetAddress + "%'");
				else
					sbuf.append(and + " PL.street_addr1 "
							+ oper + " '"
							+ streetAddress + "'");
			}
	
			if ((find.getCityDescTxt() != null)
					&& (find.getCityDescTxt().trim().length() != 0)) {
				oper = find.getCityDescTxtOperator().trim();
	
				if (oper.length() != 0)
					if (firstWhere)
						firstWhere = false;
					else
						and = " AND ";
				if (oper.equalsIgnoreCase("SL"))
					sbuf.append(and
							+ " soundex (PL.city_desc_txt) = soundex('"
							+ find.getCityDescTxt().trim() + "')");
				else if (oper.equalsIgnoreCase("CT")) // contains
					sbuf.append(and + "  PL.city_desc_txt  like '%"
							+ find.getCityDescTxt().trim() + "%'");
				else
					sbuf.append(and + " PL.city_desc_txt "
							+ oper + " '"
							+ find.getCityDescTxt().trim() + "'");
	
			}
	
			if ((find.getState() != null) && (find.getState().trim().length() != 0)) {
				if (firstWhere)
					firstWhere = false;
				else
					and = " AND ";
				sbuf.append(and + " PL.state_cd =  '"
						+ find.getState().trim() + "'");
			}
	
			if ((find.getZipCd() != null) && (find.getZipCd().trim().length() != 0)) {
				if (firstWhere)
					firstWhere = false;
				else
					and = " AND ";
				sbuf.append(and + " PL.zip_cd = '"
						+ find.getZipCd().trim() + "'");
			}
			// nclogger.info("--" + new java.util.Date().getTime() +
			// "--DAO:buildAddressSearchWhereClause : END");
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return sbuf.toString();
	}

	private String buildTeleNestedQuery(PatientSearchVO find,
			String nestedQuery, String wqDerivedTableName) {
		StringBuffer query = new StringBuffer("");

		query.append(buildNestedQuerySelect(wqDerivedTableName));

		query.append(" from tele_locator tl with (nolock), entity_locator_participation elp with (nolock), ( ");
		query.append(nestedQuery);
		query.append(" ) ");
		query.append(wqDerivedTableName);

		query.append(" where ");
		query.append(wqDerivedTableName);
		query.append(".\"personUid\" = ");
		query.append("elp.entity_uid and ");
		query.append("elp.locator_uid = tl.tele_locator_uid and ");
		query.append("elp.class_cd = 'TELE' and ");
		query.append(buildTeleSearchWhereClause(find));
		query.append(" and elp.status_cd='A' ");

		return query.toString();
	}

	private String buildTeleSearchWhereClause(PersonSearchVO find) {
		// nclogger.info("--" + new java.util.Date().getTime() +
		// "--DAO:buildTeleSearchWhereClause : START");
		StringBuffer sbuf = new StringBuffer();
		String oper = " = ";
		String and = " ";
		boolean firstWhere = true;
		try{
			if ((find.getPhoneNbrTxt() != null)
					&& (find.getPhoneNbrTxt().trim().length() != 0)) {
				// oper = find.getPhoneNbrTxtOperator().trim();
				if (oper.length() != 0)
					if (firstWhere)
						firstWhere = false;
					else
						and = " AND ";
	
				String phoneNumber = find.getPhoneNbrTxt().trim();
				String formattedPhone = formatPhoneNumber(phoneNumber);
	
				if (formattedPhone.indexOf("-") == 3
						&& formattedPhone.lastIndexOf("-") == 4
						&& formattedPhone.length() == 9) {
					sbuf.append(and + " TL.phone_nbr_txt  like '"
							+ formattedPhone.substring(0, 4) + "%'"
							+ " and TL.phone_nbr_txt  like '%"
							+ formattedPhone.substring(5, 9) + "%'");
				} else if (formattedPhone.indexOf("-") == 3
						&& formattedPhone.lastIndexOf("-") == 3
						&& formattedPhone.length() == 4) {
					sbuf.append(and + " TL.phone_nbr_txt  like '"
							+ formattedPhone.trim() + "%'");
				} else {
					sbuf.append(and + " TL.phone_nbr_txt  like '%"
							+ formattedPhone.trim() + "%'");
				}
	
			}
			
			if ((find.getEmailAddress() != null) && (find.getEmailAddress()
					.trim().length() != 0)) {
				String emailAddress = find.getEmailAddress().trim();
				
				sbuf.append(and + " TL.email_address  like '%"
						+ emailAddress.trim() + "%'");
			}
				
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return sbuf.toString();
	}

	/**
	 * This method buils first subquery for patient search. All first subqueries
	 * are joined with PERSON table unless the first subquery is against PERSON
	 * table itself.
	 *
	 * @param searchDataType
	 *            Indicates the type of data specified by the search criteria.
	 * @param find
	 *            Represents all search criteria specified by the user.
	 * @return Returns the first subquery using the first set of search
	 *         criteria.
	 */
	private String buildFirstNestedQuery(int searchDataType,
			PatientSearchVO find) {
		String result = "";
		try{
			switch (searchDataType) {
			case DT_NAME:
				result = buildNameFirstQuery(find);
				break;
			case DT_ADDRESS:
				result = buildAddressFirstQuery(find);
				break;
			case DT_TELE:
				result = buildTeleFirstQuery(find);
				break;
			case DT_ETHNICITY:
				result = buildEthnicityFirstQuery(find);
				break;
			case DT_RACE:
				result = buildRaceFirstQuery(find);
				break;
			case DT_ID:
				result = buildIdsFirstQuery(find);
				break;
			case DT_PERSON:
				result = buildPersonFirstQuery(find);
				break;
			case DT_ABC_CASE_ID:
				result = buildAbcCaseFirstQuery(find);
				break;
			case DT_STATE_CASE_ID:
				result = buildStateCaseFirstQuery(find);
				break;
			case DT_INVESTIGATION:
				result = buildPublicHealthCaseFirstQuery(find);
				break;
			case DT_LAB_REPORT:
				result = buildLabReportFirstQuery(find);
				break;
			case DT_MORBIDITY_REPORT:
				result = buildMorbReportFirstQuery(find);
				break;
			case DT_VACCINATION:
				result = buildVaccinationFirstQuery(find);
				break;
			case DT_NOTIFICATION:
				result = buildNotificationFirstQuery(find);
				break;
			case DT_TREATMENT:
				result = buildTreatmentFirstQuery(find);
				break;
			// case DT_RVCT_STATE_CASE_NUMBER:
			// result = buildRVCTStateFirstQuery(find);
			// break;
			case DT_CITY_COUNTY_CASE_ID:
				result = buildCityCountyFirstQuery(find);
				break;
			case DT_ACCESSION_NUMBER: // changes made for event search based on
										// accession number
				result = buildAccessionNumberFirstQuery(find);
				break;
			case DT_DOC_NUMBER: // changes made for event search based on doc number
				result = buildDocNumberFirstQuery(find);
				break;
			case DT_EPILINK:
				result = buildEpilinkFirstQuery(find);
				break;
			default:
				// raise exception
			}
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return result;
	}

	
	
	private String BuildEpilinkFirstQuery(PatientSearchVO find){
		String query = SELECT_PERSON + "  Act a WITH(NOLOCK), Entity e WITH(NOLOCK), NBS_act_entity ae WITH(NOLOCK), Public_health_case phc WITH(NOLOCK), Person p WITH(NOLOCK) "
				+ "where   phc.public_health_case_uid = a.act_uid and "
				+ "ae.act_uid = a.act_uid and "
				+ "ae.entity_uid = p.person_uid and "
				+ "phc.public_health_case_uid in "
				+ "(select phc.public_health_case_uid "
					+ "from case_management cm WITH(NOLOCK), Public_heaWITH(NOLOCK) phc WITH(NOLOCK)"
					+ "where cm.epi_link_id in "
						+ "(select  cm.epi_link_id  "
						+ "from case_management cm "
						+ "where cm.public_health_case_uid = " + find.getActId() + ") "
					+ "and cm.public_health_case_uid = phc.public_health_case_uid )";
		
		return query;
	}
	private String buildAbcCaseFirstQuery(PatientSearchVO find) {
		StringBuffer query = new StringBuffer();
		query.append("select distinct p.person_parent_uid \"personParentUid\" ");
		query.append("from person p with (nolock), public_health_case phc with (nolock), participation par with (nolock), act_id actid with (nolock) ");
		query.append("where actid.root_extension_txt ='");
		query.append(find.getActId());
		query.append("' and actid.act_id_seq = '2' and ");
		query.append("actid.act_uid = phc.public_health_case_uid and ");
		query.append("par.act_uid = phc.public_health_case_uid and ");
		query.append(" par.type_cd = 'SubjOfPHC' and par.subject_entity_uid = p.person_uid ");
		query.append(" and p.cd='PAT' and phc.record_status_cd = 'OPEN' ");
		query.append(find.getDataAccessWhereClause());

		return query.toString();
	}

	private String buildRVCTStateFirstQuery(PatientSearchVO find) {
		StringBuffer query = new StringBuffer();
		query.append("select distinct p.person_parent_uid \"personParentUid\" ");
		query.append("from person p with (nolock) , public_health_case phc with (nolock) , participation par with (nolock), act_id actid with (nolock) ");
		query.append("where actid.root_extension_txt ='");
		query.append(find.getActId());
		query.append("' and actid.act_id_seq = '1' and ");
		query.append("actid.act_uid = phc.public_health_case_uid and ");
		query.append("par.act_uid = phc.public_health_case_uid and ");
		query.append(" par.type_cd = 'SubjOfPHC' and par.subject_entity_uid = p.person_uid ");
		query.append(" and p.cd='PAT' and phc.record_status_cd = 'OPEN' and phc.cd='10220'");
		query.append(find.getDataAccessWhereClause());

		return query.toString();
	}
	
	private String buildCityCountyFirstQuery(PatientSearchVO find) {
		StringBuffer query = new StringBuffer();
		query.append("select distinct p.person_parent_uid \"personParentUid\" ");
		query.append("from person p with (nolock), public_health_case phc with (nolock), participation par with (nolock), act_id actid with (nolock) ");
		query.append("where actid.root_extension_txt ='");
		query.append(find.getActId());
		query.append("' and actid.act_id_seq = '2' and ");
		query.append("actid.act_uid = phc.public_health_case_uid and ");
		query.append("par.act_uid = phc.public_health_case_uid and ");
		query.append(" par.type_cd = 'SubjOfPHC' and par.subject_entity_uid = p.person_uid ");
		// query.append(" and p.cd='PAT' and phc.record_status_cd = 'OPEN' and phc.cd='10220'");
		query.append(" and p.cd='PAT' and phc.record_status_cd = 'OPEN' ");
		query.append(find.getDataAccessWhereClause());

		return query.toString();
	}

	private String buildStateCaseFirstQuery(PatientSearchVO find) {
		StringBuffer query = new StringBuffer();
		query.append("select distinct p.person_parent_uid \"personParentUid\" ");
		query.append("from person p with (nolock) , public_health_case phc with (nolock) , participation par with (nolock) , act_id actid with (nolock) ");
		query.append("where actid.root_extension_txt ='");
		query.append(find.getActId());
		query.append("' and actid.act_id_seq = '1' and ");
		query.append("actid.act_uid = phc.public_health_case_uid and ");
		query.append("par.act_uid = phc.public_health_case_uid and ");
		query.append(" par.type_cd = 'SubjOfPHC' and par.subject_entity_uid = p.person_uid and ");
		query.append(" p.cd='PAT' and phc.record_status_cd = 'OPEN'  ");
		query.append(find.getDataAccessWhereClause());

		return query.toString();
	}

	private String buildVaccinationFirstQuery(PatientSearchVO find) {
		StringBuffer query = new StringBuffer();
		query.append("select distinct p.person_parent_uid \"personParentUid\" ");
		query.append("from person p with (nolock) , intervention inter with (nolock), participation par with (nolock) ");
		query.append("where inter.local_id='");
		query.append(find.getActId());
		query.append("' and par.act_uid = inter.intervention_uid and");
		query.append(" par.type_cd = 'SubOfVacc' and par.subject_entity_uid = p.person_uid and ");
		query.append(" p.cd='PAT' and inter.record_status_cd='ACTIVE' ");
		query.append(find.getDataAccessWhereClause());

		return query.toString();
	}
	
	private String buildNotificationFirstQuery(PatientSearchVO find){
		StringBuffer query = new StringBuffer();
		query.append("select distinct p.person_parent_uid \"personParentUid\" ");
		query.append("from person p with (nolock) , notification notif with (nolock) , participation par with (nolock), public_health_Case phc with (nolock), act_relationship ar with (nolock) ");
		query.append("where notif.notification_uid = ar.source_act_uid and phc.public_health_case_uid = ar.target_act_uid and ar.type_cd='Notification' and notif.local_id='");
		query.append(find.getActId());
		query.append("' and par.act_uid = phc.public_health_Case_uid and");
		query.append(" par.type_cd = 'SubjOfPHC' and par.subject_entity_uid = p.person_uid and ");
		query.append(" p.cd='PAT' and notif.record_status_cd !='LOG_DEL' ");
		query.append(find.getDataAccessWhereClause());
		return query.toString();
	}

	private String buildTreatmentFirstQuery(PatientSearchVO find) {
		StringBuffer query = new StringBuffer();
		query.append("select distinct p.person_parent_uid \"personParentUid\" ");
		query.append("from person p with (nolock) , treatment t with (nolock) , participation par with (nolock) ");
		query.append("where t.local_id='");
		query.append(find.getActId());
		query.append("' and par.act_uid = t.treatment_uid and");
		query.append(" par.type_cd = 'SubjOfTrmt' and par.subject_entity_uid = p.person_uid and ");
		query.append(" p.cd='PAT' and t.record_status_cd = 'ACTIVE' ");
		query.append(find.getDataAccessWhereClause());

		return query.toString();
	}

	private String buildMorbReportFirstQuery(PatientSearchVO find) {
		StringBuffer query = new StringBuffer();
		query.append("select distinct p.person_parent_uid \"personParentUid\" ");
		query.append("from person p with (nolock) , observation obs with (nolock) , participation par with (nolock) ");
		query.append("where obs.local_id='");
		query.append(find.getActId());
		query.append("' and par.act_uid = obs.observation_uid and");
		query.append(" par.type_cd = 'SubjOfMorbReport' and par.subject_entity_uid = p.person_uid and ");
		query.append(" p.cd='PAT' and obs.record_status_cd in ('PROCESSED', 'UNPROCESSED') ");
		query.append(find.getDataAccessWhereClause());

		return query.toString();
	}

	private String buildLabReportFirstQuery(PatientSearchVO find) {
		StringBuffer query = new StringBuffer();
		query.append("select distinct p.person_parent_uid \"personParentUid\" ");
		query.append("from person p with (nolock) , observation obs with (nolock) , participation par with (nolock) ");
		query.append("where obs.local_id='");
		query.append(find.getActId());
		query.append("' and par.act_uid = obs.observation_uid and");
		query.append(" par.type_cd = 'PATSBJ' and par.subject_entity_uid = p.person_uid and ");
		query.append(" p.cd='PAT' and obs.record_status_cd in ('PROCESSED', 'UNPROCESSED') ");
		query.append(find.getDataAccessWhereClause());

		return query.toString();
	}

	private String buildPublicHealthCaseFirstQuery(PatientSearchVO find) {
		StringBuffer query = new StringBuffer();
		query.append("select distinct p.person_parent_uid \"personParentUid\" ");
		query.append("from person p with (nolock) , public_health_case phc with (nolock) , participation par with (nolock) ");
		query.append("where phc.local_id = '");
		query.append(find.getActId());
		query.append("' and par.act_uid = phc.public_health_case_uid and");
		query.append(" par.type_cd = 'SubjOfPHC' and par.subject_entity_uid = p.person_uid ");
		query.append(" and p.cd='PAT' and phc.record_status_cd = 'OPEN' ");
		query.append(find.getDataAccessWhereClause());

		return query.toString();
	}
	
	private String buildEpilinkFirstQuery(PatientSearchVO find) {
		StringBuffer query = new StringBuffer();
		query.append("select distinct p.person_parent_uid \"personParentUid\" ");
		query.append("from person p with (nolock) , public_health_case phc with (nolock) , participation par with (nolock) ");
		query.append("where phc.local_id in (");
		query.append(buildEpiLinkWhereClause(find));
		query.append(") and par.act_uid = phc.public_health_case_uid and");
		query.append(" par.type_cd = 'SubjOfPHC' and par.subject_entity_uid = p.person_uid ");
		query.append(" and p.cd='PAT' and phc.record_status_cd = 'OPEN' ");
		query.append(find.getDataAccessWhereClause());

		return query.toString();
	}
	
	

	// changes made for event search based on the accession number
	private String buildAccessionNumberFirstQuery(PatientSearchVO find) {
		StringBuffer query = new StringBuffer();
		query.append("select distinct p.person_parent_uid \"personParentUid\" ");
		query.append("from person p with (nolock) , observation obs with (nolock) , participation par with (nolock) , act_id actid with (nolock) ");
		query.append("where actid.root_extension_txt = '");
		query.append(find.getActId());
		query.append("' and actid.type_cd = 'FN' and actid.type_desc_txt ='Filler Number' and actid.record_status_cd ='ACTIVE' and ");
		query.append("actid.act_uid = obs.observation_uid and ");
		query.append("par.act_uid = obs.observation_uid and ");
		query.append(" par.subject_entity_uid = p.person_uid and ");
		query.append(" p.cd='PAT' ");
		query.append(find.getDataAccessWhereClause());

		return query.toString();
	}

	// changes made for event search based on the doc number
	private String buildDocNumberFirstQuery(PatientSearchVO find) {
		StringBuffer query = new StringBuffer();
		query.append("select distinct p.person_parent_uid \"personParentUid\" ");
		query.append("from person p with (nolock) , nbs_document doc with (nolock) , participation par with (nolock) ");
		query.append("where doc.local_id='");
		query.append(find.getActId());
		query.append("' and par.act_uid = doc.nbs_document_uid and");
		query.append(" par.type_cd = 'SubjOfDoc' and par.subject_entity_uid = p.person_uid ");
		query.append(" and p.cd='PAT' and doc.record_status_cd !=  'LOG_DEL' ");
		query.append(find.getDataAccessWhereClause());

		return query.toString();
	}

	private String buildNameFirstQuery(PatientSearchVO find) {
		StringBuffer query = new StringBuffer();
		query.append("select distinct p.person_parent_uid \"personParentUid\" ");
		query.append("from person p with (nolock) , person_name pn with (nolock) ");
		query.append("where p.person_uid = pn.person_uid and p.person_uid = p.person_parent_uid ");
		query.append(" and p.cd='PAT' ");
		query.append(find.getDataAccessWhereClause());
		query.append(" and ");
		query.append(buildNameSearchWhereClause(find));
		query.append(" and pn.record_status_cd='ACTIVE' ");

		return query.toString();
	}

	private String buildAddressFirstQuery(PatientSearchVO find) {
		StringBuffer query = new StringBuffer();
		query.append("select distinct p.person_parent_uid \"personParentUid\" ");
		query.append(" from person p with (nolock), postal_locator pl with (nolock), entity_locator_participation elp with (nolock) where ");
		query.append("p.person_uid = elp.entity_uid and p.person_uid = p.person_parent_uid and elp.locator_uid = pl.postal_locator_uid ");
		query.append(" and p.cd='PAT' ");
		query.append(find.getDataAccessWhereClause());
		query.append(" and elp.class_cd = 'PST' ");
		query.append(" and " + buildAddressSearchWhereClause(find));
		query.append(" and elp.status_cd='A' ");
		return query.toString();
	}

	private String buildTeleFirstQuery(PatientSearchVO find) {
		StringBuffer query = new StringBuffer();
		query.append("select distinct p.person_parent_uid \"personParentUid\" ");
		query.append(" from person p with (nolock), tele_locator tl with (nolock), entity_locator_participation elp with (nolock) where ");
		query.append("p.person_uid = elp.entity_uid and p.person_uid = p.person_parent_uid and elp.locator_uid = tl.tele_locator_uid ");
		query.append(" and p.cd='PAT' ");
		query.append(find.getDataAccessWhereClause());
		query.append(" and elp.class_cd='TELE' ");
		query.append(" and " + buildTeleSearchWhereClause(find));
		query.append(" and elp.status_cd='A' ");
		return query.toString();
	}

	private String buildEthnicityFirstQuery(PatientSearchVO find) {
		StringBuffer query = new StringBuffer();
		query.append("select distinct p.person_parent_uid \"personParentUid\" ");
		query.append(" from person p with (nolock) where ");
		query.append(" p.cd='PAT' and p.person_uid = p.person_parent_uid ");
		query.append(find.getDataAccessWhereClause());
		query.append(" and ");
		query.append(" p.ethnic_group_ind = " + "'"
				+ find.getEthnicGroupInd().trim() + "' ");
		return query.toString();
	}

	private String buildRaceFirstQuery(PatientSearchVO find) {
		StringBuffer query = new StringBuffer();
		query.append("select distinct p.person_parent_uid \"personParentUid\" ");
		query.append(" from person p with (nolock) , person_race pr with (nolock) where ");
		query.append("p.person_uid = pr.person_uid and p.person_uid = p.person_parent_uid ");
		query.append(" and p.cd='PAT' ");
		query.append(find.getDataAccessWhereClause());
		query.append(" and pr.race_category_cd = '" + find.getRaceCd() + "' ");
		query.append(" and pr.record_status_cd='ACTIVE' ");
		return query.toString();
	}

	private String buildIdsFirstQuery(PatientSearchVO find) {
		StringBuffer query = new StringBuffer();
		query.append("select distinct p.person_parent_uid \"personParentUid\" ");
		query.append(" from person p with (nolock) , entity_id ei with (nolock) , entity_id e2 with (nolock) ");
		query.append(" where p.person_uid = ei.entity_uid ");
		query.append(" AND p.person_uid = e2.entity_uid and p.person_uid = p.person_parent_uid ");
		query.append(" and p.cd='PAT' ");
		query.append(find.getDataAccessWhereClause());
		query.append(buildEntityIdSearchWhereClause(find));
		query.append(" and ei.status_cd='A' ");
		query.append(" and e2.status_cd='A' ");
		return query.toString();
	}

	private String buildPersonFirstQuery(PatientSearchVO find) {
		StringBuffer query = new StringBuffer();
		query.append("select distinct p.person_parent_uid \"personParentUid\" ");
		query.append(" from person p  with (nolock) where p.person_uid = p.person_parent_uid and ");
		query.append(buildPersonSearchWhereClause(find));
		query.append(" and p.cd='PAT' ");
		query.append(find.getDataAccessWhereClause());
		return query.toString();
	}

	/**
	 * This method builds retrieval portion of the query using UNION between
	 * select statements against PERSON, PERSON_NAME, PERSON_RACE,
	 * POSTAL_LOCATOR, TELE_LOCATOR tables. Each SELECT statement is built using
	 * a join to the derived table defined by the subQuery. subQuery returns
	 * person_uids for all MPRs and revisions, for which data needs to be
	 * retrieved.
	 *
	 * @param subQuery
	 *            Contains person_uid in SELECT clause.
	 * @return UNION select query retrieving all VO data for all person_uids
	 *         specified in subQuery at once. Each select query contains
	 *         dataType fake column, which indicates the type of data in any
	 *         particular row.
	 */
	private String buildRetrievalQuery(String subQuery) {
		StringBuffer query = new StringBuffer();

		query.append("( ");
		query.append(buildNameRetrievalQuery(subQuery));
		query.append(" ) UNION ( ");
		query.append(buildPersonRetrievalQuery(subQuery));
		query.append(" ) UNION ( ");
		query.append(buildIdsRetrievalQuery(subQuery));
		query.append(" ) UNION ( ");
		query.append(buildRaceRetrievalQuery(subQuery));
		query.append(" ) UNION ( ");
		query.append(buildAddressRetrievalQuery(subQuery));
		query.append(" ) UNION ( ");
		query.append(buildTeleRetrievalQuery(subQuery));
		query.append(" ) ");
		return query.toString();
	}

//	private String buildRetrievalQueryInvestigation(String subQuery) {
//		StringBuffer query = new StringBuffer();
///*
//		query.append("( ");
//		query.append(buildInvestigationCriteriaRetrievalQuery(subQuery));
//		query.append(" ) UNION ( ");
//		query.append(buildPersonRetrievalQuery(subQuery));
//		query.append(" ) UNION ( ");
//		query.append(buildIdsRetrievalQuery(subQuery));
//		query.append(" ) UNION ( ");
//		query.append(buildRaceRetrievalQuery(subQuery));
//		query.append(" ) UNION ( ");
//		query.append(buildAddressRetrievalQuery(subQuery));
//		query.append(" ) UNION ( ");
//		query.append(buildTeleRetrievalQuery(subQuery));
//		query.append(" ) ");*/
//		return query.toString();
//	}
	
	private String buildNameRetrievalQuery(String subQuery) {
		StringBuffer query = new StringBuffer();
		
		query.append("select distinct ");

		query.append("'NAME' \"dataType\", ");
		query.append("pn.person_uid \"personUid\", ");
		query.append("sq.person_parent_uid \"personParentUid\", ");
		query.append("pn.last_nm \"lastName\", ");
		query.append("pn.middle_nm \"nmMiddle\", ");
		query.append("pn.first_nm \"firstName\", ");
		query.append("pn.record_status_cd \"recordStatusCd\", ");
		query.append("pn.nm_use_cd \"nameUseCd\", ");
		query.append("pn.nm_suffix \"nmSuffix\", ");
		query.append("pn.nm_degree \"nmDegree\", ");
		query.append("null \"localId\", ");
		query.append("null \"recordStatusCd\", ");
		query.append("null \"asOfDateAdmin\", ");
		query.append("null \"ageReported\", ");
		query.append("CONVERT(integer,0) \"versionCtrlNbr\", ");
		query.append("null \"ageUnit\", ");
		query.append("null \"ssn\", ");
		query.append("null \"maritalStatusCd\", ");
		query.append("null \"ethnicGroupInd\", ");
		query.append("null \"dob\", ");
		query.append("null \"currentSex\", ");
		query.append("CONVERT(datetime,'') \"deceasedTime\", ");
		query.append("null \"eiRootExtensionTxt\",");
		query.append("null \"eiTypeDesc\", ");
		query.append("null \"raceCd\", ");
		query.append("null \"raceDescTxt\", ");
		query.append("null \"classCd\", ");
		query.append("null \"locatorTypeCdDesc\", ");
		query.append("null \"locatorUseCd\", ");
		query.append("null \"locatorCd\", ");
		query.append("null \"streetAddr1\", ");
		query.append("null \"streetAddr2\", ");
		query.append("null \"city\", ");
		query.append("null \"zip\", ");
		query.append("null \"cntyCd\", ");
		query.append("null \"state\", ");
		query.append("null \"cntryCd\", ");
		query.append("CONVERT(integer,null) \"locatorUid\", ");
		query.append("null \"telephoneNbr\", ");
		query.append("null \"extensionTxt\", ");
		query.append("null \"emailAddress\", ");
		query.append("null \"eiRootExtensionTxt\", ");
		query.append("null \"eiTypeDesc\" ");
		// query.append("null \"condition\" ");

		query.append("from person_name pn with (nolock) , person sq with (nolock) ");
		query.append("where pn.person_uid in (");
		query.append(subQuery);
		query.append(") and pn.person_uid = sq.person_uid ");
		query.append("and pn.record_status_cd='ACTIVE' ");

		return query.toString();
	}

	private String buildPersonRetrievalQuery(String subQuery) {
		StringBuffer query = new StringBuffer();
		
		query.append("select distinct ");

		query.append("'PERSON' \"dataType\", ");
		query.append("p.person_uid \"personUid\", ");
		query.append("p.person_parent_uid \"personParentUid\", ");
		query.append("null \"lastName\", ");
		query.append("null \"nmMiddle\", ");
		query.append("null \"firstName\", ");
		query.append("null \"recordStatusCd\", ");
		query.append("null \"nameUseCd\", ");
		query.append("null \"nmSuffix\", ");
		query.append("null \"nmDegree\", ");
		query.append("p.local_id \"localId\", ");
		query.append("p.record_status_cd \"recordStatusCd\", ");
		query.append("CONVERT(char, p.as_of_date_admin) \"asOfDateAdmin\" , ");
		query.append("p.age_reported \"ageReported\", ");
		query.append("p.version_ctrl_nbr \"versionCtrlNbr\", ");
		query.append("p.age_reported_unit_cd \"ageUnit\", ");
		query.append("p.ssn \"ssn\", ");
		query.append("p.marital_status_cd \"maritalStatusCd\", ");
		query.append("p.ethnic_group_ind \"ethnicGroupInd\", ");
		query.append("CONVERT(varchar, p.birth_time, 101)  \"dob\", ");
		query.append("p.curr_sex_cd \"currentSex\", ");
		query.append("p.deceased_time \"deceasedTime\", ");
		query.append("null \"eiRootExtensionTxt\",");
		query.append("null \"eiTypeDesc\", ");
		query.append("null \"raceCd\", ");
		query.append("null \"raceDescTxt\", ");
		query.append("null \"classCd\", ");
		query.append("null \"locatorTypeCdDesc\", ");
		query.append("null \"locatorUseCd\", ");
		query.append("null \"locatorCd\", ");
		query.append("null \"streetAddr1\", ");
		query.append("null \"streetAddr2\", ");
		query.append("null \"city\", ");
		query.append("null \"zip\", ");
		query.append("null \"cntyCd\", ");
		query.append("null \"state\", ");
		query.append("null \"cntryCd\", ");
		query.append("CONVERT(integer,null) \"locatorUid\", ");
		query.append("null \"telephoneNbr\", ");
		query.append("null \"extensionTxt\", ");
		query.append("null \"emailAddress\", ");
		query.append("null \"eiRootExtensionTxt\", ");
		query.append("null \"eiTypeDesc\" ");
		// query.append("null \"condition\" ");

		query.append("from person p with (nolock) ");
		query.append("where p.person_uid in ( ");
		query.append(subQuery);
		query.append(" )");

		return query.toString();
	}

	private String buildIdsRetrievalQuery(String subQuery) {
		StringBuffer query = new StringBuffer();
		
		query.append("select distinct ");

		query.append("'ID' \"dataType\", ");
		query.append("ei.entity_uid \"personUid\", ");
		query.append("sq.person_parent_uid \"personParentUid\", ");
		query.append("null \"lastName\", ");
		query.append("null \"nmMiddle\", ");
		query.append("null \"firstName\", ");
		query.append("null \"recordStatusCd\", ");
		query.append("null \"nameUseCd\", ");
		query.append("null \"nmSuffix\", ");
		query.append("null \"nmDegree\", ");
		query.append("null \"localId\", ");
		query.append("null \"recordStatusCd\", ");
		query.append("null \"asOfDateAdmin\", ");
		query.append("null \"ageReported\", ");
		query.append("CONVERT(integer,0) \"versionCtrlNbr\", ");
		query.append("null \"ageUnit\", ");
		query.append("null \"ssn\", ");
		query.append("null \"maritalStatusCd\", ");
		query.append("null \"ethnicGroupInd\", ");
		query.append("null \"dob\", ");
		query.append("null \"currentSex\", ");
		query.append("CONVERT(datetime,'') \"deceasedTime\", ");
		query.append("ei.root_extension_txt \"eiRootExtensionTxt\",");
		query.append("ei.type_cd \"eiTypeDesc\", ");
		query.append("null \"raceCd\", ");
		query.append("null \"raceDescTxt\", ");
		query.append("null \"classCd\", ");
		query.append("null \"locatorTypeCdDesc\", ");
		query.append("null \"locatorUseCd\", ");
		query.append("null \"locatorCd\", ");
		query.append("null \"streetAddr1\", ");
		query.append("null \"streetAddr2\", ");
		query.append("null \"city\", ");
		query.append("null \"zip\", ");
		query.append("null \"cntyCd\", ");
		query.append("null \"state\", ");
		query.append("null \"cntryCd\", ");
		query.append("CONVERT(integer,null) \"locatorUid\", ");
		query.append("null \"telephoneNbr\", ");
		query.append("null \"extensionTxt\", ");
		query.append("null \"emailAddress\", ");
		query.append("null \"eiRootExtensionTxt\", ");
		query.append("null \"eiTypeDesc\" ");
		// query.append("null \"condition\" ");

		query.append("from entity_id ei with (nolock) , person sq with (nolock) ");
		query.append("where ei.entity_uid in ( ");
		query.append(subQuery);
		query.append(" ) and ei.entity_uid = sq.person_uid ");
		query.append("and ei.status_cd='A' ");

		return query.toString();
	}

	private String buildRaceRetrievalQuery(String subQuery) {
		StringBuffer query = new StringBuffer();
		
		query.append("select distinct ");

		query.append("'RACE' \"dataType\", ");
		query.append("pr.person_uid \"personUid\", ");
		query.append("sq.person_parent_uid \"personParentUid\", ");
		query.append("null \"lastName\", ");
		query.append("null \"nmMiddle\", ");
		query.append("null \"firstName\", ");
		query.append("null \"recordStatusCd\", ");
		query.append("null \"nameUseCd\", ");
		query.append("null \"nmSuffix\", ");
		query.append("null \"nmDegree\", ");
		query.append("null \"localId\", ");
		query.append("null \"recordStatusCd\", ");
		query.append("null \"asOfDateAdmin\", ");
		query.append("null \"ageReported\", ");
		query.append("CONVERT(integer,0) \"versionCtrlNbr\", ");
		query.append("null \"ageUnit\", ");
		query.append("null \"ssn\", ");
		query.append("null \"maritalStatusCd\", ");
		query.append("null \"ethnicGroupInd\", ");
		query.append("null \"dob\", ");
		query.append("null \"currentSex\", ");
		query.append("CONVERT(datetime,'') \"deceasedTime\", ");
		query.append("null \"eiRootExtensionTxt\",");
		query.append("null \"eiTypeDesc\", ");
		query.append("pr.race_cd \"raceCd\", ");
		query.append("pr.race_desc_txt \"raceDescTxt\", ");
		query.append("null \"classCd\", ");
		query.append("null \"locatorTypeCdDesc\", ");
		query.append("null \"locatorUseCd\", ");
		query.append("null \"locatorCd\", ");
		query.append("null \"streetAddr1\", ");
		query.append("null \"streetAddr2\", ");
		query.append("null \"city\", ");
		query.append("null \"zip\", ");
		query.append("null \"cntyCd\", ");
		query.append("null \"state\", ");
		query.append("null \"cntryCd\", ");
		query.append("CONVERT(integer,null) \"locatorUid\", ");
		query.append("null \"telephoneNbr\", ");
		query.append("null \"extensionTxt\", ");
		query.append("null \"emailAddress\", ");
		query.append("null \"eiRootExtensionTxt\", ");
		query.append("null \"eiTypeDesc\" ");
		// query.append("null \"condition\" ");

		query.append("from person_race pr with (nolock), person sq with (nolock) ");
		query.append("where pr.person_uid in ( ");
		query.append(subQuery);
		query.append(" ) and pr.person_uid = sq.person_uid ");
		query.append("and pr.record_status_cd='ACTIVE' ");
		return query.toString();
	}

	private String buildAddressRetrievalQuery(String subQuery) {
		StringBuffer query = new StringBuffer();

		
		query.append("select distinct ");

		query.append("'ADDRESS' \"dataType\", ");
		query.append("elp.entity_uid \"personUid\", ");
		query.append("sq.person_parent_uid \"personParentUid\", ");
		query.append("null \"lastName\", ");
		query.append("null \"nmMiddle\", ");
		query.append("null \"firstName\", ");
		query.append("null \"recordStatusCd\", ");
		query.append("null \"nameUseCd\", ");
		query.append("null \"nmSuffix\", ");
		query.append("null \"nmDegree\", ");
		query.append("null \"localId\", ");
		query.append("null \"recordStatusCd\", ");
		query.append("null \"asOfDateAdmin\", ");
		query.append("null \"ageReported\", ");
		query.append("CONVERT(integer,0) \"versionCtrlNbr\", ");
		query.append("null \"ageUnit\", ");
		query.append("null \"ssn\", ");
		query.append("null \"maritalStatusCd\", ");
		query.append("null \"ethnicGroupInd\", ");
		query.append("null \"dob\", ");
		query.append("null \"currentSex\", ");
		query.append("CONVERT(datetime,'') \"deceasedTime\", ");
		query.append("null \"eiRootExtensionTxt\",");
		query.append("null \"eiTypeDesc\", ");
		query.append("null \"raceCd\", ");
		query.append("null \"raceDescTxt\", ");
		query.append("elp.class_Cd \"classCd\", ");
		query.append("elp.cd \"locatorTypeCdDesc\", ");
		query.append("elp.use_cd \"locatorUseCd\", ");
		query.append("elp.cd \"locatorCd\", ");
		query.append("pl.street_addr1 \"streetAddr1\", ");
		query.append("pl.street_addr2 \"streetAddr2\", ");
		query.append("pl.city_desc_txt \"city\", ");
		query.append("pl.zip_cd \"zip\", ");
		query.append("pl.cnty_cd \"cntyCd\", ");
		query.append("pl.state_cd \"state\", ");
		query.append("pl.cntry_cd \"cntryCd\", ");
		query.append("CONVERT(integer,null) \"locatorUid\", ");
		query.append("null \"telephoneNbr\", ");
		query.append("null \"extensionTxt\", ");
		query.append("null \"emailAddress\", ");
		query.append("null \"eiRootExtensionTxt\", ");
		query.append("null \"eiTypeDesc\" ");
		// query.append("null \"condition\" ");

		query.append("from entity_locator_participation elp with (nolock) , postal_locator pl with (nolock) , person sq with (nolock) ");
		query.append("Where elp.entity_uid in ("+subQuery+") and elp.entity_uid = sq.person_uid " );
		query.append("and elp.locator_uid = pl.postal_locator_uid  ");
		query.append("and elp.class_cd = 'PST' and elp.status_cd = 'A' ");
		

		return query.toString();
	}

	private String buildTeleRetrievalQuery(String subQuery) {
		StringBuffer query = new StringBuffer();
		

		query.append("select distinct ");

		query.append("'TELE' \"dataType\", ");
		query.append("elp.entity_uid \"personUid\", ");
		query.append("sq.person_parent_uid \"personParentUid\", ");
		query.append("null \"lastName\", ");
		query.append("null \"nmMiddle\", ");
		query.append("null \"firstName\", ");
		query.append("null \"recordStatusCd\", ");
		query.append("null \"nameUseCd\", ");
		query.append("null \"nmSuffix\", ");
		query.append("null \"nmDegree\", ");
		query.append("null \"localId\", ");
		query.append("null \"recordStatusCd\", ");
		query.append("null \"asOfDateAdmin\", ");
		query.append("null \"ageReported\", ");
		query.append("CONVERT(integer,0) \"versionCtrlNbr\", ");
		query.append("null \"ageUnit\", ");
		query.append("null \"ssn\", ");
		query.append("null \"maritalStatusCd\", ");
		query.append("null \"ethnicGroupInd\", ");
		query.append("null \"dob\", ");
		query.append("null \"currentSex\", ");
		query.append("CONVERT(datetime,'') \"deceasedTime\", ");
		query.append("null \"eiRootExtensionTxt\",");
		query.append("null \"eiTypeDesc\", ");
		query.append("null \"raceCd\", ");
		query.append("null \"raceDescTxt\", ");
		query.append("elp.class_cd \"classCd\", ");
		query.append("null \"locatorTypeCdDesc\", ");
		query.append("elp.use_cd \"locatorUseCd\", ");
		query.append("elp.cd \"locatorCd\", ");
		query.append("null \"streetAddr1\", ");
		query.append("null \"streetAddr2\", ");
		query.append("null \"city\", ");
		query.append("null \"zip\", ");
		query.append("null \"cntyCd\", ");
		query.append("null \"state\", ");
		query.append("null \"cntryCd\", ");
		query.append("tl.tele_locator_uid \"locatorUid\", ");
		query.append("tl.phone_nbr_txt \"telephoneNbr\", ");
		query.append("tl.extension_txt \"extensionTxt\", ");
		query.append("tl.email_address \"emailAddress\", ");
		query.append("null \"eiRootExtensionTxt\", ");
		query.append("null \"eiTypeDesc\" ");
		// query.append("null \"condition\" ");

		query.append("from entity_locator_participation elp with (nolock) , tele_locator tl with (nolock) , person sq with (nolock) ");
		query.append("Where elp.entity_uid in ("+subQuery+") " );
		query.append("and elp.locator_uid = tl.tele_locator_uid   and elp.entity_uid = sq.person_uid ");
		query.append("and elp.class_cd = 'TELE' and elp.status_cd = 'A' ");

		return query.toString();
	}

	/**
	 * Assembles all data retrieved from the database into Patient value
	 * objects, which can be passed back to the client. A collection of
	 * PatientSrchResultVO representing MPRs. Each MPR has all (if any)
	 * corresponding revisions packaged within it.
	 *
	 * @param allRetrievedData
	 *            Collection<Object> of PersonSearchResultTmp representing
	 *            results of a db query. Each row must contain a dataType
	 *            column.
	 * @return A collection of PatientSrchResultVO representing MPRs. Each MPR
	 *         has all (if any) corresponding revisions packaged within it.
	 */
	private ArrayList<Object> assemblePersonData(ArrayList<?> allRetrievedData) {
		// nclogger.info("--" + new java.util.Date().getTime() +
		// "--DAO:assemblePersonData(ArrayList<Object> ) : START");

		ArrayList<Object> result = new ArrayList<Object>();
		try{
			if (allRetrievedData != null) {
				int size = allRetrievedData.size();
				HashMap<Object, Object> uidsWithParents = new HashMap<Object, Object>();
				ArrayList<Object> personDataArray = new ArrayList<Object>();
				ArrayList<Object> nameDataArray = new ArrayList<Object>();
				ArrayList<Object> idsDataArray = new ArrayList<Object>();
				ArrayList<Object> raceDataArray = new ArrayList<Object>();
				ArrayList<Object> addressDataArray = new ArrayList<Object>();
				ArrayList<Object> teleDataArray = new ArrayList<Object>();
	
				PersonSearchResultTmp curPerson = null;
				String curDataType = null;
				Long curPersonUid = null;
				Long curParentUid = null;
	
				for (int i = 0; i < size; i++) {
					curPerson = (PersonSearchResultTmp) allRetrievedData.get(i);
					curDataType = curPerson.getDataType();
					curPersonUid = curPerson.getPersonUid();
					curParentUid = curPerson.getPersonParentUid();
	
					if (curDataType.equals("PERSON")) {
						if (!uidsWithParents.containsKey(curPersonUid)) {
							uidsWithParents.put(curPersonUid, curParentUid);
						}
	
						personDataArray.add(curPerson);
					} else if (curDataType.equals("NAME")) {
						nameDataArray.add(curPerson);
					} else if (curDataType.equals("ID")) {
						idsDataArray.add(curPerson);
					} else if (curDataType.equals("RACE")) {
						raceDataArray.add(curPerson);
					} else if (curDataType.equals("ADDRESS")) {
						addressDataArray.add(curPerson);
					} else if (curDataType.equals("TELE")) {
						teleDataArray.add(curPerson);
					}
	
				}
	
				HashMap<Object, Object> personData = repackageResults(
						personDataArray, DT_PERSON);
				HashMap<Object, Object> nameData = repackageResults(nameDataArray,
						DT_NAME);
				HashMap<Object, Object> idsData = repackageResults(idsDataArray,
						DT_ID);
				HashMap<Object, Object> raceData = repackageResults(raceDataArray,
						DT_RACE);
				HashMap<Object, Object> addressData = repackageResults(
						addressDataArray, DT_ADDRESS);
				HashMap<Object, Object> teleData = repackageResults(teleDataArray,
						DT_TELE);
	
				result = assemblePersonData(uidsWithParents, personData, nameData,
						idsData, raceData, addressData, teleData);
			}
	
			// nclogger.info("--" + new java.util.Date().getTime() +
			// "--DAO:assemblePersonData(ArrayList<Object> ) : END");
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return result;
	}
	
	private ArrayList<Object> assembleInvestigationData(ArrayList<?> allRetrievedData) {
		// nclogger.info("--" + new java.util.Date().getTime() +
		// "--DAO:assemblePersonData(ArrayList<Object> ) : START");

		ArrayList<Object> result = new ArrayList<Object>();
		try{
			if (allRetrievedData != null) {
				int size = allRetrievedData.size();
				HashMap<Object, Object> uidsWithParents = new HashMap<Object, Object>();
				ArrayList<Object> personDataArray = new ArrayList<Object>();
				ArrayList<Object> nameDataArray = new ArrayList<Object>();
				ArrayList<Object> idsDataArray = new ArrayList<Object>();
				ArrayList<Object> raceDataArray = new ArrayList<Object>();
				ArrayList<Object> addressDataArray = new ArrayList<Object>();
				ArrayList<Object> teleDataArray = new ArrayList<Object>();
	
				PersonSearchResultTmp curInvestigation = null;
				String curDataType = null;
				Long curPersonUid = null;
				Long curParentUid = null;
				PatientSrchResultVO investigation;
				
				for (int i = 0; i < size; i++) {
					curInvestigation = (PersonSearchResultTmp) allRetrievedData.get(i);
					investigation=new PatientSrchResultVO();
					
					investigation.setCondition(curInvestigation.getConditionCodeText());
					investigation.setNotification(curInvestigation.getNotifRecordStatusCd());
					investigation.setCaseStatus(curInvestigation.getCaseClassCodeTxt());
					investigation.setPersonUID(curInvestigation.getMPRUid());
					investigation.setCd(curInvestigation.getCd());
					investigation.setCaseStatusCd(curInvestigation.getCaseClassCd());
				//	investigation.g
					investigation.setJurisdictionCd(curInvestigation.getJurisdictionCd());
					investigation.setJurisdiction(curInvestigation.getJurisdictionDescTxt());
					investigation.setInvestigatorFirstName(curInvestigation.getInvestigatorFirstName());
					investigation.setInvestigatorLastName(curInvestigation.getInvestigatorLastName());
					investigation.setStartDate(curInvestigation.getActivityFromTime());
					investigation.setPersonFirstName(curInvestigation.getPatientFirstName());
					investigation.setPersonLastName(curInvestigation.getPatientLastName());
					investigation.setPublicHealthCaseUid(curInvestigation.getPublicHealthCaseUid()+"");
					
					result.add(investigation);
				//investigation.setSummaryVOColl(result);
					//curDataType = curInvestigation.getDataType();
					//curPersonUid = curInvestigation.getPersonUid();
					//curParentUid = curInvestigation.getPersonParentUid();
					//result.add(curInvestigation);//TODO: if we add this, it causes error page.
					
	/*
					if (curDataType.equals("PERSON")) {
						if (!uidsWithParents.containsKey(curPersonUid)) {
							uidsWithParents.put(curPersonUid, curParentUid);
						}
	
						personDataArray.add(curPerson);
					} else if (curDataType.equals("NAME")) {
						nameDataArray.add(curPerson);
					} else if (curDataType.equals("ID")) {
						idsDataArray.add(curPerson);
					} else if (curDataType.equals("RACE")) {
						raceDataArray.add(curPerson);
					} else if (curDataType.equals("ADDRESS")) {
						addressDataArray.add(curPerson);
					} else if (curDataType.equals("TELE")) {
						teleDataArray.add(curPerson);
					}
	
				}
	
				HashMap<Object, Object> personData = repackageResults(
						personDataArray, DT_PERSON);
				HashMap<Object, Object> nameData = repackageResults(nameDataArray,
						DT_NAME);
				HashMap<Object, Object> idsData = repackageResults(idsDataArray,
						DT_ID);
				HashMap<Object, Object> raceData = repackageResults(raceDataArray,
						DT_RACE);
				HashMap<Object, Object> addressData = repackageResults(
						addressDataArray, DT_ADDRESS);
				HashMap<Object, Object> teleData = repackageResults(teleDataArray,
						DT_TELE);
	
				result = assemblePersonData(uidsWithParents, personData, nameData,
						idsData, raceData, addressData, teleData);
			}
	*/
			// nclogger.info("--" + new java.util.Date().getTime() +
			// "--DAO:assemblePersonData(ArrayList<Object> ) : END");
		
				}
			}
				}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return result;
	}
	

	/**
	 * Rearranges the data of a given dataType in such a way, where for each
	 * person_uid in HashMap, the corresponding value is an array of DT objects
	 * of the type specified by dataType.
	 *
	 * @param source
	 *            Collection<Object> of PersonSearchResultTmp objects, which at
	 *            least have personUid and dataType attributes populated.
	 * @param dataType
	 *            Indicates the type of DT object to package data into.
	 * @return HashMap<Object,Object> with person_uid as keys and values being
	 *         collection of corresponding DT objects of data type specified by
	 *         dataType input parameter.
	 */
	private HashMap<Object, Object> repackageResults(ArrayList<Object> source,
			int dataType) {
		HashMap<Object, Object> result = new HashMap<Object, Object>();

		try{
			if (source != null && !source.isEmpty()) {
				int size = source.size();
				// nclogger.info("source size is " + size);
				PersonSearchResultTmp cur = null;
				Long curUid = null;
	
				for (int i = 0; i < size; i++) {
					cur = (PersonSearchResultTmp) source.get(i);
	
					if (cur != null) {
						curUid = (Long) cur.getPersonUid();
						ArrayList<Object> valueCollector = new ArrayList<Object>();
	
						if (result.containsKey(curUid)) {
							// nclogger.info("uid " + curUid +
							// " is already in result");
						} else {
							for (int j = 0; j < size; j++) {
								PersonSearchResultTmp cur1 = (PersonSearchResultTmp) source
										.get(j);
								Long curUid1 = (Long) cur1.getPersonUid();
								// nclogger.info("i = " + i + " | curUid = " +
								// curUid + " | j = " + j + " | curUid1 = " +
								// curUid1);
	
								if (curUid1.equals(curUid)) {
									switch (dataType) {
									case DT_PERSON:
										// nclogger.info("datatype = DT_PERSON");
										valueCollector.add(cur1);
										break;
									case DT_NAME:
										// nclogger.info("datatype = DT_NAME");
										addNameDTObjectTo(valueCollector, cur1);
										break;
									case DT_ID:
										// nclogger.info("datatype = DT_ID");
										addEntityIdDTObjectTo(valueCollector, cur1);
										break;
									case DT_RACE:
										// nclogger.info("datatype = DT_RACE");
										addRaceDTObjectTo(valueCollector, cur1);
										break;
									case DT_ADDRESS:
										// nclogger.info("datatype = DT_ADDRESS");
										addAddressLocatorDTObjectTo(valueCollector,
												cur1);
										break;
									case DT_TELE:
										// nclogger.info("datatype = DT_TELE");
										addTeleLocatorDTObjectTo(valueCollector,
												cur1);
										break;
	
									default:
										break;
									}
								}
							}
	
							result.put(curUid, valueCollector);
						}
					}
				}
			} else {
				// nclogger.info("SOURCE ArrayList<Object> is NULL or EMPTY, NOTHING TO REPACKAGE");
			}


		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return result;
	}

	/**
	 * Assembles input data into a collection of value objects representing MPRs
	 * (revisions are included within MPRs VO objects)
	 *
	 * @param uidsWithParents
	 *            Key - person_uid for MPR or revision, value -
	 *            person_parent_uid.
	 * @param personData
	 *            Key - person_uid for MPR or revision, value - PersonDT object.
	 * @param nameData
	 *            Key - person_uid for MPR or revision, value - PersonNameDT
	 *            object.
	 * @param idsData
	 *            Key - person_uid for MPR or revision, value - PersonIdDT
	 *            object.
	 * @param raceData
	 *            Key - person_uid for MPR or revision, value - PersonRaceDT
	 *            object.
	 * @param addressData
	 *            Key - person_uid for MPR or revision, value - PersonAddressDT
	 *            object.
	 * @param teleData
	 *            Key - person_uid for MPR or revision, value - PersonTeleDT
	 *            object.
	 * @return A collection of PatietnSrchResultVO objects representing MPRs.
	 */
	private ArrayList<Object> assemblePersonData(
			HashMap<Object, Object> uidsWithParents,
			HashMap<Object, Object> personData,
			HashMap<Object, Object> nameData, HashMap<Object, Object> idsData,
			HashMap<Object, Object> raceData,
			HashMap<Object, Object> addressData,
			HashMap<Object, Object> teleData

	) {
		// nclogger.info("--" + new java.util.Date().getTime() +
		// "--DAO:assemblePersonData : START");

		ArrayList<Object> result = new ArrayList<Object>(); // array of
															// PatientSrchResultVO
															// objects
															// representing MPRs
															// with revisions
		try{
			if (uidsWithParents != null && !uidsWithParents.isEmpty()) {
				ArrayList<Object> arrMprUids = new ArrayList<Object>();
				ArrayList<Object> arrRevisionUids = new ArrayList<Object>();
				HashMap<Object, Object> mapMprs = new HashMap<Object, Object>();
				HashMap<Object, Object> mapRevisions = new HashMap<Object, Object>();
				HashMap<Object, Object> mapMappedRevisionsByParentUid = new HashMap<Object, Object>();
	
				separateMprsAndRevisions(uidsWithParents, arrMprUids,
						arrRevisionUids);
				boolean populateMprs = true;
				populatePatients(arrMprUids, personData, nameData, idsData,
						raceData, addressData, teleData, mapMprs, populateMprs);
				populatePatients(arrRevisionUids, personData, nameData, idsData,
						raceData, addressData, teleData, mapRevisions,
						!populateMprs);
				mapRevisionsToMprUid(mapRevisions, mapMappedRevisionsByParentUid);
	
				// attach revisions to MPRs
				// nclogger.info("+++ATTACHING REVISIONS TO MPRs+++");
				if (mapMprs != null && !mapMprs.isEmpty()) {
					Collection<Object> keys = mapMprs.keySet();
					Iterator<Object> it = keys.iterator();
					Long curMprUid = null;
					PatientSrchResultVO curMpr = null;
					ArrayList<Object> curRevisions = null;
					int count = 0;
	
					while (it.hasNext()) {
						curMprUid = (Long) it.next();
						// nclogger.info("processing MPR item " + count +
						// " curMprUid:" + curMprUid);
						curMpr = (PatientSrchResultVO) mapMprs.get(curMprUid);
	
						if (curMpr != null) {
							if (mapMappedRevisionsByParentUid != null
									&& !mapMappedRevisionsByParentUid.isEmpty()) {
								curRevisions = (ArrayList<Object>) mapMappedRevisionsByParentUid
										.get(curMprUid);
								curMpr.setRevisionColl(curRevisions);
							} else {
								// nclogger.info("mapMappedRevisionsByParentUid is NULL or EMPTY");
							}
						} else {
							// nclogger.info("curMpr is NULL");
						}
	
						result.add(curMpr);
						count++;
					}
				} else {
					// nclogger.info("THERE ARE NO MPRS to map to revisions");
				}
			} else {
				// nclogger.info("uidsiwthparents is null or empty");
			}

		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return result;
	}

	/**
	 * This method evaluates which keys in uidsWithParents represent MPRs and
	 * which represent revisions and puts them into two different collections.
	 *
	 * @param [IN] uidsWithParents Key - person_uid for MPR or revision, value -
	 *        person_parent_uid.
	 * @param [IN/OUT] arrMprUids Collection<Object> of MPR person_uids.
	 * @param [IN/OUT] arrRevisionUids Collection<Object> of reivision
	 *        person_uids.
	 */
	private void separateMprsAndRevisions(
			HashMap<Object, Object> uidsWithParents,
			ArrayList<Object> arrMprUids, ArrayList<Object> arrRevisionUids) {
		try{
			if (arrMprUids == null)
				arrMprUids = new ArrayList<Object>();
	
			if (arrRevisionUids == null)
				arrRevisionUids = new ArrayList<Object>();
	
			Collection<Object> keys = uidsWithParents.keySet();
			Iterator<Object> it = keys.iterator();
			Long curPersonUid = null;
			Long curParentUid = null;
	
			while (it.hasNext()) {
				curPersonUid = (Long) it.next();
				curParentUid = (Long) uidsWithParents.get(curPersonUid);
	
				if (curParentUid != null && curPersonUid != null) {
					if (curParentUid.equals(curPersonUid)) {
						// nclogger.info("adding curParentUid="+curParentUid);
						arrMprUids.add(curParentUid);
					} else {
						// nclogger.info("adding curPersonUid="+curPersonUid);
						arrRevisionUids.add(curPersonUid);
					}
				} else {
					// nclogger.info("curparentuid or curpersonuid is null");
				}
			}
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	/**
	 * Builds [IN]PatientSrchResultVO objects for MPR or revision object
	 * indicated by person_uid in arrUids collection.
	 *
	 * @param [IN]arrUids Collection<Object> of person_uid.
	 * @param [IN]personData Key - person_uid for MPR or revision, value -
	 *        PersonDT object.
	 * @param [IN]nameData Key - person_uid for MPR or revision, value -
	 *        PersonNameDT object.
	 * @param [IN]idsData Key - person_uid for MPR or revision, value -
	 *        PersonIdDT object.
	 * @param [IN]raceData Key - person_uid for MPR or revision, value -
	 *        PersonRaceDT object.
	 * @param [IN]addressData Key - person_uid for MPR or revision, value -
	 *        PersonAddressDT object.
	 * @param [IN]teleData Key - person_uid for MPR or revision, value -
	 *        PersonTeleDT object.
	 * @param [IN/OUT] mapVosByUidKey Key-person_uid, value- corresponding
	 *        PatientSrchResultVO
	 * @param [IN]popupateMprs Indicates whether MPRs are revisions are being
	 *        processed.
	 */
	private void populatePatients(ArrayList<Object> arrUids,
			HashMap<Object, Object> personData,
			HashMap<Object, Object> nameData, HashMap<Object, Object> idsData,
			HashMap<Object, Object> raceData,
			HashMap<Object, Object> addressData,
			HashMap<Object, Object> teleData,
			HashMap<Object, Object> mapVosByUidKey, boolean popupateMprs) {
		try{
			if (arrUids != null && !arrUids.isEmpty()) {
				if (mapVosByUidKey == null) {
					mapVosByUidKey = new HashMap<Object, Object>();
				}
	
				int size = arrUids.size();
				Long curUid = null;
				PersonSrchResultVO curPatient = null;
				ArrayList<?> curPersonData = null;
				ArrayList<Object> curNameData = null;
				ArrayList<Object> curIdsData = null;
				ArrayList<Object> curRaceData = null;
				ArrayList<?> curAddressData = null;
				ArrayList<?> curTeleData = null;
				ArrayList<Object> curConditionData = null;
				for (int i = 0; i < size; i++) {
					curUid = (Long) arrUids.get(i);
					if (popupateMprs) {
						curPatient = new PatientSrchResultVO();
					} else {
						curPatient = new PatientRevisionSrchResultVO();
					}
	
					curPersonData = (ArrayList<?>) personData.get(curUid);
					curNameData = (ArrayList<Object>) nameData.get(curUid);
					curIdsData = (ArrayList<Object>) idsData.get(curUid);
					curRaceData = (ArrayList<Object>) raceData.get(curUid);
					curAddressData = (ArrayList<?>) addressData.get(curUid);
					curTeleData = (ArrayList<?>) teleData.get(curUid);
	
					// nclogger.info("PROCESSING ITEM " + i + " WITH UID " +
					// curUid);
					populatePatientVO(curPatient, curPersonData, curNameData,
							curIdsData, curRaceData, curAddressData, curTeleData);
	
					mapVosByUidKey.put(curUid, curPatient);
				}
			}
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	/**
	 * This method populates a given PersonSrchResultVO object.
	 *
	 * @param [IN/OUT]patientVo PersonSearchVo object to be populated. Cannot be
	 *        null.
	 * @param [IN]personData Key - person_uid for MPR or revision, value -
	 *        PersonDT object.
	 * @param [IN]nameData Key - person_uid for MPR or revision, value -
	 *        PersonNameDT object.
	 * @param [IN]idsData Key - person_uid for MPR or revision, value -
	 *        PersonIdDT object.
	 * @param [IN]raceData Key - person_uid for MPR or revision, value -
	 *        PersonRaceDT object.
	 * @param [IN]addressData Key - person_uid for MPR or revision, value -
	 *        PersonAddressDT object.
	 * @param [IN]teleData Key - person_uid for MPR or revision, value -
	 *        PersonTeleDT object.
	 */
	private void populatePatientVO(PersonSrchResultVO patientVo,
			ArrayList<?> personData, ArrayList<Object> nameData,
			ArrayList<Object> idsData, ArrayList<Object> raceData,
			ArrayList<?> addressData, ArrayList<?> teleData

	) {
		try{
			if (patientVo != null && personData != null && !personData.isEmpty()) {
				PersonSearchResultTmp tmp = (PersonSearchResultTmp) personData
						.get(0);
				patientVo.setPersonUID(tmp.getPersonUid());
				patientVo.setPersonParentUid(tmp.getPersonParentUid());
				patientVo.setPersonDOB(tmp.getDob());
				patientVo.setEthnicGroupInd(tmp.getEthnicGroupInd());
				patientVo.setAsOfDateAdmin(tmp.getAsOfDateAdmin());
				patientVo.setAgeReported(tmp.getAgeReported());
				patientVo.setAgeUnit(tmp.getAgeUnit());
				patientVo.setSsn(tmp.getSsn());
				patientVo.setMaritalStatusCd(tmp.getMaritalStatusCd());
				patientVo.setSex(tmp.getCurrentSex());
				patientVo.setDeceasedTime(tmp.getDeceasedTime());
				patientVo.setVersionCtrlNbr(tmp.getVersionCtrlNbr());
	
				if (tmp.getCurrentSex() != null
						&& tmp.getCurrentSex().trim().length() != 0) {
					CachedDropDownValues cache = new CachedDropDownValues();
					TreeMap<?, ?> map = cache.getCodedValuesAsTreeMap("SEX");
					map = cache.reverseMap(map);
					// we can add another method that does not do reverse
					patientVo.setCurrentSex((String) map.get(tmp.getCurrentSex()));
				}
	
				patientVo.setPersonUID(tmp.getPersonUid());
				patientVo.setPersonParentUid(tmp.getPersonParentUid());
				patientVo.setPersonLocalID(tmp.getLocalId());
				patientVo.setRecordStatusCd(tmp.getRecordStatusCd());
	
				patientVo.setPersonNameColl(nameData);
				patientVo.setRaceCdColl(raceData);
				patientVo.setPersonIdColl(idsData);
	
				ArrayList<Object> locatorColl = new ArrayList<Object>();
	
				if ((addressData != null && !addressData.isEmpty())
						|| (teleData != null && !teleData.isEmpty())) {
					EntityLocatorParticipationDT cur = null;
	
					if (addressData != null) {
						int count = addressData.size();
						for (int n = 0; n < count; n++) {
							cur = (EntityLocatorParticipationDT) addressData.get(n);
							locatorColl.add(cur);
						}
					}
	
					if (teleData != null) {
						int count = teleData.size();
						for (int m = 0; m < count; m++) {
							cur = (EntityLocatorParticipationDT) teleData.get(m);
							locatorColl.add(cur);
						}
					}
				}
	
				patientVo.setPersonLocatorsColl(locatorColl);
	
				// logVO(patientVo, false);
			}

		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	/**
	 * This method correlates revisions with the corresponding MPRs.
	 *
	 * @param [IN]mapRevisionsByPersonUid Key - revision person_uid, value -
	 *        revision VO object.
	 * @param [OUT]mapRevisionsByParentUid Key - MPR person_uid, value -
	 *        collection of corresponding revisions.
	 */
	private void mapRevisionsToMprUid(
			HashMap<Object, Object> mapRevisionsByPersonUid,
			HashMap<Object, Object> mapRevisionsByParentUid) {
		try{
			if (mapRevisionsByPersonUid != null
					&& !mapRevisionsByPersonUid.isEmpty()) {
				Collection<Object> personUids = mapRevisionsByPersonUid.keySet();
				HashMap<Object, Object> unsortedMapRevisionsByParentUid = new HashMap<Object, Object>();
	
				if (personUids != null) {
					if (mapRevisionsByParentUid == null)
						mapRevisionsByParentUid = new HashMap<Object, Object>();
	
					Iterator<Object> it = personUids.iterator();
					Iterator<Object> it1 = personUids.iterator();
					Long curPersonUid = null;
					PatientRevisionSrchResultVO curPatient = null;
					Long curParentUid = null;
					Long curParentUid1 = null;
					int i = 0;
	
					while (it.hasNext()) {
						// nclogger.info("processing Revision item " + i);
	
						curPersonUid = (Long) it.next();
						curPatient = (PatientRevisionSrchResultVO) mapRevisionsByPersonUid
								.get(curPersonUid);
						curParentUid = curPatient.getPersonParentUid();
						ArrayList<Object> revisionsCollector = new ArrayList<Object>();
	
						// nclogger.info("curParentUid = " + curParentUid);
	
						if (!unsortedMapRevisionsByParentUid
								.containsKey(curParentUid)) {
							while (it1.hasNext()) {
								curPersonUid = (Long) it1.next();
								curPatient = (PatientRevisionSrchResultVO) mapRevisionsByPersonUid
										.get(curPersonUid);
								curParentUid1 = curPatient.getPersonParentUid();
	
								if (curParentUid.equals(curParentUid1)) {
									// nclogger.info("adding revision with uid " +
									// curPersonUid + " to mpr with uid " +
									// curParentUid1);
									revisionsCollector.add(curPatient);
								}
							}
	
							// nclogger.info("cureParentUid1 = " + curParentUid1 +
							// " | revisionsCollection.size = " +
							// revisionsCollector.size());
							unsortedMapRevisionsByParentUid.put(curParentUid,
									revisionsCollector);
							it1 = personUids.iterator();
						} else {
							// nclogger.info("THIS parentUid is alredy in mapRevisionsByParentUid");
						}
	
						i++;
					}
	
					// sort all revisions appropriately
					Collection<Object> parentUids = unsortedMapRevisionsByParentUid
							.keySet();
					Iterator<Object> itParentUids = parentUids.iterator();
					ArrayList<?> curRevisions = null;
	
					while (itParentUids != null && itParentUids.hasNext()) {
						curParentUid = (Long) itParentUids.next();
						curRevisions = (ArrayList<?>) unsortedMapRevisionsByParentUid
								.get(curParentUid);
						Collections
								.sort(curRevisions,
										PatientRevisionSrchResultVO.PatientRevisionSrchRsltVOComparator);
						mapRevisionsByParentUid.put(curParentUid, curRevisions);
					}
				}
			}
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	// ************************************************************************
	// *********************** NEW FIND PATIENT: END **************************
	// ************************************************************************
	// ************************************************************************

	private String assemblePersonCondtion(String subQuery) {

		StringBuffer query = new StringBuffer();

		query.append("select distinct ");
		query.append("sq.person_Parent_Uid \"personUid\", ");
		query.append("phc.cd_desc_txt \"condition\" ");
		query.append("from public_health_case phc with (nolock), participation par with (nolock), person sq with (nolock) where sq.person_parent_uid in");
		query.append("( ");
		query.append(subQuery);
		query.append(" ) ");
		query.append("and par.subject_entity_uid = sq.person_Uid ");
		query.append("and phc.public_health_case_uid = par.act_uid  ");
		query.append("and phc.status_cd = 'A' ");
		query.append("and phc.investigation_status_cd = 'O' ");
		query.append("order by sq.person_Parent_Uid ");

		return query.toString();
	}

	public ArrayList<Object> getProviderUids() throws NEDSSSystemException {
		PersonDT personDT = new PersonDT();
		String query;
		PropertyUtil propUtil = PropertyUtil.getInstance();
		// Added for Electronic indicator flag reading from NEDSS.Preperties
		// file-Sathya
		 {
				query = "SELECT p.person_uid \"personUid\" from person p with (nolock)"
						+ " WHERE"
						+ " p.cd = '"	+NEDSSConstants.PRV+ "'"
						+ " and "
						+ "(p.Edx_ind<>'" +NEDSSConstants.EDX_IND+ "' OR p.edx_ind is null) "
				        + " and"
						+" p.electronic_ind='"+NEDSSConstants.ELECTRONIC_IND_ENTITY_MATCH + "'";
			}

		try {
			return (ArrayList<Object>) preparedStmtMethod(personDT, null,
					query, NEDSSConstants.SELECT);
		} catch (Exception e) {
			logger.error("Error in getting the person  UID "+e.getMessage(),e);
			throw new NEDSSSystemException(e.getMessage());
		}

	}

	public Long getPatientUid() throws NEDSSSystemException {

		PropertyUtil propUtil = PropertyUtil.getInstance();

		String query;
		{
			query = "SELECT top(1) p.person_uid \"personUid\" from person p with (nolock) WHERE p.cd = '"
					+ NEDSSConstants.PAT
					+ "' and p.person_uid = p.person_Parent_uid and (p.Edx_ind<>'"
					+ NEDSSConstants.EDX_IND + "' OR p.edx_ind is null) ";
		}
		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;

		try {
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(query);
			resultSet = preparedStmt.executeQuery();
			Long result = null;
			while (resultSet.next()) {
				result = resultSet.getLong(1);
			}
			return result;
		} catch (Exception e) {
			logger.error("Error in getting the person  UID "+e.getMessage(),e);
			throw new NEDSSSystemException(e.getMessage());
		} finally {
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
	}
public void updateMPREdxIndicator(Long personUid)
			throws NEDSSSystemException {
		java.util.Date dateTime = new java.util.Date();
		Timestamp time = new Timestamp(dateTime.getTime());
		String UPDATE_EDX_IND_PERSON = "UPDATE PERSON SET edx_ind = '"
				+ NEDSSConstants.EDX_IND + "' WHERE person_uid = ?";

		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		int resultCount = 0;
		logger.debug(" UPDATE_EDX_IND_PERSON=" + UPDATE_EDX_IND_PERSON);
		dbConnection = getConnection();
		try {
			preparedStmt = dbConnection.prepareStatement(UPDATE_EDX_IND_PERSON);
			int i = 1;

			if (personUid != null)
				preparedStmt.setLong(1, personUid.longValue()); // 2

			resultCount = preparedStmt.executeUpdate();
			logger.debug("resultCount is " + resultCount);
		} catch (SQLException sqlex) {
			logger.fatal("SQLException while personUid:" + personUid, sqlex);
			throw new NEDSSDAOSysException(sqlex.toString());
		} catch (Exception ex) {
			logger.fatal(
					"Error while update into UPDATE_EDX_IND_PERSON, personUid="
							+ personUid, ex);
			throw new NEDSSSystemException(ex.toString());
		} finally {
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}

	}// end of update method

	// to get the hascode from edx patient entity table.
	public void deleteEdxPatientMatchDTColl(long personUid)
			throws NEDSSSystemException {
		EdxPatientMatchDT edxPatientMatchDT = new EdxPatientMatchDT();

		final String DELETE_EDX_PATIENT_MATCH = "DELETE from  "
				+ DataTables.NBS_EDX_PATIENT_MATCH_TABLE
				+ " WITH (ROWLOCK) where patient_uid = ? and match_string not like 'LR^%'";

		PreparedStatement preparedStmt = null;
		Connection dbConnection = null;

		try {
			logger.debug("$$$$###Delete DELETE_EDX_PATIENT_MATCH being called :"
					+ DELETE_EDX_PATIENT_MATCH);
			dbConnection = getConnection();
			preparedStmt = dbConnection
					.prepareStatement(DELETE_EDX_PATIENT_MATCH);
			preparedStmt.setLong(1, personUid);
			preparedStmt.executeUpdate();
		} catch (SQLException se) {
			se.printStackTrace();
			logger.fatal("SQLException while removeEdxPatientMatch "
					+ edxPatientMatchDT.toString(), se);
			throw new NEDSSDAOSysException(
					"Error: SQLException while deleting\n" + se.getMessage());
		} finally {
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}

	}
	public void updateHashCodes() throws NEDSSSystemException, SQLException {
		
		PropertyUtil propUtil = PropertyUtil.getInstance();
	//	callPatientHashCodeStoredProc();
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement psmt = null;
		String SELECT_EDX_PATIENT_MATCH_TMP;
		{
			  SELECT_EDX_PATIENT_MATCH_TMP = "SELECT PATIENT_UID,LAST_NM,FIRST_NM,BIRTH_TIME,CURR_SEX_CD,"
				+ "ROOT_EXTENSION_TXT,TYPE_CD,ASSIGNING_AUTHORITY_CD,CODE_DESC_TXT,CODE_SYSTEM_CD,CD from  dbo.EDX_PATIENT_MATCH_TMP";
			  
			 
				
			
		}
		/**
		 * PATIENT_UID NUMBER(20), LAST_NM VARCHAR2(50 BYTE), FIRST_NM
		 * VARCHAR2(50 BYTE), BIRTH_TIME DATE, CURR_SEX_CD CHAR(1 BYTE),
		 * ROOT_EXTENSION_TXT VARCHAR2(100 BYTE), TYPE_CD VARCHAR2(50 BYTE),
		 * ASSIGNING_AUTHORITY_CD VARCHAR2(20 BYTE), CODE_DESC_TXT VARCHAR2(300
		 * BYTE), CODE_SYSTEM_CD VARCHAR2(300 BYTE), CD VARCHAR2(20 BYTE)
		 */
		String INSERT_EDX_PATIENT_MATCH = "INSERT INTO edx_patient_match (PATIENT_UID,MATCH_STRING,TYPE_CD,MATCH_STRING_HASHCODE)  VALUES(?,?,?,?) ";
		con = this.getConnection();
		Statement select = con.createStatement();
		psmt = con.prepareStatement(INSERT_EDX_PATIENT_MATCH);
	    String UPDATE_EDX_IND_PERSON  = "UPDATE PERSON SET edx_ind = 'Y' WHERE person_uid  IN (select Patient_uid from EDX_PATIENT_MATCH)";
		try {
			
				rs = select.executeQuery(SELECT_EDX_PATIENT_MATCH_TMP);
				logger.debug("getting results");
				while (rs.next()) { // process results one row at a time
					PatientHashCodeModel patientHashCodeModel = new PatientHashCodeModel();
					long patientuid = rs.getLong(1);//1
					
					patientHashCodeModel.setPatientuid(patientuid);
					
					String lastNm = rs.getString(2);//2
					patientHashCodeModel.setLastNm(lastNm);
					
					String firstNm = rs.getString(3);//3
					patientHashCodeModel.setFirstNm(firstNm);
					
					Timestamp birthTime = rs.getTimestamp(4);//4
					patientHashCodeModel.setBirthTime(birthTime);
					
					String currSexCd = rs.getString(5);//5
					patientHashCodeModel.setCurrSexCd(currSexCd);
					
					String rootExtensionTxt = rs.getString(6);//6
					patientHashCodeModel.setRootExtensionTxt(rootExtensionTxt);
					
					String typeCd = rs.getString(7);//7
					patientHashCodeModel.setTypeCd(typeCd);
					
					String code = rs.getString(8);//8
					patientHashCodeModel.setCode(code);
					
					String codeDescTxt = rs.getString(9);//8
					patientHashCodeModel.setCodeDescTxt(codeDescTxt);
					
					String codeSystemCd = rs.getString(10);//9
					patientHashCodeModel.setCodeSystemCd(codeSystemCd);
					
					String cd = rs.getString(11);//10
					patientHashCodeModel.setCd(cd);
					
					boolean flag = patientHashCodeModel.generateHash();
					if (flag) {
						// insert in EDX_PATIENT_MATCH Table
						psmt.setLong(1, patientHashCodeModel.getPatientuid());
						psmt.setString(2, patientHashCodeModel.getMatchstring());
						psmt.setString(3, patientHashCodeModel.getCd());
						psmt.setLong(4, patientHashCodeModel.getMatchStringHashCode());
						psmt.executeUpdate();
						
					}
				}
				
						
		}
			catch (Exception e) {   
			logger.error("Error in getting updating hash codes"+e.getMessage(),e);
			throw new NEDSSSystemException(e.getMessage());
		} finally {
				closeResultSet(rs);	
				closeStatement(psmt);
				// update all Person_uid rows EDX_IND flag as 'Y'
				PreparedStatement psmt2 = null;
			    try {
					psmt2 = con.prepareStatement(UPDATE_EDX_IND_PERSON);
					psmt2.executeUpdate();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					logger.error("SQLException ="+e1.getMessage(),e1);
				}

				closeStatement(psmt2);
				releaseConnection(con);

		}

	}
	
	public void updateHashCodesforNOK() throws NEDSSSystemException, SQLException {
		
		PropertyUtil propUtil = PropertyUtil.getInstance();
	//	callPatientNOKHashCodeStoredProc();
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement psmt = null;
		Statement select = null;
	    String SELECT_EDX_NOK_MATCH_TMP;
		{
			 
			SELECT_EDX_NOK_MATCH_TMP  = "SELECT PATIENT_UID,LAST_NM,FIRST_NM,"
					+ "ROOT_EXTENSION_TXT,TYPE_CD,ASSIGNING_AUTHORITY_CD,CODE_DESC_TXT,CODE_SYSTEM_CD,STREET_ADDR1,CITY,STATE,ZIP_CODE,TELEPHONE,CD from dbo.EDX_NOK_MATCH_TMP";
					
			
		}
		
		String INSERT_EDX_PATIENT_MATCH = "INSERT INTO edx_patient_match (PATIENT_UID,MATCH_STRING,TYPE_CD,MATCH_STRING_HASHCODE)  VALUES(?,?,?,?) ";
		con = this.getConnection();
		select = con.createStatement();
		psmt = con.prepareStatement(INSERT_EDX_PATIENT_MATCH);
	    String UPDATE_EDX_IND_PERSON  = "UPDATE PERSON SET edx_ind = 'Y' WHERE person_uid  IN (select Patient_uid from EDX_PATIENT_MATCH)";
		try {
			
				rs = select.executeQuery(SELECT_EDX_NOK_MATCH_TMP );
				logger.debug("getting results");
				while (rs.next()) { // process results one row at a time
					PatientNOKHashCodeModel patientNOKHashCodeModel = new PatientNOKHashCodeModel();
					long patientuid = rs.getLong(1);//1
					patientNOKHashCodeModel.setPatientuid(patientuid);
					
					String lastNm = rs.getString(2);//2
					patientNOKHashCodeModel.setLastNm(lastNm);
					
					String firstNm = rs.getString(3);//3
					patientNOKHashCodeModel.setFirstNm(firstNm);
					
									
					String rootExtensionTxt = rs.getString(4);//4
					patientNOKHashCodeModel.setRootExtensionTxt(rootExtensionTxt);
					
					String typeCd = rs.getString(5);//5
					patientNOKHashCodeModel.setTypeCd(typeCd);
					
					String code = rs.getString(6);//6
					patientNOKHashCodeModel.setCode(code);
					
					String codeDescTxt = rs.getString(7);//7
					patientNOKHashCodeModel.setCodeDescTxt(codeDescTxt);
					
					String codeSystemCd = rs.getString(8);//8
					patientNOKHashCodeModel.setCodeSystemCd(codeSystemCd);
					
					
					String streetaddress1 = rs.getString(9);//9
					patientNOKHashCodeModel.setStreetaddress1(streetaddress1);
					
					String city = rs.getString(10);//10
					patientNOKHashCodeModel.setCity(city);
					
					String state = rs.getString(11);//11
					patientNOKHashCodeModel.setState(state);
					
					String zipcode = rs.getString(12);//12
					patientNOKHashCodeModel.setZipcode(zipcode);
					
					String telephonetxt = rs.getString(13);//13
					patientNOKHashCodeModel.setTelephonetxt(telephonetxt);
					
					String cd = rs.getString(14);//14
					patientNOKHashCodeModel.setCd(cd);
					
					
					
					
					
					boolean flag = patientNOKHashCodeModel.generateHash();
					if (flag) {
						// insert in EDX_PATIENT_MATCH Table
						psmt.setLong(1, patientNOKHashCodeModel.getPatientuid());
						psmt.setString(2, patientNOKHashCodeModel.getMatchstring());
						psmt.setString(3, patientNOKHashCodeModel.getCd());
						psmt.setLong(4, patientNOKHashCodeModel.getMatchStringHashCode());
						psmt.executeUpdate();
						
					}
				}
				
					psmt.close();
					rs.close();
				
						
		}
			catch (Exception e) {   
			logger.error("Error in getting updating hash codes"+e.getMessage(),e);
			throw new NEDSSSystemException(e.getMessage());
		} finally {
				closeResultSet(rs);
				closeStatement(psmt);
				closeStatement(select);
				// update all Person_uid rows EDX_IND flag as 'Y'
				PreparedStatement psmt2 = null;
			     try {
					psmt2 = con.prepareStatement(UPDATE_EDX_IND_PERSON);
					psmt2.executeUpdate();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					logger.error("SQLException ="+e1.getMessage(),e1);
				}
			     
			    closeStatement(psmt2);
			    releaseConnection(con);
		}

	}


// Stored Procedure to create EDX_PATIENT_MATCH_TMP table
public void callPatientNOKHashCodeStoredProc()throws NEDSSSystemException, SQLException {
		Connection con = null;
		PropertyUtil propUtil = PropertyUtil.getInstance();
		CallableStatement proc_stmt = null;
		try {
			con = getConnection();
			{
				
				proc_stmt = con.prepareCall("{ call dbo.usp_EDX_NOK_HashString() }");
			}
			proc_stmt.execute();

		} catch (SQLException ex) {
			logger.error("Error in calling NOK stored procedure"+ex.getMessage(),ex);
			throw new NEDSSSystemException(ex.getMessage());
		} finally {
			closeStatement(proc_stmt);
			releaseConnection(con);
		}
	}

public void callPatientHashCodeStoredProc()throws NEDSSSystemException, SQLException {
	Connection con = null;
	CallableStatement proc_stmt = null;
	PropertyUtil propUtil = PropertyUtil.getInstance();
	try {
		con = getConnection();
		{
			proc_stmt = con.prepareCall("{ call dbo.usp_EDX_Patient_HashString() }");
		}
		proc_stmt.execute();

	} catch (SQLException ex) {
		logger.error("Error in calling patient stored procedure");
		throw new NEDSSSystemException(ex.getMessage());
	} finally {
		closeStatement(proc_stmt);
		releaseConnection(con);
		
	}
}

@SuppressWarnings("unchecked")
public Collection<Object> getPersonByEpilinkCollection(Long publicHealthcaseUID) throws NEDSSSystemException{
	PersonDT personDT = new PersonDT();
    ArrayList<Object>  arrayList = new ArrayList<Object> ();
    arrayList.add(publicHealthcaseUID);
	try {
		arrayList  = (ArrayList<Object> )preparedStmtMethod(personDT, arrayList, SELECT_PERSON_BY_EPILINK_COLLECTION, NEDSSConstants.SELECT);
	} catch (Exception ex) {
		logger.fatal("Exception in getPersonByEpilinkCollection:  ERROR = " + ex.getMessage(),ex);
		throw new NEDSSSystemException(ex.toString());
	}
	
	return arrayList;
}

/*	public String getPersonParentUids(String aQuery) throws Exception {
		try {
			logger.debug(aQuery);
			String UidList = null;
			logger.debug("Begin execution of FindPersonDaoImpl.getPersonParentUids() query :" + aQuery);
			ArrayList<Object> inArrayList = new ArrayList<Object>();
			ArrayList<Object> outArrayList = new ArrayList<Object>();
			ArrayList<Object> arrayList = new ArrayList<Object>();
			inArrayList.add(aQuery);// Query to be executed
			outArrayList.add(java.sql.Types.VARCHAR); // count
			aQuery = aQuery.replace("\"", "");
			String sQuery = "{call GETPERSONUIDS_SP(?,?)}";
			arrayList = (ArrayList<Object>) callStoredProcedureMethod(sQuery, inArrayList, outArrayList);

			if (arrayList != null && arrayList.size() > 0) {
				UidList = ((String) arrayList.get(0));
			}
			logger.debug("End execution of FindPersonDaoImpl.getPersonParentUids() query :" + aQuery);
			logger.info("UidList = " + UidList);
			return UidList;
		} catch (Exception ex) {
			logger.fatal("Exception  = " + ex.getMessage(), ex);
			throw new Exception(ex.toString());
		}
	}
	*/

	public String getPersonParentUids(String aQuery) throws Exception {

		Connection dbConnection = null;
		PreparedStatement preparedStmt = null;
		ResultSet resultSet = null;

		try {
			dbConnection = getConnection();
			preparedStmt = dbConnection.prepareStatement(aQuery);
			resultSet = preparedStmt.executeQuery();
			String result = null;
			while (resultSet.next()) {
				result = resultSet.getString(1);
			}
			return result;
		} catch (Exception e) {
			logger.error("Error in getting the person  UID " + e.getMessage(), e);
			throw new NEDSSSystemException(e.getMessage());
		} finally {
			closeStatement(preparedStmt);
			releaseConnection(dbConnection);
		}
	}

}
