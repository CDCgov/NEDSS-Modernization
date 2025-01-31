import random
import time
from builtins import print
from datetime import datetime

from selenium.common.exceptions import NoSuchElementException, TimeoutException
from selenium.webdriver import ActionChains
from selenium.webdriver.common.by import By
from selenium.webdriver.remote.webelement import WebElement
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.support.select import Select
from selenium.webdriver.support.ui import WebDriverWait

from src.utilities.constants import NBSConstants
from src.utilities.db_utilities import DBUtilities
from src.utilities.log_config import LogUtils


class BasePageObject:
    logger = LogUtils.loggen(__name__)
    selected_dropdown_value = None

    def __init__(self, driver):
        self.driver = driver

    """def findElement(self, type_code, path):
        element = None
        try:
            if type_code == NBSConstants.TYPE_CODE_ID:
                element = WebDriverWait(self.driver, NBSConstants.LOCATOR_ELEMENT_TIME_OUT).until(
                    EC.visibility_of_element_located((By.ID, path)))
            elif type_code == NBSConstants.TYPE_CODE_XPATH:
                element = WebDriverWait(self.driver, NBSConstants.LOCATOR_ELEMENT_TIME_OUT).until(
                    EC.visibility_of_element_located((By.XPATH, path)))
            elif type_code == NBSConstants.TYPE_CODE_LINK_TEXT:
                element = WebDriverWait(self.driver, NBSConstants.LOCATOR_ELEMENT_TIME_OUT).until(
                    EC.visibility_of_element_located((By.LINK_TEXT, path)))
            elif type_code == NBSConstants.TYPE_CODE_NAME:
                element = WebDriverWait(self.driver, NBSConstants.LOCATOR_ELEMENT_TIME_OUT).until(
                    EC.visibility_of_element_located((By.NAME, path)))
            elif type_code == NBSConstants.TYPE_CODE_CSS:
                element = WebDriverWait(self.driver, NBSConstants.LOCATOR_ELEMENT_TIME_OUT).until(
                    EC.visibility_of_element_located((By.CSS_SELECTOR, path)))
        except TimeoutException as te:
            raise te
        except NoSuchElementException as nse:
            raise nse
        return element"""

    def findElement(self, type_code, path):
        element = None
        try:
            if type_code == NBSConstants.TYPE_CODE_ID:
                element = self.driver.find_element(By.ID, path)
            elif type_code == NBSConstants.TYPE_CODE_XPATH:
                element = self.driver.find_element(By.XPATH, path)
            elif type_code == NBSConstants.TYPE_CODE_LINK_TEXT:
                element = self.driver.find_element(By.LINK_TEXT, path)
            elif type_code == NBSConstants.TYPE_CODE_NAME:
                element = self.driver.find_element(By.NAME, path)
            elif type_code == NBSConstants.TYPE_CODE_CSS:
                element = self.driver.find_element(By.CSS_SELECTOR, path)
            # elif type_code == NBSConstants.TYPE_CODE_CLASS_NAME:
            #     element = self.driver.find_element(By.CLASS_NAME, path)
        except NoSuchElementException as nse:
            raise nse
        return element

    def is_element_present(self, type_code, path):
        try:
            self.findElement(type_code, path)
            return True
        except (NoSuchElementException, TimeoutException):
            return False

    def findElements(self, type_code, path):
        element = None
        try:
            if type_code == NBSConstants.TYPE_CODE_ID:
                element = self.driver.find_elements(By.ID, path)
            elif type_code == NBSConstants.TYPE_CODE_XPATH:
                element = self.driver.find_elements(By.XPATH, path)
            elif type_code == NBSConstants.TYPE_CODE_LINK_TEXT:
                element = self.driver.find_elements(By.LINK_TEXT, path)
            elif type_code == NBSConstants.TYPE_CODE_NAME:
                element = self.driver.find_elements(By.NAME, path)
            elif type_code == NBSConstants.TYPE_CODE_CSS:
                element = self.driver.find_elements(By.CSS_SELECTOR, path)
        except NoSuchElementException as nse:
            raise nse
        return element

    def addRecordToSection(self, section_type):
        element = None
        if section_type == "Identification":
            element = self.findElement("ID", "BatchEntryAddButtonIdentification")
        elif section_type == "Address":
            element = self.findElement("ID", "BatchEntryAddButtonAddress")
        elif section_type == "Telephone":
            element = self.findElement("ID", "BatchEntryAddButtonTelephone")
        if element is not None:
            element.click()
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)

    def setProviderIdentificationRecordPopulate(self, random_number):
        raise Exception("Not Implemented")

    def setProviderAddressRecordPopulate(self, random_number):
        raise Exception("Not Implemented")

    def setProviderTelephonePopulate(self):
        raise Exception("Not Implemented")

    def populateSectionRecord(self, section_type, random_number):
        if section_type == "Identification":
            self.setProviderIdentificationRecordPopulate(str(random_number))
        elif section_type == "Address":
            self.setProviderAddressRecordPopulate(str(random_number))
        elif section_type == "Telephone":
            self.setProviderTelephonePopulate()
        elif section_type == "SUSCEPTIBILITY":
            self.setSusceptibilityRecordPopulate()

    def setRepeatableSections(self, section_type, random_number):
        for i in range(1, NBSConstants.ADD_NUMBER_OF_REPEATABLE_SECTIONS + 1):
            self.populateSectionRecord(section_type, str(random_number))
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
            self.addRecordToSection(section_type)
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)

    def performButtonClick(self, type_code, path):
        element = self.findElement(type_code, path)
        if element is not None:
            element.click()

    def performLinkClick(self, type_code, path):
        element = self.findElement(type_code, path)
        if element is not None:
            element.click()


    def performActionMouseClick(self, type_code, path):
        element = self.findElement(type_code,path)
        if element is not None:
            ActionChains(self.driver) \
                .double_click(element)\
                .perform()

    def getTextValueFromPage(self, type_code, path):
        element = self.findElement(type_code, path)
        if element is not None:
            self.logger.info("***" + element.text)
            return element.text
        else:
            return None

    def setValueForHTMLElement(self, element_type, type_code, path, value):
        if element_type == "TEXTBOX":
            element = self.findElement(type_code, path)
            if element is not None:
                element.clear()
                element.send_keys(value)
        elif element_type == "DROPDOWN":
            element = self.findElement(type_code, path)
            self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
            select_element = Select(element)
            select_element.select_by_value(value)
        elif element_type == "DROPDOWN-RANDOM":
            element = self.findElement(type_code, path)
            self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
            select_element = Select(element)
            if select_element.is_multiple:
                for index in select_element.options:
                    if index.text != "":
                        select_element.select_by_visible_text(index.text)
            else:
                select_element.select_by_index(random.randint(1, len(select_element.options) - 1))
                self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)

    def findElementInMultiplePages(self, uid):
        element_find = None
        validate_element = True
        while validate_element:
            try:
                element_find = self.driver.find_element(By.XPATH,
                                                        "//a[contains(@href, 'uid=".__add__(str(uid)) + "')]")
                if element_find is not None:
                    break
            except NoSuchElementException:
                try:
                    self.driver.find_element(By.LINK_TEXT, 'Next').click()
                except NoSuchElementException:
                    element_find = None
                    validate_element = False  # if element does not fine in all pages, exit loop
        return element_find

    def validatePaginationOnResults(self):
        self.setValueForHTMLElement("TEXTBOX", NBSConstants.TYPE_CODE_ID,
                                    "providerSearch.lastName", "%")
        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                        NBSConstants.WAITING_TIME_IN_SECONDS)
        self.performButtonClick(NBSConstants.TYPE_CODE_ID, "Submit")
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
        self.performButtonClick("LINK_TEXT", 'New Search')
        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                        NBSConstants.WAITING_TIME_IN_SECONDS)
        return True

    def setDropdownValueRandom(self, type_code, element_path):
        try:
            element = self.findElement(type_code, element_path)
            element_id = element.get_attribute("id")
            self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
            select_element = Select(element)
            if len(select_element.options) > 2:
                random_index = random.randint(2, len(select_element.options))
            else:
                random_index = 2
            selected_drop_down_xpath = (element_path.__add__("/option[")
                                        .__add__(str(random_index)) + "]")
            self.driver.find_element(By.XPATH, selected_drop_down_xpath).click()
            selected_dropdown_value = (self.driver.find_element(By.XPATH, selected_drop_down_xpath)
                                       .get_attribute("innerHTML"))
            self.setValueForHTMLElement("TEXTBOX", NBSConstants.TYPE_CODE_NAME,
                                        element_id.__add__("_textbox"), selected_dropdown_value)
        except Exception as ex:
            self.logger.info("An exception occurred with dropdown:", type(ex).__name__, "–", ex)

    def setDropdownValue(self, type_code, element_path, value):
        try:
            element = self.findElement(type_code, element_path)
            element_id = element.get_attribute("id")
            self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
            selected_drop_down_xpath = (element_path.__add__("/option[@value='")
                                        .__add__(str(value)) + "']")
            self.driver.find_element(By.XPATH, selected_drop_down_xpath).click()
            self.setValueForHTMLElement("TEXTBOX", NBSConstants.TYPE_CODE_NAME,
                                        element_id.__add__("_textbox"), value)
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
        except Exception as ex:
            self.logger.info("An exception occurred:", type(ex).__name__, "–", ex)

    def selectCalendarDate(self, type_code, path, selected_day=None, selected_month=None, selected_year=None):
       
        try:
            element = self.findElement(type_code, path)
            if element is not None:
                print("Calendar click")
                element.click()
                # self.driver.execute_script("arguments[0].click();", element)
                self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
                original_window = self.driver.current_window_handle
                WebDriverWait(self.driver, 10).until(EC.number_of_windows_to_be(2))
                for window_handle in self.driver.window_handles:
                    if window_handle != original_window:
                        self.driver.switch_to.window(window_handle)
                        # Today born Baby's
                        if selected_day is None and selected_month is None and selected_year is None:
                            element = self.driver.find_element(By.CLASS_NAME, NBSConstants.CALENDAR_TODAY_DATE)
                            element.click()
                        else:
                            # specific year
                            self.findElementInCalendar(NBSConstants.TYPE_CODE_XPATH,
                                                       NBSConstants.CALENDAR_DISPLAY_YEAR,
                                                       NBSConstants.CALENDAR_PRE_YEAR_ARROW,
                                                       selected_year)
                            # specific Month
                            self.findElementInCalendar(NBSConstants.TYPE_CODE_XPATH,
                                                       NBSConstants.CALENDAR_DISPLAY_MONTH,
                                                       NBSConstants.CALENDAR_PRE_MONTH_ARROW,
                                                       selected_month)
                            # specific date
                            all_dates = self.findElements(NBSConstants.TYPE_CODE_XPATH,
                                                          NBSConstants.CALENDAR_GET_ALL_DAYS)
                            for selected_date_val in all_dates:
                                if selected_date_val.text.strip() == selected_day.strip():
                                    selected_date_val.click()
                                    selected_year = None
                                    selected_month = None
                                    selected_day = None
                                    break

                self.driver.switch_to.window(original_window)
                self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
        except Exception as exc:
            self.logger.exception(exc, exc_info=(type(exc), exc, exc.__traceback__))

    def selectCalendarDateForNPopups(self, type_code, path, selected_day=None, selected_month=None, selected_year=None):
        element = self.findElement(type_code, path)
        if element is not None:
            element.click()
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
            original_window = self.driver.current_window_handle
            try:
                WebDriverWait(self.driver, 10).until(EC.number_of_windows_to_be(len(self.driver.window_handles)))
                self.driver.switch_to.window(self.driver.window_handles[len(self.driver.window_handles) - 1])
                if selected_day is None and selected_month is None and selected_year is None:
                    element = self.driver.find_element(By.XPATH, "//a[@class='cpTodayText']")
                    element.click()
                else:
                    # specific year
                    self.findElementInCalendar(NBSConstants.TYPE_CODE_XPATH,
                                               NBSConstants.CALENDAR_DISPLAY_YEAR,
                                               NBSConstants.CALENDAR_PRE_YEAR_ARROW,
                                               selected_year)
                    # specific Month
                    self.findElementInCalendar(NBSConstants.TYPE_CODE_XPATH,
                                               NBSConstants.CALENDAR_DISPLAY_MONTH,
                                               NBSConstants.CALENDAR_PRE_MONTH_ARROW,
                                               selected_month)
                    # specific date
                    all_dates = self.findElements(NBSConstants.TYPE_CODE_XPATH,
                                                  NBSConstants.CALENDAR_GET_ALL_DAYS)
                    for selected_date_val in all_dates:
                        if selected_date_val.text.strip() == selected_day.strip():
                            selected_date_val.click()
                self.driver.switch_to.window(original_window)

            except Exception as exc:
                self.logger.exception(exc, exc_info=(type(exc), exc, exc.__traceback__))

    def findElementInCalendar(self, type_code, cal_year_path, cal_prev_path, value):
        try:
            while True:
                element_current = self.findElement(type_code, cal_year_path)
                if element_current is not None:
                    if element_current.text.strip() == value.strip():
                        break
                    else:
                        self.findElement(type_code, cal_prev_path).click()
        except Exception as exc:
            self.logger.exception(exc, exc_info=(type(exc), exc, exc.__traceback__))

    # find search text element in the search results page which spans more than one page.
    def findElementInMultiplePagesByText(self, search_text):
        element_find = None
        validate_element = True
        while validate_element:
            try:
                element_find = self.findElement(NBSConstants.TYPE_CODE_XPATH,
                                                "//a[contains(@href,'".__add__(str(search_text)) + "')]")
                if element_find is not None:
                    break
            except NoSuchElementException:
                try:
                    self.findElement(NBSConstants.TYPE_CODE_LINK_TEXT, 'Next').click()
                except NoSuchElementException:
                    element_find = None
                    validate_element = False  # if element does not fine in all pages, exit loop
        return element_find

    @staticmethod
    def setWaitTimeAfterEachAction(debug_on, wait_time):
        if debug_on == "YES":
            time.sleep(wait_time)
        else:
            time.sleep(0)

    def setSusceptibilityRecordPopulate(self):
        raise Exception("Not Implemented")

    @staticmethod
    def getPatienetIdFromSystem(patient_uid):
        # PSN2157685649GA01
        patient_id = None
        local_id = DBUtilities().getSingleColumnValueByTable("local_id", "Person",
                                                             "person_uid", patient_uid)
        suffix_code = DBUtilities().getSingleColumnValueByTable("config_value", "NBS_configuration",
                                                                "config_key", "UID_SUFFIX_CODE")
        seed_code = DBUtilities().getSingleColumnValueByTable("config_value", "NBS_configuration",
                                                              "config_key", "SEED_VALUE")
        if local_id is not None:
            patient_id = int(local_id[3:(len(local_id) - len(suffix_code))]) - int(seed_code)
        return patient_id

    def getSelectedOptionForSingleSelect(self, type_code, path):
        element = self.findElement(type_code, path)
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        select_element = Select(element)
        return select_element.first_selected_option.get_attribute("value")

    def getSelectedVisibleTextForSingleSelect(self, type_code, path):
        element = self.findElement(type_code, path)
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        select_element = Select(element)
        return select_element.first_selected_option.get_attribute("innerHTML")

    def findElement_web(self, type_code, path):
        element = None
        try:
            if type_code == NBSConstants.TYPE_CODE_ID:
                element = WebDriverWait(self.driver, 17).until(EC.visibility_of_element_located((By.ID, path)))
            elif type_code == NBSConstants.TYPE_CODE_XPATH:
                element = WebDriverWait(self.driver, 17).until(EC.visibility_of_element_located((By.XPATH, path)))
            elif type_code == NBSConstants.TYPE_CODE_LINK_TEXT:
                element = WebDriverWait(self.driver, 17).until(
                    EC.visibility_of_element_located((By.LINK_TEXT, path)))
            elif type_code == NBSConstants.TYPE_CODE_NAME:
                element = WebDriverWait(self.driver, 17).until(EC.visibility_of_element_located((By.NAME, path)))
            elif type_code == NBSConstants.TYPE_CODE_CSS:
                element = WebDriverWait(self.driver, 17).until(
                    EC.visibility_of_element_located((By.CSS_SELECTOR, path)))
        except NoSuchElementException as nse:
            raise nse
        return element

    def setValueForHTMLElement_web(self, element_type, type_code, path, value):
        if element_type == "TEXTBOX":
            element = self.findElement_web(type_code, path)
            if element is not None:
                element.clear()
                element.send_keys(value)
        elif element_type == "DROPDOWN":
            element = self.findElement_web(type_code, path)
            self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
            select_element = Select(element)
            select_element.select_by_value(value)
        elif element_type == "DROPDOWN-RANDOM":
            element = self.findElement_web(type_code, path)
            self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
            select_element = Select(element)
            select_element.select_by_index(random.randint(1, len(select_element.options) - 1))
        else:
            print("Nothing")

    def performButtonClick_web(self, type_code, path):
        element = self.findElement_web(type_code, path)
        if element is not None:
            element.click()

    def selectDropDownOptionValueRandom(self, path, selected_value=None):
        select_ipath = str(path.__add__("_button"))
        WebDriverWait(self.driver, 15).until(
            EC.element_to_be_clickable((By.NAME, select_ipath))).click()
        element = WebDriverWait(self.driver, 15).until(
            EC.visibility_of_element_located((By.ID, path)))
        select_element = Select(element)
        if selected_value is not None:
            select_element.select_by_value(selected_value)
        else:
            if len(select_element.options) > 1:
                select_element.select_by_index(random.randint(1, len(select_element.options) - 1))

    def checkDateIsExisted(self, type_code, path):
        element_date = self.findElement_web(type_code, path)
        date_value = element_date.get_attribute("value")
        if date_value is not None:
            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                        type_code, path, datetime.today().strftime('%m/%d/%Y'))
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)

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
                    print("sort ascending")
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

    def sortResultsGridColumnsOneByOneUsingActions(self, grid_columns, initial_sort_type):
        for key, value in grid_columns.items():
            if value[0] is not None and str(value[0]) != "No":
                if str(value[0]) == "Yes-D":
                    path = str(value[1]).__add__("[@src='SortDesc.gif']")
                    element = self.findElement(NBSConstants.TYPE_CODE_XPATH, path)
                    if element is not None:
                        self.logger.info(key + " default descending sort is passed")
                    # sort ascending
                    print("sort ascending")
                    self.sortResultsGridColumnByAscUsingAction(key, str(value[1]))
                elif str(value[0]) == "Yes-A":
                    path = str(value[1]).__add__("[@src='SortAsc.gif']")
                    element = self.findElement(NBSConstants.TYPE_CODE_XPATH, path)
                    if element is not None:
                        self.logger.info(key + " default ascending sort is passed")
                    # sort ascending
                    self.sortResultsGridColumnByDescUsingAction(key, str(value[1]))
            else:
                if initial_sort_type == "Asc":
                    self.sortResultsGridColumnByAscUsingAction(key, str(value[1]))
                    self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                    NBSConstants.WAITING_TIME_IN_SECONDS)

                    self.sortResultsGridColumnByDescUsingAction(key, str(value[1]))
                else:
                    self.sortResultsGridColumnByDescUsingAction(key, str(value[1]))
                    self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                    NBSConstants.WAITING_TIME_IN_SECONDS)

                    self.sortResultsGridColumnByAscUsingAction(key,str(value[1]))

    def sortResultsGridColumnByDesc(self, column_name, path):
        self.performButtonClick(NBSConstants.TYPE_CODE_LINK_TEXT, column_name)
        time.sleep(2)
        path = path.__add__("[@src='SortDesc.gif']")
        sort_ele = self.findElement(NBSConstants.TYPE_CODE_XPATH, path)
        if sort_ele is not None:
            self.logger.info(column_name + " descending sort is passed")
        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)



    def sortResultsGridColumnByAsc(self, column_name, path):
        time.sleep(1)
        self.performButtonClick(NBSConstants.TYPE_CODE_LINK_TEXT, column_name)
        time.sleep(1)
        path = path.__add__("[@src='SortAsc.gif']")
        src_ele = self.findElement(NBSConstants.TYPE_CODE_XPATH, path)
        if src_ele is not None:  # ascending
            self.logger.info(column_name + " ascending sort is passed")
        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)

    def sortResultsGridColumnByAscUsingAction(self, column_name, path):
        xpath = self.replace_last_occurrence(path, "/img", "/a")
        self.performButtonClick(NBSConstants.TYPE_CODE_XPATH, xpath)
        path = path.__add__("[@src='SortAsc.gif']")
        src_ele = self.findElement(NBSConstants.TYPE_CODE_XPATH, path)
        if src_ele is not None:  # ascending
            self.logger.info(column_name + " ascending sort is passed")
        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)


    def sortResultsGridColumnByDescUsingAction(self, column_name, path):
        xpath = self.replace_last_occurrence(path, "/img", "/a")
        print("XPATH@@@@@@@@@@@", xpath)
        self.performActionMouseClick(NBSConstants.TYPE_CODE_XPATH, xpath)
        path = path.__add__("[@src='SortDesc.gif']")
        print("path",path)
        sort_ele = self.findElement(NBSConstants.TYPE_CODE_XPATH, path)
        if sort_ele is not None:
            self.logger.info(column_name + " descending sort is passed")
        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)

    def validatePrintAndExportInTheResultsPage(self):
        try:
            # Print Button
            pdf_element = WebDriverWait(self.driver, 10).until(EC.element_to_be_clickable((By.XPATH,
                                                                                           "//*[@id='printExport']/img[2]")))
            if pdf_element is not None:
                pdf_element.click()
            # self.performButtonClick_web(NBSConstants.TYPE_CODE_XPATH, "//*[@id='printExport']/img[1]")
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
            # pyautogui.press('enter')
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
            # export CSV Button
            csv_element = WebDriverWait(self.driver, 10).until(EC.element_to_be_clickable((By.XPATH,"//*[@id='printExport']/img[3]")))
            if csv_element is not None:
                csv_element.click()
            # self.performButtonClick_web(NBSConstants.TYPE_CODE_XPATH, "//*[@id='printExport']/img[2]")
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
            # pyautogui.press('enter')
        except:
            return False
        return True

    def filterResultsGridDataByColumn(self, filter_columns):
        for key, value in filter_columns.items():
            if str(value[0]) == "checkbox":
                self.performButtonClick(NBSConstants.TYPE_CODE_XPATH, str(value[1]))
                self.performColumnCheckBoxPopupSearch("OPENINV", str(key), value)
            elif str(value[0]) == "textfield":
                self.performButtonClick(NBSConstants.TYPE_CODE_XPATH, str(value[1]))
                self.performColumnTextFieldPopupSearch("OPENINV", str(key), value)
            time.sleep(1)
            # click on Remove All Filter/Sorts
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
            WebDriverWait(self.driver, 17).until(
                EC.element_to_be_clickable((By.LINK_TEXT, "| Remove All Filters/Sorts"))).click()
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
            # self.performButtonClick(NBSConstants.TYPE_CODE_CLASS_NAME, "removefilerLink")

    def performColumnCheckBoxPopupSearch(self, component_name, key, value_list):
        if component_name == "OPENINV":
            if self.performCheckboxFilterOnTheResultsGrid(str(value_list[2])):
                self.logger.info(str(key) + " Results Grid Filter Test Passed")

    def performColumnTextFieldPopupSearch(self, component_name, key, value_list):
        if component_name == "OPENINV":
            if self.performTextFieldFilterOnTheResultsGrid(str(value_list[3]), key):
                self.logger.info(str(key) + " Results Grid Filter Test Passed")

    def performCheckboxFilterOnTheResultsGrid(self, param):
        raise Exception("Not Implemented in Base class")

    def performTextFieldFilterOnTheResultsGrid(self, param, param1):
        raise Exception("Not Implemented in Base class")

    def set_dropdown_value(self, locate):
        element = self.driver.find_element(By.ID, locate)
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        if element.tag_name.lower() == 'select':
            select_element = Select(self.driver.find_element(By.ID, locate))
            random_index = random.randint(2, len(select_element.options))
            select_dropdown_xpath = (
                    f"//*[@id='{locate}']/option[".__add__(
                        str(random_index)) + "]")
            dropdown_value = (self.driver.find_element(By.XPATH, select_dropdown_xpath).get_attribute("value"))
            time.sleep(1)
            select_element.select_by_value(dropdown_value)

    def set_and_return_dropdown_value(self, locate):
        element = self.driver.find_element(By.ID, locate)
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        if element.tag_name.lower() == 'select':
            select_element = Select(self.driver.find_element(By.ID, locate))
            random_index = random.randint(2, len(select_element.options))
            select_dropdown_xpath = (
                    f"//*[@id='{locate}']/option[".__add__(
                        str(random_index)) + "]")
            dropdown_value = (self.driver.find_element(By.XPATH, select_dropdown_xpath).get_attribute("value"))
            select_element.select_by_value(dropdown_value)
            time.sleep(1)
            # select_opt = select_element.options
            # for i in select_opt:
            #     if i.get_attribute("value") == dropdown_value:
            #         gettext = i.text
            #         print(gettext)


            print("dropdown",dropdown_value)
            # print("select_opt",select_opt.text)
            return dropdown_value

    def set_dropdown_by_visibletext(self,id,selecttext):
        select = Select(self.driver.find_element(By.ID,id))
        select.select_by_visible_text(selecttext)


    def get_todaysdate(self):
        today = datetime.now().date()
        today = today.strftime("%d/%m/%Y")
        return today

    def get_todaysdate_m_d_y(self):
        today = datetime.now().date()
        today = today.strftime("%m/%d/%Y")
        return today

    def replace_last_occurrence(self,text, old_char, new_char):
        # Split the string into parts, starting from the right, with a maximum of one split
        parts = text.rsplit(old_char, 1)

        # Join the parts back together with the new character
        return new_char.join(parts)

    # # Example usage
    # text = "example/text/here/img"
    # new_text = replace_last_occurrence(text, '/img', '/a')
    # print(new_text)








