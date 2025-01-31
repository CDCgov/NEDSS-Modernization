import time
from selenium.webdriver.support.wait import WebDriverWait
from src.page_objects.BasePageObject import BasePageObject
from src.utilities.constants import NBSConstants
from src.utilities.log_config import LogUtils
from src.utilities.nbs_utilities import NBSUtilities
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.by import By
from datetime import datetime


class ManageSendingReceivingObject(BasePageObject):
    logger = LogUtils.loggen(__name__)

    def __init__(self, driver):
        super().__init__(driver)
        self.driver = driver
        
    # SYSTEM_NAME=""
    REPORT_TYPE = 'reportTypeField'
    MANAGE_SENDING_RECEIVING_SYSTEM = '//*[@id="systemAdmin4"]/thead/tr/th/a/img'
    ADD_NEW_SYSTEM_BUTTON = 'Add New System'
    random_number = datetime.now().strftime("%d%m%y%H%M%S")
    DISPLAY_NAME = 'sysShrtNm'
    APPLICATION_NAME = 'sysNm'
    APPLICATION_OID = 'sysOid'
    FACILITY_NAME = 'sysOwner'
    FACILITY_ID = 'sysOwnerOid'
    FACILITY_DESCRIPTION = 'sysDesc'
    SENDING_SYSTEM = 'sendSys'
    RECEIVING_SYSTEM = 'receivSys'
    ALLOW_TRANSFER = 'allowTransfer'
    JURISDICTION = 'jurDeriveIndCd'
    ADMIN_DESCRIPTION = "selection.adminComment"
    SUBMIT = 'submitCrSub'
    
    def switch_to_current_window(self):
        current_window = self.driver.window_handles[-1]
        self.driver.switch_to.window(current_window)
        return True

    def switch_to_original_window(self):
        self.driver.switch_to.window(self.driver.window_handles[0])
        return True
    
    def create_system(self,random_number,param):

        report = self.set_and_return_dropdown_value(self.REPORT_TYPE)
        print('report++++++++++++++++++++++',report)
        
        if param == True:
            self.driver.find_element(By.ID, self.DISPLAY_NAME).clear()
            self.driver.find_element(By.ID, self.APPLICATION_NAME).clear()
            self.driver.find_element(By.ID, self.APPLICATION_OID).clear()
            self.driver.find_element(By.ID, self.FACILITY_NAME).clear()
            self.driver.find_element(By.ID, self.FACILITY_ID).clear()
            self.driver.find_element(By.ID, self.FACILITY_DESCRIPTION).clear()
            self.driver.find_element(By.NAME, self.ADMIN_DESCRIPTION).clear()
        
        # SYSTEM_NAME = "SystemName".__add__(str(random_number))
        self.driver.find_element(By.ID, self.DISPLAY_NAME).send_keys("SystemName".__add__(str(random_number)))
        time.sleep(1)
        self.driver.find_element(By.ID, self.APPLICATION_NAME).send_keys("AppName".__add__(str(random_number)))
        time.sleep(1)
        self.driver.find_element(By.ID, self.APPLICATION_OID).send_keys("ApplicationID".__add__(str(random_number)))
        time.sleep(1)
        self.driver.find_element(By.ID, self.FACILITY_NAME).send_keys("FacilityName".__add__(str(random_number)))
        time.sleep(1)
        self.driver.find_element(By.ID, self.FACILITY_ID).send_keys("FacilityID".__add__(str(random_number)))
        time.sleep(1)
        self.driver.find_element(By.ID, self.FACILITY_DESCRIPTION).send_keys("Description:" + NBSUtilities.generate_random_string(50))
        self.setValueForHTMLElement(NBSConstants.HTML_SET_DROP_DOWN_RANDOM_VALUE,NBSConstants.TYPE_CODE_ID,
                                        self.SENDING_SYSTEM,
                                        None)
        if report == 'PHC236':
            REC_SYS = self.set_and_return_dropdown_value(self.RECEIVING_SYSTEM)
            if REC_SYS == 'Y':
                self.setValueForHTMLElement(NBSConstants.HTML_SET_DROP_DOWN_RANDOM_VALUE,NBSConstants.TYPE_CODE_ID,
                                        self.ALLOW_TRANSFER,
                                        None)
        self.setValueForHTMLElement(NBSConstants.HTML_SET_DROP_DOWN_RANDOM_VALUE,NBSConstants.TYPE_CODE_ID,
                                        self.JURISDICTION,
                                        None)
        self.driver.find_element(By.NAME, self.ADMIN_DESCRIPTION).send_keys("Admin_Comments:" + NBSUtilities.generate_random_string(50))
        self.driver.find_element(By.ID, self.SUBMIT).click()

    def edit_system(self,random_number):
        self.driver.find_element(By.ID, 'edit').click()
        self.create_system(random_number,param=True)
        
    def valiate_system(self):
        self.driver.find_element(By.LINK_TEXT,'Open Investigations').click()
        self.driver.find_element(By.XPATH,'//*[@id="parent"]/tbody/tr[1]/td[6]/a').click()
        self.driver.find_element(By.XPATH,'//*[@id="caseRep"]').click()
        self.switch_to_current_window()
        self.driver.maximize_window()
        # print('SYSTEM_NAME',self.SYSTEM_NAME)
        self.driver.find_element(By.XPATH,'//*[@id="recipientRow"]/td[2]/input').send_keys("SystemName")
        self.set_and_return_dropdown_value("documentType")      
        self.driver.find_element(By.ID, 'shareComments').send_keys("Admin_Comments:" + NBSUtilities.generate_random_string(50))
        self.driver.find_element(By.XPATH,  '//*[@value="Submit"]').click()
       