import time

from selenium.common import NoSuchElementException
from selenium.webdriver import ActionChains
from selenium.webdriver.common.by import By
from selenium.webdriver.support.wait import WebDriverWait
from src.page_objects.BasePageObject import BasePageObject
from src.utilities.constants import NBSConstants

from src.utilities.log_config import LogUtils
import random
from selenium.webdriver.support import expected_conditions as EC
from src.utilities.nbs_utilities import NBSUtilities


class SupervisorQueue(BasePageObject):
    logger = LogUtils.loggen(__name__)

    def __init__(self, driver):
        super().__init__(driver)
        self.driver = driver

    # locators
    select_all_popup_checkbox = '//*[@id="filterTopDiv"]/div[{}]/table/tbody/tr[1]/td[1]/input'
    checkbox_popup_options = '//*[@id="filterTopDiv"]/div[{}]/table/tbody/tr'
    ok_button_on_the_popup = '//*[@id="filterTopDiv"]/span/input[1]'
    cancel_button_on_the_popup = '//*[@id="filterTopDiv"]/span/input[2]'
    text_filed_search_text = "//*[@id='{}']/label/input"
    SUPERVISORY_REVIEW_QUEUE = '//*[@id="myQueues"]/div/div[3]/ul[1]/li[8]/a'

    # Sort/filter Results Grid
    column1_name = "Submit Date"
    column2_name = "Supervisor"
    column3_name = "Investigator"
    column4_name = "Patient"
    column5_name = "Condition"
    column6_name = "Referral Basis"
    column7_name = "Type"

    # Sort/filter Results Grid by Id
    column1_name_id = "submitDate"
    column2_name_id = "supervisor"
    column3_name_id = "investigator"
    column4_name_id = "patient"
    column5_name_id = "textCond"
    column6_name_id = "referralBasis"
    column7_name_id = "activityType"


    # Default Sort
    column1_default_sort = "Yes-A"
    column2_default_sort = "No"
    column3_default_sort = "No"
    column4_default_sort = "No"
    column5_default_sort = "No"
    column6_default_sort = "No"
    column7_default_sort = "No"

    # sort/filter image xpath
    column1_xpath = "//*[@id='parent']/thead/tr/th[3]/img"
    column2_xpath = "//*[@id='parent']/thead/tr/th[4]/img"
    column3_xpath = "//*[@id='parent']/thead/tr/th[5]/img"
    column4_xpath = "//*[@id='parent']/thead/tr/th[6]/img"
    column5_xpath = "//*[@id='parent']/thead/tr/th[7]/img"
    column6_xpath = "//*[@id='parent']/thead/tr/th[8]/img"
    column7_xpath = "//*[@id='parent']/thead/tr/th[9]/img"

    # filter type
    column1_filter_type = "checkbox"
    column2_filter_type = "checkbox"
    column3_filter_type = "checkbox"
    column4_filter_type = "textfield"
    column5_filter_type = "checkbox"
    column6_filter_type = "checkbox"
    column7_filter_type = "checkbox"

    # filter select all and options header index
    column1_header_index = 1
    column2_header_index = 5
    column3_header_index = 4
    column4_header_index = 3
    column5_header_index = 2
    column6_header_index = 6
    column7_header_index = 7

    def validateSortingOnResultsGrid(self):
        # validate columns sort for results grid column name --default sort
        grid_columns1 = {

            self.column2_name: [self.column2_default_sort, self.column2_xpath],

            self.column4_name: [self.column4_default_sort, self.column4_xpath],
           }
        grid_columns2 = {
            self.column3_name: [self.column3_default_sort, self.column3_xpath],
            self.column5_name: [self.column5_default_sort, self.column5_xpath],
            self.column6_name: [self.column6_default_sort, self.column6_xpath],
            self.column7_name: [self.column7_default_sort, self.column7_xpath]}
        time.sleep(1)
        self.sortResultsGridColumnsOneByOne(grid_columns1, "Asc")
        self.sortResultsGridColumnsOneByOne(grid_columns2, "Desc")
        return True

    def validateFilterOnResultsGrid(self):
        # validate filters with first row data for each column
        filter_columns = {
            self.column1_name: [self.column1_filter_type, self.column1_xpath, self.column1_header_index, 0,
                                self.column1_name_id],
            self.column2_name: [self.column2_filter_type, self.column2_xpath, self.column2_header_index, 0,
                                self.column2_name_id],
            self.column3_name: [self.column3_filter_type, self.column3_xpath, self.column3_header_index, 1,
                                self.column3_name_id],
            self.column4_name: [self.column4_filter_type, self.column4_xpath, self.column4_header_index, 1,
                                self.column4_name_id],
            self.column5_name: [self.column5_filter_type, self.column5_xpath, self.column5_header_index, 1,
                                self.column5_name_id],
            self.column6_name: [self.column6_filter_type, self.column6_xpath, self.column6_header_index, 0,
                                self.column6_name_id],
            self.column7_name: [self.column7_filter_type, self.column7_xpath, self.column7_header_index, 0,
                                self.column7_name_id]
        }
        self.supQueueFilterResultsGridDataByColumn(filter_columns)

        return True

    def supQueueFilterResultsGridDataByColumn(self, filter_columns):
        for key, value in filter_columns.items():
            if str(value[0]) == "checkbox":
                self.performButtonClick(NBSConstants.TYPE_CODE_XPATH, str(value[1]))
                time.sleep(1)
                self.supQueuePerformColumnCheckBoxPopupSearch("OPENINV", str(key), value)
            elif str(value[0]) == "textfield":
                self.performButtonClick(NBSConstants.TYPE_CODE_XPATH, str(value[1]))
                self.supQueuePerformColumnTextFieldPopupSearch("OPENINV", str(key), value)
            time.sleep(1)
            # click on Remove All Filter/Sorts
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
            WebDriverWait(self.driver, 17).until(
                EC.element_to_be_clickable((By.LINK_TEXT, "| Remove All Filters/Sorts"))).click()
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
            # self.performButtonClick(NBSConstants.TYPE_CODE_CLASS_NAME, "removefilerLink"

    def supQueuePerformColumnCheckBoxPopupSearch(self, component_name, key, value_list):
        if component_name == "OPENINV":
            if self.supQueuePerformCheckboxFilterOnTheResultsGrid(value_list[2]):
                self.logger.info(str(key) + " Results Grid Filter Test Passed")

    def supQueuePerformColumnTextFieldPopupSearch(self, component_name, key, value_list):
        if component_name == "OPENINV":
            if self.supQueuePerformTextFieldFilterOnTheResultsGrid(key, value_list[4]):
                self.logger.info(str(key) + " Results Grid Filter Test Passed")

    def supQueuePerformCheckboxFilterOnTheResultsGrid(self, replace):
        checkbox_options_popup = self.checkbox_popup_options.format(replace)
        select_options = self.select_all_popup_checkbox.format(replace)
        # print("checkbox_options_popup", checkbox_options_popup)
        # print("Selectoptions", select_options)
        select_all = self.driver.find_element(By.XPATH, select_options)
        option_checkboxes = self.driver.find_elements(By.XPATH, checkbox_options_popup)
        # print("option_checkboxes", len(option_checkboxes))
        # self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
        if len(option_checkboxes) > 5:
            # unselect all options
            # select_all = self.driver.find_element(NBSConstants.TYPE_CODE_XPATH, select_options)
            select_all.click()
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
            # option_checkboxes_correct_size = len(self.findElements(NBSConstants.TYPE_CODE_XPATH, checkbox_options_popup
            #                                                        + "[@class='pText']"))
            select_checkbox_list = self.getRandomlySelectCheckBoxOptions(len(option_checkboxes) - 1)
            # print("select_checkbox_list", select_checkbox_list)
            if select_checkbox_list is not None:
                if len(select_checkbox_list) > 1:

                    WebDriverWait(self.driver, 25).until(
                        EC.element_to_be_clickable((By.XPATH,
                                                    checkbox_options_popup + "[" + str(
                                                        int(select_checkbox_list[0]) + 1) + "]/td/input"))).click()
                    WebDriverWait(self.driver, 25).until(
                        EC.element_to_be_clickable((By.XPATH,
                                                    checkbox_options_popup + "[" + str(
                                                        int(select_checkbox_list[1]) + 1) + "]/td/input"))).click()
                elif len(select_checkbox_list) == 1:
                    WebDriverWait(self.driver, 25).until(
                        EC.element_to_be_clickable((By.XPATH,
                                                    checkbox_options_popup + "[" + str(
                                                        int(select_checkbox_list[0]) + 2) + "]td/input"))).click()

        # click OK Button, it should filter results grid

        if len(option_checkboxes) <= 5:
            select_all.click()

        WebDriverWait(self.driver, 25).until(
            EC.element_to_be_clickable((By.XPATH, self.ok_button_on_the_popup))).click()

        # self.performButtonClick(NBSConstants.TYPE_CODE_XPATH, checkbox_options_popup + "[1]/input[1]")
        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                        NBSConstants.WAITING_TIME_IN_SECONDS)
        return True

    def supQueuePerformTextFieldFilterOnTheResultsGrid(self, key, column_id):
        search_text_element = self.text_filed_search_text.format(column_id)
        # print("search_text_element", search_text_element)
        search_text_ok_button = self.ok_button_on_the_popup
        self.setValueForHTMLElement("TEXTBOX", NBSConstants.TYPE_CODE_XPATH,
                                    search_text_element, self.getSearchText(key))
        # self.performButtonClick(NBSConstants.TYPE_CODE_XPATH, search_text_ok_button)
        WebDriverWait(self.driver, 25).until(
            EC.element_to_be_clickable((By.XPATH, search_text_ok_button))).click()
        return True

    def validatePatientLInkInTheResultsPage(self):
        try:
            self.performButtonClick(NBSConstants.TYPE_CODE_XPATH, '//*[@id="parent"]/tbody/tr[1]/td[6]/a')
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
            self.performButtonClick_web(NBSConstants.TYPE_CODE_LINK_TEXT,
                                        "Return to Supervisor Review Queue")
        except:
            return False
        return True

    def getSearchText(self, key):
        search_text = None
        if key == "Patient":
            search_text = (self.findElement(NBSConstants.TYPE_CODE_XPATH, '//*[@id="parent"]/tbody/tr[1]/td[6]/a')
                           .get_attribute('text'))
        self.logger.info(search_text)

        if search_text is not None:
            return search_text
        else:
            return "test"

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

    def validateConditionLInkInTheResultsPage(self):
        try:
            self.performButtonClick(NBSConstants.TYPE_CODE_XPATH, '//*[@id="parent"]/tbody/tr[1]/td[7]/a')
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
            self.performButtonClick_web(NBSConstants.TYPE_CODE_LINK_TEXT,
                                        "Return to Supervisor Review Queue")
        except:
            return False
        return True

    @staticmethod
    def getRandomlySelectCheckBoxOptions(check_boxes_size):
        SupervisorQueue.logger.info(check_boxes_size)
        list_random_cb = None
        if check_boxes_size is not None and int(check_boxes_size) > 1:
            list_random_cb = random.sample(range(1, int(check_boxes_size)), 2)
        elif check_boxes_size is not None and int(check_boxes_size) == 1:
            list_random_cb = [1]
        SupervisorQueue.logger.info(list_random_cb)
        return list_random_cb

    def validateSupervisorQueueDelete(self):
        self.performButtonClick(NBSConstants.TYPE_CODE_XPATH, '//*[@id="parent"]/tbody/tr[1]/td[2]/img')
        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                        NBSConstants.WAITING_TIME_IN_SECONDS)


        self.performActionMouseClick(By.XPATH,'//*[@name="supervisorVO.comments"]')
        element = self.driver.find_element(By.XPATH, '//*[@name="supervisorVO.comments"]')
        element.send_keys(NBSUtilities.generate_random_string(60))
        self.performActionMouseClick(NBSConstants.TYPE_CODE_XPATH,
                                '//*[@id="rejectMessageDiv"]/span/input[1]')

        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                        NBSConstants.WAITING_TIME_IN_SECONDS)
        # Results Screen
        elem = self.findElement(NBSConstants.TYPE_CODE_XPATH,
                                "//*[@id='bd']/div[2]")
        if elem is not None and len(elem.text) > 30:
            if "has been " in elem.text:
                return True
            else:
                return False

    def validateApproveNotificationOnTheResultsPage(self):
        self.performButtonClick(NBSConstants.TYPE_CODE_XPATH, '//*[@id="parent"]/tbody/tr[1]/td[1]/img')
        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                        NBSConstants.WAITING_TIME_IN_SECONDS)
        # Search Screen //*[@id="rejectMessageDiv"]/textarea
        element = self.driver.find_element(By.XPATH, '//*[@name="supervisorVO.comments"]')
        element.send_keys(NBSUtilities.generate_random_string(60))
        self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                '//*[@id="rejectMessageDiv"]/span/input[1]')

        # Results Screen
        elem = self.findElement(NBSConstants.TYPE_CODE_XPATH,
                                "//*[@id='bd']/div[2]")
        if elem is not None and len(elem.text) > 30:
            if "has been approved" in elem.text:
                return True
            else:
                return False

    def performActionMouseClick(self, type_code, path):
        element = self.findElement(type_code,path)
        # print("element",element)
        if element is not None:
            ActionChains(self.driver) \
                .double_click(element)\
                .perform()