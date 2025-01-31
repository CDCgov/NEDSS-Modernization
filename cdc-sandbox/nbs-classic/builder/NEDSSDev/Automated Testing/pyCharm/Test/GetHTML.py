



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

file = open('C:\\Py\\PageSource.txt','w+')
PageSource = driver.page_source
file.writelines(PageSource)
file.close()

PageSplit = PageSource.split()
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
