


import os
import win_unicode_console
import io
import re
import time
from selenium.webdriver.common.keys import Keys
from selenium import webdriver
from selenium.common.exceptions import NoSuchElementException
from selenium.common.exceptions import WebDriverException
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.common.exceptions import TimeoutException
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.action_chains import ActionChains
from selenium.webdriver.common.alert import Alert
from seleniumbase import BaseCase

#driver = webdriver.Chrome('C:\\Py\\chromedriver_win32\\chromedriver.exe')  # Optional argument, if not specified will search path.

def check_exists_by_name(name):
    try:
        driver.find_element_by_name(name)
    except NoSuchElementException:
        return False
    return True

def check_exists_by_id(id):
    try:
        driver.find_element_by_id(id)
    except NoSuchElementException:
        return False
    return True



driver = webdriver.Firefox(executable_path=r'C:\\Py\\geckodriver.exe')

driver.get('http://www.google.com/xhtml')
time.sleep(5) # Let the user actually see something!
# driver.get('http://nedss-perform1:7001/nbs/login')
# driver.get('http://54.208.14.194:7001/nbs/login')
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


#Add_New = driver.find_element_by_name('Submit')
#Add_New.click()


#Submit_New_Pat = driver.find_element_by_id('Submit')
#Submit_New_Pat.click()

time.sleep(3)

Events_Tab = driver.find_element_by_id('tabs0head1')
Events_Tab.click()

#'.//*[@id=\'subsect_Inv\']/table/tbody/tr/td[3]/input[2]'  .//*[@id=\'subsect_Inv\']/table/tbody/tr/td[3]/input
# Add_INV = driver.find_element_by_xpath('.//*[@id=\'subsect_Inv\']/table/tbody/tr/td[3]/input[2]')
Add_INV = driver.find_element_by_xpath('.//*[@id=\'subsect_Inv\']/table/tbody/tr/td[3]/input')

if(Add_INV.get_attribute('value') == "Compare"):
    Add_INV = driver.find_element_by_xpath('.//*[@id=\'subsect_Inv\']/table/tbody/tr/td[3]/input[2]')
    Add_INV.click()
else:
    Add_INV.click()

time.sleep(2)

Enter_Condition = driver.find_element_by_name('ccd_textbox')#ccd_textbox
#Enter_Condition.send_keys('Cache Valley virus, neuroinvasive disease')
# Enter_Condition.send_keys('Hepatitis B, perinatal infection')
# Enter_Condition.send_keys('Acute flaccid myelitis')
Enter_Condition.send_keys('Gonorrhea')
Enter_Condition.send_keys(Keys.TAB)

time.sleep(1)

Referral_Basis = driver.find_element_by_name('referralBasis_textbox')
Referral_Basis.send_keys("A1 - Associate 1")

Submit_AddInv = driver.find_element_by_id('Submit')
# Submit_AddInv.click()
######################################################################
###################_____POPUP_Start__________#########################
######################################################################
parentWindow = driver.current_window_handle
print('parentWindow = driver.current_window_handle')
window_before = driver.window_handles[0]
print('window_before = driver.window_handles[0]')
# time.sleep(3)
Submit_AddInv.click()
print('Submit_AddInv')

time.sleep(3)
# Edt for loop from test1
window_after = driver.window_handles[1]

driver.switch_to.window(window_after)
time.sleep(3)

Processing_Decision = driver.find_element_by_name('reviewReason_textbox')
Processing_Decision.send_keys("Field Follow-up")

Submit_Processing_Decision = driver.find_element_by_xpath("//input[@value='Submit']")
Submit_Processing_Decision.click()
# Publish_Comments = driver.find_element_by_name('selection.versionNote')
# Publish_Comments.send_keys('Automated_VersionNotes')
#
# Publish_Submit = driver.find_element_by_name('Submit')
# Publish_Submit.click()

# driver.switchTo().window(parentWindow)
driver.switch_to.window(window_before)
time.sleep(3)

######################################################################
###################_____POPUP__END___________#########################
######################################################################

time.sleep(8)


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
quick_code_lookup = []

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

with open("C:\\Py\\PageSource.txt", "w") as text_file:
    for i in PageSplit:
        try:
            text_file.write(i)
            text_file.write("\n")
        except:
            continue
time.sleep(2)
for i in PageSplit:
    print(i)
    b = b + 1
    ##################################################print(i)
    ##All lines with 'id=' if 'id=' in i :
    if 'id=' in i and 'class=\"subSect\"' not in i :#and 'INV165' not in i and 'INV166' not in i: #exclude id's wth class="subSect" in the same line  and i != 'INV165' and i != 'INV166'
        # print(i)
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
        if 'CodeLookupButton' in i:
            quick_code_lookup.append(i[x + 4:y])
            # code_lookup_input_id = i[x + 4:y]
            # quick_code_lookup.append(code_lookup_input_id[: -16]+"Text")


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



#################### DROP DOWN SELECTS#################################################################
#Fill in questions happens below here#########################################################################################


Click_New_Tab = False
tab_count = 0
filter_fieldNames = [x for x in fieldNames if x in Questions or x in tab_ids_lst or x in next_tab or x in drop_down_select_ids or x in quick_code_lookup]
other_fieldNames = [xx for xx in fieldNames if xx not in filter_fieldNames]
# print(fieldNames)
# print(filter_fieldNames)
# print(other_fieldNames)

