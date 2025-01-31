


import os
import win_unicode_console
import io
import re
import time
from selenium.webdriver.common.keys import Keys
from selenium import webdriver
from selenium.common.exceptions import NoSuchElementException
from selenium.webdriver.support.select import Select
from selenium.webdriver.common.action_chains import ActionChains
from selenium.common.exceptions import WebDriverException
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.common.exceptions import TimeoutException
from selenium.webdriver.common.alert import Alert
from seleniumbase import BaseCase

#driver = webdriver.Chrome('C:\\Py\\chromedriver_win32\\chromedriver.exe')  # Optional argument, if not specified will search path.

def check_exists_by_name(name):
    try:
        driver.find_element_by_name(name)
    except NoSuchElementException:
        return False
    return True


driver = webdriver.Firefox(executable_path=r'C:\\Py\\geckodriver.exe')

driver.get('http://www.google.com/xhtml')
time.sleep(5) # Let the user actually see something!
#driver.get('http://nedss-perform1:7001/nbs/login')
driver.get('http://35.175.6.208:7001/nbs/login')
#driver.get('http://35.175.68.52:7001/nbs/login')


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


#Add_New = driver.find_element_by_name('Submit')
#Add_New.click()


#Submit_New_Pat = driver.find_element_by_id('Submit')
#Submit_New_Pat.click()

time.sleep(3)

Events_Tab = driver.find_element_by_id('tabs0head1')
Events_Tab.click()


Add_New_Lab = driver.find_element_by_xpath('.//*[@id=\'subsect_Lab\']/table/tbody/tr/td[3]/input')
Add_New_Lab.click()

time.sleep(5) # Let the page load

Lab_Report_Tab = driver.find_element_by_id('tabs0head1')
Lab_Report_Tab.click()

##########################################GET PAGE HTML#####################################################
PageSource = driver.page_source


PageSplit = PageSource.splitlines()
fieldNames = []
Input_Id_lst = []
Add_List = []
Tab_List = []
Other_Id_List = []
SplitLines = []
Questions = []
tab_ids_lst = []
next_tab = []
filter_fieldNames = []
drop_down_select_ids = []

select_lst = []

a = 0
b = 0
c = 0
#print(PageSplit)


def ids_in_line(input_id_lst):
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
        if 'tabs0head' in j:
            tab_ids_lst.append(j[xa + 4:ya])
        if 'tabs0tab' in j:
            next_tab.append(j[xa + 4:ya])
    return new_list

def ids_in_list(input_id_lst):
    xa = 0
    ya = 0
    id_list = []
    for j in input_id_lst:
        if 'id=' in j:
            while 'id=' != j[xa:xa + 3]:
                xa = xa + 1
            ya = xa + 4
            while '\"' != j[ya:ya + 1]:
                ya = ya + 1
            id_list.append(j[xa + 4:ya])

    return id_list

def ids_in_page(lst):
    id_lst = []
    b = 0
    for i in lst:
        b = b + 1
        ###############################################print(i)
        if 'id=' in i:
            c = i.count('id=')
            if c > 1:
                SplitLines = i.split()
                id_lst.extend(ids_in_list(SplitLines))

            x = 0
            y = 0
            qid = ''
            while 'id=' != i[x:x+3]:
                x = x + 1
            y = x + 4
            while '\"' != i[y:y+1]:
                y = y + 1
            id_lst.append(i[x + 4:y])
    return id_lst


for i in PageSplit:
    b = b + 1
    ##################################################print(i)
    ##All lines with 'id=' if 'id=' in i :
    if 'id=' in i and 'class=\"subSect\"' not in i :#and 'INV165' not in i and 'INV166' not in i: #exclude id's wth class="subSect" in the same line  and i != 'INV165' and i != 'INV166'
        print(i)
        c = i.count('id=')
        if c > 1:
            SplitLines = i.split()
            fieldNames.extend(ids_in_line(SplitLines))

        x = 0
        y = 0
        qid = ''
        while 'id=' != i[x:x+3]:
            x = x + 1
        y = x + 4
        while '\"' != i[y:y+1]:
            y = y + 1
        if 'L' != i[y - 1:y]:
            fieldNames.append(i[x + 4:y])

        if 'maxlength=\"10\" size=\"10\"' in i and 'DateMask' in i:
            Questions.append(i[x + 4:y])
        if 'tabs0tab' in i:
            next_tab.append(i[x + 4:y])
        if 'AddButtonToggle' in i:
            Questions.append(i[x + 4:y])


#################### DROP DOWN SELECTS#################################################################

