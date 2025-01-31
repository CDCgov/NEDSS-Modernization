

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
driver.get('http://35.170.137.196:7001/nbs/login')

login_box = driver.find_element_by_id('id_UserName')
login_box.send_keys('pks')

submit_login = driver.find_element_by_id('id_Submit_bottom_ToolbarButtonGraphic')
submit_login.click()
time.sleep(5)
#Add Condition
System_Management= driver.find_element_by_xpath('.//*[@id=\'bd\']/form[1]/table/tbody/tr/td[1]/table/tbody/tr/td[11]/a')
System_Management.click()


Page_Management = driver.find_element_by_xpath('.//*[@id=\'systemAdmin5\']/thead/tr/th/a/img')
Page_Management.click()


Manage_Conditions = driver.find_element_by_xpath('.//*[@id=\'systemAdmin5\']/tbody/tr/td/table/tbody/tr[1]/td/a')
Manage_Conditions.click()

Manage_Conditions_AddNew = driver.find_element_by_id('submitCr')
Manage_Conditions_AddNew.click()


Condition_Code = driver.find_element_by_id('cCodeFld')
Condition_Code.send_keys('TestCon1')

Condition_Name = Manage_Conditions = driver.find_element_by_id('condFld')
Condition_Name.send_keys('TestCon1')

# Program_Area = driver.find_element_by_id('subsec2')
# Program_Area.send_keys('GCD')
Program_Area = driver.find_element_by_xpath('.//*[@id=\'subsec2\']/tbody/tr[4]/td[2]/img')
Program_Area.click()
Program_Area_Option = driver.find_element_by_xpath('.//*[@id=\'pAreaFld\']/option[4]')
Program_Area_Option.click()

Submit_TestCon1 = driver.find_element_by_id('submitB')
Submit_TestCon1.click()
#####################################

System_Management= driver.find_element_by_xpath('.//*[@id=\'bd\']/form[1]/table/tbody/tr/td[1]/table/tbody/tr/td[11]/a')
System_Management.click()


Page_Management = driver.find_element_by_xpath('.//*[@id=\'systemAdmin5\']/thead/tr/th/a/img')
Page_Management.click()

Manage_Pages = driver.find_element_by_xpath('.//*[@id=\'systemAdmin5\']/tbody/tr/td/table/tbody/tr[2]/td/a')
Manage_Pages.click()
