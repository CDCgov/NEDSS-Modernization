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


class OpenInvestigations(BasePageObject):
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
    OPEN_INVESTIGATION_XPATH = "//*[@id='myQueues']/div/div[3]/ul[1]/li[1]/a"

    # Sort/filter Results Grid
    column1_name = "Start Date"
    column2_name = "Investigator"
    column3_name = "Jurisdiction"
    column4_name = "Patient"
    column5_name = "Condition"
    column6_name = "CaseStatus"
    column7_name = "Notification"
    column8_name = "Investigation ID"

    # Default Sort
    column1_default_sort = "Yes-D"
    column2_default_sort = "No"
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
    column3_filter_type = "checkbox"
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
            self.column1_name: [self.column1_default_sort, self.column1_xpath],
            self.column2_name: [self.column2_default_sort, self.column2_xpath],
            self.column3_name: [self.column3_default_sort, self.column3_xpath],
            self.column4_name: [self.column4_default_sort, self.column4_xpath],
            self.column5_name: [self.column5_default_sort, self.column5_xpath],
            self.column6_name: [self.column6_default_sort, self.column6_xpath],
            self.column7_name: [self.column7_default_sort, self.column7_xpath],
            self.column8_name: [self.column8_default_sort, self.column8_xpath]}

        self.sortResultsGridColumnsOneByOne(grid_columns, "Asc")

        return True

    def validateFilterOnResultsGrid(self):
        # validate filters with first row data for each column
        filter_columns = {
            self.column1_name: [self.column1_filter_type, self.column1_xpath, self.column1_header_index, 0],
            self.column2_name: [self.column2_filter_type, self.column2_xpath, self.column2_header_index, 0],
            self.column3_name: [self.column3_filter_type, self.column3_xpath, self.column3_header_index, 0],
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
            select_checkbox_list = self.getRandomlySelectCheckBoxOptions(
                option_checkboxes_correct_size)  # multiple checkboxes
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

    """def performCheckboxFilterOnTheResultsGrid(self, replace_index):
        checkbox_options_popup = self.checkbox_popup_options.replace("REPLACE_INDEX", replace_index)
        select_all = self.findElement(NBSConstants.TYPE_CODE_XPATH, checkbox_options_popup + "[2]/input")
        option_checkboxes = self.findElements(NBSConstants.TYPE_CODE_XPATH, checkbox_options_popup)
        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
        if len(option_checkboxes) > 1:
            # unselect all options
            select_all.click()
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
            # randomly select check box option
            # select_checkbox = random.randint(2, len(option_checkboxes) - 1)
            # self.performButtonClick(NBSConstants.TYPE_CODE_XPATH, checkbox_options_popup + "[" + str(
            # select_checkbox) + "]/input")
            select_checkbox_list = self.getRandomlySelectCheckBoxOptions(len(option_checkboxes))  # multiple checkboxes
            if len(select_checkbox_list) > 1:
                WebDriverWait(self.driver, 25).until(
                    EC.element_to_be_clickable((By.XPATH,
                                                checkbox_options_popup + "[" + str(
                                                    select_checkbox_list[0]) + "]/input"))).click()
                WebDriverWait(self.driver, 25).until(
                    EC.element_to_be_clickable((By.XPATH,
                                                checkbox_options_popup + "[" + str(
                                                    select_checkbox_list[1]) + "]/input"))).click()
            elif len(select_checkbox_list) == 1:
                WebDriverWait(self.driver, 25).until(
                    EC.element_to_be_clickable((By.XPATH,
                                                checkbox_options_popup + "[" + str(
                                                    select_checkbox_list[0]) + "]/input"))).click()

            # click OK Button, it should filter results grid
            WebDriverWait(self.driver, 25).until(
                EC.element_to_be_clickable((By.XPATH, checkbox_options_popup + "[1]/input[1]"))).click()

            # self.performButtonClick(NBSConstants.TYPE_CODE_XPATH, checkbox_options_popup + "[1]/input[1]")
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
            return True
        return False"""

    def performTextFieldFilterOnTheResultsGrid(self, replace_index, key):
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
        # self.logger.info(search_text)

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
            self.performButtonClick_web(NBSConstants.TYPE_CODE_LINK_TEXT, "Return to Open Investigations")
        except:
            return False
        return True

    def validateConditionLInkInTheResultsPage(self):
        try:
            self.performButtonClick(NBSConstants.TYPE_CODE_XPATH, "//*[@id='parent']/tbody/tr[1]/td[6]/a")
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
            self.performButtonClick_web(NBSConstants.TYPE_CODE_LINK_TEXT, "Return to Open Investigations")
        except:
            return False
        return True

    def validateAssignSubFormOnTheResultsPage(self):
        try:
            investigator_last_name = None
            first_page_records = self.getPageRecordsSize()
            if int(first_page_records) > 2:
                row = random.randint(2, int(first_page_records) - 1)
            else:
                row = 2
            investigator_search_text = self.findElement(NBSConstants.TYPE_CODE_XPATH,
                                                        "//*[@id='parent']/tbody/tr[1]/td[3]")
            if investigator_search_text is not None and len(investigator_search_text.text) > 0:
                investigator_last_name = investigator_search_text.text.split(",")[0]
            else:
                self.performButtonClick(NBSConstants.TYPE_CODE_LINK_TEXT, self.column4_name)
                investigator_search_text = self.findElement(NBSConstants.TYPE_CODE_XPATH,
                                                            "//*[@id='parent']/tbody/tr[1]/td[3]")
                if investigator_search_text is not None and len(investigator_search_text.text) > 0:
                    investigator_last_name = investigator_search_text.text.split(",")[0]
            # select random record
            self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                    "//*[@id='parent']/tbody/tr[" + str(row) + "]/td[1]/input")

            # click assign link which opens the sub form
            self.performButtonClick(NBSConstants.TYPE_CODE_XPATH, "//*[@id='removeFilters']/table/tbody/tr/td[1]/a")

            # ************ Assign Investigator *************
            # Quick Code Search By Number
            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                        NBSConstants.TYPE_CODE_ID,
                                        "INV207Text",
                                        "3")

            # Quick Code Search Button
            self.performButtonClick(NBSConstants.TYPE_CODE_ID, "INV207CodeLookupButton")
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)

            # clear selected ordering facility
            self.performButtonClick(NBSConstants.TYPE_CODE_ID, "INV207CodeClearButton")

            # Quick Code Search By Search Button
            self.getInvestigatorDetails(investigator_last_name, path_id="INV207Icon")
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)

            # Date Assigned to Investigation

            if self.checkDateIsExisted(NBSConstants.TYPE_CODE_ID, "INV110"):
                selected_birth_day, selected_birth_month, selected_birth_year = NBSUtilities.getRandomDateByVars()
                self.selectCalendarDate(NBSConstants.TYPE_CODE_ID,
                                        "INV110Icon",
                                        str(int(selected_birth_day)),
                                        selected_birth_month,
                                        str(int(selected_birth_year)))

            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
            # submit form
            self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                    "//*[@id='assignInvestigator']/table/tbody/tr[7]/td/input[1]")

            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
        except:
            return False
        return True

    def getInvestigatorDetails(self, last_name, path_id):
        self.logger.info("Last Name:::::::" + str(last_name))
        if last_name is None:
            last_name = "Keable"
        if path_id is not None:
            self.performButtonClick(NBSConstants.TYPE_CODE_ID, path_id)
            original_window = self.driver.current_window_handle
            try:
                WebDriverWait(self.driver, 10).until(EC.number_of_windows_to_be(2))
                for window_handle in self.driver.window_handles:
                    if window_handle != original_window:
                        self.driver.switch_to.window(window_handle)
                        # Search Screen
                        self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                                    NBSConstants.TYPE_CODE_NAME,
                                                    "providerSearch.lastName",
                                                    str(last_name))
                        self.performButtonClick(NBSConstants.TYPE_CODE_NAME, "Submit")

                        # Results Screen
                        provider_uid = DBUtilities().getSingleColumnValueByTable("person_uid", "person",
                                                                                 "last_nm", last_name)
                        # self.logger.info("Investigator UID::::" + str(provider_uid))
                        elem = self.findElementInMultiplePagesByText(str(provider_uid))
                        if elem is not None:
                            elem.click()
                self.driver.switch_to.window(original_window)
            except Exception as ex:
                self.logger.debug("Exception while getting Investigator details:::", str(ex))
                self.driver.switch_to.window(original_window)
                pass
            return True

    """@staticmethod
    def getRandomlySelectCheckBoxOptions(check_boxes_size):
        list_random_cb = None
        if check_boxes_size is not None and int(check_boxes_size) > 2:
            list_random_cb = random.sample(range(2, int(check_boxes_size) - 1), 2)
        elif check_boxes_size is not None and int(check_boxes_size) == 2:
            list_random_cb = [2]

        return list_random_cb"""

    @staticmethod
    def getRandomlySelectCheckBoxOptions(check_boxes_size):
        list_random_cb = None
        if check_boxes_size is not None and int(check_boxes_size) > 1:
            list_random_cb = random.sample(range(1, int(check_boxes_size)), 2)
        elif check_boxes_size is not None and int(check_boxes_size) == 1:
            list_random_cb = [1]

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
