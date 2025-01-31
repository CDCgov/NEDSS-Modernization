import time
from selenium import webdriver
from NBS_Login import nbs
# from NBS_Login import testpatientA

# driver = webdriver.Firefox(executable_path=r'C:\\Py\\geckodriver.exe')
driver_chrome = webdriver.Chrome('C:\\Py\\chromedriver_win32_new\\chromedriver.exe',service_args= ['--ignore-ssl-errors=true','--ssl-protocol=TLSv1'])

nbs.login_w_pks(driver_chrome)

nbs.testpatientA(driver_chrome)

lab_id = driver_chrome.find_element_by_xpath("/html/body/div/div/form/div[2]/div[4]/table[1]/tbody/tr[3]/td[1]/span[2]")

print("lab_id.text: ",lab_id.text)



