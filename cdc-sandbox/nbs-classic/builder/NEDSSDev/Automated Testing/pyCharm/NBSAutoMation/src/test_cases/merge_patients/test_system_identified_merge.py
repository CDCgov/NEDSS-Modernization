########################################################################################################################
# Test Case Name: Test Dedupsimalar and Dedupsame batch process 
# User Story 1 : ND-34381
# ND-34381 -Test Case Overview:
# Create 2 Patients test data based on the same Match criteria 
# Run the DeDuplicationSameBatchProcess.bat file 
# Run the Jenkins pipeline to run this batch file
# Verify in the UI can be validated.
# User Story 2 : ND-34255
# This script performs the Creation of Test Data for System Identified Merge Patients. Below are the list which needs to be verified 
# Below are some of the different Patient Match Criteria Rules to Consider for automation system identified Merge Patient functionality.
# Create at least 2 patients for each of the below patient match criteria rules. Proper naming convention should be used so that to verify the created test data are grouped properly.
# Patient 1 Data = Patient 2 Data
# Patient Name & DOB same & Rest of the Data Different (Create Similar one Patient2)
# verify MPR Changes for First Name and Last Name (Patient 1 Data = Patient 2 Data with MPR Changes)
# ND-34258
###### Results Grid Verification
# Verify by default the Results Grid is displayed in ascending by Patient ID (Check the icon A/Z)
# Note the ‘Number of groups remaining to view for merge: ‘X’
# Click the button ‘Skip.’
# Check Skip Merge Functionality
# Check Sorting on All Columns
# Check Filter on All Columns
# Check Print Functionality
# Check Download Functionality
# Skip all the queues until you find pagination and click on the pagination links to make sure it isn’t broken.
# Click on the Patient ID in one of the row and verify the Patient File is opened in a new tab. Close the tab and back to the main window
####### Merge Functionality Verification
# Select 2 Patients and 2 Surviving ID and Verify the Pop up message to select only one Surviving Patient ID is selected
# Without selecting any record, click on the ‘Merge’ button and check for the warning message to select 2 or more patients
# Select 2 Patients including the ‘Surviving ID’ and click on Merge. Verify the Merge with Surviving Patient and verify the Merged patients are removed from the list
# Select 2 Patients without the ‘Surviving ID’ and click on Merge. Verify the default Surviving Patient when no patient is selected as Surviving ID and verify the Merged patients are removed from the list
# Select 3 or more patients and Merge
# Verify No Merge functionality
##########################################################################################################################################
import os
import time
import random
import pytest
from selenium.webdriver.common.by import By
from datetime import datetime, timedelta
from src.page_objects.merge_patients.system_identified_merge import system_identified_merge
from src.utilities.db_utilities import DBUtilities
from src.utilities.log_config import LogUtils



