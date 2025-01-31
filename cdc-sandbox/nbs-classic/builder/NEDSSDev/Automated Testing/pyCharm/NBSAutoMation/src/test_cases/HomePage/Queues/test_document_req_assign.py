import os
import time
from src.page_objects.HomePage.Queues.documentreqassign import DocumentReqAssign
from src.page_objects.login.login import LoginPage
from src.utilities.constants import NBSConstants
from src.utilities.log_config import LogUtils
from src.utilities.nbs_utilities import NBSUtilities


class TestDocumentReqAssign:
    testcase_start_time = int(time.time() * 1000)
    logger = LogUtils.loggen(__name__)

    # Document Requirement Assign Queue link on the home page
    def test_document_req_assign_queue_from_home_page(self, setup):
        self.driver = setup
        self.document_req_assign = DocumentReqAssign(self.driver)
        if NBSUtilities.performLoginValidation(self.driver):  # Login Validation
            element = self.document_req_assign.findElement(NBSConstants.TYPE_CODE_XPATH,
                                                          self.document_req_assign.DOCUMENT_REQ_ASSIGN_XPATH)
            if element is not None:
                element.click()

            self.document_req_assign.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                                NBSConstants.WAITING_TIME_IN_SECONDS)

            # validate pagination if records spawns more than one page.
            if self.document_req_assign.validatePaginationOnResults():
                self.logger.info("*test_document_req_assign_queue_from_home_page:Pagination on Search Results PASSED*")
                assert True
            else:
                self.logger.info("*test_document_req_assign_queue_from_home_page:Pagination on Search Results FAILED**")
                self.driver.save_screenshot(
                    os.path.abspath(os.curdir) + "\\screenshots\\" + "dr_assign_pg.png")

            # sorting on the results grid
            if self.document_req_assign.validateSortingOnResultsGrid():
                self.logger.info("*test_document_req_assign_queue_from_home_page:Sorting on All Columns on the Results "
                                 "Grid PASSED*")
                assert True
            else:
                self.logger.info("*test_document_req_assign_queue_from_home_page:Sorting on one or multiple Columns "
                                 " on the Results Grid Failed**")
            # validate filter on the results Grid.

            if self.document_req_assign.validateFilterOnResultsGrid():
                self.logger.info("*test_document_req_assign_queue_from_home_page:Filter Data on All Columns on the "
                                 "Results Grid PASSED*")
                assert True
            else:
                self.logger.info("*test_document_req_assign_queue_from_home_page:Filter Data on one or multiple Columns"
                                 " on the Results Grid Failed**")

            # validate Document Type link of the first record in the results grid
            if self.document_req_assign.validateDocumentTypeLInkInTheResultsPage():
                self.logger.info("*test_document_req_assign_queue_from_home_page:Document Type on the Results Grid Test"
                                 " PASSED*")
                assert True
            else:
                self.logger.info("*test_document_req_assign_queue_from_home_page:Document Type Link on the Results "
                                 "Grid Test "
                                 "Failed*")
            self.document_req_assign.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                                NBSConstants.WAITING_TIME_IN_SECONDS)

            # validate patient link of the first record in the results grid
            if self.document_req_assign.validatePatientLInkInTheResultsPage():
                self.logger.info("*test_document_req_assign_queue_from_home_page:Patient Link on the Results Grid Test "
                                 " PASSED*")
                assert True
            else:
                self.logger.info("*test_document_req_assign_queue_from_home_page:Patient Link on the Results Grid Test "
                                 " Failed*")
            self.document_req_assign.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                                NBSConstants.WAITING_TIME_IN_SECONDS)
            # validate print and export buttons
            if self.document_req_assign.validatePrintAndExportInTheResultsPage():
                self.logger.info(
                    "*test_document_req_assign_queue_from_home_page:Print And Export Links validation on the Results "
                    "Grid Test PASSED*")
                assert True
            else:
                self.logger.info(
                    "*test_document_req_assign_queue_from_home_page:Print And Export Links validation on the Results "
                    "Grid Test Failed*")

            # validate mark as reviewed sub-form
            if self.document_req_assign.validateTransferProgramAreaOnTheResultsPage(str(2)):
                self.logger.info(
                    "*test_document_req_assign_queue_from_home_page:Transfer Program Area on selected Lab records"
                    " in the Results Grid Test PASSED*")
                assert True
            else:
                self.logger.info(
                    "*test_document_req_assign_queue_from_home_page:Transfer Program Area on selected Lab records "
                    "in the Results Grid Test Failed*")

            # validate transfer jurisdiction sub-form
            if self.document_req_assign.validateTransferJurisdictionOnTheResultsPage():
                self.logger.info(
                    "*test_document_req_assign_queue_from_home_page:Transfer Jurisdiction on selected Lab records"
                    " in the Results Grid Test PASSED*")
                assert True
            else:
                self.logger.info(
                    "*test_document_req_assign_queue_from_home_page:Transfer Jurisdiction on selected Lab records "
                    "in the Results Grid Test Failed*")

            # validate transfer OwnerShip sub-form
            if self.document_req_assign.validateTransferOwnerShipOnTheResultsPage(str(2)):
                self.logger.info(
                    "*test_document_req_assign_queue_from_home_page:Transfer Ownership on selected Lab records"
                    " in the Results Grid Test PASSED*")
                assert True
            else:
                self.logger.info(
                    "*test_document_req_assign_queue_from_home_page:Transfer Ownership on selected Lab records "
                    "in the Results Grid Test Failed*")

            self.document_req_assign.performButtonClick(NBSConstants.TYPE_CODE_LINK_TEXT, NBSConstants.NBS_HOME_PAGE)
            self.lp = LoginPage(self.driver)
            self.document_req_assign.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                                NBSConstants.WAITING_TIME_IN_SECONDS)
            self.lp.logoutFromNBSApplication()
            testcase_end_time = int(time.time() * 1000)
            execution_time_for_all = testcase_end_time - TestDocumentReqAssign.testcase_start_time
            self.logger.info("Execution Time::" + str(NBSUtilities.convertMillis(execution_time_for_all)))
            self.driver.close()
            self.driver.quit()
