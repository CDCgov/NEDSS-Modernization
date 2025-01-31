import json

import random
import time
from builtins import print, set, range
from enum import Flag

from faker.generator import random
from selenium.webdriver.common.by import By

from selenium.webdriver.support.select import Select
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from self import self
from src.page_objects.BasePageObject import BasePageObject
from src.page_objects.data_entry.patient import PatientObject
from src.utilities.constants import NBSConstants
from src.utilities.log_config import LogUtils
from src.utilities.db_utilities import DBUtilities

from datetime import datetime, timedelta

from src.utilities.nbs_utilities import NBSUtilities

class stdInvestigation(BasePageObject):
    def __init__(self, driver):

        self.driver = driver
        # self = BasePageObject(self.driver)
        random_number = datetime.now().strftime("%d%m%y%H%M%S")
        self.illness_onset_dt = "02/10/2024"
        self.data = {}
        self.jurisdiction_dict = {
            "130006": "Clayton County",
            "130004": "Cobb County",
            "130005": "Dekalb County",
            "130001": "Fulton County",
            "130002": "Gwinnett County",
            "999999": "Out of system"
        }
        self.hangoutinfo_dict  = {
            "N":"No",
            "R":"Refused to answer",
            "U":"Unknown",
            "Y":"Yes",
            "NASK":"Did not ask",
            "ASKU":"asked but unknown"
        }
        self.Microorganism_Identified =        {
            "1": "Neisseria gonorrhoeae",
            "2": "Neisseria gonorrhoeae, beta lactamase negative",
            "3": "Penicillinase-producing Neisseria gonorrhoeae"
        }

    logger = LogUtils.loggen(__name__)
    db_utilities = DBUtilities()
    
    vaccination_search_submit_id = 'Submit'
    random_number = datetime.now().strftime("%d%m%y%H%M%S")
    add_new_btn_xpath = '//*[@id="subsect_Inv"]/table/tbody/tr/td['.__add__(
        str(NBSConstants.ADD_NEW_OPTION)) + ']/input[2]'
    std_inv_referral_basis_id = "referralBasis"

    gono_inv_referral_basis_value = "A1 - Associate 1"
    std_inv_header = "N1001E"
    std_processing_decision_id = "reviewReason"
    std_processing_decision_value = "Field Follow-up"

    std_case_info_jurisdiction = "INV107"
    std_case_info_jurisdiction_value = "999999"
    std_case_info_system_recipient = "exportFacility"
    std_case_info_doc_type = "documentType"
    std_case_transfer_comments = "NTF137"
    std_case_info_inv_start_date = "INV147"
    std_case_info_initiating_agency = "NBS111"
    std_case_info_date_from_init = "NBS112Icon"
    std_case_info_due_init = "NBS113Icon"
    std_case_info_date_info_sent = "NBS114Icon"
    std_case_info_first_sexual_exposure = "NBS118Icon"
    std_case_info_sexual_frequency = "NBS119"
    std_case_info_last_sexual_exposure = "NBS120Icon"
    std_case_info_first_needle_sharing_expo = "NBS121Icon"
    std_case_info_needle_sharing_frequency = "NBS122"
    std_case_info_last_needle_sharing_expo = "NBS123Icon"
    std_case_info_relationship = "NBS124"
    std_case_info_spouse = "NBS125"
    std_case_info_met_op = "NBS126"
    std_case_info_internet_info = "NBS127"
    std_case_info_date_of_report = "INV111"
    std_case_info_date_reported_to_county = "INV120"
    std_case_info_date_reported_to_state = "INV121"
    std_case_info_reporting_source_type = "INV112"
    std_case_info_reporting_org = "INV183Icon"
    std_case_info_org_search_button = "organizationSearch.nmTxt"
    std_case_info_reporting_provider = "INV181Icon"
    std_case_info_provider_lastname = "lastNameTxt"
    std_case_info_reporting_physician = "INV182Icon"
    std_case_info_physician_lastname = "lastNameTxt"
    std_case_info_reporting_clinic = "NBS291Icon"
    std_case_info_reporting_ishospitalized = "INV128"
    std_case_info_hospital = "INV184Icon"
    std_case_info_admission_date = "INV132"
    std_case_info_discharge_date = "INV133"
    std_case_info_diagnosis_date = "INV136"
    std_case_info_illness_onset_date = "INV137"
    std_case_info_illness_end_date = "INV138"
    std_case_info_illness_duration_units = "INV140"
    std_case_info_age_at_onset = "INV144"
    std_case_info_is_patient_alive = "INV145"
    std_case_info_date_of_death = "INV146"
    std_case_info_treatment_start_date = "STD105"
    std_case_info_date_of_initial_exam = "STD099"
    std_case_info_daycare_facility = "INV148"
    std_case_info_is_food_handler = "INV149"
    std_case_info_part_of_outbreak = "INV150"
    std_case_info_outbreak_name = "INV151"
    std_case_info_disease_acquired_place = "INV152"
    std_case_info_out_of_country = "OOC"
    std_case_info_out_of_state = "OOS"
    std_case_info_out_of_jurisdiction = "OOJ"
    std_case_info_country = "INV153"
    std_case_info_state = "INV154"
    std_case_info_city = "INV155"
    std_case_info_county = "INV156"
    std_case_info_source = "NBS135"
    std_case_info_residence = "INV501"
    std_case_info_reporting_county = "NOT113"
    std_case_info_country_of_exposure = "INV502"
    std_case_info_state_of_exposure = "INV503"
    std_case_info_city_of_exposure = "INV504"
    std_case_info_county_of_exposure = "INV505"
    std_case_info_exposure_location_add = "//*[@id='AddButtonToggleNBS11002']/td/input"
    std_case_info_binational_criteria = "INV515"
    std_case_info_transmission_mode = "INV157"
    std_case_info_detection_mode = "INV159"
    std_case_info_confirmation_method = "INV161"
    std_case_info_confirmation_date = "INV162Icon"
    std_case_info_case_status = "INV163_textbox"
    std_case_info_case_status_value = "P"
    std_case_info_diagnosis_reported = "NBS136"
    std_case_info_case_status_diagnosis_reported_to_cdc = "710 - Syphilis, primary"
    std_case_info_national_condition = "NOT120"
    std_case_info_notification_comments = "INV886"
    std_case_info_neuro_manisfestations = "STD102_textbox"
    std_case_info_neuro_manisfestations_value = "P"
    std_case_info_neuro_manisfestations_label = "STD102L"
    std_case_info_neuro_signs = "102957003"
    std_case_info_other_neuro_signs = "102957003Oth"
    std_case_info_ocular_manifestations = "410478005"
    std_case_info_otic_manisfestations = "PHC1472"
    std_case_info_late_clinical_manisfestations = "72083004"
    std_case_info_pid = "INV179"
    std_case_info_disseminated = "NBS137"
    std_case_info_conjunctivits = "INV361"
    std_case_info_resistant_to = "NBS138"
    std_case_info_general_comments = "INV167"
    std_case_info_country_of_exposure_code = "840"

    """
    STD case management tab details
    """
    std_case_mgmt_legacy_caseID = "INV200"
    std_case_mgmt_internet_followup = "NBS142"
    std_case_mgmt_clinic_code = "NBS144"
    std_case_mgmt_surveillance_notes = "NBS152"
    std_case_mgmt_surveillance_notes_add_button = "//*[@id='AddButtonToggleNBS_INV_STD_UI_18']/td/input"
    std_case_mgmt_surveillance_notes_update_button = '// *[ @ id = "UpdateButtonToggleNBS_INV_STD_UI_18"] / td / input'
    std_case_mgmt_noti_of_exposure = "NBS143_textbox"
    std_case_mgmt_noti_of_exposure_value = "06"
    std_case_mgmt_noti_of_exposure_field_label = "NBS143L"
    std_case_mgmt_noti_plan = "NBS167"
    std_case_mgmt_noti_plan_text_click = "NBS167_textbox"
    std_case_mgmt_actual_referral_paln = "NBS177"
    std_case_mgmt_follow_up_inv = "pageClientVO.answer(NBS161)"
    std_case_mgmt_follow_up_inv_quickcd_value = "local"
    std_case_mgmt_follow_up_inv_quickcd_lookup_button = "NBS161CodeLookupButton"
    std_case_mgmt_follow_up_inv_date_assigned = "NBS162"
    std_case_mgmt_exam_reason = "NBS165"
    std_case_mgmt_provider_diagnosis = "NBS166_textbox"
    std_case_mgmt_provider_diagnosis_value = "710 - Syphilis, primary"
    std_case_mgmt_provider_diagnosis_ghono_value = "300 - Gonorrhea"
    std_case_mgmt_expected_in = "NBS168"
    std_case_mgmt_exam_date = "NBS170Icon"
    std_case_mgmt_exam_follow_up_provider = "pageClientVO.answer(NBS171)"
    std_case_mgmt_exam_follow_up_provider_value = "1"
    std_case_mgmt_exam_follow_up_provider_code_lookup_button = "NBS171CodeLookupButton"
    std_case_mgmt_exam_follow_up_facility = "NBS172Icon"
    std_case_mgmt_case_disposition = "NBS173_textbox"
    std_case_mgmt_internet_outcome_value = "C - Infected, Brought to Treatment"
    std_case_mgmt_case_disposition_date = "NBS174"
    std_case_mgmt_case_disposition_by = "pageClientVO.answer(NBS175)"
    std_case_mgmt_case_disposition_by_value = "local"
    std_case_mgmt_case_disposition_by_quickcd_lookup_button = "NBS175CodeLookupButton"
    std_case_mgmt_case_disposition_supervisor = "pageClientVO.answer(NBS176)"
    std_case_mgmt_case_disposition_supervisor_value = "state"
    std_case_mgmt_case_disposition_superv_quickcd_lookup_button = "NBS176CodeLookupButton"
    std_case_mgmt_internet_outcome = "NBS178"
    std_case_mgmt_follow_up_notes = "NBS185"
    std_case_mgmt_follow_up_notes_add_button = "//*[@id='AddButtonToggleNBS_INV_STD_UI_26']/td/input"
    std_case_mgmt_follow_up_notes_update_button = "//*[@id='UpdateButtonToggleNBS_INV_STD_UI_26']/td/input"
    std_case_mgmt_supervisory_review_notes = "NBS268"
    std_case_mgmt_supervisory_review_notes_add_button = "//*[@id='AddButtonToggleNBS_INV_STD_UI_27']/td/input"
    std_case_mgmt_supervisory_review_notes_update_button = "//*[@id='UpdateButtonToggleNBS_INV_STD_UI_27']/td/input"
    std_case_mgmt_interviewer_selected = "pageClientVO.answer(NBS186)"
    std_case_mgmt_interviewer_selected_value = "1"
    std_case_mgmt_interviewer_code_lookup = "NBS186CodeLookupButton"
    std_case_mgmt_interviewer_date_assigned = "NBS187Icon"
    std_case_mgmt_supervisor = "pageClientVO.answer(NBS190)"
    std_case_mgmt_supervisor_value = "state"
    std_case_mgmt_supervisor_quickcd_lookup_button = "NBS190CodeLookupButton"
    std_case_mgmt_patient_interview_status = "NBS192"
    std_case_mgmt_patient_interview_status_value = "I"
    std_case_mgmt_patient_interview_notes = "NBS195"
    std_case_mgmt_patient_interview_notes_add_button = "//*[@id='AddButtonToggleNBS_INV_STD_UI_30']/td/input"
    std_case_mgmt_patient_interview_notes_update_button = "//*[@id='UpdateButtonToggleNBS_INV_STD_UI_30']/td/input"
    std_case_mgmt_supervisory_review_comments = "NBS200"
    std_case_mgmt_supervisory_review_comments_add_button = "//*[@id='AddButtonToggleNBS_INV_STD_UI_34']/td/input"
    std_case_mgmt_supervisory_review_comments_update_button = "//*[@id='UpdateButtonToggleNBS_INV_STD_UI_34']/td/input"
    no_of_repeats = 2
    std_contract_record_add_interview = "IXS101"
    std_add_interview_clear_reassign ="clearIXS102"
    std_add_interview_type = "IXS105" 
    std_add_interview_location = "IXS106"
    std_add_interview_contacts_named = "STD301"
    std_add_interview_900_site_type = "IXS109"
    std_add_interview_interventions = "IXS110"
    std_add_interview_case_status = "NBS445"
    std_add_interview_notes_add_button = "//*[@id='AddButtonToggleGA25100']/td/input"
    std_update_interview_notes_update_button = "//*[@id='UpdateButtonToggleGA25100']/td/input"
    std_add_interview_physician = "IXS102Icon"
    std_add_interview_notes_add_textarea = 'IXS111'
    std_add_interview_organization_textarea="NBS442Text"
    std_add_interview_organization_button="NBS442CodeLookupButton"
    std_add_interview_provider_textarea="IXS102Text"
    std_add_interview_provider_button="IXS102CodeLookupButton"


    """
     STD core info tab details
     """
    std_core_info_900_status = "NBS153"
    std_core_info_eHARS_record_search = "INV892"
    std_core_info_transmission_category_eHARS = "INV894"
    std_core_info_case_sampled = "INV895"
    """if option is 1 - Completed Risk Profile then below fields ae enabled"""
    std_core_info_was_behavioral_risk_assessed = "NBS229"
    std_core_info_was_behavioral_risk_assessed_name = "NBS229_textbox"
    std_core_info_was_behavioral_risk_assessed_value = "1 - Completed Risk Profile"
    std_core_info_had_sex_with_male = "STD107"
    std_core_info_had_sex_with_female = "STD108"
    std_core_info_had_sex_with_transgen = "NBS230"
    std_core_info_had_sex_with_anonymous = "STD109"
    std_core_info_had_sex_with_condom = "NBS231"
    std_core_info_had_sex_while_intoxicated = "STD111"
    std_core_info_exchanged_drugs_for_sex = "STD112"
    std_core_info_had_sex_with_known_IDU = "STD110"
    std_core_info_been_incarcerated = "STD118"
    std_core_info_injection_drug_use = "STD114_textbox"
    std_core_info_no_drug_use_reported = "NBS233_textbox"
    std_core_info_no_drug_use_reported_value = "N"
    std_core_info_cocaine = "NBS237"
    std_core_info_crack = "NBS235"
    std_core_info_heroin = "NBS239"
    std_core_info_methamphetamine = "NBS234"
    std_core_info_nitrates = "NBS236"
    std_core_info_erectile_dysfunction_med = "NBS238"
    std_core_info_other_drug_used = "NBS240_textbox"
    std_core_info_other_drug_used_value = "Y"
    """if above option is yes below field is enable"""
    std_core_info_specify_other_drug_used = "STD300"
    std_core_info_places_to_meet_partners = "NBS242_textbox"
    std_core_info_places_to_meet_partners_value = "N"
    std_core_info_places_to_have_sex = "NBS244"
    std_core_info_female_partners = "NBS223"
    std_core_info_male_partners = "NBS225"
    std_core_info_trans_gen = "NBS227"
    std_core_info_female_partners_interview_period = "NBS129"
    std_core_info_male_partners_interview_period = "NBS131"
    std_core_info_transgen_partners_interview_period = "NBS133"
    std_core_info_met_through_internet = "STD119"
    std_core_info_target_population = "NBS271"
    std_core_info_is_test_performed = "NBS275_textbox"
    std_core_info_is_test_performed_value = "Y"
    std_core_info_test_type = "INV290_textbox"
    std_core_info_test_type_value = "Rapid Plasma Reagin (RPR)"
    std_core_info_test_result = "INV291"
    std_core_info_test_coded_quantitative = "STD123_1"
    std_core_info_test_result_quantitative = "LAB628"
    std_core_info_test_results_units = "LAB115"
    std_core_info_lab_result_date = "LAB167Icon"
    std_core_info_specimen_source = "LAB165"
    std_core_info_other_specimen_source = "pageClientVO.answer(LAB165Oth)"
    std_core_info_specimen_coll_date = "338822Icon"
    std_core_info_add_button = "//*[@id='AddButtonToggleNBS_INV_STD_UI_71']/td/input"
    std_core_info_update_button = "// *[ @ id = 'UpdateButtonToggleNBS_INV_STD_UI_71'] / td / input"

    std_core_info_ast_microorganism_in_isolate = "LABAST1"
    std_core_info_ast_isolate_identifier = "LABAST2"
    std_core_info_ast_specimen_type = "LABAST3"
    std_core_info_ast_other_specimen_type = "LABAST3Oth"
    std_core_info_ast_specimen_collection_site = "LABAST4"
    std_core_info_ast_other_specimen_collection_site = "LABAST4Oth"
    std_core_info_ast_specimen_collection_date = "LABAST5Icon"
    std_core_info_ast_type = "LABAST6"
    std_core_info_ast_method = "LABAST7"
    std_core_info_ast_interpretation = "LABAST8"
    std_core_info_ast_result_coded = "LABAST11"
    std_core_info_ast_result_quantitative_value = "LABAST9"
    std_core_info_ast_test_results_unit = "LABAST10"
    std_core_info_ast_add_button = "//*[@id='AddButtonToggleNBS_INV_STD_UI_72']/td/input"
    std_core_info_ast_update_button = "//*[@id='UpdateButtonToggleNBS_INV_STD_UI_72']/td/input"

    std_core_info_source = "NBS246"
    std_core_info_onset_date = "NBS247Icon"
    std_core_info_sign_symptom = "INV272"
    std_core_info_atomic_site = "STD121"
    std_core_info_atomic_site_value = "PHC1170"
    std_core_info_another_anatomic_site = "NBS248"
    std_core_info_signs_symptoms_add_button = "//*[@id='AddButtonToggleNBS_INV_STD_UI_61']/td/input"
    std_core_info_signs_symptoms_update_button = "//*[@id='UpdateButtonToggleNBS_INV_STD_UI_61']/td/input"
    """set option to yes"""
    std_core_info_previous_STD_history = "STD117_textbox"
    std_core_info_previous_STD_history_value = "Y"
    std_core_info_previous_condition = "NBS250"
    std_core_info_diagnosis_date = "NBS251Icon"
    std_core_info_treatment_date = "NBS252Icon"
    std_core_info_confirmation = "NBS253"
    std_core_info_previous_history_button = "//*[@id='AddButtonToggleNBS_INV_STD_UI_64']/td/input"
    std_core_info_previous_history_update_button = "//*[@id='UpdateButtonToggleNBS_INV_STD_UI_64']/td/input"
    std_core_info_enrolled_in_partner_services = "NBS257"
    """set previous 900 test option to YES """
    std_core_info_previous_900_test = "NBS254_textbox"
    std_core_info_self_reported_result_id = "STD106"
    std_core_info_self_reported_result_name = "STD106_textbox"
    std_core_info_self_reported_result_value = "Positive-Self Report"
    ynu_random_option = "Y"
    std_core_info_date_last_900_test = "NBS259Icon"
    std_core_info_refer_for_test = "NBS260_textbox"
    std_core_info_referral_date = "NBS261Icon"
    std_core_info_900_test = "NBS262_textbox"
    std_core_info_900_test_sample_date = "NBS450Icon"
    std_core_info_900_result = "NBS263"
    std_core_info_result_provided = "NBS265"
    std_core_info_post_test_counselling = "NBS264_textbox"
    std_core_info_patient_tested_for_syphillis = "NBS447_textbox"
    std_core_info_syphillis_test_result = "NBS448"
    std_core_info_refer_for_care = "NBS266_textbox"
    std_core_info_keep_appointment_id = "NBS267"
    std_core_info_keep_appointment_name = "NBS267_textbox"
    std_core_info_keep_appointment_value = "6-Lost to Follow-up"
    std_core_info_appointment_date = "NBS302Icon"
    std_core_info_is_client_on_prEP = "NBS443_textbox"
    std_core_info_referred_to_prEP_provider = "NBS446_textbox"
    std_core_info_anti_viral_therapy = "NBS255_textbox"
    std_core_info_anti_viral_therapy_ever = "NBS256_textbox"
    std_core_info_shared_injection = "NBS232_textbox"

    """
    STD contract tracing tab
    """
    std_contract_tracing_inv_priority = "NBS055"
    std_contract_tracing_infectious_from = "NBS056Icon"
    std_contract_tracing_infectious_to = "NBS057Icon"
    std_contract_tracing_contact_inv_status = "NBS058"
    std_contract_tracing_inv_comments = "NBS059"
    
    """
    Add new contact Record
    """
    add_patient_general_comments = "cTContactClientVO.answer(DEM196)"
    add_patient_last_name = "cTContactClientVO.answer(DEM102)"
    add_patient_first_name = "cTContactClientVO.answer(DEM104)"
    add_patient_middle_name = "cTContactClientVO.answer(DEM105)"
    # add_patient_name_suffix = "//*[@id='NBS_UI_7']/tbody/tr[4]/td[2]/input"
    add_patient_date_of_birth_icon = "DEM115Icon"
    add_patient_current_sex = "//*[@id='DEM113']"
    is_patient_deceased = "//*[@id='DEM127']"
    check_patient_deceased_enabled = "cTContactClientVO.answer(DEM128)"
    patient_deceased_date_icon = "DEM128Icon"
    patient_martial_status = '//*[@id="DEM140"]'
    home_patient_address_street1 = "cTContactClientVO.answer(DEM159)"
    home_patient_address_street2 = "cTContactClientVO.answer(DEM160)"
    home_patient_address_city = "cTContactClientVO.answer(DEM161)"
    home_patient_address_state = "//*[@id='DEM162']"
    home_patient_address_postal = "cTContactClientVO.answer(DEM163)"
    home_patient_address_county = "//*[@id='DEM165']"
    home_patient_address_census = "cTContactClientVO.answer(DEM168)"
    home_patient_address_country = "//*[@id='DEM167']"
    
    work_patient_address_street1 = "cTContactClientVO.answer(DEM159_W)"
    work_patient_address_street2 = "cTContactClientVO.answer(DEM160_W)"
    work_patient_address_city = "cTContactClientVO.answer(DEM161_W)"
    work_patient_address_state = "//*[@id='DEM162_W']"
    work_patient_address_postal = "cTContactClientVO.answer(DEM163_W)"
    work_patient_address_county = "//*[@id='DEM165_W']"
    work_patient_address_census = "cTContactClientVO.answer(DEM168_W)"
    work_patient_address_country = "//*[@id='DEM167_W']"
    work_address_comments = "cTContactClientVO.answer(DEM175_W)"
    
    patient_home_phone = "cTContactClientVO.answer(DEM177)"
    patient_work_phone = "cTContactClientVO.answer(NBS002)"
    patient_work_phone_ext = "cTContactClientVO.answer(NBS003)"
    patient_cell_phone = "cTContactClientVO.answer(NBS006)"
    patient_email = "cTContactClientVO.answer(DEM182)"
    
    patient_indian_alaskan_race = "cTContactClientVO.americanIndianAlskanRace"
    patient_asian_race = "cTContactClientVO.asianRace"
    patient_african_race = "cTContactClientVO.africanAmericanRace"
    patient_hawaiian_race = "cTContactClientVO.hawaiianRace"
    patient_white_race = "cTContactClientVO.whiteRace"
    patient_other_race = "cTContactClientVO.otherRace"
    patient_refused_to_answer = "cTContactClientVO.refusedToAnswer"
    patient_not_asked = "cTContactClientVO.notAsked"
    patient_unknown_race = "cTContactClientVO.unKnownRace"
    
    contact_record_relationship = "CON103"
    std_inv_referral_basis_value = "P3 - Partner, Both"
    
    relationship_with_patient = "CON141_textbox"
    contact_record_named = "CON143"
    contact_health_status = "CON115"
    contact_record_height = "NBS155"
    contact_record_size = "NBS156"
    contact_record_hair_name ="cTContactClientVO.answer(NBS157)"
    contact_record_hair_value = "black"
    contact_record_complexion_name = "cTContactClientVO.answer(NBS158)"
    contact_record_complexion_value = "fair"
    other_identifying_info = "NBS159"
    follow_up_date = "CON146"
    contact_record_procession_decision = "CON145"
    contact_record_procession_decision_value = "FF"
    disposition_clear_reassign_button = "CON137CodeClearButton"
    assign_ivestigator = "cTContactClientVO.answer(CON137)"
    assign_ivestigator_lookup_button = "CON137CodeLookupButton"
    date_assigned = "CON138"
    disposition_date = "CON140"
    assign_provider = "cTContactClientVO.answer(CON147)"
    assign_provider_lookup_button = "CON147CodeLookupButton"
    disposition_source = "NBS135"
    contact_record_comments = "CON133"
    field_followup_inv = "CONINV180Text"
    field_followup_lookup_button = "CONINV180CodeLookupButton"
    date_assigned_to_inv = "INV110"
    
    
    
    
    """inv
    std inv submit
    """
    std_investigation_submit = "//*[@id='SubmitTop']"

    def setTodayDate(self, type, type_val):
        now = datetime.now()
        today = now.strftime("%m-%d-%Y")
        element = self.driver.find_element(type, type_val)
        element.clear()
        element.send_keys(today)

    def switch_to_current_window(self):
        current_window = self.driver.window_handles[-1]
        self.driver.switch_to.window(current_window)
        return True

    def switch_to_original_window(self):
        self.driver.switch_to.window(self.driver.window_handles[0])
        return True

    def setOrgLastName(self):
        self.driver.find_element(By.NAME, self.std_case_info_org_search_button).clear()
        self.driver.find_element(By.NAME, self.std_case_info_org_search_button).send_keys("%")

    def set_organization(self):
        self.setOrgLastName()
        self.driver.find_element(By.NAME, "Submit").click()
        self.driver.find_element(By.LINK_TEXT, 'Select').click()
        self.switch_to_original_window()
        return True

    def click_search_organization(self, elm_type, elm_type_code):
        self.driver.find_element(elm_type, elm_type_code).click()
        time.sleep(1)

    def click_search_provider(self, elm_type, elm_type_code):
        self.driver.find_element(elm_type, elm_type_code).click()
        time.sleep(2)

    def setProviderLastName(self):
        # self.driver.find_element(By.ID, self.std_case_info_provider_lastname).clear()
        self.driver.find_element(By.ID, self.std_case_info_provider_lastname).send_keys("%")
        time.sleep(1)

    def set_provider(self):
        self.setProviderLastName()
        self.driver.find_element(By.NAME, "Submit").click()
        self.driver.find_element(By.LINK_TEXT, 'Select').click()
        self.switch_to_original_window()
        return True

    def setPhysicianLastName(self):
        self.driver.find_element(By.ID, self.std_case_info_physician_lastname).clear()
        self.driver.find_element(By.ID, self.std_case_info_physician_lastname).send_keys("%")
        time.sleep(1)

    def set_physician(self):
        self.setPhysicianLastName()
        self.driver.find_element(By.NAME, "Submit").click()
        self.driver.find_element(By.LINK_TEXT, 'Select').click()
        self.switch_to_original_window()
        return True

    def set_case_info(self, Flag):
        self.set_ooj_initiating_agency_information(Flag)
        self.set_reporting_information()
        self.set_clinical_information(Flag)

    def set_edit_case_info(self):
        self.set_edit_ooj_initiating_agency_information(Flag)
        self.set_edit_reporting_information()
        self.set_edit_clinical_information()

    def set_edit_ooj_initiating_agency_information(self, Flag):
        # calendar date
        self.selectCalendarDate(NBSConstants.TYPE_CODE_ID, self.std_case_info_date_from_init)
        self.selectCalendarDate(NBSConstants.TYPE_CODE_ID, self.std_case_info_due_init)
        self.selectCalendarDate(NBSConstants.TYPE_CODE_ID, self.std_case_info_date_info_sent)
        # relationship data commented as the below feilds are enabled with different referral selections
        if Flag == True:
            self.selectCalendarDate(NBSConstants.TYPE_CODE_ID, self.std_case_info_first_sexual_exposure)
            self.driver.find_element(By.ID, self.std_case_info_sexual_frequency).clear()
            ran_int = NBSUtilities.generate_random_int(2)
            self.driver.find_element(By.ID, self.std_case_info_sexual_frequency).send_keys(
                ran_int)
            self.selectCalendarDate(NBSConstants.TYPE_CODE_ID, self.std_case_info_last_sexual_exposure)
            self.selectCalendarDate(NBSConstants.TYPE_CODE_ID,
                                                   self.std_case_info_first_needle_sharing_expo)
            self.driver.find_element(By.ID, self.std_case_info_needle_sharing_frequency).clear()
            self.driver.find_element(By.ID, self.std_case_info_needle_sharing_frequency).send_keys(
                NBSUtilities.generate_random_int(2))
            self.selectCalendarDate(NBSConstants.TYPE_CODE_ID,
                                                   self.std_case_info_last_needle_sharing_expo)

        # Relationship data
        self.set_and_return_dropdown_value(self.std_case_info_relationship)
        self.set_and_return_dropdown_value(self.std_case_info_spouse)
        self.set_and_return_dropdown_value(self.std_case_info_met_op)

    def set_ooj_initiating_agency_information(self, Flag):
        self.driver.find_element(By.ID, "tabs0head1").click()
        self.set_jurisdiction()
        time.sleep(1)
        self.set_initiating_agency()
        time.sleep(1)
        # calendar date

        self.selectCalendarDate(NBSConstants.TYPE_CODE_ID, self.std_case_info_date_from_init)
        self.selectCalendarDate(NBSConstants.TYPE_CODE_ID, self.std_case_info_due_init)
        self.selectCalendarDate(NBSConstants.TYPE_CODE_ID, self.std_case_info_date_info_sent)
        # relationship data commented as the below fields are enabled with different referral selections
        if Flag == True:
            self.selectCalendarDate(NBSConstants.TYPE_CODE_ID, self.std_case_info_first_sexual_exposure)
            self.driver.find_element(By.ID, self.std_case_info_sexual_frequency).clear()
            ran_int = NBSUtilities.generate_random_int(2)
            self.driver.find_element(By.ID, self.std_case_info_sexual_frequency).send_keys(
                ran_int)
            self.data[self.std_case_info_sexual_frequency]=ran_int

            self.selectCalendarDate(NBSConstants.TYPE_CODE_ID, self.std_case_info_last_sexual_exposure)
            self.selectCalendarDate(NBSConstants.TYPE_CODE_ID,
                                                   self.std_case_info_first_needle_sharing_expo)
            self.driver.find_element(By.ID, self.std_case_info_needle_sharing_frequency).clear()
            self.driver.find_element(By.ID, self.std_case_info_needle_sharing_frequency).send_keys(
                NBSUtilities.generate_random_int(2))
            self.selectCalendarDate(NBSConstants.TYPE_CODE_ID,
                                                   self.std_case_info_last_needle_sharing_expo)

        # Relationship data
        self.set_and_return_dropdown_value(self.std_case_info_relationship)
        self.set_and_return_dropdown_value(self.std_case_info_spouse)
        self.set_and_return_dropdown_value(self.std_case_info_met_op)

    def set_reporting_information(self):
        self.setTodayDate(By.ID, self.std_case_info_date_of_report)
        self.data[self.std_case_info_date_of_report] = str(self.get_todaysdate())
        self.setTodayDate(By.ID, self.std_case_info_date_reported_to_county)
        self.data[self.std_case_info_date_reported_to_county] = str(self.get_todaysdate())
        self.setTodayDate(By.ID, self.std_case_info_date_reported_to_state)
        self.data[self.std_case_info_date_reported_to_state] = str(self.get_todaysdate())
        value = self.set_and_return_dropdown_value(self.std_case_info_reporting_source_type)
        self.data[self.std_case_info_reporting_source_type] = value
        # search organization
        self.click_search_organization(By.ID, self.std_case_info_reporting_org)
        self.switch_to_current_window()
        self.set_organization()
        self.click_search_provider(By.ID, self.std_case_info_reporting_provider)
        self.switch_to_current_window()
        self.set_provider()
        self.click_search_provider(By.ID, self.std_case_info_reporting_physician)
        self.switch_to_current_window()
        self.set_physician()
        self.click_search_organization(By.ID, self.std_case_info_reporting_clinic)
        self.switch_to_current_window()
        self.set_organization()


    def set_hsp_adm_date(self, type_code, type_val, new_hsp_adm):
        element = self.driver.find_element(type_code, type_val)
        element.clear()
        element.send_keys(new_hsp_adm)

    def set_discharge_date(self):
        self.set_hsp_adm_date(By.NAME, self.std_case_info_discharge_date, datetime.now().strftime("%m-%d-%Y"))

    def set_clinical_information(self, Flag):
        # self.set_and_return_dropdown_value(self.std_case_info_reporting_ishospitalized)
        hospitalization_option_value = self.set_and_return_dropdown_value(
            self.std_case_info_reporting_ishospitalized)
        self.data[self.std_case_info_reporting_ishospitalized]= hospitalization_option_value
        if hospitalization_option_value == "Y":
            self.click_search_organization(By.ID, self.std_case_info_hospital)
            self.switch_to_current_window()
            self.set_organization()
            now = datetime.now()
            earlier = now - timedelta(days=3)
            adm_date = earlier.strftime("%m-%d-%Y")
            self.set_hsp_adm_date(By.ID, self.std_case_info_admission_date, adm_date)
            # self.set_discharge_date()
        self.set_condition_details()
        self.set_epidemiologic_details(Flag)

    def set_condition_details(self):

        # diagnosis date
        self.driver.find_element(By.ID, self.std_case_info_diagnosis_date).clear()
        self.driver.find_element(By.ID, self.std_case_info_diagnosis_date).send_keys(
            datetime.now().strftime("%m/%d/%Y"))
        self.data[self.std_case_info_diagnosis_date] = str(self.get_todaysdate())
        # illness onset date
        self.driver.find_element(By.ID, self.std_case_info_illness_onset_date).clear()
        self.driver.find_element(By.ID, self.std_case_info_illness_onset_date).send_keys(self.illness_onset_dt)
        self.data[self.std_case_info_illness_onset_date] = str(self.get_todaysdate())
        # illness end date
        self.driver.find_element(By.ID, self.std_case_info_illness_end_date).clear()
        self.driver.find_element(By.ID, self.std_case_info_illness_end_date).send_keys(
            datetime.now().strftime("%m/%d/%Y"))
        self.data[self.std_case_info_illness_end_date] = str(self.get_todaysdate())

        std_case_info_is_patient_alive = self.set_and_return_dropdown_value(self.std_case_info_is_patient_alive)
        self.data[self.std_case_info_is_patient_alive] = std_case_info_is_patient_alive

        if std_case_info_is_patient_alive == "Y":
            self.driver.find_element(By.ID, self.std_case_info_date_of_death).send_keys(
                datetime.now().strftime("%m/%d/%Y"))
            self.data[self.std_case_info_date_of_death] = str(self.get_todaysdate())

        self.driver.find_element(By.ID, self.std_case_info_treatment_start_date).send_keys(
            datetime.now().strftime("%m/%d/%Y"))
        self.data[self.std_case_info_treatment_start_date] = str(self.get_todaysdate())

        self.driver.find_element(By.ID, self.std_case_info_date_of_initial_exam).send_keys(
            datetime.now().strftime("%m/%d/%Y"))
        self.data[self.std_case_info_date_of_initial_exam] = str(self.get_todaysdate())


    def set_epidemiologic_details(self, Flag):
        # patient associated with daycare
        element = self.driver.find_element(By.ID, self.std_case_info_daycare_facility)
        # print("Element type",element.tag_name)
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        Select(element).select_by_value("Y")

        element = self.driver.find_element(By.ID, self.std_case_info_is_food_handler)
        # print("Element type",element.tag_name)
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        Select(element).select_by_value("Y")

        # case part of an outbreak
        part_of_outbreak_value = self.set_and_return_dropdown_value(self.std_case_info_part_of_outbreak)
        self.data[self.std_case_info_part_of_outbreak] = part_of_outbreak_value

        self.set_disease_acquired()
        self.set_and_return_dropdown_value(self.std_case_info_source)
        self.set_and_return_dropdown_value(self.std_case_info_residence)
        self.set_and_return_dropdown_value(self.std_case_info_reporting_county)
        self.set_exposure_location(self.no_of_repeats)
        self.set_binational_reporting_criteria()
        self.set_and_return_dropdown_value(self.std_case_info_transmission_mode)
        self.set_and_return_dropdown_value(self.std_case_info_detection_mode)
        self.set_std_case_info_confirmation_method()
        self.selectCalendarDate(NBSConstants.TYPE_CODE_ID, self.std_case_info_confirmation_date)
        self.driver.find_element(By.NAME, self.std_case_info_case_status).send_keys(self.std_case_info_case_status_value)
        self.data["INV163"]= self.std_case_info_case_status_value
        if Flag == False:
            self.std_case_info_case_status_diagnosis_reported_to_cdc = "300 - Gonorrhea"
            self.driver.find_element(By.NAME, "NBS136_textbox").send_keys(self.std_case_info_case_status_diagnosis_reported_to_cdc)
            self.data["NBS136"] = self.std_case_info_case_status_diagnosis_reported_to_cdc
        else:
            self.driver.find_element(By.NAME, "NBS136_textbox").send_keys(self.std_case_info_case_status_diagnosis_reported_to_cdc)
            self.data["NBS136"] = self.std_case_info_case_status_diagnosis_reported_to_cdc
        self.set_and_return_dropdown_value(self.std_case_info_national_condition)
        self.driver.find_element(By.ID, self.std_case_info_notification_comments).send_keys("General_comments",
                                                                                            NBSUtilities.generate_random_string(
                                                                                                100))
        if Flag == True:
            self.driver.find_element(By.NAME, self.std_case_info_neuro_manisfestations).send_keys(
                self.std_case_info_neuro_manisfestations_value)
            self.data["STD102"]=self.std_case_info_neuro_manisfestations_value
            self.driver.find_element(By.NAME, self.std_case_info_neuro_manisfestations).click()
            self.set_and_return_dropdown_value(self.std_case_info_ocular_manifestations)
            self.set_and_return_dropdown_value(self.std_case_info_otic_manisfestations)
            self.set_and_return_dropdown_value(self.std_case_info_late_clinical_manisfestations)
        else:
            self.set_and_return_dropdown_value(self.std_case_info_pid)
            self.set_and_return_dropdown_value(self.std_case_info_disseminated)
            self.set_and_return_dropdown_value(self.std_case_info_conjunctivits)
            pass
        self.driver.find_element(By.ID, self.std_case_info_general_comments).send_keys("General_comments",
                                                                                       NBSUtilities.generate_random_string(
                                                                                           100))

    def set_disease_acquired(self):
        hospitalization_option_value = self.set_and_return_dropdown_value(self.std_case_info_disease_acquired_place)
        self.data[self.std_case_info_disease_acquired_place]=str(hospitalization_option_value)
        self.driver.find_element(By.ID, "INV152L").click()

        disease_acquired = [self.std_case_info_out_of_country, self.std_case_info_out_of_state,
                            self.std_case_info_out_of_jurisdiction]

        if hospitalization_option_value in disease_acquired:
            country_option_value = self.set_and_return_dropdown_value(
                self.std_case_info_country)
            self.data[self.std_case_info_country] = country_option_value
            state_option_value = self.set_and_return_dropdown_value(
                self.std_case_info_state)
            self.data[self.std_case_info_state] = state_option_value

            city = "city" + "-" + NBSUtilities.generate_random_string(5)
            self.driver.find_element(By.ID, self.std_case_info_city).send_keys(city)
            self.data[self.std_case_info_city]=city
            country_option_value = self.set_and_return_dropdown_value(
                self.std_case_info_country)
            self.data[self.std_case_info_country] = country_option_value


    def set_exposure_list(self):
        element = self.driver.find_element(By.ID, self.std_case_info_country_of_exposure)
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        if element.tag_name.lower() == 'select':
            select_element = Select(self.driver.find_element(By.ID, self.std_case_info_country_of_exposure))
            random_index = random.randint(2, len(select_element.options))
            select_initiating_agency_xpath = (
                    f"//*[@id='{self.std_case_info_country_of_exposure}']/option[".__add__(
                        str(random_index)) + "]")
            country_of_exposure_value = (
                self.driver.find_element(By.XPATH, select_initiating_agency_xpath))
            # open the drop down window
            self.driver.execute_script("arguments[0].scrollIntoView()", country_of_exposure_value)
            # print(f"initiating_agencyvalue = {initiating_agency_value.get_attribute('value')}")
            # send the drop down value to the text box linked to the select
            self.driver.find_element(By.NAME, f"{self.std_case_info_country_of_exposure}_textbox").send_keys(
                country_of_exposure_value.text)
            # Close the dropdown window
            self.data[self.std_case_info_country_of_exposure + "_" + str(1)] = country_of_exposure_value.text
            self.driver.find_element(By.XPATH, select_initiating_agency_xpath).click()
            self.driver.find_element(By.ID, self.std_case_info_city_of_exposure).send_keys(
                "city" + "-" + NBSUtilities.generate_random_string(5))
            if country_of_exposure_value.text == self.std_case_info_country_of_exposure_code:
                self.set_and_return_dropdown_value(self.std_case_info_state_of_exposure)
                self.driver.find_element(By.ID, self.std_case_info_city).send_keys(
                    "city" + "-" + NBSUtilities.generate_random_string(5))
                self.set_and_return_dropdown_value(self.std_case_info_county_of_exposure)
        self.driver.find_element(By.XPATH, "// *[ @ id = 'UpdateButtonToggleNBS11002'] / td / input").click()

    def set_exposure_location(self, no_of_repeats):
        for i in range(no_of_repeats):
            element = self.driver.find_element(By.ID, self.std_case_info_country_of_exposure)
            self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
            if element.tag_name.lower() == 'select':
                select_element = Select(self.driver.find_element(By.ID, self.std_case_info_country_of_exposure))
                random_index = random.randint(2, len(select_element.options))
                select_initiating_agency_xpath = (
                        f"//*[@id='{self.std_case_info_country_of_exposure}']/option[".__add__(
                            str(random_index)) + "]")
                country_of_exposure_value = (
                    self.driver.find_element(By.XPATH, select_initiating_agency_xpath))
                # open the drop down window
                self.driver.execute_script("arguments[0].scrollIntoView()", country_of_exposure_value)
                # print(f"initiating_agencyvalue = {initiating_agency_value.get_attribute('value')}")
                # send the drop down value to the text box linked to the select
                self.driver.find_element(By.NAME, f"{self.std_case_info_country_of_exposure}_textbox").send_keys(
                    country_of_exposure_value.text)
                # Close the dropdown window
                self.data[self.std_case_info_country_of_exposure + "_" + str(i)] = country_of_exposure_value.text
                self.driver.find_element(By.XPATH, select_initiating_agency_xpath).click()

                self.driver.find_element(By.ID, self.std_case_info_city_of_exposure).send_keys(
                    "city" + "-" + NBSUtilities.generate_random_string(5))
                if country_of_exposure_value.text == self.std_case_info_country_of_exposure_code:
                    self.set_and_return_dropdown_value(self.std_case_info_state_of_exposure)
                    self.driver.find_element(By.ID, self.std_case_info_city).send_keys(
                        "city" + "-" + NBSUtilities.generate_random_string(5))
                    self.set_and_return_dropdown_value(self.std_case_info_county_of_exposure)
            self.addIdRowToExposureLocation()
        self.setExposureLocationActions()

    def addIdRowToExposureLocation(self):
        self.driver.find_element(By.XPATH, "// *[ @ id = 'AddButtonToggleNBS11002'] / td / input").click()

    def setExposureLocationActions(self):

        identi_edit_row_xpath = "//*[@id='NBS11002']/tbody/tr[1]/td/table/tbody/tr[1]/td/table/tbody/tr[1]/td[3]"
        self.driver.find_element(By.XPATH, identi_edit_row_xpath).click()
        time.sleep(2)
        # selected_type_cd_value = self.setIdType(random_number)
        self.set_exposure_list()
        time.sleep(1)

        identi_delete_row_xpath = "//*[@id='NBS11002']/tbody/tr[1]/td/table/tbody/tr[1]/td/table/tbody/tr[2]/td[4]"
        self.driver.find_element(By.XPATH, identi_delete_row_xpath).click()
        self.driver.switch_to.alert.accept()
        time.sleep(1)

    def set_initiating_agency(self):
        # print("INITIATING_AGENCY")
        element = self.driver.find_element(By.ID, self.std_case_info_initiating_agency)
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        if element.tag_name.lower() == 'select':
            select_element = Select(self.driver.find_element(By.ID, self.std_case_info_initiating_agency))
            random_index = random.randint(2, len(select_element.options))
            select_initiating_agency_xpath = (
                    f"//*[@id='{self.std_case_info_initiating_agency}']/option[".__add__(
                        str(random_index)) + "]")
            initiating_agency_value = (self.driver.find_element(By.XPATH, select_initiating_agency_xpath))
            # open the drop down window

            self.driver.execute_script("arguments[0].scrollIntoView()", initiating_agency_value)
            # print(f"initiating_agencyvalue = {initiating_agency_value.get_attribute('value')}")
            # send the drop down value to the text box linked to the select
            time.sleep(1)
            # print("city", initiating_agency_value.text)
            if initiating_agency_value.text == " ":
                self.driver.find_element(By.NAME, f"{self.std_case_info_initiating_agency}_textbox").send_keys(
                    "Alabama")
                self.data[self.std_case_info_initiating_agency] = "01"
            else:
                self.driver.find_element(By.NAME, f"{self.std_case_info_initiating_agency}_textbox").send_keys(
                    initiating_agency_value.text)
                self.data[self.std_case_info_initiating_agency] = initiating_agency_value.text
            # Close the dropdown window
            self.driver.find_element(By.XPATH, select_initiating_agency_xpath).click()

            print("Exiting INITIATING_AGENCY")

    def set_binational_reporting_criteria(self):
        # self.driver.find_element(By.ID, "rolesList").clear()
        element = self.driver.find_element(By.ID, self.std_case_info_binational_criteria)
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        select_element = Select(self.driver.find_element(By.ID, self.std_case_info_binational_criteria))
        size = len(select_element.options) - 1
        select_element.select_by_index(random.randint(1, size))
        selected_options = select_element.all_selected_options
        selected_values = [option.text for option in selected_options]
        # Get the text of the selected option
        self.data[self.std_case_info_binational_criteria] = selected_values[0]

    def set_std_case_info_confirmation_method(self):
        element = self.driver.find_element(By.ID, self.std_case_info_confirmation_method)
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        select_element = Select(self.driver.find_element(By.ID, self.std_case_info_confirmation_method))
        size = len(select_element.options) - 1
        select_element.select_by_index(random.randint(1, size))

    def set_std_case_mgmt_noti_plan(self):
        element = self.driver.find_element(By.ID, self.std_case_mgmt_noti_plan)
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        select_element = Select(self.driver.find_element(By.ID, self.std_case_mgmt_noti_plan))
        size = len(select_element.options) - 1
        select_element.select_by_index(random.randint(1, size))

    def set_neurologic_signs(self):
        element = self.driver.find_element(By.ID, self.std_case_info_neuro_signs)
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        select_element = Select(self.driver.find_element(By.ID, self.std_case_info_neuro_signs))
        size = len(select_element.options) - 1
        selected_index = random.randint(1, size)
        select_element.select_by_index(selected_index)
        selected_option = select_element.options[selected_index]
        return selected_option.text

    #  """
    #  STD Case management tab details
    #  """
    def set_case_management(self, Flag):
        time.sleep(1)
        self.driver.find_element(By.ID, self.std_case_mgmt_legacy_caseID).send_keys(
            "LegacyID" + "-" + NBSUtilities.generate_random_string(5))
        self.set_and_return_dropdown_value(self.std_case_mgmt_internet_followup)
        self.driver.find_element(By.ID, self.std_case_mgmt_clinic_code).send_keys(
            "clicnkcd" + "-" + NBSUtilities.generate_random_string(4))
        for i in range(self.no_of_repeats):
            self.set_case_management_notes(self.std_case_mgmt_surveillance_notes,
                                           self.std_case_mgmt_surveillance_notes_add_button)
            time.sleep(1)

        identi_edit_row_xpath = "//*[@id='NBS_INV_STD_UI_18']/tbody/tr[1]/td/table/tbody/tr[1]/td/table/tbody/tr[1]/td[3]"
        self.driver.find_element(By.XPATH, identi_edit_row_xpath).click()
        time.sleep(2)
        self.driver.find_element(By.ID, self.std_case_mgmt_surveillance_notes).clear()
        self.set_case_management_notes(self.std_case_mgmt_surveillance_notes,
                                       self.std_case_mgmt_surveillance_notes_update_button)
        time.sleep(1)

        identi_delete_row_xpath = "//*[@id='NBS_INV_STD_UI_18']/tbody/tr[1]/td/table/tbody/tr[1]/td/table/tbody/tr[2]/td[4]"
        self.driver.find_element(By.XPATH, identi_delete_row_xpath).click()
        self.driver.switch_to.alert.accept()
        time.sleep(1)

        self.driver.find_element(By.NAME, self.std_case_mgmt_noti_of_exposure).send_keys(
            self.std_case_mgmt_noti_of_exposure_value)

        self.driver.find_element(By.ID, self.std_case_mgmt_noti_of_exposure_field_label).click()
        self.set_std_case_mgmt_noti_plan()
        # self.driver.find_element(By.NAME, self.std_case_mgmt_noti_plan_text_click).click()
        self.set_and_return_dropdown_value(self.std_case_mgmt_actual_referral_paln)
        self.driver.find_element(By.NAME, self.std_case_mgmt_follow_up_inv).send_keys(
            self.std_case_mgmt_follow_up_inv_quickcd_value)
        self.data[self.std_case_mgmt_follow_up_inv] = self.std_case_mgmt_follow_up_inv_quickcd_value

        self.driver.find_element(By.ID, self.std_case_mgmt_follow_up_inv_quickcd_lookup_button).click()

        self.setTodayDate(By.ID, self.std_case_mgmt_follow_up_inv_date_assigned)
        self.data[self.std_case_mgmt_follow_up_inv_date_assigned] = str(self.get_todaysdate())
        self.set_and_return_dropdown_value(self.std_case_mgmt_exam_reason)
        if Flag == True:
            self.driver.find_element(By.NAME, self.std_case_mgmt_provider_diagnosis).send_keys(
                self.std_case_mgmt_provider_diagnosis_value)
        else:
            self.driver.find_element(By.NAME, self.std_case_mgmt_provider_diagnosis).send_keys(
                self.std_case_mgmt_provider_diagnosis_ghono_value)

        self.set_and_return_dropdown_value(self.std_case_mgmt_expected_in)
        self.selectCalendarDate(NBSConstants.TYPE_CODE_ID, self.std_case_mgmt_exam_date)
        self.driver.find_element(By.NAME, self.std_case_mgmt_exam_follow_up_provider).send_keys(
            self.std_case_mgmt_exam_follow_up_provider_value)
        self.driver.find_element(By.ID, self.std_case_mgmt_exam_follow_up_provider_code_lookup_button).click()
        self.click_search_organization(By.ID, self.std_case_mgmt_exam_follow_up_facility)
        self.switch_to_current_window()
        self.set_organization()
        value = self.driver.find_element(By.ID, "NBS172").text
        self.data["NBS172"] = value
        self.driver.find_element(By.NAME, self.std_case_mgmt_case_disposition).send_keys(
            self.std_case_mgmt_internet_outcome_value)
        time.sleep(1)
        self.driver.find_element(By.ID, "NBS173L").click()
        time.sleep(1)
        # self.selectCalendarDate(NBSConstants.TYPE_CODE_ID, self.std_case_mgmt_case_disposition_date)
        # self.set_tomorrow_date(By.ID,self.std_case_mgmt_case_disposition_date)
        self.setTodayDate(By.ID, self.std_case_mgmt_case_disposition_date)
        self.driver.find_element(By.NAME, self.std_case_mgmt_case_disposition_by).send_keys(
            self.std_case_mgmt_case_disposition_by_value)
        self.driver.find_element(By.ID, self.std_case_mgmt_case_disposition_by_quickcd_lookup_button).click()
        self.driver.find_element(By.NAME, self.std_case_mgmt_case_disposition_supervisor).send_keys(
            self.std_case_mgmt_case_disposition_supervisor_value)
        self.driver.find_element(By.ID, self.std_case_mgmt_case_disposition_superv_quickcd_lookup_button).click()
        self.set_and_return_dropdown_value(self.std_case_mgmt_internet_outcome)

        for i in range(0, self.no_of_repeats):
            self.set_case_management_notes(self.std_case_mgmt_follow_up_notes,
                                           self.std_case_mgmt_follow_up_notes_add_button)
            time.sleep(1)

        identi_edit_row_xpath = "//*[@id='NBS_INV_STD_UI_26']/tbody/tr[1]/td/table/tbody/tr[1]/td/table/tbody/tr[1]/td[3]"
        self.driver.find_element(By.XPATH, identi_edit_row_xpath).click()
        time.sleep(2)
        self.driver.find_element(By.ID, self.std_case_mgmt_follow_up_notes).clear()
        self.set_case_management_notes(self.std_case_mgmt_follow_up_notes,
                                       self.std_case_mgmt_follow_up_notes_update_button)
        time.sleep(1)

        identi_delete_row_xpath = "//*[@id='NBS_INV_STD_UI_26']/tbody/tr[1]/td/table/tbody/tr[1]/td/table/tbody/tr[2]/td[4]"
        self.driver.find_element(By.XPATH, identi_delete_row_xpath).click()
        self.driver.switch_to.alert.accept()
        time.sleep(1)

        for i in range(0, self.no_of_repeats):
            self.set_case_management_notes(self.std_case_mgmt_supervisory_review_notes,
                                           self.std_case_mgmt_supervisory_review_notes_add_button)
            time.sleep(1)

        identi_edit_row_xpath = "//*[@id='NBS_INV_STD_UI_27']/tbody/tr[1]/td/table/tbody/tr[1]/td/table/tbody/tr[1]/td[3]"
        self.driver.find_element(By.XPATH, identi_edit_row_xpath).click()
        time.sleep(2)
        self.driver.find_element(By.ID, self.std_case_mgmt_supervisory_review_notes).clear()
        self.set_case_management_notes(self.std_case_mgmt_supervisory_review_notes,
                                       self.std_case_mgmt_supervisory_review_notes_update_button)
        time.sleep(1)

        identi_delete_row_xpath = "//*[@id='NBS_INV_STD_UI_27']/tbody/tr[1]/td/table/tbody/tr[1]/td/table/tbody/tr[2]/td[4]"
        self.driver.find_element(By.XPATH, identi_delete_row_xpath).click()
        self.driver.switch_to.alert.accept()
        time.sleep(1)
        # self.driver.find_element(By.ID, "NBS192").send_keys(self.std_case_mgmt_patient_interview_status_value)
        element = self.driver.find_element(By.ID, self.std_case_mgmt_patient_interview_status)
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        select_element = Select(self.driver.find_element(By.ID, self.std_case_mgmt_patient_interview_status))
        random_index = random.randint(2, len(select_element.options))
        select_jurisdiction_xpath = (
                f"//*[@id='{self.std_case_mgmt_patient_interview_status}']/option[".__add__(
                    str(random_index)) + "]")
        # jurisdiction_value = (self.driver.find_element(By.XPATH, select_jurisdiction_xpath).get_attribute("value"))
        time.sleep(1)
        select_element.select_by_value(self.std_case_mgmt_patient_interview_status_value)
        self.driver.find_element(By.NAME, self.std_case_mgmt_interviewer_selected).send_keys(
            self.std_case_mgmt_interviewer_selected_value)
        self.driver.find_element(By.ID, self.std_case_mgmt_interviewer_code_lookup).click()

        self.selectCalendarDate(NBSConstants.TYPE_CODE_ID, self.std_case_mgmt_interviewer_date_assigned)
        self.driver.find_element(By.NAME, self.std_case_mgmt_supervisor).send_keys(self.std_case_mgmt_supervisor_value)
        self.driver.find_element(By.ID, self.std_case_mgmt_supervisor_quickcd_lookup_button).click()
        time.sleep(1)

        for i in range(0, self.no_of_repeats):
            self.set_case_management_notes(self.std_case_mgmt_patient_interview_notes,
                                           self.std_case_mgmt_patient_interview_notes_add_button)
            time.sleep(1)

        identi_edit_row_xpath = "//*[@id='NBS_INV_STD_UI_30']/tbody/tr[1]/td/table/tbody/tr[1]/td/table/tbody/tr[1]/td[3]"
        self.driver.find_element(By.XPATH, identi_edit_row_xpath).click()
        time.sleep(2)
        self.driver.find_element(By.ID, self.std_case_mgmt_patient_interview_notes).clear()
        self.set_case_management_notes(self.std_case_mgmt_patient_interview_notes,
                                       self.std_case_mgmt_patient_interview_notes_update_button)
        time.sleep(1)

        identi_delete_row_xpath = "//*[@id='NBS_INV_STD_UI_30']/tbody/tr[1]/td/table/tbody/tr[1]/td/table/tbody/tr[2]/td[4]"
        self.driver.find_element(By.XPATH, identi_delete_row_xpath).click()
        self.driver.switch_to.alert.accept()
        time.sleep(1)

        for i in range(0, self.no_of_repeats):
            self.set_case_management_notes(self.std_case_mgmt_supervisory_review_comments,
                                           self.std_case_mgmt_supervisory_review_comments_add_button)
            time.sleep(1)

        identi_edit_row_xpath = "//*[@id='NBS_INV_STD_UI_34']/tbody/tr[1]/td/table/tbody/tr[1]/td/table/tbody/tr[1]/td[3]"
        self.driver.find_element(By.XPATH, identi_edit_row_xpath).click()
        time.sleep(2)
        self.driver.find_element(By.ID, self.std_case_mgmt_supervisory_review_comments).clear()
        self.set_case_management_notes(self.std_case_mgmt_supervisory_review_comments,
                                       self.std_case_mgmt_supervisory_review_comments_update_button)
        time.sleep(1)

        identi_delete_row_xpath = "//*[@id='NBS_INV_STD_UI_34']/tbody/tr[1]/td/table/tbody/tr[1]/td/table/tbody/tr[2]/td[4]"
        self.driver.find_element(By.XPATH, identi_delete_row_xpath).click()
        self.driver.switch_to.alert.accept()
        time.sleep(1)

    def set_case_management_notes(self, id, xpath):
        self.driver.find_element(By.ID, id).send_keys(
            "Notes" + "-" + NBSUtilities.generate_random_string(100))
        self.driver.find_element(By.XPATH, xpath).click()

    def set_core_info(self,Flag):
        if Flag == True:
            self.set_900_case_status()
            self.set_risk_factors_last_12_months(Flag)
            self.set_hangout_info()
            self.set_partner_info()
            self.set_target_population()
            self.set_std_testing()
            self.set_sign_symptoms()
            self.set_std_history()
            self.set_900_partner_services_info(Flag)
        else:
            self.set_std_testing()
            self.set_ast_testing_data()

    def set_900_case_status(self):
        value = self.set_and_return_dropdown_value(self.std_core_info_900_status)
        self.data[self.std_core_info_900_status] = value
        value = self.set_and_return_dropdown_value(self.std_core_info_eHARS_record_search)
        self.data[self.std_core_info_eHARS_record_search] = value
        self.set_and_return_dropdown_value(self.std_core_info_transmission_category_eHARS)
        value = self.set_and_return_dropdown_value(self.std_core_info_case_sampled)
        self.data[self.std_core_info_case_sampled] = value

    def set_risk_factors_last_12_months(self, Flag):

        if Flag == True:
            self.driver.find_element(By.NAME, self.std_core_info_was_behavioral_risk_assessed_name).send_keys(
                self.std_core_info_was_behavioral_risk_assessed_value)
        else:
            self.set_dropdown_by_visibletext(self.std_core_info_was_behavioral_risk_assessed,
                                                            self.std_core_info_was_behavioral_risk_assessed_value)

        self.driver.find_element(By.ID, "STD107L").click()
        self.set_and_return_dropdown_value(self.std_core_info_had_sex_with_male)
        self.set_and_return_dropdown_value(self.std_core_info_had_sex_with_female)
        self.set_and_return_dropdown_value(self.std_core_info_had_sex_with_transgen)
        self.set_and_return_dropdown_value(self.std_core_info_had_sex_with_anonymous)
        self.set_and_return_dropdown_value(self.std_core_info_had_sex_with_condom)
        self.set_and_return_dropdown_value(self.std_core_info_had_sex_while_intoxicated)
        self.set_and_return_dropdown_value(self.std_core_info_exchanged_drugs_for_sex)
        self.set_and_return_dropdown_value(self.std_core_info_had_sex_with_known_IDU)
        self.set_and_return_dropdown_value(self.std_core_info_been_incarcerated)
        self.driver.find_element(By.NAME, self.std_core_info_injection_drug_use).send_keys(self.ynu_random_option)
        self.driver.find_element(By.ID, "NBS232L").click()

        self.driver.find_element(By.NAME, self.std_core_info_shared_injection).send_keys(self.ynu_random_option)
        time.sleep(1)
        self.driver.find_element(By.NAME, self.std_core_info_no_drug_use_reported).send_keys(
            self.std_core_info_no_drug_use_reported_value)
        self.driver.find_element(By.ID, "NBS237L").click()
        self.set_and_return_dropdown_value(self.std_core_info_cocaine)
        self.set_and_return_dropdown_value(self.std_core_info_crack)
        self.set_and_return_dropdown_value(self.std_core_info_heroin)
        self.set_and_return_dropdown_value(self.std_core_info_methamphetamine)
        self.set_and_return_dropdown_value(self.std_core_info_nitrates)
        self.set_and_return_dropdown_value(self.std_core_info_erectile_dysfunction_med)
        self.driver.find_element(By.NAME, self.std_core_info_other_drug_used).send_keys(
            self.std_core_info_other_drug_used_value)
        self.driver.find_element(By.ID, "STD300L").click()
        self.driver.find_element(By.ID, self.std_core_info_specify_other_drug_used).send_keys(
            "Other_Drug" + "-" + NBSUtilities.generate_random_string(10))

    def set_hangout_info(self):
        self.driver.find_element(By.NAME, self.std_core_info_places_to_meet_partners).send_keys(
            self.std_core_info_places_to_meet_partners_value)
        self.data[self.std_core_info_places_to_meet_partners] = self.std_core_info_places_to_meet_partners_value

        sendkeys = self.set_and_return_dropdown_value(self.std_core_info_places_to_have_sex)
        # print("reurn the value", sendkeys)
        self.driver.find_element(By.NAME, self.std_core_info_places_to_have_sex + '_textbox').send_keys(sendkeys)
        self.data[self.std_core_info_places_to_have_sex] = sendkeys

    def set_partner_info(self):
        sendkeys = self.set_and_return_dropdown_value(self.std_core_info_female_partners)
        self.driver.find_element(By.NAME, self.std_core_info_female_partners + '_textbox').send_keys(sendkeys)
        time.sleep(1)
        self.data[self.std_core_info_female_partners] = sendkeys

        if sendkeys == "Y":
            time.sleep(1)
            random_int = random.randint(20, 30)
            self.driver.find_element(By.ID, "NBS224").send_keys(
                random_int)
            self.data["NBS224"] = random_int

        sendkeys = self.set_and_return_dropdown_value(self.std_core_info_male_partners)
        self.driver.find_element(By.NAME, self.std_core_info_male_partners + '_textbox').send_keys(sendkeys)
        if sendkeys == "Y":
            time.sleep(1)
            random_int = random.randint(20, 30)
            self.driver.find_element(By.ID, "NBS226").send_keys(random_int)
            self.data["NBS226"] = random_int
        sendkeys = self.set_and_return_dropdown_value(self.std_core_info_trans_gen)
        self.driver.find_element(By.NAME, self.std_core_info_trans_gen + '_textbox').send_keys(sendkeys)
        self.data[self.std_core_info_trans_gen] = sendkeys
        if sendkeys == "Y":
            time.sleep(1)
            random_int = random.randint(20,30)
            self.driver.find_element(By.ID, "NBS228").send_keys(random_int)
            self.data["NBS228"] = random_int

        sendkeys = self.set_and_return_dropdown_value(self.std_core_info_female_partners_interview_period)
        self.driver.find_element(By.NAME, self.std_core_info_female_partners_interview_period + '_textbox').send_keys(
            sendkeys)
        self.data[self.std_core_info_female_partners_interview_period] = sendkeys

        if sendkeys == "Y":
            time.sleep(1)
            random_int = random.randint(10, 20)
            self.driver.find_element(By.ID, "NBS130").send_keys(random_int)
            time.sleep(1)
            self.data["NBS130"] = random_int

        sendkeys = self.set_and_return_dropdown_value(self.std_core_info_male_partners_interview_period)
        self.driver.find_element(By.NAME, self.std_core_info_male_partners_interview_period + '_textbox').send_keys(
            sendkeys)
        self.data[self.std_core_info_male_partners_interview_period] = sendkeys

        if sendkeys == "Y":
            time.sleep(1)
            random_int = random.randint(10, 20)
            self.driver.find_element(By.ID, "NBS132").send_keys(random_int)
            self.data["NBS132"] = random_int

        sendkeys = self.set_and_return_dropdown_value(self.std_core_info_transgen_partners_interview_period)
        self.driver.find_element(By.NAME, self.std_core_info_transgen_partners_interview_period + '_textbox').send_keys(
            sendkeys)
        self.data[self.std_core_info_transgen_partners_interview_period] = sendkeys

        if sendkeys == "Y":
            time.sleep(1)
            random_int = random.randint(10, 20)
            self.driver.find_element(By.ID, "NBS134").send_keys(random_int)
            self.data["NBS134"] = random_int

        value = self.set_and_return_dropdown_value(self.std_core_info_met_through_internet)
        self.data[self.std_core_info_met_through_internet] = value

    def set_target_population(self):
        # self.driver.find_element(By.ID, "rolesList").clear()
        element = self.driver.find_element(By.ID, self.std_core_info_target_population)
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        select_element = Select(self.driver.find_element(By.ID, self.std_core_info_target_population))
        size = len(select_element.options) - 1
        select_element.select_by_index(random.randint(1, size))

    def set_edit_std_testing(self):
        identi_edit_row_xpath = "//*[@id='NBS_INV_STD_UI_71']/tbody/tr[1]/td/table/tbody/tr[1]/td/table/tbody/tr[1]/td[3]"
        self.driver.find_element(By.XPATH, identi_edit_row_xpath).click()
        time.sleep(2)
        self.driver.find_element(By.NAME, self.std_core_info_test_type).send_keys(
            self.std_core_info_test_type_value)
        # self.set_and_return_dropdown_value(self.std_core_info_test_type)
        self.set_and_return_dropdown_value(self.std_core_info_test_result)
        self.set_and_return_dropdown_value(self.std_core_info_test_coded_quantitative)
        # self.set_and_return_dropdown_value(self.std_core_info_test_result_quantitative)
        # self.set_and_return_dropdown_value(self.std_core_info_test_results_units)
        self.selectCalendarDate(NBSConstants.TYPE_CODE_ID, self.std_core_info_lab_result_date)
        sendkeys = self.set_and_return_dropdown_value(self.std_core_info_specimen_source)
        select_element = Select(self.driver.find_element(By.ID, self.std_core_info_specimen_source))
        select_element.select_by_value(sendkeys)
        # self.set_and_return_dropdown_value(self.std_core_info_other_specimen_source)
        self.selectCalendarDate(NBSConstants.TYPE_CODE_ID, self.std_core_info_specimen_coll_date)
        self.driver.find_element(By.XPATH, self.std_core_info_update_button).click()
        time.sleep(1)

    def set_std_testing(self):
        self.driver.find_element(By.NAME, self.std_core_info_is_test_performed).send_keys(self.ynu_random_option)
        self.driver.find_element(By.ID, "NBS275L").click()

        for i in range(self.no_of_repeats):
            self.driver.find_element(By.NAME, self.std_core_info_test_type).send_keys(
                self.std_core_info_test_type_value)
            # self.set_and_return_dropdown_value(self.std_core_info_test_type)
            self.set_and_return_dropdown_value(self.std_core_info_test_result)
            self.set_and_return_dropdown_value(self.std_core_info_test_coded_quantitative)
            # self.set_and_return_dropdown_value(self.std_core_info_test_result_quantitative)
            # self.set_and_return_dropdown_value(self.std_core_info_test_results_units)
            self.selectCalendarDate(NBSConstants.TYPE_CODE_ID, self.std_core_info_lab_result_date)
            sendkeys = self.set_and_return_dropdown_value(self.std_core_info_specimen_source)
            select_element = Select(self.driver.find_element(By.ID, self.std_core_info_specimen_source))
            select_element.select_by_value(sendkeys)
            # self.set_and_return_dropdown_value(self.std_core_info_other_specimen_source)
            self.selectCalendarDate(NBSConstants.TYPE_CODE_ID, self.std_core_info_specimen_coll_date)
            self.driver.find_element(By.XPATH, self.std_core_info_add_button).click()
            # self.set_std_testing_Actions(self.no_of_repeats)
            time.sleep(2)

        identi_edit_row_xpath = "//*[@id='NBS_INV_STD_UI_71']/tbody/tr[1]/td/table/tbody/tr[1]/td/table/tbody/tr[1]/td[3]"
        self.driver.find_element(By.XPATH, identi_edit_row_xpath).click()
        time.sleep(2)
        self.driver.find_element(By.NAME, self.std_core_info_test_type).send_keys(
            self.std_core_info_test_type_value)
        # self.set_and_return_dropdown_value(self.std_core_info_test_type)
        self.set_and_return_dropdown_value(self.std_core_info_test_result)
        self.set_and_return_dropdown_value(self.std_core_info_test_coded_quantitative)
        # self.set_and_return_dropdown_value(self.std_core_info_test_result_quantitative)
        # self.set_and_return_dropdown_value(self.std_core_info_test_results_units)
        self.selectCalendarDate(NBSConstants.TYPE_CODE_ID, self.std_core_info_lab_result_date)
        sendkeys = self.set_and_return_dropdown_value(self.std_core_info_specimen_source)
        select_element = Select(self.driver.find_element(By.ID, self.std_core_info_specimen_source))
        select_element.select_by_value(sendkeys)
        # self.set_and_return_dropdown_value(self.std_core_info_other_specimen_source)
        self.selectCalendarDate(NBSConstants.TYPE_CODE_ID, self.std_core_info_specimen_coll_date)
        self.driver.find_element(By.XPATH, self.std_core_info_update_button).click()
        time.sleep(1)

        identi_delete_row_xpath = "//*[@id='NBS_INV_STD_UI_71']/tbody/tr[1]/td/table/tbody/tr[1]/td/table/tbody/tr[2]/td[4]"
        self.driver.find_element(By.XPATH, identi_delete_row_xpath).click()
        self.driver.switch_to.alert.accept()
        time.sleep(1)


    def set_ast_testing_data(self):
        for i in range(self.no_of_repeats):
            self.set_and_return_dropdown_value(self.std_core_info_ast_microorganism_in_isolate)
            self.driver.find_element(By.ID, self.std_core_info_ast_isolate_identifier).send_keys(NBSUtilities.generate_random_int(2))
            key = self.set_and_return_dropdown_value(self.std_core_info_ast_specimen_type)
            # if above option is "other below data should be given OTH"
            if key == "OTH":
                self.driver.find_element(By.ID, self.std_core_info_ast_other_specimen_type).send_keys(NBSUtilities.generate_random_string(5))
            key = self.set_and_return_dropdown_value(self.std_core_info_ast_specimen_collection_site)
                # if above option is "other below data should be given OTH"
            if key == "OTH":
                self.driver.find_element(By.ID, self.std_core_info_ast_other_specimen_collection_site).send_keys(NBSUtilities.generate_random_string(5))
            self.selectCalendarDate(NBSConstants.TYPE_CODE_ID, self.std_core_info_ast_specimen_collection_site)
            self.set_and_return_dropdown_value(self.std_core_info_ast_type)
            self.set_and_return_dropdown_value(self.std_core_info_ast_method)
            self.set_and_return_dropdown_value(self.std_core_info_ast_interpretation)
            self.set_and_return_dropdown_value(self.std_core_info_ast_result_coded)
            self.driver.find_element(By.ID, self.std_core_info_ast_result_quantitative_value).send_keys(NBSUtilities.generate_random_int(2))
            self.set_and_return_dropdown_value(self.std_core_info_ast_test_results_unit)
            self.driver.find_element(By.XPATH, self.std_core_info_ast_add_button).click()

        time.sleep(1)
        identi_edit_row_xpath = "//*[@id='NBS_INV_STD_UI_72']/tbody/tr[1]/td/table/tbody/tr/td/table/tbody/tr[1]/td[3]/input"
        self.driver.find_element(By.XPATH, identi_edit_row_xpath).click()
        time.sleep(1)
        value = self.set_and_return_dropdown_value(self.std_core_info_ast_microorganism_in_isolate)
        self.data[self.std_core_info_ast_microorganism_in_isolate] = value
        self.driver.find_element(By.ID, self.std_core_info_ast_isolate_identifier).send_keys(
            NBSUtilities.generate_random_int(2))
        key = self.set_and_return_dropdown_value(self.std_core_info_ast_specimen_type)
        # if above option is "other below data should be given OTH"
        if key == "OTH":
            self.driver.find_element(By.ID, self.std_core_info_ast_other_specimen_type).send_keys(
                NBSUtilities.generate_random_string(5))
        key = self.set_and_return_dropdown_value(self.std_core_info_ast_specimen_collection_site)
        # if above option is "other below data should be given OTH"
        if key == "OTH":
            self.driver.find_element(By.ID, self.std_core_info_ast_other_specimen_collection_site).send_keys(
                NBSUtilities.generate_random_string(5))
        self.selectCalendarDate(NBSConstants.TYPE_CODE_ID,self.std_core_info_ast_specimen_collection_date)
        self.set_and_return_dropdown_value(self.std_core_info_ast_type)
        self.set_and_return_dropdown_value(self.std_core_info_ast_method)
        self.set_and_return_dropdown_value(self.std_core_info_ast_interpretation)
        self.set_and_return_dropdown_value(self.std_core_info_ast_result_coded)
        self.driver.find_element(By.ID, self.std_core_info_ast_result_quantitative_value).send_keys(
            NBSUtilities.generate_random_int(2))
        self.set_and_return_dropdown_value(self.std_core_info_ast_test_results_unit)
        time.sleep(1)
        self.driver.find_element(By.XPATH, self.std_core_info_ast_update_button).click()
        time.sleep(1)
        identi_delete_row_xpath = "//*[@id='NBS_INV_STD_UI_72']/tbody/tr[1]/td/table/tbody/tr/td/table/tbody/tr[2]/td[4]/input"
        self.driver.find_element(By.XPATH, identi_delete_row_xpath).click()
        time.sleep(1)
        self.driver.switch_to.alert.accept()
        time.sleep(1)


    def set_edit_sign_symptoms(self):
        identi_edit_row_xpath = "//*[@id='NBS_INV_STD_UI_61']/tbody/tr[1]/td/table/tbody/tr[1]/td/table/tbody/tr[1]/td[3]"
        self.driver.find_element(By.XPATH, identi_edit_row_xpath).click()
        time.sleep(1)
        self.set_and_return_dropdown_value(self.std_core_info_source)
        self.selectCalendarDate(NBSConstants.TYPE_CODE_ID, self.std_core_info_onset_date)
        self.set_and_return_dropdown_value(self.std_core_info_sign_symptom)
        self.set_automic_site()
        self.driver.find_element(By.XPATH, self.std_core_info_signs_symptoms_update_button).click()
        time.sleep(1)

    def set_sign_symptoms(self):
        # print("REPEATS!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!", self.no_of_repeats)
        repeat = self.no_of_repeats
        for i in range(repeat+1):
            self.set_and_return_dropdown_value(self.std_core_info_source)
            self.selectCalendarDate(NBSConstants.TYPE_CODE_ID, self.std_core_info_onset_date)
            self.set_and_return_dropdown_value(self.std_core_info_sign_symptom)
            self.set_automic_site()
            self.driver.find_element(By.XPATH, self.std_core_info_signs_symptoms_add_button).click()

        time.sleep(1)
        identi_edit_row_xpath = "//*[@id='NBS_INV_STD_UI_61']/tbody/tr[1]/td/table/tbody/tr[1]/td/table/tbody/tr[1]/td[3]"
        self.driver.find_element(By.XPATH, identi_edit_row_xpath).click()
        time.sleep(1)
        self.set_and_return_dropdown_value(self.std_core_info_source)
        self.selectCalendarDate(NBSConstants.TYPE_CODE_ID, self.std_core_info_onset_date)
        self.set_and_return_dropdown_value(self.std_core_info_sign_symptom)
        self.set_automic_site()
        time.sleep(1)
        self.driver.find_element(By.XPATH, self.std_core_info_signs_symptoms_update_button).click()
        time.sleep(1)

        identi_delete_row_xpath = "//*[@id='NBS_INV_STD_UI_61']/tbody/tr[1]/td/table/tbody/tr[1]/td/table/tbody/tr[2]/td[4]/input"
        time.sleep(1)
        self.driver.find_element(By.XPATH, identi_delete_row_xpath).click()
        time.sleep(1)
        self.driver.switch_to.alert.accept()
        time.sleep(1)

    def set_automic_site(self):
        # self.driver.find_element(By.ID, "rolesList").clear()
        element = self.driver.find_element(By.ID, self.std_core_info_atomic_site)
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        select_element = Select(self.driver.find_element(By.ID, self.std_core_info_atomic_site))
        size = len(select_element.options) - 1
        select_element.select_by_index(random.randint(1, size))
        if select_element == self.std_core_info_atomic_site_value:
            self.driver.find_element(By.ID, self.std_core_info_another_anatomic_site).send_keys(
                "Other_Drug" + "-" + NBSUtilities.generate_random_string(10))

    def set_edit_std_history(self):
        identi_edit_row_xpath = "//*[@id='NBS_INV_STD_UI_64']/tbody/tr[1]/td/table/tbody/tr[1]/td/table/tbody/tr[1]/td[3]"
        self.driver.find_element(By.XPATH, identi_edit_row_xpath).click()
        time.sleep(1)
        self.driver.find_element(By.NAME, self.std_core_info_previous_STD_history).send_keys(
            self.ynu_random_option)
        self.driver.find_element(By.ID, "STD117L").click()
        self.set_and_return_dropdown_value(self.std_core_info_previous_condition)
        self.selectCalendarDate(NBSConstants.TYPE_CODE_ID, self.std_core_info_diagnosis_date)
        self.selectCalendarDate(NBSConstants.TYPE_CODE_ID, self.std_core_info_treatment_date)
        self.set_and_return_dropdown_value(stdInvestigation.std_core_info_confirmation)
        self.driver.find_element(By.XPATH, self.std_core_info_previous_history_update_button).click()
        time.sleep(1)

    def set_std_history(self):
        repeat = self.no_of_repeats
        self.driver.find_element(By.NAME, self.std_core_info_previous_STD_history).send_keys(self.ynu_random_option)
        self.driver.find_element(By.ID, "STD117L").click()
        for i in range(repeat):
            self.set_and_return_dropdown_value(self.std_core_info_previous_condition)
            self.selectCalendarDate(NBSConstants.TYPE_CODE_ID, self.std_core_info_diagnosis_date)
            self.selectCalendarDate(NBSConstants.TYPE_CODE_ID, self.std_core_info_treatment_date)
            self.set_and_return_dropdown_value(stdInvestigation.std_core_info_confirmation)
            self.driver.find_element(By.XPATH, self.std_core_info_previous_history_button).click()

        identi_edit_row_xpath = "//*[@id='NBS_INV_STD_UI_64']/tbody/tr[1]/td/table/tbody/tr[1]/td/table/tbody/tr[1]/td[3]/input"
        self.driver.find_element(By.XPATH, identi_edit_row_xpath).click()
        time.sleep(1)
        value = self.set_and_return_dropdown_value(self.std_core_info_previous_condition)
        self.data[self.std_core_info_previous_condition] = value
        self.selectCalendarDate(NBSConstants.TYPE_CODE_ID, self.std_core_info_diagnosis_date)
        self.selectCalendarDate(NBSConstants.TYPE_CODE_ID, self.std_core_info_treatment_date)
        self.set_and_return_dropdown_value(stdInvestigation.std_core_info_confirmation)
        time.sleep(1)
        self.driver.find_element(By.XPATH, self.std_core_info_previous_history_update_button).click()
        time.sleep(1)

        identi_delete_row_xpath = "//*[@id='NBS_INV_STD_UI_64']/tbody/tr[1]/td/table/tbody/tr[1]/td/table/tbody/tr[1]/td[4]/input"
        self.driver.find_element(By.XPATH, identi_delete_row_xpath).click()
        self.driver.switch_to.alert.accept()
        time.sleep(1)

    def set_900_partner_services_info(self, Flag):
        self.set_and_return_dropdown_value(self.std_core_info_enrolled_in_partner_services)
        self.driver.find_element(By.NAME, self.std_core_info_previous_900_test).send_keys(self.ynu_random_option)
        self.driver.find_element(By.ID, "NBS254L").click()
        if Flag == True:
            self.driver.find_element(By.NAME, self.std_core_info_self_reported_result_name).send_keys(
                self.std_core_info_self_reported_result_value)
        else:
            self.set_dropdown_by_visibletext(self.std_core_info_self_reported_result_id,
                                                            self.std_core_info_self_reported_result_value)

        self.selectCalendarDate(NBSConstants.TYPE_CODE_ID, self.std_core_info_date_last_900_test)
        self.driver.find_element(By.NAME, self.std_core_info_refer_for_test).send_keys(self.ynu_random_option)
        self.selectCalendarDate(NBSConstants.TYPE_CODE_ID, self.std_core_info_referral_date)
        self.driver.find_element(By.NAME, self.std_core_info_900_test).send_keys(self.ynu_random_option)
        self.selectCalendarDate(NBSConstants.TYPE_CODE_ID, self.std_core_info_900_test_sample_date)
        self.set_and_return_dropdown_value(self.std_core_info_900_result)
        self.set_and_return_dropdown_value(self.std_core_info_result_provided)
        self.driver.find_element(By.NAME, self.std_core_info_post_test_counselling).send_keys(self.ynu_random_option)
        self.driver.find_element(By.NAME, self.std_core_info_patient_tested_for_syphillis).send_keys(
            self.ynu_random_option)
        self.driver.find_element(By.ID, "NBS447L").click()
        self.set_and_return_dropdown_value(self.std_core_info_syphillis_test_result)
        self.driver.find_element(By.NAME, self.std_core_info_refer_for_care).send_keys(self.ynu_random_option)
        self.driver.find_element(By.ID, "NBS266L").click()
        if Flag == True:
            self.driver.find_element(By.NAME, self.std_core_info_keep_appointment_name).send_keys(
                self.std_core_info_keep_appointment_value)
        else:
            self.set_dropdown_by_visibletext(self.std_core_info_keep_appointment_id,
                                                            self.std_core_info_keep_appointment_value)

        self.selectCalendarDate(NBSConstants.TYPE_CODE_ID, self.std_core_info_appointment_date)
        self.driver.find_element(By.NAME, self.std_core_info_is_client_on_prEP).send_keys(self.ynu_random_option)
        self.driver.find_element(By.NAME, self.std_core_info_referred_to_prEP_provider).send_keys(
            self.ynu_random_option)
        self.driver.find_element(By.NAME, self.std_core_info_anti_viral_therapy).send_keys(self.ynu_random_option)
        self.driver.find_element(By.NAME, self.std_core_info_anti_viral_therapy_ever).send_keys(self.ynu_random_option)
        self.driver.find_element(By.ID, "NBS256L").click()

    def set_contract_tracing_info(self):
        self.set_and_return_dropdown_value(self.std_contract_tracing_inv_priority)
        self.selectCalendarDate(NBSConstants.TYPE_CODE_ID, self.std_contract_tracing_infectious_from)
        self.selectCalendarDate(NBSConstants.TYPE_CODE_ID, self.std_contract_tracing_infectious_to)
        self.set_and_return_dropdown_value(self.std_contract_tracing_contact_inv_status)
        self.driver.find_element(By.ID, self.std_contract_tracing_inv_comments).send_keys(
            "Contact_Investigation_Comments",
            NBSUtilities.generate_random_string(
                100))
        
    def set_contract_record(self):
        self.driver.find_element(By.NAME,'submitIXS').click()
        time.sleep(2)
        self.switch_to_current_window()
        self.driver.maximize_window()
        # self.selectCalendarDate(NBSConstants.TYPE_CODE_ID,self.std_contract_record_add_interview)
        self.setTodayDate(By.ID, self.std_contract_record_add_interview)
        
        self.driver.find_element(By.ID, self.std_add_interview_clear_reassign).click()
        self.driver.find_element(By.ID,self.std_add_interview_provider_textarea).send_keys("1")
        self.driver.find_element(By.ID,self.std_add_interview_provider_button).click()
        
        self.set_dropdown_value(self.std_add_interview_type, "INITIAL")
        # self.set_and_return_dropdown_value(self.std_add_interview_type)
        self.set_and_return_dropdown_value(self.std_add_interview_location)
        self.set_and_return_dropdown_value(self.std_add_interview_contacts_named)
       
        self.driver.find_element(By.ID,self.std_add_interview_organization_textarea).send_keys("1")
        self.driver.find_element(By.ID,self.std_add_interview_organization_button).click()
              
        self.set_and_return_dropdown_value(self.std_add_interview_900_site_type)
        self.set_and_return_dropdown_value(self.std_add_interview_interventions)
        self.set_and_return_dropdown_value(self.std_add_interview_case_status)
        
        for i in range(0, self.no_of_repeats+1):
            self.driver.find_element(By.ID,self.std_add_interview_notes_add_textarea).send_keys("notes",
                                                                                           NBSUtilities.generate_random_string(
                                                                                               100))
            self.driver.find_element(By.XPATH,self.std_add_interview_notes_add_button).click()
            time.sleep(1)

        identi_edit_row_xpath = "//*[@id='questionbodyGA25100']/tr[1]/td[3]"
        self.driver.find_element(By.XPATH, identi_edit_row_xpath).click()
        time.sleep(2)
        self.driver.find_element(By.ID,self.std_add_interview_notes_add_textarea).clear()
        self.driver.find_element(By.ID,self.std_add_interview_notes_add_textarea).send_keys("notes",
                                                                                           NBSUtilities.generate_random_string(
                                                                                               100))
        self.driver.find_element(By.XPATH,self.std_update_interview_notes_update_button).click()
       
        time.sleep(1)

        identi_delete_row_xpath = "//*[@id='questionbodyGA25100']/tr[2]/td[4]"
        self.driver.find_element(By.XPATH, identi_delete_row_xpath).click()
        self.driver.switch_to.alert.accept()
        time.sleep(1)
      
        self.driver.find_element(By.NAME, "Submit").click()
        self.close_add_interview()
        self.switch_to_original_window()
 
    def set_tomorrow_date(self, type, type_val):
        today = datetime.now().date()
        tomorrow = today + timedelta(days=1)
        format_tomorrow = tomorrow.strftime("%m/%d/%Y")
        element = self.driver.find_element(type, type_val)
        element.clear()
        element.send_keys(format_tomorrow)

    def set_jurisdiction(self):
        self.driver.find_element(By.ID, "tabs0head1").click()
        # self.set_and_return_dropdown_value(self.std_case_info_jurisdiction)
        # proxy.publicHealthCaseVO_s.thePublicHealthCaseDT.jurisdictionCd_ac_table
        element = self.driver.find_element(By.ID, self.std_case_info_jurisdiction)
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        select_element = Select(self.driver.find_element(By.ID, self.std_case_info_jurisdiction))
        random_index = random.randint(2, len(select_element.options))
        select_jurisdiction_xpath = (
                f"//*[@id='{self.std_case_info_jurisdiction}']/option[".__add__(
                    str(random_index)) + "]")
        jurisdiction_value = (self.driver.find_element(By.XPATH, select_jurisdiction_xpath).get_attribute("value"))
        time.sleep(1)

        select_element.select_by_value(jurisdiction_value)
        self.data[self.std_case_info_jurisdiction] = jurisdiction_value

        # print(self.data)

    def set_jurisdiction_edit(self):
        # proxy.publicHealthCaseVO_s.thePublicHealthCaseDT.jurisdictionCd_ac_table
        element = self.driver.find_element(By.ID, self.std_case_info_jurisdiction)
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        select_element = Select(self.driver.find_element(By.ID, self.std_case_info_jurisdiction))
        random_index = random.randint(2, len(select_element.options))
        select_jurisdiction_xpath = (
                f"//*[@id='{self.std_case_info_jurisdiction}']/option[".__add__(
                    str(random_index)) + "]")
        jurisdiction_value = (self.driver.find_element(By.XPATH, select_jurisdiction_xpath).get_attribute("value"))
        # jurisdiction_value = "999999"
        time.sleep(1)
        # print(jurisdiction_value)
        select_element.select_by_value(jurisdiction_value)  # Out of System = 999999
        time.sleep(1)

        if jurisdiction_value == self.std_case_info_jurisdiction_value:
            element = self.driver.find_element(By.ID, self.std_case_info_system_recipient)
            self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
            select_element = Select(element)
            random_index = random.randint(2, len(select_element.options))
            select_recipient_xpath = (
                    f"//*[@id='{self.std_case_info_system_recipient}']/option[".__add__(
                        str(random_index)) + "]")
            value = self.driver.find_element(By.XPATH, select_recipient_xpath).get_attribute("value")
            select_element.select_by_value(value)
            self.set_and_return_dropdown_value(self.std_case_info_doc_type)
            self.driver.find_element(By.ID, self.std_case_transfer_comments).send_keys("General_comments",
                                                                                           NBSUtilities.generate_random_string(
                                                                                               100))
            self.driver.find_element(By.XPATH, '//*[@id="topButtId"]/input[1]').click()
            self.switch_to_original_window()
        self.driver.find_element(By.XPATH, '//*[@id="topButtId"]/input[1]').click()
        self.switch_to_original_window()

    def setEditExposureLocationActions(self):
        identi_edit_row_xpath = "//*[@id='NBS11002']/tbody/tr[1]/td/table/tbody/tr[1]/td/table/tbody/tr[1]/td[3]"
        self.driver.find_element(By.XPATH, identi_edit_row_xpath).click()
        time.sleep(1)
        # selected_type_cd_value = self.setIdType(random_number)
        self.set_exposure_list()
        time.sleep(1)

    def set_edit_clinical_information(self):
        self.set_and_return_dropdown_value(self.std_case_info_reporting_ishospitalized)
        hospitalization_option_value = self.set_and_return_dropdown_value(
            self.std_case_info_reporting_ishospitalized)
        if hospitalization_option_value == "Y":
            self.click_search_organization(By.ID, self.std_case_info_hospital)
            self.switch_to_current_window()
            self.set_organization()
            now = datetime.now()
            earlier = now - timedelta(days=3)
            adm_date = earlier.strftime("%m-%d-%Y")
            self.set_hsp_adm_date(By.ID, self.std_case_info_admission_date, adm_date)
            # self.set_discharge_date()
        self.set_condition_details()
        self.set_edit_epidemiologic_details()

    def set_edit_epidemiologic_details(self):
        # patient associated with daycare
        element = self.driver.find_element(By.ID, self.std_case_info_daycare_facility)
        # print("Element type",element.tag_name)
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        Select(element).select_by_value("Y")

        element = self.driver.find_element(By.ID, self.std_case_info_is_food_handler)
        # print("Element type",element.tag_name)
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        Select(element).select_by_value("Y")

        # case part of an outbreak
        self.set_and_return_dropdown_value(self.std_case_info_part_of_outbreak)

        self.set_disease_acquired()
        self.set_and_return_dropdown_value(self.std_case_info_source)
        self.set_and_return_dropdown_value(self.std_case_info_residence)
        self.set_and_return_dropdown_value(self.std_case_info_reporting_county)
        # self.setEditExposureLocationActions()
        # self.set_binational_reporting_criteria()
        self.set_and_return_dropdown_value(self.std_case_info_transmission_mode)
        self.set_and_return_dropdown_value(self.std_case_info_detection_mode)
        self.set_std_case_info_confirmation_method()
        self.selectCalendarDate(NBSConstants.TYPE_CODE_ID, self.std_case_info_confirmation_date)
        self.driver.find_element(By.NAME, "INV163_textbox").send_keys(self.std_case_info_case_status_value)
        time.sleep(1)
        self.set_and_return_dropdown_value(self.std_case_info_national_condition)
        self.driver.find_element(By.ID, self.std_case_info_notification_comments).send_keys("General_comments",
                                                                                            NBSUtilities.generate_random_string(
                                                                                                100))

        self.driver.find_element(By.NAME, self.std_case_info_neuro_manisfestations).send_keys(
            self.std_case_info_neuro_manisfestations_value)
        self.driver.find_element(By.NAME, self.std_case_info_neuro_manisfestations).click()
        self.set_and_return_dropdown_value(self.std_case_info_ocular_manifestations)
        self.set_and_return_dropdown_value(self.std_case_info_otic_manisfestations)
        self.set_and_return_dropdown_value(self.std_case_info_late_clinical_manisfestations)
        self.driver.find_element(By.ID, self.std_case_info_general_comments).send_keys("General_comments",
                                                                                       NBSUtilities.generate_random_string(
                                                                                           100))

    def set_edit_reporting_information(self):
        self.setTodayDate(By.ID, self.std_case_info_date_of_report)
        self.setTodayDate(By.ID, self.std_case_info_date_reported_to_county)
        self.setTodayDate(By.ID, self.std_case_info_date_reported_to_state)
        self.set_and_return_dropdown_value(self.std_case_info_reporting_source_type)
        # search organization
        self.driver.find_element(By.ID, "INV183CodeClearButton").click()
        time.sleep(1)
        self.click_search_organization(By.ID, self.std_case_info_reporting_org)
        self.switch_to_current_window()
        self.set_organization()
        self.driver.find_element(By.ID, "INV181CodeClearButton").click()
        self.click_search_provider(By.ID, self.std_case_info_reporting_provider)
        self.switch_to_current_window()
        self.set_provider()
        self.driver.find_element(By.ID, "INV182CodeClearButton").click()
        self.click_search_provider(By.ID, self.std_case_info_reporting_physician)
        self.switch_to_current_window()
        self.set_physician()
        self.driver.find_element(By.ID, "NBS291CodeClearButton").click()
        self.click_search_organization(By.ID, self.std_case_info_reporting_clinic)
        self.switch_to_current_window()
        self.set_organization()

    def set_edit_case_management(self):
        self.driver.find_element(By.ID, self.std_case_mgmt_legacy_caseID).clear()
        self.driver.find_element(By.ID, self.std_case_mgmt_legacy_caseID).send_keys(
            "LegacyID" + "-" + NBSUtilities.generate_random_string(5))
        self.set_and_return_dropdown_value(self.std_case_mgmt_internet_followup)
        self.driver.find_element(By.ID, self.std_case_mgmt_clinic_code).send_keys(
            "clicnkcd" + "-" + NBSUtilities.generate_random_string(4))
        identi_edit_row_xpath = "//*[@id='NBS_INV_STD_UI_18']/tbody/tr[1]/td/table/tbody/tr[1]/td/table/tbody/tr[1]/td[3]"
        self.driver.find_element(By.XPATH, identi_edit_row_xpath).click()
        time.sleep(2)
        self.driver.find_element(By.ID, self.std_case_mgmt_surveillance_notes).clear()
        self.set_case_management_notes(self.std_case_mgmt_surveillance_notes,
                                       self.std_case_mgmt_surveillance_notes_update_button)

        # self.driver.find_element(By.ID, self.std_case_mgmt_noti_of_exposure_field_label).click()
        self.set_std_case_mgmt_noti_plan()
        # self.driver.find_element(By.NAME, self.std_case_mgmt_noti_plan_text_click).click()
        self.set_and_return_dropdown_value(self.std_case_mgmt_actual_referral_paln)
        self.setTodayDate(By.ID, self.std_case_mgmt_follow_up_inv_date_assigned)
        # self.driver.find_element(By.ID, self.std_case_mgmt_follow_up_inv_date_assigned).send_keys(tomorrow)
        self.set_and_return_dropdown_value(self.std_case_mgmt_exam_reason)
        self.set_and_return_dropdown_value(self.std_case_mgmt_expected_in)
        self.selectCalendarDate(NBSConstants.TYPE_CODE_ID, self.std_case_mgmt_exam_date)
        self.driver.find_element(By.ID, "NBS171CodeClearButton").click()
        time.sleep(1)
        self.driver.find_element(By.NAME, self.std_case_mgmt_exam_follow_up_provider).send_keys(
            self.std_case_mgmt_exam_follow_up_provider_value)
        self.driver.find_element(By.ID, self.std_case_mgmt_exam_follow_up_provider_code_lookup_button).click()
        self.driver.find_element(By.ID, "NBS172CodeClearButton").click()
        self.click_search_organization(By.ID, self.std_case_mgmt_exam_follow_up_facility)
        time.sleep(1)

        self.switch_to_current_window()
        self.set_organization()

        self.driver.find_element(By.ID, "NBS173L").click()
        time.sleep(1)
        self.setTodayDate(By.ID, self.std_case_mgmt_case_disposition_date)
        self.set_and_return_dropdown_value(self.std_case_mgmt_internet_outcome)

        identi_edit_row_xpath = "//*[@id='NBS_INV_STD_UI_26']/tbody/tr[1]/td/table/tbody/tr[1]/td/table/tbody/tr[1]/td[3]"
        self.driver.find_element(By.XPATH, identi_edit_row_xpath).click()
        time.sleep(2)
        self.driver.find_element(By.ID, self.std_case_mgmt_follow_up_notes).clear()
        self.set_case_management_notes(self.std_case_mgmt_follow_up_notes,
                                       self.std_case_mgmt_follow_up_notes_update_button)
        time.sleep(1)
        identi_edit_row_xpath = "//*[@id='NBS_INV_STD_UI_27']/tbody/tr[1]/td/table/tbody/tr[1]/td/table/tbody/tr[1]/td[3]"
        self.driver.find_element(By.XPATH, identi_edit_row_xpath).click()
        time.sleep(2)
        self.driver.find_element(By.ID, self.std_case_mgmt_supervisory_review_notes).clear()
        self.set_case_management_notes(self.std_case_mgmt_supervisory_review_notes,
                                       self.std_case_mgmt_supervisory_review_notes_update_button)
        time.sleep(1)

        self.selectCalendarDate(NBSConstants.TYPE_CODE_ID, self.std_case_mgmt_interviewer_date_assigned)

        identi_edit_row_xpath = "//*[@id='NBS_INV_STD_UI_30']/tbody/tr[1]/td/table/tbody/tr[1]/td/table/tbody/tr[1]/td[3]"
        self.driver.find_element(By.XPATH, identi_edit_row_xpath).click()
        time.sleep(2)
        self.driver.find_element(By.ID, self.std_case_mgmt_patient_interview_notes).clear()
        self.set_case_management_notes(self.std_case_mgmt_patient_interview_notes,
                                       self.std_case_mgmt_patient_interview_notes_update_button)
        time.sleep(1)

        identi_edit_row_xpath = "//*[@id='NBS_INV_STD_UI_34']/tbody/tr[1]/td/table/tbody/tr[1]/td/table/tbody/tr[1]/td[3]"
        self.driver.find_element(By.XPATH, identi_edit_row_xpath).click()
        time.sleep(2)
        self.driver.find_element(By.ID, self.std_case_mgmt_supervisory_review_comments).clear()
        self.set_case_management_notes(self.std_case_mgmt_supervisory_review_comments,
                                       self.std_case_mgmt_supervisory_review_comments_update_button)
        time.sleep(1)

    def set_edit_core_info(self):
        Flag = False
        self.set_900_case_status()
        self.set_risk_factors_last_12_months(Flag)
        self.set_hangout_info()
        self.set_partner_info()
        self.set_target_population()
        self.set_edit_std_testing()
        self.set_edit_sign_symptoms()
        self.set_edit_std_history()
        self.set_900_partner_services_info(Flag)

    def std_investigation_headers(self, Flag):
        element =  WebDriverWait(self.driver, 10).until(EC.visibility_of_element_located((By.ID, "tabs0head1")))
        # element = self.driver.find_elements(By.ID, "tabs0head1")
        if element is not None:
            element.click()
        time.sleep(1)
        self.set_case_info(Flag)
        self.driver.find_element(By.ID, "tabs0head2").click()
        self.set_case_management(Flag)
        self.driver.find_element(By.ID, "tabs0head3").click()
        self.set_core_info(Flag)
        self.driver.find_element(By.ID, "tabs0head4").click()
        self.set_contract_tracing_info()
        # print("dictionary",self.data)
        self.driver.find_element(By.XPATH, "//*[@id='SubmitTop']").click()
        self.driver.find_element(By.XPATH, "//*[@id='bd']/div[1]/a").click()
        # if Flag == True:
        #     self.driver.find_element(By.XPATH, self.add_new_btn_xpath).click()

        with open("data.json", "w") as json_file:
            json.dump(self.data, json_file)

    def std_header_home(self, condition, referralbasis, decision, Flag):
        if Flag == True:
            self.driver.find_element(By.ID, "tabs0head1").click()
            time.sleep(1)

        add_new_btn_xpath = '//*[@id="subsect_Inv"]/table/tbody/tr/td['.__add__(
            str(NBSConstants.ADD_NEW_OPTION)) + ']/input[2]'
        self.driver.find_element(By.XPATH, add_new_btn_xpath).click()
        # STD
        time.sleep(1)
        self.driver.find_element(By.NAME, "ccd_textbox").send_keys(condition)

        # self.driver.find_element(By.ID, "nestedElementsControllerController").click()
        self.driver.find_element(By.NAME, stdInvestigation.std_inv_header).click()
        time.sleep(1)
        self.driver.find_element(By.NAME, "referralBasis_textbox").send_keys(
            referralbasis)
        self.driver.find_element(By.ID, "Submit").click()
        self.switch_to_current_window()
        self.driver.find_element(By.NAME, "reviewReason_textbox").send_keys(
            decision)
        time.sleep(1)
        self.driver.find_element(By.XPATH, "//*[@id='topProcessingDecisionId']/input[1]").click()
        self.switch_to_original_window()


    def isElementDisplayed_check(self, elm_path, elm_val):
        try:
            elm = self.driver.find_element(elm_path, elm_val)
            if elm:
                return True
            else:
                return False
        except Exception as e:
            self.logger.info(str(e))
            return False

    def validations(self,field_id,data):
        # print("update_fields!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!", json.dumps((data)))
        current_field = self.driver.find_element(By.ID, field_id)
        try:
            old_text = data.get(field_id, None)
            # print("old_text@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@", old_text)
            if current_field.tag_name == 'select' and self.isElementDisplayed_check(By.ID,
                                                                                    field_id) and old_text is not None:  # old value is none during add new action
                current_text = current_field.get_attribute("value")
                # print('current_textcurrent_text',current_text)
                # check if the element if a dropdown
                drop_down = Select(current_field)
                if drop_down is not None and current_field.tag_name == "select":
                    self.logger.info(f"This element is a select element {field_id}")
                    current_text = drop_down.all_selected_options.__getitem__(1)
                # validate match
                if current_text != old_text:
                    self.logger.error(
                        f"Data loss for field {field_id}  old value  {old_text} current value{current_text}")
                    # print(f"Data loss for field {field_id}  old value  {old_text} current value {current_text}")
                    return False
        except Exception as e:
            print(e)
        return True

    def verifyandvaildate(self,headers):
        # data[self.std_case_info_jurisdiction] = "130002"
        # data = {'INV107': '130002', 'NBS111': 'Alaska', 'NBS119': '4860', 'INV111': '01/05/2024', 'INV120': '01/05/2024', 'INV121': '01/05/2024', 'INV112': 'LD', 'INV128': 'Y', 'INV136': '01/05/2024', 'INV137': '01/05/2024', 'INV138': '01/05/2024', 'INV145': 'N', 'STD105': '01/05/2024', 'STD099': '01/05/2024', 'INV150': 'N', 'INV152': 'OOC', 'INV153': '620', 'INV154': '10', 'INV155': 'city-lsmzh', 'INV502_0': 'CROATIA', 'INV502_1': 'LESOTHO',
        #             'INV515': 'Other situations that may require binational notification or coordination of response', 'INV163': 'P', 'NBS136': '710 - Syphilis, primary', 'STD102': 'P', 'pageClientVO.answer(NBS161)': 'local', 'NBS162': '01/05/2024', 'NBS242_textbox': 'N', 'NBS244': 'Y', 'NBS223': 'U', 'NBS227': 'N', 'NBS129': 'U', 'NBS131': 'U', 'NBS133': 'Y', 'NBS134': 13, 'STD119': 'NASK', 'NBS250': '350'}
        with open("data.json", "r") as json_file:
            data = json.load(json_file)

        print("Data dictionary in the validation",data)
        if headers == "case info":
            self.driver.find_element(By.ID, "tabs0head1").click()
            #validation_jurisdiction
            text = self.driver.find_element(By.ID,self.std_case_info_jurisdiction).text
            # print("Jur",self.jurisdiction_dict[data[self.std_case_info_jurisdiction]])
            assert text == self.jurisdiction_dict[data[self.std_case_info_jurisdiction]]

            # initiating agency validation
            assert self.driver.find_element(By.ID,self.std_case_info_initiating_agency).text == data[self.std_case_info_initiating_agency]
            # print("std_case_info_first_sexual_exposure!!!!!!!!!!!!!!!!!!!!!!!!!!", self.driver.find_element(By.ID,"NBS118").text,"===>", str(self.get_todaysdate_m_d_y()) )
            # assert self.driver.find_element(By.ID,"NBS118").text == str(self.get_todaysdate_m_d_y())
            # assert self.driver.find_element(By.ID,"NBS119").text == data[self.std_case_info_sexual_frequency]

            #exposerlocation
            element = self.driver.find_element(By.XPATH,"//*[@id='NBS11002']/tbody/tr[1]/td/table/tbody/tr[1]/td/table/tbody/tr[1]/td[3]")
            # element = self.driver.find_element(By.XPATH,                                               "//*[@id='NBS11002']/tbody/tr[1]/td/table/tbody/tr[1]/td/table/tbody/tr[1]/td[5]")
            actual = element.text
            # print("exposurelocation1", actual)
            assert data[self.std_case_info_country_of_exposure + "_" + str(0)] == actual
            element = self.driver.find_element(By.XPATH,"//*[@id='NBS11002']/tbody/tr[1]/td/table/tbody/tr[1]/td/table/tbody/tr[2]/td[3]")
            # element = self.driver.find_element(By.XPATH,                                               "//*[@id='NBS11002']/tbody/tr[1]/td/table/tbody/tr[1]/td/table/tbody/tr[2]/td[5]")
            actual = element.text
            # print("exposurelocation2",actual)
            assert data[self.std_case_info_country_of_exposure + "_" + str(1)] == actual
            assert self.driver.find_element(By.ID,"INV163").text == "Probable"
            assert self.driver.find_element(By.ID, self.std_case_info_diagnosis_reported).text == data[self.std_case_info_diagnosis_reported]
            if self.driver.find_element(By.ID, self.std_case_info_diagnosis_reported).text == "710 - Syphilis, primary":
                assert self.driver.find_element(By.ID, "STD102").text == "P - Yes, Probable"
        elif headers == "case management":
            self.driver.find_element(By.ID, "tabs0head2").click()
            if "Gonorrhea"  in self.driver.find_element(By.XPATH,'//*[@id="bd"]/h1/table/tbody/tr[1]/td[1]/a').text:
                 assert self.driver.find_element(By.ID, "NBS140").text == "Field Follow-up"
                 assert self.driver.find_element(By.ID, "NBS166").text == "300 - Gonorrhea"
            else:
                assert self.driver.find_element(By.ID, "NBS166").text == "710 - Syphilis, primary"
            assert self.driver.find_element(By.ID, "NBS143").text == "6-Yes, Notifiable"
            assert self.driver.find_element(By.ID, "NBS161").text == "DIS LocalUser"
            assert self.driver.find_element(By.ID, "NBS162").text == str(self.get_todaysdate_m_d_y())
            assert self.driver.find_element(By.ID, "NBS170").text == str(self.get_todaysdate_m_d_y())
            assert self.driver.find_element(By.ID, "NBS172").text == data["NBS172"]
            assert self.driver.find_element(By.ID, "NBS173").text == "C - Infected, Brought to Treatment"
            assert self.driver.find_element(By.ID, "NBS174").text == str(self.get_todaysdate_m_d_y())
            assert self.driver.find_element(By.ID, "NBS175").text == "DIS LocalUser"
            assert self.driver.find_element(By.ID, "NBS176").text == "Supervisor StateUser"
            assert self.driver.find_element(By.ID, "NBS187").text == str(self.get_todaysdate_m_d_y())
            # need to verify interview status is set to I or not
            # assert self.driver.find_element(By.ID, "NBS192").text == "I - Interviewed"

        elif headers == "Core Info":
            if "Gonorrhea" in self.driver.find_element(By.XPATH, '//*[@id="bd"]/h1/table/tbody/tr[1]/td[1]/a').text:

                assert self.data[self.std_core_info_900_status] in self.driver.find_element(By.ID,self.std_core_info_900_status).text

                assert self.driver.find_element(By.ID,self.std_core_info_900_status).text == self.jurisdiction_dict[self.data[self.std_core_info_eHARS_record_search]]

                assert self.driver.find_element(By.ID, self.std_core_info_900_status).text == self.jurisdiction_dict[self.data[self.std_core_info_case_sampled]]

            self.driver.find_element(By.ID, "tabs0head3").click()
            assert self.driver.find_element(By.ID, "NBS242").text == self.hangoutinfo_dict[data[self.std_core_info_places_to_meet_partners]]
            assert self.driver.find_element(By.ID, "NBS244").text == self.hangoutinfo_dict[data[self.std_core_info_places_to_have_sex]]

            assert self.driver.find_element(By.ID, self.std_core_info_female_partners).text == self.hangoutinfo_dict[data[self.std_core_info_female_partners]]
            if data[self.std_core_info_female_partners] == "Y":
                assert self.driver.find_element(By.ID, "NBS224").text == data["NBS224"]

            assert self.driver.find_element(By.ID, self.std_core_info_male_partners).text == self.hangoutinfo_dict[
                data[self.std_core_info_male_partners]]
            if data[self.std_core_info_male_partners] == "Y":
                assert self.driver.find_element(By.ID, "NBS226").text == data["NBS226"]

            assert self.driver.find_element(By.ID, self.std_core_info_trans_gen).text == self.hangoutinfo_dict[
                data[self.std_core_info_trans_gen]]
            if data[self.std_core_info_trans_gen] == "Y":
                assert self.driver.find_element(By.ID, "NBS228").text == data["NBS228"]

            assert self.driver.find_element(By.ID, self.std_core_info_female_partners_interview_period).text == self.hangoutinfo_dict[
                data[self.std_core_info_female_partners_interview_period]]
            if data[self.std_core_info_female_partners_interview_period] == "Y":
                assert self.driver.find_element(By.ID, "NBS130").text == data["NBS130"]

            assert self.driver.find_element(By.ID, self.std_core_info_male_partners_interview_period).text == self.hangoutinfo_dict[
                data[self.std_core_info_male_partners_interview_period]]
            if data[self.std_core_info_male_partners_interview_period] == "Y":
                assert self.driver.find_element(By.ID, "NBS132").text == data["NBS132"]

            assert self.driver.find_element(By.ID, self.std_core_info_transgen_partners_interview_period).text == self.hangoutinfo_dict[
                data[self.std_core_info_transgen_partners_interview_period]]
            if data[self.std_core_info_transgen_partners_interview_period] == "Y":
                assert self.driver.find_element(By.ID, "NBS134").text == data["NBS134"]

            assert self.driver.find_element(By.ID, self.std_core_info_met_through_internet).text == self.hangoutinfo_dict[
                       data[self.std_core_info_met_through_internet]]
            
            
    def add_interview(self):
            time.sleep(1)
            self.set_contract_record()
            self.edit_add_interview()
            self.associateInvestigation_add_interview()
            self.print_add_interview()
            # self.delete_add_interview()
            
            
            
    def edit_add_interview(self):
        self.driver.find_element(By.XPATH, '//*[@id="interviewListID"]/tbody/tr[1]/td[1]/a').click()
        time.sleep(2)
        self.switch_to_current_window()
        self.driver.maximize_window()
        element = self.driver.find_elements(By.NAME, "Edit")
        if element:
            element[0].click()
        # self.switch_to_current_window()
        # self.set_dropdown_value(self.std_add_interview_type)
        self.set_and_return_dropdown_value(self.std_add_interview_location)
        self.set_and_return_dropdown_value(self.std_add_interview_contacts_named)
        self.set_and_return_dropdown_value(self.std_add_interview_900_site_type)
        self.set_and_return_dropdown_value(self.std_add_interview_interventions)
        self.set_and_return_dropdown_value(self.std_add_interview_case_status)
        
        element = self.driver.find_elements(By.NAME, "Submit")
        if element:
            element[0].click()
        
        
    def set_dropdown_value(self, locate,value):
        element = self.driver.find_element(By.ID, locate)
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        if element.tag_name.lower() == 'select':
            select_element = Select(self.driver.find_element(By.ID, locate))
            # random_index = random.randint(2, len(select_element.options))
            # select_dropdown_xpath = (
            #         f"//*[@id='{locate}']/option[".__add__(
            #             str(random_index)) + "]")
            # dropdown_value = (self.driver.find_element(By.XPATH, select_dropdown_xpath).get_attribute("value"))
            # time.sleep(1)
            # select_element.select_by_value("INITIAL")
            select_element.select_by_value(value)
        
    def print_add_interview(self):
        time.sleep(2)
        self.switch_to_current_window()
        self.driver.find_element(By.XPATH, "//*[@name='Submit' and @value='Print']").click()
        time.sleep(1)
        self.switch_to_current_window()
        self.driver.close()
        time.sleep(1)
        self.switch_to_current_window()
        self.driver.find_element(By.NAME,"Cancel").click()
       
    
    # def delete_add_interview(self):
    #     time.sleep(2)
    #     self.switch_to_current_window()
    #     self.driver.find_element(By.NAME, "Delete").click()
    #     time.sleep(2)
    #     self.driver.switch_to.alert.accept()
        
    def associateInvestigation_add_interview(self):
        self.driver.find_element(By.NAME, "AssociateInvestigations").click()
        time.sleep(2)
        self.switch_to_current_window()
        self.driver.find_element(By.XPATH, '//*[@id="coinflist"]/tbody/tr[1]/td[1]/div/input').click()
        self.driver.find_element(By.NAME, "Submit").click()
        # self.switch_to_original_window()
    
    def close_add_interview(self):
        self.driver.find_element(By.XPATH, "//*[@name='Cancel' and @value='Close']").click()
        
        
        
    def add_new_contact_record(self,random_number):
        patient = PatientObject(self.driver)
        self.switch_to_original_window()
        self.driver.find_element(By.NAME, "submitct").click()
        time.sleep(1)
        self.switch_to_current_window()
        self.driver.find_element(By.ID, "DEM102").send_keys("%")
        ele= self.driver.find_elements(By.NAME,"Search")
        if ele :
         ele[0].click()
        self.driver.find_element(By.ID, "Add").click()
        time.sleep(1)
        self.switch_to_current_window()
        self.driver.maximize_window()
        self.driver.find_element(By.ID, "tabs0head0").click()
        self.patient_information()
        self.add_contact_record()
        self.add_followup_Investigation()
        time.sleep(2)
        
        self.switch_to_current_window()
        self.edit_new_contact_record()
        
    def add_contact_record(self):
        print("inside Contact record")
        self.driver.find_element(By.ID, "tabs0head1").click()
        time.sleep(2)
        self.setcontact_info()
        self.exposure_information()
        self.setdisposition_details()

        
        
    def patient_information(self):
        print("inside patient info")

        time.sleep(1)
        self.driver.find_element(By.ID,"DEM196").click()
        self.driver.find_element(By.ID,"DEM196").send_keys("Comments:" + NBSUtilities.generate_random_string(100))
        
        self.setPatientNameSection(self.random_number)
        self.setPersonalDetails(self.random_number)
        self.setPersonHomeAddressDetails(self.random_number)
        self.setPersonWorkAddressDetails(self.random_number)
        self.setPersonPhoneDetails()
        self.setPersonRaceDetails()
    
    def setPatientNameSection(self,random_number):  
        # Last Name
        self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                    NBSConstants.TYPE_CODE_NAME,
                                    self.add_patient_last_name,
                                    NBSConstants.APPEND_TEST_FOR_LAST_NAME.__add__("-").__add__(str(random_number)))
        # First Name
        self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                    NBSConstants.TYPE_CODE_NAME,
                                    self.add_patient_first_name,
                                    NBSConstants.APPEND_TEST_FOR_FIRST_NAME.__add__("-").__add__(
                                        str(random_number)))
        # Middle Name
        self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                    NBSConstants.TYPE_CODE_NAME,
                                    self.add_patient_middle_name,
                                    NBSConstants.APPEND_TEST_FOR_MIDDLE_NAME.__add__("-").__add__(
                                        str(random_number)))
        # Patient Suffix
        # self.setValueForHTMLElement(NBSConstants.HTML_SET_DROP_DOWN_RANDOM_VALUE,
        #                             NBSConstants.TYPE_CODE_NAME,
        #                             self.add_patient_name_suffix,
        #                             random_number)
        self.selectDropDownOptionValueRandom("DEM107")
            
    def setPersonalDetails(self, random_number):
        # address as of date
        dob="03/03/1950"
        calendar_button = self.driver.find_element(By.ID, 'DEM115')
        calendar_button.click()
        calendar_button.clear()
        calendar_button.send_keys(dob)
        
        # self.patient_dob = self.driver.find_element('id', "patientDOB").get_attribute("value")

        # current Sex

        self.setDropdownValueRandom(NBSConstants.TYPE_CODE_XPATH,
                                    self.add_patient_current_sex)

        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                        NBSConstants.WAITING_TIME_IN_SECONDS)

        # Patient Deceased
        self.setDropdownValueRandom(NBSConstants.TYPE_CODE_XPATH,
                                    self.is_patient_deceased)

        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                        NBSConstants.WAITING_TIME_IN_SECONDS)

        # if death occurred, then death date element is enable
        deceased_date = self.findElement(NBSConstants.TYPE_CODE_NAME,
                                            self.check_patient_deceased_enabled)
        if deceased_date.is_enabled():
            dob="03/03/1990"
            calendar_button = self.driver.find_element(By.ID, 'DEM128')
            calendar_button.click()
            calendar_button.clear()
            calendar_button.send_keys(dob)

        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                        NBSConstants.WAITING_TIME_IN_SECONDS)
        # Patient Martial Status
        self.setDropdownValueRandom(NBSConstants.TYPE_CODE_XPATH,self.patient_martial_status)

    def setPersonHomeAddressDetails(self, random_number):
            # Address1
            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                        NBSConstants.TYPE_CODE_NAME,
                                        self.home_patient_address_street1,
                                        NBSConstants.APPEND_TEST_FOR_STREET_ADDR1.__add__(str(random_number)))
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
            # Address2
            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                        NBSConstants.TYPE_CODE_NAME,
                                        self.home_patient_address_street2,
                                        NBSConstants.APPEND_TEST_FOR_STREET_ADDR2.__add__(str(random_number)))
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
            # City
            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                        NBSConstants.TYPE_CODE_NAME,
                                        self.home_patient_address_city,
                                        NBSConstants.APPEND_TEST_FOR_STREET_CITY.__add__(str(random_number)))
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
            # State
            self.setValueForHTMLElement(NBSConstants.HTML_SET_DROP_DOWN_RANDOM_VALUE,
                                        NBSConstants.TYPE_CODE_XPATH,
                                        self.home_patient_address_state,
                                        random_number)
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
            # postal
            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                        NBSConstants.TYPE_CODE_NAME,
                                        self.home_patient_address_postal,
                                        NBSUtilities.generate_random_int(5))
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)

            # county
            self.setValueForHTMLElement(NBSConstants.HTML_SET_DROP_DOWN_RANDOM_VALUE,
                                        NBSConstants.TYPE_CODE_XPATH,
                                        self.home_patient_address_county,
                                        random_number)
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
            # Census
            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                        NBSConstants.TYPE_CODE_NAME,
                                        self.home_patient_address_census,
                                        NBSUtilities.generate_random_int(4))
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
            # country
            self.setValueForHTMLElement(NBSConstants.HTML_SET_DROP_DOWN_RANDOM_VALUE,
                                        NBSConstants.TYPE_CODE_XPATH,
                                        self.home_patient_address_country,
                                        random_number)
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
        
    def setPersonWorkAddressDetails(self, random_number):

            # Address1
            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                        NBSConstants.TYPE_CODE_NAME,
                                        self.work_patient_address_street1,
                                        NBSConstants.APPEND_TEST_FOR_STREET_ADDR1.__add__(str(random_number)))
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
            # Address2
            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                        NBSConstants.TYPE_CODE_NAME,
                                        self.work_patient_address_street2,
                                        NBSConstants.APPEND_TEST_FOR_STREET_ADDR2.__add__(str(random_number)))
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
            # City
            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                        NBSConstants.TYPE_CODE_NAME,
                                        self.work_patient_address_city,
                                        NBSConstants.APPEND_TEST_FOR_STREET_CITY.__add__(str(random_number)))
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
            # State
            self.setValueForHTMLElement(NBSConstants.HTML_SET_DROP_DOWN_RANDOM_VALUE,
                                        NBSConstants.TYPE_CODE_XPATH,
                                        self.work_patient_address_state,
                                        random_number)
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
            # postal
            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                        NBSConstants.TYPE_CODE_NAME,
                                        self.work_patient_address_postal,
                                        NBSUtilities.generate_random_int(5))
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)

            # county
            self.setValueForHTMLElement(NBSConstants.HTML_SET_DROP_DOWN_RANDOM_VALUE,
                                        NBSConstants.TYPE_CODE_XPATH,
                                        self.work_patient_address_county,
                                        random_number)
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
            # Census
            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                        NBSConstants.TYPE_CODE_NAME,
                                        self.work_patient_address_census,
                                        NBSUtilities.generate_random_int(4))
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
            # country
            self.setValueForHTMLElement(NBSConstants.HTML_SET_DROP_DOWN_RANDOM_VALUE,
                                        NBSConstants.TYPE_CODE_XPATH,
                                        self.work_patient_address_country,
                                        random_number)
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
            #adress comments
            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT, NBSConstants.TYPE_CODE_NAME,
                self.work_address_comments,
                "Comments:" + NBSUtilities.generate_random_string(100))
    
    def setPersonPhoneDetails(self):

            # Home Phone
            home_phone = (NBSUtilities.generate_random_int(3).__add__("-").__add__(NBSUtilities.generate_random_int(3))
                          .__add__("-").__add__(NBSUtilities.generate_random_int(4)))
            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                        NBSConstants.TYPE_CODE_NAME,
                                        self.patient_home_phone, home_phone)
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
            # Work Phone
            work_phone = (NBSUtilities.generate_random_int(3).__add__("-").__add__(NBSUtilities.generate_random_int(3))
                          .__add__("-").__add__(NBSUtilities.generate_random_int(4)))
            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                        NBSConstants.TYPE_CODE_NAME,
                                        self.patient_work_phone, work_phone)
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                        NBSConstants.TYPE_CODE_NAME,
                                        self.patient_work_phone_ext, NBSUtilities.generate_random_int(4))
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
            # Cell Phone
            cell_phone = (NBSUtilities.generate_random_int(3).__add__("-").__add__(NBSUtilities.generate_random_int(3))
                          .__add__("-").__add__(NBSUtilities.generate_random_int(4)))
            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                        NBSConstants.TYPE_CODE_NAME,
                                        self.patient_cell_phone, cell_phone)
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
            # Email
            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                        NBSConstants.TYPE_CODE_NAME,
                                        self.patient_email, NBSUtilities.generate_random_string(6)
                                        .__add__("@uuu.com"))
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
            
    def setPersonRaceDetails(self):

            # Ethnicity
            self.setDropdownValueRandom(NBSConstants.TYPE_CODE_XPATH,
                                        "//*[@id='DEM155']")

            # Alaskan Race
            self.performButtonClick(NBSConstants.TYPE_CODE_NAME, self.patient_indian_alaskan_race)
            # Asian Race
            self.performButtonClick(NBSConstants.TYPE_CODE_NAME, self.patient_asian_race)
            # African Race
            self.performButtonClick(NBSConstants.TYPE_CODE_NAME, self.patient_african_race)
            # Hawaiian Race
            self.performButtonClick(NBSConstants.TYPE_CODE_NAME, self.patient_hawaiian_race)
            # White Race
            self.performButtonClick(NBSConstants.TYPE_CODE_NAME, self.patient_white_race)
            # Other Race
            self.performButtonClick(NBSConstants.TYPE_CODE_NAME, self.patient_other_race)
            # Refused To Answer
            self.performButtonClick(NBSConstants.TYPE_CODE_NAME, self.patient_refused_to_answer)
            # Not Asked
            self.performButtonClick(NBSConstants.TYPE_CODE_NAME, self.patient_not_asked)
            # Unknown Race
            self.performButtonClick(NBSConstants.TYPE_CODE_NAME, self.patient_unknown_race)
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)

    def setcontact_info(self):
        
        self.set_dropdown_value("CON141","THSPAT")
        # self.driver.find_element(By.ID,"CON141").send_keys("This patient")
        time.sleep(1)
        self.selectDropDownOptionValueRandom(self.contact_record_named)
        self.driver.find_element(By.NAME, 'CON143_textbox').click()
        self.driver.find_element(By.NAME, 'CON143_textbox').clear()
        self.driver.find_element(By.NAME, 'CON143_textbox').send_keys("Xerogeanes, John")
        time.sleep(1)
        self.selectDropDownOptionValueRandom(self.contact_health_status)
        self.driver.find_element(By.ID, self.contact_record_height).send_keys(NBSUtilities.generate_random_int(2))
        self.driver.find_element(By.ID, self.contact_record_size).send_keys(NBSUtilities.generate_random_int(2))
        self.driver.find_element(By.NAME, self.contact_record_hair_name).send_keys(self.contact_record_hair_value)
        self.driver.find_element(By.NAME, self.contact_record_complexion_name).send_keys(self.contact_record_complexion_value)
        self.driver.find_element(By.ID, self.other_identifying_info).send_keys("Other Identifying Comments",
                                                                                           NBSUtilities.generate_random_string(
                                                                                               100))
        
    def exposure_information(self):
        time.sleep(3)
        self.set_dropdown_value("CON144","P3")
        self.driver.find_element(By.ID, self.std_case_info_sexual_frequency).clear()
        ran_int = NBSUtilities.generate_random_int(2)
        self.driver.find_element(By.ID, self.std_case_info_sexual_frequency).send_keys(
            ran_int)
        self.data[self.std_case_info_sexual_frequency]=ran_int
        self.driver.find_element(By.ID, self.std_case_info_needle_sharing_frequency).clear()
        self.driver.find_element(By.ID, self.std_case_info_needle_sharing_frequency).send_keys(
            NBSUtilities.generate_random_int(2))
        self.set_and_return_dropdown_value(self.std_case_info_spouse)
        self.set_and_return_dropdown_value(self.std_case_info_met_op)
        self.set_and_return_dropdown_value(self.std_case_info_internet_info)
        
    def setdisposition_details(self):
       
        self.set_dropdown_value(self.contact_record_procession_decision, "FF")
        self.driver.find_element(By.ID, self.contact_record_comments).send_keys("Comments",
                                                                                           NBSUtilities.generate_random_string(
                                                                                               100))

    def add_followup_Investigation(self):
        print("inside followup_Investigation")
        self.driver.find_element(By.ID, "tabs0head2").click()
        time.sleep(2)
        self.setTodayDate(By.ID,self.std_case_info_inv_start_date)
        # self.driver.find_element(By.ID,self.std_case_info_inv_start_date).send_keys(self.setTodayDate)
        self.driver.find_element(By.ID,self.field_followup_inv).send_keys("1")
        self.driver.find_element(By.ID, self.field_followup_lookup_button).click()
        self.setTodayDate(By.ID,self.date_assigned_to_inv)
        # self.driver.find_element(By.ID,self.date_assigned_to_inv).send_keys(self.setTodayDate)
        self.set_and_return_dropdown_value(self.std_case_mgmt_internet_followup)
        self.driver.find_element(By.NAME, self.std_case_mgmt_noti_of_exposure).send_keys(
        self.std_case_mgmt_noti_of_exposure_value)
        ele = self.driver.find_elements(By.NAME,'Submit')
        if ele:
            ele[0].click()
        # self.driver.find_elements(By.XPATH, '//*[@id="doc3"]/form/div[2]/input[5]')
        time.sleep(2)    
        element = self.driver.find_elements(By.NAME, "Cancel")
        if element:
            element[0].click()
        
        # self.driver.close()
        
        
    def edit_new_contact_record(self):
        
        self.driver.find_element(By.XPATH,'//*[@id="contactNamedByPatListID"]/tbody/tr[1]/td[2]/a').click()
        time.sleep(2)
        self.switch_to_current_window()
        self.driver.maximize_window()
        element = self.driver.find_elements(By.NAME, "Edit")
        if element:
            element[0].click()
           
        self.setPersonWorkAddressDetails(self.random_number)
        self.setPersonPhoneDetails()
        # self.add_contact_record()
        ele = self.driver.find_elements(By.NAME,'Submit')
        if ele:
            ele[0].click()
            
        element=  self.driver.find_elements(By.NAME, "AssociateInvestigation")
        if element:
            element[0].click()
        time.sleep(1)
        self.switch_to_current_window()
        self.driver.find_element(By.XPATH,'//*[@id="coinflist"]/tbody/tr[2]/td[1]/div/input').click()
        self.set_and_return_dropdown_value('pdCodes2')
        self.driver.find_element(By.NAME, "Submit").click()
        time.sleep(2)
        self.switch_to_current_window()
        element=self.driver.find_elements(By.NAME, "Cancel")
        if element:
            element[0].click()
        time.sleep(2)
        self.switch_to_current_window()
        element = self.driver.find_element(By.XPATH, '//*[@name="pageTop"]')
        element = element.text
        if "View Investigation" in element:
            self.driver.find_element(By.XPATH, '//*[@id="bd"]/div[1]/a').click()
        
        
        
        