for i in fieldNames:
    if Click_New_Tab:
        Next_Tab = driver.find_element_by_id(tab_ids_lst[tab_count])
        Next_Tab.click()
        Click_New_Tab = False
    for j in Questions:
        if i == j:
            Fill_Question = driver.find_element_by_id(i)
            if Fill_Question.is_enabled() and Fill_Question.is_displayed() and Fill_Question.get_attribute("value") == '':
                if i == 'DEM115':
                    Fill_Question.send_keys('10/04/1998')
                elif i == 'NBS104':
                    continue
                elif 'AddButtonToggle' in i:
                    Fill_Question.click()
                else:
                    Fill_Question.clear()
                    Fill_Question.send_keys('10/04/2018')
    for jj in drop_down_select_ids:
        Isfilled = ''
        if i == jj :
            time.sleep(.5)
            if check_exists_by_name(i + '_textbox'):
                try:
                    Fill_Question_select = driver.find_element_by_name(i + '_textbox')



                except WebDriverException:
                    continue
            # print("check_exists_by_id("+i+"): ", check_exists_by_id(i))
            if check_exists_by_id(i):

                try:
                    # Fill_Question_select_multi = driver.find_element_by_id(i)
                    Fill_Question_select_multi = driver.find_element_by_xpath(".//*[@id='"+i+"']/option[2]")
                    # print("Fill_Question_select_multi: ",Fill_Question_select_multi)

                    if Fill_Question_select_multi.is_enabled() and Fill_Question_select_multi.is_displayed():  # and Fill_Question_select_multi.get_attribute("value") == '':
                        try:

                            Fill_Question_select_multi.click()

                        except (TimeoutException, IndexError):  # except IndexError:
                            print('except IndexError')

                            continue

                except WebDriverException:
                    continue




            if Fill_Question_select.is_enabled() and Fill_Question_select.is_displayed() and Fill_Question_select.get_attribute("value") == '':
                Fill_Question_select.clear()
                try:
                    Fill_Question_select.send_keys(drop_down_options[i][0])
                    actions = ActionChains(driver)
                    actions = actions.send_keys(Keys.TAB)
                    actions.perform()
                    actions.perform()


                    # ActionChains(driver)\
                    #     .key_down(Keys.SHIFT) \
                    #     .key_down(Keys.TAB) \
                    #     .key_up(Keys.TAB) \
                    #     .key_up(Keys.SHIFT)
                    # time.sleep(.1)
                    # ActionChains(driver) \
                    #     .key_down(Keys.TAB) \
                    #     .key_up(Keys.TAB)
                    # time.sleep(.1)

                    #if i == 'INV178' or i == 'INV145':
                        #print(Fill_Question_select.send_keys(drop_down_options[i][0]))
                        #print(drop_down_options[i][0])


                except (TimeoutException, IndexError):     #except IndexError:
                    print('except IndexError')

                    continue

    for jjj in quick_code_lookup:
        if i == jjj:
            enter_quick_code = driver.find_element_by_id(i[:-16]+"Text")
            if enter_quick_code.is_enabled() and enter_quick_code.is_displayed():
                enter_quick_code.send_keys(1)

                click_quick_code_lookup = driver.find_element_by_id(i)
                click_quick_code_lookup.click()

    quick_code_fix = []
    for q1 in quick_code_lookup:
        quick_code_fix.append(q1[:-16]+"Text")


    is_NBS104_displayed = driver.find_element_by_id('NBS104')
    for jjjj in other_fieldNames:
        if i == jjjj and 'SearchControls' not in i and jjjj not in quick_code_fix:
            Fill_Question2 = driver.find_element_by_id(i)
            if Fill_Question2.is_enabled() and Fill_Question2.is_displayed() and Fill_Question2.get_attribute("value") == '':
                if is_NBS104_displayed.is_displayed():
                    continue
                else:
                    if EC.visibility_of_element_located(Fill_Question2):
                        print(i)
                        try:
                            Fill_Question2.clear()
                            Fill_Question2.send_keys(i)
                        except WebDriverException:
                            continue


    if 'tabs0tab' in i:
        print("tabs0tab: ",i)
        if int(i[-1]) > 0:
            tab_count = tab_count + 1
            Click_New_Tab = True
    if 'AddButtonToggle' in i:
        try:
            print('ZZ')
            print(i)
            print('ZZ')
            click_add = driver.find_element_by_xpath('.//*[@id=\'' + i + '\']/td/input')
            click_add.click()
        except WebDriverException:
            continue


#print(drop_down_options['DEM107'][0])
print("fieldNames: ",fieldNames)
print("Questions: ",Questions)
print("tab_ids_lst: ",tab_ids_lst)
print("next_tab: ",next_tab)
print("filter_fieldNames", filter_fieldNames)
print("select_lst: ",select_lst)
print("drop_down_select_ids: ",drop_down_select_ids)
print("drop_down_options: ",drop_down_options)
print("other_fieldNames: ",other_fieldNames)
print("quick_code_lookup: ",quick_code_lookup)

for a11 in quick_code_lookup:
    print(a11)

