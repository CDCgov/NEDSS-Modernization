package gov.cdc.nedss.entity.person.ejb.dao;

import gov.cdc.nedss.entity.person.dt.PersonDT;
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.DataTables;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
* Name:		PersonHistDAOImpl.java
* Description:	This is the implementation of NEDSSDAOInterface for old the
*               PersonHist value object inserting into PersonHist table.
* Copyright:	Copyright (c) 2001
* Company: 	Computer Sciences Corporation
* @author	Ning Peng
* @version	1.0
*/
public class PersonHistDAOImpl  extends DAOBase
{

	static final LogUtils logger = new LogUtils(PersonHistDAOImpl.class.getName());

	private long personUid = -1;

	private short versionCtrlNbr = 0;
	private static final String SELECT_PERSON_HIST =
	"SELECT person_uid \"personUid\", " +
	"person_parent_uid \"personParentUid\", " +
	"administrative_gender_cd \"administrativeGenderCd\", " +
	"add_reason_cd \"addReasonCd\", " +
	"add_time \"addTime\", " +
	"add_user_id \"addUserId\", " +
	"age_calc \"ageCalc\", " +
	"age_calc_time \"ageCalcTime\", " +
	"age_calc_unit_cd \"ageCalcUnitCd\", " +
	"age_category_cd \"ageCategoryCd\", " +
	"age_reported \"ageReported\", " +
	"age_reported_time \"ageReportedTime\", " +
	"age_reported_unit_cd \"ageReportedUnitCd\", " +
	"as_of_date_admin \"asOfDateAdmin\", " +
	"as_of_date_ethnicity \"asOfDateEthnicity\", " +
	"as_of_date_general \"asOfDateGeneral\", " +
	"as_of_date_morbidity \"asOfDateMorbidity\", " +
	"as_of_date_sex \"asOfDateSex\", " +
	"birth_gender_cd \"birthGenderCd\", " +
	"birth_order_nbr \"birthOrderNbr\", " +
	"birth_time \"birthTime\", " +
	"birth_time_calc \"birthTimeCalc\", " +
	"cd \"cd\", " +
	"cd_desc_txt \"cdDescTxt\", " +
	"curr_sex_cd \"currSexCd\", " +
	"deceased_ind_cd \"deceasedIndCd\", " +
	"deceased_time \"deceasedTime\", " +
	"description \"description\", " +
	"education_level_cd \"educationLevelCd\", " +
	"education_level_desc_txt \"educationLevelDescTxt\", " +
	"electronic_ind \"electronicInd\", " +
	"ethnic_group_ind \"ethnicGroupInd\", " +
	"last_chg_reason_cd \"lastChgReasonCd\", " +
	"last_chg_time \"lastChgTime\", " +
	"last_chg_user_id \"lastChgUserId\", " +
	"marital_status_cd \"maritalStatusCd\", " +
	"marital_status_desc_txt \"maritalStatusDescTxt\", " +
	"mothers_maiden_nm \"mothersMaidenNm\", " +
	"multiple_birth_ind \"multipleBirthInd\", " +
	"occupation_cd \"occupationCd\", " +
	"preferred_gender_cd \"preferredGenderCd\", " +
	"prim_lang_cd \"primLangCd\", " +
	"prim_lang_desc_txt \"primLangDescTxt\", " +
	"record_status_cd \"recordStatusCd\", " +
	"record_status_time \"recordStatusTime\", " +
	"status_cd \"statusCd\", " +
	"status_time \"statusTime\", " +
	"survived_ind_cd \"survivedIndCd\", " +
	"user_affiliation_txt \"userAffiliationTxt\", " +
	"first_nm \"firstNm\", " +
	"last_nm \"lastNm\", " +
	"middle_nm \"middleNm\", " +
	"nm_prefix \"nmPrefix\", " +
	"nm_suffix \"nmSuffix\", " +
	"preferred_nm \"preferredNm\", " +
	"hm_street_addr1 \"hmStreetAddr1\", " +
	"hm_street_addr2 \"hmStreetAddr2\", " +
	"hm_city_cd \"hmCityCd\", " +
	"hm_city_desc_txt \"hmCityDescTxt\", " +
	"hm_state_cd \"hmStateCd\", " +
	"hm_zip_cd \"hmZipCd\", " +
	"hm_cnty_cd \"hmCntyCd\", " +
	"hm_cntry_cd \"hmCntryCd\", " +
	"hm_phone_nbr \"hmPhoneNbr\", " +
	"hm_phone_cntry_cd \"hmPhoneCntryCd\", " +
	"hm_email_addr \"hmEmailAddr\", " +
	"cell_phone_nbr \"cellPhoneNbr\", " +
	"wk_street_addr1 \"wkStreetAddr1\", " +
	"wk_street_addr2 \"wkStreetAddr2\", " +
	"wk_city_cd \"wkCityCd\", " +
	"wk_city_desc_txt \"wkCityDescTxt\", " +
	"wk_state_cd \"wkStateCd\", " +
	"wk_zip_cd \"wkZipCd\", " +
	"wk_cnty_cd \"wkCntyCd\", " +
	"wk_cntry_cd \"wkCntryCd\", " +
	"wk_phone_nbr \"wkPhoneNbr\", " +
	"wk_phone_cntry_cd \"wkPhoneCntryCd\", " +
	"wk_email_addr \"wkEmailAddr\", " +
	"SSN \"SSN\", " +
	"medicaid_num \"medicaidNum\", " +
	"dl_num \"dlNum\", " +
	"dl_state_cd \"dlStateCd\", " +
	"race_cd \"raceCd\", " +
	"race_seq_nbr \"raceSeqNbr\", " +
	"race_category_cd \"raceCategoryCd\",  " +
	"adults_in_house_nbr \"adultsInHouseNbr\", " +
	"children_in_house_nbr \"childrenInHouseNbr\",  " +
	"birth_city_cd \"birthCityCd\",  " +
	"birth_city_desc_txt \"birthCityDescTxt\",  " +
	"birth_cntry_cd \"birthCntryCd\",  " +
	"birth_state_cd \"birthStateCd\",  " +
	"local_id \"localId\",  " +
	"edx_ind \"edxInd\", "+
	"speaks_english_cd \"speaksEnglishCd\", additional_gender_cd \"additionalGenderCd\",ehars_id \"eharsId\","+
	 "ethnic_unk_reason_cd \"ethnicUnkReasonCd\", "+
	 "sex_unk_reason_cd \"sexUnkReasonCd\", "+
        "dedup_match_ind \"dedupMatchInd\", "+
        "group_nbr \"groupNbr\", "+
        "group_time \"groupTime\" "+

