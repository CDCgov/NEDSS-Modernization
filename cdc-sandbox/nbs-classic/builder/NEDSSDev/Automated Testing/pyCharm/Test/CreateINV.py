


import os
import win_unicode_console
import io
import re
import time
from selenium.webdriver.common.keys import Keys
from selenium import webdriver
from selenium.common.exceptions import WebDriverException


#driver = webdriver.Chrome('C:\\Py\\chromedriver_win32\\chromedriver.exe')  # Optional argument, if not specified will search path.

driver = webdriver.Firefox(executable_path=r'C:\\Py\\geckodriver.exe')

driver.get('http://www.google.com/xhtml')
time.sleep(5) # Let the user actually see something!
driver.get('http://nedss-test3:7001/nbs/login')

login_box = driver.find_element_by_id('id_UserName')
login_box.send_keys('pks')

submit_login = driver.find_element_by_id('id_Submit_bottom_ToolbarButtonGraphic')
submit_login.click()

Last_Name = driver.find_element_by_id('DEM102')
Last_Name.send_keys('TestPatientA')

First_Name = driver.find_element_by_id('DEM104')
First_Name.send_keys('TestPatientA')

Click_Search = driver.find_element_by_xpath('.//*[@id=\'patientSearchByDetails\']/table[2]/tbody/tr[8]/td[2]/input[1]')
Click_Search.click()

Add_New = driver.find_element_by_name('Submit')
Add_New.click()


Submit_New_Pat = driver.find_element_by_id('Submit')
Submit_New_Pat.click()

time.sleep(3)

Events_Tab = driver.find_element_by_id('tabs0head1')
Events_Tab.click()

Add_INV = driver.find_element_by_xpath('.//*[@id=\'subsect_Inv\']/table/tbody/tr/td[3]/input[2]')
Add_INV.click()

Enter_Condition = driver.find_element_by_name('ccd_textbox')
Enter_Condition.send_keys('Cache Valley virus, neuroinvasive disease')

Submit_AddInv = driver.find_element_by_id('Submit')
Submit_AddInv.click()

time.sleep(2)


PageSource1 = [driver.page_source]
PageSource1 = PageSource1[0].encode("utf-8")

file = open('C:\\Py\\PageSource.txt','ab')

file.writelines(PageSource1)
file.close()

file = open('C:\\Py\\PageSource1.txt','w')
file.close()

with open ('C:\\Py\\PageSource.txt','rb') as outfile, open('C:\\Py\\PageSource1.txt','a') as fl:
    for line in outfile:
        uline = line.decode('ascii', errors = 'ignore')
        fl.write(uline)

outfile.close()
fl.close()

PageSplit = PageSource1.split()
fieldNames = []

a = 0
b = 0

for i in PageSplit:
    if "class=\"fieldName\"" in i:
        b = a
        while i != '</td>':
            i = PageSplit[b]
            b = b + 1
        #', '.join (fieldNames for x in range(a, b))
        fieldNames.append(' '.join(PageSplit[x] for x in range(a + 1, b - 1)))
            #fieldNames.append(PageSplit[x])
        print(PageSplit[a])
    a = a + 1



print(fieldNames)
