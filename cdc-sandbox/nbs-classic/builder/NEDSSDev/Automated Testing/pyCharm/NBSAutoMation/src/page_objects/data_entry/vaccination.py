from datetime import datetime

import string
import random
import time

from selenium.webdriver.common.by import By
from selenium.webdriver.support.select import Select

from src.page_objects.data_entry.patient import PatientObject
from src.utilities.constants import NBSConstants
from src.utilities.log_config import LogUtils
from src.utilities.nbs_utilities import NBSUtilities
from src.page_objects.data_entry.organization import Organization


class Vaccination:
    def __init__(self, driver):
        self.driver = driver
        self.organization = Organization(self.driver)
        self.patient = PatientObject(self.driver)

    logger = LogUtils.loggen(__name__)
    vaccination_search_submit_id = 'Submit'
    random_number = datetime.now().strftime("%d%m%y%H%M%S")

    @staticmethod
    def generate_random_string(s_length):
        letters = string.ascii_lowercase
        # Randomly choose characters from letters for the given length of the string
        random_string = ''.join(random.choice(letters) for i in range(s_length))
        return random_string

    #   test expand and collapse tabs

    #  Vaccination Administered
    def set_vaccination_admin(self):
        # VaccineEventInformationSource
        self.setVaccineEventInfoSource()
        time.sleep(2)
        # # VaccineAdministeredDate
        self.setVaccineAdministeredDate()
        # Vaccination Anatomical Site
        self.setVaccinationSite()
        time.sleep(2)

    def set_vacc_administered_by_details(self, random_number, call_from=''):
        self.setVaccineType()
        self.setVaccineManufacturer()
        if call_from == 'edit':
            self.setExpDate()
        else:
            self.setVaccineExpDate()
        self.setVaccineLotNum(random_number)
        self.setVaccineDoseNum(random_number)
        # self.setHighTemp(random_number)
        time.sleep(2)

    def switch_to_current_window(self):
        current_window = self.driver.window_handles[-1]
        self.driver.switch_to.window(current_window)
        return True

    def switch_to_original_window(self):
        self.driver.switch_to.window(self.driver.window_handles[0])
        return True

    def setVaccineEventInfoSource(self):
        element = self.driver.find_element(By.ID, "VAC147")
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        select_element = Select(
            self.driver.find_element(By.ID, "VAC147"))
        size = len(select_element.options) - 1
        select_element.select_by_index(random.randint(1, size))

    def setVaccineAdministeredDate(self):
        """
        Function to get patient dob from patient and match with the vaccination administrator
        date (vad) with dob as per requirements and set the value to vad.
        dob : patient dob example 12/25/2001
        dob_dmy_list : dob , date month year list , example [12, 25, 2001]
        vad_birth_year + 1 : vad should be greater or equal to patient dob
        :return: None
        """
        dob = self.get_patient_dob()
        self.click_vaccination_tab()

        # dob = self.patient.patient_dob
        time.sleep(1)
        dob_dmy_list = dob.split('/')
        # addr_birth_day, addr_birth_month, addr_birth_year = NBSUtilities.getRandomDateByVars()
        vad_day, vad_month, vad_year = dob_dmy_list[1], dob_dmy_list[0], dob_dmy_list[2]
        vad_year = int(vad_year) + 1
        # NBSUtilities.selectCalendarDate(By.ID, 'VAC103Icon', vad_day, vad_month, str(vad_year), self.driver)
        new_vad = vad_month + '/' + vad_day + '/' + str(vad_year)
        self.set_admin_date_dob(By.ID, "VAC103", new_vad)

    def set_admin_date_dob(self, type_code, type_val, dob):
        element = self.driver.find_element(type_code, type_val)
        element.clear()
        element.send_keys(dob)

    def setVaccinationSite(self):
        element = self.driver.find_element(By.ID, "VAC104")
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        select_element = Select(
            self.driver.find_element(By.ID, "VAC104"))
        size = len(select_element.options) - 1
        select_element.select_by_index(random.randint(1, size))

    def search_and_select_organization(self):
        self.driver.find_element(By.ID, "lastNameTxt").clear()
        self.driver.find_element(By.ID, "lastNameTxt").send_keys(NBSConstants.SEARCH_BY_LAST_NAME_VAL)
        time.sleep(2)
        self.driver.find_element(By.NAME, self.vaccination_search_submit_id).click()
        time.sleep(2)
        self.driver.find_element(By.LINK_TEXT, 'Select').click()
        time.sleep(1)
        return True

    def search_view_organization(self):
        """
        search organization
        click on add org
        :return:
        """
        self.driver.find_element(By.ID, "lastNameTxt").clear()
        self.driver.find_element(By.ID, "lastNameTxt").send_keys(NBSConstants.SEARCH_BY_LAST_NAME_VAL)
        time.sleep(2)
        self.driver.find_element(By.NAME, self.vaccination_search_submit_id).click()
        time.sleep(1)
        self.click_addOrg()

        return True

    def click_addOrg(self):
        """
        Click on add org button
        add new organization
        :return:
        """
        self.driver.find_element(By.ID, 'Add').click()
        time.sleep(2)
        self.addOrg()
        return True

    def search_and_select_provider(self):
        self.driver.find_element(By.ID, "lastNameTxt").clear()
        self.driver.find_element(By.ID, "lastNameTxt").send_keys(NBSConstants.SEARCH_BY_LAST_NAME_VAL)
        time.sleep(1)
        self.driver.find_element(By.NAME, "Submit").click()
        time.sleep(2)
        self.driver.find_element(By.LINK_TEXT, 'Select').click()
        return True

    def click_search_provider(self):
        self.driver.find_element(By.ID, "VAC117Icon").click()
        time.sleep(2)

    def click_search_org(self):
        self.driver.find_element(By.ID, "VAC116Icon").click()
        time.sleep(2)

    #  Vaccine Type
    def setVaccineType(self):
        element = self.driver.find_element(By.ID, "VAC101")
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        select_element = Select(
            self.driver.find_element(By.ID, "VAC101"))
        size = len(select_element.options) - 1
        select_element.select_by_index(random.randint(1, size))

    #  Vaccine Manufacturer
    def setVaccineManufacturer(self):
        element = self.driver.find_element(By.ID, "VAC107")
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        select_element = Select(
            self.driver.find_element(By.ID, "VAC107"))
        size = len(select_element.options) - 1
        select_element.select_by_index(random.randint(1, size))

    # Vaccine Expiration Date
    def setVaccineExpDate(self):
        """
        Method to get the vaccine administrated date , validate and make it as greater than vaccine
        expire date and save.
        vad: vaccine administrated date
        vad_dmy_list: vaccine administrated date dmy( day, month, year) list, example [25, 12, 2000]
        vad_y + 1 : making vaccine expire date as greater than a year of vad
        :return: None
        """
        vad = self.driver.find_element(By.ID, 'VAC103').get_attribute('value')
        format_data = "%m/%d/%Y"
        vad_dt_obj = datetime.strptime(vad, format_data)
        updated_date = NBSUtilities.add_years(vad_dt_obj, 3)
        vaccine_exp_day, vaccine_exp_month, vaccine_exp_year = updated_date.strftime("%d"), \
            updated_date.strftime("%B"), updated_date.strftime("%Y")
        NBSUtilities.selectCalendarDate(By.ID, 'VAC109Icon', str(int(vaccine_exp_day)), vaccine_exp_month,
                                        str(vaccine_exp_year), self.driver)

    # Vaccine Lot Number
    def setVaccineLotNum(self, random_number):
        self.driver.find_element(By.ID, "VAC108").clear()
        self.driver.find_element(By.ID, "VAC108").send_keys(random_number)

    # Dose Number
    def setVaccineDoseNum(self, random_number):
        self.driver.find_element(By.ID, "VAC120").clear()
        self.driver.find_element(By.ID, "VAC120").send_keys(random_number)

    # Highest Measured Temperature
    def setHighTemp(self, random_number):
        self.driver.find_element(By.ID, "INV202").clear()
        self.driver.find_element(By.ID, "INV202").send_keys(random_number)

        element = self.driver.find_element(By.ID, "INV202UNIT")
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        select_element = Select(
            self.driver.find_element(By.ID, "INV202UNIT"))
        size = len(select_element.options) - 1
        select_element.select_by_index(random.randint(1, size))

    def search_patientBy_lastName(self, last_name):
        self.driver.find_element(By.NAME, "personSearch.lastName").clear()
        self.driver.find_element(By.NAME, "personSearch.lastName").send_keys(last_name)
        time.sleep(1)
        self.driver.find_element(By.NAME, "Submit").click()

    def click_patient_tab(self):
        self.driver.find_element(By.ID, 'tabs0head0').click()
        time.sleep(2)

    def click_vaccination_tab(self):
        self.driver.find_element(By.ID, 'tabs0head1').click()
        time.sleep(2)

    def get_patient_dob(self):
        """
        Get patient date of birth.
        :return: dob, str
        """
        self.click_patient_tab()
        dob = self.driver.find_element(By.ID, "DEM115").get_attribute('value')
        return dob

    def setOrganizationQuickCode(self):
        self.driver.find_element(By.NAME, "quickCode").clear()
        self.driver.find_element(By.NAME, "quickCode").send_keys('qcode', NBSUtilities.generate_random_int(10))
        time.sleep(1)

    def setOrganizationName(self):
        # //*[@id="subSec1"]/tbody/tr[2]/td[2]/input
        self.driver.find_element(By.NAME, "name").clear()
        self.driver.find_element(By.NAME, "name").send_keys('OrgName', (NBSUtilities.generate_random_int(10)))
        time.sleep(1)

    def editOrgTelephoneActions(self, edit_option):
        addr_edit_row_xpath = "//*[@id='nestedElementsHistoryBox|Telephone']/tr[".__add__(
            str(edit_option)) + "]/td[1]/a[1]"
        self.driver.find_element(By.XPATH, addr_edit_row_xpath).click()
        # self.setTelephoneInfo()
        # self.performActionClick("BatchEntryAddButtonTelephone")
        time.sleep(2)
        self.driver.find_element(By.ID, "BatchEntryAddButtonTelephone").click()
        time.sleep(3)

    def setStreetAddress1(self):
        self.driver.find_element(By.NAME, "streetAddress1").clear()
        self.driver.find_element(By.NAME, "streetAddress1").send_keys(
            "address1-" + NBSUtilities.generate_random_string(10))

    def setStreetAddress2(self):
        self.driver.find_element(By.NAME, "streetAddress2").clear()
        self.driver.find_element(By.NAME, "streetAddress2").send_keys(
            "address2-" + NBSUtilities.generate_random_string(10))

    def setCity(self):
        self.driver.find_element(By.NAME, "city").clear()
        self.driver.find_element(By.NAME, "city").send_keys("PostalAddress",
                                                            "_" + NBSUtilities.generate_random_string(5))

    def setState(self):
        element = self.driver.find_element(By.ID, "NPP021")
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        select_element = Select(self.driver.find_element(By.ID, "NPP021"))
        random_index = random.randint(2, len(select_element.options))
        add_org_addr_state_xpath = ("//*[@id='NPP021']/option[".__add__(str(random_index)) + "]")
        addr_state_value = (self.driver.find_element(By.XPATH, add_org_addr_state_xpath)
                            .get_attribute("value"))
        select_element.select_by_value(addr_state_value)

    def setZipcode(self):
        fixed_digits = 5
        random_number = random.randrange(11111, 99999, fixed_digits)
        self.driver.find_element(By.NAME, "zip").clear()
        self.driver.find_element(By.NAME, "zip").send_keys(random_number)

    def setCounty(self):
        element = self.driver.find_element(By.ID, "NPP023")
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        select_element = Select(self.driver.find_element(By.ID, "NPP023"))
        random_index = random.randint(2, len(select_element.options))
        add_org_addr_county_xpath = ("//*[@id='NPP023']/option[".__add__(str(random_index)) + "]")
        addr_state_value = (self.driver.find_element(By.XPATH, add_org_addr_county_xpath)
                            .get_attribute("value"))
        select_element.select_by_value(addr_state_value)

    def setCountry(self):
        element = self.driver.find_element(By.ID, "DEM167")
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        select_element = Select(self.driver.find_element(By.ID, "DEM167"))
        random_index = random.randint(2, len(select_element.options))
        add_org_addr_county_xpath = ("//*[@id='DEM167']/option[".__add__(str(random_index)) + "]")
        addr_state_value = (self.driver.find_element(By.XPATH, add_org_addr_county_xpath)
                            .get_attribute("value"))
        select_element.select_by_value(addr_state_value)

    def setOrgTextBoxValue(self, element, value):
        self.driver.find_element(By.NAME, element).clear()
        self.driver.find_element(By.NAME, element).send_keys(value)

    def setTelephoneValue(self):
        self.driver.find_element(By.NAME, "telephone").clear()
        self.driver.find_element(By.NAME, "telephone").send_keys(NBSUtilities.generate_random_int(10))

    def addOrg(self):
        """
        Add organization.
        :return: boolean, true
        """
        # NBSUtilities.search_ByLastName('organizationSearch.nmTxt', last_name, self.driver)
        # self.driver.find_element(By.ID, "Add").click()
        self.setOrganizationQuickCode()
        self.setOrganizationName()
        self.setStreetAddress1()
        self.setStreetAddress2()
        self.setCity()
        self.setState()
        self.setZipcode()
        self.setCounty()
        self.setCountry()
        self.setTelephoneValue()
        self.setOrgTextBoxValue("ext", NBSUtilities.generate_random_int(4))
        self.setOrgTextBoxValue("EMail", NBSUtilities.generate_random_string(8).__add__("@omg.com"))
        time.sleep(2)
        self.driver.find_element(By.XPATH, '/html/body/div[2]/input[1]').click()
        return True

    def view_patient(self):
        try:
            patient_elm_xpath = '//*[@id="searchResultsTable"]/tbody/tr/td[1]/a'
            self.driver.find_element(By.XPATH, patient_elm_xpath).click()
            return True
        except Exception as e:
            return False

    def clear_and_reassign_provider(self):
        # click on clear and reassign
        self.driver.find_element(By.ID, "VAC117CodeClearButton").click()
        # click on search VAC117Icon
        self.driver.find_element(By.ID, "VAC117Icon").click()
        time.sleep(1)
        self.switch_to_current_window()
        self.search_and_select_provider()
        time.sleep(1)
        return True

    def setTodayDate(self, type, type_val):
        now = datetime.datetime.now()
        today = now.strftime("%m-%d-%Y")
        element = self.driver.find_element(type, type_val)
        element.clear()
        element.send_keys(today)

    def setExpDate(self):
        self.driver.find_element(By.ID, "VAC109").send_keys(NBSConstants.CALENDAR_TODAY_DATE)
        time.sleep(1)

    def clear_and_reassign_org(self):
        # click on clear and reassign
        self.driver.find_element(By.ID, "VAC116CodeClearButton").click()
        # click on search button organization VAC117Icon
        self.driver.find_element(By.ID, "VAC116Icon").click()
        time.sleep(1)
        self.switch_to_current_window()
        self.search_and_select_organization()
        time.sleep(1)
        return True

    def edit_and_set_vaccinationData(self):
        # click event tab
        self.patient.get_event_handle()
        time.sleep(1)
        # click edit vaccination
        self.driver.find_element(By.XPATH, "//*[@id='eventVaccination']/tbody/tr/td[1]/a").click()
        time.sleep(2)
        # switch to vaccination window
        self.switch_to_current_window()
        # click edit button
        self.driver.find_element(By.NAME, "Edit").click()
        time.sleep(2)
        # go to vaccination tab
        self.click_vaccination_tab()
        # set vaccination data
        self.edit_vaccination()

    def edit_vaccination(self):
        # VaccineEventInformationSource
        self.setVaccineEventInfoSource()
        time.sleep(2)
        # # VaccineAdministeredDate
        self.setVaccineAdministeredDate()
        # Vaccination Anatomical Site
        self.setVaccinationSite()
        time.sleep(2)
        self.clear_and_reassign_provider()
        time.sleep(2)
        self.switch_to_current_window()
        time.sleep(1)
        self.clear_and_reassign_org()
        self.switch_to_current_window()
        time.sleep(2)
        self.set_vacc_administered_by_details(self.random_number, 'edit')
        time.sleep(2)
        self.driver.find_element(By.NAME, self.vaccination_search_submit_id).click()
        self.driver.find_element(By.NAME, "Cancel").click()
        time.sleep(2)
        self.switch_to_original_window()
        return True

    def delete_vaccination(self):
        self.get_vaccination_tab()
        # delete vaccination
        self.driver.find_element(By.NAME, "Delete").click()
        time.sleep(2)
        self.driver.switch_to.alert.accept()

    def get_vaccination_tab(self):
        # click event tab
        self.patient.get_event_handle()
        time.sleep(1)
        # click edit vaccination
        self.driver.find_element(By.XPATH, "//*[@id='eventVaccination']/tbody/tr/td[1]/a").click()
        time.sleep(2)
        # switch to vaccination window
        self.switch_to_current_window()
        time.sleep(2)
        self.click_vaccination_tab()
        return True

    def print_vaccination(self):
        self.get_vaccination_tab()
        self.driver.find_element(By.NAME, "Submit").click()
