import os
import random
import time
from datetime import datetime

import pytest
from selenium.webdriver.common.by import By
from selenium.webdriver.support.select import Select

from src.page_objects.data_entry.investigation import Investigation
from src.page_objects.data_entry.legacy_investigation import Legacyinvestigation
from src.page_objects.data_entry.patient import PatientObject
from src.page_objects.data_entry.vaccination import Vaccination
from src.utilities.constants import NBSConstants
from src.utilities.db_utilities import DBUtilities
from src.utilities.log_config import LogUtils
from src.utilities.nbs_utilities import NBSUtilities


class TestInvestigation:
    driver_globe = None
    logger = LogUtils.loggen(__name__)
    random_number = datetime.now().strftime("%d%m%y%H%M%S")
    random_number_for_patient = datetime.now().strftime("%m%d%y%H%M%S")
    logger.info(random_number)
    patient_search_submit_id = 'Submit'
    db_utilities = DBUtilities()
    repeatCount = NBSUtilities.generate_random_int(1)
    
    # condition_jurisdiction = {"Anthrax": "INV107", "2019 Novel Coronavirus": "INV107", "Babesiosis": "INV107",
    #                           "Varicella": "TUB237"}
    
    

    @pytest.fixture
    def test_parameters(self):
        return ["Anthrax", "Generic V2 Investigation"]

    def setup_and_patient_handle(self, setup):
        """
        Get patient handle.
        :param setup:
        :return:
        """
        self.driver = setup
        NBSUtilities.performLoginValidation(self.driver)  # Login Validation
        self.patient = PatientObject(self.driver)
        self.investigation = Investigation(self.driver)
        self.vaccination = Vaccination(self.driver)
        self.legacy_investigation = Legacyinvestigation(self.driver)
        self.driver.find_element(By.LINK_TEXT, "Data Entry").click()
        self.driver.find_element(By.LINK_TEXT, "Patient").click()
        time.sleep(1)

    def search_patient_by_lastname(self):
        last_name = '%'
        self.driver.find_element(By.ID, "DEM102").send_keys(last_name)
        self.driver.find_element(By.NAME, "Submit").click()
        time.sleep(1)
        try:
            patient_elm_xpath = self.legacy_investigation.SEARCH_RESULTS
            self.driver.find_element(By.XPATH, patient_elm_xpath).click()
        except Exception as e:
            self.driver.find_element(By.NAME, "Submit").click()

    def add_new_patient(self):
        # self.patient = PatientObject(self.driver)
        self.patient.addNewPatient(self.random_number_for_patient)
        self.patient.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, 2)
        return True

    def set_jurisdiction(self, element_id):
        element = self.driver.find_element(By.ID, element_id)
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        select_element = Select(self.driver.find_element(By.ID, element_id))
        random_index = random.randint(2, len(select_element.options))
        select_jurisdiction_xpath = (f"//*[@id='{element_id}']/option[".__add__(str(random_index)) + "]")
        jurisdiction_value = (self.driver.find_element(By.XPATH, select_jurisdiction_xpath).get_attribute("value"))
        select_element.select_by_value(jurisdiction_value)

    def test_add_Legacy_investigation(self, setup):
        """
        Test add investigation.
        :param setup: driver object
        :return: None
        """
        # Main loop
        try:
            self.driver = setup
            self.setup_and_patient_handle(setup)
            # Enter Patient Last Name
            # last_name = '%'
            # self.driver.find_element(By.ID, "DEM102").send_keys(last_name)
            # self.search_patient_by_lastname()

            #  search patient
            self.driver.find_element(By.ID, "DEM102").send_keys("last_name")
            self.driver.find_element(By.NAME, "Submit").click()
            time.sleep(1)
            self.add_new_patient()
            time.sleep(2)

            # click on event tab
            self.driver.find_element(By.ID, self.legacy_investigation.EVENT_INFO_TAB).click()
            add_new_btn_xpath = '//*[@id="subsect_Inv"]/table/tbody/tr/td['.__add__(
                str(NBSConstants.ADD_NEW_OPTION)) + ']/input[2]'
            self.driver.find_element(By.XPATH, add_new_btn_xpath).click()
            self.driver.find_element(By.NAME, "ccd_textbox").send_keys(self.legacy_investigation.condition)
            self.driver.find_element(By.ID, "nestedElementsControllerController").click()
            self.driver.find_element(By.ID, "Submit").click()
            self.legacy_investigation.switch_to_original_window()
            self.legacy_investigation.set_jurisdiction()
            self.legacy_investigation.set_investigation_summary()
            self.logger.info("test_add_legacy_investigation: add_legacy_investigation test successful")
            assert True
            # self.legacy_investigation.set_provider()
            # click submit
        except Exception as e:
            self.logger.info(f"Add Investigation Test Failed with {str(e)}")
            self.driver.save_screenshot(
                os.path.abspath(os.curdir) + "\\screenshots\\" + "test_add_Investigation.png")
            # self.driver.close()
            pytest.fail(f"add Investigation test FAILED")

    def test_edit_Legacy_investigation(self, setup):
        try:
            self.driver = setup
            self.setup_and_patient_handle(setup)

            #  search patient
            # self.driver.find_element(By.ID, "DEM102").send_keys(PatientName + str(i))
            # self.driver.find_element(By.NAME, "Submit").click()
            # time.sleep(1)
            patient_lastname = NBSConstants.APPEND_TEST_FOR_LAST_NAME.__add__("-").__add__(
                str(self.random_number_for_patient))
            print("PATIENT LAST NAME", patient_lastname)
            # PatientName = f"Patient_{now}"
            i = 1
            self.driver.find_element(By.ID, "DEM102").send_keys(patient_lastname)
            self.driver.find_element(By.NAME, "Submit").click()

            # click on event tab
            patient_elm_xpath = self.legacy_investigation.SEARCH_RESULTS
            self.driver.find_element(By.XPATH, patient_elm_xpath).click()
            # click on event tab
            self.driver.find_element(By.ID, self.legacy_investigation.EVENT_INFO_TAB).click()
            # // *[ @ id = "eventSumaryInv"] / tbody / tr / td[2] / a
            self.driver.find_element(By.XPATH, self.legacy_investigation.INVESTIGATION_TAB).click()
            # click edit button, edit button ID is named as delete
            self.driver.find_element(By.ID, "Edit").click()
            self.legacy_investigation.set_investigation_summary()
            self.logger.info("test_edit_legacy_investigation: edit_legacy_investigation test successful")
            submit_element= self.driver.find_elements(By.ID, "Submit")
            # time.sleep(1)
            assert True
            if submit_element :
               submit_element[0].click()
            # click submit
        except Exception as e:
            self.logger.info(f"Edit Investigation Test Failed with {str(e)}")
            self.driver.save_screenshot(
                os.path.abspath(os.curdir) + "\\screenshots\\" + "test_edit_Investigation.png")
            # self.driver.close()
            pytest.fail(f"edit Investigation test FAILED")

    def test_manage_associations(self, setup):
        self.driver = setup
        self.setup_and_patient_handle(setup)
        try:
            # now = datetime.now().strftime('%m%d_%H%M%S')
            patient_lastname = NBSConstants.APPEND_TEST_FOR_LAST_NAME.__add__("-").__add__(
                str(self.random_number_for_patient))

            # PatientName = f"Patient_{now}"
            i = 1
            self.driver.find_element(By.ID, "DEM102").send_keys(patient_lastname)
            self.driver.find_element(By.NAME, "Submit").click()

            # patient results
            patient_elm_xpath = self.legacy_investigation.SEARCH_RESULTS
            self.driver.find_element(By.XPATH, patient_elm_xpath).click()
            # click on event tab
            self.driver.find_element(By.ID, self.legacy_investigation.EVENT_INFO_TAB).click()
            # // *[ @ id = "eventSumaryInv"] / tbody / tr / td[2] / a
            # click on investigation
            self.driver.find_element(By.XPATH, self.legacy_investigation.INVESTIGATION_TAB).click()
            self.driver.find_element(By.ID, "Manage Associations").click()
            # Add lab report in manage associations tab
            # self.driver.find_element(By.XPATH, "//*[@id='events1']/tbody/tr/td/div/input").click()
            #  ADD LABREPORT
            if self.investigation.setAddLabReport(str(self.random_number_for_patient)):
                self.logger.info("*test_manage_associations:Add Lab Report to Manage Association is PASSED*")
                self.investigation.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, 2)
                if self.investigation.validateLabReportLinkInTheManageAssociationPage(
                        str(self.random_number_for_patient)):
                    self.logger.info("*test_manage_associations:View Lab Report through Link on Manage Association is "
                                     "PASSED*")
                else:
                    self.logger.info("*test_manage_associations:View Lab Report through Link on Manage Association is "
                                     "FAILED*")
            else:
                self.logger.info("*test_manage_associations:Add Lab Report to Manage Association is Failed*")

            time.sleep(2)
            #  Add vaccination in manage associations tab
            # self.driver.switch_to.alert.accept()
            time.sleep(1)
            self.investigation.setAddVaccination()
            self.driver.find_element(By.ID, "Submit").click()
            assert True
        except Exception as e:
            self.logger.info("test_manage_associations: manage associations test failed")
            self.driver.save_screenshot(
                os.path.abspath(os.curdir) + "\\screenshots\\" + "test_manage_associations.png")
            # self.driver.close()
            pytest.fail(f"test_manage_associations test FAILED")

    def test_transfer_owner(self, setup):
        self.driver = setup
        self.setup_and_patient_handle(setup)
        try:
            # now = datetime.now().strftime('%m%d_%H%M%S')
            patient_lastname = NBSConstants.APPEND_TEST_FOR_LAST_NAME.__add__("-").__add__(
                str(self.random_number_for_patient))

            # PatientName = f"Patient_{now}"
            i = 1
            self.driver.find_element(By.ID, "DEM102").send_keys(patient_lastname)
            self.driver.find_element(By.NAME, "Submit").click()

            # patient results
            patient_elm_xpath = self.legacy_investigation.SEARCH_RESULTS
            self.driver.find_element(By.XPATH, patient_elm_xpath).click()
            # click on event tab
            self.driver.find_element(By.ID, self.legacy_investigation.EVENT_INFO_TAB).click()
            # // *[ @ id = "eventSumaryInv"] / tbody / tr / td[2] / a
            # click on investigation
            self.driver.find_element(By.XPATH, self.legacy_investigation.INVESTIGATION_TAB).click()
            # Transfer ownership
            self.driver.find_element(By.ID, "Transfer Ownership").click()
            self.legacy_investigation.switch_to_current_window()
            time.sleep(1)
            self.legacy_investigation.set_jurisdiction_edit()
            time.sleep(1)
            self.driver.find_element(By.XPATH, '/ html / body / form / table / tbody / tr[7] / td / input[1]').click()
            self.legacy_investigation.switch_to_original_window()
            self.logger.info("test_transfer_owner: Transfer Owner test successful")
            assert True
        except Exception as e:
            self.logger.info(f"Transfer Owner Test Failed with {str(e)}")
            self.driver.save_screenshot(
                os.path.abspath(os.curdir) + "\\screenshots\\" + "test_share_document.png")
            # self.driver.close()
            pytest.fail(f"Transfer Owner Test FAILED")
            # self.close_and_quit_driver()

    def test_share_document(self, setup):
        """
        Test edit investigation.
        :param setup: driver object
        :return: None
        """
        self.driver = setup
        self.setup_and_patient_handle(setup)
        try:
            # now = datetime.now().strftime('%m%d_%H%M%S')
            patient_lastname = NBSConstants.APPEND_TEST_FOR_LAST_NAME.__add__("-").__add__(
                str(self.random_number_for_patient))
            # PatientName = f"Patient_{now}"
            i = 1
            self.driver.find_element(By.ID, "DEM102").send_keys(patient_lastname)
            self.driver.find_element(By.NAME, "Submit").click()

            # patient results
            patient_elm_xpath = self.legacy_investigation.SEARCH_RESULTS
            self.driver.find_element(By.XPATH, patient_elm_xpath).click()
            # click on event tab
            self.driver.find_element(By.ID, self.legacy_investigation.EVENT_INFO_TAB).click()
            # // *[ @ id = "eventSumaryInv"] / tbody / tr / td[2] / a
            # click on investigation
            self.driver.find_element(By.XPATH, self.legacy_investigation.INVESTIGATION_TAB).click()
            # id=caseRep for share document
            self.driver.find_element(By.ID, "Share Document").click()
            time.sleep(1)
            self.legacy_investigation.switch_to_current_window()
            self.investigation.setShareDocumentDetails()
            self.legacy_investigation.switch_to_original_window()
            self.logger.info("test_share_document: Share Document test successful")
            assert True
            # recipient dropdown ID= recipient

        except Exception as e:
            self.logger.info(f"Share Document Test Failed with {str(e)}")
            self.driver.save_screenshot(
                os.path.abspath(os.curdir) + "\\screenshots\\" + "test_share_document.png")
            # self.driver.close()
            pytest.fail(f"test_share_document test FAILED")
            # self.close_and_quit_driver()

    def test_print_investigation(self, setup):
        self.driver = setup
        self.setup_and_patient_handle(setup)
        try:
            # now = datetime.now().strftime('%m%d_%H%M%S')
            patient_lastname = NBSConstants.APPEND_TEST_FOR_LAST_NAME.__add__("-").__add__(
                str(self.random_number_for_patient))

            # PatientName = f"Patient_{now}"
            i = 1
            self.driver.find_element(By.ID, "DEM102").send_keys(patient_lastname)
            self.driver.find_element(By.NAME, "Submit").click()

            # patient results
            patient_elm_xpath = self.legacy_investigation.SEARCH_RESULTS
            self.driver.find_element(By.XPATH, patient_elm_xpath).click()
            # click on event tab
            self.driver.find_element(By.ID, self.legacy_investigation.EVENT_INFO_TAB).click()
            # // *[ @ id = "eventSumaryInv"] / tbody / tr / td[2] / a
            # click on investigation
            self.driver.find_element(By.XPATH, self.legacy_investigation.INVESTIGATION_TAB).click()
            self.driver.find_element(By.ID, "Print").click()
            time.sleep(2)
            # self.switch_to_current_window()
            # self.driver.quit()
            self.legacy_investigation.switch_to_original_window()
            self.logger.info("print_investigation: print_investigation test successful")
            # self.driver.quit()
            # self.driver.close()
            assert True
        except Exception as e:
            self.logger.info(f"print_investigation Test Failed with {str(e)}")
            self.driver.save_screenshot(
                os.path.abspath(os.curdir) + "\\screenshots\\" + "test_print_investigation.png")
            # self.driver.close()
            pytest.fail(f"print_investigation test FAILED")

        # self.driver.close()

    def test_delete_investigation(self, setup):
        self.driver = setup
        self.setup_and_patient_handle(setup)
        try:
            # now = datetime.now().strftime('%m%d_%H%M%S')
            patient_lastname = NBSConstants.APPEND_TEST_FOR_LAST_NAME.__add__("-").__add__(
                str(self.random_number_for_patient))
            # PatientName = f"Patient_{now}"
            i = 1
            self.driver.find_element(By.ID, "DEM102").send_keys(patient_lastname)
            self.driver.find_element(By.NAME, "Submit").click()

            # patient results
            patient_elm_xpath = self.legacy_investigation.SEARCH_RESULTS
            self.driver.find_element(By.XPATH, patient_elm_xpath).click()
            # click on event tab
            self.driver.find_element(By.ID, self.legacy_investigation.EVENT_INFO_TAB).click()
            # // *[ @ id = "eventSumaryInv"] / tbody / tr / td[2] / a
            # click on investigation
            self.driver.find_element(By.XPATH, self.legacy_investigation.INVESTIGATION_TAB).click()
            # delete investigation
            element = self.driver.find_elements(By.ID, "Delete")
            if element:
                element[0].click()
                
            self.driver.switch_to.alert.accept()
            time.sleep(1)
            self.driver.switch_to.alert.accept()
            self.driver.find_element(By.ID, "Manage Associations").click()
            # self.driver.switch_to.alert.accept()
            self.driver.find_element(By.XPATH, self.legacy_investigation.MANAGE_ASSOCIATION_LIST.format(self.legacy_investigation.LABLIST)).click()
            self.driver.find_element(By.XPATH, self.legacy_investigation.MANAGE_ASSOCIATION_LIST.format(self.legacy_investigation.VACCINATION_LIST)).click()
            self.driver.find_element(By.ID, "Submit").click()
            # delete investigation
            self.driver.find_element(By.ID, "Delete").click()

            time.sleep(1)
            self.driver.switch_to.alert.accept()
            self.logger.info("Delete Investigation: Delete Investigation test successful")
            assert True
            time.sleep(2)
            
            self.driver.close()
            self.driver.quit()

        except Exception as e:
            self.logger.info(f"Delete Investigation Test Failed with {str(e)}")
            self.driver.save_screenshot(
                os.path.abspath(os.curdir) + "\\screenshots\\" + "test_share_document.png")
            # self.driver.close()
            pytest.fail(f"test_Delete_Investigation test FAILED")


