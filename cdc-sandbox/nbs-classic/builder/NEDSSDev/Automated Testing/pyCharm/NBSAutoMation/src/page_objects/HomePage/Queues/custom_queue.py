import time

from selenium.common import NoSuchElementException
from selenium.webdriver.common.by import By
from selenium.webdriver.support.wait import WebDriverWait
from src.page_objects.BasePageObject import BasePageObject
from src.page_objects.HomePage.Queues.openinvestigation import OpenInvestigations
from src.utilities.constants import NBSConstants

from src.utilities.log_config import LogUtils
import random
from selenium.webdriver.support import expected_conditions as EC


class CustomQueue(BasePageObject):
    logger = LogUtils.loggen(__name__)

    def __init__(self, driver):
        super().__init__(driver)
        self.driver = driver
        self.openinvestigation = OpenInvestigations(self.driver)

    # locators
    select_all_popup_checkbox = "//*[@id='searchResultsTable']/thead/tr/th[REPLACE_INDEX]/div/label[2]/input"
    checkbox_popup_options = "//*[@id='searchResultsTable']/thead/tr/th[REPLACE_INDEX]/div/label"
    ok_button_on_the_popup = "//*[@id='searchResultsTable']/thead/tr/th[REPLACE_INDEX]/div/label[1]/input[1]"
    cancel_button_on_the_popup = "//*[@id='searchResultsTable']/thead/tr/th[REPLACE_INDEX]/div/label[1]/input[2]"
    text_filed_search_text = "//*[@id='{0}' and @name='answerArrayText({0})']"
    text_filed_search_ok_button = "//*[@id='b2{}']"
    ADVANCED_SEARCH_XPATH = '//*[@id="homePageAdvancedSearch"]'
    EVENT_SEARCH_ID=  "tabs0head1"
    EVENT_TYPE_DROPDOWN_XPATH=   '//*[@id="patSearch6"]/tbody/tr/td[2]/input'
    SAVE_GIF_XPATH= "//*[@src='save_with_text.gif']"
    NAME_QUEUE_XPATH = '//*[@id="nameQueue"]'
    DESCRIPTION_QUEUE_XPATH = '//*[@id="descriptionQueue"]'
                # select public radio button
    PUBLIC_RADIO_BUTTON_XPATH    =    '// *[ @ id = "publicId"]'
                # click submit
    SAVE_XPATH = '//*[@id="saveQueueBox"]/table/tbody/tr[5]/td/input[1]'
                #return to homepage
    RETUNR_HOME_XPATH ='//*[@id="bd"]/table/tbody/tr/td[1]/table/tbody/tr/td[1]/a'
    PUBLIC_CUSTOM_QUEUE_XPATH = '//*[@id="myQueues"]/div/div[3]/b[3]/font'
    PUBLIC_CUSTOM_QUEUE_ID ='//*[@id="myInputPublic"]'
    COND_XPATH= '//*[@id="COND"]'
    
    
    
    # Sort/filter Results Grid
    column1_name = "Start Date"
    column2_name = "Investigator"
    column3_name = "Jurisdiction"
    column4_name = "Patient"
    column5_name = "Condition"
    column6_name = "Case Status"
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
    column1_xpath = '//*[@id="searchResultsTable"]/thead/tr/th[1]/img'
    column2_xpath = '//*[@id="searchResultsTable"]/thead/tr/th[2]/img'
    column3_xpath = '//*[@id="searchResultsTable"]/thead/tr/th[3]/img'
    column4_xpath = '//*[@id="searchResultsTable"]/thead/tr/th[4]/img'
    column5_xpath = '//*[@id="searchResultsTable"]/thead/tr/th[5]/img'
    column6_xpath = '//*[@id="searchResultsTable"]/thead/tr/th[6]/img'
    column7_xpath = '//*[@id="searchResultsTable"]/thead/tr/th[7]/img'
    column8_xpath = '//*[@id="searchResultsTable"]/thead/tr/th[8]/img'

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
    column1_header_index = 1
    column2_header_index = 2
    column3_header_index = 3
    column4_header_index = 4  # changed for replace index for path
    column5_header_index = 5
    column6_header_index = 6
    column7_header_index = 7
    column8_header_index = 8  # changed for replace index for path

    # Columen id for text field
    column4_id = "Patient"
    column8_id = "LocalIdSearchInv"

    def validateSortingOnResultsGrid(self):
        # validate columns sort for results grid column name --default sort
        grid_columns1 = {
            self.column1_name: [self.column1_default_sort, self.column1_xpath],
            self.column2_name: [self.column2_default_sort, self.column2_xpath],
            self.column3_name: [self.column3_default_sort, self.column3_xpath],
            self.column4_name: [self.column4_default_sort, self.column4_xpath],
            self.column5_name: [self.column5_default_sort, self.column5_xpath],
            self.column6_name: [self.column6_default_sort, self.column6_xpath],
            self.column7_name: [self.column7_default_sort, self.column7_xpath],
            self.column8_name: [self.column8_default_sort, self.column8_xpath]
        }
        self.sortResultsGridColumnsOneByOne(grid_columns1, "Asc")

        return True

    def validateFilterOnResultsGrid(self):
        # validate filters with first row data for each column
        filter_columns = {
            self.column1_name: [self.column1_filter_type, self.column1_xpath, self.column1_header_index, 0],
            self.column2_name: [self.column2_filter_type, self.column2_xpath, self.column2_header_index, 0],
            self.column3_name: [self.column3_filter_type, self.column3_xpath, self.column3_header_index, 0],
            self.column4_name: [self.column4_filter_type, self.column4_xpath, self.column4_header_index, 1,
                                self.column4_id],
            self.column5_name: [self.column5_filter_type, self.column5_xpath, self.column5_header_index, 0],
            self.column6_name: [self.column6_filter_type, self.column6_xpath, self.column6_header_index, 0],
            self.column7_name: [self.column7_filter_type, self.column7_xpath, self.column7_header_index, 0],
            self.column8_name: [self.column8_filter_type, self.column8_xpath, self.column8_header_index, 2,
                                self.column8_id]}

        self.customQueuefilterResultsGridDataByColumn(filter_columns)

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

    def customQueuefilterResultsGridDataByColumn(self, filter_columns):
        for key, value in filter_columns.items():
            if str(value[0]) == "checkbox":
                self.performButtonClick(NBSConstants.TYPE_CODE_XPATH, str(value[1]))
                self.performColumnCheckBoxPopupSearch("OPENINV", str(key), value)
            elif str(value[0]) == "textfield":
                self.performButtonClick(NBSConstants.TYPE_CODE_XPATH, str(value[1]))
                self.customQueuePerformColumnTextFieldPopupSearch("OPENINV", str(key), value)
            time.sleep(1)
            # click on Remove All Filter/Sorts
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
            WebDriverWait(self.driver, 17).until(
                EC.element_to_be_clickable((By.LINK_TEXT, "| Remove All Filters/Sorts"))).click()
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)

    def customQueuePerformColumnTextFieldPopupSearch(self, component_name, key, value_list):
        if component_name == "OPENINV":
            if self.customQueueTextFieldFilterOnTheResultsGrid(key, value_list[4]):
                self.logger.info(str(key) + " Results Grid Filter Test Passed")

    def customQueueTextFieldFilterOnTheResultsGrid(self, key, column_id):
        search_text_element = self.text_filed_search_text.format(column_id)
        search_text_ok_button = self.text_filed_search_ok_button.format(column_id)
        self.setValueForHTMLElement("TEXTBOX", NBSConstants.TYPE_CODE_XPATH,
                                    search_text_element, self.getSearchText(key))
        # self.performButtonClick(NBSConstants.TYPE_CODE_XPATH, search_text_ok_button)
        WebDriverWait(self.driver, 25).until(
            EC.element_to_be_clickable((By.XPATH, search_text_ok_button))).click()
        return True

    def getSearchText(self, key):
        search_text = None
        if key == "Patient":
            search_text = (
                self.findElement(NBSConstants.TYPE_CODE_XPATH, '//*[@id="searchResultsTable"]/tbody/tr[1]/td[4]/a')
                .get_attribute('text'))
        elif key == "Investigation ID":
            search_text = self.findElement(NBSConstants.TYPE_CODE_XPATH,
                                           '//*[@id="searchResultsTable"]/tbody/tr[1]/td[8]').text
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
            self.performButtonClick(NBSConstants.TYPE_CODE_XPATH, '//*[@id="searchResultsTable"]/tbody/tr[1]/td[4]/a')
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
            self.performButtonClick_web(NBSConstants.TYPE_CODE_XPATH,
                                        '//*[@id="bd"]/table[1]/tbody/tr/td[1]/table/tbody/tr/td[1]/a')
            self.driver.find_element(By.XPATH, '//*[@id="myQueues"]/div/div[3]/b[3]/font').click()


        except:
            return False
        return True

    def validateConditionLInkInTheResultsPage(self):
        try:
            self.performButtonClick(NBSConstants.TYPE_CODE_XPATH, '//*[@id="searchResultsTable"]/tbody/tr[1]/td[5]/a')
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
            self.performButtonClick_web(NBSConstants.TYPE_CODE_XPATH, '//*[@id="bd"]/div[1]/a[2]')
        except:
            return False
        return True

    @staticmethod
    def getRandomlySelectCheckBoxOptions(check_boxes_size):
        list_random_cb = None
        if check_boxes_size is not None and int(check_boxes_size) > 1:
            list_random_cb = random.sample(range(1, int(check_boxes_size)), 2)
        elif check_boxes_size is not None and int(check_boxes_size) == 1:
            list_random_cb = [1]

        return list_random_cb

    def validateDeleteQueue(self):
        self.driver.find_element(By.XPATH, "//*[@title='Delete custom queue']").click()
        time.sleep(2)
        self.driver.switch_to.alert.accept()