def get_page_dropdown_options(select_lst,id_lst):
    key_lst = []
    drop_down_options = {}
    option = []
    line_op = []
    set_id = ''

    for k in id_lst:
        drop_down_options[k] = ''

    for ii in select_lst:
        for jj in id_lst:
            if jj in ii:
                set_id = jj
                option = []

        line_op = ii[1:len(ii) - 1]

        if 'option value=' in line_op:
            option.append(line_op[line_op.index('>') + 1:line_op.index('<')])
        drop_down_options[set_id] = [o for o in option[1:]]
    return drop_down_options


Copy_Select_Line = False
for i in PageSplit:
    if '<select' in i:
        Copy_Select_Line = True
    if Copy_Select_Line:
        select_lst.append(i)
    if '</select>' in i:
        Copy_Select_Line = False




n = 0
for i in select_lst:
    x = 0
    y = 0
    if '<select' in i:
        while '<select' in i[x:]:
            x = x + 1
        select_lst[n] = i[x-1:]
    if '/select>' in select_lst[n]:
        while '/select>' in str(select_lst[n])[:y-1]:
            y = y - 1
        select_lst[n] = str(select_lst[n])[:y]

    n = n + 1

drop_down_select_ids = ids_in_page(select_lst)

drop_down_options = {}
drop_down_options = get_page_dropdown_options(select_lst, drop_down_select_ids)

print(drop_down_options)

#################### DROP DOWN SELECTS#################################################################
#Fill in questions happens below here#########################################################################################

#Reporting_Facility
try:
    Reporting_Facility = driver.find_element_by_id('NBS_LAB365Text')
    Reporting_Facility.send_keys('1')

    #Click Quick Code Lookup
    Quick_Code_Lookup = driver.find_element_by_id('NBS_LAB365CodeLookupButton')
    Quick_Code_Lookup.click()
except WebDriverException:
    pass


#INV108
Fill_ProgramArea_select = driver.find_element_by_name('INV108' + '_textbox')
Fill_ProgramArea_select.send_keys(drop_down_options['INV108'][0])

# Jurisdiction if blank
Jurisdiction = driver.find_element_by_name('INV107_textbox')
if Jurisdiction.get_attribute("value") == '':
    Jurisdiction.click()
    actions = ActionChains(driver)
    actions.send_keys(Keys.ARROW_DOWN).perform()
    actions.send_keys(Keys.TAB).perform()



#NBS_LAB201
Fill_Date_Received_by_Public_Health = driver.find_element_by_id('NBS_LAB201')
Fill_Date_Received_by_Public_Health.send_keys('01/02/2019')

#name="NBS_LAB220_textbox"
Resulted_Test = driver.find_element_by_name('NBS_LAB220_textbox')
time.sleep(3)
Resulted_Test.click()

actions = ActionChains(driver)
actions.send_keys(Keys.ARROW_DOWN).perform()
actions.send_keys(Keys.TAB).perform()

####################################################################################
#      FIND THE Resulted Test DROP DOWN OPTIONS THAT JUST LOADED                   #
####################################################################################
PageSource2 = driver.page_source
print(PageSource2)
PageSplit2 = PageSource2.splitlines()
fieldNames2 = []
Input_Id_lst2 = []
Add_List2 = []
Tab_List2 = []
Other_Id_List2 = []
SplitLines2 = []
Questions2 = []
tab_ids_lst2 = []
next_tab2 = []
filter_fieldNames2 = []
drop_down_select_ids2 = []

select_lst2 = []
a2 = 0
b2 = 0
c2 = 0

for i in PageSplit2:
    b = b + 1
    ##################################################print(i)
    ##All lines with 'id=' if 'id=' in i :
    if 'id=' in i and 'class=\"subSect\"' not in i :#and 'INV165' not in i and 'INV166' not in i: #exclude id's wth class="subSect" in the same line  and i != 'INV165' and i != 'INV166'
        print(i)
        c = i.count('id=')
        if c > 1:
            SplitLines2 = i.split()
            fieldNames2.extend(ids_in_line(SplitLines2))

        x = 0
        y = 0
        qid = ''
        while 'id=' != i[x:x+3]:
            x = x + 1
        y = x + 4
        while '\"' != i[y:y+1]:
            y = y + 1
        if 'L' != i[y - 1:y]:
            fieldNames2.append(i[x + 4:y])

        if 'maxlength=\"10\" size=\"10\"' in i and 'DateMask' in i:
            Questions2.append(i[x + 4:y])
        if 'tabs0tab' in i:
            next_tab2.append(i[x + 4:y])
        if 'AddButtonToggle' in i:
            Questions2.append(i[x + 4:y])


Copy_Select_Line2 = False
for i in PageSplit2:
    if '<select' in i:
        Copy_Select_Line2 = True
    if Copy_Select_Line2:
        select_lst2.append(i)
    if '</select>' in i:
        Copy_Select_Line2 = False

