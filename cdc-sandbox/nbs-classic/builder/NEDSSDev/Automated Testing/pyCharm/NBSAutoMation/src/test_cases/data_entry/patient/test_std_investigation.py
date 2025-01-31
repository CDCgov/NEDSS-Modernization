import os

import time
from datetime import datetime

import pytest
from pip._vendor.rich import print
from selenium.webdriver.common.by import By


from src.page_objects.BasePageObject import BasePageObject

from src.page_objects.data_entry.investigation import Investigation
from src.page_objects.data_entry.legacy_investigation import Legacyinvestigation
from src.page_objects.data_entry.patient import PatientObject
from src.page_objects.data_entry.std_investigation import stdInvestigation

from src.utilities.constants import NBSConstants
from src.utilities.db_utilities import DBUtilities
from src.utilities.log_config import LogUtils
from src.utilities.nbs_utilities import NBSUtilities


class TestSTDInvestigation:
    driver_globe = None
    logger = LogUtils.loggen(__name__)
    random_number = datetime.now().strftime("%d%m%y%H%M%S")
    random_number_for_patient = datetime.now().strftime("%m%d%y%H%M%S")
    logger.info(random_number)
    patient_search_submit_id = 'Submit'
    db_utilities = DBUtilities()
    repeatCount = NBSUtilities.generate_random_int(1)
    condition_1 = "Syphilis, primary"
    std_inv_referral_basis_value = "P3 - Partner, Both"
    std_processing_decision_value = "Field Follow-up"
    condition_2 = "Gonorrhea"
    gono_inv_referral_basis_value = "A1 - Associate 1"
    data_dic = {}




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
        self.std_investigation = stdInvestigation(self.driver)
        self.basePageObject = BasePageObject(self.driver)
        self.legacy_investigation = Legacyinvestigation(self.driver)
        self.driver.find_element(By.LINK_TEXT, "Data Entry").click()
        self.driver.find_element(By.LINK_TEXT, "Patient").click()
        # self.data_dic = {}
        time.sleep(1)


    # def search_patient_by_lastname(self):
    #     last_name = 'LastName-051024204711'
    #     self.driver.find_element(By.ID, "DEM102").send_keys(last_name)
    #     self.driver.find_element(By.NAME, "Submit").click()
    #     time.sleep(1)
    #     try:
    #         patient_elm_xpath = '//*[@id="searchResultsTable"]/tbody/tr/td[1]/a'
    #         self.driver.find_element(By.XPATH, patient_elm_xpath).click()
    #     except Exception as e:
    #         self.driver.find_element(By.NAME, "Submit").click()

    def add_new_patient(self):
        # self.patient = PatientObject(self.driver)
        self.patient.addNewPatient(self.random_number_for_patient)
        self.patient.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, 2)
        return True

    def test_add_std_investigation(self, setup):
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

            # self.search_patient_by_lastname()

            # search patient
            self.driver.find_element(By.ID, "DEM102").send_keys("last_name")
            self.driver.find_element(By.NAME, "Submit").click()
            time.sleep(1)
            self.add_new_patient()
            time.sleep(2)

            # click on event tab
            time.sleep(2)
            Flag = True
            self.std_investigation.std_header_home(self.condition_1, self.std_inv_referral_basis_value,
                                                   self.std_processing_decision_value, Flag)
            time.sleep(2)
            self.std_investigation.std_investigation_headers(Flag)
            # print("Completed condition {}".format(self.condition_1))
            self.logger.info("test_add_std_investigation:Syphilis, primary Investigation created successfully")
            time.sleep(2)

            self.validate_data(setup,Flag)
            self.driver.find_element(By.XPATH, '//*[@id="bd"]/div[1]/a').click()
            # print("Syphilis condition validation completed")
            assert True
            time.sleep(1)
            # adding Gonorrhea as co-infection to Syphilis, primary
            Flag = False
            self.std_investigation.std_header_home(self.condition_2, self.gono_inv_referral_basis_value, self.std_processing_decision_value,Flag)
            self.std_investigation.std_investigation_headers(Flag)
            # print("Completed condition {}".format(self.condition_2))
            self.logger.info("test_add_std_investigation:Gonorrhea as co-infection Investigation created successfully")
            time.sleep(1)
            
            # # ################################################ NEED TO REMOVE THIS BELOW CODE
            # self.driver.find_element(By.ID, "DEM102").send_keys("LastName-101524123814")
            # self.driver.find_element(By.NAME, "Submit").click()
            # self.driver.find_element(By.XPATH,'//*[@id="searchResultsTable"]/tbody/tr/td[1]/a').click()
            # self.driver.find_element(By.ID, "tabs0head1").click()
            
            # time.sleep(1)
            # # ###################################################################################
            self.driver.find_element(By.XPATH, "//*[@id='eventSumaryInv']/tbody/tr[2]/td[2]/a").click()
            self.driver.find_element(By.ID, "tabs0head5").click()
            time.sleep(2)
            self.std_investigation.add_interview()
            self.std_investigation.add_new_contact_record(self.random_number)
        
            self.validate_data(setup,Flag)
            print("Gonorrhea condition validation completed")
            assert True
        except Exception as e:
            # self.logger.info(f"Add Investigation Test Failed with {str(e)}")
            self.driver.save_screenshot(
                os.path.abspath(os.curdir) + "\\screenshots\\" + "test_add_Investigation.png")
            # self.driver.close()
            pytest.fail(f"add Investigation test FAILED")
      
    def validate_data(self, setup,Flag ):

        try:
            self.driver = setup
            # std_investigation_headers
            self.setup_and_patient_handle(setup)
            patient_lastname = NBSConstants.APPEND_TEST_FOR_LAST_NAME.__add__("-").__add__(
                str(self.random_number_for_patient))
            print("PATIENT LAST NAME", patient_lastname)
            # PatientName = f"Patient_{now}"
            i = 1
            self.driver.find_element(By.ID, "DEM102").send_keys(patient_lastname)
            self.driver.find_element(By.NAME, "Submit").click()
            patient_elm_xpath = '//*[@id="searchResultsTable"]/tbody/tr/td[1]/a'
            self.driver.find_element(By.XPATH, patient_elm_xpath).click()
            # click on event tab
            self.driver.find_element(By.ID, "tabs0head1").click()

            if Flag == True:
                self.driver.find_element(By.XPATH, "//*[@id='eventSumaryInv']/tbody/tr[1]/td[2]/a").click()
            else:
                # self.driver.find_element(By.XPATH, "//*[@id='eventSumaryInv']/tbody/tr[1]/td[2]/a").click()
                self.driver.find_element(By.XPATH, "//*[@id='eventSumaryInv']/tbody/tr[2]/td[2]/a").click()
            # element_text = self.driver.find_element(By.XPATH,'//*[@id="bd"]/h1/table/tbody/tr[1]/td[1]/a').text
            # print("element1", element_text)
            # if element_text in self.condition_1:
            self.driver.find_element(By.ID, "tabs0head1").click()
            time.sleep(1)
            self.std_investigation.verifyandvaildate("case info")
            self.driver.find_element(By.ID, "tabs0head2").click()
            time.sleep(1)
            self.std_investigation.verifyandvaildate("case management")
            self.driver.find_element(By.ID, "tabs0head3").click()
            time.sleep(1)
            self.std_investigation.verifyandvaildate("core info")
            self.driver.find_element(By.ID, "tabs0head4").click()
            time.sleep(1)
            self.std_investigation.verifyandvaildate("contract tracing")
            
            assert True
        except Exception as e:
            self.logger.info(f"Validate Data Test Failed with {str(e)}")
            self.driver.save_screenshot(
                os.path.abspath(os.curdir) + "\\screenshots\\" + "test_Validate.png")
            # self.driver.close()
            pytest.fail(f"Validate test FAILED")

    def test_edit_std_investigation(self, setup):
        try:
            self.driver = setup
            # std_investigation_headers
            self.setup_and_patient_handle(setup)
            patient_lastname = NBSConstants.APPEND_TEST_FOR_LAST_NAME.__add__("-").__add__(
                str(self.random_number_for_patient))
            print("PATIENT LAST NAME", patient_lastname)
            # PatientName = f"Patient_{now}"
            i = 1
            self.driver.find_element(By.ID, "DEM102").send_keys(patient_lastname)
            self.driver.find_element(By.NAME, "Submit").click()
            patient_elm_xpath = '//*[@id="searchResultsTable"]/tbody/tr/td[1]/a'
            self.driver.find_element(By.XPATH, patient_elm_xpath).click()
            # click on event tab
            self.driver.find_element(By.ID, "tabs0head1").click()
            # // *[ @ id = "eventSumaryInv"] / tbody / tr / td[2] / a
            self.driver.find_element(By.XPATH, "//*[@id='eventSumaryInv']/tbody/tr/td[2]/a").click()
            # click edit button, edit button ID is named as delete
            self.driver.find_element(By.ID, "delete").click()
            self.driver.find_element(By.ID, "tabs0head1").click()
            self.std_investigation.set_edit_case_info()

            self.driver.find_element(By.ID, "tabs0head2").click()
            self.std_investigation.set_edit_case_management()

            self.driver.find_element(By.ID, "tabs0head3").click()
            self.std_investigation.set_edit_core_info()

            self.driver.find_element(By.ID, "tabs0head4").click()
            self.std_investigation.set_contract_tracing_info()

            self.driver.find_element(By.XPATH, "//*[@id='SubmitTop']").click()
            self.logger.info("test_edit_std_investigation:Syphilis, primary Investigation Edited successfully")
            assert True
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
            patient_elm_xpath = '//*[@id="searchResultsTable"]/tbody/tr/td[1]/a'
            self.driver.find_element(By.XPATH, patient_elm_xpath).click()
            # click on event tab
            self.driver.find_element(By.ID, "tabs0head1").click()
            # // *[ @ id = "eventSumaryInv"] / tbody / tr / td[2] / a
            # click on investigation
            self.driver.find_element(By.XPATH, "//*[@id='eventSumaryInv']/tbody/tr/td[2]/a").click()
            self.driver.find_element(By.ID, "manageAssociations").click()
            # Add lab report in manage associations tab
            # self.driver.find_element(By.XPATH, "//*[@id='events1']/tbody/tr/td/div/input").click()
            #  ADD LABREPORT
            # self.investigation.setAddLabReport()
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
            # LastName-0412
            self.driver.find_element(By.ID, "DEM102").send_keys(patient_lastname)
            self.driver.find_element(By.NAME, "Submit").click()

            # patient results
            patient_elm_xpath = '//*[@id="searchResultsTable"]/tbody/tr/td[1]/a'
            self.driver.find_element(By.XPATH, patient_elm_xpath).click()
            # click on event tab
            self.driver.find_element(By.ID, "tabs0head1").click()
            # // *[ @ id = "eventSumaryInv"] / tbody / tr / td[2] / a
            # click on investigation
            self.driver.find_element(By.XPATH, "//*[@id='eventSumaryInv']/tbody/tr/td[2]/a").click()
            # Transfer ownership
            self.driver.find_element(By.XPATH, "//input[@title='Transfer Ownership']").click()
            self.std_investigation.switch_to_current_window()
            time.sleep(1)
            # TUB237
            self.std_investigation.set_jurisdiction_edit()
            time.sleep(3)
            self.logger.info("test_transfer_owner: Transfer Owner test successful")
            assert True
        except Exception as e:
            self.logger.info(f"Transfer Owner Test Failed with {str(e)}")
            self.driver.save_screenshot(
                os.path.abspath(os.curdir) + "\\screenshots\\" + "test_share_document.png")
            # self.driver.close()
            pytest.fail(f"Transfer Owner Test FAILED")

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
            patient_elm_xpath = '//*[@id="searchResultsTable"]/tbody/tr/td[1]/a'
            self.driver.find_element(By.XPATH, patient_elm_xpath).click()
            # click on event tab
            self.driver.find_element(By.ID, "tabs0head1").click()
            # // *[ @ id = "eventSumaryInv"] / tbody / tr / td[2] / a
            # click on investigation
            self.driver.find_element(By.XPATH, "//*[@id='eventSumaryInv']/tbody/tr/td[2]/a").click()
            # id=caseRep for share document
            self.driver.find_element(By.ID, "caseRep").click()
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
            patient_elm_xpath = '//*[@id="searchResultsTable"]/tbody/tr/td[1]/a'
            self.driver.find_element(By.XPATH, patient_elm_xpath).click()
            # click on event tab
            self.driver.find_element(By.ID, "tabs0head1").click()
            # // *[ @ id = "eventSumaryInv"] / tbody / tr / td[2] / a
            # click on investigation
            self.driver.find_element(By.XPATH, "//*[@id='eventSumaryInv']/tbody/tr/td[2]/a").click()
            self.driver.find_element(By.ID, "print").click()
            time.sleep(2)
            # self.switch_to_current_window()
            # self.driver.quit()
            self.std_investigation.switch_to_original_window()
            self.logger.info("print_investigation: print_investigation test successful")
            # self.driver.quit()
            # self.driver.close()
            assert True
        except Exception as e:
            self.logger.info(f"print_investigation Test Failed with {str(e)}")
            self.driver.save_screenshot(
                os.path.abspath(os.curdir) + "\\screenshots\\" + "test_print_investigation.png")
            self.driver.close()
            pytest.fail(f"print_investigation test FAILED")

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
            patient_elm_xpath = '//*[@id="searchResultsTable"]/tbody/tr/td[1]/a'
            self.driver.find_element(By.XPATH, patient_elm_xpath).click()
            # click on event tab
            self.driver.find_element(By.ID, "tabs0head1").click()
            # // *[ @ id = "eventSumaryInv"] / tbody / tr / td[2] / a
            # click on investigation
            self.driver.find_element(By.XPATH, "//*[@id='eventSumaryInv']/tbody/tr/td[2]/a").click()
            # delete investigation
            self.driver.find_element(By.XPATH, "//input[@id='delete' and contains(@title, 'Delete')]").click()
            self.driver.switch_to.alert.accept()
            time.sleep(1)
            self.driver.switch_to.alert.accept()
            self.driver.find_element(By.ID, "manageAssociations").click()
            self.driver.switch_to.alert.accept()
            self.driver.find_element(By.XPATH,'//*[@id="lablist"]/tbody/tr/td[1]/div/input').click()
            self.driver.find_element(By.XPATH, '//*[@id="vaccilist"]/tbody/tr/td[1]/div/input').click()
            self.driver.find_element(By.ID, "Submit").click()
            # delete investigation
            self.driver.find_element(By.XPATH, "//input[@id='delete' and contains(@title, 'Delete')]").click()

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
            self.driver.close()
            pytest.fail(f"test_Delete_Investigation test FAILED")
