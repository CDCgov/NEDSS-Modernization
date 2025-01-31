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

import gov.cdc.nedss.act.sqlscript.WumSqlQuery;
import gov.cdc.nedss.entity.entityid.dt.EntityIdDT;
import gov.cdc.nedss.entity.person.dt.PersonDT;
import gov.cdc.nedss.entity.person.dt.PersonNameDT;
import gov.cdc.nedss.entity.person.dt.PersonRaceDT;
import gov.cdc.nedss.entity.person.util.DisplayPersonList;
import gov.cdc.nedss.entity.person.vo.PatientRevisionSrchResultVO;
import gov.cdc.nedss.entity.person.vo.PatientSearchVO;
import gov.cdc.nedss.entity.person.vo.PatientSrchResultVO;
import gov.cdc.nedss.entity.person.vo.PersonSearchResultTmp;
import gov.cdc.nedss.entity.person.vo.PersonSearchVO;
import gov.cdc.nedss.entity.person.vo.ProviderSearchVO;
import gov.cdc.nedss.entity.sqlscript.NEDSSSqlQuery;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.locator.dt.EntityLocatorParticipationDT;
import gov.cdc.nedss.locator.dt.PostalLocatorDT;
import gov.cdc.nedss.locator.dt.TeleLocatorDT;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.dao.ObservationSummaryDAOImpl;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.dao.RetrieveSummaryVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.LabReportSummaryVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.ObservationPersonInfoVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.ResultedTestSummaryVO;
import gov.cdc.nedss.proxy.util.PatientHashCodeModel;
import gov.cdc.nedss.proxy.util.PatientNOKHashCodeModel;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dt.EdxPatientMatchDT;
import gov.cdc.nedss.systemservice.nbssecurity.NBSBOLookup;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.DataTables;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.util.StringUtils;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;



public class FindLaboratoryReportDAOImpl extends SearchResultDAOImpl

{

	// For logging
	static final LogUtils logger = new LogUtils(
			FindLaboratoryReportDAOImpl.class.getName());
	static final int MAX_CACHE_COUNT = 105;
	PropertyUtil propertyUtil = PropertyUtil.getInstance();
	
	  private static final String SELECT_PERSON = "SELECT distinct p.person_uid \"personUid\", p.person_parent_uid \"personParentUid\", p.administrative_gender_cd \"administrativeGenderCd\", p.add_reason_cd \"addReasonCd\", p.add_time \"addTime\", p.add_user_id \"addUserId\", p.age_calc \"ageCalc\", p.age_calc_time \"ageCalcTime\", p.age_calc_unit_cd \"ageCalcUnitCd\", p.age_category_cd \"ageCategoryCd\", p.age_reported \"ageReported\", p.age_reported_time \"ageReportedTime\", p.age_reported_unit_cd \"ageReportedUnitCd\", p.as_of_date_admin \"asOfDateAdmin\", p.as_of_date_ethnicity \"asOfDateEthnicity\", p.as_of_date_general \"asOfDateGeneral\", p.as_of_date_morbidity \"asOfDateMorbidity\", p.as_of_date_sex \"asOfDateSex\", p.birth_gender_cd \"birthGenderCd\", p.birth_order_nbr \"birthOrderNbr\", p.birth_time \"birthTime\", p.birth_time_calc \"birthTimeCalc\", p.cd \"cd\", p.cd_desc_txt \"cdDescTxt\", p.curr_sex_cd \"currSexCd\", p.deceased_ind_cd \"deceasedIndCd\", p.deceased_time \"deceasedTime\", p.description \"description\", p.education_level_cd \"educationLevelCd\", p.education_level_desc_txt \"educationLevelDescTxt\", p.electronic_ind \"electronicInd\", p.ethnic_group_ind \"ethnicGroupInd\", p.last_chg_reason_cd \"lastChgReasonCd\", p.last_chg_time \"lastChgTime\", p.last_chg_user_id \"lastChgUserId\", p.marital_status_cd \"maritalStatusCd\", " +
			    " p.marital_status_desc_txt \"maritalStatusDescTxt\", p.mothers_maiden_nm \"mothersMaidenNm\", p.multiple_birth_ind \"multipleBirthInd\", " +
			    "p.occupation_cd \"occupationCd\", p.preferred_gender_cd \"preferredGenderCd\", p.prim_lang_cd \"primLangCd\", p.prim_lang_desc_txt \"primLangDescTxt\", p.record_status_cd \"recordStatusCd\", p.record_status_time \"recordStatusTime\", p.status_cd \"statusCd\", p.status_time \"statusTime\", p.survived_ind_cd \"survivedIndCd\", p.user_affiliation_txt \"userAffiliationTxt\", " +
			    "p.first_nm \"firstNm\", p.last_nm \"lastNm\", p.middle_nm \"middleNm\", p.nm_prefix \"nmPrefix\", p.nm_suffix \"nmSuffix\", p.preferred_nm \"preferredNm\", p.hm_street_addr1 \"hmStreetAddr1\", p.hm_street_addr2 \"hmStreetAddr2\", p.hm_city_cd \"hmCityCd\", p.hm_city_desc_txt \"hmCityDescTxt\", p.hm_state_cd \"hmStateCd\", p.hm_zip_cd \"hmZipCd\", p.hm_cnty_cd \"hmCntyCd\", p.hm_cntry_cd \"hmCntryCd\", p.hm_phone_nbr \"hmPhoneNbr\", p.hm_phone_cntry_cd \"hmPhoneCntryCd\", p.hm_email_addr \"hmEmailAddr\", p.cell_phone_nbr \"cellPhoneNbr\", p.wk_street_addr1 \"wkStreetAddr1\", p.wk_street_addr2 \"wkStreetAddr2\", p.wk_city_cd \"wkCityCd\", p.wk_city_desc_txt \"wkCityDescTxt\", p.wk_state_cd \"wkStateCd\", p.wk_zip_cd \"wkZipCd\", p.wk_cnty_cd \"wkCntyCd\", p.wk_cntry_cd \"wkCntryCd\", p.wk_phone_nbr \"wkPhoneNbr\", p.wk_phone_cntry_cd \"wkPhoneCntryCd\", p.wk_email_addr \"wkEmailAddr\", p.SSN \"SSN\", p.medicaid_num \"medicaidNum\", p.dl_num \"dlNum\", p.dl_state_cd \"dlStateCd\", p.race_cd \"raceCd\", p.race_seq_nbr \"raceSeqNbr\"," +
			    " p.race_category_cd \"raceCategoryCd\", p.ethnicity_group_cd \"ethnicityGroupCd\", p.ethnic_group_seq_nbr \"ethnicGroupSeqNbr\", p.ethnic_group_desc_txt \"ethnicGroupDescTxt\", p.adults_in_house_nbr \"adultsInHouseNbr\", " +
			    "p.children_in_house_nbr \"childrenInHouseNbr\", p.birth_city_cd \"birthCityCd\", p.birth_city_desc_txt \"birthCityDescTxt\", p.birth_cntry_cd \"birthCntryCd\", p.birth_state_cd \"birthStateCd\", p.race_desc_txt \"raceDescTxt\", p.local_id \"localId\", p.version_ctrl_nbr \"versionCtrlNbr\" , p.group_nbr \"groupNbr\" , p.group_time \"groupTime\", p.edx_ind \"edxInd\", p.speaks_english_cd \"speaksEnglishCd\", p.additional_gender_cd \"additionalGenderCd\", p.ehars_id \"eharsId\", p.dedup_match_ind \"dedupMatchInd\" FROM ";
			    
