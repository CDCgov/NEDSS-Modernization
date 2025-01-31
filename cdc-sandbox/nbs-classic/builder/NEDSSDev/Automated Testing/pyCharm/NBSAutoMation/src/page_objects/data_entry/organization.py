import random
import string
import time

from selenium.common import NoSuchElementException
from selenium.webdriver.common.by import By
from selenium.webdriver.support.select import Select
from src.utilities.constants import NBSConstants
from src.utilities.dbconnections import close_db_connection


class Organization:
    org_search_submit_id = "Submit"
    add_org_header_title = "//*[contains(text(), 'Add Organization')]"

    def __init__(self, driver):
        self.driver = driver

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

    def setOrgLastName(self, lastname):
        self.driver.find_element(By.ID, "organizationSearch.nmTxt").clear()
        self.driver.find_element(By.ID, "organizationSearch.nmTxt").send_keys(lastname)

    def setOrgLastNameEdit(self, lastname):
        self.driver.find_element(By.ID, "name.nmTxt").clear()
        self.driver.find_element(By.ID, "name.nmTxt").send_keys(lastname)

    def setOrgSubmit(self):
        self.driver.find_element(By.ID, "Submit").click()

    def setOrgAdd(self):
        self.driver.find_element(By.NAME, "Add").click()

    def getAddOrganizationHeader(self):
        return self.driver.find_element(By.XPATH, self.add_org_header_title).text

    # Add Organization Admin Section
    def setOrgAdminSection(self, random_number):
        self.setOrganizationQuickCode(random_number[:10])
        self.setStandardIndustryClass()
        self.setOrgRole()
        self.setOrgComments()

    # Add organization Identification section
    def setOrgIdentitySection(self, no_of_repeats, random_number):
        for i in range(no_of_repeats):
            selected_type_cd_value = self.setIdType(random_number)
            self.setAssigningAuth(random_number)
            self.setIdValue(selected_type_cd_value, random_number)
            self.addIdRowToOrganization()
        self.setOrganizationIdActions(no_of_repeats, random_number)
        time.sleep(2)

    # Add org Address section
    def setOrgAddressSection(self, no_of_repeats, random_number):
        for i in range(1, no_of_repeats + 1):
            self.setAddressUse()
            self.setType()
            self.setStreetAddress1(random_number.__add__(str(i)))
            self.setStreetAddress2(random_number.__add__(str(i)))
            self.setCity()
            self.setState()
            self.setZipcode()
            self.setCounty()
            self.setAddressComments()
            self.addAddressRowToOrg()
            time.sleep(3)
        self.setOrgAddressActions(no_of_repeats, random_number)
        time.sleep(2)

    def setOrgAddressActions(self, no_of_repeats, random_number):
        for i in range(1, no_of_repeats + 1):
            if i == no_of_repeats - 2:
                addr_edit_row_xpath = "//*[@id='nestedElementsHistoryBox|Address']/tr[".__add__(
                    str(i)) + "]/td[1]/a[1]"
                self.driver.find_element(By.XPATH, addr_edit_row_xpath).click()
                time.sleep(2)
                self.setAddressUse()
                self.setType()
                self.setStreetAddress1(random_number.__add__(str(i)))
                self.setStreetAddress2(random_number.__add__(str(i)))
                self.setCity()
                self.setState()
                self.setZipcode()
                self.setCounty()
                self.setAddressComments()
                self.driver.find_element(By.ID, "BatchEntryAddButtonAddress").click()
                time.sleep(3)
            if i == no_of_repeats:
                addr_delete_row_xpath = "//*[@id='nestedElementsHistoryBox|Address']/tr[".__add__(
                    str(i)) + "]/td[1]/a[2]"
                self.driver.find_element(By.XPATH, addr_delete_row_xpath).click()
                time.sleep(3)

    def editOrgAddressActions(self, edit_option, random_number):
        addr_edit_row_xpath = "//*[@id='nestedElementsHistoryBox|Address']/tr[".__add__(
            str(edit_option)) + "]/td[1]/a[1]"
        self.driver.find_element(By.XPATH, addr_edit_row_xpath).click()
        time.sleep(2)
        self.setAddressUse()
        self.setType()
        self.setStreetAddress1(random_number.__add__(str(edit_option)))
        self.setStreetAddress2(random_number.__add__(str(edit_option)))
        self.setCity()
        self.setState()
        self.setZipcode()
        self.setCounty()
        self.setAddressComments()
        self.driver.find_element(By.ID, "BatchEntryAddButtonAddress").click()
        time.sleep(3)

    def addAddressRowToOrg(self):
        self.driver.find_element(By.ID, "BatchEntryAddButtonAddress").click()

    def setTelephoneInfo(self):
        self.setAddUse()
        self.setTypeId()
        # country code
        self.setOrgTextBoxValue("telephone[i].theTeleLocatorDT_s.cntryCd", "001")
        # telephone number first 3 digits
        self.setOrgTextBoxValue("telephone[i].theTeleLocatorDT_s.phoneNbrTxt1",
                                Organization.generate_random_int(3))
        # telephone number middle 3 digits
        self.setOrgTextBoxValue("telephone[i].theTeleLocatorDT_s.phoneNbrTxt2",
                                Organization.generate_random_int(3))
        # telephone number last 4 digits
        self.setOrgTextBoxValue("telephone[i].theTeleLocatorDT_s.phoneNbrTxt3",
                                Organization.generate_random_int(4))
        # telephone number extension
        self.setOrgTextBoxValue("telephone[i].theTeleLocatorDT_s.extensionTxt",
                                Organization.generate_random_int(4))
        # email
        self.setOrgTextBoxValue("telephone[i].theTeleLocatorDT_s.emailAddress",
                                Organization.generate_random_string(8).__add__("@omg.com"))
        # url
        self.setOrgTextBoxValue("telephone[i].theTeleLocatorDT_s.urlAddress",
                                "www.".__add__(Organization.generate_random_string(8)).__add__(".com"))
        # Comments
        self.setOrgTextBoxValue("telephone[i].locatorDescTxt",
                                "telephone_comments".__add__(
                                    Organization.generate_random_string(
                                        80)))

    def setOrgTelephoneSection(self, no_of_repeats):
        for i in range(1, no_of_repeats + 1):
            # Populate Telephone Record
            self.setTelephoneInfo()
            # Add Telephone Row to Table
            self.performActionClick("BatchEntryAddButtonTelephone")
            time.sleep(1)
        self.setOrgTelephoneActions(no_of_repeats)
        time.sleep(2)

    def performActionClick(self, element):
        self.driver.find_element(By.ID, element).click()

    def setOrgTelephoneActions(self, no_of_repeats):
        for i in range(1, no_of_repeats + 1):
            if i == no_of_repeats - 2:
                addr_edit_row_xpath = "//*[@id='nestedElementsHistoryBox|Telephone']/tr[".__add__(
                    str(i)) + "]/td[1]/a[1]"
                self.driver.find_element(By.XPATH, addr_edit_row_xpath).click()
                self.setTelephoneInfo()
                self.performActionClick("BatchEntryAddButtonTelephone")
                time.sleep(2)
            if i == no_of_repeats:
                addr_delete_row_xpath = "//*[@id='nestedElementsHistoryBox|Telephone']/tr[".__add__(
                    str(i)) + "]/td[1]/a[2]"
                self.driver.find_element(By.XPATH, addr_delete_row_xpath).click()
                time.sleep(2)

    def editOrgTelephoneActions(self, edit_option):
        addr_edit_row_xpath = "//*[@id='nestedElementsHistoryBox|Telephone']/tr[".__add__(
            str(edit_option)) + "]/td[1]/a[1]"
        self.driver.find_element(By.XPATH, addr_edit_row_xpath).click()
        self.setTelephoneInfo()
        # self.performActionClick("BatchEntryAddButtonTelephone")
        time.sleep(2)
        self.driver.find_element(By.ID, "BatchEntryAddButtonTelephone").click()
        time.sleep(3)

    def setOrganizationQuickCode(self, random_number):
        self.driver.find_element(By.XPATH, "//*[@id='test']").clear()
        self.driver.find_element(By.XPATH, "//*[@id='test']").send_keys(random_number)

    def closeDBConnection(self):
        close_db_connection()

    def setOrgComments(self):
        N = 7
        self.driver.find_element(By.ID, "organization.theOrganizationDT.description").clear()
        res = ''.join(random.choices(string.ascii_lowercase + string.digits, k=N))
        # append Date
        (self.driver.find_element(By.ID, "organization.theOrganizationDT.description")
         .send_keys("General Comments", '_', (Organization.generate_random_string(100))))

    def setOrgRole(self):
        #self.driver.find_element(By.ID, "rolesList").clear()
        element = self.driver.find_element(By.ID, "rolesList")
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        select_element = Select(self.driver.find_element(By.ID, "rolesList"))
        size = len(select_element.options) - 1
        select_element.select_by_index(random.randint(1, size))

    def setStandardIndustryClass(self):
        element = self.driver.find_element(By.ID, "organization.theOrganizationDT.standardIndustryClassCd")
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        select_element = Select(
            self.driver.find_element(By.ID, "organization.theOrganizationDT.standardIndustryClassCd"))
        size = len(select_element.options) - 1
        select_element.select_by_index(random.randint(1, size))

    def setOrganizationIdActions(self, no_of_repeats, random_number):
        for i in range(1, no_of_repeats + 1):
            if i == no_of_repeats - 2:
                identi_edit_row_xpath = "//*[@id='nestedElementsHistoryBox|Identification']/tr[".__add__(
                    str(i)) + "]/td[1]/a[1]"
                self.driver.find_element(By.XPATH, identi_edit_row_xpath).click()
                time.sleep(2)
                selected_type_cd_value = self.setIdType(random_number)
                self.setAssigningAuth(random_number)
                self.setIdValue(selected_type_cd_value, random_number)
                self.driver.find_element(By.ID, "BatchEntryAddButtonIdentification").click()
                time.sleep(1)
            if i == no_of_repeats:
                identi_delete_row_xpath = "//*[@id='nestedElementsHistoryBox|Identification']/tr[".__add__(
                    str(i)) + "]/td[1]/a[2]"
                self.driver.find_element(By.XPATH, identi_delete_row_xpath).click()
                time.sleep(1)

    def editOrganizationIdActions(self, edit_option, random_number):
        identi_edit_row_xpath = "//*[@id='nestedElementsHistoryBox|Identification']/tr[".__add__(
            str(edit_option)) + "]/td[1]/a[1]"
        self.driver.find_element(By.XPATH, identi_edit_row_xpath).click()
        time.sleep(2)
        selected_type_cd_value = self.setIdType(random_number)
        self.setAssigningAuth(random_number)
        self.setIdValue(selected_type_cd_value, random_number)
        self.driver.find_element(By.ID, "BatchEntryAddButtonIdentification").click()
        time.sleep(3)

        # set organization ID type for regular and other

    def setIdType(self, random_number):
        element = self.driver.find_element(By.ID, "organization.entityIdDT_s[i].typeCd")
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        select_element = Select(self.driver.find_element(By.ID,
                                                         "organization.entityIdDT_s[i].typeCd"))
        random_index = random.randint(2, len(select_element.options))
        add_organization_ident_type_cd_opt_xpath = (
                "//*[@id='organization.entityIdDT_s[i].typeCd']/option["
                .__add__(str(random_index)) + "]")
        identi_type_value = (self.driver.find_element(By.XPATH, add_organization_ident_type_cd_opt_xpath)
                             .get_attribute("value"))
        select_element.select_by_value(identi_type_value)
        if identi_type_value == "OTH":
            element = self.driver.find_element(By.ID, "nestedElementsControllerPayloadorganization.entityIdDT_s["
                                                      "i].typeCdc")
            self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
            self.driver.find_element(By.ID, "organization.entityIdDT_s[i].typeDescTxt").clear()
            self.driver.find_element(By.ID, "organization.entityIdDT_s[i].typeDescTxt"
                                     ).send_keys(identi_type_value + "-" + random_number)
        else:
            element = self.driver.find_element(By.ID, "nestedElementsControllerPayloadorganization.entityIdDT_s["
                                                      "i].typeCdc")
            self.driver.execute_script("arguments[0].setAttribute('class','none')", element)

        time.sleep(1)
        return identi_type_value

    # assigning auth multiple rows

    def setAssigningAuth(self, random_number):
        #self.driver.find_element(By.ID, "organization.entityIdDT_s[i].assigningAuthorityCd").clear()
        element = self.driver.find_element(By.ID, "organization.entityIdDT_s[i].assigningAuthorityCd")
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        select_element = Select(self.driver.find_element(By.ID, "organization.entityIdDT_s[i].assigningAuthorityCd"))
        size = len(select_element.options) - 1
        select_element.select_by_index(random.randint(1, size))

        element = self.driver.find_element(By.ID, "organization.entityIdDT_s[i].assigningAuthorityCd")
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        select_element = Select(self.driver.find_element(By.ID,
                                                         "organization.entityIdDT_s[i].assigningAuthorityCd"))
        random_index = random.randint(2, len(select_element.options))
        add_org_id_type_cd_opt_xpath = (
                "//*[@id='organization.entityIdDT_s[i].assigningAuthorityCd']/option["
                .__add__(str(random_index)) + "]")

        identi_assign_value = (self.driver.find_element(By.XPATH, add_org_id_type_cd_opt_xpath)
                               .get_attribute("value"))
        select_element.select_by_value(identi_assign_value)
        if identi_assign_value == "OTH":
            element = self.driver.find_element(By.ID,
                                               "nestedElementsControllerPayloadorganization.entityIdDT_s["
                                               "i].assigningAuthorityCd")
            self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
            self.driver.find_element(By.ID, "organization.entityIdDT_s[i].assigningAuthorityDescTxt").clear()
            self.driver.find_element(By.ID, "organization.entityIdDT_s[i].assigningAuthorityDescTxt"
                                     ).send_keys(identi_assign_value + "-" + random_number)
        else:
            element = self.driver.find_element(By.ID,
                                               "nestedElementsControllerPayloadorganization.entityIdDT_s["
                                               "i].assigningAuthorityCd")
            self.driver.execute_script("arguments[0].setAttribute('class','none')", element)

    def setIdValue(self, selected_type_cd_value, random_number):
        self.driver.find_element(By.ID, "organization.entityIdDT_s[i].rootExtensionTxt").clear()
        self.driver.find_element(By.ID, "organization.entityIdDT_s[i].rootExtensionTxt").send_keys(
            selected_type_cd_value + "-" + random_number)

    def addIdRowToOrganization(self):
        self.driver.find_element(By.ID, "BatchEntryAddButtonIdentification").click()

    def setAddressUse(self):
        self.setOrgElementVisible("address[i].useCd")
        select_element = Select(self.driver.find_element(By.ID,
                                                         "address[i].useCd"))
        random_index = random.randint(2, len(select_element.options))

        add_org_addr_type_use_xpath = (
                "//*[@id='address[i].useCd']/option["
                .__add__(str(random_index)) + "]")
        addr_type_use_value = (self.driver.find_element(By.XPATH, add_org_addr_type_use_xpath)
                               .get_attribute("value"))
        if self.driver.find_element(By.ID, "address[i].useCd").is_displayed():
            select_element.select_by_value(addr_type_use_value)
        else:
            self.setOrgElementVisible("address[i].useCd")
            select_element.select_by_value(addr_type_use_value)

        self.setOrgElementVisible("address[i].useCd")

    def setType(self):
        element = self.driver.find_element(By.ID, "address[i].cd")
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        select_element = Select(self.driver.find_element(By.ID,
                                                         "address[i].cd"))
        random_index = random.randint(2, len(select_element.options))
        add_org_addr_type_xpath = (
                "//*[@id='address[i].cd']/option["
                .__add__(str(random_index)) + "]")
        addr_type_value = (self.driver.find_element(By.XPATH, add_org_addr_type_xpath)
                           .get_attribute("value"))
        select_element.select_by_value(addr_type_value)

    def setStreetAddress1(self, random_value):
        self.driver.find_element(By.ID, "address[i].thePostalLocatorDT_s.streetAddr1").clear()
        self.driver.find_element(By.ID, "address[i].thePostalLocatorDT_s.streetAddr1"
                                 ).send_keys("address1-" + random_value)

    def setStreetAddress2(self, random_value):
        self.driver.find_element(By.ID, "address[i].thePostalLocatorDT_s.streetAddr2").clear()
        self.driver.find_element(By.ID, "address[i].thePostalLocatorDT_s.streetAddr2"
                                 ).send_keys("address2-" + random_value)

    def setCity(self):
        self.driver.find_element(By.ID, "address[i].thePostalLocatorDT_s.cityDescTxt").clear()
        self.driver.find_element(By.ID, "address[i].thePostalLocatorDT_s.cityDescTxt"). \
            send_keys("PostalAddress", "_" + Organization.generate_random_string(5))

    def setState(self):
        element = self.driver.find_element(By.ID, "address[i].thePostalLocatorDT_s.stateCd")
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        select_element = Select(self.driver.find_element(By.ID,
                                                         "address[i].thePostalLocatorDT_s.stateCd"))
        random_index = random.randint(2, len(select_element.options))
        add_org_addr_state_xpath = (
                "//*[@id='address[i].thePostalLocatorDT_s.stateCd']/option["
                .__add__(str(random_index)) + "]")
        addr_state_value = (self.driver.find_element(By.XPATH, add_org_addr_state_xpath)
                            .get_attribute("value"))
        select_element.select_by_value(addr_state_value)

    def setZipcode(self):
        fixed_digits = 5
        random_number = random.randrange(11111, 99999, fixed_digits)
        self.driver.find_element(By.ID, "address[i].thePostalLocatorDT_s.zipCd").clear()
        self.driver.find_element(By.ID, "address[i].thePostalLocatorDT_s.zipCd").send_keys(random_number)

    def setCounty(self):
        element = self.driver.find_element(By.ID, "address[i].thePostalLocatorDT_s.cntyCd")
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        select_element = Select(self.driver.find_element(By.ID,
                                                         "address[i].thePostalLocatorDT_s.cntyCd"))
        random_index = random.randint(2, len(select_element.options))
        add_org_addr_county_xpath = (
                "//*[@id='address[i].thePostalLocatorDT_s.cntyCd']/option[".__add__(str(random_index)) + "]")
        addr_state_value = (self.driver.find_element(By.XPATH, add_org_addr_county_xpath)
                            .get_attribute("value"))
        select_element.select_by_value(addr_state_value)

    def setAddressComments(self):
        self.driver.find_element(By.NAME, "address[i].locatorDescTxt").clear()
        self.driver.find_element(By.NAME, "address[i].locatorDescTxt").send_keys("AddressComments", '_', (
            Organization.generate_random_string(10)))

    """def addAddressRowToOrg(self):
        self.driver.find_element(By.ID, "BatchEntryAddButtonAddress").click()"""

    def setAddUse(self):
        self.setOrgSelectionDropdown("telephone[i].useCd", "//*[@id='telephone[i].useCd']")

    def setOrgSelectionDropdown(self, element1, element2):
        element = self.driver.find_element(By.ID, element1)
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
        select_element = Select(self.driver.find_element(By.ID, element1))
        random_index = random.randint(2, len(select_element.options))
        add_org_element2_xpath = (
            element2.__add__("/option[".__add__(str(random_index)) + "]"))
        org_element2_value = (self.driver.find_element(By.XPATH, add_org_element2_xpath)
                              .get_attribute("value"))
        select_element.select_by_value(org_element2_value)

    def setTypeId(self):
        self.setOrgSelectionDropdown("telephone[i].cd", "//*[@id='telephone[i].cd']")

    def addOrganizationToSystem(self):
        self.performActionClick("Submit")

    def setOrgElementVisible(self, element):
        element = self.driver.find_element(By.ID, element)
        self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)

    @staticmethod
    def generate_random_string(s_length):
        letters = string.ascii_lowercase
        # Randomly choose characters from letters for the given length of the string
        random_string = ''.join(random.choice(letters) for i in range(s_length))
        return random_string

    def setOrgTextBoxValue(self, element, value):
        self.driver.find_element(By.ID, element).clear()
        self.driver.find_element(By.ID, element).send_keys(value)

    def performSearchByElement(self, element, random_number, organization_uid):
        return_val = True
        if element == NBSConstants.ADD_ORG_LAST_NAME:
            self.setOrgLastName(str(random_number))
        time.sleep(2)
        self.clickOrgSubmitButton()
        try:
            next_elem = self.driver.find_element(By.LINK_TEXT, 'Next')
        except NoSuchElementException:
            next_elem = None
        if next_elem is not None:
            elem1 = self.findElementInMultiplePages(organization_uid)
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
                                                       "//a[contains(@href, 'uid=".__add__(str(organization_uid)) +
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
                # added exception to handle the error when no org available and returning False
                try:
                    self.driver.find_element(By.LINK_TEXT, 'View').click()
                except Exception as e:
                    return_val = False
                time.sleep(2)
                self.driver.find_element(By.LINK_TEXT, 'New Search').click()
            time.sleep(2)
        return return_val

    def clickOrgSubmitButton(self):
        self.driver.find_element(By.ID, self.org_search_submit_id).click()

    def findElementInMultiplePages(self, organization_uid):
        element_find = None
        while True:
            try:
                element_find = self.driver.find_element(By.XPATH,
                                                        "//a[contains(@href, 'uid=".__add__(
                                                            str(organization_uid)) + "')]")
                if element_find is not None:
                    break
            except NoSuchElementException:
                try:
                    self.driver.find_element(By.LINK_TEXT, 'Next').click()
                except NoSuchElementException:
                    element_find = None
        return element_find

    def performSearchByElementAndExit(self, element, random_number):
        if element == NBSConstants.ADD_ORG_LAST_NAME:
            self.setOrgLastName(str(random_number))
        time.sleep(2)
        self.clickOrgSubmitButton()
        time.sleep(2)
        return True

    def performSearchByInactiveElement(self, element, inactive_org, organization_uid):
        if element == NBSConstants.ADD_ORG_LAST_NAME_WILD:
            self.setOrgLastName(inactive_org)
        time.sleep(2)
        self.clickOrgSubmitButton()
        try:
            next_elem = self.driver.find_element(By.LINK_TEXT, 'Next')
        except NoSuchElementException:
            next_elem = None
        if next_elem is not None:
            elem1 = self.findElementInMultiplePages(organization_uid)
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
                                                       "//a[contains(@href, 'uid=".__add__(str(organization_uid)) +
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

    def performSearchByLastname(self, element, random_number, organization_uid):
        if element == NBSConstants.ADD_ORG_LAST_NAME_WILD:
            self.setOrgLastName("lastname")
        time.sleep(2)
        self.clickOrgSubmitButton()
        try:
            next_elem = self.driver.find_element(By.LINK_TEXT, 'Next')
        except NoSuchElementException:
            next_elem = None
        if next_elem is not None:
            elem1 = self.findElementInMultiplePages(organization_uid)
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
                                                       "//a[contains(@href, 'uid=".__add__(str(organization_uid)) +
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
                self.driver.find_element(By.LINK_TEXT, 'View').click()
                # time.sleep(2)
                # self.driver.find_element(By.LINK_TEXT, 'New Search').click()
            time.sleep(2)
        return True
