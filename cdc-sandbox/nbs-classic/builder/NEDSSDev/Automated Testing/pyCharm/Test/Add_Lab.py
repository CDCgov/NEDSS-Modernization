

import time
from selenium.webdriver.common.keys import Keys
from selenium import webdriver
from selenium.common.exceptions import WebDriverException
from selenium.webdriver.firefox.firefox_binary import FirefoxBinary


#driver = webdriver.Chrome('C:\\Py\\chromedriver_win32\\chromedriver.exe')  # Optional argument, if not specified will search path.
#binary = FirefoxBinary('C:\\Program Files\\Mozilla Firefox\\firefox.exe')
driver = webdriver.Firefox(executable_path=r'C:\\Py\\geckodriver.exe')

#driver = webdriver.Firefox(executable_path=r'C:\\Py\\firefox-sdk\\bin\\firefox.exe')  # Optional argument, if not specified will search path.

# = webdriver.Firefox(executable_path=r'C:\\Py\\firefox-sdk\\bin\\firefox.exe', firefox_binary=binary)

driver.get('http://www.google.com/xhtml')
time.sleep(5) # Let the user actually see something!
# driver.get('http://35.170.137.196:7001/nbs/login')
driver.get('http://35.172.177.22:7001/nbs/login')

login_box = driver.find_element_by_id('id_UserName')
login_box.send_keys('pks')

submit_login = driver.find_element_by_id('id_Submit_bottom_ToolbarButtonGraphic')
submit_login.click()



Last_Name = driver.find_element_by_id('DEM102')
checkelement = 0
while checkelement != 1:
    if Last_Name.is_displayed():
        checkelement = 1
    else:
        time.sleep(1)
        print('Wait for home page to load')

Last_Name.send_keys('TestPatientA')

First_Name = driver.find_element_by_id('DEM104')
First_Name.send_keys('TestPatientA')

Click_Search = driver.find_element_by_xpath('.//*[@id=\'patientSearchByDetails\']/table[2]/tbody/tr[8]/td[2]/input[1]')
Click_Search.click()

time.sleep(2)

Click_ID_Link = driver.find_element_by_xpath('.//*[@id=\'searchResultsTable\']/tbody/tr[1]/td[1]/a')
Click_ID_Link.click()



time.sleep(3)

Events_Tab = driver.find_element_by_id('tabs0head1')
Events_Tab.click()

Add_New_Lab = driver.find_element_by_id('subsect_Lab')
Add_New_Lab.click()