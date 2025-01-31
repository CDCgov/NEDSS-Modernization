import os
import time
from datetime import datetime

import pytest

from src.page_objects.data_entry.labreport import LabReportObject
from src.page_objects.data_entry.patient import PatientObject
from src.page_objects.login.login import LoginPage
from src.utilities.constants import NBSConstants
from src.utilities.db_utilities import DBUtilities
from src.utilities.log_config import LogUtils
from src.utilities.nbs_utilities import NBSUtilities

class TestLabReport:
    testcase_start_time = int(time.time() * 1000)
    random_number_for_patient = datetime.now().strftime("%m%d%y%H%M%S")
    patient_uid = None
    observation_uid = None
    logger = LogUtils.loggen(__name__)
    db_utilities = DBUtilities()

    @pytest.mark.run(order=1)
    def test_add_new_lab_report_by_patient_search(self, setup):
        self.driver = setup
        self.lab_report = LabReportObject(self.driver)
        self.patient = PatientObject(self.driver)
        if NBSUtilities.performLoginValidation(self.driver):  # Login Validation
            if self.navigateToPatientSearch(self.driver, self.lab_report, True,
                                            TestLabReport.random_number_for_patient):
                if self.patient.addNewPatient(
                        TestLabReport.random_number_for_patient):
                    self.logger.info(
                        "*test_add_new_lab_report_by_patient_search: Add New Patient Created For Lab Test PASSED*")
                    assert True
                    try:
                        # get values related to created patient for extended screen from DB
                        TestLabReport.patient_uid = self.db_utilities.getProviderUniqueId(
                            "LastName-".__add__(str(TestLabReport.random_number_for_patient)),
                            "FirstName-".__add__(str(TestLabReport.random_number_for_patient))
                        )
                        # self.logger.info("TestPatient.provider_uid:::" + str(TestLabReport.patient_uid))
                    except Exception as ex:
                        self.logger.debug("Exception while getting patient id from database"
                                          " in test_add_new_patient", ex)
                else:
                    self.logger.info(
                        "**test_add_new_lab_report_by_patient_search:NBS Add New Patient Created for Lab Test FAILED**")
                    self.driver.save_screenshot(
                        os.path.abspath(os.curdir) + "\\screenshots\\" + "Lab_patient.png")
                    self.driver.close()
                    pytest.fail(f"Add Patient Created For Lab Test FAILED")
        self.lab_report.performButtonClick(NBSConstants.TYPE_CODE_LINK_TEXT, NBSConstants.NBS_HOME_PAGE)
        self.lab_report.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                   NBSConstants.WAITING_TIME_IN_SECONDS)
        self.lab_report.performButtonClick(NBSConstants.TYPE_CODE_LINK_TEXT,
                                           NBSConstants.NBS_DATA_ENTRY_MENU)
        self.lab_report.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                   NBSConstants.WAITING_TIME_IN_SECONDS)
        self.lab_report.performButtonClick(NBSConstants.TYPE_CODE_LINK_TEXT,
                                           LabReportObject.nbs_data_entry_lab_report_menu)
        self.lab_report.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                   NBSConstants.WAITING_TIME_IN_SECONDS)

        if self.lab_report.addLabReportForPatient(TestLabReport.random_number_for_patient,
                                                  TestLabReport.patient_uid):
            self.logger.info(
                "*test_add_new_lab_report_by_patient_search: Add New Lab Report for Patient Test PASSED*")
            try:
                TestLabReport.observation_uid = self.db_utilities.getObservationUid(
                    "LastName-" + str(TestLabReport.random_number_for_patient),
                    "FirstName-" + str(TestLabReport.random_number_for_patient)
                )
                self.logger.info("Lab Observation Id::" + str(TestLabReport.observation_uid))
                if TestLabReport.observation_uid is not None:
                    # Successful Submit focus goes to Patient Tab
                    first_name_ele = self.lab_report.findElement(NBSConstants.TYPE_CODE_ID, "DEM104")
                    if first_name_ele is not None and first_name_ele.get_attribute("value") is not None:
                        self.logger.info(
                            "*test_add_new_lab_report_by_patient_search: Keeping Patient Information after lab report "
                            "created "
                            "PASSED*")
                    self.lab_report.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                               NBSConstants.WAITING_TIME_IN_SECONDS)
                    # clear the patient info
                    self.lab_report.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                                       "//*[@id='NBS_UI_27']/tbody/tr/td/input[2]")
                    last_name_ele = self.lab_report.findElement(NBSConstants.TYPE_CODE_ID, "DEM102")
                    last_name_value = first_name_ele.get_attribute("value")
                    if last_name_ele is not None and not last_name_value:
                        self.logger.info(
                            "*test_add_new_lab_report_by_patient_search: Clearing Patient Information after lab "
                            "report created "
                            "PASSED*")
                    self.lab_report.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                               NBSConstants.WAITING_TIME_IN_SECONDS)
                    # Retain Reporting Test Facility
                    # Lab Report Tab
                    self.lab_report.performButtonClick(NBSConstants.TYPE_CODE_ID,
                                                       "tabs0head1")
                    reporting_facility_ele = self.lab_report.findElement(NBSConstants.TYPE_CODE_ID, "NBS_LAB365")
                    reporting_facility_value = reporting_facility_ele.get_attribute("innerHTML")
                    if reporting_facility_ele is not None and reporting_facility_value is not None:
                        self.logger.info(
                            "*test_add_new_lab_report_by_patient_search: Keeping Reporting Facility Info after lab "
                            "report "
                            "created PASSED*")
                    self.lab_report.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                               NBSConstants.WAITING_TIME_IN_SECONDS)
                    # Check Same as Reporting Facility
                    if LabReportObject.facility_checked:
                        self.logger.info(
                            "*test_add_new_lab_report_by_patient_search: Reporting Facility and Ordering Facility "
                            " both are same - SAME AS REPORTING FACILITY "
                            " Test PASSED*")
                    assert True
            except Exception as ex:
                self.logger.debug("Exception while getting observation id from database"
                                  " in test_add_new_patient", ex)

        else:
            self.logger.info(
                "**test_add_new_lab_report_by_patient_search:Add New Lab Report for Patient Test FAILED**")
            self.driver.save_screenshot(
                os.path.abspath(os.curdir) + "\\screenshots\\" + "add_lab_report.png")
            self.driver.close()
        if NBSConstants.LOG_OUT_FROM_PATIENT_SCREEN == "YES":
            self.lp = LoginPage(self.driver)
            self.lab_report.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                       NBSConstants.WAITING_TIME_IN_SECONDS)
            self.lp.logoutFromNBSApplication()
            self.lab_report.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                       NBSConstants.WAITING_TIME_IN_SECONDS)
            testcase_end_time = int(time.time() * 1000)
            execution_time_for_all = testcase_end_time - TestLabReport.testcase_start_time
            self.logger.info("Execution Time::" + str(NBSUtilities.convertMillis(execution_time_for_all)))
            self.driver.close()
            self.driver.quit()

    def navigateToPatientSearch(self, driver, lab_report, display_success, random_number):
        self.lab_report.performButtonClick(NBSConstants.TYPE_CODE_LINK_TEXT,
                                           NBSConstants.NBS_DATA_ENTRY_MENU)
        self.lab_report.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                   NBSConstants.WAITING_TIME_IN_SECONDS)
        self.lab_report.performButtonClick(NBSConstants.TYPE_CODE_LINK_TEXT,
                                           LabReportObject.nbs_data_entry_patient_menu)
        self.lab_report.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                   NBSConstants.WAITING_TIME_IN_SECONDS)

        if self.driver.title.strip() == LabReportObject.patient_search_page_title.strip():
            if display_success:
                self.logger.info("**navigateToPatientSearch:NBS Patient Search Landing Page PASSED**")
                assert True

            self.lab_report.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT, NBSConstants.TYPE_CODE_NAME,
                                                   LabReportObject.patient_search_by_last_name,
                                                   "LastName-" + str(random_number))
            self.lab_report.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                       NBSConstants.WAITING_TIME_IN_SECONDS)
            self.lab_report.performButtonClick(NBSConstants.TYPE_CODE_NAME, LabReportObject.patient_search_submit_id)
            self.lab_report.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                       NBSConstants.WAITING_TIME_IN_SECONDS)
        else:
            self.logger.info("**navigateToProviderLandingPage:NBS Patient Search Landing Page FAILED**")
            driver.close()
            pytest.fail("navigateToProviderLandingPage:NBS Patient Search Landing Page test failed.")

        return True

    @pytest.mark.run(order=2)
    def test_add_new_patient_and_lab_report_same_screen(self, setup):
        self.driver = setup
        self.lab_report = LabReportObject(self.driver)
        random_number_for_patient_same = datetime.now().strftime("%m%d%y%H%M%S")
        if NBSUtilities.performLoginValidation(self.driver):  # Login Validation
            self.lab_report.performButtonClick(NBSConstants.TYPE_CODE_LINK_TEXT,
                                               NBSConstants.NBS_DATA_ENTRY_MENU)
            self.lab_report.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                       NBSConstants.WAITING_TIME_IN_SECONDS)
            self.lab_report.performButtonClick(NBSConstants.TYPE_CODE_LINK_TEXT,
                                               LabReportObject.nbs_data_entry_lab_report_menu)
            self.lab_report.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                       NBSConstants.WAITING_TIME_IN_SECONDS)
            if self.lab_report.createPatientAndLabReport(random_number_for_patient_same):
                self.logger.info(
                    "*test_add_new_patient_and_lab_report_same_screen: Create Patient and Lab Report Test PASSED*")
                assert True
                try:
                    observation_id = self.db_utilities.getObservationUid(
                        "LastName-" + str(random_number_for_patient_same),
                        "FirstName-" + str(random_number_for_patient_same)
                    )
                    self.logger.info("Lab Observation Id::" + str(observation_id))
                except Exception as ex:
                    self.logger.debug("Exception while getting observation id from database"
                                      " in test_add_new_patient", ex)

            else:
                self.logger.info(
                    "**test_add_new_patient_and_lab_report_same_screen:Create Patient and LabReport Test FAILED**")
                self.driver.save_screenshot(
                    os.path.abspath(os.curdir) + "\\screenshots\\" + "create_lab_report.png")
                self.driver.close()
            if NBSConstants.LOG_OUT_FROM_PATIENT_SCREEN == "YES":
                self.lp = LoginPage(self.driver)
                self.lab_report.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                           NBSConstants.WAITING_TIME_IN_SECONDS)
                self.lp.logoutFromNBSApplication()
                self.lab_report.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                           NBSConstants.WAITING_TIME_IN_SECONDS)
                testcase_end_time = int(time.time() * 1000)
                execution_time_for_all = testcase_end_time - TestLabReport.testcase_start_time
                self.logger.info("Execution Time::" + str(NBSUtilities.convertMillis(execution_time_for_all)))
                self.driver.close()
                self.driver.quit()

    @staticmethod
    def isPatientProfileOpen(lab_report):
        return False

    @pytest.mark.run(order=3)
    def test_verify_all_links_at_patient_and_lab_report_tabs(self, setup):
        self.logger.info("TestLabReport.observation_uid:::" + str(TestLabReport.observation_uid))
        self.driver = setup
        self.lab_report = LabReportObject(self.driver)
        if not TestLabReport.isPatientProfileOpen(self.lab_report):
            if NBSUtilities.performLoginValidation(self.driver):  # Login Validation
                if self.navigateToPatientSearch(self.driver, self.lab_report, False,
                                                TestLabReport.random_number_for_patient):
                    patient_id = self.lab_report.getPatienetIdFromSystem(TestLabReport.patient_uid)
                    self.logger.info(patient_id)
                    self.lab_report.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                               NBSConstants.WAITING_TIME_IN_SECONDS)
                    self.lab_report.performButtonClick(NBSConstants.TYPE_CODE_LINK_TEXT,
                                                       str(patient_id))  # open patient profile
        if self.lab_report.verifyAllLinksInLabReport(TestLabReport.observation_uid):
            self.logger.info(
                "*test_verify_all_links_at_patient_and_lab_report_tabs: Verify All Links Test PASSED*")
            assert True
        else:
            self.logger.info(
                "**test_add_new_patient_and_lab_report_same_screen:Verify All Links Test FAILED**")
            self.driver.save_screenshot(
                os.path.abspath(os.curdir) + "\\screenshots\\" + "verify_lab_links.png")
            self.driver.close()

        self.lp = LoginPage(self.driver)
        self.lab_report.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                   NBSConstants.WAITING_TIME_IN_SECONDS)
        self.lp.logoutFromNBSApplication()
        self.lab_report.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                   NBSConstants.WAITING_TIME_IN_SECONDS)
        testcase_end_time = int(time.time() * 1000)
        execution_time_for_all = testcase_end_time - TestLabReport.testcase_start_time
        self.logger.info("Execution Time::" + str(NBSUtilities.convertMillis(execution_time_for_all)))
        self.driver.close()
        self.driver.quit()
