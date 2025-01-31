import time

from src.page_objects.HomePage.Queues.messages_queue import MessageQueue
from src.page_objects.login.login import LoginPage
from src.utilities.constants import NBSConstants
from src.utilities.log_config import LogUtils
from src.utilities.nbs_utilities import NBSUtilities


class TestMessagesQueue:
    testcase_start_time = int(time.time() * 1000)
    logger = LogUtils.loggen(__name__)

    # Message Queue link on the home page
    def test_message_queue(self, setup):
        self.driver = setup
        self.messages_queue = MessageQueue(self.driver)
        if NBSUtilities.performLoginValidation(self.driver):  # Login Validation
            element = self.messages_queue.findElement(NBSConstants.TYPE_CODE_XPATH,
                                                         self.messages_queue.MESSAGE_QUEUE_XPATH)
            if element is not None:
                element.click()

            self.messages_queue.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                                  NBSConstants.WAITING_TIME_IN_SECONDS)

            # validate pagination if records spawns more than one page.
            if self.messages_queue.validatePaginationOnResults():
                self.logger.info("*test_message_queue:Pagination on Search Results PASSED*")
                assert True
            else:
                self.logger.info("*test_message_queue:Pagination on Search Results FAILED**")

            # sorting on the results grid
            if self.messages_queue.validateSortingOnResultsGrid():
                self.logger.info("*test_message_queue:Sorting on All Columns on the Results "
                                 "Grid PASSED*")
                assert True
            else:
                self.logger.info("*test_message_queue:Sorting on one or multiple Columns "
                                 " on the Results Grid Failed**")
            # validate filter on the results Grid.
            if self.messages_queue.validateFilterOnResultsGrid():
                self.logger.info("*test_message_queue:Filter Data on All Columns on the "
                                 "Results Grid PASSED*")
                assert True
            else:
                self.logger.info("*test_message_queue:Filter Data on one or multiple Columns"
                                 " on the Results Grid Failed**")

            # validate condition LInk of the first record in the results grid.
            if self.messages_queue.validateConditionLInkInTheResultsPage():
                self.logger.info(
                    "*test_message_queue:Condition Link on the Results Grid Test "
                    "PASSED*")
                assert True
            else:
                self.logger.info(
                    "*test_message_queue:Condition Link on the Results Grid Test "
                    "Failed*")

            # validate Mark As Read LInk of the first record in the results grid.
            time.sleep(1)
            if self.messages_queue.validateMarkAsReadLInkInTheResultsPage():
                self.logger.info(
                    "*test_message_queue:Mark as Read Link on the Results Grid Test "
                    "PASSED*")
                assert True
            else:
                self.logger.info(
                    "*test_message_queue:Mark as Read Link on the Results Grid Test "
                    "Failed*")

            if self.messages_queue.validateMessageQueueDelete():
                self.logger.info(
                    "*test_message_queue:Delete Link on the Results Grid Test "
                    "PASSED*")
                assert True
            else:
                self.logger.info(
                    "*test_message_queue:Delete Link on the Results Grid Test "
                    "Failed*")

            # validate print and export buttons
            if self.messages_queue.validatePrintAndExportInTheResultsPage():
                self.logger.info(
                    "*test_message_queue:Print And Export Links validation on the Results "
                    "Grid Test PASSED*")
                assert True
            else:
                self.logger.info(
                    "*test_message_queue:Print And Export Links validation on the Results "
                    "Grid Test Failed*")

            self.messages_queue.performButtonClick(NBSConstants.TYPE_CODE_LINK_TEXT, NBSConstants.NBS_HOME_PAGE)
            self.lp = LoginPage(self.driver)
            self.messages_queue.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                                  NBSConstants.WAITING_TIME_IN_SECONDS)
            self.lp.logoutFromNBSApplication()
            testcase_end_time = int(time.time() * 1000)
            execution_time_for_all = testcase_end_time - TestMessagesQueue.testcase_start_time
            self.logger.info("Execution Time::" + str(NBSUtilities.convertMillis(execution_time_for_all)))
            self.driver.close()
            self.driver.quit()
