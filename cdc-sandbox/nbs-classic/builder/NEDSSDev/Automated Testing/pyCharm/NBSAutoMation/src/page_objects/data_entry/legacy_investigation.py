from selenium.webdriver import Keys
from selenium.webdriver.common.by import By
from selenium.webdriver.support.select import Select
from src.page_objects.data_entry.organization import Organization
from src.page_objects.data_entry.patient import PatientObject
from src.page_objects.BasePageObject import BasePageObject
from src.page_objects.data_entry.provider import ProviderObject
from src.page_objects.data_entry.vaccination import Vaccination
from src.utilities.constants import NBSConstants
from src.utilities.db_utilities import DBUtilities
from src.utilities.log_config import LogUtils
from datetime import datetime, timedelta
import string
import random
import time

from src.utilities.nbs_utilities import NBSUtilities


class Legacyinvestigation:
    def __init__(self, driver):
        self.driver = driver
        self.organization = Organization(self.driver)
        self.patient = PatientObject(self.driver)
        self.vaccination = Vaccination(self.driver)
        self.provider = ProviderObject(self.driver)
        self.var_illness_onset_dt = "02/10/2024"

    logger = LogUtils.loggen(__name__)
    db_utilities = DBUtilities()
    vaccination_search_submit_id = 'Submit'
    random_number = datetime.now().strftime("%d%m%y%H%M%S")
    ynu_random = "Yes"
    ynu_no = "No"
    # legacy investigation summary details
    legacy_jurisdiction = "proxy.publicHealthCaseVO_s.thePublicHealthCaseDT.jurisdictionCd"
    legacy_state_case_id = "proxy.publicHealthCaseVO_s.actIdDT_s[0].rootExtensionTxt"
    legacy_inv_start_date = "proxy.publicHealthCaseVO_s.thePublicHealthCaseDT.activityFromTime_s"
    legacy_investigation_status = "proxy.publicHealthCaseVO_s.thePublicHealthCaseDT.investigationStatusCd"
    legacy_investigator_search = "investigator.personUidButton"
    legacy_date_assigned_to_investigator = "dateAssignedToInvestigation"
    legacy_date_of_report = "proxy.publicHealthCaseVO_s.thePublicHealthCaseDT.rptFormCmpltTime_s"
    legacy_reporting_source = "proxy.publicHealthCaseVO_s.thePublicHealthCaseDT.rptSourceCd"
    legacy_county_date = "proxy.publicHealthCaseVO_s.thePublicHealthCaseDT.rptToCountyTime_s"
    legacy_state_date = "proxy.publicHealthCaseVO_s.thePublicHealthCaseDT.rptToStateTime_s"
    legacy_search_provider = "investigator.personUidButton"
    legacy_search_organization = "reportingOrg.organizationUIDButton"
    legacy_provider_lastname = "providerSearch.lastName"
    legacy_organization_lastname = '%'
    legacy_organization_search_button = "organizationSearch.nmTxt"
    legacy_reporter_quick_code_button = "// *[ @ id = 'entity-table-reporter.personUid'] / thead / tr / td[2] / input[2]"
    legacy_physician_quick_code_button = "// *[ @ id = 'entity-table-physician.personUid'] / thead / tr / td[2] / input[2]"
    legacy_reporter_quick_code_lookup = "//*[@id='entity-table-reporter.personUid']/thead/tr/td[2]/input[1]"
    legacy_physician_quick_code_lookup = "// *[ @ id = 'entity-table-physician.personUid'] / thead / tr / td[2] / input[1]"
    legacy_was_patient_hospitalized_option = "proxy.observationVO_s[0].obsValueCodedDT_s[0].code"
    legacy_was_patient_hospitalized_ind = "proxy.observationVO_s[0].obsValueCodedDT_s[0].code_textbox"
    legacy_search_hospital = "hospitalOrg.organizationUIDButton"
    legacy_admin_cal_date = "proxy.observationVO_s[10].obsValueDateDT_s[0].fromTime_s"
    legacy_discharge_date = "proxy.observationVO_s[11].obsValueDateDT_s[0].fromTime_s"
    legacy_diagnosis_date = "proxy.publicHealthCaseVO_s.thePublicHealthCaseDT.diagnosisTime_s"
    legacy_illness_on_set_date = "proxy.publicHealthCaseVO_s.thePublicHealthCaseDT.effectiveFromTime_s"
    legacy_illness_end_date = "proxy.publicHealthCaseVO_s.thePublicHealthCaseDT.effectiveToTime_s"
    legacy_illness_duration_date = "proxy.publicHealthCaseVO_s.thePublicHealthCaseDT.effectiveDurationAmt"
    legacy_age_at_onset = "proxy.publicHealthCaseVO_s.thePublicHealthCaseDT.patAgeAtOnset"
    legacy_is_patient_pregnant = "proxy.observationVO_s[1].obsValueCodedDT_s[0].code"
    legacy_have_pelvic_inflammatory = "proxy.observationVO_s[2].obsValueCodedDT_s[0].code"
    legacy_patient_die_from_illness = "proxy.publicHealthCaseVO_s.thePublicHealthCaseDT.outcomeCd"
    legacy_patient_associated_with_daycare = "proxy.observationVO_s[4].obsValueCodedDT_s[0].code_textbox"
    legacy_patient_associated_with_daycare_select = 'proxy.observationVO_s[4].obsValueCodedDT_s[0].code'
    legacy_patient_food_handler = "proxy.observationVO_s[5].obsValueCodedDT_s[0].code"
    legacy_part_of_an_outbreak = "proxy.publicHealthCaseVO_s.thePublicHealthCaseDT.outbreakInd"
    legacy_outbreak_name = "proxy.publicHealthCaseVO_s.thePublicHealthCaseDT.outbreakName"
    legacy_disease_acquired = "proxy.publicHealthCaseVO_s.thePublicHealthCaseDT.diseaseImportedCd"
    legacy_transmission_mode = "proxy.publicHealthCaseVO_s.thePublicHealthCaseDT.transmissionModeCd"
    legacy_detection_mode = "proxy.publicHealthCaseVO_s.thePublicHealthCaseDT.detectionMethodCd"
    legacy_confirmation_method = "confirmationMethods"
    legacy_confirmation_date = "confirmationDate"
    legacy_case_status = "proxy.publicHealthCaseVO_s.thePublicHealthCaseDT.caseClassCd"
    legacy_mmwr_week = "proxy.publicHealthCaseVO_s.thePublicHealthCaseDT.mmwrWeek"
    legacy_mmwr_year = "proxy.publicHealthCaseVO_s.thePublicHealthCaseDT.mmwrYear"
    legacy_general_comments = "proxy.publicHealthCaseVO_s.thePublicHealthCaseDT.txt"
    legacy_transfer_owner_jurisdiction = "transOwn"
    legacy_provider_last_name_id = "providerSearch.lastName"
    legacy_current_sex = "DEM113"
    legacy_comments = "NTF137"
    legacy_out_of_system_recipient = "exportFacility"
    legacy_out_of_system_jurisdiction_value = "999999"
    legacy_hospitalization_out_of_country = "OOC"
    legacy_hospitalization_out_of_state = "OOS"
    legacy_hospitalization_out_of_jurisdiction = "OOJ"
    legacy_imported_country = "proxy.observationVO_s[6].obsValueCodedDT_s[0].code"
    legacy_imported_state = "proxy.observationVO_s[7].obsValueCodedDT_s[0].code"
    legacy_imported_city = "proxy.observationVO_s[8].obsValueTxtDT_s[0].valueTxt"
    legacy_imported_county = "proxy.observationVO_s[9].obsValueCodedDT_s[0].code"
    condition = "African Tick's Bite Fever"
    
    EVENT_INFO_TAB="tabs0head1"
    SEARCH_RESULTS='//*[@id="searchResultsTable"]/tbody/tr/td[1]/a'
    INVESTIGATION_TAB = "//*[@id='eventSumaryInv']/tbody/tr/td[2]/a"
    MANAGE_ASSOCIATION_LIST='//*[@id="{}"]/tbody/tr/td[1]/div/input'
    LABLIST="lablist"
    VACCINATION_LIST="vaccilist"

    @staticmethod
    def generate_random_string(s_length):
        letters = string.ascii_lowercase
        # Randomly choose characters from letters for the given length of the string
        random_string = ''.join(random.choice(letters) for i in range(s_length))
        return random_string

    def generate_random_int(s_length):
        letters = string.digits
        # Randomly choose characters from letters for the given length of the string
        random_int = ''.join(random.choice(letters) for i in range(s_length))
        return random_int

    def switch_to_current_window(self):
        current_window = self.driver.window_handles[-1]
        self.driver.switch_to.window(current_window)
        return True

    def switch_to_original_window(self):
        self.driver.switch_to.window(self.driver.window_handles[0])
        return True

    def set_investigation_summary(self):
        self.set_state_case_id(self.random_number)
        self.set_investigation_status()
        self.setTodayDate(By.ID, self.legacy_inv_start_date)
        self.click_search_provider(By.ID, self.legacy_search_provider)
        self.switch_to_current_window()
        self.set_provider()
        self.setTodayDate(By.ID, self.legacy_date_assigned_to_investigator)
        self.setTodayDate(By.ID, self.legacy_date_of_report)
        self.set_reporting_source_type()
        self.click_search_organization(By.ID, self.legacy_search_organization)
        self.switch_to_current_window()
        self.set_organization()
        self.setTodayDate(By.ID, self.legacy_county_date)
        self.setTodayDate(By.ID, self.legacy_state_date)
        self.set_reporter_quick_code_lookup()
        self.set_physician_quick_code_lookup()
        self.set_hospital()
        self.set_condition_details()
        self.set_epidemiologic_details()
        self.driver.find_element(By.ID, "Submit").click()

    def set_provider(self):
        self.setProviderLastName()
        self.driver.find_element(By.ID, "Submit").click()
        self.driver.find_element(By.LINK_TEXT, 'Select').click()
        self.switch_to_original_window()
        return True

    def setProviderLastName(self):
        self.driver.find_element(By.ID, self.legacy_provider_last_name_id).clear()
        self.driver.find_element(By.ID, self.legacy_provider_last_name_id).send_keys("%")

    def set_organization(self):
        self.setOrgLastName()
        self.driver.find_element(By.ID, "Submit").click()
        self.driver.find_element(By.LINK_TEXT, 'Select').click()
        self.switch_to_original_window()
        return True

    def setOrgLastName(self):
        self.driver.find_element(By.ID, self.legacy_organization_search_button).clear()
        self.driver.find_element(By.ID, self.legacy_organization_search_button).send_keys("%")

    def set_jurisdiction(self):
        # proxy.publicHealthCaseVO_s.thePublicHealthCaseDT.jurisdictionCd_ac_table
        element = self.driver.find_element(By.ID, self.legacy_jurisdiction)
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        if element.tag_name.lower() == 'select':
            select_element = Select(self.driver.find_element(By.ID, self.legacy_jurisdiction))
            random_index = random.randint(2, len(select_element.options))
            select_jurisdiction_xpath = (
                    f"//*[@id='{self.legacy_jurisdiction}']/option[".__add__(
                        str(random_index)) + "]")
            jurisdiction_value = (self.driver.find_element(By.XPATH, select_jurisdiction_xpath).get_attribute("value"))
            select_element.select_by_value(jurisdiction_value)

    def set_jurisdiction_edit(self):
        # proxy.publicHealthCaseVO_s.thePublicHealthCaseDT.jurisdictionCd_ac_table
        element = self.driver.find_element(By.ID, self.legacy_transfer_owner_jurisdiction)
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        select_element = Select(self.driver.find_element(By.ID, self.legacy_transfer_owner_jurisdiction))
        random_index = random.randint(2, len(select_element.options))
        select_jurisdiction_xpath = (
                f"//*[@id='{self.legacy_transfer_owner_jurisdiction}']/option[".__add__(
                    str(random_index)) + "]")
        jurisdiction_value = (self.driver.find_element(By.XPATH, select_jurisdiction_xpath).get_attribute("value"))

        select_element.select_by_value(jurisdiction_value)  # Out of System = 999999

        if jurisdiction_value == self.legacy_out_of_system_jurisdiction_value:
            element = self.driver.find_element(By.ID, self.legacy_out_of_system_recipient)
            self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
            select_element = Select(element)
            random_index = random.randint(2, len(select_element.options))
            select_recipient_xpath = (
                    f"//*[@id='{self.legacy_out_of_system_recipient}']/option[".__add__(
                        str(random_index)) + "]")
            value = self.driver.find_element(By.XPATH, select_recipient_xpath).get_attribute("value")
            select_element.select_by_value(value)
            self.set_general_comments(self.legacy_comments)

    def set_disease_acquired(self):
        element = self.driver.find_element(By.ID, self.legacy_disease_acquired)
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        select_element = Select(self.driver.find_element(By.ID, self.legacy_disease_acquired))
        random_index = random.randint(2, len(select_element.options))
        hospitalization_option_xpath = (
                f"//*[@id='{self.legacy_disease_acquired}']/option[".__add__(
                    str(random_index)) + "]")
        hospitalization_option_value = (
            self.driver.find_element(By.XPATH, hospitalization_option_xpath).get_attribute("value"))
        # hospitalization_option_value = "OOC"
        select_element.select_by_value(hospitalization_option_value)

        disease_acquired = [self.legacy_hospitalization_out_of_country, self.legacy_hospitalization_out_of_state,
                            self.legacy_hospitalization_out_of_jurisdiction]

        if hospitalization_option_value in disease_acquired:
            element = self.driver.find_element(By.ID, self.legacy_imported_country)
            self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
            select_element = Select(element)
            random_index = random.randint(2, len(select_element.options))
            select_imported_country_xpath = (
                    f"//*[@id='{self.legacy_imported_country}']/option[".__add__(
                        str(random_index)) + "]")
            print("XPATH", select_imported_country_xpath)
            value = self.driver.find_element(By.XPATH, select_imported_country_xpath).get_attribute("value")
            select_element.select_by_value(value)

            element = self.driver.find_element(By.ID, self.legacy_imported_state)
            self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
            select_element = Select(element)
            random_index = random.randint(2, len(select_element.options))
            select_imported_state_xpath = (
                    f"//*[@id='{self.legacy_imported_state}']/option[".__add__(
                        str(random_index)) + "]")
            value = self.driver.find_element(By.XPATH, select_imported_state_xpath).get_attribute("value")
            select_element.select_by_value(value)

            self.driver.find_element(By.ID, self.legacy_imported_city).send_keys(
                "city" + "-" + self.generate_random_string(5))

            element = self.driver.find_element(By.ID, self.legacy_imported_county)
            self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
            select_element = Select(element)
            random_index = random.randint(2, len(select_element.options))
            select_imported_county_xpath = (
                    f"//*[@id='{self.legacy_imported_county}']/option[".__add__(
                        str(random_index)) + "]")
            value = self.driver.find_element(By.XPATH, select_imported_county_xpath).get_attribute("value")
            select_element.select_by_value(value)

    def set_state_case_id(self, random_number):
        self.driver.find_element(By.ID, self.legacy_state_case_id).clear()
        self.driver.find_element(By.ID, self.legacy_state_case_id).send_keys("stateID", random_number)

    def set_investigation_status(self):
        # // *[ @ id = "INV109"] / option[1]
        element = self.driver.find_element(By.ID, self.legacy_investigation_status)
        print(f"Element type  {element.tag_name}captured for id {self.legacy_investigation_status}")
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        select_element = Select(self.driver.find_element(By.ID, self.legacy_investigation_status))
        random_index = random.randint(2, len(select_element.options))
        select_jurisdiction_xpath = (
                f"//*[@id='{self.legacy_investigation_status}']/option[".__add__(
                    str(random_index)) + "]")
        jurisdiction_value = (self.driver.find_element(By.XPATH, select_jurisdiction_xpath).get_attribute("value"))
        select_element.select_by_value(jurisdiction_value)

    def setTodayDate(self, type, type_val):
        now = datetime.now()
        today = now.strftime("%m-%d-%Y")
        element = self.driver.find_element(type, type_val)
        element.clear()
        element.send_keys(today)

    def click_search_provider(self, elm_type, elm_type_code):
        self.driver.find_element(elm_type, elm_type_code).click()
        time.sleep(1)

    def click_search_organization(self, elm_type, elm_type_code):
        self.driver.find_element(elm_type, elm_type_code).click()
        time.sleep(1)

    def set_reporting_source_type(self):
        element = self.driver.find_element(By.ID, self.legacy_reporting_source)
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        select_element = Select(self.driver.find_element(By.ID, self.legacy_reporting_source))
        random_index = random.randint(2, len(select_element.options))
        reporting_source_type_xpath = (
                f"//*[@id='{self.legacy_reporting_source}']/option[".__add__(
                    str(random_index)) + "]")
        reporting_source_value = (
            self.driver.find_element(By.XPATH, reporting_source_type_xpath).get_attribute("value"))
        select_element.select_by_value(reporting_source_value)

    def set_calender_date(self, elm_type, elm_type_code):
        day, month, year = NBSUtilities.getRandomDateByVars()
        # set calendar date
        NBSUtilities.selectCalendarDate(elm_type, elm_type_code, str(int(day)), month, str(int(year)), self.driver)
        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)

    @staticmethod
    def setWaitTimeAfterEachAction(debug_on, wait_time):
        if debug_on == "YES":
            time.sleep(wait_time)
        else:
            time.sleep(0)

    def set_reporter_quick_code_lookup(self):
        # //*[@id="entity-table-reporter.personUid"]/thead/tr/td[2]/input[1]
        self.driver.find_element(By.XPATH, self.legacy_reporter_quick_code_lookup).clear()
        self.driver.find_element(By.XPATH, self.legacy_reporter_quick_code_lookup).send_keys("2")
        # // *[ @ id = "entity-table-reporter.personUid"] / thead / tr / td[2] / input[2]
        self.driver.find_element(By.XPATH, self.legacy_reporter_quick_code_button).click()
        time.sleep(1)

    def set_physician_quick_code_lookup(self):
        self.driver.find_element(By.XPATH, self.legacy_physician_quick_code_lookup).clear()
        self.driver.find_element(By.XPATH, self.legacy_physician_quick_code_lookup).send_keys("1")
        # // *[ @ id = "entity-table-reporter.personUid"] / thead / tr / td[2] / input[2]
        self.driver.find_element(By.XPATH, self.legacy_physician_quick_code_button).click()
        time.sleep(1)

    def set_hospital(self):
        self.set_hospitalization_option()
        time.sleep(1)
        self.set_hospital_data()

    def set_hospitalization_option(self):
        #  //*[@id="proxy.observationVO_s[0].obsValueCodedDT_s[0].code_ac_table"]/tbody/tr[1]/td/input
        element = self.driver.find_element(By.ID, self.legacy_was_patient_hospitalized_option)
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        select_element = Select(self.driver.find_element(By.ID, self.legacy_was_patient_hospitalized_option))
        random_index = random.randint(2, len(select_element.options))
        hospitalization_option_xpath = (
                f"//*[@id='{self.legacy_was_patient_hospitalized_option}']/option[".__add__(
                    str(random_index)) + "]")
        hospitalization_option_value = (
            self.driver.find_element(By.XPATH, hospitalization_option_xpath).get_attribute("value"))
        hospitalization_option_value = "Y"
        select_element.select_by_value(hospitalization_option_value)

        if hospitalization_option_value == "Y":
            now = datetime.now()
            earlier = now - timedelta(days=3)
            adm_date = earlier.strftime("%m-%d-%Y")
            self.set_hsp_adm_date(By.ID, self.legacy_admin_cal_date, adm_date)
            self.set_discharge_date()

    def set_hospital_data(self):
        was_patient_hospitalized = self.driver.find_element(By.NAME,
                                                            self.legacy_was_patient_hospitalized_ind).get_attribute(
            "value")
        if was_patient_hospitalized == 'Yes':
            # set the value
            self.driver.find_element(By.ID, self.legacy_search_hospital).click()
            self.switch_to_current_window()
            time.sleep(1)
            self.set_organization()
            self.switch_to_original_window()
            time.sleep(1)
            # self.set_calender_date(By.ID, self.legacy_admin_cal_date)
            # time.sleep(1)
            # self.set_discharge_date()
        time.sleep(1)

    def set_hsp_adm_date(self, type_code, type_val, new_hsp_adm):
        element = self.driver.find_element(type_code, type_val)
        element.clear()
        element.send_keys(new_hsp_adm)

    def set_discharge_date(self):
        self.set_hsp_adm_date(By.NAME, self.legacy_discharge_date, datetime.now().strftime("%m-%d-%Y"))

    def set_condition_details(self):

        # diagnosis date
        self.driver.find_element(By.ID, self.legacy_diagnosis_date).clear()
        self.driver.find_element(By.ID, self.legacy_diagnosis_date).send_keys(datetime.now().strftime("%m/%d/%Y"))
        # illness onset date
        self.driver.find_element(By.ID, self.legacy_illness_on_set_date).clear()
        self.driver.find_element(By.ID, self.legacy_illness_on_set_date).send_keys(self.var_illness_onset_dt)
        # illness end date
        self.driver.find_element(By.ID, self.legacy_illness_end_date).clear()
        self.driver.find_element(By.ID, self.legacy_illness_end_date).send_keys(datetime.now().strftime("%m/%d/%Y"))
        # illness duration
        # age at onset

        # is patient pregnant
        sab_element = self.driver.find_element(By.ID, self.legacy_current_sex)
        # Get currSex field to determine male or female for selecting the is pregnant option
        sab_value = sab_element.get_attribute("value")
        element = self.driver.find_element(By.NAME, self.legacy_is_patient_pregnant)
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        if sab_value == "F":
            Select(element).select_by_value("Y")
        else:
            Select(element).select_by_value("N")

        # have pelvic inflammatory disease
        element = self.driver.find_element(By.ID, self.legacy_have_pelvic_inflammatory)
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        Select(element).select_by_value("Y")

        # Did the patient die from this illness
        element = self.driver.find_element(By.ID, self.legacy_patient_die_from_illness)
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        Select(element).select_by_value("Y")

    def set_epidemiologic_details(self):
        # patient associated with daycare
        element = self.driver.find_element(By.ID, self.legacy_patient_associated_with_daycare_select)
        # print("Element type",element.tag_name)
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        Select(element).select_by_value("Y")

        element = self.driver.find_element(By.ID, self.legacy_patient_food_handler)
        # print("Element type",element.tag_name)
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        Select(element).select_by_value("Y")

        # case part of an outbreak
        element = self.driver.find_element(By.ID, self.legacy_part_of_an_outbreak)
        # print("Element type",element.tag_name)
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        Select(element).select_by_value("Y")

        #  disease acquired
        self.set_disease_acquired()
        self.set_transmission_mode()
        self.set_detection_mode()
        self.set_confirmation_methods()
        # self.set_confirmation_date()
        # diagnosis date
        self.driver.find_element(By.ID, self.legacy_confirmation_date).clear()
        self.driver.find_element(By.ID, self.legacy_confirmation_date).send_keys(datetime.now().strftime("%m/%d/%Y"))
        self.set_case_status()
        # self.set_mmwr_week()
        # self.set_mmwr_year()
        self.set_general_comments(self.legacy_general_comments)

    def set_outbreak_name(self):
        element = self.driver.find_element(By.NAME, self.legacy_outbreak_name)
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        select_element = Select(self.driver.find_element(By.NAME, self.legacy_outbreak_name))
        random_index = random.randint(2, len(select_element.options))
        hospitalization_option_xpath = (
                f"//*[@id='{self.legacy_outbreak_name}']/option[".__add__(
                    str(random_index)) + "]")
        hospitalization_option_value = (
            self.driver.find_element(By.XPATH, hospitalization_option_xpath).get_attribute("value"))
        select_element.select_by_value(hospitalization_option_value)

    def set_transmission_mode(self):
        element = self.driver.find_element(By.ID, self.legacy_transmission_mode)
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        select_element = Select(self.driver.find_element(By.ID, self.legacy_transmission_mode))
        random_index = random.randint(2, len(select_element.options))
        hospitalization_option_xpath = (
                f"//*[@id='{self.legacy_transmission_mode}']/option[".__add__(
                    str(random_index)) + "]")
        hospitalization_option_value = (
            self.driver.find_element(By.XPATH, hospitalization_option_xpath).get_attribute("value"))
        select_element.select_by_value(hospitalization_option_value)

    def set_detection_mode(self):
        element = self.driver.find_element(By.ID, self.legacy_detection_mode)
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        select_element = Select(self.driver.find_element(By.ID, self.legacy_detection_mode))
        random_index = random.randint(2, len(select_element.options))
        hospitalization_option_xpath = (
                f"//*[@id='{self.legacy_detection_mode}']/option[".__add__(
                    str(random_index)) + "]")
        hospitalization_option_value = (
            self.driver.find_element(By.XPATH, hospitalization_option_xpath).get_attribute("value"))
        select_element.select_by_value(hospitalization_option_value)

    def set_confirmation_methods(self):
        element = self.driver.find_element(By.ID, self.legacy_confirmation_method)
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        select_element = Select(self.driver.find_element(By.ID, self.legacy_confirmation_method))
        random_index = random.randint(2, len(select_element.options))
        hospitalization_option_xpath = (
                f"//*[@id='{self.legacy_confirmation_method}']/option[".__add__(
                    str(random_index)) + "]")
        hospitalization_option_value = (
            self.driver.find_element(By.XPATH, hospitalization_option_xpath).get_attribute("value"))
        select_element.select_by_value(hospitalization_option_value)

    def set_case_status(self):
        element = self.driver.find_element(By.ID, self.legacy_case_status)
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        select_element = Select(self.driver.find_element(By.ID, self.legacy_case_status))
        random_index = random.randint(2, len(select_element.options))
        hospitalization_option_xpath = (
                f"//*[@id='{self.legacy_case_status}']/option[".__add__(
                    str(random_index)) + "]")
        hospitalization_option_value = (
            self.driver.find_element(By.XPATH, hospitalization_option_xpath).get_attribute("value"))
        select_element.select_by_value(hospitalization_option_value)

    # def set_mmwr_week(self):
    #     self.driver.find_element(By.ID, self.legacy_mmwr_week).clear()
    #     self.driver.find_element(By.ID, self.legacy_mmwr_week).send_keys("GeneralComments", self.generate_random_int(10))

    def set_mmwr_year(self):
        today = datetime.date.today()
        year = today.year
        self.driver.find_element(By.ID, self.legacy_mmwr_year).clear()
        self.driver.find_element(By.ID, self.legacy_mmwr_year).send_keys(year)

    def set_general_comments(self, id):
        self.driver.find_element(By.ID, id).clear()
        self.driver.find_element(By.ID, id).send_keys("GeneralComments", self.generate_random_string(100))
