import time
from selenium import webdriver
from NBS_Login import nbs
import random


driver_chrome = webdriver.Chrome('C:\\Py\\chromedriver_win32_new\\chromedriver.exe',service_args= ['--ignore-ssl-errors=true','--ssl-protocol=TLSv1'])

nbs.login_w_pks(driver_chrome)
nbs.select_existing_pat(driver_chrome)
# nbs.add_pertussis_patient(driver_chrome,3)
nbs.add_inv_from_patient_file(driver_chrome)
# nbs.select_existing_pat(driver_chrome)


#######################################
jurisdiction = driver_chrome.find_element_by_name('proxy.publicHealthCaseVO_s.thePublicHealthCaseDT.jurisdictionCd_textbox')
jurisdiction.send_keys("Clayton County")

state_case_id = driver_chrome.find_element_by_id('proxy.publicHealthCaseVO_s.actIdDT_s[0].rootExtensionTxt')
random_1 = random.randint(1000,1000000)
state_case_id.send_keys(random_1)


inv_start_date = driver_chrome.find_element_by_name('proxy.publicHealthCaseVO_s.thePublicHealthCaseDT.activityFromTime_s')
inv_start_date.send_keys('04/28/2019')


q1 = driver_chrome.find_element_by_xpath('//*[@id="proxy.observationVO_s[1].obsValueCodedDT_s[0].code_ac_table"]/tbody/tr[1]/td/input')
q1.send_keys('Yes')

