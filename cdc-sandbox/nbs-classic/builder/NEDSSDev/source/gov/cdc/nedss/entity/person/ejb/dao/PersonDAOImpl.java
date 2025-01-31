/**
* Name:        PersonDAOImpl.java
* Description:    This is the implementation of NEDSSDAOInterface for the
*               Person value object in the Person entity bean.
*               This class encapsulates all the JDBC calls made by the PersonEJB
*               for a Person object. Actual logic of
*               inserting/reading/updating/deleting the data in relational
*               database tables to mirror the state of PersonEJB is
*               implemented here.
* Copyright:    Copyright (c) 2001
* Company:     Computer Sciences Corporation
* @author    Brent Chen & NEDSS Development Team
* @version    1.0
*/
package gov.cdc.nedss.entity.person.ejb.dao;

import gov.cdc.nedss.entity.person.dt.PersonDT;
import gov.cdc.nedss.entity.person.dt.PersonNameDT;
import gov.cdc.nedss.entity.sqlscript.NEDSSSqlQuery;
import gov.cdc.nedss.exception.NEDSSConcurrentDataException;
import gov.cdc.nedss.exception.NEDSSDAOSysException;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.systemservice.uidgenerator.UidClassCodes;
import gov.cdc.nedss.systemservice.uidgenerator.UidGeneratorHelper;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.DataTables;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;

import java.math.BigDecimal;
import java.sql.*;
import java.util.*;

