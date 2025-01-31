import unittest

from selenium import webdriver

#Login to NBS
driver = webdriver.Chrome("C:\\Py\\chromedriver_win32\\chromedriver.exe")
driver.implicitly_wait(2)
driver.get("http://nedss-test1:7001/nbs/login")
driver.implicitly_wait(4)
driver.get("http://nedss-test1:7001/nbs/login")
driver.implicitly_wait(2)
driver.execute_script('document.getElementById("id_UserName").value= "pks"')
driver.implicitly_wait(2)
driver.execute_script('document.getElementById("id_Submit_bottom_ToolbarButtonGraphic").click()')
#driver.find_elements_by_id("id_Submit_top_ToolbarButtonGraphic").click()
#driver.execute_script('document.getElementById("id_Submit_top_ToolbarButtonGraphic").click()')

#Click System Management
driver.implicitly_wait(5)
driver.execute_script('document.getElementByXPath(".//*[@id=\'bd\']/form[1]/table/tbody/tr/td[1]/table/tbody/tr/td[11]/a").click()')
element = driver.find_elements_by_xpath(".//*[@id='bd']/form[1]/table/tbody/tr/td[1]/table/tbody/tr/td[11]/a")[0]
element.click()


#if element.is_displayed():
# print "Element found"
#else:
#print "Element not found"