class TestSystemIdentified():
    random_number_for_patient = datetime.now().strftime("%m%d%y%H%M%S")
    random_number_for_patient_extended = None
    logger = LogUtils.loggen(__name__)
    patient_uid = None
    patient_uid_extended = None
    
    db_utilities = DBUtilities()
    
    # ND-34258 - Automate System Identified Merge Patients - Results Grid Verification - Merge Multiple Patients
    # ND-34255 - Automate System Identified Merge Patients – Test Data Creation
    def test_sys_identi_merge(self, setup):
        try:
            self.driver = setup
            systemObj = system_identified_merge(self.driver)
            patientIdList = systemObj.add_new_patient(setup,systemObj.Jenkins_Similiar_value,systemObj.LASTNAME_SIMILIAR,param=True)
            time.sleep(5)
            self.driver.find_element(By.LINK_TEXT,systemObj.MERGE_PATIENT).click()
            self.driver.find_element(By.LINK_TEXT,systemObj.SYSTEM_IDENTIFIED).click()
            self.driver.find_element(By.XPATH,systemObj.EXIT_MERGE_XPATH).click()
            actual = self.driver.find_element(By.XPATH,systemObj.PATIENT_SEARCH_LINK_XPATH).text 
            expected = "Patient Search"
            assert expected == actual
            
            # validate patient merge
            self.driver.find_element(By.LINK_TEXT,systemObj.MERGE_PATIENT).click()
            self.driver.find_element(By.LINK_TEXT,systemObj.SYSTEM_IDENTIFIED).click()
            time.sleep(5)
            STOP=True
            while(STOP):
                
                ID = self.driver.find_elements(By.XPATH,systemObj.SEARCH_RESULT_TABLE_XPATH.format(3))
                patID= []
                for i in ID:
                    patID.append(i.text)
               
                # print(patientIdList ,"===>",patID)            
                if patientIdList[0] in patID:
                    STOP=False
                
                if (STOP):
                    self.driver.find_element(By.XPATH,systemObj.SKIP_XPATH).click()
                time.sleep(2)
                
            # validate sorting results grid
            if systemObj.validateSortingOnResultsGrid():
                self.logger.info("*sys_identi_merge:Sorting on All Columns on the Results "
                                "Grid PASSED*")
                assert True
            else:
                self.logger.info("*sys_identi_merge:Sorting on one or multiple Columns "
                                    " on the Results Grid Failed**")
                
                
            # validate patient link of the first record in the results grid
            if systemObj.validatePatientLInkInTheResultsPage():
                self.logger.info("*sys_identi_merge:Patient Link on the Results Grid Test "
                                "PASSED*")
                assert True
            else:
                self.logger.info("*sys_identi_merge:Patient Link on the Results Grid Test "
                                "Failed*")
                
                
            # validate RefineYourSearchLink
            if systemObj.validateRefineYourSearchLink():
                self.logger.info("*sys_identi_merge:Filter Data on All Columns on the "
                                "Results Grid PASSED*")
                assert True
            # else:
                self.logger.info("*sys_identi_merge:Filter Data on one or multiple Columns"
                                " on the Results Grid Failed**")
                
                
            # validate filter on the results Grid.
            if systemObj.validateFilterOnResultsGrid():
                self.logger.info("*sys_identi_merge:Filter Data on All Columns on the "
                                "Results Grid PASSED*")
                assert True
            else:
                self.logger.info("*sys_identi_merge:Filter Data on one or multiple Columns"
                                " on the Results Grid Failed**")  
            # validate validatePrint
            if systemObj.validatePrint():
                self.logger.info("*sys_identi_merge:Filter Data on All Columns on the "
                                "Results Grid PASSED*")
                assert True
            else:
                self.logger.info("*sys_identi_merge:Filter Data on one or multiple Columns"
                                " on the Results Grid Failed**")
            time.sleep(1)

            # validate validateDownload
            if systemObj.validateDownload():
                self.logger.info("*sys_identi_merge:Filter Data on All Columns on the "
                                "Results Grid PASSED*")
                assert True
            else:
                self.logger.info("*sys_identi_merge:Filter Data on one or multiple Columns"
                                " on the Results Grid Failed**")
            
            #Getting the Merge of the first patient ID
            First_patient_ID = self.driver.find_element(By.XPATH,'//*[@id="searchResultsTable"]/tbody/tr[1]/td[3]/a').text
            print("First Mereg Patient Id is : ",First_patient_ID)
            systemObj.merge_two_ids(patientIdList)
            self.driver.find_element(By.XPATH,systemObj.NO_MERGE_XPATH).click()
            self.driver.find_element(By.XPATH,systemObj.EXIT_MERGE_XPATH).click()
            
            self.driver.find_element(By.ID,systemObj.PATIENT_SEARCH_INPUT_ID).send_keys(First_patient_ID)
            time.sleep(1)
            self.driver.find_element(By.XPATH,systemObj.SEARCH_XPATH).click()
                        
            systemObj.validatePatientLInkInTheResultsPage()
            time.sleep(2)
            self.driver.find_element(By.XPATH, '//*[@id="searchResultsTable"]/tbody/tr/td[1]/a').click()
            self.driver.find_element(By.ID, 'tabs0head1').click()
            time.sleep(2)
                      
            labassert = self.driver.find_element(By.XPATH,'//*[@id="subsect_Lab"]/table/tbody/tr/td[2]').text
            mobassert = self.driver.find_element(By.XPATH,'//*[@id="subsect_Morb"]/table/tbody/tr/td[2]').text
            
            print(labassert,"++",mobassert)
            
            
            numbers = ''.join([char for char in labassert if char.isdigit()])
            
            resultlab = int(numbers)
            assert resultlab > 0 
            
            numbers = ''.join([char for char in mobassert if char.isdigit()])
            resultmob = int(numbers)
            assert resultmob > 0 
            
       
        except Exception as e:
            self.logger.info(f"SystemIdentified Merge Test Failed with {str(e)}")
            self.driver.save_screenshot(
            os.path.abspath(os.curdir) + "\\screenshots\\" + "test_share_document.png")
            # self.driver.close()
            pytest.fail(f"SystemIdentified Merge test FAILED")
            
        #ND-34381-Automate Same Match Criteria - Auto Merge Patient Records
    def test_dedupsame(self,setup):
        try:
            self.driver = setup
            systemObj = system_identified_merge(self.driver)
            #code for dedupsame batch process
            patientIdList = systemObj.add_new_patient(setup,systemObj.Jenkins_Same_value,systemObj.LASTNAME_SAME,param=False)
            time.sleep(5)
            self.driver.find_element(By.XPATH,systemObj.HOME_LINK_XPATH).click()
            
            self.driver.find_element(By.ID,systemObj.PATIENT_SEARCH_INPUT_ID).send_keys(patientIdList[0])
            time.sleep(1)
            self.driver.find_element(By.XPATH,systemObj.SEARCH_XPATH).click()
            actual = self.driver.find_element(By.XPATH,systemObj.SEARCH_RESULT_TABLE_XPATH.format(1)).text
           
            assert patientIdList[0] == actual
            self.driver.find_element(By.LINK_TEXT,patientIdList[0]).click()
            time.sleep(2)
            # delete existing patient
            self.driver.find_element(By.XPATH,systemObj.DELETE_BUTTON_XPATH).click()
            print("Deleted patient ID :", patientIdList[0])
            
            alert = self.driver.switch_to.alert
            alert.accept()
            
            self.driver.close()
            self.driver.quit()
        
        except Exception as e:
            self.logger.info(f"Delete Investigation Test Failed with {str(e)}")
            self.driver.save_screenshot(
            os.path.abspath(os.curdir) + "\\screenshots\\" + "test_share_document.png")
            # self.driver.close()
            pytest.fail(f"test_Delete_Investigation test FAILED")
        

         
        


       
        
   
                    
        
   

    