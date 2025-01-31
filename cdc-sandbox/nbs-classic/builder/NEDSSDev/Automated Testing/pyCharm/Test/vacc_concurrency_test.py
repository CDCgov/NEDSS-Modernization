import time
from selenium import webdriver
from NBS_Login import nbs

driver_chrome = webdriver.Chrome('C:\\Py\\chromedriver_win32_new\\chromedriver.exe',service_args= ['--ignore-ssl-errors=true','--ssl-protocol=TLSv1'])
nbs.login_w_pks(driver_chrome)

driver = webdriver.Firefox(executable_path=r'C:\\Py\\firefox_gecko\\geckodriver.exe')
nbs.login_w_pks(driver)

driver_chrome.set_window_position(975,0)
driver.set_window_size(960,1000)

nbs.test_patient_A_file(driver)
nbs.test_patient_A_file(driver_chrome)

vacc_xpath = '//*[@id="eventVaccination"]/tbody/tr/td[1]/a'
vacc_link_firefox = driver.find_element_by_xpath(vacc_xpath)
vacc_link_chrome = driver_chrome.find_element_by_xpath(vacc_xpath)

vacc_link_firefox.click()
vacc_link_chrome.click()

#assume a vaccination exits

# nbs.add_pertussis_patient(driver_chrome,3)
# nbs.add_inv_from_patient_file(driver_chrome)