	"FROM " + DataTables.PERSON_TABLE_HIST +
	" WHERE person_uid = ? AND version_ctrl_nbr = ? ";

	public static final String SELECT_PERSON_HIST_ITEMS =
	"select last_chg_time \"LastChgTime\", " +
	"version_ctrl_nbr \"versionCtrlNbr\" " +
	"FROM " + DataTables.PERSON_TABLE_HIST +
	" where person_uid = ?" ;

	private static final String INSERT_PERSON_HIST
	  = "INSERT INTO Person_hist ( "
	  + "person_uid, "
	  + "person_parent_uid, "
	  + "version_ctrl_nbr, "
	  + "administrative_gender_cd, "
	  + "add_reason_cd, "
	  + "add_time, "
	  + "add_user_id, "
	  + "age_calc, "
	  + "age_calc_time, "
	  + "age_calc_unit_cd, "
	  + "age_category_cd, "
	  + "age_reported, "
	  + "age_reported_time, "
	  + "age_reported_unit_cd, "
	  + "as_of_date_admin, "
	  + "as_of_date_ethnicity, "
	  + "as_of_date_general, "
	  + "as_of_date_morbidity, "
	  + "as_of_date_sex, "
	  + "birth_gender_cd, "
	  + "birth_order_nbr, "
	  + "birth_time, "
	  + "birth_time_calc, "
	  + "cd, "
	  + "cd_desc_txt, "
	  + "curr_sex_cd, "
	  + "deceased_ind_cd, "
	  + "deceased_time, "
	  + "description, "
	  + "education_level_cd, "
	  + "education_level_desc_txt, "
	  + "electronic_ind, "
	  + "ethnic_group_ind, "
	  + "last_chg_reason_cd, "
	  + "last_chg_time, "
	  + "last_chg_user_id, "
	  + "marital_status_cd, "
	  + "marital_status_desc_txt, "
	  + "mothers_maiden_nm, "
	  + "multiple_birth_ind, "
	  + "occupation_cd, "
	  + "preferred_gender_cd, "
	  + "prim_lang_cd, "
	  + "prim_lang_desc_txt, "
	  + "record_status_cd, "
	  + "record_status_time, "
	  + "status_cd, "
	  + "status_time, "
	  + "survived_ind_cd, "
	  + "user_affiliation_txt, "
	  + "first_nm, "
	  + "last_nm, "
	  + "middle_nm, "
	  + "nm_prefix, "
	  + "nm_suffix, "
	  + "preferred_nm, "
	  + "hm_street_addr1, "
	  + "hm_street_addr2, "
	  + "hm_city_cd, "
	  + "hm_city_desc_txt, "
	  + "hm_state_cd, "
	  + "hm_zip_cd, "
	  + "hm_cnty_cd, "
	  + "hm_cntry_cd, "
	  + "hm_phone_nbr, "
	  + "hm_phone_cntry_cd, "
	  + "hm_email_addr, "
	  + "cell_phone_nbr, "
	  + "wk_street_addr1, "
	  + "wk_street_addr2, "
	  + "wk_city_cd, "
	  + "wk_city_desc_txt, "
	  + "wk_state_cd, "
	  + "wk_zip_cd, "
	  + "wk_cnty_cd, "
	  + "wk_cntry_cd, "
	  + "wk_phone_nbr, "
	  + "wk_phone_cntry_cd, "
	  + "wk_email_addr, "
	  + "SSN, "
	  + "medicaid_num, "
	  + "dl_num, "
	  + "dl_state_cd, "
	  + "race_cd, "
	  + "race_seq_nbr, "
	  + "race_category_cd, "
	  + "ethnicity_group_cd, "
	  + "ethnicity_group_seq_nbr, "
	  + "adults_in_house_nbr, "
	  + "children_in_house_nbr, "
	  + "birth_city_cd, "
	  + "birth_city_desc_txt, "
	  + "birth_cntry_cd, "
	  + "birth_state_cd, "
	  + "race_desc_txt, "
	  + "ethnic_group_desc_txt, "
	  + "local_id, "
	  + "edx_ind, "
	  + "speaks_english_cd,"
	  + "additional_gender_cd,"
	  + "ehars_id,"
	  + "ethnic_unk_reason_cd,"
	  + "sex_unk_reason_cd,"
          + "dedup_match_ind, "
          +"group_nbr, "
          +"group_time"
	  + " ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?, ?, ?, ?)";

