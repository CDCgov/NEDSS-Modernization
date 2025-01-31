import unittest

from selenium import webdriver

driver = webdriver.Firefox("C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe")
driver.implicitly_wait(2)
driver.window.open
driver.implicitly_wait(2)
driver.get("http://nedss-test1:7001/nbs/login")
driver.implicitly_wait(2)
driver.execute_script('document.getElementById("id_UserName").value= "pks"')
driver.implicitly_wait(2)
driver.execute_script('document.getElementById("id_Submit_top_ToolbarButtonGraphic").click')