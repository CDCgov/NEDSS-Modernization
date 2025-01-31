import time
from selenium.webdriver.common.keys import Keys
from selenium import webdriver



driver = webdriver.Firefox(executable_path=r'C:\\Py\\geckodriver.exe')

driver.get('http://www.google.com/xhtml')
time.sleep(5) # Let the user actually see something!
driver.get('http://nedss-perform1:7001/nbs/login')

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

add_new_lab = driver.find_element_by_xpath('.//*[@id=\'subsect_Lab\']/table/tbody/tr/td[3]/input')
add_new_lab.click()
time.sleep(4)
labreport_tab = driver.find_element_by_id('tabs0head1')
labreport_tab.click()

#get parent window handle
parentWindow = driver.current_window_handle
print('parentWindow = driver.current_window_handle')
window_before = driver.window_handles[0]
print('window_before = driver.window_handles[0]')
time.sleep(3)


search_Org_Name = driver.find_element_by_id('NBS_LAB278Search')
search_Org_Name.click()

#get popup window handle
time.sleep(3)
window_after = driver.window_handles[1]

driver.switch_to.window(window_after)
time.sleep(2)

Organism_Name = driver.find_element_by_xpath('.//*[@id=\'labTest\']')
Organism_Name.send_keys('a')

orgnm_click_submit = driver.find_element_by_id('Submit')
orgnm_click_submit.click()