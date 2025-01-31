import os
import random
import string
import time

from selenium.common.exceptions import NoSuchElementException
from selenium.webdriver.common.by import By
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.support.ui import Select
from selenium.webdriver.support.ui import WebDriverWait

from src.page_objects.BasePageObject import BasePageObject
from src.utilities.constants import NBSConstants
from src.utilities.log_config import LogUtils


class ProviderObject(BasePageObject):
    logger = LogUtils.loggen(__name__)

    # provider locators
    provider_last_name_id = "providerSearch.lastName"
    provider_first_name_id = "providerSearch.firstName"
    provider_street_addr_id = "providerSearch.streetAddr1"
    provider_city_id = "providerSearch.cityDescTxt"
    provider_state_id = "providerSearch.state"
    provider_zip_id = "providerSearch.zipCd"
    provider_tele1_id = "providerSearch.phoneNbrTxt1"
    provider_tele2_id = "providerSearch.phoneNbrTxt2"
    provider_tele3_id = "providerSearch.phoneNbrTxt3"
    provider_id_type_code = "providerSearch.typeCd"
    provider_id_value_id = "providerSearch.rootExtensionTxt"
    nbs_data_entry_menu_link_text = "Data Entry"
    data_entry_provider_menu_link_text = "Provider"
    add_provider_record_id = "Add"
    provider_add_quick_code = "//*[@id='test']"
    add_provider_header_title = "//*[contains(text(), 'Add Provider')]"
    add_provider_rolesList_xpath = "//*[@id='rolesList']"
    add_provider_general_comments1 = "provider.thePersonDT.description"
    add_provider_name_prefix_xpath = "//*[@id='provider.nmPrefix']"
    add_provider_last_name_id = "provider.lastNm"
    add_provider_first_name_id = "provider.firstNm"
    add_provider_middle_name_id = "provider.middleNm"
    add_provider_suffix_id = "provider.nmSuffix"
    add_provider_degree_id = "provider.nmDegree"
    add_provider_prefix_name = "provider.nmPrefix_textbox"
    add_provider_suffix_name = "provider.nmSuffix_textbox"
    add_provider_degree_name = "provider.nmDegree_textbox"
    provider_search_section_header_label = "Provider Search Criteria"
    provider_last_name_search_page_id = "providerSearch.lastName"
    add_provider_last_name = "LastName"
    provider_search_submit_id = "Submit"
    add_provider_first_name = "FirstName"
    provider_first_name_search_page_id = "providerSearch.firstName"
    provider_address_1_search_page_id = "providerSearch.streetAddr1"
    add_provider_street_address = "address1"
    provider_city_search_page_id = "providerSearch.cityDescTxt"
    add_provider_city_name = "City"
    provider_state_search_page_id = "providerSearch.state"
    add_provider_state_code = "state"
    provider_postal_search_page_id = "providerSearch.zipCd"
    add_provider_postal_code = "postal"
    provider_phone_search_page_id = "providerSearch.phoneNbrTxt"
    add_provider_tel_phone = "Telephone"
    add_provider_ident_select = "Identification"
    add_provider_last_name_wild = "LastNameWild"
    add_provider_general_comments = "General Comments"
    add_provider_address_comments = "Address Comments"
    add_provider_locator_comments = "Telephone Comments"
    provider_ident_type_search_page_id = "//*[@id='providerSearch.typeCd']"
    provider_ident_value_search_page_id = "//*[@id='providerSearch.rootExtensionTxt']"
    add_provider_middle_name = "MiddleName"
    provider_identification_section = "Identification"
    provider_address_section = "Address"
    provider_phone_section = "Telephone"

    # provider init method
    def __init__(self, driver):
        super().__init__(driver)
        self.driver = driver

    # provider actions
    @staticmethod
    def generate_random_string(s_length):
        letters = string.ascii_lowercase
        # Randomly choose characters from letters for the given length of the string
        random_string = ''.join(random.choice(letters) for i in range(s_length))
        return random_string

    @staticmethod
    def generate_random_int(s_length):
        letters = string.digits
        # Randomly choose characters from letters for the given length of the string
        random_int = ''.join(random.choice(letters) for i in range(s_length))
        return random_int

    def setProviderLastName(self, lastname):
        self.driver.find_element(By.ID, self.provider_last_name_id).clear()
        self.driver.find_element(By.ID, self.provider_last_name_id).send_keys(lastname)

    def setProviderFirstName(self, firstname):
        self.driver.find_element(By.ID, self.provider_first_name_id).clear()
        self.driver.find_element(By.ID, self.provider_first_name_id).send_keys(firstname)

    def setProviderStreetAddr(self, streetaddress):
        self.driver.find_element(By.ID, self.provider_street_addr_id).clear()
        self.driver.find_element(By.ID, self.provider_street_addr_id).send_keys(streetaddress)

    def setProviderCityName(self, city):
        self.driver.find_element(By.ID, self.provider_city_id).clear()
        self.driver.find_element(By.ID, self.provider_city_id).send_keys(city)

    def setProviderStateName(self, state):
        element = self.driver.find_element(By.ID, self.provider_state_id)
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        select_element = Select(self.driver.find_element(By.ID, self.provider_state_id))
        select_element.select_by_value(state)

    def setProviderZipCode(self, zipcode):
        self.driver.find_element(By.ID, self.provider_zip_id).clear()
        self.driver.find_element(By.ID, self.provider_zip_id).send_keys(zipcode)

    def setProviderTelephone_1(self, part1):
        self.driver.find_element(By.ID, self.provider_tele1_id).clear()
        self.driver.find_element(By.ID, self.provider_tele1_id).send_keys(part1)

    def setProviderTelephone_2(self, part2):
        self.driver.find_element(By.ID, self.provider_tele2_id).clear()
        self.driver.find_element(By.ID, self.provider_tele2_id).send_keys(part2)

    def setProviderTelephone_3(self, part3):
        self.driver.find_element(By.ID, self.provider_tele3_id).clear()
        self.driver.find_element(By.ID, self.provider_tele3_id).send_keys(part3)

    def setProviderIdTypeName(self, idtype):
        element = self.driver.find_element(By.ID, self.provider_id_type_code)
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        select_element = Select(self.driver.find_element(By.ID, self.provider_id_type_code))
        select_element.select_by_value(idtype)

    def setProviderIdTypeValue(self, value):
        self.driver.find_element(By.ID, self.provider_id_value_id).clear()
        self.driver.find_element(By.ID, self.provider_id_value_id).send_keys(value)

    def clickProviderSubmitButton(self):
        self.driver.find_element(By.ID, self.provider_search_submit_id).click()

    def clickDataEntryMenuLink(self):
        self.driver.find_element(By.LINK_TEXT, self.nbs_data_entry_menu_link_text).click()

    def clickProviderMenuLink(self):
        self.driver.find_element(By.LINK_TEXT, self.data_entry_provider_menu_link_text).click()

    def clickAddProviderButton(self):
        self.driver.find_element(By.ID, self.add_provider_record_id).click()

    def getAddProviderHeader(self):
        return self.driver.find_element(By.XPATH, self.add_provider_header_title).text

    def setQuickCodeForProvider(self, qcode):
        self.driver.find_element(By.XPATH, self.provider_add_quick_code).clear()
        self.driver.find_element(By.XPATH, self.provider_add_quick_code).send_keys(qcode)

    def setAddProviderRoleId(self):
        element = Select(self.driver.find_element(By.XPATH, self.add_provider_rolesList_xpath))
        size = len(element.options)
        if element.is_multiple:
            element.deselect_all()
            element.select_by_index(random.randint(1, size - 1))
            element.select_by_index(random.randint(1, size - 1))
        time.sleep(1)

    def setAddProviderGeneralComments(self):
        self.driver.find_element(By.ID, self.add_provider_general_comments1).clear()
        self.driver.find_element(By.ID, self.add_provider_general_comments1).send_keys(
            self.add_provider_general_comments.__add__(ProviderObject.generate_random_string(100)))

    def setAddProviderNamePrefix(self):
        element = self.driver.find_element(By.ID, "provider.nmPrefix")
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        select_element = Select(self.driver.find_element(By.ID,
                                                         "provider.nmPrefix"))
        size = len(select_element.options) - 1
        select_element.select_by_index(random.randint(1, size))

    def setAddProviderLastName(self, last_name):
        self.driver.find_element(By.ID, self.add_provider_last_name_id).clear()
        self.driver.find_element(By.ID, self.add_provider_last_name_id).send_keys(last_name)

    def setAddProviderFirstName(self, first_name):
        self.driver.find_element(By.ID, self.add_provider_first_name_id).clear()
        self.driver.find_element(By.ID, self.add_provider_first_name_id).send_keys(first_name)

    def setAddProviderMiddleName(self, middle_name):
        self.driver.find_element(By.ID, self.add_provider_middle_name_id).clear()
        self.driver.find_element(By.ID, self.add_provider_middle_name_id).send_keys(middle_name)

    def setAddProviderNameSuffix(self):
        element = self.driver.find_element(By.ID, "provider.nmSuffix")
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        select_element = Select(self.driver.find_element(By.ID,
                                                         "provider.nmSuffix"))
        size = len(select_element.options)
        select_element.select_by_index(random.randint(1, size - 1))

    def setAddProviderDegreeName(self):
        element = self.driver.find_element(By.ID, "provider.nmDegree")
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        select_element = Select(self.driver.find_element(By.ID,
                                                         "provider.nmDegree"))
        size = len(select_element.options) - 1
        select_element.select_by_index(random.randint(1, size))

    def setProviderIdentificationActions(self, no_of_repeats, random_number):
        for i in range(1, no_of_repeats + 1):
            if i == no_of_repeats - 2:
                identi_edit_row_xpath = "//*[@id='nestedElementsHistoryBox|Identification']/tr[".__add__(
                    str(i)) + "]/td[1]/a[1]"
                self.driver.find_element(By.XPATH, identi_edit_row_xpath).click()
                time.sleep(2)
                selected_type_cd_value = self.setProviderIdentificationTypeCode(random_number)
                self.setProviderIdentificationAssignAuth(random_number)
                self.setProviderIdentificationTypeCodeValue(selected_type_cd_value, random_number)
                self.driver.find_element(By.ID, "BatchEntryAddButtonIdentification").click()
                time.sleep(1)
            if i == no_of_repeats:
                identi_delete_row_xpath = "//*[@id='nestedElementsHistoryBox|Identification']/tr[".__add__(
                    str(i)) + "]/td[1]/a[2]"
                self.driver.find_element(By.XPATH, identi_delete_row_xpath).click()
                time.sleep(1)

    def setProviderIdentificationTypeCode(self, random_number):
        element = self.driver.find_element(By.ID, "provider.entityIdDT_s[i].typeCd")
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        select_element = Select(self.driver.find_element(By.ID,
                                                         "provider.entityIdDT_s[i].typeCd"))
        random_index = random.randint(2, len(select_element.options))
        add_provider_ident_type_cd_opt_xpath = (
                "//*[@id='provider.entityIdDT_s[i].typeCd']/option["
                .__add__(str(random_index)) + "]")
        identi_type_value = (self.driver.find_element(By.XPATH, add_provider_ident_type_cd_opt_xpath)
                             .get_attribute("value"))
        select_element.select_by_value(identi_type_value)
        if identi_type_value == "OTH":
            element = self.driver.find_element(By.ID, "nestedElementsControllerPayloadprovider.entityIdDT_s[i].typeCdc")
            self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
            self.driver.find_element(By.ID, "provider.entityIdDT_s[i].typeDescTxt").clear()
            self.driver.find_element(By.ID, "provider.entityIdDT_s[i].typeDescTxt"
                                     ).send_keys(identi_type_value + "-" + random_number)
        else:
            element = self.driver.find_element(By.ID, "nestedElementsControllerPayloadprovider.entityIdDT_s[i].typeCdc")
            self.driver.execute_script("arguments[0].setAttribute('class','none')", element)

        time.sleep(1)
        return identi_type_value

    def setProviderIdentificationAssignAuth(self, random_number):
        element = self.driver.find_element(By.ID, "provider.entityIdDT_s[i].assigningAuthorityCd")
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        select_element = Select(self.driver.find_element(By.ID,
                                                         "provider.entityIdDT_s[i].assigningAuthorityCd"))
        random_index = random.randint(2, len(select_element.options) - 1)
        add_provider_ident_type_cd_opt_xpath = (
                "//*[@id='provider.entityIdDT_s[i].assigningAuthorityCd']/option["
                .__add__(str(random_index)) + "]")

        identi_assign_value = (self.driver.find_element(By.XPATH, add_provider_ident_type_cd_opt_xpath)
                               .get_attribute("value"))
        select_element.select_by_value(identi_assign_value)
        if identi_assign_value == "OTH":
            element = self.driver.find_element(By.ID, "nestedElementsControllerPayloadprovider.entityIdDT_s["
                                                      "i].assigningAuthorityCd")
            self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
            self.driver.find_element(By.ID, "provider.entityIdDT_s[i].assigningAuthorityDescTxt").clear()
            self.driver.find_element(By.ID, "provider.entityIdDT_s[i].assigningAuthorityDescTxt"
                                     ).send_keys(identi_assign_value + "-" + random_number)
        else:
            element = self.driver.find_element(By.ID, "nestedElementsControllerPayloadprovider.entityIdDT_s["
                                                      "i].assigningAuthorityCd")
            self.driver.execute_script("arguments[0].setAttribute('class','none')", element)

    def setProviderIdentificationTypeCodeValue(self, selected_type_cd_value, random_value):
        self.driver.find_element(By.ID, "provider.entityIdDT_s[i].rootExtensionTxt").clear()
        self.driver.find_element(By.ID, "provider.entityIdDT_s[i].rootExtensionTxt"
                                 ).send_keys(selected_type_cd_value + "-" + random_value)

    def populateAddressRecord(self, index, random_number):
        self.setProviderAddressByUse()
        self.setProviderAddressByType()
        self.setProviderAddressStreet1(random_number.__add__(str(index)))
        self.setProviderAddressStreet2(random_number.__add__(str(index)))
        self.setProviderAddressCity()
        self.setProviderAddressState()
        self.setProviderAddressZip()
        self.setProviderAddressCounty()
        self.setProviderAddressComments()

    def setProviderAddressActions(self, no_of_repeats, random_number):
        for i in range(1, no_of_repeats + 1):
            if i == no_of_repeats - 2:
                addr_edit_row_xpath = "//*[@id='nestedElementsHistoryBox|Address']/tr[".__add__(
                    str(i)) + "]/td[1]/a[1]"
                self.driver.find_element(By.XPATH, addr_edit_row_xpath).click()
                time.sleep(2)
                self.setProviderAddressByUse()
                self.setProviderAddressByType()
                self.setProviderAddressStreet1(random_number.__add__(str(i)))
                self.setProviderAddressStreet2(random_number.__add__(str(i)))
                self.setProviderAddressCity()
                self.setProviderAddressState()
                self.setProviderAddressZip()
                self.setProviderAddressCounty()
                self.setProviderAddressComments()
                self.driver.find_element(By.ID, "BatchEntryAddButtonAddress").click()
                time.sleep(3)
            if i == no_of_repeats:
                addr_delete_row_xpath = "//*[@id='nestedElementsHistoryBox|Address']/tr[".__add__(
                    str(i)) + "]/td[1]/a[2]"
                self.driver.find_element(By.XPATH, addr_delete_row_xpath).click()
                time.sleep(3)

    def setProviderAddressByUse(self):
        self.setProviderElementVisible("address[i].useCd")
        select_element = Select(self.driver.find_element(By.ID,
                                                         "address[i].useCd"))
        random_index = random.randint(2, len(select_element.options))

        add_provider_addr_type_use_xpath = (
                "//*[@id='address[i].useCd']/option["
                .__add__(str(random_index)) + "]")
        addr_type_use_value = (self.driver.find_element(By.XPATH, add_provider_addr_type_use_xpath)
                               .get_attribute("value"))
        if self.driver.find_element(By.ID, "address[i].useCd").is_displayed():
            select_element.select_by_value(addr_type_use_value)
        else:
            self.setProviderElementVisible("address[i].useCd")
            select_element.select_by_value(addr_type_use_value)

        self.setProviderElementInVisible("address[i].useCd")

    def setProviderAddressByType(self):
        element = self.driver.find_element(By.ID, "address[i].cd")
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        select_element = Select(self.driver.find_element(By.ID,
                                                         "address[i].cd"))
        random_index = random.randint(2, len(select_element.options))
        add_provider_addr_type_xpath = (
                "//*[@id='address[i].cd']/option["
                .__add__(str(random_index)) + "]")
        addr_type_value = (self.driver.find_element(By.XPATH, add_provider_addr_type_xpath)
                           .get_attribute("value"))
        select_element.select_by_value(addr_type_value)

    def setProviderAddressStreet1(self, random_value):
        self.driver.find_element(By.ID, "address[i].thePostalLocatorDT_s.streetAddr1").clear()
        self.driver.find_element(By.ID, "address[i].thePostalLocatorDT_s.streetAddr1"
                                 ).send_keys("address1-" + random_value)

    def setProviderAddressStreet2(self, random_value):
        self.driver.find_element(By.ID, "address[i].thePostalLocatorDT_s.streetAddr2").clear()
        self.driver.find_element(By.ID, "address[i].thePostalLocatorDT_s.streetAddr2"
                                 ).send_keys("address2-" + random_value)

    def setProviderAddressCity(self):
        self.driver.find_element(By.ID, "address[i].thePostalLocatorDT_s.cityDescTxt").clear()
        self.driver.find_element(By.ID, "address[i].thePostalLocatorDT_s.cityDescTxt"
                                 ).send_keys(
            self.add_provider_city_name + "-" + ProviderObject.generate_random_string(5))

    def setProviderAddressState(self):
        element = self.driver.find_element(By.ID, "address[i].thePostalLocatorDT_s.stateCd")
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        select_element = Select(self.driver.find_element(By.ID,
                                                         "address[i].thePostalLocatorDT_s.stateCd"))
        random_index = random.randint(2, len(select_element.options))
        add_provider_addr_state_xpath = (
                "//*[@id='address[i].thePostalLocatorDT_s.stateCd']/option["
                .__add__(str(random_index)) + "]")
        addr_state_value = (self.driver.find_element(By.XPATH, add_provider_addr_state_xpath)
                            .get_attribute("value"))
        select_element.select_by_value(addr_state_value)

    def setProviderAddressZip(self):
        self.driver.find_element(By.ID, "address[i].thePostalLocatorDT_s.zipCd").clear()
        self.driver.find_element(By.ID, "address[i].thePostalLocatorDT_s.zipCd"
                                 ).send_keys(random.randint(11111, 99999))

    def setProviderAddressCounty(self):
        element = self.driver.find_element(By.ID, "address[i].thePostalLocatorDT_s.cntyCd")
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        select_element = Select(self.driver.find_element(By.ID,
                                                         "address[i].thePostalLocatorDT_s.cntyCd"))
        random_index = random.randint(2, len(select_element.options))
        add_provider_addr_county_xpath = (
                "//*[@id='address[i].thePostalLocatorDT_s.cntyCd']/option["
                .__add__(str(random_index)) + "]")
        addr_state_value = (self.driver.find_element(By.XPATH, add_provider_addr_county_xpath)
                            .get_attribute("value"))
        select_element.select_by_value(addr_state_value)

    def setProviderAddressComments(self):
        self.driver.find_element(By.ID, "address[i].locatorDescTxt").clear()
        self.driver.find_element(By.ID, "address[i].locatorDescTxt"
                                 ).send_keys(
            self.add_provider_address_comments.__add__(ProviderObject.generate_random_string(80)))

    def setProviderTelephoneActions(self, no_of_repeats):
        for i in range(1, no_of_repeats + 1):
            if i == no_of_repeats - 2:
                addr_edit_row_xpath = "//*[@id='nestedElementsHistoryBox|Telephone']/tr[".__add__(
                    str(i)) + "]/td[1]/a[1]"
                self.driver.find_element(By.XPATH, addr_edit_row_xpath).click()
                self.setProviderTelephonePopulate()
                self.performActionClick("BatchEntryAddButtonTelephone")
                self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
            if i == no_of_repeats:
                addr_delete_row_xpath = "//*[@id='nestedElementsHistoryBox|Telephone']/tr[".__add__(
                    str(i)) + "]/td[1]/a[2]"
                self.driver.find_element(By.XPATH, addr_delete_row_xpath).click()
                self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)

    def setProviderTelephoneByUse(self):
        self.setProviderSelectionDropdown("telephone[i].useCd", "//*[@id='telephone[i].useCd']")

    def setProviderTelephoneByType(self):
        self.setProviderSelectionDropdown("telephone[i].cd", "//*[@id='telephone[i].cd']")

    def performRefineSearchValidation(self, random_number, city_dict, phone_dict, ident_dict):
        self.setValueForHTMLElement("TEXTBOX", NBSConstants.TYPE_CODE_ID,
                                    self.provider_last_name_search_page_id,
                                    self.add_provider_last_name.__add__("-").__add__(str(random_number)))

        self.setValueForHTMLElement("TEXTBOX", NBSConstants.TYPE_CODE_ID,
                                    self.provider_first_name_search_page_id,
                                    self.add_provider_first_name.__add__("-").__add__(str(random_number)))

        self.setValueForHTMLElement("TEXTBOX", NBSConstants.TYPE_CODE_ID,
                                    self.provider_address_1_search_page_id,
                                    city_dict.get("address"))

        self.setValueForHTMLElement("TEXTBOX", NBSConstants.TYPE_CODE_ID,
                                    self.provider_city_search_page_id,
                                    city_dict.get("city"))
        self.selectDropDownOptionValueRandom(self.provider_state_search_page_id, city_dict.get("state"))

        self.setValueForHTMLElement("TEXTBOX", NBSConstants.TYPE_CODE_ID,
                                    self.provider_postal_search_page_id,
                                    city_dict.get("postal"))
        phone_number = phone_dict.get("telephone")

        self.setValueForHTMLElement("TEXTBOX", NBSConstants.TYPE_CODE_ID,
                                    self.provider_phone_search_page_id.__add__("1"),
                                    phone_number[:3])
        self.setValueForHTMLElement("TEXTBOX", NBSConstants.TYPE_CODE_ID,
                                    self.provider_phone_search_page_id.__add__("2"),
                                    phone_number[4:7])
        self.setValueForHTMLElement("TEXTBOX", NBSConstants.TYPE_CODE_ID,
                                    self.provider_phone_search_page_id.__add__("3"),
                                    phone_number[8:12])
        self.selectDropDownOptionValueRandom("providerSearch.typeCd", ident_dict.get("identi_select"))

        self.setValueForHTMLElement("TEXTBOX", NBSConstants.TYPE_CODE_XPATH,
                                    self.provider_ident_value_search_page_id,
                                    ident_dict.get("identi_value"))

        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                        NBSConstants.WAITING_TIME_IN_SECONDS)

        self.performButtonClick(NBSConstants.TYPE_CODE_ID, ProviderObject.provider_search_submit_id)
        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                        NBSConstants.WAITING_TIME_IN_SECONDS)
        self.performButtonClick("LINK_TEXT", 'Refine Search')
        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                        NBSConstants.WAITING_TIME_IN_SECONDS)
        return True

    def performProviderSearch(self, type_code, path, append_element, random_number):
        if append_element == self.add_provider_tel_phone:
            self.setValueForHTMLElement("TEXTBOX", type_code, path.__add__("1"), random_number[:3])
            self.setValueForHTMLElement("TEXTBOX", type_code, path.__add__("2"), random_number[4:7])
            self.setValueForHTMLElement("TEXTBOX", type_code, path.__add__("3"), random_number[8:12])
        elif append_element == self.add_provider_state_code:
            self.selectDropDownOptionValueRandom(path)
        elif append_element == self.add_provider_ident_select:
            self.selectDropDownOptionValueRandom("providerSearch.typeCd", random_number.get("identi_select"))
            self.setValueForHTMLElement("TEXTBOX", type_code, ProviderObject.provider_ident_value_search_page_id,
                                        random_number.get("identi_value"))
        elif append_element == self.add_provider_postal_code:
            self.setValueForHTMLElement("TEXTBOX", type_code, path, random_number)
        elif append_element == self.add_provider_last_name_wild:
            self.setValueForHTMLElement("TEXTBOX", type_code, path, "%")
        elif append_element == self.add_provider_city_name:
            self.setValueForHTMLElement("TEXTBOX", type_code, path, random_number)
        else:
            self.setValueForHTMLElement("TEXTBOX", type_code, path,
                                        append_element.__add__("-").__add__(str(random_number)))
        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                        NBSConstants.WAITING_TIME_IN_SECONDS)
        self.performButtonClick(NBSConstants.TYPE_CODE_ID, self.provider_search_submit_id)
        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                        NBSConstants.WAITING_TIME_IN_SECONDS)

    def performSearchByElement(self, type_code, path, append_text, random_number, provider_uid):
        self.performProviderSearch(type_code, path, append_text, random_number)
        # check next link on the results page--wildcard
        try:
            next_elem = self.driver.find_element(By.LINK_TEXT, "Next")
        except NoSuchElementException:
            next_elem = None
        if next_elem is not None:
            elem1 = self.findElementInMultiplePages(provider_uid)
            if elem1 is not None:
                elem1.click()
                self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
                self.performButtonClick("LINK_TEXT", 'Return to Search Results')
                self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
                self.performButtonClick("LINK_TEXT", 'New Search')
                self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
            else:
                self.performButtonClick("LINK_TEXT", 'New Search')
                self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
        else:
            try:
                empty_check = self.findElement(NBSConstants.TYPE_CODE_XPATH,
                                               "//a[contains(@href, 'uid=".__add__(str(provider_uid)) + "')]")
            except:
                empty_check = None
            if empty_check is not None:
                empty_check.click()
                self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
                self.performButtonClick("LINK_TEXT", 'Return to Search Results')
                self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
                self.performButtonClick("LINK_TEXT", 'New Search')
            else:
                self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
                self.performButtonClick("LINK_TEXT", 'New Search')
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
        return True

    def setProviderSelectionDropdown(self, element1, element2):
        element = self.driver.find_element(By.ID, element1)
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        select_element = Select(self.driver.find_element(By.ID, element1))
        random_index = random.randint(2, len(select_element.options))
        add_provider_element2_xpath = (
            element2.__add__("/option[".__add__(str(random_index)) + "]"))
        provider_element2_value = (self.driver.find_element(By.XPATH, add_provider_element2_xpath)
                                   .get_attribute("value"))
        select_element.select_by_value(provider_element2_value)

    def setProviderTextBoxValue(self, element, value):
        self.driver.find_element(By.ID, element).clear()
        self.driver.find_element(By.ID, element).send_keys(value)

    def performActionClick(self, element):
        self.driver.find_element(By.ID, element).click()

    def setProviderElementVisible(self, element):
        element = self.driver.find_element(By.ID, element)
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)

    def setProviderElementInVisible(self, element):
        element = self.driver.find_element(By.ID, element)
        self.driver.execute_script("arguments[0].setAttribute('class','none')", element)

    def setProviderAdminInfoSection(self, random_number):
        self.setQuickCodeForProvider(random_number[:10])
        self.setAddProviderRoleId()
        self.setAddProviderGeneralComments()
        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)

    def setProviderNameSection(self, random_number):
        self.setAddProviderNamePrefix()
        self.setAddProviderLastName(self.add_provider_last_name
                                    .__add__("-").__add__(str(random_number)))
        self.setAddProviderFirstName(self.add_provider_first_name
                                     .__add__("-").__add__(str(random_number)))
        self.setAddProviderMiddleName(self.add_provider_middle_name
                                      .__add__("-").__add__(str(random_number)))
        self.setAddProviderNameSuffix()
        self.setAddProviderDegreeName()
        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)

    def setProviderIdentificationRecordPopulate(self, random_number):
        selected_type_cd_value = self.setProviderIdentificationTypeCode(random_number)
        self.setProviderIdentificationAssignAuth(random_number)
        self.setProviderIdentificationTypeCodeValue(selected_type_cd_value, random_number)

    def setProviderAddressRecordPopulate(self, random_number):
        self.setProviderAddressByUse()
        self.setProviderAddressByType()
        self.setProviderAddressStreet1(
            random_number.__add__("-").__add__(str(ProviderObject.generate_random_int(3))))
        self.setProviderAddressStreet2(
            random_number.__add__("-").__add__(str(ProviderObject.generate_random_int(3))))
        self.setProviderAddressCity()
        self.setProviderAddressState()
        self.setProviderAddressZip()
        self.setProviderAddressCounty()
        self.setProviderAddressComments()

    def setProviderTelephonePopulate(self):
        self.setProviderTelephoneByUse()
        self.setProviderTelephoneByType()
        # country code
        self.setProviderTextBoxValue("telephone[i].theTeleLocatorDT_s.cntryCd", "001")
        # telephone number first 3 digits
        self.setProviderTextBoxValue("telephone[i].theTeleLocatorDT_s.phoneNbrTxt1",
                                     ProviderObject.generate_random_int(3))
        # telephone number middle 3 digits
        self.setProviderTextBoxValue("telephone[i].theTeleLocatorDT_s.phoneNbrTxt2",
                                     ProviderObject.generate_random_int(3))
        # telephone number last 4 digits
        self.setProviderTextBoxValue("telephone[i].theTeleLocatorDT_s.phoneNbrTxt3",
                                     ProviderObject.generate_random_int(4))
        # telephone number extension
        self.setProviderTextBoxValue("telephone[i].theTeleLocatorDT_s.extensionTxt",
                                     ProviderObject.generate_random_int(4))
        # email
        self.setProviderTextBoxValue("telephone[i].theTeleLocatorDT_s.emailAddress",
                                     ProviderObject.generate_random_string(8).__add__("@omg.com"))
        # url
        self.setProviderTextBoxValue("telephone[i].theTeleLocatorDT_s.urlAddress",
                                     "www.".__add__(ProviderObject.generate_random_string(8)).__add__(".com"))
        # Comments
        self.setProviderTextBoxValue("telephone[i].locatorDescTxt",
                                     self.add_provider_locator_comments.__add__(
                                         ProviderObject.generate_random_string(
                                             100)))

    def editExistingRecord(self, section_type, record_number, random_number):
        edit_section_record = "//*[@id='nestedElementsHistoryBox|" + section_type + "']/tr[".__add__(
            str(record_number)) + "]/td[1]/a[1]"
        # self.logger.info(edit_section_record)
        # self.logger.info(random_number)
        element = self.findElement("XPATH", edit_section_record)
        if element is not None:
            element.click()
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            2)
            self.populateSectionRecord(section_type, str(random_number))
            self.addRecordToSection(section_type)
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)

    def deleteExistingRecord(self, section_type, record_number, random_number):
        identi_delete_row_xpath = "//*[@id='nestedElementsHistoryBox|" + section_type + "']/tr[".__add__(
            str(record_number)) + "]/td[1]/a[2]"
        element = self.findElement("XPATH", identi_delete_row_xpath)
        if element is not None:
            element.click()
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)

    def displayRecordDetails(self, section_type, record_number):
        display_record_details = "//*[@id='nestedElementsHistoryBox|" + section_type + "']/tr[".__add__(
            str(record_number)) + "]/td[1]/a"
        element = self.findElement("XPATH", display_record_details)
        if element is not None:
            element.click()
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)

    def validateDetailsLink(self):
        self.displayRecordDetails(self.provider_identification_section, 1)
        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
        self.displayRecordDetails(self.provider_address_section, 1)
        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
        self.displayRecordDetails(self.provider_phone_section, 1)
        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
        elements = self.findElements(NBSConstants.TYPE_CODE_LINK_TEXT, "Back to Top")
        if len(elements) > 0:
            elements[-1].click()

    def makeProviderInactive(self):
        self.performButtonClick(NBSConstants.TYPE_CODE_ID, "Inactivate")
        try:
            WebDriverWait(self.driver, 5).until(EC.alert_is_present())
            alert = self.driver.switch_to.alert
            alert.accept()
            return True
        except Exception as ex:
            self.logger.info("Error while accepting popup confirmation")
            self.logger.exception(ex)
            return False

    def setRepeatableSectionActions(self, section_type, random_number):
        self.editExistingRecord(section_type, NBSConstants.EDIT_RECORD_NUM_IN_SECTION, str(random_number))
        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                        NBSConstants.WAITING_TIME_IN_SECONDS)
        self.deleteExistingRecord(section_type, NBSConstants.DELETE_RECORD_NUM_IN_SECTION, str(random_number))
        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                        NBSConstants.WAITING_TIME_IN_SECONDS)

    def setAllRepeatableSections(self, random_number):
        try:
            # Add Records To Identification Information Section
            self.setRepeatableSections(self.provider_identification_section, str(random_number))
            # Perform Actions (Edit/Delete) To Identification Information Section
            self.setRepeatableSectionActions(self.provider_identification_section, str(random_number))
            # Address Information Section
            self.setRepeatableSections(self.provider_address_section, str(random_number))
            # Perform Actions (Edit/Delete) To Address Section
            self.setRepeatableSectionActions(self.provider_address_section, str(random_number))
            # Telephone Information Section
            self.setRepeatableSections(self.provider_phone_section, str(random_number))
            # Perform Actions (Edit/Delete) To Phone Section
            self.setRepeatableSectionActions(self.provider_phone_section, str(random_number))
            self.logger.info("Repeatable Sections are done")
        except:
            self.logger.exception(msg="Error While setting up repeatable sections")

    def addNewProvider(self, random_number):
        try:
            self.logger.info("started addNewProvider")
            self.setProviderAdminInfoSection(random_number)
            self.setProviderNameSection(random_number)
            self.setAllRepeatableSections(random_number)
            # Add Provider to the System
            self.performButtonClick(NBSConstants.TYPE_CODE_XPATH, "//*[@id='Submit']")
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            2)
        except:
            self.logger.info("Error While creating new provider")
            return False
        if "PSN" in self.getTextValueFromPage(NBSConstants.TYPE_CODE_XPATH, "//*[contains(text(), 'Provider ID:')]"):
            return True
        else:
            return False

    def performEditProvider(self, random_number):
        try:
            self.performButtonClick(NBSConstants.TYPE_CODE_XPATH, "//*[@id='Edit']")
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
            # elements = self.findElements(NBSConstants.TYPE_CODE_NAME, "tba")
            # elements[0].click()
            self.performButtonClick(NBSConstants.TYPE_CODE_XPATH, "//*[@id='c']")
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
            self.setAddProviderRoleId()
            self.setAddProviderGeneralComments()
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
            # Name Section
            self.setProviderNameSection(str(random_number))

            # self.setAddProviderNameSuffix()
            # self.setAddProviderDegreeName()
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
            self.setAllRepeatableSections(random_number)
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            2)
            # Add Provider to the System
            self.performButtonClick(NBSConstants.TYPE_CODE_ID, "Submit")
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            2)
        except Exception as ex:
            self.logger.info("Error While Editing the existing provider")
            self.logger.exception(ex)
            return False

        if "PSN" in self.getTextValueFromPage(NBSConstants.TYPE_CODE_XPATH, "//*[contains(text(), 'Provider ID:')]"):
            return True
        else:
            return False

    def createNewProviderFromExistingProvider(self, random_number):
        try:
            self.performButtonClick(NBSConstants.TYPE_CODE_XPATH, "//*[@id='Edit']")
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
            # elements = self.findElements(NBSConstants.TYPE_CODE_NAME, "tba")

            self.performButtonClick(NBSConstants.TYPE_CODE_XPATH, "//*[@id='n']")
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)

            if isinstance(random_number, int):
                random_number = random_number + int(ProviderObject.generate_random_int(3))
            elif isinstance(random_number, str):
                random_number = int(random_number) + int(ProviderObject.generate_random_int(3))
            # self.logger.info("random_number:::"+str(random_number))
            # Administrative Information section
            self.setProviderAdminInfoSection(str(random_number))

            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)

            # Name Section
            self.setProviderNameSection(str(random_number))
            # Repeatable Sections
            self.setAllRepeatableSections(random_number)
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
            # Create New Provider to the System from the Edit Screen
            self.performButtonClick(NBSConstants.TYPE_CODE_ID, "Submit")
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            2)
            try:
                WebDriverWait(self.driver, 5).until(EC.alert_is_present())
                alert = self.driver.switch_to.alert
                alert.accept()
                self.performButtonClick(NBSConstants.TYPE_CODE_XPATH, "//*[@id='n']")
                self.setQuickCodeForProvider(random_number[:10])
                self.performButtonClick(NBSConstants.TYPE_CODE_ID, "Submit")
                self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                NBSConstants.WAITING_TIME_IN_SECONDS)
            except:
                pass
        except:
            self.logger.info("Error While Creating the Provider from Edit Screen")

        if "PSN" in self.getTextValueFromPage(NBSConstants.TYPE_CODE_XPATH, "//*[contains(text(), 'Provider ID:')]"):
            return True
        else:
            return False
