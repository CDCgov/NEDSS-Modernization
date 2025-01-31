import time

from src.page_objects.HomePage.Queues.supervisor_queue import SupervisorQueue
from src.page_objects.login.login import LoginPage
from src.utilities.constants import NBSConstants
from src.utilities.log_config import LogUtils
from src.utilities.nbs_utilities import NBSUtilities


class TestMessagesQueue:
    testcase_start_time = int(time.time() * 1000)
    logger = LogUtils.loggen(__name__)

    # Message Queue link on the home page
    def test_supervisor_queue(self, setup):
        self.driver = setup
        self.supervisor_queue = SupervisorQueue(self.driver)
        if NBSUtilities.performLoginValidation(self.driver):  # Login Validation
            element = self.supervisor_queue.findElement(NBSConstants.TYPE_CODE_XPATH,
                                                            SupervisorQueue.SUPERVISORY_REVIEW_QUEUE
                                                            )
            if element is not None:
                element.click()

            self.supervisor_queue.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                                  NBSConstants.WAITING_TIME_IN_SECONDS)

            # validate pagination if records spawns more than one page.
            if self.supervisor_queue.validatePaginationOnResults():
                self.logger.info("*test_supervisor_queue:Pagination on Search Results PASSED*")
                assert True
            else:
                self.logger.info("*test_supervisor_queue:Pagination on Search Results FAILED**")

            # # sorting on the results grid
            if self.supervisor_queue.validateSortingOnResultsGrid():
                self.logger.info("*test_supervisor_queue:Sorting on All Columns on the Results "
                                 "Grid PASSED*")
                assert True
            else:
                self.logger.info("*test_supervisor_queue:Sorting on one or multiple Columns "
                                 " on the Results Grid Failed**")
            # validate filter on the results Grid.
            if self.supervisor_queue.validateFilterOnResultsGrid():
                self.logger.info("*test_supervisor_queue:Filter Data on All Columns on the "
                                 "Results Grid PASSED*")
                assert True
            else:
                self.logger.info("*test_supervisor_queue:Filter Data on one or multiple Columns"
                                 " on the Results Grid Failed**")

            if self.supervisor_queue.validatePatientLInkInTheResultsPage():
                self.logger.info("*test_updated_initial_notification_queue:Patient Link on the Results Grid Test "
                                 " PASSED*")
                assert True
            else:
                self.logger.info("*test_updated_initial_notification_queue:Patient Link on the Results Grid Test "
                                 " Failed*")
            self.supervisor_queue.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                                  NBSConstants.WAITING_TIME_IN_SECONDS)


            # validate condition LInk of the first record in the results grid.
            if self.supervisor_queue.validateConditionLInkInTheResultsPage():
                self.logger.info(
                    "*test_supervisor_queue:Condition Link on the Results Grid Test "
                    "PASSED*")
                assert True
            else:
                self.logger.info(
                    "*test_supervisor_queue:Condition Link on the Results Grid Test "
                    "Failed*")

            # validate Mark As Read LInk of the first record in the results grid.
            if self.supervisor_queue.validateApproveNotificationOnTheResultsPage():
                self.logger.info(
                    "*test_supervisor_queue:Approve Link on the Results Grid Test "
                    "PASSED*")
                assert True
            else:
                self.logger.info(
                    "*test_supervisor_queue:Approve Link on the Results Grid Test "
                    "Failed*")

            if self.supervisor_queue.validateSupervisorQueueDelete():
                self.logger.info(
                    "*test_supervisor_queue:Condition Link on the Results Grid Test "
                    "PASSED*")
                assert True
            else:
                self.logger.info(
                    "*test_supervisor_queue:Condition Link on the Results Grid Test "
                    "Failed*")
            #
            # validate print and export buttons
            if self.supervisor_queue.validatePrintAndExportInTheResultsPage():
                self.logger.info(
                    "*test_supervisor_queue:Print And Export Links validation on the Results "
                    "Grid Test PASSED*")
                assert True
            else:
                self.logger.info(
                    "*test_supervisor_queue:Print And Export Links validation on the Results "
                    "Grid Test Failed*")

            self.supervisor_queue.performButtonClick(NBSConstants.TYPE_CODE_LINK_TEXT, NBSConstants.NBS_HOME_PAGE)
            self.lp = LoginPage(self.driver)
            self.supervisor_queue.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                                  NBSConstants.WAITING_TIME_IN_SECONDS)
            self.lp.logoutFromNBSApplication()
            testcase_end_time = int(time.time() * 1000)
            execution_time_for_all = testcase_end_time - TestMessagesQueue.testcase_start_time
            self.logger.info("Execution Time::" + str(NBSUtilities.convertMillis(execution_time_for_all)))
            self.driver.close()
            self.driver.quit()
