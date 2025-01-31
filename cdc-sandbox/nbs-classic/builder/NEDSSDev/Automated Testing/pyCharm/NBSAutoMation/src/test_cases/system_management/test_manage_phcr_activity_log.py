import os
import time

import pytest
from datetime import datetime
from selenium.webdriver.common.by import By

from src.page_objects.login.login import LoginPage
from src.page_objects.system_management.manage_phcr_activity_log import manage_phcr_activity_log
from src.page_objects.system_management.manage_sending_receiving_systems import ManageSendingReceivingObject

from src.utilities.constants import NBSConstants
from src.utilities.db_utilities import DBUtilities
from src.utilities.log_config import LogUtils
from src.utilities.nbs_utilities import NBSUtilities
from dateutil.relativedelta import relativedelta


class TestManagePHCRActivityLog():
    
    testcase_start_time = int(time.time() * 1000)
    logger = LogUtils.loggen(__name__)
    
    
    random_number = datetime.now().strftime("%m%d%y%H%M%S")
    random_number_for_patient_extended = None
    logger = LogUtils.loggen(__name__)
    patient_uid = None
    patient_uid_extended = None
    db_utilities = DBUtilities()
    
    
    def test_activity_log_filter(self,setup):
        self.driver = setup
        NBSUtilities.performLoginValidation(self.driver)  # Login Validation
        receivingobject = ManageSendingReceivingObject(self.driver)
        manage_activy_obj = manage_phcr_activity_log(self.driver)
        self.driver.find_element(By.LINK_TEXT, "System Management").click()
        self.driver.find_element(By.XPATH, receivingobject.MANAGE_SENDING_RECEIVING_SYSTEM).click()
        self.driver.find_element(By.LINK_TEXT, "Manage PHCR Activity Log").click()
        time.sleep(2)
        
        current_date = datetime.now()
        current_date_f = current_date.strftime("%m/%d/%Y %I:%M")
        current_date_f = current_date_f.lstrip("0").replace("/0", "/")
        print("current_date_f",current_date_f)
              
        previous_date = current_date-relativedelta(months=10)
        previous_date_formatted = previous_date.strftime("%m/%d/%Y %I:%M")
        # previous_date_formatted = previous_date_formatted.lstrip("0").replace("/0", "/")
        
        c_date,c_time=manage_activy_obj.date_time(current_date_f)
        print("Current",c_date,c_time)
        
        p_date,p_time=manage_activy_obj.date_time(previous_date_formatted)
        print("Previous",p_date,p_time)
        
        self.driver.find_element(By.ID, "from_date").clear()
        self.driver.find_element(By.ID, "from_date").send_keys(p_date)
        self.driver.find_element(By.XPATH, '//*[@id="subsec0"]/tbody/tr[2]/td[2]/input[2]').clear()
        self.driver.find_element(By.XPATH, '//*[@id="subsec0"]/tbody/tr[2]/td[2]/input[2]').send_keys(p_time)
                        
        self.driver.find_element(By.ID, "to_date").clear()           
        self.driver.find_element(By.ID, "to_date").send_keys(c_date)
        self.driver.find_element(By.XPATH, '//*[@id="subsec0"]/tbody/tr[4]/td[2]/input[2]').clear()
        self.driver.find_element(By.XPATH, '//*[@id="subsec0"]/tbody/tr[4]/td[2]/input[2]').send_keys(c_time)
        
        # self.driver.find_element(By.ID, "searchButton").click()
        #success
        self.driver.find_element(By.XPATH,'//*[@id="subsec0"]/tbody/tr[6]/td[2]/input[2]').click()
        time.sleep(1)
        self.driver.find_element(By.ID, "searchButton").click()
        time.sleep(2)
        
        #Failure
        self.driver.find_element(By.XPATH,'//*[@id="subsec0"]/tbody/tr[6]/td[2]/input[1]').click()
        time.sleep(1)
        self.driver.find_element(By.XPATH,'//*[@id="subsec0"]/tbody/tr[6]/td[2]/input[2]').click()
        self.driver.find_element(By.ID, "searchButton").click()
        time.sleep(2)
        
        #ALL
        self.driver.find_element(By.XPATH,'//*[@id="subsec0"]/tbody/tr[6]/td[2]/input[1]').click()
        self.driver.find_element(By.ID, "searchButton").click()
        time.sleep(2)
        
        
        ele = self.driver.find_elements(By.XPATH,'//*[@value="Print"]')
        if ele:
            ele[0].click()
        
        ele = self.driver.find_elements(By.XPATH,'//*[@value="Download"]')
        if ele:
            ele[0].click()
            
            
        if manage_activy_obj.validateSortingOnResultsGrid():
            self.logger.info("*test_message_queue:Sorting on All Columns on the Results "
                                "Grid PASSED*")
            assert True
        else:
            self.logger.info("*test_message_queue:Sorting on one or multiple Columns "
                                " on the Results Grid Failed**")
            
         # validate pagination if records spawns more than one page.
        if manage_activy_obj.validatePaginationOnResults():
            self.logger.info("*test_message_queue:Pagination on Search Results PASSED*")
            assert True
        else:
            self.logger.info("*test_message_queue:Pagination on Search Results FAILED**")

        
        self.driver.find_element(By.XPATH,'//*[@id="parent"]/tbody/tr[1]/td[1]/a').click()
        time.sleep(1)
        self.driver.find_element(By.XPATH,'//*[@id="srtLink"]/a').click()
        
        self.lp = LoginPage(self.driver)
        time.sleep(1)
        self.lp.logoutFromNBSApplication()
        testcase_end_time = int(time.time() * 1000)
        execution_time_for_all = testcase_end_time - TestManagePHCRActivityLog.testcase_start_time
        self.logger.info("Execution Time::" + str(NBSUtilities.convertMillis(execution_time_for_all)))
        self.driver.close()
        self.driver.quit()
         
  
        
        
    
        
        
        
        
        
        