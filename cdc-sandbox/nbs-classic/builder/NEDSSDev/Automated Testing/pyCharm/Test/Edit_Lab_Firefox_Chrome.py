

import time
from selenium.webdriver.common.keys import Keys
from selenium import webdriver
from selenium.common.exceptions import WebDriverException
from selenium.webdriver.firefox.firefox_binary import FirefoxBinary


#driver = webdriver.Chrome('C:\\Py\\chromedriver_win32\\chromedriver.exe')  # Optional argument, if not specified will search path.
#binary = FirefoxBinary('C:\\Program Files\\Mozilla Firefox\\firefox.exe')
driver = webdriver.Firefox(executable_path=r'C:\\Py\\geckodriver.exe')
driver_chrome = webdriver.Chrome('C:\\Py\\chromedriver_win32_new\\chromedriver.exe',service_args= ['--ignore-ssl-errors=true','--ssl-protocol=TLSv1'])

#driver = webdriver.Firefox(executable_path=r'C:\\Py\\firefox-sdk\\bin\\firefox.exe')  # Optional argument, if not specified will search path.

# = webdriver.Firefox(executable_path=r'C:\\Py\\firefox-sdk\\bin\\firefox.exe', firefox_binary=binary)

driver.get('http://www.google.com/xhtml')
driver_chrome.get('http://www.google.com/xhtml')
time.sleep(5) # Let the user actually see something!
driver_chrome.get('http://www.google.com/xhtml')
# driver.get('http://35.170.137.196:7001/nbs/login')
# move chrome to the left
# driver_chrome.manage().window().setPosition(new Point(500,0))
driver_chrome.set_window_position(975,0)
driver.set_window_size(960,1000)

driver.get('http://35.172.177.22:7001/nbs/login')
driver_chrome.get('http://35.172.177.22:7001/nbs/login')

login_box = driver.find_element_by_id('id_UserName')
login_box_chrome = driver_chrome.find_element_by_id('id_UserName')
login_box.send_keys('pks')
login_box_chrome.send_keys('pks')

submit_login = driver.find_element_by_id('id_Submit_bottom_ToolbarButtonGraphic')
submit_login_chrome = driver_chrome.find_element_by_id('id_Submit_bottom_ToolbarButtonGraphic')
submit_login.click()
submit_login_chrome.click()



Last_Name = driver.find_element_by_id('DEM102')
Last_Name_chrome = driver_chrome.find_element_by_id('DEM102')
checkelement_chrome = 0
checkelement = 0
while checkelement != 1:
    if Last_Name.is_displayed():
        checkelement = 1
    else:
        time.sleep(1)
        print('Firefox: Wait for home page to load')

while checkelement_chrome != 1:
    if Last_Name_chrome.is_displayed():
        checkelement_chrome = 1
    else:
        time.sleep(1)
        print('Chrome: Wait for home page to load')

Last_Name.send_keys('TestPatientA')
Last_Name_chrome.send_keys('TestPatientA')

First_Name = driver.find_element_by_id('DEM104')
First_Name_chrome = driver_chrome.find_element_by_id('DEM104')
First_Name.send_keys('TestPatientA')
First_Name_chrome.send_keys('TestPatientA')

Click_Search = driver.find_element_by_xpath('.//*[@id=\'patientSearchByDetails\']/table[2]/tbody/tr[8]/td[2]/input[1]')
Click_Search_chrome = driver_chrome.find_element_by_xpath('.//*[@id=\'patientSearchByDetails\']/table[2]/tbody/tr[8]/td[2]/input[1]')
Click_Search.click()
Click_Search_chrome.click()

time.sleep(2)

Click_ID_Link = driver.find_element_by_xpath('.//*[@id=\'searchResultsTable\']/tbody/tr[1]/td[1]/a')
Click_ID_Link_chrome = driver_chrome.find_element_by_xpath('.//*[@id=\'searchResultsTable\']/tbody/tr[1]/td[1]/a')
Click_ID_Link.click()
Click_ID_Link_chrome.click()



time.sleep(3)

Events_Tab = driver.find_element_by_id('tabs0head1')
Events_Tab_chrome = driver_chrome.find_element_by_id('tabs0head1')
Events_Tab.click()
Events_Tab_chrome.click()

Add_New_Lab = driver.find_element_by_id('subsect_Lab')
Add_New_Lab_chrome = driver_chrome.find_element_by_id('subsect_Lab')
Add_New_Lab.click()
Add_New_Lab_chrome.click()

click_lab_link = driver.find_element_by_xpath(".//*[@id='eventLabReport']/tbody/tr/td[1]/a")
click_lab_link_chrome = driver_chrome.find_element_by_xpath(".//*[@id='eventLabReport']/tbody/tr/td[1]/a")
click_lab_link.click()
click_lab_link_chrome.click()


click_lab_edit = driver.find_element_by_name("edit")
click_lab_edit_chrome = driver_chrome.find_element_by_name("edit")
click_lab_edit.click()
click_lab_edit_chrome.click()

edit_LAB125 = driver.find_element_by_id("LAB125")
edit_LAB125_chrome = driver_chrome.find_element_by_id("LAB125")

LAB125_text = "111111"
LAB125_text_chrome = "222222"
time.sleep(1)
edit_LAB125.clear()
edit_LAB125_chrome.clear()
edit_LAB125.send_keys(LAB125_text)
edit_LAB125_chrome.send_keys(LAB125_text_chrome)


click_lab_submit = driver.find_element_by_name("Submit")
click_lab_submit_chrome = driver_chrome.find_element_by_name("Submit")
click_lab_submit.click()
time.sleep(2) # wait so the error is returned in Chrome
click_lab_submit_chrome.click()


PageSource_chrome = driver_chrome.page_source
PageSplit_chrome = PageSource_chrome.splitlines()

error_count = 0
for i in PageSplit_chrome:
    if "The record you are editing has been edited by PKS PKS since you have opened it. The system is unable to save your changes at this time. Please Cancel from this Edit and try again" in i:
        error_count = error_count +1


edit_LAB125_chrome = driver_chrome.find_element_by_id("LAB125")
time.sleep(2)
a = edit_LAB125_chrome.get_attribute('value')

while a == "":
    time.sleep(1)
    edit_LAB125_chrome = driver_chrome.find_element_by_id("LAB125")
    a = edit_LAB125_chrome.get_attribute('value')
    print("whileAAA:", a)


print("AAA:",a)

print("The error occurs ", error_count, " time(s) on the screen")

print("User 2 entered", LAB125_text_chrome, "and", a, "is displayed after submit")


