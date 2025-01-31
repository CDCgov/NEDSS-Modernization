

import time
from selenium.webdriver.common.keys import Keys
from selenium import webdriver
from selenium.webdriver.common.keys import Keys
from selenium.common.exceptions import WebDriverException
from selenium.webdriver.firefox.firefox_binary import FirefoxBinary


#driver = webdriver.Chrome('C:\\Py\\chromedriver_win32\\chromedriver.exe')  # Optional argument, if not specified will search path.
#binary = FirefoxBinary('C:\\Program Files\\Mozilla Firefox\\firefox.exe')
driver = webdriver.Firefox(executable_path=r'C:\\Py\\geckodriver.exe')
#driver = webdriver.Firefox(executable_path=r'C:\\Py\\firefox-sdk\\bin\\firefox.exe')  # Optional argument, if not specified will search path.

# = webdriver.Firefox(executable_path=r'C:\\Py\\firefox-sdk\\bin\\firefox.exe', firefox_binary=binary)

driver.get('http://www.google.com/xhtml')
time.sleep(5) # Let the user actually see something!
driver.get('http://nedss-perform:7001/nbs/login')

login_box = driver.find_element_by_id('id_UserName')
login_box.send_keys('pks')

submit_login = driver.find_element_by_id('id_Submit_bottom_ToolbarButtonGraphic')
submit_login.click()



Data_Entry= driver.find_element_by_link_text('Data Entry')
Data_Entry.click()

DE_MorbRep = driver.find_element_by_link_text('Morbidity Report')
DE_MorbRep.click()

MR_LastNM = driver.find_element_by_id('entity.lastNm')
MR_LastNM.send_keys('MR1_LastNM')

MR_FirstNM = driver.find_element_by_id('entity.firstNm')
MR_FirstNM.send_keys('MR1_FirstNM')

Rep_Info_Tab = driver.find_element_by_link_text('Report Information')
Rep_Info_Tab.click()

Condition = driver.find_element_by_name('conditionCd_textbox')
Condition.send_keys('Acute flaccid myelitis')

Tab1 = driver.find_element_by_name('conditionCd_textbox')
Tab1.send_keys(Keys.TAB)

Jurisdiction = driver.find_element_by_name('morbidityReport.theObservationDT.jurisdictionCd_textbox')
Jurisdiction.send_keys('Autauga County')

Tab1 = driver.find_element_by_xpath('.//*[@id=\'morbidityProxy.observationVO_s[1].obsValueCodedDT_s[0].code_ac_table\']/tbody/tr[1]/td/input')
Tab1.send_keys(Keys.TAB)

DateofMR = driver.find_element_by_id('morbidityReport.theObservationDT.activityToTime_s')and driver.find_element_by_name('morbidityReport.theObservationDT.activityToTime_s')
DateofMR.send_keys('09/25/2018')
time.sleep(2)
ReportFacility = driver.find_element_by_name('entity-codeLookupText-Org-ReportingOrganizationUID')
ReportFacility.send_keys('ACHS')
time.sleep(2)
CodeLookup = driver.find_element_by_xpath('.//*[@id=\'entity-table-Org-ReportingOrganizationUID\']/thead/tr/td[2]/input[2]')
CodeLookup.send_keys(Keys.RETURN)
time.sleep(2)
Submit_Morb = driver.find_element_by_name('Submit')
Submit_Morb.click()
time.sleep(2)
#Home = driver.find_element_by_link_text('Home')
#Home.click()