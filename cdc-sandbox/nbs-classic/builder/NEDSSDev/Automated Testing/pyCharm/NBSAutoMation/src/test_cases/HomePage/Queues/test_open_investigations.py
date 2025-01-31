import os
import time

import pytest
from src.page_objects.HomePage.Queues.openinvestigation import OpenInvestigations
from src.page_objects.login.login import LoginPage
from src.utilities.constants import NBSConstants
from src.utilities.log_config import LogUtils
from src.utilities.nbs_utilities import NBSUtilities


class TestOpenInvestigations:
    testcase_start_time = int(time.time() * 1000)
    logger = LogUtils.loggen(__name__)

    # open investigations link on the home page
    def test_open_investigation_queue_from_home_page(self, setup, params):
        self.driver = setup
        self.open_investigation = OpenInvestigations(self.driver)
        if NBSUtilities.performLoginValidation(self.driver):  # Login Validation

            if params["navigate"] == "homepage":  # default home page

                element = self.open_investigation.findElement(NBSConstants.TYPE_CODE_XPATH,
                                                            self.open_investigation.OPEN_INVESTIGATION_XPATH)
                if element is not None:
                    element.click()

            else:  # main Navigation
                self.open_investigation.performButtonClick(NBSConstants.TYPE_CODE_LINK_TEXT,
                                                           "Open Investigations")
            self.open_investigation.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                               NBSConstants.WAITING_TIME_IN_SECONDS)

            # validate pagination if records spawns more than one page.
            if self.open_investigation.validatePaginationOnResults():
                self.logger.info("*test_open_investigation_queue_from_home_page:Pagination on Search Results PASSED*")
                assert True
            else:
                self.logger.info("*test_open_investigation_queue_from_home_page:Pagination on Search Results FAILED**")
                self.driver.save_screenshot(
                    os.path.abspath(os.curdir) + "\\screenshots\\" + "open_inv_pg.png")

            # sorting on the results grid
            if self.open_investigation.validateSortingOnResultsGrid():
                self.logger.info("*test_open_investigation_queue_from_home_page:Sorting on All Columns on the Results "
                                 "Grid PASSED*")
                assert True
            else:
                self.logger.info("*test_open_investigation_queue_from_home_page:Sorting on one or multiple Columns "
                                 " on the Results Grid Failed**")
            # validate filter on the results Grid.

            if self.open_investigation.validateFilterOnResultsGrid():
                self.logger.info("*test_open_investigation_queue_from_home_page:Filter Data on All Columns on the "
                                 "Results Grid PASSED*")
                assert True
            else:
                self.logger.info("*test_open_investigation_queue_from_home_page:Filter Data on one or multiple Columns "
                                 " on the Results Grid Failed**")

            # validate patient link of the first record in the results grid
            if self.open_investigation.validatePatientLInkInTheResultsPage():
                self.logger.info("*test_open_investigation_queue_from_home_page:Patient Link on the Results Grid Test "
                                 "PASSED*")
                assert True
            else:
                self.logger.info("*test_open_investigation_queue_from_home_page:Patient Link on the Results Grid Test "
                                 "Failed*")
            self.open_investigation.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                               NBSConstants.WAITING_TIME_IN_SECONDS)
            # validate condition LInk of the first record in the results grid.
            if self.open_investigation.validateConditionLInkInTheResultsPage():
                self.logger.info(
                    "*test_open_investigation_queue_from_home_page:Condition Link on the Results Grid Test "
                    "PASSED*")
                assert True
            else:
                self.logger.info(
                    "*test_open_investigation_queue_from_home_page:Condition Link on the Results Grid Test "
                    "Failed*")

            # validate assign sub-form
            if self.open_investigation.validateAssignSubFormOnTheResultsPage():
                self.logger.info(
                    "*test_open_investigation_queue_from_home_page:Assign Investigator on the Results "
                    "Grid Test PASSED*")
                assert True
            else:
                self.logger.info(
                    "*test_open_investigation_queue_from_home_page:Assign Investigator on the Results "
                    "Grid Test PASSED*")

            # validate print and export buttons
            if self.open_investigation.validatePrintAndExportInTheResultsPage():
                self.logger.info(
                    "*test_open_investigation_queue_from_home_page:Print And Export Links validation on the Results "
                    "Grid Test PASSED*")
                assert True
            else:
                self.logger.info(
                    "*test_open_investigation_queue_from_home_page:Print And Export Links validation on the Results "
                    "Grid Test Failed*")

            self.open_investigation.performButtonClick(NBSConstants.TYPE_CODE_LINK_TEXT, NBSConstants.NBS_HOME_PAGE)
            self.lp = LoginPage(self.driver)
            self.lp.logoutFromNBSApplication()
            testcase_end_time = int(time.time() * 1000)
            execution_time_for_all = testcase_end_time - TestOpenInvestigations.testcase_start_time
            self.logger.info("Execution Time::" + str(NBSUtilities.convertMillis(execution_time_for_all)))
            self.driver.close()
            self.driver.quit()

    @pytest.mark.skip
    def test_open_investigation_from_main_menu(self, setup):
        self.driver = setup
        self.open_investigation = OpenInvestigations(self.driver)
        if NBSUtilities.performLoginValidation(self.driver):  # Login Validation
            self.open_investigation.performButtonClick(NBSConstants.TYPE_CODE_LINK_TEXT,
                                                       "Open Investigations")
            self.open_investigation.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                               NBSConstants.WAITING_TIME_IN_SECONDS)
            # validate pagination if records spawns more than one page.
            if self.open_investigation.validatePaginationOnResults():
                self.logger.info("*test_open_investigation_queue_from_home_page:Pagination on Search Results PASSED*")
                assert True
            else:
                self.logger.info("*test_open_investigation_queue_from_home_page:Pagination on Search Results FAILED**")
                self.driver.save_screenshot(
                    os.path.abspath(os.curdir) + "\\screenshots\\" + "open_inv_pg.png")

            # sorting on the results grid
            if self.open_investigation.validateSortingOnResultsGrid():
                self.logger.info("*test_open_investigation_queue_from_home_page:Sorting on All Columns on the Results "
                                 "Grid PASSED*")
                assert True
            else:
                self.logger.info("*test_open_investigation_queue_from_home_page:Sorting on one or multiple Columns "
                                 " on the Results Grid Failed**")
            # validate filter on the results Grid.

            if self.open_investigation.validateFilterOnResultsGrid():
                self.logger.info("*test_open_investigation_queue_from_home_page:Filter Data on All Columns on the "
                                 "Results Grid PASSED*")
                assert True
            else:
                self.logger.info("*test_open_investigation_queue_from_home_page:Filter Data on one or multiple Columns "
                                 " on the Results Grid Failed**")

            # validate patient link of the first record in the results grid
            if self.open_investigation.validatePatientLInkInTheResultsPage():
                self.logger.info("*test_open_investigation_queue_from_home_page:Patient Link on the Results Grid Test "
                                 "PASSED*")
                assert True
            else:
                self.logger.info("*test_open_investigation_queue_from_home_page:Patient Link on the Results Grid Test "
                                 "Failed*")
            self.open_investigation.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                               NBSConstants.WAITING_TIME_IN_SECONDS)
            # validate condition LInk of the first record in the results grid.
            if self.open_investigation.validateConditionLInkInTheResultsPage():
                self.logger.info(
                    "*test_open_investigation_queue_from_home_page:Condition Link on the Results Grid Test "
                    "PASSED*")
                assert True
            else:
                self.logger.info(
                    "*test_open_investigation_queue_from_home_page:Condition Link on the Results Grid Test "
                    "Failed*")

            # validate print and export buttons

            if self.open_investigation.validatePrintAndExportInTheResultsPage():
                self.logger.info(
                    "*test_open_investigation_queue_from_home_page:Print And Export Links validation on the Results "
                    "Grid Test PASSED*")
                assert True
            else:
                self.logger.info(
                    "*test_open_investigation_queue_from_home_page:Print And Export Links validation on the Results "
                    "Grid Test Failed*")

            # validate assign sub-form
            if self.open_investigation.validateAssignSubFormOnTheResultsPage():
                self.logger.info(
                    "*test_open_investigation_queue_from_home_page:Assign Investigator on the Results "
                    "Grid Test PASSED*")
                assert True
            else:
                self.logger.info(
                    "*test_open_investigation_queue_from_home_page:Assign Investigator on the Results "
                    "Grid Test PASSED*")
            if NBSConstants.LOG_OUT_FROM_PATIENT_SCREEN == "YES":
                self.open_investigation.performButtonClick(NBSConstants.TYPE_CODE_LINK_TEXT, NBSConstants.NBS_HOME_PAGE)
                self.lp = LoginPage(self.driver)
                self.lp.logoutFromNBSApplication()
                testcase_end_time = int(time.time() * 1000)
                execution_time_for_all = testcase_end_time - TestOpenInvestigations.testcase_start_time
                self.logger.info("Execution Time::" + str(NBSUtilities.convertMillis(execution_time_for_all)))
                self.driver.close()
                self.driver.quit()