import javax.ejb.CreateException;
import javax.ejb.DuplicateKeyException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class PersonDAOImpl extends DAOBase
{

    private long personUID = -1;
    static final LogUtils logger = new LogUtils(PersonDAOImpl.class.getName());

    private final String SELECT_BY_GROUP = "select person_uid \"personUid\" from person where group_nbr = ?";

    private final String SELECT_PARENT_PERSON = "select person_uid \"personUid\" from person where person_parent_uid = ? and record_status_cd =  ? and person_uid != person_parent_uid";

    private final String SELECT_ALL_GROUPS = "select distinct group_nbr \"groupNbr\", group_time \"groupTime\" from person where group_nbr is not null";

    private static final String SUBJECT_NAME_USE_CD = "L";
    private static final String USE_CD = "WP";
    private static final String CLASS_CD = "TELE";
    private static final String SELECT_PROVIDER_MATCH =
                                    "select x.person_uid from  " +
                                    "person x, person_name a, " +
                                    "entity_locator_participation b, " +
                                    "tele_locator c " +
                                    "where x.person_uid = a.person_uid " +
                                    "and a.first_nm like ? " +
                                    "and a.last_nm = ? " +
                                    "and a.nm_use_cd = ? " +
                                    "and b.use_cd = ? " +
                                    "and b.class_cd = ? "+
                                    "and b.entity_uid = a.person_uid " +
                                    "and c.tele_locator_uid = b.locator_uid " +
                                    "and c.phone_nbr_txt = ?";

    private static final String SELECT_SUBJECT_MATCH =
                                                    "select " +
                                                    "a.person_uid \"personUid\" " +
                                                    "from person_name n, " +
                                                    "(select person_uid from person " +
                                                    "where curr_sex_cd = ? " +
                                                    "and birth_time = ?)a " +
                                                    "where n.person_uid = a.person_uid " +
                                                    "and n.first_nm = ? " +
                                                    "and n.last_nm = ? " +
                                                    "and n.nm_use_cd = ?";


    private static final String SELECT_PERSON_UID = "SELECT person_UID \"personUid\" FROM " +
                                                   DataTables.PERSON_TABLE +
                                                   " WHERE person_UID = ?";
    
    private static final String SELECT_PERSON = "SELECT person_uid \"personUid\", person_parent_uid \"personParentUid\", administrative_gender_cd \"administrativeGenderCd\", add_reason_cd \"addReasonCd\", add_time \"addTime\", add_user_id \"addUserId\", age_calc \"ageCalc\", age_calc_time \"ageCalcTime\", age_calc_unit_cd \"ageCalcUnitCd\", age_category_cd \"ageCategoryCd\", age_reported \"ageReported\", age_reported_time \"ageReportedTime\", age_reported_unit_cd \"ageReportedUnitCd\", as_of_date_admin \"asOfDateAdmin\", as_of_date_ethnicity \"asOfDateEthnicity\", as_of_date_general \"asOfDateGeneral\",as_of_date_morbidity \"asOfDateMorbidity\", as_of_date_sex \"asOfDateSex\", birth_gender_cd \"birthGenderCd\", birth_order_nbr \"birthOrderNbr\", birth_time \"birthTime\", birth_time_calc \"birthTimeCalc\", cd \"cd\", cd_desc_txt \"cdDescTxt\", curr_sex_cd \"currSexCd\", deceased_ind_cd \"deceasedIndCd\", deceased_time \"deceasedTime\", description \"description\", education_level_cd \"educationLevelCd\", education_level_desc_txt \"educationLevelDescTxt\", electronic_ind \"electronicInd\", ethnic_group_ind \"ethnicGroupInd\", last_chg_reason_cd \"lastChgReasonCd\", last_chg_time \"lastChgTime\", last_chg_user_id \"lastChgUserId\", marital_status_cd \"maritalStatusCd\", " +
    " marital_status_desc_txt \"maritalStatusDescTxt\", mothers_maiden_nm \"mothersMaidenNm\", multiple_birth_ind \"multipleBirthInd\", " +
    "occupation_cd \"occupationCd\", preferred_gender_cd \"preferredGenderCd\", prim_lang_cd \"primLangCd\", prim_lang_desc_txt \"primLangDescTxt\", record_status_cd \"recordStatusCd\", record_status_time \"recordStatusTime\", status_cd \"statusCd\", status_time \"statusTime\", survived_ind_cd \"survivedIndCd\", user_affiliation_txt \"userAffiliationTxt\", " +
    "first_nm \"firstNm\", last_nm \"lastNm\", last_chg_user_id \"lastChgUserId\", middle_nm \"middleNm\", nm_prefix \"nmPrefix\", nm_suffix \"nmSuffix\", preferred_nm \"preferredNm\", hm_street_addr1 \"hmStreetAddr1\", hm_street_addr2 \"hmStreetAddr2\", hm_city_cd \"hmCityCd\", hm_city_desc_txt \"hmCityDescTxt\", hm_state_cd \"hmStateCd\", hm_zip_cd \"hmZipCd\", hm_cnty_cd \"hmCntyCd\", hm_cntry_cd \"hmCntryCd\", hm_phone_nbr \"hmPhoneNbr\", hm_phone_cntry_cd \"hmPhoneCntryCd\", hm_email_addr \"hmEmailAddr\", cell_phone_nbr \"cellPhoneNbr\", wk_street_addr1 \"wkStreetAddr1\", wk_street_addr2 \"wkStreetAddr2\", wk_city_cd \"wkCityCd\", wk_city_desc_txt \"wkCityDescTxt\", wk_state_cd \"wkStateCd\", wk_zip_cd \"wkZipCd\", wk_cnty_cd \"wkCntyCd\", wk_cntry_cd \"wkCntryCd\", wk_phone_nbr \"wkPhoneNbr\", wk_phone_cntry_cd \"wkPhoneCntryCd\", wk_email_addr \"wkEmailAddr\", SSN \"SSN\", medicaid_num \"medicaidNum\", dl_num \"dlNum\", dl_state_cd \"dlStateCd\", race_cd \"raceCd\", race_seq_nbr \"raceSeqNbr\"," +
    " race_category_cd \"raceCategoryCd\", last_chg_time \"LastChgTime\", ethnicity_group_cd \"ethnicityGroupCd\", ethnic_group_seq_nbr \"ethnicGroupSeqNbr\", ethnic_group_desc_txt \"ethnicGroupDescTxt\", adults_in_house_nbr \"adultsInHouseNbr\", " +
    "children_in_house_nbr \"childrenInHouseNbr\", birth_city_cd \"birthCityCd\", birth_city_desc_txt \"birthCityDescTxt\", birth_cntry_cd \"birthCntryCd\", birth_state_cd \"birthStateCd\", race_desc_txt \"raceDescTxt\", local_id \"localId\", version_ctrl_nbr \"versionCtrlNbr\" , group_nbr \"groupNbr\" , group_time \"groupTime\", edx_ind \"edxInd\", speaks_english_cd \"speaksEnglishCd\", additional_gender_cd \"additionalGenderCd\",ehars_id \"eharsId\", ethnic_unk_reason_cd \"ethnicUnkReasonCd\",sex_unk_reason_cd \"sexUnkReasonCd\", dedup_match_ind \"dedupMatchInd\" FROM " + DataTables.PERSON_TABLE;
    
    public static final String SELECT_PERSON_BY_LOCAL_ID = SELECT_PERSON + " WHERE local_id = ?";
    public static final String SELECT_PERSON_BY_UID = SELECT_PERSON + " WHERE person_uid = ?";
    
    public static final String SELECT_PERSON_FOR_VERSION =
        "SELECT person_UID \"personUid\" FROM " + DataTables.PERSON_TABLE +
         " WHERE person_UID = ? and " + " version_ctrl_nbr = ?";
    public static final String INSERT_PERSON = "INSERT INTO " +
                                               DataTables.PERSON_TABLE +
                                                "(person_uid, " + //1
                                                "person_parent_uid, " +
                                                "administrative_gender_cd, " +
                                                "add_reason_cd,  " +
                                                "add_time,  " +
                                                "add_user_id,  " +
                                                "age_calc,  " +
                                                "age_calc_time,  " +
                                                "age_calc_unit_cd,  " +
                                                "age_category_cd,  " + //10
                                                "age_reported,  " +
                                                "age_reported_time,  " +
                                                "age_reported_unit_cd,  " +
                                                "as_of_date_admin, " +
                                                "as_of_date_ethnicity, " +
                                                "as_of_date_general, " +
                                                "as_of_date_morbidity, " +
                                                "as_of_date_sex, " +
                                                "birth_gender_cd,  " +
                                                "birth_order_nbr,  " + //20
                                                "birth_time,  " +
                                                "birth_time_calc,  " +
                                                "cd,  " +
                                                "cd_desc_txt,  " +
                                                "curr_sex_cd,  " +
                                                "deceased_ind_cd,  " +
                                                "deceased_time,  " +
                                                "description,  " +
                                                "education_level_cd,  " +
                                                "education_level_desc_txt,  " + //30
                                                "electronic_ind, " +
                                                "ethnic_group_ind,  " +
                                                "last_chg_reason_cd,  " +
                                                "last_chg_time,  " +
                                                "last_chg_user_id,  " +
                                                "marital_status_cd,  " +
                                                "marital_status_desc_txt,  " +
                                                "mothers_maiden_nm,  " +
                                                "multiple_birth_ind,  " +
                                                "occupation_cd,  " + //40
                                                "preferred_gender_cd,  " +
                                                "prim_lang_cd,  " +
                                                "prim_lang_desc_txt,  " +
                                                "record_status_cd,  " +
                                                "record_status_time,  " +
                                                "status_cd,  " +
                                                "status_time,  " +
                                                "survived_ind_cd,  " +
                                                "user_affiliation_txt, " +
                                                "first_nm,  " + //50
                                                "last_nm,  " +
                                                "middle_nm,  " +
                                                "nm_prefix,  " +
                                                "nm_suffix,  " +
                                                "preferred_nm,  " +
                                                "hm_street_addr1,  " +
                                                "hm_street_addr2,  " +
                                                "hm_city_cd,  " +
                                                "hm_city_desc_txt,  " +
                                                "hm_state_cd,  " + //60
                                                "hm_zip_cd,  " +
                                                "hm_cnty_cd,  " +
                                                "hm_cntry_cd,  " +
                                                "hm_phone_nbr,  " +
                                                "hm_phone_cntry_cd,  " +
                                                "hm_email_addr,  " +
                                                "cell_phone_nbr,  " +
                                                "wk_street_addr1,  " +
                                                "wk_street_addr2,  " +
                                                "wk_city_cd,  " + //70
                                                "wk_city_desc_txt,  " +
                                                "wk_state_cd,  " +
                                                "wk_zip_cd,  " +
                                                "wk_cnty_cd,  " +
                                                "wk_cntry_cd,  " +
                                                "wk_phone_nbr,  " +
                                                "wk_phone_cntry_cd,  " +
                                                "wk_email_addr,  " +
                                                "SSN,  " +
                                                "medicaid_num,  " + //80
                                                "dl_num,  " +
                                                "dl_state_cd,  " +
                                                "race_cd,  " +
                                                "race_seq_nbr,  " +
                                                "race_category_cd,  " +
                                                "ethnicity_group_cd,  " +
                                                "ethnic_group_seq_nbr,  " +
                                                "adults_in_house_nbr,  " +
                                                "children_in_house_nbr,  " +
                                                "birth_city_cd,  " + //90
                                                "birth_city_desc_txt,  " +
                                                "birth_cntry_cd,  " +
                                                "birth_state_cd,  " +
                                                "race_desc_txt,  " +
                                                "ethnic_group_desc_txt,  " +
                                                "local_id,  " +
                                                "edx_ind, "+    // new added on jan 18th 2011
                                                "speaks_english_cd, "+ 
                                                "additional_gender_cd, "+ 
                                                "ehars_id, "+ 
                                                "ethnic_unk_reason_cd, "+
                                                "sex_unk_reason_cd, "+
                                                "version_ctrl_nbr)  " + //98
                                                "VALUES ( " +
                                                " ?, ?, ?, ?, ?, ?, ?, " +
                                                " ?, ?, ?, ?, ?, " +
                                                " ?, ?, ?, ?, ?, " +
                                                " ?, ?, ?, ?, ?, " +
                                                " ?, ?, ?, ?, ?, " +
                                                " ?, ?, ?, ?, ?, " +
                                                " ?, ?, ?, ?, ?, " +
                                                " ?, ?, ?, ?, ?, " +
                                                " ?, ?, ?, ?, ?, " +
                                                " ?, ?, ?, ?, ?, " +
                                                " ?, ?, ?, ?, ?, " +
                                                " ?, ?, ?, ?, ?, " +
                                                " ?, ?, ?, ?, ?, " +
                                                " ?, ?, ?, ?, ?, " +
                                                " ?, ?, ?, ?, ?, " +
                                                " ?, ?, ?, ?, ?, " +
                                                " ?, ?, ?, ?, ?, " +
                                                " ?, ?, ?, ?, ?, " +
                                                " ?, ?, ?, ?, ?,?,?,?,?,?,?)";

    public static final String UPDATE_PERSON = "UPDATE " +
                                               DataTables.PERSON_TABLE +
                                               " set " +

                                                "person_parent_uid = ?, " +
                                                "administrative_gender_cd = ?, " +
                                                "add_reason_cd = ?, " +
                                                "add_time = ?,  " +
                                                "add_user_id = ?,  " +
                                                "age_calc = ?,  " +
                                                "age_calc_time = ?,  " +
                                                "age_calc_unit_cd = ?,  " +
                                                "age_category_cd = ?,  " +
                                                "age_reported = ?,  " + //10
                                                "age_reported_time = ?,  " +
                                                "age_reported_unit_cd = ?,  " +
                                                "as_of_date_admin = ?, " +
                                                "as_of_date_ethnicity = ?," +
                                                "as_of_date_general = ?, " +
                                                "as_of_date_morbidity = ?, " +
                                                "as_of_date_sex = ?, " +
                                                "birth_gender_cd = ?,  " +
                                                "birth_order_nbr = ?,  " +
                                                "birth_time = ?,  " + //20
                                                "birth_time_calc = ?,  " +
                                                "cd = ?,  " +
                                                "cd_desc_txt = ?,  " +
                                                "curr_sex_cd = ?,  " +
                                                "deceased_ind_cd = ?,  " +
                                                "deceased_time = ?,  " +
                                                "description = ?,  " +
                                                "education_level_cd = ?,  " +
                                                "education_level_desc_txt = ?,  " +
                                                "electronic_ind = ?, " + //30
                                                "ethnic_group_ind = ?,  " +
                                                "last_chg_reason_cd = ?,  " +
                                                "last_chg_time = ?,  " +
                                                "last_chg_user_id = ?,  " +
                                                "marital_status_cd = ?,  " +
                                                "marital_status_desc_txt = ?,  " +
                                                "mothers_maiden_nm = ?,  " +
                                                "multiple_birth_ind = ?,  " +
                                                "occupation_cd = ?,  " +
                                                "preferred_gender_cd = ?,  " + //40
                                                "prim_lang_cd = ?,  " +
                                                "prim_lang_desc_txt = ?,  " +
                                                "record_status_cd = ?,  " +
                                                "record_status_time = ?,  " +
                                                "status_cd = ?,  " +
                                                "status_time = ?,  " +
                                                "survived_ind_cd = ?,  " +
                                                "user_affiliation_txt = ?, " +
                                                "first_nm = ?,  " +
                                                "last_nm = ?,  " + //50
                                                "middle_nm = ?,  " +
                                                "nm_prefix = ?,  " +
                                                "nm_suffix = ?,  " +
                                                "preferred_nm = ?,  " +
                                                "hm_street_addr1 = ?,  " +
                                                "hm_street_addr2 = ?,  " +
                                                "hm_city_cd = ?,  " +
                                                "hm_city_desc_txt = ?,  " +
                                                "hm_state_cd = ?,  " +
                                                "hm_zip_cd = ?,  " + //60
                                                "hm_cnty_cd = ?,  " +
                                                "hm_cntry_cd = ?,  " +
                                                "hm_phone_nbr = ?,  " +
                                                "hm_phone_cntry_cd = ?,  " +
                                                "hm_email_addr = ?,  " +
                                                "cell_phone_nbr = ?,  " +
                                                "wk_street_addr1 = ?,  " +
                                                "wk_street_addr2 = ?,  " +
                                                "wk_city_cd = ?,  " +
                                                "wk_city_desc_txt = ?,  " + //70
                                                "wk_state_cd = ?,  " +
                                                "wk_zip_cd = ?,  " +
                                                "wk_cnty_cd = ?,  " +
                                                "wk_cntry_cd = ?,  " +
                                                "wk_phone_nbr = ?,  " +
                                                "wk_phone_cntry_cd = ?,  " +
                                                "wk_email_addr = ?,  " +
                                                "SSN = ?,  " +
                                                "medicaid_num = ?,  " +
                                                "dl_num = ?,  " + //80
                                                "dl_state_cd = ?,  " +
                                                "race_cd = ?,  " +
                                                "race_seq_nbr = ?,  " +
                                                "race_category_cd = ?,  " +
                                                "ethnicity_group_cd = ?,  " +
                                                "ethnic_group_seq_nbr = ?,  " +
                                                "adults_in_house_nbr = ?,  " +
                                                "children_in_house_nbr = ?,  " +
                                                "birth_city_cd = ?,  " +
                                                "birth_city_desc_txt = ?,  " + //90
                                                "birth_cntry_cd = ?,  " +
                                                "birth_state_cd = ?,  " +
                                                "race_desc_txt = ?,  " +
                                                "ethnic_group_desc_txt = ?,  " +
                                                "version_ctrl_nbr = ?,  " +
                                                "dedup_match_ind = ?, "+
                                                "group_nbr = ?, "+
                                                "group_time = ?, "+
                                                "edx_ind = ?, " +
                                                "speaks_english_cd = ?, " +
                                                "additional_gender_cd = ?, " +
                                                "ehars_id = ?, " +
                                                "ethnic_unk_reason_cd = ?, " +
                                                "sex_unk_reason_cd = ?, " +
                                                "local_id = ? "+
                                                "WHERE person_uid = ?  " +
                                                "AND version_ctrl_nbr = ?"; //98

    public static final String DELETE_PERSON = "DELETE FROM " +
                                               DataTables.PERSON_TABLE +
                                               " WHERE person_uid = ?";

    public PersonDAOImpl()
    {
    }

    /**
      * This method creates a new person record and returns the personUID for this person.
      * @J2EE_METHOD  --  create
      * @param obj       the Object
      * @throws NEDSSSystemException
      **/
    public long create(Object obj) throws NEDSSSystemException
    {
    	try{
	        logger.info("Starts create() for a new person in dao...");
	        personUID = insertPerson((PersonDT)obj);
	        ((PersonDT)obj).setItNew(false);
	        ((PersonDT)obj).setItDirty(false);
	        logger.info("Done create() for a new person - return Person UID = " + personUID);
	        return personUID;
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    /**
      * This method is used to update a person record.
      * @J2EE_METHOD  --  store
      * @param obj       the Object
      * @throws NEDSSSystemException
      * @throws NEDSSConcurrentDataException
      **/
    public void store(Object obj) throws NEDSSSystemException, NEDSSConcurrentDataException
    {
    	try{
	        logger.info("Starts store() for a person...");
	        updatePerson((PersonDT)obj);
	        logger.info("Done store() for a person - return: void");
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    /**
      * This method is used to delete a person record.
      * @J2EE_METHOD  --  remove
      * @param personUID       the long
      * @throws NEDSSSystemException
      **/
    public void remove(long personUID) throws NEDSSSystemException
    {
    	try{
    		removePerson(personUID);
    	}catch(Exception ex){
    		logger.fatal("personUID: "+personUID+" Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    /**
      * This method loads a PersonDT object for a given personUID.
      * @J2EE_METHOD  --  loadObject
      * @param personUID       the long
      * @throws NEDSSSystemException
      **/
    public Object loadObject(long personUID) throws NEDSSSystemException
    {
    	try{
	        logger.info("Starts loadObject() for a person...");
	        PersonDT personDT = selectPerson(personUID);
	        personDT.setItNew(false);
	        personDT.setItDirty(false);
	        logger.info("Done loadObject() for a person - return: " + personDT.toString());
	        return personDT;
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    /**
     * Will return a collection of Long objects representing the personUid.
     * @param groupNbr
     * @return Collection<Object>
     */
    @SuppressWarnings("unchecked")
	public Collection<Object> findByGroup(long groupNbr) {
      PersonDT personDT = new PersonDT();
      ArrayList<Object>  arrayList = new ArrayList<Object> ();
      Long lgGroupNbr = new Long(groupNbr);
      arrayList.add(lgGroupNbr);
      try {
        arrayList = (ArrayList<Object> )preparedStmtMethod(personDT, arrayList, SELECT_BY_GROUP, NEDSSConstants.SELECT);
        for(int k = 0; k < arrayList.size(); k++) {
            PersonDT dt = (PersonDT)arrayList.get(k);
            arrayList.set(k, dt.getPersonUid());
        }//end of for
      } catch (Exception ex) {
        logger.fatal("groupNbr: "+groupNbr+" Exception in findByGroup():  ERROR = " + ex.getMessage(),ex);
        throw new NEDSSSystemException(ex.toString());
      }//end of catch
            return arrayList;
    }
    /**
     * Will be used to return a collection of Long objects representing the person_uid
     * where the person_parent_uid is the parameter passed in and is an "ACTIVE" record.
     *
     * @param personParentUid
     * @return Collection<Object> Long objects representing the person_uid associated with the person_parent_uid
     * @throws NEDSSSystemException
     */
    @SuppressWarnings("unchecked")
	public Collection<Object> findByPersonParentUid(long personParentUid) throws NEDSSSystemException{

    PersonDT personDT = new PersonDT();
    ArrayList<Object>  arrayList = new ArrayList<Object> ();
    Long longPersonParentUid = new Long(personParentUid);
    arrayList.add(longPersonParentUid);
    arrayList.add(NEDSSConstants.ACTIVE);
    try
    {
        // using the preparedStmtMethod from DAOBase
        // pass the dt in to hold the uid for comparison
        // pass in an arraylist uid for the sql statement
        // pass in the sql you wish to use
        // pass in queryType constant so it will return a RootDTInterface
        arrayList = (ArrayList<Object> )preparedStmtMethod(personDT, arrayList, SELECT_PARENT_PERSON, NEDSSConstants.SELECT);
        for(int k = 0; k < arrayList.size(); k++) {
            PersonDT dt = (PersonDT)arrayList.get(k);
            arrayList.set(k, dt.getPersonUid());
        }//end of for

    }
     catch (Exception ex) {
        logger.fatal("personParentUid: "+personParentUid+" Exception in findByPersonParentUid():  ERROR = " + ex.getMessage(),ex);
        throw new NEDSSSystemException(ex.toString());
    }
            return arrayList;
    }

    @SuppressWarnings("unchecked")
	public HashMap<Object,Object> findAllDistinctGroups() throws NEDSSSystemException
    {
    	try{
	        PersonDT personDT = new PersonDT();
	        ArrayList<Object>  arrayList = new ArrayList<Object> ();
	        HashMap<Object,Object> hashMap = new HashMap<Object,Object>();
	
	        arrayList = (ArrayList<Object> )preparedStmtMethod(personDT, arrayList, SELECT_ALL_GROUPS, NEDSSConstants.SELECT);
	
	        Iterator<Object> iterator = arrayList.iterator();
	
	        while(iterator.hasNext())
	        {
	            personDT = (PersonDT)iterator.next();
	            hashMap.put(personDT.getGroupNbr(), personDT.getGroupTime());
	        }
	
	        return hashMap;
    	}catch(Exception ex){
    		logger.fatal("Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    public Long findByPrimaryKey(long personUID) throws NEDSSSystemException
    /**
      * This method is used to determine is a person record exists for a given personUID.
      * @J2EE_METHOD  --  findByPrimaryKey
      * @param personUID       the long
      * @throws NEDSSSystemException
      **/
    {
    	try{
	        if (personExists(personUID))
	            return (new Long(personUID));
	        else
	            throw new NEDSSSystemException("No person found for this primary key :" + personUID);
    	}catch(Exception ex){
    		logger.fatal("personUID: "+personUID+" Exception  = "+ex.getMessage(), ex);
    		throw new NEDSSSystemException(ex.toString());
    	}
    }

    /**
      * This method is used determine is a user record exists for a given personUID.
      * @J2EE_METHOD  --  personExists
      * @param personUID       the long
      * @throws NEDSSDAOSysException
      * @throws NEDSSSystemException
      **/
    @SuppressWarnings("unchecked")
	protected boolean personExists(long personUID) throws NEDSSDAOSysException, NEDSSSystemException
    {
        boolean returnValue = false;
        PersonDT personDT = new PersonDT();
        ArrayList<Object>  arrayList = new ArrayList<Object> ();
        Long longPersonUid = new Long(personUID);
        arrayList.add(longPersonUid);

        try
        {
            // using the preparedStmtMethod from DAOBase
            // pass the dt in to hold the uid for comparison
            // pass in an arraylist uid for the sql statement
            // pass in the sql you wish to use
            // pass in queryType constant so it will return a RootDTInterface
            arrayList = (ArrayList<Object> )preparedStmtMethod(personDT, arrayList, SELECT_PERSON_UID, NEDSSConstants.SELECT);
            if(arrayList.size()!=0)
                personDT = (PersonDT)arrayList.get(0);
            if(personDT!=null)
                if(personDT.getPersonUid()!=null)
                    if (personDT.getPersonUid().equals(longPersonUid))
                        returnValue = true; //only true if it exist
        }
         catch (Exception ex) {
            logger.fatal("personUID: "+personUID+" Exception in personExists():  ERROR = " + ex.getMessage(),ex);
            throw new NEDSSSystemException(ex.toString());
        }

        return returnValue;
    }

    /**
     * This method is used retrieve a person_uid with a given local_id
     * @J2EE_METHOD  --  retrievePersonUIDByLocalId
     * @param localId String
     * @throws NEDSSDAOSysException
     * @throws NEDSSSystemException
     **/
   @SuppressWarnings("unchecked")
	public Long retrievePersonUIDByLocalId(String localId) throws NEDSSDAOSysException, NEDSSSystemException
   {
       PersonDT personDT = new PersonDT();
       ArrayList<Object>  arrayList = new ArrayList<Object> ();
       arrayList.add(localId);

       try
       {
           // using the preparedStmtMethod from DAOBase
           // pass the dt in to hold the uid for comparison
           // pass in an arraylist uid for the sql statement
           // pass in the sql you wish to use
           // pass in queryType constant so it will return a RootDTInterface
           arrayList = (ArrayList<Object> )preparedStmtMethod(personDT, arrayList, SELECT_PERSON_BY_LOCAL_ID, NEDSSConstants.SELECT);
           if(arrayList.size()!=0)
               personDT = (PersonDT)arrayList.get(0);
           if(personDT!=null)
               if(personDT.getPersonUid()!=null)
                   return personDT.getPersonUid(); //only true if it exist
       }
       catch (Exception ex) {
           logger.fatal("localId: "+localId+" Exception in personExists():  ERROR = " + ex.getMessage(),ex);
           throw new NEDSSSystemException(ex.toString());
       }

       return null;
   }

   /**
    * This method is used retrieve a PersonDT with a given local_id
    * @J2EE_METHOD  --  retrievePersonDTByLocalId
    * @param localId String
    * @throws NEDSSDAOSysException
    * @throws NEDSSSystemException
    **/
  @SuppressWarnings("unchecked")
	public PersonDT retrievePersonDTByLocalId(String localId) throws NEDSSDAOSysException, NEDSSSystemException
  {
      PersonDT personDT = new PersonDT();
      ArrayList<Object>  arrayList = new ArrayList<Object> ();
      arrayList.add(localId);

      try
      {
          // using the preparedStmtMethod from DAOBase
          // pass the dt in to hold the uid for comparison
          // pass in an arraylist uid for the sql statement
          // pass in the sql you wish to use
          // pass in queryType constant so it will return a RootDTInterface
          arrayList = (ArrayList<Object> )preparedStmtMethod(personDT, arrayList, SELECT_PERSON_BY_LOCAL_ID, NEDSSConstants.SELECT);
          if(arrayList.size()!=0)
              personDT = (PersonDT)arrayList.get(0);
          if(personDT!=null)
              return personDT; //only true if it exist
      }
      catch (Exception ex) {
          logger.fatal("localId: "+localId+" Exception in personExists():  ERROR = " + ex.getMessage(),ex);
          throw new NEDSSSystemException(ex.toString());
      }

      return null;
  }

    
    /**
      * This method is used to determine is a person has the given version number.
      * @J2EE_METHOD  --  personExistsForVersion
      * @param personUID       the long
      * @param vesrion       the int
      * @throws NEDSSSystemException
      **/
    @SuppressWarnings("unchecked")
	public boolean personExistsForVersion(long personUID, int version) throws NEDSSSystemException
    {
        boolean returnValue = false;
        PersonDT personDT = new PersonDT();
        ArrayList<Object>  arrayList = new ArrayList<Object> ();
        Long longPersonUid = new Long(personUID);
        Long longVersion = new Long(version);
        arrayList.add(longPersonUid);
        arrayList.add(longVersion);

        try
        {
            // using the preparedStmtMethod from DAOBase
            // pass the dt in to hold the uid for comparison
            // pass in an arraylist with uid adn versionctrl for the sql statement
            // pass in the sql you wish to use
            // pass in queryType constant so it will return a RootDTInterface
            arrayList = (ArrayList<Object> )preparedStmtMethod(personDT, arrayList, SELECT_PERSON_FOR_VERSION, NEDSSConstants.SELECT);
            if(arrayList.size()!=0)
                personDT = (PersonDT)arrayList.get(0);
            if(personDT!=null)
                if(personDT.getPersonUid()!=null)
                    if (personDT.getPersonUid().equals(longPersonUid))
                        returnValue = true; //only true if it exist
        }
         catch (Exception ex) {
            logger.fatal("personUID: "+personUID+" Exception in personExists():  ERROR = " + ex.getMessage(),ex);
            throw new NEDSSSystemException(ex.toString());
        }
        return returnValue;
    }

    /**
      * This method is used insert a new person record.
      * @J2EE_METHOD  --  insertPerson
      * @param personDT       the PersonDT
      * @throws NEDSSSystemException
      **/
    /**
     *
     * @param personDT
     * @return long
     * @throws NEDSSSystemException
     */
    private long insertPerson(PersonDT personDT) throws NEDSSSystemException
    {
        ArrayList<Object>  arrayList = new ArrayList<Object> ();
        Long personUid = new Long(0);
        String localUid = null;
        UidGeneratorHelper uidGen = null;
        try
        {
            uidGen = new UidGeneratorHelper();
            // new person uid
            personUid = uidGen.getNbsIDLong(UidClassCodes.NBS_CLASS_CODE);
            // new local uid
            localUid = uidGen.getLocalID(UidClassCodes.PERSON_CLASS_CODE);
             //for revision or mpr LocalId is same
            if(personDT.getLocalId() == null || personDT.getLocalId().trim().length() == 0)
              personDT.setLocalId(localUid);
            //for mpr personParentUid is same as personUid
            //if null assume dt is a master parent record else a revision
            if(personDT.getPersonParentUid() == null)
              personDT.setPersonParentUid(personUid);



            // set new person uid in entity table
            personDT.setPersonUid(personUid);
            arrayList.add(personUid);
            arrayList.add(NEDSSConstants.PERSON);

            // using the preparedStmtMethod from DAOBase
            // pass the dt in for the update
            // pass in an arraylist (ordered list)
            //        with all the parameters for the sql statement
            // pass in the sql you wish to use
            // update is the same as insert so it uses the same opperation constant
            int resultCount = ((Integer)preparedStmtMethod(personDT, arrayList, NEDSSSqlQuery.INSERT_ENTITY, NEDSSConstants.UPDATE)).intValue();
            if (resultCount != 1)
            {
                logger.error("Error creating new person UID, " + "resultCount = " + resultCount);
                throw new NEDSSConcurrentDataException("NEDSSConcurrentDataException: The data has been modified by other user, please verify!");
            }
         }
         catch (Exception ex)
         {
             logger.fatal("Exception while inserting  into Entity Table"+ex.getMessage(), ex);
             throw new NEDSSSystemException("Table Name : "+DataTables.ENTITY_TABLE+"  "+ex.getMessage());
         }

         // clear out the previous values
        arrayList.clear();
        try
        {
            // insert new person into person table
            if (personDT != null)
            {
                arrayList.add(personUid);


                arrayList.add(personDT.getPersonParentUid());
                arrayList.add(personDT.getAdministrativeGenderCd());
                arrayList.add(personDT.getAddReasonCd());
                arrayList.add(personDT.getAddTime());
                arrayList.add(personDT.getAddUserId());
                arrayList.add(personDT.getAgeCalc());
                arrayList.add(personDT.getAgeCalcTime());
                arrayList.add(personDT.getAgeCalcUnitCd());
                arrayList.add(personDT.getAgeCategoryCd());//10
                arrayList.add(personDT.getAgeReported());
                arrayList.add(personDT.getAgeReportedTime());
                arrayList.add(personDT.getAgeReportedUnitCd());
                arrayList.add(personDT.getAsOfDateAdmin());
                arrayList.add(personDT.getAsOfDateEthnicity());
                arrayList.add(personDT.getAsOfDateGeneral());
                arrayList.add(personDT.getAsOfDateMorbidity());
                arrayList.add(personDT.getAsOfDateSex());
                arrayList.add(personDT.getBirthGenderCd());
                arrayList.add(personDT.getBirthOrderNbr());
                arrayList.add(personDT.getBirthTime());//20
                arrayList.add(personDT.getBirthTimeCalc());
                arrayList.add(personDT.getCd());
                arrayList.add(personDT.getCdDescTxt());
                arrayList.add(personDT.getCurrSexCd());
                arrayList.add(personDT.getDeceasedIndCd());
                arrayList.add(personDT.getDeceasedTime());
                arrayList.add(personDT.getDescription());
                arrayList.add(personDT.getEducationLevelCd());
                arrayList.add(personDT.getEducationLevelDescTxt());
                arrayList.add(personDT.getElectronicInd());//30
                arrayList.add(personDT.getEthnicGroupInd());
                arrayList.add(personDT.getLastChgReasonCd());
                arrayList.add(personDT.getLastChgTime());
                arrayList.add(personDT.getLastChgUserId());
                arrayList.add(personDT.getMaritalStatusCd());
                arrayList.add(personDT.getMaritalStatusDescTxt());
                arrayList.add(personDT.getMothersMaidenNm());
                arrayList.add(personDT.getMultipleBirthInd());
                arrayList.add(personDT.getOccupationCd());
                arrayList.add(personDT.getPreferredGenderCd());//40
                arrayList.add(personDT.getPrimLangCd());
                arrayList.add(personDT.getPrimLangDescTxt());
                arrayList.add(personDT.getRecordStatusCd());
                arrayList.add(personDT.getRecordStatusTime());
                arrayList.add(personDT.getStatusCd());
                arrayList.add(personDT.getStatusTime());
                arrayList.add(personDT.getSurvivedIndCd());
                arrayList.add(personDT.getUserAffiliationTxt());
                arrayList.add(personDT.getFirstNm());
                arrayList.add(personDT.getLastNm());//50
                arrayList.add(personDT.getMiddleNm());
                arrayList.add(personDT.getNmPrefix());
                arrayList.add(personDT.getNmSuffix());
                arrayList.add(personDT.getPreferredNm());
                arrayList.add(personDT.getHmStreetAddr1());
                arrayList.add(personDT.getHmStreetAddr2());
                arrayList.add(personDT.getHmCityCd());
                arrayList.add(personDT.getHmCityDescTxt());
                arrayList.add(personDT.getHmStateCd());
                arrayList.add(personDT.getHmZipCd());//60
                arrayList.add(personDT.getHmCntyCd());
                arrayList.add(personDT.getHmCntryCd());
                arrayList.add(personDT.getHmPhoneNbr());
                arrayList.add(personDT.getHmPhoneCntryCd());
                arrayList.add(personDT.getHmEmailAddr());
                arrayList.add(personDT.getCellPhoneNbr());
                arrayList.add(personDT.getWkStreetAddr1());
                arrayList.add(personDT.getWkStreetAddr2());
                arrayList.add(personDT.getWkCityCd());
                arrayList.add(personDT.getWkCityDescTxt());//70
                arrayList.add(personDT.getWkStateCd());
                arrayList.add(personDT.getWkZipCd());
                arrayList.add(personDT.getWkCntyCd());
                arrayList.add(personDT.getWkCntryCd());
                arrayList.add(personDT.getWkPhoneNbr());
                arrayList.add(personDT.getWkPhoneCntryCd());
                arrayList.add(personDT.getWkEmailAddr());
                arrayList.add(personDT.getSSN());
                arrayList.add(personDT.getMedicaidNum());
                arrayList.add(personDT.getDlNum());//80
                arrayList.add(personDT.getDlStateCd());
                arrayList.add(personDT.getRaceCd());
                arrayList.add(personDT.getRaceSeqNbr());
                arrayList.add(personDT.getRaceCategoryCd());
                arrayList.add(personDT.getEthnicityGroupCd());
                arrayList.add(personDT.getEthnicGroupSeqNbr());
                arrayList.add(personDT.getAdultsInHouseNbr());
                arrayList.add(personDT.getChildrenInHouseNbr());
                arrayList.add(personDT.getBirthCityCd());
                arrayList.add(personDT.getBirthCityDescTxt());//90
                arrayList.add(personDT.getBirthCntryCd());
                arrayList.add(personDT.getBirthStateCd());
                arrayList.add(personDT.getRaceDescTxt());
                arrayList.add(personDT.getEthnicGroupDescTxt());
               	arrayList.add(personDT.getLocalId());
               	arrayList.add(personDT.getEdxInd());   //New added on Jan 18th 2011 - release 411_INV_AUTOCREATE
               	arrayList.add(personDT.getSpeaksEnglishCd());
               	arrayList.add(personDT.getAdditionalGenderCd());
               	arrayList.add(personDT.getEharsId());
               	arrayList.add(personDT.getEthnicUnkReasonCd());
               	arrayList.add(personDT.getSexUnkReasonCd());
                arrayList.add(personDT.getVersionCtrlNbr());

                // using the preparedStmtMethod from DAOBase
                // pass the dt in for the update
                // pass in an arraylist (ordered list)
                //        with all the parameters for the sql statement
                // pass in the sql you wish to use
                // update is the same as insert so it uses the same opperation constant
                int resultCount = ((Integer)preparedStmtMethod(personDT, arrayList, INSERT_PERSON, NEDSSConstants.UPDATE)).intValue();
                if (resultCount != 1)
                {
                    logger.error("Error: none or more than one person updated at a time, " + "resultCount = " + resultCount);
                    throw new NEDSSConcurrentDataException("NEDSSConcurrentDataException: The data has been modified by other user, please verify!");
                }
            }
            return personUid.longValue();
        }
         catch (Exception ex) {
            logger.fatal("Exception in updatePerson() = " + ex.getMessage(), ex);
            throw new NEDSSSystemException("Table Name : "+DataTables.PERSON_TABLE+" "+ex.toString());
        }
    } //end of inserting person

    /**
      * This method is used update a person record with a given PersonDT.
      * @J2EE_METHOD  --  updatePerson
      * @param personDT       the PersonDT
      * @throws NEDSSSystemException
      * @throws NEDSSConcurrentDataException
      **/
    /**
     * updates person table
     * @param personDT
     */
    private void updatePerson(PersonDT personDT) throws NEDSSSystemException, NEDSSConcurrentDataException
    {
        ArrayList<Object>  arrayList = new ArrayList<Object> ();
         try
        {
            if (personDT != null)
            {
                logger.debug("updating person  ----- personDT.getPersonUid() = " + personDT.getPersonUid());
                arrayList.add(personDT.getPersonParentUid());
                arrayList.add(personDT.getAdministrativeGenderCd());
                arrayList.add(personDT.getAddReasonCd());
                arrayList.add(personDT.getAddTime());
                arrayList.add(personDT.getAddUserId());
                arrayList.add(personDT.getAgeCalc());
                arrayList.add(personDT.getAgeCalcTime());
                arrayList.add(personDT.getAgeCalcUnitCd());
                arrayList.add(personDT.getAgeCategoryCd());
                arrayList.add(personDT.getAgeReported());//10
                arrayList.add(personDT.getAgeReportedTime());
                arrayList.add(personDT.getAgeReportedUnitCd());
                arrayList.add(personDT.getAsOfDateAdmin());
                arrayList.add(personDT.getAsOfDateEthnicity());
                arrayList.add(personDT.getAsOfDateGeneral());
                arrayList.add(personDT.getAsOfDateMorbidity());
                arrayList.add(personDT.getAsOfDateSex());
                arrayList.add(personDT.getBirthGenderCd());
                arrayList.add(personDT.getBirthOrderNbr());
                arrayList.add(personDT.getBirthTime());//20
                arrayList.add(personDT.getBirthTimeCalc());
                arrayList.add(personDT.getCd());
                arrayList.add(personDT.getCdDescTxt());
                arrayList.add(personDT.getCurrSexCd());
                arrayList.add(personDT.getDeceasedIndCd());
                arrayList.add(personDT.getDeceasedTime());
                arrayList.add(personDT.getDescription());
                arrayList.add(personDT.getEducationLevelCd());
                arrayList.add(personDT.getEducationLevelDescTxt());
                arrayList.add(personDT.getElectronicInd());//30
                arrayList.add(personDT.getEthnicGroupInd());
                arrayList.add(personDT.getLastChgReasonCd());
                arrayList.add(personDT.getLastChgTime());
                arrayList.add(personDT.getLastChgUserId());
                arrayList.add(personDT.getMaritalStatusCd());
                arrayList.add(personDT.getMaritalStatusDescTxt());
                arrayList.add(personDT.getMothersMaidenNm());
                arrayList.add(personDT.getMultipleBirthInd());
                arrayList.add(personDT.getOccupationCd());
                arrayList.add(personDT.getPreferredGenderCd());//40
                arrayList.add(personDT.getPrimLangCd());
                arrayList.add(personDT.getPrimLangDescTxt());
                arrayList.add(personDT.getRecordStatusCd());
                arrayList.add(personDT.getRecordStatusTime());
                arrayList.add(personDT.getStatusCd());
                arrayList.add(personDT.getStatusTime());
                arrayList.add(personDT.getSurvivedIndCd());
                arrayList.add(personDT.getUserAffiliationTxt());
                arrayList.add(personDT.getFirstNm());
                arrayList.add(personDT.getLastNm());//50
                arrayList.add(personDT.getMiddleNm());
                arrayList.add(personDT.getNmPrefix());
                arrayList.add(personDT.getNmSuffix());
                arrayList.add(personDT.getPreferredNm());
                arrayList.add(personDT.getHmStreetAddr1());
                arrayList.add(personDT.getHmStreetAddr2());
                arrayList.add(personDT.getHmCityCd());
                arrayList.add(personDT.getHmCityDescTxt());
                arrayList.add(personDT.getHmStateCd());
                arrayList.add(personDT.getHmZipCd());//60
                arrayList.add(personDT.getHmCntyCd());
                arrayList.add(personDT.getHmCntryCd());
                arrayList.add(personDT.getHmPhoneNbr());
                arrayList.add(personDT.getHmPhoneCntryCd());
                arrayList.add(personDT.getHmEmailAddr());
                arrayList.add(personDT.getCellPhoneNbr());
                arrayList.add(personDT.getWkStreetAddr1());
                arrayList.add(personDT.getWkStreetAddr2());
                arrayList.add(personDT.getWkCityCd());
                arrayList.add(personDT.getWkCityDescTxt());//70
                arrayList.add(personDT.getWkStateCd());
                arrayList.add(personDT.getWkZipCd());
                arrayList.add(personDT.getWkCntyCd());
                arrayList.add(personDT.getWkCntryCd());
                arrayList.add(personDT.getWkPhoneNbr());
                arrayList.add(personDT.getWkPhoneCntryCd());
                arrayList.add(personDT.getWkEmailAddr());
                arrayList.add(personDT.getSSN());
                arrayList.add(personDT.getMedicaidNum());
                arrayList.add(personDT.getDlNum());//80
                arrayList.add(personDT.getDlStateCd());
                arrayList.add(personDT.getRaceCd());
                arrayList.add(personDT.getRaceSeqNbr());
                arrayList.add(personDT.getRaceCategoryCd());
                arrayList.add(personDT.getEthnicityGroupCd());
                arrayList.add(personDT.getEthnicGroupSeqNbr());
                arrayList.add(personDT.getAdultsInHouseNbr());
                arrayList.add(personDT.getChildrenInHouseNbr());
                arrayList.add(personDT.getBirthCityCd());
                arrayList.add(personDT.getBirthCityDescTxt());//90
                arrayList.add(personDT.getBirthCntryCd());
                arrayList.add(personDT.getBirthStateCd());
                arrayList.add(personDT.getRaceDescTxt());
                arrayList.add(personDT.getEthnicGroupDescTxt());
                arrayList.add(personDT.getVersionCtrlNbr());
                arrayList.add(personDT.getDedupMatchInd());
                arrayList.add(personDT.getGroupNbr());
                arrayList.add(personDT.getGroupTime());
                arrayList.add(personDT.getEdxInd());
                arrayList.add(personDT.getSpeaksEnglishCd());
                arrayList.add(personDT.getAdditionalGenderCd());
                arrayList.add(personDT.getEharsId());
                arrayList.add(personDT.getEthnicUnkReasonCd());
                arrayList.add(personDT.getSexUnkReasonCd());
                arrayList.add(personDT.getLocalId());
                arrayList.add(personDT.getPersonUid());
                arrayList.add(new Integer(personDT.getVersionCtrlNbr().intValue() - 1));//97



               // using the preparedStmtMethod from DAOBase
               // pass the dt in for the update
               // pass in an arraylist (ordered list)
               //        with all the parameters for the sql statement
               // pass in the sql you wish to use
               // update is the same as insert so it uses the same opperation constant
               int resultCount = ((Integer)preparedStmtMethod(personDT, arrayList, UPDATE_PERSON, NEDSSConstants.UPDATE)).intValue();
                if (resultCount != 1)
                {
                    logger.error("Error: none or more than one person updated at a time, " + "resultCount = " + resultCount);
                    throw new NEDSSConcurrentDataException("NEDSSConcurrentDataException: The data has been modified by other user, please verify!");
                }
            }
        }
         catch (Exception ex) {
            logger.fatal("Exception in updatePerson() = " + ex.getMessage(),ex);
            throw new NEDSSSystemException("Table Name : "+DataTables.PERSON_TABLE+"  "+ex.getMessage() + " For PersonUid :"+personDT.getPersonUid());
        }
    } //end of updating person table

    /**
      * This method is used retrieve a PersonDT for a specific person record, given the personUID.
      * @J2EE_METHOD  --  selectPerson
      * @param personUID       the long
      * @throws NEDSSDAOSysException
      **/
    @SuppressWarnings("unchecked")
	private PersonDT selectPerson(long personUID) throws NEDSSDAOSysException
    {
        PersonDT personDT = new PersonDT();
        ArrayList<Object>  arrayList = new ArrayList<Object> ();
        arrayList.add(new Long(personUID));

        try
        {
            // using the preparedStmtMethod from DAOBase
            // pass the dt in for the update
            // pass in an arraylist (ordered list)
            //        with all the parameters for the sql statement
            // pass in the sql you wish to use
            // pass in queryType constant so it will return a RootDTInterface
            arrayList = (ArrayList<Object> )preparedStmtMethod(personDT, arrayList, SELECT_PERSON_BY_UID, NEDSSConstants.SELECT);

            if (arrayList.size()!=0)
                personDT = (PersonDT)arrayList.get(0);

            return personDT;
        }
         catch (Exception ex) {
            logger.fatal("personUID: "+personUID+" Exception in selectPerson:  ERROR = " + ex.getMessage(),ex);
            throw new NEDSSSystemException(ex.toString());
        }
    } //end of selectPerson

    /**
      * This method is used delete a person record using its personUID.
      * @J2EE_METHOD  --  removePerson
      * @param personUID       the long
      * @throws NEDSSDAOSysException
      **/
    private void removePerson(long personUID)
                       throws NEDSSDAOSysException
    {
        PersonDT personDT = new PersonDT();
        ArrayList<Object>  arrayList = new ArrayList<Object> ();
        arrayList.add(new Long(personUID));

        try
        {

        	logger.debug("**********PersonDAOImpl.removePerson********************");
            // using the preparedStmtMethod from DAOBase
            // pass the dt in for the update
            // pass in an arraylist (ordered list)
            //        with all the parameters for the sql statement
            // pass in the sql you wish to use
            // pass in queryType constant so it will return a RootDTInterface
            preparedStmtMethod(personDT, arrayList, DELETE_PERSON, NEDSSConstants.UPDATE);
        }
         catch (Exception ex) {
            logger.fatal("personUID: "+personUID+" Exception in removePerson:  ERROR = " + ex.getMessage(),ex);
            throw new NEDSSSystemException(ex.toString());
        }
    } //end of removing person

       /**
      * This method determines the count of records for this personUID to determine whether a delete
      * is appropriate.
      * @J2EE_METHOD  --  checkDeletePerson
      * @param personUID       the Long
      **/
    public int checkDeletePerson(Long personUID)
    {
        ArrayList<Object>  inArrayList= new ArrayList<Object> ();
        ArrayList<Object>  outArrayList= new ArrayList<Object> ();

        try
        {
            logger.debug("About to call stored procedure");

            inArrayList.add(personUID);
            outArrayList.add(new Integer(java.sql.Types.INTEGER));

            String sQuery = "{call CHECKDELETEENTITY_SP(?,?)}";
            //logger.debug("********* sQuery = " + sQuery);
            logger.info("sQuery = " + sQuery);

            ArrayList<Object>  returnArrayList = (ArrayList<Object> )callStoredProcedureMethod(sQuery, inArrayList, outArrayList);

            logger.debug("after prepareCall");
            //int count = null;
            int count = Integer.parseInt(returnArrayList.get(0).toString());
            return count;
        }
        catch (Exception se)
        {
        	logger.fatal("Exception = " + se.getMessage(),se);
            throw new NEDSSSystemException("Error: SQLException while obtaining database connection.\n" +
                                           se.getMessage());
        }
    } //end of checking delete function

    /**
      * This method is used return a personUID of a matching subject.
      * @J2EE_METHOD  --  matchingSubject
      * @param personDT       the PersonDT
      * @param personNameDt       the PersonNameDT
      **/
    @SuppressWarnings("unchecked")
	public Long matchingSubject(PersonDT personDT, PersonNameDT personNameDt)
    {
        if (personDT == null || personNameDt == null)
            return null;

        if (personNameDt.getFirstNm() == null || personNameDt.getLastNm() == null ||
            personDT.getBirthTime() == null ||
            personDT.getCurrSexCd() == null )
	    return null;

        Long subjectUidToReturn = null;
        ArrayList<Object>  arrayList = new ArrayList<Object> ();
        arrayList.add(personDT.getCurrSexCd());
        arrayList.add(personDT.getBirthTime());
        arrayList.add(personNameDt.getFirstNm());
        arrayList.add(personNameDt.getLastNm());
        arrayList.add(SUBJECT_NAME_USE_CD);

        try
        {
            // using the preparedStmtMethod from DAOBase
            // pass the dt in to hold the uid for comparison
            // pass in an arraylist with uid adn versionctrl for the sql statement
            // pass in the sql you wish to use
            // pass in queryType constant so it will return a RootDTInterface
            arrayList = (ArrayList<Object> )preparedStmtMethod(personDT, arrayList, SELECT_SUBJECT_MATCH, NEDSSConstants.SELECT);
            if(arrayList.size()!=0)
                personDT = (PersonDT)arrayList.get(0);
            if(personDT!=null)
                if(personDT.getPersonUid()!=null)
                        subjectUidToReturn = personDT.getPersonUid();
        }
         catch (Exception ex) {
            logger.fatal("Exception in personExists():  ERROR = " + ex.getMessage(),ex);
            throw new NEDSSSystemException(ex.toString());
        }
        return subjectUidToReturn;
    }

    /**
      * This method is used return a personUID of a matching ordering provider.
      * @J2EE_METHOD  --  matchingOrderingProvider
      * @param personDT       the PersonDT
      * @param personNameDt       the PersonNameDT
      **/
    @SuppressWarnings("unchecked")
	public Long matchingOrderingProvider(PersonDT personDT, PersonNameDT personNameDt)
    {
        if (personDT == null || personNameDt == null)
            return null;

        if (personNameDt.getFirstNm() == null || personNameDt.getLastNm() == null ||
          personDT.getWkPhoneNbr() == null)
	    return null;

        Long subjectUidToReturn = null;
        ArrayList<Object>  arrayList = new ArrayList<Object> ();
        String beginsWith = personNameDt.getFirstNm().substring(0, 1).concat("%");
        arrayList.add(beginsWith);//1
        arrayList.add(personNameDt.getLastNm()); //2
        arrayList.add(SUBJECT_NAME_USE_CD); //3
        arrayList.add(USE_CD); //4
        arrayList.add(CLASS_CD); //5
        arrayList.add(personDT.getWkPhoneNbr()); //6

        try
        {
            // using the preparedStmtMethod from DAOBase
            // pass the dt in to hold the uid for comparison
            // pass in an arraylist with uid adn versionctrl for the sql statement
            // pass in the sql you wish to use
            // pass in queryType constant so it will return a RootDTInterface
            arrayList = (ArrayList<Object> )preparedStmtMethod(personDT, arrayList, SELECT_PROVIDER_MATCH, NEDSSConstants.SELECT);

            if(arrayList.size()!=0)
                personDT = (PersonDT)arrayList.get(0);

            if(personDT!=null)
                if(personDT.getPersonUid()!=null)
                        subjectUidToReturn = personDT.getPersonUid();
        }
         catch (Exception ex) {
            logger.fatal("Exception in personExists():  ERROR = " + ex.getMessage(),ex);
            throw new NEDSSSystemException(ex.toString());
        }
        return subjectUidToReturn;
    }
/*
    public static void main(String[] strg)
    {
        logger.setLogLevel(6);

        PersonDAOImpl personDAOImpl = new PersonDAOImpl();
        PersonDT personDT = new PersonDT();
        PersonNameDT personNameDT = new PersonNameDT();
        long personUidLong = 0;
        try
        {
            //**** sql server personuid
            personUidLong = 470691900;
            //int versionctrlnbr = 5;

            //**** oracle server personuid
            //personUidLong = 310326053;

//            Long returnedvalue = personDAOImpl.matchingSubject(personDT, personNameDT);

//            logger.debug("returnedvalue = " + returnedvalue);


//            if(personUidLong != 0)
            {
                logger.debug("-----------start-----------------");

                //personDAOImpl.removePerson(personUidLong);
                //ArrayList<Object>  arrayList = (ArrayList<Object> )personDAOImpl.selectPerson(personUidLong);
                //if(arrayList.size()!=0)
                {
//                    personDT = (PersonDT)personDAOImpl.selectPerson(personUidLong);
                logger.debug(personDAOImpl.checkDeletePerson(new Long(personUidLong)));
                logger.debug(personDAOImpl.checkDeletePerson(new Long(personUidLong + 5)));


//                    logger.debug("personDT.getPersonUid() = " + personDT.getPersonUid());
//                    logger.debug("personDT.getFirstNm() = " + personDT.getFirstNm());
//                    logger.debug("personDT.getLastNm() = " + personDT.getLastNm());
//                    logger.debug("personDT.getAddTime() = " + personDT.getAddTime());
//                    logger.debug("personDT.getRecordStatusTime() = " + personDT.getRecordStatusTime());
//                    logger.debug("personDT.getLocalId() = " + personDT.getLocalId());

//                    personDT.setPersonUid(new Long(personUidLong));
//                    personDT.setFirstNm("John");
//                    personDT.setLastNm("Paul");
//                    personDAOImpl.updatePerson(personDT);
                }
                logger.debug("-----------finish-----------------");
            }

        }
         catch (Exception ex) {
            logger.fatal("Exception in main():  ERROR = " + ex);
            throw new NEDSSSystemException(ex.toString());
        }
    }// end of main - for testing
*/
} //end of PersonDAOImpl class

