


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


PageSource1 = driver.page_source


PageSplit = PageSource1.splitlines()
fieldNames = []
Input_Id_lst = []
Add_List = []
Tab_List = []
Other_Id_List = []
SplitLines = []

a = 0
b = 0
c = 0
#print(PageSplit)


def ids_in_list(input_id_lst):
    xa = 0
    ya = 0
    new_list = []
    for j in input_id_lst:
        if 'id=' in j:
            while 'id=' != j[xa:xa + 3]:
                xa = xa + 1
            ya = xa + 4
            while '\"' != j[ya:ya + 1]:
                ya = ya + 1
            new_list.append(j[xa + 4:ya])
    return new_list

for i in PageSplit:
    b = b + 1
    #print(i)
    if 'id=' in i:
        c = i.count('id=')
        # xa = 0
        # ya = 0
        if c > 1:
            SplitLines = i.split()
            fieldNames.extend(ids_in_list(SplitLines))

            # for j in SplitLines:
            #
            #     if 'id=' in j:
            #
            #         while 'id=' != j[xa:xa + 3]:
            #             xa = xa + 1
            #
            #         ya = xa + 4
            #         while '\"' != j[ya:ya + 1]:
            #             ya = ya + 1
            #         fieldNames.append(j[xa + 4:ya])
        print(i)
        print(b)
        print(c)
        x = 0
        y = 0
        qid = ''
        while 'id=' != i[x:x+3]:
            x = x + 1
        y = x + 4
        while '\"' != i[y:y+1]:
            y = y + 1
        fieldNames.append(i[x + 4:y])

print(fieldNames)
