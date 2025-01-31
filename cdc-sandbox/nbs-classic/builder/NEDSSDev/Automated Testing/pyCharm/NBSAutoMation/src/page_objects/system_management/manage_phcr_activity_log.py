import time
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


class manage_phcr_activity_log(BasePageObject):
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
    column1_name = "Processed Time"
    column2_name = "Event ID"
    column3_name = "Action"
    column4_name = "Algorithm Name"
    column5_name = "Status"
    column6_name = "Exception Text"
    

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
    column1_xpath = "//*[@id='parent']/thead/tr/th[1]/img"
    column2_xpath = "//*[@id='parent']/thead/tr/th[2]/img"
    column3_xpath = "//*[@id='parent']/thead/tr/th[3]/img"
    column4_xpath = "//*[@id='parent']/thead/tr/th[4]/img"
    column5_xpath = "//*[@id='parent']/thead/tr/th[5]/img"
    column6_xpath = "//*[@id='parent']/thead/tr/th[6]/img"
   
    # filter type
    column1_filter_type = "checkbox"
    column2_filter_type = "checkbox"
    column3_filter_type = "checkbox"
    column4_filter_type = "textfield"
    column5_filter_type = "checkbox"
    column6_filter_type = "checkbox"
  
    # filter select all and options header index
    column1_header_index = 2
    column2_header_index = 3
    column3_header_index = 4
    column4_header_index = 5  # changed for replace index for path
    column5_header_index = 6
    column6_header_index = 7
    
    def mergeSortResultsGridColumnByDesc(self, column_name, path):
        self.performButtonClick(NBSConstants.TYPE_CODE_LINK_TEXT, column_name)
        time.sleep(2)
        path = path.__add__("[@src='GraySortDesc.gif']")
        sort_ele = self.findElement(NBSConstants.TYPE_CODE_XPATH, path)
        if sort_ele is not None:
            self.logger.info(column_name + " descending sort is passed")
        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)


    def validateSortingOnResultsGrid(self):
        # validate columns sort for results grid column name --default sort
        grid_columns = {
            # self.column1_name: [self.column1_default_sort, self.column1_xpath],
            self.column2_name: [self.column2_default_sort, self.column2_xpath],
            self.column3_name: [self.column3_default_sort, self.column3_xpath],
            self.column4_name: [self.column4_default_sort, self.column4_xpath],
            self.column5_name: [self.column5_default_sort, self.column5_xpath],
            # self.column6_name: [self.column6_default_sort, self.column6_xpath],
           }

        self.sortResultsGridColumnsOneByOne(grid_columns, "Desc")

        return True
    
    
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


    def date_time(self,t):
        c = t.split(" ")
        date_c = c[0]
        time_c = c[1]
        return date_c,time_c