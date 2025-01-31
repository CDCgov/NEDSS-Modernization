import string
import time
import datetime
import random

from selenium.common import NoSuchElementException
from selenium.webdriver.common.by import By
from selenium.webdriver.support.select import Select

from src.utilities.constants import NBSConstants
from src.utilities.log_config import LogUtils
from src.utilities.nbs_utilities import NBSUtilities


class Place:
    def __init__(self, driver):
        self.driver = driver

    place_search_submit_id = "Submit"
    logger = LogUtils.loggen(__name__)

    @staticmethod
    def generate_random_string(s_length):
        letters = string.ascii_lowercase
        # Randomly choose characters from letters for the given length of the string
        random_string = ''.join(random.choice(letters) for i in range(s_length))
        return random_string

    add_place_header_title = "//*[contains(text(), 'Add Place')]"

    def getAddPlaceHeader(self):
        return self.driver.find_element(By.XPATH, self.add_place_header_title).text

    def setPlaceLastName(self, lastname):
        self.driver.find_element(By.NAME, "placeSearch.nm").clear()
        self.driver.find_element(By.NAME, "placeSearch.nm").send_keys(lastname)

    def editPlaceLastName(self, lastname):
        self.driver.find_element(By.NAME, "place.thePlaceDT.nm").clear()
        self.driver.find_element(By.NAME, "place.thePlaceDT.nm").send_keys(lastname)

    def setPlaceSubmit(self):
        self.driver.find_element(By.NAME, "Submit").click()

    def setPlaceAdd(self):
        self.driver.find_element(By.NAME, "Add").click()

    def setPlaceAdminSection(self, random_number):
        self.setPlaceQuickCode(random_number[:10])
        self.setPlaceType()
        self.setPlaceComments()

    def editPlaceAdminSection(self, random_number, option):
        if option == NBSConstants.EDIT_PLACE_RADIO_SECOND_OPTION:
            self.setPlaceQuickCode(random_number[:10])
        self.setPlaceType()
        self.setPlaceComments()

    """def editPlaceAdminSection(self, random_number):
        # self.setPlaceQuickCode(random_number[:10])
        self.setPlaceType()
        self.setPlaceComments()"""

    def setPlaceQuickCode(self, random_number):
        # self.driver.find_element(By.NAME, "quick.rootExtensionTxt").clear()
        self.driver.find_element(By.NAME, "quick.rootExtensionTxt").send_keys(str(random_number))

    # CheckTab('placeType', this);

    def setPlaceType(self):
        element = self.driver.find_element(By.ID, "placeType")
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        select_element = Select(
            self.driver.find_element(By.ID, "placeType"))
        size = len(select_element.options) - 1
        select_element.select_by_index(random.randint(1, size))

    def setPlaceComments(self):
        self.driver.find_element(By.NAME, "place.thePlaceDT.description").clear()
        self.driver.find_element(By.NAME, "place.thePlaceDT.description").send_keys \
            ("General Comments", '_', (Place.generate_random_string(10)))

    def setPlaceAddressSection(self, no_of_repeats, random_number):
        for i in range(1, no_of_repeats + 1):
            self.setAddressInfoDate()
            self.setType()
            self.setAddressUse()
            self.setStreetAddress1(random_number.__add__(str(i)))
            self.setStreetAddress2(random_number.__add__(str(i)))
            self.setCity()
            self.setState()
            self.setZipcode()
            self.setCounty()
            self.setCensusTract(random_number)
            self.setCountry()
            self.setAddressComments()
            self.addAddressRowToPlace()
            time.sleep(5)
        self.setPlaceAddressActions(no_of_repeats, random_number)
        time.sleep(2)

    def selectCalendarDate(self, cal_type, cal_type_val, selected_day, selected_month, selected_year):
        try:
            element = self.driver.find_element(cal_type, cal_type_val)
            if element is not None:
                element.click()
                time.sleep(1)
                original_window = self.driver.current_window_handle
                time.sleep(2)
                for window_handle in self.driver.window_handles:
                    if window_handle != original_window:
                        self.driver.switch_to.window(window_handle)
                        # Today born Baby's
                        if selected_day is None and selected_month is None and selected_year is None:
                            element = self.driver.find_element(By.CLASS_NAME, NBSConstants.CALENDAR_TODAY_DATE)
                            element.click()
                        else:
                            # specific year
                            self.findElementInCalendar(By.XPATH,
                                                       NBSConstants.CALENDAR_DISPLAY_YEAR,
                                                       NBSConstants.CALENDAR_PRE_YEAR_ARROW,
                                                       selected_year)
                            # specific Month
                            self.findElementInCalendar(By.XPATH,
                                                       NBSConstants.CALENDAR_DISPLAY_MONTH,
                                                       NBSConstants.CALENDAR_PRE_MONTH_ARROW,
                                                       selected_month)
                            # specific date
                            all_dates = self.driver.find_elements(By.XPATH, NBSConstants.CALENDAR_GET_ALL_DAYS)
                            for selected_date_val in all_dates:
                                if selected_date_val.text.strip() == selected_day.strip():
                                    selected_date_val.click()
                                    break
                self.driver.switch_to.window(original_window)
                time.sleep(2)
        except Exception as ex:
            self.logger.info("Exception While Working with Calendar Date Selection")

    def findElementInCalendar(self, type_code, cal_year_path, cal_prev_path, value):
        try:
            while True:
                element_current = self.driver.find_element(type_code, cal_year_path)
                if element_current is not None:
                    if element_current.text.strip() == value.strip():
                        break
                    else:
                        self.driver.find_element(type_code, cal_prev_path).click()
        except Exception as ex:
            self.logger.info("Exception occurred while setting value for calendar")

    def setTodayDate(self, type, type_val):
        now = datetime.datetime.now()
        today = now.strftime("%m-%d-%Y")
        element = self.driver.find_element(type, type_val)
        element.clear()
        element.send_keys(today)

    def setAddressInfoDate(self):
        addr_birth_day, addr_birth_month, addr_birth_year = NBSUtilities.getRandomDateByVars()
        self.selectCalendarDate(By.ID, 'AddrAsOfIcon', str(int(addr_birth_day)), addr_birth_month, str(int(addr_birth_year)))

    def setType(self):
        element = self.driver.find_element(By.ID, "addrType")
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        select_element = Select(self.driver.find_element(By.ID, "addrType"))
        select_element.select_by_value('PLC')

    def setAddressUse(self):
        element = self.driver.find_element(By.ID, "addrUse")
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        select_element = Select(self.driver.find_element(By.ID, "addrUse"))
        select_element.select_by_value('WP')

    def setPlaceElementVisible(self, element):
        element = self.driver.find_element(By.ID, element)
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)

    def setStreetAddress1(self, random_value):
        self.driver.find_element(By.NAME, "streetAddr1").clear()
        self.driver.find_element(By.NAME, "streetAddr1"
                                 ).send_keys("address1-" + random_value)

    def setStreetAddress2(self, random_value):
        self.driver.find_element(By.NAME, "streetAddr2").clear()
        self.driver.find_element(By.NAME, "streetAddr2"
                                 ).send_keys("address2-" + random_value)

    def setCity(self):

        self.driver.find_element(By.NAME, "city").clear()
        self.driver.find_element(By.NAME, "city"). \
            send_keys("PostalAddress", "_" + Place.generate_random_string(5))

    def setState(self):
        element = self.driver.find_element(By.ID, "state")
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        select_element = Select(self.driver.find_element(By.ID,
                                                         "state"))
        random_index = random.randint(2, len(select_element.options))
        add_place_addr_state_xpath = (
                "//*[@id='state']/option["
                .__add__(str(random_index)) + "]")
        addr_state_value = (self.driver.find_element(By.XPATH, add_place_addr_state_xpath)
                            .get_attribute("value"))
        select_element.select_by_value(addr_state_value)

    # //*[@id="trAddr7"]/td[2]/input

    def setZipcode(self):
        fixed_digits = 5
        random_number = random.randrange(11111, 99999, fixed_digits)
        self.driver.find_element(By.ID, "zip").clear()
        self.driver.find_element(By.ID, "zip").send_keys(random_number)

    def setCounty(self):
        element = self.driver.find_element(By.ID, "county")
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        select_element = Select(self.driver.find_element(By.ID, "county"))
        
        random_index = random.randint(2, len(select_element.options))
        try:
            add_place_addr_county_xpath = ("//*[@id='county']/option[".__add__(str(random_index)) + "]")
            addr_state_value = (self.driver.find_element(By.XPATH, add_place_addr_county_xpath).get_attribute("value"))
            select_element.select_by_value(addr_state_value)
            time.sleep(2)
        except Exception as e:
            self.logger.info("County Not found : no county is present for the state")

    # def setCensusTract(self):
    def setCensusTract(self, random_number):
        fixed_digits = 4
        random_number = random.randrange(1111, 9999, fixed_digits)
        self.driver.find_element(By.NAME, "censusTract").clear()
        self.driver.find_element(By.NAME, "censusTract").send_keys(random_number)

    def setCountry(self):
        element = self.driver.find_element(By.ID, "country")
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        select_element = Select(self.driver.find_element(By.ID, "country"))
        random_index = random.randint(2, len(select_element.options))
        add_place_addr_county_xpath = ("//*[@id='country']/option[".__add__(str(random_index)) + "]")
        addr_state_value = (self.driver.find_element(By.XPATH, add_place_addr_county_xpath).get_attribute("value"))
        select_element.select_by_value(addr_state_value)

    def setAddressComments(self):
        self.driver.find_element(By.NAME, "locatorDescTxt").clear()
        self.driver.find_element(By.NAME, "locatorDescTxt").send_keys("AddressComments", '_', (
            Place.generate_random_string(10)))

    def addAddressRowToPlace(self):
        self.driver.find_element(By.ID, "btnAddAddress").click()

    def setPlaceAddressActions(self, no_of_repeats, random_number):
        for i in range(0, no_of_repeats):
            if i == no_of_repeats - 3:
                place_view_row_xpath = "//*[@id='a".__add__(str(i)) + "']/td[1]/img"
                self.driver.find_element(By.XPATH, place_view_row_xpath).click()
                time.sleep(3)
            if i == no_of_repeats - 2:
                place_edit_row_xpath = "//*[@id='a".__add__(str(i)) + "']/td[2]/img"
                self.driver.find_element(By.XPATH, place_edit_row_xpath).click()
                time.sleep(2)
                self.setTodayDate(By.ID, "AddrAsOf")
                self.setType()
                self.setAddressUse()
                self.setStreetAddress1(random_number.__add__(str(i)))
                self.setStreetAddress2(random_number.__add__(str(i)))
                self.setCity()
                self.setState()
                self.setZipcode()
                self.setCounty()
                self.setCensusTract(random_number)
                self.setCountry()
                self.setAddressComments()
                self.driver.find_element(By.ID, "btnUpdateAddress").click()
                time.sleep(3)
            if i == no_of_repeats - 1:
                place_delete_row_xpath = "//*[@id='a".__add__(str(i)) + "']/td[3]/img"
                self.driver.find_element(By.XPATH, place_delete_row_xpath).click()
                time.sleep(3)

    def editPlaceAddressActions(self, edit_option, random_number):
        place_edit_row_xpath = "//*[@id='a".__add__(str(edit_option)) + "']/td[2]/img"
        self.driver.find_element(By.XPATH, place_edit_row_xpath).click()
        time.sleep(2)
        # self.setAddressInfoDate()
        self.setTodayDate(By.ID, "AddrAsOf")
        self.setType()
        self.setAddressUse()
        self.setStreetAddress1(random_number.__add__(str(edit_option)))
        self.setStreetAddress2(random_number.__add__(str(edit_option)))
        self.setCity()
        self.setState()
        self.setZipcode()
        self.setCounty()
        self.setCensusTract(random_number)
        self.setCountry()
        self.setAddressComments()
        self.driver.find_element(By.ID, "btnUpdateAddress").click()
        time.sleep(3)

    def setPlaceTelephoneSection(self, no_of_repeats):
        for i in range(1, no_of_repeats + 1):
            # Populate Telephone Record
            self.setTelephoneInfo()
            # Add Telephone Row to Table
            self.performActionClick("btnAddTelephone")
            time.sleep(1)
        self.setPlaceTelephoneActions(no_of_repeats)
        time.sleep(2)

    def performActionClick(self, element):
        self.driver.find_element(By.ID, element).click()

    def setPlaceTelephoneActions(self, no_of_repeats):
        for i in range(0, no_of_repeats):
            if i == no_of_repeats - 3:
                tel_view_row_xpath = "//*[@id='p".__add__(str(i)) + "']/td[1]/img"
                self.driver.find_element(By.XPATH, tel_view_row_xpath).click()
                time.sleep(3)
            if i == no_of_repeats - 2:
                tel_edit_row_xpath = "//*[@id='p".__add__(str(i)) + "']/td[2]/img"
                self.driver.find_element(By.XPATH, tel_edit_row_xpath).click()
                # self.setTelephoneInfo()
                # self.setContactInfoDate()
                self.setTodayDate(By.ID, "PhoneAsOf")
                self.setTypeId()
                self.setAddUse()
                # country code
                self.setPlaceTextBoxValue("cntryCd", "001")
                # telephone
                self.setPlaceTelephoneValue("phoneNbrTxt", Place.generate_random_int(10))
                # telephone number extension
                self.setPlaceTextBoxValue("phoneExt",
                                          Place.generate_random_int(4))
                # email
                self.setPlaceTextBoxValue("email",
                                          Place.generate_random_string(8).__add__("@omg.com"))
                # url
                self.setPlaceTextBoxValue("urlAddress",
                                          "www.".__add__(Place.generate_random_string(8)).__add__(".com"))
                # Comments
                self.setPlaceTextBoxValue("locatorDescTxt_p",
                                          "telephone_comments".__add__(
                                              Place.generate_random_string(
                                                  10)))

                self.performActionClick("btnUpdateTelephone")
                time.sleep(2)
            if i == no_of_repeats - 1:
                tel_delete_row_xpath = "//*[@id='p".__add__(str(i)) + "']/td[3]/img"
                self.driver.find_element(By.XPATH, tel_delete_row_xpath).click()
                time.sleep(2)

    def editPlaceTelephoneActions(self, edit_option):
        tel_edit_row_xpath = "//*[@id='p".__add__(str(edit_option)) + "']/td[2]/img"
        self.driver.find_element(By.XPATH, tel_edit_row_xpath).click()
        # self.setTelephoneInfo()
        self.setTodayDate(By.ID, "PhoneAsOf")
        self.setTypeId()
        self.setAddUse()
        # country code
        self.setPlaceTextBoxValue("cntryCd", "001")
        # telephone
        self.setPlaceTelephoneValue("phoneNbrTxt", Place.generate_random_int(10))
        # telephone number extension
        self.setPlaceTextBoxValue("phoneExt",
                                  Place.generate_random_int(4))
        # email
        self.setPlaceTextBoxValue("email",
                                  Place.generate_random_string(8).__add__("@omg.com"))
        # url
        self.setPlaceTextBoxValue("urlAddress",
                                  "www.".__add__(Place.generate_random_string(8)).__add__(".com"))
        # Comments
        self.setPlaceTextBoxValue("locatorDescTxt_p",
                                  "telephone_comments".__add__(
                                      Place.generate_random_string(
                                          10)))

        self.performActionClick("btnUpdateTelephone")
        time.sleep(2)

    def setTelephoneInfo(self):
        self.setContactInfoDate()
        self.setTypeId()
        self.setAddUse()
        # country code
        self.setPlaceTextBoxValue("cntryCd", "001")
        # telephone
        self.setPlaceTelephoneValue("phoneNbrTxt", Place.generate_random_int(10))
        # telephone number extension
        self.setPlaceTextBoxValue("phoneExt",
                                  Place.generate_random_int(4))
        # email
        self.setPlaceTextBoxValue("email",
                                  Place.generate_random_string(8).__add__("@omg.com"))
        # url
        self.setPlaceTextBoxValue("urlAddress",
                                  "www.".__add__(Place.generate_random_string(8)).__add__(".com"))
        # Comments
        self.setPlaceTextBoxValue("locatorDescTxt_p",
                                  "telephone_comments".__add__(
                                      Place.generate_random_string(
                                          10)))

    def setContactInfoDate(self):
        addr_birth_day, addr_birth_month, addr_birth_year = NBSUtilities.getRandomDateByVars()
        self.selectCalendarDate(By.ID, 'PhoneAsOfIcon', str(int(addr_birth_day)), addr_birth_month, str(int(addr_birth_year)))

    def setTypeId(self):
        element = self.driver.find_element(By.ID, "phoneType")
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        select_element = Select(self.driver.find_element(By.ID,
                                                         "phoneType"))
        random_index = random.randint(2, len(select_element.options))
        add_place_addr_type_xpath = ("//*[@id='phoneType']/option[".__add__(str(random_index)) + "]")
        addr_type_value = (self.driver.find_element(By.XPATH, add_place_addr_type_xpath)
                           .get_attribute("value"))
        select_element.select_by_value(addr_type_value)

    def setAddUse(self):
        element = self.driver.find_element(By.ID, "phoneUse")
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        select_element = Select(self.driver.find_element(By.ID, "phoneUse"))
        select_element.select_by_value('WP')

    @staticmethod
    def generate_random_int(s_length):
        letters = string.digits
        # Randomly choose characters from letters for the given length of the string
        random_int = ''.join(random.choice(letters) for i in range(s_length))
        return random_int

    def setPlaceTextBoxValue(self, element, value):
        self.driver.find_element(By.NAME, element).clear()
        self.driver.find_element(By.NAME, element).send_keys(value)

    def setPlaceTelephoneValue(self, element, value):
        self.driver.find_element(By.ID, element).clear()
        self.driver.find_element(By.ID, element).send_keys(value)

    def addPlaceToSystem(self):
        self.performActionClick("btnSubmitB")

    def clickPlaceSubmitButton(self):
        self.driver.find_element(By.NAME, self.place_search_submit_id).click()

    def performSearchByElementAndView(self, element, last_name):
        try:
            if element == NBSConstants.ADD_PLACE_LAST_NAME_WILD:
                self.setPlaceLastName(last_name)
            time.sleep(2)
            self.clickPlaceSubmitButton()
            time.sleep(2)
            self.driver.find_element(By.LINK_TEXT, 'View').click()
            return True
        except Exception as e:
            return False

    def performSearchByElement(self, element, random_number, place_uid):
        return_val = True
        if element == NBSConstants.ADD_PLACE_LAST_NAME:
            self.setPlaceLastName(str(random_number))
        time.sleep(2)
        self.clickPlaceSubmitButton()
        try:
            next_elem = self.driver.find_element(By.LINK_TEXT, 'Next')
        except NoSuchElementException:
            next_elem = None
        if next_elem is not None:
            elem1 = self.findElementInMultiplePages(place_uid)
            if elem1 is not None:
                elem1.click()
                time.sleep(2)
                self.driver.find_element(By.LINK_TEXT, 'Return to Search Results').click()
                time.sleep(2)
                self.driver.find_element(By.LINK_TEXT, 'New Search').click()
                time.sleep(2)
            else:
                time.sleep(2)
                self.driver.find_element(By.LINK_TEXT, 'New Search').click()
                time.sleep(2)
        else:
            try:
                empty_check = self.driver.find_element(By.XPATH,
                                                       "//a[contains(@href, 'uid=".__add__(str(place_uid)) +
                                                       "')]")
            except NoSuchElementException:
                empty_check = None
            if empty_check is not None:
                empty_check.click()
                time.sleep(2)
                self.driver.find_element(By.LINK_TEXT, 'Return to Search Results').click()
                time.sleep(2)
                self.driver.find_element(By.LINK_TEXT, 'New Search').click()
            else:
                time.sleep(2)
                # added exception to handle the error when no place available and returning False
                try:
                    self.driver.find_element(By.LINK_TEXT, 'View').click()
                except Exception as e:
                    return_val = False
                # time.sleep(2)
                # self.driver.find_element(By.LINK_TEXT, 'New Search').click()
            time.sleep(2)
        return return_val

    def findElementInMultiplePages(self, place_uid):
        element_find = None
        while True:
            try:
                element_find = self.driver.find_element(By.XPATH,
                                                        "//a[contains(@href, 'uid=".__add__(
                                                            str(place_uid)) + "')]")
                if element_find is not None:
                    break
            except NoSuchElementException:
                try:
                    self.driver.find_element(By.LINK_TEXT, 'Next').click()
                except NoSuchElementException:
                    element_find = None
        return element_find

    def performSearchByInactiveElement(self, element, inactive_place, place_uid):
        if element == NBSConstants.ADD_PLACE_LAST_NAME_WILD:
            self.setPlaceLastName(inactive_place)
        time.sleep(2)
        self.clickPlaceSubmitButton()
        try:
            next_elem = self.driver.find_element(By.LINK_TEXT, 'Next')
        except NoSuchElementException:
            next_elem = None
        if next_elem is not None:
            elem1 = self.findElementInMultiplePages(place_uid)
            if elem1 is not None:
                elem1.click()
                time.sleep(2)
                self.driver.find_element(By.LINK_TEXT, 'Return to Search Results').click()
                time.sleep(2)
                self.driver.find_element(By.LINK_TEXT, 'New Search').click()
                time.sleep(2)
            else:
                time.sleep(2)
                self.driver.find_element(By.LINK_TEXT, 'New Search').click()
                time.sleep(2)
        else:
            try:
                empty_check = self.driver.find_element(By.XPATH,
                                                       "//a[contains(@href, 'uid=".__add__(str(place_uid)) +
                                                       "')]")
            except NoSuchElementException:
                empty_check = None
            if empty_check is not None:
                empty_check.click()
                time.sleep(2)
                self.driver.find_element(By.LINK_TEXT, 'Return to Search Results').click()
                time.sleep(2)
                self.driver.find_element(By.LINK_TEXT, 'New Search').click()
            else:
                time.sleep(2)
                self.driver.find_element(By.LINK_TEXT, 'New Search').click()
            time.sleep(2)
        return True
