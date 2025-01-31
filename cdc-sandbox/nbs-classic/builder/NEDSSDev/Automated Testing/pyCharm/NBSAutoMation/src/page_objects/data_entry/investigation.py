from datetime import datetime

import string
import random
import time

from selenium.webdriver.common.by import By
from selenium.webdriver.support.select import Select
from src.page_objects.data_entry.labreport import LabReportObject
from selenium.webdriver.support.wait import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC

from src.page_objects.BasePageObject import BasePageObject
from src.page_objects.data_entry.labreport import LabReportObject
from src.page_objects.data_entry.patient import PatientObject
from src.page_objects.data_entry.provider import ProviderObject
from src.page_objects.data_entry.vaccination import Vaccination
from src.utilities.constants import NBSConstants
from src.utilities.investigation_locators import InvestigationLocators
from src.utilities.log_config import LogUtils
from src.utilities.nbs_utilities import NBSUtilities
from src.page_objects.data_entry.organization import Organization


class Investigation(LabReportObject):
    def __init__(self, driver):
        super().__init__(driver)
        self.organization = Organization(self.driver)
        self.patient = PatientObject(self.driver)
        self.vaccination = Vaccination(self.driver)
        self.provider = ProviderObject(self.driver)
        self.lab_report = LabReportObject(self.driver)

    logger = LogUtils.loggen(__name__)
    random_number = datetime.now().strftime("%d%m%y%H%M%S")
    GENERIC_V2_CONDITION = "Anthrax"
    INV_SUBMIT_ID = 'Submit'
    TYPE_CODE_ID = "ID"
    CASE_INFO_TAB="tabs0head1"
    CLICK_SEARCH_PROVIDER="INV180Icon"
    INVESTIGATION_ADD_PROVIDER_SUBMIT="/ html / body / div[2] / input[1]"
    ADDPROVIDER_NAME_PREFIX="NPP006"
    ADDPROVIDER_NAME_SUFFIX="NPP010"
    ADDPROVIDER_NAME_DEGREE="NPP060"
    ADD_PROVIDER_STATE="NPP021"
    PROVIDER_ADDRESS_COUNTRY="NPP023"
    SET_JURISDICTION = "INV107"
    SET_INVESTIGATION_STATUS = "INV109"
    SET_REPORTING_COUNTY="NOT113"
    SET_CLINICAL_INFO="IconINV182"
    STATE_CASE_ID="INV173"
    LEGACY_ID="INV200"
    SHARE_COMMENTS_SUBMIT='//*[@id="topButtId"]/input[1]'
    SHARE_COMMENTS_ID="shareComments"
    ADD_VACCINATION_XPATH="//*[@id='events3']/tbody/tr/td/div/input[2]"
    ADD_LAB_REPORT_XPATH="//*[@id='events1']/tbody/tr/td/div/input"
    LABREPORT_DATE="NBS_LAB197Icon"
    PUBLIC_HEALTH_DATE="NBS_LAB201Icon"
    PREGANANCY_STATUS="INV178"
   
    SEARCH_RESULTS='//*[@id="searchResultsTable"]/tbody/tr/td[1]/a'
    INVESTIGATION_TAB = "//*[@id='eventSumaryInv']/tbody/tr/td[2]/a"
    TRANSFER_OWNERSHIP="//input[@title='Transfer Ownership']"
    CREATE_NOTIFICATION="//input[@title='Create Notification']"
    NOTE_ID='//*[@id="topcreatenotId"]/input[1]'
    DELETE="//input[@id='delete']"
   

    def search_patientBy_lastName(self, last_name):
        self.driver.find_element(By.NAME, "personSearch.lastName").clear()
        self.driver.find_element(By.NAME, "personSearch.lastName").send_keys(last_name)
        time.sleep(1)
        self.driver.find_element(By.NAME, "Submit").click()

    # def click_addNew_investigation(self):
    #     add_new_btn_xpath = '//*[@id="subsect_Inv"]/table/tbody/tr/td['.__add__(
    #         str(NBSConstants.ADD_NEW_OPTION)) + ']/input[2]'
    #     self.findElement(NBSConstants.TYPE_CODE_XPATH, add_new_btn_xpath).click()
    #     return True

    # ccd_textbox
    def select_pg_condition(self):
        self.driver.find_element(By.NAME, "ccd_textbox").clear()
        self.driver.find_element(By.NAME, "ccd_textbox").send_keys(self.GENERIC_V2_CONDITION)
        self.driver.find_element(By.NAME, self.INV_SUBMIT_ID).click()
        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_TWO_SECONDS)

    def click_case_info_tab(self):
        # tabs0head1
        self.driver.find_element(By.ID, self.CASE_INFO_TAB).click()
        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_TWO_SECONDS)

    def switch_to_current_window(self):
        current_window = self.driver.window_handles[-1]
        self.driver.switch_to.window(current_window)
        return True

    def switch_to_original_window(self):
        self.driver.switch_to.window(self.driver.window_handles[0])
        return True

    def set_investigation_details(self):
        self.set_jurisdiction()
        self.set_investigation_status()
        self.set_state_case_id(self.random_number)
        self.set_legacy_case_id(self.random_number)
        self.click_search_provider(By.ID, self.CLICK_SEARCH_PROVIDER)
        self.switch_to_current_window()
        self.vaccination.search_and_select_provider()
        self.switch_to_original_window()
        time.sleep(2)
        self.setTodayDate(By.ID, 'INV110')

    def set_reporting_information(self):
        # Date of report
        self.setTodayDate(By.ID, InvestigationLocators.REPORT_DATE)
        #  Earliest Date Reported to County
        self.setTodayDate(By.ID, InvestigationLocators.REPORTED_DATE_TO_COUNTY)
        #  Earliest Date Reported to State
        self.setTodayDate(By.ID, InvestigationLocators.REPORTED_DATE_TO_STATE)
        self.set_reporting_source_type()
        self.click_search_org()
        self.vaccination.switch_to_current_window()
        time.sleep(1)
        # seach and add organization
        self.vaccination.search_view_organization()
        time.sleep(1)
        self.vaccination.switch_to_current_window()
        self.click_search_provider(By.ID, self.CLICK_SEARCH_PROVIDER)
        self.vaccination.switch_to_current_window()
        self.vaccination.search_and_select_provider()
        self.vaccination.switch_to_original_window()
        self.set_reporting_county()
        time.sleep(2)

    def set_hospital(self):
        self.set_hospitalization_option()
        time.sleep(3)
        self.set_hospital_data()

    def set_condition_details(self):
        self.setTodayDate(By.ID, InvestigationLocators.DIAGNOSIS_DATE)
        self.setTodayDate(By.ID, InvestigationLocators.ILLNESS_ONSET_DATE)
        self.setTodayDate(By.ID, InvestigationLocators.ILLNESS_END_DATE)
        # self.set_illness_duration_units()
        # self.set_age_at_onset()
        # self.set_age_at_onset_units()
        self.set_is_pregnant()
        self.set_is_patient_alive()
        # self.set_date_of_death()

        pass

    def investigation_add_provider(self):
        self.set_provider_name(self.random_number)
        time.sleep(1)
        self.set_provider_address(self.random_number)
        time.sleep(1)
        self.set_provider_contact_info()
        time.sleep(1)
        # submit
        self.driver.find_element(By.XPATH, self.INVESTIGATION_ADD_PROVIDER_SUBMIT).click()
        time.sleep(1)
        return True

    def set_provider_name(self, random_number):
        self.setQuickCodeForProvider()
        self.setAddProviderNamePrefix()
        self.setAddProviderLastName(NBSConstants.ADD_PROVIDER_LAST_NAME
                                    .__add__("-").__add__(str(random_number)))
        self.setAddProviderFirstName(NBSConstants.ADD_PROVIDER_FIRST_NAME
                                     .__add__("-").__add__(str(random_number)))
        self.setAddProviderMiddleName(NBSConstants.ADD_PROVIDER_MIDDLE_NAME
                                      .__add__("-").__add__(str(random_number)))
        self.setAddProviderNameSuffix()
        self.setAddProviderDegreeName()
        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)

    def set_provider_address(self, random_number):
        self.setProviderAddressStreet1(
            random_number.__add__("-").__add__(str(ProviderObject.generate_random_int(3))))
        self.setProviderAddressStreet2(
            random_number.__add__("-").__add__(str(ProviderObject.generate_random_int(3))))
        self.setProviderAddressCity()
        self.setProviderAddressState()
        self.setProviderAddressZip()
        self.setProviderAddressCounty()

    def setQuickCodeForProvider(self):
        self.driver.find_element(By.NAME, "quickCode").clear()
        self.driver.find_element(By.NAME, "quickCode").send_keys(self.random_number[:10])

    # NPP006
    def setAddProviderNamePrefix(self):
        element = self.driver.find_element(By.ID, self.ADDPROVIDER_NAME_PREFIX)
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        select_element = Select(self.driver.find_element(By.ID,
                                                         self.ADDPROVIDER_NAME_PREFIX))
        size = len(select_element.options) - 1
        select_element.select_by_index(random.randint(1, size))

    def setAddProviderLastName(self, last_name):
        self.driver.find_element(By.NAME, "lastName").clear()
        self.driver.find_element(By.NAME, "lastName").send_keys(last_name)

    def setAddProviderFirstName(self, first_name):
        self.driver.find_element(By.NAME, "firstName").clear()
        self.driver.find_element(By.NAME, "firstName").send_keys(first_name)

    def setAddProviderMiddleName(self, middle_name):
        self.driver.find_element(By.NAME, "middleName").clear()
        self.driver.find_element(By.NAME, "middleName").send_keys(middle_name)

    def setAddProviderNameSuffix(self):
        element = self.driver.find_element(By.ID, self.ADDPROVIDER_NAME_SUFFIX)
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        select_element = Select(self.driver.find_element(By.ID,
                                                         self.ADDPROVIDER_NAME_SUFFIX))
        size = len(select_element.options)
        select_element.select_by_index(random.randint(1, size - 1))

    def setAddProviderDegreeName(self):
        element = self.driver.find_element(By.ID, self.ADDPROVIDER_NAME_DEGREE)
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        select_element = Select(self.driver.find_element(By.ID,
                                                         self.ADDPROVIDER_NAME_DEGREE))
        size = len(select_element.options) - 1
        select_element.select_by_index(random.randint(1, size))

    def setProviderAddressStreet1(self, random_number):
        self.driver.find_element(By.NAME, "streetAddress1").clear()
        self.driver.find_element(By.NAME, "streetAddress1"
                                 ).send_keys("address1-" + random_number)

    def setProviderAddressStreet2(self, random_number):
        self.driver.find_element(By.NAME, "streetAddress2").clear()
        self.driver.find_element(By.NAME, "streetAddress2"
                                 ).send_keys("address2-" + random_number)

    def setProviderAddressCity(self):
        self.driver.find_element(By.NAME, "city").clear()
        self.driver.find_element(By.NAME, "city"
                                 ).send_keys(
            NBSConstants.ADD_PROVIDER_CITY_NAME + "-" + ProviderObject.generate_random_string(5))

    def setProviderAddressState(self):
        element = self.driver.find_element(By.ID, self.ADD_PROVIDER_STATE)
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        select_element = Select(self.driver.find_element(By.ID,
                                                         self.ADD_PROVIDER_STATE))
        random_index = random.randint(2, len(select_element.options))
        add_provider_addr_state_xpath = (
                "//*[@id='NPP021']/option["
                .__add__(str(random_index)) + "]")
        addr_state_value = (self.driver.find_element(By.XPATH, add_provider_addr_state_xpath)
                            .get_attribute("value"))
        select_element.select_by_value(addr_state_value)

    def setProviderAddressZip(self):
        self.driver.find_element(By.NAME, "zip").clear()
        self.driver.find_element(By.NAME, "zip"
                                 ).send_keys(random.randint(11111, 99999))

    def setProviderAddressCounty(self):
        element = self.driver.find_element(By.ID, self.PROVIDER_ADDRESS_COUNTRY)
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        select_element = Select(self.driver.find_element(By.ID,
                                                          self.PROVIDER_ADDRESS_COUNTRY))
        size = len(select_element.options) - 1
        select_element.select_by_index(random.randint(1, size))

    def set_provider_contact_info(self):
        # telephone
        self.setTelephoneValue()
        self.vaccination.setOrgTextBoxValue("ext", NBSUtilities.generate_random_int(4))
        self.vaccination.setOrgTextBoxValue("EMail", NBSUtilities.generate_random_string(8).__add__("@omg.com"))

    def set_jurisdiction(self):
        # //*[@id="INV107"]/option[1]
        element = self.driver.find_element(By.ID, self.SET_JURISDICTION)
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        select_element = Select(self.driver.find_element(By.ID, self.SET_JURISDICTION))
        random_index = random.randint(2, len(select_element.options))
        select_jurisdiction_xpath = ("//*[@id=self.SET_JURISDICTION]/option[".__add__(str(random_index)) + "]")
        jurisdiction_value = (self.driver.find_element(By.XPATH, select_jurisdiction_xpath).get_attribute("value"))
        select_element.select_by_value(jurisdiction_value)

    def set_investigation_status(self):
        # // *[ @ id = "INV109"] / option[1]
        element = self.driver.find_element(By.ID, self.SET_INVESTIGATION_STATUS)
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        select_element = Select(self.driver.find_element(By.ID, self.SET_INVESTIGATION_STATUS))
        random_index = random.randint(2, len(select_element.options))
        select_jurisdiction_xpath = ("//*[@id='INV109']/option[".__add__(str(random_index)) + "]")
        jurisdiction_value = (self.driver.find_element(By.XPATH, select_jurisdiction_xpath).get_attribute("value"))
        select_element.select_by_value(jurisdiction_value)

    def set_state_case_id(self, random_number):
        self.driver.find_element(By.ID, self.STATE_CASE_ID).clear()
        self.driver.find_element(By.ID, self.STATE_CASE_ID).send_keys("stateID", random_number)

    def set_legacy_case_id(self, random_number):
        self.driver.find_element(By.ID, self.LEGACY_ID).clear()
        self.driver.find_element(By.ID, self.LEGACY_ID).send_keys("legacyID", random_number)

    def setTodayDate(self, type, type_val):
        now = datetime.now()
        today = now.strftime("%m-%d-%Y")
        element = self.driver.find_element(type, type_val)
        element.clear()
        element.send_keys(today)

    def set_calender_date(self, elm_type, elm_type_code):
        day, month, year = NBSUtilities.getRandomDateByVars()
        # set calender date
        NBSUtilities.selectCalendarDate(elm_type, elm_type_code, str(int(day)), month, str(int(year)), self.driver)
        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)

    # INV112

    def click_search_org(self):
        self.driver.find_element(By.ID, "INV183Icon").click()
        time.sleep(2)

    #      INV181Icon
    def click_search_provider(self, elm_type, elm_type_code):
        self.driver.find_element(elm_type, elm_type_code).click()
        time.sleep(2)

    def set_reporting_county(self):
        # NOT113
        element = self.driver.find_element(By.ID, self.SET_REPORTING_COUNTY)
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        select_element = Select(self.driver.find_element(By.ID, self.SET_REPORTING_COUNTY))
        random_index = random.randint(2, len(select_element.options))
        reporting_county_xpath = ("//*[@id='NOT113']/option[".__add__(str(random_index)) + "]")
        reporting_county_value = (self.driver.find_element(By.XPATH, reporting_county_xpath).get_attribute("value"))
        select_element.select_by_value(reporting_county_value)

    # Clinical
    def set_clinical_info(self):
        self.click_search_provider(By.ID, self.SET_CLINICAL_INFO)
        self.vaccination.switch_to_current_window()
        self.set_search_and_add_provider()
        self.vaccination.switch_to_original_window()
        time.sleep(2)
        self.set_hospital()

    def set_search_and_add_provider(self):
        self.driver.find_element(By.ID, "lastNameTxt").clear()
        self.driver.find_element(By.ID, "lastNameTxt").send_keys(NBSConstants.SEARCH_BY_LAST_NAME_VAL)
        time.sleep(1)
        self.driver.find_element(By.NAME, "Submit").click()
        self.click_addProvider()

    def click_addProvider(self):
        """
        Click on add provider button
        add new provider
        :return:
        """
        self.driver.find_element(By.ID, 'Add').click()
        # time.sleep(2)
        self.investigation_add_provider()
        return True

    def setTelephoneValue(self):
        self.driver.find_element(By.NAME, "telephone").clear()
        self.driver.find_element(By.NAME, "telephone").send_keys(NBSUtilities.generate_random_int(10))

    # def set_hospitalization_option(self):
    #     element = self.driver.find_element(By.ID, "INV128")
    #     self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
    #     select_element = Select(self.driver.find_element(By.ID, "INV128"))
    #     random_index = random.randint(2, len(select_element.options))
    #     # hospitalization_option = (
    #     #         "//*[@id=" + '"' + InvestigationLocators.WAS_THE_PATIENT_HOSPITALIZED + '"' + "]/option[".__add__(
    #     #     str(4)) + "]")
    #
    #     hospitalization_option_xpath = ("//*[@id='INV128']/option[".__add__(str(4)) + "]")
    #     hospitalization_option_value = (
    #         self.driver.find_element(By.XPATH, hospitalization_option_xpath).get_attribute("value"))
    #     select_element.select_by_value(hospitalization_option_value)
    #
    #     time.sleep(1)
    # // *[ @ id = "NBS_INV_GENV2_UI_3"] / tbody / tr[1] / td[2] / input
    # def set_hospitalization_option(self, path):
    #     select_ipath = str("INV128".__add__("_button"))
    #     WebDriverWait(self.driver, 10).until(
    #         EC.element_to_be_clickable((By.NAME, select_ipath))).click()
    #     element = WebDriverWait(self.driver, 10).until(
    #         EC.visibility_of_element_located((By.ID, path)))
    #     select_element = Select(element)
    #     select_element.select_by_index(random.randint(1, len(select_element.options) - 1))

    def set_hospitalization_option(self):
        element = self.driver.find_element(By.ID, "INV128")
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        select_element = Select(self.driver.find_element(By.ID, "INV128"))
        random_index = random.randint(2, len(select_element.options))
        hospitalization_option_xpath = ("//*[@id='INV128']/option[".__add__(str(random_index)) + "]")
        hospitalization_option_value = (
            self.driver.find_element(By.XPATH, hospitalization_option_xpath).get_attribute("value"))
        select_element.select_by_value(hospitalization_option_value)

    def set_reporting_source_type(self):
        element = self.driver.find_element(By.ID, "INV112")
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        select_element = Select(self.driver.find_element(By.ID, "INV112"))
        random_index = random.randint(2, len(select_element.options))
        reporting_source_type_xpath = ("//*[@id='INV112']/option[".__add__(str(random_index)) + "]")
        reporting_source_value = (
            self.driver.find_element(By.XPATH, reporting_source_type_xpath).get_attribute("value"))
        select_element.select_by_value(reporting_source_value)

    def set_hospital_data(self):
        was_patient_hospitalized = self.driver.find_element(By.NAME,
                                                            InvestigationLocators.PATIENT_HOSPITALIZED_INPUT).get_attribute(
            "value")
        if was_patient_hospitalized == 'Yes':
            # set the value
            self.driver.find_element(By.ID, InvestigationLocators.HOSPITAL_SEARCH).click()
            self.switch_to_current_window()
            time.sleep(1)
            self.vaccination.search_view_organization()
            self.switch_to_original_window()
            time.sleep(1)
            self.set_calender_date(By.ID, InvestigationLocators.ADMISSION_CAL_DATE)
            time.sleep(1)
            self.set_discharge_date()
        time.sleep(1)

    def set_discharge_date(self):
        hsp_admission_dt = self.driver.find_element(By.ID, InvestigationLocators.ADMISSION_CAL_DATE).get_attribute(
            "value")
        hsp_adm_dmy_list = hsp_admission_dt.split('/')
        adm_day, adm_month, adm_year = hsp_adm_dmy_list[1], hsp_adm_dmy_list[0], hsp_adm_dmy_list[2]
        vad_year = int(adm_year) + 1
        new_hsp_adm = adm_month + '/' + adm_day + '/' + str(vad_year)
        self.set_hsp_adm_date(By.NAME, InvestigationLocators.DISCHARGE_INP_DATE, new_hsp_adm)

    def set_hsp_adm_date(self, type_code, type_val, new_hsp_adm):
        element = self.driver.find_element(type_code, type_val)
        element.clear()
        element.send_keys(new_hsp_adm)

    # condition
    # def set_illness_duration_units(self):
    #     element = self.driver.find_element(By.ID, InvestigationLocators.ILLNESS_DURATION_UNITS)
    #     self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
    #     select_element = Select(self.driver.find_element(By.ID, "InvestigationLocators.ILLNESS_DURATION_UNITS"))
    #     random_index = random.randint(2, len(select_element.options))
    #     illness_duration_xpath = ("//*[@id=" + '"' + InvestigationLocators.ILLNESS_DURATION_UNITS + '"' + "]/option[".__add__(str(random_index)) + "]")
    #     illness_duration_value = (
    #         self.driver.find_element(By.XPATH, illness_duration_xpath).get_attribute("value"))
    #     select_element.select_by_value(illness_duration_value)
    #
    # set_age_at_onset

    def set_is_pregnant(self):
        element = self.driver.find_element(By.ID, InvestigationLocators.IS_PREGNANT)
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        select_element = Select(self.driver.find_element(By.ID, InvestigationLocators.IS_PREGNANT))
        random_index = random.randint(2, len(select_element.options))
        set_is_pregnant_xpath = ("//*[@id=" + '"' + InvestigationLocators.IS_PREGNANT + '"' + "]/option[".__add__(
            str(random_index)) + "]")
        set_is_pregnant_value = (
            self.driver.find_element(By.XPATH, set_is_pregnant_xpath).get_attribute("value"))
        select_element.select_by_value(set_is_pregnant_value)

    def set_is_patient_alive(self):
        element = self.driver.find_element(By.ID, InvestigationLocators.IS_PATIENT_ALIVE)
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        select_element = Select(self.driver.find_element(By.ID, InvestigationLocators.IS_PATIENT_ALIVE))
        random_index = random.randint(2, len(select_element.options))
        is_patient_alive_xpath = ("//*[@id=" + '"' + InvestigationLocators.IS_PATIENT_ALIVE + '"' + "]/option[".__add__(
            str(random_index)) + "]")
        is_patient_alive_value = (
            self.driver.find_element(By.XPATH, is_patient_alive_xpath).get_attribute("value"))
        select_element.select_by_value(is_patient_alive_value)

    def setShareDocumentDetails(self):
        self.setRecipient()
        self.setDocumentType()
        self.setShareComments()
        try:
            self.driver.find_element(By.XPATH, self.SHARE_COMMENTS_SUBMIT).click()
            time.sleep(3)
        except Exception as e:
            self.logger.info(f'Element not found error, {str(e)}')

        try:
            self.driver.find_element(By.XPATH, "/html/body/form/table/tbody/tr[4]/td/input[1]").click()
        except Exception as e:
            self.logger.info(f'Element not found error, {str(e)}')

        # self.switch_to_original_window()

    def setRecipient(self):
        try:
            element = self.driver.find_element(By.ID, "recipient")
            self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
            select_element = Select(self.driver.find_element(By.ID, "recipient"))
            size = len(select_element.options) - 1
            select_element.select_by_index(random.randint(1, size))
        except Exception as e:
            self.logger.info(f'Element not found error, {str(e)}')

    def setDocumentType(self):
        try:
            element = self.driver.find_element(By.ID, "documentType")
            self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
            select_element = Select(self.driver.find_element(By.ID, "documentType"))
            size = len(select_element.options) - 1
            select_element.select_by_index(random.randint(1, size))
        except Exception as e:
            self.logger.info(f'Element not found error, {str(e)}')

    # keep the functions seperate fopr PB, HYBRID, LEGACY
    def setShareComments(self):
        try:
            self.driver.find_element(By.ID, self.SHARE_COMMENTS_ID ).clear()
            self.driver.find_element(By.ID, self.SHARE_COMMENTS_ID).send_keys(
                "Comments:" + NBSUtilities.generate_random_string(50))
        except Exception as e:
            self.logger.info(f'Element not found error, {str(e)}')

    def setAddVaccination(self):
        self.driver.find_element(By.XPATH, self.ADD_VACCINATION_XPATH).click()
        self.vaccination.switch_to_current_window()
        self.driver.maximize_window()
        time.sleep(2)
        self.vaccination.click_vaccination_tab()
        time.sleep(2)
        self.vaccination.set_vaccination_admin()

        # get search view provider window
        time.sleep(2)
        self.vaccination.click_search_provider()
        self.vaccination.switch_to_current_window()
        # self.driver.maximize_window()
        time.sleep(1)
        self.vaccination.search_and_select_provider()
        time.sleep(1)

        # click on search organization button
        self.vaccination.switch_to_current_window()
        self.vaccination.click_search_org()

        # search the organization by last name
        self.vaccination.switch_to_current_window()
        # self.driver.maximize_window()
        time.sleep(1)
        # search_view_organization will search, click on add and will add details for new organization
        self.vaccination.search_view_organization()
        time.sleep(1)

        self.vaccination.switch_to_current_window()
        self.vaccination.set_vacc_administered_by_details(self.random_number)
        time.sleep(1)
        self.driver.find_element(By.NAME, "Submit").click()
        time.sleep(1)
        # close current window and back to original window
        self.driver.find_element(By.NAME, "Cancel").click()
        time.sleep(2)
        self.vaccination.switch_to_original_window()
        self.logger.info("test_add_vaccinations: Add vaccination test successful")

    def setAddLabReport(self, random_number):
        try:
            # click Add Lab Report Button
            self.performButtonClick(NBSConstants.TYPE_CODE_XPATH, self.ADD_LAB_REPORT_XPATH)
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
            # Facility and Provider Information
            self.setFacilityAndProviderSection(random_number)

            # Order Details For Lab
            self.setOrderDetailsForLab()

            # Test Results For Lab
            # set Ordered Test
            self.setOrderedTestDetails(random_number, "N")

            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
            # set Resulted Test
            self.setResultedTestDetails("N")

            # Lab Report Comments
            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                        NBSConstants.TYPE_CODE_NAME,
                                        "pageClientVO.answer(NBS460)",
                                        "Comments:".__add__(NBSUtilities.generate_random_string(60)))

            # Create Lab Report by clicking Submit
            self.performButtonClick(NBSConstants.TYPE_CODE_ID, "SubmitBottom")

            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            2)

        except:
            self.logger.info("Adding LabReport to Manage Associations is Failed")
            return False
        return True

    def validateLabReportLinkInTheManageAssociationPage(self, random_number):
        self.logger.info(random_number)
        # get local_id
        local_uid = self.db_utilities.getObservationUid(
            "LastName-" + str(random_number),
            "FirstName-" + str(random_number)
        )
        self.logger.info(local_uid)
        # get Observation Uid Based on Local ID
        observation_uid = self.db_utilities.getSingleColumnValueByTable(
            "observation_uid", "observation", "local_id", local_uid)
        self.logger.info(observation_uid)
        if observation_uid is not None:
            # click created Lab Report link manage association page, this will open view lab report
            self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                    "//a[contains(@href, 'observationUID=".__add__(
                                        str(observation_uid)) + "')]")
            # click on Return toManage Association link on View Lab Report
            self.performButtonClick(NBSConstants.TYPE_CODE_LINK_TEXT,
                                    "Return to Manage Associations")
            return True
        return False

    def setOrderDetailsForLab(self):
        # Lab Report Date
        selected_birth_day, selected_birth_month, selected_birth_year = NBSUtilities.getRandomDateByVars()
        self.selectCalendarDate(NBSConstants.TYPE_CODE_ID,
                               self.LABREPORT_DATE,
                                str(int(selected_birth_day)),
                                selected_birth_month,
                                str(int(selected_birth_year)))
        # Date Received By Public Health, set as current date

        self.selectCalendarDate(NBSConstants.TYPE_CODE_ID,
                                self.PUBLIC_HEALTH_DATE)
        # Pregnancy Status

        element = self.findElement(NBSConstants.TYPE_CODE_ID, self.PREGANANCY_STATUS)
        if element.is_enabled():
            self.setDropdownValueRandom(NBSConstants.TYPE_CODE_XPATH, "//*[@id='INV178']")
            if element.get_attribute("value") == "Y":
                # Pregnancy weeks
                self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                            NBSConstants.TYPE_CODE_ID,
                                            "NBS128",
                                            "19")


