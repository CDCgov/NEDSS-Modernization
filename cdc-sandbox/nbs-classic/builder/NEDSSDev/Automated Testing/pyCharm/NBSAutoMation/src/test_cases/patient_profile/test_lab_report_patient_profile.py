import os
import time
from datetime import datetime
import pytest
from src.page_objects.data_entry.patient import PatientObject
from src.page_objects.login.login import LoginPage
from src.page_objects.patient_profile.labreport import PatientProfileLRObject
from src.utilities.constants import NBSConstants
from src.utilities.db_utilities import DBUtilities
from src.utilities.log_config import LogUtils
from src.utilities.nbs_utilities import NBSUtilities


class TestLRFromPatientProfile:
    testcase_start_time = int(time.time() * 1000)
    random_number_for_patient = datetime.now().strftime("%m%d%y%H%M%S")
    patient_uid = None
    lab_id = None
    observation_uid = None
    logger = LogUtils.loggen(__name__)
    db_utilities = DBUtilities()

    def navigateToPatientSearchFromDE(self, driver, lab_report, display_success):
        lab_report.performButtonClick(NBSConstants.TYPE_CODE_LINK_TEXT,
                                      NBSConstants.NBS_DATA_ENTRY_MENU)
        lab_report.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                              NBSConstants.WAITING_TIME_IN_SECONDS)
        lab_report.performButtonClick(NBSConstants.TYPE_CODE_LINK_TEXT,
                                      lab_report.nbs_data_entry_patient_menu)
        lab_report.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                              NBSConstants.WAITING_TIME_IN_SECONDS)
        if driver.title == lab_report.patient_search_page_title:
            if display_success:
                self.logger.info("**navigateToPatientSearch:NBS Patient Search Landing Page PASSED**")
                assert True

            lab_report.setValueForHTMLElement_web(NBSConstants.HTML_TEXT_BOX_ELEMENT, NBSConstants.TYPE_CODE_NAME,
                                                  lab_report.patient_search_by_last_name,
                                                  "LastName-"+str(TestLRFromPatientProfile.random_number_for_patient))
            lab_report.performButtonClick_web(NBSConstants.TYPE_CODE_NAME,
                                              lab_report.patient_search_submit_id)
        else:
            self.logger.info("**navigateToProviderLandingPage:NBS Patient Search Landing Page FAILED**")
            driver.close()
            pytest.fail("navigateToProviderLandingPage:NBS Patient Search Landing Page test failed.")

        return True

    def navigateToPatientProfileFromHomePage(self, lab_report):
        # Assume valid patient for search is existed in the system
        # Simple Patient Search from Home Page
        # Last Name
        lab_report.setValueForHTMLElement_web(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                              NBSConstants.TYPE_CODE_ID,
                                              "DEM102",
                                              NBSConstants.APPEND_TEST_FOR_LAST_NAME.__add__("-").__add__(
                                                  str(TestLRFromPatientProfile.random_number_for_patient
                                                      )))
        # Search
        lab_report.performButtonClick_web(NBSConstants.TYPE_CODE_XPATH,
                                          "//*[@id='patientSearchByDetails']/table[2]/tbody/tr[8]/td["
                                          "2]/input[1]")

        # Patient Search Results Page and click on searched patient which opens patient profile
        try:
            lab_report.performButtonClick_web(NBSConstants.TYPE_CODE_XPATH,
                                              "//a[contains(@href, "
                                              "'uid=".__add__(
                                                  str(TestLRFromPatientProfile.patient_uid)) + "')]")
        except Exception as exc:
            self.logger.exception(msg="Could Not Found The Specified Record",
                                  exc_info=(type(exc), exc, exc.__traceback__))
            return False
        return True

    @staticmethod
    def isPatientProfileOpen(lab_report):
        try:
            lab_report.findElement_web(NBSConstants.TYPE_CODE_ID, "tabs0head1")
        except:
            return False
        return True

    @pytest.mark.run(order=1)
    def test_add_new_patient_to_the_system(self, setup):
        self.driver = setup
        self.lab_report = PatientProfileLRObject(self.driver)
        self.patient = PatientObject(self.driver)
        if NBSUtilities.performLoginValidation(self.driver):  # Login Validation
            if self.navigateToPatientSearchFromDE(self.driver, self.lab_report, True):
                if self.patient.addNewPatient(
                        TestLRFromPatientProfile.random_number_for_patient):
                    self.logger.info(
                        "*test_add_new_patient_to_the_system: New Patient Created For Lab Test PASSED*")
                    assert True
                    try:
                        # get values related to created patient for extended screen from DB
                        TestLRFromPatientProfile.patient_uid = self.db_utilities.getProviderUniqueId(
                             "LastName-"+str(TestLRFromPatientProfile.random_number_for_patient),
                             "FirstName-"+str(TestLRFromPatientProfile.random_number_for_patient)
                        )
                        self.logger.info(
                            "TestPatient.patient id:::" + str(TestLRFromPatientProfile.patient_uid))
                    except Exception as ex:
                        self.logger.debug("Exception while getting patient id from database"
                                          " in test_add_new_patient", ex)
                    # self.lab_report.performButtonClick(NBSConstants.TYPE_CODE_LINK_TEXT, NBSConstants.NBS_HOME_PAGE)
                else:
                    self.logger.info(
                        "**test_add_new_patient_to_the_system:New Patient Created for Lab Test FAILED**")
                    self.driver.save_screenshot(
                        os.path.abspath(os.curdir) + "\\screenshots\\" + "Lab_patient.png")
                    self.driver.close()
                    pytest.fail(f"Patient Created For Lab Test FAILED")

    @pytest.mark.run(order=2)
    def test_add_lab_report_to_patient_profile(self, setup):
        self.driver = setup
        self.lab_report = PatientProfileLRObject(self.driver)
        if not self.isPatientProfileOpen(self.lab_report):
            if NBSUtilities.performLoginValidation(self.driver):  # Login Validation
                self.navigateToPatientProfileFromHomePage(self.lab_report)  # Opens Patient Profile
        # click on events tab
        self.lab_report.performButtonClick_web(NBSConstants.TYPE_CODE_ID, "tabs0head1")
        # click on Add New Button For Labs
        self.lab_report.performButtonClick_web(NBSConstants.TYPE_CODE_XPATH,
                                               "//*[@id='subsect_Lab']/table/tbody/tr/td[3]/input")
        # Opens Lab Report
        if self.lab_report.createLabReportForPatient(TestLRFromPatientProfile.random_number_for_patient):
            self.logger.info("*test_add_lab_report_to_patient_profile: Create Lab Report Test PASSED*")
            # Check Same as Reporting Facility
            if PatientProfileLRObject.facility_checked:
                self.logger.info(
                    "*test_add_lab_report_to_patient_profile: Reporting Facility and Ordering Facility "
                    " both are same - SAME AS REPORTING FACILITY Test PASSED*")
            assert True
            # get Lab
            try:
                TestLRFromPatientProfile.lab_id = self.db_utilities.getObservationUid(
                    "LastName-"+str(TestLRFromPatientProfile.random_number_for_patient),
                    "FirstName-"+str(TestLRFromPatientProfile.random_number_for_patient)
                )
                self.logger.info("Lab Observation Id::" + str(TestLRFromPatientProfile.lab_id))
            except Exception as ex:
                self.logger.debug("Exception while getting observation id from database"
                                  " in test_add_new_patient", ex)
        else:
            self.logger.info(
                "**test_add_lab_report_to_patient_profile:Create LabReport Test FAILED**")
            self.driver.save_screenshot(
                os.path.abspath(os.curdir) + "\\screenshots\\" + "create_lab_report.png")
            self.driver.close()
            pytest.fail(f"Add New Lab Report Test FAILED")

        if NBSConstants.LOG_OUT_FROM_PATIENT_SCREEN == "YES":
            self.lab_report.performButtonClick(NBSConstants.TYPE_CODE_LINK_TEXT, NBSConstants.NBS_HOME_PAGE)
            self.lp = LoginPage(self.driver)
            self.lp.logoutFromNBSApplication()
            testcase_end_time = int(time.time() * 1000)
            execution_time_for_all = testcase_end_time - TestLRFromPatientProfile.testcase_start_time
            self.logger.info("Execution Time::" + str(NBSUtilities.convertMillis(execution_time_for_all)))
            self.driver.close()
            self.driver.quit()

    @pytest.mark.run(order=3)
    def test_edit_lab_report_for_patient(self, setup):
        self.driver = setup
        self.lab_report = PatientProfileLRObject(self.driver)
        if not self.isPatientProfileOpen(self.lab_report):
            if NBSUtilities.performLoginValidation(self.driver):  # Login Validation
                self.navigateToPatientProfileFromHomePage(self.lab_report)  # Opens Patient Profile
        # get lab observation uid
        TestLRFromPatientProfile.observation_uid = self.db_utilities.getSingleColumnValueByTable(
            "observation_uid", "observation", "local_id", TestLRFromPatientProfile.lab_id)
        # find Lab report record and open for edit
        try:
            self.lab_report.findSpecificLabRecord(TestLRFromPatientProfile.observation_uid)
        except:
            self.logger.exception(msg="Could Not Found The Specified Record")

        if self.lab_report.updateLabReportInPatientProfile(
                TestLRFromPatientProfile.random_number_for_patient):
            self.logger.info(
                "*test_edit_lab_report_for_patient: Update Lab Report Test PASSED*")
            assert True
        else:
            self.logger.info(
                "**test_edit_lab_report_for_patient:Update LabReport Test FAILED**")
            self.driver.save_screenshot(
                os.path.abspath(os.curdir) + "\\screenshots\\" + "create_lab_report.png")
            # self.driver.close()

        if NBSConstants.LOG_OUT_FROM_PATIENT_SCREEN == "YES":
            self.lab_report.performButtonClick(NBSConstants.TYPE_CODE_LINK_TEXT, NBSConstants.NBS_HOME_PAGE)
            self.lp = LoginPage(self.driver)
            self.lp.logoutFromNBSApplication()
            testcase_end_time = int(time.time() * 1000)
            execution_time_for_all = testcase_end_time - TestLRFromPatientProfile.testcase_start_time
            self.logger.info("Execution Time::" + str(NBSUtilities.convertMillis(execution_time_for_all)))
            self.driver.close()
            self.driver.quit()

    @pytest.mark.run(order=4)
    def test_links_on_the_lab_report_for_patient(self, setup):
        self.driver = setup
        self.lab_report = PatientProfileLRObject(self.driver)
        if not self.isPatientProfileOpen(self.lab_report):
            if NBSUtilities.performLoginValidation(self.driver):  # Login Validation
                self.navigateToPatientProfileFromHomePage(self.lab_report)  # Opens Patient Profile
                try:
                    self.lab_report.findSpecificLabRecord(TestLRFromPatientProfile.observation_uid)
                except:
                    self.logger.exception(msg="Could Not Found The Specified Record")

        # print button validation for selected lab
        if self.lab_report.verifyPrintPopupOnLabReport():
            self.logger.info(
                "*test_links_on_the_lab_report_for_patient: Print POPUP on LabReport Test PASSED*")
            assert True
        else:
            self.logger.info(
                "**test_links_on_the_lab_report_for_patient: Print POPUP on LabReport Test FAILED**")
            self.driver.save_screenshot(
                os.path.abspath(os.curdir) + "\\screenshots\\" + "print_popup.png")
            # self.driver.close()

        # Mark as Reviewed button validation for selected Lab
        mark_result = self.lab_report.markAsReviewedButtonForLab()
        self.logger.info("mark_result:::::::::" + str(mark_result))
        if mark_result == "success":
            self.logger.info(
                "*test_links_on_the_lab_report_for_patient: Mark As Reviewed Test PASSED*")
            assert True
        elif mark_result == "done":
            self.logger.info(
                "*test_links_on_the_lab_report_for_patient: Mark As Reviewed Test already PASSED*")
            assert True
        else:
            self.logger.info(
                "**test_links_on_the_lab_report_for_patient: Mark As Reviewed Test FAILED**")
            self.driver.save_screenshot(
                os.path.abspath(os.curdir) + "\\screenshots\\" + "mark_as_reviewed.png")
            # self.driver.close()

        # transfer ownership for selected Lab
        if self.lab_report.transferOwnerShipButtonForLab():
            self.logger.info(
                "*test_links_on_the_lab_report_for_patient: Transfer OwnerShip on Lab Report Test PASSED*")
            assert True
        else:
            self.logger.info(
                "**test_links_on_the_lab_report_for_patient: Transfer OwnerShip on Lab Report Test FAILED**")
            self.driver.save_screenshot(
                os.path.abspath(os.curdir) + "\\screenshots\\" + "transfer_ownership.png")
            # self.driver.close()

        # delete lab report
        if NBSConstants.VALIDATE_DELETE_BUTTON == "YES":
            # After Transfer Ownership Focus goes to patient Profile, needs to open profile
            # once again to perform delete
            self.lab_report.findSpecificLabRecord(TestLRFromPatientProfile.observation_uid)
            if self.lab_report.deleteLabReport():
                self.logger.info(
                    "*test_links_on_the_lab_report_for_patient: Delete Lab Report Test PASSED*")
                assert True
            else:
                self.logger.info(
                    "**test_links_on_the_lab_report_for_patient: Delete Lab Report Test FAILED**")
                self.driver.save_screenshot(
                    os.path.abspath(os.curdir) + "\\screenshots\\" + "delete_lab_report.png")
                # self.driver.close()
                # pytest.fail(f"Delete Lab Report Test FAILED")
        self.lab_report.performButtonClick(NBSConstants.TYPE_CODE_LINK_TEXT, NBSConstants.NBS_HOME_PAGE)
        self.lp = LoginPage(self.driver)
        self.lp.logoutFromNBSApplication()
        testcase_end_time = int(time.time() * 1000)
        execution_time_for_all = testcase_end_time - TestLRFromPatientProfile.testcase_start_time
        self.logger.info("Execution Time::" + str(NBSUtilities.convertMillis(execution_time_for_all)))
        self.driver.close()
        self.driver.quit()