n2 = 0
for i in select_lst2:
    x = 0
    y = 0
    if '<select' in i:
        while '<select' in i[x:]:
            x = x + 1
        select_lst2[n2] = i[x-1:]
    if '/select>' in select_lst2[n2]:
        while '/select>' in str(select_lst2[n2])[:y-1]:
            y = y - 1
        select_lst2[n2] = str(select_lst2[n2])[:y]

    n2 = n2 + 1

def ids_in_page2(lst):
    id_lst = []
    b = 0
    for i in lst:
        b = b + 1
        ###############################################print(i)
        if 'id=' in i:
            c = i.count('id=')
            if c > 1:
                SplitLines2 = i.split()
                id_lst.extend(ids_in_list(SplitLines2))

            x = 0
            y = 0
            qid = ''
            while 'id=' != i[x:x+3]:
                x = x + 1
            y = x + 4
            while '\"' != i[y:y+1]:
                y = y + 1
            id_lst.append(i[x + 4:y])
    return id_lst


def get_page_dropdown_options2(select_lst2,id_lst2):
    key_lst = []
    drop_down_options2 = {}
    option2 = []
    line_op = []
    set_id2 = ''

    for k in id_lst2:
        drop_down_options2[k] = ''

    for ii in select_lst2:
        for jj in id_lst2:
            if jj in ii:
                set_id2 = jj
                option2 = []

        line_op2 = ii[1:len(ii) - 1]

        if 'option value=' in line_op2:
            option2.append(line_op2[line_op2.index('>') + 1:line_op2.index('<')])
        drop_down_options2[set_id2] = [o for o in option2[1:]]
    return drop_down_options2

drop_down_select_ids2 = ids_in_page2(select_lst2)

drop_down_options2 = {}
drop_down_options2 = get_page_dropdown_options2(select_lst2, drop_down_select_ids2)
print(drop_down_options2)

#######################################################################################################################
# POPULATE  Resulted Test With the new dropdown list
#######################################################################################################################

#RESULTED_TEST_CONTAINER
#Fill_ProgramArea_select = driver.find_element_by_name('NBS_LAB220' + '_textbox')
#Fill_ProgramArea_select = driver.find_element_by_name('NBS_LAB220' + '_textbox')
#Fill_ProgramArea_select.send_keys(drop_down_options['RESULTED_TEST_CONTAINER'][0])
#Resulted_Test_Option = driver.find_element_by_xpath('.//*[@id=\'NBS_LAB220\']/option[2]')
#Resulted_Test_Option.click()

#Drop_Down_Count = Select(driver.find_element_by_name("NBS_LAB220_textbox"))

#Coded Result
Coded_Result = driver.find_element_by_name('NBS_LAB280_textbox')
time.sleep(3)
Coded_Result.click()

actions = ActionChains(driver)
actions.send_keys(Keys.ARROW_DOWN).perform()
actions.send_keys(Keys.TAB).perform()


Add_Resulted_Test = driver.find_element_by_xpath('.//*[@id=\'AddButtonToggleRESULTED_TEST_CONTAINER\']/td/input')
Add_Resulted_Test.click

Submit_Lab = driver.find_element_by_name('Submit')
Submit_Lab.click()

time.sleep(3)

Events_Tab = driver.find_element_by_id('tabs0head1')
Events_Tab.click()

Lab_Link = driver.find_element_by_xpath('.//*[@id=\'eventLabReport\']/tbody/tr[1]/td[1]/a')
Lab_Link.click()

TransferOwnership_Button = driver.find_element_by_name('TransferOwn')
TransferOwnership_Button.click()

#Wait for the page to load
time.sleep(1)

#Edit the Program Area
Program_Area = driver.find_element_by_id('orderedTest.theObservationDT.progAreaCd_ac_table')
time.sleep(3)
Program_Area.click()

actions = ActionChains(driver)
actions.send_keys(Keys.ARROW_DOWN).perform()
actions.send_keys(Keys.TAB).perform()


#Edit Jurisdiction
Jurisdiction = driver.find_element_by_id('orderedTest.theObservationDT.jurisdictionCd_ac_table')
time.sleep(3)
Jurisdiction.click()

actions = ActionChains(driver)
actions.send_keys(Keys.ARROW_DOWN).perform()
actions.send_keys(Keys.TAB).perform()

#Click Submit
Submit_TO = driver.find_element_by_id('Submit')
Submit_TO.click()

#print Observation ID
Observation_ID = driver.find_element_by_xpath('html/body/table/tbody/tr/td/table/tbody/tr[3]/td/table/thead/tr[1]/td/table/tbody/tr/td[1]/span/b')
print('A')
print(Observation_ID)
print('A')

#Click view file
ViewFileLink = driver.find_element_by_link_text('View File')
ViewFileLink.click()




