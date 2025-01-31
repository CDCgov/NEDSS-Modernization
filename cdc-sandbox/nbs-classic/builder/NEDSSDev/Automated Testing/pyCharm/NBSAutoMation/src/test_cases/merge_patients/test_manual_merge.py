import os
import time

import pytest
from datetime import datetime
from selenium.webdriver.common.by import By

from src.page_objects.data_entry.patient import PatientObject
from src.page_objects.login.login import LoginPage
from src.page_objects.merge_patients.manual_merge import ManualMerge
from src.utilities.constants import NBSConstants
from src.utilities.db_utilities import DBUtilities
from src.utilities.log_config import LogUtils
from src.utilities.nbs_utilities import NBSUtilities


class TestMergePatient():
    random_number_for_patient = datetime.now().strftime("%m%d%y%H%M%S")
    random_number_for_patient_extended = None
    logger = LogUtils.loggen(__name__)
    patient_uid = None
    patient_uid_extended = None
    testcase_start_time = None
    db_utilities = DBUtilities()


    def test_add_new_patient(self, setup):
        TestMergePatient.testcase_start_time = int(time.time() * 1000)
        self.driver = setup
        self.patient = PatientObject(self.driver)
        self.manual_merge = ManualMerge(self.driver)
        if NBSUtilities.performLoginValidation(self.driver):  # Login Validation
            flag = True
            while (flag):
                rowcount = self.navigateToPatientSearch(self.driver, self.patient, True,
                                                        TestMergePatient.random_number_for_patient)
                if rowcount == 22:
                    flag = False
                else:
                    for i in range(rowcount, 23):
                        if self.patient.addNewPatient(TestMergePatient.random_number_for_patient, False):
                            self.logger.info("*test_add_new_patient:NBS Add New Patient Created Test PASSED*")
                            assert True

        self.patient.performButtonClick(NBSConstants.TYPE_CODE_LINK_TEXT, NBSConstants.NBS_HOME_PAGE)
        self.patient.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, 2)

        self.driver.find_element(By.LINK_TEXT, "Merge Patients").click()
        self.driver.find_element(By.LINK_TEXT, "Manual Search").click()
        time.sleep(1)
        self.manual_merge.search()

        # validate PaginationOnResults Screen
        if self.manual_merge.validatePaginationOnResults():
            self.logger.info("*manual_merge:Pagination on Search Results PASSED*")
            assert True
        else:
            self.logger.info("*manual_merge:Pagination on Search Results FAILED**")
            self.driver.save_screenshot(
                os.path.abspath(os.curdir) + "\\screenshots\\" + "open_inv_pg.png")

        # Validate sorting on the results grid
        if self.manual_merge.validateSortingOnResultsGrid():
            self.logger.info("*manual_merge:Sorting on All Columns on the Results "
                             "Grid PASSED*")
            assert True
        else:
            self.logger.info("*manual_merge:Sorting on one or multiple Columns "
                             " on the Results Grid Failed**")

        # validate patient link of the first record in the results grid
        if self.manual_merge.validatePatientLInkInTheResultsPage():
            self.logger.info("*manual_merge:Patient Link on the Results Grid Test "
                             "PASSED*")
            assert True
        else:
            self.logger.info("*manual_merge:Patient Link on the Results Grid Test "
                             "Failed*")

        self.manual_merge.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                     NBSConstants.WAITING_TIME_IN_SECONDS)
        # validate filter on the results Grid.
        if self.manual_merge.validateFilterOnResultsGrid():
            self.logger.info("*manual_merge:Filter Data on All Columns on the "
                             "Results Grid PASSED*")
            assert True
        else:
            self.logger.info("*manual_merge:Filter Data on one or multiple Columns"
                             " on the Results Grid Failed**")

        # validate RefineYourSearchLink
        if self.manual_merge.validateRefineYourSearchLink():
            self.logger.info("*manual_merge:Filter Data on All Columns on the "
                             "Results Grid PASSED*")
            assert True
        else:
            self.logger.info("*manual_merge:Filter Data on one or multiple Columns"
                             " on the Results Grid Failed**")

        # validate validateNewSearchLink
        if self.manual_merge.validateNewSearchLink():
            self.logger.info("*manual_merge:Filter Data on All Columns on the "
                             "Results Grid PASSED*")
            assert True
        else:
            self.logger.info("*manual_merge:Filter Data on one or multiple Columns"
                             " on the Results Grid Failed**")

        # validate validateRefineSearchTop
        if self.manual_merge.validateRefineSearchTop():
            self.logger.info("*manual_mergee:Filter Data on All Columns on the "
                             "Results Grid PASSED*")
            assert True
        else:
            self.logger.info("*manual_merge:Filter Data on one or multiple Columns"
                             " on the Results Grid Failed**")
        time.sleep(1)

        # validate validatePrint
        if self.manual_merge.validatePrint():
            self.logger.info("*manual_merge:Filter Data on All Columns on the "
                             "Results Grid PASSED*")
            assert True
        else:
            self.logger.info("*manual_merge:Filter Data on one or multiple Columns"
                             " on the Results Grid Failed**")
        time.sleep(1)

        # validate validateDownload
        if self.manual_merge.validateDownload():
            self.logger.info("*manual_merge:Filter Data on All Columns on the "
                             "Results Grid PASSED*")
            assert True
        else:
            self.logger.info("*manual_merge:Filter Data on one or multiple Columns"
                             " on the Results Grid Failed**")

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
                                                "Manual_Merge")
            self.patient.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                    NBSConstants.WAITING_TIME_IN_SECONDS)
            self.patient.performButtonClick(NBSConstants.TYPE_CODE_NAME, PatientObject.patient_search_submit_id)
            self.patient.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                    NBSConstants.WAITING_TIME_IN_SECONDS)
            rowCount = self.driver.find_elements(By.XPATH, self.manual_merge.SEARCH_ROW_COUNT_XPATH)
            
            getCount = 1
            if self.driver.find_element(By.XPATH,
                                        self.manual_merge.SEARCH_ROW_COUNT_XPATH).text != 'Nothing found to display.':
                g = self.driver.find_element(By.XPATH, self.manual_merge.RESULTS_PAGINATION_XPATH).text
                # print("gettext",g)
                s = g.split('of')
                getCount = s[1]
        else:
            self.logger.info("**navigateToManualMerge:NBS Patient Search Landing Page FAILED**")
            driver.close()
            pytest.fail("navigateToManualMerge:NBS Patient Search Landing Page test failed.")

        if len(rowCount) >= 1 and len(rowCount) < 22:
            return int(getCount)
        else:
            return int(getCount)

    def test_merge_two_ids(self,setup):
            self.driver = setup
            self.patient = PatientObject(self.driver)
            self.manual_merge = ManualMerge(self.driver)
            NBSUtilities.performLoginValidation(self.driver)  # Login Validation
            self.driver.find_element(By.LINK_TEXT, self.manual_merge.MERGE_PATIENT).click()
            self.driver.find_element(By.LINK_TEXT, self.manual_merge.MANUAL_SEARCH).click()
            self.manual_merge.search()
            self.driver.find_element(By.XPATH,self.manual_merge.MERGE_ID_CHECKBOX_INPUT_XPATH.format(1,1)).click()
            self.driver.find_element(By.XPATH,self.manual_merge.MERGE_ID_CHECKBOX_INPUT_XPATH.format(1,2)).click()
            self.driver.find_element(By.XPATH,self.manual_merge.MERGE_ID_CHECKBOX_INPUT_XPATH.format(2,1)).click()
            self.driver.find_element(By.XPATH,self.manual_merge.MERGE_ID_CHECKBOX_INPUT_XPATH.format(2,2)).click()
            
            self.driver.switch_to.alert.accept()
            time.sleep(2)
            self.driver.find_element(By.XPATH, self.manual_merge.REMOVE_FILTER_XPATH).click()

            self.driver.find_element(By.XPATH,self.manual_merge.MERGE_XPATH).click()
            time.sleep(1)
            
            element = self.driver.find_element(By.ID,'errorMessages')
            expected_message="Please fix the following errors:\nPlease select a minimum of two patients in order to merge the patient data."

            assert expected_message in element.text


    def test_merge_two_patients_surviving_id(self,setup):
        self.driver = setup
        self.patient = PatientObject(self.driver)
        self.manual_merge = ManualMerge(self.driver)
        NBSUtilities.performLoginValidation(self.driver)  # Login Validation
        self.driver.find_element(By.LINK_TEXT, self.manual_merge.MERGE_PATIENT).click()
        self.driver.find_element(By.LINK_TEXT, self.manual_merge.MANUAL_SEARCH).click()
        self.manual_merge.search()
        self.driver.find_element(By.XPATH,self.manual_merge.MERGE_ID_CHECKBOX_INPUT_XPATH.format(1,1)).click()
        self.driver.find_element(By.XPATH,self.manual_merge.MERGE_ID_CHECKBOX_INPUT_XPATH.format(1,2)).click()
        self.driver.find_element(By.XPATH,self.manual_merge.MERGE_ID_CHECKBOX_INPUT_XPATH.format(2,1)).click()
        get_id_1 = self.driver.find_element(By.XPATH,self.manual_merge.MERGE_ID_CHECKBOX_XPATH.format(1,3)).text
        get_id_2 = self.driver.find_element(By.XPATH,self.manual_merge.MERGE_ID_CHECKBOX_XPATH.format(2,3)).text 
        self.driver.find_element(By.XPATH, self.manual_merge.MERGE_XPATH).click()
        self.driver.switch_to.alert.accept()
        time.sleep(2)
        self.manual_merge.search()
        self.driver.find_element(By.XPATH, self.manual_merge.PATIENT_ID_SORT_IMG_XPATH).click()
        self.driver.find_element(By.XPATH,
                                 "// *[ @ id = 'SearchText4'and @name='answerArrayText(SearchText4)']").send_keys(get_id_2)
        self.driver.find_element(By.XPATH, self.manual_merge.PATIENT_ID_FILTER_OK_XPATH).click()
        actual_msg = self.driver.find_element(By.XPATH, self.manual_merge.SEARCH_RESULT_ROW_XPATH).text
        assert actual_msg == "Nothing found to display."

    def test_merge_two_patients(self,setup):
        self.driver = setup
        self.patient = PatientObject(self.driver)
        self.manual_merge = ManualMerge(self.driver)
        NBSUtilities.performLoginValidation(self.driver)  # Login Validation
        self.driver.find_element(By.LINK_TEXT, self.manual_merge.MERGE_PATIENT).click()
        self.driver.find_element(By.LINK_TEXT, self.manual_merge.MANUAL_SEARCH).click()
        self.manual_merge.search()
        self.driver.find_element(By.XPATH,self.manual_merge.MERGE_ID_CHECKBOX_INPUT_XPATH.format(1,1)).click()
        self.driver.find_element(By.XPATH,self.manual_merge.MERGE_ID_CHECKBOX_INPUT_XPATH.format(2,1)).click()
        get_id_1 = self.driver.find_element(By.XPATH,self.manual_merge.MERGE_ID_CHECKBOX_XPATH.format(1,3)).text
        get_id_2 = self.driver.find_element(By.XPATH,self.manual_merge.MERGE_ID_CHECKBOX_XPATH.format(2,3)).text 

       
        self.driver.find_element(By.XPATH, self.manual_merge.MERGE_XPATH).click()
        self.driver.switch_to.alert.accept()
        time.sleep(2)
        self.manual_merge.search()
        self.driver.find_element(By.XPATH, self.manual_merge.PATIENT_ID_SORT_IMG_XPATH).click()
        self.driver.find_element(By.XPATH, "// *[ @ id = 'SearchText4'and @name='answerArrayText(SearchText4)']").send_keys(get_id_2)
        self.driver.find_element(By.XPATH, self.manual_merge.PATIENT_ID_FILTER_OK_XPATH).click()
        actual_msg = self.driver.find_element(By.XPATH, self.manual_merge.SEARCH_RESULT_ROW_XPATH).text

        assert actual_msg == "Nothing found to display."

        self.manual_merge.performButtonClick(NBSConstants.TYPE_CODE_LINK_TEXT, NBSConstants.NBS_HOME_PAGE)
        self.lp = LoginPage(self.driver)
        self.lp.logoutFromNBSApplication()
        testcase_end_time = int(time.time() * 1000)
        execution_time_for_all = testcase_end_time - TestMergePatient.testcase_start_time
        self.logger.info("Execution Time::" + str(NBSUtilities.convertMillis(execution_time_for_all)))
        self.driver.close()
        self.driver.quit()









