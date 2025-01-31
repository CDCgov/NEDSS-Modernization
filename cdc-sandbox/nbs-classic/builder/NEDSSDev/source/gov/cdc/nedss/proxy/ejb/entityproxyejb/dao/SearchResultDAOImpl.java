/**
 * Name:		SearchResultDAOImpl.java
 * Description:	This is a class determine the DAO implementation based on
 *               the information provided in the deployment descriptor.
 * Copyright:	Copyright (c) 2001In
 * Company: 	Computer Sciences Corporation
 * @author	NEDSS Development Team
 * @version	1.0
 */

package gov.cdc.nedss.proxy.ejb.entityproxyejb.dao;

import java.util.ArrayList;

import gov.cdc.nedss.entity.person.vo.PatientSearchVO;
import gov.cdc.nedss.entity.person.vo.PersonSearchVO;
import gov.cdc.nedss.exception.NEDSSSystemException;
import gov.cdc.nedss.util.DAOBase;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;


public class SearchResultDAOImpl extends DAOBase

{

	// For logging
	static final LogUtils logger = new LogUtils(
			SearchResultDAOImpl.class.getName());
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
	  

	
	public SearchResultDAOImpl() throws NEDSSSystemException {
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

//	// ************************************************************************
//	// ************************************************************************
//	// *********************** NEW FIND PATIENT: START ************************
//	// ************************************************************************
//
	// constant variables to represent various type of data used in patient
	// search
	protected static final int DT_PERSON = 0;
	protected static final int DT_NAME = 1;
	protected static final int DT_ID = 2;
	protected static final int DT_RACE = 3;
	protected static final int DT_ADDRESS = 4;
	protected static final int DT_TELE = 5;
	protected static final int DT_ETHNICITY = 6;
	protected static final int DT_INVESTIGATION = 7;
	protected static final int DT_ABC_CASE_ID = 8;
	protected static final int DT_STATE_CASE_ID = 9;
	protected static final int DT_VACCINATION = 10;
	protected static final int DT_TREATMENT = 11;
	protected static final int DT_LAB_REPORT = 12;
	protected static final int DT_MORBIDITY_REPORT = 13;
	// protected static final int DT_RVCT_STATE_CASE_NUMBER = 14;
	protected static final int DT_CITY_COUNTY_CASE_ID = 15;
	protected static final int DT_ACCESSION_NUMBER = 16;// changes made for event
														// search based on
														// accession number
	protected static final int DT_DOC_NUMBER = 17;// changes made for event search
												// based on doc number
	protected static final int DT_EPILINK = 18;

	protected static final int DT_NOTIFICATION = 19;
	protected static final int DT_INVESTIGATION_CRITERIA = 20;
	protected static final int DT_EVENT_CREATED_UPDATED_BY_USER = 21;
	//protected static final int DT_INVESTIGATOR = 22;
	protected static final int DT_REPORTING_FACILITY = 23;

	
	protected static final int DT_REPORTING_PROVIDER = 24;
	protected static final int DT_NOTIFICATION_STATUS = 25;
	protected static final int DT_DATE_REPORT = 26;
	protected static final int DT_INVESTIGATION_CLOSED_DATE = 27;
	protected static final int DT_INVESTIGATION_CREATE_DATE = 28;
	protected static final int DT_INVESTIGATION_LAST_UPDATE_DATE = 29;
	protected static final int DT_INVESTIGATION_START_DATE = 30;
	protected static final int DT_NOTIFICATION_CREATE_DATE = 31;
	protected static final int DT_RECEIVED_BY_PUBLIC_HEALTH_DATE = 32;
	protected static final int DT_SPECIMEN_COLLECTION_DATE = 33;
	protected static final int DT_LAB_REPORT_CREATE_DATE = 34;
	protected static final int DT_LAB_REPORT_LAST_UPDATE_DATE = 35;
	
	
	
	protected static final int DT_LAB_REPORT_CRITERIA = 36;
	protected static final int DT_INTERNAL_EXTERNAL_USER = 37;
	protected static final int DT_ORDERING_FACILITY = 38;
	protected static final int DT_ORDERING_PROVIDER = 39;
	protected static final int DT_CODED_RESULT_ORGANISM = 40;
	protected static final int DT_RESULTED_TEST = 41;
	
	
	

	

	/**
	 * This method checks, which search criteria were specified by the client
	 * and returns a collection of contants indicating what type of search
	 * criteria were specified.
	 *
	 * @author ykulikova
	 * @param find
	 *            Represents all search criteria specified by the client.
	 * @return Indicates all types of search criteria that were specified by the
	 *         client. The order of the search criteria types is important as it
	 *         will affect how the final search query will be created. The
	 *         search criteria types with the higher ability to limit the final
	 *         result are specified earlier in the list. For example, SSN will
	 *         limit the search result better than RACE.
	 */
	protected ArrayList<Object> computeSearchCriteriaTypes(PatientSearchVO find) {
		ArrayList<Object> result = new ArrayList<Object>();
		try{
			if ((find.getRootExtensionTxt() != null && find.getRootExtensionTxt()
					.trim().length() != 0)
					|| (find.getSsn() != null && find.getSsn().trim().length() != 0)) {
				result.add(new Integer(DT_ID));
			}
	
			if ((find.getPhoneNbrTxt() != null
					&& find.getPhoneNbrTxt().trim().length() != 0)
					|| (find.getEmailAddress() != null && find.getEmailAddress()
					.trim().length() != 0))  {
				result.add(new Integer(DT_TELE));
			}
	
			if ((find.getStreetAddr1() != null && find.getStreetAddr1().trim()
					.length() != 0)
					|| (find.getCityDescTxt() != null && find.getCityDescTxt()
							.trim().length() != 0)
					|| (find.getState() != null && find.getState().trim().length() != 0)
					|| (find.getZipCd() != null && find.getZipCd().trim().length() != 0)) {
				result.add(new Integer(DT_ADDRESS));
			}
	
			if ((find.getLastName() != null && find.getLastName().trim().length() != 0)
					|| (find.getFirstName() != null && find.getFirstName().trim()
							.length() != 0)) {
				result.add(new Integer(DT_NAME));
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
				result.add(new Integer(DT_PERSON));
			}
	
			if ((find.getEthnicGroupInd() != null)
					&& (find.getEthnicGroupInd().trim().length() != 0)) {
				result.add(new Integer(DT_ETHNICITY));
			}
	
			if (find.getRaceCd() != null && find.getRaceCd().trim().length() != 0) {
				result.add(new Integer(DT_RACE));
			}
	
			if(find.isEpilink()){
				result.add(new Integer(DT_EPILINK));
			}
			
			if(find.getReportType()!=null && find.getReportType().equalsIgnoreCase("I"))//Investigation
				if ((find.getConditionSelected() != null && find.getConditionSelected().length > 1
						|| (find.getConditionSelected() != null && find.getConditionSelected().length == 1
								&& !find.getConditionSelected()[0].trim().equals("")))
						|| (find.getProgramArea() != null && find.getProgramArea().length > 1
								|| (find.getProgramArea() != null && find.getProgramArea().length == 1
										&& !find.getProgramArea()[0].trim().equals("")))
						|| (find.getJurisdictionSelected() != null && find.getJurisdictionSelected().length > 1
								|| (find.getJurisdictionSelected() != null && find.getJurisdictionSelected().length == 1
										&& !find.getJurisdictionSelected()[0].trim().equals("")))
						|| find.getPregnantSelected() != null && !find.getPregnantSelected().isEmpty()
						|| find.getInvestigationStatusSelected() != null
								&& !find.getInvestigationStatusSelected().isEmpty()
						|| (find.getOutbreakNameSelected() != null && find.getOutbreakNameSelected().length > 1
								|| (find.getOutbreakNameSelected() != null && find.getOutbreakNameSelected().length == 1
										&& !find.getOutbreakNameSelected()[0].trim().equals("")))
						|| find.getCaseStatusCodedValuesSelected() != null
								&& !find.getCaseStatusCodedValuesSelected().isEmpty()
						|| find.getCurrentProcessCodedValuesSelected() != null
								&& !find.getCurrentProcessCodedValuesSelected().isEmpty()
						|| find.getInvestigatorSelected() != null && !find.getInvestigatorSelected().isEmpty()
						|| ((find.getActType() != null) && find.getActType().equalsIgnoreCase("P10001"))
						|| find.getNotificationCodedValuesSelected() != null
								&& !find.getNotificationCodedValuesSelected().equals("")
						|| (find.getReportType() != null && find.getReportType().equalsIgnoreCase("I")
								&& find.getEventStatusInitialSelected() != null
								&& find.getEventStatusInitialSelected().equalsIgnoreCase("true"))
						|| (find.getReportType() != null && find.getReportType().equalsIgnoreCase("I")
								&& find.getEventStatusUpdateSelected() != null
								&& find.getEventStatusUpdateSelected().equalsIgnoreCase("true"))) {

					result.add(new Integer(DT_INVESTIGATION_CRITERIA));
				}
			

			//Notification
			/*if(find.getNotificationStatusSelected()!=null && !find.getNotificationStatusSelected().equals("")){
				result.add(new Integer(DT_NOTIFICATION_STATUS));
				
			}*/
			//Event Created/Last Updated By User:
			if((find.getDocumentCreateSelected()!=null && !find.getDocumentCreateSelected().equals("")) || (find.getDocumentUpdateSelected()!=null && !find.getDocumentUpdateSelected().equals("") )){
				result.add(new Integer(DT_EVENT_CREATED_UPDATED_BY_USER));
				
			}

		/*	if(find.getInvestigatorSelected()!=null && !find.getInvestigatorSelected().isEmpty()){
				result.add(new Integer(DT_INVESTIGATOR));
				
			}*/
			
			if(find.getReportingFacilitySelected()!=null && !find.getReportingFacilitySelected().isEmpty()){
				result.add(new Integer(DT_REPORTING_FACILITY));
			}
			
			if(find.getOrderingFacilitySelected()!=null && !find.getOrderingFacilitySelected().isEmpty()){
				result.add(new Integer(DT_ORDERING_FACILITY));
			}
			if(find.getOrderingProviderSelected()!=null && !find.getOrderingProviderSelected().isEmpty()){
				result.add(new Integer(DT_ORDERING_PROVIDER));
			}
			if(find.getReportingProviderSelected()!=null && !find.getReportingProviderSelected().isEmpty()){
				result.add(new Integer(DT_REPORTING_PROVIDER));
			}
			

			String dateType = find.getDateType();
			String dateId = find.getDateFrom();
			String dateOperator=find.getDateOperator();
			if (dateType != null && dateType.trim().length() != 0) {
				if ((dateId != null && dateId.trim().length() != 0) || (null != dateOperator && (NEDSSConstants.ADV_SEARCH_EVENT_TIME_LAST_SIX_MONTHS.equalsIgnoreCase(dateOperator) || NEDSSConstants.ADV_SEARCH_EVENT_TIME_LAST_THIRTY_DAYS.equalsIgnoreCase(dateOperator) ||NEDSSConstants.ADV_SEARCH_EVENT_TIME_LAST_SEVEN_DAYS.equalsIgnoreCase(dateOperator))) ) {
					if (dateType.equals(PersonSearchVO.DATE_REPORT)) {
						result.add(new Integer(DT_DATE_REPORT));
					}else
					if (dateType.equals(PersonSearchVO.DATE_INVESTIGATION_CLOSED_DATE)) {
						result.add(new Integer(DT_INVESTIGATION_CLOSED_DATE));
					}else
					if (dateType.equals(PersonSearchVO.DATE_INVESTIGATION_CREATE_DATE)) {
						result.add(new Integer(DT_INVESTIGATION_CREATE_DATE));
					}else
					if (find.getReportType()==null || (find.getReportType()!=null && find.getReportType().equalsIgnoreCase("I")) && 
							dateType.equals(PersonSearchVO.DATE_INVESTIGATION_LAST_UPDATE_DATE)) {
						result.add(new Integer(DT_INVESTIGATION_LAST_UPDATE_DATE));
					}else
					if (dateType.equals(PersonSearchVO.DATE_INVESTIGATION_START_DATE)) {
							result.add(new Integer(DT_INVESTIGATION_START_DATE));
					}else				
						if (dateType.equals(PersonSearchVO.DATE_NOTIFICATION_CREATE_DATE)) {
							result.add(new Integer(DT_NOTIFICATION_CREATE_DATE));
					}else				
						if (dateType.equals(PersonSearchVO.Date_Received_by_Public_Health)) {
							result.add(new Integer(DT_RECEIVED_BY_PUBLIC_HEALTH_DATE));
					}else				
						if (dateType.equals(PersonSearchVO.Date_Specimen_Collection)) {
							result.add(new Integer(DT_SPECIMEN_COLLECTION_DATE));
					}else				
						if (dateType.equals(PersonSearchVO.Lab_Report_Create_Date)) {
							result.add(new Integer(DT_LAB_REPORT_CREATE_DATE));
					}else				
						if (find.getReportType()==null || (find.getReportType()!=null && find.getReportType().equalsIgnoreCase("LR")) && dateType.equals(PersonSearchVO.Last_Update_Date)) {
							result.add(new Integer(DT_LAB_REPORT_LAST_UPDATE_DATE));
					}
					
	
				}
			}
			
			
			String actType = find.getActType();
			String actId = find.getActId();
	
			if (actType != null && actType.trim().length() != 0) {
				if (actId != null && actId.trim().length() != 0) {
					if (actType.equals(PersonSearchVO.ABC_STATE_CASE_ID)) {
						result.add(new Integer(DT_ABC_CASE_ID));
					} else if (actType.equals(PersonSearchVO.STATE_CASE_ID)) {
						result.add(new Integer(DT_STATE_CASE_ID));
					} //Not for investigation
					else if (find.getReportType()==null && actType
							.equals(PersonSearchVO.INVESTIGATION_LOCAL_ID)) {
						result.add(new Integer(DT_INVESTIGATION));
					} else if (actType.equals(PersonSearchVO.VACCINATION_LOCAL_ID)) {
						result.add(new Integer(DT_VACCINATION));
					} else if (actType.equals(PersonSearchVO.LAB_REPORT_LOCAL_ID)) {
						result.add(new Integer(DT_LAB_REPORT));
					} else if (actType
							.equals(PersonSearchVO.MORBIDITY_REPORT_LOCAL_ID)) {
						result.add(new Integer(DT_MORBIDITY_REPORT));
					} else if (actType.equals(PersonSearchVO.TREATMENT_LOCAL_ID)) {
						result.add(new Integer(DT_TREATMENT));
						// } else if
						// (actType.equals(PersonSearchVO.RVCT_STATE_CASE_NUMBER)){
						// result.add(new Integer(DT_RVCT_STATE_CASE_NUMBER));
					} else if (actType.equals(PersonSearchVO.CITY_COUNTY_CASE_ID)) {
						result.add(new Integer(DT_CITY_COUNTY_CASE_ID));
					}
					// changes made for event search based on accession number
					else if (actType
							.equals(PersonSearchVO.Accession_Number_LOCAL_ID)) {
						result.add(new Integer(DT_ACCESSION_NUMBER));
					}
					// changes made for event search based on doc number
					else if (actType.equals(PersonSearchVO.Doc_Number_LOCAL_ID)) {
						result.add(new Integer(DT_DOC_NUMBER));
					}
					// changes made for event search based on notification ID
					else if (actType.equals(PersonSearchVO.NOTIFICATION_ID)) {
						result.add(new Integer(DT_NOTIFICATION));
					}
				}
			}
			//Laboratory Report
			if(find.getReportType()!=null && find.getReportType().equalsIgnoreCase("LR")){
				
				if(
					(find.getProgramArea()!=null && find.getProgramArea().length!=0 ||
					find.getJurisdictionSelected()!=null && find.getJurisdictionSelected().length!=0 ||
					find.getPregnantSelected()!=null && !find.getPregnantSelected().isEmpty()||
					find.getElectronicValueSelected()!=null && find.getElectronicValueSelected().equalsIgnoreCase("true")||
					find.getManualValueSelected()!=null && find.getManualValueSelected().equalsIgnoreCase("true")||
					        (find.getProcessedState() != null &&  find.getProcessedState().equalsIgnoreCase("true") )||
					        (find.getUnProcessedState() != null && find.getUnProcessedState().equalsIgnoreCase("true")) ||
							(find.getEventStatusInitialSelected()!=null && find.getEventStatusInitialSelected().equalsIgnoreCase("true")) ||
							(find.getEventStatusUpdateSelected()!=null && find.getEventStatusUpdateSelected().equalsIgnoreCase("true")||
							(!find.getResultedTestDescriptionWithCodeValue().isEmpty() || !find.getTestDescription().isEmpty())
									)
					)
					)//Lab Report
				result.add(new Integer(DT_LAB_REPORT_CRITERIA));
			
				/*if((find.getActType() != null)
						&&(find.getActType().trim().equalsIgnoreCase("P10009")))
					result.add(new Integer(DT_ACCESSION_NUMBER));*/
			
				
				if(find.getExternalValueSelected().equalsIgnoreCase("false") ||find.getInternalValueSelected().equalsIgnoreCase("false"))
					result.add(new Integer(DT_INTERNAL_EXTERNAL_USER));
				if(!find.getResultDescriptionValue().isEmpty() || !find.getCodeResultOrganismDescriptionValue().isEmpty())
					result.add(new Integer(DT_CODED_RESULT_ORGANISM));
				if (!find.getResultedTestDescriptionWithCodeValue().isEmpty() || !find.getTestDescription().isEmpty())
					result.add(new Integer(DT_RESULTED_TEST));
				
			}
		}catch(Exception ex){
			logger.fatal("Exception = "+ex.getMessage(), ex);
			throw new NEDSSSystemException(ex.toString());
		}
		return result;
	}

}
