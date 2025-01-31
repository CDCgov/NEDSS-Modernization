from selenium.common import NoSuchElementException
from selenium.webdriver.common.by import By
from src.utilities.constants import NBSConstants
from src.utilities.log_config import LogUtils
from src.page_objects.BasePageObject import BasePageObject
import time


class ManualMerge(BasePageObject):
    logger = LogUtils.loggen(__name__)

    def __init__(self, driver):
        super().__init__(driver)
        self.driver = driver

    # locators

    text_filed_search_cancel_button = '//*[@id="searchResultsTable"]/thead/tr/th[{0}]/div/label[1]/input[2]'
    text_filed_search_text = "//*[@id='SearchText{0}' and @name='answerArrayText(SearchText{0})']"
    text_filed_search_ok_button = '//*[@id="searchResultsTable"]/thead/tr/th[{0}]/div/label[1]/input[1]'
    MERGE_ID_CHECKBOX_INPUT_XPATH = '// *[ @ id = "searchResultsTable"] / tbody / tr[{0}] / td[{1}] / input'
    MERGE_ID_CHECKBOX_XPATH = '// *[ @ id = "searchResultsTable"] / tbody / tr[{0}] / td[{1}]'
    PATIENT_ID_SORT_IMG_XPATH = '// *[ @ id = "searchResultsTable"] / thead / tr / th[3] / img'
    PATIENT_ID_FILTER_OK_XPATH = '//*[@id="searchResultsTable"]/thead/tr/th[3]/div/label[1]/input[1]'
    SEARCH_RESULT_ROW_XPATH = '//*[@id="searchResultsTable"]/tbody/tr/td'
    MERGE_XPATH = '//*[@value="Merge"]'
    MERGE_PATIENT = "Merge Patients"
    MANUAL_SEARCH = "Manual Search"
    REMOVE_FILTER_XPATH = '//*[@id="removeFilters"]/div/a'
    SEARCH_ROW_COUNT_XPATH = '//*[@id="searchResultsTable"]/tbody/tr'
    RESULTS_PAGINATION_XPATH = '//*[@id="bd"]/form/table/tbody/tr/td/span[b]'
    PATIENT_ID_LINK_XPATH = '//*[@id="searchResultsTable"]/tbody/tr[1]/td[3]/a'
    NEW_REFINE_SEARCH_XPATH = ' // *[ @ id = "bd"] / div[4] / a'
    SEARCH_LINK_XPATH ='//*[@id="bd"]/div[1]/a[{i}]'
    


    # Sort/filter Results Grid
    column1_name = "Patient ID"
    column2_name = "Name"
    column3_name = "Age/DOB/Sex"
    column4_name = "Address"
    column5_name = "Phone/Email"
    column6_name = "ID"

    # Default Sort
    column1_default_sort = "Yes-D"
    column2_default_sort = "No"
    column3_default_sort = "No"
    column4_default_sort = "No"
    column5_default_sort = "No"
    column6_default_sort = "No"

    # sort/filter image xpath
    column1_xpath = '//*[@id="searchResultsTable"]/thead/tr/th[3]/img'
    column2_xpath = '//*[@id="searchResultsTable"]/thead/tr/th[4]/img'
    column3_xpath = '//*[@id="searchResultsTable"]/thead/tr/th[5]/img'
    column4_xpath = '//*[@id="searchResultsTable"]/thead/tr/th[6]/img'
    column5_xpath = '//*[@id="searchResultsTable"]/thead/tr/th[7]/img'
    column6_xpath = '//*[@id="searchResultsTable"]/thead/tr/th[8]/img'

    # filter type
    column1_filter_type = "textfield"
    column2_filter_type = "textfield"
    column3_filter_type = "textfield"
    column4_filter_type = "textfield"
    column5_filter_type = "textfield"
    column6_filter_type = "textfield"

    # filter select all and options header index
    column3_header_index = 3
    column4_header_index = 4
    column5_header_index = 5
    column6_header_index = 6
    column7_header_index = 7
    column8_header_index = 8

    column4_id = "Patient ID"


    def search(self):
        self.setValueForHTMLElement("TEXTBOX", NBSConstants.TYPE_CODE_ID,
                                    "DEM102", "manual_merge")
        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                        NBSConstants.WAITING_TIME_IN_SECONDS)
        self.performButtonClick(NBSConstants.TYPE_CODE_NAME, "Submit")
        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                        NBSConstants.WAITING_TIME_IN_SECONDS)


    def validatePaginationOnResults(self):
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
        return True

    def validateSortingOnResultsGrid(self):
        # validate columns sort for results grid column name --default sort
        grid_columns = {
            self.column2_name: [self.column2_default_sort, self.column2_xpath],
            self.column3_name: [self.column3_default_sort, self.column3_xpath],
            self.column4_name: [self.column4_default_sort, self.column4_xpath],
            self.column5_name: [self.column5_default_sort, self.column5_xpath],
            self.column6_name: [self.column6_default_sort, self.column6_xpath],
        }
        # print("grid_columns!!!!!!!!!!!!!!!!!!", grid_columns)

        time.sleep(2)
        self.sortResultsGridColumnsOneByOne(grid_columns, "Desc")

        return True

    def sortResultsGridColumnsOneByOne(self, grid_columns, initial_sort_type):
        for key, value in grid_columns.items():
            if value[0] is not None and str(value[0]) != "No":
                if str(value[0]) == "Yes-D":
                    path = str(value[1]).__add__("[@src='SortDesc.gif']")

                    element = self.findElement(NBSConstants.TYPE_CODE_XPATH, path)
                    if element is not None:
                        self.logger.info(key + " default descending sort is passed")
                    # sort ascending
                    # print("sort ascending")
                    self.sortResultsGridColumnByAsc(key, str(value[1]))
                elif str(value[0]) == "Yes-A":
                    path = str(value[1]).__add__("[@src='SortAsc.gif']")
                    element = self.findElement(NBSConstants.TYPE_CODE_XPATH, path)
                    if element is not None:
                        self.logger.info(key + " default ascending sort is passed")
                    # sort ascending
                    self.sortResultsGridColumnByDesc(key, str(value[1]))
            else:
                if initial_sort_type == "Asc":
                    self.sortResultsGridColumnByAsc(key, str(value[1]))
                    self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                    NBSConstants.WAITING_TIME_IN_SECONDS)
                    self.sortResultsGridColumnByDesc(key, str(value[1]))
                else:
                    self.sortResultsGridColumnByDesc(key, str(value[1]))
                    self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                    NBSConstants.WAITING_TIME_IN_SECONDS)
                    self.sortResultsGridColumnByAsc(key, str(value[1]))

    def sortResultsGridColumnByAsc(self, column_name, path):
        time.sleep(1)
        # print("column_name", column_name)
        self.performButtonClick(NBSConstants.TYPE_CODE_LINK_TEXT, column_name)
        time.sleep(1)
        path = path.__add__("[@src='SortAsc.gif']")
        src_ele = self.findElement(NBSConstants.TYPE_CODE_XPATH, path)
        if src_ele is not None:  # ascending
            self.logger.info(column_name + " ascending sort is passed")
        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)

    def sortResultsGridColumnByDesc(self, column_name, path):
        self.performButtonClick(NBSConstants.TYPE_CODE_LINK_TEXT, column_name)
        time.sleep(2)
        path = path.__add__("[@src='SortDesc.gif']")
        sort_ele = self.findElement(NBSConstants.TYPE_CODE_XPATH, path)
        if sort_ele is not None:
            self.logger.info(column_name + " descending sort is passed")
        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)

    def switch_to_current_window(self,input):
        current_window = self.driver.window_handles[input]
        self.driver.switch_to.window(current_window)
        return True

    def validatePatientLInkInTheResultsPage(self):
        try:
            self.performButtonClick(NBSConstants.TYPE_CODE_XPATH, self.PATIENT_ID_LINK_XPATH)
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
            self.switch_to_current_window(1)
            self.driver.close()
            self.switch_to_current_window(0)
        except:
            return False
        return True

    def validateFilterOnResultsGrid(self):
        # validate filters with first row data for each column
        filter_columns = {
            self.column1_name: [self.column1_filter_type, self.column1_xpath, 4, 0,
                                3],
            self.column2_name: [self.column2_filter_type, self.column2_xpath, 1, 0,
                                4],
            self.column3_name: [self.column3_filter_type, self.column3_xpath, 5, 0,
                                5],
            self.column4_name: [self.column4_filter_type, self.column4_xpath, 6, 1,
                                6],
            self.column5_name: [self.column5_filter_type, self.column5_xpath, 2, 0,
                                7],
            self.column6_name: [self.column6_filter_type, self.column6_xpath, 3, 0,
                                8]}
        self.patMergefilterResultsGridDataByColumn(filter_columns)

        return True

    def patMergefilterResultsGridDataByColumn(self, filter_columns):
        for key, value in filter_columns.items():
            if str(value[0]) == "checkbox":
                self.performButtonClick(NBSConstants.TYPE_CODE_XPATH, str(value[1]))
                self.performColumnCheckBoxPopupSearch("OPENINV", str(key), value)
            elif str(value[0]) == "textfield":
                self.performButtonClick(NBSConstants.TYPE_CODE_XPATH, str(value[1]))
                self.patMergePerformColumnTextFieldPopupSearch("OPENINV", str(key), value)
            # time.sleep(1)
            # click on Remove All Filter/Sorts
            self.driver.find_element(By.XPATH,self.REMOVE_FILTER_XPATH).click()

    def patMergePerformColumnTextFieldPopupSearch(self, component_name, key, value_list):
        if component_name == "OPENINV":
            # print("value_list",value_list)
            if self.patMergeTextFieldFilterOnTheResultsGrid(key, value_list[2],value_list[4]):
                self.logger.info(str(key) + " Results Grid Filter Test Passed")

    def patMergeTextFieldFilterOnTheResultsGrid(self, key, column_name,column_index):
        search_text_element = self.text_filed_search_text.format(column_name)
        # print("search_text_element",search_text_element)
        search_text_ok_button = self.text_filed_search_ok_button.format(column_index)
        # print("search_text_element", search_text_ok_button)
        self.setValueForHTMLElement("TEXTBOX", NBSConstants.TYPE_CODE_XPATH,
                                    search_text_element, "xyz")
        self.performButtonClick(NBSConstants.TYPE_CODE_XPATH, search_text_ok_button)
        return True

    # def getSearchText(self, key):
    #     search_text = None
    #     if key == "Patient":
    #         search_text = (
    #             self.findElement(NBSConstants.TYPE_CODE_XPATH, '//*[@id="searchResultsTable"]/tbody/tr[1]/td[4]/a')
    #             .get_attribute('text'))
    #     # self.logger.info(search_text)

    #     if search_text is not None:
    #         return search_text
    #     else:
    #         return ""


    def validateRefineYourSearchLink(self):
        try:
            self.performActionMouseClick(NBSConstants.TYPE_CODE_XPATH, self.NEW_REFINE_SEARCH_XPATH)
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
            self.performButtonClick(NBSConstants.TYPE_CODE_NAME, "Submit")
        except:
            return False
        return True

    def validateNewSearchLink(self):
        try:
            self.performActionMouseClick(NBSConstants.TYPE_CODE_XPATH,self.SEARCH_LINK_XPATH.format(1))
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
            self.driver.find_element(By.NAME, "personSearch.lastName").send_keys("Manual_Merge")
            self.performButtonClick(NBSConstants.TYPE_CODE_NAME, "Submit")

        except:
            return False
        return True

    def validateRefineSearchTop(self):
        try:
            self.performActionMouseClick(NBSConstants.TYPE_CODE_XPATH, self.SEARCH_LINK_XPATH.format(2))
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
            self.performButtonClick(NBSConstants.TYPE_CODE_NAME, "Submit")
        except:
            return False
        return True
    def validatePrint(self):
        try:
            self.driver.find_element(By.XPATH, '//input[contains(@value,"Print")]').click()
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
        except:
            return False
        return True

    def validateDownload(self):
        try:
            self.performActionMouseClick(NBSConstants.TYPE_CODE_XPATH, '//input[contains(@value,"Download")]')
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
        except:
            return False
        return True








