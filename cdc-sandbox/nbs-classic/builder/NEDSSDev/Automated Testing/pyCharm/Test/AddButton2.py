


import os
import win_unicode_console
import io
import re
import time
from selenium.webdriver.common.keys import Keys
from selenium import webdriver
from selenium.common.exceptions import WebDriverException

import selenium.webdriver as webdriver
import selenium.webdriver.support.ui as UI
import contextlib

with contextlib.closing(webdriver.Firefox(executable_path=r'C:\\Py\\geckodriver.exe')) as driver:

    #driver = webdriver.Chrome('C:\\Py\\chromedriver_win32\\chromedriver.exe')  # Optional argument, if not specified will search path.

    #driver = webdriver.Firefox(executable_path=r'C:\\Py\\geckodriver.exe')

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

    time.sleep(2)
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
    Questions = []
    tab_ids_lst = []
    next_tab = []
    filter_fieldNames = []

    a = 0
    b = 0
    c = 0
    #print(PageSplit)

    Suffix = driver.find_element_by_id('DEM107')
    Suffix_options = [x for x in Suffix.find_elements_by_tag_name("option")]
    Suffix_options_val = []
    #Suffix.send_keys(Suffix_options[0])
    for element in Suffix_options:
        print(element.get_attribute("value"))
        Suffix_options_val.append(element.get_attribute("value"))

    Suffix_text = driver.find_element_by_name('DEM107_textbox')
    Suffix_text.send_keys(Suffix_options_val[1])
    #print(Suffix_options)
    print(Suffix_options_val)



    # dropdown_menu = driver.find_element_by_name('DEM107_textbox')
    # for option in dropdown_menu.options:
    #         print (option.text)


    select = UI.Select(driver.find_element_by_id('DEM107'))
    for option in select.options:
        print(option.text, option.get_attribute('value'))

# clicktab = driver.find_element_by_id('tabs0head1')
# clicktab.click()
#
# Add_Date = driver.find_element_by_id('338822')
# Add_Date.send_keys('10/04/2018')
#
# Click_Add = driver.find_element_by_link_text('Add')
# Click_Add.click()


