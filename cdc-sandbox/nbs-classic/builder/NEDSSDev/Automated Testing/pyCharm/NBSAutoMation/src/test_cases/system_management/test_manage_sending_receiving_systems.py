import os
import time

import pytest
from datetime import datetime
from selenium.webdriver.common.by import By

from src.page_objects.login.login import LoginPage
from src.page_objects.system_management.manage_sending_receiving_systems import ManageSendingReceivingObject

from src.utilities.constants import NBSConstants
from src.utilities.db_utilities import DBUtilities
from src.utilities.log_config import LogUtils
from src.utilities.nbs_utilities import NBSUtilities


class TestManageSendingRecevingSystems():
    random_number = datetime.now().strftime("%m%d%y%H%M%S")
    random_number_for_patient_extended = None
    logger = LogUtils.loggen(__name__)
    patient_uid = None
    patient_uid_extended = None
    testcase_start_time = None
    db_utilities = DBUtilities()
    
    
        
          
    def test_add_new_system(self,setup):
        self.driver = setup
        NBSUtilities.performLoginValidation(self.driver)  # Login Validation
        receivingobject = ManageSendingReceivingObject(self.driver)
        self.driver.find_element(By.LINK_TEXT, "System Management").click()
        self.driver.find_element(By.XPATH, receivingobject.MANAGE_SENDING_RECEIVING_SYSTEM).click()
        self.driver.find_element(By.LINK_TEXT, "Manage Sending and Receiving Systems").click()
        time.sleep(2)
        self.driver.find_element(By.ID, "submitCr").click()
        receivingobject.create_system(self.random_number,False)
        
    def test_edit_new_system(self,setup):
        self.driver = setup
        receivingobject = ManageSendingReceivingObject(self.driver)
        receivingobject.edit_system(self.random_number)
        
        
    def test_validate_system(self,setup):
        self.driver = setup
        receivingobject = ManageSendingReceivingObject(self.driver)
       
        receivingobject.valiate_system()
    
        

        
        
    