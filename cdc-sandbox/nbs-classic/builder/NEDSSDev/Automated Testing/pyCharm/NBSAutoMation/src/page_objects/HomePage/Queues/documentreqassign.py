from selenium.common import NoSuchElementException
from selenium.webdriver.common.by import By
from selenium.webdriver.support.wait import WebDriverWait

from src.page_objects.BasePageObject import BasePageObject
from src.utilities.constants import NBSConstants
from src.utilities.db_utilities import DBUtilities
from src.utilities.log_config import LogUtils
import random
from selenium.webdriver.support import expected_conditions as EC

from src.utilities.nbs_utilities import NBSUtilities


class DocumentReqAssign(BasePageObject):
    logger = LogUtils.loggen(__name__)

    def __init__(self, driver):
        super().__init__(driver)
        self.driver = driver

    # locators
    select_all_popup_checkbox = "//*[@id='parent']/thead/tr/th[REPLACE_INDEX]/div/label[2]/input"
    checkbox_popup_options = "//*[@id='parent']/thead/tr/th[REPLACE_INDEX]/div/label"
    ok_button_on_the_popup = "//*[@id='parent']/thead/tr/th[REPLACE_INDEX]/div/label[1]/input[1]"
    cancel_button_on_the_popup = "//*[@id='parent']/thead/tr/th[REPLACE_INDEX]/div/label[1]/input[2]"
    text_filed_search_text = "//*[@id='SearchText{REPLACE_INDEX}']"
    text_filed_search_ok_button = "//*[@id='b2SearchText{REPLACE_INDEX}']"
    DOCUMENT_REQ_ASSIGN_XPATH= "//*[@id='myQueues']/div/div[3]/ul[1]/li[5]/a"

    # Sort/filter Results Grid
    column1_name = "Document Type"
    column2_name = "Date Received"
    column3_name = "Reporting Facility/Provider"
    column4_name = "Patient"
    column5_name = "Description"
    column6_name = "Jurisdiction"
    column7_name = "Program Area"
    column8_name = "Local ID"

    # Default Sort
    column1_default_sort = "No"
    column2_default_sort = "Yes-A"
    column3_default_sort = "No"
    column4_default_sort = "No"
    column5_default_sort = "No"
    column6_default_sort = "No"
    column7_default_sort = "No"
    column8_default_sort = "No"

    # sort/filter image xpath
    column1_xpath = "//*[@id='parent']/thead/tr/th[2]/img"
    column2_xpath = "//*[@id='parent']/thead/tr/th[3]/img"
    column3_xpath = "//*[@id='parent']/thead/tr/th[4]/img"
    column4_xpath = "//*[@id='parent']/thead/tr/th[5]/img"
    column5_xpath = "//*[@id='parent']/thead/tr/th[6]/img"
    column6_xpath = "//*[@id='parent']/thead/tr/th[7]/img"
    column7_xpath = "//*[@id='parent']/thead/tr/th[8]/img"
    column8_xpath = "//*[@id='parent']/thead/tr/th[9]/img"

    # filter type
    column1_filter_type = "checkbox"
    column2_filter_type = "checkbox"
    column3_filter_type = "textfield"
    column4_filter_type = "textfield"
    column5_filter_type = "checkbox"
    column6_filter_type = "checkbox"
    column7_filter_type = "checkbox"
    column8_filter_type = "textfield"

    # filter select all and options header index
    column1_header_index = 2
    column2_header_index = 3
    column3_header_index = 4
    column4_header_index = 5  # changed for replace index for path
    column5_header_index = 6
    column6_header_index = 7
    column7_header_index = 8
    column8_header_index = 9  # changed for replace index for path

    def validateSortingOnResultsGrid(self):
        # validate columns sort for results grid column name --default sort
        grid_columns = {
            self.column2_name: [self.column2_default_sort, self.column2_xpath],  # default sort column
            self.column1_name: [self.column1_default_sort, self.column1_xpath],
            self.column3_name: [self.column3_default_sort, self.column3_xpath],
            self.column4_name: [self.column4_default_sort, self.column4_xpath],
            self.column5_name: [self.column5_default_sort, self.column5_xpath],
            self.column6_name: [self.column6_default_sort, self.column6_xpath],
            self.column7_name: [self.column7_default_sort, self.column7_xpath],
            self.column8_name: [self.column8_default_sort, self.column8_xpath]}

        self.sortResultsGridColumnsOneByOne(grid_columns, "Desc")

        return True

    def validateFilterOnResultsGrid(self):
        # validate filters with first row data for each column
        filter_columns = {
            self.column1_name: [self.column1_filter_type, self.column1_xpath, self.column1_header_index, 0],
            self.column2_name: [self.column2_filter_type, self.column2_xpath, self.column2_header_index, 0],
            self.column3_name: [self.column3_filter_type, self.column3_xpath, self.column3_header_index, "Provider"],
            self.column4_name: [self.column4_filter_type, self.column4_xpath, self.column4_header_index, 1],
            self.column5_name: [self.column5_filter_type, self.column5_xpath, self.column5_header_index, 0],
            self.column6_name: [self.column6_filter_type, self.column6_xpath, self.column6_header_index, 0],
            self.column7_name: [self.column7_filter_type, self.column7_xpath, self.column7_header_index, 0],
            self.column8_name: [self.column8_filter_type, self.column8_xpath, self.column8_header_index, 2]}

        self.filterResultsGridDataByColumn(filter_columns)

        return True

    def performCheckboxFilterOnTheResultsGrid(self, replace_index):

        checkbox_options_popup = self.checkbox_popup_options.replace("REPLACE_INDEX", replace_index)
        select_all = self.findElement(NBSConstants.TYPE_CODE_XPATH, checkbox_options_popup + "[2]/input")
        option_checkboxes = self.findElements(NBSConstants.TYPE_CODE_XPATH, checkbox_options_popup)
        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
        if len(option_checkboxes) > 5:
            # unselect all options
            select_all.click()
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
            option_checkboxes_correct_size = len(self.findElements(NBSConstants.TYPE_CODE_XPATH, checkbox_options_popup
                                                                   + "[@class='pText']"))
            select_checkbox_list = self.getRandomlySelectCheckBoxOptions(option_checkboxes_correct_size)
            if select_checkbox_list is not None:
                if len(select_checkbox_list) > 1:
                    WebDriverWait(self.driver, 25).until(
                        EC.element_to_be_clickable((By.XPATH,
                                                    checkbox_options_popup + "[" + str(
                                                        int(select_checkbox_list[0]) + 2) + "]/input"))).click()
                    WebDriverWait(self.driver, 25).until(
                        EC.element_to_be_clickable((By.XPATH,
                                                    checkbox_options_popup + "[" + str(
                                                        int(select_checkbox_list[1]) + 2) + "]/input"))).click()
                elif len(select_checkbox_list) == 1:
                    WebDriverWait(self.driver, 25).until(
                        EC.element_to_be_clickable((By.XPATH,
                                                    checkbox_options_popup + "[" + str(
                                                        int(select_checkbox_list[0]) + 2) + "]/input"))).click()

        # click OK Button, it should filter results grid
        WebDriverWait(self.driver, 25).until(
            EC.element_to_be_clickable((By.XPATH, checkbox_options_popup + "[1]/input[1]"))).click()

        # self.performButtonClick(NBSConstants.TYPE_CODE_XPATH, checkbox_options_popup + "[1]/input[1]")
        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                        NBSConstants.WAITING_TIME_IN_SECONDS)
        return True

    def performTextFieldFilterOnTheResultsGrid(self, replace_index, key):
        if replace_index == "Provider":
            search_text_element = self.text_filed_search_text.replace("SearchText{REPLACE_INDEX}", replace_index)
            search_text_ok_button = self.text_filed_search_ok_button.replace("SearchText{REPLACE_INDEX}", replace_index)
        else:
            search_text_element = self.text_filed_search_text.replace("{REPLACE_INDEX}", replace_index)
            search_text_ok_button = self.text_filed_search_ok_button.replace("{REPLACE_INDEX}", replace_index)
        self.setValueForHTMLElement("TEXTBOX", NBSConstants.TYPE_CODE_XPATH,
                                    search_text_element, self.getSearchText(key))
        # self.performButtonClick(NBSConstants.TYPE_CODE_XPATH, search_text_ok_button)
        WebDriverWait(self.driver, 25).until(
            EC.element_to_be_clickable((By.XPATH, search_text_ok_button))).click()
        return True

    def getSearchText(self, key):
        search_text = None
        if key == "Patient":
            search_text = (self.findElement(NBSConstants.TYPE_CODE_XPATH, "//*[@id='parent']/tbody/tr[1]/td[5]/a")
                           .get_attribute('text'))
        elif key == "Investigation ID":
            search_text = self.findElement(NBSConstants.TYPE_CODE_XPATH,
                                           "//*[@id='parent']/tbody/tr[1]/td[9]").text
        elif key == "Local ID":
            search_text = self.findElement(NBSConstants.TYPE_CODE_XPATH,
                                           "//*[@id='parent']/tbody/tr[1]/td[9]").text
        elif key == "Reporting Facility/Provider":
            search_text_e = self.findElement(NBSConstants.TYPE_CODE_XPATH,
                                             "//*[@id='parent']/tbody/tr[1]/td[4]").get_attribute("innerHTML")
            if "<br" in search_text_e:
                search_text = search_text_e.split("<br>")[1]
            else:
                search_text = search_text_e
        self.logger.info(search_text)

        if search_text is not None:
            return search_text
        else:
            return ""

    def validatePaginationOnResults(self):
        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                        NBSConstants.WAITING_TIME_IN_SECONDS)
        # next
        while True:
            try:
                self.driver.find_element(By.LINK_TEXT, "Next").click()
            except NoSuchElementException:
                break
        while True:
            try:
                self.driver.find_element(By.LINK_TEXT, "Previous").click()
            except NoSuchElementException:
                break
        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                        NBSConstants.WAITING_TIME_IN_SECONDS)
        return True

    def validatePatientLInkInTheResultsPage(self):
        try:
            self.performButtonClick(NBSConstants.TYPE_CODE_XPATH, "//*[@id='parent']/tbody/tr[1]/td[5]/a")
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
            self.performButtonClick_web(NBSConstants.TYPE_CODE_LINK_TEXT,
                                        "Return to Documents Requiring Security Assignment")
        except:
            return False
        return True

    def validateAssociateWithLInkInTheResultsPage(self):
        try:
            self.performButtonClick(NBSConstants.TYPE_CODE_LINK_TEXT, self.column7_name)
            self.performButtonClick(NBSConstants.TYPE_CODE_LINK_TEXT, self.column7_name)
            element = self.findElement(NBSConstants.TYPE_CODE_XPATH, "//*[@id='parent']/tbody/tr[1]/td[8]/a")
            if element is not None and len(element.text.strip()) > 0:
                element.click()
                self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
                self.performButtonClick_web(NBSConstants.TYPE_CODE_LINK_TEXT, "Return to Documents Requiring Review")
        except:
            return True
        return True

    def validateMarkAsReviewdSubFormOnTheResultsPage(self):
        try:
            randomly_select_from_std_list = None
            randomly_select_from_list = None
            first_page_records = self.getPageRecordsSize()
            std_list_records = []
            non_std_list_records = []
            for index in range(1, int(first_page_records) + 1):
                std_program_area = self.findElement(NBSConstants.TYPE_CODE_XPATH,
                                                    "//*[@id='parent']/tbody/tr[" + str(index) + "]/td[10]")
                if std_program_area.get_attribute("innerText").strip() == "true":
                    std_list_records.append(index)
                else:
                    non_std_list_records.append(index)

            # Mark As Reviewed for HIV/STD Records if there are any
            if std_list_records is not None and len(std_list_records) >= 2:
                randomly_select_from_std_list = random.sample(std_list_records, 2)
            elif std_list_records is not None and len(std_list_records) == 1:
                randomly_select_from_std_list = [std_list_records[0]]
            if randomly_select_from_std_list is not None and len(randomly_select_from_std_list) > 0:
                self.performMarkAsReviewdOnSelectedRecord(randomly_select_from_std_list, std_list="true")
                self.logger.info("Bulk STD Records are Mark As Reviewd")

            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)

            # Mark As Reviewed for Non HIV/STD Records if there are any
            if non_std_list_records is not None and len(non_std_list_records) >= 2:
                randomly_select_from_list = random.sample(non_std_list_records, 2)
            elif non_std_list_records is not None and len(non_std_list_records) == 1:
                randomly_select_from_list = [non_std_list_records[0]]
            if randomly_select_from_list is not None and len(randomly_select_from_list) > 0:
                self.performMarkAsReviewdOnSelectedRecord(randomly_select_from_list, std_list="false")
                self.logger.info("Bulk Non STD RRecords are Mark As Reviewd")
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
        except:
            return False
        return True

    def performMarkAsReviewdOnSelectedRecord(self, records_list, std_list):
        self.logger.info(records_list)
        if len(records_list) == 2:
            # select random record
            self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                    "//*[@id='parent']/tbody/tr[" + str(records_list[0]) + "]/td[1]/input")
            # select random record
            self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                    "//*[@id='parent']/tbody/tr[" + str(records_list[1]) + "]/td[1]/input")
        else:
            # select random record
            self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                    "//*[@id='parent']/tbody/tr[" + str(records_list[0]) + "]/td[1]/input")

        # click on Mark As Reviewed
        self.performButtonClick(NBSConstants.TYPE_CODE_XPATH, "//*[@id='removeFilters']/table/tbody/tr/td[1]/a")

        self.logger.info(std_list)

        if std_list == "true":
            # Processing Decision Dropdown random select
            self.selectDropDownOptionValueRandom("processingDecisionStyleSTD")
            # add comments
            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                        NBSConstants.TYPE_CODE_ID,
                                        "markAsReviewedCommentsSTD",
                                        "Comments:" + NBSUtilities.generate_random_string(65))
        else:
            # Processing Decision Dropdown random select
            self.selectDropDownOptionValueRandom("processingDecisionStyle")
            # add comments
            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                        NBSConstants.TYPE_CODE_ID,
                                        "markAsReviewedComments",
                                        "Comments:" + NBSUtilities.generate_random_string(65))

        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)

        # Click On Submit Button
        if std_list == "true":
            self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                    "//*[@id='addMarkAsReviewedBlockSTD']/table/tbody/tr[5]/td/input[1]")
        else:
            self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                    "//*[@id='addMarkAsReviewedBlock']/table/tbody/tr[5]/td/input[1]")

        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)

    @staticmethod
    def getRandomlySelectCheckBoxOptions(check_boxes_size):
        DocumentReqAssign.logger.info(check_boxes_size)
        list_random_cb = None
        if check_boxes_size is not None and int(check_boxes_size) > 1:
            list_random_cb = random.sample(range(1, int(check_boxes_size)), 2)
        elif check_boxes_size is not None and int(check_boxes_size) == 1:
            list_random_cb = [1]
        DocumentReqAssign.logger.info(list_random_cb)
        return list_random_cb

    def getPageRecordsSize(self):
        records_per_page = DBUtilities().getSingleColumnValueByTable("config_value",
                                                                     "NBS_configuration",
                                                                     "config_key",
                                                                     "MY_PROGRAM_AREAS_INVESTIGATIONS_QUEUE_SIZE")
        total_records = self.findElement(NBSConstants.TYPE_CODE_XPATH, "//*[@id='queueCnt']").get_attribute("value")
        if int(total_records) > int(records_per_page):
            return records_per_page
        else:
            return total_records

    def validateDocumentTypeLInkInTheResultsPage(self):
        try:
            self.performButtonClick(NBSConstants.TYPE_CODE_XPATH, "//*[@id='parent']/tbody/tr[1]/td[2]/a")
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
            self.performButtonClick_web(NBSConstants.TYPE_CODE_LINK_TEXT,
                                        "Return to Documents Requiring Security Assignment")
        except:
            return False
        return True

    def validateTransferProgramAreaOnTheResultsPage(self, column_index):
        try:
            option_found = self.filterByCheckBoxOptionByColumn(column_index,["Lab Report"])
            self.logger.info(option_found)
            # get filtered record size
            first_page_records = self.getPageRecordsSize()
            if option_found and first_page_records is not None:
                if int(first_page_records) > 1:
                    # select two records
                    self.performButtonClick(NBSConstants.TYPE_CODE_XPATH, "//*[@id='parent']/tbody/tr[1]/td[1]/input")
                    self.performButtonClick(NBSConstants.TYPE_CODE_XPATH, "//*[@id='parent']/tbody/tr[2]/td[1]/input")
                elif int(first_page_records) == 1:
                    # select one records
                    self.performButtonClick(NBSConstants.TYPE_CODE_XPATH, "//*[@id='parent']/tbody/tr[1]/td[1]/input")
                # click on Transfer Program Area, opens transfer program area subform
                self.performButtonClick(NBSConstants.TYPE_CODE_LINK_TEXT, "Transfer Program Area")
                # Subform , select program area randomly
                self.selectDropDownOptionValueRandom("programAreaStyleP")
                self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                NBSConstants.WAITING_TIME_IN_SECONDS)
                # click on submit
                self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                        "//*[@id='addProgramAreaBlock']/table/tbody/tr[4]/td/input[1]")

                self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                NBSConstants.WAITING_TIME_IN_SECONDS)
        except:
            return False
        return True

    def filterByCheckBoxOptionByColumn(self, column_index, options):
        option_found = False
        # Transfer Program Area works only with LAb Records, so filter Lab records
        self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                "//*[@id='parent']/thead/tr/th[" + str(column_index) + "]/img")
        checkbox_options_popup = self.checkbox_popup_options.replace("REPLACE_INDEX", column_index)
        select_all = self.findElement(NBSConstants.TYPE_CODE_XPATH, checkbox_options_popup + "[2]/input")
        option_checkboxes = self.findElements(NBSConstants.TYPE_CODE_XPATH, checkbox_options_popup)
        if len(option_checkboxes) > 3:
            # unselect all options
            select_all.click()
            popup_options = self.findElements(NBSConstants.TYPE_CODE_XPATH, checkbox_options_popup
                                              + "[@class='pText']")
            for option in popup_options:
                if option.text.strip() == options[0]:
                    option.click()
                    option_found = True
                    break
        if option_found:
            # click OK Button, it should filter results grid
            WebDriverWait(self.driver, 25).until(
                EC.element_to_be_clickable((By.XPATH, checkbox_options_popup + "[1]/input[1]"))).click()
        else:
            # click Cancel Button, it will go back to original state
            WebDriverWait(self.driver, 25).until(
                EC.element_to_be_clickable((By.XPATH, checkbox_options_popup + "[1]/input[2]"))).click()
        return option_found

    def validateTransferJurisdictionOnTheResultsPage(self):
        try:
            # get filtered record size
            first_page_records = self.getPageRecordsSize()
            if first_page_records is not None:
                if int(first_page_records) > 1:
                    # select first two records
                    self.performButtonClick(NBSConstants.TYPE_CODE_XPATH, "//*[@id='parent']/tbody/tr[1]/td[1]/input")
                    self.performButtonClick(NBSConstants.TYPE_CODE_XPATH, "//*[@id='parent']/tbody/tr[2]/td[1]/input")
                elif int(first_page_records) == 1:
                    # select one record
                    self.performButtonClick(NBSConstants.TYPE_CODE_XPATH, "//*[@id='parent']/tbody/tr[1]/td[1]/input")
                # click on Transfer Program Area, opens transfer Jurisdiction subform
                self.performButtonClick(NBSConstants.TYPE_CODE_LINK_TEXT, "| Transfer Jurisdiction")
                # Subform , select program area randomly
                self.selectDropDownOptionValueRandom("jurisdictionStyleJ")
                self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                NBSConstants.WAITING_TIME_IN_SECONDS)
                # click on submit
                self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                        "//*[@id='addJurisdictionBlock']/table/tbody/tr[4]/td/input[1]")

                self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                NBSConstants.WAITING_TIME_IN_SECONDS)
        except:
            # self.logger.info("Exception While validateTransferJurisdictionOnTheResultsPage")
            return False
        return True

    def validateTransferOwnerShipOnTheResultsPage(self, column_index):
        try:
            option_found = self.filterByCheckBoxOptionByColumn(column_index, ["Lab Report"])
            self.logger.info(option_found)
            # get filtered record size
            first_page_records = self.getPageRecordsSize()
            if option_found and first_page_records is not None:
                if int(first_page_records) > 1:
                    # select two records
                    self.performButtonClick(NBSConstants.TYPE_CODE_XPATH, "//*[@id='parent']/tbody/tr[1]/td[1]/input")
                    self.performButtonClick(NBSConstants.TYPE_CODE_XPATH, "//*[@id='parent']/tbody/tr[2]/td[1]/input")
                elif int(first_page_records) == 1:
                    # select one records
                    self.performButtonClick(NBSConstants.TYPE_CODE_XPATH, "//*[@id='parent']/tbody/tr[1]/td[1]/input")
                # click on Transfer Program Area, opens transfer program area subform
                self.performButtonClick(NBSConstants.TYPE_CODE_LINK_TEXT, "| Transfer Ownership")
                # Subform , select program area randomly
                self.selectDropDownOptionValueRandom("programAreaStyle")
                self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                NBSConstants.WAITING_TIME_IN_SECONDS)
                # Subform , select jurisdiction randomly
                self.selectDropDownOptionValueRandom("jurisdictionStyle")
                # click on submit
                self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                        "//*[@id='addAttachmentBlock']/table/tbody/tr[5]/td/input[1]")

                self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                NBSConstants.WAITING_TIME_IN_SECONDS)
        except:
            return False
        return True

    # Print and Export buttons are freezing when click those buttons..
    # Website itself has problem of these buttons for DRRQ and DRSA
    # When buttons issues are fixed in the original website,
    # please remove this function, the function in the Base Object will be called.
    def validatePrintAndExportInTheResultsPage(self):
        return False
