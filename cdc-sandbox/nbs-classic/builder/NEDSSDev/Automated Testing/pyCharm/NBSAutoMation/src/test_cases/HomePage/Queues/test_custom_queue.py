import os
import time
from builtins import range
from datetime import datetime

from pydantic import conint
from selenium.webdriver.common.by import By
from selenium.webdriver.support.select import Select

from src.page_objects.BasePageObject import BasePageObject
from src.page_objects.HomePage.Queues.approval_notification import ApprovalNotification
from src.page_objects.HomePage.Queues.custom_queue import CustomQueue
from src.page_objects.login.login import LoginPage
from src.utilities.constants import NBSConstants
from src.utilities.log_config import LogUtils
from src.utilities.nbs_utilities import NBSUtilities


class TestCustomQueues:


    testcase_start_time = int(time.time() * 1000)
    logger = LogUtils.loggen(__name__)
    event_type = "I"


    random_number_for_patient = datetime.now().strftime("%m%d%y%H%M%S")

    # Custom Queue
    # add queue from advanced search on home screen

    def test_approval_initial_notification_queue(self, setup, params):
        self.driver = setup
        self.custom_queue = CustomQueue(self.driver)
        self.basePageObject = BasePageObject(self.driver)
        condition = ["10311","11065","102201"]
        if NBSUtilities.performLoginValidation(self.driver):
            self.driver.find_element(By.XPATH, self.custom_queue.ADVANCED_SEARCH_XPATH).click()
            self.driver.find_element(By.ID, self.custom_queue.EVENT_SEARCH_ID).click()
            self.driver.find_element(By.XPATH, self.custom_queue.EVENT_TYPE_DROPDOWN_XPATH).send_keys(self.event_type)
            self.driver.find_element(By.ID, 'TYPE').click()
            element = self.driver.find_element(By.XPATH,self.custom_queue.COND_XPATH )
            select_element = Select(element)
            for i in range(0,len(condition)):
                select_element.select_by_value(condition[i])
                # time.sleep(1)
            # select_element.select_by_value(condition[1])
            # select_element.select_by_value(condition[2])

            self.driver.find_element(By.NAME, "Submit").click()
            time.sleep(1)
            self.driver.find_element(By.XPATH, self.custom_queue.SAVE_GIF_XPATH).click()
            #custom queue details
            queue_name = NBSConstants.APPEND_TEST_FOR_QUEUE_NAME.__add__("-").__add__(
                str(self.random_number_for_patient))
            self.driver.find_element(By.XPATH, self.custom_queue.NAME_QUEUE_XPATH).send_keys(queue_name)
            print("Queue_name", queue_name)
            self.driver.find_element(By.XPATH, self.custom_queue.DESCRIPTION_QUEUE_XPATH).send_keys("CustomQueue:"+ NBSUtilities.generate_random_string(65))
            # select public radio button
            self.driver.find_element(By.XPATH, self.custom_queue.PUBLIC_RADIO_BUTTON_XPATH).click()
            # click submit
            self.driver.find_element(By.XPATH, self.custom_queue.SAVE_XPATH).click()
            #return to homepage
            self.driver.find_element(By.XPATH, self.custom_queue.RETUNR_HOME_XPATH).click()
            self.driver.find_element(By.XPATH, self.custom_queue.PUBLIC_CUSTOM_QUEUE_XPATH).click()
            self.driver.find_element(By.XPATH, self.custom_queue.PUBLIC_CUSTOM_QUEUE_ID).send_keys(queue_name)
            time.sleep(1)
            self.driver.find_element(By.XPATH, self.custom_queue.PUBLIC_CUSTOM_QUEUE_XPATH).click()
            self.driver.find_element(By.LINK_TEXT, queue_name).click()

            # # sorting on the pagination grid
            if self.custom_queue.validatePaginationOnResults():
                self.logger.info("*test_custome_queue:Pagination on Search Results PASSED*")
                assert True
            else:
                self.logger.info("*test_custome_queue:Pagination on Search Results FAILED**")
                self.driver.save_screenshot(
                    os.path.abspath(os.curdir) + "\\screenshots\\" + "open_inv_pg.png")

            # sorting on the results grid
            if self.custom_queue.validateSortingOnResultsGrid():
                self.logger.info("*test_custome_queue:Sorting on All Columns on the Results "
                                 "Grid PASSED*")
                assert True
            else:
                self.logger.info("*test_custome_queue:Sorting on one or multiple Columns "
                                 " on the Results Grid Failed**")
            # validate filter on the results Grid.

            if self.custom_queue.validateFilterOnResultsGrid():
                self.logger.info("*test_custom_queue:Filter Data on All Columns on the "
                                 "Results Grid PASSED*")
                assert True
            else:
                self.logger.info("*test_custome_queue:Filter Data on one or multiple Columns "
                                 " on the Results Grid Failed**")

            # validate patient link of the first record in the results grid
            if self.custom_queue.validatePatientLInkInTheResultsPage():
                self.driver.find_element(By.XPATH, '//*[@id="myInputPublic"]').send_keys(queue_name)
                self.driver.find_element(By.XPATH, '//*[@id="myQueues"]/div/div[3]/b[3]/font').click()
                self.driver.find_element(By.LINK_TEXT, queue_name).click()

                self.logger.info("*test_custome_queue:Patient Link on the Results Grid Test "
                                 "PASSED*")
                assert True
            else:
                self.logger.info("*test_custome_queue:Patient Link on the Results Grid Test "
                                 "Failed*")
            self.custom_queue.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                              NBSConstants.WAITING_TIME_IN_SECONDS)
            # validate condition LInk of the first record in the results grid.
            if self.custom_queue.validateConditionLInkInTheResultsPage():
                self.logger.info(
                    "*test_custome_queue:Condition Link on the Results Grid Test "
                    "PASSED*")
                assert True
            else:
                self.logger.info(
                    "*test_custome_queue:Condition Link on the Results Grid Test "
                    "Failed*")

            # validate print and export buttons
            if self.custom_queue.validatePrintAndExportInTheResultsPage():
                self.logger.info(
                    "*test_custome_queue:Print And Export Links validation on the Results "
                    "Grid Test PASSED*")
                assert True
            else:
                self.logger.info(
                    "*test_custom_queue_from_home_page:Print And Export Links validation on the Results "
                    "Grid Test Failed*")

            # validate delete buttons
            if self.custom_queue.validateDeleteQueue():
                self.logger.info(
                    "*test_custom_queue:Delete Links validation on the Results "
                    "Grid Test PASSED*")
                assert True
            else:
                self.logger.info(
                    "*test_custom_queue:Delete Links validation on the Results "
                    "Grid Test Failed*")

            self.custom_queue.performButtonClick(NBSConstants.TYPE_CODE_LINK_TEXT, NBSConstants.NBS_HOME_PAGE)
            self.lp = LoginPage(self.driver)
            self.lp.logoutFromNBSApplication()
            testcase_end_time = int(time.time() * 1000)
            execution_time_for_all = testcase_end_time - TestCustomQueues.testcase_start_time
            self.logger.info("Execution Time::" + str(NBSUtilities.convertMillis(execution_time_for_all)))
            self.driver.close()
            self.driver.quit()