   //for the time being, using default change_user_id for testing
	private long defaultChangeUserId = 0;

  /**
   * Default constructor
   */
   public PersonHistDAOImpl()
	  throws NEDSSDAOSysException, NEDSSSystemException
	{
	}

	/**
	 * Initializes the class attributes to the passed in parameters
	 * @param uid
	 * @param versionCtrlNbr
	 */
	public PersonHistDAOImpl(long uid, short versionCtrlNbr)
	  throws NEDSSDAOSysException, NEDSSSystemException
	{
		personUid = uid;
		this.versionCtrlNbr = versionCtrlNbr;
	}

	/**
	 * Stores the collection of dt objects
	 * @param coll  A collection of dt objects to be inserted into the database.
	 */
	public void store(Collection<Object> coll)
	   throws NEDSSDAOSysException, NEDSSSystemException
	{
		try{
			Iterator<Object> iterator = null;
			if(coll != null)
			{
				iterator = coll.iterator();
				while(iterator.hasNext())
				{
					store(iterator.next());
				}
			}
		}catch(Exception ex){
			logger.fatal("Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}


	/**
	 * Results in the insertion of a record into the database.
	 * @param obj  Represents the dt object to be stored.
	 */
	public void store(Object obj)
	  throws NEDSSDAOSysException, NEDSSSystemException
	{
		try{
			PersonDT personDT = (PersonDT)obj;
			if(personDT == null)
			   throw new NEDSSSystemException("Error: try to store null PersonDT object.");
			  insertPersonHist(personDT);
		}catch(Exception ex){
			logger.fatal("Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}

	}

	/**
	 * Populated and returns a DT object based on the parameters passed in.
	 * @param personUid   Is a Long object.
	 * @param versionCtrlNbr  A Integer object.
	 * @return PersonDT
	 */
	public PersonDT load(Long personUid, Integer versionCtrlNbr)
	throws NEDSSSystemException, NEDSSSystemException
	{
		try{
			logger.info("Starts loadObject() for a person history...");
			PersonDT personDT = selectPersonHist(personUid.longValue(),versionCtrlNbr.intValue());
			personDT.setItNew(false);
			personDT.setItDirty(false);
			logger.info("Done loadObject() for a person history - return: " + personDT.toString());
			return personDT;
		}catch(Exception ex){
			logger.fatal("Exception  = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	/**
	 * Loads the person record with the specified personUid and versionCtrlNbr
	 * @param personUid long  The person uid of the record to be loaded
	 * @param versionCtrlNbr int  The versionCtrlNbr of the record to be loaded.
	 * @return PersonDT   The personDT of the specified search criteria.
	 */
	@SuppressWarnings("unchecked")
	private PersonDT selectPersonHist (long personUid, int versionCtrlNbr)
	throws NEDSSSystemException, NEDSSSystemException
	{
		PersonDT personDT = new PersonDT();
		ArrayList<Object>  arrayList = new ArrayList<Object> ();
		arrayList.add(new Long(personUid));
		arrayList.add(new Integer(versionCtrlNbr));
		try
		{
			// using the preparedStmtMethod from DAOBase
			// pass the dt in for the update
			// pass in an arraylist (ordered list)
			//        with all the parameters for the sql statement
			// pass in the sql you wish to use
			// pass in queryType constant so it will return a RootDTInterface
			arrayList = (ArrayList<Object> )preparedStmtMethod(personDT, arrayList, SELECT_PERSON_HIST, NEDSSConstants.SELECT);
			return personDT = (PersonDT)arrayList.get(0);
		}
		 catch (Exception ex) {
			logger.fatal("personUid: "+personUid+" Exception in selectPerson:  ERROR = " + ex.getMessage(),ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	/**
	 * Inserts the person dt record into the database
	 * @param dt The personDT to be inserted into the database.
	 * @return void
	 */
	private void insertPersonHist(PersonDT personDT)
	  throws NEDSSDAOSysException, NEDSSSystemException
	{
		ArrayList<Object>  arrayList = new ArrayList<Object> ();
		try
		{
			// insert new person into person table
			if (personDT != null)
			{
				  arrayList.add(personDT.getPersonUid());
				  arrayList.add(personDT.getPersonParentUid());
				  arrayList.add(personDT.getVersionCtrlNbr());
				  arrayList.add(personDT.getAdministrativeGenderCd());
				  arrayList.add(personDT.getAddReasonCd());
				  arrayList.add(personDT.getAddTime() );
				  arrayList.add(personDT.getAddUserId());
				  arrayList.add(personDT.getAgeCalc());
				  arrayList.add(personDT.getAgeCalcTime() );
				  arrayList.add(personDT.getAgeCalcUnitCd());
				  arrayList.add(personDT.getAgeCategoryCd());
				  arrayList.add(personDT.getAgeReported());
				  arrayList.add(personDT.getAgeReportedTime() );
				  arrayList.add(personDT.getAgeReportedUnitCd());
				  arrayList.add(personDT.getAsOfDateAdmin());
				  arrayList.add(personDT.getAsOfDateEthnicity());
				  arrayList.add(personDT.getAsOfDateGeneral());
				  arrayList.add(personDT.getAsOfDateMorbidity());
				  arrayList.add(personDT.getAsOfDateSex());
				  arrayList.add(personDT.getBirthGenderCd());
				  arrayList.add(personDT.getBirthOrderNbr());
				  arrayList.add(personDT.getBirthTime() );
				  arrayList.add(personDT.getBirthTimeCalc() );
				  arrayList.add(personDT.getCd());
				  arrayList.add(personDT.getCdDescTxt());
				  arrayList.add(personDT.getCurrSexCd());
				  arrayList.add(personDT.getDeceasedIndCd());
				  arrayList.add(personDT.getDeceasedTime() );
				  arrayList.add(personDT.getDescription());
				  arrayList.add(personDT.getEducationLevelCd());
				  arrayList.add(personDT.getEducationLevelDescTxt());
				  arrayList.add(personDT.getElectronicInd());
				  arrayList.add(personDT.getEthnicGroupInd());
				  arrayList.add(personDT.getLastChgReasonCd());
				  arrayList.add(personDT.getLastChgTime() );
				  arrayList.add(personDT.getLastChgUserId());
				  arrayList.add(personDT.getMaritalStatusCd());
				  arrayList.add(personDT.getMaritalStatusDescTxt());
				  arrayList.add(personDT.getMothersMaidenNm());
				  arrayList.add(personDT.getMultipleBirthInd());
				  arrayList.add(personDT.getOccupationCd());
				  arrayList.add(personDT.getPreferredGenderCd());
				  arrayList.add(personDT.getPrimLangCd());
				  arrayList.add(personDT.getPrimLangDescTxt());
				  arrayList.add(personDT.getRecordStatusCd());
				  arrayList.add(personDT.getRecordStatusTime() );
				  arrayList.add(personDT.getStatusCd());
				  arrayList.add(personDT.getStatusTime());
				  arrayList.add(personDT.getSurvivedIndCd());
				  arrayList.add(personDT.getUserAffiliationTxt());
				  arrayList.add(personDT.getFirstNm());
				  arrayList.add(personDT.getLastNm());
				  arrayList.add(personDT.getMiddleNm());
				  arrayList.add(personDT.getNmPrefix());
				  arrayList.add(personDT.getNmSuffix());
				  arrayList.add(personDT.getPreferredNm());
				  arrayList.add(personDT.getHmStreetAddr1());
				  arrayList.add(personDT.getHmStreetAddr2());
				  arrayList.add(personDT.getHmCityCd());
				  arrayList.add(personDT.getHmCityDescTxt());
				  arrayList.add(personDT.getHmStateCd());
				  arrayList.add(personDT.getHmZipCd());
				  arrayList.add(personDT.getHmCntyCd());
				  arrayList.add(personDT.getHmCntryCd());
				  arrayList.add(personDT.getHmPhoneNbr());
				  arrayList.add(personDT.getHmPhoneCntryCd());
				  arrayList.add(personDT.getHmEmailAddr());
				  arrayList.add(personDT.getCellPhoneNbr());
				  arrayList.add(personDT.getWkStreetAddr1());
				  arrayList.add(personDT.getWkStreetAddr2());
				  arrayList.add(personDT.getWkCityCd());
				  arrayList.add(personDT.getWkCityDescTxt());
				  arrayList.add(personDT.getWkStateCd());
				  arrayList.add(personDT.getWkZipCd());
				  arrayList.add(personDT.getWkCntyCd());
				  arrayList.add(personDT.getWkCntryCd());
				  arrayList.add(personDT.getWkPhoneNbr());
				  arrayList.add(personDT.getWkPhoneCntryCd());
				  arrayList.add(personDT.getWkEmailAddr());
				  arrayList.add(personDT.getSSN());
				  arrayList.add(personDT.getMedicaidNum());
				  arrayList.add(personDT.getDlNum());
				  arrayList.add(personDT.getDlStateCd());
				  arrayList.add(personDT.getRaceCd());
				  arrayList.add(personDT.getRaceSeqNbr());
				  arrayList.add(personDT.getRaceCategoryCd());
				  arrayList.add(personDT.getEthnicityGroupCd());
				  arrayList.add(personDT.getEthnicGroupSeqNbr());
				  arrayList.add(personDT.getAdultsInHouseNbr());
				  arrayList.add(personDT.getChildrenInHouseNbr());
				  arrayList.add(personDT.getBirthCityCd());
				  arrayList.add(personDT.getBirthCityDescTxt());
				  arrayList.add(personDT.getBirthCntryCd());
				  arrayList.add(personDT.getBirthStateCd());
				  arrayList.add(personDT.getRaceDescTxt());
				  arrayList.add(personDT.getEthnicGroupDescTxt());
				  arrayList.add(personDT.getLocalId());
				  arrayList.add(personDT.getEdxInd());  // New added on Jan 18th 2011 Release 411_INV_AUTOCREATE
				  arrayList.add(personDT.getSpeaksEnglishCd());
				  arrayList.add(personDT.getAdditionalGenderCd());
				  arrayList.add(personDT.getEharsId()); 
				  arrayList.add(personDT.getEthnicUnkReasonCd());
				  arrayList.add(personDT.getSexUnkReasonCd());
                                  arrayList.add(personDT.getDedupMatchInd());
                                  arrayList.add(personDT.getGroupNbr());
                                  arrayList.add(personDT.getGroupTime());
				// using the preparedStmtMethod from DAOBase
				// pass the dt in for the update
				// pass in an arraylist (ordered list)
				//        with all the parameters for the sql statement
				// pass in the sql you wish to use
				// update is the same as insert so it uses the same opperation constant
				int resultCount = ((Integer)preparedStmtMethod(personDT, arrayList, INSERT_PERSON_HIST, NEDSSConstants.UPDATE)).intValue();
				if (resultCount != 1)
				{
					logger.error("Error: none or more than one person updated at a time, " + "resultCount = " + resultCount);
					throw new NEDSSConcurrentDataException("NEDSSConcurrentDataException: The data has been modified by other user, please verify!");
				}
			}
		}
		 catch (Exception ex) {
			logger.fatal("Exception in updatePerson() = " + ex.getMessage(),ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}

	/**
	 * Returns the versionCtrlNbr which has been used to initialize this object
	 * @return versionCtrlNbr : Short  The versionCtrlNbr which is a class attribute.
	 */
	public short getVersionCtrlNbr()
	{
	  return this.versionCtrlNbr;
	}

// for testing
/*
	public static void main(String[] strg)
	{
		logger.setLogLevel(6);

		PersonHistDAOImpl personHistDAOImpl = new PersonHistDAOImpl();
		PersonHistItemVO personHistItemVO = new PersonHistItemVO();
		PersonDT personDT = null;
		long personUidLong = 0;
		try
		{
			//**** sql server personuid
			personUidLong = 470006002;
			//int versionctrlnbr = 5;

			//**** oracle server personuid
			//personUidLong = 310326053;


			logger.debug("-----------start-----------------");
//			  personHistItemVO = personHistDAOImpl.getPersonHistItem(new Long(personUidLong), SELECT_PERSON_HIST_ITEMS);

//			  personDT = (PersonDT)personHistDAOImpl.selectPersonHist(personUidLong, 1);

			ArrayList<Object>  arrayList = (ArrayList<Object> )personHistDAOImpl.getPersonHistItemColl(new Long(personUidLong), SELECT_PERSON_HIST_ITEMS);
			Iterator<Object> it = arrayList.iterator();
			while (it.hasNext())
			{
				personHistItemVO = (PersonHistItemVO)it.next();
				logger.debug("----------------------------");
				logger.debug("personHistItemVO.getLastChgTime() = " + personHistItemVO.getLastChgTime());
				logger.debug("personHistItemVO.getVersionCtrlNbr() = " + personHistItemVO.getVersionCtrlNbr());
//				  personDT = (PersonDT)it.next();
//				  logger.debug("personDT.getPersonUid() = " + personDT.getPersonUid());
//				  logger.debug("personDT.getFirstNm() = " + personDT.getFirstNm());
//				  logger.debug("personDT.getLastNm() = " + personDT.getLastNm());
//				  logger.debug("personDT.getAddTime() = " + personDT.getAddTime());
//				  logger.debug("personDT.getRecordStatusTime() = " + personDT.getRecordStatusTime());
//				  logger.debug("personDT.getLocalId() = " + personDT.getLocalId());
			}

//			  personDT.setVersionCtrlNbr(new Integer(3));
//			  personDT.setAddTime(new Timestamp(new java.util.Date().getTime()));
//			  personHistDAOImpl.insertPersonHist(personDT);


			logger.debug("-----------finish-----------------");

		}
		 catch (Exception ex) {
			logger.fatal("Exception in main():  ERROR = " + ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
	}// end of main - for testing
*/
}


