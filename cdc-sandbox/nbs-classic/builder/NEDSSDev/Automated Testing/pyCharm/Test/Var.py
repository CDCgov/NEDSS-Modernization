import time
from selenium.webdriver.common.keys import Keys
from selenium import webdriver



driver = webdriver.Firefox(executable_path=r'C:\\Py\\geckodriver.exe')

driver.get('http://www.google.com/xhtml')
time.sleep(5) # Let the user actually see something!
driver.get('http://nedss-perform1:7001/nbs/login')

login_box = driver.find_element_by_id('id_UserName')
login_box.send_keys('pks')

submit_login = driver.find_element_by_id('id_Submit_bottom_ToolbarButtonGraphic')
submit_login.click()

SM = driver.find_element_by_xpath('.//*[@id=\'bd\']/form[1]/table/tbody/tr/td[1]/table/tbody/tr/td[11]/a')
SM.click()

PM = driver.find_element_by_xpath('.//*[@id=\'systemAdmin5\']/thead/tr/th/a/img')
PM.click()

MP = driver.find_element_by_xpath('.//*[@id=\'systemAdmin5\']/tbody/tr/td/table/tbody/tr[2]/td/a')
MP.click()

Porting = driver.find_element_by_xpath('.//*[@id=\'bd\']/div[2]/input[1]')
Porting.click()

map1 = driver.find_element_by_xpath('.//*[@id=\'parent\']/tbody/tr[4]/td[1]/img')
map1.click()

map_answers = driver.find_element_by_xpath('.//*[@id=\'bd\']/div[3]/input[2]')
map_answers.click()


map_needed = driver.find_element_by_xpath('.//*[@id=\'parent\']/tbody/tr[1]/td[2]/img')
map_needed.click()

for i in range(20):
    set_no = driver.find_element_by_xpath('.//*[@id=\'pageMappingAttributes\']/table/tbody/tr[7]/td[2]/input')
    set_no.send_keys('No')
    time.sleep(1)
    click = driver.find_element_by_xpath('.//*[@id=\'fromQuestionID\']')
    click.click()
    time.sleep(1)

    Add = driver.find_element_by_xpath('.//*[@id=\'addButton\']')
    Add.click()
    time.sleep(1)