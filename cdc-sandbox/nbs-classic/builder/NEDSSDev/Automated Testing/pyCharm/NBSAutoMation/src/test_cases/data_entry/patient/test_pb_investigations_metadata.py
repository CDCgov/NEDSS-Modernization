import os
import random
import time
from datetime import datetime

import pytest
from selenium.webdriver.common.by import By
from selenium.webdriver.support.select import Select

from src.page_objects.data_entry.investigation import Investigation
from src.page_objects.data_entry.patient import PatientObject
from src.page_objects.data_entry.vaccination import Vaccination
from src.utilities.constants import NBSConstants
from src.utilities.db_utilities import DBUtilities
from src.utilities.log_config import LogUtils
from src.utilities.nbs_utilities import NBSUtilities
from src.utilities.ReadMetaData_PB_New import ReadMetaData_PB_New


class TestVaccinationV2:
    driver_globe = None
    logger = LogUtils.loggen(__name__)
    # random_number = datetime.now().strftime("%d%m%y%H%M%S")
    random_number_for_patient = datetime.now().strftime("%d%m%y%H%M%S")
    # logger.info(random_number)
    patient_search_submit_id = 'Submit'
    db_utilities = DBUtilities()
    repeatCount = NBSUtilities.generate_random_int(1)
    # start_date
    var_illness_onset_dt = "10/01/1951"
    num_of_macules = random.randint(2, 15)
    num_of_papules = random.randint(2, 15)
    num_of_vesicles = random.randint(2, 15)
    DOB = "10/01/1950"
    tub195 = "09/01/1951"
    specialChars = 'Deep^~&#~!@#$%^&*()_+{}:<>?`-=[],.\'\"\\'
    investigation_date_of_report = "10/01/1959"
    dt = "10/01/1951"
    random_number_for_patient_extended = None
    patient_uid = None
    # condition = "Anthrax"
    condition_jurisdiction = {"Anthrax": "INV107", "2019 Novel Coronavirus": "INV107", "Babesiosis": "INV107",
                              "Varicella": "TUB237"}
    # "2019 Novel Coronavirus"
    # "Anthrax"
    # "Babesiosis"
    # "Varicella"
    # templateName = "Generic V2 Investigation"
    # "COVID-19 v1.1"
    # "Generic V2 Investigation"
    # "Babesiosis Investigation"
    # "Legacy Varicella Investigation"
    tab_name = "Case Info"
    numeric_value = 1
    text1 = ''
    ynu_random = ''
    rNumber = 1
    database = "Test10"
    YNU_Random = "Yes"
    legacy_comments = 'NTF137'
    legacy_out_of_system_recipient = 'exportFacility'
    legacy_out_of_system_jurisdiction_value = "999999"

    # current fields
    updated_fields = {}


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
        self.driver.find_element(By.LINK_TEXT, "Data Entry").click()
        self.driver.find_element(By.LINK_TEXT, "Patient").click()
        time.sleep(1)

    # def close_and_quit_driver(self):
    #     """
    #     Close a d quit driver.
    #     :return:
    #     """
    #     self.driver.close()
    #     self.driver.quit()

    def switch_to_original_window(self):
        self.driver.switch_to.window(self.driver.window_handles[0])
        return True

    def switch_to_current_window(self):
        current_window = self.driver.window_handles[-1]
        self.driver.switch_to.window(current_window)
        return True

    def isElementDisplayed(self, elm_path, elm_val):
        try:
            self.driver.find_element(elm_path, elm_val).click()
        except Exception as e:
            self.logger.info(str(e))
            return False
        return True

    def add_new_patient(self):
        # self.patient = PatientObject(self.driver)
        self.patient.addNewPatient(self.random_number_for_patient)
        self.patient.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, 2)
        return True

    def search_patient_by_lastname(self):
        last_name = '%'
        self.driver.find_element(By.ID, "DEM102").send_keys(last_name)
        self.driver.find_element(By.NAME, "Submit").click()
        time.sleep(1)
        try:
            patient_elm_xpath = self.investigation.SEARCH_RESULTS
            self.driver.find_element(By.XPATH, patient_elm_xpath).click()
        except Exception as e:
            self.driver.find_element(By.NAME, "Submit").click()

    def set_jurisdiction(self, element_id):
        element = self.driver.find_element(By.ID, element_id)
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        select_element = Select(self.driver.find_element(By.ID, element_id))
        random_index = random.randint(2, len(select_element.options))
        select_jurisdiction_xpath = (f"//*[@id='{element_id}']/option[".__add__(str(random_index)) + "]")
        jurisdiction_value = (self.driver.find_element(By.XPATH, select_jurisdiction_xpath).get_attribute("value"))
        jurisdiction_value = '999999'
        select_element.select_by_value(jurisdiction_value)

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
            element = self.driver.find_element(By.ID, 'documentType')
            self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
            select_element = Select(element)
            random_index = random.randint(2, len(select_element.options))
            select_recipient_xpath = (
                    f"//*[@id='documentType']/option[".__add__(
                        str(random_index)) + "]")
            value = self.driver.find_element(By.XPATH, select_recipient_xpath).get_attribute("value")
            select_element.select_by_value(value)
            self.set_general_comments(self.legacy_comments)
        self.updated_fields['INV107'] = jurisdiction_value

    def set_general_comments(self, id):
        self.driver.find_element(By.ID, id).clear()
        self.driver.find_element(By.ID, id).send_keys("GeneralComments")

    def test_add_investigation(self, setup, test_parameters):
        """
        Test add investigation.
        :param setup: driver object
        :return: None
        """
        self.driver = setup
        self.setup_and_patient_handle(setup)
        # Get the current date and time
        now = datetime.now().strftime('%m%d_%H%M%S')
        # Set up ReadMetaData_PB_New
        newSTD_Patient = ReadMetaData_PB_New(self.driver)
        condition, templateName = test_parameters["condition"], test_parameters["templateName"]
        # Main loop
        for l in range(1, 2):
            PatientName = f"Patient_{now}"
            if l % 20 == 0:
                newSTD_Patient = ReadMetaData_PB_New(self.driver)
            i = 1

            try:
                # Enter Patient Last Name by hard coding the last name
                # last_name = '%'
                # self.driver.find_element(By.ID, "DEM102").send_keys(last_name)
                # self.search_patient_by_lastname()

                # search patient by adding and searching a new patient
                self.driver.find_element(By.ID, "DEM102").send_keys(PatientName + str(i))
                self.driver.find_element(By.NAME, "Submit").click()
                time.sleep(1)
                self.add_new_patient()
                time.sleep(2)

                # click on event tab
                self.driver.find_element(By.ID, self.investigation.CASE_INFO_TAB).click()
                add_new_btn_xpath = '//*[@id="subsect_Inv"]/table/tbody/tr/td['.__add__(
                    str(NBSConstants.ADD_NEW_OPTION)) + ']/input[2]'
                self.driver.find_element(By.XPATH, add_new_btn_xpath).click()
                self.driver.find_element(By.NAME, "ccd_textbox").send_keys(condition)
                self.updated_fields['ccd_textbox'] = condition

                self.driver.find_element(By.ID, "nestedElementsControllerController").click()
                self.driver.find_element(By.ID, "Submit").click()
                self.switch_to_original_window()
                for col in range(1, 2):
                    newSTD_Patient.readData(col, PatientName, templateName, self.updated_fields)
                    self.logger.info("test_add_investigations: Add investigation test successful")
                time.sleep(2)

                patient_lastname = NBSConstants.APPEND_TEST_FOR_LAST_NAME.__add__("-").__add__(
                    str(self.random_number_for_patient))
                print("PATIENT LAST NAME", patient_lastname)
                # PatientName = f"Patient_{now}"
                self.driver.find_element(By.XPATH, "//*[@class='returnToPageLink']/a").click()
                time.sleep(2)
                self.driver.find_element(By.XPATH, self.investigation.INVESTIGATION_TAB).click()
                # click edit button, edit button ID is named as delete
                time.sleep(2)
                # click submit
                # self.driver.find_element(By.NAME, "SubmitTop").click()
                self.logger.info("test_edit_investigation: Add investigation test successful")
            except Exception as e:
                self.logger.info(f"Add Investigation Test Failed with {str(e)}")
                self.driver.save_screenshot(
                    os.path.abspath(os.curdir) + "\\screenshots\\" + "test_add_Investigation.png")
                # self.driver.close()
                pytest.fail(f"add Investigation test FAILED")

    def test_edit_investigation(self, setup, test_parameters):
        """
        Test edit investigation.
        :param setup: driver object
        :return: None
        """
        self.setup_and_patient_handle(setup)
        condition, templateName = test_parameters["condition"], test_parameters["templateName"]
        try:
            # now = datetime.now().strftime('%m%d_%H%M%S')

            patient_lastname = NBSConstants.APPEND_TEST_FOR_LAST_NAME.__add__("-").__add__(
                str(self.random_number_for_patient))
            print("PATIENT LAST NAME", patient_lastname)
            # PatientName = f"Patient_{now}"
            i = 1
            self.driver.find_element(By.ID, "DEM102").send_keys(patient_lastname)
            self.driver.find_element(By.NAME, "Submit").click()
            # patient results
            patient_elm_xpath = self.investigation.SEARCH_RESULTS
            self.driver.find_element(By.XPATH, patient_elm_xpath).click()
            # click on event tab
            self.driver.find_element(By.ID, self.investigation.CASE_INFO_TAB).click()
            # // *[ @ id = "eventSumaryInv"] / tbody / tr / td[2] / a
            self.driver.find_element(By.XPATH, self.investigation.INVESTIGATION_TAB).click()
            # click edit button, edit button ID is named as delete
            time.sleep(2)
            self.driver.find_element(By.ID, "delete").click()
            time.sleep(2)
            newSTD_Patient = ReadMetaData_PB_New(self.driver)

            for col in range(1, 2):
                time.sleep(2)
                newSTD_Patient.readData(col, patient_lastname, templateName, self.updated_fields)
                time.sleep(2)
            
            element=self.driver.find_elements(By.ID, "SubmitTop")
            if element:
                element[0].click()
                time.sleep(1)

            self.logger.info("test_edit_investigation: Edit investigation test successful")
            assert True
        except Exception as e:
            self.logger.error(f"Edit Investigation Test Failed with {str(e)}")
            self.driver.save_screenshot(
                os.path.abspath(os.curdir) + "\\screenshots\\" + "test_edit_Investigation.png")
            # self.driver.close()
            pytest.fail(f"edit Investigation test FAILED")
        # self.close_and_quit_driver()

    # def test_manage_associations(self, setup, test_parameters):
    #     self.driver = setup
    #     self.setup_and_patient_handle(setup)
    #     try:
    #         # now = datetime.now().strftime('%m%d_%H%M%S')
    #         patient_lastname = NBSConstants.APPEND_TEST_FOR_LAST_NAME.__add__("-").__add__(str(self.random_number_for_patient))
    #         # PatientName = f"Patient_{now}"
    #         i = 1
    #         self.driver.find_element(By.ID, "DEM102").send_keys(patient_lastname)
    #         self.driver.find_element(By.NAME, "Submit").click()

    #         # patient results
    #         patient_elm_xpath = self.investigation.SEARCH_RESULTS
    #         self.driver.find_element(By.XPATH, patient_elm_xpath).click()
    #         # click on event tab
    #         self.driver.find_element(By.ID, self.investigation.CASE_INFO_TAB).click()
    #         # // *[ @ id = "eventSumaryInv"] / tbody / tr / td[2] / a
    #         # click on investigation
    #         self.driver.find_element(By.XPATH, self.investigation.INVESTIGATION_TAB).click()
    #         self.driver.find_element(By.ID, "manageAssociations").click()
    #         # Add lab report in manage associations tab
    #         # self.driver.find_element(By.XPATH, "//*[@id='events1']/tbody/tr/td/div/input").click()
    #         #  ADD LABREPORT
    #         # self.investigation.setAddLabReport()
    #         if self.investigation.setAddLabReport(str(self.random_number_for_patient)):
    #             self.logger.info("*test_manage_associations:Add Lab Report to Manage Association is PASSED*")
    #             self.investigation.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, 2)
    #             if self.investigation.validateLabReportLinkInTheManageAssociationPage(
    #                     str(self.random_number_for_patient)):
    #                 self.logger.info("*test_manage_associations:View Lab Report through Link on Manage Association is "
    #                                  "PASSED*")
    #             else:
    #                 self.logger.info("*test_manage_associations:View Lab Report through Link on Manage Association is "
    #                                  "FAILED*")
    #         else:
    #             self.logger.info("*test_manage_associations:Add Lab Report to Manage Association is Failed*")

    #         time.sleep(2)
    #         #  Add vaccination in manage associations tab
    #         # self.driver.switch_to.alert.accept()
    #         time.sleep(1)
    #         self.investigation.setAddVaccination()
    #         self.driver.find_element(By.ID, "Submit").click()
    #         assert True
    #     except Exception as e:
    #         self.logger.info("test_manage_associations: manage associations test failed")
    #         self.driver.save_screenshot(
    #             os.path.abspath(os.curdir) + "\\screenshots\\" + "test_manage_associations.png")
    #         # self.driver.close()
    #         pytest.fail(f"test_manage_associations test FAILED")

    # def test_transfer_owner(self, setup, test_parameters):
    #     self.driver = setup
    #     self.setup_and_patient_handle(setup)
    #     condition, templateName = test_parameters["condition"], test_parameters["templateName"]
    #     try:
    #         # now = datetime.now().strftime('%m%d_%H%M%S')
    #         patient_lastname = NBSConstants.APPEND_TEST_FOR_LAST_NAME.__add__("-").__add__(
    #             str(self.random_number_for_patient))

    #         # PatientName = f"Patient_{now}"
    #         i = 1
    #         self.driver.find_element(By.ID, "DEM102").send_keys(patient_lastname)
    #         self.driver.find_element(By.NAME, "Submit").click()

    #         # patient results
    #         patient_elm_xpath = self.investigation.SEARCH_RESULTS
    #         self.driver.find_element(By.XPATH, patient_elm_xpath).click()
    #         # click on event tab
    #         self.driver.find_element(By.ID, self.investigation.CASE_INFO_TAB).click()
    #         # // *[ @ id = "eventSumaryInv"] / tbody / tr / td[2] / a
    #         # click on investigation
    #         self.driver.find_element(By.XPATH, self.investigation.INVESTIGATION_TAB).click()
    #         # Transfer ownership
    #         self.driver.find_element(By.XPATH, self.investigation.TRANSFER_OWNERSHIP).click()
    #         self.switch_to_current_window()
    #         time.sleep(1)
    #         # TUB237
    #         self.set_jurisdiction(self.condition_jurisdiction.get(condition))
    #         time.sleep(3)
    #         self.driver.find_element(By.XPATH, self.investigation.SHARE_COMMENTS_SUBMIT).click()
    #         self.switch_to_original_window()
    #         self.logger.info("test_transfer_owner: Transfer Owner test successful")
    #         assert True
    #     except Exception as e:
    #         self.logger.info(f"Transfer Owner Test Failed with {str(e)}")
    #         self.driver.save_screenshot(
    #             os.path.abspath(os.curdir) + "\\screenshots\\" + "test_share_document.png")
    #         # self.driver.close()
    #         pytest.fail(f"Transfer Owner Test FAILED")
    #         # self.close_and_quit_driver()

    # def test_share_document(self, setup, test_parameters):
    #     """
    #     Test edit investigation.
    #     :param setup: driver object
    #     :return: None
    #     """
    #     self.driver = setup
    #     self.setup_and_patient_handle(setup)
    #     newSTD_Patient = ReadMetaData_PB_New(self.driver)
    #     try:
    #         # now = datetime.now().strftime('%m%d_%H%M%S')
    #         patient_lastname = NBSConstants.APPEND_TEST_FOR_LAST_NAME.__add__("-").__add__(
    #             str(self.random_number_for_patient))
    #         # PatientName = f"Patient_{now}"
    #         i = 1
    #         self.driver.find_element(By.ID, "DEM102").send_keys(patient_lastname)
    #         self.driver.find_element(By.NAME, "Submit").click()

    #         # patient results
    #         patient_elm_xpath = self.investigation.SEARCH_RESULTS
    #         self.driver.find_element(By.XPATH, patient_elm_xpath).click()
    #         # click on event tab
    #         self.driver.find_element(By.ID, self.investigation.CASE_INFO_TAB).click()
    #         # // *[ @ id = "eventSumaryInv"] / tbody / tr / td[2] / a
    #         # click on investigation
    #         self.driver.find_element(By.XPATH, self.investigation.INVESTIGATION_TAB).click()
    #         # id=caseRep for share document
    #         self.driver.find_element(By.ID, "caseRep").click()
    #         time.sleep(1)
    #         self.switch_to_current_window()
    #         self.investigation.setShareDocumentDetails()
    #         self.switch_to_original_window()
    #         self.logger.info("test_share_document: Share Document test successful")
    #         assert True
    #         # recipient dropdown ID= recipient

    #     except Exception as e:
    #         self.logger.info(f"Share Document Test Failed with {str(e)}")
    #         self.driver.save_screenshot(
    #             os.path.abspath(os.curdir) + "\\screenshots\\" + "test_share_document.png")
    #         # self.driver.close()
    #         pytest.fail(f"test_share_document test FAILED")
    #         # self.close_and_quit_driver()

    # #
    # def test_create_notifications(self, setup, test_parameters):
    #     self.driver = setup
    #     self.setup_and_patient_handle(setup)
    #     try:
    #         # now = datetime.now().strftime('%m%d_%H%M%S')
    #         patient_lastname = NBSConstants.APPEND_TEST_FOR_LAST_NAME.__add__("-").__add__(
    #             str(self.random_number_for_patient))

    #         # PatientName = f"Patient_{now}"
    #         i = 1
    #         self.driver.find_element(By.ID, "DEM102").send_keys(patient_lastname)
    #         self.driver.find_element(By.NAME, "Submit").click()

    #         # patient results
    #         patient_elm_xpath = self.investigation.SEARCH_RESULTS
    #         self.driver.find_element(By.XPATH, patient_elm_xpath).click()
    #         # click on event tab
    #         self.driver.find_element(By.ID, self.investigation.CASE_INFO_TAB).click()
    #         # // *[ @ id = "eventSumaryInv"] / tbody / tr / td[2] / a
    #         # click on investigation
    #         self.driver.find_element(By.XPATH, self.investigation.INVESTIGATION_TAB).click()
    #         # createNotification
    #         self.driver.find_element(By.XPATH, self.investigation.CREATE_NOTIFICATION).click()

    #         self.switch_to_current_window()
    #         self.driver.find_element(By.ID, "NTF137").send_keys("Comments:" + NBSUtilities.generate_random_string(50))
    #         # //*[@id="topcreatenotId"]/input[1]
    #         self.driver.find_element(By.XPATH, self.investigation.NOTE_ID).click()
    #         time.sleep(2)
    #         self.switch_to_original_window()
    #         # self.driver.switch_to.alert.accept()
    #         self.logger.info("test_create_notifications: create_notifications test successful")
    #         assert True
    #     except Exception as e:
    #         self.logger.info(f"Create notifications Test Failed with {str(e)}")
    #         self.driver.save_screenshot(
    #             os.path.abspath(os.curdir) + "\\screenshots\\" + "test_share_document.png")
    #         # self.driver.close()
    #         pytest.fail(f"test_create_notifications test FAILED")
    #         # self.close_and_quit_driver()

    # def test_print_investigation(self, setup):
    #     self.driver = setup
    #     self.setup_and_patient_handle(setup)
    #     try:
    #         # now = datetime.now().strftime('%m%d_%H%M%S')
    #         patient_lastname = NBSConstants.APPEND_TEST_FOR_LAST_NAME.__add__("-").__add__(
    #             str(self.random_number_for_patient))

    #         # PatientName = f"Patient_{now}"
    #         i = 1
    #         self.driver.find_element(By.ID, "DEM102").send_keys(patient_lastname)
    #         self.driver.find_element(By.NAME, "Submit").click()

    #         # patient results
    #         patient_elm_xpath = self.investigation.SEARCH_RESULTS
    #         self.driver.find_element(By.XPATH, patient_elm_xpath).click()
    #         # click on event tab
    #         self.driver.find_element(By.ID, self.investigation.CASE_INFO_TAB).click()
    #         # // *[ @ id = "eventSumaryInv"] / tbody / tr / td[2] / a
    #         # click on investigation
    #         self.driver.find_element(By.XPATH, self.investigation.INVESTIGATION_TAB).click()
    #         self.driver.find_element(By.ID, "print").click()
    #         time.sleep(2)
    #         # self.switch_to_current_window()
    #         # self.driver.quit()
    #         self.switch_to_original_window()
    #         self.logger.info("print_investigation: print_investigation test successful")
    #         # self.driver.quit()
    #         assert True
    #     except Exception as e:
    #         self.logger.info(f"print_investigation Test Failed with {str(e)}")
    #         self.driver.save_screenshot(
    #             os.path.abspath(os.curdir) + "\\screenshots\\" + "test_print_investigation.png")
    #         # self.driver.close()
    #         pytest.fail(f"print_investigation test FAILED")

    #     # self.driver.close()

    # def test_delete_investigation(self, setup):
    #     self.driver = setup
    #     self.setup_and_patient_handle(setup)
    #     try:
    #         # now = datetime.now().strftime('%m%d_%H%M%S')
    #         patient_lastname = NBSConstants.APPEND_TEST_FOR_LAST_NAME.__add__("-").__add__(
    #             str(self.random_number_for_patient))
    #         # PatientName = f"Patient_{now}"
    #         i = 1
    #         self.driver.find_element(By.ID, "DEM102").send_keys(patient_lastname)
    #         self.driver.find_element(By.NAME, "Submit").click()

    #         # patient results
    #         patient_elm_xpath = self.investigation.SEARCH_RESULTS
    #         self.driver.find_element(By.XPATH, patient_elm_xpath).click()
    #         # click on event tab
    #         self.driver.find_element(By.ID, self.investigation.CASE_INFO_TAB).click()
    #         # // *[ @ id = "eventSumaryInv"] / tbody / tr / td[2] / a
    #         # click on investigation
    #         self.driver.find_element(By.XPATH, self.investigation.INVESTIGATION_TAB).click()
    #         # delete investigation
    #         element = self.driver.find_elements(By.XPATH,self.investigation.DELETE )
    #         if element:
    #             element[0].click()
    #         self.driver.switch_to.alert.accept()
    #         self.logger.info("Delete Investigation: Delete Investigation test successful")
    #         assert True
    #         time.sleep(2)
    #         self.driver.quit()

    #     except Exception as e:
    #         self.logger.info(f"Delete Investigation Test Failed with {str(e)}")
    #         self.driver.save_screenshot(
    #             os.path.abspath(os.curdir) + "\\screenshots\\" + "test_share_document.png")
    #         # self.driver.close()
    #         pytest.fail(f"test_Delete_Investigation test FAILED")
    #         # self.close_and_quit_driver()

    # def test_close_and_quit_driver(self, setup):
    #     """
    #     Close a d quit driver.
    #     :return:
    #     """
    #     try:
    #         self.driver = setup
    #         self.switch_to_current_window()
    #         self.driver.close()
    #         self.driver.quit()
    #         assert True
    #     except Exception as e:
    #         pytest.fail(f"FAILED to close driver. Error, {str(e)}")
