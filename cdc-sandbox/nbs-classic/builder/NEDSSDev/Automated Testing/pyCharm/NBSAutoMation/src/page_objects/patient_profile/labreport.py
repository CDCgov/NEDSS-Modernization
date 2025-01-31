from selenium.webdriver.support.wait import WebDriverWait
from src.page_objects.data_entry.labreport import LabReportObject
from src.utilities.constants import NBSConstants
from src.utilities.log_config import LogUtils
from src.utilities.nbs_utilities import NBSUtilities
from selenium.webdriver.support import expected_conditions as EC


class PatientProfileLRObject(LabReportObject):
    logger = LogUtils.loggen(__name__)

    def __init__(self, driver):
        super().__init__(driver)
        self.driver = driver

    def createLabReportForPatient(self, random_number_for_patient):
        # Facility and Provider Information
        self.setFacilityAndProviderSection(random_number_for_patient)
        # Order Details For Lab
        self.setOrderDetailsForLab()
        # Test Results For Lab
        # set Ordered Test
        self.setOrderedTestDetails(random_number_for_patient)
        # set Resulted Test
        self.setResultedTestDetails()
        # Lab Report Comments

        self.setValueForHTMLElement_web(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                        NBSConstants.TYPE_CODE_NAME,
                                        "pageClientVO.answer(NBS460)",
                                        "Comments:".__add__(NBSUtilities.generate_random_string(60)))
        # Other Information
        # before click submit, save same reporting facility to variable
        PatientProfileLRObject.facility_checked = self.findElement(NBSConstants.TYPE_CODE_NAME,
                                                                   "pageClientVO.answer(NBS_LAB267)").is_selected()
        # Create Lab Report by clicking Submit
        self.performButtonClick_web(NBSConstants.TYPE_CODE_ID, "SubmitBottom")

        return True

    def updateLabReportInPatientProfile(self, random_number_for_patient):
        # click on Edit Button to switch to Edit Mode for Lab
        self.performButtonClick_web(NBSConstants.TYPE_CODE_XPATH,
                                    "//*[@id='doc3']/form/div[2]/table/tbody/tr/td[2]/input[1]")
        # clear the ordering facility
        self.performButtonClick_web(NBSConstants.TYPE_CODE_ID, "NBS_LAB367CodeClearButton")
        # reselect ordering facility
        # Quick Code Search By Number
        self.setValueForHTMLElement_web(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                        NBSConstants.TYPE_CODE_ID,
                                        "NBS_LAB367Text",
                                        "2")
        # Quick Code Search Button
        self.performButtonClick_web(NBSConstants.TYPE_CODE_ID, "NBS_LAB367CodeLookupButton")
        # clear selected ordering facility
        self.performButtonClick_web(NBSConstants.TYPE_CODE_ID, "NBS_LAB367CodeClearButton")
        # Quick Code Search By Search Button
        self.getOrganizationDetails(random_number_for_patient, path_id="NBS_LAB367Icon")

        # reselect ordering Provider
        self.performButtonClick_web(NBSConstants.TYPE_CODE_ID, "NBS_LAB366CodeClearButton")
        self.setValueForHTMLElement_web(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                        NBSConstants.TYPE_CODE_ID,
                                        "NBS_LAB366Text",
                                        "2")
        # Quick Code Search Button
        self.performButtonClick_web(NBSConstants.TYPE_CODE_ID, "NBS_LAB366CodeLookupButton")

        # clear selected ordering facility
        self.performButtonClick_web(NBSConstants.TYPE_CODE_ID, "NBS_LAB366CodeClearButton")
        # Quick Code Search By Search Button
        self.getProviderDetails(random_number_for_patient, path_id="NBS_LAB366Icon")

        # shared indicator
        self.performButtonClick_web(NBSConstants.TYPE_CODE_NAME, "pageClientVO.answer(NBS012)")

        # Lab Report Date
        if self.checkDateIsExisted(NBSConstants.TYPE_CODE_ID, "NBS_LAB197"):
            selected_birth_day, selected_birth_month, selected_birth_year = NBSUtilities.getRandomDateByVars()
            self.selectCalendarDate(NBSConstants.TYPE_CODE_ID,
                                    "NBS_LAB197Icon",
                                    str(int(selected_birth_day)),
                                    selected_birth_month,
                                    str(int(selected_birth_year)))

        # Date Received By Public Health, set as current date
        self.selectCalendarDate(NBSConstants.TYPE_CODE_ID,
                                "NBS_LAB201Icon")
        # Pregnancy Validation
        element = self.findElement(NBSConstants.TYPE_CODE_ID, "INV178")
        if element.is_enabled() and element.get_attribute("value") is not None:
            self.selectDropDownOptionValueRandom("INV178")
            element1 = self.findElement(NBSConstants.TYPE_CODE_ID, "INV178")
            if element1.get_attribute("value") == "Y":
                # Pregnancy weeks
                self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                            NBSConstants.TYPE_CODE_ID,
                                            "NBS128",
                                            "10")

        # Test Results For Lab
        # set Ordered Test
        self.setOrderedTestDetails(random_number_for_patient, edit_lab="N")
        # set Resulted Test
        self.setResultedTestDetails()
        # Lab Report Comments
        self.setValueForHTMLElement_web(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                        NBSConstants.TYPE_CODE_NAME,
                                        "pageClientVO.answer(NBS460)",
                                        "Comments:".__add__(NBSUtilities.generate_random_string(60)))
        # update Lab Report by clicking Submit
        self.performButtonClick_web(NBSConstants.TYPE_CODE_ID, "SubmitBottom")

        return True

    """def addSusceptTrackingRecord(self, path_id=None):
        # Add Susceptibility Tracking Records to Resulted Test
        if path_id is not None:
            self.performButtonClick(NBSConstants.TYPE_CODE_ID, path_id)
            original_window = self.driver.current_window_handle
            try:
                WebDriverWait(self.driver, 10).until(EC.number_of_windows_to_be(2))
                for window_handle in self.driver.window_handles:
                    if window_handle != original_window:
                        self.driver.switch_to.window(window_handle)
                        self.manageSusceptTrackingRecord()
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
            except Exception as exc:
                self.logger.exception(msg="exception while adding susceptibility record")
                pass
            return True

    def manageSusceptTrackingRecord(self):
        try:
            self.logger.info("Patient Profile Lab Report::manageSusceptTracking")
            self.selectDropDownOptionValueRandom("NBS_LAB331")
            el = self.getSelectedOptionForSingleSelect(NBSConstants.TYPE_CODE_XPATH, "//*[@id='NBS_LAB331']")
            if el is not None:
                # self.logger.info("el value:::" + str(el))
                if el == "N":
                    # If an isolate wasn't received at the state public health lab, what is the reason
                    self.selectDropDownOptionValueRandom("NBS_LAB332")
                    self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                    NBSConstants.WAITING_TIME_IN_SECONDS)
                elif el == "Y":
                    # If Yes, please specify date received in state public health lab
                    self.selectCalendarDateForNPopups(NBSConstants.TYPE_CODE_ID, "NBS_LAB334Icon")
                    # State public health lab isolate id number
                    self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                    NBSConstants.WAITING_TIME_IN_SECONDS)
                    self.setValueForHTMLElement_web(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                                    NBSConstants.TYPE_CODE_ID,
                                                    "NBS_LAB335",
                                                    NBSUtilities.generate_random_int(7))
                    # Was case confirmed at state public health lab
                    self.selectDropDownOptionValueRandom("NBS_LAB336")
                    self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                    NBSConstants.WAITING_TIME_IN_SECONDS)

            ################################################################################
            # Was specimen or isolate forwarded to CDC for testing or confirmation?
            self.selectDropDownOptionValueRandom("NBS_LAB363")
            ################################################################################
            # PulseNet Isolate?
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, 2)
            self.selectDropDownOptionValueRandom("NBS_LAB337")
            elem2 = self.getSelectedOptionForSingleSelect(NBSConstants.TYPE_CODE_XPATH,
                                                          "//*[@id='NBS_LAB337']")
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
            # self.logger.info("elem2 value:::" + str(elem2))
            if elem2 is not None and elem2 == "Y":
                # Has isolate PFGE pattern been sent to central PulseNet database
                self.selectDropDownOptionValueRandom("NBS_LAB338")
                elem3 = self.getSelectedOptionForSingleSelect(NBSConstants.TYPE_CODE_XPATH,
                                                              "//*[@id='NBS_LAB338']")
                self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
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
            #############################################################################################
            # NARMS Isolate
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, 2)
            self.selectDropDownOptionValueRandom("NBS_LAB345")
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
            iso_element = self.getSelectedOptionForSingleSelect(NBSConstants.TYPE_CODE_XPATH,
                                                                "//*[@id='NBS_LAB345']")
            # self.logger.info("iso_element value:::" + str(iso_element))
            if iso_element is not None and iso_element == "Y":
                # Has isolate been sent to NARMS
                self.selectDropDownOptionValueRandom("NBS_LAB346")
                narms_element = self.getSelectedOptionForSingleSelect(NBSConstants.TYPE_CODE_XPATH,
                                                                      "//*[@id='NBS_LAB346']")
                self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
                # self.logger.info("narms_element value:::" + str(narms_element))
                if narms_element is not None:
                    if narms_element == "N":
                        # If an isolate was not sent to NARMS, what is the reason
                        self.selectDropDownOptionValueRandom("NBS_LAB347")
                        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                        NBSConstants.WAITING_TIME_IN_SECONDS)
                    elif narms_element == "Y":
                        # State-assigned NARMS ID number
                        self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                                    NBSConstants.TYPE_CODE_ID,
                                                    "NBS_LAB348",
                                                    NBSUtilities.generate_random_int(10))

                        # self.logger.info("Expected Ship Date")
                        # Expected Ship Date

                        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                        NBSConstants.WAITING_TIME_IN_SECONDS)
                        self.selectCalendarDateForNPopups(NBSConstants.TYPE_CODE_ID,
                                                          "NBS_LAB349Icon")
                        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                        NBSConstants.WAITING_TIME_IN_SECONDS)
                        self.selectCalendarDateForNPopups(NBSConstants.TYPE_CODE_ID,
                                                          "NBS_LAB350Icon")

            #############################################################################################

            # EIP Isolate
            # self.logger.info("starts eip isolate")
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, 2)
            self.selectDropDownOptionValueRandom("NBS_LAB351")
            eip_element = self.getSelectedOptionForSingleSelect(NBSConstants.TYPE_CODE_XPATH,
                                                                "//*[@id='NBS_LAB351']")
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
            if eip_element is not None:
                # self.logger.info("eip_element value:::" + str(eip_element))
                if eip_element == "Y":
                    self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                    NBSConstants.WAITING_TIME_IN_SECONDS)
                    # Is this specimen available for further EIP testing?
                    self.selectDropDownOptionValueRandom("NBS_LAB352")
                    eip_testing = self.getSelectedOptionForSingleSelect(NBSConstants.TYPE_CODE_XPATH,
                                                                        "//*[@id='NBS_LAB352']")
                    self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                    NBSConstants.WAITING_TIME_IN_SECONDS)
                    # self.logger.info("eip_testing value:::" + str(eip_testing))
                    if eip_testing is not None and eip_testing == "ISOLATNO":
                        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                        NBSConstants.WAITING_TIME_IN_SECONDS)
                        # If a specimen is not available for further EIP testing, what is the reason
                        self.selectDropDownOptionValueRandom("NBS_LAB353")
                        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                        NBSConstants.WAITING_TIME_IN_SECONDS)
                        # If Yes, where will the specimen be shipped?
                        self.selectDropDownOptionValueRandom("NBS_LAB355")
                        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                        NBSConstants.WAITING_TIME_IN_SECONDS)
                        # Expected Ship Date
                        self.selectCalendarDateForNPopups(NBSConstants.TYPE_CODE_ID,
                                                          "NBS_LAB356Icon")
                        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                        NBSConstants.WAITING_TIME_IN_SECONDS)
                        # Actual Ship Date
                        self.selectCalendarDateForNPopups(NBSConstants.TYPE_CODE_ID,
                                                          "NBS_LAB357Icon")
                        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                        NBSConstants.WAITING_TIME_IN_SECONDS)
                        # Was specimen requested for reshipment
                        self.selectDropDownOptionValueRandom("NBS_LAB358")
                        reship_element = self.getSelectedOptionForSingleSelect(NBSConstants.TYPE_CODE_XPATH,
                                                                               "//*[@id='NBS_LAB358']")
                        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                        NBSConstants.WAITING_TIME_IN_SECONDS)
                        # self.logger.info("reship_element value:::" + str(reship_element))
                        if reship_element is not None and reship_element == "Y":
                            #  a specimen was requested for reshipment for further EIP testing,
                            #  what is the reason
                            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                            NBSConstants.WAITING_TIME_IN_SECONDS)
                            self.selectDropDownOptionValueRandom("NBS_LAB359")
                            #  Expected Reship Date
                            self.selectCalendarDateForNPopups(NBSConstants.TYPE_CODE_ID,
                                                              "NBS_LAB361Icon")
                            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                            NBSConstants.WAITING_TIME_IN_SECONDS)
                            #  Actual Reship Date
                            self.selectCalendarDateForNPopups(NBSConstants.TYPE_CODE_ID,
                                                              "NBS_LAB362Icon")
                    elif eip_testing is not None and eip_testing == "N":
                        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                        NBSConstants.WAITING_TIME_IN_SECONDS)
                        # If a specimen is not available for further EIP testing, what is the reason?
                        self.selectDropDownOptionValueRandom("NBS_LAB353")
                    elif eip_testing is not None and eip_testing == "Y":
                        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                        NBSConstants.WAITING_TIME_IN_SECONDS)
                        # if Yes, where will the specimen be shipped?
                        self.selectDropDownOptionValueRandom("NBS_LAB355")
                        # Expected Ship Date
                       
                        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                        NBSConstants.WAITING_TIME_IN_SECONDS)
                        self.selectCalendarDateForNPopups(NBSConstants.TYPE_CODE_ID,
                                                          "NBS_LAB356Icon")
                        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                        NBSConstants.WAITING_TIME_IN_SECONDS)
                        # Actual Ship Date
                        self.selectCalendarDateForNPopups(NBSConstants.TYPE_CODE_ID,
                                                          "NBS_LAB357Icon")
                        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                        NBSConstants.WAITING_TIME_IN_SECONDS)
                        # Was specimen requested for reshipment
                        self.selectDropDownOptionValueRandom("NBS_LAB358")
                        reship_element = self.getSelectedOptionForSingleSelect(NBSConstants.TYPE_CODE_XPATH,
                                                                               "//*[@id='NBS_LAB358']")
                        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                        NBSConstants.WAITING_TIME_IN_SECONDS)
                        if reship_element is not None and reship_element == "Y":
                            #  a specimen was requested for reshipment for further EIP testing,
                            #  what is the reason
                            self.selectDropDownOptionValueRandom("NBS_LAB359")
                            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                            NBSConstants.WAITING_TIME_IN_SECONDS)
                            #  Expected Reship Date
                            self.selectCalendarDateForNPopups(NBSConstants.TYPE_CODE_ID,
                                                              "NBS_LAB361Icon")
                            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                            NBSConstants.WAITING_TIME_IN_SECONDS)
                            #  Actual Reship Date
                            self.selectCalendarDateForNPopups(NBSConstants.TYPE_CODE_ID,
                                                              "NBS_LAB362Icon")

        except Exception as exc:
            self.logger.exception("Exception raised in manage suscept tracking in patient profile")
            pass"""

    def markAsReviewedButtonForLab(self):
        try:
            mark_reviewed_ele = None
            program_area_code = self.findElement_web(NBSConstants.TYPE_CODE_XPATH, "//*[@id='INV108']")
            program_area_code_val = program_area_code.get_attribute("innerHTML").strip()

            self.logger.info("program_area_code_val:::" + str(program_area_code_val))
            try:
                mark_reviewed_ele = self.findElement(NBSConstants.TYPE_CODE_NAME, "markReviewd")
            except:
                mark_reviewed_ele = None
            # self.logger.info(mark_reviewed_ele)
            if mark_reviewed_ele is not None and mark_reviewed_ele.is_displayed():
                mark_reviewed_ele.click()
                self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
                # check Program Area code, if it is STD, needs to populate processing decision popup.
                if program_area_code_val == "HIV" or program_area_code_val == "STD":
                    self.processDecisionForMark()
                self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
                message = self.findElement_web(NBSConstants.TYPE_CODE_XPATH, "//*[@id='successMessages']")
                if message is not None:
                    return "success"
            else:
                return "done"

        except:
            self.logger.info("Exception in Mark As Reviewed")
            return "failed"

    def transferOwnerShipButtonForLab(self):
        # transfer ownership button
        self.performButtonClick_web(NBSConstants.TYPE_CODE_NAME, "TransferOwn")

        # randon select program area on transfer ownership form
        self.selectDropDownOptionValueRandom("orderedTest.theObservationDT.progAreaCd")
        selected_pa_code = self.getSelectedVisibleTextForSingleSelect(NBSConstants.TYPE_CODE_ID,
                                                                      "orderedTest.theObservationDT.progAreaCd")
        # self.logger.info(selected_pa_code)
        # random select jurisdiction
        self.selectDropDownOptionValueRandom("orderedTest.theObservationDT.jurisdictionCd")

        selected_jd_code = self.getSelectedVisibleTextForSingleSelect(NBSConstants.TYPE_CODE_ID,
                                                                      "orderedTest.theObservationDT.jurisdictionCd")
        # self.logger.info(selected_jd_code)
        # submit button
        self.performButtonClick_web(NBSConstants.TYPE_CODE_ID, "Submit")

        # Check Success Message on the success page
        success_pa_code = self.findElement_web(NBSConstants.TYPE_CODE_XPATH, "//*[@id='1']/tbody/tr/td/table/tbody"
                                                                             "/tr[3]/td/table/tbody/tr[11]/td["
                                                                             "2]/table/tbody/tr/td/span")
        success_pa_code_val = success_pa_code.get_attribute("innerHTML").replace('&nbsp;', '')
        # self.logger.info(success_pa_code_val)
        success_jd_code = self.findElement_web(NBSConstants.TYPE_CODE_XPATH, "//*[@id='1']/tbody/tr/td/table/tbody/tr["
                                                                             "3]/td/table/tbody/tr[17]/td["
                                                                             "2]/table/tbody/tr/td/span")
        success_jd_code_val = success_jd_code.get_attribute("innerHTML")
        # self.logger.info(success_jd_code_val)

        if (selected_pa_code is not None and selected_jd_code is not None and success_pa_code is not None and
                success_jd_code is not None):
            if (selected_pa_code.strip() == success_pa_code_val.strip()
                    and selected_jd_code.strip() == success_jd_code_val.strip()):
                # on the confirmation page, click on ViewFIle link to go to patient profile
                self.performButtonClick_web(NBSConstants.TYPE_CODE_LINK_TEXT, "View File")
                return True
        else:
            self.performButtonClick_web(NBSConstants.TYPE_CODE_LINK_TEXT, "View File")
            return False

    def verifyPrintPopupOnLabReport(self):
        print_popup = False
        # print button on Lab Report
        self.performButtonClick(NBSConstants.TYPE_CODE_XPATH,
                                "//*[@id='doc3']/form/div[2]/table/tbody/tr/td[2]/input[5]")
        # get original window reference
        original_window = self.driver.current_window_handle
        try:
            WebDriverWait(self.driver, 10).until(EC.number_of_windows_to_be(2))
            for window_handle in self.driver.window_handles:
                if window_handle != original_window:
                    self.driver.switch_to.window(window_handle)
                    print_popup = True
                    break
        except Exception as exc:
            self.logger.exception("Exception While Opening Print Popup",
                                  exc_info=(type(exc), exc, exc.__traceback__))
        if print_popup:
            # close the print popup
            self.driver.close()
            self.driver.switch_to.window(original_window)
        return True

    def deleteLabReport(self):
        try:
            self.performButtonClick_web(NBSConstants.TYPE_CODE_XPATH,
                                        "//*[@id='doc3']/form/div[2]/table/tbody/tr/td[2]/input[2]")
            WebDriverWait(self.driver, 10).until(EC.alert_is_present())
            alert = self.driver.switch_to.alert
            alert.accept()
        except:
            self.logger.exception("Exception while Deleting the Lab Report")
            return False
        return True

    def processDecisionForMark(self):
        # get original window reference
        original_window = self.driver.current_window_handle
        try:
            WebDriverWait(self.driver, 10).until(EC.number_of_windows_to_be(2))
            for window_handle in self.driver.window_handles:
                if window_handle != original_window:
                    self.driver.switch_to.window(window_handle)
                    # populate processing decision
                    self.selectDropDownOptionValueRandom("reviewReason")
                    self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                    NBSConstants.WAITING_TIME_IN_SECONDS)
                    self.performButtonClick_web(NBSConstants.TYPE_CODE_XPATH,
                                                "//*[@id='botMarkAsReviewedId']/input[1]")
                    self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                    NBSConstants.WAITING_TIME_IN_SECONDS)
                    self.driver.switch_to.window(original_window)
        except:
            self.logger.info("Exception While Opening Mark As Reviewed POPUP For HIV")
            self.driver.switch_to.window(original_window)

    def findSpecificLabRecord(self, observation_uid):
        self.logger.info("observation_uid::::::"+str(observation_uid))
        # click on events tab
        self.performButtonClick_web(NBSConstants.TYPE_CODE_ID, "tabs0head1")
        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
        # search observation uid, in the event lab table
        elements = self.findElements(NBSConstants.TYPE_CODE_XPATH,
                                     "//a[contains(@href, "
                                     "'observationUID=".__add__(
                                         str(observation_uid)) + "')]")
        self.logger.info(len(elements))
        if elements is not None and len(elements) > 0:
            elements[-1].click()

    def deleteResultedTestRecord(self):
        try:
            self.performButtonClick_web(NBSConstants.TYPE_CODE_XPATH,
                                        "//*[@id='questionbodyRESULTED_TEST_CONTAINER']/tr[2]/td[4]/input")
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)

            WebDriverWait(self.driver, 10).until(EC.alert_is_present())
            alert = self.driver.switch_to.alert
            alert.accept()
        except:
            self.logger.exception(msg="exception while deleting resulted test record")
