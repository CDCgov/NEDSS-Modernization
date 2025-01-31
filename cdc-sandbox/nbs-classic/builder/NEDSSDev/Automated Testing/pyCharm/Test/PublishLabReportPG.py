

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
#driver.get('http://nedss-perform1:7001/nbs/login')
driver.get('http://35.170.137.196:7001/nbs/login')

login_box = driver.find_element_by_id('id_UserName')
login_box.send_keys('pks')

submit_login = driver.find_element_by_id('id_Submit_bottom_ToolbarButtonGraphic')
submit_login.click()



System_Management= driver.find_element_by_xpath('.//*[@id=\'bd\']/form[1]/table/tbody/tr/td[1]/table/tbody/tr/td[11]/a')
System_Management.click()

Page_Management = driver.find_element_by_xpath('.//*[@id=\'systemAdmin5\']/thead/tr/th/a/img')
Page_Management.click()

Manage_Pages = driver.find_element_by_xpath('.//*[@id=\'systemAdmin5\']/tbody/tr/td/table/tbody/tr[2]/td/a')
Manage_Pages.click()

time.sleep(2)
Event_Type_DropDown = driver.find_element_by_id('queueIcon')
Event_Type_DropDown.click()

Select_All = driver.find_element_by_xpath('.//*[@id=\'parent\']/thead/tr/th[3]/div/label[2]/input')
Select_All.click()

#INV_Option = driver.find_element_by_xpath('.//*[@id=\'parent\']/thead/tr/th[3]/div/label[5]/input')
#INV_Option.click()

lab_report_option = driver.find_element_by_css_selector('[value="Lab Report"]')
lab_report_option.click()

OK = driver.find_element_by_id('b1')
OK.click()

#Next_PageResults = driver.find_element_by_link_text('Next')
z=1

while (z<2):

    for x in range(20):
        print(x)
        Click_View_Page = driver.find_element_by_xpath('.//*[@id=\'parent\']/tbody/tr['+str(x+1)+']/td[1]/a/img')
        Click_Edit_Page = driver.find_element_by_xpath('.//*[@id=\'parent\']/tbody/tr[1]/td[2]/a/img')
        Page_State = driver.find_element_by_xpath('.//*[@id=\'parent\']/tbody/tr['+str(x+1)+']/td[5]').text
        file = open('C:\\Users\\OrtegaJ\\Documents\\Work\\Py\\Test.txt', 'w')
        file.write(Page_State)
        print(Page_State)
        Click_View_Page.click()
        print(Page_State)

        if Page_State == 'Published':
           Click_CreateNewDraft = driver.find_element_by_name('Create New Draft')
           Click_CreateNewDraft.click()
           print('if Page_State == Published ')
           time.sleep(10)

        if Page_State == 'Initial Draft':
           ReturntoPageLibrary = Return_to_Page_Library = driver.find_element_by_link_text('Return to Page Library')
           ReturntoPageLibrary.click()
           print('if Page_State == Initial Draft')
           time.sleep(10)
           continue #this is not skipping the loop to x=3

        parentWindow = driver.current_window_handle
        print('parentWindow = driver.current_window_handle')
        window_before = driver.window_handles[0]
        print('window_before = driver.window_handles[0]')
        time.sleep(3)
        Click_Publish = driver.find_element_by_name('Publish')
        Click_Publish.send_keys(Keys.RETURN)

        print('Click_Publish')

        time.sleep(3)
        #Edt for loop from test1
        window_after = driver.window_handles[1]

        driver.switch_to.window(window_after)
        time.sleep(2)

        Publish_Comments = driver.find_element_by_name('selection.versionNote')
        Publish_Comments.send_keys('Automated_VersionNotes')

        Publish_Submit = driver.find_element_by_name('Submit')
        Publish_Submit.click()

        #driver.switchTo().window(parentWindow)
        driver.switch_to.window(window_before)
        time.sleep(10)
        Return_to_Page_Library = driver.find_element_by_link_text('Return to Page Library')
        print ("Return_to_Page_Library")

        wait2 =0
        for wait2 in range(10):

           try:
              Return_to_Page_Library.click()
              file.write('wait2')
              print("Return_to_Page_Library1")
              wait2=wait2+1
           except WebDriverException:
              time.sleep(2)
    try:
        Next_PageResults = driver.find_element_by_link_text('Next')
    except WebDriverException:
        Next_PageResults =''
    if Next_PageResults =='':
        z = 2
        break
    else:
        Next_PageResults.click()


