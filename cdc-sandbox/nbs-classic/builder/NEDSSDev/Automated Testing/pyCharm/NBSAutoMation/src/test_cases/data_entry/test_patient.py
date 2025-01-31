import os
import time

import pytest
from datetime import datetime
from src.page_objects.data_entry.patient import PatientObject
from src.page_objects.login.login import LoginPage
from src.utilities.constants import NBSConstants
from src.utilities.db_utilities import DBUtilities
from src.utilities.log_config import LogUtils
from src.utilities.nbs_utilities import NBSUtilities

class TestPatient:
    random_number_for_patient = datetime.now().strftime("%m%d%y%H%M%S")
    random_number_for_patient_extended = None
    logger = LogUtils.loggen(__name__)
    patient_uid = None
    patient_uid_extended = None
    testcase_start_time = None
    db_utilities = DBUtilities()

    @pytest.mark.run(order=1)
    def test_add_new_patient(self, setup):
        TestPatient.testcase_start_time = int(time.time() * 1000)
        self.driver = setup
        self.patient = PatientObject(self.driver)
        if NBSUtilities.performLoginValidation(self.driver):  # Login Validation
            if self.navigateToPatientSearch(self.driver, self.patient, True, TestPatient.random_number_for_patient):
                if self.patient.addNewPatient(TestPatient.random_number_for_patient):
                    self.logger.info(
                        "*test_add_new_patient:NBS Add New Patient Created Test PASSED*")
                    assert True
                    try:
                        # get values related to created patient for extended screen from DB
                        TestPatient.patient_uid = self.db_utilities.getProviderUniqueId(
                            "LastName-".__add__(str(TestPatient.random_number_for_patient)),
                            "FirstName-".__add__(str(TestPatient.random_number_for_patient))
                        )

                        self.logger.info("TestPatient.provider_uid:::" + str(TestPatient.patient_uid))
                    except Exception as ex:
                        self.logger.debug("Exception while getting patient id from database"
                                          " in test_add_new_patient", ex)
                else:
                    self.logger.info(
                        "**test_add_new_patient:NBS Add New Patient Created Test FAILED**")
                    self.driver.save_screenshot(
                        os.path.abspath(os.curdir) + "\\screenshots\\" + "add_patient.png")
                    self.driver.close()
                    pytest.fail(f"Add Patient Created Test FAILED")
        self.patient.performButtonClick(NBSConstants.TYPE_CODE_LINK_TEXT, NBSConstants.NBS_HOME_PAGE)
        self.patient.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, 2)

    @pytest.mark.run(order=5)
    def test_add_patient_by_extended_screen(self, setup):
        self.driver = setup
        self.patient = PatientObject(self.driver)
        TestPatient.random_number_for_patient_extended = datetime.now().strftime("%m%d%y%H%M%S")
        self.logger.info(TestPatient.random_number_for_patient_extended)
        if NBSUtilities.performLoginValidation(self.driver):  # Login Validation
            if self.navigateToPatientSearch(self.driver, self.patient, False,
                                            TestPatient.random_number_for_patient_extended):
                if self.patient.addNewPatientByExtendedScreen(TestPatient.random_number_for_patient_extended):
                    self.logger.info(
                        "*test_add_patient_by_extended_screen:NBS Add New Patient Created Test "
                        "by extended screen PASSED*")
                    assert True
                else:
                    self.logger.info(
                        "**test_add_patient_by_extended_screen:NBS Add New Patient Created Test "
                        "by extended screen FAILED**")
                    self.driver.save_screenshot(
                        os.path.abspath(os.curdir) + "\\screenshots\\" + "add_patient_extended.png")
                    self.driver.close()
                    pytest.fail(f"Add Patient Created by extended  Test FAILED")

        self.patient.performButtonClick(NBSConstants.TYPE_CODE_LINK_TEXT, NBSConstants.NBS_HOME_PAGE)
        self.patient.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, 2)
        self.lp = LoginPage(self.driver)
        self.patient.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                NBSConstants.WAITING_TIME_IN_SECONDS)
        self.lp.logoutFromNBSApplication()
        self.patient.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                NBSConstants.WAITING_TIME_IN_SECONDS)
        testcase_end_time = int(time.time() * 1000)
        execution_time_for_all = testcase_end_time - TestPatient.testcase_start_time
        self.logger.info("Execution Time::" + str(NBSUtilities.convertMillis(execution_time_for_all)))
        self.driver.close()
        self.driver.quit()

    @pytest.mark.run(order=2)
    def test_edit_patient_demographics(self, setup):
        self.driver = setup
        self.patient = PatientObject(self.driver)
        if NBSUtilities.performLoginValidation(self.driver):  # Login Validation
            if self.navigateToPatientProfile(self.driver, self.patient, TestPatient.patient_uid,
                                             TestPatient.random_number_for_patient):
                if self.patient.editPatientFromDemographicsSection(TestPatient.random_number_for_patient):
                    self.logger.info(
                        "*test_edit_patient_demographics:Edit Patient from Patient Profile Test "
                        " PASSED*")
                    assert True
                else:
                    self.logger.info(
                        "**test_edit_patient_demographics:Edit Patient from Patient Profile TEST FAILED**")
                    self.driver.save_screenshot(
                        os.path.abspath(os.curdir) + "\\screenshots\\" + "edit_patient_extended.png")
                    self.driver.close()
                    pytest.fail(f"edit Patient Test FAILED")

    @pytest.mark.run(order=3)
    def test_patient_search_and_results_page(self, setup):
        self.driver = setup
        self.patient = PatientObject(self.driver)
        if NBSUtilities.performLoginValidation(self.driver):  # Login Validation
            self.patient.performButtonClick(NBSConstants.TYPE_CODE_LINK_TEXT,
                                            PatientObject.nbs_data_entry_menu)
            self.patient.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                    NBSConstants.WAITING_TIME_IN_SECONDS)
            self.patient.performButtonClick(NBSConstants.TYPE_CODE_LINK_TEXT,
                                            PatientObject.nbs_data_entry_patient_menu)
            self.patient.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                    NBSConstants.WAITING_TIME_IN_SECONDS)
            if self.driver.title == PatientObject.patient_search_page_title:
                # search by last name
                if self.patient.searchPatient(NBSConstants.TYPE_CODE_NAME,
                                              PatientObject.patient_search_by_last_name,
                                              NBSConstants.APPEND_TEST_FOR_LAST_NAME,
                                              TestPatient.random_number_for_patient,
                                              TestPatient.patient_uid):
                    self.logger.info("*test_patient_search_and_results_page:Patient Search By Last Name PASSED*")
                    assert True
                else:
                    self.driver.close()
                    self.logger.info("*test_patient_search_and_results_page:Patient Search By Last Name FAILED**")
                    self.driver.save_screenshot(
                        os.path.abspath(os.curdir) + "\\screenshots\\" + "patient_search_by_ln.png")
                    pytest.fail(f"Patient Search By Last Name FAILED")

                # search by first name
                if self.patient.searchPatient(NBSConstants.TYPE_CODE_NAME,
                                              PatientObject.patient_search_by_first_name,
                                              NBSConstants.APPEND_TEST_FOR_FIRST_NAME,
                                              TestPatient.random_number_for_patient,
                                              TestPatient.patient_uid):
                    self.logger.info("*test_patient_search_and_results_page:Patient Search By First Name PASSED*")
                    assert True
                else:
                    self.driver.close()
                    self.logger.info("*test_patient_search_and_results_page:Patient Search By First Name FAILED**")
                    self.driver.save_screenshot(
                        os.path.abspath(os.curdir) + "\\screenshots\\" + "patient_search_by_fn.png")
                    pytest.fail(f"Patient Search By Last Name FAILED")
                # search by date of birth
                if self.patient.searchPatient(NBSConstants.TYPE_CODE_ID,
                                              PatientObject.add_patient_date_of_birth,
                                              PatientObject.add_patient_date_of_birth,
                                              TestPatient.random_number_for_patient,
                                              TestPatient.patient_uid):
                    self.logger.info("*test_patient_search_and_results_page:Patient Search By Date of Birth PASSED*")
                    assert True
                else:
                    self.driver.close()
                    self.logger.info("*test_patient_search_and_results_page:Patient Search By Date of Birth FAILED**")
                    self.driver.save_screenshot(
                        os.path.abspath(os.curdir) + "\\screenshots\\" + "patient_search_by_db.png")
                    pytest.fail(f"Patient Search By Last Name FAILED")

                # search by patient id
                if self.patient.searchPatient(NBSConstants.TYPE_CODE_NAME,
                                              PatientObject.patient_search_by_patient_id,
                                              None,
                                              None,
                                              TestPatient.patient_uid):
                    self.logger.info("*test_patient_search_and_results_page:Patient Search By Patient ID PASSED*")
                    assert True
                else:
                    self.driver.close()
                    self.logger.info("*test_patient_search_and_results_page:Patient Search By Patient ID FAILED**")
                    self.driver.save_screenshot(
                        os.path.abspath(os.curdir) + "\\screenshots\\" + "patient_search_by_id.png")
                    pytest.fail(f"Patient Search By Last Name FAILED")

                # search by phone number
                if self.patient.searchPatient(NBSConstants.TYPE_CODE_NAME,
                                              PatientObject.patient_search_by_phone_number,
                                              "telephone",
                                              TestPatient.random_number_for_patient,
                                              TestPatient.patient_uid):
                    self.logger.info(
                        "*test_patient_search_and_results_page:Patient Search By Phone Number PASSED*")
                    assert True
                else:
                    self.logger.info(
                        "*test_patient_search_and_results_page:Patient Search By Phone Number FAILED**")
                    self.driver.save_screenshot(
                        os.path.abspath(os.curdir) + "\\screenshots\\" + "patient_search_by_phone.png")
                    pytest.fail(f"Patient Search By Last Name FAILED")

                # search by email
                """if self.patient.searchPatient(NBSConstants.TYPE_CODE_NAME,
                                              DataEntryLocators.PATIENT_SEARCH_BY_EMAIL,
                                              "email",
                                              TestPatient.random_number_for_patient,
                                              TestPatient.patient_uid):
                    self.logger.info(
                        "*test_patient_search_and_results_page:Patient Search By Date of Birth PASSED*")
                    assert True
                else:
                    self.logger.info(
                        "*test_patient_search_and_results_page:Patient Search By Date of Birth FAILED**")
                    self.driver.save_screenshot(
                        os.path.abspath(os.curdir) + "\\screenshots\\" + "patient_search_by_db.png")
                    pytest.fail(f"Patient Search By Last Name FAILED")"""

                # find specific patient with wildcard search validation
                if self.patient.searchPatient(NBSConstants.TYPE_CODE_NAME,
                                              PatientObject.patient_search_by_last_name,
                                              NBSConstants.ADD_PATIENT_LAST_NAME_WILD,
                                              None,
                                              TestPatient.patient_uid):
                    self.logger.info(
                        "*test_patient_search_and_results_page:Patient wildcard Search By last name PASSED*")
                    assert True
                else:
                    self.driver.close()
                    self.logger.info(
                        "*test_patient_search_and_results_page:Patient wildcard Search By last name FAILED**")
                    self.driver.save_screenshot(
                        os.path.abspath(os.curdir) + "\\screenshots\\" + "prov_search_by_wild_ln.png")
                    pytest.fail(f"Patient wildcard Search By last name FAILED")

                # pagination validation
                if self.patient.validatePaginationOnResults():
                    self.logger.info(
                            "*test_patient_search_and_results_page:Patient Search Results Pagination PASSED*")
                    assert True
                else:
                    self.driver.close()
                    self.logger.info(
                            "*test_patient_search_and_results_page:Patient Search Results Pagination FAILED**")
                    self.driver.save_screenshot(
                            os.path.abspath(os.curdir) + "\\screenshots\\" + "prov_search_by_pagination.png")
                    pytest.fail(f"Patient Search Results Pagination FAILED")

                # Validate Refine search on provider search results page
                if self.patient.performRefineSearchValidation(TestPatient.random_number_for_patient,
                                                              TestPatient.patient_uid):
                    self.logger.info(
                        "*test_patient_search_and_results_page:Patient Refine Search By all fields PASSED*")
                    assert True
                else:
                    self.driver.close()
                    self.logger.info(
                        "*test_patient_search_and_results_page:Patient Refine Search By all fields FAILED**")
                    self.driver.save_screenshot(
                        os.path.abspath(os.curdir) + "\\screenshots\\" + "patient_refine_search.png")
                    pytest.fail(f"Patient wildcard Search By all fields FAILED")

            self.patient.performButtonClick(NBSConstants.TYPE_CODE_LINK_TEXT, NBSConstants.NBS_HOME_PAGE)
            self.patient.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, 2)

    @pytest.mark.run(order=4)
    def test_view_and_delete_patient_profile(self, setup):
        self.driver = setup
        self.patient = PatientObject(self.driver)
        if NBSUtilities.performLoginValidation(self.driver):  # Login Validation
            if self.navigateToPatientProfile(self.driver, self.patient,
                                             TestPatient.patient_uid,
                                             TestPatient.random_number_for_patient):
                if self.patient.verifyLinksWithDeleteValidation():
                    self.logger.info("*test_view_and_delete_patient_profile:View and Delete Patient "
                                     "from Patient Profile Test PASSED**")
                    assert True
                else:
                    self.logger.info(
                        "**test_view_and_delete_patient_profile:View and Delete Patient "
                        "from Patient Profile TEST FAILED**")
                    self.driver.save_screenshot(
                        os.path.abspath(os.curdir) + "\\screenshots\\" + "view_delete_patient_profile.png")
                    self.driver.close()
                    pytest.fail(f"View Patient Test FAILED")

                self.patient.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                        NBSConstants.WAITING_TIME_IN_SECONDS)
            self.navigateToPatientSearch(self.patient, self.driver, False,
                                         TestPatient.random_number_for_patient)
            try:
                element = self.patient.findElement(NBSConstants.TYPE_CODE_XPATH,
                                                   "//a[contains(@href, "
                                                   "'uid=".__add__(str(TestPatient.patient_uid)) + "')]")
                if element is not None:
                    self.logger.info(
                        "*test_view_and_delete_patient_profile:Patient is not Deleted**")
                    self.driver.save_screenshot(
                        os.path.abspath(os.curdir) + "\\screenshots\\" + "patient_delete.png")
                    pytest.fail(f"Patient Delete FAILED!!!!")
            except:
                pass
        self.patient.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                NBSConstants.WAITING_TIME_IN_SECONDS)
        self.patient.performButtonClick(NBSConstants.TYPE_CODE_LINK_TEXT, NBSConstants.NBS_HOME_PAGE)
        self.patient.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)

    def navigateToPatientProfile(self, patient, driver, uid, random_number):
        try:
            if self.navigateToPatientSearch(patient, driver, False, random_number):
                self.patient.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                                "//a[contains(@href, 'uid=".__add__(str(uid)) + "')]")

        except Exception as ex:
            self.logger.debug("Could Not Found The Specified Record", ex)

        self.patient.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                NBSConstants.WAITING_TIME_IN_SECONDS)
        return True

    def navigateToPatientSearch(self, driver, patient, display_success, random_number):
        self.patient.performButtonClick(NBSConstants.TYPE_CODE_LINK_TEXT,
                                        PatientObject.nbs_data_entry_menu)
        self.patient.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                NBSConstants.WAITING_TIME_IN_SECONDS)
        self.patient.performButtonClick(NBSConstants.TYPE_CODE_LINK_TEXT,
                                        PatientObject.nbs_data_entry_patient_menu)
        self.patient.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                NBSConstants.WAITING_TIME_IN_SECONDS)
        if self.driver.title == PatientObject.patient_search_page_title:
            if display_success:
                self.logger.info("**navigateToPatientSearch:NBS Patient Search Landing Page PASSED**")
                assert True

            self.patient.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT, NBSConstants.TYPE_CODE_NAME,
                                                PatientObject.patient_search_by_last_name,
                                                NBSConstants.APPEND_TEST_FOR_LAST_NAME.__add__("-").__add__(
                                                    str(random_number)))
            self.patient.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                    NBSConstants.WAITING_TIME_IN_SECONDS)
            self.patient.performButtonClick(NBSConstants.TYPE_CODE_NAME, PatientObject.patient_search_submit_id)
            self.patient.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                    NBSConstants.WAITING_TIME_IN_SECONDS)
        else:
            self.logger.info("**navigateToProviderLandingPage:NBS Patient Search Landing Page FAILED**")
            driver.close()
            pytest.fail("navigateToProviderLandingPage:NBS Patient Search Landing Page test failed.")

        return True