	private static final String SELECT_PERSON_BY_EPILINK_COLLECTION = SELECT_PERSON + "  Act a, Entity e, NBS_act_entity ae, Public_health_case phc, Person p "
			+ "where   phc.public_health_case_uid = a.act_uid and "
			+ "ae.act_uid = a.act_uid and "
			+ "ae.entity_uid = p.person_uid and "
			+ "phc.public_health_case_uid in "
			+ "(select phc.public_health_case_uid "
				+ "from case_management cm, Public_health_case phc "
				+ "where cm.epi_link_id in "
					+ "(select  cm.epi_link_id  "
					+ "from case_management cm "
					+ "where cm.public_health_case_uid = ?) "
				+ "and cm.public_health_case_uid = phc.public_health_case_uid )";
	  

	
	public FindLaboratoryReportDAOImpl() throws NEDSSSystemException {
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
			query.append(NEDSSSqlQuery.BASENAMEQUERYSQL);
			query.append(whereClause);

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
			query.append(NEDSSSqlQuery.BASELOCALIDQUERYSQL);
			query.append(whereClause);

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
			query.append(NEDSSSqlQuery.BASEADDRESSQUERYSQL);
			query.append(whereClause);
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
				{
					if (find.getBirthTimeOperator().equals("=")) {
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
					if (personLastName.indexOf("'") > 0) {
						specialCharacter = "'";
						personLastName = replaceCharacters(personLastName,
								specialCharacter, "''");
	
					}
					if (oper.equalsIgnoreCase("SL")) // sounds like
						sbuf.append(whereAnd
								+ " (soundex(upper( PN.last_nm))  =  soundex('"
								+ personLastName.toUpperCase() + "'))");
					else if (oper.equalsIgnoreCase("CT")) { // contains
						sbuf.append(whereAnd + "( upper( PN.last_nm)  like '%"
								+ personLastName.toUpperCase() + "%')");
						// sbuf.append(" and PN.nm_use_cd = '0')");
					} else if (oper.equalsIgnoreCase("SW")) { // Starts With
						sbuf.append(whereAnd + "( upper( PN.last_nm)  like '"
								+ personLastName.toUpperCase() + "%')");
						// sbuf.append(" and PN.nm_use_cd = '0')");
					} else {
						sbuf.append(whereAnd + " (upper( PN.last_nm) " + oper
								+ "   '" + personLastName.toUpperCase() + "')");
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
					if (personFirstName.indexOf("'") > 0) {
						specialCharacter = "'";
						personFirstName = replaceCharacters(personFirstName,
								specialCharacter, "''");
	
					}
					if (oper.equalsIgnoreCase("SL")) // sounds like
						sbuf.append(whereAnd
								+ " (soundex(upper( PN.first_nm))  =  soundex('"
								+ personFirstName.toUpperCase() + "'))");
					else if (oper.equalsIgnoreCase("CT")) // contains
						sbuf.append(whereAnd + "( upper( PN.first_nm)  like '%"
								+ personFirstName.toUpperCase() + "%')");
					else if (oper.equalsIgnoreCase("SW")) // Starts With
						sbuf.append(whereAnd + "( upper( PN.first_nm)  like '"
								+ personFirstName.toUpperCase() + "%')");
					else
						sbuf.append(whereAnd + " (upper( PN.first_nm) " + oper
								+ "   '" + personFirstName.toUpperCase() + "')");
	
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
					if (streetAddress.indexOf("'") > 0) {
						specialCharacter = "'";
						streetAddress = replaceCharacters(streetAddress,
								specialCharacter, "''");
	
					}
					if (oper.equalsIgnoreCase("SL"))
						sbuf.append(whereAnd
								+ " soundex (upper(PL.street_addr1)) = soundex('"
								+ streetAddress.toUpperCase() + "')");
					else if (oper.equalsIgnoreCase("CT")) // contains
						sbuf.append(whereAnd + " upper( PL.street_addr1)  like '%"
								+ streetAddress.toUpperCase() + "%'");
					else
						sbuf.append(whereAnd + " upper(PL.street_addr1) "
								+ oper.toUpperCase() + " '"
								+ streetAddress.toUpperCase() + "'");
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
								+ " soundex (upper(PL.city_desc_txt)) = soundex('"
								+ find.getCityDescTxt().trim().toUpperCase() + "')");
					else if (oper.equalsIgnoreCase("CT")) // contains
						sbuf.append(whereAnd + " upper( PL.city_desc_txt)  like '%"
								+ find.getCityDescTxt().trim().toUpperCase() + "%'");
					else
						sbuf.append(whereAnd + " upper(PL.city_desc_txt) "
								+ oper.toUpperCase() + " '"
								+ find.getCityDescTxt().trim().toUpperCase() + "'");
	
				}
			}
			if (find.getState() != null) {
				if (find.getState().trim().length() != 0) {
					if (firstWhere)
						firstWhere = false;
					else
						whereAnd = " AND ";
					sbuf.append(whereAnd + " upper(PL.state_cd) =  '"
							+ find.getState().trim().toUpperCase() + "'");
	
				}
			}
			if (find.getZipCd() != null) {
				if (find.getZipCd().trim().length() != 0) {
					if (firstWhere)
						firstWhere = false;
					else
						whereAnd = " AND ";
					sbuf.append(whereAnd + " upper(PL.zip_cd) = '"
							+ find.getZipCd().trim().toUpperCase() + "'");
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
						sbuf.append(whereAnd + " upper( TL.phone_nbr_txt)  like '"
								+ formattedPhone.substring(0, 4).toUpperCase()
								+ "%'" + " and upper( TL.phone_nbr_txt)  like '%"
								+ formattedPhone.substring(5, 9).toUpperCase()
								+ "%'");
					} else if (formattedPhone.indexOf("-") == 3
							&& formattedPhone.lastIndexOf("-") == 3
							&& formattedPhone.length() == 4) {
						sbuf.append(whereAnd + " upper( TL.phone_nbr_txt)  like '"
								+ formattedPhone.trim().toUpperCase() + "%'");
					} else
						sbuf.append(whereAnd + " upper( TL.phone_nbr_txt)  like '%"
								+ formattedPhone.trim().toUpperCase() + "%'");
				}
	
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
						if (rootExtension.indexOf("'") > 0) {
							specialCharacter = "'";
							rootExtension = replaceCharacters(rootExtension,
									specialCharacter, "''");
						}
						if (oper.length() != 0)
							sbuf.append("AND (EI.TYPE_CD = '"
									+ find.getTypeCd().trim() + "')");
						sbuf.append("AND  upper( EI.root_extension_txt) = '"
								+ rootExtension.toUpperCase() + "'  ");
	
					} // end of 2nd if
				}
			}
	
			if (find.getSsn() != null) {
	
				if (find.getSsn().trim().length() != 0) {
					// There is no operator for ID and value; take default
					String oper = "=";
					String specialCharacter;
					String entityid = find.getSsn().trim();
	
					if (entityid.indexOf("'") > 0) {
						specialCharacter = "'";
						entityid = replaceCharacters(entityid, specialCharacter, "''");
					}
					if (oper.length() != 0)
						sbuf.append("AND (EI.TYPE_CD = 'SS')");
					sbuf.append("AND  upper( EI.root_extension_txt) = '"
							+ entityid.toUpperCase() + "'  ");
	
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


	private String constructUidWhereInClause(String fieldName,
			ArrayList<Object> uidsStringArray) {
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
	 
		subQuery = "(select local_id from Public_health_case"
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
				whereClause = buildNameWhereClause(find);
				whereClause += find.getDataAccessWhereClause();
				whereClause += orderByRule;
				personNameColl = runNameQuery(whereClause);
	
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
				whereClause = buildPersonBasicWhereClause(find);
				whereClause += find.getDataAccessWhereClause();
				whereClause += orderByRuleForBasic;
				personBasicColl = runPersonBasicQuery(whereClause);
	
			}
			if ((find.getBirthTime() != null && find.getBirthTime().trim().length() != 0)
					|| (find.getCurrentSex() != null && find.getCurrentSex().trim()
							.length() != 0)
					|| (find.getLocalID() != null && find.getLocalID().trim()
							.length() != 0)) {
				whereClause = buildPersonBasicWhereClause(find);
				whereClause += find.getDataAccessWhereClause();
				whereClause += orderByRuleForBasic;
				personBasicColl = runPersonBasicQuery(whereClause);
	
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
			}
			// Has user entered telephone number for search
			if (find.getPhoneNbrTxt() != null
					&& find.getPhoneNbrTxt().trim().length() != 0) {
				whereClause = buildTeleWhereClause(find);
				// whereClause = whereClause + buildStatusWhereClause(find);
				whereClause += find.getDataAccessWhereClause();
				personTeleColl = runTeleQuery(whereClause);
					
			}
	
			// Has user entered any EntityId information
			if ((find.getRootExtensionTxt() != null && find.getRootExtensionTxt()
					.trim().length() != 0)
					|| (find.getSsn() != null && find.getSsn().trim().length() != 0)) {
				whereClause = buildEntityIdWhereClause(find);
				// whereClause = whereClause + buildStatusWhereClause(find);
				whereClause += find.getDataAccessWhereClause();
				personEntityIdColl = runEntityIdQuery(whereClause);
				
			}
			/* Has user entered Ethnicity */
			if (find.getEthnicGroupInd() != null
					&& find.getEthnicGroupInd().trim().length() != 0) {
				whereClause = buildEthnicityWhereClause(find);
				// whereClause = whereClause + buildStatusWhereClause(find);
				whereClause += find.getDataAccessWhereClause();
				personEthnicityColl = runEthnicityQuery(whereClause);
			}
			/* Has user entered Race */
			if (find.getRaceCd() != null && find.getRaceCd().trim().length() != 0) {
				whereClause = buildRaceWhereClause(find);
				// whereClause = whereClause + buildStatusWhereClause(find);
				whereClause += find.getDataAccessWhereClause();
				personRaceColl = runRaceQuery(whereClause);
				
			}
			/* Has user entered Role */
			if (find.getRole() != null && find.getRole().trim().length() != 0) {
				whereClause = buildRoleWhereClause(find);
				// whereClause = whereClause + buildStatusWhereClause(find);
				whereClause += find.getDataAccessWhereClause();
				personRoleColl = runRoleQuery(whereClause);
				
			}
			/* Find a list of unique PersonParentUIDs from all the queries */
			ArrayList<Object> parentUidList = filterForUniquePersonParentUIDs(
					personBasicColl, personNameColl, personAddressColl,
					personTeleColl, personEntityIdColl, personEthnicityColl,
					personRaceColl, personRoleColl);
	
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
				
				Iterator<Object> teleItr = personTeleColl.iterator();
				while (teleItr.hasNext()) {
					PersonSearchResultTmp tmp = (PersonSearchResultTmp) teleItr
							.next();
					if (tmp.getPersonUid().equals(personParentUid)) {
						if (tmp.getClassCd() != null) {
							addTeleLocatorDTObjectTo(locatorList, tmp);
	
						
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


	private ArrayList<Object> filterForUniquePersonParentUIDs(
			ArrayList<Object> personBasicColl,
			ArrayList<Object> personNameColl,
			ArrayList<Object> personAddressColl,
			ArrayList<Object> personTeleColl,
			ArrayList<Object> personEntityIdColl,
			ArrayList<Object> personEthnicityColl,
			ArrayList<Object> personRaceColl, ArrayList<Object> personRoleColl) {
		ArrayList<Object> list = new ArrayList<Object>();
		list = (ArrayList<Object>) getDistinctIDs(personBasicColl,
				personAddressColl);
		list = (ArrayList<Object>) getDistinctIDs(personNameColl, list);
		list = (ArrayList<Object>) getDistinctIDs(personTeleColl, list);
		list = (ArrayList<Object>) getDistinctIDs(personEntityIdColl, list);
		list = (ArrayList<Object>) getDistinctIDs(personEthnicityColl, list);
		list = (ArrayList<Object>) getDistinctIDs(personRaceColl, list);
		list = (ArrayList<Object>) getDistinctIDs(personRoleColl, list);

		ArrayList<Object> idList = new ArrayList<Object>();
		
		try{
			if (list != null) {

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
			PersonRaceDT raceDT = new PersonRaceDT();
			raceDT.setPersonUid(tmp.getPersonUid());
			raceDT.setRaceCd(tmp.getRaceCd());
			raceDT.setRaceDescTxt(tmp.getRaceDescTxt());
			raceList.add(raceDT);
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}

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
						}
					}
				} 
				Iterator<Object> teleItr = personTeleColl.iterator();
				while (teleItr.hasNext()) {
					EntityLocatorParticipationDT entityLocatorDT = new EntityLocatorParticipationDT();
					PersonSearchResultTmp tmp = (PersonSearchResultTmp) teleItr
							.next();
					if (tmp.getPersonUid().equals(patientRevisionUid)) {
						if (tmp.getClassCd() != null) {
							addTeleLocatorDTObjectTo(locatorList, tmp);
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
		try{
			list = (ArrayList<Object>) getPersonUIDs(personBasicColl,
					personNameColl);
			list = (ArrayList<Object>) getPersonUIDs(personAddressColl, list);
			list = (ArrayList<Object>) getPersonUIDs(personTeleColl, list);
			list = (ArrayList<Object>) getPersonUIDs(personEntityIdColl, list);
	
	
			if (list != null) {
				Iterator<Object> itr = list.iterator();
				while (itr.hasNext()) {
					PersonSearchResultTmp tmp = (PersonSearchResultTmp) itr.next();
					if (tmp != null) {
						Long currentParentUid = tmp.getPersonParentUid();
						Long currentPersonUid = tmp.getPersonUid();
	
						if (currentParentUid.compareTo(personParentUid) == 0
								&& currentParentUid.compareTo(currentPersonUid) != 0) {
							revisionIdList.add(currentPersonUid);
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
			} else
				formattedPhoneNumber = phoneNumber;
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return formattedPhoneNumber;

	}

	private int getReturnedPatientNumber() {
		if (propertyUtil.getLabNumberOfRows() != 0) {
			return propertyUtil.getLabNumberOfRows();
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

			FindLaboratoryReportDAOImpl search = new FindLaboratoryReportDAOImpl();
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

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public ArrayList<Object> findPatientsByKeyWords(PatientSearchVO find,
			int cacheNumber, int fromIndex, NBSSecurityObj nbsSecurityObj) {
		// nclogger.info("--" + new java.util.Date().getTime() +
		// "--DAO:findPatientsByKeyWords4 : START");

		ArrayList<Object> result = new ArrayList<Object>();
		try{
			
			
			//HashMap<Object, Object> revisionsWithMprs = new HashMap<Object, Object>(); // key
																						// is
																						// person_uid,
																						// value
																						// is
																						// person_parent_uid
	
			//if (cacheNumber <= 0) {
				// if cacheNumber if not specified by the client, set it to the max
				// number of records to be
				// returned by the SQL select statement
				cacheNumber = this.getReturnedPatientNumber();
			//}
	
			ArrayList<Object> queries;
			//if(find.getReportType()!=null && find.getReportType().equalsIgnoreCase("I"))//Investigation
			//	queries= buildSearchQueryInvestigation(find, cacheNumber, nbsSecurityObj);
		//	else if(find.getReportType()!=null && find.getReportType().equalsIgnoreCase("LR"))//Laboratory Report
				queries= buildSearchQueryLaboratoryReport(find, cacheNumber, nbsSecurityObj);
		//	else
			//	queries= buildSearchQuery(find, cacheNumber);
			//String countSearchQuery = "";
			String finalQuery = "";
		//	String countDistinctQuery = "";
			if (queries != null && queries.size() > 1) //{
				//countSearchQuery = (String) queries.get(0);
				finalQuery = (String) queries.get(1);
				//countDistinctQuery = (String) queries.get(2);
//			} else {
//				//countSearchQuery = "SELECT COUNT(*) from PERSON WHERE 1=0";
//				//countDistinctQuery = "SELECT DISTINCT COUNT(*) from PERSON WHERE 1=0";
//			}
	
			// nclogger.info("SEARCH QUERY: " + searchQuery);
			// nclogger.info("COUNT SEARCH QUERY: " + countSearchQuery);
			PersonSearchResultTmp searchResultName = new PersonSearchResultTmp();
//			PersonConditionDT perconDT = new PersonConditionDT();
//			Integer intTotalCount = null;
					//(Integer) preparedStmtMethod(searchResultName,
					//null, countSearchQuery, NEDSSConstants.SELECT_COUNT);
			// nclogger.info("COUNT :" + intTotalCount);
			//Integer intCountDistinct = (Integer) preparedStmtMethod(searchResultName,
			//		null, countDistinctQuery, NEDSSConstants.SELECT_COUNT);
			
			ArrayList<Object> personVOs = null;
			//ArrayList<?> personConditionDTs = null;
	
//			if (intTotalCount == null || intTotalCount.intValue() <= 0) {
//				// if count query returned less than one item, there is
//				// no need to waiste time performing the full search/retrieval
//				personVOs = new ArrayList<Object>();
//				intTotalCount = new Integer(0);
//			} else {
		
				ArrayList<?> searchResult = (ArrayList<?>) preparedStmtMethod(
						searchResultName, null, finalQuery, NEDSSConstants.SELECT);
	
				// assemble VOs into the form expected by the client (mprs, include
				// revisions, etc)

					personVOs= assembleLabReportData(searchResult, nbsSecurityObj);

			//}
	
			DisplayPersonList displayPersonList = new DisplayPersonList(
					personVOs.size(), personVOs, fromIndex,
					personVOs.size());
			result.add(displayPersonList);
			result.add(finalQuery);

		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return result;
	}

	private String finishBuildingSearchQueryLabReport(String nestedQuery, int cacheNumber) {
		StringBuffer  buffer =new StringBuffer();
		try{
			//query.append("select distinct p.person_uid \"personUid\", p.person_parent_uid \"personParentUid\" from person p, ");
	
			/*ORACLE NO LONGER SUPPORTED!!!!
			 * if (propertyUtil.getDatabaseServerType() != null
					&& propertyUtil.getDatabaseServerType().equalsIgnoreCase(
							NEDSSConstants.ORACLE_ID)) {
				query.append(nestedQuery);
				query.append(" and rownum between 1 and ");
				query.append(cacheNumber);
				//query.append(" ) qq ");
			} else // SQL Server
			{
				query.append(" select distinct top ");
				query.append(cacheNumber); 
				query.append(nestedQuery.substring("select".length()+1));
			}*/
			String whereObservationClause=nestedQuery.substring(nestedQuery.toLowerCase().indexOf("from"), nestedQuery.length());
			whereObservationClause = "select observ.observation_uid from (select  distinct  top "+ cacheNumber +"  obs.observation_uid, obs.rpt_to_state_time " + whereObservationClause;
			if(whereObservationClause.toLowerCase().indexOf("where")==-1)
				whereObservationClause=whereObservationClause.replace("and ((obs.program_jurisdiction_oid ", ") where  (((obs.program_jurisdiction_oid ");
			whereObservationClause= whereObservationClause+ " and obs.ctrl_cd_display_form='LabReport' and obs.record_status_cd<> 'LOG_DEL' order by obs.rpt_to_state_time, obs.observation_uid) observ";
			
			//String uniqueObsUids=getUniqueObservationUids(whereObservationClause);

            int programJDIndex= nestedQuery.toLowerCase().indexOf("and (((obs.program_jurisdiction_oid"); 
            if(programJDIndex == -1) programJDIndex= nestedQuery.toLowerCase().indexOf("and ((obs.program_jurisdiction_oid");
            if(programJDIndex != -1) nestedQuery=nestedQuery.substring(0,programJDIndex);
   		    int personIndex= whereObservationClause.indexOf("inner join person");
            int personLastIndex= whereObservationClause.indexOf("= p.person_uid");
            if(personIndex != -1 && personLastIndex != -1) {
            	String personClause=whereObservationClause.substring(personIndex,personLastIndex+14);
            	whereObservationClause=whereObservationClause.replace(personClause,"");
            }
 			buffer.append(nestedQuery);
			
			if((buffer.toString().indexOf("where"))>0)
				buffer.append(" and part.type_cd='PATSBJ' and obs.observation_uid in (");
			else
				buffer.append(" and part.type_cd='PATSBJ' where obs.observation_uid in (");
				
			buffer.append(whereObservationClause);
			buffer.append(") order by obs.rpt_to_state_time,obs.observation_uid");
			//query.append(" where qq.\"personParentUid\" = p.person_parent_uid ");
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return buffer.toString();
	}
	

	
	private ArrayList<Object> buildSearchQueryLaboratoryReport(PatientSearchVO find,
			int cacheNumber, NBSSecurityObj nbsSecurityObj) {
		ArrayList<Object> result = new ArrayList<Object>();
		try{
			ArrayList<Object> specifiedSearchCriteriaTypes = computeSearchCriteriaTypes(find);
	
			if (specifiedSearchCriteriaTypes != null
					&& !specifiedSearchCriteriaTypes.isEmpty()) {
				int sizeSC = specifiedSearchCriteriaTypes.size();
	
				String labNumberQuery = "";
				String nestedQuery = "";
				String countQuery = "";
				//String countDistinctQuery = "";
	
				for (int i = 0; i < sizeSC; i++) {
					int curSearchDataType = ((Integer) specifiedSearchCriteriaTypes
							.get(i)).intValue();
	
			
					if (i == 0)// first set of search criteria
					{
						nestedQuery = buildFirstNestedQueryLaboratoryReport(curSearchDataType, find, nbsSecurityObj);
					} else// other than first set of search criteria
					{
						nestedQuery = buildNonFirstNestedQueryLaboratoryReport(curSearchDataType,
								find, nestedQuery, i);
					}
					
					if (i == sizeSC - 1)// last set of search criteria
					{
						String temp = nestedQuery;
						
						
						nestedQuery = finishBuildingSearchQueryLabReport(nestedQuery,
								cacheNumber);
//						if(find.getReportType().equalsIgnoreCase("LR")){
							countQuery = finishBuildingSearchCountQueryLaboratoryReport(temp);
						
							//nestedQuery = finishBuildingSearchQuery(nestedQuery,cacheNumber);//TODO: do we need to create a method like this?
//						}
						//	countDistinctQuery = finishBuildingSearchDistinctCountQueryLaboratoryReport(temp);
//						else{
//							
//							nestedQuery = finishBuildingSearchQuery(nestedQuery,
//									cacheNumber);
//							countQuery = finishBuildingSearchCountQuery(temp);
//						}
					}
				}
	
				result.add(countQuery);
				result.add(nestedQuery);
		
				//result.add(countDistinctQuery);
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
		try{
			query.append("select distinct p.person_uid \"personUid\", p.person_parent_uid \"personParentUid\" from person p, ");
			// SQL Server
			{
				query.append(" (select distinct top ");
				query.append(cacheNumber); 
				query.append(" \"personParentUid\" , \"parentLastName\", \"parentFirstName\", \"parentBirthTimeCalc\" from person p, ");
				query.append(" ( ");
				query.append(nestedQuery);
				query.append(" ) derivedTable ");
				query.append(" where p.person_uid=p.person_parent_uid and p.person_parent_uid=\"personParentUid\" ");
				query.append(" order by \"parentLastName\", \"parentFirstName\", \"parentBirthTimeCalc\" ");
				query.append(") qq ");
			}
	
			query.append(" where qq.\"personParentUid\" = p.person_parent_uid ");
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
				query.append(" select count(*) from ");
				query.append("(select distinct \"personParentUid\" from ");
				query.append(" ( ");
				query.append(nestedQuery);
				query.append(") derivedTable ) t ");
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return query.toString();
	}

//	private String finishBuildingSearchCountQueryInvestigation(String nestedQuery) {
//		StringBuffer query = new StringBuffer();
//		try{
//			if (propertyUtil.getDatabaseServerType() != null
//					&& propertyUtil.getDatabaseServerType().equalsIgnoreCase(
//							NEDSSConstants.ORACLE_ID)) {
//				query.append(" select count(*) from ");
//			//	query.append("(select distinct \"personParentUid\" from ");
//				query.append(" ( ");
//				query.append(nestedQuery);
//				query.append(") derivedTable");
//			} else// SQL server
//			{
//				query.append(" select count(*) from ");
//				//query.append("(select distinct \"personParentUid\" from ");
//				query.append(" ( ");
//				query.append(nestedQuery);
//				query.append(") derivedTable");
//			}
//		}catch(Exception ex){
//			logger.fatal("Exception = "+ex.getMessage(), ex);
//			throw new NEDSSSystemException(ex.toString());
//		}
//		return query.toString();
//	}
	
	private String finishBuildingSearchCountQueryLaboratoryReport(String nestedQuery) {
		StringBuffer query = new StringBuffer();
		try{
			
				query.append(" select count(*) from ");
				query.append("(select distinct \"ObservationUid\" from ");
				query.append(" ( ");
				query.append(nestedQuery);
				query.append(") derivedTable) derivedTable1");
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return query.toString();
	}
	
	private String buildNonFirstNestedQueryLaboratoryReport(int searchDataType,
			PatientSearchVO find, String nestedQuery, int seqNumber) {
		String result = "";
		try{
			//String nqDerivedTableName = "nq" + seqNumber;
			//String wqDerivedTableName = "wq" + seqNumber;
			//String wrappedNestedQuery = wrapNestedQueryLabReport(nestedQuery,
					//nqDerivedTableName);//TODO: change this
	
			switch (searchDataType) {
			case DT_LAB_REPORT_CRITERIA:
				result = buildLabReportCriteriaNestedQueryLabReport(find, nestedQuery);
				break;
			case DT_LAB_REPORT:
				result = buildLabreportCriteriaNestedQueryLabReport(find, nestedQuery);
				break;
			case DT_ACCESSION_NUMBER:
				result = buildAccessionNumberCriteriaNestedQueryLabReport(find, nestedQuery);
				break;
			case DT_DATE_REPORT:
				result = buildDateReportCriteriaNestedQueryLabReport(find, nestedQuery);
				break;
			case DT_RECEIVED_BY_PUBLIC_HEALTH_DATE:
				result = buildDateReceivedPublicHealthCaseCriteriaNestedQueryLabReport(find, nestedQuery);
				break;
			case DT_SPECIMEN_COLLECTION_DATE:
				result = buildSpecimenCollectionDateCriteriaNestedQueryLabReport(find, nestedQuery);
				break;
			case DT_LAB_REPORT_CREATE_DATE:
				result = buildLabReportCreateDateCriteriaNestedQueryLabReport(find, nestedQuery);
				break;
			case DT_LAB_REPORT_LAST_UPDATE_DATE:
				result = buildLabReportLastUpdatedCriteriaNestedQueryLabReport(find, nestedQuery);
				break;
			case DT_INTERNAL_EXTERNAL_USER:
				result = buildInternalExternalUserCriteriaNestedQueryLabReport(find, nestedQuery);
				break;
			case DT_EVENT_CREATED_UPDATED_BY_USER:
				result = buildCreatedUpdatedUserCriteriaNestedQueryLabReport(find, nestedQuery);
				break;
			case DT_REPORTING_FACILITY:
				result = buildReportingFacilityCriteriaNestedQueryLabReport(find, nestedQuery);
				break;	
			case DT_ORDERING_FACILITY:
				result = buildOrderingFacilityCriteriaNestedQueryLabReport(find, nestedQuery);
				break;	
			case DT_ORDERING_PROVIDER:
				result = buildOrderingProviderCriteriaNestedQueryLabReport(find, nestedQuery);
				break;
			case DT_CODED_RESULT_ORGANISM:
				result = buildCodedResultOrganismCriteriaNestedQueryLabReport(find, nestedQuery);
				break;	
			case DT_RESULTED_TEST:
				result = buildResultedTestCriteriaNestedQueryLabReport(find, nestedQuery);
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
		select.append(".\"personParentUid\", ");
		select.append(wqDerivedTableName);
		select.append(".\"parentLastName\", ");
		select.append(wqDerivedTableName);
		select.append(".\"parentFirstName\", ");
		select.append(wqDerivedTableName);
		select.append(".\"parentBirthTimeCalc\" ");

		return select.toString();
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
				// Start DOB where clause for Oracle
			// Start DOB Where Clause for Sql Server
				
					if (find.getBirthTimeOperator().equals("=")) {
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
				// End DOB where clause for Sql Server
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

	/*
	 private String buildIdsNestedQuery(PatientSearchVO find,
			String nestedQuery) {
		StringBuffer query = new StringBuffer("");

		query.append(buildNestedQuerySelect(wqDerivedTableName));

		query.append(" from entity_id ei, ( ");
		query.append(nestedQuery);
		//query.append(" ) ");
		query.append(wqDerivedTableName);

		query.append(" where ");
		query.append(wqDerivedTableName);
		query.append(".\"personUid\" = ");
		query.append("ei.entity_uid and ");
		query.append(buildEntityIdSearchWhereClause(find));
		query.append(" and ei.status_cd='A' ");

		return query.toString();
	}
*/
	private String buildEntityIdSearchWhereClause(PatientSearchVO find) {
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
				strbResult.append("AND  upper(EI.root_extension_txt) like '%"
						+ rootExtension.toUpperCase() + "%' ");
				
			}
			
			if ((find.getRootExtensionTxt()) != null && (find.getTypeCd() == null)) {
				// There is no operator for ID and value; take default
				String idValueNumber = find.getRootExtensionTxt().trim();
	
				if (idValueNumber.indexOf("'") > 0) {
					idValueNumber = replaceCharacters(idValueNumber,
							specialCharacter, "''");
				}
	
				strbResult.append("AND  (upper(EI.root_extension_txt) like '%"
						+ idValueNumber.toUpperCase() + "%' ");
				strbResult.append("OR  upper(p.ehars_id) like '%"
						+ idValueNumber.toUpperCase() + "%') ");
			}
	
			if ((find.getSsn() != null) && (find.getSsn().trim().length() != 0)) {
				// There is no operator for ID and value; take default
				String ssn = find.getSsn().trim();
	
				if (ssn.indexOf("'") > 0) {
					ssn = replaceCharacters(ssn, specialCharacter, "''");
				}
	
				strbResult.append("AND (E2.TYPE_CD = 'SS')");
				strbResult.append("AND  upper(E2.root_extension_txt) like '%"
						+ ssn.toUpperCase() + "%' ");
			}
			// nclogger.info("--" + new java.util.Date().getTime() +
			// "--DAO:buildEntityIdSearchWhereClause : END");
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return strbResult.toString();
	}


	private String buildLabReportCriteriaFirstQueryLabReport(PatientSearchVO find, NBSSecurityObj nbsSecurityObj) {
		StringBuffer query = new StringBuffer();
        String myPAInvestigationsSecurity = propertyUtil.getMyProgramAreaSecurity();
        //String dataAccessWhereClause = nbsSecurityObj.getDataAccessWhereClause(NBSBOLookup.INVESTIGATION, myPAInvestigationsSecurity, DataTables.PUBLIC_HEALTH_CASE_TABLE);
		
        int labCountFix = propertyUtil.getLabCount();
        
        String labDataAccessWhereClause = nbsSecurityObj.
                getDataAccessWhereClause(NBSBOLookup.OBSERVATIONLABREPORT,
                		NEDSSConstants.VIEW);//TODO: review this

           // labDataAccessWhereClause = labDataAccessWhereClause != null ? "AND " + labDataAccessWhereClause : "";
            labDataAccessWhereClause = labDataAccessWhereClause.replaceAll("program_jurisdiction_oid", "obs.program_jurisdiction_oid");
            labDataAccessWhereClause = labDataAccessWhereClause.replaceAll("shared_ind", "obs.shared_ind");
            
           
            //add Filter Processed/UnProcessed to query
            
            String processedStateWhereClause="";
            
    		if(null != find && (null != find.getProcessedState() && "true".equalsIgnoreCase(find.getProcessedState())) 
        			&& (null == find.getUnProcessedState() || "false".equalsIgnoreCase(find.getUnProcessedState()))){
				processedStateWhereClause= "(obs.record_status_cd='PROCESSED')";
        		
        	} else if( null != find && (null == find.getProcessedState() || "false".equalsIgnoreCase(find.getProcessedState())) 
        			&& (null != find.getUnProcessedState() && "true".equalsIgnoreCase(find.getUnProcessedState()))) {
        		processedStateWhereClause="(obs.record_status_cd='UNPROCESSED')";
        	} else {
        		processedStateWhereClause= "(obs.record_status_cd in ('PROCESSED', 'UNPROCESSED'))";
        	}
			
       	
	        query.append(WumSqlQuery.SELECT_LAB_REPORTS_EVENT_SEARCH_SQL_BY_PROCESS_STATE +
	        	    "and ("+//TODO: review this
	        	    processedStateWhereClause+" and " +labDataAccessWhereClause);
	                   
      
	    	query.append(buildLaboratoryReportCriteriaSearchWhereClause(find)+")");
	    //" order by obs.rpt_to_state_time, obs.observation_uid");
       
		return query.toString();
	}
	
	/**
	 * buildABCCaseQueryInvestigation:
	 * @param find
	 * @param nbsSecurityObj
	 * @return
	 */
	StringBuffer query = new StringBuffer();
	
	
	private String buildLabReportIdCriteriaFirstQueryLabReport(PatientSearchVO find, NBSSecurityObj nbsSecurityObj) {
		StringBuffer query = new StringBuffer();
        String myPAInvestigationsSecurity = propertyUtil.getMyProgramAreaSecurity();
        //String dataAccessWhereClause = nbsSecurityObj.getDataAccessWhereClause(NBSBOLookup.INVESTIGATION, myPAInvestigationsSecurity, DataTables.PUBLIC_HEALTH_CASE_TABLE);
		
        int labCountFix = propertyUtil.getLabCount();
        
        String labDataAccessWhereClause = nbsSecurityObj.
                getDataAccessWhereClause(NBSBOLookup.OBSERVATIONLABREPORT,
                		NEDSSConstants.VIEW);//TODO: review this

           // labDataAccessWhereClause = labDataAccessWhereClause != null ? "AND " + labDataAccessWhereClause : "";
            labDataAccessWhereClause = labDataAccessWhereClause.replaceAll("program_jurisdiction_oid", "obs.program_jurisdiction_oid");
            labDataAccessWhereClause = labDataAccessWhereClause.replaceAll("shared_ind", "obs.shared_ind");
            
           query.append(WumSqlQuery.SELECT_LAB_REPORTS_EVENT_SEARCH_SQL +
	        	    "and ("+//TODO: review this
	                    labDataAccessWhereClause);
	        
	    	query.append(buildLabReportSearchWhereClause(find)+")");
	    //" order by obs.rpt_to_state_time, obs.observation_uid");
        
		return query.toString();
	}
	
	private String buildAccesionNumberCriteriaFirstQueryLabReport(PatientSearchVO find, NBSSecurityObj nbsSecurityObj) {
		StringBuffer query = new StringBuffer();
        String myPAInvestigationsSecurity = propertyUtil.getMyProgramAreaSecurity();
        //String dataAccessWhereClause = nbsSecurityObj.getDataAccessWhereClause(NBSBOLookup.INVESTIGATION, myPAInvestigationsSecurity, DataTables.PUBLIC_HEALTH_CASE_TABLE);
		
        int labCountFix = propertyUtil.getLabCount();
        
        String labDataAccessWhereClause = nbsSecurityObj.
                getDataAccessWhereClause(NBSBOLookup.OBSERVATIONLABREPORT,
                		NEDSSConstants.VIEW);//TODO: review this

           // labDataAccessWhereClause = labDataAccessWhereClause != null ? "AND " + labDataAccessWhereClause : "";
            labDataAccessWhereClause = labDataAccessWhereClause.replaceAll("program_jurisdiction_oid", "obs.program_jurisdiction_oid");
            labDataAccessWhereClause = labDataAccessWhereClause.replaceAll("shared_ind", "obs.shared_ind");
            
            
	        query.append(WumSqlQuery.SELECT_LAB_REPORTS_EVENT_SEARCH_SQL +
	        	    "and ("+//TODO: review this
	                    labDataAccessWhereClause);
	    	query.append(buildAccesionNumberSearchWhereClause(find)+")");
		return query.toString();
	}
	

	
	private String buildDateReportCriteriaFirstQueryLabReport(PatientSearchVO find, NBSSecurityObj nbsSecurityObj) {
		StringBuffer query = new StringBuffer();
        String myPAInvestigationsSecurity = propertyUtil.getMyProgramAreaSecurity();
        //String dataAccessWhereClause = nbsSecurityObj.getDataAccessWhereClause(NBSBOLookup.INVESTIGATION, myPAInvestigationsSecurity, DataTables.PUBLIC_HEALTH_CASE_TABLE);
		
        int labCountFix = propertyUtil.getLabCount();
        
        String labDataAccessWhereClause = nbsSecurityObj.
                getDataAccessWhereClause(NBSBOLookup.OBSERVATIONLABREPORT,
                		NEDSSConstants.VIEW);//TODO: review this

           // labDataAccessWhereClause = labDataAccessWhereClause != null ? "AND " + labDataAccessWhereClause : "";
            labDataAccessWhereClause = labDataAccessWhereClause.replaceAll("program_jurisdiction_oid", "obs.program_jurisdiction_oid");
            labDataAccessWhereClause = labDataAccessWhereClause.replaceAll("shared_ind", "obs.shared_ind");
            
	        query.append(WumSqlQuery.SELECT_LAB_REPORTS_EVENT_SEARCH_SQL +
	        	    "and ("+//TODO: review this
	                    labDataAccessWhereClause);
	                   
	        
	    	query.append(buildDateReportSearchWhereClause(find)+")");
	    //" order by obs.rpt_to_state_time, obs.observation_uid");
       
		return query.toString();
	}
	private String buildReceivedPublicHealthCaseCriteriaFirstQueryLabReport(PatientSearchVO find, NBSSecurityObj nbsSecurityObj) {
		StringBuffer query = new StringBuffer();
        String myPAInvestigationsSecurity = propertyUtil.getMyProgramAreaSecurity();
        //String dataAccessWhereClause = nbsSecurityObj.getDataAccessWhereClause(NBSBOLookup.INVESTIGATION, myPAInvestigationsSecurity, DataTables.PUBLIC_HEALTH_CASE_TABLE);
		
        int labCountFix = propertyUtil.getLabCount();
        
        String labDataAccessWhereClause = nbsSecurityObj.
                getDataAccessWhereClause(NBSBOLookup.OBSERVATIONLABREPORT,
                		NEDSSConstants.VIEW);//TODO: review this

           // labDataAccessWhereClause = labDataAccessWhereClause != null ? "AND " + labDataAccessWhereClause : "";
            labDataAccessWhereClause = labDataAccessWhereClause.replaceAll("program_jurisdiction_oid", "obs.program_jurisdiction_oid");
            labDataAccessWhereClause = labDataAccessWhereClause.replaceAll("shared_ind", "obs.shared_ind");
            
            
              query.append(WumSqlQuery.SELECT_LAB_REPORTS_EVENT_SEARCH_SQL +
	        	    "and ("+//TODO: review this
	                    labDataAccessWhereClause);
	                   
	        
	    	query.append(buildReceivedPublicHealthCaseSearchWhereClause(find)+")");
	    //" order by obs.rpt_to_state_time, obs.observation_uid");
        
		return query.toString();
	}
	
	private String buildSpecimenCollectionCriteriaFirstQueryLabReport(PatientSearchVO find, NBSSecurityObj nbsSecurityObj) {
		StringBuffer query = new StringBuffer();
        String myPAInvestigationsSecurity = propertyUtil.getMyProgramAreaSecurity();
        //String dataAccessWhereClause = nbsSecurityObj.getDataAccessWhereClause(NBSBOLookup.INVESTIGATION, myPAInvestigationsSecurity, DataTables.PUBLIC_HEALTH_CASE_TABLE);
		
        int labCountFix = propertyUtil.getLabCount();
        
        String labDataAccessWhereClause = nbsSecurityObj.
                getDataAccessWhereClause(NBSBOLookup.OBSERVATIONLABREPORT,
                		NEDSSConstants.VIEW);//TODO: review this

           // labDataAccessWhereClause = labDataAccessWhereClause != null ? "AND " + labDataAccessWhereClause : "";
            labDataAccessWhereClause = labDataAccessWhereClause.replaceAll("program_jurisdiction_oid", "obs.program_jurisdiction_oid");
            labDataAccessWhereClause = labDataAccessWhereClause.replaceAll("shared_ind", "obs.shared_ind");
            
        query.append(WumSqlQuery.SELECT_LAB_REPORTS_EVENT_SEARCH_SQL +
	        	    "and ("+//TODO: review this
	                    labDataAccessWhereClause);
	                   
	    	query.append(buildSpecimenCollectionSearchWhereClause(find)+")");
	    //" order by obs.rpt_to_state_time, obs.observation_uid");
        
		return query.toString();
	}
	
	private String buildLabReportCreateDateCriteriaFirstQueryLabReport(PatientSearchVO find, NBSSecurityObj nbsSecurityObj) {
		StringBuffer query = new StringBuffer();
        String myPAInvestigationsSecurity = propertyUtil.getMyProgramAreaSecurity();
        //String dataAccessWhereClause = nbsSecurityObj.getDataAccessWhereClause(NBSBOLookup.INVESTIGATION, myPAInvestigationsSecurity, DataTables.PUBLIC_HEALTH_CASE_TABLE);
		
        int labCountFix = propertyUtil.getLabCount();
        
        String labDataAccessWhereClause = nbsSecurityObj.
                getDataAccessWhereClause(NBSBOLookup.OBSERVATIONLABREPORT,
                		NEDSSConstants.VIEW);//TODO: review this

           // labDataAccessWhereClause = labDataAccessWhereClause != null ? "AND " + labDataAccessWhereClause : "";
            labDataAccessWhereClause = labDataAccessWhereClause.replaceAll("program_jurisdiction_oid", "obs.program_jurisdiction_oid");
            labDataAccessWhereClause = labDataAccessWhereClause.replaceAll("shared_ind", "obs.shared_ind");
 	        query.append(WumSqlQuery.SELECT_LAB_REPORTS_EVENT_SEARCH_SQL +
	        	    "and ("+//TODO: review this
	                    labDataAccessWhereClause);
	                   
	        
	    	query.append(buildLabReportCreateDateSearchWhereClause(find)+")");
	    //" order by obs.rpt_to_state_time, obs.observation_uid");
      
		return query.toString();
	}
	
	private String buildLabReportLastUpdateDateCriteriaFirstQueryLabReport(PatientSearchVO find, NBSSecurityObj nbsSecurityObj) {
		StringBuffer query = new StringBuffer();
        String myPAInvestigationsSecurity = propertyUtil.getMyProgramAreaSecurity();
        //String dataAccessWhereClause = nbsSecurityObj.getDataAccessWhereClause(NBSBOLookup.INVESTIGATION, myPAInvestigationsSecurity, DataTables.PUBLIC_HEALTH_CASE_TABLE);
		
        int labCountFix = propertyUtil.getLabCount();
        
        String labDataAccessWhereClause = nbsSecurityObj.
                getDataAccessWhereClause(NBSBOLookup.OBSERVATIONLABREPORT,
                		NEDSSConstants.VIEW);//TODO: review this

           // labDataAccessWhereClause = labDataAccessWhereClause != null ? "AND " + labDataAccessWhereClause : "";
            labDataAccessWhereClause = labDataAccessWhereClause.replaceAll("program_jurisdiction_oid", "obs.program_jurisdiction_oid");
            labDataAccessWhereClause = labDataAccessWhereClause.replaceAll("shared_ind", "obs.shared_ind");
            
           query.append(WumSqlQuery.SELECT_LAB_REPORTS_EVENT_SEARCH_SQL +
	        	    "and ("+//TODO: review this
	                    labDataAccessWhereClause);
	                   
	        
	    	query.append(buildLabReportLastUpdateDateSearchWhereClause(find)+")");
	    //" order by obs.rpt_to_state_time, obs.observation_uid");
        
		return query.toString();
	}
	
	private String buildInternalExternalUserCriteriaFirstQueryLabReport(PatientSearchVO find, NBSSecurityObj nbsSecurityObj) {
		StringBuffer query = new StringBuffer();
        //String dataAccessWhereClause = nbsSecurityObj.getDataAccessWhereClause(NBSBOLookup.INVESTIGATION, myPAInvestigationsSecurity, DataTables.PUBLIC_HEALTH_CASE_TABLE);
		
        String labDataAccessWhereClause = nbsSecurityObj.
                getDataAccessWhereClause(NBSBOLookup.OBSERVATIONLABREPORT,
                		NEDSSConstants.VIEW);//TODO: review this

           // labDataAccessWhereClause = labDataAccessWhereClause != null ? "AND " + labDataAccessWhereClause : "";
            labDataAccessWhereClause = labDataAccessWhereClause.replaceAll("program_jurisdiction_oid", "obs.program_jurisdiction_oid");
            labDataAccessWhereClause = labDataAccessWhereClause.replaceAll("shared_ind", "obs.shared_ind");

	        query.append(WumSqlQuery.SELECT_LAB_REPORTS_EVENT_SEARCH_SQL +
	        	    "and ("+//TODO: review this
	                    labDataAccessWhereClause);
	                   
	    	query.append(buildInternalExternalUserSearchWhereClause(find)+")");
	    //" order by obs.rpt_to_state_time, obs.observation_uid");
        
		return query.toString();
	}
	
	private String buildCreatedUpdatedUserCriteriaFirstQueryLabReport(PatientSearchVO find, NBSSecurityObj nbsSecurityObj) {
		StringBuffer query = new StringBuffer();
        
        String labDataAccessWhereClause = nbsSecurityObj.
                getDataAccessWhereClause(NBSBOLookup.OBSERVATIONLABREPORT,
                		NEDSSConstants.VIEW);//TODO: review this

           // labDataAccessWhereClause = labDataAccessWhereClause != null ? "AND " + labDataAccessWhereClause : "";
            labDataAccessWhereClause = labDataAccessWhereClause.replaceAll("program_jurisdiction_oid", "obs.program_jurisdiction_oid");
            labDataAccessWhereClause = labDataAccessWhereClause.replaceAll("shared_ind", "obs.shared_ind");
            
	        query.append(WumSqlQuery.SELECT_LAB_REPORTS_EVENT_SEARCH_SQL +
	        	    "and "+//TODO: review this
	                    labDataAccessWhereClause);
	                   
	    	query.append(buildCreatedUpdatedUserSearchWhereClause(find));
	    //" order by obs.rpt_to_state_time, obs.observation_uid");
       
        
		return query.toString();
	}
	
	private String buildReportingFacilityCriteriaFirstQueryLabReport(PatientSearchVO find, NBSSecurityObj nbsSecurityObj) {
		StringBuffer query = new StringBuffer();
        String labDataAccessWhereClause = nbsSecurityObj.
                getDataAccessWhereClause(NBSBOLookup.OBSERVATIONLABREPORT,
                		NEDSSConstants.VIEW);//TODO: review this

           // labDataAccessWhereClause = labDataAccessWhereClause != null ? "AND " + labDataAccessWhereClause : "";
            labDataAccessWhereClause = labDataAccessWhereClause.replaceAll("program_jurisdiction_oid", "obs.program_jurisdiction_oid");
            labDataAccessWhereClause = labDataAccessWhereClause.replaceAll("shared_ind", "obs.shared_ind");
 	        query.append(WumSqlQuery.SELECT_LAB_REPORTS_EVENT_SEARCH_SQL +
	        	    " and ("+//TODO: review this
	                    labDataAccessWhereClause);
	    	query.append(buildReportingFacilitySearchWhereClause(find)+")");
	    //" order by obs.rpt_to_state_time, obs.observation_uid");
		return query.toString();
	}
	
	private String buildOrderingFacilityCriteriaFirstQueryLabReport(PatientSearchVO find, NBSSecurityObj nbsSecurityObj) {
		StringBuffer query = new StringBuffer();

        String labDataAccessWhereClause = nbsSecurityObj.
                getDataAccessWhereClause(NBSBOLookup.OBSERVATIONLABREPORT,
                		NEDSSConstants.VIEW);//TODO: review this

           // labDataAccessWhereClause = labDataAccessWhereClause != null ? "AND " + labDataAccessWhereClause : "";
            labDataAccessWhereClause = labDataAccessWhereClause.replaceAll("program_jurisdiction_oid", "obs.program_jurisdiction_oid");
            labDataAccessWhereClause = labDataAccessWhereClause.replaceAll("shared_ind", "obs.shared_ind");
            
 	        query.append(WumSqlQuery.SELECT_LAB_REPORTS_EVENT_SEARCH_SQL +
	        	    " and ("+//TODO: review this
	                    labDataAccessWhereClause);
	    	query.append(buildOrderingFacilitySearchWhereClause(find)+")");
		return query.toString();
	}
	
	private String buildOrderingProviderCriteriaFirstQueryLabReport(PatientSearchVO find, NBSSecurityObj nbsSecurityObj) {
		StringBuffer query = new StringBuffer();
         String labDataAccessWhereClause = nbsSecurityObj.
                getDataAccessWhereClause(NBSBOLookup.OBSERVATIONLABREPORT,
                		NEDSSConstants.VIEW);//TODO: review this

           // labDataAccessWhereClause = labDataAccessWhereClause != null ? "AND " + labDataAccessWhereClause : "";
            labDataAccessWhereClause = labDataAccessWhereClause.replaceAll("program_jurisdiction_oid", "obs.program_jurisdiction_oid");
            labDataAccessWhereClause = labDataAccessWhereClause.replaceAll("shared_ind", "obs.shared_ind");

	        query.append(WumSqlQuery.SELECT_LAB_REPORTS_EVENT_SEARCH_SQL +
	        	    "and ("+//TODO: review this
	                    labDataAccessWhereClause);
	                   
	        
	    	query.append(buildOrderingProviderSearchWhereClause(find)+")");
        
        
		return query.toString();
	}
	
	private String buildCodedResultOrganismCriteriaFirstQueryLabReport(PatientSearchVO find, NBSSecurityObj nbsSecurityObj) {
		StringBuffer query = new StringBuffer();
        
        String labDataAccessWhereClause = nbsSecurityObj.
                getDataAccessWhereClause(NBSBOLookup.OBSERVATIONLABREPORT,
                		NEDSSConstants.VIEW);//TODO: review this

           // labDataAccessWhereClause = labDataAccessWhereClause != null ? "AND " + labDataAccessWhereClause : "";
            labDataAccessWhereClause = labDataAccessWhereClause.replaceAll("program_jurisdiction_oid", "obs.program_jurisdiction_oid");
            labDataAccessWhereClause = labDataAccessWhereClause.replaceAll("shared_ind", "obs.shared_ind");
	        query.append(WumSqlQuery.SELECT_LAB_REPORTS_EVENT_SEARCH_SQL +
	        	    "and ("+//TODO: review this
	                    labDataAccessWhereClause);
	    	query.append(buildCodedResultOrganismSearchWhereClause(find)+")");
		return query.toString();
	}
	
	private String buildResultedTestCriteriaFirstQueryLabReport(PatientSearchVO find, NBSSecurityObj nbsSecurityObj) {
		StringBuffer query = new StringBuffer();
        String labDataAccessWhereClause = nbsSecurityObj.
                getDataAccessWhereClause(NBSBOLookup.OBSERVATIONLABREPORT,
                		NEDSSConstants.VIEW);//TODO: review this

           // labDataAccessWhereClause = labDataAccessWhereClause != null ? "AND " + labDataAccessWhereClause : "";
            labDataAccessWhereClause = labDataAccessWhereClause.replaceAll("program_jurisdiction_oid", "obs.program_jurisdiction_oid");
            labDataAccessWhereClause = labDataAccessWhereClause.replaceAll("shared_ind", "obs.shared_ind");
	        query.append(WumSqlQuery.SELECT_LAB_REPORTS_EVENT_SEARCH_SQL +
	        	    "and ("+//TODO: review this
	                    labDataAccessWhereClause);
	                   
	    	query.append(buildResultedTestSearchWhereClause(find)+")");
  	return query.toString();
	}
	
	private String buildLabReportSearchWhereClause(PatientSearchVO find) {
		StringBuffer strbResult = new StringBuffer("");
		try{
			String and = " ";
			String oper = null;
	
			
			//Event ID: Lab ID
			if ((find.getActType() != null)
					&& (find.getActType().trim().equalsIgnoreCase("P10002"))) {
				oper = find.getDocOperator();
				if (oper.length() != 0)
						and = " AND ";
		
				String labId = find.getActId().trim();
				String specialCharacter;
	
				if (labId.indexOf("'") > 0) {
					specialCharacter = "'";
					labId = replaceCharacters(labId,
							specialCharacter, "''");
				}
					
					if(oper.equalsIgnoreCase("=") || oper.equalsIgnoreCase("!="))
						strbResult.append(and +" (obs.local_id " + oper
								+ "   '" + labId + "')");
						else
							if(oper.equalsIgnoreCase("CT"))
								strbResult.append(and  +"(obs.local_id like '%" + labId + "%')");
		
			}

		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return strbResult.toString();
	}
	
	/**
	 * buildAccesionNumberSearchWhereClause:
	 * @param find
	 * @return
	 */
	
	private String buildAccesionNumberSearchWhereClause(PatientSearchVO find) {
		StringBuffer strbResult = new StringBuffer("");
		try{
			String and = " ";
			String oper = null;
	
			
			//Accession Number
			if ((find.getActType() != null)
					&& (find.getActType().trim().length() != 0)) {
				oper = find.getDocOperator();
				if (oper.length() != 0)
					and = " AND ";
		
				String abcCase = find.getActId().trim();
				String specialCharacter;
	
				if (abcCase.indexOf("'") > 0) {
					specialCharacter = "'";
					abcCase = replaceCharacters(abcCase,
							specialCharacter, "''");
				}
				
				if(oper.equalsIgnoreCase("=") || oper.equalsIgnoreCase("!="))
					strbResult.append(and + " obs.observation_uid in (select act_uid from act_id with (nolock) where type_desc_txt ='Filler Number'"
							+ " and root_extension_txt "+oper
							+ "   '" + abcCase.toUpperCase() + "')");
				else//contains
					strbResult.append(and + " obs.observation_uid in (select act_uid from act_id with (nolock) where type_desc_txt ='Filler Number'"
							+ " and root_extension_txt like '%" + abcCase.toUpperCase() + "%')");
					
			}

		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return strbResult.toString();
	}
	
	private String buildDateReportSearchWhereClause(PatientSearchVO find) {
		StringBuffer strbResult = new StringBuffer("");
		try{
			String and = " ";
			String oper = null;
			String dateFrom=null;
			String dateTo=null;
			
			//Date of report
			if (find.getDateType()!=null && !find.getDateType().isEmpty()) {
				oper=find.getDateOperator();
				if (oper.length() != 0) and = " AND ";
				if(oper != null && (NEDSSConstants.ADV_SEARCH_EVENT_TIME_LAST_SIX_MONTHS.equalsIgnoreCase(oper) || NEDSSConstants.ADV_SEARCH_EVENT_TIME_LAST_THIRTY_DAYS.equalsIgnoreCase(oper) || NEDSSConstants.ADV_SEARCH_EVENT_TIME_LAST_SEVEN_DAYS.equalsIgnoreCase(oper))) {
					if(NEDSSConstants.ADV_SEARCH_EVENT_TIME_LAST_SIX_MONTHS.equalsIgnoreCase(oper))	   strbResult.append( and +" obs.ACTIVITY_TO_TIME  >= DATEADD(M, -6, GETDATE())");
					else if(NEDSSConstants.ADV_SEARCH_EVENT_TIME_LAST_THIRTY_DAYS.equalsIgnoreCase(oper)) strbResult.append( and +" obs.ACTIVITY_TO_TIME  >= DATEADD(Day, -30, GETDATE())");
					else strbResult.append( and +" obs.ACTIVITY_TO_TIME  >= DATEADD(Day, -7, GETDATE())");
				} else {
					dateFrom = find.getDateFrom().trim();
					dateTo = find.getDateTo()==null?null: find.getDateTo().trim();
					String specialCharacter;
					
					if (dateFrom.indexOf("'") > 0) {
						specialCharacter = "'";
						dateFrom = replaceCharacters(dateFrom,specialCharacter, "''");
					}
					
					if (dateTo!=null && dateTo.indexOf("'") > 0) {
						specialCharacter = "'";
						dateTo = replaceCharacters(dateTo,specialCharacter, "''");
					}
		

					if(oper.equalsIgnoreCase("=") || oper.equalsIgnoreCase("!="))
						strbResult.append( and + " (format(obs.ACTIVITY_TO_TIME,'MM/dd/yyyy','en-US' )  " + oper + "   '"
							+ dateFrom.toUpperCase() + "')");
					else//Between
						strbResult.append( and +" ( CAST(obs.ACTIVITY_TO_TIME as DATE ) between '"
								+ dateFrom.toUpperCase() + "' and '"+dateTo.toUpperCase()+"')");
				}
			}

		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return strbResult.toString();
	}

	
	private String buildReceivedPublicHealthCaseSearchWhereClause(PatientSearchVO find) {
		StringBuffer strbResult = new StringBuffer("");	
		try{
			String and = " ";
			String oper = null;
			String dateFrom=null;
			String dateTo=null;
			
			//Date of report
			if (find.getDateType()!=null && !find.getDateType().isEmpty()) {
				//oper = "=";
				oper=find.getDateOperator();
				if (oper.length() != 0)
						and = " AND ";
	
				if(oper != null && (NEDSSConstants.ADV_SEARCH_EVENT_TIME_LAST_SIX_MONTHS.equalsIgnoreCase(oper) || NEDSSConstants.ADV_SEARCH_EVENT_TIME_LAST_THIRTY_DAYS.equalsIgnoreCase(oper) || NEDSSConstants.ADV_SEARCH_EVENT_TIME_LAST_SEVEN_DAYS.equalsIgnoreCase(oper))) {
					if(NEDSSConstants.ADV_SEARCH_EVENT_TIME_LAST_SIX_MONTHS.equalsIgnoreCase(oper))	   strbResult.append( and +" obs.RPT_TO_STATE_TIME  >= DATEADD(M, -6, GETDATE())");
					else if(NEDSSConstants.ADV_SEARCH_EVENT_TIME_LAST_THIRTY_DAYS.equalsIgnoreCase(oper)) strbResult.append( and +" obs.RPT_TO_STATE_TIME  >= DATEADD(Day, -30, GETDATE())");
					else strbResult.append( and +" obs.RPT_TO_STATE_TIME  >= DATEADD(Day, -7, GETDATE())");
				} else {
					dateFrom = find.getDateFrom().trim();
					dateTo = find.getDateTo()==null?null: find.getDateTo().trim();
				
					String specialCharacter;
	
					
					if (dateFrom.indexOf("'") > 0) {
						specialCharacter = "'";
						dateFrom = replaceCharacters(dateFrom,
							specialCharacter, "''");
					}
					if (dateTo!=null && dateTo.indexOf("'") > 0) {
						specialCharacter = "'";
						dateTo = replaceCharacters(dateTo,
							specialCharacter, "''");
					}
					if(oper.equalsIgnoreCase("=") || oper.equalsIgnoreCase("!="))
						strbResult.append( and +"(format(obs.RPT_TO_STATE_TIME ,'MM/dd/yyyy','en-US' )  " + oper + "   '"
							+ dateFrom.toUpperCase() + "')");
					else//Between
						strbResult.append( and +" ( CAST(obs.RPT_TO_STATE_TIME  as DATE ) between '"
								+ dateFrom.toUpperCase() + "' and '"+dateTo.toUpperCase()+"')");
			    }
			}

		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return strbResult.toString();
	}

	
	private String buildSpecimenCollectionSearchWhereClause(PatientSearchVO find) {
		StringBuffer strbResult = new StringBuffer("");
		try{
			String and = " ";
			String oper = null;
			String dateFrom=null;
			String dateTo=null;
			
			
			if (find.getDateType()!=null && !find.getDateType().isEmpty()) {
				oper=find.getDateOperator();
				if (oper.length() != 0)  and = " AND ";
				if(oper != null && (NEDSSConstants.ADV_SEARCH_EVENT_TIME_LAST_SIX_MONTHS.equalsIgnoreCase(oper) || NEDSSConstants.ADV_SEARCH_EVENT_TIME_LAST_THIRTY_DAYS.equalsIgnoreCase(oper) || NEDSSConstants.ADV_SEARCH_EVENT_TIME_LAST_SEVEN_DAYS.equalsIgnoreCase(oper))) {
					if(NEDSSConstants.ADV_SEARCH_EVENT_TIME_LAST_SIX_MONTHS.equalsIgnoreCase(oper))	   strbResult.append( and +" obs.EFFECTIVE_FROM_TIME  >= DATEADD(M, -6, GETDATE())");
					else if(NEDSSConstants.ADV_SEARCH_EVENT_TIME_LAST_THIRTY_DAYS.equalsIgnoreCase(oper)) strbResult.append( and +" obs.EFFECTIVE_FROM_TIME  >= DATEADD(Day, -30, GETDATE())");
					else strbResult.append( and +" obs.EFFECTIVE_FROM_TIME  >= DATEADD(Day, -7, GETDATE())");
				} else {
					dateFrom = find.getDateFrom().trim();
					dateTo = find.getDateTo()==null?null: find.getDateTo().trim();
					String specialCharacter;
					if (dateFrom.indexOf("'") > 0) {
						specialCharacter = "'";
						dateFrom = replaceCharacters(dateFrom,specialCharacter, "''");
					}
					if (dateTo!=null && dateTo.indexOf("'") > 0) {
						specialCharacter = "'";
						dateTo = replaceCharacters(dateTo,specialCharacter, "''");
					}
					
					
					if(oper.equalsIgnoreCase("=") || oper.equalsIgnoreCase("!="))
						strbResult.append( and +" (format(obs.EFFECTIVE_FROM_TIME ,'MM/dd/yyyy','en-US' )  " + oper + "   '"
							+ dateFrom.toUpperCase() + "')");
					else//Between
						strbResult.append( and +" ( CAST(obs.EFFECTIVE_FROM_TIME  as DATE ) between '"
								+ dateFrom.toUpperCase() + "' and '"+dateTo.toUpperCase()+"')");
			  }
			}

		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return strbResult.toString();
	}
	
	private String buildLabReportCreateDateSearchWhereClause(PatientSearchVO find) {
		StringBuffer strbResult = new StringBuffer("");
		try{
			String and = " ";
			String oper = null;
			String dateFrom=null;
			String dateTo=null;

			if (find.getDateType()!=null && !find.getDateType().isEmpty()) {
				oper=find.getDateOperator();
				if (oper.length() != 0)  and = " AND ";
				
				if(oper != null && (NEDSSConstants.ADV_SEARCH_EVENT_TIME_LAST_SIX_MONTHS.equalsIgnoreCase(oper) || NEDSSConstants.ADV_SEARCH_EVENT_TIME_LAST_THIRTY_DAYS.equalsIgnoreCase(oper) || NEDSSConstants.ADV_SEARCH_EVENT_TIME_LAST_SEVEN_DAYS.equalsIgnoreCase(oper))) {
					if(NEDSSConstants.ADV_SEARCH_EVENT_TIME_LAST_SIX_MONTHS.equalsIgnoreCase(oper))	   strbResult.append( and +" obs.ADD_TIME  >= DATEADD(M, -6, GETDATE())");
					else if(NEDSSConstants.ADV_SEARCH_EVENT_TIME_LAST_THIRTY_DAYS.equalsIgnoreCase(oper)) strbResult.append( and +" obs.ADD_TIME  >= DATEADD(Day, -30, GETDATE())");
					else strbResult.append( and +" obs.ADD_TIME  >= DATEADD(Day, -7, GETDATE())");
				} else {
					dateFrom = find.getDateFrom().trim();
					dateTo = find.getDateTo()==null?null: find.getDateTo().trim();
					String specialCharacter;
					if (dateFrom.indexOf("'") > 0) {
						specialCharacter = "'";
						dateFrom = replaceCharacters(dateFrom,specialCharacter, "''");
					}
					if (dateTo!=null && dateTo.indexOf("'") > 0) {
						specialCharacter = "'";
						dateTo = replaceCharacters(dateTo,specialCharacter, "''");
					}

					if(oper.equalsIgnoreCase("=") || oper.equalsIgnoreCase("!="))
							strbResult.append( and +" (format(obs.ADD_TIME ,'MM/dd/yyyy','en-US' )  " + oper + "   '"
									+ dateFrom.toUpperCase() + "')");
					else//Between
							strbResult.append( and + " ( CAST(obs.ADD_TIME  as DATE ) between '"
									+ dateFrom.toUpperCase() + "' and '"+dateTo.toUpperCase()+"')");

				}
			}
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return strbResult.toString();
	}
	
	private String buildLabReportLastUpdateDateSearchWhereClause(PatientSearchVO find) {
		StringBuffer strbResult = new StringBuffer("");
		try{
			String and = " ";
			String oper = null;
			String dateFrom=null;
			String dateTo=null;

			if (find.getDateType()!=null && !find.getDateType().isEmpty()) {
				oper=find.getDateOperator();
				if (oper.length() != 0) and = " AND ";
				
				if(oper != null && (NEDSSConstants.ADV_SEARCH_EVENT_TIME_LAST_SIX_MONTHS.equalsIgnoreCase(oper) || NEDSSConstants.ADV_SEARCH_EVENT_TIME_LAST_THIRTY_DAYS.equalsIgnoreCase(oper) || NEDSSConstants.ADV_SEARCH_EVENT_TIME_LAST_SEVEN_DAYS.equalsIgnoreCase(oper))) {
					if(NEDSSConstants.ADV_SEARCH_EVENT_TIME_LAST_SIX_MONTHS.equalsIgnoreCase(oper))	   strbResult.append( and +" obs.LAST_CHG_TIME  >= DATEADD(M, -6, GETDATE())");
					else if(NEDSSConstants.ADV_SEARCH_EVENT_TIME_LAST_THIRTY_DAYS.equalsIgnoreCase(oper)) strbResult.append( and +" obs.LAST_CHG_TIME  >= DATEADD(Day, -30, GETDATE())");
					else strbResult.append( and +" obs.LAST_CHG_TIME  >= DATEADD(Day, -7, GETDATE())");
				} else {
					dateFrom = find.getDateFrom().trim();
					dateTo = find.getDateTo()==null?null: find.getDateTo().trim();
					String specialCharacter;
					if (dateFrom.indexOf("'") > 0) {
						specialCharacter = "'";
						dateFrom = replaceCharacters(dateFrom,specialCharacter, "''");
					}
					if (dateTo!=null && dateTo.indexOf("'") > 0) {
						specialCharacter = "'";
						dateTo = replaceCharacters(dateTo,specialCharacter, "''");
					}
					if(oper.equalsIgnoreCase("=") || oper.equalsIgnoreCase("!="))
							strbResult.append( and+" (format(obs.LAST_CHG_TIME  ,'MM/dd/yyyy','en-US' )  " + oper + "   '"
									+ dateFrom.toUpperCase() + "')");
						else//Between
							strbResult.append( and+" ( CAST(obs.LAST_CHG_TIME   as DATE ) between '"
									+ dateFrom.toUpperCase() + "' and '"+dateTo.toUpperCase()+"')");
				}
			}
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return strbResult.toString();
	}
	
	
	private String buildInternalExternalUserSearchWhereClause(PatientSearchVO find) {
		StringBuffer strbResult = new StringBuffer("");
		try{
			String and = " ";
			String oper = null;
	
			
			//Accession Number
			if (find.getExternalValueSelected().equalsIgnoreCase("false") ||find.getInternalValueSelected().equalsIgnoreCase("false")){
				oper = "=";
				and = " AND ";
		
				String externalUser = find.getExternalValueSelected().trim();
				String specialCharacter;
	
				if (externalUser.indexOf("'") > 0) {
					specialCharacter = "'";
					externalUser = replaceCharacters(externalUser,
							specialCharacter, "''");
				}
				String internalUser = find.getInternalValueSelected().trim();
	
				if (internalUser.indexOf("'") > 0) {
					specialCharacter = "'";
					internalUser = replaceCharacters(internalUser,
							specialCharacter, "''");
				}
				
				if(internalUser.equalsIgnoreCase("false") && externalUser.equalsIgnoreCase("false"))
					strbResult.append(and + " upper(obs.add_user_id) in (select nedss_entry_id from auth_user with (nolock) where user_type <> 'externalUser' and user_type <> 'internalUser' )");
				if(internalUser.equalsIgnoreCase("true") && externalUser.equalsIgnoreCase("false"))
					strbResult.append(and + " upper(obs.add_user_id) in (select nedss_entry_id from auth_user with (nolock) where user_type ='internalUser' and user_type<>'externalUser')");
				if(internalUser.equalsIgnoreCase("false") && externalUser.equalsIgnoreCase("true"))
					strbResult.append(and + " upper(obs.add_user_id) in (select nedss_entry_id from auth_user with (nolock) where user_type <>'internalUser' and user_type='externalUser')");	
			}

		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return strbResult.toString();
	}
	
	private String buildCreatedUpdatedUserSearchWhereClause(PatientSearchVO find) {
		StringBuffer strbResult = new StringBuffer("");
		try{
			String and = " ";
			String oper = null;
	

			
			//Document Created by
			if ((find.getDocumentCreateSelected() != null)
					&& (find.getDocumentCreateSelected().trim().length() != 0)) {
				oper = "=";
				if (oper.length() != 0)
					
						and = " AND ";
		
				String documentCreate = find.getDocumentCreateSelected().trim();
				String specialCharacter;
	
				if (documentCreate.indexOf("'") > 0) {
					specialCharacter = "'";
					documentCreate = replaceCharacters(documentCreate,
							specialCharacter, "''");
				}
					strbResult.append(and + " (upper( obs.add_user_id  ) in (select nedss_entry_id from auth_user with (nolock) where upper(user_id)" + oper
							+ "   '" + documentCreate.toUpperCase() + "'))");
				
			}
			
			//Document Last Updated by
			if ((find.getDocumentUpdateSelected() != null)
					&& (find.getDocumentUpdateSelected().trim().length() != 0)) {
				oper = "=";
				if (oper.length() != 0)

						and = " AND ";
		
				String documentUpdate = find.getDocumentUpdateSelected().trim();
				String specialCharacter;
	
				if (documentUpdate.indexOf("'") > 0) {
					specialCharacter = "'";
					documentUpdate = replaceCharacters(documentUpdate,
							specialCharacter, "''");
				}
					strbResult.append(and + " (upper( obs.last_chg_user_id  ) in (select nedss_entry_id from auth_user with (nolock) where upper(user_id)" + oper
							+ "   '" + documentUpdate.toUpperCase() + "'))");
				
			}
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return strbResult.toString();
	}
	
	private String buildReportingFacilitySearchWhereClause(PatientSearchVO find) {
		StringBuffer strbResult = new StringBuffer("");
		try{
			String and = " ";
			String oper = null;
	
			
			//Reporting Facility
			if ((find.getReportingFacilitySelected() != null)
					&& (find.getReportingFacilitySelected().trim().length() != 0)) {
				oper = "=";
				if (oper.length() != 0)
						and = " AND ";
		
				String reportingFacility = find.getReportingFacilitySelected().trim();
				String specialCharacter;
	
				if (reportingFacility.indexOf("'") > 0) {
					specialCharacter = "'";
					reportingFacility = replaceCharacters(reportingFacility,
							specialCharacter, "''");
				}

				strbResult.append(and + " obs.observation_uid in"
						+ "(select participation.act_uid from participation with (nolock) where participation.type_cd='AUT' and participation.subject_entity_uid" + oper
						+ "   '" + reportingFacility.toUpperCase() + "')");
			}

		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return strbResult.toString();
	}
	
	private String buildOrderingFacilitySearchWhereClause(PatientSearchVO find) {
		StringBuffer strbResult = new StringBuffer("");
		try{
			String and = " ";
			String oper = null;
	
			
			//Ordering Facility
			if ((find.getOrderingFacilitySelected() != null)
					&& (find.getOrderingFacilitySelected().trim().length() != 0)) {
				oper = "=";
				if (oper.length() != 0)
					and = " AND ";
		
				String reportingFacility = find.getOrderingFacilitySelected().trim();
				String specialCharacter;
	
				if (reportingFacility.indexOf("'") > 0) {
					specialCharacter = "'";
					reportingFacility = replaceCharacters(reportingFacility,
							specialCharacter, "''");
				}

				strbResult.append(and + " obs.observation_uid in"
						+ "(select participation.act_uid from participation with (nolock) where participation.type_cd='ORD' and participation.subject_entity_uid" + oper
						+ "   '" + reportingFacility.toUpperCase() + "' and participation.subject_class_cd = 'ORG')");
			}

		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return strbResult.toString();
	}
	
	private String buildOrderingProviderSearchWhereClause(PatientSearchVO find) {
		StringBuffer strbResult = new StringBuffer("");
		try{
			String and = " ";
			String oper = null;
	
			
			//Ordering Provider
			if ((find.getOrderingProviderSelected() != null)
					&& (find.getOrderingProviderSelected().trim().length() != 0)) {
				oper = "=";
				if (oper.length() != 0)
					and = " AND ";
		
				String reportingFacility = find.getOrderingProviderSelected().trim();
				String specialCharacter;
	
				if (reportingFacility.indexOf("'") > 0) {
					specialCharacter = "'";
					reportingFacility = replaceCharacters(reportingFacility,
							specialCharacter, "''");
				}

				strbResult.append(and + " obs.observation_uid in"
						+ "(select participation.act_uid from participation with (nolock) where participation.type_cd='ORD' and participation.subject_entity_uid" + oper
						+ "   '" + reportingFacility.toUpperCase() + "' and participation.subject_class_cd = 'PSN')");
			}

		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return strbResult.toString();
	}
	
	private String buildCodedResultOrganismSearchWhereClause(PatientSearchVO find) {
		StringBuffer strbResult = new StringBuffer("");
		try{
			String oper = null;
			//Coded Result / Organism
			if(!find.getResultDescriptionValue().isEmpty() || !find.getCodeResultOrganismDescriptionValue().isEmpty()){
				oper = find.getCodeResultOrganismDropdown();
				String codedResultOrganism = find.getResultDescriptionValue().trim();
				
				if(codedResultOrganism.isEmpty())
					codedResultOrganism=find.getCodeResultOrganismDescriptionValue();
				
				String specialCharacter;
	
				if (codedResultOrganism.indexOf("'") > 0) {
					specialCharacter = "'";
					codedResultOrganism = replaceCharacters(codedResultOrganism,
							specialCharacter, "''");
				}
				if(oper.equals("=") || oper.equals("!="))
				strbResult.append(" and act.source_act_uid in"
						+ "(select observation_uid from obs_value_coded with (nolock) where upper(display_name) "+oper+"'"+codedResultOrganism.toUpperCase()+"')");
				else
					if(oper.equals("CT"))
						strbResult.append( " and act.source_act_uid in"
								+ "(select observation_uid from obs_value_coded with (nolock) where upper(display_name) like '%"+codedResultOrganism.toUpperCase()+"%')");
					else
						if(oper.equals("NOTNULL"))
							strbResult.append(" and act.source_act_uid in"
									+ "(select observation_uid from obs_value_coded with (nolock) where upper(display_name) <> NULL)");
						else // Starts With
							strbResult.append(" and act.source_act_uid in"
									+ "(select observation_uid from obs_value_coded with (nolock) where upper(display_name) like '"+codedResultOrganism.toUpperCase()+"%')");
							
			}

		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return strbResult.toString();
	}
	
	private String buildResultedTestSearchWhereClause(PatientSearchVO find) {
		StringBuffer strbResult = new StringBuffer("");
		try{
			String and = " ";
			String oper = null;
			if (!find.getResultedTestDescriptionWithCodeValue().isEmpty() || !find.getTestDescription().isEmpty()) {
				oper = find.getResultedTestCodeDropdown();
				if (oper.length() != 0)
					and = " AND ";
		
				String resultedTestDescriptionWithCode = find.getResultedTestDescriptionWithCodeValue().trim();
				String specialCharacter;
	
				if (resultedTestDescriptionWithCode.indexOf("'") > 0) {
					specialCharacter = "'";
					resultedTestDescriptionWithCode = replaceCharacters(resultedTestDescriptionWithCode,
							specialCharacter, "''");
				}
				
				String testDescription = find.getTestDescription().trim();

				if (testDescription.indexOf("'") > 0) {
					specialCharacter = "'";
					testDescription = replaceCharacters(testDescription,
							specialCharacter, "''");
				}
				String description="";
				
				if(!testDescription.isEmpty()){
					int index = testDescription.lastIndexOf("(");
					description=testDescription.substring(0,index-1);
				
				}else
					description=resultedTestDescriptionWithCode;
				
				description=description.toUpperCase();
				if(oper.equals("=") || oper.equals("!="))
				strbResult.append(and + " act.source_act_uid in"
						+ "(select observation_uid from observation with (nolock) where upper(cd_desc_txt) "+oper+"'"+description+"')");
				else
					if(oper.equals("CT"))
						strbResult.append(and + " act.source_act_uid in"
								+ "(select observation_uid from observation with (nolock) where upper(cd_desc_txt) like '%"+description+"%')");
					else
						if(oper.equals("NOTNULL"))
							strbResult.append(and + " act.source_act_uid in"
									+ "(select observation_uid from observation with (nolock) where upper(cd_desc_txt) <> NULL)");
						else // Starts With
							strbResult.append(and + " act.source_act_uid in"
									+ "(select observation_uid from observation with (nolock) where upper(cd_desc_txt) like '"+description+"%')");
							
			}
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return strbResult.toString();
	}
	
	
	private String buildLaboratoryReportCriteriaSearchWhereClause(PatientSearchVO find) {
		StringBuffer strbResult = new StringBuffer("");
		try{
			String and = " ";
			String oper = null;

			//Program Area
			if ((find.getProgramArea() != null
					&& find.getProgramArea().length > 1)
					|| (find.getProgramArea() != null && find.getProgramArea().length == 1
					&& !find.getProgramArea()[0].trim().equals(""))) {
				oper = "=";
				if (oper.length() != 0)
						and = " AND ";
	
				String[] progArea = find.getProgramArea();

				strbResult.append(and);

				String result = ("" + Arrays.asList(progArea)).replaceAll("(^.|.$)", "\'").replace(", ", "\',\'");

				strbResult.append(" obs.prog_area_cd  in (" + result + ")");
			}
			
			
		
			
			//Jurisdiction
			if ((find.getJurisdictionSelected() != null
					&& find.getJurisdictionSelected().length > 1)
					|| (find.getJurisdictionSelected() != null && find.getJurisdictionSelected().length == 1
					&& !find.getJurisdictionSelected()[0].trim().equals(""))) {
				oper = "=";
				if (oper.length() != 0)
						and = " AND ";
		
				String[] jurisdiction = find.getJurisdictionSelected();
				strbResult.append(and);

				String result = ("" + Arrays.asList(jurisdiction)).replaceAll("(^.|.$)", "\'").replace(", ", "\',\'");

				strbResult.append(" obs.jurisdiction_cd  in (" + result + ")");
			
			}
			
			//Pregnancy Status
			if ((find.getPregnantSelected() != null)
					&& (find.getPregnantSelected().trim().length() != 0)) {
				oper = "=";
				if (oper.length() != 0)

						and = " AND ";
		
				String pregnant = find.getPregnantSelected().trim();
				String specialCharacter;
	
				if (pregnant.indexOf("'") > 0) {
					specialCharacter = "'";
					pregnant = replaceCharacters(pregnant,
							specialCharacter, "''");
				}
					strbResult.append(and + " (upper( obs.pregnant_ind_cd) " + oper
							+ "   '" + pregnant.toUpperCase() + "')");
				
			}

	
			//Electronic/Manual
			if ((find.getElectronicValueSelected() != null)
					&& (find.getElectronicValueSelected().equalsIgnoreCase("true"))||
					(find.getManualValueSelected() != null)
					&& (find.getManualValueSelected().equalsIgnoreCase("true")) ||
					(find.getExternalValueSelected() != null)
					&& (find.getExternalValueSelected().equalsIgnoreCase("true"))) {
				oper = "=";
				if (oper.length() != 0)
						and = " AND ";
		
				String electronic = find.getElectronicValueSelected().trim();
				String manual = find.getManualValueSelected().trim();
				String external = find.getExternalValueSelected().trim();
				
				/*if(electronic!=null){
					String specialCharacter;
		
					if (electronic.indexOf("'") > 0) {
						specialCharacter = "'";
						electronic = replaceCharacters(electronic,
								specialCharacter, "''");
					}
				}
				if(manual!=null){
					String specialCharacter;
		
					if (manual.indexOf("'") > 0) {
						specialCharacter = "'";
						manual = replaceCharacters(electronic,
								specialCharacter, "''");
					}
				}*/

				if(electronic.equalsIgnoreCase("true") && manual.equalsIgnoreCase("true") && external.equalsIgnoreCase("true"))
					strbResult.append(and + " (upper( obs.electronic_ind) " + oper
							+ "   'Y' or obs.electronic_ind='N' or obs.electronic_ind='E' )");
					else
						if(!electronic.equalsIgnoreCase("true") && manual.equalsIgnoreCase("true") && external.equalsIgnoreCase("true"))
							strbResult.append(and + " (upper( obs.electronic_ind) " + oper
									+ "   'N' or obs.electronic_ind='E')");
						else 
							if(electronic.equalsIgnoreCase("true") && manual.equalsIgnoreCase("true"))
								strbResult.append(and + " (upper( obs.electronic_ind) " + oper
										+ "   'Y' or obs.electronic_ind='N')");
							else
								if(electronic.equalsIgnoreCase("true") && !manual.equalsIgnoreCase("true"))
									strbResult.append(and + " (upper( obs.electronic_ind) " + oper
											+ "   'Y')");
								else
									if(!electronic.equalsIgnoreCase("true") && manual.equalsIgnoreCase("true"))
										strbResult.append(and + " (upper( obs.electronic_ind) " + oper
												+ "   'N')");

			}
			
			
			//Event Status
			if ((find.getEventStatusInitialSelected() != null)
					|| (find.getEventStatusUpdateSelected() != null)) {
				oper = "=";
				if (oper.length() != 0)
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
					strbResult.append(and + " obs.version_ctrl_nbr >='1'");
				else
					if (newInitial.equalsIgnoreCase("true"))
						strbResult.append(and + " obs.version_ctrl_nbr = '1'");
					else
						if (update.equalsIgnoreCase("true"))
							strbResult.append(and + " obs.version_ctrl_nbr >'1'");
			}
			
		
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return strbResult.toString();
	}
	
	private String buildFirstNestedQueryLaboratoryReport(int searchDataType,
			PatientSearchVO find, NBSSecurityObj nbsSecurityObj) {
		String result = "";
		try{
			switch (searchDataType) {
			case DT_LAB_REPORT_CRITERIA:
				result = buildLabReportCriteriaFirstQueryLabReport(find, nbsSecurityObj);
				break;
			case DT_LAB_REPORT:
				result = buildLabReportIdCriteriaFirstQueryLabReport(find, nbsSecurityObj);
				break;
			case DT_ACCESSION_NUMBER:
				result = buildAccesionNumberCriteriaFirstQueryLabReport(find, nbsSecurityObj);
				break;
			case DT_DATE_REPORT:
				result = buildDateReportCriteriaFirstQueryLabReport(find, nbsSecurityObj);
				break;
			case DT_RECEIVED_BY_PUBLIC_HEALTH_DATE:
				result = buildReceivedPublicHealthCaseCriteriaFirstQueryLabReport(find, nbsSecurityObj);
				break;
			case DT_SPECIMEN_COLLECTION_DATE:
				result = buildSpecimenCollectionCriteriaFirstQueryLabReport(find, nbsSecurityObj);
				break;
			case DT_LAB_REPORT_CREATE_DATE:
				result = buildLabReportCreateDateCriteriaFirstQueryLabReport(find, nbsSecurityObj);
				break;
			case DT_LAB_REPORT_LAST_UPDATE_DATE:
				result = buildLabReportLastUpdateDateCriteriaFirstQueryLabReport(find, nbsSecurityObj);
				break;
			case DT_INTERNAL_EXTERNAL_USER:
				result = buildInternalExternalUserCriteriaFirstQueryLabReport(find, nbsSecurityObj);
				break;
			case DT_EVENT_CREATED_UPDATED_BY_USER:
				result = buildCreatedUpdatedUserCriteriaFirstQueryLabReport(find, nbsSecurityObj);
				break;
			case DT_REPORTING_FACILITY:
				result = buildReportingFacilityCriteriaFirstQueryLabReport(find, nbsSecurityObj);
				break;
			case DT_ORDERING_FACILITY:
				result = buildOrderingFacilityCriteriaFirstQueryLabReport(find, nbsSecurityObj);
				break;
			case DT_ORDERING_PROVIDER:
				result = buildOrderingProviderCriteriaFirstQueryLabReport(find, nbsSecurityObj);
				break;
			case DT_CODED_RESULT_ORGANISM:
				result = buildCodedResultOrganismCriteriaFirstQueryLabReport(find, nbsSecurityObj);
				break;	
			case DT_RESULTED_TEST:
				result = buildResultedTestCriteriaFirstQueryLabReport(find, nbsSecurityObj);
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
	   * getProviderInformation(): common method for getting the provider information from DRRQ and Patient file (Summary and Event tab). Method copied from ObservationProcessor.java
	   * @param providerDetails
	   * @param labRep
	   * @return
	   */
	  private Long getProviderInformation (ArrayList<Object>  providerDetails, PatientSrchResultVO labRep){  

		  Long providerUid = null;
		  
	      if (providerDetails != null && providerDetails.size() > 0 && labRep != null) {
	          Object[] orderProvider = providerDetails.toArray();

	          if (orderProvider[0] != null) {
	        	  labRep.setProviderLastName((String) orderProvider[0]);
	          }
	          if (orderProvider[1] != null){
	        	  labRep.setProviderFirstName((String) orderProvider[1]);
	          }
	          if (orderProvider[2] != null){
	        	  labRep.setProviderPrefix((String) orderProvider[2]);
	          }
	          if (orderProvider[3] != null){
	        	  labRep.setProviderSuffix(( String)orderProvider[3]);
	          }
	      	  if (orderProvider[4] != null){
	      		labRep.setDegree(( String)orderProvider[4]);
	      	  }
	     	  if (orderProvider[5] != null){
	     		providerUid= (Long)orderProvider[5];
	     	    labRep.setProviderUid((String)(orderProvider[5]+""));
	     	  }
	        }
	      
	      return providerUid;
	      
	  }
	  


	  @SuppressWarnings("unchecked")
	private Map<Long, Object> getLabReportPersonInfo()
	 {
	   HashMap<Long, Object> observationPersonInfoVOMap = new HashMap<Long, Object>();
	   int labCountFix = propertyUtil.getLabCount();
	   String labPersonInfoQuery = "";
	  try{
		  String obsNeedingReviewSecurity = propertyUtil.getObservationsNeedingReviewSecurity();
		 // if(nbsSecurityObj.getPermission(NBSBOLookup.OBSERVATIONLABREPORT,obsNeedingReviewSecurity))
		//  {
		   // String labDataAccessWhereClause = nbsSecurityObj.getDataAccessWhereClause(NBSBOLookup.OBSERVATIONLABREPORT,obsNeedingReviewSecurity);
		 //   labDataAccessWhereClause = labDataAccessWhereClause != null ? "AND " + labDataAccessWhereClause : "";
		
	     labPersonInfoQuery = 
			" SELECT  observation.OBSERVATION_UID \"observationUid\", "
	        + " ISNULL(pnm.last_nm,'No Last') \"lastNm\","
	        + " ISNULL(pnm.first_nm,'No First') \"firstNm\","
	        + " p.local_id \"personLocalId\","
	        + " p.person_parent_uid \"personParentUid\"," 
	        + " p.curr_sex_cd \"currSexCd\", "
	        + " p.birth_time \"birthTime\", " 
	        + " observation.record_status_cd \"recordStatusCd\" "
	        + " FROM Person p Left outer join Person_name pnm on p.person_uid=pnm.person_uid, observation, participation"
	        + " WHERE ((observation.observation_uid = participation.act_uid) "
	        + " AND (participation.type_cd = 'PATSBJ')  "
	        + " AND (p.person_uid = participation.subject_entity_uid) "
	        + " AND (pnm.nm_use_cd = 'L' or pnm.nm_use_cd IS NULL) "
	        + " AND (observation.record_status_cd in ('PROCESSED', 'UNPROCESSED'))"
	        + " and observation.ctrl_cd_display_form = 'LabReport' "
	        +")"
	        + " order by observation.observation_uid";
	         long timebegin = 0; long timeend = 0;
	         timebegin=System.currentTimeMillis();
	       
	                ObservationPersonInfoVO observationPersonInfoVO = new ObservationPersonInfoVO();
	                observationPersonInfoVOMap =(HashMap)preparedStmtMethodForMap(observationPersonInfoVO, null,
	                                            labPersonInfoQuery, NEDSSConstants.SELECT, "getObservationUid");
	         timeend = System.currentTimeMillis();
	         //logger.debug("\n timeend  for LabReportSummaryListforReview(PersonInfo) " + timeend);
	         //logger.debug("\n total time for LabReportSummaryListforReview(PersonInfo) " + new Long(timeend-timebegin));


	    

	  }
	  catch(Exception e)
	  {
		  logger.error("Error in fetching person Information data for Lab Report(Observations) for Review "); 
	      throw new NEDSSSystemException(e.toString());
	  }

	  return observationPersonInfoVOMap;
	 }
	   
	private ArrayList<Object> assembleLabReportData(ArrayList<?> allRetrievedData, NBSSecurityObj nbsSecurityObj) {
		CachedDropDownValues cache = new CachedDropDownValues();
		ArrayList<Object> result = new ArrayList<Object>();
		ArrayList<String> labsLocalId = new ArrayList<String>();
		//HashMap<Long, Object> personListMap = null;
		try{
			
		   // personListMap = (HashMap<Long, Object>) getLabReportPersonInfo();
		    
		    
			if (allRetrievedData != null) {
				int size = allRetrievedData.size();
				PersonSearchResultTmp curLabReport = null;

				PatientSrchResultVO labReport;
				
				for (int i = 0; i < size; i++) {
					
					curLabReport = (PersonSearchResultTmp) allRetrievedData.get(i);
					if(!labsLocalId.contains(curLabReport.getLocalId())){
						
						Long observationUid = curLabReport.getObservationUid();
						labReport=new PatientSrchResultVO();
						
						labReport.setDocumentType("Lab Report");
						labReport.setStartDate(curLabReport.getDateReceived());
						labReport.setReportingFacilityProvider("");
						labReport.setPersonUID(curLabReport.getMPRUid());
						labReport.setPersonFirstName(curLabReport.getFirstName());
						labReport.setPersonLastName(curLabReport.getLastName());
						labReport.setDescription("");
						labReport.setLocalId(curLabReport.getLocalId());
						labReport.setObservationUid(curLabReport.getObservationUid());
						labReport.setMPRUid(curLabReport.getMPRUid());
						labReport.setPersonParentUid(curLabReport.getMPRUid());
						
						labReport.setElectronicInd(curLabReport.getElectronicInd());
						labReport.setProgramAreaCode(curLabReport.getProgramArea());
						labReport.setRecordStatusCd(curLabReport.getRecordStatusCd());
                        
		            	if(curLabReport.getBirthTime()!=null)
		            		  labReport.setPersonDOB(StringUtils.formatDate(curLabReport.getBirthTime()));
				          labReport.setCurrentSex(curLabReport.getCurrentSex());
		            	 // labReport.setPersonDOB(personInfo.getBirthTime());
		            	  //labReport.setCurrentSex(personInfo.getCurrSexCd());
		            	  labReport.setPersonLocalID(curLabReport.getPersonLocalID());
		            	  if(null == curLabReport.getRecordStatusCd()) {
		            		  labReport.setRecordStatusCd(labReport.getRecordStatusCd());
		            	  }
						
						
			
		     			//Associated With
						 if(null != curLabReport.getProgramArea()) labReport.setNonStdHivProgramAreaCode(PropertyUtil.isStdOrHivProgramArea(curLabReport.getProgramArea()));

						//Check for Investigation associations
						
				        RetrieveSummaryVO rsvo = new RetrieveSummaryVO();
				        labReport.setInvSummaryVOs(
								rsvo.getAssociatedInvListVersion2(observationUid, nbsSecurityObj, NEDSSConstants.OBSERVATION_CLASS_CODE)
						);
						
						
						String jurisdiction ="";
						jurisdiction = cache.getJurisdictionDesc(curLabReport.getJurisdictionCd());
						labReport.setJurisdiction(jurisdiction);
						labReport.setJurisdictionCd(curLabReport.getJurisdictionCd());
						labReport.setAssociatedWith("");
						labsLocalId.add(curLabReport.getLocalId());
						
						//Get Provider/Facility information
						
						ObservationSummaryDAOImpl osd = new ObservationSummaryDAOImpl();
						if(observationUid!=null){

							ArrayList<Object>  providerDetails = osd.getProviderInfo(observationUid,"ORD");
						
							getProviderInformation(providerDetails, labReport);
							
							 //Get the Reporting Facility
							 Map<Object,Object> uidMap = osd.getLabParticipations(observationUid);
				              if (uidMap != null && uidMap.containsKey(NEDSSConstants.PAR111_TYP_CD) && labReport != null) {
				            	  labReport.setReportingFacility(osd.getReportingFacilityName((Long)uidMap.get(NEDSSConstants.PAR111_TYP_CD)));  
				              }
				          }
						
				    	//Get tests and susceptibilities
				        ArrayList<Object>  argList = new ArrayList<Object> ();
				        argList.clear();
				        argList.add("COMP"); // It should be constant
				        argList.add(observationUid);
				        
						
						getTestAndSusceptibilitiesDRRQ(argList, labReport, null); 
										
						result.add(labReport);
					}
				}
				
				this.populateDescTxtFromCachedValuesDRRQ(result);
				
			}
				}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return result;
	}
	
	 private void getTestAndSusceptibilitiesDRRQ(ArrayList<Object> argList, PatientSrchResultVO labRepEvent, LabReportSummaryVO labRepSumm){
   	  String query = "";
   	  
   	  
   	  
   	  ResultedTestSummaryVO testVO = new ResultedTestSummaryVO();
   	  ArrayList<Object>  testList = null;
   	  
		query = WumSqlQuery.SELECT_LABRESULTED_REFLEXTEST_SUMMARY_FORWORKUP_SQL;

				testList = (ArrayList<Object> ) preparedStmtMethod(testVO, argList,query, NEDSSConstants.SELECT);
          //afterReflex = System.currentTimeMillis();
          //totalReflex += (afterReflex - beforeReflex);
				
		    	  if (testList != null) {

		             	if(labRepEvent != null)
		             	  labRepEvent.setTheResultedSummaryTestVOCollection(testList);
		             	if(labRepSumm != null)
		             		labRepSumm.setTheResultedSummaryTestVOCollection(testList);
		             	
		                 Iterator<Object> it = testList.iterator();
		                 
		                 //timing
		                 //t3begin = System.currentTimeMillis();
		                 while (it.hasNext()) {
		                   ResultedTestSummaryVO RVO = (ResultedTestSummaryVO) it.next();
		                   
		                   
		                   
		                   	setSusceptibilityDRRQ(RVO, labRepEvent, labRepSumm, argList);
          
		                 }
		    	  }
        }
	 
	
	  private void populateDescTxtFromCachedValuesDRRQ(Collection<Object>
      reportSummaryVOCollection) {
		PatientSrchResultVO sumVO = null;
		PatientSrchResultVO labVO = null;
		ResultedTestSummaryVO resVO = null;
		Iterator<Object> resItor = null;
		CachedDropDownValues cdv = new CachedDropDownValues();
		String tempStr = null;
		
		Iterator<Object>  itor = reportSummaryVOCollection.iterator();
		while (itor.hasNext()) {
		cdv = new CachedDropDownValues();
		sumVO = (PatientSrchResultVO) itor.next();
		if (sumVO instanceof PatientSrchResultVO) {
		labVO = (PatientSrchResultVO) sumVO;
		labVO.setType(NEDSSConstants.LAB_REPORT_DESC);
		
		if (labVO.getTheResultedTestSummaryVOCollection() != null &&
		labVO.getTheResultedTestSummaryVOCollection().size() > 0) {
			resItor = labVO.getTheResultedTestSummaryVOCollection().iterator();
			while (resItor.hasNext()) {
				resVO = (ResultedTestSummaryVO) resItor.next();
				
				// Added this for ER16368
				if ((resVO.getResultedTestStatusCd() != null) &&(! (resVO.getResultedTestStatusCd().equals("")))){
					tempStr = cdv.getDescForCode("ACT_OBJ_ST",resVO.getResultedTestStatusCd());
				if (tempStr != null && !tempStr.equals(""))
					resVO.setResultedTestStatus(tempStr);
				}
				// End  ER16368
				
				if (resVO.getCtrlCdUserDefined1() != null && resVO.getCtrlCdUserDefined1().equals("N"))
				{
					if (resVO.getCodedResultValue() != null &&
					!resVO.getCodedResultValue().equals("")) {
					tempStr = cdv.getCodedResultDesc(resVO.getCodedResultValue());
					resVO.setCodedResultValue(tempStr);
				}
				}
			
			} //outer while
		} //if
		}
	}
}
	 
  private void setSusceptibilityDRRQ(ResultedTestSummaryVO RVO, PatientSrchResultVO labRepEvent, LabReportSummaryVO labRepSumm, ArrayList<Object> argList){
    	  
    	  
    	  ResultedTestSummaryVO susVO = new ResultedTestSummaryVO();
    	  Long sourceActUid = RVO.getSourceActUid();

	      ArrayList<Object>  susListFinal = new ArrayList<Object> ();
	
	
	      ArrayList<Object>  multipleSusceptArray = new ArrayList<Object> ();
	
          argList.clear();
          argList.add("COMP"); // It should be constant
          argList.add("REFR"); 
          argList.add(sourceActUid);
	                 
	
        susListFinal = (ArrayList<Object> ) preparedStmtMethod(susVO, argList,
               WumSqlQuery.SELECT_LABSUSCEPTIBILITES_REFLEXTEST_SUMMARY_FORWORKUP_SQLSERVER_DRRQ,
               NEDSSConstants.SELECT);
        
	
				Iterator<Object> multSuscepts = susListFinal.iterator();
				while (multSuscepts.hasNext()){
						ResultedTestSummaryVO rtsVO = (ResultedTestSummaryVO)multSuscepts.next();
						multipleSusceptArray.add(rtsVO);
				}
			
   

				if (multipleSusceptArray != null) {
					RVO.setTheSusTestSummaryVOColl(multipleSusceptArray);
				}

 
      }
  
	public ArrayList<Object> getProviderUids() throws NEDSSSystemException {
		PersonDT personDT = new PersonDT();
		String query;
		PropertyUtil propUtil = PropertyUtil.getInstance();
		// Added for Electronic indicator flag reading from NEDSS.Preperties
		// file-Sathya
		
				query = "SELECT p.person_uid \"personUid\" from person p"
						+ " WHERE"
						+ " p.cd = '"	+NEDSSConstants.PRV+ "'"
						+ " and "
						+ "(p.Edx_ind<>'" +NEDSSConstants.EDX_IND+ "' OR p.edx_ind is null) "
				        + " and"
						+" p.electronic_ind='"+NEDSSConstants.ELECTRONIC_IND_ENTITY_MATCH + "'";
		
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
		
		// for Sql server
		{
			query = "SELECT top(1) p.person_uid \"personUid\" from person p WHERE p.cd = '"
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
		dbConnection = getConnection();
		try {
			preparedStmt = dbConnection.prepareStatement(UPDATE_EDX_IND_PERSON);
			int i = 1;

			if (personUid != null)
				preparedStmt.setLong(1, personUid.longValue()); // 2

			resultCount = preparedStmt.executeUpdate();
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
				+ " where patient_uid = ? and match_string not like 'LR^%'";

		PreparedStatement preparedStmt = null;
		Connection dbConnection = null;

		try {
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
		
			  SELECT_EDX_PATIENT_MATCH_TMP = "SELECT PATIENT_UID,LAST_NM,FIRST_NM,BIRTH_TIME,CURR_SEX_CD,"
				+ "ROOT_EXTENSION_TXT,TYPE_CD,ASSIGNING_AUTHORITY_CD,CODE_DESC_TXT,CODE_SYSTEM_CD,CD from  dbo.EDX_PATIENT_MATCH_TMP";
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
		SELECT_EDX_NOK_MATCH_TMP  = "SELECT PATIENT_UID,LAST_NM,FIRST_NM,"
					+ "ROOT_EXTENSION_TXT,TYPE_CD,ASSIGNING_AUTHORITY_CD,CODE_DESC_TXT,CODE_SYSTEM_CD,STREET_ADDR1,CITY,STATE,ZIP_CODE,TELEPHONE,CD from dbo.EDX_NOK_MATCH_TMP";

		
		String INSERT_EDX_PATIENT_MATCH = "INSERT INTO edx_patient_match (PATIENT_UID,MATCH_STRING,TYPE_CD,MATCH_STRING_HASHCODE)  VALUES(?,?,?,?) ";
		con = this.getConnection();
		select = con.createStatement();
		psmt = con.prepareStatement(INSERT_EDX_PATIENT_MATCH);
	    String UPDATE_EDX_IND_PERSON  = "UPDATE PERSON SET edx_ind = 'Y' WHERE person_uid  IN (select Patient_uid from EDX_PATIENT_MATCH)";
		try {
			
				rs = select.executeQuery(SELECT_EDX_NOK_MATCH_TMP );
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
			proc_stmt = con.prepareCall("{ call dbo.usp_EDX_NOK_HashString() }");
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



/*
private String wrapNestedQueryLabReport(String nestedQuery, String nqDerivedTableName) {
	StringBuffer query = new StringBuffer("");
	query.append(" select ");
	query.append(nqDerivedTableName);
	query.append(".\"ObservationUid\", ");
	query.append(nqDerivedTableName);
	query.append(".\"LocalId\", ");
	query.append(nqDerivedTableName);
	query.append(".\"sharedInd\", ");
	query.append(nqDerivedTableName);
	query.append(".\"electronicInd\", ");
	query.append(nqDerivedTableName);
	query.append(".\"JurisdictionCd\", ");
	query.append(nqDerivedTableName);
	query.append(".\"ProgramArea\", ");
	query.append(nqDerivedTableName);
	query.append(".\"DateReceived\", ");
	query.append(nqDerivedTableName);
	query.append(".\"versionCtrlNbr\", ");
	query.append(nqDerivedTableName);
	query.append(".\"ctrlCdDisplayForm\", ");
	query.append(nqDerivedTableName);
	query.append(".\"Status\", ");
	query.append(nqDerivedTableName);
	query.append(".\"resultedTest\", ");
	query.append(nqDerivedTableName);
	query.append(".\"resultedTestCd\", ");
	query.append(nqDerivedTableName);
	query.append(".\"resultedTestCdSystemCd\", ");
	query.append(nqDerivedTableName);
	query.append(".\"recordStatusCd\" ");
	
	

	query.append(" from observation obs with (nolock), ");
	query.append(" ( ");
	query.append(nestedQuery);
	query.append(" ) ");
	query.append(nqDerivedTableName);
	query.append(" where ");
	query.append(nqDerivedTableName);
	query.append(".\"ObservationUid\" = obs.observation_uid ");


return query.toString();
}*/

/**
 * buildInvestigationCriteriaNestedQueryInvestigation(): program area, jurisdiction, condition
 * @param find
 * @param nestedQuery
 * @param wqDerivedTableName
 * @return
 */

private String buildLabReportCriteriaNestedQueryLabReport(PatientSearchVO find,
	String nestedQuery) {
	StringBuffer query = new StringBuffer("");
    String processedStateWhereClause="";
    if(!nestedQuery.trim().endsWith("and")){
    	nestedQuery = nestedQuery+ " and "; 
    }
	
    if(null != find && (null != find.getProcessedState() && "true".equalsIgnoreCase(find.getProcessedState())) 
  			&& (null == find.getUnProcessedState() || "false".equalsIgnoreCase(find.getUnProcessedState()))){
			processedStateWhereClause= "(obs.record_status_cd='PROCESSED')";
  	} else if( null != find && (null == find.getProcessedState() || "false".equalsIgnoreCase(find.getProcessedState())) 
  			&& (null != find.getUnProcessedState() && "true".equalsIgnoreCase(find.getUnProcessedState()))) {
  		processedStateWhereClause="(obs.record_status_cd='UNPROCESSED')";
  	} else {
  		processedStateWhereClause= "(obs.record_status_cd in ('PROCESSED', 'UNPROCESSED'))";
  	}
	
	//query.append(buildNestedQuerySelectLabReport(wqDerivedTableName));//Everything we need to show

	//query.append(" from observation obs with(nolock),( ");
	query.append(nestedQuery);//The nested query contains everything that we need to show
	//query.append(" ) ");
	//query.append(wqDerivedTableName);

	//query.append(" where ");
	
	//query.append(wqDerivedTableName);
	//query.append(".\"ObservationUid\" = ");
	//query.append("obs.observation_uid and ");
	if(null != processedStateWhereClause && processedStateWhereClause.trim().length() > 0) query.append(processedStateWhereClause);
	query.append(buildLaboratoryReportCriteriaSearchWhereClause(find));
	//query.append(".\"prog_area_cd\" = '"+find.getProgramAreaSelected()+"'");

	return query.toString();
}


private String buildLabreportCriteriaNestedQueryLabReport(PatientSearchVO find,
		String nestedQuery) {
	StringBuffer query = new StringBuffer("");

	//query.append(buildNestedQuerySelectLabReport(wqDerivedTableName));//Everything we need to show

	//query.append(" from observation obs with(nolock),( ");
	query.append(nestedQuery);//The nested query contains everything that we need to show
	//query.append(" ) ");
	//query.append(wqDerivedTableName);

	//query.append(" where ");
	
	//query.append(wqDerivedTableName);
	//query.append(".\"ObservationUid\" = ");
	//query.append("obs.observation_uid and ");
	query.append(buildLabReportSearchWhereClause(find));
	//query.append(".\"prog_area_cd\" = '"+find.getProgramAreaSelected()+"'");

	return query.toString();
}


private String buildAccessionNumberCriteriaNestedQueryLabReport(PatientSearchVO find,
		String nestedQuery) {
	StringBuffer query = new StringBuffer("");

	//query.append(buildNestedQuerySelectLabReport(wqDerivedTableName));//Everything we need to show

	//query.append(" from observation obs with(nolock),( ");
	query.append(nestedQuery);//The nested query contains everything that we need to show
	//query.append(" ) ");
	//query.append(wqDerivedTableName);

	//query.append(" where ");
	
	//query.append(wqDerivedTableName);
	//query.append(".\"ObservationUid\" = ");
	//query.append("obs.observation_uid and ");
	query.append(buildAccesionNumberSearchWhereClause(find));
	//query.append(".\"prog_area_cd\" = '"+find.getProgramAreaSelected()+"'");

	return query.toString();
}


private String buildDateReportCriteriaNestedQueryLabReport(PatientSearchVO find,
		String nestedQuery) {
	StringBuffer query = new StringBuffer("");

	//query.append(buildNestedQuerySelectLabReport(wqDerivedTableName));//Everything we need to show

	//query.append(" from observation obs with(nolock),( ");
	query.append(nestedQuery);//The nested query contains everything that we need to show
	//query.append(" ) ");
	//query.append(wqDerivedTableName);

	//query.append(" where ");
	
	//query.append(wqDerivedTableName);
	//query.append(".\"ObservationUid\" = ");
	//query.append("obs.observation_uid and ");
	query.append(buildDateReportSearchWhereClause(find));
	//query.append(".\"prog_area_cd\" = '"+find.getProgramAreaSelected()+"'");

	return query.toString();
}

private String buildDateReceivedPublicHealthCaseCriteriaNestedQueryLabReport(PatientSearchVO find,
		String nestedQuery) {
	StringBuffer query = new StringBuffer("");

	//query.append(buildNestedQuerySelectLabReport(wqDerivedTableName));//Everything we need to show

	//query.append(" from observation obs with(nolock),( ");
	query.append(nestedQuery);//The nested query contains everything that we need to show
	//query.append(" ) ");
	//query.append(wqDerivedTableName);

	//query.append(" where ");
	
	//query.append(wqDerivedTableName);
	//query.append(".\"ObservationUid\" = ");
	//query.append("obs.observation_uid and ");
	query.append(buildReceivedPublicHealthCaseSearchWhereClause(find));
	//query.append(".\"prog_area_cd\" = '"+find.getProgramAreaSelected()+"'");

	return query.toString();
}


private String buildSpecimenCollectionDateCriteriaNestedQueryLabReport(PatientSearchVO find,
		String nestedQuery) {
	StringBuffer query = new StringBuffer("");

	//query.append(buildNestedQuerySelectLabReport(wqDerivedTableName));//Everything we need to show

	//query.append(" from observation obs with(nolock),( ");
	query.append(nestedQuery);//The nested query contains everything that we need to show
	//query.append(" ) ");
	//query.append(wqDerivedTableName);

	//query.append(" where ");
	
	//query.append(wqDerivedTableName);
	//query.append(".\"ObservationUid\" = ");
	//query.append("obs.observation_uid and ");
	query.append(buildSpecimenCollectionSearchWhereClause(find));
	//query.append(".\"prog_area_cd\" = '"+find.getProgramAreaSelected()+"'");

	return query.toString();
}

private String buildLabReportCreateDateCriteriaNestedQueryLabReport(PatientSearchVO find,
		String nestedQuery) {
	StringBuffer query = new StringBuffer("");

	//query.append(buildNestedQuerySelectLabReport(wqDerivedTableName));//Everything we need to show

	//query.append(" from observation obs with(nolock),( ");
	query.append(nestedQuery);//The nested query contains everything that we need to show
	//query.append(" ) ");
	//query.append(wqDerivedTableName);

	//query.append(" where ");
	
	//query.append(wqDerivedTableName);
	//query.append(".\"ObservationUid\" = ");
	//query.append("obs.observation_uid and ");
	query.append(buildLabReportCreateDateSearchWhereClause(find));
	//query.append(".\"prog_area_cd\" = '"+find.getProgramAreaSelected()+"'");

	return query.toString();
}

private String buildLabReportLastUpdatedCriteriaNestedQueryLabReport(PatientSearchVO find,
		String nestedQuery ) {
	StringBuffer query = new StringBuffer("");

	//query.append(buildNestedQuerySelectLabReport(wqDerivedTableName));//Everything we need to show

	//query.append(" from observation obs with(nolock),( ");
	query.append(nestedQuery);//The nested query contains everything that we need to show
	//query.append(" ) ");
	//query.append(wqDerivedTableName);

	//query.append(" where ");
	
	//uery.append(wqDerivedTableName);
	//query.append(".\"ObservationUid\" = ");
	//query.append("obs.observation_uid and ");
	query.append(buildLabReportLastUpdateDateSearchWhereClause(find));
	//query.append(".\"prog_area_cd\" = '"+find.getProgramAreaSelected()+"'");

	return query.toString();
}

private String buildInternalExternalUserCriteriaNestedQueryLabReport(PatientSearchVO find,
		String nestedQuery) {
	StringBuffer query = new StringBuffer("");

	//query.append(buildNestedQuerySelectLabReport(wqDerivedTableName));//Everything we need to show

	//query.append(" from observation obs with(nolock),( ");
	query.append(nestedQuery);//The nested query contains everything that we need to show
	////query.append(" ) ");
	//query.append(wqDerivedTableName);

	//query.append(" where ");
	
	//query.append(wqDerivedTableName);
	//query.append(".\"ObservationUid\" = ");
	//query.append("obs.observation_uid and ");
	query.append(buildInternalExternalUserSearchWhereClause(find));
	//query.append(".\"prog_area_cd\" = '"+find.getProgramAreaSelected()+"'");

	return query.toString();
}

private String buildCreatedUpdatedUserCriteriaNestedQueryLabReport(PatientSearchVO find,
		String nestedQuery) {
	StringBuffer query = new StringBuffer("");

	//query.append(buildNestedQuerySelectLabReport(wqDerivedTableName));//Everything we need to show

	//query.append(" from observation obs with(nolock),( ");
	query.append(nestedQuery);//The nested query contains everything that we need to show
	//query.append(" ) ");
	//query.append(wqDerivedTableName);

	//query.append(" where ");
	
	//query.append(wqDerivedTableName);
	//query.append(".\"ObservationUid\" = ");
	//query.append("obs.observation_uid and ");
	query.append(buildCreatedUpdatedUserSearchWhereClause(find));
	//query.append(".\"prog_area_cd\" = '"+find.getProgramAreaSelected()+"'");

	return query.toString();
}

private String buildReportingFacilityCriteriaNestedQueryLabReport(PatientSearchVO find,
		String nestedQuery) {
	StringBuffer query = new StringBuffer("");

	//query.append(buildNestedQuerySelectLabReport(wqDerivedTableName));//Everything we need to show

	//query.append(" from observation obs with(nolock),( ");
	query.append(nestedQuery);//The nested query contains everything that we need to show
	//query.append(" ) ");
	//query.append(wqDerivedTableName);

	//query.append(" where ");
	
	//query.append(wqDerivedTableName);
	//query.append(".\"ObservationUid\" = ");
	//query.append("obs.observation_uid and ");
	query.append(buildReportingFacilitySearchWhereClause(find));
	//query.append(".\"prog_area_cd\" = '"+find.getProgramAreaSelected()+"'");

	return query.toString();
}

private String buildOrderingFacilityCriteriaNestedQueryLabReport(PatientSearchVO find,
		String nestedQuery) {
	StringBuffer query = new StringBuffer("");

	//query.append(buildNestedQuerySelectLabReport(wqDerivedTableName));//Everything we need to show

	//query.append(" from observation obs with(nolock),( ");
	query.append(nestedQuery);//The nested query contains everything that we need to show
	//query.append(" ) ");
	//query.append(wqDerivedTableName);

	//query.append(" where ");
	
	//query.append(wqDerivedTableName);
	//query.append(".\"ObservationUid\" = ");
	//query.append("obs.observation_uid and ");
	query.append(buildOrderingFacilitySearchWhereClause(find));
	//query.append(".\"prog_area_cd\" = '"+find.getProgramAreaSelected()+"'");

	return query.toString();
}
private String buildOrderingProviderCriteriaNestedQueryLabReport(PatientSearchVO find,
		String nestedQuery) {
	StringBuffer query = new StringBuffer("");

	//query.append(buildNestedQuerySelectLabReport(wqDerivedTableName));//Everything we need to show

	//query.append(" from observation obs with(nolock),( ");
	query.append(nestedQuery);//The nested query contains everything that we need to show
	//query.append(" ) ");
	//query.append(wqDerivedTableName);

	//query.append(" where ");
	
	//query.append(wqDerivedTableName);
	//query.append(".\"ObservationUid\" = ");
	//query.append("obs.observation_uid and ");
	query.append(buildOrderingProviderSearchWhereClause(find));
	//query.append(".\"prog_area_cd\" = '"+find.getProgramAreaSelected()+"'");

	return query.toString();
}

private String buildCodedResultOrganismCriteriaNestedQueryLabReport(PatientSearchVO find,
		String nestedQuery) {
	StringBuffer query = new StringBuffer("");

	//query.append(buildNestedQuerySelectLabReport(wqDerivedTableName));//Everything we need to show

	//query.append(" from observation obs with(nolock), act_relationship act with(nolock), ( ");
	query.append(nestedQuery);//The nested query contains everything that we need to show
	//query.append(" ) ");
	//query.append(wqDerivedTableName);

	//query.append(" where ");
	
	//query.append(wqDerivedTableName);
	//query.append(".\"ObservationUid\" = ");
	//query.append("obs.observation_uid and ");
	//query.append(wqDerivedTableName);
	//query.append(".\"ObservationUid\" = ");
	//query.append("act.target_act_uid and ");
	
	query.append(buildCodedResultOrganismSearchWhereClause(find));
	//query.append(".\"prog_area_cd\" = '"+find.getProgramAreaSelected()+"'");

	return query.toString();
}

private String buildResultedTestCriteriaNestedQueryLabReport(PatientSearchVO find,
		String nestedQuery) {
	StringBuffer query = new StringBuffer("");

	//query.append(buildNestedQuerySelectLabReport(wqDerivedTableName));//Everything we need to show

	//query.append(" from observation obs with(nolock), act_relationship act with(nolock), ( ");
	query.append(nestedQuery);//The nested query contains everything that we need to show
	//query.append(" ) ");
	//query.append(wqDerivedTableName);

	//query.append(" where ");
	
	//query.append(wqDerivedTableName);
	//query.append(".\"ObservationUid\" = ");
	//query.append("obs.observation_uid and ");
	//query.append(wqDerivedTableName);
	//query.append(".\"ObservationUid\" = ");
	//query.append("act.target_act_uid and ");
	
	query.append(buildResultedTestSearchWhereClause(find));
	//query.append(".\"prog_area_cd\" = '"+find.getProgramAreaSelected()+"'");

	return query.toString();
}

/*
private String buildNestedQuerySelectLabReport(String wqDerivedTableName) {
	StringBuffer select = new StringBuffer("");
	
	select.append(" select ");
	select.append(wqDerivedTableName);
	select.append(".\"ObservationUid\", ");
	select.append(wqDerivedTableName);
	select.append(".\"LocalId\", ");
	select.append(wqDerivedTableName);
	select.append(".\"sharedInd\", ");
	select.append(wqDerivedTableName);
	select.append(".\"electronicInd\", ");
	select.append(wqDerivedTableName);
	select.append(".\"JurisdictionCd\", ");
	select.append(wqDerivedTableName);
	select.append(".\"ProgramArea\", ");
	select.append(wqDerivedTableName);
	select.append(".\"DateReceived\", ");
	select.append(wqDerivedTableName);
	select.append(".\"versionCtrlNbr\", ");
	select.append(wqDerivedTableName);
	select.append(".\"ctrlCdDisplayForm\", ");
	select.append(wqDerivedTableName);
	select.append(".\"Status\", ");
	select.append(wqDerivedTableName);
	select.append(".\"resultedTest\", ");
	select.append(wqDerivedTableName);
	select.append(".\"resultedTestCd\", ");
	select.append(wqDerivedTableName);
	select.append(".\"resultedTestCdSystemCd\", ");
	select.append(wqDerivedTableName);
	select.append(".\"recordStatusCd\" ");
	
	return select.toString();
}
*/

public ArrayList<Object> findPatientsByQuery(PatientSearchVO thePatientSearchVO, String finalQuery, int cacheNumber,
		int fromIndex, NBSSecurityObj nbsSecurityObj) {
	String myPAInvestigationsSecurity = propertyUtil.getMyProgramAreaSecurity();
    String dataAccessWhereClause = nbsSecurityObj.getDataAccessWhereClause(NBSBOLookup.OBSERVATIONLABREPORT, myPAInvestigationsSecurity, "obs");  	
	
	ArrayList<Object> result = new ArrayList<Object>();
	if (cacheNumber <= 0) cacheNumber = this.getReturnedPatientNumber();
	try {
		int countIndex= finalQuery.indexOf("{count}");
		int jurisIndex= finalQuery.indexOf("{program_jurisdiction_oid}");
		
		if(countIndex !=-1 || jurisIndex !=-1) {
			try {
			 if(countIndex !=-1)	finalQuery=finalQuery.replaceAll("\\{count\\}", String.valueOf(this.getReturnedPatientNumber()));
			 if(jurisIndex !=-1)    finalQuery=finalQuery.replaceAll("\\{program_jurisdiction_oid\\}", dataAccessWhereClause);
		   }catch(Exception ex) {
				throw new NEDSSSystemException(ex.toString());
		   }
		}
		
		if(jurisIndex ==-1) finalQuery = finalQuery+" AND "+dataAccessWhereClause;
		
		logger.debug("final::::::::::Query:::"+finalQuery);
		//String countSearchQuery = "Select COUNT(*) from ("+finalQuery+")countQuery";
		PersonSearchResultTmp searchResultName = new PersonSearchResultTmp();
		//Integer totalCount = (Integer) preparedStmtMethod(searchResultName,null,countSearchQuery, NEDSSConstants.SELECT_COUNT);
		DisplayPersonList displayPersonList=null;
		ArrayList<Object> personVOs = new ArrayList<Object>();
		
		ArrayList<?> searchResult = (ArrayList<?>) preparedStmtMethod(
				searchResultName, null, finalQuery, NEDSSConstants.SELECT);
			personVOs= assembleLabReportData(searchResult, nbsSecurityObj);
	
	displayPersonList = new DisplayPersonList(
			personVOs.size()+1, personVOs, fromIndex,
			personVOs.size());
	result.add(displayPersonList);

	}catch(Exception ex) {
		logger.debug("Final Query::::exception::::from HomePage"+finalQuery);
		throw new NEDSSSystemException(ex.toString());
	}
	return result;
}

private String getUniqueObservationUids(String aQuery) throws Exception {

	Connection dbConnection = null;
	PreparedStatement preparedStmt = null;
	ResultSet resultSet = null;

	try {
		dbConnection = getConnection();
		preparedStmt = dbConnection.prepareStatement(aQuery);
		resultSet = preparedStmt.executeQuery();
		String result = null;
		boolean reenterant= false;
		while (resultSet.next()) {
			if(reenterant)
				result = result+","+ resultSet.getString(1);
			else {
				result = resultSet.getString(1);
				reenterant = true;
			}

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
