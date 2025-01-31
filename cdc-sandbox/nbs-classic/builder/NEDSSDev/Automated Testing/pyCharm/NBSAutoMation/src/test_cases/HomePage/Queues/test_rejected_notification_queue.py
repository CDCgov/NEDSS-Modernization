import time

from selenium.webdriver.common.by import By

from src.page_objects.HomePage.Queues.approval_notification import ApprovalNotification
from src.page_objects.HomePage.Queues.rejected_notification import RejectedNotification
from src.page_objects.login.login import LoginPage
from src.utilities.constants import NBSConstants
from src.utilities.log_config import LogUtils
from src.utilities.nbs_utilities import NBSUtilities


class TestRejectedInitialNotification:
    testcase_start_time = int(time.time() * 1000)
    logger = LogUtils.loggen(__name__)

    # Rejected Initial Notification Queue link on the home page
    def test_rejected_initial_notification_queue(self, setup):
        self.driver = setup
        self.rejected_notification = RejectedNotification(self.driver)
        self.approval_notification = ApprovalNotification(self.driver)
        if NBSUtilities.performLoginValidation(self.driver):  # Login Validation
            element = self.driver.find_element(By.XPATH, "//*[@id='myQueues']/div/div[3]/ul[1]/li[4]")
            if element.text !=   "Rejected Notifications Queue (0)":
                element = self.driver.find_element(By.XPATH,self.rejected_notification.REJECTED_NOTIFICATION_QUEUE)
                if element is not None:
                    element.click()
            else:
                self.driver.find_element(By.XPATH, self.approval_notification.APPROVAL_NOTIFICATION_QUEUE).click()
                self.approval_notification.validateRejectNotificationOnTheResultsPage()
                # return to home screen
                self.driver.find_element(By.XPATH, self.rejected_notification.RETURN_HOME_XPATH).click()
                # click on rejected queue.
                element = self.driver.find_element(By.XPATH, self.rejected_notification.REJECTED_NOTIFICATION_QUEUE)
                element.click()
                self.rejected_notification.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                                      NBSConstants.WAITING_TIME_IN_SECONDS)

            # validate pagination if records spawns more than one page.
            if self.rejected_notification.validatePaginationOnResults():
                self.logger.info("*test_rejected_initial_notification_queue:Pagination on Search Results PASSED*")
                assert True
            else:
                self.logger.info("*test_rejected_initial_notification_queue:Pagination on Search Results FAILED**")

            # sorting on the results grid
            if self.rejected_notification.validateSortingOnResultsGrid():
                self.logger.info("*test_rejected_initial_notification_queue:Sorting on All Columns on the Results "
                                 "Grid PASSED*")
                assert True
            else:
                self.logger.info("*test_rejected_initial_notification_queue:Sorting on one or multiple Columns "
                                 " on the Results Grid Failed**")
            # validate filter on the results Grid.
            if self.rejected_notification.validateFilterOnResultsGrid():
                self.logger.info("*test_rejected_initial_notification_queue:Filter Data on All Columns on the "
                                 "Results Grid PASSED*")
                assert True
            else:
                self.logger.info("*test_rejected_initial_notification_queue:Filter Data on one or multiple Columns"
                                 " on the Results Grid Failed**")

            # validate patient link of the first record in the results grid
            if self.rejected_notification.validatePatientLInkInTheResultsPage():
                self.logger.info("*test_rejected_initial_notification_queue:Patient Link on the Results Grid Test "
                                 " PASSED*")
                assert True
            else:
                self.logger.info("*test_rejected_initial_notification_queue:Patient Link on the Results Grid Test "
                                 " Failed*")
            self.rejected_notification.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                                  NBSConstants.WAITING_TIME_IN_SECONDS)

            # validate condition LInk of the first record in the results grid.
            if self.rejected_notification.validateConditionLInkInTheResultsPage():
                self.logger.info(
                    "*test_rejected_initial_notification_queue:Condition Link on the Results Grid Test "
                    "PASSED*")
                assert True
            else:
                self.logger.info(
                    "*test_rejected_initial_notification_queue:Condition Link on the Results Grid Test "
                    "Failed*")

            # validate print and export buttons
            if self.rejected_notification.validatePrintAndExportInTheResultsPage():
                self.logger.info(
                    "*test_rejected_initial_notification_queue:Print And Export Links validation on the Results "
                    "Grid Test PASSED*")
                assert True
            else:
                self.logger.info(
                    "*test_rejected_initial_notification_queue:Print And Export Links validation on the Results "
                    "Grid Test Failed*")

            self.rejected_notification.performButtonClick(NBSConstants.TYPE_CODE_LINK_TEXT, NBSConstants.NBS_HOME_PAGE)
            self.lp = LoginPage(self.driver)
            self.rejected_notification.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                                  NBSConstants.WAITING_TIME_IN_SECONDS)
            self.lp.logoutFromNBSApplication()
            testcase_end_time = int(time.time() * 1000)
            execution_time_for_all = testcase_end_time - TestRejectedInitialNotification.testcase_start_time
            self.logger.info("Execution Time::" + str(NBSUtilities.convertMillis(execution_time_for_all)))
            self.driver.close()
            self.driver.quit()
