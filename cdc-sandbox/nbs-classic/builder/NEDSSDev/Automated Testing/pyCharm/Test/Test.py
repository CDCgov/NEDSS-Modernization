

import time
from selenium import webdriver
from selenium.common.exceptions import NoSuchElementException
from selenium.common.exceptions import WebDriverException



driver = webdriver.Firefox(executable_path=r'C:\\Py\\geckodriver.exe')

###driver = webdriver.Firefox('C:\\Gecko\\geckodriver1.exe')


driver.get('http://www.google.com/xhtml')
time.sleep(5) # Let the user actually see something!
driver.get('http://nedss-perform1:7001/nbs/login')

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

INV_Option = driver.find_element_by_xpath('.//*[@id=\'parent\']/thead/tr/th[3]/div/label[5]/input')
INV_Option.click()

OK = driver.find_element_by_id('b1')
OK.click()


for x in range(1,2):
        Click_View_Page = driver.find_element_by_xpath('.//*[@id=\'parent\']/tbody/tr[1]/td[1]/a/img')
        Click_Edit_Page =  driver.find_element_by_xpath ('.//*[@id=\'parent\']/tbody/tr[1]/td[2]/a/img')
        Page_State= driver.find_element_by_xpath ('.//*[@id=\'parent\']/tbody/tr[20]/td[2]/a/img').text
        file = open('C:\\Users\\OrtegaJ\\Documents\\Work\\Py\\Test.txt','w')
        file.write(Page_State)
        print(Page_State)
        Click_View_Page.click()

        if Page_State == 'Published':
           Click_CreateNewDraft = driver.find_element_by_xpath ('.//*[@id=\'doc3\']/div[2]/table/tbody/tr/td[2]/input[3]')
           Click_CreateNewDraft.click()
           time.sleep(3)

        parentWindow = driver.current_window_handle
        window_before = driver.window_handles[0]

        Click_Publish = driver.find_element_by_xpath('.//*[@id=\'doc3\']/div[2]/table/tbody/tr/td[2]/input[3]')
        Click_Publish.click()

        time.sleep(3)
        for wait1 in range(10):

           try:
               window_after = driver.window_handles[1]
               file.write('wait1 try')

           except WebDriverException:
               file.write('wait1 except')
               time.sleep(2)



        driver.switch_to.window(window_after)
        time.sleep(2)

        Publish_Comments = driver.find_element_by_name('selection.versionNote')
        Publish_Comments.send_keys('Automated_VersionNotes')

        Publish_Submit = driver.find_element_by_name('Submit')
        Publish_Submit.click()

        #driver.switchTo().window(parentWindow)
        driver.switch_to.window(window_before)

        Return_to_Page_Library = driver.find_element_by_link_text('Return to Page Library')



        for wait2 in range(10):

            try:
                    Return_to_Page_Library.click()
                    file.write('wait2')
            except WebDriverException:
                    time.sleep(2)




#driver.execute_script('document.getElementById("id_UserName").value= "pks"')
#driver.implicitly_wait(2)
#driver.execute_script('document.getElementById("id_Submit_bottom_ToolbarButtonGraphic").click()')

#search_box = driver.find_element_by_name('q')
#search_box.send_keys('ChromeDriver')
#search_box.submit()
#time.sleep(5) # Let the user actually see something!
#driver.quit()
