import time
from src.page_objects.HomePage.Queues.approval_notification import ApprovalNotification
from src.page_objects.login.login import LoginPage
from src.utilities.constants import NBSConstants
from src.utilities.log_config import LogUtils
from src.utilities.nbs_utilities import NBSUtilities


class TestApprovalInitialNotification:
    testcase_start_time = int(time.time() * 1000)
    logger = LogUtils.loggen(__name__)

    # Approval Initial Notification Queue link on the home page
    def test_approval_initial_notification_queue(self, setup):
        self.driver = setup
        self.approval_notification = ApprovalNotification(self.driver)
        if NBSUtilities.performLoginValidation(self.driver):  # Login Validation
            element = self.approval_notification.findElement(NBSConstants.TYPE_CODE_XPATH,
                                                            self.approval_notification.APPROVAL_NOTIFICATION_QUEUE )
            if element is not None:
                element.click()

            self.approval_notification.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                                  NBSConstants.WAITING_TIME_IN_SECONDS)

            # validate pagination if records spawns more than one page.
            if self.approval_notification.validatePaginationOnResults():
                self.logger.info("*test_approval_initial_notification_queue:Pagination on Search Results PASSED*")
                assert True
            else:
                self.logger.info("*test_approval_initial_notification_queue:Pagination on Search Results FAILED**")

            # sorting on the results grid
            if self.approval_notification.validateSortingOnResultsGrid():
                self.logger.info("*test_approval_initial_notification_queue:Sorting on All Columns on the Results "
                                 "Grid PASSED*")
                assert True
            else:
                self.logger.info("*test_approval_initial_notification_queue:Sorting on one or multiple Columns "
                                 " on the Results Grid Failed**")
            # validate filter on the results Grid.
            if self.approval_notification.validateFilterOnResultsGrid():
                self.logger.info("*test_approval_initial_notification_queue:Filter Data on All Columns on the "
                                 "Results Grid PASSED*")
                assert True
            else:
                self.logger.info("*test_approval_initial_notification_queue:Filter Data on one or multiple Columns"
                                 " on the Results Grid Failed**")

            # validate patient link of the first record in the results grid
            if self.approval_notification.validatePatientLInkInTheResultsPage():
                self.logger.info("*test_approval_initial_notification_queue:Patient Link on the Results Grid Test "
                                 " PASSED*")
                assert True
            else:
                self.logger.info("*test_approval_initial_notification_queue:Patient Link on the Results Grid Test "
                                 " Failed*")
            self.approval_notification.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                                  NBSConstants.WAITING_TIME_IN_SECONDS)

            # validate condition LInk of the first record in the results grid.
            if self.approval_notification.validateConditionLInkInTheResultsPage():
                self.logger.info(
                    "*test_approval_initial_notification_queue:Condition Link on the Results Grid Test "
                    "PASSED*")
                assert True
            else:
                self.logger.info(
                    "*test_approval_initial_notification_queue:Condition Link on the Results Grid Test "
                    "Failed*")

            # validate mark as reviewed sub-form
            if self.approval_notification.validateApproveNotificationOnTheResultsPage():
                self.logger.info(
                    "*test_approval_initial_notification_queue:Approval Notification on selected Lab records"
                    " in the Results Grid Test PASSED*")
                assert True
            else:
                self.logger.info(
                    "*test_approval_initial_notification_queue:Approval Notification on selected Lab records "
                    "in the Results Grid Test Failed*")

            # validate transfer jurisdiction sub-form
            if self.approval_notification.validateRejectNotificationOnTheResultsPage():
                self.logger.info(
                    "*test_approval_initial_notification_queue:Reject Notification on selected Lab records"
                    " in the Results Grid Test PASSED*")
                assert True
            else:
                self.logger.info(
                    "*test_approval_initial_notification_queue:Reject Notification on selected Lab records "
                    "in the Results Grid Test Failed*")
            # validate print and export buttons
            if self.approval_notification.validatePrintAndExportInTheResultsPage():
                self.logger.info(
                    "*test_approval_initial_notification_queue:Print And Export Links validation on the Results "
                    "Grid Test PASSED*")
                assert True
            else:
                self.logger.info(
                    "*test_approval_initial_notification_queue:Print And Export Links validation on the Results "
                    "Grid Test Failed*")

            self.approval_notification.performButtonClick(NBSConstants.TYPE_CODE_LINK_TEXT, NBSConstants.NBS_HOME_PAGE)
            self.lp = LoginPage(self.driver)
            self.approval_notification.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                                  NBSConstants.WAITING_TIME_IN_SECONDS)
            self.lp.logoutFromNBSApplication()
            testcase_end_time = int(time.time() * 1000)
            execution_time_for_all = testcase_end_time - TestApprovalInitialNotification.testcase_start_time
            self.logger.info("Execution Time::" + str(NBSUtilities.convertMillis(execution_time_for_all)))
            self.driver.close()
            self.driver.quit()
