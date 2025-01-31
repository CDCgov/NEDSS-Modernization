import time
import random
import re
import pytest
from datetime import datetime, timedelta
from selenium.webdriver.common.by import By
from selenium.webdriver.common.action_chains import ActionChains
from src.page_objects.data_entry.labreport import LabReportObject
from src.page_objects.data_entry.std_investigation import stdInvestigation
from src.page_objects.data_entry.vaccination import Vaccination
from src.page_objects.patient_profile.labreport import PatientProfileLRObject
from src.utilities.properties import Properties
from selenium.webdriver.support.select import Select
from src.page_objects.data_entry.patient import PatientObject
from src.page_objects.login.login import LoginPage

from selenium.common.exceptions import NoSuchElementException
from src.utilities.constants import NBSConstants
from src.utilities.db_utilities import DBUtilities
from src.utilities.log_config import LogUtils
from src.utilities.nbs_utilities import NBSUtilities
from src.page_objects.BasePageObject import BasePageObject


class system_identified_merge(BasePageObject):
    logger = LogUtils.loggen(__name__)
    testcase_start_time = None
    
    
    def __init__(self, driver):
        super().__init__(driver)
        self.driver = driver
        self.std_investigation = stdInvestigation(self.driver)
        self.LabReportObject = LabReportObject(self.driver)
        self.vaccine = Vaccination(self.driver)
    # locators and initializers 
    Jenkins_Similiar_value = 'similiar'
    Jenkins_Same_value = 'same'
    LASTNAME_SIMILIAR = 'dedupsimiliar'
    LASTNAME_SAME = 'dedupsame'
    MERGE_PATIENT = "Merge Patients"
    SYSTEM_IDENTIFIED = "System Identified"
    EXIT_MERGE_XPATH = '//*[@value="Exit Merge"]'
    PATIENT_SEARCH_LINK_XPATH= '//*[@id="dd-handle-0_0a"]/h2'
    SKIP_XPATH = '//*[@value="Skip"]'
    NO_MERGE_XPATH = '//*[@value="No Merge"]'
    SEARCH_XPATH = '//*[@value="Search"]'
    HOME_LINK_XPATH = '//*[@id="bd"]/table[1]/tbody/tr/td[1]/table/tbody/tr/td[1]/a'
    SEARCH_RESULT_TABLE_XPATH='//*[@id="searchResultsTable"]/tbody/tr/td[{0}]'
    PATIENT_SEARCH_INPUT_ID = 'DEM229'
    DELETE_BUTTON_XPATH = '//*[@id="bd"]/div[3]/table/tbody/tr/td/input[1]'
    text_filed_search_cancel_button = '//*[@id="searchResultsTable"]/thead/tr/th[{0}]/div/label[1]/input[2]'
    text_filed_search_text = "//*[@id='SearchText{0}' and @name='answerArrayText(SearchText{0})']"
    text_filed_search_ok_button = '//*[@id="searchResultsTable"]/thead/tr/th[{0}]/div/label[1]/input[1]'
    dob="03/03/1950"
    PATIENT_ID_LINK_XPATH = '//*[@id="searchResultsTable"]/tbody/tr[1]/td[4]/a'
    CREATED_PATIENT_ID = '//*[@id="bd"]/table[2]/tbody/tr/td[2]/span[2]'
    PATIENT_SUFFIX_XPATH = '//*[@id="name_subsection"]/tbody/tr[4]/td[2]/input'
    PATIENT_SUFFIX_VALUE = 'II / The Second' 
    SEX_XPATH = '//*[@id="otherDetails_subsection"]/tbody/tr[{i}]/td[2]/input'
    SEX = 'Female'
    PATIENT_RACE_XPATH = '//*[@id="raceSubsection"]/tbody/tr[1]/td[2]/input'
    PATIENT_RACE_VALUE = 'Not Hispanic or Latino'
    MERGE_ID_CHECKBOX_XPATH = '// *[ @ id = "searchResultsTable"] / tbody / tr[{0}] / td[{1}] / input'
    PATIENT_COUNT_XPATH = '// *[ @ id = "searchResultsTable"] / tbody / tr'
    MERGE_RESULTS_XPATH = '//*[@id="systemIdentifiedPage"]/div[2]'
    PRINT_XPATH = '//input[contains(@value,"Print")]'
    DOWNLOAD_XPATH = '//input[contains(@value,"Download")]' 
    MERGE = 'Merge'
    PATIENT_DOB_ID = 'patientDOB'
    random_number = datetime.now().strftime("%d%m%y%H%M%S")
    CONDITION = 'conditionCd'
    JURISDICTION = 'morbidityReport.theObservationDT.jurisdictionCd'
    REPORT_TYPE = 'morbidityProxy.observationVO_s[0].obsValueCodedDT_s[0].code'
    MORBIDITY_REPORT_DATE = 'morbidityReport.theObservationDT.activityToTime_s'
    REPORTING_FACILITY = 'entity-codeLookupText-Org-ReportingOrganizationUID'
    CODE_LOOKUP_BUTTON = '//*[@id="entity-table-Org-ReportingOrganizationUID"]/thead/tr/td[2]/input[2]'
   
    

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
 
    def validateSortingOnResultsGrid(self):
        # validate columns sort for results grid column name --default sort
        grid_columns = {
            self.column2_name: [self.column2_default_sort, self.column2_xpath],
            self.column3_name: [self.column3_default_sort, self.column3_xpath],
            self.column4_name: [self.column4_default_sort, self.column4_xpath],
            self.column5_name: [self.column5_default_sort, self.column5_xpath],
            self.column6_name: [self.column6_default_sort, self.column6_xpath],
        }
        time.sleep(2)
        self.sortResultsGridColumnsOneByOne(grid_columns, "Desc")

        return True
    
    
    def switch_to_current_window(self,input):
        current_window = self.driver.window_handles[input]
        self.driver.switch_to.window(current_window)
        return True
        
    
    def validatePatientLInkInTheResultsPage(self):
        try:
            self.performButtonClick(NBSConstants.TYPE_CODE_XPATH, '//*[@id="searchResultsTable"]/tbody/tr[1]/td[3]/a')
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
            self.switch_to_current_window(1)
            self.driver.close()
            self.switch_to_current_window(0)
        except:
            return False
        return True
    
    
    def validateRefineYourSearchLink(self):
        try:
            self.performActionMouseClick(NBSConstants.TYPE_CODE_XPATH, '// *[ @ id = "bd"] / div[4] / a')
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
            self.performButtonClick(NBSConstants.TYPE_CODE_NAME, "Submit")
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
            time.sleep(1)
            # click on Remove All Filter/Sorts
            self.driver.find_element(By.LINK_TEXT, 'Remove All Filters/Sorts').click()
            time.sleep(1)

    def patMergePerformColumnTextFieldPopupSearch(self, component_name, key, value_list):
        if component_name == "OPENINV":
            # print("value_list",value_list)
            if self.patMergeTextFieldFilterOnTheResultsGrid(key, value_list[2],value_list[4]):
                self.logger.info(str(key) + " Results Grid Filter Test Passed")

    def patMergeTextFieldFilterOnTheResultsGrid(self, key, column_name,column_index):
        search_text_element = self.text_filed_search_text.format(column_name)
       
        search_text_ok_button = self.text_filed_search_ok_button.format(column_index)
        
        self.setValueForHTMLElement("TEXTBOX", NBSConstants.TYPE_CODE_XPATH,
                                    search_text_element, "xyz")
        self.performButtonClick(NBSConstants.TYPE_CODE_XPATH, search_text_ok_button)
        return True

    def getSearchText(self, key):
        search_text = None
        if key == "Patient":
            search_text = (
                self.findElement(NBSConstants.TYPE_CODE_XPATH, self.PATIENT_ID_LINK_XPATH)
                .get_attribute('text'))
        # self.logger.info(search_text)

        if search_text is not None:
            return search_text
        else:
            return ""

    
    def add_new_patient(self, setup,jen_val,name,param):
        random_number_for_patient = datetime.now().strftime("%m%d%y")
        system_identified_merge.testcase_start_time = int(time.time() * 1000)
        self.driver = setup
        self.patient = PatientObject(self.driver)
        patientIdList = []
        namelist = []
        if NBSUtilities.performLoginValidation(self.driver):  # Login Validation

            for i in range(0, 2):
                self.navigateToPatientSearch(self.driver, self.patient, True,random_number_for_patient)
                countPatient=i
                if self.addNewPatient(random_number_for_patient,name,countPatient,param):
                    time.sleep(1)
                    self.logger.info("*test_add_new_patient:NBS Add New Patient Created Test PASSED*")
                    assert True
                time.sleep(1)
                val = self.driver.find_element(By.XPATH,self.CREATED_PATIENT_ID).text
                patientIdList.append(val)
                
        self.jenkinsBatch(jen_val)
        time.sleep(5)
        return patientIdList
    
    
    def setSysIdentiMergeNameSection(self, random_number,name):
        base = BasePageObject(self.driver)
        try:
            # Last Name
            base.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                        NBSConstants.TYPE_CODE_NAME,
                                        PatientObject.add_patient_last_name,
                                        name.__add__("-").__add__(str(random_number)))
            # First Name
            base.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                        NBSConstants.TYPE_CODE_NAME,
                                        PatientObject.add_patient_first_name,
                                        NBSConstants.SYSTEM_MERGE_FIRST_NAME)
            # Middle Name
            base.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                        NBSConstants.TYPE_CODE_NAME,
                                        PatientObject.add_patient_middle_name,
                                        NBSConstants.SYSTEM_MERGE_MIDDLE_NAME)
            # Patient Suffix
            
            self.driver.find_element(By.XPATH,self.PATIENT_SUFFIX_XPATH).click
            self.driver.find_element(By.XPATH,self.PATIENT_SUFFIX_XPATH).send_keys(self.PATIENT_SUFFIX_VALUE)
           
        except Exception as exc:
            self.logger.exception(exc, exc_info=(type(exc), exc, exc.__traceback__))
            return False
        return True
    
                        
    def jenkinsBatch(self,Flag):
        print("***********************RUNNING JENKINS*********************")
        server = NBSUtilities.get_jenkins_server()
        if Flag == "similiar":
            job_name = Properties.getJenkinsJobdedupsimilar()
        else:
            job_name = Properties.getJenkinsJobdedupsame()
        node_name = Properties.get_active_environment()
        NBSUtilities.run_jenkins_pipeline(server,job_name,node_name)
             
    def navigateToPatientSearch(self, driver, patient, display_success, random_number):
        self.patient.performButtonClick(NBSConstants.TYPE_CODE_LINK_TEXT,
                                        PatientObject.nbs_data_entry_menu)
        self.patient.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                NBSConstants.WAITING_TIME_IN_SECONDS)
        self.patient.performButtonClick(NBSConstants.TYPE_CODE_LINK_TEXT,
                                        PatientObject.nbs_data_entry_patient_menu)
        self.patient.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                NBSConstants.WAITING_TIME_IN_SECONDS)
        if self.driver.title == PatientObject.patient_search_page_title:
            if display_success:
                self.logger.info("**navigateToPatientSearch:NBS Patient Search Landing Page PASSED**")
                assert True

            self.patient.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT, NBSConstants.TYPE_CODE_NAME,
                                                PatientObject.patient_search_by_last_name,
                                                NBSConstants.SYSTEM_MERGE_LAST_NAME)
            self.patient.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                    NBSConstants.WAITING_TIME_IN_SECONDS)
            self.patient.performButtonClick(NBSConstants.TYPE_CODE_NAME, PatientObject.patient_search_submit_id)
            self.patient.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                                    NBSConstants.WAITING_TIME_IN_SECONDS)
     
    def addNewPatient(self, random_number, name, countPatient, param=True):
        print('countPatient',countPatient)
        base = BasePageObject(self.driver)
        
        # try:
        base.performButtonClick(NBSConstants.TYPE_CODE_NAME,
                                PatientObject.create_new_patient_submit_button)
        base.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                        NBSConstants.WAITING_TIME_IN_SECONDS)
        base.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT, NBSConstants.TYPE_CODE_NAME,
                                        PatientObject.add_patient_geneal_comments,
                                    "Comments:" + NBSUtilities.generate_random_string(100))
        self.setSysIdentiMergeNameSection(random_number,name)
        self.setSysIdentiMergePersonDetails()
        self.setPersonRaceDetails()
        time.sleep(2)
      
        
        # Create Patient
        
        base.performButtonClick(NBSConstants.TYPE_CODE_NAME,
                                PatientObject.create_new_patient_submit_button)
        time.sleep(2)
        
        
        
        if countPatient == 0:
            self.event1()
        else :
            if param == True: 
                self.event2()
            
       
        # except Exception as ex:
        #     self.logger.info(ex)
        #     return False

        # return True
    
    def setSysIdentiMergePersonDetails(self):
        try:
            base = BasePageObject(self.driver)
          
            calendar_button = self.driver.find_element(By.ID, self.PATIENT_DOB_ID)
             
            dob=self.dob
            calendar_button.click()
            calendar_button.clear()
            calendar_button.send_keys(dob)
            base.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
            # current Sex
            self.driver.find_element(By.XPATH,self.SEX_XPATH.format(3)).click()
            
            time.sleep(1)
           
            self.driver.find_element(By.XPATH,self.SEX_XPATH.format(3)).send_keys(self.SEX)
            base.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
            # Birth Sex
            self.driver.find_element(By.XPATH,self.SEX_XPATH.format(4)).click()
            
            time.sleep(1)
           
            self.driver.find_element(By.XPATH,
                                    self.SEX_XPATH.format(4)).send_keys(self.SEX)

        except Exception as exc:
            self.logger.exception(exc, exc_info=(type(exc), exc, exc.__traceback__))
            return False
        return True
    
    # def setSex(self):
    #     element = self.driver.find_element(By.ID, "DEM113")
    #     self.driver.execute_script("arguments[0].setAttribute('class','visible')", element)
    #     select_element = Select(self.driver.find_element(By.ID, "DEM113"))
    #     random_index = random.randint(2, len(select_element.options))
    #     add_org_addr_state_xpath = ("//*[@id='DEM113']/option[".__add__(str(random_index)) + "]")
    #     addr_state_value = (self.driver.find_element(By.XPATH, add_org_addr_state_xpath)
    #                         .get_attribute("value"))
    #     select_element.select_by_value(addr_state_value)
        
    def setPersonRaceDetails(self):
        try:
            # Ethnicity
            self.driver.find_element(By.XPATH,
                                     self.PATIENT_RACE_XPATH).click()
            self.driver.find_element(By.XPATH,
                                     self.PATIENT_RACE_XPATH).send_keys(self.PATIENT_RACE_VALUE)
           # Asian Race
            self.performButtonClick(NBSConstants.TYPE_CODE_NAME, PatientObject.patient_asian_race)
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
                                            NBSConstants.WAITING_TIME_IN_SECONDS)
        except Exception as ex:
            # self.logger.error("Exception While Populating Race Section", ex)
            return False
        return True

  
    def merge_two_ids(self,patID):
        
        time.sleep(1)
        # self.search()
        self.driver.find_element(By.XPATH,self.MERGE_ID_CHECKBOX_XPATH.format(1,1)).click()
        self.driver.find_element(By.XPATH,self.MERGE_ID_CHECKBOX_XPATH.format(1,2)).click()
        self.driver.find_element(By.XPATH,
                                    self.MERGE_ID_CHECKBOX_XPATH.format(2,1)).click()
        self.driver.find_element(By.XPATH,
                                    self.MERGE_ID_CHECKBOX_XPATH.format(2,2)).click()
        self.driver.switch_to.alert.accept()
        time.sleep(2)
        
        self.driver.find_element(By.LINK_TEXT, 'Remove All Filters/Sorts').click()
        time.sleep(2)
        
        countcheck = self.driver.find_elements(By.XPATH,self.PATIENT_COUNT_XPATH)
        self.driver.find_element(By.XPATH,self.MERGE_ID_CHECKBOX_XPATH.format(1,1)).click()
        self.driver.find_element(By.XPATH,self.MERGE_ID_CHECKBOX_XPATH.format(1,2)).click()
        for i in range(2,len(countcheck)+1):
            self.driver.find_element(By.XPATH,self.MERGE_ID_CHECKBOX_XPATH.format(i,1)).click()
            

        self.driver.find_element(By.LINK_TEXT,self.MERGE).click()
        time.sleep(2)
        self.driver.switch_to.alert.accept()
        time.sleep(2)
        element = self.driver.find_element(By.XPATH,self.MERGE_RESULTS_XPATH)
        element = element.text
        result = self.assert_Merge_Validation(element,patID)
        assert result
        
        
    def assert_Merge_Validation(self,element,patID):
     
        numbers = re.findall(r'\b\d{7,}\b', element)
        numbers_list = [int(number) for number in numbers]
        
        patID = [int(item) for item in patID]
        # print(patID,"===>",numbers_list)
        if patID[0] in numbers_list and patID[1] in numbers_list:
            return True
        
        return False
       
        
    # def getPatientCount(self):
    #     s = self.driver.find_element(By.XPATH,' //*[@id="MergeCandidatesBox"]/table/tbody/tr[2]/td/span').text
    #     Count=s.split(":")
    #     expected = Count[1]
    #     return int(expected)

    # def search(self):
    #     base = BasePageObject(self.driver)
    #     base.setValueForHTMLElement("TEXTBOX", NBSConstants.TYPE_CODE_ID,
    #                                 "DEM102", NBSConstants.SYSTEM_MERGE_LAST_NAME)
    #     base.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
    #                                     NBSConstants.WAITING_TIME_IN_SECONDS)
    #     base.performButtonClick(NBSConstants.TYPE_CODE_NAME, "Submit")
    #     base.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME,
    #                                     NBSConstants.WAITING_TIME_IN_SECONDS)
        
    def validatePrint(self):
        try:
            self.driver.find_element(By.XPATH, self.PRINT_XPATH).click()
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
        except:
            return False
        return True

    def validateDownload(self):
        try:
            self.performActionMouseClick(NBSConstants.TYPE_CODE_XPATH, self.DOWNLOAD_XPATH)
            self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
        except:
            return False
        return True
    
    def event1(self):
        print("Inside Event")
        self.performButtonClick(NBSConstants.TYPE_CODE_ID, 'tabs0head1')
        time.sleep(2)
        add_new_btn_xpath = '//*[@id="subsect_Inv"]/table/tbody/tr/td['.__add__(
            str(NBSConstants.ADD_NEW_OPTION)) + ']/input[2]'
        self.driver.find_element(By.XPATH, add_new_btn_xpath).click()
        
         # STD
        time.sleep(1)
        self.driver.find_element(By.NAME, "ccd_textbox").send_keys("Anthrax")
        self.driver.find_element(By.ID, "Submit").click()
        time.sleep(2)
        self.driver.find_element(By.ID, "tabs0head1").click()
        time.sleep(2)
        self.std_investigation.set_jurisdiction()
        self.driver.find_element(By.ID,'SubmitTop').click()
        time.sleep(2)
        self.driver.find_element(By.XPATH, '//*[@id="bd"]/div[1]/a').click()
        
    def setTodayDate(self, type, type_val):
        now = datetime.now()
        today = now.strftime("%m-%d-%Y")
        element = self.driver.find_element(type, type_val)
        element.clear()
        element.send_keys(today)   
         
    def event2(self):
        print("Inside Event2")
        time.sleep(2)
        self.performButtonClick(NBSConstants.TYPE_CODE_ID, 'tabs0head1')
        time.sleep(2)
        add_new_btn_xpath = '//*[@id="subsect_Lab"]/table/tbody/tr/td[3]/input[1]'
        self.driver.find_element(By.XPATH, add_new_btn_xpath).click()
             
        # time.sleep(1)
       
        # Facility and Provider Information
        self.LabReportObject.setFacilityAndProviderSection(self.random_number)
        # Order Details For Lab
        self.LabReportObject.setOrderDetailsForLab()
        # Test Results For Lab
        # set Ordered Test
        # self.LabReportObject.setOrderedTestDetails(self.random_number)
        # set Resulted Test
        self.LabReportObject.setResultedTestDetails()
        # Lab Report Comments

        self.setValueForHTMLElement(NBSConstants.HTML_TEXT_BOX_ELEMENT,
                                    NBSConstants.TYPE_CODE_NAME,
                                    "pageClientVO.answer(NBS460)",
                                    "Comments:".__add__(NBSUtilities.generate_random_string(60)))

        # Create Lab Report by clicking Submit
        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
        element =self.driver.find_elements(By.NAME,"Submit")
        if element:
            element[0].click()
        self.setWaitTimeAfterEachAction(NBSConstants.ENABLE_WAITING_TIME, NBSConstants.WAITING_TIME_IN_SECONDS)
        
        ###########################Vaccination###########################
        self.performButtonClick(NBSConstants.TYPE_CODE_ID, 'tabs0head1')
        time.sleep(2)
        add_new_btn_xpath = '//*[@id="subsect_Vaccination"]/table/tbody/tr/td[3]/input[1]'
        self.driver.find_element(By.XPATH, add_new_btn_xpath).click()
        time.sleep(1)
        self.switch_to_current_window(1)
        self.driver.maximize_window()
        time.sleep(2)
        self.driver.find_element(By.ID, "tabs0head1").click()
        time.sleep(2)
        self.vaccine.set_vaccination_admin()
        time.sleep(2)
        self.vaccine.set_vacc_administered_by_details(self.random_number)
        self.driver.find_element(By.NAME, "Submit").click()
        time.sleep(1)
         ###### close the window
         
        self.switch_to_current_window(1)
        self.driver.find_element(By.NAME, "Cancel").click()
        self.switch_to_current_window(0)
        ############################Morbidity###############################
        self.performButtonClick(NBSConstants.TYPE_CODE_ID, 'tabs0head1')
        self.driver.find_element(By.XPATH, "//*[@id='subsect_Morb']/table/tbody/tr/td[3]/input").click()
        # self.switch_to_current_window(1)
        self.set_and_return_dropdown_value(self.CONDITION)
        self.set_and_return_dropdown_value(self.JURISDICTION)
        # self.set_and_return_dropdown_value(self.REPORT_TYPE)
        self.setTodayDate(By.ID, self.MORBIDITY_REPORT_DATE)
        self.driver.find_element(By.NAME, self.REPORTING_FACILITY).send_keys("2")
        self.driver.find_element(By.XPATH, self.CODE_LOOKUP_BUTTON).click()
        time.sleep(1)
        self.performButtonClick(NBSConstants.TYPE_CODE_NAME, "Submit")
        
        
        
        
        
       
       