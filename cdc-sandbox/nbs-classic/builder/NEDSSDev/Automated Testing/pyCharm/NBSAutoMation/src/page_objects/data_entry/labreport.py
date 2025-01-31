from PIL.ImageOps import pad
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.support.ui import WebDriverWait
from src.utilities.constants import NBSConstants
from src.utilities.db_utilities import DBUtilities
from src.utilities.log_config import LogUtils
from src.utilities.nbs_utilities import NBSUtilities
from src.page_objects.BasePageObject import BasePageObject
import random


class LabReportObject(BasePageObject):
    logger = LogUtils.loggen(__name__)
    facility_checked = False
    db_utilities = DBUtilities()

    # Lab Report
    nbs_data_entry_lab_report_menu = "Lab Report"
    nbs_de_lr_add_org_name = "//*[@id='subSec1']/tbody/tr[2]/td[2]/input"
    add_org_submit_button = "/html/body/div[5]/input[1]"
    identification_section = "Identification"
    add_patient_current_sex = "//*[@id='DEM113']"
    is_patient_deceased = "//*[@id='DEM127']"
    patient_martial_status = "#DEM140"
    patient_search_page_title = "NBS:Find Patient"
    patient_search_by_last_name = "personSearch.lastName"
    patient_search_submit_id = "Submit"
    nbs_data_entry_patient_menu = "Patient"

    def __init__(self, driver):
        super().__init__(driver)
        self.driver = driver

    def addLabReportForPatient(self, random_number, patient_uid):
        self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                "//*[@id='NBS_UI_27']/tbody/tr/td/input[1]")
        if self.searchPatientFromLabReport(random_number, patient_uid):
            # Lab Report Tab
            self.performButtonClick(NBSConstants.TYPE_CODE_ID,
                                    "tabs0head1")
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
            # Facility and Provider Information
            self.setFacilityAndProviderSection(random_number)
            # Order Details For Lab
            self.setOrderDetailsForLab()
            # Test Results For Lab
            # set Ordered Test
            self.setOrderedTestDetails(random_number)
            # set Resulted Test
            self.setResultedTestDetails()
            # Lab Report Comments

            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                        NBSConstants.TYPE_CODE_NAME,
                                        "pageClientVO.answer(NBS460)",
                                        "Comments:".__add__(NBSUtilities.generate_random_string(60)))
            # Other Information
            # Retain Patient For Next Entry
            self.performButtonClick(NBSConstants.TYPE_CODE_NAME,
                                    "pageClientVO.answer(NBS_LAB223)")
            # Retain Reporting Facility For Next Entry
            self.performButtonClick(NBSConstants.TYPE_CODE_NAME,
                                    "pageClientVO.answer(NBS_LAB224)")
            # before click submit, save same reporting facility to variable
            LabReportObject.facility_checked = self.findElement(NBSConstants.TYPE_CODE_NAME,
                                                                "pageClientVO.answer(NBS_LAB267)").is_selected()
            # Create Lab Report by clicking Submit
            self.performButtonClick(NBSConstants.TYPE_CODE_ID, "SubmitBottom")

            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, 2)

        return True

    def createPatientAndLabReport(self, random_number):
        # populate patient tab
        self.populatePatientTab(random_number)
        # click next to Lab Report Tab
        self.performButtonClick(NBSConstants.TYPE_CODE_XPATH, "//*[@id='editPatient']/div[2]/a[2]")
        # Facility and Provider Information
        self.setFacilityAndProviderSection(random_number)
        # Order Details For Lab
        self.setOrderDetailsForLab()
        # Test Results For Lab
        # set Ordered Test
        self.setOrderedTestDetails(random_number)
        # set Resulted Test
        self.setResultedTestDetails()
        # Lab Report Comments

        self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                    NBSConstants.TYPE_CODE_NAME,
                                    "pageClientVO.answer(NBS460)",
                                    "Comments:".__add__(NBSUtilities.generate_random_string(60)))
        # Other Information
        # Retain Patient For Next Entry
        self.performButtonClick(NBSConstants.TYPE_CODE_NAME,
                                "pageClientVO.answer(NBS_LAB223)")
        # Retain Reporting Facility For Next Entry
        self.performButtonClick(NBSConstants.TYPE_CODE_NAME,
                                "pageClientVO.answer(NBS_LAB224)")
        # Create Lab Report by clicking Submit
        self.performButtonClick(NBSConstants.TYPE_CODE_ID, "SubmitBottom")
        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
        return True

    def populatePatientTab(self, random_number):
        # General Information Section
        # Information As Of Date (Make it as current Date)
        self.selectCalendarDate(NBSConstants.TYPE_CODE_ID, "NBS104Icon")
        # comments
        self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                    NBSConstants.TYPE_CODE_ID,
                                    "DEM196",
                                    "Comments:".__add__(NBSUtilities.generate_random_string(100)))

        # Name Information
        # first name
        self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                    NBSConstants.TYPE_CODE_ID,
                                    "DEM104",
                                    NBSConstants.APPEND_TEST_FOR_FIRST_NAME
                                    .__add__("-").__add__(str(random_number)))
        # middle name
        self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                    NBSConstants.TYPE_CODE_ID,
                                    "DEM105",
                                    NBSConstants.APPEND_TEST_FOR_MIDDLE_NAME
                                    .__add__("-").__add__(str(random_number)))

        # last name
        self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                    NBSConstants.TYPE_CODE_ID,
                                    "DEM102",
                                    NBSConstants.APPEND_TEST_FOR_LAST_NAME
                                    .__add__("-").__add__(str(random_number)))
        # suffix
        self.setValueForHTMLElement(NBSConstants.HTML_SET_DROP_DOWN_RANDOM_VALUE,
                                    NBSConstants.TYPE_CODE_ID,
                                    "DEM107",
                                    None)
        # Other Personal Details
        # DOB
        selected_birth_day, selected_birth_month, selected_birth_year = NBSUtilities.getRandomDateByVars()
        self.selectCalendarDate(NBSConstants.TYPE_CODE_ID,
                                "DEM115Icon",
                                str(int(selected_birth_day)),
                                selected_birth_month,
                                str(int(selected_birth_year)))
        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)

        # current sex

        self.setDropdownValueRandom(NBSConstants.TYPE_CODE_XPATH,
                                    self.add_patient_current_sex)

        # is patient deceased

        self.setDropdownValueRandom(NBSConstants.TYPE_CODE_XPATH,
                                    self.is_patient_deceased)

        # is patient deceased yes, then populate deceased date
        deceased_date = self.findElement(NBSConstants.TYPE_CODE_XPATH,
                                         self.is_patient_deceased)
        if deceased_date.get_attribute("value") == "Y":
            self.selectCalendarDate(NBSConstants.TYPE_CODE_ID,
                                    "DEM128Icon")
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
        # martial status
        self.setValueForHTMLElement(NBSConstants.HTML_SET_DROP_DOWN_RANDOM_VALUE,
                                    NBSConstants.TYPE_CODE_CSS,
                                    self.patient_martial_status,
                                    None)
        # populate ssn

        self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                    NBSConstants.TYPE_CODE_ID,
                                    "DEM133",
                                    NBSUtilities.generate_random_int(9))
        # Entity Section

        # Add Records To Identification Information Section
        self.setRepeatableSections(self.identification_section, str(random_number))
        # Perform Actions (Edit/Delete) To Identification Information Section
        self.setRepeatableSectionActions(self.identification_section, str(random_number))

        # Reporting Address and Case Counting
        # Street Address1
        self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                    NBSConstants.TYPE_CODE_ID,
                                    "DEM159",
                                    NBSConstants.APPEND_TEST_FOR_STREET_ADDR1
                                    .__add__("-").__add__(str(random_number)))
        # Street Address2
        self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                    NBSConstants.TYPE_CODE_ID,
                                    "DEM160",
                                    NBSConstants.APPEND_TEST_FOR_STREET_ADDR2
                                    .__add__("-").__add__(str(random_number)))

        # city
        self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                    NBSConstants.TYPE_CODE_ID,
                                    "DEM161",
                                    NBSConstants.APPEND_TEST_FOR_STREET_CITY
                                    .__add__("-").__add__(str(random_number)))

        # state
        self.setValueForHTMLElement(NBSConstants.HTML_SET_DROP_DOWN_RANDOM_VALUE,
                                    NBSConstants.TYPE_CODE_ID,
                                    "DEM162",
                                    None)
        # postal

        self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                    NBSConstants.TYPE_CODE_ID,
                                    "DEM163",
                                    NBSUtilities.generate_random_int(5))

        # county
        """self.setValueForHTMLElement(NBSConstants.HTML_SET_DROP_DOWN_RANDOM_VALUE,
                                    NBSConstants.TYPE_CODE_ID,
                                    "DEM165",
                                    None)"""
        self.setDropdownValueRandom(NBSConstants.TYPE_CODE_XPATH, "//*[@id='DEM165']")

        # country
        self.setValueForHTMLElement(NBSConstants.HTML_SET_DROP_DOWN_RANDOM_VALUE,
                                    NBSConstants.TYPE_CODE_ID,
                                    "DEM167",
                                    None)
        # Telephone Information
        # home phone
        home_tele_phone = (NBSUtilities.generate_random_int(3).__add__("-").__add__(NBSUtilities.generate_random_int(3))
                           .__add__("-").__add__(NBSUtilities.generate_random_int(4)))
        self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                    NBSConstants.TYPE_CODE_ID,
                                    "DEM177",
                                    home_tele_phone)
        work_tele_phone = (NBSUtilities.generate_random_int(3).__add__("-").__add__(NBSUtilities.generate_random_int(3))
                           .__add__("-").__add__(NBSUtilities.generate_random_int(4)))
        self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                    NBSConstants.TYPE_CODE_ID,
                                    "NBS002",
                                    work_tele_phone)

        # Extension
        self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                    NBSConstants.TYPE_CODE_ID,
                                    "NBS003",
                                    NBSUtilities.generate_random_int(4))

        # cell phone
        mobile_phone = (NBSUtilities.generate_random_int(3).__add__("-").__add__(NBSUtilities.generate_random_int(3))
                        .__add__("-").__add__(NBSUtilities.generate_random_int(4)))

        self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                    NBSConstants.TYPE_CODE_ID,
                                    "NBS006",
                                    mobile_phone)
        # Email
        self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                    NBSConstants.TYPE_CODE_ID,
                                    "DEM182",
                                    NBSUtilities.generate_random_string(6).__add__("@uuu.com")
                                    )
        # Ethnicity and Race Information
        # Ethnicity
        self.setValueForHTMLElement(NBSConstants.HTML_SET_DROP_DOWN_RANDOM_VALUE,
                                    NBSConstants.TYPE_CODE_ID,
                                    "DEM155",
                                    None)
        # Alaskan Race
        self.performButtonClick(NBSConstants.TYPE_CODE_NAME, "pageClientVO.americanIndianAlskanRace")
        # Asian Race
        self.performButtonClick(NBSConstants.TYPE_CODE_NAME, "pageClientVO.asianRace")
        # African Race
        self.performButtonClick(NBSConstants.TYPE_CODE_NAME, "pageClientVO.africanAmericanRace")
        # Hawaiian Race
        self.performButtonClick(NBSConstants.TYPE_CODE_NAME, "pageClientVO.hawaiianRace")
        # White Race
        self.performButtonClick(NBSConstants.TYPE_CODE_NAME, "pageClientVO.whiteRace")
        # Other Race
        self.performButtonClick(NBSConstants.TYPE_CODE_NAME, "pageClientVO.otherRace")
        # Refused To Answer
        self.performButtonClick(NBSConstants.TYPE_CODE_NAME, "pageClientVO.refusedToAnswer")
        # Not Asked
        self.performButtonClick(NBSConstants.TYPE_CODE_NAME, "pageClientVO.notAsked")
        # Unknown Race
        self.performButtonClick(NBSConstants.TYPE_CODE_NAME, "pageClientVO.unKnownRace")
        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                        NBSConstants.WAITING_TIME_IN_SECONDS)

    def searchPatientFromLabReport(self, random_number, patient_uid):
        # get patient id
        patient_id = self.getPatienetIdFromSystem(patient_uid)
        original_window = self.driver.current_window_handle
        WebDriverWait(self.driver, 10).until(EC.number_of_windows_to_be(2))
        for window_handle in self.driver.window_handles:
            if window_handle != original_window:
                self.driver.switch_to.window(window_handle)
                self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT, NBSConstants.TYPE_CODE_NAME,
                                            "personSearch.lastName",
                                            NBSConstants.APPEND_TEST_FOR_LAST_NAME
                                            .__add__("-").__add__(str(random_number)))
                self.performButtonClick(NBSConstants.TYPE_CODE_NAME, "Submit")
                # self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                #                         '"//a[contains(@href,"{}")]"'.format(patient_id))
                self.performButtonClick(NBSConstants.TYPE_CODE_LINK_TEXT,str(patient_id))

                self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                NBSConstants.WAITING_TIME_IN_SECONDS)
                break
        self.driver.switch_to.window(original_window)
        return True

    def getOrganizationDetails(self, random_number, path_id=None):
        if path_id is not None:
            org_name = "Org-".__add__(str(random_number))
            org_uid = self.db_utilities.getSingleColumnValueByTable("organization_uid", "Organization",
                                                                    "display_nm", org_name)
            self.performButtonClick(NBSConstants.TYPE_CODE_ID, path_id)
            # if organization is existed, pick from results screen,
            # otherwise create new one by using Add Button
            original_window = self.driver.current_window_handle
            try:
                WebDriverWait(self.driver, 10).until(EC.number_of_windows_to_be(2))
                for window_handle in self.driver.window_handles:
                    if window_handle != original_window:
                        self.driver.switch_to.window(window_handle)
                        # Search Screen
                        self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                                    NBSConstants.TYPE_CODE_ID,
                                                    "lastNameTxt",
                                                    "Org".__add__("-").__add__(str(random_number)))
                        self.performButtonClick(NBSConstants.TYPE_CODE_NAME, "Submit")

                        # Results Screen
                        elem = self.findElementInMultiplePagesByText(str(org_uid))
                        if elem is not None:
                            elem.click()
                        else:
                            # Create Organization from Add Organization Screen
                            self.createNewOrganization(random_number)
                        break
                self.driver.switch_to.window(original_window)
            except Exception as ex:
                self.logger.debug("Exception while getting organization details:::", str(ex))
                self.driver.switch_to.window(original_window)
                pass

            return True

    def getProviderDetails(self, random_number, path_id=None):
        # if provider is existed, pick from results screen,
        # otherwise create new one by using Add Button
        if path_id is not None:
            last_name = "Prov-".__add__(str(random_number))
            provider_uid = self.db_utilities.getSingleColumnValueByTable("person_uid", "person",
                                                                         "last_nm", last_name)
            self.performButtonClick(NBSConstants.TYPE_CODE_ID, path_id)
            original_window = self.driver.current_window_handle
            try:
                WebDriverWait(self.driver, 10).until(EC.number_of_windows_to_be(2))
                for window_handle in self.driver.window_handles:
                    if window_handle != original_window:
                        self.driver.switch_to.window(window_handle)
                        # Search Screen
                        self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                                    NBSConstants.TYPE_CODE_ID,
                                                    "lastNameTxt",
                                                    "Prov-".__add__(str(random_number)))
                        self.performButtonClick(NBSConstants.TYPE_CODE_NAME, "Submit")

                        # Results Screen
                        elem = self.findElementInMultiplePagesByText(str(provider_uid))
                        if elem is not None:
                            elem.click()
                        else:
                            # Create Provider from Add Organization Screen
                            self.createNewProvider(random_number)
                        break
                self.driver.switch_to.window(original_window)
            except Exception as ex:
                self.logger.debug("Exception while getting provider details:::", str(ex))
                self.driver.switch_to.window(original_window)
                pass
            return True

    def getTestSearchDetails(self, path_id, search_type=None):
        if path_id is not None:
            self.performButtonClick(NBSConstants.TYPE_CODE_ID, path_id)
            original_window = self.driver.current_window_handle
            try:
                WebDriverWait(self.driver, 10).until(EC.number_of_windows_to_be(2))
                for window_handle in self.driver.window_handles:
                    if window_handle != original_window:
                        self.driver.switch_to.window(window_handle)
                        # Search Screen
                        if search_type is not None and search_type == "LOINC-S":
                            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                                        NBSConstants.TYPE_CODE_ID,
                                                        "labTest",
                                                        "T-50095")
                        elif search_type is not None and search_type == "CODED":
                            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                                        NBSConstants.TYPE_CODE_ID,
                                                        "labTest",
                                                        "Abnormal")
                        else:
                            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                                        NBSConstants.TYPE_CODE_ID,
                                                        "labTest",
                                                        "Test")

                        if search_type is not None and search_type != "LOINC-S":
                            ele = self.findElements(NBSConstants.TYPE_CODE_ID, "searchList")
                            if ele is not None and len(ele) > 1:
                                ele[1].click()
                        # keep default radio button as it is (SHORT) if search_type is none and click submit
                        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                        NBSConstants.WAITING_TIME_IN_SECONDS)
                        self.performButtonClick(NBSConstants.TYPE_CODE_ID, "Submit")
                        ele = self.findElement(NBSConstants.TYPE_CODE_XPATH, "//*[@id='parent']/tbody/tr[1]/td[1]/a")
                        if ele is not None:
                            ele.click()
                        break
                self.driver.switch_to.window(original_window)
            except Exception as ex:
                self.logger.debug("Exception while getting test result code:::%s", str(ex))
                self.driver.switch_to.window(original_window)
                pass
            return True

    def setFacilityAndProviderSection(self, random_number):
        #  Reporting Facility

        # Quick Code Search By Number
        self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                    NBSConstants.TYPE_CODE_ID,
                                    "NBS_LAB365Text",
                                    "2")
        # Quick Code Search Button
        self.performButtonClick(NBSConstants.TYPE_CODE_ID, "NBS_LAB365CodeLookupButton")
        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
        # clear selected reporting facility
        self.performButtonClick(NBSConstants.TYPE_CODE_ID, "NBS_LAB365CodeClearButton")

        # Quick Code Search By Search Button
        self.getOrganizationDetails(random_number, path_id="NBS_LAB365Icon")

        # ************ Ordering Facility *************
        # Quick Code Search By Number
        self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                    NBSConstants.TYPE_CODE_ID,
                                    "NBS_LAB367Text",
                                    "2")
        # Quick Code Search Button
        self.performButtonClick(NBSConstants.TYPE_CODE_ID, "NBS_LAB367CodeLookupButton")
        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
        # clear selected ordering facility
        self.performButtonClick(NBSConstants.TYPE_CODE_ID, "NBS_LAB367CodeClearButton")
        # Quick Code Search By Search Button
        self.getOrganizationDetails(random_number, path_id="NBS_LAB367Icon")
        # same as reporting facility check box
        self.performButtonClick(NBSConstants.TYPE_CODE_NAME, "pageClientVO.answer(NBS_LAB267)")
        # ************ Ordering Provider *************
        # Quick Code Search By Number
        self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                    NBSConstants.TYPE_CODE_ID,
                                    "NBS_LAB366Text",
                                    "2")
        # Quick Code Search Button
        self.performButtonClick(NBSConstants.TYPE_CODE_ID, "NBS_LAB366CodeLookupButton")
        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
        # clear selected ordering facility
        self.performButtonClick(NBSConstants.TYPE_CODE_ID, "NBS_LAB366CodeClearButton")
        # Quick Code Search By Search Button
        self.getProviderDetails(random_number, path_id="NBS_LAB366Icon")
        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)

    def setOrderDetailsForLab(self):
        # Program Area
        self.setValueForHTMLElement(NBSConstants.HTML_SET_DROP_DOWN_RANDOM_VALUE,
                                    NBSConstants.TYPE_CODE_ID,
                                    "INV108",
                                    None)
        # Jurisdiction
        self.setValueForHTMLElement(NBSConstants.HTML_SET_DROP_DOWN_RANDOM_VALUE,
                                    NBSConstants.TYPE_CODE_ID,
                                    "INV107",
                                    None)
        # shared indicator
        element = self.findElement(NBSConstants.TYPE_CODE_NAME, "pageClientVO.answer(NBS012)")
        if not element.is_selected():
            self.performButtonClick(NBSConstants.TYPE_CODE_NAME, "pageClientVO.answer(NBS012)")

        # Lab Report Date
        selected_birth_day, selected_birth_month, selected_birth_year = NBSUtilities.getRandomDateByVars()
        self.selectCalendarDate(NBSConstants.TYPE_CODE_ID,
                                "NBS_LAB197Icon",
                                str(int(selected_birth_day)),
                                selected_birth_month,
                                str(int(selected_birth_year)))
        # Date Received By Public Health, set as current date

        self.selectCalendarDate(NBSConstants.TYPE_CODE_ID,
                                "NBS_LAB201Icon")
        # Pregnancy Status

        element = self.findElement(NBSConstants.TYPE_CODE_ID, "INV178")
        if element.is_enabled():
            """self.setValueForHTMLElement(NBSConstants.HTML_SET_DROP_DOWN_RANDOM_VALUE,
                                        NBSConstants.TYPE_CODE_ID,
                                        "INV178",
                                        None)"""
            self.setDropdownValueRandom(NBSConstants.TYPE_CODE_XPATH, "//*[@id='INV178']")
            if element.get_attribute("value") == "Y":
                # Pregnancy weeks
                self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                            NBSConstants.TYPE_CODE_ID,
                                            "NBS128",
                                            "9")

    def setOrderedTestDetails(self, random_number, edit_lab=None):
        # Test Results - Ordered Test
        if edit_lab is not None and edit_lab != "N":
            # clear the dropdown with clear button
            self.performButtonClick(NBSConstants.TYPE_CODE_ID, "NBS_LAB112ClearButton")
            # select value from dropdown
            self.setValueForHTMLElement(NBSConstants.HTML_SET_DROP_DOWN_RANDOM_VALUE,
                                        NBSConstants.TYPE_CODE_ID,
                                        "NBS_LAB112",
                                        None)
        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                        NBSConstants.WAITING_TIME_IN_SECONDS)
        # clear the dropdown with clear button
        self.performButtonClick(NBSConstants.TYPE_CODE_ID, "NBS_LAB112ClearButton")
        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
        # select value by Search Button with shortlist
        self.getTestSearchDetails(path_id="NBS_LAB112Search")
        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
        # Access Number
        self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                    NBSConstants.TYPE_CODE_ID,
                                    "LAB125",
                                    "AN-".__add__(str(random_number)))
        # Specimen Source
        self.setValueForHTMLElement(NBSConstants.HTML_SET_DROP_DOWN_RANDOM_VALUE,
                                    NBSConstants.TYPE_CODE_ID,
                                    "LAB165",
                                    None)

        # Specimen Site
        self.setValueForHTMLElement(NBSConstants.HTML_SET_DROP_DOWN_RANDOM_VALUE,
                                    NBSConstants.TYPE_CODE_ID,
                                    "NBS_LAB166",
                                    None)
        # Specimen Collection Date
        if self.checkDateIsExisted(NBSConstants.TYPE_CODE_ID, "LAB163"):
            specimen_col_day, specimen_col_month, specimen_col_year = NBSUtilities.getRandomDateByVars()
            self.selectCalendarDate(NBSConstants.TYPE_CODE_ID,
                                    "LAB163Icon",
                                    str(int(specimen_col_day)),
                                    specimen_col_month,
                                    str(int(specimen_col_year)))

        # Patient Status at Specimen Collection time
        self.setValueForHTMLElement(NBSConstants.HTML_SET_DROP_DOWN_RANDOM_VALUE,
                                    NBSConstants.TYPE_CODE_ID,
                                    "NBS_LAB330",
                                    None)

        if edit_lab == "Y":
            # Security Testing for Lab Defect
            self.setValueForHTMLElement(NBSConstants.HTML_SET_DROP_DOWN_RANDOM_VALUE,
                                        NBSConstants.TYPE_CODE_ID,
                                        "GA12000",
                                        None)

            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                        NBSConstants.TYPE_CODE_ID,
                                        "NBS_LAB364",
                                        "NMR-".__add__(str(random_number)))

    def setResultedTestDetails(self, edit_mode=None):
        try:
            # Test Results - Resulted Test
            # Add Records to Resulted Test
            for i in range(1, NBSConstants.ADD_NUMBER_OF_REPEATABLE_SECTIONS + 1):
                if i == 2:
                    self.populateResultedTestRecord(edit_mode, suscept=False)
                else:
                    self.populateResultedTestRecord(edit_mode)
                self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
            # Perform Actions On Result Test Table
            # display first record details
            # self.performButtonClick(NBSConstants.TYPE_CODE_ID, "viewRESULTED_TEST_CONTAINER1")
            self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                    "//*[@id='questionbodyRESULTED_TEST_CONTAINER']/tr[1]/td[2]/input")

            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
            # Edit first record in the Resulted Test Table
            self.editResultedTestRecord(edit_mode)
            # delete second record in the Resulted Test Table
            self.deleteResultedTestRecord()
        except:
            self.logger.exception(msg="exception while setting resulted test details record")

    def deleteResultedTestRecord(self):
        try:
            self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                    "//*[@id='questionbodyRESULTED_TEST_CONTAINER']/tr[2]/td[4]/input")
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)

            WebDriverWait(self.driver, 10).until(EC.alert_is_present())
            alert = self.driver.switch_to.alert
            alert.accept()
        except:
            self.logger.exception(msg="exception while deleting resulted test record")

    def editResultedTestRecord(self, edit_mode):
        self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                "//*[@id='questionbodyRESULTED_TEST_CONTAINER']/tr[1]/td[3]/input")
        self.populateResultedTestRecord(edit_mode)
        element = self.findElement(NBSConstants.TYPE_CODE_XPATH,
                                   "//*[@id='UpdateButtonToggleRESULTED_TEST_CONTAINER']/td/input")
        self.driver.execute_script("arguments[0].click();", element)

    def populateResultedTestRecord(self, edit_mode, suscept=True):
        if edit_mode is not None and edit_mode != "N":
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
            self.performButtonClick(NBSConstants.TYPE_CODE_ID, "NBS_LAB220ClearButton")
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
            # select value from dropdown
            self.setValueForHTMLElement(NBSConstants.HTML_SET_DROP_DOWN_RANDOM_VALUE,
                                        NBSConstants.TYPE_CODE_ID,
                                        "NBS_LAB220",
                                        None)
        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                        NBSConstants.WAITING_TIME_IN_SECONDS)
        # clear the dropdown with cleat button
        self.performButtonClick(NBSConstants.TYPE_CODE_ID, "NBS_LAB220ClearButton")
        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                        NBSConstants.WAITING_TIME_IN_SECONDS)
        # select value by Search Button with shortlist
        if suscept:
            self.getTestSearchDetails(path_id="NBS_LAB220Search", search_type="LOINC-S")
        else:
            self.getTestSearchDetails(path_id="NBS_LAB220Search", search_type="LOINC")

        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                        NBSConstants.WAITING_TIME_IN_SECONDS)

        # coded result
        clear_coded_result = self.findElement(NBSConstants.TYPE_CODE_ID,
                                              "NBS_LAB280ClearButton")
        if clear_coded_result is not None and clear_coded_result.is_displayed():
            self.performButtonClick(NBSConstants.TYPE_CODE_ID, "NBS_LAB280ClearButton")
            self.getTestSearchDetails(path_id="NBS_LAB280Search", search_type="CODED")
        else:
            self.setValueForHTMLElement(NBSConstants.HTML_SET_DROP_DOWN_RANDOM_VALUE,
                                        NBSConstants.TYPE_CODE_ID,
                                        "NBS_LAB280",
                                        None)
        # Numeric Result
        self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                    NBSConstants.TYPE_CODE_ID,
                                    "NBS_LAB364",
                                    "1.0")
        # units
        el = self.findElement(NBSConstants.TYPE_CODE_ID, "LAB115")
        if el is not None and el.is_enabled():
            self.setValueForHTMLElement(NBSConstants.HTML_SET_DROP_DOWN_RANDOM_VALUE,
                                        NBSConstants.TYPE_CODE_ID,
                                        "LAB115",
                                        None)
        # Text Result
        self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                    NBSConstants.TYPE_CODE_ID,
                                    "NBS_LAB208",
                                    "Text Result:".__add__(NBSUtilities.generate_random_string(55)))
        # Reference Range From
        self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                    NBSConstants.TYPE_CODE_ID,
                                    "NBS_LAB119",
                                    NBSUtilities.generate_random_int(4))
        # Reference Range To
        self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                    NBSConstants.TYPE_CODE_ID,
                                    "NBS_LAB120",
                                    NBSUtilities.generate_random_int(6))
        # status
        self.setValueForHTMLElement(NBSConstants.HTML_SET_DROP_DOWN_RANDOM_VALUE,
                                    NBSConstants.TYPE_CODE_ID,
                                    "NBS_LAB207",
                                    None)
        # Result Comments
        self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                    NBSConstants.TYPE_CODE_ID,
                                    "NBS_LAB104",
                                    "Result Comments:".__add__(NBSUtilities.generate_random_string(100)))

        # Manage Susceptibilities if the button is enabled/displayed
        elem = self.findElement(NBSConstants.TYPE_CODE_ID, "NBS_LAB222Button")
        if elem is not None and elem.is_displayed():
            self.addSusceptibilityRecords(path_id="NBS_LAB222Button")
        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                        NBSConstants.WAITING_TIME_IN_SECONDS)
        elem1 = self.findElement(NBSConstants.TYPE_CODE_ID, "NBS_LAB329Button")
        if elem1 is not None and elem1.is_displayed():
            self.addSusceptTrackingRecord(path_id="NBS_LAB329Button")
        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                        NBSConstants.WAITING_TIME_IN_SECONDS)

        # add record to section

        element = self.findElement(NBSConstants.TYPE_CODE_XPATH,
                                   "//*[@id='AddButtonToggleRESULTED_TEST_CONTAINER']/td/input")
        self.driver.execute_script("arguments[0].click();", element)

    def createNewOrganization(self, random_number):
        self.performButtonClick(NBSConstants.TYPE_CODE_ID, "Add")
        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
        # set quick code
        self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                    NBSConstants.TYPE_CODE_NAME,
                                    "quickCode",
                                    "Q".__add__(str(NBSUtilities.generate_random_int(9))))
        # Org Name
        self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                    NBSConstants.TYPE_CODE_XPATH,
                                    self.nbs_de_lr_add_org_name,
                                    "Org".__add__("-").__add__(str(random_number)))
        # Street Address1
        self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                    NBSConstants.TYPE_CODE_NAME,
                                    "streetAddress1",
                                    NBSConstants.APPEND_TEST_FOR_STREET_ADDR1.
                                    __add__("-").__add__(str(random_number)))
        # Street Address2
        self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                    NBSConstants.TYPE_CODE_NAME,
                                    "streetAddress2",
                                    NBSConstants.APPEND_TEST_FOR_STREET_ADDR2.
                                    __add__("-").__add__(str(random_number)))
        # City
        self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                    NBSConstants.TYPE_CODE_NAME,
                                    "city",
                                    NBSConstants.APPEND_TEST_FOR_STREET_CITY.
                                    __add__("-").__add__(str(random_number)))

        # State
        self.setValueForHTMLElement(NBSConstants.HTML_SET_DROP_DOWN_RANDOM_VALUE,
                                    NBSConstants.TYPE_CODE_ID,
                                    "NPP021",
                                    None)
        # Postal
        self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                    NBSConstants.TYPE_CODE_NAME,
                                    "zip",
                                    NBSUtilities.generate_random_int(5))
        # County
        self.setValueForHTMLElement(NBSConstants.HTML_SET_DROP_DOWN_RANDOM_VALUE,
                                    NBSConstants.TYPE_CODE_ID,
                                    "NPP023",
                                    None)
        # Country
        self.setValueForHTMLElement(NBSConstants.HTML_SET_DROP_DOWN_RANDOM_VALUE,
                                    NBSConstants.TYPE_CODE_ID,
                                    "DEM167",
                                    None)

        # Phone
        tele_phone = (NBSUtilities.generate_random_int(3).__add__("-").__add__(NBSUtilities.generate_random_int(3))
                      .__add__("-").__add__(NBSUtilities.generate_random_int(4)))
        self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                    NBSConstants.TYPE_CODE_NAME,
                                    "telephone",
                                    tele_phone)
        # Extension
        self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                    NBSConstants.TYPE_CODE_NAME,
                                    "ext",
                                    NBSUtilities.generate_random_int(4))
        # Email
        self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                    NBSConstants.TYPE_CODE_NAME,
                                    "EMail",
                                    NBSUtilities.generate_random_string(6).__add__("@uuu.com")
                                    )
        self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                self.add_org_submit_button)

    def createNewProvider(self, random_number):
        self.performButtonClick(NBSConstants.TYPE_CODE_ID, "Add")
        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
        # NAME SECTION
        # set quick code
        self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                    NBSConstants.TYPE_CODE_NAME,
                                    "quickCode",
                                    "Q".__add__(str(NBSUtilities.generate_random_int(9))))
        # prefix
        self.setValueForHTMLElement(NBSConstants.HTML_SET_DROP_DOWN_RANDOM_VALUE,
                                    NBSConstants.TYPE_CODE_ID,
                                    "NPP006",
                                    None)
        # lastName
        self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                    NBSConstants.TYPE_CODE_NAME,
                                    "lastName",
                                    "Prov-".__add__(str(random_number)))
        # firstName
        self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                    NBSConstants.TYPE_CODE_NAME,
                                    "firstName",
                                    NBSConstants.APPEND_TEST_FOR_FIRST_NAME
                                    .__add__("-").__add__(str(random_number)))
        # middleName
        self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                    NBSConstants.TYPE_CODE_NAME,
                                    "middleName",
                                    NBSConstants.APPEND_TEST_FOR_MIDDLE_NAME
                                    .__add__("-").__add__(str(random_number)))
        # suffix
        self.setValueForHTMLElement(NBSConstants.HTML_SET_DROP_DOWN_RANDOM_VALUE,
                                    NBSConstants.TYPE_CODE_ID,
                                    "NPP010",
                                    None)
        # degree
        self.setValueForHTMLElement(NBSConstants.HTML_SET_DROP_DOWN_RANDOM_VALUE,
                                    NBSConstants.TYPE_CODE_ID,
                                    "NPP060",
                                    None)

        # Address Section

        # Street Address1
        self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                    NBSConstants.TYPE_CODE_NAME,
                                    "streetAddress1",
                                    NBSConstants.APPEND_TEST_FOR_STREET_ADDR1
                                    .__add__("-").__add__(str(random_number)))
        # Street Address2
        self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                    NBSConstants.TYPE_CODE_NAME,
                                    "streetAddress2",
                                    NBSConstants.APPEND_TEST_FOR_STREET_ADDR2
                                    .__add__("-").__add__(str(random_number)))

        # city
        self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                    NBSConstants.TYPE_CODE_NAME,
                                    "city",
                                    NBSConstants.APPEND_TEST_FOR_STREET_CITY
                                    .__add__("-").__add__(str(random_number)))

        # state
        self.setValueForHTMLElement(NBSConstants.HTML_SET_DROP_DOWN_RANDOM_VALUE,
                                    NBSConstants.TYPE_CODE_ID,
                                    "NPP021",
                                    None)
        # postal

        self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                    NBSConstants.TYPE_CODE_NAME,
                                    "zip",
                                    NBSUtilities.generate_random_int(5))

        # county
        self.setDropdownValueRandom(NBSConstants.TYPE_CODE_XPATH, "//*[@id='NPP023']")

        """self.setValueForHTMLElement(NBSConstants.HTML_SET_DROP_DOWN_RANDOM_VALUE,
                                    NBSConstants.TYPE_CODE_ID,
                                    "NPP023",
                                    None)"""

        # Contact Information Section
        # phone
        tele_phone = (NBSUtilities.generate_random_int(3).__add__("-").__add__(NBSUtilities.generate_random_int(3))
                      .__add__("-").__add__(NBSUtilities.generate_random_int(4)))
        self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                    NBSConstants.TYPE_CODE_NAME,
                                    "telephone",
                                    tele_phone)
        # Extension
        self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                    NBSConstants.TYPE_CODE_NAME,
                                    "ext",
                                    NBSUtilities.generate_random_int(4))
        # Email
        self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                    NBSConstants.TYPE_CODE_NAME,
                                    "EMail",
                                    NBSUtilities.generate_random_string(6).__add__("@uuu.com")
                                    )
        self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                self.add_org_submit_button)
        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)

    def setProviderIdentificationRecordPopulate(self, random_number):
        try:
            # Identification as of date
            self.selectCalendarDate(NBSConstants.TYPE_CODE_ID,
                                    "NBS452Icon")
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            2)
            # ID type
            self.setDropdownValueRandom(NBSConstants.TYPE_CODE_XPATH,
                                        "//*[@id='DEM144']")

            element = self.findElement(NBSConstants.TYPE_CODE_XPATH, "//*[@id='DEM144']")
            if element.get_attribute("value") == "":
                self.setDropdownValueRandom(NBSConstants.TYPE_CODE_XPATH,
                                            "//*[@id='DEM144']")
                element = self.findElement(NBSConstants.TYPE_CODE_XPATH, "//*[@id='DEM144']")
            elif element.get_attribute("value") == "OTH":
                self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                            NBSConstants.TYPE_CODE_ID,
                                            "DEM144Oth",
                                            element.get_attribute("value").__add__("-TYPE"))

            # ID Assign
            self.setValueForHTMLElement(NBSConstants.HTML_SET_DROP_DOWN_RANDOM_VALUE,
                                        NBSConstants.TYPE_CODE_ID,
                                        "DEM146",
                                        None)
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
            # ID Value
            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                        NBSConstants.TYPE_CODE_ID,
                                        "DEM147",
                                        element.get_attribute("value").__add__("-").__add__(random_number))
        except Exception as exc:
            self.logger.exception(msg="exception while populating provider record")

    def setRepeatableSectionActions(self, section_type, random_number):
        self.displayRecordDetails()
        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                        NBSConstants.WAITING_TIME_IN_SECONDS)

        self.editExistingRecord(section_type, NBSConstants.EDIT_RECORD_NUM_IN_SECTION, str(random_number))
        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                        NBSConstants.WAITING_TIME_IN_SECONDS)
        self.deleteExistingRecord()
        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                        NBSConstants.WAITING_TIME_IN_SECONDS)

    def addRecordToSection(self, section_type, update_path=None):
        element = None
        if section_type == LabReportObject.identification_section:
            if update_path is None or not update_path:
                element = self.findElement(NBSConstants.TYPE_CODE_XPATH,
                                           "//*[@id='AddButtonToggleENTITYID100']/td/input")
            else:
                element = self.findElement(NBSConstants.TYPE_CODE_XPATH, update_path)

        if element is not None:
            element.click()
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)

    def displayRecordDetails(self):
        element = self.findElement(NBSConstants.TYPE_CODE_XPATH,
                                   "//*[@id='questionbodyENTITYID100']/tr[1]/td[2]/input")
        if element is not None:
            element.click()
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)

    def deleteExistingRecord(self):
        element = self.findElement(NBSConstants.TYPE_CODE_XPATH,
                                   "//*[@id='questionbodyENTITYID100']/tr[2]/td[4]/input")
        if element is not None:
            try:
                element.click()
                self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                NBSConstants.WAITING_TIME_IN_SECONDS)
                WebDriverWait(self.driver, 10).until(EC.alert_is_present())
                alert = self.driver.switch_to.alert
                alert.accept()
            except:
                self.logger.exception(msg="exception while deleting id record")

    def editExistingRecord(self, section_type, record_number, random_number):
        element = self.findElement(NBSConstants.TYPE_CODE_XPATH,
                                   "//*[@id='questionbodyENTITYID100']/tr[1]/td[3]/input")
        if element is not None:
            element.click()
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
            self.populateSectionRecord(section_type, str(random_number))
            updated_path = "//*[@id='UpdateButtonToggleENTITYID100']/td/input"
            self.addRecordToSection(section_type, updated_path)
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)

    def findSpecificLabRecord(self, local_uid):
        # get observation uid
        uid = self.db_utilities.getSingleColumnValueByTable("observation_uid", "observation",
                                                            "local_id", local_uid)
        self.logger.info("observation_uid:::" + str(uid))
        # click on events tab
        self.performButtonClick_web(NBSConstants.TYPE_CODE_ID, "tabs0head1")
        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
        # search observation uid, in the event lab table
        try:
            elements = self.findElements(NBSConstants.TYPE_CODE_XPATH,
                                         "//a[contains(@href, 'observationUID=".__add__(str(uid)) + "')]")
            self.logger.info(len(elements))
            if elements is not None and len(elements) > 1:
                elements[-1].click()
        except:
            return False
        return True

    def verifyAllLinksInLabReport(self, local_uid):
        # open lab report
        if self.findSpecificLabRecord(local_uid):

            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
            # verify patient links
            # self.verifyPatientTabLinks()

            # verify lab-report links
            # self.verifyLabReportTabLinks()

            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
            self.performButtonClick(NBSConstants.TYPE_CODE_NAME, "Delete")
            WebDriverWait(self.driver, 10).until(EC.alert_is_present())
            alert = self.driver.switch_to.alert
            alert.accept()
            return True
        else:
            return False

    def verifyPatientTabLinks(self):
        try:
            # Patient TAB
            self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                    "//*[@id='tabs0head0']")

            # collapse patient information
            self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                    "//*[@id='PatientInformation']/table/tbody/tr/td[1]/a[1]/img")
            # expand patient information
            self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                    "//*[@id='PatientInformation']/table/tbody/tr/td[1]/a[1]/img")
            # collapse subsections of patient information
            self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                    "//*[@id='PatientInformation']/div/table[1]/tbody/tr/td/a")
            # expand subsections of patient information
            self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                    "//*[@id='PatientInformation']/div/table[1]/tbody/tr/td/a")
            # collapse patient search
            self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                    "//*[@id='NBS_UI_27']/thead/tr/th/a/img")
            # expand patient search
            self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                    "//*[@id='NBS_UI_27']/thead/tr/th/a/img")
            # collapse general information
            self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                    "//*[@id='NBS_UI_4']/thead/tr/th/a/img")
            # expand general information
            self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                    "//*[@id='NBS_UI_4']/thead/tr/th/a/img")
            # collapse name information
            self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                    "//*[@id='NBS_UI_5']/thead/tr/th/a/img")
            # expand name information
            self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                    "//*[@id='NBS_UI_5']/thead/tr/th/a/img")
            # collapse Other personal Details
            self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                    "//*[@id='NBS_UI_6']/thead/tr/th/a/img")
            # expand Other personal Details
            self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                    "//*[@id='NBS_UI_6']/thead/tr/th/a/img")
            # collapse Entity ID Information
            self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                    "//*[@id='ENTITYID100']/thead/tr/th/a/img")
            # expand Entity ID Information
            self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                    "//*[@id='ENTITYID100']/thead/tr/th/a/img")
            # collapse Reporting Address for Case Counting
            self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                    "//*[@id='NBS_UI_7']/thead/tr/th/a/img")
            # expand Reporting Address for Case Counting
            self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                    "//*[@id='NBS_UI_7']/thead/tr/th/a/img")
            # collapse telephone information
            self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                    "//*[@id='NBS_UI_8']/thead/tr/th/a/img")
            # expand telephone information
            self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                    "//*[@id='NBS_UI_8']/thead/tr/th/a/img")
            # collapse ethnicity and Race information
            self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                    "//*[@id='NBS_UI_9']/thead/tr/th/a/img")
            # expand ethnicity and Race information
            self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                    "//*[@id='NBS_UI_9']/thead/tr/th/a/img")

        except:
            self.logger.exception(msg="exception while verifying links in the patient profile")
            pass

    def verifyLabReportTabLinks(self):
        try:
            # LAB REPORT TAB
            self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                    "//*[@id='tabs0head1']")
            # collapse all sections
            self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                    "//*[@id='editLabReport']/table/tbody/tr[2]/td/a")
            # expand all sections
            self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                    "//*[@id='editLabReport']/table/tbody/tr[2]/td/a")
            # collapse order information
            self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                    "//*[@id='OrderInformation']/table/tbody/tr/td[1]/a[1]/img")
            # expand order information
            self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                    "//*[@id='OrderInformation']/table/tbody/tr/td[1]/a[1]/img")
            # collapse subsections under order information
            self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                    "//*[@id='OrderInformation']/div/table[1]/tbody/tr/td/a")
            # expand subsections under order information
            self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                    "//*[@id='OrderInformation']/div/table[1]/tbody/tr/td/a")
            # collapse facility and provider information
            self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                    "//*[@id='NBS_UI_13']/thead/tr/th/a/img")
            # expand facility and provider information
            self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                    "//*[@id='NBS_UI_13']/thead/tr/th/a/img")
            # collapse order details information
            self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                    "//*[@id='NBS_UI_14']/thead/tr/th/a/img")
            # expand order details information
            self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                    "//*[@id='NBS_UI_14']/thead/tr/th/a/img")
            # collapse test results
            self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                    "//*[@id='TestResults']/table/tbody/tr/td[1]/a[1]/img")
            # expand test results
            self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                    "//*[@id='TestResults']/table/tbody/tr/td[1]/a[1]/img")
            # collapse subsections under test results
            self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                    "//*[@id='TestResults']/div/table[1]/tbody/tr/td/a")
            # expand subsections under test results
            self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                    "//*[@id='TestResults']/div/table[1]/tbody/tr/td/a")
            # collapse ordered test
            self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                    "//*[@id='NBS_UI_16']/thead/tr/th/a/img")
            # expand ordered test
            self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                    "//*[@id='NBS_UI_16']/thead/tr/th/a/img")
            # collapse resulted test
            self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                    "//*[@id='RESULTED_TEST_CONTAINER']/thead/tr/th/a/img")
            # expand resulted test
            self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                    "//*[@id='RESULTED_TEST_CONTAINER']/thead/tr/th/a/img")
            # collapse lab report comments
            self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                    "//*[@id='LabReportComments']/table/tbody/tr/td[1]/a[1]/img")
            # expand lab report comments
            self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                    "//*[@id='LabReportComments']/table/tbody/tr/td[1]/a[1]/img")
            # collapse add comments
            self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                    "//*[@id='NBS_UI_29']/thead/tr/th/a/img")
            # expand add comments
            self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                    "//*[@id='NBS_UI_29']/thead/tr/th/a/img")
            # collapse other information
            self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                    "//*[@id='OtherInformation']/table/tbody/tr/td[1]/a[1]/img")
            # expand other information
            self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                    "//*[@id='OtherInformation']/table/tbody/tr/td[1]/a[1]/img")
            # back to top link
            self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                    "//*[@id='OtherInformation']/table/tbody/tr/td[2]/a")
            # anchor links
            # order information
            self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                    "//*[@id='editLabReport']/table/tbody/tr[1]/td/ul/li[2]/a")
            # test results
            self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                    "//*[@id='editLabReport']/table/tbody/tr[1]/td/ul/li[4]/a")
            # lab report comments
            self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                    "//*[@id='editLabReport']/table/tbody/tr[1]/td/ul/li[6]/a")
            # other information
            self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                    "//*[@id='editLabReport']/table/tbody/tr[1]/td/ul/li[8]/a")
        except:
            self.logger.exception(msg="exception while verifying lab report links")
            pass

    def setSusceptibilityRecordPopulate(self, coded_result=False):
        # clear drug name
        self.performButtonClick(NBSConstants.TYPE_CODE_ID, "NBS_LAB110ClearButton")
        # select drug name by dropdown
        """self.setValueForHTMLElement(NBSConstants.HTML_SET_DROP_DOWN_RANDOM_VALUE,
                                    NBSConstants.TYPE_CODE_ID,
                                    "NBS_LAB110",
                                    None)"""
        try:
            self.selectDropDownOptionValueRandom("NBS_LAB110")
        except:
            self.logger.info("NBS110 Error")
            pass

        # clear drug name
        self.performButtonClick(NBSConstants.TYPE_CODE_ID, "NBS_LAB110ClearButton")
        # select drug name by search button
        self.getDrugSearchDetails(path_id="NBS_LAB110Search")
        # select Result Method by dropdown
        self.setValueForHTMLElement(NBSConstants.HTML_SET_DROP_DOWN_RANDOM_VALUE,
                                    NBSConstants.TYPE_CODE_ID,
                                    "NBS377",
                                    None)
        if coded_result:
            # Numeric Result
            self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                        NBSConstants.TYPE_CODE_ID,
                                        "NBS369",
                                        "1.0")
            # units
            el = self.findElement(NBSConstants.TYPE_CODE_ID, "NBS372")
            if el is not None and el.is_enabled():
                self.setValueForHTMLElement(NBSConstants.HTML_SET_DROP_DOWN_RANDOM_VALUE,
                                            NBSConstants.TYPE_CODE_ID,
                                            "NBS372",
                                            None)
        else:
            # Coded Result
            self.setValueForHTMLElement(NBSConstants.HTML_SET_DROP_DOWN_RANDOM_VALUE,
                                        NBSConstants.TYPE_CODE_ID,
                                        "NBS367",
                                        None)
            # Interpretation
            self.setValueForHTMLElement(NBSConstants.HTML_SET_DROP_DOWN_RANDOM_VALUE,
                                        NBSConstants.TYPE_CODE_ID,
                                        "NBS378",
                                        None)
        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                        NBSConstants.WAITING_TIME_IN_SECONDS)
        # add record
        element = self.findElement(NBSConstants.TYPE_CODE_XPATH,
                                   "//*[@id='AddButtonToggleNBS_UI_2']/td/input")
        self.driver.execute_script("arguments[0].click();", element)

    def editSusceptibilityRecord(self):
        # self.performButtonClick(NBSConstants.TYPE_CODE_XPATH, "//*[@id='questionbodyNBS_UI_2']/tr/td[3]/input")
        self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                "/html/body/div[2]/form/div[3]/table/tbody/tr["
                                "2]/td/div/table/tbody/tr/td/div/div/div/table["
                                "2]/tbody/tr[1]/td/table/tbody/tr/td[2]/table/tbody[1]/tr[1]/td["
                                "3]/input")
        self.setSusceptibilityRecordPopulate(coded_result=False)
        # self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,"//*[@id='UpdateButtonToggleNBS_UI_2']/td/input")
        self.performButtonClick(NBSConstants.TYPE_CODE_XPATH, "/html/body/div[2]/form/div[3]/table/tbody/tr["
                                                              "2]/td/div/table/tbody/tr/td/div/div/div/table["
                                                              "2]/tbody/tr[19]/td/input")

    def deleteSusceptibilityRecord(self):
        try:
            # self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
            #                        "//*[@id='questionbodyNBS_UI_2']/tr[2]/td[3]/input")
            self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                    "/html/body/div[2]/form/div[3]/table/tbody/tr["
                                    "2]/td/div/table/tbody/tr/td/div/div/div/table["
                                    "2]/tbody/tr[1]/td/table/tbody/tr/td[2]/table/tbody[1]/tr[2]/td[4]/input")

            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)

            WebDriverWait(self.driver, 10).until(EC.alert_is_present())
            alert = self.driver.switch_to.alert
            alert.accept()
        except:
            self.logger.exception(msg="exception while deleting susceptibility record")

    def addSusceptTrackingRecord(self, path_id=None):
        # Add Susceptibility Tracking Records to Resulted Test
        if path_id is not None:
            self.performButtonClick(NBSConstants.TYPE_CODE_ID, path_id)
            original_window = self.driver.current_window_handle
            try:
                WebDriverWait(self.driver, 10).until(EC.number_of_windows_to_be(2))
                for window_handle in self.driver.window_handles:
                    if window_handle != original_window:
                        self.driver.switch_to.window(window_handle)
                        self.manageSusceptTracking()
                        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                        NBSConstants.WAITING_TIME_IN_SECONDS)
                        # submit
                        self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                                "//*[@id='bd']/div[4]/input[1]")
                        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                        NBSConstants.WAITING_TIME_IN_SECONDS)
                        # close
                        self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                                "//*[@id='bd']/div[3]/input[3]")
                        break
                self.driver.switch_to.window(original_window)
            except:
                self.logger.exception(msg="exception while adding susceptibility record")
                pass
            return True

    def addSusceptibilityRecords(self, path_id=None):
        # Add Susceptibility Records to Resulted Test
        if path_id is not None:
            self.performButtonClick(NBSConstants.TYPE_CODE_ID, path_id)
            original_window = self.driver.current_window_handle
            try:
                WebDriverWait(self.driver, 10).until(EC.number_of_windows_to_be(2))
                for window_handle in self.driver.window_handles:
                    if window_handle != original_window:
                        self.driver.switch_to.window(window_handle)

                        for i in range(1, NBSConstants.ADD_NUMBER_OF_REPEATABLE_SECTIONS + 1):
                            if i == 2:
                                self.setSusceptibilityRecordPopulate(coded_result=True)
                            else:
                                self.setSusceptibilityRecordPopulate(coded_result=False)

                        # Perform Actions On Susceptibility Table
                        # display first record details
                        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                        2)
                        self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                               "//*[@id='questionbodyNBS_UI_2']/tr[1]/td[2]/input")

                        # self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                        #                         "/html/body/div[2]/form/div[3]/table/tbody/tr["
                        #                         "2]/td/div/table/tbody/tr/td/div/div/div/table["
                        #                         "2]/tbody/tr[1]/td/table/tbody/tr/td[2]/table/tbody[1]/tr[1]/td["
                        #                         "2]/input")

                        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                        NBSConstants.WAITING_TIME_IN_SECONDS)
                        # Edit first record in the Susceptibility Table
                        # self.editSusceptibilityRecord()
                        # delete second record in the Resulted Test Table
                        self.deleteSusceptibilityRecord()
                        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                        2)
                        # submit button
                        self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                                "//*[@id='bd']/div[4]/input[1]")
                        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                        2)
                        # close the Susceptibility
                        self.performButtonClick(NBSConstants.TYPE_CODE_XPATH, "//*[@id='bd']/div[3]/input[3]")
                        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                        2)
                        break
                self.driver.switch_to.window(original_window)
            except Exception as exc:
                self.logger.exception(exc, exc_info=(type(exc), exc, exc.__traceback__))
                pass
            return True

    def manageSusceptTracking(self):
        try:
            random_choice_list = ["Y", "N"]
            select_value = random.choice(random_choice_list)
            # self.logger.info("select_value:::" + str(select_value))
            # Was an isolate received at the state public health lab
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
            # self.setDropdownValueRandom(NBSConstants.TYPE_CODE_XPATH, "//*[@id='NBS_LAB331']")
            self.setValueForHTMLElement(NBSConstants.HTML_DROP_DOWN_ELEMENT,
                                        NBSConstants.TYPE_CODE_ID,
                                        "NBS_LAB331",
                                        select_value)
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
            el = self.getSelectedOptionForSingleSelect(NBSConstants.TYPE_CODE_XPATH, "//*[@id='NBS_LAB331']")
            if el is not None:
                # self.logger.info("el value:::" + str(el))
                if el == "N":
                    # If an isolate wasn't received at the state public health lab, what is the reason
                    # self.setDropdownValueRandom(NBSConstants.TYPE_CODE_XPATH,"//*[@id='NBS_LAB332']")
                    self.setValueForHTMLElement(NBSConstants.HTML_DROP_DOWN_ELEMENT,
                                                NBSConstants.TYPE_CODE_ID,
                                                "NBS_LAB332",
                                                "FORWARD")
                    # self.setDropdownValueRandom(NBSConstants.TYPE_CODE_XPATH,
                    #                            "//*[@id='NBS_LAB332']")
                elif el == "Y":
                    # If Yes, please specify date received in state public health lab
                    self.selectCalendarDateForNPopups(NBSConstants.TYPE_CODE_ID, "NBS_LAB334Icon")
                    # State public health lab isolate id number
                    self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                                NBSConstants.TYPE_CODE_ID,
                                                "NBS_LAB335",
                                                NBSUtilities.generate_random_int(7))
                    # Was case confirmed at state public health lab
                    self.setValueForHTMLElement(NBSConstants.HTML_DROP_DOWN_ELEMENT,
                                                NBSConstants.TYPE_CODE_ID,
                                                "NBS_LAB336",
                                                select_value)
                    # self.setDropdownValueRandom(NBSConstants.TYPE_CODE_XPATH,
                    #                            "//*[@id='NBS_LAB336']")

                self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                NBSConstants.WAITING_TIME_IN_SECONDS)

            ################################################################################

            # Was specimen or isolate forwarded to CDC for testing or confirmation?
            # self.setDropdownValueRandom(NBSConstants.TYPE_CODE_XPATH, "//*[@id='NBS_LAB363']")
            self.setValueForHTMLElement(NBSConstants.HTML_DROP_DOWN_ELEMENT,
                                        NBSConstants.TYPE_CODE_ID,
                                        "NBS_LAB363",
                                        select_value)
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)

            ################################################################################

            # PulseNet Isolate?
            # self.setDropdownValueRandom(NBSConstants.TYPE_CODE_XPATH, "//*[@id='NBS_LAB337']")
            self.setValueForHTMLElement(NBSConstants.HTML_DROP_DOWN_ELEMENT,
                                        NBSConstants.TYPE_CODE_ID,
                                        "NBS_LAB337",
                                        select_value)
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
            elem2 = self.getSelectedOptionForSingleSelect(NBSConstants.TYPE_CODE_XPATH,
                                                          "//*[@id='NBS_LAB337']")
            # self.logger.info("elem2 value:::" + str(elem2))
            if elem2 is not None and elem2 == "Y":
                # Has isolate PFGE pattern been sent to central PulseNet database
                # self.setDropdownValueRandom(NBSConstants.TYPE_CODE_XPATH,
                #                            "//*[@id='NBS_LAB338']")
                self.setValueForHTMLElement(NBSConstants.HTML_DROP_DOWN_ELEMENT,
                                            NBSConstants.TYPE_CODE_ID,
                                            "NBS_LAB338",
                                            select_value)
                elem3 = self.getSelectedOptionForSingleSelect(NBSConstants.TYPE_CODE_XPATH,
                                                              "//*[@id='NBS_LAB338']")
                # self.logger.info("elem3 value:::" + str(elem3))
                if elem3 is not None and elem3 == "Y":
                    # PulseNet PFGE Designation Enzyme 1
                    self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                                NBSConstants.TYPE_CODE_ID,
                                                "NBS_LAB339",
                                                NBSUtilities.generate_random_int(10))
                    # State Health Dept Lab PFGE Designation Enzyme 1
                    self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                                NBSConstants.TYPE_CODE_ID,
                                                "NBS_LAB340",
                                                NBSUtilities.generate_random_int(10))
                    # PulseNet PFGE Designation Enzyme 2
                    self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                                NBSConstants.TYPE_CODE_ID,
                                                "NBS_LAB341",
                                                NBSUtilities.generate_random_int(10))
                    # State Health Dept Lab PFGE Designation Enzyme 2
                    self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                                NBSConstants.TYPE_CODE_ID,
                                                "NBS_LAB342",
                                                NBSUtilities.generate_random_int(10))
                    # PulseNet PFGE Designation Enzyme 3
                    self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                                NBSConstants.TYPE_CODE_ID,
                                                "NBS_LAB343",
                                                NBSUtilities.generate_random_int(10))
                    # State Health Dept Lab PFGE Designation Enzyme 3
                    self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                                NBSConstants.TYPE_CODE_ID,
                                                "NBS_LAB344",
                                                NBSUtilities.generate_random_int(10))
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
            #############################################################################################
            # NARMS Isolate
            self.setValueForHTMLElement(NBSConstants.HTML_DROP_DOWN_ELEMENT,
                                        NBSConstants.TYPE_CODE_ID,
                                        "NBS_LAB345",
                                        select_value)

            # self.setDropdownValueRandom(NBSConstants.TYPE_CODE_XPATH,
            #                            "//*[@id='NBS_LAB345']")
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)

            iso_element = self.getSelectedOptionForSingleSelect(NBSConstants.TYPE_CODE_XPATH,
                                                                "//*[@id='NBS_LAB345']")
            # self.logger.info("iso_element value:::" + str(iso_element))
            if iso_element is not None and iso_element == "Y":
                # Has isolate been sent to NARMS
                # self.setDropdownValueRandom(NBSConstants.TYPE_CODE_XPATH,
                #                            "//*[@id='NBS_LAB346']")
                self.setValueForHTMLElement(NBSConstants.HTML_DROP_DOWN_ELEMENT,
                                            NBSConstants.TYPE_CODE_ID,
                                            "NBS_LAB346",
                                            select_value)
                narms_element = self.getSelectedOptionForSingleSelect(NBSConstants.TYPE_CODE_XPATH,
                                                                      "//*[@id='NBS_LAB346']")
                # self.logger.info("narms_element value:::" + str(narms_element))
                if narms_element is not None:
                    if narms_element == "N":
                        # If an isolate was not sent to NARMS, what is the reason
                        # self.setDropdownValueRandom(NBSConstants.TYPE_CODE_XPATH,
                        #                            "//*[@id='NBS_LAB347']")
                        self.setValueForHTMLElement(NBSConstants.HTML_DROP_DOWN_ELEMENT,
                                                    NBSConstants.TYPE_CODE_ID,
                                                    "NBS_LAB347",
                                                    "ISOLATCT")

                    elif narms_element == "Y":
                        # State-assigned NARMS ID number
                        self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                                    NBSConstants.TYPE_CODE_ID,
                                                    "NBS_LAB348",
                                                    NBSUtilities.generate_random_int(10))

                        # self.logger.info("Expected Ship Date")

                        # Expected Ship Date

                        _birth_day, _birth_month, _birth_year = NBSUtilities.getRandomDateByVars()
                        """self.selectCalendarDateForNPopups(NBSConstants.TYPE_CODE_ID,
                                                          "NBS_LAB349Icon",
                                                          str(int(_birth_day)),
                                                          _birth_month,
                                                          str(int(_birth_year)))
                        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                        NBSConstants.WAITING_TIME_IN_SECONDS)
                        elf.logger.info("Actual Ship Date")
                        self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                                    NBSConstants.TYPE_CODE_ID,
                                                    "NBS_LAB350",
                                                    datetime.now().strftime("%m/%d/%Y"))"""
                        self.selectCalendarDateForNPopups(NBSConstants.TYPE_CODE_ID,
                                                          "NBS_LAB349Icon")
                        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                        NBSConstants.WAITING_TIME_IN_SECONDS)
                        self.selectCalendarDateForNPopups(NBSConstants.TYPE_CODE_ID,
                                                          "NBS_LAB350Icon")

                        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                        NBSConstants.WAITING_TIME_IN_SECONDS)

            #############################################################################################

            # EIP Isolate
            # self.setDropdownValueRandom(NBSConstants.TYPE_CODE_XPATH,
            #                            "//*[@id='NBS_LAB351']")
            # self.logger.info("starts eip isolate")
            self.setValueForHTMLElement(NBSConstants.HTML_DROP_DOWN_ELEMENT,
                                        NBSConstants.TYPE_CODE_ID,
                                        "NBS_LAB351",
                                        select_value)

            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)

            eip_element = self.getSelectedOptionForSingleSelect(NBSConstants.TYPE_CODE_XPATH,
                                                                "//*[@id='NBS_LAB351']")

            if eip_element is not None:
                # self.logger.info("eip_element value:::" + str(eip_element))
                if eip_element == "Y":
                    # Is this specimen available for further EIP testing?
                    # self.setDropdownValueRandom(NBSConstants.TYPE_CODE_XPATH,
                    #                            "//*[@id='NBS_LAB352']")
                    self.setValueForHTMLElement(NBSConstants.HTML_DROP_DOWN_ELEMENT,
                                                NBSConstants.TYPE_CODE_ID,
                                                "NBS_LAB352",
                                                select_value)
                    eip_testing = self.getSelectedOptionForSingleSelect(NBSConstants.TYPE_CODE_XPATH,
                                                                        "//*[@id='NBS_LAB352']")
                    # self.logger.info("eip_testing value:::" + str(eip_testing))
                    if eip_testing is not None and eip_testing == "ISOLATNO":
                        # If a specimen is not available for further EIP testing, what is the reason
                        self.setDropdownValueRandom(NBSConstants.TYPE_CODE_XPATH,
                                                    "//*[@id='NBS_LAB353']")
                        # If Yes, where will the specimen be shipped?
                        self.setDropdownValueRandom(NBSConstants.TYPE_CODE_XPATH,
                                                    "//*[@id='NBS_LAB355']")
                        # Expected Ship Date
                        s_birth_day, s_birth_month, s_birth_year = NBSUtilities.getRandomDateByVars()
                        self.selectCalendarDateForNPopups(NBSConstants.TYPE_CODE_ID,
                                                          "NBS_LAB356Icon",
                                                          str(int(s_birth_day)),
                                                          s_birth_month,
                                                          str(int(s_birth_year)))
                        # Actual Ship Date
                        self.selectCalendarDateForNPopups(NBSConstants.TYPE_CODE_ID,
                                                          "NBS_LAB357Icon")
                        # Was specimen requested for reshipment
                        self.setDropdownValueRandom(NBSConstants.TYPE_CODE_XPATH,
                                                    "//*[@id='NBS_LAB358']")
                        reship_element = self.getSelectedOptionForSingleSelect(NBSConstants.TYPE_CODE_XPATH,
                                                                               "//*[@id='NBS_LAB358']")
                        # self.logger.info("reship_element value:::" + str(reship_element))
                        if reship_element is not None and reship_element == "Y":
                            #  a specimen was requested for reshipment for further EIP testing,
                            #  what is the reason
                            self.setDropdownValueRandom(NBSConstants.TYPE_CODE_XPATH,
                                                        "//*[@id='NBS_LAB359']")

                            #  Expected Reship Date
                            self.selectCalendarDateForNPopups(NBSConstants.TYPE_CODE_ID,
                                                              "NBS_LAB361Icon")
                            #  Actual Reship Date
                            self.selectCalendarDateForNPopups(NBSConstants.TYPE_CODE_ID,
                                                              "NBS_LAB362Icon")
                    elif eip_testing is not None and eip_testing == "N":
                        # If a specimen is not available for further EIP testing, what is the reason?
                        # self.setDropdownValueRandom(NBSConstants.TYPE_CODE_XPATH,
                        #                            "//*[@id='NBS_LAB353']")
                        self.setValueForHTMLElement(NBSConstants.HTML_DROP_DOWN_ELEMENT,
                                                    NBSConstants.TYPE_CODE_ID,
                                                    "NBS_LAB353",
                                                    "REF")

                    elif eip_testing is not None and eip_testing == "Y":
                        # if Yes, where will the specimen be shipped?
                        self.setValueForHTMLElement(NBSConstants.HTML_DROP_DOWN_ELEMENT,
                                                    NBSConstants.TYPE_CODE_ID,
                                                    "NBS_LAB355",
                                                    "EIPLAB")
                        # self.setDropdownValueRandom(NBSConstants.TYPE_CODE_XPATH,
                        #                            "//*[@id='NBS_LAB355']")
                        # Expected Ship Date
                        """birth_day, birth_month, birth_year = NBSUtilities.getRandomDateByVars()
                        self.selectCalendarDateForNPopups(NBSConstants.TYPE_CODE_ID,
                                                          "NBS_LAB356Icon",
                                                          str(int(birth_day)),
                                                          birth_month,
                                                          str(int(birth_year)))"""
                        self.selectCalendarDateForNPopups(NBSConstants.TYPE_CODE_ID,
                                                          "NBS_LAB356Icon")
                        # Actual Ship Date
                        self.selectCalendarDateForNPopups(NBSConstants.TYPE_CODE_ID,
                                                          "NBS_LAB357Icon")
                        # Was specimen requested for reshipment
                        self.setValueForHTMLElement(NBSConstants.HTML_DROP_DOWN_ELEMENT,
                                                    NBSConstants.TYPE_CODE_ID,
                                                    "NBS_LAB358",
                                                    select_value)

                        # self.setDropdownValueRandom(NBSConstants.TYPE_CODE_XPATH,
                        #                            "//*[@id='NBS_LAB358']")

                        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                        NBSConstants.WAITING_TIME_IN_SECONDS)
                        reship_element = self.getSelectedOptionForSingleSelect(NBSConstants.TYPE_CODE_XPATH,
                                                                               "//*[@id='NBS_LAB358']")

                        if reship_element is not None and reship_element == "Y":
                            #  a specimen was requested for reshipment for further EIP testing,
                            #  what is the reason
                            # self.setDropdownValueRandom(NBSConstants.TYPE_CODE_XPATH,
                            #                            "//*[@id='NBS_LAB359']")
                            self.setValueForHTMLElement(NBSConstants.HTML_DROP_DOWN_ELEMENT,
                                                        NBSConstants.TYPE_CODE_ID,
                                                        "NBS_LAB359",
                                                        "LOSTCDC")
                            #  Expected Reship Date
                            self.selectCalendarDateForNPopups(NBSConstants.TYPE_CODE_ID,
                                                              "NBS_LAB361Icon")
                            #  Actual Reship Date
                            self.selectCalendarDateForNPopups(NBSConstants.TYPE_CODE_ID,
                                                              "NBS_LAB362Icon")
                self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                NBSConstants.WAITING_TIME_IN_SECONDS)
        except Exception as exc:
            self.logger.exception(exc, exc_info=(type(exc), exc, exc.__traceback__))
            pass

    def getDrugSearchDetails(self, path_id):
        if path_id is not None:
            self.performButtonClick(NBSConstants.TYPE_CODE_ID, path_id)
            original_window = self.driver.current_window_handle
            try:
                WebDriverWait(self.driver, 10).until(EC.number_of_windows_to_be(len(self.driver.window_handles)))
                self.driver.switch_to.window(self.driver.window_handles[len(self.driver.window_handles) - 1])
                # Search Screen
                self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                            NBSConstants.TYPE_CODE_XPATH,
                                            "//*[@id='labTest']",
                                            "%")
                # keep default radio button as it is (SHORT) if search_type is none and click submit
                self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                NBSConstants.WAITING_TIME_IN_SECONDS)
                self.performButtonClick(NBSConstants.TYPE_CODE_ID, "Submit")
                ele = self.findElement(NBSConstants.TYPE_CODE_XPATH, "//*[@id='parent']/tbody/tr[1]/td[1]/a")
                if ele is not None:
                    ele.click()
                self.driver.switch_to.window(original_window)
            except Exception as ex:
                self.logger.debug("Exception while getting test result code:::::%s", str(ex))
                self.driver.switch_to.window(original_window)
                pass
            return True
