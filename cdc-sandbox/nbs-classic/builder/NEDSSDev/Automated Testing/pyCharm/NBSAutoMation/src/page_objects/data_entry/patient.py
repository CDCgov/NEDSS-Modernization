import os
import time

from selenium.common import NoSuchElementException
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.support.wait import WebDriverWait

from src.page_objects.BasePageObject import BasePageObject
from src.utilities import constants
from src.utilities.constants import NBSConstants
from src.utilities.db_utilities import DBUtilities
from src.utilities.log_config import LogUtils
from src.utilities.nbs_utilities import NBSUtilities
from datetime import datetime


class PatientObject(BasePageObject):
    logger = LogUtils.loggen(__name__)
    db_utilities = DBUtilities()
    race_type = None

    # Patient
    patient_search_by_dob1 = "patientDOB1"
    patient_search_by_dob2 = "patientDOB2"
    patient_search_by_dob3 = "patientDOB3"
    patient_search_by_gender = "personSearch.currentSex"
    patient_search_by_straddr1 = "personSearch.streetAddr1"
    patient_search_by_city = "personSearch.cityDescTxt"
    patient_search_by_state = "personSearch.state"
    patient_search_by_postal = "personSearch.zipCd"
    patient_search_by_type_code = "personSearch.typeCd"
    patient_search_by_type_code_value = "personSearch.rootExtensionTxt"
    patient_search_by_phone_number = "personSearch.phoneNbrTxt"
    patient_search_by_email = "personSearch.emailAddress"
    patient_search_by_ethnic_ind = "personSearch.ethnicGroupInd"
    patient_search_by_race_ind = "personSearch.raceCd"
    patient_search_by_patient_id = "personSearch.localID"
    nbs_data_entry_menu = "Data Entry"
    nbs_data_entry_patient_menu = "Patient"
    patient_search_submit_id = "Submit"
    patient_search_page_title = "NBS:Find Patient"
    patient_search_by_last_name = "personSearch.lastName"
    patient_search_by_first_name = "personSearch.firstName"
    add_patient_geneal_comments = "person.thePersonDT.description"
    add_patient_last_name = "person.thePersonDT.lastNm"
    add_patient_first_name = "person.thePersonDT.firstNm"
    add_patient_middle_name = "person.thePersonDT.middleNm"
    add_patient_name_suffix = "person.thePersonDT.nmSuffix"
    add_patient_date_of_birth_icon = "patientDOBIcon"
    add_patient_date_of_birth = "patientDOB"
    add_patient_current_sex = "//*[@id='DEM113']"
    add_patient_birth_sex = "//*[@id='DEM114']"
    is_patient_deceased = "//*[@id='DEM127']"
    check_patient_deceased_enabled = "patientDeceasedDate"
    patient_deceased_date_icon = "deceasedDateIcon"
    patient_martial_status = "#DEM140"
    patient_hiv_case_id = "person.thePersonDT.eharsId"
    patient_address_street1 = "address[0].thePostalLocatorDT_s.streetAddr1"
    patient_address_street2 = "address[0].thePostalLocatorDT_s.streetAddr2"
    patient_address_city = "address[0].thePostalLocatorDT_s.cityDescTxt"
    patient_address_state = "//*[@id='DEM162']"
    patient_address_postal = "address[0].thePostalLocatorDT_s.zipCd"
    patient_address_county = "//*[@id='DEM165']"
    patient_address_census = "address[0].thePostalLocatorDT_s.censusTract"
    patient_address_country = "//*[@id='DEM167']"
    patient_home_phone = "patientHomePhone"
    patient_work_phone = "patientWorkPhone"
    patient_work_phone_ext = "patientWorkPhoneExt"
    patient_cell_phone = "patientCellPhone"
    patient_email = "patientEmail"
    patient_indian_alaskan_race = "pamClientVO.americanIndianAlskanRace"
    patient_asian_race = "pamClientVO.asianRace"
    patient_african_race = "pamClientVO.africanAmericanRace"
    patient_hawaiian_race = "pamClientVO.hawaiianRace"
    patient_white_race = "pamClientVO.whiteRace"
    patient_other_race = "pamClientVO.otherRace"
    patient_refused_to_answer = "pamClientVO.refusedToAnswer"
    patient_not_asked = "pamClientVO.notAsked"
    patient_unknown_race = "pamClientVO.unKnownRace"
    patient_identification_type = "//*[@id='typeID']"
    patient_assigning_auth = "//*[@id='assigningAuthority']"
    patient_id_value = "idValue"
    add_identification_record_to_section = "//*[@id='AddButtonToggleIdSubSection']/td/input"
    update_identification_record_to_section = "//*[@id='UpdateButtonToggleIdSubSection']/td/input"
    view_record_from_section = "//*[@id='viewIdSubSection']"
    edit_record_from_section = "//*[@id='editIdSubSection']"
    delete_record_from_section = "//*[@id='deleteIdSubSection']"
    create_new_patient_submit_button = "Submit"
    create_new_patient_by_extended = "//*[@id='printExport']/input[3]"
    patient_name_date_icon_extended = "NmAsOfIcon"
    patient_name_type_extended = "NmType"
    patient_name_prefix_extended = "NmPrefix"
    patient_last_name_extended = "NmLast"
    patient_second_last_name_extended = "NmSecLast"
    patient_first_name_extended = "NmFirst"
    patient_middle_name_extended = "NmMiddle"
    patient_middle_name_second_extended = "NmSecMiddle"
    patient_name_suffix_extended = "NmSuffix"
    patient_name_degree_extended = "NmDegree"
    name_add_record_to_section_extended = "//*[@id='AddButtonToggleIdSubSection']/td/input"
    name_update_record_to_section_extended = "//*[@id='UpdateButtonToggleIdSubSection']/td/input"
    name_add_new_record_to_section_extended = "//*[@id='AddNewButtonToggleIdSubSection']/td/input"
    patient_addr_date_icon_extended = "AddrAsOfIcon"
    patient_addr_type_extended = "AddrType"
    patient_addr_use_extended = "AddrUse"
    patient_addr_street1_extended = "AddrStreet1"
    patient_addr_street2_extended = "AddrStreet2"
    patient_addr_city_extended = "AddrCity"
    patient_addr_state_extended = "AddrState"
    patient_addr_postal_extended = "AddrZip"
    patient_addr_county_extended = "AddrCnty"
    patient_addr_census_extended = "AddrCensusTract"
    patient_addr_cntry_extended = "AddrCntry"
    patient_addr_comments_extended = "AddrComments"
    addr_add_record_to_section_extended = "//*[@id='AddAddrButtonToggleIdSubSection']/td/input"
    addr_update_record_to_section_extended = "//*[@id='UpdateAddrButtonToggleIdSubSection']/td/input"
    addr_add_new_record_to_section_extended = "//*[@id='AddNewAddrButtonToggleIdSubSection']/td/input"
    view_action_type = "View"
    edit_action_type = "Edit"
    delete_action_type = "Delete"

    patient_phone_date_icon_extended = "PhAsOfIcon"
    patient_phone_type_extended = "PhType"
    patient_phone_use_extended = "PhUse"
    patient_phone_cntry_code_extended = "PhCntryCd"
    patient_phone_number_extended = "PhNum"
    patient_phone_ext_extended = "PhExt"
    patient_phone_email_extended = "PhEmail"
    patient_phone_url_extended = "PhUrl"
    patient_phone_comments_extended = "PhComments"
    phone_add_record_to_section_extended = "//*[@id='AddPhButtonToggleIdSubSection']/td/input"
    phone_update_record_to_section_extended = "//*[@id='UpdatePhButtonToggleIdSubSection']/td/input"
    phone_add_new_record_to_section_extended = "//*[@id='AddNewPhButtonToggleIdSubSection']/td/input"

    patient_ident_date_icon_extended = "IdAsOfIcon"
    patient_ident_type_extended = "IdType"
    patient_ident_assign_type_extended = "IdAssgn"
    patient_ident_id_value_extended = "IdValue"
    ident_add_record_to_section_extended = "//*[@id='AddIdenButtonToggleIdSubSection']/td/input"
    ident_update_record_to_section_extended = "//*[@id='UpdateIdenButtonToggleIdSubSection']/td/input"
    ident_add_new_record_to_section_extended = "//*[@id='AddNewIdenButtonToggleIdSubSection']/td/input"

    patient_race_date_icon_extended = "RaceAsOfIcon"
    patient_race_type_extended = "RaceType"
    patient_race_detailed_extended = "//*[@id='RaceDetailCat']"
    patient_race_id_detailed_extended = "RaceDetailCat"

    race_add_record_to_section_extended = "//*[@id='AddRaceButtonToggleIdSubSection']/td/input"
    race_update_record_to_section_extended = "//*[@id='UpdateRaceButtonToggleIdSubSection']/td/input"
    race_add_new_record_to_section_extended = "//*[@id='AddNewRaceButtonToggleIdSubSection']/td/input"

    patient_ethnicity_date_icon_extended = "AsOfDateEthIcon"
    patient_ethnicity_extended = "person.thePersonDT.ethnicGroupInd"
    patient_ethnicity_reason_extended = "person.thePersonDT.ethnicUnkReasonCd"
    patient_ethnicity_orgin_extended = "SpanOrigin"

    patient_sab_date_icon_extended = "AsofDateSBIcon"
    patient_dob_date_icon_extended = "patientDOBIcon"
    patient_sab_current_sex_extended = "CurrSex"
    patient_sab_unknown_reason_extended = "person.thePersonDT.sexUnkReasonCd"
    patient_sab_tg_info_extended = "TransgenderInformation"
    patient_sab_additional_gender_extended = "AdditionalGender"
    patient_sab_birth_sex_extended = "BirSex"
    patient_sab_multiple_birth_extended = "MulBir"
    patient_sab_birth_order_extended = "BirOrder"
    patient_sab_birth_city_extended = "BirCity"
    patient_sab_birth_state_extended = "BirState"
    patient_sab_birth_county_extended = "BirCnty"
    patient_sab_birth_country_extended = "BirCntry"

    patient_mortality_date_icon_extended = "AsofDateMorbIcon"
    patient_mortality_deceased_extended = "person.thePersonDT.deceasedIndCd"
    patient_mortality_dod_extended = "DateOfDeathIcon"
    patient_mortality_death_city_extended = "DeathCity"
    patient_mortality_death_state_extended = "DeathState"
    patient_mortality_death_county_extended = "DeathCnty"
    patient_mortality_death_country_extended = "DeathCntry"

    patient_gi_date_icon_extended = "AsOfDateGenInfoIcon"
    patient_gi_martial_status_extended = "MarStatus"
    patient_gi_maid_in_name_extended = "MomMNm"
    patient_gi_number_of_adults_extended = "NumAdults"
    patient_gi_number_of_children_extended = "NumChild"
    patient_gi_primary_occupation_extended = "PriOccup"
    patient_gi_education_level_extended = "HLevelEdu"
    patient_gi_primary_language_extended = "PriLang"
    patient_gi_speak_english_extended = "SpeaksEnglishCd"
    patient_gi_hiv_code_extended = "eHARSID"

    patient_demographics_profile = "tabs0head2"
    patient_demographics_edit_profile = "//*[@id='patDemo']/table[1]/tbody/tr[1]/td[2]/input"
    patient_profile_events_tab = "tabs0head1"
    patient_profile_summary_tab = "tabs0head0"
    collapse_all_link_patient_profile = "Collapse All"
    expand_all_link_patient_profile = "Expand All"
    patient_summary_anchor_links = "Patient Summary"
    patient_open_investigation_anchor_link = "Open Investigations"
    patient_drr_anchor_link = "Documents Requiring Review"
    patient_summary_collapse = "//*[@id='subsect_basicInfo']/table/tbody/tr/td[1]/a"
    patient_expand_investigations = "//*[@id='subsect_Inv']/table/tbody/tr/td[1]/a/img"
    # sections
    identification_section = "Identification"
    address_section = "Address"
    tele_phone_section = "Telephone"
    name_section = "Name"
    race_section = "Race"
    ethnicity_section = "Ethnicity"
    sex_birth_section = "SexAndBirth"
    mortality_section = "Mortality"
    general_information_section = "General"
    provider_drug_section = "SUSCEPTIBILITY"

    def __init__(self, driver):
        super().__init__(driver)
        self.driver = driver
       

    def editExistingRecord(self, section_type, record_number, random_number, extended_screen=False):
        element = self.findElement(NBSConstants.TYPE_CODE_XPATH,
                                   PatientObject.get_element_id_by_section_number(section_type,
                                                                                  PatientObject.edit_action_type,
                                                                                  extended_screen=extended_screen))
        if element is not None:
            element.click()
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
            self.populateSectionRecord(section_type, str(random_number), extended_screen=extended_screen)
            updated_path = PatientObject.getPathForEditSection(section_type, extended_screen=extended_screen)
            # self.logger.info("updated_path::::" + updated_path)
            self.addRecordToSection(section_type, updated_path, extended_screen=extended_screen)
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)

    def deleteExistingRecord(self, section_type, record_number, random_number, extended_screen=False):
        element = self.findElement(NBSConstants.TYPE_CODE_XPATH,
                                   PatientObject.get_element_id_by_section_number(section_type,
                                                                                  PatientObject.delete_action_type,
                                                                                  extended_screen=extended_screen))
        if element is not None:
            element.click()
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)

    def displayRecordDetails(self, section_type, record_number, extended_screen=False):
        element = self.findElement(NBSConstants.TYPE_CODE_XPATH,
                                   PatientObject.get_element_id_by_section_number(section_type,
                                                                                  PatientObject.view_action_type,
                                                                                  extended_screen=extended_screen))
        if element is not None:
            element.click()
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)

    def setRepeatableSectionActions(self, section_type, random_number, extended_screen=False):
        try:
            if section_type != PatientObject.race_section:
                self.displayRecordDetails(section_type, NBSConstants.EDIT_RECORD_NUM_IN_SECTION,
                                          extended_screen=extended_screen)
                self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                NBSConstants.WAITING_TIME_IN_SECONDS)

                self.editExistingRecord(section_type, NBSConstants.EDIT_RECORD_NUM_IN_SECTION,
                                        str(random_number), extended_screen=extended_screen)
                self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                NBSConstants.WAITING_TIME_IN_SECONDS)

                self.deleteExistingRecord(section_type, NBSConstants.DELETE_RECORD_NUM_IN_SECTION,
                                          str(random_number), extended_screen=extended_screen)
                self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                NBSConstants.WAITING_TIME_IN_SECONDS)
        except Exception as ex:
            self.logger.info(ex)

    def addRecordToSection(self, section_type, update_path=None, extended_screen=False):
        element = None
        if not extended_screen:
            if section_type == PatientObject.identification_section:
                if update_path is None or not update_path:
                    element = self.findElement(NBSConstants.TYPE_CODE_XPATH,
                                               PatientObject.add_identification_record_to_section)
                else:
                    element = self.findElement(NBSConstants.TYPE_CODE_XPATH, update_path)
        else:
            if section_type == PatientObject.name_section:
                if update_path is None:
                    element = self.findElement(NBSConstants.TYPE_CODE_XPATH,
                                               PatientObject.name_add_record_to_section_extended)
                else:
                    element = self.findElement(NBSConstants.TYPE_CODE_XPATH, update_path)
            elif section_type == PatientObject.address_section:
                if update_path is None:
                    element = self.findElement(NBSConstants.TYPE_CODE_XPATH,
                                               PatientObject.addr_add_record_to_section_extended)
                else:
                    element = self.findElement(NBSConstants.TYPE_CODE_XPATH, update_path)
            elif section_type == PatientObject.tele_phone_section:
                if update_path is None:
                    element = self.findElement(NBSConstants.TYPE_CODE_XPATH,
                                               PatientObject.phone_add_record_to_section_extended)
                else:
                    element = self.findElement(NBSConstants.TYPE_CODE_XPATH, update_path)
            elif section_type == PatientObject.identification_section:
                if update_path is None:
                    element = self.findElement(NBSConstants.TYPE_CODE_XPATH,
                                               PatientObject.ident_add_record_to_section_extended)
                else:
                    element = self.findElement(NBSConstants.TYPE_CODE_XPATH, update_path)
            elif section_type == PatientObject.race_section:
                if update_path is None:
                    element = self.findElement(NBSConstants.TYPE_CODE_XPATH,
                                               PatientObject.race_add_record_to_section_extended)
                else:
                    element = self.findElement(NBSConstants.TYPE_CODE_XPATH, update_path)
        if element is not None:
            element.click()
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)

    def populateSectionRecord(self, section_type, random_number, extended_screen=False):
        if not extended_screen:
            if section_type == PatientObject.identification_section:
                self.setIdentificationRecordPopulate(str(random_number))
        else:
            if section_type == PatientObject.name_section:
                self.populateNameSection(str(random_number))
            elif section_type == PatientObject.address_section:
                self.populateAddressSection(str(random_number))
            elif section_type == PatientObject.tele_phone_section:
                self.populateTelephoneSection()
            elif section_type == PatientObject.identification_section:
                self.populateIdentificationDetails(str(random_number))
            elif section_type == PatientObject.race_section:
                self.populateRaceSection()
            elif section_type == PatientObject.ethnicity_section:
                self.populateEthnicitySection()
            elif section_type == PatientObject.sex_birth_section:
                self.populateSexAndBirthSection(str(random_number))
            elif section_type == PatientObject.mortality_section:
                self.populateMortalitySection(str(random_number))
            elif section_type == PatientObject.general_information_section:
                self.populateGeneralInfoSection(str(random_number))

    def setRepeatableSections(self, section_type, random_number, extended_screen=False):
        if section_type == PatientObject.race_section:
            self.populateSectionRecord(section_type, str(random_number), extended_screen=extended_screen)
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
            self.addRecordToSection(section_type, extended_screen=extended_screen)
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
        else:
            for i in range(1, NBSConstants.ADD_NUMBER_OF_REPEATABLE_SECTIONS + 1):
                self.populateSectionRecord(section_type, str(random_number), extended_screen=extended_screen)
                self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
                self.addRecordToSection(section_type, extended_screen=extended_screen)
                self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)

    def setSectionDetails(self, random_number, section_type=None, extended_screen=False):
        try:
            # Add Records To Sections
            self.setRepeatableSections(section_type, str(random_number), extended_screen=extended_screen)
            # Perform Actions (View/dit/Delete) To Sections
            self.setRepeatableSectionActions(section_type, str(random_number), extended_screen=extended_screen)
        except Exception as ex:
            self.logger.info("Error While setting up repeatable sections", ex)
        return True

    def addNewPatient(self, random_number, param=True):
        try:
            self.performButtonClick(NBSConstants.TYPE_CODE_NAME,
                                    PatientObject.create_new_patient_submit_button)
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT, NBSConstants.TYPE_CODE_NAME,
                                        PatientObject.add_patient_geneal_comments,
                                        "Comments:" + NBSUtilities.generate_random_string(100))
            # Name Section
            if param:
                if not self.setPatientNameSection(random_number):
                    return False
            else:
                if not self.setMergePatientNameSection(random_number):
                    return False

            # Patient Other Details
            if param:
                if not self.setPersonalDetails(random_number):
                    return False
            else:
                if not self.setMergePersonalDetails():
                    return False

            # Patient Address
            if not self.setPersonAddressDetails(random_number):
                return False

            # Patient Phone Details
            if param:
                if not self.setPersonPhoneDetails():
                    return False
            else:
                if not self.setMergePersonPhoneDetails():
                    return False
            if param:
                # Patient Race Details
                if not self.setPersonRaceDetails():
                    return False

                # Patient Identification Details

                if not self.setSectionDetails(random_number, PatientObject.identification_section, False):
                    return False

            # Create Patient
            self.performButtonClick(NBSConstants.TYPE_CODE_NAME,
                                    PatientObject.create_new_patient_submit_button)
        except Exception as ex:
            self.logger.info(ex)
            return False

        return True

    def addNewPatientByExtendedScreen(self, random_number):
        try:
            self.performButtonClick(NBSConstants.TYPE_CODE_NAME,
                                    PatientObject.create_new_patient_submit_button)
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
            self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                    PatientObject.create_new_patient_by_extended)

            if self.setDataOnExtendedScreen(random_number, "create"):
                # Create New Patient through Extended Screen
                self.performButtonClick(NBSConstants.TYPE_CODE_NAME,
                                        PatientObject.create_new_patient_submit_button)
                self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                2)
                return True
            else:
                return False

        except Exception as ex:
            self.logger.info(ex)
            return False

    def setDataOnExtendedScreen(self, random_number, mode):
        try:
            # Admin Section Details
            if not self.setAdministrativeSectionForExtended():
                return False
            # Patient Name Section Details
            if not self.setSectionDetails(random_number, PatientObject.name_section, extended_screen=True):
                return False
            # Patient Address Section Details
            if not self.setSectionDetails(random_number, PatientObject.address_section, extended_screen=True):
                return False
            # Patient Phone Section Details
            if not self.setSectionDetails(random_number, PatientObject.tele_phone_section, extended_screen=True):
                return False
            # Patient Identification Section Details
            if not self.setSectionDetails(random_number, PatientObject.identification_section, extended_screen=True):
                return False
            # Patient Race Section Details
            try:
                if mode != "edit":
                    self.setSectionDetails(random_number, PatientObject.race_section, extended_screen=True)
            except Exception as ex:
                self.logger.debug(f"Exception while setting race {str(ex)}")
                pass

            # Patient ETHNICITY Section Details
            if not self.populateEthnicitySection():
                return False

            # Patient Sex And Birth Section Details
            if not self.populateSexAndBirthSection(random_number):
                return False
            # Patient Mortality Section Details
            if not self.populateMortalitySection(random_number):
                return False
            if not self.populateGeneralInfoSection(random_number):
                return False
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, 2)
        except Exception as ex:
            self.logger.debug("Exception while populating sections", ex)
            return False
        return True

    def editPatientFromDemographicsSection(self, random_number):
        try:
            self.performButtonClick(NBSConstants.TYPE_CODE_ID,
                                    PatientObject.patient_demographics_profile)
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
            self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                    PatientObject.patient_demographics_edit_profile)

            if self.setDataOnExtendedScreen(random_number, "edit"):
                # Update Patient Profile through Demographics Screen
                self.performButtonClick(NBSConstants.TYPE_CODE_NAME,
                                        PatientObject.create_new_patient_submit_button)
                self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                2)
            else:
                return False
        except Exception as ex:
            self.logger.debug("Exception While Editing Patient Profile", ex)
            return False
        return True

    def verifyLinksWithDeleteValidation(self):
        # Summary Tab
        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                        2)
        self.performButtonClick(NBSConstants.TYPE_CODE_LINK_TEXT,
                                PatientObject.collapse_all_link_patient_profile)
        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                        NBSConstants.WAITING_TIME_IN_SECONDS)
        self.performButtonClick(NBSConstants.TYPE_CODE_LINK_TEXT,
                                PatientObject.expand_all_link_patient_profile)
        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                        NBSConstants.WAITING_TIME_IN_SECONDS)
        self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                PatientObject.patient_summary_collapse)
        self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                PatientObject.patient_summary_collapse)
        # EVENTS Tab
        self.performButtonClick(NBSConstants.TYPE_CODE_ID,
                                PatientObject.patient_profile_events_tab)
        self.performButtonClick(NBSConstants.TYPE_CODE_LINK_TEXT,
                                PatientObject.expand_all_link_patient_profile)
        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                        NBSConstants.WAITING_TIME_IN_SECONDS)
        self.performButtonClick(NBSConstants.TYPE_CODE_LINK_TEXT,
                                PatientObject.collapse_all_link_patient_profile)
        self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                PatientObject.patient_expand_investigations)
        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                        NBSConstants.WAITING_TIME_IN_SECONDS)
        self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                PatientObject.patient_expand_investigations)

        # Demographics Tab

        self.performButtonClick(NBSConstants.TYPE_CODE_ID,
                                PatientObject.patient_demographics_profile)
        self.performButtonClick(NBSConstants.TYPE_CODE_LINK_TEXT,
                                PatientObject.expand_all_link_patient_profile)
        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                        NBSConstants.WAITING_TIME_IN_SECONDS)
        self.performButtonClick(NBSConstants.TYPE_CODE_LINK_TEXT,
                                PatientObject.collapse_all_link_patient_profile)
        self.performButtonClick(NBSConstants.TYPE_CODE_LINK_TEXT, "Patient Summary")
        self.performButtonClick(NBSConstants.TYPE_CODE_LINK_TEXT, "Administrative")
        self.performButtonClick(NBSConstants.TYPE_CODE_LINK_TEXT, "Name")
        self.performButtonClick(NBSConstants.TYPE_CODE_LINK_TEXT, "Address")
        self.performButtonClick(NBSConstants.TYPE_CODE_LINK_TEXT, "Phone & Email")
        self.performButtonClick(NBSConstants.TYPE_CODE_LINK_TEXT, "Identification")
        self.performButtonClick(NBSConstants.TYPE_CODE_LINK_TEXT, "Race")
        self.performButtonClick(NBSConstants.TYPE_CODE_LINK_TEXT, "Ethnicity")
        self.performButtonClick(NBSConstants.TYPE_CODE_LINK_TEXT, "Sex & Birth")
        self.performButtonClick(NBSConstants.TYPE_CODE_LINK_TEXT, "Mortality")
        self.performButtonClick(NBSConstants.TYPE_CODE_LINK_TEXT, "General Patient Information")
        self.performButtonClick(NBSConstants.TYPE_CODE_NAME, "delete")
        try:
            WebDriverWait(self.driver, 5).until(EC.alert_is_present())
            alert = self.driver.switch_to.alert
            alert.accept()
            return True
        except Exception as ex:
            self.logger.info("Error while accepting popup confirmation", ex)
            return False

    def setPatientSearchValues(self, type_code, path, append_element, random_number, patient_uid):
        person_locator_values = {}
        tele_phone_dict = {}
        try:
            person_locator_values = DBUtilities().getProviderCityStatePostal(patient_uid)
            # ident_dict = DBUtilities.getProviderIdentificationRecord(patient_uid)
            tele_phone_dict = DBUtilities().getProviderContactTelephone(patient_uid)
            # person_race_list = DBUtilities.getPersonSelectedRaces(patient_uid)
        except Exception as ex:
            self.logger.debug("Exception While Getting values from Database", ex)

        # search for last name or first name or wildcard (lastname)
        if append_element == NBSConstants.ADD_PATIENT_LAST_NAME_WILD:
            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT, type_code, path, "%")
        elif append_element is None and random_number is None:
            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT, type_code, path,
                                        PatientObject.getPatienetIdFromSystem(patient_uid))
        elif append_element == PatientObject.add_patient_date_of_birth:
            if person_locator_values.get("dob"):
                date_of_birth = person_locator_values.get("dob")
                self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT, type_code, path.__add__("1"),
                                            date_of_birth.strftime("%m"))
                self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT, type_code, path.__add__("2"),
                                            date_of_birth.strftime("%d"))
                self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT, type_code, path.__add__("3"),
                                            date_of_birth.strftime("%Y"))
        elif append_element == "email":
            if tele_phone_dict.get("email"):
                self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT, type_code, path,
                                            tele_phone_dict.get("email"))
        elif append_element == "telephone":
            if tele_phone_dict.get("telephone"):
                self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT, type_code, path,
                                            tele_phone_dict.get("telephone"))
        else:
            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT, type_code, path,
                                        append_element.__add__("-").__add__(str(random_number)))

        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                        NBSConstants.WAITING_TIME_IN_SECONDS)

        self.performButtonClick(NBSConstants.TYPE_CODE_NAME,
                                PatientObject.create_new_patient_submit_button)
        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)

    def searchPatient(self, type_code, path, append_text, random_number, patient_uid):
        try:
            self.setPatientSearchValues(type_code, path, append_text, random_number, patient_uid)
            next_elem = self.findElement("LINK_TEXT", "Next")
        except NoSuchElementException:
            next_elem = None
        if next_elem is not None:
            elem1 = self.findElementInMultiplePages(patient_uid)
            if elem1 is not None:
                elem1.click()
                self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
                self.performButtonClick("LINK_TEXT", NBSConstants.NBS_HOME_PAGE)
                self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
                self.performButtonClick("LINK_TEXT", NBSConstants.NBS_DATA_ENTRY_MENU)
                self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
                self.performButtonClick("LINK_TEXT", PatientObject.nbs_data_entry_patient_menu)
            else:
                self.performButtonClick("LINK_TEXT", 'New Search')
                self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
        else:
            try:
                empty_check = self.findElement("XPATH", "//a[contains(@href, 'uid=".__add__(str(patient_uid)) + "')]")
            except NoSuchElementException:
                empty_check = None
            if empty_check is not None:
                empty_check.click()
                self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
                self.performButtonClick("LINK_TEXT", NBSConstants.NBS_HOME_PAGE)
                self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
                self.performButtonClick("LINK_TEXT", NBSConstants.NBS_DATA_ENTRY_MENU)
                self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
                self.performButtonClick("LINK_TEXT", PatientObject.nbs_data_entry_patient_menu)
            else:
                self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
                self.performButtonClick("LINK_TEXT", 'New Search')
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
        return True

    def setAdministrativeSectionForExtended(self):
        try:
            # comments
            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT, NBSConstants.TYPE_CODE_NAME,
                                        PatientObject.add_patient_geneal_comments,
                                        "Comments:" + NBSUtilities.generate_random_string(100))
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
        except Exception as ex:
            self.logger.error(f"Exception while working Administration Section {str(ex)}")
            return False
        return True

    def setPatientNameSection(self, random_number):
        try:
            # Last Name
            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                        NBSConstants.TYPE_CODE_NAME,
                                        PatientObject.add_patient_last_name,
                                        NBSConstants.APPEND_TEST_FOR_LAST_NAME.__add__("-").__add__(str(random_number)))
            # First Name
            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                        NBSConstants.TYPE_CODE_NAME,
                                        PatientObject.add_patient_first_name,
                                        NBSConstants.APPEND_TEST_FOR_FIRST_NAME.__add__("-").__add__(
                                            str(random_number)))
            # Middle Name
            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                        NBSConstants.TYPE_CODE_NAME,
                                        PatientObject.add_patient_middle_name,
                                        NBSConstants.APPEND_TEST_FOR_MIDDLE_NAME.__add__("-").__add__(
                                            str(random_number)))
            # Patient Suffix
            self.setValueForHTMLElement(NBSConstants.HTML_SET_DROP_DOWN_RANDOM_VALUE,
                                        NBSConstants.TYPE_CODE_NAME,
                                        PatientObject.add_patient_name_suffix,
                                        random_number)
            # self.selectDropDownOptionValueRandom("DEM107")
        except Exception as exc:
            self.logger.exception(exc, exc_info=(type(exc), exc, exc.__traceback__))
            return False
        return True

    def setMergePatientNameSection(self, random_number):
        try:
            # Last Name
            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                        NBSConstants.TYPE_CODE_NAME,
                                        PatientObject.add_patient_last_name,
                                        NBSConstants.MERGE_LAST_NAME.__add__("-").__add__(str(random_number)))
            # First Name
            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                        NBSConstants.TYPE_CODE_NAME,
                                        PatientObject.add_patient_first_name,
                                        NBSConstants.MERGE_FIRST_NAME.__add__("-").__add__(
                                            str(random_number)))
            # Middle Name
            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                        NBSConstants.TYPE_CODE_NAME,
                                        PatientObject.add_patient_middle_name,
                                        NBSConstants.MERGE_MIDDLE_NAME.__add__("-").__add__(
                                            str(random_number)))
            # Patient Suffix
            self.setValueForHTMLElement(NBSConstants.HTML_SET_DROP_DOWN_RANDOM_VALUE,
                                        NBSConstants.TYPE_CODE_NAME,
                                        PatientObject.add_patient_name_suffix,
                                        random_number)
            # self.selectDropDownOptionValueRandom("DEM107")
        except Exception as exc:
            self.logger.exception(exc, exc_info=(type(exc), exc, exc.__traceback__))
            return False
        return True

    def setMergePersonalDetails(self):
        try:

            selected_birth_day, selected_birth_month, selected_birth_year = NBSUtilities.getRandomDateByVars()
            # address as of date
            self.selectCalendarDate(NBSConstants.TYPE_CODE_ID,
                                    PatientObject.add_patient_date_of_birth_icon,
                                    str(int(selected_birth_day)),
                                    selected_birth_month,
                                    str(int(selected_birth_year)))

            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
            # self.patient_dob = self.driver.find_element('id', "patientDOB").get_attribute("value")

            # current Sex

            self.setDropdownValueRandom(NBSConstants.TYPE_CODE_XPATH,
                                        PatientObject.add_patient_current_sex)

            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)

            # Birth Sex
            self.setDropdownValueRandom(NBSConstants.TYPE_CODE_XPATH,
                                        PatientObject.add_patient_birth_sex)

            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)

        except Exception as exc:
            self.logger.exception(exc, exc_info=(type(exc), exc, exc.__traceback__))
            return False
        return True

    def generate_dob(self):
        dob = NBSUtilities.generate_random_date
    


    def setPersonalDetails(self, random_number):
        try:
            
            
            selected_birth_day, selected_birth_month, selected_birth_year = NBSUtilities.getRandomDateByVars()
            # address as of date
            self.selectCalendarDate(NBSConstants.TYPE_CODE_ID,
                                    PatientObject.add_patient_date_of_birth_icon,
                                    str(int(selected_birth_day)),
                                    selected_birth_month,
                                    str(int(selected_birth_year)))

            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
            
            self.patient_dob = self.driver.find_element('id', "patientDOB").get_attribute("value")

            # current Sex

            self.setDropdownValueRandom(NBSConstants.TYPE_CODE_XPATH,
                                        PatientObject.add_patient_current_sex)

            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)

            # Birth Sex
            self.setDropdownValueRandom(NBSConstants.TYPE_CODE_XPATH,
                                        PatientObject.add_patient_birth_sex)

            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)

            # Patient Deceased
            self.setDropdownValueRandom(NBSConstants.TYPE_CODE_XPATH,
                                        PatientObject.is_patient_deceased)

            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)

            # if death occurred, then death date element is enable
            deceased_date = self.findElement(NBSConstants.TYPE_CODE_NAME,
                                             PatientObject.check_patient_deceased_enabled)
            if deceased_date.is_enabled():
                self.selectCalendarDate(NBSConstants.TYPE_CODE_ID,
                                        PatientObject.patient_deceased_date_icon)
                self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                NBSConstants.WAITING_TIME_IN_SECONDS)

            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
            # Patient Martial Status
            # self.setDropdownValueRandom(NBSConstants.TYPE_CODE_XPATH,PatientObject.PATIENT_MARTIAL_STATUS)

            self.setValueForHTMLElement(NBSConstants.HTML_SET_DROP_DOWN_RANDOM_VALUE,
                                        NBSConstants.TYPE_CODE_CSS,
                                        PatientObject.patient_martial_status,
                                        random_number)

            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
            # eHars

            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                        NBSConstants.TYPE_CODE_NAME,
                                        PatientObject.patient_hiv_case_id,
                                        "HIV".__add__("-").__add__(str(random_number)))
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
        except Exception as ex:
            self.logger.error("Exception While Populating Person Details Section", ex)
            return False
        return True

    def setPersonAddressDetails(self, random_number):
        try:
            # Address1
            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                        NBSConstants.TYPE_CODE_NAME,
                                        PatientObject.patient_address_street1,
                                        NBSConstants.APPEND_TEST_FOR_STREET_ADDR1.__add__(str(random_number)))
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
            # Address2
            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                        NBSConstants.TYPE_CODE_NAME,
                                        PatientObject.patient_address_street2,
                                        NBSConstants.APPEND_TEST_FOR_STREET_ADDR2.__add__(str(random_number)))
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
            # City
            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                        NBSConstants.TYPE_CODE_NAME,
                                        PatientObject.patient_address_city,
                                        NBSConstants.APPEND_TEST_FOR_STREET_CITY.__add__(str(random_number)))
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
            # State
            self.setValueForHTMLElement(NBSConstants.HTML_SET_DROP_DOWN_RANDOM_VALUE,
                                        NBSConstants.TYPE_CODE_XPATH,
                                        PatientObject.patient_address_state,
                                        random_number)
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
            # postal
            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                        NBSConstants.TYPE_CODE_NAME,
                                        PatientObject.patient_address_postal,
                                        NBSUtilities.generate_random_int(5))
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)

            # county
            self.setValueForHTMLElement(NBSConstants.HTML_SET_DROP_DOWN_RANDOM_VALUE,
                                        NBSConstants.TYPE_CODE_XPATH,
                                        PatientObject.patient_address_county,
                                        random_number)
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
            # Census
            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                        NBSConstants.TYPE_CODE_NAME,
                                        PatientObject.patient_address_census,
                                        NBSUtilities.generate_random_int(4))
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
            # country
            self.setValueForHTMLElement(NBSConstants.HTML_SET_DROP_DOWN_RANDOM_VALUE,
                                        NBSConstants.TYPE_CODE_XPATH,
                                        PatientObject.patient_address_country,
                                        random_number)
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
        except Exception as ex:
            self.logger.error("Exception while Populating Address Section", ex)
            return False
        return True

    def setMergePersonPhoneDetails(self):
        try:
            # Home Phone
            home_phone = (NBSUtilities.generate_random_int(3).__add__("-").__add__(NBSUtilities.generate_random_int(3))
                          .__add__("-").__add__(NBSUtilities.generate_random_int(4)))
            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                        NBSConstants.TYPE_CODE_NAME,
                                        PatientObject.patient_home_phone, home_phone)
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
            # Email
            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                        NBSConstants.TYPE_CODE_NAME,
                                        PatientObject.patient_email, NBSUtilities.generate_random_string(6)
                                        .__add__("@uuu.com"))
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
        except Exception as ex:
            self.logger.error("Error While Populating Phone Details", ex)
            return False
        return True



    def setPersonPhoneDetails(self):
        try:
            # Home Phone
            home_phone = (NBSUtilities.generate_random_int(3).__add__("-").__add__(NBSUtilities.generate_random_int(3))
                          .__add__("-").__add__(NBSUtilities.generate_random_int(4)))
            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                        NBSConstants.TYPE_CODE_NAME,
                                        PatientObject.patient_home_phone, home_phone)
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
            # Work Phone
            work_phone = (NBSUtilities.generate_random_int(3).__add__("-").__add__(NBSUtilities.generate_random_int(3))
                          .__add__("-").__add__(NBSUtilities.generate_random_int(4)))
            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                        NBSConstants.TYPE_CODE_NAME,
                                        PatientObject.patient_work_phone, work_phone)
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                        NBSConstants.TYPE_CODE_NAME,
                                        PatientObject.patient_work_phone_ext, NBSUtilities.generate_random_int(4))
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
            # Cell Phone
            cell_phone = (NBSUtilities.generate_random_int(3).__add__("-").__add__(NBSUtilities.generate_random_int(3))
                          .__add__("-").__add__(NBSUtilities.generate_random_int(4)))
            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                        NBSConstants.TYPE_CODE_NAME,
                                        PatientObject.patient_cell_phone, cell_phone)
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
            # Email
            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                        NBSConstants.TYPE_CODE_NAME,
                                        PatientObject.patient_email, NBSUtilities.generate_random_string(6)
                                        .__add__("@uuu.com"))
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
        except Exception as ex:
            self.logger.error("Error While Populating Phone Details", ex)
            return False
        return True

    def setPersonRaceDetails(self):
        try:

            # Ethnicity
            self.setDropdownValueRandom(NBSConstants.TYPE_CODE_XPATH,
                                        "//*[@id='DEM155']")

            # Alaskan Race
            self.performButtonClick(NBSConstants.TYPE_CODE_NAME, PatientObject.patient_indian_alaskan_race)
            # Asian Race
            self.performButtonClick(NBSConstants.TYPE_CODE_NAME, PatientObject.patient_asian_race)
            # African Race
            self.performButtonClick(NBSConstants.TYPE_CODE_NAME, PatientObject.patient_african_race)
            # Hawaiian Race
            self.performButtonClick(NBSConstants.TYPE_CODE_NAME, PatientObject.patient_hawaiian_race)
            # White Race
            self.performButtonClick(NBSConstants.TYPE_CODE_NAME, PatientObject.patient_white_race)
            # Other Race
            self.performButtonClick(NBSConstants.TYPE_CODE_NAME, PatientObject.patient_other_race)
            # Refused To Answer
            self.performButtonClick(NBSConstants.TYPE_CODE_NAME, PatientObject.patient_refused_to_answer)
            # Not Asked
            self.performButtonClick(NBSConstants.TYPE_CODE_NAME, PatientObject.patient_not_asked)
            # Unknown Race
            self.performButtonClick(NBSConstants.TYPE_CODE_NAME, PatientObject.patient_unknown_race)
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
        except Exception as ex:
            self.logger.error("Exception While Populating Race Section", ex)
            return False
        return True

    def setIdentificationRecordPopulate(self, random_number):
        # Type Code
        self.setValueForHTMLElement(NBSConstants.HTML_SET_DROP_DOWN_RANDOM_VALUE,
                                    NBSConstants.TYPE_CODE_XPATH,
                                    PatientObject.patient_identification_type,
                                    random_number)
        # Assigning Authority Code
        self.setValueForHTMLElement(NBSConstants.HTML_SET_DROP_DOWN_RANDOM_VALUE,
                                    NBSConstants.TYPE_CODE_XPATH,
                                    PatientObject.patient_assigning_auth,
                                    random_number)
        # ID Value
        element = self.findElement(NBSConstants.TYPE_CODE_XPATH, PatientObject.patient_identification_type)
        self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                    NBSConstants.TYPE_CODE_ID,
                                    PatientObject.patient_id_value,
                                    element.get_attribute("value").__add__("-").__add__(random_number))

    def populateNameSection(self, random_number):
        try:
            if self.checkDateIsExisted(NBSConstants.TYPE_CODE_ID, "NmAsOf"):
                name_birth_day, name_birth_month, name_birth_year = NBSUtilities.getRandomDateByVars()
                # As of Date
                self.selectCalendarDate(NBSConstants.TYPE_CODE_ID,
                                        PatientObject.patient_name_date_icon_extended,
                                        str(int(name_birth_day)),
                                        name_birth_month,
                                        str(int(name_birth_year)))
                self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                NBSConstants.WAITING_TIME_IN_SECONDS)
            # Name Type
            self.setValueForHTMLElement(NBSConstants.HTML_SET_DROP_DOWN_RANDOM_VALUE,
                                        NBSConstants.TYPE_CODE_ID,
                                        PatientObject.patient_name_type_extended,
                                        random_number)
            # Name Prefix
            self.setValueForHTMLElement(NBSConstants.HTML_SET_DROP_DOWN_RANDOM_VALUE,
                                        NBSConstants.TYPE_CODE_ID,
                                        PatientObject.patient_name_prefix_extended,
                                        random_number)
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
            # Name Last
            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                        NBSConstants.TYPE_CODE_ID,
                                        PatientObject.patient_last_name_extended,
                                        NBSConstants.APPEND_TEST_FOR_LAST_NAME.__add__("-").__add__(random_number))
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
            # Name Last Second
            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                        NBSConstants.TYPE_CODE_ID,
                                        PatientObject.patient_second_last_name_extended,
                                        NBSConstants.APPEND_TEST_FOR_SECOND_LAST_NAME.__add__("-").__add__(
                                            random_number))
            # Name First
            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                        NBSConstants.TYPE_CODE_ID,
                                        PatientObject.patient_first_name_extended,
                                        NBSConstants.APPEND_TEST_FOR_FIRST_NAME.__add__("-").__add__(random_number))
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
            # Name Middle
            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                        NBSConstants.TYPE_CODE_ID,
                                        PatientObject.patient_middle_name_extended,
                                        NBSConstants.APPEND_TEST_FOR_MIDDLE_NAME.__add__("-").__add__(random_number))
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
            # Name Middle second
            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                        NBSConstants.TYPE_CODE_ID,
                                        PatientObject.patient_middle_name_second_extended,
                                        NBSConstants.APPEND_TEST_FOR_MIDDLE_NAME_SECOND.__add__("-")
                                        .__add__(random_number))
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
            # Name Suffix
            self.setValueForHTMLElement(NBSConstants.HTML_SET_DROP_DOWN_RANDOM_VALUE,
                                        NBSConstants.TYPE_CODE_ID,
                                        PatientObject.patient_name_suffix_extended,
                                        random_number)
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
            # Name Degree
            self.setValueForHTMLElement(NBSConstants.HTML_SET_DROP_DOWN_RANDOM_VALUE,
                                        NBSConstants.TYPE_CODE_ID,
                                        PatientObject.patient_name_degree_extended,
                                        random_number)
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)

        except Exception as ex:
            self.logger.info("Exception While populating Name Section", ex)
            return False
        return True

    def populateAddressSection(self, random_number):
        try:
            if self.checkDateIsExisted(NBSConstants.TYPE_CODE_ID, "AddrAsOf"):
                addr_birth_day, addr_birth_month, addr_birth_year = NBSUtilities.getRandomDateByVars()
                # address as of date
                self.selectCalendarDate(NBSConstants.TYPE_CODE_ID,
                                        PatientObject.patient_addr_date_icon_extended,
                                        str(int(addr_birth_day)),
                                        addr_birth_month,
                                        str(int(addr_birth_year)))
                self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                NBSConstants.WAITING_TIME_IN_SECONDS)
            # address type
            self.setValueForHTMLElement(NBSConstants.HTML_SET_DROP_DOWN_RANDOM_VALUE,
                                        NBSConstants.TYPE_CODE_ID,
                                        PatientObject.patient_addr_type_extended,
                                        random_number)
            # address use
            self.setValueForHTMLElement(NBSConstants.HTML_SET_DROP_DOWN_RANDOM_VALUE,
                                        NBSConstants.TYPE_CODE_ID,
                                        PatientObject.patient_addr_use_extended,
                                        random_number)
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
            # street address1
            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                        NBSConstants.TYPE_CODE_ID,
                                        PatientObject.patient_addr_street1_extended,
                                        NBSConstants.APPEND_TEST_FOR_STREET_ADDR1.__add__("-").__add__(
                                            random_number))
            # street address2
            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                        NBSConstants.TYPE_CODE_ID,
                                        PatientObject.patient_addr_street2_extended,
                                        NBSConstants.APPEND_TEST_FOR_STREET_ADDR2.__add__("-").__add__(
                                            random_number))
            # city
            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                        NBSConstants.TYPE_CODE_ID,
                                        PatientObject.patient_addr_city_extended,
                                        NBSConstants.APPEND_TEST_FOR_STREET_CITY.__add__("-").__add__(
                                            random_number))
            # state
            self.setValueForHTMLElement(NBSConstants.HTML_SET_DROP_DOWN_RANDOM_VALUE,
                                        NBSConstants.TYPE_CODE_ID,
                                        PatientObject.patient_addr_state_extended,
                                        random_number)
            # postal
            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                        NBSConstants.TYPE_CODE_ID,
                                        PatientObject.patient_addr_postal_extended,
                                        NBSUtilities.generate_random_int(5))
            # address county
            self.setValueForHTMLElement(NBSConstants.HTML_SET_DROP_DOWN_RANDOM_VALUE,
                                        NBSConstants.TYPE_CODE_ID,
                                        PatientObject.patient_addr_county_extended,
                                        random_number)

            # Census
            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                        NBSConstants.TYPE_CODE_ID,
                                        PatientObject.patient_addr_census_extended,
                                        NBSUtilities.generate_random_int(4))
            # address country
            self.setValueForHTMLElement(NBSConstants.HTML_SET_DROP_DOWN_RANDOM_VALUE,
                                        NBSConstants.TYPE_CODE_ID,
                                        PatientObject.patient_addr_cntry_extended,
                                        random_number)

            # address comments
            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                        NBSConstants.TYPE_CODE_ID,
                                        PatientObject.patient_addr_comments_extended,
                                        "Address Comments-".__add__(NBSUtilities.generate_random_string(100)))
        except Exception as ex:
            self.logger.info("Exception while populating Address Section", ex)
            return False
        return True

    def populateTelephoneSection(self):
        try:
            # tele_birth_day, tele_birth_month, tele_birth_year = NBSUtilities.getRandomDateByVars()
            # phone as of date
            self.selectCalendarDate(NBSConstants.TYPE_CODE_ID,
                                    PatientObject.patient_phone_date_icon_extended)
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
            # phone type
            self.setValueForHTMLElement(NBSConstants.HTML_SET_DROP_DOWN_RANDOM_VALUE,
                                        NBSConstants.TYPE_CODE_ID,
                                        PatientObject.patient_phone_type_extended,
                                        None)
            # phone use
            self.setValueForHTMLElement(NBSConstants.HTML_SET_DROP_DOWN_RANDOM_VALUE,
                                        NBSConstants.TYPE_CODE_ID,
                                        PatientObject.patient_phone_use_extended,
                                        None)
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)

            # phone county code
            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                        NBSConstants.TYPE_CODE_ID,
                                        PatientObject.patient_phone_cntry_code_extended,
                                        NBSUtilities.generate_random_int(3))

            # phone number
            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                        NBSConstants.TYPE_CODE_ID,
                                        PatientObject.patient_phone_number_extended,
                                        "".__add__(str(NBSUtilities.generate_random_int(3))).__add__("-")
                                        .__add__(str(NBSUtilities.generate_random_int(3))).__add__("-")
                                        .__add__(str(NBSUtilities.generate_random_int(4))))
            # phone extension code
            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                        NBSConstants.TYPE_CODE_ID,
                                        PatientObject.patient_phone_ext_extended,
                                        NBSUtilities.generate_random_int(4))
            # email
            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                        NBSConstants.TYPE_CODE_ID,
                                        PatientObject.patient_phone_email_extended,
                                        NBSUtilities.generate_random_string(10).__add__("@ccd.com"))
            # url
            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                        NBSConstants.TYPE_CODE_ID,
                                        PatientObject.patient_phone_url_extended,
                                        "www.".__add__(NBSUtilities.generate_random_string(10).__add__(".com")))
            # phone comments
            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                        NBSConstants.TYPE_CODE_ID,
                                        PatientObject.patient_phone_comments_extended,
                                        "Phone Comments-".__add__(NBSUtilities.generate_random_string(60)))
        except Exception as ex:
            self.logger.info("Exception While Populating Telephone Section", ex)
            return False
        return True

    def populateIdentificationDetails(self, random_number):
        try:
            # ident_birth_day, ident_birth_month, ident_birth_year = NBSUtilities.getRandomDateByVars()
            # Identification as of date
            self.selectCalendarDate(NBSConstants.TYPE_CODE_ID,
                                    PatientObject.patient_ident_date_icon_extended)
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
            # ID type
            self.setValueForHTMLElement(NBSConstants.HTML_SET_DROP_DOWN_RANDOM_VALUE,
                                        NBSConstants.TYPE_CODE_ID,
                                        PatientObject.patient_ident_type_extended,
                                        None)
            # ID Assign
            self.setValueForHTMLElement(NBSConstants.HTML_SET_DROP_DOWN_RANDOM_VALUE,
                                        NBSConstants.TYPE_CODE_ID,
                                        PatientObject.patient_ident_assign_type_extended,
                                        None)
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
            # ID Value
            element = self.findElement(NBSConstants.TYPE_CODE_ID, PatientObject.patient_ident_type_extended)
            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                        NBSConstants.TYPE_CODE_ID,
                                        PatientObject.patient_ident_id_value_extended,
                                        element.get_attribute("value").__add__("-").__add__(random_number))
        except Exception as ex:
            self.logger.info("Exception While Populating identification section", ex)
            return False
        return True

    def populateRaceSection(self):
        try:
            list_not = ["M", "NASK", "U", "PHC1175", "2131-1"]
            # race_birth_day, race_birth_month, race_birth_year = NBSUtilities.getRandomDateByVars()
            # race as of date
            self.selectCalendarDate(NBSConstants.TYPE_CODE_ID,
                                    PatientObject.patient_race_date_icon_extended)
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
            # race type
            self.setValueForHTMLElement(NBSConstants.HTML_SET_DROP_DOWN_RANDOM_VALUE,
                                        NBSConstants.TYPE_CODE_ID,
                                        PatientObject.patient_race_type_extended,
                                        None)
            element = self.findElement(NBSConstants.TYPE_CODE_ID,
                                       PatientObject.patient_race_type_extended)

            while True:
                element_value = element.get_attribute("value")
                if PatientObject.race_type is None or PatientObject.race_type != element_value:
                    PatientObject.race_type = element_value
                    if element_value not in list_not:
                        # select detailed race Multiple Values
                        self.setValueForHTMLElement(NBSConstants.HTML_SET_DROP_DOWN_RANDOM_VALUE,
                                                    NBSConstants.TYPE_CODE_ID,
                                                    PatientObject.patient_race_id_detailed_extended,
                                                    None)
                        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                        NBSConstants.WAITING_TIME_IN_SECONDS)
                    break
                else:
                    self.setValueForHTMLElement(NBSConstants.HTML_SET_DROP_DOWN_RANDOM_VALUE,
                                                NBSConstants.TYPE_CODE_ID,
                                                PatientObject.patient_race_type_extended,
                                                None)
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
        except Exception as ex:
            self.logger.info("Exception while populating race section", ex)
            pass
        return True

    def populateEthnicitySection(self):
        try:
            # As of Date

            self.selectCalendarDate(NBSConstants.TYPE_CODE_ID,
                                    "AsOfDateEthIcon")
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
            # Ethnicity Type
            self.setValueForHTMLElement(NBSConstants.HTML_SET_DROP_DOWN_RANDOM_VALUE,
                                        NBSConstants.TYPE_CODE_NAME,
                                        PatientObject.patient_ethnicity_extended,
                                        None)
            e_element = self.findElement(NBSConstants.TYPE_CODE_NAME,
                                         PatientObject.patient_ethnicity_extended)
            e_value = e_element.get_attribute("value")
            if e_value == "UNK":
                e_element = self.findElement(NBSConstants.TYPE_CODE_NAME,
                                             PatientObject.patient_ethnicity_reason_extended)
                if e_element.is_enabled():
                    self.setValueForHTMLElement(NBSConstants.HTML_SET_DROP_DOWN_RANDOM_VALUE,
                                                NBSConstants.TYPE_CODE_NAME,
                                                PatientObject.patient_ethnicity_reason_extended,
                                                None)
                    self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                    NBSConstants.WAITING_TIME_IN_SECONDS)
            if e_value == "2135-2":
                s_element = self.findElement(NBSConstants.TYPE_CODE_ID,
                                             PatientObject.patient_ethnicity_orgin_extended)
                if s_element.is_displayed() and s_element.is_enabled():
                    self.setDropdownValueRandom(NBSConstants.TYPE_CODE_XPATH,
                                                "//*[@id='SpanOrigin']")
                    self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                    NBSConstants.WAITING_TIME_IN_SECONDS)
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
        except Exception as ex:
            self.logger.info(ex)
            pass
        return True

    def populateSexAndBirthSection(self, random_number):
        try:
            # As of Date

            self.selectCalendarDate(NBSConstants.TYPE_CODE_ID,
                                    "AsofDateSBIcon")

            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)

            # Date Of Birth
            if self.checkDateIsExisted(NBSConstants.TYPE_CODE_ID, "patientDOB"):
                selected_birth_day, selected_birth_month, selected_birth_year = NBSUtilities.getRandomDateByVars()
                self.selectCalendarDate(NBSConstants.TYPE_CODE_ID,
                                        PatientObject.patient_dob_date_icon_extended,
                                        str(int(selected_birth_day)),
                                        selected_birth_month,
                                        str(int(selected_birth_year)))
            # current
            self.setDropdownValueRandom(NBSConstants.TYPE_CODE_XPATH,
                                        "//*[@id='CurrSex']")

            sab_element = self.findElement(NBSConstants.TYPE_CODE_ID,
                                           PatientObject.patient_sab_current_sex_extended)
            sab_value = sab_element.get_attribute("value")
            if sab_value == "UNK":
                self.setDropdownValueRandom(NBSConstants.TYPE_CODE_XPATH,
                                            "//*[@id='UnknownSpecify']")

                self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                NBSConstants.WAITING_TIME_IN_SECONDS)
            # trans gender Information
            self.setValueForHTMLElement(NBSConstants.HTML_SET_DROP_DOWN_RANDOM_VALUE,
                                        NBSConstants.TYPE_CODE_ID,
                                        PatientObject.patient_sab_tg_info_extended,
                                        None)
            # additional gender
            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                        NBSConstants.TYPE_CODE_ID,
                                        PatientObject.patient_sab_additional_gender_extended,
                                        NBSUtilities.generate_random_string(15))
            # birth sex
            self.setValueForHTMLElement(NBSConstants.HTML_SET_DROP_DOWN_RANDOM_VALUE,
                                        NBSConstants.TYPE_CODE_ID,
                                        PatientObject.patient_sab_birth_sex_extended,
                                        None)
            # multiple births
            self.setValueForHTMLElement(NBSConstants.HTML_SET_DROP_DOWN_RANDOM_VALUE,
                                        NBSConstants.TYPE_CODE_ID,
                                        PatientObject.patient_sab_multiple_birth_extended,
                                        None)
            # birth order
            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                        NBSConstants.TYPE_CODE_ID,
                                        PatientObject.patient_sab_birth_order_extended,
                                        NBSUtilities.generate_random_int(1))
            # birth city
            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                        NBSConstants.TYPE_CODE_ID,
                                        PatientObject.patient_sab_birth_city_extended,
                                        NBSConstants.APPEND_TEST_FOR_STREET_CITY
                                        .__add__("-").__add__(str(random_number)))
            # birth state
            self.setValueForHTMLElement(NBSConstants.HTML_SET_DROP_DOWN_RANDOM_VALUE,
                                        NBSConstants.TYPE_CODE_ID,
                                        PatientObject.patient_sab_birth_state_extended,
                                        None)
            # birth county
            self.setValueForHTMLElement(NBSConstants.HTML_SET_DROP_DOWN_RANDOM_VALUE,
                                        NBSConstants.TYPE_CODE_ID,
                                        PatientObject.patient_sab_birth_county_extended,
                                        None)
            # birth country
            self.setValueForHTMLElement(NBSConstants.HTML_SET_DROP_DOWN_RANDOM_VALUE,
                                        NBSConstants.TYPE_CODE_ID,
                                        PatientObject.patient_sab_birth_country_extended,
                                        None)
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
        except Exception as ex:
            self.logger.debug("Exception While Populating Birth and Sex Section", ex)
            pass
        return True

    def populateMortalitySection(self, random_number):
        try:
            # Date As Of

            self.selectCalendarDate(NBSConstants.TYPE_CODE_ID,
                                    "AsofDateMorbIcon")
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
            # is patient deceased
            self.setValueForHTMLElement(NBSConstants.HTML_SET_DROP_DOWN_RANDOM_VALUE,
                                        NBSConstants.TYPE_CODE_NAME,
                                        PatientObject.patient_mortality_deceased_extended,
                                        None)
            deceased_element = self.findElement(NBSConstants.TYPE_CODE_NAME,
                                                PatientObject.patient_mortality_deceased_extended)
            deceased_value = deceased_element.get_attribute("value")
            if deceased_value == "Y":
                # deceased_birth_day, deceased_birth_month, deceased_birth_year = NBSUtilities.getRandomDateByVars()
                # deceased date
                self.selectCalendarDate(NBSConstants.TYPE_CODE_ID,
                                        PatientObject.patient_mortality_dod_extended)
                self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                NBSConstants.WAITING_TIME_IN_SECONDS)
                # deceased city
                self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                            NBSConstants.TYPE_CODE_ID,
                                            PatientObject.patient_mortality_death_city_extended,
                                            NBSConstants.APPEND_TEST_FOR_STREET_CITY
                                            .__add__("-").__add__(str(random_number)))
                # deceased state
                self.setValueForHTMLElement(NBSConstants.HTML_SET_DROP_DOWN_RANDOM_VALUE,
                                            NBSConstants.TYPE_CODE_ID,
                                            PatientObject.patient_mortality_death_state_extended,
                                            None)
                # deceased county
                self.setValueForHTMLElement(NBSConstants.HTML_SET_DROP_DOWN_RANDOM_VALUE,
                                            NBSConstants.TYPE_CODE_ID,
                                            PatientObject.patient_mortality_death_county_extended,
                                            None)
                # deceased country
                self.setValueForHTMLElement(NBSConstants.HTML_SET_DROP_DOWN_RANDOM_VALUE,
                                            NBSConstants.TYPE_CODE_ID,
                                            PatientObject.patient_mortality_death_country_extended,
                                            None)
                self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                NBSConstants.WAITING_TIME_IN_SECONDS)
                self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                NBSConstants.WAITING_TIME_IN_SECONDS)

        except Exception as ex:
            self.logger.info(ex)
            pass
        return True

    def populateGeneralInfoSection(self, random_number):
        try:
            # As Of Date
            self.selectCalendarDate(NBSConstants.TYPE_CODE_ID,
                                    "AsOfDateGenInfoIcon")
            # martial status
            self.setValueForHTMLElement(NBSConstants.HTML_SET_DROP_DOWN_RANDOM_VALUE,
                                        NBSConstants.TYPE_CODE_ID,
                                        PatientObject.patient_gi_martial_status_extended,
                                        None)
            # made-in name
            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                        NBSConstants.TYPE_CODE_ID,
                                        PatientObject.patient_gi_maid_in_name_extended,
                                        "MADE".__add__("-").__add__(str(random_number)))
            # no of adults
            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                        NBSConstants.TYPE_CODE_ID,
                                        PatientObject.patient_gi_number_of_adults_extended,
                                        NBSUtilities.generate_random_int(1))
            # no of children
            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                        NBSConstants.TYPE_CODE_ID,
                                        PatientObject.patient_gi_number_of_children_extended,
                                        NBSUtilities.generate_random_int(1))
            # primary occupation
            self.setValueForHTMLElement(NBSConstants.HTML_SET_DROP_DOWN_RANDOM_VALUE,
                                        NBSConstants.TYPE_CODE_ID,
                                        PatientObject.patient_gi_primary_occupation_extended,
                                        None)
            # Education Level
            self.setValueForHTMLElement(NBSConstants.HTML_SET_DROP_DOWN_RANDOM_VALUE,
                                        NBSConstants.TYPE_CODE_ID,
                                        PatientObject.patient_gi_education_level_extended,
                                        None)
            # Primary Language
            self.setValueForHTMLElement(NBSConstants.HTML_SET_DROP_DOWN_RANDOM_VALUE,
                                        NBSConstants.TYPE_CODE_ID,
                                        PatientObject.patient_gi_primary_language_extended,
                                        None)
            # English Speaking
            self.setValueForHTMLElement(NBSConstants.HTML_SET_DROP_DOWN_RANDOM_VALUE,
                                        NBSConstants.TYPE_CODE_ID,
                                        PatientObject.patient_gi_speak_english_extended,
                                        None)
            # eHars code
            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                        NBSConstants.TYPE_CODE_ID,
                                        PatientObject.patient_gi_hiv_code_extended,
                                        "HIV-".__add__(NBSUtilities.generate_random_int(10)))
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            2)

        except Exception as ex:
            self.logger.info(ex)
            pass
        return True

    @staticmethod
    def get_element_id_by_section_number(section_type, action_type, extended_screen):
        get_path = ""
        section_id = ""
        section_number = ""
        if not extended_screen and section_type == PatientObject.identification_section:
            if action_type == PatientObject.view_action_type:
                get_path = "//*[@id='viewIdSubSection1']"
            elif action_type == PatientObject.edit_action_type:
                get_path = "//*[@id='editIdSubSection1']"
            elif action_type == PatientObject.delete_action_type:
                get_path = "//*[@id='deleteIdSubSection2']"
        else:
            if section_type == PatientObject.name_section:
                section_id = "patternnameTableRow"
                section_number = "1"
            elif section_type == PatientObject.address_section:
                section_id = "patternaddrTableRow"
                section_number = "2"
            elif section_type == PatientObject.tele_phone_section:
                section_id = "patternphoneTableRow"
                section_number = "3"
            elif section_type == PatientObject.identification_section:
                section_id = "patternidenTableRow"
                section_number = "4"
            elif section_type == PatientObject.race_section:
                section_id = "patternraceTableRow"
                section_number = "5"

            if action_type == PatientObject.view_action_type:
                get_path = "//*[@id='" + section_id.__add__(section_number) + "']/td/table/tbody/tr/td[1]/input"
            elif action_type == PatientObject.edit_action_type:
                get_path = "//*[@id='" + section_id.__add__(section_number) + "']/td/table/tbody/tr/td[2]/input"
            elif action_type == PatientObject.delete_action_type:
                get_path = "//*[@id='" + section_id.__add__(
                    str(int(section_number) + 1)) + "']/td/table/tbody/tr/td[3]/input"
        return get_path

    @staticmethod
    def getPathForEditSection(section_type, extended_screen=False):
        updated_path = ""
        if not extended_screen and section_type == PatientObject.identification_section:
            updated_path = PatientObject.update_identification_record_to_section
        else:
            if section_type == PatientObject.name_section:
                updated_path = PatientObject.name_update_record_to_section_extended
            elif section_type == PatientObject.address_section:
                updated_path = PatientObject.addr_update_record_to_section_extended
            elif section_type == PatientObject.tele_phone_section:
                updated_path = PatientObject.phone_update_record_to_section_extended
            elif section_type == PatientObject.identification_section:
                updated_path = PatientObject.ident_update_record_to_section_extended
            elif section_type == PatientObject.race_section:
                updated_path = PatientObject.race_update_record_to_section_extended
        return updated_path

    def performRefineSearchValidation(self, randon_number_add_provider, patient_uid):
        person_locator_values = {}
        ident_dict = {}
        tele_phone_dict = {}
        # email_dict = {}
        person_race_list = []

        try:
            person_locator_values = DBUtilities().getProviderCityStatePostal(patient_uid)
            ident_dict = DBUtilities().getProviderIdentificationRecord(patient_uid)
            tele_phone_dict = DBUtilities().getProviderContactTelephone(patient_uid)
            person_race_list = DBUtilities().getPersonSelectedRaces(patient_uid)
            self.logger.info(person_locator_values.get("eth-ind"))
        except Exception as ex:
            self.logger.debug("Exception While Getting values from Database", ex)

        try:
            # set Last NAme

            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT, NBSConstants.TYPE_CODE_NAME,
                                        PatientObject.patient_search_by_last_name,
                                        NBSConstants.APPEND_TEST_FOR_LAST_NAME.__add__("-")
                                        .__add__(str(randon_number_add_provider)))
            # set First NAme

            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT, NBSConstants.TYPE_CODE_NAME,
                                        PatientObject.patient_search_by_first_name,
                                        NBSConstants.APPEND_TEST_FOR_FIRST_NAME.__add__("-")
                                        .__add__(str(randon_number_add_provider)))
            # set Date of Birth

            if person_locator_values.get("dob"):
                date_of_birth = person_locator_values.get("dob")
                self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT, NBSConstants.TYPE_CODE_ID,
                                            PatientObject.patient_search_by_dob1,
                                            date_of_birth.strftime("%m"))
                self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT, NBSConstants.TYPE_CODE_ID,
                                            PatientObject.patient_search_by_dob2,
                                            date_of_birth.strftime("%d"))
                self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT, NBSConstants.TYPE_CODE_ID,
                                            PatientObject.patient_search_by_dob3,
                                            date_of_birth.strftime("%Y"))
            # sex code
            self.setValueForHTMLElement(NBSConstants.HTML_DROP_DOWN_ELEMENT, NBSConstants.TYPE_CODE_NAME,
                                        PatientObject.patient_search_by_gender,
                                        person_locator_values.get("gender"))
            # Street Address

            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT, NBSConstants.TYPE_CODE_NAME,
                                        PatientObject.patient_search_by_straddr1,
                                        person_locator_values.get("address"))
            # City
            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT, NBSConstants.TYPE_CODE_NAME,
                                        PatientObject.patient_search_by_city,
                                        person_locator_values.get("city"))
            # State
            self.setValueForHTMLElement(NBSConstants.HTML_DROP_DOWN_ELEMENT, NBSConstants.TYPE_CODE_NAME,
                                        PatientObject.patient_search_by_state,
                                        person_locator_values.get("state"))
            # Postal

            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT, NBSConstants.TYPE_CODE_NAME,
                                        PatientObject.patient_search_by_postal,
                                        person_locator_values.get("postal"))
            # Patient ID

            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT, NBSConstants.TYPE_CODE_NAME,
                                        PatientObject.patient_search_by_patient_id,
                                        PatientObject.getPatienetIdFromSystem(patient_uid))

            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, 2)

            # id type
            self.setValueForHTMLElement(NBSConstants.HTML_DROP_DOWN_ELEMENT, NBSConstants.TYPE_CODE_NAME,
                                        PatientObject.patient_search_by_type_code,
                                        ident_dict.get("identi_select"))
            # id value
            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT, NBSConstants.TYPE_CODE_NAME,
                                        PatientObject.patient_search_by_type_code_value,
                                        ident_dict.get("identi_value"))

            # phone number
            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT, NBSConstants.TYPE_CODE_NAME,
                                        PatientObject.patient_search_by_phone_number,
                                        tele_phone_dict.get("telephone"))
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, 2)
            # email validation is failing

            """self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT, NBSConstants.TYPE_CODE_NAME,
                                        PatientObject.PATIENT_SEARCH_BY_EMAIL,
                                        email_dict.get("email"))"""

            # Ethnicity
            try:
                if person_locator_values.get("eth-ind") is not None:
                    self.setValueForHTMLElement(NBSConstants.HTML_DROP_DOWN_ELEMENT,
                                                NBSConstants.TYPE_CODE_ID,
                                                "DEM155",
                                                str(person_locator_values.get("eth-ind")))
                    # self.setDropdownValue(NBSConstants.TYPE_CODE_XPATH,"//*[@id='DEM155']",
                    #                      str(person_locator_values.get("eth-ind")))

                    self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, 2)
            except:
                self.logger.debug("Exception While Setting Values to Ethnicity")

            # Race
            self.setValueForHTMLElement(NBSConstants.HTML_DROP_DOWN_ELEMENT, NBSConstants.TYPE_CODE_NAME,
                                        PatientObject.patient_search_by_race_ind,
                                        person_race_list[0][0])
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, 2)
            self.driver.save_screenshot(
                os.path.abspath(os.curdir) + "\\screenshots\\" + "patient_refine_search.png")
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, 2)
            self.performButtonClick(NBSConstants.TYPE_CODE_NAME,
                                    PatientObject.create_new_patient_submit_button)
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            2)
            self.performButtonClick("LINK_TEXT", 'Refine Search')
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
        except Exception as ex:
            self.logger.debug("Exception While Setting Values For Refine Search", ex)
            pass
        return True

    @staticmethod
    def getPatienetIdFromSystem(patient_uid):
        # PSN2157685649GA01
        patient_id = None
        local_id = DBUtilities().getSingleColumnValueByTable("local_id", "Person",
                                                             "person_uid", patient_uid)
        suffix_code = DBUtilities().getSingleColumnValueByTable("config_value", "NBS_configuration",
                                                                "config_key", "UID_SUFFIX_CODE")
        seed_code = DBUtilities().getSingleColumnValueByTable("config_value", "NBS_configuration",
                                                              "config_key", "SEED_VALUE")
        if local_id is not None:
            patient_id = int(local_id[3:(len(local_id) - len(suffix_code))]) - int(seed_code)
        print("PATIENT::::::%d", patient_id)
        return patient_id

    def validatePaginationOnResults(self):
        self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT, NBSConstants.TYPE_CODE_NAME,
                                    PatientObject.patient_search_by_last_name, "%")
        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                        NBSConstants.WAITING_TIME_IN_SECONDS)
        self.performButtonClick(NBSConstants.TYPE_CODE_NAME,
                                PatientObject.create_new_patient_submit_button)
        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                        NBSConstants.WAITING_TIME_IN_SECONDS)
        # next
        while True:
            try:
                self.findElement(NBSConstants.TYPE_CODE_LINK_TEXT, "Next").click()
            except NoSuchElementException:
                break
        while True:
            try:
                self.findElement(NBSConstants.TYPE_CODE_LINK_TEXT, "Previous").click()
            except NoSuchElementException:
                break
        self.performButtonClick("LINK_TEXT", 'New Search')
        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                        NBSConstants.WAITING_TIME_IN_SECONDS)
        return True

    def find_patient(self):
        try:
            patient_elm_xpath = '//*[@id="parent"]/tbody/tr['.__add__(str(NBSConstants.EDIT_OPTION)) + ']/td[5]/a'
            patient = self.findElement(NBSConstants.TYPE_CODE_XPATH, patient_elm_xpath)
            patient.click()
            time.sleep(2)
        except Exception as e:
            return False
        return True

    def get_event_handle(self):
        self.findElement(NBSConstants.TYPE_CODE_ID, 'tabs0head1').click()
        return True

    def click_addNew_vaccinations(self):
        add_new_btn_xpath = '//*[@id="subsect_Vaccination"]/table/tbody/tr/td['.__add__(
            str(NBSConstants.ADD_NEW_OPTION)) + ']/input'
        self.findElement(NBSConstants.TYPE_CODE_XPATH, add_new_btn_xpath).click()
        return True

    def click_vaccination_tab(self):
        self.findElement(NBSConstants.TYPE_CODE_ID, 'tabs0head1').click()

    def checkDateIsExisted(self, type_code, path):
        element_date = self.findElement(type_code, path)
        date_value = element_date.get_attribute("value")
        # self.logger.info("date_value:::")
        # self.logger.info(date_value)
        # if date value is not empty
        if date_value is not None:
            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                        type_code, path, datetime.today().strftime('%m/%d/%Y'))
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)

        return True
